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
/*     */ public class LongAdder
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   
/*     */   public void add(long paramLong)
/*     */   {
/*     */     Striped64.Cell[] arrayOfCell;
/*     */     long l1;
/*  86 */     if (((arrayOfCell = this.cells) != null) || (!casBase(l1 = this.base, l1 + paramLong))) {
/*  87 */       boolean bool = true;
/*  88 */       int i; Striped64.Cell localCell; long l2; if ((arrayOfCell == null) || ((i = arrayOfCell.length - 1) < 0) || 
/*  89 */         ((localCell = arrayOfCell[(getProbe() & i)]) == null) || 
/*  90 */         (!(bool = localCell.cas(l2 = localCell.value, l2 + paramLong)))) {
/*  91 */         longAccumulate(paramLong, null, bool);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void increment()
/*     */   {
/*  99 */     add(1L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void decrement()
/*     */   {
/* 106 */     add(-1L);
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
/*     */   public long sum()
/*     */   {
/* 119 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 120 */     long l = this.base;
/* 121 */     if (arrayOfCell != null) {
/* 122 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 123 */         if ((localCell = arrayOfCell[i]) != null)
/* 124 */           l += localCell.value;
/*     */       }
/*     */     }
/* 127 */     return l;
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
/* 138 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 139 */     this.base = 0L;
/* 140 */     if (arrayOfCell != null) {
/* 141 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 142 */         if ((localCell = arrayOfCell[i]) != null) {
/* 143 */           localCell.value = 0L;
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
/*     */   public long sumThenReset()
/*     */   {
/* 159 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 160 */     long l = this.base;
/* 161 */     this.base = 0L;
/* 162 */     if (arrayOfCell != null) {
/* 163 */       for (int i = 0; i < arrayOfCell.length; i++) { Striped64.Cell localCell;
/* 164 */         if ((localCell = arrayOfCell[i]) != null) {
/* 165 */           l += localCell.value;
/* 166 */           localCell.value = 0L;
/*     */         }
/*     */       }
/*     */     }
/* 170 */     return l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 178 */     return Long.toString(sum());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 187 */     return sum();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 195 */     return (int)sum();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 203 */     return (float)sum();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 211 */     return sum();
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
/*     */     private final long value;
/*     */     
/*     */ 
/*     */ 
/*     */     SerializationProxy(LongAdder paramLongAdder)
/*     */     {
/* 229 */       this.value = paramLongAdder.sum();
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
/* 240 */       LongAdder localLongAdder = new LongAdder();
/* 241 */       localLongAdder.base = this.value;
/* 242 */       return localLongAdder;
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
/* 256 */     return new SerializationProxy(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 265 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/LongAdder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */