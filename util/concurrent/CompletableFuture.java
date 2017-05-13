/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompletableFuture<T>
/*      */   implements Future<T>, CompletionStage<T>
/*      */ {
/*      */   static final class AltResult
/*      */   {
/*      */     final Throwable ex;
/*      */     
/*  163 */     AltResult(Throwable paramThrowable) { this.ex = paramThrowable; }
/*      */   }
/*      */   
/*  166 */   static final AltResult NIL = new AltResult(null);
/*      */   
/*      */ 
/*      */   volatile Object result;
/*      */   
/*      */ 
/*      */   volatile WaitNode waiters;
/*      */   
/*      */   volatile CompletionNode completions;
/*      */   
/*      */ 
/*      */   final void postComplete()
/*      */   {
/*      */     WaitNode localWaitNode;
/*      */     
/*  181 */     while ((localWaitNode = this.waiters) != null) { Thread localThread;
/*  182 */       if ((UNSAFE.compareAndSwapObject(this, WAITERS, localWaitNode, localWaitNode.next)) && ((localThread = localWaitNode.thread) != null))
/*      */       {
/*  184 */         localWaitNode.thread = null;
/*  185 */         LockSupport.unpark(localThread);
/*      */       }
/*      */     }
/*      */     
/*      */     CompletionNode localCompletionNode;
/*  190 */     while ((localCompletionNode = this.completions) != null) { Completion localCompletion;
/*  191 */       if ((UNSAFE.compareAndSwapObject(this, COMPLETIONS, localCompletionNode, localCompletionNode.next)) && ((localCompletion = localCompletionNode.completion) != null))
/*      */       {
/*  193 */         localCompletion.run();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void internalComplete(T paramT, Throwable paramThrowable)
/*      */   {
/*  204 */     if (this.result == null)
/*      */     {
/*  206 */       UNSAFE.compareAndSwapObject(this, RESULT, null, paramThrowable == null ? paramT : paramT == null ? NIL : new AltResult((paramThrowable instanceof CompletionException) ? paramThrowable : new CompletionException(paramThrowable)));
/*      */     }
/*      */     
/*      */ 
/*  210 */     postComplete();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void helpPostComplete()
/*      */   {
/*  217 */     if (this.result != null) {
/*  218 */       postComplete();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  224 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  230 */   static final int SPINS = NCPU > 1 ? 256 : 0;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long RESULT;
/*      */   private static final long WAITERS;
/*      */   private static final long COMPLETIONS;
/*      */   
/*      */   static final class WaitNode implements ForkJoinPool.ManagedBlocker
/*      */   {
/*      */     long nanos;
/*      */     final long deadline;
/*      */     volatile int interruptControl;
/*      */     volatile Thread thread;
/*      */     volatile WaitNode next;
/*      */     
/*      */     WaitNode(boolean paramBoolean, long paramLong1, long paramLong2)
/*      */     {
/*  246 */       this.thread = Thread.currentThread();
/*  247 */       this.interruptControl = (paramBoolean ? 1 : 0);
/*  248 */       this.nanos = paramLong1;
/*  249 */       this.deadline = paramLong2;
/*      */     }
/*      */     
/*  252 */     public boolean isReleasable() { if (this.thread == null)
/*  253 */         return true;
/*  254 */       if (Thread.interrupted()) {
/*  255 */         int i = this.interruptControl;
/*  256 */         this.interruptControl = -1;
/*  257 */         if (i > 0)
/*  258 */           return true;
/*      */       }
/*  260 */       if ((this.deadline != 0L) && ((this.nanos <= 0L) || 
/*  261 */         ((this.nanos = this.deadline - System.nanoTime()) <= 0L))) {
/*  262 */         this.thread = null;
/*  263 */         return true;
/*      */       }
/*  265 */       return false;
/*      */     }
/*      */     
/*  268 */     public boolean block() { if (isReleasable())
/*  269 */         return true;
/*  270 */       if (this.deadline == 0L) {
/*  271 */         LockSupport.park(this);
/*  272 */       } else if (this.nanos > 0L)
/*  273 */         LockSupport.parkNanos(this, this.nanos);
/*  274 */       return isReleasable();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object waitingGet(boolean paramBoolean)
/*      */   {
/*  283 */     WaitNode localWaitNode = null;
/*  284 */     boolean bool = false;
/*  285 */     int i = SPINS;
/*      */     for (;;) { Object localObject;
/*  287 */       if ((localObject = this.result) != null) {
/*  288 */         if (localWaitNode != null) {
/*  289 */           localWaitNode.thread = null;
/*  290 */           if (localWaitNode.interruptControl < 0) {
/*  291 */             if (paramBoolean) {
/*  292 */               removeWaiter(localWaitNode);
/*  293 */               return null;
/*      */             }
/*  295 */             Thread.currentThread().interrupt();
/*      */           }
/*      */         }
/*  298 */         postComplete();
/*  299 */         return localObject;
/*      */       }
/*  301 */       if (i > 0) {
/*  302 */         int j = ThreadLocalRandom.nextSecondarySeed();
/*  303 */         if (j == 0)
/*  304 */           j = ThreadLocalRandom.current().nextInt();
/*  305 */         if (j >= 0) {
/*  306 */           i--;
/*      */         }
/*  308 */       } else if (localWaitNode == null) {
/*  309 */         localWaitNode = new WaitNode(paramBoolean, 0L, 0L);
/*  310 */       } else if (!bool) {
/*  311 */         bool = UNSAFE.compareAndSwapObject(this, WAITERS, localWaitNode.next = this.waiters, localWaitNode);
/*      */       } else {
/*  313 */         if ((paramBoolean) && (localWaitNode.interruptControl < 0)) {
/*  314 */           removeWaiter(localWaitNode);
/*  315 */           return null;
/*      */         }
/*  317 */         if ((localWaitNode.thread != null) && (this.result == null)) {
/*      */           try {
/*  319 */             ForkJoinPool.managedBlock(localWaitNode);
/*      */           } catch (InterruptedException localInterruptedException) {
/*  321 */             localWaitNode.interruptControl = -1;
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
/*      */   private Object timedAwaitDone(long paramLong)
/*      */     throws InterruptedException, TimeoutException
/*      */   {
/*  335 */     WaitNode localWaitNode = null;
/*  336 */     boolean bool = false;
/*      */     for (;;) { Object localObject;
/*  338 */       if ((localObject = this.result) != null) {
/*  339 */         if (localWaitNode != null) {
/*  340 */           localWaitNode.thread = null;
/*  341 */           if (localWaitNode.interruptControl < 0) {
/*  342 */             removeWaiter(localWaitNode);
/*  343 */             throw new InterruptedException();
/*      */           }
/*      */         }
/*  346 */         postComplete();
/*  347 */         return localObject;
/*      */       }
/*  349 */       if (localWaitNode == null) {
/*  350 */         if (paramLong <= 0L)
/*  351 */           throw new TimeoutException();
/*  352 */         long l = System.nanoTime() + paramLong;
/*  353 */         localWaitNode = new WaitNode(true, paramLong, l == 0L ? 1L : l);
/*      */       }
/*  355 */       else if (!bool) {
/*  356 */         bool = UNSAFE.compareAndSwapObject(this, WAITERS, localWaitNode.next = this.waiters, localWaitNode);
/*      */       } else {
/*  358 */         if (localWaitNode.interruptControl < 0) {
/*  359 */           removeWaiter(localWaitNode);
/*  360 */           throw new InterruptedException();
/*      */         }
/*  362 */         if (localWaitNode.nanos <= 0L) {
/*  363 */           if (this.result == null) {
/*  364 */             removeWaiter(localWaitNode);
/*  365 */             throw new TimeoutException();
/*      */           }
/*      */         }
/*  368 */         else if ((localWaitNode.thread != null) && (this.result == null)) {
/*      */           try {
/*  370 */             ForkJoinPool.managedBlock(localWaitNode);
/*      */           } catch (InterruptedException localInterruptedException) {
/*  372 */             localWaitNode.interruptControl = -1;
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
/*      */   private void removeWaiter(WaitNode paramWaitNode)
/*      */   {
/*  389 */     if (paramWaitNode != null) {
/*  390 */       paramWaitNode.thread = null;
/*      */       
/*      */ 
/*  393 */       Object localObject1 = null; WaitNode localWaitNode; for (Object localObject2 = this.waiters;; localObject2 = localWaitNode) { if (localObject2 == null) return;
/*  394 */         localWaitNode = ((WaitNode)localObject2).next;
/*  395 */         if (((WaitNode)localObject2).thread != null) {
/*  396 */           localObject1 = localObject2;
/*  397 */         } else { if (localObject1 != null) {
/*  398 */             ((WaitNode)localObject1).next = localWaitNode;
/*  399 */             if (((WaitNode)localObject1).thread != null) continue;
/*  400 */             break;
/*      */           }
/*  402 */           if (!UNSAFE.compareAndSwapObject(this, WAITERS, localObject2, localWaitNode)) {
/*      */             break;
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
/*      */   public static abstract interface AsynchronousCompletionTask {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static abstract class Async
/*      */     extends ForkJoinTask<Void>
/*      */     implements Runnable, CompletableFuture.AsynchronousCompletionTask
/*      */   {
/*  426 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/*  428 */     public final void run() { exec(); }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static void execAsync(Executor paramExecutor, Async paramAsync)
/*      */   {
/*  437 */     if ((paramExecutor == ForkJoinPool.commonPool()) && 
/*  438 */       (ForkJoinPool.getCommonPoolParallelism() <= 1)) {
/*  439 */       new Thread(paramAsync).start();
/*      */     } else
/*  441 */       paramExecutor.execute(paramAsync);
/*      */   }
/*      */   
/*      */   static final class AsyncRun extends CompletableFuture.Async { final Runnable fn;
/*      */     final CompletableFuture<Void> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*  448 */     AsyncRun(Runnable paramRunnable, CompletableFuture<Void> paramCompletableFuture) { this.fn = paramRunnable;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  452 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) {
/*      */         Object localObject;
/*  454 */         try { this.fn.run();
/*  455 */           localObject = null;
/*      */         } catch (Throwable localThrowable) {
/*  457 */           localObject = localThrowable;
/*      */         }
/*  459 */         localCompletableFuture.internalComplete(null, (Throwable)localObject);
/*      */       }
/*  461 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncSupply<U> extends CompletableFuture.Async {
/*      */     final Supplier<U> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*  470 */     AsyncSupply(Supplier<U> paramSupplier, CompletableFuture<U> paramCompletableFuture) { this.fn = paramSupplier;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  474 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) { Object localObject1;
/*      */         Object localObject2;
/*  476 */         try { localObject1 = this.fn.get();
/*  477 */           localObject2 = null;
/*      */         } catch (Throwable localThrowable) {
/*  479 */           localObject2 = localThrowable;
/*  480 */           localObject1 = null;
/*      */         }
/*  482 */         localCompletableFuture.internalComplete(localObject1, (Throwable)localObject2);
/*      */       }
/*  484 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncApply<T, U> extends CompletableFuture.Async {
/*      */     final T arg;
/*      */     final Function<? super T, ? extends U> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AsyncApply(T paramT, Function<? super T, ? extends U> paramFunction, CompletableFuture<U> paramCompletableFuture) {
/*  495 */       this.arg = paramT;this.fn = paramFunction;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  499 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) { Object localObject1;
/*      */         Object localObject2;
/*  501 */         try { localObject1 = this.fn.apply(this.arg);
/*  502 */           localObject2 = null;
/*      */         } catch (Throwable localThrowable) {
/*  504 */           localObject2 = localThrowable;
/*  505 */           localObject1 = null;
/*      */         }
/*  507 */         localCompletableFuture.internalComplete(localObject1, (Throwable)localObject2);
/*      */       }
/*  509 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncCombine<T, U, V> extends CompletableFuture.Async
/*      */   {
/*      */     final T arg1;
/*      */     final U arg2;
/*      */     final BiFunction<? super T, ? super U, ? extends V> fn;
/*      */     final CompletableFuture<V> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AsyncCombine(T paramT, U paramU, BiFunction<? super T, ? super U, ? extends V> paramBiFunction, CompletableFuture<V> paramCompletableFuture) {
/*  522 */       this.arg1 = paramT;this.arg2 = paramU;this.fn = paramBiFunction;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  526 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) { Object localObject1;
/*      */         Object localObject2;
/*  528 */         try { localObject1 = this.fn.apply(this.arg1, this.arg2);
/*  529 */           localObject2 = null;
/*      */         } catch (Throwable localThrowable) {
/*  531 */           localObject2 = localThrowable;
/*  532 */           localObject1 = null;
/*      */         }
/*  534 */         localCompletableFuture.internalComplete(localObject1, (Throwable)localObject2);
/*      */       }
/*  536 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncAccept<T> extends CompletableFuture.Async {
/*      */     final T arg;
/*      */     final Consumer<? super T> fn;
/*      */     final CompletableFuture<?> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AsyncAccept(T paramT, Consumer<? super T> paramConsumer, CompletableFuture<?> paramCompletableFuture) {
/*  547 */       this.arg = paramT;this.fn = paramConsumer;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  551 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) {
/*      */         Object localObject;
/*  553 */         try { this.fn.accept(this.arg);
/*  554 */           localObject = null;
/*      */         } catch (Throwable localThrowable) {
/*  556 */           localObject = localThrowable;
/*      */         }
/*  558 */         localCompletableFuture.internalComplete(null, (Throwable)localObject);
/*      */       }
/*  560 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncAcceptBoth<T, U> extends CompletableFuture.Async
/*      */   {
/*      */     final T arg1;
/*      */     final U arg2;
/*      */     final BiConsumer<? super T, ? super U> fn;
/*      */     final CompletableFuture<?> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AsyncAcceptBoth(T paramT, U paramU, BiConsumer<? super T, ? super U> paramBiConsumer, CompletableFuture<?> paramCompletableFuture) {
/*  573 */       this.arg1 = paramT;this.arg2 = paramU;this.fn = paramBiConsumer;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  577 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) {
/*      */         Object localObject;
/*  579 */         try { this.fn.accept(this.arg1, this.arg2);
/*  580 */           localObject = null;
/*      */         } catch (Throwable localThrowable) {
/*  582 */           localObject = localThrowable;
/*      */         }
/*  584 */         localCompletableFuture.internalComplete(null, (Throwable)localObject);
/*      */       }
/*  586 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncCompose<T, U> extends CompletableFuture.Async
/*      */   {
/*      */     final T arg;
/*      */     final Function<? super T, ? extends CompletionStage<U>> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AsyncCompose(T paramT, Function<? super T, ? extends CompletionStage<U>> paramFunction, CompletableFuture<U> paramCompletableFuture) {
/*  598 */       this.arg = paramT;this.fn = paramFunction;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture1;
/*  602 */       if (((localCompletableFuture1 = this.dst) != null) && (localCompletableFuture1.result == null)) { CompletableFuture localCompletableFuture2;
/*      */         Throwable localThrowable1;
/*  604 */         try { CompletionStage localCompletionStage = (CompletionStage)this.fn.apply(this.arg);
/*  605 */           localCompletableFuture2 = localCompletionStage == null ? null : localCompletionStage.toCompletableFuture();
/*  606 */           localThrowable1 = localCompletableFuture2 == null ? new NullPointerException() : null;
/*      */         } catch (Throwable localThrowable2) {
/*  608 */           localThrowable1 = localThrowable2;
/*  609 */           localCompletableFuture2 = null; }
/*      */         Object localObject1;
/*  611 */         if (localThrowable1 != null) {
/*  612 */           localObject1 = null;
/*      */         } else {
/*  614 */           Object localObject2 = localCompletableFuture2.result;
/*  615 */           if (localObject2 == null)
/*  616 */             localObject2 = localCompletableFuture2.waitingGet(false);
/*  617 */           if ((localObject2 instanceof CompletableFuture.AltResult)) {
/*  618 */             localThrowable1 = ((CompletableFuture.AltResult)localObject2).ex;
/*  619 */             localObject1 = null;
/*      */           }
/*      */           else {
/*  622 */             Object localObject3 = localObject2;
/*  623 */             localObject1 = localObject3;
/*      */           }
/*      */         }
/*  626 */         localCompletableFuture1.internalComplete(localObject1, localThrowable1);
/*      */       }
/*  628 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AsyncWhenComplete<T> extends CompletableFuture.Async
/*      */   {
/*      */     final T arg1;
/*      */     final Throwable arg2;
/*      */     final BiConsumer<? super T, ? super Throwable> fn;
/*      */     final CompletableFuture<T> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AsyncWhenComplete(T paramT, Throwable paramThrowable, BiConsumer<? super T, ? super Throwable> paramBiConsumer, CompletableFuture<T> paramCompletableFuture) {
/*  641 */       this.arg1 = paramT;this.arg2 = paramThrowable;this.fn = paramBiConsumer;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final boolean exec() { CompletableFuture localCompletableFuture;
/*  645 */       if (((localCompletableFuture = this.dst) != null) && (localCompletableFuture.result == null)) {
/*  646 */         Object localObject = this.arg2;
/*      */         try {
/*  648 */           this.fn.accept(this.arg1, localObject);
/*      */         } catch (Throwable localThrowable) {
/*  650 */           if (localObject == null)
/*  651 */             localObject = localThrowable;
/*      */         }
/*  653 */         localCompletableFuture.internalComplete(this.arg1, (Throwable)localObject);
/*      */       }
/*  655 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class CompletionNode
/*      */   {
/*      */     final CompletableFuture.Completion completion;
/*      */     
/*      */ 
/*      */     volatile CompletionNode next;
/*      */     
/*      */ 
/*      */     CompletionNode(CompletableFuture.Completion paramCompletion)
/*      */     {
/*  671 */       this.completion = paramCompletion;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Completion extends AtomicInteger implements Runnable
/*      */   {}
/*      */   
/*      */   static final class ThenApply<T, U> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final Function<? super T, ? extends U> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenApply(CompletableFuture<? extends T> paramCompletableFuture, Function<? super T, ? extends U> paramFunction, CompletableFuture<U> paramCompletableFuture1, Executor paramExecutor)
/*      */     {
/*  688 */       this.src = paramCompletableFuture;this.fn = paramFunction;this.dst = paramCompletableFuture1;
/*  689 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       Function localFunction;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*  696 */       if (((localCompletableFuture2 = this.dst) != null) && ((localFunction = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*  700 */         if (compareAndSet(0, 1)) { Object localObject3;
/*  701 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/*  702 */             localObject3 = ((CompletableFuture.AltResult)localObject1).ex;
/*  703 */             localObject2 = null;
/*      */           }
/*      */           else {
/*  706 */             localObject3 = null;
/*  707 */             localObject4 = localObject1;
/*  708 */             localObject2 = localObject4;
/*      */           }
/*  710 */           Object localObject4 = this.executor;
/*  711 */           Object localObject5 = null;
/*  712 */           if (localObject3 == null) {
/*      */             try {
/*  714 */               if (localObject4 != null) {
/*  715 */                 CompletableFuture.execAsync((Executor)localObject4, new CompletableFuture.AsyncApply(localObject2, localFunction, localCompletableFuture2));
/*      */               } else
/*  717 */                 localObject5 = localFunction.apply(localObject2);
/*      */             } catch (Throwable localThrowable) {
/*  719 */               localObject3 = localThrowable;
/*      */             }
/*      */           }
/*  722 */           if ((localObject4 == null) || (localObject3 != null)) {
/*  723 */             localCompletableFuture2.internalComplete(localObject5, (Throwable)localObject3);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenAccept<T> extends CompletableFuture.Completion {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final Consumer<? super T> fn;
/*      */     final CompletableFuture<?> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenAccept(CompletableFuture<? extends T> paramCompletableFuture, Consumer<? super T> paramConsumer, CompletableFuture<?> paramCompletableFuture1, Executor paramExecutor) {
/*  738 */       this.src = paramCompletableFuture;this.fn = paramConsumer;this.dst = paramCompletableFuture1;
/*  739 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       Consumer localConsumer;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*  746 */       if (((localCompletableFuture2 = this.dst) != null) && ((localConsumer = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*  750 */         if (compareAndSet(0, 1)) { Object localObject3;
/*  751 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/*  752 */             localObject3 = ((CompletableFuture.AltResult)localObject1).ex;
/*  753 */             localObject2 = null;
/*      */           }
/*      */           else {
/*  756 */             localObject3 = null;
/*  757 */             localObject4 = localObject1;
/*  758 */             localObject2 = localObject4;
/*      */           }
/*  760 */           Object localObject4 = this.executor;
/*  761 */           if (localObject3 == null) {
/*      */             try {
/*  763 */               if (localObject4 != null) {
/*  764 */                 CompletableFuture.execAsync((Executor)localObject4, new CompletableFuture.AsyncAccept(localObject2, localConsumer, localCompletableFuture2));
/*      */               } else
/*  766 */                 localConsumer.accept(localObject2);
/*      */             } catch (Throwable localThrowable) {
/*  768 */               localObject3 = localThrowable;
/*      */             }
/*      */           }
/*  771 */           if ((localObject4 == null) || (localObject3 != null)) {
/*  772 */             localCompletableFuture2.internalComplete(null, (Throwable)localObject3);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenRun extends CompletableFuture.Completion {
/*      */     final CompletableFuture<?> src;
/*      */     final Runnable fn;
/*      */     final CompletableFuture<Void> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenRun(CompletableFuture<?> paramCompletableFuture, Runnable paramRunnable, CompletableFuture<Void> paramCompletableFuture1, Executor paramExecutor) {
/*  787 */       this.src = paramCompletableFuture;this.fn = paramRunnable;this.dst = paramCompletableFuture1;
/*  788 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       Runnable localRunnable;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*  795 */       if (((localCompletableFuture2 = this.dst) != null) && ((localRunnable = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*  799 */         if (compareAndSet(0, 1)) { Object localObject2;
/*  800 */           if ((localObject1 instanceof CompletableFuture.AltResult)) {
/*  801 */             localObject2 = ((CompletableFuture.AltResult)localObject1).ex;
/*      */           } else
/*  803 */             localObject2 = null;
/*  804 */           Executor localExecutor = this.executor;
/*  805 */           if (localObject2 == null) {
/*      */             try {
/*  807 */               if (localExecutor != null) {
/*  808 */                 CompletableFuture.execAsync(localExecutor, new CompletableFuture.AsyncRun(localRunnable, localCompletableFuture2));
/*      */               } else
/*  810 */                 localRunnable.run();
/*      */             } catch (Throwable localThrowable) {
/*  812 */               localObject2 = localThrowable;
/*      */             }
/*      */           }
/*  815 */           if ((localExecutor == null) || (localObject2 != null)) {
/*  816 */             localCompletableFuture2.internalComplete(null, (Throwable)localObject2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenCombine<T, U, V> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final CompletableFuture<? extends U> snd;
/*      */     final BiFunction<? super T, ? super U, ? extends V> fn;
/*      */     final CompletableFuture<V> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenCombine(CompletableFuture<? extends T> paramCompletableFuture, CompletableFuture<? extends U> paramCompletableFuture1, BiFunction<? super T, ? super U, ? extends V> paramBiFunction, CompletableFuture<V> paramCompletableFuture2, Executor paramExecutor) {
/*  833 */       this.src = paramCompletableFuture;this.snd = paramCompletableFuture1;
/*  834 */       this.fn = paramBiFunction;this.dst = paramCompletableFuture2;
/*  835 */       this.executor = paramExecutor; }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       BiFunction localBiFunction;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/*      */       Object localObject2;
/*  843 */       if (((localCompletableFuture3 = this.dst) != null) && ((localBiFunction = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null) && ((localCompletableFuture2 = this.snd) != null) && ((localObject2 = localCompletableFuture2.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  849 */         if (compareAndSet(0, 1)) { Object localObject5;
/*  850 */           Object localObject3; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/*  851 */             localObject5 = ((CompletableFuture.AltResult)localObject1).ex;
/*  852 */             localObject3 = null;
/*      */           }
/*      */           else {
/*  855 */             localObject5 = null;
/*  856 */             localObject6 = localObject1;
/*  857 */             localObject3 = localObject6; }
/*      */           Object localObject4;
/*  859 */           if (localObject5 != null) {
/*  860 */             localObject4 = null;
/*  861 */           } else if ((localObject2 instanceof CompletableFuture.AltResult)) {
/*  862 */             localObject5 = ((CompletableFuture.AltResult)localObject2).ex;
/*  863 */             localObject4 = null;
/*      */           }
/*      */           else {
/*  866 */             localObject6 = localObject2;
/*  867 */             localObject4 = localObject6;
/*      */           }
/*  869 */           Object localObject6 = this.executor;
/*  870 */           Object localObject7 = null;
/*  871 */           if (localObject5 == null) {
/*      */             try {
/*  873 */               if (localObject6 != null) {
/*  874 */                 CompletableFuture.execAsync((Executor)localObject6, new CompletableFuture.AsyncCombine(localObject3, localObject4, localBiFunction, localCompletableFuture3));
/*      */               } else
/*  876 */                 localObject7 = localBiFunction.apply(localObject3, localObject4);
/*      */             } catch (Throwable localThrowable) {
/*  878 */               localObject5 = localThrowable;
/*      */             }
/*      */           }
/*  881 */           if ((localObject6 == null) || (localObject5 != null)) {
/*  882 */             localCompletableFuture3.internalComplete(localObject7, (Throwable)localObject5);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenAcceptBoth<T, U> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final CompletableFuture<? extends U> snd;
/*      */     final BiConsumer<? super T, ? super U> fn;
/*      */     final CompletableFuture<Void> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenAcceptBoth(CompletableFuture<? extends T> paramCompletableFuture, CompletableFuture<? extends U> paramCompletableFuture1, BiConsumer<? super T, ? super U> paramBiConsumer, CompletableFuture<Void> paramCompletableFuture2, Executor paramExecutor) {
/*  899 */       this.src = paramCompletableFuture;this.snd = paramCompletableFuture1;
/*  900 */       this.fn = paramBiConsumer;this.dst = paramCompletableFuture2;
/*  901 */       this.executor = paramExecutor; }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       BiConsumer localBiConsumer;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/*      */       Object localObject2;
/*  909 */       if (((localCompletableFuture3 = this.dst) != null) && ((localBiConsumer = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null) && ((localCompletableFuture2 = this.snd) != null) && ((localObject2 = localCompletableFuture2.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  915 */         if (compareAndSet(0, 1)) { Object localObject5;
/*  916 */           Object localObject3; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/*  917 */             localObject5 = ((CompletableFuture.AltResult)localObject1).ex;
/*  918 */             localObject3 = null;
/*      */           }
/*      */           else {
/*  921 */             localObject5 = null;
/*  922 */             localObject6 = localObject1;
/*  923 */             localObject3 = localObject6; }
/*      */           Object localObject4;
/*  925 */           if (localObject5 != null) {
/*  926 */             localObject4 = null;
/*  927 */           } else if ((localObject2 instanceof CompletableFuture.AltResult)) {
/*  928 */             localObject5 = ((CompletableFuture.AltResult)localObject2).ex;
/*  929 */             localObject4 = null;
/*      */           }
/*      */           else {
/*  932 */             localObject6 = localObject2;
/*  933 */             localObject4 = localObject6;
/*      */           }
/*  935 */           Object localObject6 = this.executor;
/*  936 */           if (localObject5 == null) {
/*      */             try {
/*  938 */               if (localObject6 != null) {
/*  939 */                 CompletableFuture.execAsync((Executor)localObject6, new CompletableFuture.AsyncAcceptBoth(localObject3, localObject4, localBiConsumer, localCompletableFuture3));
/*      */               } else
/*  941 */                 localBiConsumer.accept(localObject3, localObject4);
/*      */             } catch (Throwable localThrowable) {
/*  943 */               localObject5 = localThrowable;
/*      */             }
/*      */           }
/*  946 */           if ((localObject6 == null) || (localObject5 != null)) {
/*  947 */             localCompletableFuture3.internalComplete(null, (Throwable)localObject5);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class RunAfterBoth extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<?> src;
/*      */     final CompletableFuture<?> snd;
/*      */     final Runnable fn;
/*      */     final CompletableFuture<Void> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     RunAfterBoth(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, Runnable paramRunnable, CompletableFuture<Void> paramCompletableFuture, Executor paramExecutor) {
/*  964 */       this.src = paramCompletableFuture1;this.snd = paramCompletableFuture2;
/*  965 */       this.fn = paramRunnable;this.dst = paramCompletableFuture;
/*  966 */       this.executor = paramExecutor; }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       Runnable localRunnable;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/*      */       Object localObject2;
/*  974 */       if (((localCompletableFuture3 = this.dst) != null) && ((localRunnable = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null) && ((localCompletableFuture2 = this.snd) != null) && ((localObject2 = localCompletableFuture2.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  980 */         if (compareAndSet(0, 1)) { Object localObject3;
/*  981 */           if ((localObject1 instanceof CompletableFuture.AltResult)) {
/*  982 */             localObject3 = ((CompletableFuture.AltResult)localObject1).ex;
/*      */           } else
/*  984 */             localObject3 = null;
/*  985 */           if ((localObject3 == null) && ((localObject2 instanceof CompletableFuture.AltResult)))
/*  986 */             localObject3 = ((CompletableFuture.AltResult)localObject2).ex;
/*  987 */           Executor localExecutor = this.executor;
/*  988 */           if (localObject3 == null) {
/*      */             try {
/*  990 */               if (localExecutor != null) {
/*  991 */                 CompletableFuture.execAsync(localExecutor, new CompletableFuture.AsyncRun(localRunnable, localCompletableFuture3));
/*      */               } else
/*  993 */                 localRunnable.run();
/*      */             } catch (Throwable localThrowable) {
/*  995 */               localObject3 = localThrowable;
/*      */             }
/*      */           }
/*  998 */           if ((localExecutor == null) || (localObject3 != null))
/*  999 */             localCompletableFuture3.internalComplete(null, (Throwable)localObject3);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AndCompletion extends CompletableFuture.Completion {
/*      */     final CompletableFuture<?> src;
/*      */     final CompletableFuture<?> snd;
/*      */     final CompletableFuture<Void> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AndCompletion(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, CompletableFuture<Void> paramCompletableFuture) {
/* 1012 */       this.src = paramCompletableFuture1;this.snd = paramCompletableFuture2;this.dst = paramCompletableFuture; }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/*      */       Object localObject2;
/* 1019 */       if (((localCompletableFuture3 = this.dst) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null) && ((localCompletableFuture2 = this.snd) != null) && ((localObject2 = localCompletableFuture2.result) != null))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1024 */         if (compareAndSet(0, 1)) { Throwable localThrowable;
/* 1025 */           if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1026 */             localThrowable = ((CompletableFuture.AltResult)localObject1).ex;
/*      */           } else
/* 1028 */             localThrowable = null;
/* 1029 */           if ((localThrowable == null) && ((localObject2 instanceof CompletableFuture.AltResult)))
/* 1030 */             localThrowable = ((CompletableFuture.AltResult)localObject2).ex;
/* 1031 */           localCompletableFuture3.internalComplete(null, localThrowable);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ApplyToEither<T, U> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final CompletableFuture<? extends T> snd;
/*      */     final Function<? super T, ? extends U> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ApplyToEither(CompletableFuture<? extends T> paramCompletableFuture1, CompletableFuture<? extends T> paramCompletableFuture2, Function<? super T, ? extends U> paramFunction, CompletableFuture<U> paramCompletableFuture, Executor paramExecutor)
/*      */     {
/* 1048 */       this.src = paramCompletableFuture1;this.snd = paramCompletableFuture2;
/* 1049 */       this.fn = paramFunction;this.dst = paramCompletableFuture;
/* 1050 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       Function localFunction;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/* 1058 */       if (((localCompletableFuture3 = this.dst) != null) && ((localFunction = this.fn) != null) && ((((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null)) || (((localCompletableFuture2 = this.snd) != null) && ((localObject1 = localCompletableFuture2.result) != null))))
/*      */       {
/*      */ 
/*      */ 
/* 1062 */         if (compareAndSet(0, 1)) { Object localObject3;
/* 1063 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1064 */             localObject3 = ((CompletableFuture.AltResult)localObject1).ex;
/* 1065 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1068 */             localObject3 = null;
/* 1069 */             localObject4 = localObject1;
/* 1070 */             localObject2 = localObject4;
/*      */           }
/* 1072 */           Object localObject4 = this.executor;
/* 1073 */           Object localObject5 = null;
/* 1074 */           if (localObject3 == null) {
/*      */             try {
/* 1076 */               if (localObject4 != null) {
/* 1077 */                 CompletableFuture.execAsync((Executor)localObject4, new CompletableFuture.AsyncApply(localObject2, localFunction, localCompletableFuture3));
/*      */               } else
/* 1079 */                 localObject5 = localFunction.apply(localObject2);
/*      */             } catch (Throwable localThrowable) {
/* 1081 */               localObject3 = localThrowable;
/*      */             }
/*      */           }
/* 1084 */           if ((localObject4 == null) || (localObject3 != null)) {
/* 1085 */             localCompletableFuture3.internalComplete(localObject5, (Throwable)localObject3);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AcceptEither<T> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final CompletableFuture<? extends T> snd;
/*      */     final Consumer<? super T> fn;
/*      */     final CompletableFuture<Void> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AcceptEither(CompletableFuture<? extends T> paramCompletableFuture1, CompletableFuture<? extends T> paramCompletableFuture2, Consumer<? super T> paramConsumer, CompletableFuture<Void> paramCompletableFuture, Executor paramExecutor) {
/* 1102 */       this.src = paramCompletableFuture1;this.snd = paramCompletableFuture2;
/* 1103 */       this.fn = paramConsumer;this.dst = paramCompletableFuture;
/* 1104 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       Consumer localConsumer;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/* 1112 */       if (((localCompletableFuture3 = this.dst) != null) && ((localConsumer = this.fn) != null) && ((((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null)) || (((localCompletableFuture2 = this.snd) != null) && ((localObject1 = localCompletableFuture2.result) != null))))
/*      */       {
/*      */ 
/*      */ 
/* 1116 */         if (compareAndSet(0, 1)) { Object localObject3;
/* 1117 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1118 */             localObject3 = ((CompletableFuture.AltResult)localObject1).ex;
/* 1119 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1122 */             localObject3 = null;
/* 1123 */             localObject4 = localObject1;
/* 1124 */             localObject2 = localObject4;
/*      */           }
/* 1126 */           Object localObject4 = this.executor;
/* 1127 */           if (localObject3 == null) {
/*      */             try {
/* 1129 */               if (localObject4 != null) {
/* 1130 */                 CompletableFuture.execAsync((Executor)localObject4, new CompletableFuture.AsyncAccept(localObject2, localConsumer, localCompletableFuture3));
/*      */               } else
/* 1132 */                 localConsumer.accept(localObject2);
/*      */             } catch (Throwable localThrowable) {
/* 1134 */               localObject3 = localThrowable;
/*      */             }
/*      */           }
/* 1137 */           if ((localObject4 == null) || (localObject3 != null)) {
/* 1138 */             localCompletableFuture3.internalComplete(null, (Throwable)localObject3);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class RunAfterEither extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<?> src;
/*      */     final CompletableFuture<?> snd;
/*      */     final Runnable fn;
/*      */     final CompletableFuture<Void> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     RunAfterEither(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, Runnable paramRunnable, CompletableFuture<Void> paramCompletableFuture, Executor paramExecutor) {
/* 1155 */       this.src = paramCompletableFuture1;this.snd = paramCompletableFuture2;
/* 1156 */       this.fn = paramRunnable;this.dst = paramCompletableFuture;
/* 1157 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       Runnable localRunnable;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/* 1165 */       if (((localCompletableFuture3 = this.dst) != null) && ((localRunnable = this.fn) != null) && ((((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null)) || (((localCompletableFuture2 = this.snd) != null) && ((localObject1 = localCompletableFuture2.result) != null))))
/*      */       {
/*      */ 
/*      */ 
/* 1169 */         if (compareAndSet(0, 1)) { Object localObject2;
/* 1170 */           if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1171 */             localObject2 = ((CompletableFuture.AltResult)localObject1).ex;
/*      */           } else
/* 1173 */             localObject2 = null;
/* 1174 */           Executor localExecutor = this.executor;
/* 1175 */           if (localObject2 == null) {
/*      */             try {
/* 1177 */               if (localExecutor != null) {
/* 1178 */                 CompletableFuture.execAsync(localExecutor, new CompletableFuture.AsyncRun(localRunnable, localCompletableFuture3));
/*      */               } else
/* 1180 */                 localRunnable.run();
/*      */             } catch (Throwable localThrowable) {
/* 1182 */               localObject2 = localThrowable;
/*      */             }
/*      */           }
/* 1185 */           if ((localExecutor == null) || (localObject2 != null))
/* 1186 */             localCompletableFuture3.internalComplete(null, (Throwable)localObject2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class OrCompletion extends CompletableFuture.Completion {
/*      */     final CompletableFuture<?> src;
/*      */     final CompletableFuture<?> snd;
/*      */     final CompletableFuture<Object> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     OrCompletion(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, CompletableFuture<Object> paramCompletableFuture) {
/* 1199 */       this.src = paramCompletableFuture1;this.snd = paramCompletableFuture2;this.dst = paramCompletableFuture;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture3;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/*      */       CompletableFuture localCompletableFuture2;
/* 1206 */       if (((localCompletableFuture3 = this.dst) != null) && ((((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null)) || (((localCompletableFuture2 = this.snd) != null) && ((localObject1 = localCompletableFuture2.result) != null))))
/*      */       {
/*      */ 
/* 1209 */         if (compareAndSet(0, 1)) { Throwable localThrowable;
/* 1210 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1211 */             localThrowable = ((CompletableFuture.AltResult)localObject1).ex;
/* 1212 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1215 */             localThrowable = null;
/* 1216 */             localObject2 = localObject1;
/*      */           }
/* 1218 */           localCompletableFuture3.internalComplete(localObject2, localThrowable);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ExceptionCompletion<T> extends CompletableFuture.Completion {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final Function<? super Throwable, ? extends T> fn;
/*      */     final CompletableFuture<T> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ExceptionCompletion(CompletableFuture<? extends T> paramCompletableFuture, Function<? super Throwable, ? extends T> paramFunction, CompletableFuture<T> paramCompletableFuture1) {
/* 1231 */       this.src = paramCompletableFuture;this.fn = paramFunction;this.dst = paramCompletableFuture1;
/*      */     }
/*      */     
/*      */ 
/*      */     public final void run()
/*      */     {
/* 1237 */       Object localObject2 = null;Object localObject3 = null;
/* 1238 */       CompletableFuture localCompletableFuture2; Function localFunction; CompletableFuture localCompletableFuture1; Object localObject1; if (((localCompletableFuture2 = this.dst) != null) && ((localFunction = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/* 1242 */         if (compareAndSet(0, 1)) { Throwable localThrowable1;
/* 1243 */           if (((localObject1 instanceof CompletableFuture.AltResult)) && ((localThrowable1 = ((CompletableFuture.AltResult)localObject1).ex) != null))
/*      */           {
/*      */             try {
/* 1246 */               localObject2 = localFunction.apply(localThrowable1);
/*      */             } catch (Throwable localThrowable2) {
/* 1248 */               localObject3 = localThrowable2;
/*      */             }
/*      */           }
/*      */           else {
/* 1252 */             Object localObject4 = localObject1;
/* 1253 */             localObject2 = localObject4;
/*      */           }
/* 1255 */           localCompletableFuture2.internalComplete(localObject2, (Throwable)localObject3);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class WhenCompleteCompletion<T> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final BiConsumer<? super T, ? super Throwable> fn;
/*      */     final CompletableFuture<T> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     WhenCompleteCompletion(CompletableFuture<? extends T> paramCompletableFuture, BiConsumer<? super T, ? super Throwable> paramBiConsumer, CompletableFuture<T> paramCompletableFuture1, Executor paramExecutor) {
/* 1270 */       this.src = paramCompletableFuture;this.fn = paramBiConsumer;this.dst = paramCompletableFuture1;
/* 1271 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       BiConsumer localBiConsumer;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/* 1278 */       if (((localCompletableFuture2 = this.dst) != null) && ((localBiConsumer = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/* 1282 */         if (compareAndSet(0, 1)) { Throwable localThrowable1;
/* 1283 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1284 */             localThrowable1 = ((CompletableFuture.AltResult)localObject1).ex;
/* 1285 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1288 */             localThrowable1 = null;
/* 1289 */             localObject3 = localObject1;
/* 1290 */             localObject2 = localObject3;
/*      */           }
/* 1292 */           Object localObject3 = this.executor;
/* 1293 */           Object localObject4 = null;
/*      */           try {
/* 1295 */             if (localObject3 != null) {
/* 1296 */               CompletableFuture.execAsync((Executor)localObject3, new CompletableFuture.AsyncWhenComplete(localObject2, localThrowable1, localBiConsumer, localCompletableFuture2));
/*      */             } else
/* 1298 */               localBiConsumer.accept(localObject2, localThrowable1);
/*      */           } catch (Throwable localThrowable2) {
/* 1300 */             localObject4 = localThrowable2;
/*      */           }
/* 1302 */           if ((localObject3 == null) || (localObject4 != null))
/* 1303 */             localCompletableFuture2.internalComplete(localObject2, localThrowable1 != null ? localThrowable1 : (Throwable)localObject4);
/*      */         } }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenCopy<T> extends CompletableFuture.Completion {
/*      */     final CompletableFuture<?> src;
/*      */     final CompletableFuture<T> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenCopy(CompletableFuture<?> paramCompletableFuture, CompletableFuture<T> paramCompletableFuture1) {
/* 1314 */       this.src = paramCompletableFuture;this.dst = paramCompletableFuture1;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/* 1320 */       if (((localCompletableFuture2 = this.dst) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/* 1323 */         if (compareAndSet(0, 1)) { Throwable localThrowable;
/* 1324 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1325 */             localThrowable = ((CompletableFuture.AltResult)localObject1).ex;
/* 1326 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1329 */             localThrowable = null;
/* 1330 */             Object localObject3 = localObject1;
/* 1331 */             localObject2 = localObject3;
/*      */           }
/* 1333 */           localCompletableFuture2.internalComplete(localObject2, localThrowable);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenPropagate extends CompletableFuture.Completion {
/*      */     final CompletableFuture<?> src;
/*      */     final CompletableFuture<Void> dst;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenPropagate(CompletableFuture<?> paramCompletableFuture, CompletableFuture<Void> paramCompletableFuture1) {
/* 1345 */       this.src = paramCompletableFuture;this.dst = paramCompletableFuture1;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject;
/* 1351 */       if (((localCompletableFuture2 = this.dst) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/* 1354 */         if (compareAndSet(0, 1)) { Throwable localThrowable;
/* 1355 */           if ((localObject instanceof CompletableFuture.AltResult)) {
/* 1356 */             localThrowable = ((CompletableFuture.AltResult)localObject).ex;
/*      */           } else
/* 1358 */             localThrowable = null;
/* 1359 */           localCompletableFuture2.internalComplete(null, localThrowable);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class HandleCompletion<T, U> extends CompletableFuture.Completion
/*      */   {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final BiFunction<? super T, Throwable, ? extends U> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     HandleCompletion(CompletableFuture<? extends T> paramCompletableFuture, BiFunction<? super T, Throwable, ? extends U> paramBiFunction, CompletableFuture<U> paramCompletableFuture1, Executor paramExecutor) {
/* 1374 */       this.src = paramCompletableFuture;this.fn = paramBiFunction;this.dst = paramCompletableFuture1;
/* 1375 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       BiFunction localBiFunction;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/* 1382 */       if (((localCompletableFuture2 = this.dst) != null) && ((localBiFunction = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/* 1386 */         if (compareAndSet(0, 1)) { Throwable localThrowable1;
/* 1387 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1388 */             localThrowable1 = ((CompletableFuture.AltResult)localObject1).ex;
/* 1389 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1392 */             localThrowable1 = null;
/* 1393 */             localObject3 = localObject1;
/* 1394 */             localObject2 = localObject3;
/*      */           }
/* 1396 */           Object localObject3 = this.executor;
/* 1397 */           Object localObject4 = null;
/* 1398 */           Object localObject5 = null;
/*      */           try {
/* 1400 */             if (localObject3 != null) {
/* 1401 */               CompletableFuture.execAsync((Executor)localObject3, new CompletableFuture.AsyncCombine(localObject2, localThrowable1, localBiFunction, localCompletableFuture2));
/*      */             } else
/* 1403 */               localObject4 = localBiFunction.apply(localObject2, localThrowable1);
/*      */           } catch (Throwable localThrowable2) {
/* 1405 */             localObject5 = localThrowable2;
/*      */           }
/* 1407 */           if ((localObject3 == null) || (localObject5 != null)) {
/* 1408 */             localCompletableFuture2.internalComplete(localObject4, (Throwable)localObject5);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ThenCompose<T, U> extends CompletableFuture.Completion {
/*      */     final CompletableFuture<? extends T> src;
/*      */     final Function<? super T, ? extends CompletionStage<U>> fn;
/*      */     final CompletableFuture<U> dst;
/*      */     final Executor executor;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     ThenCompose(CompletableFuture<? extends T> paramCompletableFuture, Function<? super T, ? extends CompletionStage<U>> paramFunction, CompletableFuture<U> paramCompletableFuture1, Executor paramExecutor) {
/* 1423 */       this.src = paramCompletableFuture;this.fn = paramFunction;this.dst = paramCompletableFuture1;
/* 1424 */       this.executor = paramExecutor;
/*      */     }
/*      */     
/*      */     public final void run() { CompletableFuture localCompletableFuture2;
/*      */       Function localFunction;
/*      */       CompletableFuture localCompletableFuture1;
/*      */       Object localObject1;
/* 1431 */       if (((localCompletableFuture2 = this.dst) != null) && ((localFunction = this.fn) != null) && ((localCompletableFuture1 = this.src) != null) && ((localObject1 = localCompletableFuture1.result) != null))
/*      */       {
/*      */ 
/*      */ 
/* 1435 */         if (compareAndSet(0, 1)) { Object localObject3;
/* 1436 */           Object localObject2; if ((localObject1 instanceof CompletableFuture.AltResult)) {
/* 1437 */             localObject3 = ((CompletableFuture.AltResult)localObject1).ex;
/* 1438 */             localObject2 = null;
/*      */           }
/*      */           else {
/* 1441 */             localObject3 = null;
/* 1442 */             localObject4 = localObject1;
/* 1443 */             localObject2 = localObject4;
/*      */           }
/* 1445 */           Object localObject4 = null;
/* 1446 */           Object localObject5 = null;
/* 1447 */           int i = 0;
/* 1448 */           if (localObject3 == null) { Executor localExecutor;
/* 1449 */             if ((localExecutor = this.executor) != null) {
/* 1450 */               CompletableFuture.execAsync(localExecutor, new CompletableFuture.AsyncCompose(localObject2, localFunction, localCompletableFuture2));
/*      */             } else {
/*      */               try {
/* 1453 */                 CompletionStage localCompletionStage = (CompletionStage)localFunction.apply(localObject2);
/* 1454 */                 localObject4 = localCompletionStage == null ? null : localCompletionStage.toCompletableFuture();
/* 1455 */                 if (localObject4 == null)
/* 1456 */                   localObject3 = new NullPointerException();
/*      */               } catch (Throwable localThrowable) {
/* 1458 */                 localObject3 = localThrowable;
/*      */               }
/*      */             }
/*      */           }
/* 1462 */           if (localObject4 != null) {
/* 1463 */             CompletableFuture.ThenCopy localThenCopy = null;
/*      */             Object localObject6;
/* 1465 */             Object localObject7; if ((localObject6 = ((CompletableFuture)localObject4).result) == null) {
/* 1466 */               localObject7 = new CompletableFuture.CompletionNode(localThenCopy = new CompletableFuture.ThenCopy((CompletableFuture)localObject4, localCompletableFuture2));
/*      */               
/* 1468 */               while ((localObject6 = ((CompletableFuture)localObject4).result) == null)
/*      */               {
/* 1470 */                 if (CompletableFuture.UNSAFE.compareAndSwapObject(localObject4, CompletableFuture.COMPLETIONS, ((CompletableFuture.CompletionNode)localObject7).next = ((CompletableFuture)localObject4).completions, localObject7))
/*      */                   break;
/*      */               }
/*      */             }
/* 1474 */             if ((localObject6 != null) && ((localThenCopy == null) || (localThenCopy.compareAndSet(0, 1)))) {
/* 1475 */               i = 1;
/* 1476 */               if ((localObject6 instanceof CompletableFuture.AltResult)) {
/* 1477 */                 localObject3 = ((CompletableFuture.AltResult)localObject6).ex;
/* 1478 */                 localObject5 = null;
/*      */               }
/*      */               else {
/* 1481 */                 localObject7 = localObject6;
/* 1482 */                 localObject5 = localObject7;
/*      */               }
/*      */             }
/*      */           }
/* 1486 */           if ((i != 0) || (localObject3 != null))
/* 1487 */             localCompletableFuture2.internalComplete(localObject5, (Throwable)localObject3);
/* 1488 */           if (localObject4 != null) {
/* 1489 */             ((CompletableFuture)localObject4).helpPostComplete();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private <U> CompletableFuture<U> doThenApply(Function<? super T, ? extends U> paramFunction, Executor paramExecutor)
/*      */   {
/* 1500 */     if (paramFunction == null) throw new NullPointerException();
/* 1501 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1502 */     ThenApply localThenApply = null;
/*      */     Object localObject1;
/* 1504 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 1505 */       localObject2 = new CompletionNode(localThenApply = new ThenApply(this, paramFunction, localCompletableFuture, paramExecutor));
/*      */       
/* 1507 */       while ((localObject1 = this.result) == null)
/*      */       {
/* 1509 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2))
/*      */           break;
/*      */       }
/*      */     }
/* 1513 */     if ((localObject1 != null) && ((localThenApply == null) || (localThenApply.compareAndSet(0, 1)))) {
/*      */       Object localObject3;
/* 1515 */       if ((localObject1 instanceof AltResult)) {
/* 1516 */         localObject3 = ((AltResult)localObject1).ex;
/* 1517 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 1520 */         localObject3 = null;
/* 1521 */         localObject4 = localObject1;
/* 1522 */         localObject2 = localObject4;
/*      */       }
/* 1524 */       Object localObject4 = null;
/* 1525 */       if (localObject3 == null) {
/*      */         try {
/* 1527 */           if (paramExecutor != null) {
/* 1528 */             execAsync(paramExecutor, new AsyncApply(localObject2, paramFunction, localCompletableFuture));
/*      */           } else
/* 1530 */             localObject4 = paramFunction.apply(localObject2);
/*      */         } catch (Throwable localThrowable) {
/* 1532 */           localObject3 = localThrowable;
/*      */         }
/*      */       }
/* 1535 */       if ((paramExecutor == null) || (localObject3 != null))
/* 1536 */         localCompletableFuture.internalComplete(localObject4, (Throwable)localObject3);
/*      */     }
/* 1538 */     helpPostComplete();
/* 1539 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */   private CompletableFuture<Void> doThenAccept(Consumer<? super T> paramConsumer, Executor paramExecutor)
/*      */   {
/* 1544 */     if (paramConsumer == null) throw new NullPointerException();
/* 1545 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1546 */     ThenAccept localThenAccept = null;
/*      */     Object localObject1;
/* 1548 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 1549 */       localObject2 = new CompletionNode(localThenAccept = new ThenAccept(this, paramConsumer, localCompletableFuture, paramExecutor));
/*      */       
/* 1551 */       while ((localObject1 = this.result) == null)
/*      */       {
/* 1553 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2))
/*      */           break;
/*      */       }
/*      */     }
/* 1557 */     if ((localObject1 != null) && ((localThenAccept == null) || (localThenAccept.compareAndSet(0, 1)))) {
/*      */       Object localObject3;
/* 1559 */       if ((localObject1 instanceof AltResult)) {
/* 1560 */         localObject3 = ((AltResult)localObject1).ex;
/* 1561 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 1564 */         localObject3 = null;
/* 1565 */         Object localObject4 = localObject1;
/* 1566 */         localObject2 = localObject4;
/*      */       }
/* 1568 */       if (localObject3 == null) {
/*      */         try {
/* 1570 */           if (paramExecutor != null) {
/* 1571 */             execAsync(paramExecutor, new AsyncAccept(localObject2, paramConsumer, localCompletableFuture));
/*      */           } else
/* 1573 */             paramConsumer.accept(localObject2);
/*      */         } catch (Throwable localThrowable) {
/* 1575 */           localObject3 = localThrowable;
/*      */         }
/*      */       }
/* 1578 */       if ((paramExecutor == null) || (localObject3 != null))
/* 1579 */         localCompletableFuture.internalComplete(null, (Throwable)localObject3);
/*      */     }
/* 1581 */     helpPostComplete();
/* 1582 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */   private CompletableFuture<Void> doThenRun(Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 1587 */     if (paramRunnable == null) throw new NullPointerException();
/* 1588 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1589 */     ThenRun localThenRun = null;
/*      */     Object localObject1;
/* 1591 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 1592 */       localObject2 = new CompletionNode(localThenRun = new ThenRun(this, paramRunnable, localCompletableFuture, paramExecutor));
/*      */       
/* 1594 */       while ((localObject1 = this.result) == null)
/*      */       {
/* 1596 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2))
/*      */           break;
/*      */       }
/*      */     }
/* 1600 */     if ((localObject1 != null) && ((localThenRun == null) || (localThenRun.compareAndSet(0, 1))))
/*      */     {
/* 1602 */       if ((localObject1 instanceof AltResult)) {
/* 1603 */         localObject2 = ((AltResult)localObject1).ex;
/*      */       } else
/* 1605 */         localObject2 = null;
/* 1606 */       if (localObject2 == null) {
/*      */         try {
/* 1608 */           if (paramExecutor != null) {
/* 1609 */             execAsync(paramExecutor, new AsyncRun(paramRunnable, localCompletableFuture));
/*      */           } else
/* 1611 */             paramRunnable.run();
/*      */         } catch (Throwable localThrowable) {
/* 1613 */           localObject2 = localThrowable;
/*      */         }
/*      */       }
/* 1616 */       if ((paramExecutor == null) || (localObject2 != null))
/* 1617 */         localCompletableFuture.internalComplete(null, (Throwable)localObject2);
/*      */     }
/* 1619 */     helpPostComplete();
/* 1620 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private <U, V> CompletableFuture<V> doThenCombine(CompletableFuture<? extends U> paramCompletableFuture, BiFunction<? super T, ? super U, ? extends V> paramBiFunction, Executor paramExecutor)
/*      */   {
/* 1627 */     if ((paramCompletableFuture == null) || (paramBiFunction == null)) throw new NullPointerException();
/* 1628 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1629 */     ThenCombine localThenCombine = null;
/* 1630 */     Object localObject2 = null;
/* 1631 */     Object localObject1; Object localObject3; Object localObject4; if (((localObject1 = this.result) == null) || ((localObject2 = paramCompletableFuture.result) == null)) {
/* 1632 */       localThenCombine = new ThenCombine(this, paramCompletableFuture, paramBiFunction, localCompletableFuture, paramExecutor);
/* 1633 */       localObject3 = null;localObject4 = new CompletionNode(localThenCombine);
/* 1634 */       while (((localObject1 == null) && ((localObject1 = this.result) == null)) || ((localObject2 == null) && ((localObject2 = paramCompletableFuture.result) == null)))
/*      */       {
/* 1636 */         if (localObject3 != null) {
/* 1637 */           if (localObject2 != null)
/*      */             break;
/* 1639 */           if (UNSAFE.compareAndSwapObject(paramCompletableFuture, COMPLETIONS, ((CompletionNode)localObject3).next = paramCompletableFuture.completions, localObject3)) {
/*      */             break;
/*      */           }
/* 1642 */         } else if ((localObject1 != null) || 
/*      */         
/* 1644 */           (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject4).next = this.completions, localObject4))) {
/* 1645 */           if (localObject2 != null)
/*      */             break;
/* 1647 */           localObject3 = new CompletionNode(localThenCombine);
/*      */         }
/*      */       }
/*      */     }
/* 1651 */     if ((localObject1 != null) && (localObject2 != null) && ((localThenCombine == null) || (localThenCombine.compareAndSet(0, 1)))) {
/*      */       Object localObject5;
/* 1653 */       if ((localObject1 instanceof AltResult)) {
/* 1654 */         localObject5 = ((AltResult)localObject1).ex;
/* 1655 */         localObject3 = null;
/*      */       }
/*      */       else {
/* 1658 */         localObject5 = null;
/* 1659 */         localObject6 = localObject1;
/* 1660 */         localObject3 = localObject6;
/*      */       }
/* 1662 */       if (localObject5 != null) {
/* 1663 */         localObject4 = null;
/* 1664 */       } else if ((localObject2 instanceof AltResult)) {
/* 1665 */         localObject5 = ((AltResult)localObject2).ex;
/* 1666 */         localObject4 = null;
/*      */       }
/*      */       else {
/* 1669 */         localObject6 = localObject2;
/* 1670 */         localObject4 = localObject6;
/*      */       }
/* 1672 */       Object localObject6 = null;
/* 1673 */       if (localObject5 == null) {
/*      */         try {
/* 1675 */           if (paramExecutor != null) {
/* 1676 */             execAsync(paramExecutor, new AsyncCombine(localObject3, localObject4, paramBiFunction, localCompletableFuture));
/*      */           } else
/* 1678 */             localObject6 = paramBiFunction.apply(localObject3, localObject4);
/*      */         } catch (Throwable localThrowable) {
/* 1680 */           localObject5 = localThrowable;
/*      */         }
/*      */       }
/* 1683 */       if ((paramExecutor == null) || (localObject5 != null))
/* 1684 */         localCompletableFuture.internalComplete(localObject6, (Throwable)localObject5);
/*      */     }
/* 1686 */     helpPostComplete();
/* 1687 */     paramCompletableFuture.helpPostComplete();
/* 1688 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private <U> CompletableFuture<Void> doThenAcceptBoth(CompletableFuture<? extends U> paramCompletableFuture, BiConsumer<? super T, ? super U> paramBiConsumer, Executor paramExecutor)
/*      */   {
/* 1695 */     if ((paramCompletableFuture == null) || (paramBiConsumer == null)) throw new NullPointerException();
/* 1696 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1697 */     ThenAcceptBoth localThenAcceptBoth = null;
/* 1698 */     Object localObject2 = null;
/* 1699 */     Object localObject1; Object localObject3; Object localObject4; if (((localObject1 = this.result) == null) || ((localObject2 = paramCompletableFuture.result) == null)) {
/* 1700 */       localThenAcceptBoth = new ThenAcceptBoth(this, paramCompletableFuture, paramBiConsumer, localCompletableFuture, paramExecutor);
/* 1701 */       localObject3 = null;localObject4 = new CompletionNode(localThenAcceptBoth);
/* 1702 */       while (((localObject1 == null) && ((localObject1 = this.result) == null)) || ((localObject2 == null) && ((localObject2 = paramCompletableFuture.result) == null)))
/*      */       {
/* 1704 */         if (localObject3 != null) {
/* 1705 */           if (localObject2 != null)
/*      */             break;
/* 1707 */           if (UNSAFE.compareAndSwapObject(paramCompletableFuture, COMPLETIONS, ((CompletionNode)localObject3).next = paramCompletableFuture.completions, localObject3)) {
/*      */             break;
/*      */           }
/* 1710 */         } else if ((localObject1 != null) || 
/*      */         
/* 1712 */           (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject4).next = this.completions, localObject4))) {
/* 1713 */           if (localObject2 != null)
/*      */             break;
/* 1715 */           localObject3 = new CompletionNode(localThenAcceptBoth);
/*      */         }
/*      */       }
/*      */     }
/* 1719 */     if ((localObject1 != null) && (localObject2 != null) && ((localThenAcceptBoth == null) || (localThenAcceptBoth.compareAndSet(0, 1)))) { Object localObject5;
/*      */       Object localObject6;
/* 1721 */       if ((localObject1 instanceof AltResult)) {
/* 1722 */         localObject5 = ((AltResult)localObject1).ex;
/* 1723 */         localObject3 = null;
/*      */       }
/*      */       else {
/* 1726 */         localObject5 = null;
/* 1727 */         localObject6 = localObject1;
/* 1728 */         localObject3 = localObject6;
/*      */       }
/* 1730 */       if (localObject5 != null) {
/* 1731 */         localObject4 = null;
/* 1732 */       } else if ((localObject2 instanceof AltResult)) {
/* 1733 */         localObject5 = ((AltResult)localObject2).ex;
/* 1734 */         localObject4 = null;
/*      */       }
/*      */       else {
/* 1737 */         localObject6 = localObject2;
/* 1738 */         localObject4 = localObject6;
/*      */       }
/* 1740 */       if (localObject5 == null) {
/*      */         try {
/* 1742 */           if (paramExecutor != null) {
/* 1743 */             execAsync(paramExecutor, new AsyncAcceptBoth(localObject3, localObject4, paramBiConsumer, localCompletableFuture));
/*      */           } else
/* 1745 */             paramBiConsumer.accept(localObject3, localObject4);
/*      */         } catch (Throwable localThrowable) {
/* 1747 */           localObject5 = localThrowable;
/*      */         }
/*      */       }
/* 1750 */       if ((paramExecutor == null) || (localObject5 != null))
/* 1751 */         localCompletableFuture.internalComplete(null, (Throwable)localObject5);
/*      */     }
/* 1753 */     helpPostComplete();
/* 1754 */     paramCompletableFuture.helpPostComplete();
/* 1755 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */   private CompletableFuture<Void> doRunAfterBoth(CompletableFuture<?> paramCompletableFuture, Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 1761 */     if ((paramCompletableFuture == null) || (paramRunnable == null)) throw new NullPointerException();
/* 1762 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1763 */     RunAfterBoth localRunAfterBoth = null;
/* 1764 */     Object localObject2 = null;
/* 1765 */     Object localObject1; Object localObject3; if (((localObject1 = this.result) == null) || ((localObject2 = paramCompletableFuture.result) == null)) {
/* 1766 */       localRunAfterBoth = new RunAfterBoth(this, paramCompletableFuture, paramRunnable, localCompletableFuture, paramExecutor);
/* 1767 */       localObject3 = null;CompletionNode localCompletionNode = new CompletionNode(localRunAfterBoth);
/* 1768 */       while (((localObject1 == null) && ((localObject1 = this.result) == null)) || ((localObject2 == null) && ((localObject2 = paramCompletableFuture.result) == null)))
/*      */       {
/* 1770 */         if (localObject3 != null) {
/* 1771 */           if (localObject2 != null)
/*      */             break;
/* 1773 */           if (UNSAFE.compareAndSwapObject(paramCompletableFuture, COMPLETIONS, ((CompletionNode)localObject3).next = paramCompletableFuture.completions, localObject3)) {
/*      */             break;
/*      */           }
/* 1776 */         } else if ((localObject1 != null) || 
/*      */         
/* 1778 */           (UNSAFE.compareAndSwapObject(this, COMPLETIONS, localCompletionNode.next = this.completions, localCompletionNode))) {
/* 1779 */           if (localObject2 != null)
/*      */             break;
/* 1781 */           localObject3 = new CompletionNode(localRunAfterBoth);
/*      */         }
/*      */       }
/*      */     }
/* 1785 */     if ((localObject1 != null) && (localObject2 != null) && ((localRunAfterBoth == null) || (localRunAfterBoth.compareAndSet(0, 1))))
/*      */     {
/* 1787 */       if ((localObject1 instanceof AltResult)) {
/* 1788 */         localObject3 = ((AltResult)localObject1).ex;
/*      */       } else
/* 1790 */         localObject3 = null;
/* 1791 */       if ((localObject3 == null) && ((localObject2 instanceof AltResult)))
/* 1792 */         localObject3 = ((AltResult)localObject2).ex;
/* 1793 */       if (localObject3 == null) {
/*      */         try {
/* 1795 */           if (paramExecutor != null) {
/* 1796 */             execAsync(paramExecutor, new AsyncRun(paramRunnable, localCompletableFuture));
/*      */           } else
/* 1798 */             paramRunnable.run();
/*      */         } catch (Throwable localThrowable) {
/* 1800 */           localObject3 = localThrowable;
/*      */         }
/*      */       }
/* 1803 */       if ((paramExecutor == null) || (localObject3 != null))
/* 1804 */         localCompletableFuture.internalComplete(null, (Throwable)localObject3);
/*      */     }
/* 1806 */     helpPostComplete();
/* 1807 */     paramCompletableFuture.helpPostComplete();
/* 1808 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private <U> CompletableFuture<U> doApplyToEither(CompletableFuture<? extends T> paramCompletableFuture, Function<? super T, U> paramFunction, Executor paramExecutor)
/*      */   {
/* 1815 */     if ((paramCompletableFuture == null) || (paramFunction == null)) throw new NullPointerException();
/* 1816 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1817 */     ApplyToEither localApplyToEither = null;
/*      */     Object localObject1;
/* 1819 */     Object localObject2; Object localObject3; if (((localObject1 = this.result) == null) && ((localObject1 = paramCompletableFuture.result) == null)) {
/* 1820 */       localApplyToEither = new ApplyToEither(this, paramCompletableFuture, paramFunction, localCompletableFuture, paramExecutor);
/* 1821 */       localObject2 = null;localObject3 = new CompletionNode(localApplyToEither);
/* 1822 */       while (((localObject1 = this.result) == null) && ((localObject1 = paramCompletableFuture.result) == null)) {
/* 1823 */         if (localObject2 != null)
/*      */         {
/* 1825 */           if (UNSAFE.compareAndSwapObject(paramCompletableFuture, COMPLETIONS, ((CompletionNode)localObject2).next = paramCompletableFuture.completions, localObject2)) {
/*      */             break;
/*      */           }
/*      */         }
/* 1829 */         else if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject3).next = this.completions, localObject3))
/* 1830 */           localObject2 = new CompletionNode(localApplyToEither);
/*      */       }
/*      */     }
/* 1833 */     if ((localObject1 != null) && ((localApplyToEither == null) || (localApplyToEither.compareAndSet(0, 1))))
/*      */     {
/* 1835 */       if ((localObject1 instanceof AltResult)) {
/* 1836 */         localObject3 = ((AltResult)localObject1).ex;
/* 1837 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 1840 */         localObject3 = null;
/* 1841 */         localObject4 = localObject1;
/* 1842 */         localObject2 = localObject4;
/*      */       }
/* 1844 */       Object localObject4 = null;
/* 1845 */       if (localObject3 == null) {
/*      */         try {
/* 1847 */           if (paramExecutor != null) {
/* 1848 */             execAsync(paramExecutor, new AsyncApply(localObject2, paramFunction, localCompletableFuture));
/*      */           } else
/* 1850 */             localObject4 = paramFunction.apply(localObject2);
/*      */         } catch (Throwable localThrowable) {
/* 1852 */           localObject3 = localThrowable;
/*      */         }
/*      */       }
/* 1855 */       if ((paramExecutor == null) || (localObject3 != null))
/* 1856 */         localCompletableFuture.internalComplete(localObject4, (Throwable)localObject3);
/*      */     }
/* 1858 */     helpPostComplete();
/* 1859 */     paramCompletableFuture.helpPostComplete();
/* 1860 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private CompletableFuture<Void> doAcceptEither(CompletableFuture<? extends T> paramCompletableFuture, Consumer<? super T> paramConsumer, Executor paramExecutor)
/*      */   {
/* 1867 */     if ((paramCompletableFuture == null) || (paramConsumer == null)) throw new NullPointerException();
/* 1868 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1869 */     AcceptEither localAcceptEither = null;
/*      */     Object localObject1;
/* 1871 */     Object localObject2; Object localObject3; if (((localObject1 = this.result) == null) && ((localObject1 = paramCompletableFuture.result) == null)) {
/* 1872 */       localAcceptEither = new AcceptEither(this, paramCompletableFuture, paramConsumer, localCompletableFuture, paramExecutor);
/* 1873 */       localObject2 = null;localObject3 = new CompletionNode(localAcceptEither);
/* 1874 */       while (((localObject1 = this.result) == null) && ((localObject1 = paramCompletableFuture.result) == null)) {
/* 1875 */         if (localObject2 != null)
/*      */         {
/* 1877 */           if (UNSAFE.compareAndSwapObject(paramCompletableFuture, COMPLETIONS, ((CompletionNode)localObject2).next = paramCompletableFuture.completions, localObject2)) {
/*      */             break;
/*      */           }
/*      */         }
/* 1881 */         else if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject3).next = this.completions, localObject3))
/* 1882 */           localObject2 = new CompletionNode(localAcceptEither);
/*      */       }
/*      */     }
/* 1885 */     if ((localObject1 != null) && ((localAcceptEither == null) || (localAcceptEither.compareAndSet(0, 1))))
/*      */     {
/* 1887 */       if ((localObject1 instanceof AltResult)) {
/* 1888 */         localObject3 = ((AltResult)localObject1).ex;
/* 1889 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 1892 */         localObject3 = null;
/* 1893 */         Object localObject4 = localObject1;
/* 1894 */         localObject2 = localObject4;
/*      */       }
/* 1896 */       if (localObject3 == null) {
/*      */         try {
/* 1898 */           if (paramExecutor != null) {
/* 1899 */             execAsync(paramExecutor, new AsyncAccept(localObject2, paramConsumer, localCompletableFuture));
/*      */           } else
/* 1901 */             paramConsumer.accept(localObject2);
/*      */         } catch (Throwable localThrowable) {
/* 1903 */           localObject3 = localThrowable;
/*      */         }
/*      */       }
/* 1906 */       if ((paramExecutor == null) || (localObject3 != null))
/* 1907 */         localCompletableFuture.internalComplete(null, (Throwable)localObject3);
/*      */     }
/* 1909 */     helpPostComplete();
/* 1910 */     paramCompletableFuture.helpPostComplete();
/* 1911 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private CompletableFuture<Void> doRunAfterEither(CompletableFuture<?> paramCompletableFuture, Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 1918 */     if ((paramCompletableFuture == null) || (paramRunnable == null)) throw new NullPointerException();
/* 1919 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 1920 */     RunAfterEither localRunAfterEither = null;
/*      */     Object localObject1;
/* 1922 */     Object localObject2; if (((localObject1 = this.result) == null) && ((localObject1 = paramCompletableFuture.result) == null)) {
/* 1923 */       localRunAfterEither = new RunAfterEither(this, paramCompletableFuture, paramRunnable, localCompletableFuture, paramExecutor);
/* 1924 */       localObject2 = null;CompletionNode localCompletionNode = new CompletionNode(localRunAfterEither);
/* 1925 */       while (((localObject1 = this.result) == null) && ((localObject1 = paramCompletableFuture.result) == null)) {
/* 1926 */         if (localObject2 != null)
/*      */         {
/* 1928 */           if (UNSAFE.compareAndSwapObject(paramCompletableFuture, COMPLETIONS, ((CompletionNode)localObject2).next = paramCompletableFuture.completions, localObject2)) {
/*      */             break;
/*      */           }
/*      */         }
/* 1932 */         else if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, localCompletionNode.next = this.completions, localCompletionNode))
/* 1933 */           localObject2 = new CompletionNode(localRunAfterEither);
/*      */       }
/*      */     }
/* 1936 */     if ((localObject1 != null) && ((localRunAfterEither == null) || (localRunAfterEither.compareAndSet(0, 1))))
/*      */     {
/* 1938 */       if ((localObject1 instanceof AltResult)) {
/* 1939 */         localObject2 = ((AltResult)localObject1).ex;
/*      */       } else
/* 1941 */         localObject2 = null;
/* 1942 */       if (localObject2 == null) {
/*      */         try {
/* 1944 */           if (paramExecutor != null) {
/* 1945 */             execAsync(paramExecutor, new AsyncRun(paramRunnable, localCompletableFuture));
/*      */           } else
/* 1947 */             paramRunnable.run();
/*      */         } catch (Throwable localThrowable) {
/* 1949 */           localObject2 = localThrowable;
/*      */         }
/*      */       }
/* 1952 */       if ((paramExecutor == null) || (localObject2 != null))
/* 1953 */         localCompletableFuture.internalComplete(null, (Throwable)localObject2);
/*      */     }
/* 1955 */     helpPostComplete();
/* 1956 */     paramCompletableFuture.helpPostComplete();
/* 1957 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */   private <U> CompletableFuture<U> doThenCompose(Function<? super T, ? extends CompletionStage<U>> paramFunction, Executor paramExecutor)
/*      */   {
/* 1963 */     if (paramFunction == null) throw new NullPointerException();
/* 1964 */     CompletableFuture localCompletableFuture = null;
/* 1965 */     ThenCompose localThenCompose = null;
/*      */     Object localObject1;
/* 1967 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 1968 */       localCompletableFuture = new CompletableFuture();
/* 1969 */       localObject2 = new CompletionNode(localThenCompose = new ThenCompose(this, paramFunction, localCompletableFuture, paramExecutor));
/*      */       
/* 1971 */       while ((localObject1 = this.result) == null)
/*      */       {
/* 1973 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2))
/*      */           break;
/*      */       }
/*      */     }
/* 1977 */     if ((localObject1 != null) && ((localThenCompose == null) || (localThenCompose.compareAndSet(0, 1)))) { Object localObject3;
/*      */       Object localObject4;
/* 1979 */       if ((localObject1 instanceof AltResult)) {
/* 1980 */         localObject3 = ((AltResult)localObject1).ex;
/* 1981 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 1984 */         localObject3 = null;
/* 1985 */         localObject4 = localObject1;
/* 1986 */         localObject2 = localObject4;
/*      */       }
/* 1988 */       if (localObject3 == null) {
/* 1989 */         if (paramExecutor != null) {
/* 1990 */           if (localCompletableFuture == null)
/* 1991 */             localCompletableFuture = new CompletableFuture();
/* 1992 */           execAsync(paramExecutor, new AsyncCompose(localObject2, paramFunction, localCompletableFuture));
/*      */         }
/*      */         else {
/*      */           try {
/* 1996 */             localObject4 = (CompletionStage)paramFunction.apply(localObject2);
/* 1997 */             if ((localObject4 == null) || 
/* 1998 */               ((localCompletableFuture = ((CompletionStage)localObject4).toCompletableFuture()) == null))
/* 1999 */               localObject3 = new NullPointerException();
/*      */           } catch (Throwable localThrowable) {
/* 2001 */             localObject3 = localThrowable;
/*      */           }
/*      */         }
/*      */       }
/* 2005 */       if (localCompletableFuture == null)
/* 2006 */         localCompletableFuture = new CompletableFuture();
/* 2007 */       if (localObject3 != null)
/* 2008 */         localCompletableFuture.internalComplete(null, (Throwable)localObject3);
/*      */     }
/* 2010 */     helpPostComplete();
/* 2011 */     localCompletableFuture.helpPostComplete();
/* 2012 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */   private CompletableFuture<T> doWhenComplete(BiConsumer<? super T, ? super Throwable> paramBiConsumer, Executor paramExecutor)
/*      */   {
/* 2018 */     if (paramBiConsumer == null) throw new NullPointerException();
/* 2019 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2020 */     WhenCompleteCompletion localWhenCompleteCompletion = null;
/*      */     Object localObject1;
/* 2022 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 2023 */       localObject2 = new CompletionNode(localWhenCompleteCompletion = new WhenCompleteCompletion(this, paramBiConsumer, localCompletableFuture, paramExecutor));
/*      */       
/*      */ 
/* 2026 */       while ((localObject1 = this.result) == null) {
/* 2027 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2)) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 2032 */     if ((localObject1 != null) && ((localWhenCompleteCompletion == null) || (localWhenCompleteCompletion.compareAndSet(0, 1)))) {
/*      */       Throwable localThrowable1;
/* 2034 */       if ((localObject1 instanceof AltResult)) {
/* 2035 */         localThrowable1 = ((AltResult)localObject1).ex;
/* 2036 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 2039 */         localThrowable1 = null;
/* 2040 */         localObject3 = localObject1;
/* 2041 */         localObject2 = localObject3;
/*      */       }
/* 2043 */       Object localObject3 = null;
/*      */       try {
/* 2045 */         if (paramExecutor != null) {
/* 2046 */           execAsync(paramExecutor, new AsyncWhenComplete(localObject2, localThrowable1, paramBiConsumer, localCompletableFuture));
/*      */         } else
/* 2048 */           paramBiConsumer.accept(localObject2, localThrowable1);
/*      */       } catch (Throwable localThrowable2) {
/* 2050 */         localObject3 = localThrowable2;
/*      */       }
/* 2052 */       if ((paramExecutor == null) || (localObject3 != null))
/* 2053 */         localCompletableFuture.internalComplete(localObject2, localThrowable1 != null ? localThrowable1 : (Throwable)localObject3);
/*      */     }
/* 2055 */     helpPostComplete();
/* 2056 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */   private <U> CompletableFuture<U> doHandle(BiFunction<? super T, Throwable, ? extends U> paramBiFunction, Executor paramExecutor)
/*      */   {
/* 2062 */     if (paramBiFunction == null) throw new NullPointerException();
/* 2063 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2064 */     HandleCompletion localHandleCompletion = null;
/*      */     Object localObject1;
/* 2066 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 2067 */       localObject2 = new CompletionNode(localHandleCompletion = new HandleCompletion(this, paramBiFunction, localCompletableFuture, paramExecutor));
/*      */       
/*      */ 
/* 2070 */       while ((localObject1 = this.result) == null) {
/* 2071 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2)) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 2076 */     if ((localObject1 != null) && ((localHandleCompletion == null) || (localHandleCompletion.compareAndSet(0, 1)))) {
/*      */       Throwable localThrowable1;
/* 2078 */       if ((localObject1 instanceof AltResult)) {
/* 2079 */         localThrowable1 = ((AltResult)localObject1).ex;
/* 2080 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 2083 */         localThrowable1 = null;
/* 2084 */         localObject3 = localObject1;
/* 2085 */         localObject2 = localObject3;
/*      */       }
/* 2087 */       Object localObject3 = null;
/* 2088 */       Object localObject4 = null;
/*      */       try {
/* 2090 */         if (paramExecutor != null) {
/* 2091 */           execAsync(paramExecutor, new AsyncCombine(localObject2, localThrowable1, paramBiFunction, localCompletableFuture));
/*      */         } else {
/* 2093 */           localObject3 = paramBiFunction.apply(localObject2, localThrowable1);
/* 2094 */           localObject4 = null;
/*      */         }
/*      */       } catch (Throwable localThrowable2) {
/* 2097 */         localObject4 = localThrowable2;
/* 2098 */         localObject3 = null;
/*      */       }
/* 2100 */       if ((paramExecutor == null) || (localObject4 != null))
/* 2101 */         localCompletableFuture.internalComplete(localObject3, (Throwable)localObject4);
/*      */     }
/* 2103 */     helpPostComplete();
/* 2104 */     return localCompletableFuture;
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
/*      */   public static <U> CompletableFuture<U> supplyAsync(Supplier<U> paramSupplier)
/*      */   {
/* 2127 */     if (paramSupplier == null) throw new NullPointerException();
/* 2128 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2129 */     execAsync(ForkJoinPool.commonPool(), new AsyncSupply(paramSupplier, localCompletableFuture));
/* 2130 */     return localCompletableFuture;
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
/*      */   public static <U> CompletableFuture<U> supplyAsync(Supplier<U> paramSupplier, Executor paramExecutor)
/*      */   {
/* 2146 */     if ((paramExecutor == null) || (paramSupplier == null))
/* 2147 */       throw new NullPointerException();
/* 2148 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2149 */     execAsync(paramExecutor, new AsyncSupply(paramSupplier, localCompletableFuture));
/* 2150 */     return localCompletableFuture;
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
/*      */   public static CompletableFuture<Void> runAsync(Runnable paramRunnable)
/*      */   {
/* 2163 */     if (paramRunnable == null) throw new NullPointerException();
/* 2164 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2165 */     execAsync(ForkJoinPool.commonPool(), new AsyncRun(paramRunnable, localCompletableFuture));
/* 2166 */     return localCompletableFuture;
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
/*      */   public static CompletableFuture<Void> runAsync(Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 2181 */     if ((paramExecutor == null) || (paramRunnable == null))
/* 2182 */       throw new NullPointerException();
/* 2183 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2184 */     execAsync(paramExecutor, new AsyncRun(paramRunnable, localCompletableFuture));
/* 2185 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <U> CompletableFuture<U> completedFuture(U paramU)
/*      */   {
/* 2197 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2198 */     localCompletableFuture.result = (paramU == null ? NIL : paramU);
/* 2199 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDone()
/*      */   {
/* 2209 */     return this.result != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public T get()
/*      */     throws InterruptedException, ExecutionException
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2224 */     if (((localObject1 = this.result) == null) && ((localObject1 = waitingGet(true)) == null))
/* 2225 */       throw new InterruptedException();
/* 2226 */     if (!(localObject1 instanceof AltResult)) {
/* 2227 */       Object localObject3 = localObject1;
/* 2228 */       return (T)localObject3; }
/*      */     Object localObject2;
/* 2230 */     if ((localObject2 = ((AltResult)localObject1).ex) == null)
/* 2231 */       return null;
/* 2232 */     if ((localObject2 instanceof CancellationException))
/* 2233 */       throw ((CancellationException)localObject2);
/* 2234 */     Throwable localThrowable; if (((localObject2 instanceof CompletionException)) && 
/* 2235 */       ((localThrowable = ((Throwable)localObject2).getCause()) != null))
/* 2236 */       localObject2 = localThrowable;
/* 2237 */     throw new ExecutionException((Throwable)localObject2);
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
/*      */   public T get(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException, ExecutionException, TimeoutException
/*      */   {
/* 2256 */     long l = paramTimeUnit.toNanos(paramLong);
/* 2257 */     if (Thread.interrupted())
/* 2258 */       throw new InterruptedException();
/* 2259 */     Object localObject1; if ((localObject1 = this.result) == null)
/* 2260 */       localObject1 = timedAwaitDone(l);
/* 2261 */     if (!(localObject1 instanceof AltResult)) {
/* 2262 */       Object localObject3 = localObject1;
/* 2263 */       return (T)localObject3; }
/*      */     Object localObject2;
/* 2265 */     if ((localObject2 = ((AltResult)localObject1).ex) == null)
/* 2266 */       return null;
/* 2267 */     if ((localObject2 instanceof CancellationException))
/* 2268 */       throw ((CancellationException)localObject2);
/* 2269 */     Throwable localThrowable; if (((localObject2 instanceof CompletionException)) && 
/* 2270 */       ((localThrowable = ((Throwable)localObject2).getCause()) != null))
/* 2271 */       localObject2 = localThrowable;
/* 2272 */     throw new ExecutionException((Throwable)localObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public T join()
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2291 */     if ((localObject1 = this.result) == null)
/* 2292 */       localObject1 = waitingGet(false);
/* 2293 */     if (!(localObject1 instanceof AltResult)) {
/* 2294 */       Object localObject2 = localObject1;
/* 2295 */       return (T)localObject2; }
/*      */     Throwable localThrowable;
/* 2297 */     if ((localThrowable = ((AltResult)localObject1).ex) == null)
/* 2298 */       return null;
/* 2299 */     if ((localThrowable instanceof CancellationException))
/* 2300 */       throw ((CancellationException)localThrowable);
/* 2301 */     if ((localThrowable instanceof CompletionException))
/* 2302 */       throw ((CompletionException)localThrowable);
/* 2303 */     throw new CompletionException(localThrowable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public T getNow(T paramT)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2318 */     if ((localObject1 = this.result) == null)
/* 2319 */       return paramT;
/* 2320 */     if (!(localObject1 instanceof AltResult)) {
/* 2321 */       Object localObject2 = localObject1;
/* 2322 */       return (T)localObject2; }
/*      */     Throwable localThrowable;
/* 2324 */     if ((localThrowable = ((AltResult)localObject1).ex) == null)
/* 2325 */       return null;
/* 2326 */     if ((localThrowable instanceof CancellationException))
/* 2327 */       throw ((CancellationException)localThrowable);
/* 2328 */     if ((localThrowable instanceof CompletionException))
/* 2329 */       throw ((CompletionException)localThrowable);
/* 2330 */     throw new CompletionException(localThrowable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean complete(T paramT)
/*      */   {
/* 2342 */     if (this.result == null) {}
/* 2343 */     boolean bool = UNSAFE.compareAndSwapObject(this, RESULT, null, paramT == null ? NIL : paramT);
/*      */     
/* 2345 */     postComplete();
/* 2346 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean completeExceptionally(Throwable paramThrowable)
/*      */   {
/* 2358 */     if (paramThrowable == null) { throw new NullPointerException();
/*      */     }
/* 2360 */     boolean bool = (this.result == null) && (UNSAFE.compareAndSwapObject(this, RESULT, null, new AltResult(paramThrowable)));
/* 2361 */     postComplete();
/* 2362 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> paramFunction)
/*      */   {
/* 2369 */     return doThenApply(paramFunction, null);
/*      */   }
/*      */   
/*      */   public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> paramFunction)
/*      */   {
/* 2374 */     return doThenApply(paramFunction, ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */   public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> paramFunction, Executor paramExecutor)
/*      */   {
/* 2380 */     if (paramExecutor == null) throw new NullPointerException();
/* 2381 */     return doThenApply(paramFunction, paramExecutor);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenAccept(Consumer<? super T> paramConsumer)
/*      */   {
/* 2386 */     return doThenAccept(paramConsumer, null);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> paramConsumer)
/*      */   {
/* 2391 */     return doThenAccept(paramConsumer, ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> paramConsumer, Executor paramExecutor)
/*      */   {
/* 2397 */     if (paramExecutor == null) throw new NullPointerException();
/* 2398 */     return doThenAccept(paramConsumer, paramExecutor);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenRun(Runnable paramRunnable)
/*      */   {
/* 2403 */     return doThenRun(paramRunnable, null);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenRunAsync(Runnable paramRunnable)
/*      */   {
/* 2408 */     return doThenRun(paramRunnable, ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> thenRunAsync(Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 2414 */     if (paramExecutor == null) throw new NullPointerException();
/* 2415 */     return doThenRun(paramRunnable, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */   public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
/*      */   {
/* 2421 */     return doThenCombine(paramCompletionStage.toCompletableFuture(), paramBiFunction, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
/*      */   {
/* 2427 */     return doThenCombine(paramCompletionStage.toCompletableFuture(), paramBiFunction, 
/* 2428 */       ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction, Executor paramExecutor)
/*      */   {
/* 2435 */     if (paramExecutor == null) throw new NullPointerException();
/* 2436 */     return doThenCombine(paramCompletionStage.toCompletableFuture(), paramBiFunction, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */   public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer)
/*      */   {
/* 2442 */     return doThenAcceptBoth(paramCompletionStage.toCompletableFuture(), paramBiConsumer, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer)
/*      */   {
/* 2448 */     return doThenAcceptBoth(paramCompletionStage.toCompletableFuture(), paramBiConsumer, 
/* 2449 */       ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer, Executor paramExecutor)
/*      */   {
/* 2456 */     if (paramExecutor == null) throw new NullPointerException();
/* 2457 */     return doThenAcceptBoth(paramCompletionStage.toCompletableFuture(), paramBiConsumer, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> runAfterBoth(CompletionStage<?> paramCompletionStage, Runnable paramRunnable)
/*      */   {
/* 2463 */     return doRunAfterBoth(paramCompletionStage.toCompletableFuture(), paramRunnable, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable)
/*      */   {
/* 2469 */     return doRunAfterBoth(paramCompletionStage.toCompletableFuture(), paramRunnable, 
/* 2470 */       ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 2477 */     if (paramExecutor == null) throw new NullPointerException();
/* 2478 */     return doRunAfterBoth(paramCompletionStage.toCompletableFuture(), paramRunnable, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction)
/*      */   {
/* 2485 */     return doApplyToEither(paramCompletionStage.toCompletableFuture(), paramFunction, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction)
/*      */   {
/* 2491 */     return doApplyToEither(paramCompletionStage.toCompletableFuture(), paramFunction, 
/* 2492 */       ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction, Executor paramExecutor)
/*      */   {
/* 2499 */     if (paramExecutor == null) throw new NullPointerException();
/* 2500 */     return doApplyToEither(paramCompletionStage.toCompletableFuture(), paramFunction, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer)
/*      */   {
/* 2506 */     return doAcceptEither(paramCompletionStage.toCompletableFuture(), paramConsumer, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer)
/*      */   {
/* 2512 */     return doAcceptEither(paramCompletionStage.toCompletableFuture(), paramConsumer, 
/* 2513 */       ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer, Executor paramExecutor)
/*      */   {
/* 2520 */     if (paramExecutor == null) throw new NullPointerException();
/* 2521 */     return doAcceptEither(paramCompletionStage.toCompletableFuture(), paramConsumer, paramExecutor);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> runAfterEither(CompletionStage<?> paramCompletionStage, Runnable paramRunnable)
/*      */   {
/* 2526 */     return doRunAfterEither(paramCompletionStage.toCompletableFuture(), paramRunnable, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable)
/*      */   {
/* 2532 */     return doRunAfterEither(paramCompletionStage.toCompletableFuture(), paramRunnable, 
/* 2533 */       ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable, Executor paramExecutor)
/*      */   {
/* 2540 */     if (paramExecutor == null) throw new NullPointerException();
/* 2541 */     return doRunAfterEither(paramCompletionStage.toCompletableFuture(), paramRunnable, paramExecutor);
/*      */   }
/*      */   
/*      */   public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> paramFunction)
/*      */   {
/* 2546 */     return doThenCompose(paramFunction, null);
/*      */   }
/*      */   
/*      */   public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> paramFunction)
/*      */   {
/* 2551 */     return doThenCompose(paramFunction, ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */   public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> paramFunction, Executor paramExecutor)
/*      */   {
/* 2557 */     if (paramExecutor == null) throw new NullPointerException();
/* 2558 */     return doThenCompose(paramFunction, paramExecutor);
/*      */   }
/*      */   
/*      */   public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> paramBiConsumer)
/*      */   {
/* 2563 */     return doWhenComplete(paramBiConsumer, null);
/*      */   }
/*      */   
/*      */   public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> paramBiConsumer)
/*      */   {
/* 2568 */     return doWhenComplete(paramBiConsumer, ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */   public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> paramBiConsumer, Executor paramExecutor)
/*      */   {
/* 2574 */     if (paramExecutor == null) throw new NullPointerException();
/* 2575 */     return doWhenComplete(paramBiConsumer, paramExecutor);
/*      */   }
/*      */   
/*      */   public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> paramBiFunction)
/*      */   {
/* 2580 */     return doHandle(paramBiFunction, null);
/*      */   }
/*      */   
/*      */   public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> paramBiFunction)
/*      */   {
/* 2585 */     return doHandle(paramBiFunction, ForkJoinPool.commonPool());
/*      */   }
/*      */   
/*      */ 
/*      */   public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> paramBiFunction, Executor paramExecutor)
/*      */   {
/* 2591 */     if (paramExecutor == null) throw new NullPointerException();
/* 2592 */     return doHandle(paramBiFunction, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompletableFuture<T> toCompletableFuture()
/*      */   {
/* 2601 */     return this;
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
/*      */   public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> paramFunction)
/*      */   {
/* 2623 */     if (paramFunction == null) throw new NullPointerException();
/* 2624 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/* 2625 */     ExceptionCompletion localExceptionCompletion = null;
/*      */     Object localObject1;
/* 2627 */     Object localObject2; if ((localObject1 = this.result) == null) {
/* 2628 */       localObject2 = new CompletionNode(localExceptionCompletion = new ExceptionCompletion(this, paramFunction, localCompletableFuture));
/*      */       
/*      */ 
/* 2631 */       while ((localObject1 = this.result) == null) {
/* 2632 */         if (UNSAFE.compareAndSwapObject(this, COMPLETIONS, ((CompletionNode)localObject2).next = this.completions, localObject2)) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 2637 */     if ((localObject1 != null) && ((localExceptionCompletion == null) || (localExceptionCompletion.compareAndSet(0, 1)))) {
/* 2638 */       localObject2 = null;Object localObject3 = null;
/* 2639 */       if ((localObject1 instanceof AltResult)) { Throwable localThrowable1;
/* 2640 */         if ((localThrowable1 = ((AltResult)localObject1).ex) != null) {
/*      */           try {
/* 2642 */             localObject2 = paramFunction.apply(localThrowable1);
/*      */           } catch (Throwable localThrowable2) {
/* 2644 */             localObject3 = localThrowable2;
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/* 2649 */         Object localObject4 = localObject1;
/* 2650 */         localObject2 = localObject4;
/*      */       }
/* 2652 */       localCompletableFuture.internalComplete(localObject2, (Throwable)localObject3);
/*      */     }
/* 2654 */     helpPostComplete();
/* 2655 */     return localCompletableFuture;
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
/*      */   public static CompletableFuture<Void> allOf(CompletableFuture<?>... paramVarArgs)
/*      */   {
/* 2691 */     int i = paramVarArgs.length;
/* 2692 */     if (i > 1) {
/* 2693 */       return allTree(paramVarArgs, 0, i - 1);
/*      */     }
/* 2695 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/*      */     
/* 2697 */     if (i == 0) {
/* 2698 */       localCompletableFuture.result = NIL; } else { CompletableFuture<?> localCompletableFuture1;
/* 2699 */       if ((localCompletableFuture1 = paramVarArgs[0]) == null) {
/* 2700 */         throw new NullPointerException();
/*      */       }
/* 2702 */       ThenPropagate localThenPropagate = null;
/* 2703 */       CompletionNode localCompletionNode = null;
/*      */       Object localObject;
/* 2705 */       while ((localObject = localCompletableFuture1.result) == null) {
/* 2706 */         if (localThenPropagate == null) {
/* 2707 */           localThenPropagate = new ThenPropagate(localCompletableFuture1, localCompletableFuture);
/* 2708 */         } else if (localCompletionNode == null) {
/* 2709 */           localCompletionNode = new CompletionNode(localThenPropagate);
/*      */         }
/* 2711 */         else if (UNSAFE.compareAndSwapObject(localCompletableFuture1, COMPLETIONS, localCompletionNode.next = localCompletableFuture1.completions, localCompletionNode))
/*      */           break;
/*      */       }
/* 2714 */       if ((localObject != null) && ((localThenPropagate == null) || (localThenPropagate.compareAndSet(0, 1)))) {
/* 2715 */         localCompletableFuture.internalComplete(null, (localObject instanceof AltResult) ? ((AltResult)localObject).ex : null);
/*      */       }
/* 2717 */       localCompletableFuture1.helpPostComplete();
/*      */     }
/* 2719 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static CompletableFuture<Void> allTree(CompletableFuture<?>[] paramArrayOfCompletableFuture, int paramInt1, int paramInt2)
/*      */   {
/* 2730 */     int i = paramInt1 + paramInt2 >>> 1;
/* 2731 */     CompletableFuture localCompletableFuture1; CompletableFuture localCompletableFuture2; if ((localCompletableFuture1 = paramInt1 == i ? paramArrayOfCompletableFuture[paramInt1] : allTree(paramArrayOfCompletableFuture, paramInt1, i)) != null) {
/* 2732 */       if ((localCompletableFuture2 = paramInt2 == i + 1 ? paramArrayOfCompletableFuture[paramInt2] : allTree(paramArrayOfCompletableFuture, i + 1, paramInt2)) != null) {}
/* 2733 */     } else throw new NullPointerException();
/* 2734 */     CompletableFuture localCompletableFuture3 = new CompletableFuture();
/* 2735 */     AndCompletion localAndCompletion = null;
/* 2736 */     CompletionNode localCompletionNode1 = null;CompletionNode localCompletionNode2 = null;
/* 2737 */     Object localObject1 = null;Object localObject2 = null;
/* 2738 */     while (((localObject1 = localCompletableFuture1.result) == null) || ((localObject2 = localCompletableFuture2.result) == null)) {
/* 2739 */       if (localAndCompletion == null) {
/* 2740 */         localAndCompletion = new AndCompletion(localCompletableFuture1, localCompletableFuture2, localCompletableFuture3);
/* 2741 */       } else if (localCompletionNode1 == null) {
/* 2742 */         localCompletionNode1 = new CompletionNode(localAndCompletion);
/* 2743 */       } else if (localCompletionNode2 == null)
/*      */       {
/* 2745 */         if (UNSAFE.compareAndSwapObject(localCompletableFuture1, COMPLETIONS, localCompletionNode1.next = localCompletableFuture1.completions, localCompletionNode1)) {
/* 2746 */           localCompletionNode2 = new CompletionNode(localAndCompletion);
/*      */         }
/*      */       }
/* 2749 */       else if (UNSAFE.compareAndSwapObject(localCompletableFuture2, COMPLETIONS, localCompletionNode2.next = localCompletableFuture2.completions, localCompletionNode2))
/*      */         break;
/*      */     }
/* 2752 */     if (((localObject1 != null) || ((localObject1 = localCompletableFuture1.result) != null)) && ((localObject2 != null) || ((localObject2 = localCompletableFuture2.result) != null)) && ((localAndCompletion == null) || 
/*      */     
/* 2754 */       (localAndCompletion.compareAndSet(0, 1)))) {
/*      */       Throwable localThrowable;
/* 2756 */       if ((localObject1 instanceof AltResult)) {
/* 2757 */         localThrowable = ((AltResult)localObject1).ex;
/*      */       } else
/* 2759 */         localThrowable = null;
/* 2760 */       if ((localThrowable == null) && ((localObject2 instanceof AltResult)))
/* 2761 */         localThrowable = ((AltResult)localObject2).ex;
/* 2762 */       localCompletableFuture3.internalComplete(null, localThrowable);
/*      */     }
/* 2764 */     localCompletableFuture1.helpPostComplete();
/* 2765 */     localCompletableFuture2.helpPostComplete();
/* 2766 */     return localCompletableFuture3;
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
/*      */   public static CompletableFuture<Object> anyOf(CompletableFuture<?>... paramVarArgs)
/*      */   {
/* 2785 */     int i = paramVarArgs.length;
/* 2786 */     if (i > 1) {
/* 2787 */       return anyTree(paramVarArgs, 0, i - 1);
/*      */     }
/* 2789 */     CompletableFuture localCompletableFuture = new CompletableFuture();
/*      */     
/* 2791 */     if (i != 0) {
/*      */       CompletableFuture<?> localCompletableFuture1;
/* 2793 */       if ((localCompletableFuture1 = paramVarArgs[0]) == null) {
/* 2794 */         throw new NullPointerException();
/*      */       }
/* 2796 */       ThenCopy localThenCopy = null;
/* 2797 */       CompletionNode localCompletionNode = null;
/*      */       Object localObject1;
/* 2799 */       while ((localObject1 = localCompletableFuture1.result) == null) {
/* 2800 */         if (localThenCopy == null) {
/* 2801 */           localThenCopy = new ThenCopy(localCompletableFuture1, localCompletableFuture);
/* 2802 */         } else if (localCompletionNode == null) {
/* 2803 */           localCompletionNode = new CompletionNode(localThenCopy);
/*      */         }
/* 2805 */         else if (UNSAFE.compareAndSwapObject(localCompletableFuture1, COMPLETIONS, localCompletionNode.next = localCompletableFuture1.completions, localCompletionNode))
/*      */           break;
/*      */       }
/* 2808 */       if ((localObject1 != null) && ((localThenCopy == null) || (localThenCopy.compareAndSet(0, 1)))) { Throwable localThrowable;
/*      */         Object localObject2;
/* 2810 */         if ((localObject1 instanceof AltResult)) {
/* 2811 */           localThrowable = ((AltResult)localObject1).ex;
/* 2812 */           localObject2 = null;
/*      */         }
/*      */         else {
/* 2815 */           localThrowable = null;
/* 2816 */           localObject2 = localObject1;
/*      */         }
/* 2818 */         localCompletableFuture.internalComplete(localObject2, localThrowable);
/*      */       }
/* 2820 */       localCompletableFuture1.helpPostComplete();
/*      */     }
/* 2822 */     return localCompletableFuture;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static CompletableFuture<Object> anyTree(CompletableFuture<?>[] paramArrayOfCompletableFuture, int paramInt1, int paramInt2)
/*      */   {
/* 2832 */     int i = paramInt1 + paramInt2 >>> 1;
/* 2833 */     CompletableFuture localCompletableFuture1; CompletableFuture localCompletableFuture2; if ((localCompletableFuture1 = paramInt1 == i ? paramArrayOfCompletableFuture[paramInt1] : anyTree(paramArrayOfCompletableFuture, paramInt1, i)) != null) {
/* 2834 */       if ((localCompletableFuture2 = paramInt2 == i + 1 ? paramArrayOfCompletableFuture[paramInt2] : anyTree(paramArrayOfCompletableFuture, i + 1, paramInt2)) != null) {}
/* 2835 */     } else throw new NullPointerException();
/* 2836 */     CompletableFuture localCompletableFuture3 = new CompletableFuture();
/* 2837 */     OrCompletion localOrCompletion = null;
/* 2838 */     CompletionNode localCompletionNode1 = null;CompletionNode localCompletionNode2 = null;
/*      */     Object localObject1;
/* 2840 */     while (((localObject1 = localCompletableFuture1.result) == null) && ((localObject1 = localCompletableFuture2.result) == null)) {
/* 2841 */       if (localOrCompletion == null) {
/* 2842 */         localOrCompletion = new OrCompletion(localCompletableFuture1, localCompletableFuture2, localCompletableFuture3);
/* 2843 */       } else if (localCompletionNode1 == null) {
/* 2844 */         localCompletionNode1 = new CompletionNode(localOrCompletion);
/* 2845 */       } else if (localCompletionNode2 == null)
/*      */       {
/* 2847 */         if (UNSAFE.compareAndSwapObject(localCompletableFuture1, COMPLETIONS, localCompletionNode1.next = localCompletableFuture1.completions, localCompletionNode1)) {
/* 2848 */           localCompletionNode2 = new CompletionNode(localOrCompletion);
/*      */         }
/*      */       }
/* 2851 */       else if (UNSAFE.compareAndSwapObject(localCompletableFuture2, COMPLETIONS, localCompletionNode2.next = localCompletableFuture2.completions, localCompletionNode2))
/*      */         break;
/*      */     }
/* 2854 */     if (((localObject1 != null) || ((localObject1 = localCompletableFuture1.result) != null) || ((localObject1 = localCompletableFuture2.result) != null)) && ((localOrCompletion == null) || 
/*      */     
/* 2856 */       (localOrCompletion.compareAndSet(0, 1)))) { Throwable localThrowable;
/*      */       Object localObject2;
/* 2858 */       if ((localObject1 instanceof AltResult)) {
/* 2859 */         localThrowable = ((AltResult)localObject1).ex;
/* 2860 */         localObject2 = null;
/*      */       }
/*      */       else {
/* 2863 */         localThrowable = null;
/* 2864 */         localObject2 = localObject1;
/*      */       }
/* 2866 */       localCompletableFuture3.internalComplete(localObject2, localThrowable);
/*      */     }
/* 2868 */     localCompletableFuture1.helpPostComplete();
/* 2869 */     localCompletableFuture2.helpPostComplete();
/* 2870 */     return localCompletableFuture3;
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
/*      */   public boolean cancel(boolean paramBoolean)
/*      */   {
/* 2889 */     if (this.result == null) {}
/*      */     
/* 2891 */     int i = UNSAFE.compareAndSwapObject(this, RESULT, null, new AltResult(new CancellationException())) ? 1 : 0;
/* 2892 */     postComplete();
/* 2893 */     return (i != 0) || (isCancelled());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isCancelled()
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/* 2905 */     return (((localObject = this.result) instanceof AltResult)) && ((((AltResult)localObject).ex instanceof CancellationException));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isCompletedExceptionally()
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2921 */     return (((localObject = this.result) instanceof AltResult)) && (localObject != NIL);
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
/*      */   public void obtrudeValue(T paramT)
/*      */   {
/* 2935 */     this.result = (paramT == null ? NIL : paramT);
/* 2936 */     postComplete();
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
/*      */   public void obtrudeException(Throwable paramThrowable)
/*      */   {
/* 2950 */     if (paramThrowable == null) throw new NullPointerException();
/* 2951 */     this.result = new AltResult(paramThrowable);
/* 2952 */     postComplete();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getNumberOfDependents()
/*      */   {
/* 2964 */     int i = 0;
/* 2965 */     for (CompletionNode localCompletionNode = this.completions; localCompletionNode != null; localCompletionNode = localCompletionNode.next)
/* 2966 */       i++;
/* 2967 */     return i;
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
/*      */   public String toString()
/*      */   {
/* 2981 */     Object localObject = this.result;
/*      */     
/*      */     int i;
/*      */     
/* 2985 */     return super.toString() + (((localObject instanceof AltResult)) && (((AltResult)localObject).ex != null) ? "[Completed exceptionally]" : localObject == null ? "[Not completed, " + i + " dependents]" : (i = getNumberOfDependents()) == 0 ? "[Not completed]" : "[Completed normally]");
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
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 3000 */       UNSAFE = Unsafe.getUnsafe();
/* 3001 */       Class localClass = CompletableFuture.class;
/*      */       
/* 3003 */       RESULT = UNSAFE.objectFieldOffset(localClass.getDeclaredField("result"));
/*      */       
/* 3005 */       WAITERS = UNSAFE.objectFieldOffset(localClass.getDeclaredField("waiters"));
/*      */       
/* 3007 */       COMPLETIONS = UNSAFE.objectFieldOffset(localClass.getDeclaredField("completions"));
/*      */     } catch (Exception localException) {
/* 3009 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/CompletableFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */