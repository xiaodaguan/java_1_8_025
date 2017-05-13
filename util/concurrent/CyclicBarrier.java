/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CyclicBarrier
/*     */ {
/*     */   private static class Generation
/*     */   {
/* 152 */     boolean broken = false;
/*     */   }
/*     */   
/*     */ 
/* 156 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   
/* 158 */   private final Condition trip = this.lock.newCondition();
/*     */   
/*     */   private final int parties;
/*     */   
/*     */   private final Runnable barrierCommand;
/*     */   
/* 164 */   private Generation generation = new Generation(null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int count;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void nextGeneration()
/*     */   {
/* 179 */     this.trip.signalAll();
/*     */     
/* 181 */     this.count = this.parties;
/* 182 */     this.generation = new Generation(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void breakBarrier()
/*     */   {
/* 190 */     this.generation.broken = true;
/* 191 */     this.count = this.parties;
/* 192 */     this.trip.signalAll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int dowait(boolean paramBoolean, long paramLong)
/*     */     throws InterruptedException, BrokenBarrierException, TimeoutException
/*     */   {
/* 201 */     ReentrantLock localReentrantLock = this.lock;
/* 202 */     localReentrantLock.lock();
/*     */     try {
/* 204 */       Generation localGeneration = this.generation;
/*     */       
/* 206 */       if (localGeneration.broken) {
/* 207 */         throw new BrokenBarrierException();
/*     */       }
/* 209 */       if (Thread.interrupted()) {
/* 210 */         breakBarrier();
/* 211 */         throw new InterruptedException();
/*     */       }
/*     */       
/* 214 */       InterruptedException localInterruptedException1 = --this.count;
/* 215 */       if (localInterruptedException1 == 0) {
/* 216 */         int i = 0;
/*     */         try {
/* 218 */           Runnable localRunnable = this.barrierCommand;
/* 219 */           if (localRunnable != null)
/* 220 */             localRunnable.run();
/* 221 */           i = 1;
/* 222 */           nextGeneration();
/* 223 */           int j = 0;
/*     */           
/* 225 */           if (i == 0) {
/* 226 */             breakBarrier();
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 261 */           return j;
/*     */         }
/*     */         finally
/*     */         {
/* 225 */           if (i == 0) {
/* 226 */             breakBarrier();
/*     */           }
/*     */         }
/*     */       }
/*     */       do
/*     */       {
/*     */         try {
/* 233 */           if (!paramBoolean) {
/* 234 */             this.trip.await();
/* 235 */           } else if (paramLong > 0L)
/* 236 */             paramLong = this.trip.awaitNanos(paramLong);
/*     */         } catch (InterruptedException localInterruptedException2) {
/* 238 */           if ((localGeneration == this.generation) && (!localGeneration.broken)) {
/* 239 */             breakBarrier();
/* 240 */             throw localInterruptedException2;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 245 */           Thread.currentThread().interrupt();
/*     */         }
/*     */         
/*     */ 
/* 249 */         if (localGeneration.broken) {
/* 250 */           throw new BrokenBarrierException();
/*     */         }
/* 252 */         if (localGeneration != this.generation) {
/* 253 */           return localInterruptedException1;
/*     */         }
/* 255 */       } while ((!paramBoolean) || (paramLong > 0L));
/* 256 */       breakBarrier();
/* 257 */       throw new TimeoutException();
/*     */     }
/*     */     finally
/*     */     {
/* 261 */       localReentrantLock.unlock();
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
/*     */   public CyclicBarrier(int paramInt, Runnable paramRunnable)
/*     */   {
/* 278 */     if (paramInt <= 0) throw new IllegalArgumentException();
/* 279 */     this.parties = paramInt;
/* 280 */     this.count = paramInt;
/* 281 */     this.barrierCommand = paramRunnable;
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
/*     */   public CyclicBarrier(int paramInt)
/*     */   {
/* 294 */     this(paramInt, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getParties()
/*     */   {
/* 303 */     return this.parties;
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
/*     */ 
/*     */ 
/*     */   public int await()
/*     */     throws InterruptedException, BrokenBarrierException
/*     */   {
/*     */     try
/*     */     {
/* 362 */       return dowait(false, 0L);
/*     */     } catch (TimeoutException localTimeoutException) {
/* 364 */       throw new Error(localTimeoutException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int await(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException, BrokenBarrierException, TimeoutException
/*     */   {
/* 435 */     return dowait(true, paramTimeUnit.toNanos(paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBroken()
/*     */   {
/* 447 */     ReentrantLock localReentrantLock = this.lock;
/* 448 */     localReentrantLock.lock();
/*     */     try {
/* 450 */       return this.generation.broken;
/*     */     } finally {
/* 452 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void reset()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 9	java/util/concurrent/CyclicBarrier:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   4: astore_1
/*     */     //   5: aload_1
/*     */     //   6: invokevirtual 10	java/util/concurrent/locks/ReentrantLock:lock	()V
/*     */     //   9: aload_0
/*     */     //   10: invokespecial 14	java/util/concurrent/CyclicBarrier:breakBarrier	()V
/*     */     //   13: aload_0
/*     */     //   14: invokespecial 19	java/util/concurrent/CyclicBarrier:nextGeneration	()V
/*     */     //   17: aload_1
/*     */     //   18: invokevirtual 20	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   21: goto +10 -> 31
/*     */     //   24: astore_2
/*     */     //   25: aload_1
/*     */     //   26: invokevirtual 20	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   29: aload_2
/*     */     //   30: athrow
/*     */     //   31: return
/*     */     // Line number table:
/*     */     //   Java source line #466	-> byte code offset #0
/*     */     //   Java source line #467	-> byte code offset #5
/*     */     //   Java source line #469	-> byte code offset #9
/*     */     //   Java source line #470	-> byte code offset #13
/*     */     //   Java source line #472	-> byte code offset #17
/*     */     //   Java source line #473	-> byte code offset #21
/*     */     //   Java source line #472	-> byte code offset #24
/*     */     //   Java source line #474	-> byte code offset #31
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	32	0	this	CyclicBarrier
/*     */     //   4	22	1	localReentrantLock	ReentrantLock
/*     */     //   24	6	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   9	17	24	finally
/*     */   }
/*     */   
/*     */   public int getNumberWaiting()
/*     */   {
/* 483 */     ReentrantLock localReentrantLock = this.lock;
/* 484 */     localReentrantLock.lock();
/*     */     try {
/* 486 */       return this.parties - this.count;
/*     */     } finally {
/* 488 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/CyclicBarrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */