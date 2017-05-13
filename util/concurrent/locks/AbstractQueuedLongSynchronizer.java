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
/*      */ public abstract class AbstractQueuedLongSynchronizer
/*      */   extends AbstractOwnableSynchronizer
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7373984972572414692L;
/*      */   private volatile transient Node head;
/*      */   private volatile transient Node tail;
/*      */   private volatile long state;
/*      */   static final long spinForTimeoutThreshold = 1000L;
/*      */   
/*      */   static final class Node
/*      */   {
/*  160 */     static final Node SHARED = new Node();
/*      */     
/*  162 */     static final Node EXCLUSIVE = null;
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
/*  262 */       return this.nextWaiter == SHARED;
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
/*  273 */       Node localNode = this.prev;
/*  274 */       if (localNode == null) {
/*  275 */         throw new NullPointerException();
/*      */       }
/*  277 */       return localNode;
/*      */     }
/*      */     
/*      */     Node() {}
/*      */     
/*      */     Node(Thread paramThread, Node paramNode)
/*      */     {
/*  284 */       this.nextWaiter = paramNode;
/*  285 */       this.thread = paramThread;
/*      */     }
/*      */     
/*      */     Node(Thread paramThread, int paramInt) {
/*  289 */       this.waitStatus = paramInt;
/*  290 */       this.thread = paramThread;
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
/*      */   protected final long getState()
/*      */   {
/*  319 */     return this.state;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setState(long paramLong)
/*      */   {
/*  328 */     this.state = paramLong;
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
/*      */   protected final boolean compareAndSetState(long paramLong1, long paramLong2)
/*      */   {
/*  344 */     return unsafe.compareAndSwapLong(this, stateOffset, paramLong1, paramLong2);
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
/*  363 */       Node localNode = this.tail;
/*  364 */       if (localNode == null) {
/*  365 */         if (compareAndSetHead(new Node()))
/*  366 */           this.tail = this.head;
/*      */       } else {
/*  368 */         paramNode.prev = localNode;
/*  369 */         if (compareAndSetTail(localNode, paramNode)) {
/*  370 */           localNode.next = paramNode;
/*  371 */           return localNode;
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
/*  384 */     Node localNode1 = new Node(Thread.currentThread(), paramNode);
/*      */     
/*  386 */     Node localNode2 = this.tail;
/*  387 */     if (localNode2 != null) {
/*  388 */       localNode1.prev = localNode2;
/*  389 */       if (compareAndSetTail(localNode2, localNode1)) {
/*  390 */         localNode2.next = localNode1;
/*  391 */         return localNode1;
/*      */       }
/*      */     }
/*  394 */     enq(localNode1);
/*  395 */     return localNode1;
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
/*  406 */     this.head = paramNode;
/*  407 */     paramNode.thread = null;
/*  408 */     paramNode.prev = null;
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
/*  422 */     int i = paramNode.waitStatus;
/*  423 */     if (i < 0) {
/*  424 */       compareAndSetWaitStatus(paramNode, i, 0);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  432 */     Object localObject = paramNode.next;
/*  433 */     if ((localObject == null) || (((Node)localObject).waitStatus > 0)) {
/*  434 */       localObject = null;
/*  435 */       for (Node localNode = this.tail; (localNode != null) && (localNode != paramNode); localNode = localNode.prev)
/*  436 */         if (localNode.waitStatus <= 0)
/*  437 */           localObject = localNode;
/*      */     }
/*  439 */     if (localObject != null) {
/*  440 */       LockSupport.unpark(((Node)localObject).thread);
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
/*  461 */       Node localNode = this.head;
/*  462 */       if ((localNode != null) && (localNode != this.tail)) {
/*  463 */         int i = localNode.waitStatus;
/*  464 */         if (i == -1) {
/*  465 */           if (!compareAndSetWaitStatus(localNode, -1, 0))
/*      */             continue;
/*  467 */           unparkSuccessor(localNode);
/*      */         } else {
/*  469 */           if ((i == 0) && 
/*  470 */             (!compareAndSetWaitStatus(localNode, 0, -3))) continue;
/*      */         }
/*      */       }
/*  473 */       if (localNode == this.head) {
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
/*      */   private void setHeadAndPropagate(Node paramNode, long paramLong)
/*      */   {
/*  487 */     Node localNode1 = this.head;
/*  488 */     setHead(paramNode);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  505 */     if ((paramLong > 0L) || (localNode1 == null) || (localNode1.waitStatus < 0) || ((localNode1 = this.head) == null) || (localNode1.waitStatus < 0))
/*      */     {
/*  507 */       Node localNode2 = paramNode.next;
/*  508 */       if ((localNode2 == null) || (localNode2.isShared())) {
/*  509 */         doReleaseShared();
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
/*  522 */     if (paramNode == null) {
/*  523 */       return;
/*      */     }
/*  525 */     paramNode.thread = null;
/*      */     
/*      */ 
/*  528 */     Node localNode1 = paramNode.prev;
/*  529 */     while (localNode1.waitStatus > 0) {
/*  530 */       paramNode.prev = (localNode1 = localNode1.prev);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  535 */     Node localNode2 = localNode1.next;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  540 */     paramNode.waitStatus = 1;
/*      */     
/*      */ 
/*  543 */     if ((paramNode == this.tail) && (compareAndSetTail(paramNode, localNode1))) {
/*  544 */       compareAndSetNext(localNode1, localNode2, null);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  549 */       if (localNode1 != this.head) { int i; if ((i = localNode1.waitStatus) != -1) { if (i <= 0)
/*      */           {
/*  551 */             if (!compareAndSetWaitStatus(localNode1, i, -1)) {} } } else if (localNode1.thread != null)
/*      */         {
/*  553 */           Node localNode3 = paramNode.next;
/*  554 */           if ((localNode3 != null) && (localNode3.waitStatus <= 0))
/*  555 */             compareAndSetNext(localNode1, localNode2, localNode3);
/*      */           break label148; } }
/*  557 */       unparkSuccessor(paramNode);
/*      */       
/*      */       label148:
/*  560 */       paramNode.next = paramNode;
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
/*  574 */     int i = paramNode1.waitStatus;
/*  575 */     if (i == -1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  580 */       return true; }
/*  581 */     if (i > 0)
/*      */     {
/*      */ 
/*      */       do
/*      */       {
/*      */ 
/*  587 */         paramNode2.prev = (paramNode1 = paramNode1.prev);
/*  588 */       } while (paramNode1.waitStatus > 0);
/*  589 */       paramNode1.next = paramNode2;
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  596 */       compareAndSetWaitStatus(paramNode1, i, -1);
/*      */     }
/*  598 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void selfInterrupt()
/*      */   {
/*  605 */     Thread.currentThread().interrupt();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final boolean parkAndCheckInterrupt()
/*      */   {
/*  614 */     LockSupport.park(this);
/*  615 */     return Thread.interrupted();
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
/*      */   protected boolean tryAcquire(long paramLong)
/*      */   {
/*  854 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryRelease(long paramLong)
/*      */   {
/*  880 */     throw new UnsupportedOperationException();
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
/*      */   protected long tryAcquireShared(long paramLong)
/*      */   {
/*  916 */     throw new UnsupportedOperationException();
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
/*      */   protected boolean tryReleaseShared(long paramLong)
/*      */   {
/*  941 */     throw new UnsupportedOperationException();
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
/*  960 */     throw new UnsupportedOperationException();
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
/*      */   public final void acquire(long paramLong)
/*      */   {
/*  976 */     if ((!tryAcquire(paramLong)) && 
/*  977 */       (acquireQueued(addWaiter(Node.EXCLUSIVE), paramLong))) {
/*  978 */       selfInterrupt();
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
/*      */   public final void acquireInterruptibly(long paramLong)
/*      */     throws InterruptedException
/*      */   {
/*  997 */     if (Thread.interrupted())
/*  998 */       throw new InterruptedException();
/*  999 */     if (!tryAcquire(paramLong)) {
/* 1000 */       doAcquireInterruptibly(paramLong);
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
/*      */   public final boolean tryAcquireNanos(long paramLong1, long paramLong2)
/*      */     throws InterruptedException
/*      */   {
/* 1022 */     if (Thread.interrupted()) {
/* 1023 */       throw new InterruptedException();
/*      */     }
/* 1025 */     return (tryAcquire(paramLong1)) || (doAcquireNanos(paramLong1, paramLong2));
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
/*      */   public final boolean release(long paramLong)
/*      */   {
/* 1039 */     if (tryRelease(paramLong)) {
/* 1040 */       Node localNode = this.head;
/* 1041 */       if ((localNode != null) && (localNode.waitStatus != 0))
/* 1042 */         unparkSuccessor(localNode);
/* 1043 */       return true;
/*      */     }
/* 1045 */     return false;
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
/*      */   public final void acquireShared(long paramLong)
/*      */   {
/* 1060 */     if (tryAcquireShared(paramLong) < 0L) {
/* 1061 */       doAcquireShared(paramLong);
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
/*      */   public final void acquireSharedInterruptibly(long paramLong)
/*      */     throws InterruptedException
/*      */   {
/* 1079 */     if (Thread.interrupted())
/* 1080 */       throw new InterruptedException();
/* 1081 */     if (tryAcquireShared(paramLong) < 0L) {
/* 1082 */       doAcquireSharedInterruptibly(paramLong);
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
/*      */   public final boolean tryAcquireSharedNanos(long paramLong1, long paramLong2)
/*      */     throws InterruptedException
/*      */   {
/* 1103 */     if (Thread.interrupted()) {
/* 1104 */       throw new InterruptedException();
/*      */     }
/* 1106 */     return (tryAcquireShared(paramLong1) >= 0L) || (doAcquireSharedNanos(paramLong1, paramLong2));
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
/*      */   public final boolean releaseShared(long paramLong)
/*      */   {
/* 1119 */     if (tryReleaseShared(paramLong)) {
/* 1120 */       doReleaseShared();
/* 1121 */       return true;
/*      */     }
/* 1123 */     return false;
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
/* 1140 */     return this.head != this.tail;
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
/* 1153 */     return this.head != null;
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
/* 1169 */     return this.head == this.tail ? null : fullGetFirstQueuedThread();
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
/* 1186 */     if ((((localNode1 = this.head) != null) && ((localNode2 = localNode1.next) != null) && (localNode2.prev == this.head) && ((localThread1 = localNode2.thread) != null)) || (((localNode1 = this.head) != null) && ((localNode2 = localNode1.next) != null) && (localNode2.prev == this.head) && ((localThread1 = localNode2.thread) != null)))
/*      */     {
/*      */ 
/*      */ 
/* 1190 */       return localThread1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1200 */     Node localNode3 = this.tail;
/* 1201 */     Object localObject = null;
/* 1202 */     while ((localNode3 != null) && (localNode3 != this.head)) {
/* 1203 */       Thread localThread2 = localNode3.thread;
/* 1204 */       if (localThread2 != null)
/* 1205 */         localObject = localThread2;
/* 1206 */       localNode3 = localNode3.prev;
/*      */     }
/* 1208 */     return (Thread)localObject;
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
/* 1222 */     if (paramThread == null)
/* 1223 */       throw new NullPointerException();
/* 1224 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev)
/* 1225 */       if (localNode.thread == paramThread)
/* 1226 */         return true;
/* 1227 */     return false;
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
/* 1241 */     if (((localNode1 = this.head) != null) && ((localNode2 = localNode1.next) != null)) {}
/*      */     
/* 1243 */     return (!localNode2.isShared()) && (localNode2.thread != null);
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
/* 1294 */     Node localNode1 = this.tail;
/* 1295 */     Node localNode2 = this.head;
/*      */     
/*      */     Node localNode3;
/* 1298 */     return (localNode2 != localNode1) && (((localNode3 = localNode2.next) == null) || (localNode3.thread != Thread.currentThread()));
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
/* 1315 */     int i = 0;
/* 1316 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1317 */       if (localNode.thread != null)
/* 1318 */         i++;
/*      */     }
/* 1320 */     return i;
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
/* 1335 */     ArrayList localArrayList = new ArrayList();
/* 1336 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1337 */       Thread localThread = localNode.thread;
/* 1338 */       if (localThread != null)
/* 1339 */         localArrayList.add(localThread);
/*      */     }
/* 1341 */     return localArrayList;
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
/* 1353 */     ArrayList localArrayList = new ArrayList();
/* 1354 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1355 */       if (!localNode.isShared()) {
/* 1356 */         Thread localThread = localNode.thread;
/* 1357 */         if (localThread != null)
/* 1358 */           localArrayList.add(localThread);
/*      */       }
/*      */     }
/* 1361 */     return localArrayList;
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
/* 1373 */     ArrayList localArrayList = new ArrayList();
/* 1374 */     for (Node localNode = this.tail; localNode != null; localNode = localNode.prev) {
/* 1375 */       if (localNode.isShared()) {
/* 1376 */         Thread localThread = localNode.thread;
/* 1377 */         if (localThread != null)
/* 1378 */           localArrayList.add(localThread);
/*      */       }
/*      */     }
/* 1381 */     return localArrayList;
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
/* 1394 */     long l = getState();
/* 1395 */     String str = hasQueuedThreads() ? "non" : "";
/* 1396 */     return super.toString() + "[State = " + l + ", " + str + "empty queue]";
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
/* 1410 */     if ((paramNode.waitStatus == -2) || (paramNode.prev == null))
/* 1411 */       return false;
/* 1412 */     if (paramNode.next != null) {
/* 1413 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1422 */     return findNodeFromTail(paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean findNodeFromTail(Node paramNode)
/*      */   {
/* 1431 */     Node localNode = this.tail;
/*      */     for (;;) {
/* 1433 */       if (localNode == paramNode)
/* 1434 */         return true;
/* 1435 */       if (localNode == null)
/* 1436 */         return false;
/* 1437 */       localNode = localNode.prev;
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
/* 1452 */     if (!compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1453 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1461 */     Node localNode = enq(paramNode);
/* 1462 */     int i = localNode.waitStatus;
/* 1463 */     if ((i > 0) || (!compareAndSetWaitStatus(localNode, i, -1)))
/* 1464 */       LockSupport.unpark(paramNode.thread);
/* 1465 */     return true;
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
/* 1476 */     if (compareAndSetWaitStatus(paramNode, -2, 0)) {
/* 1477 */       enq(paramNode);
/* 1478 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1486 */     while (!isOnSyncQueue(paramNode))
/* 1487 */       Thread.yield();
/* 1488 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final long fullyRelease(Node paramNode)
/*      */   {
/* 1498 */     int i = 1;
/*      */     try {
/* 1500 */       long l1 = getState();
/* 1501 */       if (release(l1)) {
/* 1502 */         i = 0;
/* 1503 */         return l1;
/*      */       }
/* 1505 */       throw new IllegalMonitorStateException();
/*      */     }
/*      */     finally {
/* 1508 */       if (i != 0) {
/* 1509 */         paramNode.waitStatus = 1;
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
/* 1524 */     return paramConditionObject.isOwnedBy(this);
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
/* 1544 */     if (!owns(paramConditionObject))
/* 1545 */       throw new IllegalArgumentException("Not owner");
/* 1546 */     return paramConditionObject.hasWaiters();
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
/* 1566 */     if (!owns(paramConditionObject))
/* 1567 */       throw new IllegalArgumentException("Not owner");
/* 1568 */     return paramConditionObject.getWaitQueueLength();
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
/* 1588 */     if (!owns(paramConditionObject))
/* 1589 */       throw new IllegalArgumentException("Not owner");
/* 1590 */     return paramConditionObject.getWaitingThreads();
/*      */   }
/*      */   
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
/*      */ 
/*      */     private transient AbstractQueuedLongSynchronizer.Node firstWaiter;
/*      */     
/*      */ 
/*      */ 
/*      */     private transient AbstractQueuedLongSynchronizer.Node lastWaiter;
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
/*      */     private AbstractQueuedLongSynchronizer.Node addConditionWaiter()
/*      */     {
/* 1629 */       AbstractQueuedLongSynchronizer.Node localNode1 = this.lastWaiter;
/*      */       
/* 1631 */       if ((localNode1 != null) && (localNode1.waitStatus != -2)) {
/* 1632 */         unlinkCancelledWaiters();
/* 1633 */         localNode1 = this.lastWaiter;
/*      */       }
/* 1635 */       AbstractQueuedLongSynchronizer.Node localNode2 = new AbstractQueuedLongSynchronizer.Node(Thread.currentThread(), -2);
/* 1636 */       if (localNode1 == null) {
/* 1637 */         this.firstWaiter = localNode2;
/*      */       } else
/* 1639 */         localNode1.nextWaiter = localNode2;
/* 1640 */       this.lastWaiter = localNode2;
/* 1641 */       return localNode2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void doSignal(AbstractQueuedLongSynchronizer.Node paramNode)
/*      */     {
/*      */       do
/*      */       {
/* 1652 */         if ((this.firstWaiter = paramNode.nextWaiter) == null)
/* 1653 */           this.lastWaiter = null;
/* 1654 */         paramNode.nextWaiter = null;
/* 1655 */       } while ((!AbstractQueuedLongSynchronizer.this.transferForSignal(paramNode)) && ((paramNode = this.firstWaiter) != null));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void doSignalAll(AbstractQueuedLongSynchronizer.Node paramNode)
/*      */     {
/* 1664 */       this.lastWaiter = (this.firstWaiter = null);
/*      */       do {
/* 1666 */         AbstractQueuedLongSynchronizer.Node localNode = paramNode.nextWaiter;
/* 1667 */         paramNode.nextWaiter = null;
/* 1668 */         AbstractQueuedLongSynchronizer.this.transferForSignal(paramNode);
/* 1669 */         paramNode = localNode;
/* 1670 */       } while (paramNode != null);
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
/* 1688 */       Object localObject1 = this.firstWaiter;
/* 1689 */       Object localObject2 = null;
/* 1690 */       while (localObject1 != null) {
/* 1691 */         AbstractQueuedLongSynchronizer.Node localNode = ((AbstractQueuedLongSynchronizer.Node)localObject1).nextWaiter;
/* 1692 */         if (((AbstractQueuedLongSynchronizer.Node)localObject1).waitStatus != -2) {
/* 1693 */           ((AbstractQueuedLongSynchronizer.Node)localObject1).nextWaiter = null;
/* 1694 */           if (localObject2 == null) {
/* 1695 */             this.firstWaiter = localNode;
/*      */           } else
/* 1697 */             ((AbstractQueuedLongSynchronizer.Node)localObject2).nextWaiter = localNode;
/* 1698 */           if (localNode == null) {
/* 1699 */             this.lastWaiter = ((AbstractQueuedLongSynchronizer.Node)localObject2);
/*      */           }
/*      */         } else {
/* 1702 */           localObject2 = localObject1; }
/* 1703 */         localObject1 = localNode;
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
/* 1718 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1719 */         throw new IllegalMonitorStateException();
/* 1720 */       AbstractQueuedLongSynchronizer.Node localNode = this.firstWaiter;
/* 1721 */       if (localNode != null) {
/* 1722 */         doSignal(localNode);
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
/* 1733 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1734 */         throw new IllegalMonitorStateException();
/* 1735 */       AbstractQueuedLongSynchronizer.Node localNode = this.firstWaiter;
/* 1736 */       if (localNode != null) {
/* 1737 */         doSignalAll(localNode);
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
/* 1752 */       AbstractQueuedLongSynchronizer.Node localNode = addConditionWaiter();
/* 1753 */       long l = AbstractQueuedLongSynchronizer.this.fullyRelease(localNode);
/* 1754 */       int i = 0;
/* 1755 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(localNode)) {
/* 1756 */         LockSupport.park(this);
/* 1757 */         if (Thread.interrupted())
/* 1758 */           i = 1;
/*      */       }
/* 1760 */       if ((AbstractQueuedLongSynchronizer.this.acquireQueued(localNode, l)) || (i != 0)) {
/* 1761 */         AbstractQueuedLongSynchronizer.selfInterrupt();
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
/*      */     private int checkInterruptWhileWaiting(AbstractQueuedLongSynchronizer.Node paramNode)
/*      */     {
/* 1783 */       return Thread.interrupted() ? 1 : AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(paramNode) ? -1 : 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void reportInterruptAfterWait(int paramInt)
/*      */       throws InterruptedException
/*      */     {
/* 1793 */       if (paramInt == -1)
/* 1794 */         throw new InterruptedException();
/* 1795 */       if (paramInt == 1) {
/* 1796 */         AbstractQueuedLongSynchronizer.selfInterrupt();
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
/* 1813 */       if (Thread.interrupted())
/* 1814 */         throw new InterruptedException();
/* 1815 */       AbstractQueuedLongSynchronizer.Node localNode = addConditionWaiter();
/* 1816 */       long l = AbstractQueuedLongSynchronizer.this.fullyRelease(localNode);
/* 1817 */       int i = 0;
/* 1818 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(localNode)) {
/* 1819 */         LockSupport.park(this);
/* 1820 */         if ((i = checkInterruptWhileWaiting(localNode)) != 0)
/*      */           break;
/*      */       }
/* 1823 */       if ((AbstractQueuedLongSynchronizer.this.acquireQueued(localNode, l)) && (i != -1))
/* 1824 */         i = 1;
/* 1825 */       if (localNode.nextWaiter != null)
/* 1826 */         unlinkCancelledWaiters();
/* 1827 */       if (i != 0) {
/* 1828 */         reportInterruptAfterWait(i);
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
/* 1846 */       if (Thread.interrupted())
/* 1847 */         throw new InterruptedException();
/* 1848 */       AbstractQueuedLongSynchronizer.Node localNode = addConditionWaiter();
/* 1849 */       long l1 = AbstractQueuedLongSynchronizer.this.fullyRelease(localNode);
/* 1850 */       long l2 = System.nanoTime() + paramLong;
/* 1851 */       int i = 0;
/* 1852 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(localNode)) {
/* 1853 */         if (paramLong <= 0L) {
/* 1854 */           AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(localNode);
/* 1855 */           break;
/*      */         }
/* 1857 */         if (paramLong >= 1000L)
/* 1858 */           LockSupport.parkNanos(this, paramLong);
/* 1859 */         if ((i = checkInterruptWhileWaiting(localNode)) != 0)
/*      */           break;
/* 1861 */         paramLong = l2 - System.nanoTime();
/*      */       }
/* 1863 */       if ((AbstractQueuedLongSynchronizer.this.acquireQueued(localNode, l1)) && (i != -1))
/* 1864 */         i = 1;
/* 1865 */       if (localNode.nextWaiter != null)
/* 1866 */         unlinkCancelledWaiters();
/* 1867 */       if (i != 0)
/* 1868 */         reportInterruptAfterWait(i);
/* 1869 */       return l2 - System.nanoTime();
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
/* 1888 */       long l1 = paramDate.getTime();
/* 1889 */       if (Thread.interrupted())
/* 1890 */         throw new InterruptedException();
/* 1891 */       AbstractQueuedLongSynchronizer.Node localNode = addConditionWaiter();
/* 1892 */       long l2 = AbstractQueuedLongSynchronizer.this.fullyRelease(localNode);
/* 1893 */       boolean bool = false;
/* 1894 */       int i = 0;
/* 1895 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(localNode))
/* 1896 */         if (System.currentTimeMillis() > l1) {
/* 1897 */           bool = AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(localNode);
/*      */         }
/*      */         else {
/* 1900 */           LockSupport.parkUntil(this, l1);
/* 1901 */           if ((i = checkInterruptWhileWaiting(localNode)) != 0)
/*      */             break;
/*      */         }
/* 1904 */       if ((AbstractQueuedLongSynchronizer.this.acquireQueued(localNode, l2)) && (i != -1))
/* 1905 */         i = 1;
/* 1906 */       if (localNode.nextWaiter != null)
/* 1907 */         unlinkCancelledWaiters();
/* 1908 */       if (i != 0)
/* 1909 */         reportInterruptAfterWait(i);
/* 1910 */       return !bool;
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
/* 1929 */       long l1 = paramTimeUnit.toNanos(paramLong);
/* 1930 */       if (Thread.interrupted())
/* 1931 */         throw new InterruptedException();
/* 1932 */       AbstractQueuedLongSynchronizer.Node localNode = addConditionWaiter();
/* 1933 */       long l2 = AbstractQueuedLongSynchronizer.this.fullyRelease(localNode);
/* 1934 */       long l3 = System.nanoTime() + l1;
/* 1935 */       boolean bool = false;
/* 1936 */       int i = 0;
/* 1937 */       while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(localNode)) {
/* 1938 */         if (l1 <= 0L) {
/* 1939 */           bool = AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(localNode);
/* 1940 */           break;
/*      */         }
/* 1942 */         if (l1 >= 1000L)
/* 1943 */           LockSupport.parkNanos(this, l1);
/* 1944 */         if ((i = checkInterruptWhileWaiting(localNode)) != 0)
/*      */           break;
/* 1946 */         l1 = l3 - System.nanoTime();
/*      */       }
/* 1948 */       if ((AbstractQueuedLongSynchronizer.this.acquireQueued(localNode, l2)) && (i != -1))
/* 1949 */         i = 1;
/* 1950 */       if (localNode.nextWaiter != null)
/* 1951 */         unlinkCancelledWaiters();
/* 1952 */       if (i != 0)
/* 1953 */         reportInterruptAfterWait(i);
/* 1954 */       return !bool;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean isOwnedBy(AbstractQueuedLongSynchronizer paramAbstractQueuedLongSynchronizer)
/*      */     {
/* 1966 */       return paramAbstractQueuedLongSynchronizer == AbstractQueuedLongSynchronizer.this;
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
/* 1978 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1979 */         throw new IllegalMonitorStateException();
/* 1980 */       for (AbstractQueuedLongSynchronizer.Node localNode = this.firstWaiter; localNode != null; localNode = localNode.nextWaiter) {
/* 1981 */         if (localNode.waitStatus == -2)
/* 1982 */           return true;
/*      */       }
/* 1984 */       return false;
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
/* 1997 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 1998 */         throw new IllegalMonitorStateException();
/* 1999 */       int i = 0;
/* 2000 */       for (AbstractQueuedLongSynchronizer.Node localNode = this.firstWaiter; localNode != null; localNode = localNode.nextWaiter) {
/* 2001 */         if (localNode.waitStatus == -2)
/* 2002 */           i++;
/*      */       }
/* 2004 */       return i;
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
/* 2017 */       if (!AbstractQueuedLongSynchronizer.this.isHeldExclusively())
/* 2018 */         throw new IllegalMonitorStateException();
/* 2019 */       ArrayList localArrayList = new ArrayList();
/* 2020 */       for (AbstractQueuedLongSynchronizer.Node localNode = this.firstWaiter; localNode != null; localNode = localNode.nextWaiter) {
/* 2021 */         if (localNode.waitStatus == -2) {
/* 2022 */           Thread localThread = localNode.thread;
/* 2023 */           if (localThread != null)
/* 2024 */             localArrayList.add(localThread);
/*      */         }
/*      */       }
/* 2027 */       return localArrayList;
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
/* 2040 */   private static final Unsafe unsafe = ;
/*      */   private static final long stateOffset;
/*      */   private static final long headOffset;
/*      */   private static final long tailOffset;
/*      */   private static final long waitStatusOffset;
/*      */   private static final long nextOffset;
/*      */   
/*      */   static
/*      */   {
/*      */     try {
/* 2050 */       stateOffset = unsafe.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("state"));
/*      */       
/* 2052 */       headOffset = unsafe.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("head"));
/*      */       
/* 2054 */       tailOffset = unsafe.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("tail"));
/*      */       
/* 2056 */       waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
/*      */       
/* 2058 */       nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
/*      */     } catch (Exception localException) {
/* 2060 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private final boolean compareAndSetHead(Node paramNode)
/*      */   {
/* 2067 */     return unsafe.compareAndSwapObject(this, headOffset, null, paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final boolean compareAndSetTail(Node paramNode1, Node paramNode2)
/*      */   {
/* 2074 */     return unsafe.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final boolean compareAndSetWaitStatus(Node paramNode, int paramInt1, int paramInt2)
/*      */   {
/* 2083 */     return unsafe.compareAndSwapInt(paramNode, waitStatusOffset, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final boolean compareAndSetNext(Node paramNode1, Node paramNode2, Node paramNode3)
/*      */   {
/* 2093 */     return unsafe.compareAndSwapObject(paramNode1, nextOffset, paramNode2, paramNode3);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   final boolean acquireQueued(Node paramNode, long paramLong)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: iconst_1
/*      */     //   1: istore 4
/*      */     //   3: iconst_0
/*      */     //   4: istore 5
/*      */     //   6: aload_1
/*      */     //   7: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   10: astore 6
/*      */     //   12: aload 6
/*      */     //   14: aload_0
/*      */     //   15: getfield 10	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   18: if_acmpne +42 -> 60
/*      */     //   21: aload_0
/*      */     //   22: lload_2
/*      */     //   23: invokevirtual 30	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:tryAcquire	(J)Z
/*      */     //   26: ifeq +34 -> 60
/*      */     //   29: aload_0
/*      */     //   30: aload_1
/*      */     //   31: invokespecial 22	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:setHead	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   34: aload 6
/*      */     //   36: aconst_null
/*      */     //   37: putfield 13	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   40: iconst_0
/*      */     //   41: istore 4
/*      */     //   43: iload 5
/*      */     //   45: istore 7
/*      */     //   47: iload 4
/*      */     //   49: ifeq +8 -> 57
/*      */     //   52: aload_0
/*      */     //   53: aload_1
/*      */     //   54: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   57: iload 7
/*      */     //   59: ireturn
/*      */     //   60: aload 6
/*      */     //   62: aload_1
/*      */     //   63: invokestatic 32	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Z
/*      */     //   66: ifeq +13 -> 79
/*      */     //   69: aload_0
/*      */     //   70: invokespecial 33	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   73: ifeq +6 -> 79
/*      */     //   76: iconst_1
/*      */     //   77: istore 5
/*      */     //   79: goto -73 -> 6
/*      */     //   82: astore 8
/*      */     //   84: iload 4
/*      */     //   86: ifeq +8 -> 94
/*      */     //   89: aload_0
/*      */     //   90: aload_1
/*      */     //   91: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   94: aload 8
/*      */     //   96: athrow
/*      */     // Line number table:
/*      */     //   Java source line #636	-> byte code offset #0
/*      */     //   Java source line #638	-> byte code offset #3
/*      */     //   Java source line #640	-> byte code offset #6
/*      */     //   Java source line #641	-> byte code offset #12
/*      */     //   Java source line #642	-> byte code offset #29
/*      */     //   Java source line #643	-> byte code offset #34
/*      */     //   Java source line #644	-> byte code offset #40
/*      */     //   Java source line #645	-> byte code offset #43
/*      */     //   Java source line #652	-> byte code offset #47
/*      */     //   Java source line #653	-> byte code offset #52
/*      */     //   Java source line #647	-> byte code offset #60
/*      */     //   Java source line #648	-> byte code offset #70
/*      */     //   Java source line #649	-> byte code offset #76
/*      */     //   Java source line #650	-> byte code offset #79
/*      */     //   Java source line #652	-> byte code offset #82
/*      */     //   Java source line #653	-> byte code offset #89
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	97	0	this	AbstractQueuedLongSynchronizer
/*      */     //   0	97	1	paramNode	Node
/*      */     //   0	97	2	paramLong	long
/*      */     //   1	84	4	i	int
/*      */     //   4	74	5	bool1	boolean
/*      */     //   10	51	6	localNode	Node
/*      */     //   45	13	7	bool2	boolean
/*      */     //   82	13	8	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   3	47	82	finally
/*      */     //   60	84	82	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void doAcquireInterruptibly(long paramLong)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 34	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:EXCLUSIVE	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   4: invokespecial 35	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   7: astore_3
/*      */     //   8: iconst_1
/*      */     //   9: istore 4
/*      */     //   11: aload_3
/*      */     //   12: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   15: astore 5
/*      */     //   17: aload 5
/*      */     //   19: aload_0
/*      */     //   20: getfield 10	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   23: if_acmpne +36 -> 59
/*      */     //   26: aload_0
/*      */     //   27: lload_1
/*      */     //   28: invokevirtual 30	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:tryAcquire	(J)Z
/*      */     //   31: ifeq +28 -> 59
/*      */     //   34: aload_0
/*      */     //   35: aload_3
/*      */     //   36: invokespecial 22	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:setHead	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   39: aload 5
/*      */     //   41: aconst_null
/*      */     //   42: putfield 13	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   45: iconst_0
/*      */     //   46: istore 4
/*      */     //   48: iload 4
/*      */     //   50: ifeq +8 -> 58
/*      */     //   53: aload_0
/*      */     //   54: aload_3
/*      */     //   55: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   58: return
/*      */     //   59: aload 5
/*      */     //   61: aload_3
/*      */     //   62: invokestatic 32	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Z
/*      */     //   65: ifeq +18 -> 83
/*      */     //   68: aload_0
/*      */     //   69: invokespecial 33	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   72: ifeq +11 -> 83
/*      */     //   75: new 36	java/lang/InterruptedException
/*      */     //   78: dup
/*      */     //   79: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   82: athrow
/*      */     //   83: goto -72 -> 11
/*      */     //   86: astore 6
/*      */     //   88: iload 4
/*      */     //   90: ifeq +8 -> 98
/*      */     //   93: aload_0
/*      */     //   94: aload_3
/*      */     //   95: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   98: aload 6
/*      */     //   100: athrow
/*      */     // Line number table:
/*      */     //   Java source line #663	-> byte code offset #0
/*      */     //   Java source line #664	-> byte code offset #8
/*      */     //   Java source line #667	-> byte code offset #11
/*      */     //   Java source line #668	-> byte code offset #17
/*      */     //   Java source line #669	-> byte code offset #34
/*      */     //   Java source line #670	-> byte code offset #39
/*      */     //   Java source line #671	-> byte code offset #45
/*      */     //   Java source line #679	-> byte code offset #48
/*      */     //   Java source line #680	-> byte code offset #53
/*      */     //   Java source line #672	-> byte code offset #58
/*      */     //   Java source line #674	-> byte code offset #59
/*      */     //   Java source line #675	-> byte code offset #69
/*      */     //   Java source line #676	-> byte code offset #75
/*      */     //   Java source line #677	-> byte code offset #83
/*      */     //   Java source line #679	-> byte code offset #86
/*      */     //   Java source line #680	-> byte code offset #93
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	101	0	this	AbstractQueuedLongSynchronizer
/*      */     //   0	101	1	paramLong	long
/*      */     //   7	88	3	localNode1	Node
/*      */     //   9	80	4	i	int
/*      */     //   15	45	5	localNode2	Node
/*      */     //   86	13	6	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   11	48	86	finally
/*      */     //   59	88	86	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private boolean doAcquireNanos(long paramLong1, long paramLong2)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: lload_3
/*      */     //   1: lconst_0
/*      */     //   2: lcmp
/*      */     //   3: ifgt +5 -> 8
/*      */     //   6: iconst_0
/*      */     //   7: ireturn
/*      */     //   8: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   11: lload_3
/*      */     //   12: ladd
/*      */     //   13: lstore 5
/*      */     //   15: aload_0
/*      */     //   16: getstatic 34	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:EXCLUSIVE	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   19: invokespecial 35	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   22: astore 7
/*      */     //   24: iconst_1
/*      */     //   25: istore 8
/*      */     //   27: aload 7
/*      */     //   29: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   32: astore 9
/*      */     //   34: aload 9
/*      */     //   36: aload_0
/*      */     //   37: getfield 10	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   40: if_acmpne +43 -> 83
/*      */     //   43: aload_0
/*      */     //   44: lload_1
/*      */     //   45: invokevirtual 30	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:tryAcquire	(J)Z
/*      */     //   48: ifeq +35 -> 83
/*      */     //   51: aload_0
/*      */     //   52: aload 7
/*      */     //   54: invokespecial 22	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:setHead	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   57: aload 9
/*      */     //   59: aconst_null
/*      */     //   60: putfield 13	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   63: iconst_0
/*      */     //   64: istore 8
/*      */     //   66: iconst_1
/*      */     //   67: istore 10
/*      */     //   69: iload 8
/*      */     //   71: ifeq +9 -> 80
/*      */     //   74: aload_0
/*      */     //   75: aload 7
/*      */     //   77: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   80: iload 10
/*      */     //   82: ireturn
/*      */     //   83: lload 5
/*      */     //   85: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   88: lsub
/*      */     //   89: lstore_3
/*      */     //   90: lload_3
/*      */     //   91: lconst_0
/*      */     //   92: lcmp
/*      */     //   93: ifgt +20 -> 113
/*      */     //   96: iconst_0
/*      */     //   97: istore 10
/*      */     //   99: iload 8
/*      */     //   101: ifeq +9 -> 110
/*      */     //   104: aload_0
/*      */     //   105: aload 7
/*      */     //   107: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   110: iload 10
/*      */     //   112: ireturn
/*      */     //   113: aload 9
/*      */     //   115: aload 7
/*      */     //   117: invokestatic 32	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Z
/*      */     //   120: ifeq +16 -> 136
/*      */     //   123: lload_3
/*      */     //   124: ldc2_w 40
/*      */     //   127: lcmp
/*      */     //   128: ifle +8 -> 136
/*      */     //   131: aload_0
/*      */     //   132: lload_3
/*      */     //   133: invokestatic 42	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
/*      */     //   136: invokestatic 28	java/lang/Thread:interrupted	()Z
/*      */     //   139: ifeq +11 -> 150
/*      */     //   142: new 36	java/lang/InterruptedException
/*      */     //   145: dup
/*      */     //   146: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   149: athrow
/*      */     //   150: goto -123 -> 27
/*      */     //   153: astore 11
/*      */     //   155: iload 8
/*      */     //   157: ifeq +9 -> 166
/*      */     //   160: aload_0
/*      */     //   161: aload 7
/*      */     //   163: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   166: aload 11
/*      */     //   168: athrow
/*      */     // Line number table:
/*      */     //   Java source line #693	-> byte code offset #0
/*      */     //   Java source line #694	-> byte code offset #6
/*      */     //   Java source line #695	-> byte code offset #8
/*      */     //   Java source line #696	-> byte code offset #15
/*      */     //   Java source line #697	-> byte code offset #24
/*      */     //   Java source line #700	-> byte code offset #27
/*      */     //   Java source line #701	-> byte code offset #34
/*      */     //   Java source line #702	-> byte code offset #51
/*      */     //   Java source line #703	-> byte code offset #57
/*      */     //   Java source line #704	-> byte code offset #63
/*      */     //   Java source line #705	-> byte code offset #66
/*      */     //   Java source line #717	-> byte code offset #69
/*      */     //   Java source line #718	-> byte code offset #74
/*      */     //   Java source line #707	-> byte code offset #83
/*      */     //   Java source line #708	-> byte code offset #90
/*      */     //   Java source line #709	-> byte code offset #96
/*      */     //   Java source line #717	-> byte code offset #99
/*      */     //   Java source line #718	-> byte code offset #104
/*      */     //   Java source line #710	-> byte code offset #113
/*      */     //   Java source line #712	-> byte code offset #131
/*      */     //   Java source line #713	-> byte code offset #136
/*      */     //   Java source line #714	-> byte code offset #142
/*      */     //   Java source line #715	-> byte code offset #150
/*      */     //   Java source line #717	-> byte code offset #153
/*      */     //   Java source line #718	-> byte code offset #160
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	169	0	this	AbstractQueuedLongSynchronizer
/*      */     //   0	169	1	paramLong1	long
/*      */     //   0	169	3	paramLong2	long
/*      */     //   13	71	5	l	long
/*      */     //   22	140	7	localNode1	Node
/*      */     //   25	131	8	i	int
/*      */     //   32	82	9	localNode2	Node
/*      */     //   67	44	10	bool	boolean
/*      */     //   153	14	11	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   27	69	153	finally
/*      */     //   83	99	153	finally
/*      */     //   113	155	153	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void doAcquireShared(long paramLong)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 43	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:SHARED	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   4: invokespecial 35	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   7: astore_3
/*      */     //   8: iconst_1
/*      */     //   9: istore 4
/*      */     //   11: iconst_0
/*      */     //   12: istore 5
/*      */     //   14: aload_3
/*      */     //   15: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   18: astore 6
/*      */     //   20: aload 6
/*      */     //   22: aload_0
/*      */     //   23: getfield 10	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   26: if_acmpne +52 -> 78
/*      */     //   29: aload_0
/*      */     //   30: lload_1
/*      */     //   31: invokevirtual 44	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:tryAcquireShared	(J)J
/*      */     //   34: lstore 7
/*      */     //   36: lload 7
/*      */     //   38: lconst_0
/*      */     //   39: lcmp
/*      */     //   40: iflt +38 -> 78
/*      */     //   43: aload_0
/*      */     //   44: aload_3
/*      */     //   45: lload 7
/*      */     //   47: invokespecial 45	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:setHeadAndPropagate	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;J)V
/*      */     //   50: aload 6
/*      */     //   52: aconst_null
/*      */     //   53: putfield 13	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   56: iload 5
/*      */     //   58: ifeq +6 -> 64
/*      */     //   61: invokestatic 46	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:selfInterrupt	()V
/*      */     //   64: iconst_0
/*      */     //   65: istore 4
/*      */     //   67: iload 4
/*      */     //   69: ifeq +8 -> 77
/*      */     //   72: aload_0
/*      */     //   73: aload_3
/*      */     //   74: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   77: return
/*      */     //   78: aload 6
/*      */     //   80: aload_3
/*      */     //   81: invokestatic 32	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Z
/*      */     //   84: ifeq +13 -> 97
/*      */     //   87: aload_0
/*      */     //   88: invokespecial 33	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   91: ifeq +6 -> 97
/*      */     //   94: iconst_1
/*      */     //   95: istore 5
/*      */     //   97: goto -83 -> 14
/*      */     //   100: astore 9
/*      */     //   102: iload 4
/*      */     //   104: ifeq +8 -> 112
/*      */     //   107: aload_0
/*      */     //   108: aload_3
/*      */     //   109: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   112: aload 9
/*      */     //   114: athrow
/*      */     // Line number table:
/*      */     //   Java source line #727	-> byte code offset #0
/*      */     //   Java source line #728	-> byte code offset #8
/*      */     //   Java source line #730	-> byte code offset #11
/*      */     //   Java source line #732	-> byte code offset #14
/*      */     //   Java source line #733	-> byte code offset #20
/*      */     //   Java source line #734	-> byte code offset #29
/*      */     //   Java source line #735	-> byte code offset #36
/*      */     //   Java source line #736	-> byte code offset #43
/*      */     //   Java source line #737	-> byte code offset #50
/*      */     //   Java source line #738	-> byte code offset #56
/*      */     //   Java source line #739	-> byte code offset #61
/*      */     //   Java source line #740	-> byte code offset #64
/*      */     //   Java source line #749	-> byte code offset #67
/*      */     //   Java source line #750	-> byte code offset #72
/*      */     //   Java source line #741	-> byte code offset #77
/*      */     //   Java source line #744	-> byte code offset #78
/*      */     //   Java source line #745	-> byte code offset #88
/*      */     //   Java source line #746	-> byte code offset #94
/*      */     //   Java source line #747	-> byte code offset #97
/*      */     //   Java source line #749	-> byte code offset #100
/*      */     //   Java source line #750	-> byte code offset #107
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	115	0	this	AbstractQueuedLongSynchronizer
/*      */     //   0	115	1	paramLong	long
/*      */     //   7	102	3	localNode1	Node
/*      */     //   9	94	4	i	int
/*      */     //   12	84	5	j	int
/*      */     //   18	61	6	localNode2	Node
/*      */     //   34	12	7	l	long
/*      */     //   100	13	9	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   11	67	100	finally
/*      */     //   78	102	100	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void doAcquireSharedInterruptibly(long paramLong)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 43	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:SHARED	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   4: invokespecial 35	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   7: astore_3
/*      */     //   8: iconst_1
/*      */     //   9: istore 4
/*      */     //   11: aload_3
/*      */     //   12: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   15: astore 5
/*      */     //   17: aload 5
/*      */     //   19: aload_0
/*      */     //   20: getfield 10	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   23: if_acmpne +44 -> 67
/*      */     //   26: aload_0
/*      */     //   27: lload_1
/*      */     //   28: invokevirtual 44	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:tryAcquireShared	(J)J
/*      */     //   31: lstore 6
/*      */     //   33: lload 6
/*      */     //   35: lconst_0
/*      */     //   36: lcmp
/*      */     //   37: iflt +30 -> 67
/*      */     //   40: aload_0
/*      */     //   41: aload_3
/*      */     //   42: lload 6
/*      */     //   44: invokespecial 45	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:setHeadAndPropagate	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;J)V
/*      */     //   47: aload 5
/*      */     //   49: aconst_null
/*      */     //   50: putfield 13	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   53: iconst_0
/*      */     //   54: istore 4
/*      */     //   56: iload 4
/*      */     //   58: ifeq +8 -> 66
/*      */     //   61: aload_0
/*      */     //   62: aload_3
/*      */     //   63: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   66: return
/*      */     //   67: aload 5
/*      */     //   69: aload_3
/*      */     //   70: invokestatic 32	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Z
/*      */     //   73: ifeq +18 -> 91
/*      */     //   76: aload_0
/*      */     //   77: invokespecial 33	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:parkAndCheckInterrupt	()Z
/*      */     //   80: ifeq +11 -> 91
/*      */     //   83: new 36	java/lang/InterruptedException
/*      */     //   86: dup
/*      */     //   87: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   90: athrow
/*      */     //   91: goto -80 -> 11
/*      */     //   94: astore 8
/*      */     //   96: iload 4
/*      */     //   98: ifeq +8 -> 106
/*      */     //   101: aload_0
/*      */     //   102: aload_3
/*      */     //   103: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   106: aload 8
/*      */     //   108: athrow
/*      */     // Line number table:
/*      */     //   Java source line #760	-> byte code offset #0
/*      */     //   Java source line #761	-> byte code offset #8
/*      */     //   Java source line #764	-> byte code offset #11
/*      */     //   Java source line #765	-> byte code offset #17
/*      */     //   Java source line #766	-> byte code offset #26
/*      */     //   Java source line #767	-> byte code offset #33
/*      */     //   Java source line #768	-> byte code offset #40
/*      */     //   Java source line #769	-> byte code offset #47
/*      */     //   Java source line #770	-> byte code offset #53
/*      */     //   Java source line #779	-> byte code offset #56
/*      */     //   Java source line #780	-> byte code offset #61
/*      */     //   Java source line #771	-> byte code offset #66
/*      */     //   Java source line #774	-> byte code offset #67
/*      */     //   Java source line #775	-> byte code offset #77
/*      */     //   Java source line #776	-> byte code offset #83
/*      */     //   Java source line #777	-> byte code offset #91
/*      */     //   Java source line #779	-> byte code offset #94
/*      */     //   Java source line #780	-> byte code offset #101
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	109	0	this	AbstractQueuedLongSynchronizer
/*      */     //   0	109	1	paramLong	long
/*      */     //   7	96	3	localNode1	Node
/*      */     //   9	88	4	i	int
/*      */     //   15	53	5	localNode2	Node
/*      */     //   31	12	6	l	long
/*      */     //   94	13	8	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   11	56	94	finally
/*      */     //   67	96	94	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private boolean doAcquireSharedNanos(long paramLong1, long paramLong2)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: lload_3
/*      */     //   1: lconst_0
/*      */     //   2: lcmp
/*      */     //   3: ifgt +5 -> 8
/*      */     //   6: iconst_0
/*      */     //   7: ireturn
/*      */     //   8: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   11: lload_3
/*      */     //   12: ladd
/*      */     //   13: lstore 5
/*      */     //   15: aload_0
/*      */     //   16: getstatic 43	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:SHARED	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   19: invokespecial 35	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:addWaiter	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   22: astore 7
/*      */     //   24: iconst_1
/*      */     //   25: istore 8
/*      */     //   27: aload 7
/*      */     //   29: invokevirtual 29	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:predecessor	()Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   32: astore 9
/*      */     //   34: aload 9
/*      */     //   36: aload_0
/*      */     //   37: getfield 10	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:head	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   40: if_acmpne +51 -> 91
/*      */     //   43: aload_0
/*      */     //   44: lload_1
/*      */     //   45: invokevirtual 44	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:tryAcquireShared	(J)J
/*      */     //   48: lstore 10
/*      */     //   50: lload 10
/*      */     //   52: lconst_0
/*      */     //   53: lcmp
/*      */     //   54: iflt +37 -> 91
/*      */     //   57: aload_0
/*      */     //   58: aload 7
/*      */     //   60: lload 10
/*      */     //   62: invokespecial 45	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:setHeadAndPropagate	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;J)V
/*      */     //   65: aload 9
/*      */     //   67: aconst_null
/*      */     //   68: putfield 13	java/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node:next	Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;
/*      */     //   71: iconst_0
/*      */     //   72: istore 8
/*      */     //   74: iconst_1
/*      */     //   75: istore 12
/*      */     //   77: iload 8
/*      */     //   79: ifeq +9 -> 88
/*      */     //   82: aload_0
/*      */     //   83: aload 7
/*      */     //   85: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   88: iload 12
/*      */     //   90: ireturn
/*      */     //   91: lload 5
/*      */     //   93: invokestatic 38	java/lang/System:nanoTime	()J
/*      */     //   96: lsub
/*      */     //   97: lstore_3
/*      */     //   98: lload_3
/*      */     //   99: lconst_0
/*      */     //   100: lcmp
/*      */     //   101: ifgt +20 -> 121
/*      */     //   104: iconst_0
/*      */     //   105: istore 10
/*      */     //   107: iload 8
/*      */     //   109: ifeq +9 -> 118
/*      */     //   112: aload_0
/*      */     //   113: aload 7
/*      */     //   115: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   118: iload 10
/*      */     //   120: ireturn
/*      */     //   121: aload 9
/*      */     //   123: aload 7
/*      */     //   125: invokestatic 32	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:shouldParkAfterFailedAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)Z
/*      */     //   128: ifeq +16 -> 144
/*      */     //   131: lload_3
/*      */     //   132: ldc2_w 40
/*      */     //   135: lcmp
/*      */     //   136: ifle +8 -> 144
/*      */     //   139: aload_0
/*      */     //   140: lload_3
/*      */     //   141: invokestatic 42	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
/*      */     //   144: invokestatic 28	java/lang/Thread:interrupted	()Z
/*      */     //   147: ifeq +11 -> 158
/*      */     //   150: new 36	java/lang/InterruptedException
/*      */     //   153: dup
/*      */     //   154: invokespecial 37	java/lang/InterruptedException:<init>	()V
/*      */     //   157: athrow
/*      */     //   158: goto -131 -> 27
/*      */     //   161: astore 13
/*      */     //   163: iload 8
/*      */     //   165: ifeq +9 -> 174
/*      */     //   168: aload_0
/*      */     //   169: aload 7
/*      */     //   171: invokespecial 31	java/util/concurrent/locks/AbstractQueuedLongSynchronizer:cancelAcquire	(Ljava/util/concurrent/locks/AbstractQueuedLongSynchronizer$Node;)V
/*      */     //   174: aload 13
/*      */     //   176: athrow
/*      */     // Line number table:
/*      */     //   Java source line #793	-> byte code offset #0
/*      */     //   Java source line #794	-> byte code offset #6
/*      */     //   Java source line #795	-> byte code offset #8
/*      */     //   Java source line #796	-> byte code offset #15
/*      */     //   Java source line #797	-> byte code offset #24
/*      */     //   Java source line #800	-> byte code offset #27
/*      */     //   Java source line #801	-> byte code offset #34
/*      */     //   Java source line #802	-> byte code offset #43
/*      */     //   Java source line #803	-> byte code offset #50
/*      */     //   Java source line #804	-> byte code offset #57
/*      */     //   Java source line #805	-> byte code offset #65
/*      */     //   Java source line #806	-> byte code offset #71
/*      */     //   Java source line #807	-> byte code offset #74
/*      */     //   Java source line #820	-> byte code offset #77
/*      */     //   Java source line #821	-> byte code offset #82
/*      */     //   Java source line #810	-> byte code offset #91
/*      */     //   Java source line #811	-> byte code offset #98
/*      */     //   Java source line #812	-> byte code offset #104
/*      */     //   Java source line #820	-> byte code offset #107
/*      */     //   Java source line #821	-> byte code offset #112
/*      */     //   Java source line #813	-> byte code offset #121
/*      */     //   Java source line #815	-> byte code offset #139
/*      */     //   Java source line #816	-> byte code offset #144
/*      */     //   Java source line #817	-> byte code offset #150
/*      */     //   Java source line #818	-> byte code offset #158
/*      */     //   Java source line #820	-> byte code offset #161
/*      */     //   Java source line #821	-> byte code offset #168
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	177	0	this	AbstractQueuedLongSynchronizer
/*      */     //   0	177	1	paramLong1	long
/*      */     //   0	177	3	paramLong2	long
/*      */     //   13	79	5	l1	long
/*      */     //   22	148	7	localNode1	Node
/*      */     //   25	139	8	i	int
/*      */     //   32	90	9	localNode2	Node
/*      */     //   48	13	10	l2	long
/*      */     //   105	14	10	bool1	boolean
/*      */     //   75	14	12	bool2	boolean
/*      */     //   161	14	13	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   27	77	161	finally
/*      */     //   91	107	161	finally
/*      */     //   121	163	161	finally
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/locks/AbstractQueuedLongSynchronizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */