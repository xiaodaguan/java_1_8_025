/*     */ package java.util.concurrent;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CountedCompleter<T>
/*     */   extends ForkJoinTask<T>
/*     */ {
/*     */   private static final long serialVersionUID = 5232453752276485070L;
/*     */   final CountedCompleter<?> completer;
/*     */   volatile int pending;
/*     */   private static final Unsafe U;
/*     */   private static final long PENDING;
/*     */   
/*     */   protected CountedCompleter(CountedCompleter<?> paramCountedCompleter, int paramInt)
/*     */   {
/* 428 */     this.completer = paramCountedCompleter;
/* 429 */     this.pending = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CountedCompleter(CountedCompleter<?> paramCountedCompleter)
/*     */   {
/* 439 */     this.completer = paramCountedCompleter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CountedCompleter()
/*     */   {
/* 447 */     this.completer = null;
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
/*     */   public abstract void compute();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCompletion(CountedCompleter<?> paramCountedCompleter) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onExceptionalCompletion(Throwable paramThrowable, CountedCompleter<?> paramCountedCompleter)
/*     */   {
/* 489 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CountedCompleter<?> getCompleter()
/*     */   {
/* 499 */     return this.completer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getPendingCount()
/*     */   {
/* 508 */     return this.pending;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setPendingCount(int paramInt)
/*     */   {
/* 517 */     this.pending = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void addToPendingCount(int paramInt)
/*     */   {
/* 526 */     U.getAndAddInt(this, PENDING, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean compareAndSetPendingCount(int paramInt1, int paramInt2)
/*     */   {
/* 538 */     return U.compareAndSwapInt(this, PENDING, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int decrementPendingCountUnlessZero()
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/* 549 */     while (((i = this.pending) != 0) && 
/* 550 */       (!U.compareAndSwapInt(this, PENDING, i, i - 1))) {}
/* 551 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CountedCompleter<?> getRoot()
/*     */   {
/* 561 */     Object localObject = this;
/* 562 */     CountedCompleter localCountedCompleter; while ((localCountedCompleter = ((CountedCompleter)localObject).completer) != null)
/* 563 */       localObject = localCountedCompleter;
/* 564 */     return (CountedCompleter<?>)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void tryComplete()
/*     */   {
/* 574 */     CountedCompleter localCountedCompleter1 = this;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*     */     int i;
/* 576 */     do { while ((i = localCountedCompleter1.pending) == 0) {
/* 577 */         localCountedCompleter1.onCompletion(localCountedCompleter2);
/* 578 */         if ((localCountedCompleter1 = (localCountedCompleter2 = localCountedCompleter1).completer) == null) {
/* 579 */           localCountedCompleter2.quietlyComplete();
/* 580 */           return;
/*     */         }
/*     */       }
/* 583 */     } while (!U.compareAndSwapInt(localCountedCompleter1, PENDING, i, i - 1));
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
/*     */   public final void propagateCompletion()
/*     */   {
/* 598 */     CountedCompleter localCountedCompleter1 = this;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*     */     int i;
/* 600 */     do { while ((i = localCountedCompleter1.pending) == 0) {
/* 601 */         if ((localCountedCompleter1 = (localCountedCompleter2 = localCountedCompleter1).completer) == null) {
/* 602 */           localCountedCompleter2.quietlyComplete();
/* 603 */           return;
/*     */         }
/*     */       }
/* 606 */     } while (!U.compareAndSwapInt(localCountedCompleter1, PENDING, i, i - 1));
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
/*     */   public void complete(T paramT)
/*     */   {
/* 632 */     setRawResult(paramT);
/* 633 */     onCompletion(this);
/* 634 */     quietlyComplete();
/* 635 */     CountedCompleter localCountedCompleter; if ((localCountedCompleter = this.completer) != null) {
/* 636 */       localCountedCompleter.tryComplete();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final CountedCompleter<?> firstComplete()
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 649 */       if ((i = this.pending) == 0)
/* 650 */         return this;
/* 651 */     } while (!U.compareAndSwapInt(this, PENDING, i, i - 1));
/* 652 */     return null;
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
/*     */   public final CountedCompleter<?> nextComplete()
/*     */   {
/*     */     CountedCompleter localCountedCompleter;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 675 */     if ((localCountedCompleter = this.completer) != null) {
/* 676 */       return localCountedCompleter.firstComplete();
/*     */     }
/* 678 */     quietlyComplete();
/* 679 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void quietlyCompleteRoot()
/*     */   {
/* 687 */     Object localObject = this;
/* 688 */     for (;;) { CountedCompleter localCountedCompleter; if ((localCountedCompleter = ((CountedCompleter)localObject).completer) == null) {
/* 689 */         ((CountedCompleter)localObject).quietlyComplete();
/* 690 */         return;
/*     */       }
/* 692 */       localObject = localCountedCompleter;
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
/*     */   public final void helpComplete(int paramInt)
/*     */   {
/* 707 */     if ((paramInt > 0) && (this.status >= 0)) { Thread localThread;
/* 708 */       if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/*     */         ForkJoinWorkerThread localForkJoinWorkerThread;
/* 710 */         (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool.helpComplete(localForkJoinWorkerThread.workQueue, this, paramInt);
/*     */       } else {
/* 712 */         ForkJoinPool.common.externalHelpComplete(this, paramInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void internalPropagateException(Throwable paramThrowable)
/*     */   {
/* 720 */     CountedCompleter localCountedCompleter1 = this;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/* 721 */     while ((localCountedCompleter1.onExceptionalCompletion(paramThrowable, localCountedCompleter2)) && ((localCountedCompleter1 = (localCountedCompleter2 = localCountedCompleter1).completer) != null) && (localCountedCompleter1.status >= 0) && 
/*     */     
/* 723 */       (localCountedCompleter1.recordExceptionalCompletion(paramThrowable) == Integer.MIN_VALUE)) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean exec()
/*     */   {
/* 731 */     compute();
/* 732 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getRawResult()
/*     */   {
/* 744 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setRawResult(T paramT) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 760 */       U = Unsafe.getUnsafe();
/*     */       
/* 762 */       PENDING = U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
/*     */     } catch (Exception localException) {
/* 764 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/CountedCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */