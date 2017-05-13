/*      */ package java.math;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class MutableBigInteger
/*      */ {
/*      */   int[] value;
/*      */   int intLen;
/*   68 */   int offset = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   76 */   static final MutableBigInteger ONE = new MutableBigInteger(1);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int KNUTH_POW2_THRESH_LEN = 6;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int KNUTH_POW2_THRESH_ZEROS = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger()
/*      */   {
/*  103 */     this.value = new int[1];
/*  104 */     this.intLen = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger(int paramInt)
/*      */   {
/*  112 */     this.value = new int[1];
/*  113 */     this.intLen = 1;
/*  114 */     this.value[0] = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger(int[] paramArrayOfInt)
/*      */   {
/*  122 */     this.value = paramArrayOfInt;
/*  123 */     this.intLen = paramArrayOfInt.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger(BigInteger paramBigInteger)
/*      */   {
/*  131 */     this.intLen = paramBigInteger.mag.length;
/*  132 */     this.value = Arrays.copyOf(paramBigInteger.mag, this.intLen);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  140 */     this.intLen = paramMutableBigInteger.intLen;
/*  141 */     this.value = Arrays.copyOfRange(paramMutableBigInteger.value, paramMutableBigInteger.offset, paramMutableBigInteger.offset + this.intLen);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void ones(int paramInt)
/*      */   {
/*  151 */     if (paramInt > this.value.length)
/*  152 */       this.value = new int[paramInt];
/*  153 */     Arrays.fill(this.value, -1);
/*  154 */     this.offset = 0;
/*  155 */     this.intLen = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int[] getMagnitudeArray()
/*      */   {
/*  163 */     if ((this.offset > 0) || (this.value.length != this.intLen))
/*  164 */       return Arrays.copyOfRange(this.value, this.offset, this.offset + this.intLen);
/*  165 */     return this.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long toLong()
/*      */   {
/*  173 */     assert (this.intLen <= 2) : "this MutableBigInteger exceeds the range of long";
/*  174 */     if (this.intLen == 0)
/*  175 */       return 0L;
/*  176 */     long l = this.value[this.offset] & 0xFFFFFFFF;
/*  177 */     return this.intLen == 2 ? l << 32 | this.value[(this.offset + 1)] & 0xFFFFFFFF : l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   BigInteger toBigInteger(int paramInt)
/*      */   {
/*  184 */     if ((this.intLen == 0) || (paramInt == 0))
/*  185 */       return BigInteger.ZERO;
/*  186 */     return new BigInteger(getMagnitudeArray(), paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   BigInteger toBigInteger()
/*      */   {
/*  193 */     normalize();
/*  194 */     return toBigInteger(isZero() ? 0 : 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigDecimal toBigDecimal(int paramInt1, int paramInt2)
/*      */   {
/*  202 */     if ((this.intLen == 0) || (paramInt1 == 0))
/*  203 */       return BigDecimal.zeroValueOf(paramInt2);
/*  204 */     int[] arrayOfInt = getMagnitudeArray();
/*  205 */     int i = arrayOfInt.length;
/*  206 */     int j = arrayOfInt[0];
/*      */     
/*      */ 
/*  209 */     if ((i > 2) || ((j < 0) && (i == 2)))
/*  210 */       return new BigDecimal(new BigInteger(arrayOfInt, paramInt1), Long.MIN_VALUE, paramInt2, 0);
/*  211 */     long l = i == 2 ? arrayOfInt[1] & 0xFFFFFFFF | (j & 0xFFFFFFFF) << 32 : j & 0xFFFFFFFF;
/*      */     
/*      */ 
/*  214 */     return BigDecimal.valueOf(paramInt1 == -1 ? -l : l, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   long toCompactValue(int paramInt)
/*      */   {
/*  223 */     if ((this.intLen == 0) || (paramInt == 0))
/*  224 */       return 0L;
/*  225 */     int[] arrayOfInt = getMagnitudeArray();
/*  226 */     int i = arrayOfInt.length;
/*  227 */     int j = arrayOfInt[0];
/*      */     
/*      */ 
/*  230 */     if ((i > 2) || ((j < 0) && (i == 2)))
/*  231 */       return Long.MIN_VALUE;
/*  232 */     long l = i == 2 ? arrayOfInt[1] & 0xFFFFFFFF | (j & 0xFFFFFFFF) << 32 : j & 0xFFFFFFFF;
/*      */     
/*      */ 
/*  235 */     return paramInt == -1 ? -l : l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void clear()
/*      */   {
/*  242 */     this.offset = (this.intLen = 0);
/*  243 */     int i = 0; for (int j = this.value.length; i < j; i++) {
/*  244 */       this.value[i] = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   void reset()
/*      */   {
/*  251 */     this.offset = (this.intLen = 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int compare(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  260 */     int i = paramMutableBigInteger.intLen;
/*  261 */     if (this.intLen < i)
/*  262 */       return -1;
/*  263 */     if (this.intLen > i) {
/*  264 */       return 1;
/*      */     }
/*      */     
/*      */ 
/*  268 */     int[] arrayOfInt = paramMutableBigInteger.value;
/*  269 */     int j = this.offset; for (int k = paramMutableBigInteger.offset; j < this.intLen + this.offset; k++) {
/*  270 */       int m = this.value[j] + Integer.MIN_VALUE;
/*  271 */       int n = arrayOfInt[k] + Integer.MIN_VALUE;
/*  272 */       if (m < n)
/*  273 */         return -1;
/*  274 */       if (m > n) {
/*  275 */         return 1;
/*      */       }
/*  269 */       j++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  277 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int compareShifted(MutableBigInteger paramMutableBigInteger, int paramInt)
/*      */   {
/*  285 */     int i = paramMutableBigInteger.intLen;
/*  286 */     int j = this.intLen - paramInt;
/*  287 */     if (j < i)
/*  288 */       return -1;
/*  289 */     if (j > i) {
/*  290 */       return 1;
/*      */     }
/*      */     
/*      */ 
/*  294 */     int[] arrayOfInt = paramMutableBigInteger.value;
/*  295 */     int k = this.offset; for (int m = paramMutableBigInteger.offset; k < j + this.offset; m++) {
/*  296 */       int n = this.value[k] + Integer.MIN_VALUE;
/*  297 */       int i1 = arrayOfInt[m] + Integer.MIN_VALUE;
/*  298 */       if (n < i1)
/*  299 */         return -1;
/*  300 */       if (n > i1) {
/*  301 */         return 1;
/*      */       }
/*  295 */       k++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  303 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int compareHalf(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  313 */     int i = paramMutableBigInteger.intLen;
/*  314 */     int j = this.intLen;
/*  315 */     if (j <= 0)
/*  316 */       return i <= 0 ? 0 : -1;
/*  317 */     if (j > i)
/*  318 */       return 1;
/*  319 */     if (j < i - 1)
/*  320 */       return -1;
/*  321 */     int[] arrayOfInt1 = paramMutableBigInteger.value;
/*  322 */     int k = 0;
/*  323 */     int m = 0;
/*      */     
/*  325 */     if (j != i) {
/*  326 */       if (arrayOfInt1[k] == 1) {
/*  327 */         k++;
/*  328 */         m = Integer.MIN_VALUE;
/*      */       } else {
/*  330 */         return -1;
/*      */       }
/*      */     }
/*      */     
/*  334 */     int[] arrayOfInt2 = this.value;
/*  335 */     int n = this.offset; for (int i1 = k; n < j + this.offset;) {
/*  336 */       int i2 = arrayOfInt1[(i1++)];
/*  337 */       long l1 = (i2 >>> 1) + m & 0xFFFFFFFF;
/*  338 */       long l2 = arrayOfInt2[(n++)] & 0xFFFFFFFF;
/*  339 */       if (l2 != l1)
/*  340 */         return l2 < l1 ? -1 : 1;
/*  341 */       m = (i2 & 0x1) << 31;
/*      */     }
/*  343 */     return m == 0 ? 0 : -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int getLowestSetBit()
/*      */   {
/*  351 */     if (this.intLen == 0) {
/*  352 */       return -1;
/*      */     }
/*  354 */     for (int i = this.intLen - 1; (i > 0) && (this.value[(i + this.offset)] == 0); i--) {}
/*      */     
/*  356 */     int j = this.value[(i + this.offset)];
/*  357 */     if (j == 0)
/*  358 */       return -1;
/*  359 */     return (this.intLen - 1 - i << 5) + Integer.numberOfTrailingZeros(j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int getInt(int paramInt)
/*      */   {
/*  368 */     return this.value[(this.offset + paramInt)];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final long getLong(int paramInt)
/*      */   {
/*  377 */     return this.value[(this.offset + paramInt)] & 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void normalize()
/*      */   {
/*  386 */     if (this.intLen == 0) {
/*  387 */       this.offset = 0;
/*  388 */       return;
/*      */     }
/*      */     
/*  391 */     int i = this.offset;
/*  392 */     if (this.value[i] != 0) {
/*  393 */       return;
/*      */     }
/*  395 */     int j = i + this.intLen;
/*      */     do {
/*  397 */       i++;
/*  398 */     } while ((i < j) && (this.value[i] == 0));
/*      */     
/*  400 */     int k = i - this.offset;
/*  401 */     this.intLen -= k;
/*  402 */     this.offset = (this.intLen == 0 ? 0 : this.offset + k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void ensureCapacity(int paramInt)
/*      */   {
/*  410 */     if (this.value.length < paramInt) {
/*  411 */       this.value = new int[paramInt];
/*  412 */       this.offset = 0;
/*  413 */       this.intLen = paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   int[] toIntArray()
/*      */   {
/*  422 */     int[] arrayOfInt = new int[this.intLen];
/*  423 */     for (int i = 0; i < this.intLen; i++)
/*  424 */       arrayOfInt[i] = this.value[(this.offset + i)];
/*  425 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setInt(int paramInt1, int paramInt2)
/*      */   {
/*  434 */     this.value[(this.offset + paramInt1)] = paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void setValue(int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  442 */     this.value = paramArrayOfInt;
/*  443 */     this.intLen = paramInt;
/*  444 */     this.offset = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void copyValue(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  452 */     int i = paramMutableBigInteger.intLen;
/*  453 */     if (this.value.length < i)
/*  454 */       this.value = new int[i];
/*  455 */     System.arraycopy(paramMutableBigInteger.value, paramMutableBigInteger.offset, this.value, 0, i);
/*  456 */     this.intLen = i;
/*  457 */     this.offset = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void copyValue(int[] paramArrayOfInt)
/*      */   {
/*  465 */     int i = paramArrayOfInt.length;
/*  466 */     if (this.value.length < i)
/*  467 */       this.value = new int[i];
/*  468 */     System.arraycopy(paramArrayOfInt, 0, this.value, 0, i);
/*  469 */     this.intLen = i;
/*  470 */     this.offset = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   boolean isOne()
/*      */   {
/*  477 */     return (this.intLen == 1) && (this.value[this.offset] == 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   boolean isZero()
/*      */   {
/*  484 */     return this.intLen == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   boolean isEven()
/*      */   {
/*  491 */     return (this.intLen == 0) || ((this.value[(this.offset + this.intLen - 1)] & 0x1) == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   boolean isOdd()
/*      */   {
/*  498 */     return !isZero();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isNormal()
/*      */   {
/*  507 */     if (this.intLen + this.offset > this.value.length)
/*  508 */       return false;
/*  509 */     if (this.intLen == 0)
/*  510 */       return true;
/*  511 */     return this.value[this.offset] != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  518 */     BigInteger localBigInteger = toBigInteger(1);
/*  519 */     return localBigInteger.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void safeRightShift(int paramInt)
/*      */   {
/*  526 */     if (paramInt / 32 >= this.intLen) {
/*  527 */       reset();
/*      */     } else {
/*  529 */       rightShift(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void rightShift(int paramInt)
/*      */   {
/*  538 */     if (this.intLen == 0)
/*  539 */       return;
/*  540 */     int i = paramInt >>> 5;
/*  541 */     int j = paramInt & 0x1F;
/*  542 */     this.intLen -= i;
/*  543 */     if (j == 0)
/*  544 */       return;
/*  545 */     int k = BigInteger.bitLengthForInt(this.value[this.offset]);
/*  546 */     if (j >= k) {
/*  547 */       primitiveLeftShift(32 - j);
/*  548 */       this.intLen -= 1;
/*      */     } else {
/*  550 */       primitiveRightShift(j);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void safeLeftShift(int paramInt)
/*      */   {
/*  558 */     if (paramInt > 0) {
/*  559 */       leftShift(paramInt);
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
/*      */   void leftShift(int paramInt)
/*      */   {
/*  573 */     if (this.intLen == 0)
/*  574 */       return;
/*  575 */     int i = paramInt >>> 5;
/*  576 */     int j = paramInt & 0x1F;
/*  577 */     int k = BigInteger.bitLengthForInt(this.value[this.offset]);
/*      */     
/*      */ 
/*  580 */     if (paramInt <= 32 - k) {
/*  581 */       primitiveLeftShift(j);
/*  582 */       return;
/*      */     }
/*      */     
/*  585 */     int m = this.intLen + i + 1;
/*  586 */     if (j <= 32 - k)
/*  587 */       m--;
/*  588 */     if (this.value.length < m)
/*      */     {
/*  590 */       int[] arrayOfInt = new int[m];
/*  591 */       for (int i1 = 0; i1 < this.intLen; i1++)
/*  592 */         arrayOfInt[i1] = this.value[(this.offset + i1)];
/*  593 */       setValue(arrayOfInt, m); } else { int n;
/*  594 */       if (this.value.length - this.offset >= m)
/*      */       {
/*  596 */         for (n = 0; n < m - this.intLen; n++) {
/*  597 */           this.value[(this.offset + this.intLen + n)] = 0;
/*      */         }
/*      */       } else {
/*  600 */         for (n = 0; n < this.intLen; n++)
/*  601 */           this.value[n] = this.value[(this.offset + n)];
/*  602 */         for (n = this.intLen; n < m; n++)
/*  603 */           this.value[n] = 0;
/*  604 */         this.offset = 0;
/*      */       } }
/*  606 */     this.intLen = m;
/*  607 */     if (j == 0)
/*  608 */       return;
/*  609 */     if (j <= 32 - k) {
/*  610 */       primitiveLeftShift(j);
/*      */     } else {
/*  612 */       primitiveRightShift(32 - j);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int divadd(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*      */   {
/*  621 */     long l1 = 0L;
/*      */     
/*  623 */     for (int i = paramArrayOfInt1.length - 1; i >= 0; i--) {
/*  624 */       long l2 = (paramArrayOfInt1[i] & 0xFFFFFFFF) + (paramArrayOfInt2[(i + paramInt)] & 0xFFFFFFFF) + l1;
/*      */       
/*  626 */       paramArrayOfInt2[(i + paramInt)] = ((int)l2);
/*  627 */       l1 = l2 >>> 32;
/*      */     }
/*  629 */     return (int)l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int mulsub(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  638 */     long l1 = paramInt1 & 0xFFFFFFFF;
/*  639 */     long l2 = 0L;
/*  640 */     paramInt3 += paramInt2;
/*      */     
/*  642 */     for (int i = paramInt2 - 1; i >= 0; i--) {
/*  643 */       long l3 = (paramArrayOfInt2[i] & 0xFFFFFFFF) * l1 + l2;
/*  644 */       long l4 = paramArrayOfInt1[paramInt3] - l3;
/*  645 */       paramArrayOfInt1[(paramInt3--)] = ((int)l4);
/*  646 */       l2 = (l3 >>> 32) + ((l4 & 0xFFFFFFFF) > (((int)l3 ^ 0xFFFFFFFF) & 0xFFFFFFFF) ? 1 : 0);
/*      */     }
/*      */     
/*      */ 
/*  650 */     return (int)l2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int mulsubBorrow(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  658 */     long l1 = paramInt1 & 0xFFFFFFFF;
/*  659 */     long l2 = 0L;
/*  660 */     paramInt3 += paramInt2;
/*  661 */     for (int i = paramInt2 - 1; i >= 0; i--) {
/*  662 */       long l3 = (paramArrayOfInt2[i] & 0xFFFFFFFF) * l1 + l2;
/*  663 */       long l4 = paramArrayOfInt1[(paramInt3--)] - l3;
/*  664 */       l2 = (l3 >>> 32) + ((l4 & 0xFFFFFFFF) > (((int)l3 ^ 0xFFFFFFFF) & 0xFFFFFFFF) ? 1 : 0);
/*      */     }
/*      */     
/*      */ 
/*  668 */     return (int)l2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void primitiveRightShift(int paramInt)
/*      */   {
/*  677 */     int[] arrayOfInt = this.value;
/*  678 */     int i = 32 - paramInt;
/*  679 */     int j = this.offset + this.intLen - 1; for (int k = arrayOfInt[j]; j > this.offset; j--) {
/*  680 */       int m = k;
/*  681 */       k = arrayOfInt[(j - 1)];
/*  682 */       arrayOfInt[j] = (k << i | m >>> paramInt);
/*      */     }
/*  684 */     arrayOfInt[this.offset] >>>= paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void primitiveLeftShift(int paramInt)
/*      */   {
/*  693 */     int[] arrayOfInt = this.value;
/*  694 */     int i = 32 - paramInt;
/*  695 */     int j = this.offset;int k = arrayOfInt[j]; for (int m = j + this.intLen - 1; j < m; j++) {
/*  696 */       int n = k;
/*  697 */       k = arrayOfInt[(j + 1)];
/*  698 */       arrayOfInt[j] = (n << paramInt | k >>> i);
/*      */     }
/*  700 */     arrayOfInt[(this.offset + this.intLen - 1)] <<= paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getLower(int paramInt)
/*      */   {
/*  708 */     if (isZero())
/*  709 */       return BigInteger.ZERO;
/*  710 */     if (this.intLen < paramInt) {
/*  711 */       return toBigInteger(1);
/*      */     }
/*      */     
/*  714 */     int i = paramInt;
/*  715 */     while ((i > 0) && (this.value[(this.offset + this.intLen - i)] == 0))
/*  716 */       i--;
/*  717 */     int j = i > 0 ? 1 : 0;
/*  718 */     return new BigInteger(Arrays.copyOfRange(this.value, this.offset + this.intLen - i, this.offset + this.intLen), j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void keepLower(int paramInt)
/*      */   {
/*  726 */     if (this.intLen >= paramInt) {
/*  727 */       this.offset += this.intLen - paramInt;
/*  728 */       this.intLen = paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void add(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  738 */     int i = this.intLen;
/*  739 */     int j = paramMutableBigInteger.intLen;
/*  740 */     int k = this.intLen > paramMutableBigInteger.intLen ? this.intLen : paramMutableBigInteger.intLen;
/*  741 */     Object localObject = this.value.length < k ? new int[k] : this.value;
/*      */     
/*  743 */     int m = localObject.length - 1;
/*      */     
/*  745 */     long l2 = 0L;
/*      */     
/*      */     long l1;
/*  748 */     while ((i > 0) && (j > 0)) {
/*  749 */       i--;j--;
/*  750 */       l1 = (this.value[(i + this.offset)] & 0xFFFFFFFF) + (paramMutableBigInteger.value[(j + paramMutableBigInteger.offset)] & 0xFFFFFFFF) + l2;
/*      */       
/*  752 */       localObject[(m--)] = ((int)l1);
/*  753 */       l2 = l1 >>> 32;
/*      */     }
/*      */     
/*      */ 
/*  757 */     while (i > 0) {
/*  758 */       i--;
/*  759 */       if ((l2 == 0L) && (localObject == this.value) && (m == i + this.offset))
/*  760 */         return;
/*  761 */       l1 = (this.value[(i + this.offset)] & 0xFFFFFFFF) + l2;
/*  762 */       localObject[(m--)] = ((int)l1);
/*  763 */       l2 = l1 >>> 32;
/*      */     }
/*  765 */     while (j > 0) {
/*  766 */       j--;
/*  767 */       l1 = (paramMutableBigInteger.value[(j + paramMutableBigInteger.offset)] & 0xFFFFFFFF) + l2;
/*  768 */       localObject[(m--)] = ((int)l1);
/*  769 */       l2 = l1 >>> 32;
/*      */     }
/*      */     
/*  772 */     if (l2 > 0L) {
/*  773 */       k++;
/*  774 */       if (localObject.length < k) {
/*  775 */         int[] arrayOfInt = new int[k];
/*      */         
/*      */ 
/*  778 */         System.arraycopy(localObject, 0, arrayOfInt, 1, localObject.length);
/*  779 */         arrayOfInt[0] = 1;
/*  780 */         localObject = arrayOfInt;
/*      */       } else {
/*  782 */         localObject[(m--)] = 1;
/*      */       }
/*      */     }
/*      */     
/*  786 */     this.value = ((int[])localObject);
/*  787 */     this.intLen = k;
/*  788 */     this.offset = (localObject.length - k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addShifted(MutableBigInteger paramMutableBigInteger, int paramInt)
/*      */   {
/*  797 */     if (paramMutableBigInteger.isZero()) {
/*  798 */       return;
/*      */     }
/*      */     
/*  801 */     int i = this.intLen;
/*  802 */     int j = paramMutableBigInteger.intLen + paramInt;
/*  803 */     int k = this.intLen > j ? this.intLen : j;
/*  804 */     Object localObject = this.value.length < k ? new int[k] : this.value;
/*      */     
/*  806 */     int m = localObject.length - 1;
/*      */     
/*  808 */     long l2 = 0L;
/*      */     int n;
/*      */     long l1;
/*  811 */     while ((i > 0) && (j > 0)) {
/*  812 */       i--;j--;
/*  813 */       n = j + paramMutableBigInteger.offset < paramMutableBigInteger.value.length ? paramMutableBigInteger.value[(j + paramMutableBigInteger.offset)] : 0;
/*  814 */       l1 = (this.value[(i + this.offset)] & 0xFFFFFFFF) + (n & 0xFFFFFFFF) + l2;
/*      */       
/*  816 */       localObject[(m--)] = ((int)l1);
/*  817 */       l2 = l1 >>> 32;
/*      */     }
/*      */     
/*      */ 
/*  821 */     while (i > 0) {
/*  822 */       i--;
/*  823 */       if ((l2 == 0L) && (localObject == this.value) && (m == i + this.offset)) {
/*  824 */         return;
/*      */       }
/*  826 */       l1 = (this.value[(i + this.offset)] & 0xFFFFFFFF) + l2;
/*  827 */       localObject[(m--)] = ((int)l1);
/*  828 */       l2 = l1 >>> 32;
/*      */     }
/*  830 */     while (j > 0) {
/*  831 */       j--;
/*  832 */       n = j + paramMutableBigInteger.offset < paramMutableBigInteger.value.length ? paramMutableBigInteger.value[(j + paramMutableBigInteger.offset)] : 0;
/*  833 */       l1 = (n & 0xFFFFFFFF) + l2;
/*  834 */       localObject[(m--)] = ((int)l1);
/*  835 */       l2 = l1 >>> 32;
/*      */     }
/*      */     
/*  838 */     if (l2 > 0L) {
/*  839 */       k++;
/*  840 */       if (localObject.length < k) {
/*  841 */         int[] arrayOfInt = new int[k];
/*      */         
/*      */ 
/*  844 */         System.arraycopy(localObject, 0, arrayOfInt, 1, localObject.length);
/*  845 */         arrayOfInt[0] = 1;
/*  846 */         localObject = arrayOfInt;
/*      */       } else {
/*  848 */         localObject[(m--)] = 1;
/*      */       }
/*      */     }
/*      */     
/*  852 */     this.value = ((int[])localObject);
/*  853 */     this.intLen = k;
/*  854 */     this.offset = (localObject.length - k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addDisjoint(MutableBigInteger paramMutableBigInteger, int paramInt)
/*      */   {
/*  863 */     if (paramMutableBigInteger.isZero()) {
/*  864 */       return;
/*      */     }
/*  866 */     int i = this.intLen;
/*  867 */     int j = paramMutableBigInteger.intLen + paramInt;
/*  868 */     int k = this.intLen > j ? this.intLen : j;
/*      */     int[] arrayOfInt;
/*  870 */     if (this.value.length < k) {
/*  871 */       arrayOfInt = new int[k];
/*      */     } else {
/*  873 */       arrayOfInt = this.value;
/*  874 */       Arrays.fill(this.value, this.offset + this.intLen, this.value.length, 0);
/*      */     }
/*      */     
/*  877 */     int m = arrayOfInt.length - 1;
/*      */     
/*      */ 
/*  880 */     System.arraycopy(this.value, this.offset, arrayOfInt, m + 1 - i, i);
/*  881 */     j -= i;
/*  882 */     m -= i;
/*      */     
/*  884 */     int n = Math.min(j, paramMutableBigInteger.value.length - paramMutableBigInteger.offset);
/*  885 */     System.arraycopy(paramMutableBigInteger.value, paramMutableBigInteger.offset, arrayOfInt, m + 1 - j, n);
/*      */     
/*      */ 
/*  888 */     for (int i1 = m + 1 - j + n; i1 < m + 1; i1++) {
/*  889 */       arrayOfInt[i1] = 0;
/*      */     }
/*  891 */     this.value = arrayOfInt;
/*  892 */     this.intLen = k;
/*  893 */     this.offset = (arrayOfInt.length - k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void addLower(MutableBigInteger paramMutableBigInteger, int paramInt)
/*      */   {
/*  900 */     MutableBigInteger localMutableBigInteger = new MutableBigInteger(paramMutableBigInteger);
/*  901 */     if (localMutableBigInteger.offset + localMutableBigInteger.intLen >= paramInt) {
/*  902 */       localMutableBigInteger.offset = (localMutableBigInteger.offset + localMutableBigInteger.intLen - paramInt);
/*  903 */       localMutableBigInteger.intLen = paramInt;
/*      */     }
/*  905 */     localMutableBigInteger.normalize();
/*  906 */     add(localMutableBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   int subtract(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  914 */     MutableBigInteger localMutableBigInteger1 = this;
/*      */     
/*  916 */     int[] arrayOfInt = this.value;
/*  917 */     int i = localMutableBigInteger1.compare(paramMutableBigInteger);
/*      */     
/*  919 */     if (i == 0) {
/*  920 */       reset();
/*  921 */       return 0;
/*      */     }
/*  923 */     if (i < 0) {
/*  924 */       MutableBigInteger localMutableBigInteger2 = localMutableBigInteger1;
/*  925 */       localMutableBigInteger1 = paramMutableBigInteger;
/*  926 */       paramMutableBigInteger = localMutableBigInteger2;
/*      */     }
/*      */     
/*  929 */     int j = localMutableBigInteger1.intLen;
/*  930 */     if (arrayOfInt.length < j) {
/*  931 */       arrayOfInt = new int[j];
/*      */     }
/*  933 */     long l = 0L;
/*  934 */     int k = localMutableBigInteger1.intLen;
/*  935 */     int m = paramMutableBigInteger.intLen;
/*  936 */     int n = arrayOfInt.length - 1;
/*      */     
/*      */ 
/*  939 */     while (m > 0) {
/*  940 */       k--;m--;
/*      */       
/*  942 */       l = (localMutableBigInteger1.value[(k + localMutableBigInteger1.offset)] & 0xFFFFFFFF) - (paramMutableBigInteger.value[(m + paramMutableBigInteger.offset)] & 0xFFFFFFFF) - (int)-(l >> 32);
/*      */       
/*  944 */       arrayOfInt[(n--)] = ((int)l);
/*      */     }
/*      */     
/*  947 */     while (k > 0) {
/*  948 */       k--;
/*  949 */       l = (localMutableBigInteger1.value[(k + localMutableBigInteger1.offset)] & 0xFFFFFFFF) - (int)-(l >> 32);
/*  950 */       arrayOfInt[(n--)] = ((int)l);
/*      */     }
/*      */     
/*  953 */     this.value = arrayOfInt;
/*  954 */     this.intLen = j;
/*  955 */     this.offset = (this.value.length - j);
/*  956 */     normalize();
/*  957 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int difference(MutableBigInteger paramMutableBigInteger)
/*      */   {
/*  966 */     MutableBigInteger localMutableBigInteger1 = this;
/*  967 */     int i = localMutableBigInteger1.compare(paramMutableBigInteger);
/*  968 */     if (i == 0)
/*  969 */       return 0;
/*  970 */     if (i < 0) {
/*  971 */       MutableBigInteger localMutableBigInteger2 = localMutableBigInteger1;
/*  972 */       localMutableBigInteger1 = paramMutableBigInteger;
/*  973 */       paramMutableBigInteger = localMutableBigInteger2;
/*      */     }
/*      */     
/*  976 */     long l = 0L;
/*  977 */     int j = localMutableBigInteger1.intLen;
/*  978 */     int k = paramMutableBigInteger.intLen;
/*      */     
/*      */ 
/*  981 */     while (k > 0) {
/*  982 */       j--;k--;
/*  983 */       l = (localMutableBigInteger1.value[(localMutableBigInteger1.offset + j)] & 0xFFFFFFFF) - (paramMutableBigInteger.value[(paramMutableBigInteger.offset + k)] & 0xFFFFFFFF) - (int)-(l >> 32);
/*      */       
/*  985 */       localMutableBigInteger1.value[(localMutableBigInteger1.offset + j)] = ((int)l);
/*      */     }
/*      */     
/*  988 */     while (j > 0) {
/*  989 */       j--;
/*  990 */       l = (localMutableBigInteger1.value[(localMutableBigInteger1.offset + j)] & 0xFFFFFFFF) - (int)-(l >> 32);
/*  991 */       localMutableBigInteger1.value[(localMutableBigInteger1.offset + j)] = ((int)l);
/*      */     }
/*      */     
/*  994 */     localMutableBigInteger1.normalize();
/*  995 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void multiply(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2)
/*      */   {
/* 1003 */     int i = this.intLen;
/* 1004 */     int j = paramMutableBigInteger1.intLen;
/* 1005 */     int k = i + j;
/*      */     
/*      */ 
/* 1008 */     if (paramMutableBigInteger2.value.length < k)
/* 1009 */       paramMutableBigInteger2.value = new int[k];
/* 1010 */     paramMutableBigInteger2.offset = 0;
/* 1011 */     paramMutableBigInteger2.intLen = k;
/*      */     
/*      */ 
/* 1014 */     long l1 = 0L;
/* 1015 */     int m = j - 1; for (int n = j + i - 1; m >= 0; n--) {
/* 1016 */       long l2 = (paramMutableBigInteger1.value[(m + paramMutableBigInteger1.offset)] & 0xFFFFFFFF) * (this.value[(i - 1 + this.offset)] & 0xFFFFFFFF) + l1;
/*      */       
/* 1018 */       paramMutableBigInteger2.value[n] = ((int)l2);
/* 1019 */       l1 = l2 >>> 32;m--;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1021 */     paramMutableBigInteger2.value[(i - 1)] = ((int)l1);
/*      */     
/*      */ 
/* 1024 */     for (m = i - 2; m >= 0; m--) {
/* 1025 */       l1 = 0L;
/* 1026 */       n = j - 1; for (int i1 = j + m; n >= 0; i1--) {
/* 1027 */         long l3 = (paramMutableBigInteger1.value[(n + paramMutableBigInteger1.offset)] & 0xFFFFFFFF) * (this.value[(m + this.offset)] & 0xFFFFFFFF) + (paramMutableBigInteger2.value[i1] & 0xFFFFFFFF) + l1;
/*      */         
/*      */ 
/* 1030 */         paramMutableBigInteger2.value[i1] = ((int)l3);
/* 1031 */         l1 = l3 >>> 32;n--;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1033 */       paramMutableBigInteger2.value[m] = ((int)l1);
/*      */     }
/*      */     
/*      */ 
/* 1037 */     paramMutableBigInteger2.normalize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void mul(int paramInt, MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1045 */     if (paramInt == 1) {
/* 1046 */       paramMutableBigInteger.copyValue(this);
/* 1047 */       return;
/*      */     }
/*      */     
/* 1050 */     if (paramInt == 0) {
/* 1051 */       paramMutableBigInteger.clear();
/* 1052 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1056 */     long l1 = paramInt & 0xFFFFFFFF;
/* 1057 */     int[] arrayOfInt = paramMutableBigInteger.value.length < this.intLen + 1 ? new int[this.intLen + 1] : paramMutableBigInteger.value;
/*      */     
/* 1059 */     long l2 = 0L;
/* 1060 */     for (int i = this.intLen - 1; i >= 0; i--) {
/* 1061 */       long l3 = l1 * (this.value[(i + this.offset)] & 0xFFFFFFFF) + l2;
/* 1062 */       arrayOfInt[(i + 1)] = ((int)l3);
/* 1063 */       l2 = l3 >>> 32;
/*      */     }
/*      */     
/* 1066 */     if (l2 == 0L) {
/* 1067 */       paramMutableBigInteger.offset = 1;
/* 1068 */       paramMutableBigInteger.intLen = this.intLen;
/*      */     } else {
/* 1070 */       paramMutableBigInteger.offset = 0;
/* 1071 */       this.intLen += 1;
/* 1072 */       arrayOfInt[0] = ((int)l2);
/*      */     }
/* 1074 */     paramMutableBigInteger.value = arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int divideOneWord(int paramInt, MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1086 */     long l1 = paramInt & 0xFFFFFFFF;
/*      */     
/*      */ 
/* 1089 */     if (this.intLen == 1) {
/* 1090 */       long l2 = this.value[this.offset] & 0xFFFFFFFF;
/* 1091 */       int k = (int)(l2 / l1);
/* 1092 */       int m = (int)(l2 - k * l1);
/* 1093 */       paramMutableBigInteger.value[0] = k;
/* 1094 */       paramMutableBigInteger.intLen = (k == 0 ? 0 : 1);
/* 1095 */       paramMutableBigInteger.offset = 0;
/* 1096 */       return m;
/*      */     }
/*      */     
/* 1099 */     if (paramMutableBigInteger.value.length < this.intLen)
/* 1100 */       paramMutableBigInteger.value = new int[this.intLen];
/* 1101 */     paramMutableBigInteger.offset = 0;
/* 1102 */     paramMutableBigInteger.intLen = this.intLen;
/*      */     
/*      */ 
/* 1105 */     int i = Integer.numberOfLeadingZeros(paramInt);
/*      */     
/* 1107 */     int j = this.value[this.offset];
/* 1108 */     long l3 = j & 0xFFFFFFFF;
/* 1109 */     if (l3 < l1) {
/* 1110 */       paramMutableBigInteger.value[0] = 0;
/*      */     } else {
/* 1112 */       paramMutableBigInteger.value[0] = ((int)(l3 / l1));
/* 1113 */       j = (int)(l3 - paramMutableBigInteger.value[0] * l1);
/* 1114 */       l3 = j & 0xFFFFFFFF;
/*      */     }
/* 1116 */     int n = this.intLen;
/* 1117 */     for (;;) { n--; if (n <= 0) break;
/* 1118 */       long l4 = l3 << 32 | this.value[(this.offset + this.intLen - n)] & 0xFFFFFFFF;
/*      */       
/*      */       int i1;
/* 1121 */       if (l4 >= 0L) {
/* 1122 */         i1 = (int)(l4 / l1);
/* 1123 */         j = (int)(l4 - i1 * l1);
/*      */       } else {
/* 1125 */         long l5 = divWord(l4, paramInt);
/* 1126 */         i1 = (int)(l5 & 0xFFFFFFFF);
/* 1127 */         j = (int)(l5 >>> 32);
/*      */       }
/* 1129 */       paramMutableBigInteger.value[(this.intLen - n)] = i1;
/* 1130 */       l3 = j & 0xFFFFFFFF;
/*      */     }
/*      */     
/* 1133 */     paramMutableBigInteger.normalize();
/*      */     
/* 1135 */     if (i > 0) {
/* 1136 */       return j % paramInt;
/*      */     }
/* 1138 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger divide(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2)
/*      */   {
/* 1147 */     return divide(paramMutableBigInteger1, paramMutableBigInteger2, true);
/*      */   }
/*      */   
/*      */   MutableBigInteger divide(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, boolean paramBoolean) {
/* 1151 */     if ((paramMutableBigInteger1.intLen < 80) || (this.intLen - paramMutableBigInteger1.intLen < 40))
/*      */     {
/* 1153 */       return divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2, paramBoolean);
/*      */     }
/* 1155 */     return divideAndRemainderBurnikelZiegler(paramMutableBigInteger1, paramMutableBigInteger2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger divideKnuth(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2)
/*      */   {
/* 1163 */     return divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2, true);
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
/*      */   MutableBigInteger divideKnuth(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, boolean paramBoolean)
/*      */   {
/* 1178 */     if (paramMutableBigInteger1.intLen == 0) {
/* 1179 */       throw new ArithmeticException("BigInteger divide by zero");
/*      */     }
/*      */     
/* 1182 */     if (this.intLen == 0) {
/* 1183 */       paramMutableBigInteger2.intLen = (paramMutableBigInteger2.offset = 0);
/* 1184 */       return paramBoolean ? new MutableBigInteger() : null;
/*      */     }
/*      */     
/* 1187 */     int i = compare(paramMutableBigInteger1);
/*      */     
/* 1189 */     if (i < 0) {
/* 1190 */       paramMutableBigInteger2.intLen = (paramMutableBigInteger2.offset = 0);
/* 1191 */       return paramBoolean ? new MutableBigInteger(this) : null;
/*      */     }
/*      */     
/* 1194 */     if (i == 0) {
/* 1195 */       paramMutableBigInteger2.value[0] = (paramMutableBigInteger2.intLen = 1);
/* 1196 */       paramMutableBigInteger2.offset = 0;
/* 1197 */       return paramBoolean ? new MutableBigInteger() : null;
/*      */     }
/*      */     
/* 1200 */     paramMutableBigInteger2.clear();
/*      */     int j;
/* 1202 */     if (paramMutableBigInteger1.intLen == 1) {
/* 1203 */       j = divideOneWord(paramMutableBigInteger1.value[paramMutableBigInteger1.offset], paramMutableBigInteger2);
/* 1204 */       if (paramBoolean) {
/* 1205 */         if (j == 0)
/* 1206 */           return new MutableBigInteger();
/* 1207 */         return new MutableBigInteger(j);
/*      */       }
/* 1209 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1214 */     if (this.intLen >= 6) {
/* 1215 */       j = Math.min(getLowestSetBit(), paramMutableBigInteger1.getLowestSetBit());
/* 1216 */       if (j >= 96) {
/* 1217 */         MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(this);
/* 1218 */         paramMutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger1);
/* 1219 */         localMutableBigInteger1.rightShift(j);
/* 1220 */         paramMutableBigInteger1.rightShift(j);
/* 1221 */         MutableBigInteger localMutableBigInteger2 = localMutableBigInteger1.divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2);
/* 1222 */         localMutableBigInteger2.leftShift(j);
/* 1223 */         return localMutableBigInteger2;
/*      */       }
/*      */     }
/*      */     
/* 1227 */     return divideMagnitude(paramMutableBigInteger1, paramMutableBigInteger2, paramBoolean);
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
/*      */   MutableBigInteger divideAndRemainderBurnikelZiegler(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2)
/*      */   {
/* 1242 */     int i = this.intLen;
/* 1243 */     int j = paramMutableBigInteger1.intLen;
/*      */     
/*      */ 
/* 1246 */     paramMutableBigInteger2.offset = (paramMutableBigInteger2.intLen = 0);
/*      */     
/* 1248 */     if (i < j) {
/* 1249 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1256 */     int k = 1 << 32 - Integer.numberOfLeadingZeros(j / 80);
/*      */     
/* 1258 */     int m = (j + k - 1) / k;
/* 1259 */     int n = m * k;
/* 1260 */     long l = 32L * n;
/* 1261 */     int i1 = (int)Math.max(0L, l - paramMutableBigInteger1.bitLength());
/* 1262 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger1);
/* 1263 */     localMutableBigInteger1.safeLeftShift(i1);
/* 1264 */     safeLeftShift(i1);
/*      */     
/*      */ 
/* 1267 */     int i2 = (int)((bitLength() + l) / l);
/* 1268 */     if (i2 < 2) {
/* 1269 */       i2 = 2;
/*      */     }
/*      */     
/*      */ 
/* 1273 */     MutableBigInteger localMutableBigInteger2 = getBlock(i2 - 1, i2, n);
/*      */     
/*      */ 
/* 1276 */     MutableBigInteger localMutableBigInteger3 = getBlock(i2 - 2, i2, n);
/* 1277 */     localMutableBigInteger3.addDisjoint(localMutableBigInteger2, n);
/*      */     
/*      */ 
/* 1280 */     MutableBigInteger localMutableBigInteger4 = new MutableBigInteger();
/*      */     
/* 1282 */     for (int i3 = i2 - 2; i3 > 0; i3--)
/*      */     {
/* 1284 */       localMutableBigInteger5 = localMutableBigInteger3.divide2n1n(localMutableBigInteger1, localMutableBigInteger4);
/*      */       
/*      */ 
/* 1287 */       localMutableBigInteger3 = getBlock(i3 - 1, i2, n);
/* 1288 */       localMutableBigInteger3.addDisjoint(localMutableBigInteger5, n);
/* 1289 */       paramMutableBigInteger2.addShifted(localMutableBigInteger4, i3 * n);
/*      */     }
/*      */     
/* 1292 */     MutableBigInteger localMutableBigInteger5 = localMutableBigInteger3.divide2n1n(localMutableBigInteger1, localMutableBigInteger4);
/* 1293 */     paramMutableBigInteger2.add(localMutableBigInteger4);
/*      */     
/* 1295 */     localMutableBigInteger5.rightShift(i1);
/* 1296 */     return localMutableBigInteger5;
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
/*      */   private MutableBigInteger divide2n1n(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2)
/*      */   {
/* 1311 */     int i = paramMutableBigInteger1.intLen;
/*      */     
/*      */ 
/* 1314 */     if ((i % 2 != 0) || (i < 80)) {
/* 1315 */       return divideKnuth(paramMutableBigInteger1, paramMutableBigInteger2);
/*      */     }
/*      */     
/*      */ 
/* 1319 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(this);
/* 1320 */     localMutableBigInteger1.safeRightShift(32 * (i / 2));
/* 1321 */     keepLower(i / 2);
/*      */     
/*      */ 
/* 1324 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger();
/* 1325 */     MutableBigInteger localMutableBigInteger3 = localMutableBigInteger1.divide3n2n(paramMutableBigInteger1, localMutableBigInteger2);
/*      */     
/*      */ 
/* 1328 */     addDisjoint(localMutableBigInteger3, i / 2);
/* 1329 */     MutableBigInteger localMutableBigInteger4 = divide3n2n(paramMutableBigInteger1, paramMutableBigInteger2);
/*      */     
/*      */ 
/* 1332 */     paramMutableBigInteger2.addDisjoint(localMutableBigInteger2, i / 2);
/* 1333 */     return localMutableBigInteger4;
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
/*      */   private MutableBigInteger divide3n2n(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2)
/*      */   {
/* 1346 */     int i = paramMutableBigInteger1.intLen / 2;
/*      */     
/*      */ 
/* 1349 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(this);
/* 1350 */     localMutableBigInteger1.safeRightShift(32 * i);
/*      */     
/*      */ 
/* 1353 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramMutableBigInteger1);
/* 1354 */     localMutableBigInteger2.safeRightShift(i * 32);
/* 1355 */     BigInteger localBigInteger = paramMutableBigInteger1.getLower(i);
/*      */     
/*      */     MutableBigInteger localMutableBigInteger3;
/*      */     MutableBigInteger localMutableBigInteger4;
/* 1359 */     if (compareShifted(paramMutableBigInteger1, i) < 0)
/*      */     {
/* 1361 */       localMutableBigInteger3 = localMutableBigInteger1.divide2n1n(localMutableBigInteger2, paramMutableBigInteger2);
/*      */       
/*      */ 
/* 1364 */       localMutableBigInteger4 = new MutableBigInteger(paramMutableBigInteger2.toBigInteger().multiply(localBigInteger));
/*      */     }
/*      */     else {
/* 1367 */       paramMutableBigInteger2.ones(i);
/* 1368 */       localMutableBigInteger1.add(localMutableBigInteger2);
/* 1369 */       localMutableBigInteger2.leftShift(32 * i);
/* 1370 */       localMutableBigInteger1.subtract(localMutableBigInteger2);
/* 1371 */       localMutableBigInteger3 = localMutableBigInteger1;
/*      */       
/*      */ 
/* 1374 */       localMutableBigInteger4 = new MutableBigInteger(localBigInteger);
/* 1375 */       localMutableBigInteger4.leftShift(32 * i);
/* 1376 */       localMutableBigInteger4.subtract(new MutableBigInteger(localBigInteger));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1381 */     localMutableBigInteger3.leftShift(32 * i);
/* 1382 */     localMutableBigInteger3.addLower(this, i);
/*      */     
/*      */ 
/* 1385 */     while (localMutableBigInteger3.compare(localMutableBigInteger4) < 0) {
/* 1386 */       localMutableBigInteger3.add(paramMutableBigInteger1);
/* 1387 */       paramMutableBigInteger2.subtract(ONE);
/*      */     }
/* 1389 */     localMutableBigInteger3.subtract(localMutableBigInteger4);
/*      */     
/* 1391 */     return localMutableBigInteger3;
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
/*      */   private MutableBigInteger getBlock(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1404 */     int i = paramInt1 * paramInt3;
/* 1405 */     if (i >= this.intLen) {
/* 1406 */       return new MutableBigInteger();
/*      */     }
/*      */     
/*      */     int j;
/* 1410 */     if (paramInt1 == paramInt2 - 1) {
/* 1411 */       j = this.intLen;
/*      */     } else {
/* 1413 */       j = (paramInt1 + 1) * paramInt3;
/*      */     }
/* 1415 */     if (j > this.intLen) {
/* 1416 */       return new MutableBigInteger();
/*      */     }
/*      */     
/* 1419 */     int[] arrayOfInt = Arrays.copyOfRange(this.value, this.offset + this.intLen - j, this.offset + this.intLen - i);
/* 1420 */     return new MutableBigInteger(arrayOfInt);
/*      */   }
/*      */   
/*      */   long bitLength()
/*      */   {
/* 1425 */     if (this.intLen == 0)
/* 1426 */       return 0L;
/* 1427 */     return this.intLen * 32L - Integer.numberOfLeadingZeros(this.value[this.offset]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   long divide(long paramLong, MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1438 */     if (paramLong == 0L) {
/* 1439 */       throw new ArithmeticException("BigInteger divide by zero");
/*      */     }
/*      */     
/* 1442 */     if (this.intLen == 0) {
/* 1443 */       paramMutableBigInteger.intLen = (paramMutableBigInteger.offset = 0);
/* 1444 */       return 0L;
/*      */     }
/* 1446 */     if (paramLong < 0L) {
/* 1447 */       paramLong = -paramLong;
/*      */     }
/* 1449 */     int i = (int)(paramLong >>> 32);
/* 1450 */     paramMutableBigInteger.clear();
/*      */     
/* 1452 */     if (i == 0) {
/* 1453 */       return divideOneWord((int)paramLong, paramMutableBigInteger) & 0xFFFFFFFF;
/*      */     }
/* 1455 */     return divideLongMagnitude(paramLong, paramMutableBigInteger).toLong();
/*      */   }
/*      */   
/*      */   private static void copyAndShift(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1460 */     int i = 32 - paramInt4;
/* 1461 */     int j = paramArrayOfInt1[paramInt1];
/* 1462 */     for (int k = 0; k < paramInt2 - 1; k++) {
/* 1463 */       int m = j;
/* 1464 */       j = paramArrayOfInt1[(++paramInt1)];
/* 1465 */       paramArrayOfInt2[(paramInt3 + k)] = (m << paramInt4 | j >>> i);
/*      */     }
/* 1467 */     paramArrayOfInt2[(paramInt3 + paramInt2 - 1)] = (j << paramInt4);
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
/*      */   private MutableBigInteger divideMagnitude(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, boolean paramBoolean)
/*      */   {
/* 1480 */     int i = Integer.numberOfLeadingZeros(paramMutableBigInteger1.value[paramMutableBigInteger1.offset]);
/*      */     
/* 1482 */     int j = paramMutableBigInteger1.intLen;
/*      */     int[] arrayOfInt1;
/*      */     MutableBigInteger localMutableBigInteger;
/* 1485 */     if (i > 0) {
/* 1486 */       arrayOfInt1 = new int[j];
/* 1487 */       copyAndShift(paramMutableBigInteger1.value, paramMutableBigInteger1.offset, j, arrayOfInt1, 0, i);
/* 1488 */       int[] arrayOfInt2; if (Integer.numberOfLeadingZeros(this.value[this.offset]) >= i) {
/* 1489 */         arrayOfInt2 = new int[this.intLen + 1];
/* 1490 */         localMutableBigInteger = new MutableBigInteger(arrayOfInt2);
/* 1491 */         localMutableBigInteger.intLen = this.intLen;
/* 1492 */         localMutableBigInteger.offset = 1;
/* 1493 */         copyAndShift(this.value, this.offset, this.intLen, arrayOfInt2, 1, i);
/*      */       } else {
/* 1495 */         arrayOfInt2 = new int[this.intLen + 2];
/* 1496 */         localMutableBigInteger = new MutableBigInteger(arrayOfInt2);
/* 1497 */         this.intLen += 1;
/* 1498 */         localMutableBigInteger.offset = 1;
/* 1499 */         m = this.offset;
/* 1500 */         int n = 0;
/* 1501 */         i1 = 32 - i;
/* 1502 */         for (int i2 = 1; i2 < this.intLen + 1; m++) {
/* 1503 */           int i3 = n;
/* 1504 */           n = this.value[m];
/* 1505 */           arrayOfInt2[i2] = (i3 << i | n >>> i1);i2++;
/*      */         }
/*      */         
/*      */ 
/* 1507 */         arrayOfInt2[(this.intLen + 1)] = (n << i);
/*      */       }
/*      */     } else {
/* 1510 */       arrayOfInt1 = Arrays.copyOfRange(paramMutableBigInteger1.value, paramMutableBigInteger1.offset, paramMutableBigInteger1.offset + paramMutableBigInteger1.intLen);
/* 1511 */       localMutableBigInteger = new MutableBigInteger(new int[this.intLen + 1]);
/* 1512 */       System.arraycopy(this.value, this.offset, localMutableBigInteger.value, 1, this.intLen);
/* 1513 */       localMutableBigInteger.intLen = this.intLen;
/* 1514 */       localMutableBigInteger.offset = 1;
/*      */     }
/*      */     
/* 1517 */     int k = localMutableBigInteger.intLen;
/*      */     
/*      */ 
/* 1520 */     int m = k - j + 1;
/* 1521 */     if (paramMutableBigInteger2.value.length < m) {
/* 1522 */       paramMutableBigInteger2.value = new int[m];
/* 1523 */       paramMutableBigInteger2.offset = 0;
/*      */     }
/* 1525 */     paramMutableBigInteger2.intLen = m;
/* 1526 */     int[] arrayOfInt3 = paramMutableBigInteger2.value;
/*      */     
/*      */ 
/*      */ 
/* 1530 */     if (localMutableBigInteger.intLen == k) {
/* 1531 */       localMutableBigInteger.offset = 0;
/* 1532 */       localMutableBigInteger.value[0] = 0;
/* 1533 */       localMutableBigInteger.intLen += 1;
/*      */     }
/*      */     
/* 1536 */     int i1 = arrayOfInt1[0];
/* 1537 */     long l1 = i1 & 0xFFFFFFFF;
/* 1538 */     int i4 = arrayOfInt1[1];
/*      */     
/*      */ 
/* 1541 */     for (int i5 = 0; i5 < m - 1; i5++)
/*      */     {
/*      */ 
/* 1544 */       i6 = 0;
/* 1545 */       i7 = 0;
/* 1546 */       i8 = 0;
/* 1547 */       i9 = localMutableBigInteger.value[(i5 + localMutableBigInteger.offset)];
/* 1548 */       i10 = i9 + Integer.MIN_VALUE;
/* 1549 */       int i11 = localMutableBigInteger.value[(i5 + 1 + localMutableBigInteger.offset)];
/*      */       long l3;
/* 1551 */       long l5; if (i9 == i1) {
/* 1552 */         i6 = -1;
/* 1553 */         i7 = i9 + i11;
/* 1554 */         i8 = i7 + Integer.MIN_VALUE < i10 ? 1 : 0;
/*      */       } else {
/* 1556 */         l3 = i9 << 32 | i11 & 0xFFFFFFFF;
/* 1557 */         if (l3 >= 0L) {
/* 1558 */           i6 = (int)(l3 / l1);
/* 1559 */           i7 = (int)(l3 - i6 * l1);
/*      */         } else {
/* 1561 */           l5 = divWord(l3, i1);
/* 1562 */           i6 = (int)(l5 & 0xFFFFFFFF);
/* 1563 */           i7 = (int)(l5 >>> 32);
/*      */         }
/*      */       }
/*      */       
/* 1567 */       if (i6 != 0)
/*      */       {
/*      */ 
/* 1570 */         if (i8 == 0) {
/* 1571 */           l3 = localMutableBigInteger.value[(i5 + 2 + localMutableBigInteger.offset)] & 0xFFFFFFFF;
/* 1572 */           l5 = (i7 & 0xFFFFFFFF) << 32 | l3;
/* 1573 */           long l7 = (i4 & 0xFFFFFFFF) * (i6 & 0xFFFFFFFF);
/*      */           
/* 1575 */           if (unsignedLongCompare(l7, l5)) {
/* 1576 */             i6--;
/* 1577 */             i7 = (int)((i7 & 0xFFFFFFFF) + l1);
/* 1578 */             if ((i7 & 0xFFFFFFFF) >= l1) {
/* 1579 */               l7 -= (i4 & 0xFFFFFFFF);
/* 1580 */               l5 = (i7 & 0xFFFFFFFF) << 32 | l3;
/* 1581 */               if (unsignedLongCompare(l7, l5)) {
/* 1582 */                 i6--;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1588 */         localMutableBigInteger.value[(i5 + localMutableBigInteger.offset)] = 0;
/* 1589 */         int i13 = mulsub(localMutableBigInteger.value, arrayOfInt1, i6, j, i5 + localMutableBigInteger.offset);
/*      */         
/*      */ 
/* 1592 */         if (i13 + Integer.MIN_VALUE > i10)
/*      */         {
/* 1594 */           divadd(arrayOfInt1, localMutableBigInteger.value, i5 + 1 + localMutableBigInteger.offset);
/* 1595 */           i6--;
/*      */         }
/*      */         
/*      */ 
/* 1599 */         arrayOfInt3[i5] = i6;
/*      */       }
/*      */     }
/*      */     
/* 1603 */     i5 = 0;
/* 1604 */     int i6 = 0;
/* 1605 */     int i7 = 0;
/* 1606 */     int i8 = localMutableBigInteger.value[(m - 1 + localMutableBigInteger.offset)];
/* 1607 */     int i9 = i8 + Integer.MIN_VALUE;
/* 1608 */     int i10 = localMutableBigInteger.value[(m + localMutableBigInteger.offset)];
/*      */     long l2;
/* 1610 */     long l4; if (i8 == i1) {
/* 1611 */       i5 = -1;
/* 1612 */       i6 = i8 + i10;
/* 1613 */       i7 = i6 + Integer.MIN_VALUE < i9 ? 1 : 0;
/*      */     } else {
/* 1615 */       l2 = i8 << 32 | i10 & 0xFFFFFFFF;
/* 1616 */       if (l2 >= 0L) {
/* 1617 */         i5 = (int)(l2 / l1);
/* 1618 */         i6 = (int)(l2 - i5 * l1);
/*      */       } else {
/* 1620 */         l4 = divWord(l2, i1);
/* 1621 */         i5 = (int)(l4 & 0xFFFFFFFF);
/* 1622 */         i6 = (int)(l4 >>> 32);
/*      */       }
/*      */     }
/* 1625 */     if (i5 != 0) {
/* 1626 */       if (i7 == 0) {
/* 1627 */         l2 = localMutableBigInteger.value[(m + 1 + localMutableBigInteger.offset)] & 0xFFFFFFFF;
/* 1628 */         l4 = (i6 & 0xFFFFFFFF) << 32 | l2;
/* 1629 */         long l6 = (i4 & 0xFFFFFFFF) * (i5 & 0xFFFFFFFF);
/*      */         
/* 1631 */         if (unsignedLongCompare(l6, l4)) {
/* 1632 */           i5--;
/* 1633 */           i6 = (int)((i6 & 0xFFFFFFFF) + l1);
/* 1634 */           if ((i6 & 0xFFFFFFFF) >= l1) {
/* 1635 */             l6 -= (i4 & 0xFFFFFFFF);
/* 1636 */             l4 = (i6 & 0xFFFFFFFF) << 32 | l2;
/* 1637 */             if (unsignedLongCompare(l6, l4)) {
/* 1638 */               i5--;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1646 */       localMutableBigInteger.value[(m - 1 + localMutableBigInteger.offset)] = 0;
/* 1647 */       int i12; if (paramBoolean) {
/* 1648 */         i12 = mulsub(localMutableBigInteger.value, arrayOfInt1, i5, j, m - 1 + localMutableBigInteger.offset);
/*      */       } else {
/* 1650 */         i12 = mulsubBorrow(localMutableBigInteger.value, arrayOfInt1, i5, j, m - 1 + localMutableBigInteger.offset);
/*      */       }
/*      */       
/* 1653 */       if (i12 + Integer.MIN_VALUE > i9)
/*      */       {
/* 1655 */         if (paramBoolean)
/* 1656 */           divadd(arrayOfInt1, localMutableBigInteger.value, m - 1 + 1 + localMutableBigInteger.offset);
/* 1657 */         i5--;
/*      */       }
/*      */       
/*      */ 
/* 1661 */       arrayOfInt3[(m - 1)] = i5;
/*      */     }
/*      */     
/*      */ 
/* 1665 */     if (paramBoolean)
/*      */     {
/* 1667 */       if (i > 0)
/* 1668 */         localMutableBigInteger.rightShift(i);
/* 1669 */       localMutableBigInteger.normalize();
/*      */     }
/* 1671 */     paramMutableBigInteger2.normalize();
/* 1672 */     return paramBoolean ? localMutableBigInteger : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private MutableBigInteger divideLongMagnitude(long paramLong, MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1682 */     MutableBigInteger localMutableBigInteger = new MutableBigInteger(new int[this.intLen + 1]);
/* 1683 */     System.arraycopy(this.value, this.offset, localMutableBigInteger.value, 1, this.intLen);
/* 1684 */     localMutableBigInteger.intLen = this.intLen;
/* 1685 */     localMutableBigInteger.offset = 1;
/*      */     
/* 1687 */     int i = localMutableBigInteger.intLen;
/*      */     
/* 1689 */     int j = i - 2 + 1;
/* 1690 */     if (paramMutableBigInteger.value.length < j) {
/* 1691 */       paramMutableBigInteger.value = new int[j];
/* 1692 */       paramMutableBigInteger.offset = 0;
/*      */     }
/* 1694 */     paramMutableBigInteger.intLen = j;
/* 1695 */     int[] arrayOfInt = paramMutableBigInteger.value;
/*      */     
/*      */ 
/* 1698 */     int k = Long.numberOfLeadingZeros(paramLong);
/* 1699 */     if (k > 0) {
/* 1700 */       paramLong <<= k;
/* 1701 */       localMutableBigInteger.leftShift(k);
/*      */     }
/*      */     
/*      */ 
/* 1705 */     if (localMutableBigInteger.intLen == i) {
/* 1706 */       localMutableBigInteger.offset = 0;
/* 1707 */       localMutableBigInteger.value[0] = 0;
/* 1708 */       localMutableBigInteger.intLen += 1;
/*      */     }
/*      */     
/* 1711 */     int m = (int)(paramLong >>> 32);
/* 1712 */     long l1 = m & 0xFFFFFFFF;
/* 1713 */     int n = (int)(paramLong & 0xFFFFFFFF);
/*      */     
/*      */ 
/* 1716 */     for (int i1 = 0; i1 < j; i1++)
/*      */     {
/*      */ 
/* 1719 */       int i2 = 0;
/* 1720 */       int i3 = 0;
/* 1721 */       int i4 = 0;
/* 1722 */       int i5 = localMutableBigInteger.value[(i1 + localMutableBigInteger.offset)];
/* 1723 */       int i6 = i5 + Integer.MIN_VALUE;
/* 1724 */       int i7 = localMutableBigInteger.value[(i1 + 1 + localMutableBigInteger.offset)];
/*      */       long l2;
/* 1726 */       long l3; if (i5 == m) {
/* 1727 */         i2 = -1;
/* 1728 */         i3 = i5 + i7;
/* 1729 */         i4 = i3 + Integer.MIN_VALUE < i6 ? 1 : 0;
/*      */       } else {
/* 1731 */         l2 = i5 << 32 | i7 & 0xFFFFFFFF;
/* 1732 */         if (l2 >= 0L) {
/* 1733 */           i2 = (int)(l2 / l1);
/* 1734 */           i3 = (int)(l2 - i2 * l1);
/*      */         } else {
/* 1736 */           l3 = divWord(l2, m);
/* 1737 */           i2 = (int)(l3 & 0xFFFFFFFF);
/* 1738 */           i3 = (int)(l3 >>> 32);
/*      */         }
/*      */       }
/*      */       
/* 1742 */       if (i2 != 0)
/*      */       {
/*      */ 
/* 1745 */         if (i4 == 0) {
/* 1746 */           l2 = localMutableBigInteger.value[(i1 + 2 + localMutableBigInteger.offset)] & 0xFFFFFFFF;
/* 1747 */           l3 = (i3 & 0xFFFFFFFF) << 32 | l2;
/* 1748 */           long l4 = (n & 0xFFFFFFFF) * (i2 & 0xFFFFFFFF);
/*      */           
/* 1750 */           if (unsignedLongCompare(l4, l3)) {
/* 1751 */             i2--;
/* 1752 */             i3 = (int)((i3 & 0xFFFFFFFF) + l1);
/* 1753 */             if ((i3 & 0xFFFFFFFF) >= l1) {
/* 1754 */               l4 -= (n & 0xFFFFFFFF);
/* 1755 */               l3 = (i3 & 0xFFFFFFFF) << 32 | l2;
/* 1756 */               if (unsignedLongCompare(l4, l3)) {
/* 1757 */                 i2--;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1763 */         localMutableBigInteger.value[(i1 + localMutableBigInteger.offset)] = 0;
/* 1764 */         int i8 = mulsubLong(localMutableBigInteger.value, m, n, i2, i1 + localMutableBigInteger.offset);
/*      */         
/*      */ 
/* 1767 */         if (i8 + Integer.MIN_VALUE > i6)
/*      */         {
/* 1769 */           divaddLong(m, n, localMutableBigInteger.value, i1 + 1 + localMutableBigInteger.offset);
/* 1770 */           i2--;
/*      */         }
/*      */         
/*      */ 
/* 1774 */         arrayOfInt[i1] = i2;
/*      */       }
/*      */     }
/*      */     
/* 1778 */     if (k > 0) {
/* 1779 */       localMutableBigInteger.rightShift(k);
/*      */     }
/* 1781 */     paramMutableBigInteger.normalize();
/* 1782 */     localMutableBigInteger.normalize();
/* 1783 */     return localMutableBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int divaddLong(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
/*      */   {
/* 1792 */     long l1 = 0L;
/*      */     
/* 1794 */     long l2 = (paramInt2 & 0xFFFFFFFF) + (paramArrayOfInt[(1 + paramInt3)] & 0xFFFFFFFF);
/* 1795 */     paramArrayOfInt[(1 + paramInt3)] = ((int)l2);
/*      */     
/* 1797 */     l2 = (paramInt1 & 0xFFFFFFFF) + (paramArrayOfInt[paramInt3] & 0xFFFFFFFF) + l1;
/* 1798 */     paramArrayOfInt[paramInt3] = ((int)l2);
/* 1799 */     l1 = l2 >>> 32;
/* 1800 */     return (int)l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int mulsubLong(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1809 */     long l1 = paramInt3 & 0xFFFFFFFF;
/* 1810 */     paramInt4 += 2;
/* 1811 */     long l2 = (paramInt2 & 0xFFFFFFFF) * l1;
/* 1812 */     long l3 = paramArrayOfInt[paramInt4] - l2;
/* 1813 */     paramArrayOfInt[(paramInt4--)] = ((int)l3);
/* 1814 */     long l4 = (l2 >>> 32) + ((l3 & 0xFFFFFFFF) > (((int)l2 ^ 0xFFFFFFFF) & 0xFFFFFFFF) ? 1 : 0);
/*      */     
/*      */ 
/* 1817 */     l2 = (paramInt1 & 0xFFFFFFFF) * l1 + l4;
/* 1818 */     l3 = paramArrayOfInt[paramInt4] - l2;
/* 1819 */     paramArrayOfInt[(paramInt4--)] = ((int)l3);
/* 1820 */     l4 = (l2 >>> 32) + ((l3 & 0xFFFFFFFF) > (((int)l2 ^ 0xFFFFFFFF) & 0xFFFFFFFF) ? 1 : 0);
/*      */     
/*      */ 
/* 1823 */     return (int)l4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean unsignedLongCompare(long paramLong1, long paramLong2)
/*      */   {
/* 1831 */     return paramLong1 + Long.MIN_VALUE > paramLong2 + Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static long divWord(long paramLong, int paramInt)
/*      */   {
/* 1842 */     long l1 = paramInt & 0xFFFFFFFF;
/*      */     
/*      */ 
/* 1845 */     if (l1 == 1L) {
/* 1846 */       l3 = (int)paramLong;
/* 1847 */       l2 = 0L;
/* 1848 */       return l2 << 32 | l3 & 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */ 
/* 1852 */     long l3 = (paramLong >>> 1) / (l1 >>> 1);
/* 1853 */     long l2 = paramLong - l3 * l1;
/*      */     
/*      */ 
/* 1856 */     while (l2 < 0L) {
/* 1857 */       l2 += l1;
/* 1858 */       l3 -= 1L;
/*      */     }
/* 1860 */     while (l2 >= l1) {
/* 1861 */       l2 -= l1;
/* 1862 */       l3 += 1L;
/*      */     }
/*      */     
/* 1865 */     return l2 << 32 | l3 & 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger hybridGCD(MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1874 */     MutableBigInteger localMutableBigInteger1 = this;
/* 1875 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger();
/*      */     
/* 1877 */     while (paramMutableBigInteger.intLen != 0) {
/* 1878 */       if (Math.abs(localMutableBigInteger1.intLen - paramMutableBigInteger.intLen) < 2) {
/* 1879 */         return localMutableBigInteger1.binaryGCD(paramMutableBigInteger);
/*      */       }
/* 1881 */       MutableBigInteger localMutableBigInteger3 = localMutableBigInteger1.divide(paramMutableBigInteger, localMutableBigInteger2);
/* 1882 */       localMutableBigInteger1 = paramMutableBigInteger;
/* 1883 */       paramMutableBigInteger = localMutableBigInteger3;
/*      */     }
/* 1885 */     return localMutableBigInteger1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private MutableBigInteger binaryGCD(MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1894 */     Object localObject1 = this;
/* 1895 */     MutableBigInteger localMutableBigInteger = new MutableBigInteger();
/*      */     
/*      */ 
/* 1898 */     int i = ((MutableBigInteger)localObject1).getLowestSetBit();
/* 1899 */     int j = paramMutableBigInteger.getLowestSetBit();
/* 1900 */     int k = i < j ? i : j;
/* 1901 */     if (k != 0) {
/* 1902 */       ((MutableBigInteger)localObject1).rightShift(k);
/* 1903 */       paramMutableBigInteger.rightShift(k);
/*      */     }
/*      */     
/*      */ 
/* 1907 */     int m = k == i ? 1 : 0;
/* 1908 */     Object localObject2 = m != 0 ? paramMutableBigInteger : localObject1;
/* 1909 */     int n = m != 0 ? -1 : 1;
/*      */     
/*      */     int i1;
/* 1912 */     while ((i1 = ((MutableBigInteger)localObject2).getLowestSetBit()) >= 0)
/*      */     {
/* 1914 */       ((MutableBigInteger)localObject2).rightShift(i1);
/*      */       
/* 1916 */       if (n > 0) {
/* 1917 */         localObject1 = localObject2;
/*      */       } else {
/* 1919 */         paramMutableBigInteger = (MutableBigInteger)localObject2;
/*      */       }
/*      */       
/* 1922 */       if ((((MutableBigInteger)localObject1).intLen < 2) && (paramMutableBigInteger.intLen < 2)) {
/* 1923 */         int i2 = localObject1.value[localObject1.offset];
/* 1924 */         int i3 = paramMutableBigInteger.value[paramMutableBigInteger.offset];
/* 1925 */         i2 = binaryGcd(i2, i3);
/* 1926 */         localMutableBigInteger.value[0] = i2;
/* 1927 */         localMutableBigInteger.intLen = 1;
/* 1928 */         localMutableBigInteger.offset = 0;
/* 1929 */         if (k > 0)
/* 1930 */           localMutableBigInteger.leftShift(k);
/* 1931 */         return localMutableBigInteger;
/*      */       }
/*      */       
/*      */ 
/* 1935 */       if ((n = ((MutableBigInteger)localObject1).difference(paramMutableBigInteger)) == 0)
/*      */         break;
/* 1937 */       localObject2 = n >= 0 ? localObject1 : paramMutableBigInteger;
/*      */     }
/*      */     
/* 1940 */     if (k > 0)
/* 1941 */       ((MutableBigInteger)localObject1).leftShift(k);
/* 1942 */     return (MutableBigInteger)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static int binaryGcd(int paramInt1, int paramInt2)
/*      */   {
/* 1949 */     if (paramInt2 == 0)
/* 1950 */       return paramInt1;
/* 1951 */     if (paramInt1 == 0) {
/* 1952 */       return paramInt2;
/*      */     }
/*      */     
/* 1955 */     int i = Integer.numberOfTrailingZeros(paramInt1);
/* 1956 */     int j = Integer.numberOfTrailingZeros(paramInt2);
/* 1957 */     paramInt1 >>>= i;
/* 1958 */     paramInt2 >>>= j;
/*      */     
/* 1960 */     int k = i < j ? i : j;
/*      */     
/* 1962 */     while (paramInt1 != paramInt2) {
/* 1963 */       if (paramInt1 + Integer.MIN_VALUE > paramInt2 + Integer.MIN_VALUE) {
/* 1964 */         paramInt1 -= paramInt2;
/* 1965 */         paramInt1 >>>= Integer.numberOfTrailingZeros(paramInt1);
/*      */       } else {
/* 1967 */         paramInt2 -= paramInt1;
/* 1968 */         paramInt2 >>>= Integer.numberOfTrailingZeros(paramInt2);
/*      */       }
/*      */     }
/* 1971 */     return paramInt1 << k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger mutableModInverse(MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 1980 */     if (paramMutableBigInteger.isOdd()) {
/* 1981 */       return modInverse(paramMutableBigInteger);
/*      */     }
/*      */     
/* 1984 */     if (isEven()) {
/* 1985 */       throw new ArithmeticException("BigInteger not invertible.");
/*      */     }
/*      */     
/* 1988 */     int i = paramMutableBigInteger.getLowestSetBit();
/*      */     
/*      */ 
/* 1991 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(paramMutableBigInteger);
/* 1992 */     localMutableBigInteger1.rightShift(i);
/*      */     
/* 1994 */     if (localMutableBigInteger1.isOne()) {
/* 1995 */       return modInverseMP2(i);
/*      */     }
/*      */     
/* 1998 */     MutableBigInteger localMutableBigInteger2 = modInverse(localMutableBigInteger1);
/*      */     
/*      */ 
/* 2001 */     MutableBigInteger localMutableBigInteger3 = modInverseMP2(i);
/*      */     
/*      */ 
/* 2004 */     MutableBigInteger localMutableBigInteger4 = modInverseBP2(localMutableBigInteger1, i);
/* 2005 */     MutableBigInteger localMutableBigInteger5 = localMutableBigInteger1.modInverseMP2(i);
/*      */     
/* 2007 */     MutableBigInteger localMutableBigInteger6 = new MutableBigInteger();
/* 2008 */     MutableBigInteger localMutableBigInteger7 = new MutableBigInteger();
/* 2009 */     MutableBigInteger localMutableBigInteger8 = new MutableBigInteger();
/*      */     
/* 2011 */     localMutableBigInteger2.leftShift(i);
/* 2012 */     localMutableBigInteger2.multiply(localMutableBigInteger4, localMutableBigInteger8);
/*      */     
/* 2014 */     localMutableBigInteger3.multiply(localMutableBigInteger1, localMutableBigInteger6);
/* 2015 */     localMutableBigInteger6.multiply(localMutableBigInteger5, localMutableBigInteger7);
/*      */     
/* 2017 */     localMutableBigInteger8.add(localMutableBigInteger7);
/* 2018 */     return localMutableBigInteger8.divide(paramMutableBigInteger, localMutableBigInteger6);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   MutableBigInteger modInverseMP2(int paramInt)
/*      */   {
/* 2025 */     if (isEven()) {
/* 2026 */       throw new ArithmeticException("Non-invertible. (GCD != 1)");
/*      */     }
/* 2028 */     if (paramInt > 64) {
/* 2029 */       return euclidModInverse(paramInt);
/*      */     }
/* 2031 */     int i = inverseMod32(this.value[(this.offset + this.intLen - 1)]);
/*      */     
/* 2033 */     if (paramInt < 33) {
/* 2034 */       i = paramInt == 32 ? i : i & (1 << paramInt) - 1;
/* 2035 */       return new MutableBigInteger(i);
/*      */     }
/*      */     
/* 2038 */     long l1 = this.value[(this.offset + this.intLen - 1)] & 0xFFFFFFFF;
/* 2039 */     if (this.intLen > 1)
/* 2040 */       l1 |= this.value[(this.offset + this.intLen - 2)] << 32;
/* 2041 */     long l2 = i & 0xFFFFFFFF;
/* 2042 */     l2 *= (2L - l1 * l2);
/* 2043 */     l2 = paramInt == 64 ? l2 : l2 & (1L << paramInt) - 1L;
/*      */     
/* 2045 */     MutableBigInteger localMutableBigInteger = new MutableBigInteger(new int[2]);
/* 2046 */     localMutableBigInteger.value[0] = ((int)(l2 >>> 32));
/* 2047 */     localMutableBigInteger.value[1] = ((int)l2);
/* 2048 */     localMutableBigInteger.intLen = 2;
/* 2049 */     localMutableBigInteger.normalize();
/* 2050 */     return localMutableBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static int inverseMod32(int paramInt)
/*      */   {
/* 2058 */     int i = paramInt;
/* 2059 */     i *= (2 - paramInt * i);
/* 2060 */     i *= (2 - paramInt * i);
/* 2061 */     i *= (2 - paramInt * i);
/* 2062 */     i *= (2 - paramInt * i);
/* 2063 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static MutableBigInteger modInverseBP2(MutableBigInteger paramMutableBigInteger, int paramInt)
/*      */   {
/* 2071 */     return fixup(new MutableBigInteger(1), new MutableBigInteger(paramMutableBigInteger), paramInt);
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
/*      */   private MutableBigInteger modInverse(MutableBigInteger paramMutableBigInteger)
/*      */   {
/* 2084 */     MutableBigInteger localMutableBigInteger = new MutableBigInteger(paramMutableBigInteger);
/* 2085 */     Object localObject1 = new MutableBigInteger(this);
/* 2086 */     Object localObject2 = new MutableBigInteger(localMutableBigInteger);
/* 2087 */     Object localObject3 = new SignedMutableBigInteger(1);
/* 2088 */     Object localObject4 = new SignedMutableBigInteger();
/* 2089 */     Object localObject5 = null;
/* 2090 */     Object localObject6 = null;
/*      */     
/* 2092 */     int i = 0;
/*      */     int j;
/* 2094 */     if (((MutableBigInteger)localObject1).isEven()) {
/* 2095 */       j = ((MutableBigInteger)localObject1).getLowestSetBit();
/* 2096 */       ((MutableBigInteger)localObject1).rightShift(j);
/* 2097 */       ((SignedMutableBigInteger)localObject4).leftShift(j);
/* 2098 */       i = j;
/*      */     }
/*      */     
/*      */ 
/* 2102 */     while (!((MutableBigInteger)localObject1).isOne())
/*      */     {
/* 2104 */       if (((MutableBigInteger)localObject1).isZero()) {
/* 2105 */         throw new ArithmeticException("BigInteger not invertible.");
/*      */       }
/*      */       
/* 2108 */       if (((MutableBigInteger)localObject1).compare((MutableBigInteger)localObject2) < 0) {
/* 2109 */         localObject5 = localObject1;localObject1 = localObject2;localObject2 = localObject5;
/* 2110 */         localObject6 = localObject4;localObject4 = localObject3;localObject3 = localObject6;
/*      */       }
/*      */       
/*      */ 
/* 2114 */       if (((localObject1.value[(localObject1.offset + localObject1.intLen - 1)] ^ localObject2.value[(localObject2.offset + localObject2.intLen - 1)]) & 0x3) == 0)
/*      */       {
/* 2116 */         ((MutableBigInteger)localObject1).subtract((MutableBigInteger)localObject2);
/* 2117 */         ((SignedMutableBigInteger)localObject3).signedSubtract((SignedMutableBigInteger)localObject4);
/*      */       } else {
/* 2119 */         ((MutableBigInteger)localObject1).add((MutableBigInteger)localObject2);
/* 2120 */         ((SignedMutableBigInteger)localObject3).signedAdd((SignedMutableBigInteger)localObject4);
/*      */       }
/*      */       
/*      */ 
/* 2124 */       j = ((MutableBigInteger)localObject1).getLowestSetBit();
/* 2125 */       ((MutableBigInteger)localObject1).rightShift(j);
/* 2126 */       ((SignedMutableBigInteger)localObject4).leftShift(j);
/* 2127 */       i += j;
/*      */     }
/*      */     
/* 2130 */     while (((SignedMutableBigInteger)localObject3).sign < 0) {
/* 2131 */       ((SignedMutableBigInteger)localObject3).signedAdd(localMutableBigInteger);
/*      */     }
/* 2133 */     return fixup((MutableBigInteger)localObject3, localMutableBigInteger, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static MutableBigInteger fixup(MutableBigInteger paramMutableBigInteger1, MutableBigInteger paramMutableBigInteger2, int paramInt)
/*      */   {
/* 2143 */     MutableBigInteger localMutableBigInteger = new MutableBigInteger();
/*      */     
/* 2145 */     int i = -inverseMod32(paramMutableBigInteger2.value[(paramMutableBigInteger2.offset + paramMutableBigInteger2.intLen - 1)]);
/*      */     
/* 2147 */     int j = 0; for (int k = paramInt >> 5; j < k; j++)
/*      */     {
/* 2149 */       int m = i * paramMutableBigInteger1.value[(paramMutableBigInteger1.offset + paramMutableBigInteger1.intLen - 1)];
/*      */       
/* 2151 */       paramMutableBigInteger2.mul(m, localMutableBigInteger);
/* 2152 */       paramMutableBigInteger1.add(localMutableBigInteger);
/*      */       
/* 2154 */       paramMutableBigInteger1.intLen -= 1;
/*      */     }
/* 2156 */     j = paramInt & 0x1F;
/* 2157 */     if (j != 0)
/*      */     {
/* 2159 */       k = i * paramMutableBigInteger1.value[(paramMutableBigInteger1.offset + paramMutableBigInteger1.intLen - 1)];
/* 2160 */       k &= (1 << j) - 1;
/*      */       
/* 2162 */       paramMutableBigInteger2.mul(k, localMutableBigInteger);
/* 2163 */       paramMutableBigInteger1.add(localMutableBigInteger);
/*      */       
/* 2165 */       paramMutableBigInteger1.rightShift(j);
/*      */     }
/*      */     
/*      */ 
/* 2169 */     while (paramMutableBigInteger1.compare(paramMutableBigInteger2) >= 0) {
/* 2170 */       paramMutableBigInteger1.subtract(paramMutableBigInteger2);
/*      */     }
/* 2172 */     return paramMutableBigInteger1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   MutableBigInteger euclidModInverse(int paramInt)
/*      */   {
/* 2180 */     Object localObject1 = new MutableBigInteger(1);
/* 2181 */     ((MutableBigInteger)localObject1).leftShift(paramInt);
/* 2182 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger((MutableBigInteger)localObject1);
/*      */     
/* 2184 */     Object localObject2 = new MutableBigInteger(this);
/* 2185 */     Object localObject3 = new MutableBigInteger();
/* 2186 */     Object localObject4 = ((MutableBigInteger)localObject1).divide((MutableBigInteger)localObject2, (MutableBigInteger)localObject3);
/*      */     
/* 2188 */     Object localObject5 = localObject1;
/*      */     
/* 2190 */     localObject1 = localObject4;
/* 2191 */     localObject4 = localObject5;
/*      */     
/* 2193 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger((MutableBigInteger)localObject3);
/* 2194 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(1);
/* 2195 */     Object localObject6 = new MutableBigInteger();
/*      */     
/* 2197 */     while (!((MutableBigInteger)localObject1).isOne()) {
/* 2198 */       localObject4 = ((MutableBigInteger)localObject2).divide((MutableBigInteger)localObject1, (MutableBigInteger)localObject3);
/*      */       
/* 2200 */       if (((MutableBigInteger)localObject4).intLen == 0) {
/* 2201 */         throw new ArithmeticException("BigInteger not invertible.");
/*      */       }
/* 2203 */       localObject5 = localObject4;
/* 2204 */       localObject2 = localObject5;
/*      */       
/* 2206 */       if (((MutableBigInteger)localObject3).intLen == 1) {
/* 2207 */         localMutableBigInteger2.mul(localObject3.value[localObject3.offset], (MutableBigInteger)localObject6);
/*      */       } else
/* 2209 */         ((MutableBigInteger)localObject3).multiply(localMutableBigInteger2, (MutableBigInteger)localObject6);
/* 2210 */       localObject5 = localObject3;
/* 2211 */       localObject3 = localObject6;
/* 2212 */       localObject6 = localObject5;
/* 2213 */       localMutableBigInteger3.add((MutableBigInteger)localObject3);
/*      */       
/* 2215 */       if (((MutableBigInteger)localObject2).isOne()) {
/* 2216 */         return localMutableBigInteger3;
/*      */       }
/* 2218 */       localObject4 = ((MutableBigInteger)localObject1).divide((MutableBigInteger)localObject2, (MutableBigInteger)localObject3);
/*      */       
/* 2220 */       if (((MutableBigInteger)localObject4).intLen == 0) {
/* 2221 */         throw new ArithmeticException("BigInteger not invertible.");
/*      */       }
/* 2223 */       localObject5 = localObject1;
/* 2224 */       localObject1 = localObject4;
/*      */       
/* 2226 */       if (((MutableBigInteger)localObject3).intLen == 1) {
/* 2227 */         localMutableBigInteger3.mul(localObject3.value[localObject3.offset], (MutableBigInteger)localObject6);
/*      */       } else
/* 2229 */         ((MutableBigInteger)localObject3).multiply(localMutableBigInteger3, (MutableBigInteger)localObject6);
/* 2230 */       localObject5 = localObject3;localObject3 = localObject6;localObject6 = localObject5;
/*      */       
/* 2232 */       localMutableBigInteger2.add((MutableBigInteger)localObject3);
/*      */     }
/* 2234 */     localMutableBigInteger1.subtract(localMutableBigInteger2);
/* 2235 */     return localMutableBigInteger1;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/math/MutableBigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */