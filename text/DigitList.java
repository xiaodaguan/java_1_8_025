/*     */ package java.text;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.RoundingMode;
/*     */ import sun.misc.FloatingDecimal;
/*     */ import sun.misc.FloatingDecimal.BinaryToASCIIConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DigitList
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int MAX_COUNT = 19;
/* 103 */   public int decimalAt = 0;
/* 104 */   public int count = 0;
/* 105 */   public char[] digits = new char[19];
/*     */   
/*     */   private char[] data;
/* 108 */   private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
/* 109 */   private boolean isNegative = false;
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isZero()
/*     */   {
/* 115 */     for (int i = 0; i < this.count; i++) {
/* 116 */       if (this.digits[i] != '0') {
/* 117 */         return false;
/*     */       }
/*     */     }
/* 120 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setRoundingMode(RoundingMode paramRoundingMode)
/*     */   {
/* 127 */     this.roundingMode = paramRoundingMode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 138 */     this.decimalAt = 0;
/* 139 */     this.count = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void append(char paramChar)
/*     */   {
/* 146 */     if (this.count == this.digits.length) {
/* 147 */       char[] arrayOfChar = new char[this.count + 100];
/* 148 */       System.arraycopy(this.digits, 0, arrayOfChar, 0, this.count);
/* 149 */       this.digits = arrayOfChar;
/*     */     }
/* 151 */     this.digits[(this.count++)] = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final double getDouble()
/*     */   {
/* 160 */     if (this.count == 0) {
/* 161 */       return 0.0D;
/*     */     }
/*     */     
/* 164 */     StringBuffer localStringBuffer = getStringBuffer();
/* 165 */     localStringBuffer.append('.');
/* 166 */     localStringBuffer.append(this.digits, 0, this.count);
/* 167 */     localStringBuffer.append('E');
/* 168 */     localStringBuffer.append(this.decimalAt);
/* 169 */     return Double.parseDouble(localStringBuffer.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getLong()
/*     */   {
/* 179 */     if (this.count == 0) {
/* 180 */       return 0L;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 186 */     if (isLongMIN_VALUE()) {
/* 187 */       return Long.MIN_VALUE;
/*     */     }
/*     */     
/* 190 */     StringBuffer localStringBuffer = getStringBuffer();
/* 191 */     localStringBuffer.append(this.digits, 0, this.count);
/* 192 */     for (int i = this.count; i < this.decimalAt; i++) {
/* 193 */       localStringBuffer.append('0');
/*     */     }
/* 195 */     return Long.parseLong(localStringBuffer.toString());
/*     */   }
/*     */   
/*     */   public final BigDecimal getBigDecimal() {
/* 199 */     if (this.count == 0) {
/* 200 */       if (this.decimalAt == 0) {
/* 201 */         return BigDecimal.ZERO;
/*     */       }
/* 203 */       return new BigDecimal("0E" + this.decimalAt);
/*     */     }
/*     */     
/*     */ 
/* 207 */     if (this.decimalAt == this.count) {
/* 208 */       return new BigDecimal(this.digits, 0, this.count);
/*     */     }
/* 210 */     return new BigDecimal(this.digits, 0, this.count).scaleByPowerOfTen(this.decimalAt - this.count);
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
/*     */   boolean fitsIntoLong(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 230 */     while ((this.count > 0) && (this.digits[(this.count - 1)] == '0')) {
/* 231 */       this.count -= 1;
/*     */     }
/*     */     
/* 234 */     if (this.count == 0)
/*     */     {
/*     */ 
/* 237 */       return (paramBoolean1) || (paramBoolean2);
/*     */     }
/*     */     
/* 240 */     if ((this.decimalAt < this.count) || (this.decimalAt > 19)) {
/* 241 */       return false;
/*     */     }
/*     */     
/* 244 */     if (this.decimalAt < 19) { return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 249 */     for (int i = 0; i < this.count; i++) {
/* 250 */       int j = this.digits[i];int k = LONG_MIN_REP[i];
/* 251 */       if (j > k) return false;
/* 252 */       if (j < k) { return true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 257 */     if (this.count < this.decimalAt) { return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 262 */     return !paramBoolean1;
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
/*     */   final void set(boolean paramBoolean, double paramDouble, int paramInt)
/*     */   {
/* 275 */     set(paramBoolean, paramDouble, paramInt, true);
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
/*     */   final void set(boolean paramBoolean1, double paramDouble, int paramInt, boolean paramBoolean2)
/*     */   {
/* 291 */     FloatingDecimal.BinaryToASCIIConverter localBinaryToASCIIConverter = FloatingDecimal.getBinaryToASCIIConverter(paramDouble);
/* 292 */     boolean bool1 = localBinaryToASCIIConverter.digitsRoundedUp();
/* 293 */     boolean bool2 = localBinaryToASCIIConverter.decimalDigitsExact();
/* 294 */     assert (!localBinaryToASCIIConverter.isExceptional());
/* 295 */     String str = localBinaryToASCIIConverter.toJavaFormatString();
/*     */     
/* 297 */     set(paramBoolean1, str, bool1, bool2, paramInt, paramBoolean2);
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
/*     */   private void set(boolean paramBoolean1, String paramString, boolean paramBoolean2, boolean paramBoolean3, int paramInt, boolean paramBoolean4)
/*     */   {
/* 312 */     this.isNegative = paramBoolean1;
/* 313 */     int i = paramString.length();
/* 314 */     char[] arrayOfChar = getDataChars(i);
/* 315 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */     
/* 317 */     this.decimalAt = -1;
/* 318 */     this.count = 0;
/* 319 */     int j = 0;
/*     */     
/*     */ 
/* 322 */     int k = 0;
/* 323 */     int m = 0;
/*     */     
/* 325 */     for (int n = 0; n < i;) {
/* 326 */       int i1 = arrayOfChar[(n++)];
/* 327 */       if (i1 == 46) {
/* 328 */         this.decimalAt = this.count;
/* 329 */       } else { if ((i1 == 101) || (i1 == 69)) {
/* 330 */           j = parseInt(arrayOfChar, n, i);
/* 331 */           break;
/*     */         }
/* 333 */         if (m == 0) {
/* 334 */           m = i1 != 48 ? 1 : 0;
/* 335 */           if ((m == 0) && (this.decimalAt != -1))
/* 336 */             k++;
/*     */         }
/* 338 */         if (m != 0) {
/* 339 */           this.digits[(this.count++)] = i1;
/*     */         }
/*     */       }
/*     */     }
/* 343 */     if (this.decimalAt == -1) {
/* 344 */       this.decimalAt = this.count;
/*     */     }
/* 346 */     if (m != 0) {
/* 347 */       this.decimalAt += j - k;
/*     */     }
/*     */     
/* 350 */     if (paramBoolean4)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 356 */       if (-this.decimalAt > paramInt)
/*     */       {
/*     */ 
/* 359 */         this.count = 0;
/* 360 */         return; }
/* 361 */       if (-this.decimalAt == paramInt)
/*     */       {
/*     */ 
/* 364 */         if (shouldRoundUp(0, paramBoolean2, paramBoolean3)) {
/* 365 */           this.count = 1;
/* 366 */           this.decimalAt += 1;
/* 367 */           this.digits[0] = '1';
/*     */         } else {
/* 369 */           this.count = 0;
/*     */         }
/* 371 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 377 */     while ((this.count > 1) && (this.digits[(this.count - 1)] == '0')) {
/* 378 */       this.count -= 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 383 */     round(paramBoolean4 ? paramInt + this.decimalAt : paramInt, paramBoolean2, paramBoolean3);
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
/*     */   private final void round(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 401 */     if ((paramInt >= 0) && (paramInt < this.count)) {
/* 402 */       if (shouldRoundUp(paramInt, paramBoolean1, paramBoolean2))
/*     */       {
/*     */ 
/*     */         for (;;)
/*     */         {
/* 407 */           paramInt--;
/* 408 */           if (paramInt < 0)
/*     */           {
/*     */ 
/* 411 */             this.digits[0] = '1';
/* 412 */             this.decimalAt += 1;
/* 413 */             paramInt = 0;
/*     */           }
/*     */           else
/*     */           {
/* 417 */             int tmp57_56 = paramInt; char[] tmp57_53 = this.digits;tmp57_53[tmp57_56] = ((char)(tmp57_53[tmp57_56] + '\001'));
/* 418 */             if (this.digits[paramInt] <= '9') break;
/*     */           }
/*     */         }
/* 421 */         paramInt++;
/*     */       }
/* 423 */       this.count = paramInt;
/*     */       
/*     */ 
/* 426 */       while ((this.count > 1) && (this.digits[(this.count - 1)] == '0')) {
/* 427 */         this.count -= 1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean shouldRoundUp(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 451 */     if (paramInt < this.count)
/*     */     {
/*     */       int i;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 498 */       switch (this.roundingMode) {
/*     */       case UP: 
/* 500 */         for (i = paramInt; i < this.count; i++) {
/* 501 */           if (this.digits[i] != '0') {
/* 502 */             return true;
/*     */           }
/*     */         }
/* 505 */         break;
/*     */       case DOWN: 
/*     */         break;
/*     */       case CEILING: 
/* 509 */         for (i = paramInt; i < this.count; i++) {
/* 510 */           if (this.digits[i] != '0') {
/* 511 */             return !this.isNegative;
/*     */           }
/*     */         }
/* 514 */         break;
/*     */       case FLOOR: 
/* 516 */         for (i = paramInt; i < this.count; i++) {
/* 517 */           if (this.digits[i] != '0') {
/* 518 */             return this.isNegative;
/*     */           }
/*     */         }
/* 521 */         break;
/*     */       case HALF_UP: 
/* 523 */         if (this.digits[paramInt] >= '5')
/*     */         {
/*     */ 
/* 526 */           if ((paramInt == this.count - 1) && (paramBoolean1))
/*     */           {
/* 528 */             return false;
/*     */           }
/*     */           
/* 531 */           return true;
/*     */         }
/*     */         break;
/*     */       case HALF_DOWN: 
/* 535 */         if (this.digits[paramInt] > '5')
/* 536 */           return true;
/* 537 */         if (this.digits[paramInt] == '5') {
/* 538 */           if (paramInt == this.count - 1)
/*     */           {
/* 540 */             if ((paramBoolean2) || (paramBoolean1))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 546 */               return false;
/*     */             }
/*     */             
/* 549 */             return true;
/*     */           }
/*     */           
/*     */ 
/* 553 */           for (i = paramInt + 1; i < this.count; i++) {
/* 554 */             if (this.digits[i] != '0') {
/* 555 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */         break;
/*     */       case HALF_EVEN: 
/* 562 */         if (this.digits[paramInt] > '5')
/* 563 */           return true;
/* 564 */         if (this.digits[paramInt] == '5') {
/* 565 */           if (paramInt == this.count - 1)
/*     */           {
/* 567 */             if (paramBoolean1)
/*     */             {
/*     */ 
/* 570 */               return false;
/*     */             }
/* 572 */             if (!paramBoolean2)
/*     */             {
/*     */ 
/*     */ 
/* 576 */               return true;
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 581 */             return (paramInt > 0) && (this.digits[(paramInt - 1)] % '\002' != 0);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 586 */           for (i = paramInt + 1; i < this.count; i++) {
/* 587 */             if (this.digits[i] != '0') {
/* 588 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */         break;
/*     */       case UNNECESSARY: 
/* 594 */         for (i = paramInt; i < this.count; i++) {
/* 595 */           if (this.digits[i] != '0') {
/* 596 */             throw new ArithmeticException("Rounding needed with the rounding mode being set to RoundingMode.UNNECESSARY");
/*     */           }
/*     */         }
/*     */         
/* 600 */         break;
/*     */       default: 
/* 602 */         if (!$assertionsDisabled) throw new AssertionError();
/*     */         break; }
/*     */     }
/* 605 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final void set(boolean paramBoolean, long paramLong)
/*     */   {
/* 612 */     set(paramBoolean, paramLong, 0);
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
/*     */   final void set(boolean paramBoolean, long paramLong, int paramInt)
/*     */   {
/* 625 */     this.isNegative = paramBoolean;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 633 */     if (paramLong <= 0L) {
/* 634 */       if (paramLong == Long.MIN_VALUE) {
/* 635 */         this.decimalAt = (this.count = 19);
/* 636 */         System.arraycopy(LONG_MIN_REP, 0, this.digits, 0, this.count);
/*     */       } else {
/* 638 */         this.decimalAt = (this.count = 0);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 643 */       int i = 19;
/*     */       
/* 645 */       while (paramLong > 0L) {
/* 646 */         this.digits[(--i)] = ((char)(int)(48L + paramLong % 10L));
/* 647 */         paramLong /= 10L;
/*     */       }
/* 649 */       this.decimalAt = (19 - i);
/*     */       
/*     */ 
/* 652 */       for (int j = 18; this.digits[j] == '0'; j--) {}
/*     */       
/* 654 */       this.count = (j - i + 1);
/* 655 */       System.arraycopy(this.digits, i, this.digits, 0, this.count);
/*     */     }
/* 657 */     if (paramInt > 0) { round(paramInt, false, true);
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
/*     */   final void set(boolean paramBoolean1, BigDecimal paramBigDecimal, int paramInt, boolean paramBoolean2)
/*     */   {
/* 671 */     String str = paramBigDecimal.toString();
/* 672 */     extendDigits(str.length());
/*     */     
/* 674 */     set(paramBoolean1, str, false, true, paramInt, paramBoolean2);
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
/*     */   final void set(boolean paramBoolean, BigInteger paramBigInteger, int paramInt)
/*     */   {
/* 688 */     this.isNegative = paramBoolean;
/* 689 */     String str = paramBigInteger.toString();
/* 690 */     int i = str.length();
/* 691 */     extendDigits(i);
/* 692 */     str.getChars(0, i, this.digits, 0);
/*     */     
/* 694 */     this.decimalAt = i;
/*     */     
/* 696 */     for (int j = i - 1; (j >= 0) && (this.digits[j] == '0'); j--) {}
/*     */     
/* 698 */     this.count = (j + 1);
/*     */     
/* 700 */     if (paramInt > 0) {
/* 701 */       round(paramInt, false, true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 709 */     if (this == paramObject)
/* 710 */       return true;
/* 711 */     if (!(paramObject instanceof DigitList))
/* 712 */       return false;
/* 713 */     DigitList localDigitList = (DigitList)paramObject;
/* 714 */     if ((this.count != localDigitList.count) || (this.decimalAt != localDigitList.decimalAt))
/*     */     {
/* 716 */       return false; }
/* 717 */     for (int i = 0; i < this.count; i++)
/* 718 */       if (this.digits[i] != localDigitList.digits[i])
/* 719 */         return false;
/* 720 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 727 */     int i = this.decimalAt;
/*     */     
/* 729 */     for (int j = 0; j < this.count; j++) {
/* 730 */       i = i * 37 + this.digits[j];
/*     */     }
/*     */     
/* 733 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 742 */       DigitList localDigitList = (DigitList)super.clone();
/* 743 */       char[] arrayOfChar = new char[this.digits.length];
/* 744 */       System.arraycopy(this.digits, 0, arrayOfChar, 0, this.digits.length);
/* 745 */       localDigitList.digits = arrayOfChar;
/* 746 */       localDigitList.tempBuffer = null;
/* 747 */       return localDigitList;
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 749 */       throw new InternalError(localCloneNotSupportedException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isLongMIN_VALUE()
/*     */   {
/* 758 */     if ((this.decimalAt != this.count) || (this.count != 19)) {
/* 759 */       return false;
/*     */     }
/*     */     
/* 762 */     for (int i = 0; i < this.count; i++) {
/* 763 */       if (this.digits[i] != LONG_MIN_REP[i]) { return false;
/*     */       }
/*     */     }
/* 766 */     return true;
/*     */   }
/*     */   
/*     */   private static final int parseInt(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 771 */     int j = 1;
/* 772 */     int i; if ((i = paramArrayOfChar[paramInt1]) == '-') {
/* 773 */       j = 0;
/* 774 */       paramInt1++;
/* 775 */     } else if (i == 43) {
/* 776 */       paramInt1++;
/*     */     }
/*     */     
/* 779 */     int k = 0;
/* 780 */     while (paramInt1 < paramInt2) {
/* 781 */       i = paramArrayOfChar[(paramInt1++)];
/* 782 */       if ((i < 48) || (i > 57)) break;
/* 783 */       k = k * 10 + (i - 48);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 788 */     return j != 0 ? k : -k;
/*     */   }
/*     */   
/*     */ 
/* 792 */   private static final char[] LONG_MIN_REP = "9223372036854775808".toCharArray();
/*     */   private StringBuffer tempBuffer;
/*     */   
/* 795 */   public String toString() { if (isZero()) {
/* 796 */       return "0";
/*     */     }
/* 798 */     StringBuffer localStringBuffer = getStringBuffer();
/* 799 */     localStringBuffer.append("0.");
/* 800 */     localStringBuffer.append(this.digits, 0, this.count);
/* 801 */     localStringBuffer.append("x10^");
/* 802 */     localStringBuffer.append(this.decimalAt);
/* 803 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   private StringBuffer getStringBuffer()
/*     */   {
/* 809 */     if (this.tempBuffer == null) {
/* 810 */       this.tempBuffer = new StringBuffer(19);
/*     */     } else {
/* 812 */       this.tempBuffer.setLength(0);
/*     */     }
/* 814 */     return this.tempBuffer;
/*     */   }
/*     */   
/*     */   private void extendDigits(int paramInt) {
/* 818 */     if (paramInt > this.digits.length) {
/* 819 */       this.digits = new char[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   private final char[] getDataChars(int paramInt) {
/* 824 */     if ((this.data == null) || (this.data.length < paramInt)) {
/* 825 */       this.data = new char[paramInt];
/*     */     }
/* 827 */     return this.data;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/DigitList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */