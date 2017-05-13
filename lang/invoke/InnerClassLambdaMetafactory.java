/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.PropertyPermission;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import sun.invoke.util.BytecodeDescriptor;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InnerClassLambdaMetafactory
/*     */   extends AbstractValidatingLambdaMetafactory
/*     */ {
/*  52 */   private static final Unsafe UNSAFE = ;
/*     */   
/*     */   private static final int CLASSFILE_VERSION = 52;
/*  55 */   private static final String METHOD_DESCRIPTOR_VOID = Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]);
/*     */   
/*     */   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
/*     */   
/*     */   private static final String NAME_CTOR = "<init>";
/*     */   
/*     */   private static final String NAME_FACTORY = "get$Lambda";
/*     */   private static final String NAME_SERIALIZED_LAMBDA = "java/lang/invoke/SerializedLambda";
/*     */   private static final String NAME_NOT_SERIALIZABLE_EXCEPTION = "java/io/NotSerializableException";
/*     */   private static final String DESCR_METHOD_WRITE_REPLACE = "()Ljava/lang/Object;";
/*     */   private static final String DESCR_METHOD_WRITE_OBJECT = "(Ljava/io/ObjectOutputStream;)V";
/*     */   private static final String DESCR_METHOD_READ_OBJECT = "(Ljava/io/ObjectInputStream;)V";
/*     */   private static final String NAME_METHOD_WRITE_REPLACE = "writeReplace";
/*     */   private static final String NAME_METHOD_READ_OBJECT = "readObject";
/*     */   private static final String NAME_METHOD_WRITE_OBJECT = "writeObject";
/*  70 */   private static final String DESCR_CTOR_SERIALIZED_LAMBDA = MethodType.methodType(Void.TYPE, Class.class, new Class[] { String.class, String.class, String.class, Integer.TYPE, String.class, String.class, String.class, String.class, Object[].class })
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  75 */     .toMethodDescriptorString();
/*     */   
/*  77 */   private static final String DESCR_CTOR_NOT_SERIALIZABLE_EXCEPTION = MethodType.methodType(Void.TYPE, String.class).toMethodDescriptorString();
/*  78 */   private static final String[] SER_HOSTILE_EXCEPTIONS = { "java/io/NotSerializableException" };
/*     */   
/*     */ 
/*  81 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*     */   
/*     */ 
/*  84 */   private static final AtomicInteger counter = new AtomicInteger(0);
/*     */   private static final ProxyClassesDumper dumper;
/*     */   private final String implMethodClassName;
/*     */   private final String implMethodName;
/*     */   private final String implMethodDesc;
/*     */   private final Class<?> implMethodReturnClass;
/*     */   
/*  91 */   static { String str = (String)AccessController.doPrivileged(new GetPropertyAction("jdk.internal.lambda.dumpProxyClasses"), null, new Permission[] { new PropertyPermission("jdk.internal.lambda.dumpProxyClasses", "read") });
/*     */     
/*     */ 
/*  94 */     dumper = null == str ? null : ProxyClassesDumper.getInstance(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final MethodType constructorType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final ClassWriter cw;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String[] argNames;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String[] argDescs;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String lambdaClassName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InnerClassLambdaMetafactory(MethodHandles.Lookup paramLookup, MethodType paramMethodType1, String paramString, MethodType paramMethodType2, MethodHandle paramMethodHandle, MethodType paramMethodType3, boolean paramBoolean, Class<?>[] paramArrayOfClass, MethodType[] paramArrayOfMethodType)
/*     */     throws LambdaConversionException
/*     */   {
/* 155 */     super(paramLookup, paramMethodType1, paramString, paramMethodType2, paramMethodHandle, paramMethodType3, paramBoolean, paramArrayOfClass, paramArrayOfMethodType);
/*     */     
/*     */ 
/* 158 */     this.implMethodClassName = this.implDefiningClass.getName().replace('.', '/');
/* 159 */     this.implMethodName = this.implInfo.getName();
/* 160 */     this.implMethodDesc = this.implMethodType.toMethodDescriptorString();
/*     */     
/*     */ 
/* 163 */     this.implMethodReturnClass = (this.implKind == 8 ? this.implDefiningClass : this.implMethodType.returnType());
/* 164 */     this.constructorType = paramMethodType1.changeReturnType(Void.TYPE);
/* 165 */     this.lambdaClassName = (this.targetClass.getName().replace('.', '/') + "$$Lambda$" + counter.incrementAndGet());
/* 166 */     this.cw = new ClassWriter(1);
/* 167 */     int i = paramMethodType1.parameterCount();
/* 168 */     if (i > 0) {
/* 169 */       this.argNames = new String[i];
/* 170 */       this.argDescs = new String[i];
/* 171 */       for (int j = 0; j < i; j++) {
/* 172 */         this.argNames[j] = ("arg$" + (j + 1));
/* 173 */         this.argDescs[j] = BytecodeDescriptor.unparse(paramMethodType1.parameterType(j));
/*     */       }
/*     */     } else {
/* 176 */       this.argNames = (this.argDescs = EMPTY_STRING_ARRAY);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   CallSite buildCallSite()
/*     */     throws LambdaConversionException
/*     */   {
/* 194 */     final Class localClass = spinInnerClass();
/* 195 */     if (this.invokedType.parameterCount() == 0) {
/* 196 */       Constructor[] arrayOfConstructor = (Constructor[])AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Constructor[] run()
/*     */         {
/* 200 */           Constructor[] arrayOfConstructor = localClass.getDeclaredConstructors();
/* 201 */           if (arrayOfConstructor.length == 1)
/*     */           {
/*     */ 
/* 204 */             arrayOfConstructor[0].setAccessible(true);
/*     */           }
/* 206 */           return arrayOfConstructor;
/*     */         }
/*     */       });
/* 209 */       if (arrayOfConstructor.length != 1)
/*     */       {
/* 211 */         throw new LambdaConversionException("Expected one lambda constructor for " + localClass.getCanonicalName() + ", got " + arrayOfConstructor.length);
/*     */       }
/*     */       try
/*     */       {
/* 215 */         Object localObject = arrayOfConstructor[0].newInstance(new Object[0]);
/* 216 */         return new ConstantCallSite(MethodHandles.constant(this.samBase, localObject));
/*     */       }
/*     */       catch (ReflectiveOperationException localReflectiveOperationException2) {
/* 219 */         throw new LambdaConversionException("Exception instantiating lambda object", localReflectiveOperationException2);
/*     */       }
/*     */     }
/*     */     try {
/* 223 */       UNSAFE.ensureClassInitialized(localClass);
/*     */       
/*     */ 
/* 226 */       return new ConstantCallSite(MethodHandles.Lookup.IMPL_LOOKUP.findStatic(localClass, "get$Lambda", this.invokedType));
/*     */     }
/*     */     catch (ReflectiveOperationException localReflectiveOperationException1) {
/* 229 */       throw new LambdaConversionException("Exception finding constructor", localReflectiveOperationException1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Class<?> spinInnerClass()
/*     */     throws LambdaConversionException
/*     */   {
/* 251 */     String str = this.samBase.getName().replace('.', '/');
/* 252 */     int i = (!this.isSerializable) && (Serializable.class.isAssignableFrom(this.samBase)) ? 1 : 0;
/* 253 */     String[] arrayOfString; Class localClass; if (this.markerInterfaces.length == 0) {
/* 254 */       arrayOfString = new String[] { str };
/*     */     }
/*     */     else {
/* 257 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(this.markerInterfaces.length + 1);
/* 258 */       localLinkedHashSet.add(str);
/* 259 */       for (localClass : this.markerInterfaces) {
/* 260 */         localLinkedHashSet.add(localClass.getName().replace('.', '/'));
/* 261 */         i |= ((!this.isSerializable) && (Serializable.class.isAssignableFrom(localClass)) ? 1 : 0);
/*     */       }
/* 263 */       arrayOfString = (String[])localLinkedHashSet.toArray(new String[localLinkedHashSet.size()]);
/*     */     }
/*     */     
/* 266 */     this.cw.visit(52, 4144, this.lambdaClassName, null, "java/lang/Object", arrayOfString);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 271 */     for (int j = 0; j < this.argDescs.length; j++) {
/* 272 */       ??? = this.cw.visitField(18, this.argNames[j], this.argDescs[j], null, null);
/*     */       
/*     */ 
/*     */ 
/* 276 */       ((FieldVisitor)???).visitEnd();
/*     */     }
/*     */     
/* 279 */     generateConstructor();
/*     */     
/* 281 */     if (this.invokedType.parameterCount() != 0) {
/* 282 */       generateFactory();
/*     */     }
/*     */     
/*     */ 
/* 286 */     MethodVisitor localMethodVisitor = this.cw.visitMethod(1, this.samMethodName, this.samMethodType
/* 287 */       .toMethodDescriptorString(), null, null);
/* 288 */     new ForwardingMethodGenerator(localMethodVisitor).generate(this.samMethodType);
/*     */     
/*     */ 
/* 291 */     if (this.additionalBridges != null) {
/* 292 */       for (localClass : this.additionalBridges) {
/* 293 */         localMethodVisitor = this.cw.visitMethod(65, this.samMethodName, localClass
/* 294 */           .toMethodDescriptorString(), null, null);
/* 295 */         new ForwardingMethodGenerator(localMethodVisitor).generate(localClass);
/*     */       }
/*     */     }
/*     */     
/* 299 */     if (this.isSerializable) {
/* 300 */       generateSerializationFriendlyMethods();
/* 301 */     } else if (i != 0) {
/* 302 */       generateSerializationHostileMethods();
/*     */     }
/* 304 */     this.cw.visitEnd();
/*     */     
/*     */ 
/*     */ 
/* 308 */     ??? = this.cw.toByteArray();
/*     */     
/*     */ 
/* 311 */     if (dumper != null) {
/* 312 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Void run() {
/* 315 */           InnerClassLambdaMetafactory.dumper.dumpClass(InnerClassLambdaMetafactory.this.lambdaClassName, localObject);
/* 316 */           return null; } }, null, new Permission[] { new FilePermission("<<ALL FILES>>", "read, write"), new PropertyPermission("user.dir", "read") });
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 324 */     return UNSAFE.defineAnonymousClass(this.targetClass, (byte[])???, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generateFactory()
/*     */   {
/* 331 */     MethodVisitor localMethodVisitor = this.cw.visitMethod(10, "get$Lambda", this.invokedType.toMethodDescriptorString(), null, null);
/* 332 */     localMethodVisitor.visitCode();
/* 333 */     localMethodVisitor.visitTypeInsn(187, this.lambdaClassName);
/* 334 */     localMethodVisitor.visitInsn(89);
/* 335 */     int i = this.invokedType.parameterCount();
/* 336 */     int j = 0; for (int k = 0; j < i; j++) {
/* 337 */       Class localClass = this.invokedType.parameterType(j);
/* 338 */       localMethodVisitor.visitVarInsn(getLoadOpcode(localClass), k);
/* 339 */       k += getParameterSize(localClass);
/*     */     }
/* 341 */     localMethodVisitor.visitMethodInsn(183, this.lambdaClassName, "<init>", this.constructorType.toMethodDescriptorString());
/* 342 */     localMethodVisitor.visitInsn(176);
/* 343 */     localMethodVisitor.visitMaxs(-1, -1);
/* 344 */     localMethodVisitor.visitEnd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void generateConstructor()
/*     */   {
/* 352 */     MethodVisitor localMethodVisitor = this.cw.visitMethod(2, "<init>", this.constructorType
/* 353 */       .toMethodDescriptorString(), null, null);
/* 354 */     localMethodVisitor.visitCode();
/* 355 */     localMethodVisitor.visitVarInsn(25, 0);
/* 356 */     localMethodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", METHOD_DESCRIPTOR_VOID);
/*     */     
/* 358 */     int i = this.invokedType.parameterCount();
/* 359 */     int j = 0; for (int k = 0; j < i; j++) {
/* 360 */       localMethodVisitor.visitVarInsn(25, 0);
/* 361 */       Class localClass = this.invokedType.parameterType(j);
/* 362 */       localMethodVisitor.visitVarInsn(getLoadOpcode(localClass), k + 1);
/* 363 */       k += getParameterSize(localClass);
/* 364 */       localMethodVisitor.visitFieldInsn(181, this.lambdaClassName, this.argNames[j], this.argDescs[j]);
/*     */     }
/* 366 */     localMethodVisitor.visitInsn(177);
/*     */     
/* 368 */     localMethodVisitor.visitMaxs(-1, -1);
/* 369 */     localMethodVisitor.visitEnd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void generateSerializationFriendlyMethods()
/*     */   {
/* 378 */     TypeConvertingMethodAdapter localTypeConvertingMethodAdapter = new TypeConvertingMethodAdapter(this.cw.visitMethod(18, "writeReplace", "()Ljava/lang/Object;", null, null));
/*     */     
/*     */ 
/*     */ 
/* 382 */     localTypeConvertingMethodAdapter.visitCode();
/* 383 */     localTypeConvertingMethodAdapter.visitTypeInsn(187, "java/lang/invoke/SerializedLambda");
/* 384 */     localTypeConvertingMethodAdapter.visitInsn(89);
/* 385 */     localTypeConvertingMethodAdapter.visitLdcInsn(Type.getType(this.targetClass));
/* 386 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.invokedType.returnType().getName().replace('.', '/'));
/* 387 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.samMethodName);
/* 388 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.samMethodType.toMethodDescriptorString());
/* 389 */     localTypeConvertingMethodAdapter.visitLdcInsn(Integer.valueOf(this.implInfo.getReferenceKind()));
/* 390 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.implInfo.getDeclaringClass().getName().replace('.', '/'));
/* 391 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.implInfo.getName());
/* 392 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.implInfo.getMethodType().toMethodDescriptorString());
/* 393 */     localTypeConvertingMethodAdapter.visitLdcInsn(this.instantiatedMethodType.toMethodDescriptorString());
/* 394 */     localTypeConvertingMethodAdapter.iconst(this.argDescs.length);
/* 395 */     localTypeConvertingMethodAdapter.visitTypeInsn(189, "java/lang/Object");
/* 396 */     for (int i = 0; i < this.argDescs.length; i++) {
/* 397 */       localTypeConvertingMethodAdapter.visitInsn(89);
/* 398 */       localTypeConvertingMethodAdapter.iconst(i);
/* 399 */       localTypeConvertingMethodAdapter.visitVarInsn(25, 0);
/* 400 */       localTypeConvertingMethodAdapter.visitFieldInsn(180, this.lambdaClassName, this.argNames[i], this.argDescs[i]);
/* 401 */       localTypeConvertingMethodAdapter.boxIfTypePrimitive(Type.getType(this.argDescs[i]));
/* 402 */       localTypeConvertingMethodAdapter.visitInsn(83);
/*     */     }
/* 404 */     localTypeConvertingMethodAdapter.visitMethodInsn(183, "java/lang/invoke/SerializedLambda", "<init>", DESCR_CTOR_SERIALIZED_LAMBDA);
/*     */     
/* 406 */     localTypeConvertingMethodAdapter.visitInsn(176);
/*     */     
/* 408 */     localTypeConvertingMethodAdapter.visitMaxs(-1, -1);
/* 409 */     localTypeConvertingMethodAdapter.visitEnd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generateSerializationHostileMethods()
/*     */   {
/* 416 */     MethodVisitor localMethodVisitor = this.cw.visitMethod(18, "writeObject", "(Ljava/io/ObjectOutputStream;)V", null, SER_HOSTILE_EXCEPTIONS);
/*     */     
/*     */ 
/* 419 */     localMethodVisitor.visitCode();
/* 420 */     localMethodVisitor.visitTypeInsn(187, "java/io/NotSerializableException");
/* 421 */     localMethodVisitor.visitInsn(89);
/* 422 */     localMethodVisitor.visitLdcInsn("Non-serializable lambda");
/* 423 */     localMethodVisitor.visitMethodInsn(183, "java/io/NotSerializableException", "<init>", DESCR_CTOR_NOT_SERIALIZABLE_EXCEPTION);
/*     */     
/* 425 */     localMethodVisitor.visitInsn(191);
/* 426 */     localMethodVisitor.visitMaxs(-1, -1);
/* 427 */     localMethodVisitor.visitEnd();
/*     */     
/* 429 */     localMethodVisitor = this.cw.visitMethod(18, "readObject", "(Ljava/io/ObjectInputStream;)V", null, SER_HOSTILE_EXCEPTIONS);
/*     */     
/*     */ 
/* 432 */     localMethodVisitor.visitCode();
/* 433 */     localMethodVisitor.visitTypeInsn(187, "java/io/NotSerializableException");
/* 434 */     localMethodVisitor.visitInsn(89);
/* 435 */     localMethodVisitor.visitLdcInsn("Non-serializable lambda");
/* 436 */     localMethodVisitor.visitMethodInsn(183, "java/io/NotSerializableException", "<init>", DESCR_CTOR_NOT_SERIALIZABLE_EXCEPTION);
/*     */     
/* 438 */     localMethodVisitor.visitInsn(191);
/* 439 */     localMethodVisitor.visitMaxs(-1, -1);
/* 440 */     localMethodVisitor.visitEnd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class ForwardingMethodGenerator
/*     */     extends TypeConvertingMethodAdapter
/*     */   {
/*     */     ForwardingMethodGenerator(MethodVisitor paramMethodVisitor)
/*     */     {
/* 450 */       super();
/*     */     }
/*     */     
/*     */     void generate(MethodType paramMethodType) {
/* 454 */       visitCode();
/*     */       
/* 456 */       if (InnerClassLambdaMetafactory.this.implKind == 8) {
/* 457 */         visitTypeInsn(187, InnerClassLambdaMetafactory.this.implMethodClassName);
/* 458 */         visitInsn(89);
/*     */       }
/* 460 */       for (int i = 0; i < InnerClassLambdaMetafactory.this.argNames.length; i++) {
/* 461 */         visitVarInsn(25, 0);
/* 462 */         visitFieldInsn(180, InnerClassLambdaMetafactory.this.lambdaClassName, InnerClassLambdaMetafactory.this.argNames[i], InnerClassLambdaMetafactory.this.argDescs[i]);
/*     */       }
/*     */       
/* 465 */       convertArgumentTypes(paramMethodType);
/*     */       
/*     */ 
/* 468 */       visitMethodInsn(invocationOpcode(), InnerClassLambdaMetafactory.this.implMethodClassName, 
/* 469 */         InnerClassLambdaMetafactory.this.implMethodName, InnerClassLambdaMetafactory.this.implMethodDesc, InnerClassLambdaMetafactory.this.implDefiningClass
/* 470 */         .isInterface());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 475 */       Class localClass = paramMethodType.returnType();
/* 476 */       convertType(InnerClassLambdaMetafactory.this.implMethodReturnClass, localClass, localClass);
/* 477 */       visitInsn(InnerClassLambdaMetafactory.getReturnOpcode(localClass));
/*     */       
/* 479 */       visitMaxs(-1, -1);
/* 480 */       visitEnd();
/*     */     }
/*     */     
/*     */     private void convertArgumentTypes(MethodType paramMethodType) {
/* 484 */       int i = 0;
/*     */       
/* 486 */       int j = (InnerClassLambdaMetafactory.this.implIsInstanceMethod) && (InnerClassLambdaMetafactory.this.invokedType.parameterCount() == 0) ? 1 : 0;
/* 487 */       int k = j != 0 ? 1 : 0;
/* 488 */       if (j != 0)
/*     */       {
/* 490 */         Class localClass1 = paramMethodType.parameterType(0);
/* 491 */         visitVarInsn(InnerClassLambdaMetafactory.getLoadOpcode(localClass1), i + 1);
/* 492 */         i += InnerClassLambdaMetafactory.getParameterSize(localClass1);
/* 493 */         convertType(localClass1, InnerClassLambdaMetafactory.this.implDefiningClass, InnerClassLambdaMetafactory.this.instantiatedMethodType.parameterType(0));
/*     */       }
/* 495 */       int m = paramMethodType.parameterCount();
/* 496 */       int n = InnerClassLambdaMetafactory.this.implMethodType.parameterCount() - m;
/* 497 */       for (int i1 = k; i1 < m; i1++) {
/* 498 */         Class localClass2 = paramMethodType.parameterType(i1);
/* 499 */         visitVarInsn(InnerClassLambdaMetafactory.getLoadOpcode(localClass2), i + 1);
/* 500 */         i += InnerClassLambdaMetafactory.getParameterSize(localClass2);
/* 501 */         convertType(localClass2, InnerClassLambdaMetafactory.this.implMethodType.parameterType(n + i1), InnerClassLambdaMetafactory.this.instantiatedMethodType.parameterType(i1));
/*     */       }
/*     */     }
/*     */     
/*     */     private int invocationOpcode() throws InternalError {
/* 506 */       switch (InnerClassLambdaMetafactory.this.implKind) {
/*     */       case 6: 
/* 508 */         return 184;
/*     */       case 8: 
/* 510 */         return 183;
/*     */       case 5: 
/* 512 */         return 182;
/*     */       case 9: 
/* 514 */         return 185;
/*     */       case 7: 
/* 516 */         return 183;
/*     */       }
/* 518 */       throw new InternalError("Unexpected invocation kind: " + InnerClassLambdaMetafactory.this.implKind);
/*     */     }
/*     */   }
/*     */   
/*     */   static int getParameterSize(Class<?> paramClass)
/*     */   {
/* 524 */     if (paramClass == Void.TYPE)
/* 525 */       return 0;
/* 526 */     if ((paramClass == Long.TYPE) || (paramClass == Double.TYPE)) {
/* 527 */       return 2;
/*     */     }
/* 529 */     return 1;
/*     */   }
/*     */   
/*     */   static int getLoadOpcode(Class<?> paramClass) {
/* 533 */     if (paramClass == Void.TYPE) {
/* 534 */       throw new InternalError("Unexpected void type of load opcode");
/*     */     }
/* 536 */     return 21 + getOpcodeOffset(paramClass);
/*     */   }
/*     */   
/*     */   static int getReturnOpcode(Class<?> paramClass) {
/* 540 */     if (paramClass == Void.TYPE) {
/* 541 */       return 177;
/*     */     }
/* 543 */     return 172 + getOpcodeOffset(paramClass);
/*     */   }
/*     */   
/*     */   private static int getOpcodeOffset(Class<?> paramClass) {
/* 547 */     if (paramClass.isPrimitive()) {
/* 548 */       if (paramClass == Long.TYPE)
/* 549 */         return 1;
/* 550 */       if (paramClass == Float.TYPE)
/* 551 */         return 2;
/* 552 */       if (paramClass == Double.TYPE) {
/* 553 */         return 3;
/*     */       }
/* 555 */       return 0;
/*     */     }
/* 557 */     return 4;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/InnerClassLambdaMetafactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */