/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.net.JarURLConnection;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.jar.JarEntry;
/*      */ import java.util.spi.ResourceBundleControlProvider;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.util.locale.BaseLocale;
/*      */ import sun.util.locale.LocaleObjectCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ResourceBundle
/*      */ {
/*      */   private static final int INITIAL_CACHE_SIZE = 32;
/*      */   private static final ResourceBundle NONEXISTENT_BUNDLE;
/*      */   private static final ConcurrentMap<CacheKey, BundleReference> cacheList;
/*      */   private static final ReferenceQueue<Object> referenceQueue;
/*      */   
/*      */   public String getBaseBundleName()
/*      */   {
/*  335 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  343 */   protected ResourceBundle parent = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  348 */   private Locale locale = null;
/*      */   private String name;
/*      */   private volatile boolean expired;
/*      */   private volatile CacheKey cacheKey;
/*      */   private volatile Set<String> keySet;
/*      */   private static final List<ResourceBundleControlProvider> providers;
/*      */   
/*      */   static
/*      */   {
/*  293 */     NONEXISTENT_BUNDLE = new ResourceBundle() {
/*  294 */       public Enumeration<String> getKeys() { return null; }
/*  295 */       protected Object handleGetObject(String paramAnonymousString) { return null; }
/*  296 */       public String toString() { return "NONEXISTENT_BUNDLE";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  311 */     };
/*  312 */     cacheList = new ConcurrentHashMap(32);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  318 */     referenceQueue = new ReferenceQueue();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  374 */     ArrayList localArrayList = null;
/*      */     
/*  376 */     ServiceLoader localServiceLoader = ServiceLoader.loadInstalled(ResourceBundleControlProvider.class);
/*  377 */     for (ResourceBundleControlProvider localResourceBundleControlProvider : localServiceLoader) {
/*  378 */       if (localArrayList == null) {
/*  379 */         localArrayList = new ArrayList();
/*      */       }
/*  381 */       localArrayList.add(localResourceBundleControlProvider);
/*      */     }
/*  383 */     providers = localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getString(String paramString)
/*      */   {
/*  407 */     return (String)getObject(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String[] getStringArray(String paramString)
/*      */   {
/*  424 */     return (String[])getObject(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Object getObject(String paramString)
/*      */   {
/*  441 */     Object localObject = handleGetObject(paramString);
/*  442 */     if (localObject == null) {
/*  443 */       if (this.parent != null) {
/*  444 */         localObject = this.parent.getObject(paramString);
/*      */       }
/*  446 */       if (localObject == null)
/*      */       {
/*      */ 
/*      */ 
/*  450 */         throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + paramString, getClass().getName(), paramString);
/*      */       }
/*      */     }
/*      */     
/*  454 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Locale getLocale()
/*      */   {
/*  465 */     return this.locale;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ClassLoader getLoader(Class<?> paramClass)
/*      */   {
/*  473 */     Object localObject = paramClass == null ? null : paramClass.getClassLoader();
/*  474 */     if (localObject == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  482 */       localObject = RBClassLoader.INSTANCE;
/*      */     }
/*  484 */     return (ClassLoader)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */   private static class RBClassLoader
/*      */     extends ClassLoader
/*      */   {
/*  491 */     private static final RBClassLoader INSTANCE = (RBClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public ResourceBundle.RBClassLoader run() {
/*  494 */         return new ResourceBundle.RBClassLoader(null);
/*      */       }
/*  491 */     });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  497 */     private static final ClassLoader loader = ClassLoader.getSystemClassLoader();
/*      */     
/*      */     public Class<?> loadClass(String paramString)
/*      */       throws ClassNotFoundException
/*      */     {
/*  502 */       if (loader != null) {
/*  503 */         return loader.loadClass(paramString);
/*      */       }
/*  505 */       return Class.forName(paramString);
/*      */     }
/*      */     
/*  508 */     public URL getResource(String paramString) { if (loader != null) {
/*  509 */         return loader.getResource(paramString);
/*      */       }
/*  511 */       return ClassLoader.getSystemResource(paramString);
/*      */     }
/*      */     
/*  514 */     public InputStream getResourceAsStream(String paramString) { if (loader != null) {
/*  515 */         return loader.getResourceAsStream(paramString);
/*      */       }
/*  517 */       return ClassLoader.getSystemResourceAsStream(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setParent(ResourceBundle paramResourceBundle)
/*      */   {
/*  529 */     assert (paramResourceBundle != NONEXISTENT_BUNDLE);
/*  530 */     this.parent = paramResourceBundle;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class CacheKey
/*      */     implements Cloneable
/*      */   {
/*      */     private String name;
/*      */     
/*      */ 
/*      */ 
/*      */     private Locale locale;
/*      */     
/*      */ 
/*      */ 
/*      */     private ResourceBundle.LoaderReference loaderRef;
/*      */     
/*      */ 
/*      */     private String format;
/*      */     
/*      */ 
/*      */     private volatile long loadTime;
/*      */     
/*      */ 
/*      */     private volatile long expirationTime;
/*      */     
/*      */ 
/*      */     private Throwable cause;
/*      */     
/*      */ 
/*      */     private int hashCodeCache;
/*      */     
/*      */ 
/*      */ 
/*      */     CacheKey(String paramString, Locale paramLocale, ClassLoader paramClassLoader)
/*      */     {
/*  568 */       this.name = paramString;
/*  569 */       this.locale = paramLocale;
/*  570 */       if (paramClassLoader == null) {
/*  571 */         this.loaderRef = null;
/*      */       } else {
/*  573 */         this.loaderRef = new ResourceBundle.LoaderReference(paramClassLoader, ResourceBundle.referenceQueue, this);
/*      */       }
/*  575 */       calculateHashCode();
/*      */     }
/*      */     
/*      */     String getName() {
/*  579 */       return this.name;
/*      */     }
/*      */     
/*      */     CacheKey setName(String paramString) {
/*  583 */       if (!this.name.equals(paramString)) {
/*  584 */         this.name = paramString;
/*  585 */         calculateHashCode();
/*      */       }
/*  587 */       return this;
/*      */     }
/*      */     
/*      */     Locale getLocale() {
/*  591 */       return this.locale;
/*      */     }
/*      */     
/*      */     CacheKey setLocale(Locale paramLocale) {
/*  595 */       if (!this.locale.equals(paramLocale)) {
/*  596 */         this.locale = paramLocale;
/*  597 */         calculateHashCode();
/*      */       }
/*  599 */       return this;
/*      */     }
/*      */     
/*      */     ClassLoader getLoader() {
/*  603 */       return this.loaderRef != null ? (ClassLoader)this.loaderRef.get() : null;
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/*  607 */       if (this == paramObject) {
/*  608 */         return true;
/*      */       }
/*      */       try {
/*  611 */         CacheKey localCacheKey = (CacheKey)paramObject;
/*      */         
/*  613 */         if (this.hashCodeCache != localCacheKey.hashCodeCache) {
/*  614 */           return false;
/*      */         }
/*      */         
/*  617 */         if (!this.name.equals(localCacheKey.name)) {
/*  618 */           return false;
/*      */         }
/*      */         
/*  621 */         if (!this.locale.equals(localCacheKey.locale)) {
/*  622 */           return false;
/*      */         }
/*      */         
/*  625 */         if (this.loaderRef == null) {
/*  626 */           return localCacheKey.loaderRef == null;
/*      */         }
/*  628 */         ClassLoader localClassLoader = (ClassLoader)this.loaderRef.get();
/*  629 */         if ((localCacheKey.loaderRef != null) && (localClassLoader != null)) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  634 */         return localClassLoader == localCacheKey.loaderRef.get();
/*      */       }
/*      */       catch (NullPointerException|ClassCastException localNullPointerException) {}
/*  637 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  641 */       return this.hashCodeCache;
/*      */     }
/*      */     
/*      */     private void calculateHashCode() {
/*  645 */       this.hashCodeCache = (this.name.hashCode() << 3);
/*  646 */       this.hashCodeCache ^= this.locale.hashCode();
/*  647 */       ClassLoader localClassLoader = getLoader();
/*  648 */       if (localClassLoader != null) {
/*  649 */         this.hashCodeCache ^= localClassLoader.hashCode();
/*      */       }
/*      */     }
/*      */     
/*      */     public Object clone() {
/*      */       try {
/*  655 */         CacheKey localCacheKey = (CacheKey)super.clone();
/*  656 */         if (this.loaderRef != null)
/*      */         {
/*  658 */           localCacheKey.loaderRef = new ResourceBundle.LoaderReference((ClassLoader)this.loaderRef.get(), ResourceBundle.referenceQueue, localCacheKey);
/*      */         }
/*      */         
/*  661 */         localCacheKey.cause = null;
/*  662 */         return localCacheKey;
/*      */       }
/*      */       catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  665 */         throw new InternalError(localCloneNotSupportedException);
/*      */       }
/*      */     }
/*      */     
/*      */     String getFormat() {
/*  670 */       return this.format;
/*      */     }
/*      */     
/*      */     void setFormat(String paramString) {
/*  674 */       this.format = paramString;
/*      */     }
/*      */     
/*      */     private void setCause(Throwable paramThrowable) {
/*  678 */       if (this.cause == null) {
/*  679 */         this.cause = paramThrowable;
/*      */ 
/*      */ 
/*      */       }
/*  683 */       else if ((this.cause instanceof ClassNotFoundException)) {
/*  684 */         this.cause = paramThrowable;
/*      */       }
/*      */     }
/*      */     
/*      */     private Throwable getCause()
/*      */     {
/*  690 */       return this.cause;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  694 */       String str = this.locale.toString();
/*  695 */       if (str.length() == 0) {
/*  696 */         if (this.locale.getVariant().length() != 0) {
/*  697 */           str = "__" + this.locale.getVariant();
/*      */         } else {
/*  699 */           str = "\"\"";
/*      */         }
/*      */       }
/*  702 */       return "CacheKey[" + this.name + ", lc=" + str + ", ldr=" + getLoader() + "(format=" + this.format + ")]";
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static abstract interface CacheKeyReference
/*      */   {
/*      */     public abstract ResourceBundle.CacheKey getCacheKey();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class LoaderReference
/*      */     extends WeakReference<ClassLoader>
/*      */     implements ResourceBundle.CacheKeyReference
/*      */   {
/*      */     private ResourceBundle.CacheKey cacheKey;
/*      */     
/*      */ 
/*      */ 
/*      */     LoaderReference(ClassLoader paramClassLoader, ReferenceQueue<Object> paramReferenceQueue, ResourceBundle.CacheKey paramCacheKey)
/*      */     {
/*  725 */       super(paramReferenceQueue);
/*  726 */       this.cacheKey = paramCacheKey;
/*      */     }
/*      */     
/*      */     public ResourceBundle.CacheKey getCacheKey() {
/*  730 */       return this.cacheKey;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class BundleReference
/*      */     extends SoftReference<ResourceBundle>
/*      */     implements ResourceBundle.CacheKeyReference
/*      */   {
/*      */     private ResourceBundle.CacheKey cacheKey;
/*      */     
/*      */     BundleReference(ResourceBundle paramResourceBundle, ReferenceQueue<Object> paramReferenceQueue, ResourceBundle.CacheKey paramCacheKey)
/*      */     {
/*  743 */       super(paramReferenceQueue);
/*  744 */       this.cacheKey = paramCacheKey;
/*      */     }
/*      */     
/*      */     public ResourceBundle.CacheKey getCacheKey() {
/*  748 */       return this.cacheKey;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString)
/*      */   {
/*  773 */     return getBundleImpl(paramString, Locale.getDefault(), 
/*  774 */       getLoader(Reflection.getCallerClass()), 
/*  775 */       getDefaultControl(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString, Control paramControl)
/*      */   {
/*  815 */     return getBundleImpl(paramString, Locale.getDefault(), 
/*  816 */       getLoader(Reflection.getCallerClass()), paramControl);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString, Locale paramLocale)
/*      */   {
/*  845 */     return getBundleImpl(paramString, paramLocale, 
/*  846 */       getLoader(Reflection.getCallerClass()), 
/*  847 */       getDefaultControl(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString, Locale paramLocale, Control paramControl)
/*      */   {
/*  890 */     return getBundleImpl(paramString, paramLocale, 
/*  891 */       getLoader(Reflection.getCallerClass()), paramControl);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ResourceBundle getBundle(String paramString, Locale paramLocale, ClassLoader paramClassLoader)
/*      */   {
/* 1079 */     if (paramClassLoader == null) {
/* 1080 */       throw new NullPointerException();
/*      */     }
/* 1082 */     return getBundleImpl(paramString, paramLocale, paramClassLoader, getDefaultControl(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ResourceBundle getBundle(String paramString, Locale paramLocale, ClassLoader paramClassLoader, Control paramControl)
/*      */   {
/* 1296 */     if ((paramClassLoader == null) || (paramControl == null)) {
/* 1297 */       throw new NullPointerException();
/*      */     }
/* 1299 */     return getBundleImpl(paramString, paramLocale, paramClassLoader, paramControl);
/*      */   }
/*      */   
/*      */   private static Control getDefaultControl(String paramString) {
/* 1303 */     if (providers != null) {
/* 1304 */       for (ResourceBundleControlProvider localResourceBundleControlProvider : providers) {
/* 1305 */         Control localControl = localResourceBundleControlProvider.getControl(paramString);
/* 1306 */         if (localControl != null) {
/* 1307 */           return localControl;
/*      */         }
/*      */       }
/*      */     }
/* 1311 */     return Control.INSTANCE;
/*      */   }
/*      */   
/*      */   private static ResourceBundle getBundleImpl(String paramString, Locale paramLocale, ClassLoader paramClassLoader, Control paramControl)
/*      */   {
/* 1316 */     if ((paramLocale == null) || (paramControl == null)) {
/* 1317 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1324 */     CacheKey localCacheKey = new CacheKey(paramString, paramLocale, paramClassLoader);
/* 1325 */     Object localObject1 = null;
/*      */     
/*      */ 
/* 1328 */     BundleReference localBundleReference = (BundleReference)cacheList.get(localCacheKey);
/* 1329 */     if (localBundleReference != null) {
/* 1330 */       localObject1 = (ResourceBundle)localBundleReference.get();
/* 1331 */       localBundleReference = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1338 */     if ((isValidBundle((ResourceBundle)localObject1)) && (hasValidParentChain((ResourceBundle)localObject1))) {
/* 1339 */       return (ResourceBundle)localObject1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1345 */     int i = (paramControl == Control.INSTANCE) || ((paramControl instanceof SingleFormatControl)) ? 1 : 0;
/*      */     
/* 1347 */     List localList1 = paramControl.getFormats(paramString);
/* 1348 */     if ((i == 0) && (!checkList(localList1))) {
/* 1349 */       throw new IllegalArgumentException("Invalid Control: getFormats");
/*      */     }
/*      */     
/* 1352 */     Object localObject2 = null;
/* 1353 */     for (Locale localLocale = paramLocale; 
/* 1354 */         localLocale != null; 
/* 1355 */         localLocale = paramControl.getFallbackLocale(paramString, localLocale)) {
/* 1356 */       List localList2 = paramControl.getCandidateLocales(paramString, localLocale);
/* 1357 */       if ((i == 0) && (!checkList(localList2))) {
/* 1358 */         throw new IllegalArgumentException("Invalid Control: getCandidateLocales");
/*      */       }
/*      */       
/* 1361 */       localObject1 = findBundle(localCacheKey, localList2, localList1, 0, paramControl, (ResourceBundle)localObject2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1368 */       if (isValidBundle((ResourceBundle)localObject1)) {
/* 1369 */         boolean bool = Locale.ROOT.equals(((ResourceBundle)localObject1).locale);
/* 1370 */         if ((!bool) || (((ResourceBundle)localObject1).locale.equals(paramLocale)) || (
/* 1371 */           (localList2.size() == 1) && 
/* 1372 */           (((ResourceBundle)localObject1).locale.equals(localList2.get(0))))) {
/*      */           break;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1379 */         if ((bool) && (localObject2 == null)) {
/* 1380 */           localObject2 = localObject1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1385 */     if (localObject1 == null) {
/* 1386 */       if (localObject2 == null) {
/* 1387 */         throwMissingResourceException(paramString, paramLocale, localCacheKey.getCause());
/*      */       }
/* 1389 */       localObject1 = localObject2;
/*      */     }
/*      */     
/* 1392 */     return (ResourceBundle)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean checkList(List<?> paramList)
/*      */   {
/* 1400 */     boolean bool = (paramList != null) && (!paramList.isEmpty());
/* 1401 */     if (bool) {
/* 1402 */       int i = paramList.size();
/* 1403 */       for (int j = 0; (bool) && (j < i); j++) {
/* 1404 */         bool = paramList.get(j) != null;
/*      */       }
/*      */     }
/* 1407 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ResourceBundle findBundle(CacheKey paramCacheKey, List<Locale> paramList, List<String> paramList1, int paramInt, Control paramControl, ResourceBundle paramResourceBundle)
/*      */   {
/* 1416 */     Locale localLocale = (Locale)paramList.get(paramInt);
/* 1417 */     ResourceBundle localResourceBundle1 = null;
/* 1418 */     if (paramInt != paramList.size() - 1) {
/* 1419 */       localResourceBundle1 = findBundle(paramCacheKey, paramList, paramList1, paramInt + 1, paramControl, paramResourceBundle);
/*      */     }
/* 1421 */     else if ((paramResourceBundle != null) && (Locale.ROOT.equals(localLocale))) {
/* 1422 */       return paramResourceBundle;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     Reference localReference;
/*      */     
/*      */ 
/* 1430 */     while ((localReference = referenceQueue.poll()) != null) {
/* 1431 */       cacheList.remove(((CacheKeyReference)localReference).getCacheKey());
/*      */     }
/*      */     
/*      */ 
/* 1435 */     boolean bool = false;
/*      */     
/*      */ 
/*      */ 
/* 1439 */     paramCacheKey.setLocale(localLocale);
/* 1440 */     ResourceBundle localResourceBundle2 = findBundleInCache(paramCacheKey, paramControl);
/* 1441 */     Object localObject1; if (isValidBundle(localResourceBundle2)) {
/* 1442 */       bool = localResourceBundle2.expired;
/* 1443 */       if (!bool)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1449 */         if (localResourceBundle2.parent == localResourceBundle1) {
/* 1450 */           return localResourceBundle2;
/*      */         }
/*      */         
/*      */ 
/* 1454 */         localObject1 = (BundleReference)cacheList.get(paramCacheKey);
/* 1455 */         if ((localObject1 != null) && (((BundleReference)localObject1).get() == localResourceBundle2)) {
/* 1456 */           cacheList.remove(paramCacheKey, localObject1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1461 */     if (localResourceBundle2 != NONEXISTENT_BUNDLE) {
/* 1462 */       localObject1 = (CacheKey)paramCacheKey.clone();
/*      */       try
/*      */       {
/* 1465 */         localResourceBundle2 = loadBundle(paramCacheKey, paramList1, paramControl, bool);
/* 1466 */         if (localResourceBundle2 != null) {
/* 1467 */           if (localResourceBundle2.parent == null) {
/* 1468 */             localResourceBundle2.setParent(localResourceBundle1);
/*      */           }
/* 1470 */           localResourceBundle2.locale = localLocale;
/* 1471 */           localResourceBundle2 = putBundleInCache(paramCacheKey, localResourceBundle2, paramControl);
/* 1472 */           return localResourceBundle2;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1477 */         putBundleInCache(paramCacheKey, NONEXISTENT_BUNDLE, paramControl);
/*      */       } finally {
/* 1479 */         if ((((CacheKey)localObject1).getCause() instanceof InterruptedException)) {
/* 1480 */           Thread.currentThread().interrupt();
/*      */         }
/*      */       }
/*      */     }
/* 1484 */     return localResourceBundle1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ResourceBundle loadBundle(CacheKey paramCacheKey, List<String> paramList, Control paramControl, boolean paramBoolean)
/*      */   {
/* 1494 */     Locale localLocale = paramCacheKey.getLocale();
/*      */     
/* 1496 */     ResourceBundle localResourceBundle = null;
/* 1497 */     int i = paramList.size();
/* 1498 */     for (int j = 0; j < i; j++) {
/* 1499 */       String str = (String)paramList.get(j);
/*      */       try {
/* 1501 */         localResourceBundle = paramControl.newBundle(paramCacheKey.getName(), localLocale, str, paramCacheKey
/* 1502 */           .getLoader(), paramBoolean);
/*      */ 
/*      */       }
/*      */       catch (LinkageError localLinkageError)
/*      */       {
/* 1507 */         paramCacheKey.setCause(localLinkageError);
/*      */       } catch (Exception localException) {
/* 1509 */         paramCacheKey.setCause(localException);
/*      */       }
/* 1511 */       if (localResourceBundle != null)
/*      */       {
/*      */ 
/* 1514 */         paramCacheKey.setFormat(str);
/* 1515 */         localResourceBundle.name = paramCacheKey.getName();
/* 1516 */         localResourceBundle.locale = localLocale;
/*      */         
/*      */ 
/* 1519 */         localResourceBundle.expired = false;
/* 1520 */         break;
/*      */       }
/*      */     }
/*      */     
/* 1524 */     return localResourceBundle;
/*      */   }
/*      */   
/*      */   private static boolean isValidBundle(ResourceBundle paramResourceBundle) {
/* 1528 */     return (paramResourceBundle != null) && (paramResourceBundle != NONEXISTENT_BUNDLE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean hasValidParentChain(ResourceBundle paramResourceBundle)
/*      */   {
/* 1536 */     long l1 = System.currentTimeMillis();
/* 1537 */     while (paramResourceBundle != null) {
/* 1538 */       if (paramResourceBundle.expired) {
/* 1539 */         return false;
/*      */       }
/* 1541 */       CacheKey localCacheKey = paramResourceBundle.cacheKey;
/* 1542 */       if (localCacheKey != null) {
/* 1543 */         long l2 = localCacheKey.expirationTime;
/* 1544 */         if ((l2 >= 0L) && (l2 <= l1)) {
/* 1545 */           return false;
/*      */         }
/*      */       }
/* 1548 */       paramResourceBundle = paramResourceBundle.parent;
/*      */     }
/* 1550 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void throwMissingResourceException(String paramString, Locale paramLocale, Throwable paramThrowable)
/*      */   {
/* 1561 */     if ((paramThrowable instanceof MissingResourceException)) {
/* 1562 */       paramThrowable = null;
/*      */     }
/* 1564 */     throw new MissingResourceException("Can't find bundle for base name " + paramString + ", locale " + paramLocale, paramString + "_" + paramLocale, "", paramThrowable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ResourceBundle findBundleInCache(CacheKey paramCacheKey, Control paramControl)
/*      */   {
/* 1583 */     BundleReference localBundleReference = (BundleReference)cacheList.get(paramCacheKey);
/* 1584 */     if (localBundleReference == null) {
/* 1585 */       return null;
/*      */     }
/* 1587 */     ResourceBundle localResourceBundle1 = (ResourceBundle)localBundleReference.get();
/* 1588 */     if (localResourceBundle1 == null) {
/* 1589 */       return null;
/*      */     }
/* 1591 */     ResourceBundle localResourceBundle2 = localResourceBundle1.parent;
/* 1592 */     assert (localResourceBundle2 != NONEXISTENT_BUNDLE);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1626 */     if ((localResourceBundle2 != null) && (localResourceBundle2.expired)) {
/* 1627 */       assert (localResourceBundle1 != NONEXISTENT_BUNDLE);
/* 1628 */       localResourceBundle1.expired = true;
/* 1629 */       localResourceBundle1.cacheKey = null;
/* 1630 */       cacheList.remove(paramCacheKey, localBundleReference);
/* 1631 */       localResourceBundle1 = null;
/*      */     } else {
/* 1633 */       CacheKey localCacheKey = localBundleReference.getCacheKey();
/* 1634 */       long l = localCacheKey.expirationTime;
/* 1635 */       if ((!localResourceBundle1.expired) && (l >= 0L) && 
/* 1636 */         (l <= System.currentTimeMillis()))
/*      */       {
/* 1638 */         if (localResourceBundle1 != NONEXISTENT_BUNDLE)
/*      */         {
/*      */ 
/* 1641 */           synchronized (localResourceBundle1) {
/* 1642 */             l = localCacheKey.expirationTime;
/* 1643 */             if ((!localResourceBundle1.expired) && (l >= 0L) && 
/* 1644 */               (l <= System.currentTimeMillis())) {
/*      */               try {
/* 1646 */                 localResourceBundle1.expired = paramControl.needsReload(localCacheKey.getName(), localCacheKey
/* 1647 */                   .getLocale(), localCacheKey
/* 1648 */                   .getFormat(), localCacheKey
/* 1649 */                   .getLoader(), localResourceBundle1, 
/*      */                   
/* 1651 */                   localCacheKey.loadTime);
/*      */               } catch (Exception localException) {
/* 1653 */                 paramCacheKey.setCause(localException);
/*      */               }
/* 1655 */               if (localResourceBundle1.expired)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/* 1660 */                 localResourceBundle1.cacheKey = null;
/* 1661 */                 cacheList.remove(paramCacheKey, localBundleReference);
/*      */               }
/*      */               else
/*      */               {
/* 1665 */                 setExpirationTime(localCacheKey, paramControl);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1671 */           cacheList.remove(paramCacheKey, localBundleReference);
/* 1672 */           localResourceBundle1 = null;
/*      */         }
/*      */       }
/*      */     }
/* 1676 */     return localResourceBundle1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ResourceBundle putBundleInCache(CacheKey paramCacheKey, ResourceBundle paramResourceBundle, Control paramControl)
/*      */   {
/* 1691 */     setExpirationTime(paramCacheKey, paramControl);
/* 1692 */     if (paramCacheKey.expirationTime != -1L) {
/* 1693 */       CacheKey localCacheKey = (CacheKey)paramCacheKey.clone();
/* 1694 */       BundleReference localBundleReference1 = new BundleReference(paramResourceBundle, referenceQueue, localCacheKey);
/* 1695 */       paramResourceBundle.cacheKey = localCacheKey;
/*      */       
/*      */ 
/* 1698 */       BundleReference localBundleReference2 = (BundleReference)cacheList.putIfAbsent(localCacheKey, localBundleReference1);
/*      */       
/*      */ 
/*      */ 
/* 1702 */       if (localBundleReference2 != null) {
/* 1703 */         ResourceBundle localResourceBundle = (ResourceBundle)localBundleReference2.get();
/* 1704 */         if ((localResourceBundle != null) && (!localResourceBundle.expired))
/*      */         {
/* 1706 */           paramResourceBundle.cacheKey = null;
/* 1707 */           paramResourceBundle = localResourceBundle;
/*      */           
/*      */ 
/* 1710 */           localBundleReference1.clear();
/*      */         }
/*      */         else
/*      */         {
/* 1714 */           cacheList.put(localCacheKey, localBundleReference1);
/*      */         }
/*      */       }
/*      */     }
/* 1718 */     return paramResourceBundle;
/*      */   }
/*      */   
/*      */   private static void setExpirationTime(CacheKey paramCacheKey, Control paramControl) {
/* 1722 */     long l1 = paramControl.getTimeToLive(paramCacheKey.getName(), paramCacheKey
/* 1723 */       .getLocale());
/* 1724 */     if (l1 >= 0L)
/*      */     {
/*      */ 
/* 1727 */       long l2 = System.currentTimeMillis();
/* 1728 */       paramCacheKey.loadTime = l2;
/* 1729 */       paramCacheKey.expirationTime = (l2 + l1);
/* 1730 */     } else if (l1 >= -2L) {
/* 1731 */       paramCacheKey.expirationTime = l1;
/*      */     } else {
/* 1733 */       throw new IllegalArgumentException("Invalid Control: TTL=" + l1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public static final void clearCache()
/*      */   {
/* 1746 */     clearCache(getLoader(Reflection.getCallerClass()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final void clearCache(ClassLoader paramClassLoader)
/*      */   {
/* 1759 */     if (paramClassLoader == null) {
/* 1760 */       throw new NullPointerException();
/*      */     }
/* 1762 */     Set localSet = cacheList.keySet();
/* 1763 */     for (CacheKey localCacheKey : localSet) {
/* 1764 */       if (localCacheKey.getLoader() == paramClassLoader) {
/* 1765 */         localSet.remove(localCacheKey);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsKey(String paramString)
/*      */   {
/* 1803 */     if (paramString == null) {
/* 1804 */       throw new NullPointerException();
/*      */     }
/* 1806 */     for (ResourceBundle localResourceBundle = this; localResourceBundle != null; localResourceBundle = localResourceBundle.parent) {
/* 1807 */       if (localResourceBundle.handleKeySet().contains(paramString)) {
/* 1808 */         return true;
/*      */       }
/*      */     }
/* 1811 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<String> keySet()
/*      */   {
/* 1823 */     HashSet localHashSet = new HashSet();
/* 1824 */     for (ResourceBundle localResourceBundle = this; localResourceBundle != null; localResourceBundle = localResourceBundle.parent) {
/* 1825 */       localHashSet.addAll(localResourceBundle.handleKeySet());
/*      */     }
/* 1827 */     return localHashSet;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Set<String> handleKeySet()
/*      */   {
/* 1848 */     if (this.keySet == null) {
/* 1849 */       synchronized (this) {
/* 1850 */         if (this.keySet == null) {
/* 1851 */           HashSet localHashSet = new HashSet();
/* 1852 */           Enumeration localEnumeration = getKeys();
/* 1853 */           while (localEnumeration.hasMoreElements()) {
/* 1854 */             String str = (String)localEnumeration.nextElement();
/* 1855 */             if (handleGetObject(str) != null) {
/* 1856 */               localHashSet.add(str);
/*      */             }
/*      */           }
/* 1859 */           this.keySet = localHashSet;
/*      */         }
/*      */       }
/*      */     }
/* 1863 */     return this.keySet;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract Object handleGetObject(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Enumeration<String> getKeys();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class Control
/*      */   {
/* 2025 */     public static final List<String> FORMAT_DEFAULT = Collections.unmodifiableList(Arrays.asList(new String[] { "java.class", "java.properties" }));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2036 */     public static final List<String> FORMAT_CLASS = Collections.unmodifiableList(Arrays.asList(new String[] { "java.class" }));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2046 */     public static final List<String> FORMAT_PROPERTIES = Collections.unmodifiableList(Arrays.asList(new String[] { "java.properties" }));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final long TTL_DONT_CACHE = -1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final long TTL_NO_EXPIRATION_CONTROL = -2L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2064 */     private static final Control INSTANCE = new Control();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final Control getControl(List<String> paramList)
/*      */     {
/* 2097 */       if (paramList.equals(FORMAT_PROPERTIES)) {
/* 2098 */         return ResourceBundle.SingleFormatControl.PROPERTIES_ONLY;
/*      */       }
/* 2100 */       if (paramList.equals(FORMAT_CLASS)) {
/* 2101 */         return ResourceBundle.SingleFormatControl.CLASS_ONLY;
/*      */       }
/* 2103 */       if (paramList.equals(FORMAT_DEFAULT)) {
/* 2104 */         return INSTANCE;
/*      */       }
/* 2106 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final Control getNoFallbackControl(List<String> paramList)
/*      */     {
/* 2132 */       if (paramList.equals(FORMAT_DEFAULT)) {
/* 2133 */         return ResourceBundle.NoFallbackControl.NO_FALLBACK;
/*      */       }
/* 2135 */       if (paramList.equals(FORMAT_PROPERTIES)) {
/* 2136 */         return ResourceBundle.NoFallbackControl.PROPERTIES_ONLY_NO_FALLBACK;
/*      */       }
/* 2138 */       if (paramList.equals(FORMAT_CLASS)) {
/* 2139 */         return ResourceBundle.NoFallbackControl.CLASS_ONLY_NO_FALLBACK;
/*      */       }
/* 2141 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<String> getFormats(String paramString)
/*      */     {
/* 2179 */       if (paramString == null) {
/* 2180 */         throw new NullPointerException();
/*      */       }
/* 2182 */       return FORMAT_DEFAULT;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<Locale> getCandidateLocales(String paramString, Locale paramLocale)
/*      */     {
/* 2364 */       if (paramString == null) {
/* 2365 */         throw new NullPointerException();
/*      */       }
/* 2367 */       return new ArrayList((Collection)CANDIDATES_CACHE.get(paramLocale.getBaseLocale()));
/*      */     }
/*      */     
/* 2370 */     private static final CandidateListCache CANDIDATES_CACHE = new CandidateListCache(null);
/*      */     
/*      */     private static class CandidateListCache extends LocaleObjectCache<BaseLocale, List<Locale>> {
/*      */       protected List<Locale> createObject(BaseLocale paramBaseLocale) {
/* 2374 */         String str1 = paramBaseLocale.getLanguage();
/* 2375 */         String str2 = paramBaseLocale.getScript();
/* 2376 */         String str3 = paramBaseLocale.getRegion();
/* 2377 */         String str4 = paramBaseLocale.getVariant();
/*      */         
/*      */ 
/* 2380 */         int i = 0;
/* 2381 */         int j = 0;
/* 2382 */         if (str1.equals("no"))
/* 2383 */           if ((str3.equals("NO")) && (str4.equals("NY"))) {
/* 2384 */             str4 = "";
/* 2385 */             j = 1;
/*      */           } else {
/* 2387 */             i = 1;
/*      */           }
/*      */         Object localObject;
/* 2390 */         if ((str1.equals("nb")) || (i != 0)) {
/* 2391 */           localObject = getDefaultList("nb", str2, str3, str4);
/*      */           
/* 2393 */           LinkedList localLinkedList = new LinkedList();
/* 2394 */           for (Locale localLocale : (List)localObject) {
/* 2395 */             localLinkedList.add(localLocale);
/* 2396 */             if (localLocale.getLanguage().length() == 0) {
/*      */               break;
/*      */             }
/* 2399 */             localLinkedList.add(Locale.getInstance("no", localLocale.getScript(), localLocale.getCountry(), localLocale
/* 2400 */               .getVariant(), null));
/*      */           }
/* 2402 */           return localLinkedList; }
/* 2403 */         int k; if ((str1.equals("nn")) || (j != 0))
/*      */         {
/* 2405 */           localObject = getDefaultList("nn", str2, str3, str4);
/* 2406 */           k = ((List)localObject).size() - 1;
/* 2407 */           ((List)localObject).add(k++, Locale.getInstance("no", "NO", "NY"));
/* 2408 */           ((List)localObject).add(k++, Locale.getInstance("no", "NO", ""));
/* 2409 */           ((List)localObject).add(k++, Locale.getInstance("no", "", ""));
/* 2410 */           return (List<Locale>)localObject;
/*      */         }
/*      */         
/* 2413 */         if (str1.equals("zh")) {
/* 2414 */           if ((str2.length() == 0) && (str3.length() > 0))
/*      */           {
/*      */ 
/* 2417 */             localObject = str3;k = -1; switch (((String)localObject).hashCode()) {case 2691:  if (((String)localObject).equals("TW")) k = 0; break; case 2307:  if (((String)localObject).equals("HK")) k = 1; break; case 2466:  if (((String)localObject).equals("MO")) k = 2; break; case 2155:  if (((String)localObject).equals("CN")) k = 3; break; case 2644:  if (((String)localObject).equals("SG")) k = 4; break; } switch (k) {
/*      */             case 0: 
/*      */             case 1: 
/*      */             case 2: 
/* 2421 */               str2 = "Hant";
/* 2422 */               break;
/*      */             case 3: 
/*      */             case 4: 
/* 2425 */               str2 = "Hans";
/*      */             }
/*      */           }
/* 2428 */           else if ((str2.length() > 0) && (str3.length() == 0))
/*      */           {
/*      */ 
/* 2431 */             localObject = str2;k = -1; switch (((String)localObject).hashCode()) {case 2241694:  if (((String)localObject).equals("Hans")) k = 0; break; case 2241695:  if (((String)localObject).equals("Hant")) k = 1; break; } switch (k) {
/*      */             case 0: 
/* 2433 */               str3 = "CN";
/* 2434 */               break;
/*      */             case 1: 
/* 2436 */               str3 = "TW";
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         
/* 2442 */         return getDefaultList(str1, str2, str3, str4);
/*      */       }
/*      */       
/*      */       private static List<Locale> getDefaultList(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 2446 */         LinkedList localLinkedList1 = null;
/*      */         
/* 2448 */         if (paramString4.length() > 0) {
/* 2449 */           localLinkedList1 = new LinkedList();
/* 2450 */           int i = paramString4.length();
/* 2451 */           while (i != -1) {
/* 2452 */             localLinkedList1.add(paramString4.substring(0, i));
/* 2453 */             i = paramString4.lastIndexOf('_', --i);
/*      */           }
/*      */         }
/*      */         
/* 2457 */         LinkedList localLinkedList2 = new LinkedList();
/*      */         Iterator localIterator;
/* 2459 */         if (localLinkedList1 != null)
/* 2460 */           for (localIterator = localLinkedList1.iterator(); localIterator.hasNext();) { str = (String)localIterator.next();
/* 2461 */             localLinkedList2.add(Locale.getInstance(paramString1, paramString2, paramString3, str, null));
/*      */           }
/*      */         String str;
/* 2464 */         if (paramString3.length() > 0) {
/* 2465 */           localLinkedList2.add(Locale.getInstance(paramString1, paramString2, paramString3, "", null));
/*      */         }
/* 2467 */         if (paramString2.length() > 0) {
/* 2468 */           localLinkedList2.add(Locale.getInstance(paramString1, paramString2, "", "", null));
/*      */           
/*      */ 
/*      */ 
/* 2472 */           if (localLinkedList1 != null) {
/* 2473 */             for (localIterator = localLinkedList1.iterator(); localIterator.hasNext();) { str = (String)localIterator.next();
/* 2474 */               localLinkedList2.add(Locale.getInstance(paramString1, "", paramString3, str, null));
/*      */             }
/*      */           }
/* 2477 */           if (paramString3.length() > 0) {
/* 2478 */             localLinkedList2.add(Locale.getInstance(paramString1, "", paramString3, "", null));
/*      */           }
/*      */         }
/* 2481 */         if (paramString1.length() > 0) {
/* 2482 */           localLinkedList2.add(Locale.getInstance(paramString1, "", "", "", null));
/*      */         }
/*      */         
/* 2485 */         localLinkedList2.add(Locale.ROOT);
/*      */         
/* 2487 */         return localLinkedList2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Locale getFallbackLocale(String paramString, Locale paramLocale)
/*      */     {
/* 2528 */       if (paramString == null) {
/* 2529 */         throw new NullPointerException();
/*      */       }
/* 2531 */       Locale localLocale = Locale.getDefault();
/* 2532 */       return paramLocale.equals(localLocale) ? null : localLocale;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public ResourceBundle newBundle(String paramString1, Locale paramLocale, String paramString2, ClassLoader paramClassLoader, boolean paramBoolean)
/*      */       throws IllegalAccessException, InstantiationException, IOException
/*      */     {
/* 2634 */       String str1 = toBundleName(paramString1, paramLocale);
/* 2635 */       Object localObject1 = null;
/* 2636 */       if (paramString2.equals("java.class"))
/*      */       {
/*      */         try
/*      */         {
/* 2640 */           Class localClass = paramClassLoader.loadClass(str1);
/*      */           
/*      */ 
/*      */ 
/* 2644 */           if (ResourceBundle.class.isAssignableFrom(localClass)) {
/* 2645 */             localObject1 = (ResourceBundle)localClass.newInstance();
/*      */           } else {
/* 2647 */             throw new ClassCastException(localClass.getName() + " cannot be cast to ResourceBundle");
/*      */           }
/*      */         }
/*      */         catch (ClassNotFoundException localClassNotFoundException) {}
/*      */       }
/* 2652 */       else if (paramString2.equals("java.properties")) {
/* 2653 */         final String str2 = toResourceName0(str1, "properties");
/* 2654 */         if (str2 == null) {
/* 2655 */           return (ResourceBundle)localObject1;
/*      */         }
/* 2657 */         final ClassLoader localClassLoader = paramClassLoader;
/* 2658 */         final boolean bool = paramBoolean;
/* 2659 */         InputStream localInputStream = null;
/*      */         try {
/* 2661 */           localInputStream = (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */           {
/*      */             public InputStream run() throws IOException {
/* 2664 */               InputStream localInputStream = null;
/* 2665 */               if (bool) {
/* 2666 */                 URL localURL = localClassLoader.getResource(str2);
/* 2667 */                 if (localURL != null) {
/* 2668 */                   URLConnection localURLConnection = localURL.openConnection();
/* 2669 */                   if (localURLConnection != null)
/*      */                   {
/*      */ 
/* 2672 */                     localURLConnection.setUseCaches(false);
/* 2673 */                     localInputStream = localURLConnection.getInputStream();
/*      */                   }
/*      */                 }
/*      */               } else {
/* 2677 */                 localInputStream = localClassLoader.getResourceAsStream(str2);
/*      */               }
/* 2679 */               return localInputStream;
/*      */             }
/*      */           });
/*      */         } catch (PrivilegedActionException localPrivilegedActionException) {
/* 2683 */           throw ((IOException)localPrivilegedActionException.getException());
/*      */         }
/* 2685 */         if (localInputStream != null) {
/*      */           try {
/* 2687 */             localObject1 = new PropertyResourceBundle(localInputStream);
/*      */           } finally {
/* 2689 */             localInputStream.close();
/*      */           }
/*      */         }
/*      */       } else {
/* 2693 */         throw new IllegalArgumentException("unknown format: " + paramString2);
/*      */       }
/* 2695 */       return (ResourceBundle)localObject1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public long getTimeToLive(String paramString, Locale paramLocale)
/*      */     {
/* 2745 */       if ((paramString == null) || (paramLocale == null)) {
/* 2746 */         throw new NullPointerException();
/*      */       }
/* 2748 */       return -2L;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean needsReload(String paramString1, Locale paramLocale, String paramString2, ClassLoader paramClassLoader, ResourceBundle paramResourceBundle, long paramLong)
/*      */     {
/* 2802 */       if (paramResourceBundle == null) {
/* 2803 */         throw new NullPointerException();
/*      */       }
/* 2805 */       if ((paramString2.equals("java.class")) || (paramString2.equals("java.properties"))) {
/* 2806 */         paramString2 = paramString2.substring(5);
/*      */       }
/* 2808 */       boolean bool = false;
/*      */       try {
/* 2810 */         String str = toResourceName0(toBundleName(paramString1, paramLocale), paramString2);
/* 2811 */         if (str == null) {
/* 2812 */           return bool;
/*      */         }
/* 2814 */         URL localURL = paramClassLoader.getResource(str);
/* 2815 */         if (localURL != null) {
/* 2816 */           long l = 0L;
/* 2817 */           URLConnection localURLConnection = localURL.openConnection();
/* 2818 */           if (localURLConnection != null)
/*      */           {
/* 2820 */             localURLConnection.setUseCaches(false);
/* 2821 */             if ((localURLConnection instanceof JarURLConnection)) {
/* 2822 */               JarEntry localJarEntry = ((JarURLConnection)localURLConnection).getJarEntry();
/* 2823 */               if (localJarEntry != null) {
/* 2824 */                 l = localJarEntry.getTime();
/* 2825 */                 if (l == -1L) {
/* 2826 */                   l = 0L;
/*      */                 }
/*      */               }
/*      */             } else {
/* 2830 */               l = localURLConnection.getLastModified();
/*      */             }
/*      */           }
/* 2833 */           bool = l >= paramLong;
/*      */         }
/*      */       } catch (NullPointerException localNullPointerException) {
/* 2836 */         throw localNullPointerException;
/*      */       }
/*      */       catch (Exception localException) {}
/*      */       
/* 2840 */       return bool;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toBundleName(String paramString, Locale paramLocale)
/*      */     {
/* 2886 */       if (paramLocale == Locale.ROOT) {
/* 2887 */         return paramString;
/*      */       }
/*      */       
/* 2890 */       String str1 = paramLocale.getLanguage();
/* 2891 */       String str2 = paramLocale.getScript();
/* 2892 */       String str3 = paramLocale.getCountry();
/* 2893 */       String str4 = paramLocale.getVariant();
/*      */       
/* 2895 */       if ((str1 == "") && (str3 == "") && (str4 == "")) {
/* 2896 */         return paramString;
/*      */       }
/*      */       
/* 2899 */       StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 2900 */       localStringBuilder.append('_');
/* 2901 */       if (str2 != "") {
/* 2902 */         if (str4 != "") {
/* 2903 */           localStringBuilder.append(str1).append('_').append(str2).append('_').append(str3).append('_').append(str4);
/* 2904 */         } else if (str3 != "") {
/* 2905 */           localStringBuilder.append(str1).append('_').append(str2).append('_').append(str3);
/*      */         } else {
/* 2907 */           localStringBuilder.append(str1).append('_').append(str2);
/*      */         }
/*      */       }
/* 2910 */       else if (str4 != "") {
/* 2911 */         localStringBuilder.append(str1).append('_').append(str3).append('_').append(str4);
/* 2912 */       } else if (str3 != "") {
/* 2913 */         localStringBuilder.append(str1).append('_').append(str3);
/*      */       } else {
/* 2915 */         localStringBuilder.append(str1);
/*      */       }
/*      */       
/* 2918 */       return localStringBuilder.toString();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final String toResourceName(String paramString1, String paramString2)
/*      */     {
/* 2943 */       StringBuilder localStringBuilder = new StringBuilder(paramString1.length() + 1 + paramString2.length());
/* 2944 */       localStringBuilder.append(paramString1.replace('.', '/')).append('.').append(paramString2);
/* 2945 */       return localStringBuilder.toString();
/*      */     }
/*      */     
/*      */     private String toResourceName0(String paramString1, String paramString2)
/*      */     {
/* 2950 */       if (paramString1.contains("://")) {
/* 2951 */         return null;
/*      */       }
/* 2953 */       return toResourceName(paramString1, paramString2);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SingleFormatControl extends ResourceBundle.Control
/*      */   {
/* 2959 */     private static final ResourceBundle.Control PROPERTIES_ONLY = new SingleFormatControl(FORMAT_PROPERTIES);
/*      */     
/*      */ 
/* 2962 */     private static final ResourceBundle.Control CLASS_ONLY = new SingleFormatControl(FORMAT_CLASS);
/*      */     
/*      */     private final List<String> formats;
/*      */     
/*      */     protected SingleFormatControl(List<String> paramList)
/*      */     {
/* 2968 */       this.formats = paramList;
/*      */     }
/*      */     
/*      */     public List<String> getFormats(String paramString) {
/* 2972 */       if (paramString == null) {
/* 2973 */         throw new NullPointerException();
/*      */       }
/* 2975 */       return this.formats;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class NoFallbackControl extends ResourceBundle.SingleFormatControl {
/* 2980 */     private static final ResourceBundle.Control NO_FALLBACK = new NoFallbackControl(FORMAT_DEFAULT);
/*      */     
/*      */ 
/* 2983 */     private static final ResourceBundle.Control PROPERTIES_ONLY_NO_FALLBACK = new NoFallbackControl(FORMAT_PROPERTIES);
/*      */     
/*      */ 
/* 2986 */     private static final ResourceBundle.Control CLASS_ONLY_NO_FALLBACK = new NoFallbackControl(FORMAT_CLASS);
/*      */     
/*      */     protected NoFallbackControl(List<String> paramList)
/*      */     {
/* 2990 */       super();
/*      */     }
/*      */     
/*      */     public Locale getFallbackLocale(String paramString, Locale paramLocale) {
/* 2994 */       if ((paramString == null) || (paramLocale == null)) {
/* 2995 */         throw new NullPointerException();
/*      */       }
/* 2997 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */