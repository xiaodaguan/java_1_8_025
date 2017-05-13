/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Hashtable<K, V>
/*      */   extends Dictionary<K, V>
/*      */   implements Map<K, V>, Cloneable, Serializable
/*      */ {
/*      */   private transient Entry<?, ?>[] table;
/*      */   private transient int count;
/*      */   private int threshold;
/*      */   private float loadFactor;
/*  166 */   private transient int modCount = 0;
/*      */   
/*      */   private static final long serialVersionUID = 1421746759512286392L;
/*      */   
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   
/*      */   private volatile transient Set<K> keySet;
/*      */   private volatile transient Set<Map.Entry<K, V>> entrySet;
/*      */   private volatile transient Collection<V> values;
/*      */   private static final int KEYS = 0;
/*      */   private static final int VALUES = 1;
/*      */   private static final int ENTRIES = 2;
/*      */   
/*      */   public Hashtable(int paramInt, float paramFloat)
/*      */   {
/*  181 */     if (paramInt < 0) {
/*  182 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
/*      */     }
/*  184 */     if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat))) {
/*  185 */       throw new IllegalArgumentException("Illegal Load: " + paramFloat);
/*      */     }
/*  187 */     if (paramInt == 0)
/*  188 */       paramInt = 1;
/*  189 */     this.loadFactor = paramFloat;
/*  190 */     this.table = new Entry[paramInt];
/*  191 */     this.threshold = ((int)Math.min(paramInt * paramFloat, 2.14748365E9F));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Hashtable(int paramInt)
/*      */   {
/*  203 */     this(paramInt, 0.75F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Hashtable()
/*      */   {
/*  211 */     this(11, 0.75F);
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
/*      */   public Hashtable(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  224 */     this(Math.max(2 * paramMap.size(), 11), 0.75F);
/*  225 */     putAll(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int size()
/*      */   {
/*  234 */     return this.count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isEmpty()
/*      */   {
/*  244 */     return this.count == 0;
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
/*      */   public synchronized Enumeration<K> keys()
/*      */   {
/*  257 */     return getEnumeration(0);
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
/*      */   public synchronized Enumeration<V> elements()
/*      */   {
/*  272 */     return getEnumeration(1);
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
/*      */   public synchronized boolean contains(Object paramObject)
/*      */   {
/*  292 */     if (paramObject == null) {
/*  293 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  296 */     Entry[] arrayOfEntry = this.table;
/*  297 */     for (int i = arrayOfEntry.length; i-- > 0;) {
/*  298 */       for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next) {
/*  299 */         if (localEntry.value.equals(paramObject)) {
/*  300 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*  304 */     return false;
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
/*      */   public boolean containsValue(Object paramObject)
/*      */   {
/*  320 */     return contains(paramObject);
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
/*      */   public synchronized boolean containsKey(Object paramObject)
/*      */   {
/*  334 */     Entry[] arrayOfEntry = this.table;
/*  335 */     int i = paramObject.hashCode();
/*  336 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*  337 */     for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next) {
/*  338 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramObject))) {
/*  339 */         return true;
/*      */       }
/*      */     }
/*  342 */     return false;
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
/*      */   public synchronized V get(Object paramObject)
/*      */   {
/*  362 */     Entry[] arrayOfEntry = this.table;
/*  363 */     int i = paramObject.hashCode();
/*  364 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*  365 */     for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next) {
/*  366 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramObject))) {
/*  367 */         return (V)localEntry.value;
/*      */       }
/*      */     }
/*  370 */     return null;
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
/*      */   protected void rehash()
/*      */   {
/*  390 */     int i = this.table.length;
/*  391 */     Entry[] arrayOfEntry1 = this.table;
/*      */     
/*      */ 
/*  394 */     int j = (i << 1) + 1;
/*  395 */     if (j - 2147483639 > 0) {
/*  396 */       if (i == 2147483639)
/*      */       {
/*  398 */         return; }
/*  399 */       j = 2147483639;
/*      */     }
/*  401 */     Entry[] arrayOfEntry2 = new Entry[j];
/*      */     
/*  403 */     this.modCount += 1;
/*  404 */     this.threshold = ((int)Math.min(j * this.loadFactor, 2.14748365E9F));
/*  405 */     this.table = arrayOfEntry2;
/*      */     
/*  407 */     for (int k = i; k-- > 0;)
/*  408 */       for (localEntry1 = arrayOfEntry1[k]; localEntry1 != null;) {
/*  409 */         Entry localEntry2 = localEntry1;
/*  410 */         localEntry1 = localEntry1.next;
/*      */         
/*  412 */         int m = (localEntry2.hash & 0x7FFFFFFF) % j;
/*  413 */         localEntry2.next = arrayOfEntry2[m];
/*  414 */         arrayOfEntry2[m] = localEntry2;
/*      */       }
/*      */     Entry localEntry1;
/*      */   }
/*      */   
/*      */   private void addEntry(int paramInt1, K paramK, V paramV, int paramInt2) {
/*  420 */     this.modCount += 1;
/*      */     
/*  422 */     Entry[] arrayOfEntry = this.table;
/*  423 */     if (this.count >= this.threshold)
/*      */     {
/*  425 */       rehash();
/*      */       
/*  427 */       arrayOfEntry = this.table;
/*  428 */       paramInt1 = paramK.hashCode();
/*  429 */       paramInt2 = (paramInt1 & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  434 */     Entry localEntry = arrayOfEntry[paramInt2];
/*  435 */     arrayOfEntry[paramInt2] = new Entry(paramInt1, paramK, paramV, localEntry);
/*  436 */     this.count += 1;
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
/*      */   public synchronized V put(K paramK, V paramV)
/*      */   {
/*  458 */     if (paramV == null) {
/*  459 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/*  463 */     Entry[] arrayOfEntry = this.table;
/*  464 */     int i = paramK.hashCode();
/*  465 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/*  467 */     for (Entry localEntry = arrayOfEntry[j]; 
/*  468 */         localEntry != null; localEntry = localEntry.next) {
/*  469 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramK))) {
/*  470 */         Object localObject = localEntry.value;
/*  471 */         localEntry.value = paramV;
/*  472 */         return (V)localObject;
/*      */       }
/*      */     }
/*      */     
/*  476 */     addEntry(i, paramK, paramV, j);
/*  477 */     return null;
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
/*      */   public synchronized V remove(Object paramObject)
/*      */   {
/*  490 */     Entry[] arrayOfEntry = this.table;
/*  491 */     int i = paramObject.hashCode();
/*  492 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/*  494 */     Entry localEntry1 = arrayOfEntry[j];
/*  495 */     for (Entry localEntry2 = null; localEntry1 != null; localEntry1 = localEntry1.next) {
/*  496 */       if ((localEntry1.hash == i) && (localEntry1.key.equals(paramObject))) {
/*  497 */         this.modCount += 1;
/*  498 */         if (localEntry2 != null) {
/*  499 */           localEntry2.next = localEntry1.next;
/*      */         } else {
/*  501 */           arrayOfEntry[j] = localEntry1.next;
/*      */         }
/*  503 */         this.count -= 1;
/*  504 */         Object localObject = localEntry1.value;
/*  505 */         localEntry1.value = null;
/*  506 */         return (V)localObject;
/*      */       }
/*  495 */       localEntry2 = localEntry1;
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
/*  509 */     return null;
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
/*      */   public synchronized void putAll(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  522 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  523 */       put(localEntry.getKey(), localEntry.getValue());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized void clear()
/*      */   {
/*  530 */     Entry[] arrayOfEntry = this.table;
/*  531 */     this.modCount += 1;
/*  532 */     int i = arrayOfEntry.length; for (;;) { i--; if (i < 0) break;
/*  533 */       arrayOfEntry[i] = null; }
/*  534 */     this.count = 0;
/*      */   }
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
/*  546 */       Hashtable localHashtable = (Hashtable)super.clone();
/*  547 */       localHashtable.table = new Entry[this.table.length];
/*  548 */       for (int i = this.table.length; i-- > 0;)
/*      */       {
/*  550 */         localHashtable.table[i] = (this.table[i] != null ? (Entry)this.table[i].clone() : null);
/*      */       }
/*  552 */       localHashtable.keySet = null;
/*  553 */       localHashtable.entrySet = null;
/*  554 */       localHashtable.values = null;
/*  555 */       localHashtable.modCount = 0;
/*  556 */       return localHashtable;
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  559 */       throw new InternalError(localCloneNotSupportedException);
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
/*      */   public synchronized String toString()
/*      */   {
/*  574 */     int i = size() - 1;
/*  575 */     if (i == -1) {
/*  576 */       return "{}";
/*      */     }
/*  578 */     StringBuilder localStringBuilder = new StringBuilder();
/*  579 */     Iterator localIterator = entrySet().iterator();
/*      */     
/*  581 */     localStringBuilder.append('{');
/*  582 */     for (int j = 0;; j++) {
/*  583 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  584 */       Object localObject1 = localEntry.getKey();
/*  585 */       Object localObject2 = localEntry.getValue();
/*  586 */       localStringBuilder.append(localObject1 == this ? "(this Map)" : localObject1.toString());
/*  587 */       localStringBuilder.append('=');
/*  588 */       localStringBuilder.append(localObject2 == this ? "(this Map)" : localObject2.toString());
/*      */       
/*  590 */       if (j == i)
/*  591 */         return '}';
/*  592 */       localStringBuilder.append(", ");
/*      */     }
/*      */   }
/*      */   
/*      */   private <T> Enumeration<T> getEnumeration(int paramInt)
/*      */   {
/*  598 */     if (this.count == 0) {
/*  599 */       return Collections.emptyEnumeration();
/*      */     }
/*  601 */     return new Enumerator(paramInt, false);
/*      */   }
/*      */   
/*      */   private <T> Iterator<T> getIterator(int paramInt)
/*      */   {
/*  606 */     if (this.count == 0) {
/*  607 */       return Collections.emptyIterator();
/*      */     }
/*  609 */     return new Enumerator(paramInt, true);
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
/*      */   public Set<K> keySet()
/*      */   {
/*  640 */     if (this.keySet == null)
/*  641 */       this.keySet = Collections.synchronizedSet(new KeySet(null), this);
/*  642 */     return this.keySet;
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*      */     
/*  647 */     public Iterator<K> iterator() { return Hashtable.this.getIterator(0); }
/*      */     
/*      */     public int size() {
/*  650 */       return Hashtable.this.count;
/*      */     }
/*      */     
/*  653 */     public boolean contains(Object paramObject) { return Hashtable.this.containsKey(paramObject); }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/*  656 */       return Hashtable.this.remove(paramObject) != null;
/*      */     }
/*      */     
/*  659 */     public void clear() { Hashtable.this.clear(); }
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
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/*  680 */     if (this.entrySet == null)
/*  681 */       this.entrySet = Collections.synchronizedSet(new EntrySet(null), this);
/*  682 */     return this.entrySet;
/*      */   }
/*      */   
/*      */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*      */     
/*  687 */     public Iterator<Map.Entry<K, V>> iterator() { return Hashtable.this.getIterator(2); }
/*      */     
/*      */     public boolean add(Map.Entry<K, V> paramEntry)
/*      */     {
/*  691 */       return super.add(paramEntry);
/*      */     }
/*      */     
/*      */     public boolean contains(Object paramObject) {
/*  695 */       if (!(paramObject instanceof Map.Entry))
/*  696 */         return false;
/*  697 */       Map.Entry localEntry = (Map.Entry)paramObject;
/*  698 */       Object localObject = localEntry.getKey();
/*  699 */       Hashtable.Entry[] arrayOfEntry = Hashtable.this.table;
/*  700 */       int i = localObject.hashCode();
/*  701 */       int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */       
/*  703 */       for (Hashtable.Entry localEntry1 = arrayOfEntry[j]; localEntry1 != null; localEntry1 = localEntry1.next)
/*  704 */         if ((localEntry1.hash == i) && (localEntry1.equals(localEntry)))
/*  705 */           return true;
/*  706 */       return false;
/*      */     }
/*      */     
/*      */     public boolean remove(Object paramObject) {
/*  710 */       if (!(paramObject instanceof Map.Entry))
/*  711 */         return false;
/*  712 */       Map.Entry localEntry = (Map.Entry)paramObject;
/*  713 */       Object localObject = localEntry.getKey();
/*  714 */       Hashtable.Entry[] arrayOfEntry = Hashtable.this.table;
/*  715 */       int i = localObject.hashCode();
/*  716 */       int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */       
/*      */ 
/*  719 */       Hashtable.Entry localEntry1 = arrayOfEntry[j];
/*  720 */       for (Hashtable.Entry localEntry2 = null; localEntry1 != null; localEntry1 = localEntry1.next) {
/*  721 */         if ((localEntry1.hash == i) && (localEntry1.equals(localEntry))) {
/*  722 */           Hashtable.access$508(Hashtable.this);
/*  723 */           if (localEntry2 != null) {
/*  724 */             localEntry2.next = localEntry1.next;
/*      */           } else {
/*  726 */             arrayOfEntry[j] = localEntry1.next;
/*      */           }
/*  728 */           Hashtable.access$210(Hashtable.this);
/*  729 */           localEntry1.value = null;
/*  730 */           return true;
/*      */         }
/*  720 */         localEntry2 = localEntry1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  733 */       return false;
/*      */     }
/*      */     
/*      */     public int size() {
/*  737 */       return Hashtable.this.count;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  741 */       Hashtable.this.clear();
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
/*      */   public Collection<V> values()
/*      */   {
/*  761 */     if (this.values == null) {
/*  762 */       this.values = Collections.synchronizedCollection(new ValueCollection(null), this);
/*      */     }
/*  764 */     return this.values;
/*      */   }
/*      */   
/*      */   private class ValueCollection extends AbstractCollection<V> { private ValueCollection() {}
/*      */     
/*  769 */     public Iterator<V> iterator() { return Hashtable.this.getIterator(1); }
/*      */     
/*      */     public int size() {
/*  772 */       return Hashtable.this.count;
/*      */     }
/*      */     
/*  775 */     public boolean contains(Object paramObject) { return Hashtable.this.containsValue(paramObject); }
/*      */     
/*      */     public void clear() {
/*  778 */       Hashtable.this.clear();
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
/*      */   public synchronized boolean equals(Object paramObject)
/*      */   {
/*  794 */     if (paramObject == this) {
/*  795 */       return true;
/*      */     }
/*  797 */     if (!(paramObject instanceof Map))
/*  798 */       return false;
/*  799 */     Map localMap = (Map)paramObject;
/*  800 */     if (localMap.size() != size()) {
/*  801 */       return false;
/*      */     }
/*      */     try {
/*  804 */       Iterator localIterator = entrySet().iterator();
/*  805 */       while (localIterator.hasNext()) {
/*  806 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  807 */         Object localObject1 = localEntry.getKey();
/*  808 */         Object localObject2 = localEntry.getValue();
/*  809 */         if (localObject2 == null) {
/*  810 */           if ((localMap.get(localObject1) != null) || (!localMap.containsKey(localObject1))) {
/*  811 */             return false;
/*      */           }
/*  813 */         } else if (!localObject2.equals(localMap.get(localObject1))) {
/*  814 */           return false;
/*      */         }
/*      */       }
/*      */     } catch (ClassCastException localClassCastException) {
/*  818 */       return false;
/*      */     } catch (NullPointerException localNullPointerException) {
/*  820 */       return false;
/*      */     }
/*      */     
/*  823 */     return true;
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
/*      */   public synchronized int hashCode()
/*      */   {
/*  844 */     int i = 0;
/*  845 */     if ((this.count == 0) || (this.loadFactor < 0.0F)) {
/*  846 */       return i;
/*      */     }
/*  848 */     this.loadFactor = (-this.loadFactor);
/*  849 */     Entry[] arrayOfEntry1 = this.table;
/*  850 */     for (Entry localEntry : arrayOfEntry1) {
/*  851 */       while (localEntry != null) {
/*  852 */         i += localEntry.hashCode();
/*  853 */         localEntry = localEntry.next;
/*      */       }
/*      */     }
/*      */     
/*  857 */     this.loadFactor = (-this.loadFactor);
/*      */     
/*  859 */     return i;
/*      */   }
/*      */   
/*      */   public synchronized V getOrDefault(Object paramObject, V paramV)
/*      */   {
/*  864 */     Object localObject = get(paramObject);
/*  865 */     return null == localObject ? paramV : localObject;
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized void forEach(BiConsumer<? super K, ? super V> paramBiConsumer)
/*      */   {
/*  871 */     Objects.requireNonNull(paramBiConsumer);
/*      */     
/*  873 */     int i = this.modCount;
/*      */     
/*  875 */     Entry[] arrayOfEntry1 = this.table;
/*  876 */     for (Entry localEntry : arrayOfEntry1) {
/*  877 */       while (localEntry != null) {
/*  878 */         paramBiConsumer.accept(localEntry.key, localEntry.value);
/*  879 */         localEntry = localEntry.next;
/*      */         
/*  881 */         if (i != this.modCount) {
/*  882 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/*  891 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/*  893 */     int i = this.modCount;
/*      */     
/*  895 */     Entry[] arrayOfEntry1 = (Entry[])this.table;
/*  896 */     for (Entry localEntry : arrayOfEntry1) {
/*  897 */       while (localEntry != null) {
/*  898 */         localEntry.value = Objects.requireNonNull(paramBiFunction
/*  899 */           .apply(localEntry.key, localEntry.value));
/*  900 */         localEntry = localEntry.next;
/*      */         
/*  902 */         if (i != this.modCount) {
/*  903 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized V putIfAbsent(K paramK, V paramV)
/*      */   {
/*  911 */     Objects.requireNonNull(paramV);
/*      */     
/*      */ 
/*  914 */     Entry[] arrayOfEntry = this.table;
/*  915 */     int i = paramK.hashCode();
/*  916 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/*  918 */     for (Entry localEntry = arrayOfEntry[j]; 
/*  919 */         localEntry != null; localEntry = localEntry.next) {
/*  920 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramK))) {
/*  921 */         Object localObject = localEntry.value;
/*  922 */         if (localObject == null) {
/*  923 */           localEntry.value = paramV;
/*      */         }
/*  925 */         return (V)localObject;
/*      */       }
/*      */     }
/*      */     
/*  929 */     addEntry(i, paramK, paramV, j);
/*  930 */     return null;
/*      */   }
/*      */   
/*      */   public synchronized boolean remove(Object paramObject1, Object paramObject2)
/*      */   {
/*  935 */     Objects.requireNonNull(paramObject2);
/*      */     
/*  937 */     Entry[] arrayOfEntry = this.table;
/*  938 */     int i = paramObject1.hashCode();
/*  939 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/*  941 */     Entry localEntry1 = arrayOfEntry[j];
/*  942 */     for (Entry localEntry2 = null; localEntry1 != null; localEntry1 = localEntry1.next) {
/*  943 */       if ((localEntry1.hash == i) && (localEntry1.key.equals(paramObject1)) && (localEntry1.value.equals(paramObject2))) {
/*  944 */         this.modCount += 1;
/*  945 */         if (localEntry2 != null) {
/*  946 */           localEntry2.next = localEntry1.next;
/*      */         } else {
/*  948 */           arrayOfEntry[j] = localEntry1.next;
/*      */         }
/*  950 */         this.count -= 1;
/*  951 */         localEntry1.value = null;
/*  952 */         return true;
/*      */       }
/*  942 */       localEntry2 = localEntry1;
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
/*  955 */     return false;
/*      */   }
/*      */   
/*      */   public synchronized boolean replace(K paramK, V paramV1, V paramV2)
/*      */   {
/*  960 */     Objects.requireNonNull(paramV1);
/*  961 */     Objects.requireNonNull(paramV2);
/*  962 */     Entry[] arrayOfEntry = this.table;
/*  963 */     int i = paramK.hashCode();
/*  964 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/*  966 */     for (Entry localEntry = arrayOfEntry[j]; 
/*  967 */         localEntry != null; localEntry = localEntry.next) {
/*  968 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramK))) {
/*  969 */         if (localEntry.value.equals(paramV1)) {
/*  970 */           localEntry.value = paramV2;
/*  971 */           return true;
/*      */         }
/*  973 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  977 */     return false;
/*      */   }
/*      */   
/*      */   public synchronized V replace(K paramK, V paramV)
/*      */   {
/*  982 */     Objects.requireNonNull(paramV);
/*  983 */     Entry[] arrayOfEntry = this.table;
/*  984 */     int i = paramK.hashCode();
/*  985 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/*  987 */     for (Entry localEntry = arrayOfEntry[j]; 
/*  988 */         localEntry != null; localEntry = localEntry.next) {
/*  989 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramK))) {
/*  990 */         Object localObject = localEntry.value;
/*  991 */         localEntry.value = paramV;
/*  992 */         return (V)localObject;
/*      */       }
/*      */     }
/*  995 */     return null;
/*      */   }
/*      */   
/*      */   public synchronized V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction)
/*      */   {
/* 1000 */     Objects.requireNonNull(paramFunction);
/*      */     
/* 1002 */     Entry[] arrayOfEntry = this.table;
/* 1003 */     int i = paramK.hashCode();
/* 1004 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/* 1006 */     for (Entry localEntry = arrayOfEntry[j]; 
/* 1007 */         localEntry != null; localEntry = localEntry.next) {
/* 1008 */       if ((localEntry.hash == i) && (localEntry.key.equals(paramK)))
/*      */       {
/* 1010 */         return (V)localEntry.value;
/*      */       }
/*      */     }
/*      */     
/* 1014 */     Object localObject = paramFunction.apply(paramK);
/* 1015 */     if (localObject != null) {
/* 1016 */       addEntry(i, paramK, localObject, j);
/*      */     }
/*      */     
/* 1019 */     return (V)localObject;
/*      */   }
/*      */   
/*      */   public synchronized V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1024 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/* 1026 */     Entry[] arrayOfEntry = this.table;
/* 1027 */     int i = paramK.hashCode();
/* 1028 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/* 1030 */     Entry localEntry1 = arrayOfEntry[j];
/* 1031 */     for (Entry localEntry2 = null; localEntry1 != null; localEntry1 = localEntry1.next) {
/* 1032 */       if ((localEntry1.hash == i) && (localEntry1.key.equals(paramK))) {
/* 1033 */         Object localObject = paramBiFunction.apply(paramK, localEntry1.value);
/* 1034 */         if (localObject == null) {
/* 1035 */           this.modCount += 1;
/* 1036 */           if (localEntry2 != null) {
/* 1037 */             localEntry2.next = localEntry1.next;
/*      */           } else {
/* 1039 */             arrayOfEntry[j] = localEntry1.next;
/*      */           }
/* 1041 */           this.count -= 1;
/*      */         } else {
/* 1043 */           localEntry1.value = localObject;
/*      */         }
/* 1045 */         return (V)localObject;
/*      */       }
/* 1031 */       localEntry2 = localEntry1;
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
/* 1048 */     return null;
/*      */   }
/*      */   
/*      */   public synchronized V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1053 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/* 1055 */     Entry[] arrayOfEntry = this.table;
/* 1056 */     int i = paramK.hashCode();
/* 1057 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/* 1059 */     Entry localEntry = arrayOfEntry[j];
/* 1060 */     for (Object localObject1 = null; localEntry != null; localEntry = localEntry.next) {
/* 1061 */       if ((localEntry.hash == i) && (Objects.equals(localEntry.key, paramK))) {
/* 1062 */         Object localObject2 = paramBiFunction.apply(paramK, localEntry.value);
/* 1063 */         if (localObject2 == null) {
/* 1064 */           this.modCount += 1;
/* 1065 */           if (localObject1 != null) {
/* 1066 */             ((Entry)localObject1).next = localEntry.next;
/*      */           } else {
/* 1068 */             arrayOfEntry[j] = localEntry.next;
/*      */           }
/* 1070 */           this.count -= 1;
/*      */         } else {
/* 1072 */           localEntry.value = localObject2;
/*      */         }
/* 1074 */         return (V)localObject2;
/*      */       }
/* 1060 */       localObject1 = localEntry;
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
/* 1078 */     localObject1 = paramBiFunction.apply(paramK, null);
/* 1079 */     if (localObject1 != null) {
/* 1080 */       addEntry(i, paramK, localObject1, j);
/*      */     }
/*      */     
/* 1083 */     return (V)localObject1;
/*      */   }
/*      */   
/*      */   public synchronized V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1088 */     Objects.requireNonNull(paramBiFunction);
/*      */     
/* 1090 */     Entry[] arrayOfEntry = this.table;
/* 1091 */     int i = paramK.hashCode();
/* 1092 */     int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */     
/* 1094 */     Entry localEntry1 = arrayOfEntry[j];
/* 1095 */     for (Entry localEntry2 = null; localEntry1 != null; localEntry1 = localEntry1.next) {
/* 1096 */       if ((localEntry1.hash == i) && (localEntry1.key.equals(paramK))) {
/* 1097 */         Object localObject = paramBiFunction.apply(localEntry1.value, paramV);
/* 1098 */         if (localObject == null) {
/* 1099 */           this.modCount += 1;
/* 1100 */           if (localEntry2 != null) {
/* 1101 */             localEntry2.next = localEntry1.next;
/*      */           } else {
/* 1103 */             arrayOfEntry[j] = localEntry1.next;
/*      */           }
/* 1105 */           this.count -= 1;
/*      */         } else {
/* 1107 */           localEntry1.value = localObject;
/*      */         }
/* 1109 */         return (V)localObject;
/*      */       }
/* 1095 */       localEntry2 = localEntry1;
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
/* 1113 */     if (paramV != null) {
/* 1114 */       addEntry(i, paramK, paramV, j);
/*      */     }
/*      */     
/* 1117 */     return paramV;
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
/* 1132 */     Entry localEntry1 = null;
/*      */     
/* 1134 */     synchronized (this)
/*      */     {
/* 1136 */       paramObjectOutputStream.defaultWriteObject();
/*      */       
/*      */ 
/* 1139 */       paramObjectOutputStream.writeInt(this.table.length);
/* 1140 */       paramObjectOutputStream.writeInt(this.count);
/*      */       
/*      */ 
/* 1143 */       for (int i = 0; i < this.table.length; i++) {
/* 1144 */         Entry localEntry2 = this.table[i];
/*      */         
/* 1146 */         while (localEntry2 != null) {
/* 1147 */           localEntry1 = new Entry(0, localEntry2.key, localEntry2.value, localEntry1);
/*      */           
/* 1149 */           localEntry2 = localEntry2.next;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1155 */     while (localEntry1 != null) {
/* 1156 */       paramObjectOutputStream.writeObject(localEntry1.key);
/* 1157 */       paramObjectOutputStream.writeObject(localEntry1.value);
/* 1158 */       localEntry1 = localEntry1.next;
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
/* 1169 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/* 1172 */     int i = paramObjectInputStream.readInt();
/* 1173 */     int j = paramObjectInputStream.readInt();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1179 */     int k = (int)(j * this.loadFactor) + j / 20 + 3;
/* 1180 */     if ((k > j) && ((k & 0x1) == 0))
/* 1181 */       k--;
/* 1182 */     if ((i > 0) && (k > i))
/* 1183 */       k = i;
/* 1184 */     this.table = new Entry[k];
/* 1185 */     this.threshold = ((int)Math.min(k * this.loadFactor, 2.14748365E9F));
/* 1186 */     this.count = 0;
/* 1189 */     for (; 
/*      */         
/* 1189 */         j > 0; j--)
/*      */     {
/* 1191 */       Object localObject1 = paramObjectInputStream.readObject();
/*      */       
/* 1193 */       Object localObject2 = paramObjectInputStream.readObject();
/*      */       
/* 1195 */       reconstitutionPut(this.table, localObject1, localObject2);
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
/*      */   private void reconstitutionPut(Entry<?, ?>[] paramArrayOfEntry, K paramK, V paramV)
/*      */     throws StreamCorruptedException
/*      */   {
/* 1213 */     if (paramV == null) {
/* 1214 */       throw new StreamCorruptedException();
/*      */     }
/*      */     
/*      */ 
/* 1218 */     int i = paramK.hashCode();
/* 1219 */     int j = (i & 0x7FFFFFFF) % paramArrayOfEntry.length;
/* 1220 */     for (Object localObject = paramArrayOfEntry[j]; localObject != null; localObject = ((Entry)localObject).next) {
/* 1221 */       if ((((Entry)localObject).hash == i) && (((Entry)localObject).key.equals(paramK))) {
/* 1222 */         throw new StreamCorruptedException();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1227 */     localObject = paramArrayOfEntry[j];
/* 1228 */     paramArrayOfEntry[j] = new Entry(i, paramK, paramV, (Entry)localObject);
/* 1229 */     this.count += 1;
/*      */   }
/*      */   
/*      */   private static class Entry<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final K key;
/*      */     V value;
/*      */     Entry<K, V> next;
/*      */     
/*      */     protected Entry(int paramInt, K paramK, V paramV, Entry<K, V> paramEntry)
/*      */     {
/* 1242 */       this.hash = paramInt;
/* 1243 */       this.key = paramK;
/* 1244 */       this.value = paramV;
/* 1245 */       this.next = paramEntry;
/*      */     }
/*      */     
/*      */ 
/*      */     protected Object clone()
/*      */     {
/* 1251 */       return new Entry(this.hash, this.key, this.value, this.next == null ? null : (Entry)this.next.clone());
/*      */     }
/*      */     
/*      */ 
/*      */     public K getKey()
/*      */     {
/* 1257 */       return (K)this.key;
/*      */     }
/*      */     
/*      */     public V getValue() {
/* 1261 */       return (V)this.value;
/*      */     }
/*      */     
/*      */     public V setValue(V paramV) {
/* 1265 */       if (paramV == null) {
/* 1266 */         throw new NullPointerException();
/*      */       }
/* 1268 */       Object localObject = this.value;
/* 1269 */       this.value = paramV;
/* 1270 */       return (V)localObject;
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/* 1274 */       if (!(paramObject instanceof Map.Entry))
/* 1275 */         return false;
/* 1276 */       Map.Entry localEntry = (Map.Entry)paramObject;
/*      */       
/*      */ 
/* 1279 */       return (this.key == null ? localEntry.getKey() == null : this.key.equals(localEntry.getKey())) && (this.value == null ? localEntry.getValue() == null : this.value.equals(localEntry.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1283 */       return this.hash ^ Objects.hashCode(this.value);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1287 */       return this.key.toString() + "=" + this.value.toString();
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
/*      */   private class Enumerator<T>
/*      */     implements Enumeration<T>, Iterator<T>
/*      */   {
/* 1304 */     Hashtable.Entry<?, ?>[] table = Hashtable.this.table;
/* 1305 */     int index = this.table.length;
/*      */     
/*      */ 
/*      */ 
/*      */     Hashtable.Entry<?, ?> entry;
/*      */     
/*      */ 
/*      */     Hashtable.Entry<?, ?> lastReturned;
/*      */     
/*      */ 
/*      */     int type;
/*      */     
/*      */ 
/*      */     boolean iterator;
/*      */     
/*      */ 
/* 1321 */     protected int expectedModCount = Hashtable.this.modCount;
/*      */     
/*      */     Enumerator(int paramInt, boolean paramBoolean) {
/* 1324 */       this.type = paramInt;
/* 1325 */       this.iterator = paramBoolean;
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1329 */       Hashtable.Entry localEntry = this.entry;
/* 1330 */       int i = this.index;
/* 1331 */       Hashtable.Entry[] arrayOfEntry = this.table;
/*      */       
/* 1333 */       while ((localEntry == null) && (i > 0)) {
/* 1334 */         localEntry = arrayOfEntry[(--i)];
/*      */       }
/* 1336 */       this.entry = localEntry;
/* 1337 */       this.index = i;
/* 1338 */       return localEntry != null;
/*      */     }
/*      */     
/*      */     public T nextElement()
/*      */     {
/* 1343 */       Hashtable.Entry localEntry1 = this.entry;
/* 1344 */       int i = this.index;
/* 1345 */       Hashtable.Entry[] arrayOfEntry = this.table;
/*      */       
/* 1347 */       while ((localEntry1 == null) && (i > 0)) {
/* 1348 */         localEntry1 = arrayOfEntry[(--i)];
/*      */       }
/* 1350 */       this.entry = localEntry1;
/* 1351 */       this.index = i;
/* 1352 */       if (localEntry1 != null) {
/* 1353 */         Hashtable.Entry localEntry2 = this.lastReturned = this.entry;
/* 1354 */         this.entry = localEntry2.next;
/* 1355 */         return (T)(this.type == 1 ? localEntry2.value : this.type == 0 ? localEntry2.key : localEntry2);
/*      */       }
/* 1357 */       throw new NoSuchElementException("Hashtable Enumerator");
/*      */     }
/*      */     
/*      */     public boolean hasNext()
/*      */     {
/* 1362 */       return hasMoreElements();
/*      */     }
/*      */     
/*      */     public T next() {
/* 1366 */       if (Hashtable.this.modCount != this.expectedModCount)
/* 1367 */         throw new ConcurrentModificationException();
/* 1368 */       return (T)nextElement();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1372 */       if (!this.iterator)
/* 1373 */         throw new UnsupportedOperationException();
/* 1374 */       if (this.lastReturned == null)
/* 1375 */         throw new IllegalStateException("Hashtable Enumerator");
/* 1376 */       if (Hashtable.this.modCount != this.expectedModCount) {
/* 1377 */         throw new ConcurrentModificationException();
/*      */       }
/* 1379 */       synchronized (Hashtable.this) {
/* 1380 */         Hashtable.Entry[] arrayOfEntry = Hashtable.this.table;
/* 1381 */         int i = (this.lastReturned.hash & 0x7FFFFFFF) % arrayOfEntry.length;
/*      */         
/*      */ 
/* 1384 */         Hashtable.Entry localEntry1 = arrayOfEntry[i];
/* 1385 */         for (Hashtable.Entry localEntry2 = null; localEntry1 != null; localEntry1 = localEntry1.next) {
/* 1386 */           if (localEntry1 == this.lastReturned) {
/* 1387 */             Hashtable.access$508(Hashtable.this);
/* 1388 */             this.expectedModCount += 1;
/* 1389 */             if (localEntry2 == null) {
/* 1390 */               arrayOfEntry[i] = localEntry1.next;
/*      */             } else
/* 1392 */               localEntry2.next = localEntry1.next;
/* 1393 */             Hashtable.access$210(Hashtable.this);
/* 1394 */             this.lastReturned = null; return;
/*      */           }
/* 1385 */           localEntry2 = localEntry1;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1398 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Hashtable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */