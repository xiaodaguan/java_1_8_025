/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PriorityQueue<E>
/*     */   extends AbstractQueue<E>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7720805057305804111L;
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 11;
/*     */   transient Object[] queue;
/* 102 */   private int size = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Comparator<? super E> comparator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */   transient int modCount = 0;
/*     */   
/*     */ 
/*     */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*     */   
/*     */ 
/*     */   public PriorityQueue()
/*     */   {
/* 122 */     this(11, null);
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
/*     */   public PriorityQueue(int paramInt)
/*     */   {
/* 135 */     this(paramInt, null);
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
/*     */   public PriorityQueue(Comparator<? super E> paramComparator)
/*     */   {
/* 148 */     this(11, paramComparator);
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
/*     */   public PriorityQueue(int paramInt, Comparator<? super E> paramComparator)
/*     */   {
/* 166 */     if (paramInt < 1)
/* 167 */       throw new IllegalArgumentException();
/* 168 */     this.queue = new Object[paramInt];
/* 169 */     this.comparator = paramComparator;
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
/*     */   public PriorityQueue(Collection<? extends E> paramCollection)
/*     */   {
/*     */     Object localObject;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 190 */     if ((paramCollection instanceof SortedSet)) {
/* 191 */       localObject = (SortedSet)paramCollection;
/* 192 */       this.comparator = ((SortedSet)localObject).comparator();
/* 193 */       initElementsFromCollection((Collection)localObject);
/*     */     }
/* 195 */     else if ((paramCollection instanceof PriorityQueue)) {
/* 196 */       localObject = (PriorityQueue)paramCollection;
/* 197 */       this.comparator = ((PriorityQueue)localObject).comparator();
/* 198 */       initFromPriorityQueue((PriorityQueue)localObject);
/*     */     }
/*     */     else {
/* 201 */       this.comparator = null;
/* 202 */       initFromCollection(paramCollection);
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
/*     */   public PriorityQueue(PriorityQueue<? extends E> paramPriorityQueue)
/*     */   {
/* 222 */     this.comparator = paramPriorityQueue.comparator();
/* 223 */     initFromPriorityQueue(paramPriorityQueue);
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
/*     */   public PriorityQueue(SortedSet<? extends E> paramSortedSet)
/*     */   {
/* 241 */     this.comparator = paramSortedSet.comparator();
/* 242 */     initElementsFromCollection(paramSortedSet);
/*     */   }
/*     */   
/*     */   private void initFromPriorityQueue(PriorityQueue<? extends E> paramPriorityQueue) {
/* 246 */     if (paramPriorityQueue.getClass() == PriorityQueue.class) {
/* 247 */       this.queue = paramPriorityQueue.toArray();
/* 248 */       this.size = paramPriorityQueue.size();
/*     */     } else {
/* 250 */       initFromCollection(paramPriorityQueue);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initElementsFromCollection(Collection<? extends E> paramCollection) {
/* 255 */     Object[] arrayOfObject = paramCollection.toArray();
/*     */     
/* 257 */     if (arrayOfObject.getClass() != Object[].class)
/* 258 */       arrayOfObject = Arrays.copyOf(arrayOfObject, arrayOfObject.length, Object[].class);
/* 259 */     int i = arrayOfObject.length;
/* 260 */     if ((i == 1) || (this.comparator != null))
/* 261 */       for (int j = 0; j < i; j++)
/* 262 */         if (arrayOfObject[j] == null)
/* 263 */           throw new NullPointerException();
/* 264 */     this.queue = arrayOfObject;
/* 265 */     this.size = arrayOfObject.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initFromCollection(Collection<? extends E> paramCollection)
/*     */   {
/* 274 */     initElementsFromCollection(paramCollection);
/* 275 */     heapify();
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
/*     */   private void grow(int paramInt)
/*     */   {
/* 292 */     int i = this.queue.length;
/*     */     
/* 294 */     int j = i + (i < 64 ? i + 2 : i >> 1);
/*     */     
/*     */ 
/*     */ 
/* 298 */     if (j - 2147483639 > 0)
/* 299 */       j = hugeCapacity(paramInt);
/* 300 */     this.queue = Arrays.copyOf(this.queue, j);
/*     */   }
/*     */   
/*     */   private static int hugeCapacity(int paramInt) {
/* 304 */     if (paramInt < 0)
/* 305 */       throw new OutOfMemoryError();
/* 306 */     return paramInt > 2147483639 ? Integer.MAX_VALUE : 2147483639;
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 321 */     return offer(paramE);
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
/*     */   public boolean offer(E paramE)
/*     */   {
/* 334 */     if (paramE == null)
/* 335 */       throw new NullPointerException();
/* 336 */     this.modCount += 1;
/* 337 */     int i = this.size;
/* 338 */     if (i >= this.queue.length)
/* 339 */       grow(i + 1);
/* 340 */     this.size = (i + 1);
/* 341 */     if (i == 0) {
/* 342 */       this.queue[0] = paramE;
/*     */     } else
/* 344 */       siftUp(i, paramE);
/* 345 */     return true;
/*     */   }
/*     */   
/*     */   public E peek()
/*     */   {
/* 350 */     return this.size == 0 ? null : this.queue[0];
/*     */   }
/*     */   
/*     */   private int indexOf(Object paramObject) {
/* 354 */     if (paramObject != null) {
/* 355 */       for (int i = 0; i < this.size; i++)
/* 356 */         if (paramObject.equals(this.queue[i]))
/* 357 */           return i;
/*     */     }
/* 359 */     return -1;
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
/* 374 */     int i = indexOf(paramObject);
/* 375 */     if (i == -1) {
/* 376 */       return false;
/*     */     }
/* 378 */     removeAt(i);
/* 379 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean removeEq(Object paramObject)
/*     */   {
/* 391 */     for (int i = 0; i < this.size; i++) {
/* 392 */       if (paramObject == this.queue[i]) {
/* 393 */         removeAt(i);
/* 394 */         return true;
/*     */       }
/*     */     }
/* 397 */     return false;
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
/* 409 */     return indexOf(paramObject) != -1;
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
/* 426 */     return Arrays.copyOf(this.queue, this.size);
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
/* 467 */     int i = this.size;
/* 468 */     if (paramArrayOfT.length < i)
/*     */     {
/* 470 */       return (Object[])Arrays.copyOf(this.queue, i, paramArrayOfT.getClass()); }
/* 471 */     System.arraycopy(this.queue, 0, paramArrayOfT, 0, i);
/* 472 */     if (paramArrayOfT.length > i)
/* 473 */       paramArrayOfT[i] = null;
/* 474 */     return paramArrayOfT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 484 */     return new Itr(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final class Itr
/*     */     implements Iterator<E>
/*     */   {
/* 492 */     private int cursor = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 499 */     private int lastRet = -1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 512 */     private ArrayDeque<E> forgetMeNot = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 518 */     private E lastRetElt = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 525 */     private int expectedModCount = PriorityQueue.this.modCount;
/*     */     
/*     */     private Itr() {}
/*     */     
/* 529 */     public boolean hasNext() { return (this.cursor < PriorityQueue.this.size) || ((this.forgetMeNot != null) && (!this.forgetMeNot.isEmpty())); }
/*     */     
/*     */ 
/*     */     public E next()
/*     */     {
/* 534 */       if (this.expectedModCount != PriorityQueue.this.modCount)
/* 535 */         throw new ConcurrentModificationException();
/* 536 */       if (this.cursor < PriorityQueue.this.size)
/* 537 */         return (E)PriorityQueue.this.queue[(this.lastRet = this.cursor++)];
/* 538 */       if (this.forgetMeNot != null) {
/* 539 */         this.lastRet = -1;
/* 540 */         this.lastRetElt = this.forgetMeNot.poll();
/* 541 */         if (this.lastRetElt != null)
/* 542 */           return (E)this.lastRetElt;
/*     */       }
/* 544 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 548 */       if (this.expectedModCount != PriorityQueue.this.modCount)
/* 549 */         throw new ConcurrentModificationException();
/* 550 */       if (this.lastRet != -1) {
/* 551 */         Object localObject = PriorityQueue.this.removeAt(this.lastRet);
/* 552 */         this.lastRet = -1;
/* 553 */         if (localObject == null) {
/* 554 */           this.cursor -= 1;
/*     */         } else {
/* 556 */           if (this.forgetMeNot == null)
/* 557 */             this.forgetMeNot = new ArrayDeque();
/* 558 */           this.forgetMeNot.add(localObject);
/*     */         }
/* 560 */       } else if (this.lastRetElt != null) {
/* 561 */         PriorityQueue.this.removeEq(this.lastRetElt);
/* 562 */         this.lastRetElt = null;
/*     */       } else {
/* 564 */         throw new IllegalStateException();
/*     */       }
/* 566 */       this.expectedModCount = PriorityQueue.this.modCount;
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 571 */     return this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 579 */     this.modCount += 1;
/* 580 */     for (int i = 0; i < this.size; i++)
/* 581 */       this.queue[i] = null;
/* 582 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public E poll()
/*     */   {
/* 587 */     if (this.size == 0)
/* 588 */       return null;
/* 589 */     int i = --this.size;
/* 590 */     this.modCount += 1;
/* 591 */     Object localObject1 = this.queue[0];
/* 592 */     Object localObject2 = this.queue[i];
/* 593 */     this.queue[i] = null;
/* 594 */     if (i != 0)
/* 595 */       siftDown(0, localObject2);
/* 596 */     return (E)localObject1;
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
/*     */   private E removeAt(int paramInt)
/*     */   {
/* 614 */     this.modCount += 1;
/* 615 */     int i = --this.size;
/* 616 */     if (i == paramInt) {
/* 617 */       this.queue[paramInt] = null;
/*     */     } else {
/* 619 */       Object localObject = this.queue[i];
/* 620 */       this.queue[i] = null;
/* 621 */       siftDown(paramInt, localObject);
/* 622 */       if (this.queue[paramInt] == localObject) {
/* 623 */         siftUp(paramInt, localObject);
/* 624 */         if (this.queue[paramInt] != localObject)
/* 625 */           return (E)localObject;
/*     */       }
/*     */     }
/* 628 */     return null;
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
/*     */   private void siftUp(int paramInt, E paramE)
/*     */   {
/* 644 */     if (this.comparator != null) {
/* 645 */       siftUpUsingComparator(paramInt, paramE);
/*     */     } else {
/* 647 */       siftUpComparable(paramInt, paramE);
/*     */     }
/*     */   }
/*     */   
/*     */   private void siftUpComparable(int paramInt, E paramE) {
/* 652 */     Comparable localComparable = (Comparable)paramE;
/* 653 */     while (paramInt > 0) {
/* 654 */       int i = paramInt - 1 >>> 1;
/* 655 */       Object localObject = this.queue[i];
/* 656 */       if (localComparable.compareTo(localObject) >= 0)
/*     */         break;
/* 658 */       this.queue[paramInt] = localObject;
/* 659 */       paramInt = i;
/*     */     }
/* 661 */     this.queue[paramInt] = localComparable;
/*     */   }
/*     */   
/*     */   private void siftUpUsingComparator(int paramInt, E paramE)
/*     */   {
/* 666 */     while (paramInt > 0) {
/* 667 */       int i = paramInt - 1 >>> 1;
/* 668 */       Object localObject = this.queue[i];
/* 669 */       if (this.comparator.compare(paramE, localObject) >= 0)
/*     */         break;
/* 671 */       this.queue[paramInt] = localObject;
/* 672 */       paramInt = i;
/*     */     }
/* 674 */     this.queue[paramInt] = paramE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void siftDown(int paramInt, E paramE)
/*     */   {
/* 686 */     if (this.comparator != null) {
/* 687 */       siftDownUsingComparator(paramInt, paramE);
/*     */     } else {
/* 689 */       siftDownComparable(paramInt, paramE);
/*     */     }
/*     */   }
/*     */   
/*     */   private void siftDownComparable(int paramInt, E paramE) {
/* 694 */     Comparable localComparable = (Comparable)paramE;
/* 695 */     int i = this.size >>> 1;
/* 696 */     while (paramInt < i) {
/* 697 */       int j = (paramInt << 1) + 1;
/* 698 */       Object localObject = this.queue[j];
/* 699 */       int k = j + 1;
/* 700 */       if ((k < this.size) && 
/* 701 */         (((Comparable)localObject).compareTo(this.queue[k]) > 0))
/* 702 */         localObject = this.queue[(j = k)];
/* 703 */       if (localComparable.compareTo(localObject) <= 0)
/*     */         break;
/* 705 */       this.queue[paramInt] = localObject;
/* 706 */       paramInt = j;
/*     */     }
/* 708 */     this.queue[paramInt] = localComparable;
/*     */   }
/*     */   
/*     */   private void siftDownUsingComparator(int paramInt, E paramE)
/*     */   {
/* 713 */     int i = this.size >>> 1;
/* 714 */     while (paramInt < i) {
/* 715 */       int j = (paramInt << 1) + 1;
/* 716 */       Object localObject = this.queue[j];
/* 717 */       int k = j + 1;
/* 718 */       if ((k < this.size) && 
/* 719 */         (this.comparator.compare(localObject, this.queue[k]) > 0))
/* 720 */         localObject = this.queue[(j = k)];
/* 721 */       if (this.comparator.compare(paramE, localObject) <= 0)
/*     */         break;
/* 723 */       this.queue[paramInt] = localObject;
/* 724 */       paramInt = j;
/*     */     }
/* 726 */     this.queue[paramInt] = paramE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void heapify()
/*     */   {
/* 735 */     for (int i = (this.size >>> 1) - 1; i >= 0; i--) {
/* 736 */       siftDown(i, this.queue[i]);
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
/*     */   public Comparator<? super E> comparator()
/*     */   {
/* 749 */     return this.comparator;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 763 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 766 */     paramObjectOutputStream.writeInt(Math.max(2, this.size + 1));
/*     */     
/*     */ 
/* 769 */     for (int i = 0; i < this.size; i++) {
/* 770 */       paramObjectOutputStream.writeObject(this.queue[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 782 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 785 */     paramObjectInputStream.readInt();
/*     */     
/* 787 */     this.queue = new Object[this.size];
/*     */     
/*     */ 
/* 790 */     for (int i = 0; i < this.size; i++) {
/* 791 */       this.queue[i] = paramObjectInputStream.readObject();
/*     */     }
/*     */     
/*     */ 
/* 795 */     heapify();
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
/*     */   public final Spliterator<E> spliterator()
/*     */   {
/* 812 */     return new PriorityQueueSpliterator(this, 0, -1, 0);
/*     */   }
/*     */   
/*     */ 
/*     */   static final class PriorityQueueSpliterator<E>
/*     */     implements Spliterator<E>
/*     */   {
/*     */     private final PriorityQueue<E> pq;
/*     */     
/*     */     private int index;
/*     */     
/*     */     private int fence;
/*     */     private int expectedModCount;
/*     */     
/*     */     PriorityQueueSpliterator(PriorityQueue<E> paramPriorityQueue, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 828 */       this.pq = paramPriorityQueue;
/* 829 */       this.index = paramInt1;
/* 830 */       this.fence = paramInt2;
/* 831 */       this.expectedModCount = paramInt3;
/*     */     }
/*     */     
/*     */     private int getFence() {
/*     */       int i;
/* 836 */       if ((i = this.fence) < 0) {
/* 837 */         this.expectedModCount = this.pq.modCount;
/* 838 */         i = this.fence = this.pq.size;
/*     */       }
/* 840 */       return i;
/*     */     }
/*     */     
/*     */     public PriorityQueueSpliterator<E> trySplit() {
/* 844 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 845 */       return j >= k ? null : new PriorityQueueSpliterator(this.pq, j, this.index = k, this.expectedModCount);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*     */     {
/* 854 */       if (paramConsumer == null)
/* 855 */         throw new NullPointerException();
/* 856 */       PriorityQueue localPriorityQueue; Object[] arrayOfObject; if (((localPriorityQueue = this.pq) != null) && ((arrayOfObject = localPriorityQueue.queue) != null)) { int j;
/* 857 */         int k; if ((j = this.fence) < 0) {
/* 858 */           k = localPriorityQueue.modCount;
/* 859 */           j = localPriorityQueue.size;
/*     */         }
/*     */         else {
/* 862 */           k = this.expectedModCount; }
/* 863 */         int i; if (((i = this.index) >= 0) && ((this.index = j) <= arrayOfObject.length)) {
/* 864 */           for (;; i++)
/* 865 */             if (i < j) { Object localObject;
/* 866 */               if ((localObject = arrayOfObject[i]) == null)
/*     */                 break;
/* 868 */               paramConsumer.accept(localObject);
/*     */             } else {
/* 870 */               if (localPriorityQueue.modCount != k) {
/*     */                 break;
/*     */               }
/* 873 */               return;
/*     */             }
/*     */         }
/*     */       }
/* 877 */       throw new ConcurrentModificationException();
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super E> paramConsumer) {
/* 881 */       if (paramConsumer == null)
/* 882 */         throw new NullPointerException();
/* 883 */       int i = getFence();int j = this.index;
/* 884 */       if ((j >= 0) && (j < i)) {
/* 885 */         this.index = (j + 1);
/* 886 */         Object localObject = this.pq.queue[j];
/* 887 */         if (localObject == null)
/* 888 */           throw new ConcurrentModificationException();
/* 889 */         paramConsumer.accept(localObject);
/* 890 */         if (this.pq.modCount != this.expectedModCount)
/* 891 */           throw new ConcurrentModificationException();
/* 892 */         return true;
/*     */       }
/* 894 */       return false;
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 898 */       return getFence() - this.index;
/*     */     }
/*     */     
/*     */     public int characteristics() {
/* 902 */       return 16704;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/PriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */