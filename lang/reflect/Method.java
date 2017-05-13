/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.nio.ByteBuffer;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.MethodAccessor;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.ReflectionFactory;
/*     */ import sun.reflect.annotation.AnnotationParser;
/*     */ import sun.reflect.annotation.AnnotationType;
/*     */ import sun.reflect.annotation.ExceptionProxy;
/*     */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.repository.MethodRepository;
/*     */ import sun.reflect.generics.scope.MethodScope;
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
/*     */ public final class Method
/*     */   extends Executable
/*     */ {
/*     */   private Class<?> clazz;
/*     */   private int slot;
/*     */   private String name;
/*     */   private Class<?> returnType;
/*     */   private Class<?>[] parameterTypes;
/*     */   private Class<?>[] exceptionTypes;
/*     */   private int modifiers;
/*     */   private transient String signature;
/*     */   private transient MethodRepository genericInfo;
/*     */   private byte[] annotations;
/*     */   private byte[] parameterAnnotations;
/*     */   private byte[] annotationDefault;
/*     */   private volatile MethodAccessor methodAccessor;
/*     */   private Method root;
/*     */   
/*     */   private String getGenericSignature()
/*     */   {
/*  85 */     return this.signature;
/*     */   }
/*     */   
/*     */   private GenericsFactory getFactory()
/*     */   {
/*  90 */     return CoreReflectionFactory.make(this, MethodScope.make(this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   MethodRepository getGenericInfo()
/*     */   {
/*  97 */     if (this.genericInfo == null)
/*     */     {
/*  99 */       this.genericInfo = MethodRepository.make(getGenericSignature(), 
/* 100 */         getFactory());
/*     */     }
/* 102 */     return this.genericInfo;
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
/*     */   Method(Class<?> paramClass1, String paramString1, Class<?>[] paramArrayOfClass1, Class<?> paramClass2, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/*     */   {
/* 121 */     this.clazz = paramClass1;
/* 122 */     this.name = paramString1;
/* 123 */     this.parameterTypes = paramArrayOfClass1;
/* 124 */     this.returnType = paramClass2;
/* 125 */     this.exceptionTypes = paramArrayOfClass2;
/* 126 */     this.modifiers = paramInt1;
/* 127 */     this.slot = paramInt2;
/* 128 */     this.signature = paramString2;
/* 129 */     this.annotations = paramArrayOfByte1;
/* 130 */     this.parameterAnnotations = paramArrayOfByte2;
/* 131 */     this.annotationDefault = paramArrayOfByte3;
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
/*     */   Method copy()
/*     */   {
/* 147 */     Method localMethod = new Method(this.clazz, this.name, this.parameterTypes, this.returnType, this.exceptionTypes, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations, this.annotationDefault);
/*     */     
/*     */ 
/* 150 */     localMethod.root = this;
/*     */     
/* 152 */     localMethod.methodAccessor = this.methodAccessor;
/* 153 */     return localMethod;
/*     */   }
/*     */   
/*     */   boolean hasGenericInformation()
/*     */   {
/* 158 */     return getGenericSignature() != null;
/*     */   }
/*     */   
/*     */   byte[] getAnnotationBytes()
/*     */   {
/* 163 */     return this.annotations;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getDeclaringClass()
/*     */   {
/* 171 */     return this.clazz;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 180 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getModifiers()
/*     */   {
/* 188 */     return this.modifiers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeVariable<Method>[] getTypeParameters()
/*     */   {
/* 199 */     if (getGenericSignature() != null) {
/* 200 */       return (TypeVariable[])getGenericInfo().getTypeParameters();
/*     */     }
/* 202 */     return (TypeVariable[])new TypeVariable[0];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getReturnType()
/*     */   {
/* 212 */     return this.returnType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getGenericReturnType()
/*     */   {
/* 240 */     if (getGenericSignature() != null)
/* 241 */       return getGenericInfo().getReturnType();
/* 242 */     return getReturnType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?>[] getParameterTypes()
/*     */   {
/* 250 */     return (Class[])this.parameterTypes.clone();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getParameterCount()
/*     */   {
/* 256 */     return this.parameterTypes.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type[] getGenericParameterTypes()
/*     */   {
/* 268 */     return super.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?>[] getExceptionTypes()
/*     */   {
/* 276 */     return (Class[])this.exceptionTypes.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type[] getGenericExceptionTypes()
/*     */   {
/* 288 */     return super.getGenericExceptionTypes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 298 */     if ((paramObject != null) && ((paramObject instanceof Method))) {
/* 299 */       Method localMethod = (Method)paramObject;
/* 300 */       if ((getDeclaringClass() == localMethod.getDeclaringClass()) && 
/* 301 */         (getName() == localMethod.getName())) {
/* 302 */         if (!this.returnType.equals(localMethod.getReturnType()))
/* 303 */           return false;
/* 304 */         return equalParamTypes(this.parameterTypes, localMethod.parameterTypes);
/*     */       }
/*     */     }
/* 307 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 316 */     return getDeclaringClass().getName().hashCode() ^ getName().hashCode();
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
/*     */   public String toString()
/*     */   {
/* 346 */     return sharedToString(Modifier.methodModifiers(), 
/* 347 */       isDefault(), this.parameterTypes, this.exceptionTypes);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void specificToStringHeader(StringBuilder paramStringBuilder)
/*     */   {
/* 354 */     paramStringBuilder.append(getReturnType().getTypeName()).append(' ');
/* 355 */     paramStringBuilder.append(getDeclaringClass().getTypeName()).append('.');
/* 356 */     paramStringBuilder.append(getName());
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
/*     */   public String toGenericString()
/*     */   {
/* 400 */     return sharedToGenericString(Modifier.methodModifiers(), isDefault());
/*     */   }
/*     */   
/*     */   void specificToGenericStringHeader(StringBuilder paramStringBuilder)
/*     */   {
/* 405 */     Type localType = getGenericReturnType();
/* 406 */     paramStringBuilder.append(localType.getTypeName()).append(' ');
/* 407 */     paramStringBuilder.append(getDeclaringClass().getTypeName()).append('.');
/* 408 */     paramStringBuilder.append(getName());
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
/*     */   @CallerSensitive
/*     */   public Object invoke(Object paramObject, Object... paramVarArgs)
/*     */     throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
/*     */   {
/* 473 */     if ((!this.override) && 
/* 474 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/* 475 */       localObject = Reflection.getCallerClass();
/* 476 */       checkAccess((Class)localObject, this.clazz, paramObject, this.modifiers);
/*     */     }
/*     */     
/* 479 */     Object localObject = this.methodAccessor;
/* 480 */     if (localObject == null) {
/* 481 */       localObject = acquireMethodAccessor();
/*     */     }
/* 483 */     return ((MethodAccessor)localObject).invoke(paramObject, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBridge()
/*     */   {
/* 495 */     return (getModifiers() & 0x40) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isVarArgs()
/*     */   {
/* 504 */     return super.isVarArgs();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSynthetic()
/*     */   {
/* 514 */     return super.isSynthetic();
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
/*     */   public boolean isDefault()
/*     */   {
/* 533 */     return ((getModifiers() & 0x409) == 1) && (getDeclaringClass().isInterface());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private MethodAccessor acquireMethodAccessor()
/*     */   {
/* 543 */     MethodAccessor localMethodAccessor = null;
/* 544 */     if (this.root != null) localMethodAccessor = this.root.getMethodAccessor();
/* 545 */     if (localMethodAccessor != null) {
/* 546 */       this.methodAccessor = localMethodAccessor;
/*     */     }
/*     */     else {
/* 549 */       localMethodAccessor = reflectionFactory.newMethodAccessor(this);
/* 550 */       setMethodAccessor(localMethodAccessor);
/*     */     }
/*     */     
/* 553 */     return localMethodAccessor;
/*     */   }
/*     */   
/*     */ 
/*     */   MethodAccessor getMethodAccessor()
/*     */   {
/* 559 */     return this.methodAccessor;
/*     */   }
/*     */   
/*     */ 
/*     */   void setMethodAccessor(MethodAccessor paramMethodAccessor)
/*     */   {
/* 565 */     this.methodAccessor = paramMethodAccessor;
/*     */     
/* 567 */     if (this.root != null) {
/* 568 */       this.root.setMethodAccessor(paramMethodAccessor);
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
/*     */   public Object getDefaultValue()
/*     */   {
/* 587 */     if (this.annotationDefault == null)
/* 588 */       return null;
/* 589 */     Class localClass = AnnotationType.invocationHandlerReturnType(
/* 590 */       getReturnType());
/* 591 */     Object localObject = AnnotationParser.parseMemberValue(localClass, 
/* 592 */       ByteBuffer.wrap(this.annotationDefault), 
/* 593 */       SharedSecrets.getJavaLangAccess()
/* 594 */       .getConstantPool(getDeclaringClass()), 
/* 595 */       getDeclaringClass());
/* 596 */     if ((localObject instanceof ExceptionProxy))
/* 597 */       throw new AnnotationFormatError("Invalid default: " + this);
/* 598 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass)
/*     */   {
/* 607 */     return super.getAnnotation(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Annotation[] getDeclaredAnnotations()
/*     */   {
/* 615 */     return super.getDeclaredAnnotations();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Annotation[][] getParameterAnnotations()
/*     */   {
/* 624 */     return sharedGetParameterAnnotations(this.parameterTypes, this.parameterAnnotations);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedType getAnnotatedReturnType()
/*     */   {
/* 633 */     return getAnnotatedReturnType0(getGenericReturnType());
/*     */   }
/*     */   
/*     */   void handleParameterNumberMismatch(int paramInt1, int paramInt2)
/*     */   {
/* 638 */     throw new AnnotationFormatError("Parameter annotations don't match number of parameters");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Method.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */