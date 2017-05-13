/*     */ package java.net;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.SecureClassLoader;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.Attributes.Name;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.misc.JavaNetAccess;
/*     */ import sun.misc.PerfCounter;
/*     */ import sun.misc.Resource;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.misc.URLClassPath;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.net.www.protocol.file.FileURLConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLClassLoader
/*     */   extends SecureClassLoader
/*     */   implements Closeable
/*     */ {
/*     */   private final URLClassPath ucp;
/*     */   private final AccessControlContext acc;
/*     */   
/*     */   public URLClassLoader(URL[] paramArrayOfURL, ClassLoader paramClassLoader)
/*     */   {
/* 100 */     super(paramClassLoader);
/*     */     
/* 102 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 103 */     if (localSecurityManager != null) {
/* 104 */       localSecurityManager.checkCreateClassLoader();
/*     */     }
/* 106 */     this.ucp = new URLClassPath(paramArrayOfURL);
/* 107 */     this.acc = AccessController.getContext();
/*     */   }
/*     */   
/*     */   URLClassLoader(URL[] paramArrayOfURL, ClassLoader paramClassLoader, AccessControlContext paramAccessControlContext)
/*     */   {
/* 112 */     super(paramClassLoader);
/*     */     
/* 114 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 115 */     if (localSecurityManager != null) {
/* 116 */       localSecurityManager.checkCreateClassLoader();
/*     */     }
/* 118 */     this.ucp = new URLClassPath(paramArrayOfURL);
/* 119 */     this.acc = paramAccessControlContext;
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
/*     */   public URLClassLoader(URL[] paramArrayOfURL)
/*     */   {
/* 146 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 147 */     if (localSecurityManager != null) {
/* 148 */       localSecurityManager.checkCreateClassLoader();
/*     */     }
/* 150 */     this.ucp = new URLClassPath(paramArrayOfURL);
/* 151 */     this.acc = AccessController.getContext();
/*     */   }
/*     */   
/*     */ 
/*     */   URLClassLoader(URL[] paramArrayOfURL, AccessControlContext paramAccessControlContext)
/*     */   {
/* 157 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 158 */     if (localSecurityManager != null) {
/* 159 */       localSecurityManager.checkCreateClassLoader();
/*     */     }
/* 161 */     this.ucp = new URLClassPath(paramArrayOfURL);
/* 162 */     this.acc = paramAccessControlContext;
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
/*     */   public URLClassLoader(URL[] paramArrayOfURL, ClassLoader paramClassLoader, URLStreamHandlerFactory paramURLStreamHandlerFactory)
/*     */   {
/* 188 */     super(paramClassLoader);
/*     */     
/* 190 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 191 */     if (localSecurityManager != null) {
/* 192 */       localSecurityManager.checkCreateClassLoader();
/*     */     }
/* 194 */     this.ucp = new URLClassPath(paramArrayOfURL, paramURLStreamHandlerFactory);
/* 195 */     this.acc = AccessController.getContext();
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
/* 212 */   private WeakHashMap<Closeable, Void> closeables = new WeakHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputStream getResourceAsStream(String paramString)
/*     */   {
/* 232 */     URL localURL = getResource(paramString);
/*     */     try {
/* 234 */       if (localURL == null) {
/* 235 */         return null;
/*     */       }
/* 237 */       URLConnection localURLConnection = localURL.openConnection();
/* 238 */       InputStream localInputStream = localURLConnection.getInputStream();
/* 239 */       if ((localURLConnection instanceof JarURLConnection)) {
/* 240 */         JarURLConnection localJarURLConnection = (JarURLConnection)localURLConnection;
/* 241 */         JarFile localJarFile = localJarURLConnection.getJarFile();
/* 242 */         synchronized (this.closeables) {
/* 243 */           if (!this.closeables.containsKey(localJarFile)) {
/* 244 */             this.closeables.put(localJarFile, null);
/*     */           }
/*     */         }
/* 247 */       } else if ((localURLConnection instanceof FileURLConnection)) {
/* 248 */         synchronized (this.closeables) {
/* 249 */           this.closeables.put(localInputStream, null);
/*     */         }
/*     */       }
/* 252 */       return localInputStream;
/*     */     } catch (IOException localIOException) {}
/* 254 */     return null;
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 287 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 288 */     if (localSecurityManager != null) {
/* 289 */       localSecurityManager.checkPermission(new RuntimePermission("closeClassLoader"));
/*     */     }
/* 291 */     List localList = this.ucp.closeLoaders();
/*     */     
/*     */     Object localObject2;
/*     */     
/* 295 */     synchronized (this.closeables) {
/* 296 */       localObject1 = this.closeables.keySet();
/* 297 */       for (localObject2 = ((Set)localObject1).iterator(); ((Iterator)localObject2).hasNext();) { Closeable localCloseable = (Closeable)((Iterator)localObject2).next();
/*     */         try {
/* 299 */           localCloseable.close();
/*     */         } catch (IOException localIOException) {
/* 301 */           localList.add(localIOException);
/*     */         }
/*     */       }
/* 304 */       this.closeables.clear();
/*     */     }
/*     */     
/* 307 */     if (localList.isEmpty()) {
/* 308 */       return;
/*     */     }
/*     */     
/* 311 */     ??? = (IOException)localList.remove(0);
/*     */     
/*     */ 
/*     */ 
/* 315 */     for (Object localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (IOException)((Iterator)localObject1).next();
/* 316 */       ((IOException)???).addSuppressed((Throwable)localObject2);
/*     */     }
/* 318 */     throw ((Throwable)???);
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
/*     */   protected void addURL(URL paramURL)
/*     */   {
/* 332 */     this.ucp.addURL(paramURL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public URL[] getURLs()
/*     */   {
/* 342 */     return this.ucp.getURLs();
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
/*     */   protected Class<?> findClass(final String paramString)
/*     */     throws ClassNotFoundException
/*     */   {
/*     */     try
/*     */     {
/* 360 */       (Class)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Class<?> run() throws ClassNotFoundException {
/* 363 */           String str = paramString.replace('.', '/').concat(".class");
/* 364 */           Resource localResource = URLClassLoader.this.ucp.getResource(str, false);
/* 365 */           if (localResource != null) {
/*     */             try {
/* 367 */               return URLClassLoader.this.defineClass(paramString, localResource);
/*     */             } catch (IOException localIOException) {
/* 369 */               throw new ClassNotFoundException(paramString, localIOException);
/*     */             }
/*     */           }
/* 372 */           throw new ClassNotFoundException(paramString); } }, this.acc);
/*     */ 
/*     */     }
/*     */     catch (PrivilegedActionException localPrivilegedActionException)
/*     */     {
/* 377 */       throw ((ClassNotFoundException)localPrivilegedActionException.getException());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Package getAndVerifyPackage(String paramString, Manifest paramManifest, URL paramURL)
/*     */   {
/* 388 */     Package localPackage = getPackage(paramString);
/* 389 */     if (localPackage != null)
/*     */     {
/* 391 */       if (localPackage.isSealed())
/*     */       {
/* 393 */         if (!localPackage.isSealed(paramURL)) {
/* 394 */           throw new SecurityException("sealing violation: package " + paramString + " is sealed");
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 400 */       else if ((paramManifest != null) && (isSealed(paramString, paramManifest))) {
/* 401 */         throw new SecurityException("sealing violation: can't seal package " + paramString + ": already loaded");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 407 */     return localPackage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Class<?> defineClass(String paramString, Resource paramResource)
/*     */     throws IOException
/*     */   {
/* 416 */     long l = System.nanoTime();
/* 417 */     int i = paramString.lastIndexOf('.');
/* 418 */     URL localURL = paramResource.getCodeSourceURL();
/* 419 */     if (i != -1) {
/* 420 */       localObject1 = paramString.substring(0, i);
/*     */       
/* 422 */       localObject2 = paramResource.getManifest();
/* 423 */       if (getAndVerifyPackage((String)localObject1, (Manifest)localObject2, localURL) == null) {
/*     */         try {
/* 425 */           if (localObject2 != null) {
/* 426 */             definePackage((String)localObject1, (Manifest)localObject2, localURL);
/*     */           } else {
/* 428 */             definePackage((String)localObject1, null, null, null, null, null, null, null);
/*     */           }
/*     */         }
/*     */         catch (IllegalArgumentException localIllegalArgumentException)
/*     */         {
/* 433 */           if (getAndVerifyPackage((String)localObject1, (Manifest)localObject2, localURL) == null)
/*     */           {
/* 435 */             throw new AssertionError("Cannot find package " + (String)localObject1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 442 */     Object localObject1 = paramResource.getByteBuffer();
/* 443 */     if (localObject1 != null)
/*     */     {
/* 445 */       localObject2 = paramResource.getCodeSigners();
/* 446 */       localObject3 = new CodeSource(localURL, (CodeSigner[])localObject2);
/* 447 */       PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(l);
/* 448 */       return defineClass(paramString, (ByteBuffer)localObject1, (CodeSource)localObject3);
/*     */     }
/* 450 */     Object localObject2 = paramResource.getBytes();
/*     */     
/* 452 */     Object localObject3 = paramResource.getCodeSigners();
/* 453 */     CodeSource localCodeSource = new CodeSource(localURL, (CodeSigner[])localObject3);
/* 454 */     PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(l);
/* 455 */     return defineClass(paramString, (byte[])localObject2, 0, localObject2.length, localCodeSource);
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
/*     */   protected Package definePackage(String paramString, Manifest paramManifest, URL paramURL)
/*     */     throws IllegalArgumentException
/*     */   {
/* 477 */     String str1 = paramString.replace('.', '/').concat("/");
/* 478 */     String str2 = null;String str3 = null;String str4 = null;
/* 479 */     String str5 = null;String str6 = null;String str7 = null;
/* 480 */     String str8 = null;
/* 481 */     URL localURL = null;
/*     */     
/* 483 */     Attributes localAttributes = paramManifest.getAttributes(str1);
/* 484 */     if (localAttributes != null) {
/* 485 */       str2 = localAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/* 486 */       str3 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/* 487 */       str4 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/* 488 */       str5 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/* 489 */       str6 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/* 490 */       str7 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/* 491 */       str8 = localAttributes.getValue(Attributes.Name.SEALED);
/*     */     }
/* 493 */     localAttributes = paramManifest.getMainAttributes();
/* 494 */     if (localAttributes != null) {
/* 495 */       if (str2 == null) {
/* 496 */         str2 = localAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/*     */       }
/* 498 */       if (str3 == null) {
/* 499 */         str3 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/*     */       }
/* 501 */       if (str4 == null) {
/* 502 */         str4 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/*     */       }
/* 504 */       if (str5 == null) {
/* 505 */         str5 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/*     */       }
/* 507 */       if (str6 == null) {
/* 508 */         str6 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/*     */       }
/* 510 */       if (str7 == null) {
/* 511 */         str7 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/*     */       }
/* 513 */       if (str8 == null) {
/* 514 */         str8 = localAttributes.getValue(Attributes.Name.SEALED);
/*     */       }
/*     */     }
/* 517 */     if ("true".equalsIgnoreCase(str8)) {
/* 518 */       localURL = paramURL;
/*     */     }
/* 520 */     return definePackage(paramString, str2, str3, str4, str5, str6, str7, localURL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isSealed(String paramString, Manifest paramManifest)
/*     */   {
/* 529 */     String str1 = paramString.replace('.', '/').concat("/");
/* 530 */     Attributes localAttributes = paramManifest.getAttributes(str1);
/* 531 */     String str2 = null;
/* 532 */     if (localAttributes != null) {
/* 533 */       str2 = localAttributes.getValue(Attributes.Name.SEALED);
/*     */     }
/* 535 */     if ((str2 == null) && 
/* 536 */       ((localAttributes = paramManifest.getMainAttributes()) != null)) {
/* 537 */       str2 = localAttributes.getValue(Attributes.Name.SEALED);
/*     */     }
/*     */     
/* 540 */     return "true".equalsIgnoreCase(str2);
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
/*     */   public URL findResource(final String paramString)
/*     */   {
/* 554 */     URL localURL = (URL)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */ 
/* 557 */       public URL run() { return URLClassLoader.this.ucp.findResource(paramString, true); } }, this.acc);
/*     */     
/*     */ 
/*     */ 
/* 561 */     return localURL != null ? this.ucp.checkURL(localURL) : null;
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
/*     */   public Enumeration<URL> findResources(String paramString)
/*     */     throws IOException
/*     */   {
/* 576 */     final Enumeration localEnumeration = this.ucp.findResources(paramString, true);
/*     */     
/* 578 */     new Enumeration() {
/* 579 */       private URL url = null;
/*     */       
/*     */       private boolean next() {
/* 582 */         if (this.url != null) {
/* 583 */           return true;
/*     */         }
/*     */         do {
/* 586 */           URL localURL = (URL)AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public URL run() {
/* 589 */               if (!URLClassLoader.3.this.val$e.hasMoreElements())
/* 590 */                 return null;
/* 591 */               return (URL)URLClassLoader.3.this.val$e.nextElement();
/*     */             }
/* 593 */           }, URLClassLoader.this.acc);
/* 594 */           if (localURL == null)
/*     */             break;
/* 596 */           this.url = URLClassLoader.this.ucp.checkURL(localURL);
/* 597 */         } while (this.url == null);
/* 598 */         return this.url != null;
/*     */       }
/*     */       
/*     */       public URL nextElement() {
/* 602 */         if (!next()) {
/* 603 */           throw new NoSuchElementException();
/*     */         }
/* 605 */         URL localURL = this.url;
/* 606 */         this.url = null;
/* 607 */         return localURL;
/*     */       }
/*     */       
/*     */       public boolean hasMoreElements() {
/* 611 */         return next();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected PermissionCollection getPermissions(CodeSource paramCodeSource)
/*     */   {
/* 642 */     PermissionCollection localPermissionCollection = super.getPermissions(paramCodeSource);
/*     */     
/* 644 */     URL localURL = paramCodeSource.getLocation();
/*     */     
/*     */     URLConnection localURLConnection;
/*     */     Object localObject1;
/*     */     try
/*     */     {
/* 650 */       localURLConnection = localURL.openConnection();
/* 651 */       localObject1 = localURLConnection.getPermission();
/*     */     } catch (IOException localIOException) {
/* 653 */       localObject1 = null;
/* 654 */       localURLConnection = null; }
/*     */     final Object localObject2;
/*     */     final Object localObject3;
/* 657 */     if ((localObject1 instanceof FilePermission))
/*     */     {
/*     */ 
/*     */ 
/* 661 */       localObject2 = ((Permission)localObject1).getName();
/* 662 */       if (((String)localObject2).endsWith(File.separator)) {
/* 663 */         localObject2 = (String)localObject2 + "-";
/* 664 */         localObject1 = new FilePermission((String)localObject2, "read");
/*     */       }
/* 666 */     } else if ((localObject1 == null) && (localURL.getProtocol().equals("file"))) {
/* 667 */       localObject2 = localURL.getFile().replace('/', File.separatorChar);
/* 668 */       localObject2 = ParseUtil.decode((String)localObject2);
/* 669 */       if (((String)localObject2).endsWith(File.separator))
/* 670 */         localObject2 = (String)localObject2 + "-";
/* 671 */       localObject1 = new FilePermission((String)localObject2, "read");
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 678 */       localObject2 = localURL;
/* 679 */       if ((localURLConnection instanceof JarURLConnection)) {
/* 680 */         localObject2 = ((JarURLConnection)localURLConnection).getJarFileURL();
/*     */       }
/* 682 */       localObject3 = ((URL)localObject2).getHost();
/* 683 */       if ((localObject3 != null) && (((String)localObject3).length() > 0)) {
/* 684 */         localObject1 = new SocketPermission((String)localObject3, "connect,accept");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 691 */     if (localObject1 != null) {
/* 692 */       localObject2 = System.getSecurityManager();
/* 693 */       if (localObject2 != null) {
/* 694 */         localObject3 = localObject1;
/* 695 */         AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Void run() throws SecurityException {
/* 697 */             localObject2.checkPermission(localObject3);
/* 698 */             return null; } }, this.acc);
/*     */       }
/*     */       
/*     */ 
/* 702 */       localPermissionCollection.add((Permission)localObject1);
/*     */     }
/* 704 */     return localPermissionCollection;
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
/*     */   public static URLClassLoader newInstance(URL[] paramArrayOfURL, final ClassLoader paramClassLoader)
/*     */   {
/* 723 */     final AccessControlContext localAccessControlContext = AccessController.getContext();
/*     */     
/* 725 */     URLClassLoader localURLClassLoader = (URLClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public URLClassLoader run() {
/* 728 */         return new FactoryURLClassLoader(this.val$urls, paramClassLoader, localAccessControlContext);
/*     */       }
/* 730 */     });
/* 731 */     return localURLClassLoader;
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
/*     */   public static URLClassLoader newInstance(URL[] paramArrayOfURL)
/*     */   {
/* 748 */     final AccessControlContext localAccessControlContext = AccessController.getContext();
/*     */     
/* 750 */     URLClassLoader localURLClassLoader = (URLClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public URLClassLoader run() {
/* 753 */         return new FactoryURLClassLoader(this.val$urls, localAccessControlContext);
/*     */       }
/* 755 */     });
/* 756 */     return localURLClassLoader;
/*     */   }
/*     */   
/*     */   static {
/* 760 */     SharedSecrets.setJavaNetAccess(new JavaNetAccess()
/*     */     {
/*     */       public URLClassPath getURLClassPath(URLClassLoader paramAnonymousURLClassLoader) {
/* 763 */         return paramAnonymousURLClassLoader.ucp;
/*     */       }
/*     */       
/* 766 */     });
/* 767 */     ClassLoader.registerAsParallelCapable();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/URLClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */