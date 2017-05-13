/*      */ package java.lang;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.net.URL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.misc.CompoundEnumeration;
/*      */ import sun.misc.Launcher;
/*      */ import sun.misc.PerfCounter;
/*      */ import sun.misc.Resource;
/*      */ import sun.misc.URLClassPath;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ClassLoader
/*      */ {
/*      */   private final ClassLoader parent;
/*      */   private final ConcurrentHashMap<String, Object> parallelLockMap;
/*      */   private final Map<String, Certificate[]> package2certs;
/*      */   private static native void registerNatives();
/*      */   
/*      */   private static class ParallelLoaders
/*      */   {
/*  198 */     private static final Set<Class<? extends ClassLoader>> loaderTypes = Collections.newSetFromMap(new WeakHashMap());
/*      */     
/*      */     static {
/*  201 */       synchronized (loaderTypes) { loaderTypes.add(ClassLoader.class);
/*      */       }
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     static boolean isRegistered(Class<? extends ClassLoader> paramClass)
/*      */     {
/*      */       // Byte code:
/*      */       //   0: getstatic 2	java/lang/ClassLoader$ParallelLoaders:loaderTypes	Ljava/util/Set;
/*      */       //   3: dup
/*      */       //   4: astore_1
/*      */       //   5: monitorenter
/*      */       //   6: getstatic 2	java/lang/ClassLoader$ParallelLoaders:loaderTypes	Ljava/util/Set;
/*      */       //   9: aload_0
/*      */       //   10: invokeinterface 4 2 0
/*      */       //   15: aload_1
/*      */       //   16: monitorexit
/*      */       //   17: ireturn
/*      */       //   18: astore_2
/*      */       //   19: aload_1
/*      */       //   20: monitorexit
/*      */       //   21: aload_2
/*      */       //   22: athrow
/*      */       // Line number table:
/*      */       //   Java source line #230	-> byte code offset #0
/*      */       //   Java source line #231	-> byte code offset #6
/*      */       //   Java source line #232	-> byte code offset #18
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	23	0	paramClass	Class<? extends ClassLoader>
/*      */       //   4	16	1	Ljava/lang/Object;	Object
/*      */       //   18	4	2	localObject1	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   6	17	18	finally
/*      */       //   18	21	18	finally
/*      */     }
/*      */     
/*      */     static boolean register(Class<? extends ClassLoader> paramClass)
/*      */     {
/*  210 */       synchronized (loaderTypes) {
/*  211 */         if (loaderTypes.contains(paramClass.getSuperclass()))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  217 */           loaderTypes.add(paramClass);
/*  218 */           return true;
/*      */         }
/*  220 */         return false;
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
/*  246 */   private static final Certificate[] nocerts = new Certificate[0];
/*      */   
/*      */ 
/*      */ 
/*  250 */   private final Vector<Class<?>> classes = new Vector();
/*      */   
/*      */ 
/*      */ 
/*  254 */   private final ProtectionDomain defaultDomain = new ProtectionDomain(new CodeSource(null, (Certificate[])null), null, this, null);
/*      */   
/*      */ 
/*      */   private final Set<ProtectionDomain> domains;
/*      */   
/*      */ 
/*      */ 
/*      */   void addClass(Class<?> paramClass)
/*      */   {
/*  263 */     this.classes.addElement(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  269 */   private final HashMap<String, Package> packages = new HashMap();
/*      */   private static ClassLoader scl;
/*      */   
/*  272 */   private static Void checkCreateClassLoader() { SecurityManager localSecurityManager = System.getSecurityManager();
/*  273 */     if (localSecurityManager != null) {
/*  274 */       localSecurityManager.checkCreateClassLoader();
/*      */     }
/*  276 */     return null;
/*      */   }
/*      */   
/*      */   private ClassLoader(Void paramVoid, ClassLoader paramClassLoader) {
/*  280 */     this.parent = paramClassLoader;
/*  281 */     if (ParallelLoaders.isRegistered(getClass())) {
/*  282 */       this.parallelLockMap = new ConcurrentHashMap();
/*  283 */       this.package2certs = new ConcurrentHashMap();
/*      */       
/*  285 */       this.domains = Collections.synchronizedSet(new HashSet());
/*  286 */       this.assertionLock = new Object();
/*      */     }
/*      */     else {
/*  289 */       this.parallelLockMap = null;
/*  290 */       this.package2certs = new Hashtable();
/*  291 */       this.domains = new HashSet();
/*  292 */       this.assertionLock = this;
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
/*      */   protected ClassLoader(ClassLoader paramClassLoader)
/*      */   {
/*  316 */     this(checkCreateClassLoader(), paramClassLoader);
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
/*      */   protected ClassLoader()
/*      */   {
/*  335 */     this(checkCreateClassLoader(), getSystemClassLoader());
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
/*      */   public Class<?> loadClass(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  357 */     return loadClass(paramString, false);
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
/*      */   protected Class<?> loadClass(String paramString, boolean paramBoolean)
/*      */     throws ClassNotFoundException
/*      */   {
/*  404 */     synchronized (getClassLoadingLock(paramString))
/*      */     {
/*  406 */       Class localClass = findLoadedClass(paramString);
/*  407 */       if (localClass == null) {
/*  408 */         long l1 = System.nanoTime();
/*      */         try {
/*  410 */           if (this.parent != null) {
/*  411 */             localClass = this.parent.loadClass(paramString, false);
/*      */           } else {
/*  413 */             localClass = findBootstrapClassOrNull(paramString);
/*      */           }
/*      */         }
/*      */         catch (ClassNotFoundException localClassNotFoundException) {}
/*      */         
/*      */ 
/*      */ 
/*  420 */         if (localClass == null)
/*      */         {
/*      */ 
/*  423 */           long l2 = System.nanoTime();
/*  424 */           localClass = findClass(paramString);
/*      */           
/*      */ 
/*  427 */           PerfCounter.getParentDelegationTime().addTime(l2 - l1);
/*  428 */           PerfCounter.getFindClassTime().addElapsedTimeFrom(l2);
/*  429 */           PerfCounter.getFindClasses().increment();
/*      */         }
/*      */       }
/*  432 */       if (paramBoolean) {
/*  433 */         resolveClass(localClass);
/*      */       }
/*  435 */       return localClass;
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
/*      */   protected Object getClassLoadingLock(String paramString)
/*      */   {
/*  460 */     Object localObject1 = this;
/*  461 */     if (this.parallelLockMap != null) {
/*  462 */       Object localObject2 = new Object();
/*  463 */       localObject1 = this.parallelLockMap.putIfAbsent(paramString, localObject2);
/*  464 */       if (localObject1 == null) {
/*  465 */         localObject1 = localObject2;
/*      */       }
/*      */     }
/*  468 */     return localObject1;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private Class<?> loadClassInternal(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 28	java/lang/ClassLoader:parallelLockMap	Ljava/util/concurrent/ConcurrentHashMap;
/*      */     //   4: ifnonnull +20 -> 24
/*      */     //   7: aload_0
/*      */     //   8: dup
/*      */     //   9: astore_2
/*      */     //   10: monitorenter
/*      */     //   11: aload_0
/*      */     //   12: aload_1
/*      */     //   13: invokevirtual 56	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
/*      */     //   16: aload_2
/*      */     //   17: monitorexit
/*      */     //   18: areturn
/*      */     //   19: astore_3
/*      */     //   20: aload_2
/*      */     //   21: monitorexit
/*      */     //   22: aload_3
/*      */     //   23: athrow
/*      */     //   24: aload_0
/*      */     //   25: aload_1
/*      */     //   26: invokevirtual 56	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
/*      */     //   29: areturn
/*      */     // Line number table:
/*      */     //   Java source line #477	-> byte code offset #0
/*      */     //   Java source line #478	-> byte code offset #7
/*      */     //   Java source line #479	-> byte code offset #11
/*      */     //   Java source line #480	-> byte code offset #19
/*      */     //   Java source line #482	-> byte code offset #24
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	30	0	this	ClassLoader
/*      */     //   0	30	1	paramString	String
/*      */     //   9	12	2	Ljava/lang/Object;	Object
/*      */     //   19	4	3	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   11	18	19	finally
/*      */     //   19	22	19	finally
/*      */   }
/*      */   
/*      */   private void checkPackageAccess(Class<?> paramClass, ProtectionDomain paramProtectionDomain)
/*      */   {
/*  488 */     final SecurityManager localSecurityManager = System.getSecurityManager();
/*  489 */     if (localSecurityManager != null) {
/*  490 */       if (ReflectUtil.isNonPublicProxyClass(paramClass)) {
/*  491 */         for (Class localClass : paramClass.getInterfaces()) {
/*  492 */           checkPackageAccess(localClass, paramProtectionDomain);
/*      */         }
/*  494 */         return;
/*      */       }
/*      */       
/*  497 */       ??? = paramClass.getName();
/*  498 */       ??? = ((String)???).lastIndexOf('.');
/*  499 */       if (??? != -1) {
/*  500 */         AccessController.doPrivileged(new PrivilegedAction()
/*      */         
/*      */ 
/*  503 */           new AccessControlContext {
/*      */             public Void run() {
/*  502 */               localSecurityManager.checkPackageAccess(localObject.substring(0, i));
/*  503 */               return null; } }, new AccessControlContext(new ProtectionDomain[] { paramProtectionDomain }));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  508 */     this.domains.add(paramProtectionDomain);
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
/*      */   protected Class<?> findClass(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  530 */     throw new ClassNotFoundException(paramString);
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
/*      */   @Deprecated
/*      */   protected final Class<?> defineClass(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws ClassFormatError
/*      */   {
/*  578 */     return defineClass(null, paramArrayOfByte, paramInt1, paramInt2, null);
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
/*      */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws ClassFormatError
/*      */   {
/*  642 */     return defineClass(paramString, paramArrayOfByte, paramInt1, paramInt2, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ProtectionDomain preDefineClass(String paramString, ProtectionDomain paramProtectionDomain)
/*      */   {
/*  653 */     if (!checkName(paramString)) {
/*  654 */       throw new NoClassDefFoundError("IllegalName: " + paramString);
/*      */     }
/*  656 */     if ((paramString != null) && (paramString.startsWith("java.")))
/*      */     {
/*      */ 
/*  659 */       throw new SecurityException("Prohibited package name: " + paramString.substring(0, paramString.lastIndexOf('.')));
/*      */     }
/*  661 */     if (paramProtectionDomain == null) {
/*  662 */       paramProtectionDomain = this.defaultDomain;
/*      */     }
/*      */     
/*  665 */     if (paramString != null) { checkCerts(paramString, paramProtectionDomain.getCodeSource());
/*      */     }
/*  667 */     return paramProtectionDomain;
/*      */   }
/*      */   
/*      */   private String defineClassSourceLocation(ProtectionDomain paramProtectionDomain)
/*      */   {
/*  672 */     CodeSource localCodeSource = paramProtectionDomain.getCodeSource();
/*  673 */     String str = null;
/*  674 */     if ((localCodeSource != null) && (localCodeSource.getLocation() != null)) {
/*  675 */       str = localCodeSource.getLocation().toString();
/*      */     }
/*  677 */     return str;
/*      */   }
/*      */   
/*      */   private void postDefineClass(Class<?> paramClass, ProtectionDomain paramProtectionDomain)
/*      */   {
/*  682 */     if (paramProtectionDomain.getCodeSource() != null) {
/*  683 */       Certificate[] arrayOfCertificate = paramProtectionDomain.getCodeSource().getCertificates();
/*  684 */       if (arrayOfCertificate != null) {
/*  685 */         setSigners(paramClass, arrayOfCertificate);
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
/*      */ 
/*      */   private static boolean sclSet;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain)
/*      */     throws ClassFormatError
/*      */   {
/*  758 */     paramProtectionDomain = preDefineClass(paramString, paramProtectionDomain);
/*  759 */     String str = defineClassSourceLocation(paramProtectionDomain);
/*  760 */     Class localClass = defineClass1(paramString, paramArrayOfByte, paramInt1, paramInt2, paramProtectionDomain, str);
/*  761 */     postDefineClass(localClass, paramProtectionDomain);
/*  762 */     return localClass;
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
/*      */   protected final Class<?> defineClass(String paramString, ByteBuffer paramByteBuffer, ProtectionDomain paramProtectionDomain)
/*      */     throws ClassFormatError
/*      */   {
/*  831 */     int i = paramByteBuffer.remaining();
/*      */     
/*      */ 
/*  834 */     if (!paramByteBuffer.isDirect()) {
/*  835 */       if (paramByteBuffer.hasArray()) {
/*  836 */         return defineClass(paramString, paramByteBuffer.array(), paramByteBuffer
/*  837 */           .position() + paramByteBuffer.arrayOffset(), i, paramProtectionDomain);
/*      */       }
/*      */       
/*      */ 
/*  841 */       localObject = new byte[i];
/*  842 */       paramByteBuffer.get((byte[])localObject);
/*  843 */       return defineClass(paramString, (byte[])localObject, 0, i, paramProtectionDomain);
/*      */     }
/*      */     
/*      */ 
/*  847 */     paramProtectionDomain = preDefineClass(paramString, paramProtectionDomain);
/*  848 */     Object localObject = defineClassSourceLocation(paramProtectionDomain);
/*  849 */     Class localClass = defineClass2(paramString, paramByteBuffer, paramByteBuffer.position(), i, paramProtectionDomain, (String)localObject);
/*  850 */     postDefineClass(localClass, paramProtectionDomain);
/*  851 */     return localClass;
/*      */   }
/*      */   
/*      */ 
/*      */   private native Class<?> defineClass0(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain);
/*      */   
/*      */ 
/*      */   private native Class<?> defineClass1(String paramString1, byte[] paramArrayOfByte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain, String paramString2);
/*      */   
/*      */ 
/*      */   private native Class<?> defineClass2(String paramString1, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain, String paramString2);
/*      */   
/*      */ 
/*      */   private boolean checkName(String paramString)
/*      */   {
/*  866 */     if ((paramString == null) || (paramString.length() == 0))
/*  867 */       return true;
/*  868 */     if ((paramString.indexOf('/') != -1) || (
/*  869 */       (!VM.allowArraySyntax()) && (paramString.charAt(0) == '[')))
/*  870 */       return false;
/*  871 */     return true;
/*      */   }
/*      */   
/*      */   private void checkCerts(String paramString, CodeSource paramCodeSource) {
/*  875 */     int i = paramString.lastIndexOf('.');
/*  876 */     String str = i == -1 ? "" : paramString.substring(0, i);
/*      */     
/*  878 */     Certificate[] arrayOfCertificate1 = null;
/*  879 */     if (paramCodeSource != null) {
/*  880 */       arrayOfCertificate1 = paramCodeSource.getCertificates();
/*      */     }
/*  882 */     Certificate[] arrayOfCertificate2 = null;
/*  883 */     if (this.parallelLockMap == null) {
/*  884 */       synchronized (this) {
/*  885 */         arrayOfCertificate2 = (Certificate[])this.package2certs.get(str);
/*  886 */         if (arrayOfCertificate2 == null) {
/*  887 */           this.package2certs.put(str, arrayOfCertificate1 == null ? nocerts : arrayOfCertificate1);
/*      */         }
/*      */         
/*      */       }
/*      */     } else {
/*  892 */       arrayOfCertificate2 = (Certificate[])((ConcurrentHashMap)this.package2certs).putIfAbsent(str, arrayOfCertificate1 == null ? nocerts : arrayOfCertificate1);
/*      */     }
/*  894 */     if ((arrayOfCertificate2 != null) && (!compareCerts(arrayOfCertificate2, arrayOfCertificate1))) {
/*  895 */       throw new SecurityException("class \"" + paramString + "\"'s signer information does not match signer information of other classes in the same package");
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
/*      */   private boolean compareCerts(Certificate[] paramArrayOfCertificate1, Certificate[] paramArrayOfCertificate2)
/*      */   {
/*  908 */     if ((paramArrayOfCertificate2 == null) || (paramArrayOfCertificate2.length == 0)) {
/*  909 */       return paramArrayOfCertificate1.length == 0;
/*      */     }
/*      */     
/*      */ 
/*  913 */     if (paramArrayOfCertificate2.length != paramArrayOfCertificate1.length) {
/*  914 */       return false;
/*      */     }
/*      */     
/*      */     int i;
/*      */     int k;
/*  919 */     for (int j = 0; j < paramArrayOfCertificate2.length; j++) {
/*  920 */       i = 0;
/*  921 */       for (k = 0; k < paramArrayOfCertificate1.length; k++) {
/*  922 */         if (paramArrayOfCertificate2[j].equals(paramArrayOfCertificate1[k])) {
/*  923 */           i = 1;
/*  924 */           break;
/*      */         }
/*      */       }
/*  927 */       if (i == 0) { return false;
/*      */       }
/*      */     }
/*      */     
/*  931 */     for (j = 0; j < paramArrayOfCertificate1.length; j++) {
/*  932 */       i = 0;
/*  933 */       for (k = 0; k < paramArrayOfCertificate2.length; k++) {
/*  934 */         if (paramArrayOfCertificate1[j].equals(paramArrayOfCertificate2[k])) {
/*  935 */           i = 1;
/*  936 */           break;
/*      */         }
/*      */       }
/*  939 */       if (i == 0) { return false;
/*      */       }
/*      */     }
/*  942 */     return true;
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
/*      */   protected final void resolveClass(Class<?> paramClass)
/*      */   {
/*  961 */     resolveClass0(paramClass);
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
/*      */   private native void resolveClass0(Class<?> paramClass);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Class<?> findSystemClass(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  991 */     ClassLoader localClassLoader = getSystemClassLoader();
/*  992 */     if (localClassLoader == null) {
/*  993 */       if (!checkName(paramString))
/*  994 */         throw new ClassNotFoundException(paramString);
/*  995 */       Class localClass = findBootstrapClass(paramString);
/*  996 */       if (localClass == null) {
/*  997 */         throw new ClassNotFoundException(paramString);
/*      */       }
/*  999 */       return localClass;
/*      */     }
/* 1001 */     return localClassLoader.loadClass(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Class<?> findBootstrapClassOrNull(String paramString)
/*      */   {
/* 1010 */     if (!checkName(paramString)) { return null;
/*      */     }
/* 1012 */     return findBootstrapClass(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native Class<?> findBootstrapClass(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Class<?> findLoadedClass(String paramString)
/*      */   {
/* 1033 */     if (!checkName(paramString))
/* 1034 */       return null;
/* 1035 */     return findLoadedClass0(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final native Class<?> findLoadedClass0(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setSigners(Class<?> paramClass, Object[] paramArrayOfObject)
/*      */   {
/* 1053 */     paramClass.setSigners(paramArrayOfObject);
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
/*      */   public URL getResource(String paramString)
/*      */   {
/*      */     URL localURL;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1087 */     if (this.parent != null) {
/* 1088 */       localURL = this.parent.getResource(paramString);
/*      */     } else {
/* 1090 */       localURL = getBootstrapResource(paramString);
/*      */     }
/* 1092 */     if (localURL == null) {
/* 1093 */       localURL = findResource(paramString);
/*      */     }
/* 1095 */     return localURL;
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
/*      */   public Enumeration<URL> getResources(String paramString)
/*      */     throws IOException
/*      */   {
/* 1133 */     Enumeration[] arrayOfEnumeration = (Enumeration[])new Enumeration[2];
/* 1134 */     if (this.parent != null) {
/* 1135 */       arrayOfEnumeration[0] = this.parent.getResources(paramString);
/*      */     } else {
/* 1137 */       arrayOfEnumeration[0] = getBootstrapResources(paramString);
/*      */     }
/* 1139 */     arrayOfEnumeration[1] = findResources(paramString);
/*      */     
/* 1141 */     return new CompoundEnumeration(arrayOfEnumeration);
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
/*      */   protected URL findResource(String paramString)
/*      */   {
/* 1157 */     return null;
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
/*      */   protected Enumeration<URL> findResources(String paramString)
/*      */     throws IOException
/*      */   {
/* 1178 */     return Collections.emptyEnumeration();
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
/*      */   @CallerSensitive
/*      */   protected static boolean registerAsParallelCapable()
/*      */   {
/* 1201 */     Class localClass = Reflection.getCallerClass().asSubclass(ClassLoader.class);
/* 1202 */     return ParallelLoaders.register(localClass);
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
/*      */   public static URL getSystemResource(String paramString)
/*      */   {
/* 1219 */     ClassLoader localClassLoader = getSystemClassLoader();
/* 1220 */     if (localClassLoader == null) {
/* 1221 */       return getBootstrapResource(paramString);
/*      */     }
/* 1223 */     return localClassLoader.getResource(paramString);
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
/*      */   public static Enumeration<URL> getSystemResources(String paramString)
/*      */     throws IOException
/*      */   {
/* 1249 */     ClassLoader localClassLoader = getSystemClassLoader();
/* 1250 */     if (localClassLoader == null) {
/* 1251 */       return getBootstrapResources(paramString);
/*      */     }
/* 1253 */     return localClassLoader.getResources(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static URL getBootstrapResource(String paramString)
/*      */   {
/* 1260 */     URLClassPath localURLClassPath = getBootstrapClassPath();
/* 1261 */     Resource localResource = localURLClassPath.getResource(paramString);
/* 1262 */     return localResource != null ? localResource.getURL() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Enumeration<URL> getBootstrapResources(String paramString)
/*      */     throws IOException
/*      */   {
/* 1272 */     Enumeration localEnumeration = getBootstrapClassPath().getResources(paramString);
/* 1273 */     new Enumeration() {
/*      */       public URL nextElement() {
/* 1275 */         return ((Resource)this.val$e.nextElement()).getURL();
/*      */       }
/*      */       
/* 1278 */       public boolean hasMoreElements() { return this.val$e.hasMoreElements(); }
/*      */     };
/*      */   }
/*      */   
/*      */ 
/*      */   static URLClassPath getBootstrapClassPath()
/*      */   {
/* 1285 */     return Launcher.getBootstrapClassPath();
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
/*      */   public InputStream getResourceAsStream(String paramString)
/*      */   {
/* 1304 */     URL localURL = getResource(paramString);
/*      */     try {
/* 1306 */       return localURL != null ? localURL.openStream() : null;
/*      */     } catch (IOException localIOException) {}
/* 1308 */     return null;
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
/*      */   public static InputStream getSystemResourceAsStream(String paramString)
/*      */   {
/* 1326 */     URL localURL = getSystemResource(paramString);
/*      */     try {
/* 1328 */       return localURL != null ? localURL.openStream() : null;
/*      */     } catch (IOException localIOException) {}
/* 1330 */     return null;
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
/*      */   @CallerSensitive
/*      */   public final ClassLoader getParent()
/*      */   {
/* 1364 */     if (this.parent == null)
/* 1365 */       return null;
/* 1366 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1367 */     if (localSecurityManager != null) {
/* 1368 */       checkClassLoaderPermission(this, Reflection.getCallerClass());
/*      */     }
/* 1370 */     return this.parent;
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
/*      */   @CallerSensitive
/*      */   public static ClassLoader getSystemClassLoader()
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1431 */     if (scl == null) {
/* 1432 */       return null;
/*      */     }
/* 1434 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1435 */     if (localSecurityManager != null) {
/* 1436 */       checkClassLoaderPermission(scl, Reflection.getCallerClass());
/*      */     }
/* 1438 */     return scl;
/*      */   }
/*      */   
/*      */   private static synchronized void initSystemClassLoader() {
/* 1442 */     if (!sclSet) {
/* 1443 */       if (scl != null)
/* 1444 */         throw new IllegalStateException("recursive invocation");
/* 1445 */       Launcher localLauncher = Launcher.getLauncher();
/* 1446 */       if (localLauncher != null) {
/* 1447 */         Throwable localThrowable = null;
/* 1448 */         scl = localLauncher.getClassLoader();
/*      */         try {
/* 1450 */           scl = (ClassLoader)AccessController.doPrivileged(new SystemClassLoaderAction(scl));
/*      */         }
/*      */         catch (PrivilegedActionException localPrivilegedActionException) {
/* 1453 */           localThrowable = localPrivilegedActionException.getCause();
/* 1454 */           if ((localThrowable instanceof InvocationTargetException)) {
/* 1455 */             localThrowable = localThrowable.getCause();
/*      */           }
/*      */         }
/* 1458 */         if (localThrowable != null) {
/* 1459 */           if ((localThrowable instanceof Error)) {
/* 1460 */             throw ((Error)localThrowable);
/*      */           }
/*      */           
/* 1463 */           throw new Error(localThrowable);
/*      */         }
/*      */       }
/*      */       
/* 1467 */       sclSet = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   boolean isAncestor(ClassLoader paramClassLoader)
/*      */   {
/* 1474 */     ClassLoader localClassLoader = this;
/*      */     do {
/* 1476 */       localClassLoader = localClassLoader.parent;
/* 1477 */       if (paramClassLoader == localClassLoader) {
/* 1478 */         return true;
/*      */       }
/* 1480 */     } while (localClassLoader != null);
/* 1481 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean needsClassLoaderPermissionCheck(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2)
/*      */   {
/* 1492 */     if (paramClassLoader1 == paramClassLoader2) {
/* 1493 */       return false;
/*      */     }
/* 1495 */     if (paramClassLoader1 == null) {
/* 1496 */       return false;
/*      */     }
/* 1498 */     return !paramClassLoader2.isAncestor(paramClassLoader1);
/*      */   }
/*      */   
/*      */ 
/*      */   static ClassLoader getClassLoader(Class<?> paramClass)
/*      */   {
/* 1504 */     if (paramClass == null) {
/* 1505 */       return null;
/*      */     }
/*      */     
/* 1508 */     return paramClass.getClassLoader0();
/*      */   }
/*      */   
/*      */   static void checkClassLoaderPermission(ClassLoader paramClassLoader, Class<?> paramClass) {
/* 1512 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1513 */     if (localSecurityManager != null)
/*      */     {
/* 1515 */       ClassLoader localClassLoader = getClassLoader(paramClass);
/* 1516 */       if (needsClassLoaderPermissionCheck(localClassLoader, paramClassLoader)) {
/* 1517 */         localSecurityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Package definePackage(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, URL paramURL)
/*      */     throws IllegalArgumentException
/*      */   {
/* 1580 */     synchronized (this.packages) {
/* 1581 */       Package localPackage = getPackage(paramString1);
/* 1582 */       if (localPackage != null) {
/* 1583 */         throw new IllegalArgumentException(paramString1);
/*      */       }
/* 1585 */       localPackage = new Package(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramURL, this);
/*      */       
/*      */ 
/* 1588 */       this.packages.put(paramString1, localPackage);
/* 1589 */       return localPackage;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Package getPackage(String paramString)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1607 */     synchronized (this.packages) {
/* 1608 */       localObject1 = (Package)this.packages.get(paramString);
/*      */     }
/* 1610 */     if (localObject1 == null) {
/* 1611 */       if (this.parent != null) {
/* 1612 */         localObject1 = this.parent.getPackage(paramString);
/*      */       } else {
/* 1614 */         localObject1 = Package.getSystemPackage(paramString);
/*      */       }
/* 1616 */       if (localObject1 != null) {
/* 1617 */         synchronized (this.packages) {
/* 1618 */           Package localPackage = (Package)this.packages.get(paramString);
/* 1619 */           if (localPackage == null) {
/* 1620 */             this.packages.put(paramString, localObject1);
/*      */           } else {
/* 1622 */             localObject1 = localPackage;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1627 */     return (Package)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Package[] getPackages()
/*      */   {
/*      */     HashMap localHashMap;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1641 */     synchronized (this.packages) {
/* 1642 */       localHashMap = new HashMap(this.packages);
/*      */     }
/*      */     
/* 1645 */     if (this.parent != null) {
/* 1646 */       ??? = this.parent.getPackages();
/*      */     } else {
/* 1648 */       ??? = Package.getSystemPackages();
/*      */     }
/* 1650 */     if (??? != null) {
/* 1651 */       for (int i = 0; i < ???.length; i++) {
/* 1652 */         String str = ???[i].getName();
/* 1653 */         if (localHashMap.get(str) == null) {
/* 1654 */           localHashMap.put(str, ???[i]);
/*      */         }
/*      */       }
/*      */     }
/* 1658 */     return (Package[])localHashMap.values().toArray(new Package[localHashMap.size()]);
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
/*      */   protected String findLibrary(String paramString)
/*      */   {
/* 1682 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static class NativeLibrary
/*      */   {
/*      */     long handle;
/*      */     
/*      */ 
/*      */     private int jniVersion;
/*      */     
/*      */ 
/*      */     private final Class<?> fromClass;
/*      */     
/*      */ 
/*      */     String name;
/*      */     
/*      */ 
/*      */     boolean isBuiltin;
/*      */     
/*      */ 
/*      */     boolean loaded;
/*      */     
/*      */ 
/*      */ 
/*      */     native void load(String paramString, boolean paramBoolean);
/*      */     
/*      */ 
/*      */     native long find(String paramString);
/*      */     
/*      */ 
/*      */     native void unload(String paramString, boolean paramBoolean);
/*      */     
/*      */ 
/*      */     static native String findBuiltinLib(String paramString);
/*      */     
/*      */ 
/*      */     public NativeLibrary(Class<?> paramClass, String paramString, boolean paramBoolean)
/*      */     {
/* 1722 */       this.name = paramString;
/* 1723 */       this.fromClass = paramClass;
/* 1724 */       this.isBuiltin = paramBoolean;
/*      */     }
/*      */     
/*      */     protected void finalize() {
/* 1728 */       synchronized (ClassLoader.loadedLibraryNames) {
/* 1729 */         if ((this.fromClass.getClassLoader() != null) && (this.loaded))
/*      */         {
/* 1731 */           int i = ClassLoader.loadedLibraryNames.size();
/* 1732 */           for (int j = 0; j < i; j++) {
/* 1733 */             if (this.name.equals(ClassLoader.loadedLibraryNames.elementAt(j))) {
/* 1734 */               ClassLoader.loadedLibraryNames.removeElementAt(j);
/* 1735 */               break;
/*      */             }
/*      */           }
/*      */           
/* 1739 */           ClassLoader.nativeLibraryContext.push(this);
/*      */           try {
/* 1741 */             unload(this.name, this.isBuiltin);
/*      */           } finally {
/* 1743 */             ClassLoader.nativeLibraryContext.pop();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     static Class<?> getFromClass()
/*      */     {
/* 1751 */       return ((NativeLibrary)ClassLoader.nativeLibraryContext.peek()).fromClass;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/* 1756 */   private static Vector<String> loadedLibraryNames = new Vector();
/*      */   
/*      */ 
/* 1759 */   private static Vector<NativeLibrary> systemNativeLibraries = new Vector();
/*      */   
/*      */ 
/*      */ 
/* 1763 */   private Vector<NativeLibrary> nativeLibraries = new Vector();
/*      */   
/*      */ 
/* 1766 */   private static Stack<NativeLibrary> nativeLibraryContext = new Stack();
/*      */   private static String[] usr_paths;
/*      */   private static String[] sys_paths;
/*      */   final Object assertionLock;
/*      */   
/*      */   private static String[] initializePath(String paramString)
/*      */   {
/* 1773 */     String str1 = System.getProperty(paramString, "");
/* 1774 */     String str2 = File.pathSeparator;
/* 1775 */     int i = str1.length();
/*      */     
/*      */ 
/* 1778 */     int j = str1.indexOf(str2);
/* 1779 */     int m = 0;
/* 1780 */     while (j >= 0) {
/* 1781 */       m++;
/* 1782 */       j = str1.indexOf(str2, j + 1);
/*      */     }
/*      */     
/*      */ 
/* 1786 */     String[] arrayOfString = new String[m + 1];
/*      */     
/*      */ 
/* 1789 */     m = j = 0;
/* 1790 */     int k = str1.indexOf(str2);
/* 1791 */     while (k >= 0) {
/* 1792 */       if (k - j > 0) {
/* 1793 */         arrayOfString[(m++)] = str1.substring(j, k);
/* 1794 */       } else if (k - j == 0) {
/* 1795 */         arrayOfString[(m++)] = ".";
/*      */       }
/* 1797 */       j = k + 1;
/* 1798 */       k = str1.indexOf(str2, j);
/*      */     }
/* 1800 */     arrayOfString[m] = str1.substring(j, i);
/* 1801 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void loadLibrary(Class<?> paramClass, String paramString, boolean paramBoolean)
/*      */   {
/* 1808 */     ClassLoader localClassLoader = paramClass == null ? null : paramClass.getClassLoader();
/* 1809 */     if (sys_paths == null) {
/* 1810 */       usr_paths = initializePath("java.library.path");
/* 1811 */       sys_paths = initializePath("sun.boot.library.path");
/*      */     }
/* 1813 */     if (paramBoolean) {
/* 1814 */       if (loadLibrary0(paramClass, new File(paramString))) {
/* 1815 */         return;
/*      */       }
/* 1817 */       throw new UnsatisfiedLinkError("Can't load library: " + paramString); }
/*      */     File localFile;
/* 1819 */     if (localClassLoader != null) {
/* 1820 */       String str = localClassLoader.findLibrary(paramString);
/* 1821 */       if (str != null) {
/* 1822 */         localFile = new File(str);
/* 1823 */         if (!localFile.isAbsolute()) {
/* 1824 */           throw new UnsatisfiedLinkError("ClassLoader.findLibrary failed to return an absolute path: " + str);
/*      */         }
/*      */         
/* 1827 */         if (loadLibrary0(paramClass, localFile)) {
/* 1828 */           return;
/*      */         }
/* 1830 */         throw new UnsatisfiedLinkError("Can't load " + str);
/*      */       }
/*      */     }
/* 1833 */     for (int i = 0; i < sys_paths.length; i++) {
/* 1834 */       localFile = new File(sys_paths[i], System.mapLibraryName(paramString));
/* 1835 */       if (loadLibrary0(paramClass, localFile)) {
/* 1836 */         return;
/*      */       }
/* 1838 */       localFile = ClassLoaderHelper.mapAlternativeName(localFile);
/* 1839 */       if ((localFile != null) && (loadLibrary0(paramClass, localFile))) {
/* 1840 */         return;
/*      */       }
/*      */     }
/* 1843 */     if (localClassLoader != null) {
/* 1844 */       for (i = 0; i < usr_paths.length; i++)
/*      */       {
/* 1846 */         localFile = new File(usr_paths[i], System.mapLibraryName(paramString));
/* 1847 */         if (loadLibrary0(paramClass, localFile)) {
/* 1848 */           return;
/*      */         }
/* 1850 */         localFile = ClassLoaderHelper.mapAlternativeName(localFile);
/* 1851 */         if ((localFile != null) && (loadLibrary0(paramClass, localFile))) {
/* 1852 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1857 */     throw new UnsatisfiedLinkError("no " + paramString + " in java.library.path");
/*      */   }
/*      */   
/*      */   private static boolean loadLibrary0(Class<?> paramClass, File paramFile)
/*      */   {
/* 1862 */     String str = NativeLibrary.findBuiltinLib(paramFile.getName());
/* 1863 */     boolean bool = str != null;
/* 1864 */     if (!bool) {
/* 1865 */       int i = AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */ 
/* 1868 */         public Object run() { return this.val$file.exists() ? Boolean.TRUE : null; } }) != null ? 1 : 0;
/*      */       
/*      */ 
/* 1871 */       if (i == 0) {
/* 1872 */         return false;
/*      */       }
/*      */       try {
/* 1875 */         str = paramFile.getCanonicalPath();
/*      */       } catch (IOException localIOException) {
/* 1877 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 1881 */     ClassLoader localClassLoader = paramClass == null ? null : paramClass.getClassLoader();
/* 1882 */     Vector localVector = localClassLoader != null ? localClassLoader.nativeLibraries : systemNativeLibraries;
/*      */     
/* 1884 */     synchronized (localVector) {
/* 1885 */       int j = localVector.size();
/* 1886 */       for (int k = 0; k < j; k++) {
/* 1887 */         NativeLibrary localNativeLibrary1 = (NativeLibrary)localVector.elementAt(k);
/* 1888 */         if (str.equals(localNativeLibrary1.name)) {
/* 1889 */           return true;
/*      */         }
/*      */       }
/*      */       
/* 1893 */       synchronized (loadedLibraryNames) {
/* 1894 */         if (loadedLibraryNames.contains(str)) {
/* 1895 */           throw new UnsatisfiedLinkError("Native Library " + str + " already loaded in another classloader");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1912 */         int m = nativeLibraryContext.size();
/* 1913 */         for (int n = 0; n < m; n++) {
/* 1914 */           NativeLibrary localNativeLibrary3 = (NativeLibrary)nativeLibraryContext.elementAt(n);
/* 1915 */           if (str.equals(localNativeLibrary3.name)) {
/* 1916 */             if (localClassLoader == localNativeLibrary3.fromClass.getClassLoader()) {
/* 1917 */               return true;
/*      */             }
/* 1919 */             throw new UnsatisfiedLinkError("Native Library " + str + " is being loaded in another classloader");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1926 */         NativeLibrary localNativeLibrary2 = new NativeLibrary(paramClass, str, bool);
/* 1927 */         nativeLibraryContext.push(localNativeLibrary2);
/*      */         try {
/* 1929 */           localNativeLibrary2.load(str, bool);
/*      */         } finally {
/* 1931 */           nativeLibraryContext.pop();
/*      */         }
/* 1933 */         if (localNativeLibrary2.loaded) {
/* 1934 */           loadedLibraryNames.addElement(str);
/* 1935 */           localVector.addElement(localNativeLibrary2);
/* 1936 */           return true;
/*      */         }
/* 1938 */         return false;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static long findNative(ClassLoader paramClassLoader, String paramString)
/*      */   {
/* 1945 */     Vector localVector = paramClassLoader != null ? paramClassLoader.nativeLibraries : systemNativeLibraries;
/*      */     
/* 1947 */     synchronized (localVector) {
/* 1948 */       int i = localVector.size();
/* 1949 */       for (int j = 0; j < i; j++) {
/* 1950 */         NativeLibrary localNativeLibrary = (NativeLibrary)localVector.elementAt(j);
/* 1951 */         long l = localNativeLibrary.find(paramString);
/* 1952 */         if (l != 0L)
/* 1953 */           return l;
/*      */       }
/*      */     }
/* 1956 */     return 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1966 */   private boolean defaultAssertionStatus = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1974 */   private Map<String, Boolean> packageAssertionStatus = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1981 */   Map<String, Boolean> classAssertionStatus = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDefaultAssertionStatus(boolean paramBoolean)
/*      */   {
/* 1999 */     synchronized (this.assertionLock) {
/* 2000 */       if (this.classAssertionStatus == null) {
/* 2001 */         initializeJavaAssertionMaps();
/*      */       }
/* 2003 */       this.defaultAssertionStatus = paramBoolean;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPackageAssertionStatus(String paramString, boolean paramBoolean)
/*      */   {
/* 2046 */     synchronized (this.assertionLock) {
/* 2047 */       if (this.packageAssertionStatus == null) {
/* 2048 */         initializeJavaAssertionMaps();
/*      */       }
/* 2050 */       this.packageAssertionStatus.put(paramString, Boolean.valueOf(paramBoolean));
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
/*      */   public void setClassAssertionStatus(String paramString, boolean paramBoolean)
/*      */   {
/* 2077 */     synchronized (this.assertionLock) {
/* 2078 */       if (this.classAssertionStatus == null) {
/* 2079 */         initializeJavaAssertionMaps();
/*      */       }
/* 2081 */       this.classAssertionStatus.put(paramString, Boolean.valueOf(paramBoolean));
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
/*      */   public void clearAssertionStatus()
/*      */   {
/* 2099 */     synchronized (this.assertionLock) {
/* 2100 */       this.classAssertionStatus = new HashMap();
/* 2101 */       this.packageAssertionStatus = new HashMap();
/* 2102 */       this.defaultAssertionStatus = false;
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
/*      */   boolean desiredAssertionStatus(String paramString)
/*      */   {
/* 2129 */     synchronized (this.assertionLock)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2134 */       Boolean localBoolean = (Boolean)this.classAssertionStatus.get(paramString);
/* 2135 */       if (localBoolean != null) {
/* 2136 */         return localBoolean.booleanValue();
/*      */       }
/*      */       
/* 2139 */       int i = paramString.lastIndexOf(".");
/* 2140 */       if (i < 0) {
/* 2141 */         localBoolean = (Boolean)this.packageAssertionStatus.get(null);
/* 2142 */         if (localBoolean != null)
/* 2143 */           return localBoolean.booleanValue();
/*      */       }
/* 2145 */       while (i > 0) {
/* 2146 */         paramString = paramString.substring(0, i);
/* 2147 */         localBoolean = (Boolean)this.packageAssertionStatus.get(paramString);
/* 2148 */         if (localBoolean != null)
/* 2149 */           return localBoolean.booleanValue();
/* 2150 */         i = paramString.lastIndexOf(".", i - 1);
/*      */       }
/*      */       
/*      */ 
/* 2154 */       return this.defaultAssertionStatus;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initializeJavaAssertionMaps()
/*      */   {
/* 2163 */     this.classAssertionStatus = new HashMap();
/* 2164 */     this.packageAssertionStatus = new HashMap();
/* 2165 */     AssertionStatusDirectives localAssertionStatusDirectives = retrieveDirectives();
/*      */     
/* 2167 */     for (int i = 0; i < localAssertionStatusDirectives.classes.length; i++) {
/* 2168 */       this.classAssertionStatus.put(localAssertionStatusDirectives.classes[i], 
/* 2169 */         Boolean.valueOf(localAssertionStatusDirectives.classEnabled[i]));
/*      */     }
/* 2171 */     for (i = 0; i < localAssertionStatusDirectives.packages.length; i++) {
/* 2172 */       this.packageAssertionStatus.put(localAssertionStatusDirectives.packages[i], 
/* 2173 */         Boolean.valueOf(localAssertionStatusDirectives.packageEnabled[i]));
/*      */     }
/* 2175 */     this.defaultAssertionStatus = localAssertionStatusDirectives.deflt;
/*      */   }
/*      */   
/*      */   private static native AssertionStatusDirectives retrieveDirectives();
/*      */   
/*      */   static {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */