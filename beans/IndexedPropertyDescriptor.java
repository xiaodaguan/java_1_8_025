/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedPropertyDescriptor
/*     */   extends PropertyDescriptor
/*     */ {
/*     */   private Reference<? extends Class<?>> indexedPropertyTypeRef;
/*  44 */   private final MethodRef indexedReadMethodRef = new MethodRef();
/*  45 */   private final MethodRef indexedWriteMethodRef = new MethodRef();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String indexedReadMethodName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String indexedWriteMethodName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IndexedPropertyDescriptor(String paramString, Class<?> paramClass)
/*     */     throws IntrospectionException
/*     */   {
/*  67 */     this(paramString, paramClass, "get" + 
/*  68 */       NameGenerator.capitalize(paramString), "set" + 
/*  69 */       NameGenerator.capitalize(paramString), "get" + 
/*  70 */       NameGenerator.capitalize(paramString), "set" + 
/*  71 */       NameGenerator.capitalize(paramString));
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
/*     */   public IndexedPropertyDescriptor(String paramString1, Class<?> paramClass, String paramString2, String paramString3, String paramString4, String paramString5)
/*     */     throws IntrospectionException
/*     */   {
/* 100 */     super(paramString1, paramClass, paramString2, paramString3);
/*     */     
/* 102 */     this.indexedReadMethodName = paramString4;
/* 103 */     if ((paramString4 != null) && (getIndexedReadMethod() == null)) {
/* 104 */       throw new IntrospectionException("Method not found: " + paramString4);
/*     */     }
/*     */     
/* 107 */     this.indexedWriteMethodName = paramString5;
/* 108 */     if ((paramString5 != null) && (getIndexedWriteMethod() == null)) {
/* 109 */       throw new IntrospectionException("Method not found: " + paramString5);
/*     */     }
/*     */     
/* 112 */     findIndexedPropertyType(getIndexedReadMethod(), getIndexedWriteMethod());
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
/*     */   public IndexedPropertyDescriptor(String paramString, Method paramMethod1, Method paramMethod2, Method paramMethod3, Method paramMethod4)
/*     */     throws IntrospectionException
/*     */   {
/* 134 */     super(paramString, paramMethod1, paramMethod2);
/*     */     
/* 136 */     setIndexedReadMethod0(paramMethod3);
/* 137 */     setIndexedWriteMethod0(paramMethod4);
/*     */     
/*     */ 
/* 140 */     setIndexedPropertyType(findIndexedPropertyType(paramMethod3, paramMethod4));
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
/*     */   IndexedPropertyDescriptor(Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2, Method paramMethod3, Method paramMethod4)
/*     */     throws IntrospectionException
/*     */   {
/* 158 */     super(paramClass, paramString, paramMethod1, paramMethod2);
/*     */     
/* 160 */     setIndexedReadMethod0(paramMethod3);
/* 161 */     setIndexedWriteMethod0(paramMethod4);
/*     */     
/*     */ 
/* 164 */     setIndexedPropertyType(findIndexedPropertyType(paramMethod3, paramMethod4));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getIndexedReadMethod()
/*     */   {
/* 176 */     Method localMethod = this.indexedReadMethodRef.get();
/* 177 */     if (localMethod == null) {
/* 178 */       Class localClass = getClass0();
/* 179 */       if ((localClass == null) || ((this.indexedReadMethodName == null) && 
/* 180 */         (!this.indexedReadMethodRef.isSet())))
/*     */       {
/* 182 */         return null;
/*     */       }
/* 184 */       String str = "get" + getBaseName();
/* 185 */       if (this.indexedReadMethodName == null) {
/* 186 */         localObject = getIndexedPropertyType0();
/* 187 */         if ((localObject == Boolean.TYPE) || (localObject == null)) {
/* 188 */           this.indexedReadMethodName = ("is" + getBaseName());
/*     */         } else {
/* 190 */           this.indexedReadMethodName = str;
/*     */         }
/*     */       }
/*     */       
/* 194 */       Object localObject = { Integer.TYPE };
/* 195 */       localMethod = Introspector.findMethod(localClass, this.indexedReadMethodName, 1, (Class[])localObject);
/* 196 */       if ((localMethod == null) && (!this.indexedReadMethodName.equals(str)))
/*     */       {
/* 198 */         this.indexedReadMethodName = str;
/* 199 */         localMethod = Introspector.findMethod(localClass, this.indexedReadMethodName, 1, (Class[])localObject);
/*     */       }
/* 201 */       setIndexedReadMethod0(localMethod);
/*     */     }
/* 203 */     return localMethod;
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
/*     */   public synchronized void setIndexedReadMethod(Method paramMethod)
/*     */     throws IntrospectionException
/*     */   {
/* 217 */     setIndexedPropertyType(findIndexedPropertyType(paramMethod, this.indexedWriteMethodRef
/* 218 */       .get()));
/* 219 */     setIndexedReadMethod0(paramMethod);
/*     */   }
/*     */   
/*     */   private void setIndexedReadMethod0(Method paramMethod) {
/* 223 */     this.indexedReadMethodRef.set(paramMethod);
/* 224 */     if (paramMethod == null) {
/* 225 */       this.indexedReadMethodName = null;
/* 226 */       return;
/*     */     }
/* 228 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 230 */     this.indexedReadMethodName = paramMethod.getName();
/* 231 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getIndexedWriteMethod()
/*     */   {
/* 243 */     Method localMethod = this.indexedWriteMethodRef.get();
/* 244 */     if (localMethod == null) {
/* 245 */       Class localClass1 = getClass0();
/* 246 */       if ((localClass1 == null) || ((this.indexedWriteMethodName == null) && 
/* 247 */         (!this.indexedWriteMethodRef.isSet())))
/*     */       {
/* 249 */         return null;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 255 */       Class localClass2 = getIndexedPropertyType0();
/* 256 */       if (localClass2 == null) {
/*     */         try {
/* 258 */           localClass2 = findIndexedPropertyType(getIndexedReadMethod(), null);
/* 259 */           setIndexedPropertyType(localClass2);
/*     */         }
/*     */         catch (IntrospectionException localIntrospectionException) {
/* 262 */           Class localClass3 = getPropertyType();
/* 263 */           if (localClass3.isArray()) {
/* 264 */             localClass2 = localClass3.getComponentType();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 269 */       if (this.indexedWriteMethodName == null) {
/* 270 */         this.indexedWriteMethodName = ("set" + getBaseName());
/*     */       }
/*     */       
/* 273 */       Class[] arrayOfClass = { Integer.TYPE, localClass2 == null ? null : localClass2 };
/* 274 */       localMethod = Introspector.findMethod(localClass1, this.indexedWriteMethodName, 2, arrayOfClass);
/* 275 */       if ((localMethod != null) && 
/* 276 */         (!localMethod.getReturnType().equals(Void.TYPE))) {
/* 277 */         localMethod = null;
/*     */       }
/*     */       
/* 280 */       setIndexedWriteMethod0(localMethod);
/*     */     }
/* 282 */     return localMethod;
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
/*     */   public synchronized void setIndexedWriteMethod(Method paramMethod)
/*     */     throws IntrospectionException
/*     */   {
/* 296 */     Class localClass = findIndexedPropertyType(getIndexedReadMethod(), paramMethod);
/*     */     
/* 298 */     setIndexedPropertyType(localClass);
/* 299 */     setIndexedWriteMethod0(paramMethod);
/*     */   }
/*     */   
/*     */   private void setIndexedWriteMethod0(Method paramMethod) {
/* 303 */     this.indexedWriteMethodRef.set(paramMethod);
/* 304 */     if (paramMethod == null) {
/* 305 */       this.indexedWriteMethodName = null;
/* 306 */       return;
/*     */     }
/* 308 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 310 */     this.indexedWriteMethodName = paramMethod.getName();
/* 311 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
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
/*     */   public synchronized Class<?> getIndexedPropertyType()
/*     */   {
/* 325 */     Class localClass = getIndexedPropertyType0();
/* 326 */     if (localClass == null) {
/*     */       try {
/* 328 */         localClass = findIndexedPropertyType(getIndexedReadMethod(), 
/* 329 */           getIndexedWriteMethod());
/* 330 */         setIndexedPropertyType(localClass);
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException) {}
/*     */     }
/*     */     
/* 335 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */   private void setIndexedPropertyType(Class<?> paramClass)
/*     */   {
/* 341 */     this.indexedPropertyTypeRef = getWeakReference(paramClass);
/*     */   }
/*     */   
/*     */   private Class<?> getIndexedPropertyType0()
/*     */   {
/* 346 */     return this.indexedPropertyTypeRef != null ? (Class)this.indexedPropertyTypeRef.get() : null;
/*     */   }
/*     */   
/*     */ 
/*     */   private Class<?> findIndexedPropertyType(Method paramMethod1, Method paramMethod2)
/*     */     throws IntrospectionException
/*     */   {
/* 353 */     Class localClass = null;
/*     */     
/* 355 */     if (paramMethod1 != null) {
/* 356 */       localObject = getParameterTypes(getClass0(), paramMethod1);
/* 357 */       if (localObject.length != 1) {
/* 358 */         throw new IntrospectionException("bad indexed read method arg count");
/*     */       }
/* 360 */       if (localObject[0] != Integer.TYPE) {
/* 361 */         throw new IntrospectionException("non int index to indexed read method");
/*     */       }
/* 363 */       localClass = getReturnType(getClass0(), paramMethod1);
/* 364 */       if (localClass == Void.TYPE) {
/* 365 */         throw new IntrospectionException("indexed read method returns void");
/*     */       }
/*     */     }
/* 368 */     if (paramMethod2 != null) {
/* 369 */       localObject = getParameterTypes(getClass0(), paramMethod2);
/* 370 */       if (localObject.length != 2) {
/* 371 */         throw new IntrospectionException("bad indexed write method arg count");
/*     */       }
/* 373 */       if (localObject[0] != Integer.TYPE) {
/* 374 */         throw new IntrospectionException("non int index to indexed write method");
/*     */       }
/* 376 */       if ((localClass == null) || (localObject[1].isAssignableFrom(localClass))) {
/* 377 */         localClass = localObject[1];
/* 378 */       } else if (!localClass.isAssignableFrom(localObject[1]))
/*     */       {
/*     */ 
/* 381 */         throw new IntrospectionException("type mismatch between indexed read and indexed write methods: " + getName());
/*     */       }
/*     */     }
/* 384 */     Object localObject = getPropertyType();
/* 385 */     if ((localObject != null) && ((!((Class)localObject).isArray()) || 
/* 386 */       (((Class)localObject).getComponentType() != localClass)))
/*     */     {
/* 388 */       throw new IntrospectionException("type mismatch between indexed and non-indexed methods: " + getName());
/*     */     }
/* 390 */     return localClass;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 404 */     if (this == paramObject) {
/* 405 */       return true;
/*     */     }
/*     */     
/* 408 */     if ((paramObject != null) && ((paramObject instanceof IndexedPropertyDescriptor))) {
/* 409 */       IndexedPropertyDescriptor localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)paramObject;
/* 410 */       Method localMethod1 = localIndexedPropertyDescriptor.getIndexedReadMethod();
/* 411 */       Method localMethod2 = localIndexedPropertyDescriptor.getIndexedWriteMethod();
/*     */       
/* 413 */       if (!compareMethods(getIndexedReadMethod(), localMethod1)) {
/* 414 */         return false;
/*     */       }
/*     */       
/* 417 */       if (!compareMethods(getIndexedWriteMethod(), localMethod2)) {
/* 418 */         return false;
/*     */       }
/*     */       
/* 421 */       if (getIndexedPropertyType() != localIndexedPropertyDescriptor.getIndexedPropertyType()) {
/* 422 */         return false;
/*     */       }
/* 424 */       return super.equals(paramObject);
/*     */     }
/* 426 */     return false;
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
/*     */   IndexedPropertyDescriptor(PropertyDescriptor paramPropertyDescriptor1, PropertyDescriptor paramPropertyDescriptor2)
/*     */   {
/* 439 */     super(paramPropertyDescriptor1, paramPropertyDescriptor2);
/* 440 */     IndexedPropertyDescriptor localIndexedPropertyDescriptor; Method localMethod3; if ((paramPropertyDescriptor1 instanceof IndexedPropertyDescriptor)) {
/* 441 */       localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)paramPropertyDescriptor1;
/*     */       try {
/* 443 */         Method localMethod1 = localIndexedPropertyDescriptor.getIndexedReadMethod();
/* 444 */         if (localMethod1 != null) {
/* 445 */           setIndexedReadMethod(localMethod1);
/*     */         }
/*     */         
/* 448 */         localMethod3 = localIndexedPropertyDescriptor.getIndexedWriteMethod();
/* 449 */         if (localMethod3 != null) {
/* 450 */           setIndexedWriteMethod(localMethod3);
/*     */         }
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException1) {
/* 454 */         throw new AssertionError(localIntrospectionException1);
/*     */       }
/*     */     }
/* 457 */     if ((paramPropertyDescriptor2 instanceof IndexedPropertyDescriptor)) {
/* 458 */       localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)paramPropertyDescriptor2;
/*     */       try {
/* 460 */         Method localMethod2 = localIndexedPropertyDescriptor.getIndexedReadMethod();
/* 461 */         if ((localMethod2 != null) && (localMethod2.getDeclaringClass() == getClass0())) {
/* 462 */           setIndexedReadMethod(localMethod2);
/*     */         }
/*     */         
/* 465 */         localMethod3 = localIndexedPropertyDescriptor.getIndexedWriteMethod();
/* 466 */         if ((localMethod3 != null) && (localMethod3.getDeclaringClass() == getClass0())) {
/* 467 */           setIndexedWriteMethod(localMethod3);
/*     */         }
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException2) {
/* 471 */         throw new AssertionError(localIntrospectionException2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   IndexedPropertyDescriptor(IndexedPropertyDescriptor paramIndexedPropertyDescriptor)
/*     */   {
/* 481 */     super(paramIndexedPropertyDescriptor);
/* 482 */     this.indexedReadMethodRef.set(paramIndexedPropertyDescriptor.indexedReadMethodRef.get());
/* 483 */     this.indexedWriteMethodRef.set(paramIndexedPropertyDescriptor.indexedWriteMethodRef.get());
/* 484 */     this.indexedPropertyTypeRef = paramIndexedPropertyDescriptor.indexedPropertyTypeRef;
/* 485 */     this.indexedWriteMethodName = paramIndexedPropertyDescriptor.indexedWriteMethodName;
/* 486 */     this.indexedReadMethodName = paramIndexedPropertyDescriptor.indexedReadMethodName;
/*     */   }
/*     */   
/*     */   void updateGenericsFor(Class<?> paramClass) {
/* 490 */     super.updateGenericsFor(paramClass);
/*     */     try {
/* 492 */       setIndexedPropertyType(findIndexedPropertyType(this.indexedReadMethodRef.get(), this.indexedWriteMethodRef.get()));
/*     */     }
/*     */     catch (IntrospectionException localIntrospectionException) {
/* 495 */       setIndexedPropertyType(null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 507 */     int i = super.hashCode();
/*     */     
/*     */ 
/* 510 */     i = 37 * i + (this.indexedWriteMethodName == null ? 0 : this.indexedWriteMethodName.hashCode());
/*     */     
/* 512 */     i = 37 * i + (this.indexedReadMethodName == null ? 0 : this.indexedReadMethodName.hashCode());
/*     */     
/* 514 */     i = 37 * i + (getIndexedPropertyType() == null ? 0 : getIndexedPropertyType().hashCode());
/*     */     
/* 516 */     return i;
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 520 */     super.appendTo(paramStringBuilder);
/* 521 */     appendTo(paramStringBuilder, "indexedPropertyType", this.indexedPropertyTypeRef);
/* 522 */     appendTo(paramStringBuilder, "indexedReadMethod", this.indexedReadMethodRef.get());
/* 523 */     appendTo(paramStringBuilder, "indexedWriteMethod", this.indexedWriteMethodRef.get());
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/IndexedPropertyDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */