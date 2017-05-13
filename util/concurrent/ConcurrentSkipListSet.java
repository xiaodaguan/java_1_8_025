/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NavigableSet;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.Spliterator;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentSkipListSet<E>
/*     */   extends AbstractSet<E>
/*     */   implements NavigableSet<E>, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2479143111061671589L;
/*     */   private final ConcurrentNavigableMap<E, Object> m;
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long mapOffset;
/*     */   
/*     */   public ConcurrentSkipListSet()
/*     */   {
/* 112 */     this.m = new ConcurrentSkipListMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConcurrentSkipListSet(Comparator<? super E> paramComparator)
/*     */   {
/* 124 */     this.m = new ConcurrentSkipListMap(paramComparator);
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
/*     */   public ConcurrentSkipListSet(Collection<? extends E> paramCollection)
/*     */   {
/* 139 */     this.m = new ConcurrentSkipListMap();
/* 140 */     addAll(paramCollection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConcurrentSkipListSet(SortedSet<E> paramSortedSet)
/*     */   {
/* 152 */     this.m = new ConcurrentSkipListMap(paramSortedSet.comparator());
/* 153 */     addAll(paramSortedSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   ConcurrentSkipListSet(ConcurrentNavigableMap<E, Object> paramConcurrentNavigableMap)
/*     */   {
/* 160 */     this.m = paramConcurrentNavigableMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConcurrentSkipListSet<E> clone()
/*     */   {
/*     */     try
/*     */     {
/* 173 */       ConcurrentSkipListSet localConcurrentSkipListSet = (ConcurrentSkipListSet)super.clone();
/* 174 */       localConcurrentSkipListSet.setMap(new ConcurrentSkipListMap(this.m));
/* 175 */       return localConcurrentSkipListSet;
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 177 */       throw new InternalError();
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
/*     */   public int size()
/*     */   {
/* 200 */     return this.m.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 208 */     return this.m.isEmpty();
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
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 223 */     return this.m.containsKey(paramObject);
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 241 */     return this.m.putIfAbsent(paramE, Boolean.TRUE) == null;
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
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 259 */     return this.m.remove(paramObject, Boolean.TRUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 266 */     this.m.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 275 */     return this.m.navigableKeySet().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<E> descendingIterator()
/*     */   {
/* 284 */     return this.m.descendingKeySet().iterator();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 304 */     if (paramObject == this)
/* 305 */       return true;
/* 306 */     if (!(paramObject instanceof Set))
/* 307 */       return false;
/* 308 */     Collection localCollection = (Collection)paramObject;
/*     */     try {
/* 310 */       return (containsAll(localCollection)) && (localCollection.containsAll(this));
/*     */     } catch (ClassCastException localClassCastException) {
/* 312 */       return false;
/*     */     } catch (NullPointerException localNullPointerException) {}
/* 314 */     return false;
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
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 333 */     boolean bool = false;
/* 334 */     for (Object localObject : paramCollection)
/* 335 */       if (remove(localObject))
/* 336 */         bool = true;
/* 337 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public E lower(E paramE)
/*     */   {
/* 347 */     return (E)this.m.lowerKey(paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E floor(E paramE)
/*     */   {
/* 355 */     return (E)this.m.floorKey(paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E ceiling(E paramE)
/*     */   {
/* 363 */     return (E)this.m.ceilingKey(paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E higher(E paramE)
/*     */   {
/* 371 */     return (E)this.m.higherKey(paramE);
/*     */   }
/*     */   
/*     */   public E pollFirst() {
/* 375 */     Map.Entry localEntry = this.m.pollFirstEntry();
/* 376 */     return localEntry == null ? null : localEntry.getKey();
/*     */   }
/*     */   
/*     */   public E pollLast() {
/* 380 */     Map.Entry localEntry = this.m.pollLastEntry();
/* 381 */     return localEntry == null ? null : localEntry.getKey();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Comparator<? super E> comparator()
/*     */   {
/* 389 */     return this.m.comparator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E first()
/*     */   {
/* 396 */     return (E)this.m.firstKey();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E last()
/*     */   {
/* 403 */     return (E)this.m.lastKey();
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
/*     */   public NavigableSet<E> subSet(E paramE1, boolean paramBoolean1, E paramE2, boolean paramBoolean2)
/*     */   {
/* 417 */     return new ConcurrentSkipListSet(this.m.subMap(paramE1, paramBoolean1, paramE2, paramBoolean2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NavigableSet<E> headSet(E paramE, boolean paramBoolean)
/*     */   {
/* 427 */     return new ConcurrentSkipListSet(this.m.headMap(paramE, paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NavigableSet<E> tailSet(E paramE, boolean paramBoolean)
/*     */   {
/* 436 */     return new ConcurrentSkipListSet(this.m.tailMap(paramE, paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NavigableSet<E> subSet(E paramE1, E paramE2)
/*     */   {
/* 446 */     return subSet(paramE1, true, paramE2, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NavigableSet<E> headSet(E paramE)
/*     */   {
/* 455 */     return headSet(paramE, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NavigableSet<E> tailSet(E paramE)
/*     */   {
/* 464 */     return tailSet(paramE, true);
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
/*     */   public NavigableSet<E> descendingSet()
/*     */   {
/* 480 */     return new ConcurrentSkipListSet(this.m.descendingMap());
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
/*     */   public Spliterator<E> spliterator()
/*     */   {
/* 503 */     if ((this.m instanceof ConcurrentSkipListMap)) {
/* 504 */       return ((ConcurrentSkipListMap)this.m).keySpliterator();
/*     */     }
/* 506 */     return (Spliterator)((ConcurrentSkipListMap.SubMap)this.m).keyIterator();
/*     */   }
/*     */   
/*     */   private void setMap(ConcurrentNavigableMap<E, Object> paramConcurrentNavigableMap)
/*     */   {
/* 511 */     UNSAFE.putObjectVolatile(this, mapOffset, paramConcurrentNavigableMap);
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 518 */       UNSAFE = Unsafe.getUnsafe();
/* 519 */       Class localClass = ConcurrentSkipListSet.class;
/*     */       
/* 521 */       mapOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("m"));
/*     */     } catch (Exception localException) {
/* 523 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ConcurrentSkipListSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */