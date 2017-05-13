/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IdentityHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements Map<K, V>, Serializable, Cloneable
/*      */ {
/*      */   private static final int DEFAULT_CAPACITY = 32;
/*      */   private static final int MINIMUM_CAPACITY = 4;
/*      */   private static final int MAXIMUM_CAPACITY = 536870912;
/*      */   transient Object[] table;
/*      */   int size;
/*      */   transient int modCount;
/*      */   private transient int threshold;
/*  191 */   static final Object NULL_KEY = new Object();
/*      */   private transient Set<Map.Entry<K, V>> entrySet;
/*      */   private static final long serialVersionUID = 8188218128353913216L;
/*      */   
/*      */   private static Object maskNull(Object paramObject)
/*      */   {
/*  197 */     return paramObject == null ? NULL_KEY : paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final Object unmaskNull(Object paramObject)
/*      */   {
/*  204 */     return paramObject == NULL_KEY ? null : paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public IdentityHashMap()
/*      */   {
/*  212 */     init(32);
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
/*      */   public IdentityHashMap(int paramInt)
/*      */   {
/*  225 */     if (paramInt < 0) {
/*  226 */       throw new IllegalArgumentException("expectedMaxSize is negative: " + paramInt);
/*      */     }
/*  228 */     init(capacity(paramInt));
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
/*      */   private int capacity(int paramInt)
/*      */   {
/*  241 */     int i = 3 * paramInt / 2;
/*      */     
/*      */     int j;
/*      */     
/*  245 */     if ((i > 536870912) || (i < 0)) {
/*  246 */       j = 536870912;
/*      */     } else {
/*  248 */       j = 4;
/*  249 */       while (j < i)
/*  250 */         j <<= 1;
/*      */     }
/*  252 */     return j;
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
/*      */   private void init(int paramInt)
/*      */   {
/*  265 */     this.threshold = (paramInt * 2 / 3);
/*  266 */     this.table = new Object[2 * paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IdentityHashMap(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  278 */     this((int)((1 + paramMap.size()) * 1.1D));
/*  279 */     putAll(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  288 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  299 */     return this.size == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int hash(Object paramObject, int paramInt)
/*      */   {
/*  306 */     int i = System.identityHashCode(paramObject);
/*      */     
/*  308 */     return (i << 1) - (i << 8) & paramInt - 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int nextKeyIndex(int paramInt1, int paramInt2)
/*      */   {
/*  315 */     return paramInt1 + 2 < paramInt2 ? paramInt1 + 2 : 0;
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
/*      */   public V get(Object paramObject)
/*      */   {
/*  337 */     Object localObject1 = maskNull(paramObject);
/*  338 */     Object[] arrayOfObject = this.table;
/*  339 */     int i = arrayOfObject.length;
/*  340 */     int j = hash(localObject1, i);
/*      */     for (;;) {
/*  342 */       Object localObject2 = arrayOfObject[j];
/*  343 */       if (localObject2 == localObject1)
/*  344 */         return (V)arrayOfObject[(j + 1)];
/*  345 */       if (localObject2 == null)
/*  346 */         return null;
/*  347 */       j = nextKeyIndex(j, i);
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
/*      */   public boolean containsKey(Object paramObject)
/*      */   {
/*  361 */     Object localObject1 = maskNull(paramObject);
/*  362 */     Object[] arrayOfObject = this.table;
/*  363 */     int i = arrayOfObject.length;
/*  364 */     int j = hash(localObject1, i);
/*      */     for (;;) {
/*  366 */       Object localObject2 = arrayOfObject[j];
/*  367 */       if (localObject2 == localObject1)
/*  368 */         return true;
/*  369 */       if (localObject2 == null)
/*  370 */         return false;
/*  371 */       j = nextKeyIndex(j, i);
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
/*      */   public boolean containsValue(Object paramObject)
/*      */   {
/*  385 */     Object[] arrayOfObject = this.table;
/*  386 */     for (int i = 1; i < arrayOfObject.length; i += 2) {
/*  387 */       if ((arrayOfObject[i] == paramObject) && (arrayOfObject[(i - 1)] != null))
/*  388 */         return true;
/*      */     }
/*  390 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean containsMapping(Object paramObject1, Object paramObject2)
/*      */   {
/*  402 */     Object localObject1 = maskNull(paramObject1);
/*  403 */     Object[] arrayOfObject = this.table;
/*  404 */     int i = arrayOfObject.length;
/*  405 */     int j = hash(localObject1, i);
/*      */     for (;;) {
/*  407 */       Object localObject2 = arrayOfObject[j];
/*  408 */       if (localObject2 == localObject1)
/*  409 */         return arrayOfObject[(j + 1)] == paramObject2;
/*  410 */       if (localObject2 == null)
/*  411 */         return false;
/*  412 */       j = nextKeyIndex(j, i);
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
/*      */   public V put(K paramK, V paramV)
/*      */   {
/*  432 */     Object localObject1 = maskNull(paramK);
/*  433 */     Object[] arrayOfObject = this.table;
/*  434 */     int i = arrayOfObject.length;
/*  435 */     int j = hash(localObject1, i);
/*      */     
/*      */     Object localObject2;
/*  438 */     while ((localObject2 = arrayOfObject[j]) != null) {
/*  439 */       if (localObject2 == localObject1)
/*      */       {
/*  441 */         Object localObject3 = arrayOfObject[(j + 1)];
/*  442 */         arrayOfObject[(j + 1)] = paramV;
/*  443 */         return (V)localObject3;
/*      */       }
/*  445 */       j = nextKeyIndex(j, i);
/*      */     }
/*      */     
/*  448 */     this.modCount += 1;
/*  449 */     arrayOfObject[j] = localObject1;
/*  450 */     arrayOfObject[(j + 1)] = paramV;
/*  451 */     if (++this.size >= this.threshold)
/*  452 */       resize(i);
/*  453 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resize(int paramInt)
/*      */   {
/*  463 */     int i = paramInt * 2;
/*      */     
/*  465 */     Object[] arrayOfObject1 = this.table;
/*  466 */     int j = arrayOfObject1.length;
/*  467 */     if (j == 1073741824) {
/*  468 */       if (this.threshold == 536870911)
/*  469 */         throw new IllegalStateException("Capacity exhausted.");
/*  470 */       this.threshold = 536870911;
/*  471 */       return;
/*      */     }
/*  473 */     if (j >= i) {
/*  474 */       return;
/*      */     }
/*  476 */     Object[] arrayOfObject2 = new Object[i];
/*  477 */     this.threshold = (i / 3);
/*      */     
/*  479 */     for (int k = 0; k < j; k += 2) {
/*  480 */       Object localObject1 = arrayOfObject1[k];
/*  481 */       if (localObject1 != null) {
/*  482 */         Object localObject2 = arrayOfObject1[(k + 1)];
/*  483 */         arrayOfObject1[k] = null;
/*  484 */         arrayOfObject1[(k + 1)] = null;
/*  485 */         int m = hash(localObject1, i);
/*  486 */         while (arrayOfObject2[m] != null)
/*  487 */           m = nextKeyIndex(m, i);
/*  488 */         arrayOfObject2[m] = localObject1;
/*  489 */         arrayOfObject2[(m + 1)] = localObject2;
/*      */       }
/*      */     }
/*  492 */     this.table = arrayOfObject2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  504 */     int i = paramMap.size();
/*  505 */     if (i == 0)
/*  506 */       return;
/*  507 */     if (i > this.threshold) {
/*  508 */       resize(capacity(i));
/*      */     }
/*  510 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  511 */       put(localEntry.getKey(), localEntry.getValue());
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
/*      */   public V remove(Object paramObject)
/*      */   {
/*  524 */     Object localObject1 = maskNull(paramObject);
/*  525 */     Object[] arrayOfObject = this.table;
/*  526 */     int i = arrayOfObject.length;
/*  527 */     int j = hash(localObject1, i);
/*      */     for (;;)
/*      */     {
/*  530 */       Object localObject2 = arrayOfObject[j];
/*  531 */       if (localObject2 == localObject1) {
/*  532 */         this.modCount += 1;
/*  533 */         this.size -= 1;
/*      */         
/*  535 */         Object localObject3 = arrayOfObject[(j + 1)];
/*  536 */         arrayOfObject[(j + 1)] = null;
/*  537 */         arrayOfObject[j] = null;
/*  538 */         closeDeletion(j);
/*  539 */         return (V)localObject3;
/*      */       }
/*  541 */       if (localObject2 == null)
/*  542 */         return null;
/*  543 */       j = nextKeyIndex(j, i);
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
/*      */   private boolean removeMapping(Object paramObject1, Object paramObject2)
/*      */   {
/*  557 */     Object localObject1 = maskNull(paramObject1);
/*  558 */     Object[] arrayOfObject = this.table;
/*  559 */     int i = arrayOfObject.length;
/*  560 */     int j = hash(localObject1, i);
/*      */     for (;;)
/*      */     {
/*  563 */       Object localObject2 = arrayOfObject[j];
/*  564 */       if (localObject2 == localObject1) {
/*  565 */         if (arrayOfObject[(j + 1)] != paramObject2)
/*  566 */           return false;
/*  567 */         this.modCount += 1;
/*  568 */         this.size -= 1;
/*  569 */         arrayOfObject[j] = null;
/*  570 */         arrayOfObject[(j + 1)] = null;
/*  571 */         closeDeletion(j);
/*  572 */         return true;
/*      */       }
/*  574 */       if (localObject2 == null)
/*  575 */         return false;
/*  576 */       j = nextKeyIndex(j, i);
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
/*      */   private void closeDeletion(int paramInt)
/*      */   {
/*  589 */     Object[] arrayOfObject = this.table;
/*  590 */     int i = arrayOfObject.length;
/*      */     
/*      */ 
/*      */ 
/*      */     Object localObject;
/*      */     
/*      */ 
/*  597 */     for (int j = nextKeyIndex(paramInt, i); (localObject = arrayOfObject[j]) != null; 
/*  598 */         j = nextKeyIndex(j, i))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  605 */       int k = hash(localObject, i);
/*  606 */       if (((j < k) && ((k <= paramInt) || (paramInt <= j))) || ((k <= paramInt) && (paramInt <= j))) {
/*  607 */         arrayOfObject[paramInt] = localObject;
/*  608 */         arrayOfObject[(paramInt + 1)] = arrayOfObject[(j + 1)];
/*  609 */         arrayOfObject[j] = null;
/*  610 */         arrayOfObject[(j + 1)] = null;
/*  611 */         paramInt = j;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  621 */     this.modCount += 1;
/*  622 */     Object[] arrayOfObject = this.table;
/*  623 */     for (int i = 0; i < arrayOfObject.length; i++)
/*  624 */       arrayOfObject[i] = null;
/*  625 */     this.size = 0;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  646 */     if (paramObject == this)
/*  647 */       return true;
/*  648 */     Object localObject1; if ((paramObject instanceof IdentityHashMap)) {
/*  649 */       localObject1 = (IdentityHashMap)paramObject;
/*  650 */       if (((IdentityHashMap)localObject1).size() != this.size) {
/*  651 */         return false;
/*      */       }
/*  653 */       Object[] arrayOfObject = ((IdentityHashMap)localObject1).table;
/*  654 */       for (int i = 0; i < arrayOfObject.length; i += 2) {
/*  655 */         Object localObject2 = arrayOfObject[i];
/*  656 */         if ((localObject2 != null) && (!containsMapping(localObject2, arrayOfObject[(i + 1)])))
/*  657 */           return false;
/*      */       }
/*  659 */       return true; }
/*  660 */     if ((paramObject instanceof Map)) {
/*  661 */       localObject1 = (Map)paramObject;
/*  662 */       return entrySet().equals(((Map)localObject1).entrySet());
/*      */     }
/*  664 */     return false;
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
/*      */   public int hashCode()
/*      */   {
/*  688 */     int i = 0;
/*  689 */     Object[] arrayOfObject = this.table;
/*  690 */     for (int j = 0; j < arrayOfObject.length; j += 2) {
/*  691 */       Object localObject1 = arrayOfObject[j];
/*  692 */       if (localObject1 != null) {
/*  693 */         Object localObject2 = unmaskNull(localObject1);
/*      */         
/*  695 */         i = i + (System.identityHashCode(localObject2) ^ System.identityHashCode(arrayOfObject[(j + 1)]));
/*      */       }
/*      */     }
/*  698 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  709 */       IdentityHashMap localIdentityHashMap = (IdentityHashMap)super.clone();
/*  710 */       localIdentityHashMap.entrySet = null;
/*  711 */       localIdentityHashMap.table = ((Object[])this.table.clone());
/*  712 */       return localIdentityHashMap;
/*      */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  714 */       throw new InternalError(localCloneNotSupportedException);
/*      */     } }
/*      */   
/*      */   private abstract class IdentityHashMapIterator<T> implements Iterator<T> { private IdentityHashMapIterator() {}
/*      */     
/*  719 */     int index = IdentityHashMap.this.size != 0 ? 0 : IdentityHashMap.this.table.length;
/*  720 */     int expectedModCount = IdentityHashMap.this.modCount;
/*  721 */     int lastReturnedIndex = -1;
/*      */     boolean indexValid;
/*  723 */     Object[] traversalTable = IdentityHashMap.this.table;
/*      */     
/*      */     public boolean hasNext() {
/*  726 */       Object[] arrayOfObject = this.traversalTable;
/*  727 */       for (int i = this.index; i < arrayOfObject.length; i += 2) {
/*  728 */         Object localObject = arrayOfObject[i];
/*  729 */         if (localObject != null) {
/*  730 */           this.index = i;
/*  731 */           return this.indexValid = 1;
/*      */         }
/*      */       }
/*  734 */       this.index = arrayOfObject.length;
/*  735 */       return false;
/*      */     }
/*      */     
/*      */     protected int nextIndex() {
/*  739 */       if (IdentityHashMap.this.modCount != this.expectedModCount)
/*  740 */         throw new ConcurrentModificationException();
/*  741 */       if ((!this.indexValid) && (!hasNext())) {
/*  742 */         throw new NoSuchElementException();
/*      */       }
/*  744 */       this.indexValid = false;
/*  745 */       this.lastReturnedIndex = this.index;
/*  746 */       this.index += 2;
/*  747 */       return this.lastReturnedIndex;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  751 */       if (this.lastReturnedIndex == -1)
/*  752 */         throw new IllegalStateException();
/*  753 */       if (IdentityHashMap.this.modCount != this.expectedModCount) {
/*  754 */         throw new ConcurrentModificationException();
/*      */       }
/*  756 */       this.expectedModCount = (++IdentityHashMap.this.modCount);
/*  757 */       int i = this.lastReturnedIndex;
/*  758 */       this.lastReturnedIndex = -1;
/*      */       
/*  760 */       this.index = i;
/*  761 */       this.indexValid = false;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  775 */       Object[] arrayOfObject1 = this.traversalTable;
/*  776 */       int j = arrayOfObject1.length;
/*      */       
/*  778 */       int k = i;
/*  779 */       Object localObject1 = arrayOfObject1[k];
/*  780 */       arrayOfObject1[k] = null;
/*  781 */       arrayOfObject1[(k + 1)] = null;
/*      */       
/*      */ 
/*      */ 
/*  785 */       if (arrayOfObject1 != IdentityHashMap.this.table) {
/*  786 */         IdentityHashMap.this.remove(localObject1);
/*  787 */         this.expectedModCount = IdentityHashMap.this.modCount;
/*  788 */         return;
/*      */       }
/*      */       
/*  791 */       IdentityHashMap.this.size -= 1;
/*      */       
/*      */       Object localObject2;
/*  794 */       for (int m = IdentityHashMap.nextKeyIndex(k, j); (localObject2 = arrayOfObject1[m]) != null; 
/*  795 */           m = IdentityHashMap.nextKeyIndex(m, j)) {
/*  796 */         int n = IdentityHashMap.hash(localObject2, j);
/*      */         
/*  798 */         if (((m < n) && ((n <= k) || (k <= m))) || ((n <= k) && (k <= m)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  808 */           if ((m < i) && (k >= i) && (this.traversalTable == IdentityHashMap.this.table))
/*      */           {
/*  810 */             int i1 = j - i;
/*  811 */             Object[] arrayOfObject2 = new Object[i1];
/*  812 */             System.arraycopy(arrayOfObject1, i, arrayOfObject2, 0, i1);
/*      */             
/*  814 */             this.traversalTable = arrayOfObject2;
/*  815 */             this.index = 0;
/*      */           }
/*      */           
/*  818 */           arrayOfObject1[k] = localObject2;
/*  819 */           arrayOfObject1[(k + 1)] = arrayOfObject1[(m + 1)];
/*  820 */           arrayOfObject1[m] = null;
/*  821 */           arrayOfObject1[(m + 1)] = null;
/*  822 */           k = m;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*  828 */   private class KeyIterator extends IdentityHashMap<K, V>.IdentityHashMapIterator<K> { private KeyIterator() { super(null); }
/*      */     
/*      */ 
/*  831 */     public K next() { return (K)IdentityHashMap.unmaskNull(this.traversalTable[nextIndex()]); }
/*      */   }
/*      */   
/*      */   private class ValueIterator extends IdentityHashMap<K, V>.IdentityHashMapIterator<V> {
/*  835 */     private ValueIterator() { super(null); }
/*      */     
/*      */ 
/*  838 */     public V next() { return (V)this.traversalTable[(nextIndex() + 1)]; } }
/*      */   
/*      */   private class EntryIterator extends IdentityHashMap<K, V>.IdentityHashMapIterator<Map.Entry<K, V>> { private IdentityHashMap<K, V>.EntryIterator.Entry lastReturnedEntry;
/*      */     
/*  842 */     private EntryIterator() { super(null); }
/*      */     
/*      */ 
/*      */ 
/*      */     public Map.Entry<K, V> next()
/*      */     {
/*  848 */       this.lastReturnedEntry = new Entry(nextIndex(), null);
/*  849 */       return this.lastReturnedEntry;
/*      */     }
/*      */     
/*      */     public void remove()
/*      */     {
/*  854 */       this.lastReturnedIndex = (null == this.lastReturnedEntry ? -1 : this.lastReturnedEntry.index);
/*  855 */       super.remove();
/*  856 */       this.lastReturnedEntry.index = this.lastReturnedIndex;
/*  857 */       this.lastReturnedEntry = null;
/*      */     }
/*      */     
/*      */     private class Entry implements Map.Entry<K, V> {
/*      */       private int index;
/*      */       
/*      */       private Entry(int paramInt) {
/*  864 */         this.index = paramInt;
/*      */       }
/*      */       
/*      */       public K getKey()
/*      */       {
/*  869 */         checkIndexForEntryUse();
/*  870 */         return (K)IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index]);
/*      */       }
/*      */       
/*      */       public V getValue()
/*      */       {
/*  875 */         checkIndexForEntryUse();
/*  876 */         return (V)IdentityHashMap.EntryIterator.this.traversalTable[(this.index + 1)];
/*      */       }
/*      */       
/*      */       public V setValue(V paramV)
/*      */       {
/*  881 */         checkIndexForEntryUse();
/*  882 */         Object localObject = IdentityHashMap.EntryIterator.this.traversalTable[(this.index + 1)];
/*  883 */         IdentityHashMap.EntryIterator.this.traversalTable[(this.index + 1)] = paramV;
/*      */         
/*  885 */         if (IdentityHashMap.EntryIterator.this.traversalTable != IdentityHashMap.this.table)
/*  886 */           IdentityHashMap.this.put(IdentityHashMap.EntryIterator.this.traversalTable[this.index], paramV);
/*  887 */         return (V)localObject;
/*      */       }
/*      */       
/*      */       public boolean equals(Object paramObject) {
/*  891 */         if (this.index < 0) {
/*  892 */           return super.equals(paramObject);
/*      */         }
/*  894 */         if (!(paramObject instanceof Map.Entry))
/*  895 */           return false;
/*  896 */         Map.Entry localEntry = (Map.Entry)paramObject;
/*      */         
/*  898 */         return (localEntry.getKey() == IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index])) && (localEntry.getValue() == IdentityHashMap.EntryIterator.this.traversalTable[(this.index + 1)]);
/*      */       }
/*      */       
/*      */       public int hashCode() {
/*  902 */         if (IdentityHashMap.EntryIterator.this.lastReturnedIndex < 0) {
/*  903 */           return super.hashCode();
/*      */         }
/*      */         
/*  906 */         return System.identityHashCode(IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index])) ^ System.identityHashCode(IdentityHashMap.EntryIterator.this.traversalTable[(this.index + 1)]);
/*      */       }
/*      */       
/*      */       public String toString() {
/*  910 */         if (this.index < 0) {
/*  911 */           return super.toString();
/*      */         }
/*  913 */         return IdentityHashMap.unmaskNull(IdentityHashMap.EntryIterator.this.traversalTable[this.index]) + "=" + IdentityHashMap.EntryIterator.this.traversalTable[(this.index + 1)];
/*      */       }
/*      */       
/*      */       private void checkIndexForEntryUse()
/*      */       {
/*  918 */         if (this.index < 0) {
/*  919 */           throw new IllegalStateException("Entry was removed");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<K> keySet()
/*      */   {
/*  972 */     Set localSet = this.keySet;
/*  973 */     if (localSet != null) {
/*  974 */       return localSet;
/*      */     }
/*  976 */     return this.keySet = new KeySet(null);
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*      */     
/*  981 */     public Iterator<K> iterator() { return new IdentityHashMap.KeyIterator(IdentityHashMap.this, null); }
/*      */     
/*      */     public int size() {
/*  984 */       return IdentityHashMap.this.size;
/*      */     }
/*      */     
/*  987 */     public boolean contains(Object paramObject) { return IdentityHashMap.this.containsKey(paramObject); }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/*  990 */       int i = IdentityHashMap.this.size;
/*  991 */       IdentityHashMap.this.remove(paramObject);
/*  992 */       return IdentityHashMap.this.size != i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean removeAll(Collection<?> paramCollection)
/*      */     {
/* 1000 */       Objects.requireNonNull(paramCollection);
/* 1001 */       boolean bool = false;
/* 1002 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 1003 */         if (paramCollection.contains(localIterator.next())) {
/* 1004 */           localIterator.remove();
/* 1005 */           bool = true;
/*      */         }
/*      */       }
/* 1008 */       return bool;
/*      */     }
/*      */     
/* 1011 */     public void clear() { IdentityHashMap.this.clear(); }
/*      */     
/*      */     public int hashCode() {
/* 1014 */       int i = 0;
/* 1015 */       for (Object localObject : this)
/* 1016 */         i += System.identityHashCode(localObject);
/* 1017 */       return i;
/*      */     }
/*      */     
/* 1020 */     public Object[] toArray() { return toArray(new Object[0]); }
/*      */     
/*      */     public <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/* 1024 */       int i = IdentityHashMap.this.modCount;
/* 1025 */       int j = size();
/* 1026 */       if (paramArrayOfT.length < j)
/* 1027 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), j);
/* 1028 */       Object[] arrayOfObject = IdentityHashMap.this.table;
/* 1029 */       int k = 0;
/* 1030 */       for (int m = 0; m < arrayOfObject.length; m += 2) {
/*      */         Object localObject;
/* 1032 */         if ((localObject = arrayOfObject[m]) != null)
/*      */         {
/* 1034 */           if (k >= j) {
/* 1035 */             throw new ConcurrentModificationException();
/*      */           }
/* 1037 */           paramArrayOfT[(k++)] = IdentityHashMap.unmaskNull(localObject);
/*      */         }
/*      */       }
/*      */       
/* 1041 */       if ((k < j) || (i != IdentityHashMap.this.modCount)) {
/* 1042 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/* 1045 */       if (k < paramArrayOfT.length) {
/* 1046 */         paramArrayOfT[k] = null;
/*      */       }
/* 1048 */       return paramArrayOfT;
/*      */     }
/*      */     
/*      */     public Spliterator<K> spliterator() {
/* 1052 */       return new IdentityHashMap.KeySpliterator(IdentityHashMap.this, 0, -1, 0, 0);
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
/*      */   public Collection<V> values()
/*      */   {
/* 1077 */     Collection localCollection = this.values;
/* 1078 */     if (localCollection != null) {
/* 1079 */       return localCollection;
/*      */     }
/* 1081 */     return this.values = new Values(null);
/*      */   }
/*      */   
/*      */   private class Values extends AbstractCollection<V> { private Values() {}
/*      */     
/* 1086 */     public Iterator<V> iterator() { return new IdentityHashMap.ValueIterator(IdentityHashMap.this, null); }
/*      */     
/*      */     public int size() {
/* 1089 */       return IdentityHashMap.this.size;
/*      */     }
/*      */     
/* 1092 */     public boolean contains(Object paramObject) { return IdentityHashMap.this.containsValue(paramObject); }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/* 1095 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 1096 */         if (localIterator.next() == paramObject) {
/* 1097 */           localIterator.remove();
/* 1098 */           return true;
/*      */         }
/*      */       }
/* 1101 */       return false;
/*      */     }
/*      */     
/* 1104 */     public void clear() { IdentityHashMap.this.clear(); }
/*      */     
/*      */     public Object[] toArray() {
/* 1107 */       return toArray(new Object[0]);
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] paramArrayOfT) {
/* 1111 */       int i = IdentityHashMap.this.modCount;
/* 1112 */       int j = size();
/* 1113 */       if (paramArrayOfT.length < j)
/* 1114 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), j);
/* 1115 */       Object[] arrayOfObject = IdentityHashMap.this.table;
/* 1116 */       int k = 0;
/* 1117 */       for (int m = 0; m < arrayOfObject.length; m += 2) {
/* 1118 */         if (arrayOfObject[m] != null)
/*      */         {
/* 1120 */           if (k >= j) {
/* 1121 */             throw new ConcurrentModificationException();
/*      */           }
/* 1123 */           paramArrayOfT[(k++)] = arrayOfObject[(m + 1)];
/*      */         }
/*      */       }
/*      */       
/* 1127 */       if ((k < j) || (i != IdentityHashMap.this.modCount)) {
/* 1128 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/* 1131 */       if (k < paramArrayOfT.length) {
/* 1132 */         paramArrayOfT[k] = null;
/*      */       }
/* 1134 */       return paramArrayOfT;
/*      */     }
/*      */     
/*      */     public Spliterator<V> spliterator() {
/* 1138 */       return new IdentityHashMap.ValueSpliterator(IdentityHashMap.this, 0, -1, 0, 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/* 1181 */     Set localSet = this.entrySet;
/* 1182 */     if (localSet != null) {
/* 1183 */       return localSet;
/*      */     }
/* 1185 */     return this.entrySet = new EntrySet(null);
/*      */   }
/*      */   
/*      */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */     
/* 1190 */     public Iterator<Map.Entry<K, V>> iterator() { return new IdentityHashMap.EntryIterator(IdentityHashMap.this, null); }
/*      */     
/*      */     public boolean contains(Object paramObject) {
/* 1193 */       if (!(paramObject instanceof Map.Entry))
/* 1194 */         return false;
/* 1195 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 1196 */       return IdentityHashMap.this.containsMapping(localEntry.getKey(), localEntry.getValue());
/*      */     }
/*      */     
/* 1199 */     public boolean remove(Object paramObject) { if (!(paramObject instanceof Map.Entry))
/* 1200 */         return false;
/* 1201 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 1202 */       return IdentityHashMap.this.removeMapping(localEntry.getKey(), localEntry.getValue());
/*      */     }
/*      */     
/* 1205 */     public int size() { return IdentityHashMap.this.size; }
/*      */     
/*      */     public void clear() {
/* 1208 */       IdentityHashMap.this.clear();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean removeAll(Collection<?> paramCollection)
/*      */     {
/* 1216 */       Objects.requireNonNull(paramCollection);
/* 1217 */       boolean bool = false;
/* 1218 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 1219 */         if (paramCollection.contains(localIterator.next())) {
/* 1220 */           localIterator.remove();
/* 1221 */           bool = true;
/*      */         }
/*      */       }
/* 1224 */       return bool;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 1228 */       return toArray(new Object[0]);
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/* 1233 */       int i = IdentityHashMap.this.modCount;
/* 1234 */       int j = size();
/* 1235 */       if (paramArrayOfT.length < j)
/* 1236 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), j);
/* 1237 */       Object[] arrayOfObject = IdentityHashMap.this.table;
/* 1238 */       int k = 0;
/* 1239 */       for (int m = 0; m < arrayOfObject.length; m += 2) {
/*      */         Object localObject;
/* 1241 */         if ((localObject = arrayOfObject[m]) != null)
/*      */         {
/* 1243 */           if (k >= j) {
/* 1244 */             throw new ConcurrentModificationException();
/*      */           }
/* 1246 */           paramArrayOfT[(k++)] = new AbstractMap.SimpleEntry(IdentityHashMap.unmaskNull(localObject), arrayOfObject[(m + 1)]);
/*      */         }
/*      */       }
/*      */       
/* 1250 */       if ((k < j) || (i != IdentityHashMap.this.modCount)) {
/* 1251 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/* 1254 */       if (k < paramArrayOfT.length) {
/* 1255 */         paramArrayOfT[k] = null;
/*      */       }
/* 1257 */       return paramArrayOfT;
/*      */     }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1261 */       return new IdentityHashMap.EntrySpliterator(IdentityHashMap.this, 0, -1, 0, 0);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1281 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*      */ 
/* 1284 */     paramObjectOutputStream.writeInt(this.size);
/*      */     
/*      */ 
/* 1287 */     Object[] arrayOfObject = this.table;
/* 1288 */     for (int i = 0; i < arrayOfObject.length; i += 2) {
/* 1289 */       Object localObject = arrayOfObject[i];
/* 1290 */       if (localObject != null) {
/* 1291 */         paramObjectOutputStream.writeObject(unmaskNull(localObject));
/* 1292 */         paramObjectOutputStream.writeObject(arrayOfObject[(i + 1)]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1304 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/* 1307 */     int i = paramObjectInputStream.readInt();
/*      */     
/*      */ 
/* 1310 */     init(capacity(i * 4 / 3));
/*      */     
/*      */ 
/* 1313 */     for (int j = 0; j < i; j++)
/*      */     {
/* 1315 */       Object localObject1 = paramObjectInputStream.readObject();
/*      */       
/* 1317 */       Object localObject2 = paramObjectInputStream.readObject();
/* 1318 */       putForCreate(localObject1, localObject2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void putForCreate(K paramK, V paramV)
/*      */     throws IOException
/*      */   {
/* 1329 */     Object localObject1 = maskNull(paramK);
/* 1330 */     Object[] arrayOfObject = this.table;
/* 1331 */     int i = arrayOfObject.length;
/* 1332 */     int j = hash(localObject1, i);
/*      */     
/*      */     Object localObject2;
/* 1335 */     while ((localObject2 = arrayOfObject[j]) != null) {
/* 1336 */       if (localObject2 == localObject1)
/* 1337 */         throw new StreamCorruptedException();
/* 1338 */       j = nextKeyIndex(j, i);
/*      */     }
/* 1340 */     arrayOfObject[j] = localObject1;
/* 1341 */     arrayOfObject[(j + 1)] = paramV;
/*      */   }
/*      */   
/*      */ 
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer)
/*      */   {
/* 1347 */     Objects.requireNonNull(paramBiConsumer);
/* 1348 */     int i = this.modCount;
/*      */     
/* 1350 */     Object[] arrayOfObject = this.table;
/* 1351 */     for (int j = 0; j < arrayOfObject.length; j += 2) {
/* 1352 */       Object localObject = arrayOfObject[j];
/* 1353 */       if (localObject != null) {
/* 1354 */         paramBiConsumer.accept(unmaskNull(localObject), arrayOfObject[(j + 1)]);
/*      */       }
/*      */       
/* 1357 */       if (this.modCount != i) {
/* 1358 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1366 */     Objects.requireNonNull(paramBiFunction);
/* 1367 */     int i = this.modCount;
/*      */     
/* 1369 */     Object[] arrayOfObject = this.table;
/* 1370 */     for (int j = 0; j < arrayOfObject.length; j += 2) {
/* 1371 */       Object localObject = arrayOfObject[j];
/* 1372 */       if (localObject != null) {
/* 1373 */         arrayOfObject[(j + 1)] = paramBiFunction.apply(unmaskNull(localObject), arrayOfObject[(j + 1)]);
/*      */       }
/*      */       
/* 1376 */       if (this.modCount != i) {
/* 1377 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static class IdentityHashMapSpliterator<K, V>
/*      */   {
/*      */     final IdentityHashMap<K, V> map;
/*      */     
/*      */     int index;
/*      */     
/*      */     int fence;
/*      */     int est;
/*      */     int expectedModCount;
/*      */     
/*      */     IdentityHashMapSpliterator(IdentityHashMap<K, V> paramIdentityHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1395 */       this.map = paramIdentityHashMap;
/* 1396 */       this.index = paramInt1;
/* 1397 */       this.fence = paramInt2;
/* 1398 */       this.est = paramInt3;
/* 1399 */       this.expectedModCount = paramInt4;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/* 1404 */       if ((i = this.fence) < 0) {
/* 1405 */         this.est = this.map.size;
/* 1406 */         this.expectedModCount = this.map.modCount;
/* 1407 */         i = this.fence = this.map.table.length;
/*      */       }
/* 1409 */       return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 1413 */       getFence();
/* 1414 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends IdentityHashMap.IdentityHashMapSpliterator<K, V> implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(IdentityHashMap<K, V> paramIdentityHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1423 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 1427 */       int i = getFence();int j = this.index;int k = j + i >>> 1 & 0xFFFFFFFE;
/* 1428 */       return j >= k ? null : new KeySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super K> paramConsumer)
/*      */     {
/* 1435 */       if (paramConsumer == null)
/* 1436 */         throw new NullPointerException();
/*      */       IdentityHashMap localIdentityHashMap;
/*      */       Object[] arrayOfObject;
/* 1439 */       int i; int j; if (((localIdentityHashMap = this.map) != null) && ((arrayOfObject = localIdentityHashMap.table) != null) && ((i = this.index) >= 0) && 
/* 1440 */         ((this.index = j = getFence()) <= arrayOfObject.length)) {
/* 1441 */         for (; i < j; i += 2) { Object localObject;
/* 1442 */           if ((localObject = arrayOfObject[i]) != null)
/* 1443 */             paramConsumer.accept(IdentityHashMap.unmaskNull(localObject));
/*      */         }
/* 1445 */         if (localIdentityHashMap.modCount == this.expectedModCount)
/* 1446 */           return;
/*      */       }
/* 1448 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> paramConsumer)
/*      */     {
/* 1453 */       if (paramConsumer == null)
/* 1454 */         throw new NullPointerException();
/* 1455 */       Object[] arrayOfObject = this.map.table;
/* 1456 */       int i = getFence();
/* 1457 */       while (this.index < i) {
/* 1458 */         Object localObject = arrayOfObject[this.index];
/* 1459 */         this.index += 2;
/* 1460 */         if (localObject != null) {
/* 1461 */           paramConsumer.accept(IdentityHashMap.unmaskNull(localObject));
/* 1462 */           if (this.map.modCount != this.expectedModCount)
/* 1463 */             throw new ConcurrentModificationException();
/* 1464 */           return true;
/*      */         }
/*      */       }
/* 1467 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1471 */       return ((this.fence < 0) || (this.est == this.map.size) ? 64 : 0) | 0x1;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends IdentityHashMap.IdentityHashMapSpliterator<K, V> implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(IdentityHashMap<K, V> paramIdentityHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1480 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 1484 */       int i = getFence();int j = this.index;int k = j + i >>> 1 & 0xFFFFFFFE;
/* 1485 */       return j >= k ? null : new ValueSpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super V> paramConsumer)
/*      */     {
/* 1491 */       if (paramConsumer == null)
/* 1492 */         throw new NullPointerException();
/*      */       IdentityHashMap localIdentityHashMap;
/*      */       Object[] arrayOfObject;
/* 1495 */       int i; int j; if (((localIdentityHashMap = this.map) != null) && ((arrayOfObject = localIdentityHashMap.table) != null) && ((i = this.index) >= 0) && 
/* 1496 */         ((this.index = j = getFence()) <= arrayOfObject.length)) {
/* 1497 */         for (; i < j; i += 2) {
/* 1498 */           if (arrayOfObject[i] != null) {
/* 1499 */             Object localObject = arrayOfObject[(i + 1)];
/* 1500 */             paramConsumer.accept(localObject);
/*      */           }
/*      */         }
/* 1503 */         if (localIdentityHashMap.modCount == this.expectedModCount)
/* 1504 */           return;
/*      */       }
/* 1506 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> paramConsumer) {
/* 1510 */       if (paramConsumer == null)
/* 1511 */         throw new NullPointerException();
/* 1512 */       Object[] arrayOfObject = this.map.table;
/* 1513 */       int i = getFence();
/* 1514 */       while (this.index < i) {
/* 1515 */         Object localObject1 = arrayOfObject[this.index];
/* 1516 */         Object localObject2 = arrayOfObject[(this.index + 1)];
/* 1517 */         this.index += 2;
/* 1518 */         if (localObject1 != null) {
/* 1519 */           paramConsumer.accept(localObject2);
/* 1520 */           if (this.map.modCount != this.expectedModCount)
/* 1521 */             throw new ConcurrentModificationException();
/* 1522 */           return true;
/*      */         }
/*      */       }
/* 1525 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1529 */       return (this.fence < 0) || (this.est == this.map.size) ? 64 : 0;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends IdentityHashMap.IdentityHashMapSpliterator<K, V>
/*      */     implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(IdentityHashMap<K, V> paramIdentityHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1539 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 1543 */       int i = getFence();int j = this.index;int k = j + i >>> 1 & 0xFFFFFFFE;
/* 1544 */       return j >= k ? null : new EntrySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */     {
/* 1550 */       if (paramConsumer == null)
/* 1551 */         throw new NullPointerException();
/*      */       IdentityHashMap localIdentityHashMap;
/*      */       Object[] arrayOfObject;
/* 1554 */       int i; int j; if (((localIdentityHashMap = this.map) != null) && ((arrayOfObject = localIdentityHashMap.table) != null) && ((i = this.index) >= 0) && 
/* 1555 */         ((this.index = j = getFence()) <= arrayOfObject.length)) {
/* 1556 */         for (; i < j; i += 2) {
/* 1557 */           Object localObject1 = arrayOfObject[i];
/* 1558 */           if (localObject1 != null)
/*      */           {
/* 1560 */             Object localObject2 = IdentityHashMap.unmaskNull(localObject1);
/* 1561 */             Object localObject3 = arrayOfObject[(i + 1)];
/* 1562 */             paramConsumer
/* 1563 */               .accept(new AbstractMap.SimpleImmutableEntry(localObject2, localObject3));
/*      */           }
/*      */         }
/*      */         
/* 1567 */         if (localIdentityHashMap.modCount == this.expectedModCount)
/* 1568 */           return;
/*      */       }
/* 1570 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> paramConsumer) {
/* 1574 */       if (paramConsumer == null)
/* 1575 */         throw new NullPointerException();
/* 1576 */       Object[] arrayOfObject = this.map.table;
/* 1577 */       int i = getFence();
/* 1578 */       while (this.index < i) {
/* 1579 */         Object localObject1 = arrayOfObject[this.index];
/* 1580 */         Object localObject2 = arrayOfObject[(this.index + 1)];
/* 1581 */         this.index += 2;
/* 1582 */         if (localObject1 != null)
/*      */         {
/* 1584 */           Object localObject3 = IdentityHashMap.unmaskNull(localObject1);
/* 1585 */           paramConsumer
/* 1586 */             .accept(new AbstractMap.SimpleImmutableEntry(localObject3, localObject2));
/* 1587 */           if (this.map.modCount != this.expectedModCount)
/* 1588 */             throw new ConcurrentModificationException();
/* 1589 */           return true;
/*      */         }
/*      */       }
/* 1592 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1596 */       return ((this.fence < 0) || (this.est == this.map.size) ? 64 : 0) | 0x1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/IdentityHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */