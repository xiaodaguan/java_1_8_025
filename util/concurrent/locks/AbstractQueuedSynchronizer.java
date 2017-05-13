/*      */ package java.util.concurrent.locks;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractQueuedSynchronizer
/*      */   extends AbstractOwnableSynchronizer
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7373984972572414691L;
/*      */   private volatile transient Node head;
/*      */   private volatile transient Node tail;
/*      */   private volatile int state;
/*      */   static final long spinForTimeoutThreshold = 1000L;
/*      */   
/*      */   static final class Node
/*      */   {
/*  382 */     static final Node SHARED = new Node();
/*      */     
/*  384 */     static final Node EXCLUSIVE = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int CANCELLED = 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int SIGNAL = -1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int CONDITION = -2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int PROPAGATE = -3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile int waitStatus;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile Node prev;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile Node next;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile Thread thread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Node nextWaiter;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean isShared()
/*      */     {
/*  484 */       return this.nextWaiter == SHARED;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final Node predecessor()
/*      */       throws NullPointerException
/*      */     {
/*  495 */       Node localNode = this.prev;
/*  496 */       if (localNode == null) {
/*  497 */         throw new NullPointerException();
/*      */       }
/*  499 */       return localNode;
/*      */     }
/*      */     
/*      */     Node() {}
/*      */     
/*      */     Node(Thread paramThread, Node paramNode)
/*      */     {
/*  506 */       this.nextWaiter = paramNode;
/*  507 */       this.thread = paramThread;
/*      */     }
/*      */     
/*      */     Node(Thread paramThread, int paramInt) {
/*  511 */       this.waitStatus = paramInt;
/*  512 */       this.thread = paramThread;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final int getState()
/*      */   {
/*  541 */     return this.state;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setState(int paramInt)
/*      */   {
/*  550 */     this.state = paramInt;
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
/*      */   protected final boolean compareAndSetState(int paramInt1, int paramInt2)
/*      */   {
/*  566 */     return unsafe.compareAndSwapInt(this, stateOffset, paramInt1, paramInt2);
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
/*      */   private Node enq(Node paramNode)
/*      */   {
/*      */     for (;;)
/*      */     {
/*  585 */       Node localNode = this.tail;
/*  586 */       if (localNode == null) {
/*  587 */         if (compareAndSetHead(new Node()))
/*  588 */           this.tail = this.head;
/*      */       } else {
/*  590 */         paramNode.prev = localNode;
/*  591 */         if (compareAndSetTail(localNode, paramNode)) {
/*  592 */           localNode.next = paramNode;
/*  593 */           return localNode;
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
/*      */   private Node addWaiter(Node paramNode)
/*      */   {
/*  606 */     Node localNode1 = new Node(Thread.currentThread(), paramNode);
/*      */     
/*  608 */     Node localNode2 = this.tail;
/*  609 */     if (localNode2 != null) {
/*  610 */       localNode1.prev = localNode2;
/*  611 */       if (compareAndSetTail(localNode2, localNode1)) {
/*  612 */         localNode2.next = localNode1;
/*  613 */         return localNode1;
/*      */       }
/*      */     }
/*  616 */     enq(localNode1);
/*  617 */     return localNode1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setHead(Node paramNode)
/*      */   {
/*  628 */     this.head = paramNode;
/*  629 */     paramNode.thread = null;
/*  630 */     paramNode.prev = null;
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
/*      */   private void unparkSuccessor(Node paramNode)
/*      */   {
/*  644 */     int i = paramNode.waitStatus;
/*  645 */     if (i < 0) {
/*  646 */       compareAndSetWaitStatus(paramNode, i, 0);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  654 */     Object localObject = paramNode.next;
/*  655 */     if ((localObject == null) || (((Node)localObject).waitStatus > 0)) {
/*  656 */       localObject = null;
/*  657 */       for (Node localNode = this.tail; (localNode != null) && (localNode != paramNode); localNode = localNode.prev)
/*  658 */         if (localNode.waitStatus <= 0)
/*  659 */           localObject = localNode;
/*      */     }
/*  661 */     if (localObject != null) {
/*  662 */       LockSupport.unpark(((Node)localObject).thread);
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
/*      */   private void doReleaseShared()
/*      */   {
/*      */     for (;;)
/*      */     {
/*  683 */       Node localNode = this.head;
/*  684 */       if ((localNode != null) && (localNode != this.tail)) {
/*  685 */         int i = localNode.waitStatus;
/*  686 */         if (i == -1) {
/*  687 */           if (!compareAndSetWaitStatus(localNode, -1, 0))
/*      */             continue;
/*  689 */           unparkSuccessor(localNode);
/*      */         } else {
/*  691 */           if ((i == 0) && 
/*  692 */             (!compareAndSetWaitStatus(localNode, 0, -3))) continue;
/*      */         }
/*      */       }
/*  695 */       if (localNode == this.head) {
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
/*      */   private void setHeadAndPropagate(Node paramNode, int paramInt)
/*      */   {
/*  709 */     Node localNode1 = this.head;
/*  710 */     setHead(paramNode);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  727 */     if ((paramInt > 0) || (localNode1 == null) || (localNode1.waitStatus < 0) || ((localNode1 = this.head) == null) || (localNode1.waitStatus < 0))
/*      */     {
/*  729 */       Node localNode2 = paramNode.next;
/*  730 */       if ((localNode2 == null) || (localNode2.isShared())) {
/*  731 */         doReleaseShared();
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
/*      */   private void cancelAcquire(Node paramNode)
/*      */   {
/*  744 */     if (paramNode == null) {
/*  745 */       return;
/*      */     }
/*  747 */     paramNode.thread = null;
/*      */     
/*      */ 
/*  750 */     Node localNode1 = paramNode.prev;
/*  751 */     while (localNode1.waitStatus > 0) {
/*  752 */       paramNode.prev = (localNode1 = localNode1.prev);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  757 */     Node localNode2 = localNode1.next;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  762 */     paramNode.waitStatus = 1;
/*      */     
/*      */ 
/*  765 */     if ((paramNode == this.tail) && (compareAndSetTail(paramNode, localNode1))) {
/*  766 */       compareAndSetNext(localNode1, localNode2, null);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  771 */       if (localNode1 != this.head) { int i; if ((i = localNode1.waitStatus) != -1) { if (i <= 0)
/*      */           {
/*  773 */             if (!compareAndSetWaitStatus(localNode1, i, -1)) {} } } else if (localNode1.thread != null)
/*      */         {
/*  775 */           Node localNode3 = paramNode.next;
/*  776 */           if ((localNode3 != null) && (localNode3.waitStatus <= 0))
/*  777 */             compareAndSetNext(localNode1, localNode2, localNode3);
/*      */           break label148; } }
/*  779 */       unparkSuccessor(paramNode);
/*      */       
/*      */       label148:
/*  782 */       paramNode.next = paramNode;
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
/*      */   private static boolean shouldParkAfterFailedAcquire(Node paramNode1, Node paramNode2)
/*      */   {
/*  796 */     int i = paramNode1.waitStatus;
/*  797 */     if (i == -1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  802 */       return true; }
/*  803 */     if (i > 0)
/*      */     {
/*      */ 
/*      */       do
/*      */       {
/*      */ 
/*  809 */         paramNode2.prev = (paramNode1 = paramNode1.prev);
/*  810 */       } while (paramNode1.waitStatus > 0);
/*  811 */       paramNode1.next = paramNode2;
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  818 */       compareAndSetWaitStatus(paramNode1, i, -1);
/*      */     }
/*  820 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void selfInterrupt()
/*      */   {
/*  827 */     Thread.currentThread().interrupt();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final boolean parkAndCheckInterrupt()
/*      */   {
/*  836 */     LockSupport.park(this);
/*  837 */     return Thread.interrupted();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean tryAcquire(int paramInt)
/*      */   {
/* 1076 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryRelease(int paramInt)
/*      */   {
/* 1102 */     throw new UnsupportedOperationException();
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
/*      */   protected int tryAcquireShared(int paramInt)
/*      */   {
/* 1138 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryReleaseShared(int paramInt)
/*      */   {
/* 1163 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean isHeldExclusively()
/*      */   {
/* 1182 */     throw new UnsupportedOperationException();
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
/*      */   public final void acquire(int paramInt)
/*      */   {
/* 1198 */     if ((!tryAcquire(paramInt)) && 
/* 1199 */       (acquireQueued(addWaiter(Node.EXCLUSIVE), paramInt))) {
/* 1200 */       selfInterrupt();
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
/*      */   public final void acquireInterruptibly(int paramInt)
/*      */     throws InterruptedException
/*      */   {
/* 1219 */     if (Thread.interrupted())
/* 1220 */       throw new InterruptedException();
/* 1221 */     if (!tryAcquire(paramInt)) {
/* 1222 */       doAcquireInterruptibly(paramInt);
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
/*      */   public final boolean tryAcquireNanos(int paramInt, long paramLong)
/*      */     throws InterruptedException
/*      */   {
/* 1244 */     if (Thread.interrupted()) {
/* 1245 */       throw new InterruptedException();
/*      */     }
/* 1247 */     return (tryAcquire(paramInt)) || (doAcquireNanos(paramInt, paramLong));
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
/*      */   public final boolean release(int paramInt)
/*      */   {
/* 1261 */     if (tryRelease(paramInt)) {
/* 1262 */       Node localNode = this.head;
/* 1263 */       if ((localNode != null) && (localNode.waitStatus != 0))
/* 1264 */         unparkSuccessor(localNode);
/* 1265 */       return true;
/*      */     }
/* 1267 */     return false;
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
/*      */   public final void acquireShared(int paramInt)
/*      */   {
/* 1282 */     if (tryAcquireShared(paramInt) < 0) {
/* 1283 */       doAcquireShared(paramInt);
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
/*      */   public final void acquireSharedInterruptibly(int paramInt)
/*      */     throws InterruptedException
/*      */   {
/* 1301 */     if (Thread.interrupted())
/* 1302 */       throw new InterruptedException();
/* 1303 */     if (tryAcquireShared(paramInt) < 0) {
/* 1304 */       doAcquireSharedInterruptibly(paramInt);
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
/*      */   public final boolean tryAcquireSharedNanos(int paramInt, long paramLong)
/*      */     throws InterruptedException
/*      */   {
/* 1325 */     if (Thread.interrupted()) {
/* 1326 */       throw new InterruptedException();
/*      */     }
/* 1328 */     return (tryAcquireShared(paramInt) >= 0) || (doAcquireSharedNanos(paramInt, paramLong));
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
/*      */   public final boolean releaseShared(int paramInt)
/*      */   {
/* 1341 */     if (tryReleaseShared(paramInt)) {
/* 1342 */       doReleaseShared();
/* 1343 */       return true;
/*      */     }
/* 1345 */     return false;
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
/*      */   public final boolean hasQueuedThreads()
/*      */   {
/* 1362 */     return this.head != this.tail;
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
/*      */   public final boolean hasContended()
/*      */   {
/* 1375 */     return this.head != null;
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
/*      */   public final Thread getFirstQueuedThread()
/*      */   {
/* 1391 */     return this.head == this.tail ? null : fullGetFirstQueuedThread();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Thread fullGetFirstQueuedThread()
/*      */   {
/*      */     Node localNode1;
/*      */     
/*      */ 
/*      */     Node localNode2;
/*      */     
/*      */ 
/*      */     Thread localThread1;
/*      */     
/*      */ 
/* 1408 */     if ((((localNode1 = this.head) != null) && ((localNode2 = localNode1.next) != null) && (localNode2.prev == this.head) && ((localThread1 = localNode2.thread) != null)) || (((localNode1 = this.head) != null) && ((localNode2 = localNode1.next) != null) && (localNode2.prev == this.head) && ((localThread1 = localNode2.thread) != null)))
/*      */     {
/*      */ 
/*      */ 
/* 1412 */       return localThread1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1422 */     Node localNode3 = this.tail;
/* 1423 */     Object localObject = null;
/* 1424 */     while ((localNode3 != null) && (localNode3 != this.head)) {
/* 1425 */       Thread localThread2 = localNode3.thread;
/* 1426 */       if (localThread2 != null)
/* 1427 */         localObject = localThread2;
/* 1428 */       localNode3 = localNode3.prev;
/*      */     }
/* 1430 */     return (Thread)localObject;
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
/*      */   public final boolean isQueued(Thread paramThread)
/*      */   {
/* 1444 */     if (paramThread == null)
/* 1445 */       throw new NullPointerException();
/* 1446 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev)
/* 1447 */       if (localNode.thread == paramThread)
/* 1448 */         return true;
/* 1449 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean apparentlyFirstQueuedIsExclusive()
/*      */   {
/*      */     Node localNode1;
/*      */     
/*      */ 
/*      */     Node localNode2;
/*      */     
/*      */ 
/* 1463 */     if (((localNode1 = this.head) != null) && ((localNode2 = localNode1.next) != null)) {}
/*      */     
/* 1465 */     return (!localNode2.isShared()) && (localNode2.thread != null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean hasQueuedPredecessors()
/*      */   {
/* 1516 */     Node localNode1 = this.tail;
/* 1517 */     Node localNode2 = this.head;
/*      */     
/*      */     Node localNode3;
/* 1520 */     return (localNode2 != localNode1) && (((localNode3 = localNode2.next) == null) || (localNode3.thread != Thread.currentThread()));
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
/*      */   public final int getQueueLength()
/*      */   {
/* 1537 */     int i = 0;
/* 1538 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1539 */       if (localNode.thread != null)
/* 1540 */         i++;
/*      */     }
/* 1542 */     return i;
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
/*      */   public final Collection<Thread> getQueuedThreads()
/*      */   {
/* 1557 */     ArrayList localArrayList = new ArrayList();
/* 1558 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1559 */       Thread localThread = localNode.thread;
/* 1560 */       if (localThread != null)
/* 1561 */         localArrayList.add(localThread);
/*      */     }
/* 1563 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Collection<Thread> getExclusiveQueuedThreads()
/*      */   {
/* 1575 */     ArrayList localArrayList = new ArrayList();
/* 1576 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1577 */       if (!localNode.isShared()) {
/* 1578 */         Thread localThread = localNode.thread;
/* 1579 */         if (localThread != null)
/* 1580 */           localArrayList.add(localThread);
/*      */       }
/*      */     }
/* 1583 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Collection<Thread> getSharedQueuedThreads()
/*      */   {
/* 1595 */     ArrayList localArrayList = new ArrayList();
/* 1596 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1597 */       if (localNode.isShared()) {
/* 1598 */         Thread localThread = localNode.thread;
/* 1599 */         if (localThread != null)
/* 1600 */           localArrayList.add(localThread);
/*      */       }
/*      */     }
/* 1603 */     return localArrayList;
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
/* 1616 */     int i = getState();
/* 1617 */     String str = hasQueuedThreads() ? "non" : "";
/* 1618 */     return super.toString() + "[State = " + i + ", " + str + "empty queue]";
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
/*      */   final boolean isOnSyncQueue(Node paramNode)
/*      */   {
/* 1632 */     if ((paramNode.waitStatus == -2) || (paramNode.prev == null))
/* 1633 */       return false;
/* 1634 */     if (paramNode.next != null) {
/* 1635 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1644 */     return findNodeFromTail(paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean findNodeFromTail(Node paramNode)
/*      */   {
/* 1653 */     Node localNode = this.tail;
/*      */     for (;;) {
/* 1655 */       if (localNode == paramNode)
/* 1656 */         return true;
/* 1657 */       if (localNode == null)
/* 1658 */         return false;
/* 1659 */       localNode = localNode.prev;
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
/*      */   final boolean transferForSignal(Node paramNode)
/*      */   {
/* 1674 */     if (!compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1675 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1683 */     Node localNode = enq(paramNode);
/* 1684 */     int i = localNode.waitStatus;
/* 1685 */     if ((i > 0) || (!compareAndSetWaitStatus(localNode, i, -1)))
/* 1686 */       LockSupport.unpark(paramNode.thread);
/* 1687 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean transferAfterCancelledWait(Node paramNode)
/*      */   {
/* 1698 */     if (compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1699 */       enq(paramNode);
/* 1700 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1708 */     while (!isOnSyncQueue(paramNode))
/* 1709 */       Thread.yield();
/* 1710 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int fullyRelease(Node paramNode)
/*      */   {
/* 1720 */     int i = 1;
/*      */     try {
/* 1722 */       int j = getState();
/* 1723 */       if (release(j)) {
/* 1724 */         i = 0;
/* 1725 */         return j;
/*      */       }
/* 1727 */       throw new IllegalMonitorStateException();
/*      */     }
/*      */     finally {
/* 1730 */       if (i != 0) {
/* 1731 */         paramNode.waitStatus = 1;
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
/*      */   public final boolean owns(ConditionObject paramConditionObject)
/*      */   {
/* 1746 */     return paramConditionObject.isOwnedBy(this);
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
/*      */   public final boolean hasWaiters(ConditionObject paramConditionObject)
/*      */   {
/* 1766 */     if (!owns(paramConditionObject))
/* 1767 */       throw new IllegalArgumentException("Not owner");
/* 1768 */     return paramConditionObject.hasWaiters();
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
/*      */   public final int getWaitQueueLength(ConditionObject paramConditionObject)
/*      */   {
/* 1788 */     if (!owns(paramConditionObject))
/* 1789 */       throw new IllegalArgumentException("Not owner");
/* 1790 */     return paramConditionObject.getWaitQueueLength();
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
/*      */   public final Collection<Thread> getWaitingThreads(ConditionObject paramConditionObject)
/*      */   {
/* 1810 */     if (!owns(paramConditionObject))
/* 1811 */       throw new IllegalArgumentException("Not owner");
/* 1812 */     return paramConditionObject.getWaitingThreads();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public class ConditionObject
/*      */     implements Condition, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1173984872572414699L;
/*      */     
/*      */ 
/*      */ 
/*      */     private transient AbstractQueuedSynchronizer.Node firstWaiter;
/*      */     
/*      */ 
/*      */ 
/*      */     private transient AbstractQueuedSynchronizer.Node lastWaiter;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int REINTERRUPT = 1;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int THROW_IE = -1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public ConditionObject() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private AbstractQueuedSynchronizer.Node addConditionWaiter()
/*      */     {
/* 1849 */       AbstractQueuedSynchronizer.Node localNode1 = this.lastWaiter;
/*      */       
/* 1851 */       if ((localNode1 != null) && (localNode1.waitStatus != -2)) {
/* 1852 */         unlinkCancelledWaiters();
/* 1853 */         localNode1 = this.lastWaiter;
/*      */       }
/* 1855 */       AbstractQueuedSynchronizer.Node localNode2 = new AbstractQueuedSynchronizer.Node(Thread.currentThread(), -2);
/* 1856 */       if (localNode1 == null) {
/* 1857 */         this.firstWaiter = localNode2;
/*      */       } else
/* 1859 */         localNode1.nextWaiter = localNode2;
/* 1860 */       this.lastWaiter = localNode2;
/* 1861 */       return localNode2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void doSignal(AbstractQueuedSynchronizer.Node paramNode)
/*      */     {
/*      */       do
/*      */       {
/* 1872 */         if ((this.firstWaiter = paramNode.nextWaiter) == null)
/* 1873 */           this.lastWaiter = null;
/* 1874 */         paramNode.nextWaiter = null;
/* 1875 */       } while ((!AbstractQueuedSynchronizer.this.transferForSignal(paramNode)) && ((paramNode = this.firstWaiter) != null));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void doSignalAll(AbstractQueuedSynchronizer.Node paramNode)
/*      */     {
/* 1884 */       this.lastWaiter = (this.firstWaiter = null);
/*      */       do {
/* 1886 */         AbstractQueuedSynchronizer.Node localNode = paramNode.nextWaiter;
/* 1887 */         paramNode.nextWaiter = null;
/* 1888 */         AbstractQueuedSynchronizer.this.transferForSignal(paramNode);
/* 1889 */         paramNode = localNode;
/* 1890 */       } while (paramNode != null);
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
/*      */     private void unlinkCancelledWaiters()
/*      */     {
/* 1908 */       Object localObject1 = this.firstWaiter;
/* 1909 */       Object localObject2 = null;
/* 1910 */       while (localObject1 != null) {
/* 1911 */         AbstractQueuedSynchronizer.Node localNode = ((AbstractQueuedSynchronizer.Node)localObject1).nextWaiter;
/* 1912 */         if (((AbstractQueuedSynchronizer.Node)localObject1).waitStatus != -2) {
/* 1913 */           ((AbstractQueuedSynchronizer.Node)localObject1).nextWaiter = null;
/* 1914 */           if (localObject2 == null) {
/* 1915 */             this.firstWaiter = localNode;
/*      */           } else
/* 1917 */             ((AbstractQueuedSynchronizer.Node)localObject2).nextWaiter = localNode;
/* 1918 */           if (localNode == null) {
/* 1919 */             this.lastWaiter = ((AbstractQueuedSynchronizer.Node)localObject2);
/*      */           }
/*      */         } else {
/* 1922 */           localObject2 = localObject1; }
/* 1923 */         localObject1 = localNode;
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
/*      */     public final void signal()
/*      */     {
/* 1938 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 1939 */         throw new IllegalMonitorStateException();
/* 1940 */       AbstractQueuedSynchronizer.Node localNode = this.firstWaiter;
/* 1941 */       if (localNode != null) {
/* 1942 */         doSignal(localNode);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void signalAll()
/*      */     {
/* 1953 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 1954 */         throw new IllegalMonitorStateException();
/* 1955 */       AbstractQueuedSynchronizer.Node localNode = this.firstWaiter;
/* 1956 */       if (localNode != null) {
/* 1957 */         doSignalAll(localNode);
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
/*      */     public final void awaitUninterruptibly()
/*      */     {
/* 1972 */       AbstractQueuedSynchronizer.Node localNode = addConditionWaiter();
/* 1973 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(localNode);
/* 1974 */       int j = 0;
/* 1975 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(localNode)) {
/* 1976 */         LockSupport.park(this);
/* 1977 */         if (Thread.interrupted())
/* 1978 */           j = 1;
/*      */       }
/* 1980 */       if ((AbstractQueuedSynchronizer.this.acquireQueued(localNode, i)) || (j != 0)) {
/* 1981 */         AbstractQueuedSynchronizer.selfInterrupt();
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
/*      */     private int checkInterruptWhileWaiting(AbstractQueuedSynchronizer.Node paramNode)
/*      */     {
/* 2003 */       return Thread.interrupted() ? 1 : AbstractQueuedSynchronizer.this.transferAfterCancelledWait(paramNode) ? -1 : 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void reportInterruptAfterWait(int paramInt)
/*      */       throws InterruptedException
/*      */     {
/* 2013 */       if (paramInt == -1)
/* 2014 */         throw new InterruptedException();
/* 2015 */       if (paramInt == 1) {
/* 2016 */         AbstractQueuedSynchronizer.selfInterrupt();
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
/*      */     public final void await()
/*      */       throws InterruptedException
/*      */     {
/* 2033 */       if (Thread.interrupted())
/* 2034 */         throw new InterruptedException();
/* 2035 */       AbstractQueuedSynchronizer.Node localNode = addConditionWaiter();
/* 2036 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(localNode);
/* 2037 */       int j = 0;
/* 2038 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(localNode)) {
/* 2039 */         LockSupport.park(this);
/* 2040 */         if ((j = checkInterruptWhileWaiting(localNode)) != 0)
/*      */           break;
/*      */       }
/* 2043 */       if ((AbstractQueuedSynchronizer.this.acquireQueued(localNode, i)) && (j != -1))
/* 2044 */         j = 1;
/* 2045 */       if (localNode.nextWaiter != null)
/* 2046 */         unlinkCancelledWaiters();
/* 2047 */       if (j != 0) {
/* 2048 */         reportInterruptAfterWait(j);
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
/*      */     public final long awaitNanos(long paramLong)
/*      */       throws InterruptedException
/*      */     {
/* 2066 */       if (Thread.interrupted())
/* 2067 */         throw new InterruptedException();
/* 2068 */       AbstractQueuedSynchronizer.Node localNode = addConditionWaiter();
/* 2069 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(localNode);
/* 2070 */       long l = System.nanoTime() + paramLong;
/* 2071 */       int j = 0;
/* 2072 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(localNode)) {
/* 2073 */         if (paramLong <= 0L) {
/* 2074 */           AbstractQueuedSynchronizer.this.transferAfterCancelledWait(localNode);
/* 2075 */           break;
/*      */         }
/* 2077 */         if (paramLong >= 1000L)
/* 2078 */           LockSupport.parkNanos(this, paramLong);
/* 2079 */         if ((j = checkInterruptWhileWaiting(localNode)) != 0)
/*      */           break;
/* 2081 */         paramLong = l - System.nanoTime();
/*      */       }
/* 2083 */       if ((AbstractQueuedSynchronizer.this.acquireQueued(localNode, i)) && (j != -1))
/* 2084 */         j = 1;
/* 2085 */       if (localNode.nextWaiter != null)
/* 2086 */         unlinkCancelledWaiters();
/* 2087 */       if (j != 0)
/* 2088 */         reportInterruptAfterWait(j);
/* 2089 */       return l - System.nanoTime();
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
/*      */     public final boolean awaitUntil(Date paramDate)
/*      */       throws InterruptedException
/*      */     {
/* 2108 */       long l = paramDate.getTime();
/* 2109 */       if (Thread.interrupted())
/* 2110 */         throw new InterruptedException();
/* 2111 */       AbstractQueuedSynchronizer.Node localNode = addConditionWaiter();
/* 2112 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(localNode);
/* 2113 */       boolean bool = false;
/* 2114 */       int j = 0;
/* 2115 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(localNode))
/* 2116 */         if (System.currentTimeMillis() > l) {
/* 2117 */           bool = AbstractQueuedSynchronizer.this.transferAfterCancelledWait(localNode);
/*      */         }
/*      */         else {
/* 2120 */           LockSupport.parkUntil(this, l);
/* 2121 */           if ((j = checkInterruptWhileWaiting(localNode)) != 0)
/*      */             break;
/*      */         }
/* 2124 */       if ((AbstractQueuedSynchronizer.this.acquireQueued(localNode, i)) && (j != -1))
/* 2125 */         j = 1;
/* 2126 */       if (localNode.nextWaiter != null)
/* 2127 */         unlinkCancelledWaiters();
/* 2128 */       if (j != 0)
/* 2129 */         reportInterruptAfterWait(j);
/* 2130 */       return !bool;
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
/*      */     public final boolean await(long paramLong, TimeUnit paramTimeUnit)
/*      */       throws InterruptedException
/*      */     {
/* 2149 */       long l1 = paramTimeUnit.toNanos(paramLong);
/* 2150 */       if (Thread.interrupted())
/* 2151 */         throw new InterruptedException();
/* 2152 */       AbstractQueuedSynchronizer.Node localNode = addConditionWaiter();
/* 2153 */       int i = AbstractQueuedSynchronizer.this.fullyRelease(localNode);
/* 2154 */       long l2 = System.nanoTime() + l1;
/* 2155 */       boolean bool = false;
/* 2156 */       int j = 0;
/* 2157 */       while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(localNode)) {
/* 2158 */         if (l1 <= 0L) {
/* 2159 */           bool = AbstractQueuedSynchronizer.this.transferAfterCancelledWait(localNode);
/* 2160 */           break;
/*      */         }
/* 2162 */         if (l1 >= 1000L)
/* 2163 */           LockSupport.parkNanos(this, l1);
/* 2164 */         if ((j = checkInterruptWhileWaiting(localNode)) != 0)
/*      */           break;
/* 2166 */         l1 = l2 - System.nanoTime();
/*      */       }
/* 2168 */       if ((AbstractQueuedSynchronizer.this.acquireQueued(localNode, i)) && (j != -1))
/* 2169 */         j = 1;
/* 2170 */       if (localNode.nextWaiter != null)
/* 2171 */         unlinkCancelledWaiters();
/* 2172 */       if (j != 0)
/* 2173 */         reportInterruptAfterWait(j);
/* 2174 */       return !bool;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean isOwnedBy(AbstractQueuedSynchronizer paramAbstractQueuedSynchronizer)
/*      */     {
/* 2186 */       return paramAbstractQueuedSynchronizer == AbstractQueuedSynchronizer.this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected final boolean hasWaiters()
/*      */     {
/* 2198 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 2199 */         throw new IllegalMonitorStateException();
/* 2200 */       for (AbstractQueuedSynchronizer.Node localNode = this.firstWaiter; localNode != null; localNode = localNode.nextWaiter) {
/* 2201 */         if (localNode.waitStatus == -2)
/* 2202 */           return true;
/*      */       }
/* 2204 */       return false;
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
/*      */     protected final int getWaitQueueLength()
/*      */     {
/* 2217 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 2218 */         throw new IllegalMonitorStateException();
/* 2219 */       int i = 0;
/* 2220 */       for (AbstractQueuedSynchronizer.Node localNode = this.firstWaiter; localNode != null; localNode = localNode.nextWaiter) {
/* 2221 */         if (localNode.waitStatus == -2)
/* 2222 */           i++;
/*      */       }
/* 2224 */       return i;
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
/*      */     protected final Collection<Thread> getWaitingThreads()
/*      */     {
/* 2237 */       if (!AbstractQueuedSynchronizer.this.isHeldExclusively())
/* 2238 */         throw new IllegalMonitorStateException();
/* 2239 */       ArrayList localArrayList = new ArrayList();
/* 2240 */       for (AbstractQueuedSynchronizer.Node localNode = this.firstWaiter; localNode != null; localNode = localNode.nextWaiter) {
/* 2241 */         if (localNode.waitStatus == -2) {
/* 2242 */           Thread localThread = localNode.thread;
/* 2243 */           if (localThread != null)
/* 2244 */             localArrayList.add(localThread);
/*      */         }
/*      */       }
/* 2247 */       return localArrayList;
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
/* 2260 */   private static final Unsafe unsafe = ;
/*      */   private static final long stateOffset;
/*      */   private static final long headOffset;
/*      */   private static final long tailOffset;
/*      */   private static final long waitStatusOffset;
/*      */   private static final long nextOffset;
/*      */   
/*      */   static
/*      */   {
/*      */     try {
/* 2270 */       stateOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("state"));
/*      */       
/* 2272 */       headOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("head"));
/*      */       
/* 2274 */       tailOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
/*      */       
/* 2276 */       waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
/*      */       
/* 2278 */       nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
/*      */     } catch (Exception localException) {
/* 2280 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private final boolean compareAndSetHead(Node paramNode)
/*      */   {
/* 2287 */     return unsafe.compareAndSwapObject(this, headOffset, null, paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final boolean compareAndSetTail(Node paramNode1, Node paramNode2)
/*      */   {
/* 2294 */     return unsafe.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final boolean compareAndSetWaitStatus(Node paramNode, int paramInt1, int paramInt2)
/*      */   {
/* 2303 */     return unsafe.compareAndSwapInt(paramNode, waitStatusOffset, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final boolean compareAndSetNext(Node paramNode1, Node paramNode2, Node paramNode3)
/*      */   {
/* 2313 */     return unsafe.compareAndSwapObject(paramNode1, nextOffset, paramNode2, paramNode3);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   final boolean acquireQueued(Node paramNode, int paramInt)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: iconst_1
/*      */     //   1: istore_3
/*      */     //   2: iconst_0
/*      */     //   3: istore 4
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   9: astore 5
/*      */     //   11: aload 5
/*      */     //   13: aload_0
/*      */     //   14: getfield 10	java/util/concurrent/locks/AbstractQueuedSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   17: if_acmpne +40 -> 57
/*      */     //   20: aload_0
/*      */     //   21: iload_2
/*      */     //   22: invokevirtual 30	java/util/concurrent/locks/AbstractQueuedSynchronizer:tryAcquire	(I)Z
/*      */     //   25: ifeq +32 -> 57
/*      */     //   28: aload_0
/*      */     //   29: aload_1
/*      */     //   30: invokespecial 22	java/util/concurrent/locks/AbstractQueuedSynchronizer:setHead	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   33: aload 5
/*      */     //   35: aconst_null
/*      */     //   36: putfield 13	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   39: iconst_0
/*      */     //   40: istore_3
/*      */     //   41: iload 4
/*      */     //   43: istore 6
/*      */     //   45: iload_3
/*      */     //   46: ifeq +8 -> 54
/*      */     //   49: aload_0
/*      */     //   50: aload_1
/*      */     //   51: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   54: iload 6
/*      */     //   56: ireturn
/*      */     //   57: aload 5
/*      */     //   59: aload_1
/*      */     //   60: invokestatic 32	java/util/concurrent/locks/AbstractQueuedSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Z
/*      */     //   63: ifeq +13 -> 76
/*      */     //   66: aload_0
/*      */     //   67: invokespecial 33	java/util/concurrent/locks/AbstractQueuedSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   70: ifeq +6 -> 76
/*      */     //   73: iconst_1
/*      */     //   74: istore 4
/*      */     //   76: goto -71 -> 5
/*      */     //   79: astore 7
/*      */     //   81: iload_3
/*      */     //   82: ifeq +8 -> 90
/*      */     //   85: aload_0
/*      */     //   86: aload_1
/*      */     //   87: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   90: aload 7
/*      */     //   92: athrow
/*      */     // Line number table:
/*      */     //   Java source line #858	-> byte code offset #0
/*      */     //   Java source line #860	-> byte code offset #2
/*      */     //   Java source line #862	-> byte code offset #5
/*      */     //   Java source line #863	-> byte code offset #11
/*      */     //   Java source line #864	-> byte code offset #28
/*      */     //   Java source line #865	-> byte code offset #33
/*      */     //   Java source line #866	-> byte code offset #39
/*      */     //   Java source line #867	-> byte code offset #41
/*      */     //   Java source line #874	-> byte code offset #45
/*      */     //   Java source line #875	-> byte code offset #49
/*      */     //   Java source line #869	-> byte code offset #57
/*      */     //   Java source line #870	-> byte code offset #67
/*      */     //   Java source line #871	-> byte code offset #73
/*      */     //   Java source line #872	-> byte code offset #76
/*      */     //   Java source line #874	-> byte code offset #79
/*      */     //   Java source line #875	-> byte code offset #85
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	93	0	this	AbstractQueuedSynchronizer
/*      */     //   0	93	1	paramNode	Node
/*      */     //   0	93	2	paramInt	int
/*      */     //   1	81	3	i	int
/*      */     //   3	72	4	bool1	boolean
/*      */     //   9	49	5	localNode	Node
/*      */     //   43	12	6	bool2	boolean
/*      */     //   79	12	7	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   2	45	79	finally
/*      */     //   57	81	79	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void doAcquireInterruptibly(int paramInt)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 34	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:EXCLUSIVE	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   4: invokespecial 35	java/util/concurrent/locks/AbstractQueuedSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   7: astore_2
/*      */     //   8: iconst_1
/*      */     //   9: istore_3
/*      */     //   10: aload_2
/*      */     //   11: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   14: astore 4
/*      */     //   16: aload 4
/*      */     //   18: aload_0
/*      */     //   19: getfield 10	java/util/concurrent/locks/AbstractQueuedSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   22: if_acmpne +34 -> 56
/*      */     //   25: aload_0
/*      */     //   26: iload_1
/*      */     //   27: invokevirtual 30	java/util/concurrent/locks/AbstractQueuedSynchronizer:tryAcquire	(I)Z
/*      */     //   30: ifeq +26 -> 56
/*      */     //   33: aload_0
/*      */     //   34: aload_2
/*      */     //   35: invokespecial 22	java/util/concurrent/locks/AbstractQueuedSynchronizer:setHead	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   38: aload 4
/*      */     //   40: aconst_null
/*      */     //   41: putfield 13	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   44: iconst_0
/*      */     //   45: istore_3
/*      */     //   46: iload_3
/*      */     //   47: ifeq +8 -> 55
/*      */     //   50: aload_0
/*      */     //   51: aload_2
/*      */     //   52: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   55: return
/*      */     //   56: aload 4
/*      */     //   58: aload_2
/*      */     //   59: invokestatic 32	java/util/concurrent/locks/AbstractQueuedSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Z
/*      */     //   62: ifeq +18 -> 80
/*      */     //   65: aload_0
/*      */     //   66: invokespecial 33	java/util/concurrent/locks/AbstractQueuedSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   69: ifeq +11 -> 80
/*      */     //   72: new 36	java/lang/InterruptedException
/*      */     //   75: dup
/*      */     //   76: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   79: athrow
/*      */     //   80: goto -70 -> 10
/*      */     //   83: astore 5
/*      */     //   85: iload_3
/*      */     //   86: ifeq +8 -> 94
/*      */     //   89: aload_0
/*      */     //   90: aload_2
/*      */     //   91: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   94: aload 5
/*      */     //   96: athrow
/*      */     // Line number table:
/*      */     //   Java source line #885	-> byte code offset #0
/*      */     //   Java source line #886	-> byte code offset #8
/*      */     //   Java source line #889	-> byte code offset #10
/*      */     //   Java source line #890	-> byte code offset #16
/*      */     //   Java source line #891	-> byte code offset #33
/*      */     //   Java source line #892	-> byte code offset #38
/*      */     //   Java source line #893	-> byte code offset #44
/*      */     //   Java source line #901	-> byte code offset #46
/*      */     //   Java source line #902	-> byte code offset #50
/*      */     //   Java source line #894	-> byte code offset #55
/*      */     //   Java source line #896	-> byte code offset #56
/*      */     //   Java source line #897	-> byte code offset #66
/*      */     //   Java source line #898	-> byte code offset #72
/*      */     //   Java source line #899	-> byte code offset #80
/*      */     //   Java source line #901	-> byte code offset #83
/*      */     //   Java source line #902	-> byte code offset #89
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	97	0	this	AbstractQueuedSynchronizer
/*      */     //   0	97	1	paramInt	int
/*      */     //   7	84	2	localNode1	Node
/*      */     //   9	77	3	i	int
/*      */     //   14	43	4	localNode2	Node
/*      */     //   83	12	5	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   10	46	83	finally
/*      */     //   56	85	83	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private boolean doAcquireNanos(int paramInt, long paramLong)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: lload_2
/*      */     //   1: lconst_0
/*      */     //   2: lcmp
/*      */     //   3: ifgt +5 -> 8
/*      */     //   6: iconst_0
/*      */     //   7: ireturn
/*      */     //   8: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   11: lload_2
/*      */     //   12: ladd
/*      */     //   13: lstore 4
/*      */     //   15: aload_0
/*      */     //   16: getstatic 34	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:EXCLUSIVE	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   19: invokespecial 35	java/util/concurrent/locks/AbstractQueuedSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   22: astore 6
/*      */     //   24: iconst_1
/*      */     //   25: istore 7
/*      */     //   27: aload 6
/*      */     //   29: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   32: astore 8
/*      */     //   34: aload 8
/*      */     //   36: aload_0
/*      */     //   37: getfield 10	java/util/concurrent/locks/AbstractQueuedSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   40: if_acmpne +43 -> 83
/*      */     //   43: aload_0
/*      */     //   44: iload_1
/*      */     //   45: invokevirtual 30	java/util/concurrent/locks/AbstractQueuedSynchronizer:tryAcquire	(I)Z
/*      */     //   48: ifeq +35 -> 83
/*      */     //   51: aload_0
/*      */     //   52: aload 6
/*      */     //   54: invokespecial 22	java/util/concurrent/locks/AbstractQueuedSynchronizer:setHead	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   57: aload 8
/*      */     //   59: aconst_null
/*      */     //   60: putfield 13	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   63: iconst_0
/*      */     //   64: istore 7
/*      */     //   66: iconst_1
/*      */     //   67: istore 9
/*      */     //   69: iload 7
/*      */     //   71: ifeq +9 -> 80
/*      */     //   74: aload_0
/*      */     //   75: aload 6
/*      */     //   77: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   80: iload 9
/*      */     //   82: ireturn
/*      */     //   83: lload 4
/*      */     //   85: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   88: lsub
/*      */     //   89: lstore_2
/*      */     //   90: lload_2
/*      */     //   91: lconst_0
/*      */     //   92: lcmp
/*      */     //   93: ifgt +20 -> 113
/*      */     //   96: iconst_0
/*      */     //   97: istore 9
/*      */     //   99: iload 7
/*      */     //   101: ifeq +9 -> 110
/*      */     //   104: aload_0
/*      */     //   105: aload 6
/*      */     //   107: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   110: iload 9
/*      */     //   112: ireturn
/*      */     //   113: aload 8
/*      */     //   115: aload 6
/*      */     //   117: invokestatic 32	java/util/concurrent/locks/AbstractQueuedSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Z
/*      */     //   120: ifeq +16 -> 136
/*      */     //   123: lload_2
/*      */     //   124: ldc2_w 40
/*      */     //   127: lcmp
/*      */     //   128: ifle +8 -> 136
/*      */     //   131: aload_0
/*      */     //   132: lload_2
/*      */     //   133: invokestatic 42	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
/*      */     //   136: invokestatic 28	java/lang/Thread:interrupted	()Z
/*      */     //   139: ifeq +11 -> 150
/*      */     //   142: new 36	java/lang/InterruptedException
/*      */     //   145: dup
/*      */     //   146: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   149: athrow
/*      */     //   150: goto -123 -> 27
/*      */     //   153: astore 10
/*      */     //   155: iload 7
/*      */     //   157: ifeq +9 -> 166
/*      */     //   160: aload_0
/*      */     //   161: aload 6
/*      */     //   163: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   166: aload 10
/*      */     //   168: athrow
/*      */     // Line number table:
/*      */     //   Java source line #915	-> byte code offset #0
/*      */     //   Java source line #916	-> byte code offset #6
/*      */     //   Java source line #917	-> byte code offset #8
/*      */     //   Java source line #918	-> byte code offset #15
/*      */     //   Java source line #919	-> byte code offset #24
/*      */     //   Java source line #922	-> byte code offset #27
/*      */     //   Java source line #923	-> byte code offset #34
/*      */     //   Java source line #924	-> byte code offset #51
/*      */     //   Java source line #925	-> byte code offset #57
/*      */     //   Java source line #926	-> byte code offset #63
/*      */     //   Java source line #927	-> byte code offset #66
/*      */     //   Java source line #939	-> byte code offset #69
/*      */     //   Java source line #940	-> byte code offset #74
/*      */     //   Java source line #929	-> byte code offset #83
/*      */     //   Java source line #930	-> byte code offset #90
/*      */     //   Java source line #931	-> byte code offset #96
/*      */     //   Java source line #939	-> byte code offset #99
/*      */     //   Java source line #940	-> byte code offset #104
/*      */     //   Java source line #932	-> byte code offset #113
/*      */     //   Java source line #934	-> byte code offset #131
/*      */     //   Java source line #935	-> byte code offset #136
/*      */     //   Java source line #936	-> byte code offset #142
/*      */     //   Java source line #937	-> byte code offset #150
/*      */     //   Java source line #939	-> byte code offset #153
/*      */     //   Java source line #940	-> byte code offset #160
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	169	0	this	AbstractQueuedSynchronizer
/*      */     //   0	169	1	paramInt	int
/*      */     //   0	169	2	paramLong	long
/*      */     //   13	71	4	l	long
/*      */     //   22	140	6	localNode1	Node
/*      */     //   25	131	7	i	int
/*      */     //   32	82	8	localNode2	Node
/*      */     //   67	44	9	bool	boolean
/*      */     //   153	14	10	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   27	69	153	finally
/*      */     //   83	99	153	finally
/*      */     //   113	155	153	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void doAcquireShared(int paramInt)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 43	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:SHARED	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   4: invokespecial 35	java/util/concurrent/locks/AbstractQueuedSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   7: astore_2
/*      */     //   8: iconst_1
/*      */     //   9: istore_3
/*      */     //   10: iconst_0
/*      */     //   11: istore 4
/*      */     //   13: aload_2
/*      */     //   14: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   17: astore 5
/*      */     //   19: aload 5
/*      */     //   21: aload_0
/*      */     //   22: getfield 10	java/util/concurrent/locks/AbstractQueuedSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   25: if_acmpne +48 -> 73
/*      */     //   28: aload_0
/*      */     //   29: iload_1
/*      */     //   30: invokevirtual 44	java/util/concurrent/locks/AbstractQueuedSynchronizer:tryAcquireShared	(I)I
/*      */     //   33: istore 6
/*      */     //   35: iload 6
/*      */     //   37: iflt +36 -> 73
/*      */     //   40: aload_0
/*      */     //   41: aload_2
/*      */     //   42: iload 6
/*      */     //   44: invokespecial 45	java/util/concurrent/locks/AbstractQueuedSynchronizer:setHeadAndPropagate	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;I)V
/*      */     //   47: aload 5
/*      */     //   49: aconst_null
/*      */     //   50: putfield 13	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   53: iload 4
/*      */     //   55: ifeq +6 -> 61
/*      */     //   58: invokestatic 46	java/util/concurrent/locks/AbstractQueuedSynchronizer:selfInterrupt	()V
/*      */     //   61: iconst_0
/*      */     //   62: istore_3
/*      */     //   63: iload_3
/*      */     //   64: ifeq +8 -> 72
/*      */     //   67: aload_0
/*      */     //   68: aload_2
/*      */     //   69: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   72: return
/*      */     //   73: aload 5
/*      */     //   75: aload_2
/*      */     //   76: invokestatic 32	java/util/concurrent/locks/AbstractQueuedSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Z
/*      */     //   79: ifeq +13 -> 92
/*      */     //   82: aload_0
/*      */     //   83: invokespecial 33	java/util/concurrent/locks/AbstractQueuedSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   86: ifeq +6 -> 92
/*      */     //   89: iconst_1
/*      */     //   90: istore 4
/*      */     //   92: goto -79 -> 13
/*      */     //   95: astore 7
/*      */     //   97: iload_3
/*      */     //   98: ifeq +8 -> 106
/*      */     //   101: aload_0
/*      */     //   102: aload_2
/*      */     //   103: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   106: aload 7
/*      */     //   108: athrow
/*      */     // Line number table:
/*      */     //   Java source line #949	-> byte code offset #0
/*      */     //   Java source line #950	-> byte code offset #8
/*      */     //   Java source line #952	-> byte code offset #10
/*      */     //   Java source line #954	-> byte code offset #13
/*      */     //   Java source line #955	-> byte code offset #19
/*      */     //   Java source line #956	-> byte code offset #28
/*      */     //   Java source line #957	-> byte code offset #35
/*      */     //   Java source line #958	-> byte code offset #40
/*      */     //   Java source line #959	-> byte code offset #47
/*      */     //   Java source line #960	-> byte code offset #53
/*      */     //   Java source line #961	-> byte code offset #58
/*      */     //   Java source line #962	-> byte code offset #61
/*      */     //   Java source line #971	-> byte code offset #63
/*      */     //   Java source line #972	-> byte code offset #67
/*      */     //   Java source line #963	-> byte code offset #72
/*      */     //   Java source line #966	-> byte code offset #73
/*      */     //   Java source line #967	-> byte code offset #83
/*      */     //   Java source line #968	-> byte code offset #89
/*      */     //   Java source line #969	-> byte code offset #92
/*      */     //   Java source line #971	-> byte code offset #95
/*      */     //   Java source line #972	-> byte code offset #101
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	109	0	this	AbstractQueuedSynchronizer
/*      */     //   0	109	1	paramInt	int
/*      */     //   7	96	2	localNode1	Node
/*      */     //   9	89	3	i	int
/*      */     //   11	80	4	j	int
/*      */     //   17	57	5	localNode2	Node
/*      */     //   33	10	6	k	int
/*      */     //   95	12	7	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   10	63	95	finally
/*      */     //   73	97	95	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void doAcquireSharedInterruptibly(int paramInt)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 43	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:SHARED	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   4: invokespecial 35	java/util/concurrent/locks/AbstractQueuedSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   7: astore_2
/*      */     //   8: iconst_1
/*      */     //   9: istore_3
/*      */     //   10: aload_2
/*      */     //   11: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   14: astore 4
/*      */     //   16: aload 4
/*      */     //   18: aload_0
/*      */     //   19: getfield 10	java/util/concurrent/locks/AbstractQueuedSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   22: if_acmpne +40 -> 62
/*      */     //   25: aload_0
/*      */     //   26: iload_1
/*      */     //   27: invokevirtual 44	java/util/concurrent/locks/AbstractQueuedSynchronizer:tryAcquireShared	(I)I
/*      */     //   30: istore 5
/*      */     //   32: iload 5
/*      */     //   34: iflt +28 -> 62
/*      */     //   37: aload_0
/*      */     //   38: aload_2
/*      */     //   39: iload 5
/*      */     //   41: invokespecial 45	java/util/concurrent/locks/AbstractQueuedSynchronizer:setHeadAndPropagate	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;I)V
/*      */     //   44: aload 4
/*      */     //   46: aconst_null
/*      */     //   47: putfield 13	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   50: iconst_0
/*      */     //   51: istore_3
/*      */     //   52: iload_3
/*      */     //   53: ifeq +8 -> 61
/*      */     //   56: aload_0
/*      */     //   57: aload_2
/*      */     //   58: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   61: return
/*      */     //   62: aload 4
/*      */     //   64: aload_2
/*      */     //   65: invokestatic 32	java/util/concurrent/locks/AbstractQueuedSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Z
/*      */     //   68: ifeq +18 -> 86
/*      */     //   71: aload_0
/*      */     //   72: invokespecial 33	java/util/concurrent/locks/AbstractQueuedSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   75: ifeq +11 -> 86
/*      */     //   78: new 36	java/lang/InterruptedException
/*      */     //   81: dup
/*      */     //   82: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   85: athrow
/*      */     //   86: goto -76 -> 10
/*      */     //   89: astore 6
/*      */     //   91: iload_3
/*      */     //   92: ifeq +8 -> 100
/*      */     //   95: aload_0
/*      */     //   96: aload_2
/*      */     //   97: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   100: aload 6
/*      */     //   102: athrow
/*      */     // Line number table:
/*      */     //   Java source line #982	-> byte code offset #0
/*      */     //   Java source line #983	-> byte code offset #8
/*      */     //   Java source line #986	-> byte code offset #10
/*      */     //   Java source line #987	-> byte code offset #16
/*      */     //   Java source line #988	-> byte code offset #25
/*      */     //   Java source line #989	-> byte code offset #32
/*      */     //   Java source line #990	-> byte code offset #37
/*      */     //   Java source line #991	-> byte code offset #44
/*      */     //   Java source line #992	-> byte code offset #50
/*      */     //   Java source line #1001	-> byte code offset #52
/*      */     //   Java source line #1002	-> byte code offset #56
/*      */     //   Java source line #993	-> byte code offset #61
/*      */     //   Java source line #996	-> byte code offset #62
/*      */     //   Java source line #997	-> byte code offset #72
/*      */     //   Java source line #998	-> byte code offset #78
/*      */     //   Java source line #999	-> byte code offset #86
/*      */     //   Java source line #1001	-> byte code offset #89
/*      */     //   Java source line #1002	-> byte code offset #95
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	103	0	this	AbstractQueuedSynchronizer
/*      */     //   0	103	1	paramInt	int
/*      */     //   7	90	2	localNode1	Node
/*      */     //   9	83	3	i	int
/*      */     //   14	49	4	localNode2	Node
/*      */     //   30	10	5	j	int
/*      */     //   89	12	6	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   10	52	89	finally
/*      */     //   62	91	89	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private boolean doAcquireSharedNanos(int paramInt, long paramLong)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: lload_2
/*      */     //   1: lconst_0
/*      */     //   2: lcmp
/*      */     //   3: ifgt +5 -> 8
/*      */     //   6: iconst_0
/*      */     //   7: ireturn
/*      */     //   8: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   11: lload_2
/*      */     //   12: ladd
/*      */     //   13: lstore 4
/*      */     //   15: aload_0
/*      */     //   16: getstatic 43	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:SHARED	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   19: invokespecial 35	java/util/concurrent/locks/AbstractQueuedSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   22: astore 6
/*      */     //   24: iconst_1
/*      */     //   25: istore 7
/*      */     //   27: aload 6
/*      */     //   29: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   32: astore 8
/*      */     //   34: aload 8
/*      */     //   36: aload_0
/*      */     //   37: getfield 10	java/util/concurrent/locks/AbstractQueuedSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   40: if_acmpne +49 -> 89
/*      */     //   43: aload_0
/*      */     //   44: iload_1
/*      */     //   45: invokevirtual 44	java/util/concurrent/locks/AbstractQueuedSynchronizer:tryAcquireShared	(I)I
/*      */     //   48: istore 9
/*      */     //   50: iload 9
/*      */     //   52: iflt +37 -> 89
/*      */     //   55: aload_0
/*      */     //   56: aload 6
/*      */     //   58: iload 9
/*      */     //   60: invokespecial 45	java/util/concurrent/locks/AbstractQueuedSynchronizer:setHeadAndPropagate	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;I)V
/*      */     //   63: aload 8
/*      */     //   65: aconst_null
/*      */     //   66: putfield 13	java/util/concurrent/locks/AbstractQueuedSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;
/*      */     //   69: iconst_0
/*      */     //   70: istore 7
/*      */     //   72: iconst_1
/*      */     //   73: istore 10
/*      */     //   75: iload 7
/*      */     //   77: ifeq +9 -> 86
/*      */     //   80: aload_0
/*      */     //   81: aload 6
/*      */     //   83: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   86: iload 10
/*      */     //   88: ireturn
/*      */     //   89: lload 4
/*      */     //   91: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   94: lsub
/*      */     //   95: lstore_2
/*      */     //   96: lload_2
/*      */     //   97: lconst_0
/*      */     //   98: lcmp
/*      */     //   99: ifgt +20 -> 119
/*      */     //   102: iconst_0
/*      */     //   103: istore 9
/*      */     //   105: iload 7
/*      */     //   107: ifeq +9 -> 116
/*      */     //   110: aload_0
/*      */     //   111: aload 6
/*      */     //   113: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   116: iload 9
/*      */     //   118: ireturn
/*      */     //   119: aload 8
/*      */     //   121: aload 6
/*      */     //   123: invokestatic 32	java/util/concurrent/locks/AbstractQueuedSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)Z
/*      */     //   126: ifeq +16 -> 142
/*      */     //   129: lload_2
/*      */     //   130: ldc2_w 40
/*      */     //   133: lcmp
/*      */     //   134: ifle +8 -> 142
/*      */     //   137: aload_0
/*      */     //   138: lload_2
/*      */     //   139: invokestatic 42	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
/*      */     //   142: invokestatic 28	java/lang/Thread:interrupted	()Z
/*      */     //   145: ifeq +11 -> 156
/*      */     //   148: new 36	java/lang/InterruptedException
/*      */     //   151: dup
/*      */     //   152: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   155: athrow
/*      */     //   156: goto -129 -> 27
/*      */     //   159: astore 11
/*      */     //   161: iload 7
/*      */     //   163: ifeq +9 -> 172
/*      */     //   166: aload_0
/*      */     //   167: aload 6
/*      */     //   169: invokespecial 31	java/util/concurrent/locks/AbstractQueuedSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedSynchronizer$Node;)V
/*      */     //   172: aload 11
/*      */     //   174: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1015	-> byte code offset #0
/*      */     //   Java source line #1016	-> byte code offset #6
/*      */     //   Java source line #1017	-> byte code offset #8
/*      */     //   Java source line #1018	-> byte code offset #15
/*      */     //   Java source line #1019	-> byte code offset #24
/*      */     //   Java source line #1022	-> byte code offset #27
/*      */     //   Java source line #1023	-> byte code offset #34
/*      */     //   Java source line #1024	-> byte code offset #43
/*      */     //   Java source line #1025	-> byte code offset #50
/*      */     //   Java source line #1026	-> byte code offset #55
/*      */     //   Java source line #1027	-> byte code offset #63
/*      */     //   Java source line #1028	-> byte code offset #69
/*      */     //   Java source line #1029	-> byte code offset #72
/*      */     //   Java source line #1042	-> byte code offset #75
/*      */     //   Java source line #1043	-> byte code offset #80
/*      */     //   Java source line #1032	-> byte code offset #89
/*      */     //   Java source line #1033	-> byte code offset #96
/*      */     //   Java source line #1034	-> byte code offset #102
/*      */     //   Java source line #1042	-> byte code offset #105
/*      */     //   Java source line #1043	-> byte code offset #110
/*      */     //   Java source line #1035	-> byte code offset #119
/*      */     //   Java source line #1037	-> byte code offset #137
/*      */     //   Java source line #1038	-> byte code offset #142
/*      */     //   Java source line #1039	-> byte code offset #148
/*      */     //   Java source line #1040	-> byte code offset #156
/*      */     //   Java source line #1042	-> byte code offset #159
/*      */     //   Java source line #1043	-> byte code offset #166
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	175	0	this	AbstractQueuedSynchronizer
/*      */     //   0	175	1	paramInt	int
/*      */     //   0	175	2	paramLong	long
/*      */     //   13	77	4	l	long
/*      */     //   22	146	6	localNode1	Node
/*      */     //   25	137	7	i	int
/*      */     //   32	88	8	localNode2	Node
/*      */     //   48	69	9	j	int
/*      */     //   73	14	10	bool	boolean
/*      */     //   159	14	11	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   27	75	159	finally
/*      */     //   89	105	159	finally
/*      */     //   119	161	159	finally
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/locks/AbstractQueuedSynchronizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */