/*      */ package java.security;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Field;
/*      */ import java.net.URL;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.security.jca.GetInstance;
/*      */ import sun.security.jca.GetInstance.Instance;
/*      */ import sun.security.jca.ProviderList;
/*      */ import sun.security.jca.Providers;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.PropertyExpander;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Security
/*      */ {
/*   53 */   private static final Debug sdebug = Debug.getInstance("properties");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Properties props;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*   69 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Void run() {
/*   71 */         Security.access$000();
/*   72 */         return null;
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private static void initialize() {
/*   78 */     props = new Properties();
/*   79 */     int i = 0;
/*   80 */     int j = 0;
/*      */     
/*      */ 
/*      */ 
/*   84 */     File localFile = securityPropFile("java.security");
/*   85 */     if (localFile.exists()) {
/*   86 */       localObject1 = null;
/*      */       try {
/*   88 */         FileInputStream localFileInputStream = new FileInputStream(localFile);
/*   89 */         localObject1 = new BufferedInputStream(localFileInputStream);
/*   90 */         props.load((InputStream)localObject1);
/*   91 */         i = 1;
/*      */         
/*   93 */         if (sdebug != null) {
/*   94 */           sdebug.println("reading security properties file: " + localFile);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  104 */         if (localObject1 != null) {
/*      */           try {
/*  106 */             ((InputStream)localObject1).close();
/*      */           } catch (IOException localIOException1) {
/*  108 */             if (sdebug != null) {
/*  109 */               sdebug.println("unable to close input stream");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  116 */         if (!"true".equalsIgnoreCase(props
/*  117 */           .getProperty("security.overridePropertiesFile"))) {
/*      */           break label566;
/*      */         }
/*      */       }
/*      */       catch (IOException localIOException2)
/*      */       {
/*   98 */         if (sdebug != null) {
/*   99 */           sdebug.println("unable to load security properties from " + localFile);
/*      */           
/*  101 */           localIOException2.printStackTrace();
/*      */         }
/*      */       } finally {
/*  104 */         if (localObject1 != null) {
/*      */           try {
/*  106 */             ((InputStream)localObject1).close();
/*      */           } catch (IOException localIOException6) {
/*  108 */             if (sdebug != null) {
/*  109 */               sdebug.println("unable to close input stream");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  120 */     Object localObject1 = System.getProperty("java.security.properties");
/*  121 */     if ((localObject1 != null) && (((String)localObject1).startsWith("="))) {
/*  122 */       j = 1;
/*  123 */       localObject1 = ((String)localObject1).substring(1);
/*      */     }
/*      */     
/*  126 */     if (j != 0) {
/*  127 */       props = new Properties();
/*  128 */       if (sdebug != null)
/*      */       {
/*  130 */         sdebug.println("overriding other security properties files!");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  136 */     if (localObject1 != null) {
/*  137 */       BufferedInputStream localBufferedInputStream = null;
/*      */       
/*      */       try
/*      */       {
/*  141 */         localObject1 = PropertyExpander.expand((String)localObject1);
/*  142 */         localFile = new File((String)localObject1);
/*  143 */         URL localURL; if (localFile.exists())
/*      */         {
/*  145 */           localURL = new URL("file:" + localFile.getCanonicalPath());
/*      */         } else {
/*  147 */           localURL = new URL((String)localObject1);
/*      */         }
/*  149 */         localBufferedInputStream = new BufferedInputStream(localURL.openStream());
/*  150 */         props.load(localBufferedInputStream);
/*  151 */         i = 1;
/*      */         
/*  153 */         if (sdebug != null) {
/*  154 */           sdebug.println("reading security properties file: " + localURL);
/*      */           
/*  156 */           if (j != 0)
/*      */           {
/*  158 */             sdebug.println("overriding other security properties files!");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  169 */         if (localBufferedInputStream != null) {
/*      */           try {
/*  171 */             localBufferedInputStream.close();
/*      */           } catch (IOException localIOException4) {
/*  173 */             if (sdebug != null) {
/*  174 */               sdebug.println("unable to close input stream");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  182 */         if (i != 0) {
/*      */           return;
/*      */         }
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*  162 */         if (sdebug != null)
/*      */         {
/*  164 */           sdebug.println("unable to load security properties from " + (String)localObject1);
/*      */           
/*  166 */           localException.printStackTrace();
/*      */         }
/*      */       } finally {
/*  169 */         if (localBufferedInputStream != null) {
/*      */           try {
/*  171 */             localBufferedInputStream.close();
/*      */           } catch (IOException localIOException7) {
/*  173 */             if (sdebug != null) {
/*  174 */               sdebug.println("unable to close input stream");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     label566:
/*      */     
/*  183 */     initializeStatic();
/*  184 */     if (sdebug != null) {
/*  185 */       sdebug.println("unable to load security properties -- using defaults");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void initializeStatic()
/*      */   {
/*  197 */     props.put("security.provider.1", "sun.security.provider.Sun");
/*  198 */     props.put("security.provider.2", "sun.security.rsa.SunRsaSign");
/*  199 */     props.put("security.provider.3", "com.sun.net.ssl.internal.ssl.Provider");
/*  200 */     props.put("security.provider.4", "com.sun.crypto.provider.SunJCE");
/*  201 */     props.put("security.provider.5", "sun.security.jgss.SunProvider");
/*  202 */     props.put("security.provider.6", "com.sun.security.sasl.Provider");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static File securityPropFile(String paramString)
/*      */   {
/*  214 */     String str = File.separator;
/*  215 */     return new File(System.getProperty("java.home") + str + "lib" + str + "security" + str + paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ProviderProperty getProviderProperty(String paramString)
/*      */   {
/*  227 */     ProviderProperty localProviderProperty = null;
/*      */     
/*  229 */     List localList = Providers.getProviderList().providers();
/*  230 */     for (int i = 0; i < localList.size(); i++)
/*      */     {
/*  232 */       String str1 = null;
/*  233 */       Provider localProvider = (Provider)localList.get(i);
/*  234 */       String str2 = localProvider.getProperty(paramString);
/*      */       Object localObject;
/*  236 */       if (str2 == null)
/*      */       {
/*      */ 
/*  239 */         localObject = localProvider.keys();
/*  240 */         while ((((Enumeration)localObject).hasMoreElements()) && (str2 == null)) {
/*  241 */           str1 = (String)((Enumeration)localObject).nextElement();
/*  242 */           if (paramString.equalsIgnoreCase(str1)) {
/*  243 */             str2 = localProvider.getProperty(str1);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  249 */       if (str2 != null) {
/*  250 */         localObject = new ProviderProperty(null);
/*  251 */         ((ProviderProperty)localObject).className = str2;
/*  252 */         ((ProviderProperty)localObject).provider = localProvider;
/*  253 */         return (ProviderProperty)localObject;
/*      */       }
/*      */     }
/*      */     
/*  257 */     return localProviderProperty;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String getProviderProperty(String paramString, Provider paramProvider)
/*      */   {
/*  264 */     String str1 = paramProvider.getProperty(paramString);
/*  265 */     if (str1 == null)
/*      */     {
/*      */ 
/*  268 */       Enumeration localEnumeration = paramProvider.keys();
/*  269 */       while ((localEnumeration.hasMoreElements()) && (str1 == null)) {
/*  270 */         String str2 = (String)localEnumeration.nextElement();
/*  271 */         if (paramString.equalsIgnoreCase(str2)) {
/*  272 */           str1 = paramProvider.getProperty(str2);
/*  273 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  277 */     return str1;
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
/*      */   @Deprecated
/*      */   public static String getAlgorithmProperty(String paramString1, String paramString2)
/*      */   {
/*  307 */     ProviderProperty localProviderProperty = getProviderProperty("Alg." + paramString2 + "." + paramString1);
/*      */     
/*  309 */     if (localProviderProperty != null) {
/*  310 */       return localProviderProperty.className;
/*      */     }
/*  312 */     return null;
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
/*      */   public static synchronized int insertProviderAt(Provider paramProvider, int paramInt)
/*      */   {
/*  358 */     String str = paramProvider.getName();
/*  359 */     checkInsertProvider(str);
/*  360 */     ProviderList localProviderList1 = Providers.getFullProviderList();
/*  361 */     ProviderList localProviderList2 = ProviderList.insertAt(localProviderList1, paramProvider, paramInt - 1);
/*  362 */     if (localProviderList1 == localProviderList2) {
/*  363 */       return -1;
/*      */     }
/*  365 */     Providers.setProviderList(localProviderList2);
/*  366 */     return localProviderList2.getIndex(str) + 1;
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
/*      */   public static int addProvider(Provider paramProvider)
/*      */   {
/*  403 */     return insertProviderAt(paramProvider, 0);
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
/*      */   public static synchronized void removeProvider(String paramString)
/*      */   {
/*  439 */     check("removeProvider." + paramString);
/*  440 */     ProviderList localProviderList1 = Providers.getFullProviderList();
/*  441 */     ProviderList localProviderList2 = ProviderList.remove(localProviderList1, paramString);
/*  442 */     Providers.setProviderList(localProviderList2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Provider[] getProviders()
/*      */   {
/*  452 */     return Providers.getFullProviderList().toArray();
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
/*      */   public static Provider getProvider(String paramString)
/*      */   {
/*  468 */     return Providers.getProviderList().getProvider(paramString);
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
/*      */   public static Provider[] getProviders(String paramString)
/*      */   {
/*  532 */     String str1 = null;
/*  533 */     String str2 = null;
/*  534 */     int i = paramString.indexOf(':');
/*      */     
/*  536 */     if (i == -1) {
/*  537 */       str1 = paramString;
/*  538 */       str2 = "";
/*      */     } else {
/*  540 */       str1 = paramString.substring(0, i);
/*  541 */       str2 = paramString.substring(i + 1);
/*      */     }
/*      */     
/*  544 */     Hashtable localHashtable = new Hashtable(1);
/*  545 */     localHashtable.put(str1, str2);
/*      */     
/*  547 */     return getProviders(localHashtable);
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
/*      */   public static Provider[] getProviders(Map<String, String> paramMap)
/*      */   {
/*  605 */     Provider[] arrayOfProvider = getProviders();
/*  606 */     Set localSet = paramMap.keySet();
/*  607 */     Object localObject1 = new LinkedHashSet(5);
/*      */     
/*      */ 
/*      */ 
/*  611 */     if ((localSet == null) || (arrayOfProvider == null)) {
/*  612 */       return arrayOfProvider;
/*      */     }
/*      */     
/*  615 */     int i = 1;
/*      */     
/*      */ 
/*      */ 
/*  619 */     for (Object localObject2 = localSet.iterator(); ((Iterator)localObject2).hasNext();) {
/*  620 */       localObject3 = (String)((Iterator)localObject2).next();
/*  621 */       String str = (String)paramMap.get(localObject3);
/*      */       
/*  623 */       LinkedHashSet localLinkedHashSet = getAllQualifyingCandidates((String)localObject3, str, arrayOfProvider);
/*      */       
/*  625 */       if (i != 0) {
/*  626 */         localObject1 = localLinkedHashSet;
/*  627 */         i = 0;
/*      */       }
/*      */       
/*  630 */       if ((localLinkedHashSet != null) && (!localLinkedHashSet.isEmpty()))
/*      */       {
/*      */ 
/*      */ 
/*  634 */         Iterator localIterator = ((LinkedHashSet)localObject1).iterator();
/*  635 */         while (localIterator.hasNext()) {
/*  636 */           Provider localProvider = (Provider)localIterator.next();
/*  637 */           if (!localLinkedHashSet.contains(localProvider)) {
/*  638 */             localIterator.remove();
/*      */           }
/*      */         }
/*      */       } else {
/*  642 */         localObject1 = null;
/*  643 */         break;
/*      */       }
/*      */     }
/*      */     
/*  647 */     if ((localObject1 == null) || (((LinkedHashSet)localObject1).isEmpty())) {
/*  648 */       return null;
/*      */     }
/*  650 */     localObject2 = ((LinkedHashSet)localObject1).toArray();
/*  651 */     Object localObject3 = new Provider[localObject2.length];
/*      */     
/*  653 */     for (int j = 0; j < localObject3.length; j++) {
/*  654 */       localObject3[j] = ((Provider)localObject2[j]);
/*      */     }
/*      */     
/*  657 */     return (Provider[])localObject3;
/*      */   }
/*      */   
/*      */ 
/*  661 */   private static final Map<String, Class<?>> spiMap = new ConcurrentHashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Class<?> getSpiClass(String paramString)
/*      */   {
/*  670 */     Class localClass = (Class)spiMap.get(paramString);
/*  671 */     if (localClass != null) {
/*  672 */       return localClass;
/*      */     }
/*      */     try {
/*  675 */       localClass = Class.forName("java.security." + paramString + "Spi");
/*  676 */       spiMap.put(paramString, localClass);
/*  677 */       return localClass;
/*      */     } catch (ClassNotFoundException localClassNotFoundException) {
/*  679 */       throw new AssertionError("Spi class not found", localClassNotFoundException);
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
/*      */   static Object[] getImpl(String paramString1, String paramString2, String paramString3)
/*      */     throws NoSuchAlgorithmException, NoSuchProviderException
/*      */   {
/*  693 */     if (paramString3 == null)
/*      */     {
/*  695 */       return GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1).toArray();
/*      */     }
/*      */     
/*  698 */     return GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramString3).toArray();
/*      */   }
/*      */   
/*      */ 
/*      */   static Object[] getImpl(String paramString1, String paramString2, String paramString3, Object paramObject)
/*      */     throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException
/*      */   {
/*  705 */     if (paramString3 == null)
/*      */     {
/*  707 */       return GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramObject).toArray();
/*      */     }
/*      */     
/*  710 */     return GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramObject, paramString3).toArray();
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
/*      */   static Object[] getImpl(String paramString1, String paramString2, Provider paramProvider)
/*      */     throws NoSuchAlgorithmException
/*      */   {
/*  724 */     return GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramProvider).toArray();
/*      */   }
/*      */   
/*      */ 
/*      */   static Object[] getImpl(String paramString1, String paramString2, Provider paramProvider, Object paramObject)
/*      */     throws NoSuchAlgorithmException, InvalidAlgorithmParameterException
/*      */   {
/*  731 */     return GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramObject, paramProvider).toArray();
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
/*      */   public static String getProperty(String paramString)
/*      */   {
/*  758 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  759 */     if (localSecurityManager != null) {
/*  760 */       localSecurityManager.checkPermission(new SecurityPermission("getProperty." + paramString));
/*      */     }
/*      */     
/*  763 */     String str = props.getProperty(paramString);
/*  764 */     if (str != null)
/*  765 */       str = str.trim();
/*  766 */     return str;
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
/*      */   public static void setProperty(String paramString1, String paramString2)
/*      */   {
/*  792 */     check("setProperty." + paramString1);
/*  793 */     props.put(paramString1, paramString2);
/*  794 */     invalidateSMCache(paramString1);
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
/*      */   private static void invalidateSMCache(String paramString)
/*      */   {
/*  809 */     boolean bool1 = paramString.equals("package.access");
/*  810 */     boolean bool2 = paramString.equals("package.definition");
/*      */     
/*  812 */     if ((bool1) || (bool2)) {
/*  813 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*      */           try {
/*  817 */             Class localClass = Class.forName("java.lang.SecurityManager", false, null);
/*      */             
/*  819 */             Field localField = null;
/*  820 */             boolean bool = false;
/*      */             
/*  822 */             if (this.val$pa) {
/*  823 */               localField = localClass.getDeclaredField("packageAccessValid");
/*  824 */               bool = localField.isAccessible();
/*  825 */               localField.setAccessible(true);
/*      */             } else {
/*  827 */               localField = localClass.getDeclaredField("packageDefinitionValid");
/*  828 */               bool = localField.isAccessible();
/*  829 */               localField.setAccessible(true);
/*      */             }
/*  831 */             localField.setBoolean(localField, false);
/*  832 */             localField.setAccessible(bool);
/*      */           }
/*      */           catch (Exception localException) {}
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  845 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */   
/*      */   private static void check(String paramString) {
/*  852 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  853 */     if (localSecurityManager != null) {
/*  854 */       localSecurityManager.checkSecurityAccess(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void checkInsertProvider(String paramString) {
/*  859 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  860 */     if (localSecurityManager != null) {
/*      */       try {
/*  862 */         localSecurityManager.checkSecurityAccess("insertProvider");
/*      */       } catch (SecurityException localSecurityException1) {
/*      */         try {
/*  865 */           localSecurityManager.checkSecurityAccess("insertProvider." + paramString);
/*      */         }
/*      */         catch (SecurityException localSecurityException2) {
/*  868 */           localSecurityException1.addSuppressed(localSecurityException2);
/*  869 */           throw localSecurityException1;
/*      */         }
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
/*      */   private static LinkedHashSet<Provider> getAllQualifyingCandidates(String paramString1, String paramString2, Provider[] paramArrayOfProvider)
/*      */   {
/*  883 */     String[] arrayOfString = getFilterComponents(paramString1, paramString2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  889 */     String str1 = arrayOfString[0];
/*  890 */     String str2 = arrayOfString[1];
/*  891 */     String str3 = arrayOfString[2];
/*      */     
/*  893 */     return getProvidersNotUsingCache(str1, str2, str3, paramString2, paramArrayOfProvider);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static LinkedHashSet<Provider> getProvidersNotUsingCache(String paramString1, String paramString2, String paramString3, String paramString4, Provider[] paramArrayOfProvider)
/*      */   {
/*  903 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet(5);
/*  904 */     for (int i = 0; i < paramArrayOfProvider.length; i++) {
/*  905 */       if (isCriterionSatisfied(paramArrayOfProvider[i], paramString1, paramString2, paramString3, paramString4))
/*      */       {
/*      */ 
/*  908 */         localLinkedHashSet.add(paramArrayOfProvider[i]);
/*      */       }
/*      */     }
/*  911 */     return localLinkedHashSet;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isCriterionSatisfied(Provider paramProvider, String paramString1, String paramString2, String paramString3, String paramString4)
/*      */   {
/*  923 */     String str1 = paramString1 + '.' + paramString2;
/*      */     
/*  925 */     if (paramString3 != null) {
/*  926 */       str1 = str1 + ' ' + paramString3;
/*      */     }
/*      */     
/*      */ 
/*  930 */     String str2 = getProviderProperty(str1, paramProvider);
/*      */     
/*  932 */     if (str2 == null)
/*      */     {
/*      */ 
/*  935 */       String str3 = getProviderProperty("Alg.Alias." + paramString1 + "." + paramString2, paramProvider);
/*      */       
/*      */ 
/*      */ 
/*  939 */       if (str3 != null) {
/*  940 */         str1 = paramString1 + "." + str3;
/*      */         
/*  942 */         if (paramString3 != null) {
/*  943 */           str1 = str1 + ' ' + paramString3;
/*      */         }
/*      */         
/*  946 */         str2 = getProviderProperty(str1, paramProvider);
/*      */       }
/*      */       
/*  949 */       if (str2 == null)
/*      */       {
/*      */ 
/*  952 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  960 */     if (paramString3 == null) {
/*  961 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  966 */     if (isStandardAttr(paramString3)) {
/*  967 */       return isConstraintSatisfied(paramString3, paramString4, str2);
/*      */     }
/*  969 */     return paramString4.equalsIgnoreCase(str2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isStandardAttr(String paramString)
/*      */   {
/*  980 */     if (paramString.equalsIgnoreCase("KeySize")) {
/*  981 */       return true;
/*      */     }
/*  983 */     if (paramString.equalsIgnoreCase("ImplementedIn")) {
/*  984 */       return true;
/*      */     }
/*  986 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isConstraintSatisfied(String paramString1, String paramString2, String paramString3)
/*      */   {
/*  998 */     if (paramString1.equalsIgnoreCase("KeySize")) {
/*  999 */       int i = Integer.parseInt(paramString2);
/* 1000 */       int j = Integer.parseInt(paramString3);
/* 1001 */       if (i <= j) {
/* 1002 */         return true;
/*      */       }
/* 1004 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1010 */     if (paramString1.equalsIgnoreCase("ImplementedIn")) {
/* 1011 */       return paramString2.equalsIgnoreCase(paramString3);
/*      */     }
/*      */     
/* 1014 */     return false;
/*      */   }
/*      */   
/*      */   static String[] getFilterComponents(String paramString1, String paramString2) {
/* 1018 */     int i = paramString1.indexOf('.');
/*      */     
/* 1020 */     if (i < 0)
/*      */     {
/*      */ 
/* 1023 */       throw new InvalidParameterException("Invalid filter");
/*      */     }
/*      */     
/* 1026 */     String str1 = paramString1.substring(0, i);
/* 1027 */     String str2 = null;
/* 1028 */     String str3 = null;
/*      */     
/* 1030 */     if (paramString2.length() == 0)
/*      */     {
/*      */ 
/* 1033 */       str2 = paramString1.substring(i + 1).trim();
/* 1034 */       if (str2.length() == 0)
/*      */       {
/* 1036 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1042 */       int j = paramString1.indexOf(' ');
/*      */       
/* 1044 */       if (j == -1)
/*      */       {
/* 1046 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/* 1048 */       str3 = paramString1.substring(j + 1).trim();
/* 1049 */       if (str3.length() == 0)
/*      */       {
/* 1051 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1056 */       if ((j < i) || (i == j - 1))
/*      */       {
/* 1058 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/* 1060 */       str2 = paramString1.substring(i + 1, j);
/*      */     }
/*      */     
/*      */ 
/* 1064 */     String[] arrayOfString = new String[3];
/* 1065 */     arrayOfString[0] = str1;
/* 1066 */     arrayOfString[1] = str2;
/* 1067 */     arrayOfString[2] = str3;
/*      */     
/* 1069 */     return arrayOfString;
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
/*      */   public static Set<String> getAlgorithms(String paramString)
/*      */   {
/* 1095 */     if ((paramString == null) || (paramString.length() == 0) || 
/* 1096 */       (paramString.endsWith("."))) {
/* 1097 */       return Collections.emptySet();
/*      */     }
/*      */     
/* 1100 */     HashSet localHashSet = new HashSet();
/* 1101 */     Provider[] arrayOfProvider = getProviders();
/*      */     
/* 1103 */     for (int i = 0; i < arrayOfProvider.length; i++)
/*      */     {
/* 1105 */       Enumeration localEnumeration = arrayOfProvider[i].keys();
/* 1106 */       while (localEnumeration.hasMoreElements())
/*      */       {
/* 1108 */         String str = ((String)localEnumeration.nextElement()).toUpperCase(Locale.ENGLISH);
/* 1109 */         if (str.startsWith(paramString
/* 1110 */           .toUpperCase(Locale.ENGLISH)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1117 */           if (str.indexOf(" ") < 0) {
/* 1118 */             localHashSet.add(str.substring(paramString
/* 1119 */               .length() + 1));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1124 */     return Collections.unmodifiableSet(localHashSet);
/*      */   }
/*      */   
/*      */   private static class ProviderProperty
/*      */   {
/*      */     String className;
/*      */     Provider provider;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Security.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */