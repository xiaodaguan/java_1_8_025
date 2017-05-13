/*      */ package java.util.logging;
/*      */ 
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Objects;
/*      */ import java.util.Properties;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.misc.JavaAWTAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LogManager
/*      */ {
/*  156 */   private volatile Properties props = new Properties();
/*  157 */   static { defaultLevel = Level.INFO; }
/*      */   
/*      */ 
/*      */ 
/*  161 */   private final Map<Object, Integer> listenerMap = new HashMap();
/*      */   
/*      */ 
/*  164 */   private final LoggerContext systemContext = new SystemLoggerContext();
/*  165 */   private final LoggerContext userContext = new LoggerContext(null);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  176 */   private boolean initializedGlobalHandlers = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  181 */   private static final LogManager manager = (LogManager)AccessController.doPrivileged(new PrivilegedAction()
/*      */   {
/*      */     public LogManager run() {
/*  184 */       LogManager localLogManager = null;
/*  185 */       String str = null;
/*      */       try {
/*  187 */         str = System.getProperty("java.util.logging.manager");
/*  188 */         if (str != null) {
/*      */           try
/*      */           {
/*  191 */             Class localClass1 = ClassLoader.getSystemClassLoader().loadClass(str);
/*  192 */             localLogManager = (LogManager)localClass1.newInstance();
/*      */           }
/*      */           catch (ClassNotFoundException localClassNotFoundException) {
/*  195 */             Class localClass2 = Thread.currentThread().getContextClassLoader().loadClass(str);
/*  196 */             localLogManager = (LogManager)localClass2.newInstance();
/*      */           }
/*      */         }
/*      */       } catch (Exception localException) {
/*  200 */         System.err.println("Could not load Logmanager \"" + str + "\"");
/*  201 */         localException.printStackTrace();
/*      */       }
/*  203 */       if (localLogManager == null) {
/*  204 */         localLogManager = new LogManager();
/*      */       }
/*  206 */       return localLogManager;
/*      */     }
/*  181 */   });
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private class Cleaner
/*      */     extends Thread
/*      */   {
/*      */     private Cleaner()
/*      */     {
/*  221 */       setContextClassLoader(null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void run()
/*      */     {
/*  228 */       LogManager localLogManager = LogManager.manager;
/*      */       
/*      */ 
/*      */ 
/*  232 */       synchronized (LogManager.this)
/*      */       {
/*  234 */         LogManager.this.deathImminent = true;
/*  235 */         LogManager.this.initializedGlobalHandlers = true;
/*      */       }
/*      */       
/*      */ 
/*  239 */       LogManager.this.reset();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected LogManager()
/*      */   {
/*  251 */     this(checkSubclassPermissions());
/*      */   }
/*      */   
/*      */   private LogManager(Void paramVoid)
/*      */   {
/*      */     try
/*      */     {
/*  258 */       Runtime.getRuntime().addShutdownHook(new Cleaner(null));
/*      */     }
/*      */     catch (IllegalStateException localIllegalStateException) {}
/*      */   }
/*      */   
/*      */ 
/*      */   private static Void checkSubclassPermissions()
/*      */   {
/*  266 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  267 */     if (localSecurityManager != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  272 */       localSecurityManager.checkPermission(new RuntimePermission("shutdownHooks"));
/*  273 */       localSecurityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*      */     }
/*  275 */     return null;
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
/*  295 */   private boolean initializedCalled = false;
/*  296 */   private volatile boolean initializationDone = false;
/*      */   
/*  298 */   final void ensureLogManagerInitialized() { final LogManager localLogManager = this;
/*  299 */     if ((this.initializationDone) || (localLogManager != manager))
/*      */     {
/*      */ 
/*  302 */       return;
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
/*  313 */     synchronized (this)
/*      */     {
/*      */ 
/*      */ 
/*  317 */       int i = this.initializedCalled == true ? 1 : 0;
/*      */       
/*  319 */       assert ((this.initializedCalled) || (!this.initializationDone)) : "Initialization can't be done if initialized has not been called!";
/*      */       
/*      */ 
/*  322 */       if ((i != 0) || (this.initializationDone))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  331 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  336 */       this.initializedCalled = true;
/*      */       try {
/*  338 */         AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*  341 */             assert (LogManager.this.rootLogger == null);
/*  342 */             assert ((LogManager.this.initializedCalled) && (!LogManager.this.initializationDone));
/*      */             
/*      */ 
/*  345 */             localLogManager.readPrimordialConfiguration(); LogManager 
/*      */             
/*      */ 
/*  348 */               tmp77_74 = localLogManager;tmp77_74.getClass();localLogManager.rootLogger = new LogManager.RootLogger(tmp77_74, null);
/*  349 */             localLogManager.addLogger(localLogManager.rootLogger);
/*  350 */             if (!localLogManager.rootLogger.isLevelInitialized()) {
/*  351 */               localLogManager.rootLogger.setLevel(LogManager.defaultLevel);
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  358 */             Logger localLogger = Logger.global;
/*      */             
/*      */ 
/*      */ 
/*  362 */             localLogManager.addLogger(localLogger);
/*  363 */             return null;
/*      */           }
/*      */         });
/*      */       } finally {
/*  367 */         this.initializationDone = true;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static LogManager getLogManager()
/*      */   {
/*  377 */     if (manager != null) {
/*  378 */       manager.ensureLogManagerInitialized();
/*      */     }
/*  380 */     return manager;
/*      */   }
/*      */   
/*      */   private void readPrimordialConfiguration() {
/*  384 */     if (!this.readPrimordialConfiguration) {
/*  385 */       synchronized (this) {
/*  386 */         if (!this.readPrimordialConfiguration)
/*      */         {
/*      */ 
/*      */ 
/*  390 */           if (System.out == null) {
/*  391 */             return;
/*      */           }
/*  393 */           this.readPrimordialConfiguration = true;
/*      */           try
/*      */           {
/*  396 */             AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */             {
/*      */               public Void run() throws Exception {
/*  399 */                 LogManager.this.readConfiguration();
/*      */                 
/*      */ 
/*  402 */                 PlatformLogger.redirectPlatformLoggers();
/*  403 */                 return null;
/*      */               }
/*      */             });
/*      */           } catch (Exception localException) {
/*  407 */             if (!$assertionsDisabled) { throw new AssertionError("Exception raised while reading logging configuration: " + localException);
/*      */             }
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */     throws SecurityException
/*      */   {
/*  437 */     PropertyChangeListener localPropertyChangeListener = (PropertyChangeListener)Objects.requireNonNull(paramPropertyChangeListener);
/*  438 */     checkPermission();
/*  439 */     synchronized (this.listenerMap)
/*      */     {
/*  441 */       Integer localInteger = (Integer)this.listenerMap.get(localPropertyChangeListener);
/*  442 */       localInteger = Integer.valueOf(localInteger == null ? 1 : localInteger.intValue() + 1);
/*  443 */       this.listenerMap.put(localPropertyChangeListener, localInteger);
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
/*      */   @Deprecated
/*      */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */     throws SecurityException
/*      */   {
/*  473 */     checkPermission();
/*  474 */     if (paramPropertyChangeListener != null) {
/*  475 */       PropertyChangeListener localPropertyChangeListener = paramPropertyChangeListener;
/*  476 */       synchronized (this.listenerMap) {
/*  477 */         Integer localInteger = (Integer)this.listenerMap.get(localPropertyChangeListener);
/*  478 */         if (localInteger != null)
/*      */         {
/*      */ 
/*  481 */           int i = localInteger.intValue();
/*  482 */           if (i == 1) {
/*  483 */             this.listenerMap.remove(localPropertyChangeListener);
/*      */           } else {
/*  485 */             assert (i > 1);
/*  486 */             this.listenerMap.put(localPropertyChangeListener, Integer.valueOf(i - 1));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*  494 */   private WeakHashMap<Object, LoggerContext> contextsMap = null;
/*      */   
/*      */ 
/*      */   private LoggerContext getUserContext()
/*      */   {
/*  499 */     LoggerContext localLoggerContext = null;
/*      */     
/*  501 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  502 */     JavaAWTAccess localJavaAWTAccess = SharedSecrets.getJavaAWTAccess();
/*  503 */     if ((localSecurityManager != null) && (localJavaAWTAccess != null))
/*      */     {
/*  505 */       synchronized (localJavaAWTAccess)
/*      */       {
/*      */ 
/*  508 */         Object localObject1 = localJavaAWTAccess.getAppletContext();
/*  509 */         if (localObject1 != null) {
/*  510 */           if (this.contextsMap == null) {
/*  511 */             this.contextsMap = new WeakHashMap();
/*      */           }
/*  513 */           localLoggerContext = (LoggerContext)this.contextsMap.get(localObject1);
/*  514 */           if (localLoggerContext == null)
/*      */           {
/*  516 */             localLoggerContext = new LoggerContext(null);
/*  517 */             this.contextsMap.put(localObject1, localLoggerContext);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  523 */     return localLoggerContext != null ? localLoggerContext : this.userContext;
/*      */   }
/*      */   
/*      */   final LoggerContext getSystemContext()
/*      */   {
/*  528 */     return this.systemContext;
/*      */   }
/*      */   
/*      */   private List<LoggerContext> contexts() {
/*  532 */     ArrayList localArrayList = new ArrayList();
/*  533 */     localArrayList.add(getSystemContext());
/*  534 */     localArrayList.add(getUserContext());
/*  535 */     return localArrayList;
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
/*      */   Logger demandLogger(String paramString1, String paramString2, Class<?> paramClass)
/*      */   {
/*  551 */     Logger localLogger1 = getLogger(paramString1);
/*  552 */     if (localLogger1 == null)
/*      */     {
/*  554 */       Logger localLogger2 = new Logger(paramString1, paramString2, paramClass, this, false);
/*      */       do {
/*  556 */         if (addLogger(localLogger2))
/*      */         {
/*      */ 
/*  559 */           return localLogger2;
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
/*  573 */         localLogger1 = getLogger(paramString1);
/*  574 */       } while (localLogger1 == null);
/*      */     }
/*  576 */     return localLogger1;
/*      */   }
/*      */   
/*      */   Logger demandSystemLogger(String paramString1, String paramString2)
/*      */   {
/*  581 */     final Logger localLogger1 = getSystemContext().demandLogger(paramString1, paramString2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Logger localLogger2;
/*      */     
/*      */ 
/*      */ 
/*      */     do
/*      */     {
/*  592 */       if (addLogger(localLogger1))
/*      */       {
/*  594 */         localLogger2 = localLogger1;
/*      */       } else {
/*  596 */         localLogger2 = getLogger(paramString1);
/*      */       }
/*  598 */     } while (localLogger2 == null);
/*      */     
/*      */ 
/*  601 */     if ((localLogger2 != localLogger1) && (localLogger1.accessCheckedHandlers().length == 0))
/*      */     {
/*  603 */       final Logger localLogger3 = localLogger2;
/*  604 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*  607 */           for (Handler localHandler : localLogger3.accessCheckedHandlers()) {
/*  608 */             localLogger1.addHandler(localHandler);
/*      */           }
/*  610 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*  614 */     return localLogger1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   class LoggerContext
/*      */   {
/*  626 */     private final Hashtable<String, LogManager.LoggerWeakRef> namedLoggers = new Hashtable();
/*      */     private final LogManager.LogNode root;
/*      */     
/*      */     private LoggerContext() {
/*  630 */       this.root = new LogManager.LogNode(null, this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final boolean requiresDefaultLoggers()
/*      */     {
/*  637 */       boolean bool = getOwner() == LogManager.manager;
/*  638 */       if (bool) {
/*  639 */         getOwner().ensureLogManagerInitialized();
/*      */       }
/*  641 */       return bool;
/*      */     }
/*      */     
/*      */     final LogManager getOwner()
/*      */     {
/*  646 */       return LogManager.this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final Logger getRootLogger()
/*      */     {
/*  653 */       return getOwner().rootLogger;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final Logger getGlobalLogger()
/*      */     {
/*  661 */       Logger localLogger = Logger.global;
/*  662 */       return localLogger;
/*      */     }
/*      */     
/*      */ 
/*      */     Logger demandLogger(String paramString1, String paramString2)
/*      */     {
/*  668 */       LogManager localLogManager = getOwner();
/*  669 */       return localLogManager.demandLogger(paramString1, paramString2, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void ensureInitialized()
/*      */     {
/*  681 */       if (requiresDefaultLoggers())
/*      */       {
/*  683 */         ensureDefaultLogger(getRootLogger());
/*  684 */         ensureDefaultLogger(getGlobalLogger());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     synchronized Logger findLogger(String paramString)
/*      */     {
/*  692 */       ensureInitialized();
/*  693 */       LogManager.LoggerWeakRef localLoggerWeakRef = (LogManager.LoggerWeakRef)this.namedLoggers.get(paramString);
/*  694 */       if (localLoggerWeakRef == null) {
/*  695 */         return null;
/*      */       }
/*  697 */       Logger localLogger = (Logger)localLoggerWeakRef.get();
/*  698 */       if (localLogger == null)
/*      */       {
/*      */ 
/*  701 */         localLoggerWeakRef.dispose();
/*      */       }
/*  703 */       return localLogger;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void ensureAllDefaultLoggers(Logger paramLogger)
/*      */     {
/*  713 */       if (requiresDefaultLoggers()) {
/*  714 */         String str = paramLogger.getName();
/*  715 */         if (!str.isEmpty()) {
/*  716 */           ensureDefaultLogger(getRootLogger());
/*  717 */           if (!"global".equals(str)) {
/*  718 */             ensureDefaultLogger(getGlobalLogger());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void ensureDefaultLogger(Logger paramLogger)
/*      */     {
/*  731 */       if ((!requiresDefaultLoggers()) || (paramLogger == null) || ((paramLogger != Logger.global) && 
/*  732 */         (paramLogger != LogManager.this.rootLogger)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  739 */         assert (paramLogger == null);
/*      */         
/*  741 */         return;
/*      */       }
/*      */       
/*      */ 
/*  745 */       if (!this.namedLoggers.containsKey(paramLogger.getName()))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  752 */         addLocalLogger(paramLogger, false);
/*      */       }
/*      */     }
/*      */     
/*      */     boolean addLocalLogger(Logger paramLogger)
/*      */     {
/*  758 */       return addLocalLogger(paramLogger, requiresDefaultLoggers());
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
/*      */     synchronized boolean addLocalLogger(Logger paramLogger, boolean paramBoolean)
/*      */     {
/*  773 */       if (paramBoolean) {
/*  774 */         ensureAllDefaultLoggers(paramLogger);
/*      */       }
/*      */       
/*  777 */       String str = paramLogger.getName();
/*  778 */       if (str == null) {
/*  779 */         throw new NullPointerException();
/*      */       }
/*  781 */       LogManager.LoggerWeakRef localLoggerWeakRef1 = (LogManager.LoggerWeakRef)this.namedLoggers.get(str);
/*  782 */       if (localLoggerWeakRef1 != null) {
/*  783 */         if (localLoggerWeakRef1.get() == null)
/*      */         {
/*      */ 
/*      */ 
/*  787 */           localLoggerWeakRef1.dispose();
/*      */         }
/*      */         else {
/*  790 */           return false;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  796 */       LogManager localLogManager = getOwner();
/*  797 */       paramLogger.setLogManager(localLogManager); LogManager 
/*  798 */         tmp80_78 = localLogManager;tmp80_78.getClass();localLoggerWeakRef1 = new LogManager.LoggerWeakRef(tmp80_78, paramLogger);
/*  799 */       this.namedLoggers.put(str, localLoggerWeakRef1);
/*      */       
/*      */ 
/*      */ 
/*  803 */       Level localLevel = localLogManager.getLevelProperty(str + ".level", null);
/*  804 */       if ((localLevel != null) && (!paramLogger.isLevelInitialized())) {
/*  805 */         LogManager.doSetLevel(paramLogger, localLevel);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  811 */       processParentHandlers(paramLogger, str);
/*      */       
/*      */ 
/*  814 */       LogManager.LogNode localLogNode1 = getNode(str);
/*  815 */       localLogNode1.loggerRef = localLoggerWeakRef1;
/*  816 */       Logger localLogger = null;
/*  817 */       LogManager.LogNode localLogNode2 = localLogNode1.parent;
/*  818 */       while (localLogNode2 != null) {
/*  819 */         LogManager.LoggerWeakRef localLoggerWeakRef2 = localLogNode2.loggerRef;
/*  820 */         if (localLoggerWeakRef2 != null) {
/*  821 */           localLogger = (Logger)localLoggerWeakRef2.get();
/*  822 */           if (localLogger != null) {
/*      */             break;
/*      */           }
/*      */         }
/*  826 */         localLogNode2 = localLogNode2.parent;
/*      */       }
/*      */       
/*  829 */       if (localLogger != null) {
/*  830 */         LogManager.doSetParent(paramLogger, localLogger);
/*      */       }
/*      */       
/*  833 */       localLogNode1.walkAndSetParent(paramLogger);
/*      */       
/*  835 */       localLoggerWeakRef1.setNode(localLogNode1);
/*  836 */       return true;
/*      */     }
/*      */     
/*      */     synchronized void removeLoggerRef(String paramString, LogManager.LoggerWeakRef paramLoggerWeakRef) {
/*  840 */       this.namedLoggers.remove(paramString, paramLoggerWeakRef);
/*      */     }
/*      */     
/*      */ 
/*      */     synchronized Enumeration<String> getLoggerNames()
/*      */     {
/*  846 */       ensureInitialized();
/*  847 */       return this.namedLoggers.keys();
/*      */     }
/*      */     
/*      */ 
/*      */     private void processParentHandlers(final Logger paramLogger, final String paramString)
/*      */     {
/*  853 */       final LogManager localLogManager = getOwner();
/*  854 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*  857 */           if (paramLogger != localLogManager.rootLogger) {
/*  858 */             boolean bool = localLogManager.getBooleanProperty(paramString + ".useParentHandlers", true);
/*  859 */             if (!bool) {
/*  860 */               paramLogger.setUseParentHandlers(false);
/*      */             }
/*      */           }
/*  863 */           return null;
/*      */         }
/*      */         
/*  866 */       });
/*  867 */       int i = 1;
/*      */       for (;;) {
/*  869 */         int j = paramString.indexOf(".", i);
/*  870 */         if (j < 0) {
/*      */           break;
/*      */         }
/*  873 */         String str = paramString.substring(0, j);
/*  874 */         if ((localLogManager.getProperty(str + ".level") != null) || 
/*  875 */           (localLogManager.getProperty(str + ".handlers") != null))
/*      */         {
/*      */ 
/*  878 */           demandLogger(str, null);
/*      */         }
/*  880 */         i = j + 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     LogManager.LogNode getNode(String paramString)
/*      */     {
/*  887 */       if ((paramString == null) || (paramString.equals(""))) {
/*  888 */         return this.root;
/*      */       }
/*  890 */       Object localObject = this.root;
/*  891 */       while (paramString.length() > 0) {
/*  892 */         int i = paramString.indexOf(".");
/*      */         String str;
/*  894 */         if (i > 0) {
/*  895 */           str = paramString.substring(0, i);
/*  896 */           paramString = paramString.substring(i + 1);
/*      */         } else {
/*  898 */           str = paramString;
/*  899 */           paramString = "";
/*      */         }
/*  901 */         if (((LogManager.LogNode)localObject).children == null) {
/*  902 */           ((LogManager.LogNode)localObject).children = new HashMap();
/*      */         }
/*  904 */         LogManager.LogNode localLogNode = (LogManager.LogNode)((LogManager.LogNode)localObject).children.get(str);
/*  905 */         if (localLogNode == null) {
/*  906 */           localLogNode = new LogManager.LogNode((LogManager.LogNode)localObject, this);
/*  907 */           ((LogManager.LogNode)localObject).children.put(str, localLogNode);
/*      */         }
/*  909 */         localObject = localLogNode;
/*      */       }
/*  911 */       return (LogManager.LogNode)localObject;
/*      */     }
/*      */   }
/*      */   
/*  915 */   final class SystemLoggerContext extends LogManager.LoggerContext { SystemLoggerContext() { super(null); }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Logger demandLogger(String paramString1, String paramString2)
/*      */     {
/*  922 */       Object localObject = findLogger(paramString1);
/*  923 */       if (localObject == null)
/*      */       {
/*  925 */         Logger localLogger = new Logger(paramString1, paramString2, null, getOwner(), true);
/*      */         do {
/*  927 */           if (addLocalLogger(localLogger))
/*      */           {
/*      */ 
/*  930 */             localObject = localLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  943 */             localObject = findLogger(paramString1);
/*      */           }
/*  945 */         } while (localObject == null);
/*      */       }
/*  947 */       return (Logger)localObject;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void loadLoggerHandlers(final Logger paramLogger, String paramString1, final String paramString2)
/*      */   {
/*  958 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/*  961 */         String[] arrayOfString = LogManager.this.parseClassNames(paramString2);
/*  962 */         for (int i = 0; i < arrayOfString.length; i++) {
/*  963 */           String str1 = arrayOfString[i];
/*      */           try {
/*  965 */             Class localClass = ClassLoader.getSystemClassLoader().loadClass(str1);
/*  966 */             Handler localHandler = (Handler)localClass.newInstance();
/*      */             
/*      */ 
/*  969 */             String str2 = LogManager.this.getProperty(str1 + ".level");
/*  970 */             if (str2 != null) {
/*  971 */               Level localLevel = Level.findLevel(str2);
/*  972 */               if (localLevel != null) {
/*  973 */                 localHandler.setLevel(localLevel);
/*      */               }
/*      */               else {
/*  976 */                 System.err.println("Can't set level for " + str1);
/*      */               }
/*      */             }
/*      */             
/*  980 */             paramLogger.addHandler(localHandler);
/*      */           } catch (Exception localException) {
/*  982 */             System.err.println("Can't load log handler \"" + str1 + "\"");
/*  983 */             System.err.println("" + localException);
/*  984 */             localException.printStackTrace();
/*      */           }
/*      */         }
/*  987 */         return null;
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  995 */   private final ReferenceQueue<Logger> loggerRefQueue = new ReferenceQueue();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final class LoggerWeakRef
/*      */     extends WeakReference<Logger>
/*      */   {
/*      */     private String name;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private LogManager.LogNode node;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private WeakReference<Logger> parentRef;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1022 */     private boolean disposed = false;
/*      */     
/*      */     LoggerWeakRef(Logger paramLogger) {
/* 1025 */       super(LogManager.this.loggerRefQueue);
/*      */       
/* 1027 */       this.name = paramLogger.getName();
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
/*      */     void dispose()
/*      */     {
/* 1041 */       synchronized (this)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1048 */         if (this.disposed) return;
/* 1049 */         this.disposed = true;
/*      */       }
/*      */       
/* 1052 */       ??? = this.node;
/* 1053 */       if (??? != null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1058 */         synchronized (((LogManager.LogNode)???).context)
/*      */         {
/*      */ 
/* 1061 */           ((LogManager.LogNode)???).context.removeLoggerRef(this.name, this);
/* 1062 */           this.name = null;
/*      */           
/*      */ 
/*      */ 
/* 1066 */           if (((LogManager.LogNode)???).loggerRef == this) {
/* 1067 */             ((LogManager.LogNode)???).loggerRef = null;
/*      */           }
/* 1069 */           this.node = null;
/*      */         }
/*      */       }
/*      */       
/* 1073 */       if (this.parentRef != null)
/*      */       {
/* 1075 */         ??? = (Logger)this.parentRef.get();
/* 1076 */         if (??? != null)
/*      */         {
/*      */ 
/* 1079 */           ((Logger)???).removeChildLogger(this);
/*      */         }
/* 1081 */         this.parentRef = null;
/*      */       }
/*      */     }
/*      */     
/*      */     void setNode(LogManager.LogNode paramLogNode)
/*      */     {
/* 1087 */       this.node = paramLogNode;
/*      */     }
/*      */     
/*      */     void setParentRef(WeakReference<Logger> paramWeakReference)
/*      */     {
/* 1092 */       this.parentRef = paramWeakReference;
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
/*      */   final void drainLoggerRefQueueBounded()
/*      */   {
/* 1124 */     for (int i = 0; i < 400; i++) {
/* 1125 */       if (this.loggerRefQueue == null) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/* 1130 */       LoggerWeakRef localLoggerWeakRef = (LoggerWeakRef)this.loggerRefQueue.poll();
/* 1131 */       if (localLoggerWeakRef == null) {
/*      */         break;
/*      */       }
/*      */       
/* 1135 */       localLoggerWeakRef.dispose();
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
/*      */   public boolean addLogger(Logger paramLogger)
/*      */   {
/* 1156 */     String str = paramLogger.getName();
/* 1157 */     if (str == null) {
/* 1158 */       throw new NullPointerException();
/*      */     }
/* 1160 */     drainLoggerRefQueueBounded();
/* 1161 */     LoggerContext localLoggerContext = getUserContext();
/* 1162 */     if (localLoggerContext.addLocalLogger(paramLogger))
/*      */     {
/*      */ 
/* 1165 */       loadLoggerHandlers(paramLogger, str, str + ".handlers");
/* 1166 */       return true;
/*      */     }
/* 1168 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void doSetLevel(Logger paramLogger, final Level paramLevel)
/*      */   {
/* 1175 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1176 */     if (localSecurityManager == null)
/*      */     {
/* 1178 */       paramLogger.setLevel(paramLevel);
/* 1179 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1183 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/* 1186 */         this.val$logger.setLevel(paramLevel);
/* 1187 */         return null;
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private static void doSetParent(Logger paramLogger1, final Logger paramLogger2)
/*      */   {
/* 1194 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1195 */     if (localSecurityManager == null)
/*      */     {
/* 1197 */       paramLogger1.setParent(paramLogger2);
/* 1198 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1202 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/* 1205 */         this.val$logger.setParent(paramLogger2);
/* 1206 */         return null;
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
/*      */   public Logger getLogger(String paramString)
/*      */   {
/* 1226 */     return getUserContext().findLogger(paramString);
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
/*      */   public Enumeration<String> getLoggerNames()
/*      */   {
/* 1246 */     return getUserContext().getLoggerNames();
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
/*      */   public void readConfiguration()
/*      */     throws IOException, SecurityException
/*      */   {
/* 1266 */     checkPermission();
/*      */     
/*      */ 
/* 1269 */     String str1 = System.getProperty("java.util.logging.config.class");
/* 1270 */     if (str1 != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/* 1276 */         Class localClass = ClassLoader.getSystemClassLoader().loadClass(str1);
/* 1277 */         localClass.newInstance();
/* 1278 */         return;
/*      */       } catch (ClassNotFoundException localClassNotFoundException) {
/* 1280 */         localObject1 = Thread.currentThread().getContextClassLoader().loadClass(str1);
/* 1281 */         ((Class)localObject1).newInstance();
/* 1282 */         return;
/*      */       }
/*      */       catch (Exception localException) {
/* 1285 */         System.err.println("Logging configuration class \"" + str1 + "\" failed");
/* 1286 */         System.err.println("" + localException);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1291 */     String str2 = System.getProperty("java.util.logging.config.file");
/* 1292 */     if (str2 == null) {
/* 1293 */       str2 = System.getProperty("java.home");
/* 1294 */       if (str2 == null) {
/* 1295 */         throw new Error("Can't find java.home ??");
/*      */       }
/* 1297 */       localObject1 = new File(str2, "lib");
/* 1298 */       localObject1 = new File((File)localObject1, "logging.properties");
/* 1299 */       str2 = ((File)localObject1).getCanonicalPath();
/*      */     }
/* 1301 */     Object localObject1 = new FileInputStream(str2);Object localObject2 = null;
/* 1302 */     try { BufferedInputStream localBufferedInputStream = new BufferedInputStream((InputStream)localObject1);
/* 1303 */       readConfiguration(localBufferedInputStream);
/*      */     }
/*      */     catch (Throwable localThrowable2)
/*      */     {
/* 1301 */       localObject2 = localThrowable2;throw localThrowable2;
/*      */     }
/*      */     finally {
/* 1304 */       if (localObject1 != null) { if (localObject2 != null) try { ((InputStream)localObject1).close(); } catch (Throwable localThrowable3) { ((Throwable)localObject2).addSuppressed(localThrowable3); } else { ((InputStream)localObject1).close();
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
/*      */   public void reset()
/*      */     throws SecurityException
/*      */   {
/* 1319 */     checkPermission();
/* 1320 */     synchronized (this) {
/* 1321 */       this.props = new Properties();
/*      */       
/*      */ 
/* 1324 */       this.initializedGlobalHandlers = true;
/*      */     }
/* 1326 */     for (??? = contexts().iterator(); ((Iterator)???).hasNext();) { LoggerContext localLoggerContext = (LoggerContext)((Iterator)???).next();
/* 1327 */       Enumeration localEnumeration = localLoggerContext.getLoggerNames();
/* 1328 */       while (localEnumeration.hasMoreElements()) {
/* 1329 */         String str = (String)localEnumeration.nextElement();
/* 1330 */         Logger localLogger = localLoggerContext.findLogger(str);
/* 1331 */         if (localLogger != null) {
/* 1332 */           resetLogger(localLogger);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void resetLogger(Logger paramLogger)
/*      */   {
/* 1341 */     Handler[] arrayOfHandler = paramLogger.getHandlers();
/* 1342 */     for (int i = 0; i < arrayOfHandler.length; i++) {
/* 1343 */       Handler localHandler = arrayOfHandler[i];
/* 1344 */       paramLogger.removeHandler(localHandler);
/*      */       try {
/* 1346 */         localHandler.close();
/*      */       }
/*      */       catch (Exception localException) {}
/*      */     }
/*      */     
/* 1351 */     String str = paramLogger.getName();
/* 1352 */     if ((str != null) && (str.equals("")))
/*      */     {
/* 1354 */       paramLogger.setLevel(defaultLevel);
/*      */     } else {
/* 1356 */       paramLogger.setLevel(null);
/*      */     }
/*      */   }
/*      */   
/*      */   private String[] parseClassNames(String paramString)
/*      */   {
/* 1362 */     String str1 = getProperty(paramString);
/* 1363 */     if (str1 == null) {
/* 1364 */       return new String[0];
/*      */     }
/* 1366 */     str1 = str1.trim();
/* 1367 */     int i = 0;
/* 1368 */     ArrayList localArrayList = new ArrayList();
/* 1369 */     while (i < str1.length()) {
/* 1370 */       int j = i;
/* 1371 */       while ((j < str1.length()) && 
/* 1372 */         (!Character.isWhitespace(str1.charAt(j))))
/*      */       {
/*      */ 
/* 1375 */         if (str1.charAt(j) == ',') {
/*      */           break;
/*      */         }
/* 1378 */         j++;
/*      */       }
/* 1380 */       String str2 = str1.substring(i, j);
/* 1381 */       i = j + 1;
/* 1382 */       str2 = str2.trim();
/* 1383 */       if (str2.length() != 0)
/*      */       {
/*      */ 
/* 1386 */         localArrayList.add(str2); }
/*      */     }
/* 1388 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
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
/*      */   public void readConfiguration(InputStream paramInputStream)
/*      */     throws IOException, SecurityException
/*      */   {
/* 1405 */     checkPermission();
/* 1406 */     reset();
/*      */     
/*      */ 
/* 1409 */     this.props.load(paramInputStream);
/*      */     
/* 1411 */     String[] arrayOfString = parseClassNames("config");
/*      */     
/* 1413 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 1414 */       String str = arrayOfString[i];
/*      */       try {
/* 1416 */         Class localClass = ClassLoader.getSystemClassLoader().loadClass(str);
/* 1417 */         localClass.newInstance();
/*      */       } catch (Exception localException) {
/* 1419 */         System.err.println("Can't load config class \"" + str + "\"");
/* 1420 */         System.err.println("" + localException);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1426 */     setLevelsOnExistingLoggers();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1431 */     HashMap localHashMap = null;
/* 1432 */     synchronized (this.listenerMap) {
/* 1433 */       if (!this.listenerMap.isEmpty())
/* 1434 */         localHashMap = new HashMap(this.listenerMap);
/*      */     }
/* 1436 */     if (localHashMap != null) {
/* 1437 */       assert (Beans.isBeansPresent());
/* 1438 */       ??? = Beans.newPropertyChangeEvent(LogManager.class, null, null, null);
/* 1439 */       for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 1440 */         Object localObject2 = localEntry.getKey();
/* 1441 */         int j = ((Integer)localEntry.getValue()).intValue();
/* 1442 */         for (int k = 0; k < j; k++) {
/* 1443 */           Beans.invokePropertyChange(localObject2, ???);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1451 */     synchronized (this) {
/* 1452 */       this.initializedGlobalHandlers = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getProperty(String paramString)
/*      */   {
/* 1463 */     return this.props.getProperty(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   String getStringProperty(String paramString1, String paramString2)
/*      */   {
/* 1470 */     String str = getProperty(paramString1);
/* 1471 */     if (str == null) {
/* 1472 */       return paramString2;
/*      */     }
/* 1474 */     return str.trim();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   int getIntProperty(String paramString, int paramInt)
/*      */   {
/* 1481 */     String str = getProperty(paramString);
/* 1482 */     if (str == null) {
/* 1483 */       return paramInt;
/*      */     }
/*      */     try {
/* 1486 */       return Integer.parseInt(str.trim());
/*      */     } catch (Exception localException) {}
/* 1488 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean getBooleanProperty(String paramString, boolean paramBoolean)
/*      */   {
/* 1496 */     String str = getProperty(paramString);
/* 1497 */     if (str == null) {
/* 1498 */       return paramBoolean;
/*      */     }
/* 1500 */     str = str.toLowerCase();
/* 1501 */     if ((str.equals("true")) || (str.equals("1")))
/* 1502 */       return true;
/* 1503 */     if ((str.equals("false")) || (str.equals("0"))) {
/* 1504 */       return false;
/*      */     }
/* 1506 */     return paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   Level getLevelProperty(String paramString, Level paramLevel)
/*      */   {
/* 1513 */     String str = getProperty(paramString);
/* 1514 */     if (str == null) {
/* 1515 */       return paramLevel;
/*      */     }
/* 1517 */     Level localLevel = Level.findLevel(str.trim());
/* 1518 */     return localLevel != null ? localLevel : paramLevel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Filter getFilterProperty(String paramString, Filter paramFilter)
/*      */   {
/* 1526 */     String str = getProperty(paramString);
/*      */     try {
/* 1528 */       if (str != null) {
/* 1529 */         Class localClass = ClassLoader.getSystemClassLoader().loadClass(str);
/* 1530 */         return (Filter)localClass.newInstance();
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1538 */     return paramFilter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Formatter getFormatterProperty(String paramString, Formatter paramFormatter)
/*      */   {
/* 1547 */     String str = getProperty(paramString);
/*      */     try {
/* 1549 */       if (str != null) {
/* 1550 */         Class localClass = ClassLoader.getSystemClassLoader().loadClass(str);
/* 1551 */         return (Formatter)localClass.newInstance();
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1559 */     return paramFormatter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private synchronized void initializeGlobalHandlers()
/*      */   {
/* 1566 */     if (this.initializedGlobalHandlers) {
/* 1567 */       return;
/*      */     }
/*      */     
/* 1570 */     this.initializedGlobalHandlers = true;
/*      */     
/* 1572 */     if (this.deathImminent)
/*      */     {
/*      */ 
/*      */ 
/* 1576 */       return;
/*      */     }
/* 1578 */     loadLoggerHandlers(this.rootLogger, null, "handlers");
/*      */   }
/*      */   
/* 1581 */   private final Permission controlPermission = new LoggingPermission("control", null);
/*      */   
/*      */   void checkPermission() {
/* 1584 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1585 */     if (localSecurityManager != null) {
/* 1586 */       localSecurityManager.checkPermission(this.controlPermission);
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
/*      */   public void checkAccess()
/*      */     throws SecurityException
/*      */   {
/* 1600 */     checkPermission();
/*      */   }
/*      */   
/*      */   private static class LogNode
/*      */   {
/*      */     HashMap<String, LogNode> children;
/*      */     LogManager.LoggerWeakRef loggerRef;
/*      */     LogNode parent;
/*      */     final LogManager.LoggerContext context;
/*      */     
/*      */     LogNode(LogNode paramLogNode, LogManager.LoggerContext paramLoggerContext) {
/* 1611 */       this.parent = paramLogNode;
/* 1612 */       this.context = paramLoggerContext;
/*      */     }
/*      */     
/*      */ 
/*      */     void walkAndSetParent(Logger paramLogger)
/*      */     {
/* 1618 */       if (this.children == null) {
/* 1619 */         return;
/*      */       }
/* 1621 */       Iterator localIterator = this.children.values().iterator();
/* 1622 */       while (localIterator.hasNext()) {
/* 1623 */         LogNode localLogNode = (LogNode)localIterator.next();
/* 1624 */         LogManager.LoggerWeakRef localLoggerWeakRef = localLogNode.loggerRef;
/* 1625 */         Logger localLogger = localLoggerWeakRef == null ? null : (Logger)localLoggerWeakRef.get();
/* 1626 */         if (localLogger == null) {
/* 1627 */           localLogNode.walkAndSetParent(paramLogger);
/*      */         } else {
/* 1629 */           LogManager.doSetParent(localLogger, paramLogger);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final class RootLogger
/*      */     extends Logger
/*      */   {
/*      */     private RootLogger()
/*      */     {
/* 1643 */       super(null, null, LogManager.this, true);
/*      */     }
/*      */     
/*      */ 
/*      */     public void log(LogRecord paramLogRecord)
/*      */     {
/* 1649 */       LogManager.this.initializeGlobalHandlers();
/* 1650 */       super.log(paramLogRecord);
/*      */     }
/*      */     
/*      */     public void addHandler(Handler paramHandler)
/*      */     {
/* 1655 */       LogManager.this.initializeGlobalHandlers();
/* 1656 */       super.addHandler(paramHandler);
/*      */     }
/*      */     
/*      */     public void removeHandler(Handler paramHandler)
/*      */     {
/* 1661 */       LogManager.this.initializeGlobalHandlers();
/* 1662 */       super.removeHandler(paramHandler);
/*      */     }
/*      */     
/*      */     Handler[] accessCheckedHandlers()
/*      */     {
/* 1667 */       LogManager.this.initializeGlobalHandlers();
/* 1668 */       return super.accessCheckedHandlers();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private synchronized void setLevelsOnExistingLoggers()
/*      */   {
/* 1676 */     Enumeration localEnumeration = this.props.propertyNames();
/* 1677 */     String str2; Level localLevel; while (localEnumeration.hasMoreElements()) {
/* 1678 */       String str1 = (String)localEnumeration.nextElement();
/* 1679 */       if (str1.endsWith(".level"))
/*      */       {
/*      */ 
/*      */ 
/* 1683 */         int i = str1.length() - 6;
/* 1684 */         str2 = str1.substring(0, i);
/* 1685 */         localLevel = getLevelProperty(str1, null);
/* 1686 */         if (localLevel == null) {
/* 1687 */           System.err.println("Bad level value for property: " + str1);
/*      */         }
/*      */         else
/* 1690 */           for (LoggerContext localLoggerContext : contexts()) {
/* 1691 */             Logger localLogger = localLoggerContext.findLogger(str2);
/* 1692 */             if (localLogger != null)
/*      */             {
/*      */ 
/* 1695 */               localLogger.setLevel(localLevel); }
/*      */           }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1701 */   private static LoggingMXBean loggingMXBean = null;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final Level defaultLevel;
/*      */   
/*      */ 
/*      */ 
/*      */   private volatile Logger rootLogger;
/*      */   
/*      */ 
/*      */ 
/*      */   private volatile boolean readPrimordialConfiguration;
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean deathImminent;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final int MAX_ITERATIONS = 400;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final String LOGGING_MXBEAN_NAME = "java.util.logging:type=Logging";
/*      */   
/*      */ 
/*      */ 
/*      */   public static synchronized LoggingMXBean getLoggingMXBean()
/*      */   {
/* 1731 */     if (loggingMXBean == null) {
/* 1732 */       loggingMXBean = new Logging();
/*      */     }
/* 1734 */     return loggingMXBean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class Beans
/*      */   {
/* 1745 */     private static final Class<?> propertyChangeListenerClass = getClass("java.beans.PropertyChangeListener");
/*      */     
/*      */ 
/* 1748 */     private static final Class<?> propertyChangeEventClass = getClass("java.beans.PropertyChangeEvent");
/*      */     
/*      */ 
/* 1751 */     private static final Method propertyChangeMethod = getMethod(propertyChangeListenerClass, "propertyChange", new Class[] { propertyChangeEventClass });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1756 */     private static final Constructor<?> propertyEventCtor = getConstructor(propertyChangeEventClass, new Class[] { Object.class, String.class, Object.class, Object.class });
/*      */     
/*      */ 
/*      */ 
/*      */     private static Class<?> getClass(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 1764 */         return Class.forName(paramString, true, Beans.class.getClassLoader());
/*      */       } catch (ClassNotFoundException localClassNotFoundException) {}
/* 1766 */       return null;
/*      */     }
/*      */     
/*      */     private static Constructor<?> getConstructor(Class<?> paramClass, Class<?>... paramVarArgs) {
/*      */       try {
/* 1771 */         return paramClass == null ? null : paramClass.getDeclaredConstructor(paramVarArgs);
/*      */       } catch (NoSuchMethodException localNoSuchMethodException) {
/* 1773 */         throw new AssertionError(localNoSuchMethodException);
/*      */       }
/*      */     }
/*      */     
/*      */     private static Method getMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
/*      */       try {
/* 1779 */         return paramClass == null ? null : paramClass.getMethod(paramString, paramVarArgs);
/*      */       } catch (NoSuchMethodException localNoSuchMethodException) {
/* 1781 */         throw new AssertionError(localNoSuchMethodException);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static boolean isBeansPresent()
/*      */     {
/* 1789 */       return (propertyChangeListenerClass != null) && (propertyChangeEventClass != null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static Object newPropertyChangeEvent(Object paramObject1, String paramString, Object paramObject2, Object paramObject3)
/*      */     {
/*      */       try
/*      */       {
/* 1801 */         return propertyEventCtor.newInstance(new Object[] { paramObject1, paramString, paramObject2, paramObject3 });
/*      */       } catch (InstantiationException|IllegalAccessException localInstantiationException) {
/* 1803 */         throw new AssertionError(localInstantiationException);
/*      */       } catch (InvocationTargetException localInvocationTargetException) {
/* 1805 */         Throwable localThrowable = localInvocationTargetException.getCause();
/* 1806 */         if ((localThrowable instanceof Error))
/* 1807 */           throw ((Error)localThrowable);
/* 1808 */         if ((localThrowable instanceof RuntimeException))
/* 1809 */           throw ((RuntimeException)localThrowable);
/* 1810 */         throw new AssertionError(localInvocationTargetException);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static void invokePropertyChange(Object paramObject1, Object paramObject2)
/*      */     {
/*      */       try
/*      */       {
/* 1820 */         propertyChangeMethod.invoke(paramObject1, new Object[] { paramObject2 });
/*      */       } catch (IllegalAccessException localIllegalAccessException) {
/* 1822 */         throw new AssertionError(localIllegalAccessException);
/*      */       } catch (InvocationTargetException localInvocationTargetException) {
/* 1824 */         Throwable localThrowable = localInvocationTargetException.getCause();
/* 1825 */         if ((localThrowable instanceof Error))
/* 1826 */           throw ((Error)localThrowable);
/* 1827 */         if ((localThrowable instanceof RuntimeException))
/* 1828 */           throw ((RuntimeException)localThrowable);
/* 1829 */         throw new AssertionError(localInvocationTargetException);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/LogManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */