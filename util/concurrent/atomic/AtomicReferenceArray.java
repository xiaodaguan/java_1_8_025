/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.UnaryOperator;
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
/*     */ public class AtomicReferenceArray<E>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6209656149925076980L;
/*     */   private static final Unsafe unsafe;
/*     */   private static final int base;
/*     */   private static final int shift;
/*     */   private static final long arrayFieldOffset;
/*     */   private final Object[] array;
/*     */   
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  63 */       unsafe = Unsafe.getUnsafe();
/*     */       
/*  65 */       arrayFieldOffset = unsafe.objectFieldOffset(AtomicReferenceArray.class.getDeclaredField("array"));
/*  66 */       base = unsafe.arrayBaseOffset(Object[].class);
/*  67 */       int i = unsafe.arrayIndexScale(Object[].class);
/*  68 */       if ((i & i - 1) != 0)
/*  69 */         throw new Error("data type scale not a power of two");
/*  70 */       shift = 31 - Integer.numberOfLeadingZeros(i);
/*     */     } catch (Exception localException) {
/*  72 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private long checkedByteOffset(int paramInt) {
/*  77 */     if ((paramInt < 0) || (paramInt >= this.array.length)) {
/*  78 */       throw new IndexOutOfBoundsException("index " + paramInt);
/*     */     }
/*  80 */     return byteOffset(paramInt);
/*     */   }
/*     */   
/*     */   private static long byteOffset(int paramInt) {
/*  84 */     return (paramInt << shift) + base;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicReferenceArray(int paramInt)
/*     */   {
/*  94 */     this.array = new Object[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicReferenceArray(E[] paramArrayOfE)
/*     */   {
/* 106 */     this.array = Arrays.copyOf(paramArrayOfE, paramArrayOfE.length, Object[].class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int length()
/*     */   {
/* 115 */     return this.array.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final E get(int paramInt)
/*     */   {
/* 125 */     return (E)getRaw(checkedByteOffset(paramInt));
/*     */   }
/*     */   
/*     */   private E getRaw(long paramLong)
/*     */   {
/* 130 */     return (E)unsafe.getObjectVolatile(this.array, paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(int paramInt, E paramE)
/*     */   {
/* 140 */     unsafe.putObjectVolatile(this.array, checkedByteOffset(paramInt), paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void lazySet(int paramInt, E paramE)
/*     */   {
/* 151 */     unsafe.putOrderedObject(this.array, checkedByteOffset(paramInt), paramE);
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
/*     */   public final E getAndSet(int paramInt, E paramE)
/*     */   {
/* 164 */     return (E)unsafe.getAndSetObject(this.array, checkedByteOffset(paramInt), paramE);
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
/*     */   public final boolean compareAndSet(int paramInt, E paramE1, E paramE2)
/*     */   {
/* 178 */     return compareAndSetRaw(checkedByteOffset(paramInt), paramE1, paramE2);
/*     */   }
/*     */   
/*     */   private boolean compareAndSetRaw(long paramLong, E paramE1, E paramE2) {
/* 182 */     return unsafe.compareAndSwapObject(this.array, paramLong, paramE1, paramE2);
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
/*     */   public final boolean weakCompareAndSet(int paramInt, E paramE1, E paramE2)
/*     */   {
/* 199 */     return compareAndSet(paramInt, paramE1, paramE2);
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
/*     */   public final E getAndUpdate(int paramInt, UnaryOperator<E> paramUnaryOperator)
/*     */   {
/* 214 */     long l = checkedByteOffset(paramInt);
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 217 */     do { localObject1 = getRaw(l);
/* 218 */       localObject2 = paramUnaryOperator.apply(localObject1);
/* 219 */     } while (!compareAndSetRaw(l, localObject1, localObject2));
/* 220 */     return (E)localObject1;
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
/*     */   public final E updateAndGet(int paramInt, UnaryOperator<E> paramUnaryOperator)
/*     */   {
/* 235 */     long l = checkedByteOffset(paramInt);
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 238 */     do { localObject1 = getRaw(l);
/* 239 */       localObject2 = paramUnaryOperator.apply(localObject1);
/* 240 */     } while (!compareAndSetRaw(l, localObject1, localObject2));
/* 241 */     return (E)localObject2;
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
/*     */   public final E getAndAccumulate(int paramInt, E paramE, BinaryOperator<E> paramBinaryOperator)
/*     */   {
/* 261 */     long l = checkedByteOffset(paramInt);
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 264 */     do { localObject1 = getRaw(l);
/* 265 */       localObject2 = paramBinaryOperator.apply(localObject1, paramE);
/* 266 */     } while (!compareAndSetRaw(l, localObject1, localObject2));
/* 267 */     return (E)localObject1;
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
/*     */   public final E accumulateAndGet(int paramInt, E paramE, BinaryOperator<E> paramBinaryOperator)
/*     */   {
/* 287 */     long l = checkedByteOffset(paramInt);
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 290 */     do { localObject1 = getRaw(l);
/* 291 */       localObject2 = paramBinaryOperator.apply(localObject1, paramE);
/* 292 */     } while (!compareAndSetRaw(l, localObject1, localObject2));
/* 293 */     return (E)localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 301 */     int i = this.array.length - 1;
/* 302 */     if (i == -1) {
/* 303 */       return "[]";
/*     */     }
/* 305 */     StringBuilder localStringBuilder = new StringBuilder();
/* 306 */     localStringBuilder.append('[');
/* 307 */     for (int j = 0;; j++) {
/* 308 */       localStringBuilder.append(getRaw(byteOffset(j)));
/* 309 */       if (j == i)
/* 310 */         return ']';
/* 311 */       localStringBuilder.append(',').append(' ');
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException, InvalidObjectException
/*     */   {
/* 322 */     Object localObject = paramObjectInputStream.readFields().get("array", null);
/* 323 */     if ((localObject == null) || (!localObject.getClass().isArray()))
/* 324 */       throw new InvalidObjectException("Not array type");
/* 325 */     if (localObject.getClass() != Object[].class)
/* 326 */       localObject = Arrays.copyOf((Object[])localObject, Array.getLength(localObject), Object[].class);
/* 327 */     unsafe.putObjectVolatile(this, arrayFieldOffset, localObject);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicReferenceArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */