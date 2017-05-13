/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntUnaryOperator;
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
/*     */ public class AtomicInteger
/*     */   extends Number
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6214790243416807050L;
/*  58 */   private static final Unsafe unsafe = ;
/*     */   private static final long valueOffset;
/*     */   private volatile int value;
/*     */   
/*     */   static {
/*     */     try {
/*  64 */       valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
/*  65 */     } catch (Exception localException) { throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicInteger(int paramInt)
/*     */   {
/*  76 */     this.value = paramInt;
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
/*     */   public final int get()
/*     */   {
/*  91 */     return this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(int paramInt)
/*     */   {
/* 100 */     this.value = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void lazySet(int paramInt)
/*     */   {
/* 110 */     unsafe.putOrderedInt(this, valueOffset, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndSet(int paramInt)
/*     */   {
/* 120 */     return unsafe.getAndSetInt(this, valueOffset, paramInt);
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
/*     */   public final boolean compareAndSet(int paramInt1, int paramInt2)
/*     */   {
/* 133 */     return unsafe.compareAndSwapInt(this, valueOffset, paramInt1, paramInt2);
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
/*     */   public final boolean weakCompareAndSet(int paramInt1, int paramInt2)
/*     */   {
/* 149 */     return unsafe.compareAndSwapInt(this, valueOffset, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndIncrement()
/*     */   {
/* 158 */     return unsafe.getAndAddInt(this, valueOffset, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndDecrement()
/*     */   {
/* 167 */     return unsafe.getAndAddInt(this, valueOffset, -1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndAdd(int paramInt)
/*     */   {
/* 177 */     return unsafe.getAndAddInt(this, valueOffset, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int incrementAndGet()
/*     */   {
/* 186 */     return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int decrementAndGet()
/*     */   {
/* 195 */     return unsafe.getAndAddInt(this, valueOffset, -1) - 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int addAndGet(int paramInt)
/*     */   {
/* 205 */     return unsafe.getAndAddInt(this, valueOffset, paramInt) + paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndUpdate(IntUnaryOperator paramIntUnaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 221 */       i = get();
/* 222 */       j = paramIntUnaryOperator.applyAsInt(i);
/* 223 */     } while (!compareAndSet(i, j));
/* 224 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int updateAndGet(IntUnaryOperator paramIntUnaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 240 */       i = get();
/* 241 */       j = paramIntUnaryOperator.applyAsInt(i);
/* 242 */     } while (!compareAndSet(i, j));
/* 243 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndAccumulate(int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 264 */       i = get();
/* 265 */       j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 266 */     } while (!compareAndSet(i, j));
/* 267 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int accumulateAndGet(int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 288 */       i = get();
/* 289 */       j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 290 */     } while (!compareAndSet(i, j));
/* 291 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 299 */     return Integer.toString(get());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 306 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 315 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 324 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 333 */     return get();
/*     */   }
/*     */   
/*     */   public AtomicInteger() {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */