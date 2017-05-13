/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinkedHashMap<K, V>
/*     */   extends HashMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = 3801124242820219131L;
/*     */   transient Entry<K, V> head;
/*     */   transient Entry<K, V> tail;
/*     */   final boolean accessOrder;
/*     */   
/*     */   static class Entry<K, V>
/*     */     extends HashMap.Node<K, V>
/*     */   {
/*     */     Entry<K, V> before;
/*     */     Entry<K, V> after;
/*     */     
/*     */     Entry(int paramInt, K paramK, V paramV, HashMap.Node<K, V> paramNode)
/*     */     {
/* 195 */       super(paramK, paramV, paramNode);
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
/*     */   private void linkNodeLast(Entry<K, V> paramEntry)
/*     */   {
/* 223 */     Entry localEntry = this.tail;
/* 224 */     this.tail = paramEntry;
/* 225 */     if (localEntry == null) {
/* 226 */       this.head = paramEntry;
/*     */     } else {
/* 228 */       paramEntry.before = localEntry;
/* 229 */       localEntry.after = paramEntry;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void transferLinks(Entry<K, V> paramEntry1, Entry<K, V> paramEntry2)
/*     */   {
/* 236 */     Entry localEntry1 = paramEntry2.before = paramEntry1.before;
/* 237 */     Entry localEntry2 = paramEntry2.after = paramEntry1.after;
/* 238 */     if (localEntry1 == null) {
/* 239 */       this.head = paramEntry2;
/*     */     } else
/* 241 */       localEntry1.after = paramEntry2;
/* 242 */     if (localEntry2 == null) {
/* 243 */       this.tail = paramEntry2;
/*     */     } else {
/* 245 */       localEntry2.before = paramEntry2;
/*     */     }
/*     */   }
/*     */   
/*     */   void reinitialize()
/*     */   {
/* 251 */     super.reinitialize();
/* 252 */     this.head = (this.tail = null);
/*     */   }
/*     */   
/*     */   HashMap.Node<K, V> newNode(int paramInt, K paramK, V paramV, HashMap.Node<K, V> paramNode) {
/* 256 */     Entry localEntry = new Entry(paramInt, paramK, paramV, paramNode);
/*     */     
/* 258 */     linkNodeLast(localEntry);
/* 259 */     return localEntry;
/*     */   }
/*     */   
/*     */   HashMap.Node<K, V> replacementNode(HashMap.Node<K, V> paramNode1, HashMap.Node<K, V> paramNode2) {
/* 263 */     Entry localEntry1 = (Entry)paramNode1;
/* 264 */     Entry localEntry2 = new Entry(localEntry1.hash, localEntry1.key, localEntry1.value, paramNode2);
/*     */     
/* 266 */     transferLinks(localEntry1, localEntry2);
/* 267 */     return localEntry2;
/*     */   }
/*     */   
/*     */   HashMap.TreeNode<K, V> newTreeNode(int paramInt, K paramK, V paramV, HashMap.Node<K, V> paramNode) {
/* 271 */     HashMap.TreeNode localTreeNode = new HashMap.TreeNode(paramInt, paramK, paramV, paramNode);
/* 272 */     linkNodeLast(localTreeNode);
/* 273 */     return localTreeNode;
/*     */   }
/*     */   
/*     */   HashMap.TreeNode<K, V> replacementTreeNode(HashMap.Node<K, V> paramNode1, HashMap.Node<K, V> paramNode2) {
/* 277 */     Entry localEntry = (Entry)paramNode1;
/* 278 */     HashMap.TreeNode localTreeNode = new HashMap.TreeNode(localEntry.hash, localEntry.key, localEntry.value, paramNode2);
/* 279 */     transferLinks(localEntry, localTreeNode);
/* 280 */     return localTreeNode;
/*     */   }
/*     */   
/*     */   void afterNodeRemoval(HashMap.Node<K, V> paramNode) {
/* 284 */     Entry localEntry1 = (Entry)paramNode;
/* 285 */     Entry localEntry2 = localEntry1.before;Entry localEntry3 = localEntry1.after;
/* 286 */     localEntry1.before = (localEntry1.after = null);
/* 287 */     if (localEntry2 == null) {
/* 288 */       this.head = localEntry3;
/*     */     } else
/* 290 */       localEntry2.after = localEntry3;
/* 291 */     if (localEntry3 == null) {
/* 292 */       this.tail = localEntry2;
/*     */     } else
/* 294 */       localEntry3.before = localEntry2;
/*     */   }
/*     */   
/*     */   void afterNodeInsertion(boolean paramBoolean) {
/*     */     Entry localEntry;
/* 299 */     if ((paramBoolean) && ((localEntry = this.head) != null) && (removeEldestEntry(localEntry))) {
/* 300 */       Object localObject = localEntry.key;
/* 301 */       removeNode(hash(localObject), localObject, null, false, true);
/*     */     }
/*     */   }
/*     */   
/*     */   void afterNodeAccess(HashMap.Node<K, V> paramNode) {
/*     */     Object localObject;
/* 307 */     if ((this.accessOrder) && ((localObject = this.tail) != paramNode)) {
/* 308 */       Entry localEntry1 = (Entry)paramNode;
/* 309 */       Entry localEntry2 = localEntry1.before;Entry localEntry3 = localEntry1.after;
/* 310 */       localEntry1.after = null;
/* 311 */       if (localEntry2 == null) {
/* 312 */         this.head = localEntry3;
/*     */       } else
/* 314 */         localEntry2.after = localEntry3;
/* 315 */       if (localEntry3 != null) {
/* 316 */         localEntry3.before = localEntry2;
/*     */       } else
/* 318 */         localObject = localEntry2;
/* 319 */       if (localObject == null) {
/* 320 */         this.head = localEntry1;
/*     */       } else {
/* 322 */         localEntry1.before = ((Entry)localObject);
/* 323 */         ((Entry)localObject).after = localEntry1;
/*     */       }
/* 325 */       this.tail = localEntry1;
/* 326 */       this.modCount += 1;
/*     */     }
/*     */   }
/*     */   
/*     */   void internalWriteEntries(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 331 */     for (Entry localEntry = this.head; localEntry != null; localEntry = localEntry.after) {
/* 332 */       paramObjectOutputStream.writeObject(localEntry.key);
/* 333 */       paramObjectOutputStream.writeObject(localEntry.value);
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
/*     */   public LinkedHashMap(int paramInt, float paramFloat)
/*     */   {
/* 347 */     super(paramInt, paramFloat);
/* 348 */     this.accessOrder = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LinkedHashMap(int paramInt)
/*     */   {
/* 359 */     super(paramInt);
/* 360 */     this.accessOrder = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LinkedHashMap()
/*     */   {
/* 369 */     this.accessOrder = false;
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
/*     */   public LinkedHashMap(Map<? extends K, ? extends V> paramMap)
/*     */   {
/* 383 */     this.accessOrder = false;
/* 384 */     putMapEntries(paramMap, false);
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
/*     */   public LinkedHashMap(int paramInt, float paramFloat, boolean paramBoolean)
/*     */   {
/* 401 */     super(paramInt, paramFloat);
/* 402 */     this.accessOrder = paramBoolean;
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
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/* 415 */     for (Entry localEntry = this.head; localEntry != null; localEntry = localEntry.after) {
/* 416 */       Object localObject = localEntry.value;
/* 417 */       if ((localObject == paramObject) || ((paramObject != null) && (paramObject.equals(localObject))))
/* 418 */         return true;
/*     */     }
/* 420 */     return false;
/*     */   }
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
/*     */     HashMap.Node localNode;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 440 */     if ((localNode = getNode(hash(paramObject), paramObject)) == null)
/* 441 */       return null;
/* 442 */     if (this.accessOrder)
/* 443 */       afterNodeAccess(localNode);
/* 444 */     return (V)localNode.value;
/*     */   }
/*     */   
/*     */ 
/*     */   public V getOrDefault(Object paramObject, V paramV)
/*     */   {
/*     */     HashMap.Node localNode;
/*     */     
/* 452 */     if ((localNode = getNode(hash(paramObject), paramObject)) == null)
/* 453 */       return paramV;
/* 454 */     if (this.accessOrder)
/* 455 */       afterNodeAccess(localNode);
/* 456 */     return (V)localNode.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 463 */     super.clear();
/* 464 */     this.head = (this.tail = null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean removeEldestEntry(Map.Entry<K, V> paramEntry)
/*     */   {
/* 509 */     return false;
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
/*     */   public Set<K> keySet()
/*     */   {
/*     */     Set localSet;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 532 */     return (localSet = this.keySet) == null ? (this.keySet = new LinkedKeySet()) : localSet; }
/*     */   
/*     */   final class LinkedKeySet extends AbstractSet<K> { LinkedKeySet() {}
/*     */     
/* 536 */     public final int size() { return LinkedHashMap.this.size; }
/* 537 */     public final void clear() { LinkedHashMap.this.clear(); }
/*     */     
/* 539 */     public final Iterator<K> iterator() { return new LinkedHashMap.LinkedKeyIterator(LinkedHashMap.this); }
/*     */     
/* 541 */     public final boolean contains(Object paramObject) { return LinkedHashMap.this.containsKey(paramObject); }
/*     */     
/* 543 */     public final boolean remove(Object paramObject) { return LinkedHashMap.this.removeNode(HashMap.hash(paramObject), paramObject, null, false, true) != null; }
/*     */     
/*     */     public final Spliterator<K> spliterator() {
/* 546 */       return Spliterators.spliterator(this, 81);
/*     */     }
/*     */     
/*     */     public final void forEach(Consumer<? super K> paramConsumer)
/*     */     {
/* 551 */       if (paramConsumer == null)
/* 552 */         throw new NullPointerException();
/* 553 */       int i = LinkedHashMap.this.modCount;
/* 554 */       for (LinkedHashMap.Entry localEntry = LinkedHashMap.this.head; localEntry != null; localEntry = localEntry.after)
/* 555 */         paramConsumer.accept(localEntry.key);
/* 556 */       if (LinkedHashMap.this.modCount != i) {
/* 557 */         throw new ConcurrentModificationException();
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
/*     */   public Collection<V> values()
/*     */   {
/*     */     Collection localCollection;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 581 */     return (localCollection = this.values) == null ? (this.values = new LinkedValues()) : localCollection; }
/*     */   
/*     */   final class LinkedValues extends AbstractCollection<V> { LinkedValues() {}
/*     */     
/* 585 */     public final int size() { return LinkedHashMap.this.size; }
/* 586 */     public final void clear() { LinkedHashMap.this.clear(); }
/*     */     
/* 588 */     public final Iterator<V> iterator() { return new LinkedHashMap.LinkedValueIterator(LinkedHashMap.this); }
/*     */     
/* 590 */     public final boolean contains(Object paramObject) { return LinkedHashMap.this.containsValue(paramObject); }
/*     */     
/* 592 */     public final Spliterator<V> spliterator() { return Spliterators.spliterator(this, 80); }
/*     */     
/*     */     public final void forEach(Consumer<? super V> paramConsumer)
/*     */     {
/* 596 */       if (paramConsumer == null)
/* 597 */         throw new NullPointerException();
/* 598 */       int i = LinkedHashMap.this.modCount;
/* 599 */       for (LinkedHashMap.Entry localEntry = LinkedHashMap.this.head; localEntry != null; localEntry = localEntry.after)
/* 600 */         paramConsumer.accept(localEntry.value);
/* 601 */       if (LinkedHashMap.this.modCount != i) {
/* 602 */         throw new ConcurrentModificationException();
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
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/*     */     Set localSet;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 627 */     return (localSet = this.entrySet) == null ? (this.entrySet = new LinkedEntrySet()) : localSet; }
/*     */   
/*     */   final class LinkedEntrySet extends AbstractSet<Map.Entry<K, V>> { LinkedEntrySet() {}
/*     */     
/* 631 */     public final int size() { return LinkedHashMap.this.size; }
/* 632 */     public final void clear() { LinkedHashMap.this.clear(); }
/*     */     
/* 634 */     public final Iterator<Map.Entry<K, V>> iterator() { return new LinkedHashMap.LinkedEntryIterator(LinkedHashMap.this); }
/*     */     
/*     */     public final boolean contains(Object paramObject) {
/* 637 */       if (!(paramObject instanceof Map.Entry))
/* 638 */         return false;
/* 639 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 640 */       Object localObject = localEntry.getKey();
/* 641 */       HashMap.Node localNode = LinkedHashMap.this.getNode(HashMap.hash(localObject), localObject);
/* 642 */       return (localNode != null) && (localNode.equals(localEntry));
/*     */     }
/*     */     
/* 645 */     public final boolean remove(Object paramObject) { if ((paramObject instanceof Map.Entry)) {
/* 646 */         Map.Entry localEntry = (Map.Entry)paramObject;
/* 647 */         Object localObject1 = localEntry.getKey();
/* 648 */         Object localObject2 = localEntry.getValue();
/* 649 */         return LinkedHashMap.this.removeNode(HashMap.hash(localObject1), localObject1, localObject2, true, true) != null;
/*     */       }
/* 651 */       return false;
/*     */     }
/*     */     
/* 654 */     public final Spliterator<Map.Entry<K, V>> spliterator() { return Spliterators.spliterator(this, 81); }
/*     */     
/*     */ 
/*     */     public final void forEach(Consumer<? super Map.Entry<K, V>> paramConsumer)
/*     */     {
/* 659 */       if (paramConsumer == null)
/* 660 */         throw new NullPointerException();
/* 661 */       int i = LinkedHashMap.this.modCount;
/* 662 */       for (LinkedHashMap.Entry localEntry = LinkedHashMap.this.head; localEntry != null; localEntry = localEntry.after)
/* 663 */         paramConsumer.accept(localEntry);
/* 664 */       if (LinkedHashMap.this.modCount != i) {
/* 665 */         throw new ConcurrentModificationException();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer)
/*     */   {
/* 672 */     if (paramBiConsumer == null)
/* 673 */       throw new NullPointerException();
/* 674 */     int i = this.modCount;
/* 675 */     for (Entry localEntry = this.head; localEntry != null; localEntry = localEntry.after)
/* 676 */       paramBiConsumer.accept(localEntry.key, localEntry.value);
/* 677 */     if (this.modCount != i)
/* 678 */       throw new ConcurrentModificationException();
/*     */   }
/*     */   
/*     */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 682 */     if (paramBiFunction == null)
/* 683 */       throw new NullPointerException();
/* 684 */     int i = this.modCount;
/* 685 */     for (Entry localEntry = this.head; localEntry != null; localEntry = localEntry.after)
/* 686 */       localEntry.value = paramBiFunction.apply(localEntry.key, localEntry.value);
/* 687 */     if (this.modCount != i) {
/* 688 */       throw new ConcurrentModificationException();
/*     */     }
/*     */   }
/*     */   
/*     */   abstract class LinkedHashIterator
/*     */   {
/*     */     LinkedHashMap.Entry<K, V> next;
/*     */     LinkedHashMap.Entry<K, V> current;
/*     */     int expectedModCount;
/*     */     
/*     */     LinkedHashIterator() {
/* 699 */       this.next = LinkedHashMap.this.head;
/* 700 */       this.expectedModCount = LinkedHashMap.this.modCount;
/* 701 */       this.current = null;
/*     */     }
/*     */     
/*     */     public final boolean hasNext() {
/* 705 */       return this.next != null;
/*     */     }
/*     */     
/*     */     final LinkedHashMap.Entry<K, V> nextNode() {
/* 709 */       LinkedHashMap.Entry localEntry = this.next;
/* 710 */       if (LinkedHashMap.this.modCount != this.expectedModCount)
/* 711 */         throw new ConcurrentModificationException();
/* 712 */       if (localEntry == null)
/* 713 */         throw new NoSuchElementException();
/* 714 */       this.current = localEntry;
/* 715 */       this.next = localEntry.after;
/* 716 */       return localEntry;
/*     */     }
/*     */     
/*     */     public final void remove() {
/* 720 */       LinkedHashMap.Entry localEntry = this.current;
/* 721 */       if (localEntry == null)
/* 722 */         throw new IllegalStateException();
/* 723 */       if (LinkedHashMap.this.modCount != this.expectedModCount)
/* 724 */         throw new ConcurrentModificationException();
/* 725 */       this.current = null;
/* 726 */       Object localObject = localEntry.key;
/* 727 */       LinkedHashMap.this.removeNode(HashMap.hash(localObject), localObject, null, false, false);
/* 728 */       this.expectedModCount = LinkedHashMap.this.modCount;
/*     */     }
/*     */   }
/*     */   
/* 732 */   final class LinkedKeyIterator extends LinkedHashMap<K, V>.LinkedHashIterator implements Iterator<K> { LinkedKeyIterator() { super(); }
/*     */     
/* 734 */     public final K next() { return (K)nextNode().getKey(); }
/*     */   }
/*     */   
/* 737 */   final class LinkedValueIterator extends LinkedHashMap<K, V>.LinkedHashIterator implements Iterator<V> { LinkedValueIterator() { super(); }
/*     */     
/* 739 */     public final V next() { return (V)nextNode().value; }
/*     */   }
/*     */   
/* 742 */   final class LinkedEntryIterator extends LinkedHashMap<K, V>.LinkedHashIterator implements Iterator<Map.Entry<K, V>> { LinkedEntryIterator() { super(); }
/*     */     
/* 744 */     public final Map.Entry<K, V> next() { return nextNode(); }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/LinkedHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */