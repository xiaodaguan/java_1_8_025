/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.Serializable;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Vector<E>
/*      */   extends AbstractList<E>
/*      */   implements List<E>, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   protected Object[] elementData;
/*      */   protected int elementCount;
/*      */   protected int capacityIncrement;
/*      */   private static final long serialVersionUID = -2767605614048989439L;
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   
/*      */   public Vector(int paramInt1, int paramInt2)
/*      */   {
/*  132 */     if (paramInt1 < 0) {
/*  133 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt1);
/*      */     }
/*  135 */     this.elementData = new Object[paramInt1];
/*  136 */     this.capacityIncrement = paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Vector(int paramInt)
/*      */   {
/*  148 */     this(paramInt, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Vector()
/*      */   {
/*  157 */     this(10);
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
/*      */   public Vector(Collection<? extends E> paramCollection)
/*      */   {
/*  171 */     this.elementData = paramCollection.toArray();
/*  172 */     this.elementCount = this.elementData.length;
/*      */     
/*  174 */     if (this.elementData.getClass() != Object[].class) {
/*  175 */       this.elementData = Arrays.copyOf(this.elementData, this.elementCount, Object[].class);
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
/*      */   public synchronized void copyInto(Object[] paramArrayOfObject)
/*      */   {
/*  192 */     System.arraycopy(this.elementData, 0, paramArrayOfObject, 0, this.elementCount);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void trimToSize()
/*      */   {
/*  204 */     this.modCount += 1;
/*  205 */     int i = this.elementData.length;
/*  206 */     if (this.elementCount < i) {
/*  207 */       this.elementData = Arrays.copyOf(this.elementData, this.elementCount);
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
/*      */   public synchronized void ensureCapacity(int paramInt)
/*      */   {
/*  229 */     if (paramInt > 0) {
/*  230 */       this.modCount += 1;
/*  231 */       ensureCapacityHelper(paramInt);
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
/*      */   private void ensureCapacityHelper(int paramInt)
/*      */   {
/*  245 */     if (paramInt - this.elementData.length > 0) {
/*  246 */       grow(paramInt);
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
/*      */   private void grow(int paramInt)
/*      */   {
/*  259 */     int i = this.elementData.length;
/*  260 */     int j = i + (this.capacityIncrement > 0 ? this.capacityIncrement : i);
/*      */     
/*  262 */     if (j - paramInt < 0)
/*  263 */       j = paramInt;
/*  264 */     if (j - 2147483639 > 0)
/*  265 */       j = hugeCapacity(paramInt);
/*  266 */     this.elementData = Arrays.copyOf(this.elementData, j);
/*      */   }
/*      */   
/*      */   private static int hugeCapacity(int paramInt) {
/*  270 */     if (paramInt < 0)
/*  271 */       throw new OutOfMemoryError();
/*  272 */     return paramInt > 2147483639 ? Integer.MAX_VALUE : 2147483639;
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
/*      */   public synchronized void setSize(int paramInt)
/*      */   {
/*  287 */     this.modCount += 1;
/*  288 */     if (paramInt > this.elementCount) {
/*  289 */       ensureCapacityHelper(paramInt);
/*      */     } else {
/*  291 */       for (int i = paramInt; i < this.elementCount; i++) {
/*  292 */         this.elementData[i] = null;
/*      */       }
/*      */     }
/*  295 */     this.elementCount = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int capacity()
/*      */   {
/*  306 */     return this.elementData.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int size()
/*      */   {
/*  315 */     return this.elementCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isEmpty()
/*      */   {
/*  326 */     return this.elementCount == 0;
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
/*      */   public Enumeration<E> elements()
/*      */   {
/*  339 */     new Enumeration() {
/*  340 */       int count = 0;
/*      */       
/*      */       public boolean hasMoreElements() {
/*  343 */         return this.count < Vector.this.elementCount;
/*      */       }
/*      */       
/*      */       public E nextElement() {
/*  347 */         synchronized (Vector.this) {
/*  348 */           if (this.count < Vector.this.elementCount) {
/*  349 */             return (E)Vector.this.elementData(this.count++);
/*      */           }
/*      */         }
/*  352 */         throw new NoSuchElementException("Vector Enumeration");
/*      */       }
/*      */     };
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
/*      */   public boolean contains(Object paramObject)
/*      */   {
/*  367 */     return indexOf(paramObject, 0) >= 0;
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
/*      */   public int indexOf(Object paramObject)
/*      */   {
/*  382 */     return indexOf(paramObject, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int indexOf(Object paramObject, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  402 */     if (paramObject == null) {
/*  403 */       for (i = paramInt; i < this.elementCount; i++)
/*  404 */         if (this.elementData[i] == null)
/*  405 */           return i;
/*      */     } else {
/*  407 */       for (i = paramInt; i < this.elementCount; i++)
/*  408 */         if (paramObject.equals(this.elementData[i]))
/*  409 */           return i;
/*      */     }
/*  411 */     return -1;
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
/*      */   public synchronized int lastIndexOf(Object paramObject)
/*      */   {
/*  426 */     return lastIndexOf(paramObject, this.elementCount - 1);
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
/*      */   public synchronized int lastIndexOf(Object paramObject, int paramInt)
/*      */   {
/*  446 */     if (paramInt >= this.elementCount)
/*  447 */       throw new IndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     int i;
/*  449 */     if (paramObject == null) {
/*  450 */       for (i = paramInt; i >= 0; i--)
/*  451 */         if (this.elementData[i] == null)
/*  452 */           return i;
/*      */     } else {
/*  454 */       for (i = paramInt; i >= 0; i--)
/*  455 */         if (paramObject.equals(this.elementData[i]))
/*  456 */           return i;
/*      */     }
/*  458 */     return -1;
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
/*      */   public synchronized E elementAt(int paramInt)
/*      */   {
/*  473 */     if (paramInt >= this.elementCount) {
/*  474 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*      */     
/*  477 */     return (E)elementData(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized E firstElement()
/*      */   {
/*  488 */     if (this.elementCount == 0) {
/*  489 */       throw new NoSuchElementException();
/*      */     }
/*  491 */     return (E)elementData(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized E lastElement()
/*      */   {
/*  502 */     if (this.elementCount == 0) {
/*  503 */       throw new NoSuchElementException();
/*      */     }
/*  505 */     return (E)elementData(this.elementCount - 1);
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
/*      */   public synchronized void setElementAt(E paramE, int paramInt)
/*      */   {
/*  529 */     if (paramInt >= this.elementCount) {
/*  530 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*      */     
/*  533 */     this.elementData[paramInt] = paramE;
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
/*      */   public synchronized void removeElementAt(int paramInt)
/*      */   {
/*  556 */     this.modCount += 1;
/*  557 */     if (paramInt >= this.elementCount) {
/*  558 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.elementCount);
/*      */     }
/*      */     
/*  561 */     if (paramInt < 0) {
/*  562 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  564 */     int i = this.elementCount - paramInt - 1;
/*  565 */     if (i > 0) {
/*  566 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  568 */     this.elementCount -= 1;
/*  569 */     this.elementData[this.elementCount] = null;
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
/*      */   public synchronized void insertElementAt(E paramE, int paramInt)
/*      */   {
/*  596 */     this.modCount += 1;
/*  597 */     if (paramInt > this.elementCount) {
/*  598 */       throw new ArrayIndexOutOfBoundsException(paramInt + " > " + this.elementCount);
/*      */     }
/*      */     
/*  601 */     ensureCapacityHelper(this.elementCount + 1);
/*  602 */     System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.elementCount - paramInt);
/*  603 */     this.elementData[paramInt] = paramE;
/*  604 */     this.elementCount += 1;
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
/*      */   public synchronized void addElement(E paramE)
/*      */   {
/*  619 */     this.modCount += 1;
/*  620 */     ensureCapacityHelper(this.elementCount + 1);
/*  621 */     this.elementData[(this.elementCount++)] = paramE;
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
/*      */   public synchronized boolean removeElement(Object paramObject)
/*      */   {
/*  640 */     this.modCount += 1;
/*  641 */     int i = indexOf(paramObject);
/*  642 */     if (i >= 0) {
/*  643 */       removeElementAt(i);
/*  644 */       return true;
/*      */     }
/*  646 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void removeAllElements()
/*      */   {
/*  656 */     this.modCount += 1;
/*      */     
/*  658 */     for (int i = 0; i < this.elementCount; i++) {
/*  659 */       this.elementData[i] = null;
/*      */     }
/*  661 */     this.elementCount = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  674 */       Vector localVector = (Vector)super.clone();
/*  675 */       localVector.elementData = Arrays.copyOf(this.elementData, this.elementCount);
/*  676 */       localVector.modCount = 0;
/*  677 */       return localVector;
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  680 */       throw new InternalError(localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Object[] toArray()
/*      */   {
/*  691 */     return Arrays.copyOf(this.elementData, this.elementCount);
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
/*      */   public synchronized <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/*  719 */     if (paramArrayOfT.length < this.elementCount) {
/*  720 */       return (Object[])Arrays.copyOf(this.elementData, this.elementCount, paramArrayOfT.getClass());
/*      */     }
/*  722 */     System.arraycopy(this.elementData, 0, paramArrayOfT, 0, this.elementCount);
/*      */     
/*  724 */     if (paramArrayOfT.length > this.elementCount) {
/*  725 */       paramArrayOfT[this.elementCount] = null;
/*      */     }
/*  727 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   E elementData(int paramInt)
/*      */   {
/*  734 */     return (E)this.elementData[paramInt];
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
/*      */   public synchronized E get(int paramInt)
/*      */   {
/*  747 */     if (paramInt >= this.elementCount) {
/*  748 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  750 */     return (E)elementData(paramInt);
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
/*      */   public synchronized E set(int paramInt, E paramE)
/*      */   {
/*  765 */     if (paramInt >= this.elementCount) {
/*  766 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  768 */     Object localObject = elementData(paramInt);
/*  769 */     this.elementData[paramInt] = paramE;
/*  770 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean add(E paramE)
/*      */   {
/*  781 */     this.modCount += 1;
/*  782 */     ensureCapacityHelper(this.elementCount + 1);
/*  783 */     this.elementData[(this.elementCount++)] = paramE;
/*  784 */     return true;
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
/*  799 */     return removeElement(paramObject);
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
/*      */   public void add(int paramInt, E paramE)
/*      */   {
/*  814 */     insertElementAt(paramE, paramInt);
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
/*      */   public synchronized E remove(int paramInt)
/*      */   {
/*  829 */     this.modCount += 1;
/*  830 */     if (paramInt >= this.elementCount)
/*  831 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*  832 */     Object localObject = elementData(paramInt);
/*      */     
/*  834 */     int i = this.elementCount - paramInt - 1;
/*  835 */     if (i > 0) {
/*  836 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  838 */     this.elementData[(--this.elementCount)] = null;
/*      */     
/*  840 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  850 */     removeAllElements();
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
/*      */   public synchronized boolean containsAll(Collection<?> paramCollection)
/*      */   {
/*  866 */     return super.containsAll(paramCollection);
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
/*      */   public synchronized boolean addAll(Collection<? extends E> paramCollection)
/*      */   {
/*  883 */     this.modCount += 1;
/*  884 */     Object[] arrayOfObject = paramCollection.toArray();
/*  885 */     int i = arrayOfObject.length;
/*  886 */     ensureCapacityHelper(this.elementCount + i);
/*  887 */     System.arraycopy(arrayOfObject, 0, this.elementData, this.elementCount, i);
/*  888 */     this.elementCount += i;
/*  889 */     return i != 0;
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
/*      */   public synchronized boolean removeAll(Collection<?> paramCollection)
/*      */   {
/*  910 */     return super.removeAll(paramCollection);
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
/*      */   public synchronized boolean retainAll(Collection<?> paramCollection)
/*      */   {
/*  933 */     return super.retainAll(paramCollection);
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
/*      */   public synchronized boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*      */   {
/*  954 */     this.modCount += 1;
/*  955 */     if ((paramInt < 0) || (paramInt > this.elementCount)) {
/*  956 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/*  958 */     Object[] arrayOfObject = paramCollection.toArray();
/*  959 */     int i = arrayOfObject.length;
/*  960 */     ensureCapacityHelper(this.elementCount + i);
/*      */     
/*  962 */     int j = this.elementCount - paramInt;
/*  963 */     if (j > 0) {
/*  964 */       System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + i, j);
/*      */     }
/*      */     
/*  967 */     System.arraycopy(arrayOfObject, 0, this.elementData, paramInt, i);
/*  968 */     this.elementCount += i;
/*  969 */     return i != 0;
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
/*      */   public synchronized boolean equals(Object paramObject)
/*      */   {
/*  985 */     return super.equals(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized int hashCode()
/*      */   {
/*  992 */     return super.hashCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized String toString()
/*      */   {
/* 1000 */     return super.toString();
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
/*      */   public synchronized List<E> subList(int paramInt1, int paramInt2)
/*      */   {
/* 1038 */     return Collections.synchronizedList(super.subList(paramInt1, paramInt2), this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected synchronized void removeRange(int paramInt1, int paramInt2)
/*      */   {
/* 1050 */     this.modCount += 1;
/* 1051 */     int i = this.elementCount - paramInt2;
/* 1052 */     System.arraycopy(this.elementData, paramInt2, this.elementData, paramInt1, i);
/*      */     
/*      */ 
/*      */ 
/* 1056 */     int j = this.elementCount - (paramInt2 - paramInt1);
/* 1057 */     while (this.elementCount != j) {
/* 1058 */       this.elementData[(--this.elementCount)] = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1069 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/*      */     Object[] arrayOfObject;
/* 1071 */     synchronized (this) {
/* 1072 */       localPutField.put("capacityIncrement", this.capacityIncrement);
/* 1073 */       localPutField.put("elementCount", this.elementCount);
/* 1074 */       arrayOfObject = (Object[])this.elementData.clone();
/*      */     }
/* 1076 */     localPutField.put("elementData", arrayOfObject);
/* 1077 */     paramObjectOutputStream.writeFields();
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
/*      */   public synchronized ListIterator<E> listIterator(int paramInt)
/*      */   {
/* 1093 */     if ((paramInt < 0) || (paramInt > this.elementCount))
/* 1094 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
/* 1095 */     return new ListItr(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized ListIterator<E> listIterator()
/*      */   {
/* 1107 */     return new ListItr(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Iterator<E> iterator()
/*      */   {
/* 1118 */     return new Itr(null);
/*      */   }
/*      */   
/*      */ 
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     int cursor;
/* 1126 */     int lastRet = -1;
/* 1127 */     int expectedModCount = Vector.this.modCount;
/*      */     
/*      */     private Itr() {}
/*      */     
/*      */     public boolean hasNext() {
/* 1132 */       return this.cursor != Vector.this.elementCount;
/*      */     }
/*      */     
/*      */     public E next() {
/* 1136 */       synchronized (Vector.this) {
/* 1137 */         checkForComodification();
/* 1138 */         int i = this.cursor;
/* 1139 */         if (i >= Vector.this.elementCount)
/* 1140 */           throw new NoSuchElementException();
/* 1141 */         this.cursor = (i + 1);
/* 1142 */         return (E)Vector.this.elementData(this.lastRet = i);
/*      */       }
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1147 */       if (this.lastRet == -1)
/* 1148 */         throw new IllegalStateException();
/* 1149 */       synchronized (Vector.this) {
/* 1150 */         checkForComodification();
/* 1151 */         Vector.this.remove(this.lastRet);
/* 1152 */         this.expectedModCount = Vector.this.modCount;
/*      */       }
/* 1154 */       this.cursor = this.lastRet;
/* 1155 */       this.lastRet = -1;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1160 */       Objects.requireNonNull(paramConsumer);
/* 1161 */       synchronized (Vector.this) {
/* 1162 */         int i = Vector.this.elementCount;
/* 1163 */         int j = this.cursor;
/* 1164 */         if (j >= i) {
/* 1165 */           return;
/*      */         }
/*      */         
/* 1168 */         Object[] arrayOfObject = (Object[])Vector.this.elementData;
/* 1169 */         if (j >= arrayOfObject.length) {
/* 1170 */           throw new ConcurrentModificationException();
/*      */         }
/* 1172 */         while ((j != i) && (Vector.this.modCount == this.expectedModCount)) {
/* 1173 */           paramConsumer.accept(arrayOfObject[(j++)]);
/*      */         }
/*      */         
/* 1176 */         this.cursor = j;
/* 1177 */         this.lastRet = (j - 1);
/* 1178 */         checkForComodification();
/*      */       }
/*      */     }
/*      */     
/*      */     final void checkForComodification() {
/* 1183 */       if (Vector.this.modCount != this.expectedModCount) {
/* 1184 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   final class ListItr extends Vector<E>.Itr implements ListIterator<E>
/*      */   {
/*      */     ListItr(int paramInt)
/*      */     {
/* 1193 */       super(null);
/* 1194 */       this.cursor = paramInt;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1198 */       return this.cursor != 0;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1202 */       return this.cursor;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1206 */       return this.cursor - 1;
/*      */     }
/*      */     
/*      */     public E previous() {
/* 1210 */       synchronized (Vector.this) {
/* 1211 */         checkForComodification();
/* 1212 */         int i = this.cursor - 1;
/* 1213 */         if (i < 0)
/* 1214 */           throw new NoSuchElementException();
/* 1215 */         this.cursor = i;
/* 1216 */         return (E)Vector.this.elementData(this.lastRet = i);
/*      */       }
/*      */     }
/*      */     
/*      */     public void set(E paramE) {
/* 1221 */       if (this.lastRet == -1)
/* 1222 */         throw new IllegalStateException();
/* 1223 */       synchronized (Vector.this) {
/* 1224 */         checkForComodification();
/* 1225 */         Vector.this.set(this.lastRet, paramE);
/*      */       }
/*      */     }
/*      */     
/*      */     public void add(E paramE) {
/* 1230 */       int i = this.cursor;
/* 1231 */       synchronized (Vector.this) {
/* 1232 */         checkForComodification();
/* 1233 */         Vector.this.add(i, paramE);
/* 1234 */         this.expectedModCount = Vector.this.modCount;
/*      */       }
/* 1236 */       this.cursor = (i + 1);
/* 1237 */       this.lastRet = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized void forEach(Consumer<? super E> paramConsumer)
/*      */   {
/* 1243 */     Objects.requireNonNull(paramConsumer);
/* 1244 */     int i = this.modCount;
/*      */     
/* 1246 */     Object[] arrayOfObject = (Object[])this.elementData;
/* 1247 */     int j = this.elementCount;
/* 1248 */     for (int k = 0; (this.modCount == i) && (k < j); k++) {
/* 1249 */       paramConsumer.accept(arrayOfObject[k]);
/*      */     }
/* 1251 */     if (this.modCount != i) {
/* 1252 */       throw new ConcurrentModificationException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized boolean removeIf(Predicate<? super E> paramPredicate)
/*      */   {
/* 1259 */     Objects.requireNonNull(paramPredicate);
/*      */     
/*      */ 
/*      */ 
/* 1263 */     int i = 0;
/* 1264 */     int j = this.elementCount;
/* 1265 */     BitSet localBitSet = new BitSet(j);
/* 1266 */     int k = this.modCount;
/* 1267 */     for (int m = 0; (this.modCount == k) && (m < j); m++)
/*      */     {
/* 1269 */       Object localObject = this.elementData[m];
/* 1270 */       if (paramPredicate.test(localObject)) {
/* 1271 */         localBitSet.set(m);
/* 1272 */         i++;
/*      */       }
/*      */     }
/* 1275 */     if (this.modCount != k) {
/* 1276 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */ 
/* 1280 */     m = i > 0 ? 1 : 0;
/* 1281 */     if (m != 0) {
/* 1282 */       int n = j - i;
/* 1283 */       int i1 = 0; for (int i2 = 0; (i1 < j) && (i2 < n); i2++) {
/* 1284 */         i1 = localBitSet.nextClearBit(i1);
/* 1285 */         this.elementData[i2] = this.elementData[i1];i1++;
/*      */       }
/*      */       
/* 1287 */       for (i1 = n; i1 < j; i1++) {
/* 1288 */         this.elementData[i1] = null;
/*      */       }
/* 1290 */       this.elementCount = n;
/* 1291 */       if (this.modCount != k) {
/* 1292 */         throw new ConcurrentModificationException();
/*      */       }
/* 1294 */       this.modCount += 1;
/*      */     }
/*      */     
/* 1297 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized void replaceAll(UnaryOperator<E> paramUnaryOperator)
/*      */   {
/* 1303 */     Objects.requireNonNull(paramUnaryOperator);
/* 1304 */     int i = this.modCount;
/* 1305 */     int j = this.elementCount;
/* 1306 */     for (int k = 0; (this.modCount == i) && (k < j); k++) {
/* 1307 */       this.elementData[k] = paramUnaryOperator.apply(this.elementData[k]);
/*      */     }
/* 1309 */     if (this.modCount != i) {
/* 1310 */       throw new ConcurrentModificationException();
/*      */     }
/* 1312 */     this.modCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized void sort(Comparator<? super E> paramComparator)
/*      */   {
/* 1318 */     int i = this.modCount;
/* 1319 */     Arrays.sort((Object[])this.elementData, 0, this.elementCount, paramComparator);
/* 1320 */     if (this.modCount != i) {
/* 1321 */       throw new ConcurrentModificationException();
/*      */     }
/* 1323 */     this.modCount += 1;
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1341 */     return new VectorSpliterator(this, null, 0, -1, 0);
/*      */   }
/*      */   
/*      */   static final class VectorSpliterator<E>
/*      */     implements Spliterator<E>
/*      */   {
/*      */     private final Vector<E> list;
/*      */     private Object[] array;
/*      */     private int index;
/*      */     private int fence;
/*      */     private int expectedModCount;
/*      */     
/*      */     VectorSpliterator(Vector<E> paramVector, Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1355 */       this.list = paramVector;
/* 1356 */       this.array = paramArrayOfObject;
/* 1357 */       this.index = paramInt1;
/* 1358 */       this.fence = paramInt2;
/* 1359 */       this.expectedModCount = paramInt3;
/*      */     }
/*      */     
/*      */     private int getFence() {
/*      */       int i;
/* 1364 */       if ((i = this.fence) < 0) {
/* 1365 */         synchronized (this.list) {
/* 1366 */           this.array = this.list.elementData;
/* 1367 */           this.expectedModCount = this.list.modCount;
/* 1368 */           i = this.fence = this.list.elementCount;
/*      */         }
/*      */       }
/* 1371 */       return i;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/* 1375 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1376 */       return j >= k ? null : new VectorSpliterator(this.list, this.array, j, this.index = k, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer)
/*      */     {
/* 1384 */       if (paramConsumer == null)
/* 1385 */         throw new NullPointerException();
/* 1386 */       int i; if (getFence() > (i = this.index)) {
/* 1387 */         this.index = (i + 1);
/* 1388 */         paramConsumer.accept(this.array[i]);
/* 1389 */         if (this.list.modCount != this.expectedModCount)
/* 1390 */           throw new ConcurrentModificationException();
/* 1391 */         return true;
/*      */       }
/* 1393 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1400 */       if (paramConsumer == null)
/* 1401 */         throw new NullPointerException();
/* 1402 */       Vector localVector; if ((localVector = this.list) != null) { int j;
/* 1403 */         Object[] arrayOfObject; if ((j = this.fence) < 0) {
/* 1404 */           synchronized (localVector) {
/* 1405 */             this.expectedModCount = localVector.modCount;
/* 1406 */             arrayOfObject = this.array = localVector.elementData;
/* 1407 */             j = this.fence = localVector.elementCount;
/*      */           }
/*      */           
/*      */         } else
/* 1411 */           arrayOfObject = this.array;
/* 1412 */         int i; if ((arrayOfObject != null) && ((i = this.index) >= 0) && ((this.index = j) <= arrayOfObject.length)) {
/* 1413 */           while (i < j)
/* 1414 */             paramConsumer.accept(arrayOfObject[(i++)]);
/* 1415 */           if (localVector.modCount == this.expectedModCount)
/* 1416 */             return;
/*      */         }
/*      */       }
/* 1419 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1423 */       return getFence() - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1427 */       return 16464;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Vector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */