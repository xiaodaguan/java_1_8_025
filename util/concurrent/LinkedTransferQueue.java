/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.function.Consumer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinkedTransferQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements TransferQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3223113410248163686L;
/*  415 */   private static final boolean MP = Runtime.getRuntime().availableProcessors() > 1;
/*      */   
/*      */   private static final int FRONT_SPINS = 128;
/*      */   
/*      */   private static final int CHAINED_SPINS = 64;
/*      */   
/*      */   static final int SWEEP_THRESHOLD = 32;
/*      */   
/*      */   volatile transient Node head;
/*      */   
/*      */   private volatile transient Node tail;
/*      */   
/*      */   private volatile transient int sweepVotes;
/*      */   
/*      */   private static final int NOW = 0;
/*      */   
/*      */   private static final int ASYNC = 1;
/*      */   
/*      */   private static final int SYNC = 2;
/*      */   
/*      */   private static final int TIMED = 3;
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   private static final long headOffset;
/*      */   
/*      */   private static final long tailOffset;
/*      */   
/*      */   private static final long sweepVotesOffset;
/*      */   
/*      */   static final class Node
/*      */   {
/*      */     final boolean isData;
/*      */     volatile Object item;
/*      */     volatile Node next;
/*      */     volatile Thread waiter;
/*      */     private static final long serialVersionUID = -3375979862319811754L;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long itemOffset;
/*      */     private static final long nextOffset;
/*      */     private static final long waiterOffset;
/*      */     
/*      */     final boolean casNext(Node paramNode1, Node paramNode2)
/*      */     {
/*  459 */       return UNSAFE.compareAndSwapObject(this, nextOffset, paramNode1, paramNode2);
/*      */     }
/*      */     
/*      */     final boolean casItem(Object paramObject1, Object paramObject2)
/*      */     {
/*  464 */       return UNSAFE.compareAndSwapObject(this, itemOffset, paramObject1, paramObject2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Node(Object paramObject, boolean paramBoolean)
/*      */     {
/*  472 */       UNSAFE.putObject(this, itemOffset, paramObject);
/*  473 */       this.isData = paramBoolean;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final void forgetNext()
/*      */     {
/*  481 */       UNSAFE.putObject(this, nextOffset, this);
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
/*      */     final void forgetContents()
/*      */     {
/*  494 */       UNSAFE.putObject(this, itemOffset, this);
/*  495 */       UNSAFE.putObject(this, waiterOffset, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean isMatched()
/*      */     {
/*  503 */       Object localObject = this.item;
/*  504 */       if (localObject != this) {} return (localObject == null) == this.isData;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final boolean isUnmatchedRequest()
/*      */     {
/*  511 */       return (!this.isData) && (this.item == null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean cannotPrecede(boolean paramBoolean)
/*      */     {
/*  520 */       boolean bool = this.isData;
/*      */       Object localObject;
/*  522 */       if ((bool != paramBoolean) && ((localObject = this.item) != this)) {} return (localObject != null) == bool;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean tryMatchData()
/*      */     {
/*  530 */       Object localObject = this.item;
/*  531 */       if ((localObject != null) && (localObject != this) && (casItem(localObject, null))) {
/*  532 */         LockSupport.unpark(this.waiter);
/*  533 */         return true;
/*      */       }
/*  535 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static
/*      */     {
/*      */       try
/*      */       {
/*  547 */         UNSAFE = Unsafe.getUnsafe();
/*  548 */         Class localClass = Node.class;
/*      */         
/*  550 */         itemOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("item"));
/*      */         
/*  552 */         nextOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("next"));
/*      */         
/*  554 */         waiterOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("waiter"));
/*      */       } catch (Exception localException) {
/*  556 */         throw new Error(localException);
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
/*      */   private boolean casTail(Node paramNode1, Node paramNode2)
/*      */   {
/*  572 */     return UNSAFE.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   private boolean casHead(Node paramNode1, Node paramNode2) {
/*  576 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   private boolean casSweepVotes(int paramInt1, int paramInt2) {
/*  580 */     return UNSAFE.compareAndSwapInt(this, sweepVotesOffset, paramInt1, paramInt2);
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
/*      */   static <E> E cast(Object paramObject)
/*      */   {
/*  594 */     return (E)paramObject;
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
/*      */   private E xfer(E paramE, boolean paramBoolean, int paramInt, long paramLong)
/*      */   {
/*  608 */     if ((paramBoolean) && (paramE == null))
/*  609 */       throw new NullPointerException();
/*  610 */     Node localNode1 = null;
/*      */     
/*      */     Node localNode2;
/*      */     do
/*      */     {
/*  615 */       localNode2 = this.head; for (Node localNode3 = localNode2; localNode3 != null;) {
/*  616 */         boolean bool = localNode3.isData;
/*  617 */         Object localObject = localNode3.item;
/*  618 */         if (localObject != localNode3) if ((localObject != null) == bool) {
/*  619 */             if (bool == paramBoolean)
/*      */               break;
/*  621 */             if (localNode3.casItem(localObject, paramE)) {
/*  622 */               for (localNode4 = localNode3; localNode4 != localNode2;) {
/*  623 */                 Node localNode5 = localNode4.next;
/*  624 */                 if (this.head == localNode2) if (casHead(localNode2, localNode5 == null ? localNode4 : localNode5)) {
/*  625 */                     localNode2.forgetNext();
/*  626 */                     break;
/*      */                   }
/*  628 */                 if (((localNode2 = this.head) == null) || ((localNode4 = localNode2.next) == null) || 
/*  629 */                   (!localNode4.isMatched()))
/*      */                   break;
/*      */               }
/*  632 */               LockSupport.unpark(localNode3.waiter);
/*  633 */               return (E)cast(localObject);
/*      */             }
/*      */           }
/*  636 */         Node localNode4 = localNode3.next;
/*  637 */         localNode3 = localNode3 != localNode4 ? localNode4 : (localNode2 = this.head);
/*      */       }
/*      */       
/*  640 */       if (paramInt == 0) break;
/*  641 */       if (localNode1 == null)
/*  642 */         localNode1 = new Node(paramE, paramBoolean);
/*  643 */       localNode2 = tryAppend(localNode1, paramBoolean);
/*  644 */     } while (localNode2 == null);
/*      */     
/*  646 */     if (paramInt != 1) {
/*  647 */       return (E)awaitMatch(localNode1, localNode2, paramE, paramInt == 3, paramLong);
/*      */     }
/*  649 */     return paramE;
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
/*      */   private Node tryAppend(Node paramNode, boolean paramBoolean)
/*      */   {
/*  663 */     Object localObject1 = this.tail;Object localObject2 = localObject1;
/*      */     for (;;) {
/*  665 */       if ((localObject2 == null) && ((localObject2 = this.head) == null)) {
/*  666 */         if (casHead(null, paramNode))
/*  667 */           return paramNode;
/*      */       } else {
/*  669 */         if (((Node)localObject2).cannotPrecede(paramBoolean))
/*  670 */           return null;
/*  671 */         Node localNode1; if ((localNode1 = ((Node)localObject2).next) != null) { Node localNode2;
/*  672 */           localObject2 = localObject2 != localNode1 ? localNode1 : (localObject2 != localObject1) && (localObject1 != (localNode2 = this.tail)) ? (localObject1 = localNode2) : null;
/*      */         }
/*  674 */         else if (!((Node)localObject2).casNext(null, paramNode)) {
/*  675 */           localObject2 = ((Node)localObject2).next;
/*      */         } else {
/*  677 */           while ((localObject2 != localObject1) && 
/*  678 */             ((this.tail != localObject1) || (!casTail((Node)localObject1, paramNode))) && ((localObject1 = this.tail) != null) && ((paramNode = ((Node)localObject1).next) != null) && ((paramNode = paramNode.next) != null) && (paramNode != localObject1)) {}
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  683 */           return (Node)localObject2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private E awaitMatch(Node paramNode1, Node paramNode2, E paramE, boolean paramBoolean, long paramLong)
/*      */   {
/*  701 */     long l = paramBoolean ? System.nanoTime() + paramLong : 0L;
/*  702 */     Thread localThread = Thread.currentThread();
/*  703 */     int i = -1;
/*  704 */     ThreadLocalRandom localThreadLocalRandom = null;
/*      */     for (;;)
/*      */     {
/*  707 */       Object localObject = paramNode1.item;
/*  708 */       if (localObject != paramE)
/*      */       {
/*  710 */         paramNode1.forgetContents();
/*  711 */         return (E)cast(localObject);
/*      */       }
/*  713 */       if (((localThread.isInterrupted()) || ((paramBoolean) && (paramLong <= 0L))) && 
/*  714 */         (paramNode1.casItem(paramE, paramNode1))) {
/*  715 */         unsplice(paramNode2, paramNode1);
/*  716 */         return paramE;
/*      */       }
/*      */       
/*  719 */       if (i < 0) {
/*  720 */         if ((i = spinsFor(paramNode2, paramNode1.isData)) > 0) {
/*  721 */           localThreadLocalRandom = ThreadLocalRandom.current();
/*      */         }
/*  723 */       } else if (i > 0) {
/*  724 */         i--;
/*  725 */         if (localThreadLocalRandom.nextInt(64) == 0) {
/*  726 */           Thread.yield();
/*      */         }
/*  728 */       } else if (paramNode1.waiter == null) {
/*  729 */         paramNode1.waiter = localThread;
/*      */       }
/*  731 */       else if (paramBoolean) {
/*  732 */         paramLong = l - System.nanoTime();
/*  733 */         if (paramLong > 0L) {
/*  734 */           LockSupport.parkNanos(this, paramLong);
/*      */         }
/*      */       } else {
/*  737 */         LockSupport.park(this);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int spinsFor(Node paramNode, boolean paramBoolean)
/*      */   {
/*  747 */     if ((MP) && (paramNode != null)) {
/*  748 */       if (paramNode.isData != paramBoolean)
/*  749 */         return 192;
/*  750 */       if (paramNode.isMatched())
/*  751 */         return 128;
/*  752 */       if (paramNode.waiter == null)
/*  753 */         return 64;
/*      */     }
/*  755 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final Node succ(Node paramNode)
/*      */   {
/*  766 */     Node localNode = paramNode.next;
/*  767 */     return paramNode == localNode ? this.head : localNode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Node firstOfMode(boolean paramBoolean)
/*      */   {
/*  775 */     for (Node localNode = this.head; localNode != null; localNode = succ(localNode)) {
/*  776 */       if (!localNode.isMatched())
/*  777 */         return localNode.isData == paramBoolean ? localNode : null;
/*      */     }
/*  779 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final Node firstDataNode()
/*      */   {
/*  786 */     for (Node localNode = this.head; localNode != null;) {
/*  787 */       Object localObject = localNode.item;
/*  788 */       if (localNode.isData) {
/*  789 */         if ((localObject != null) && (localObject != localNode))
/*  790 */           return localNode;
/*      */       } else
/*  792 */         if (localObject == null)
/*      */           break;
/*  794 */       if (localNode == (localNode = localNode.next))
/*  795 */         localNode = this.head;
/*      */     }
/*  797 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private E firstDataItem()
/*      */   {
/*  805 */     for (Node localNode = this.head; localNode != null; localNode = succ(localNode)) {
/*  806 */       Object localObject = localNode.item;
/*  807 */       if (localNode.isData) {
/*  808 */         if ((localObject != null) && (localObject != localNode)) {
/*  809 */           return (E)cast(localObject);
/*      */         }
/*  811 */       } else if (localObject == null)
/*  812 */         return null;
/*      */     }
/*  814 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int countOfMode(boolean paramBoolean)
/*      */   {
/*  822 */     int i = 0;
/*  823 */     for (Object localObject = this.head; localObject != null;) {
/*  824 */       if (!((Node)localObject).isMatched()) {
/*  825 */         if (((Node)localObject).isData != paramBoolean)
/*  826 */           return 0;
/*  827 */         i++; if (i == Integer.MAX_VALUE)
/*      */           break;
/*      */       }
/*  830 */       Node localNode = ((Node)localObject).next;
/*  831 */       if (localNode != localObject) {
/*  832 */         localObject = localNode;
/*      */       } else {
/*  834 */         i = 0;
/*  835 */         localObject = this.head;
/*      */       }
/*      */     }
/*  838 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private LinkedTransferQueue.Node nextNode;
/*      */     
/*      */ 
/*      */     private E nextItem;
/*      */     
/*      */     private LinkedTransferQueue.Node lastRet;
/*      */     
/*      */     private LinkedTransferQueue.Node lastPred;
/*      */     
/*      */ 
/*      */     private void advance(LinkedTransferQueue.Node paramNode)
/*      */     {
/*      */       LinkedTransferQueue.Node localNode1;
/*      */       
/*      */       LinkedTransferQueue.Node localNode3;
/*      */       
/*  862 */       if (((localNode1 = this.lastRet) != null) && (!localNode1.isMatched())) {
/*  863 */         this.lastPred = localNode1; } else { LinkedTransferQueue.Node localNode2;
/*  864 */         if (((localNode2 = this.lastPred) == null) || (localNode2.isMatched())) {
/*  865 */           this.lastPred = null;
/*      */         }
/*      */         else {
/*  868 */           while (((localObject1 = localNode2.next) != null) && (localObject1 != localNode2) && 
/*  869 */             (((LinkedTransferQueue.Node)localObject1).isMatched()) && ((localNode3 = ((LinkedTransferQueue.Node)localObject1).next) != null) && (localNode3 != localObject1))
/*      */           {
/*  871 */             localNode2.casNext((LinkedTransferQueue.Node)localObject1, localNode3); }
/*      */         }
/*      */       }
/*  874 */       this.lastRet = paramNode;
/*      */       
/*  876 */       Object localObject1 = paramNode;
/*  877 */       for (;;) { localNode3 = localObject1 == null ? LinkedTransferQueue.this.head : ((LinkedTransferQueue.Node)localObject1).next;
/*  878 */         if (localNode3 == null)
/*      */           break;
/*  880 */         if (localNode3 == localObject1) {
/*  881 */           localObject1 = null;
/*      */         }
/*      */         else {
/*  884 */           Object localObject2 = localNode3.item;
/*  885 */           if (localNode3.isData) {
/*  886 */             if ((localObject2 != null) && (localObject2 != localNode3)) {
/*  887 */               this.nextItem = LinkedTransferQueue.cast(localObject2);
/*  888 */               this.nextNode = localNode3;
/*      */             }
/*      */           }
/*      */           else {
/*  892 */             if (localObject2 == null)
/*      */               break;
/*      */           }
/*  895 */           if (localObject1 == null) {
/*  896 */             localObject1 = localNode3; } else { LinkedTransferQueue.Node localNode4;
/*  897 */             if ((localNode4 = localNode3.next) == null)
/*      */               break;
/*  899 */             if (localNode3 == localNode4) {
/*  900 */               localObject1 = null;
/*      */             } else
/*  902 */               ((LinkedTransferQueue.Node)localObject1).casNext(localNode3, localNode4);
/*      */           } } }
/*  904 */       this.nextNode = null;
/*  905 */       this.nextItem = null;
/*      */     }
/*      */     
/*      */     Itr() {
/*  909 */       advance(null);
/*      */     }
/*      */     
/*      */     public final boolean hasNext() {
/*  913 */       return this.nextNode != null;
/*      */     }
/*      */     
/*      */     public final E next() {
/*  917 */       LinkedTransferQueue.Node localNode = this.nextNode;
/*  918 */       if (localNode == null) throw new NoSuchElementException();
/*  919 */       Object localObject = this.nextItem;
/*  920 */       advance(localNode);
/*  921 */       return (E)localObject;
/*      */     }
/*      */     
/*      */     public final void remove() {
/*  925 */       LinkedTransferQueue.Node localNode = this.lastRet;
/*  926 */       if (localNode == null)
/*  927 */         throw new IllegalStateException();
/*  928 */       this.lastRet = null;
/*  929 */       if (localNode.tryMatchData())
/*  930 */         LinkedTransferQueue.this.unsplice(this.lastPred, localNode);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class LTQSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedTransferQueue<E> queue;
/*      */     LinkedTransferQueue.Node current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     
/*      */     LTQSpliterator(LinkedTransferQueue<E> paramLinkedTransferQueue) {
/*  942 */       this.queue = paramLinkedTransferQueue;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit()
/*      */     {
/*  947 */       LinkedTransferQueue localLinkedTransferQueue = this.queue;
/*  948 */       int i = this.batch;
/*  949 */       int j = i >= 33554432 ? 33554432 : i <= 0 ? 1 : i + 1;
/*  950 */       LinkedTransferQueue.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/*  951 */         ((localNode = localLinkedTransferQueue.firstDataNode()) != null)) && (localNode.next != null))
/*      */       {
/*  953 */         Object[] arrayOfObject = new Object[j];
/*  954 */         int k = 0;
/*      */         do {
/*  956 */           if ((arrayOfObject[k] = localNode.item) != null)
/*  957 */             k++;
/*  958 */           if (localNode == (localNode = localNode.next))
/*  959 */             localNode = localLinkedTransferQueue.firstDataNode();
/*  960 */         } while ((localNode != null) && (k < j));
/*  961 */         if ((this.current = localNode) == null)
/*  962 */           this.exhausted = true;
/*  963 */         if (k > 0) {
/*  964 */           this.batch = k;
/*      */           
/*  966 */           return Spliterators.spliterator(arrayOfObject, 0, k, 4368);
/*      */         }
/*      */       }
/*      */       
/*  970 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/*  976 */       if (paramConsumer == null) throw new NullPointerException();
/*  977 */       LinkedTransferQueue localLinkedTransferQueue = this.queue;
/*  978 */       LinkedTransferQueue.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/*  979 */         ((localNode = localLinkedTransferQueue.firstDataNode()) != null))) {
/*  980 */         this.exhausted = true;
/*      */         do {
/*  982 */           Object localObject = localNode.item;
/*  983 */           if (localNode == (localNode = localNode.next))
/*  984 */             localNode = localLinkedTransferQueue.firstDataNode();
/*  985 */           if (localObject != null)
/*  986 */             paramConsumer.accept(localObject);
/*  987 */         } while (localNode != null);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer)
/*      */     {
/*  994 */       if (paramConsumer == null) throw new NullPointerException();
/*  995 */       LinkedTransferQueue localLinkedTransferQueue = this.queue;
/*  996 */       LinkedTransferQueue.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/*  997 */         ((localNode = localLinkedTransferQueue.firstDataNode()) != null))) {
/*      */         Object localObject;
/*      */         do {
/* 1000 */           localObject = localNode.item;
/* 1001 */           if (localNode == (localNode = localNode.next))
/* 1002 */             localNode = localLinkedTransferQueue.firstDataNode();
/* 1003 */         } while ((localObject == null) && (localNode != null));
/* 1004 */         if ((this.current = localNode) == null)
/* 1005 */           this.exhausted = true;
/* 1006 */         if (localObject != null) {
/* 1007 */           paramConsumer.accept(localObject);
/* 1008 */           return true;
/*      */         }
/*      */       }
/* 1011 */       return false;
/*      */     }
/*      */     
/* 1014 */     public long estimateSize() { return Long.MAX_VALUE; }
/*      */     
/*      */     public int characteristics() {
/* 1017 */       return 4368;
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1039 */     return new LTQSpliterator(this);
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
/*      */   final void unsplice(Node paramNode1, Node paramNode2)
/*      */   {
/* 1053 */     paramNode2.forgetContents();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1061 */     if ((paramNode1 != null) && (paramNode1 != paramNode2) && (paramNode1.next == paramNode2)) {
/* 1062 */       Node localNode1 = paramNode2.next;
/* 1063 */       if ((localNode1 == null) || ((localNode1 != paramNode2) && 
/* 1064 */         (paramNode1.casNext(paramNode2, localNode1)) && (paramNode1.isMatched()))) {
/*      */         for (;;) {
/* 1066 */           Node localNode2 = this.head;
/* 1067 */           if ((localNode2 == paramNode1) || (localNode2 == paramNode2) || (localNode2 == null))
/* 1068 */             return;
/* 1069 */           if (!localNode2.isMatched())
/*      */             break;
/* 1071 */           Node localNode3 = localNode2.next;
/* 1072 */           if (localNode3 == null)
/* 1073 */             return;
/* 1074 */           if ((localNode3 != localNode2) && (casHead(localNode2, localNode3)))
/* 1075 */             localNode2.forgetNext();
/*      */         }
/* 1077 */         if ((paramNode1.next != paramNode1) && (paramNode2.next != paramNode2)) {
/*      */           for (;;) {
/* 1079 */             int i = this.sweepVotes;
/* 1080 */             if (i < 32) {
/* 1081 */               if (casSweepVotes(i, i + 1)) {
/*      */                 break;
/*      */               }
/* 1084 */             } else if (casSweepVotes(i, 0)) {
/* 1085 */               sweep();
/* 1086 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void sweep()
/*      */   {
/*      */     Node localNode1;
/*      */     
/* 1099 */     for (Object localObject = this.head; (localObject != null) && ((localNode1 = ((Node)localObject).next) != null);) {
/* 1100 */       if (!localNode1.isMatched())
/*      */       {
/* 1102 */         localObject = localNode1; } else { Node localNode2;
/* 1103 */         if ((localNode2 = localNode1.next) == null)
/*      */           break;
/* 1105 */         if (localNode1 == localNode2)
/*      */         {
/* 1107 */           localObject = this.head;
/*      */         } else
/* 1109 */           ((Node)localObject).casNext(localNode1, localNode2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean findAndRemove(Object paramObject) {
/*      */     Object localObject1;
/*      */     Node localNode;
/* 1117 */     if (paramObject != null) {
/* 1118 */       localObject1 = null; for (localNode = this.head; localNode != null;) {
/* 1119 */         Object localObject2 = localNode.item;
/* 1120 */         if (localNode.isData) {
/* 1121 */           if ((localObject2 != null) && (localObject2 != localNode) && (paramObject.equals(localObject2)) && 
/* 1122 */             (localNode.tryMatchData())) {
/* 1123 */             unsplice((Node)localObject1, localNode);
/* 1124 */             return true;
/*      */           }
/*      */         } else
/* 1127 */           if (localObject2 == null)
/*      */             break;
/* 1129 */         localObject1 = localNode;
/* 1130 */         if ((localNode = localNode.next) == localObject1) {
/* 1131 */           localObject1 = null;
/* 1132 */           localNode = this.head;
/*      */         }
/*      */       }
/*      */     }
/* 1136 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedTransferQueue() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedTransferQueue(Collection<? extends E> paramCollection)
/*      */   {
/* 1155 */     this();
/* 1156 */     addAll(paramCollection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(E paramE)
/*      */   {
/* 1166 */     xfer(paramE, true, 1, 0L);
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/* 1180 */     xfer(paramE, true, 1, 0L);
/* 1181 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offer(E paramE)
/*      */   {
/* 1192 */     xfer(paramE, true, 1, 0L);
/* 1193 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean add(E paramE)
/*      */   {
/* 1205 */     xfer(paramE, true, 1, 0L);
/* 1206 */     return true;
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
/*      */   public boolean tryTransfer(E paramE)
/*      */   {
/* 1220 */     return xfer(paramE, true, 0, 0L) == null;
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
/*      */   public void transfer(E paramE)
/*      */     throws InterruptedException
/*      */   {
/* 1235 */     if (xfer(paramE, true, 2, 0L) != null) {
/* 1236 */       Thread.interrupted();
/* 1237 */       throw new InterruptedException();
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
/*      */   public boolean tryTransfer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/* 1257 */     if (xfer(paramE, true, 3, paramTimeUnit.toNanos(paramLong)) == null)
/* 1258 */       return true;
/* 1259 */     if (!Thread.interrupted())
/* 1260 */       return false;
/* 1261 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/* 1265 */     Object localObject = xfer(null, false, 2, 0L);
/* 1266 */     if (localObject != null)
/* 1267 */       return (E)localObject;
/* 1268 */     Thread.interrupted();
/* 1269 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 1273 */     Object localObject = xfer(null, false, 3, paramTimeUnit.toNanos(paramLong));
/* 1274 */     if ((localObject != null) || (!Thread.interrupted()))
/* 1275 */       return (E)localObject;
/* 1276 */     throw new InterruptedException();
/*      */   }
/*      */   
/*      */   public E poll() {
/* 1280 */     return (E)xfer(null, false, 0, 0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection)
/*      */   {
/* 1288 */     if (paramCollection == null)
/* 1289 */       throw new NullPointerException();
/* 1290 */     if (paramCollection == this)
/* 1291 */       throw new IllegalArgumentException();
/* 1292 */     int i = 0;
/* 1293 */     Object localObject; while ((localObject = poll()) != null) {
/* 1294 */       paramCollection.add(localObject);
/* 1295 */       i++;
/*      */     }
/* 1297 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*      */   {
/* 1305 */     if (paramCollection == null)
/* 1306 */       throw new NullPointerException();
/* 1307 */     if (paramCollection == this)
/* 1308 */       throw new IllegalArgumentException();
/* 1309 */     int i = 0;
/* 1310 */     Object localObject; while ((i < paramInt) && ((localObject = poll()) != null)) {
/* 1311 */       paramCollection.add(localObject);
/* 1312 */       i++;
/*      */     }
/* 1314 */     return i;
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
/*      */   public Iterator<E> iterator()
/*      */   {
/* 1327 */     return new Itr();
/*      */   }
/*      */   
/*      */   public E peek() {
/* 1331 */     return (E)firstDataItem();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/* 1340 */     for (Node localNode = this.head; localNode != null; localNode = succ(localNode)) {
/* 1341 */       if (!localNode.isMatched())
/* 1342 */         return !localNode.isData;
/*      */     }
/* 1344 */     return true;
/*      */   }
/*      */   
/*      */   public boolean hasWaitingConsumer() {
/* 1348 */     return firstOfMode(false) != null;
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
/*      */   public int size()
/*      */   {
/* 1364 */     return countOfMode(true);
/*      */   }
/*      */   
/*      */   public int getWaitingConsumerCount() {
/* 1368 */     return countOfMode(false);
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/* 1383 */     return findAndRemove(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(Object paramObject)
/*      */   {
/* 1395 */     if (paramObject == null) return false;
/* 1396 */     for (Node localNode = this.head; localNode != null; localNode = succ(localNode)) {
/* 1397 */       Object localObject = localNode.item;
/* 1398 */       if (localNode.isData) {
/* 1399 */         if ((localObject != null) && (localObject != localNode) && (paramObject.equals(localObject)))
/* 1400 */           return true;
/*      */       } else
/* 1402 */         if (localObject == null)
/*      */           break;
/*      */     }
/* 1405 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int remainingCapacity()
/*      */   {
/* 1417 */     return Integer.MAX_VALUE;
/*      */   }
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
/* 1430 */     paramObjectOutputStream.defaultWriteObject();
/* 1431 */     for (Object localObject : this) {
/* 1432 */       paramObjectOutputStream.writeObject(localObject);
/*      */     }
/* 1434 */     paramObjectOutputStream.writeObject(null);
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
/* 1446 */     paramObjectInputStream.defaultReadObject();
/*      */     for (;;)
/*      */     {
/* 1449 */       Object localObject = paramObjectInputStream.readObject();
/* 1450 */       if (localObject == null) {
/*      */         break;
/*      */       }
/* 1453 */       offer(localObject);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 1465 */       UNSAFE = Unsafe.getUnsafe();
/* 1466 */       Class localClass = LinkedTransferQueue.class;
/*      */       
/* 1468 */       headOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("head"));
/*      */       
/* 1470 */       tailOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("tail"));
/*      */       
/* 1472 */       sweepVotesOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("sweepVotes"));
/*      */     } catch (Exception localException) {
/* 1474 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/LinkedTransferQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */