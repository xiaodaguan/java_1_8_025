/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.reflect.Constructor;
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
/*     */ public class PropertyDescriptor
/*     */   extends FeatureDescriptor
/*     */ {
/*     */   private Reference<? extends Class<?>> propertyTypeRef;
/*  39 */   private final MethodRef readMethodRef = new MethodRef();
/*  40 */   private final MethodRef writeMethodRef = new MethodRef();
/*     */   
/*     */ 
/*     */ 
/*     */   private Reference<? extends Class<?>> propertyEditorClassRef;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean bound;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean constrained;
/*     */   
/*     */ 
/*     */ 
/*     */   private String baseName;
/*     */   
/*     */ 
/*     */ 
/*     */   private String writeMethodName;
/*     */   
/*     */ 
/*     */   private String readMethodName;
/*     */   
/*     */ 
/*     */ 
/*     */   public PropertyDescriptor(String paramString, Class<?> paramClass)
/*     */     throws IntrospectionException
/*     */   {
/*  70 */     this(paramString, paramClass, "is" + 
/*  71 */       NameGenerator.capitalize(paramString), "set" + 
/*  72 */       NameGenerator.capitalize(paramString));
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
/*     */   public PropertyDescriptor(String paramString1, Class<?> paramClass, String paramString2, String paramString3)
/*     */     throws IntrospectionException
/*     */   {
/*  92 */     if (paramClass == null) {
/*  93 */       throw new IntrospectionException("Target Bean class is null");
/*     */     }
/*  95 */     if ((paramString1 == null) || (paramString1.length() == 0)) {
/*  96 */       throw new IntrospectionException("bad property name");
/*     */     }
/*  98 */     if (("".equals(paramString2)) || ("".equals(paramString3))) {
/*  99 */       throw new IntrospectionException("read or write method name should not be the empty string");
/*     */     }
/* 101 */     setName(paramString1);
/* 102 */     setClass0(paramClass);
/*     */     
/* 104 */     this.readMethodName = paramString2;
/* 105 */     if ((paramString2 != null) && (getReadMethod() == null)) {
/* 106 */       throw new IntrospectionException("Method not found: " + paramString2);
/*     */     }
/* 108 */     this.writeMethodName = paramString3;
/* 109 */     if ((paramString3 != null) && (getWriteMethod() == null)) {
/* 110 */       throw new IntrospectionException("Method not found: " + paramString3);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 115 */     Class[] arrayOfClass = { PropertyChangeListener.class };
/* 116 */     this.bound = (null != Introspector.findMethod(paramClass, "addPropertyChangeListener", arrayOfClass.length, arrayOfClass));
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
/*     */   public PropertyDescriptor(String paramString, Method paramMethod1, Method paramMethod2)
/*     */     throws IntrospectionException
/*     */   {
/* 133 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 134 */       throw new IntrospectionException("bad property name");
/*     */     }
/* 136 */     setName(paramString);
/* 137 */     setReadMethod(paramMethod1);
/* 138 */     setWriteMethod(paramMethod2);
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
/*     */   PropertyDescriptor(Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2)
/*     */     throws IntrospectionException
/*     */   {
/* 154 */     if (paramClass == null) {
/* 155 */       throw new IntrospectionException("Target Bean class is null");
/*     */     }
/* 157 */     setClass0(paramClass);
/* 158 */     setName(Introspector.decapitalize(paramString));
/* 159 */     setReadMethod(paramMethod1);
/* 160 */     setWriteMethod(paramMethod2);
/* 161 */     this.baseName = paramString;
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
/*     */   public synchronized Class<?> getPropertyType()
/*     */   {
/* 177 */     Class localClass = getPropertyType0();
/* 178 */     if (localClass == null) {
/*     */       try {
/* 180 */         localClass = findPropertyType(getReadMethod(), getWriteMethod());
/* 181 */         setPropertyType(localClass);
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException) {}
/*     */     }
/*     */     
/* 186 */     return localClass;
/*     */   }
/*     */   
/*     */   private void setPropertyType(Class<?> paramClass) {
/* 190 */     this.propertyTypeRef = getWeakReference(paramClass);
/*     */   }
/*     */   
/*     */   private Class<?> getPropertyType0()
/*     */   {
/* 195 */     return this.propertyTypeRef != null ? (Class)this.propertyTypeRef.get() : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getReadMethod()
/*     */   {
/* 206 */     Method localMethod = this.readMethodRef.get();
/* 207 */     if (localMethod == null) {
/* 208 */       Class localClass1 = getClass0();
/* 209 */       if ((localClass1 == null) || ((this.readMethodName == null) && (!this.readMethodRef.isSet())))
/*     */       {
/* 211 */         return null;
/*     */       }
/* 213 */       String str = "get" + getBaseName();
/* 214 */       if (this.readMethodName == null) {
/* 215 */         Class localClass2 = getPropertyType0();
/* 216 */         if ((localClass2 == Boolean.TYPE) || (localClass2 == null)) {
/* 217 */           this.readMethodName = ("is" + getBaseName());
/*     */         } else {
/* 219 */           this.readMethodName = str;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 228 */       localMethod = Introspector.findMethod(localClass1, this.readMethodName, 0);
/* 229 */       if ((localMethod == null) && (!this.readMethodName.equals(str))) {
/* 230 */         this.readMethodName = str;
/* 231 */         localMethod = Introspector.findMethod(localClass1, this.readMethodName, 0);
/*     */       }
/*     */       try {
/* 234 */         setReadMethod(localMethod);
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException) {}
/*     */     }
/*     */     
/* 239 */     return localMethod;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setReadMethod(Method paramMethod)
/*     */     throws IntrospectionException
/*     */   {
/* 250 */     this.readMethodRef.set(paramMethod);
/* 251 */     if (paramMethod == null) {
/* 252 */       this.readMethodName = null;
/* 253 */       return;
/*     */     }
/*     */     
/* 256 */     setPropertyType(findPropertyType(paramMethod, this.writeMethodRef.get()));
/* 257 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 259 */     this.readMethodName = paramMethod.getName();
/* 260 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getWriteMethod()
/*     */   {
/* 270 */     Method localMethod = this.writeMethodRef.get();
/* 271 */     if (localMethod == null) {
/* 272 */       Class localClass1 = getClass0();
/* 273 */       if ((localClass1 == null) || ((this.writeMethodName == null) && (!this.writeMethodRef.isSet())))
/*     */       {
/* 275 */         return null;
/*     */       }
/*     */       
/*     */ 
/* 279 */       Class localClass2 = getPropertyType0();
/* 280 */       if (localClass2 == null) {
/*     */         try
/*     */         {
/* 283 */           localClass2 = findPropertyType(getReadMethod(), null);
/* 284 */           setPropertyType(localClass2);
/*     */         }
/*     */         catch (IntrospectionException localIntrospectionException1)
/*     */         {
/* 288 */           return null;
/*     */         }
/*     */       }
/*     */       
/* 292 */       if (this.writeMethodName == null) {
/* 293 */         this.writeMethodName = ("set" + getBaseName());
/*     */       }
/*     */       
/* 296 */       Class[] arrayOfClass = { localClass2 == null ? null : localClass2 };
/* 297 */       localMethod = Introspector.findMethod(localClass1, this.writeMethodName, 1, arrayOfClass);
/* 298 */       if ((localMethod != null) && 
/* 299 */         (!localMethod.getReturnType().equals(Void.TYPE))) {
/* 300 */         localMethod = null;
/*     */       }
/*     */       try
/*     */       {
/* 304 */         setWriteMethod(localMethod);
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException2) {}
/*     */     }
/*     */     
/* 309 */     return localMethod;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setWriteMethod(Method paramMethod)
/*     */     throws IntrospectionException
/*     */   {
/* 320 */     this.writeMethodRef.set(paramMethod);
/* 321 */     if (paramMethod == null) {
/* 322 */       this.writeMethodName = null;
/* 323 */       return;
/*     */     }
/*     */     
/* 326 */     setPropertyType(findPropertyType(getReadMethod(), paramMethod));
/* 327 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 329 */     this.writeMethodName = paramMethod.getName();
/* 330 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setClass0(Class<?> paramClass)
/*     */   {
/* 337 */     if ((getClass0() != null) && (paramClass.isAssignableFrom(getClass0())))
/*     */     {
/* 339 */       return;
/*     */     }
/* 341 */     super.setClass0(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 351 */     return this.bound;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBound(boolean paramBoolean)
/*     */   {
/* 361 */     this.bound = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConstrained()
/*     */   {
/* 371 */     return this.constrained;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConstrained(boolean paramBoolean)
/*     */   {
/* 381 */     this.constrained = paramBoolean;
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
/*     */   public void setPropertyEditorClass(Class<?> paramClass)
/*     */   {
/* 394 */     this.propertyEditorClassRef = getWeakReference(paramClass);
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
/*     */   public Class<?> getPropertyEditorClass()
/*     */   {
/* 409 */     return this.propertyEditorClassRef != null ? (Class)this.propertyEditorClassRef.get() : null;
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
/*     */   public PropertyEditor createPropertyEditor(Object paramObject)
/*     */   {
/* 427 */     Object localObject = null;
/*     */     
/* 429 */     Class localClass = getPropertyEditorClass();
/* 430 */     if (localClass != null) {
/* 431 */       Constructor localConstructor = null;
/* 432 */       if (paramObject != null) {
/*     */         try {
/* 434 */           localConstructor = localClass.getConstructor(new Class[] { Object.class });
/*     */         }
/*     */         catch (Exception localException1) {}
/*     */       }
/*     */       try
/*     */       {
/* 440 */         if (localConstructor == null) {
/* 441 */           localObject = localClass.newInstance();
/*     */         } else {
/* 443 */           localObject = localConstructor.newInstance(new Object[] { paramObject });
/*     */         }
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */     
/* 449 */     return (PropertyEditor)localObject;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 462 */     if (this == paramObject) {
/* 463 */       return true;
/*     */     }
/* 465 */     if ((paramObject != null) && ((paramObject instanceof PropertyDescriptor))) {
/* 466 */       PropertyDescriptor localPropertyDescriptor = (PropertyDescriptor)paramObject;
/* 467 */       Method localMethod1 = localPropertyDescriptor.getReadMethod();
/* 468 */       Method localMethod2 = localPropertyDescriptor.getWriteMethod();
/*     */       
/* 470 */       if (!compareMethods(getReadMethod(), localMethod1)) {
/* 471 */         return false;
/*     */       }
/*     */       
/* 474 */       if (!compareMethods(getWriteMethod(), localMethod2)) {
/* 475 */         return false;
/*     */       }
/*     */       
/* 478 */       if ((getPropertyType() == localPropertyDescriptor.getPropertyType()) && 
/* 479 */         (getPropertyEditorClass() == localPropertyDescriptor.getPropertyEditorClass()) && 
/* 480 */         (this.bound == localPropertyDescriptor.isBound()) && (this.constrained == localPropertyDescriptor.isConstrained()) && (this.writeMethodName == localPropertyDescriptor.writeMethodName) && (this.readMethodName == localPropertyDescriptor.readMethodName))
/*     */       {
/*     */ 
/* 483 */         return true;
/*     */       }
/*     */     }
/* 486 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean compareMethods(Method paramMethod1, Method paramMethod2)
/*     */   {
/* 498 */     if ((paramMethod1 == null ? 1 : 0) != (paramMethod2 == null ? 1 : 0)) {
/* 499 */       return false;
/*     */     }
/*     */     
/* 502 */     if ((paramMethod1 != null) && (paramMethod2 != null) && 
/* 503 */       (!paramMethod1.equals(paramMethod2))) {
/* 504 */       return false;
/*     */     }
/*     */     
/* 507 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PropertyDescriptor(PropertyDescriptor paramPropertyDescriptor1, PropertyDescriptor paramPropertyDescriptor2)
/*     */   {
/* 519 */     super(paramPropertyDescriptor1, paramPropertyDescriptor2);
/*     */     
/* 521 */     if (paramPropertyDescriptor2.baseName != null) {
/* 522 */       this.baseName = paramPropertyDescriptor2.baseName;
/*     */     } else {
/* 524 */       this.baseName = paramPropertyDescriptor1.baseName;
/*     */     }
/*     */     
/* 527 */     if (paramPropertyDescriptor2.readMethodName != null) {
/* 528 */       this.readMethodName = paramPropertyDescriptor2.readMethodName;
/*     */     } else {
/* 530 */       this.readMethodName = paramPropertyDescriptor1.readMethodName;
/*     */     }
/*     */     
/* 533 */     if (paramPropertyDescriptor2.writeMethodName != null) {
/* 534 */       this.writeMethodName = paramPropertyDescriptor2.writeMethodName;
/*     */     } else {
/* 536 */       this.writeMethodName = paramPropertyDescriptor1.writeMethodName;
/*     */     }
/*     */     
/* 539 */     if (paramPropertyDescriptor2.propertyTypeRef != null) {
/* 540 */       this.propertyTypeRef = paramPropertyDescriptor2.propertyTypeRef;
/*     */     } else {
/* 542 */       this.propertyTypeRef = paramPropertyDescriptor1.propertyTypeRef;
/*     */     }
/*     */     
/*     */ 
/* 546 */     Method localMethod1 = paramPropertyDescriptor1.getReadMethod();
/* 547 */     Method localMethod2 = paramPropertyDescriptor2.getReadMethod();
/*     */     
/*     */     try
/*     */     {
/* 551 */       if (isAssignable(localMethod1, localMethod2)) {
/* 552 */         setReadMethod(localMethod2);
/*     */       } else {
/* 554 */         setReadMethod(localMethod1);
/*     */       }
/*     */     }
/*     */     catch (IntrospectionException localIntrospectionException1) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 562 */     if ((localMethod1 != null) && (localMethod2 != null) && 
/* 563 */       (localMethod1.getDeclaringClass() == localMethod2.getDeclaringClass()) && 
/* 564 */       (getReturnType(getClass0(), localMethod1) == Boolean.TYPE) && 
/* 565 */       (getReturnType(getClass0(), localMethod2) == Boolean.TYPE) && 
/* 566 */       (localMethod1.getName().indexOf("is") == 0) && 
/* 567 */       (localMethod2.getName().indexOf("get") == 0)) {
/*     */       try {
/* 569 */         setReadMethod(localMethod1);
/*     */       }
/*     */       catch (IntrospectionException localIntrospectionException2) {}
/*     */     }
/*     */     
/*     */ 
/* 575 */     Method localMethod3 = paramPropertyDescriptor1.getWriteMethod();
/* 576 */     Method localMethod4 = paramPropertyDescriptor2.getWriteMethod();
/*     */     try
/*     */     {
/* 579 */       if (localMethod4 != null) {
/* 580 */         setWriteMethod(localMethod4);
/*     */       } else {
/* 582 */         setWriteMethod(localMethod3);
/*     */       }
/*     */     }
/*     */     catch (IntrospectionException localIntrospectionException3) {}
/*     */     
/*     */ 
/* 588 */     if (paramPropertyDescriptor2.getPropertyEditorClass() != null) {
/* 589 */       setPropertyEditorClass(paramPropertyDescriptor2.getPropertyEditorClass());
/*     */     } else {
/* 591 */       setPropertyEditorClass(paramPropertyDescriptor1.getPropertyEditorClass());
/*     */     }
/*     */     
/*     */ 
/* 595 */     paramPropertyDescriptor1.bound |= paramPropertyDescriptor2.bound;
/* 596 */     paramPropertyDescriptor1.constrained |= paramPropertyDescriptor2.constrained;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   PropertyDescriptor(PropertyDescriptor paramPropertyDescriptor)
/*     */   {
/* 604 */     super(paramPropertyDescriptor);
/* 605 */     this.propertyTypeRef = paramPropertyDescriptor.propertyTypeRef;
/* 606 */     this.readMethodRef.set(paramPropertyDescriptor.readMethodRef.get());
/* 607 */     this.writeMethodRef.set(paramPropertyDescriptor.writeMethodRef.get());
/* 608 */     this.propertyEditorClassRef = paramPropertyDescriptor.propertyEditorClassRef;
/*     */     
/* 610 */     this.writeMethodName = paramPropertyDescriptor.writeMethodName;
/* 611 */     this.readMethodName = paramPropertyDescriptor.readMethodName;
/* 612 */     this.baseName = paramPropertyDescriptor.baseName;
/*     */     
/* 614 */     this.bound = paramPropertyDescriptor.bound;
/* 615 */     this.constrained = paramPropertyDescriptor.constrained;
/*     */   }
/*     */   
/*     */   void updateGenericsFor(Class<?> paramClass) {
/* 619 */     setClass0(paramClass);
/*     */     try {
/* 621 */       setPropertyType(findPropertyType(this.readMethodRef.get(), this.writeMethodRef.get()));
/*     */     }
/*     */     catch (IntrospectionException localIntrospectionException) {
/* 624 */       setPropertyType(null);
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
/*     */   private Class<?> findPropertyType(Method paramMethod1, Method paramMethod2)
/*     */     throws IntrospectionException
/*     */   {
/* 638 */     Class localClass = null;
/*     */     try { Class[] arrayOfClass;
/* 640 */       if (paramMethod1 != null) {
/* 641 */         arrayOfClass = getParameterTypes(getClass0(), paramMethod1);
/* 642 */         if (arrayOfClass.length != 0) {
/* 643 */           throw new IntrospectionException("bad read method arg count: " + paramMethod1);
/*     */         }
/*     */         
/* 646 */         localClass = getReturnType(getClass0(), paramMethod1);
/* 647 */         if (localClass == Void.TYPE)
/*     */         {
/* 649 */           throw new IntrospectionException("read method " + paramMethod1.getName() + " returns void");
/*     */         }
/*     */       }
/* 652 */       if (paramMethod2 != null) {
/* 653 */         arrayOfClass = getParameterTypes(getClass0(), paramMethod2);
/* 654 */         if (arrayOfClass.length != 1) {
/* 655 */           throw new IntrospectionException("bad write method arg count: " + paramMethod2);
/*     */         }
/*     */         
/* 658 */         if ((localClass != null) && (!arrayOfClass[0].isAssignableFrom(localClass))) {
/* 659 */           throw new IntrospectionException("type mismatch between read and write methods");
/*     */         }
/* 661 */         localClass = arrayOfClass[0];
/*     */       }
/*     */     } catch (IntrospectionException localIntrospectionException) {
/* 664 */       throw localIntrospectionException;
/*     */     }
/* 666 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 678 */     int i = 7;
/*     */     
/*     */ 
/* 681 */     i = 37 * i + (getPropertyType() == null ? 0 : getPropertyType().hashCode());
/*     */     
/* 683 */     i = 37 * i + (getReadMethod() == null ? 0 : getReadMethod().hashCode());
/*     */     
/* 685 */     i = 37 * i + (getWriteMethod() == null ? 0 : getWriteMethod().hashCode());
/*     */     
/* 687 */     i = 37 * i + (getPropertyEditorClass() == null ? 0 : getPropertyEditorClass().hashCode());
/*     */     
/* 689 */     i = 37 * i + (this.writeMethodName == null ? 0 : this.writeMethodName.hashCode());
/*     */     
/* 691 */     i = 37 * i + (this.readMethodName == null ? 0 : this.readMethodName.hashCode());
/* 692 */     i = 37 * i + getName().hashCode();
/* 693 */     i = 37 * i + (!this.bound ? 0 : 1);
/* 694 */     i = 37 * i + (!this.constrained ? 0 : 1);
/*     */     
/* 696 */     return i;
/*     */   }
/*     */   
/*     */   String getBaseName()
/*     */   {
/* 701 */     if (this.baseName == null) {
/* 702 */       this.baseName = NameGenerator.capitalize(getName());
/*     */     }
/* 704 */     return this.baseName;
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 708 */     appendTo(paramStringBuilder, "bound", this.bound);
/* 709 */     appendTo(paramStringBuilder, "constrained", this.constrained);
/* 710 */     appendTo(paramStringBuilder, "propertyEditorClass", this.propertyEditorClassRef);
/* 711 */     appendTo(paramStringBuilder, "propertyType", this.propertyTypeRef);
/* 712 */     appendTo(paramStringBuilder, "readMethod", this.readMethodRef.get());
/* 713 */     appendTo(paramStringBuilder, "writeMethod", this.writeMethodRef.get());
/*     */   }
/*     */   
/*     */   private boolean isAssignable(Method paramMethod1, Method paramMethod2) {
/* 717 */     if (paramMethod1 == null) {
/* 718 */       return true;
/*     */     }
/* 720 */     if (paramMethod2 == null) {
/* 721 */       return false;
/*     */     }
/* 723 */     if (!paramMethod1.getName().equals(paramMethod2.getName())) {
/* 724 */       return true;
/*     */     }
/* 726 */     Class localClass1 = paramMethod1.getDeclaringClass();
/* 727 */     Class localClass2 = paramMethod2.getDeclaringClass();
/* 728 */     if (!localClass1.isAssignableFrom(localClass2)) {
/* 729 */       return false;
/*     */     }
/* 731 */     localClass1 = getReturnType(getClass0(), paramMethod1);
/* 732 */     localClass2 = getReturnType(getClass0(), paramMethod2);
/* 733 */     if (!localClass1.isAssignableFrom(localClass2)) {
/* 734 */       return false;
/*     */     }
/* 736 */     Class[] arrayOfClass1 = getParameterTypes(getClass0(), paramMethod1);
/* 737 */     Class[] arrayOfClass2 = getParameterTypes(getClass0(), paramMethod2);
/* 738 */     if (arrayOfClass1.length != arrayOfClass2.length) {
/* 739 */       return true;
/*     */     }
/* 741 */     for (int i = 0; i < arrayOfClass1.length; i++) {
/* 742 */       if (!arrayOfClass1[i].isAssignableFrom(arrayOfClass2[i])) {
/* 743 */         return false;
/*     */       }
/*     */     }
/* 746 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/PropertyDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */