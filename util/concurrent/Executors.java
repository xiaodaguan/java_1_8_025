/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Executors
/*     */ {
/*     */   public static ExecutorService newFixedThreadPool(int paramInt)
/*     */   {
/*  89 */     return new ThreadPoolExecutor(paramInt, paramInt, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
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
/*     */   public static ExecutorService newWorkStealingPool(int paramInt)
/*     */   {
/* 110 */     return new ForkJoinPool(paramInt, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
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
/*     */   public static ExecutorService newWorkStealingPool()
/*     */   {
/* 126 */     return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
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
/*     */   public static ExecutorService newFixedThreadPool(int paramInt, ThreadFactory paramThreadFactory)
/*     */   {
/* 151 */     return new ThreadPoolExecutor(paramInt, paramInt, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), paramThreadFactory);
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
/*     */   public static ExecutorService newSingleThreadExecutor()
/*     */   {
/* 171 */     return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()));
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
/*     */   public static ExecutorService newSingleThreadExecutor(ThreadFactory paramThreadFactory)
/*     */   {
/* 192 */     return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), paramThreadFactory));
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
/*     */   public static ExecutorService newCachedThreadPool()
/*     */   {
/* 216 */     return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue());
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
/*     */   public static ExecutorService newCachedThreadPool(ThreadFactory paramThreadFactory)
/*     */   {
/* 231 */     return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), paramThreadFactory);
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
/*     */   public static ScheduledExecutorService newSingleThreadScheduledExecutor()
/*     */   {
/* 251 */     return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1));
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
/*     */   public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory paramThreadFactory)
/*     */   {
/* 272 */     return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1, paramThreadFactory));
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
/*     */   public static ScheduledExecutorService newScheduledThreadPool(int paramInt)
/*     */   {
/* 285 */     return new ScheduledThreadPoolExecutor(paramInt);
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
/*     */   public static ScheduledExecutorService newScheduledThreadPool(int paramInt, ThreadFactory paramThreadFactory)
/*     */   {
/* 301 */     return new ScheduledThreadPoolExecutor(paramInt, paramThreadFactory);
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
/*     */   public static ExecutorService unconfigurableExecutorService(ExecutorService paramExecutorService)
/*     */   {
/* 315 */     if (paramExecutorService == null)
/* 316 */       throw new NullPointerException();
/* 317 */     return new DelegatedExecutorService(paramExecutorService);
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
/*     */   public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService paramScheduledExecutorService)
/*     */   {
/* 331 */     if (paramScheduledExecutorService == null)
/* 332 */       throw new NullPointerException();
/* 333 */     return new DelegatedScheduledExecutorService(paramScheduledExecutorService);
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
/*     */   public static ThreadFactory defaultThreadFactory()
/*     */   {
/* 353 */     return new DefaultThreadFactory();
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
/*     */ 
/*     */ 
/*     */   public static ThreadFactory privilegedThreadFactory()
/*     */   {
/* 390 */     return new PrivilegedThreadFactory();
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
/*     */   public static <T> Callable<T> callable(Runnable paramRunnable, T paramT)
/*     */   {
/* 405 */     if (paramRunnable == null)
/* 406 */       throw new NullPointerException();
/* 407 */     return new RunnableAdapter(paramRunnable, paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Callable<Object> callable(Runnable paramRunnable)
/*     */   {
/* 418 */     if (paramRunnable == null)
/* 419 */       throw new NullPointerException();
/* 420 */     return new RunnableAdapter(paramRunnable, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Callable<Object> callable(PrivilegedAction<?> paramPrivilegedAction)
/*     */   {
/* 431 */     if (paramPrivilegedAction == null)
/* 432 */       throw new NullPointerException();
/* 433 */     new Callable() {
/* 434 */       public Object call() { return this.val$action.run(); }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Callable<Object> callable(PrivilegedExceptionAction<?> paramPrivilegedExceptionAction)
/*     */   {
/* 446 */     if (paramPrivilegedExceptionAction == null)
/* 447 */       throw new NullPointerException();
/* 448 */     new Callable() {
/* 449 */       public Object call() throws Exception { return this.val$action.run(); }
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
/*     */   public static <T> Callable<T> privilegedCallable(Callable<T> paramCallable)
/*     */   {
/* 467 */     if (paramCallable == null)
/* 468 */       throw new NullPointerException();
/* 469 */     return new PrivilegedCallable(paramCallable);
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
/*     */   public static <T> Callable<T> privilegedCallableUsingCurrentClassLoader(Callable<T> paramCallable)
/*     */   {
/* 493 */     if (paramCallable == null)
/* 494 */       throw new NullPointerException();
/* 495 */     return new PrivilegedCallableUsingCurrentClassLoader(paramCallable);
/*     */   }
/*     */   
/*     */ 
/*     */   static final class RunnableAdapter<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     final Runnable task;
/*     */     final T result;
/*     */     
/*     */     RunnableAdapter(Runnable paramRunnable, T paramT)
/*     */     {
/* 507 */       this.task = paramRunnable;
/* 508 */       this.result = paramT;
/*     */     }
/*     */     
/* 511 */     public T call() { this.task.run();
/* 512 */       return (T)this.result;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class PrivilegedCallable<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     private final Callable<T> task;
/*     */     private final AccessControlContext acc;
/*     */     
/*     */     PrivilegedCallable(Callable<T> paramCallable)
/*     */     {
/* 524 */       this.task = paramCallable;
/* 525 */       this.acc = AccessController.getContext();
/*     */     }
/*     */     
/*     */     public T call() throws Exception {
/*     */       try {
/* 530 */         (T)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */ 
/* 533 */           public T run() throws Exception { return (T)Executors.PrivilegedCallable.this.task.call(); } }, this.acc);
/*     */       }
/*     */       catch (PrivilegedActionException localPrivilegedActionException)
/*     */       {
/* 537 */         throw localPrivilegedActionException.getException();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static final class PrivilegedCallableUsingCurrentClassLoader<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     private final Callable<T> task;
/*     */     private final AccessControlContext acc;
/*     */     private final ClassLoader ccl;
/*     */     
/*     */     PrivilegedCallableUsingCurrentClassLoader(Callable<T> paramCallable)
/*     */     {
/* 552 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 553 */       if (localSecurityManager != null)
/*     */       {
/*     */ 
/*     */ 
/* 557 */         localSecurityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */         
/*     */ 
/*     */ 
/* 561 */         localSecurityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*     */       }
/* 563 */       this.task = paramCallable;
/* 564 */       this.acc = AccessController.getContext();
/* 565 */       this.ccl = Thread.currentThread().getContextClassLoader();
/*     */     }
/*     */     
/*     */     public T call() throws Exception {
/*     */       try {
/* 570 */         (T)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public T run() throws Exception {
/* 573 */             Thread localThread = Thread.currentThread();
/* 574 */             ClassLoader localClassLoader = localThread.getContextClassLoader();
/* 575 */             if (Executors.PrivilegedCallableUsingCurrentClassLoader.this.ccl == localClassLoader) {
/* 576 */               return (T)Executors.PrivilegedCallableUsingCurrentClassLoader.this.task.call();
/*     */             }
/* 578 */             localThread.setContextClassLoader(Executors.PrivilegedCallableUsingCurrentClassLoader.this.ccl);
/*     */             try {
/* 580 */               return (T)Executors.PrivilegedCallableUsingCurrentClassLoader.this.task.call();
/*     */             } finally {
/* 582 */               localThread.setContextClassLoader(localClassLoader); } } }, this.acc);
/*     */ 
/*     */       }
/*     */       catch (PrivilegedActionException localPrivilegedActionException)
/*     */       {
/*     */ 
/* 588 */         throw localPrivilegedActionException.getException();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static class DefaultThreadFactory
/*     */     implements ThreadFactory
/*     */   {
/* 597 */     private static final AtomicInteger poolNumber = new AtomicInteger(1);
/*     */     private final ThreadGroup group;
/* 599 */     private final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */     private final String namePrefix;
/*     */     
/*     */     DefaultThreadFactory() {
/* 603 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*     */       
/* 605 */       this.group = (localSecurityManager != null ? localSecurityManager.getThreadGroup() : Thread.currentThread().getThreadGroup());
/*     */       
/* 607 */       this.namePrefix = ("pool-" + poolNumber.getAndIncrement() + "-thread-");
/*     */     }
/*     */     
/*     */ 
/*     */     public Thread newThread(Runnable paramRunnable)
/*     */     {
/* 613 */       Thread localThread = new Thread(this.group, paramRunnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
/*     */       
/* 615 */       if (localThread.isDaemon())
/* 616 */         localThread.setDaemon(false);
/* 617 */       if (localThread.getPriority() != 5)
/* 618 */         localThread.setPriority(5);
/* 619 */       return localThread;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static class PrivilegedThreadFactory
/*     */     extends Executors.DefaultThreadFactory
/*     */   {
/*     */     private final AccessControlContext acc;
/*     */     private final ClassLoader ccl;
/*     */     
/*     */     PrivilegedThreadFactory()
/*     */     {
/* 632 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 633 */       if (localSecurityManager != null)
/*     */       {
/*     */ 
/*     */ 
/* 637 */         localSecurityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */         
/*     */ 
/* 640 */         localSecurityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
/*     */       }
/* 642 */       this.acc = AccessController.getContext();
/* 643 */       this.ccl = Thread.currentThread().getContextClassLoader();
/*     */     }
/*     */     
/*     */     public Thread newThread(final Runnable paramRunnable) {
/* 647 */       super.newThread(new Runnable() {
/*     */         public void run() {
/* 649 */           AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Void run() {
/* 651 */               Thread.currentThread().setContextClassLoader(Executors.PrivilegedThreadFactory.this.ccl);
/* 652 */               Executors.PrivilegedThreadFactory.1.this.val$r.run();
/* 653 */               return null;
/*     */             }
/* 655 */           }, Executors.PrivilegedThreadFactory.this.acc);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static class DelegatedExecutorService
/*     */     extends AbstractExecutorService
/*     */   {
/*     */     private final ExecutorService e;
/*     */     
/* 667 */     DelegatedExecutorService(ExecutorService paramExecutorService) { this.e = paramExecutorService; }
/* 668 */     public void execute(Runnable paramRunnable) { this.e.execute(paramRunnable); }
/* 669 */     public void shutdown() { this.e.shutdown(); }
/* 670 */     public List<Runnable> shutdownNow() { return this.e.shutdownNow(); }
/* 671 */     public boolean isShutdown() { return this.e.isShutdown(); }
/* 672 */     public boolean isTerminated() { return this.e.isTerminated(); }
/*     */     
/*     */     public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 675 */       return this.e.awaitTermination(paramLong, paramTimeUnit);
/*     */     }
/*     */     
/* 678 */     public Future<?> submit(Runnable paramRunnable) { return this.e.submit(paramRunnable); }
/*     */     
/*     */     public <T> Future<T> submit(Callable<T> paramCallable) {
/* 681 */       return this.e.submit(paramCallable);
/*     */     }
/*     */     
/* 684 */     public <T> Future<T> submit(Runnable paramRunnable, T paramT) { return this.e.submit(paramRunnable, paramT); }
/*     */     
/*     */     public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection) throws InterruptedException
/*     */     {
/* 688 */       return this.e.invokeAll(paramCollection);
/*     */     }
/*     */     
/*     */     public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
/*     */     {
/* 693 */       return this.e.invokeAll(paramCollection, paramLong, paramTimeUnit);
/*     */     }
/*     */     
/*     */     public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection) throws InterruptedException, ExecutionException {
/* 697 */       return (T)this.e.invokeAny(paramCollection);
/*     */     }
/*     */     
/*     */     public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException
/*     */     {
/* 702 */       return (T)this.e.invokeAny(paramCollection, paramLong, paramTimeUnit);
/*     */     }
/*     */   }
/*     */   
/*     */   static class FinalizableDelegatedExecutorService extends Executors.DelegatedExecutorService
/*     */   {
/*     */     FinalizableDelegatedExecutorService(ExecutorService paramExecutorService) {
/* 709 */       super();
/*     */     }
/*     */     
/* 712 */     protected void finalize() { super.shutdown(); }
/*     */   }
/*     */   
/*     */ 
/*     */   static class DelegatedScheduledExecutorService
/*     */     extends Executors.DelegatedExecutorService
/*     */     implements ScheduledExecutorService
/*     */   {
/*     */     private final ScheduledExecutorService e;
/*     */     
/*     */ 
/*     */     DelegatedScheduledExecutorService(ScheduledExecutorService paramScheduledExecutorService)
/*     */     {
/* 725 */       super();
/* 726 */       this.e = paramScheduledExecutorService;
/*     */     }
/*     */     
/* 729 */     public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) { return this.e.schedule(paramRunnable, paramLong, paramTimeUnit); }
/*     */     
/*     */     public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit) {
/* 732 */       return this.e.schedule(paramCallable, paramLong, paramTimeUnit);
/*     */     }
/*     */     
/* 735 */     public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) { return this.e.scheduleAtFixedRate(paramRunnable, paramLong1, paramLong2, paramTimeUnit); }
/*     */     
/*     */     public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 738 */       return this.e.scheduleWithFixedDelay(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/Executors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */