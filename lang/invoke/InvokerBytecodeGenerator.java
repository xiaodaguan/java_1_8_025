/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*      */ import jdk.internal.org.objectweb.asm.Label;
/*      */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*      */ import sun.invoke.util.VerifyAccess;
/*      */ import sun.invoke.util.VerifyType;
/*      */ import sun.invoke.util.Wrapper;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class InvokerBytecodeGenerator
/*      */ {
/*      */   private static final String MH = "java/lang/invoke/MethodHandle";
/*      */   private static final String MHI = "java/lang/invoke/MethodHandleImpl";
/*      */   private static final String LF = "java/lang/invoke/LambdaForm";
/*      */   private static final String LFN = "java/lang/invoke/LambdaForm$Name";
/*      */   private static final String CLS = "java/lang/Class";
/*      */   private static final String OBJ = "java/lang/Object";
/*      */   private static final String OBJARY = "[Ljava/lang/Object;";
/*      */   private static final String LF_SIG = "Ljava/lang/invoke/LambdaForm;";
/*      */   private static final String LFN_SIG = "Ljava/lang/invoke/LambdaForm$Name;";
/*      */   private static final String LL_SIG = "(Ljava/lang/Object;)Ljava/lang/Object;";
/*      */   private static final String CLL_SIG = "(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;";
/*      */   private static final String superName = "java/lang/invoke/LambdaForm";
/*      */   private final String className;
/*      */   private final String sourceFile;
/*      */   private final LambdaForm lambdaForm;
/*      */   private final String invokerName;
/*      */   private final MethodType invokerType;
/*      */   private final int[] localsMap;
/*      */   private ClassWriter cw;
/*      */   private MethodVisitor mv;
/*      */   private static final MemberName.Factory MEMBERNAME_FACTORY;
/*      */   private static final Class<?> HOST_CLASS;
/*      */   private static final HashMap<String, Integer> DUMP_CLASS_FILES_COUNTERS;
/*      */   private static final File DUMP_CLASS_FILES_DIR;
/*      */   
/*      */   private InvokerBytecodeGenerator(LambdaForm paramLambdaForm, int paramInt, String paramString1, String paramString2, MethodType paramMethodType)
/*      */   {
/*   87 */     if (paramString2.contains(".")) {
/*   88 */       int i = paramString2.indexOf(".");
/*   89 */       paramString1 = paramString2.substring(0, i);
/*   90 */       paramString2 = paramString2.substring(i + 1);
/*      */     }
/*   92 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/*   93 */       paramString1 = makeDumpableClassName(paramString1);
/*      */     }
/*   95 */     this.className = ("java/lang/invoke/LambdaForm$" + paramString1);
/*   96 */     this.sourceFile = ("LambdaForm$" + paramString1);
/*   97 */     this.lambdaForm = paramLambdaForm;
/*   98 */     this.invokerName = paramString2;
/*   99 */     this.invokerType = paramMethodType;
/*  100 */     this.localsMap = new int[paramInt];
/*      */   }
/*      */   
/*      */   private InvokerBytecodeGenerator(String paramString1, String paramString2, MethodType paramMethodType) {
/*  104 */     this(null, paramMethodType.parameterCount(), paramString1, paramString2, paramMethodType);
/*      */     
/*      */ 
/*  107 */     for (int i = 0; i < this.localsMap.length; i++) {
/*  108 */       this.localsMap[i] = (paramMethodType.parameterSlotCount() - paramMethodType.parameterSlotDepth(i));
/*      */     }
/*      */   }
/*      */   
/*      */   private InvokerBytecodeGenerator(String paramString, LambdaForm paramLambdaForm, MethodType paramMethodType) {
/*  113 */     this(paramLambdaForm, paramLambdaForm.names.length, paramString, paramLambdaForm.debugName, paramMethodType);
/*      */     
/*      */ 
/*  116 */     LambdaForm.Name[] arrayOfName = paramLambdaForm.names;
/*  117 */     int i = 0; for (int j = 0; i < this.localsMap.length; i++) {
/*  118 */       this.localsMap[i] = j;
/*  119 */       j += Wrapper.forBasicType(arrayOfName[i].type).stackSlots();
/*      */     }
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*   82 */     MEMBERNAME_FACTORY = MemberName.getFactory();
/*   83 */     HOST_CLASS = LambdaForm.class;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  130 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/*  131 */       DUMP_CLASS_FILES_COUNTERS = new HashMap();
/*      */       try {
/*  133 */         File localFile = new File("DUMP_CLASS_FILES");
/*  134 */         if (!localFile.exists()) {
/*  135 */           localFile.mkdirs();
/*      */         }
/*  137 */         DUMP_CLASS_FILES_DIR = localFile;
/*  138 */         System.out.println("Dumping class files to " + DUMP_CLASS_FILES_DIR + "/...");
/*      */       } catch (Exception localException) {
/*  140 */         throw MethodHandleStatics.newInternalError(localException);
/*      */       }
/*      */     } else {
/*  143 */       DUMP_CLASS_FILES_COUNTERS = null;
/*  144 */       DUMP_CLASS_FILES_DIR = null;
/*      */     }
/*      */   }
/*      */   
/*      */   static void maybeDump(String paramString, final byte[] paramArrayOfByte) {
/*  149 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/*  150 */       System.out.println("dump: " + paramString);
/*  151 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*      */           try {
/*  155 */             String str = this.val$className;
/*      */             
/*  157 */             File localFile = new File(InvokerBytecodeGenerator.DUMP_CLASS_FILES_DIR, str + ".class");
/*  158 */             localFile.getParentFile().mkdirs();
/*  159 */             FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
/*  160 */             localFileOutputStream.write(paramArrayOfByte);
/*  161 */             localFileOutputStream.close();
/*  162 */             return null;
/*      */           } catch (IOException localIOException) {
/*  164 */             throw MethodHandleStatics.newInternalError(localIOException);
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */   
/*      */   private static String makeDumpableClassName(String paramString)
/*      */   {
/*      */     Integer localInteger;
/*  174 */     synchronized (DUMP_CLASS_FILES_COUNTERS) {
/*  175 */       localInteger = (Integer)DUMP_CLASS_FILES_COUNTERS.get(paramString);
/*  176 */       if (localInteger == null) localInteger = Integer.valueOf(0);
/*  177 */       DUMP_CLASS_FILES_COUNTERS.put(paramString, Integer.valueOf(localInteger.intValue() + 1));
/*      */     }
/*  179 */     ??? = localInteger.toString();
/*  180 */     while (((String)???).length() < 3)
/*  181 */       ??? = "0" + (String)???;
/*  182 */     paramString = paramString + (String)???;
/*  183 */     return paramString;
/*      */   }
/*      */   
/*      */   class CpPatch {
/*      */     final int index;
/*      */     final String placeholder;
/*      */     final Object value;
/*      */     
/*  191 */     CpPatch(int paramInt, String paramString, Object paramObject) { this.index = paramInt;
/*  192 */       this.placeholder = paramString;
/*  193 */       this.value = paramObject;
/*      */     }
/*      */     
/*  196 */     public String toString() { return "CpPatch/index=" + this.index + ",placeholder=" + this.placeholder + ",value=" + this.value; }
/*      */   }
/*      */   
/*      */ 
/*  200 */   Map<Object, CpPatch> cpPatches = new HashMap();
/*      */   
/*  202 */   int cph = 0;
/*      */   
/*      */   String constantPlaceholder(Object paramObject) {
/*  205 */     String str = "CONSTANT_PLACEHOLDER_" + this.cph++;
/*  206 */     if (MethodHandleStatics.DUMP_CLASS_FILES) str = str + " <<" + paramObject.toString() + ">>";
/*  207 */     if (this.cpPatches.containsKey(str)) {
/*  208 */       throw new InternalError("observed CP placeholder twice: " + str);
/*      */     }
/*      */     
/*  211 */     int i = this.cw.newConst(str);
/*  212 */     this.cpPatches.put(str, new CpPatch(i, str, paramObject));
/*  213 */     return str;
/*      */   }
/*      */   
/*      */   Object[] cpPatches(byte[] paramArrayOfByte) {
/*  217 */     int i = getConstantPoolSize(paramArrayOfByte);
/*  218 */     Object[] arrayOfObject = new Object[i];
/*  219 */     for (CpPatch localCpPatch : this.cpPatches.values()) {
/*  220 */       if (localCpPatch.index >= i)
/*  221 */         throw new InternalError("in cpool[" + i + "]: " + localCpPatch + "\n" + Arrays.toString(Arrays.copyOf(paramArrayOfByte, 20)));
/*  222 */       arrayOfObject[localCpPatch.index] = localCpPatch.value;
/*      */     }
/*  224 */     return arrayOfObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int getConstantPoolSize(byte[] paramArrayOfByte)
/*      */   {
/*  239 */     return (paramArrayOfByte[8] & 0xFF) << 8 | paramArrayOfByte[9] & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private MemberName loadMethod(byte[] paramArrayOfByte)
/*      */   {
/*  246 */     Class localClass = loadAndInitializeInvokerClass(paramArrayOfByte, cpPatches(paramArrayOfByte));
/*  247 */     return resolveInvokerMember(localClass, this.invokerName, this.invokerType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static Class<?> loadAndInitializeInvokerClass(byte[] paramArrayOfByte, Object[] paramArrayOfObject)
/*      */   {
/*  254 */     Class localClass = MethodHandleStatics.UNSAFE.defineAnonymousClass(HOST_CLASS, paramArrayOfByte, paramArrayOfObject);
/*  255 */     MethodHandleStatics.UNSAFE.ensureClassInitialized(localClass);
/*  256 */     return localClass;
/*      */   }
/*      */   
/*      */   private static MemberName resolveInvokerMember(Class<?> paramClass, String paramString, MethodType paramMethodType) {
/*  260 */     MemberName localMemberName = new MemberName(paramClass, paramString, paramMethodType, (byte)6);
/*      */     
/*      */     try
/*      */     {
/*  264 */       localMemberName = MEMBERNAME_FACTORY.resolveOrFail((byte)6, localMemberName, HOST_CLASS, ReflectiveOperationException.class);
/*      */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/*  266 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*      */     }
/*      */     
/*  269 */     return localMemberName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void classFilePrologue()
/*      */   {
/*  277 */     this.cw = new ClassWriter(3);
/*  278 */     this.cw.visit(52, 48, this.className, null, "java/lang/invoke/LambdaForm", null);
/*  279 */     this.cw.visitSource(this.sourceFile, null);
/*      */     
/*  281 */     String str = this.invokerType.toMethodDescriptorString();
/*  282 */     this.mv = this.cw.visitMethod(8, this.invokerName, str, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void classFileEpilogue()
/*      */   {
/*  289 */     this.mv.visitMaxs(0, 0);
/*  290 */     this.mv.visitEnd();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void emitConst(Object paramObject)
/*      */   {
/*  297 */     if (paramObject == null) {
/*  298 */       this.mv.visitInsn(1);
/*  299 */       return;
/*      */     }
/*  301 */     if ((paramObject instanceof Integer)) {
/*  302 */       emitIconstInsn(((Integer)paramObject).intValue());
/*  303 */       return;
/*      */     }
/*  305 */     if ((paramObject instanceof Long)) {
/*  306 */       long l = ((Long)paramObject).longValue();
/*  307 */       if (l == (short)(int)l) {
/*  308 */         emitIconstInsn((int)l);
/*  309 */         this.mv.visitInsn(133);
/*  310 */         return;
/*      */       }
/*      */     }
/*  313 */     if ((paramObject instanceof Float)) {
/*  314 */       float f = ((Float)paramObject).floatValue();
/*  315 */       if (f == (short)(int)f) {
/*  316 */         emitIconstInsn((int)f);
/*  317 */         this.mv.visitInsn(134);
/*  318 */         return;
/*      */       }
/*      */     }
/*  321 */     if ((paramObject instanceof Double)) {
/*  322 */       double d = ((Double)paramObject).doubleValue();
/*  323 */       if (d == (short)(int)d) {
/*  324 */         emitIconstInsn((int)d);
/*  325 */         this.mv.visitInsn(135);
/*  326 */         return;
/*      */       }
/*      */     }
/*  329 */     if ((paramObject instanceof Boolean)) {
/*  330 */       emitIconstInsn(((Boolean)paramObject).booleanValue() ? 1 : 0);
/*  331 */       return;
/*      */     }
/*      */     
/*  334 */     this.mv.visitLdcInsn(paramObject);
/*      */   }
/*      */   
/*      */   private void emitIconstInsn(int paramInt) {
/*      */     int i;
/*  339 */     switch (paramInt) {
/*  340 */     case 0:  i = 3; break;
/*  341 */     case 1:  i = 4; break;
/*  342 */     case 2:  i = 5; break;
/*  343 */     case 3:  i = 6; break;
/*  344 */     case 4:  i = 7; break;
/*  345 */     case 5:  i = 8; break;
/*      */     default: 
/*  347 */       if (paramInt == (byte)paramInt) {
/*  348 */         this.mv.visitIntInsn(16, paramInt & 0xFF);
/*  349 */       } else if (paramInt == (short)paramInt) {
/*  350 */         this.mv.visitIntInsn(17, (char)paramInt);
/*      */       } else {
/*  352 */         this.mv.visitLdcInsn(Integer.valueOf(paramInt));
/*      */       }
/*  354 */       return;
/*      */     }
/*  356 */     this.mv.visitInsn(i);
/*      */   }
/*      */   
/*      */ 
/*      */   private void emitLoadInsn(char paramChar, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*  364 */     switch (paramChar) {
/*  365 */     case 'I':  i = 21; break;
/*  366 */     case 'J':  i = 22; break;
/*  367 */     case 'F':  i = 23; break;
/*  368 */     case 'D':  i = 24; break;
/*  369 */     case 'L':  i = 25; break;
/*      */     case 'E': case 'G': case 'H': case 'K': default: 
/*  371 */       throw new InternalError("unknown type: " + paramChar);
/*      */     }
/*  373 */     this.mv.visitVarInsn(i, this.localsMap[paramInt]);
/*      */   }
/*      */   
/*  376 */   private void emitAloadInsn(int paramInt) { emitLoadInsn('L', paramInt); }
/*      */   
/*      */   private void emitStoreInsn(char paramChar, int paramInt)
/*      */   {
/*      */     int i;
/*  381 */     switch (paramChar) {
/*  382 */     case 'I':  i = 54; break;
/*  383 */     case 'J':  i = 55; break;
/*  384 */     case 'F':  i = 56; break;
/*  385 */     case 'D':  i = 57; break;
/*  386 */     case 'L':  i = 58; break;
/*      */     case 'E': case 'G': case 'H': case 'K': default: 
/*  388 */       throw new InternalError("unknown type: " + paramChar);
/*      */     }
/*  390 */     this.mv.visitVarInsn(i, this.localsMap[paramInt]);
/*      */   }
/*      */   
/*  393 */   private void emitAstoreInsn(int paramInt) { emitStoreInsn('L', paramInt); }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitBoxing(Class<?> paramClass)
/*      */   {
/*  402 */     Wrapper localWrapper = Wrapper.forPrimitiveType(paramClass);
/*  403 */     String str1 = "java/lang/" + localWrapper.wrapperType().getSimpleName();
/*  404 */     String str2 = "valueOf";
/*  405 */     String str3 = "(" + localWrapper.basicTypeChar() + ")L" + str1 + ";";
/*  406 */     this.mv.visitMethodInsn(184, str1, str2, str3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitUnboxing(Class<?> paramClass)
/*      */   {
/*  415 */     Wrapper localWrapper = Wrapper.forWrapperType(paramClass);
/*  416 */     String str1 = "java/lang/" + localWrapper.wrapperType().getSimpleName();
/*  417 */     String str2 = localWrapper.primitiveSimpleName() + "Value";
/*  418 */     String str3 = "()" + localWrapper.basicTypeChar();
/*  419 */     this.mv.visitTypeInsn(192, str1);
/*  420 */     this.mv.visitMethodInsn(182, str1, str2, str3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitImplicitConversion(char paramChar, Class<?> paramClass)
/*      */   {
/*  430 */     switch (paramChar) {
/*      */     case 'L': 
/*  432 */       if (VerifyType.isNullConversion(Object.class, paramClass))
/*  433 */         return;
/*  434 */       if (isStaticallyNameable(paramClass)) {
/*  435 */         this.mv.visitTypeInsn(192, getInternalName(paramClass));
/*      */       } else {
/*  437 */         this.mv.visitLdcInsn(constantPlaceholder(paramClass));
/*  438 */         this.mv.visitTypeInsn(192, "java/lang/Class");
/*  439 */         this.mv.visitInsn(95);
/*  440 */         this.mv.visitMethodInsn(184, "java/lang/invoke/MethodHandleImpl", "castReference", "(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;");
/*  441 */         if (paramClass.isArray())
/*  442 */           this.mv.visitTypeInsn(192, "[Ljava/lang/Object;");
/*      */       }
/*  444 */       return;
/*      */     case 'I': 
/*  446 */       if (!VerifyType.isNullConversion(Integer.TYPE, paramClass))
/*  447 */         emitPrimCast(paramChar, Wrapper.basicTypeChar(paramClass));
/*  448 */       return;
/*      */     case 'J': 
/*  450 */       assert (paramClass == Long.TYPE);
/*  451 */       return;
/*      */     case 'F': 
/*  453 */       assert (paramClass == Float.TYPE);
/*  454 */       return;
/*      */     case 'D': 
/*  456 */       assert (paramClass == Double.TYPE);
/*  457 */       return;
/*      */     }
/*  459 */     throw new InternalError("bad implicit conversion: tc=" + paramChar + ": " + paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */   private void emitReturnInsn(Class<?> paramClass)
/*      */   {
/*      */     int i;
/*      */     
/*  467 */     switch (Wrapper.basicTypeChar(paramClass)) {
/*  468 */     case 'I':  i = 172; break;
/*  469 */     case 'J':  i = 173; break;
/*  470 */     case 'F':  i = 174; break;
/*  471 */     case 'D':  i = 175; break;
/*  472 */     case 'L':  i = 176; break;
/*  473 */     case 'V':  i = 177; break;
/*      */     case 'E': case 'G': case 'H': case 'K': case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U': default: 
/*  475 */       throw new InternalError("unknown return type: " + paramClass);
/*      */     }
/*  477 */     this.mv.visitInsn(i);
/*      */   }
/*      */   
/*      */   private static String getInternalName(Class<?> paramClass) {
/*  481 */     assert (VerifyAccess.isTypeVisible(paramClass, Object.class));
/*  482 */     return paramClass.getName().replace('.', '/');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static MemberName generateCustomizedCode(LambdaForm paramLambdaForm, MethodType paramMethodType)
/*      */   {
/*  489 */     InvokerBytecodeGenerator localInvokerBytecodeGenerator = new InvokerBytecodeGenerator("MH", paramLambdaForm, paramMethodType);
/*  490 */     return localInvokerBytecodeGenerator.loadMethod(localInvokerBytecodeGenerator.generateCustomizedCodeBytes());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private byte[] generateCustomizedCodeBytes()
/*      */   {
/*  497 */     classFilePrologue();
/*      */     
/*      */ 
/*  500 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/*      */     
/*      */ 
/*  503 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Compiled;", true);
/*      */     
/*      */ 
/*  506 */     this.mv.visitAnnotation("Ljava/lang/invoke/ForceInline;", true);
/*      */     
/*      */ 
/*      */ 
/*  510 */     for (int i = this.lambdaForm.arity; i < this.lambdaForm.names.length; i++) {
/*  511 */       LambdaForm.Name localName = this.lambdaForm.names[i];
/*  512 */       MemberName localMemberName = localName.function.member();
/*      */       
/*  514 */       if (isSelectAlternative(i)) {
/*  515 */         emitSelectAlternative(localName, this.lambdaForm.names[(i + 1)]);
/*  516 */         i++;
/*  517 */       } else if (isGuardWithCatch(i)) {
/*  518 */         emitGuardWithCatch(i);
/*  519 */         i += 2;
/*  520 */       } else if (isStaticallyInvocable(localMemberName)) {
/*  521 */         emitStaticInvoke(localMemberName, localName);
/*      */       } else {
/*  523 */         emitInvoke(localName);
/*      */       }
/*      */       
/*      */ 
/*  527 */       localName = this.lambdaForm.names[i];
/*  528 */       localMemberName = localName.function.member();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  533 */       if ((i != this.lambdaForm.names.length - 1) || (i != this.lambdaForm.result))
/*      */       {
/*  535 */         if (localName.type != 'V')
/*      */         {
/*  537 */           emitStoreInsn(localName.type, localName.index());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  542 */     emitReturn();
/*      */     
/*  544 */     classFileEpilogue();
/*  545 */     bogusMethod(new Object[] { this.lambdaForm });
/*      */     
/*  547 */     byte[] arrayOfByte = this.cw.toByteArray();
/*  548 */     maybeDump(this.className, arrayOfByte);
/*  549 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void emitInvoke(LambdaForm.Name paramName)
/*      */   {
/*  558 */     MethodHandle localMethodHandle = paramName.function.resolvedHandle;
/*  559 */     assert (localMethodHandle != null) : paramName.exprString();
/*  560 */     this.mv.visitLdcInsn(constantPlaceholder(localMethodHandle));
/*  561 */     this.mv.visitTypeInsn(192, "java/lang/invoke/MethodHandle");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  572 */     for (int i = 0; i < paramName.arguments.length; i++) {
/*  573 */       emitPushArgument(paramName, i);
/*      */     }
/*      */     
/*      */ 
/*  577 */     MethodType localMethodType = paramName.function.methodType();
/*  578 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", localMethodType.basicType().toMethodDescriptorString());
/*      */   }
/*      */   
/*  581 */   private static Class<?>[] STATICALLY_INVOCABLE_PACKAGES = { Object.class, Arrays.class, Unsafe.class };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static boolean isStaticallyInvocable(MemberName paramMemberName)
/*      */   {
/*  590 */     if (paramMemberName == null) return false;
/*  591 */     if (paramMemberName.isConstructor()) return false;
/*  592 */     Class localClass1 = paramMemberName.getDeclaringClass();
/*  593 */     if ((localClass1.isArray()) || (localClass1.isPrimitive()))
/*  594 */       return false;
/*  595 */     if ((localClass1.isAnonymousClass()) || (localClass1.isLocalClass()))
/*  596 */       return false;
/*  597 */     if (localClass1.getClassLoader() != MethodHandle.class.getClassLoader())
/*  598 */       return false;
/*  599 */     if (ReflectUtil.isVMAnonymousClass(localClass1))
/*  600 */       return false;
/*  601 */     MethodType localMethodType = paramMemberName.getMethodOrFieldType();
/*  602 */     if (!isStaticallyNameable(localMethodType.returnType()))
/*  603 */       return false;
/*  604 */     for (Class localClass2 : localMethodType.parameterArray())
/*  605 */       if (!isStaticallyNameable(localClass2))
/*  606 */         return false;
/*  607 */     if ((!paramMemberName.isPrivate()) && (VerifyAccess.isSamePackage(MethodHandle.class, localClass1)))
/*  608 */       return true;
/*  609 */     if ((paramMemberName.isPublic()) && (isStaticallyNameable(localClass1)))
/*  610 */       return true;
/*  611 */     return false;
/*      */   }
/*      */   
/*      */   static boolean isStaticallyNameable(Class<?> paramClass) {
/*  615 */     while (paramClass.isArray())
/*  616 */       paramClass = paramClass.getComponentType();
/*  617 */     if (paramClass.isPrimitive())
/*  618 */       return true;
/*  619 */     if (ReflectUtil.isVMAnonymousClass(paramClass)) {
/*  620 */       return false;
/*      */     }
/*  622 */     if (paramClass.getClassLoader() != Object.class.getClassLoader())
/*  623 */       return false;
/*  624 */     if (VerifyAccess.isSamePackage(MethodHandle.class, paramClass))
/*  625 */       return true;
/*  626 */     if (!Modifier.isPublic(paramClass.getModifiers()))
/*  627 */       return false;
/*  628 */     for (Class localClass : STATICALLY_INVOCABLE_PACKAGES) {
/*  629 */       if (VerifyAccess.isSamePackage(localClass, paramClass))
/*  630 */         return true;
/*      */     }
/*  632 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void emitStaticInvoke(MemberName paramMemberName, LambdaForm.Name paramName)
/*      */   {
/*  639 */     assert (paramMemberName.equals(paramName.function.member()));
/*  640 */     String str1 = getInternalName(paramMemberName.getDeclaringClass());
/*  641 */     String str2 = paramMemberName.getName();
/*      */     
/*  643 */     byte b = paramMemberName.getReferenceKind();
/*  644 */     if (b == 7)
/*      */     {
/*  646 */       assert (paramMemberName.canBeStaticallyBound()) : paramMemberName;
/*  647 */       b = 5;
/*      */     }
/*      */     
/*  650 */     if ((paramMemberName.getDeclaringClass().isInterface()) && (b == 5))
/*      */     {
/*      */ 
/*  653 */       b = 9;
/*      */     }
/*      */     
/*      */ 
/*  657 */     for (int i = 0; i < paramName.arguments.length; i++) {
/*  658 */       emitPushArgument(paramName, i);
/*      */     }
/*      */     
/*      */     String str3;
/*  662 */     if (paramMemberName.isMethod()) {
/*  663 */       str3 = paramMemberName.getMethodType().toMethodDescriptorString();
/*  664 */       this.mv.visitMethodInsn(refKindOpcode(b), str1, str2, str3, paramMemberName
/*  665 */         .getDeclaringClass().isInterface());
/*      */     } else {
/*  667 */       str3 = MethodType.toFieldDescriptorString(paramMemberName.getFieldType());
/*  668 */       this.mv.visitFieldInsn(refKindOpcode(b), str1, str2, str3);
/*      */     }
/*      */   }
/*      */   
/*  672 */   int refKindOpcode(byte paramByte) { switch (paramByte) {
/*  673 */     case 5:  return 182;
/*  674 */     case 6:  return 184;
/*  675 */     case 7:  return 183;
/*  676 */     case 9:  return 185;
/*  677 */     case 1:  return 180;
/*  678 */     case 3:  return 181;
/*  679 */     case 2:  return 178;
/*  680 */     case 4:  return 179;
/*      */     }
/*  682 */     throw new InternalError("refKind=" + paramByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean memberRefersTo(MemberName paramMemberName, Class<?> paramClass, String paramString)
/*      */   {
/*  691 */     return (paramMemberName != null) && (paramMemberName.getDeclaringClass() == paramClass) && (paramMemberName.getName().equals(paramString));
/*      */   }
/*      */   
/*      */   private boolean nameRefersTo(LambdaForm.Name paramName, Class<?> paramClass, String paramString) {
/*  695 */     return (paramName.function != null) && (memberRefersTo(paramName.function.member(), paramClass, paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isInvokeBasic(LambdaForm.Name paramName)
/*      */   {
/*  702 */     if (paramName.function == null)
/*  703 */       return false;
/*  704 */     if (paramName.arguments.length < 1)
/*  705 */       return false;
/*  706 */     MemberName localMemberName = paramName.function.member();
/*      */     
/*  708 */     return (memberRefersTo(localMemberName, MethodHandle.class, "invokeBasic")) && (!localMemberName.isPublic()) && (!localMemberName.isStatic());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isSelectAlternative(int paramInt)
/*      */   {
/*  718 */     if (paramInt + 1 >= this.lambdaForm.names.length) return false;
/*  719 */     LambdaForm.Name localName1 = this.lambdaForm.names[paramInt];
/*  720 */     LambdaForm.Name localName2 = this.lambdaForm.names[(paramInt + 1)];
/*      */     
/*      */ 
/*      */ 
/*  724 */     return (nameRefersTo(localName1, MethodHandleImpl.class, "selectAlternative")) && (isInvokeBasic(localName2)) && (localName2.lastUseIndex(localName1) == 0) && (this.lambdaForm.lastUseIndex(localName1) == paramInt + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isGuardWithCatch(int paramInt)
/*      */   {
/*  735 */     if (paramInt + 2 >= this.lambdaForm.names.length) return false;
/*  736 */     LambdaForm.Name localName1 = this.lambdaForm.names[paramInt];
/*  737 */     LambdaForm.Name localName2 = this.lambdaForm.names[(paramInt + 1)];
/*  738 */     LambdaForm.Name localName3 = this.lambdaForm.names[(paramInt + 2)];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  745 */     return (nameRefersTo(localName2, MethodHandleImpl.class, "guardWithCatch")) && (isInvokeBasic(localName1)) && (isInvokeBasic(localName3)) && (localName2.lastUseIndex(localName1) == 3) && (this.lambdaForm.lastUseIndex(localName1) == paramInt + 1) && (localName3.lastUseIndex(localName2) == 1) && (this.lambdaForm.lastUseIndex(localName2) == paramInt + 2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitSelectAlternative(LambdaForm.Name paramName1, LambdaForm.Name paramName2)
/*      */   {
/*  760 */     LambdaForm.Name localName = (LambdaForm.Name)paramName2.arguments[0];
/*      */     
/*  762 */     Label localLabel1 = new Label();
/*  763 */     Label localLabel2 = new Label();
/*      */     
/*      */ 
/*  766 */     emitPushArgument(paramName1, 0);
/*  767 */     this.mv.visitInsn(4);
/*      */     
/*      */ 
/*  770 */     this.mv.visitJumpInsn(160, localLabel1);
/*      */     
/*      */ 
/*  773 */     emitPushArgument(paramName1, 1);
/*  774 */     emitAstoreInsn(localName.index());
/*  775 */     emitInvoke(paramName2);
/*      */     
/*      */ 
/*  778 */     this.mv.visitJumpInsn(167, localLabel2);
/*      */     
/*      */ 
/*  781 */     this.mv.visitLabel(localLabel1);
/*      */     
/*      */ 
/*  784 */     emitPushArgument(paramName1, 2);
/*  785 */     emitAstoreInsn(localName.index());
/*  786 */     emitInvoke(paramName2);
/*      */     
/*      */ 
/*  789 */     this.mv.visitLabel(localLabel2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitGuardWithCatch(int paramInt)
/*      */   {
/*  813 */     LambdaForm.Name localName1 = this.lambdaForm.names[paramInt];
/*  814 */     LambdaForm.Name localName2 = this.lambdaForm.names[(paramInt + 1)];
/*  815 */     LambdaForm.Name localName3 = this.lambdaForm.names[(paramInt + 2)];
/*      */     
/*  817 */     Label localLabel1 = new Label();
/*  818 */     Label localLabel2 = new Label();
/*  819 */     Label localLabel3 = new Label();
/*  820 */     Label localLabel4 = new Label();
/*      */     
/*  822 */     Class localClass = localName3.function.resolvedHandle.type().returnType();
/*      */     
/*      */ 
/*  825 */     MethodType localMethodType1 = localName1.function.resolvedHandle.type().dropParameterTypes(0, 1).changeReturnType(localClass);
/*      */     
/*  827 */     this.mv.visitTryCatchBlock(localLabel1, localLabel2, localLabel3, "java/lang/Throwable");
/*      */     
/*      */ 
/*  830 */     this.mv.visitLabel(localLabel1);
/*      */     
/*  832 */     emitPushArgument(localName2, 0);
/*  833 */     emitPushArguments(localName1, 1);
/*  834 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", localMethodType1.basicType().toMethodDescriptorString(), false);
/*  835 */     this.mv.visitLabel(localLabel2);
/*  836 */     this.mv.visitJumpInsn(167, localLabel4);
/*      */     
/*      */ 
/*  839 */     this.mv.visitLabel(localLabel3);
/*      */     
/*      */ 
/*  842 */     this.mv.visitInsn(89);
/*      */     
/*  844 */     emitPushArgument(localName2, 1);
/*  845 */     this.mv.visitInsn(95);
/*  846 */     this.mv.visitMethodInsn(182, "java/lang/Class", "isInstance", "(Ljava/lang/Object;)Z", false);
/*  847 */     Label localLabel5 = new Label();
/*  848 */     this.mv.visitJumpInsn(153, localLabel5);
/*      */     
/*      */ 
/*      */ 
/*  852 */     emitPushArgument(localName2, 2);
/*  853 */     this.mv.visitInsn(95);
/*  854 */     emitPushArguments(localName1, 1);
/*  855 */     MethodType localMethodType2 = localMethodType1.insertParameterTypes(0, new Class[] { Throwable.class });
/*  856 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", localMethodType2.basicType().toMethodDescriptorString(), false);
/*  857 */     this.mv.visitJumpInsn(167, localLabel4);
/*      */     
/*  859 */     this.mv.visitLabel(localLabel5);
/*  860 */     this.mv.visitInsn(191);
/*      */     
/*  862 */     this.mv.visitLabel(localLabel4);
/*      */   }
/*      */   
/*      */   private void emitPushArguments(LambdaForm.Name paramName, int paramInt) {
/*  866 */     for (int i = paramInt; i < paramName.arguments.length; i++) {
/*  867 */       emitPushArgument(paramName, i);
/*      */     }
/*      */   }
/*      */   
/*      */   private void emitPushArgument(LambdaForm.Name paramName, int paramInt) {
/*  872 */     Object localObject = paramName.arguments[paramInt];
/*  873 */     int i = paramName.function.parameterType(paramInt);
/*  874 */     MethodType localMethodType = paramName.function.methodType();
/*  875 */     if ((localObject instanceof LambdaForm.Name)) {
/*  876 */       LambdaForm.Name localName = (LambdaForm.Name)localObject;
/*  877 */       emitLoadInsn(localName.type, localName.index());
/*  878 */       emitImplicitConversion(localName.type, localMethodType.parameterType(paramInt));
/*  879 */     } else if (((localObject == null) || ((localObject instanceof String))) && (i == 76)) {
/*  880 */       emitConst(localObject);
/*      */     }
/*  882 */     else if ((Wrapper.isWrapperType(localObject.getClass())) && (i != 76)) {
/*  883 */       emitConst(localObject);
/*      */     } else {
/*  885 */       this.mv.visitLdcInsn(constantPlaceholder(localObject));
/*  886 */       emitImplicitConversion('L', localMethodType.parameterType(paramInt));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitReturn()
/*      */   {
/*  896 */     if (this.lambdaForm.result == -1)
/*      */     {
/*  898 */       this.mv.visitInsn(177);
/*      */     } else {
/*  900 */       LambdaForm.Name localName = this.lambdaForm.names[this.lambdaForm.result];
/*  901 */       char c1 = Wrapper.basicTypeChar(this.invokerType.returnType());
/*      */       
/*      */ 
/*  904 */       if (this.lambdaForm.result != this.lambdaForm.names.length - 1) {
/*  905 */         emitLoadInsn(localName.type, this.lambdaForm.result);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  911 */       if (c1 != localName.type)
/*      */       {
/*  913 */         if (c1 == 'L')
/*      */         {
/*  915 */           char c2 = Wrapper.forWrapperType(this.invokerType.returnType()).basicTypeChar();
/*  916 */           if (c2 != localName.type) {
/*  917 */             emitPrimCast(localName.type, c2);
/*      */           }
/*      */           
/*  920 */           emitBoxing(this.invokerType.returnType());
/*      */ 
/*      */         }
/*  923 */         else if (localName.type != 'L')
/*      */         {
/*  925 */           emitPrimCast(localName.type, c1);
/*      */         }
/*      */         else {
/*  928 */           throw new InternalError("no ref-to-prim (unboxing) casts supported right now");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  934 */       emitReturnInsn(this.invokerType.returnType());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void emitPrimCast(char paramChar1, char paramChar2)
/*      */   {
/*  954 */     if (paramChar1 == paramChar2)
/*      */     {
/*  956 */       return;
/*      */     }
/*  958 */     Wrapper localWrapper1 = Wrapper.forBasicType(paramChar1);
/*  959 */     Wrapper localWrapper2 = Wrapper.forBasicType(paramChar2);
/*  960 */     if (localWrapper1.isSubwordOrInt())
/*      */     {
/*  962 */       emitI2X(paramChar2);
/*      */ 
/*      */     }
/*  965 */     else if (localWrapper2.isSubwordOrInt())
/*      */     {
/*  967 */       emitX2I(paramChar1);
/*  968 */       if (localWrapper2.bitWidth() < 32)
/*      */       {
/*  970 */         emitI2X(paramChar2);
/*      */       }
/*      */     }
/*      */     else {
/*  974 */       int i = 0;
/*  975 */       switch (paramChar1) {
/*      */       case 'J': 
/*  977 */         if (paramChar2 == 'F') { this.mv.visitInsn(137);
/*  978 */         } else if (paramChar2 == 'D') this.mv.visitInsn(138); else
/*  979 */           i = 1;
/*  980 */         break;
/*      */       case 'F': 
/*  982 */         if (paramChar2 == 'J') { this.mv.visitInsn(140);
/*  983 */         } else if (paramChar2 == 'D') this.mv.visitInsn(141); else
/*  984 */           i = 1;
/*  985 */         break;
/*      */       case 'D': 
/*  987 */         if (paramChar2 == 'J') { this.mv.visitInsn(143);
/*  988 */         } else if (paramChar2 == 'F') this.mv.visitInsn(144); else
/*  989 */           i = 1;
/*  990 */         break;
/*      */       default: 
/*  992 */         i = 1;
/*      */       }
/*      */       
/*  995 */       if (i != 0) {
/*  996 */         throw new IllegalStateException("unhandled prim cast: " + paramChar1 + "2" + paramChar2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void emitI2X(char paramChar)
/*      */   {
/* 1003 */     switch (paramChar) {
/* 1004 */     case 'B':  this.mv.visitInsn(145); break;
/* 1005 */     case 'S':  this.mv.visitInsn(147); break;
/* 1006 */     case 'C':  this.mv.visitInsn(146); break;
/*      */     case 'I': 
/*      */       break; case 'J':  this.mv.visitInsn(133); break;
/* 1009 */     case 'F':  this.mv.visitInsn(134); break;
/* 1010 */     case 'D':  this.mv.visitInsn(135); break;
/*      */     
/*      */     case 'Z': 
/* 1013 */       this.mv.visitInsn(4);
/* 1014 */       this.mv.visitInsn(126);
/* 1015 */       break;
/* 1016 */     case 'E': case 'G': case 'H': case 'K': case 'L': case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R': case 'T': case 'U': case 'V': case 'W': case 'X': case 'Y': default:  throw new InternalError("unknown type: " + paramChar);
/*      */     }
/*      */   }
/*      */   
/*      */   private void emitX2I(char paramChar) {
/* 1021 */     switch (paramChar) {
/* 1022 */     case 'J':  this.mv.visitInsn(136); break;
/* 1023 */     case 'F':  this.mv.visitInsn(139); break;
/* 1024 */     case 'D':  this.mv.visitInsn(142); break;
/* 1025 */     default:  throw new InternalError("unknown type: " + paramChar);
/*      */     }
/*      */   }
/*      */   
/*      */   private static String basicTypeCharSignature(String paramString, MethodType paramMethodType) {
/* 1030 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 1031 */     for (Class localClass : paramMethodType.parameterList())
/* 1032 */       localStringBuilder.append(Wrapper.forBasicType(localClass).basicTypeChar());
/* 1033 */     localStringBuilder.append('_').append(Wrapper.forBasicType(paramMethodType.returnType()).basicTypeChar());
/* 1034 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static MemberName generateLambdaFormInterpreterEntryPoint(String paramString)
/*      */   {
/* 1041 */     assert (LambdaForm.isValidSignature(paramString));
/*      */     
/*      */ 
/*      */ 
/* 1045 */     char c = LambdaForm.signatureReturn(paramString);
/* 1046 */     MethodType localMethodType = MethodType.methodType(LambdaForm.typeClass(c), MethodHandle.class);
/*      */     
/* 1048 */     int i = LambdaForm.signatureArity(paramString);
/* 1049 */     for (int j = 1; j < i; j++) {
/* 1050 */       localMethodType = localMethodType.appendParameterTypes(new Class[] { LambdaForm.typeClass(paramString.charAt(j)) });
/*      */     }
/* 1052 */     InvokerBytecodeGenerator localInvokerBytecodeGenerator = new InvokerBytecodeGenerator("LFI", "interpret_" + c, localMethodType);
/* 1053 */     return localInvokerBytecodeGenerator.loadMethod(localInvokerBytecodeGenerator.generateLambdaFormInterpreterEntryPointBytes());
/*      */   }
/*      */   
/*      */   private byte[] generateLambdaFormInterpreterEntryPointBytes() {
/* 1057 */     classFilePrologue();
/*      */     
/*      */ 
/* 1060 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/*      */     
/*      */ 
/* 1063 */     this.mv.visitAnnotation("Ljava/lang/invoke/DontInline;", true);
/*      */     
/*      */ 
/* 1066 */     emitIconstInsn(this.invokerType.parameterCount());
/* 1067 */     this.mv.visitTypeInsn(189, "java/lang/Object");
/*      */     
/*      */ 
/* 1070 */     for (int i = 0; i < this.invokerType.parameterCount(); i++) {
/* 1071 */       localObject = this.invokerType.parameterType(i);
/* 1072 */       this.mv.visitInsn(89);
/* 1073 */       emitIconstInsn(i);
/* 1074 */       emitLoadInsn(Wrapper.basicTypeChar((Class)localObject), i);
/*      */       
/* 1076 */       if (((Class)localObject).isPrimitive()) {
/* 1077 */         emitBoxing((Class)localObject);
/*      */       }
/* 1079 */       this.mv.visitInsn(83);
/*      */     }
/*      */     
/* 1082 */     emitAloadInsn(0);
/* 1083 */     this.mv.visitFieldInsn(180, "java/lang/invoke/MethodHandle", "form", "Ljava/lang/invoke/LambdaForm;");
/* 1084 */     this.mv.visitInsn(95);
/* 1085 */     this.mv.visitMethodInsn(182, "java/lang/invoke/LambdaForm", "interpretWithArguments", "([Ljava/lang/Object;)Ljava/lang/Object;");
/*      */     
/*      */ 
/* 1088 */     Class localClass = this.invokerType.returnType();
/* 1089 */     if ((localClass.isPrimitive()) && (localClass != Void.TYPE)) {
/* 1090 */       emitUnboxing(Wrapper.asWrapperType(localClass));
/*      */     }
/*      */     
/*      */ 
/* 1094 */     emitReturnInsn(localClass);
/*      */     
/* 1096 */     classFileEpilogue();
/* 1097 */     bogusMethod(new Object[] { this.invokerType });
/*      */     
/* 1099 */     Object localObject = this.cw.toByteArray();
/* 1100 */     maybeDump(this.className, (byte[])localObject);
/* 1101 */     return (byte[])localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static MemberName generateNamedFunctionInvoker(MethodTypeForm paramMethodTypeForm)
/*      */   {
/* 1108 */     MethodType localMethodType = LambdaForm.NamedFunction.INVOKER_METHOD_TYPE;
/* 1109 */     String str = basicTypeCharSignature("invoke_", paramMethodTypeForm.erasedType());
/* 1110 */     InvokerBytecodeGenerator localInvokerBytecodeGenerator = new InvokerBytecodeGenerator("NFI", str, localMethodType);
/* 1111 */     return localInvokerBytecodeGenerator.loadMethod(localInvokerBytecodeGenerator.generateNamedFunctionInvokerImpl(paramMethodTypeForm));
/*      */   }
/*      */   
/* 1114 */   static int nfi = 0;
/*      */   
/*      */   private byte[] generateNamedFunctionInvokerImpl(MethodTypeForm paramMethodTypeForm) {
/* 1117 */     MethodType localMethodType = paramMethodTypeForm.erasedType();
/* 1118 */     classFilePrologue();
/*      */     
/*      */ 
/* 1121 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/*      */     
/*      */ 
/* 1124 */     this.mv.visitAnnotation("Ljava/lang/invoke/ForceInline;", true);
/*      */     
/*      */ 
/* 1127 */     emitAloadInsn(0);
/*      */     
/*      */     Object localObject2;
/* 1130 */     for (int i = 0; i < localMethodType.parameterCount(); i++) {
/* 1131 */       emitAloadInsn(1);
/* 1132 */       emitIconstInsn(i);
/* 1133 */       this.mv.visitInsn(50);
/*      */       
/*      */ 
/* 1136 */       localClass = localMethodType.parameterType(i);
/* 1137 */       if (localClass.isPrimitive()) {
/* 1138 */         localObject1 = localMethodType.basicType().wrap().parameterType(i);
/* 1139 */         localObject2 = Wrapper.forBasicType(localClass);
/* 1140 */         Object localObject3 = ((Wrapper)localObject2).isSubwordOrInt() ? Wrapper.INT : localObject2;
/* 1141 */         emitUnboxing(((Wrapper)localObject3).wrapperType());
/* 1142 */         emitPrimCast(((Wrapper)localObject3).basicTypeChar(), ((Wrapper)localObject2).basicTypeChar());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1147 */     String str = localMethodType.basicType().toMethodDescriptorString();
/* 1148 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", str);
/*      */     
/*      */ 
/* 1151 */     Class localClass = localMethodType.returnType();
/* 1152 */     if ((localClass != Void.TYPE) && (localClass.isPrimitive())) {
/* 1153 */       localObject1 = Wrapper.forBasicType(localClass);
/* 1154 */       localObject2 = ((Wrapper)localObject1).isSubwordOrInt() ? Wrapper.INT : localObject1;
/*      */       
/* 1156 */       emitPrimCast(((Wrapper)localObject1).basicTypeChar(), ((Wrapper)localObject2).basicTypeChar());
/* 1157 */       emitBoxing(((Wrapper)localObject2).primitiveType());
/*      */     }
/*      */     
/*      */ 
/* 1161 */     if (localClass == Void.TYPE) {
/* 1162 */       this.mv.visitInsn(1);
/*      */     }
/* 1164 */     emitReturnInsn(Object.class);
/*      */     
/* 1166 */     classFileEpilogue();
/* 1167 */     bogusMethod(new Object[] { localMethodType });
/*      */     
/* 1169 */     Object localObject1 = this.cw.toByteArray();
/* 1170 */     maybeDump(this.className, (byte[])localObject1);
/* 1171 */     return (byte[])localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void bogusMethod(Object... paramVarArgs)
/*      */   {
/* 1179 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/* 1180 */       this.mv = this.cw.visitMethod(8, "dummy", "()V", null, null);
/* 1181 */       for (Object localObject : paramVarArgs) {
/* 1182 */         this.mv.visitLdcInsn(localObject.toString());
/* 1183 */         this.mv.visitInsn(87);
/*      */       }
/* 1185 */       this.mv.visitInsn(177);
/* 1186 */       this.mv.visitMaxs(0, 0);
/* 1187 */       this.mv.visitEnd();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/InvokerBytecodeGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */