/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.annotation.AnnotationParser;
/*     */ import sun.reflect.annotation.AnnotationSupport;
/*     */ import sun.reflect.annotation.TypeAnnotation.TypeAnnotationTarget;
/*     */ import sun.reflect.annotation.TypeAnnotationParser;
/*     */ import sun.reflect.generics.repository.ConstructorRepository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Executable
/*     */   extends AccessibleObject
/*     */   implements Member, GenericDeclaration
/*     */ {
/*     */   private volatile transient boolean hasRealParameterData;
/*     */   private volatile transient Parameter[] parameters;
/*     */   private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*     */   
/*     */   abstract byte[] getAnnotationBytes();
/*     */   
/*     */   abstract boolean hasGenericInformation();
/*     */   
/*     */   abstract ConstructorRepository getGenericInfo();
/*     */   
/*     */   boolean equalParamTypes(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2)
/*     */   {
/*  64 */     if (paramArrayOfClass1.length == paramArrayOfClass2.length) {
/*  65 */       for (int i = 0; i < paramArrayOfClass1.length; i++) {
/*  66 */         if (paramArrayOfClass1[i] != paramArrayOfClass2[i])
/*  67 */           return false;
/*     */       }
/*  69 */       return true;
/*     */     }
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   Annotation[][] parseParameterAnnotations(byte[] paramArrayOfByte) {
/*  75 */     return AnnotationParser.parseParameterAnnotations(paramArrayOfByte, 
/*     */     
/*  77 */       SharedSecrets.getJavaLangAccess()
/*  78 */       .getConstantPool(getDeclaringClass()), 
/*  79 */       getDeclaringClass());
/*     */   }
/*     */   
/*     */   void separateWithCommas(Class<?>[] paramArrayOfClass, StringBuilder paramStringBuilder) {
/*  83 */     for (int i = 0; i < paramArrayOfClass.length; i++) {
/*  84 */       paramStringBuilder.append(paramArrayOfClass[i].getTypeName());
/*  85 */       if (i < paramArrayOfClass.length - 1) {
/*  86 */         paramStringBuilder.append(",");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void printModifiersIfNonzero(StringBuilder paramStringBuilder, int paramInt, boolean paramBoolean) {
/*  92 */     int i = getModifiers() & paramInt;
/*     */     
/*  94 */     if ((i != 0) && (!paramBoolean)) {
/*  95 */       paramStringBuilder.append(Modifier.toString(i)).append(' ');
/*     */     } else {
/*  97 */       int j = i & 0x7;
/*  98 */       if (j != 0)
/*  99 */         paramStringBuilder.append(Modifier.toString(j)).append(' ');
/* 100 */       if (paramBoolean)
/* 101 */         paramStringBuilder.append("default ");
/* 102 */       i &= 0xFFFFFFF8;
/* 103 */       if (i != 0) {
/* 104 */         paramStringBuilder.append(Modifier.toString(i)).append(' ');
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String sharedToString(int paramInt, boolean paramBoolean, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2)
/*     */   {
/*     */     try
/*     */     {
/* 113 */       StringBuilder localStringBuilder = new StringBuilder();
/*     */       
/* 115 */       printModifiersIfNonzero(localStringBuilder, paramInt, paramBoolean);
/* 116 */       specificToStringHeader(localStringBuilder);
/*     */       
/* 118 */       localStringBuilder.append('(');
/* 119 */       separateWithCommas(paramArrayOfClass1, localStringBuilder);
/* 120 */       localStringBuilder.append(')');
/* 121 */       if (paramArrayOfClass2.length > 0) {
/* 122 */         localStringBuilder.append(" throws ");
/* 123 */         separateWithCommas(paramArrayOfClass2, localStringBuilder);
/*     */       }
/* 125 */       return localStringBuilder.toString();
/*     */     } catch (Exception localException) {
/* 127 */       return "<" + localException + ">";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   abstract void specificToStringHeader(StringBuilder paramStringBuilder);
/*     */   
/*     */ 
/*     */   String sharedToGenericString(int paramInt, boolean paramBoolean)
/*     */   {
/*     */     try
/*     */     {
/* 139 */       StringBuilder localStringBuilder = new StringBuilder();
/*     */       
/* 141 */       printModifiersIfNonzero(localStringBuilder, paramInt, paramBoolean);
/*     */       
/* 143 */       TypeVariable[] arrayOfTypeVariable1 = getTypeParameters();
/* 144 */       if (arrayOfTypeVariable1.length > 0) {
/* 145 */         int i = 1;
/* 146 */         localStringBuilder.append('<');
/* 147 */         for (TypeVariable localTypeVariable : arrayOfTypeVariable1) {
/* 148 */           if (i == 0) {
/* 149 */             localStringBuilder.append(',');
/*     */           }
/*     */           
/* 152 */           localStringBuilder.append(localTypeVariable.toString());
/* 153 */           i = 0;
/*     */         }
/* 155 */         localStringBuilder.append("> ");
/*     */       }
/*     */       
/* 158 */       specificToGenericStringHeader(localStringBuilder);
/*     */       
/* 160 */       localStringBuilder.append('(');
/* 161 */       Type[] arrayOfType1 = getGenericParameterTypes();
/* 162 */       for (int j = 0; j < arrayOfType1.length; j++) {
/* 163 */         String str = arrayOfType1[j].getTypeName();
/* 164 */         if ((isVarArgs()) && (j == arrayOfType1.length - 1))
/* 165 */           str = str.replaceFirst("\\[\\]$", "...");
/* 166 */         localStringBuilder.append(str);
/* 167 */         if (j < arrayOfType1.length - 1)
/* 168 */           localStringBuilder.append(',');
/*     */       }
/* 170 */       localStringBuilder.append(')');
/* 171 */       Type[] arrayOfType2 = getGenericExceptionTypes();
/* 172 */       if (arrayOfType2.length > 0) {
/* 173 */         localStringBuilder.append(" throws ");
/* 174 */         for (int m = 0; m < arrayOfType2.length; m++) {
/* 175 */           localStringBuilder.append((arrayOfType2[m] instanceof Class) ? ((Class)arrayOfType2[m])
/* 176 */             .getName() : arrayOfType2[m]
/* 177 */             .toString());
/* 178 */           if (m < arrayOfType2.length - 1)
/* 179 */             localStringBuilder.append(',');
/*     */         }
/*     */       }
/* 182 */       return localStringBuilder.toString();
/*     */     } catch (Exception localException) {
/* 184 */       return "<" + localException + ">";
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
/*     */   abstract void specificToGenericStringHeader(StringBuilder paramStringBuilder);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Class<?> getDeclaringClass();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getName();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int getModifiers();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract TypeVariable<?>[] getTypeParameters();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Class<?>[] getParameterTypes();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getParameterCount()
/*     */   {
/* 248 */     throw new AbstractMethodError();
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
/*     */   public Type[] getGenericParameterTypes()
/*     */   {
/* 278 */     if (hasGenericInformation()) {
/* 279 */       return getGenericInfo().getParameterTypes();
/*     */     }
/* 281 */     return getParameterTypes();
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
/*     */   public Parameter[] getParameters()
/*     */   {
/* 307 */     return (Parameter[])privateGetParameters().clone();
/*     */   }
/*     */   
/*     */   private Parameter[] synthesizeAllParams() {
/* 311 */     int i = getParameterCount();
/* 312 */     Parameter[] arrayOfParameter = new Parameter[i];
/* 313 */     for (int j = 0; j < i; j++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 318 */       arrayOfParameter[j] = new Parameter("arg" + j, 0, this, j); }
/* 319 */     return arrayOfParameter;
/*     */   }
/*     */   
/*     */ 
/*     */   private void verifyParameters(Parameter[] paramArrayOfParameter)
/*     */   {
/* 325 */     if (getParameterTypes().length != paramArrayOfParameter.length) {
/* 326 */       throw new MalformedParametersException("Wrong number of parameters in MethodParameters attribute");
/*     */     }
/* 328 */     for (Parameter localParameter : paramArrayOfParameter) {
/* 329 */       String str = localParameter.getRealName();
/* 330 */       int k = localParameter.getModifiers();
/*     */       
/* 332 */       if ((str != null) && (
/* 333 */         (str.isEmpty()) || (str.indexOf('.') != -1) || 
/* 334 */         (str.indexOf(';') != -1) || (str.indexOf('[') != -1) || 
/* 335 */         (str.indexOf('/') != -1))) {
/* 336 */         throw new MalformedParametersException("Invalid parameter name \"" + str + "\"");
/*     */       }
/*     */       
/*     */ 
/* 340 */       if (k != (k & 0x9010)) {
/* 341 */         throw new MalformedParametersException("Invalid parameter modifiers");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Parameter[] privateGetParameters()
/*     */   {
/* 348 */     Parameter[] arrayOfParameter = this.parameters;
/*     */     
/* 350 */     if (arrayOfParameter == null)
/*     */     {
/*     */       try
/*     */       {
/* 354 */         arrayOfParameter = getParameters0();
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {
/* 357 */         throw new MalformedParametersException("Invalid constant pool index");
/*     */       }
/*     */       
/*     */ 
/* 361 */       if (arrayOfParameter == null) {
/* 362 */         this.hasRealParameterData = false;
/* 363 */         arrayOfParameter = synthesizeAllParams();
/*     */       } else {
/* 365 */         this.hasRealParameterData = true;
/* 366 */         verifyParameters(arrayOfParameter);
/*     */       }
/*     */       
/* 369 */       this.parameters = arrayOfParameter;
/*     */     }
/*     */     
/* 372 */     return arrayOfParameter;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean hasRealParameterData()
/*     */   {
/* 378 */     if (this.parameters == null) {
/* 379 */       privateGetParameters();
/*     */     }
/* 381 */     return this.hasRealParameterData;
/*     */   }
/*     */   
/*     */ 
/*     */   private native Parameter[] getParameters0();
/*     */   
/*     */ 
/*     */   native byte[] getTypeAnnotationBytes0();
/*     */   
/*     */   byte[] getTypeAnnotationBytes()
/*     */   {
/* 392 */     return getTypeAnnotationBytes0();
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
/*     */   public abstract Class<?>[] getExceptionTypes();
/*     */   
/*     */ 
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
/*     */     Type[] arrayOfType;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 430 */     if ((hasGenericInformation()) && 
/* 431 */       ((arrayOfType = getGenericInfo().getExceptionTypes()).length > 0)) {
/* 432 */       return arrayOfType;
/*     */     }
/* 434 */     return getExceptionTypes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String toGenericString();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isVarArgs()
/*     */   {
/* 453 */     return (getModifiers() & 0x80) != 0;
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
/*     */   public boolean isSynthetic()
/*     */   {
/* 466 */     return Modifier.isSynthetic(getModifiers());
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
/*     */   public abstract Annotation[][] getParameterAnnotations();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Annotation[][] sharedGetParameterAnnotations(Class<?>[] paramArrayOfClass, byte[] paramArrayOfByte)
/*     */   {
/* 501 */     int i = paramArrayOfClass.length;
/* 502 */     if (paramArrayOfByte == null) {
/* 503 */       return new Annotation[i][0];
/*     */     }
/* 505 */     Annotation[][] arrayOfAnnotation = parseParameterAnnotations(paramArrayOfByte);
/*     */     
/* 507 */     if (arrayOfAnnotation.length != i)
/* 508 */       handleParameterNumberMismatch(arrayOfAnnotation.length, i);
/* 509 */     return arrayOfAnnotation;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   abstract void handleParameterNumberMismatch(int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass)
/*     */   {
/* 519 */     Objects.requireNonNull(paramClass);
/* 520 */     return (Annotation)paramClass.cast(declaredAnnotations().get(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass)
/*     */   {
/* 530 */     Objects.requireNonNull(paramClass);
/*     */     
/* 532 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getDeclaredAnnotations()
/*     */   {
/* 539 */     return AnnotationParser.toArray(declaredAnnotations());
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations()
/*     */   {
/* 545 */     if (this.declaredAnnotations == null) {
/* 546 */       this.declaredAnnotations = AnnotationParser.parseAnnotations(
/* 547 */         getAnnotationBytes(), 
/* 548 */         SharedSecrets.getJavaLangAccess()
/* 549 */         .getConstantPool(getDeclaringClass()), 
/* 550 */         getDeclaringClass());
/*     */     }
/* 552 */     return this.declaredAnnotations;
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
/*     */   public abstract AnnotatedType getAnnotatedReturnType();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   AnnotatedType getAnnotatedReturnType0(Type paramType)
/*     */   {
/* 583 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 584 */       SharedSecrets.getJavaLangAccess()
/* 585 */       .getConstantPool(getDeclaringClass()), this, 
/*     */       
/* 587 */       getDeclaringClass(), paramType, TypeAnnotation.TypeAnnotationTarget.METHOD_RETURN);
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
/*     */   public AnnotatedType getAnnotatedReceiverType()
/*     */   {
/* 614 */     if (Modifier.isStatic(getModifiers()))
/* 615 */       return null;
/* 616 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 617 */       SharedSecrets.getJavaLangAccess()
/* 618 */       .getConstantPool(getDeclaringClass()), this, 
/*     */       
/* 620 */       getDeclaringClass(), 
/* 621 */       getDeclaringClass(), TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
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
/*     */   public AnnotatedType[] getAnnotatedParameterTypes()
/*     */   {
/* 642 */     return TypeAnnotationParser.buildAnnotatedTypes(getTypeAnnotationBytes0(), 
/* 643 */       SharedSecrets.getJavaLangAccess()
/* 644 */       .getConstantPool(getDeclaringClass()), this, 
/*     */       
/* 646 */       getDeclaringClass(), 
/* 647 */       getGenericParameterTypes(), TypeAnnotation.TypeAnnotationTarget.METHOD_FORMAL_PARAMETER);
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
/*     */   public AnnotatedType[] getAnnotatedExceptionTypes()
/*     */   {
/* 668 */     return TypeAnnotationParser.buildAnnotatedTypes(getTypeAnnotationBytes0(), 
/* 669 */       SharedSecrets.getJavaLangAccess()
/* 670 */       .getConstantPool(getDeclaringClass()), this, 
/*     */       
/* 672 */       getDeclaringClass(), 
/* 673 */       getGenericExceptionTypes(), TypeAnnotation.TypeAnnotationTarget.THROWS);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Executable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */