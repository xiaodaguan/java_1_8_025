/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.locks.LockSupport;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FutureTask<V>
/*     */   implements RunnableFuture<V>
/*     */ {
/*     */   private volatile int state;
/*     */   private static final int NEW = 0;
/*     */   private static final int COMPLETING = 1;
/*     */   private static final int NORMAL = 2;
/*     */   private static final int EXCEPTIONAL = 3;
/*     */   private static final int CANCELLED = 4;
/*     */   private static final int INTERRUPTING = 5;
/*     */   private static final int INTERRUPTED = 6;
/*     */   private Callable<V> callable;
/*     */   private Object outcome;
/*     */   private volatile Thread runner;
/*     */   private volatile WaitNode waiters;
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long stateOffset;
/*     */   private static final long runnerOffset;
/*     */   private static final long waitersOffset;
/*     */   
/*     */   private V report(int paramInt)
/*     */     throws ExecutionException
/*     */   {
/* 117 */     Object localObject = this.outcome;
/* 118 */     if (paramInt == 2)
/* 119 */       return (V)localObject;
/* 120 */     if (paramInt >= 4)
/* 121 */       throw new CancellationException();
/* 122 */     throw new ExecutionException((Throwable)localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FutureTask(Callable<V> paramCallable)
/*     */   {
/* 133 */     if (paramCallable == null)
/* 134 */       throw new NullPointerException();
/* 135 */     this.callable = paramCallable;
/* 136 */     this.state = 0;
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
/*     */   public FutureTask(Runnable paramRunnable, V paramV)
/*     */   {
/* 152 */     this.callable = Executors.callable(paramRunnable, paramV);
/* 153 */     this.state = 0;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 157 */     return this.state >= 4;
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/* 161 */     return this.state != 0;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean cancel(boolean paramBoolean)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 12	java/util/concurrent/FutureTask:state	I
/*     */     //   4: ifne +26 -> 30
/*     */     //   7: getstatic 14	java/util/concurrent/FutureTask:UNSAFE	Lsun/misc/Unsafe;
/*     */     //   10: aload_0
/*     */     //   11: getstatic 15	java/util/concurrent/FutureTask:stateOffset	J
/*     */     //   14: iconst_0
/*     */     //   15: iload_1
/*     */     //   16: ifeq +7 -> 23
/*     */     //   19: iconst_5
/*     */     //   20: goto +4 -> 24
/*     */     //   23: iconst_4
/*     */     //   24: invokevirtual 16	sun/misc/Unsafe:compareAndSwapInt	(Ljava/lang/Object;JII)Z
/*     */     //   27: ifne +5 -> 32
/*     */     //   30: iconst_0
/*     */     //   31: ireturn
/*     */     //   32: iload_1
/*     */     //   33: ifeq +46 -> 79
/*     */     //   36: aload_0
/*     */     //   37: getfield 17	java/util/concurrent/FutureTask:runner	Ljava/lang/Thread;
/*     */     //   40: astore_2
/*     */     //   41: aload_2
/*     */     //   42: ifnull +7 -> 49
/*     */     //   45: aload_2
/*     */     //   46: invokevirtual 18	java/lang/Thread:interrupt	()V
/*     */     //   49: getstatic 14	java/util/concurrent/FutureTask:UNSAFE	Lsun/misc/Unsafe;
/*     */     //   52: aload_0
/*     */     //   53: getstatic 15	java/util/concurrent/FutureTask:stateOffset	J
/*     */     //   56: bipush 6
/*     */     //   58: invokevirtual 19	sun/misc/Unsafe:putOrderedInt	(Ljava/lang/Object;JI)V
/*     */     //   61: goto +18 -> 79
/*     */     //   64: astore_3
/*     */     //   65: getstatic 14	java/util/concurrent/FutureTask:UNSAFE	Lsun/misc/Unsafe;
/*     */     //   68: aload_0
/*     */     //   69: getstatic 15	java/util/concurrent/FutureTask:stateOffset	J
/*     */     //   72: bipush 6
/*     */     //   74: invokevirtual 19	sun/misc/Unsafe:putOrderedInt	(Ljava/lang/Object;JI)V
/*     */     //   77: aload_3
/*     */     //   78: athrow
/*     */     //   79: aload_0
/*     */     //   80: invokespecial 20	java/util/concurrent/FutureTask:finishCompletion	()V
/*     */     //   83: goto +12 -> 95
/*     */     //   86: astore 4
/*     */     //   88: aload_0
/*     */     //   89: invokespecial 20	java/util/concurrent/FutureTask:finishCompletion	()V
/*     */     //   92: aload 4
/*     */     //   94: athrow
/*     */     //   95: iconst_1
/*     */     //   96: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #165	-> byte code offset #0
/*     */     //   Java source line #166	-> byte code offset #24
/*     */     //   Java source line #168	-> byte code offset #30
/*     */     //   Java source line #170	-> byte code offset #32
/*     */     //   Java source line #172	-> byte code offset #36
/*     */     //   Java source line #173	-> byte code offset #41
/*     */     //   Java source line #174	-> byte code offset #45
/*     */     //   Java source line #176	-> byte code offset #49
/*     */     //   Java source line #177	-> byte code offset #61
/*     */     //   Java source line #176	-> byte code offset #64
/*     */     //   Java source line #180	-> byte code offset #79
/*     */     //   Java source line #181	-> byte code offset #83
/*     */     //   Java source line #180	-> byte code offset #86
/*     */     //   Java source line #182	-> byte code offset #95
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	97	0	this	FutureTask
/*     */     //   0	97	1	paramBoolean	boolean
/*     */     //   40	6	2	localThread	Thread
/*     */     //   64	14	3	localObject1	Object
/*     */     //   86	7	4	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   36	49	64	finally
/*     */     //   32	79	86	finally
/*     */     //   86	88	86	finally
/*     */   }
/*     */   
/*     */   public V get()
/*     */     throws InterruptedException, ExecutionException
/*     */   {
/* 189 */     int i = this.state;
/* 190 */     if (i <= 1)
/* 191 */       i = awaitDone(false, 0L);
/* 192 */     return (V)report(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public V get(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 200 */     if (paramTimeUnit == null)
/* 201 */       throw new NullPointerException();
/* 202 */     int i = this.state;
/* 203 */     if ((i <= 1) && 
/* 204 */       ((i = awaitDone(true, paramTimeUnit.toNanos(paramLong))) <= 1))
/* 205 */       throw new TimeoutException();
/* 206 */     return (V)report(i);
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
/*     */   protected void done() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void set(V paramV)
/*     */   {
/* 230 */     if (UNSAFE.compareAndSwapInt(this, stateOffset, 0, 1)) {
/* 231 */       this.outcome = paramV;
/* 232 */       UNSAFE.putOrderedInt(this, stateOffset, 2);
/* 233 */       finishCompletion();
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
/*     */ 
/*     */   protected void setException(Throwable paramThrowable)
/*     */   {
/* 248 */     if (UNSAFE.compareAndSwapInt(this, stateOffset, 0, 1)) {
/* 249 */       this.outcome = paramThrowable;
/* 250 */       UNSAFE.putOrderedInt(this, stateOffset, 3);
/* 251 */       finishCompletion();
/*     */     }
/*     */   }
/*     */   
/*     */   public void run() {
/* 256 */     if ((this.state != 0) || 
/* 257 */       (!UNSAFE.compareAndSwapObject(this, runnerOffset, null, 
/* 258 */       Thread.currentThread())))
/* 259 */       return;
/*     */     try {
/* 261 */       Callable localCallable = this.callable;
/* 262 */       if ((localCallable != null) && (this.state == 0)) {
/*     */         Object localObject1;
/*     */         int j;
/*     */         try {
/* 266 */           localObject1 = localCallable.call();
/* 267 */           j = 1;
/*     */         } catch (Throwable localThrowable) {
/* 269 */           localObject1 = null;
/* 270 */           j = 0;
/* 271 */           setException(localThrowable);
/*     */         }
/* 273 */         if (j != 0) {
/* 274 */           set(localObject1);
/*     */         }
/*     */       }
/*     */     } finally {
/*     */       int i;
/* 279 */       this.runner = null;
/*     */       
/*     */ 
/* 282 */       int k = this.state;
/* 283 */       if (k >= 5) {
/* 284 */         handlePossibleCancellationInterrupt(k);
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
/*     */   protected boolean runAndReset()
/*     */   {
/* 298 */     if ((this.state != 0) || 
/* 299 */       (!UNSAFE.compareAndSwapObject(this, runnerOffset, null, 
/* 300 */       Thread.currentThread())))
/* 301 */       return false;
/* 302 */     int i = 0;
/* 303 */     int j = this.state;
/*     */     try {
/* 305 */       Callable localCallable = this.callable;
/* 306 */       if ((localCallable != null) && (j == 0)) {
/*     */         try {
/* 308 */           localCallable.call();
/* 309 */           i = 1;
/*     */         } catch (Throwable localThrowable) {
/* 311 */           setException(localThrowable);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 317 */       this.runner = null;
/*     */       
/*     */ 
/* 320 */       j = this.state;
/* 321 */       if (j >= 5)
/* 322 */         handlePossibleCancellationInterrupt(j);
/*     */     }
/* 324 */     return (i != 0) && (j == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void handlePossibleCancellationInterrupt(int paramInt)
/*     */   {
/* 334 */     if (paramInt == 5) {
/* 335 */       while (this.state == 5) {
/* 336 */         Thread.yield();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class WaitNode
/*     */   {
/* 357 */     volatile Thread thread = Thread.currentThread();
/*     */     
/*     */     volatile WaitNode next;
/*     */   }
/*     */   
/*     */ 
/*     */   private void finishCompletion()
/*     */   {
/*     */     Object localObject;
/* 366 */     while ((localObject = this.waiters) != null) {
/* 367 */       if (UNSAFE.compareAndSwapObject(this, waitersOffset, localObject, null)) {
/*     */         for (;;) {
/* 369 */           Thread localThread = ((WaitNode)localObject).thread;
/* 370 */           if (localThread != null) {
/* 371 */             ((WaitNode)localObject).thread = null;
/* 372 */             LockSupport.unpark(localThread);
/*     */           }
/* 374 */           WaitNode localWaitNode = ((WaitNode)localObject).next;
/* 375 */           if (localWaitNode == null)
/*     */             break;
/* 377 */           ((WaitNode)localObject).next = null;
/* 378 */           localObject = localWaitNode;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 384 */     done();
/*     */     
/* 386 */     this.callable = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int awaitDone(boolean paramBoolean, long paramLong)
/*     */     throws InterruptedException
/*     */   {
/* 398 */     long l = paramBoolean ? System.nanoTime() + paramLong : 0L;
/* 399 */     WaitNode localWaitNode = null;
/* 400 */     boolean bool = false;
/*     */     for (;;) {
/* 402 */       if (Thread.interrupted()) {
/* 403 */         removeWaiter(localWaitNode);
/* 404 */         throw new InterruptedException();
/*     */       }
/*     */       
/* 407 */       int i = this.state;
/* 408 */       if (i > 1) {
/* 409 */         if (localWaitNode != null)
/* 410 */           localWaitNode.thread = null;
/* 411 */         return i;
/*     */       }
/* 413 */       if (i == 1) {
/* 414 */         Thread.yield();
/* 415 */       } else if (localWaitNode == null) {
/* 416 */         localWaitNode = new WaitNode();
/* 417 */       } else if (!bool) {
/* 418 */         bool = UNSAFE.compareAndSwapObject(this, waitersOffset, localWaitNode.next = this.waiters, localWaitNode);
/*     */       }
/* 420 */       else if (paramBoolean) {
/* 421 */         paramLong = l - System.nanoTime();
/* 422 */         if (paramLong <= 0L) {
/* 423 */           removeWaiter(localWaitNode);
/* 424 */           return this.state;
/*     */         }
/* 426 */         LockSupport.parkNanos(this, paramLong);
/*     */       }
/*     */       else {
/* 429 */         LockSupport.park(this);
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
/*     */ 
/*     */   private void removeWaiter(WaitNode paramWaitNode)
/*     */   {
/* 444 */     if (paramWaitNode != null) {
/* 445 */       paramWaitNode.thread = null;
/*     */       
/*     */ 
/* 448 */       Object localObject1 = null; WaitNode localWaitNode; for (Object localObject2 = this.waiters;; localObject2 = localWaitNode) { if (localObject2 == null) return;
/* 449 */         localWaitNode = ((WaitNode)localObject2).next;
/* 450 */         if (((WaitNode)localObject2).thread != null) {
/* 451 */           localObject1 = localObject2;
/* 452 */         } else { if (localObject1 != null) {
/* 453 */             ((WaitNode)localObject1).next = localWaitNode;
/* 454 */             if (((WaitNode)localObject1).thread != null) continue;
/* 455 */             break;
/*     */           }
/* 457 */           if (!UNSAFE.compareAndSwapObject(this, waitersOffset, localObject2, localWaitNode)) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 473 */       UNSAFE = Unsafe.getUnsafe();
/* 474 */       Class localClass = FutureTask.class;
/*     */       
/* 476 */       stateOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("state"));
/*     */       
/* 478 */       runnerOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("runner"));
/*     */       
/* 480 */       waitersOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("waiters"));
/*     */     } catch (Exception localException) {
/* 482 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/FutureTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */