/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class AtomicBoolean
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4654671469794556979L;
/*  53 */   private static final Unsafe unsafe = ;
/*     */   private static final long valueOffset;
/*     */   private volatile int value;
/*     */   
/*     */   static {
/*     */     try {
/*  59 */       valueOffset = unsafe.objectFieldOffset(AtomicBoolean.class.getDeclaredField("value"));
/*  60 */     } catch (Exception localException) { throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AtomicBoolean(boolean paramBoolean)
/*     */   {
/*  71 */     this.value = (paramBoolean ? 1 : 0);
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
/*     */   public final boolean get()
/*     */   {
/*  86 */     return this.value != 0;
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
/*     */   public final boolean compareAndSet(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  99 */     int i = paramBoolean1 ? 1 : 0;
/* 100 */     int j = paramBoolean2 ? 1 : 0;
/* 101 */     return unsafe.compareAndSwapInt(this, valueOffset, i, j);
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
/*     */   public boolean weakCompareAndSet(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 117 */     int i = paramBoolean1 ? 1 : 0;
/* 118 */     int j = paramBoolean2 ? 1 : 0;
/* 119 */     return unsafe.compareAndSwapInt(this, valueOffset, i, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(boolean paramBoolean)
/*     */   {
/* 128 */     this.value = (paramBoolean ? 1 : 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void lazySet(boolean paramBoolean)
/*     */   {
/* 138 */     int i = paramBoolean ? 1 : 0;
/* 139 */     unsafe.putOrderedInt(this, valueOffset, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean getAndSet(boolean paramBoolean)
/*     */   {
/*     */     boolean bool;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 151 */       bool = get();
/* 152 */     } while (!compareAndSet(bool, paramBoolean));
/* 153 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 161 */     return Boolean.toString(get());
/*     */   }
/*     */   
/*     */   public AtomicBoolean() {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicBoolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */