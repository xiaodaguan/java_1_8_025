/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.LockSupport;
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
/*      */ public class SynchronousQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3223113410248163686L;
/*  186 */   static final int NCPUS = Runtime.getRuntime().availableProcessors();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  195 */   static final int maxTimedSpins = NCPUS < 2 ? 0 : 32;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  202 */   static final int maxUntimedSpins = maxTimedSpins * 16;
/*      */   
/*      */   static final long spinForTimeoutThreshold = 1000L;
/*      */   
/*      */   private volatile transient Transferer<E> transferer;
/*      */   
/*      */   private ReentrantLock qlock;
/*      */   private WaitQueue waitingProducers;
/*      */   private WaitQueue waitingConsumers;
/*      */   
/*      */   static abstract class Transferer<E>
/*      */   {
/*      */     abstract E transfer(E paramE, boolean paramBoolean, long paramLong);
/*      */   }
/*      */   
/*      */   static final class TransferStack<E>
/*      */     extends SynchronousQueue.Transferer<E>
/*      */   {
/*      */     static final int REQUEST = 0;
/*      */     static final int DATA = 1;
/*      */     static final int FULFILLING = 2;
/*      */     volatile SNode head;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long headOffset;
/*      */     
/*      */     static boolean isFulfilling(int paramInt)
/*      */     {
/*  229 */       return (paramInt & 0x2) != 0;
/*      */     }
/*      */     
/*      */     static final class SNode {
/*      */       volatile SNode next;
/*      */       volatile SNode match;
/*      */       volatile Thread waiter;
/*      */       Object item;
/*      */       int mode;
/*      */       private static final Unsafe UNSAFE;
/*      */       private static final long matchOffset;
/*      */       private static final long nextOffset;
/*      */       
/*      */       SNode(Object paramObject) {
/*  243 */         this.item = paramObject;
/*      */       }
/*      */       
/*      */       boolean casNext(SNode paramSNode1, SNode paramSNode2)
/*      */       {
/*  248 */         return (paramSNode1 == this.next) && (UNSAFE.compareAndSwapObject(this, nextOffset, paramSNode1, paramSNode2));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       boolean tryMatch(SNode paramSNode)
/*      */       {
/*  260 */         if ((this.match == null) && 
/*  261 */           (UNSAFE.compareAndSwapObject(this, matchOffset, null, paramSNode))) {
/*  262 */           Thread localThread = this.waiter;
/*  263 */           if (localThread != null) {
/*  264 */             this.waiter = null;
/*  265 */             LockSupport.unpark(localThread);
/*      */           }
/*  267 */           return true;
/*      */         }
/*  269 */         return this.match == paramSNode;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       void tryCancel()
/*      */       {
/*  276 */         UNSAFE.compareAndSwapObject(this, matchOffset, null, this);
/*      */       }
/*      */       
/*      */       boolean isCancelled() {
/*  280 */         return this.match == this;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       static
/*      */       {
/*      */         try
/*      */         {
/*  290 */           UNSAFE = Unsafe.getUnsafe();
/*  291 */           Class localClass = SNode.class;
/*      */           
/*  293 */           matchOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("match"));
/*      */           
/*  295 */           nextOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("next"));
/*      */         } catch (Exception localException) {
/*  297 */           throw new Error(localException);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean casHead(SNode paramSNode1, SNode paramSNode2)
/*      */     {
/*  307 */       return (paramSNode1 == this.head) && (UNSAFE.compareAndSwapObject(this, headOffset, paramSNode1, paramSNode2));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static SNode snode(SNode paramSNode1, Object paramObject, SNode paramSNode2, int paramInt)
/*      */     {
/*  318 */       if (paramSNode1 == null) paramSNode1 = new SNode(paramObject);
/*  319 */       paramSNode1.mode = paramInt;
/*  320 */       paramSNode1.next = paramSNode2;
/*  321 */       return paramSNode1;
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
/*      */     E transfer(E paramE, boolean paramBoolean, long paramLong)
/*      */     {
/*  350 */       SNode localSNode1 = null;
/*  351 */       int i = paramE == null ? 0 : 1;
/*      */       for (;;)
/*      */       {
/*  354 */         SNode localSNode2 = this.head;
/*  355 */         SNode localSNode3; if ((localSNode2 == null) || (localSNode2.mode == i)) {
/*  356 */           if ((paramBoolean) && (paramLong <= 0L)) {
/*  357 */             if ((localSNode2 != null) && (localSNode2.isCancelled())) {
/*  358 */               casHead(localSNode2, localSNode2.next);
/*      */             } else
/*  360 */               return null;
/*  361 */           } else if (casHead(localSNode2, localSNode1 = snode(localSNode1, paramE, localSNode2, i))) {
/*  362 */             localSNode3 = awaitFulfill(localSNode1, paramBoolean, paramLong);
/*  363 */             if (localSNode3 == localSNode1) {
/*  364 */               clean(localSNode1);
/*  365 */               return null;
/*      */             }
/*  367 */             if (((localSNode2 = this.head) != null) && (localSNode2.next == localSNode1))
/*  368 */               casHead(localSNode2, localSNode1.next);
/*  369 */             return (E)(i == 0 ? localSNode3.item : localSNode1.item);
/*      */           } } else { SNode localSNode4;
/*  371 */           if (!isFulfilling(localSNode2.mode)) {
/*  372 */             if (localSNode2.isCancelled()) {
/*  373 */               casHead(localSNode2, localSNode2.next);
/*  374 */             } else if (casHead(localSNode2, localSNode1 = snode(localSNode1, paramE, localSNode2, 0x2 | i))) {
/*      */               for (;;) {
/*  376 */                 localSNode3 = localSNode1.next;
/*  377 */                 if (localSNode3 == null) {
/*  378 */                   casHead(localSNode1, null);
/*  379 */                   localSNode1 = null;
/*  380 */                   break;
/*      */                 }
/*  382 */                 localSNode4 = localSNode3.next;
/*  383 */                 if (localSNode3.tryMatch(localSNode1)) {
/*  384 */                   casHead(localSNode1, localSNode4);
/*  385 */                   return (E)(i == 0 ? localSNode3.item : localSNode1.item);
/*      */                 }
/*  387 */                 localSNode1.casNext(localSNode3, localSNode4);
/*      */               }
/*      */             }
/*      */           } else {
/*  391 */             localSNode3 = localSNode2.next;
/*  392 */             if (localSNode3 == null) {
/*  393 */               casHead(localSNode2, null);
/*      */             } else {
/*  395 */               localSNode4 = localSNode3.next;
/*  396 */               if (localSNode3.tryMatch(localSNode2)) {
/*  397 */                 casHead(localSNode2, localSNode4);
/*      */               } else {
/*  399 */                 localSNode2.casNext(localSNode3, localSNode4);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
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
/*      */     SNode awaitFulfill(SNode paramSNode, boolean paramBoolean, long paramLong)
/*      */     {
/*  436 */       long l = paramBoolean ? System.nanoTime() + paramLong : 0L;
/*  437 */       Thread localThread = Thread.currentThread();
/*  438 */       int i = shouldSpin(paramSNode) ? SynchronousQueue.maxUntimedSpins : paramBoolean ? SynchronousQueue.maxTimedSpins : 0;
/*      */       for (;;)
/*      */       {
/*  441 */         if (localThread.isInterrupted())
/*  442 */           paramSNode.tryCancel();
/*  443 */         SNode localSNode = paramSNode.match;
/*  444 */         if (localSNode != null)
/*  445 */           return localSNode;
/*  446 */         if (paramBoolean) {
/*  447 */           paramLong = l - System.nanoTime();
/*  448 */           if (paramLong <= 0L) {
/*  449 */             paramSNode.tryCancel();
/*  450 */             continue;
/*      */           }
/*      */         }
/*  453 */         if (i > 0) {
/*  454 */           i = shouldSpin(paramSNode) ? i - 1 : 0;
/*  455 */         } else if (paramSNode.waiter == null) {
/*  456 */           paramSNode.waiter = localThread;
/*  457 */         } else if (!paramBoolean) {
/*  458 */           LockSupport.park(this);
/*  459 */         } else if (paramLong > 1000L) {
/*  460 */           LockSupport.parkNanos(this, paramLong);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     boolean shouldSpin(SNode paramSNode)
/*      */     {
/*  469 */       SNode localSNode = this.head;
/*  470 */       return (localSNode == paramSNode) || (localSNode == null) || (isFulfilling(localSNode.mode));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void clean(SNode paramSNode)
/*      */     {
/*  477 */       paramSNode.item = null;
/*  478 */       paramSNode.waiter = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  491 */       SNode localSNode1 = paramSNode.next;
/*  492 */       if ((localSNode1 != null) && (localSNode1.isCancelled())) {
/*  493 */         localSNode1 = localSNode1.next;
/*      */       }
/*      */       
/*      */       Object localObject;
/*  497 */       while (((localObject = this.head) != null) && (localObject != localSNode1) && (((SNode)localObject).isCancelled())) {
/*  498 */         casHead((SNode)localObject, ((SNode)localObject).next);
/*      */       }
/*      */       
/*  501 */       while ((localObject != null) && (localObject != localSNode1)) {
/*  502 */         SNode localSNode2 = ((SNode)localObject).next;
/*  503 */         if ((localSNode2 != null) && (localSNode2.isCancelled())) {
/*  504 */           ((SNode)localObject).casNext(localSNode2, localSNode2.next);
/*      */         } else {
/*  506 */           localObject = localSNode2;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     static
/*      */     {
/*      */       try
/*      */       {
/*  515 */         UNSAFE = Unsafe.getUnsafe();
/*  516 */         Class localClass = TransferStack.class;
/*      */         
/*  518 */         headOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("head"));
/*      */       } catch (Exception localException) {
/*  520 */         throw new Error(localException);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class TransferQueue<E> extends SynchronousQueue.Transferer<E> {
/*      */     volatile transient QNode head;
/*      */     volatile transient QNode tail;
/*      */     volatile transient QNode cleanMe;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long headOffset;
/*      */     private static final long tailOffset;
/*      */     private static final long cleanMeOffset;
/*      */     
/*      */     static final class QNode {
/*      */       volatile QNode next;
/*      */       volatile Object item;
/*      */       volatile Thread waiter;
/*      */       final boolean isData;
/*      */       private static final Unsafe UNSAFE;
/*      */       private static final long itemOffset;
/*      */       private static final long nextOffset;
/*      */       
/*      */       QNode(Object paramObject, boolean paramBoolean) {
/*  544 */         this.item = paramObject;
/*  545 */         this.isData = paramBoolean;
/*      */       }
/*      */       
/*      */       boolean casNext(QNode paramQNode1, QNode paramQNode2)
/*      */       {
/*  550 */         return (this.next == paramQNode1) && (UNSAFE.compareAndSwapObject(this, nextOffset, paramQNode1, paramQNode2));
/*      */       }
/*      */       
/*      */       boolean casItem(Object paramObject1, Object paramObject2)
/*      */       {
/*  555 */         return (this.item == paramObject1) && (UNSAFE.compareAndSwapObject(this, itemOffset, paramObject1, paramObject2));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       void tryCancel(Object paramObject)
/*      */       {
/*  562 */         UNSAFE.compareAndSwapObject(this, itemOffset, paramObject, this);
/*      */       }
/*      */       
/*      */       boolean isCancelled() {
/*  566 */         return this.item == this;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       boolean isOffList()
/*      */       {
/*  575 */         return this.next == this;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       static
/*      */       {
/*      */         try
/*      */         {
/*  585 */           UNSAFE = Unsafe.getUnsafe();
/*  586 */           Class localClass = QNode.class;
/*      */           
/*  588 */           itemOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("item"));
/*      */           
/*  590 */           nextOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("next"));
/*      */         } catch (Exception localException) {
/*  592 */           throw new Error(localException);
/*      */         }
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
/*      */     TransferQueue()
/*      */     {
/*  609 */       QNode localQNode = new QNode(null, false);
/*  610 */       this.head = localQNode;
/*  611 */       this.tail = localQNode;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     void advanceHead(QNode paramQNode1, QNode paramQNode2)
/*      */     {
/*  619 */       if ((paramQNode1 == this.head) && 
/*  620 */         (UNSAFE.compareAndSwapObject(this, headOffset, paramQNode1, paramQNode2))) {
/*  621 */         paramQNode1.next = paramQNode1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     void advanceTail(QNode paramQNode1, QNode paramQNode2)
/*      */     {
/*  628 */       if (this.tail == paramQNode1) {
/*  629 */         UNSAFE.compareAndSwapObject(this, tailOffset, paramQNode1, paramQNode2);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     boolean casCleanMe(QNode paramQNode1, QNode paramQNode2)
/*      */     {
/*  637 */       return (this.cleanMe == paramQNode1) && (UNSAFE.compareAndSwapObject(this, cleanMeOffset, paramQNode1, paramQNode2));
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
/*      */     E transfer(E paramE, boolean paramBoolean, long paramLong)
/*      */     {
/*  670 */       QNode localQNode1 = null;
/*  671 */       boolean bool = paramE != null;
/*      */       QNode localQNode3;
/*      */       QNode localQNode4;
/*  674 */       Object localObject; for (;;) { QNode localQNode2 = this.tail;
/*  675 */         localQNode3 = this.head;
/*  676 */         if ((localQNode2 != null) && (localQNode3 != null))
/*      */         {
/*      */ 
/*  679 */           if ((localQNode3 == localQNode2) || (localQNode2.isData == bool)) {
/*  680 */             localQNode4 = localQNode2.next;
/*  681 */             if (localQNode2 == this.tail)
/*      */             {
/*  683 */               if (localQNode4 != null) {
/*  684 */                 advanceTail(localQNode2, localQNode4);
/*      */               }
/*      */               else {
/*  687 */                 if ((paramBoolean) && (paramLong <= 0L))
/*  688 */                   return null;
/*  689 */                 if (localQNode1 == null)
/*  690 */                   localQNode1 = new QNode(paramE, bool);
/*  691 */                 if (localQNode2.casNext(null, localQNode1))
/*      */                 {
/*      */ 
/*  694 */                   advanceTail(localQNode2, localQNode1);
/*  695 */                   localObject = awaitFulfill(localQNode1, paramE, paramBoolean, paramLong);
/*  696 */                   if (localObject == localQNode1) {
/*  697 */                     clean(localQNode2, localQNode1);
/*  698 */                     return null;
/*      */                   }
/*      */                   
/*  701 */                   if (!localQNode1.isOffList()) {
/*  702 */                     advanceHead(localQNode2, localQNode1);
/*  703 */                     if (localObject != null)
/*  704 */                       localQNode1.item = localQNode1;
/*  705 */                     localQNode1.waiter = null;
/*      */                   }
/*  707 */                   return (E)(localObject != null ? localObject : paramE);
/*      */                 }
/*      */               } }
/*  710 */           } else { localQNode4 = localQNode3.next;
/*  711 */             if ((localQNode2 == this.tail) && (localQNode4 != null) && (localQNode3 == this.head))
/*      */             {
/*      */ 
/*  714 */               localObject = localQNode4.item;
/*  715 */               if (bool != (localObject != null)) if ((localObject != localQNode4) && 
/*      */                 
/*  717 */                   (localQNode4.casItem(localObject, paramE))) break;
/*  718 */               advanceHead(localQNode3, localQNode4);
/*      */             }
/*      */           } }
/*      */       }
/*  722 */       advanceHead(localQNode3, localQNode4);
/*  723 */       LockSupport.unpark(localQNode4.waiter);
/*  724 */       return (E)(localObject != null ? localObject : paramE);
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
/*      */     Object awaitFulfill(QNode paramQNode, E paramE, boolean paramBoolean, long paramLong)
/*      */     {
/*  740 */       long l = paramBoolean ? System.nanoTime() + paramLong : 0L;
/*  741 */       Thread localThread = Thread.currentThread();
/*  742 */       int i = this.head.next == paramQNode ? SynchronousQueue.maxUntimedSpins : paramBoolean ? SynchronousQueue.maxTimedSpins : 0;
/*      */       for (;;)
/*      */       {
/*  745 */         if (localThread.isInterrupted())
/*  746 */           paramQNode.tryCancel(paramE);
/*  747 */         Object localObject = paramQNode.item;
/*  748 */         if (localObject != paramE)
/*  749 */           return localObject;
/*  750 */         if (paramBoolean) {
/*  751 */           paramLong = l - System.nanoTime();
/*  752 */           if (paramLong <= 0L) {
/*  753 */             paramQNode.tryCancel(paramE);
/*  754 */             continue;
/*      */           }
/*      */         }
/*  757 */         if (i > 0) {
/*  758 */           i--;
/*  759 */         } else if (paramQNode.waiter == null) {
/*  760 */           paramQNode.waiter = localThread;
/*  761 */         } else if (!paramBoolean) {
/*  762 */           LockSupport.park(this);
/*  763 */         } else if (paramLong > 1000L) {
/*  764 */           LockSupport.parkNanos(this, paramLong);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     void clean(QNode paramQNode1, QNode paramQNode2)
/*      */     {
/*  772 */       paramQNode2.waiter = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  781 */       while (paramQNode1.next == paramQNode2) {
/*  782 */         QNode localQNode1 = this.head;
/*  783 */         QNode localQNode2 = localQNode1.next;
/*  784 */         if ((localQNode2 != null) && (localQNode2.isCancelled())) {
/*  785 */           advanceHead(localQNode1, localQNode2);
/*      */         }
/*      */         else {
/*  788 */           QNode localQNode3 = this.tail;
/*  789 */           if (localQNode3 == localQNode1)
/*  790 */             return;
/*  791 */           QNode localQNode4 = localQNode3.next;
/*  792 */           if (localQNode3 == this.tail)
/*      */           {
/*  794 */             if (localQNode4 != null) {
/*  795 */               advanceTail(localQNode3, localQNode4);
/*      */             }
/*      */             else {
/*  798 */               if (paramQNode2 != localQNode3) {
/*  799 */                 localQNode5 = paramQNode2.next;
/*  800 */                 if ((localQNode5 == paramQNode2) || (paramQNode1.casNext(paramQNode2, localQNode5)))
/*  801 */                   return;
/*      */               }
/*  803 */               QNode localQNode5 = this.cleanMe;
/*  804 */               if (localQNode5 != null) {
/*  805 */                 QNode localQNode6 = localQNode5.next;
/*      */                 
/*  807 */                 if ((localQNode6 != null) && (localQNode6 != localQNode5))
/*      */                 {
/*  809 */                   if (localQNode6.isCancelled()) { QNode localQNode7; if ((localQNode6 == localQNode3) || ((localQNode7 = localQNode6.next) == null) || (localQNode7 == localQNode6)) {
/*      */                       break label207;
/*      */                     }
/*      */                     
/*  813 */                     if (!localQNode5.casNext(localQNode6, localQNode7)) break label207; } }
/*  814 */                 casCleanMe(localQNode5, null);
/*  815 */                 label207: if (localQNode5 == paramQNode1)
/*  816 */                   return;
/*  817 */               } else if (casCleanMe(null, paramQNode1)) {
/*  818 */                 return;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     static {
/*      */       try {
/*  828 */         UNSAFE = Unsafe.getUnsafe();
/*  829 */         Class localClass = TransferQueue.class;
/*      */         
/*  831 */         headOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("head"));
/*      */         
/*  833 */         tailOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("tail"));
/*      */         
/*  835 */         cleanMeOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("cleanMe"));
/*      */       } catch (Exception localException) {
/*  837 */         throw new Error(localException);
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
/*      */   public SynchronousQueue()
/*      */   {
/*  855 */     this(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SynchronousQueue(boolean paramBoolean)
/*      */   {
/*  865 */     this.transferer = (paramBoolean ? new TransferQueue() : new TransferStack());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(E paramE)
/*      */     throws InterruptedException
/*      */   {
/*  876 */     if (paramE == null) throw new NullPointerException();
/*  877 */     if (this.transferer.transfer(paramE, false, 0L) == null) {
/*  878 */       Thread.interrupted();
/*  879 */       throw new InterruptedException();
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  894 */     if (paramE == null) throw new NullPointerException();
/*  895 */     if (this.transferer.transfer(paramE, true, paramTimeUnit.toNanos(paramLong)) != null)
/*  896 */       return true;
/*  897 */     if (!Thread.interrupted())
/*  898 */       return false;
/*  899 */     throw new InterruptedException();
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
/*      */   public boolean offer(E paramE)
/*      */   {
/*  912 */     if (paramE == null) throw new NullPointerException();
/*  913 */     return this.transferer.transfer(paramE, true, 0L) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E take()
/*      */     throws InterruptedException
/*      */   {
/*  924 */     Object localObject = this.transferer.transfer(null, false, 0L);
/*  925 */     if (localObject != null)
/*  926 */       return (E)localObject;
/*  927 */     Thread.interrupted();
/*  928 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  941 */     Object localObject = this.transferer.transfer(null, true, paramTimeUnit.toNanos(paramLong));
/*  942 */     if ((localObject != null) || (!Thread.interrupted()))
/*  943 */       return (E)localObject;
/*  944 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E poll()
/*      */   {
/*  955 */     return (E)this.transferer.transfer(null, true, 0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  965 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  975 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int remainingCapacity()
/*      */   {
/*  985 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(Object paramObject)
/*      */   {
/* 1003 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean remove(Object paramObject)
/*      */   {
/* 1014 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsAll(Collection<?> paramCollection)
/*      */   {
/* 1025 */     return paramCollection.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean removeAll(Collection<?> paramCollection)
/*      */   {
/* 1036 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean retainAll(Collection<?> paramCollection)
/*      */   {
/* 1047 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E peek()
/*      */   {
/* 1058 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Iterator<E> iterator()
/*      */   {
/* 1068 */     return Collections.emptyIterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1079 */     return Spliterators.emptySpliterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object[] toArray()
/*      */   {
/* 1087 */     return new Object[0];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/* 1099 */     if (paramArrayOfT.length > 0)
/* 1100 */       paramArrayOfT[0] = null;
/* 1101 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection)
/*      */   {
/* 1111 */     if (paramCollection == null)
/* 1112 */       throw new NullPointerException();
/* 1113 */     if (paramCollection == this)
/* 1114 */       throw new IllegalArgumentException();
/* 1115 */     int i = 0;
/* 1116 */     Object localObject; while ((localObject = poll()) != null) {
/* 1117 */       paramCollection.add(localObject);
/* 1118 */       i++;
/*      */     }
/* 1120 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*      */   {
/* 1130 */     if (paramCollection == null)
/* 1131 */       throw new NullPointerException();
/* 1132 */     if (paramCollection == this)
/* 1133 */       throw new IllegalArgumentException();
/* 1134 */     int i = 0;
/* 1135 */     Object localObject; while ((i < paramInt) && ((localObject = poll()) != null)) {
/* 1136 */       paramCollection.add(localObject);
/* 1137 */       i++;
/*      */     }
/* 1139 */     return i;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1169 */     boolean bool = this.transferer instanceof TransferQueue;
/* 1170 */     if (bool) {
/* 1171 */       this.qlock = new ReentrantLock(true);
/* 1172 */       this.waitingProducers = new FifoWaitQueue();
/* 1173 */       this.waitingConsumers = new FifoWaitQueue();
/*      */     }
/*      */     else {
/* 1176 */       this.qlock = new ReentrantLock();
/* 1177 */       this.waitingProducers = new LifoWaitQueue();
/* 1178 */       this.waitingConsumers = new LifoWaitQueue();
/*      */     }
/* 1180 */     paramObjectOutputStream.defaultWriteObject();
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
/* 1192 */     paramObjectInputStream.defaultReadObject();
/* 1193 */     if ((this.waitingProducers instanceof FifoWaitQueue)) {
/* 1194 */       this.transferer = new TransferQueue();
/*      */     } else {
/* 1196 */       this.transferer = new TransferStack();
/*      */     }
/*      */   }
/*      */   
/*      */   static long objectFieldOffset(Unsafe paramUnsafe, String paramString, Class<?> paramClass)
/*      */   {
/*      */     try {
/* 1203 */       return paramUnsafe.objectFieldOffset(paramClass.getDeclaredField(paramString));
/*      */     }
/*      */     catch (NoSuchFieldException localNoSuchFieldException) {
/* 1206 */       NoSuchFieldError localNoSuchFieldError = new NoSuchFieldError(paramString);
/* 1207 */       localNoSuchFieldError.initCause(localNoSuchFieldException);
/* 1208 */       throw localNoSuchFieldError;
/*      */     }
/*      */   }
/*      */   
/*      */   static class WaitQueue
/*      */     implements Serializable
/*      */   {}
/*      */   
/*      */   static class LifoWaitQueue
/*      */     extends SynchronousQueue.WaitQueue
/*      */   {
/*      */     private static final long serialVersionUID = -3633113410248163686L;
/*      */   }
/*      */   
/*      */   static class FifoWaitQueue
/*      */     extends SynchronousQueue.WaitQueue
/*      */   {
/*      */     private static final long serialVersionUID = -3623113410248163686L;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/SynchronousQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */