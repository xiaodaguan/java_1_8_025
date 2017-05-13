/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThreadPoolExecutor
/*      */   extends AbstractExecutorService
/*      */ {
/*  377 */   private final AtomicInteger ctl = new AtomicInteger(ctlOf(-536870912, 0));
/*      */   
/*      */   private static final int COUNT_BITS = 29;
/*      */   
/*      */   private static final int CAPACITY = 536870911;
/*      */   private static final int RUNNING = -536870912;
/*      */   private static final int SHUTDOWN = 0;
/*      */   private static final int STOP = 536870912;
/*      */   private static final int TIDYING = 1073741824;
/*      */   private static final int TERMINATED = 1610612736;
/*      */   private final BlockingQueue<Runnable> workQueue;
/*      */   
/*  389 */   private static int runStateOf(int paramInt) { return paramInt & 0xE0000000; }
/*  390 */   private static int workerCountOf(int paramInt) { return paramInt & 0x1FFFFFFF; }
/*  391 */   private static int ctlOf(int paramInt1, int paramInt2) { return paramInt1 | paramInt2; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean runStateLessThan(int paramInt1, int paramInt2)
/*      */   {
/*  399 */     return paramInt1 < paramInt2;
/*      */   }
/*      */   
/*      */   private static boolean runStateAtLeast(int paramInt1, int paramInt2) {
/*  403 */     return paramInt1 >= paramInt2;
/*      */   }
/*      */   
/*      */   private static boolean isRunning(int paramInt) {
/*  407 */     return paramInt < 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean compareAndIncrementWorkerCount(int paramInt)
/*      */   {
/*  414 */     return this.ctl.compareAndSet(paramInt, paramInt + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean compareAndDecrementWorkerCount(int paramInt)
/*      */   {
/*  421 */     return this.ctl.compareAndSet(paramInt, paramInt - 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void decrementWorkerCount()
/*      */   {
/*  430 */     while (!compareAndDecrementWorkerCount(this.ctl.get())) {}
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
/*  459 */   private final ReentrantLock mainLock = new ReentrantLock();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  465 */   private final HashSet<Worker> workers = new HashSet();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  470 */   private final Condition termination = this.mainLock.newCondition();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int largestPoolSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long completedTaskCount;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile ThreadFactory threadFactory;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile RejectedExecutionHandler handler;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile long keepAliveTime;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile boolean allowCoreThreadTimeOut;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile int corePoolSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile int maximumPoolSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  546 */   private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  569 */   private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final boolean ONLY_ONE = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final class Worker
/*      */     extends AbstractQueuedSynchronizer
/*      */     implements Runnable
/*      */   {
/*      */     private static final long serialVersionUID = 6138294804551838833L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final Thread thread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Runnable firstTask;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile long completedTasks;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Worker(Runnable paramRunnable)
/*      */     {
/*  610 */       setState(-1);
/*  611 */       this.firstTask = paramRunnable;
/*  612 */       this.thread = ThreadPoolExecutor.this.getThreadFactory().newThread(this);
/*      */     }
/*      */     
/*      */     public void run()
/*      */     {
/*  617 */       ThreadPoolExecutor.this.runWorker(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected boolean isHeldExclusively()
/*      */     {
/*  626 */       return getState() != 0;
/*      */     }
/*      */     
/*      */     protected boolean tryAcquire(int paramInt) {
/*  630 */       if (compareAndSetState(0, 1)) {
/*  631 */         setExclusiveOwnerThread(Thread.currentThread());
/*  632 */         return true;
/*      */       }
/*  634 */       return false;
/*      */     }
/*      */     
/*      */     protected boolean tryRelease(int paramInt) {
/*  638 */       setExclusiveOwnerThread(null);
/*  639 */       setState(0);
/*  640 */       return true;
/*      */     }
/*      */     
/*  643 */     public void lock() { acquire(1); }
/*  644 */     public boolean tryLock() { return tryAcquire(1); }
/*  645 */     public void unlock() { release(1); }
/*  646 */     public boolean isLocked() { return isHeldExclusively(); }
/*      */     
/*      */     void interruptIfStarted() {
/*      */       Thread localThread;
/*  650 */       if ((getState() >= 0) && ((localThread = this.thread) != null) && (!localThread.isInterrupted())) {
/*      */         try {
/*  652 */           localThread.interrupt();
/*      */         }
/*      */         catch (SecurityException localSecurityException) {}
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
/*      */   private void advanceRunState(int paramInt)
/*      */   {
/*      */     for (;;)
/*      */     {
/*  672 */       int i = this.ctl.get();
/*  673 */       if ((runStateAtLeast(i, paramInt)) || 
/*  674 */         (this.ctl.compareAndSet(i, ctlOf(paramInt, workerCountOf(i))))) {
/*      */         break;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   final void tryTerminate()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 4	java/util/concurrent/ThreadPoolExecutor:ctl	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   4: invokevirtual 6	java/util/concurrent/atomic/AtomicInteger:get	()I
/*      */     //   7: istore_1
/*      */     //   8: iload_1
/*      */     //   9: invokestatic 11	java/util/concurrent/ThreadPoolExecutor:isRunning	(I)Z
/*      */     //   12: ifne +31 -> 43
/*      */     //   15: iload_1
/*      */     //   16: ldc 12
/*      */     //   18: invokestatic 8	java/util/concurrent/ThreadPoolExecutor:runStateAtLeast	(II)Z
/*      */     //   21: ifne +22 -> 43
/*      */     //   24: iload_1
/*      */     //   25: invokestatic 13	java/util/concurrent/ThreadPoolExecutor:runStateOf	(I)I
/*      */     //   28: ifne +16 -> 44
/*      */     //   31: aload_0
/*      */     //   32: getfield 14	java/util/concurrent/ThreadPoolExecutor:workQueue	Ljava/util/concurrent/BlockingQueue;
/*      */     //   35: invokeinterface 15 1 0
/*      */     //   40: ifne +4 -> 44
/*      */     //   43: return
/*      */     //   44: iload_1
/*      */     //   45: invokestatic 9	java/util/concurrent/ThreadPoolExecutor:workerCountOf	(I)I
/*      */     //   48: ifeq +9 -> 57
/*      */     //   51: aload_0
/*      */     //   52: iconst_1
/*      */     //   53: invokespecial 16	java/util/concurrent/ThreadPoolExecutor:interruptIdleWorkers	(Z)V
/*      */     //   56: return
/*      */     //   57: aload_0
/*      */     //   58: getfield 17	java/util/concurrent/ThreadPoolExecutor:mainLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   61: astore_2
/*      */     //   62: aload_2
/*      */     //   63: invokevirtual 18	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   66: aload_0
/*      */     //   67: getfield 4	java/util/concurrent/ThreadPoolExecutor:ctl	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   70: iload_1
/*      */     //   71: ldc 12
/*      */     //   73: iconst_0
/*      */     //   74: invokestatic 10	java/util/concurrent/ThreadPoolExecutor:ctlOf	(II)I
/*      */     //   77: invokevirtual 5	java/util/concurrent/atomic/AtomicInteger:compareAndSet	(II)Z
/*      */     //   80: ifeq +62 -> 142
/*      */     //   83: aload_0
/*      */     //   84: invokevirtual 19	java/util/concurrent/ThreadPoolExecutor:terminated	()V
/*      */     //   87: aload_0
/*      */     //   88: getfield 4	java/util/concurrent/ThreadPoolExecutor:ctl	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   91: ldc 20
/*      */     //   93: iconst_0
/*      */     //   94: invokestatic 10	java/util/concurrent/ThreadPoolExecutor:ctlOf	(II)I
/*      */     //   97: invokevirtual 21	java/util/concurrent/atomic/AtomicInteger:set	(I)V
/*      */     //   100: aload_0
/*      */     //   101: getfield 22	java/util/concurrent/ThreadPoolExecutor:termination	Ljava/util/concurrent/locks/Condition;
/*      */     //   104: invokeinterface 23 1 0
/*      */     //   109: goto +28 -> 137
/*      */     //   112: astore_3
/*      */     //   113: aload_0
/*      */     //   114: getfield 4	java/util/concurrent/ThreadPoolExecutor:ctl	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   117: ldc 20
/*      */     //   119: iconst_0
/*      */     //   120: invokestatic 10	java/util/concurrent/ThreadPoolExecutor:ctlOf	(II)I
/*      */     //   123: invokevirtual 21	java/util/concurrent/atomic/AtomicInteger:set	(I)V
/*      */     //   126: aload_0
/*      */     //   127: getfield 22	java/util/concurrent/ThreadPoolExecutor:termination	Ljava/util/concurrent/locks/Condition;
/*      */     //   130: invokeinterface 23 1 0
/*      */     //   135: aload_3
/*      */     //   136: athrow
/*      */     //   137: aload_2
/*      */     //   138: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   141: return
/*      */     //   142: aload_2
/*      */     //   143: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   146: goto +12 -> 158
/*      */     //   149: astore 4
/*      */     //   151: aload_2
/*      */     //   152: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   155: aload 4
/*      */     //   157: athrow
/*      */     //   158: goto -158 -> 0
/*      */     // Line number table:
/*      */     //   Java source line #691	-> byte code offset #0
/*      */     //   Java source line #692	-> byte code offset #8
/*      */     //   Java source line #693	-> byte code offset #18
/*      */     //   Java source line #694	-> byte code offset #25
/*      */     //   Java source line #695	-> byte code offset #43
/*      */     //   Java source line #696	-> byte code offset #44
/*      */     //   Java source line #697	-> byte code offset #51
/*      */     //   Java source line #698	-> byte code offset #56
/*      */     //   Java source line #701	-> byte code offset #57
/*      */     //   Java source line #702	-> byte code offset #62
/*      */     //   Java source line #704	-> byte code offset #66
/*      */     //   Java source line #706	-> byte code offset #83
/*      */     //   Java source line #708	-> byte code offset #87
/*      */     //   Java source line #709	-> byte code offset #100
/*      */     //   Java source line #710	-> byte code offset #109
/*      */     //   Java source line #708	-> byte code offset #112
/*      */     //   Java source line #709	-> byte code offset #126
/*      */     //   Java source line #714	-> byte code offset #137
/*      */     //   Java source line #711	-> byte code offset #141
/*      */     //   Java source line #714	-> byte code offset #142
/*      */     //   Java source line #715	-> byte code offset #146
/*      */     //   Java source line #714	-> byte code offset #149
/*      */     //   Java source line #717	-> byte code offset #158
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	161	0	this	ThreadPoolExecutor
/*      */     //   7	64	1	i	int
/*      */     //   61	91	2	localReentrantLock	ReentrantLock
/*      */     //   112	24	3	localObject1	Object
/*      */     //   149	7	4	localObject2	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   83	87	112	finally
/*      */     //   66	137	149	finally
/*      */     //   149	151	149	finally
/*      */   }
/*      */   
/*      */   private void checkShutdownAccess()
/*      */   {
/*  733 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  734 */     if (localSecurityManager != null) {
/*  735 */       localSecurityManager.checkPermission(shutdownPerm);
/*  736 */       ReentrantLock localReentrantLock = this.mainLock;
/*  737 */       localReentrantLock.lock();
/*      */       try {
/*  739 */         for (Worker localWorker : this.workers)
/*  740 */           localSecurityManager.checkAccess(localWorker.thread);
/*      */       } finally {
/*  742 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void interruptWorkers()
/*      */   {
/*  752 */     ReentrantLock localReentrantLock = this.mainLock;
/*  753 */     localReentrantLock.lock();
/*      */     try {
/*  755 */       for (Worker localWorker : this.workers)
/*  756 */         localWorker.interruptIfStarted();
/*      */     } finally {
/*  758 */       localReentrantLock.unlock();
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
/*      */   private void interruptIdleWorkers(boolean paramBoolean)
/*      */   {
/*  782 */     ReentrantLock localReentrantLock = this.mainLock;
/*  783 */     localReentrantLock.lock();
/*      */     try {
/*  785 */       for (Worker localWorker : this.workers) {
/*  786 */         Thread localThread = localWorker.thread;
/*  787 */         if ((!localThread.isInterrupted()) && (localWorker.tryLock())) {
/*      */           try {}catch (SecurityException localSecurityException) {}finally {}
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  795 */         if (paramBoolean)
/*      */           break;
/*      */       }
/*      */     } finally {
/*  799 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void interruptIdleWorkers()
/*      */   {
/*  808 */     interruptIdleWorkers(false);
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
/*      */   final void reject(Runnable paramRunnable)
/*      */   {
/*  823 */     this.handler.rejectedExecution(paramRunnable, this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void onShutdown() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean isRunningOrShutdown(boolean paramBoolean)
/*      */   {
/*  841 */     int i = runStateOf(this.ctl.get());
/*  842 */     return (i == -536870912) || ((i == 0) && (paramBoolean));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private List<Runnable> drainQueue()
/*      */   {
/*  852 */     BlockingQueue localBlockingQueue = this.workQueue;
/*  853 */     ArrayList localArrayList = new ArrayList();
/*  854 */     localBlockingQueue.drainTo(localArrayList);
/*  855 */     if (!localBlockingQueue.isEmpty()) {
/*  856 */       for (Runnable localRunnable : (Runnable[])localBlockingQueue.toArray(new Runnable[0])) {
/*  857 */         if (localBlockingQueue.remove(localRunnable))
/*  858 */           localArrayList.add(localRunnable);
/*      */       }
/*      */     }
/*  861 */     return localArrayList;
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
/*      */   private boolean addWorker(Runnable paramRunnable, boolean paramBoolean)
/*      */   {
/*  897 */     int i = this.ctl.get();
/*  898 */     int j = runStateOf(i);
/*      */     
/*      */ 
/*  901 */     if ((j >= 0) && ((j != 0) || (paramRunnable != null) || 
/*      */     
/*      */ 
/*  904 */       (this.workQueue.isEmpty()))) {
/*  905 */       return false;
/*      */     }
/*      */     for (;;) {
/*  908 */       int k = workerCountOf(i);
/*  909 */       if (k < 536870911) { if (k < (paramBoolean ? this.corePoolSize : this.maximumPoolSize)) {}
/*      */       } else
/*  911 */         return false;
/*  912 */       if (compareAndIncrementWorkerCount(i))
/*      */         break label111;
/*  914 */       i = this.ctl.get();
/*  915 */       if (runStateOf(i) != j) {
/*      */         break;
/*      */       }
/*      */     }
/*      */     
/*      */     label111:
/*  921 */     i = 0;
/*  922 */     j = 0;
/*  923 */     Worker localWorker = null;
/*      */     try {
/*  925 */       localWorker = new Worker(paramRunnable);
/*  926 */       Thread localThread = localWorker.thread;
/*  927 */       if (localThread != null) {
/*  928 */         ReentrantLock localReentrantLock = this.mainLock;
/*  929 */         localReentrantLock.lock();
/*      */         
/*      */ 
/*      */         try
/*      */         {
/*  934 */           int m = runStateOf(this.ctl.get());
/*      */           
/*  936 */           if ((m < 0) || ((m == 0) && (paramRunnable == null)))
/*      */           {
/*  938 */             if (localThread.isAlive())
/*  939 */               throw new IllegalThreadStateException();
/*  940 */             this.workers.add(localWorker);
/*  941 */             int n = this.workers.size();
/*  942 */             if (n > this.largestPoolSize)
/*  943 */               this.largestPoolSize = n;
/*  944 */             j = 1;
/*      */           }
/*      */         } finally {
/*  947 */           localReentrantLock.unlock();
/*      */         }
/*  949 */         if (j != 0) {
/*  950 */           localThread.start();
/*  951 */           i = 1;
/*      */         }
/*      */       }
/*      */     } finally {
/*  955 */       if (i == 0)
/*  956 */         addWorkerFailed(localWorker);
/*      */     }
/*  958 */     return i;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void addWorkerFailed(Worker paramWorker)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 17	java/util/concurrent/ThreadPoolExecutor:mainLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_2
/*      */     //   5: aload_2
/*      */     //   6: invokevirtual 18	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_1
/*      */     //   10: ifnull +12 -> 22
/*      */     //   13: aload_0
/*      */     //   14: getfield 28	java/util/concurrent/ThreadPoolExecutor:workers	Ljava/util/HashSet;
/*      */     //   17: aload_1
/*      */     //   18: invokevirtual 63	java/util/HashSet:remove	(Ljava/lang/Object;)Z
/*      */     //   21: pop
/*      */     //   22: aload_0
/*      */     //   23: invokespecial 64	java/util/concurrent/ThreadPoolExecutor:decrementWorkerCount	()V
/*      */     //   26: aload_0
/*      */     //   27: invokevirtual 65	java/util/concurrent/ThreadPoolExecutor:tryTerminate	()V
/*      */     //   30: aload_2
/*      */     //   31: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   34: goto +10 -> 44
/*      */     //   37: astore_3
/*      */     //   38: aload_2
/*      */     //   39: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   42: aload_3
/*      */     //   43: athrow
/*      */     //   44: return
/*      */     // Line number table:
/*      */     //   Java source line #969	-> byte code offset #0
/*      */     //   Java source line #970	-> byte code offset #5
/*      */     //   Java source line #972	-> byte code offset #9
/*      */     //   Java source line #973	-> byte code offset #13
/*      */     //   Java source line #974	-> byte code offset #22
/*      */     //   Java source line #975	-> byte code offset #26
/*      */     //   Java source line #977	-> byte code offset #30
/*      */     //   Java source line #978	-> byte code offset #34
/*      */     //   Java source line #977	-> byte code offset #37
/*      */     //   Java source line #979	-> byte code offset #44
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	45	0	this	ThreadPoolExecutor
/*      */     //   0	45	1	paramWorker	Worker
/*      */     //   4	35	2	localReentrantLock	ReentrantLock
/*      */     //   37	6	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	30	37	finally
/*      */   }
/*      */   
/*      */   private void processWorkerExit(Worker paramWorker, boolean paramBoolean)
/*      */   {
/*  995 */     if (paramBoolean) {
/*  996 */       decrementWorkerCount();
/*      */     }
/*  998 */     ReentrantLock localReentrantLock = this.mainLock;
/*  999 */     localReentrantLock.lock();
/*      */     try {
/* 1001 */       this.completedTaskCount += paramWorker.completedTasks;
/* 1002 */       this.workers.remove(paramWorker);
/*      */     } finally {
/* 1004 */       localReentrantLock.unlock();
/*      */     }
/*      */     
/* 1007 */     tryTerminate();
/*      */     
/* 1009 */     int i = this.ctl.get();
/* 1010 */     if (runStateLessThan(i, 536870912)) {
/* 1011 */       if (!paramBoolean) {
/* 1012 */         int j = this.allowCoreThreadTimeOut ? 0 : this.corePoolSize;
/* 1013 */         if ((j == 0) && (!this.workQueue.isEmpty()))
/* 1014 */           j = 1;
/* 1015 */         if (workerCountOf(i) >= j)
/* 1016 */           return;
/*      */       }
/* 1018 */       addWorker(null, false);
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
/*      */   private Runnable getTask()
/*      */   {
/* 1040 */     int i = 0;
/*      */     for (;;)
/*      */     {
/* 1043 */       int j = this.ctl.get();
/* 1044 */       int k = runStateOf(j);
/*      */       
/*      */ 
/* 1047 */       if ((k >= 0) && ((k >= 536870912) || (this.workQueue.isEmpty()))) {
/* 1048 */         decrementWorkerCount();
/* 1049 */         return null;
/*      */       }
/*      */       
/* 1052 */       int m = workerCountOf(j);
/*      */       
/*      */ 
/* 1055 */       int n = (this.allowCoreThreadTimeOut) || (m > this.corePoolSize) ? 1 : 0;
/*      */       
/* 1057 */       if (((m > this.maximumPoolSize) || ((n != 0) && (i != 0))) && ((m > 1) || 
/* 1058 */         (this.workQueue.isEmpty()))) {
/* 1059 */         if (compareAndDecrementWorkerCount(j)) {
/* 1060 */           return null;
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/*      */         try
/*      */         {
/* 1067 */           Runnable localRunnable = n != 0 ? (Runnable)this.workQueue.poll(this.keepAliveTime, TimeUnit.NANOSECONDS) : (Runnable)this.workQueue.take();
/* 1068 */           if (localRunnable != null)
/* 1069 */             return localRunnable;
/* 1070 */           i = 1;
/*      */         } catch (InterruptedException localInterruptedException) {
/* 1072 */           i = 0;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void runWorker(Worker paramWorker)
/*      */   {
/* 1121 */     Thread localThread = Thread.currentThread();
/* 1122 */     Runnable localRunnable = paramWorker.firstTask;
/* 1123 */     paramWorker.firstTask = null;
/* 1124 */     paramWorker.unlock();
/* 1125 */     boolean bool = true;
/*      */     try {
/* 1127 */       while ((localRunnable != null) || ((localRunnable = getTask()) != null)) {
/* 1128 */         paramWorker.lock();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1133 */         if (((runStateAtLeast(this.ctl.get(), 536870912)) || (
/* 1134 */           (Thread.interrupted()) && 
/* 1135 */           (runStateAtLeast(this.ctl.get(), 536870912)))) && 
/* 1136 */           (!localThread.isInterrupted()))
/* 1137 */           localThread.interrupt();
/*      */         try {
/* 1139 */           beforeExecute(localThread, localRunnable);
/* 1140 */           Object localObject1 = null;
/*      */           try {
/* 1142 */             localRunnable.run();
/*      */           } catch (RuntimeException localRuntimeException) {
/* 1144 */             localObject1 = localRuntimeException;throw localRuntimeException;
/*      */           } catch (Error localError) {
/* 1146 */             localObject1 = localError;throw localError;
/*      */           } catch (Throwable localThrowable) {
/* 1148 */             localObject1 = localThrowable;throw new Error(localThrowable);
/*      */           } finally {
/* 1150 */             afterExecute(localRunnable, (Throwable)localObject1);
/*      */           }
/*      */           
/* 1153 */           localRunnable = null;
/* 1154 */           paramWorker.completedTasks += 1L;
/* 1155 */           paramWorker.unlock();
/*      */         }
/*      */         finally
/*      */         {
/* 1153 */           localRunnable = null;
/* 1154 */           paramWorker.completedTasks += 1L;
/* 1155 */           paramWorker.unlock();
/*      */         }
/*      */       }
/* 1158 */       bool = false;
/*      */     } finally {
/* 1160 */       processWorkerExit(paramWorker, bool);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue)
/*      */   {
/* 1195 */     this(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, 
/* 1196 */       Executors.defaultThreadFactory(), defaultHandler);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory)
/*      */   {
/* 1230 */     this(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory, defaultHandler);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, RejectedExecutionHandler paramRejectedExecutionHandler)
/*      */   {
/* 1265 */     this(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, 
/* 1266 */       Executors.defaultThreadFactory(), paramRejectedExecutionHandler);
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
/*      */   public ThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler)
/*      */   {
/* 1303 */     if ((paramInt1 < 0) || (paramInt2 <= 0) || (paramInt2 < paramInt1) || (paramLong < 0L))
/*      */     {
/*      */ 
/*      */ 
/* 1307 */       throw new IllegalArgumentException(); }
/* 1308 */     if ((paramBlockingQueue == null) || (paramThreadFactory == null) || (paramRejectedExecutionHandler == null))
/* 1309 */       throw new NullPointerException();
/* 1310 */     this.corePoolSize = paramInt1;
/* 1311 */     this.maximumPoolSize = paramInt2;
/* 1312 */     this.workQueue = paramBlockingQueue;
/* 1313 */     this.keepAliveTime = paramTimeUnit.toNanos(paramLong);
/* 1314 */     this.threadFactory = paramThreadFactory;
/* 1315 */     this.handler = paramRejectedExecutionHandler;
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
/*      */   public void execute(Runnable paramRunnable)
/*      */   {
/* 1333 */     if (paramRunnable == null) {
/* 1334 */       throw new NullPointerException();
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
/* 1355 */     int i = this.ctl.get();
/* 1356 */     if (workerCountOf(i) < this.corePoolSize) {
/* 1357 */       if (addWorker(paramRunnable, true))
/* 1358 */         return;
/* 1359 */       i = this.ctl.get();
/*      */     }
/* 1361 */     if ((isRunning(i)) && (this.workQueue.offer(paramRunnable))) {
/* 1362 */       int j = this.ctl.get();
/* 1363 */       if ((!isRunning(j)) && (remove(paramRunnable))) {
/* 1364 */         reject(paramRunnable);
/* 1365 */       } else if (workerCountOf(j) == 0) {
/* 1366 */         addWorker(null, false);
/*      */       }
/* 1368 */     } else if (!addWorker(paramRunnable, false)) {
/* 1369 */       reject(paramRunnable);
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void shutdown()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 17	java/util/concurrent/ThreadPoolExecutor:mainLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 18	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: invokespecial 110	java/util/concurrent/ThreadPoolExecutor:checkShutdownAccess	()V
/*      */     //   13: aload_0
/*      */     //   14: iconst_0
/*      */     //   15: invokespecial 111	java/util/concurrent/ThreadPoolExecutor:advanceRunState	(I)V
/*      */     //   18: aload_0
/*      */     //   19: invokespecial 112	java/util/concurrent/ThreadPoolExecutor:interruptIdleWorkers	()V
/*      */     //   22: aload_0
/*      */     //   23: invokevirtual 113	java/util/concurrent/ThreadPoolExecutor:onShutdown	()V
/*      */     //   26: aload_1
/*      */     //   27: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   30: goto +10 -> 40
/*      */     //   33: astore_2
/*      */     //   34: aload_1
/*      */     //   35: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   38: aload_2
/*      */     //   39: athrow
/*      */     //   40: aload_0
/*      */     //   41: invokevirtual 65	java/util/concurrent/ThreadPoolExecutor:tryTerminate	()V
/*      */     //   44: return
/*      */     // Line number table:
/*      */     //   Java source line #1384	-> byte code offset #0
/*      */     //   Java source line #1385	-> byte code offset #5
/*      */     //   Java source line #1387	-> byte code offset #9
/*      */     //   Java source line #1388	-> byte code offset #13
/*      */     //   Java source line #1389	-> byte code offset #18
/*      */     //   Java source line #1390	-> byte code offset #22
/*      */     //   Java source line #1392	-> byte code offset #26
/*      */     //   Java source line #1393	-> byte code offset #30
/*      */     //   Java source line #1392	-> byte code offset #33
/*      */     //   Java source line #1394	-> byte code offset #40
/*      */     //   Java source line #1395	-> byte code offset #44
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	45	0	this	ThreadPoolExecutor
/*      */     //   4	31	1	localReentrantLock	ReentrantLock
/*      */     //   33	6	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	26	33	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public List<Runnable> shutdownNow()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 17	java/util/concurrent/ThreadPoolExecutor:mainLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_2
/*      */     //   5: aload_2
/*      */     //   6: invokevirtual 18	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: invokespecial 110	java/util/concurrent/ThreadPoolExecutor:checkShutdownAccess	()V
/*      */     //   13: aload_0
/*      */     //   14: ldc 68
/*      */     //   16: invokespecial 111	java/util/concurrent/ThreadPoolExecutor:advanceRunState	(I)V
/*      */     //   19: aload_0
/*      */     //   20: invokespecial 114	java/util/concurrent/ThreadPoolExecutor:interruptWorkers	()V
/*      */     //   23: aload_0
/*      */     //   24: invokespecial 115	java/util/concurrent/ThreadPoolExecutor:drainQueue	()Ljava/util/List;
/*      */     //   27: astore_1
/*      */     //   28: aload_2
/*      */     //   29: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   32: goto +10 -> 42
/*      */     //   35: astore_3
/*      */     //   36: aload_2
/*      */     //   37: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   40: aload_3
/*      */     //   41: athrow
/*      */     //   42: aload_0
/*      */     //   43: invokevirtual 65	java/util/concurrent/ThreadPoolExecutor:tryTerminate	()V
/*      */     //   46: aload_1
/*      */     //   47: areturn
/*      */     // Line number table:
/*      */     //   Java source line #1416	-> byte code offset #0
/*      */     //   Java source line #1417	-> byte code offset #5
/*      */     //   Java source line #1419	-> byte code offset #9
/*      */     //   Java source line #1420	-> byte code offset #13
/*      */     //   Java source line #1421	-> byte code offset #19
/*      */     //   Java source line #1422	-> byte code offset #23
/*      */     //   Java source line #1424	-> byte code offset #28
/*      */     //   Java source line #1425	-> byte code offset #32
/*      */     //   Java source line #1424	-> byte code offset #35
/*      */     //   Java source line #1426	-> byte code offset #42
/*      */     //   Java source line #1427	-> byte code offset #46
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	48	0	this	ThreadPoolExecutor
/*      */     //   27	20	1	localList	List
/*      */     //   4	33	2	localReentrantLock	ReentrantLock
/*      */     //   35	6	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	28	35	finally
/*      */   }
/*      */   
/*      */   public boolean isShutdown()
/*      */   {
/* 1431 */     return !isRunning(this.ctl.get());
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
/*      */   public boolean isTerminating()
/*      */   {
/* 1446 */     int i = this.ctl.get();
/* 1447 */     return (!isRunning(i)) && (runStateLessThan(i, 1610612736));
/*      */   }
/*      */   
/*      */   public boolean isTerminated() {
/* 1451 */     return runStateAtLeast(this.ctl.get(), 1610612736);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_3
/*      */     //   1: lload_1
/*      */     //   2: invokevirtual 105	java/util/concurrent/TimeUnit:toNanos	(J)J
/*      */     //   5: lstore 4
/*      */     //   7: aload_0
/*      */     //   8: getfield 17	java/util/concurrent/ThreadPoolExecutor:mainLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   11: astore 6
/*      */     //   13: aload 6
/*      */     //   15: invokevirtual 18	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   18: aload_0
/*      */     //   19: getfield 4	java/util/concurrent/ThreadPoolExecutor:ctl	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   22: invokevirtual 6	java/util/concurrent/atomic/AtomicInteger:get	()I
/*      */     //   25: ldc 20
/*      */     //   27: invokestatic 8	java/util/concurrent/ThreadPoolExecutor:runStateAtLeast	(II)Z
/*      */     //   30: ifeq +14 -> 44
/*      */     //   33: iconst_1
/*      */     //   34: istore 7
/*      */     //   36: aload 6
/*      */     //   38: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   41: iload 7
/*      */     //   43: ireturn
/*      */     //   44: lload 4
/*      */     //   46: lconst_0
/*      */     //   47: lcmp
/*      */     //   48: ifgt +14 -> 62
/*      */     //   51: iconst_0
/*      */     //   52: istore 7
/*      */     //   54: aload 6
/*      */     //   56: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   59: iload 7
/*      */     //   61: ireturn
/*      */     //   62: aload_0
/*      */     //   63: getfield 22	java/util/concurrent/ThreadPoolExecutor:termination	Ljava/util/concurrent/locks/Condition;
/*      */     //   66: lload 4
/*      */     //   68: invokeinterface 116 3 0
/*      */     //   73: lstore 4
/*      */     //   75: goto -57 -> 18
/*      */     //   78: astore 8
/*      */     //   80: aload 6
/*      */     //   82: invokevirtual 24	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   85: aload 8
/*      */     //   87: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1456	-> byte code offset #0
/*      */     //   Java source line #1457	-> byte code offset #7
/*      */     //   Java source line #1458	-> byte code offset #13
/*      */     //   Java source line #1461	-> byte code offset #18
/*      */     //   Java source line #1462	-> byte code offset #33
/*      */     //   Java source line #1468	-> byte code offset #36
/*      */     //   Java source line #1463	-> byte code offset #44
/*      */     //   Java source line #1464	-> byte code offset #51
/*      */     //   Java source line #1468	-> byte code offset #54
/*      */     //   Java source line #1465	-> byte code offset #62
/*      */     //   Java source line #1468	-> byte code offset #78
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	88	0	this	ThreadPoolExecutor
/*      */     //   0	88	1	paramLong	long
/*      */     //   0	88	3	paramTimeUnit	TimeUnit
/*      */     //   5	69	4	l	long
/*      */     //   11	70	6	localReentrantLock	ReentrantLock
/*      */     //   34	26	7	bool	boolean
/*      */     //   78	8	8	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   18	36	78	finally
/*      */     //   44	54	78	finally
/*      */     //   62	80	78	finally
/*      */   }
/*      */   
/*      */   protected void finalize()
/*      */   {
/* 1477 */     shutdown();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThreadFactory(ThreadFactory paramThreadFactory)
/*      */   {
/* 1488 */     if (paramThreadFactory == null)
/* 1489 */       throw new NullPointerException();
/* 1490 */     this.threadFactory = paramThreadFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ThreadFactory getThreadFactory()
/*      */   {
/* 1500 */     return this.threadFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRejectedExecutionHandler(RejectedExecutionHandler paramRejectedExecutionHandler)
/*      */   {
/* 1511 */     if (paramRejectedExecutionHandler == null)
/* 1512 */       throw new NullPointerException();
/* 1513 */     this.handler = paramRejectedExecutionHandler;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public RejectedExecutionHandler getRejectedExecutionHandler()
/*      */   {
/* 1523 */     return this.handler;
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
/*      */   public void setCorePoolSize(int paramInt)
/*      */   {
/* 1538 */     if (paramInt < 0)
/* 1539 */       throw new IllegalArgumentException();
/* 1540 */     int i = paramInt - this.corePoolSize;
/* 1541 */     this.corePoolSize = paramInt;
/* 1542 */     if (workerCountOf(this.ctl.get()) > paramInt) {
/* 1543 */       interruptIdleWorkers();
/* 1544 */     } else if (i > 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1549 */       int j = Math.min(i, this.workQueue.size());
/* 1550 */       while ((j-- > 0) && (addWorker(null, true))) {
/* 1551 */         if (this.workQueue.isEmpty()) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getCorePoolSize()
/*      */   {
/* 1564 */     return this.corePoolSize;
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
/*      */   public boolean prestartCoreThread()
/*      */   {
/* 1577 */     return (workerCountOf(this.ctl.get()) < this.corePoolSize) && (addWorker(null, true));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void ensurePrestart()
/*      */   {
/* 1585 */     int i = workerCountOf(this.ctl.get());
/* 1586 */     if (i < this.corePoolSize) {
/* 1587 */       addWorker(null, true);
/* 1588 */     } else if (i == 0) {
/* 1589 */       addWorker(null, false);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int prestartAllCoreThreads()
/*      */   {
/* 1600 */     int i = 0;
/* 1601 */     while (addWorker(null, true))
/* 1602 */       i++;
/* 1603 */     return i;
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
/*      */   public boolean allowsCoreThreadTimeOut()
/*      */   {
/* 1620 */     return this.allowCoreThreadTimeOut;
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
/*      */   public void allowCoreThreadTimeOut(boolean paramBoolean)
/*      */   {
/* 1641 */     if ((paramBoolean) && (this.keepAliveTime <= 0L))
/* 1642 */       throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
/* 1643 */     if (paramBoolean != this.allowCoreThreadTimeOut) {
/* 1644 */       this.allowCoreThreadTimeOut = paramBoolean;
/* 1645 */       if (paramBoolean) {
/* 1646 */         interruptIdleWorkers();
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
/*      */   public void setMaximumPoolSize(int paramInt)
/*      */   {
/* 1663 */     if ((paramInt <= 0) || (paramInt < this.corePoolSize))
/* 1664 */       throw new IllegalArgumentException();
/* 1665 */     this.maximumPoolSize = paramInt;
/* 1666 */     if (workerCountOf(this.ctl.get()) > paramInt) {
/* 1667 */       interruptIdleWorkers();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaximumPoolSize()
/*      */   {
/* 1677 */     return this.maximumPoolSize;
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
/*      */   public void setKeepAliveTime(long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/* 1695 */     if (paramLong < 0L)
/* 1696 */       throw new IllegalArgumentException();
/* 1697 */     if ((paramLong == 0L) && (allowsCoreThreadTimeOut()))
/* 1698 */       throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
/* 1699 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 1700 */     long l2 = l1 - this.keepAliveTime;
/* 1701 */     this.keepAliveTime = l1;
/* 1702 */     if (l2 < 0L) {
/* 1703 */       interruptIdleWorkers();
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
/*      */   public long getKeepAliveTime(TimeUnit paramTimeUnit)
/*      */   {
/* 1716 */     return paramTimeUnit.convert(this.keepAliveTime, TimeUnit.NANOSECONDS);
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
/*      */   public BlockingQueue<Runnable> getQueue()
/*      */   {
/* 1730 */     return this.workQueue;
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
/*      */   public boolean remove(Runnable paramRunnable)
/*      */   {
/* 1750 */     boolean bool = this.workQueue.remove(paramRunnable);
/* 1751 */     tryTerminate();
/* 1752 */     return bool;
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
/*      */   public void purge()
/*      */   {
/* 1766 */     BlockingQueue localBlockingQueue = this.workQueue;
/*      */     Object localObject1;
/* 1768 */     int i; int j; try { Iterator localIterator = localBlockingQueue.iterator();
/* 1769 */       while (localIterator.hasNext()) {
/* 1770 */         localObject1 = (Runnable)localIterator.next();
/* 1771 */         if (((localObject1 instanceof Future)) && (((Future)localObject1).isCancelled())) {
/* 1772 */           localIterator.remove();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (ConcurrentModificationException localConcurrentModificationException)
/*      */     {
/* 1778 */       localObject1 = localBlockingQueue.toArray();i = localObject1.length;j = 0; } for (; j < i; j++) { Object localObject2 = localObject1[j];
/* 1779 */       if (((localObject2 instanceof Future)) && (((Future)localObject2).isCancelled())) {
/* 1780 */         localBlockingQueue.remove(localObject2);
/*      */       }
/*      */     }
/* 1783 */     tryTerminate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getPoolSize()
/*      */   {
/* 1794 */     ReentrantLock localReentrantLock = this.mainLock;
/* 1795 */     localReentrantLock.lock();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1800 */       return runStateAtLeast(this.ctl.get(), 1073741824) ? 0 : this.workers.size();
/*      */     } finally {
/* 1802 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getActiveCount()
/*      */   {
/* 1813 */     ReentrantLock localReentrantLock = this.mainLock;
/* 1814 */     localReentrantLock.lock();
/*      */     try {
/* 1816 */       Iterator localIterator1 = 0;
/* 1817 */       for (Worker localWorker : this.workers)
/* 1818 */         if (localWorker.isLocked())
/* 1819 */           localIterator1++;
/* 1820 */       return localIterator1;
/*      */     } finally {
/* 1822 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLargestPoolSize()
/*      */   {
/* 1833 */     ReentrantLock localReentrantLock = this.mainLock;
/* 1834 */     localReentrantLock.lock();
/*      */     try {
/* 1836 */       return this.largestPoolSize;
/*      */     } finally {
/* 1838 */       localReentrantLock.unlock();
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
/*      */   public long getTaskCount()
/*      */   {
/* 1851 */     ReentrantLock localReentrantLock = this.mainLock;
/* 1852 */     localReentrantLock.lock();
/*      */     try {
/* 1854 */       long l1 = this.completedTaskCount;
/* 1855 */       for (Worker localWorker : this.workers) {
/* 1856 */         l1 += localWorker.completedTasks;
/* 1857 */         if (localWorker.isLocked())
/* 1858 */           l1 += 1L;
/*      */       }
/* 1860 */       return l1 + this.workQueue.size();
/*      */     } finally {
/* 1862 */       localReentrantLock.unlock();
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
/*      */   public long getCompletedTaskCount()
/*      */   {
/* 1876 */     ReentrantLock localReentrantLock = this.mainLock;
/* 1877 */     localReentrantLock.lock();
/*      */     try {
/* 1879 */       long l1 = this.completedTaskCount;
/* 1880 */       for (Worker localWorker : this.workers)
/* 1881 */         l1 += localWorker.completedTasks;
/* 1882 */       return l1;
/*      */     } finally {
/* 1884 */       localReentrantLock.unlock();
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
/*      */   public String toString()
/*      */   {
/* 1898 */     ReentrantLock localReentrantLock = this.mainLock;
/* 1899 */     localReentrantLock.lock();
/*      */     long l;
/* 1901 */     int j; int i; try { l = this.completedTaskCount;
/* 1902 */       j = 0;
/* 1903 */       i = this.workers.size();
/* 1904 */       for (localIterator = this.workers.iterator(); localIterator.hasNext();) { localObject1 = (Worker)localIterator.next();
/* 1905 */         l += ((Worker)localObject1).completedTasks;
/* 1906 */         if (((Worker)localObject1).isLocked())
/* 1907 */           j++;
/*      */       }
/*      */     } finally { Iterator localIterator;
/* 1910 */       localReentrantLock.unlock();
/*      */     }
/* 1912 */     int k = this.ctl.get();
/*      */     
/* 1914 */     Object localObject1 = runStateAtLeast(k, 1610612736) ? "Terminated" : runStateLessThan(k, 0) ? "Running" : "Shutting down";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1920 */     return super.toString() + "[" + (String)localObject1 + ", pool size = " + i + ", active threads = " + j + ", queued tasks = " + this.workQueue.size() + ", completed tasks = " + l + "]";
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
/*      */   protected void beforeExecute(Thread paramThread, Runnable paramRunnable) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void terminated() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class CallerRunsPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor)
/*      */     {
/* 2021 */       if (!paramThreadPoolExecutor.isShutdown()) {
/* 2022 */         paramRunnable.run();
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
/*      */   public static class AbortPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor)
/*      */     {
/* 2047 */       throw new RejectedExecutionException("Task " + paramRunnable.toString() + " rejected from " + paramThreadPoolExecutor.toString());
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
/*      */   public static class DiscardPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor) {}
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
/*      */   public static class DiscardOldestPolicy
/*      */     implements RejectedExecutionHandler
/*      */   {
/*      */     public void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor)
/*      */     {
/* 2092 */       if (!paramThreadPoolExecutor.isShutdown()) {
/* 2093 */         paramThreadPoolExecutor.getQueue().poll();
/* 2094 */         paramThreadPoolExecutor.execute(paramRunnable);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ThreadPoolExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */