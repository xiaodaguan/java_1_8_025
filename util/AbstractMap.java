/*     */ package java.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   volatile transient Set<K> keySet;
/*     */   volatile transient Collection<V> values;
/*     */   
/*     */   public int size()
/*     */   {
/*  85 */     return entrySet().size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  95 */     return size() == 0;
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
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/* 112 */     Iterator localIterator = entrySet().iterator();
/* 113 */     Map.Entry localEntry; if (paramObject == null) {
/* 114 */       while (localIterator.hasNext()) {
/* 115 */         localEntry = (Map.Entry)localIterator.next();
/* 116 */         if (localEntry.getValue() == null)
/* 117 */           return true;
/*     */       }
/*     */     }
/* 120 */     while (localIterator.hasNext()) {
/* 121 */       localEntry = (Map.Entry)localIterator.next();
/* 122 */       if (paramObject.equals(localEntry.getValue())) {
/* 123 */         return true;
/*     */       }
/*     */     }
/* 126 */     return false;
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
/*     */   public boolean containsKey(Object paramObject)
/*     */   {
/* 144 */     Iterator localIterator = entrySet().iterator();
/* 145 */     Map.Entry localEntry; if (paramObject == null) {
/* 146 */       while (localIterator.hasNext()) {
/* 147 */         localEntry = (Map.Entry)localIterator.next();
/* 148 */         if (localEntry.getKey() == null)
/* 149 */           return true;
/*     */       }
/*     */     }
/* 152 */     while (localIterator.hasNext()) {
/* 153 */       localEntry = (Map.Entry)localIterator.next();
/* 154 */       if (paramObject.equals(localEntry.getKey())) {
/* 155 */         return true;
/*     */       }
/*     */     }
/* 158 */     return false;
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
/*     */   public V get(Object paramObject)
/*     */   {
/* 176 */     Iterator localIterator = entrySet().iterator();
/* 177 */     Map.Entry localEntry; if (paramObject == null) {
/* 178 */       while (localIterator.hasNext()) {
/* 179 */         localEntry = (Map.Entry)localIterator.next();
/* 180 */         if (localEntry.getKey() == null)
/* 181 */           return (V)localEntry.getValue();
/*     */       }
/*     */     }
/* 184 */     while (localIterator.hasNext()) {
/* 185 */       localEntry = (Map.Entry)localIterator.next();
/* 186 */       if (paramObject.equals(localEntry.getKey())) {
/* 187 */         return (V)localEntry.getValue();
/*     */       }
/*     */     }
/* 190 */     return null;
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
/*     */   public V put(K paramK, V paramV)
/*     */   {
/* 209 */     throw new UnsupportedOperationException();
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
/*     */   public V remove(Object paramObject)
/*     */   {
/* 235 */     Iterator localIterator = entrySet().iterator();
/* 236 */     Object localObject1 = null;
/* 237 */     if (paramObject == null) {
/* 238 */       while ((localObject1 == null) && (localIterator.hasNext())) {
/* 239 */         localObject2 = (Map.Entry)localIterator.next();
/* 240 */         if (((Map.Entry)localObject2).getKey() == null)
/* 241 */           localObject1 = localObject2;
/*     */       }
/*     */     }
/* 244 */     while ((localObject1 == null) && (localIterator.hasNext())) {
/* 245 */       localObject2 = (Map.Entry)localIterator.next();
/* 246 */       if (paramObject.equals(((Map.Entry)localObject2).getKey())) {
/* 247 */         localObject1 = localObject2;
/*     */       }
/*     */     }
/*     */     
/* 251 */     Object localObject2 = null;
/* 252 */     if (localObject1 != null) {
/* 253 */       localObject2 = ((Map.Entry)localObject1).getValue();
/* 254 */       localIterator.remove();
/*     */     }
/* 256 */     return (V)localObject2;
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
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*     */   {
/* 280 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 281 */       put(localEntry.getKey(), localEntry.getValue());
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
/*     */   public void clear()
/*     */   {
/* 297 */     entrySet().clear();
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
/*     */   public Set<K> keySet()
/*     */   {
/* 328 */     if (this.keySet == null) {
/* 329 */       this.keySet = new AbstractSet() {
/*     */         public Iterator<K> iterator() {
/* 331 */           new Iterator() {
/* 332 */             private Iterator<Map.Entry<K, V>> i = AbstractMap.this.entrySet().iterator();
/*     */             
/*     */             public boolean hasNext() {
/* 335 */               return this.i.hasNext();
/*     */             }
/*     */             
/*     */             public K next() {
/* 339 */               return (K)((Map.Entry)this.i.next()).getKey();
/*     */             }
/*     */             
/*     */             public void remove() {
/* 343 */               this.i.remove();
/*     */             }
/*     */           };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return AbstractMap.this.size();
/*     */         }
/*     */         
/*     */         public boolean isEmpty() {
/* 353 */           return AbstractMap.this.isEmpty();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 357 */           AbstractMap.this.clear();
/*     */         }
/*     */         
/*     */         public boolean contains(Object paramAnonymousObject) {
/* 361 */           return AbstractMap.this.containsKey(paramAnonymousObject);
/*     */         }
/*     */       };
/*     */     }
/* 365 */     return this.keySet;
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
/*     */   public Collection<V> values()
/*     */   {
/* 385 */     if (this.values == null) {
/* 386 */       this.values = new AbstractCollection() {
/*     */         public Iterator<V> iterator() {
/* 388 */           new Iterator() {
/* 389 */             private Iterator<Map.Entry<K, V>> i = AbstractMap.this.entrySet().iterator();
/*     */             
/*     */             public boolean hasNext() {
/* 392 */               return this.i.hasNext();
/*     */             }
/*     */             
/*     */             public V next() {
/* 396 */               return (V)((Map.Entry)this.i.next()).getValue();
/*     */             }
/*     */             
/*     */             public void remove() {
/* 400 */               this.i.remove();
/*     */             }
/*     */           };
/*     */         }
/*     */         
/*     */         public int size() {
/* 406 */           return AbstractMap.this.size();
/*     */         }
/*     */         
/*     */         public boolean isEmpty() {
/* 410 */           return AbstractMap.this.isEmpty();
/*     */         }
/*     */         
/*     */         public void clear() {
/* 414 */           AbstractMap.this.clear();
/*     */         }
/*     */         
/*     */         public boolean contains(Object paramAnonymousObject) {
/* 418 */           return AbstractMap.this.containsValue(paramAnonymousObject);
/*     */         }
/*     */       };
/*     */     }
/* 422 */     return this.values;
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
/*     */   public abstract Set<Map.Entry<K, V>> entrySet();
/*     */   
/*     */ 
/*     */ 
/*     */ 
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
/* 453 */     if (paramObject == this) {
/* 454 */       return true;
/*     */     }
/* 456 */     if (!(paramObject instanceof Map))
/* 457 */       return false;
/* 458 */     Map localMap = (Map)paramObject;
/* 459 */     if (localMap.size() != size()) {
/* 460 */       return false;
/*     */     }
/*     */     try {
/* 463 */       Iterator localIterator = entrySet().iterator();
/* 464 */       while (localIterator.hasNext()) {
/* 465 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 466 */         Object localObject1 = localEntry.getKey();
/* 467 */         Object localObject2 = localEntry.getValue();
/* 468 */         if (localObject2 == null) {
/* 469 */           if ((localMap.get(localObject1) != null) || (!localMap.containsKey(localObject1))) {
/* 470 */             return false;
/*     */           }
/* 472 */         } else if (!localObject2.equals(localMap.get(localObject1))) {
/* 473 */           return false;
/*     */         }
/*     */       }
/*     */     } catch (ClassCastException localClassCastException) {
/* 477 */       return false;
/*     */     } catch (NullPointerException localNullPointerException) {
/* 479 */       return false;
/*     */     }
/*     */     
/* 482 */     return true;
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
/*     */   public int hashCode()
/*     */   {
/* 504 */     int i = 0;
/* 505 */     Iterator localIterator = entrySet().iterator();
/* 506 */     while (localIterator.hasNext())
/* 507 */       i += ((Map.Entry)localIterator.next()).hashCode();
/* 508 */     return i;
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
/*     */   public String toString()
/*     */   {
/* 524 */     Iterator localIterator = entrySet().iterator();
/* 525 */     if (!localIterator.hasNext()) {
/* 526 */       return "{}";
/*     */     }
/* 528 */     StringBuilder localStringBuilder = new StringBuilder();
/* 529 */     localStringBuilder.append('{');
/*     */     for (;;) {
/* 531 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 532 */       Object localObject1 = localEntry.getKey();
/* 533 */       Object localObject2 = localEntry.getValue();
/* 534 */       localStringBuilder.append(localObject1 == this ? "(this Map)" : localObject1);
/* 535 */       localStringBuilder.append('=');
/* 536 */       localStringBuilder.append(localObject2 == this ? "(this Map)" : localObject2);
/* 537 */       if (!localIterator.hasNext())
/* 538 */         return '}';
/* 539 */       localStringBuilder.append(',').append(' ');
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 550 */     AbstractMap localAbstractMap = (AbstractMap)super.clone();
/* 551 */     localAbstractMap.keySet = null;
/* 552 */     localAbstractMap.values = null;
/* 553 */     return localAbstractMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean eq(Object paramObject1, Object paramObject2)
/*     */   {
/* 563 */     return paramObject1 == null ? false : paramObject2 == null ? true : paramObject1.equals(paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class SimpleEntry<K, V>
/*     */     implements Map.Entry<K, V>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -8499721149061103585L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final K key;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private V value;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public SimpleEntry(K paramK, V paramV)
/*     */     {
/* 600 */       this.key = paramK;
/* 601 */       this.value = paramV;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public SimpleEntry(Map.Entry<? extends K, ? extends V> paramEntry)
/*     */     {
/* 611 */       this.key = paramEntry.getKey();
/* 612 */       this.value = paramEntry.getValue();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public K getKey()
/*     */     {
/* 621 */       return (K)this.key;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public V getValue()
/*     */     {
/* 630 */       return (V)this.value;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public V setValue(V paramV)
/*     */     {
/* 641 */       Object localObject = this.value;
/* 642 */       this.value = paramV;
/* 643 */       return (V)localObject;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 668 */       if (!(paramObject instanceof Map.Entry))
/* 669 */         return false;
/* 670 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 671 */       return (AbstractMap.eq(this.key, localEntry.getKey())) && (AbstractMap.eq(this.value, localEntry.getValue()));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 689 */       return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 701 */       return this.key + "=" + this.value;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class SimpleImmutableEntry<K, V>
/*     */     implements Map.Entry<K, V>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7138329143949025153L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private final K key;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private final V value;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public SimpleImmutableEntry(K paramK, V paramV)
/*     */     {
/* 730 */       this.key = paramK;
/* 731 */       this.value = paramV;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public SimpleImmutableEntry(Map.Entry<? extends K, ? extends V> paramEntry)
/*     */     {
/* 741 */       this.key = paramEntry.getKey();
/* 742 */       this.value = paramEntry.getValue();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public K getKey()
/*     */     {
/* 751 */       return (K)this.key;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public V getValue()
/*     */     {
/* 760 */       return (V)this.value;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public V setValue(V paramV)
/*     */     {
/* 774 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 799 */       if (!(paramObject instanceof Map.Entry))
/* 800 */         return false;
/* 801 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 802 */       return (AbstractMap.eq(this.key, localEntry.getKey())) && (AbstractMap.eq(this.value, localEntry.getValue()));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 820 */       return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 832 */       return this.key + "=" + this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/AbstractMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */