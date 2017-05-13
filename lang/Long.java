/*      */ package java.lang;
/*      */ 
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Long
/*      */   extends Number
/*      */   implements Comparable<Long>
/*      */ {
/*      */   public static final long MIN_VALUE = Long.MIN_VALUE;
/*      */   public static final long MAX_VALUE = Long.MAX_VALUE;
/*   74 */   public static final Class<Long> TYPE = Class.getPrimitiveClass("long");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final long value;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int SIZE = 64;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int BYTES = 8;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 4290774380558885855L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toString(long paramLong, int paramInt)
/*      */   {
/*  121 */     if ((paramInt < 2) || (paramInt > 36))
/*  122 */       paramInt = 10;
/*  123 */     if (paramInt == 10)
/*  124 */       return toString(paramLong);
/*  125 */     char[] arrayOfChar = new char[65];
/*  126 */     int i = 64;
/*  127 */     int j = paramLong < 0L ? 1 : 0;
/*      */     
/*  129 */     if (j == 0) {
/*  130 */       paramLong = -paramLong;
/*      */     }
/*      */     
/*  133 */     while (paramLong <= -paramInt) {
/*  134 */       arrayOfChar[(i--)] = Integer.digits[((int)-(paramLong % paramInt))];
/*  135 */       paramLong /= paramInt;
/*      */     }
/*  137 */     arrayOfChar[i] = Integer.digits[((int)-paramLong)];
/*      */     
/*  139 */     if (j != 0) {
/*  140 */       arrayOfChar[(--i)] = '-';
/*      */     }
/*      */     
/*  143 */     return new String(arrayOfChar, i, 65 - i);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toUnsignedString(long paramLong, int paramInt)
/*      */   {
/*  173 */     if (paramLong >= 0L) {
/*  174 */       return toString(paramLong, paramInt);
/*      */     }
/*  176 */     switch (paramInt) {
/*      */     case 2: 
/*  178 */       return toBinaryString(paramLong);
/*      */     
/*      */     case 4: 
/*  181 */       return toUnsignedString0(paramLong, 2);
/*      */     
/*      */     case 8: 
/*  184 */       return toOctalString(paramLong);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 10: 
/*  195 */       long l1 = (paramLong >>> 1) / 5L;
/*  196 */       long l2 = paramLong - l1 * 10L;
/*  197 */       return toString(l1) + l2;
/*      */     
/*      */     case 16: 
/*  200 */       return toHexString(paramLong);
/*      */     
/*      */     case 32: 
/*  203 */       return toUnsignedString0(paramLong, 5);
/*      */     }
/*      */     
/*  206 */     return toUnsignedBigInteger(paramLong).toString(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger toUnsignedBigInteger(long paramLong)
/*      */   {
/*  216 */     if (paramLong >= 0L) {
/*  217 */       return BigInteger.valueOf(paramLong);
/*      */     }
/*  219 */     int i = (int)(paramLong >>> 32);
/*  220 */     int j = (int)paramLong;
/*      */     
/*      */ 
/*      */ 
/*  224 */     return BigInteger.valueOf(Integer.toUnsignedLong(i)).shiftLeft(32).add(BigInteger.valueOf(Integer.toUnsignedLong(j)));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toHexString(long paramLong)
/*      */   {
/*  272 */     return toUnsignedString0(paramLong, 4);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toOctalString(long paramLong)
/*      */   {
/*  311 */     return toUnsignedString0(paramLong, 3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toBinaryString(long paramLong)
/*      */   {
/*  344 */     return toUnsignedString0(paramLong, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static String toUnsignedString0(long paramLong, int paramInt)
/*      */   {
/*  354 */     int i = 64 - numberOfLeadingZeros(paramLong);
/*  355 */     int j = Math.max((i + (paramInt - 1)) / paramInt, 1);
/*  356 */     char[] arrayOfChar = new char[j];
/*      */     
/*  358 */     formatUnsignedLong(paramLong, paramInt, arrayOfChar, 0, j);
/*  359 */     return new String(arrayOfChar, true);
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
/*      */   static int formatUnsignedLong(long paramLong, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*      */   {
/*  372 */     int i = paramInt3;
/*  373 */     int j = 1 << paramInt1;
/*  374 */     int k = j - 1;
/*      */     do {
/*  376 */       paramArrayOfChar[(paramInt2 + --i)] = Integer.digits[((int)paramLong & k)];
/*  377 */       paramLong >>>= paramInt1;
/*  378 */     } while ((paramLong != 0L) && (i > 0));
/*      */     
/*  380 */     return i;
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
/*      */   public static String toString(long paramLong)
/*      */   {
/*  394 */     if (paramLong == Long.MIN_VALUE)
/*  395 */       return "-9223372036854775808";
/*  396 */     int i = paramLong < 0L ? stringSize(-paramLong) + 1 : stringSize(paramLong);
/*  397 */     char[] arrayOfChar = new char[i];
/*  398 */     getChars(paramLong, i, arrayOfChar);
/*  399 */     return new String(arrayOfChar, true);
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
/*      */   public static String toUnsignedString(long paramLong)
/*      */   {
/*  417 */     return toUnsignedString(paramLong, 10);
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
/*      */   static void getChars(long paramLong, int paramInt, char[] paramArrayOfChar)
/*      */   {
/*  432 */     int j = paramInt;
/*  433 */     int k = 0;
/*      */     
/*  435 */     if (paramLong < 0L) {
/*  436 */       k = 45;
/*  437 */       paramLong = -paramLong;
/*      */     }
/*      */     
/*      */     int i;
/*  441 */     while (paramLong > 2147483647L) {
/*  442 */       long l = paramLong / 100L;
/*      */       
/*  444 */       i = (int)(paramLong - ((l << 6) + (l << 5) + (l << 2)));
/*  445 */       paramLong = l;
/*  446 */       paramArrayOfChar[(--j)] = Integer.DigitOnes[i];
/*  447 */       paramArrayOfChar[(--j)] = Integer.DigitTens[i];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  452 */     int n = (int)paramLong;
/*  453 */     int m; while (n >= 65536) {
/*  454 */       m = n / 100;
/*      */       
/*  456 */       i = n - ((m << 6) + (m << 5) + (m << 2));
/*  457 */       n = m;
/*  458 */       paramArrayOfChar[(--j)] = Integer.DigitOnes[i];
/*  459 */       paramArrayOfChar[(--j)] = Integer.DigitTens[i];
/*      */     }
/*      */     
/*      */ 
/*      */     for (;;)
/*      */     {
/*  465 */       m = n * 52429 >>> 19;
/*  466 */       i = n - ((m << 3) + (m << 1));
/*  467 */       paramArrayOfChar[(--j)] = Integer.digits[i];
/*  468 */       n = m;
/*  469 */       if (n == 0) break;
/*      */     }
/*  471 */     if (k != 0) {
/*  472 */       paramArrayOfChar[(--j)] = k;
/*      */     }
/*      */   }
/*      */   
/*      */   static int stringSize(long paramLong)
/*      */   {
/*  478 */     long l = 10L;
/*  479 */     for (int i = 1; i < 19; i++) {
/*  480 */       if (paramLong < l)
/*  481 */         return i;
/*  482 */       l = 10L * l;
/*      */     }
/*  484 */     return 19;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long parseLong(String paramString, int paramInt)
/*      */     throws NumberFormatException
/*      */   {
/*  551 */     if (paramString == null) {
/*  552 */       throw new NumberFormatException("null");
/*      */     }
/*      */     
/*  555 */     if (paramInt < 2) {
/*  556 */       throw new NumberFormatException("radix " + paramInt + " less than Character.MIN_RADIX");
/*      */     }
/*      */     
/*  559 */     if (paramInt > 36) {
/*  560 */       throw new NumberFormatException("radix " + paramInt + " greater than Character.MAX_RADIX");
/*      */     }
/*      */     
/*      */ 
/*  564 */     long l1 = 0L;
/*  565 */     int i = 0;
/*  566 */     int j = 0;int k = paramString.length();
/*  567 */     long l2 = -9223372036854775807L;
/*      */     
/*      */ 
/*      */ 
/*  571 */     if (k > 0) {
/*  572 */       int n = paramString.charAt(0);
/*  573 */       if (n < 48) {
/*  574 */         if (n == 45) {
/*  575 */           i = 1;
/*  576 */           l2 = Long.MIN_VALUE;
/*  577 */         } else if (n != 43) {
/*  578 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  580 */         if (k == 1)
/*  581 */           throw NumberFormatException.forInputString(paramString);
/*  582 */         j++;
/*      */       }
/*  584 */       long l3 = l2 / paramInt;
/*  585 */       while (j < k)
/*      */       {
/*  587 */         int m = Character.digit(paramString.charAt(j++), paramInt);
/*  588 */         if (m < 0) {
/*  589 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  591 */         if (l1 < l3) {
/*  592 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  594 */         l1 *= paramInt;
/*  595 */         if (l1 < l2 + m) {
/*  596 */           throw NumberFormatException.forInputString(paramString);
/*      */         }
/*  598 */         l1 -= m;
/*      */       }
/*      */     } else {
/*  601 */       throw NumberFormatException.forInputString(paramString);
/*      */     }
/*  603 */     return i != 0 ? l1 : -l1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long parseLong(String paramString)
/*      */     throws NumberFormatException
/*      */   {
/*  631 */     return parseLong(paramString, 10);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long parseUnsignedLong(String paramString, int paramInt)
/*      */     throws NumberFormatException
/*      */   {
/*  679 */     if (paramString == null) {
/*  680 */       throw new NumberFormatException("null");
/*      */     }
/*      */     
/*  683 */     int i = paramString.length();
/*  684 */     if (i > 0) {
/*  685 */       int j = paramString.charAt(0);
/*  686 */       if (j == 45)
/*      */       {
/*  688 */         throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s.", new Object[] { paramString }));
/*      */       }
/*      */       
/*  691 */       if ((i <= 12) || ((paramInt == 10) && (i <= 18)))
/*      */       {
/*  693 */         return parseLong(paramString, paramInt);
/*      */       }
/*      */       
/*      */ 
/*  697 */       long l1 = parseLong(paramString.substring(0, i - 1), paramInt);
/*  698 */       int k = Character.digit(paramString.charAt(i - 1), paramInt);
/*  699 */       if (k < 0) {
/*  700 */         throw new NumberFormatException("Bad digit at end of " + paramString);
/*      */       }
/*  702 */       long l2 = l1 * paramInt + k;
/*  703 */       if (compareUnsigned(l2, l1) < 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  719 */         throw new NumberFormatException(String.format("String value %s exceeds range of unsigned long.", new Object[] { paramString }));
/*      */       }
/*      */       
/*  722 */       return l2;
/*      */     }
/*      */     
/*  725 */     throw NumberFormatException.forInputString(paramString);
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
/*      */ 
/*      */   public static long parseUnsignedLong(String paramString)
/*      */     throws NumberFormatException
/*      */   {
/*  746 */     return parseUnsignedLong(paramString, 10);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long valueOf(String paramString, int paramInt)
/*      */     throws NumberFormatException
/*      */   {
/*  776 */     return valueOf(parseLong(paramString, paramInt));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long valueOf(String paramString)
/*      */     throws NumberFormatException
/*      */   {
/*  803 */     return valueOf(parseLong(paramString, 10));
/*      */   }
/*      */   
/*      */ 
/*      */   private static class LongCache
/*      */   {
/*  809 */     static final Long[] cache = new Long['Ā'];
/*      */     
/*      */     static {
/*  812 */       for (int i = 0; i < cache.length; i++) {
/*  813 */         cache[i] = new Long(i - 128);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long valueOf(long paramLong)
/*      */   {
/*  837 */     if ((paramLong >= -128L) && (paramLong <= 127L)) {
/*  838 */       return LongCache.cache[((int)paramLong + 128)];
/*      */     }
/*  840 */     return new Long(paramLong);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long decode(String paramString)
/*      */     throws NumberFormatException
/*      */   {
/*  887 */     int i = 10;
/*  888 */     int j = 0;
/*  889 */     int k = 0;
/*      */     
/*      */ 
/*  892 */     if (paramString.length() == 0)
/*  893 */       throw new NumberFormatException("Zero length string");
/*  894 */     int m = paramString.charAt(0);
/*      */     
/*  896 */     if (m == 45) {
/*  897 */       k = 1;
/*  898 */       j++;
/*  899 */     } else if (m == 43) {
/*  900 */       j++;
/*      */     }
/*      */     
/*  903 */     if ((paramString.startsWith("0x", j)) || (paramString.startsWith("0X", j))) {
/*  904 */       j += 2;
/*  905 */       i = 16;
/*      */     }
/*  907 */     else if (paramString.startsWith("#", j)) {
/*  908 */       j++;
/*  909 */       i = 16;
/*      */     }
/*  911 */     else if ((paramString.startsWith("0", j)) && (paramString.length() > 1 + j)) {
/*  912 */       j++;
/*  913 */       i = 8;
/*      */     }
/*      */     
/*  916 */     if ((paramString.startsWith("-", j)) || (paramString.startsWith("+", j)))
/*  917 */       throw new NumberFormatException("Sign character in wrong position");
/*      */     Long localLong;
/*      */     try {
/*  920 */       localLong = valueOf(paramString.substring(j), i);
/*  921 */       localLong = k != 0 ? valueOf(-localLong.longValue()) : localLong;
/*      */ 
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException)
/*      */     {
/*      */ 
/*  927 */       String str = k != 0 ? "-" + paramString.substring(j) : paramString.substring(j);
/*  928 */       localLong = valueOf(str, i);
/*      */     }
/*  930 */     return localLong;
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
/*      */   public Long(long paramLong)
/*      */   {
/*  948 */     this.value = paramLong;
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
/*      */   public Long(String paramString)
/*      */     throws NumberFormatException
/*      */   {
/*  965 */     this.value = parseLong(paramString, 10);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte byteValue()
/*      */   {
/*  974 */     return (byte)(int)this.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short shortValue()
/*      */   {
/*  983 */     return (short)(int)this.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int intValue()
/*      */   {
/*  992 */     return (int)this.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public long longValue()
/*      */   {
/* 1000 */     return this.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float floatValue()
/*      */   {
/* 1009 */     return (float)this.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double doubleValue()
/*      */   {
/* 1018 */     return this.value;
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
/*      */   public String toString()
/*      */   {
/* 1032 */     return toString(this.value);
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
/*      */   public int hashCode()
/*      */   {
/* 1049 */     return hashCode(this.value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int hashCode(long paramLong)
/*      */   {
/* 1061 */     return (int)(paramLong ^ paramLong >>> 32);
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1075 */     if ((paramObject instanceof Long)) {
/* 1076 */       return this.value == ((Long)paramObject).longValue();
/*      */     }
/* 1078 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long getLong(String paramString)
/*      */   {
/* 1112 */     return getLong(paramString, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long getLong(String paramString, long paramLong)
/*      */   {
/* 1157 */     Long localLong = getLong(paramString, null);
/* 1158 */     return localLong == null ? valueOf(paramLong) : localLong;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long getLong(String paramString, Long paramLong)
/*      */   {
/* 1206 */     String str = null;
/*      */     try {
/* 1208 */       str = System.getProperty(paramString);
/*      */     }
/*      */     catch (IllegalArgumentException|NullPointerException localIllegalArgumentException) {}
/* 1211 */     if (str != null) {
/*      */       try {
/* 1213 */         return decode(str);
/*      */       }
/*      */       catch (NumberFormatException localNumberFormatException) {}
/*      */     }
/* 1217 */     return paramLong;
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
/*      */   public int compareTo(Long paramLong)
/*      */   {
/* 1234 */     return compare(this.value, paramLong.value);
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
/*      */   public static int compare(long paramLong1, long paramLong2)
/*      */   {
/* 1252 */     return paramLong1 == paramLong2 ? 0 : paramLong1 < paramLong2 ? -1 : 1;
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
/*      */   public static int compareUnsigned(long paramLong1, long paramLong2)
/*      */   {
/* 1268 */     return compare(paramLong1 + Long.MIN_VALUE, paramLong2 + Long.MIN_VALUE);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long divideUnsigned(long paramLong1, long paramLong2)
/*      */   {
/* 1291 */     if (paramLong2 < 0L)
/*      */     {
/*      */ 
/* 1294 */       return compareUnsigned(paramLong1, paramLong2) < 0 ? 0L : 1L;
/*      */     }
/*      */     
/* 1297 */     if (paramLong1 > 0L) {
/* 1298 */       return paramLong1 / paramLong2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1307 */     return toUnsignedBigInteger(paramLong1).divide(toUnsignedBigInteger(paramLong2)).longValue();
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
/*      */   public static long remainderUnsigned(long paramLong1, long paramLong2)
/*      */   {
/* 1324 */     if ((paramLong1 > 0L) && (paramLong2 > 0L)) {
/* 1325 */       return paramLong1 % paramLong2;
/*      */     }
/* 1327 */     if (compareUnsigned(paramLong1, paramLong2) < 0) {
/* 1328 */       return paramLong1;
/*      */     }
/*      */     
/* 1331 */     return toUnsignedBigInteger(paramLong1).remainder(toUnsignedBigInteger(paramLong2)).longValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long highestOneBit(long paramLong)
/*      */   {
/* 1368 */     paramLong |= paramLong >> 1;
/* 1369 */     paramLong |= paramLong >> 2;
/* 1370 */     paramLong |= paramLong >> 4;
/* 1371 */     paramLong |= paramLong >> 8;
/* 1372 */     paramLong |= paramLong >> 16;
/* 1373 */     paramLong |= paramLong >> 32;
/* 1374 */     return paramLong - (paramLong >>> 1);
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
/*      */   public static long lowestOneBit(long paramLong)
/*      */   {
/* 1392 */     return paramLong & -paramLong;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int numberOfLeadingZeros(long paramLong)
/*      */   {
/* 1418 */     if (paramLong == 0L)
/* 1419 */       return 64;
/* 1420 */     int i = 1;
/* 1421 */     int j = (int)(paramLong >>> 32);
/* 1422 */     if (j == 0) { i += 32;j = (int)paramLong; }
/* 1423 */     if (j >>> 16 == 0) { i += 16;j <<= 16; }
/* 1424 */     if (j >>> 24 == 0) { i += 8;j <<= 8; }
/* 1425 */     if (j >>> 28 == 0) { i += 4;j <<= 4; }
/* 1426 */     if (j >>> 30 == 0) { i += 2;j <<= 2; }
/* 1427 */     i -= (j >>> 31);
/* 1428 */     return i;
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
/*      */ 
/*      */   public static int numberOfTrailingZeros(long paramLong)
/*      */   {
/* 1448 */     if (paramLong == 0L) return 64;
/* 1449 */     int k = 63;
/* 1450 */     int j = (int)paramLong; int i; if (j != 0) { k -= 32;i = j; } else { i = (int)(paramLong >>> 32); }
/* 1451 */     j = i << 16; if (j != 0) { k -= 16;i = j; }
/* 1452 */     j = i << 8; if (j != 0) { k -= 8;i = j; }
/* 1453 */     j = i << 4; if (j != 0) { k -= 4;i = j; }
/* 1454 */     j = i << 2; if (j != 0) { k -= 2;i = j; }
/* 1455 */     return k - (i << 1 >>> 31);
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
/*      */   public static int bitCount(long paramLong)
/*      */   {
/* 1470 */     paramLong -= (paramLong >>> 1 & 0x5555555555555555);
/* 1471 */     paramLong = (paramLong & 0x3333333333333333) + (paramLong >>> 2 & 0x3333333333333333);
/* 1472 */     paramLong = paramLong + (paramLong >>> 4) & 0xF0F0F0F0F0F0F0F;
/* 1473 */     paramLong += (paramLong >>> 8);
/* 1474 */     paramLong += (paramLong >>> 16);
/* 1475 */     paramLong += (paramLong >>> 32);
/* 1476 */     return (int)paramLong & 0x7F;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long rotateLeft(long paramLong, int paramInt)
/*      */   {
/* 1500 */     return paramLong << paramInt | paramLong >>> -paramInt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long rotateRight(long paramLong, int paramInt)
/*      */   {
/* 1524 */     return paramLong >>> paramInt | paramLong << -paramInt;
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
/*      */   public static long reverse(long paramLong)
/*      */   {
/* 1539 */     paramLong = (paramLong & 0x5555555555555555) << 1 | paramLong >>> 1 & 0x5555555555555555;
/* 1540 */     paramLong = (paramLong & 0x3333333333333333) << 2 | paramLong >>> 2 & 0x3333333333333333;
/* 1541 */     paramLong = (paramLong & 0xF0F0F0F0F0F0F0F) << 4 | paramLong >>> 4 & 0xF0F0F0F0F0F0F0F;
/* 1542 */     paramLong = (paramLong & 0xFF00FF00FF00FF) << 8 | paramLong >>> 8 & 0xFF00FF00FF00FF;
/* 1543 */     paramLong = paramLong << 48 | (paramLong & 0xFFFF0000) << 16 | paramLong >>> 16 & 0xFFFF0000 | paramLong >>> 48;
/*      */     
/* 1545 */     return paramLong;
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
/*      */   public static int signum(long paramLong)
/*      */   {
/* 1559 */     return (int)(paramLong >> 63 | -paramLong >>> 63);
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
/*      */   public static long reverseBytes(long paramLong)
/*      */   {
/* 1572 */     paramLong = (paramLong & 0xFF00FF00FF00FF) << 8 | paramLong >>> 8 & 0xFF00FF00FF00FF;
/* 1573 */     return paramLong << 48 | (paramLong & 0xFFFF0000) << 16 | paramLong >>> 16 & 0xFFFF0000 | paramLong >>> 48;
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
/*      */   public static long sum(long paramLong1, long paramLong2)
/*      */   {
/* 1587 */     return paramLong1 + paramLong2;
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
/*      */   public static long max(long paramLong1, long paramLong2)
/*      */   {
/* 1601 */     return Math.max(paramLong1, paramLong2);
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
/*      */   public static long min(long paramLong1, long paramLong2)
/*      */   {
/* 1615 */     return Math.min(paramLong1, paramLong2);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Long.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */