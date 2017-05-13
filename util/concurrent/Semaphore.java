/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Semaphore
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3222578661600680210L;
/*     */   private final Sync sync;
/*     */   
/*     */   static abstract class Sync
/*     */     extends AbstractQueuedSynchronizer
/*     */   {
/*     */     private static final long serialVersionUID = 1192457210091910933L;
/*     */     
/*     */     Sync(int paramInt)
/*     */     {
/* 170 */       setState(paramInt);
/*     */     }
/*     */     
/*     */     final int getPermits() {
/* 174 */       return getState();
/*     */     }
/*     */     
/*     */     final int nonfairTryAcquireShared(int paramInt) {
/*     */       for (;;) {
/* 179 */         int i = getState();
/* 180 */         int j = i - paramInt;
/* 181 */         if ((j < 0) || 
/* 182 */           (compareAndSetState(i, j)))
/* 183 */           return j;
/*     */       }
/*     */     }
/*     */     
/*     */     protected final boolean tryReleaseShared(int paramInt) {
/*     */       for (;;) {
/* 189 */         int i = getState();
/* 190 */         int j = i + paramInt;
/* 191 */         if (j < i)
/* 192 */           throw new Error("Maximum permit count exceeded");
/* 193 */         if (compareAndSetState(i, j))
/* 194 */           return true;
/*     */       }
/*     */     }
/*     */     
/*     */     final void reducePermits(int paramInt) {
/*     */       for (;;) {
/* 200 */         int i = getState();
/* 201 */         int j = i - paramInt;
/* 202 */         if (j > i)
/* 203 */           throw new Error("Permit count underflow");
/* 204 */         if (compareAndSetState(i, j))
/* 205 */           return;
/*     */       }
/*     */     }
/*     */     
/*     */     final int drainPermits() {
/*     */       for (;;) {
/* 211 */         int i = getState();
/* 212 */         if ((i == 0) || (compareAndSetState(i, 0))) {
/* 213 */           return i;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static final class NonfairSync extends Semaphore.Sync
/*     */   {
/*     */     private static final long serialVersionUID = -2694183684443567898L;
/*     */     
/*     */     NonfairSync(int paramInt)
/*     */     {
/* 225 */       super();
/*     */     }
/*     */     
/*     */     protected int tryAcquireShared(int paramInt) {
/* 229 */       return nonfairTryAcquireShared(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class FairSync
/*     */     extends Semaphore.Sync
/*     */   {
/*     */     private static final long serialVersionUID = 2014338818796000944L;
/*     */     
/*     */     FairSync(int paramInt)
/*     */     {
/* 240 */       super();
/*     */     }
/*     */     
/*     */     protected int tryAcquireShared(int paramInt) {
/*     */       for (;;) {
/* 245 */         if (hasQueuedPredecessors())
/* 246 */           return -1;
/* 247 */         int i = getState();
/* 248 */         int j = i - paramInt;
/* 249 */         if ((j < 0) || 
/* 250 */           (compareAndSetState(i, j))) {
/* 251 */           return j;
/*     */         }
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
/*     */   public Semaphore(int paramInt)
/*     */   {
/* 265 */     this.sync = new NonfairSync(paramInt);
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
/*     */   public Semaphore(int paramInt, boolean paramBoolean)
/*     */   {
/* 280 */     this.sync = (paramBoolean ? new FairSync(paramInt) : new NonfairSync(paramInt));
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
/*     */   public void acquire()
/*     */     throws InterruptedException
/*     */   {
/* 312 */     this.sync.acquireSharedInterruptibly(1);
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
/*     */   public void acquireUninterruptibly()
/*     */   {
/* 335 */     this.sync.acquireShared(1);
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
/*     */   public boolean tryAcquire()
/*     */   {
/* 363 */     return this.sync.nonfairTryAcquireShared(1) >= 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean tryAcquire(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 409 */     return this.sync.tryAcquireSharedNanos(1, paramTimeUnit.toNanos(paramLong));
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
/*     */   public void release()
/*     */   {
/* 426 */     this.sync.releaseShared(1);
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
/*     */ 
/*     */ 
/*     */   public void acquire(int paramInt)
/*     */     throws InterruptedException
/*     */   {
/* 466 */     if (paramInt < 0) throw new IllegalArgumentException();
/* 467 */     this.sync.acquireSharedInterruptibly(paramInt);
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
/*     */   public void acquireUninterruptibly(int paramInt)
/*     */   {
/* 493 */     if (paramInt < 0) throw new IllegalArgumentException();
/* 494 */     this.sync.acquireShared(paramInt);
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
/*     */   public boolean tryAcquire(int paramInt)
/*     */   {
/* 525 */     if (paramInt < 0) throw new IllegalArgumentException();
/* 526 */     return this.sync.nonfairTryAcquireShared(paramInt) >= 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean tryAcquire(int paramInt, long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 581 */     if (paramInt < 0) throw new IllegalArgumentException();
/* 582 */     return this.sync.tryAcquireSharedNanos(paramInt, paramTimeUnit.toNanos(paramLong));
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
/*     */   public void release(int paramInt)
/*     */   {
/* 608 */     if (paramInt < 0) throw new IllegalArgumentException();
/* 609 */     this.sync.releaseShared(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int availablePermits()
/*     */   {
/* 620 */     return this.sync.getPermits();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int drainPermits()
/*     */   {
/* 629 */     return this.sync.drainPermits();
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
/*     */   protected void reducePermits(int paramInt)
/*     */   {
/* 643 */     if (paramInt < 0) throw new IllegalArgumentException();
/* 644 */     this.sync.reducePermits(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFair()
/*     */   {
/* 653 */     return this.sync instanceof FairSync;
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
/*     */   public final boolean hasQueuedThreads()
/*     */   {
/* 667 */     return this.sync.hasQueuedThreads();
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
/*     */   public final int getQueueLength()
/*     */   {
/* 680 */     return this.sync.getQueueLength();
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
/*     */   protected Collection<Thread> getQueuedThreads()
/*     */   {
/* 694 */     return this.sync.getQueuedThreads();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 705 */     return super.toString() + "[Permits = " + this.sync.getPermits() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/Semaphore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */