/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
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
/*      */ public class ConcurrentLinkedDeque<E>
/*      */   extends AbstractCollection<E>
/*      */   implements Deque<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 876323262645176354L;
/*      */   private volatile transient Node<E> head;
/*      */   private volatile transient Node<E> tail;
/*      */   
/*      */   Node<E> prevTerminator()
/*      */   {
/*  282 */     return PREV_TERMINATOR;
/*      */   }
/*      */   
/*      */   Node<E> nextTerminator()
/*      */   {
/*  287 */     return NEXT_TERMINATOR;
/*      */   }
/*      */   
/*      */   static final class Node<E>
/*      */   {
/*      */     volatile Node<E> prev;
/*      */     volatile E item;
/*      */     volatile Node<E> next;
/*      */     private static final Unsafe UNSAFE;
/*      */     private static final long prevOffset;
/*      */     private static final long itemOffset;
/*      */     private static final long nextOffset;
/*      */     
/*      */     Node() {}
/*      */     
/*      */     Node(E paramE) {
/*  303 */       UNSAFE.putObject(this, itemOffset, paramE);
/*      */     }
/*      */     
/*      */     boolean casItem(E paramE1, E paramE2) {
/*  307 */       return UNSAFE.compareAndSwapObject(this, itemOffset, paramE1, paramE2);
/*      */     }
/*      */     
/*      */     void lazySetNext(Node<E> paramNode) {
/*  311 */       UNSAFE.putOrderedObject(this, nextOffset, paramNode);
/*      */     }
/*      */     
/*      */     boolean casNext(Node<E> paramNode1, Node<E> paramNode2) {
/*  315 */       return UNSAFE.compareAndSwapObject(this, nextOffset, paramNode1, paramNode2);
/*      */     }
/*      */     
/*      */     void lazySetPrev(Node<E> paramNode) {
/*  319 */       UNSAFE.putOrderedObject(this, prevOffset, paramNode);
/*      */     }
/*      */     
/*      */     boolean casPrev(Node<E> paramNode1, Node<E> paramNode2) {
/*  323 */       return UNSAFE.compareAndSwapObject(this, prevOffset, paramNode1, paramNode2);
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
/*  335 */         UNSAFE = Unsafe.getUnsafe();
/*  336 */         Class localClass = Node.class;
/*      */         
/*  338 */         prevOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("prev"));
/*      */         
/*  340 */         itemOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("item"));
/*      */         
/*  342 */         nextOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("next"));
/*      */       } catch (Exception localException) {
/*  344 */         throw new Error(localException);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void linkFirst(E paramE)
/*      */   {
/*  353 */     checkNotNull(paramE);
/*  354 */     Node localNode1 = new Node(paramE);
/*      */     
/*      */ 
/*      */ 
/*  358 */     Node localNode2 = this.head;Object localObject = localNode2;
/*  359 */     do { Node localNode3; while (((localNode3 = ((Node)localObject).prev) != null) && ((localNode3 = (localObject = localNode3).prev) != null))
/*      */       {
/*      */ 
/*      */ 
/*  363 */         localObject = localNode2 != (localNode2 = this.head) ? localNode2 : localNode3; }
/*  364 */       if (((Node)localObject).next == localObject) {
/*      */         break;
/*      */       }
/*      */       
/*  368 */       localNode1.lazySetNext((Node)localObject);
/*  369 */     } while (!((Node)localObject).casPrev(null, localNode1));
/*      */     
/*      */ 
/*      */ 
/*  373 */     if (localObject != localNode2) {
/*  374 */       casHead(localNode2, localNode1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void linkLast(E paramE)
/*      */   {
/*  386 */     checkNotNull(paramE);
/*  387 */     Node localNode1 = new Node(paramE);
/*      */     
/*      */ 
/*      */ 
/*  391 */     Node localNode2 = this.tail;Object localObject = localNode2;
/*  392 */     do { Node localNode3; while (((localNode3 = ((Node)localObject).next) != null) && ((localNode3 = (localObject = localNode3).next) != null))
/*      */       {
/*      */ 
/*      */ 
/*  396 */         localObject = localNode2 != (localNode2 = this.tail) ? localNode2 : localNode3; }
/*  397 */       if (((Node)localObject).prev == localObject) {
/*      */         break;
/*      */       }
/*      */       
/*  401 */       localNode1.lazySetPrev((Node)localObject);
/*  402 */     } while (!((Node)localObject).casNext(null, localNode1));
/*      */     
/*      */ 
/*      */ 
/*  406 */     if (localObject != localNode2) {
/*  407 */       casTail(localNode2, localNode1);
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
/*      */   void unlink(Node<E> paramNode)
/*      */   {
/*  426 */     Node localNode1 = paramNode.prev;
/*  427 */     Node localNode2 = paramNode.next;
/*  428 */     if (localNode1 == null) {
/*  429 */       unlinkFirst(paramNode, localNode2);
/*  430 */     } else if (localNode2 == null) {
/*  431 */       unlinkLast(paramNode, localNode1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  454 */       int k = 1;
/*      */       Object localObject1;
/*      */       int i;
/*  457 */       Node localNode3; for (Object localObject3 = localNode1;; k++) {
/*  458 */         if (((Node)localObject3).item != null) {
/*  459 */           localObject1 = localObject3;
/*  460 */           i = 0;
/*  461 */           break;
/*      */         }
/*  463 */         localNode3 = ((Node)localObject3).prev;
/*  464 */         if (localNode3 == null) {
/*  465 */           if (((Node)localObject3).next == localObject3)
/*  466 */             return;
/*  467 */           localObject1 = localObject3;
/*  468 */           i = 1;
/*  469 */           break;
/*      */         }
/*  471 */         if (localObject3 == localNode3) {
/*  472 */           return;
/*      */         }
/*  474 */         localObject3 = localNode3;
/*      */       }
/*      */       Object localObject2;
/*      */       int j;
/*  478 */       for (localObject3 = localNode2;; k++) {
/*  479 */         if (((Node)localObject3).item != null) {
/*  480 */           localObject2 = localObject3;
/*  481 */           j = 0;
/*  482 */           break;
/*      */         }
/*  484 */         localNode3 = ((Node)localObject3).next;
/*  485 */         if (localNode3 == null) {
/*  486 */           if (((Node)localObject3).prev == localObject3)
/*  487 */             return;
/*  488 */           localObject2 = localObject3;
/*  489 */           j = 1;
/*  490 */           break;
/*      */         }
/*  492 */         if (localObject3 == localNode3) {
/*  493 */           return;
/*      */         }
/*  495 */         localObject3 = localNode3;
/*      */       }
/*      */       
/*      */ 
/*  499 */       if ((k < 2) && ((i | j) != 0))
/*      */       {
/*      */ 
/*  502 */         return;
/*      */       }
/*      */       
/*      */ 
/*  506 */       skipDeletedSuccessors((Node)localObject1);
/*  507 */       skipDeletedPredecessors((Node)localObject2);
/*      */       
/*      */ 
/*  510 */       if (((i | j) != 0) && (((Node)localObject1).next == localObject2) && (((Node)localObject2).prev == localObject1) && (i != 0 ? ((Node)localObject1).prev == null : ((Node)localObject1).item != null) && (j != 0 ? ((Node)localObject2).next == null : ((Node)localObject2).item != null))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  518 */         updateHead();
/*  519 */         updateTail();
/*      */         
/*      */ 
/*  522 */         paramNode.lazySetPrev(i != 0 ? prevTerminator() : paramNode);
/*  523 */         paramNode.lazySetNext(j != 0 ? nextTerminator() : paramNode);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void unlinkFirst(Node<E> paramNode1, Node<E> paramNode2)
/*      */   {
/*  535 */     Object localObject1 = null;Object localObject2 = paramNode2;
/*  536 */     for (;;) { Node localNode; if ((((Node)localObject2).item != null) || ((localNode = ((Node)localObject2).next) == null)) {
/*  537 */         if ((localObject1 != null) && (((Node)localObject2).prev != localObject2) && (paramNode1.casNext(paramNode2, (Node)localObject2))) {
/*  538 */           skipDeletedPredecessors((Node)localObject2);
/*  539 */           if ((paramNode1.prev == null) && ((((Node)localObject2).next == null) || (((Node)localObject2).item != null)) && (((Node)localObject2).prev == paramNode1))
/*      */           {
/*      */ 
/*      */ 
/*  543 */             updateHead();
/*  544 */             updateTail();
/*      */             
/*      */ 
/*  547 */             ((Node)localObject1).lazySetNext((Node)localObject1);
/*  548 */             ((Node)localObject1).lazySetPrev(prevTerminator());
/*      */           }
/*      */         }
/*  551 */         return;
/*      */       }
/*  553 */       if (localObject2 == localNode) {
/*  554 */         return;
/*      */       }
/*  556 */       localObject1 = localObject2;
/*  557 */       localObject2 = localNode;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void unlinkLast(Node<E> paramNode1, Node<E> paramNode2)
/*      */   {
/*  569 */     Object localObject1 = null;Object localObject2 = paramNode2;
/*  570 */     for (;;) { Node localNode; if ((((Node)localObject2).item != null) || ((localNode = ((Node)localObject2).prev) == null)) {
/*  571 */         if ((localObject1 != null) && (((Node)localObject2).next != localObject2) && (paramNode1.casPrev(paramNode2, (Node)localObject2))) {
/*  572 */           skipDeletedSuccessors((Node)localObject2);
/*  573 */           if ((paramNode1.next == null) && ((((Node)localObject2).prev == null) || (((Node)localObject2).item != null)) && (((Node)localObject2).next == paramNode1))
/*      */           {
/*      */ 
/*      */ 
/*  577 */             updateHead();
/*  578 */             updateTail();
/*      */             
/*      */ 
/*  581 */             ((Node)localObject1).lazySetPrev((Node)localObject1);
/*  582 */             ((Node)localObject1).lazySetNext(nextTerminator());
/*      */           }
/*      */         }
/*  585 */         return;
/*      */       }
/*  587 */       if (localObject2 == localNode) {
/*  588 */         return;
/*      */       }
/*  590 */       localObject1 = localObject2;
/*  591 */       localObject2 = localNode;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void updateHead()
/*      */   {
/*      */     Node localNode1;
/*      */     
/*      */ 
/*      */ 
/*      */     Object localObject;
/*      */     
/*      */ 
/*  607 */     if (((localNode1 = this.head).item == null) && ((localObject = localNode1.prev) != null)) {
/*      */       for (;;) { Node localNode2;
/*  609 */         if (((localNode2 = ((Node)localObject).prev) == null) || ((localNode2 = (localObject = localNode2).prev) == null))
/*      */         {
/*      */ 
/*      */ 
/*  613 */           if (!casHead(localNode1, (Node)localObject)) break;
/*  614 */           return;
/*      */         }
/*      */         
/*      */ 
/*  618 */         if (localNode1 != this.head) {
/*      */           break;
/*      */         }
/*  621 */         localObject = localNode2;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void updateTail()
/*      */   {
/*      */     Node localNode1;
/*      */     
/*      */ 
/*      */     Object localObject;
/*      */     
/*      */ 
/*  637 */     if (((localNode1 = this.tail).item == null) && ((localObject = localNode1.next) != null)) {
/*      */       for (;;) { Node localNode2;
/*  639 */         if (((localNode2 = ((Node)localObject).next) == null) || ((localNode2 = (localObject = localNode2).next) == null))
/*      */         {
/*      */ 
/*      */ 
/*  643 */           if (!casTail(localNode1, (Node)localObject)) break;
/*  644 */           return;
/*      */         }
/*      */         
/*      */ 
/*  648 */         if (localNode1 != this.tail) {
/*      */           break;
/*      */         }
/*  651 */         localObject = localNode2;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void skipDeletedPredecessors(Node<E> paramNode) {
/*      */     label69:
/*      */     do {
/*  659 */       Node localNode1 = paramNode.prev;
/*      */       
/*      */ 
/*      */ 
/*  663 */       Object localObject = localNode1;
/*      */       
/*      */ 
/*  666 */       while (((Node)localObject).item == null)
/*      */       {
/*  668 */         Node localNode2 = ((Node)localObject).prev;
/*  669 */         if (localNode2 == null) {
/*  670 */           if (((Node)localObject).next != localObject)
/*      */             break;
/*      */           break label69;
/*      */         }
/*  674 */         if (localObject == localNode2) {
/*      */           break label69;
/*      */         }
/*  677 */         localObject = localNode2;
/*      */       }
/*      */       
/*      */ 
/*  681 */       if ((localNode1 == localObject) || (paramNode.casPrev(localNode1, (Node)localObject))) {
/*  682 */         return;
/*      */       }
/*  684 */     } while ((paramNode.item != null) || (paramNode.next == null));
/*      */   }
/*      */   
/*      */   private void skipDeletedSuccessors(Node<E> paramNode) {
/*      */     label69:
/*      */     do {
/*  690 */       Node localNode1 = paramNode.next;
/*      */       
/*      */ 
/*      */ 
/*  694 */       Object localObject = localNode1;
/*      */       
/*      */ 
/*  697 */       while (((Node)localObject).item == null)
/*      */       {
/*  699 */         Node localNode2 = ((Node)localObject).next;
/*  700 */         if (localNode2 == null) {
/*  701 */           if (((Node)localObject).prev != localObject)
/*      */             break;
/*      */           break label69;
/*      */         }
/*  705 */         if (localObject == localNode2) {
/*      */           break label69;
/*      */         }
/*  708 */         localObject = localNode2;
/*      */       }
/*      */       
/*      */ 
/*  712 */       if ((localNode1 == localObject) || (paramNode.casNext(localNode1, (Node)localObject))) {
/*  713 */         return;
/*      */       }
/*  715 */     } while ((paramNode.item != null) || (paramNode.prev == null));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final Node<E> succ(Node<E> paramNode)
/*      */   {
/*  725 */     Node localNode = paramNode.next;
/*  726 */     return paramNode == localNode ? first() : localNode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final Node<E> pred(Node<E> paramNode)
/*      */   {
/*  735 */     Node localNode = paramNode.prev;
/*  736 */     return paramNode == localNode ? last() : localNode;
/*      */   }
/*      */   
/*      */ 
/*      */   Node<E> first()
/*      */   {
/*      */     Node localNode1;
/*      */     
/*      */     Object localObject;
/*      */     
/*      */     do
/*      */     {
/*  748 */       localNode1 = this.head;localObject = localNode1;
/*  749 */       Node localNode2; while (((localNode2 = ((Node)localObject).prev) != null) && ((localNode2 = (localObject = localNode2).prev) != null))
/*      */       {
/*      */ 
/*      */ 
/*  753 */         localObject = localNode1 != (localNode1 = this.head) ? localNode1 : localNode2; }
/*  754 */     } while ((localObject != localNode1) && 
/*      */     
/*      */ 
/*  757 */       (!casHead(localNode1, (Node)localObject)));
/*  758 */     return (Node<E>)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   Node<E> last()
/*      */   {
/*      */     Node localNode1;
/*      */     
/*      */ 
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */     do
/*      */     {
/*  773 */       localNode1 = this.tail;localObject = localNode1;
/*  774 */       Node localNode2; while (((localNode2 = ((Node)localObject).next) != null) && ((localNode2 = (localObject = localNode2).next) != null))
/*      */       {
/*      */ 
/*      */ 
/*  778 */         localObject = localNode1 != (localNode1 = this.tail) ? localNode1 : localNode2; }
/*  779 */     } while ((localObject != localNode1) && 
/*      */     
/*      */ 
/*  782 */       (!casTail(localNode1, (Node)localObject)));
/*  783 */     return (Node<E>)localObject;
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
/*      */   private static void checkNotNull(Object paramObject)
/*      */   {
/*  797 */     if (paramObject == null) {
/*  798 */       throw new NullPointerException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private E screenNullResult(E paramE)
/*      */   {
/*  809 */     if (paramE == null)
/*  810 */       throw new NoSuchElementException();
/*  811 */     return paramE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList<E> toArrayList()
/*      */   {
/*  821 */     ArrayList localArrayList = new ArrayList();
/*  822 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/*  823 */       Object localObject = localNode.item;
/*  824 */       if (localObject != null)
/*  825 */         localArrayList.add(localObject);
/*      */     }
/*  827 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ConcurrentLinkedDeque()
/*      */   {
/*  834 */     this.head = (this.tail = new Node(null));
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
/*      */   public ConcurrentLinkedDeque(Collection<? extends E> paramCollection)
/*      */   {
/*  848 */     Object localObject1 = null;Object localObject2 = null;
/*  849 */     for (Object localObject3 : paramCollection) {
/*  850 */       checkNotNull(localObject3);
/*  851 */       Node localNode = new Node(localObject3);
/*  852 */       if (localObject1 == null) {
/*  853 */         localObject1 = localObject2 = localNode;
/*      */       } else {
/*  855 */         ((Node)localObject2).lazySetNext(localNode);
/*  856 */         localNode.lazySetPrev((Node)localObject2);
/*  857 */         localObject2 = localNode;
/*      */       }
/*      */     }
/*  860 */     initHeadTail((Node)localObject1, (Node)localObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void initHeadTail(Node<E> paramNode1, Node<E> paramNode2)
/*      */   {
/*  867 */     if (paramNode1 == paramNode2) {
/*  868 */       if (paramNode1 == null) {
/*  869 */         paramNode1 = paramNode2 = new Node(null);
/*      */       }
/*      */       else {
/*  872 */         Node localNode = new Node(null);
/*  873 */         paramNode2.lazySetNext(localNode);
/*  874 */         localNode.lazySetPrev(paramNode2);
/*  875 */         paramNode2 = localNode;
/*      */       }
/*      */     }
/*  878 */     this.head = paramNode1;
/*  879 */     this.tail = paramNode2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addFirst(E paramE)
/*      */   {
/*  890 */     linkFirst(paramE);
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
/*      */   public void addLast(E paramE)
/*      */   {
/*  903 */     linkLast(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offerFirst(E paramE)
/*      */   {
/*  914 */     linkFirst(paramE);
/*  915 */     return true;
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
/*      */   public boolean offerLast(E paramE)
/*      */   {
/*  928 */     linkLast(paramE);
/*  929 */     return true;
/*      */   }
/*      */   
/*      */   public E peekFirst() {
/*  933 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/*  934 */       Object localObject = localNode.item;
/*  935 */       if (localObject != null)
/*  936 */         return (E)localObject;
/*      */     }
/*  938 */     return null;
/*      */   }
/*      */   
/*      */   public E peekLast() {
/*  942 */     for (Node localNode = last(); localNode != null; localNode = pred(localNode)) {
/*  943 */       Object localObject = localNode.item;
/*  944 */       if (localObject != null)
/*  945 */         return (E)localObject;
/*      */     }
/*  947 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E getFirst()
/*      */   {
/*  954 */     return (E)screenNullResult(peekFirst());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E getLast()
/*      */   {
/*  961 */     return (E)screenNullResult(peekLast());
/*      */   }
/*      */   
/*      */   public E pollFirst() {
/*  965 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/*  966 */       Object localObject = localNode.item;
/*  967 */       if ((localObject != null) && (localNode.casItem(localObject, null))) {
/*  968 */         unlink(localNode);
/*  969 */         return (E)localObject;
/*      */       }
/*      */     }
/*  972 */     return null;
/*      */   }
/*      */   
/*      */   public E pollLast() {
/*  976 */     for (Node localNode = last(); localNode != null; localNode = pred(localNode)) {
/*  977 */       Object localObject = localNode.item;
/*  978 */       if ((localObject != null) && (localNode.casItem(localObject, null))) {
/*  979 */         unlink(localNode);
/*  980 */         return (E)localObject;
/*      */       }
/*      */     }
/*  983 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E removeFirst()
/*      */   {
/*  990 */     return (E)screenNullResult(pollFirst());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E removeLast()
/*      */   {
/*  997 */     return (E)screenNullResult(pollLast());
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
/* 1010 */     return offerLast(paramE);
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
/* 1022 */     return offerLast(paramE);
/*      */   }
/*      */   
/* 1025 */   public E poll() { return (E)pollFirst(); }
/* 1026 */   public E peek() { return (E)peekFirst(); }
/*      */   
/*      */ 
/*      */   public E remove()
/*      */   {
/* 1031 */     return (E)removeFirst();
/*      */   }
/*      */   
/*      */   public E pop()
/*      */   {
/* 1036 */     return (E)removeFirst();
/*      */   }
/*      */   
/*      */   public E element()
/*      */   {
/* 1041 */     return (E)getFirst();
/*      */   }
/*      */   
/*      */   public void push(E paramE)
/*      */   {
/* 1046 */     addFirst(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean removeFirstOccurrence(Object paramObject)
/*      */   {
/* 1058 */     checkNotNull(paramObject);
/* 1059 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/* 1060 */       Object localObject = localNode.item;
/* 1061 */       if ((localObject != null) && (paramObject.equals(localObject)) && (localNode.casItem(localObject, null))) {
/* 1062 */         unlink(localNode);
/* 1063 */         return true;
/*      */       }
/*      */     }
/* 1066 */     return false;
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
/*      */   public boolean removeLastOccurrence(Object paramObject)
/*      */   {
/* 1079 */     checkNotNull(paramObject);
/* 1080 */     for (Node localNode = last(); localNode != null; localNode = pred(localNode)) {
/* 1081 */       Object localObject = localNode.item;
/* 1082 */       if ((localObject != null) && (paramObject.equals(localObject)) && (localNode.casItem(localObject, null))) {
/* 1083 */         unlink(localNode);
/* 1084 */         return true;
/*      */       }
/*      */     }
/* 1087 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(Object paramObject)
/*      */   {
/* 1098 */     if (paramObject == null) return false;
/* 1099 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/* 1100 */       Object localObject = localNode.item;
/* 1101 */       if ((localObject != null) && (paramObject.equals(localObject)))
/* 1102 */         return true;
/*      */     }
/* 1104 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/* 1113 */     return peekFirst() == null;
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
/*      */   public int size()
/*      */   {
/* 1133 */     int i = 0;
/* 1134 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode))
/* 1135 */       if (localNode.item != null)
/*      */       {
/* 1137 */         i++; if (i == Integer.MAX_VALUE) break;
/*      */       }
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/* 1152 */     return removeFirstOccurrence(paramObject);
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection)
/*      */   {
/* 1168 */     if (paramCollection == this)
/*      */     {
/* 1170 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 1173 */     Object localObject1 = null;Object localObject2 = null;
/* 1174 */     for (Object localObject3 = paramCollection.iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = ((Iterator)localObject3).next();
/* 1175 */       checkNotNull(localObject4);
/* 1176 */       localNode = new Node(localObject4);
/* 1177 */       if (localObject1 == null) {
/* 1178 */         localObject1 = localObject2 = localNode;
/*      */       } else {
/* 1180 */         ((Node)localObject2).lazySetNext(localNode);
/* 1181 */         localNode.lazySetPrev((Node)localObject2);
/* 1182 */         localObject2 = localNode;
/*      */       } }
/*      */     Node localNode;
/* 1185 */     if (localObject1 == null) {
/* 1186 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1191 */     localObject3 = this.tail;Object localObject4 = localObject3;
/* 1192 */     do { while (((localNode = ((Node)localObject4).next) != null) && ((localNode = (localObject4 = localNode).next) != null))
/*      */       {
/*      */ 
/*      */ 
/* 1196 */         localObject4 = localObject3 != (localObject3 = this.tail) ? localObject3 : localNode; }
/* 1197 */       if (((Node)localObject4).prev == localObject4) {
/*      */         break;
/*      */       }
/*      */       
/* 1201 */       ((Node)localObject1).lazySetPrev((Node)localObject4);
/* 1202 */     } while (!((Node)localObject4).casNext(null, (Node)localObject1));
/*      */     
/*      */ 
/* 1205 */     if (!casTail((Node)localObject3, (Node)localObject2))
/*      */     {
/*      */ 
/* 1208 */       localObject3 = this.tail;
/* 1209 */       if (((Node)localObject2).next == null)
/* 1210 */         casTail((Node)localObject3, (Node)localObject2);
/*      */     }
/* 1212 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/* 1223 */     while (pollFirst() != null) {}
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
/*      */   public Object[] toArray()
/*      */   {
/* 1241 */     return toArrayList().toArray();
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/* 1282 */     return toArrayList().toArray(paramArrayOfT);
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
/* 1295 */     return new Itr(null);
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
/*      */   public Iterator<E> descendingIterator()
/*      */   {
/* 1309 */     return new DescendingItr(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private abstract class AbstractItr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private ConcurrentLinkedDeque.Node<E> nextNode;
/*      */     
/*      */ 
/*      */     private E nextItem;
/*      */     
/*      */ 
/*      */     private ConcurrentLinkedDeque.Node<E> lastRet;
/*      */     
/*      */ 
/*      */ 
/*      */     abstract ConcurrentLinkedDeque.Node<E> startNode();
/*      */     
/*      */ 
/*      */ 
/*      */     abstract ConcurrentLinkedDeque.Node<E> nextNode(ConcurrentLinkedDeque.Node<E> paramNode);
/*      */     
/*      */ 
/*      */     AbstractItr()
/*      */     {
/* 1336 */       advance();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private void advance()
/*      */     {
/* 1344 */       this.lastRet = this.nextNode;
/*      */       
/* 1346 */       ConcurrentLinkedDeque.Node localNode = this.nextNode == null ? startNode() : nextNode(this.nextNode);
/* 1347 */       for (;; localNode = nextNode(localNode)) {
/* 1348 */         if (localNode == null)
/*      */         {
/* 1350 */           this.nextNode = null;
/* 1351 */           this.nextItem = null;
/* 1352 */           break;
/*      */         }
/* 1354 */         Object localObject = localNode.item;
/* 1355 */         if (localObject != null) {
/* 1356 */           this.nextNode = localNode;
/* 1357 */           this.nextItem = localObject;
/* 1358 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1364 */       return this.nextItem != null;
/*      */     }
/*      */     
/*      */     public E next() {
/* 1368 */       Object localObject = this.nextItem;
/* 1369 */       if (localObject == null) throw new NoSuchElementException();
/* 1370 */       advance();
/* 1371 */       return (E)localObject;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1375 */       ConcurrentLinkedDeque.Node localNode = this.lastRet;
/* 1376 */       if (localNode == null) throw new IllegalStateException();
/* 1377 */       localNode.item = null;
/* 1378 */       ConcurrentLinkedDeque.this.unlink(localNode);
/* 1379 */       this.lastRet = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class Itr extends ConcurrentLinkedDeque<E>.AbstractItr {
/* 1384 */     private Itr() { super(); }
/* 1385 */     ConcurrentLinkedDeque.Node<E> startNode() { return ConcurrentLinkedDeque.this.first(); }
/* 1386 */     ConcurrentLinkedDeque.Node<E> nextNode(ConcurrentLinkedDeque.Node<E> paramNode) { return ConcurrentLinkedDeque.this.succ(paramNode); }
/*      */   }
/*      */   
/*      */   private class DescendingItr extends ConcurrentLinkedDeque<E>.AbstractItr {
/* 1390 */     private DescendingItr() { super(); }
/* 1391 */     ConcurrentLinkedDeque.Node<E> startNode() { return ConcurrentLinkedDeque.this.last(); }
/* 1392 */     ConcurrentLinkedDeque.Node<E> nextNode(ConcurrentLinkedDeque.Node<E> paramNode) { return ConcurrentLinkedDeque.this.pred(paramNode); }
/*      */   }
/*      */   
/*      */   static final class CLDSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final ConcurrentLinkedDeque<E> queue;
/*      */     ConcurrentLinkedDeque.Node<E> current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     
/*      */     CLDSpliterator(ConcurrentLinkedDeque<E> paramConcurrentLinkedDeque) {
/* 1403 */       this.queue = paramConcurrentLinkedDeque;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit()
/*      */     {
/* 1408 */       ConcurrentLinkedDeque localConcurrentLinkedDeque = this.queue;
/* 1409 */       int i = this.batch;
/* 1410 */       int j = i >= 33554432 ? 33554432 : i <= 0 ? 1 : i + 1;
/* 1411 */       ConcurrentLinkedDeque.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/* 1412 */         ((localNode = localConcurrentLinkedDeque.first()) != null))) {
/* 1413 */         if ((localNode.item == null) && (localNode == (localNode = localNode.next)))
/* 1414 */           this.current = (localNode = localConcurrentLinkedDeque.first());
/* 1415 */         if ((localNode != null) && (localNode.next != null)) {
/* 1416 */           Object[] arrayOfObject = new Object[j];
/* 1417 */           int k = 0;
/*      */           do {
/* 1419 */             if ((arrayOfObject[k] = localNode.item) != null)
/* 1420 */               k++;
/* 1421 */             if (localNode == (localNode = localNode.next))
/* 1422 */               localNode = localConcurrentLinkedDeque.first();
/* 1423 */           } while ((localNode != null) && (k < j));
/* 1424 */           if ((this.current = localNode) == null)
/* 1425 */             this.exhausted = true;
/* 1426 */           if (k > 0) {
/* 1427 */             this.batch = k;
/*      */             
/* 1429 */             return Spliterators.spliterator(arrayOfObject, 0, k, 4368);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1434 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1439 */       if (paramConsumer == null) throw new NullPointerException();
/* 1440 */       ConcurrentLinkedDeque localConcurrentLinkedDeque = this.queue;
/* 1441 */       ConcurrentLinkedDeque.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/* 1442 */         ((localNode = localConcurrentLinkedDeque.first()) != null))) {
/* 1443 */         this.exhausted = true;
/*      */         do {
/* 1445 */           Object localObject = localNode.item;
/* 1446 */           if (localNode == (localNode = localNode.next))
/* 1447 */             localNode = localConcurrentLinkedDeque.first();
/* 1448 */           if (localObject != null)
/* 1449 */             paramConsumer.accept(localObject);
/* 1450 */         } while (localNode != null);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer)
/*      */     {
/* 1456 */       if (paramConsumer == null) throw new NullPointerException();
/* 1457 */       ConcurrentLinkedDeque localConcurrentLinkedDeque = this.queue;
/* 1458 */       ConcurrentLinkedDeque.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/* 1459 */         ((localNode = localConcurrentLinkedDeque.first()) != null))) {
/*      */         Object localObject;
/*      */         do {
/* 1462 */           localObject = localNode.item;
/* 1463 */           if (localNode == (localNode = localNode.next))
/* 1464 */             localNode = localConcurrentLinkedDeque.first();
/* 1465 */         } while ((localObject == null) && (localNode != null));
/* 1466 */         if ((this.current = localNode) == null)
/* 1467 */           this.exhausted = true;
/* 1468 */         if (localObject != null) {
/* 1469 */           paramConsumer.accept(localObject);
/* 1470 */           return true;
/*      */         }
/*      */       }
/* 1473 */       return false;
/*      */     }
/*      */     
/* 1476 */     public long estimateSize() { return Long.MAX_VALUE; }
/*      */     
/*      */     public int characteristics() {
/* 1479 */       return 4368;
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
/* 1501 */     return new CLDSpliterator(this);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1516 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*      */ 
/* 1519 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/* 1520 */       Object localObject = localNode.item;
/* 1521 */       if (localObject != null) {
/* 1522 */         paramObjectOutputStream.writeObject(localObject);
/*      */       }
/*      */     }
/*      */     
/* 1526 */     paramObjectOutputStream.writeObject(null);
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
/* 1538 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/* 1541 */     Object localObject1 = null;Object localObject2 = null;
/*      */     Object localObject3;
/* 1543 */     while ((localObject3 = paramObjectInputStream.readObject()) != null)
/*      */     {
/* 1545 */       Node localNode = new Node(localObject3);
/* 1546 */       if (localObject1 == null) {
/* 1547 */         localObject1 = localObject2 = localNode;
/*      */       } else {
/* 1549 */         ((Node)localObject2).lazySetNext(localNode);
/* 1550 */         localNode.lazySetPrev((Node)localObject2);
/* 1551 */         localObject2 = localNode;
/*      */       }
/*      */     }
/* 1554 */     initHeadTail((Node)localObject1, (Node)localObject2);
/*      */   }
/*      */   
/*      */   private boolean casHead(Node<E> paramNode1, Node<E> paramNode2) {
/* 1558 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   private boolean casTail(Node<E> paramNode1, Node<E> paramNode2) {
/* 1562 */     return UNSAFE.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1571 */   private static final Node<Object> PREV_TERMINATOR = new Node();
/* 1572 */   static { PREV_TERMINATOR.next = PREV_TERMINATOR;
/* 1573 */     NEXT_TERMINATOR = new Node();
/* 1574 */     NEXT_TERMINATOR.prev = NEXT_TERMINATOR;
/*      */     try {
/* 1576 */       UNSAFE = Unsafe.getUnsafe();
/* 1577 */       Class localClass = ConcurrentLinkedDeque.class;
/*      */       
/* 1579 */       headOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("head"));
/*      */       
/* 1581 */       tailOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("tail"));
/*      */     } catch (Exception localException) {
/* 1583 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final Node<Object> NEXT_TERMINATOR;
/*      */   private static final int HOPS = 2;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long headOffset;
/*      */   private static final long tailOffset;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ConcurrentLinkedDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */