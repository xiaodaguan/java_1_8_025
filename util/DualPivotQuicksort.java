/*      */ package java.util;
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
/*      */ final class DualPivotQuicksort
/*      */ {
/*      */   private static final int MAX_RUN_COUNT = 67;
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
/*      */   private static final int MAX_RUN_LENGTH = 33;
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
/*      */   private static final int QUICKSORT_THRESHOLD = 286;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int INSERTION_SORT_THRESHOLD = 47;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int COUNTING_SORT_THRESHOLD_FOR_BYTE = 29;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int COUNTING_SORT_THRESHOLD_FOR_SHORT_OR_CHAR = 3200;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int NUM_SHORT_VALUES = 65536;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int NUM_CHAR_VALUES = 65536;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int NUM_BYTE_VALUES = 256;
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
/*      */   static void sort(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4)
/*      */   {
/*  110 */     if (paramInt2 - paramInt1 < 286) {
/*  111 */       sort(paramArrayOfInt1, paramInt1, paramInt2, true);
/*  112 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  119 */     int[] arrayOfInt1 = new int[68];
/*  120 */     int i = 0;arrayOfInt1[0] = paramInt1;
/*      */     int m;
/*      */     int n;
/*  123 */     for (int j = paramInt1; j < paramInt2; arrayOfInt1[i] = j) {
/*  124 */       if (paramArrayOfInt1[j] < paramArrayOfInt1[(j + 1)])
/*  125 */         for (;;) { j++; if ((j > paramInt2) || (paramArrayOfInt1[(j - 1)] > paramArrayOfInt1[j])) break; }
/*  126 */       if (paramArrayOfInt1[j] > paramArrayOfInt1[(j + 1)]) {
/*  127 */         do { j++; } while ((j <= paramInt2) && (paramArrayOfInt1[(j - 1)] >= paramArrayOfInt1[j]));
/*  128 */         k = arrayOfInt1[i] - 1;m = j; for (;;) { k++; if (k >= --m) break;
/*  129 */           n = paramArrayOfInt1[k];paramArrayOfInt1[k] = paramArrayOfInt1[m];paramArrayOfInt1[m] = n;
/*      */         }
/*      */       } else {
/*  132 */         k = 33; do { j++; if ((j > paramInt2) || (paramArrayOfInt1[(j - 1)] != paramArrayOfInt1[j])) break;
/*  133 */           k--; } while (k != 0);
/*  134 */         sort(paramArrayOfInt1, paramInt1, paramInt2, true);
/*  135 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  144 */       i++; if (i == 67) {
/*  145 */         sort(paramArrayOfInt1, paramInt1, paramInt2, true);
/*  146 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  152 */     if (arrayOfInt1[i] == paramInt2++) {
/*  153 */       arrayOfInt1[(++i)] = paramInt2;
/*  154 */     } else if (i == 1) {
/*  155 */       return;
/*      */     }
/*      */     
/*      */ 
/*  159 */     j = 0;
/*  160 */     for (int k = 1; k <<= 1 < i; j = (byte)(j ^ 0x1)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  165 */     int i1 = paramInt2 - paramInt1;
/*  166 */     if ((paramArrayOfInt2 == null) || (paramInt4 < i1) || (paramInt3 + i1 > paramArrayOfInt2.length)) {
/*  167 */       paramArrayOfInt2 = new int[i1];
/*  168 */       paramInt3 = 0; }
/*      */     Object localObject;
/*  170 */     if (j == 0) {
/*  171 */       System.arraycopy(paramArrayOfInt1, paramInt1, paramArrayOfInt2, paramInt3, i1);
/*  172 */       localObject = paramArrayOfInt1;
/*  173 */       n = 0;
/*  174 */       paramArrayOfInt1 = paramArrayOfInt2;
/*  175 */       m = paramInt3 - paramInt1;
/*      */     } else {
/*  177 */       localObject = paramArrayOfInt2;
/*  178 */       m = 0;
/*  179 */       n = paramInt3 - paramInt1;
/*      */     }
/*      */     int i2;
/*  183 */     for (; 
/*  183 */         i > 1; i = i2) {
/*  184 */       for (int i3 = (i2 = 0) + 2; i3 <= i; i3 += 2) {
/*  185 */         i4 = arrayOfInt1[i3];int i5 = arrayOfInt1[(i3 - 1)];
/*  186 */         int i6 = arrayOfInt1[(i3 - 2)];int i7 = i6; for (int i8 = i5; i6 < i4; i6++) {
/*  187 */           if ((i8 >= i4) || ((i7 < i5) && (paramArrayOfInt1[(i7 + m)] <= paramArrayOfInt1[(i8 + m)]))) {
/*  188 */             localObject[(i6 + n)] = paramArrayOfInt1[(i7++ + m)];
/*      */           } else {
/*  190 */             localObject[(i6 + n)] = paramArrayOfInt1[(i8++ + m)];
/*      */           }
/*      */         }
/*  193 */         arrayOfInt1[(++i2)] = i4;
/*      */       }
/*  195 */       if ((i & 0x1) != 0) {
/*  196 */         i3 = paramInt2;i4 = arrayOfInt1[(i - 1)]; for (;;) { i3--; if (i3 < i4) break;
/*  197 */           localObject[(i3 + n)] = paramArrayOfInt1[(i3 + m)];
/*      */         }
/*  199 */         arrayOfInt1[(++i2)] = paramInt2;
/*      */       }
/*  201 */       int[] arrayOfInt2 = paramArrayOfInt1;paramArrayOfInt1 = (int[])localObject;localObject = arrayOfInt2;
/*  202 */       int i4 = m;m = n;n = i4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void sort(int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/*  215 */     int i = paramInt2 - paramInt1 + 1;
/*      */     
/*      */ 
/*  218 */     if (i < 47) {
/*  219 */       if (paramBoolean)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  225 */         j = paramInt1; for (k = j; j < paramInt2; k = j) {
/*  226 */           m = paramArrayOfInt[(j + 1)];
/*  227 */           while (m < paramArrayOfInt[k]) {
/*  228 */             paramArrayOfInt[(k + 1)] = paramArrayOfInt[k];
/*  229 */             if (k-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           }
/*  233 */           paramArrayOfInt[(k + 1)] = m;j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/*  240 */           if (paramInt1 >= paramInt2) {
/*  241 */             return;
/*      */           }
/*  243 */         } while (paramArrayOfInt[(++paramInt1)] >= paramArrayOfInt[(paramInt1 - 1)]);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  253 */         for (j = paramInt1;; j = paramInt1) { paramInt1++; if (paramInt1 > paramInt2) break;
/*  254 */           k = paramArrayOfInt[j];m = paramArrayOfInt[paramInt1];
/*      */           
/*  256 */           if (k < m) {
/*  257 */             m = k;k = paramArrayOfInt[paramInt1];
/*      */           }
/*  259 */           while (k < paramArrayOfInt[(--j)]) {
/*  260 */             paramArrayOfInt[(j + 2)] = paramArrayOfInt[j];
/*      */           }
/*  262 */           paramArrayOfInt[(++j + 1)] = k;
/*      */           
/*  264 */           while (m < paramArrayOfInt[(--j)]) {
/*  265 */             paramArrayOfInt[(j + 1)] = paramArrayOfInt[j];
/*      */           }
/*  267 */           paramArrayOfInt[(j + 1)] = m;paramInt1++;
/*      */         }
/*  269 */         j = paramArrayOfInt[paramInt2];
/*      */         
/*  271 */         while (j < paramArrayOfInt[(--paramInt2)]) {
/*  272 */           paramArrayOfInt[(paramInt2 + 1)] = paramArrayOfInt[paramInt2];
/*      */         }
/*  274 */         paramArrayOfInt[(paramInt2 + 1)] = j;
/*      */       }
/*  276 */       return;
/*      */     }
/*      */     
/*      */ 
/*  280 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  289 */     int k = paramInt1 + paramInt2 >>> 1;
/*  290 */     int m = k - j;
/*  291 */     int n = m - j;
/*  292 */     int i1 = k + j;
/*  293 */     int i2 = i1 + j;
/*      */     
/*      */ 
/*  296 */     if (paramArrayOfInt[m] < paramArrayOfInt[n]) { i3 = paramArrayOfInt[m];paramArrayOfInt[m] = paramArrayOfInt[n];paramArrayOfInt[n] = i3;
/*      */     }
/*  298 */     if (paramArrayOfInt[k] < paramArrayOfInt[m]) { i3 = paramArrayOfInt[k];paramArrayOfInt[k] = paramArrayOfInt[m];paramArrayOfInt[m] = i3;
/*  299 */       if (i3 < paramArrayOfInt[n]) { paramArrayOfInt[m] = paramArrayOfInt[n];paramArrayOfInt[n] = i3;
/*      */       } }
/*  301 */     if (paramArrayOfInt[i1] < paramArrayOfInt[k]) { i3 = paramArrayOfInt[i1];paramArrayOfInt[i1] = paramArrayOfInt[k];paramArrayOfInt[k] = i3;
/*  302 */       if (i3 < paramArrayOfInt[m]) { paramArrayOfInt[k] = paramArrayOfInt[m];paramArrayOfInt[m] = i3;
/*  303 */         if (i3 < paramArrayOfInt[n]) { paramArrayOfInt[m] = paramArrayOfInt[n];paramArrayOfInt[n] = i3;
/*      */         }
/*      */       } }
/*  306 */     if (paramArrayOfInt[i2] < paramArrayOfInt[i1]) { i3 = paramArrayOfInt[i2];paramArrayOfInt[i2] = paramArrayOfInt[i1];paramArrayOfInt[i1] = i3;
/*  307 */       if (i3 < paramArrayOfInt[k]) { paramArrayOfInt[i1] = paramArrayOfInt[k];paramArrayOfInt[k] = i3;
/*  308 */         if (i3 < paramArrayOfInt[m]) { paramArrayOfInt[k] = paramArrayOfInt[m];paramArrayOfInt[m] = i3;
/*  309 */           if (i3 < paramArrayOfInt[n]) { paramArrayOfInt[m] = paramArrayOfInt[n];paramArrayOfInt[n] = i3;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  315 */     int i3 = paramInt1;
/*  316 */     int i4 = paramInt2;
/*      */     int i5;
/*  318 */     int i6; int i7; if ((paramArrayOfInt[n] != paramArrayOfInt[m]) && (paramArrayOfInt[m] != paramArrayOfInt[k]) && (paramArrayOfInt[k] != paramArrayOfInt[i1]) && (paramArrayOfInt[i1] != paramArrayOfInt[i2]))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  324 */       i5 = paramArrayOfInt[m];
/*  325 */       i6 = paramArrayOfInt[i1];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  333 */       paramArrayOfInt[m] = paramArrayOfInt[paramInt1];
/*  334 */       paramArrayOfInt[i1] = paramArrayOfInt[paramInt2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  339 */       while (paramArrayOfInt[(++i3)] < i5) {}
/*  340 */       while (paramArrayOfInt[(--i4)] > i6) {}
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
/*  362 */       i7 = i3 - 1; int i8; for (;;) { i7++; if (i7 > i4) break;
/*  363 */         i8 = paramArrayOfInt[i7];
/*  364 */         if (i8 < i5) {
/*  365 */           paramArrayOfInt[i7] = paramArrayOfInt[i3];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  370 */           paramArrayOfInt[i3] = i8;
/*  371 */           i3++;
/*  372 */         } else if (i8 > i6) {
/*  373 */           while (paramArrayOfInt[i4] > i6) {
/*  374 */             if (i4-- == i7) {
/*      */               break label808;
/*      */             }
/*      */           }
/*  378 */           if (paramArrayOfInt[i4] < i5) {
/*  379 */             paramArrayOfInt[i7] = paramArrayOfInt[i3];
/*  380 */             paramArrayOfInt[i3] = paramArrayOfInt[i4];
/*  381 */             i3++;
/*      */           } else {
/*  383 */             paramArrayOfInt[i7] = paramArrayOfInt[i4];
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  389 */           paramArrayOfInt[i4] = i8;
/*  390 */           i4--;
/*      */         }
/*      */       }
/*      */       
/*      */       label808:
/*  395 */       paramArrayOfInt[paramInt1] = paramArrayOfInt[(i3 - 1)]; paramArrayOfInt[(i3 - 1)] = i5;
/*  396 */       paramArrayOfInt[paramInt2] = paramArrayOfInt[(i4 + 1)];paramArrayOfInt[(i4 + 1)] = i6;
/*      */       
/*      */ 
/*  399 */       sort(paramArrayOfInt, paramInt1, i3 - 2, paramBoolean);
/*  400 */       sort(paramArrayOfInt, i4 + 2, paramInt2, false);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  406 */       if ((i3 < n) && (i2 < i4))
/*      */       {
/*      */ 
/*      */ 
/*  410 */         while (paramArrayOfInt[i3] == i5) {
/*  411 */           i3++;
/*      */         }
/*      */         
/*  414 */         while (paramArrayOfInt[i4] == i6) {
/*  415 */           i4--;
/*      */         }
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
/*  438 */         i7 = i3 - 1; for (;;) { i7++; if (i7 > i4) break;
/*  439 */           i8 = paramArrayOfInt[i7];
/*  440 */           if (i8 == i5) {
/*  441 */             paramArrayOfInt[i7] = paramArrayOfInt[i3];
/*  442 */             paramArrayOfInt[i3] = i8;
/*  443 */             i3++;
/*  444 */           } else if (i8 == i6) {
/*  445 */             while (paramArrayOfInt[i4] == i6) {
/*  446 */               if (i4-- == i7) {
/*      */                 break label1033;
/*      */               }
/*      */             }
/*  450 */             if (paramArrayOfInt[i4] == i5) {
/*  451 */               paramArrayOfInt[i7] = paramArrayOfInt[i3];
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  460 */               paramArrayOfInt[i3] = i5;
/*  461 */               i3++;
/*      */             } else {
/*  463 */               paramArrayOfInt[i7] = paramArrayOfInt[i4];
/*      */             }
/*  465 */             paramArrayOfInt[i4] = i8;
/*  466 */             i4--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       label1033:
/*  472 */       sort(paramArrayOfInt, i3, i4, false);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  479 */       i5 = paramArrayOfInt[k];
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
/*  501 */       for (i6 = i3; i6 <= i4; i6++) {
/*  502 */         if (paramArrayOfInt[i6] != i5)
/*      */         {
/*      */ 
/*  505 */           i7 = paramArrayOfInt[i6];
/*  506 */           if (i7 < i5) {
/*  507 */             paramArrayOfInt[i6] = paramArrayOfInt[i3];
/*  508 */             paramArrayOfInt[i3] = i7;
/*  509 */             i3++;
/*      */           } else {
/*  511 */             while (paramArrayOfInt[i4] > i5) {
/*  512 */               i4--;
/*      */             }
/*  514 */             if (paramArrayOfInt[i4] < i5) {
/*  515 */               paramArrayOfInt[i6] = paramArrayOfInt[i3];
/*  516 */               paramArrayOfInt[i3] = paramArrayOfInt[i4];
/*  517 */               i3++;
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  527 */               paramArrayOfInt[i6] = i5;
/*      */             }
/*  529 */             paramArrayOfInt[i4] = i7;
/*  530 */             i4--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  539 */       sort(paramArrayOfInt, paramInt1, i3 - 1, paramBoolean);
/*  540 */       sort(paramArrayOfInt, i4 + 1, paramInt2, false);
/*      */     }
/*      */   }
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
/*      */   static void sort(long[] paramArrayOfLong1, int paramInt1, int paramInt2, long[] paramArrayOfLong2, int paramInt3, int paramInt4)
/*      */   {
/*  558 */     if (paramInt2 - paramInt1 < 286) {
/*  559 */       sort(paramArrayOfLong1, paramInt1, paramInt2, true);
/*  560 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  567 */     int[] arrayOfInt = new int[68];
/*  568 */     int i = 0;arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int m;
/*  571 */     for (int j = paramInt1; j < paramInt2; arrayOfInt[i] = j) {
/*  572 */       if (paramArrayOfLong1[j] < paramArrayOfLong1[(j + 1)])
/*  573 */         for (;;) { j++; if ((j > paramInt2) || (paramArrayOfLong1[(j - 1)] > paramArrayOfLong1[j])) break; }
/*  574 */       if (paramArrayOfLong1[j] > paramArrayOfLong1[(j + 1)]) {
/*  575 */         do { j++; } while ((j <= paramInt2) && (paramArrayOfLong1[(j - 1)] >= paramArrayOfLong1[j]));
/*  576 */         k = arrayOfInt[i] - 1;m = j; for (;;) { k++; if (k >= --m) break;
/*  577 */           long l = paramArrayOfLong1[k];paramArrayOfLong1[k] = paramArrayOfLong1[m];paramArrayOfLong1[m] = l;
/*      */         }
/*      */       } else {
/*  580 */         k = 33; do { j++; if ((j > paramInt2) || (paramArrayOfLong1[(j - 1)] != paramArrayOfLong1[j])) break;
/*  581 */           k--; } while (k != 0);
/*  582 */         sort(paramArrayOfLong1, paramInt1, paramInt2, true);
/*  583 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  592 */       i++; if (i == 67) {
/*  593 */         sort(paramArrayOfLong1, paramInt1, paramInt2, true);
/*  594 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  600 */     if (arrayOfInt[i] == paramInt2++) {
/*  601 */       arrayOfInt[(++i)] = paramInt2;
/*  602 */     } else if (i == 1) {
/*  603 */       return;
/*      */     }
/*      */     
/*      */ 
/*  607 */     j = 0;
/*  608 */     for (int k = 1; k <<= 1 < i; j = (byte)(j ^ 0x1)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  613 */     int i1 = paramInt2 - paramInt1;
/*  614 */     if ((paramArrayOfLong2 == null) || (paramInt4 < i1) || (paramInt3 + i1 > paramArrayOfLong2.length)) {
/*  615 */       paramArrayOfLong2 = new long[i1];
/*  616 */       paramInt3 = 0; }
/*      */     Object localObject;
/*  618 */     int n; if (j == 0) {
/*  619 */       System.arraycopy(paramArrayOfLong1, paramInt1, paramArrayOfLong2, paramInt3, i1);
/*  620 */       localObject = paramArrayOfLong1;
/*  621 */       n = 0;
/*  622 */       paramArrayOfLong1 = paramArrayOfLong2;
/*  623 */       m = paramInt3 - paramInt1;
/*      */     } else {
/*  625 */       localObject = paramArrayOfLong2;
/*  626 */       m = 0;
/*  627 */       n = paramInt3 - paramInt1;
/*      */     }
/*      */     int i2;
/*  631 */     for (; 
/*  631 */         i > 1; i = i2) {
/*  632 */       for (int i3 = (i2 = 0) + 2; i3 <= i; i3 += 2) {
/*  633 */         i4 = arrayOfInt[i3];int i5 = arrayOfInt[(i3 - 1)];
/*  634 */         int i6 = arrayOfInt[(i3 - 2)];int i7 = i6; for (int i8 = i5; i6 < i4; i6++) {
/*  635 */           if ((i8 >= i4) || ((i7 < i5) && (paramArrayOfLong1[(i7 + m)] <= paramArrayOfLong1[(i8 + m)]))) {
/*  636 */             localObject[(i6 + n)] = paramArrayOfLong1[(i7++ + m)];
/*      */           } else {
/*  638 */             localObject[(i6 + n)] = paramArrayOfLong1[(i8++ + m)];
/*      */           }
/*      */         }
/*  641 */         arrayOfInt[(++i2)] = i4;
/*      */       }
/*  643 */       if ((i & 0x1) != 0) {
/*  644 */         i3 = paramInt2;i4 = arrayOfInt[(i - 1)]; for (;;) { i3--; if (i3 < i4) break;
/*  645 */           localObject[(i3 + n)] = paramArrayOfLong1[(i3 + m)];
/*      */         }
/*  647 */         arrayOfInt[(++i2)] = paramInt2;
/*      */       }
/*  649 */       long[] arrayOfLong = paramArrayOfLong1;paramArrayOfLong1 = (long[])localObject;localObject = arrayOfLong;
/*  650 */       int i4 = m;m = n;n = i4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void sort(long[] paramArrayOfLong, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/*  663 */     int i = paramInt2 - paramInt1 + 1;
/*      */     
/*      */ 
/*  666 */     if (i < 47) { int j;
/*  667 */       if (paramBoolean)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  673 */         j = paramInt1; for (int m = j; j < paramInt2; m = j) {
/*  674 */           long l3 = paramArrayOfLong[(j + 1)];
/*  675 */           while (l3 < paramArrayOfLong[m]) {
/*  676 */             paramArrayOfLong[(m + 1)] = paramArrayOfLong[m];
/*  677 */             if (m-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           }
/*  681 */           paramArrayOfLong[(m + 1)] = l3;j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/*  688 */           if (paramInt1 >= paramInt2) {
/*  689 */             return;
/*      */           }
/*  691 */         } while (paramArrayOfLong[(++paramInt1)] >= paramArrayOfLong[(paramInt1 - 1)]);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  701 */         for (j = paramInt1;; j = paramInt1) { paramInt1++; if (paramInt1 > paramInt2) break;
/*  702 */           long l2 = paramArrayOfLong[j];long l4 = paramArrayOfLong[paramInt1];
/*      */           
/*  704 */           if (l2 < l4) {
/*  705 */             l4 = l2;l2 = paramArrayOfLong[paramInt1];
/*      */           }
/*  707 */           while (l2 < paramArrayOfLong[(--j)]) {
/*  708 */             paramArrayOfLong[(j + 2)] = paramArrayOfLong[j];
/*      */           }
/*  710 */           paramArrayOfLong[(++j + 1)] = l2;
/*      */           
/*  712 */           while (l4 < paramArrayOfLong[(--j)]) {
/*  713 */             paramArrayOfLong[(j + 1)] = paramArrayOfLong[j];
/*      */           }
/*  715 */           paramArrayOfLong[(j + 1)] = l4;paramInt1++;
/*      */         }
/*  717 */         long l1 = paramArrayOfLong[paramInt2];
/*      */         
/*  719 */         while (l1 < paramArrayOfLong[(--paramInt2)]) {
/*  720 */           paramArrayOfLong[(paramInt2 + 1)] = paramArrayOfLong[paramInt2];
/*      */         }
/*  722 */         paramArrayOfLong[(paramInt2 + 1)] = l1;
/*      */       }
/*  724 */       return;
/*      */     }
/*      */     
/*      */ 
/*  728 */     int k = (i >> 3) + (i >> 6) + 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  737 */     int n = paramInt1 + paramInt2 >>> 1;
/*  738 */     int i1 = n - k;
/*  739 */     int i2 = i1 - k;
/*  740 */     int i3 = n + k;
/*  741 */     int i4 = i3 + k;
/*      */     
/*      */     long l5;
/*  744 */     if (paramArrayOfLong[i1] < paramArrayOfLong[i2]) { l5 = paramArrayOfLong[i1];paramArrayOfLong[i1] = paramArrayOfLong[i2];paramArrayOfLong[i2] = l5;
/*      */     }
/*  746 */     if (paramArrayOfLong[n] < paramArrayOfLong[i1]) { l5 = paramArrayOfLong[n];paramArrayOfLong[n] = paramArrayOfLong[i1];paramArrayOfLong[i1] = l5;
/*  747 */       if (l5 < paramArrayOfLong[i2]) { paramArrayOfLong[i1] = paramArrayOfLong[i2];paramArrayOfLong[i2] = l5;
/*      */       } }
/*  749 */     if (paramArrayOfLong[i3] < paramArrayOfLong[n]) { l5 = paramArrayOfLong[i3];paramArrayOfLong[i3] = paramArrayOfLong[n];paramArrayOfLong[n] = l5;
/*  750 */       if (l5 < paramArrayOfLong[i1]) { paramArrayOfLong[n] = paramArrayOfLong[i1];paramArrayOfLong[i1] = l5;
/*  751 */         if (l5 < paramArrayOfLong[i2]) { paramArrayOfLong[i1] = paramArrayOfLong[i2];paramArrayOfLong[i2] = l5;
/*      */         }
/*      */       } }
/*  754 */     if (paramArrayOfLong[i4] < paramArrayOfLong[i3]) { l5 = paramArrayOfLong[i4];paramArrayOfLong[i4] = paramArrayOfLong[i3];paramArrayOfLong[i3] = l5;
/*  755 */       if (l5 < paramArrayOfLong[n]) { paramArrayOfLong[i3] = paramArrayOfLong[n];paramArrayOfLong[n] = l5;
/*  756 */         if (l5 < paramArrayOfLong[i1]) { paramArrayOfLong[n] = paramArrayOfLong[i1];paramArrayOfLong[i1] = l5;
/*  757 */           if (l5 < paramArrayOfLong[i2]) { paramArrayOfLong[i1] = paramArrayOfLong[i2];paramArrayOfLong[i2] = l5;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  763 */     long l6 = paramInt1;
/*  764 */     long l7 = paramInt2;
/*      */     long l8;
/*  766 */     long l9; if ((paramArrayOfLong[i2] != paramArrayOfLong[i1]) && (paramArrayOfLong[i1] != paramArrayOfLong[n]) && (paramArrayOfLong[n] != paramArrayOfLong[i3]) && (paramArrayOfLong[i3] != paramArrayOfLong[i4]))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  772 */       l8 = paramArrayOfLong[i1];
/*  773 */       l9 = paramArrayOfLong[i3];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  781 */       paramArrayOfLong[i1] = paramArrayOfLong[paramInt1];
/*  782 */       paramArrayOfLong[i3] = paramArrayOfLong[paramInt2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  787 */       while (paramArrayOfLong[(++l6)] < l8) {}
/*  788 */       while (paramArrayOfLong[(--l7)] > l9) {}
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
/*  810 */       long l11 = l6 - 1; long l13; for (;;) { l11++; if (l11 > l7) break;
/*  811 */         l13 = paramArrayOfLong[l11];
/*  812 */         if (l13 < l8) {
/*  813 */           paramArrayOfLong[l11] = paramArrayOfLong[l6];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  818 */           paramArrayOfLong[l6] = l13;
/*  819 */           l6++;
/*  820 */         } else if (l13 > l9) {
/*  821 */           while (paramArrayOfLong[l7] > l9) {
/*  822 */             if (l7-- == l11) {
/*      */               break label834;
/*      */             }
/*      */           }
/*  826 */           if (paramArrayOfLong[l7] < l8) {
/*  827 */             paramArrayOfLong[l11] = paramArrayOfLong[l6];
/*  828 */             paramArrayOfLong[l6] = paramArrayOfLong[l7];
/*  829 */             l6++;
/*      */           } else {
/*  831 */             paramArrayOfLong[l11] = paramArrayOfLong[l7];
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  837 */           paramArrayOfLong[l7] = l13;
/*  838 */           l7--;
/*      */         }
/*      */       }
/*      */       
/*      */       label834:
/*  843 */       paramArrayOfLong[paramInt1] = paramArrayOfLong[(l6 - 1)]; paramArrayOfLong[(l6 - 1)] = l8;
/*  844 */       paramArrayOfLong[paramInt2] = paramArrayOfLong[(l7 + 1)];paramArrayOfLong[(l7 + 1)] = l9;
/*      */       
/*      */ 
/*  847 */       sort(paramArrayOfLong, paramInt1, l6 - 2, paramBoolean);
/*  848 */       sort(paramArrayOfLong, l7 + 2, paramInt2, false);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  854 */       if ((l6 < i2) && (i4 < l7))
/*      */       {
/*      */ 
/*      */ 
/*  858 */         while (paramArrayOfLong[l6] == l8) {
/*  859 */           l6++;
/*      */         }
/*      */         
/*  862 */         while (paramArrayOfLong[l7] == l9) {
/*  863 */           l7--;
/*      */         }
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
/*  886 */         long l12 = l6 - 1; for (;;) { l12++; if (l12 > l7) break;
/*  887 */           l13 = paramArrayOfLong[l12];
/*  888 */           if (l13 == l8) {
/*  889 */             paramArrayOfLong[l12] = paramArrayOfLong[l6];
/*  890 */             paramArrayOfLong[l6] = l13;
/*  891 */             l6++;
/*  892 */           } else if (l13 == l9) {
/*  893 */             while (paramArrayOfLong[l7] == l9) {
/*  894 */               if (l7-- == l12) {
/*      */                 break label1065;
/*      */               }
/*      */             }
/*  898 */             if (paramArrayOfLong[l7] == l8) {
/*  899 */               paramArrayOfLong[l12] = paramArrayOfLong[l6];
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  908 */               paramArrayOfLong[l6] = l8;
/*  909 */               l6++;
/*      */             } else {
/*  911 */               paramArrayOfLong[l12] = paramArrayOfLong[l7];
/*      */             }
/*  913 */             paramArrayOfLong[l7] = l13;
/*  914 */             l7--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       label1065:
/*  920 */       sort(paramArrayOfLong, l6, l7, false);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  927 */       l8 = paramArrayOfLong[n];
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
/*  949 */       for (l9 = l6; l9 <= l7; l9++) {
/*  950 */         if (paramArrayOfLong[l9] != l8)
/*      */         {
/*      */ 
/*  953 */           long l10 = paramArrayOfLong[l9];
/*  954 */           if (l10 < l8) {
/*  955 */             paramArrayOfLong[l9] = paramArrayOfLong[l6];
/*  956 */             paramArrayOfLong[l6] = l10;
/*  957 */             l6++;
/*      */           } else {
/*  959 */             while (paramArrayOfLong[l7] > l8) {
/*  960 */               l7--;
/*      */             }
/*  962 */             if (paramArrayOfLong[l7] < l8) {
/*  963 */               paramArrayOfLong[l9] = paramArrayOfLong[l6];
/*  964 */               paramArrayOfLong[l6] = paramArrayOfLong[l7];
/*  965 */               l6++;
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  975 */               paramArrayOfLong[l9] = l8;
/*      */             }
/*  977 */             paramArrayOfLong[l7] = l10;
/*  978 */             l7--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */       sort(paramArrayOfLong, paramInt1, l6 - 1, paramBoolean);
/*  988 */       sort(paramArrayOfLong, l7 + 1, paramInt2, false);
/*      */     }
/*      */   }
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
/*      */   static void sort(short[] paramArrayOfShort1, int paramInt1, int paramInt2, short[] paramArrayOfShort2, int paramInt3, int paramInt4)
/*      */   {
/* 1006 */     if (paramInt2 - paramInt1 > 3200) {
/* 1007 */       int[] arrayOfInt = new int[65536];
/*      */       
/* 1009 */       int i = paramInt1 - 1; for (;;) { i++; if (i > paramInt2) break;
/* 1010 */         arrayOfInt[(paramArrayOfShort1[i] - Short.MIN_VALUE)] += 1;
/*      */       }
/* 1012 */       i = 65536; int m; for (int j = paramInt2 + 1; j > paramInt1; 
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1019 */           m > 0)
/*      */       {
/* 1013 */         while (arrayOfInt[(--i)] == 0) {}
/* 1014 */         int k = (short)(i + 32768);
/* 1015 */         m = arrayOfInt[i];
/*      */         
/*      */ 
/* 1018 */         paramArrayOfShort1[(--j)] = k;
/* 1019 */         m--;
/*      */       }
/*      */     } else {
/* 1022 */       doSort(paramArrayOfShort1, paramInt1, paramInt2, paramArrayOfShort2, paramInt3, paramInt4);
/*      */     }
/*      */   }
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
/*      */   private static void doSort(short[] paramArrayOfShort1, int paramInt1, int paramInt2, short[] paramArrayOfShort2, int paramInt3, int paramInt4)
/*      */   {
/* 1042 */     if (paramInt2 - paramInt1 < 286) {
/* 1043 */       sort(paramArrayOfShort1, paramInt1, paramInt2, true);
/* 1044 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1051 */     int[] arrayOfInt = new int[68];
/* 1052 */     int i = 0;arrayOfInt[0] = paramInt1;
/*      */     int m;
/*      */     int n;
/* 1055 */     for (int j = paramInt1; j < paramInt2; arrayOfInt[i] = j) {
/* 1056 */       if (paramArrayOfShort1[j] < paramArrayOfShort1[(j + 1)])
/* 1057 */         for (;;) { j++; if ((j > paramInt2) || (paramArrayOfShort1[(j - 1)] > paramArrayOfShort1[j])) break; }
/* 1058 */       if (paramArrayOfShort1[j] > paramArrayOfShort1[(j + 1)]) {
/* 1059 */         do { j++; } while ((j <= paramInt2) && (paramArrayOfShort1[(j - 1)] >= paramArrayOfShort1[j]));
/* 1060 */         k = arrayOfInt[i] - 1;m = j; for (;;) { k++; if (k >= --m) break;
/* 1061 */           n = paramArrayOfShort1[k];paramArrayOfShort1[k] = paramArrayOfShort1[m];paramArrayOfShort1[m] = n;
/*      */         }
/*      */       } else {
/* 1064 */         k = 33; do { j++; if ((j > paramInt2) || (paramArrayOfShort1[(j - 1)] != paramArrayOfShort1[j])) break;
/* 1065 */           k--; } while (k != 0);
/* 1066 */         sort(paramArrayOfShort1, paramInt1, paramInt2, true);
/* 1067 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1076 */       i++; if (i == 67) {
/* 1077 */         sort(paramArrayOfShort1, paramInt1, paramInt2, true);
/* 1078 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1084 */     if (arrayOfInt[i] == paramInt2++) {
/* 1085 */       arrayOfInt[(++i)] = paramInt2;
/* 1086 */     } else if (i == 1) {
/* 1087 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1091 */     j = 0;
/* 1092 */     for (int k = 1; k <<= 1 < i; j = (byte)(j ^ 0x1)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1097 */     int i1 = paramInt2 - paramInt1;
/* 1098 */     if ((paramArrayOfShort2 == null) || (paramInt4 < i1) || (paramInt3 + i1 > paramArrayOfShort2.length)) {
/* 1099 */       paramArrayOfShort2 = new short[i1];
/* 1100 */       paramInt3 = 0; }
/*      */     Object localObject;
/* 1102 */     if (j == 0) {
/* 1103 */       System.arraycopy(paramArrayOfShort1, paramInt1, paramArrayOfShort2, paramInt3, i1);
/* 1104 */       localObject = paramArrayOfShort1;
/* 1105 */       n = 0;
/* 1106 */       paramArrayOfShort1 = paramArrayOfShort2;
/* 1107 */       m = paramInt3 - paramInt1;
/*      */     } else {
/* 1109 */       localObject = paramArrayOfShort2;
/* 1110 */       m = 0;
/* 1111 */       n = paramInt3 - paramInt1;
/*      */     }
/*      */     int i2;
/* 1115 */     for (; 
/* 1115 */         i > 1; i = i2) {
/* 1116 */       for (int i3 = (i2 = 0) + 2; i3 <= i; i3 += 2) {
/* 1117 */         i4 = arrayOfInt[i3];int i5 = arrayOfInt[(i3 - 1)];
/* 1118 */         int i6 = arrayOfInt[(i3 - 2)];int i7 = i6; for (int i8 = i5; i6 < i4; i6++) {
/* 1119 */           if ((i8 >= i4) || ((i7 < i5) && (paramArrayOfShort1[(i7 + m)] <= paramArrayOfShort1[(i8 + m)]))) {
/* 1120 */             localObject[(i6 + n)] = paramArrayOfShort1[(i7++ + m)];
/*      */           } else {
/* 1122 */             localObject[(i6 + n)] = paramArrayOfShort1[(i8++ + m)];
/*      */           }
/*      */         }
/* 1125 */         arrayOfInt[(++i2)] = i4;
/*      */       }
/* 1127 */       if ((i & 0x1) != 0) {
/* 1128 */         i3 = paramInt2;i4 = arrayOfInt[(i - 1)]; for (;;) { i3--; if (i3 < i4) break;
/* 1129 */           localObject[(i3 + n)] = paramArrayOfShort1[(i3 + m)];
/*      */         }
/* 1131 */         arrayOfInt[(++i2)] = paramInt2;
/*      */       }
/* 1133 */       short[] arrayOfShort = paramArrayOfShort1;paramArrayOfShort1 = (short[])localObject;localObject = arrayOfShort;
/* 1134 */       int i4 = m;m = n;n = i4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void sort(short[] paramArrayOfShort, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 1147 */     int i = paramInt2 - paramInt1 + 1;
/*      */     
/*      */ 
/* 1150 */     if (i < 47) {
/* 1151 */       if (paramBoolean)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1157 */         j = paramInt1; for (k = j; j < paramInt2; k = j) {
/* 1158 */           m = paramArrayOfShort[(j + 1)];
/* 1159 */           while (m < paramArrayOfShort[k]) {
/* 1160 */             paramArrayOfShort[(k + 1)] = paramArrayOfShort[k];
/* 1161 */             if (k-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           }
/* 1165 */           paramArrayOfShort[(k + 1)] = m;j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/* 1172 */           if (paramInt1 >= paramInt2) {
/* 1173 */             return;
/*      */           }
/* 1175 */         } while (paramArrayOfShort[(++paramInt1)] >= paramArrayOfShort[(paramInt1 - 1)]);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1185 */         for (j = paramInt1;; j = paramInt1) { paramInt1++; if (paramInt1 > paramInt2) break;
/* 1186 */           k = paramArrayOfShort[j];m = paramArrayOfShort[paramInt1];
/*      */           
/* 1188 */           if (k < m) {
/* 1189 */             m = k;k = paramArrayOfShort[paramInt1];
/*      */           }
/* 1191 */           while (k < paramArrayOfShort[(--j)]) {
/* 1192 */             paramArrayOfShort[(j + 2)] = paramArrayOfShort[j];
/*      */           }
/* 1194 */           paramArrayOfShort[(++j + 1)] = k;
/*      */           
/* 1196 */           while (m < paramArrayOfShort[(--j)]) {
/* 1197 */             paramArrayOfShort[(j + 1)] = paramArrayOfShort[j];
/*      */           }
/* 1199 */           paramArrayOfShort[(j + 1)] = m;paramInt1++;
/*      */         }
/* 1201 */         j = paramArrayOfShort[paramInt2];
/*      */         
/* 1203 */         while (j < paramArrayOfShort[(--paramInt2)]) {
/* 1204 */           paramArrayOfShort[(paramInt2 + 1)] = paramArrayOfShort[paramInt2];
/*      */         }
/* 1206 */         paramArrayOfShort[(paramInt2 + 1)] = j;
/*      */       }
/* 1208 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1212 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1221 */     int k = paramInt1 + paramInt2 >>> 1;
/* 1222 */     int m = k - j;
/* 1223 */     int n = m - j;
/* 1224 */     int i1 = k + j;
/* 1225 */     int i2 = i1 + j;
/*      */     
/*      */ 
/* 1228 */     if (paramArrayOfShort[m] < paramArrayOfShort[n]) { i3 = paramArrayOfShort[m];paramArrayOfShort[m] = paramArrayOfShort[n];paramArrayOfShort[n] = i3;
/*      */     }
/* 1230 */     if (paramArrayOfShort[k] < paramArrayOfShort[m]) { i3 = paramArrayOfShort[k];paramArrayOfShort[k] = paramArrayOfShort[m];paramArrayOfShort[m] = i3;
/* 1231 */       if (i3 < paramArrayOfShort[n]) { paramArrayOfShort[m] = paramArrayOfShort[n];paramArrayOfShort[n] = i3;
/*      */       } }
/* 1233 */     if (paramArrayOfShort[i1] < paramArrayOfShort[k]) { i3 = paramArrayOfShort[i1];paramArrayOfShort[i1] = paramArrayOfShort[k];paramArrayOfShort[k] = i3;
/* 1234 */       if (i3 < paramArrayOfShort[m]) { paramArrayOfShort[k] = paramArrayOfShort[m];paramArrayOfShort[m] = i3;
/* 1235 */         if (i3 < paramArrayOfShort[n]) { paramArrayOfShort[m] = paramArrayOfShort[n];paramArrayOfShort[n] = i3;
/*      */         }
/*      */       } }
/* 1238 */     if (paramArrayOfShort[i2] < paramArrayOfShort[i1]) { i3 = paramArrayOfShort[i2];paramArrayOfShort[i2] = paramArrayOfShort[i1];paramArrayOfShort[i1] = i3;
/* 1239 */       if (i3 < paramArrayOfShort[k]) { paramArrayOfShort[i1] = paramArrayOfShort[k];paramArrayOfShort[k] = i3;
/* 1240 */         if (i3 < paramArrayOfShort[m]) { paramArrayOfShort[k] = paramArrayOfShort[m];paramArrayOfShort[m] = i3;
/* 1241 */           if (i3 < paramArrayOfShort[n]) { paramArrayOfShort[m] = paramArrayOfShort[n];paramArrayOfShort[n] = i3;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1247 */     int i3 = paramInt1;
/* 1248 */     int i4 = paramInt2;
/*      */     int i5;
/* 1250 */     int i6; int i7; if ((paramArrayOfShort[n] != paramArrayOfShort[m]) && (paramArrayOfShort[m] != paramArrayOfShort[k]) && (paramArrayOfShort[k] != paramArrayOfShort[i1]) && (paramArrayOfShort[i1] != paramArrayOfShort[i2]))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1256 */       i5 = paramArrayOfShort[m];
/* 1257 */       i6 = paramArrayOfShort[i1];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1265 */       paramArrayOfShort[m] = paramArrayOfShort[paramInt1];
/* 1266 */       paramArrayOfShort[i1] = paramArrayOfShort[paramInt2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1271 */       while (paramArrayOfShort[(++i3)] < i5) {}
/* 1272 */       while (paramArrayOfShort[(--i4)] > i6) {}
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
/* 1294 */       i7 = i3 - 1; int i8; for (;;) { i7++; if (i7 > i4) break;
/* 1295 */         i8 = paramArrayOfShort[i7];
/* 1296 */         if (i8 < i5) {
/* 1297 */           paramArrayOfShort[i7] = paramArrayOfShort[i3];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1302 */           paramArrayOfShort[i3] = i8;
/* 1303 */           i3++;
/* 1304 */         } else if (i8 > i6) {
/* 1305 */           while (paramArrayOfShort[i4] > i6) {
/* 1306 */             if (i4-- == i7) {
/*      */               break label808;
/*      */             }
/*      */           }
/* 1310 */           if (paramArrayOfShort[i4] < i5) {
/* 1311 */             paramArrayOfShort[i7] = paramArrayOfShort[i3];
/* 1312 */             paramArrayOfShort[i3] = paramArrayOfShort[i4];
/* 1313 */             i3++;
/*      */           } else {
/* 1315 */             paramArrayOfShort[i7] = paramArrayOfShort[i4];
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1321 */           paramArrayOfShort[i4] = i8;
/* 1322 */           i4--;
/*      */         }
/*      */       }
/*      */       
/*      */       label808:
/* 1327 */       paramArrayOfShort[paramInt1] = paramArrayOfShort[(i3 - 1)]; paramArrayOfShort[(i3 - 1)] = i5;
/* 1328 */       paramArrayOfShort[paramInt2] = paramArrayOfShort[(i4 + 1)];paramArrayOfShort[(i4 + 1)] = i6;
/*      */       
/*      */ 
/* 1331 */       sort(paramArrayOfShort, paramInt1, i3 - 2, paramBoolean);
/* 1332 */       sort(paramArrayOfShort, i4 + 2, paramInt2, false);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1338 */       if ((i3 < n) && (i2 < i4))
/*      */       {
/*      */ 
/*      */ 
/* 1342 */         while (paramArrayOfShort[i3] == i5) {
/* 1343 */           i3++;
/*      */         }
/*      */         
/* 1346 */         while (paramArrayOfShort[i4] == i6) {
/* 1347 */           i4--;
/*      */         }
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
/* 1370 */         i7 = i3 - 1; for (;;) { i7++; if (i7 > i4) break;
/* 1371 */           i8 = paramArrayOfShort[i7];
/* 1372 */           if (i8 == i5) {
/* 1373 */             paramArrayOfShort[i7] = paramArrayOfShort[i3];
/* 1374 */             paramArrayOfShort[i3] = i8;
/* 1375 */             i3++;
/* 1376 */           } else if (i8 == i6) {
/* 1377 */             while (paramArrayOfShort[i4] == i6) {
/* 1378 */               if (i4-- == i7) {
/*      */                 break label1033;
/*      */               }
/*      */             }
/* 1382 */             if (paramArrayOfShort[i4] == i5) {
/* 1383 */               paramArrayOfShort[i7] = paramArrayOfShort[i3];
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1392 */               paramArrayOfShort[i3] = i5;
/* 1393 */               i3++;
/*      */             } else {
/* 1395 */               paramArrayOfShort[i7] = paramArrayOfShort[i4];
/*      */             }
/* 1397 */             paramArrayOfShort[i4] = i8;
/* 1398 */             i4--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       label1033:
/* 1404 */       sort(paramArrayOfShort, i3, i4, false);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1411 */       i5 = paramArrayOfShort[k];
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
/* 1433 */       for (i6 = i3; i6 <= i4; i6++) {
/* 1434 */         if (paramArrayOfShort[i6] != i5)
/*      */         {
/*      */ 
/* 1437 */           i7 = paramArrayOfShort[i6];
/* 1438 */           if (i7 < i5) {
/* 1439 */             paramArrayOfShort[i6] = paramArrayOfShort[i3];
/* 1440 */             paramArrayOfShort[i3] = i7;
/* 1441 */             i3++;
/*      */           } else {
/* 1443 */             while (paramArrayOfShort[i4] > i5) {
/* 1444 */               i4--;
/*      */             }
/* 1446 */             if (paramArrayOfShort[i4] < i5) {
/* 1447 */               paramArrayOfShort[i6] = paramArrayOfShort[i3];
/* 1448 */               paramArrayOfShort[i3] = paramArrayOfShort[i4];
/* 1449 */               i3++;
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1459 */               paramArrayOfShort[i6] = i5;
/*      */             }
/* 1461 */             paramArrayOfShort[i4] = i7;
/* 1462 */             i4--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1471 */       sort(paramArrayOfShort, paramInt1, i3 - 1, paramBoolean);
/* 1472 */       sort(paramArrayOfShort, i4 + 1, paramInt2, false);
/*      */     }
/*      */   }
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
/*      */   static void sort(char[] paramArrayOfChar1, int paramInt1, int paramInt2, char[] paramArrayOfChar2, int paramInt3, int paramInt4)
/*      */   {
/* 1490 */     if (paramInt2 - paramInt1 > 3200) {
/* 1491 */       int[] arrayOfInt = new int[65536];
/*      */       
/* 1493 */       int i = paramInt1 - 1; for (;;) { i++; if (i > paramInt2) break;
/* 1494 */         arrayOfInt[paramArrayOfChar1[i]] += 1;
/*      */       }
/* 1496 */       i = 65536; int m; for (int j = paramInt2 + 1; j > paramInt1; 
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1503 */           m > 0)
/*      */       {
/* 1497 */         while (arrayOfInt[(--i)] == 0) {}
/* 1498 */         int k = (char)i;
/* 1499 */         m = arrayOfInt[i];
/*      */         
/*      */ 
/* 1502 */         paramArrayOfChar1[(--j)] = k;
/* 1503 */         m--;
/*      */       }
/*      */     } else {
/* 1506 */       doSort(paramArrayOfChar1, paramInt1, paramInt2, paramArrayOfChar2, paramInt3, paramInt4);
/*      */     }
/*      */   }
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
/*      */   private static void doSort(char[] paramArrayOfChar1, int paramInt1, int paramInt2, char[] paramArrayOfChar2, int paramInt3, int paramInt4)
/*      */   {
/* 1526 */     if (paramInt2 - paramInt1 < 286) {
/* 1527 */       sort(paramArrayOfChar1, paramInt1, paramInt2, true);
/* 1528 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1535 */     int[] arrayOfInt = new int[68];
/* 1536 */     int i = 0;arrayOfInt[0] = paramInt1;
/*      */     int m;
/*      */     int n;
/* 1539 */     for (int j = paramInt1; j < paramInt2; arrayOfInt[i] = j) {
/* 1540 */       if (paramArrayOfChar1[j] < paramArrayOfChar1[(j + 1)])
/* 1541 */         for (;;) { j++; if ((j > paramInt2) || (paramArrayOfChar1[(j - 1)] > paramArrayOfChar1[j])) break; }
/* 1542 */       if (paramArrayOfChar1[j] > paramArrayOfChar1[(j + 1)]) {
/* 1543 */         do { j++; } while ((j <= paramInt2) && (paramArrayOfChar1[(j - 1)] >= paramArrayOfChar1[j]));
/* 1544 */         k = arrayOfInt[i] - 1;m = j; for (;;) { k++; if (k >= --m) break;
/* 1545 */           n = paramArrayOfChar1[k];paramArrayOfChar1[k] = paramArrayOfChar1[m];paramArrayOfChar1[m] = n;
/*      */         }
/*      */       } else {
/* 1548 */         k = 33; do { j++; if ((j > paramInt2) || (paramArrayOfChar1[(j - 1)] != paramArrayOfChar1[j])) break;
/* 1549 */           k--; } while (k != 0);
/* 1550 */         sort(paramArrayOfChar1, paramInt1, paramInt2, true);
/* 1551 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1560 */       i++; if (i == 67) {
/* 1561 */         sort(paramArrayOfChar1, paramInt1, paramInt2, true);
/* 1562 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1568 */     if (arrayOfInt[i] == paramInt2++) {
/* 1569 */       arrayOfInt[(++i)] = paramInt2;
/* 1570 */     } else if (i == 1) {
/* 1571 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1575 */     j = 0;
/* 1576 */     for (int k = 1; k <<= 1 < i; j = (byte)(j ^ 0x1)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1581 */     int i1 = paramInt2 - paramInt1;
/* 1582 */     if ((paramArrayOfChar2 == null) || (paramInt4 < i1) || (paramInt3 + i1 > paramArrayOfChar2.length)) {
/* 1583 */       paramArrayOfChar2 = new char[i1];
/* 1584 */       paramInt3 = 0; }
/*      */     Object localObject;
/* 1586 */     if (j == 0) {
/* 1587 */       System.arraycopy(paramArrayOfChar1, paramInt1, paramArrayOfChar2, paramInt3, i1);
/* 1588 */       localObject = paramArrayOfChar1;
/* 1589 */       n = 0;
/* 1590 */       paramArrayOfChar1 = paramArrayOfChar2;
/* 1591 */       m = paramInt3 - paramInt1;
/*      */     } else {
/* 1593 */       localObject = paramArrayOfChar2;
/* 1594 */       m = 0;
/* 1595 */       n = paramInt3 - paramInt1;
/*      */     }
/*      */     int i2;
/* 1599 */     for (; 
/* 1599 */         i > 1; i = i2) {
/* 1600 */       for (int i3 = (i2 = 0) + 2; i3 <= i; i3 += 2) {
/* 1601 */         i4 = arrayOfInt[i3];int i5 = arrayOfInt[(i3 - 1)];
/* 1602 */         int i6 = arrayOfInt[(i3 - 2)];int i7 = i6; for (int i8 = i5; i6 < i4; i6++) {
/* 1603 */           if ((i8 >= i4) || ((i7 < i5) && (paramArrayOfChar1[(i7 + m)] <= paramArrayOfChar1[(i8 + m)]))) {
/* 1604 */             localObject[(i6 + n)] = paramArrayOfChar1[(i7++ + m)];
/*      */           } else {
/* 1606 */             localObject[(i6 + n)] = paramArrayOfChar1[(i8++ + m)];
/*      */           }
/*      */         }
/* 1609 */         arrayOfInt[(++i2)] = i4;
/*      */       }
/* 1611 */       if ((i & 0x1) != 0) {
/* 1612 */         i3 = paramInt2;i4 = arrayOfInt[(i - 1)]; for (;;) { i3--; if (i3 < i4) break;
/* 1613 */           localObject[(i3 + n)] = paramArrayOfChar1[(i3 + m)];
/*      */         }
/* 1615 */         arrayOfInt[(++i2)] = paramInt2;
/*      */       }
/* 1617 */       char[] arrayOfChar = paramArrayOfChar1;paramArrayOfChar1 = (char[])localObject;localObject = arrayOfChar;
/* 1618 */       int i4 = m;m = n;n = i4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void sort(char[] paramArrayOfChar, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 1631 */     int i = paramInt2 - paramInt1 + 1;
/*      */     
/*      */ 
/* 1634 */     if (i < 47) {
/* 1635 */       if (paramBoolean)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1641 */         j = paramInt1; for (k = j; j < paramInt2; k = j) {
/* 1642 */           m = paramArrayOfChar[(j + 1)];
/* 1643 */           while (m < paramArrayOfChar[k]) {
/* 1644 */             paramArrayOfChar[(k + 1)] = paramArrayOfChar[k];
/* 1645 */             if (k-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           }
/* 1649 */           paramArrayOfChar[(k + 1)] = m;j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/* 1656 */           if (paramInt1 >= paramInt2) {
/* 1657 */             return;
/*      */           }
/* 1659 */         } while (paramArrayOfChar[(++paramInt1)] >= paramArrayOfChar[(paramInt1 - 1)]);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1669 */         for (j = paramInt1;; j = paramInt1) { paramInt1++; if (paramInt1 > paramInt2) break;
/* 1670 */           k = paramArrayOfChar[j];m = paramArrayOfChar[paramInt1];
/*      */           
/* 1672 */           if (k < m) {
/* 1673 */             m = k;k = paramArrayOfChar[paramInt1];
/*      */           }
/* 1675 */           while (k < paramArrayOfChar[(--j)]) {
/* 1676 */             paramArrayOfChar[(j + 2)] = paramArrayOfChar[j];
/*      */           }
/* 1678 */           paramArrayOfChar[(++j + 1)] = k;
/*      */           
/* 1680 */           while (m < paramArrayOfChar[(--j)]) {
/* 1681 */             paramArrayOfChar[(j + 1)] = paramArrayOfChar[j];
/*      */           }
/* 1683 */           paramArrayOfChar[(j + 1)] = m;paramInt1++;
/*      */         }
/* 1685 */         j = paramArrayOfChar[paramInt2];
/*      */         
/* 1687 */         while (j < paramArrayOfChar[(--paramInt2)]) {
/* 1688 */           paramArrayOfChar[(paramInt2 + 1)] = paramArrayOfChar[paramInt2];
/*      */         }
/* 1690 */         paramArrayOfChar[(paramInt2 + 1)] = j;
/*      */       }
/* 1692 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1696 */     int j = (i >> 3) + (i >> 6) + 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1705 */     int k = paramInt1 + paramInt2 >>> 1;
/* 1706 */     int m = k - j;
/* 1707 */     int n = m - j;
/* 1708 */     int i1 = k + j;
/* 1709 */     int i2 = i1 + j;
/*      */     
/*      */ 
/* 1712 */     if (paramArrayOfChar[m] < paramArrayOfChar[n]) { i3 = paramArrayOfChar[m];paramArrayOfChar[m] = paramArrayOfChar[n];paramArrayOfChar[n] = i3;
/*      */     }
/* 1714 */     if (paramArrayOfChar[k] < paramArrayOfChar[m]) { i3 = paramArrayOfChar[k];paramArrayOfChar[k] = paramArrayOfChar[m];paramArrayOfChar[m] = i3;
/* 1715 */       if (i3 < paramArrayOfChar[n]) { paramArrayOfChar[m] = paramArrayOfChar[n];paramArrayOfChar[n] = i3;
/*      */       } }
/* 1717 */     if (paramArrayOfChar[i1] < paramArrayOfChar[k]) { i3 = paramArrayOfChar[i1];paramArrayOfChar[i1] = paramArrayOfChar[k];paramArrayOfChar[k] = i3;
/* 1718 */       if (i3 < paramArrayOfChar[m]) { paramArrayOfChar[k] = paramArrayOfChar[m];paramArrayOfChar[m] = i3;
/* 1719 */         if (i3 < paramArrayOfChar[n]) { paramArrayOfChar[m] = paramArrayOfChar[n];paramArrayOfChar[n] = i3;
/*      */         }
/*      */       } }
/* 1722 */     if (paramArrayOfChar[i2] < paramArrayOfChar[i1]) { i3 = paramArrayOfChar[i2];paramArrayOfChar[i2] = paramArrayOfChar[i1];paramArrayOfChar[i1] = i3;
/* 1723 */       if (i3 < paramArrayOfChar[k]) { paramArrayOfChar[i1] = paramArrayOfChar[k];paramArrayOfChar[k] = i3;
/* 1724 */         if (i3 < paramArrayOfChar[m]) { paramArrayOfChar[k] = paramArrayOfChar[m];paramArrayOfChar[m] = i3;
/* 1725 */           if (i3 < paramArrayOfChar[n]) { paramArrayOfChar[m] = paramArrayOfChar[n];paramArrayOfChar[n] = i3;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1731 */     int i3 = paramInt1;
/* 1732 */     int i4 = paramInt2;
/*      */     int i5;
/* 1734 */     int i6; int i7; if ((paramArrayOfChar[n] != paramArrayOfChar[m]) && (paramArrayOfChar[m] != paramArrayOfChar[k]) && (paramArrayOfChar[k] != paramArrayOfChar[i1]) && (paramArrayOfChar[i1] != paramArrayOfChar[i2]))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1740 */       i5 = paramArrayOfChar[m];
/* 1741 */       i6 = paramArrayOfChar[i1];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1749 */       paramArrayOfChar[m] = paramArrayOfChar[paramInt1];
/* 1750 */       paramArrayOfChar[i1] = paramArrayOfChar[paramInt2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1755 */       while (paramArrayOfChar[(++i3)] < i5) {}
/* 1756 */       while (paramArrayOfChar[(--i4)] > i6) {}
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
/* 1778 */       i7 = i3 - 1; int i8; for (;;) { i7++; if (i7 > i4) break;
/* 1779 */         i8 = paramArrayOfChar[i7];
/* 1780 */         if (i8 < i5) {
/* 1781 */           paramArrayOfChar[i7] = paramArrayOfChar[i3];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1786 */           paramArrayOfChar[i3] = i8;
/* 1787 */           i3++;
/* 1788 */         } else if (i8 > i6) {
/* 1789 */           while (paramArrayOfChar[i4] > i6) {
/* 1790 */             if (i4-- == i7) {
/*      */               break label808;
/*      */             }
/*      */           }
/* 1794 */           if (paramArrayOfChar[i4] < i5) {
/* 1795 */             paramArrayOfChar[i7] = paramArrayOfChar[i3];
/* 1796 */             paramArrayOfChar[i3] = paramArrayOfChar[i4];
/* 1797 */             i3++;
/*      */           } else {
/* 1799 */             paramArrayOfChar[i7] = paramArrayOfChar[i4];
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1805 */           paramArrayOfChar[i4] = i8;
/* 1806 */           i4--;
/*      */         }
/*      */       }
/*      */       
/*      */       label808:
/* 1811 */       paramArrayOfChar[paramInt1] = paramArrayOfChar[(i3 - 1)]; paramArrayOfChar[(i3 - 1)] = i5;
/* 1812 */       paramArrayOfChar[paramInt2] = paramArrayOfChar[(i4 + 1)];paramArrayOfChar[(i4 + 1)] = i6;
/*      */       
/*      */ 
/* 1815 */       sort(paramArrayOfChar, paramInt1, i3 - 2, paramBoolean);
/* 1816 */       sort(paramArrayOfChar, i4 + 2, paramInt2, false);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1822 */       if ((i3 < n) && (i2 < i4))
/*      */       {
/*      */ 
/*      */ 
/* 1826 */         while (paramArrayOfChar[i3] == i5) {
/* 1827 */           i3++;
/*      */         }
/*      */         
/* 1830 */         while (paramArrayOfChar[i4] == i6) {
/* 1831 */           i4--;
/*      */         }
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
/* 1854 */         i7 = i3 - 1; for (;;) { i7++; if (i7 > i4) break;
/* 1855 */           i8 = paramArrayOfChar[i7];
/* 1856 */           if (i8 == i5) {
/* 1857 */             paramArrayOfChar[i7] = paramArrayOfChar[i3];
/* 1858 */             paramArrayOfChar[i3] = i8;
/* 1859 */             i3++;
/* 1860 */           } else if (i8 == i6) {
/* 1861 */             while (paramArrayOfChar[i4] == i6) {
/* 1862 */               if (i4-- == i7) {
/*      */                 break label1033;
/*      */               }
/*      */             }
/* 1866 */             if (paramArrayOfChar[i4] == i5) {
/* 1867 */               paramArrayOfChar[i7] = paramArrayOfChar[i3];
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1876 */               paramArrayOfChar[i3] = i5;
/* 1877 */               i3++;
/*      */             } else {
/* 1879 */               paramArrayOfChar[i7] = paramArrayOfChar[i4];
/*      */             }
/* 1881 */             paramArrayOfChar[i4] = i8;
/* 1882 */             i4--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       label1033:
/* 1888 */       sort(paramArrayOfChar, i3, i4, false);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1895 */       i5 = paramArrayOfChar[k];
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
/* 1917 */       for (i6 = i3; i6 <= i4; i6++) {
/* 1918 */         if (paramArrayOfChar[i6] != i5)
/*      */         {
/*      */ 
/* 1921 */           i7 = paramArrayOfChar[i6];
/* 1922 */           if (i7 < i5) {
/* 1923 */             paramArrayOfChar[i6] = paramArrayOfChar[i3];
/* 1924 */             paramArrayOfChar[i3] = i7;
/* 1925 */             i3++;
/*      */           } else {
/* 1927 */             while (paramArrayOfChar[i4] > i5) {
/* 1928 */               i4--;
/*      */             }
/* 1930 */             if (paramArrayOfChar[i4] < i5) {
/* 1931 */               paramArrayOfChar[i6] = paramArrayOfChar[i3];
/* 1932 */               paramArrayOfChar[i3] = paramArrayOfChar[i4];
/* 1933 */               i3++;
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1943 */               paramArrayOfChar[i6] = i5;
/*      */             }
/* 1945 */             paramArrayOfChar[i4] = i7;
/* 1946 */             i4--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1955 */       sort(paramArrayOfChar, paramInt1, i3 - 1, paramBoolean);
/* 1956 */       sort(paramArrayOfChar, i4 + 1, paramInt2, false);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void sort(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*      */     int j;
/*      */     
/*      */ 
/*      */ 
/*      */     int k;
/*      */     
/*      */ 
/* 1972 */     if (paramInt2 - paramInt1 > 29) {
/* 1973 */       int[] arrayOfInt = new int['Ā'];
/*      */       
/* 1975 */       j = paramInt1 - 1; for (;;) { j++; if (j > paramInt2) break;
/* 1976 */         arrayOfInt[(paramArrayOfByte[j] - Byte.MIN_VALUE)] += 1;
/*      */       }
/* 1978 */       j = 256; int n; for (k = paramInt2 + 1; k > paramInt1; 
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1985 */           n > 0)
/*      */       {
/* 1979 */         while (arrayOfInt[(--j)] == 0) {}
/* 1980 */         int m = (byte)(j + -128);
/* 1981 */         n = arrayOfInt[j];
/*      */         
/*      */ 
/* 1984 */         paramArrayOfByte[(--k)] = m;
/* 1985 */         n--;
/*      */       }
/*      */     } else {
/* 1988 */       int i = paramInt1; for (j = i; i < paramInt2; j = i) {
/* 1989 */         k = paramArrayOfByte[(i + 1)];
/* 1990 */         while (k < paramArrayOfByte[j]) {
/* 1991 */           paramArrayOfByte[(j + 1)] = paramArrayOfByte[j];
/* 1992 */           if (j-- == paramInt1) {
/*      */             break;
/*      */           }
/*      */         }
/* 1996 */         paramArrayOfByte[(j + 1)] = k;i++;
/*      */       }
/*      */     }
/*      */   }
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
/*      */   static void sort(float[] paramArrayOfFloat1, int paramInt1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3, int paramInt4)
/*      */   {
/* 2017 */     while ((paramInt1 <= paramInt2) && (Float.isNaN(paramArrayOfFloat1[paramInt2]))) {
/* 2018 */       paramInt2--;
/*      */     }
/* 2020 */     int i = paramInt2; for (;;) { i--; if (i < paramInt1) break;
/* 2021 */       float f1 = paramArrayOfFloat1[i];
/* 2022 */       if (f1 != f1) {
/* 2023 */         paramArrayOfFloat1[i] = paramArrayOfFloat1[paramInt2];
/* 2024 */         paramArrayOfFloat1[paramInt2] = f1;
/* 2025 */         paramInt2--;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2032 */     doSort(paramArrayOfFloat1, paramInt1, paramInt2, paramArrayOfFloat2, paramInt3, paramInt4);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2037 */     i = paramInt2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2042 */     while (paramInt1 < i) {
/* 2043 */       j = paramInt1 + i >>> 1;
/* 2044 */       float f2 = paramArrayOfFloat1[j];
/*      */       
/* 2046 */       if (f2 < 0.0F) {
/* 2047 */         paramInt1 = j + 1;
/*      */       } else {
/* 2049 */         i = j;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2056 */     while ((paramInt1 <= paramInt2) && (Float.floatToRawIntBits(paramArrayOfFloat1[paramInt1]) < 0)) {
/* 2057 */       paramInt1++;
/*      */     }
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
/* 2081 */     int j = paramInt1;int k = paramInt1 - 1; for (;;) { j++; if (j > paramInt2) break;
/* 2082 */       float f3 = paramArrayOfFloat1[j];
/* 2083 */       if (f3 != 0.0F) {
/*      */         break;
/*      */       }
/* 2086 */       if (Float.floatToRawIntBits(f3) < 0) {
/* 2087 */         paramArrayOfFloat1[j] = 0.0F;
/* 2088 */         paramArrayOfFloat1[(++k)] = -0.0F;
/*      */       }
/*      */     }
/*      */   }
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
/*      */   private static void doSort(float[] paramArrayOfFloat1, int paramInt1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3, int paramInt4)
/*      */   {
/* 2106 */     if (paramInt2 - paramInt1 < 286) {
/* 2107 */       sort(paramArrayOfFloat1, paramInt1, paramInt2, true);
/* 2108 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2115 */     int[] arrayOfInt = new int[68];
/* 2116 */     int i = 0;arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int m;
/* 2119 */     for (int j = paramInt1; j < paramInt2; arrayOfInt[i] = j) {
/* 2120 */       if (paramArrayOfFloat1[j] < paramArrayOfFloat1[(j + 1)])
/* 2121 */         for (;;) { j++; if ((j > paramInt2) || (paramArrayOfFloat1[(j - 1)] > paramArrayOfFloat1[j])) break; }
/* 2122 */       if (paramArrayOfFloat1[j] > paramArrayOfFloat1[(j + 1)]) {
/* 2123 */         do { j++; } while ((j <= paramInt2) && (paramArrayOfFloat1[(j - 1)] >= paramArrayOfFloat1[j]));
/* 2124 */         k = arrayOfInt[i] - 1;m = j; for (;;) { k++; if (k >= --m) break;
/* 2125 */           float f = paramArrayOfFloat1[k];paramArrayOfFloat1[k] = paramArrayOfFloat1[m];paramArrayOfFloat1[m] = f;
/*      */         }
/*      */       } else {
/* 2128 */         k = 33; do { j++; if ((j > paramInt2) || (paramArrayOfFloat1[(j - 1)] != paramArrayOfFloat1[j])) break;
/* 2129 */           k--; } while (k != 0);
/* 2130 */         sort(paramArrayOfFloat1, paramInt1, paramInt2, true);
/* 2131 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2140 */       i++; if (i == 67) {
/* 2141 */         sort(paramArrayOfFloat1, paramInt1, paramInt2, true);
/* 2142 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2148 */     if (arrayOfInt[i] == paramInt2++) {
/* 2149 */       arrayOfInt[(++i)] = paramInt2;
/* 2150 */     } else if (i == 1) {
/* 2151 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2155 */     j = 0;
/* 2156 */     for (int k = 1; k <<= 1 < i; j = (byte)(j ^ 0x1)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2161 */     int i1 = paramInt2 - paramInt1;
/* 2162 */     if ((paramArrayOfFloat2 == null) || (paramInt4 < i1) || (paramInt3 + i1 > paramArrayOfFloat2.length)) {
/* 2163 */       paramArrayOfFloat2 = new float[i1];
/* 2164 */       paramInt3 = 0; }
/*      */     Object localObject;
/* 2166 */     int n; if (j == 0) {
/* 2167 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt3, i1);
/* 2168 */       localObject = paramArrayOfFloat1;
/* 2169 */       n = 0;
/* 2170 */       paramArrayOfFloat1 = paramArrayOfFloat2;
/* 2171 */       m = paramInt3 - paramInt1;
/*      */     } else {
/* 2173 */       localObject = paramArrayOfFloat2;
/* 2174 */       m = 0;
/* 2175 */       n = paramInt3 - paramInt1;
/*      */     }
/*      */     int i2;
/* 2179 */     for (; 
/* 2179 */         i > 1; i = i2) {
/* 2180 */       for (int i3 = (i2 = 0) + 2; i3 <= i; i3 += 2) {
/* 2181 */         i4 = arrayOfInt[i3];int i5 = arrayOfInt[(i3 - 1)];
/* 2182 */         int i6 = arrayOfInt[(i3 - 2)];int i7 = i6; for (int i8 = i5; i6 < i4; i6++) {
/* 2183 */           if ((i8 >= i4) || ((i7 < i5) && (paramArrayOfFloat1[(i7 + m)] <= paramArrayOfFloat1[(i8 + m)]))) {
/* 2184 */             localObject[(i6 + n)] = paramArrayOfFloat1[(i7++ + m)];
/*      */           } else {
/* 2186 */             localObject[(i6 + n)] = paramArrayOfFloat1[(i8++ + m)];
/*      */           }
/*      */         }
/* 2189 */         arrayOfInt[(++i2)] = i4;
/*      */       }
/* 2191 */       if ((i & 0x1) != 0) {
/* 2192 */         i3 = paramInt2;i4 = arrayOfInt[(i - 1)]; for (;;) { i3--; if (i3 < i4) break;
/* 2193 */           localObject[(i3 + n)] = paramArrayOfFloat1[(i3 + m)];
/*      */         }
/* 2195 */         arrayOfInt[(++i2)] = paramInt2;
/*      */       }
/* 2197 */       float[] arrayOfFloat = paramArrayOfFloat1;paramArrayOfFloat1 = (float[])localObject;localObject = arrayOfFloat;
/* 2198 */       int i4 = m;m = n;n = i4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void sort(float[] paramArrayOfFloat, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 2211 */     int i = paramInt2 - paramInt1 + 1;
/*      */     
/*      */ 
/* 2214 */     if (i < 47) { int j;
/* 2215 */       float f3; if (paramBoolean)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2221 */         j = paramInt1; for (int m = j; j < paramInt2; m = j) {
/* 2222 */           f3 = paramArrayOfFloat[(j + 1)];
/* 2223 */           while (f3 < paramArrayOfFloat[m]) {
/* 2224 */             paramArrayOfFloat[(m + 1)] = paramArrayOfFloat[m];
/* 2225 */             if (m-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           }
/* 2229 */           paramArrayOfFloat[(m + 1)] = f3;j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/* 2236 */           if (paramInt1 >= paramInt2) {
/* 2237 */             return;
/*      */           }
/* 2239 */         } while (paramArrayOfFloat[(++paramInt1)] >= paramArrayOfFloat[(paramInt1 - 1)]);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2249 */         for (j = paramInt1;; j = paramInt1) { paramInt1++; if (paramInt1 > paramInt2) break;
/* 2250 */           float f2 = paramArrayOfFloat[j];f3 = paramArrayOfFloat[paramInt1];
/*      */           
/* 2252 */           if (f2 < f3) {
/* 2253 */             f3 = f2;f2 = paramArrayOfFloat[paramInt1];
/*      */           }
/* 2255 */           while (f2 < paramArrayOfFloat[(--j)]) {
/* 2256 */             paramArrayOfFloat[(j + 2)] = paramArrayOfFloat[j];
/*      */           }
/* 2258 */           paramArrayOfFloat[(++j + 1)] = f2;
/*      */           
/* 2260 */           while (f3 < paramArrayOfFloat[(--j)]) {
/* 2261 */             paramArrayOfFloat[(j + 1)] = paramArrayOfFloat[j];
/*      */           }
/* 2263 */           paramArrayOfFloat[(j + 1)] = f3;paramInt1++;
/*      */         }
/* 2265 */         float f1 = paramArrayOfFloat[paramInt2];
/*      */         
/* 2267 */         while (f1 < paramArrayOfFloat[(--paramInt2)]) {
/* 2268 */           paramArrayOfFloat[(paramInt2 + 1)] = paramArrayOfFloat[paramInt2];
/*      */         }
/* 2270 */         paramArrayOfFloat[(paramInt2 + 1)] = f1;
/*      */       }
/* 2272 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2276 */     int k = (i >> 3) + (i >> 6) + 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2285 */     int n = paramInt1 + paramInt2 >>> 1;
/* 2286 */     int i1 = n - k;
/* 2287 */     int i2 = i1 - k;
/* 2288 */     int i3 = n + k;
/* 2289 */     int i4 = i3 + k;
/*      */     
/*      */     float f4;
/* 2292 */     if (paramArrayOfFloat[i1] < paramArrayOfFloat[i2]) { f4 = paramArrayOfFloat[i1];paramArrayOfFloat[i1] = paramArrayOfFloat[i2];paramArrayOfFloat[i2] = f4;
/*      */     }
/* 2294 */     if (paramArrayOfFloat[n] < paramArrayOfFloat[i1]) { f4 = paramArrayOfFloat[n];paramArrayOfFloat[n] = paramArrayOfFloat[i1];paramArrayOfFloat[i1] = f4;
/* 2295 */       if (f4 < paramArrayOfFloat[i2]) { paramArrayOfFloat[i1] = paramArrayOfFloat[i2];paramArrayOfFloat[i2] = f4;
/*      */       } }
/* 2297 */     if (paramArrayOfFloat[i3] < paramArrayOfFloat[n]) { f4 = paramArrayOfFloat[i3];paramArrayOfFloat[i3] = paramArrayOfFloat[n];paramArrayOfFloat[n] = f4;
/* 2298 */       if (f4 < paramArrayOfFloat[i1]) { paramArrayOfFloat[n] = paramArrayOfFloat[i1];paramArrayOfFloat[i1] = f4;
/* 2299 */         if (f4 < paramArrayOfFloat[i2]) { paramArrayOfFloat[i1] = paramArrayOfFloat[i2];paramArrayOfFloat[i2] = f4;
/*      */         }
/*      */       } }
/* 2302 */     if (paramArrayOfFloat[i4] < paramArrayOfFloat[i3]) { f4 = paramArrayOfFloat[i4];paramArrayOfFloat[i4] = paramArrayOfFloat[i3];paramArrayOfFloat[i3] = f4;
/* 2303 */       if (f4 < paramArrayOfFloat[n]) { paramArrayOfFloat[i3] = paramArrayOfFloat[n];paramArrayOfFloat[n] = f4;
/* 2304 */         if (f4 < paramArrayOfFloat[i1]) { paramArrayOfFloat[n] = paramArrayOfFloat[i1];paramArrayOfFloat[i1] = f4;
/* 2305 */           if (f4 < paramArrayOfFloat[i2]) { paramArrayOfFloat[i1] = paramArrayOfFloat[i2];paramArrayOfFloat[i2] = f4;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2311 */     float f5 = paramInt1;
/* 2312 */     float f6 = paramInt2;
/*      */     float f7;
/* 2314 */     float f8; label834: float f10; if ((paramArrayOfFloat[i2] != paramArrayOfFloat[i1]) && (paramArrayOfFloat[i1] != paramArrayOfFloat[n]) && (paramArrayOfFloat[n] != paramArrayOfFloat[i3]) && (paramArrayOfFloat[i3] != paramArrayOfFloat[i4]))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2320 */       f7 = paramArrayOfFloat[i1];
/* 2321 */       f8 = paramArrayOfFloat[i3];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2329 */       paramArrayOfFloat[i1] = paramArrayOfFloat[paramInt1];
/* 2330 */       paramArrayOfFloat[i3] = paramArrayOfFloat[paramInt2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2335 */       while (paramArrayOfFloat[(++f5)] < f7) {}
/* 2336 */       while (paramArrayOfFloat[(--f6)] > f8) {}
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
/* 2358 */       float f9 = f5 - 1; float f11; for (;;) { f9++; if (f9 > f6) break;
/* 2359 */         f11 = paramArrayOfFloat[f9];
/* 2360 */         if (f11 < f7) {
/* 2361 */           paramArrayOfFloat[f9] = paramArrayOfFloat[f5];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2366 */           paramArrayOfFloat[f5] = f11;
/* 2367 */           f5++;
/* 2368 */         } else if (f11 > f8) {
/* 2369 */           while (paramArrayOfFloat[f6] > f8) {
/* 2370 */             if (f6-- == f9) {
/*      */               break label834;
/*      */             }
/*      */           }
/* 2374 */           if (paramArrayOfFloat[f6] < f7) {
/* 2375 */             paramArrayOfFloat[f9] = paramArrayOfFloat[f5];
/* 2376 */             paramArrayOfFloat[f5] = paramArrayOfFloat[f6];
/* 2377 */             f5++;
/*      */           } else {
/* 2379 */             paramArrayOfFloat[f9] = paramArrayOfFloat[f6];
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2385 */           paramArrayOfFloat[f6] = f11;
/* 2386 */           f6--;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2391 */       paramArrayOfFloat[paramInt1] = paramArrayOfFloat[(f5 - 1)];paramArrayOfFloat[(f5 - 1)] = f7;
/* 2392 */       paramArrayOfFloat[paramInt2] = paramArrayOfFloat[(f6 + 1)];paramArrayOfFloat[(f6 + 1)] = f8;
/*      */       
/*      */ 
/* 2395 */       sort(paramArrayOfFloat, paramInt1, f5 - 2, paramBoolean);
/* 2396 */       sort(paramArrayOfFloat, f6 + 2, paramInt2, false);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2402 */       if ((f5 < i2) && (i4 < f6))
/*      */       {
/*      */ 
/*      */ 
/* 2406 */         while (paramArrayOfFloat[f5] == f7) {
/* 2407 */           f5++;
/*      */         }
/*      */         
/* 2410 */         while (paramArrayOfFloat[f6] == f8) {
/* 2411 */           f6--;
/*      */         }
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
/* 2434 */         f10 = f5 - 1; for (;;) { f10++; if (f10 > f6) break;
/* 2435 */           f11 = paramArrayOfFloat[f10];
/* 2436 */           if (f11 == f7) {
/* 2437 */             paramArrayOfFloat[f10] = paramArrayOfFloat[f5];
/* 2438 */             paramArrayOfFloat[f5] = f11;
/* 2439 */             f5++;
/* 2440 */           } else if (f11 == f8) {
/* 2441 */             while (paramArrayOfFloat[f6] == f8) {
/* 2442 */               if (f6-- == f10) {
/*      */                 break label1067;
/*      */               }
/*      */             }
/* 2446 */             if (paramArrayOfFloat[f6] == f7) {
/* 2447 */               paramArrayOfFloat[f10] = paramArrayOfFloat[f5];
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2456 */               paramArrayOfFloat[f5] = paramArrayOfFloat[f6];
/* 2457 */               f5++;
/*      */             } else {
/* 2459 */               paramArrayOfFloat[f10] = paramArrayOfFloat[f6];
/*      */             }
/* 2461 */             paramArrayOfFloat[f6] = f11;
/* 2462 */             f6--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       label1067:
/* 2468 */       sort(paramArrayOfFloat, f5, f6, false);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 2475 */       f7 = paramArrayOfFloat[n];
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
/* 2497 */       for (f8 = f5; f8 <= f6; f8++) {
/* 2498 */         if (paramArrayOfFloat[f8] != f7)
/*      */         {
/*      */ 
/* 2501 */           f10 = paramArrayOfFloat[f8];
/* 2502 */           if (f10 < f7) {
/* 2503 */             paramArrayOfFloat[f8] = paramArrayOfFloat[f5];
/* 2504 */             paramArrayOfFloat[f5] = f10;
/* 2505 */             f5++;
/*      */           } else {
/* 2507 */             while (paramArrayOfFloat[f6] > f7) {
/* 2508 */               f6--;
/*      */             }
/* 2510 */             if (paramArrayOfFloat[f6] < f7) {
/* 2511 */               paramArrayOfFloat[f8] = paramArrayOfFloat[f5];
/* 2512 */               paramArrayOfFloat[f5] = paramArrayOfFloat[f6];
/* 2513 */               f5++;
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 2523 */               paramArrayOfFloat[f8] = paramArrayOfFloat[f6];
/*      */             }
/* 2525 */             paramArrayOfFloat[f6] = f10;
/* 2526 */             f6--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2535 */       sort(paramArrayOfFloat, paramInt1, f5 - 1, paramBoolean);
/* 2536 */       sort(paramArrayOfFloat, f6 + 1, paramInt2, false);
/*      */     }
/*      */   }
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
/*      */   static void sort(double[] paramArrayOfDouble1, int paramInt1, int paramInt2, double[] paramArrayOfDouble2, int paramInt3, int paramInt4)
/*      */   {
/* 2556 */     while ((paramInt1 <= paramInt2) && (Double.isNaN(paramArrayOfDouble1[paramInt2]))) {
/* 2557 */       paramInt2--;
/*      */     }
/* 2559 */     int i = paramInt2; for (;;) { i--; if (i < paramInt1) break;
/* 2560 */       double d1 = paramArrayOfDouble1[i];
/* 2561 */       if (d1 != d1) {
/* 2562 */         paramArrayOfDouble1[i] = paramArrayOfDouble1[paramInt2];
/* 2563 */         paramArrayOfDouble1[paramInt2] = d1;
/* 2564 */         paramInt2--;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2571 */     doSort(paramArrayOfDouble1, paramInt1, paramInt2, paramArrayOfDouble2, paramInt3, paramInt4);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2576 */     i = paramInt2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2581 */     while (paramInt1 < i) {
/* 2582 */       j = paramInt1 + i >>> 1;
/* 2583 */       double d2 = paramArrayOfDouble1[j];
/*      */       
/* 2585 */       if (d2 < 0.0D) {
/* 2586 */         paramInt1 = j + 1;
/*      */       } else {
/* 2588 */         i = j;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2595 */     while ((paramInt1 <= paramInt2) && (Double.doubleToRawLongBits(paramArrayOfDouble1[paramInt1]) < 0L)) {
/* 2596 */       paramInt1++;
/*      */     }
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
/* 2620 */     int j = paramInt1;int k = paramInt1 - 1; for (;;) { j++; if (j > paramInt2) break;
/* 2621 */       double d3 = paramArrayOfDouble1[j];
/* 2622 */       if (d3 != 0.0D) {
/*      */         break;
/*      */       }
/* 2625 */       if (Double.doubleToRawLongBits(d3) < 0L) {
/* 2626 */         paramArrayOfDouble1[j] = 0.0D;
/* 2627 */         paramArrayOfDouble1[(++k)] = -0.0D;
/*      */       }
/*      */     }
/*      */   }
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
/*      */   private static void doSort(double[] paramArrayOfDouble1, int paramInt1, int paramInt2, double[] paramArrayOfDouble2, int paramInt3, int paramInt4)
/*      */   {
/* 2645 */     if (paramInt2 - paramInt1 < 286) {
/* 2646 */       sort(paramArrayOfDouble1, paramInt1, paramInt2, true);
/* 2647 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2654 */     int[] arrayOfInt = new int[68];
/* 2655 */     int i = 0;arrayOfInt[0] = paramInt1;
/*      */     
/*      */     int m;
/* 2658 */     for (int j = paramInt1; j < paramInt2; arrayOfInt[i] = j) {
/* 2659 */       if (paramArrayOfDouble1[j] < paramArrayOfDouble1[(j + 1)])
/* 2660 */         for (;;) { j++; if ((j > paramInt2) || (paramArrayOfDouble1[(j - 1)] > paramArrayOfDouble1[j])) break; }
/* 2661 */       if (paramArrayOfDouble1[j] > paramArrayOfDouble1[(j + 1)]) {
/* 2662 */         do { j++; } while ((j <= paramInt2) && (paramArrayOfDouble1[(j - 1)] >= paramArrayOfDouble1[j]));
/* 2663 */         k = arrayOfInt[i] - 1;m = j; for (;;) { k++; if (k >= --m) break;
/* 2664 */           double d = paramArrayOfDouble1[k];paramArrayOfDouble1[k] = paramArrayOfDouble1[m];paramArrayOfDouble1[m] = d;
/*      */         }
/*      */       } else {
/* 2667 */         k = 33; do { j++; if ((j > paramInt2) || (paramArrayOfDouble1[(j - 1)] != paramArrayOfDouble1[j])) break;
/* 2668 */           k--; } while (k != 0);
/* 2669 */         sort(paramArrayOfDouble1, paramInt1, paramInt2, true);
/* 2670 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2679 */       i++; if (i == 67) {
/* 2680 */         sort(paramArrayOfDouble1, paramInt1, paramInt2, true);
/* 2681 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2687 */     if (arrayOfInt[i] == paramInt2++) {
/* 2688 */       arrayOfInt[(++i)] = paramInt2;
/* 2689 */     } else if (i == 1) {
/* 2690 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2694 */     j = 0;
/* 2695 */     for (int k = 1; k <<= 1 < i; j = (byte)(j ^ 0x1)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2700 */     int i1 = paramInt2 - paramInt1;
/* 2701 */     if ((paramArrayOfDouble2 == null) || (paramInt4 < i1) || (paramInt3 + i1 > paramArrayOfDouble2.length)) {
/* 2702 */       paramArrayOfDouble2 = new double[i1];
/* 2703 */       paramInt3 = 0; }
/*      */     Object localObject;
/* 2705 */     int n; if (j == 0) {
/* 2706 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt3, i1);
/* 2707 */       localObject = paramArrayOfDouble1;
/* 2708 */       n = 0;
/* 2709 */       paramArrayOfDouble1 = paramArrayOfDouble2;
/* 2710 */       m = paramInt3 - paramInt1;
/*      */     } else {
/* 2712 */       localObject = paramArrayOfDouble2;
/* 2713 */       m = 0;
/* 2714 */       n = paramInt3 - paramInt1;
/*      */     }
/*      */     int i2;
/* 2718 */     for (; 
/* 2718 */         i > 1; i = i2) {
/* 2719 */       for (int i3 = (i2 = 0) + 2; i3 <= i; i3 += 2) {
/* 2720 */         i4 = arrayOfInt[i3];int i5 = arrayOfInt[(i3 - 1)];
/* 2721 */         int i6 = arrayOfInt[(i3 - 2)];int i7 = i6; for (int i8 = i5; i6 < i4; i6++) {
/* 2722 */           if ((i8 >= i4) || ((i7 < i5) && (paramArrayOfDouble1[(i7 + m)] <= paramArrayOfDouble1[(i8 + m)]))) {
/* 2723 */             localObject[(i6 + n)] = paramArrayOfDouble1[(i7++ + m)];
/*      */           } else {
/* 2725 */             localObject[(i6 + n)] = paramArrayOfDouble1[(i8++ + m)];
/*      */           }
/*      */         }
/* 2728 */         arrayOfInt[(++i2)] = i4;
/*      */       }
/* 2730 */       if ((i & 0x1) != 0) {
/* 2731 */         i3 = paramInt2;i4 = arrayOfInt[(i - 1)]; for (;;) { i3--; if (i3 < i4) break;
/* 2732 */           localObject[(i3 + n)] = paramArrayOfDouble1[(i3 + m)];
/*      */         }
/* 2734 */         arrayOfInt[(++i2)] = paramInt2;
/*      */       }
/* 2736 */       double[] arrayOfDouble = paramArrayOfDouble1;paramArrayOfDouble1 = (double[])localObject;localObject = arrayOfDouble;
/* 2737 */       int i4 = m;m = n;n = i4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void sort(double[] paramArrayOfDouble, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 2750 */     int i = paramInt2 - paramInt1 + 1;
/*      */     
/*      */ 
/* 2753 */     if (i < 47) { int j;
/* 2754 */       if (paramBoolean)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2760 */         j = paramInt1; for (int m = j; j < paramInt2; m = j) {
/* 2761 */           double d3 = paramArrayOfDouble[(j + 1)];
/* 2762 */           while (d3 < paramArrayOfDouble[m]) {
/* 2763 */             paramArrayOfDouble[(m + 1)] = paramArrayOfDouble[m];
/* 2764 */             if (m-- == paramInt1) {
/*      */               break;
/*      */             }
/*      */           }
/* 2768 */           paramArrayOfDouble[(m + 1)] = d3;j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/* 2775 */           if (paramInt1 >= paramInt2) {
/* 2776 */             return;
/*      */           }
/* 2778 */         } while (paramArrayOfDouble[(++paramInt1)] >= paramArrayOfDouble[(paramInt1 - 1)]);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2788 */         for (j = paramInt1;; j = paramInt1) { paramInt1++; if (paramInt1 > paramInt2) break;
/* 2789 */           double d2 = paramArrayOfDouble[j];double d4 = paramArrayOfDouble[paramInt1];
/*      */           
/* 2791 */           if (d2 < d4) {
/* 2792 */             d4 = d2;d2 = paramArrayOfDouble[paramInt1];
/*      */           }
/* 2794 */           while (d2 < paramArrayOfDouble[(--j)]) {
/* 2795 */             paramArrayOfDouble[(j + 2)] = paramArrayOfDouble[j];
/*      */           }
/* 2797 */           paramArrayOfDouble[(++j + 1)] = d2;
/*      */           
/* 2799 */           while (d4 < paramArrayOfDouble[(--j)]) {
/* 2800 */             paramArrayOfDouble[(j + 1)] = paramArrayOfDouble[j];
/*      */           }
/* 2802 */           paramArrayOfDouble[(j + 1)] = d4;paramInt1++;
/*      */         }
/* 2804 */         double d1 = paramArrayOfDouble[paramInt2];
/*      */         
/* 2806 */         while (d1 < paramArrayOfDouble[(--paramInt2)]) {
/* 2807 */           paramArrayOfDouble[(paramInt2 + 1)] = paramArrayOfDouble[paramInt2];
/*      */         }
/* 2809 */         paramArrayOfDouble[(paramInt2 + 1)] = d1;
/*      */       }
/* 2811 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2815 */     int k = (i >> 3) + (i >> 6) + 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2824 */     int n = paramInt1 + paramInt2 >>> 1;
/* 2825 */     int i1 = n - k;
/* 2826 */     int i2 = i1 - k;
/* 2827 */     int i3 = n + k;
/* 2828 */     int i4 = i3 + k;
/*      */     
/*      */     double d5;
/* 2831 */     if (paramArrayOfDouble[i1] < paramArrayOfDouble[i2]) { d5 = paramArrayOfDouble[i1];paramArrayOfDouble[i1] = paramArrayOfDouble[i2];paramArrayOfDouble[i2] = d5;
/*      */     }
/* 2833 */     if (paramArrayOfDouble[n] < paramArrayOfDouble[i1]) { d5 = paramArrayOfDouble[n];paramArrayOfDouble[n] = paramArrayOfDouble[i1];paramArrayOfDouble[i1] = d5;
/* 2834 */       if (d5 < paramArrayOfDouble[i2]) { paramArrayOfDouble[i1] = paramArrayOfDouble[i2];paramArrayOfDouble[i2] = d5;
/*      */       } }
/* 2836 */     if (paramArrayOfDouble[i3] < paramArrayOfDouble[n]) { d5 = paramArrayOfDouble[i3];paramArrayOfDouble[i3] = paramArrayOfDouble[n];paramArrayOfDouble[n] = d5;
/* 2837 */       if (d5 < paramArrayOfDouble[i1]) { paramArrayOfDouble[n] = paramArrayOfDouble[i1];paramArrayOfDouble[i1] = d5;
/* 2838 */         if (d5 < paramArrayOfDouble[i2]) { paramArrayOfDouble[i1] = paramArrayOfDouble[i2];paramArrayOfDouble[i2] = d5;
/*      */         }
/*      */       } }
/* 2841 */     if (paramArrayOfDouble[i4] < paramArrayOfDouble[i3]) { d5 = paramArrayOfDouble[i4];paramArrayOfDouble[i4] = paramArrayOfDouble[i3];paramArrayOfDouble[i3] = d5;
/* 2842 */       if (d5 < paramArrayOfDouble[n]) { paramArrayOfDouble[i3] = paramArrayOfDouble[n];paramArrayOfDouble[n] = d5;
/* 2843 */         if (d5 < paramArrayOfDouble[i1]) { paramArrayOfDouble[n] = paramArrayOfDouble[i1];paramArrayOfDouble[i1] = d5;
/* 2844 */           if (d5 < paramArrayOfDouble[i2]) { paramArrayOfDouble[i1] = paramArrayOfDouble[i2];paramArrayOfDouble[i2] = d5;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2850 */     double d6 = paramInt1;
/* 2851 */     double d7 = paramInt2;
/*      */     double d8;
/* 2853 */     double d9; if ((paramArrayOfDouble[i2] != paramArrayOfDouble[i1]) && (paramArrayOfDouble[i1] != paramArrayOfDouble[n]) && (paramArrayOfDouble[n] != paramArrayOfDouble[i3]) && (paramArrayOfDouble[i3] != paramArrayOfDouble[i4]))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2859 */       d8 = paramArrayOfDouble[i1];
/* 2860 */       d9 = paramArrayOfDouble[i3];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2868 */       paramArrayOfDouble[i1] = paramArrayOfDouble[paramInt1];
/* 2869 */       paramArrayOfDouble[i3] = paramArrayOfDouble[paramInt2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2874 */       while (paramArrayOfDouble[(++d6)] < d8) {}
/* 2875 */       while (paramArrayOfDouble[(--d7)] > d9) {}
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
/* 2897 */       double d11 = d6 - 1; double d13; for (;;) { d11++; if (d11 > d7) break;
/* 2898 */         d13 = paramArrayOfDouble[d11];
/* 2899 */         if (d13 < d8) {
/* 2900 */           paramArrayOfDouble[d11] = paramArrayOfDouble[d6];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2905 */           paramArrayOfDouble[d6] = d13;
/* 2906 */           d6++;
/* 2907 */         } else if (d13 > d9) {
/* 2908 */           while (paramArrayOfDouble[d7] > d9) {
/* 2909 */             if (d7-- == d11) {
/*      */               break label834;
/*      */             }
/*      */           }
/* 2913 */           if (paramArrayOfDouble[d7] < d8) {
/* 2914 */             paramArrayOfDouble[d11] = paramArrayOfDouble[d6];
/* 2915 */             paramArrayOfDouble[d6] = paramArrayOfDouble[d7];
/* 2916 */             d6++;
/*      */           } else {
/* 2918 */             paramArrayOfDouble[d11] = paramArrayOfDouble[d7];
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2924 */           paramArrayOfDouble[d7] = d13;
/* 2925 */           d7--;
/*      */         }
/*      */       }
/*      */       
/*      */       label834:
/* 2930 */       paramArrayOfDouble[paramInt1] = paramArrayOfDouble[(d6 - 1)]; paramArrayOfDouble[(d6 - 1)] = d8;
/* 2931 */       paramArrayOfDouble[paramInt2] = paramArrayOfDouble[(d7 + 1)];paramArrayOfDouble[(d7 + 1)] = d9;
/*      */       
/*      */ 
/* 2934 */       sort(paramArrayOfDouble, paramInt1, d6 - 2, paramBoolean);
/* 2935 */       sort(paramArrayOfDouble, d7 + 2, paramInt2, false);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2941 */       if ((d6 < i2) && (i4 < d7))
/*      */       {
/*      */ 
/*      */ 
/* 2945 */         while (paramArrayOfDouble[d6] == d8) {
/* 2946 */           d6++;
/*      */         }
/*      */         
/* 2949 */         while (paramArrayOfDouble[d7] == d9) {
/* 2950 */           d7--;
/*      */         }
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
/* 2973 */         double d12 = d6 - 1; for (;;) { d12++; if (d12 > d7) break;
/* 2974 */           d13 = paramArrayOfDouble[d12];
/* 2975 */           if (d13 == d8) {
/* 2976 */             paramArrayOfDouble[d12] = paramArrayOfDouble[d6];
/* 2977 */             paramArrayOfDouble[d6] = d13;
/* 2978 */             d6++;
/* 2979 */           } else if (d13 == d9) {
/* 2980 */             while (paramArrayOfDouble[d7] == d9) {
/* 2981 */               if (d7-- == d12) {
/*      */                 break label1067;
/*      */               }
/*      */             }
/* 2985 */             if (paramArrayOfDouble[d7] == d8) {
/* 2986 */               paramArrayOfDouble[d12] = paramArrayOfDouble[d6];
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2995 */               paramArrayOfDouble[d6] = paramArrayOfDouble[d7];
/* 2996 */               d6++;
/*      */             } else {
/* 2998 */               paramArrayOfDouble[d12] = paramArrayOfDouble[d7];
/*      */             }
/* 3000 */             paramArrayOfDouble[d7] = d13;
/* 3001 */             d7--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       label1067:
/* 3007 */       sort(paramArrayOfDouble, d6, d7, false);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 3014 */       d8 = paramArrayOfDouble[n];
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
/* 3036 */       for (d9 = d6; d9 <= d7; d9++) {
/* 3037 */         if (paramArrayOfDouble[d9] != d8)
/*      */         {
/*      */ 
/* 3040 */           double d10 = paramArrayOfDouble[d9];
/* 3041 */           if (d10 < d8) {
/* 3042 */             paramArrayOfDouble[d9] = paramArrayOfDouble[d6];
/* 3043 */             paramArrayOfDouble[d6] = d10;
/* 3044 */             d6++;
/*      */           } else {
/* 3046 */             while (paramArrayOfDouble[d7] > d8) {
/* 3047 */               d7--;
/*      */             }
/* 3049 */             if (paramArrayOfDouble[d7] < d8) {
/* 3050 */               paramArrayOfDouble[d9] = paramArrayOfDouble[d6];
/* 3051 */               paramArrayOfDouble[d6] = paramArrayOfDouble[d7];
/* 3052 */               d6++;
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 3062 */               paramArrayOfDouble[d9] = paramArrayOfDouble[d7];
/*      */             }
/* 3064 */             paramArrayOfDouble[d7] = d10;
/* 3065 */             d7--;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3074 */       sort(paramArrayOfDouble, paramInt1, d6 - 1, paramBoolean);
/* 3075 */       sort(paramArrayOfDouble, d7 + 1, paramInt2, false);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/DualPivotQuicksort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */