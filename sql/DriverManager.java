/*     */ package java.sql;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public class DriverManager
/*     */ {
/*  85 */   private static final CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList();
/*  86 */   private static volatile int loginTimeout = 0;
/*  87 */   private static volatile PrintWriter logWriter = null;
/*  88 */   private static volatile PrintStream logStream = null;
/*     */   
/*  90 */   private static final Object logSync = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 101 */     loadInitialDrivers();
/* 102 */     println("JDBC DriverManager initialized");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */   static final SQLPermission SET_LOG_PERMISSION = new SQLPermission("setLog");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 118 */   static final SQLPermission DEREGISTER_DRIVER_PERMISSION = new SQLPermission("deregisterDriver");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PrintWriter getLogWriter()
/*     */   {
/* 134 */     return logWriter;
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
/*     */   public static void setLogWriter(PrintWriter paramPrintWriter)
/*     */   {
/* 169 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 170 */     if (localSecurityManager != null) {
/* 171 */       localSecurityManager.checkPermission(SET_LOG_PERMISSION);
/*     */     }
/* 173 */     logStream = null;
/* 174 */     logWriter = paramPrintWriter;
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
/*     */   @CallerSensitive
/*     */   public static Connection getConnection(String paramString, Properties paramProperties)
/*     */     throws SQLException
/*     */   {
/* 208 */     return getConnection(paramString, paramProperties, Reflection.getCallerClass());
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
/*     */   @CallerSensitive
/*     */   public static Connection getConnection(String paramString1, String paramString2, String paramString3)
/*     */     throws SQLException
/*     */   {
/* 238 */     Properties localProperties = new Properties();
/*     */     
/* 240 */     if (paramString2 != null) {
/* 241 */       localProperties.put("user", paramString2);
/*     */     }
/* 243 */     if (paramString3 != null) {
/* 244 */       localProperties.put("password", paramString3);
/*     */     }
/*     */     
/* 247 */     return getConnection(paramString1, localProperties, Reflection.getCallerClass());
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
/*     */   public static Connection getConnection(String paramString)
/*     */     throws SQLException
/*     */   {
/* 269 */     Properties localProperties = new Properties();
/* 270 */     return getConnection(paramString, localProperties, Reflection.getCallerClass());
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
/*     */   @CallerSensitive
/*     */   public static Driver getDriver(String paramString)
/*     */     throws SQLException
/*     */   {
/* 288 */     println("DriverManager.getDriver(\"" + paramString + "\")");
/*     */     
/* 290 */     Class localClass = Reflection.getCallerClass();
/*     */     
/*     */ 
/*     */ 
/* 294 */     for (DriverInfo localDriverInfo : registeredDrivers)
/*     */     {
/*     */ 
/* 297 */       if (isDriverAllowed(localDriverInfo.driver, localClass)) {
/*     */         try {
/* 299 */           if (localDriverInfo.driver.acceptsURL(paramString))
/*     */           {
/* 301 */             println("getDriver returning " + localDriverInfo.driver.getClass().getName());
/* 302 */             return localDriverInfo.driver;
/*     */           }
/*     */         }
/*     */         catch (SQLException localSQLException) {}
/*     */       }
/*     */       
/*     */ 
/* 309 */       println("    skipping: " + localDriverInfo.driver.getClass().getName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 314 */     println("getDriver: no suitable driver");
/* 315 */     throw new SQLException("No suitable driver", "08001");
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
/*     */   public static synchronized void registerDriver(Driver paramDriver)
/*     */     throws SQLException
/*     */   {
/* 334 */     registerDriver(paramDriver, null);
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
/*     */   public static synchronized void registerDriver(Driver paramDriver, DriverAction paramDriverAction)
/*     */     throws SQLException
/*     */   {
/* 357 */     if (paramDriver != null) {
/* 358 */       registeredDrivers.addIfAbsent(new DriverInfo(paramDriver, paramDriverAction));
/*     */     }
/*     */     else {
/* 361 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 364 */     println("registerDriver: " + paramDriver);
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
/*     */   @CallerSensitive
/*     */   public static synchronized void deregisterDriver(Driver paramDriver)
/*     */     throws SQLException
/*     */   {
/* 396 */     if (paramDriver == null) {
/* 397 */       return;
/*     */     }
/*     */     
/* 400 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 401 */     if (localSecurityManager != null) {
/* 402 */       localSecurityManager.checkPermission(DEREGISTER_DRIVER_PERMISSION);
/*     */     }
/*     */     
/* 405 */     println("DriverManager.deregisterDriver: " + paramDriver);
/*     */     
/* 407 */     DriverInfo localDriverInfo1 = new DriverInfo(paramDriver, null);
/* 408 */     if (registeredDrivers.contains(localDriverInfo1)) {
/* 409 */       if (isDriverAllowed(paramDriver, Reflection.getCallerClass())) {
/* 410 */         DriverInfo localDriverInfo2 = (DriverInfo)registeredDrivers.get(registeredDrivers.indexOf(localDriverInfo1));
/*     */         
/*     */ 
/* 413 */         if (localDriverInfo2.action() != null) {
/* 414 */           localDriverInfo2.action().deregister();
/*     */         }
/* 416 */         registeredDrivers.remove(localDriverInfo1);
/*     */       }
/*     */       else
/*     */       {
/* 420 */         throw new SecurityException();
/*     */       }
/*     */     } else {
/* 423 */       println("    couldn't find driver to unload");
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
/*     */   @CallerSensitive
/*     */   public static Enumeration<Driver> getDrivers()
/*     */   {
/* 438 */     Vector localVector = new Vector();
/*     */     
/* 440 */     Class localClass = Reflection.getCallerClass();
/*     */     
/*     */ 
/* 443 */     for (DriverInfo localDriverInfo : registeredDrivers)
/*     */     {
/*     */ 
/* 446 */       if (isDriverAllowed(localDriverInfo.driver, localClass)) {
/* 447 */         localVector.addElement(localDriverInfo.driver);
/*     */       } else {
/* 449 */         println("    skipping: " + localDriverInfo.getClass().getName());
/*     */       }
/*     */     }
/* 452 */     return localVector.elements();
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
/*     */   public static void setLoginTimeout(int paramInt)
/*     */   {
/* 465 */     loginTimeout = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getLoginTimeout()
/*     */   {
/* 476 */     return loginTimeout;
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
/*     */   @Deprecated
/*     */   public static void setLogStream(PrintStream paramPrintStream)
/*     */   {
/* 501 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 502 */     if (localSecurityManager != null) {
/* 503 */       localSecurityManager.checkPermission(SET_LOG_PERMISSION);
/*     */     }
/*     */     
/* 506 */     logStream = paramPrintStream;
/* 507 */     if (paramPrintStream != null) {
/* 508 */       logWriter = new PrintWriter(paramPrintStream);
/*     */     } else {
/* 510 */       logWriter = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static PrintStream getLogStream()
/*     */   {
/* 523 */     return logStream;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void println(String paramString)
/*     */   {
/* 532 */     synchronized (logSync) {
/* 533 */       if (logWriter != null) {
/* 534 */         logWriter.println(paramString);
/*     */         
/*     */ 
/* 537 */         logWriter.flush();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isDriverAllowed(Driver paramDriver, Class<?> paramClass)
/*     */   {
/* 547 */     ClassLoader localClassLoader = paramClass != null ? paramClass.getClassLoader() : null;
/* 548 */     return isDriverAllowed(paramDriver, localClassLoader);
/*     */   }
/*     */   
/*     */   private static boolean isDriverAllowed(Driver paramDriver, ClassLoader paramClassLoader) {
/* 552 */     boolean bool = false;
/* 553 */     if (paramDriver != null) {
/* 554 */       Class localClass = null;
/*     */       try {
/* 556 */         localClass = Class.forName(paramDriver.getClass().getName(), true, paramClassLoader);
/*     */       } catch (Exception localException) {
/* 558 */         bool = false;
/*     */       }
/*     */       
/* 561 */       bool = localClass == paramDriver.getClass();
/*     */     }
/*     */     
/* 564 */     return bool;
/*     */   }
/*     */   
/*     */   private static void loadInitialDrivers() {
/*     */     String str1;
/*     */     try {
/* 570 */       str1 = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public String run() {
/* 572 */           return System.getProperty("jdbc.drivers");
/*     */         }
/*     */       });
/*     */     } catch (Exception localException1) {
/* 576 */       str1 = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 583 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 586 */         ServiceLoader localServiceLoader = ServiceLoader.load(Driver.class);
/* 587 */         Iterator localIterator = localServiceLoader.iterator();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 602 */           while (localIterator.hasNext()) {
/* 603 */             localIterator.next();
/*     */           }
/*     */         }
/*     */         catch (Throwable localThrowable) {}
/*     */         
/* 608 */         return null;
/*     */       }
/*     */       
/* 611 */     });
/* 612 */     println("DriverManager.initialize: jdbc.drivers = " + str1);
/*     */     
/* 614 */     if ((str1 == null) || (str1.equals(""))) {
/* 615 */       return;
/*     */     }
/* 617 */     String[] arrayOfString1 = str1.split(":");
/* 618 */     println("number of Drivers:" + arrayOfString1.length);
/* 619 */     for (String str2 : arrayOfString1) {
/*     */       try {
/* 621 */         println("DriverManager.Initialize: loading " + str2);
/* 622 */         Class.forName(str2, true, 
/* 623 */           ClassLoader.getSystemClassLoader());
/*     */       } catch (Exception localException2) {
/* 625 */         println("DriverManager.Initialize: load failed: " + localException2);
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
/*     */   private static Connection getConnection(String paramString, Properties paramProperties, Class<?> paramClass)
/*     */     throws SQLException
/*     */   {
/* 640 */     ClassLoader localClassLoader = paramClass != null ? paramClass.getClassLoader() : null;
/* 641 */     synchronized (DriverManager.class)
/*     */     {
/* 643 */       if (localClassLoader == null) {
/* 644 */         localClassLoader = Thread.currentThread().getContextClassLoader();
/*     */       }
/*     */     }
/*     */     
/* 648 */     if (paramString == null) {
/* 649 */       throw new SQLException("The url cannot be null", "08001");
/*     */     }
/*     */     
/* 652 */     println("DriverManager.getConnection(\"" + paramString + "\")");
/*     */     
/*     */ 
/*     */ 
/* 656 */     ??? = null;
/*     */     
/* 658 */     for (DriverInfo localDriverInfo : registeredDrivers)
/*     */     {
/*     */ 
/* 661 */       if (isDriverAllowed(localDriverInfo.driver, localClassLoader)) {
/*     */         try {
/* 663 */           println("    trying " + localDriverInfo.driver.getClass().getName());
/* 664 */           Connection localConnection = localDriverInfo.driver.connect(paramString, paramProperties);
/* 665 */           if (localConnection != null)
/*     */           {
/* 667 */             println("getConnection returning " + localDriverInfo.driver.getClass().getName());
/* 668 */             return localConnection;
/*     */           }
/*     */         } catch (SQLException localSQLException) {
/* 671 */           if (??? == null) {
/* 672 */             ??? = localSQLException;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 677 */       println("    skipping: " + localDriverInfo.getClass().getName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 683 */     if (??? != null) {
/* 684 */       println("getConnection failed: " + ???);
/* 685 */       throw ((Throwable)???);
/*     */     }
/*     */     
/* 688 */     println("getConnection: no suitable driver found for " + paramString);
/* 689 */     throw new SQLException("No suitable driver found for " + paramString, "08001");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/DriverManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */