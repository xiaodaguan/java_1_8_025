/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import sun.misc.Contended;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Striped64
/*     */   extends Number
/*     */ {
/*     */   @Contended
/*     */   static final class Cell
/*     */   {
/*     */     volatile long value;
/*     */     private static final Unsafe UNSAFE;
/*     */     private static final long valueOffset;
/*     */     
/* 122 */     Cell(long paramLong) { this.value = paramLong; }
/*     */     
/* 124 */     final boolean cas(long paramLong1, long paramLong2) { return UNSAFE.compareAndSwapLong(this, valueOffset, paramLong1, paramLong2); }
/*     */     
/*     */ 
/*     */ 
/*     */     static
/*     */     {
/*     */       try
/*     */       {
/* 132 */         UNSAFE = Unsafe.getUnsafe();
/* 133 */         Class localClass = Cell.class;
/*     */         
/* 135 */         valueOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("value"));
/*     */       } catch (Exception localException) {
/* 137 */         throw new Error(localException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 143 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*     */   
/*     */ 
/*     */ 
/*     */   volatile transient Cell[] cells;
/*     */   
/*     */ 
/*     */   volatile transient long base;
/*     */   
/*     */ 
/*     */   volatile transient int cellsBusy;
/*     */   
/*     */ 
/*     */   private static final Unsafe UNSAFE;
/*     */   
/*     */ 
/*     */   private static final long BASE;
/*     */   
/*     */ 
/*     */   private static final long CELLSBUSY;
/*     */   
/*     */ 
/*     */   private static final long PROBE;
/*     */   
/*     */ 
/*     */ 
/*     */   final boolean casBase(long paramLong1, long paramLong2)
/*     */   {
/* 171 */     return UNSAFE.compareAndSwapLong(this, BASE, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final boolean casCellsBusy()
/*     */   {
/* 178 */     return UNSAFE.compareAndSwapInt(this, CELLSBUSY, 0, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int getProbe()
/*     */   {
/* 186 */     return UNSAFE.getInt(Thread.currentThread(), PROBE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int advanceProbe(int paramInt)
/*     */   {
/* 195 */     paramInt ^= paramInt << 13;
/* 196 */     paramInt ^= paramInt >>> 17;
/* 197 */     paramInt ^= paramInt << 5;
/* 198 */     UNSAFE.putInt(Thread.currentThread(), PROBE, paramInt);
/* 199 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final void longAccumulate(long paramLong, LongBinaryOperator paramLongBinaryOperator, boolean paramBoolean)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 217 */     if ((i = getProbe()) == 0) {
/* 218 */       ThreadLocalRandom.current();
/* 219 */       i = getProbe();
/* 220 */       paramBoolean = true;
/*     */     }
/* 222 */     int j = 0;
/*     */     for (;;) { Cell[] arrayOfCell1;
/*     */       int k;
/* 225 */       long l; if (((arrayOfCell1 = this.cells) != null) && ((k = arrayOfCell1.length) > 0)) { Cell localCell;
/* 226 */         Object localObject1; int n; if ((localCell = arrayOfCell1[(k - 1 & i)]) == null) {
/* 227 */           if (this.cellsBusy == 0) {
/* 228 */             localObject1 = new Cell(paramLong);
/* 229 */             if ((this.cellsBusy == 0) && (casCellsBusy())) {
/* 230 */               n = 0;
/*     */               try { Cell[] arrayOfCell3;
/*     */                 int i1;
/* 233 */                 int i2; if (((arrayOfCell3 = this.cells) != null) && ((i1 = arrayOfCell3.length) > 0) && (arrayOfCell3[(i2 = i1 - 1 & i)] == null))
/*     */                 {
/*     */ 
/* 236 */                   arrayOfCell3[i2] = localObject1;
/* 237 */                   n = 1;
/*     */                 }
/*     */               } finally {
/* 240 */                 this.cellsBusy = 0;
/*     */               }
/* 242 */               if (n == 0) continue;
/* 243 */               break;
/*     */             }
/*     */           }
/*     */           
/* 247 */           j = 0;
/*     */         }
/* 249 */         else if (!paramBoolean) {
/* 250 */           paramBoolean = true;
/* 251 */         } else { if (localCell.cas(l = localCell.value, paramLongBinaryOperator == null ? l + paramLong : paramLongBinaryOperator
/* 252 */             .applyAsLong(l, paramLong)))
/*     */             break;
/* 254 */           if ((k >= NCPU) || (this.cells != arrayOfCell1)) {
/* 255 */             j = 0;
/* 256 */           } else if (j == 0) {
/* 257 */             j = 1;
/* 258 */           } else if ((this.cellsBusy == 0) && (casCellsBusy())) {
/*     */             try {
/* 260 */               if (this.cells == arrayOfCell1) {
/* 261 */                 localObject1 = new Cell[k << 1];
/* 262 */                 for (n = 0; n < k; n++)
/* 263 */                   localObject1[n] = arrayOfCell1[n];
/* 264 */                 this.cells = ((Cell[])localObject1);
/*     */               }
/*     */             } finally {
/* 267 */               this.cellsBusy = 0;
/*     */             }
/* 269 */             j = 0;
/* 270 */             continue;
/*     */           } }
/* 272 */         i = advanceProbe(i);
/*     */       }
/* 274 */       else if ((this.cellsBusy == 0) && (this.cells == arrayOfCell1) && (casCellsBusy())) {
/* 275 */         int m = 0;
/*     */         try {
/* 277 */           if (this.cells == arrayOfCell1) {
/* 278 */             Cell[] arrayOfCell2 = new Cell[2];
/* 279 */             arrayOfCell2[(i & 0x1)] = new Cell(paramLong);
/* 280 */             this.cells = arrayOfCell2;
/* 281 */             m = 1;
/*     */           }
/*     */         } finally {
/* 284 */           this.cellsBusy = 0;
/*     */         }
/* 286 */         if (m != 0)
/*     */           break;
/*     */       } else {
/* 289 */         if (casBase(l = this.base, paramLongBinaryOperator == null ? l + paramLong : paramLongBinaryOperator
/* 290 */           .applyAsLong(l, paramLong))) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final void doubleAccumulate(double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator, boolean paramBoolean)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/* 304 */     if ((i = getProbe()) == 0) {
/* 305 */       ThreadLocalRandom.current();
/* 306 */       i = getProbe();
/* 307 */       paramBoolean = true;
/*     */     }
/* 309 */     int j = 0;
/*     */     for (;;) { Cell[] arrayOfCell1;
/*     */       int k;
/* 312 */       long l; if (((arrayOfCell1 = this.cells) != null) && ((k = arrayOfCell1.length) > 0)) { Cell localCell;
/* 313 */         Object localObject1; int n; if ((localCell = arrayOfCell1[(k - 1 & i)]) == null) {
/* 314 */           if (this.cellsBusy == 0) {
/* 315 */             localObject1 = new Cell(Double.doubleToRawLongBits(paramDouble));
/* 316 */             if ((this.cellsBusy == 0) && (casCellsBusy())) {
/* 317 */               n = 0;
/*     */               try { Cell[] arrayOfCell3;
/*     */                 int i1;
/* 320 */                 int i2; if (((arrayOfCell3 = this.cells) != null) && ((i1 = arrayOfCell3.length) > 0) && (arrayOfCell3[(i2 = i1 - 1 & i)] == null))
/*     */                 {
/*     */ 
/* 323 */                   arrayOfCell3[i2] = localObject1;
/* 324 */                   n = 1;
/*     */                 }
/*     */               } finally {
/* 327 */                 this.cellsBusy = 0;
/*     */               }
/* 329 */               if (n == 0) continue;
/* 330 */               break;
/*     */             }
/*     */           }
/*     */           
/* 334 */           j = 0;
/*     */         }
/* 336 */         else if (!paramBoolean) {
/* 337 */           paramBoolean = true;
/* 338 */         } else { if (localCell.cas(l = localCell.value, paramDoubleBinaryOperator == null ? 
/*     */           
/*     */ 
/* 341 */             Double.doubleToRawLongBits(Double.longBitsToDouble(l) + paramDouble) : 
/*     */             
/* 343 */             Double.doubleToRawLongBits(paramDoubleBinaryOperator
/* 344 */             .applyAsDouble(Double.longBitsToDouble(l), paramDouble))))
/*     */             break;
/* 346 */           if ((k >= NCPU) || (this.cells != arrayOfCell1)) {
/* 347 */             j = 0;
/* 348 */           } else if (j == 0) {
/* 349 */             j = 1;
/* 350 */           } else if ((this.cellsBusy == 0) && (casCellsBusy())) {
/*     */             try {
/* 352 */               if (this.cells == arrayOfCell1) {
/* 353 */                 localObject1 = new Cell[k << 1];
/* 354 */                 for (n = 0; n < k; n++)
/* 355 */                   localObject1[n] = arrayOfCell1[n];
/* 356 */                 this.cells = ((Cell[])localObject1);
/*     */               }
/*     */             } finally {
/* 359 */               this.cellsBusy = 0;
/*     */             }
/* 361 */             j = 0;
/* 362 */             continue;
/*     */           } }
/* 364 */         i = advanceProbe(i);
/*     */       }
/* 366 */       else if ((this.cellsBusy == 0) && (this.cells == arrayOfCell1) && (casCellsBusy())) {
/* 367 */         int m = 0;
/*     */         try {
/* 369 */           if (this.cells == arrayOfCell1) {
/* 370 */             Cell[] arrayOfCell2 = new Cell[2];
/* 371 */             arrayOfCell2[(i & 0x1)] = new Cell(Double.doubleToRawLongBits(paramDouble));
/* 372 */             this.cells = arrayOfCell2;
/* 373 */             m = 1;
/*     */           }
/*     */         } finally {
/* 376 */           this.cellsBusy = 0;
/*     */         }
/* 378 */         if (m != 0)
/*     */           break;
/*     */       } else {
/* 381 */         if (casBase(l = this.base, paramDoubleBinaryOperator == null ? 
/*     */         
/*     */ 
/* 384 */           Double.doubleToRawLongBits(Double.longBitsToDouble(l) + paramDouble) : 
/*     */           
/* 386 */           Double.doubleToRawLongBits(paramDoubleBinaryOperator
/* 387 */           .applyAsDouble(Double.longBitsToDouble(l), paramDouble)))) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 399 */       UNSAFE = Unsafe.getUnsafe();
/* 400 */       Class localClass1 = Striped64.class;
/*     */       
/* 402 */       BASE = UNSAFE.objectFieldOffset(localClass1.getDeclaredField("base"));
/*     */       
/* 404 */       CELLSBUSY = UNSAFE.objectFieldOffset(localClass1.getDeclaredField("cellsBusy"));
/* 405 */       Class localClass2 = Thread.class;
/*     */       
/* 407 */       PROBE = UNSAFE.objectFieldOffset(localClass2.getDeclaredField("threadLocalRandomProbe"));
/*     */     } catch (Exception localException) {
/* 409 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/Striped64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */