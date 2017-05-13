/*      */ package java.math;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ThreadLocalRandom;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigInteger
/*      */   extends Number
/*      */   implements Comparable<BigInteger>
/*      */ {
/*      */   final int signum;
/*      */   final int[] mag;
/*      */   @Deprecated
/*      */   private int bitCount;
/*      */   @Deprecated
/*      */   private int bitLength;
/*      */   @Deprecated
/*      */   private int lowestSetBit;
/*      */   @Deprecated
/*      */   private int firstNonzeroIntNum;
/*      */   static final long LONG_MASK = 4294967295L;
/*      */   private static final int MAX_MAG_LENGTH = 67108864;
/*      */   private static final int PRIME_SEARCH_BIT_LENGTH_LIMIT = 500000000;
/*      */   private static final int KARATSUBA_THRESHOLD = 80;
/*      */   private static final int TOOM_COOK_THRESHOLD = 240;
/*      */   private static final int KARATSUBA_SQUARE_THRESHOLD = 128;
/*      */   private static final int TOOM_COOK_SQUARE_THRESHOLD = 216;
/*      */   static final int BURNIKEL_ZIEGLER_THRESHOLD = 80;
/*      */   static final int BURNIKEL_ZIEGLER_OFFSET = 40;
/*      */   private static final int SCHOENHAGE_BASE_CONVERSION_THRESHOLD = 20;
/*      */   private static final int MULTIPLY_SQUARE_THRESHOLD = 20;
/*      */   private static long[] bitsPerDigit;
/*      */   private static final int SMALL_PRIME_THRESHOLD = 95;
/*      */   private static final int DEFAULT_PRIME_CERTAINTY = 100;
/*      */   private static final BigInteger SMALL_PRIME_PRODUCT;
/*      */   private static final int MAX_CONSTANT = 16;
/*      */   private static BigInteger[] posConst;
/*      */   private static BigInteger[] negConst;
/*      */   private static volatile BigInteger[][] powerCache;
/*      */   private static final double[] logCache;
/*      */   private static final double LOG_TWO;
/*      */   public static final BigInteger ZERO;
/*      */   public static final BigInteger ONE;
/*      */   private static final BigInteger TWO;
/*      */   private static final BigInteger NEGATIVE_ONE;
/*      */   public static final BigInteger TEN;
/*      */   static int[] bnExpModThreshTable;
/*      */   private static String[] zeros;
/*      */   
/*      */   public BigInteger(byte[] paramArrayOfByte)
/*      */   {
/*  292 */     if (paramArrayOfByte.length == 0) {
/*  293 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*  295 */     if (paramArrayOfByte[0] < 0) {
/*  296 */       this.mag = makePositive(paramArrayOfByte);
/*  297 */       this.signum = -1;
/*      */     } else {
/*  299 */       this.mag = stripLeadingZeroBytes(paramArrayOfByte);
/*  300 */       this.signum = (this.mag.length == 0 ? 0 : 1);
/*      */     }
/*  302 */     if (this.mag.length >= 67108864) {
/*  303 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger(int[] paramArrayOfInt)
/*      */   {
/*  314 */     if (paramArrayOfInt.length == 0) {
/*  315 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*  317 */     if (paramArrayOfInt[0] < 0) {
/*  318 */       this.mag = makePositive(paramArrayOfInt);
/*  319 */       this.signum = -1;
/*      */     } else {
/*  321 */       this.mag = trustedStripLeadingZeroInts(paramArrayOfInt);
/*  322 */       this.signum = (this.mag.length == 0 ? 0 : 1);
/*      */     }
/*  324 */     if (this.mag.length >= 67108864) {
/*  325 */       checkRange();
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
/*      */   public BigInteger(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/*  346 */     this.mag = stripLeadingZeroBytes(paramArrayOfByte);
/*      */     
/*  348 */     if ((paramInt < -1) || (paramInt > 1)) {
/*  349 */       throw new NumberFormatException("Invalid signum value");
/*      */     }
/*  351 */     if (this.mag.length == 0) {
/*  352 */       this.signum = 0;
/*      */     } else {
/*  354 */       if (paramInt == 0)
/*  355 */         throw new NumberFormatException("signum-magnitude mismatch");
/*  356 */       this.signum = paramInt;
/*      */     }
/*  358 */     if (this.mag.length >= 67108864) {
/*  359 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  370 */     this.mag = stripLeadingZeroInts(paramArrayOfInt);
/*      */     
/*  372 */     if ((paramInt < -1) || (paramInt > 1)) {
/*  373 */       throw new NumberFormatException("Invalid signum value");
/*      */     }
/*  375 */     if (this.mag.length == 0) {
/*  376 */       this.signum = 0;
/*      */     } else {
/*  378 */       if (paramInt == 0)
/*  379 */         throw new NumberFormatException("signum-magnitude mismatch");
/*  380 */       this.signum = paramInt;
/*      */     }
/*  382 */     if (this.mag.length >= 67108864) {
/*  383 */       checkRange();
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
/*      */   public BigInteger(String paramString, int paramInt)
/*      */   {
/*  405 */     int i = 0;
/*  406 */     int k = paramString.length();
/*      */     
/*  408 */     if ((paramInt < 2) || (paramInt > 36))
/*  409 */       throw new NumberFormatException("Radix out of range");
/*  410 */     if (k == 0) {
/*  411 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*      */     
/*  414 */     int m = 1;
/*  415 */     int n = paramString.lastIndexOf('-');
/*  416 */     int i1 = paramString.lastIndexOf('+');
/*  417 */     if (n >= 0) {
/*  418 */       if ((n != 0) || (i1 >= 0)) {
/*  419 */         throw new NumberFormatException("Illegal embedded sign character");
/*      */       }
/*  421 */       m = -1;
/*  422 */       i = 1;
/*  423 */     } else if (i1 >= 0) {
/*  424 */       if (i1 != 0) {
/*  425 */         throw new NumberFormatException("Illegal embedded sign character");
/*      */       }
/*  427 */       i = 1;
/*      */     }
/*  429 */     if (i == k) {
/*  430 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*      */     
/*  433 */     while ((i < k) && 
/*  434 */       (Character.digit(paramString.charAt(i), paramInt) == 0)) {
/*  435 */       i++;
/*      */     }
/*      */     
/*  438 */     if (i == k) {
/*  439 */       this.signum = 0;
/*  440 */       this.mag = ZERO.mag;
/*  441 */       return;
/*      */     }
/*      */     
/*  444 */     int j = k - i;
/*  445 */     this.signum = m;
/*      */     
/*      */ 
/*      */ 
/*  449 */     long l = (j * bitsPerDigit[paramInt] >>> 10) + 1L;
/*  450 */     if (l + 31L >= 4294967296L) {
/*  451 */       reportOverflow();
/*      */     }
/*  453 */     int i2 = (int)(l + 31L) >>> 5;
/*  454 */     int[] arrayOfInt = new int[i2];
/*      */     
/*      */ 
/*  457 */     int i3 = j % digitsPerInt[paramInt];
/*  458 */     if (i3 == 0)
/*  459 */       i3 = digitsPerInt[paramInt];
/*  460 */     String str = paramString.substring(i, i += i3);
/*  461 */     arrayOfInt[(i2 - 1)] = Integer.parseInt(str, paramInt);
/*  462 */     if (arrayOfInt[(i2 - 1)] < 0) {
/*  463 */       throw new NumberFormatException("Illegal digit");
/*      */     }
/*      */     
/*  466 */     int i4 = intRadix[paramInt];
/*  467 */     int i5 = 0;
/*  468 */     while (i < k) {
/*  469 */       str = paramString.substring(i, i += digitsPerInt[paramInt]);
/*  470 */       i5 = Integer.parseInt(str, paramInt);
/*  471 */       if (i5 < 0)
/*  472 */         throw new NumberFormatException("Illegal digit");
/*  473 */       destructiveMulAdd(arrayOfInt, i4, i5);
/*      */     }
/*      */     
/*  476 */     this.mag = trustedStripLeadingZeroInts(arrayOfInt);
/*  477 */     if (this.mag.length >= 67108864) {
/*  478 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  487 */     int i = 0;
/*      */     
/*      */ 
/*  490 */     while ((i < paramInt2) && (Character.digit(paramArrayOfChar[i], 10) == 0)) {
/*  491 */       i++;
/*      */     }
/*  493 */     if (i == paramInt2) {
/*  494 */       this.signum = 0;
/*  495 */       this.mag = ZERO.mag;
/*  496 */       return;
/*      */     }
/*      */     
/*  499 */     int j = paramInt2 - i;
/*  500 */     this.signum = paramInt1;
/*      */     
/*      */     int k;
/*  503 */     if (paramInt2 < 10) {
/*  504 */       k = 1;
/*      */     } else {
/*  506 */       long l = (j * bitsPerDigit[10] >>> 10) + 1L;
/*  507 */       if (l + 31L >= 4294967296L) {
/*  508 */         reportOverflow();
/*      */       }
/*  510 */       k = (int)(l + 31L) >>> 5;
/*      */     }
/*  512 */     int[] arrayOfInt = new int[k];
/*      */     
/*      */ 
/*  515 */     int m = j % digitsPerInt[10];
/*  516 */     if (m == 0)
/*  517 */       m = digitsPerInt[10];
/*  518 */     arrayOfInt[(k - 1)] = parseInt(paramArrayOfChar, i, i += m);
/*      */     
/*      */ 
/*  521 */     while (i < paramInt2) {
/*  522 */       int n = parseInt(paramArrayOfChar, i, i += digitsPerInt[10]);
/*  523 */       destructiveMulAdd(arrayOfInt, intRadix[10], n);
/*      */     }
/*  525 */     this.mag = trustedStripLeadingZeroInts(arrayOfInt);
/*  526 */     if (this.mag.length >= 67108864) {
/*  527 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int parseInt(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  535 */     int i = Character.digit(paramArrayOfChar[(paramInt1++)], 10);
/*  536 */     if (i == -1) {
/*  537 */       throw new NumberFormatException(new String(paramArrayOfChar));
/*      */     }
/*  539 */     for (int j = paramInt1; j < paramInt2; j++) {
/*  540 */       int k = Character.digit(paramArrayOfChar[j], 10);
/*  541 */       if (k == -1)
/*  542 */         throw new NumberFormatException(new String(paramArrayOfChar));
/*  543 */       i = 10 * i + k;
/*      */     }
/*      */     
/*  546 */     return i;
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
/*      */   private static void destructiveMulAdd(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/*  560 */     long l1 = paramInt1 & 0xFFFFFFFF;
/*  561 */     long l2 = paramInt2 & 0xFFFFFFFF;
/*  562 */     int i = paramArrayOfInt.length;
/*      */     
/*  564 */     long l3 = 0L;
/*  565 */     long l4 = 0L;
/*  566 */     for (int j = i - 1; j >= 0; j--) {
/*  567 */       l3 = l1 * (paramArrayOfInt[j] & 0xFFFFFFFF) + l4;
/*  568 */       paramArrayOfInt[j] = ((int)l3);
/*  569 */       l4 = l3 >>> 32;
/*      */     }
/*      */     
/*      */ 
/*  573 */     long l5 = (paramArrayOfInt[(i - 1)] & 0xFFFFFFFF) + l2;
/*  574 */     paramArrayOfInt[(i - 1)] = ((int)l5);
/*  575 */     l4 = l5 >>> 32;
/*  576 */     for (int k = i - 2; k >= 0; k--) {
/*  577 */       l5 = (paramArrayOfInt[k] & 0xFFFFFFFF) + l4;
/*  578 */       paramArrayOfInt[k] = ((int)l5);
/*  579 */       l4 = l5 >>> 32;
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
/*      */   public BigInteger(String paramString)
/*      */   {
/*  597 */     this(paramString, 10);
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
/*      */   public BigInteger(int paramInt, Random paramRandom)
/*      */   {
/*  614 */     this(1, randomBits(paramInt, paramRandom));
/*      */   }
/*      */   
/*      */   private static byte[] randomBits(int paramInt, Random paramRandom) {
/*  618 */     if (paramInt < 0)
/*  619 */       throw new IllegalArgumentException("numBits must be non-negative");
/*  620 */     int i = (int)((paramInt + 7L) / 8L);
/*  621 */     byte[] arrayOfByte = new byte[i];
/*      */     
/*      */ 
/*  624 */     if (i > 0) {
/*  625 */       paramRandom.nextBytes(arrayOfByte);
/*  626 */       int j = 8 * i - paramInt; int 
/*  627 */         tmp49_48 = 0; byte[] tmp49_47 = arrayOfByte;tmp49_47[tmp49_48] = ((byte)(tmp49_47[tmp49_48] & (1 << 8 - j) - 1));
/*      */     }
/*  629 */     return arrayOfByte;
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
/*      */   public BigInteger(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  654 */     if (paramInt1 < 2) {
/*  655 */       throw new ArithmeticException("bitLength < 2");
/*      */     }
/*      */     
/*  658 */     BigInteger localBigInteger = paramInt1 < 95 ? smallPrime(paramInt1, paramInt2, paramRandom) : largePrime(paramInt1, paramInt2, paramRandom);
/*  659 */     this.signum = 1;
/*  660 */     this.mag = localBigInteger.mag;
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
/*      */   public static BigInteger probablePrime(int paramInt, Random paramRandom)
/*      */   {
/*  685 */     if (paramInt < 2) {
/*  686 */       throw new ArithmeticException("bitLength < 2");
/*      */     }
/*      */     
/*      */ 
/*  690 */     return paramInt < 95 ? smallPrime(paramInt, 100, paramRandom) : largePrime(paramInt, 100, paramRandom);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger smallPrime(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  701 */     int i = paramInt1 + 31 >>> 5;
/*  702 */     int[] arrayOfInt = new int[i];
/*  703 */     int j = 1 << (paramInt1 + 31 & 0x1F);
/*  704 */     int k = (j << 1) - 1;
/*      */     
/*      */     for (;;)
/*      */     {
/*  708 */       for (int m = 0; m < i; m++)
/*  709 */         arrayOfInt[m] = paramRandom.nextInt();
/*  710 */       arrayOfInt[0] = (arrayOfInt[0] & k | j);
/*  711 */       if (paramInt1 > 2) {
/*  712 */         arrayOfInt[(i - 1)] |= 0x1;
/*      */       }
/*  714 */       BigInteger localBigInteger = new BigInteger(arrayOfInt, 1);
/*      */       
/*      */ 
/*  717 */       if (paramInt1 > 6) {
/*  718 */         long l = localBigInteger.remainder(SMALL_PRIME_PRODUCT).longValue();
/*  719 */         if ((l % 3L == 0L) || (l % 5L == 0L) || (l % 7L == 0L) || (l % 11L == 0L) || (l % 13L == 0L) || (l % 17L == 0L) || (l % 19L == 0L) || (l % 23L == 0L) || (l % 29L == 0L) || (l % 31L == 0L) || (l % 37L == 0L) || (l % 41L == 0L)) {}
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  726 */         if (paramInt1 < 4) {
/*  727 */           return localBigInteger;
/*      */         }
/*      */         
/*  730 */         if (localBigInteger.primeToCertainty(paramInt2, paramRandom)) {
/*  731 */           return localBigInteger;
/*      */         }
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
/*      */   private static BigInteger largePrime(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  746 */     BigInteger localBigInteger1 = new BigInteger(paramInt1, paramRandom).setBit(paramInt1 - 1);
/*  747 */     localBigInteger1.mag[(localBigInteger1.mag.length - 1)] &= 0xFFFFFFFE;
/*      */     
/*      */ 
/*  750 */     int i = getPrimeSearchLen(paramInt1);
/*  751 */     BitSieve localBitSieve = new BitSieve(localBigInteger1, i);
/*  752 */     BigInteger localBigInteger2 = localBitSieve.retrieve(localBigInteger1, paramInt2, paramRandom);
/*      */     
/*  754 */     while ((localBigInteger2 == null) || (localBigInteger2.bitLength() != paramInt1)) {
/*  755 */       localBigInteger1 = localBigInteger1.add(valueOf(2 * i));
/*  756 */       if (localBigInteger1.bitLength() != paramInt1)
/*  757 */         localBigInteger1 = new BigInteger(paramInt1, paramRandom).setBit(paramInt1 - 1);
/*  758 */       localBigInteger1.mag[(localBigInteger1.mag.length - 1)] &= 0xFFFFFFFE;
/*  759 */       localBitSieve = new BitSieve(localBigInteger1, i);
/*  760 */       localBigInteger2 = localBitSieve.retrieve(localBigInteger1, paramInt2, paramRandom);
/*      */     }
/*  762 */     return localBigInteger2;
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
/*      */   public BigInteger nextProbablePrime()
/*      */   {
/*  778 */     if (this.signum < 0) {
/*  779 */       throw new ArithmeticException("start < 0: " + this);
/*      */     }
/*      */     
/*  782 */     if ((this.signum == 0) || (equals(ONE))) {
/*  783 */       return TWO;
/*      */     }
/*  785 */     BigInteger localBigInteger1 = add(ONE);
/*      */     
/*      */ 
/*  788 */     if (localBigInteger1.bitLength() < 95)
/*      */     {
/*      */ 
/*  791 */       if (!localBigInteger1.testBit(0)) {
/*  792 */         localBigInteger1 = localBigInteger1.add(ONE);
/*      */       }
/*      */       for (;;)
/*      */       {
/*  796 */         if (localBigInteger1.bitLength() > 6) {
/*  797 */           long l = localBigInteger1.remainder(SMALL_PRIME_PRODUCT).longValue();
/*  798 */           if ((l % 3L == 0L) || (l % 5L == 0L) || (l % 7L == 0L) || (l % 11L == 0L) || (l % 13L == 0L) || (l % 17L == 0L) || (l % 19L == 0L) || (l % 23L == 0L) || (l % 29L == 0L) || (l % 31L == 0L) || (l % 37L == 0L) || (l % 41L == 0L))
/*      */           {
/*      */ 
/*  801 */             localBigInteger1 = localBigInteger1.add(TWO);
/*  802 */             continue;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  807 */         if (localBigInteger1.bitLength() < 4) {
/*  808 */           return localBigInteger1;
/*      */         }
/*      */         
/*  811 */         if (localBigInteger1.primeToCertainty(100, null)) {
/*  812 */           return localBigInteger1;
/*      */         }
/*  814 */         localBigInteger1 = localBigInteger1.add(TWO);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  819 */     if (localBigInteger1.testBit(0)) {
/*  820 */       localBigInteger1 = localBigInteger1.subtract(ONE);
/*      */     }
/*      */     
/*  823 */     int i = getPrimeSearchLen(localBigInteger1.bitLength());
/*      */     for (;;)
/*      */     {
/*  826 */       BitSieve localBitSieve = new BitSieve(localBigInteger1, i);
/*  827 */       BigInteger localBigInteger2 = localBitSieve.retrieve(localBigInteger1, 100, null);
/*      */       
/*  829 */       if (localBigInteger2 != null)
/*  830 */         return localBigInteger2;
/*  831 */       localBigInteger1 = localBigInteger1.add(valueOf(2 * i));
/*      */     }
/*      */   }
/*      */   
/*      */   private static int getPrimeSearchLen(int paramInt) {
/*  836 */     if (paramInt > 500000001) {
/*  837 */       throw new ArithmeticException("Prime search implementation restriction on bitLength");
/*      */     }
/*  839 */     return paramInt / 20 * 64;
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
/*      */   boolean primeToCertainty(int paramInt, Random paramRandom)
/*      */   {
/*  857 */     int i = 0;
/*  858 */     int j = (Math.min(paramInt, 2147483646) + 1) / 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  863 */     int k = bitLength();
/*  864 */     if (k < 100) {
/*  865 */       i = 50;
/*  866 */       i = j < i ? j : i;
/*  867 */       return passesMillerRabin(i, paramRandom);
/*      */     }
/*      */     
/*  870 */     if (k < 256) {
/*  871 */       i = 27;
/*  872 */     } else if (k < 512) {
/*  873 */       i = 15;
/*  874 */     } else if (k < 768) {
/*  875 */       i = 8;
/*  876 */     } else if (k < 1024) {
/*  877 */       i = 4;
/*      */     } else {
/*  879 */       i = 2;
/*      */     }
/*  881 */     i = j < i ? j : i;
/*      */     
/*  883 */     return (passesMillerRabin(i, paramRandom)) && (passesLucasLehmer());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean passesLucasLehmer()
/*      */   {
/*  893 */     BigInteger localBigInteger1 = add(ONE);
/*      */     
/*      */ 
/*  896 */     int i = 5;
/*  897 */     while (jacobiSymbol(i, this) != -1)
/*      */     {
/*  899 */       i = i < 0 ? Math.abs(i) + 2 : -(i + 2);
/*      */     }
/*      */     
/*      */ 
/*  903 */     BigInteger localBigInteger2 = lucasLehmerSequence(i, localBigInteger1, this);
/*      */     
/*      */ 
/*  906 */     return localBigInteger2.mod(this).equals(ZERO);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int jacobiSymbol(int paramInt, BigInteger paramBigInteger)
/*      */   {
/*  914 */     if (paramInt == 0) {
/*  915 */       return 0;
/*      */     }
/*      */     
/*  918 */     int i = 1;
/*  919 */     int j = paramBigInteger.mag[(paramBigInteger.mag.length - 1)];
/*      */     
/*      */     int k;
/*  922 */     if (paramInt < 0) {
/*  923 */       paramInt = -paramInt;
/*  924 */       k = j & 0x7;
/*  925 */       if ((k == 3) || (k == 7)) {
/*  926 */         i = -i;
/*      */       }
/*      */     }
/*      */     
/*  930 */     while ((paramInt & 0x3) == 0)
/*  931 */       paramInt >>= 2;
/*  932 */     if ((paramInt & 0x1) == 0) {
/*  933 */       paramInt >>= 1;
/*  934 */       if (((j ^ j >> 1) & 0x2) != 0)
/*  935 */         i = -i;
/*      */     }
/*  937 */     if (paramInt == 1) {
/*  938 */       return i;
/*      */     }
/*  940 */     if ((paramInt & j & 0x2) != 0) {
/*  941 */       i = -i;
/*      */     }
/*  943 */     j = paramBigInteger.mod(valueOf(paramInt)).intValue();
/*      */     
/*      */ 
/*  946 */     while (j != 0) {
/*  947 */       while ((j & 0x3) == 0)
/*  948 */         j >>= 2;
/*  949 */       if ((j & 0x1) == 0) {
/*  950 */         j >>= 1;
/*  951 */         if (((paramInt ^ paramInt >> 1) & 0x2) != 0)
/*  952 */           i = -i;
/*      */       }
/*  954 */       if (j == 1) {
/*  955 */         return i;
/*      */       }
/*  957 */       assert (j < paramInt);
/*  958 */       k = j;j = paramInt;paramInt = k;
/*  959 */       if ((j & paramInt & 0x2) != 0) {
/*  960 */         i = -i;
/*      */       }
/*  962 */       j %= paramInt;
/*      */     }
/*  964 */     return 0;
/*      */   }
/*      */   
/*      */   private static BigInteger lucasLehmerSequence(int paramInt, BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/*  968 */     BigInteger localBigInteger1 = valueOf(paramInt);
/*  969 */     Object localObject1 = ONE;
/*  970 */     Object localObject2 = ONE;
/*      */     
/*  972 */     for (int i = paramBigInteger1.bitLength() - 2; i >= 0; i--) {
/*  973 */       BigInteger localBigInteger2 = ((BigInteger)localObject1).multiply((BigInteger)localObject2).mod(paramBigInteger2);
/*      */       
/*  975 */       BigInteger localBigInteger3 = ((BigInteger)localObject2).square().add(localBigInteger1.multiply(((BigInteger)localObject1).square())).mod(paramBigInteger2);
/*  976 */       if (localBigInteger3.testBit(0)) {
/*  977 */         localBigInteger3 = localBigInteger3.subtract(paramBigInteger2);
/*      */       }
/*  979 */       localBigInteger3 = localBigInteger3.shiftRight(1);
/*      */       
/*  981 */       localObject1 = localBigInteger2;localObject2 = localBigInteger3;
/*  982 */       if (paramBigInteger1.testBit(i)) {
/*  983 */         localBigInteger2 = ((BigInteger)localObject1).add((BigInteger)localObject2).mod(paramBigInteger2);
/*  984 */         if (localBigInteger2.testBit(0)) {
/*  985 */           localBigInteger2 = localBigInteger2.subtract(paramBigInteger2);
/*      */         }
/*  987 */         localBigInteger2 = localBigInteger2.shiftRight(1);
/*  988 */         localBigInteger3 = ((BigInteger)localObject2).add(localBigInteger1.multiply((BigInteger)localObject1)).mod(paramBigInteger2);
/*  989 */         if (localBigInteger3.testBit(0))
/*  990 */           localBigInteger3 = localBigInteger3.subtract(paramBigInteger2);
/*  991 */         localBigInteger3 = localBigInteger3.shiftRight(1);
/*      */         
/*  993 */         localObject1 = localBigInteger2;localObject2 = localBigInteger3;
/*      */       }
/*      */     }
/*  996 */     return (BigInteger)localObject1;
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
/*      */   private boolean passesMillerRabin(int paramInt, Random paramRandom)
/*      */   {
/* 1010 */     BigInteger localBigInteger1 = subtract(ONE);
/* 1011 */     BigInteger localBigInteger2 = localBigInteger1;
/* 1012 */     int i = localBigInteger2.getLowestSetBit();
/* 1013 */     localBigInteger2 = localBigInteger2.shiftRight(i);
/*      */     
/*      */ 
/* 1016 */     if (paramRandom == null) {
/* 1017 */       paramRandom = ThreadLocalRandom.current();
/*      */     }
/* 1019 */     for (int j = 0; j < paramInt; j++)
/*      */     {
/*      */       BigInteger localBigInteger3;
/*      */       do {
/* 1023 */         localBigInteger3 = new BigInteger(bitLength(), paramRandom);
/* 1024 */       } while ((localBigInteger3.compareTo(ONE) <= 0) || (localBigInteger3.compareTo(this) >= 0));
/*      */       
/* 1026 */       int k = 0;
/* 1027 */       BigInteger localBigInteger4 = localBigInteger3.modPow(localBigInteger2, this);
/* 1028 */       while (((k != 0) || (!localBigInteger4.equals(ONE))) && (!localBigInteger4.equals(localBigInteger1))) {
/* 1029 */         if ((k <= 0) || (!localBigInteger4.equals(ONE))) { k++; if (k != i) {}
/* 1030 */         } else { return false; }
/* 1031 */         localBigInteger4 = localBigInteger4.modPow(TWO, this);
/*      */       }
/*      */     }
/* 1034 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 1043 */     this.signum = (paramArrayOfInt.length == 0 ? 0 : paramInt);
/* 1044 */     this.mag = paramArrayOfInt;
/* 1045 */     if (this.mag.length >= 67108864) {
/* 1046 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 1055 */     this.signum = (paramArrayOfByte.length == 0 ? 0 : paramInt);
/* 1056 */     this.mag = stripLeadingZeroBytes(paramArrayOfByte);
/* 1057 */     if (this.mag.length >= 67108864) {
/* 1058 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkRange()
/*      */   {
/* 1069 */     if ((this.mag.length > 67108864) || ((this.mag.length == 67108864) && (this.mag[0] < 0))) {
/* 1070 */       reportOverflow();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void reportOverflow() {
/* 1075 */     throw new ArithmeticException("BigInteger would overflow supported range");
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
/*      */   public static BigInteger valueOf(long paramLong)
/*      */   {
/* 1091 */     if (paramLong == 0L)
/* 1092 */       return ZERO;
/* 1093 */     if ((paramLong > 0L) && (paramLong <= 16L))
/* 1094 */       return posConst[((int)paramLong)];
/* 1095 */     if ((paramLong < 0L) && (paramLong >= -16L)) {
/* 1096 */       return negConst[((int)-paramLong)];
/*      */     }
/* 1098 */     return new BigInteger(paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private BigInteger(long paramLong)
/*      */   {
/* 1105 */     if (paramLong < 0L) {
/* 1106 */       paramLong = -paramLong;
/* 1107 */       this.signum = -1;
/*      */     } else {
/* 1109 */       this.signum = 1;
/*      */     }
/*      */     
/* 1112 */     int i = (int)(paramLong >>> 32);
/* 1113 */     if (i == 0) {
/* 1114 */       this.mag = new int[1];
/* 1115 */       this.mag[0] = ((int)paramLong);
/*      */     } else {
/* 1117 */       this.mag = new int[2];
/* 1118 */       this.mag[0] = i;
/* 1119 */       this.mag[1] = ((int)paramLong);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger valueOf(int[] paramArrayOfInt)
/*      */   {
/* 1129 */     return paramArrayOfInt[0] > 0 ? new BigInteger(paramArrayOfInt, 1) : new BigInteger(paramArrayOfInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger add(BigInteger paramBigInteger)
/*      */   {
/* 1216 */     if (paramBigInteger.signum == 0)
/* 1217 */       return this;
/* 1218 */     if (this.signum == 0)
/* 1219 */       return paramBigInteger;
/* 1220 */     if (paramBigInteger.signum == this.signum) {
/* 1221 */       return new BigInteger(add(this.mag, paramBigInteger.mag), this.signum);
/*      */     }
/* 1223 */     int i = compareMagnitude(paramBigInteger);
/* 1224 */     if (i == 0) {
/* 1225 */       return ZERO;
/*      */     }
/* 1227 */     int[] arrayOfInt = i > 0 ? subtract(this.mag, paramBigInteger.mag) : subtract(paramBigInteger.mag, this.mag);
/* 1228 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/*      */     
/* 1230 */     return new BigInteger(arrayOfInt, i == this.signum ? 1 : -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger add(long paramLong)
/*      */   {
/* 1238 */     if (paramLong == 0L)
/* 1239 */       return this;
/* 1240 */     if (this.signum == 0)
/* 1241 */       return valueOf(paramLong);
/* 1242 */     if (Long.signum(paramLong) == this.signum)
/* 1243 */       return new BigInteger(add(this.mag, Math.abs(paramLong)), this.signum);
/* 1244 */     int i = compareMagnitude(paramLong);
/* 1245 */     if (i == 0)
/* 1246 */       return ZERO;
/* 1247 */     int[] arrayOfInt = i > 0 ? subtract(this.mag, Math.abs(paramLong)) : subtract(Math.abs(paramLong), this.mag);
/* 1248 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1249 */     return new BigInteger(arrayOfInt, i == this.signum ? 1 : -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] add(int[] paramArrayOfInt, long paramLong)
/*      */   {
/* 1260 */     long l = 0L;
/* 1261 */     int i = paramArrayOfInt.length;
/*      */     
/* 1263 */     int j = (int)(paramLong >>> 32);
/* 1264 */     int[] arrayOfInt1; if (j == 0) {
/* 1265 */       arrayOfInt1 = new int[i];
/* 1266 */       l = (paramArrayOfInt[(--i)] & 0xFFFFFFFF) + paramLong;
/* 1267 */       arrayOfInt1[i] = ((int)l);
/*      */     } else {
/* 1269 */       if (i == 1) {
/* 1270 */         arrayOfInt1 = new int[2];
/* 1271 */         l = paramLong + (paramArrayOfInt[0] & 0xFFFFFFFF);
/* 1272 */         arrayOfInt1[1] = ((int)l);
/* 1273 */         arrayOfInt1[0] = ((int)(l >>> 32));
/* 1274 */         return arrayOfInt1;
/*      */       }
/* 1276 */       arrayOfInt1 = new int[i];
/* 1277 */       l = (paramArrayOfInt[(--i)] & 0xFFFFFFFF) + (paramLong & 0xFFFFFFFF);
/* 1278 */       arrayOfInt1[i] = ((int)l);
/* 1279 */       l = (paramArrayOfInt[(--i)] & 0xFFFFFFFF) + (j & 0xFFFFFFFF) + (l >>> 32);
/* 1280 */       arrayOfInt1[i] = ((int)l);
/*      */     }
/*      */     
/*      */ 
/* 1284 */     int k = l >>> 32 != 0L ? 1 : 0;
/* 1285 */     while ((i > 0) && (k != 0)) {
/* 1286 */       k = (arrayOfInt1[(--i)] = paramArrayOfInt[i] + 1) == 0 ? 1 : 0;
/*      */     }
/* 1288 */     while (i > 0) {
/* 1289 */       arrayOfInt1[(--i)] = paramArrayOfInt[i];
/*      */     }
/* 1291 */     if (k != 0) {
/* 1292 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 1293 */       System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 1, arrayOfInt1.length);
/* 1294 */       arrayOfInt2[0] = 1;
/* 1295 */       return arrayOfInt2;
/*      */     }
/* 1297 */     return arrayOfInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] add(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1307 */     if (paramArrayOfInt1.length < paramArrayOfInt2.length) {
/* 1308 */       int[] arrayOfInt1 = paramArrayOfInt1;
/* 1309 */       paramArrayOfInt1 = paramArrayOfInt2;
/* 1310 */       paramArrayOfInt2 = arrayOfInt1;
/*      */     }
/*      */     
/* 1313 */     int i = paramArrayOfInt1.length;
/* 1314 */     int j = paramArrayOfInt2.length;
/* 1315 */     int[] arrayOfInt2 = new int[i];
/* 1316 */     long l = 0L;
/* 1317 */     if (j == 1) {
/* 1318 */       l = (paramArrayOfInt1[(--i)] & 0xFFFFFFFF) + (paramArrayOfInt2[0] & 0xFFFFFFFF);
/* 1319 */       arrayOfInt2[i] = ((int)l);
/*      */     }
/*      */     else {
/* 1322 */       while (j > 0) {
/* 1323 */         l = (paramArrayOfInt1[(--i)] & 0xFFFFFFFF) + (paramArrayOfInt2[(--j)] & 0xFFFFFFFF) + (l >>> 32);
/*      */         
/* 1325 */         arrayOfInt2[i] = ((int)l);
/*      */       }
/*      */     }
/*      */     
/* 1329 */     int k = l >>> 32 != 0L ? 1 : 0;
/* 1330 */     while ((i > 0) && (k != 0)) {
/* 1331 */       k = (arrayOfInt2[(--i)] = paramArrayOfInt1[i] + 1) == 0 ? 1 : 0;
/*      */     }
/*      */     
/* 1334 */     while (i > 0) {
/* 1335 */       arrayOfInt2[(--i)] = paramArrayOfInt1[i];
/*      */     }
/*      */     
/* 1338 */     if (k != 0) {
/* 1339 */       int[] arrayOfInt3 = new int[arrayOfInt2.length + 1];
/* 1340 */       System.arraycopy(arrayOfInt2, 0, arrayOfInt3, 1, arrayOfInt2.length);
/* 1341 */       arrayOfInt3[0] = 1;
/* 1342 */       return arrayOfInt3;
/*      */     }
/* 1344 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */   private static int[] subtract(long paramLong, int[] paramArrayOfInt) {
/* 1348 */     int i = (int)(paramLong >>> 32);
/* 1349 */     if (i == 0) {
/* 1350 */       arrayOfInt = new int[1];
/* 1351 */       arrayOfInt[0] = ((int)(paramLong - (paramArrayOfInt[0] & 0xFFFFFFFF)));
/* 1352 */       return arrayOfInt;
/*      */     }
/* 1354 */     int[] arrayOfInt = new int[2];
/* 1355 */     if (paramArrayOfInt.length == 1) {
/* 1356 */       l = ((int)paramLong & 0xFFFFFFFF) - (paramArrayOfInt[0] & 0xFFFFFFFF);
/* 1357 */       arrayOfInt[1] = ((int)l);
/*      */       
/* 1359 */       int j = l >> 32 != 0L ? 1 : 0;
/* 1360 */       if (j != 0) {
/* 1361 */         arrayOfInt[0] = (i - 1);
/*      */       } else {
/* 1363 */         arrayOfInt[0] = i;
/*      */       }
/* 1365 */       return arrayOfInt;
/*      */     }
/* 1367 */     long l = ((int)paramLong & 0xFFFFFFFF) - (paramArrayOfInt[1] & 0xFFFFFFFF);
/* 1368 */     arrayOfInt[1] = ((int)l);
/* 1369 */     l = (i & 0xFFFFFFFF) - (paramArrayOfInt[0] & 0xFFFFFFFF) + (l >> 32);
/* 1370 */     arrayOfInt[0] = ((int)l);
/* 1371 */     return arrayOfInt;
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
/*      */   private static int[] subtract(int[] paramArrayOfInt, long paramLong)
/*      */   {
/* 1384 */     int i = (int)(paramLong >>> 32);
/* 1385 */     int j = paramArrayOfInt.length;
/* 1386 */     int[] arrayOfInt = new int[j];
/* 1387 */     long l = 0L;
/*      */     
/* 1389 */     if (i == 0) {
/* 1390 */       l = (paramArrayOfInt[(--j)] & 0xFFFFFFFF) - paramLong;
/* 1391 */       arrayOfInt[j] = ((int)l);
/*      */     } else {
/* 1393 */       l = (paramArrayOfInt[(--j)] & 0xFFFFFFFF) - (paramLong & 0xFFFFFFFF);
/* 1394 */       arrayOfInt[j] = ((int)l);
/* 1395 */       l = (paramArrayOfInt[(--j)] & 0xFFFFFFFF) - (i & 0xFFFFFFFF) + (l >> 32);
/* 1396 */       arrayOfInt[j] = ((int)l);
/*      */     }
/*      */     
/*      */ 
/* 1400 */     int k = l >> 32 != 0L ? 1 : 0;
/* 1401 */     while ((j > 0) && (k != 0)) {
/* 1402 */       k = (arrayOfInt[(--j)] = paramArrayOfInt[j] - 1) == -1 ? 1 : 0;
/*      */     }
/*      */     
/* 1405 */     while (j > 0) {
/* 1406 */       arrayOfInt[(--j)] = paramArrayOfInt[j];
/*      */     }
/* 1408 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger subtract(BigInteger paramBigInteger)
/*      */   {
/* 1418 */     if (paramBigInteger.signum == 0)
/* 1419 */       return this;
/* 1420 */     if (this.signum == 0)
/* 1421 */       return paramBigInteger.negate();
/* 1422 */     if (paramBigInteger.signum != this.signum) {
/* 1423 */       return new BigInteger(add(this.mag, paramBigInteger.mag), this.signum);
/*      */     }
/* 1425 */     int i = compareMagnitude(paramBigInteger);
/* 1426 */     if (i == 0) {
/* 1427 */       return ZERO;
/*      */     }
/* 1429 */     int[] arrayOfInt = i > 0 ? subtract(this.mag, paramBigInteger.mag) : subtract(paramBigInteger.mag, this.mag);
/* 1430 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1431 */     return new BigInteger(arrayOfInt, i == this.signum ? 1 : -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] subtract(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1441 */     int i = paramArrayOfInt1.length;
/* 1442 */     int[] arrayOfInt = new int[i];
/* 1443 */     int j = paramArrayOfInt2.length;
/* 1444 */     long l = 0L;
/*      */     
/*      */ 
/* 1447 */     while (j > 0) {
/* 1448 */       l = (paramArrayOfInt1[(--i)] & 0xFFFFFFFF) - (paramArrayOfInt2[(--j)] & 0xFFFFFFFF) + (l >> 32);
/*      */       
/*      */ 
/* 1451 */       arrayOfInt[i] = ((int)l);
/*      */     }
/*      */     
/*      */ 
/* 1455 */     int k = l >> 32 != 0L ? 1 : 0;
/* 1456 */     while ((i > 0) && (k != 0)) {
/* 1457 */       k = (arrayOfInt[(--i)] = paramArrayOfInt1[i] - 1) == -1 ? 1 : 0;
/*      */     }
/*      */     
/* 1460 */     while (i > 0) {
/* 1461 */       arrayOfInt[(--i)] = paramArrayOfInt1[i];
/*      */     }
/* 1463 */     return arrayOfInt;
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
/*      */   public BigInteger multiply(BigInteger paramBigInteger)
/*      */   {
/* 1476 */     if ((paramBigInteger.signum == 0) || (this.signum == 0)) {
/* 1477 */       return ZERO;
/*      */     }
/* 1479 */     int i = this.mag.length;
/*      */     
/* 1481 */     if ((paramBigInteger == this) && (i > 20)) {
/* 1482 */       return square();
/*      */     }
/*      */     
/* 1485 */     int j = paramBigInteger.mag.length;
/*      */     
/* 1487 */     if ((i < 80) || (j < 80)) {
/* 1488 */       int k = this.signum == paramBigInteger.signum ? 1 : -1;
/* 1489 */       if (paramBigInteger.mag.length == 1) {
/* 1490 */         return multiplyByInt(this.mag, paramBigInteger.mag[0], k);
/*      */       }
/* 1492 */       if (this.mag.length == 1) {
/* 1493 */         return multiplyByInt(paramBigInteger.mag, this.mag[0], k);
/*      */       }
/* 1495 */       int[] arrayOfInt = multiplyToLen(this.mag, i, paramBigInteger.mag, j, null);
/*      */       
/* 1497 */       arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1498 */       return new BigInteger(arrayOfInt, k);
/*      */     }
/* 1500 */     if ((i < 240) && (j < 240)) {
/* 1501 */       return multiplyKaratsuba(this, paramBigInteger);
/*      */     }
/* 1503 */     return multiplyToomCook3(this, paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */   private static BigInteger multiplyByInt(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 1509 */     if (Integer.bitCount(paramInt1) == 1) {
/* 1510 */       return new BigInteger(shiftLeft(paramArrayOfInt, Integer.numberOfTrailingZeros(paramInt1)), paramInt2);
/*      */     }
/* 1512 */     int i = paramArrayOfInt.length;
/* 1513 */     int[] arrayOfInt = new int[i + 1];
/* 1514 */     long l1 = 0L;
/* 1515 */     long l2 = paramInt1 & 0xFFFFFFFF;
/* 1516 */     int j = arrayOfInt.length - 1;
/* 1517 */     for (int k = i - 1; k >= 0; k--) {
/* 1518 */       long l3 = (paramArrayOfInt[k] & 0xFFFFFFFF) * l2 + l1;
/* 1519 */       arrayOfInt[(j--)] = ((int)l3);
/* 1520 */       l1 = l3 >>> 32;
/*      */     }
/* 1522 */     if (l1 == 0L) {
/* 1523 */       arrayOfInt = Arrays.copyOfRange(arrayOfInt, 1, arrayOfInt.length);
/*      */     } else {
/* 1525 */       arrayOfInt[j] = ((int)l1);
/*      */     }
/* 1527 */     return new BigInteger(arrayOfInt, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger multiply(long paramLong)
/*      */   {
/* 1535 */     if ((paramLong == 0L) || (this.signum == 0))
/* 1536 */       return ZERO;
/* 1537 */     if (paramLong == Long.MIN_VALUE)
/* 1538 */       return multiply(valueOf(paramLong));
/* 1539 */     int i = paramLong > 0L ? this.signum : -this.signum;
/* 1540 */     if (paramLong < 0L)
/* 1541 */       paramLong = -paramLong;
/* 1542 */     long l1 = paramLong >>> 32;
/* 1543 */     long l2 = paramLong & 0xFFFFFFFF;
/*      */     
/* 1545 */     int j = this.mag.length;
/* 1546 */     int[] arrayOfInt1 = this.mag;
/* 1547 */     int[] arrayOfInt2 = l1 == 0L ? new int[j + 1] : new int[j + 2];
/* 1548 */     long l3 = 0L;
/* 1549 */     int k = arrayOfInt2.length - 1;
/* 1550 */     long l4; for (int m = j - 1; m >= 0; m--) {
/* 1551 */       l4 = (arrayOfInt1[m] & 0xFFFFFFFF) * l2 + l3;
/* 1552 */       arrayOfInt2[(k--)] = ((int)l4);
/* 1553 */       l3 = l4 >>> 32;
/*      */     }
/* 1555 */     arrayOfInt2[k] = ((int)l3);
/* 1556 */     if (l1 != 0L) {
/* 1557 */       l3 = 0L;
/* 1558 */       k = arrayOfInt2.length - 2;
/* 1559 */       for (m = j - 1; m >= 0; m--) {
/* 1560 */         l4 = (arrayOfInt1[m] & 0xFFFFFFFF) * l1 + (arrayOfInt2[k] & 0xFFFFFFFF) + l3;
/*      */         
/* 1562 */         arrayOfInt2[(k--)] = ((int)l4);
/* 1563 */         l3 = l4 >>> 32;
/*      */       }
/* 1565 */       arrayOfInt2[0] = ((int)l3);
/*      */     }
/* 1567 */     if (l3 == 0L)
/* 1568 */       arrayOfInt2 = Arrays.copyOfRange(arrayOfInt2, 1, arrayOfInt2.length);
/* 1569 */     return new BigInteger(arrayOfInt2, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int[] multiplyToLen(int[] paramArrayOfInt1, int paramInt1, int[] paramArrayOfInt2, int paramInt2, int[] paramArrayOfInt3)
/*      */   {
/* 1577 */     int i = paramInt1 - 1;
/* 1578 */     int j = paramInt2 - 1;
/*      */     
/* 1580 */     if ((paramArrayOfInt3 == null) || (paramArrayOfInt3.length < paramInt1 + paramInt2)) {
/* 1581 */       paramArrayOfInt3 = new int[paramInt1 + paramInt2];
/*      */     }
/* 1583 */     long l1 = 0L;
/* 1584 */     int k = j; for (int m = j + 1 + i; k >= 0; m--) {
/* 1585 */       long l2 = (paramArrayOfInt2[k] & 0xFFFFFFFF) * (paramArrayOfInt1[i] & 0xFFFFFFFF) + l1;
/*      */       
/* 1587 */       paramArrayOfInt3[m] = ((int)l2);
/* 1588 */       l1 = l2 >>> 32;k--;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1590 */     paramArrayOfInt3[i] = ((int)l1);
/*      */     
/* 1592 */     for (k = i - 1; k >= 0; k--) {
/* 1593 */       l1 = 0L;
/* 1594 */       m = j; for (int n = j + 1 + k; m >= 0; n--) {
/* 1595 */         long l3 = (paramArrayOfInt2[m] & 0xFFFFFFFF) * (paramArrayOfInt1[k] & 0xFFFFFFFF) + (paramArrayOfInt3[n] & 0xFFFFFFFF) + l1;
/*      */         
/*      */ 
/* 1598 */         paramArrayOfInt3[n] = ((int)l3);
/* 1599 */         l1 = l3 >>> 32;m--;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1601 */       paramArrayOfInt3[k] = ((int)l1);
/*      */     }
/* 1603 */     return paramArrayOfInt3;
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
/*      */   private static BigInteger multiplyKaratsuba(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 1622 */     int i = paramBigInteger1.mag.length;
/* 1623 */     int j = paramBigInteger2.mag.length;
/*      */     
/*      */ 
/* 1626 */     int k = (Math.max(i, j) + 1) / 2;
/*      */     
/*      */ 
/*      */ 
/* 1630 */     BigInteger localBigInteger1 = paramBigInteger1.getLower(k);
/* 1631 */     BigInteger localBigInteger2 = paramBigInteger1.getUpper(k);
/* 1632 */     BigInteger localBigInteger3 = paramBigInteger2.getLower(k);
/* 1633 */     BigInteger localBigInteger4 = paramBigInteger2.getUpper(k);
/*      */     
/* 1635 */     BigInteger localBigInteger5 = localBigInteger2.multiply(localBigInteger4);
/* 1636 */     BigInteger localBigInteger6 = localBigInteger1.multiply(localBigInteger3);
/*      */     
/*      */ 
/* 1639 */     BigInteger localBigInteger7 = localBigInteger2.add(localBigInteger1).multiply(localBigInteger4.add(localBigInteger3));
/*      */     
/*      */ 
/* 1642 */     BigInteger localBigInteger8 = localBigInteger5.shiftLeft(32 * k).add(localBigInteger7.subtract(localBigInteger5).subtract(localBigInteger6)).shiftLeft(32 * k).add(localBigInteger6);
/*      */     
/* 1644 */     if (paramBigInteger1.signum != paramBigInteger2.signum) {
/* 1645 */       return localBigInteger8.negate();
/*      */     }
/* 1647 */     return localBigInteger8;
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
/*      */   private static BigInteger multiplyToomCook3(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 1680 */     int i = paramBigInteger1.mag.length;
/* 1681 */     int j = paramBigInteger2.mag.length;
/*      */     
/* 1683 */     int k = Math.max(i, j);
/*      */     
/*      */ 
/* 1686 */     int m = (k + 2) / 3;
/*      */     
/*      */ 
/* 1689 */     int n = k - 2 * m;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1694 */     BigInteger localBigInteger3 = paramBigInteger1.getToomSlice(m, n, 0, k);
/* 1695 */     BigInteger localBigInteger2 = paramBigInteger1.getToomSlice(m, n, 1, k);
/* 1696 */     BigInteger localBigInteger1 = paramBigInteger1.getToomSlice(m, n, 2, k);
/* 1697 */     BigInteger localBigInteger6 = paramBigInteger2.getToomSlice(m, n, 0, k);
/* 1698 */     BigInteger localBigInteger5 = paramBigInteger2.getToomSlice(m, n, 1, k);
/* 1699 */     BigInteger localBigInteger4 = paramBigInteger2.getToomSlice(m, n, 2, k);
/*      */     
/*      */ 
/*      */ 
/* 1703 */     BigInteger localBigInteger7 = localBigInteger1.multiply(localBigInteger4);
/* 1704 */     BigInteger localBigInteger15 = localBigInteger3.add(localBigInteger1);
/* 1705 */     BigInteger localBigInteger16 = localBigInteger6.add(localBigInteger4);
/* 1706 */     BigInteger localBigInteger10 = localBigInteger15.subtract(localBigInteger2).multiply(localBigInteger16.subtract(localBigInteger5));
/* 1707 */     localBigInteger15 = localBigInteger15.add(localBigInteger2);
/* 1708 */     localBigInteger16 = localBigInteger16.add(localBigInteger5);
/* 1709 */     BigInteger localBigInteger8 = localBigInteger15.multiply(localBigInteger16);
/* 1710 */     BigInteger localBigInteger9 = localBigInteger15.add(localBigInteger3).shiftLeft(1).subtract(localBigInteger1).multiply(localBigInteger16
/* 1711 */       .add(localBigInteger6).shiftLeft(1).subtract(localBigInteger4));
/* 1712 */     BigInteger localBigInteger11 = localBigInteger3.multiply(localBigInteger6);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1720 */     BigInteger localBigInteger13 = localBigInteger9.subtract(localBigInteger10).exactDivideBy3();
/* 1721 */     BigInteger localBigInteger14 = localBigInteger8.subtract(localBigInteger10).shiftRight(1);
/* 1722 */     BigInteger localBigInteger12 = localBigInteger8.subtract(localBigInteger7);
/* 1723 */     localBigInteger13 = localBigInteger13.subtract(localBigInteger12).shiftRight(1);
/* 1724 */     localBigInteger12 = localBigInteger12.subtract(localBigInteger14).subtract(localBigInteger11);
/* 1725 */     localBigInteger13 = localBigInteger13.subtract(localBigInteger11.shiftLeft(1));
/* 1726 */     localBigInteger14 = localBigInteger14.subtract(localBigInteger13);
/*      */     
/*      */ 
/* 1729 */     int i1 = m * 32;
/*      */     
/* 1731 */     BigInteger localBigInteger17 = localBigInteger11.shiftLeft(i1).add(localBigInteger13).shiftLeft(i1).add(localBigInteger12).shiftLeft(i1).add(localBigInteger14).shiftLeft(i1).add(localBigInteger7);
/*      */     
/* 1733 */     if (paramBigInteger1.signum != paramBigInteger2.signum) {
/* 1734 */       return localBigInteger17.negate();
/*      */     }
/* 1736 */     return localBigInteger17;
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
/*      */   private BigInteger getToomSlice(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1758 */     int m = this.mag.length;
/* 1759 */     int n = paramInt4 - m;
/*      */     int i;
/* 1761 */     int j; if (paramInt3 == 0) {
/* 1762 */       i = 0 - n;
/* 1763 */       j = paramInt2 - 1 - n;
/*      */     } else {
/* 1765 */       i = paramInt2 + (paramInt3 - 1) * paramInt1 - n;
/* 1766 */       j = i + paramInt1 - 1;
/*      */     }
/*      */     
/* 1769 */     if (i < 0) {
/* 1770 */       i = 0;
/*      */     }
/* 1772 */     if (j < 0) {
/* 1773 */       return ZERO;
/*      */     }
/*      */     
/* 1776 */     int k = j - i + 1;
/*      */     
/* 1778 */     if (k <= 0) {
/* 1779 */       return ZERO;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1784 */     if ((i == 0) && (k >= m)) {
/* 1785 */       return abs();
/*      */     }
/*      */     
/* 1788 */     int[] arrayOfInt = new int[k];
/* 1789 */     System.arraycopy(this.mag, i, arrayOfInt, 0, k);
/*      */     
/* 1791 */     return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger exactDivideBy3()
/*      */   {
/* 1803 */     int i = this.mag.length;
/* 1804 */     int[] arrayOfInt = new int[i];
/*      */     
/* 1806 */     long l4 = 0L;
/* 1807 */     for (int j = i - 1; j >= 0; j--) {
/* 1808 */       long l1 = this.mag[j] & 0xFFFFFFFF;
/* 1809 */       long l2 = l1 - l4;
/* 1810 */       if (l4 > l1) {
/* 1811 */         l4 = 1L;
/*      */       } else {
/* 1813 */         l4 = 0L;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1819 */       long l3 = l2 * 2863311531L & 0xFFFFFFFF;
/* 1820 */       arrayOfInt[j] = ((int)l3);
/*      */       
/*      */ 
/*      */ 
/* 1824 */       if (l3 >= 1431655766L) {
/* 1825 */         l4 += 1L;
/* 1826 */         if (l3 >= 2863311531L)
/* 1827 */           l4 += 1L;
/*      */       }
/*      */     }
/* 1830 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1831 */     return new BigInteger(arrayOfInt, this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getLower(int paramInt)
/*      */   {
/* 1839 */     int i = this.mag.length;
/*      */     
/* 1841 */     if (i <= paramInt) {
/* 1842 */       return abs();
/*      */     }
/*      */     
/* 1845 */     int[] arrayOfInt = new int[paramInt];
/* 1846 */     System.arraycopy(this.mag, i - paramInt, arrayOfInt, 0, paramInt);
/*      */     
/* 1848 */     return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getUpper(int paramInt)
/*      */   {
/* 1857 */     int i = this.mag.length;
/*      */     
/* 1859 */     if (i <= paramInt) {
/* 1860 */       return ZERO;
/*      */     }
/*      */     
/* 1863 */     int j = i - paramInt;
/* 1864 */     int[] arrayOfInt = new int[j];
/* 1865 */     System.arraycopy(this.mag, 0, arrayOfInt, 0, j);
/*      */     
/* 1867 */     return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger square()
/*      */   {
/* 1878 */     if (this.signum == 0) {
/* 1879 */       return ZERO;
/*      */     }
/* 1881 */     int i = this.mag.length;
/*      */     
/* 1883 */     if (i < 128) {
/* 1884 */       int[] arrayOfInt = squareToLen(this.mag, i, null);
/* 1885 */       return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */     }
/* 1887 */     if (i < 216) {
/* 1888 */       return squareKaratsuba();
/*      */     }
/* 1890 */     return squareToomCook3();
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
/*      */   private static final int[] squareToLen(int[] paramArrayOfInt1, int paramInt, int[] paramArrayOfInt2)
/*      */   {
/* 1934 */     int i = paramInt << 1;
/* 1935 */     if ((paramArrayOfInt2 == null) || (paramArrayOfInt2.length < i)) {
/* 1936 */       paramArrayOfInt2 = new int[i];
/*      */     }
/*      */     
/* 1939 */     int j = 0;
/* 1940 */     int k = 0; for (int m = 0; k < paramInt; k++) {
/* 1941 */       long l1 = paramArrayOfInt1[k] & 0xFFFFFFFF;
/* 1942 */       long l2 = l1 * l1;
/* 1943 */       paramArrayOfInt2[(m++)] = (j << 31 | (int)(l2 >>> 33));
/* 1944 */       paramArrayOfInt2[(m++)] = ((int)(l2 >>> 1));
/* 1945 */       j = (int)l2;
/*      */     }
/*      */     
/*      */ 
/* 1949 */     k = paramInt; for (m = 1; k > 0; m += 2) {
/* 1950 */       int n = paramArrayOfInt1[(k - 1)];
/* 1951 */       n = mulAdd(paramArrayOfInt2, paramArrayOfInt1, m, k - 1, n);
/* 1952 */       addOne(paramArrayOfInt2, m - 1, k, n);k--;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1956 */     primitiveLeftShift(paramArrayOfInt2, i, 1);
/* 1957 */     paramArrayOfInt2[(i - 1)] |= paramArrayOfInt1[(paramInt - 1)] & 0x1;
/*      */     
/* 1959 */     return paramArrayOfInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger squareKaratsuba()
/*      */   {
/* 1970 */     int i = (this.mag.length + 1) / 2;
/*      */     
/* 1972 */     BigInteger localBigInteger1 = getLower(i);
/* 1973 */     BigInteger localBigInteger2 = getUpper(i);
/*      */     
/* 1975 */     BigInteger localBigInteger3 = localBigInteger2.square();
/* 1976 */     BigInteger localBigInteger4 = localBigInteger1.square();
/*      */     
/*      */ 
/* 1979 */     return localBigInteger3.shiftLeft(i * 32).add(localBigInteger1.add(localBigInteger2).square().subtract(localBigInteger3.add(localBigInteger4))).shiftLeft(i * 32).add(localBigInteger4);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger squareToomCook3()
/*      */   {
/* 1990 */     int i = this.mag.length;
/*      */     
/*      */ 
/* 1993 */     int j = (i + 2) / 3;
/*      */     
/*      */ 
/* 1996 */     int k = i - 2 * j;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2001 */     BigInteger localBigInteger3 = getToomSlice(j, k, 0, i);
/* 2002 */     BigInteger localBigInteger2 = getToomSlice(j, k, 1, i);
/* 2003 */     BigInteger localBigInteger1 = getToomSlice(j, k, 2, i);
/*      */     
/*      */ 
/* 2006 */     BigInteger localBigInteger4 = localBigInteger1.square();
/* 2007 */     BigInteger localBigInteger12 = localBigInteger3.add(localBigInteger1);
/* 2008 */     BigInteger localBigInteger7 = localBigInteger12.subtract(localBigInteger2).square();
/* 2009 */     localBigInteger12 = localBigInteger12.add(localBigInteger2);
/* 2010 */     BigInteger localBigInteger5 = localBigInteger12.square();
/* 2011 */     BigInteger localBigInteger8 = localBigInteger3.square();
/* 2012 */     BigInteger localBigInteger6 = localBigInteger12.add(localBigInteger3).shiftLeft(1).subtract(localBigInteger1).square();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2020 */     BigInteger localBigInteger10 = localBigInteger6.subtract(localBigInteger7).exactDivideBy3();
/* 2021 */     BigInteger localBigInteger11 = localBigInteger5.subtract(localBigInteger7).shiftRight(1);
/* 2022 */     BigInteger localBigInteger9 = localBigInteger5.subtract(localBigInteger4);
/* 2023 */     localBigInteger10 = localBigInteger10.subtract(localBigInteger9).shiftRight(1);
/* 2024 */     localBigInteger9 = localBigInteger9.subtract(localBigInteger11).subtract(localBigInteger8);
/* 2025 */     localBigInteger10 = localBigInteger10.subtract(localBigInteger8.shiftLeft(1));
/* 2026 */     localBigInteger11 = localBigInteger11.subtract(localBigInteger10);
/*      */     
/*      */ 
/* 2029 */     int m = j * 32;
/*      */     
/* 2031 */     return localBigInteger8.shiftLeft(m).add(localBigInteger10).shiftLeft(m).add(localBigInteger9).shiftLeft(m).add(localBigInteger11).shiftLeft(m).add(localBigInteger4);
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
/*      */   public BigInteger divide(BigInteger paramBigInteger)
/*      */   {
/* 2044 */     if ((paramBigInteger.mag.length < 80) || (this.mag.length - paramBigInteger.mag.length < 40))
/*      */     {
/* 2046 */       return divideKnuth(paramBigInteger);
/*      */     }
/* 2048 */     return divideBurnikelZiegler(paramBigInteger);
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
/*      */   private BigInteger divideKnuth(BigInteger paramBigInteger)
/*      */   {
/* 2061 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2062 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this.mag);
/* 2063 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag);
/*      */     
/* 2065 */     localMutableBigInteger2.divideKnuth(localMutableBigInteger3, localMutableBigInteger1, false);
/* 2066 */     return localMutableBigInteger1.toBigInteger(this.signum * paramBigInteger.signum);
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
/*      */   public BigInteger[] divideAndRemainder(BigInteger paramBigInteger)
/*      */   {
/* 2081 */     if ((paramBigInteger.mag.length < 80) || (this.mag.length - paramBigInteger.mag.length < 40))
/*      */     {
/* 2083 */       return divideAndRemainderKnuth(paramBigInteger);
/*      */     }
/* 2085 */     return divideAndRemainderBurnikelZiegler(paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */   private BigInteger[] divideAndRemainderKnuth(BigInteger paramBigInteger)
/*      */   {
/* 2091 */     BigInteger[] arrayOfBigInteger = new BigInteger[2];
/* 2092 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2093 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this.mag);
/* 2094 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag);
/* 2095 */     MutableBigInteger localMutableBigInteger4 = localMutableBigInteger2.divideKnuth(localMutableBigInteger3, localMutableBigInteger1);
/* 2096 */     arrayOfBigInteger[0] = localMutableBigInteger1.toBigInteger(this.signum == paramBigInteger.signum ? 1 : -1);
/* 2097 */     arrayOfBigInteger[1] = localMutableBigInteger4.toBigInteger(this.signum);
/* 2098 */     return arrayOfBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger remainder(BigInteger paramBigInteger)
/*      */   {
/* 2110 */     if ((paramBigInteger.mag.length < 80) || (this.mag.length - paramBigInteger.mag.length < 40))
/*      */     {
/* 2112 */       return remainderKnuth(paramBigInteger);
/*      */     }
/* 2114 */     return remainderBurnikelZiegler(paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */   private BigInteger remainderKnuth(BigInteger paramBigInteger)
/*      */   {
/* 2120 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2121 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this.mag);
/* 2122 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag);
/*      */     
/* 2124 */     return localMutableBigInteger2.divideKnuth(localMutableBigInteger3, localMutableBigInteger1).toBigInteger(this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger divideBurnikelZiegler(BigInteger paramBigInteger)
/*      */   {
/* 2133 */     return divideAndRemainderBurnikelZiegler(paramBigInteger)[0];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger remainderBurnikelZiegler(BigInteger paramBigInteger)
/*      */   {
/* 2142 */     return divideAndRemainderBurnikelZiegler(paramBigInteger)[1];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger[] divideAndRemainderBurnikelZiegler(BigInteger paramBigInteger)
/*      */   {
/* 2152 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2153 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this).divideAndRemainderBurnikelZiegler(new MutableBigInteger(paramBigInteger), localMutableBigInteger1);
/* 2154 */     BigInteger localBigInteger1 = localMutableBigInteger1.isZero() ? ZERO : localMutableBigInteger1.toBigInteger(this.signum * paramBigInteger.signum);
/* 2155 */     BigInteger localBigInteger2 = localMutableBigInteger2.isZero() ? ZERO : localMutableBigInteger2.toBigInteger(this.signum);
/* 2156 */     return new BigInteger[] { localBigInteger1, localBigInteger2 };
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
/*      */   public BigInteger pow(int paramInt)
/*      */   {
/* 2169 */     if (paramInt < 0) {
/* 2170 */       throw new ArithmeticException("Negative exponent");
/*      */     }
/* 2172 */     if (this.signum == 0) {
/* 2173 */       return paramInt == 0 ? ONE : this;
/*      */     }
/*      */     
/* 2176 */     BigInteger localBigInteger1 = abs();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */     int i = localBigInteger1.getLowestSetBit();
/* 2183 */     long l1 = i * paramInt;
/* 2184 */     if (l1 > 2147483647L) {
/* 2185 */       reportOverflow();
/*      */     }
/*      */     
/*      */ 
/*      */     int j;
/*      */     
/* 2191 */     if (i > 0) {
/* 2192 */       localBigInteger1 = localBigInteger1.shiftRight(i);
/* 2193 */       j = localBigInteger1.bitLength();
/* 2194 */       if (j == 1) {
/* 2195 */         if ((this.signum < 0) && ((paramInt & 0x1) == 1)) {
/* 2196 */           return NEGATIVE_ONE.shiftLeft(i * paramInt);
/*      */         }
/* 2198 */         return ONE.shiftLeft(i * paramInt);
/*      */       }
/*      */     }
/*      */     else {
/* 2202 */       j = localBigInteger1.bitLength();
/* 2203 */       if (j == 1) {
/* 2204 */         if ((this.signum < 0) && ((paramInt & 0x1) == 1)) {
/* 2205 */           return NEGATIVE_ONE;
/*      */         }
/* 2207 */         return ONE;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2215 */     long l2 = j * paramInt;
/*      */     
/*      */ 
/*      */ 
/* 2219 */     if ((localBigInteger1.mag.length == 1) && (l2 <= 62L))
/*      */     {
/* 2221 */       int k = (this.signum < 0) && ((paramInt & 0x1) == 1) ? -1 : 1;
/* 2222 */       long l3 = 1L;
/* 2223 */       long l4 = localBigInteger1.mag[0] & 0xFFFFFFFF;
/*      */       
/* 2225 */       int n = paramInt;
/*      */       
/*      */ 
/* 2228 */       while (n != 0) {
/* 2229 */         if ((n & 0x1) == 1) {
/* 2230 */           l3 *= l4;
/*      */         }
/*      */         
/* 2233 */         if (n >>>= 1 != 0) {
/* 2234 */           l4 *= l4;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2239 */       if (i > 0) {
/* 2240 */         if (l1 + l2 <= 62L) {
/* 2241 */           return valueOf((l3 << (int)l1) * k);
/*      */         }
/* 2243 */         return valueOf(l3 * k).shiftLeft((int)l1);
/*      */       }
/*      */       
/*      */ 
/* 2247 */       return valueOf(l3 * k);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2253 */     BigInteger localBigInteger2 = ONE;
/*      */     
/* 2255 */     int m = paramInt;
/*      */     
/* 2257 */     while (m != 0) {
/* 2258 */       if ((m & 0x1) == 1) {
/* 2259 */         localBigInteger2 = localBigInteger2.multiply(localBigInteger1);
/*      */       }
/*      */       
/* 2262 */       if (m >>>= 1 != 0) {
/* 2263 */         localBigInteger1 = localBigInteger1.square();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2268 */     if (i > 0) {
/* 2269 */       localBigInteger2 = localBigInteger2.shiftLeft(i * paramInt);
/*      */     }
/*      */     
/* 2272 */     if ((this.signum < 0) && ((paramInt & 0x1) == 1)) {
/* 2273 */       return localBigInteger2.negate();
/*      */     }
/* 2275 */     return localBigInteger2;
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
/*      */   public BigInteger gcd(BigInteger paramBigInteger)
/*      */   {
/* 2289 */     if (paramBigInteger.signum == 0)
/* 2290 */       return abs();
/* 2291 */     if (this.signum == 0) {
/* 2292 */       return paramBigInteger.abs();
/*      */     }
/* 2294 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(this);
/* 2295 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramBigInteger);
/*      */     
/* 2297 */     MutableBigInteger localMutableBigInteger3 = localMutableBigInteger1.hybridGCD(localMutableBigInteger2);
/*      */     
/* 2299 */     return localMutableBigInteger3.toBigInteger(1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static int bitLengthForInt(int paramInt)
/*      */   {
/* 2306 */     return 32 - Integer.numberOfLeadingZeros(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] leftShift(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2314 */     int i = paramInt2 >>> 5;
/* 2315 */     int j = paramInt2 & 0x1F;
/* 2316 */     int k = bitLengthForInt(paramArrayOfInt[0]);
/*      */     
/*      */ 
/* 2319 */     if (paramInt2 <= 32 - k) {
/* 2320 */       primitiveLeftShift(paramArrayOfInt, paramInt1, j);
/* 2321 */       return paramArrayOfInt;
/*      */     }
/* 2323 */     if (j <= 32 - k) {
/* 2324 */       arrayOfInt = new int[i + paramInt1];
/* 2325 */       System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt1);
/* 2326 */       primitiveLeftShift(arrayOfInt, arrayOfInt.length, j);
/* 2327 */       return arrayOfInt;
/*      */     }
/* 2329 */     int[] arrayOfInt = new int[i + paramInt1 + 1];
/* 2330 */     System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt1);
/* 2331 */     primitiveRightShift(arrayOfInt, arrayOfInt.length, 32 - j);
/* 2332 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void primitiveRightShift(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2339 */     int i = 32 - paramInt2;
/* 2340 */     int j = paramInt1 - 1; for (int k = paramArrayOfInt[j]; j > 0; j--) {
/* 2341 */       int m = k;
/* 2342 */       k = paramArrayOfInt[(j - 1)];
/* 2343 */       paramArrayOfInt[j] = (k << i | m >>> paramInt2);
/*      */     }
/* 2345 */     paramArrayOfInt[0] >>>= paramInt2;
/*      */   }
/*      */   
/*      */   static void primitiveLeftShift(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2350 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/* 2351 */       return;
/*      */     }
/* 2353 */     int i = 32 - paramInt2;
/* 2354 */     int j = 0;int k = paramArrayOfInt[j]; for (int m = j + paramInt1 - 1; j < m; j++) {
/* 2355 */       int n = k;
/* 2356 */       k = paramArrayOfInt[(j + 1)];
/* 2357 */       paramArrayOfInt[j] = (n << paramInt2 | k >>> i);
/*      */     }
/* 2359 */     paramArrayOfInt[(paramInt1 - 1)] <<= paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int bitLength(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2367 */     if (paramInt == 0)
/* 2368 */       return 0;
/* 2369 */     return (paramInt - 1 << 5) + bitLengthForInt(paramArrayOfInt[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger abs()
/*      */   {
/* 2379 */     return this.signum >= 0 ? this : negate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger negate()
/*      */   {
/* 2388 */     return new BigInteger(this.mag, -this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int signum()
/*      */   {
/* 2398 */     return this.signum;
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
/*      */   public BigInteger mod(BigInteger paramBigInteger)
/*      */   {
/* 2414 */     if (paramBigInteger.signum <= 0) {
/* 2415 */       throw new ArithmeticException("BigInteger: modulus not positive");
/*      */     }
/* 2417 */     BigInteger localBigInteger = remainder(paramBigInteger);
/* 2418 */     return localBigInteger.signum >= 0 ? localBigInteger : localBigInteger.add(paramBigInteger);
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
/*      */   public BigInteger modPow(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 2435 */     if (paramBigInteger2.signum <= 0) {
/* 2436 */       throw new ArithmeticException("BigInteger: modulus not positive");
/*      */     }
/*      */     
/* 2439 */     if (paramBigInteger1.signum == 0) {
/* 2440 */       return paramBigInteger2.equals(ONE) ? ZERO : ONE;
/*      */     }
/* 2442 */     if (equals(ONE)) {
/* 2443 */       return paramBigInteger2.equals(ONE) ? ZERO : ONE;
/*      */     }
/* 2445 */     if ((equals(ZERO)) && (paramBigInteger1.signum >= 0)) {
/* 2446 */       return ZERO;
/*      */     }
/* 2448 */     if ((equals(negConst[1])) && (!paramBigInteger1.testBit(0))) {
/* 2449 */       return paramBigInteger2.equals(ONE) ? ZERO : ONE;
/*      */     }
/*      */     int i;
/* 2452 */     if ((i = paramBigInteger1.signum < 0 ? 1 : 0) != 0) {
/* 2453 */       paramBigInteger1 = paramBigInteger1.negate();
/*      */     }
/*      */     
/* 2456 */     BigInteger localBigInteger1 = (this.signum < 0) || (compareTo(paramBigInteger2) >= 0) ? mod(paramBigInteger2) : this;
/*      */     BigInteger localBigInteger2;
/* 2458 */     if (paramBigInteger2.testBit(0)) {
/* 2459 */       localBigInteger2 = localBigInteger1.oddModPow(paramBigInteger1, paramBigInteger2);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/* 2468 */       int j = paramBigInteger2.getLowestSetBit();
/*      */       
/* 2470 */       BigInteger localBigInteger3 = paramBigInteger2.shiftRight(j);
/* 2471 */       BigInteger localBigInteger4 = ONE.shiftLeft(j);
/*      */       
/*      */ 
/*      */ 
/* 2475 */       BigInteger localBigInteger5 = (this.signum < 0) || (compareTo(localBigInteger3) >= 0) ? mod(localBigInteger3) : this;
/*      */       
/*      */ 
/*      */ 
/* 2479 */       BigInteger localBigInteger6 = localBigInteger3.equals(ONE) ? ZERO : localBigInteger5.oddModPow(paramBigInteger1, localBigInteger3);
/*      */       
/*      */ 
/* 2482 */       BigInteger localBigInteger7 = localBigInteger1.modPow2(paramBigInteger1, j);
/*      */       
/*      */ 
/* 2485 */       BigInteger localBigInteger8 = localBigInteger4.modInverse(localBigInteger3);
/* 2486 */       BigInteger localBigInteger9 = localBigInteger3.modInverse(localBigInteger4);
/*      */       
/* 2488 */       if (paramBigInteger2.mag.length < 33554432) {
/* 2489 */         localBigInteger2 = localBigInteger6.multiply(localBigInteger4).multiply(localBigInteger8).add(localBigInteger7.multiply(localBigInteger3).multiply(localBigInteger9)).mod(paramBigInteger2);
/*      */       } else {
/* 2491 */         MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2492 */         new MutableBigInteger(localBigInteger6.multiply(localBigInteger4)).multiply(new MutableBigInteger(localBigInteger8), localMutableBigInteger1);
/* 2493 */         MutableBigInteger localMutableBigInteger2 = new MutableBigInteger();
/* 2494 */         new MutableBigInteger(localBigInteger7.multiply(localBigInteger3)).multiply(new MutableBigInteger(localBigInteger9), localMutableBigInteger2);
/* 2495 */         localMutableBigInteger1.add(localMutableBigInteger2);
/* 2496 */         MutableBigInteger localMutableBigInteger3 = new MutableBigInteger();
/* 2497 */         localBigInteger2 = localMutableBigInteger1.divide(new MutableBigInteger(paramBigInteger2), localMutableBigInteger3).toBigInteger();
/*      */       }
/*      */     }
/*      */     
/* 2501 */     return i != 0 ? localBigInteger2.modInverse(paramBigInteger2) : localBigInteger2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger oddModPow(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 2570 */     if (paramBigInteger1.equals(ONE)) {
/* 2571 */       return this;
/*      */     }
/*      */     
/* 2574 */     if (this.signum == 0) {
/* 2575 */       return ZERO;
/*      */     }
/* 2577 */     int[] arrayOfInt1 = (int[])this.mag.clone();
/* 2578 */     int[] arrayOfInt2 = paramBigInteger1.mag;
/* 2579 */     int[] arrayOfInt3 = paramBigInteger2.mag;
/* 2580 */     int i = arrayOfInt3.length;
/*      */     
/*      */ 
/* 2583 */     int j = 0;
/* 2584 */     int k = bitLength(arrayOfInt2, arrayOfInt2.length);
/*      */     
/* 2586 */     if ((k != 17) || (arrayOfInt2[0] != 65537)) {
/* 2587 */       while (k > bnExpModThreshTable[j]) {
/* 2588 */         j++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2593 */     int m = 1 << j;
/*      */     
/*      */ 
/* 2596 */     int[][] arrayOfInt = new int[m][];
/* 2597 */     for (int n = 0; n < m; n++) {
/* 2598 */       arrayOfInt[n] = new int[i];
/*      */     }
/*      */     
/* 2601 */     n = -MutableBigInteger.inverseMod32(arrayOfInt3[(i - 1)]);
/*      */     
/*      */ 
/* 2604 */     Object localObject1 = leftShift(arrayOfInt1, arrayOfInt1.length, i << 5);
/*      */     
/* 2606 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2607 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger((int[])localObject1);
/* 2608 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(arrayOfInt3);
/*      */     
/* 2610 */     MutableBigInteger localMutableBigInteger4 = localMutableBigInteger2.divide(localMutableBigInteger3, localMutableBigInteger1);
/* 2611 */     arrayOfInt[0] = localMutableBigInteger4.toIntArray();
/*      */     
/*      */ 
/* 2614 */     if (arrayOfInt[0].length < i) {
/* 2615 */       int i1 = i - arrayOfInt[0].length;
/* 2616 */       localObject3 = new int[i];
/* 2617 */       for (i2 = 0; i2 < arrayOfInt[0].length; i2++)
/* 2618 */         localObject3[(i2 + i1)] = arrayOfInt[0][i2];
/* 2619 */       arrayOfInt[0] = localObject3;
/*      */     }
/*      */     
/*      */ 
/* 2623 */     Object localObject2 = squareToLen(arrayOfInt[0], i, null);
/* 2624 */     localObject2 = montReduce((int[])localObject2, arrayOfInt3, i, n);
/*      */     
/*      */ 
/* 2627 */     Object localObject3 = Arrays.copyOf((int[])localObject2, i);
/*      */     
/*      */ 
/* 2630 */     for (int i2 = 1; i2 < m; i2++) {
/* 2631 */       int[] arrayOfInt4 = multiplyToLen((int[])localObject3, i, arrayOfInt[(i2 - 1)], i, null);
/* 2632 */       arrayOfInt[i2] = montReduce(arrayOfInt4, arrayOfInt3, i, n);
/*      */     }
/*      */     
/*      */ 
/* 2636 */     i2 = 1 << (k - 1 & 0x1F);
/*      */     
/* 2638 */     int i3 = 0;
/* 2639 */     int i4 = arrayOfInt2.length;
/* 2640 */     int i5 = 0;
/* 2641 */     for (int i6 = 0; i6 <= j; i6++) {
/* 2642 */       i3 = i3 << 1 | ((arrayOfInt2[i5] & i2) != 0 ? 1 : 0);
/* 2643 */       i2 >>>= 1;
/* 2644 */       if (i2 == 0) {
/* 2645 */         i5++;
/* 2646 */         i2 = Integer.MIN_VALUE;
/* 2647 */         i4--;
/*      */       }
/*      */     }
/*      */     
/* 2651 */     i6 = k;
/*      */     
/*      */ 
/* 2654 */     k--;
/* 2655 */     int i7 = 1;
/*      */     
/* 2657 */     i6 = k - j;
/* 2658 */     while ((i3 & 0x1) == 0) {
/* 2659 */       i3 >>>= 1;
/* 2660 */       i6++;
/*      */     }
/*      */     
/* 2663 */     int[] arrayOfInt5 = arrayOfInt[(i3 >>> 1)];
/*      */     
/* 2665 */     i3 = 0;
/* 2666 */     if (i6 == k) {
/* 2667 */       i7 = 0;
/*      */     }
/*      */     for (;;)
/*      */     {
/* 2671 */       k--;
/*      */       
/* 2673 */       i3 <<= 1;
/*      */       
/* 2675 */       if (i4 != 0) {
/* 2676 */         i3 |= ((arrayOfInt2[i5] & i2) != 0 ? 1 : 0);
/* 2677 */         i2 >>>= 1;
/* 2678 */         if (i2 == 0) {
/* 2679 */           i5++;
/* 2680 */           i2 = Integer.MIN_VALUE;
/* 2681 */           i4--;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2686 */       if ((i3 & m) != 0) {
/* 2687 */         i6 = k - j;
/* 2688 */         while ((i3 & 0x1) == 0) {
/* 2689 */           i3 >>>= 1;
/* 2690 */           i6++;
/*      */         }
/* 2692 */         arrayOfInt5 = arrayOfInt[(i3 >>> 1)];
/* 2693 */         i3 = 0;
/*      */       }
/*      */       
/*      */ 
/* 2697 */       if (k == i6) {
/* 2698 */         if (i7 != 0) {
/* 2699 */           localObject2 = (int[])arrayOfInt5.clone();
/* 2700 */           i7 = 0;
/*      */         } else {
/* 2702 */           localObject3 = localObject2;
/* 2703 */           localObject1 = multiplyToLen((int[])localObject3, i, arrayOfInt5, i, (int[])localObject1);
/* 2704 */           localObject1 = montReduce((int[])localObject1, arrayOfInt3, i, n);
/* 2705 */           localObject3 = localObject1;localObject1 = localObject2;localObject2 = localObject3;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2710 */       if (k == 0) {
/*      */         break;
/*      */       }
/*      */       
/* 2714 */       if (i7 == 0) {
/* 2715 */         localObject3 = localObject2;
/* 2716 */         localObject1 = squareToLen((int[])localObject3, i, (int[])localObject1);
/* 2717 */         localObject1 = montReduce((int[])localObject1, arrayOfInt3, i, n);
/* 2718 */         localObject3 = localObject1;localObject1 = localObject2;localObject2 = localObject3;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2723 */     int[] arrayOfInt6 = new int[2 * i];
/* 2724 */     System.arraycopy(localObject2, 0, arrayOfInt6, i, i);
/*      */     
/* 2726 */     localObject2 = montReduce(arrayOfInt6, arrayOfInt3, i, n);
/*      */     
/* 2728 */     arrayOfInt6 = Arrays.copyOf((int[])localObject2, i);
/*      */     
/* 2730 */     return new BigInteger(1, arrayOfInt6);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] montReduce(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
/*      */   {
/* 2738 */     int i = 0;
/* 2739 */     int j = paramInt1;
/* 2740 */     int k = 0;
/*      */     do
/*      */     {
/* 2743 */       int m = paramArrayOfInt1[(paramArrayOfInt1.length - 1 - k)];
/* 2744 */       int n = mulAdd(paramArrayOfInt1, paramArrayOfInt2, k, paramInt1, paramInt2 * m);
/* 2745 */       i += addOne(paramArrayOfInt1, k, paramInt1, n);
/* 2746 */       k++;
/* 2747 */       j--; } while (j > 0);
/*      */     
/* 2749 */     while (i > 0) {
/* 2750 */       i += subN(paramArrayOfInt1, paramArrayOfInt2, paramInt1);
/*      */     }
/* 2752 */     while (intArrayCmpToLen(paramArrayOfInt1, paramArrayOfInt2, paramInt1) >= 0) {
/* 2753 */       subN(paramArrayOfInt1, paramArrayOfInt2, paramInt1);
/*      */     }
/* 2755 */     return paramArrayOfInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int intArrayCmpToLen(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*      */   {
/* 2764 */     for (int i = 0; i < paramInt; i++) {
/* 2765 */       long l1 = paramArrayOfInt1[i] & 0xFFFFFFFF;
/* 2766 */       long l2 = paramArrayOfInt2[i] & 0xFFFFFFFF;
/* 2767 */       if (l1 < l2)
/* 2768 */         return -1;
/* 2769 */       if (l1 > l2)
/* 2770 */         return 1;
/*      */     }
/* 2772 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int subN(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*      */   {
/* 2779 */     long l = 0L;
/*      */     for (;;) {
/* 2781 */       paramInt--; if (paramInt < 0) break;
/* 2782 */       l = (paramArrayOfInt1[paramInt] & 0xFFFFFFFF) - (paramArrayOfInt2[paramInt] & 0xFFFFFFFF) + (l >> 32);
/*      */       
/* 2784 */       paramArrayOfInt1[paramInt] = ((int)l);
/*      */     }
/*      */     
/* 2787 */     return (int)(l >> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static int mulAdd(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2794 */     long l1 = paramInt3 & 0xFFFFFFFF;
/* 2795 */     long l2 = 0L;
/*      */     
/* 2797 */     paramInt1 = paramArrayOfInt1.length - paramInt1 - 1;
/* 2798 */     for (int i = paramInt2 - 1; i >= 0; i--) {
/* 2799 */       long l3 = (paramArrayOfInt2[i] & 0xFFFFFFFF) * l1 + (paramArrayOfInt1[paramInt1] & 0xFFFFFFFF) + l2;
/*      */       
/* 2801 */       paramArrayOfInt1[(paramInt1--)] = ((int)l3);
/* 2802 */       l2 = l3 >>> 32;
/*      */     }
/* 2804 */     return (int)l2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static int addOne(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2812 */     paramInt1 = paramArrayOfInt.length - 1 - paramInt2 - paramInt1;
/* 2813 */     long l = (paramArrayOfInt[paramInt1] & 0xFFFFFFFF) + (paramInt3 & 0xFFFFFFFF);
/*      */     
/* 2815 */     paramArrayOfInt[paramInt1] = ((int)l);
/* 2816 */     if (l >>> 32 == 0L)
/* 2817 */       return 0;
/* 2818 */     do { paramInt2--; if (paramInt2 < 0) break;
/* 2819 */       paramInt1--; if (paramInt1 < 0) {
/* 2820 */         return 1;
/*      */       }
/* 2822 */       paramArrayOfInt[paramInt1] += 1;
/* 2823 */     } while (paramArrayOfInt[paramInt1] == 0);
/* 2824 */     return 0;
/*      */     
/*      */ 
/* 2827 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger modPow2(BigInteger paramBigInteger, int paramInt)
/*      */   {
/* 2838 */     BigInteger localBigInteger1 = ONE;
/* 2839 */     BigInteger localBigInteger2 = mod2(paramInt);
/* 2840 */     int i = 0;
/*      */     
/* 2842 */     int j = paramBigInteger.bitLength();
/*      */     
/* 2844 */     if (testBit(0)) {
/* 2845 */       j = paramInt - 1 < j ? paramInt - 1 : j;
/*      */     }
/* 2847 */     while (i < j) {
/* 2848 */       if (paramBigInteger.testBit(i))
/* 2849 */         localBigInteger1 = localBigInteger1.multiply(localBigInteger2).mod2(paramInt);
/* 2850 */       i++;
/* 2851 */       if (i < j) {
/* 2852 */         localBigInteger2 = localBigInteger2.square().mod2(paramInt);
/*      */       }
/*      */     }
/* 2855 */     return localBigInteger1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger mod2(int paramInt)
/*      */   {
/* 2863 */     if (bitLength() <= paramInt) {
/* 2864 */       return this;
/*      */     }
/*      */     
/* 2867 */     int i = paramInt + 31 >>> 5;
/* 2868 */     int[] arrayOfInt = new int[i];
/* 2869 */     System.arraycopy(this.mag, this.mag.length - i, arrayOfInt, 0, i);
/*      */     
/*      */ 
/* 2872 */     int j = (i << 5) - paramInt; int 
/* 2873 */       tmp47_46 = 0; int[] tmp47_45 = arrayOfInt;tmp47_45[tmp47_46] = ((int)(tmp47_45[tmp47_46] & (1L << 32 - j) - 1L));
/*      */     
/* 2875 */     return arrayOfInt[0] == 0 ? new BigInteger(1, arrayOfInt) : new BigInteger(arrayOfInt, 1);
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
/*      */   public BigInteger modInverse(BigInteger paramBigInteger)
/*      */   {
/* 2888 */     if (paramBigInteger.signum != 1) {
/* 2889 */       throw new ArithmeticException("BigInteger: modulus not positive");
/*      */     }
/* 2891 */     if (paramBigInteger.equals(ONE)) {
/* 2892 */       return ZERO;
/*      */     }
/*      */     
/* 2895 */     BigInteger localBigInteger = this;
/* 2896 */     if ((this.signum < 0) || (compareMagnitude(paramBigInteger) >= 0)) {
/* 2897 */       localBigInteger = mod(paramBigInteger);
/*      */     }
/* 2899 */     if (localBigInteger.equals(ONE)) {
/* 2900 */       return ONE;
/*      */     }
/* 2902 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(localBigInteger);
/* 2903 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramBigInteger);
/*      */     
/* 2905 */     MutableBigInteger localMutableBigInteger3 = localMutableBigInteger1.mutableModInverse(localMutableBigInteger2);
/* 2906 */     return localMutableBigInteger3.toBigInteger(1);
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
/*      */   public BigInteger shiftLeft(int paramInt)
/*      */   {
/* 2922 */     if (this.signum == 0)
/* 2923 */       return ZERO;
/* 2924 */     if (paramInt > 0)
/* 2925 */       return new BigInteger(shiftLeft(this.mag, paramInt), this.signum);
/* 2926 */     if (paramInt == 0) {
/* 2927 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 2931 */     return shiftRightImpl(-paramInt);
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
/*      */   private static int[] shiftLeft(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2945 */     int i = paramInt >>> 5;
/* 2946 */     int j = paramInt & 0x1F;
/* 2947 */     int k = paramArrayOfInt.length;
/* 2948 */     int[] arrayOfInt = null;
/*      */     
/* 2950 */     if (j == 0) {
/* 2951 */       arrayOfInt = new int[k + i];
/* 2952 */       System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, k);
/*      */     } else {
/* 2954 */       int m = 0;
/* 2955 */       int n = 32 - j;
/* 2956 */       int i1 = paramArrayOfInt[0] >>> n;
/* 2957 */       if (i1 != 0) {
/* 2958 */         arrayOfInt = new int[k + i + 1];
/* 2959 */         arrayOfInt[(m++)] = i1;
/*      */       } else {
/* 2961 */         arrayOfInt = new int[k + i];
/*      */       }
/* 2963 */       int i2 = 0;
/* 2964 */       while (i2 < k - 1)
/* 2965 */         arrayOfInt[(m++)] = (paramArrayOfInt[(i2++)] << j | paramArrayOfInt[i2] >>> n);
/* 2966 */       paramArrayOfInt[i2] <<= j;
/*      */     }
/* 2968 */     return arrayOfInt;
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
/*      */   public BigInteger shiftRight(int paramInt)
/*      */   {
/* 2982 */     if (this.signum == 0)
/* 2983 */       return ZERO;
/* 2984 */     if (paramInt > 0)
/* 2985 */       return shiftRightImpl(paramInt);
/* 2986 */     if (paramInt == 0) {
/* 2987 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 2991 */     return new BigInteger(shiftLeft(this.mag, -paramInt), this.signum);
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
/*      */   private BigInteger shiftRightImpl(int paramInt)
/*      */   {
/* 3004 */     int i = paramInt >>> 5;
/* 3005 */     int j = paramInt & 0x1F;
/* 3006 */     int k = this.mag.length;
/* 3007 */     int[] arrayOfInt = null;
/*      */     
/*      */ 
/* 3010 */     if (i >= k)
/* 3011 */       return this.signum >= 0 ? ZERO : negConst[1];
/*      */     int m;
/* 3013 */     int n; int i1; if (j == 0) {
/* 3014 */       m = k - i;
/* 3015 */       arrayOfInt = Arrays.copyOf(this.mag, m);
/*      */     } else {
/* 3017 */       m = 0;
/* 3018 */       n = this.mag[0] >>> j;
/* 3019 */       if (n != 0) {
/* 3020 */         arrayOfInt = new int[k - i];
/* 3021 */         arrayOfInt[(m++)] = n;
/*      */       } else {
/* 3023 */         arrayOfInt = new int[k - i - 1];
/*      */       }
/*      */       
/* 3026 */       i1 = 32 - j;
/* 3027 */       int i2 = 0;
/* 3028 */       while (i2 < k - i - 1) {
/* 3029 */         arrayOfInt[(m++)] = (this.mag[(i2++)] << i1 | this.mag[i2] >>> j);
/*      */       }
/*      */     }
/* 3032 */     if (this.signum < 0)
/*      */     {
/* 3034 */       m = 0;
/* 3035 */       n = k - 1; for (i1 = k - i; (n >= i1) && (m == 0); n--)
/* 3036 */         m = this.mag[n] != 0 ? 1 : 0;
/* 3037 */       if ((m == 0) && (j != 0)) {
/* 3038 */         m = this.mag[(k - i - 1)] << 32 - j != 0 ? 1 : 0;
/*      */       }
/* 3040 */       if (m != 0) {
/* 3041 */         arrayOfInt = javaIncrement(arrayOfInt);
/*      */       }
/*      */     }
/* 3044 */     return new BigInteger(arrayOfInt, this.signum);
/*      */   }
/*      */   
/*      */   int[] javaIncrement(int[] paramArrayOfInt) {
/* 3048 */     int i = 0;
/* 3049 */     for (int j = paramArrayOfInt.length - 1; (j >= 0) && (i == 0); j--)
/* 3050 */       i = paramArrayOfInt[j] += 1;
/* 3051 */     if (i == 0) {
/* 3052 */       paramArrayOfInt = new int[paramArrayOfInt.length + 1];
/* 3053 */       paramArrayOfInt[0] = 1;
/*      */     }
/* 3055 */     return paramArrayOfInt;
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
/*      */   public BigInteger and(BigInteger paramBigInteger)
/*      */   {
/* 3069 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3070 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3072 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) & paramBigInteger.getInt(arrayOfInt.length - i - 1));
/*      */     }
/* 3074 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger or(BigInteger paramBigInteger)
/*      */   {
/* 3086 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3087 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3089 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) | paramBigInteger.getInt(arrayOfInt.length - i - 1));
/*      */     }
/* 3091 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger xor(BigInteger paramBigInteger)
/*      */   {
/* 3103 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3104 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3106 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) ^ paramBigInteger.getInt(arrayOfInt.length - i - 1));
/*      */     }
/* 3108 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger not()
/*      */   {
/* 3119 */     int[] arrayOfInt = new int[intLength()];
/* 3120 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 3121 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) ^ 0xFFFFFFFF);
/*      */     }
/* 3123 */     return valueOf(arrayOfInt);
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
/*      */   public BigInteger andNot(BigInteger paramBigInteger)
/*      */   {
/* 3137 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3138 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3140 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) & (paramBigInteger.getInt(arrayOfInt.length - i - 1) ^ 0xFFFFFFFF));
/*      */     }
/* 3142 */     return valueOf(arrayOfInt);
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
/*      */   public boolean testBit(int paramInt)
/*      */   {
/* 3157 */     if (paramInt < 0) {
/* 3158 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3160 */     return (getInt(paramInt >>> 5) & 1 << (paramInt & 0x1F)) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger setBit(int paramInt)
/*      */   {
/* 3172 */     if (paramInt < 0) {
/* 3173 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3175 */     int i = paramInt >>> 5;
/* 3176 */     int[] arrayOfInt = new int[Math.max(intLength(), i + 2)];
/*      */     
/* 3178 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 3179 */       arrayOfInt[(arrayOfInt.length - j - 1)] = getInt(j);
/*      */     }
/* 3181 */     arrayOfInt[(arrayOfInt.length - i - 1)] |= 1 << (paramInt & 0x1F);
/*      */     
/* 3183 */     return valueOf(arrayOfInt);
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
/*      */   public BigInteger clearBit(int paramInt)
/*      */   {
/* 3196 */     if (paramInt < 0) {
/* 3197 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3199 */     int i = paramInt >>> 5;
/* 3200 */     int[] arrayOfInt = new int[Math.max(intLength(), (paramInt + 1 >>> 5) + 1)];
/*      */     
/* 3202 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 3203 */       arrayOfInt[(arrayOfInt.length - j - 1)] = getInt(j);
/*      */     }
/* 3205 */     arrayOfInt[(arrayOfInt.length - i - 1)] &= (1 << (paramInt & 0x1F) ^ 0xFFFFFFFF);
/*      */     
/* 3207 */     return valueOf(arrayOfInt);
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
/*      */   public BigInteger flipBit(int paramInt)
/*      */   {
/* 3220 */     if (paramInt < 0) {
/* 3221 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3223 */     int i = paramInt >>> 5;
/* 3224 */     int[] arrayOfInt = new int[Math.max(intLength(), i + 2)];
/*      */     
/* 3226 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 3227 */       arrayOfInt[(arrayOfInt.length - j - 1)] = getInt(j);
/*      */     }
/* 3229 */     arrayOfInt[(arrayOfInt.length - i - 1)] ^= 1 << (paramInt & 0x1F);
/*      */     
/* 3231 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLowestSetBit()
/*      */   {
/* 3243 */     int i = this.lowestSetBit - 2;
/* 3244 */     if (i == -2) {
/* 3245 */       i = 0;
/* 3246 */       if (this.signum == 0) {
/* 3247 */         i--;
/*      */       }
/*      */       else {
/*      */         int k;
/* 3251 */         for (int j = 0; (k = getInt(j)) == 0; j++) {}
/*      */         
/* 3253 */         i += (j << 5) + Integer.numberOfTrailingZeros(k);
/*      */       }
/* 3255 */       this.lowestSetBit = (i + 2);
/*      */     }
/* 3257 */     return i;
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
/*      */   public int bitLength()
/*      */   {
/* 3274 */     int i = this.bitLength - 1;
/* 3275 */     if (i == -1) {
/* 3276 */       int[] arrayOfInt = this.mag;
/* 3277 */       int j = arrayOfInt.length;
/* 3278 */       if (j == 0) {
/* 3279 */         i = 0;
/*      */       }
/*      */       else {
/* 3282 */         int k = (j - 1 << 5) + bitLengthForInt(this.mag[0]);
/* 3283 */         if (this.signum < 0)
/*      */         {
/* 3285 */           int m = Integer.bitCount(this.mag[0]) == 1 ? 1 : 0;
/* 3286 */           for (int n = 1; (n < j) && (m != 0); n++) {
/* 3287 */             m = this.mag[n] == 0 ? 1 : 0;
/*      */           }
/* 3289 */           i = m != 0 ? k - 1 : k;
/*      */         } else {
/* 3291 */           i = k;
/*      */         }
/*      */       }
/* 3294 */       this.bitLength = (i + 1);
/*      */     }
/* 3296 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int bitCount()
/*      */   {
/* 3308 */     int i = this.bitCount - 1;
/* 3309 */     if (i == -1) {
/* 3310 */       i = 0;
/*      */       
/* 3312 */       for (int j = 0; j < this.mag.length; j++)
/* 3313 */         i += Integer.bitCount(this.mag[j]);
/* 3314 */       if (this.signum < 0)
/*      */       {
/* 3316 */         j = 0;
/* 3317 */         for (int k = this.mag.length - 1; this.mag[k] == 0; k--)
/* 3318 */           j += 32;
/* 3319 */         j += Integer.numberOfTrailingZeros(this.mag[k]);
/* 3320 */         i += j - 1;
/*      */       }
/* 3322 */       this.bitCount = (i + 1);
/*      */     }
/* 3324 */     return i;
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
/*      */   public boolean isProbablePrime(int paramInt)
/*      */   {
/* 3344 */     if (paramInt <= 0)
/* 3345 */       return true;
/* 3346 */     BigInteger localBigInteger = abs();
/* 3347 */     if (localBigInteger.equals(TWO))
/* 3348 */       return true;
/* 3349 */     if ((!localBigInteger.testBit(0)) || (localBigInteger.equals(ONE))) {
/* 3350 */       return false;
/*      */     }
/* 3352 */     return localBigInteger.primeToCertainty(paramInt, null);
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
/*      */   public int compareTo(BigInteger paramBigInteger)
/*      */   {
/* 3371 */     if (this.signum == paramBigInteger.signum) {
/* 3372 */       switch (this.signum) {
/*      */       case 1: 
/* 3374 */         return compareMagnitude(paramBigInteger);
/*      */       case -1: 
/* 3376 */         return paramBigInteger.compareMagnitude(this);
/*      */       }
/* 3378 */       return 0;
/*      */     }
/*      */     
/* 3381 */     return this.signum > paramBigInteger.signum ? 1 : -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int compareMagnitude(BigInteger paramBigInteger)
/*      */   {
/* 3393 */     int[] arrayOfInt1 = this.mag;
/* 3394 */     int i = arrayOfInt1.length;
/* 3395 */     int[] arrayOfInt2 = paramBigInteger.mag;
/* 3396 */     int j = arrayOfInt2.length;
/* 3397 */     if (i < j)
/* 3398 */       return -1;
/* 3399 */     if (i > j)
/* 3400 */       return 1;
/* 3401 */     for (int k = 0; k < i; k++) {
/* 3402 */       int m = arrayOfInt1[k];
/* 3403 */       int n = arrayOfInt2[k];
/* 3404 */       if (m != n)
/* 3405 */         return (m & 0xFFFFFFFF) < (n & 0xFFFFFFFF) ? -1 : 1;
/*      */     }
/* 3407 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final int compareMagnitude(long paramLong)
/*      */   {
/* 3415 */     assert (paramLong != Long.MIN_VALUE);
/* 3416 */     int[] arrayOfInt = this.mag;
/* 3417 */     int i = arrayOfInt.length;
/* 3418 */     if (i > 2) {
/* 3419 */       return 1;
/*      */     }
/* 3421 */     if (paramLong < 0L) {
/* 3422 */       paramLong = -paramLong;
/*      */     }
/* 3424 */     int j = (int)(paramLong >>> 32);
/* 3425 */     if (j == 0) {
/* 3426 */       if (i < 1)
/* 3427 */         return -1;
/* 3428 */       if (i > 1)
/* 3429 */         return 1;
/* 3430 */       k = arrayOfInt[0];
/* 3431 */       m = (int)paramLong;
/* 3432 */       if (k != m) {
/* 3433 */         return (k & 0xFFFFFFFF) < (m & 0xFFFFFFFF) ? -1 : 1;
/*      */       }
/* 3435 */       return 0;
/*      */     }
/* 3437 */     if (i < 2)
/* 3438 */       return -1;
/* 3439 */     int k = arrayOfInt[0];
/* 3440 */     int m = j;
/* 3441 */     if (k != m) {
/* 3442 */       return (k & 0xFFFFFFFF) < (m & 0xFFFFFFFF) ? -1 : 1;
/*      */     }
/* 3444 */     k = arrayOfInt[1];
/* 3445 */     m = (int)paramLong;
/* 3446 */     if (k != m) {
/* 3447 */       return (k & 0xFFFFFFFF) < (m & 0xFFFFFFFF) ? -1 : 1;
/*      */     }
/* 3449 */     return 0;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 3462 */     if (paramObject == this) {
/* 3463 */       return true;
/*      */     }
/* 3465 */     if (!(paramObject instanceof BigInteger)) {
/* 3466 */       return false;
/*      */     }
/* 3468 */     BigInteger localBigInteger = (BigInteger)paramObject;
/* 3469 */     if (localBigInteger.signum != this.signum) {
/* 3470 */       return false;
/*      */     }
/* 3472 */     int[] arrayOfInt1 = this.mag;
/* 3473 */     int i = arrayOfInt1.length;
/* 3474 */     int[] arrayOfInt2 = localBigInteger.mag;
/* 3475 */     if (i != arrayOfInt2.length) {
/* 3476 */       return false;
/*      */     }
/* 3478 */     for (int j = 0; j < i; j++) {
/* 3479 */       if (arrayOfInt2[j] != arrayOfInt1[j])
/* 3480 */         return false;
/*      */     }
/* 3482 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger min(BigInteger paramBigInteger)
/*      */   {
/* 3493 */     return compareTo(paramBigInteger) < 0 ? this : paramBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger max(BigInteger paramBigInteger)
/*      */   {
/* 3504 */     return compareTo(paramBigInteger) > 0 ? this : paramBigInteger;
/*      */   }
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
/* 3516 */     int i = 0;
/*      */     
/* 3518 */     for (int j = 0; j < this.mag.length; j++) {
/* 3519 */       i = (int)(31 * i + (this.mag[j] & 0xFFFFFFFF));
/*      */     }
/* 3521 */     return i * this.signum;
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
/*      */   public String toString(int paramInt)
/*      */   {
/* 3542 */     if (this.signum == 0)
/* 3543 */       return "0";
/* 3544 */     if ((paramInt < 2) || (paramInt > 36)) {
/* 3545 */       paramInt = 10;
/*      */     }
/*      */     
/* 3548 */     if (this.mag.length <= 20) {
/* 3549 */       return smallToString(paramInt);
/*      */     }
/*      */     
/*      */ 
/* 3553 */     StringBuilder localStringBuilder = new StringBuilder();
/* 3554 */     if (this.signum < 0) {
/* 3555 */       toString(negate(), localStringBuilder, paramInt, 0);
/* 3556 */       localStringBuilder.insert(0, '-');
/*      */     }
/*      */     else {
/* 3559 */       toString(this, localStringBuilder, paramInt, 0);
/*      */     }
/* 3561 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */   private String smallToString(int paramInt)
/*      */   {
/* 3566 */     if (this.signum == 0) {
/* 3567 */       return "0";
/*      */     }
/*      */     
/*      */ 
/* 3571 */     int i = (4 * this.mag.length + 6) / 7;
/* 3572 */     String[] arrayOfString = new String[i];
/*      */     
/*      */ 
/* 3575 */     Object localObject1 = abs();
/* 3576 */     int j = 0;
/* 3577 */     while (((BigInteger)localObject1).signum != 0) {
/* 3578 */       localObject2 = longRadix[paramInt];
/*      */       
/* 3580 */       MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 3581 */       MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(((BigInteger)localObject1).mag);
/* 3582 */       MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(((BigInteger)localObject2).mag);
/* 3583 */       MutableBigInteger localMutableBigInteger4 = localMutableBigInteger2.divide(localMutableBigInteger3, localMutableBigInteger1);
/* 3584 */       BigInteger localBigInteger1 = localMutableBigInteger1.toBigInteger(((BigInteger)localObject1).signum * ((BigInteger)localObject2).signum);
/* 3585 */       BigInteger localBigInteger2 = localMutableBigInteger4.toBigInteger(((BigInteger)localObject1).signum * ((BigInteger)localObject2).signum);
/*      */       
/* 3587 */       arrayOfString[(j++)] = Long.toString(localBigInteger2.longValue(), paramInt);
/* 3588 */       localObject1 = localBigInteger1;
/*      */     }
/*      */     
/*      */ 
/* 3592 */     Object localObject2 = new StringBuilder(j * digitsPerLong[paramInt] + 1);
/* 3593 */     if (this.signum < 0) {
/* 3594 */       ((StringBuilder)localObject2).append('-');
/*      */     }
/* 3596 */     ((StringBuilder)localObject2).append(arrayOfString[(j - 1)]);
/*      */     
/*      */ 
/* 3599 */     for (int k = j - 2; k >= 0; k--)
/*      */     {
/* 3601 */       int m = digitsPerLong[paramInt] - arrayOfString[k].length();
/* 3602 */       if (m != 0) {
/* 3603 */         ((StringBuilder)localObject2).append(zeros[m]);
/*      */       }
/* 3605 */       ((StringBuilder)localObject2).append(arrayOfString[k]);
/*      */     }
/* 3607 */     return ((StringBuilder)localObject2).toString();
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
/*      */   private static void toString(BigInteger paramBigInteger, StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/* 3627 */     if (paramBigInteger.mag.length <= 20) {
/* 3628 */       String str = paramBigInteger.smallToString(paramInt1);
/*      */       
/*      */ 
/*      */ 
/* 3632 */       if ((str.length() < paramInt2) && (paramStringBuilder.length() > 0)) {
/* 3633 */         for (j = str.length(); j < paramInt2; j++) {
/* 3634 */           paramStringBuilder.append('0');
/*      */         }
/*      */       }
/*      */       
/* 3638 */       paramStringBuilder.append(str);
/* 3639 */       return;
/*      */     }
/*      */     
/*      */ 
/* 3643 */     int i = paramBigInteger.bitLength();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3648 */     int j = (int)Math.round(Math.log(i * LOG_TWO / logCache[paramInt1]) / LOG_TWO - 1.0D);
/* 3649 */     BigInteger localBigInteger = getRadixConversionCache(paramInt1, j);
/*      */     
/* 3651 */     BigInteger[] arrayOfBigInteger = paramBigInteger.divideAndRemainder(localBigInteger);
/*      */     
/* 3653 */     int k = 1 << j;
/*      */     
/*      */ 
/* 3656 */     toString(arrayOfBigInteger[0], paramStringBuilder, paramInt1, paramInt2 - k);
/* 3657 */     toString(arrayOfBigInteger[1], paramStringBuilder, paramInt1, k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger getRadixConversionCache(int paramInt1, int paramInt2)
/*      */   {
/* 3668 */     BigInteger[] arrayOfBigInteger = powerCache[paramInt1];
/* 3669 */     if (paramInt2 < arrayOfBigInteger.length) {
/* 3670 */       return arrayOfBigInteger[paramInt2];
/*      */     }
/*      */     
/* 3673 */     int i = arrayOfBigInteger.length;
/* 3674 */     arrayOfBigInteger = (BigInteger[])Arrays.copyOf(arrayOfBigInteger, paramInt2 + 1);
/* 3675 */     for (int j = i; j <= paramInt2; j++) {
/* 3676 */       arrayOfBigInteger[j] = arrayOfBigInteger[(j - 1)].pow(2);
/*      */     }
/*      */     
/* 3679 */     BigInteger[][] arrayOfBigInteger1 = powerCache;
/* 3680 */     if (paramInt2 >= arrayOfBigInteger1[paramInt1].length) {
/* 3681 */       arrayOfBigInteger1 = (BigInteger[][])arrayOfBigInteger1.clone();
/* 3682 */       arrayOfBigInteger1[paramInt1] = arrayOfBigInteger;
/* 3683 */       powerCache = arrayOfBigInteger1;
/*      */     }
/* 3685 */     return arrayOfBigInteger[paramInt2];
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  551 */     bitsPerDigit = new long[] { 0L, 0L, 1024L, 1624L, 2048L, 2378L, 2648L, 2875L, 3072L, 3247L, 3402L, 3543L, 3672L, 3790L, 3899L, 4001L, 4096L, 4186L, 4271L, 4350L, 4426L, 4498L, 4567L, 4633L, 4696L, 4756L, 4814L, 4870L, 4923L, 4975L, 5025L, 5074L, 5120L, 5166L, 5210L, 5253L, 5295L };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  736 */     SMALL_PRIME_PRODUCT = valueOf(152125131763605L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1138 */     posConst = new BigInteger[17];
/* 1139 */     negConst = new BigInteger[17];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1152 */     LOG_TWO = Math.log(2.0D);
/*      */     
/*      */ 
/* 1155 */     for (int i = 1; i <= 16; i++) {
/* 1156 */       int[] arrayOfInt = new int[1];
/* 1157 */       arrayOfInt[0] = i;
/* 1158 */       posConst[i] = new BigInteger(arrayOfInt, 1);
/* 1159 */       negConst[i] = new BigInteger(arrayOfInt, -1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1167 */     powerCache = new BigInteger[37][];
/* 1168 */     logCache = new double[37];
/*      */     
/* 1170 */     for (i = 2; i <= 36; i++) {
/* 1171 */       powerCache[i] = { valueOf(i) };
/* 1172 */       logCache[i] = Math.log(i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1181 */     ZERO = new BigInteger(new int[0], 0);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1188 */     ONE = valueOf(1L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1193 */     TWO = valueOf(2L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1198 */     NEGATIVE_ONE = valueOf(-1L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1205 */     TEN = valueOf(10L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2504 */     bnExpModThreshTable = new int[] { 7, 25, 81, 241, 673, 1793, Integer.MAX_VALUE };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3689 */     zeros = new String[64];
/*      */     
/* 3691 */     zeros[63] = "000000000000000000000000000000000000000000000000000000000000000";
/*      */     
/* 3693 */     for (i = 0; i < 63; i++) {
/* 3694 */       zeros[i] = zeros[63].substring(0, i);
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
/*      */   public String toString()
/*      */   {
/* 3710 */     return toString(10);
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
/*      */   public byte[] toByteArray()
/*      */   {
/* 3728 */     int i = bitLength() / 8 + 1;
/* 3729 */     byte[] arrayOfByte = new byte[i];
/*      */     
/* 3731 */     int j = i - 1;int k = 4;int m = 0; for (int n = 0; j >= 0; j--) {
/* 3732 */       if (k == 4) {
/* 3733 */         m = getInt(n++);
/* 3734 */         k = 1;
/*      */       } else {
/* 3736 */         m >>>= 8;
/* 3737 */         k++;
/*      */       }
/* 3739 */       arrayOfByte[j] = ((byte)m);
/*      */     }
/* 3741 */     return arrayOfByte;
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
/*      */   public int intValue()
/*      */   {
/* 3760 */     int i = 0;
/* 3761 */     i = getInt(0);
/* 3762 */     return i;
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
/*      */   public long longValue()
/*      */   {
/* 3781 */     long l = 0L;
/*      */     
/* 3783 */     for (int i = 1; i >= 0; i--)
/* 3784 */       l = (l << 32) + (getInt(i) & 0xFFFFFFFF);
/* 3785 */     return l;
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
/*      */   public float floatValue()
/*      */   {
/* 3804 */     if (this.signum == 0) {
/* 3805 */       return 0.0F;
/*      */     }
/*      */     
/* 3808 */     int i = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1;
/*      */     
/*      */ 
/* 3811 */     if (i < 63)
/* 3812 */       return (float)longValue();
/* 3813 */     if (i > 127) {
/* 3814 */       return this.signum > 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
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
/* 3827 */     int j = i - 24;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3833 */     int m = j & 0x1F;
/* 3834 */     int n = 32 - m;
/*      */     int k;
/* 3836 */     if (m == 0) {
/* 3837 */       k = this.mag[0];
/*      */     } else {
/* 3839 */       k = this.mag[0] >>> m;
/* 3840 */       if (k == 0) {
/* 3841 */         k = this.mag[0] << n | this.mag[1] >>> m;
/*      */       }
/*      */     }
/*      */     
/* 3845 */     int i1 = k >> 1;
/* 3846 */     i1 &= 0x7FFFFF;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3856 */     int i2 = ((k & 0x1) != 0) && (((i1 & 0x1) != 0) || (abs().getLowestSetBit() < j)) ? 1 : 0;
/* 3857 */     int i3 = i2 != 0 ? i1 + 1 : i1;
/* 3858 */     int i4 = i + 127 << 23;
/*      */     
/* 3860 */     i4 += i3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3868 */     i4 |= this.signum & 0x80000000;
/* 3869 */     return Float.intBitsToFloat(i4);
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
/*      */   public double doubleValue()
/*      */   {
/* 3888 */     if (this.signum == 0) {
/* 3889 */       return 0.0D;
/*      */     }
/*      */     
/* 3892 */     int i = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1;
/*      */     
/*      */ 
/* 3895 */     if (i < 63)
/* 3896 */       return longValue();
/* 3897 */     if (i > 1023) {
/* 3898 */       return this.signum > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
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
/* 3911 */     int j = i - 53;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3917 */     int k = j & 0x1F;
/* 3918 */     int m = 32 - k;
/*      */     
/*      */     int n;
/*      */     int i1;
/* 3922 */     if (k == 0) {
/* 3923 */       n = this.mag[0];
/* 3924 */       i1 = this.mag[1];
/*      */     } else {
/* 3926 */       n = this.mag[0] >>> k;
/* 3927 */       i1 = this.mag[0] << m | this.mag[1] >>> k;
/* 3928 */       if (n == 0) {
/* 3929 */         n = i1;
/* 3930 */         i1 = this.mag[1] << m | this.mag[2] >>> k;
/*      */       }
/*      */     }
/*      */     
/* 3934 */     long l1 = (n & 0xFFFFFFFF) << 32 | i1 & 0xFFFFFFFF;
/*      */     
/*      */ 
/* 3937 */     long l2 = l1 >> 1;
/* 3938 */     l2 &= 0xFFFFFFFFFFFFF;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3948 */     int i2 = ((l1 & 1L) != 0L) && (((l2 & 1L) != 0L) || (abs().getLowestSetBit() < j)) ? 1 : 0;
/* 3949 */     long l3 = i2 != 0 ? l2 + 1L : l2;
/* 3950 */     long l4 = i + 1023 << 52;
/*      */     
/* 3952 */     l4 += l3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3960 */     l4 |= this.signum & 0x8000000000000000;
/* 3961 */     return Double.longBitsToDouble(l4);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int[] stripLeadingZeroInts(int[] paramArrayOfInt)
/*      */   {
/* 3968 */     int i = paramArrayOfInt.length;
/*      */     
/*      */ 
/*      */ 
/* 3972 */     for (int j = 0; (j < i) && (paramArrayOfInt[j] == 0); j++) {}
/*      */     
/* 3974 */     return Arrays.copyOfRange(paramArrayOfInt, j, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] trustedStripLeadingZeroInts(int[] paramArrayOfInt)
/*      */   {
/* 3982 */     int i = paramArrayOfInt.length;
/*      */     
/*      */ 
/*      */ 
/* 3986 */     for (int j = 0; (j < i) && (paramArrayOfInt[j] == 0); j++) {}
/*      */     
/* 3988 */     return j == 0 ? paramArrayOfInt : Arrays.copyOfRange(paramArrayOfInt, j, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int[] stripLeadingZeroBytes(byte[] paramArrayOfByte)
/*      */   {
/* 3995 */     int i = paramArrayOfByte.length;
/*      */     
/*      */ 
/*      */ 
/* 3999 */     for (int j = 0; (j < i) && (paramArrayOfByte[j] == 0); j++) {}
/*      */     
/*      */ 
/*      */ 
/* 4003 */     int k = i - j + 3 >>> 2;
/* 4004 */     int[] arrayOfInt = new int[k];
/* 4005 */     int m = i - 1;
/* 4006 */     for (int n = k - 1; n >= 0; n--) {
/* 4007 */       arrayOfInt[n] = (paramArrayOfByte[(m--)] & 0xFF);
/* 4008 */       int i1 = m - j + 1;
/* 4009 */       int i2 = Math.min(3, i1);
/* 4010 */       for (int i3 = 8; i3 <= i2 << 3; i3 += 8)
/* 4011 */         arrayOfInt[n] |= (paramArrayOfByte[(m--)] & 0xFF) << i3;
/*      */     }
/* 4013 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] makePositive(byte[] paramArrayOfByte)
/*      */   {
/* 4022 */     int k = paramArrayOfByte.length;
/*      */     
/*      */ 
/* 4025 */     for (int i = 0; (i < k) && (paramArrayOfByte[i] == -1); i++) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4031 */     for (int j = i; (j < k) && (paramArrayOfByte[j] == 0); j++) {}
/*      */     
/*      */ 
/* 4034 */     int m = j == k ? 1 : 0;
/* 4035 */     int n = k - i + m + 3 >>> 2;
/* 4036 */     int[] arrayOfInt = new int[n];
/*      */     
/*      */ 
/*      */ 
/* 4040 */     int i1 = k - 1;
/* 4041 */     for (int i2 = n - 1; i2 >= 0; i2--) {
/* 4042 */       arrayOfInt[i2] = (paramArrayOfByte[(i1--)] & 0xFF);
/* 4043 */       int i3 = Math.min(3, i1 - i + 1);
/* 4044 */       if (i3 < 0)
/* 4045 */         i3 = 0;
/* 4046 */       for (int i4 = 8; i4 <= 8 * i3; i4 += 8) {
/* 4047 */         arrayOfInt[i2] |= (paramArrayOfByte[(i1--)] & 0xFF) << i4;
/*      */       }
/*      */       
/* 4050 */       i4 = -1 >>> 8 * (3 - i3);
/* 4051 */       arrayOfInt[i2] = ((arrayOfInt[i2] ^ 0xFFFFFFFF) & i4);
/*      */     }
/*      */     
/*      */ 
/* 4055 */     for (i2 = arrayOfInt.length - 1; i2 >= 0; i2--) {
/* 4056 */       arrayOfInt[i2] = ((int)((arrayOfInt[i2] & 0xFFFFFFFF) + 1L));
/* 4057 */       if (arrayOfInt[i2] != 0) {
/*      */         break;
/*      */       }
/*      */     }
/* 4061 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] makePositive(int[] paramArrayOfInt)
/*      */   {
/* 4072 */     for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] == -1); i++) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4077 */     for (int j = i; (j < paramArrayOfInt.length) && (paramArrayOfInt[j] == 0); j++) {}
/*      */     
/* 4079 */     int k = j == paramArrayOfInt.length ? 1 : 0;
/* 4080 */     int[] arrayOfInt = new int[paramArrayOfInt.length - i + k];
/*      */     
/*      */ 
/*      */ 
/* 4084 */     for (int m = i; m < paramArrayOfInt.length; m++) {
/* 4085 */       arrayOfInt[(m - i + k)] = (paramArrayOfInt[m] ^ 0xFFFFFFFF);
/*      */     }
/*      */     
/* 4088 */     for (m = arrayOfInt.length - 1;; m--) if (arrayOfInt[m] += 1 != 0) {
/*      */         break;
/*      */       }
/* 4091 */     return arrayOfInt;
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
/* 4105 */   private static int[] digitsPerLong = { 0, 0, 62, 39, 31, 27, 24, 22, 20, 19, 18, 18, 17, 17, 16, 16, 15, 15, 15, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 12, 12 };
/*      */   
/*      */ 
/*      */ 
/* 4109 */   private static BigInteger[] longRadix = { null, null, 
/* 4110 */     valueOf(4611686018427387904L), valueOf(4052555153018976267L), 
/* 4111 */     valueOf(4611686018427387904L), valueOf(7450580596923828125L), 
/* 4112 */     valueOf(4738381338321616896L), valueOf(3909821048582988049L), 
/* 4113 */     valueOf(1152921504606846976L), valueOf(1350851717672992089L), 
/* 4114 */     valueOf(1000000000000000000L), valueOf(5559917313492231481L), 
/* 4115 */     valueOf(2218611106740436992L), valueOf(8650415919381337933L), 
/* 4116 */     valueOf(2177953337809371136L), valueOf(6568408355712890625L), 
/* 4117 */     valueOf(1152921504606846976L), valueOf(2862423051509815793L), 
/* 4118 */     valueOf(6746640616477458432L), valueOf(799006685782884121L), 
/* 4119 */     valueOf(1638400000000000000L), valueOf(3243919932521508681L), 
/* 4120 */     valueOf(6221821273427820544L), valueOf(504036361936467383L), 
/* 4121 */     valueOf(876488338465357824L), valueOf(1490116119384765625L), 
/* 4122 */     valueOf(2481152873203736576L), valueOf(4052555153018976267L), 
/* 4123 */     valueOf(6502111422497947648L), valueOf(353814783205469041L), 
/* 4124 */     valueOf(531441000000000000L), valueOf(787662783788549761L), 
/* 4125 */     valueOf(1152921504606846976L), valueOf(1667889514952984961L), 
/* 4126 */     valueOf(2386420683693101056L), valueOf(3379220508056640625L), 
/* 4127 */     valueOf(4738381338321616896L) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 4132 */   private static int[] digitsPerInt = { 0, 0, 30, 19, 15, 13, 11, 11, 10, 9, 9, 8, 8, 8, 8, 7, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5 };
/*      */   
/*      */ 
/*      */ 
/* 4136 */   private static int[] intRadix = { 0, 0, 1073741824, 1162261467, 1073741824, 1220703125, 362797056, 1977326743, 1073741824, 387420489, 1000000000, 214358881, 429981696, 815730721, 1475789056, 170859375, 268435456, 410338673, 612220032, 893871739, 1280000000, 1801088541, 113379904, 148035889, 191102976, 244140625, 308915776, 387420489, 481890304, 594823321, 729000000, 887503681, 1073741824, 1291467969, 1544804416, 1838265625, 60466176 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = -8287574255936472291L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int intLength()
/*      */   {
/* 4156 */     return (bitLength() >>> 5) + 1;
/*      */   }
/*      */   
/*      */   private int signBit()
/*      */   {
/* 4161 */     return this.signum < 0 ? 1 : 0;
/*      */   }
/*      */   
/*      */   private int signInt()
/*      */   {
/* 4166 */     return this.signum < 0 ? -1 : 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getInt(int paramInt)
/*      */   {
/* 4176 */     if (paramInt < 0)
/* 4177 */       return 0;
/* 4178 */     if (paramInt >= this.mag.length) {
/* 4179 */       return signInt();
/*      */     }
/* 4181 */     int i = this.mag[(this.mag.length - paramInt - 1)];
/*      */     
/*      */ 
/* 4184 */     return paramInt <= firstNonzeroIntNum() ? -i : this.signum >= 0 ? i : i ^ 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int firstNonzeroIntNum()
/*      */   {
/* 4193 */     int i = this.firstNonzeroIntNum - 2;
/* 4194 */     if (i == -2) {
/* 4195 */       i = 0;
/*      */       
/*      */ 
/*      */ 
/* 4199 */       int k = this.mag.length;
/* 4200 */       for (int j = k - 1; (j >= 0) && (this.mag[j] == 0); j--) {}
/*      */       
/* 4202 */       i = k - j - 1;
/* 4203 */       this.firstNonzeroIntNum = (i + 2);
/*      */     }
/* 4205 */     return i;
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
/* 4226 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("signum", Integer.TYPE), new ObjectStreamField("magnitude", byte[].class), new ObjectStreamField("bitCount", Integer.TYPE), new ObjectStreamField("bitLength", Integer.TYPE), new ObjectStreamField("firstNonzeroByteNum", Integer.TYPE), new ObjectStreamField("lowestSetBit", Integer.TYPE) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 4258 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*      */     
/*      */ 
/* 4261 */     int i = localGetField.get("signum", -2);
/* 4262 */     byte[] arrayOfByte = (byte[])localGetField.get("magnitude", null);
/*      */     
/*      */ 
/* 4265 */     if ((i < -1) || (i > 1)) {
/* 4266 */       localObject = "BigInteger: Invalid signum value";
/* 4267 */       if (localGetField.defaulted("signum"))
/* 4268 */         localObject = "BigInteger: Signum not present in stream";
/* 4269 */       throw new StreamCorruptedException((String)localObject);
/*      */     }
/* 4271 */     Object localObject = stripLeadingZeroBytes(arrayOfByte);
/* 4272 */     if ((localObject.length == 0 ? 1 : 0) != (i == 0 ? 1 : 0)) {
/* 4273 */       String str = "BigInteger: signum-magnitude mismatch";
/* 4274 */       if (localGetField.defaulted("magnitude"))
/* 4275 */         str = "BigInteger: Magnitude not present in stream";
/* 4276 */       throw new StreamCorruptedException(str);
/*      */     }
/*      */     
/*      */ 
/* 4280 */     UnsafeHolder.putSign(this, i);
/*      */     
/*      */ 
/* 4283 */     UnsafeHolder.putMag(this, (int[])localObject);
/* 4284 */     if (localObject.length >= 67108864) {
/*      */       try {
/* 4286 */         checkRange();
/*      */       } catch (ArithmeticException localArithmeticException) {
/* 4288 */         throw new StreamCorruptedException("BigInteger: Out of the supported range");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static class UnsafeHolder {
/*      */     private static final Unsafe unsafe;
/*      */     private static final long signumOffset;
/*      */     private static final long magOffset;
/*      */     
/*      */     static {
/*      */       try {
/* 4300 */         unsafe = Unsafe.getUnsafe();
/*      */         
/* 4302 */         signumOffset = unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("signum"));
/*      */         
/* 4304 */         magOffset = unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("mag"));
/*      */       } catch (Exception localException) {
/* 4306 */         throw new ExceptionInInitializerError(localException);
/*      */       }
/*      */     }
/*      */     
/*      */     static void putSign(BigInteger paramBigInteger, int paramInt) {
/* 4311 */       unsafe.putIntVolatile(paramBigInteger, signumOffset, paramInt);
/*      */     }
/*      */     
/*      */     static void putMag(BigInteger paramBigInteger, int[] paramArrayOfInt) {
/* 4315 */       unsafe.putObjectVolatile(paramBigInteger, magOffset, paramArrayOfInt);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 4329 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 4330 */     localPutField.put("signum", this.signum);
/* 4331 */     localPutField.put("magnitude", magSerializedForm());
/*      */     
/*      */ 
/* 4334 */     localPutField.put("bitCount", -1);
/* 4335 */     localPutField.put("bitLength", -1);
/* 4336 */     localPutField.put("lowestSetBit", -2);
/* 4337 */     localPutField.put("firstNonzeroByteNum", -2);
/*      */     
/*      */ 
/* 4340 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private byte[] magSerializedForm()
/*      */   {
/* 4347 */     int i = this.mag.length;
/*      */     
/* 4349 */     int j = i == 0 ? 0 : (i - 1 << 5) + bitLengthForInt(this.mag[0]);
/* 4350 */     int k = j + 7 >>> 3;
/* 4351 */     byte[] arrayOfByte = new byte[k];
/*      */     
/* 4353 */     int m = k - 1;int n = 4;int i1 = i - 1;int i2 = 0;
/* 4354 */     for (; m >= 0; m--) {
/* 4355 */       if (n == 4) {
/* 4356 */         i2 = this.mag[(i1--)];
/* 4357 */         n = 1;
/*      */       } else {
/* 4359 */         i2 >>>= 8;
/* 4360 */         n++;
/*      */       }
/* 4362 */       arrayOfByte[m] = ((byte)i2);
/*      */     }
/* 4364 */     return arrayOfByte;
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
/*      */   public long longValueExact()
/*      */   {
/* 4380 */     if ((this.mag.length <= 2) && (bitLength() <= 63)) {
/* 4381 */       return longValue();
/*      */     }
/* 4383 */     throw new ArithmeticException("BigInteger out of long range");
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
/*      */   public int intValueExact()
/*      */   {
/* 4399 */     if ((this.mag.length <= 1) && (bitLength() <= 31)) {
/* 4400 */       return intValue();
/*      */     }
/* 4402 */     throw new ArithmeticException("BigInteger out of int range");
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
/*      */   public short shortValueExact()
/*      */   {
/* 4418 */     if ((this.mag.length <= 1) && (bitLength() <= 31)) {
/* 4419 */       int i = intValue();
/* 4420 */       if ((i >= 32768) && (i <= 32767))
/* 4421 */         return shortValue();
/*      */     }
/* 4423 */     throw new ArithmeticException("BigInteger out of short range");
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
/*      */   public byte byteValueExact()
/*      */   {
/* 4439 */     if ((this.mag.length <= 1) && (bitLength() <= 31)) {
/* 4440 */       int i = intValue();
/* 4441 */       if ((i >= -128) && (i <= 127))
/* 4442 */         return byteValue();
/*      */     }
/* 4444 */     throw new ArithmeticException("BigInteger out of byte range");
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/math/BigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */