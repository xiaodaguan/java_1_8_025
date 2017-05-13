/*      */ package java.lang;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.Console;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Executable;
/*      */ import java.nio.channels.Channel;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.PropertyPermission;
/*      */ import sun.misc.JavaIOAccess;
/*      */ import sun.misc.JavaLangAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.misc.VM;
/*      */ import sun.misc.Version;
/*      */ import sun.nio.ch.Interruptible;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.ConstantPool;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.annotation.AnnotationType;
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
/*      */ public final class System
/*      */ {
/*   83 */   public static final InputStream in = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  110 */   public static final PrintStream out = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  124 */   public static final PrintStream err = null;
/*      */   
/*      */ 
/*      */ 
/*  128 */   private static volatile SecurityManager security = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native void registerNatives();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setIn(InputStream paramInputStream)
/*      */   {
/*  151 */     checkIO();
/*  152 */     setIn0(paramInputStream);
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
/*      */   public static void setOut(PrintStream paramPrintStream)
/*      */   {
/*  175 */     checkIO();
/*  176 */     setOut0(paramPrintStream);
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
/*      */   public static void setErr(PrintStream paramPrintStream)
/*      */   {
/*  199 */     checkIO();
/*  200 */     setErr0(paramPrintStream);
/*      */   }
/*      */   
/*  203 */   private static volatile Console cons = null;
/*      */   
/*      */ 
/*      */   private static Properties props;
/*      */   
/*      */   private static String lineSeparator;
/*      */   
/*      */ 
/*      */   public static Console console()
/*      */   {
/*  213 */     if (cons == null) {
/*  214 */       synchronized (System.class) {
/*  215 */         cons = SharedSecrets.getJavaIOAccess().console();
/*      */       }
/*      */     }
/*  218 */     return cons;
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
/*      */   public static Channel inheritedChannel()
/*      */     throws IOException
/*      */   {
/*  247 */     return SelectorProvider.provider().inheritedChannel();
/*      */   }
/*      */   
/*      */   private static void checkIO() {
/*  251 */     SecurityManager localSecurityManager = getSecurityManager();
/*  252 */     if (localSecurityManager != null) {
/*  253 */       localSecurityManager.checkPermission(new RuntimePermission("setIO"));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native void setIn0(InputStream paramInputStream);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native void setOut0(PrintStream paramPrintStream);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native void setErr0(PrintStream paramPrintStream);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setSecurityManager(SecurityManager paramSecurityManager)
/*      */   {
/*      */     try
/*      */     {
/*  287 */       paramSecurityManager.checkPackageAccess("java.lang");
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*  291 */     setSecurityManager0(paramSecurityManager);
/*      */   }
/*      */   
/*      */   private static synchronized void setSecurityManager0(SecurityManager paramSecurityManager)
/*      */   {
/*  296 */     SecurityManager localSecurityManager = getSecurityManager();
/*  297 */     if (localSecurityManager != null)
/*      */     {
/*      */ 
/*  300 */       localSecurityManager.checkPermission(new RuntimePermission("setSecurityManager"));
/*      */     }
/*      */     
/*      */ 
/*  304 */     if ((paramSecurityManager != null) && (paramSecurityManager.getClass().getClassLoader() != null))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  313 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Object run() {
/*  316 */           this.val$s.getClass().getProtectionDomain().implies(SecurityConstants.ALL_PERMISSION);
/*  317 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*      */     
/*  322 */     security = paramSecurityManager;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static SecurityManager getSecurityManager()
/*      */   {
/*  334 */     return security;
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
/*      */   public static native long currentTimeMillis();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native long nanoTime();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native void arraycopy(Object paramObject1, int paramInt1, Object paramObject2, int paramInt2, int paramInt3);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native int identityHashCode(Object paramObject);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native Properties initProperties(Properties paramProperties);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Properties getProperties()
/*      */   {
/*  625 */     SecurityManager localSecurityManager = getSecurityManager();
/*  626 */     if (localSecurityManager != null) {
/*  627 */       localSecurityManager.checkPropertiesAccess();
/*      */     }
/*      */     
/*  630 */     return props;
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
/*      */   public static String lineSeparator()
/*      */   {
/*  645 */     return lineSeparator;
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
/*      */   public static void setProperties(Properties paramProperties)
/*      */   {
/*  673 */     SecurityManager localSecurityManager = getSecurityManager();
/*  674 */     if (localSecurityManager != null) {
/*  675 */       localSecurityManager.checkPropertiesAccess();
/*      */     }
/*  677 */     if (paramProperties == null) {
/*  678 */       paramProperties = new Properties();
/*  679 */       initProperties(paramProperties);
/*      */     }
/*  681 */     props = paramProperties;
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
/*      */   public static String getProperty(String paramString)
/*      */   {
/*  711 */     checkKey(paramString);
/*  712 */     SecurityManager localSecurityManager = getSecurityManager();
/*  713 */     if (localSecurityManager != null) {
/*  714 */       localSecurityManager.checkPropertyAccess(paramString);
/*      */     }
/*      */     
/*  717 */     return props.getProperty(paramString);
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
/*      */   public static String getProperty(String paramString1, String paramString2)
/*      */   {
/*  747 */     checkKey(paramString1);
/*  748 */     SecurityManager localSecurityManager = getSecurityManager();
/*  749 */     if (localSecurityManager != null) {
/*  750 */       localSecurityManager.checkPropertyAccess(paramString1);
/*      */     }
/*      */     
/*  753 */     return props.getProperty(paramString1, paramString2);
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
/*      */   public static String setProperty(String paramString1, String paramString2)
/*      */   {
/*  786 */     checkKey(paramString1);
/*  787 */     SecurityManager localSecurityManager = getSecurityManager();
/*  788 */     if (localSecurityManager != null) {
/*  789 */       localSecurityManager.checkPermission(new PropertyPermission(paramString1, "write"));
/*      */     }
/*      */     
/*      */ 
/*  793 */     return (String)props.setProperty(paramString1, paramString2);
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
/*      */   public static String clearProperty(String paramString)
/*      */   {
/*  824 */     checkKey(paramString);
/*  825 */     SecurityManager localSecurityManager = getSecurityManager();
/*  826 */     if (localSecurityManager != null) {
/*  827 */       localSecurityManager.checkPermission(new PropertyPermission(paramString, "write"));
/*      */     }
/*      */     
/*  830 */     return (String)props.remove(paramString);
/*      */   }
/*      */   
/*      */   private static void checkKey(String paramString) {
/*  834 */     if (paramString == null) {
/*  835 */       throw new NullPointerException("key can't be null");
/*      */     }
/*  837 */     if (paramString.equals("")) {
/*  838 */       throw new IllegalArgumentException("key can't be empty");
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
/*      */   public static String getenv(String paramString)
/*      */   {
/*  889 */     SecurityManager localSecurityManager = getSecurityManager();
/*  890 */     if (localSecurityManager != null) {
/*  891 */       localSecurityManager.checkPermission(new RuntimePermission("getenv." + paramString));
/*      */     }
/*      */     
/*  894 */     return ProcessEnvironment.getenv(paramString);
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
/*      */   public static Map<String, String> getenv()
/*      */   {
/*  939 */     SecurityManager localSecurityManager = getSecurityManager();
/*  940 */     if (localSecurityManager != null) {
/*  941 */       localSecurityManager.checkPermission(new RuntimePermission("getenv.*"));
/*      */     }
/*      */     
/*  944 */     return ProcessEnvironment.getenv();
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
/*      */   public static void exit(int paramInt)
/*      */   {
/*  968 */     Runtime.getRuntime().exit(paramInt);
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
/*      */   public static void gc()
/*      */   {
/*  990 */     Runtime.getRuntime().gc();
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
/*      */   public static void runFinalization()
/*      */   {
/* 1012 */     Runtime.getRuntime().runFinalization();
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
/*      */   public static void runFinalizersOnExit(boolean paramBoolean)
/*      */   {
/* 1042 */     Runtime.runFinalizersOnExit(paramBoolean);
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
/*      */   @CallerSensitive
/*      */   public static void load(String paramString)
/*      */   {
/* 1083 */     Runtime.getRuntime().load0(Reflection.getCallerClass(), paramString);
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
/*      */   @CallerSensitive
/*      */   public static void loadLibrary(String paramString)
/*      */   {
/* 1119 */     Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native String mapLibraryName(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static PrintStream newPrintStream(FileOutputStream paramFileOutputStream, String paramString)
/*      */   {
/* 1140 */     if (paramString != null) {
/*      */       try {
/* 1142 */         return new PrintStream(new BufferedOutputStream(paramFileOutputStream, 128), true, paramString);
/*      */       } catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/*      */     }
/* 1145 */     return new PrintStream(new BufferedOutputStream(paramFileOutputStream, 128), true);
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
/*      */   private static void initializeSystemClass()
/*      */   {
/* 1162 */     props = new Properties();
/* 1163 */     initProperties(props);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1179 */     VM.saveAndRemoveProperties(props);
/*      */     
/*      */ 
/* 1182 */     lineSeparator = props.getProperty("line.separator");
/* 1183 */     Version.init();
/*      */     
/* 1185 */     FileInputStream localFileInputStream = new FileInputStream(FileDescriptor.in);
/* 1186 */     FileOutputStream localFileOutputStream1 = new FileOutputStream(FileDescriptor.out);
/* 1187 */     FileOutputStream localFileOutputStream2 = new FileOutputStream(FileDescriptor.err);
/* 1188 */     setIn0(new BufferedInputStream(localFileInputStream));
/* 1189 */     setOut0(newPrintStream(localFileOutputStream1, props.getProperty("sun.stdout.encoding")));
/* 1190 */     setErr0(newPrintStream(localFileOutputStream2, props.getProperty("sun.stderr.encoding")));
/*      */     
/*      */ 
/*      */ 
/* 1194 */     loadLibrary("zip");
/*      */     
/*      */ 
/* 1197 */     Terminator.setup();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1203 */     VM.initializeOSEnvironment();
/*      */     
/*      */ 
/*      */ 
/* 1207 */     Thread localThread = Thread.currentThread();
/* 1208 */     localThread.getThreadGroup().add(localThread);
/*      */     
/*      */ 
/* 1211 */     setJavaLangAccess();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1217 */     VM.booted();
/*      */   }
/*      */   
/*      */   private static void setJavaLangAccess()
/*      */   {
/* 1222 */     SharedSecrets.setJavaLangAccess(new JavaLangAccess() {
/*      */       public ConstantPool getConstantPool(Class<?> paramAnonymousClass) {
/* 1224 */         return paramAnonymousClass.getConstantPool();
/*      */       }
/*      */       
/* 1227 */       public boolean casAnnotationType(Class<?> paramAnonymousClass, AnnotationType paramAnonymousAnnotationType1, AnnotationType paramAnonymousAnnotationType2) { return paramAnonymousClass.casAnnotationType(paramAnonymousAnnotationType1, paramAnonymousAnnotationType2); }
/*      */       
/*      */       public AnnotationType getAnnotationType(Class<?> paramAnonymousClass) {
/* 1230 */         return paramAnonymousClass.getAnnotationType();
/*      */       }
/*      */       
/* 1233 */       public Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(Class<?> paramAnonymousClass) { return paramAnonymousClass.getDeclaredAnnotationMap(); }
/*      */       
/*      */       public byte[] getRawClassAnnotations(Class<?> paramAnonymousClass) {
/* 1236 */         return paramAnonymousClass.getRawAnnotations();
/*      */       }
/*      */       
/* 1239 */       public byte[] getRawClassTypeAnnotations(Class<?> paramAnonymousClass) { return paramAnonymousClass.getRawTypeAnnotations(); }
/*      */       
/*      */       public byte[] getRawExecutableTypeAnnotations(Executable paramAnonymousExecutable) {
/* 1242 */         return Class.getExecutableTypeAnnotationBytes(paramAnonymousExecutable);
/*      */       }
/*      */       
/*      */       public <E extends Enum<E>> E[] getEnumConstantsShared(Class<E> paramAnonymousClass) {
/* 1246 */         return (Enum[])paramAnonymousClass.getEnumConstantsShared();
/*      */       }
/*      */       
/* 1249 */       public void blockedOn(Thread paramAnonymousThread, Interruptible paramAnonymousInterruptible) { paramAnonymousThread.blockedOn(paramAnonymousInterruptible); }
/*      */       
/*      */       public void registerShutdownHook(int paramAnonymousInt, boolean paramAnonymousBoolean, Runnable paramAnonymousRunnable) {
/* 1252 */         Shutdown.add(paramAnonymousInt, paramAnonymousBoolean, paramAnonymousRunnable);
/*      */       }
/*      */       
/* 1255 */       public int getStackTraceDepth(Throwable paramAnonymousThrowable) { return paramAnonymousThrowable.getStackTraceDepth(); }
/*      */       
/*      */       public StackTraceElement getStackTraceElement(Throwable paramAnonymousThrowable, int paramAnonymousInt) {
/* 1258 */         return paramAnonymousThrowable.getStackTraceElement(paramAnonymousInt);
/*      */       }
/*      */       
/* 1261 */       public String newStringUnsafe(char[] paramAnonymousArrayOfChar) { return new String(paramAnonymousArrayOfChar, true); }
/*      */       
/*      */       public Thread newThreadWithAcc(Runnable paramAnonymousRunnable, AccessControlContext paramAnonymousAccessControlContext) {
/* 1264 */         return new Thread(paramAnonymousRunnable, paramAnonymousAccessControlContext);
/*      */       }
/*      */       
/* 1267 */       public void invokeFinalize(Object paramAnonymousObject) throws Throwable { paramAnonymousObject.finalize(); }
/*      */     });
/*      */   }
/*      */   
/*      */   static {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/System.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */