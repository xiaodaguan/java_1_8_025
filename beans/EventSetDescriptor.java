/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventSetDescriptor
/*     */   extends FeatureDescriptor
/*     */ {
/*     */   private MethodDescriptor[] listenerMethodDescriptors;
/*     */   private MethodDescriptor addMethodDescriptor;
/*     */   private MethodDescriptor removeMethodDescriptor;
/*     */   private MethodDescriptor getMethodDescriptor;
/*     */   private Reference<Method[]> listenerMethodsRef;
/*     */   private Reference<? extends Class<?>> listenerTypeRef;
/*     */   private boolean unicast;
/*  51 */   private boolean inDefaultEventSet = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EventSetDescriptor(Class<?> paramClass1, String paramString1, Class<?> paramClass2, String paramString2)
/*     */     throws IntrospectionException
/*     */   {
/*  75 */     this(paramClass1, paramString1, paramClass2, new String[] { paramString2 }, "add" + 
/*     */     
/*  77 */       getListenerClassName(paramClass2), "remove" + 
/*  78 */       getListenerClassName(paramClass2), "get" + 
/*  79 */       getListenerClassName(paramClass2) + "s");
/*     */     
/*  81 */     String str = NameGenerator.capitalize(paramString1) + "Event";
/*  82 */     Method[] arrayOfMethod = getListenerMethods();
/*  83 */     if (arrayOfMethod.length > 0) {
/*  84 */       Class[] arrayOfClass = getParameterTypes(getClass0(), arrayOfMethod[0]);
/*     */       
/*  86 */       if ((!"vetoableChange".equals(paramString1)) && (!arrayOfClass[0].getName().endsWith(str))) {
/*  87 */         throw new IntrospectionException("Method \"" + paramString2 + "\" should have argument \"" + str + "\"");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static String getListenerClassName(Class<?> paramClass)
/*     */   {
/*  95 */     String str = paramClass.getName();
/*  96 */     return str.substring(str.lastIndexOf('.') + 1);
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
/*     */   public EventSetDescriptor(Class<?> paramClass1, String paramString1, Class<?> paramClass2, String[] paramArrayOfString, String paramString2, String paramString3)
/*     */     throws IntrospectionException
/*     */   {
/* 124 */     this(paramClass1, paramString1, paramClass2, paramArrayOfString, paramString2, paramString3, null);
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
/*     */   public EventSetDescriptor(Class<?> paramClass1, String paramString1, Class<?> paramClass2, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4)
/*     */     throws IntrospectionException
/*     */   {
/* 158 */     if ((paramClass1 == null) || (paramString1 == null) || (paramClass2 == null)) {
/* 159 */       throw new NullPointerException();
/*     */     }
/* 161 */     setName(paramString1);
/* 162 */     setClass0(paramClass1);
/* 163 */     setListenerType(paramClass2);
/*     */     
/* 165 */     Method[] arrayOfMethod = new Method[paramArrayOfString.length];
/* 166 */     for (int i = 0; i < paramArrayOfString.length; i++)
/*     */     {
/* 168 */       if (paramArrayOfString[i] == null) {
/* 169 */         throw new NullPointerException();
/*     */       }
/* 171 */       arrayOfMethod[i] = getMethod(paramClass2, paramArrayOfString[i], 1);
/*     */     }
/* 173 */     setListenerMethods(arrayOfMethod);
/*     */     
/* 175 */     setAddListenerMethod(getMethod(paramClass1, paramString2, 1));
/* 176 */     setRemoveListenerMethod(getMethod(paramClass1, paramString3, 1));
/*     */     
/*     */ 
/* 179 */     Method localMethod = Introspector.findMethod(paramClass1, paramString4, 0);
/* 180 */     if (localMethod != null) {
/* 181 */       setGetListenerMethod(localMethod);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Method getMethod(Class<?> paramClass, String paramString, int paramInt) throws IntrospectionException
/*     */   {
/* 187 */     if (paramString == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     Method localMethod = Introspector.findMethod(paramClass, paramString, paramInt);
/* 191 */     if ((localMethod == null) || (Modifier.isStatic(localMethod.getModifiers())))
/*     */     {
/* 193 */       throw new IntrospectionException("Method not found: " + paramString + " on class " + paramClass.getName());
/*     */     }
/* 195 */     return localMethod;
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
/*     */   public EventSetDescriptor(String paramString, Class<?> paramClass, Method[] paramArrayOfMethod, Method paramMethod1, Method paramMethod2)
/*     */     throws IntrospectionException
/*     */   {
/* 219 */     this(paramString, paramClass, paramArrayOfMethod, paramMethod1, paramMethod2, null);
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
/*     */   public EventSetDescriptor(String paramString, Class<?> paramClass, Method[] paramArrayOfMethod, Method paramMethod1, Method paramMethod2, Method paramMethod3)
/*     */     throws IntrospectionException
/*     */   {
/* 248 */     setName(paramString);
/* 249 */     setListenerMethods(paramArrayOfMethod);
/* 250 */     setAddListenerMethod(paramMethod1);
/* 251 */     setRemoveListenerMethod(paramMethod2);
/* 252 */     setGetListenerMethod(paramMethod3);
/* 253 */     setListenerType(paramClass);
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
/*     */   public EventSetDescriptor(String paramString, Class<?> paramClass, MethodDescriptor[] paramArrayOfMethodDescriptor, Method paramMethod1, Method paramMethod2)
/*     */     throws IntrospectionException
/*     */   {
/* 279 */     setName(paramString);
/*     */     
/* 281 */     this.listenerMethodDescriptors = (paramArrayOfMethodDescriptor != null ? (MethodDescriptor[])paramArrayOfMethodDescriptor.clone() : null);
/*     */     
/* 283 */     setAddListenerMethod(paramMethod1);
/* 284 */     setRemoveListenerMethod(paramMethod2);
/* 285 */     setListenerType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getListenerType()
/*     */   {
/* 296 */     return this.listenerTypeRef != null ? (Class)this.listenerTypeRef.get() : null;
/*     */   }
/*     */   
/*     */   private void setListenerType(Class<?> paramClass)
/*     */   {
/* 301 */     this.listenerTypeRef = getWeakReference(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method[] getListenerMethods()
/*     */   {
/* 312 */     Method[] arrayOfMethod = getListenerMethods0();
/* 313 */     if (arrayOfMethod == null) {
/* 314 */       if (this.listenerMethodDescriptors != null) {
/* 315 */         arrayOfMethod = new Method[this.listenerMethodDescriptors.length];
/* 316 */         for (int i = 0; i < arrayOfMethod.length; i++) {
/* 317 */           arrayOfMethod[i] = this.listenerMethodDescriptors[i].getMethod();
/*     */         }
/*     */       }
/* 320 */       setListenerMethods(arrayOfMethod);
/*     */     }
/* 322 */     return arrayOfMethod;
/*     */   }
/*     */   
/*     */   private void setListenerMethods(Method[] paramArrayOfMethod) {
/* 326 */     if (paramArrayOfMethod == null) {
/* 327 */       return;
/*     */     }
/* 329 */     if (this.listenerMethodDescriptors == null) {
/* 330 */       this.listenerMethodDescriptors = new MethodDescriptor[paramArrayOfMethod.length];
/* 331 */       for (int i = 0; i < paramArrayOfMethod.length; i++) {
/* 332 */         this.listenerMethodDescriptors[i] = new MethodDescriptor(paramArrayOfMethod[i]);
/*     */       }
/*     */     }
/* 335 */     this.listenerMethodsRef = getSoftReference(paramArrayOfMethod);
/*     */   }
/*     */   
/*     */   private Method[] getListenerMethods0()
/*     */   {
/* 340 */     return this.listenerMethodsRef != null ? (Method[])this.listenerMethodsRef.get() : null;
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
/*     */   public synchronized MethodDescriptor[] getListenerMethodDescriptors()
/*     */   {
/* 353 */     return this.listenerMethodDescriptors != null ? (MethodDescriptor[])this.listenerMethodDescriptors.clone() : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getAddListenerMethod()
/*     */   {
/* 363 */     return getMethod(this.addMethodDescriptor);
/*     */   }
/*     */   
/*     */   private synchronized void setAddListenerMethod(Method paramMethod) {
/* 367 */     if (paramMethod == null) {
/* 368 */       return;
/*     */     }
/* 370 */     if (getClass0() == null) {
/* 371 */       setClass0(paramMethod.getDeclaringClass());
/*     */     }
/* 373 */     this.addMethodDescriptor = new MethodDescriptor(paramMethod);
/* 374 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getRemoveListenerMethod()
/*     */   {
/* 383 */     return getMethod(this.removeMethodDescriptor);
/*     */   }
/*     */   
/*     */   private synchronized void setRemoveListenerMethod(Method paramMethod) {
/* 387 */     if (paramMethod == null) {
/* 388 */       return;
/*     */     }
/* 390 */     if (getClass0() == null) {
/* 391 */       setClass0(paramMethod.getDeclaringClass());
/*     */     }
/* 393 */     this.removeMethodDescriptor = new MethodDescriptor(paramMethod);
/* 394 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getGetListenerMethod()
/*     */   {
/* 405 */     return getMethod(this.getMethodDescriptor);
/*     */   }
/*     */   
/*     */   private synchronized void setGetListenerMethod(Method paramMethod) {
/* 409 */     if (paramMethod == null) {
/* 410 */       return;
/*     */     }
/* 412 */     if (getClass0() == null) {
/* 413 */       setClass0(paramMethod.getDeclaringClass());
/*     */     }
/* 415 */     this.getMethodDescriptor = new MethodDescriptor(paramMethod);
/* 416 */     setTransient((Transient)paramMethod.getAnnotation(Transient.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUnicast(boolean paramBoolean)
/*     */   {
/* 425 */     this.unicast = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUnicast()
/*     */   {
/* 436 */     return this.unicast;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInDefaultEventSet(boolean paramBoolean)
/*     */   {
/* 448 */     this.inDefaultEventSet = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInDefaultEventSet()
/*     */   {
/* 458 */     return this.inDefaultEventSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   EventSetDescriptor(EventSetDescriptor paramEventSetDescriptor1, EventSetDescriptor paramEventSetDescriptor2)
/*     */   {
/* 470 */     super(paramEventSetDescriptor1, paramEventSetDescriptor2);
/* 471 */     this.listenerMethodDescriptors = paramEventSetDescriptor1.listenerMethodDescriptors;
/* 472 */     if (paramEventSetDescriptor2.listenerMethodDescriptors != null) {
/* 473 */       this.listenerMethodDescriptors = paramEventSetDescriptor2.listenerMethodDescriptors;
/*     */     }
/*     */     
/* 476 */     this.listenerTypeRef = paramEventSetDescriptor1.listenerTypeRef;
/* 477 */     if (paramEventSetDescriptor2.listenerTypeRef != null) {
/* 478 */       this.listenerTypeRef = paramEventSetDescriptor2.listenerTypeRef;
/*     */     }
/*     */     
/* 481 */     this.addMethodDescriptor = paramEventSetDescriptor1.addMethodDescriptor;
/* 482 */     if (paramEventSetDescriptor2.addMethodDescriptor != null) {
/* 483 */       this.addMethodDescriptor = paramEventSetDescriptor2.addMethodDescriptor;
/*     */     }
/*     */     
/* 486 */     this.removeMethodDescriptor = paramEventSetDescriptor1.removeMethodDescriptor;
/* 487 */     if (paramEventSetDescriptor2.removeMethodDescriptor != null) {
/* 488 */       this.removeMethodDescriptor = paramEventSetDescriptor2.removeMethodDescriptor;
/*     */     }
/*     */     
/* 491 */     this.getMethodDescriptor = paramEventSetDescriptor1.getMethodDescriptor;
/* 492 */     if (paramEventSetDescriptor2.getMethodDescriptor != null) {
/* 493 */       this.getMethodDescriptor = paramEventSetDescriptor2.getMethodDescriptor;
/*     */     }
/*     */     
/* 496 */     this.unicast = paramEventSetDescriptor2.unicast;
/* 497 */     if ((!paramEventSetDescriptor1.inDefaultEventSet) || (!paramEventSetDescriptor2.inDefaultEventSet)) {
/* 498 */       this.inDefaultEventSet = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   EventSetDescriptor(EventSetDescriptor paramEventSetDescriptor)
/*     */   {
/* 507 */     super(paramEventSetDescriptor);
/* 508 */     if (paramEventSetDescriptor.listenerMethodDescriptors != null) {
/* 509 */       int i = paramEventSetDescriptor.listenerMethodDescriptors.length;
/* 510 */       this.listenerMethodDescriptors = new MethodDescriptor[i];
/* 511 */       for (int j = 0; j < i; j++) {
/* 512 */         this.listenerMethodDescriptors[j] = new MethodDescriptor(paramEventSetDescriptor.listenerMethodDescriptors[j]);
/*     */       }
/*     */     }
/*     */     
/* 516 */     this.listenerTypeRef = paramEventSetDescriptor.listenerTypeRef;
/*     */     
/* 518 */     this.addMethodDescriptor = paramEventSetDescriptor.addMethodDescriptor;
/* 519 */     this.removeMethodDescriptor = paramEventSetDescriptor.removeMethodDescriptor;
/* 520 */     this.getMethodDescriptor = paramEventSetDescriptor.getMethodDescriptor;
/*     */     
/* 522 */     this.unicast = paramEventSetDescriptor.unicast;
/* 523 */     this.inDefaultEventSet = paramEventSetDescriptor.inDefaultEventSet;
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 527 */     appendTo(paramStringBuilder, "unicast", this.unicast);
/* 528 */     appendTo(paramStringBuilder, "inDefaultEventSet", this.inDefaultEventSet);
/* 529 */     appendTo(paramStringBuilder, "listenerType", this.listenerTypeRef);
/* 530 */     appendTo(paramStringBuilder, "getListenerMethod", getMethod(this.getMethodDescriptor));
/* 531 */     appendTo(paramStringBuilder, "addListenerMethod", getMethod(this.addMethodDescriptor));
/* 532 */     appendTo(paramStringBuilder, "removeListenerMethod", getMethod(this.removeMethodDescriptor));
/*     */   }
/*     */   
/*     */   private static Method getMethod(MethodDescriptor paramMethodDescriptor)
/*     */   {
/* 537 */     return paramMethodDescriptor != null ? paramMethodDescriptor.getMethod() : null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/EventSetDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */