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
/*     */ 
/*     */ public abstract class AbstractSequentialList<E>
/*     */   extends AbstractList<E>
/*     */ {
/*     */   public E get(int paramInt)
/*     */   {
/*     */     try
/*     */     {
/*  88 */       return (E)listIterator(paramInt).next();
/*     */     } catch (NoSuchElementException localNoSuchElementException) {
/*  90 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */   public E set(int paramInt, E paramE)
/*     */   {
/*     */     try
/*     */     {
/* 115 */       ListIterator localListIterator = listIterator(paramInt);
/* 116 */       Object localObject = localListIterator.next();
/* 117 */       localListIterator.set(paramE);
/* 118 */       return (E)localObject;
/*     */     } catch (NoSuchElementException localNoSuchElementException) {
/* 120 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */   public void add(int paramInt, E paramE)
/*     */   {
/*     */     try
/*     */     {
/* 146 */       listIterator(paramInt).add(paramE);
/*     */     } catch (NoSuchElementException localNoSuchElementException) {
/* 148 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */   public E remove(int paramInt)
/*     */   {
/*     */     try
/*     */     {
/* 171 */       ListIterator localListIterator = listIterator(paramInt);
/* 172 */       Object localObject = localListIterator.next();
/* 173 */       localListIterator.remove();
/* 174 */       return (E)localObject;
/*     */     } catch (NoSuchElementException localNoSuchElementException) {
/* 176 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*     */   {
/*     */     try
/*     */     {
/* 214 */       boolean bool = false;
/* 215 */       ListIterator localListIterator = listIterator(paramInt);
/* 216 */       Iterator localIterator = paramCollection.iterator();
/* 217 */       while (localIterator.hasNext()) {
/* 218 */         localListIterator.add(localIterator.next());
/* 219 */         bool = true;
/*     */       }
/* 221 */       return bool;
/*     */     } catch (NoSuchElementException localNoSuchElementException) {
/* 223 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */   public Iterator<E> iterator()
/*     */   {
/* 239 */     return listIterator();
/*     */   }
/*     */   
/*     */   public abstract ListIterator<E> listIterator(int paramInt);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/AbstractSequentialList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */