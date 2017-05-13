/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayDeque<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Deque<E>, Cloneable, Serializable
/*     */ {
/*     */   transient Object[] elements;
/*     */   transient int head;
/*     */   transient int tail;
/*     */   private static final int MIN_INITIAL_CAPACITY = 8;
/*     */   private static final long serialVersionUID = 2340985798034038923L;
/*     */   
/*     */   private void allocateElements(int paramInt)
/*     */   {
/* 127 */     int i = 8;
/*     */     
/*     */ 
/* 130 */     if (paramInt >= i) {
/* 131 */       i = paramInt;
/* 132 */       i |= i >>> 1;
/* 133 */       i |= i >>> 2;
/* 134 */       i |= i >>> 4;
/* 135 */       i |= i >>> 8;
/* 136 */       i |= i >>> 16;
/* 137 */       i++;
/*     */       
/* 139 */       if (i < 0)
/* 140 */         i >>>= 1;
/*     */     }
/* 142 */     this.elements = new Object[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doubleCapacity()
/*     */   {
/* 150 */     assert (this.head == this.tail);
/* 151 */     int i = this.head;
/* 152 */     int j = this.elements.length;
/* 153 */     int k = j - i;
/* 154 */     int m = j << 1;
/* 155 */     if (m < 0)
/* 156 */       throw new IllegalStateException("Sorry, deque too big");
/* 157 */     Object[] arrayOfObject = new Object[m];
/* 158 */     System.arraycopy(this.elements, i, arrayOfObject, 0, k);
/* 159 */     System.arraycopy(this.elements, 0, arrayOfObject, k, i);
/* 160 */     this.elements = arrayOfObject;
/* 161 */     this.head = 0;
/* 162 */     this.tail = j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private <T> T[] copyElements(T[] paramArrayOfT)
/*     */   {
/* 173 */     if (this.head < this.tail) {
/* 174 */       System.arraycopy(this.elements, this.head, paramArrayOfT, 0, size());
/* 175 */     } else if (this.head > this.tail) {
/* 176 */       int i = this.elements.length - this.head;
/* 177 */       System.arraycopy(this.elements, this.head, paramArrayOfT, 0, i);
/* 178 */       System.arraycopy(this.elements, 0, paramArrayOfT, i, this.tail);
/*     */     }
/* 180 */     return paramArrayOfT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayDeque()
/*     */   {
/* 188 */     this.elements = new Object[16];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayDeque(int paramInt)
/*     */   {
/* 198 */     allocateElements(paramInt);
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
/*     */   public ArrayDeque(Collection<? extends E> paramCollection)
/*     */   {
/* 212 */     allocateElements(paramCollection.size());
/* 213 */     addAll(paramCollection);
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
/*     */   public void addFirst(E paramE)
/*     */   {
/* 227 */     if (paramE == null)
/* 228 */       throw new NullPointerException();
/* 229 */     this.elements[(this.head = this.head - 1 & this.elements.length - 1)] = paramE;
/* 230 */     if (this.head == this.tail) {
/* 231 */       doubleCapacity();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addLast(E paramE)
/*     */   {
/* 243 */     if (paramE == null)
/* 244 */       throw new NullPointerException();
/* 245 */     this.elements[this.tail] = paramE;
/* 246 */     if ((this.tail = this.tail + 1 & this.elements.length - 1) == this.head) {
/* 247 */       doubleCapacity();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean offerFirst(E paramE)
/*     */   {
/* 258 */     addFirst(paramE);
/* 259 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean offerLast(E paramE)
/*     */   {
/* 270 */     addLast(paramE);
/* 271 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E removeFirst()
/*     */   {
/* 278 */     Object localObject = pollFirst();
/* 279 */     if (localObject == null)
/* 280 */       throw new NoSuchElementException();
/* 281 */     return (E)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E removeLast()
/*     */   {
/* 288 */     Object localObject = pollLast();
/* 289 */     if (localObject == null)
/* 290 */       throw new NoSuchElementException();
/* 291 */     return (E)localObject;
/*     */   }
/*     */   
/*     */   public E pollFirst() {
/* 295 */     int i = this.head;
/*     */     
/* 297 */     Object localObject = this.elements[i];
/*     */     
/* 299 */     if (localObject == null)
/* 300 */       return null;
/* 301 */     this.elements[i] = null;
/* 302 */     this.head = (i + 1 & this.elements.length - 1);
/* 303 */     return (E)localObject;
/*     */   }
/*     */   
/*     */   public E pollLast() {
/* 307 */     int i = this.tail - 1 & this.elements.length - 1;
/*     */     
/* 309 */     Object localObject = this.elements[i];
/* 310 */     if (localObject == null)
/* 311 */       return null;
/* 312 */     this.elements[i] = null;
/* 313 */     this.tail = i;
/* 314 */     return (E)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E getFirst()
/*     */   {
/* 322 */     Object localObject = this.elements[this.head];
/* 323 */     if (localObject == null)
/* 324 */       throw new NoSuchElementException();
/* 325 */     return (E)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E getLast()
/*     */   {
/* 333 */     Object localObject = this.elements[(this.tail - 1 & this.elements.length - 1)];
/* 334 */     if (localObject == null)
/* 335 */       throw new NoSuchElementException();
/* 336 */     return (E)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public E peekFirst()
/*     */   {
/* 342 */     return (E)this.elements[this.head];
/*     */   }
/*     */   
/*     */   public E peekLast()
/*     */   {
/* 347 */     return (E)this.elements[(this.tail - 1 & this.elements.length - 1)];
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
/*     */   public boolean removeFirstOccurrence(Object paramObject)
/*     */   {
/* 363 */     if (paramObject == null)
/* 364 */       return false;
/* 365 */     int i = this.elements.length - 1;
/* 366 */     int j = this.head;
/*     */     Object localObject;
/* 368 */     while ((localObject = this.elements[j]) != null) {
/* 369 */       if (paramObject.equals(localObject)) {
/* 370 */         delete(j);
/* 371 */         return true;
/*     */       }
/* 373 */       j = j + 1 & i;
/*     */     }
/* 375 */     return false;
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
/*     */   public boolean removeLastOccurrence(Object paramObject)
/*     */   {
/* 391 */     if (paramObject == null)
/* 392 */       return false;
/* 393 */     int i = this.elements.length - 1;
/* 394 */     int j = this.tail - 1 & i;
/*     */     Object localObject;
/* 396 */     while ((localObject = this.elements[j]) != null) {
/* 397 */       if (paramObject.equals(localObject)) {
/* 398 */         delete(j);
/* 399 */         return true;
/*     */       }
/* 401 */       j = j - 1 & i;
/*     */     }
/* 403 */     return false;
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
/* 418 */     addLast(paramE);
/* 419 */     return true;
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
/* 432 */     return offerLast(paramE);
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
/*     */   public E remove()
/*     */   {
/* 447 */     return (E)removeFirst();
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
/*     */   public E poll()
/*     */   {
/* 461 */     return (E)pollFirst();
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
/*     */   public E element()
/*     */   {
/* 475 */     return (E)getFirst();
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
/*     */   public E peek()
/*     */   {
/* 488 */     return (E)peekFirst();
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
/*     */   public void push(E paramE)
/*     */   {
/* 503 */     addFirst(paramE);
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
/*     */   public E pop()
/*     */   {
/* 517 */     return (E)removeFirst();
/*     */   }
/*     */   
/*     */   private void checkInvariants() {
/* 521 */     assert (this.elements[this.tail] == null);
/* 522 */     assert (this.head == this.tail ? this.elements[this.head] != null : (this.elements[this.head] != null) && (this.elements[(this.tail - 1 & this.elements.length - 1)] != null));
/*     */     
/*     */ 
/* 525 */     assert (this.elements[(this.head - 1 & this.elements.length - 1)] == null);
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
/*     */   private boolean delete(int paramInt)
/*     */   {
/* 539 */     checkInvariants();
/* 540 */     Object[] arrayOfObject = this.elements;
/* 541 */     int i = arrayOfObject.length - 1;
/* 542 */     int j = this.head;
/* 543 */     int k = this.tail;
/* 544 */     int m = paramInt - j & i;
/* 545 */     int n = k - paramInt & i;
/*     */     
/*     */ 
/* 548 */     if (m >= (k - j & i)) {
/* 549 */       throw new ConcurrentModificationException();
/*     */     }
/*     */     
/* 552 */     if (m < n) {
/* 553 */       if (j <= paramInt) {
/* 554 */         System.arraycopy(arrayOfObject, j, arrayOfObject, j + 1, m);
/*     */       } else {
/* 556 */         System.arraycopy(arrayOfObject, 0, arrayOfObject, 1, paramInt);
/* 557 */         arrayOfObject[0] = arrayOfObject[i];
/* 558 */         System.arraycopy(arrayOfObject, j, arrayOfObject, j + 1, i - j);
/*     */       }
/* 560 */       arrayOfObject[j] = null;
/* 561 */       this.head = (j + 1 & i);
/* 562 */       return false;
/*     */     }
/* 564 */     if (paramInt < k) {
/* 565 */       System.arraycopy(arrayOfObject, paramInt + 1, arrayOfObject, paramInt, n);
/* 566 */       this.tail = (k - 1);
/*     */     } else {
/* 568 */       System.arraycopy(arrayOfObject, paramInt + 1, arrayOfObject, paramInt, i - paramInt);
/* 569 */       arrayOfObject[i] = arrayOfObject[0];
/* 570 */       System.arraycopy(arrayOfObject, 1, arrayOfObject, 0, k);
/* 571 */       this.tail = (k - 1 & i);
/*     */     }
/* 573 */     return true;
/*     */   }
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
/* 585 */     return this.tail - this.head & this.elements.length - 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 594 */     return this.head == this.tail;
/*     */   }
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
/* 606 */     return new DeqIterator(null);
/*     */   }
/*     */   
/*     */   public Iterator<E> descendingIterator() {
/* 610 */     return new DescendingIterator(null);
/*     */   }
/*     */   
/*     */ 
/*     */   private class DeqIterator
/*     */     implements Iterator<E>
/*     */   {
/* 617 */     private int cursor = ArrayDeque.this.head;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 623 */     private int fence = ArrayDeque.this.tail;
/*     */     
/*     */ 
/*     */     private DeqIterator() {}
/*     */     
/*     */ 
/* 629 */     private int lastRet = -1;
/*     */     
/*     */     public boolean hasNext() {
/* 632 */       return this.cursor != this.fence;
/*     */     }
/*     */     
/*     */     public E next() {
/* 636 */       if (this.cursor == this.fence) {
/* 637 */         throw new NoSuchElementException();
/*     */       }
/* 639 */       Object localObject = ArrayDeque.this.elements[this.cursor];
/*     */       
/*     */ 
/* 642 */       if ((ArrayDeque.this.tail != this.fence) || (localObject == null))
/* 643 */         throw new ConcurrentModificationException();
/* 644 */       this.lastRet = this.cursor;
/* 645 */       this.cursor = (this.cursor + 1 & ArrayDeque.this.elements.length - 1);
/* 646 */       return (E)localObject;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 650 */       if (this.lastRet < 0)
/* 651 */         throw new IllegalStateException();
/* 652 */       if (ArrayDeque.this.delete(this.lastRet)) {
/* 653 */         this.cursor = (this.cursor - 1 & ArrayDeque.this.elements.length - 1);
/* 654 */         this.fence = ArrayDeque.this.tail;
/*     */       }
/* 656 */       this.lastRet = -1;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> paramConsumer) {
/* 660 */       Objects.requireNonNull(paramConsumer);
/* 661 */       Object[] arrayOfObject = ArrayDeque.this.elements;
/* 662 */       int i = arrayOfObject.length - 1;int j = this.fence;int k = this.cursor;
/* 663 */       this.cursor = j;
/* 664 */       while (k != j) {
/* 665 */         Object localObject = arrayOfObject[k];
/* 666 */         k = k + 1 & i;
/* 667 */         if (localObject == null)
/* 668 */           throw new ConcurrentModificationException();
/* 669 */         paramConsumer.accept(localObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class DescendingIterator
/*     */     implements Iterator<E>
/*     */   {
/*     */     private DescendingIterator() {}
/*     */     
/* 680 */     private int cursor = ArrayDeque.this.tail;
/* 681 */     private int fence = ArrayDeque.this.head;
/* 682 */     private int lastRet = -1;
/*     */     
/*     */     public boolean hasNext() {
/* 685 */       return this.cursor != this.fence;
/*     */     }
/*     */     
/*     */     public E next() {
/* 689 */       if (this.cursor == this.fence)
/* 690 */         throw new NoSuchElementException();
/* 691 */       this.cursor = (this.cursor - 1 & ArrayDeque.this.elements.length - 1);
/*     */       
/* 693 */       Object localObject = ArrayDeque.this.elements[this.cursor];
/* 694 */       if ((ArrayDeque.this.head != this.fence) || (localObject == null))
/* 695 */         throw new ConcurrentModificationException();
/* 696 */       this.lastRet = this.cursor;
/* 697 */       return (E)localObject;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 701 */       if (this.lastRet < 0)
/* 702 */         throw new IllegalStateException();
/* 703 */       if (!ArrayDeque.this.delete(this.lastRet)) {
/* 704 */         this.cursor = (this.cursor + 1 & ArrayDeque.this.elements.length - 1);
/* 705 */         this.fence = ArrayDeque.this.head;
/*     */       }
/* 707 */       this.lastRet = -1;
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
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 720 */     if (paramObject == null)
/* 721 */       return false;
/* 722 */     int i = this.elements.length - 1;
/* 723 */     int j = this.head;
/*     */     Object localObject;
/* 725 */     while ((localObject = this.elements[j]) != null) {
/* 726 */       if (paramObject.equals(localObject))
/* 727 */         return true;
/* 728 */       j = j + 1 & i;
/*     */     }
/* 730 */     return false;
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
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 747 */     return removeFirstOccurrence(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 755 */     int i = this.head;
/* 756 */     int j = this.tail;
/* 757 */     if (i != j) {
/* 758 */       this.head = (this.tail = 0);
/* 759 */       int k = i;
/* 760 */       int m = this.elements.length - 1;
/*     */       do {
/* 762 */         this.elements[k] = null;
/* 763 */         k = k + 1 & m;
/* 764 */       } while (k != j);
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
/* 782 */     return copyElements(new Object[size()]);
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
/* 823 */     int i = size();
/* 824 */     if (paramArrayOfT.length < i)
/* 825 */       paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT
/* 826 */         .getClass().getComponentType(), i);
/* 827 */     copyElements(paramArrayOfT);
/* 828 */     if (paramArrayOfT.length > i)
/* 829 */       paramArrayOfT[i] = null;
/* 830 */     return paramArrayOfT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayDeque<E> clone()
/*     */   {
/*     */     try
/*     */     {
/* 843 */       ArrayDeque localArrayDeque = (ArrayDeque)super.clone();
/* 844 */       localArrayDeque.elements = Arrays.copyOf(this.elements, this.elements.length);
/* 845 */       return localArrayDeque;
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 847 */       throw new AssertionError();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 862 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 865 */     paramObjectOutputStream.writeInt(size());
/*     */     
/*     */ 
/* 868 */     int i = this.elements.length - 1;
/* 869 */     for (int j = this.head; j != this.tail; j = j + 1 & i) {
/* 870 */       paramObjectOutputStream.writeObject(this.elements[j]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 878 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 881 */     int i = paramObjectInputStream.readInt();
/* 882 */     allocateElements(i);
/* 883 */     this.head = 0;
/* 884 */     this.tail = i;
/*     */     
/*     */ 
/* 887 */     for (int j = 0; j < i; j++) {
/* 888 */       this.elements[j] = paramObjectInputStream.readObject();
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
/*     */   public Spliterator<E> spliterator()
/*     */   {
/* 905 */     return new DeqSpliterator(this, -1, -1);
/*     */   }
/*     */   
/*     */   static final class DeqSpliterator<E> implements Spliterator<E>
/*     */   {
/*     */     private final ArrayDeque<E> deq;
/*     */     private int fence;
/*     */     private int index;
/*     */     
/*     */     DeqSpliterator(ArrayDeque<E> paramArrayDeque, int paramInt1, int paramInt2) {
/* 915 */       this.deq = paramArrayDeque;
/* 916 */       this.index = paramInt1;
/* 917 */       this.fence = paramInt2;
/*     */     }
/*     */     
/*     */     private int getFence() {
/*     */       int i;
/* 922 */       if ((i = this.fence) < 0) {
/* 923 */         i = this.fence = this.deq.tail;
/* 924 */         this.index = this.deq.head;
/*     */       }
/* 926 */       return i;
/*     */     }
/*     */     
/*     */     public DeqSpliterator<E> trySplit() {
/* 930 */       int i = getFence();int j = this.index;int k = this.deq.elements.length;
/* 931 */       if ((j != i) && ((j + 1 & k - 1) != i)) {
/* 932 */         if (j > i)
/* 933 */           i += k;
/* 934 */         int m = j + i >>> 1 & k - 1;
/* 935 */         return new DeqSpliterator(this.deq, j, this.index = m);
/*     */       }
/* 937 */       return null;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(Consumer<? super E> paramConsumer) {
/* 941 */       if (paramConsumer == null)
/* 942 */         throw new NullPointerException();
/* 943 */       Object[] arrayOfObject = this.deq.elements;
/* 944 */       int i = arrayOfObject.length - 1;int j = getFence();int k = this.index;
/* 945 */       this.index = j;
/* 946 */       while (k != j) {
/* 947 */         Object localObject = arrayOfObject[k];
/* 948 */         k = k + 1 & i;
/* 949 */         if (localObject == null)
/* 950 */           throw new ConcurrentModificationException();
/* 951 */         paramConsumer.accept(localObject);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super E> paramConsumer) {
/* 956 */       if (paramConsumer == null)
/* 957 */         throw new NullPointerException();
/* 958 */       Object[] arrayOfObject = this.deq.elements;
/* 959 */       int i = arrayOfObject.length - 1;int j = getFence();int k = this.index;
/* 960 */       if (k != this.fence) {
/* 961 */         Object localObject = arrayOfObject[k];
/* 962 */         this.index = (k + 1 & i);
/* 963 */         if (localObject == null)
/* 964 */           throw new ConcurrentModificationException();
/* 965 */         paramConsumer.accept(localObject);
/* 966 */         return true;
/*     */       }
/* 968 */       return false;
/*     */     }
/*     */     
/*     */     public long estimateSize() {
/* 972 */       int i = getFence() - this.index;
/* 973 */       if (i < 0)
/* 974 */         i += this.deq.elements.length;
/* 975 */       return i;
/*     */     }
/*     */     
/*     */     public int characteristics()
/*     */     {
/* 980 */       return 16720;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ArrayDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */