/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.concurrent.locks.ReentrantLock;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ForkJoinTask<V>
/*      */   implements Future<V>, Serializable
/*      */ {
/*      */   volatile int status;
/*      */   static final int DONE_MASK = -268435456;
/*      */   static final int NORMAL = -268435456;
/*      */   static final int CANCELLED = -1073741824;
/*      */   static final int EXCEPTIONAL = Integer.MIN_VALUE;
/*      */   static final int SIGNAL = 65536;
/*      */   static final int SMASK = 65535;
/*      */   private static final ExceptionNode[] exceptionTable;
/*      */   
/*      */   private int setCompletion(int paramInt)
/*      */   {
/*      */     int i;
/*      */     do
/*      */     {
/*  268 */       if ((i = this.status) < 0)
/*  269 */         return i;
/*  270 */     } while (!U.compareAndSwapInt(this, STATUS, i, i | paramInt));
/*  271 */     if (i >>> 16 != 0)
/*  272 */       synchronized (this) { notifyAll(); }
/*  273 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int doExec()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  287 */     if ((i = this.status) >= 0) {
/*      */       boolean bool;
/*  289 */       try { bool = exec();
/*      */       } catch (Throwable localThrowable) {
/*  291 */         return setExceptionalCompletion(localThrowable);
/*      */       }
/*  293 */       if (bool)
/*  294 */         i = setCompletion(-268435456);
/*      */     }
/*  296 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean trySetSignal()
/*      */   {
/*  307 */     int i = this.status;
/*  308 */     return (i >= 0) && (U.compareAndSwapInt(this, STATUS, i, i | 0x10000));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int externalAwaitDone()
/*      */   {
/*  317 */     ForkJoinPool localForkJoinPool = ForkJoinPool.common;
/*  318 */     int i; if ((i = this.status) >= 0) {
/*  319 */       if (localForkJoinPool != null) {
/*  320 */         if ((this instanceof CountedCompleter)) {
/*  321 */           i = localForkJoinPool.externalHelpComplete((CountedCompleter)this, Integer.MAX_VALUE);
/*  322 */         } else if (localForkJoinPool.tryExternalUnpush(this))
/*  323 */           i = doExec();
/*      */       }
/*  325 */       if ((i >= 0) && ((i = this.status) >= 0)) {
/*  326 */         int j = 0;
/*      */         do {
/*  328 */           if (U.compareAndSwapInt(this, STATUS, i, i | 0x10000)) {
/*  329 */             synchronized (this) {
/*  330 */               if (this.status >= 0) {
/*      */                 try {
/*  332 */                   wait();
/*      */                 } catch (InterruptedException localInterruptedException) {
/*  334 */                   j = 1;
/*      */                 }
/*      */                 
/*      */               } else
/*  338 */                 notifyAll();
/*      */             }
/*      */           }
/*  341 */         } while ((i = this.status) >= 0);
/*  342 */         if (j != 0)
/*  343 */           Thread.currentThread().interrupt();
/*      */       }
/*      */     }
/*  346 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int externalInterruptibleAwaitDone()
/*      */     throws InterruptedException
/*      */   {
/*  354 */     ForkJoinPool localForkJoinPool = ForkJoinPool.common;
/*  355 */     if (Thread.interrupted())
/*  356 */       throw new InterruptedException();
/*  357 */     int i; if (((i = this.status) >= 0) && (localForkJoinPool != null)) {
/*  358 */       if ((this instanceof CountedCompleter)) {
/*  359 */         localForkJoinPool.externalHelpComplete((CountedCompleter)this, Integer.MAX_VALUE);
/*  360 */       } else if (localForkJoinPool.tryExternalUnpush(this))
/*  361 */         doExec();
/*      */     }
/*  363 */     while ((i = this.status) >= 0) {
/*  364 */       if (U.compareAndSwapInt(this, STATUS, i, i | 0x10000)) {
/*  365 */         synchronized (this) {
/*  366 */           if (this.status >= 0) {
/*  367 */             wait();
/*      */           } else
/*  369 */             notifyAll();
/*      */         }
/*      */       }
/*      */     }
/*  373 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int doJoin()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */     ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */     
/*      */     ForkJoinPool.WorkQueue localWorkQueue;
/*      */     
/*  390 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? localForkJoinWorkerThread.pool.awaitJoin(localWorkQueue, this) : ((localWorkQueue = (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).workQueue).tryUnpush(this)) && ((i = doExec()) < 0) ? i : (i = this.status) < 0 ? i : externalAwaitDone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int doInvoke()
/*      */   {
/*      */     int i;
/*      */     
/*      */     Thread localThread;
/*      */     
/*      */     ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */     
/*  403 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool.awaitJoin(localForkJoinWorkerThread.workQueue, this) : (i = doExec()) < 0 ? i : externalAwaitDone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class ExceptionNode
/*      */     extends WeakReference<ForkJoinTask<?>>
/*      */   {
/*      */     final Throwable ex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ExceptionNode next;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final long thrower;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final int hashCode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ExceptionNode(ForkJoinTask<?> paramForkJoinTask, Throwable paramThrowable, ExceptionNode paramExceptionNode)
/*      */     {
/*  444 */       super(ForkJoinTask.exceptionTableRefQueue);
/*  445 */       this.ex = paramThrowable;
/*  446 */       this.next = paramExceptionNode;
/*  447 */       this.thrower = Thread.currentThread().getId();
/*  448 */       this.hashCode = System.identityHashCode(paramForkJoinTask);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final int recordExceptionalCompletion(Throwable paramThrowable)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*  459 */     if ((i = this.status) >= 0) {
/*  460 */       int j = System.identityHashCode(this);
/*  461 */       ReentrantLock localReentrantLock = exceptionTableLock;
/*  462 */       localReentrantLock.lock();
/*      */       try {
/*  464 */         expungeStaleExceptions();
/*  465 */         ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  466 */         int k = j & arrayOfExceptionNode.length - 1;
/*  467 */         for (ExceptionNode localExceptionNode = arrayOfExceptionNode[k];; localExceptionNode = localExceptionNode.next) {
/*  468 */           if (localExceptionNode == null) {
/*  469 */             arrayOfExceptionNode[k] = new ExceptionNode(this, paramThrowable, arrayOfExceptionNode[k]);
/*      */           }
/*      */           else
/*  472 */             if (localExceptionNode.get() == this)
/*      */               break;
/*      */         }
/*      */       } finally {
/*  476 */         localReentrantLock.unlock();
/*      */       }
/*  478 */       i = setCompletion(Integer.MIN_VALUE);
/*      */     }
/*  480 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int setExceptionalCompletion(Throwable paramThrowable)
/*      */   {
/*  489 */     int i = recordExceptionalCompletion(paramThrowable);
/*  490 */     if ((i & 0xF0000000) == Integer.MIN_VALUE)
/*  491 */       internalPropagateException(paramThrowable);
/*  492 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void internalPropagateException(Throwable paramThrowable) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final void cancelIgnoringExceptions(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/*  508 */     if ((paramForkJoinTask != null) && (paramForkJoinTask.status >= 0)) {
/*      */       try {
/*  510 */         paramForkJoinTask.cancel(false);
/*      */       }
/*      */       catch (Throwable localThrowable) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void clearExceptionalCompletion()
/*      */   {
/*  520 */     int i = System.identityHashCode(this);
/*  521 */     ReentrantLock localReentrantLock = exceptionTableLock;
/*  522 */     localReentrantLock.lock();
/*      */     try {
/*  524 */       ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  525 */       int j = i & arrayOfExceptionNode.length - 1;
/*  526 */       Object localObject1 = arrayOfExceptionNode[j];
/*  527 */       Object localObject2 = null;
/*  528 */       while (localObject1 != null) {
/*  529 */         ExceptionNode localExceptionNode = ((ExceptionNode)localObject1).next;
/*  530 */         if (((ExceptionNode)localObject1).get() == this) {
/*  531 */           if (localObject2 == null) {
/*  532 */             arrayOfExceptionNode[j] = localExceptionNode; break;
/*      */           }
/*  534 */           ((ExceptionNode)localObject2).next = localExceptionNode;
/*  535 */           break;
/*      */         }
/*  537 */         localObject2 = localObject1;
/*  538 */         localObject1 = localExceptionNode;
/*      */       }
/*  540 */       expungeStaleExceptions();
/*  541 */       this.status = 0;
/*      */     } finally {
/*  543 */       localReentrantLock.unlock();
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
/*      */   private Throwable getThrowableException()
/*      */   {
/*  562 */     if ((this.status & 0xF0000000) != Integer.MIN_VALUE)
/*  563 */       return null;
/*  564 */     int i = System.identityHashCode(this);
/*      */     
/*  566 */     ReentrantLock localReentrantLock = exceptionTableLock;
/*  567 */     localReentrantLock.lock();
/*      */     Object localObject1;
/*  569 */     ExceptionNode localExceptionNode; try { expungeStaleExceptions();
/*  570 */       localObject1 = exceptionTable;
/*  571 */       localExceptionNode = localObject1[(i & localObject1.length - 1)];
/*  572 */       while ((localExceptionNode != null) && (localExceptionNode.get() != this))
/*  573 */         localExceptionNode = localExceptionNode.next;
/*      */     } finally {
/*  575 */       localReentrantLock.unlock();
/*      */     }
/*      */     
/*  578 */     if ((localExceptionNode == null) || ((localObject1 = localExceptionNode.ex) == null)) {
/*  579 */       return null;
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
/*      */ 
/*  601 */     return (Throwable)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   private static void expungeStaleExceptions()
/*      */   {
/*      */     Reference localReference;
/*  608 */     while ((localReference = exceptionTableRefQueue.poll()) != null) {
/*  609 */       if ((localReference instanceof ExceptionNode)) {
/*  610 */         int i = ((ExceptionNode)localReference).hashCode;
/*  611 */         ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  612 */         int j = i & arrayOfExceptionNode.length - 1;
/*  613 */         Object localObject1 = arrayOfExceptionNode[j];
/*  614 */         Object localObject2 = null;
/*  615 */         while (localObject1 != null) {
/*  616 */           ExceptionNode localExceptionNode = ((ExceptionNode)localObject1).next;
/*  617 */           if (localObject1 == localReference) {
/*  618 */             if (localObject2 == null) {
/*  619 */               arrayOfExceptionNode[j] = localExceptionNode; break;
/*      */             }
/*  621 */             ((ExceptionNode)localObject2).next = localExceptionNode;
/*  622 */             break;
/*      */           }
/*  624 */           localObject2 = localObject1;
/*  625 */           localObject1 = localExceptionNode;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   static final void helpExpungeStaleExceptions()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: getstatic 35	java/util/concurrent/ForkJoinTask:exceptionTableLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   3: astore_0
/*      */     //   4: aload_0
/*      */     //   5: invokevirtual 51	java/util/concurrent/locks/ReentrantLock:tryLock	()Z
/*      */     //   8: ifeq +20 -> 28
/*      */     //   11: invokestatic 37	java/util/concurrent/ForkJoinTask:expungeStaleExceptions	()V
/*      */     //   14: aload_0
/*      */     //   15: invokevirtual 43	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   18: goto +10 -> 28
/*      */     //   21: astore_1
/*      */     //   22: aload_0
/*      */     //   23: invokevirtual 43	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   26: aload_1
/*      */     //   27: athrow
/*      */     //   28: return
/*      */     // Line number table:
/*      */     //   Java source line #636	-> byte code offset #0
/*      */     //   Java source line #637	-> byte code offset #4
/*      */     //   Java source line #639	-> byte code offset #11
/*      */     //   Java source line #641	-> byte code offset #14
/*      */     //   Java source line #642	-> byte code offset #18
/*      */     //   Java source line #641	-> byte code offset #21
/*      */     //   Java source line #644	-> byte code offset #28
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   3	20	0	localReentrantLock	ReentrantLock
/*      */     //   21	6	1	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   11	14	21	finally
/*      */   }
/*      */   
/*      */   static void rethrow(Throwable paramThrowable)
/*      */   {
/*  650 */     if (paramThrowable != null) {
/*  651 */       uncheckedThrow(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static <T extends Throwable> void uncheckedThrow(Throwable paramThrowable)
/*      */     throws Throwable
/*      */   {
/*  661 */     throw paramThrowable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void reportException(int paramInt)
/*      */   {
/*  668 */     if (paramInt == -1073741824)
/*  669 */       throw new CancellationException();
/*  670 */     if (paramInt == Integer.MIN_VALUE) {
/*  671 */       rethrow(getThrowableException());
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
/*      */   public final ForkJoinTask<V> fork()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  693 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/*  694 */       ((ForkJoinWorkerThread)localThread).workQueue.push(this);
/*      */     } else
/*  696 */       ForkJoinPool.common.externalPush(this);
/*  697 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final V join()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  713 */     if ((i = doJoin() & 0xF0000000) != -268435456)
/*  714 */       reportException(i);
/*  715 */     return (V)getRawResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final V invoke()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*  728 */     if ((i = doInvoke() & 0xF0000000) != -268435456)
/*  729 */       reportException(i);
/*  730 */     return (V)getRawResult();
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
/*      */   public static void invokeAll(ForkJoinTask<?> paramForkJoinTask1, ForkJoinTask<?> paramForkJoinTask2)
/*      */   {
/*  752 */     paramForkJoinTask2.fork();
/*  753 */     int i; if ((i = paramForkJoinTask1.doInvoke() & 0xF0000000) != -268435456)
/*  754 */       paramForkJoinTask1.reportException(i);
/*  755 */     int j; if ((j = paramForkJoinTask2.doJoin() & 0xF0000000) != -268435456) {
/*  756 */       paramForkJoinTask2.reportException(j);
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
/*      */   public static void invokeAll(ForkJoinTask<?>... paramVarArgs)
/*      */   {
/*  775 */     Object localObject = null;
/*  776 */     int i = paramVarArgs.length - 1;
/*  777 */     ForkJoinTask<?> localForkJoinTask; for (int j = i; j >= 0; j--) {
/*  778 */       localForkJoinTask = paramVarArgs[j];
/*  779 */       if (localForkJoinTask == null) {
/*  780 */         if (localObject == null) {
/*  781 */           localObject = new NullPointerException();
/*      */         }
/*  783 */       } else if (j != 0) {
/*  784 */         localForkJoinTask.fork();
/*  785 */       } else if ((localForkJoinTask.doInvoke() < -268435456) && (localObject == null))
/*  786 */         localObject = localForkJoinTask.getException();
/*      */     }
/*  788 */     for (j = 1; j <= i; j++) {
/*  789 */       localForkJoinTask = paramVarArgs[j];
/*  790 */       if (localForkJoinTask != null) {
/*  791 */         if (localObject != null) {
/*  792 */           localForkJoinTask.cancel(false);
/*  793 */         } else if (localForkJoinTask.doJoin() < -268435456)
/*  794 */           localObject = localForkJoinTask.getException();
/*      */       }
/*      */     }
/*  797 */     if (localObject != null) {
/*  798 */       rethrow((Throwable)localObject);
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
/*      */   public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> paramCollection)
/*      */   {
/*  820 */     if ((!(paramCollection instanceof RandomAccess)) || (!(paramCollection instanceof List))) {
/*  821 */       invokeAll((ForkJoinTask[])paramCollection.toArray(new ForkJoinTask[paramCollection.size()]));
/*  822 */       return paramCollection;
/*      */     }
/*      */     
/*  825 */     List localList = (List)paramCollection;
/*      */     
/*  827 */     Object localObject = null;
/*  828 */     int i = localList.size() - 1;
/*  829 */     ForkJoinTask localForkJoinTask; for (int j = i; j >= 0; j--) {
/*  830 */       localForkJoinTask = (ForkJoinTask)localList.get(j);
/*  831 */       if (localForkJoinTask == null) {
/*  832 */         if (localObject == null) {
/*  833 */           localObject = new NullPointerException();
/*      */         }
/*  835 */       } else if (j != 0) {
/*  836 */         localForkJoinTask.fork();
/*  837 */       } else if ((localForkJoinTask.doInvoke() < -268435456) && (localObject == null))
/*  838 */         localObject = localForkJoinTask.getException();
/*      */     }
/*  840 */     for (j = 1; j <= i; j++) {
/*  841 */       localForkJoinTask = (ForkJoinTask)localList.get(j);
/*  842 */       if (localForkJoinTask != null) {
/*  843 */         if (localObject != null) {
/*  844 */           localForkJoinTask.cancel(false);
/*  845 */         } else if (localForkJoinTask.doJoin() < -268435456)
/*  846 */           localObject = localForkJoinTask.getException();
/*      */       }
/*      */     }
/*  849 */     if (localObject != null)
/*  850 */       rethrow((Throwable)localObject);
/*  851 */     return paramCollection;
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
/*      */   public boolean cancel(boolean paramBoolean)
/*      */   {
/*  882 */     return (setCompletion(-1073741824) & 0xF0000000) == -1073741824;
/*      */   }
/*      */   
/*      */   public final boolean isDone() {
/*  886 */     return this.status < 0;
/*      */   }
/*      */   
/*      */   public final boolean isCancelled() {
/*  890 */     return (this.status & 0xF0000000) == -1073741824;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isCompletedAbnormally()
/*      */   {
/*  899 */     return this.status < -268435456;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isCompletedNormally()
/*      */   {
/*  910 */     return (this.status & 0xF0000000) == -268435456;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Throwable getException()
/*      */   {
/*  921 */     int i = this.status & 0xF0000000;
/*      */     
/*      */ 
/*  924 */     return i == -1073741824 ? new CancellationException() : i >= -268435456 ? null : getThrowableException();
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
/*      */   public void completeExceptionally(Throwable paramThrowable)
/*      */   {
/*  942 */     setExceptionalCompletion(((paramThrowable instanceof RuntimeException)) || ((paramThrowable instanceof Error)) ? paramThrowable : new RuntimeException(paramThrowable));
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
/*      */   public void complete(V paramV)
/*      */   {
/*      */     try
/*      */     {
/*  962 */       setRawResult(paramV);
/*      */     } catch (Throwable localThrowable) {
/*  964 */       setExceptionalCompletion(localThrowable);
/*  965 */       return;
/*      */     }
/*  967 */     setCompletion(-268435456);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void quietlyComplete()
/*      */   {
/*  979 */     setCompletion(-268435456);
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
/*      */   public final V get()
/*      */     throws InterruptedException, ExecutionException
/*      */   {
/*  995 */     int i = (Thread.currentThread() instanceof ForkJoinWorkerThread) ? doJoin() : externalInterruptibleAwaitDone();
/*      */     
/*  997 */     if ((i &= 0xF0000000) == -1073741824)
/*  998 */       throw new CancellationException();
/*  999 */     Throwable localThrowable; if ((i == Integer.MIN_VALUE) && ((localThrowable = getThrowableException()) != null))
/* 1000 */       throw new ExecutionException(localThrowable);
/* 1001 */     return (V)getRawResult();
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
/*      */   public final V get(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException, ExecutionException, TimeoutException
/*      */   {
/* 1020 */     if (Thread.interrupted()) {
/* 1021 */       throw new InterruptedException();
/*      */     }
/*      */     
/* 1024 */     long l2 = paramTimeUnit.toNanos(paramLong);
/*      */     int i;
/* 1026 */     if (((i = this.status) >= 0) && (l2 > 0L)) {
/* 1027 */       long l3 = System.nanoTime() + l2;
/* 1028 */       ForkJoinPool localForkJoinPool2 = null;
/* 1029 */       ForkJoinPool.WorkQueue localWorkQueue = null;
/* 1030 */       Thread localThread = Thread.currentThread();
/* 1031 */       if ((localThread instanceof ForkJoinWorkerThread)) {
/* 1032 */         ForkJoinWorkerThread localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread;
/* 1033 */         localForkJoinPool2 = localForkJoinWorkerThread.pool;
/* 1034 */         localWorkQueue = localForkJoinWorkerThread.workQueue;
/* 1035 */         localForkJoinPool2.helpJoinOnce(localWorkQueue, this);
/*      */       } else { ForkJoinPool localForkJoinPool1;
/* 1037 */         if ((localForkJoinPool1 = ForkJoinPool.common) != null)
/* 1038 */           if ((this instanceof CountedCompleter)) {
/* 1039 */             localForkJoinPool1.externalHelpComplete((CountedCompleter)this, Integer.MAX_VALUE);
/* 1040 */           } else if (localForkJoinPool1.tryExternalUnpush(this))
/* 1041 */             doExec();
/*      */       }
/* 1043 */       int j = 0;
/* 1044 */       int k = 0;
/*      */       try {
/* 1046 */         while ((i = this.status) >= 0) {
/* 1047 */           if ((localWorkQueue != null) && (localWorkQueue.qlock < 0)) {
/* 1048 */             cancelIgnoringExceptions(this);
/* 1049 */           } else if (j == 0) {
/* 1050 */             if ((localForkJoinPool2 == null) || (localForkJoinPool2.tryCompensate(localForkJoinPool2.ctl)))
/* 1051 */               j = 1;
/*      */           } else {
/*      */             long l1;
/* 1054 */             if (((l1 = TimeUnit.NANOSECONDS.toMillis(l2)) > 0L) && 
/* 1055 */               (U.compareAndSwapInt(this, STATUS, i, i | 0x10000))) {
/* 1056 */               synchronized (this) {
/* 1057 */                 if (this.status >= 0) {
/*      */                   try {
/* 1059 */                     wait(l1);
/*      */                   } catch (InterruptedException localInterruptedException) {
/* 1061 */                     if (localForkJoinPool2 == null) {
/* 1062 */                       k = 1;
/*      */                     }
/*      */                   }
/*      */                 } else
/* 1066 */                   notifyAll();
/*      */               }
/*      */             }
/* 1069 */             if (((i = this.status) >= 0) && (k == 0))
/* 1070 */               if ((l2 = l3 - System.nanoTime()) <= 0L)
/*      */                 break;
/*      */           }
/*      */         }
/*      */       } finally {
/* 1075 */         if ((localForkJoinPool2 != null) && (j != 0))
/* 1076 */           localForkJoinPool2.incrementActiveCount();
/*      */       }
/* 1078 */       if (k != 0)
/* 1079 */         throw new InterruptedException();
/*      */     }
/* 1081 */     if ((i &= 0xF0000000) != -268435456)
/*      */     {
/* 1083 */       if (i == -1073741824)
/* 1084 */         throw new CancellationException();
/* 1085 */       if (i != Integer.MIN_VALUE)
/* 1086 */         throw new TimeoutException();
/* 1087 */       Throwable localThrowable; if ((localThrowable = getThrowableException()) != null)
/* 1088 */         throw new ExecutionException(localThrowable);
/*      */     }
/* 1090 */     return (V)getRawResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void quietlyJoin()
/*      */   {
/* 1100 */     doJoin();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void quietlyInvoke()
/*      */   {
/* 1109 */     doInvoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void helpQuiesce()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/* 1121 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/* 1122 */       ForkJoinWorkerThread localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread;
/* 1123 */       localForkJoinWorkerThread.pool.helpQuiescePool(localForkJoinWorkerThread.workQueue);
/*      */     }
/*      */     else {
/* 1126 */       ForkJoinPool.quiesceCommonPool();
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
/*      */   public void reinitialize()
/*      */   {
/* 1146 */     if ((this.status & 0xF0000000) == Integer.MIN_VALUE) {
/* 1147 */       clearExceptionalCompletion();
/*      */     } else {
/* 1149 */       this.status = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ForkJoinPool getPool()
/*      */   {
/* 1160 */     Thread localThread = Thread.currentThread();
/* 1161 */     return (localThread instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)localThread).pool : null;
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
/*      */   public static boolean inForkJoinPool()
/*      */   {
/* 1174 */     return Thread.currentThread() instanceof ForkJoinWorkerThread;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean tryUnfork()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1191 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)localThread).workQueue.tryUnpush(this) : ForkJoinPool.common.tryExternalUnpush(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int getQueuedTaskCount()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */     ForkJoinPool.WorkQueue localWorkQueue;
/*      */     
/*      */ 
/* 1204 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/* 1205 */       localWorkQueue = ((ForkJoinWorkerThread)localThread).workQueue;
/*      */     } else
/* 1207 */       localWorkQueue = ForkJoinPool.commonSubmitterQueue();
/* 1208 */     return localWorkQueue == null ? 0 : localWorkQueue.queueSize();
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
/*      */   public static int getSurplusQueuedTaskCount()
/*      */   {
/* 1225 */     return ForkJoinPool.getSurplusQueuedTaskCount();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract V getRawResult();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void setRawResult(V paramV);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean exec();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ForkJoinTask<?> peekNextLocalTask()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ForkJoinPool.WorkQueue localWorkQueue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1280 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/* 1281 */       localWorkQueue = ((ForkJoinWorkerThread)localThread).workQueue;
/*      */     } else
/* 1283 */       localWorkQueue = ForkJoinPool.commonSubmitterQueue();
/* 1284 */     return localWorkQueue == null ? null : localWorkQueue.peek();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ForkJoinTask<?> pollNextLocalTask()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1299 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)localThread).workQueue.nextLocalTask() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ForkJoinTask<?> pollTask()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1319 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool.nextTaskFor(localForkJoinWorkerThread.workQueue) : null;
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
/*      */   public final short getForkJoinTaskTag()
/*      */   {
/* 1332 */     return (short)this.status;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final short setForkJoinTaskTag(short paramShort)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/* 1344 */     while (!U.compareAndSwapInt(this, STATUS, i = this.status, i & 0xFFFF0000 | paramShort & 0xFFFF)) {}
/*      */     
/* 1346 */     return (short)i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean compareAndSetForkJoinTaskTag(short paramShort1, short paramShort2)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     do
/*      */     {
/* 1366 */       if ((short)(i = this.status) != paramShort1)
/* 1367 */         return false;
/* 1368 */     } while (!U.compareAndSwapInt(this, STATUS, i, i & 0xFFFF0000 | paramShort2 & 0xFFFF));
/*      */     
/* 1370 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   static final class AdaptedRunnable<T>
/*      */     extends ForkJoinTask<T>
/*      */     implements RunnableFuture<T>
/*      */   {
/*      */     final Runnable runnable;
/*      */     T result;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AdaptedRunnable(Runnable paramRunnable, T paramT)
/*      */     {
/* 1384 */       if (paramRunnable == null) throw new NullPointerException();
/* 1385 */       this.runnable = paramRunnable;
/* 1386 */       this.result = paramT; }
/*      */     
/* 1388 */     public final T getRawResult() { return (T)this.result; }
/* 1389 */     public final void setRawResult(T paramT) { this.result = paramT; }
/* 1390 */     public final boolean exec() { this.runnable.run();return true; }
/* 1391 */     public final void run() { invoke(); }
/*      */   }
/*      */   
/*      */   static final class AdaptedRunnableAction
/*      */     extends ForkJoinTask<Void> implements RunnableFuture<Void>
/*      */   {
/*      */     final Runnable runnable;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AdaptedRunnableAction(Runnable paramRunnable)
/*      */     {
/* 1402 */       if (paramRunnable == null) throw new NullPointerException();
/* 1403 */       this.runnable = paramRunnable; }
/*      */     
/* 1405 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/* 1407 */     public final boolean exec() { this.runnable.run();return true; }
/* 1408 */     public final void run() { invoke(); }
/*      */   }
/*      */   
/*      */   static final class RunnableExecuteAction extends ForkJoinTask<Void>
/*      */   {
/*      */     final Runnable runnable;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     RunnableExecuteAction(Runnable paramRunnable)
/*      */     {
/* 1418 */       if (paramRunnable == null) throw new NullPointerException();
/* 1419 */       this.runnable = paramRunnable; }
/*      */     
/* 1421 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/* 1423 */     public final boolean exec() { this.runnable.run();return true; }
/*      */     
/* 1425 */     void internalPropagateException(Throwable paramThrowable) { rethrow(paramThrowable); }
/*      */   }
/*      */   
/*      */   static final class AdaptedCallable<T>
/*      */     extends ForkJoinTask<T>
/*      */     implements RunnableFuture<T>
/*      */   {
/*      */     final Callable<? extends T> callable;
/*      */     T result;
/*      */     private static final long serialVersionUID = 2838392045355241008L;
/*      */     
/*      */     AdaptedCallable(Callable<? extends T> paramCallable)
/*      */     {
/* 1438 */       if (paramCallable == null) throw new NullPointerException();
/* 1439 */       this.callable = paramCallable; }
/*      */     
/* 1441 */     public final T getRawResult() { return (T)this.result; }
/* 1442 */     public final void setRawResult(T paramT) { this.result = paramT; }
/*      */     
/*      */     public final boolean exec() {
/* 1445 */       try { this.result = this.callable.call();
/* 1446 */         return true;
/*      */       } catch (Error localError) {
/* 1448 */         throw localError;
/*      */       } catch (RuntimeException localRuntimeException) {
/* 1450 */         throw localRuntimeException;
/*      */       } catch (Exception localException) {
/* 1452 */         throw new RuntimeException(localException);
/*      */       } }
/*      */     
/* 1455 */     public final void run() { invoke(); }
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
/*      */   public static ForkJoinTask<?> adapt(Runnable paramRunnable)
/*      */   {
/* 1468 */     return new AdaptedRunnableAction(paramRunnable);
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
/*      */   public static <T> ForkJoinTask<T> adapt(Runnable paramRunnable, T paramT)
/*      */   {
/* 1482 */     return new AdaptedRunnable(paramRunnable, paramT);
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
/*      */   public static <T> ForkJoinTask<T> adapt(Callable<? extends T> paramCallable)
/*      */   {
/* 1496 */     return new AdaptedCallable(paramCallable);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1513 */     paramObjectOutputStream.defaultWriteObject();
/* 1514 */     paramObjectOutputStream.writeObject(getException());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1526 */     paramObjectInputStream.defaultReadObject();
/* 1527 */     Object localObject = paramObjectInputStream.readObject();
/* 1528 */     if (localObject != null) {
/* 1529 */       setExceptionalCompletion((Throwable)localObject);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1537 */   private static final ReentrantLock exceptionTableLock = new ReentrantLock();
/* 1538 */   private static final ReferenceQueue<Object> exceptionTableRefQueue = new ReferenceQueue();
/* 1539 */   static { exceptionTable = new ExceptionNode[32];
/*      */     try {
/* 1541 */       U = Unsafe.getUnsafe();
/* 1542 */       Class localClass = ForkJoinTask.class;
/*      */       
/* 1544 */       STATUS = U.objectFieldOffset(localClass.getDeclaredField("status"));
/*      */     } catch (Exception localException) {
/* 1546 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final int EXCEPTION_MAP_CAPACITY = 32;
/*      */   private static final long serialVersionUID = -7721805057305804111L;
/*      */   private static final Unsafe U;
/*      */   private static final long STATUS;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ForkJoinTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */