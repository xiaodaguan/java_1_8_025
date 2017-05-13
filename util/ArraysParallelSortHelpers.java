/*      */ package java.util;
/*      */ 
/*      */ import java.util.concurrent.CountedCompleter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ArraysParallelSortHelpers
/*      */ {
/*      */   static final class EmptyCompleter
/*      */     extends CountedCompleter<Void>
/*      */   {
/*      */     static final long serialVersionUID = 2446542900576103244L;
/*      */     
/*      */     EmptyCompleter(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*   91 */       super();
/*      */     }
/*      */     
/*      */     public final void compute() {}
/*      */   }
/*      */   
/*      */   static final class Relay extends CountedCompleter<Void> {
/*      */     static final long serialVersionUID = 2446542900576103244L;
/*      */     final CountedCompleter<?> task;
/*      */     
/*      */     Relay(CountedCompleter<?> paramCountedCompleter) {
/*  102 */       super(1);
/*  103 */       this.task = paramCountedCompleter; }
/*      */     
/*      */     public final void compute() {}
/*      */     
/*  107 */     public final void onCompletion(CountedCompleter<?> paramCountedCompleter) { this.task.compute(); }
/*      */   }
/*      */   
/*      */   static final class FJObject {
/*      */     static final class Sorter<T> extends CountedCompleter<Void> {
/*      */       static final long serialVersionUID = 2446542900576103244L;
/*      */       final T[] a;
/*      */       final T[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       Comparator<? super T> comparator;
/*      */       
/*  121 */       Sorter(CountedCompleter<?> paramCountedCompleter, T[] paramArrayOfT1, T[] paramArrayOfT2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Comparator<? super T> paramComparator) { super();
/*  122 */         this.a = paramArrayOfT1;this.w = paramArrayOfT2;this.base = paramInt1;this.size = paramInt2;
/*  123 */         this.wbase = paramInt3;this.gran = paramInt4;
/*  124 */         this.comparator = paramComparator;
/*      */       }
/*      */       
/*  127 */       public final void compute() { Object localObject = this;
/*  128 */         Comparator localComparator = this.comparator;
/*  129 */         Object[] arrayOfObject1 = this.a;Object[] arrayOfObject2 = this.w;
/*  130 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  131 */         while (j > m) {
/*  132 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  133 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJObject.Merger((CountedCompleter)localObject, arrayOfObject2, arrayOfObject1, k, n, k + n, j - n, i, m, localComparator));
/*      */           
/*  135 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJObject.Merger(localRelay1, arrayOfObject1, arrayOfObject2, i + n, i1, i + i2, j - i2, k + n, m, localComparator));
/*      */           
/*  137 */           new Sorter(localRelay2, arrayOfObject1, arrayOfObject2, i + i2, j - i2, k + i2, m, localComparator).fork();
/*  138 */           new Sorter(localRelay2, arrayOfObject1, arrayOfObject2, i + n, i1, k + n, m, localComparator).fork();
/*  139 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJObject.Merger(localRelay1, arrayOfObject1, arrayOfObject2, i, i1, i + i1, n - i1, k, m, localComparator));
/*      */           
/*  141 */           new Sorter(localRelay3, arrayOfObject1, arrayOfObject2, i + i1, n - i1, k + i1, m, localComparator).fork();
/*  142 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  143 */           j = i1;
/*      */         }
/*  145 */         TimSort.sort(arrayOfObject1, i, i + j, localComparator, arrayOfObject2, k, j);
/*  146 */         ((CountedCompleter)localObject).tryComplete(); } }
/*      */     
/*      */     static final class Merger<T> extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final T[] a;
/*      */       final T[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       Comparator<? super T> comparator;
/*      */       
/*  159 */       Merger(CountedCompleter<?> paramCountedCompleter, T[] paramArrayOfT1, T[] paramArrayOfT2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Comparator<? super T> paramComparator) { super();
/*  160 */         this.a = paramArrayOfT1;this.w = paramArrayOfT2;
/*  161 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  162 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  163 */         this.wbase = paramInt5;this.gran = paramInt6;
/*  164 */         this.comparator = paramComparator;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  168 */         Comparator localComparator = this.comparator;
/*  169 */         Object[] arrayOfObject1 = this.a;Object[] arrayOfObject2 = this.w;
/*  170 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  171 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  172 */         if ((arrayOfObject1 == null) || (arrayOfObject2 == null) || (i < 0) || (k < 0) || (n < 0) || (localComparator == null))
/*      */         {
/*  174 */           throw new IllegalStateException(); }
/*      */         Object localObject1;
/*  176 */         for (;;) { int i4; int i5; if (j >= m) {
/*  177 */             if (j <= i1)
/*      */               break;
/*  179 */             i3 = m;
/*  180 */             localObject1 = arrayOfObject1[((i2 = j >>> 1) + i)];
/*  181 */             for (i4 = 0; i4 < i3;) {
/*  182 */               i5 = i4 + i3 >>> 1;
/*  183 */               if (localComparator.compare(localObject1, arrayOfObject1[(i5 + k)]) <= 0) {
/*  184 */                 i3 = i5;
/*      */               } else {
/*  186 */                 i4 = i5 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  190 */             if (m <= i1)
/*      */               break;
/*  192 */             i2 = j;
/*  193 */             localObject1 = arrayOfObject1[((i3 = m >>> 1) + k)];
/*  194 */             for (i4 = 0; i4 < i2;) {
/*  195 */               i5 = i4 + i2 >>> 1;
/*  196 */               if (localComparator.compare(localObject1, arrayOfObject1[(i5 + i)]) <= 0) {
/*  197 */                 i2 = i5;
/*      */               } else
/*  199 */                 i4 = i5 + 1;
/*      */             }
/*      */           }
/*  202 */           localObject1 = new Merger(this, arrayOfObject1, arrayOfObject2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1, localComparator);
/*      */           
/*      */ 
/*  205 */           m = i3;
/*  206 */           j = i2;
/*  207 */           addToPendingCount(1);
/*  208 */           ((Merger)localObject1).fork();
/*      */         }
/*      */         
/*  211 */         int i2 = i + j;int i3 = k + m;
/*  212 */         while ((i < i2) && (k < i3)) { Object localObject2;
/*      */           Object localObject3;
/*  214 */           if (localComparator.compare(localObject2 = arrayOfObject1[i], localObject3 = arrayOfObject1[k]) <= 0) {
/*  215 */             i++;localObject1 = localObject2;
/*      */           }
/*      */           else {
/*  218 */             k++;localObject1 = localObject3;
/*      */           }
/*  220 */           arrayOfObject2[(n++)] = localObject1;
/*      */         }
/*  222 */         if (k < i3) {
/*  223 */           System.arraycopy(arrayOfObject1, k, arrayOfObject2, n, i3 - k);
/*  224 */         } else if (i < i2) {
/*  225 */           System.arraycopy(arrayOfObject1, i, arrayOfObject2, n, i2 - i);
/*      */         }
/*  227 */         tryComplete();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FJByte {
/*      */     static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final byte[] a;
/*      */       final byte[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*  241 */       Sorter(CountedCompleter<?> paramCountedCompleter, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  242 */         this.a = paramArrayOfByte1;this.w = paramArrayOfByte2;this.base = paramInt1;this.size = paramInt2;
/*  243 */         this.wbase = paramInt3;this.gran = paramInt4;
/*      */       }
/*      */       
/*  246 */       public final void compute() { Object localObject = this;
/*  247 */         byte[] arrayOfByte1 = this.a;byte[] arrayOfByte2 = this.w;
/*  248 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  249 */         while (j > m) {
/*  250 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  251 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJByte.Merger((CountedCompleter)localObject, arrayOfByte2, arrayOfByte1, k, n, k + n, j - n, i, m));
/*      */           
/*  253 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJByte.Merger(localRelay1, arrayOfByte1, arrayOfByte2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  255 */           new Sorter(localRelay2, arrayOfByte1, arrayOfByte2, i + i2, j - i2, k + i2, m).fork();
/*  256 */           new Sorter(localRelay2, arrayOfByte1, arrayOfByte2, i + n, i1, k + n, m).fork();
/*  257 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJByte.Merger(localRelay1, arrayOfByte1, arrayOfByte2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  259 */           new Sorter(localRelay3, arrayOfByte1, arrayOfByte2, i + i1, n - i1, k + i1, m).fork();
/*  260 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  261 */           j = i1;
/*      */         }
/*  263 */         DualPivotQuicksort.sort(arrayOfByte1, i, i + j - 1);
/*  264 */         ((CountedCompleter)localObject).tryComplete();
/*      */       }
/*      */     }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> {
/*      */       static final long serialVersionUID = 2446542900576103244L;
/*      */       final byte[] a;
/*      */       final byte[] w;
/*      */       final int lbase;
/*      */       
/*      */       Merger(CountedCompleter<?> paramCountedCompleter, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  275 */         super();
/*  276 */         this.a = paramArrayOfByte1;this.w = paramArrayOfByte2;
/*  277 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  278 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  279 */         this.wbase = paramInt5;this.gran = paramInt6; }
/*      */       
/*      */       final int lsize;
/*      */       
/*  283 */       public final void compute() { byte[] arrayOfByte1 = this.a;byte[] arrayOfByte2 = this.w;
/*  284 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  285 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  286 */         if ((arrayOfByte1 == null) || (arrayOfByte2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  287 */           throw new IllegalStateException();
/*      */         Merger localMerger2;
/*  289 */         Merger localMerger3; Merger localMerger1; for (;;) { int i4; if (j >= m) {
/*  290 */             if (j <= i1)
/*      */               break;
/*  292 */             i3 = m;
/*  293 */             i4 = arrayOfByte1[((i2 = j >>> 1) + i)];
/*  294 */             for (localMerger2 = 0; localMerger2 < i3;) {
/*  295 */               localMerger3 = localMerger2 + i3 >>> 1;
/*  296 */               if (i4 <= arrayOfByte1[(localMerger3 + k)]) {
/*  297 */                 i3 = localMerger3;
/*      */               } else {
/*  299 */                 localMerger2 = localMerger3 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  303 */             if (m <= i1)
/*      */               break;
/*  305 */             i2 = j;
/*  306 */             i4 = arrayOfByte1[((i3 = m >>> 1) + k)];
/*  307 */             for (localMerger2 = 0; localMerger2 < i2;) {
/*  308 */               localMerger3 = localMerger2 + i2 >>> 1;
/*  309 */               if (i4 <= arrayOfByte1[(localMerger3 + i)]) {
/*  310 */                 i2 = localMerger3;
/*      */               } else
/*  312 */                 localMerger2 = localMerger3 + 1;
/*      */             }
/*      */           }
/*  315 */           localMerger1 = new Merger(this, arrayOfByte1, arrayOfByte2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  318 */           m = i3;
/*  319 */           j = i2;
/*  320 */           addToPendingCount(1);
/*  321 */           localMerger1.fork();
/*      */         }
/*      */         
/*  324 */         int i2 = i + j;int i3 = k + m;
/*  325 */         while ((i < i2) && (k < i3))
/*      */         {
/*  327 */           if ((localMerger2 = arrayOfByte1[i]) <= (localMerger3 = arrayOfByte1[k])) {
/*  328 */             i++;localMerger1 = localMerger2;
/*      */           }
/*      */           else {
/*  331 */             k++;localMerger1 = localMerger3;
/*      */           }
/*  333 */           arrayOfByte2[(n++)] = localMerger1;
/*      */         }
/*  335 */         if (k < i3) {
/*  336 */           System.arraycopy(arrayOfByte1, k, arrayOfByte2, n, i3 - k);
/*  337 */         } else if (i < i2)
/*  338 */           System.arraycopy(arrayOfByte1, i, arrayOfByte2, n, i2 - i);
/*  339 */         tryComplete(); }
/*      */       
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran; } }
/*      */   
/*      */   static final class FJChar { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final char[] a;
/*      */       final char[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       
/*  352 */       Sorter(CountedCompleter<?> paramCountedCompleter, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  353 */         this.a = paramArrayOfChar1;this.w = paramArrayOfChar2;this.base = paramInt1;this.size = paramInt2;
/*  354 */         this.wbase = paramInt3;this.gran = paramInt4;
/*      */       }
/*      */       
/*  357 */       public final void compute() { Object localObject = this;
/*  358 */         char[] arrayOfChar1 = this.a;char[] arrayOfChar2 = this.w;
/*  359 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  360 */         while (j > m) {
/*  361 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  362 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJChar.Merger((CountedCompleter)localObject, arrayOfChar2, arrayOfChar1, k, n, k + n, j - n, i, m));
/*      */           
/*  364 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJChar.Merger(localRelay1, arrayOfChar1, arrayOfChar2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  366 */           new Sorter(localRelay2, arrayOfChar1, arrayOfChar2, i + i2, j - i2, k + i2, m).fork();
/*  367 */           new Sorter(localRelay2, arrayOfChar1, arrayOfChar2, i + n, i1, k + n, m).fork();
/*  368 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJChar.Merger(localRelay1, arrayOfChar1, arrayOfChar2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  370 */           new Sorter(localRelay3, arrayOfChar1, arrayOfChar2, i + i1, n - i1, k + i1, m).fork();
/*  371 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  372 */           j = i1;
/*      */         }
/*  374 */         DualPivotQuicksort.sort(arrayOfChar1, i, i + j - 1, arrayOfChar2, k, j);
/*  375 */         ((CountedCompleter)localObject).tryComplete(); }
/*      */       
/*      */       final int wbase;
/*      */       final int gran; }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final char[] a;
/*      */       final char[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       
/*  386 */       Merger(CountedCompleter<?> paramCountedCompleter, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { super();
/*  387 */         this.a = paramArrayOfChar1;this.w = paramArrayOfChar2;
/*  388 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  389 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  390 */         this.wbase = paramInt5;this.gran = paramInt6; }
/*      */       
/*      */       final int rbase;
/*      */       
/*  394 */       public final void compute() { char[] arrayOfChar1 = this.a;char[] arrayOfChar2 = this.w;
/*  395 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  396 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  397 */         if ((arrayOfChar1 == null) || (arrayOfChar2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  398 */           throw new IllegalStateException();
/*      */         Merger localMerger2;
/*  400 */         Merger localMerger3; Merger localMerger1; for (;;) { int i4; if (j >= m) {
/*  401 */             if (j <= i1)
/*      */               break;
/*  403 */             i3 = m;
/*  404 */             i4 = arrayOfChar1[((i2 = j >>> 1) + i)];
/*  405 */             for (localMerger2 = 0; localMerger2 < i3;) {
/*  406 */               localMerger3 = localMerger2 + i3 >>> 1;
/*  407 */               if (i4 <= arrayOfChar1[(localMerger3 + k)]) {
/*  408 */                 i3 = localMerger3;
/*      */               } else {
/*  410 */                 localMerger2 = localMerger3 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  414 */             if (m <= i1)
/*      */               break;
/*  416 */             i2 = j;
/*  417 */             i4 = arrayOfChar1[((i3 = m >>> 1) + k)];
/*  418 */             for (localMerger2 = 0; localMerger2 < i2;) {
/*  419 */               localMerger3 = localMerger2 + i2 >>> 1;
/*  420 */               if (i4 <= arrayOfChar1[(localMerger3 + i)]) {
/*  421 */                 i2 = localMerger3;
/*      */               } else
/*  423 */                 localMerger2 = localMerger3 + 1;
/*      */             }
/*      */           }
/*  426 */           localMerger1 = new Merger(this, arrayOfChar1, arrayOfChar2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  429 */           m = i3;
/*  430 */           j = i2;
/*  431 */           addToPendingCount(1);
/*  432 */           localMerger1.fork();
/*      */         }
/*      */         
/*  435 */         int i2 = i + j;int i3 = k + m;
/*  436 */         while ((i < i2) && (k < i3))
/*      */         {
/*  438 */           if ((localMerger2 = arrayOfChar1[i]) <= (localMerger3 = arrayOfChar1[k])) {
/*  439 */             i++;localMerger1 = localMerger2;
/*      */           }
/*      */           else {
/*  442 */             k++;localMerger1 = localMerger3;
/*      */           }
/*  444 */           arrayOfChar2[(n++)] = localMerger1;
/*      */         }
/*  446 */         if (k < i3) {
/*  447 */           System.arraycopy(arrayOfChar1, k, arrayOfChar2, n, i3 - k);
/*  448 */         } else if (i < i2)
/*  449 */           System.arraycopy(arrayOfChar1, i, arrayOfChar2, n, i2 - i);
/*  450 */         tryComplete();
/*      */       }
/*      */       
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran; } }
/*      */   
/*      */   static final class FJShort { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final short[] a;
/*      */       final short[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       
/*  463 */       Sorter(CountedCompleter<?> paramCountedCompleter, short[] paramArrayOfShort1, short[] paramArrayOfShort2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  464 */         this.a = paramArrayOfShort1;this.w = paramArrayOfShort2;this.base = paramInt1;this.size = paramInt2;
/*  465 */         this.wbase = paramInt3;this.gran = paramInt4;
/*      */       }
/*      */       
/*  468 */       public final void compute() { Object localObject = this;
/*  469 */         short[] arrayOfShort1 = this.a;short[] arrayOfShort2 = this.w;
/*  470 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  471 */         while (j > m) {
/*  472 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  473 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJShort.Merger((CountedCompleter)localObject, arrayOfShort2, arrayOfShort1, k, n, k + n, j - n, i, m));
/*      */           
/*  475 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJShort.Merger(localRelay1, arrayOfShort1, arrayOfShort2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  477 */           new Sorter(localRelay2, arrayOfShort1, arrayOfShort2, i + i2, j - i2, k + i2, m).fork();
/*  478 */           new Sorter(localRelay2, arrayOfShort1, arrayOfShort2, i + n, i1, k + n, m).fork();
/*  479 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJShort.Merger(localRelay1, arrayOfShort1, arrayOfShort2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  481 */           new Sorter(localRelay3, arrayOfShort1, arrayOfShort2, i + i1, n - i1, k + i1, m).fork();
/*  482 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  483 */           j = i1;
/*      */         }
/*  485 */         DualPivotQuicksort.sort(arrayOfShort1, i, i + j - 1, arrayOfShort2, k, j);
/*  486 */         ((CountedCompleter)localObject).tryComplete(); }
/*      */       
/*      */       final int wbase;
/*      */       final int gran; }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final short[] a;
/*      */       final short[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       
/*  497 */       Merger(CountedCompleter<?> paramCountedCompleter, short[] paramArrayOfShort1, short[] paramArrayOfShort2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { super();
/*  498 */         this.a = paramArrayOfShort1;this.w = paramArrayOfShort2;
/*  499 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  500 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  501 */         this.wbase = paramInt5;this.gran = paramInt6; }
/*      */       
/*      */       final int rbase;
/*      */       
/*  505 */       public final void compute() { short[] arrayOfShort1 = this.a;short[] arrayOfShort2 = this.w;
/*  506 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  507 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  508 */         if ((arrayOfShort1 == null) || (arrayOfShort2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  509 */           throw new IllegalStateException();
/*      */         Merger localMerger2;
/*  511 */         Merger localMerger3; Merger localMerger1; for (;;) { int i4; if (j >= m) {
/*  512 */             if (j <= i1)
/*      */               break;
/*  514 */             i3 = m;
/*  515 */             i4 = arrayOfShort1[((i2 = j >>> 1) + i)];
/*  516 */             for (localMerger2 = 0; localMerger2 < i3;) {
/*  517 */               localMerger3 = localMerger2 + i3 >>> 1;
/*  518 */               if (i4 <= arrayOfShort1[(localMerger3 + k)]) {
/*  519 */                 i3 = localMerger3;
/*      */               } else {
/*  521 */                 localMerger2 = localMerger3 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  525 */             if (m <= i1)
/*      */               break;
/*  527 */             i2 = j;
/*  528 */             i4 = arrayOfShort1[((i3 = m >>> 1) + k)];
/*  529 */             for (localMerger2 = 0; localMerger2 < i2;) {
/*  530 */               localMerger3 = localMerger2 + i2 >>> 1;
/*  531 */               if (i4 <= arrayOfShort1[(localMerger3 + i)]) {
/*  532 */                 i2 = localMerger3;
/*      */               } else
/*  534 */                 localMerger2 = localMerger3 + 1;
/*      */             }
/*      */           }
/*  537 */           localMerger1 = new Merger(this, arrayOfShort1, arrayOfShort2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  540 */           m = i3;
/*  541 */           j = i2;
/*  542 */           addToPendingCount(1);
/*  543 */           localMerger1.fork();
/*      */         }
/*      */         
/*  546 */         int i2 = i + j;int i3 = k + m;
/*  547 */         while ((i < i2) && (k < i3))
/*      */         {
/*  549 */           if ((localMerger2 = arrayOfShort1[i]) <= (localMerger3 = arrayOfShort1[k])) {
/*  550 */             i++;localMerger1 = localMerger2;
/*      */           }
/*      */           else {
/*  553 */             k++;localMerger1 = localMerger3;
/*      */           }
/*  555 */           arrayOfShort2[(n++)] = localMerger1;
/*      */         }
/*  557 */         if (k < i3) {
/*  558 */           System.arraycopy(arrayOfShort1, k, arrayOfShort2, n, i3 - k);
/*  559 */         } else if (i < i2)
/*  560 */           System.arraycopy(arrayOfShort1, i, arrayOfShort2, n, i2 - i);
/*  561 */         tryComplete(); }
/*      */       
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran; } }
/*      */   
/*      */   static final class FJInt { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final int[] a;
/*      */       final int[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       
/*  574 */       Sorter(CountedCompleter<?> paramCountedCompleter, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  575 */         this.a = paramArrayOfInt1;this.w = paramArrayOfInt2;this.base = paramInt1;this.size = paramInt2;
/*  576 */         this.wbase = paramInt3;this.gran = paramInt4; }
/*      */       final int gran;
/*      */       
/*  579 */       public final void compute() { Object localObject = this;
/*  580 */         int[] arrayOfInt1 = this.a;int[] arrayOfInt2 = this.w;
/*  581 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  582 */         while (j > m) {
/*  583 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  584 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJInt.Merger((CountedCompleter)localObject, arrayOfInt2, arrayOfInt1, k, n, k + n, j - n, i, m));
/*      */           
/*  586 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJInt.Merger(localRelay1, arrayOfInt1, arrayOfInt2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  588 */           new Sorter(localRelay2, arrayOfInt1, arrayOfInt2, i + i2, j - i2, k + i2, m).fork();
/*  589 */           new Sorter(localRelay2, arrayOfInt1, arrayOfInt2, i + n, i1, k + n, m).fork();
/*  590 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJInt.Merger(localRelay1, arrayOfInt1, arrayOfInt2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  592 */           new Sorter(localRelay3, arrayOfInt1, arrayOfInt2, i + i1, n - i1, k + i1, m).fork();
/*  593 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  594 */           j = i1;
/*      */         }
/*  596 */         DualPivotQuicksort.sort(arrayOfInt1, i, i + j - 1, arrayOfInt2, k, j);
/*  597 */         ((CountedCompleter)localObject).tryComplete(); }
/*      */       
/*      */        }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final int[] a;
/*      */       final int[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*  608 */       Merger(CountedCompleter<?> paramCountedCompleter, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { super();
/*  609 */         this.a = paramArrayOfInt1;this.w = paramArrayOfInt2;
/*  610 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  611 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  612 */         this.wbase = paramInt5;this.gran = paramInt6; }
/*      */       
/*      */       final int wbase;
/*      */       final int gran;
/*  616 */       public final void compute() { int[] arrayOfInt1 = this.a;int[] arrayOfInt2 = this.w;
/*  617 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  618 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  619 */         if ((arrayOfInt1 == null) || (arrayOfInt2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  620 */           throw new IllegalStateException();
/*      */         Merger localMerger2;
/*  622 */         Merger localMerger3; Merger localMerger1; for (;;) { int i4; if (j >= m) {
/*  623 */             if (j <= i1)
/*      */               break;
/*  625 */             i3 = m;
/*  626 */             i4 = arrayOfInt1[((i2 = j >>> 1) + i)];
/*  627 */             for (localMerger2 = 0; localMerger2 < i3;) {
/*  628 */               localMerger3 = localMerger2 + i3 >>> 1;
/*  629 */               if (i4 <= arrayOfInt1[(localMerger3 + k)]) {
/*  630 */                 i3 = localMerger3;
/*      */               } else {
/*  632 */                 localMerger2 = localMerger3 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  636 */             if (m <= i1)
/*      */               break;
/*  638 */             i2 = j;
/*  639 */             i4 = arrayOfInt1[((i3 = m >>> 1) + k)];
/*  640 */             for (localMerger2 = 0; localMerger2 < i2;) {
/*  641 */               localMerger3 = localMerger2 + i2 >>> 1;
/*  642 */               if (i4 <= arrayOfInt1[(localMerger3 + i)]) {
/*  643 */                 i2 = localMerger3;
/*      */               } else
/*  645 */                 localMerger2 = localMerger3 + 1;
/*      */             }
/*      */           }
/*  648 */           localMerger1 = new Merger(this, arrayOfInt1, arrayOfInt2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  651 */           m = i3;
/*  652 */           j = i2;
/*  653 */           addToPendingCount(1);
/*  654 */           localMerger1.fork();
/*      */         }
/*      */         
/*  657 */         int i2 = i + j;int i3 = k + m;
/*  658 */         while ((i < i2) && (k < i3))
/*      */         {
/*  660 */           if ((localMerger2 = arrayOfInt1[i]) <= (localMerger3 = arrayOfInt1[k])) {
/*  661 */             i++;localMerger1 = localMerger2;
/*      */           }
/*      */           else {
/*  664 */             k++;localMerger1 = localMerger3;
/*      */           }
/*  666 */           arrayOfInt2[(n++)] = localMerger1;
/*      */         }
/*  668 */         if (k < i3) {
/*  669 */           System.arraycopy(arrayOfInt1, k, arrayOfInt2, n, i3 - k);
/*  670 */         } else if (i < i2)
/*  671 */           System.arraycopy(arrayOfInt1, i, arrayOfInt2, n, i2 - i);
/*  672 */         tryComplete();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FJLong { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final long[] a;
/*      */       final long[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*  685 */       Sorter(CountedCompleter<?> paramCountedCompleter, long[] paramArrayOfLong1, long[] paramArrayOfLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  686 */         this.a = paramArrayOfLong1;this.w = paramArrayOfLong2;this.base = paramInt1;this.size = paramInt2;
/*  687 */         this.wbase = paramInt3;this.gran = paramInt4;
/*      */       }
/*      */       
/*  690 */       public final void compute() { Object localObject = this;
/*  691 */         long[] arrayOfLong1 = this.a;long[] arrayOfLong2 = this.w;
/*  692 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  693 */         while (j > m) {
/*  694 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  695 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJLong.Merger((CountedCompleter)localObject, arrayOfLong2, arrayOfLong1, k, n, k + n, j - n, i, m));
/*      */           
/*  697 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJLong.Merger(localRelay1, arrayOfLong1, arrayOfLong2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  699 */           new Sorter(localRelay2, arrayOfLong1, arrayOfLong2, i + i2, j - i2, k + i2, m).fork();
/*  700 */           new Sorter(localRelay2, arrayOfLong1, arrayOfLong2, i + n, i1, k + n, m).fork();
/*  701 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJLong.Merger(localRelay1, arrayOfLong1, arrayOfLong2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  703 */           new Sorter(localRelay3, arrayOfLong1, arrayOfLong2, i + i1, n - i1, k + i1, m).fork();
/*  704 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  705 */           j = i1;
/*      */         }
/*  707 */         DualPivotQuicksort.sort(arrayOfLong1, i, i + j - 1, arrayOfLong2, k, j);
/*  708 */         ((CountedCompleter)localObject).tryComplete(); } }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final long[] a;
/*      */       final long[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*  719 */       Merger(CountedCompleter<?> paramCountedCompleter, long[] paramArrayOfLong1, long[] paramArrayOfLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { super();
/*  720 */         this.a = paramArrayOfLong1;this.w = paramArrayOfLong2;
/*  721 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  722 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  723 */         this.wbase = paramInt5;this.gran = paramInt6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  727 */         long[] arrayOfLong1 = this.a;long[] arrayOfLong2 = this.w;
/*  728 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  729 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  730 */         if ((arrayOfLong1 == null) || (arrayOfLong2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  731 */           throw new IllegalStateException();
/*      */         for (;;) { long l1;
/*  733 */           int i4; int i5; if (j >= m) {
/*  734 */             if (j <= i1)
/*      */               break;
/*  736 */             i3 = m;
/*  737 */             l1 = arrayOfLong1[((i2 = j >>> 1) + i)];
/*  738 */             for (i4 = 0; i4 < i3;) {
/*  739 */               i5 = i4 + i3 >>> 1;
/*  740 */               if (l1 <= arrayOfLong1[(i5 + k)]) {
/*  741 */                 i3 = i5;
/*      */               } else {
/*  743 */                 i4 = i5 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  747 */             if (m <= i1)
/*      */               break;
/*  749 */             i2 = j;
/*  750 */             l1 = arrayOfLong1[((i3 = m >>> 1) + k)];
/*  751 */             for (i4 = 0; i4 < i2;) {
/*  752 */               i5 = i4 + i2 >>> 1;
/*  753 */               if (l1 <= arrayOfLong1[(i5 + i)]) {
/*  754 */                 i2 = i5;
/*      */               } else
/*  756 */                 i4 = i5 + 1;
/*      */             }
/*      */           }
/*  759 */           Merger localMerger = new Merger(this, arrayOfLong1, arrayOfLong2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  762 */           m = i3;
/*  763 */           j = i2;
/*  764 */           addToPendingCount(1);
/*  765 */           localMerger.fork();
/*      */         }
/*      */         
/*  768 */         int i2 = i + j;int i3 = k + m;
/*  769 */         while ((i < i2) && (k < i3)) { long l3;
/*      */           long l4;
/*  771 */           long l2; if ((l3 = arrayOfLong1[i]) <= (l4 = arrayOfLong1[k])) {
/*  772 */             i++;l2 = l3;
/*      */           }
/*      */           else {
/*  775 */             k++;l2 = l4;
/*      */           }
/*  777 */           arrayOfLong2[(n++)] = l2;
/*      */         }
/*  779 */         if (k < i3) {
/*  780 */           System.arraycopy(arrayOfLong1, k, arrayOfLong2, n, i3 - k);
/*  781 */         } else if (i < i2)
/*  782 */           System.arraycopy(arrayOfLong1, i, arrayOfLong2, n, i2 - i);
/*  783 */         tryComplete();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FJFloat { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final float[] a;
/*      */       final float[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*  796 */       Sorter(CountedCompleter<?> paramCountedCompleter, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  797 */         this.a = paramArrayOfFloat1;this.w = paramArrayOfFloat2;this.base = paramInt1;this.size = paramInt2;
/*  798 */         this.wbase = paramInt3;this.gran = paramInt4;
/*      */       }
/*      */       
/*  801 */       public final void compute() { Object localObject = this;
/*  802 */         float[] arrayOfFloat1 = this.a;float[] arrayOfFloat2 = this.w;
/*  803 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  804 */         while (j > m) {
/*  805 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  806 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJFloat.Merger((CountedCompleter)localObject, arrayOfFloat2, arrayOfFloat1, k, n, k + n, j - n, i, m));
/*      */           
/*  808 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJFloat.Merger(localRelay1, arrayOfFloat1, arrayOfFloat2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  810 */           new Sorter(localRelay2, arrayOfFloat1, arrayOfFloat2, i + i2, j - i2, k + i2, m).fork();
/*  811 */           new Sorter(localRelay2, arrayOfFloat1, arrayOfFloat2, i + n, i1, k + n, m).fork();
/*  812 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJFloat.Merger(localRelay1, arrayOfFloat1, arrayOfFloat2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  814 */           new Sorter(localRelay3, arrayOfFloat1, arrayOfFloat2, i + i1, n - i1, k + i1, m).fork();
/*  815 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  816 */           j = i1;
/*      */         }
/*  818 */         DualPivotQuicksort.sort(arrayOfFloat1, i, i + j - 1, arrayOfFloat2, k, j);
/*  819 */         ((CountedCompleter)localObject).tryComplete(); } }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final float[] a;
/*      */       final float[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*  830 */       Merger(CountedCompleter<?> paramCountedCompleter, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { super();
/*  831 */         this.a = paramArrayOfFloat1;this.w = paramArrayOfFloat2;
/*  832 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  833 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  834 */         this.wbase = paramInt5;this.gran = paramInt6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  838 */         float[] arrayOfFloat1 = this.a;float[] arrayOfFloat2 = this.w;
/*  839 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  840 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  841 */         if ((arrayOfFloat1 == null) || (arrayOfFloat2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  842 */           throw new IllegalStateException();
/*      */         for (;;) { float f1;
/*  844 */           int i4; int i5; if (j >= m) {
/*  845 */             if (j <= i1)
/*      */               break;
/*  847 */             i3 = m;
/*  848 */             f1 = arrayOfFloat1[((i2 = j >>> 1) + i)];
/*  849 */             for (i4 = 0; i4 < i3;) {
/*  850 */               i5 = i4 + i3 >>> 1;
/*  851 */               if (f1 <= arrayOfFloat1[(i5 + k)]) {
/*  852 */                 i3 = i5;
/*      */               } else {
/*  854 */                 i4 = i5 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  858 */             if (m <= i1)
/*      */               break;
/*  860 */             i2 = j;
/*  861 */             f1 = arrayOfFloat1[((i3 = m >>> 1) + k)];
/*  862 */             for (i4 = 0; i4 < i2;) {
/*  863 */               i5 = i4 + i2 >>> 1;
/*  864 */               if (f1 <= arrayOfFloat1[(i5 + i)]) {
/*  865 */                 i2 = i5;
/*      */               } else
/*  867 */                 i4 = i5 + 1;
/*      */             }
/*      */           }
/*  870 */           Merger localMerger = new Merger(this, arrayOfFloat1, arrayOfFloat2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  873 */           m = i3;
/*  874 */           j = i2;
/*  875 */           addToPendingCount(1);
/*  876 */           localMerger.fork();
/*      */         }
/*      */         
/*  879 */         int i2 = i + j;int i3 = k + m;
/*  880 */         while ((i < i2) && (k < i3)) { float f3;
/*      */           float f4;
/*  882 */           float f2; if ((f3 = arrayOfFloat1[i]) <= (f4 = arrayOfFloat1[k])) {
/*  883 */             i++;f2 = f3;
/*      */           }
/*      */           else {
/*  886 */             k++;f2 = f4;
/*      */           }
/*  888 */           arrayOfFloat2[(n++)] = f2;
/*      */         }
/*  890 */         if (k < i3) {
/*  891 */           System.arraycopy(arrayOfFloat1, k, arrayOfFloat2, n, i3 - k);
/*  892 */         } else if (i < i2)
/*  893 */           System.arraycopy(arrayOfFloat1, i, arrayOfFloat2, n, i2 - i);
/*  894 */         tryComplete();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FJDouble { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final double[] a;
/*      */       final double[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*  907 */       Sorter(CountedCompleter<?> paramCountedCompleter, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super();
/*  908 */         this.a = paramArrayOfDouble1;this.w = paramArrayOfDouble2;this.base = paramInt1;this.size = paramInt2;
/*  909 */         this.wbase = paramInt3;this.gran = paramInt4;
/*      */       }
/*      */       
/*  912 */       public final void compute() { Object localObject = this;
/*  913 */         double[] arrayOfDouble1 = this.a;double[] arrayOfDouble2 = this.w;
/*  914 */         int i = this.base;int j = this.size;int k = this.wbase;int m = this.gran;
/*  915 */         while (j > m) {
/*  916 */           int n = j >>> 1;int i1 = n >>> 1;int i2 = n + i1;
/*  917 */           ArraysParallelSortHelpers.Relay localRelay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJDouble.Merger((CountedCompleter)localObject, arrayOfDouble2, arrayOfDouble1, k, n, k + n, j - n, i, m));
/*      */           
/*  919 */           ArraysParallelSortHelpers.Relay localRelay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJDouble.Merger(localRelay1, arrayOfDouble1, arrayOfDouble2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  921 */           new Sorter(localRelay2, arrayOfDouble1, arrayOfDouble2, i + i2, j - i2, k + i2, m).fork();
/*  922 */           new Sorter(localRelay2, arrayOfDouble1, arrayOfDouble2, i + n, i1, k + n, m).fork();
/*  923 */           ArraysParallelSortHelpers.Relay localRelay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJDouble.Merger(localRelay1, arrayOfDouble1, arrayOfDouble2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  925 */           new Sorter(localRelay3, arrayOfDouble1, arrayOfDouble2, i + i1, n - i1, k + i1, m).fork();
/*  926 */           localObject = new ArraysParallelSortHelpers.EmptyCompleter(localRelay3);
/*  927 */           j = i1;
/*      */         }
/*  929 */         DualPivotQuicksort.sort(arrayOfDouble1, i, i + j - 1, arrayOfDouble2, k, j);
/*  930 */         ((CountedCompleter)localObject).tryComplete(); } }
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final double[] a;
/*      */       final double[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*  941 */       Merger(CountedCompleter<?> paramCountedCompleter, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { super();
/*  942 */         this.a = paramArrayOfDouble1;this.w = paramArrayOfDouble2;
/*  943 */         this.lbase = paramInt1;this.lsize = paramInt2;
/*  944 */         this.rbase = paramInt3;this.rsize = paramInt4;
/*  945 */         this.wbase = paramInt5;this.gran = paramInt6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  949 */         double[] arrayOfDouble1 = this.a;double[] arrayOfDouble2 = this.w;
/*  950 */         int i = this.lbase;int j = this.lsize;int k = this.rbase;
/*  951 */         int m = this.rsize;int n = this.wbase;int i1 = this.gran;
/*  952 */         if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null) || (i < 0) || (k < 0) || (n < 0))
/*  953 */           throw new IllegalStateException();
/*      */         for (;;) { double d1;
/*  955 */           int i4; int i5; if (j >= m) {
/*  956 */             if (j <= i1)
/*      */               break;
/*  958 */             i3 = m;
/*  959 */             d1 = arrayOfDouble1[((i2 = j >>> 1) + i)];
/*  960 */             for (i4 = 0; i4 < i3;) {
/*  961 */               i5 = i4 + i3 >>> 1;
/*  962 */               if (d1 <= arrayOfDouble1[(i5 + k)]) {
/*  963 */                 i3 = i5;
/*      */               } else {
/*  965 */                 i4 = i5 + 1;
/*      */               }
/*      */             }
/*      */           } else {
/*  969 */             if (m <= i1)
/*      */               break;
/*  971 */             i2 = j;
/*  972 */             d1 = arrayOfDouble1[((i3 = m >>> 1) + k)];
/*  973 */             for (i4 = 0; i4 < i2;) {
/*  974 */               i5 = i4 + i2 >>> 1;
/*  975 */               if (d1 <= arrayOfDouble1[(i5 + i)]) {
/*  976 */                 i2 = i5;
/*      */               } else
/*  978 */                 i4 = i5 + 1;
/*      */             }
/*      */           }
/*  981 */           Merger localMerger = new Merger(this, arrayOfDouble1, arrayOfDouble2, i + i2, j - i2, k + i3, m - i3, n + i2 + i3, i1);
/*      */           
/*      */ 
/*  984 */           m = i3;
/*  985 */           j = i2;
/*  986 */           addToPendingCount(1);
/*  987 */           localMerger.fork();
/*      */         }
/*      */         
/*  990 */         int i2 = i + j;int i3 = k + m;
/*  991 */         while ((i < i2) && (k < i3)) { double d3;
/*      */           double d4;
/*  993 */           double d2; if ((d3 = arrayOfDouble1[i]) <= (d4 = arrayOfDouble1[k])) {
/*  994 */             i++;d2 = d3;
/*      */           }
/*      */           else {
/*  997 */             k++;d2 = d4;
/*      */           }
/*  999 */           arrayOfDouble2[(n++)] = d2;
/*      */         }
/* 1001 */         if (k < i3) {
/* 1002 */           System.arraycopy(arrayOfDouble1, k, arrayOfDouble2, n, i3 - k);
/* 1003 */         } else if (i < i2)
/* 1004 */           System.arraycopy(arrayOfDouble1, i, arrayOfDouble2, n, i2 - i);
/* 1005 */         tryComplete();
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ArraysParallelSortHelpers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */