/*     */ package java.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimSort<T>
/*     */ {
/*     */   private static final int MIN_MERGE = 32;
/*     */   private final T[] a;
/*     */   private final Comparator<? super T> c;
/*     */   private static final int MIN_GALLOP = 7;
/* 103 */   private int minGallop = 7;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private T[] tmp;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int tmpBase;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int tmpLen;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */   private int stackSize = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] runBase;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] runLen;
/*     */   
/*     */ 
/*     */ 
/*     */   private TimSort(T[] paramArrayOfT1, Comparator<? super T> paramComparator, T[] paramArrayOfT2, int paramInt1, int paramInt2)
/*     */   {
/* 147 */     this.a = paramArrayOfT1;
/* 148 */     this.c = paramComparator;
/*     */     
/*     */ 
/* 151 */     int i = paramArrayOfT1.length;
/* 152 */     int j = i < 512 ? i >>> 1 : 256;
/*     */     
/* 154 */     if ((paramArrayOfT2 == null) || (paramInt2 < j) || (paramInt1 + j > paramArrayOfT2.length))
/*     */     {
/*     */ 
/* 157 */       Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT1.getClass().getComponentType(), j);
/* 158 */       this.tmp = arrayOfObject;
/* 159 */       this.tmpBase = 0;
/* 160 */       this.tmpLen = j;
/*     */     }
/*     */     else {
/* 163 */       this.tmp = paramArrayOfT2;
/* 164 */       this.tmpBase = paramInt1;
/* 165 */       this.tmpLen = paramInt2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 178 */     int k = i < 119151 ? 24 : i < 1542 ? 10 : i < 120 ? 5 : 40;
/*     */     
/*     */ 
/* 181 */     this.runBase = new int[k];
/* 182 */     this.runLen = new int[k];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static <T> void sort(T[] paramArrayOfT1, int paramInt1, int paramInt2, Comparator<? super T> paramComparator, T[] paramArrayOfT2, int paramInt3, int paramInt4)
/*     */   {
/* 208 */     assert ((paramComparator != null) && (paramArrayOfT1 != null) && (paramInt1 >= 0) && (paramInt1 <= paramInt2) && (paramInt2 <= paramArrayOfT1.length));
/*     */     
/* 210 */     int i = paramInt2 - paramInt1;
/* 211 */     if (i < 2) {
/* 212 */       return;
/*     */     }
/*     */     
/* 215 */     if (i < 32) {
/* 216 */       int j = countRunAndMakeAscending(paramArrayOfT1, paramInt1, paramInt2, paramComparator);
/* 217 */       binarySort(paramArrayOfT1, paramInt1, paramInt2, paramInt1 + j, paramComparator);
/* 218 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 226 */     TimSort localTimSort = new TimSort(paramArrayOfT1, paramComparator, paramArrayOfT2, paramInt3, paramInt4);
/* 227 */     int k = minRunLength(i);
/*     */     do
/*     */     {
/* 230 */       int m = countRunAndMakeAscending(paramArrayOfT1, paramInt1, paramInt2, paramComparator);
/*     */       
/*     */ 
/* 233 */       if (m < k) {
/* 234 */         int n = i <= k ? i : k;
/* 235 */         binarySort(paramArrayOfT1, paramInt1, paramInt1 + n, paramInt1 + m, paramComparator);
/* 236 */         m = n;
/*     */       }
/*     */       
/*     */ 
/* 240 */       localTimSort.pushRun(paramInt1, m);
/* 241 */       localTimSort.mergeCollapse();
/*     */       
/*     */ 
/* 244 */       paramInt1 += m;
/* 245 */       i -= m;
/* 246 */     } while (i != 0);
/*     */     
/*     */ 
/* 249 */     assert (paramInt1 == paramInt2);
/* 250 */     localTimSort.mergeForceCollapse();
/* 251 */     assert (localTimSort.stackSize == 1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <T> void binarySort(T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, Comparator<? super T> paramComparator)
/*     */   {
/* 275 */     assert ((paramInt1 <= paramInt3) && (paramInt3 <= paramInt2));
/* 276 */     if (paramInt3 == paramInt1)
/* 277 */       paramInt3++;
/* 278 */     for (; paramInt3 < paramInt2; paramInt3++) {
/* 279 */       T ? = paramArrayOfT[paramInt3];
/*     */       
/*     */ 
/* 282 */       int i = paramInt1;
/* 283 */       int j = paramInt3;
/* 284 */       assert (i <= j);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 290 */       while (i < j) {
/* 291 */         k = i + j >>> 1;
/* 292 */         if (paramComparator.compare(?, paramArrayOfT[k]) < 0) {
/* 293 */           j = k;
/*     */         } else
/* 295 */           i = k + 1;
/*     */       }
/* 297 */       assert (i == j);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 306 */       int k = paramInt3 - i;
/*     */       
/* 308 */       switch (k) {
/* 309 */       case 2:  paramArrayOfT[(i + 2)] = paramArrayOfT[(i + 1)];
/* 310 */       case 1:  paramArrayOfT[(i + 1)] = paramArrayOfT[i];
/* 311 */         break;
/* 312 */       default:  System.arraycopy(paramArrayOfT, i, paramArrayOfT, i + 1, k);
/*     */       }
/* 314 */       paramArrayOfT[i] = ?;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <T> int countRunAndMakeAscending(T[] paramArrayOfT, int paramInt1, int paramInt2, Comparator<? super T> paramComparator)
/*     */   {
/* 345 */     assert (paramInt1 < paramInt2);
/* 346 */     int i = paramInt1 + 1;
/* 347 */     if (i == paramInt2) {
/* 348 */       return 1;
/*     */     }
/*     */     
/* 351 */     if (paramComparator.compare(paramArrayOfT[(i++)], paramArrayOfT[paramInt1]) < 0) {
/* 352 */       while ((i < paramInt2) && (paramComparator.compare(paramArrayOfT[i], paramArrayOfT[(i - 1)]) < 0))
/* 353 */         i++;
/* 354 */       reverseRange(paramArrayOfT, paramInt1, i);
/*     */     } else {
/* 356 */       while ((i < paramInt2) && (paramComparator.compare(paramArrayOfT[i], paramArrayOfT[(i - 1)]) >= 0)) {
/* 357 */         i++;
/*     */       }
/*     */     }
/* 360 */     return i - paramInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void reverseRange(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*     */   {
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 372 */     while (paramInt1 < paramInt2) {
/* 373 */       Object localObject = paramArrayOfObject[paramInt1];
/* 374 */       paramArrayOfObject[(paramInt1++)] = paramArrayOfObject[paramInt2];
/* 375 */       paramArrayOfObject[(paramInt2--)] = localObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int minRunLength(int paramInt)
/*     */   {
/* 397 */     assert (paramInt >= 0);
/* 398 */     int i = 0;
/* 399 */     while (paramInt >= 32) {
/* 400 */       i |= paramInt & 0x1;
/* 401 */       paramInt >>= 1;
/*     */     }
/* 403 */     return paramInt + i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void pushRun(int paramInt1, int paramInt2)
/*     */   {
/* 413 */     this.runBase[this.stackSize] = paramInt1;
/* 414 */     this.runLen[this.stackSize] = paramInt2;
/* 415 */     this.stackSize += 1;
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
/*     */   private void mergeCollapse()
/*     */   {
/* 430 */     while (this.stackSize > 1) {
/* 431 */       int i = this.stackSize - 2;
/* 432 */       if ((i > 0) && (this.runLen[(i - 1)] <= this.runLen[i] + this.runLen[(i + 1)])) {
/* 433 */         if (this.runLen[(i - 1)] < this.runLen[(i + 1)])
/* 434 */           i--;
/* 435 */         mergeAt(i);
/* 436 */       } else { if (this.runLen[i] > this.runLen[(i + 1)]) break;
/* 437 */         mergeAt(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mergeForceCollapse()
/*     */   {
/* 449 */     while (this.stackSize > 1) {
/* 450 */       int i = this.stackSize - 2;
/* 451 */       if ((i > 0) && (this.runLen[(i - 1)] < this.runLen[(i + 1)]))
/* 452 */         i--;
/* 453 */       mergeAt(i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mergeAt(int paramInt)
/*     */   {
/* 465 */     assert (this.stackSize >= 2);
/* 466 */     assert (paramInt >= 0);
/* 467 */     assert ((paramInt == this.stackSize - 2) || (paramInt == this.stackSize - 3));
/*     */     
/* 469 */     int i = this.runBase[paramInt];
/* 470 */     int j = this.runLen[paramInt];
/* 471 */     int k = this.runBase[(paramInt + 1)];
/* 472 */     int m = this.runLen[(paramInt + 1)];
/* 473 */     assert ((j > 0) && (m > 0));
/* 474 */     assert (i + j == k);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 481 */     this.runLen[paramInt] = (j + m);
/* 482 */     if (paramInt == this.stackSize - 3) {
/* 483 */       this.runBase[(paramInt + 1)] = this.runBase[(paramInt + 2)];
/* 484 */       this.runLen[(paramInt + 1)] = this.runLen[(paramInt + 2)];
/*     */     }
/* 486 */     this.stackSize -= 1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 492 */     int n = gallopRight(this.a[k], this.a, i, j, 0, this.c);
/* 493 */     assert (n >= 0);
/* 494 */     i += n;
/* 495 */     j -= n;
/* 496 */     if (j == 0) {
/* 497 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 503 */     m = gallopLeft(this.a[(i + j - 1)], this.a, k, m, m - 1, this.c);
/* 504 */     assert (m >= 0);
/* 505 */     if (m == 0) {
/* 506 */       return;
/*     */     }
/*     */     
/* 509 */     if (j <= m) {
/* 510 */       mergeLo(i, j, k, m);
/*     */     } else {
/* 512 */       mergeHi(i, j, k, m);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <T> int gallopLeft(T paramT, T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, Comparator<? super T> paramComparator)
/*     */   {
/* 535 */     assert ((paramInt2 > 0) && (paramInt3 >= 0) && (paramInt3 < paramInt2));
/* 536 */     int i = 0;
/* 537 */     int j = 1;
/* 538 */     int k; if (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + paramInt3)]) > 0)
/*     */     {
/* 540 */       k = paramInt2 - paramInt3;
/* 541 */       while ((j < k) && (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + paramInt3 + j)]) > 0)) {
/* 542 */         i = j;
/* 543 */         j = (j << 1) + 1;
/* 544 */         if (j <= 0)
/* 545 */           j = k;
/*     */       }
/* 547 */       if (j > k) {
/* 548 */         j = k;
/*     */       }
/*     */       
/* 551 */       i += paramInt3;
/* 552 */       j += paramInt3;
/*     */     }
/*     */     else {
/* 555 */       k = paramInt3 + 1;
/* 556 */       while ((j < k) && (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + paramInt3 - j)]) <= 0)) {
/* 557 */         i = j;
/* 558 */         j = (j << 1) + 1;
/* 559 */         if (j <= 0)
/* 560 */           j = k;
/*     */       }
/* 562 */       if (j > k) {
/* 563 */         j = k;
/*     */       }
/*     */       
/* 566 */       int m = i;
/* 567 */       i = paramInt3 - j;
/* 568 */       j = paramInt3 - m;
/*     */     }
/* 570 */     assert ((-1 <= i) && (i < j) && (j <= paramInt2));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 577 */     i++;
/* 578 */     while (i < j) {
/* 579 */       k = i + (j - i >>> 1);
/*     */       
/* 581 */       if (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + k)]) > 0) {
/* 582 */         i = k + 1;
/*     */       } else
/* 584 */         j = k;
/*     */     }
/* 586 */     assert (i == j);
/* 587 */     return j;
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
/*     */   private static <T> int gallopRight(T paramT, T[] paramArrayOfT, int paramInt1, int paramInt2, int paramInt3, Comparator<? super T> paramComparator)
/*     */   {
/* 605 */     assert ((paramInt2 > 0) && (paramInt3 >= 0) && (paramInt3 < paramInt2));
/*     */     
/* 607 */     int i = 1;
/* 608 */     int j = 0;
/* 609 */     int k; if (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + paramInt3)]) < 0)
/*     */     {
/* 611 */       k = paramInt3 + 1;
/* 612 */       while ((i < k) && (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + paramInt3 - i)]) < 0)) {
/* 613 */         j = i;
/* 614 */         i = (i << 1) + 1;
/* 615 */         if (i <= 0)
/* 616 */           i = k;
/*     */       }
/* 618 */       if (i > k) {
/* 619 */         i = k;
/*     */       }
/*     */       
/* 622 */       int m = j;
/* 623 */       j = paramInt3 - i;
/* 624 */       i = paramInt3 - m;
/*     */     }
/*     */     else {
/* 627 */       k = paramInt2 - paramInt3;
/* 628 */       while ((i < k) && (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + paramInt3 + i)]) >= 0)) {
/* 629 */         j = i;
/* 630 */         i = (i << 1) + 1;
/* 631 */         if (i <= 0)
/* 632 */           i = k;
/*     */       }
/* 634 */       if (i > k) {
/* 635 */         i = k;
/*     */       }
/*     */       
/* 638 */       j += paramInt3;
/* 639 */       i += paramInt3;
/*     */     }
/* 641 */     assert ((-1 <= j) && (j < i) && (i <= paramInt2));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 648 */     j++;
/* 649 */     while (j < i) {
/* 650 */       k = j + (i - j >>> 1);
/*     */       
/* 652 */       if (paramComparator.compare(paramT, paramArrayOfT[(paramInt1 + k)]) < 0) {
/* 653 */         i = k;
/*     */       } else
/* 655 */         j = k + 1;
/*     */     }
/* 657 */     assert (j == i);
/* 658 */     return i;
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
/*     */   private void mergeLo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 678 */     assert ((paramInt2 > 0) && (paramInt4 > 0) && (paramInt1 + paramInt2 == paramInt3));
/*     */     
/*     */ 
/* 681 */     Object[] arrayOfObject1 = this.a;
/* 682 */     Object[] arrayOfObject2 = ensureCapacity(paramInt2);
/* 683 */     int i = this.tmpBase;
/* 684 */     int j = paramInt3;
/* 685 */     int k = paramInt1;
/* 686 */     System.arraycopy(arrayOfObject1, paramInt1, arrayOfObject2, i, paramInt2);
/*     */     
/*     */ 
/* 689 */     arrayOfObject1[(k++)] = arrayOfObject1[(j++)];
/* 690 */     paramInt4--; if (paramInt4 == 0) {
/* 691 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, paramInt2);
/* 692 */       return;
/*     */     }
/* 694 */     if (paramInt2 == 1) {
/* 695 */       System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, paramInt4);
/* 696 */       arrayOfObject1[(k + paramInt4)] = arrayOfObject2[i];
/* 697 */       return;
/*     */     }
/*     */     
/* 700 */     Comparator localComparator = this.c;
/* 701 */     int m = this.minGallop;
/*     */     for (;;)
/*     */     {
/* 704 */       int n = 0;
/* 705 */       int i1 = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 712 */         assert ((paramInt2 > 1) && (paramInt4 > 0));
/* 713 */         if (localComparator.compare(arrayOfObject1[j], arrayOfObject2[i]) < 0) {
/* 714 */           arrayOfObject1[(k++)] = arrayOfObject1[(j++)];
/* 715 */           i1++;
/* 716 */           n = 0;
/* 717 */           paramInt4--; if (paramInt4 == 0)
/*     */             break;
/*     */         } else {
/* 720 */           arrayOfObject1[(k++)] = arrayOfObject2[(i++)];
/* 721 */           n++;
/* 722 */           i1 = 0;
/* 723 */           paramInt2--; if (paramInt2 == 1)
/*     */             break;
/*     */         }
/* 726 */       } while ((n | i1) < m);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 734 */         assert ((paramInt2 > 1) && (paramInt4 > 0));
/* 735 */         n = gallopRight(arrayOfObject1[j], arrayOfObject2, i, paramInt2, 0, localComparator);
/* 736 */         if (n != 0) {
/* 737 */           System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, n);
/* 738 */           k += n;
/* 739 */           i += n;
/* 740 */           paramInt2 -= n;
/* 741 */           if (paramInt2 <= 1)
/*     */             break;
/*     */         }
/* 744 */         arrayOfObject1[(k++)] = arrayOfObject1[(j++)];
/* 745 */         paramInt4--; if (paramInt4 == 0) {
/*     */           break;
/*     */         }
/* 748 */         i1 = gallopLeft(arrayOfObject2[i], arrayOfObject1, j, paramInt4, 0, localComparator);
/* 749 */         if (i1 != 0) {
/* 750 */           System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, i1);
/* 751 */           k += i1;
/* 752 */           j += i1;
/* 753 */           paramInt4 -= i1;
/* 754 */           if (paramInt4 == 0)
/*     */             break;
/*     */         }
/* 757 */         arrayOfObject1[(k++)] = arrayOfObject2[(i++)];
/* 758 */         paramInt2--; if (paramInt2 == 1)
/*     */           break;
/* 760 */         m--;
/* 761 */       } while (((n >= 7 ? 1 : 0) | (i1 >= 7 ? 1 : 0)) != 0);
/* 762 */       if (m < 0)
/* 763 */         m = 0;
/* 764 */       m += 2;
/*     */     }
/* 766 */     this.minGallop = (m < 1 ? 1 : m);
/*     */     
/* 768 */     if (paramInt2 == 1) {
/* 769 */       assert (paramInt4 > 0);
/* 770 */       System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, paramInt4);
/* 771 */       arrayOfObject1[(k + paramInt4)] = arrayOfObject2[i];
/* 772 */     } else { if (paramInt2 == 0) {
/* 773 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 776 */       assert (paramInt4 == 0);
/* 777 */       assert (paramInt2 > 1);
/* 778 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, paramInt2);
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
/*     */   private void mergeHi(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 794 */     assert ((paramInt2 > 0) && (paramInt4 > 0) && (paramInt1 + paramInt2 == paramInt3));
/*     */     
/*     */ 
/* 797 */     Object[] arrayOfObject1 = this.a;
/* 798 */     Object[] arrayOfObject2 = ensureCapacity(paramInt4);
/* 799 */     int i = this.tmpBase;
/* 800 */     System.arraycopy(arrayOfObject1, paramInt3, arrayOfObject2, i, paramInt4);
/*     */     
/* 802 */     int j = paramInt1 + paramInt2 - 1;
/* 803 */     int k = i + paramInt4 - 1;
/* 804 */     int m = paramInt3 + paramInt4 - 1;
/*     */     
/*     */ 
/* 807 */     arrayOfObject1[(m--)] = arrayOfObject1[(j--)];
/* 808 */     paramInt2--; if (paramInt2 == 0) {
/* 809 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, m - (paramInt4 - 1), paramInt4);
/* 810 */       return;
/*     */     }
/* 812 */     if (paramInt4 == 1) {
/* 813 */       m -= paramInt2;
/* 814 */       j -= paramInt2;
/* 815 */       System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, paramInt2);
/* 816 */       arrayOfObject1[m] = arrayOfObject2[k];
/* 817 */       return;
/*     */     }
/*     */     
/* 820 */     Comparator localComparator = this.c;
/* 821 */     int n = this.minGallop;
/*     */     for (;;)
/*     */     {
/* 824 */       int i1 = 0;
/* 825 */       int i2 = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 832 */         assert ((paramInt2 > 0) && (paramInt4 > 1));
/* 833 */         if (localComparator.compare(arrayOfObject2[k], arrayOfObject1[j]) < 0) {
/* 834 */           arrayOfObject1[(m--)] = arrayOfObject1[(j--)];
/* 835 */           i1++;
/* 836 */           i2 = 0;
/* 837 */           paramInt2--; if (paramInt2 == 0)
/*     */             break;
/*     */         } else {
/* 840 */           arrayOfObject1[(m--)] = arrayOfObject2[(k--)];
/* 841 */           i2++;
/* 842 */           i1 = 0;
/* 843 */           paramInt4--; if (paramInt4 == 1)
/*     */             break;
/*     */         }
/* 846 */       } while ((i1 | i2) < n);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 854 */         assert ((paramInt2 > 0) && (paramInt4 > 1));
/* 855 */         i1 = paramInt2 - gallopRight(arrayOfObject2[k], arrayOfObject1, paramInt1, paramInt2, paramInt2 - 1, localComparator);
/* 856 */         if (i1 != 0) {
/* 857 */           m -= i1;
/* 858 */           j -= i1;
/* 859 */           paramInt2 -= i1;
/* 860 */           System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, i1);
/* 861 */           if (paramInt2 == 0)
/*     */             break;
/*     */         }
/* 864 */         arrayOfObject1[(m--)] = arrayOfObject2[(k--)];
/* 865 */         paramInt4--; if (paramInt4 == 1) {
/*     */           break;
/*     */         }
/* 868 */         i2 = paramInt4 - gallopLeft(arrayOfObject1[j], arrayOfObject2, i, paramInt4, paramInt4 - 1, localComparator);
/* 869 */         if (i2 != 0) {
/* 870 */           m -= i2;
/* 871 */           k -= i2;
/* 872 */           paramInt4 -= i2;
/* 873 */           System.arraycopy(arrayOfObject2, k + 1, arrayOfObject1, m + 1, i2);
/* 874 */           if (paramInt4 <= 1)
/*     */             break;
/*     */         }
/* 877 */         arrayOfObject1[(m--)] = arrayOfObject1[(j--)];
/* 878 */         paramInt2--; if (paramInt2 == 0)
/*     */           break;
/* 880 */         n--;
/* 881 */       } while (((i1 >= 7 ? 1 : 0) | (i2 >= 7 ? 1 : 0)) != 0);
/* 882 */       if (n < 0)
/* 883 */         n = 0;
/* 884 */       n += 2;
/*     */     }
/* 886 */     this.minGallop = (n < 1 ? 1 : n);
/*     */     
/* 888 */     if (paramInt4 == 1) {
/* 889 */       assert (paramInt2 > 0);
/* 890 */       m -= paramInt2;
/* 891 */       j -= paramInt2;
/* 892 */       System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, paramInt2);
/* 893 */       arrayOfObject1[m] = arrayOfObject2[k];
/* 894 */     } else { if (paramInt4 == 0) {
/* 895 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 898 */       assert (paramInt2 == 0);
/* 899 */       assert (paramInt4 > 0);
/* 900 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, m - (paramInt4 - 1), paramInt4);
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
/*     */   private T[] ensureCapacity(int paramInt)
/*     */   {
/* 913 */     if (this.tmpLen < paramInt)
/*     */     {
/* 915 */       int i = paramInt;
/* 916 */       i |= i >> 1;
/* 917 */       i |= i >> 2;
/* 918 */       i |= i >> 4;
/* 919 */       i |= i >> 8;
/* 920 */       i |= i >> 16;
/* 921 */       i++;
/*     */       
/* 923 */       if (i < 0) {
/* 924 */         i = paramInt;
/*     */       } else {
/* 926 */         i = Math.min(i, this.a.length >>> 1);
/*     */       }
/*     */       
/*     */ 
/* 930 */       Object[] arrayOfObject = (Object[])Array.newInstance(this.a.getClass().getComponentType(), i);
/* 931 */       this.tmp = arrayOfObject;
/* 932 */       this.tmpLen = i;
/* 933 */       this.tmpBase = 0;
/*     */     }
/* 935 */     return this.tmp;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/TimSort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */