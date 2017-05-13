/*      */ package java.util.logging;
/*      */ 
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.function.Supplier;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Logger
/*      */ {
/*  219 */   private static final Handler[] emptyHandlers = new Handler[0];
/*  220 */   private static final int offValue = Level.OFF.intValue();
/*      */   static final String SYSTEM_LOGGER_RB_NAME = "sun.util.logging.resources.logging";
/*      */   
/*      */   private static final class LoggerBundle
/*      */   {
/*      */     final String resourceBundleName;
/*      */     final ResourceBundle userBundle;
/*      */     
/*      */     private LoggerBundle(String paramString, ResourceBundle paramResourceBundle) {
/*  229 */       this.resourceBundleName = paramString;
/*  230 */       this.userBundle = paramResourceBundle;
/*      */     }
/*      */     
/*  233 */     boolean isSystemBundle() { return "sun.util.logging.resources.logging".equals(this.resourceBundleName); }
/*      */     
/*      */     static LoggerBundle get(String paramString, ResourceBundle paramResourceBundle) {
/*  236 */       if ((paramString == null) && (paramResourceBundle == null))
/*  237 */         return Logger.NO_RESOURCE_BUNDLE;
/*  238 */       if (("sun.util.logging.resources.logging".equals(paramString)) && (paramResourceBundle == null)) {
/*  239 */         return Logger.SYSTEM_BUNDLE;
/*      */       }
/*  241 */       return new LoggerBundle(paramString, paramResourceBundle);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  248 */   private static final LoggerBundle SYSTEM_BUNDLE = new LoggerBundle("sun.util.logging.resources.logging", null, null);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  253 */   private static final LoggerBundle NO_RESOURCE_BUNDLE = new LoggerBundle(null, null, null);
/*      */   
/*      */   private volatile LogManager manager;
/*      */   
/*      */   private String name;
/*  258 */   private final CopyOnWriteArrayList<Handler> handlers = new CopyOnWriteArrayList();
/*      */   
/*  260 */   private volatile LoggerBundle loggerBundle = NO_RESOURCE_BUNDLE;
/*  261 */   private volatile boolean useParentHandlers = true;
/*      */   
/*      */   private volatile Filter filter;
/*      */   
/*      */   private boolean anonymous;
/*      */   
/*      */   private ResourceBundle catalog;
/*      */   
/*      */   private String catalogName;
/*      */   
/*      */   private Locale catalogLocale;
/*  272 */   private static final Object treeLock = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile Logger parent;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList<LogManager.LoggerWeakRef> kids;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile Level levelObject;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile int levelValue;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private WeakReference<ClassLoader> callersClassLoaderRef;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final boolean isSystemLogger;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String GLOBAL_LOGGER_NAME = "global";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final Logger getGlobal()
/*      */   {
/*  321 */     LogManager.getLogManager();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  329 */     return global;
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
/*      */   @Deprecated
/*  354 */   public static final Logger global = new Logger("global");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Logger(String paramString1, String paramString2)
/*      */   {
/*  374 */     this(paramString1, paramString2, null, LogManager.getLogManager(), false);
/*      */   }
/*      */   
/*      */   Logger(String paramString1, String paramString2, Class<?> paramClass, LogManager paramLogManager, boolean paramBoolean) {
/*  378 */     this.manager = paramLogManager;
/*  379 */     this.isSystemLogger = paramBoolean;
/*  380 */     setupResourceInfo(paramString2, paramClass);
/*  381 */     this.name = paramString1;
/*  382 */     this.levelValue = Level.INFO.intValue();
/*      */   }
/*      */   
/*      */   private void setCallersClassLoaderRef(Class<?> paramClass)
/*      */   {
/*  387 */     Object localObject = paramClass != null ? paramClass.getClassLoader() : null;
/*      */     
/*  389 */     if (localObject != null) {
/*  390 */       this.callersClassLoaderRef = new WeakReference(localObject);
/*      */     }
/*      */   }
/*      */   
/*      */   private ClassLoader getCallersClassLoader()
/*      */   {
/*  396 */     return this.callersClassLoaderRef != null ? (ClassLoader)this.callersClassLoaderRef.get() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Logger(String paramString)
/*      */   {
/*  405 */     this.name = paramString;
/*  406 */     this.isSystemLogger = true;
/*  407 */     this.levelValue = Level.INFO.intValue();
/*      */   }
/*      */   
/*      */ 
/*      */   void setLogManager(LogManager paramLogManager)
/*      */   {
/*  413 */     this.manager = paramLogManager;
/*      */   }
/*      */   
/*      */   private void checkPermission() throws SecurityException {
/*  417 */     if (!this.anonymous) {
/*  418 */       if (this.manager == null)
/*      */       {
/*  420 */         this.manager = LogManager.getLogManager();
/*      */       }
/*  422 */       this.manager.checkPermission();
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
/*      */   private static class SystemLoggerHelper
/*      */   {
/*  435 */     static boolean disableCallerCheck = getBooleanProperty("sun.util.logging.disableCallerCheck");
/*      */     
/*  437 */     private static boolean getBooleanProperty(String paramString) { String str = (String)AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public String run() {
/*  440 */           return System.getProperty(this.val$key);
/*      */         }
/*  442 */       });
/*  443 */       return Boolean.valueOf(str).booleanValue();
/*      */     }
/*      */   }
/*      */   
/*      */   private static Logger demandLogger(String paramString1, String paramString2, Class<?> paramClass) {
/*  448 */     LogManager localLogManager = LogManager.getLogManager();
/*  449 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  450 */     if ((localSecurityManager != null) && (!SystemLoggerHelper.disableCallerCheck) && 
/*  451 */       (paramClass.getClassLoader() == null)) {
/*  452 */       return localLogManager.demandSystemLogger(paramString1, paramString2);
/*      */     }
/*      */     
/*  455 */     return localLogManager.demandLogger(paramString1, paramString2, paramClass);
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
/*      */   @CallerSensitive
/*      */   public static Logger getLogger(String paramString)
/*      */   {
/*  502 */     return demandLogger(paramString, null, Reflection.getCallerClass());
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
/*      */   @CallerSensitive
/*      */   public static Logger getLogger(String paramString1, String paramString2)
/*      */   {
/*  552 */     Class localClass = Reflection.getCallerClass();
/*  553 */     Logger localLogger = demandLogger(paramString1, paramString2, localClass);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  564 */     localLogger.setupResourceInfo(paramString2, localClass);
/*  565 */     return localLogger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static Logger getPlatformLogger(String paramString)
/*      */   {
/*  572 */     LogManager localLogManager = LogManager.getLogManager();
/*      */     
/*      */ 
/*      */ 
/*  576 */     Logger localLogger = localLogManager.demandSystemLogger(paramString, "sun.util.logging.resources.logging");
/*  577 */     return localLogger;
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
/*      */   public static Logger getAnonymousLogger()
/*      */   {
/*  603 */     return getAnonymousLogger(null);
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
/*      */   public static Logger getAnonymousLogger(String paramString)
/*      */   {
/*  637 */     LogManager localLogManager = LogManager.getLogManager();
/*      */     
/*  639 */     localLogManager.drainLoggerRefQueueBounded();
/*      */     
/*  641 */     Logger localLogger1 = new Logger(null, paramString, Reflection.getCallerClass(), localLogManager, false);
/*  642 */     localLogger1.anonymous = true;
/*  643 */     Logger localLogger2 = localLogManager.getLogger("");
/*  644 */     localLogger1.doSetParent(localLogger2);
/*  645 */     return localLogger1;
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
/*      */   public ResourceBundle getResourceBundle()
/*      */   {
/*  664 */     return findResourceBundle(getResourceBundleName(), true);
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
/*      */   public String getResourceBundleName()
/*      */   {
/*  681 */     return this.loggerBundle.resourceBundleName;
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
/*      */   public void setFilter(Filter paramFilter)
/*      */     throws SecurityException
/*      */   {
/*  697 */     checkPermission();
/*  698 */     this.filter = paramFilter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Filter getFilter()
/*      */   {
/*  707 */     return this.filter;
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
/*      */   public void log(LogRecord paramLogRecord)
/*      */   {
/*  720 */     if (!isLoggable(paramLogRecord.getLevel())) {
/*  721 */       return;
/*      */     }
/*  723 */     Filter localFilter = this.filter;
/*  724 */     if ((localFilter != null) && (!localFilter.isLoggable(paramLogRecord))) {
/*  725 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  731 */     Logger localLogger = this;
/*  732 */     while (localLogger != null)
/*      */     {
/*      */ 
/*  735 */       Handler[] arrayOfHandler1 = this.isSystemLogger ? localLogger.accessCheckedHandlers() : localLogger.getHandlers();
/*      */       
/*  737 */       for (Handler localHandler : arrayOfHandler1) {
/*  738 */         localHandler.publish(paramLogRecord);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  743 */       boolean bool = this.isSystemLogger ? localLogger.useParentHandlers : localLogger.getUseParentHandlers();
/*      */       
/*  745 */       if (!bool) {
/*      */         break;
/*      */       }
/*      */       
/*  749 */       localLogger = this.isSystemLogger ? localLogger.parent : localLogger.getParent();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doLog(LogRecord paramLogRecord)
/*      */   {
/*  757 */     paramLogRecord.setLoggerName(this.name);
/*  758 */     LoggerBundle localLoggerBundle = getEffectiveLoggerBundle();
/*  759 */     ResourceBundle localResourceBundle = localLoggerBundle.userBundle;
/*  760 */     String str = localLoggerBundle.resourceBundleName;
/*  761 */     if ((str != null) && (localResourceBundle != null)) {
/*  762 */       paramLogRecord.setResourceBundleName(str);
/*  763 */       paramLogRecord.setResourceBundle(localResourceBundle);
/*      */     }
/*  765 */     log(paramLogRecord);
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
/*      */   public void log(Level paramLevel, String paramString)
/*      */   {
/*  784 */     if (!isLoggable(paramLevel)) {
/*  785 */       return;
/*      */     }
/*  787 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString);
/*  788 */     doLog(localLogRecord);
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
/*      */   public void log(Level paramLevel, Supplier<String> paramSupplier)
/*      */   {
/*  805 */     if (!isLoggable(paramLevel)) {
/*  806 */       return;
/*      */     }
/*  808 */     LogRecord localLogRecord = new LogRecord(paramLevel, (String)paramSupplier.get());
/*  809 */     doLog(localLogRecord);
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
/*      */   public void log(Level paramLevel, String paramString, Object paramObject)
/*      */   {
/*  824 */     if (!isLoggable(paramLevel)) {
/*  825 */       return;
/*      */     }
/*  827 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString);
/*  828 */     Object[] arrayOfObject = { paramObject };
/*  829 */     localLogRecord.setParameters(arrayOfObject);
/*  830 */     doLog(localLogRecord);
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
/*      */   public void log(Level paramLevel, String paramString, Object[] paramArrayOfObject)
/*      */   {
/*  845 */     if (!isLoggable(paramLevel)) {
/*  846 */       return;
/*      */     }
/*  848 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString);
/*  849 */     localLogRecord.setParameters(paramArrayOfObject);
/*  850 */     doLog(localLogRecord);
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
/*      */   public void log(Level paramLevel, String paramString, Throwable paramThrowable)
/*      */   {
/*  870 */     if (!isLoggable(paramLevel)) {
/*  871 */       return;
/*      */     }
/*  873 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString);
/*  874 */     localLogRecord.setThrown(paramThrowable);
/*  875 */     doLog(localLogRecord);
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
/*      */   public void log(Level paramLevel, Throwable paramThrowable, Supplier<String> paramSupplier)
/*      */   {
/*  898 */     if (!isLoggable(paramLevel)) {
/*  899 */       return;
/*      */     }
/*  901 */     LogRecord localLogRecord = new LogRecord(paramLevel, (String)paramSupplier.get());
/*  902 */     localLogRecord.setThrown(paramThrowable);
/*  903 */     doLog(localLogRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3)
/*      */   {
/*  924 */     if (!isLoggable(paramLevel)) {
/*  925 */       return;
/*      */     }
/*  927 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString3);
/*  928 */     localLogRecord.setSourceClassName(paramString1);
/*  929 */     localLogRecord.setSourceMethodName(paramString2);
/*  930 */     doLog(localLogRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, Supplier<String> paramSupplier)
/*      */   {
/*  951 */     if (!isLoggable(paramLevel)) {
/*  952 */       return;
/*      */     }
/*  954 */     LogRecord localLogRecord = new LogRecord(paramLevel, (String)paramSupplier.get());
/*  955 */     localLogRecord.setSourceClassName(paramString1);
/*  956 */     localLogRecord.setSourceMethodName(paramString2);
/*  957 */     doLog(localLogRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3, Object paramObject)
/*      */   {
/*  976 */     if (!isLoggable(paramLevel)) {
/*  977 */       return;
/*      */     }
/*  979 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString3);
/*  980 */     localLogRecord.setSourceClassName(paramString1);
/*  981 */     localLogRecord.setSourceMethodName(paramString2);
/*  982 */     Object[] arrayOfObject = { paramObject };
/*  983 */     localLogRecord.setParameters(arrayOfObject);
/*  984 */     doLog(localLogRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
/*      */   {
/* 1003 */     if (!isLoggable(paramLevel)) {
/* 1004 */       return;
/*      */     }
/* 1006 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString3);
/* 1007 */     localLogRecord.setSourceClassName(paramString1);
/* 1008 */     localLogRecord.setSourceMethodName(paramString2);
/* 1009 */     localLogRecord.setParameters(paramArrayOfObject);
/* 1010 */     doLog(localLogRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3, Throwable paramThrowable)
/*      */   {
/* 1034 */     if (!isLoggable(paramLevel)) {
/* 1035 */       return;
/*      */     }
/* 1037 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString3);
/* 1038 */     localLogRecord.setSourceClassName(paramString1);
/* 1039 */     localLogRecord.setSourceMethodName(paramString2);
/* 1040 */     localLogRecord.setThrown(paramThrowable);
/* 1041 */     doLog(localLogRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, Throwable paramThrowable, Supplier<String> paramSupplier)
/*      */   {
/* 1068 */     if (!isLoggable(paramLevel)) {
/* 1069 */       return;
/*      */     }
/* 1071 */     LogRecord localLogRecord = new LogRecord(paramLevel, (String)paramSupplier.get());
/* 1072 */     localLogRecord.setSourceClassName(paramString1);
/* 1073 */     localLogRecord.setSourceMethodName(paramString2);
/* 1074 */     localLogRecord.setThrown(paramThrowable);
/* 1075 */     doLog(localLogRecord);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doLog(LogRecord paramLogRecord, String paramString)
/*      */   {
/* 1087 */     paramLogRecord.setLoggerName(this.name);
/* 1088 */     if (paramString != null) {
/* 1089 */       paramLogRecord.setResourceBundleName(paramString);
/* 1090 */       paramLogRecord.setResourceBundle(findResourceBundle(paramString, false));
/*      */     }
/* 1092 */     log(paramLogRecord);
/*      */   }
/*      */   
/*      */   private void doLog(LogRecord paramLogRecord, ResourceBundle paramResourceBundle)
/*      */   {
/* 1097 */     paramLogRecord.setLoggerName(this.name);
/* 1098 */     if (paramResourceBundle != null) {
/* 1099 */       paramLogRecord.setResourceBundleName(paramResourceBundle.getBaseBundleName());
/* 1100 */       paramLogRecord.setResourceBundle(paramResourceBundle);
/*      */     }
/* 1102 */     log(paramLogRecord);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4)
/*      */   {
/* 1130 */     if (!isLoggable(paramLevel)) {
/* 1131 */       return;
/*      */     }
/* 1133 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString4);
/* 1134 */     localLogRecord.setSourceClassName(paramString1);
/* 1135 */     localLogRecord.setSourceMethodName(paramString2);
/* 1136 */     doLog(localLogRecord, paramString3);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4, Object paramObject)
/*      */   {
/* 1165 */     if (!isLoggable(paramLevel)) {
/* 1166 */       return;
/*      */     }
/* 1168 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString4);
/* 1169 */     localLogRecord.setSourceClassName(paramString1);
/* 1170 */     localLogRecord.setSourceMethodName(paramString2);
/* 1171 */     Object[] arrayOfObject = { paramObject };
/* 1172 */     localLogRecord.setParameters(arrayOfObject);
/* 1173 */     doLog(localLogRecord, paramString3);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4, Object[] paramArrayOfObject)
/*      */   {
/* 1202 */     if (!isLoggable(paramLevel)) {
/* 1203 */       return;
/*      */     }
/* 1205 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString4);
/* 1206 */     localLogRecord.setSourceClassName(paramString1);
/* 1207 */     localLogRecord.setSourceMethodName(paramString2);
/* 1208 */     localLogRecord.setParameters(paramArrayOfObject);
/* 1209 */     doLog(localLogRecord, paramString3);
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
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, ResourceBundle paramResourceBundle, String paramString3, Object... paramVarArgs)
/*      */   {
/* 1235 */     if (!isLoggable(paramLevel)) {
/* 1236 */       return;
/*      */     }
/* 1238 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString3);
/* 1239 */     localLogRecord.setSourceClassName(paramString1);
/* 1240 */     localLogRecord.setSourceMethodName(paramString2);
/* 1241 */     if ((paramVarArgs != null) && (paramVarArgs.length != 0)) {
/* 1242 */       localLogRecord.setParameters(paramVarArgs);
/*      */     }
/* 1244 */     doLog(localLogRecord, paramResourceBundle);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4, Throwable paramThrowable)
/*      */   {
/* 1278 */     if (!isLoggable(paramLevel)) {
/* 1279 */       return;
/*      */     }
/* 1281 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString4);
/* 1282 */     localLogRecord.setSourceClassName(paramString1);
/* 1283 */     localLogRecord.setSourceMethodName(paramString2);
/* 1284 */     localLogRecord.setThrown(paramThrowable);
/* 1285 */     doLog(localLogRecord, paramString3);
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
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, ResourceBundle paramResourceBundle, String paramString3, Throwable paramThrowable)
/*      */   {
/* 1316 */     if (!isLoggable(paramLevel)) {
/* 1317 */       return;
/*      */     }
/* 1319 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString3);
/* 1320 */     localLogRecord.setSourceClassName(paramString1);
/* 1321 */     localLogRecord.setSourceMethodName(paramString2);
/* 1322 */     localLogRecord.setThrown(paramThrowable);
/* 1323 */     doLog(localLogRecord, paramResourceBundle);
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
/*      */   public void entering(String paramString1, String paramString2)
/*      */   {
/* 1341 */     logp(Level.FINER, paramString1, paramString2, "ENTRY");
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
/*      */   public void entering(String paramString1, String paramString2, Object paramObject)
/*      */   {
/* 1357 */     logp(Level.FINER, paramString1, paramString2, "ENTRY {0}", paramObject);
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
/*      */   public void entering(String paramString1, String paramString2, Object[] paramArrayOfObject)
/*      */   {
/* 1374 */     String str = "ENTRY";
/* 1375 */     if (paramArrayOfObject == null) {
/* 1376 */       logp(Level.FINER, paramString1, paramString2, str);
/* 1377 */       return;
/*      */     }
/* 1379 */     if (!isLoggable(Level.FINER)) return;
/* 1380 */     for (int i = 0; i < paramArrayOfObject.length; i++) {
/* 1381 */       str = str + " {" + i + "}";
/*      */     }
/* 1383 */     logp(Level.FINER, paramString1, paramString2, str, paramArrayOfObject);
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
/*      */   public void exiting(String paramString1, String paramString2)
/*      */   {
/* 1397 */     logp(Level.FINER, paramString1, paramString2, "RETURN");
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
/*      */   public void exiting(String paramString1, String paramString2, Object paramObject)
/*      */   {
/* 1414 */     logp(Level.FINER, paramString1, paramString2, "RETURN {0}", paramObject);
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
/*      */   public void throwing(String paramString1, String paramString2, Throwable paramThrowable)
/*      */   {
/* 1439 */     if (!isLoggable(Level.FINER)) {
/* 1440 */       return;
/*      */     }
/* 1442 */     LogRecord localLogRecord = new LogRecord(Level.FINER, "THROW");
/* 1443 */     localLogRecord.setSourceClassName(paramString1);
/* 1444 */     localLogRecord.setSourceMethodName(paramString2);
/* 1445 */     localLogRecord.setThrown(paramThrowable);
/* 1446 */     doLog(localLogRecord);
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
/*      */   public void severe(String paramString)
/*      */   {
/* 1463 */     log(Level.SEVERE, paramString);
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
/*      */   public void warning(String paramString)
/*      */   {
/* 1476 */     log(Level.WARNING, paramString);
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
/*      */   public void info(String paramString)
/*      */   {
/* 1489 */     log(Level.INFO, paramString);
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
/*      */   public void config(String paramString)
/*      */   {
/* 1502 */     log(Level.CONFIG, paramString);
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
/*      */   public void fine(String paramString)
/*      */   {
/* 1515 */     log(Level.FINE, paramString);
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
/*      */   public void finer(String paramString)
/*      */   {
/* 1528 */     log(Level.FINER, paramString);
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
/*      */   public void finest(String paramString)
/*      */   {
/* 1541 */     log(Level.FINEST, paramString);
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
/*      */   public void severe(Supplier<String> paramSupplier)
/*      */   {
/* 1563 */     log(Level.SEVERE, paramSupplier);
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
/*      */   public void warning(Supplier<String> paramSupplier)
/*      */   {
/* 1580 */     log(Level.WARNING, paramSupplier);
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
/*      */   public void info(Supplier<String> paramSupplier)
/*      */   {
/* 1597 */     log(Level.INFO, paramSupplier);
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
/*      */   public void config(Supplier<String> paramSupplier)
/*      */   {
/* 1614 */     log(Level.CONFIG, paramSupplier);
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
/*      */   public void fine(Supplier<String> paramSupplier)
/*      */   {
/* 1631 */     log(Level.FINE, paramSupplier);
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
/*      */   public void finer(Supplier<String> paramSupplier)
/*      */   {
/* 1648 */     log(Level.FINER, paramSupplier);
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
/*      */   public void finest(Supplier<String> paramSupplier)
/*      */   {
/* 1665 */     log(Level.FINEST, paramSupplier);
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
/*      */   public void setLevel(Level paramLevel)
/*      */     throws SecurityException
/*      */   {
/* 1688 */     checkPermission();
/* 1689 */     synchronized (treeLock) {
/* 1690 */       this.levelObject = paramLevel;
/* 1691 */       updateEffectiveLevel();
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean isLevelInitialized() {
/* 1696 */     return this.levelObject != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Level getLevel()
/*      */   {
/* 1707 */     return this.levelObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLoggable(Level paramLevel)
/*      */   {
/* 1719 */     if ((paramLevel.intValue() < this.levelValue) || (this.levelValue == offValue)) {
/* 1720 */       return false;
/*      */     }
/* 1722 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/* 1730 */     return this.name;
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
/*      */   public void addHandler(Handler paramHandler)
/*      */     throws SecurityException
/*      */   {
/* 1747 */     paramHandler.getClass();
/* 1748 */     checkPermission();
/* 1749 */     this.handlers.add(paramHandler);
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
/*      */   public void removeHandler(Handler paramHandler)
/*      */     throws SecurityException
/*      */   {
/* 1763 */     checkPermission();
/* 1764 */     if (paramHandler == null) {
/* 1765 */       return;
/*      */     }
/* 1767 */     this.handlers.remove(paramHandler);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Handler[] getHandlers()
/*      */   {
/* 1776 */     return accessCheckedHandlers();
/*      */   }
/*      */   
/*      */ 
/*      */   Handler[] accessCheckedHandlers()
/*      */   {
/* 1782 */     return (Handler[])this.handlers.toArray(emptyHandlers);
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
/*      */   public void setUseParentHandlers(boolean paramBoolean)
/*      */   {
/* 1798 */     checkPermission();
/* 1799 */     this.useParentHandlers = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getUseParentHandlers()
/*      */   {
/* 1809 */     return this.useParentHandlers;
/*      */   }
/*      */   
/*      */   private static ResourceBundle findSystemResourceBundle(Locale paramLocale)
/*      */   {
/* 1814 */     (ResourceBundle)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public ResourceBundle run() {
/*      */         try {
/* 1818 */           return ResourceBundle.getBundle("sun.util.logging.resources.logging", this.val$locale, 
/*      */           
/* 1820 */             ClassLoader.getSystemClassLoader());
/*      */         } catch (MissingResourceException localMissingResourceException) {
/* 1822 */           throw new InternalError(localMissingResourceException.toString());
/*      */         }
/*      */       }
/*      */     });
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
/*      */   private synchronized ResourceBundle findResourceBundle(String paramString, boolean paramBoolean)
/*      */   {
/* 1849 */     if (paramString == null) {
/* 1850 */       return null;
/*      */     }
/*      */     
/* 1853 */     Locale localLocale = Locale.getDefault();
/* 1854 */     LoggerBundle localLoggerBundle = this.loggerBundle;
/*      */     
/*      */ 
/* 1857 */     if ((localLoggerBundle.userBundle != null) && 
/* 1858 */       (paramString.equals(localLoggerBundle.resourceBundleName)))
/* 1859 */       return localLoggerBundle.userBundle;
/* 1860 */     if ((this.catalog != null) && (localLocale.equals(this.catalogLocale)) && 
/* 1861 */       (paramString.equals(this.catalogName))) {
/* 1862 */       return this.catalog;
/*      */     }
/*      */     
/* 1865 */     if (paramString.equals("sun.util.logging.resources.logging")) {
/* 1866 */       this.catalog = findSystemResourceBundle(localLocale);
/* 1867 */       this.catalogName = paramString;
/* 1868 */       this.catalogLocale = localLocale;
/* 1869 */       return this.catalog;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1874 */     ClassLoader localClassLoader1 = Thread.currentThread().getContextClassLoader();
/* 1875 */     if (localClassLoader1 == null) {
/* 1876 */       localClassLoader1 = ClassLoader.getSystemClassLoader();
/*      */     }
/*      */     try {
/* 1879 */       this.catalog = ResourceBundle.getBundle(paramString, localLocale, localClassLoader1);
/* 1880 */       this.catalogName = paramString;
/* 1881 */       this.catalogLocale = localLocale;
/* 1882 */       return this.catalog;
/*      */ 
/*      */     }
/*      */     catch (MissingResourceException localMissingResourceException1)
/*      */     {
/*      */ 
/* 1888 */       if (paramBoolean)
/*      */       {
/* 1890 */         ClassLoader localClassLoader2 = getCallersClassLoader();
/*      */         
/* 1892 */         if ((localClassLoader2 == null) || (localClassLoader2 == localClassLoader1)) {
/* 1893 */           return null;
/*      */         }
/*      */         try
/*      */         {
/* 1897 */           this.catalog = ResourceBundle.getBundle(paramString, localLocale, localClassLoader2);
/*      */           
/* 1899 */           this.catalogName = paramString;
/* 1900 */           this.catalogLocale = localLocale;
/* 1901 */           return this.catalog;
/*      */         } catch (MissingResourceException localMissingResourceException2) {
/* 1903 */           return null;
/*      */         }
/*      */       } }
/* 1906 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void setupResourceInfo(String paramString, Class<?> paramClass)
/*      */   {
/* 1918 */     LoggerBundle localLoggerBundle = this.loggerBundle;
/* 1919 */     if (localLoggerBundle.resourceBundleName != null)
/*      */     {
/*      */ 
/* 1922 */       if (localLoggerBundle.resourceBundleName.equals(paramString))
/*      */       {
/* 1924 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1928 */       throw new IllegalArgumentException(localLoggerBundle.resourceBundleName + " != " + paramString);
/*      */     }
/*      */     
/*      */ 
/* 1932 */     if (paramString == null) {
/* 1933 */       return;
/*      */     }
/*      */     
/* 1936 */     setCallersClassLoaderRef(paramClass);
/* 1937 */     if ((this.isSystemLogger) && (getCallersClassLoader() != null)) {
/* 1938 */       checkPermission();
/*      */     }
/* 1940 */     if (findResourceBundle(paramString, true) == null)
/*      */     {
/*      */ 
/*      */ 
/* 1944 */       this.callersClassLoaderRef = null;
/* 1945 */       throw new MissingResourceException("Can't find " + paramString + " bundle", paramString, "");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1950 */     assert (localLoggerBundle.userBundle == null);
/* 1951 */     this.loggerBundle = LoggerBundle.get(paramString, null);
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
/*      */   public void setResourceBundle(ResourceBundle paramResourceBundle)
/*      */   {
/* 1970 */     checkPermission();
/*      */     
/*      */ 
/* 1973 */     String str = paramResourceBundle.getBaseBundleName();
/*      */     
/*      */ 
/* 1976 */     if ((str == null) || (str.isEmpty())) {
/* 1977 */       throw new IllegalArgumentException("resource bundle must have a name");
/*      */     }
/*      */     
/* 1980 */     synchronized (this) {
/* 1981 */       LoggerBundle localLoggerBundle = this.loggerBundle;
/*      */       
/* 1983 */       int i = (localLoggerBundle.resourceBundleName == null) || (localLoggerBundle.resourceBundleName.equals(str)) ? 1 : 0;
/*      */       
/* 1985 */       if (i == 0) {
/* 1986 */         throw new IllegalArgumentException("can't replace resource bundle");
/*      */       }
/*      */       
/*      */ 
/* 1990 */       this.loggerBundle = LoggerBundle.get(str, paramResourceBundle);
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
/*      */   public Logger getParent()
/*      */   {
/* 2013 */     return this.parent;
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
/*      */   public void setParent(Logger paramLogger)
/*      */   {
/* 2027 */     if (paramLogger == null) {
/* 2028 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/* 2032 */     if (this.manager == null) {
/* 2033 */       this.manager = LogManager.getLogManager();
/*      */     }
/* 2035 */     this.manager.checkPermission();
/*      */     
/* 2037 */     doSetParent(paramLogger);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doSetParent(Logger paramLogger)
/*      */   {
/* 2047 */     synchronized (treeLock)
/*      */     {
/*      */ 
/* 2050 */       LogManager.LoggerWeakRef localLoggerWeakRef = null;
/* 2051 */       Iterator localIterator; if (this.parent != null)
/*      */       {
/* 2053 */         for (localIterator = this.parent.kids.iterator(); localIterator.hasNext();) {
/* 2054 */           localLoggerWeakRef = (LogManager.LoggerWeakRef)localIterator.next();
/* 2055 */           Logger localLogger = (Logger)localLoggerWeakRef.get();
/* 2056 */           if (localLogger == this)
/*      */           {
/* 2058 */             localIterator.remove();
/* 2059 */             break;
/*      */           }
/* 2061 */           localLoggerWeakRef = null;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2068 */       this.parent = paramLogger;
/* 2069 */       if (this.parent.kids == null) {
/* 2070 */         this.parent.kids = new ArrayList(2);
/*      */       }
/* 2072 */       if (localLoggerWeakRef == null)
/*      */       {
/* 2074 */         LogManager tmp120_117 = this.manager;tmp120_117.getClass();localLoggerWeakRef = new LogManager.LoggerWeakRef(tmp120_117, this);
/*      */       }
/* 2076 */       localLoggerWeakRef.setParentRef(new WeakReference(this.parent));
/* 2077 */       this.parent.kids.add(localLoggerWeakRef);
/*      */       
/*      */ 
/*      */ 
/* 2081 */       updateEffectiveLevel();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   final void removeChildLogger(LogManager.LoggerWeakRef paramLoggerWeakRef)
/*      */   {
/*      */     Iterator localIterator;
/*      */     
/* 2090 */     synchronized (treeLock) {
/* 2091 */       for (localIterator = this.kids.iterator(); localIterator.hasNext();) {
/* 2092 */         LogManager.LoggerWeakRef localLoggerWeakRef = (LogManager.LoggerWeakRef)localIterator.next();
/* 2093 */         if (localLoggerWeakRef == paramLoggerWeakRef) {
/* 2094 */           localIterator.remove();
/* 2095 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void updateEffectiveLevel()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/* 2109 */     if (this.levelObject != null) {
/* 2110 */       i = this.levelObject.intValue();
/*      */     }
/* 2112 */     else if (this.parent != null) {
/* 2113 */       i = this.parent.levelValue;
/*      */     }
/*      */     else {
/* 2116 */       i = Level.INFO.intValue();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2121 */     if (this.levelValue == i) {
/* 2122 */       return;
/*      */     }
/*      */     
/* 2125 */     this.levelValue = i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2130 */     if (this.kids != null) {
/* 2131 */       for (int j = 0; j < this.kids.size(); j++) {
/* 2132 */         LogManager.LoggerWeakRef localLoggerWeakRef = (LogManager.LoggerWeakRef)this.kids.get(j);
/* 2133 */         Logger localLogger = (Logger)localLoggerWeakRef.get();
/* 2134 */         if (localLogger != null) {
/* 2135 */           localLogger.updateEffectiveLevel();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private LoggerBundle getEffectiveLoggerBundle()
/*      */   {
/* 2146 */     LoggerBundle localLoggerBundle1 = this.loggerBundle;
/* 2147 */     if (localLoggerBundle1.isSystemBundle()) {
/* 2148 */       return SYSTEM_BUNDLE;
/*      */     }
/*      */     
/*      */ 
/* 2152 */     ResourceBundle localResourceBundle = getResourceBundle();
/* 2153 */     if ((localResourceBundle != null) && (localResourceBundle == localLoggerBundle1.userBundle))
/* 2154 */       return localLoggerBundle1;
/* 2155 */     if (localResourceBundle != null)
/*      */     {
/*      */ 
/* 2158 */       localObject = getResourceBundleName();
/* 2159 */       return LoggerBundle.get((String)localObject, localResourceBundle);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2164 */     Object localObject = this.parent;
/* 2165 */     while (localObject != null) {
/* 2166 */       LoggerBundle localLoggerBundle2 = ((Logger)localObject).loggerBundle;
/* 2167 */       if (localLoggerBundle2.isSystemBundle()) {
/* 2168 */         return SYSTEM_BUNDLE;
/*      */       }
/* 2170 */       if (localLoggerBundle2.userBundle != null) {
/* 2171 */         return localLoggerBundle2;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2177 */       String str = this.isSystemLogger ? null : ((Logger)localObject).isSystemLogger ? localLoggerBundle2.resourceBundleName : ((Logger)localObject).getResourceBundleName();
/* 2178 */       if (str != null) {
/* 2179 */         return LoggerBundle.get(str, 
/* 2180 */           findResourceBundle(str, true));
/*      */       }
/* 2182 */       localObject = this.isSystemLogger ? ((Logger)localObject).parent : ((Logger)localObject).getParent();
/*      */     }
/* 2184 */     return NO_RESOURCE_BUNDLE;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */