/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongUnaryOperator;
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
/*     */ public class AtomicLongArray
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2308431214976778248L;
/*  51 */   private static final Unsafe unsafe = ;
/*  52 */   private static final int base = unsafe.arrayBaseOffset(long[].class);
/*     */   private static final int shift;
/*     */   private final long[] array;
/*     */   
/*     */   static {
/*  57 */     int i = unsafe.arrayIndexScale(long[].class);
/*  58 */     if ((i & i - 1) != 0)
/*  59 */       throw new Error("data type scale not a power of two");
/*  60 */     shift = 31 - Integer.numberOfLeadingZeros(i);
/*     */   }
/*     */   
/*     */   private long checkedByteOffset(int paramInt) {
/*  64 */     if ((paramInt < 0) || (paramInt >= this.array.length)) {
/*  65 */       throw new IndexOutOfBoundsException("index " + paramInt);
/*     */     }
/*  67 */     return byteOffset(paramInt);
/*     */   }
/*     */   
/*     */   private static long byteOffset(int paramInt) {
/*  71 */     return (paramInt << shift) + base;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicLongArray(int paramInt)
/*     */   {
/*  81 */     this.array = new long[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicLongArray(long[] paramArrayOfLong)
/*     */   {
/*  93 */     this.array = ((long[])paramArrayOfLong.clone());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int length()
/*     */   {
/* 102 */     return this.array.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long get(int paramInt)
/*     */   {
/* 112 */     return getRaw(checkedByteOffset(paramInt));
/*     */   }
/*     */   
/*     */   private long getRaw(long paramLong) {
/* 116 */     return unsafe.getLongVolatile(this.array, paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(int paramInt, long paramLong)
/*     */   {
/* 126 */     unsafe.putLongVolatile(this.array, checkedByteOffset(paramInt), paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void lazySet(int paramInt, long paramLong)
/*     */   {
/* 137 */     unsafe.putOrderedLong(this.array, checkedByteOffset(paramInt), paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getAndSet(int paramInt, long paramLong)
/*     */   {
/* 149 */     return unsafe.getAndSetLong(this.array, checkedByteOffset(paramInt), paramLong);
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
/*     */   public final boolean compareAndSet(int paramInt, long paramLong1, long paramLong2)
/*     */   {
/* 163 */     return compareAndSetRaw(checkedByteOffset(paramInt), paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   private boolean compareAndSetRaw(long paramLong1, long paramLong2, long paramLong3) {
/* 167 */     return unsafe.compareAndSwapLong(this.array, paramLong1, paramLong2, paramLong3);
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
/*     */   public final boolean weakCompareAndSet(int paramInt, long paramLong1, long paramLong2)
/*     */   {
/* 184 */     return compareAndSet(paramInt, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getAndIncrement(int paramInt)
/*     */   {
/* 194 */     return getAndAdd(paramInt, 1L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getAndDecrement(int paramInt)
/*     */   {
/* 204 */     return getAndAdd(paramInt, -1L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getAndAdd(int paramInt, long paramLong)
/*     */   {
/* 215 */     return unsafe.getAndAddLong(this.array, checkedByteOffset(paramInt), paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long incrementAndGet(int paramInt)
/*     */   {
/* 225 */     return getAndAdd(paramInt, 1L) + 1L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long decrementAndGet(int paramInt)
/*     */   {
/* 235 */     return getAndAdd(paramInt, -1L) - 1L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long addAndGet(int paramInt, long paramLong)
/*     */   {
/* 246 */     return getAndAdd(paramInt, paramLong) + paramLong;
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
/*     */   public final long getAndUpdate(int paramInt, LongUnaryOperator paramLongUnaryOperator)
/*     */   {
/* 261 */     long l1 = checkedByteOffset(paramInt);
/*     */     long l2;
/*     */     long l3;
/* 264 */     do { l2 = getRaw(l1);
/* 265 */       l3 = paramLongUnaryOperator.applyAsLong(l2);
/* 266 */     } while (!compareAndSetRaw(l1, l2, l3));
/* 267 */     return l2;
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
/*     */   public final long updateAndGet(int paramInt, LongUnaryOperator paramLongUnaryOperator)
/*     */   {
/* 282 */     long l1 = checkedByteOffset(paramInt);
/*     */     long l2;
/*     */     long l3;
/* 285 */     do { l2 = getRaw(l1);
/* 286 */       l3 = paramLongUnaryOperator.applyAsLong(l2);
/* 287 */     } while (!compareAndSetRaw(l1, l2, l3));
/* 288 */     return l3;
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
/*     */   public final long getAndAccumulate(int paramInt, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*     */   {
/* 308 */     long l1 = checkedByteOffset(paramInt);
/*     */     long l2;
/*     */     long l3;
/* 311 */     do { l2 = getRaw(l1);
/* 312 */       l3 = paramLongBinaryOperator.applyAsLong(l2, paramLong);
/* 313 */     } while (!compareAndSetRaw(l1, l2, l3));
/* 314 */     return l2;
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
/*     */   public final long accumulateAndGet(int paramInt, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*     */   {
/* 334 */     long l1 = checkedByteOffset(paramInt);
/*     */     long l2;
/*     */     long l3;
/* 337 */     do { l2 = getRaw(l1);
/* 338 */       l3 = paramLongBinaryOperator.applyAsLong(l2, paramLong);
/* 339 */     } while (!compareAndSetRaw(l1, l2, l3));
/* 340 */     return l3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 348 */     int i = this.array.length - 1;
/* 349 */     if (i == -1) {
/* 350 */       return "[]";
/*     */     }
/* 352 */     StringBuilder localStringBuilder = new StringBuilder();
/* 353 */     localStringBuilder.append('[');
/* 354 */     for (int j = 0;; j++) {
/* 355 */       localStringBuilder.append(getRaw(byteOffset(j)));
/* 356 */       if (j == i)
/* 357 */         return ']';
/* 358 */       localStringBuilder.append(',').append(' ');
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicLongArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */