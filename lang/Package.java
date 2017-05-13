/*     */ package java.lang;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.Attributes.Name;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Package
/*     */   implements AnnotatedElement
/*     */ {
/*     */   public String getName()
/*     */   {
/* 120 */     return this.pkgName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSpecificationTitle()
/*     */   {
/* 129 */     return this.specTitle;
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
/*     */   public String getSpecificationVersion()
/*     */   {
/* 142 */     return this.specVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSpecificationVendor()
/*     */   {
/* 152 */     return this.specVendor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getImplementationTitle()
/*     */   {
/* 160 */     return this.implTitle;
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
/*     */   public String getImplementationVersion()
/*     */   {
/* 173 */     return this.implVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getImplementationVendor()
/*     */   {
/* 182 */     return this.implVendor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSealed()
/*     */   {
/* 191 */     return this.sealBase != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSealed(URL paramURL)
/*     */   {
/* 202 */     return paramURL.equals(this.sealBase);
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
/*     */   public boolean isCompatibleWith(String paramString)
/*     */     throws NumberFormatException
/*     */   {
/* 230 */     if ((this.specVersion == null) || (this.specVersion.length() < 1)) {
/* 231 */       throw new NumberFormatException("Empty version string");
/*     */     }
/*     */     
/* 234 */     String[] arrayOfString1 = this.specVersion.split("\\.", -1);
/* 235 */     int[] arrayOfInt1 = new int[arrayOfString1.length];
/* 236 */     for (int i = 0; i < arrayOfString1.length; i++) {
/* 237 */       arrayOfInt1[i] = Integer.parseInt(arrayOfString1[i]);
/* 238 */       if (arrayOfInt1[i] < 0) {
/* 239 */         throw NumberFormatException.forInputString("" + arrayOfInt1[i]);
/*     */       }
/*     */     }
/* 242 */     String[] arrayOfString2 = paramString.split("\\.", -1);
/* 243 */     int[] arrayOfInt2 = new int[arrayOfString2.length];
/* 244 */     for (int j = 0; j < arrayOfString2.length; j++) {
/* 245 */       arrayOfInt2[j] = Integer.parseInt(arrayOfString2[j]);
/* 246 */       if (arrayOfInt2[j] < 0) {
/* 247 */         throw NumberFormatException.forInputString("" + arrayOfInt2[j]);
/*     */       }
/*     */     }
/* 250 */     j = Math.max(arrayOfInt2.length, arrayOfInt1.length);
/* 251 */     for (int k = 0; k < j; k++) {
/* 252 */       int m = k < arrayOfInt2.length ? arrayOfInt2[k] : 0;
/* 253 */       int n = k < arrayOfInt1.length ? arrayOfInt1[k] : 0;
/* 254 */       if (n < m)
/* 255 */         return false;
/* 256 */       if (n > m)
/* 257 */         return true;
/*     */     }
/* 259 */     return true;
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
/*     */   @CallerSensitive
/*     */   public static Package getPackage(String paramString)
/*     */   {
/* 280 */     ClassLoader localClassLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
/* 281 */     if (localClassLoader != null) {
/* 282 */       return localClassLoader.getPackage(paramString);
/*     */     }
/* 284 */     return getSystemPackage(paramString);
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
/*     */   @CallerSensitive
/*     */   public static Package[] getPackages()
/*     */   {
/* 302 */     ClassLoader localClassLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
/* 303 */     if (localClassLoader != null) {
/* 304 */       return localClassLoader.getPackages();
/*     */     }
/* 306 */     return getSystemPackages();
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
/*     */   static Package getPackage(Class<?> paramClass)
/*     */   {
/* 328 */     String str = paramClass.getName();
/* 329 */     int i = str.lastIndexOf('.');
/* 330 */     if (i != -1) {
/* 331 */       str = str.substring(0, i);
/* 332 */       ClassLoader localClassLoader = paramClass.getClassLoader();
/* 333 */       if (localClassLoader != null) {
/* 334 */         return localClassLoader.getPackage(str);
/*     */       }
/* 336 */       return getSystemPackage(str);
/*     */     }
/*     */     
/* 339 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 348 */     return this.pkgName.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 359 */     String str1 = this.specTitle;
/* 360 */     String str2 = this.specVersion;
/* 361 */     if ((str1 != null) && (str1.length() > 0)) {
/* 362 */       str1 = ", " + str1;
/*     */     } else
/* 364 */       str1 = "";
/* 365 */     if ((str2 != null) && (str2.length() > 0)) {
/* 366 */       str2 = ", version " + str2;
/*     */     } else
/* 368 */       str2 = "";
/* 369 */     return "package " + this.pkgName + str1 + str2;
/*     */   }
/*     */   
/*     */   private Class<?> getPackageInfo() {
/* 373 */     if (this.packageInfo == null) {
/*     */       try {
/* 375 */         this.packageInfo = Class.forName(this.pkgName + ".package-info", false, this.loader);
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException)
/*     */       {
/* 379 */         this.packageInfo = 1PackageInfoProxy.class;
/*     */       }
/*     */     }
/* 382 */     return this.packageInfo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/* 390 */     return getPackageInfo().getAnnotation(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAnnotationPresent(Class<? extends Annotation> paramClass)
/*     */   {
/* 400 */     return super.isAnnotationPresent(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A[] getAnnotationsByType(Class<A> paramClass)
/*     */   {
/* 409 */     return getPackageInfo().getAnnotationsByType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getAnnotations()
/*     */   {
/* 416 */     return getPackageInfo().getAnnotations();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A getDeclaredAnnotation(Class<A> paramClass)
/*     */   {
/* 425 */     return getPackageInfo().getDeclaredAnnotation(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> paramClass)
/*     */   {
/* 434 */     return getPackageInfo().getDeclaredAnnotationsByType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getDeclaredAnnotations()
/*     */   {
/* 441 */     return getPackageInfo().getDeclaredAnnotations();
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
/*     */   Package(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, URL paramURL, ClassLoader paramClassLoader)
/*     */   {
/* 460 */     this.pkgName = paramString1;
/* 461 */     this.implTitle = paramString5;
/* 462 */     this.implVersion = paramString6;
/* 463 */     this.implVendor = paramString7;
/* 464 */     this.specTitle = paramString2;
/* 465 */     this.specVersion = paramString3;
/* 466 */     this.specVendor = paramString4;
/* 467 */     this.sealBase = paramURL;
/* 468 */     this.loader = paramClassLoader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Package(String paramString, Manifest paramManifest, URL paramURL, ClassLoader paramClassLoader)
/*     */   {
/* 479 */     String str1 = paramString.replace('.', '/').concat("/");
/* 480 */     String str2 = null;
/* 481 */     String str3 = null;
/* 482 */     String str4 = null;
/* 483 */     String str5 = null;
/* 484 */     String str6 = null;
/* 485 */     String str7 = null;
/* 486 */     String str8 = null;
/* 487 */     URL localURL = null;
/* 488 */     Attributes localAttributes = paramManifest.getAttributes(str1);
/* 489 */     if (localAttributes != null) {
/* 490 */       str3 = localAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/* 491 */       str4 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/* 492 */       str5 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/* 493 */       str6 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/* 494 */       str7 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/* 495 */       str8 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/* 496 */       str2 = localAttributes.getValue(Attributes.Name.SEALED);
/*     */     }
/* 498 */     localAttributes = paramManifest.getMainAttributes();
/* 499 */     if (localAttributes != null) {
/* 500 */       if (str3 == null) {
/* 501 */         str3 = localAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/*     */       }
/* 503 */       if (str4 == null) {
/* 504 */         str4 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/*     */       }
/* 506 */       if (str5 == null) {
/* 507 */         str5 = localAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/*     */       }
/* 509 */       if (str6 == null) {
/* 510 */         str6 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/*     */       }
/* 512 */       if (str7 == null) {
/* 513 */         str7 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/*     */       }
/* 515 */       if (str8 == null) {
/* 516 */         str8 = localAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/*     */       }
/* 518 */       if (str2 == null) {
/* 519 */         str2 = localAttributes.getValue(Attributes.Name.SEALED);
/*     */       }
/*     */     }
/* 522 */     if ("true".equalsIgnoreCase(str2)) {
/* 523 */       localURL = paramURL;
/*     */     }
/* 525 */     this.pkgName = paramString;
/* 526 */     this.specTitle = str3;
/* 527 */     this.specVersion = str4;
/* 528 */     this.specVendor = str5;
/* 529 */     this.implTitle = str6;
/* 530 */     this.implVersion = str7;
/* 531 */     this.implVendor = str8;
/* 532 */     this.sealBase = localURL;
/* 533 */     this.loader = paramClassLoader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Package getSystemPackage(String paramString)
/*     */   {
/* 540 */     synchronized (pkgs) {
/* 541 */       Package localPackage = (Package)pkgs.get(paramString);
/* 542 */       if (localPackage == null) {
/* 543 */         paramString = paramString.replace('.', '/').concat("/");
/* 544 */         String str = getSystemPackage0(paramString);
/* 545 */         if (str != null) {
/* 546 */           localPackage = defineSystemPackage(paramString, str);
/*     */         }
/*     */       }
/* 549 */       return localPackage;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Package[] getSystemPackages()
/*     */   {
/* 558 */     String[] arrayOfString = getSystemPackages0();
/* 559 */     synchronized (pkgs) {
/* 560 */       for (int i = 0; i < arrayOfString.length; i++) {
/* 561 */         defineSystemPackage(arrayOfString[i], getSystemPackage0(arrayOfString[i]));
/*     */       }
/* 563 */       return (Package[])pkgs.values().toArray(new Package[pkgs.size()]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static Package defineSystemPackage(String paramString1, final String paramString2)
/*     */   {
/* 570 */     (Package)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Package run() {
/* 572 */         String str = this.val$iname;
/*     */         
/* 574 */         URL localURL = (URL)Package.urls.get(paramString2);
/* 575 */         Object localObject; if (localURL == null)
/*     */         {
/* 577 */           localObject = new File(paramString2);
/*     */           try {
/* 579 */             localURL = ParseUtil.fileToEncodedURL((File)localObject);
/*     */           }
/*     */           catch (MalformedURLException localMalformedURLException) {}
/* 582 */           if (localURL != null) {
/* 583 */             Package.urls.put(paramString2, localURL);
/*     */             
/* 585 */             if (((File)localObject).isFile()) {
/* 586 */               Package.mans.put(paramString2, Package.loadManifest(paramString2));
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 591 */         str = str.substring(0, str.length() - 1).replace('/', '.');
/*     */         
/* 593 */         Manifest localManifest = (Manifest)Package.mans.get(paramString2);
/* 594 */         if (localManifest != null) {
/* 595 */           localObject = new Package(str, localManifest, localURL, null, null);
/*     */         } else {
/* 597 */           localObject = new Package(str, null, null, null, null, null, null, null, null);
/*     */         }
/*     */         
/* 600 */         Package.pkgs.put(str, localObject);
/* 601 */         return (Package)localObject;
/*     */       }
/*     */     });
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
/* 620 */   private static Map<String, Package> pkgs = new HashMap(31);
/*     */   
/*     */ 
/* 623 */   private static Map<String, URL> urls = new HashMap(10);
/*     */   
/*     */ 
/* 626 */   private static Map<String, Manifest> mans = new HashMap(10);
/*     */   private final String pkgName;
/*     */   private final String specTitle;
/*     */   private final String specVersion;
/*     */   private final String specVendor;
/*     */   private final String implTitle;
/*     */   private final String implVersion;
/*     */   private final String implVendor;
/*     */   private final URL sealBase;
/*     */   private final transient ClassLoader loader;
/*     */   private transient Class<?> packageInfo;
/*     */   
/*     */   /* Error */
/*     */   private static Manifest loadManifest(String paramString)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: new 86	java/io/FileInputStream
/*     */     //   3: dup
/*     */     //   4: aload_0
/*     */     //   5: invokespecial 87	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   8: astore_1
/*     */     //   9: aconst_null
/*     */     //   10: astore_2
/*     */     //   11: new 88	java/util/jar/JarInputStream
/*     */     //   14: dup
/*     */     //   15: aload_1
/*     */     //   16: iconst_0
/*     */     //   17: invokespecial 89	java/util/jar/JarInputStream:<init>	(Ljava/io/InputStream;Z)V
/*     */     //   20: astore_3
/*     */     //   21: aconst_null
/*     */     //   22: astore 4
/*     */     //   24: aload_3
/*     */     //   25: invokevirtual 90	java/util/jar/JarInputStream:getManifest	()Ljava/util/jar/Manifest;
/*     */     //   28: astore 5
/*     */     //   30: aload_3
/*     */     //   31: ifnull +31 -> 62
/*     */     //   34: aload 4
/*     */     //   36: ifnull +22 -> 58
/*     */     //   39: aload_3
/*     */     //   40: invokevirtual 91	java/util/jar/JarInputStream:close	()V
/*     */     //   43: goto +19 -> 62
/*     */     //   46: astore 6
/*     */     //   48: aload 4
/*     */     //   50: aload 6
/*     */     //   52: invokevirtual 93	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   55: goto +7 -> 62
/*     */     //   58: aload_3
/*     */     //   59: invokevirtual 91	java/util/jar/JarInputStream:close	()V
/*     */     //   62: aload_1
/*     */     //   63: ifnull +29 -> 92
/*     */     //   66: aload_2
/*     */     //   67: ifnull +21 -> 88
/*     */     //   70: aload_1
/*     */     //   71: invokevirtual 94	java/io/FileInputStream:close	()V
/*     */     //   74: goto +18 -> 92
/*     */     //   77: astore 6
/*     */     //   79: aload_2
/*     */     //   80: aload 6
/*     */     //   82: invokevirtual 93	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   85: goto +7 -> 92
/*     */     //   88: aload_1
/*     */     //   89: invokevirtual 94	java/io/FileInputStream:close	()V
/*     */     //   92: aload 5
/*     */     //   94: areturn
/*     */     //   95: astore 5
/*     */     //   97: aload 5
/*     */     //   99: astore 4
/*     */     //   101: aload 5
/*     */     //   103: athrow
/*     */     //   104: astore 7
/*     */     //   106: aload_3
/*     */     //   107: ifnull +31 -> 138
/*     */     //   110: aload 4
/*     */     //   112: ifnull +22 -> 134
/*     */     //   115: aload_3
/*     */     //   116: invokevirtual 91	java/util/jar/JarInputStream:close	()V
/*     */     //   119: goto +19 -> 138
/*     */     //   122: astore 8
/*     */     //   124: aload 4
/*     */     //   126: aload 8
/*     */     //   128: invokevirtual 93	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   131: goto +7 -> 138
/*     */     //   134: aload_3
/*     */     //   135: invokevirtual 91	java/util/jar/JarInputStream:close	()V
/*     */     //   138: aload 7
/*     */     //   140: athrow
/*     */     //   141: astore_3
/*     */     //   142: aload_3
/*     */     //   143: astore_2
/*     */     //   144: aload_3
/*     */     //   145: athrow
/*     */     //   146: astore 9
/*     */     //   148: aload_1
/*     */     //   149: ifnull +29 -> 178
/*     */     //   152: aload_2
/*     */     //   153: ifnull +21 -> 174
/*     */     //   156: aload_1
/*     */     //   157: invokevirtual 94	java/io/FileInputStream:close	()V
/*     */     //   160: goto +18 -> 178
/*     */     //   163: astore 10
/*     */     //   165: aload_2
/*     */     //   166: aload 10
/*     */     //   168: invokevirtual 93	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   171: goto +7 -> 178
/*     */     //   174: aload_1
/*     */     //   175: invokevirtual 94	java/io/FileInputStream:close	()V
/*     */     //   178: aload 9
/*     */     //   180: athrow
/*     */     //   181: astore_1
/*     */     //   182: aconst_null
/*     */     //   183: areturn
/*     */     // Line number table:
/*     */     //   Java source line #610	-> byte code offset #0
/*     */     //   Java source line #611	-> byte code offset #11
/*     */     //   Java source line #610	-> byte code offset #21
/*     */     //   Java source line #613	-> byte code offset #24
/*     */     //   Java source line #614	-> byte code offset #30
/*     */     //   Java source line #610	-> byte code offset #95
/*     */     //   Java source line #614	-> byte code offset #104
/*     */     //   Java source line #610	-> byte code offset #141
/*     */     //   Java source line #614	-> byte code offset #146
/*     */     //   Java source line #615	-> byte code offset #182
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	184	0	paramString	String
/*     */     //   8	167	1	localFileInputStream	java.io.FileInputStream
/*     */     //   181	1	1	localIOException	java.io.IOException
/*     */     //   10	156	2	localObject1	Object
/*     */     //   20	115	3	localJarInputStream	java.util.jar.JarInputStream
/*     */     //   141	4	3	localThrowable1	Throwable
/*     */     //   22	103	4	localObject2	Object
/*     */     //   95	7	5	localThrowable2	Throwable
/*     */     //   46	5	6	localThrowable3	Throwable
/*     */     //   77	4	6	localThrowable4	Throwable
/*     */     //   104	35	7	localObject3	Object
/*     */     //   122	5	8	localThrowable5	Throwable
/*     */     //   146	33	9	localObject4	Object
/*     */     //   163	4	10	localThrowable6	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   39	43	46	java/lang/Throwable
/*     */     //   70	74	77	java/lang/Throwable
/*     */     //   24	30	95	java/lang/Throwable
/*     */     //   24	30	104	finally
/*     */     //   95	106	104	finally
/*     */     //   115	119	122	java/lang/Throwable
/*     */     //   11	62	141	java/lang/Throwable
/*     */     //   95	141	141	java/lang/Throwable
/*     */     //   11	62	146	finally
/*     */     //   95	148	146	finally
/*     */     //   156	160	163	java/lang/Throwable
/*     */     //   0	92	181	java/io/IOException
/*     */     //   95	181	181	java/io/IOException
/*     */   }
/*     */   
/*     */   private static native String getSystemPackage0(String paramString);
/*     */   
/*     */   private static native String[] getSystemPackages0();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Package.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */