/*     */ package java.util;
/*     */ 
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.IntBinaryOperator;
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
/*     */ 
/*     */ class ArrayPrefixHelpers
/*     */ {
/*     */   static final int CUMULATE = 1;
/*     */   static final int SUMMED = 2;
/*     */   static final int FINISHED = 4;
/*     */   static final int MIN_PARTITION = 16;
/*     */   
/*     */   static final class CumulateTask<T>
/*     */     extends CountedCompleter<Void>
/*     */   {
/*     */     final T[] array;
/*     */     final BinaryOperator<T> function;
/*     */     CumulateTask<T> left;
/*     */     CumulateTask<T> right;
/*     */     T in;
/*     */     T out;
/*     */     final int lo;
/*     */     final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/*     */     public CumulateTask(CumulateTask<T> paramCumulateTask, BinaryOperator<T> paramBinaryOperator, T[] paramArrayOfT, int paramInt1, int paramInt2)
/*     */     {
/* 111 */       super();
/* 112 */       this.function = paramBinaryOperator;this.array = paramArrayOfT;
/* 113 */       this.lo = (this.origin = paramInt1);this.hi = (this.fence = paramInt2);
/*     */       
/*     */       int i;
/* 116 */       this.threshold = ((i = (paramInt2 - paramInt1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16 ? 16 : i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     CumulateTask(CumulateTask<T> paramCumulateTask, BinaryOperator<T> paramBinaryOperator, T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */     {
/* 124 */       super();
/* 125 */       this.function = paramBinaryOperator;this.array = paramArrayOfT;
/* 126 */       this.origin = paramInt1;this.fence = paramInt2;
/* 127 */       this.threshold = paramInt3;
/* 128 */       this.lo = paramInt4;this.hi = paramInt5;
/*     */     }
/*     */     
/*     */     public final void compute()
/*     */     {
/*     */       BinaryOperator localBinaryOperator;
/*     */       Object[] arrayOfObject;
/* 135 */       if (((localBinaryOperator = this.function) == null) || ((arrayOfObject = this.array) == null))
/* 136 */         throw new NullPointerException();
/* 137 */       int i = this.threshold;int j = this.origin;int k = this.fence;
/* 138 */       Object localObject2 = this;
/* 139 */       int m; Object localObject1; while (((m = ((CumulateTask)localObject2).lo) >= 0) && ((localObject1 = ((CumulateTask)localObject2).hi) <= arrayOfObject.length)) { Object localObject6;
/* 140 */         int i4; if (localObject1 - m > i) {
/* 141 */           CumulateTask localCumulateTask1 = ((CumulateTask)localObject2).left;CumulateTask localCumulateTask2 = ((CumulateTask)localObject2).right;
/* 142 */           Object localObject4; if (localCumulateTask1 == null) {
/* 143 */             int i2 = m + localObject1 >>> 1;
/* 144 */             localObject4 = localCumulateTask2 = ((CumulateTask)localObject2).right = new CumulateTask((CumulateTask)localObject2, localBinaryOperator, arrayOfObject, j, k, i, i2, localObject1);
/*     */             
/* 146 */             localObject2 = localCumulateTask1 = ((CumulateTask)localObject2).left = new CumulateTask((CumulateTask)localObject2, localBinaryOperator, arrayOfObject, j, k, i, m, i2);
/*     */           }
/*     */           else
/*     */           {
/* 150 */             localObject6 = ((CumulateTask)localObject2).in;
/* 151 */             localCumulateTask1.in = localObject6;
/* 152 */             localObject4 = localObject2 = null;
/* 153 */             if (localCumulateTask2 != null) {
/* 154 */               Object localObject7 = localCumulateTask1.out;
/*     */               
/* 156 */               localCumulateTask2.in = (m == j ? localObject7 : localBinaryOperator.apply(localObject6, localObject7));
/*     */               int i5;
/* 158 */               while (((i5 = localCumulateTask2.getPendingCount()) & 0x1) == 0)
/*     */               {
/* 160 */                 if (localCumulateTask2.compareAndSetPendingCount(i5, i5 | 0x1)) {
/* 161 */                   localObject2 = localCumulateTask2;
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 167 */             while (((i4 = localCumulateTask1.getPendingCount()) & 0x1) == 0)
/*     */             {
/* 169 */               if (localCumulateTask1.compareAndSetPendingCount(i4, i4 | 0x1)) {
/* 170 */                 if (localObject2 != null)
/* 171 */                   localObject4 = localObject2;
/* 172 */                 localObject2 = localCumulateTask1;
/*     */               }
/*     */             }
/*     */             
/* 176 */             if (localObject2 == null)
/*     */               break;
/*     */           }
/* 179 */           if (localObject4 != null) {
/* 180 */             ((CumulateTask)localObject4).fork();
/*     */           }
/*     */         }
/*     */         else {
/*     */           int i1;
/* 185 */           while (((i1 = ((CumulateTask)localObject2).getPendingCount()) & 0x4) == 0)
/*     */           {
/* 187 */             int n = m > j ? 2 : (i1 & 0x1) != 0 ? 4 : 6;
/*     */             
/* 189 */             if (((CumulateTask)localObject2).compareAndSetPendingCount(i1, i1 | n))
/*     */             {
/*     */               Object localObject3;
/*     */               
/*     */               Object localObject5;
/* 194 */               if (n != 2)
/*     */               {
/* 196 */                 if (m == j) {
/* 197 */                   localObject3 = arrayOfObject[j];
/* 198 */                   localObject5 = j + 1;
/*     */                 }
/*     */                 else {
/* 201 */                   localObject3 = ((CumulateTask)localObject2).in;
/* 202 */                   localObject5 = m;
/*     */                 }
/* 204 */                 for (localObject6 = localObject5; localObject6 < localObject1; localObject6++) {
/* 205 */                   arrayOfObject[localObject6] = (localObject3 = localBinaryOperator.apply(localObject3, arrayOfObject[localObject6]));
/*     */                 }
/* 207 */               } else if (localObject1 < k) {
/* 208 */                 localObject3 = arrayOfObject[m];
/* 209 */                 for (localObject5 = m + 1; localObject5 < localObject1; localObject5++) {
/* 210 */                   localObject3 = localBinaryOperator.apply(localObject3, arrayOfObject[localObject5]);
/*     */                 }
/*     */               } else {
/* 213 */                 localObject3 = ((CumulateTask)localObject2).in; }
/* 214 */               ((CumulateTask)localObject2).out = localObject3;
/*     */               for (;;) {
/* 216 */                 if ((localObject5 = (CumulateTask)((CumulateTask)localObject2).getCompleter()) == null) {
/* 217 */                   if ((n & 0x4) != 0) {
/* 218 */                     ((CumulateTask)localObject2).quietlyComplete();
/*     */                   }
/*     */                 } else {
/* 221 */                   int i3 = ((CumulateTask)localObject5).getPendingCount();
/* 222 */                   if ((i3 & n & 0x4) != 0) {
/* 223 */                     localObject2 = localObject5;
/* 224 */                   } else if ((i3 & n & 0x2) != 0) { CumulateTask localCumulateTask3;
/*     */                     CumulateTask localCumulateTask4;
/* 226 */                     if (((localCumulateTask3 = ((CumulateTask)localObject5).left) != null) && ((localCumulateTask4 = ((CumulateTask)localObject5).right) != null))
/*     */                     {
/* 228 */                       Object localObject8 = localCumulateTask3.out;
/*     */                       
/* 230 */                       ((CumulateTask)localObject5).out = (localCumulateTask4.hi == k ? localObject8 : localBinaryOperator.apply(localObject8, localCumulateTask4.out));
/*     */                     }
/* 232 */                     int i6 = ((i3 & 0x1) == 0) && (((CumulateTask)localObject5).lo == j) ? 1 : 0;
/*     */                     
/* 234 */                     if (((i4 = i3 | n | i6) == i3) || 
/* 235 */                       (((CumulateTask)localObject5).compareAndSetPendingCount(i3, i4))) {
/* 236 */                       n = 2;
/* 237 */                       localObject2 = localObject5;
/* 238 */                       if (i6 != 0)
/* 239 */                         ((CumulateTask)localObject5).fork();
/*     */                     }
/*     */                   } else {
/* 242 */                     if (((CumulateTask)localObject5).compareAndSetPendingCount(i3, i3 | n)) break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           } } } } }
/*     */   
/*     */   static final class LongCumulateTask extends CountedCompleter<Void> { final long[] array;
/*     */     final LongBinaryOperator function;
/*     */     LongCumulateTask left;
/*     */     LongCumulateTask right;
/*     */     long in;
/*     */     long out;
/*     */     final int lo;
/*     */     final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/* 261 */     public LongCumulateTask(LongCumulateTask paramLongCumulateTask, LongBinaryOperator paramLongBinaryOperator, long[] paramArrayOfLong, int paramInt1, int paramInt2) { super();
/* 262 */       this.function = paramLongBinaryOperator;this.array = paramArrayOfLong;
/* 263 */       this.lo = (this.origin = paramInt1);this.hi = (this.fence = paramInt2);
/*     */       
/*     */       int i;
/* 266 */       this.threshold = ((i = (paramInt2 - paramInt1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16 ? 16 : i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     LongCumulateTask(LongCumulateTask paramLongCumulateTask, LongBinaryOperator paramLongBinaryOperator, long[] paramArrayOfLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */     {
/* 274 */       super();
/* 275 */       this.function = paramLongBinaryOperator;this.array = paramArrayOfLong;
/* 276 */       this.origin = paramInt1;this.fence = paramInt2;
/* 277 */       this.threshold = paramInt3;
/* 278 */       this.lo = paramInt4;this.hi = paramInt5;
/*     */     }
/*     */     
/*     */     public final void compute() {
/*     */       LongBinaryOperator localLongBinaryOperator;
/*     */       long[] arrayOfLong;
/* 284 */       if (((localLongBinaryOperator = this.function) == null) || ((arrayOfLong = this.array) == null))
/* 285 */         throw new NullPointerException();
/* 286 */       int i = this.threshold;int j = this.origin;int k = this.fence;
/* 287 */       Object localObject1 = this;
/* 288 */       int m; int n; while (((m = ((LongCumulateTask)localObject1).lo) >= 0) && ((n = ((LongCumulateTask)localObject1).hi) <= arrayOfLong.length)) { int i6;
/* 289 */         if (n - m > i) {
/* 290 */           LongCumulateTask localLongCumulateTask1 = ((LongCumulateTask)localObject1).left;LongCumulateTask localLongCumulateTask2 = ((LongCumulateTask)localObject1).right;
/* 291 */           Object localObject2; if (localLongCumulateTask1 == null) {
/* 292 */             int i3 = m + n >>> 1;
/* 293 */             localObject2 = localLongCumulateTask2 = ((LongCumulateTask)localObject1).right = new LongCumulateTask((LongCumulateTask)localObject1, localLongBinaryOperator, arrayOfLong, j, k, i, i3, n);
/*     */             
/* 295 */             localObject1 = localLongCumulateTask1 = ((LongCumulateTask)localObject1).left = new LongCumulateTask((LongCumulateTask)localObject1, localLongBinaryOperator, arrayOfLong, j, k, i, m, i3);
/*     */           }
/*     */           else
/*     */           {
/* 299 */             long l2 = ((LongCumulateTask)localObject1).in;
/* 300 */             localLongCumulateTask1.in = l2;
/* 301 */             localObject2 = localObject1 = null;
/* 302 */             if (localLongCumulateTask2 != null) {
/* 303 */               long l3 = localLongCumulateTask1.out;
/*     */               
/* 305 */               localLongCumulateTask2.in = (m == j ? l3 : localLongBinaryOperator.applyAsLong(l2, l3));
/*     */               int i7;
/* 307 */               while (((i7 = localLongCumulateTask2.getPendingCount()) & 0x1) == 0)
/*     */               {
/* 309 */                 if (localLongCumulateTask2.compareAndSetPendingCount(i7, i7 | 0x1)) {
/* 310 */                   localObject1 = localLongCumulateTask2;
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 316 */             while (((i6 = localLongCumulateTask1.getPendingCount()) & 0x1) == 0)
/*     */             {
/* 318 */               if (localLongCumulateTask1.compareAndSetPendingCount(i6, i6 | 0x1)) {
/* 319 */                 if (localObject1 != null)
/* 320 */                   localObject2 = localObject1;
/* 321 */                 localObject1 = localLongCumulateTask1;
/*     */               }
/*     */             }
/*     */             
/* 325 */             if (localObject1 == null)
/*     */               break;
/*     */           }
/* 328 */           if (localObject2 != null) {
/* 329 */             ((LongCumulateTask)localObject2).fork();
/*     */           }
/*     */         }
/*     */         else {
/*     */           int i2;
/* 334 */           while (((i2 = ((LongCumulateTask)localObject1).getPendingCount()) & 0x4) == 0)
/*     */           {
/* 336 */             int i1 = m > j ? 2 : (i2 & 0x1) != 0 ? 4 : 6;
/*     */             
/* 338 */             if (((LongCumulateTask)localObject1).compareAndSetPendingCount(i2, i2 | i1))
/*     */             {
/*     */               long l1;
/*     */               int i4;
/*     */               int i5;
/* 343 */               if (i1 != 2)
/*     */               {
/* 345 */                 if (m == j) {
/* 346 */                   l1 = arrayOfLong[j];
/* 347 */                   i4 = j + 1;
/*     */                 }
/*     */                 else {
/* 350 */                   l1 = ((LongCumulateTask)localObject1).in;
/* 351 */                   i4 = m;
/*     */                 }
/* 353 */                 for (i5 = i4; i5 < n; i5++) {
/* 354 */                   arrayOfLong[i5] = (l1 = localLongBinaryOperator.applyAsLong(l1, arrayOfLong[i5]));
/*     */                 }
/* 356 */               } else if (n < k) {
/* 357 */                 l1 = arrayOfLong[m];
/* 358 */                 for (i4 = m + 1; i4 < n; i4++) {
/* 359 */                   l1 = localLongBinaryOperator.applyAsLong(l1, arrayOfLong[i4]);
/*     */                 }
/*     */               } else {
/* 362 */                 l1 = ((LongCumulateTask)localObject1).in; }
/* 363 */               ((LongCumulateTask)localObject1).out = l1;
/*     */               for (;;) { LongCumulateTask localLongCumulateTask3;
/* 365 */                 if ((localLongCumulateTask3 = (LongCumulateTask)((LongCumulateTask)localObject1).getCompleter()) == null) {
/* 366 */                   if ((i1 & 0x4) != 0) {
/* 367 */                     ((LongCumulateTask)localObject1).quietlyComplete();
/*     */                   }
/*     */                 } else {
/* 370 */                   i5 = localLongCumulateTask3.getPendingCount();
/* 371 */                   if ((i5 & i1 & 0x4) != 0) {
/* 372 */                     localObject1 = localLongCumulateTask3;
/* 373 */                   } else if ((i5 & i1 & 0x2) != 0) { LongCumulateTask localLongCumulateTask4;
/*     */                     LongCumulateTask localLongCumulateTask5;
/* 375 */                     if (((localLongCumulateTask4 = localLongCumulateTask3.left) != null) && ((localLongCumulateTask5 = localLongCumulateTask3.right) != null))
/*     */                     {
/* 377 */                       long l4 = localLongCumulateTask4.out;
/*     */                       
/* 379 */                       localLongCumulateTask3.out = (localLongCumulateTask5.hi == k ? l4 : localLongBinaryOperator.applyAsLong(l4, localLongCumulateTask5.out));
/*     */                     }
/* 381 */                     int i8 = ((i5 & 0x1) == 0) && (localLongCumulateTask3.lo == j) ? 1 : 0;
/*     */                     
/* 383 */                     if (((i6 = i5 | i1 | i8) == i5) || 
/* 384 */                       (localLongCumulateTask3.compareAndSetPendingCount(i5, i6))) {
/* 385 */                       i1 = 2;
/* 386 */                       localObject1 = localLongCumulateTask3;
/* 387 */                       if (i8 != 0)
/* 388 */                         localLongCumulateTask3.fork();
/*     */                     }
/*     */                   } else {
/* 391 */                     if (localLongCumulateTask3.compareAndSetPendingCount(i5, i5 | i1)) break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           } } } } }
/*     */   
/*     */   static final class DoubleCumulateTask extends CountedCompleter<Void> { final double[] array;
/*     */     final DoubleBinaryOperator function;
/*     */     DoubleCumulateTask left;
/*     */     DoubleCumulateTask right;
/*     */     double in;
/*     */     double out;
/*     */     final int lo;
/*     */     final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/* 410 */     public DoubleCumulateTask(DoubleCumulateTask paramDoubleCumulateTask, DoubleBinaryOperator paramDoubleBinaryOperator, double[] paramArrayOfDouble, int paramInt1, int paramInt2) { super();
/* 411 */       this.function = paramDoubleBinaryOperator;this.array = paramArrayOfDouble;
/* 412 */       this.lo = (this.origin = paramInt1);this.hi = (this.fence = paramInt2);
/*     */       
/*     */       int i;
/* 415 */       this.threshold = ((i = (paramInt2 - paramInt1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16 ? 16 : i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     DoubleCumulateTask(DoubleCumulateTask paramDoubleCumulateTask, DoubleBinaryOperator paramDoubleBinaryOperator, double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */     {
/* 423 */       super();
/* 424 */       this.function = paramDoubleBinaryOperator;this.array = paramArrayOfDouble;
/* 425 */       this.origin = paramInt1;this.fence = paramInt2;
/* 426 */       this.threshold = paramInt3;
/* 427 */       this.lo = paramInt4;this.hi = paramInt5;
/*     */     }
/*     */     
/*     */     public final void compute() {
/*     */       DoubleBinaryOperator localDoubleBinaryOperator;
/*     */       double[] arrayOfDouble;
/* 433 */       if (((localDoubleBinaryOperator = this.function) == null) || ((arrayOfDouble = this.array) == null))
/* 434 */         throw new NullPointerException();
/* 435 */       int i = this.threshold;int j = this.origin;int k = this.fence;
/* 436 */       Object localObject1 = this;
/* 437 */       int m; int n; while (((m = ((DoubleCumulateTask)localObject1).lo) >= 0) && ((n = ((DoubleCumulateTask)localObject1).hi) <= arrayOfDouble.length)) { int i6;
/* 438 */         if (n - m > i) {
/* 439 */           DoubleCumulateTask localDoubleCumulateTask1 = ((DoubleCumulateTask)localObject1).left;DoubleCumulateTask localDoubleCumulateTask2 = ((DoubleCumulateTask)localObject1).right;
/* 440 */           Object localObject2; if (localDoubleCumulateTask1 == null) {
/* 441 */             int i3 = m + n >>> 1;
/* 442 */             localObject2 = localDoubleCumulateTask2 = ((DoubleCumulateTask)localObject1).right = new DoubleCumulateTask((DoubleCumulateTask)localObject1, localDoubleBinaryOperator, arrayOfDouble, j, k, i, i3, n);
/*     */             
/* 444 */             localObject1 = localDoubleCumulateTask1 = ((DoubleCumulateTask)localObject1).left = new DoubleCumulateTask((DoubleCumulateTask)localObject1, localDoubleBinaryOperator, arrayOfDouble, j, k, i, m, i3);
/*     */           }
/*     */           else
/*     */           {
/* 448 */             double d2 = ((DoubleCumulateTask)localObject1).in;
/* 449 */             localDoubleCumulateTask1.in = d2;
/* 450 */             localObject2 = localObject1 = null;
/* 451 */             if (localDoubleCumulateTask2 != null) {
/* 452 */               double d3 = localDoubleCumulateTask1.out;
/*     */               
/* 454 */               localDoubleCumulateTask2.in = (m == j ? d3 : localDoubleBinaryOperator.applyAsDouble(d2, d3));
/*     */               int i7;
/* 456 */               while (((i7 = localDoubleCumulateTask2.getPendingCount()) & 0x1) == 0)
/*     */               {
/* 458 */                 if (localDoubleCumulateTask2.compareAndSetPendingCount(i7, i7 | 0x1)) {
/* 459 */                   localObject1 = localDoubleCumulateTask2;
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 465 */             while (((i6 = localDoubleCumulateTask1.getPendingCount()) & 0x1) == 0)
/*     */             {
/* 467 */               if (localDoubleCumulateTask1.compareAndSetPendingCount(i6, i6 | 0x1)) {
/* 468 */                 if (localObject1 != null)
/* 469 */                   localObject2 = localObject1;
/* 470 */                 localObject1 = localDoubleCumulateTask1;
/*     */               }
/*     */             }
/*     */             
/* 474 */             if (localObject1 == null)
/*     */               break;
/*     */           }
/* 477 */           if (localObject2 != null) {
/* 478 */             ((DoubleCumulateTask)localObject2).fork();
/*     */           }
/*     */         }
/*     */         else {
/*     */           int i2;
/* 483 */           while (((i2 = ((DoubleCumulateTask)localObject1).getPendingCount()) & 0x4) == 0)
/*     */           {
/* 485 */             int i1 = m > j ? 2 : (i2 & 0x1) != 0 ? 4 : 6;
/*     */             
/* 487 */             if (((DoubleCumulateTask)localObject1).compareAndSetPendingCount(i2, i2 | i1))
/*     */             {
/*     */               double d1;
/*     */               int i4;
/*     */               int i5;
/* 492 */               if (i1 != 2)
/*     */               {
/* 494 */                 if (m == j) {
/* 495 */                   d1 = arrayOfDouble[j];
/* 496 */                   i4 = j + 1;
/*     */                 }
/*     */                 else {
/* 499 */                   d1 = ((DoubleCumulateTask)localObject1).in;
/* 500 */                   i4 = m;
/*     */                 }
/* 502 */                 for (i5 = i4; i5 < n; i5++) {
/* 503 */                   arrayOfDouble[i5] = (d1 = localDoubleBinaryOperator.applyAsDouble(d1, arrayOfDouble[i5]));
/*     */                 }
/* 505 */               } else if (n < k) {
/* 506 */                 d1 = arrayOfDouble[m];
/* 507 */                 for (i4 = m + 1; i4 < n; i4++) {
/* 508 */                   d1 = localDoubleBinaryOperator.applyAsDouble(d1, arrayOfDouble[i4]);
/*     */                 }
/*     */               } else {
/* 511 */                 d1 = ((DoubleCumulateTask)localObject1).in; }
/* 512 */               ((DoubleCumulateTask)localObject1).out = d1;
/*     */               for (;;) { DoubleCumulateTask localDoubleCumulateTask3;
/* 514 */                 if ((localDoubleCumulateTask3 = (DoubleCumulateTask)((DoubleCumulateTask)localObject1).getCompleter()) == null) {
/* 515 */                   if ((i1 & 0x4) != 0) {
/* 516 */                     ((DoubleCumulateTask)localObject1).quietlyComplete();
/*     */                   }
/*     */                 } else {
/* 519 */                   i5 = localDoubleCumulateTask3.getPendingCount();
/* 520 */                   if ((i5 & i1 & 0x4) != 0) {
/* 521 */                     localObject1 = localDoubleCumulateTask3;
/* 522 */                   } else if ((i5 & i1 & 0x2) != 0) { DoubleCumulateTask localDoubleCumulateTask4;
/*     */                     DoubleCumulateTask localDoubleCumulateTask5;
/* 524 */                     if (((localDoubleCumulateTask4 = localDoubleCumulateTask3.left) != null) && ((localDoubleCumulateTask5 = localDoubleCumulateTask3.right) != null))
/*     */                     {
/* 526 */                       double d4 = localDoubleCumulateTask4.out;
/*     */                       
/* 528 */                       localDoubleCumulateTask3.out = (localDoubleCumulateTask5.hi == k ? d4 : localDoubleBinaryOperator.applyAsDouble(d4, localDoubleCumulateTask5.out));
/*     */                     }
/* 530 */                     int i8 = ((i5 & 0x1) == 0) && (localDoubleCumulateTask3.lo == j) ? 1 : 0;
/*     */                     
/* 532 */                     if (((i6 = i5 | i1 | i8) == i5) || 
/* 533 */                       (localDoubleCumulateTask3.compareAndSetPendingCount(i5, i6))) {
/* 534 */                       i1 = 2;
/* 535 */                       localObject1 = localDoubleCumulateTask3;
/* 536 */                       if (i8 != 0)
/* 537 */                         localDoubleCumulateTask3.fork();
/*     */                     }
/*     */                   } else {
/* 540 */                     if (localDoubleCumulateTask3.compareAndSetPendingCount(i5, i5 | i1)) break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           } } } } }
/*     */   
/*     */   static final class IntCumulateTask extends CountedCompleter<Void> { final int[] array;
/*     */     final IntBinaryOperator function;
/*     */     IntCumulateTask left;
/*     */     IntCumulateTask right;
/*     */     int in;
/*     */     int out;
/*     */     final int lo;
/*     */     final int hi;
/*     */     final int origin;
/*     */     final int fence;
/*     */     final int threshold;
/*     */     
/* 559 */     public IntCumulateTask(IntCumulateTask paramIntCumulateTask, IntBinaryOperator paramIntBinaryOperator, int[] paramArrayOfInt, int paramInt1, int paramInt2) { super();
/* 560 */       this.function = paramIntBinaryOperator;this.array = paramArrayOfInt;
/* 561 */       this.lo = (this.origin = paramInt1);this.hi = (this.fence = paramInt2);
/*     */       
/*     */       int i;
/* 564 */       this.threshold = ((i = (paramInt2 - paramInt1) / (ForkJoinPool.getCommonPoolParallelism() << 3)) <= 16 ? 16 : i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     IntCumulateTask(IntCumulateTask paramIntCumulateTask, IntBinaryOperator paramIntBinaryOperator, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */     {
/* 572 */       super();
/* 573 */       this.function = paramIntBinaryOperator;this.array = paramArrayOfInt;
/* 574 */       this.origin = paramInt1;this.fence = paramInt2;
/* 575 */       this.threshold = paramInt3;
/* 576 */       this.lo = paramInt4;this.hi = paramInt5;
/*     */     }
/*     */     
/*     */     public final void compute() {
/*     */       IntBinaryOperator localIntBinaryOperator;
/*     */       int[] arrayOfInt;
/* 582 */       if (((localIntBinaryOperator = this.function) == null) || ((arrayOfInt = this.array) == null))
/* 583 */         throw new NullPointerException();
/* 584 */       int i = this.threshold;int j = this.origin;int k = this.fence;
/* 585 */       Object localObject1 = this;
/* 586 */       int m; int n; while (((m = ((IntCumulateTask)localObject1).lo) >= 0) && ((n = ((IntCumulateTask)localObject1).hi) <= arrayOfInt.length)) { int i4;
/* 587 */         int i5; if (n - m > i) {
/* 588 */           IntCumulateTask localIntCumulateTask1 = ((IntCumulateTask)localObject1).left;IntCumulateTask localIntCumulateTask2 = ((IntCumulateTask)localObject1).right;
/* 589 */           Object localObject2; if (localIntCumulateTask1 == null) {
/* 590 */             i4 = m + n >>> 1;
/* 591 */             localObject2 = localIntCumulateTask2 = ((IntCumulateTask)localObject1).right = new IntCumulateTask((IntCumulateTask)localObject1, localIntBinaryOperator, arrayOfInt, j, k, i, i4, n);
/*     */             
/* 593 */             localObject1 = localIntCumulateTask1 = ((IntCumulateTask)localObject1).left = new IntCumulateTask((IntCumulateTask)localObject1, localIntBinaryOperator, arrayOfInt, j, k, i, m, i4);
/*     */           }
/*     */           else
/*     */           {
/* 597 */             i4 = ((IntCumulateTask)localObject1).in;
/* 598 */             localIntCumulateTask1.in = i4;
/* 599 */             localObject2 = localObject1 = null;
/* 600 */             if (localIntCumulateTask2 != null) {
/* 601 */               i5 = localIntCumulateTask1.out;
/*     */               
/* 603 */               localIntCumulateTask2.in = (m == j ? i5 : localIntBinaryOperator.applyAsInt(i4, i5));
/*     */               int i6;
/* 605 */               while (((i6 = localIntCumulateTask2.getPendingCount()) & 0x1) == 0)
/*     */               {
/* 607 */                 if (localIntCumulateTask2.compareAndSetPendingCount(i6, i6 | 0x1)) {
/* 608 */                   localObject1 = localIntCumulateTask2;
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 614 */             while (((i5 = localIntCumulateTask1.getPendingCount()) & 0x1) == 0)
/*     */             {
/* 616 */               if (localIntCumulateTask1.compareAndSetPendingCount(i5, i5 | 0x1)) {
/* 617 */                 if (localObject1 != null)
/* 618 */                   localObject2 = localObject1;
/* 619 */                 localObject1 = localIntCumulateTask1;
/*     */               }
/*     */             }
/*     */             
/* 623 */             if (localObject1 == null)
/*     */               break;
/*     */           }
/* 626 */           if (localObject2 != null) {
/* 627 */             ((IntCumulateTask)localObject2).fork();
/*     */           }
/*     */         }
/*     */         else {
/*     */           int i2;
/* 632 */           while (((i2 = ((IntCumulateTask)localObject1).getPendingCount()) & 0x4) == 0)
/*     */           {
/* 634 */             int i1 = m > j ? 2 : (i2 & 0x1) != 0 ? 4 : 6;
/*     */             
/* 636 */             if (((IntCumulateTask)localObject1).compareAndSetPendingCount(i2, i2 | i1))
/*     */             {
/*     */               int i3;
/*     */               
/*     */ 
/* 641 */               if (i1 != 2)
/*     */               {
/* 643 */                 if (m == j) {
/* 644 */                   i2 = arrayOfInt[j];
/* 645 */                   i3 = j + 1;
/*     */                 }
/*     */                 else {
/* 648 */                   i2 = ((IntCumulateTask)localObject1).in;
/* 649 */                   i3 = m;
/*     */                 }
/* 651 */                 for (i4 = i3; i4 < n; i4++) {
/* 652 */                   arrayOfInt[i4] = (i2 = localIntBinaryOperator.applyAsInt(i2, arrayOfInt[i4]));
/*     */                 }
/* 654 */               } else if (n < k) {
/* 655 */                 i2 = arrayOfInt[m];
/* 656 */                 for (i3 = m + 1; i3 < n; i3++) {
/* 657 */                   i2 = localIntBinaryOperator.applyAsInt(i2, arrayOfInt[i3]);
/*     */                 }
/*     */               } else {
/* 660 */                 i2 = ((IntCumulateTask)localObject1).in; }
/* 661 */               ((IntCumulateTask)localObject1).out = i2;
/*     */               for (;;) { IntCumulateTask localIntCumulateTask3;
/* 663 */                 if ((localIntCumulateTask3 = (IntCumulateTask)((IntCumulateTask)localObject1).getCompleter()) == null) {
/* 664 */                   if ((i1 & 0x4) != 0) {
/* 665 */                     ((IntCumulateTask)localObject1).quietlyComplete();
/*     */                   }
/*     */                 } else {
/* 668 */                   i4 = localIntCumulateTask3.getPendingCount();
/* 669 */                   if ((i4 & i1 & 0x4) != 0) {
/* 670 */                     localObject1 = localIntCumulateTask3;
/* 671 */                   } else if ((i4 & i1 & 0x2) != 0) { IntCumulateTask localIntCumulateTask4;
/*     */                     IntCumulateTask localIntCumulateTask5;
/* 673 */                     if (((localIntCumulateTask4 = localIntCumulateTask3.left) != null) && ((localIntCumulateTask5 = localIntCumulateTask3.right) != null))
/*     */                     {
/* 675 */                       i7 = localIntCumulateTask4.out;
/*     */                       
/* 677 */                       localIntCumulateTask3.out = (localIntCumulateTask5.hi == k ? i7 : localIntBinaryOperator.applyAsInt(i7, localIntCumulateTask5.out));
/*     */                     }
/* 679 */                     int i7 = ((i4 & 0x1) == 0) && (localIntCumulateTask3.lo == j) ? 1 : 0;
/*     */                     
/* 681 */                     if (((i5 = i4 | i1 | i7) == i4) || 
/* 682 */                       (localIntCumulateTask3.compareAndSetPendingCount(i4, i5))) {
/* 683 */                       i1 = 2;
/* 684 */                       localObject1 = localIntCumulateTask3;
/* 685 */                       if (i7 != 0)
/* 686 */                         localIntCumulateTask3.fork();
/*     */                     }
/*     */                   } else {
/* 689 */                     if (localIntCumulateTask3.compareAndSetPendingCount(i4, i4 | i1)) {
/*     */                       break;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ArrayPrefixHelpers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */