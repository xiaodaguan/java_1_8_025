/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.ConstructorAccessor;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.ReflectionFactory;
/*     */ import sun.reflect.annotation.TypeAnnotation.TypeAnnotationTarget;
/*     */ import sun.reflect.annotation.TypeAnnotationParser;
/*     */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.repository.ConstructorRepository;
/*     */ import sun.reflect.generics.scope.ConstructorScope;
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
/*     */ public final class Constructor<T>
/*     */   extends Executable
/*     */ {
/*     */   private Class<T> clazz;
/*     */   private int slot;
/*     */   private Class<?>[] parameterTypes;
/*     */   private Class<?>[] exceptionTypes;
/*     */   private int modifiers;
/*     */   private transient String signature;
/*     */   private transient ConstructorRepository genericInfo;
/*     */   private byte[] annotations;
/*     */   private byte[] parameterAnnotations;
/*     */   private volatile ConstructorAccessor constructorAccessor;
/*     */   private Constructor<T> root;
/*     */   
/*     */   private GenericsFactory getFactory()
/*     */   {
/*  77 */     return CoreReflectionFactory.make(this, ConstructorScope.make(this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   ConstructorRepository getGenericInfo()
/*     */   {
/*  84 */     if (this.genericInfo == null)
/*     */     {
/*     */ 
/*  87 */       this.genericInfo = ConstructorRepository.make(getSignature(), 
/*  88 */         getFactory());
/*     */     }
/*  90 */     return this.genericInfo;
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
/*     */   Constructor(Class<T> paramClass, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 112 */     this.clazz = paramClass;
/* 113 */     this.parameterTypes = paramArrayOfClass1;
/* 114 */     this.exceptionTypes = paramArrayOfClass2;
/* 115 */     this.modifiers = paramInt1;
/* 116 */     this.slot = paramInt2;
/* 117 */     this.signature = paramString;
/* 118 */     this.annotations = paramArrayOfByte1;
/* 119 */     this.parameterAnnotations = paramArrayOfByte2;
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
/*     */   Constructor<T> copy()
/*     */   {
/* 135 */     Constructor localConstructor = new Constructor(this.clazz, this.parameterTypes, this.exceptionTypes, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 141 */     localConstructor.root = this;
/*     */     
/* 143 */     localConstructor.constructorAccessor = this.constructorAccessor;
/* 144 */     return localConstructor;
/*     */   }
/*     */   
/*     */   boolean hasGenericInformation()
/*     */   {
/* 149 */     return getSignature() != null;
/*     */   }
/*     */   
/*     */   byte[] getAnnotationBytes()
/*     */   {
/* 154 */     return this.annotations;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<T> getDeclaringClass()
/*     */   {
/* 162 */     return this.clazz;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 171 */     return getDeclaringClass().getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getModifiers()
/*     */   {
/* 179 */     return this.modifiers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeVariable<Constructor<T>>[] getTypeParameters()
/*     */   {
/* 190 */     if (getSignature() != null) {
/* 191 */       return (TypeVariable[])getGenericInfo().getTypeParameters();
/*     */     }
/* 193 */     return (TypeVariable[])new TypeVariable[0];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?>[] getParameterTypes()
/*     */   {
/* 202 */     return (Class[])this.parameterTypes.clone();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getParameterCount()
/*     */   {
/* 208 */     return this.parameterTypes.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type[] getGenericParameterTypes()
/*     */   {
/* 219 */     return super.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?>[] getExceptionTypes()
/*     */   {
/* 227 */     return (Class[])this.exceptionTypes.clone();
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
/*     */   public Type[] getGenericExceptionTypes()
/*     */   {
/* 240 */     return super.getGenericExceptionTypes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 250 */     if ((paramObject != null) && ((paramObject instanceof Constructor))) {
/* 251 */       Constructor localConstructor = (Constructor)paramObject;
/* 252 */       if (getDeclaringClass() == localConstructor.getDeclaringClass()) {
/* 253 */         return equalParamTypes(this.parameterTypes, localConstructor.parameterTypes);
/*     */       }
/*     */     }
/* 256 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 265 */     return getDeclaringClass().getName().hashCode();
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
/*     */   public String toString()
/*     */   {
/* 287 */     return sharedToString(Modifier.constructorModifiers(), false, this.parameterTypes, this.exceptionTypes);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void specificToStringHeader(StringBuilder paramStringBuilder)
/*     */   {
/* 295 */     paramStringBuilder.append(getDeclaringClass().getTypeName());
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
/*     */   public String toGenericString()
/*     */   {
/* 334 */     return sharedToGenericString(Modifier.constructorModifiers(), false);
/*     */   }
/*     */   
/*     */   void specificToGenericStringHeader(StringBuilder paramStringBuilder)
/*     */   {
/* 339 */     specificToStringHeader(paramStringBuilder);
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
/*     */   @CallerSensitive
/*     */   public T newInstance(Object... paramVarArgs)
/*     */     throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
/*     */   {
/* 395 */     if ((!this.override) && 
/* 396 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/* 397 */       localObject1 = Reflection.getCallerClass();
/* 398 */       checkAccess((Class)localObject1, this.clazz, null, this.modifiers);
/*     */     }
/*     */     
/* 401 */     if ((this.clazz.getModifiers() & 0x4000) != 0)
/* 402 */       throw new IllegalArgumentException("Cannot reflectively create enum objects");
/* 403 */     Object localObject1 = this.constructorAccessor;
/* 404 */     if (localObject1 == null) {
/* 405 */       localObject1 = acquireConstructorAccessor();
/*     */     }
/*     */     
/* 408 */     Object localObject2 = ((ConstructorAccessor)localObject1).newInstance(paramVarArgs);
/* 409 */     return (T)localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isVarArgs()
/*     */   {
/* 418 */     return super.isVarArgs();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSynthetic()
/*     */   {
/* 428 */     return super.isSynthetic();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ConstructorAccessor acquireConstructorAccessor()
/*     */   {
/* 439 */     ConstructorAccessor localConstructorAccessor = null;
/* 440 */     if (this.root != null) localConstructorAccessor = this.root.getConstructorAccessor();
/* 441 */     if (localConstructorAccessor != null) {
/* 442 */       this.constructorAccessor = localConstructorAccessor;
/*     */     }
/*     */     else {
/* 445 */       localConstructorAccessor = reflectionFactory.newConstructorAccessor(this);
/* 446 */       setConstructorAccessor(localConstructorAccessor);
/*     */     }
/*     */     
/* 449 */     return localConstructorAccessor;
/*     */   }
/*     */   
/*     */ 
/*     */   ConstructorAccessor getConstructorAccessor()
/*     */   {
/* 455 */     return this.constructorAccessor;
/*     */   }
/*     */   
/*     */ 
/*     */   void setConstructorAccessor(ConstructorAccessor paramConstructorAccessor)
/*     */   {
/* 461 */     this.constructorAccessor = paramConstructorAccessor;
/*     */     
/* 463 */     if (this.root != null) {
/* 464 */       this.root.setConstructorAccessor(paramConstructorAccessor);
/*     */     }
/*     */   }
/*     */   
/*     */   int getSlot() {
/* 469 */     return this.slot;
/*     */   }
/*     */   
/*     */   String getSignature() {
/* 473 */     return this.signature;
/*     */   }
/*     */   
/*     */   byte[] getRawAnnotations() {
/* 477 */     return this.annotations;
/*     */   }
/*     */   
/*     */   byte[] getRawParameterAnnotations() {
/* 481 */     return this.parameterAnnotations;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass)
/*     */   {
/* 491 */     return super.getAnnotation(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Annotation[] getDeclaredAnnotations()
/*     */   {
/* 499 */     return super.getDeclaredAnnotations();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Annotation[][] getParameterAnnotations()
/*     */   {
/* 508 */     return sharedGetParameterAnnotations(this.parameterTypes, this.parameterAnnotations);
/*     */   }
/*     */   
/*     */   void handleParameterNumberMismatch(int paramInt1, int paramInt2)
/*     */   {
/* 513 */     Class localClass = getDeclaringClass();
/* 514 */     if ((localClass.isEnum()) || 
/* 515 */       (localClass.isAnonymousClass()) || 
/* 516 */       (localClass.isLocalClass())) {
/* 517 */       return;
/*     */     }
/* 519 */     if ((!localClass.isMemberClass()) || (
/*     */     
/*     */ 
/* 522 */       (localClass.isMemberClass()) && 
/* 523 */       ((localClass.getModifiers() & 0x8) == 0) && (paramInt1 + 1 != paramInt2)))
/*     */     {
/* 525 */       throw new AnnotationFormatError("Parameter annotations don't match number of parameters");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedType getAnnotatedReturnType()
/*     */   {
/* 537 */     return getAnnotatedReturnType0(getDeclaringClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedType getAnnotatedReceiverType()
/*     */   {
/* 546 */     if (getDeclaringClass().getEnclosingClass() == null) {
/* 547 */       return super.getAnnotatedReceiverType();
/*     */     }
/* 549 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 550 */       SharedSecrets.getJavaLangAccess()
/* 551 */       .getConstantPool(getDeclaringClass()), this, 
/*     */       
/* 553 */       getDeclaringClass(), 
/* 554 */       getDeclaringClass().getEnclosingClass(), TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Constructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */