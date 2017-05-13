/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.concurrent.locks.LockSupport;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Phaser
/*      */ {
/*      */   private volatile long state;
/*      */   private static final int MAX_PARTIES = 65535;
/*      */   private static final int MAX_PHASE = Integer.MAX_VALUE;
/*      */   private static final int PARTIES_SHIFT = 16;
/*      */   private static final int PHASE_SHIFT = 32;
/*      */   private static final int UNARRIVED_MASK = 65535;
/*      */   private static final long PARTIES_MASK = 4294901760L;
/*      */   private static final long COUNTS_MASK = 4294967295L;
/*      */   private static final long TERMINATION_BIT = Long.MIN_VALUE;
/*      */   private static final int ONE_ARRIVAL = 1;
/*      */   private static final int ONE_PARTY = 65536;
/*      */   private static final int ONE_DEREGISTER = 65537;
/*      */   private static final int EMPTY = 1;
/*      */   private final Phaser parent;
/*      */   private final Phaser root;
/*      */   private final AtomicReference<QNode> evenQ;
/*      */   private final AtomicReference<QNode> oddQ;
/*      */   
/*      */   private static int unarrivedOf(long paramLong)
/*      */   {
/*  315 */     int i = (int)paramLong;
/*  316 */     return i == 1 ? 0 : i & 0xFFFF;
/*      */   }
/*      */   
/*      */   private static int partiesOf(long paramLong) {
/*  320 */     return (int)paramLong >>> 16;
/*      */   }
/*      */   
/*      */   private static int phaseOf(long paramLong) {
/*  324 */     return (int)(paramLong >>> 32);
/*      */   }
/*      */   
/*      */   private static int arrivedOf(long paramLong) {
/*  328 */     int i = (int)paramLong;
/*  329 */     return i == 1 ? 0 : (i >>> 16) - (i & 0xFFFF);
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
/*      */   private AtomicReference<QNode> queueFor(int paramInt)
/*      */   {
/*  353 */     return (paramInt & 0x1) == 0 ? this.evenQ : this.oddQ;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String badArrive(long paramLong)
/*      */   {
/*  361 */     return "Attempted arrival of unregistered party for " + stateToString(paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String badRegister(long paramLong)
/*      */   {
/*  369 */     return "Attempt to register more than 65535 parties for " + stateToString(paramLong);
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
/*      */   private int doArrive(int paramInt)
/*      */   {
/*  382 */     Phaser localPhaser = this.root;
/*      */     for (;;) {
/*  384 */       long l1 = localPhaser == this ? this.state : reconcileState();
/*  385 */       int i = (int)(l1 >>> 32);
/*  386 */       if (i < 0)
/*  387 */         return i;
/*  388 */       int j = (int)l1;
/*  389 */       int k = j == 1 ? 0 : j & 0xFFFF;
/*  390 */       if (k <= 0)
/*  391 */         throw new IllegalStateException(badArrive(l1));
/*  392 */       if (UNSAFE.compareAndSwapLong(this, stateOffset, l1, l1 -= paramInt)) {
/*  393 */         if (k == 1) {
/*  394 */           long l2 = l1 & 0xFFFF0000;
/*  395 */           int m = (int)l2 >>> 16;
/*  396 */           if (localPhaser == this) {
/*  397 */             if (onAdvance(i, m)) {
/*  398 */               l2 |= 0x8000000000000000;
/*  399 */             } else if (m == 0) {
/*  400 */               l2 |= 1L;
/*      */             } else
/*  402 */               l2 |= m;
/*  403 */             int n = i + 1 & 0x7FFFFFFF;
/*  404 */             l2 |= n << 32;
/*  405 */             UNSAFE.compareAndSwapLong(this, stateOffset, l1, l2);
/*  406 */             releaseWaiters(i);
/*      */           }
/*  408 */           else if (m == 0) {
/*  409 */             i = this.parent.doArrive(65537);
/*  410 */             UNSAFE.compareAndSwapLong(this, stateOffset, l1, l1 | 1L);
/*      */           }
/*      */           else
/*      */           {
/*  414 */             i = this.parent.doArrive(1);
/*      */           } }
/*  416 */         return i;
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
/*      */   private int doRegister(int paramInt)
/*      */   {
/*  429 */     long l1 = paramInt << 16 | paramInt;
/*  430 */     Phaser localPhaser = this.parent;
/*      */     int i;
/*      */     for (;;) {
/*  433 */       long l2 = localPhaser == null ? this.state : reconcileState();
/*  434 */       int j = (int)l2;
/*  435 */       int k = j >>> 16;
/*  436 */       int m = j & 0xFFFF;
/*  437 */       if (paramInt > 65535 - k)
/*  438 */         throw new IllegalStateException(badRegister(l2));
/*  439 */       i = (int)(l2 >>> 32);
/*  440 */       if (i < 0)
/*      */         break;
/*  442 */       if (j != 1) {
/*  443 */         if ((localPhaser == null) || (reconcileState() == l2)) {
/*  444 */           if (m == 0) {
/*  445 */             this.root.internalAwaitAdvance(i, null);
/*  446 */           } else if (UNSAFE.compareAndSwapLong(this, stateOffset, l2, l2 + l1)) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*  451 */       else if (localPhaser == null) {
/*  452 */         long l3 = i << 32 | l1;
/*  453 */         if (UNSAFE.compareAndSwapLong(this, stateOffset, l2, l3)) {
/*      */           break;
/*      */         }
/*      */       } else {
/*  457 */         synchronized (this) {
/*  458 */           if (this.state == l2) {
/*  459 */             i = localPhaser.doRegister(1);
/*  460 */             if (i < 0) {
/*      */               break;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  466 */             while (!UNSAFE.compareAndSwapLong(this, stateOffset, l2, i << 32 | l1))
/*      */             {
/*  468 */               l2 = this.state;
/*  469 */               i = (int)(this.root.state >>> 32);
/*      */             }
/*      */             
/*  472 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  477 */     return i;
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
/*      */   private long reconcileState()
/*      */   {
/*  490 */     Phaser localPhaser = this.root;
/*  491 */     long l = this.state;
/*  492 */     if (localPhaser != this)
/*      */     {
/*      */       int i;
/*  495 */       while ((i = (int)(localPhaser.state >>> 32)) != (int)(l >>> 32))
/*      */       {
/*      */         int j;
/*  498 */         if (UNSAFE.compareAndSwapLong(this, stateOffset, l, l = i << 32 | ((j = (int)l >>> 16) == 0 ? 1L : i < 0 ? l & 0xFFFFFFFF : l & 0xFFFF0000 | j))) {
/*      */           break;
/*      */         }
/*      */         
/*      */ 
/*  503 */         l = this.state;
/*      */       } }
/*  505 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Phaser()
/*      */   {
/*  514 */     this(null, 0);
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
/*      */   public Phaser(int paramInt)
/*      */   {
/*  527 */     this(null, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Phaser(Phaser paramPhaser)
/*      */   {
/*  536 */     this(paramPhaser, 0);
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
/*      */   public Phaser(Phaser paramPhaser, int paramInt)
/*      */   {
/*  552 */     if (paramInt >>> 16 != 0)
/*  553 */       throw new IllegalArgumentException("Illegal number of parties");
/*  554 */     int i = 0;
/*  555 */     this.parent = paramPhaser;
/*  556 */     if (paramPhaser != null) {
/*  557 */       Phaser localPhaser = paramPhaser.root;
/*  558 */       this.root = localPhaser;
/*  559 */       this.evenQ = localPhaser.evenQ;
/*  560 */       this.oddQ = localPhaser.oddQ;
/*  561 */       if (paramInt != 0) {
/*  562 */         i = paramPhaser.doRegister(1);
/*      */       }
/*      */     } else {
/*  565 */       this.root = this;
/*  566 */       this.evenQ = new AtomicReference();
/*  567 */       this.oddQ = new AtomicReference();
/*      */     }
/*  569 */     this.state = (paramInt == 0 ? 1L : i << 32 | paramInt << 16 | paramInt);
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
/*      */   public int register()
/*      */   {
/*  591 */     return doRegister(1);
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
/*      */   public int bulkRegister(int paramInt)
/*      */   {
/*  614 */     if (paramInt < 0)
/*  615 */       throw new IllegalArgumentException();
/*  616 */     if (paramInt == 0)
/*  617 */       return getPhase();
/*  618 */     return doRegister(paramInt);
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
/*      */   public int arrive()
/*      */   {
/*  634 */     return doArrive(1);
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
/*      */   public int arriveAndDeregister()
/*      */   {
/*  654 */     return doArrive(65537);
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
/*      */   public int arriveAndAwaitAdvance()
/*      */   {
/*  677 */     Phaser localPhaser = this.root;
/*      */     for (;;) {
/*  679 */       long l1 = localPhaser == this ? this.state : reconcileState();
/*  680 */       int i = (int)(l1 >>> 32);
/*  681 */       if (i < 0)
/*  682 */         return i;
/*  683 */       int j = (int)l1;
/*  684 */       int k = j == 1 ? 0 : j & 0xFFFF;
/*  685 */       if (k <= 0)
/*  686 */         throw new IllegalStateException(badArrive(l1));
/*  687 */       if (UNSAFE.compareAndSwapLong(this, stateOffset, l1, --l1))
/*      */       {
/*  689 */         if (k > 1)
/*  690 */           return localPhaser.internalAwaitAdvance(i, null);
/*  691 */         if (localPhaser != this)
/*  692 */           return this.parent.arriveAndAwaitAdvance();
/*  693 */         long l2 = l1 & 0xFFFF0000;
/*  694 */         int m = (int)l2 >>> 16;
/*  695 */         if (onAdvance(i, m)) {
/*  696 */           l2 |= 0x8000000000000000;
/*  697 */         } else if (m == 0) {
/*  698 */           l2 |= 1L;
/*      */         } else
/*  700 */           l2 |= m;
/*  701 */         int n = i + 1 & 0x7FFFFFFF;
/*  702 */         l2 |= n << 32;
/*  703 */         if (!UNSAFE.compareAndSwapLong(this, stateOffset, l1, l2))
/*  704 */           return (int)(this.state >>> 32);
/*  705 */         releaseWaiters(i);
/*  706 */         return n;
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
/*      */   public int awaitAdvance(int paramInt)
/*      */   {
/*  724 */     Phaser localPhaser = this.root;
/*  725 */     long l = localPhaser == this ? this.state : reconcileState();
/*  726 */     int i = (int)(l >>> 32);
/*  727 */     if (paramInt < 0)
/*  728 */       return paramInt;
/*  729 */     if (i == paramInt)
/*  730 */       return localPhaser.internalAwaitAdvance(paramInt, null);
/*  731 */     return i;
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
/*      */   public int awaitAdvanceInterruptibly(int paramInt)
/*      */     throws InterruptedException
/*      */   {
/*  751 */     Phaser localPhaser = this.root;
/*  752 */     long l = localPhaser == this ? this.state : reconcileState();
/*  753 */     int i = (int)(l >>> 32);
/*  754 */     if (paramInt < 0)
/*  755 */       return paramInt;
/*  756 */     if (i == paramInt) {
/*  757 */       QNode localQNode = new QNode(this, paramInt, true, false, 0L);
/*  758 */       i = localPhaser.internalAwaitAdvance(paramInt, localQNode);
/*  759 */       if (localQNode.wasInterrupted)
/*  760 */         throw new InterruptedException();
/*      */     }
/*  762 */     return i;
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
/*      */   public int awaitAdvanceInterruptibly(int paramInt, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException, TimeoutException
/*      */   {
/*  788 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*  789 */     Phaser localPhaser = this.root;
/*  790 */     long l2 = localPhaser == this ? this.state : reconcileState();
/*  791 */     int i = (int)(l2 >>> 32);
/*  792 */     if (paramInt < 0)
/*  793 */       return paramInt;
/*  794 */     if (i == paramInt) {
/*  795 */       QNode localQNode = new QNode(this, paramInt, true, true, l1);
/*  796 */       i = localPhaser.internalAwaitAdvance(paramInt, localQNode);
/*  797 */       if (localQNode.wasInterrupted)
/*  798 */         throw new InterruptedException();
/*  799 */       if (i == paramInt)
/*  800 */         throw new TimeoutException();
/*      */     }
/*  802 */     return i;
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
/*      */   public void forceTermination()
/*      */   {
/*  816 */     Phaser localPhaser = this.root;
/*      */     long l;
/*  818 */     while ((l = localPhaser.state) >= 0L) {
/*  819 */       if (UNSAFE.compareAndSwapLong(localPhaser, stateOffset, l, l | 0x8000000000000000))
/*      */       {
/*      */ 
/*  822 */         releaseWaiters(0);
/*  823 */         releaseWaiters(1);
/*  824 */         return;
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
/*      */   public final int getPhase()
/*      */   {
/*  839 */     return (int)(this.root.state >>> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getRegisteredParties()
/*      */   {
/*  848 */     return partiesOf(this.state);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getArrivedParties()
/*      */   {
/*  859 */     return arrivedOf(reconcileState());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getUnarrivedParties()
/*      */   {
/*  870 */     return unarrivedOf(reconcileState());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Phaser getParent()
/*      */   {
/*  879 */     return this.parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Phaser getRoot()
/*      */   {
/*  889 */     return this.root;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isTerminated()
/*      */   {
/*  898 */     return this.root.state < 0L;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean onAdvance(int paramInt1, int paramInt2)
/*      */   {
/*  942 */     return paramInt2 == 0;
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
/*  955 */     return stateToString(reconcileState());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String stateToString(long paramLong)
/*      */   {
/*  965 */     return super.toString() + "[phase = " + phaseOf(paramLong) + " parties = " + partiesOf(paramLong) + " arrived = " + arrivedOf(paramLong) + "]";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void releaseWaiters(int paramInt)
/*      */   {
/*  976 */     AtomicReference localAtomicReference = (paramInt & 0x1) == 0 ? this.evenQ : this.oddQ;
/*  977 */     QNode localQNode; while (((localQNode = (QNode)localAtomicReference.get()) != null) && (localQNode.phase != (int)(this.root.state >>> 32))) {
/*      */       Thread localThread;
/*  979 */       if ((localAtomicReference.compareAndSet(localQNode, localQNode.next)) && ((localThread = localQNode.thread) != null))
/*      */       {
/*  981 */         localQNode.thread = null;
/*  982 */         LockSupport.unpark(localThread);
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
/*      */   private int abortWait(int paramInt)
/*      */   {
/*  997 */     AtomicReference localAtomicReference = (paramInt & 0x1) == 0 ? this.evenQ : this.oddQ;
/*      */     for (;;)
/*      */     {
/* 1000 */       QNode localQNode = (QNode)localAtomicReference.get();
/* 1001 */       int i = (int)(this.root.state >>> 32);
/* 1002 */       Thread localThread; if ((localQNode == null) || (((localThread = localQNode.thread) != null) && (localQNode.phase == i)))
/* 1003 */         return i;
/* 1004 */       if ((localAtomicReference.compareAndSet(localQNode, localQNode.next)) && (localThread != null)) {
/* 1005 */         localQNode.thread = null;
/* 1006 */         LockSupport.unpark(localThread);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/* 1012 */   private static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1025 */   static final int SPINS_PER_ARRIVAL = NCPU < 2 ? 1 : 256;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */ 
/*      */   private static final long stateOffset;
/*      */   
/*      */ 
/*      */ 
/*      */   private int internalAwaitAdvance(int paramInt, QNode paramQNode)
/*      */   {
/* 1038 */     releaseWaiters(paramInt - 1);
/* 1039 */     boolean bool1 = false;
/* 1040 */     int i = 0;
/* 1041 */     int j = SPINS_PER_ARRIVAL;
/*      */     long l;
/*      */     int k;
/* 1044 */     while ((k = (int)((l = this.state) >>> 32)) == paramInt) {
/* 1045 */       if (paramQNode == null) {
/* 1046 */         int m = (int)l & 0xFFFF;
/* 1047 */         if ((m != i) && ((i = m) < NCPU))
/*      */         {
/* 1049 */           j += SPINS_PER_ARRIVAL; }
/* 1050 */         boolean bool2 = Thread.interrupted();
/* 1051 */         if (!bool2) { j--; if (j >= 0) {}
/* 1052 */         } else { paramQNode = new QNode(this, paramInt, false, false, 0L);
/* 1053 */           paramQNode.wasInterrupted = bool2;
/*      */         }
/*      */       } else {
/* 1056 */         if (paramQNode.isReleasable())
/*      */           break;
/* 1058 */         if (!bool1) {
/* 1059 */           AtomicReference localAtomicReference = (paramInt & 0x1) == 0 ? this.evenQ : this.oddQ;
/* 1060 */           QNode localQNode = paramQNode.next = (QNode)localAtomicReference.get();
/* 1061 */           if (((localQNode == null) || (localQNode.phase == paramInt)) && ((int)(this.state >>> 32) == paramInt))
/*      */           {
/* 1063 */             bool1 = localAtomicReference.compareAndSet(localQNode, paramQNode);
/*      */           }
/*      */         } else {
/*      */           try {
/* 1067 */             ForkJoinPool.managedBlock(paramQNode);
/*      */           } catch (InterruptedException localInterruptedException) {
/* 1069 */             paramQNode.wasInterrupted = true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1074 */     if (paramQNode != null) {
/* 1075 */       if (paramQNode.thread != null)
/* 1076 */         paramQNode.thread = null;
/* 1077 */       if ((paramQNode.wasInterrupted) && (!paramQNode.interruptible))
/* 1078 */         Thread.currentThread().interrupt();
/* 1079 */       if ((k == paramInt) && ((k = (int)(this.state >>> 32)) == paramInt))
/* 1080 */         return abortWait(paramInt);
/*      */     }
/* 1082 */     releaseWaiters(paramInt);
/* 1083 */     return k;
/*      */   }
/*      */   
/*      */ 
/*      */   static final class QNode
/*      */     implements ForkJoinPool.ManagedBlocker
/*      */   {
/*      */     final Phaser phaser;
/*      */     final int phase;
/*      */     final boolean interruptible;
/*      */     final boolean timed;
/*      */     boolean wasInterrupted;
/*      */     long nanos;
/*      */     final long deadline;
/*      */     volatile Thread thread;
/*      */     QNode next;
/*      */     
/*      */     QNode(Phaser paramPhaser, int paramInt, boolean paramBoolean1, boolean paramBoolean2, long paramLong)
/*      */     {
/* 1102 */       this.phaser = paramPhaser;
/* 1103 */       this.phase = paramInt;
/* 1104 */       this.interruptible = paramBoolean1;
/* 1105 */       this.nanos = paramLong;
/* 1106 */       this.timed = paramBoolean2;
/* 1107 */       this.deadline = (paramBoolean2 ? System.nanoTime() + paramLong : 0L);
/* 1108 */       this.thread = Thread.currentThread();
/*      */     }
/*      */     
/*      */     public boolean isReleasable() {
/* 1112 */       if (this.thread == null)
/* 1113 */         return true;
/* 1114 */       if (this.phaser.getPhase() != this.phase) {
/* 1115 */         this.thread = null;
/* 1116 */         return true;
/*      */       }
/* 1118 */       if (Thread.interrupted())
/* 1119 */         this.wasInterrupted = true;
/* 1120 */       if ((this.wasInterrupted) && (this.interruptible)) {
/* 1121 */         this.thread = null;
/* 1122 */         return true;
/*      */       }
/* 1124 */       if (this.timed) {
/* 1125 */         if (this.nanos > 0L) {
/* 1126 */           this.nanos = (this.deadline - System.nanoTime());
/*      */         }
/* 1128 */         if (this.nanos <= 0L) {
/* 1129 */           this.thread = null;
/* 1130 */           return true;
/*      */         }
/*      */       }
/* 1133 */       return false;
/*      */     }
/*      */     
/*      */     public boolean block() {
/* 1137 */       if (isReleasable())
/* 1138 */         return true;
/* 1139 */       if (!this.timed) {
/* 1140 */         LockSupport.park(this);
/* 1141 */       } else if (this.nanos > 0L)
/* 1142 */         LockSupport.parkNanos(this, this.nanos);
/* 1143 */       return isReleasable();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 1153 */       UNSAFE = Unsafe.getUnsafe();
/* 1154 */       Class localClass = Phaser.class;
/*      */       
/* 1156 */       stateOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("state"));
/*      */     } catch (Exception localException) {
/* 1158 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/Phaser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */