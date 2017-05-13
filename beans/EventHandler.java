/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventHandler
/*     */   implements InvocationHandler
/*     */ {
/*     */   private Object target;
/*     */   private String action;
/*     */   private final String eventPropertyName;
/*     */   private final String listenerMethodName;
/* 284 */   private final AccessControlContext acc = AccessController.getContext();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @ConstructorProperties({"target", "action", "eventPropertyName", "listenerMethodName"})
/*     */   public EventHandler(Object paramObject, String paramString1, String paramString2, String paramString3)
/*     */   {
/* 313 */     this.target = paramObject;
/* 314 */     this.action = paramString1;
/* 315 */     if (paramObject == null) {
/* 316 */       throw new NullPointerException("target must be non-null");
/*     */     }
/* 318 */     if (paramString1 == null) {
/* 319 */       throw new NullPointerException("action must be non-null");
/*     */     }
/* 321 */     this.eventPropertyName = paramString2;
/* 322 */     this.listenerMethodName = paramString3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getTarget()
/*     */   {
/* 332 */     return this.target;
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
/*     */   public String getAction()
/*     */   {
/* 345 */     return this.action;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getEventPropertyName()
/*     */   {
/* 357 */     return this.eventPropertyName;
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
/*     */   public String getListenerMethodName()
/*     */   {
/* 370 */     return this.listenerMethodName;
/*     */   }
/*     */   
/*     */   private Object applyGetters(Object paramObject, String paramString) {
/* 374 */     if ((paramString == null) || (paramString.equals(""))) {
/* 375 */       return paramObject;
/*     */     }
/* 377 */     int i = paramString.indexOf('.');
/* 378 */     if (i == -1) {
/* 379 */       i = paramString.length();
/*     */     }
/* 381 */     String str1 = paramString.substring(0, i);
/* 382 */     String str2 = paramString.substring(Math.min(i + 1, paramString.length()));
/*     */     try
/*     */     {
/* 385 */       Method localMethod = null;
/* 386 */       if (paramObject != null) {
/* 387 */         localMethod = Statement.getMethod(paramObject.getClass(), "get" + 
/* 388 */           NameGenerator.capitalize(str1), new Class[0]);
/*     */         
/* 390 */         if (localMethod == null) {
/* 391 */           localMethod = Statement.getMethod(paramObject.getClass(), "is" + 
/* 392 */             NameGenerator.capitalize(str1), new Class[0]);
/*     */         }
/*     */         
/* 395 */         if (localMethod == null) {
/* 396 */           localMethod = Statement.getMethod(paramObject.getClass(), str1, new Class[0]);
/*     */         }
/*     */       }
/* 399 */       if (localMethod == null) {
/* 400 */         throw new RuntimeException("No method called: " + str1 + " defined on " + paramObject);
/*     */       }
/*     */       
/* 403 */       Object localObject = MethodUtil.invoke(localMethod, paramObject, new Object[0]);
/* 404 */       return applyGetters(localObject, str2);
/*     */     }
/*     */     catch (Exception localException) {
/* 407 */       throw new RuntimeException("Failed to call method: " + str1 + " on " + paramObject, localException);
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
/*     */   public Object invoke(final Object paramObject, final Method paramMethod, final Object[] paramArrayOfObject)
/*     */   {
/* 424 */     AccessControlContext localAccessControlContext = this.acc;
/* 425 */     if ((localAccessControlContext == null) && (System.getSecurityManager() != null)) {
/* 426 */       throw new SecurityException("AccessControlContext is not set");
/*     */     }
/* 428 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/* 430 */       public Object run() { return EventHandler.this.invokeInternal(paramObject, paramMethod, paramArrayOfObject); } }, localAccessControlContext);
/*     */   }
/*     */   
/*     */ 
/*     */   private Object invokeInternal(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
/*     */   {
/* 436 */     String str1 = paramMethod.getName();
/* 437 */     if (paramMethod.getDeclaringClass() == Object.class)
/*     */     {
/* 439 */       if (str1.equals("hashCode"))
/* 440 */         return new Integer(System.identityHashCode(paramObject));
/* 441 */       if (str1.equals("equals"))
/* 442 */         return paramObject == paramArrayOfObject[0] ? Boolean.TRUE : Boolean.FALSE;
/* 443 */       if (str1.equals("toString")) {
/* 444 */         return paramObject.getClass().getName() + '@' + Integer.toHexString(paramObject.hashCode());
/*     */       }
/*     */     }
/*     */     
/* 448 */     if ((this.listenerMethodName == null) || (this.listenerMethodName.equals(str1))) {
/* 449 */       Class[] arrayOfClass = null;
/* 450 */       Object[] arrayOfObject = null;
/*     */       
/* 452 */       if (this.eventPropertyName == null) {
/* 453 */         arrayOfObject = new Object[0];
/* 454 */         arrayOfClass = new Class[0];
/*     */       }
/*     */       else {
/* 457 */         Object localObject1 = applyGetters(paramArrayOfObject[0], getEventPropertyName());
/* 458 */         arrayOfObject = new Object[] { localObject1 };
/*     */         
/* 460 */         arrayOfClass = new Class[] { localObject1 == null ? null : localObject1.getClass() };
/*     */       }
/*     */       try {
/* 463 */         int i = this.action.lastIndexOf('.');
/* 464 */         if (i != -1) {
/* 465 */           this.target = applyGetters(this.target, this.action.substring(0, i));
/* 466 */           this.action = this.action.substring(i + 1);
/*     */         }
/* 468 */         localObject2 = Statement.getMethod(this.target
/* 469 */           .getClass(), this.action, arrayOfClass);
/* 470 */         if (localObject2 == null) {
/* 471 */           localObject2 = Statement.getMethod(this.target.getClass(), "set" + 
/* 472 */             NameGenerator.capitalize(this.action), arrayOfClass);
/*     */         }
/* 474 */         if (localObject2 == null) {
/* 475 */           String str2 = " with argument " + arrayOfClass[0];
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 480 */           throw new RuntimeException("No method called " + this.action + " on " + this.target.getClass() + str2);
/*     */         }
/* 482 */         return MethodUtil.invoke((Method)localObject2, this.target, arrayOfObject);
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException) {
/* 485 */         throw new RuntimeException(localIllegalAccessException);
/*     */       }
/*     */       catch (InvocationTargetException localInvocationTargetException) {
/* 488 */         Object localObject2 = localInvocationTargetException.getTargetException();
/* 489 */         throw ((localObject2 instanceof RuntimeException) ? (RuntimeException)localObject2 : new RuntimeException((Throwable)localObject2));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 494 */     return null;
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
/*     */   public static <T> T create(Class<T> paramClass, Object paramObject, String paramString)
/*     */   {
/* 535 */     return (T)create(paramClass, paramObject, paramString, null, null);
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
/*     */   public static <T> T create(Class<T> paramClass, Object paramObject, String paramString1, String paramString2)
/*     */   {
/* 594 */     return (T)create(paramClass, paramObject, paramString1, paramString2, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T create(Class<T> paramClass, Object paramObject, String paramString1, String paramString2, String paramString3)
/*     */   {
/* 687 */     final EventHandler localEventHandler = new EventHandler(paramObject, paramString1, paramString2, paramString3);
/*     */     
/*     */ 
/* 690 */     if (paramClass == null) {
/* 691 */       throw new NullPointerException("listenerInterface must be non-null");
/*     */     }
/*     */     
/* 694 */     ClassLoader localClassLoader = getClassLoader(paramClass);
/* 695 */     final Class[] arrayOfClass = { paramClass };
/* 696 */     (T)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public T run() {
/* 699 */         return (T)Proxy.newProxyInstance(this.val$loader, arrayOfClass, localEventHandler);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private static ClassLoader getClassLoader(Class<?> paramClass) {
/* 705 */     ReflectUtil.checkPackageAccess(paramClass);
/* 706 */     ClassLoader localClassLoader = paramClass.getClassLoader();
/* 707 */     if (localClassLoader == null) {
/* 708 */       localClassLoader = Thread.currentThread().getContextClassLoader();
/* 709 */       if (localClassLoader == null) {
/* 710 */         localClassLoader = ClassLoader.getSystemClassLoader();
/*     */       }
/*     */     }
/* 713 */     return localClassLoader;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/EventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */