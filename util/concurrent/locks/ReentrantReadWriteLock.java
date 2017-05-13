/*      */ package java.util.concurrent.locks;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.concurrent.TimeUnit;
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
/*      */ public class ReentrantReadWriteLock
/*      */   implements ReadWriteLock, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6992448646407690164L;
/*      */   private final ReadLock readerLock;
/*      */   private final WriteLock writerLock;
/*      */   final Sync sync;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long TID_OFFSET;
/*      */   
/*      */   public ReentrantReadWriteLock()
/*      */   {
/*  230 */     this(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ReentrantReadWriteLock(boolean paramBoolean)
/*      */   {
/*  240 */     this.sync = (paramBoolean ? new FairSync() : new NonfairSync());
/*  241 */     this.readerLock = new ReadLock(this);
/*  242 */     this.writerLock = new WriteLock(this);
/*      */   }
/*      */   
/*  245 */   public WriteLock writeLock() { return this.writerLock; }
/*  246 */   public ReadLock readLock() { return this.readerLock; }
/*      */   
/*      */ 
/*      */ 
/*      */   static abstract class Sync
/*      */     extends AbstractQueuedSynchronizer
/*      */   {
/*      */     private static final long serialVersionUID = 6317671515068378041L;
/*      */     
/*      */ 
/*      */     static final int SHARED_SHIFT = 16;
/*      */     
/*      */     static final int SHARED_UNIT = 65536;
/*      */     
/*      */     static final int MAX_COUNT = 65535;
/*      */     
/*      */     static final int EXCLUSIVE_MASK = 65535;
/*      */     
/*      */     private transient ThreadLocalHoldCounter readHolds;
/*      */     
/*      */     private transient HoldCounter cachedHoldCounter;
/*      */     
/*  268 */     static int sharedCount(int paramInt) { return paramInt >>> 16; }
/*      */     
/*  270 */     static int exclusiveCount(int paramInt) { return paramInt & 0xFFFF; }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     static final class HoldCounter
/*      */     {
/*  277 */       int count = 0;
/*      */       
/*  279 */       final long tid = ReentrantReadWriteLock.getThreadId(Thread.currentThread());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static final class ThreadLocalHoldCounter
/*      */       extends ThreadLocal<ReentrantReadWriteLock.Sync.HoldCounter>
/*      */     {
/*      */       public ReentrantReadWriteLock.Sync.HoldCounter initialValue()
/*      */       {
/*  289 */         return new ReentrantReadWriteLock.Sync.HoldCounter();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  334 */     private transient Thread firstReader = null;
/*      */     private transient int firstReaderHoldCount;
/*      */     
/*      */     Sync() {
/*  338 */       this.readHolds = new ThreadLocalHoldCounter();
/*  339 */       setState(getState());
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
/*      */     abstract boolean readerShouldBlock();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     abstract boolean writerShouldBlock();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected final boolean tryRelease(int paramInt)
/*      */     {
/*  370 */       if (!isHeldExclusively())
/*  371 */         throw new IllegalMonitorStateException();
/*  372 */       int i = getState() - paramInt;
/*  373 */       boolean bool = exclusiveCount(i) == 0;
/*  374 */       if (bool)
/*  375 */         setExclusiveOwnerThread(null);
/*  376 */       setState(i);
/*  377 */       return bool;
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
/*      */     protected final boolean tryAcquire(int paramInt)
/*      */     {
/*  392 */       Thread localThread = Thread.currentThread();
/*  393 */       int i = getState();
/*  394 */       int j = exclusiveCount(i);
/*  395 */       if (i != 0)
/*      */       {
/*  397 */         if ((j == 0) || (localThread != getExclusiveOwnerThread()))
/*  398 */           return false;
/*  399 */         if (j + exclusiveCount(paramInt) > 65535) {
/*  400 */           throw new Error("Maximum lock count exceeded");
/*      */         }
/*  402 */         setState(i + paramInt);
/*  403 */         return true;
/*      */       }
/*  405 */       if ((writerShouldBlock()) || 
/*  406 */         (!compareAndSetState(i, i + paramInt)))
/*  407 */         return false;
/*  408 */       setExclusiveOwnerThread(localThread);
/*  409 */       return true;
/*      */     }
/*      */     
/*      */     protected final boolean tryReleaseShared(int paramInt) {
/*  413 */       Thread localThread = Thread.currentThread();
/*  414 */       int j; if (this.firstReader == localThread)
/*      */       {
/*  416 */         if (this.firstReaderHoldCount == 1) {
/*  417 */           this.firstReader = null;
/*      */         } else
/*  419 */           this.firstReaderHoldCount -= 1;
/*      */       } else {
/*  421 */         HoldCounter localHoldCounter = this.cachedHoldCounter;
/*  422 */         if ((localHoldCounter == null) || (localHoldCounter.tid != ReentrantReadWriteLock.getThreadId(localThread)))
/*  423 */           localHoldCounter = (HoldCounter)this.readHolds.get();
/*  424 */         j = localHoldCounter.count;
/*  425 */         if (j <= 1) {
/*  426 */           this.readHolds.remove();
/*  427 */           if (j <= 0)
/*  428 */             throw unmatchedUnlockException();
/*      */         }
/*  430 */         localHoldCounter.count -= 1;
/*      */       }
/*      */       for (;;) {
/*  433 */         int i = getState();
/*  434 */         j = i - 65536;
/*  435 */         if (compareAndSetState(i, j))
/*      */         {
/*      */ 
/*      */ 
/*  439 */           return j == 0; }
/*      */       }
/*      */     }
/*      */     
/*      */     private IllegalMonitorStateException unmatchedUnlockException() {
/*  444 */       return new IllegalMonitorStateException("attempt to unlock read lock, not locked by current thread");
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
/*      */     protected final int tryAcquireShared(int paramInt)
/*      */     {
/*  464 */       Thread localThread = Thread.currentThread();
/*  465 */       int i = getState();
/*  466 */       if ((exclusiveCount(i) != 0) && 
/*  467 */         (getExclusiveOwnerThread() != localThread))
/*  468 */         return -1;
/*  469 */       int j = sharedCount(i);
/*  470 */       if ((!readerShouldBlock()) && (j < 65535))
/*      */       {
/*  472 */         if (compareAndSetState(i, i + 65536)) {
/*  473 */           if (j == 0) {
/*  474 */             this.firstReader = localThread;
/*  475 */             this.firstReaderHoldCount = 1;
/*  476 */           } else if (this.firstReader == localThread) {
/*  477 */             this.firstReaderHoldCount += 1;
/*      */           } else {
/*  479 */             HoldCounter localHoldCounter = this.cachedHoldCounter;
/*  480 */             if ((localHoldCounter == null) || (localHoldCounter.tid != ReentrantReadWriteLock.getThreadId(localThread))) {
/*  481 */               this.cachedHoldCounter = (localHoldCounter = (HoldCounter)this.readHolds.get());
/*  482 */             } else if (localHoldCounter.count == 0)
/*  483 */               this.readHolds.set(localHoldCounter);
/*  484 */             localHoldCounter.count += 1;
/*      */           }
/*  486 */           return 1;
/*      */         } }
/*  488 */       return fullTryAcquireShared(localThread);
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
/*      */     final int fullTryAcquireShared(Thread paramThread)
/*      */     {
/*  502 */       HoldCounter localHoldCounter = null;
/*      */       for (;;) {
/*  504 */         int i = getState();
/*  505 */         if (exclusiveCount(i) != 0) {
/*  506 */           if (getExclusiveOwnerThread() != paramThread) {
/*  507 */             return -1;
/*      */           }
/*      */         }
/*  510 */         else if (readerShouldBlock())
/*      */         {
/*  512 */           if (this.firstReader != paramThread)
/*      */           {
/*      */ 
/*  515 */             if (localHoldCounter == null) {
/*  516 */               localHoldCounter = this.cachedHoldCounter;
/*  517 */               if ((localHoldCounter == null) || (localHoldCounter.tid != ReentrantReadWriteLock.getThreadId(paramThread))) {
/*  518 */                 localHoldCounter = (HoldCounter)this.readHolds.get();
/*  519 */                 if (localHoldCounter.count == 0)
/*  520 */                   this.readHolds.remove();
/*      */               }
/*      */             }
/*  523 */             if (localHoldCounter.count == 0)
/*  524 */               return -1;
/*      */           }
/*      */         }
/*  527 */         if (sharedCount(i) == 65535)
/*  528 */           throw new Error("Maximum lock count exceeded");
/*  529 */         if (compareAndSetState(i, i + 65536)) {
/*  530 */           if (sharedCount(i) == 0) {
/*  531 */             this.firstReader = paramThread;
/*  532 */             this.firstReaderHoldCount = 1;
/*  533 */           } else if (this.firstReader == paramThread) {
/*  534 */             this.firstReaderHoldCount += 1;
/*      */           } else {
/*  536 */             if (localHoldCounter == null)
/*  537 */               localHoldCounter = this.cachedHoldCounter;
/*  538 */             if ((localHoldCounter == null) || (localHoldCounter.tid != ReentrantReadWriteLock.getThreadId(paramThread))) {
/*  539 */               localHoldCounter = (HoldCounter)this.readHolds.get();
/*  540 */             } else if (localHoldCounter.count == 0)
/*  541 */               this.readHolds.set(localHoldCounter);
/*  542 */             localHoldCounter.count += 1;
/*  543 */             this.cachedHoldCounter = localHoldCounter;
/*      */           }
/*  545 */           return 1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean tryWriteLock()
/*      */     {
/*  556 */       Thread localThread = Thread.currentThread();
/*  557 */       int i = getState();
/*  558 */       if (i != 0) {
/*  559 */         int j = exclusiveCount(i);
/*  560 */         if ((j == 0) || (localThread != getExclusiveOwnerThread()))
/*  561 */           return false;
/*  562 */         if (j == 65535)
/*  563 */           throw new Error("Maximum lock count exceeded");
/*      */       }
/*  565 */       if (!compareAndSetState(i, i + 1))
/*  566 */         return false;
/*  567 */       setExclusiveOwnerThread(localThread);
/*  568 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean tryReadLock()
/*      */     {
/*  577 */       Thread localThread = Thread.currentThread();
/*      */       for (;;) {
/*  579 */         int i = getState();
/*  580 */         if ((exclusiveCount(i) != 0) && 
/*  581 */           (getExclusiveOwnerThread() != localThread))
/*  582 */           return false;
/*  583 */         int j = sharedCount(i);
/*  584 */         if (j == 65535)
/*  585 */           throw new Error("Maximum lock count exceeded");
/*  586 */         if (compareAndSetState(i, i + 65536)) {
/*  587 */           if (j == 0) {
/*  588 */             this.firstReader = localThread;
/*  589 */             this.firstReaderHoldCount = 1;
/*  590 */           } else if (this.firstReader == localThread) {
/*  591 */             this.firstReaderHoldCount += 1;
/*      */           } else {
/*  593 */             HoldCounter localHoldCounter = this.cachedHoldCounter;
/*  594 */             if ((localHoldCounter == null) || (localHoldCounter.tid != ReentrantReadWriteLock.getThreadId(localThread))) {
/*  595 */               this.cachedHoldCounter = (localHoldCounter = (HoldCounter)this.readHolds.get());
/*  596 */             } else if (localHoldCounter.count == 0)
/*  597 */               this.readHolds.set(localHoldCounter);
/*  598 */             localHoldCounter.count += 1;
/*      */           }
/*  600 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     protected final boolean isHeldExclusively()
/*      */     {
/*  608 */       return getExclusiveOwnerThread() == Thread.currentThread();
/*      */     }
/*      */     
/*      */ 
/*      */     final AbstractQueuedSynchronizer.ConditionObject newCondition()
/*      */     {
/*  614 */       return new AbstractQueuedSynchronizer.ConditionObject(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final Thread getOwner()
/*      */     {
/*  621 */       return exclusiveCount(getState()) == 0 ? null : getExclusiveOwnerThread();
/*      */     }
/*      */     
/*      */     final int getReadLockCount() {
/*  625 */       return sharedCount(getState());
/*      */     }
/*      */     
/*      */     final boolean isWriteLocked() {
/*  629 */       return exclusiveCount(getState()) != 0;
/*      */     }
/*      */     
/*      */     final int getWriteHoldCount() {
/*  633 */       return isHeldExclusively() ? exclusiveCount(getState()) : 0;
/*      */     }
/*      */     
/*      */     final int getReadHoldCount() {
/*  637 */       if (getReadLockCount() == 0) {
/*  638 */         return 0;
/*      */       }
/*  640 */       Thread localThread = Thread.currentThread();
/*  641 */       if (this.firstReader == localThread) {
/*  642 */         return this.firstReaderHoldCount;
/*      */       }
/*  644 */       HoldCounter localHoldCounter = this.cachedHoldCounter;
/*  645 */       if ((localHoldCounter != null) && (localHoldCounter.tid == ReentrantReadWriteLock.getThreadId(localThread))) {
/*  646 */         return localHoldCounter.count;
/*      */       }
/*  648 */       int i = ((HoldCounter)this.readHolds.get()).count;
/*  649 */       if (i == 0) this.readHolds.remove();
/*  650 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void readObject(ObjectInputStream paramObjectInputStream)
/*      */       throws IOException, ClassNotFoundException
/*      */     {
/*  658 */       paramObjectInputStream.defaultReadObject();
/*  659 */       this.readHolds = new ThreadLocalHoldCounter();
/*  660 */       setState(0);
/*      */     }
/*      */     
/*  663 */     final int getCount() { return getState(); }
/*      */   }
/*      */   
/*      */   static final class NonfairSync extends ReentrantReadWriteLock.Sync
/*      */   {
/*      */     private static final long serialVersionUID = -8159625535654395037L;
/*      */     
/*      */     final boolean writerShouldBlock()
/*      */     {
/*  672 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean readerShouldBlock()
/*      */     {
/*  682 */       return apparentlyFirstQueuedIsExclusive();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FairSync extends ReentrantReadWriteLock.Sync
/*      */   {
/*      */     private static final long serialVersionUID = -2274990926593161451L;
/*      */     
/*      */     final boolean writerShouldBlock()
/*      */     {
/*  692 */       return hasQueuedPredecessors();
/*      */     }
/*      */     
/*  695 */     final boolean readerShouldBlock() { return hasQueuedPredecessors(); }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class ReadLock
/*      */     implements Lock, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5992448646407690164L;
/*      */     
/*      */ 
/*      */     private final ReentrantReadWriteLock.Sync sync;
/*      */     
/*      */ 
/*      */ 
/*      */     protected ReadLock(ReentrantReadWriteLock paramReentrantReadWriteLock)
/*      */     {
/*  713 */       this.sync = paramReentrantReadWriteLock.sync;
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
/*      */     public void lock()
/*      */     {
/*  727 */       this.sync.acquireShared(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void lockInterruptibly()
/*      */       throws InterruptedException
/*      */     {
/*  772 */       this.sync.acquireSharedInterruptibly(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean tryLock()
/*      */     {
/*  799 */       return this.sync.tryReadLock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean tryLock(long paramLong, TimeUnit paramTimeUnit)
/*      */       throws InterruptedException
/*      */     {
/*  871 */       return this.sync.tryAcquireSharedNanos(1, paramTimeUnit.toNanos(paramLong));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void unlock()
/*      */     {
/*  881 */       this.sync.releaseShared(1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Condition newCondition()
/*      */     {
/*  891 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  902 */       int i = this.sync.getReadLockCount();
/*  903 */       return super.toString() + "[Read locks = " + i + "]";
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class WriteLock
/*      */     implements Lock, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4992448646407690164L;
/*      */     
/*      */ 
/*      */     private final ReentrantReadWriteLock.Sync sync;
/*      */     
/*      */ 
/*      */ 
/*      */     protected WriteLock(ReentrantReadWriteLock paramReentrantReadWriteLock)
/*      */     {
/*  922 */       this.sync = paramReentrantReadWriteLock.sync;
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
/*      */     public void lock()
/*      */     {
/*  943 */       this.sync.acquire(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void lockInterruptibly()
/*      */       throws InterruptedException
/*      */     {
/*  998 */       this.sync.acquireInterruptibly(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean tryLock()
/*      */     {
/* 1031 */       return this.sync.tryWriteLock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean tryLock(long paramLong, TimeUnit paramTimeUnit)
/*      */       throws InterruptedException
/*      */     {
/* 1115 */       return this.sync.tryAcquireNanos(1, paramTimeUnit.toNanos(paramLong));
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
/*      */     public void unlock()
/*      */     {
/* 1131 */       this.sync.release(1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Condition newCondition()
/*      */     {
/* 1178 */       return this.sync.newCondition();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1190 */       Thread localThread = this.sync.getOwner();
/*      */       
/*      */ 
/* 1193 */       return super.toString() + (localThread == null ? "[Unlocked]" : new StringBuilder().append("[Locked by thread ").append(localThread.getName()).append("]").toString());
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
/*      */     public boolean isHeldByCurrentThread()
/*      */     {
/* 1206 */       return this.sync.isHeldExclusively();
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
/*      */     public int getHoldCount()
/*      */     {
/* 1220 */       return this.sync.getWriteHoldCount();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isFair()
/*      */   {
/* 1232 */     return this.sync instanceof FairSync;
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
/*      */   protected Thread getOwner()
/*      */   {
/* 1249 */     return this.sync.getOwner();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getReadLockCount()
/*      */   {
/* 1259 */     return this.sync.getReadLockCount();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWriteLocked()
/*      */   {
/* 1271 */     return this.sync.isWriteLocked();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWriteLockedByCurrentThread()
/*      */   {
/* 1281 */     return this.sync.isHeldExclusively();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWriteHoldCount()
/*      */   {
/* 1293 */     return this.sync.getWriteHoldCount();
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
/*      */   public int getReadHoldCount()
/*      */   {
/* 1306 */     return this.sync.getReadHoldCount();
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
/*      */   protected Collection<Thread> getQueuedWriterThreads()
/*      */   {
/* 1321 */     return this.sync.getExclusiveQueuedThreads();
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
/*      */   protected Collection<Thread> getQueuedReaderThreads()
/*      */   {
/* 1336 */     return this.sync.getSharedQueuedThreads();
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
/*      */   public final boolean hasQueuedThreads()
/*      */   {
/* 1350 */     return this.sync.hasQueuedThreads();
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
/*      */   public final boolean hasQueuedThread(Thread paramThread)
/*      */   {
/* 1365 */     return this.sync.isQueued(paramThread);
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
/*      */   public final int getQueueLength()
/*      */   {
/* 1379 */     return this.sync.getQueueLength();
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
/*      */   protected Collection<Thread> getQueuedThreads()
/*      */   {
/* 1394 */     return this.sync.getQueuedThreads();
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
/*      */   public boolean hasWaiters(Condition paramCondition)
/*      */   {
/* 1413 */     if (paramCondition == null)
/* 1414 */       throw new NullPointerException();
/* 1415 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 1416 */       throw new IllegalArgumentException("not owner");
/* 1417 */     return this.sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*      */   public int getWaitQueueLength(Condition paramCondition)
/*      */   {
/* 1436 */     if (paramCondition == null)
/* 1437 */       throw new NullPointerException();
/* 1438 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 1439 */       throw new IllegalArgumentException("not owner");
/* 1440 */     return this.sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/*      */   protected Collection<Thread> getWaitingThreads(Condition paramCondition)
/*      */   {
/* 1461 */     if (paramCondition == null)
/* 1462 */       throw new NullPointerException();
/* 1463 */     if (!(paramCondition instanceof AbstractQueuedSynchronizer.ConditionObject))
/* 1464 */       throw new IllegalArgumentException("not owner");
/* 1465 */     return this.sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)paramCondition);
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
/* 1478 */     int i = this.sync.getCount();
/* 1479 */     int j = Sync.exclusiveCount(i);
/* 1480 */     int k = Sync.sharedCount(i);
/*      */     
/* 1482 */     return super.toString() + "[Write locks = " + j + ", Read locks = " + k + "]";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final long getThreadId(Thread paramThread)
/*      */   {
/* 1493 */     return UNSAFE.getLongVolatile(paramThread, TID_OFFSET);
/*      */   }
/*      */   
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 1501 */       UNSAFE = Unsafe.getUnsafe();
/* 1502 */       Class localClass = Thread.class;
/*      */       
/* 1504 */       TID_OFFSET = UNSAFE.objectFieldOffset(localClass.getDeclaredField("tid"));
/*      */     } catch (Exception localException) {
/* 1506 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/locks/ReentrantReadWriteLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */