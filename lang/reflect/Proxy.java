/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.BiFunction;
/*     */ import sun.misc.ProxyGenerator;
/*     */ import sun.misc.VM;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Proxy
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2222568056686623797L;
/* 233 */   private static final Class<?>[] constructorParams = { InvocationHandler.class };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */   private static final WeakCache<ClassLoader, Class<?>[], Class<?>> proxyClassCache = new WeakCache(new KeyFactory(null), new ProxyClassFactory(null));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected InvocationHandler h;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Proxy() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Proxy(InvocationHandler paramInvocationHandler)
/*     */   {
/* 265 */     Objects.requireNonNull(paramInvocationHandler);
/* 266 */     this.h = paramInvocationHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CallerSensitive
/*     */   public static Class<?> getProxyClass(ClassLoader paramClassLoader, Class<?>... paramVarArgs)
/*     */     throws IllegalArgumentException
/*     */   {
/* 365 */     Class[] arrayOfClass = (Class[])paramVarArgs.clone();
/* 366 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 367 */     if (localSecurityManager != null) {
/* 368 */       checkProxyAccess(Reflection.getCallerClass(), paramClassLoader, arrayOfClass);
/*     */     }
/*     */     
/* 371 */     return getProxyClass0(paramClassLoader, arrayOfClass);
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
/*     */   private static void checkProxyAccess(Class<?> paramClass, ClassLoader paramClassLoader, Class<?>... paramVarArgs)
/*     */   {
/* 396 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 397 */     if (localSecurityManager != null) {
/* 398 */       ClassLoader localClassLoader = paramClass.getClassLoader();
/* 399 */       if ((VM.isSystemDomainLoader(paramClassLoader)) && (!VM.isSystemDomainLoader(localClassLoader))) {
/* 400 */         localSecurityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */       }
/* 402 */       ReflectUtil.checkProxyPackageAccess(localClassLoader, paramVarArgs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Class<?> getProxyClass0(ClassLoader paramClassLoader, Class<?>... paramVarArgs)
/*     */   {
/* 412 */     if (paramVarArgs.length > 65535) {
/* 413 */       throw new IllegalArgumentException("interface limit exceeded");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 419 */     return (Class)proxyClassCache.get(paramClassLoader, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 425 */   private static final Object key0 = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class Key1
/*     */     extends WeakReference<Class<?>>
/*     */   {
/*     */     private final int hash;
/*     */     
/*     */ 
/*     */ 
/*     */     Key1(Class<?> paramClass)
/*     */     {
/* 439 */       super();
/* 440 */       this.hash = paramClass.hashCode();
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 445 */       return this.hash;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 451 */       if (this != paramObject) if (paramObject == null)
/*     */           break label45;
/*     */       Class localClass;
/*     */       label45:
/* 455 */       return (paramObject.getClass() == Key1.class) && ((localClass = (Class)get()) != null) && (localClass == ((Key1)paramObject).get());
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Key2
/*     */     extends WeakReference<Class<?>>
/*     */   {
/*     */     private final int hash;
/*     */     private final WeakReference<Class<?>> ref2;
/*     */     
/*     */     Key2(Class<?> paramClass1, Class<?> paramClass2)
/*     */     {
/* 467 */       super();
/* 468 */       this.hash = (31 * paramClass1.hashCode() + paramClass2.hashCode());
/* 469 */       this.ref2 = new WeakReference(paramClass2);
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 474 */       return this.hash;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 480 */       if (this != paramObject) if (paramObject == null) {
/*     */           break label74;
/*     */         }
/*     */       Class localClass1;
/*     */       Class localClass2;
/*     */       label74:
/* 486 */       return (paramObject.getClass() == Key2.class) && ((localClass1 = (Class)get()) != null) && (localClass1 == ((Key2)paramObject).get()) && ((localClass2 = (Class)this.ref2.get()) != null) && (localClass2 == ((Key2)paramObject).ref2.get());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class KeyX
/*     */   {
/*     */     private final int hash;
/*     */     
/*     */     private final WeakReference<Class<?>>[] refs;
/*     */     
/*     */ 
/*     */     KeyX(Class<?>[] paramArrayOfClass)
/*     */     {
/* 500 */       this.hash = Arrays.hashCode(paramArrayOfClass);
/* 501 */       this.refs = ((WeakReference[])new WeakReference[paramArrayOfClass.length]);
/* 502 */       for (int i = 0; i < paramArrayOfClass.length; i++) {
/* 503 */         this.refs[i] = new WeakReference(paramArrayOfClass[i]);
/*     */       }
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 509 */       return this.hash;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 514 */       if (this != paramObject) if (paramObject == null)
/*     */           break label39;
/*     */       label39:
/* 517 */       return (paramObject.getClass() == KeyX.class) && (equals(this.refs, ((KeyX)paramObject).refs));
/*     */     }
/*     */     
/*     */     private static boolean equals(WeakReference<Class<?>>[] paramArrayOfWeakReference1, WeakReference<Class<?>>[] paramArrayOfWeakReference2)
/*     */     {
/* 522 */       if (paramArrayOfWeakReference1.length != paramArrayOfWeakReference2.length) {
/* 523 */         return false;
/*     */       }
/* 525 */       for (int i = 0; i < paramArrayOfWeakReference1.length; i++) {
/* 526 */         Class localClass = (Class)paramArrayOfWeakReference1[i].get();
/* 527 */         if ((localClass == null) || (localClass != paramArrayOfWeakReference2[i].get())) {
/* 528 */           return false;
/*     */         }
/*     */       }
/* 531 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class KeyFactory
/*     */     implements BiFunction<ClassLoader, Class<?>[], Object>
/*     */   {
/*     */     public Object apply(ClassLoader paramClassLoader, Class<?>[] paramArrayOfClass)
/*     */     {
/* 544 */       switch (paramArrayOfClass.length) {
/* 545 */       case 1:  return new Proxy.Key1(paramArrayOfClass[0]);
/* 546 */       case 2:  return new Proxy.Key2(paramArrayOfClass[0], paramArrayOfClass[1]);
/* 547 */       case 0:  return Proxy.key0; }
/* 548 */       return new Proxy.KeyX(paramArrayOfClass);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ProxyClassFactory
/*     */     implements BiFunction<ClassLoader, Class<?>[], Class<?>>
/*     */   {
/*     */     private static final String proxyClassNamePrefix = "$Proxy";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 564 */     private static final AtomicLong nextUniqueNumber = new AtomicLong();
/*     */     
/*     */ 
/*     */     public Class<?> apply(ClassLoader paramClassLoader, Class<?>[] paramArrayOfClass)
/*     */     {
/* 569 */       IdentityHashMap localIdentityHashMap = new IdentityHashMap(paramArrayOfClass.length);
/* 570 */       for (Object localObject2 : paramArrayOfClass)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 575 */         Class localClass = null;
/*     */         try {
/* 577 */           localClass = Class.forName(((Class)localObject2).getName(), false, paramClassLoader);
/*     */         }
/*     */         catch (ClassNotFoundException localClassNotFoundException) {}
/* 580 */         if (localClass != localObject2) {
/* 581 */           throw new IllegalArgumentException(localObject2 + " is not visible from class loader");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 588 */         if (!localClass.isInterface())
/*     */         {
/* 590 */           throw new IllegalArgumentException(localClass.getName() + " is not an interface");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 595 */         if (localIdentityHashMap.put(localClass, Boolean.TRUE) != null)
/*     */         {
/* 597 */           throw new IllegalArgumentException("repeated interface: " + localClass.getName());
/*     */         }
/*     */       }
/*     */       
/* 601 */       ??? = null;
/* 602 */       ??? = 17;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 609 */       for (localObject3 : paramArrayOfClass) {
/* 610 */         int n = ((Class)localObject3).getModifiers();
/* 611 */         if (!Modifier.isPublic(n)) {
/* 612 */           ??? = 16;
/* 613 */           String str2 = ((Class)localObject3).getName();
/* 614 */           int i1 = str2.lastIndexOf('.');
/* 615 */           String str3 = i1 == -1 ? "" : str2.substring(0, i1 + 1);
/* 616 */           if (??? == null) {
/* 617 */             ??? = str3;
/* 618 */           } else if (!str3.equals(???)) {
/* 619 */             throw new IllegalArgumentException("non-public interfaces from different packages");
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 625 */       if (??? == null)
/*     */       {
/* 627 */         ??? = "com.sun.proxy.";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 633 */       long l = nextUniqueNumber.getAndIncrement();
/* 634 */       String str1 = (String)??? + "$Proxy" + l;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 639 */       Object localObject3 = ProxyGenerator.generateProxyClass(str1, paramArrayOfClass, ???);
/*     */       try
/*     */       {
/* 642 */         return Proxy.defineClass0(paramClassLoader, str1, (byte[])localObject3, 0, localObject3.length);
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       catch (ClassFormatError localClassFormatError)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 652 */         throw new IllegalArgumentException(localClassFormatError.toString());
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static Object newProxyInstance(ClassLoader paramClassLoader, Class<?>[] paramArrayOfClass, InvocationHandler paramInvocationHandler)
/*     */     throws IllegalArgumentException
/*     */   {
/* 708 */     Objects.requireNonNull(paramInvocationHandler);
/*     */     
/* 710 */     Class[] arrayOfClass = (Class[])paramArrayOfClass.clone();
/* 711 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 712 */     if (localSecurityManager != null) {
/* 713 */       checkProxyAccess(Reflection.getCallerClass(), paramClassLoader, arrayOfClass);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 719 */     Class localClass = getProxyClass0(paramClassLoader, arrayOfClass);
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 725 */       if (localSecurityManager != null) {
/* 726 */         checkNewProxyPermission(Reflection.getCallerClass(), localClass);
/*     */       }
/*     */       
/* 729 */       Constructor localConstructor = localClass.getConstructor(constructorParams);
/* 730 */       localObject = paramInvocationHandler;
/* 731 */       if (!Modifier.isPublic(localClass.getModifiers())) {
/* 732 */         AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Void run() {
/* 734 */             this.val$cons.setAccessible(true);
/* 735 */             return null;
/*     */           }
/*     */         });
/*     */       }
/* 739 */       return localConstructor.newInstance(new Object[] { paramInvocationHandler });
/*     */     } catch (IllegalAccessException|InstantiationException localIllegalAccessException) {
/* 741 */       throw new InternalError(localIllegalAccessException.toString(), localIllegalAccessException);
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 743 */       Object localObject = localInvocationTargetException.getCause();
/* 744 */       if ((localObject instanceof RuntimeException)) {
/* 745 */         throw ((RuntimeException)localObject);
/*     */       }
/* 747 */       throw new InternalError(((Throwable)localObject).toString(), (Throwable)localObject);
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {
/* 750 */       throw new InternalError(localNoSuchMethodException.toString(), localNoSuchMethodException);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkNewProxyPermission(Class<?> paramClass1, Class<?> paramClass2) {
/* 755 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 756 */     if ((localSecurityManager != null) && 
/* 757 */       (ReflectUtil.isNonPublicProxyClass(paramClass2))) {
/* 758 */       ClassLoader localClassLoader1 = paramClass1.getClassLoader();
/* 759 */       ClassLoader localClassLoader2 = paramClass2.getClassLoader();
/*     */       
/*     */ 
/*     */ 
/* 763 */       int i = paramClass2.getName().lastIndexOf('.');
/* 764 */       String str1 = i == -1 ? "" : paramClass2.getName().substring(0, i);
/*     */       
/* 766 */       i = paramClass1.getName().lastIndexOf('.');
/* 767 */       String str2 = i == -1 ? "" : paramClass1.getName().substring(0, i);
/*     */       
/* 769 */       if ((localClassLoader2 != localClassLoader1) || (!str1.equals(str2))) {
/* 770 */         localSecurityManager.checkPermission(new ReflectPermission("newProxyInPackage." + str1));
/*     */       }
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
/*     */   public static boolean isProxyClass(Class<?> paramClass)
/*     */   {
/* 791 */     return (Proxy.class.isAssignableFrom(paramClass)) && (proxyClassCache.containsValue(paramClass));
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
/*     */   @CallerSensitive
/*     */   public static InvocationHandler getInvocationHandler(Object paramObject)
/*     */     throws IllegalArgumentException
/*     */   {
/* 815 */     if (!isProxyClass(paramObject.getClass())) {
/* 816 */       throw new IllegalArgumentException("not a proxy instance");
/*     */     }
/*     */     
/* 819 */     Proxy localProxy = (Proxy)paramObject;
/* 820 */     InvocationHandler localInvocationHandler = localProxy.h;
/* 821 */     if (System.getSecurityManager() != null) {
/* 822 */       Class localClass1 = localInvocationHandler.getClass();
/* 823 */       Class localClass2 = Reflection.getCallerClass();
/* 824 */       if (ReflectUtil.needsPackageAccessCheck(localClass2.getClassLoader(), localClass1
/* 825 */         .getClassLoader()))
/*     */       {
/* 827 */         ReflectUtil.checkPackageAccess(localClass1);
/*     */       }
/*     */     }
/*     */     
/* 831 */     return localInvocationHandler;
/*     */   }
/*     */   
/*     */   private static native Class<?> defineClass0(ClassLoader paramClassLoader, String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Proxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */