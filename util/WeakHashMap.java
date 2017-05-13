/*      */ package java.util;
/*      */ 
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
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
/*      */ public class WeakHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements Map<K, V>
/*      */ {
/*      */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*      */   Entry<K, V>[] table;
/*      */   private int size;
/*      */   private int threshold;
/*      */   private final float loadFactor;
/*  180 */   private final ReferenceQueue<Object> queue = new ReferenceQueue();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int modCount;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Entry<K, V>[] newTable(int paramInt)
/*      */   {
/*  195 */     return (Entry[])new Entry[paramInt];
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
/*      */   public WeakHashMap(int paramInt, float paramFloat)
/*      */   {
/*  208 */     if (paramInt < 0) {
/*  209 */       throw new IllegalArgumentException("Illegal Initial Capacity: " + paramInt);
/*      */     }
/*  211 */     if (paramInt > 1073741824) {
/*  212 */       paramInt = 1073741824;
/*      */     }
/*  214 */     if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat))) {
/*  215 */       throw new IllegalArgumentException("Illegal Load factor: " + paramFloat);
/*      */     }
/*  217 */     int i = 1;
/*  218 */     while (i < paramInt)
/*  219 */       i <<= 1;
/*  220 */     this.table = newTable(i);
/*  221 */     this.loadFactor = paramFloat;
/*  222 */     this.threshold = ((int)(i * paramFloat));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public WeakHashMap(int paramInt)
/*      */   {
/*  233 */     this(paramInt, 0.75F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public WeakHashMap()
/*      */   {
/*  241 */     this(16, 0.75F);
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
/*      */   public WeakHashMap(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  255 */     this(Math.max((int)(paramMap.size() / 0.75F) + 1, 16), 0.75F);
/*      */     
/*      */ 
/*  258 */     putAll(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  266 */   private static final Object NULL_KEY = new Object();
/*      */   
/*      */   private transient Set<Map.Entry<K, V>> entrySet;
/*      */   
/*      */   private static Object maskNull(Object paramObject)
/*      */   {
/*  272 */     return paramObject == null ? NULL_KEY : paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static Object unmaskNull(Object paramObject)
/*      */   {
/*  279 */     return paramObject == NULL_KEY ? null : paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean eq(Object paramObject1, Object paramObject2)
/*      */   {
/*  287 */     return (paramObject1 == paramObject2) || (paramObject1.equals(paramObject2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int hash(Object paramObject)
/*      */   {
/*  298 */     int i = paramObject.hashCode();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  303 */     i ^= i >>> 20 ^ i >>> 12;
/*  304 */     return i ^ i >>> 7 ^ i >>> 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int indexFor(int paramInt1, int paramInt2)
/*      */   {
/*  311 */     return paramInt1 & paramInt2 - 1;
/*      */   }
/*      */   
/*      */ 
/*      */   private void expungeStaleEntries()
/*      */   {
/*      */     Reference localReference;
/*  318 */     while ((localReference = this.queue.poll()) != null) {
/*  319 */       synchronized (this.queue)
/*      */       {
/*  321 */         Entry localEntry1 = (Entry)localReference;
/*  322 */         int i = indexFor(localEntry1.hash, this.table.length);
/*      */         
/*  324 */         Object localObject1 = this.table[i];
/*  325 */         Object localObject2 = localObject1;
/*  326 */         while (localObject2 != null) {
/*  327 */           Entry localEntry2 = ((Entry)localObject2).next;
/*  328 */           if (localObject2 == localEntry1) {
/*  329 */             if (localObject1 == localEntry1) {
/*  330 */               this.table[i] = localEntry2;
/*      */             } else {
/*  332 */               ((Entry)localObject1).next = localEntry2;
/*      */             }
/*      */             
/*  335 */             localEntry1.value = null;
/*  336 */             this.size -= 1;
/*  337 */             break;
/*      */           }
/*  339 */           localObject1 = localObject2;
/*  340 */           localObject2 = localEntry2;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Entry<K, V>[] getTable()
/*      */   {
/*  350 */     expungeStaleEntries();
/*  351 */     return this.table;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  361 */     if (this.size == 0)
/*  362 */       return 0;
/*  363 */     expungeStaleEntries();
/*  364 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  374 */     return size() == 0;
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
/*      */   public V get(Object paramObject)
/*      */   {
/*  395 */     Object localObject = maskNull(paramObject);
/*  396 */     int i = hash(localObject);
/*  397 */     Entry[] arrayOfEntry = getTable();
/*  398 */     int j = indexFor(i, arrayOfEntry.length);
/*  399 */     Entry localEntry = arrayOfEntry[j];
/*  400 */     while (localEntry != null) {
/*  401 */       if ((localEntry.hash == i) && (eq(localObject, localEntry.get())))
/*  402 */         return (V)localEntry.value;
/*  403 */       localEntry = localEntry.next;
/*      */     }
/*  405 */     return null;
/*      */   }
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
/*  417 */     return getEntry(paramObject) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Entry<K, V> getEntry(Object paramObject)
/*      */   {
/*  425 */     Object localObject = maskNull(paramObject);
/*  426 */     int i = hash(localObject);
/*  427 */     Entry[] arrayOfEntry = getTable();
/*  428 */     int j = indexFor(i, arrayOfEntry.length);
/*  429 */     Entry localEntry = arrayOfEntry[j];
/*  430 */     while ((localEntry != null) && ((localEntry.hash != i) || (!eq(localObject, localEntry.get()))))
/*  431 */       localEntry = localEntry.next;
/*  432 */     return localEntry;
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
/*      */   public V put(K paramK, V paramV)
/*      */   {
/*  448 */     Object localObject1 = maskNull(paramK);
/*  449 */     int i = hash(localObject1);
/*  450 */     Entry[] arrayOfEntry = getTable();
/*  451 */     int j = indexFor(i, arrayOfEntry.length);
/*      */     
/*  453 */     for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next) {
/*  454 */       if ((i == localEntry.hash) && (eq(localObject1, localEntry.get()))) {
/*  455 */         Object localObject2 = localEntry.value;
/*  456 */         if (paramV != localObject2)
/*  457 */           localEntry.value = paramV;
/*  458 */         return (V)localObject2;
/*      */       }
/*      */     }
/*      */     
/*  462 */     this.modCount += 1;
/*  463 */     localEntry = arrayOfEntry[j];
/*  464 */     arrayOfEntry[j] = new Entry(localObject1, paramV, this.queue, i, localEntry);
/*  465 */     if (++this.size >= this.threshold)
/*  466 */       resize(arrayOfEntry.length * 2);
/*  467 */     return null;
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
/*      */   void resize(int paramInt)
/*      */   {
/*  485 */     Entry[] arrayOfEntry1 = getTable();
/*  486 */     int i = arrayOfEntry1.length;
/*  487 */     if (i == 1073741824) {
/*  488 */       this.threshold = Integer.MAX_VALUE;
/*  489 */       return;
/*      */     }
/*      */     
/*  492 */     Entry[] arrayOfEntry2 = newTable(paramInt);
/*  493 */     transfer(arrayOfEntry1, arrayOfEntry2);
/*  494 */     this.table = arrayOfEntry2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  501 */     if (this.size >= this.threshold / 2) {
/*  502 */       this.threshold = ((int)(paramInt * this.loadFactor));
/*      */     } else {
/*  504 */       expungeStaleEntries();
/*  505 */       transfer(arrayOfEntry2, arrayOfEntry1);
/*  506 */       this.table = arrayOfEntry1;
/*      */     }
/*      */   }
/*      */   
/*      */   private void transfer(Entry<K, V>[] paramArrayOfEntry1, Entry<K, V>[] paramArrayOfEntry2)
/*      */   {
/*  512 */     for (int i = 0; i < paramArrayOfEntry1.length; i++) {
/*  513 */       Object localObject1 = paramArrayOfEntry1[i];
/*  514 */       paramArrayOfEntry1[i] = null;
/*  515 */       while (localObject1 != null) {
/*  516 */         Entry localEntry = ((Entry)localObject1).next;
/*  517 */         Object localObject2 = ((Entry)localObject1).get();
/*  518 */         if (localObject2 == null) {
/*  519 */           ((Entry)localObject1).next = null;
/*  520 */           ((Entry)localObject1).value = null;
/*  521 */           this.size -= 1;
/*      */         } else {
/*  523 */           int j = indexFor(((Entry)localObject1).hash, paramArrayOfEntry2.length);
/*  524 */           ((Entry)localObject1).next = paramArrayOfEntry2[j];
/*  525 */           paramArrayOfEntry2[j] = localObject1;
/*      */         }
/*  527 */         localObject1 = localEntry;
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
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  541 */     int i = paramMap.size();
/*  542 */     if (i == 0) {
/*  543 */       return;
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
/*  554 */     if (i > this.threshold) {
/*  555 */       int j = (int)(i / this.loadFactor + 1.0F);
/*  556 */       if (j > 1073741824)
/*  557 */         j = 1073741824;
/*  558 */       int k = this.table.length;
/*  559 */       while (k < j)
/*  560 */         k <<= 1;
/*  561 */       if (k > this.table.length) {
/*  562 */         resize(k);
/*      */       }
/*      */     }
/*  565 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  566 */       put(localEntry.getKey(), localEntry.getValue());
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
/*      */   public V remove(Object paramObject)
/*      */   {
/*  590 */     Object localObject1 = maskNull(paramObject);
/*  591 */     int i = hash(localObject1);
/*  592 */     Entry[] arrayOfEntry = getTable();
/*  593 */     int j = indexFor(i, arrayOfEntry.length);
/*  594 */     Object localObject2 = arrayOfEntry[j];
/*  595 */     Object localObject3 = localObject2;
/*      */     
/*  597 */     while (localObject3 != null) {
/*  598 */       Entry localEntry = ((Entry)localObject3).next;
/*  599 */       if ((i == ((Entry)localObject3).hash) && (eq(localObject1, ((Entry)localObject3).get()))) {
/*  600 */         this.modCount += 1;
/*  601 */         this.size -= 1;
/*  602 */         if (localObject2 == localObject3) {
/*  603 */           arrayOfEntry[j] = localEntry;
/*      */         } else
/*  605 */           ((Entry)localObject2).next = localEntry;
/*  606 */         return (V)((Entry)localObject3).value;
/*      */       }
/*  608 */       localObject2 = localObject3;
/*  609 */       localObject3 = localEntry;
/*      */     }
/*      */     
/*  612 */     return null;
/*      */   }
/*      */   
/*      */   boolean removeMapping(Object paramObject)
/*      */   {
/*  617 */     if (!(paramObject instanceof Map.Entry))
/*  618 */       return false;
/*  619 */     Entry[] arrayOfEntry = getTable();
/*  620 */     Map.Entry localEntry = (Map.Entry)paramObject;
/*  621 */     Object localObject1 = maskNull(localEntry.getKey());
/*  622 */     int i = hash(localObject1);
/*  623 */     int j = indexFor(i, arrayOfEntry.length);
/*  624 */     Object localObject2 = arrayOfEntry[j];
/*  625 */     Object localObject3 = localObject2;
/*      */     
/*  627 */     while (localObject3 != null) {
/*  628 */       Entry localEntry1 = ((Entry)localObject3).next;
/*  629 */       if ((i == ((Entry)localObject3).hash) && (((Entry)localObject3).equals(localEntry))) {
/*  630 */         this.modCount += 1;
/*  631 */         this.size -= 1;
/*  632 */         if (localObject2 == localObject3) {
/*  633 */           arrayOfEntry[j] = localEntry1;
/*      */         } else
/*  635 */           ((Entry)localObject2).next = localEntry1;
/*  636 */         return true;
/*      */       }
/*  638 */       localObject2 = localObject3;
/*  639 */       localObject3 = localEntry1;
/*      */     }
/*      */     
/*  642 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  652 */     while (this.queue.poll() != null) {}
/*      */     
/*      */ 
/*  655 */     this.modCount += 1;
/*  656 */     Arrays.fill(this.table, null);
/*  657 */     this.size = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  662 */     while (this.queue.poll() != null) {}
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
/*  675 */     if (paramObject == null) {
/*  676 */       return containsNullValue();
/*      */     }
/*  678 */     Entry[] arrayOfEntry = getTable();
/*  679 */     for (int i = arrayOfEntry.length; i-- > 0;)
/*  680 */       for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next)
/*  681 */         if (paramObject.equals(localEntry.value))
/*  682 */           return true;
/*  683 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean containsNullValue()
/*      */   {
/*  690 */     Entry[] arrayOfEntry = getTable();
/*  691 */     for (int i = arrayOfEntry.length; i-- > 0;)
/*  692 */       for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next)
/*  693 */         if (localEntry.value == null)
/*  694 */           return true;
/*  695 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class Entry<K, V>
/*      */     extends WeakReference<Object>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     V value;
/*      */     
/*      */     final int hash;
/*      */     
/*      */     Entry<K, V> next;
/*      */     
/*      */ 
/*      */     Entry(Object paramObject, V paramV, ReferenceQueue<Object> paramReferenceQueue, int paramInt, Entry<K, V> paramEntry)
/*      */     {
/*  713 */       super(paramReferenceQueue);
/*  714 */       this.value = paramV;
/*  715 */       this.hash = paramInt;
/*  716 */       this.next = paramEntry;
/*      */     }
/*      */     
/*      */     public K getKey()
/*      */     {
/*  721 */       return (K)WeakHashMap.unmaskNull(get());
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  725 */       return (V)this.value;
/*      */     }
/*      */     
/*      */     public V setValue(V paramV) {
/*  729 */       Object localObject = this.value;
/*  730 */       this.value = paramV;
/*  731 */       return (V)localObject;
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/*  735 */       if (!(paramObject instanceof Map.Entry))
/*  736 */         return false;
/*  737 */       Map.Entry localEntry = (Map.Entry)paramObject;
/*  738 */       Object localObject1 = getKey();
/*  739 */       Object localObject2 = localEntry.getKey();
/*  740 */       if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2)))) {
/*  741 */         Object localObject3 = getValue();
/*  742 */         Object localObject4 = localEntry.getValue();
/*  743 */         if ((localObject3 == localObject4) || ((localObject3 != null) && (localObject3.equals(localObject4))))
/*  744 */           return true;
/*      */       }
/*  746 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  750 */       Object localObject1 = getKey();
/*  751 */       Object localObject2 = getValue();
/*  752 */       return Objects.hashCode(localObject1) ^ Objects.hashCode(localObject2);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  756 */       return getKey() + "=" + getValue();
/*      */     }
/*      */   }
/*      */   
/*      */   private abstract class HashIterator<T> implements Iterator<T> {
/*      */     private int index;
/*      */     private WeakHashMap.Entry<K, V> entry;
/*      */     private WeakHashMap.Entry<K, V> lastReturned;
/*  764 */     private int expectedModCount = WeakHashMap.this.modCount;
/*      */     
/*      */ 
/*      */ 
/*      */     private Object nextKey;
/*      */     
/*      */ 
/*      */ 
/*      */     private Object currentKey;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     HashIterator()
/*      */     {
/*  779 */       this.index = (WeakHashMap.this.isEmpty() ? 0 : WeakHashMap.this.table.length);
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  783 */       WeakHashMap.Entry[] arrayOfEntry = WeakHashMap.this.table;
/*      */       
/*  785 */       while (this.nextKey == null) {
/*  786 */         WeakHashMap.Entry localEntry = this.entry;
/*  787 */         int i = this.index;
/*  788 */         while ((localEntry == null) && (i > 0))
/*  789 */           localEntry = arrayOfEntry[(--i)];
/*  790 */         this.entry = localEntry;
/*  791 */         this.index = i;
/*  792 */         if (localEntry == null) {
/*  793 */           this.currentKey = null;
/*  794 */           return false;
/*      */         }
/*  796 */         this.nextKey = localEntry.get();
/*  797 */         if (this.nextKey == null)
/*  798 */           this.entry = this.entry.next;
/*      */       }
/*  800 */       return true;
/*      */     }
/*      */     
/*      */     protected WeakHashMap.Entry<K, V> nextEntry()
/*      */     {
/*  805 */       if (WeakHashMap.this.modCount != this.expectedModCount)
/*  806 */         throw new ConcurrentModificationException();
/*  807 */       if ((this.nextKey == null) && (!hasNext())) {
/*  808 */         throw new NoSuchElementException();
/*      */       }
/*  810 */       this.lastReturned = this.entry;
/*  811 */       this.entry = this.entry.next;
/*  812 */       this.currentKey = this.nextKey;
/*  813 */       this.nextKey = null;
/*  814 */       return this.lastReturned;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  818 */       if (this.lastReturned == null)
/*  819 */         throw new IllegalStateException();
/*  820 */       if (WeakHashMap.this.modCount != this.expectedModCount) {
/*  821 */         throw new ConcurrentModificationException();
/*      */       }
/*  823 */       WeakHashMap.this.remove(this.currentKey);
/*  824 */       this.expectedModCount = WeakHashMap.this.modCount;
/*  825 */       this.lastReturned = null;
/*  826 */       this.currentKey = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class ValueIterator extends WeakHashMap<K, V>.HashIterator<V> {
/*  831 */     private ValueIterator() { super(); }
/*      */     
/*  833 */     public V next() { return (V)nextEntry().value; }
/*      */   }
/*      */   
/*      */   private class KeyIterator extends WeakHashMap<K, V>.HashIterator<K> {
/*  837 */     private KeyIterator() { super(); }
/*      */     
/*  839 */     public K next() { return (K)nextEntry().getKey(); }
/*      */   }
/*      */   
/*      */   private class EntryIterator extends WeakHashMap<K, V>.HashIterator<Map.Entry<K, V>> {
/*  843 */     private EntryIterator() { super(); }
/*      */     
/*  845 */     public Map.Entry<K, V> next() { return nextEntry(); }
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
/*      */   public Set<K> keySet()
/*      */   {
/*  867 */     Set localSet = this.keySet;
/*  868 */     return localSet != null ? localSet : (this.keySet = new KeySet(null));
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*      */     
/*  873 */     public Iterator<K> iterator() { return new WeakHashMap.KeyIterator(WeakHashMap.this, null); }
/*      */     
/*      */     public int size()
/*      */     {
/*  877 */       return WeakHashMap.this.size();
/*      */     }
/*      */     
/*      */     public boolean contains(Object paramObject) {
/*  881 */       return WeakHashMap.this.containsKey(paramObject);
/*      */     }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/*  885 */       if (WeakHashMap.this.containsKey(paramObject)) {
/*  886 */         WeakHashMap.this.remove(paramObject);
/*  887 */         return true;
/*      */       }
/*      */       
/*  890 */       return false;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  894 */       WeakHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public Spliterator<K> spliterator() {
/*  898 */       return new WeakHashMap.KeySpliterator(WeakHashMap.this, 0, -1, 0, 0);
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
/*      */   public Collection<V> values()
/*      */   {
/*  916 */     Collection localCollection = this.values;
/*  917 */     return localCollection != null ? localCollection : (this.values = new Values(null));
/*      */   }
/*      */   
/*      */   private class Values extends AbstractCollection<V> { private Values() {}
/*      */     
/*  922 */     public Iterator<V> iterator() { return new WeakHashMap.ValueIterator(WeakHashMap.this, null); }
/*      */     
/*      */     public int size()
/*      */     {
/*  926 */       return WeakHashMap.this.size();
/*      */     }
/*      */     
/*      */     public boolean contains(Object paramObject) {
/*  930 */       return WeakHashMap.this.containsValue(paramObject);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  934 */       WeakHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public Spliterator<V> spliterator() {
/*  938 */       return new WeakHashMap.ValueSpliterator(WeakHashMap.this, 0, -1, 0, 0);
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
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/*  957 */     Set localSet = this.entrySet;
/*  958 */     return localSet != null ? localSet : (this.entrySet = new EntrySet(null));
/*      */   }
/*      */   
/*      */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */     
/*  963 */     public Iterator<Map.Entry<K, V>> iterator() { return new WeakHashMap.EntryIterator(WeakHashMap.this, null); }
/*      */     
/*      */     public boolean contains(Object paramObject)
/*      */     {
/*  967 */       if (!(paramObject instanceof Map.Entry))
/*  968 */         return false;
/*  969 */       Map.Entry localEntry = (Map.Entry)paramObject;
/*  970 */       WeakHashMap.Entry localEntry1 = WeakHashMap.this.getEntry(localEntry.getKey());
/*  971 */       return (localEntry1 != null) && (localEntry1.equals(localEntry));
/*      */     }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/*  975 */       return WeakHashMap.this.removeMapping(paramObject);
/*      */     }
/*      */     
/*      */     public int size() {
/*  979 */       return WeakHashMap.this.size();
/*      */     }
/*      */     
/*      */     public void clear() {
/*  983 */       WeakHashMap.this.clear();
/*      */     }
/*      */     
/*      */     private List<Map.Entry<K, V>> deepCopy() {
/*  987 */       ArrayList localArrayList = new ArrayList(size());
/*  988 */       for (Map.Entry localEntry : this)
/*  989 */         localArrayList.add(new AbstractMap.SimpleEntry(localEntry));
/*  990 */       return localArrayList;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  994 */       return deepCopy().toArray();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] paramArrayOfT) {
/*  998 */       return deepCopy().toArray(paramArrayOfT);
/*      */     }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1002 */       return new WeakHashMap.EntrySpliterator(WeakHashMap.this, 0, -1, 0, 0);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer)
/*      */   {
/* 1009 */     Objects.requireNonNull(paramBiConsumer);
/* 1010 */     int i = this.modCount;
/*      */     
/* 1012 */     Entry[] arrayOfEntry1 = getTable();
/* 1013 */     for (Entry localEntry : arrayOfEntry1) {
/* 1014 */       while (localEntry != null) {
/* 1015 */         Object localObject = localEntry.get();
/* 1016 */         if (localObject != null) {
/* 1017 */           paramBiConsumer.accept(unmaskNull(localObject), localEntry.value);
/*      */         }
/* 1019 */         localEntry = localEntry.next;
/*      */         
/* 1021 */         if (i != this.modCount) {
/* 1022 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1031 */     Objects.requireNonNull(paramBiFunction);
/* 1032 */     int i = this.modCount;
/*      */     
/* 1034 */     Entry[] arrayOfEntry1 = getTable();
/* 1035 */     for (Entry localEntry : arrayOfEntry1) {
/* 1036 */       while (localEntry != null) {
/* 1037 */         Object localObject = localEntry.get();
/* 1038 */         if (localObject != null) {
/* 1039 */           localEntry.value = paramBiFunction.apply(unmaskNull(localObject), localEntry.value);
/*      */         }
/* 1041 */         localEntry = localEntry.next;
/*      */         
/* 1043 */         if (i != this.modCount) {
/* 1044 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static class WeakHashMapSpliterator<K, V>
/*      */   {
/*      */     final WeakHashMap<K, V> map;
/*      */     
/*      */     WeakHashMap.Entry<K, V> current;
/*      */     
/*      */     int index;
/*      */     
/*      */     int fence;
/*      */     int est;
/*      */     int expectedModCount;
/*      */     
/*      */     WeakHashMapSpliterator(WeakHashMap<K, V> paramWeakHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1065 */       this.map = paramWeakHashMap;
/* 1066 */       this.index = paramInt1;
/* 1067 */       this.fence = paramInt2;
/* 1068 */       this.est = paramInt3;
/* 1069 */       this.expectedModCount = paramInt4;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/* 1074 */       if ((i = this.fence) < 0) {
/* 1075 */         WeakHashMap localWeakHashMap = this.map;
/* 1076 */         this.est = localWeakHashMap.size();
/* 1077 */         this.expectedModCount = localWeakHashMap.modCount;
/* 1078 */         i = this.fence = localWeakHashMap.table.length;
/*      */       }
/* 1080 */       return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 1084 */       getFence();
/* 1085 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends WeakHashMap.WeakHashMapSpliterator<K, V> implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(WeakHashMap<K, V> paramWeakHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1094 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 1098 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1099 */       return j >= k ? null : new KeySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super K> paramConsumer)
/*      */     {
/* 1106 */       if (paramConsumer == null)
/* 1107 */         throw new NullPointerException();
/* 1108 */       WeakHashMap localWeakHashMap = this.map;
/* 1109 */       WeakHashMap.Entry[] arrayOfEntry = localWeakHashMap.table;
/* 1110 */       int j; int k; if ((j = this.fence) < 0) {
/* 1111 */         k = this.expectedModCount = localWeakHashMap.modCount;
/* 1112 */         j = this.fence = arrayOfEntry.length;
/*      */       }
/*      */       else {
/* 1115 */         k = this.expectedModCount; }
/* 1116 */       int i; if ((arrayOfEntry.length >= j) && ((i = this.index) >= 0) && ((i < (this.index = j)) || (this.current != null)))
/*      */       {
/* 1118 */         WeakHashMap.Entry localEntry = this.current;
/* 1119 */         this.current = null;
/*      */         do {
/* 1121 */           if (localEntry == null) {
/* 1122 */             localEntry = arrayOfEntry[(i++)];
/*      */           } else {
/* 1124 */             Object localObject1 = localEntry.get();
/* 1125 */             localEntry = localEntry.next;
/* 1126 */             if (localObject1 != null)
/*      */             {
/* 1128 */               Object localObject2 = WeakHashMap.unmaskNull(localObject1);
/* 1129 */               paramConsumer.accept(localObject2);
/*      */             }
/*      */           }
/* 1132 */         } while ((localEntry != null) || (i < j));
/*      */       }
/* 1134 */       if (localWeakHashMap.modCount != k) {
/* 1135 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> paramConsumer) {
/* 1140 */       if (paramConsumer == null)
/* 1141 */         throw new NullPointerException();
/* 1142 */       WeakHashMap.Entry[] arrayOfEntry = this.map.table;
/* 1143 */       int i; if ((arrayOfEntry.length >= (i = getFence())) && (this.index >= 0)) {
/* 1144 */         while ((this.current != null) || (this.index < i)) {
/* 1145 */           if (this.current == null) {
/* 1146 */             this.current = arrayOfEntry[(this.index++)];
/*      */           } else {
/* 1148 */             Object localObject1 = this.current.get();
/* 1149 */             this.current = this.current.next;
/* 1150 */             if (localObject1 != null)
/*      */             {
/* 1152 */               Object localObject2 = WeakHashMap.unmaskNull(localObject1);
/* 1153 */               paramConsumer.accept(localObject2);
/* 1154 */               if (this.map.modCount != this.expectedModCount)
/* 1155 */                 throw new ConcurrentModificationException();
/* 1156 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1161 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1165 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends WeakHashMap.WeakHashMapSpliterator<K, V> implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(WeakHashMap<K, V> paramWeakHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1174 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 1178 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1179 */       return j >= k ? null : new ValueSpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super V> paramConsumer)
/*      */     {
/* 1186 */       if (paramConsumer == null)
/* 1187 */         throw new NullPointerException();
/* 1188 */       WeakHashMap localWeakHashMap = this.map;
/* 1189 */       WeakHashMap.Entry[] arrayOfEntry = localWeakHashMap.table;
/* 1190 */       int j; int k; if ((j = this.fence) < 0) {
/* 1191 */         k = this.expectedModCount = localWeakHashMap.modCount;
/* 1192 */         j = this.fence = arrayOfEntry.length;
/*      */       }
/*      */       else {
/* 1195 */         k = this.expectedModCount; }
/* 1196 */       int i; if ((arrayOfEntry.length >= j) && ((i = this.index) >= 0) && ((i < (this.index = j)) || (this.current != null)))
/*      */       {
/* 1198 */         WeakHashMap.Entry localEntry = this.current;
/* 1199 */         this.current = null;
/*      */         do {
/* 1201 */           if (localEntry == null) {
/* 1202 */             localEntry = arrayOfEntry[(i++)];
/*      */           } else {
/* 1204 */             Object localObject1 = localEntry.get();
/* 1205 */             Object localObject2 = localEntry.value;
/* 1206 */             localEntry = localEntry.next;
/* 1207 */             if (localObject1 != null)
/* 1208 */               paramConsumer.accept(localObject2);
/*      */           }
/* 1210 */         } while ((localEntry != null) || (i < j));
/*      */       }
/* 1212 */       if (localWeakHashMap.modCount != k) {
/* 1213 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> paramConsumer) {
/* 1218 */       if (paramConsumer == null)
/* 1219 */         throw new NullPointerException();
/* 1220 */       WeakHashMap.Entry[] arrayOfEntry = this.map.table;
/* 1221 */       int i; if ((arrayOfEntry.length >= (i = getFence())) && (this.index >= 0)) {
/* 1222 */         while ((this.current != null) || (this.index < i)) {
/* 1223 */           if (this.current == null) {
/* 1224 */             this.current = arrayOfEntry[(this.index++)];
/*      */           } else {
/* 1226 */             Object localObject1 = this.current.get();
/* 1227 */             Object localObject2 = this.current.value;
/* 1228 */             this.current = this.current.next;
/* 1229 */             if (localObject1 != null) {
/* 1230 */               paramConsumer.accept(localObject2);
/* 1231 */               if (this.map.modCount != this.expectedModCount)
/* 1232 */                 throw new ConcurrentModificationException();
/* 1233 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1238 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1242 */       return 0;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends WeakHashMap.WeakHashMapSpliterator<K, V> implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(WeakHashMap<K, V> paramWeakHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1251 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 1255 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1256 */       return j >= k ? null : new EntrySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */     {
/* 1264 */       if (paramConsumer == null)
/* 1265 */         throw new NullPointerException();
/* 1266 */       WeakHashMap localWeakHashMap = this.map;
/* 1267 */       WeakHashMap.Entry[] arrayOfEntry = localWeakHashMap.table;
/* 1268 */       int j; int k; if ((j = this.fence) < 0) {
/* 1269 */         k = this.expectedModCount = localWeakHashMap.modCount;
/* 1270 */         j = this.fence = arrayOfEntry.length;
/*      */       }
/*      */       else {
/* 1273 */         k = this.expectedModCount; }
/* 1274 */       int i; if ((arrayOfEntry.length >= j) && ((i = this.index) >= 0) && ((i < (this.index = j)) || (this.current != null)))
/*      */       {
/* 1276 */         WeakHashMap.Entry localEntry = this.current;
/* 1277 */         this.current = null;
/*      */         do {
/* 1279 */           if (localEntry == null) {
/* 1280 */             localEntry = arrayOfEntry[(i++)];
/*      */           } else {
/* 1282 */             Object localObject1 = localEntry.get();
/* 1283 */             Object localObject2 = localEntry.value;
/* 1284 */             localEntry = localEntry.next;
/* 1285 */             if (localObject1 != null)
/*      */             {
/* 1287 */               Object localObject3 = WeakHashMap.unmaskNull(localObject1);
/* 1288 */               paramConsumer
/* 1289 */                 .accept(new AbstractMap.SimpleImmutableEntry(localObject3, localObject2));
/*      */             }
/*      */           }
/* 1292 */         } while ((localEntry != null) || (i < j));
/*      */       }
/* 1294 */       if (localWeakHashMap.modCount != k) {
/* 1295 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> paramConsumer) {
/* 1300 */       if (paramConsumer == null)
/* 1301 */         throw new NullPointerException();
/* 1302 */       WeakHashMap.Entry[] arrayOfEntry = this.map.table;
/* 1303 */       int i; if ((arrayOfEntry.length >= (i = getFence())) && (this.index >= 0)) {
/* 1304 */         while ((this.current != null) || (this.index < i)) {
/* 1305 */           if (this.current == null) {
/* 1306 */             this.current = arrayOfEntry[(this.index++)];
/*      */           } else {
/* 1308 */             Object localObject1 = this.current.get();
/* 1309 */             Object localObject2 = this.current.value;
/* 1310 */             this.current = this.current.next;
/* 1311 */             if (localObject1 != null)
/*      */             {
/* 1313 */               Object localObject3 = WeakHashMap.unmaskNull(localObject1);
/* 1314 */               paramConsumer
/* 1315 */                 .accept(new AbstractMap.SimpleImmutableEntry(localObject3, localObject2));
/* 1316 */               if (this.map.modCount != this.expectedModCount)
/* 1317 */                 throw new ConcurrentModificationException();
/* 1318 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1323 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1327 */       return 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/WeakHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */