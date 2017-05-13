/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleAdder
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   
/*     */   public void add(double paramDouble)
/*     */   {
/*     */     Striped64.Cell[] arrayOfCell;
/*     */     long l1;
/*  90 */     if (((arrayOfCell = this.cells) != null) || 
/*  91 */       (!casBase(l1 = this.base, 
/*     */       
/*  93 */       Double.doubleToRawLongBits(Double.longBitsToDouble(l1) + paramDouble)))) {
/*  94 */       boolean bool = true;
/*  95 */       int i; Striped64.Cell localCell; long l2; if ((arrayOfCell == null) || ((i = arrayOfCell.length - 1) < 0) || 
/*  96 */         ((localCell = arrayOfCell[(getProbe() & i)]) == null) || 
/*  97 */         (!(bool = localCell.cas(l2 = localCell.value, 
/*     */         
/*  99 */         Double.doubleToRawLongBits(Double.longBitsToDouble(l2) + paramDouble))))) {
/* 100 */         doubleAccumulate(paramDouble, null, bool);
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
/*     */ 
/*     */ 
/*     */   public double sum()
/*     */   {
/* 117 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 118 */     double d = Double.longBitsToDouble(this.base);
/* 119 */     if (arrayOfCell != null) {
/* 120 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 121 */         if ((localCell = arrayOfCell[i]) != null)
/* 122 */           d += Double.longBitsToDouble(localCell.value);
/*     */       }
/*     */     }
/* 125 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 136 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 137 */     this.base = 0L;
/* 138 */     if (arrayOfCell != null) {
/* 139 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 140 */         if ((localCell = arrayOfCell[i]) != null) {
/* 141 */           localCell.value = 0L;
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
/*     */   public double sumThenReset()
/*     */   {
/* 157 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 158 */     double d = Double.longBitsToDouble(this.base);
/* 159 */     this.base = 0L;
/* 160 */     if (arrayOfCell != null) {
/* 161 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 162 */         if ((localCell = arrayOfCell[i]) != null) {
/* 163 */           long l = localCell.value;
/* 164 */           localCell.value = 0L;
/* 165 */           d += Double.longBitsToDouble(l);
/*     */         }
/*     */       }
/*     */     }
/* 169 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     return Double.toString(sum());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 186 */     return sum();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 194 */     return sum();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 202 */     return (int)sum();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 210 */     return (float)sum();
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
/*     */     private final double value;
/*     */     
/*     */ 
/*     */ 
/*     */     SerializationProxy(DoubleAdder paramDoubleAdder)
/*     */     {
/* 228 */       this.value = paramDoubleAdder.sum();
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
/* 239 */       DoubleAdder localDoubleAdder = new DoubleAdder();
/* 240 */       localDoubleAdder.base = Double.doubleToRawLongBits(this.value);
/* 241 */       return localDoubleAdder;
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
/* 255 */     return new SerializationProxy(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 264 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/DoubleAdder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */