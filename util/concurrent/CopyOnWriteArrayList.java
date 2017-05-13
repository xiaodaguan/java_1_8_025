/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
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
/*      */ public class CopyOnWriteArrayList<E>
/*      */   implements List<E>, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8673264195747942595L;
/*   96 */   final transient ReentrantLock lock = new ReentrantLock();
/*      */   
/*      */   private volatile transient Object[] array;
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   private static final long lockOffset;
/*      */   
/*      */   final Object[] getArray()
/*      */   {
/*  106 */     return this.array;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void setArray(Object[] paramArrayOfObject)
/*      */   {
/*  113 */     this.array = paramArrayOfObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public CopyOnWriteArrayList()
/*      */   {
/*  120 */     setArray(new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CopyOnWriteArrayList(Collection<? extends E> paramCollection)
/*      */   {
/*      */     Object[] arrayOfObject;
/*      */     
/*      */ 
/*      */ 
/*  133 */     if (paramCollection.getClass() == CopyOnWriteArrayList.class) {
/*  134 */       arrayOfObject = ((CopyOnWriteArrayList)paramCollection).getArray();
/*      */     } else {
/*  136 */       arrayOfObject = paramCollection.toArray();
/*      */       
/*  138 */       if (arrayOfObject.getClass() != Object[].class)
/*  139 */         arrayOfObject = Arrays.copyOf(arrayOfObject, arrayOfObject.length, Object[].class);
/*      */     }
/*  141 */     setArray(arrayOfObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CopyOnWriteArrayList(E[] paramArrayOfE)
/*      */   {
/*  152 */     setArray(Arrays.copyOf(paramArrayOfE, paramArrayOfE.length, Object[].class));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  161 */     return getArray().length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  170 */     return size() == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static boolean eq(Object paramObject1, Object paramObject2)
/*      */   {
/*  177 */     return paramObject1 == null ? false : paramObject2 == null ? true : paramObject1.equals(paramObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int indexOf(Object paramObject, Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  191 */     if (paramObject == null) {
/*  192 */       for (i = paramInt1; i < paramInt2; i++)
/*  193 */         if (paramArrayOfObject[i] == null)
/*  194 */           return i;
/*      */     } else {
/*  196 */       for (i = paramInt1; i < paramInt2; i++)
/*  197 */         if (paramObject.equals(paramArrayOfObject[i]))
/*  198 */           return i;
/*      */     }
/*  200 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int lastIndexOf(Object paramObject, Object[] paramArrayOfObject, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*  211 */     if (paramObject == null) {
/*  212 */       for (i = paramInt; i >= 0; i--)
/*  213 */         if (paramArrayOfObject[i] == null)
/*  214 */           return i;
/*      */     } else {
/*  216 */       for (i = paramInt; i >= 0; i--)
/*  217 */         if (paramObject.equals(paramArrayOfObject[i]))
/*  218 */           return i;
/*      */     }
/*  220 */     return -1;
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
/*  233 */     Object[] arrayOfObject = getArray();
/*  234 */     return indexOf(paramObject, arrayOfObject, 0, arrayOfObject.length) >= 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int indexOf(Object paramObject)
/*      */   {
/*  241 */     Object[] arrayOfObject = getArray();
/*  242 */     return indexOf(paramObject, arrayOfObject, 0, arrayOfObject.length);
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
/*      */   public int indexOf(E paramE, int paramInt)
/*      */   {
/*  261 */     Object[] arrayOfObject = getArray();
/*  262 */     return indexOf(paramE, arrayOfObject, paramInt, arrayOfObject.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int lastIndexOf(Object paramObject)
/*      */   {
/*  269 */     Object[] arrayOfObject = getArray();
/*  270 */     return lastIndexOf(paramObject, arrayOfObject, arrayOfObject.length - 1);
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
/*      */   public int lastIndexOf(E paramE, int paramInt)
/*      */   {
/*  290 */     Object[] arrayOfObject = getArray();
/*  291 */     return lastIndexOf(paramE, arrayOfObject, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  304 */       CopyOnWriteArrayList localCopyOnWriteArrayList = (CopyOnWriteArrayList)super.clone();
/*  305 */       localCopyOnWriteArrayList.resetLock();
/*  306 */       return localCopyOnWriteArrayList;
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  309 */       throw new InternalError();
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
/*      */   public Object[] toArray()
/*      */   {
/*  327 */     Object[] arrayOfObject = getArray();
/*  328 */     return Arrays.copyOf(arrayOfObject, arrayOfObject.length);
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/*  371 */     Object[] arrayOfObject = getArray();
/*  372 */     int i = arrayOfObject.length;
/*  373 */     if (paramArrayOfT.length < i) {
/*  374 */       return (Object[])Arrays.copyOf(arrayOfObject, i, paramArrayOfT.getClass());
/*      */     }
/*  376 */     System.arraycopy(arrayOfObject, 0, paramArrayOfT, 0, i);
/*  377 */     if (paramArrayOfT.length > i)
/*  378 */       paramArrayOfT[i] = null;
/*  379 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private E get(Object[] paramArrayOfObject, int paramInt)
/*      */   {
/*  387 */     return (E)paramArrayOfObject[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E get(int paramInt)
/*      */   {
/*  396 */     return (E)get(getArray(), paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E set(int paramInt, E paramE)
/*      */   {
/*  406 */     ReentrantLock localReentrantLock = this.lock;
/*  407 */     localReentrantLock.lock();
/*      */     try {
/*  409 */       Object[] arrayOfObject1 = getArray();
/*  410 */       Object localObject1 = get(arrayOfObject1, paramInt);
/*      */       
/*  412 */       if (localObject1 != paramE) {
/*  413 */         int i = arrayOfObject1.length;
/*  414 */         Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i);
/*  415 */         arrayOfObject2[paramInt] = paramE;
/*  416 */         setArray(arrayOfObject2);
/*      */       }
/*      */       else {
/*  419 */         setArray(arrayOfObject1);
/*      */       }
/*  421 */       return (E)localObject1;
/*      */     } finally {
/*  423 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean add(E paramE)
/*      */   {
/*  434 */     ReentrantLock localReentrantLock = this.lock;
/*  435 */     localReentrantLock.lock();
/*      */     try {
/*  437 */       Object[] arrayOfObject1 = getArray();
/*  438 */       int i = arrayOfObject1.length;
/*  439 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + 1);
/*  440 */       arrayOfObject2[i] = paramE;
/*  441 */       setArray(arrayOfObject2);
/*  442 */       return true;
/*      */     } finally {
/*  444 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void add(int paramInt, E paramE)
/*      */   {
/*  456 */     ReentrantLock localReentrantLock = this.lock;
/*  457 */     localReentrantLock.lock();
/*      */     try {
/*  459 */       Object[] arrayOfObject1 = getArray();
/*  460 */       int i = arrayOfObject1.length;
/*  461 */       if ((paramInt > i) || (paramInt < 0)) {
/*  462 */         throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + i);
/*      */       }
/*      */       
/*  465 */       int j = i - paramInt;
/*  466 */       Object[] arrayOfObject2; if (j == 0) {
/*  467 */         arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + 1);
/*      */       } else {
/*  469 */         arrayOfObject2 = new Object[i + 1];
/*  470 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, paramInt);
/*  471 */         System.arraycopy(arrayOfObject1, paramInt, arrayOfObject2, paramInt + 1, j);
/*      */       }
/*      */       
/*  474 */       arrayOfObject2[paramInt] = paramE;
/*  475 */       setArray(arrayOfObject2);
/*      */     } finally {
/*  477 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E remove(int paramInt)
/*      */   {
/*  489 */     ReentrantLock localReentrantLock = this.lock;
/*  490 */     localReentrantLock.lock();
/*      */     try {
/*  492 */       Object[] arrayOfObject = getArray();
/*  493 */       int i = arrayOfObject.length;
/*  494 */       Object localObject1 = get(arrayOfObject, paramInt);
/*  495 */       int j = i - paramInt - 1;
/*  496 */       Object localObject2; if (j == 0) {
/*  497 */         setArray(Arrays.copyOf(arrayOfObject, i - 1));
/*      */       } else {
/*  499 */         localObject2 = new Object[i - 1];
/*  500 */         System.arraycopy(arrayOfObject, 0, localObject2, 0, paramInt);
/*  501 */         System.arraycopy(arrayOfObject, paramInt + 1, localObject2, paramInt, j);
/*      */         
/*  503 */         setArray((Object[])localObject2);
/*      */       }
/*  505 */       return (E)localObject1;
/*      */     } finally {
/*  507 */       localReentrantLock.unlock();
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*  525 */     Object[] arrayOfObject = getArray();
/*  526 */     int i = indexOf(paramObject, arrayOfObject, 0, arrayOfObject.length);
/*  527 */     return i < 0 ? false : remove(paramObject, arrayOfObject, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean remove(Object paramObject, Object[] paramArrayOfObject, int paramInt)
/*      */   {
/*  535 */     ReentrantLock localReentrantLock = this.lock;
/*  536 */     localReentrantLock.lock();
/*      */     try {
/*  538 */       Object[] arrayOfObject1 = getArray();
/*  539 */       int i = arrayOfObject1.length;
/*  540 */       boolean bool; if (paramArrayOfObject != arrayOfObject1) {
/*  541 */         int j = Math.min(paramInt, i);
/*  542 */         for (int k = 0; k < j; k++) {
/*  543 */           if ((arrayOfObject1[k] != paramArrayOfObject[k]) && (eq(paramObject, arrayOfObject1[k]))) {
/*  544 */             paramInt = k;
/*      */             break label135;
/*      */           }
/*      */         }
/*  548 */         if (paramInt >= i)
/*  549 */           return 0;
/*  550 */         if (arrayOfObject1[paramInt] != paramObject)
/*      */         {
/*  552 */           paramInt = indexOf(paramObject, arrayOfObject1, paramInt, i);
/*  553 */           if (paramInt < 0)
/*  554 */             return false; } }
/*      */       label135:
/*  556 */       Object[] arrayOfObject2 = new Object[i - 1];
/*  557 */       System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, paramInt);
/*  558 */       System.arraycopy(arrayOfObject1, paramInt + 1, arrayOfObject2, paramInt, i - paramInt - 1);
/*      */       
/*      */ 
/*  561 */       setArray(arrayOfObject2);
/*  562 */       return true;
/*      */     } finally {
/*  564 */       localReentrantLock.unlock();
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
/*      */   void removeRange(int paramInt1, int paramInt2)
/*      */   {
/*  581 */     ReentrantLock localReentrantLock = this.lock;
/*  582 */     localReentrantLock.lock();
/*      */     try {
/*  584 */       Object[] arrayOfObject1 = getArray();
/*  585 */       int i = arrayOfObject1.length;
/*      */       
/*  587 */       if ((paramInt1 < 0) || (paramInt2 > i) || (paramInt2 < paramInt1))
/*  588 */         throw new IndexOutOfBoundsException();
/*  589 */       int j = i - (paramInt2 - paramInt1);
/*  590 */       int k = i - paramInt2;
/*  591 */       if (k == 0) {
/*  592 */         setArray(Arrays.copyOf(arrayOfObject1, j));
/*      */       } else {
/*  594 */         Object[] arrayOfObject2 = new Object[j];
/*  595 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, paramInt1);
/*  596 */         System.arraycopy(arrayOfObject1, paramInt2, arrayOfObject2, paramInt1, k);
/*      */         
/*  598 */         setArray(arrayOfObject2);
/*      */       }
/*      */     } finally {
/*  601 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean addIfAbsent(E paramE)
/*      */   {
/*  612 */     Object[] arrayOfObject = getArray();
/*      */     
/*  614 */     return indexOf(paramE, arrayOfObject, 0, arrayOfObject.length) >= 0 ? false : addIfAbsent(paramE, arrayOfObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean addIfAbsent(E paramE, Object[] paramArrayOfObject)
/*      */   {
/*  622 */     ReentrantLock localReentrantLock = this.lock;
/*  623 */     localReentrantLock.lock();
/*      */     try {
/*  625 */       Object[] arrayOfObject1 = getArray();
/*  626 */       int i = arrayOfObject1.length;
/*  627 */       if (paramArrayOfObject != arrayOfObject1)
/*      */       {
/*  629 */         int j = Math.min(paramArrayOfObject.length, i);
/*  630 */         for (int k = 0; k < j; k++)
/*  631 */           if ((arrayOfObject1[k] != paramArrayOfObject[k]) && (eq(paramE, arrayOfObject1[k])))
/*  632 */             return false;
/*  633 */         if (indexOf(paramE, arrayOfObject1, j, i) >= 0)
/*  634 */           return 0;
/*      */       }
/*  636 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i + 1);
/*  637 */       arrayOfObject2[i] = paramE;
/*  638 */       setArray(arrayOfObject2);
/*  639 */       return true;
/*      */     } finally {
/*  641 */       localReentrantLock.unlock();
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
/*      */   public boolean containsAll(Collection<?> paramCollection)
/*      */   {
/*  656 */     Object[] arrayOfObject = getArray();
/*  657 */     int i = arrayOfObject.length;
/*  658 */     for (Object localObject : paramCollection) {
/*  659 */       if (indexOf(localObject, arrayOfObject, 0, i) < 0)
/*  660 */         return false;
/*      */     }
/*  662 */     return true;
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
/*      */   public boolean removeAll(Collection<?> paramCollection)
/*      */   {
/*  682 */     if (paramCollection == null) throw new NullPointerException();
/*  683 */     ReentrantLock localReentrantLock = this.lock;
/*  684 */     localReentrantLock.lock();
/*      */     try {
/*  686 */       Object[] arrayOfObject1 = getArray();
/*  687 */       int i = arrayOfObject1.length;
/*  688 */       int j; if (i != 0)
/*      */       {
/*  690 */         j = 0;
/*  691 */         Object[] arrayOfObject2 = new Object[i];
/*  692 */         for (int k = 0; k < i; k++) {
/*  693 */           Object localObject1 = arrayOfObject1[k];
/*  694 */           if (!paramCollection.contains(localObject1))
/*  695 */             arrayOfObject2[(j++)] = localObject1;
/*      */         }
/*  697 */         if (j != i) {
/*  698 */           setArray(Arrays.copyOf(arrayOfObject2, j));
/*  699 */           return 1;
/*      */         }
/*      */       }
/*  702 */       return 0;
/*      */     } finally {
/*  704 */       localReentrantLock.unlock();
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
/*      */   public boolean retainAll(Collection<?> paramCollection)
/*      */   {
/*  725 */     if (paramCollection == null) throw new NullPointerException();
/*  726 */     ReentrantLock localReentrantLock = this.lock;
/*  727 */     localReentrantLock.lock();
/*      */     try {
/*  729 */       Object[] arrayOfObject1 = getArray();
/*  730 */       int i = arrayOfObject1.length;
/*  731 */       int j; if (i != 0)
/*      */       {
/*  733 */         j = 0;
/*  734 */         Object[] arrayOfObject2 = new Object[i];
/*  735 */         for (int k = 0; k < i; k++) {
/*  736 */           Object localObject1 = arrayOfObject1[k];
/*  737 */           if (paramCollection.contains(localObject1))
/*  738 */             arrayOfObject2[(j++)] = localObject1;
/*      */         }
/*  740 */         if (j != i) {
/*  741 */           setArray(Arrays.copyOf(arrayOfObject2, j));
/*  742 */           return 1;
/*      */         }
/*      */       }
/*  745 */       return 0;
/*      */     } finally {
/*  747 */       localReentrantLock.unlock();
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
/*      */   public int addAllAbsent(Collection<? extends E> paramCollection)
/*      */   {
/*  763 */     Object[] arrayOfObject1 = paramCollection.toArray();
/*  764 */     if (arrayOfObject1.length == 0)
/*  765 */       return 0;
/*  766 */     ReentrantLock localReentrantLock = this.lock;
/*  767 */     localReentrantLock.lock();
/*      */     try {
/*  769 */       Object[] arrayOfObject2 = getArray();
/*  770 */       int i = arrayOfObject2.length;
/*  771 */       Object[] arrayOfObject3 = 0;
/*      */       
/*  773 */       for (int j = 0; j < arrayOfObject1.length; j++) {
/*  774 */         Object localObject1 = arrayOfObject1[j];
/*  775 */         if ((indexOf(localObject1, arrayOfObject2, 0, i) < 0) && 
/*  776 */           (indexOf(localObject1, arrayOfObject1, 0, arrayOfObject3) < 0))
/*  777 */           arrayOfObject1[(arrayOfObject3++)] = localObject1; }
/*      */       Object[] arrayOfObject4;
/*  779 */       if (arrayOfObject3 > 0) {
/*  780 */         arrayOfObject4 = Arrays.copyOf(arrayOfObject2, i + arrayOfObject3);
/*  781 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject4, i, arrayOfObject3);
/*  782 */         setArray(arrayOfObject4);
/*      */       }
/*  784 */       return arrayOfObject3;
/*      */     } finally {
/*  786 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void clear()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 5	java/util/concurrent/CopyOnWriteArrayList:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 26	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: iconst_0
/*      */     //   11: anewarray 6	java/lang/Object
/*      */     //   14: invokevirtual 7	java/util/concurrent/CopyOnWriteArrayList:setArray	([Ljava/lang/Object;)V
/*      */     //   17: aload_1
/*      */     //   18: invokevirtual 27	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   21: goto +10 -> 31
/*      */     //   24: astore_2
/*      */     //   25: aload_1
/*      */     //   26: invokevirtual 27	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   29: aload_2
/*      */     //   30: athrow
/*      */     //   31: return
/*      */     // Line number table:
/*      */     //   Java source line #795	-> byte code offset #0
/*      */     //   Java source line #796	-> byte code offset #5
/*      */     //   Java source line #798	-> byte code offset #9
/*      */     //   Java source line #800	-> byte code offset #17
/*      */     //   Java source line #801	-> byte code offset #21
/*      */     //   Java source line #800	-> byte code offset #24
/*      */     //   Java source line #802	-> byte code offset #31
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	32	0	this	CopyOnWriteArrayList
/*      */     //   4	22	1	localReentrantLock	ReentrantLock
/*      */     //   24	6	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	17	24	finally
/*      */   }
/*      */   
/*      */   public boolean addAll(Collection<? extends E> paramCollection)
/*      */   {
/*  816 */     Object[] arrayOfObject1 = paramCollection.getClass() == CopyOnWriteArrayList.class ? ((CopyOnWriteArrayList)paramCollection).getArray() : paramCollection.toArray();
/*  817 */     if (arrayOfObject1.length == 0)
/*  818 */       return false;
/*  819 */     ReentrantLock localReentrantLock = this.lock;
/*  820 */     localReentrantLock.lock();
/*      */     try {
/*  822 */       Object[] arrayOfObject2 = getArray();
/*  823 */       int i = arrayOfObject2.length;
/*  824 */       if ((i == 0) && (arrayOfObject1.getClass() == Object[].class)) {
/*  825 */         setArray(arrayOfObject1);
/*      */       } else {
/*  827 */         Object[] arrayOfObject3 = Arrays.copyOf(arrayOfObject2, i + arrayOfObject1.length);
/*  828 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject3, i, arrayOfObject1.length);
/*  829 */         setArray(arrayOfObject3);
/*      */       }
/*  831 */       return true;
/*      */     } finally {
/*  833 */       localReentrantLock.unlock();
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
/*      */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*      */   {
/*  854 */     Object[] arrayOfObject1 = paramCollection.toArray();
/*  855 */     ReentrantLock localReentrantLock = this.lock;
/*  856 */     localReentrantLock.lock();
/*      */     try {
/*  858 */       Object[] arrayOfObject2 = getArray();
/*  859 */       int i = arrayOfObject2.length;
/*  860 */       if ((paramInt > i) || (paramInt < 0)) {
/*  861 */         throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + i);
/*      */       }
/*  863 */       if (arrayOfObject1.length == 0)
/*  864 */         return false;
/*  865 */       int j = i - paramInt;
/*      */       Object[] arrayOfObject3;
/*  867 */       if (j == 0) {
/*  868 */         arrayOfObject3 = Arrays.copyOf(arrayOfObject2, i + arrayOfObject1.length);
/*      */       } else {
/*  870 */         arrayOfObject3 = new Object[i + arrayOfObject1.length];
/*  871 */         System.arraycopy(arrayOfObject2, 0, arrayOfObject3, 0, paramInt);
/*  872 */         System.arraycopy(arrayOfObject2, paramInt, arrayOfObject3, paramInt + arrayOfObject1.length, j);
/*      */       }
/*      */       
/*      */ 
/*  876 */       System.arraycopy(arrayOfObject1, 0, arrayOfObject3, paramInt, arrayOfObject1.length);
/*  877 */       setArray(arrayOfObject3);
/*  878 */       return true;
/*      */     } finally {
/*  880 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public void forEach(Consumer<? super E> paramConsumer) {
/*  885 */     if (paramConsumer == null) throw new NullPointerException();
/*  886 */     Object[] arrayOfObject = getArray();
/*  887 */     int i = arrayOfObject.length;
/*  888 */     for (int j = 0; j < i; j++) {
/*  889 */       Object localObject = arrayOfObject[j];
/*  890 */       paramConsumer.accept(localObject);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean removeIf(Predicate<? super E> paramPredicate) {
/*  895 */     if (paramPredicate == null) throw new NullPointerException();
/*  896 */     ReentrantLock localReentrantLock = this.lock;
/*  897 */     localReentrantLock.lock();
/*      */     try {
/*  899 */       Object[] arrayOfObject1 = getArray();
/*  900 */       int i = arrayOfObject1.length;
/*  901 */       int j; if (i != 0) {
/*  902 */         j = 0;
/*  903 */         Object[] arrayOfObject2 = new Object[i];
/*  904 */         for (int k = 0; k < i; k++) {
/*  905 */           Object localObject1 = arrayOfObject1[k];
/*  906 */           if (!paramPredicate.test(localObject1))
/*  907 */             arrayOfObject2[(j++)] = localObject1;
/*      */         }
/*  909 */         if (j != i) {
/*  910 */           setArray(Arrays.copyOf(arrayOfObject2, j));
/*  911 */           return 1;
/*      */         }
/*      */       }
/*  914 */       return 0;
/*      */     } finally {
/*  916 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public void replaceAll(UnaryOperator<E> paramUnaryOperator) {
/*  921 */     if (paramUnaryOperator == null) throw new NullPointerException();
/*  922 */     ReentrantLock localReentrantLock = this.lock;
/*  923 */     localReentrantLock.lock();
/*      */     try {
/*  925 */       Object[] arrayOfObject1 = getArray();
/*  926 */       int i = arrayOfObject1.length;
/*  927 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, i);
/*  928 */       for (int j = 0; j < i; j++) {
/*  929 */         Object localObject1 = arrayOfObject1[j];
/*  930 */         arrayOfObject2[j] = paramUnaryOperator.apply(localObject1);
/*      */       }
/*  932 */       setArray(arrayOfObject2);
/*      */     } finally {
/*  934 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public void sort(Comparator<? super E> paramComparator) {
/*  939 */     ReentrantLock localReentrantLock = this.lock;
/*  940 */     localReentrantLock.lock();
/*      */     try {
/*  942 */       Object[] arrayOfObject1 = getArray();
/*  943 */       Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, arrayOfObject1.length);
/*  944 */       Object[] arrayOfObject3 = (Object[])arrayOfObject2;
/*  945 */       Arrays.sort(arrayOfObject3, paramComparator);
/*  946 */       setArray(arrayOfObject2);
/*      */     } finally {
/*  948 */       localReentrantLock.unlock();
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*  964 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*  966 */     Object[] arrayOfObject1 = getArray();
/*      */     
/*  968 */     paramObjectOutputStream.writeInt(arrayOfObject1.length);
/*      */     
/*      */ 
/*  971 */     for (Object localObject : arrayOfObject1) {
/*  972 */       paramObjectOutputStream.writeObject(localObject);
/*      */     }
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
/*  985 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/*  988 */     resetLock();
/*      */     
/*      */ 
/*  991 */     int i = paramObjectInputStream.readInt();
/*  992 */     Object[] arrayOfObject = new Object[i];
/*      */     
/*      */ 
/*  995 */     for (int j = 0; j < i; j++)
/*  996 */       arrayOfObject[j] = paramObjectInputStream.readObject();
/*  997 */     setArray(arrayOfObject);
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
/*      */   public String toString()
/*      */   {
/* 1011 */     return Arrays.toString(getArray());
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1030 */     if (paramObject == this)
/* 1031 */       return true;
/* 1032 */     if (!(paramObject instanceof List)) {
/* 1033 */       return false;
/*      */     }
/* 1035 */     List localList = (List)paramObject;
/* 1036 */     Iterator localIterator = localList.iterator();
/* 1037 */     Object[] arrayOfObject = getArray();
/* 1038 */     int i = arrayOfObject.length;
/* 1039 */     for (int j = 0; j < i; j++)
/* 1040 */       if ((!localIterator.hasNext()) || (!eq(arrayOfObject[j], localIterator.next())))
/* 1041 */         return false;
/* 1042 */     if (localIterator.hasNext())
/* 1043 */       return false;
/* 1044 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1055 */     int i = 1;
/* 1056 */     Object[] arrayOfObject = getArray();
/* 1057 */     int j = arrayOfObject.length;
/* 1058 */     for (int k = 0; k < j; k++) {
/* 1059 */       Object localObject = arrayOfObject[k];
/* 1060 */       i = 31 * i + (localObject == null ? 0 : localObject.hashCode());
/*      */     }
/* 1062 */     return i;
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
/*      */   public Iterator<E> iterator()
/*      */   {
/* 1076 */     return new COWIterator(getArray(), 0, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ListIterator<E> listIterator()
/*      */   {
/* 1088 */     return new COWIterator(getArray(), 0, null);
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
/*      */   public ListIterator<E> listIterator(int paramInt)
/*      */   {
/* 1102 */     Object[] arrayOfObject = getArray();
/* 1103 */     int i = arrayOfObject.length;
/* 1104 */     if ((paramInt < 0) || (paramInt > i)) {
/* 1105 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
/*      */     }
/* 1107 */     return new COWIterator(arrayOfObject, paramInt, null);
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1126 */     return Spliterators.spliterator(getArray(), 1040);
/*      */   }
/*      */   
/*      */   static final class COWIterator<E> implements ListIterator<E>
/*      */   {
/*      */     private final Object[] snapshot;
/*      */     private int cursor;
/*      */     
/*      */     private COWIterator(Object[] paramArrayOfObject, int paramInt)
/*      */     {
/* 1136 */       this.cursor = paramInt;
/* 1137 */       this.snapshot = paramArrayOfObject;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1141 */       return this.cursor < this.snapshot.length;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1145 */       return this.cursor > 0;
/*      */     }
/*      */     
/*      */     public E next()
/*      */     {
/* 1150 */       if (!hasNext())
/* 1151 */         throw new NoSuchElementException();
/* 1152 */       return (E)this.snapshot[(this.cursor++)];
/*      */     }
/*      */     
/*      */     public E previous()
/*      */     {
/* 1157 */       if (!hasPrevious())
/* 1158 */         throw new NoSuchElementException();
/* 1159 */       return (E)this.snapshot[(--this.cursor)];
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1163 */       return this.cursor;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1167 */       return this.cursor - 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void remove()
/*      */     {
/* 1176 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void set(E paramE)
/*      */     {
/* 1185 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void add(E paramE)
/*      */     {
/* 1194 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1199 */       Objects.requireNonNull(paramConsumer);
/* 1200 */       Object[] arrayOfObject = this.snapshot;
/* 1201 */       int i = arrayOfObject.length;
/* 1202 */       for (int j = this.cursor; j < i; j++) {
/* 1203 */         Object localObject = arrayOfObject[j];
/* 1204 */         paramConsumer.accept(localObject);
/*      */       }
/* 1206 */       this.cursor = i;
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
/*      */   public List<E> subList(int paramInt1, int paramInt2)
/*      */   {
/* 1226 */     ReentrantLock localReentrantLock = this.lock;
/* 1227 */     localReentrantLock.lock();
/*      */     try {
/* 1229 */       Object[] arrayOfObject = getArray();
/* 1230 */       int i = arrayOfObject.length;
/* 1231 */       if ((paramInt1 < 0) || (paramInt2 > i) || (paramInt1 > paramInt2))
/* 1232 */         throw new IndexOutOfBoundsException();
/* 1233 */       return new COWSubList(this, paramInt1, paramInt2);
/*      */     } finally {
/* 1235 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class COWSubList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private final CopyOnWriteArrayList<E> l;
/*      */     
/*      */ 
/*      */ 
/*      */     private final int offset;
/*      */     
/*      */ 
/*      */ 
/*      */     private int size;
/*      */     
/*      */ 
/*      */ 
/*      */     private Object[] expectedArray;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     COWSubList(CopyOnWriteArrayList<E> paramCopyOnWriteArrayList, int paramInt1, int paramInt2)
/*      */     {
/* 1266 */       this.l = paramCopyOnWriteArrayList;
/* 1267 */       this.expectedArray = this.l.getArray();
/* 1268 */       this.offset = paramInt1;
/* 1269 */       this.size = (paramInt2 - paramInt1);
/*      */     }
/*      */     
/*      */     private void checkForComodification()
/*      */     {
/* 1274 */       if (this.l.getArray() != this.expectedArray) {
/* 1275 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     private void rangeCheck(int paramInt) {
/* 1280 */       if ((paramInt < 0) || (paramInt >= this.size)) {
/* 1281 */         throw new IndexOutOfBoundsException("Index: " + paramInt + ",Size: " + this.size);
/*      */       }
/*      */     }
/*      */     
/*      */     public E set(int paramInt, E paramE) {
/* 1286 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1287 */       localReentrantLock.lock();
/*      */       try {
/* 1289 */         rangeCheck(paramInt);
/* 1290 */         checkForComodification();
/* 1291 */         Object localObject1 = this.l.set(paramInt + this.offset, paramE);
/* 1292 */         this.expectedArray = this.l.getArray();
/* 1293 */         return (E)localObject1;
/*      */       } finally {
/* 1295 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public E get(int paramInt) {
/* 1300 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1301 */       localReentrantLock.lock();
/*      */       try {
/* 1303 */         rangeCheck(paramInt);
/* 1304 */         checkForComodification();
/* 1305 */         return (E)this.l.get(paramInt + this.offset);
/*      */       } finally {
/* 1307 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public int size() {
/* 1312 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1313 */       localReentrantLock.lock();
/*      */       try {
/* 1315 */         checkForComodification();
/* 1316 */         return this.size;
/*      */       } finally {
/* 1318 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public void add(int paramInt, E paramE) {
/* 1323 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1324 */       localReentrantLock.lock();
/*      */       try {
/* 1326 */         checkForComodification();
/* 1327 */         if ((paramInt < 0) || (paramInt > this.size))
/* 1328 */           throw new IndexOutOfBoundsException();
/* 1329 */         this.l.add(paramInt + this.offset, paramE);
/* 1330 */         this.expectedArray = this.l.getArray();
/* 1331 */         this.size += 1;
/*      */       } finally {
/* 1333 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     public void clear()
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield 2	java/util/concurrent/CopyOnWriteArrayList$COWSubList:l	Ljava/util/concurrent/CopyOnWriteArrayList;
/*      */       //   4: getfield 18	java/util/concurrent/CopyOnWriteArrayList:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   7: astore_1
/*      */       //   8: aload_1
/*      */       //   9: invokevirtual 19	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */       //   12: aload_0
/*      */       //   13: invokespecial 21	java/util/concurrent/CopyOnWriteArrayList$COWSubList:checkForComodification	()V
/*      */       //   16: aload_0
/*      */       //   17: getfield 2	java/util/concurrent/CopyOnWriteArrayList$COWSubList:l	Ljava/util/concurrent/CopyOnWriteArrayList;
/*      */       //   20: aload_0
/*      */       //   21: getfield 5	java/util/concurrent/CopyOnWriteArrayList$COWSubList:offset	I
/*      */       //   24: aload_0
/*      */       //   25: getfield 5	java/util/concurrent/CopyOnWriteArrayList$COWSubList:offset	I
/*      */       //   28: aload_0
/*      */       //   29: getfield 6	java/util/concurrent/CopyOnWriteArrayList$COWSubList:size	I
/*      */       //   32: iadd
/*      */       //   33: invokevirtual 27	java/util/concurrent/CopyOnWriteArrayList:removeRange	(II)V
/*      */       //   36: aload_0
/*      */       //   37: aload_0
/*      */       //   38: getfield 2	java/util/concurrent/CopyOnWriteArrayList$COWSubList:l	Ljava/util/concurrent/CopyOnWriteArrayList;
/*      */       //   41: invokevirtual 3	java/util/concurrent/CopyOnWriteArrayList:getArray	()[Ljava/lang/Object;
/*      */       //   44: putfield 4	java/util/concurrent/CopyOnWriteArrayList$COWSubList:expectedArray	[Ljava/lang/Object;
/*      */       //   47: aload_0
/*      */       //   48: iconst_0
/*      */       //   49: putfield 6	java/util/concurrent/CopyOnWriteArrayList$COWSubList:size	I
/*      */       //   52: aload_1
/*      */       //   53: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   56: goto +10 -> 66
/*      */       //   59: astore_2
/*      */       //   60: aload_1
/*      */       //   61: invokevirtual 23	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   64: aload_2
/*      */       //   65: athrow
/*      */       //   66: return
/*      */       // Line number table:
/*      */       //   Java source line #1338	-> byte code offset #0
/*      */       //   Java source line #1339	-> byte code offset #8
/*      */       //   Java source line #1341	-> byte code offset #12
/*      */       //   Java source line #1342	-> byte code offset #16
/*      */       //   Java source line #1343	-> byte code offset #36
/*      */       //   Java source line #1344	-> byte code offset #47
/*      */       //   Java source line #1346	-> byte code offset #52
/*      */       //   Java source line #1347	-> byte code offset #56
/*      */       //   Java source line #1346	-> byte code offset #59
/*      */       //   Java source line #1348	-> byte code offset #66
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	67	0	this	COWSubList
/*      */       //   7	54	1	localReentrantLock	ReentrantLock
/*      */       //   59	6	2	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   12	52	59	finally
/*      */     }
/*      */     
/*      */     public E remove(int paramInt)
/*      */     {
/* 1351 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1352 */       localReentrantLock.lock();
/*      */       try {
/* 1354 */         rangeCheck(paramInt);
/* 1355 */         checkForComodification();
/* 1356 */         Object localObject1 = this.l.remove(paramInt + this.offset);
/* 1357 */         this.expectedArray = this.l.getArray();
/* 1358 */         this.size -= 1;
/* 1359 */         return (E)localObject1;
/*      */       } finally {
/* 1361 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/* 1366 */       int i = indexOf(paramObject);
/* 1367 */       if (i == -1)
/* 1368 */         return false;
/* 1369 */       remove(i);
/* 1370 */       return true;
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 1374 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1375 */       localReentrantLock.lock();
/*      */       try {
/* 1377 */         checkForComodification();
/* 1378 */         return new CopyOnWriteArrayList.COWSubListIterator(this.l, 0, this.offset, this.size);
/*      */       } finally {
/* 1380 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public ListIterator<E> listIterator(int paramInt) {
/* 1385 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1386 */       localReentrantLock.lock();
/*      */       try {
/* 1388 */         checkForComodification();
/* 1389 */         if ((paramInt < 0) || (paramInt > this.size)) {
/* 1390 */           throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
/*      */         }
/* 1392 */         return new CopyOnWriteArrayList.COWSubListIterator(this.l, paramInt, this.offset, this.size);
/*      */       } finally {
/* 1394 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public List<E> subList(int paramInt1, int paramInt2) {
/* 1399 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1400 */       localReentrantLock.lock();
/*      */       try {
/* 1402 */         checkForComodification();
/* 1403 */         if ((paramInt1 < 0) || (paramInt2 > this.size) || (paramInt1 > paramInt2))
/* 1404 */           throw new IndexOutOfBoundsException();
/* 1405 */         return new COWSubList(this.l, paramInt1 + this.offset, paramInt2 + this.offset);
/*      */       }
/*      */       finally {
/* 1408 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super E> paramConsumer) {
/* 1413 */       if (paramConsumer == null) throw new NullPointerException();
/* 1414 */       int i = this.offset;
/* 1415 */       int j = this.offset + this.size;
/* 1416 */       Object[] arrayOfObject = this.expectedArray;
/* 1417 */       if (this.l.getArray() != arrayOfObject)
/* 1418 */         throw new ConcurrentModificationException();
/* 1419 */       if ((i < 0) || (j > arrayOfObject.length))
/* 1420 */         throw new IndexOutOfBoundsException();
/* 1421 */       for (int k = i; k < j; k++) {
/* 1422 */         Object localObject = arrayOfObject[k];
/* 1423 */         paramConsumer.accept(localObject);
/*      */       }
/*      */     }
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> paramUnaryOperator) {
/* 1428 */       if (paramUnaryOperator == null) throw new NullPointerException();
/* 1429 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1430 */       localReentrantLock.lock();
/*      */       try {
/* 1432 */         int i = this.offset;
/* 1433 */         int j = this.offset + this.size;
/* 1434 */         Object[] arrayOfObject1 = this.expectedArray;
/* 1435 */         if (this.l.getArray() != arrayOfObject1)
/* 1436 */           throw new ConcurrentModificationException();
/* 1437 */         int k = arrayOfObject1.length;
/* 1438 */         if ((i < 0) || (j > k))
/* 1439 */           throw new IndexOutOfBoundsException();
/* 1440 */         Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, k);
/* 1441 */         for (int m = i; m < j; m++) {
/* 1442 */           Object localObject1 = arrayOfObject1[m];
/* 1443 */           arrayOfObject2[m] = paramUnaryOperator.apply(localObject1);
/*      */         }
/* 1445 */         this.l.setArray(this.expectedArray = arrayOfObject2);
/*      */       } finally {
/* 1447 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public void sort(Comparator<? super E> paramComparator) {
/* 1452 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1453 */       localReentrantLock.lock();
/*      */       try {
/* 1455 */         int i = this.offset;
/* 1456 */         int j = this.offset + this.size;
/* 1457 */         Object[] arrayOfObject1 = this.expectedArray;
/* 1458 */         if (this.l.getArray() != arrayOfObject1)
/* 1459 */           throw new ConcurrentModificationException();
/* 1460 */         int k = arrayOfObject1.length;
/* 1461 */         if ((i < 0) || (j > k))
/* 1462 */           throw new IndexOutOfBoundsException();
/* 1463 */         Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, k);
/* 1464 */         Object[] arrayOfObject3 = (Object[])arrayOfObject2;
/* 1465 */         Arrays.sort(arrayOfObject3, i, j, paramComparator);
/* 1466 */         this.l.setArray(this.expectedArray = arrayOfObject2);
/*      */       } finally {
/* 1468 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> paramCollection) {
/* 1473 */       if (paramCollection == null) throw new NullPointerException();
/* 1474 */       boolean bool = false;
/* 1475 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1476 */       localReentrantLock.lock();
/*      */       try {
/* 1478 */         int i = this.size;
/* 1479 */         if (i > 0) {
/* 1480 */           int j = this.offset;
/* 1481 */           int k = this.offset + i;
/* 1482 */           Object[] arrayOfObject1 = this.expectedArray;
/* 1483 */           if (this.l.getArray() != arrayOfObject1)
/* 1484 */             throw new ConcurrentModificationException();
/* 1485 */           int m = arrayOfObject1.length;
/* 1486 */           if ((j < 0) || (k > m))
/* 1487 */             throw new IndexOutOfBoundsException();
/* 1488 */           int n = 0;
/* 1489 */           Object[] arrayOfObject2 = new Object[i];
/* 1490 */           for (int i1 = j; i1 < k; i1++) {
/* 1491 */             Object localObject1 = arrayOfObject1[i1];
/* 1492 */             if (!paramCollection.contains(localObject1))
/* 1493 */               arrayOfObject2[(n++)] = localObject1;
/*      */           }
/* 1495 */           if (n != i) {
/* 1496 */             Object[] arrayOfObject3 = new Object[m - i + n];
/* 1497 */             System.arraycopy(arrayOfObject1, 0, arrayOfObject3, 0, j);
/* 1498 */             System.arraycopy(arrayOfObject2, 0, arrayOfObject3, j, n);
/* 1499 */             System.arraycopy(arrayOfObject1, k, arrayOfObject3, j + n, m - k);
/*      */             
/* 1501 */             this.size = n;
/* 1502 */             bool = true;
/* 1503 */             this.l.setArray(this.expectedArray = arrayOfObject3);
/*      */           }
/*      */         }
/*      */       } finally {
/* 1507 */         localReentrantLock.unlock();
/*      */       }
/* 1509 */       return bool;
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> paramCollection) {
/* 1513 */       if (paramCollection == null) throw new NullPointerException();
/* 1514 */       boolean bool = false;
/* 1515 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1516 */       localReentrantLock.lock();
/*      */       try {
/* 1518 */         int i = this.size;
/* 1519 */         if (i > 0) {
/* 1520 */           int j = this.offset;
/* 1521 */           int k = this.offset + i;
/* 1522 */           Object[] arrayOfObject1 = this.expectedArray;
/* 1523 */           if (this.l.getArray() != arrayOfObject1)
/* 1524 */             throw new ConcurrentModificationException();
/* 1525 */           int m = arrayOfObject1.length;
/* 1526 */           if ((j < 0) || (k > m))
/* 1527 */             throw new IndexOutOfBoundsException();
/* 1528 */           int n = 0;
/* 1529 */           Object[] arrayOfObject2 = new Object[i];
/* 1530 */           for (int i1 = j; i1 < k; i1++) {
/* 1531 */             Object localObject1 = arrayOfObject1[i1];
/* 1532 */             if (paramCollection.contains(localObject1))
/* 1533 */               arrayOfObject2[(n++)] = localObject1;
/*      */           }
/* 1535 */           if (n != i) {
/* 1536 */             Object[] arrayOfObject3 = new Object[m - i + n];
/* 1537 */             System.arraycopy(arrayOfObject1, 0, arrayOfObject3, 0, j);
/* 1538 */             System.arraycopy(arrayOfObject2, 0, arrayOfObject3, j, n);
/* 1539 */             System.arraycopy(arrayOfObject1, k, arrayOfObject3, j + n, m - k);
/*      */             
/* 1541 */             this.size = n;
/* 1542 */             bool = true;
/* 1543 */             this.l.setArray(this.expectedArray = arrayOfObject3);
/*      */           }
/*      */         }
/*      */       } finally {
/* 1547 */         localReentrantLock.unlock();
/*      */       }
/* 1549 */       return bool;
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> paramPredicate) {
/* 1553 */       if (paramPredicate == null) throw new NullPointerException();
/* 1554 */       boolean bool = false;
/* 1555 */       ReentrantLock localReentrantLock = this.l.lock;
/* 1556 */       localReentrantLock.lock();
/*      */       try {
/* 1558 */         int i = this.size;
/* 1559 */         if (i > 0) {
/* 1560 */           int j = this.offset;
/* 1561 */           int k = this.offset + i;
/* 1562 */           Object[] arrayOfObject1 = this.expectedArray;
/* 1563 */           if (this.l.getArray() != arrayOfObject1)
/* 1564 */             throw new ConcurrentModificationException();
/* 1565 */           int m = arrayOfObject1.length;
/* 1566 */           if ((j < 0) || (k > m))
/* 1567 */             throw new IndexOutOfBoundsException();
/* 1568 */           int n = 0;
/* 1569 */           Object[] arrayOfObject2 = new Object[i];
/* 1570 */           for (int i1 = j; i1 < k; i1++) {
/* 1571 */             Object localObject1 = arrayOfObject1[i1];
/* 1572 */             if (!paramPredicate.test(localObject1))
/* 1573 */               arrayOfObject2[(n++)] = localObject1;
/*      */           }
/* 1575 */           if (n != i) {
/* 1576 */             Object[] arrayOfObject3 = new Object[m - i + n];
/* 1577 */             System.arraycopy(arrayOfObject1, 0, arrayOfObject3, 0, j);
/* 1578 */             System.arraycopy(arrayOfObject2, 0, arrayOfObject3, j, n);
/* 1579 */             System.arraycopy(arrayOfObject1, k, arrayOfObject3, j + n, m - k);
/*      */             
/* 1581 */             this.size = n;
/* 1582 */             bool = true;
/* 1583 */             this.l.setArray(this.expectedArray = arrayOfObject3);
/*      */           }
/*      */         }
/*      */       } finally {
/* 1587 */         localReentrantLock.unlock();
/*      */       }
/* 1589 */       return bool;
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1593 */       int i = this.offset;
/* 1594 */       int j = this.offset + this.size;
/* 1595 */       Object[] arrayOfObject = this.expectedArray;
/* 1596 */       if (this.l.getArray() != arrayOfObject)
/* 1597 */         throw new ConcurrentModificationException();
/* 1598 */       if ((i < 0) || (j > arrayOfObject.length)) {
/* 1599 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 1601 */       return Spliterators.spliterator(arrayOfObject, i, j, 1040);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class COWSubListIterator<E> implements ListIterator<E>
/*      */   {
/*      */     private final ListIterator<E> it;
/*      */     private final int offset;
/*      */     private final int size;
/*      */     
/*      */     COWSubListIterator(List<E> paramList, int paramInt1, int paramInt2, int paramInt3) {
/* 1612 */       this.offset = paramInt2;
/* 1613 */       this.size = paramInt3;
/* 1614 */       this.it = paramList.listIterator(paramInt1 + paramInt2);
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1618 */       return nextIndex() < this.size;
/*      */     }
/*      */     
/*      */     public E next() {
/* 1622 */       if (hasNext()) {
/* 1623 */         return (E)this.it.next();
/*      */       }
/* 1625 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1629 */       return previousIndex() >= 0;
/*      */     }
/*      */     
/*      */     public E previous() {
/* 1633 */       if (hasPrevious()) {
/* 1634 */         return (E)this.it.previous();
/*      */       }
/* 1636 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1640 */       return this.it.nextIndex() - this.offset;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1644 */       return this.it.previousIndex() - this.offset;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1648 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void set(E paramE) {
/* 1652 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(E paramE) {
/* 1656 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1661 */       Objects.requireNonNull(paramConsumer);
/* 1662 */       int i = this.size;
/* 1663 */       ListIterator localListIterator = this.it;
/* 1664 */       while (nextIndex() < i) {
/* 1665 */         paramConsumer.accept(localListIterator.next());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void resetLock()
/*      */   {
/* 1672 */     UNSAFE.putObjectVolatile(this, lockOffset, new ReentrantLock());
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*      */     try {
/* 1678 */       UNSAFE = Unsafe.getUnsafe();
/* 1679 */       Class localClass = CopyOnWriteArrayList.class;
/*      */       
/* 1681 */       lockOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("lock"));
/*      */     } catch (Exception localException) {
/* 1683 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/CopyOnWriteArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */