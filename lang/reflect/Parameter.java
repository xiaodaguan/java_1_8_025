/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import sun.reflect.annotation.AnnotationSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Parameter
/*     */   implements AnnotatedElement
/*     */ {
/*     */   private final String name;
/*     */   private final int modifiers;
/*     */   private final Executable executable;
/*     */   private final int index;
/*     */   
/*     */   Parameter(String paramString, int paramInt1, Executable paramExecutable, int paramInt2)
/*     */   {
/*  66 */     this.name = paramString;
/*  67 */     this.modifiers = paramInt1;
/*  68 */     this.executable = paramExecutable;
/*  69 */     this.index = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  79 */     if ((paramObject instanceof Parameter)) {
/*  80 */       Parameter localParameter = (Parameter)paramObject;
/*  81 */       return (localParameter.executable.equals(this.executable)) && (localParameter.index == this.index);
/*     */     }
/*     */     
/*  84 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  94 */     return this.executable.hashCode() ^ this.index;
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
/*     */   public boolean isNamePresent()
/*     */   {
/* 107 */     return (this.executable.hasRealParameterData()) && (this.name != null);
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
/*     */   public String toString()
/*     */   {
/* 124 */     StringBuilder localStringBuilder = new StringBuilder();
/* 125 */     Type localType = getParameterizedType();
/* 126 */     String str = localType.getTypeName();
/*     */     
/* 128 */     localStringBuilder.append(Modifier.toString(getModifiers()));
/*     */     
/* 130 */     if (0 != this.modifiers) {
/* 131 */       localStringBuilder.append(' ');
/*     */     }
/* 133 */     if (isVarArgs()) {
/* 134 */       localStringBuilder.append(str.replaceFirst("\\[\\]$", "..."));
/*     */     } else {
/* 136 */       localStringBuilder.append(str);
/*     */     }
/* 138 */     localStringBuilder.append(' ');
/* 139 */     localStringBuilder.append(getName());
/*     */     
/* 141 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Executable getDeclaringExecutable()
/*     */   {
/* 150 */     return this.executable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getModifiers()
/*     */   {
/* 160 */     return this.modifiers;
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
/*     */   public String getName()
/*     */   {
/* 179 */     if ((this.name == null) || (this.name.equals(""))) {
/* 180 */       return "arg" + this.index;
/*     */     }
/* 182 */     return this.name;
/*     */   }
/*     */   
/*     */   String getRealName()
/*     */   {
/* 187 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getParameterizedType()
/*     */   {
/* 199 */     Type localType = this.parameterTypeCache;
/* 200 */     if (null == localType) {
/* 201 */       localType = this.executable.getGenericParameterTypes()[this.index];
/* 202 */       this.parameterTypeCache = localType;
/*     */     }
/*     */     
/* 205 */     return localType;
/*     */   }
/*     */   
/* 208 */   private volatile transient Type parameterTypeCache = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getType()
/*     */   {
/* 219 */     Class localClass = this.parameterClassCache;
/* 220 */     if (null == localClass) {
/* 221 */       localClass = this.executable.getParameterTypes()[this.index];
/* 222 */       this.parameterClassCache = localClass;
/*     */     }
/* 224 */     return localClass;
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
/*     */   public AnnotatedType getAnnotatedType()
/*     */   {
/* 237 */     return this.executable.getAnnotatedParameterTypes()[this.index];
/*     */   }
/*     */   
/* 240 */   private volatile transient Class<?> parameterClassCache = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isImplicit()
/*     */   {
/* 251 */     return Modifier.isMandated(getModifiers());
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
/*     */   public boolean isSynthetic()
/*     */   {
/* 265 */     return Modifier.isSynthetic(getModifiers());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isVarArgs()
/*     */   {
/* 277 */     return (this.executable.isVarArgs()) && (this.index == this.executable.getParameterCount() - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass)
/*     */   {
/* 286 */     Objects.requireNonNull(paramClass);
/* 287 */     return (Annotation)paramClass.cast(declaredAnnotations().get(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass)
/*     */   {
/* 296 */     Objects.requireNonNull(paramClass);
/*     */     
/* 298 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getDeclaredAnnotations()
/*     */   {
/* 305 */     return this.executable.getParameterAnnotations()[this.index];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T getDeclaredAnnotation(Class<T> paramClass)
/*     */   {
/* 315 */     return getAnnotation(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> paramClass)
/*     */   {
/* 326 */     return getAnnotationsByType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getAnnotations()
/*     */   {
/* 333 */     return getDeclaredAnnotations();
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations()
/*     */   {
/* 339 */     if (null == this.declaredAnnotations) {
/* 340 */       this.declaredAnnotations = new HashMap();
/*     */       
/* 342 */       Annotation[] arrayOfAnnotation = getDeclaredAnnotations();
/* 343 */       for (int i = 0; i < arrayOfAnnotation.length; i++)
/* 344 */         this.declaredAnnotations.put(arrayOfAnnotation[i].annotationType(), arrayOfAnnotation[i]);
/*     */     }
/* 346 */     return this.declaredAnnotations;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Parameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */