/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CopyOnWriteArraySet<E>
/*     */   extends AbstractSet<E>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5457747651344034263L;
/*     */   private final CopyOnWriteArrayList<E> al;
/*     */   
/*     */   public CopyOnWriteArraySet()
/*     */   {
/* 106 */     this.al = new CopyOnWriteArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CopyOnWriteArraySet(Collection<? extends E> paramCollection)
/*     */   {
/* 117 */     if (paramCollection.getClass() == CopyOnWriteArraySet.class) {
/* 118 */       CopyOnWriteArraySet localCopyOnWriteArraySet = (CopyOnWriteArraySet)paramCollection;
/*     */       
/* 120 */       this.al = new CopyOnWriteArrayList(localCopyOnWriteArraySet.al);
/*     */     }
/*     */     else {
/* 123 */       this.al = new CopyOnWriteArrayList();
/* 124 */       this.al.addAllAbsent(paramCollection);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 134 */     return this.al.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 143 */     return this.al.isEmpty();
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
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 156 */     return this.al.contains(paramObject);
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
/*     */   public Object[] toArray()
/*     */   {
/* 176 */     return this.al.toArray();
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
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 221 */     return this.al.toArray(paramArrayOfT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 229 */     this.al.clear();
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
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 245 */     return this.al.remove(paramObject);
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 261 */     return this.al.addIfAbsent(paramE);
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
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 276 */     return this.al.containsAll(paramCollection);
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
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 293 */     return this.al.addAllAbsent(paramCollection) > 0;
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
/* 312 */     return this.al.removeAll(paramCollection);
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
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 333 */     return this.al.retainAll(paramCollection);
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
/*     */   public Iterator<E> iterator()
/*     */   {
/* 348 */     return this.al.iterator();
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
/* 368 */     if (paramObject == this)
/* 369 */       return true;
/* 370 */     if (!(paramObject instanceof Set))
/* 371 */       return false;
/* 372 */     Set localSet = (Set)paramObject;
/* 373 */     Iterator localIterator = localSet.iterator();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 379 */     Object[] arrayOfObject = this.al.getArray();
/* 380 */     int i = arrayOfObject.length;
/*     */     
/* 382 */     boolean[] arrayOfBoolean = new boolean[i];
/* 383 */     int j = 0;
/* 384 */     if (localIterator.hasNext()) {
/* 385 */       j++; if (j > i)
/* 386 */         return false;
/* 387 */       Object localObject = localIterator.next();
/* 388 */       for (int k = 0;; k++) { if (k >= i) break label129;
/* 389 */         if ((arrayOfBoolean[k] == 0) && (eq(localObject, arrayOfObject[k]))) {
/* 390 */           arrayOfBoolean[k] = true;
/* 391 */           break;
/*     */         } }
/*     */       label129:
/* 394 */       return false;
/*     */     }
/* 396 */     return j == i;
/*     */   }
/*     */   
/*     */   public boolean removeIf(Predicate<? super E> paramPredicate) {
/* 400 */     return this.al.removeIf(paramPredicate);
/*     */   }
/*     */   
/*     */   public void forEach(Consumer<? super E> paramConsumer) {
/* 404 */     this.al.forEach(paramConsumer);
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
/*     */   public Spliterator<E> spliterator()
/*     */   {
/* 424 */     return Spliterators.spliterator(this.al.getArray(), 1025);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean eq(Object paramObject1, Object paramObject2)
/*     */   {
/* 431 */     return paramObject1 == null ? false : paramObject2 == null ? true : paramObject1.equals(paramObject2);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/CopyOnWriteArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */