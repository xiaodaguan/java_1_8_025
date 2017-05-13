/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumMap<K extends Enum<K>, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private final Class<K> keyType;
/*     */   private transient K[] keyUniverse;
/*     */   private transient Object[] vals;
/* 104 */   private transient int size = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 109 */   private static final Object NULL = new Object() {
/*     */     public int hashCode() {
/* 111 */       return 0;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 115 */       return "java.util.EnumMap.NULL";
/*     */     }
/*     */   };
/*     */   
/*     */   private Object maskNull(Object paramObject) {
/* 120 */     return paramObject == null ? NULL : paramObject;
/*     */   }
/*     */   
/*     */   private V unmaskNull(Object paramObject)
/*     */   {
/* 125 */     return paramObject == NULL ? null : paramObject;
/*     */   }
/*     */   
/* 128 */   private static final Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum[0];
/*     */   
/*     */   private transient Set<Map.Entry<K, V>> entrySet;
/*     */   
/*     */   private static final long serialVersionUID = 458661240069192865L;
/*     */   
/*     */ 
/*     */   public EnumMap(Class<K> paramClass)
/*     */   {
/* 137 */     this.keyType = paramClass;
/* 138 */     this.keyUniverse = getKeyUniverse(paramClass);
/* 139 */     this.vals = new Object[this.keyUniverse.length];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumMap(EnumMap<K, ? extends V> paramEnumMap)
/*     */   {
/* 150 */     this.keyType = paramEnumMap.keyType;
/* 151 */     this.keyUniverse = paramEnumMap.keyUniverse;
/* 152 */     this.vals = ((Object[])paramEnumMap.vals.clone());
/* 153 */     this.size = paramEnumMap.size;
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
/*     */   public EnumMap(Map<K, ? extends V> paramMap)
/*     */   {
/* 169 */     if ((paramMap instanceof EnumMap)) {
/* 170 */       EnumMap localEnumMap = (EnumMap)paramMap;
/* 171 */       this.keyType = localEnumMap.keyType;
/* 172 */       this.keyUniverse = localEnumMap.keyUniverse;
/* 173 */       this.vals = ((Object[])localEnumMap.vals.clone());
/* 174 */       this.size = localEnumMap.size;
/*     */     } else {
/* 176 */       if (paramMap.isEmpty())
/* 177 */         throw new IllegalArgumentException("Specified map is empty");
/* 178 */       this.keyType = ((Enum)paramMap.keySet().iterator().next()).getDeclaringClass();
/* 179 */       this.keyUniverse = getKeyUniverse(this.keyType);
/* 180 */       this.vals = new Object[this.keyUniverse.length];
/* 181 */       putAll(paramMap);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 193 */     return this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/* 204 */     paramObject = maskNull(paramObject);
/*     */     
/* 206 */     for (Object localObject : this.vals) {
/* 207 */       if (paramObject.equals(localObject))
/* 208 */         return true;
/*     */     }
/* 210 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsKey(Object paramObject)
/*     */   {
/* 222 */     return (isValidKey(paramObject)) && (this.vals[((Enum)paramObject).ordinal()] != null);
/*     */   }
/*     */   
/*     */   private boolean containsMapping(Object paramObject1, Object paramObject2)
/*     */   {
/* 227 */     return (isValidKey(paramObject1)) && (maskNull(paramObject2).equals(this.vals[((Enum)paramObject1).ordinal()]));
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
/*     */   public V get(Object paramObject)
/*     */   {
/* 247 */     return (V)(isValidKey(paramObject) ? unmaskNull(this.vals[((Enum)paramObject).ordinal()]) : null);
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
/*     */   public V put(K paramK, V paramV)
/*     */   {
/* 267 */     typeCheck(paramK);
/*     */     
/* 269 */     int i = paramK.ordinal();
/* 270 */     Object localObject = this.vals[i];
/* 271 */     this.vals[i] = maskNull(paramV);
/* 272 */     if (localObject == null)
/* 273 */       this.size += 1;
/* 274 */     return (V)unmaskNull(localObject);
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
/*     */   public V remove(Object paramObject)
/*     */   {
/* 287 */     if (!isValidKey(paramObject))
/* 288 */       return null;
/* 289 */     int i = ((Enum)paramObject).ordinal();
/* 290 */     Object localObject = this.vals[i];
/* 291 */     this.vals[i] = null;
/* 292 */     if (localObject != null)
/* 293 */       this.size -= 1;
/* 294 */     return (V)unmaskNull(localObject);
/*     */   }
/*     */   
/*     */   private boolean removeMapping(Object paramObject1, Object paramObject2) {
/* 298 */     if (!isValidKey(paramObject1))
/* 299 */       return false;
/* 300 */     int i = ((Enum)paramObject1).ordinal();
/* 301 */     if (maskNull(paramObject2).equals(this.vals[i])) {
/* 302 */       this.vals[i] = null;
/* 303 */       this.size -= 1;
/* 304 */       return true;
/*     */     }
/* 306 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isValidKey(Object paramObject)
/*     */   {
/* 314 */     if (paramObject == null) {
/* 315 */       return false;
/*     */     }
/*     */     
/* 318 */     Class localClass = paramObject.getClass();
/* 319 */     return (localClass == this.keyType) || (localClass.getSuperclass() == this.keyType);
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
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*     */   {
/* 334 */     if ((paramMap instanceof EnumMap)) {
/* 335 */       EnumMap localEnumMap = (EnumMap)paramMap;
/* 336 */       if (localEnumMap.keyType != this.keyType) {
/* 337 */         if (localEnumMap.isEmpty())
/* 338 */           return;
/* 339 */         throw new ClassCastException(localEnumMap.keyType + " != " + this.keyType);
/*     */       }
/*     */       
/* 342 */       for (int i = 0; i < this.keyUniverse.length; i++) {
/* 343 */         Object localObject = localEnumMap.vals[i];
/* 344 */         if (localObject != null) {
/* 345 */           if (this.vals[i] == null)
/* 346 */             this.size += 1;
/* 347 */           this.vals[i] = localObject;
/*     */         }
/*     */       }
/*     */     } else {
/* 351 */       super.putAll(paramMap);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 359 */     Arrays.fill(this.vals, null);
/* 360 */     this.size = 0;
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
/*     */   public Set<K> keySet()
/*     */   {
/* 382 */     Set localSet = this.keySet;
/* 383 */     if (localSet != null) {
/* 384 */       return localSet;
/*     */     }
/* 386 */     return this.keySet = new KeySet(null);
/*     */   }
/*     */   
/*     */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*     */     
/* 391 */     public Iterator<K> iterator() { return new EnumMap.KeyIterator(EnumMap.this, null); }
/*     */     
/*     */     public int size() {
/* 394 */       return EnumMap.this.size;
/*     */     }
/*     */     
/* 397 */     public boolean contains(Object paramObject) { return EnumMap.this.containsKey(paramObject); }
/*     */     
/*     */     public boolean remove(Object paramObject) {
/* 400 */       int i = EnumMap.this.size;
/* 401 */       EnumMap.this.remove(paramObject);
/* 402 */       return EnumMap.this.size != i;
/*     */     }
/*     */     
/* 405 */     public void clear() { EnumMap.this.clear(); }
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
/*     */   public Collection<V> values()
/*     */   {
/* 420 */     Collection localCollection = this.values;
/* 421 */     if (localCollection != null) {
/* 422 */       return localCollection;
/*     */     }
/* 424 */     return this.values = new Values(null);
/*     */   }
/*     */   
/*     */   private class Values extends AbstractCollection<V> { private Values() {}
/*     */     
/* 429 */     public Iterator<V> iterator() { return new EnumMap.ValueIterator(EnumMap.this, null); }
/*     */     
/*     */     public int size() {
/* 432 */       return EnumMap.this.size;
/*     */     }
/*     */     
/* 435 */     public boolean contains(Object paramObject) { return EnumMap.this.containsValue(paramObject); }
/*     */     
/*     */     public boolean remove(Object paramObject) {
/* 438 */       paramObject = EnumMap.this.maskNull(paramObject);
/*     */       
/* 440 */       for (int i = 0; i < EnumMap.this.vals.length; i++) {
/* 441 */         if (paramObject.equals(EnumMap.this.vals[i])) {
/* 442 */           EnumMap.this.vals[i] = null;
/* 443 */           EnumMap.access$210(EnumMap.this);
/* 444 */           return true;
/*     */         }
/*     */       }
/* 447 */       return false;
/*     */     }
/*     */     
/* 450 */     public void clear() { EnumMap.this.clear(); }
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
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 464 */     Set localSet = this.entrySet;
/* 465 */     if (localSet != null) {
/* 466 */       return localSet;
/*     */     }
/* 468 */     return this.entrySet = new EntrySet(null);
/*     */   }
/*     */   
/*     */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*     */     
/* 473 */     public Iterator<Map.Entry<K, V>> iterator() { return new EnumMap.EntryIterator(EnumMap.this, null); }
/*     */     
/*     */     public boolean contains(Object paramObject)
/*     */     {
/* 477 */       if (!(paramObject instanceof Map.Entry))
/* 478 */         return false;
/* 479 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 480 */       return EnumMap.this.containsMapping(localEntry.getKey(), localEntry.getValue());
/*     */     }
/*     */     
/* 483 */     public boolean remove(Object paramObject) { if (!(paramObject instanceof Map.Entry))
/* 484 */         return false;
/* 485 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 486 */       return EnumMap.this.removeMapping(localEntry.getKey(), localEntry.getValue());
/*     */     }
/*     */     
/* 489 */     public int size() { return EnumMap.this.size; }
/*     */     
/*     */     public void clear() {
/* 492 */       EnumMap.this.clear();
/*     */     }
/*     */     
/* 495 */     public Object[] toArray() { return fillEntryArray(new Object[EnumMap.this.size]); }
/*     */     
/*     */     public <T> T[] toArray(T[] paramArrayOfT)
/*     */     {
/* 499 */       int i = size();
/* 500 */       if (paramArrayOfT.length < i)
/*     */       {
/* 502 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i); }
/* 503 */       if (paramArrayOfT.length > i)
/* 504 */         paramArrayOfT[i] = null;
/* 505 */       return (Object[])fillEntryArray(paramArrayOfT);
/*     */     }
/*     */     
/* 508 */     private Object[] fillEntryArray(Object[] paramArrayOfObject) { int i = 0;
/* 509 */       for (int j = 0; j < EnumMap.this.vals.length; j++)
/* 510 */         if (EnumMap.this.vals[j] != null)
/*     */         {
/* 512 */           paramArrayOfObject[(i++)] = new AbstractMap.SimpleEntry(EnumMap.this.keyUniverse[j], EnumMap.this.unmaskNull(EnumMap.this.vals[j])); }
/* 513 */       return paramArrayOfObject;
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class EnumMapIterator<T> implements Iterator<T> { private EnumMapIterator() {}
/*     */     
/* 519 */     int index = 0;
/*     */     
/*     */ 
/* 522 */     int lastReturnedIndex = -1;
/*     */     
/*     */     public boolean hasNext() {
/* 525 */       while ((this.index < EnumMap.this.vals.length) && (EnumMap.this.vals[this.index] == null))
/* 526 */         this.index += 1;
/* 527 */       return this.index != EnumMap.this.vals.length;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 531 */       checkLastReturnedIndex();
/*     */       
/* 533 */       if (EnumMap.this.vals[this.lastReturnedIndex] != null) {
/* 534 */         EnumMap.this.vals[this.lastReturnedIndex] = null;
/* 535 */         EnumMap.access$210(EnumMap.this);
/*     */       }
/* 537 */       this.lastReturnedIndex = -1;
/*     */     }
/*     */     
/*     */     private void checkLastReturnedIndex() {
/* 541 */       if (this.lastReturnedIndex < 0)
/* 542 */         throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */   
/* 546 */   private class KeyIterator extends EnumMap<K, V>.EnumMapIterator<K> { private KeyIterator() { super(null); }
/*     */     
/* 548 */     public K next() { if (!hasNext())
/* 549 */         throw new NoSuchElementException();
/* 550 */       this.lastReturnedIndex = (this.index++);
/* 551 */       return EnumMap.this.keyUniverse[this.lastReturnedIndex];
/*     */     }
/*     */   }
/*     */   
/* 555 */   private class ValueIterator extends EnumMap<K, V>.EnumMapIterator<V> { private ValueIterator() { super(null); }
/*     */     
/* 557 */     public V next() { if (!hasNext())
/* 558 */         throw new NoSuchElementException();
/* 559 */       this.lastReturnedIndex = (this.index++);
/* 560 */       return (V)EnumMap.this.unmaskNull(EnumMap.this.vals[this.lastReturnedIndex]); } }
/*     */   
/*     */   private class EntryIterator extends EnumMap<K, V>.EnumMapIterator<Map.Entry<K, V>> { private EnumMap<K, V>.EntryIterator.Entry lastReturnedEntry;
/*     */     
/* 564 */     private EntryIterator() { super(null); }
/*     */     
/*     */     public Map.Entry<K, V> next()
/*     */     {
/* 568 */       if (!hasNext())
/* 569 */         throw new NoSuchElementException();
/* 570 */       this.lastReturnedEntry = new Entry(this.index++, null);
/* 571 */       return this.lastReturnedEntry;
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 576 */       this.lastReturnedIndex = (null == this.lastReturnedEntry ? -1 : this.lastReturnedEntry.index);
/* 577 */       super.remove();
/* 578 */       this.lastReturnedEntry.index = this.lastReturnedIndex;
/* 579 */       this.lastReturnedEntry = null;
/*     */     }
/*     */     
/*     */     private class Entry implements Map.Entry<K, V> {
/*     */       private int index;
/*     */       
/*     */       private Entry(int paramInt) {
/* 586 */         this.index = paramInt;
/*     */       }
/*     */       
/*     */       public K getKey() {
/* 590 */         checkIndexForEntryUse();
/* 591 */         return EnumMap.this.keyUniverse[this.index];
/*     */       }
/*     */       
/*     */       public V getValue() {
/* 595 */         checkIndexForEntryUse();
/* 596 */         return (V)EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/*     */       }
/*     */       
/*     */       public V setValue(V paramV) {
/* 600 */         checkIndexForEntryUse();
/* 601 */         Object localObject = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/* 602 */         EnumMap.this.vals[this.index] = EnumMap.this.maskNull(paramV);
/* 603 */         return (V)localObject;
/*     */       }
/*     */       
/*     */       public boolean equals(Object paramObject) {
/* 607 */         if (this.index < 0) {
/* 608 */           return paramObject == this;
/*     */         }
/* 610 */         if (!(paramObject instanceof Map.Entry)) {
/* 611 */           return false;
/*     */         }
/* 613 */         Map.Entry localEntry = (Map.Entry)paramObject;
/* 614 */         Object localObject1 = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/* 615 */         Object localObject2 = localEntry.getValue();
/* 616 */         if (localEntry.getKey() == EnumMap.this.keyUniverse[this.index]) if (localObject1 != localObject2) if (localObject1 == null) break label113;
/*     */         label113:
/* 618 */         return localObject1.equals(localObject2);
/*     */       }
/*     */       
/*     */       public int hashCode() {
/* 622 */         if (this.index < 0) {
/* 623 */           return super.hashCode();
/*     */         }
/* 625 */         return EnumMap.this.entryHashCode(this.index);
/*     */       }
/*     */       
/*     */       public String toString() {
/* 629 */         if (this.index < 0) {
/* 630 */           return super.toString();
/*     */         }
/*     */         
/* 633 */         return EnumMap.this.keyUniverse[this.index] + "=" + EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/*     */       }
/*     */       
/*     */       private void checkIndexForEntryUse() {
/* 637 */         if (this.index < 0) {
/* 638 */           throw new IllegalStateException("Entry was removed");
/*     */         }
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 655 */     if (this == paramObject)
/* 656 */       return true;
/* 657 */     if ((paramObject instanceof EnumMap))
/* 658 */       return equals((EnumMap)paramObject);
/* 659 */     if (!(paramObject instanceof Map)) {
/* 660 */       return false;
/*     */     }
/* 662 */     Map localMap = (Map)paramObject;
/* 663 */     if (this.size != localMap.size()) {
/* 664 */       return false;
/*     */     }
/* 666 */     for (int i = 0; i < this.keyUniverse.length; i++) {
/* 667 */       if (null != this.vals[i]) {
/* 668 */         Enum localEnum = this.keyUniverse[i];
/* 669 */         Object localObject = unmaskNull(this.vals[i]);
/* 670 */         if (null == localObject) {
/* 671 */           if ((null != localMap.get(localEnum)) || (!localMap.containsKey(localEnum))) {
/* 672 */             return false;
/*     */           }
/* 674 */         } else if (!localObject.equals(localMap.get(localEnum))) {
/* 675 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 680 */     return true;
/*     */   }
/*     */   
/*     */   private boolean equals(EnumMap<?, ?> paramEnumMap) {
/* 684 */     if (paramEnumMap.keyType != this.keyType) {
/* 685 */       return (this.size == 0) && (paramEnumMap.size == 0);
/*     */     }
/*     */     
/* 688 */     for (int i = 0; i < this.keyUniverse.length; i++) {
/* 689 */       Object localObject1 = this.vals[i];
/* 690 */       Object localObject2 = paramEnumMap.vals[i];
/* 691 */       if ((localObject2 != localObject1) && ((localObject2 == null) || 
/* 692 */         (!localObject2.equals(localObject1))))
/* 693 */         return false;
/*     */     }
/* 695 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 703 */     int i = 0;
/*     */     
/* 705 */     for (int j = 0; j < this.keyUniverse.length; j++) {
/* 706 */       if (null != this.vals[j]) {
/* 707 */         i += entryHashCode(j);
/*     */       }
/*     */     }
/*     */     
/* 711 */     return i;
/*     */   }
/*     */   
/*     */   private int entryHashCode(int paramInt) {
/* 715 */     return this.keyUniverse[paramInt].hashCode() ^ this.vals[paramInt].hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumMap<K, V> clone()
/*     */   {
/* 726 */     EnumMap localEnumMap = null;
/*     */     try {
/* 728 */       localEnumMap = (EnumMap)super.clone();
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 730 */       throw new AssertionError();
/*     */     }
/* 732 */     localEnumMap.vals = ((Object[])localEnumMap.vals.clone());
/* 733 */     localEnumMap.entrySet = null;
/* 734 */     return localEnumMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void typeCheck(K paramK)
/*     */   {
/* 741 */     Class localClass = paramK.getClass();
/* 742 */     if ((localClass != this.keyType) && (localClass.getSuperclass() != this.keyType)) {
/* 743 */       throw new ClassCastException(localClass + " != " + this.keyType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <K extends Enum<K>> K[] getKeyUniverse(Class<K> paramClass)
/*     */   {
/* 752 */     return SharedSecrets.getJavaLangAccess().getEnumConstantsShared(paramClass);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 770 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 773 */     paramObjectOutputStream.writeInt(this.size);
/*     */     
/*     */ 
/* 776 */     int i = this.size;
/* 777 */     for (int j = 0; i > 0; j++) {
/* 778 */       if (null != this.vals[j]) {
/* 779 */         paramObjectOutputStream.writeObject(this.keyUniverse[j]);
/* 780 */         paramObjectOutputStream.writeObject(unmaskNull(this.vals[j]));
/* 781 */         i--;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 795 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 797 */     this.keyUniverse = getKeyUniverse(this.keyType);
/* 798 */     this.vals = new Object[this.keyUniverse.length];
/*     */     
/*     */ 
/* 801 */     int i = paramObjectInputStream.readInt();
/*     */     
/*     */ 
/* 804 */     for (int j = 0; j < i; j++) {
/* 805 */       Enum localEnum = (Enum)paramObjectInputStream.readObject();
/* 806 */       Object localObject = paramObjectInputStream.readObject();
/* 807 */       put(localEnum, localObject);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/EnumMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */