/*     */ package java.rmi.server;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.rmi.server.LoaderHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RMIClassLoader
/*     */ {
/* 114 */   private static final RMIClassLoaderSpi defaultProvider = ;
/*     */   
/*     */ 
/*     */ 
/* 118 */   private static final RMIClassLoaderSpi provider = (RMIClassLoaderSpi)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public RMIClassLoaderSpi run() {
/* 120 */       return RMIClassLoader.access$000();
/*     */     }
/* 118 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Class<?> loadClass(String paramString)
/*     */     throws MalformedURLException, ClassNotFoundException
/*     */   {
/* 152 */     return loadClass((String)null, paramString);
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
/*     */   public static Class<?> loadClass(URL paramURL, String paramString)
/*     */     throws MalformedURLException, ClassNotFoundException
/*     */   {
/* 186 */     return provider.loadClass(paramURL != null ? paramURL
/* 187 */       .toString() : null, paramString, null);
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
/*     */   public static Class<?> loadClass(String paramString1, String paramString2)
/*     */     throws MalformedURLException, ClassNotFoundException
/*     */   {
/* 219 */     return provider.loadClass(paramString1, paramString2, null);
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
/*     */   public static Class<?> loadClass(String paramString1, String paramString2, ClassLoader paramClassLoader)
/*     */     throws MalformedURLException, ClassNotFoundException
/*     */   {
/* 264 */     return provider.loadClass(paramString1, paramString2, paramClassLoader);
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
/*     */   public static Class<?> loadProxyClass(String paramString, String[] paramArrayOfString, ClassLoader paramClassLoader)
/*     */     throws ClassNotFoundException, MalformedURLException
/*     */   {
/* 311 */     return provider.loadProxyClass(paramString, paramArrayOfString, paramClassLoader);
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
/*     */   public static ClassLoader getClassLoader(String paramString)
/*     */     throws MalformedURLException, SecurityException
/*     */   {
/* 355 */     return provider.getClassLoader(paramString);
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
/*     */   public static String getClassAnnotation(Class<?> paramClass)
/*     */   {
/* 381 */     return provider.getClassAnnotation(paramClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static RMIClassLoaderSpi getDefaultProviderInstance()
/*     */   {
/* 604 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 605 */     if (localSecurityManager != null) {
/* 606 */       localSecurityManager.checkPermission(new RuntimePermission("setFactory"));
/*     */     }
/* 608 */     return defaultProvider;
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
/*     */   @Deprecated
/*     */   public static Object getSecurityContext(ClassLoader paramClassLoader)
/*     */   {
/* 625 */     return LoaderHandler.getSecurityContext(paramClassLoader);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static RMIClassLoaderSpi newDefaultProviderInstance()
/*     */   {
/* 632 */     new RMIClassLoaderSpi()
/*     */     {
/*     */       public Class<?> loadClass(String paramAnonymousString1, String paramAnonymousString2, ClassLoader paramAnonymousClassLoader)
/*     */         throws MalformedURLException, ClassNotFoundException
/*     */       {
/* 637 */         return LoaderHandler.loadClass(paramAnonymousString1, paramAnonymousString2, paramAnonymousClassLoader);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       public Class<?> loadProxyClass(String paramAnonymousString, String[] paramAnonymousArrayOfString, ClassLoader paramAnonymousClassLoader)
/*     */         throws MalformedURLException, ClassNotFoundException
/*     */       {
/* 646 */         return LoaderHandler.loadProxyClass(paramAnonymousString, paramAnonymousArrayOfString, paramAnonymousClassLoader);
/*     */       }
/*     */       
/*     */ 
/*     */       public ClassLoader getClassLoader(String paramAnonymousString)
/*     */         throws MalformedURLException
/*     */       {
/* 653 */         return LoaderHandler.getClassLoader(paramAnonymousString);
/*     */       }
/*     */       
/*     */       public String getClassAnnotation(Class<?> paramAnonymousClass) {
/* 657 */         return LoaderHandler.getClassAnnotation(paramAnonymousClass);
/*     */       }
/*     */     };
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
/*     */   private static RMIClassLoaderSpi initializeProvider()
/*     */   {
/* 672 */     String str = System.getProperty("java.rmi.server.RMIClassLoaderSpi");
/*     */     
/* 674 */     if (str != null) {
/* 675 */       if (str.equals("default")) {
/* 676 */         return defaultProvider;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 683 */         Class localClass = Class.forName(str, false, ClassLoader.getSystemClassLoader()).asSubclass(RMIClassLoaderSpi.class);
/* 684 */         return (RMIClassLoaderSpi)localClass.newInstance();
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException) {
/* 687 */         throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 689 */         throw new IllegalAccessError(localIllegalAccessException.getMessage());
/*     */       } catch (InstantiationException localInstantiationException) {
/* 691 */         throw new InstantiationError(localInstantiationException.getMessage());
/*     */       } catch (ClassCastException localClassCastException1) {
/* 693 */         LinkageError localLinkageError1 = new LinkageError("provider class not assignable to RMIClassLoaderSpi");
/*     */         
/* 695 */         localLinkageError1.initCause(localClassCastException1);
/* 696 */         throw localLinkageError1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 705 */     Iterator localIterator = ServiceLoader.load(RMIClassLoaderSpi.class, ClassLoader.getSystemClassLoader()).iterator();
/* 706 */     if (localIterator.hasNext()) {
/*     */       try {
/* 708 */         return (RMIClassLoaderSpi)localIterator.next();
/*     */       } catch (ClassCastException localClassCastException2) {
/* 710 */         LinkageError localLinkageError2 = new LinkageError("provider class not assignable to RMIClassLoaderSpi");
/*     */         
/* 712 */         localLinkageError2.initCause(localClassCastException2);
/* 713 */         throw localLinkageError2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 720 */     return defaultProvider;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/server/RMIClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */