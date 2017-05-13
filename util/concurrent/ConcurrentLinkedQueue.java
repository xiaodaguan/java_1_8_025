/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentLinkedQueue<E>
/*     */   extends AbstractQueue<E>
/*     */   implements Queue<E>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 196745693267521676L;
/*     */   private volatile transient Node<E> head;
/*     */   private volatile transient Node<E> tail;
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long headOffset;
/*     */   private static final long tailOffset;
/*     */   
/*     */   private static class Node<E>
/*     */   {
/*     */     volatile E item;
/*     */     volatile Node<E> next;
/*     */     private static final Unsafe UNSAFE;
/*     */     private static final long itemOffset;
/*     */     private static final long nextOffset;
/*     */     
/*     */     Node(E paramE)
/*     */     {
/* 189 */       UNSAFE.putObject(this, itemOffset, paramE);
/*     */     }
/*     */     
/*     */     boolean casItem(E paramE1, E paramE2) {
/* 193 */       return UNSAFE.compareAndSwapObject(this, itemOffset, paramE1, paramE2);
/*     */     }
/*     */     
/*     */     void lazySetNext(Node<E> paramNode) {
/* 197 */       UNSAFE.putOrderedObject(this, nextOffset, paramNode);
/*     */     }
/*     */     
/*     */     boolean casNext(Node<E> paramNode1, Node<E> paramNode2) {
/* 201 */       return UNSAFE.compareAndSwapObject(this, nextOffset, paramNode1, paramNode2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static
/*     */     {
/*     */       try
/*     */       {
/* 212 */         UNSAFE = Unsafe.getUnsafe();
/* 213 */         Class localClass = Node.class;
/*     */         
/* 215 */         itemOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("item"));
/*     */         
/* 217 */         nextOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("next"));
/*     */       } catch (Exception localException) {
/* 219 */         throw new Error(localException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConcurrentLinkedQueue()
/*     */   {
/* 256 */     this.head = (this.tail = new Node(null));
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
/*     */   public ConcurrentLinkedQueue(Collection<? extends E> paramCollection)
/*     */   {
/* 269 */     Object localObject1 = null;Object localObject2 = null;
/* 270 */     for (Object localObject3 : paramCollection) {
/* 271 */       checkNotNull(localObject3);
/* 272 */       Node localNode = new Node(localObject3);
/* 273 */       if (localObject1 == null) {
/* 274 */         localObject1 = localObject2 = localNode;
/*     */       } else {
/* 276 */         ((Node)localObject2).lazySetNext(localNode);
/* 277 */         localObject2 = localNode;
/*     */       }
/*     */     }
/* 280 */     if (localObject1 == null)
/* 281 */       localObject1 = localObject2 = new Node(null);
/* 282 */     this.head = ((Node)localObject1);
/* 283 */     this.tail = ((Node)localObject2);
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 297 */     return offer(paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final void updateHead(Node<E> paramNode1, Node<E> paramNode2)
/*     */   {
/* 305 */     if ((paramNode1 != paramNode2) && (casHead(paramNode1, paramNode2))) {
/* 306 */       paramNode1.lazySetNext(paramNode1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final Node<E> succ(Node<E> paramNode)
/*     */   {
/* 315 */     Node localNode = paramNode.next;
/* 316 */     return paramNode == localNode ? this.head : localNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean offer(E paramE)
/*     */   {
/* 327 */     checkNotNull(paramE);
/* 328 */     Node localNode1 = new Node(paramE);
/*     */     
/* 330 */     Node localNode2 = this.tail;Object localObject = localNode2;
/* 331 */     for (;;) { Node localNode3 = ((Node)localObject).next;
/* 332 */       if (localNode3 == null)
/*     */       {
/* 334 */         if (((Node)localObject).casNext(null, localNode1))
/*     */         {
/*     */ 
/*     */ 
/* 338 */           if (localObject != localNode2)
/* 339 */             casTail(localNode2, localNode1);
/* 340 */           return true;
/*     */         }
/*     */         
/*     */       }
/* 344 */       else if (localObject == localNode3)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 349 */         localObject = localNode2 != (localNode2 = this.tail) ? localNode2 : this.head;
/*     */       }
/*     */       else {
/* 352 */         localObject = (localObject != localNode2) && (localNode2 != (localNode2 = this.tail)) ? localNode2 : localNode3;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public E poll()
/*     */   {
/* 359 */     Node localNode1 = this.head;Object localObject1 = localNode1;
/* 360 */     for (;;) { Object localObject2 = ((Node)localObject1).item;
/*     */       Node localNode2;
/* 362 */       if ((localObject2 != null) && (((Node)localObject1).casItem(localObject2, null)))
/*     */       {
/*     */ 
/* 365 */         if (localObject1 != localNode1)
/* 366 */           updateHead(localNode1, (localNode2 = ((Node)localObject1).next) != null ? localNode2 : (Node)localObject1);
/* 367 */         return (E)localObject2;
/*     */       }
/* 369 */       if ((localNode2 = ((Node)localObject1).next) == null) {
/* 370 */         updateHead(localNode1, (Node)localObject1);
/* 371 */         return null;
/*     */       }
/* 373 */       if (localObject1 == localNode2) {
/*     */         break;
/*     */       }
/* 376 */       localObject1 = localNode2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E peek()
/*     */   {
/* 384 */     Node localNode1 = this.head;Object localObject1 = localNode1;
/* 385 */     for (;;) { Object localObject2 = ((Node)localObject1).item;
/* 386 */       Node localNode2; if ((localObject2 != null) || ((localNode2 = ((Node)localObject1).next) == null)) {
/* 387 */         updateHead(localNode1, (Node)localObject1);
/* 388 */         return (E)localObject2;
/*     */       }
/* 390 */       if (localObject1 == localNode2) {
/*     */         break;
/*     */       }
/* 393 */       localObject1 = localNode2;
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
/*     */   Node<E> first()
/*     */   {
/* 409 */     Node localNode1 = this.head;Object localObject = localNode1;
/* 410 */     for (;;) { int i = ((Node)localObject).item != null ? 1 : 0;
/* 411 */       Node localNode2; if ((i != 0) || ((localNode2 = ((Node)localObject).next) == null)) {
/* 412 */         updateHead(localNode1, (Node)localObject);
/* 413 */         return (Node<E>)(i != 0 ? localObject : null);
/*     */       }
/* 415 */       if (localObject == localNode2) {
/*     */         break;
/*     */       }
/* 418 */       localObject = localNode2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 429 */     return first() == null;
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
/*     */   public int size()
/*     */   {
/* 449 */     int i = 0;
/* 450 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode))
/* 451 */       if (localNode.item != null)
/*     */       {
/* 453 */         i++; if (i == Integer.MAX_VALUE) break;
/*     */       }
/* 455 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 467 */     if (paramObject == null) return false;
/* 468 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/* 469 */       Object localObject = localNode.item;
/* 470 */       if ((localObject != null) && (paramObject.equals(localObject)))
/* 471 */         return true;
/*     */     }
/* 473 */     return false;
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
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 488 */     if (paramObject == null) return false;
/* 489 */     Object localObject1 = null;
/* 490 */     for (Node localNode1 = first(); localNode1 != null; localNode1 = succ(localNode1)) {
/* 491 */       Object localObject2 = localNode1.item;
/* 492 */       if ((localObject2 != null) && 
/* 493 */         (paramObject.equals(localObject2)) && 
/* 494 */         (localNode1.casItem(localObject2, null))) {
/* 495 */         Node localNode2 = succ(localNode1);
/* 496 */         if ((localObject1 != null) && (localNode2 != null))
/* 497 */           ((Node)localObject1).casNext(localNode1, localNode2);
/* 498 */         return true;
/*     */       }
/* 500 */       localObject1 = localNode1;
/*     */     }
/* 502 */     return false;
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
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 518 */     if (paramCollection == this)
/*     */     {
/* 520 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 523 */     Object localObject1 = null;Object localObject2 = null;
/* 524 */     for (Object localObject3 = paramCollection.iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = ((Iterator)localObject3).next();
/* 525 */       checkNotNull(localObject4);
/* 526 */       localNode = new Node(localObject4);
/* 527 */       if (localObject1 == null) {
/* 528 */         localObject1 = localObject2 = localNode;
/*     */       } else {
/* 530 */         ((Node)localObject2).lazySetNext(localNode);
/* 531 */         localObject2 = localNode;
/*     */       } }
/*     */     Node localNode;
/* 534 */     if (localObject1 == null) {
/* 535 */       return false;
/*     */     }
/*     */     
/* 538 */     localObject3 = this.tail;Object localObject4 = localObject3;
/* 539 */     for (;;) { localNode = ((Node)localObject4).next;
/* 540 */       if (localNode == null)
/*     */       {
/* 542 */         if (((Node)localObject4).casNext(null, (Node)localObject1))
/*     */         {
/*     */ 
/* 545 */           if (!casTail((Node)localObject3, (Node)localObject2))
/*     */           {
/*     */ 
/* 548 */             localObject3 = this.tail;
/* 549 */             if (((Node)localObject2).next == null)
/* 550 */               casTail((Node)localObject3, (Node)localObject2);
/*     */           }
/* 552 */           return true;
/*     */         }
/*     */         
/*     */       }
/* 556 */       else if (localObject4 == localNode)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 561 */         localObject4 = localObject3 != (localObject3 = this.tail) ? localObject3 : this.head;
/*     */       }
/*     */       else {
/* 564 */         localObject4 = (localObject4 != localObject3) && (localObject3 != (localObject3 = this.tail)) ? localObject3 : localNode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 583 */     ArrayList localArrayList = new ArrayList();
/* 584 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/* 585 */       Object localObject = localNode.item;
/* 586 */       if (localObject != null)
/* 587 */         localArrayList.add(localObject);
/*     */     }
/* 589 */     return localArrayList.toArray();
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
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 630 */     int i = 0;
/*     */     
/* 632 */     for (Node localNode1 = first(); (localNode1 != null) && (i < paramArrayOfT.length); localNode1 = succ(localNode1)) {
/* 633 */       localObject1 = localNode1.item;
/* 634 */       if (localObject1 != null)
/* 635 */         paramArrayOfT[(i++)] = localObject1;
/*     */     }
/* 637 */     if (localNode1 == null) {
/* 638 */       if (i < paramArrayOfT.length)
/* 639 */         paramArrayOfT[i] = null;
/* 640 */       return paramArrayOfT;
/*     */     }
/*     */     
/*     */ 
/* 644 */     Object localObject1 = new ArrayList();
/* 645 */     for (Node localNode2 = first(); localNode2 != null; localNode2 = succ(localNode2)) {
/* 646 */       Object localObject2 = localNode2.item;
/* 647 */       if (localObject2 != null)
/* 648 */         ((ArrayList)localObject1).add(localObject2);
/*     */     }
/* 650 */     return ((ArrayList)localObject1).toArray(paramArrayOfT);
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
/*     */   public Iterator<E> iterator()
/*     */   {
/* 663 */     return new Itr();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private class Itr
/*     */     implements Iterator<E>
/*     */   {
/*     */     private ConcurrentLinkedQueue.Node<E> nextNode;
/*     */     
/*     */ 
/*     */ 
/*     */     private E nextItem;
/*     */     
/*     */ 
/*     */ 
/*     */     private ConcurrentLinkedQueue.Node<E> lastRet;
/*     */     
/*     */ 
/*     */ 
/*     */     Itr()
/*     */     {
/* 686 */       advance();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private E advance()
/*     */     {
/* 694 */       this.lastRet = this.nextNode;
/* 695 */       Object localObject1 = this.nextItem;
/*     */       Object localObject2;
/*     */       ConcurrentLinkedQueue.Node localNode1;
/* 698 */       if (this.nextNode == null) {
/* 699 */         localObject2 = ConcurrentLinkedQueue.this.first();
/* 700 */         localNode1 = null;
/*     */       } else {
/* 702 */         localNode1 = this.nextNode;
/* 703 */         localObject2 = ConcurrentLinkedQueue.this.succ(this.nextNode);
/*     */       }
/*     */       for (;;)
/*     */       {
/* 707 */         if (localObject2 == null) {
/* 708 */           this.nextNode = null;
/* 709 */           this.nextItem = null;
/* 710 */           return (E)localObject1;
/*     */         }
/* 712 */         Object localObject3 = ((ConcurrentLinkedQueue.Node)localObject2).item;
/* 713 */         if (localObject3 != null) {
/* 714 */           this.nextNode = ((ConcurrentLinkedQueue.Node)localObject2);
/* 715 */           this.nextItem = localObject3;
/* 716 */           return (E)localObject1;
/*     */         }
/*     */         
/* 719 */         ConcurrentLinkedQueue.Node localNode2 = ConcurrentLinkedQueue.this.succ((ConcurrentLinkedQueue.Node)localObject2);
/* 720 */         if ((localNode1 != null) && (localNode2 != null))
/* 721 */           localNode1.casNext((ConcurrentLinkedQueue.Node)localObject2, localNode2);
/* 722 */         localObject2 = localNode2;
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean hasNext()
/*     */     {
/* 728 */       return this.nextNode != null;
/*     */     }
/*     */     
/*     */     public E next() {
/* 732 */       if (this.nextNode == null) throw new NoSuchElementException();
/* 733 */       return (E)advance();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 737 */       ConcurrentLinkedQueue.Node localNode = this.lastRet;
/* 738 */       if (localNode == null) { throw new IllegalStateException();
/*     */       }
/* 740 */       localNode.item = null;
/* 741 */       this.lastRet = null;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 757 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 760 */     for (Node localNode = first(); localNode != null; localNode = succ(localNode)) {
/* 761 */       Object localObject = localNode.item;
/* 762 */       if (localObject != null) {
/* 763 */         paramObjectOutputStream.writeObject(localObject);
/*     */       }
/*     */     }
/*     */     
/* 767 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 779 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 782 */     Object localObject1 = null;Object localObject2 = null;
/*     */     Object localObject3;
/* 784 */     while ((localObject3 = paramObjectInputStream.readObject()) != null)
/*     */     {
/* 786 */       Node localNode = new Node(localObject3);
/* 787 */       if (localObject1 == null) {
/* 788 */         localObject1 = localObject2 = localNode;
/*     */       } else {
/* 790 */         ((Node)localObject2).lazySetNext(localNode);
/* 791 */         localObject2 = localNode;
/*     */       }
/*     */     }
/* 794 */     if (localObject1 == null)
/* 795 */       localObject1 = localObject2 = new Node(null);
/* 796 */     this.head = ((Node)localObject1);
/* 797 */     this.tail = ((Node)localObject2);
/*     */   }
/*     */   
/*     */   static final class CLQSpliterator<E> implements Spliterator<E> {
/*     */     static final int MAX_BATCH = 33554432;
/*     */     final ConcurrentLinkedQueue<E> queue;
/*     */     ConcurrentLinkedQueue.Node<E> current;
/*     */     int batch;
/*     */     boolean exhausted;
/*     */     
/*     */     CLQSpliterator(ConcurrentLinkedQueue<E> paramConcurrentLinkedQueue) {
/* 808 */       this.queue = paramConcurrentLinkedQueue;
/*     */     }
/*     */     
/*     */     public Spliterator<E> trySplit()
/*     */     {
/* 813 */       ConcurrentLinkedQueue localConcurrentLinkedQueue = this.queue;
/* 814 */       int i = this.batch;
/* 815 */       int j = i >= 33554432 ? 33554432 : i <= 0 ? 1 : i + 1;
/* 816 */       ConcurrentLinkedQueue.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/* 817 */         ((localNode = localConcurrentLinkedQueue.first()) != null)) && (localNode.next != null))
/*     */       {
/* 819 */         Object[] arrayOfObject = new Object[j];
/* 820 */         int k = 0;
/*     */         do {
/* 822 */           if ((arrayOfObject[k] = localNode.item) != null)
/* 823 */             k++;
/* 824 */           if (localNode == (localNode = localNode.next))
/* 825 */             localNode = localConcurrentLinkedQueue.first();
/* 826 */         } while ((localNode != null) && (k < j));
/* 827 */         if ((this.current = localNode) == null)
/* 828 */           this.exhausted = true;
/* 829 */         if (k > 0) {
/* 830 */           this.batch = k;
/*     */           
/* 832 */           return Spliterators.spliterator(arrayOfObject, 0, k, 4368);
/*     */         }
/*     */       }
/*     */       
/* 836 */       return null;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*     */     {
/* 841 */       if (paramConsumer == null) throw new NullPointerException();
/* 842 */       ConcurrentLinkedQueue localConcurrentLinkedQueue = this.queue;
/* 843 */       ConcurrentLinkedQueue.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/* 844 */         ((localNode = localConcurrentLinkedQueue.first()) != null))) {
/* 845 */         this.exhausted = true;
/*     */         do {
/* 847 */           Object localObject = localNode.item;
/* 848 */           if (localNode == (localNode = localNode.next))
/* 849 */             localNode = localConcurrentLinkedQueue.first();
/* 850 */           if (localObject != null)
/* 851 */             paramConsumer.accept(localObject);
/* 852 */         } while (localNode != null);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super E> paramConsumer)
/*     */     {
/* 858 */       if (paramConsumer == null) throw new NullPointerException();
/* 859 */       ConcurrentLinkedQueue localConcurrentLinkedQueue = this.queue;
/* 860 */       ConcurrentLinkedQueue.Node localNode; if ((!this.exhausted) && (((localNode = this.current) != null) || 
/* 861 */         ((localNode = localConcurrentLinkedQueue.first()) != null))) {
/*     */         Object localObject;
/*     */         do {
/* 864 */           localObject = localNode.item;
/* 865 */           if (localNode == (localNode = localNode.next))
/* 866 */             localNode = localConcurrentLinkedQueue.first();
/* 867 */         } while ((localObject == null) && (localNode != null));
/* 868 */         if ((this.current = localNode) == null)
/* 869 */           this.exhausted = true;
/* 870 */         if (localObject != null) {
/* 871 */           paramConsumer.accept(localObject);
/* 872 */           return true;
/*     */         }
/*     */       }
/* 875 */       return false;
/*     */     }
/*     */     
/* 878 */     public long estimateSize() { return Long.MAX_VALUE; }
/*     */     
/*     */     public int characteristics() {
/* 881 */       return 4368;
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
/*     */   public Spliterator<E> spliterator()
/*     */   {
/* 904 */     return new CLQSpliterator(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void checkNotNull(Object paramObject)
/*     */   {
/* 913 */     if (paramObject == null)
/* 914 */       throw new NullPointerException();
/*     */   }
/*     */   
/*     */   private boolean casTail(Node<E> paramNode1, Node<E> paramNode2) {
/* 918 */     return UNSAFE.compareAndSwapObject(this, tailOffset, paramNode1, paramNode2);
/*     */   }
/*     */   
/*     */   private boolean casHead(Node<E> paramNode1, Node<E> paramNode2) {
/* 922 */     return UNSAFE.compareAndSwapObject(this, headOffset, paramNode1, paramNode2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 932 */       UNSAFE = Unsafe.getUnsafe();
/* 933 */       Class localClass = ConcurrentLinkedQueue.class;
/*     */       
/* 935 */       headOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("head"));
/*     */       
/* 937 */       tailOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("tail"));
/*     */     } catch (Exception localException) {
/* 939 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ConcurrentLinkedQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */