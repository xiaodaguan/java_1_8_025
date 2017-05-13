/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongAccumulator
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   private final LongBinaryOperator function;
/*     */   private final long identity;
/*     */   
/*     */   public LongAccumulator(LongBinaryOperator paramLongBinaryOperator, long paramLong)
/*     */   {
/*  94 */     this.function = paramLongBinaryOperator;
/*  95 */     this.base = (this.identity = paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */   public void accumulate(long paramLong)
/*     */   {
/*     */     Striped64.Cell[] arrayOfCell;
/*     */     
/*     */     long l1;
/*     */     long l3;
/* 105 */     if (((arrayOfCell = this.cells) != null) || (
/* 106 */       ((l3 = this.function.applyAsLong(l1 = this.base, paramLong)) != l1) && (!casBase(l1, l3)))) {
/* 107 */       boolean bool = true;
/* 108 */       int i; Striped64.Cell localCell; if ((arrayOfCell != null) && ((i = arrayOfCell.length - 1) >= 0) && 
/* 109 */         ((localCell = arrayOfCell[(getProbe() & i)]) != null))
/*     */       {
/*     */         long l2;
/* 112 */         if ((bool = ((l3 = this.function.applyAsLong(l2 = localCell.value, paramLong)) == l2) || (localCell.cas(l2, l3)) ? 1 : 0) != 0) {}
/* 113 */       } else { longAccumulate(paramLong, this.function, bool);
/*     */       }
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
/*     */   public long get()
/*     */   {
/* 127 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 128 */     long l = this.base;
/* 129 */     if (arrayOfCell != null) {
/* 130 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 131 */         if ((localCell = arrayOfCell[i]) != null)
/* 132 */           l = this.function.applyAsLong(l, localCell.value);
/*     */       }
/*     */     }
/* 135 */     return l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 147 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 148 */     this.base = this.identity;
/* 149 */     if (arrayOfCell != null) {
/* 150 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 151 */         if ((localCell = arrayOfCell[i]) != null) {
/* 152 */           localCell.value = this.identity;
/*     */         }
/*     */       }
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
/*     */   public long getThenReset()
/*     */   {
/* 168 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 169 */     long l1 = this.base;
/* 170 */     this.base = this.identity;
/* 171 */     if (arrayOfCell != null) {
/* 172 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 173 */         if ((localCell = arrayOfCell[i]) != null) {
/* 174 */           long l2 = localCell.value;
/* 175 */           localCell.value = this.identity;
/* 176 */           l1 = this.function.applyAsLong(l1, l2);
/*     */         }
/*     */       }
/*     */     }
/* 180 */     return l1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 188 */     return Long.toString(get());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 197 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 205 */     return (int)get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 213 */     return (float)get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 221 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class SerializationProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7249069246863182397L;
/*     */     
/*     */ 
/*     */ 
/*     */     private final long value;
/*     */     
/*     */ 
/*     */ 
/*     */     private final LongBinaryOperator function;
/*     */     
/*     */ 
/*     */ 
/*     */     private final long identity;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     SerializationProxy(LongAccumulator paramLongAccumulator)
/*     */     {
/* 249 */       this.function = paramLongAccumulator.function;
/* 250 */       this.identity = paramLongAccumulator.identity;
/* 251 */       this.value = paramLongAccumulator.get();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Object readResolve()
/*     */     {
/* 262 */       LongAccumulator localLongAccumulator = new LongAccumulator(this.function, this.identity);
/* 263 */       localLongAccumulator.base = this.value;
/* 264 */       return localLongAccumulator;
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
/*     */   private Object writeReplace()
/*     */   {
/* 278 */     return new SerializationProxy(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 287 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/LongAccumulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */