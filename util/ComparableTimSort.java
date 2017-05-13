/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ComparableTimSort
/*     */ {
/*     */   private static final int MIN_MERGE = 32;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Object[] a;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int MIN_GALLOP = 7;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */   private int minGallop = 7;
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
/*     */   private Object[] tmp;
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
/* 108 */   private int stackSize = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] runBase;
/*     */   
/*     */ 
/*     */   private final int[] runLen;
/*     */   
/*     */ 
/*     */ 
/*     */   private ComparableTimSort(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt1, int paramInt2)
/*     */   {
/* 121 */     this.a = paramArrayOfObject1;
/*     */     
/*     */ 
/* 124 */     int i = paramArrayOfObject1.length;
/* 125 */     int j = i < 512 ? i >>> 1 : 256;
/*     */     
/* 127 */     if ((paramArrayOfObject2 == null) || (paramInt2 < j) || (paramInt1 + j > paramArrayOfObject2.length)) {
/* 128 */       this.tmp = new Object[j];
/* 129 */       this.tmpBase = 0;
/* 130 */       this.tmpLen = j;
/*     */     }
/*     */     else {
/* 133 */       this.tmp = paramArrayOfObject2;
/* 134 */       this.tmpBase = paramInt1;
/* 135 */       this.tmpLen = paramInt2;
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
/* 148 */     int k = i < 119151 ? 24 : i < 1542 ? 10 : i < 120 ? 5 : 40;
/*     */     
/*     */ 
/* 151 */     this.runBase = new int[k];
/* 152 */     this.runLen = new int[k];
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
/*     */   static void sort(Object[] paramArrayOfObject1, int paramInt1, int paramInt2, Object[] paramArrayOfObject2, int paramInt3, int paramInt4)
/*     */   {
/* 176 */     assert ((paramArrayOfObject1 != null) && (paramInt1 >= 0) && (paramInt1 <= paramInt2) && (paramInt2 <= paramArrayOfObject1.length));
/*     */     
/* 178 */     int i = paramInt2 - paramInt1;
/* 179 */     if (i < 2) {
/* 180 */       return;
/*     */     }
/*     */     
/* 183 */     if (i < 32) {
/* 184 */       int j = countRunAndMakeAscending(paramArrayOfObject1, paramInt1, paramInt2);
/* 185 */       binarySort(paramArrayOfObject1, paramInt1, paramInt2, paramInt1 + j);
/* 186 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 194 */     ComparableTimSort localComparableTimSort = new ComparableTimSort(paramArrayOfObject1, paramArrayOfObject2, paramInt3, paramInt4);
/* 195 */     int k = minRunLength(i);
/*     */     do
/*     */     {
/* 198 */       int m = countRunAndMakeAscending(paramArrayOfObject1, paramInt1, paramInt2);
/*     */       
/*     */ 
/* 201 */       if (m < k) {
/* 202 */         int n = i <= k ? i : k;
/* 203 */         binarySort(paramArrayOfObject1, paramInt1, paramInt1 + n, paramInt1 + m);
/* 204 */         m = n;
/*     */       }
/*     */       
/*     */ 
/* 208 */       localComparableTimSort.pushRun(paramInt1, m);
/* 209 */       localComparableTimSort.mergeCollapse();
/*     */       
/*     */ 
/* 212 */       paramInt1 += m;
/* 213 */       i -= m;
/* 214 */     } while (i != 0);
/*     */     
/*     */ 
/* 217 */     assert (paramInt1 == paramInt2);
/* 218 */     localComparableTimSort.mergeForceCollapse();
/* 219 */     assert (localComparableTimSort.stackSize == 1);
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
/*     */   private static void binarySort(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 241 */     assert ((paramInt1 <= paramInt3) && (paramInt3 <= paramInt2));
/* 242 */     if (paramInt3 == paramInt1)
/* 243 */       paramInt3++;
/* 244 */     for (; paramInt3 < paramInt2; paramInt3++) {
/* 245 */       Comparable localComparable = (Comparable)paramArrayOfObject[paramInt3];
/*     */       
/*     */ 
/* 248 */       int i = paramInt1;
/* 249 */       int j = paramInt3;
/* 250 */       assert (i <= j);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 256 */       while (i < j) {
/* 257 */         k = i + j >>> 1;
/* 258 */         if (localComparable.compareTo(paramArrayOfObject[k]) < 0) {
/* 259 */           j = k;
/*     */         } else
/* 261 */           i = k + 1;
/*     */       }
/* 263 */       assert (i == j);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 272 */       int k = paramInt3 - i;
/*     */       
/* 274 */       switch (k) {
/* 275 */       case 2:  paramArrayOfObject[(i + 2)] = paramArrayOfObject[(i + 1)];
/* 276 */       case 1:  paramArrayOfObject[(i + 1)] = paramArrayOfObject[i];
/* 277 */         break;
/* 278 */       default:  System.arraycopy(paramArrayOfObject, i, paramArrayOfObject, i + 1, k);
/*     */       }
/* 280 */       paramArrayOfObject[i] = localComparable;
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
/*     */   private static int countRunAndMakeAscending(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*     */   {
/* 310 */     assert (paramInt1 < paramInt2);
/* 311 */     int i = paramInt1 + 1;
/* 312 */     if (i == paramInt2) {
/* 313 */       return 1;
/*     */     }
/*     */     
/* 316 */     if (((Comparable)paramArrayOfObject[(i++)]).compareTo(paramArrayOfObject[paramInt1]) < 0) {
/* 317 */       while ((i < paramInt2) && (((Comparable)paramArrayOfObject[i]).compareTo(paramArrayOfObject[(i - 1)]) < 0))
/* 318 */         i++;
/* 319 */       reverseRange(paramArrayOfObject, paramInt1, i);
/*     */     } else {
/* 321 */       while ((i < paramInt2) && (((Comparable)paramArrayOfObject[i]).compareTo(paramArrayOfObject[(i - 1)]) >= 0)) {
/* 322 */         i++;
/*     */       }
/*     */     }
/* 325 */     return i - paramInt1;
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
/* 337 */     while (paramInt1 < paramInt2) {
/* 338 */       Object localObject = paramArrayOfObject[paramInt1];
/* 339 */       paramArrayOfObject[(paramInt1++)] = paramArrayOfObject[paramInt2];
/* 340 */       paramArrayOfObject[(paramInt2--)] = localObject;
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
/* 362 */     assert (paramInt >= 0);
/* 363 */     int i = 0;
/* 364 */     while (paramInt >= 32) {
/* 365 */       i |= paramInt & 0x1;
/* 366 */       paramInt >>= 1;
/*     */     }
/* 368 */     return paramInt + i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void pushRun(int paramInt1, int paramInt2)
/*     */   {
/* 378 */     this.runBase[this.stackSize] = paramInt1;
/* 379 */     this.runLen[this.stackSize] = paramInt2;
/* 380 */     this.stackSize += 1;
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
/* 395 */     while (this.stackSize > 1) {
/* 396 */       int i = this.stackSize - 2;
/* 397 */       if ((i > 0) && (this.runLen[(i - 1)] <= this.runLen[i] + this.runLen[(i + 1)])) {
/* 398 */         if (this.runLen[(i - 1)] < this.runLen[(i + 1)])
/* 399 */           i--;
/* 400 */         mergeAt(i);
/* 401 */       } else { if (this.runLen[i] > this.runLen[(i + 1)]) break;
/* 402 */         mergeAt(i);
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
/* 414 */     while (this.stackSize > 1) {
/* 415 */       int i = this.stackSize - 2;
/* 416 */       if ((i > 0) && (this.runLen[(i - 1)] < this.runLen[(i + 1)]))
/* 417 */         i--;
/* 418 */       mergeAt(i);
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
/*     */   private void mergeAt(int paramInt)
/*     */   {
/* 431 */     assert (this.stackSize >= 2);
/* 432 */     assert (paramInt >= 0);
/* 433 */     assert ((paramInt == this.stackSize - 2) || (paramInt == this.stackSize - 3));
/*     */     
/* 435 */     int i = this.runBase[paramInt];
/* 436 */     int j = this.runLen[paramInt];
/* 437 */     int k = this.runBase[(paramInt + 1)];
/* 438 */     int m = this.runLen[(paramInt + 1)];
/* 439 */     assert ((j > 0) && (m > 0));
/* 440 */     assert (i + j == k);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 447 */     this.runLen[paramInt] = (j + m);
/* 448 */     if (paramInt == this.stackSize - 3) {
/* 449 */       this.runBase[(paramInt + 1)] = this.runBase[(paramInt + 2)];
/* 450 */       this.runLen[(paramInt + 1)] = this.runLen[(paramInt + 2)];
/*     */     }
/* 452 */     this.stackSize -= 1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 458 */     int n = gallopRight((Comparable)this.a[k], this.a, i, j, 0);
/* 459 */     assert (n >= 0);
/* 460 */     i += n;
/* 461 */     j -= n;
/* 462 */     if (j == 0) {
/* 463 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 469 */     m = gallopLeft((Comparable)this.a[(i + j - 1)], this.a, k, m, m - 1);
/*     */     
/* 471 */     assert (m >= 0);
/* 472 */     if (m == 0) {
/* 473 */       return;
/*     */     }
/*     */     
/* 476 */     if (j <= m) {
/* 477 */       mergeLo(i, j, k, m);
/*     */     } else {
/* 479 */       mergeHi(i, j, k, m);
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
/*     */   private static int gallopLeft(Comparable<Object> paramComparable, Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 501 */     assert ((paramInt2 > 0) && (paramInt3 >= 0) && (paramInt3 < paramInt2));
/*     */     
/* 503 */     int i = 0;
/* 504 */     int j = 1;
/* 505 */     int k; if (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + paramInt3)]) > 0)
/*     */     {
/* 507 */       k = paramInt2 - paramInt3;
/* 508 */       while ((j < k) && (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + paramInt3 + j)]) > 0)) {
/* 509 */         i = j;
/* 510 */         j = (j << 1) + 1;
/* 511 */         if (j <= 0)
/* 512 */           j = k;
/*     */       }
/* 514 */       if (j > k) {
/* 515 */         j = k;
/*     */       }
/*     */       
/* 518 */       i += paramInt3;
/* 519 */       j += paramInt3;
/*     */     }
/*     */     else {
/* 522 */       k = paramInt3 + 1;
/* 523 */       while ((j < k) && (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + paramInt3 - j)]) <= 0)) {
/* 524 */         i = j;
/* 525 */         j = (j << 1) + 1;
/* 526 */         if (j <= 0)
/* 527 */           j = k;
/*     */       }
/* 529 */       if (j > k) {
/* 530 */         j = k;
/*     */       }
/*     */       
/* 533 */       int m = i;
/* 534 */       i = paramInt3 - j;
/* 535 */       j = paramInt3 - m;
/*     */     }
/* 537 */     assert ((-1 <= i) && (i < j) && (j <= paramInt2));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 544 */     i++;
/* 545 */     while (i < j) {
/* 546 */       k = i + (j - i >>> 1);
/*     */       
/* 548 */       if (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + k)]) > 0) {
/* 549 */         i = k + 1;
/*     */       } else
/* 551 */         j = k;
/*     */     }
/* 553 */     assert (i == j);
/* 554 */     return j;
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
/*     */   private static int gallopRight(Comparable<Object> paramComparable, Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 571 */     assert ((paramInt2 > 0) && (paramInt3 >= 0) && (paramInt3 < paramInt2));
/*     */     
/* 573 */     int i = 1;
/* 574 */     int j = 0;
/* 575 */     int k; if (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + paramInt3)]) < 0)
/*     */     {
/* 577 */       k = paramInt3 + 1;
/* 578 */       while ((i < k) && (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + paramInt3 - i)]) < 0)) {
/* 579 */         j = i;
/* 580 */         i = (i << 1) + 1;
/* 581 */         if (i <= 0)
/* 582 */           i = k;
/*     */       }
/* 584 */       if (i > k) {
/* 585 */         i = k;
/*     */       }
/*     */       
/* 588 */       int m = j;
/* 589 */       j = paramInt3 - i;
/* 590 */       i = paramInt3 - m;
/*     */     }
/*     */     else {
/* 593 */       k = paramInt2 - paramInt3;
/* 594 */       while ((i < k) && (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + paramInt3 + i)]) >= 0)) {
/* 595 */         j = i;
/* 596 */         i = (i << 1) + 1;
/* 597 */         if (i <= 0)
/* 598 */           i = k;
/*     */       }
/* 600 */       if (i > k) {
/* 601 */         i = k;
/*     */       }
/*     */       
/* 604 */       j += paramInt3;
/* 605 */       i += paramInt3;
/*     */     }
/* 607 */     assert ((-1 <= j) && (j < i) && (i <= paramInt2));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 614 */     j++;
/* 615 */     while (j < i) {
/* 616 */       k = j + (i - j >>> 1);
/*     */       
/* 618 */       if (paramComparable.compareTo(paramArrayOfObject[(paramInt1 + k)]) < 0) {
/* 619 */         i = k;
/*     */       } else
/* 621 */         j = k + 1;
/*     */     }
/* 623 */     assert (j == i);
/* 624 */     return i;
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
/*     */   private void mergeLo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 645 */     assert ((paramInt2 > 0) && (paramInt4 > 0) && (paramInt1 + paramInt2 == paramInt3));
/*     */     
/*     */ 
/* 648 */     Object[] arrayOfObject1 = this.a;
/* 649 */     Object[] arrayOfObject2 = ensureCapacity(paramInt2);
/*     */     
/* 651 */     int i = this.tmpBase;
/* 652 */     int j = paramInt3;
/* 653 */     int k = paramInt1;
/* 654 */     System.arraycopy(arrayOfObject1, paramInt1, arrayOfObject2, i, paramInt2);
/*     */     
/*     */ 
/* 657 */     arrayOfObject1[(k++)] = arrayOfObject1[(j++)];
/* 658 */     paramInt4--; if (paramInt4 == 0) {
/* 659 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, paramInt2);
/* 660 */       return;
/*     */     }
/* 662 */     if (paramInt2 == 1) {
/* 663 */       System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, paramInt4);
/* 664 */       arrayOfObject1[(k + paramInt4)] = arrayOfObject2[i];
/* 665 */       return;
/*     */     }
/*     */     
/* 668 */     int m = this.minGallop;
/*     */     for (;;)
/*     */     {
/* 671 */       int n = 0;
/* 672 */       int i1 = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 679 */         assert ((paramInt2 > 1) && (paramInt4 > 0));
/* 680 */         if (((Comparable)arrayOfObject1[j]).compareTo(arrayOfObject2[i]) < 0) {
/* 681 */           arrayOfObject1[(k++)] = arrayOfObject1[(j++)];
/* 682 */           i1++;
/* 683 */           n = 0;
/* 684 */           paramInt4--; if (paramInt4 == 0)
/*     */             break;
/*     */         } else {
/* 687 */           arrayOfObject1[(k++)] = arrayOfObject2[(i++)];
/* 688 */           n++;
/* 689 */           i1 = 0;
/* 690 */           paramInt2--; if (paramInt2 == 1)
/*     */             break;
/*     */         }
/* 693 */       } while ((n | i1) < m);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 701 */         assert ((paramInt2 > 1) && (paramInt4 > 0));
/* 702 */         n = gallopRight((Comparable)arrayOfObject1[j], arrayOfObject2, i, paramInt2, 0);
/* 703 */         if (n != 0) {
/* 704 */           System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, n);
/* 705 */           k += n;
/* 706 */           i += n;
/* 707 */           paramInt2 -= n;
/* 708 */           if (paramInt2 <= 1)
/*     */             break;
/*     */         }
/* 711 */         arrayOfObject1[(k++)] = arrayOfObject1[(j++)];
/* 712 */         paramInt4--; if (paramInt4 == 0) {
/*     */           break;
/*     */         }
/* 715 */         i1 = gallopLeft((Comparable)arrayOfObject2[i], arrayOfObject1, j, paramInt4, 0);
/* 716 */         if (i1 != 0) {
/* 717 */           System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, i1);
/* 718 */           k += i1;
/* 719 */           j += i1;
/* 720 */           paramInt4 -= i1;
/* 721 */           if (paramInt4 == 0)
/*     */             break;
/*     */         }
/* 724 */         arrayOfObject1[(k++)] = arrayOfObject2[(i++)];
/* 725 */         paramInt2--; if (paramInt2 == 1)
/*     */           break;
/* 727 */         m--;
/* 728 */       } while (((n >= 7 ? 1 : 0) | (i1 >= 7 ? 1 : 0)) != 0);
/* 729 */       if (m < 0)
/* 730 */         m = 0;
/* 731 */       m += 2;
/*     */     }
/* 733 */     this.minGallop = (m < 1 ? 1 : m);
/*     */     
/* 735 */     if (paramInt2 == 1) {
/* 736 */       assert (paramInt4 > 0);
/* 737 */       System.arraycopy(arrayOfObject1, j, arrayOfObject1, k, paramInt4);
/* 738 */       arrayOfObject1[(k + paramInt4)] = arrayOfObject2[i];
/* 739 */     } else { if (paramInt2 == 0) {
/* 740 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 743 */       assert (paramInt4 == 0);
/* 744 */       assert (paramInt2 > 1);
/* 745 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, k, paramInt2);
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
/*     */   private void mergeHi(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 762 */     assert ((paramInt2 > 0) && (paramInt4 > 0) && (paramInt1 + paramInt2 == paramInt3));
/*     */     
/*     */ 
/* 765 */     Object[] arrayOfObject1 = this.a;
/* 766 */     Object[] arrayOfObject2 = ensureCapacity(paramInt4);
/* 767 */     int i = this.tmpBase;
/* 768 */     System.arraycopy(arrayOfObject1, paramInt3, arrayOfObject2, i, paramInt4);
/*     */     
/* 770 */     int j = paramInt1 + paramInt2 - 1;
/* 771 */     int k = i + paramInt4 - 1;
/* 772 */     int m = paramInt3 + paramInt4 - 1;
/*     */     
/*     */ 
/* 775 */     arrayOfObject1[(m--)] = arrayOfObject1[(j--)];
/* 776 */     paramInt2--; if (paramInt2 == 0) {
/* 777 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, m - (paramInt4 - 1), paramInt4);
/* 778 */       return;
/*     */     }
/* 780 */     if (paramInt4 == 1) {
/* 781 */       m -= paramInt2;
/* 782 */       j -= paramInt2;
/* 783 */       System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, paramInt2);
/* 784 */       arrayOfObject1[m] = arrayOfObject2[k];
/* 785 */       return;
/*     */     }
/*     */     
/* 788 */     int n = this.minGallop;
/*     */     for (;;)
/*     */     {
/* 791 */       int i1 = 0;
/* 792 */       int i2 = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 799 */         assert ((paramInt2 > 0) && (paramInt4 > 1));
/* 800 */         if (((Comparable)arrayOfObject2[k]).compareTo(arrayOfObject1[j]) < 0) {
/* 801 */           arrayOfObject1[(m--)] = arrayOfObject1[(j--)];
/* 802 */           i1++;
/* 803 */           i2 = 0;
/* 804 */           paramInt2--; if (paramInt2 == 0)
/*     */             break;
/*     */         } else {
/* 807 */           arrayOfObject1[(m--)] = arrayOfObject2[(k--)];
/* 808 */           i2++;
/* 809 */           i1 = 0;
/* 810 */           paramInt4--; if (paramInt4 == 1)
/*     */             break;
/*     */         }
/* 813 */       } while ((i1 | i2) < n);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 821 */         assert ((paramInt2 > 0) && (paramInt4 > 1));
/* 822 */         i1 = paramInt2 - gallopRight((Comparable)arrayOfObject2[k], arrayOfObject1, paramInt1, paramInt2, paramInt2 - 1);
/* 823 */         if (i1 != 0) {
/* 824 */           m -= i1;
/* 825 */           j -= i1;
/* 826 */           paramInt2 -= i1;
/* 827 */           System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, i1);
/* 828 */           if (paramInt2 == 0)
/*     */             break;
/*     */         }
/* 831 */         arrayOfObject1[(m--)] = arrayOfObject2[(k--)];
/* 832 */         paramInt4--; if (paramInt4 == 1) {
/*     */           break;
/*     */         }
/* 835 */         i2 = paramInt4 - gallopLeft((Comparable)arrayOfObject1[j], arrayOfObject2, i, paramInt4, paramInt4 - 1);
/* 836 */         if (i2 != 0) {
/* 837 */           m -= i2;
/* 838 */           k -= i2;
/* 839 */           paramInt4 -= i2;
/* 840 */           System.arraycopy(arrayOfObject2, k + 1, arrayOfObject1, m + 1, i2);
/* 841 */           if (paramInt4 <= 1)
/*     */             break;
/*     */         }
/* 844 */         arrayOfObject1[(m--)] = arrayOfObject1[(j--)];
/* 845 */         paramInt2--; if (paramInt2 == 0)
/*     */           break;
/* 847 */         n--;
/* 848 */       } while (((i1 >= 7 ? 1 : 0) | (i2 >= 7 ? 1 : 0)) != 0);
/* 849 */       if (n < 0)
/* 850 */         n = 0;
/* 851 */       n += 2;
/*     */     }
/* 853 */     this.minGallop = (n < 1 ? 1 : n);
/*     */     
/* 855 */     if (paramInt4 == 1) {
/* 856 */       assert (paramInt2 > 0);
/* 857 */       m -= paramInt2;
/* 858 */       j -= paramInt2;
/* 859 */       System.arraycopy(arrayOfObject1, j + 1, arrayOfObject1, m + 1, paramInt2);
/* 860 */       arrayOfObject1[m] = arrayOfObject2[k];
/* 861 */     } else { if (paramInt4 == 0) {
/* 862 */         throw new IllegalArgumentException("Comparison method violates its general contract!");
/*     */       }
/*     */       
/* 865 */       assert (paramInt2 == 0);
/* 866 */       assert (paramInt4 > 0);
/* 867 */       System.arraycopy(arrayOfObject2, i, arrayOfObject1, m - (paramInt4 - 1), paramInt4);
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
/*     */   private Object[] ensureCapacity(int paramInt)
/*     */   {
/* 880 */     if (this.tmpLen < paramInt)
/*     */     {
/* 882 */       int i = paramInt;
/* 883 */       i |= i >> 1;
/* 884 */       i |= i >> 2;
/* 885 */       i |= i >> 4;
/* 886 */       i |= i >> 8;
/* 887 */       i |= i >> 16;
/* 888 */       i++;
/*     */       
/* 890 */       if (i < 0) {
/* 891 */         i = paramInt;
/*     */       } else {
/* 893 */         i = Math.min(i, this.a.length >>> 1);
/*     */       }
/*     */       
/* 896 */       Object[] arrayOfObject = new Object[i];
/* 897 */       this.tmp = arrayOfObject;
/* 898 */       this.tmpLen = i;
/* 899 */       this.tmpBase = 0;
/*     */     }
/* 901 */     return this.tmp;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ComparableTimSort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */