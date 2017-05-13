/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSet<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Set<E>
/*     */ {
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  86 */     if (paramObject == this) {
/*  87 */       return true;
/*     */     }
/*  89 */     if (!(paramObject instanceof Set))
/*  90 */       return false;
/*  91 */     Collection localCollection = (Collection)paramObject;
/*  92 */     if (localCollection.size() != size())
/*  93 */       return false;
/*     */     try {
/*  95 */       return containsAll(localCollection);
/*     */     } catch (ClassCastException localClassCastException) {
/*  97 */       return false;
/*     */     } catch (NullPointerException localNullPointerException) {}
/*  99 */     return false;
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
/* 121 */     int i = 0;
/* 122 */     Iterator localIterator = iterator();
/* 123 */     while (localIterator.hasNext()) {
/* 124 */       Object localObject = localIterator.next();
/* 125 */       if (localObject != null)
/* 126 */         i += localObject.hashCode();
/*     */     }
/* 128 */     return i;
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
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 169 */     Objects.requireNonNull(paramCollection);
/* 170 */     boolean bool = false;
/*     */     Iterator localIterator;
/* 172 */     if (size() > paramCollection.size()) {
/* 173 */       for (localIterator = paramCollection.iterator(); localIterator.hasNext();)
/* 174 */         bool |= remove(localIterator.next());
/*     */     } else {
/* 176 */       for (localIterator = iterator(); localIterator.hasNext();) {
/* 177 */         if (paramCollection.contains(localIterator.next())) {
/* 178 */           localIterator.remove();
/* 179 */           bool = true;
/*     */         }
/*     */       }
/*     */     }
/* 183 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/AbstractSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */