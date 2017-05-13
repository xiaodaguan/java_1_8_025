/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.concurrent.atomic.AtomicLong;
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
/*      */ public class ScheduledThreadPoolExecutor
/*      */   extends ThreadPoolExecutor
/*      */   implements ScheduledExecutorService
/*      */ {
/*      */   private volatile boolean continueExistingPeriodicTasksAfterShutdown;
/*  160 */   private volatile boolean executeExistingDelayedTasksAfterShutdown = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  165 */   private volatile boolean removeOnCancel = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  171 */   private static final AtomicLong sequencer = new AtomicLong();
/*      */   
/*      */ 
/*      */ 
/*      */   final long now()
/*      */   {
/*  177 */     return System.nanoTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private class ScheduledFutureTask<V>
/*      */     extends FutureTask<V>
/*      */     implements RunnableScheduledFuture<V>
/*      */   {
/*      */     private final long sequenceNumber;
/*      */     
/*      */ 
/*      */ 
/*      */     private long time;
/*      */     
/*      */ 
/*      */ 
/*      */     private final long period;
/*      */     
/*      */ 
/*  198 */     RunnableScheduledFuture<V> outerTask = this;
/*      */     
/*      */ 
/*      */ 
/*      */     int heapIndex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     ScheduledFutureTask(V paramV, long paramLong)
/*      */     {
/*  209 */       super(paramLong);
/*  210 */       Object localObject; this.time = localObject;
/*  211 */       this.period = 0L;
/*  212 */       this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     ScheduledFutureTask(V paramV, long paramLong1, long paramLong2)
/*      */     {
/*  219 */       super(paramLong1);
/*  220 */       this.time = ???;
/*  221 */       Object localObject; this.period = localObject;
/*  222 */       this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     ScheduledFutureTask(long paramLong)
/*      */     {
/*  229 */       super();
/*  230 */       Object localObject; this.time = localObject;
/*  231 */       this.period = 0L;
/*  232 */       this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
/*      */     }
/*      */     
/*      */     public long getDelay(TimeUnit paramTimeUnit) {
/*  236 */       return paramTimeUnit.convert(this.time - ScheduledThreadPoolExecutor.this.now(), TimeUnit.NANOSECONDS);
/*      */     }
/*      */     
/*      */     public int compareTo(Delayed paramDelayed) {
/*  240 */       if (paramDelayed == this)
/*  241 */         return 0;
/*  242 */       if ((paramDelayed instanceof ScheduledFutureTask)) {
/*  243 */         ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)paramDelayed;
/*  244 */         long l2 = this.time - localScheduledFutureTask.time;
/*  245 */         if (l2 < 0L)
/*  246 */           return -1;
/*  247 */         if (l2 > 0L)
/*  248 */           return 1;
/*  249 */         if (this.sequenceNumber < localScheduledFutureTask.sequenceNumber) {
/*  250 */           return -1;
/*      */         }
/*  252 */         return 1;
/*      */       }
/*  254 */       long l1 = getDelay(TimeUnit.NANOSECONDS) - paramDelayed.getDelay(TimeUnit.NANOSECONDS);
/*  255 */       return l1 > 0L ? 1 : l1 < 0L ? -1 : 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean isPeriodic()
/*      */     {
/*  264 */       return this.period != 0L;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void setNextRunTime()
/*      */     {
/*  271 */       long l = this.period;
/*  272 */       if (l > 0L) {
/*  273 */         this.time += l;
/*      */       } else
/*  275 */         this.time = ScheduledThreadPoolExecutor.this.triggerTime(-l);
/*      */     }
/*      */     
/*      */     public boolean cancel(boolean paramBoolean) {
/*  279 */       boolean bool = super.cancel(paramBoolean);
/*  280 */       if ((bool) && (ScheduledThreadPoolExecutor.this.removeOnCancel) && (this.heapIndex >= 0))
/*  281 */         ScheduledThreadPoolExecutor.this.remove(this);
/*  282 */       return bool;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void run()
/*      */     {
/*  289 */       boolean bool = isPeriodic();
/*  290 */       if (!ScheduledThreadPoolExecutor.this.canRunInCurrentRunState(bool)) {
/*  291 */         cancel(false);
/*  292 */       } else if (!bool) {
/*  293 */         super.run();
/*  294 */       } else if (super.runAndReset()) {
/*  295 */         setNextRunTime();
/*  296 */         ScheduledThreadPoolExecutor.this.reExecutePeriodic(this.outerTask);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean canRunInCurrentRunState(boolean paramBoolean)
/*      */   {
/*  308 */     return isRunningOrShutdown(paramBoolean ? this.continueExistingPeriodicTasksAfterShutdown : this.executeExistingDelayedTasksAfterShutdown);
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
/*      */   private void delayedExecute(RunnableScheduledFuture<?> paramRunnableScheduledFuture)
/*      */   {
/*  325 */     if (isShutdown()) {
/*  326 */       reject(paramRunnableScheduledFuture);
/*      */     } else {
/*  328 */       super.getQueue().add(paramRunnableScheduledFuture);
/*  329 */       if ((isShutdown()) && 
/*  330 */         (!canRunInCurrentRunState(paramRunnableScheduledFuture.isPeriodic())) && 
/*  331 */         (remove(paramRunnableScheduledFuture))) {
/*  332 */         paramRunnableScheduledFuture.cancel(false);
/*      */       } else {
/*  334 */         ensurePrestart();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void reExecutePeriodic(RunnableScheduledFuture<?> paramRunnableScheduledFuture)
/*      */   {
/*  345 */     if (canRunInCurrentRunState(true)) {
/*  346 */       super.getQueue().add(paramRunnableScheduledFuture);
/*  347 */       if ((!canRunInCurrentRunState(true)) && (remove(paramRunnableScheduledFuture))) {
/*  348 */         paramRunnableScheduledFuture.cancel(false);
/*      */       } else {
/*  350 */         ensurePrestart();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void onShutdown()
/*      */   {
/*  359 */     BlockingQueue localBlockingQueue = super.getQueue();
/*      */     
/*  361 */     boolean bool1 = getExecuteExistingDelayedTasksAfterShutdownPolicy();
/*      */     
/*  363 */     boolean bool2 = getContinueExistingPeriodicTasksAfterShutdownPolicy();
/*  364 */     Object localObject; if ((!bool1) && (!bool2)) {
/*  365 */       for (localObject : localBlockingQueue.toArray())
/*  366 */         if ((localObject instanceof RunnableScheduledFuture))
/*  367 */           ((RunnableScheduledFuture)localObject).cancel(false);
/*  368 */       localBlockingQueue.clear();
/*      */     }
/*      */     else
/*      */     {
/*  372 */       for (localObject : localBlockingQueue.toArray()) {
/*  373 */         if ((localObject instanceof RunnableScheduledFuture)) {
/*  374 */           RunnableScheduledFuture localRunnableScheduledFuture = (RunnableScheduledFuture)localObject;
/*      */           
/*  376 */           if (localRunnableScheduledFuture.isPeriodic() ? bool2 : bool1) {
/*  377 */             if (!localRunnableScheduledFuture.isCancelled()) {}
/*  378 */           } else if (localBlockingQueue.remove(localRunnableScheduledFuture)) {
/*  379 */             localRunnableScheduledFuture.cancel(false);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  384 */     tryTerminate();
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
/*      */   protected <V> RunnableScheduledFuture<V> decorateTask(Runnable paramRunnable, RunnableScheduledFuture<V> paramRunnableScheduledFuture)
/*      */   {
/*  401 */     return paramRunnableScheduledFuture;
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
/*      */   protected <V> RunnableScheduledFuture<V> decorateTask(Callable<V> paramCallable, RunnableScheduledFuture<V> paramRunnableScheduledFuture)
/*      */   {
/*  418 */     return paramRunnableScheduledFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ScheduledThreadPoolExecutor(int paramInt)
/*      */   {
/*  430 */     super(paramInt, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue());
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
/*      */   public ScheduledThreadPoolExecutor(int paramInt, ThreadFactory paramThreadFactory)
/*      */   {
/*  447 */     super(paramInt, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), paramThreadFactory);
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
/*      */   public ScheduledThreadPoolExecutor(int paramInt, RejectedExecutionHandler paramRejectedExecutionHandler)
/*      */   {
/*  464 */     super(paramInt, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), paramRejectedExecutionHandler);
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
/*      */   public ScheduledThreadPoolExecutor(int paramInt, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler)
/*      */   {
/*  485 */     super(paramInt, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), paramThreadFactory, paramRejectedExecutionHandler);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long triggerTime(long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/*  493 */     return triggerTime(paramTimeUnit.toNanos(paramLong < 0L ? 0L : paramLong));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   long triggerTime(long paramLong)
/*      */   {
/*  501 */     return now() + (paramLong < 4611686018427387903L ? paramLong : overflowFree(paramLong));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long overflowFree(long paramLong)
/*      */   {
/*  512 */     Delayed localDelayed = (Delayed)super.getQueue().peek();
/*  513 */     if (localDelayed != null) {
/*  514 */       long l = localDelayed.getDelay(TimeUnit.NANOSECONDS);
/*  515 */       if ((l < 0L) && (paramLong - l < 0L))
/*  516 */         paramLong = Long.MAX_VALUE + l;
/*      */     }
/*  518 */     return paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/*  528 */     if ((paramRunnable == null) || (paramTimeUnit == null))
/*  529 */       throw new NullPointerException();
/*  530 */     RunnableScheduledFuture localRunnableScheduledFuture = decorateTask(paramRunnable, new ScheduledFutureTask(paramRunnable, null, 
/*      */     
/*  532 */       triggerTime(paramLong, paramTimeUnit)));
/*  533 */     delayedExecute(localRunnableScheduledFuture);
/*  534 */     return localRunnableScheduledFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/*  544 */     if ((paramCallable == null) || (paramTimeUnit == null))
/*  545 */       throw new NullPointerException();
/*  546 */     RunnableScheduledFuture localRunnableScheduledFuture = decorateTask(paramCallable, new ScheduledFutureTask(paramCallable, 
/*      */     
/*  548 */       triggerTime(paramLong, paramTimeUnit)));
/*  549 */     delayedExecute(localRunnableScheduledFuture);
/*  550 */     return localRunnableScheduledFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*      */   {
/*  562 */     if ((paramRunnable == null) || (paramTimeUnit == null))
/*  563 */       throw new NullPointerException();
/*  564 */     if (paramLong2 <= 0L) {
/*  565 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  570 */     ScheduledFutureTask localScheduledFutureTask = new ScheduledFutureTask(paramRunnable, null, triggerTime(paramLong1, paramTimeUnit), paramTimeUnit.toNanos(paramLong2));
/*  571 */     RunnableScheduledFuture localRunnableScheduledFuture = decorateTask(paramRunnable, localScheduledFutureTask);
/*  572 */     localScheduledFutureTask.outerTask = localRunnableScheduledFuture;
/*  573 */     delayedExecute(localRunnableScheduledFuture);
/*  574 */     return localRunnableScheduledFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*      */   {
/*  586 */     if ((paramRunnable == null) || (paramTimeUnit == null))
/*  587 */       throw new NullPointerException();
/*  588 */     if (paramLong2 <= 0L) {
/*  589 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  594 */     ScheduledFutureTask localScheduledFutureTask = new ScheduledFutureTask(paramRunnable, null, triggerTime(paramLong1, paramTimeUnit), paramTimeUnit.toNanos(-paramLong2));
/*  595 */     RunnableScheduledFuture localRunnableScheduledFuture = decorateTask(paramRunnable, localScheduledFutureTask);
/*  596 */     localScheduledFutureTask.outerTask = localRunnableScheduledFuture;
/*  597 */     delayedExecute(localRunnableScheduledFuture);
/*  598 */     return localRunnableScheduledFuture;
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
/*      */   public void execute(Runnable paramRunnable)
/*      */   {
/*  622 */     schedule(paramRunnable, 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Future<?> submit(Runnable paramRunnable)
/*      */   {
/*  632 */     return schedule(paramRunnable, 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Future<T> submit(Runnable paramRunnable, T paramT)
/*      */   {
/*  640 */     return schedule(Executors.callable(paramRunnable, paramT), 0L, TimeUnit.NANOSECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Future<T> submit(Callable<T> paramCallable)
/*      */   {
/*  648 */     return schedule(paramCallable, 0L, TimeUnit.NANOSECONDS);
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
/*      */   public void setContinueExistingPeriodicTasksAfterShutdownPolicy(boolean paramBoolean)
/*      */   {
/*  663 */     this.continueExistingPeriodicTasksAfterShutdown = paramBoolean;
/*  664 */     if ((!paramBoolean) && (isShutdown())) {
/*  665 */       onShutdown();
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
/*      */   public boolean getContinueExistingPeriodicTasksAfterShutdownPolicy()
/*      */   {
/*  680 */     return this.continueExistingPeriodicTasksAfterShutdown;
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
/*      */   public void setExecuteExistingDelayedTasksAfterShutdownPolicy(boolean paramBoolean)
/*      */   {
/*  695 */     this.executeExistingDelayedTasksAfterShutdown = paramBoolean;
/*  696 */     if ((!paramBoolean) && (isShutdown())) {
/*  697 */       onShutdown();
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
/*      */   public boolean getExecuteExistingDelayedTasksAfterShutdownPolicy()
/*      */   {
/*  712 */     return this.executeExistingDelayedTasksAfterShutdown;
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
/*      */   public void setRemoveOnCancelPolicy(boolean paramBoolean)
/*      */   {
/*  725 */     this.removeOnCancel = paramBoolean;
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
/*      */   public boolean getRemoveOnCancelPolicy()
/*      */   {
/*  739 */     return this.removeOnCancel;
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
/*      */   public void shutdown()
/*      */   {
/*  761 */     super.shutdown();
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
/*      */   public List<Runnable> shutdownNow()
/*      */   {
/*  786 */     return super.shutdownNow();
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
/*      */   public BlockingQueue<Runnable> getQueue()
/*      */   {
/*  801 */     return super.getQueue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static class DelayedWorkQueue
/*      */     extends AbstractQueue<Runnable>
/*      */     implements BlockingQueue<Runnable>
/*      */   {
/*      */     private static final int INITIAL_CAPACITY = 16;
/*      */     
/*      */ 
/*      */ 
/*      */     private RunnableScheduledFuture<?>[] queue;
/*      */     
/*      */ 
/*      */ 
/*      */     private final ReentrantLock lock;
/*      */     
/*      */ 
/*      */ 
/*      */     private int size;
/*      */     
/*      */ 
/*      */ 
/*      */     private Thread leader;
/*      */     
/*      */ 
/*      */     private final Condition available;
/*      */     
/*      */ 
/*      */ 
/*      */     DelayedWorkQueue()
/*      */     {
/*  836 */       this.queue = new RunnableScheduledFuture[16];
/*      */       
/*  838 */       this.lock = new ReentrantLock();
/*  839 */       this.size = 0;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  857 */       this.leader = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  863 */       this.available = this.lock.newCondition();
/*      */     }
/*      */     
/*      */ 
/*      */     private void setIndex(RunnableScheduledFuture<?> paramRunnableScheduledFuture, int paramInt)
/*      */     {
/*  869 */       if ((paramRunnableScheduledFuture instanceof ScheduledThreadPoolExecutor.ScheduledFutureTask)) {
/*  870 */         ((ScheduledThreadPoolExecutor.ScheduledFutureTask)paramRunnableScheduledFuture).heapIndex = paramInt;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void siftUp(int paramInt, RunnableScheduledFuture<?> paramRunnableScheduledFuture)
/*      */     {
/*  878 */       while (paramInt > 0) {
/*  879 */         int i = paramInt - 1 >>> 1;
/*  880 */         RunnableScheduledFuture localRunnableScheduledFuture = this.queue[i];
/*  881 */         if (paramRunnableScheduledFuture.compareTo(localRunnableScheduledFuture) >= 0)
/*      */           break;
/*  883 */         this.queue[paramInt] = localRunnableScheduledFuture;
/*  884 */         setIndex(localRunnableScheduledFuture, paramInt);
/*  885 */         paramInt = i;
/*      */       }
/*  887 */       this.queue[paramInt] = paramRunnableScheduledFuture;
/*  888 */       setIndex(paramRunnableScheduledFuture, paramInt);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private void siftDown(int paramInt, RunnableScheduledFuture<?> paramRunnableScheduledFuture)
/*      */     {
/*  896 */       int i = this.size >>> 1;
/*  897 */       while (paramInt < i) {
/*  898 */         int j = (paramInt << 1) + 1;
/*  899 */         RunnableScheduledFuture localRunnableScheduledFuture = this.queue[j];
/*  900 */         int k = j + 1;
/*  901 */         if ((k < this.size) && (localRunnableScheduledFuture.compareTo(this.queue[k]) > 0))
/*  902 */           localRunnableScheduledFuture = this.queue[(j = k)];
/*  903 */         if (paramRunnableScheduledFuture.compareTo(localRunnableScheduledFuture) <= 0)
/*      */           break;
/*  905 */         this.queue[paramInt] = localRunnableScheduledFuture;
/*  906 */         setIndex(localRunnableScheduledFuture, paramInt);
/*  907 */         paramInt = j;
/*      */       }
/*  909 */       this.queue[paramInt] = paramRunnableScheduledFuture;
/*  910 */       setIndex(paramRunnableScheduledFuture, paramInt);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void grow()
/*      */     {
/*  917 */       int i = this.queue.length;
/*  918 */       int j = i + (i >> 1);
/*  919 */       if (j < 0)
/*  920 */         j = Integer.MAX_VALUE;
/*  921 */       this.queue = ((RunnableScheduledFuture[])Arrays.copyOf(this.queue, j));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private int indexOf(Object paramObject)
/*      */     {
/*  928 */       if (paramObject != null) { int i;
/*  929 */         if ((paramObject instanceof ScheduledThreadPoolExecutor.ScheduledFutureTask)) {
/*  930 */           i = ((ScheduledThreadPoolExecutor.ScheduledFutureTask)paramObject).heapIndex;
/*      */           
/*      */ 
/*  933 */           if ((i >= 0) && (i < this.size) && (this.queue[i] == paramObject))
/*  934 */             return i;
/*      */         } else {
/*  936 */           for (i = 0; i < this.size; i++)
/*  937 */             if (paramObject.equals(this.queue[i]))
/*  938 */               return i;
/*      */         }
/*      */       }
/*  941 */       return -1;
/*      */     }
/*      */     
/*      */     public boolean contains(Object paramObject) {
/*  945 */       ReentrantLock localReentrantLock = this.lock;
/*  946 */       localReentrantLock.lock();
/*      */       try {
/*  948 */         return indexOf(paramObject) != -1;
/*      */       } finally {
/*  950 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/*  955 */       ReentrantLock localReentrantLock = this.lock;
/*  956 */       localReentrantLock.lock();
/*      */       try {
/*  958 */         int i = indexOf(paramObject);
/*  959 */         if (i < 0) {
/*  960 */           return false;
/*      */         }
/*  962 */         setIndex(this.queue[i], -1);
/*  963 */         int j = --this.size;
/*  964 */         RunnableScheduledFuture localRunnableScheduledFuture = this.queue[j];
/*  965 */         this.queue[j] = null;
/*  966 */         if (j != i) {
/*  967 */           siftDown(i, localRunnableScheduledFuture);
/*  968 */           if (this.queue[i] == localRunnableScheduledFuture)
/*  969 */             siftUp(i, localRunnableScheduledFuture);
/*      */         }
/*  971 */         return true;
/*      */       } finally {
/*  973 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public int size() {
/*  978 */       ReentrantLock localReentrantLock = this.lock;
/*  979 */       localReentrantLock.lock();
/*      */       try {
/*  981 */         return this.size;
/*      */       } finally {
/*  983 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  988 */       return size() == 0;
/*      */     }
/*      */     
/*      */     public int remainingCapacity() {
/*  992 */       return Integer.MAX_VALUE;
/*      */     }
/*      */     
/*      */     public RunnableScheduledFuture<?> peek() {
/*  996 */       ReentrantLock localReentrantLock = this.lock;
/*  997 */       localReentrantLock.lock();
/*      */       try {
/*  999 */         return this.queue[0];
/*      */       } finally {
/* 1001 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean offer(Runnable paramRunnable) {
/* 1006 */       if (paramRunnable == null)
/* 1007 */         throw new NullPointerException();
/* 1008 */       RunnableScheduledFuture localRunnableScheduledFuture = (RunnableScheduledFuture)paramRunnable;
/* 1009 */       ReentrantLock localReentrantLock = this.lock;
/* 1010 */       localReentrantLock.lock();
/*      */       try {
/* 1012 */         int i = this.size;
/* 1013 */         if (i >= this.queue.length)
/* 1014 */           grow();
/* 1015 */         this.size = (i + 1);
/* 1016 */         if (i == 0) {
/* 1017 */           this.queue[0] = localRunnableScheduledFuture;
/* 1018 */           setIndex(localRunnableScheduledFuture, 0);
/*      */         } else {
/* 1020 */           siftUp(i, localRunnableScheduledFuture);
/*      */         }
/* 1022 */         if (this.queue[0] == localRunnableScheduledFuture) {
/* 1023 */           this.leader = null;
/* 1024 */           this.available.signal();
/*      */         }
/*      */       } finally {
/* 1027 */         localReentrantLock.unlock();
/*      */       }
/* 1029 */       return true;
/*      */     }
/*      */     
/*      */     public void put(Runnable paramRunnable) {
/* 1033 */       offer(paramRunnable);
/*      */     }
/*      */     
/*      */     public boolean add(Runnable paramRunnable) {
/* 1037 */       return offer(paramRunnable);
/*      */     }
/*      */     
/*      */     public boolean offer(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 1041 */       return offer(paramRunnable);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private RunnableScheduledFuture<?> finishPoll(RunnableScheduledFuture<?> paramRunnableScheduledFuture)
/*      */     {
/* 1051 */       int i = --this.size;
/* 1052 */       RunnableScheduledFuture localRunnableScheduledFuture = this.queue[i];
/* 1053 */       this.queue[i] = null;
/* 1054 */       if (i != 0)
/* 1055 */         siftDown(0, localRunnableScheduledFuture);
/* 1056 */       setIndex(paramRunnableScheduledFuture, -1);
/* 1057 */       return paramRunnableScheduledFuture;
/*      */     }
/*      */     
/*      */     public RunnableScheduledFuture<?> poll() {
/* 1061 */       ReentrantLock localReentrantLock = this.lock;
/* 1062 */       localReentrantLock.lock();
/*      */       try {
/* 1064 */         RunnableScheduledFuture localRunnableScheduledFuture = this.queue[0];
/* 1065 */         Object localObject1; if ((localRunnableScheduledFuture == null) || (localRunnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L)) {
/* 1066 */           return null;
/*      */         }
/* 1068 */         return finishPoll(localRunnableScheduledFuture);
/*      */       } finally {
/* 1070 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     public RunnableScheduledFuture<?> take()
/*      */       throws java.lang.InterruptedException
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield 7	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   4: astore_1
/*      */       //   5: aload_1
/*      */       //   6: invokevirtual 35	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*      */       //   9: aload_0
/*      */       //   10: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   13: iconst_0
/*      */       //   14: aaload
/*      */       //   15: astore_2
/*      */       //   16: aload_2
/*      */       //   17: ifnonnull +15 -> 32
/*      */       //   20: aload_0
/*      */       //   21: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   24: invokeinterface 36 1 0
/*      */       //   29: goto +137 -> 166
/*      */       //   32: aload_2
/*      */       //   33: getstatic 32	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
/*      */       //   36: invokeinterface 33 2 0
/*      */       //   41: lstore_3
/*      */       //   42: lload_3
/*      */       //   43: lconst_0
/*      */       //   44: lcmp
/*      */       //   45: ifgt +42 -> 87
/*      */       //   48: aload_0
/*      */       //   49: aload_2
/*      */       //   50: invokespecial 34	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:finishPoll	(Ljava/util/concurrent/RunnableScheduledFuture;)Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   53: astore 5
/*      */       //   55: aload_0
/*      */       //   56: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   59: ifnonnull +21 -> 80
/*      */       //   62: aload_0
/*      */       //   63: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   66: iconst_0
/*      */       //   67: aaload
/*      */       //   68: ifnull +12 -> 80
/*      */       //   71: aload_0
/*      */       //   72: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   75: invokeinterface 30 1 0
/*      */       //   80: aload_1
/*      */       //   81: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   84: aload 5
/*      */       //   86: areturn
/*      */       //   87: aconst_null
/*      */       //   88: astore_2
/*      */       //   89: aload_0
/*      */       //   90: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   93: ifnull +15 -> 108
/*      */       //   96: aload_0
/*      */       //   97: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   100: invokeinterface 36 1 0
/*      */       //   105: goto +61 -> 166
/*      */       //   108: invokestatic 37	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*      */       //   111: astore 5
/*      */       //   113: aload_0
/*      */       //   114: aload 5
/*      */       //   116: putfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   119: aload_0
/*      */       //   120: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   123: lload_3
/*      */       //   124: invokeinterface 38 3 0
/*      */       //   129: pop2
/*      */       //   130: aload_0
/*      */       //   131: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   134: aload 5
/*      */       //   136: if_acmpne +30 -> 166
/*      */       //   139: aload_0
/*      */       //   140: aconst_null
/*      */       //   141: putfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   144: goto +22 -> 166
/*      */       //   147: astore 6
/*      */       //   149: aload_0
/*      */       //   150: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   153: aload 5
/*      */       //   155: if_acmpne +8 -> 163
/*      */       //   158: aload_0
/*      */       //   159: aconst_null
/*      */       //   160: putfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   163: aload 6
/*      */       //   165: athrow
/*      */       //   166: goto -157 -> 9
/*      */       //   169: astore 7
/*      */       //   171: aload_0
/*      */       //   172: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   175: ifnonnull +21 -> 196
/*      */       //   178: aload_0
/*      */       //   179: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   182: iconst_0
/*      */       //   183: aaload
/*      */       //   184: ifnull +12 -> 196
/*      */       //   187: aload_0
/*      */       //   188: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   191: invokeinterface 30 1 0
/*      */       //   196: aload_1
/*      */       //   197: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   200: aload 7
/*      */       //   202: athrow
/*      */       // Line number table:
/*      */       //   Java source line #1075	-> byte code offset #0
/*      */       //   Java source line #1076	-> byte code offset #5
/*      */       //   Java source line #1079	-> byte code offset #9
/*      */       //   Java source line #1080	-> byte code offset #16
/*      */       //   Java source line #1081	-> byte code offset #20
/*      */       //   Java source line #1083	-> byte code offset #32
/*      */       //   Java source line #1084	-> byte code offset #42
/*      */       //   Java source line #1085	-> byte code offset #48
/*      */       //   Java source line #1102	-> byte code offset #55
/*      */       //   Java source line #1103	-> byte code offset #71
/*      */       //   Java source line #1104	-> byte code offset #80
/*      */       //   Java source line #1086	-> byte code offset #87
/*      */       //   Java source line #1087	-> byte code offset #89
/*      */       //   Java source line #1088	-> byte code offset #96
/*      */       //   Java source line #1090	-> byte code offset #108
/*      */       //   Java source line #1091	-> byte code offset #113
/*      */       //   Java source line #1093	-> byte code offset #119
/*      */       //   Java source line #1095	-> byte code offset #130
/*      */       //   Java source line #1096	-> byte code offset #139
/*      */       //   Java source line #1095	-> byte code offset #147
/*      */       //   Java source line #1096	-> byte code offset #158
/*      */       //   Java source line #1100	-> byte code offset #166
/*      */       //   Java source line #1102	-> byte code offset #169
/*      */       //   Java source line #1103	-> byte code offset #187
/*      */       //   Java source line #1104	-> byte code offset #196
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	203	0	this	DelayedWorkQueue
/*      */       //   4	193	1	localReentrantLock	ReentrantLock
/*      */       //   15	74	2	localRunnableScheduledFuture	RunnableScheduledFuture
/*      */       //   41	83	3	l	long
/*      */       //   53	101	5	localObject1	Object
/*      */       //   147	17	6	localObject2	Object
/*      */       //   169	32	7	localObject3	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   119	130	147	finally
/*      */       //   147	149	147	finally
/*      */       //   9	55	169	finally
/*      */       //   87	171	169	finally
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     public RunnableScheduledFuture<?> poll(long paramLong, TimeUnit paramTimeUnit)
/*      */       throws java.lang.InterruptedException
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_3
/*      */       //   1: lload_1
/*      */       //   2: invokevirtual 39	java/util/concurrent/TimeUnit:toNanos	(J)J
/*      */       //   5: lstore 4
/*      */       //   7: aload_0
/*      */       //   8: getfield 7	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   11: astore 6
/*      */       //   13: aload 6
/*      */       //   15: invokevirtual 35	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*      */       //   18: aload_0
/*      */       //   19: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   22: iconst_0
/*      */       //   23: aaload
/*      */       //   24: astore 7
/*      */       //   26: aload 7
/*      */       //   28: ifnonnull +62 -> 90
/*      */       //   31: lload 4
/*      */       //   33: lconst_0
/*      */       //   34: lcmp
/*      */       //   35: ifgt +39 -> 74
/*      */       //   38: aconst_null
/*      */       //   39: astore 8
/*      */       //   41: aload_0
/*      */       //   42: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   45: ifnonnull +21 -> 66
/*      */       //   48: aload_0
/*      */       //   49: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   52: iconst_0
/*      */       //   53: aaload
/*      */       //   54: ifnull +12 -> 66
/*      */       //   57: aload_0
/*      */       //   58: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   61: invokeinterface 30 1 0
/*      */       //   66: aload 6
/*      */       //   68: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   71: aload 8
/*      */       //   73: areturn
/*      */       //   74: aload_0
/*      */       //   75: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   78: lload 4
/*      */       //   80: invokeinterface 38 3 0
/*      */       //   85: lstore 4
/*      */       //   87: goto +210 -> 297
/*      */       //   90: aload 7
/*      */       //   92: getstatic 32	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
/*      */       //   95: invokeinterface 33 2 0
/*      */       //   100: lstore 8
/*      */       //   102: lload 8
/*      */       //   104: lconst_0
/*      */       //   105: lcmp
/*      */       //   106: ifgt +44 -> 150
/*      */       //   109: aload_0
/*      */       //   110: aload 7
/*      */       //   112: invokespecial 34	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:finishPoll	(Ljava/util/concurrent/RunnableScheduledFuture;)Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   115: astore 10
/*      */       //   117: aload_0
/*      */       //   118: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   121: ifnonnull +21 -> 142
/*      */       //   124: aload_0
/*      */       //   125: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   128: iconst_0
/*      */       //   129: aaload
/*      */       //   130: ifnull +12 -> 142
/*      */       //   133: aload_0
/*      */       //   134: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   137: invokeinterface 30 1 0
/*      */       //   142: aload 6
/*      */       //   144: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   147: aload 10
/*      */       //   149: areturn
/*      */       //   150: lload 4
/*      */       //   152: lconst_0
/*      */       //   153: lcmp
/*      */       //   154: ifgt +39 -> 193
/*      */       //   157: aconst_null
/*      */       //   158: astore 10
/*      */       //   160: aload_0
/*      */       //   161: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   164: ifnonnull +21 -> 185
/*      */       //   167: aload_0
/*      */       //   168: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   171: iconst_0
/*      */       //   172: aaload
/*      */       //   173: ifnull +12 -> 185
/*      */       //   176: aload_0
/*      */       //   177: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   180: invokeinterface 30 1 0
/*      */       //   185: aload 6
/*      */       //   187: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   190: aload 10
/*      */       //   192: areturn
/*      */       //   193: aconst_null
/*      */       //   194: astore 7
/*      */       //   196: lload 4
/*      */       //   198: lload 8
/*      */       //   200: lcmp
/*      */       //   201: iflt +10 -> 211
/*      */       //   204: aload_0
/*      */       //   205: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   208: ifnull +19 -> 227
/*      */       //   211: aload_0
/*      */       //   212: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   215: lload 4
/*      */       //   217: invokeinterface 38 3 0
/*      */       //   222: lstore 4
/*      */       //   224: goto +73 -> 297
/*      */       //   227: invokestatic 37	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*      */       //   230: astore 10
/*      */       //   232: aload_0
/*      */       //   233: aload 10
/*      */       //   235: putfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   238: aload_0
/*      */       //   239: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   242: lload 8
/*      */       //   244: invokeinterface 38 3 0
/*      */       //   249: lstore 11
/*      */       //   251: lload 4
/*      */       //   253: lload 8
/*      */       //   255: lload 11
/*      */       //   257: lsub
/*      */       //   258: lsub
/*      */       //   259: lstore 4
/*      */       //   261: aload_0
/*      */       //   262: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   265: aload 10
/*      */       //   267: if_acmpne +30 -> 297
/*      */       //   270: aload_0
/*      */       //   271: aconst_null
/*      */       //   272: putfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   275: goto +22 -> 297
/*      */       //   278: astore 13
/*      */       //   280: aload_0
/*      */       //   281: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   284: aload 10
/*      */       //   286: if_acmpne +8 -> 294
/*      */       //   289: aload_0
/*      */       //   290: aconst_null
/*      */       //   291: putfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   294: aload 13
/*      */       //   296: athrow
/*      */       //   297: goto -279 -> 18
/*      */       //   300: astore 14
/*      */       //   302: aload_0
/*      */       //   303: getfield 9	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:leader	Ljava/lang/Thread;
/*      */       //   306: ifnonnull +21 -> 327
/*      */       //   309: aload_0
/*      */       //   310: getfield 4	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:queue	[Ljava/util/concurrent/RunnableScheduledFuture;
/*      */       //   313: iconst_0
/*      */       //   314: aaload
/*      */       //   315: ifnull +12 -> 327
/*      */       //   318: aload_0
/*      */       //   319: getfield 11	java/util/concurrent/ScheduledThreadPoolExecutor$DelayedWorkQueue:available	Ljava/util/concurrent/locks/Condition;
/*      */       //   322: invokeinterface 30 1 0
/*      */       //   327: aload 6
/*      */       //   329: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   332: aload 14
/*      */       //   334: athrow
/*      */       // Line number table:
/*      */       //   Java source line #1110	-> byte code offset #0
/*      */       //   Java source line #1111	-> byte code offset #7
/*      */       //   Java source line #1112	-> byte code offset #13
/*      */       //   Java source line #1115	-> byte code offset #18
/*      */       //   Java source line #1116	-> byte code offset #26
/*      */       //   Java source line #1117	-> byte code offset #31
/*      */       //   Java source line #1118	-> byte code offset #38
/*      */       //   Java source line #1144	-> byte code offset #41
/*      */       //   Java source line #1145	-> byte code offset #57
/*      */       //   Java source line #1146	-> byte code offset #66
/*      */       //   Java source line #1120	-> byte code offset #74
/*      */       //   Java source line #1122	-> byte code offset #90
/*      */       //   Java source line #1123	-> byte code offset #102
/*      */       //   Java source line #1124	-> byte code offset #109
/*      */       //   Java source line #1144	-> byte code offset #117
/*      */       //   Java source line #1145	-> byte code offset #133
/*      */       //   Java source line #1146	-> byte code offset #142
/*      */       //   Java source line #1125	-> byte code offset #150
/*      */       //   Java source line #1126	-> byte code offset #157
/*      */       //   Java source line #1144	-> byte code offset #160
/*      */       //   Java source line #1145	-> byte code offset #176
/*      */       //   Java source line #1146	-> byte code offset #185
/*      */       //   Java source line #1127	-> byte code offset #193
/*      */       //   Java source line #1128	-> byte code offset #196
/*      */       //   Java source line #1129	-> byte code offset #211
/*      */       //   Java source line #1131	-> byte code offset #227
/*      */       //   Java source line #1132	-> byte code offset #232
/*      */       //   Java source line #1134	-> byte code offset #238
/*      */       //   Java source line #1135	-> byte code offset #251
/*      */       //   Java source line #1137	-> byte code offset #261
/*      */       //   Java source line #1138	-> byte code offset #270
/*      */       //   Java source line #1137	-> byte code offset #278
/*      */       //   Java source line #1138	-> byte code offset #289
/*      */       //   Java source line #1142	-> byte code offset #297
/*      */       //   Java source line #1144	-> byte code offset #300
/*      */       //   Java source line #1145	-> byte code offset #318
/*      */       //   Java source line #1146	-> byte code offset #327
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	335	0	this	DelayedWorkQueue
/*      */       //   0	335	1	paramLong	long
/*      */       //   0	335	3	paramTimeUnit	TimeUnit
/*      */       //   5	255	4	l1	long
/*      */       //   11	317	6	localReentrantLock	ReentrantLock
/*      */       //   24	171	7	localRunnableScheduledFuture	RunnableScheduledFuture
/*      */       //   39	33	8	localRunnableScheduledFuture1	RunnableScheduledFuture<?>
/*      */       //   100	154	8	l2	long
/*      */       //   115	170	10	localObject1	Object
/*      */       //   249	7	11	l3	long
/*      */       //   278	17	13	localObject2	Object
/*      */       //   300	33	14	localObject3	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   238	261	278	finally
/*      */       //   278	280	278	finally
/*      */       //   18	41	300	finally
/*      */       //   74	117	300	finally
/*      */       //   150	160	300	finally
/*      */       //   193	302	300	finally
/*      */     }
/*      */     
/*      */     public void clear()
/*      */     {
/* 1151 */       ReentrantLock localReentrantLock = this.lock;
/* 1152 */       localReentrantLock.lock();
/*      */       try {
/* 1154 */         for (int i = 0; i < this.size; i++) {
/* 1155 */           RunnableScheduledFuture localRunnableScheduledFuture = this.queue[i];
/* 1156 */           if (localRunnableScheduledFuture != null) {
/* 1157 */             this.queue[i] = null;
/* 1158 */             setIndex(localRunnableScheduledFuture, -1);
/*      */           }
/*      */         }
/* 1161 */         this.size = 0;
/*      */       } finally {
/* 1163 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private RunnableScheduledFuture<?> peekExpired()
/*      */     {
/* 1173 */       RunnableScheduledFuture localRunnableScheduledFuture = this.queue[0];
/* 1174 */       return (localRunnableScheduledFuture == null) || (localRunnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) ? null : localRunnableScheduledFuture;
/*      */     }
/*      */     
/*      */     public int drainTo(Collection<? super Runnable> paramCollection)
/*      */     {
/* 1179 */       if (paramCollection == null)
/* 1180 */         throw new NullPointerException();
/* 1181 */       if (paramCollection == this)
/* 1182 */         throw new IllegalArgumentException();
/* 1183 */       ReentrantLock localReentrantLock = this.lock;
/* 1184 */       localReentrantLock.lock();
/*      */       try
/*      */       {
/* 1187 */         int i = 0;
/* 1188 */         RunnableScheduledFuture localRunnableScheduledFuture; while ((localRunnableScheduledFuture = peekExpired()) != null) {
/* 1189 */           paramCollection.add(localRunnableScheduledFuture);
/* 1190 */           finishPoll(localRunnableScheduledFuture);
/* 1191 */           i++;
/*      */         }
/* 1193 */         return i;
/*      */       } finally {
/* 1195 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public int drainTo(Collection<? super Runnable> paramCollection, int paramInt) {
/* 1200 */       if (paramCollection == null)
/* 1201 */         throw new NullPointerException();
/* 1202 */       if (paramCollection == this)
/* 1203 */         throw new IllegalArgumentException();
/* 1204 */       if (paramInt <= 0)
/* 1205 */         return 0;
/* 1206 */       ReentrantLock localReentrantLock = this.lock;
/* 1207 */       localReentrantLock.lock();
/*      */       try
/*      */       {
/* 1210 */         int i = 0;
/* 1211 */         RunnableScheduledFuture localRunnableScheduledFuture; while ((i < paramInt) && ((localRunnableScheduledFuture = peekExpired()) != null)) {
/* 1212 */           paramCollection.add(localRunnableScheduledFuture);
/* 1213 */           finishPoll(localRunnableScheduledFuture);
/* 1214 */           i++;
/*      */         }
/* 1216 */         return i;
/*      */       } finally {
/* 1218 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 1223 */       ReentrantLock localReentrantLock = this.lock;
/* 1224 */       localReentrantLock.lock();
/*      */       try {
/* 1226 */         return Arrays.copyOf(this.queue, this.size, Object[].class);
/*      */       } finally {
/* 1228 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/* 1234 */       ReentrantLock localReentrantLock = this.lock;
/* 1235 */       localReentrantLock.lock();
/*      */       try { Object localObject1;
/* 1237 */         if (paramArrayOfT.length < this.size)
/* 1238 */           return (Object[])Arrays.copyOf(this.queue, this.size, paramArrayOfT.getClass());
/* 1239 */         System.arraycopy(this.queue, 0, paramArrayOfT, 0, this.size);
/* 1240 */         if (paramArrayOfT.length > this.size)
/* 1241 */           paramArrayOfT[this.size] = null;
/* 1242 */         return paramArrayOfT;
/*      */       } finally {
/* 1244 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public Iterator<Runnable> iterator() {
/* 1249 */       return new Itr((RunnableScheduledFuture[])Arrays.copyOf(this.queue, this.size));
/*      */     }
/*      */     
/*      */ 
/*      */     private class Itr
/*      */       implements Iterator<Runnable>
/*      */     {
/*      */       final RunnableScheduledFuture<?>[] array;
/* 1257 */       int cursor = 0;
/* 1258 */       int lastRet = -1;
/*      */       
/*      */       Itr() { RunnableScheduledFuture[] arrayOfRunnableScheduledFuture;
/* 1261 */         this.array = arrayOfRunnableScheduledFuture;
/*      */       }
/*      */       
/*      */       public boolean hasNext() {
/* 1265 */         return this.cursor < this.array.length;
/*      */       }
/*      */       
/*      */       public Runnable next() {
/* 1269 */         if (this.cursor >= this.array.length)
/* 1270 */           throw new NoSuchElementException();
/* 1271 */         this.lastRet = this.cursor;
/* 1272 */         return this.array[(this.cursor++)];
/*      */       }
/*      */       
/*      */       public void remove() {
/* 1276 */         if (this.lastRet < 0)
/* 1277 */           throw new IllegalStateException();
/* 1278 */         ScheduledThreadPoolExecutor.DelayedWorkQueue.this.remove(this.array[this.lastRet]);
/* 1279 */         this.lastRet = -1;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ScheduledThreadPoolExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */