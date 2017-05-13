/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AtomicReference<V>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1848883965231344442L;
/*  52 */   private static final Unsafe unsafe = ;
/*     */   private static final long valueOffset;
/*     */   private volatile V value;
/*     */   
/*     */   static {
/*     */     try {
/*  58 */       valueOffset = unsafe.objectFieldOffset(AtomicReference.class.getDeclaredField("value"));
/*  59 */     } catch (Exception localException) { throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicReference(V paramV)
/*     */   {
/*  70 */     this.value = paramV;
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
/*     */   public final V get()
/*     */   {
/*  85 */     return (V)this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(V paramV)
/*     */   {
/*  94 */     this.value = paramV;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void lazySet(V paramV)
/*     */   {
/* 104 */     unsafe.putOrderedObject(this, valueOffset, paramV);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean compareAndSet(V paramV1, V paramV2)
/*     */   {
/* 116 */     return unsafe.compareAndSwapObject(this, valueOffset, paramV1, paramV2);
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
/*     */   public final boolean weakCompareAndSet(V paramV1, V paramV2)
/*     */   {
/* 132 */     return unsafe.compareAndSwapObject(this, valueOffset, paramV1, paramV2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V getAndSet(V paramV)
/*     */   {
/* 143 */     return (V)unsafe.getAndSetObject(this, valueOffset, paramV);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V getAndUpdate(UnaryOperator<V> paramUnaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 159 */       localObject1 = get();
/* 160 */       localObject2 = paramUnaryOperator.apply(localObject1);
/* 161 */     } while (!compareAndSet(localObject1, localObject2));
/* 162 */     return (V)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V updateAndGet(UnaryOperator<V> paramUnaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 178 */       localObject1 = get();
/* 179 */       localObject2 = paramUnaryOperator.apply(localObject1);
/* 180 */     } while (!compareAndSet(localObject1, localObject2));
/* 181 */     return (V)localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V getAndAccumulate(V paramV, BinaryOperator<V> paramBinaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 202 */       localObject1 = get();
/* 203 */       localObject2 = paramBinaryOperator.apply(localObject1, paramV);
/* 204 */     } while (!compareAndSet(localObject1, localObject2));
/* 205 */     return (V)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V accumulateAndGet(V paramV, BinaryOperator<V> paramBinaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 226 */       localObject1 = get();
/* 227 */       localObject2 = paramBinaryOperator.apply(localObject1, paramV);
/* 228 */     } while (!compareAndSet(localObject1, localObject2));
/* 229 */     return (V)localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 237 */     return String.valueOf(get());
/*     */   }
/*     */   
/*     */   public AtomicReference() {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */