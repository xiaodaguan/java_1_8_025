/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleAccumulator
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   private final DoubleBinaryOperator function;
/*     */   private final long identity;
/*     */   
/*     */   public DoubleAccumulator(DoubleBinaryOperator paramDoubleBinaryOperator, double paramDouble)
/*     */   {
/*  92 */     this.function = paramDoubleBinaryOperator;
/*  93 */     this.base = (this.identity = Double.doubleToRawLongBits(paramDouble));
/*     */   }
/*     */   
/*     */ 
/*     */   public void accumulate(double paramDouble)
/*     */   {
/*     */     Striped64.Cell[] arrayOfCell;
/*     */     
/*     */     long l1;
/*     */     long l3;
/* 103 */     if (((arrayOfCell = this.cells) != null) || (
/*     */     
/* 105 */       ((l3 = Double.doubleToRawLongBits(this.function
/* 106 */       .applyAsDouble(Double.longBitsToDouble(l1 = this.base), paramDouble))) != l1) && (!casBase(l1, l3)))) {
/* 107 */       boolean bool = true;
/* 108 */       int i; Striped64.Cell localCell; if ((arrayOfCell != null) && ((i = arrayOfCell.length - 1) >= 0) && 
/* 109 */         ((localCell = arrayOfCell[(getProbe() & i)]) != null))
/*     */       {
/*     */         long l2;
/*     */         
/*     */ 
/* 114 */         if ((bool = ((l3 = Double.doubleToRawLongBits(this.function.applyAsDouble(Double.longBitsToDouble(l2 = localCell.value), paramDouble))) == l2) || (localCell.cas(l2, l3)) ? 1 : 0) != 0) {}
/* 115 */       } else { doubleAccumulate(paramDouble, this.function, bool);
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
/*     */   public double get()
/*     */   {
/* 129 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 130 */     double d = Double.longBitsToDouble(this.base);
/* 131 */     if (arrayOfCell != null) {
/* 132 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 133 */         if ((localCell = arrayOfCell[i]) != null)
/*     */         {
/* 135 */           d = this.function.applyAsDouble(d, Double.longBitsToDouble(localCell.value)); }
/*     */       }
/*     */     }
/* 138 */     return d;
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
/* 150 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 151 */     this.base = this.identity;
/* 152 */     if (arrayOfCell != null) {
/* 153 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 154 */         if ((localCell = arrayOfCell[i]) != null) {
/* 155 */           localCell.value = this.identity;
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
/*     */   public double getThenReset()
/*     */   {
/* 171 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 172 */     double d1 = Double.longBitsToDouble(this.base);
/* 173 */     this.base = this.identity;
/* 174 */     if (arrayOfCell != null) {
/* 175 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 176 */         if ((localCell = arrayOfCell[i]) != null) {
/* 177 */           double d2 = Double.longBitsToDouble(localCell.value);
/* 178 */           localCell.value = this.identity;
/* 179 */           d1 = this.function.applyAsDouble(d1, d2);
/*     */         }
/*     */       }
/*     */     }
/* 183 */     return d1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 191 */     return Double.toString(get());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 200 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 208 */     return get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 216 */     return (int)get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 224 */     return (float)get();
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
/*     */     private final double value;
/*     */     
/*     */ 
/*     */ 
/*     */     private final DoubleBinaryOperator function;
/*     */     
/*     */ 
/*     */ 
/*     */     private final long identity;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     SerializationProxy(DoubleAccumulator paramDoubleAccumulator)
/*     */     {
/* 252 */       this.function = paramDoubleAccumulator.function;
/* 253 */       this.identity = paramDoubleAccumulator.identity;
/* 254 */       this.value = paramDoubleAccumulator.get();
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
/* 265 */       double d = Double.longBitsToDouble(this.identity);
/* 266 */       DoubleAccumulator localDoubleAccumulator = new DoubleAccumulator(this.function, d);
/* 267 */       localDoubleAccumulator.base = Double.doubleToRawLongBits(this.value);
/* 268 */       return localDoubleAccumulator;
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
/* 282 */     return new SerializationProxy(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 291 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/DoubleAccumulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */