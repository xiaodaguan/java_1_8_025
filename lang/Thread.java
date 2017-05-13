/*      */ package java.lang;
/*      */ 
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.misc.Contended;
/*      */ import sun.misc.VM;
/*      */ import sun.nio.ch.Interruptible;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
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
/*      */ public class Thread
/*      */   implements Runnable
/*      */ {
/*      */   private volatile char[] name;
/*      */   private int priority;
/*      */   private Thread threadQ;
/*      */   private long eetop;
/*      */   private boolean single_step;
/*  157 */   private boolean daemon = false;
/*      */   
/*      */ 
/*  160 */   private boolean stillborn = false;
/*      */   
/*      */   private Runnable target;
/*      */   
/*      */   private ThreadGroup group;
/*      */   
/*      */   private ClassLoader contextClassLoader;
/*      */   
/*      */   private AccessControlContext inheritedAccessControlContext;
/*      */   
/*      */   private static int threadInitNumber;
/*      */   
/*      */ 
/*      */   private static native void registerNatives();
/*      */   
/*      */   private static synchronized int nextThreadNum()
/*      */   {
/*  177 */     return threadInitNumber++;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  182 */   ThreadLocal.ThreadLocalMap threadLocals = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  188 */   ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long stackSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long nativeParkEventPointer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long tid;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long threadSeqNumber;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  214 */   private volatile int threadStatus = 0;
/*      */   volatile Object parkBlocker;
/*      */   private volatile Interruptible blocker;
/*      */   
/*  218 */   private static synchronized long nextThreadID() { return ++threadSeqNumber; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  234 */   private final Object blockerLock = new Object();
/*      */   public static final int MIN_PRIORITY = 1;
/*      */   public static final int NORM_PRIORITY = 5;
/*      */   public static final int MAX_PRIORITY = 10;
/*      */   
/*  239 */   void blockedOn(Interruptible paramInterruptible) { synchronized (this.blockerLock) {
/*  240 */       this.blocker = paramInterruptible;
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
/*      */   public static native Thread currentThread();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native void yield();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native void sleep(long paramLong)
/*      */     throws InterruptedException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void sleep(long paramLong, int paramInt)
/*      */     throws InterruptedException
/*      */   {
/*  327 */     if (paramLong < 0L) {
/*  328 */       throw new IllegalArgumentException("timeout value is negative");
/*      */     }
/*      */     
/*  331 */     if ((paramInt < 0) || (paramInt > 999999)) {
/*  332 */       throw new IllegalArgumentException("nanosecond timeout value out of range");
/*      */     }
/*      */     
/*      */ 
/*  336 */     if ((paramInt >= 500000) || ((paramInt != 0) && (paramLong == 0L))) {
/*  337 */       paramLong += 1L;
/*      */     }
/*      */     
/*  340 */     sleep(paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong)
/*      */   {
/*  349 */     init(paramThreadGroup, paramRunnable, paramString, paramLong, null);
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
/*      */   private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong, AccessControlContext paramAccessControlContext)
/*      */   {
/*  365 */     if (paramString == null) {
/*  366 */       throw new NullPointerException("name cannot be null");
/*      */     }
/*      */     
/*  369 */     this.name = paramString.toCharArray();
/*      */     
/*  371 */     Thread localThread = currentThread();
/*  372 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  373 */     if (paramThreadGroup == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  378 */       if (localSecurityManager != null) {
/*  379 */         paramThreadGroup = localSecurityManager.getThreadGroup();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  384 */       if (paramThreadGroup == null) {
/*  385 */         paramThreadGroup = localThread.getThreadGroup();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  391 */     paramThreadGroup.checkAccess();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  396 */     if ((localSecurityManager != null) && 
/*  397 */       (isCCLOverridden(getClass()))) {
/*  398 */       localSecurityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */     }
/*      */     
/*      */ 
/*  402 */     paramThreadGroup.addUnstarted();
/*      */     
/*  404 */     this.group = paramThreadGroup;
/*  405 */     this.daemon = localThread.isDaemon();
/*  406 */     this.priority = localThread.getPriority();
/*  407 */     if ((localSecurityManager == null) || (isCCLOverridden(localThread.getClass()))) {
/*  408 */       this.contextClassLoader = localThread.getContextClassLoader();
/*      */     } else {
/*  410 */       this.contextClassLoader = localThread.contextClassLoader;
/*      */     }
/*  412 */     this.inheritedAccessControlContext = (paramAccessControlContext != null ? paramAccessControlContext : AccessController.getContext());
/*  413 */     this.target = paramRunnable;
/*  414 */     setPriority(this.priority);
/*  415 */     if (localThread.inheritableThreadLocals != null)
/*      */     {
/*  417 */       this.inheritableThreadLocals = ThreadLocal.createInheritedMap(localThread.inheritableThreadLocals);
/*      */     }
/*  419 */     this.stackSize = paramLong;
/*      */     
/*      */ 
/*  422 */     this.tid = nextThreadID();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object clone()
/*      */     throws CloneNotSupportedException
/*      */   {
/*  434 */     throw new CloneNotSupportedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Thread()
/*      */   {
/*  445 */     init(null, null, "Thread-" + nextThreadNum(), 0L);
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
/*      */   public Thread(Runnable paramRunnable)
/*      */   {
/*  461 */     init(null, paramRunnable, "Thread-" + nextThreadNum(), 0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Thread(Runnable paramRunnable, AccessControlContext paramAccessControlContext)
/*      */   {
/*  469 */     init(null, paramRunnable, "Thread-" + nextThreadNum(), 0L, paramAccessControlContext);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable)
/*      */   {
/*  496 */     init(paramThreadGroup, paramRunnable, "Thread-" + nextThreadNum(), 0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Thread(String paramString)
/*      */   {
/*  508 */     init(null, null, paramString, 0L);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, String paramString)
/*      */   {
/*  532 */     init(paramThreadGroup, null, paramString, 0L);
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
/*      */   public Thread(Runnable paramRunnable, String paramString)
/*      */   {
/*  548 */     init(null, paramRunnable, paramString, 0L);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString)
/*      */   {
/*  596 */     init(paramThreadGroup, paramRunnable, paramString, 0L);
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
/*      */   public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong)
/*      */   {
/*  675 */     init(paramThreadGroup, paramRunnable, paramString, paramLong);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public synchronized void start()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 49	java/lang/Thread:threadStatus	I
/*      */     //   4: ifeq +11 -> 15
/*      */     //   7: new 59	java/lang/IllegalThreadStateException
/*      */     //   10: dup
/*      */     //   11: invokespecial 60	java/lang/IllegalThreadStateException:<init>	()V
/*      */     //   14: athrow
/*      */     //   15: aload_0
/*      */     //   16: getfield 28	java/lang/Thread:group	Ljava/lang/ThreadGroup;
/*      */     //   19: aload_0
/*      */     //   20: invokevirtual 61	java/lang/ThreadGroup:add	(Ljava/lang/Thread;)V
/*      */     //   23: iconst_0
/*      */     //   24: istore_1
/*      */     //   25: aload_0
/*      */     //   26: invokespecial 62	java/lang/Thread:start0	()V
/*      */     //   29: iconst_1
/*      */     //   30: istore_1
/*      */     //   31: iload_1
/*      */     //   32: ifne +11 -> 43
/*      */     //   35: aload_0
/*      */     //   36: getfield 28	java/lang/Thread:group	Ljava/lang/ThreadGroup;
/*      */     //   39: aload_0
/*      */     //   40: invokevirtual 63	java/lang/ThreadGroup:threadStartFailed	(Ljava/lang/Thread;)V
/*      */     //   43: goto +27 -> 70
/*      */     //   46: astore_2
/*      */     //   47: goto +23 -> 70
/*      */     //   50: astore_3
/*      */     //   51: iload_1
/*      */     //   52: ifne +11 -> 63
/*      */     //   55: aload_0
/*      */     //   56: getfield 28	java/lang/Thread:group	Ljava/lang/ThreadGroup;
/*      */     //   59: aload_0
/*      */     //   60: invokevirtual 63	java/lang/ThreadGroup:threadStartFailed	(Ljava/lang/Thread;)V
/*      */     //   63: goto +5 -> 68
/*      */     //   66: astore 4
/*      */     //   68: aload_3
/*      */     //   69: athrow
/*      */     //   70: return
/*      */     // Line number table:
/*      */     //   Java source line #704	-> byte code offset #0
/*      */     //   Java source line #705	-> byte code offset #7
/*      */     //   Java source line #710	-> byte code offset #15
/*      */     //   Java source line #712	-> byte code offset #23
/*      */     //   Java source line #714	-> byte code offset #25
/*      */     //   Java source line #715	-> byte code offset #29
/*      */     //   Java source line #718	-> byte code offset #31
/*      */     //   Java source line #719	-> byte code offset #35
/*      */     //   Java source line #724	-> byte code offset #43
/*      */     //   Java source line #721	-> byte code offset #46
/*      */     //   Java source line #725	-> byte code offset #47
/*      */     //   Java source line #717	-> byte code offset #50
/*      */     //   Java source line #718	-> byte code offset #51
/*      */     //   Java source line #719	-> byte code offset #55
/*      */     //   Java source line #724	-> byte code offset #63
/*      */     //   Java source line #721	-> byte code offset #66
/*      */     //   Java source line #724	-> byte code offset #68
/*      */     //   Java source line #726	-> byte code offset #70
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	71	0	this	Thread
/*      */     //   24	28	1	i	int
/*      */     //   46	1	2	localThrowable1	Throwable
/*      */     //   50	19	3	localObject	Object
/*      */     //   66	1	4	localThrowable2	Throwable
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   31	43	46	java/lang/Throwable
/*      */     //   25	31	50	finally
/*      */     //   51	63	66	java/lang/Throwable
/*      */   }
/*      */   
/*      */   private native void start0();
/*      */   
/*      */   public void run()
/*      */   {
/*  744 */     if (this.target != null) {
/*  745 */       this.target.run();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void exit()
/*      */   {
/*  754 */     if (this.group != null) {
/*  755 */       this.group.threadTerminated(this);
/*  756 */       this.group = null;
/*      */     }
/*      */     
/*  759 */     this.target = null;
/*      */     
/*  761 */     this.threadLocals = null;
/*  762 */     this.inheritableThreadLocals = null;
/*  763 */     this.inheritedAccessControlContext = null;
/*  764 */     this.blocker = null;
/*  765 */     this.uncaughtExceptionHandler = null;
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
/*      */   @Deprecated
/*      */   public final void stop()
/*      */   {
/*  836 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  837 */     if (localSecurityManager != null) {
/*  838 */       checkAccess();
/*  839 */       if (this != currentThread()) {
/*  840 */         localSecurityManager.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  845 */     if (this.threadStatus != 0) {
/*  846 */       resume();
/*      */     }
/*      */     
/*      */ 
/*  850 */     stop0(new ThreadDeath());
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
/*      */   @Deprecated
/*      */   public final synchronized void stop(Throwable paramThrowable)
/*      */   {
/*  869 */     throw new UnsupportedOperationException();
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
/*      */   public void interrupt()
/*      */   {
/*  912 */     if (this != currentThread()) {
/*  913 */       checkAccess();
/*      */     }
/*  915 */     synchronized (this.blockerLock) {
/*  916 */       Interruptible localInterruptible = this.blocker;
/*  917 */       if (localInterruptible != null) {
/*  918 */         interrupt0();
/*  919 */         localInterruptible.interrupt(this);
/*  920 */         return;
/*      */       }
/*      */     }
/*  923 */     interrupt0();
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
/*      */   public static boolean interrupted()
/*      */   {
/*  944 */     return currentThread().isInterrupted(true);
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
/*      */   public boolean isInterrupted()
/*      */   {
/*  961 */     return isInterrupted(false);
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
/*      */   private native boolean isInterrupted(boolean paramBoolean);
/*      */   
/*      */ 
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
/*      */   public void destroy()
/*      */   {
/*  990 */     throw new NoSuchMethodError();
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
/*      */   public final native boolean isAlive();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public final void suspend()
/*      */   {
/* 1028 */     checkAccess();
/* 1029 */     suspend0();
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
/*      */   @Deprecated
/*      */   public final void resume()
/*      */   {
/* 1054 */     checkAccess();
/* 1055 */     resume0();
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
/*      */   public final void setPriority(int paramInt)
/*      */   {
/* 1084 */     checkAccess();
/* 1085 */     if ((paramInt > 10) || (paramInt < 1))
/* 1086 */       throw new IllegalArgumentException();
/*      */     ThreadGroup localThreadGroup;
/* 1088 */     if ((localThreadGroup = getThreadGroup()) != null) {
/* 1089 */       if (paramInt > localThreadGroup.getMaxPriority()) {
/* 1090 */         paramInt = localThreadGroup.getMaxPriority();
/*      */       }
/* 1092 */       setPriority0(this.priority = paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getPriority()
/*      */   {
/* 1103 */     return this.priority;
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
/*      */   public final synchronized void setName(String paramString)
/*      */   {
/* 1121 */     checkAccess();
/* 1122 */     this.name = paramString.toCharArray();
/* 1123 */     if (this.threadStatus != 0) {
/* 1124 */       setNativeName(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getName()
/*      */   {
/* 1135 */     return new String(this.name, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ThreadGroup getThreadGroup()
/*      */   {
/* 1146 */     return this.group;
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
/*      */   public static int activeCount()
/*      */   {
/* 1166 */     return currentThread().getThreadGroup().activeCount();
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
/*      */   public static int enumerate(Thread[] paramArrayOfThread)
/*      */   {
/* 1196 */     return currentThread().getThreadGroup().enumerate(paramArrayOfThread);
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
/*      */   @Deprecated
/*      */   public native int countStackFrames();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized void join(long paramLong)
/*      */     throws InterruptedException
/*      */   {
/* 1236 */     long l1 = System.currentTimeMillis();
/* 1237 */     long l2 = 0L;
/*      */     
/* 1239 */     if (paramLong < 0L) {
/* 1240 */       throw new IllegalArgumentException("timeout value is negative");
/*      */     }
/*      */     
/* 1243 */     if (paramLong == 0L) {
/* 1244 */       while (isAlive()) {
/* 1245 */         wait(0L);
/*      */       }
/*      */     }
/* 1248 */     while (isAlive()) {
/* 1249 */       long l3 = paramLong - l2;
/* 1250 */       if (l3 <= 0L) {
/*      */         break;
/*      */       }
/* 1253 */       wait(l3);
/* 1254 */       l2 = System.currentTimeMillis() - l1;
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
/*      */   public final synchronized void join(long paramLong, int paramInt)
/*      */     throws InterruptedException
/*      */   {
/* 1287 */     if (paramLong < 0L) {
/* 1288 */       throw new IllegalArgumentException("timeout value is negative");
/*      */     }
/*      */     
/* 1291 */     if ((paramInt < 0) || (paramInt > 999999)) {
/* 1292 */       throw new IllegalArgumentException("nanosecond timeout value out of range");
/*      */     }
/*      */     
/*      */ 
/* 1296 */     if ((paramInt >= 500000) || ((paramInt != 0) && (paramLong == 0L))) {
/* 1297 */       paramLong += 1L;
/*      */     }
/*      */     
/* 1300 */     join(paramLong);
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
/*      */   public final void join()
/*      */     throws InterruptedException
/*      */   {
/* 1319 */     join(0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void dumpStack()
/*      */   {
/* 1329 */     new Exception("Stack trace").printStackTrace();
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
/*      */   public final void setDaemon(boolean paramBoolean)
/*      */   {
/* 1350 */     checkAccess();
/* 1351 */     if (isAlive()) {
/* 1352 */       throw new IllegalThreadStateException();
/*      */     }
/* 1354 */     this.daemon = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isDaemon()
/*      */   {
/* 1365 */     return this.daemon;
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
/*      */   public final void checkAccess()
/*      */   {
/* 1381 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1382 */     if (localSecurityManager != null) {
/* 1383 */       localSecurityManager.checkAccess(this);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1394 */     ThreadGroup localThreadGroup = getThreadGroup();
/* 1395 */     if (localThreadGroup != null)
/*      */     {
/* 1397 */       return "Thread[" + getName() + "," + getPriority() + "," + localThreadGroup.getName() + "]";
/*      */     }
/* 1399 */     return "Thread[" + getName() + "," + getPriority() + "," + "" + "]";
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
/*      */   public ClassLoader getContextClassLoader()
/*      */   {
/* 1432 */     if (this.contextClassLoader == null)
/* 1433 */       return null;
/* 1434 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1435 */     if (localSecurityManager != null) {
/* 1436 */       ClassLoader.checkClassLoaderPermission(this.contextClassLoader, 
/* 1437 */         Reflection.getCallerClass());
/*      */     }
/* 1439 */     return this.contextClassLoader;
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
/*      */   public void setContextClassLoader(ClassLoader paramClassLoader)
/*      */   {
/* 1465 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1466 */     if (localSecurityManager != null) {
/* 1467 */       localSecurityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*      */     }
/* 1469 */     this.contextClassLoader = paramClassLoader;
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
/* 1490 */   private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static native boolean holdsLock(Object paramObject);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StackTraceElement[] getStackTrace()
/*      */   {
/* 1530 */     if (this != currentThread())
/*      */     {
/* 1532 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 1533 */       if (localSecurityManager != null) {
/* 1534 */         localSecurityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1539 */       if (!isAlive()) {
/* 1540 */         return EMPTY_STACK_TRACE;
/*      */       }
/* 1542 */       StackTraceElement[][] arrayOfStackTraceElement = dumpThreads(new Thread[] { this });
/* 1543 */       StackTraceElement[] arrayOfStackTraceElement1 = arrayOfStackTraceElement[0];
/*      */       
/*      */ 
/* 1546 */       if (arrayOfStackTraceElement1 == null) {
/* 1547 */         arrayOfStackTraceElement1 = EMPTY_STACK_TRACE;
/*      */       }
/* 1549 */       return arrayOfStackTraceElement1;
/*      */     }
/*      */     
/* 1552 */     return new Exception().getStackTrace();
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
/*      */   public static Map<Thread, StackTraceElement[]> getAllStackTraces()
/*      */   {
/* 1593 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1594 */     if (localSecurityManager != null) {
/* 1595 */       localSecurityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
/*      */       
/* 1597 */       localSecurityManager.checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1602 */     Thread[] arrayOfThread = getThreads();
/* 1603 */     StackTraceElement[][] arrayOfStackTraceElement = dumpThreads(arrayOfThread);
/* 1604 */     HashMap localHashMap = new HashMap(arrayOfThread.length);
/* 1605 */     for (int i = 0; i < arrayOfThread.length; i++) {
/* 1606 */       StackTraceElement[] arrayOfStackTraceElement1 = arrayOfStackTraceElement[i];
/* 1607 */       if (arrayOfStackTraceElement1 != null) {
/* 1608 */         localHashMap.put(arrayOfThread[i], arrayOfStackTraceElement1);
/*      */       }
/*      */     }
/*      */     
/* 1612 */     return localHashMap;
/*      */   }
/*      */   
/*      */ 
/* 1616 */   private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION = new RuntimePermission("enableContextClassLoaderOverride");
/*      */   
/*      */   private volatile UncaughtExceptionHandler uncaughtExceptionHandler;
/*      */   
/*      */   private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;
/*      */   
/*      */   private static class Caches
/*      */   {
/* 1624 */     static final ConcurrentMap<Thread.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap();
/*      */     
/*      */ 
/*      */ 
/* 1628 */     static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isCCLOverridden(Class<?> paramClass)
/*      */   {
/* 1639 */     if (paramClass == Thread.class) {
/* 1640 */       return false;
/*      */     }
/* 1642 */     processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
/* 1643 */     WeakClassKey localWeakClassKey = new WeakClassKey(paramClass, Caches.subclassAuditsQueue);
/* 1644 */     Boolean localBoolean = (Boolean)Caches.subclassAudits.get(localWeakClassKey);
/* 1645 */     if (localBoolean == null) {
/* 1646 */       localBoolean = Boolean.valueOf(auditSubclass(paramClass));
/* 1647 */       Caches.subclassAudits.putIfAbsent(localWeakClassKey, localBoolean);
/*      */     }
/*      */     
/* 1650 */     return localBoolean.booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean auditSubclass(Class<?> paramClass)
/*      */   {
/* 1659 */     Boolean localBoolean = (Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Boolean run() {
/* 1662 */         for (Class localClass = this.val$subcl; 
/* 1663 */             localClass != Thread.class; 
/* 1664 */             localClass = localClass.getSuperclass()) {
/*      */           try
/*      */           {
/* 1667 */             localClass.getDeclaredMethod("getContextClassLoader", new Class[0]);
/* 1668 */             return Boolean.TRUE;
/*      */           }
/*      */           catch (NoSuchMethodException localNoSuchMethodException1) {
/*      */             try {
/* 1672 */               Class[] arrayOfClass = { ClassLoader.class };
/* 1673 */               localClass.getDeclaredMethod("setContextClassLoader", arrayOfClass);
/* 1674 */               return Boolean.TRUE;
/*      */             } catch (NoSuchMethodException localNoSuchMethodException2) {}
/*      */           }
/*      */         }
/* 1678 */         return Boolean.FALSE;
/*      */       }
/*      */       
/* 1681 */     });
/* 1682 */     return localBoolean.booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native StackTraceElement[][] dumpThreads(Thread[] paramArrayOfThread);
/*      */   
/*      */ 
/*      */ 
/*      */   private static native Thread[] getThreads();
/*      */   
/*      */ 
/*      */ 
/*      */   public long getId()
/*      */   {
/* 1698 */     return this.tid;
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
/*      */   public static enum State
/*      */   {
/* 1739 */     NEW, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1747 */     RUNNABLE, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1756 */     BLOCKED, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1777 */     WAITING, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1791 */     TIMED_WAITING, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1797 */     TERMINATED;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private State() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public State getState()
/*      */   {
/* 1810 */     return VM.toThreadState(this.threadStatus);
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
/*      */   @Contended("tlr")
/*      */   long threadLocalRandomSeed;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Contended("tlr")
/*      */   int threadLocalRandomProbe;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Contended("tlr")
/*      */   int threadLocalRandomSecondarySeed;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler paramUncaughtExceptionHandler)
/*      */   {
/* 1891 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1892 */     if (localSecurityManager != null) {
/* 1893 */       localSecurityManager.checkPermission(new RuntimePermission("setDefaultUncaughtExceptionHandler"));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1898 */     defaultUncaughtExceptionHandler = paramUncaughtExceptionHandler;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler()
/*      */   {
/* 1910 */     return defaultUncaughtExceptionHandler;
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
/*      */   public UncaughtExceptionHandler getUncaughtExceptionHandler()
/*      */   {
/* 1923 */     return this.uncaughtExceptionHandler != null ? this.uncaughtExceptionHandler : this.group;
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
/*      */   public void setUncaughtExceptionHandler(UncaughtExceptionHandler paramUncaughtExceptionHandler)
/*      */   {
/* 1943 */     checkAccess();
/* 1944 */     this.uncaughtExceptionHandler = paramUncaughtExceptionHandler;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void dispatchUncaughtException(Throwable paramThrowable)
/*      */   {
/* 1952 */     getUncaughtExceptionHandler().uncaughtException(this, paramThrowable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void processQueue(ReferenceQueue<Class<?>> paramReferenceQueue, ConcurrentMap<? extends WeakReference<Class<?>>, ?> paramConcurrentMap)
/*      */   {
/*      */     Reference localReference;
/*      */     
/*      */ 
/*      */ 
/* 1964 */     while ((localReference = paramReferenceQueue.poll()) != null)
/* 1965 */       paramConcurrentMap.remove(localReference); }
/*      */   
/*      */   private native void setPriority0(int paramInt);
/*      */   
/*      */   private native void stop0(Object paramObject);
/*      */   
/*      */   private native void suspend0();
/*      */   
/*      */   private native void resume0();
/*      */   
/*      */   private native void interrupt0();
/*      */   
/*      */   private native void setNativeName(String paramString);
/*      */   
/*      */   static {}
/*      */   
/*      */   @FunctionalInterface
/*      */   public static abstract interface UncaughtExceptionHandler { public abstract void uncaughtException(Thread paramThread, Throwable paramThrowable); }
/*      */   
/* 1984 */   static class WeakClassKey extends WeakReference<Class<?>> { WeakClassKey(Class<?> paramClass, ReferenceQueue<Class<?>> paramReferenceQueue) { super(paramReferenceQueue);
/* 1985 */       this.hash = System.identityHashCode(paramClass);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 1993 */       return this.hash;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int hash;
/*      */     
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/* 2004 */       if (paramObject == this) {
/* 2005 */         return true;
/*      */       }
/* 2007 */       if ((paramObject instanceof WeakClassKey)) {
/* 2008 */         Object localObject = get();
/*      */         
/* 2010 */         return (localObject != null) && (localObject == ((WeakClassKey)paramObject).get());
/*      */       }
/* 2012 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Thread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */