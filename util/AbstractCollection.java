/*     */ package java.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCollection<E>
/*     */   implements Collection<E>
/*     */ {
/*     */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*     */   
/*     */   public abstract Iterator<E> iterator();
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  86 */     return size() == 0;
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
/*  99 */     Iterator localIterator = iterator();
/* 100 */     if (paramObject == null) {
/* 101 */       do { if (!localIterator.hasNext()) break;
/* 102 */       } while (localIterator.next() != null);
/* 103 */       return true;
/*     */     }
/* 105 */     while (localIterator.hasNext()) {
/* 106 */       if (paramObject.equals(localIterator.next()))
/* 107 */         return true;
/*     */     }
/* 109 */     return false;
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
/*     */   public Object[] toArray()
/*     */   {
/* 136 */     Object[] arrayOfObject = new Object[size()];
/* 137 */     Iterator localIterator = iterator();
/* 138 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 139 */       if (!localIterator.hasNext())
/* 140 */         return Arrays.copyOf(arrayOfObject, i);
/* 141 */       arrayOfObject[i] = localIterator.next();
/*     */     }
/* 143 */     return localIterator.hasNext() ? finishToArray(arrayOfObject, localIterator) : arrayOfObject;
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
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 176 */     int i = size();
/*     */     
/*     */ 
/* 179 */     Object[] arrayOfObject = paramArrayOfT.length >= i ? paramArrayOfT : (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/* 180 */     Iterator localIterator = iterator();
/*     */     
/* 182 */     for (int j = 0; j < arrayOfObject.length; j++) {
/* 183 */       if (!localIterator.hasNext()) {
/* 184 */         if (paramArrayOfT == arrayOfObject) {
/* 185 */           arrayOfObject[j] = null;
/* 186 */         } else { if (paramArrayOfT.length < j) {
/* 187 */             return Arrays.copyOf(arrayOfObject, j);
/*     */           }
/* 189 */           System.arraycopy(arrayOfObject, 0, paramArrayOfT, 0, j);
/* 190 */           if (paramArrayOfT.length > j) {
/* 191 */             paramArrayOfT[j] = null;
/*     */           }
/*     */         }
/* 194 */         return paramArrayOfT;
/*     */       }
/* 196 */       arrayOfObject[j] = localIterator.next();
/*     */     }
/*     */     
/* 199 */     return localIterator.hasNext() ? finishToArray(arrayOfObject, localIterator) : arrayOfObject;
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
/*     */   private static <T> T[] finishToArray(T[] paramArrayOfT, Iterator<?> paramIterator)
/*     */   {
/* 222 */     int i = paramArrayOfT.length;
/* 223 */     while (paramIterator.hasNext()) {
/* 224 */       int j = paramArrayOfT.length;
/* 225 */       if (i == j) {
/* 226 */         int k = j + (j >> 1) + 1;
/*     */         
/* 228 */         if (k - 2147483639 > 0)
/* 229 */           k = hugeCapacity(j + 1);
/* 230 */         paramArrayOfT = Arrays.copyOf(paramArrayOfT, k);
/*     */       }
/* 232 */       paramArrayOfT[(i++)] = paramIterator.next();
/*     */     }
/*     */     
/* 235 */     return i == paramArrayOfT.length ? paramArrayOfT : Arrays.copyOf(paramArrayOfT, i);
/*     */   }
/*     */   
/*     */   private static int hugeCapacity(int paramInt) {
/* 239 */     if (paramInt < 0) {
/* 240 */       throw new OutOfMemoryError("Required array size too large");
/*     */     }
/* 242 */     return paramInt > 2147483639 ? Integer.MAX_VALUE : 2147483639;
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 262 */     throw new UnsupportedOperationException();
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
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 282 */     Iterator localIterator = iterator();
/* 283 */     if (paramObject == null) {
/* 284 */       do { if (!localIterator.hasNext()) break;
/* 285 */       } while (localIterator.next() != null);
/* 286 */       localIterator.remove();
/* 287 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 291 */     while (localIterator.hasNext()) {
/* 292 */       if (paramObject.equals(localIterator.next())) {
/* 293 */         localIterator.remove();
/* 294 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 298 */     return false;
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
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 317 */     for (Object localObject : paramCollection)
/* 318 */       if (!contains(localObject))
/* 319 */         return false;
/* 320 */     return true;
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
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 342 */     boolean bool = false;
/* 343 */     for (Object localObject : paramCollection)
/* 344 */       if (add(localObject))
/* 345 */         bool = true;
/* 346 */     return bool;
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
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 371 */     Objects.requireNonNull(paramCollection);
/* 372 */     boolean bool = false;
/* 373 */     Iterator localIterator = iterator();
/* 374 */     while (localIterator.hasNext()) {
/* 375 */       if (paramCollection.contains(localIterator.next())) {
/* 376 */         localIterator.remove();
/* 377 */         bool = true;
/*     */       }
/*     */     }
/* 380 */     return bool;
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
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 405 */     Objects.requireNonNull(paramCollection);
/* 406 */     boolean bool = false;
/* 407 */     Iterator localIterator = iterator();
/* 408 */     while (localIterator.hasNext()) {
/* 409 */       if (!paramCollection.contains(localIterator.next())) {
/* 410 */         localIterator.remove();
/* 411 */         bool = true;
/*     */       }
/*     */     }
/* 414 */     return bool;
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
/*     */   public void clear()
/*     */   {
/* 433 */     Iterator localIterator = iterator();
/* 434 */     while (localIterator.hasNext()) {
/* 435 */       localIterator.next();
/* 436 */       localIterator.remove();
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
/*     */   public String toString()
/*     */   {
/* 454 */     Iterator localIterator = iterator();
/* 455 */     if (!localIterator.hasNext()) {
/* 456 */       return "[]";
/*     */     }
/* 458 */     StringBuilder localStringBuilder = new StringBuilder();
/* 459 */     localStringBuilder.append('[');
/*     */     for (;;) {
/* 461 */       Object localObject = localIterator.next();
/* 462 */       localStringBuilder.append(localObject == this ? "(this Collection)" : localObject);
/* 463 */       if (!localIterator.hasNext())
/* 464 */         return ']';
/* 465 */       localStringBuilder.append(',').append(' ');
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/AbstractCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */