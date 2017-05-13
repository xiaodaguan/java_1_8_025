/*      */ package java.util.concurrent.locks;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StampedLock
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6001602636862214147L;
/*  272 */   private static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */   
/*      */ 
/*  275 */   private static final int SPINS = NCPU > 1 ? 64 : 0;
/*      */   
/*      */ 
/*  278 */   private static final int HEAD_SPINS = NCPU > 1 ? 1024 : 0;
/*      */   
/*      */ 
/*  281 */   private static final int MAX_HEAD_SPINS = NCPU > 1 ? 65536 : 0;
/*      */   private static final int OVERFLOW_YIELD_RATE = 7;
/*      */   private static final int LG_READERS = 7;
/*      */   private static final long RUNIT = 1L;
/*      */   private static final long WBIT = 128L;
/*      */   private static final long RBITS = 127L;
/*      */   private static final long RFULL = 126L;
/*      */   private static final long ABITS = 255L;
/*      */   private static final long SBITS = -128L;
/*      */   private static final long ORIGIN = 256L;
/*      */   private static final long INTERRUPTED = 1L;
/*      */   private static final int WAITING = -1;
/*      */   private static final int CANCELLED = 1;
/*      */   private static final int RMODE = 0;
/*      */   private static final int WMODE = 1;
/*      */   private volatile transient WNode whead;
/*      */   private volatile transient WNode wtail;
/*      */   transient ReadLockView readLockView;
/*      */   transient WriteLockView writeLockView;
/*      */   transient ReadWriteLockView readWriteLockView;
/*      */   private volatile transient long state;
/*      */   private transient int readerOverflow;
/*      */   private static final Unsafe U;
/*      */   private static final long STATE;
/*      */   private static final long WHEAD;
/*      */   private static final long WTAIL;
/*      */   private static final long WNEXT;
/*      */   private static final long WSTATUS;
/*      */   private static final long WCOWAIT;
/*      */   private static final long PARKBLOCKER;
/*      */   
/*      */   static final class WNode { volatile WNode prev;
/*      */     volatile WNode next;
/*      */     volatile WNode cowait;
/*      */     volatile Thread thread;
/*      */     volatile int status;
/*      */     final int mode;
/*      */     
/*  319 */     WNode(int paramInt, WNode paramWNode) { this.mode = paramInt;this.prev = paramWNode;
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
/*      */   public StampedLock()
/*      */   {
/*  341 */     this.state = 256L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public long writeLock()
/*      */   {
/*      */     long l1;
/*      */     
/*      */ 
/*      */     long l2;
/*      */     
/*      */ 
/*  354 */     return (((l1 = this.state) & 0xFF) == 0L) && (U.compareAndSwapLong(this, STATE, l1, l2 = l1 + 128L)) ? l2 : acquireWrite(false, 0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public long tryWriteLock()
/*      */   {
/*      */     long l1;
/*      */     
/*      */ 
/*      */     long l2;
/*      */     
/*  366 */     return (((l1 = this.state) & 0xFF) == 0L) && (U.compareAndSwapLong(this, STATE, l1, l2 = l1 + 128L)) ? l2 : 0L;
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
/*      */   public long tryWriteLock(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  385 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*  386 */     if (!Thread.interrupted()) {
/*      */       long l2;
/*  388 */       if ((l2 = tryWriteLock()) != 0L)
/*  389 */         return l2;
/*  390 */       if (l1 <= 0L)
/*  391 */         return 0L;
/*  392 */       long l3; if ((l3 = System.nanoTime() + l1) == 0L)
/*  393 */         l3 = 1L;
/*  394 */       if ((l2 = acquireWrite(true, l3)) != 1L)
/*  395 */         return l2;
/*      */     }
/*  397 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long writeLockInterruptibly()
/*      */     throws InterruptedException
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  412 */     if ((!Thread.interrupted()) && 
/*  413 */       ((l = acquireWrite(true, 0L)) != 1L))
/*  414 */       return l;
/*  415 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long readLock()
/*      */   {
/*  425 */     long l1 = this.state;
/*      */     
/*      */     long l2;
/*  428 */     return (this.whead == this.wtail) && ((l1 & 0xFF) < 126L) && (U.compareAndSwapLong(this, STATE, l1, l2 = l1 + 1L)) ? l2 : acquireRead(false, 0L);
/*      */   }
/*      */   
/*      */ 
/*      */   public long tryReadLock()
/*      */   {
/*      */     for (;;)
/*      */     {
/*      */       long l1;
/*      */       
/*      */       long l2;
/*      */       
/*  440 */       if ((l2 = (l1 = this.state) & 0xFF) == 128L)
/*  441 */         return 0L;
/*  442 */       long l3; if (l2 < 126L) {
/*  443 */         if (U.compareAndSwapLong(this, STATE, l1, l3 = l1 + 1L)) {
/*  444 */           return l3;
/*      */         }
/*  446 */       } else if ((l3 = tryIncReaderOverflow(l1)) != 0L) {
/*  447 */         return l3;
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
/*      */   public long tryReadLock(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  467 */     long l5 = paramTimeUnit.toNanos(paramLong);
/*  468 */     if (!Thread.interrupted()) { long l1;
/*  469 */       long l2; long l3; if ((l2 = (l1 = this.state) & 0xFF) != 128L) {
/*  470 */         if (l2 < 126L) {
/*  471 */           if (U.compareAndSwapLong(this, STATE, l1, l3 = l1 + 1L)) {
/*  472 */             return l3;
/*      */           }
/*  474 */         } else if ((l3 = tryIncReaderOverflow(l1)) != 0L)
/*  475 */           return l3;
/*      */       }
/*  477 */       if (l5 <= 0L)
/*  478 */         return 0L;
/*  479 */       long l4; if ((l4 = System.nanoTime() + l5) == 0L)
/*  480 */         l4 = 1L;
/*  481 */       if ((l3 = acquireRead(true, l4)) != 1L)
/*  482 */         return l3;
/*      */     }
/*  484 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long readLockInterruptibly()
/*      */     throws InterruptedException
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  499 */     if ((!Thread.interrupted()) && 
/*  500 */       ((l = acquireRead(true, 0L)) != 1L))
/*  501 */       return l;
/*  502 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public long tryOptimisticRead()
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*  513 */     return ((l = this.state) & 0x80) == 0L ? l & 0xFFFFFFFFFFFFFF80 : 0L;
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
/*      */   public boolean validate(long paramLong)
/*      */   {
/*  529 */     U.loadFence();
/*  530 */     return (paramLong & 0xFFFFFFFFFFFFFF80) == (this.state & 0xFFFFFFFFFFFFFF80);
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
/*      */   public void unlockWrite(long paramLong)
/*      */   {
/*  543 */     if ((this.state != paramLong) || ((paramLong & 0x80) == 0L))
/*  544 */       throw new IllegalMonitorStateException();
/*  545 */     this.state = (paramLong += 128L == 0L ? 256L : paramLong);
/*  546 */     WNode localWNode; if (((localWNode = this.whead) != null) && (localWNode.status != 0)) {
/*  547 */       release(localWNode);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void unlockRead(long paramLong)
/*      */   {
/*      */     for (;;)
/*      */     {
/*      */       long l1;
/*      */       
/*      */       long l2;
/*      */       
/*  561 */       if ((((l1 = this.state) & 0xFFFFFFFFFFFFFF80) != (paramLong & 0xFFFFFFFFFFFFFF80)) || ((paramLong & 0xFF) == 0L) || ((l2 = l1 & 0xFF) == 0L) || (l2 == 128L))
/*      */       {
/*  563 */         throw new IllegalMonitorStateException(); }
/*  564 */       if (l2 < 126L) {
/*  565 */         if (U.compareAndSwapLong(this, STATE, l1, l1 - 1L)) { WNode localWNode;
/*  566 */           if ((l2 == 1L) && ((localWNode = this.whead) != null) && (localWNode.status != 0)) {
/*  567 */             release(localWNode);
/*      */           }
/*      */         }
/*      */       }
/*  571 */       else if (tryDecReaderOverflow(l1) != 0L) {
/*      */         break;
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
/*      */   public void unlock(long paramLong)
/*      */   {
/*  585 */     long l1 = paramLong & 0xFF;
/*  586 */     long l3; long l2; while ((((l3 = this.state) & 0xFFFFFFFFFFFFFF80) == (paramLong & 0xFFFFFFFFFFFFFF80)) && 
/*  587 */       ((l2 = l3 & 0xFF) != 0L)) {
/*      */       WNode localWNode;
/*  589 */       if (l2 == 128L) {
/*  590 */         if (l1 == l2)
/*      */         {
/*  592 */           this.state = (l3 += 128L == 0L ? 256L : l3);
/*  593 */           if (((localWNode = this.whead) != null) && (localWNode.status != 0)) {
/*  594 */             release(localWNode);
/*      */           }
/*      */         }
/*  597 */       } else if ((l1 != 0L) && (l1 < 128L))
/*      */       {
/*  599 */         if (l2 < 126L) {
/*  600 */           if (U.compareAndSwapLong(this, STATE, l3, l3 - 1L)) {
/*  601 */             if ((l2 == 1L) && ((localWNode = this.whead) != null) && (localWNode.status != 0)) {
/*  602 */               release(localWNode);
/*      */             }
/*      */           }
/*      */         }
/*  606 */         else if (tryDecReaderOverflow(l3) != 0L)
/*  607 */           return; }
/*      */     }
/*  609 */     throw new IllegalMonitorStateException();
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
/*      */   public long tryConvertToWriteLock(long paramLong)
/*      */   {
/*  625 */     long l1 = paramLong & 0xFF;
/*  626 */     long l3; while (((l3 = this.state) & 0xFFFFFFFFFFFFFF80) == (paramLong & 0xFFFFFFFFFFFFFF80)) { long l2;
/*  627 */       long l4; if ((l2 = l3 & 0xFF) == 0L) {
/*  628 */         if (l1 == 0L)
/*      */         {
/*  630 */           if (U.compareAndSwapLong(this, STATE, l3, l4 = l3 + 128L))
/*  631 */             return l4;
/*      */         }
/*  633 */       } else if (l2 == 128L) {
/*  634 */         if (l1 == l2)
/*      */         {
/*  636 */           return paramLong;
/*      */         }
/*  638 */       } else if ((l2 == 1L) && (l1 != 0L)) {
/*  639 */         if (U.compareAndSwapLong(this, STATE, l3, l4 = l3 - 1L + 128L))
/*      */         {
/*  641 */           return l4;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  646 */     return 0L;
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
/*      */   public long tryConvertToReadLock(long paramLong)
/*      */   {
/*  661 */     long l1 = paramLong & 0xFF;
/*  662 */     long l3; while (((l3 = this.state) & 0xFFFFFFFFFFFFFF80) == (paramLong & 0xFFFFFFFFFFFFFF80)) { long l2;
/*  663 */       long l4; if ((l2 = l3 & 0xFF) == 0L) {
/*  664 */         if (l1 == 0L)
/*      */         {
/*  666 */           if (l2 < 126L) {
/*  667 */             if (U.compareAndSwapLong(this, STATE, l3, l4 = l3 + 1L)) {
/*  668 */               return l4;
/*      */             }
/*  670 */           } else if ((l4 = tryIncReaderOverflow(l3)) != 0L)
/*  671 */             return l4;
/*      */         }
/*  673 */       } else if (l2 == 128L) {
/*  674 */         if (l1 == l2)
/*      */         {
/*  676 */           this.state = (l4 = l3 + 129L);
/*  677 */           WNode localWNode; if (((localWNode = this.whead) != null) && (localWNode.status != 0))
/*  678 */             release(localWNode);
/*  679 */           return l4;
/*      */         }
/*  681 */       } else if ((l1 != 0L) && (l1 < 128L)) {
/*  682 */         return paramLong;
/*      */       }
/*      */     }
/*      */     
/*  686 */     return 0L;
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
/*      */   public long tryConvertToOptimisticRead(long paramLong)
/*      */   {
/*  700 */     long l1 = paramLong & 0xFF;
/*  701 */     U.loadFence();
/*      */     long l3;
/*  703 */     while (((l3 = this.state) & 0xFFFFFFFFFFFFFF80) == (paramLong & 0xFFFFFFFFFFFFFF80)) {
/*      */       long l2;
/*  705 */       if ((l2 = l3 & 0xFF) == 0L) {
/*  706 */         if (l1 == 0L)
/*      */         {
/*  708 */           return l3; } } else { long l4;
/*      */         WNode localWNode;
/*  710 */         if (l2 == 128L) {
/*  711 */           if (l1 == l2)
/*      */           {
/*  713 */             this.state = (l4 = l3 += 128L == 0L ? 256L : l3);
/*  714 */             if (((localWNode = this.whead) != null) && (localWNode.status != 0))
/*  715 */               release(localWNode);
/*  716 */             return l4;
/*      */           }
/*  718 */         } else if ((l1 != 0L) && (l1 < 128L))
/*      */         {
/*  720 */           if (l2 < 126L) {
/*  721 */             if (U.compareAndSwapLong(this, STATE, l3, l4 = l3 - 1L)) {
/*  722 */               if ((l2 == 1L) && ((localWNode = this.whead) != null) && (localWNode.status != 0))
/*  723 */                 release(localWNode);
/*  724 */               return l4 & 0xFFFFFFFFFFFFFF80;
/*      */             }
/*      */           }
/*  727 */           else if ((l4 = tryDecReaderOverflow(l3)) != 0L)
/*  728 */             return l4 & 0xFFFFFFFFFFFFFF80; }
/*      */       } }
/*  730 */     return 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean tryUnlockWrite()
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*      */ 
/*  742 */     if (((l = this.state) & 0x80) != 0L) {
/*  743 */       this.state = (l += 128L == 0L ? 256L : l);
/*  744 */       WNode localWNode; if (((localWNode = this.whead) != null) && (localWNode.status != 0))
/*  745 */         release(localWNode);
/*  746 */       return true;
/*      */     }
/*  748 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean tryUnlockRead()
/*      */   {
/*      */     long l1;
/*      */     
/*      */ 
/*      */     long l2;
/*      */     
/*  760 */     while (((l2 = (l1 = this.state) & 0xFF) != 0L) && (l2 < 128L)) {
/*  761 */       if (l2 < 126L) {
/*  762 */         if (U.compareAndSwapLong(this, STATE, l1, l1 - 1L)) { WNode localWNode;
/*  763 */           if ((l2 == 1L) && ((localWNode = this.whead) != null) && (localWNode.status != 0))
/*  764 */             release(localWNode);
/*  765 */           return true;
/*      */         }
/*      */       }
/*  768 */       else if (tryDecReaderOverflow(l1) != 0L)
/*  769 */         return true;
/*      */     }
/*  771 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getReadLockCount(long paramLong)
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*  782 */     if ((l = paramLong & 0x7F) >= 126L)
/*  783 */       l = 126L + this.readerOverflow;
/*  784 */     return (int)l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWriteLocked()
/*      */   {
/*  793 */     return (this.state & 0x80) != 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isReadLocked()
/*      */   {
/*  802 */     return (this.state & 0x7F) != 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getReadLockCount()
/*      */   {
/*  812 */     return getReadLockCount(this.state);
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
/*  825 */     long l = this.state;
/*      */     
/*      */ 
/*      */ 
/*  829 */     return super.toString() + ((l & 0x80) != 0L ? "[Write-locked]" : (l & 0xFF) == 0L ? "[Unlocked]" : new StringBuilder().append("[Read-locks:").append(getReadLockCount(l)).append("]").toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Lock asReadLock()
/*      */   {
/*      */     ReadLockView localReadLockView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  846 */     return (localReadLockView = this.readLockView) != null ? localReadLockView : (this.readLockView = new ReadLockView());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Lock asWriteLock()
/*      */   {
/*      */     WriteLockView localWriteLockView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  862 */     return (localWriteLockView = this.writeLockView) != null ? localWriteLockView : (this.writeLockView = new WriteLockView());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ReadWriteLock asReadWriteLock()
/*      */   {
/*      */     ReadWriteLockView localReadWriteLockView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  876 */     return (localReadWriteLockView = this.readWriteLockView) != null ? localReadWriteLockView : (this.readWriteLockView = new ReadWriteLockView());
/*      */   }
/*      */   
/*      */   final class ReadLockView
/*      */     implements Lock {
/*      */     ReadLockView() {}
/*      */     
/*  883 */     public void lock() { StampedLock.this.readLock(); }
/*      */     
/*  885 */     public void lockInterruptibly() throws InterruptedException { StampedLock.this.readLockInterruptibly(); }
/*      */     
/*  887 */     public boolean tryLock() { return StampedLock.this.tryReadLock() != 0L; }
/*      */     
/*      */ 
/*  890 */     public boolean tryLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException { return StampedLock.this.tryReadLock(paramLong, paramTimeUnit) != 0L; }
/*      */     
/*  892 */     public void unlock() { StampedLock.this.unstampedUnlockRead(); }
/*      */     
/*  894 */     public Condition newCondition() { throw new UnsupportedOperationException(); }
/*      */   }
/*      */   
/*      */   final class WriteLockView implements Lock { WriteLockView() {}
/*      */     
/*  899 */     public void lock() { StampedLock.this.writeLock(); }
/*      */     
/*  901 */     public void lockInterruptibly() throws InterruptedException { StampedLock.this.writeLockInterruptibly(); }
/*      */     
/*  903 */     public boolean tryLock() { return StampedLock.this.tryWriteLock() != 0L; }
/*      */     
/*      */ 
/*  906 */     public boolean tryLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException { return StampedLock.this.tryWriteLock(paramLong, paramTimeUnit) != 0L; }
/*      */     
/*  908 */     public void unlock() { StampedLock.this.unstampedUnlockWrite(); }
/*      */     
/*  910 */     public Condition newCondition() { throw new UnsupportedOperationException(); }
/*      */   }
/*      */   
/*      */   final class ReadWriteLockView implements ReadWriteLock { ReadWriteLockView() {}
/*      */     
/*  915 */     public Lock readLock() { return StampedLock.this.asReadLock(); }
/*  916 */     public Lock writeLock() { return StampedLock.this.asWriteLock(); }
/*      */   }
/*      */   
/*      */ 
/*      */   final void unstampedUnlockWrite()
/*      */   {
/*      */     long l;
/*      */     
/*  924 */     if (((l = this.state) & 0x80) == 0L)
/*  925 */       throw new IllegalMonitorStateException();
/*  926 */     this.state = (l += 128L == 0L ? 256L : l);
/*  927 */     WNode localWNode; if (((localWNode = this.whead) != null) && (localWNode.status != 0))
/*  928 */       release(localWNode);
/*      */   }
/*      */   
/*      */   final void unstampedUnlockRead() {
/*      */     for (;;) { long l1;
/*      */       long l2;
/*  934 */       if (((l2 = (l1 = this.state) & 0xFF) == 0L) || (l2 >= 128L))
/*  935 */         throw new IllegalMonitorStateException();
/*  936 */       if (l2 < 126L) {
/*  937 */         if (U.compareAndSwapLong(this, STATE, l1, l1 - 1L)) { WNode localWNode;
/*  938 */           if ((l2 != 1L) || ((localWNode = this.whead) == null) || (localWNode.status == 0)) break;
/*  939 */           release(localWNode); break;
/*      */         }
/*      */       }
/*      */       else {
/*  943 */         if (tryDecReaderOverflow(l1) != 0L)
/*      */           break;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  950 */     paramObjectInputStream.defaultReadObject();
/*  951 */     this.state = 256L;
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
/*      */   private long tryIncReaderOverflow(long paramLong)
/*      */   {
/*  966 */     if ((paramLong & 0xFF) == 126L) {
/*  967 */       if (U.compareAndSwapLong(this, STATE, paramLong, paramLong | 0x7F)) {
/*  968 */         this.readerOverflow += 1;
/*  969 */         this.state = paramLong;
/*  970 */         return paramLong;
/*      */       }
/*      */     }
/*  973 */     else if ((LockSupport.nextSecondarySeed() & 0x7) == 0)
/*      */     {
/*  975 */       Thread.yield(); }
/*  976 */     return 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long tryDecReaderOverflow(long paramLong)
/*      */   {
/*  987 */     if ((paramLong & 0xFF) == 126L) {
/*  988 */       if (U.compareAndSwapLong(this, STATE, paramLong, paramLong | 0x7F)) { int i;
/*      */         long l;
/*  990 */         if ((i = this.readerOverflow) > 0) {
/*  991 */           this.readerOverflow = (i - 1);
/*  992 */           l = paramLong;
/*      */         }
/*      */         else {
/*  995 */           l = paramLong - 1L; }
/*  996 */         this.state = l;
/*  997 */         return l;
/*      */       }
/*      */     }
/* 1000 */     else if ((LockSupport.nextSecondarySeed() & 0x7) == 0)
/*      */     {
/* 1002 */       Thread.yield(); }
/* 1003 */     return 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void release(WNode paramWNode)
/*      */   {
/* 1014 */     if (paramWNode != null)
/*      */     {
/* 1016 */       U.compareAndSwapInt(paramWNode, WSTATUS, -1, 0);
/* 1017 */       Object localObject; if (((localObject = paramWNode.next) == null) || (((WNode)localObject).status == 1))
/* 1018 */         for (WNode localWNode = this.wtail; (localWNode != null) && (localWNode != paramWNode); localWNode = localWNode.prev)
/* 1019 */           if (localWNode.status <= 0)
/* 1020 */             localObject = localWNode;
/*      */       Thread localThread;
/* 1022 */       if ((localObject != null) && ((localThread = ((WNode)localObject).thread) != null)) {
/* 1023 */         U.unpark(localThread);
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
/*      */   private long acquireWrite(boolean paramBoolean, long paramLong)
/*      */   {
/* 1037 */     WNode localWNode1 = null;
/* 1038 */     long l1 = -1;
/*      */     long l4;
/* 1040 */     Object localObject1; Object localObject2; for (;;) { long l3; long l2; if ((l2 = (l3 = this.state) & 0xFF) == 0L) {
/* 1041 */         if (U.compareAndSwapLong(this, STATE, l3, l4 = l3 + 128L)) {
/* 1042 */           return l4;
/*      */         }
/* 1044 */       } else if (l1 < 0) {
/* 1045 */         l1 = (l2 == 128L) && (this.wtail == this.whead) ? SPINS : 0;
/* 1046 */       } else if (l1 > 0) {
/* 1047 */         if (LockSupport.nextSecondarySeed() >= 0) {
/* 1048 */           l1--;
/*      */         }
/* 1050 */       } else if ((localObject1 = this.wtail) == null) {
/* 1051 */         localObject2 = new WNode(1, null);
/* 1052 */         if (U.compareAndSwapObject(this, WHEAD, null, localObject2)) {
/* 1053 */           this.wtail = ((WNode)localObject2);
/*      */         }
/* 1055 */       } else if (localWNode1 == null) {
/* 1056 */         localWNode1 = new WNode(1, (WNode)localObject1);
/* 1057 */       } else if (localWNode1.prev != localObject1) {
/* 1058 */         localWNode1.prev = ((WNode)localObject1);
/* 1059 */       } else if (U.compareAndSwapObject(this, WTAIL, localObject1, localWNode1)) {
/* 1060 */         ((WNode)localObject1).next = localWNode1;
/* 1061 */         break;
/*      */       }
/*      */     }
/*      */     
/* 1065 */     l1 = -1;
/*      */     for (;;) { WNode localWNode2;
/* 1067 */       if ((localWNode2 = this.whead) == localObject1) {
/* 1068 */         if (l1 < 0) {
/* 1069 */           l1 = HEAD_SPINS;
/* 1070 */         } else if (l1 < MAX_HEAD_SPINS)
/* 1071 */           l1 <<= 1;
/* 1072 */         l4 = l1;
/*      */         for (;;) { long l6;
/* 1074 */           if (((l6 = this.state) & 0xFF) == 0L) { long l7;
/* 1075 */             if (U.compareAndSwapLong(this, STATE, l6, l7 = l6 + 128L))
/*      */             {
/* 1077 */               this.whead = localWNode1;
/* 1078 */               localWNode1.prev = null;
/* 1079 */               return l7;
/*      */             }
/*      */           }
/* 1082 */           else if (LockSupport.nextSecondarySeed() >= 0) { l4--; if (l4 <= 0) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1087 */       } else if (localWNode2 != null) {
/*      */         WNode localWNode5;
/* 1089 */         while ((localWNode5 = localWNode2.cowait) != null) { Thread localThread;
/* 1090 */           if ((U.compareAndSwapObject(localWNode2, WCOWAIT, localWNode5, localWNode5.cowait)) && ((localThread = localWNode5.thread) != null))
/*      */           {
/* 1092 */             U.unpark(localThread); }
/*      */         }
/*      */       }
/* 1095 */       if (this.whead == localWNode2) { WNode localWNode3;
/* 1096 */         if ((localWNode3 = localWNode1.prev) != localObject1) {
/* 1097 */           if (localWNode3 != null)
/* 1098 */             (localObject1 = localWNode3).next = localWNode1;
/*      */         } else { int i;
/* 1100 */           if ((i = ((WNode)localObject1).status) == 0) {
/* 1101 */             U.compareAndSwapInt(localObject1, WSTATUS, 0, -1);
/* 1102 */           } else if (i == 1) { WNode localWNode4;
/* 1103 */             if ((localWNode4 = ((WNode)localObject1).prev) != null) {
/* 1104 */               localWNode1.prev = localWNode4;
/* 1105 */               localWNode4.next = localWNode1;
/*      */             }
/*      */           }
/*      */           else {
/*      */             long l5;
/* 1110 */             if (paramLong == 0L) {
/* 1111 */               l5 = 0L;
/* 1112 */             } else if ((l5 = paramLong - System.nanoTime()) <= 0L)
/* 1113 */               return cancelWaiter(localWNode1, localWNode1, false);
/* 1114 */             localObject2 = Thread.currentThread();
/* 1115 */             U.putObject(localObject2, PARKBLOCKER, this);
/* 1116 */             localWNode1.thread = ((Thread)localObject2);
/* 1117 */             if ((((WNode)localObject1).status < 0) && ((localObject1 != localWNode2) || ((this.state & 0xFF) != 0L)) && (this.whead == localWNode2) && (localWNode1.prev == localObject1))
/*      */             {
/* 1119 */               U.park(false, l5); }
/* 1120 */             localWNode1.thread = null;
/* 1121 */             U.putObject(localObject2, PARKBLOCKER, null);
/* 1122 */             if ((paramBoolean) && (Thread.interrupted())) {
/* 1123 */               return cancelWaiter(localWNode1, localWNode1, true);
/*      */             }
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
/*      */   private long acquireRead(boolean paramBoolean, long paramLong)
/*      */   {
/* 1139 */     WNode localWNode1 = null;
/* 1140 */     long l1 = -1;
/*      */     Object localObject2;
/* 1142 */     Object localObject1; long l6; WNode localWNode2; WNode localWNode3; long l4; Thread localThread3; for (;;) { if ((localObject2 = this.whead) == (localObject1 = this.wtail)) {
/*      */         for (;;) { long l3;
/* 1144 */           long l2; if ((l2 = (l3 = this.state) & 0xFF) < 126L ? U
/* 1145 */             .compareAndSwapLong(this, STATE, l3, l6 = l3 + 1L) : (l2 < 128L) && 
/* 1146 */             ((l6 = tryIncReaderOverflow(l3)) != 0L))
/* 1147 */             return l6;
/* 1148 */           if (l2 >= 128L) {
/* 1149 */             if (l1 > 0) {
/* 1150 */               if (LockSupport.nextSecondarySeed() >= 0) {
/* 1151 */                 l1--;
/*      */               }
/*      */             } else {
/* 1154 */               if (l1 == 0) {
/* 1155 */                 WNode localWNode5 = this.whead;WNode localWNode6 = this.wtail;
/* 1156 */                 if (((localWNode5 == localObject2) && (localWNode6 == localObject1)) || ((localObject2 = localWNode5) != (localObject1 = localWNode6)))
/*      */                   break;
/*      */               }
/* 1159 */               l1 = SPINS;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1164 */       if (localObject1 == null) {
/* 1165 */         localWNode2 = new WNode(1, null);
/* 1166 */         if (U.compareAndSwapObject(this, WHEAD, null, localWNode2)) {
/* 1167 */           this.wtail = localWNode2;
/*      */         }
/* 1169 */       } else if (localWNode1 == null) {
/* 1170 */         localWNode1 = new WNode(0, (WNode)localObject1);
/* 1171 */       } else if ((localObject2 == localObject1) || (((WNode)localObject1).mode != 0)) {
/* 1172 */         if (localWNode1.prev != localObject1) {
/* 1173 */           localWNode1.prev = ((WNode)localObject1);
/* 1174 */         } else if (U.compareAndSwapObject(this, WTAIL, localObject1, localWNode1)) {
/* 1175 */           ((WNode)localObject1).next = localWNode1;
/* 1176 */           break;
/*      */         }
/*      */       }
/* 1179 */       else if (!U.compareAndSwapObject(localObject1, WCOWAIT, localWNode1.cowait = ((WNode)localObject1).cowait, localWNode1))
/*      */       {
/* 1181 */         localWNode1.cowait = null;
/*      */       } else {
/*      */         for (;;) {
/*      */           Thread localThread1;
/* 1185 */           if (((localObject2 = this.whead) != null) && ((localWNode3 = ((WNode)localObject2).cowait) != null) && 
/* 1186 */             (U.compareAndSwapObject(localObject2, WCOWAIT, localWNode3, localWNode3.cowait)) && ((localThread1 = localWNode3.thread) != null))
/*      */           {
/* 1188 */             U.unpark(localThread1); }
/* 1189 */           if ((localObject2 == (localWNode2 = ((WNode)localObject1).prev)) || (localObject2 == localObject1) || (localWNode2 == null)) {
/*      */             do { long l7;
/*      */               long l9;
/* 1192 */               if ((l4 = (l7 = this.state) & 0xFF) < 126L) {
/* 1193 */                 if (!U.compareAndSwapLong(this, STATE, l7, l9 = l7 + 1L)) continue; } else { if (l4 >= 128L) {
/*      */                   continue;
/*      */                 }
/* 1196 */                 if ((l9 = tryIncReaderOverflow(l7)) == 0L) continue; }
/* 1197 */               return l9;
/* 1198 */             } while (l4 < 128L);
/*      */           }
/* 1200 */           if ((this.whead == localObject2) && (((WNode)localObject1).prev == localWNode2))
/*      */           {
/* 1202 */             if ((localWNode2 == null) || (localObject2 == localObject1) || (((WNode)localObject1).status > 0)) {
/* 1203 */               localWNode1 = null;
/* 1204 */               break;
/*      */             }
/* 1206 */             if (paramLong == 0L) {
/* 1207 */               l4 = 0L;
/* 1208 */             } else if ((l4 = paramLong - System.nanoTime()) <= 0L)
/* 1209 */               return cancelWaiter(localWNode1, (WNode)localObject1, false);
/* 1210 */             localThread3 = Thread.currentThread();
/* 1211 */             U.putObject(localThread3, PARKBLOCKER, this);
/* 1212 */             localWNode1.thread = localThread3;
/* 1213 */             if (((localObject2 != localWNode2) || ((this.state & 0xFF) == 128L)) && (this.whead == localObject2) && (((WNode)localObject1).prev == localWNode2))
/*      */             {
/* 1215 */               U.park(false, l4); }
/* 1216 */             localWNode1.thread = null;
/* 1217 */             U.putObject(localThread3, PARKBLOCKER, null);
/* 1218 */             if ((paramBoolean) && (Thread.interrupted())) {
/* 1219 */               return cancelWaiter(localWNode1, (WNode)localObject1, true);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1225 */     l1 = -1;
/*      */     for (;;) {
/* 1227 */       if ((localObject2 = this.whead) == localObject1) {
/* 1228 */         if (l1 < 0) {
/* 1229 */           l1 = HEAD_SPINS;
/* 1230 */         } else if (l1 < MAX_HEAD_SPINS)
/* 1231 */           l1 <<= 1;
/* 1232 */         l4 = l1;
/*      */         for (;;) { long l8;
/* 1234 */           long l10; if ((l6 = (l8 = this.state) & 0xFF) < 126L ? U
/* 1235 */             .compareAndSwapLong(this, STATE, l8, l10 = l8 + 1L) : (l6 < 128L) && 
/* 1236 */             ((l10 = tryIncReaderOverflow(l8)) != 0L))
/*      */           {
/* 1238 */             this.whead = localWNode1;
/* 1239 */             localWNode1.prev = null;
/* 1240 */             WNode localWNode7; while ((localWNode7 = localWNode1.cowait) != null) { Thread localThread4;
/* 1241 */               if ((U.compareAndSwapObject(localWNode1, WCOWAIT, localWNode7, localWNode7.cowait)) && ((localThread4 = localWNode7.thread) != null))
/*      */               {
/*      */ 
/* 1244 */                 U.unpark(localThread4); }
/*      */             }
/* 1246 */             return l10;
/*      */           }
/* 1248 */           if ((l6 >= 128L) && 
/* 1249 */             (LockSupport.nextSecondarySeed() >= 0)) { l4--; if (l4 <= 0)
/*      */               break;
/*      */           }
/*      */         }
/* 1253 */       } else if (localObject2 != null) {
/*      */         WNode localWNode4;
/* 1255 */         while ((localWNode4 = ((WNode)localObject2).cowait) != null) { Thread localThread2;
/* 1256 */           if ((U.compareAndSwapObject(localObject2, WCOWAIT, localWNode4, localWNode4.cowait)) && ((localThread2 = localWNode4.thread) != null))
/*      */           {
/* 1258 */             U.unpark(localThread2); }
/*      */         }
/*      */       }
/* 1261 */       if (this.whead == localObject2) {
/* 1262 */         if ((localWNode2 = localWNode1.prev) != localObject1) {
/* 1263 */           if (localWNode2 != null)
/* 1264 */             (localObject1 = localWNode2).next = localWNode1;
/*      */         } else { int i;
/* 1266 */           if ((i = ((WNode)localObject1).status) == 0) {
/* 1267 */             U.compareAndSwapInt(localObject1, WSTATUS, 0, -1);
/* 1268 */           } else if (i == 1) {
/* 1269 */             if ((localWNode3 = ((WNode)localObject1).prev) != null) {
/* 1270 */               localWNode1.prev = localWNode3;
/* 1271 */               localWNode3.next = localWNode1;
/*      */             }
/*      */           }
/*      */           else {
/*      */             long l5;
/* 1276 */             if (paramLong == 0L) {
/* 1277 */               l5 = 0L;
/* 1278 */             } else if ((l5 = paramLong - System.nanoTime()) <= 0L)
/* 1279 */               return cancelWaiter(localWNode1, localWNode1, false);
/* 1280 */             localThread3 = Thread.currentThread();
/* 1281 */             U.putObject(localThread3, PARKBLOCKER, this);
/* 1282 */             localWNode1.thread = localThread3;
/* 1283 */             if ((((WNode)localObject1).status < 0) && ((localObject1 != localObject2) || ((this.state & 0xFF) == 128L)) && (this.whead == localObject2) && (localWNode1.prev == localObject1))
/*      */             {
/*      */ 
/* 1286 */               U.park(false, l5); }
/* 1287 */             localWNode1.thread = null;
/* 1288 */             U.putObject(localThread3, PARKBLOCKER, null);
/* 1289 */             if ((paramBoolean) && (Thread.interrupted())) {
/* 1290 */               return cancelWaiter(localWNode1, localWNode1, true);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long cancelWaiter(WNode paramWNode1, WNode paramWNode2, boolean paramBoolean)
/*      */   {
/*      */     Object localObject2;
/*      */     
/*      */ 
/*      */ 
/*      */     Object localObject3;
/*      */     
/*      */ 
/*      */     Object localObject1;
/*      */     
/*      */ 
/* 1313 */     if ((paramWNode1 != null) && (paramWNode2 != null))
/*      */     {
/* 1315 */       paramWNode1.status = 1;
/*      */       
/* 1317 */       for (localObject2 = paramWNode2; (localObject3 = ((WNode)localObject2).cowait) != null;)
/* 1318 */         if (((WNode)localObject3).status == 1) {
/* 1319 */           U.compareAndSwapObject(localObject2, WCOWAIT, localObject3, ((WNode)localObject3).cowait);
/* 1320 */           localObject2 = paramWNode2;
/*      */         }
/*      */         else {
/* 1323 */           localObject2 = localObject3;
/*      */         }
/* 1325 */       if (paramWNode2 == paramWNode1) {
/* 1326 */         for (localObject2 = paramWNode2.cowait; localObject2 != null; localObject2 = ((WNode)localObject2).cowait) {
/* 1327 */           if ((localObject1 = ((WNode)localObject2).thread) != null)
/* 1328 */             U.unpark(localObject1);
/*      */         }
/* 1330 */         for (localObject2 = paramWNode1.prev; localObject2 != null;)
/*      */         {
/* 1332 */           while (((localObject3 = paramWNode1.next) == null) || (((WNode)localObject3).status == 1))
/*      */           {
/* 1334 */             localObject5 = null;
/* 1335 */             for (WNode localWNode = this.wtail; (localWNode != null) && (localWNode != paramWNode1); localWNode = localWNode.prev)
/* 1336 */               if (localWNode.status != 1)
/* 1337 */                 localObject5 = localWNode;
/* 1338 */             if ((localObject3 == localObject5) || 
/* 1339 */               (U.compareAndSwapObject(paramWNode1, WNEXT, localObject3, localObject3 = localObject5)))
/*      */             {
/* 1341 */               if ((localObject3 != null) || (paramWNode1 != this.wtail)) break;
/* 1342 */               U.compareAndSwapObject(this, WTAIL, paramWNode1, localObject2); break;
/*      */             }
/*      */           }
/*      */           
/* 1346 */           if (((WNode)localObject2).next == paramWNode1)
/* 1347 */             U.compareAndSwapObject(localObject2, WNEXT, paramWNode1, localObject3);
/* 1348 */           if ((localObject3 != null) && ((localObject1 = ((WNode)localObject3).thread) != null)) {
/* 1349 */             ((WNode)localObject3).thread = null;
/* 1350 */             U.unpark(localObject1);
/*      */           }
/* 1352 */           if ((((WNode)localObject2).status != 1) || ((localObject4 = ((WNode)localObject2).prev) == null))
/*      */             break;
/* 1354 */           paramWNode1.prev = ((WNode)localObject4);
/* 1355 */           U.compareAndSwapObject(localObject4, WNEXT, localObject2, localObject3);
/* 1356 */           localObject2 = localObject4;
/*      */         }
/*      */       } }
/*      */     Object localObject5;
/*      */     Object localObject4;
/* 1361 */     while ((localObject1 = this.whead) != null)
/*      */     {
/* 1363 */       if (((localObject4 = ((WNode)localObject1).next) == null) || (((WNode)localObject4).status == 1)) {
/* 1364 */         for (localObject5 = this.wtail; (localObject5 != null) && (localObject5 != localObject1); localObject5 = ((WNode)localObject5).prev)
/* 1365 */           if (((WNode)localObject5).status <= 0)
/* 1366 */             localObject4 = localObject5;
/*      */       }
/* 1368 */       if (localObject1 == this.whead) { long l;
/* 1369 */         if ((localObject4 == null) || (((WNode)localObject1).status != 0) || (((l = this.state) & 0xFF) == 128L) || ((l != 0L) && (((WNode)localObject4).mode != 0))) {
/*      */           break;
/*      */         }
/* 1372 */         release((WNode)localObject1); break;
/*      */       }
/*      */     }
/*      */     
/* 1376 */     return (paramBoolean) || (Thread.interrupted()) ? 1L : 0L;
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
/* 1391 */       U = Unsafe.getUnsafe();
/* 1392 */       Class localClass1 = StampedLock.class;
/* 1393 */       Class localClass2 = WNode.class;
/*      */       
/* 1395 */       STATE = U.objectFieldOffset(localClass1.getDeclaredField("state"));
/*      */       
/* 1397 */       WHEAD = U.objectFieldOffset(localClass1.getDeclaredField("whead"));
/*      */       
/* 1399 */       WTAIL = U.objectFieldOffset(localClass1.getDeclaredField("wtail"));
/*      */       
/* 1401 */       WSTATUS = U.objectFieldOffset(localClass2.getDeclaredField("status"));
/*      */       
/* 1403 */       WNEXT = U.objectFieldOffset(localClass2.getDeclaredField("next"));
/*      */       
/* 1405 */       WCOWAIT = U.objectFieldOffset(localClass2.getDeclaredField("cowait"));
/* 1406 */       Class localClass3 = Thread.class;
/*      */       
/* 1408 */       PARKBLOCKER = U.objectFieldOffset(localClass3.getDeclaredField("parkBlocker"));
/*      */     }
/*      */     catch (Exception localException) {
/* 1411 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/locks/StampedLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */