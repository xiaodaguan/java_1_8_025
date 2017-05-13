/*     */ package java.util.concurrent.atomic;
/*     */ 
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
/*     */ public class AtomicStampedReference<V>
/*     */ {
/*     */   private volatile Pair<V> pair;
/*     */   
/*     */   private static class Pair<T>
/*     */   {
/*     */     final T reference;
/*     */     final int stamp;
/*     */     
/*     */     private Pair(T paramT, int paramInt)
/*     */     {
/*  56 */       this.reference = paramT;
/*  57 */       this.stamp = paramInt;
/*     */     }
/*     */     
/*  60 */     static <T> Pair<T> of(T paramT, int paramInt) { return new Pair(paramT, paramInt); }
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
/*     */   public AtomicStampedReference(V paramV, int paramInt)
/*     */   {
/*  74 */     this.pair = Pair.of(paramV, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V getReference()
/*     */   {
/*  83 */     return (V)this.pair.reference;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getStamp()
/*     */   {
/*  92 */     return this.pair.stamp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V get(int[] paramArrayOfInt)
/*     */   {
/* 104 */     Pair localPair = this.pair;
/* 105 */     paramArrayOfInt[0] = localPair.stamp;
/* 106 */     return (V)localPair.reference;
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
/*     */   public boolean weakCompareAndSet(V paramV1, V paramV2, int paramInt1, int paramInt2)
/*     */   {
/* 129 */     return compareAndSet(paramV1, paramV2, paramInt1, paramInt2);
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
/*     */   public boolean compareAndSet(V paramV1, V paramV2, int paramInt1, int paramInt2)
/*     */   {
/* 149 */     Pair localPair = this.pair;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 155 */     return (paramV1 == localPair.reference) && (paramInt1 == localPair.stamp) && (((paramV2 == localPair.reference) && (paramInt2 == localPair.stamp)) || (casPair(localPair, Pair.of(paramV2, paramInt2))));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(V paramV, int paramInt)
/*     */   {
/* 165 */     Pair localPair = this.pair;
/* 166 */     if ((paramV != localPair.reference) || (paramInt != localPair.stamp)) {
/* 167 */       this.pair = Pair.of(paramV, paramInt);
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
/*     */   public boolean attemptStamp(V paramV, int paramInt)
/*     */   {
/* 184 */     Pair localPair = this.pair;
/*     */     
/*     */ 
/*     */ 
/* 188 */     return (paramV == localPair.reference) && ((paramInt == localPair.stamp) || (casPair(localPair, Pair.of(paramV, paramInt))));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 193 */   private static final Unsafe UNSAFE = ;
/*     */   
/* 195 */   private static final long pairOffset = objectFieldOffset(UNSAFE, "pair", AtomicStampedReference.class);
/*     */   
/*     */   private boolean casPair(Pair<V> paramPair1, Pair<V> paramPair2) {
/* 198 */     return UNSAFE.compareAndSwapObject(this, pairOffset, paramPair1, paramPair2);
/*     */   }
/*     */   
/*     */   static long objectFieldOffset(Unsafe paramUnsafe, String paramString, Class<?> paramClass)
/*     */   {
/*     */     try {
/* 204 */       return paramUnsafe.objectFieldOffset(paramClass.getDeclaredField(paramString));
/*     */     }
/*     */     catch (NoSuchFieldException localNoSuchFieldException) {
/* 207 */       NoSuchFieldError localNoSuchFieldError = new NoSuchFieldError(paramString);
/* 208 */       localNoSuchFieldError.initCause(localNoSuchFieldException);
/* 209 */       throw localNoSuchFieldError;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicStampedReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */