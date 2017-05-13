/*      */ package java.math;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigDecimal
/*      */   extends Number
/*      */   implements Comparable<BigDecimal>
/*      */ {
/*      */   private final BigInteger intVal;
/*      */   private final int scale;
/*      */   private transient int precision;
/*      */   private transient String stringCache;
/*      */   static final long INFLATED = Long.MIN_VALUE;
/*  261 */   private static final BigInteger INFLATED_BIGINT = BigInteger.valueOf(Long.MIN_VALUE);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final transient long intCompact;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final int MAX_COMPACT_DIGITS = 18;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 6108874887143696463L;
/*      */   
/*      */ 
/*      */ 
/*  278 */   private static final ThreadLocal<StringBuilderHelper> threadLocalStringBuilderHelper = new ThreadLocal()
/*      */   {
/*      */     protected BigDecimal.StringBuilderHelper initialValue() {
/*  281 */       return new BigDecimal.StringBuilderHelper();
/*      */     }
/*      */   };
/*      */   
/*      */ 
/*  286 */   private static final BigDecimal[] zeroThroughTen = { new BigDecimal(BigInteger.ZERO, 0L, 0, 1), new BigDecimal(BigInteger.ONE, 1L, 0, 1), new BigDecimal(
/*      */   
/*      */ 
/*  289 */     BigInteger.valueOf(2L), 2L, 0, 1), new BigDecimal(
/*  290 */     BigInteger.valueOf(3L), 3L, 0, 1), new BigDecimal(
/*  291 */     BigInteger.valueOf(4L), 4L, 0, 1), new BigDecimal(
/*  292 */     BigInteger.valueOf(5L), 5L, 0, 1), new BigDecimal(
/*  293 */     BigInteger.valueOf(6L), 6L, 0, 1), new BigDecimal(
/*  294 */     BigInteger.valueOf(7L), 7L, 0, 1), new BigDecimal(
/*  295 */     BigInteger.valueOf(8L), 8L, 0, 1), new BigDecimal(
/*  296 */     BigInteger.valueOf(9L), 9L, 0, 1), new BigDecimal(BigInteger.TEN, 10L, 0, 2) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  301 */   private static final BigDecimal[] ZERO_SCALED_BY = { zeroThroughTen[0], new BigDecimal(BigInteger.ZERO, 0L, 1, 1), new BigDecimal(BigInteger.ZERO, 0L, 2, 1), new BigDecimal(BigInteger.ZERO, 0L, 3, 1), new BigDecimal(BigInteger.ZERO, 0L, 4, 1), new BigDecimal(BigInteger.ZERO, 0L, 5, 1), new BigDecimal(BigInteger.ZERO, 0L, 6, 1), new BigDecimal(BigInteger.ZERO, 0L, 7, 1), new BigDecimal(BigInteger.ZERO, 0L, 8, 1), new BigDecimal(BigInteger.ZERO, 0L, 9, 1), new BigDecimal(BigInteger.ZERO, 0L, 10, 1), new BigDecimal(BigInteger.ZERO, 0L, 11, 1), new BigDecimal(BigInteger.ZERO, 0L, 12, 1), new BigDecimal(BigInteger.ZERO, 0L, 13, 1), new BigDecimal(BigInteger.ZERO, 0L, 14, 1), new BigDecimal(BigInteger.ZERO, 0L, 15, 1) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long HALF_LONG_MAX_VALUE = 4611686018427387903L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long HALF_LONG_MIN_VALUE = -4611686018427387904L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  330 */   public static final BigDecimal ZERO = zeroThroughTen[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  338 */   public static final BigDecimal ONE = zeroThroughTen[1];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  346 */   public static final BigDecimal TEN = zeroThroughTen[10];
/*      */   public static final int ROUND_UP = 0;
/*      */   public static final int ROUND_DOWN = 1;
/*      */   public static final int ROUND_CEILING = 2;
/*      */   public static final int ROUND_FLOOR = 3;
/*      */   public static final int ROUND_HALF_UP = 4;
/*      */   public static final int ROUND_HALF_DOWN = 5;
/*      */   public static final int ROUND_HALF_EVEN = 6;
/*      */   public static final int ROUND_UNNECESSARY = 7;
/*      */   
/*      */   BigDecimal(BigInteger paramBigInteger, long paramLong, int paramInt1, int paramInt2) {
/*  357 */     this.scale = paramInt1;
/*  358 */     this.precision = paramInt2;
/*  359 */     this.intCompact = paramLong;
/*  360 */     this.intVal = paramBigInteger;
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
/*      */   public BigDecimal(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  383 */     this(paramArrayOfChar, paramInt1, paramInt2, MathContext.UNLIMITED);
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
/*      */   public BigDecimal(char[] paramArrayOfChar, int paramInt1, int paramInt2, MathContext paramMathContext)
/*      */   {
/*  411 */     if ((paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 < 0)) {
/*  412 */       throw new NumberFormatException("Bad offset or len arguments for char[] input.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  419 */     int i = 0;
/*  420 */     int j = 0;
/*  421 */     long l1 = 0L;
/*  422 */     BigInteger localBigInteger = null;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  427 */       int k = 0;
/*  428 */       if (paramArrayOfChar[paramInt1] == '-') {
/*  429 */         k = 1;
/*  430 */         paramInt1++;
/*  431 */         paramInt2--;
/*  432 */       } else if (paramArrayOfChar[paramInt1] == '+') {
/*  433 */         paramInt1++;
/*  434 */         paramInt2--;
/*      */       }
/*      */       
/*      */ 
/*  438 */       int m = 0;
/*  439 */       long l2 = 0L;
/*      */       
/*  441 */       int n = paramInt2 <= 18 ? 1 : 0;
/*      */       
/*      */ 
/*  444 */       int i1 = 0;
/*  445 */       char c; int i3; if (n != 0)
/*      */       {
/*  448 */         for (; 
/*  448 */             paramInt2 > 0; paramInt2--) {
/*  449 */           c = paramArrayOfChar[paramInt1];
/*  450 */           if (c == '0') {
/*  451 */             if (i == 0) {
/*  452 */               i = 1;
/*  453 */             } else if (l1 != 0L) {
/*  454 */               l1 *= 10L;
/*  455 */               i++;
/*      */             }
/*  457 */             if (m != 0)
/*  458 */               j++;
/*  459 */           } else if ((c >= '1') && (c <= '9')) {
/*  460 */             i2 = c - '0';
/*  461 */             if ((i != 1) || (l1 != 0L))
/*  462 */               i++;
/*  463 */             l1 = l1 * 10L + i2;
/*  464 */             if (m != 0)
/*  465 */               j++;
/*  466 */           } else if (c == '.')
/*      */           {
/*  468 */             if (m != 0)
/*  469 */               throw new NumberFormatException();
/*  470 */             m = 1;
/*  471 */           } else if (Character.isDigit(c)) {
/*  472 */             i2 = Character.digit(c, 10);
/*  473 */             if (i2 == 0) {
/*  474 */               if (i == 0) {
/*  475 */                 i = 1;
/*  476 */               } else if (l1 != 0L) {
/*  477 */                 l1 *= 10L;
/*  478 */                 i++;
/*      */               }
/*      */             } else {
/*  481 */               if ((i != 1) || (l1 != 0L))
/*  482 */                 i++;
/*  483 */               l1 = l1 * 10L + i2;
/*      */             }
/*  485 */             if (m != 0)
/*  486 */               j++;
/*  487 */           } else { if ((c == 'e') || (c == 'E')) {
/*  488 */               l2 = parseExp(paramArrayOfChar, paramInt1, paramInt2);
/*      */               
/*  490 */               if ((int)l2 == l2) break;
/*  491 */               throw new NumberFormatException();
/*      */             }
/*      */             
/*  494 */             throw new NumberFormatException();
/*      */           }
/*  448 */           paramInt1++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  497 */         if (i == 0) {
/*  498 */           throw new NumberFormatException();
/*      */         }
/*  500 */         if (l2 != 0L) {
/*  501 */           j = adjustScale(j, l2);
/*      */         }
/*  503 */         l1 = k != 0 ? -l1 : l1;
/*  504 */         int i2 = paramMathContext.precision;
/*  505 */         i3 = i - i2;
/*      */         
/*  507 */         if ((i2 > 0) && (i3 > 0)) {
/*  508 */           while (i3 > 0) {
/*  509 */             j = checkScaleNonZero(j - i3);
/*  510 */             l1 = divideAndRound(l1, LONG_TEN_POWERS_TABLE[i3], paramMathContext.roundingMode.oldMode);
/*  511 */             i = longDigitLength(l1);
/*  512 */             i3 = i - i2;
/*      */           }
/*      */         }
/*      */       } else {
/*  516 */         char[] arrayOfChar = new char[paramInt2];
/*  517 */         for (; paramInt2 > 0; paramInt2--) {
/*  518 */           c = paramArrayOfChar[paramInt1];
/*      */           
/*  520 */           if (((c >= '0') && (c <= '9')) || (Character.isDigit(c)))
/*      */           {
/*      */ 
/*  523 */             if ((c == '0') || (Character.digit(c, 10) == 0)) {
/*  524 */               if (i == 0) {
/*  525 */                 arrayOfChar[i1] = c;
/*  526 */                 i = 1;
/*  527 */               } else if (i1 != 0) {
/*  528 */                 arrayOfChar[(i1++)] = c;
/*  529 */                 i++;
/*      */               }
/*      */             } else {
/*  532 */               if ((i != 1) || (i1 != 0))
/*  533 */                 i++;
/*  534 */               arrayOfChar[(i1++)] = c;
/*      */             }
/*  536 */             if (m != 0) {
/*  537 */               j++;
/*      */             }
/*      */             
/*      */           }
/*  541 */           else if (c == '.')
/*      */           {
/*  543 */             if (m != 0)
/*  544 */               throw new NumberFormatException();
/*  545 */             m = 1;
/*      */           }
/*      */           else
/*      */           {
/*  549 */             if ((c != 'e') && (c != 'E'))
/*  550 */               throw new NumberFormatException();
/*  551 */             l2 = parseExp(paramArrayOfChar, paramInt1, paramInt2);
/*      */             
/*  553 */             if ((int)l2 == l2) break;
/*  554 */             throw new NumberFormatException();
/*      */           }
/*  517 */           paramInt1++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  558 */         if (i == 0) {
/*  559 */           throw new NumberFormatException();
/*      */         }
/*  561 */         if (l2 != 0L) {
/*  562 */           j = adjustScale(j, l2);
/*      */         }
/*      */         
/*  565 */         localBigInteger = new BigInteger(arrayOfChar, k != 0 ? -1 : 1, i);
/*  566 */         l1 = compactValFor(localBigInteger);
/*  567 */         i3 = paramMathContext.precision;
/*  568 */         if ((i3 > 0) && (i > i3)) { int i4;
/*  569 */           if (l1 == Long.MIN_VALUE) {
/*  570 */             i4 = i - i3;
/*  571 */             while (i4 > 0) {
/*  572 */               j = checkScaleNonZero(j - i4);
/*  573 */               localBigInteger = divideAndRoundByTenPow(localBigInteger, i4, paramMathContext.roundingMode.oldMode);
/*  574 */               l1 = compactValFor(localBigInteger);
/*  575 */               if (l1 != Long.MIN_VALUE) {
/*  576 */                 i = longDigitLength(l1);
/*  577 */                 break;
/*      */               }
/*  579 */               i = bigDigitLength(localBigInteger);
/*  580 */               i4 = i - i3;
/*      */             }
/*      */           }
/*  583 */           if (l1 != Long.MIN_VALUE) {
/*  584 */             i4 = i - i3;
/*  585 */             while (i4 > 0) {
/*  586 */               j = checkScaleNonZero(j - i4);
/*  587 */               l1 = divideAndRound(l1, LONG_TEN_POWERS_TABLE[i4], paramMathContext.roundingMode.oldMode);
/*  588 */               i = longDigitLength(l1);
/*  589 */               i4 = i - i3;
/*      */             }
/*  591 */             localBigInteger = null;
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/*  596 */       throw new NumberFormatException();
/*      */     } catch (NegativeArraySizeException localNegativeArraySizeException) {
/*  598 */       throw new NumberFormatException();
/*      */     }
/*  600 */     this.scale = j;
/*  601 */     this.precision = i;
/*  602 */     this.intCompact = l1;
/*  603 */     this.intVal = localBigInteger;
/*      */   }
/*      */   
/*      */   private int adjustScale(int paramInt, long paramLong) {
/*  607 */     long l = paramInt - paramLong;
/*  608 */     if ((l > 2147483647L) || (l < -2147483648L))
/*  609 */       throw new NumberFormatException("Scale out of range.");
/*  610 */     paramInt = (int)l;
/*  611 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static long parseExp(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  618 */     long l = 0L;
/*  619 */     paramInt1++;
/*  620 */     char c = paramArrayOfChar[paramInt1];
/*  621 */     paramInt2--;
/*  622 */     int i = c == '-' ? 1 : 0;
/*      */     
/*  624 */     if ((i != 0) || (c == '+')) {
/*  625 */       paramInt1++;
/*  626 */       c = paramArrayOfChar[paramInt1];
/*  627 */       paramInt2--;
/*      */     }
/*  629 */     if (paramInt2 <= 0) {
/*  630 */       throw new NumberFormatException();
/*      */     }
/*  632 */     while ((paramInt2 > 10) && ((c == '0') || (Character.digit(c, 10) == 0))) {
/*  633 */       paramInt1++;
/*  634 */       c = paramArrayOfChar[paramInt1];
/*  635 */       paramInt2--;
/*      */     }
/*  637 */     if (paramInt2 > 10) {
/*  638 */       throw new NumberFormatException();
/*      */     }
/*  640 */     for (;; paramInt2--) {
/*      */       int j;
/*  642 */       if ((c >= '0') && (c <= '9')) {
/*  643 */         j = c - '0';
/*      */       } else {
/*  645 */         j = Character.digit(c, 10);
/*  646 */         if (j < 0)
/*  647 */           throw new NumberFormatException();
/*      */       }
/*  649 */       l = l * 10L + j;
/*  650 */       if (paramInt2 == 1)
/*      */         break;
/*  652 */       paramInt1++;
/*  653 */       c = paramArrayOfChar[paramInt1];
/*      */     }
/*  655 */     if (i != 0)
/*  656 */       l = -l;
/*  657 */     return l;
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
/*      */   public BigDecimal(char[] paramArrayOfChar)
/*      */   {
/*  677 */     this(paramArrayOfChar, 0, paramArrayOfChar.length);
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
/*      */   public BigDecimal(char[] paramArrayOfChar, MathContext paramMathContext)
/*      */   {
/*  701 */     this(paramArrayOfChar, 0, paramArrayOfChar.length, paramMathContext);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal(String paramString)
/*      */   {
/*  806 */     this(paramString.toCharArray(), 0, paramString.length());
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
/*      */   public BigDecimal(String paramString, MathContext paramMathContext)
/*      */   {
/*  824 */     this(paramString.toCharArray(), 0, paramString.length(), paramMathContext);
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
/*      */   public BigDecimal(double paramDouble)
/*      */   {
/*  872 */     this(paramDouble, MathContext.UNLIMITED);
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
/*      */   public BigDecimal(double paramDouble, MathContext paramMathContext)
/*      */   {
/*  894 */     if ((Double.isInfinite(paramDouble)) || (Double.isNaN(paramDouble))) {
/*  895 */       throw new NumberFormatException("Infinite or NaN");
/*      */     }
/*      */     
/*  898 */     long l1 = Double.doubleToLongBits(paramDouble);
/*  899 */     int i = l1 >> 63 == 0L ? 1 : -1;
/*  900 */     int j = (int)(l1 >> 52 & 0x7FF);
/*  901 */     long l2 = j == 0 ? (l1 & 0xFFFFFFFFFFFFF) << 1 : l1 & 0xFFFFFFFFFFFFF | 0x10000000000000;
/*      */     
/*      */ 
/*  904 */     j -= 1075;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  911 */     if (l2 == 0L) {
/*  912 */       this.intVal = BigInteger.ZERO;
/*  913 */       this.scale = 0;
/*  914 */       this.intCompact = 0L;
/*  915 */       this.precision = 1;
/*  916 */       return;
/*      */     }
/*      */     
/*  919 */     while ((l2 & 1L) == 0L) {
/*  920 */       l2 >>= 1;
/*  921 */       j++;
/*      */     }
/*  923 */     int k = 0;
/*      */     
/*      */ 
/*  926 */     long l3 = i * l2;
/*  927 */     BigInteger localBigInteger; if (j == 0) {
/*  928 */       localBigInteger = l3 == Long.MIN_VALUE ? INFLATED_BIGINT : null;
/*      */     } else {
/*  930 */       if (j < 0) {
/*  931 */         localBigInteger = BigInteger.valueOf(5L).pow(-j).multiply(l3);
/*  932 */         k = -j;
/*      */       } else {
/*  934 */         localBigInteger = BigInteger.valueOf(2L).pow(j).multiply(l3);
/*      */       }
/*  936 */       l3 = compactValFor(localBigInteger);
/*      */     }
/*  938 */     int m = 0;
/*  939 */     int n = paramMathContext.precision;
/*  940 */     if (n > 0) {
/*  941 */       int i1 = paramMathContext.roundingMode.oldMode;
/*      */       int i2;
/*  943 */       if (l3 == Long.MIN_VALUE) {
/*  944 */         m = bigDigitLength(localBigInteger);
/*  945 */         i2 = m - n;
/*  946 */         while (i2 > 0) {
/*  947 */           k = checkScaleNonZero(k - i2);
/*  948 */           localBigInteger = divideAndRoundByTenPow(localBigInteger, i2, i1);
/*  949 */           l3 = compactValFor(localBigInteger);
/*  950 */           if (l3 != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/*  953 */           m = bigDigitLength(localBigInteger);
/*  954 */           i2 = m - n;
/*      */         }
/*      */       }
/*  957 */       if (l3 != Long.MIN_VALUE) {
/*  958 */         m = longDigitLength(l3);
/*  959 */         i2 = m - n;
/*  960 */         while (i2 > 0) {
/*  961 */           k = checkScaleNonZero(k - i2);
/*  962 */           l3 = divideAndRound(l3, LONG_TEN_POWERS_TABLE[i2], paramMathContext.roundingMode.oldMode);
/*  963 */           m = longDigitLength(l3);
/*  964 */           i2 = m - n;
/*      */         }
/*  966 */         localBigInteger = null;
/*      */       }
/*      */     }
/*  969 */     this.intVal = localBigInteger;
/*  970 */     this.intCompact = l3;
/*  971 */     this.scale = k;
/*  972 */     this.precision = m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal(BigInteger paramBigInteger)
/*      */   {
/*  983 */     this.scale = 0;
/*  984 */     this.intVal = paramBigInteger;
/*  985 */     this.intCompact = compactValFor(paramBigInteger);
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
/*      */   public BigDecimal(BigInteger paramBigInteger, MathContext paramMathContext)
/*      */   {
/* 1001 */     this(paramBigInteger, 0, paramMathContext);
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
/*      */   public BigDecimal(BigInteger paramBigInteger, int paramInt)
/*      */   {
/* 1015 */     this.intVal = paramBigInteger;
/* 1016 */     this.intCompact = compactValFor(paramBigInteger);
/* 1017 */     this.scale = paramInt;
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
/*      */   public BigDecimal(BigInteger paramBigInteger, int paramInt, MathContext paramMathContext)
/*      */   {
/* 1036 */     long l = compactValFor(paramBigInteger);
/* 1037 */     int i = paramMathContext.precision;
/* 1038 */     int j = 0;
/* 1039 */     if (i > 0) {
/* 1040 */       int k = paramMathContext.roundingMode.oldMode;
/* 1041 */       int m; if (l == Long.MIN_VALUE) {
/* 1042 */         j = bigDigitLength(paramBigInteger);
/* 1043 */         m = j - i;
/* 1044 */         while (m > 0) {
/* 1045 */           paramInt = checkScaleNonZero(paramInt - m);
/* 1046 */           paramBigInteger = divideAndRoundByTenPow(paramBigInteger, m, k);
/* 1047 */           l = compactValFor(paramBigInteger);
/* 1048 */           if (l != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/* 1051 */           j = bigDigitLength(paramBigInteger);
/* 1052 */           m = j - i;
/*      */         }
/*      */       }
/* 1055 */       if (l != Long.MIN_VALUE) {
/* 1056 */         j = longDigitLength(l);
/* 1057 */         m = j - i;
/* 1058 */         while (m > 0) {
/* 1059 */           paramInt = checkScaleNonZero(paramInt - m);
/* 1060 */           l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], k);
/* 1061 */           j = longDigitLength(l);
/* 1062 */           m = j - i;
/*      */         }
/* 1064 */         paramBigInteger = null;
/*      */       }
/*      */     }
/* 1067 */     this.intVal = paramBigInteger;
/* 1068 */     this.intCompact = l;
/* 1069 */     this.scale = paramInt;
/* 1070 */     this.precision = j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal(int paramInt)
/*      */   {
/* 1082 */     this.intCompact = paramInt;
/* 1083 */     this.scale = 0;
/* 1084 */     this.intVal = null;
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
/*      */   public BigDecimal(int paramInt, MathContext paramMathContext)
/*      */   {
/* 1099 */     int i = paramMathContext.precision;
/* 1100 */     long l = paramInt;
/* 1101 */     int j = 0;
/* 1102 */     int k = 0;
/* 1103 */     if (i > 0) {
/* 1104 */       k = longDigitLength(l);
/* 1105 */       int m = k - i;
/* 1106 */       while (m > 0) {
/* 1107 */         j = checkScaleNonZero(j - m);
/* 1108 */         l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], paramMathContext.roundingMode.oldMode);
/* 1109 */         k = longDigitLength(l);
/* 1110 */         m = k - i;
/*      */       }
/*      */     }
/* 1113 */     this.intVal = null;
/* 1114 */     this.intCompact = l;
/* 1115 */     this.scale = j;
/* 1116 */     this.precision = k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal(long paramLong)
/*      */   {
/* 1127 */     this.intCompact = paramLong;
/* 1128 */     this.intVal = (paramLong == Long.MIN_VALUE ? INFLATED_BIGINT : null);
/* 1129 */     this.scale = 0;
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
/*      */   public BigDecimal(long paramLong, MathContext paramMathContext)
/*      */   {
/* 1144 */     int i = paramMathContext.precision;
/* 1145 */     int j = paramMathContext.roundingMode.oldMode;
/* 1146 */     int k = 0;
/* 1147 */     int m = 0;
/* 1148 */     BigInteger localBigInteger = paramLong == Long.MIN_VALUE ? INFLATED_BIGINT : null;
/* 1149 */     if (i > 0) { int n;
/* 1150 */       if (paramLong == Long.MIN_VALUE) {
/* 1151 */         k = 19;
/* 1152 */         n = k - i;
/* 1153 */         while (n > 0) {
/* 1154 */           m = checkScaleNonZero(m - n);
/* 1155 */           localBigInteger = divideAndRoundByTenPow(localBigInteger, n, j);
/* 1156 */           paramLong = compactValFor(localBigInteger);
/* 1157 */           if (paramLong != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/* 1160 */           k = bigDigitLength(localBigInteger);
/* 1161 */           n = k - i;
/*      */         }
/*      */       }
/* 1164 */       if (paramLong != Long.MIN_VALUE) {
/* 1165 */         k = longDigitLength(paramLong);
/* 1166 */         n = k - i;
/* 1167 */         while (n > 0) {
/* 1168 */           m = checkScaleNonZero(m - n);
/* 1169 */           paramLong = divideAndRound(paramLong, LONG_TEN_POWERS_TABLE[n], paramMathContext.roundingMode.oldMode);
/* 1170 */           k = longDigitLength(paramLong);
/* 1171 */           n = k - i;
/*      */         }
/* 1173 */         localBigInteger = null;
/*      */       }
/*      */     }
/* 1176 */     this.intVal = localBigInteger;
/* 1177 */     this.intCompact = paramLong;
/* 1178 */     this.scale = m;
/* 1179 */     this.precision = k;
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
/*      */   public static BigDecimal valueOf(long paramLong, int paramInt)
/*      */   {
/* 1197 */     if (paramInt == 0)
/* 1198 */       return valueOf(paramLong);
/* 1199 */     if (paramLong == 0L) {
/* 1200 */       return zeroValueOf(paramInt);
/*      */     }
/* 1202 */     return new BigDecimal(paramLong == Long.MIN_VALUE ? INFLATED_BIGINT : null, paramLong, paramInt, 0);
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
/*      */   public static BigDecimal valueOf(long paramLong)
/*      */   {
/* 1218 */     if ((paramLong >= 0L) && (paramLong < zeroThroughTen.length))
/* 1219 */       return zeroThroughTen[((int)paramLong)];
/* 1220 */     if (paramLong != Long.MIN_VALUE)
/* 1221 */       return new BigDecimal(null, paramLong, 0, 0);
/* 1222 */     return new BigDecimal(INFLATED_BIGINT, paramLong, 0, 0);
/*      */   }
/*      */   
/*      */   static BigDecimal valueOf(long paramLong, int paramInt1, int paramInt2) {
/* 1226 */     if ((paramInt1 == 0) && (paramLong >= 0L) && (paramLong < zeroThroughTen.length))
/* 1227 */       return zeroThroughTen[((int)paramLong)];
/* 1228 */     if (paramLong == 0L) {
/* 1229 */       return zeroValueOf(paramInt1);
/*      */     }
/* 1231 */     return new BigDecimal(paramLong == Long.MIN_VALUE ? INFLATED_BIGINT : null, paramLong, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   static BigDecimal valueOf(BigInteger paramBigInteger, int paramInt1, int paramInt2)
/*      */   {
/* 1236 */     long l = compactValFor(paramBigInteger);
/* 1237 */     if (l == 0L)
/* 1238 */       return zeroValueOf(paramInt1);
/* 1239 */     if ((paramInt1 == 0) && (l >= 0L) && (l < zeroThroughTen.length)) {
/* 1240 */       return zeroThroughTen[((int)l)];
/*      */     }
/* 1242 */     return new BigDecimal(paramBigInteger, l, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   static BigDecimal zeroValueOf(int paramInt) {
/* 1246 */     if ((paramInt >= 0) && (paramInt < ZERO_SCALED_BY.length)) {
/* 1247 */       return ZERO_SCALED_BY[paramInt];
/*      */     }
/* 1249 */     return new BigDecimal(BigInteger.ZERO, 0L, paramInt, 1);
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
/*      */   public static BigDecimal valueOf(double paramDouble)
/*      */   {
/* 1274 */     return new BigDecimal(Double.toString(paramDouble));
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
/*      */   public BigDecimal add(BigDecimal paramBigDecimal)
/*      */   {
/* 1287 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1288 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1289 */         return add(this.intCompact, this.scale, paramBigDecimal.intCompact, paramBigDecimal.scale);
/*      */       }
/* 1291 */       return add(this.intCompact, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale);
/*      */     }
/*      */     
/* 1294 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1295 */       return add(paramBigDecimal.intCompact, paramBigDecimal.scale, this.intVal, this.scale);
/*      */     }
/* 1297 */     return add(this.intVal, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale);
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
/*      */   public BigDecimal add(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1317 */     if (paramMathContext.precision == 0)
/* 1318 */       return add(paramBigDecimal);
/* 1319 */     BigDecimal localBigDecimal1 = this;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1324 */     int i = localBigDecimal1.signum() == 0 ? 1 : 0;
/* 1325 */     int j = paramBigDecimal.signum() == 0 ? 1 : 0;
/*      */     
/* 1327 */     if ((i != 0) || (j != 0)) {
/* 1328 */       int k = Math.max(localBigDecimal1.scale(), paramBigDecimal.scale());
/*      */       
/*      */ 
/* 1331 */       if ((i != 0) && (j != 0))
/* 1332 */         return zeroValueOf(k);
/* 1333 */       BigDecimal localBigDecimal2 = i != 0 ? doRound(paramBigDecimal, paramMathContext) : doRound(localBigDecimal1, paramMathContext);
/*      */       
/* 1335 */       if (localBigDecimal2.scale() == k)
/* 1336 */         return localBigDecimal2;
/* 1337 */       if (localBigDecimal2.scale() > k) {
/* 1338 */         return stripZerosToMatchScale(localBigDecimal2.intVal, localBigDecimal2.intCompact, localBigDecimal2.scale, k);
/*      */       }
/* 1340 */       int m = paramMathContext.precision - localBigDecimal2.precision();
/* 1341 */       int n = k - localBigDecimal2.scale();
/*      */       
/* 1343 */       if (m >= n) {
/* 1344 */         return localBigDecimal2.setScale(k);
/*      */       }
/* 1346 */       return localBigDecimal2.setScale(localBigDecimal2.scale() + m);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1351 */     long l = localBigDecimal1.scale - paramBigDecimal.scale;
/* 1352 */     if (l != 0L) {
/* 1353 */       BigDecimal[] arrayOfBigDecimal = preAlign(localBigDecimal1, paramBigDecimal, l, paramMathContext);
/* 1354 */       matchScale(arrayOfBigDecimal);
/* 1355 */       localBigDecimal1 = arrayOfBigDecimal[0];
/* 1356 */       paramBigDecimal = arrayOfBigDecimal[1];
/*      */     }
/* 1358 */     return doRound(localBigDecimal1.inflated().add(paramBigDecimal.inflated()), localBigDecimal1.scale, paramMathContext);
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
/*      */   private BigDecimal[] preAlign(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, long paramLong, MathContext paramMathContext)
/*      */   {
/* 1384 */     assert (paramLong != 0L);
/*      */     
/*      */     BigDecimal localBigDecimal1;
/*      */     BigDecimal localBigDecimal2;
/* 1388 */     if (paramLong < 0L) {
/* 1389 */       localBigDecimal1 = paramBigDecimal1;
/* 1390 */       localBigDecimal2 = paramBigDecimal2;
/*      */     } else {
/* 1392 */       localBigDecimal1 = paramBigDecimal2;
/* 1393 */       localBigDecimal2 = paramBigDecimal1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1402 */     long l1 = localBigDecimal1.scale - localBigDecimal1.precision() + paramMathContext.precision;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1413 */     long l2 = localBigDecimal2.scale - localBigDecimal2.precision() + 1L;
/* 1414 */     if ((l2 > localBigDecimal1.scale + 2) && (l2 > l1 + 2L))
/*      */     {
/* 1416 */       localBigDecimal2 = valueOf(localBigDecimal2.signum(), checkScale(Math.max(localBigDecimal1.scale, l1) + 3L));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1421 */     BigDecimal[] arrayOfBigDecimal = { localBigDecimal1, localBigDecimal2 };
/* 1422 */     return arrayOfBigDecimal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal subtract(BigDecimal paramBigDecimal)
/*      */   {
/* 1434 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1435 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1436 */         return add(this.intCompact, this.scale, -paramBigDecimal.intCompact, paramBigDecimal.scale);
/*      */       }
/* 1438 */       return add(this.intCompact, this.scale, paramBigDecimal.intVal.negate(), paramBigDecimal.scale);
/*      */     }
/*      */     
/* 1441 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE)
/*      */     {
/*      */ 
/*      */ 
/* 1445 */       return add(-paramBigDecimal.intCompact, paramBigDecimal.scale, this.intVal, this.scale);
/*      */     }
/* 1447 */     return add(this.intVal, this.scale, paramBigDecimal.intVal.negate(), paramBigDecimal.scale);
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
/*      */   public BigDecimal subtract(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1467 */     if (paramMathContext.precision == 0) {
/* 1468 */       return subtract(paramBigDecimal);
/*      */     }
/* 1470 */     return add(paramBigDecimal.negate(), paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal multiply(BigDecimal paramBigDecimal)
/*      */   {
/* 1482 */     int i = checkScale(this.scale + paramBigDecimal.scale);
/* 1483 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1484 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1485 */         return multiply(this.intCompact, paramBigDecimal.intCompact, i);
/*      */       }
/* 1487 */       return multiply(this.intCompact, paramBigDecimal.intVal, i);
/*      */     }
/*      */     
/* 1490 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1491 */       return multiply(paramBigDecimal.intCompact, this.intVal, i);
/*      */     }
/* 1493 */     return multiply(this.intVal, paramBigDecimal.intVal, i);
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
/*      */   public BigDecimal multiply(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1510 */     if (paramMathContext.precision == 0)
/* 1511 */       return multiply(paramBigDecimal);
/* 1512 */     int i = checkScale(this.scale + paramBigDecimal.scale);
/* 1513 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1514 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1515 */         return multiplyAndRound(this.intCompact, paramBigDecimal.intCompact, i, paramMathContext);
/*      */       }
/* 1517 */       return multiplyAndRound(this.intCompact, paramBigDecimal.intVal, i, paramMathContext);
/*      */     }
/*      */     
/* 1520 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1521 */       return multiplyAndRound(paramBigDecimal.intCompact, this.intVal, i, paramMathContext);
/*      */     }
/* 1523 */     return multiplyAndRound(this.intVal, paramBigDecimal.intVal, i, paramMathContext);
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
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, int paramInt1, int paramInt2)
/*      */   {
/* 1557 */     if ((paramInt2 < 0) || (paramInt2 > 7))
/* 1558 */       throw new IllegalArgumentException("Invalid rounding mode");
/* 1559 */     if (this.intCompact != Long.MIN_VALUE) {
/* 1560 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1561 */         return divide(this.intCompact, this.scale, paramBigDecimal.intCompact, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */       }
/* 1563 */       return divide(this.intCompact, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */     }
/*      */     
/* 1566 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1567 */       return divide(this.intVal, this.scale, paramBigDecimal.intCompact, paramBigDecimal.scale, paramInt1, paramInt2);
/*      */     }
/* 1569 */     return divide(this.intVal, this.scale, paramBigDecimal.intVal, paramBigDecimal.scale, paramInt1, paramInt2);
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
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, int paramInt, RoundingMode paramRoundingMode)
/*      */   {
/* 1591 */     return divide(paramBigDecimal, paramInt, paramRoundingMode.oldMode);
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
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, int paramInt)
/*      */   {
/* 1622 */     return divide(paramBigDecimal, this.scale, paramInt);
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
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, RoundingMode paramRoundingMode)
/*      */   {
/* 1641 */     return divide(paramBigDecimal, this.scale, paramRoundingMode.oldMode);
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
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal)
/*      */   {
/* 1662 */     if (paramBigDecimal.signum() == 0) {
/* 1663 */       if (signum() == 0)
/* 1664 */         throw new ArithmeticException("Division undefined");
/* 1665 */       throw new ArithmeticException("Division by zero");
/*      */     }
/*      */     
/*      */ 
/* 1669 */     int i = saturateLong(this.scale - paramBigDecimal.scale);
/*      */     
/* 1671 */     if (signum() == 0) {
/* 1672 */       return zeroValueOf(i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1682 */     MathContext localMathContext = new MathContext((int)Math.min(precision() + 
/* 1683 */       Math.ceil(10.0D * paramBigDecimal.precision() / 3.0D), 2147483647L), RoundingMode.UNNECESSARY);
/*      */     
/*      */     BigDecimal localBigDecimal;
/*      */     try
/*      */     {
/* 1688 */       localBigDecimal = divide(paramBigDecimal, localMathContext);
/*      */     } catch (ArithmeticException localArithmeticException) {
/* 1690 */       throw new ArithmeticException("Non-terminating decimal expansion; no exact representable decimal result.");
/*      */     }
/*      */     
/*      */ 
/* 1694 */     int j = localBigDecimal.scale();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1700 */     if (i > j) {
/* 1701 */       return localBigDecimal.setScale(i, 7);
/*      */     }
/* 1703 */     return localBigDecimal;
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
/*      */   public BigDecimal divide(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1721 */     int i = paramMathContext.precision;
/* 1722 */     if (i == 0) {
/* 1723 */       return divide(paramBigDecimal);
/*      */     }
/* 1725 */     BigDecimal localBigDecimal = this;
/* 1726 */     long l = localBigDecimal.scale - paramBigDecimal.scale;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1739 */     if (paramBigDecimal.signum() == 0) {
/* 1740 */       if (localBigDecimal.signum() == 0)
/* 1741 */         throw new ArithmeticException("Division undefined");
/* 1742 */       throw new ArithmeticException("Division by zero");
/*      */     }
/* 1744 */     if (localBigDecimal.signum() == 0)
/* 1745 */       return zeroValueOf(saturateLong(l));
/* 1746 */     int j = localBigDecimal.precision();
/* 1747 */     int k = paramBigDecimal.precision();
/* 1748 */     if (localBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1749 */       if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1750 */         return divide(localBigDecimal.intCompact, j, paramBigDecimal.intCompact, k, l, paramMathContext);
/*      */       }
/* 1752 */       return divide(localBigDecimal.intCompact, j, paramBigDecimal.intVal, k, l, paramMathContext);
/*      */     }
/*      */     
/* 1755 */     if (paramBigDecimal.intCompact != Long.MIN_VALUE) {
/* 1756 */       return divide(localBigDecimal.intVal, j, paramBigDecimal.intCompact, k, l, paramMathContext);
/*      */     }
/* 1758 */     return divide(localBigDecimal.intVal, j, paramBigDecimal.intVal, k, l, paramMathContext);
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
/*      */   public BigDecimal divideToIntegralValue(BigDecimal paramBigDecimal)
/*      */   {
/* 1776 */     int i = saturateLong(this.scale - paramBigDecimal.scale);
/* 1777 */     if (compareMagnitude(paramBigDecimal) < 0)
/*      */     {
/* 1779 */       return zeroValueOf(i);
/*      */     }
/*      */     
/* 1782 */     if ((signum() == 0) && (paramBigDecimal.signum() != 0)) {
/* 1783 */       return setScale(i, 7);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1788 */     int j = (int)Math.min(precision() + 
/* 1789 */       Math.ceil(10.0D * paramBigDecimal.precision() / 3.0D) + 
/* 1790 */       Math.abs(scale() - paramBigDecimal.scale()) + 2L, 2147483647L);
/*      */     
/* 1792 */     BigDecimal localBigDecimal = divide(paramBigDecimal, new MathContext(j, RoundingMode.DOWN));
/*      */     
/* 1794 */     if (localBigDecimal.scale > 0) {
/* 1795 */       localBigDecimal = localBigDecimal.setScale(0, RoundingMode.DOWN);
/* 1796 */       localBigDecimal = stripZerosToMatchScale(localBigDecimal.intVal, localBigDecimal.intCompact, localBigDecimal.scale, i);
/*      */     }
/*      */     
/* 1799 */     if (localBigDecimal.scale < i)
/*      */     {
/* 1801 */       localBigDecimal = localBigDecimal.setScale(i, 7);
/*      */     }
/*      */     
/* 1804 */     return localBigDecimal;
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
/*      */   public BigDecimal divideToIntegralValue(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1828 */     if ((paramMathContext.precision == 0) || 
/* 1829 */       (compareMagnitude(paramBigDecimal) < 0)) {
/* 1830 */       return divideToIntegralValue(paramBigDecimal);
/*      */     }
/*      */     
/* 1833 */     int i = saturateLong(this.scale - paramBigDecimal.scale);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1842 */     BigDecimal localBigDecimal1 = divide(paramBigDecimal, new MathContext(paramMathContext.precision, RoundingMode.DOWN));
/*      */     
/* 1844 */     if (localBigDecimal1.scale() < 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1850 */       BigDecimal localBigDecimal2 = localBigDecimal1.multiply(paramBigDecimal);
/*      */       
/*      */ 
/* 1853 */       if (subtract(localBigDecimal2).compareMagnitude(paramBigDecimal) >= 0) {
/* 1854 */         throw new ArithmeticException("Division impossible");
/*      */       }
/* 1856 */     } else if (localBigDecimal1.scale() > 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1862 */       localBigDecimal1 = localBigDecimal1.setScale(0, RoundingMode.DOWN);
/*      */     }
/*      */     
/*      */     int j;
/*      */     
/* 1867 */     if ((i > localBigDecimal1.scale()) && 
/* 1868 */       ((j = paramMathContext.precision - localBigDecimal1.precision()) > 0)) {
/* 1869 */       return localBigDecimal1.setScale(localBigDecimal1.scale() + 
/* 1870 */         Math.min(j, i - localBigDecimal1.scale));
/*      */     }
/* 1872 */     return stripZerosToMatchScale(localBigDecimal1.intVal, localBigDecimal1.intCompact, localBigDecimal1.scale, i);
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
/*      */   public BigDecimal remainder(BigDecimal paramBigDecimal)
/*      */   {
/* 1890 */     BigDecimal[] arrayOfBigDecimal = divideAndRemainder(paramBigDecimal);
/* 1891 */     return arrayOfBigDecimal[1];
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
/*      */   public BigDecimal remainder(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1920 */     BigDecimal[] arrayOfBigDecimal = divideAndRemainder(paramBigDecimal, paramMathContext);
/* 1921 */     return arrayOfBigDecimal[1];
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
/*      */   public BigDecimal[] divideAndRemainder(BigDecimal paramBigDecimal)
/*      */   {
/* 1946 */     BigDecimal[] arrayOfBigDecimal = new BigDecimal[2];
/*      */     
/* 1948 */     arrayOfBigDecimal[0] = divideToIntegralValue(paramBigDecimal);
/* 1949 */     arrayOfBigDecimal[1] = subtract(arrayOfBigDecimal[0].multiply(paramBigDecimal));
/* 1950 */     return arrayOfBigDecimal;
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
/*      */   public BigDecimal[] divideAndRemainder(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 1980 */     if (paramMathContext.precision == 0) {
/* 1981 */       return divideAndRemainder(paramBigDecimal);
/*      */     }
/* 1983 */     BigDecimal[] arrayOfBigDecimal = new BigDecimal[2];
/* 1984 */     BigDecimal localBigDecimal = this;
/*      */     
/* 1986 */     arrayOfBigDecimal[0] = localBigDecimal.divideToIntegralValue(paramBigDecimal, paramMathContext);
/* 1987 */     arrayOfBigDecimal[1] = localBigDecimal.subtract(arrayOfBigDecimal[0].multiply(paramBigDecimal));
/* 1988 */     return arrayOfBigDecimal;
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
/*      */   public BigDecimal pow(int paramInt)
/*      */   {
/* 2009 */     if ((paramInt < 0) || (paramInt > 999999999)) {
/* 2010 */       throw new ArithmeticException("Invalid operation");
/*      */     }
/*      */     
/* 2013 */     int i = checkScale(this.scale * paramInt);
/* 2014 */     return new BigDecimal(inflated().pow(paramInt), i);
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
/*      */   public BigDecimal pow(int paramInt, MathContext paramMathContext)
/*      */   {
/* 2069 */     if (paramMathContext.precision == 0)
/* 2070 */       return pow(paramInt);
/* 2071 */     if ((paramInt < -999999999) || (paramInt > 999999999))
/* 2072 */       throw new ArithmeticException("Invalid operation");
/* 2073 */     if (paramInt == 0)
/* 2074 */       return ONE;
/* 2075 */     BigDecimal localBigDecimal1 = this;
/* 2076 */     MathContext localMathContext = paramMathContext;
/* 2077 */     int i = Math.abs(paramInt);
/* 2078 */     if (paramMathContext.precision > 0) {
/* 2079 */       int j = longDigitLength(i);
/* 2080 */       if (j > paramMathContext.precision)
/* 2081 */         throw new ArithmeticException("Invalid operation");
/* 2082 */       localMathContext = new MathContext(paramMathContext.precision + j + 1, paramMathContext.roundingMode);
/*      */     }
/*      */     
/*      */ 
/* 2086 */     BigDecimal localBigDecimal2 = ONE;
/* 2087 */     int k = 0;
/* 2088 */     for (int m = 1;; m++) {
/* 2089 */       i += i;
/* 2090 */       if (i < 0) {
/* 2091 */         k = 1;
/* 2092 */         localBigDecimal2 = localBigDecimal2.multiply(localBigDecimal1, localMathContext);
/*      */       }
/* 2094 */       if (m == 31)
/*      */         break;
/* 2096 */       if (k != 0) {
/* 2097 */         localBigDecimal2 = localBigDecimal2.multiply(localBigDecimal2, localMathContext);
/*      */       }
/*      */     }
/*      */     
/* 2101 */     if (paramInt < 0) {
/* 2102 */       localBigDecimal2 = ONE.divide(localBigDecimal2, localMathContext);
/*      */     }
/* 2104 */     return doRound(localBigDecimal2, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal abs()
/*      */   {
/* 2115 */     return signum() < 0 ? negate() : this;
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
/*      */   public BigDecimal abs(MathContext paramMathContext)
/*      */   {
/* 2130 */     return signum() < 0 ? negate(paramMathContext) : plus(paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal negate()
/*      */   {
/* 2140 */     if (this.intCompact == Long.MIN_VALUE) {
/* 2141 */       return new BigDecimal(this.intVal.negate(), Long.MIN_VALUE, this.scale, this.precision);
/*      */     }
/* 2143 */     return valueOf(-this.intCompact, this.scale, this.precision);
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
/*      */   public BigDecimal negate(MathContext paramMathContext)
/*      */   {
/* 2158 */     return negate().plus(paramMathContext);
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
/*      */   public BigDecimal plus()
/*      */   {
/* 2174 */     return this;
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
/*      */   public BigDecimal plus(MathContext paramMathContext)
/*      */   {
/* 2193 */     if (paramMathContext.precision == 0)
/* 2194 */       return this;
/* 2195 */     return doRound(this, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int signum()
/*      */   {
/* 2207 */     return this.intCompact != Long.MIN_VALUE ? Long.signum(this.intCompact) : this.intVal.signum();
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
/*      */   public int scale()
/*      */   {
/* 2221 */     return this.scale;
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
/*      */   public int precision()
/*      */   {
/* 2234 */     int i = this.precision;
/* 2235 */     if (i == 0) {
/* 2236 */       long l = this.intCompact;
/* 2237 */       if (l != Long.MIN_VALUE) {
/* 2238 */         i = longDigitLength(l);
/*      */       } else
/* 2240 */         i = bigDigitLength(this.intVal);
/* 2241 */       this.precision = i;
/*      */     }
/* 2243 */     return i;
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
/*      */   public BigInteger unscaledValue()
/*      */   {
/* 2256 */     return inflated();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal round(MathContext paramMathContext)
/*      */   {
/* 2353 */     return plus(paramMathContext);
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
/*      */   public BigDecimal setScale(int paramInt, RoundingMode paramRoundingMode)
/*      */   {
/* 2386 */     return setScale(paramInt, paramRoundingMode.oldMode);
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
/*      */   public BigDecimal setScale(int paramInt1, int paramInt2)
/*      */   {
/* 2430 */     if ((paramInt2 < 0) || (paramInt2 > 7)) {
/* 2431 */       throw new IllegalArgumentException("Invalid rounding mode");
/*      */     }
/* 2433 */     int i = this.scale;
/* 2434 */     if (paramInt1 == i)
/* 2435 */       return this;
/* 2436 */     if (signum() == 0)
/* 2437 */       return zeroValueOf(paramInt1);
/* 2438 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2439 */       long l = this.intCompact;
/* 2440 */       if (paramInt1 > i) {
/* 2441 */         k = checkScale(paramInt1 - i);
/* 2442 */         if ((l = longMultiplyPowerTen(l, k)) != Long.MIN_VALUE) {
/* 2443 */           return valueOf(l, paramInt1);
/*      */         }
/* 2445 */         BigInteger localBigInteger2 = bigMultiplyPowerTen(k);
/* 2446 */         return new BigDecimal(localBigInteger2, Long.MIN_VALUE, paramInt1, this.precision > 0 ? this.precision + k : 0);
/*      */       }
/*      */       
/*      */ 
/* 2450 */       int k = checkScale(i - paramInt1);
/* 2451 */       if (k < LONG_TEN_POWERS_TABLE.length) {
/* 2452 */         return divideAndRound(l, LONG_TEN_POWERS_TABLE[k], paramInt1, paramInt2, paramInt1);
/*      */       }
/* 2454 */       return divideAndRound(inflated(), bigTenToThe(k), paramInt1, paramInt2, paramInt1);
/*      */     }
/*      */     
/*      */ 
/* 2458 */     if (paramInt1 > i) {
/* 2459 */       j = checkScale(paramInt1 - i);
/* 2460 */       BigInteger localBigInteger1 = bigMultiplyPowerTen(this.intVal, j);
/* 2461 */       return new BigDecimal(localBigInteger1, Long.MIN_VALUE, paramInt1, this.precision > 0 ? this.precision + j : 0);
/*      */     }
/*      */     
/*      */ 
/* 2465 */     int j = checkScale(i - paramInt1);
/* 2466 */     if (j < LONG_TEN_POWERS_TABLE.length) {
/* 2467 */       return divideAndRound(this.intVal, LONG_TEN_POWERS_TABLE[j], paramInt1, paramInt2, paramInt1);
/*      */     }
/*      */     
/* 2470 */     return divideAndRound(this.intVal, bigTenToThe(j), paramInt1, paramInt2, paramInt1);
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
/*      */   public BigDecimal setScale(int paramInt)
/*      */   {
/* 2512 */     return setScale(paramInt, 7);
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
/*      */   public BigDecimal movePointLeft(int paramInt)
/*      */   {
/* 2534 */     int i = checkScale(this.scale + paramInt);
/* 2535 */     BigDecimal localBigDecimal = new BigDecimal(this.intVal, this.intCompact, i, 0);
/* 2536 */     return localBigDecimal.scale < 0 ? localBigDecimal.setScale(0, 7) : localBigDecimal;
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
/*      */   public BigDecimal movePointRight(int paramInt)
/*      */   {
/* 2556 */     int i = checkScale(this.scale - paramInt);
/* 2557 */     BigDecimal localBigDecimal = new BigDecimal(this.intVal, this.intCompact, i, 0);
/* 2558 */     return localBigDecimal.scale < 0 ? localBigDecimal.setScale(0, 7) : localBigDecimal;
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
/*      */   public BigDecimal scaleByPowerOfTen(int paramInt)
/*      */   {
/* 2576 */     return new BigDecimal(this.intVal, this.intCompact, checkScale(this.scale - paramInt), this.precision);
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
/*      */   public BigDecimal stripTrailingZeros()
/*      */   {
/* 2595 */     if ((this.intCompact == 0L) || ((this.intVal != null) && (this.intVal.signum() == 0)))
/* 2596 */       return ZERO;
/* 2597 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2598 */       return createAndStripZerosToMatchScale(this.intCompact, this.scale, Long.MIN_VALUE);
/*      */     }
/* 2600 */     return createAndStripZerosToMatchScale(this.intVal, this.scale, Long.MIN_VALUE);
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
/*      */   public int compareTo(BigDecimal paramBigDecimal)
/*      */   {
/* 2625 */     if (this.scale == paramBigDecimal.scale) {
/* 2626 */       long l1 = this.intCompact;
/* 2627 */       long l2 = paramBigDecimal.intCompact;
/* 2628 */       if ((l1 != Long.MIN_VALUE) && (l2 != Long.MIN_VALUE))
/* 2629 */         return l1 != l2 ? -1 : l1 > l2 ? 1 : 0;
/*      */     }
/* 2631 */     int i = signum();
/* 2632 */     int j = paramBigDecimal.signum();
/* 2633 */     if (i != j)
/* 2634 */       return i > j ? 1 : -1;
/* 2635 */     if (i == 0)
/* 2636 */       return 0;
/* 2637 */     int k = compareMagnitude(paramBigDecimal);
/* 2638 */     return i > 0 ? k : -k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int compareMagnitude(BigDecimal paramBigDecimal)
/*      */   {
/* 2646 */     long l1 = paramBigDecimal.intCompact;
/* 2647 */     long l2 = this.intCompact;
/* 2648 */     if (l2 == 0L)
/* 2649 */       return l1 == 0L ? 0 : -1;
/* 2650 */     if (l1 == 0L) {
/* 2651 */       return 1;
/*      */     }
/* 2653 */     long l3 = this.scale - paramBigDecimal.scale;
/* 2654 */     if (l3 != 0L)
/*      */     {
/* 2656 */       long l4 = precision() - this.scale;
/* 2657 */       long l5 = paramBigDecimal.precision() - paramBigDecimal.scale;
/* 2658 */       if (l4 < l5)
/* 2659 */         return -1;
/* 2660 */       if (l4 > l5)
/* 2661 */         return 1;
/* 2662 */       BigInteger localBigInteger = null;
/* 2663 */       if (l3 < 0L)
/*      */       {
/* 2665 */         if ((l3 > -2147483648L) && ((l2 == Long.MIN_VALUE) || 
/*      */         
/* 2667 */           ((l2 = longMultiplyPowerTen(l2, (int)-l3)) == Long.MIN_VALUE)) && (l1 == Long.MIN_VALUE))
/*      */         {
/* 2669 */           localBigInteger = bigMultiplyPowerTen((int)-l3);
/* 2670 */           return localBigInteger.compareMagnitude(paramBigDecimal.intVal);
/*      */         }
/*      */         
/*      */       }
/* 2674 */       else if ((l3 <= 2147483647L) && ((l1 == Long.MIN_VALUE) || 
/*      */       
/* 2676 */         ((l1 = longMultiplyPowerTen(l1, (int)l3)) == Long.MIN_VALUE)) && (l2 == Long.MIN_VALUE))
/*      */       {
/* 2678 */         localBigInteger = paramBigDecimal.bigMultiplyPowerTen((int)l3);
/* 2679 */         return this.intVal.compareMagnitude(localBigInteger);
/*      */       }
/*      */     }
/*      */     
/* 2683 */     if (l2 != Long.MIN_VALUE)
/* 2684 */       return l1 != Long.MIN_VALUE ? longCompareMagnitude(l2, l1) : -1;
/* 2685 */     if (l1 != Long.MIN_VALUE) {
/* 2686 */       return 1;
/*      */     }
/* 2688 */     return this.intVal.compareMagnitude(paramBigDecimal.intVal);
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 2709 */     if (!(paramObject instanceof BigDecimal))
/* 2710 */       return false;
/* 2711 */     BigDecimal localBigDecimal = (BigDecimal)paramObject;
/* 2712 */     if (paramObject == this)
/* 2713 */       return true;
/* 2714 */     if (this.scale != localBigDecimal.scale)
/* 2715 */       return false;
/* 2716 */     long l1 = this.intCompact;
/* 2717 */     long l2 = localBigDecimal.intCompact;
/* 2718 */     if (l1 != Long.MIN_VALUE) {
/* 2719 */       if (l2 == Long.MIN_VALUE)
/* 2720 */         l2 = compactValFor(localBigDecimal.intVal);
/* 2721 */       return l2 == l1; }
/* 2722 */     if (l2 != Long.MIN_VALUE) {
/* 2723 */       return l2 == compactValFor(this.intVal);
/*      */     }
/* 2725 */     return inflated().equals(localBigDecimal.inflated());
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
/*      */   public BigDecimal min(BigDecimal paramBigDecimal)
/*      */   {
/* 2740 */     return compareTo(paramBigDecimal) <= 0 ? this : paramBigDecimal;
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
/*      */   public BigDecimal max(BigDecimal paramBigDecimal)
/*      */   {
/* 2754 */     return compareTo(paramBigDecimal) >= 0 ? this : paramBigDecimal;
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
/*      */   public int hashCode()
/*      */   {
/* 2770 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2771 */       long l = this.intCompact < 0L ? -this.intCompact : this.intCompact;
/* 2772 */       int i = (int)((int)(l >>> 32) * 31 + (l & 0xFFFFFFFF));
/*      */       
/* 2774 */       return 31 * (this.intCompact < 0L ? -i : i) + this.scale;
/*      */     }
/* 2776 */     return 31 * this.intVal.hashCode() + this.scale;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2882 */     String str = this.stringCache;
/* 2883 */     if (str == null)
/* 2884 */       this.stringCache = (str = layoutChars(true));
/* 2885 */     return str;
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
/*      */   public String toEngineeringString()
/*      */   {
/* 2913 */     return layoutChars(false);
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
/*      */   public String toPlainString()
/*      */   {
/* 2950 */     if (this.scale == 0) {
/* 2951 */       if (this.intCompact != Long.MIN_VALUE) {
/* 2952 */         return Long.toString(this.intCompact);
/*      */       }
/* 2954 */       return this.intVal.toString();
/*      */     }
/*      */     
/* 2957 */     if (this.scale < 0) {
/* 2958 */       if (signum() == 0) {
/* 2959 */         return "0";
/*      */       }
/* 2961 */       int i = checkScaleNonZero(-this.scale);
/*      */       StringBuilder localStringBuilder;
/* 2963 */       if (this.intCompact != Long.MIN_VALUE) {
/* 2964 */         localStringBuilder = new StringBuilder(20 + i);
/* 2965 */         localStringBuilder.append(this.intCompact);
/*      */       } else {
/* 2967 */         String str2 = this.intVal.toString();
/* 2968 */         localStringBuilder = new StringBuilder(str2.length() + i);
/* 2969 */         localStringBuilder.append(str2);
/*      */       }
/* 2971 */       for (int j = 0; j < i; j++)
/* 2972 */         localStringBuilder.append('0');
/* 2973 */       return localStringBuilder.toString();
/*      */     }
/*      */     String str1;
/* 2976 */     if (this.intCompact != Long.MIN_VALUE) {
/* 2977 */       str1 = Long.toString(Math.abs(this.intCompact));
/*      */     } else {
/* 2979 */       str1 = this.intVal.abs().toString();
/*      */     }
/* 2981 */     return getValueString(signum(), str1, this.scale);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getValueString(int paramInt1, String paramString, int paramInt2)
/*      */   {
/* 2988 */     int i = paramString.length() - paramInt2;
/* 2989 */     if (i == 0)
/* 2990 */       return (paramInt1 < 0 ? "-0." : "0.") + paramString;
/* 2991 */     StringBuilder localStringBuilder; if (i > 0) {
/* 2992 */       localStringBuilder = new StringBuilder(paramString);
/* 2993 */       localStringBuilder.insert(i, '.');
/* 2994 */       if (paramInt1 < 0)
/* 2995 */         localStringBuilder.insert(0, '-');
/*      */     } else {
/* 2997 */       localStringBuilder = new StringBuilder(3 - i + paramString.length());
/* 2998 */       localStringBuilder.append(paramInt1 < 0 ? "-0." : "0.");
/* 2999 */       for (int j = 0; j < -i; j++)
/* 3000 */         localStringBuilder.append('0');
/* 3001 */       localStringBuilder.append(paramString);
/*      */     }
/* 3003 */     return localStringBuilder.toString();
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
/*      */   public BigInteger toBigInteger()
/*      */   {
/* 3025 */     return setScale(0, 1).inflated();
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
/*      */   public BigInteger toBigIntegerExact()
/*      */   {
/* 3040 */     return setScale(0, 7).inflated();
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
/*      */   public long longValue()
/*      */   {
/* 3062 */     return (this.intCompact != Long.MIN_VALUE) && (this.scale == 0) ? this.intCompact : toBigInteger().longValue();
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
/* 3078 */     if ((this.intCompact != Long.MIN_VALUE) && (this.scale == 0)) {
/* 3079 */       return this.intCompact;
/*      */     }
/* 3081 */     if (precision() - this.scale > 19) {
/* 3082 */       throw new ArithmeticException("Overflow");
/*      */     }
/*      */     
/* 3085 */     if (signum() == 0)
/* 3086 */       return 0L;
/* 3087 */     if (precision() - this.scale <= 0) {
/* 3088 */       throw new ArithmeticException("Rounding necessary");
/*      */     }
/* 3090 */     BigDecimal localBigDecimal = setScale(0, 7);
/* 3091 */     if (localBigDecimal.precision() >= 19)
/* 3092 */       LongOverflow.check(localBigDecimal);
/* 3093 */     return localBigDecimal.inflated().longValue();
/*      */   }
/*      */   
/*      */   private static class LongOverflow
/*      */   {
/* 3098 */     private static final BigInteger LONGMIN = BigInteger.valueOf(Long.MIN_VALUE);
/*      */     
/*      */ 
/* 3101 */     private static final BigInteger LONGMAX = BigInteger.valueOf(Long.MAX_VALUE);
/*      */     
/*      */     public static void check(BigDecimal paramBigDecimal) {
/* 3104 */       BigInteger localBigInteger = paramBigDecimal.inflated();
/* 3105 */       if ((localBigInteger.compareTo(LONGMIN) < 0) || 
/* 3106 */         (localBigInteger.compareTo(LONGMAX) > 0)) {
/* 3107 */         throw new ArithmeticException("Overflow");
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
/*      */   public int intValue()
/*      */   {
/* 3130 */     return (this.intCompact != Long.MIN_VALUE) && (this.scale == 0) ? (int)this.intCompact : toBigInteger().intValue();
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
/*      */   public int intValueExact()
/*      */   {
/* 3147 */     long l = longValueExact();
/* 3148 */     if ((int)l != l)
/* 3149 */       throw new ArithmeticException("Overflow");
/* 3150 */     return (int)l;
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
/*      */   public short shortValueExact()
/*      */   {
/* 3167 */     long l = longValueExact();
/* 3168 */     if ((short)(int)l != l)
/* 3169 */       throw new ArithmeticException("Overflow");
/* 3170 */     return (short)(int)l;
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
/*      */   public byte byteValueExact()
/*      */   {
/* 3187 */     long l = longValueExact();
/* 3188 */     if ((byte)(int)l != l)
/* 3189 */       throw new ArithmeticException("Overflow");
/* 3190 */     return (byte)(int)l;
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
/*      */   public float floatValue()
/*      */   {
/* 3210 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3211 */       if (this.scale == 0) {
/* 3212 */         return (float)this.intCompact;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3220 */       if (Math.abs(this.intCompact) < 4194304L)
/*      */       {
/*      */ 
/*      */ 
/* 3224 */         if ((this.scale > 0) && (this.scale < float10pow.length))
/* 3225 */           return (float)this.intCompact / float10pow[this.scale];
/* 3226 */         if ((this.scale < 0) && (this.scale > -float10pow.length)) {
/* 3227 */           return (float)this.intCompact * float10pow[(-this.scale)];
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3233 */     return Float.parseFloat(toString());
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
/*      */   public double doubleValue()
/*      */   {
/* 3253 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3254 */       if (this.scale == 0) {
/* 3255 */         return this.intCompact;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3263 */       if (Math.abs(this.intCompact) < 4503599627370496L)
/*      */       {
/*      */ 
/*      */ 
/* 3267 */         if ((this.scale > 0) && (this.scale < double10pow.length))
/* 3268 */           return this.intCompact / double10pow[this.scale];
/* 3269 */         if ((this.scale < 0) && (this.scale > -double10pow.length)) {
/* 3270 */           return this.intCompact * double10pow[(-this.scale)];
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3276 */     return Double.parseDouble(toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3283 */   private static final double[] double10pow = { 1.0D, 10.0D, 100.0D, 1000.0D, 10000.0D, 100000.0D, 1000000.0D, 1.0E7D, 1.0E8D, 1.0E9D, 1.0E10D, 1.0E11D, 1.0E12D, 1.0E13D, 1.0E14D, 1.0E15D, 1.0E16D, 1.0E17D, 1.0E18D, 1.0E19D, 1.0E20D, 1.0E21D, 1.0E22D };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3294 */   private static final float[] float10pow = { 1.0F, 10.0F, 100.0F, 1000.0F, 10000.0F, 100000.0F, 1000000.0F, 1.0E7F, 1.0E8F, 1.0E9F, 1.0E10F };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigDecimal ulp()
/*      */   {
/* 3314 */     return valueOf(1L, scale(), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static class StringBuilderHelper
/*      */   {
/*      */     final StringBuilder sb;
/*      */     
/*      */ 
/*      */     final char[] cmpCharArray;
/*      */     
/*      */ 
/*      */     StringBuilderHelper()
/*      */     {
/* 3329 */       this.sb = new StringBuilder();
/*      */       
/* 3331 */       this.cmpCharArray = new char[19];
/*      */     }
/*      */     
/*      */     StringBuilder getStringBuilder()
/*      */     {
/* 3336 */       this.sb.setLength(0);
/* 3337 */       return this.sb;
/*      */     }
/*      */     
/*      */     char[] getCompactCharArray() {
/* 3341 */       return this.cmpCharArray;
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
/*      */     int putIntCompact(long paramLong)
/*      */     {
/* 3354 */       assert (paramLong >= 0L);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3360 */       int j = this.cmpCharArray.length;
/*      */       
/*      */       int i;
/* 3363 */       while (paramLong > 2147483647L) {
/* 3364 */         long l = paramLong / 100L;
/* 3365 */         i = (int)(paramLong - l * 100L);
/* 3366 */         paramLong = l;
/* 3367 */         this.cmpCharArray[(--j)] = DIGIT_ONES[i];
/* 3368 */         this.cmpCharArray[(--j)] = DIGIT_TENS[i];
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3373 */       int m = (int)paramLong;
/* 3374 */       while (m >= 100) {
/* 3375 */         int k = m / 100;
/* 3376 */         i = m - k * 100;
/* 3377 */         m = k;
/* 3378 */         this.cmpCharArray[(--j)] = DIGIT_ONES[i];
/* 3379 */         this.cmpCharArray[(--j)] = DIGIT_TENS[i];
/*      */       }
/*      */       
/* 3382 */       this.cmpCharArray[(--j)] = DIGIT_ONES[m];
/* 3383 */       if (m >= 10) {
/* 3384 */         this.cmpCharArray[(--j)] = DIGIT_TENS[m];
/*      */       }
/* 3386 */       return j;
/*      */     }
/*      */     
/* 3389 */     static final char[] DIGIT_TENS = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9' };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3402 */     static final char[] DIGIT_ONES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
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
/*      */   private String layoutChars(boolean paramBoolean)
/*      */   {
/* 3426 */     if (this.scale == 0)
/*      */     {
/*      */ 
/* 3429 */       return this.intCompact != Long.MIN_VALUE ? Long.toString(this.intCompact) : this.intVal.toString(); }
/* 3430 */     if ((this.scale == 2) && (this.intCompact >= 0L) && (this.intCompact < 2147483647L))
/*      */     {
/*      */ 
/* 3433 */       int i = (int)this.intCompact % 100;
/* 3434 */       int j = (int)this.intCompact / 100;
/* 3435 */       return Integer.toString(j) + '.' + StringBuilderHelper.DIGIT_TENS[i] + StringBuilderHelper.DIGIT_ONES[i];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3440 */     StringBuilderHelper localStringBuilderHelper = (StringBuilderHelper)threadLocalStringBuilderHelper.get();
/*      */     
/*      */     int k;
/*      */     char[] arrayOfChar;
/* 3444 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3445 */       k = localStringBuilderHelper.putIntCompact(Math.abs(this.intCompact));
/* 3446 */       arrayOfChar = localStringBuilderHelper.getCompactCharArray();
/*      */     } else {
/* 3448 */       k = 0;
/* 3449 */       arrayOfChar = this.intVal.abs().toString().toCharArray();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3456 */     StringBuilder localStringBuilder = localStringBuilderHelper.getStringBuilder();
/* 3457 */     if (signum() < 0)
/* 3458 */       localStringBuilder.append('-');
/* 3459 */     int m = arrayOfChar.length - k;
/* 3460 */     long l = -this.scale + (m - 1);
/* 3461 */     int n; if ((this.scale >= 0) && (l >= -6L)) {
/* 3462 */       n = this.scale - m;
/* 3463 */       if (n >= 0) {
/* 3464 */         localStringBuilder.append('0');
/* 3465 */         localStringBuilder.append('.');
/* 3466 */         for (; n > 0; n--) {
/* 3467 */           localStringBuilder.append('0');
/*      */         }
/* 3469 */         localStringBuilder.append(arrayOfChar, k, m);
/*      */       } else {
/* 3471 */         localStringBuilder.append(arrayOfChar, k, -n);
/* 3472 */         localStringBuilder.append('.');
/* 3473 */         localStringBuilder.append(arrayOfChar, -n + k, this.scale);
/*      */       }
/*      */     } else {
/* 3476 */       if (paramBoolean) {
/* 3477 */         localStringBuilder.append(arrayOfChar[k]);
/* 3478 */         if (m > 1) {
/* 3479 */           localStringBuilder.append('.');
/* 3480 */           localStringBuilder.append(arrayOfChar, k + 1, m - 1);
/*      */         }
/*      */       } else {
/* 3483 */         n = (int)(l % 3L);
/* 3484 */         if (n < 0)
/* 3485 */           n += 3;
/* 3486 */         l -= n;
/* 3487 */         n++;
/* 3488 */         if (signum() == 0) {
/* 3489 */           switch (n) {
/*      */           case 1: 
/* 3491 */             localStringBuilder.append('0');
/* 3492 */             break;
/*      */           case 2: 
/* 3494 */             localStringBuilder.append("0.00");
/* 3495 */             l += 3L;
/* 3496 */             break;
/*      */           case 3: 
/* 3498 */             localStringBuilder.append("0.0");
/* 3499 */             l += 3L;
/* 3500 */             break;
/*      */           default: 
/* 3502 */             throw new AssertionError("Unexpected sig value " + n);
/*      */           }
/* 3504 */         } else if (n >= m) {
/* 3505 */           localStringBuilder.append(arrayOfChar, k, m);
/*      */           
/* 3507 */           for (int i1 = n - m; i1 > 0; i1--)
/* 3508 */             localStringBuilder.append('0');
/*      */         } else {
/* 3510 */           localStringBuilder.append(arrayOfChar, k, n);
/* 3511 */           localStringBuilder.append('.');
/* 3512 */           localStringBuilder.append(arrayOfChar, k + n, m - n);
/*      */         }
/*      */       }
/* 3515 */       if (l != 0L) {
/* 3516 */         localStringBuilder.append('E');
/* 3517 */         if (l > 0L)
/* 3518 */           localStringBuilder.append('+');
/* 3519 */         localStringBuilder.append(l);
/*      */       }
/*      */     }
/* 3522 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger bigTenToThe(int paramInt)
/*      */   {
/* 3532 */     if (paramInt < 0) {
/* 3533 */       return BigInteger.ZERO;
/*      */     }
/* 3535 */     if (paramInt < BIG_TEN_POWERS_TABLE_MAX) {
/* 3536 */       BigInteger[] arrayOfBigInteger = BIG_TEN_POWERS_TABLE;
/* 3537 */       if (paramInt < arrayOfBigInteger.length) {
/* 3538 */         return arrayOfBigInteger[paramInt];
/*      */       }
/* 3540 */       return expandBigIntegerTenPowers(paramInt);
/*      */     }
/*      */     
/* 3543 */     return BigInteger.TEN.pow(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger expandBigIntegerTenPowers(int paramInt)
/*      */   {
/* 3555 */     synchronized (BigDecimal.class) {
/* 3556 */       BigInteger[] arrayOfBigInteger = BIG_TEN_POWERS_TABLE;
/* 3557 */       int i = arrayOfBigInteger.length;
/*      */       
/*      */ 
/* 3560 */       if (i <= paramInt) {
/* 3561 */         int j = i << 1;
/* 3562 */         while (j <= paramInt)
/* 3563 */           j <<= 1;
/* 3564 */         arrayOfBigInteger = (BigInteger[])Arrays.copyOf(arrayOfBigInteger, j);
/* 3565 */         for (int k = i; k < j; k++) {
/* 3566 */           arrayOfBigInteger[k] = arrayOfBigInteger[(k - 1)].multiply(BigInteger.TEN);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3571 */         BIG_TEN_POWERS_TABLE = arrayOfBigInteger;
/*      */       }
/* 3573 */       return arrayOfBigInteger[paramInt];
/*      */     }
/*      */   }
/*      */   
/* 3577 */   private static final long[] LONG_TEN_POWERS_TABLE = { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3599 */   private static volatile BigInteger[] BIG_TEN_POWERS_TABLE = { BigInteger.ONE, 
/*      */   
/* 3601 */     BigInteger.valueOf(10L), 
/* 3602 */     BigInteger.valueOf(100L), 
/* 3603 */     BigInteger.valueOf(1000L), 
/* 3604 */     BigInteger.valueOf(10000L), 
/* 3605 */     BigInteger.valueOf(100000L), 
/* 3606 */     BigInteger.valueOf(1000000L), 
/* 3607 */     BigInteger.valueOf(10000000L), 
/* 3608 */     BigInteger.valueOf(100000000L), 
/* 3609 */     BigInteger.valueOf(1000000000L), 
/* 3610 */     BigInteger.valueOf(10000000000L), 
/* 3611 */     BigInteger.valueOf(100000000000L), 
/* 3612 */     BigInteger.valueOf(1000000000000L), 
/* 3613 */     BigInteger.valueOf(10000000000000L), 
/* 3614 */     BigInteger.valueOf(100000000000000L), 
/* 3615 */     BigInteger.valueOf(1000000000000000L), 
/* 3616 */     BigInteger.valueOf(10000000000000000L), 
/* 3617 */     BigInteger.valueOf(100000000000000000L), 
/* 3618 */     BigInteger.valueOf(1000000000000000000L) };
/*      */   
/*      */ 
/* 3621 */   private static final int BIG_TEN_POWERS_TABLE_INITLEN = BIG_TEN_POWERS_TABLE.length;
/*      */   
/* 3623 */   private static final int BIG_TEN_POWERS_TABLE_MAX = 16 * BIG_TEN_POWERS_TABLE_INITLEN;
/*      */   
/*      */ 
/* 3626 */   private static final long[] THRESHOLDS_TABLE = { Long.MAX_VALUE, 922337203685477580L, 92233720368547758L, 9223372036854775L, 922337203685477L, 92233720368547L, 9223372036854L, 922337203685L, 92233720368L, 9223372036L, 922337203L, 92233720L, 9223372L, 922337L, 92233L, 9223L, 922L, 92L, 9L };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long DIV_NUM_BASE = 4294967296L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long longMultiplyPowerTen(long paramLong, int paramInt)
/*      */   {
/* 3653 */     if ((paramLong == 0L) || (paramInt <= 0))
/* 3654 */       return paramLong;
/* 3655 */     long[] arrayOfLong1 = LONG_TEN_POWERS_TABLE;
/* 3656 */     long[] arrayOfLong2 = THRESHOLDS_TABLE;
/* 3657 */     if ((paramInt < arrayOfLong1.length) && (paramInt < arrayOfLong2.length)) {
/* 3658 */       long l = arrayOfLong1[paramInt];
/* 3659 */       if (paramLong == 1L)
/* 3660 */         return l;
/* 3661 */       if (Math.abs(paramLong) <= arrayOfLong2[paramInt])
/* 3662 */         return paramLong * l;
/*      */     }
/* 3664 */     return Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger bigMultiplyPowerTen(int paramInt)
/*      */   {
/* 3672 */     if (paramInt <= 0) {
/* 3673 */       return inflated();
/*      */     }
/* 3675 */     if (this.intCompact != Long.MIN_VALUE) {
/* 3676 */       return bigTenToThe(paramInt).multiply(this.intCompact);
/*      */     }
/* 3678 */     return this.intVal.multiply(bigTenToThe(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger inflated()
/*      */   {
/* 3686 */     if (this.intVal == null) {
/* 3687 */       return BigInteger.valueOf(this.intCompact);
/*      */     }
/* 3689 */     return this.intVal;
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
/*      */   private static void matchScale(BigDecimal[] paramArrayOfBigDecimal)
/*      */   {
/* 3706 */     if (paramArrayOfBigDecimal[0].scale == paramArrayOfBigDecimal[1].scale)
/* 3707 */       return;
/* 3708 */     if (paramArrayOfBigDecimal[0].scale < paramArrayOfBigDecimal[1].scale) {
/* 3709 */       paramArrayOfBigDecimal[0] = paramArrayOfBigDecimal[0].setScale(paramArrayOfBigDecimal[1].scale, 7);
/* 3710 */     } else if (paramArrayOfBigDecimal[1].scale < paramArrayOfBigDecimal[0].scale)
/* 3711 */       paramArrayOfBigDecimal[1] = paramArrayOfBigDecimal[1].setScale(paramArrayOfBigDecimal[0].scale, 7);
/*      */   }
/*      */   
/*      */   private static class UnsafeHolder {
/*      */     private static final Unsafe unsafe;
/*      */     private static final long intCompactOffset;
/*      */     private static final long intValOffset;
/*      */     
/*      */     static {
/*      */       try {
/* 3721 */         unsafe = Unsafe.getUnsafe();
/*      */         
/* 3723 */         intCompactOffset = unsafe.objectFieldOffset(BigDecimal.class.getDeclaredField("intCompact"));
/*      */         
/* 3725 */         intValOffset = unsafe.objectFieldOffset(BigDecimal.class.getDeclaredField("intVal"));
/*      */       } catch (Exception localException) {
/* 3727 */         throw new ExceptionInInitializerError(localException);
/*      */       }
/*      */     }
/*      */     
/* 3731 */     static void setIntCompactVolatile(BigDecimal paramBigDecimal, long paramLong) { unsafe.putLongVolatile(paramBigDecimal, intCompactOffset, paramLong); }
/*      */     
/*      */     static void setIntValVolatile(BigDecimal paramBigDecimal, BigInteger paramBigInteger)
/*      */     {
/* 3735 */       unsafe.putObjectVolatile(paramBigDecimal, intValOffset, paramBigInteger);
/*      */     }
/*      */   }
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
/* 3748 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 3750 */     if (this.intVal == null) {
/* 3751 */       String str = "BigDecimal: null intVal in stream";
/* 3752 */       throw new StreamCorruptedException(str);
/*      */     }
/*      */     
/* 3755 */     UnsafeHolder.setIntCompactVolatile(this, compactValFor(this.intVal));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 3766 */     if (this.intVal == null) {
/* 3767 */       UnsafeHolder.setIntValVolatile(this, BigInteger.valueOf(this.intCompact));
/*      */     }
/* 3769 */     paramObjectOutputStream.defaultWriteObject();
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
/*      */   static int longDigitLength(long paramLong)
/*      */   {
/* 3793 */     assert (paramLong != Long.MIN_VALUE);
/* 3794 */     if (paramLong < 0L)
/* 3795 */       paramLong = -paramLong;
/* 3796 */     if (paramLong < 10L)
/* 3797 */       return 1;
/* 3798 */     int i = (64 - Long.numberOfLeadingZeros(paramLong) + 1) * 1233 >>> 12;
/* 3799 */     long[] arrayOfLong = LONG_TEN_POWERS_TABLE;
/*      */     
/* 3801 */     return (i >= arrayOfLong.length) || (paramLong < arrayOfLong[i]) ? i : i + 1;
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
/*      */   private static int bigDigitLength(BigInteger paramBigInteger)
/*      */   {
/* 3817 */     if (paramBigInteger.signum == 0)
/* 3818 */       return 1;
/* 3819 */     int i = (int)((paramBigInteger.bitLength() + 1L) * 646456993L >>> 31);
/* 3820 */     return paramBigInteger.compareMagnitude(bigTenToThe(i)) < 0 ? i : i + 1;
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
/*      */   private int checkScale(long paramLong)
/*      */   {
/* 3835 */     int i = (int)paramLong;
/* 3836 */     if (i != paramLong) {
/* 3837 */       i = paramLong > 2147483647L ? Integer.MAX_VALUE : Integer.MIN_VALUE;
/*      */       BigInteger localBigInteger;
/* 3839 */       if ((this.intCompact != 0L) && (((localBigInteger = this.intVal) == null) || 
/* 3840 */         (localBigInteger.signum() != 0)))
/* 3841 */         throw new ArithmeticException(i > 0 ? "Underflow" : "Overflow");
/*      */     }
/* 3843 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long compactValFor(BigInteger paramBigInteger)
/*      */   {
/* 3852 */     int[] arrayOfInt = paramBigInteger.mag;
/* 3853 */     int i = arrayOfInt.length;
/* 3854 */     if (i == 0)
/* 3855 */       return 0L;
/* 3856 */     int j = arrayOfInt[0];
/* 3857 */     if ((i > 2) || ((i == 2) && (j < 0))) {
/* 3858 */       return Long.MIN_VALUE;
/*      */     }
/* 3860 */     long l = i == 2 ? (arrayOfInt[1] & 0xFFFFFFFF) + (j << 32) : j & 0xFFFFFFFF;
/*      */     
/*      */ 
/* 3863 */     return paramBigInteger.signum < 0 ? -l : l;
/*      */   }
/*      */   
/*      */   private static int longCompareMagnitude(long paramLong1, long paramLong2) {
/* 3867 */     if (paramLong1 < 0L)
/* 3868 */       paramLong1 = -paramLong1;
/* 3869 */     if (paramLong2 < 0L)
/* 3870 */       paramLong2 = -paramLong2;
/* 3871 */     return paramLong1 == paramLong2 ? 0 : paramLong1 < paramLong2 ? -1 : 1;
/*      */   }
/*      */   
/*      */   private static int saturateLong(long paramLong) {
/* 3875 */     int i = (int)paramLong;
/* 3876 */     return paramLong < 0L ? Integer.MIN_VALUE : paramLong == i ? i : Integer.MAX_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void print(String paramString, BigDecimal paramBigDecimal)
/*      */   {
/* 3883 */     System.err.format("%s:\tintCompact %d\tintVal %d\tscale %d\tprecision %d%n", new Object[] { paramString, 
/*      */     
/* 3885 */       Long.valueOf(paramBigDecimal.intCompact), paramBigDecimal.intVal, 
/*      */       
/* 3887 */       Integer.valueOf(paramBigDecimal.scale), 
/* 3888 */       Integer.valueOf(paramBigDecimal.precision) });
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
/*      */   private BigDecimal audit()
/*      */   {
/* 3911 */     if (this.intCompact == Long.MIN_VALUE) {
/* 3912 */       if (this.intVal == null) {
/* 3913 */         print("audit", this);
/* 3914 */         throw new AssertionError("null intVal");
/*      */       }
/*      */       
/* 3917 */       if ((this.precision > 0) && (this.precision != bigDigitLength(this.intVal))) {
/* 3918 */         print("audit", this);
/* 3919 */         throw new AssertionError("precision mismatch");
/*      */       }
/*      */     } else {
/* 3922 */       if (this.intVal != null) {
/* 3923 */         long l = this.intVal.longValue();
/* 3924 */         if (l != this.intCompact) {
/* 3925 */           print("audit", this);
/* 3926 */           throw new AssertionError("Inconsistent state, intCompact=" + this.intCompact + "\t intVal=" + l);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3931 */       if ((this.precision > 0) && (this.precision != longDigitLength(this.intCompact))) {
/* 3932 */         print("audit", this);
/* 3933 */         throw new AssertionError("precision mismatch");
/*      */       }
/*      */     }
/* 3936 */     return this;
/*      */   }
/*      */   
/*      */   private static int checkScaleNonZero(long paramLong)
/*      */   {
/* 3941 */     int i = (int)paramLong;
/* 3942 */     if (i != paramLong) {
/* 3943 */       throw new ArithmeticException(i > 0 ? "Underflow" : "Overflow");
/*      */     }
/* 3945 */     return i;
/*      */   }
/*      */   
/*      */   private static int checkScale(long paramLong1, long paramLong2) {
/* 3949 */     int i = (int)paramLong2;
/* 3950 */     if (i != paramLong2) {
/* 3951 */       i = paramLong2 > 2147483647L ? Integer.MAX_VALUE : Integer.MIN_VALUE;
/* 3952 */       if (paramLong1 != 0L)
/* 3953 */         throw new ArithmeticException(i > 0 ? "Underflow" : "Overflow");
/*      */     }
/* 3955 */     return i;
/*      */   }
/*      */   
/*      */   private static int checkScale(BigInteger paramBigInteger, long paramLong) {
/* 3959 */     int i = (int)paramLong;
/* 3960 */     if (i != paramLong) {
/* 3961 */       i = paramLong > 2147483647L ? Integer.MAX_VALUE : Integer.MIN_VALUE;
/* 3962 */       if (paramBigInteger.signum() != 0)
/* 3963 */         throw new ArithmeticException(i > 0 ? "Underflow" : "Overflow");
/*      */     }
/* 3965 */     return i;
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
/*      */   private static BigDecimal doRound(BigDecimal paramBigDecimal, MathContext paramMathContext)
/*      */   {
/* 3982 */     int i = paramMathContext.precision;
/* 3983 */     int j = 0;
/* 3984 */     if (i > 0) {
/* 3985 */       BigInteger localBigInteger = paramBigDecimal.intVal;
/* 3986 */       long l = paramBigDecimal.intCompact;
/* 3987 */       int k = paramBigDecimal.scale;
/* 3988 */       int m = paramBigDecimal.precision();
/* 3989 */       int n = paramMathContext.roundingMode.oldMode;
/*      */       int i1;
/* 3991 */       if (l == Long.MIN_VALUE) {
/* 3992 */         i1 = m - i;
/* 3993 */         while (i1 > 0) {
/* 3994 */           k = checkScaleNonZero(k - i1);
/* 3995 */           localBigInteger = divideAndRoundByTenPow(localBigInteger, i1, n);
/* 3996 */           j = 1;
/* 3997 */           l = compactValFor(localBigInteger);
/* 3998 */           if (l != Long.MIN_VALUE) {
/* 3999 */             m = longDigitLength(l);
/* 4000 */             break;
/*      */           }
/* 4002 */           m = bigDigitLength(localBigInteger);
/* 4003 */           i1 = m - i;
/*      */         }
/*      */       }
/* 4006 */       if (l != Long.MIN_VALUE) {
/* 4007 */         i1 = m - i;
/* 4008 */         while (i1 > 0) {
/* 4009 */           k = checkScaleNonZero(k - i1);
/* 4010 */           l = divideAndRound(l, LONG_TEN_POWERS_TABLE[i1], paramMathContext.roundingMode.oldMode);
/* 4011 */           j = 1;
/* 4012 */           m = longDigitLength(l);
/* 4013 */           i1 = m - i;
/* 4014 */           localBigInteger = null;
/*      */         }
/*      */       }
/* 4017 */       return j != 0 ? new BigDecimal(localBigInteger, l, k, m) : paramBigDecimal;
/*      */     }
/* 4019 */     return paramBigDecimal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal doRound(long paramLong, int paramInt, MathContext paramMathContext)
/*      */   {
/* 4027 */     int i = paramMathContext.precision;
/* 4028 */     if ((i > 0) && (i < 19)) {
/* 4029 */       int j = longDigitLength(paramLong);
/* 4030 */       int k = j - i;
/* 4031 */       while (k > 0) {
/* 4032 */         paramInt = checkScaleNonZero(paramInt - k);
/* 4033 */         paramLong = divideAndRound(paramLong, LONG_TEN_POWERS_TABLE[k], paramMathContext.roundingMode.oldMode);
/* 4034 */         j = longDigitLength(paramLong);
/* 4035 */         k = j - i;
/*      */       }
/* 4037 */       return valueOf(paramLong, paramInt, j);
/*      */     }
/* 4039 */     return valueOf(paramLong, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal doRound(BigInteger paramBigInteger, int paramInt, MathContext paramMathContext)
/*      */   {
/* 4047 */     int i = paramMathContext.precision;
/* 4048 */     int j = 0;
/* 4049 */     if (i > 0) {
/* 4050 */       long l = compactValFor(paramBigInteger);
/* 4051 */       int k = paramMathContext.roundingMode.oldMode;
/*      */       int m;
/* 4053 */       if (l == Long.MIN_VALUE) {
/* 4054 */         j = bigDigitLength(paramBigInteger);
/* 4055 */         m = j - i;
/* 4056 */         while (m > 0) {
/* 4057 */           paramInt = checkScaleNonZero(paramInt - m);
/* 4058 */           paramBigInteger = divideAndRoundByTenPow(paramBigInteger, m, k);
/* 4059 */           l = compactValFor(paramBigInteger);
/* 4060 */           if (l != Long.MIN_VALUE) {
/*      */             break;
/*      */           }
/* 4063 */           j = bigDigitLength(paramBigInteger);
/* 4064 */           m = j - i;
/*      */         }
/*      */       }
/* 4067 */       if (l != Long.MIN_VALUE) {
/* 4068 */         j = longDigitLength(l);
/* 4069 */         m = j - i;
/* 4070 */         while (m > 0) {
/* 4071 */           paramInt = checkScaleNonZero(paramInt - m);
/* 4072 */           l = divideAndRound(l, LONG_TEN_POWERS_TABLE[m], paramMathContext.roundingMode.oldMode);
/* 4073 */           j = longDigitLength(l);
/* 4074 */           m = j - i;
/*      */         }
/* 4076 */         return valueOf(l, paramInt, j);
/*      */       }
/*      */     }
/* 4079 */     return new BigDecimal(paramBigInteger, Long.MIN_VALUE, paramInt, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static BigInteger divideAndRoundByTenPow(BigInteger paramBigInteger, int paramInt1, int paramInt2)
/*      */   {
/* 4086 */     if (paramInt1 < LONG_TEN_POWERS_TABLE.length) {
/* 4087 */       paramBigInteger = divideAndRound(paramBigInteger, LONG_TEN_POWERS_TABLE[paramInt1], paramInt2);
/*      */     } else
/* 4089 */       paramBigInteger = divideAndRound(paramBigInteger, bigTenToThe(paramInt1), paramInt2);
/* 4090 */     return paramBigInteger;
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
/*      */   private static BigDecimal divideAndRound(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 4106 */     long l1 = paramLong1 / paramLong2;
/* 4107 */     if ((paramInt2 == 1) && (paramInt1 == paramInt3))
/* 4108 */       return valueOf(l1, paramInt1);
/* 4109 */     long l2 = paramLong1 % paramLong2;
/* 4110 */     int i = (paramLong1 < 0L ? 1 : 0) == (paramLong2 < 0L ? 1 : 0) ? 1 : -1;
/* 4111 */     if (l2 != 0L) {
/* 4112 */       boolean bool = needIncrement(paramLong2, paramInt2, i, l1, l2);
/* 4113 */       return valueOf(bool ? l1 + i : l1, paramInt1);
/*      */     }
/* 4115 */     if (paramInt3 != paramInt1) {
/* 4116 */       return createAndStripZerosToMatchScale(l1, paramInt1, paramInt3);
/*      */     }
/* 4118 */     return valueOf(l1, paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long divideAndRound(long paramLong1, long paramLong2, int paramInt)
/*      */   {
/* 4128 */     long l1 = paramLong1 / paramLong2;
/* 4129 */     if (paramInt == 1)
/* 4130 */       return l1;
/* 4131 */     long l2 = paramLong1 % paramLong2;
/* 4132 */     int i = (paramLong1 < 0L ? 1 : 0) == (paramLong2 < 0L ? 1 : 0) ? 1 : -1;
/* 4133 */     if (l2 != 0L) {
/* 4134 */       boolean bool = needIncrement(paramLong2, paramInt, i, l1, l2);
/* 4135 */       return bool ? l1 + i : l1;
/*      */     }
/* 4137 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean commonNeedIncrement(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*      */   {
/* 4146 */     switch (paramInt1) {
/*      */     case 7: 
/* 4148 */       throw new ArithmeticException("Rounding necessary");
/*      */     
/*      */     case 0: 
/* 4151 */       return true;
/*      */     
/*      */     case 1: 
/* 4154 */       return false;
/*      */     
/*      */     case 2: 
/* 4157 */       return paramInt2 > 0;
/*      */     
/*      */     case 3: 
/* 4160 */       return paramInt2 < 0;
/*      */     }
/*      */     
/* 4163 */     assert ((paramInt1 >= 4) && (paramInt1 <= 6)) : 
/* 4164 */       ("Unexpected rounding mode" + RoundingMode.valueOf(paramInt1));
/*      */     
/* 4166 */     if (paramInt3 < 0)
/* 4167 */       return false;
/* 4168 */     if (paramInt3 > 0) {
/* 4169 */       return true;
/*      */     }
/* 4171 */     assert (paramInt3 == 0);
/*      */     
/* 4173 */     switch (paramInt1) {
/*      */     case 5: 
/* 4175 */       return false;
/*      */     
/*      */     case 4: 
/* 4178 */       return true;
/*      */     
/*      */     case 6: 
/* 4181 */       return paramBoolean;
/*      */     }
/*      */     
/* 4184 */     throw new AssertionError("Unexpected rounding mode" + paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean needIncrement(long paramLong1, int paramInt1, int paramInt2, long paramLong2, long paramLong3)
/*      */   {
/* 4195 */     assert (paramLong3 != 0L);
/*      */     
/*      */     int i;
/* 4198 */     if ((paramLong3 <= -4611686018427387904L) || (paramLong3 > 4611686018427387903L)) {
/* 4199 */       i = 1;
/*      */     } else {
/* 4201 */       i = longCompareMagnitude(2L * paramLong3, paramLong1);
/*      */     }
/*      */     
/* 4204 */     return commonNeedIncrement(paramInt1, paramInt2, i, (paramLong2 & 1L) != 0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger divideAndRound(BigInteger paramBigInteger, long paramLong, int paramInt)
/*      */   {
/* 4214 */     long l = 0L;
/* 4215 */     MutableBigInteger localMutableBigInteger1 = null;
/*      */     
/* 4217 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramBigInteger.mag);
/* 4218 */     localMutableBigInteger1 = new MutableBigInteger();
/* 4219 */     l = localMutableBigInteger2.divide(paramLong, localMutableBigInteger1);
/* 4220 */     int i = l == 0L ? 1 : 0;
/* 4221 */     int j = paramLong < 0L ? -paramBigInteger.signum : paramBigInteger.signum;
/* 4222 */     if ((i == 0) && 
/* 4223 */       (needIncrement(paramLong, paramInt, j, localMutableBigInteger1, l))) {
/* 4224 */       localMutableBigInteger1.add(MutableBigInteger.ONE);
/*      */     }
/*      */     
/* 4227 */     return localMutableBigInteger1.toBigInteger(j);
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
/*      */   private static BigDecimal divideAndRound(BigInteger paramBigInteger, long paramLong, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 4243 */     long l1 = 0L;
/* 4244 */     MutableBigInteger localMutableBigInteger1 = null;
/*      */     
/* 4246 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramBigInteger.mag);
/* 4247 */     localMutableBigInteger1 = new MutableBigInteger();
/* 4248 */     l1 = localMutableBigInteger2.divide(paramLong, localMutableBigInteger1);
/* 4249 */     int i = l1 == 0L ? 1 : 0;
/* 4250 */     int j = paramLong < 0L ? -paramBigInteger.signum : paramBigInteger.signum;
/* 4251 */     if (i == 0) {
/* 4252 */       if (needIncrement(paramLong, paramInt2, j, localMutableBigInteger1, l1)) {
/* 4253 */         localMutableBigInteger1.add(MutableBigInteger.ONE);
/*      */       }
/* 4255 */       return localMutableBigInteger1.toBigDecimal(j, paramInt1);
/*      */     }
/* 4257 */     if (paramInt3 != paramInt1) {
/* 4258 */       long l2 = localMutableBigInteger1.toCompactValue(j);
/* 4259 */       if (l2 != Long.MIN_VALUE) {
/* 4260 */         return createAndStripZerosToMatchScale(l2, paramInt1, paramInt3);
/*      */       }
/* 4262 */       BigInteger localBigInteger = localMutableBigInteger1.toBigInteger(j);
/* 4263 */       return createAndStripZerosToMatchScale(localBigInteger, paramInt1, paramInt3);
/*      */     }
/* 4265 */     return localMutableBigInteger1.toBigDecimal(j, paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean needIncrement(long paramLong1, int paramInt1, int paramInt2, MutableBigInteger paramMutableBigInteger, long paramLong2)
/*      */   {
/* 4275 */     assert (paramLong2 != 0L);
/*      */     
/*      */     int i;
/* 4278 */     if ((paramLong2 <= -4611686018427387904L) || (paramLong2 > 4611686018427387903L)) {
/* 4279 */       i = 1;
/*      */     } else {
/* 4281 */       i = longCompareMagnitude(2L * paramLong2, paramLong1);
/*      */     }
/*      */     
/* 4284 */     return commonNeedIncrement(paramInt1, paramInt2, i, paramMutableBigInteger.isOdd());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger divideAndRound(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt)
/*      */   {
/* 4295 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(paramBigInteger1.mag);
/* 4296 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger();
/* 4297 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger2.mag);
/* 4298 */     MutableBigInteger localMutableBigInteger4 = localMutableBigInteger1.divide(localMutableBigInteger3, localMutableBigInteger2);
/* 4299 */     boolean bool = localMutableBigInteger4.isZero();
/* 4300 */     int i = paramBigInteger1.signum != paramBigInteger2.signum ? -1 : 1;
/* 4301 */     if ((!bool) && 
/* 4302 */       (needIncrement(localMutableBigInteger3, paramInt, i, localMutableBigInteger2, localMutableBigInteger4))) {
/* 4303 */       localMutableBigInteger2.add(MutableBigInteger.ONE);
/*      */     }
/*      */     
/* 4306 */     return localMutableBigInteger2.toBigInteger(i);
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
/*      */   private static BigDecimal divideAndRound(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 4323 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(paramBigInteger1.mag);
/* 4324 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger();
/* 4325 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger2.mag);
/* 4326 */     MutableBigInteger localMutableBigInteger4 = localMutableBigInteger1.divide(localMutableBigInteger3, localMutableBigInteger2);
/* 4327 */     boolean bool = localMutableBigInteger4.isZero();
/* 4328 */     int i = paramBigInteger1.signum != paramBigInteger2.signum ? -1 : 1;
/* 4329 */     if (!bool) {
/* 4330 */       if (needIncrement(localMutableBigInteger3, paramInt2, i, localMutableBigInteger2, localMutableBigInteger4)) {
/* 4331 */         localMutableBigInteger2.add(MutableBigInteger.ONE);
/*      */       }
/* 4333 */       return localMutableBigInteger2.toBigDecimal(i, paramInt1);
/*      */     }
/* 4335 */     if (paramInt3 != paramInt1) {
/* 4336 */       long l = localMutableBigInteger2.toCompactValue(i);
/* 4337 */       if (l != Long.MIN_VALUE) {
/* 4338 */         return createAndStripZerosToMatchScale(l, paramInt1, paramInt3);
/*      */       }
/* 4340 */       BigInteger localBigInteger = localMutableBigInteger2.toBigInteger(i);
/* 4341 */       return createAndStripZerosToMatchScale(localBigInteger, paramInt1, paramInt3);
/*      */     }
/* 4343 */     return localMutableBigInteger2.toBigDecimal(i, paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean needIncrement(MutableBigInteger paramMutableBigInteger1, int paramInt1, int paramInt2, MutableBigInteger paramMutableBigInteger2, MutableBigInteger paramMutableBigInteger3)
/*      */   {
/* 4353 */     assert (!paramMutableBigInteger3.isZero());
/* 4354 */     int i = paramMutableBigInteger3.compareHalf(paramMutableBigInteger1);
/* 4355 */     return commonNeedIncrement(paramInt1, paramInt2, i, paramMutableBigInteger2.isOdd());
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
/*      */   private static BigDecimal createAndStripZerosToMatchScale(BigInteger paramBigInteger, int paramInt, long paramLong)
/*      */   {
/* 4369 */     while ((paramBigInteger.compareMagnitude(BigInteger.TEN) >= 0) && (paramInt > paramLong))
/*      */     {
/* 4371 */       if (paramBigInteger.testBit(0))
/*      */         break;
/* 4373 */       BigInteger[] arrayOfBigInteger = paramBigInteger.divideAndRemainder(BigInteger.TEN);
/* 4374 */       if (arrayOfBigInteger[1].signum() != 0)
/*      */         break;
/* 4376 */       paramBigInteger = arrayOfBigInteger[0];
/* 4377 */       paramInt = checkScale(paramBigInteger, paramInt - 1L);
/*      */     }
/* 4379 */     return valueOf(paramBigInteger, paramInt, 0);
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
/*      */   private static BigDecimal createAndStripZerosToMatchScale(long paramLong1, int paramInt, long paramLong2)
/*      */   {
/* 4392 */     while ((Math.abs(paramLong1) >= 10L) && (paramInt > paramLong2) && 
/* 4393 */       ((paramLong1 & 1L) == 0L))
/*      */     {
/* 4395 */       long l = paramLong1 % 10L;
/* 4396 */       if (l != 0L)
/*      */         break;
/* 4398 */       paramLong1 /= 10L;
/* 4399 */       paramInt = checkScale(paramLong1, paramInt - 1L);
/*      */     }
/* 4401 */     return valueOf(paramLong1, paramInt);
/*      */   }
/*      */   
/*      */   private static BigDecimal stripZerosToMatchScale(BigInteger paramBigInteger, long paramLong, int paramInt1, int paramInt2) {
/* 4405 */     if (paramLong != Long.MIN_VALUE) {
/* 4406 */       return createAndStripZerosToMatchScale(paramLong, paramInt1, paramInt2);
/*      */     }
/* 4408 */     return createAndStripZerosToMatchScale(paramBigInteger == null ? INFLATED_BIGINT : paramBigInteger, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long add(long paramLong1, long paramLong2)
/*      */   {
/* 4417 */     long l = paramLong1 + paramLong2;
/*      */     
/*      */ 
/* 4420 */     if (((l ^ paramLong1) & (l ^ paramLong2)) >= 0L) {
/* 4421 */       return l;
/*      */     }
/* 4423 */     return Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private static BigDecimal add(long paramLong1, long paramLong2, int paramInt) {
/* 4427 */     long l = add(paramLong1, paramLong2);
/* 4428 */     if (l != Long.MIN_VALUE)
/* 4429 */       return valueOf(l, paramInt);
/* 4430 */     return new BigDecimal(BigInteger.valueOf(paramLong1).add(paramLong2), paramInt);
/*      */   }
/*      */   
/*      */   private static BigDecimal add(long paramLong1, int paramInt1, long paramLong2, int paramInt2) {
/* 4434 */     long l1 = paramInt1 - paramInt2;
/* 4435 */     if (l1 == 0L)
/* 4436 */       return add(paramLong1, paramLong2, paramInt1);
/* 4437 */     if (l1 < 0L) {
/* 4438 */       i = checkScale(paramLong1, -l1);
/* 4439 */       l2 = longMultiplyPowerTen(paramLong1, i);
/* 4440 */       if (l2 != Long.MIN_VALUE) {
/* 4441 */         return add(l2, paramLong2, paramInt2);
/*      */       }
/* 4443 */       localBigInteger = bigMultiplyPowerTen(paramLong1, i).add(paramLong2);
/*      */       
/*      */ 
/* 4446 */       return (paramLong1 ^ paramLong2) >= 0L ? new BigDecimal(localBigInteger, Long.MIN_VALUE, paramInt2, 0) : valueOf(localBigInteger, paramInt2, 0);
/*      */     }
/*      */     
/* 4449 */     int i = checkScale(paramLong2, l1);
/* 4450 */     long l2 = longMultiplyPowerTen(paramLong2, i);
/* 4451 */     if (l2 != Long.MIN_VALUE) {
/* 4452 */       return add(paramLong1, l2, paramInt1);
/*      */     }
/* 4454 */     BigInteger localBigInteger = bigMultiplyPowerTen(paramLong2, i).add(paramLong1);
/*      */     
/*      */ 
/* 4457 */     return (paramLong1 ^ paramLong2) >= 0L ? new BigDecimal(localBigInteger, Long.MIN_VALUE, paramInt1, 0) : valueOf(localBigInteger, paramInt1, 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private static BigDecimal add(long paramLong, int paramInt1, BigInteger paramBigInteger, int paramInt2)
/*      */   {
/* 4463 */     int i = paramInt1;
/* 4464 */     long l1 = i - paramInt2;
/* 4465 */     int j = Long.signum(paramLong) == paramBigInteger.signum ? 1 : 0;
/*      */     int k;
/* 4467 */     BigInteger localBigInteger; if (l1 < 0L) {
/* 4468 */       k = checkScale(paramLong, -l1);
/* 4469 */       i = paramInt2;
/* 4470 */       long l2 = longMultiplyPowerTen(paramLong, k);
/* 4471 */       if (l2 == Long.MIN_VALUE) {
/* 4472 */         localBigInteger = paramBigInteger.add(bigMultiplyPowerTen(paramLong, k));
/*      */       } else {
/* 4474 */         localBigInteger = paramBigInteger.add(l2);
/*      */       }
/*      */     } else {
/* 4477 */       k = checkScale(paramBigInteger, l1);
/* 4478 */       paramBigInteger = bigMultiplyPowerTen(paramBigInteger, k);
/* 4479 */       localBigInteger = paramBigInteger.add(paramLong);
/*      */     }
/*      */     
/*      */ 
/* 4483 */     return j != 0 ? new BigDecimal(localBigInteger, Long.MIN_VALUE, i, 0) : valueOf(localBigInteger, i, 0);
/*      */   }
/*      */   
/*      */   private static BigDecimal add(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2) {
/* 4487 */     int i = paramInt1;
/* 4488 */     long l = i - paramInt2;
/* 4489 */     if (l != 0L) { int j;
/* 4490 */       if (l < 0L) {
/* 4491 */         j = checkScale(paramBigInteger1, -l);
/* 4492 */         i = paramInt2;
/* 4493 */         paramBigInteger1 = bigMultiplyPowerTen(paramBigInteger1, j);
/*      */       } else {
/* 4495 */         j = checkScale(paramBigInteger2, l);
/* 4496 */         paramBigInteger2 = bigMultiplyPowerTen(paramBigInteger2, j);
/*      */       }
/*      */     }
/* 4499 */     BigInteger localBigInteger = paramBigInteger1.add(paramBigInteger2);
/*      */     
/*      */ 
/* 4502 */     return paramBigInteger1.signum == paramBigInteger2.signum ? new BigDecimal(localBigInteger, Long.MIN_VALUE, i, 0) : valueOf(localBigInteger, i, 0);
/*      */   }
/*      */   
/*      */   private static BigInteger bigMultiplyPowerTen(long paramLong, int paramInt) {
/* 4506 */     if (paramInt <= 0)
/* 4507 */       return BigInteger.valueOf(paramLong);
/* 4508 */     return bigTenToThe(paramInt).multiply(paramLong);
/*      */   }
/*      */   
/*      */   private static BigInteger bigMultiplyPowerTen(BigInteger paramBigInteger, int paramInt) {
/* 4512 */     if (paramInt <= 0)
/* 4513 */       return paramBigInteger;
/* 4514 */     if (paramInt < LONG_TEN_POWERS_TABLE.length) {
/* 4515 */       return paramBigInteger.multiply(LONG_TEN_POWERS_TABLE[paramInt]);
/*      */     }
/* 4517 */     return paramBigInteger.multiply(bigTenToThe(paramInt));
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
/*      */   private static BigDecimal divideSmallFastPath(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, MathContext paramMathContext)
/*      */   {
/* 4530 */     int i = paramMathContext.precision;
/* 4531 */     int j = paramMathContext.roundingMode.oldMode;
/*      */     
/* 4533 */     assert ((paramInt1 <= paramInt2) && (paramInt2 < 18) && (i < 18));
/* 4534 */     int k = paramInt2 - paramInt1;
/*      */     
/* 4536 */     long l1 = k == 0 ? paramLong1 : longMultiplyPowerTen(paramLong1, k);
/*      */     
/*      */ 
/* 4539 */     int m = longCompareMagnitude(l1, paramLong2);
/* 4540 */     int n; BigDecimal localBigDecimal; if (m > 0) {
/* 4541 */       paramInt2--;
/* 4542 */       n = checkScaleNonZero(paramLong3 + paramInt2 - paramInt1 + i);
/* 4543 */       int i1; if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0)
/*      */       {
/* 4545 */         i1 = checkScaleNonZero(i + paramInt2 - paramInt1);
/*      */         long l3;
/* 4547 */         if ((l3 = longMultiplyPowerTen(paramLong1, i1)) == Long.MIN_VALUE) {
/* 4548 */           localBigDecimal = null;
/* 4549 */           if ((i - 1 >= 0) && (i - 1 < LONG_TEN_POWERS_TABLE.length)) {
/* 4550 */             localBigDecimal = multiplyDivideAndRound(LONG_TEN_POWERS_TABLE[(i - 1)], l1, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */           }
/* 4552 */           if (localBigDecimal == null) {
/* 4553 */             BigInteger localBigInteger2 = bigMultiplyPowerTen(l1, i - 1);
/* 4554 */             localBigDecimal = divideAndRound(localBigInteger2, paramLong2, n, j, 
/* 4555 */               checkScaleNonZero(paramLong3));
/*      */           }
/*      */         } else {
/* 4558 */           localBigDecimal = divideAndRound(l3, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */         }
/*      */       } else {
/* 4561 */         i1 = checkScaleNonZero(paramInt1 - i);
/*      */         
/* 4563 */         if (i1 == paramInt2) {
/* 4564 */           localBigDecimal = divideAndRound(paramLong1, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */         } else {
/* 4566 */           int i2 = checkScaleNonZero(i1 - paramInt2);
/*      */           long l4;
/* 4568 */           if ((l4 = longMultiplyPowerTen(paramLong2, i2)) == Long.MIN_VALUE) {
/* 4569 */             BigInteger localBigInteger3 = bigMultiplyPowerTen(paramLong2, i2);
/* 4570 */             localBigDecimal = divideAndRound(BigInteger.valueOf(paramLong1), localBigInteger3, n, j, 
/* 4571 */               checkScaleNonZero(paramLong3));
/*      */           } else {
/* 4573 */             localBigDecimal = divideAndRound(paramLong1, l4, n, j, checkScaleNonZero(paramLong3));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 4580 */       n = checkScaleNonZero(paramLong3 + paramInt2 - paramInt1 + i);
/* 4581 */       if (m == 0)
/*      */       {
/* 4583 */         localBigDecimal = roundedTenPower((l1 < 0L ? 1 : 0) == (paramLong2 < 0L ? 1 : 0) ? 1 : -1, i, n, checkScaleNonZero(paramLong3));
/*      */       }
/*      */       else {
/*      */         long l2;
/* 4587 */         if ((l2 = longMultiplyPowerTen(l1, i)) == Long.MIN_VALUE) {
/* 4588 */           localBigDecimal = null;
/* 4589 */           if (i < LONG_TEN_POWERS_TABLE.length) {
/* 4590 */             localBigDecimal = multiplyDivideAndRound(LONG_TEN_POWERS_TABLE[i], l1, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */           }
/* 4592 */           if (localBigDecimal == null) {
/* 4593 */             BigInteger localBigInteger1 = bigMultiplyPowerTen(l1, i);
/* 4594 */             localBigDecimal = divideAndRound(localBigInteger1, paramLong2, n, j, 
/* 4595 */               checkScaleNonZero(paramLong3));
/*      */           }
/*      */         } else {
/* 4598 */           localBigDecimal = divideAndRound(l2, paramLong2, n, j, checkScaleNonZero(paramLong3));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4603 */     return doRound(localBigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal divide(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, MathContext paramMathContext)
/*      */   {
/* 4611 */     int i = paramMathContext.precision;
/* 4612 */     if ((paramInt1 <= paramInt2) && (paramInt2 < 18) && (i < 18)) {
/* 4613 */       return divideSmallFastPath(paramLong1, paramInt1, paramLong2, paramInt2, paramLong3, paramMathContext);
/*      */     }
/* 4615 */     if (compareMagnitudeNormalized(paramLong1, paramInt1, paramLong2, paramInt2) > 0) {
/* 4616 */       paramInt2--;
/*      */     }
/* 4618 */     int j = paramMathContext.roundingMode.oldMode;
/*      */     
/*      */ 
/*      */ 
/* 4622 */     int k = checkScaleNonZero(paramLong3 + paramInt2 - paramInt1 + i);
/*      */     int m;
/* 4624 */     BigDecimal localBigDecimal; if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4625 */       m = checkScaleNonZero(i + paramInt2 - paramInt1);
/*      */       long l1;
/* 4627 */       if ((l1 = longMultiplyPowerTen(paramLong1, m)) == Long.MIN_VALUE) {
/* 4628 */         BigInteger localBigInteger1 = bigMultiplyPowerTen(paramLong1, m);
/* 4629 */         localBigDecimal = divideAndRound(localBigInteger1, paramLong2, k, j, checkScaleNonZero(paramLong3));
/*      */       } else {
/* 4631 */         localBigDecimal = divideAndRound(l1, paramLong2, k, j, checkScaleNonZero(paramLong3));
/*      */       }
/*      */     } else {
/* 4634 */       m = checkScaleNonZero(paramInt1 - i);
/*      */       
/* 4636 */       if (m == paramInt2) {
/* 4637 */         localBigDecimal = divideAndRound(paramLong1, paramLong2, k, j, checkScaleNonZero(paramLong3));
/*      */       } else {
/* 4639 */         int n = checkScaleNonZero(m - paramInt2);
/*      */         long l2;
/* 4641 */         if ((l2 = longMultiplyPowerTen(paramLong2, n)) == Long.MIN_VALUE) {
/* 4642 */           BigInteger localBigInteger2 = bigMultiplyPowerTen(paramLong2, n);
/* 4643 */           localBigDecimal = divideAndRound(BigInteger.valueOf(paramLong1), localBigInteger2, k, j, 
/* 4644 */             checkScaleNonZero(paramLong3));
/*      */         } else {
/* 4646 */           localBigDecimal = divideAndRound(paramLong1, l2, k, j, checkScaleNonZero(paramLong3));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4651 */     return doRound(localBigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal divide(BigInteger paramBigInteger, int paramInt1, long paramLong1, int paramInt2, long paramLong2, MathContext paramMathContext)
/*      */   {
/* 4660 */     if (-compareMagnitudeNormalized(paramLong1, paramInt2, paramBigInteger, paramInt1) > 0) {
/* 4661 */       paramInt2--;
/*      */     }
/* 4663 */     int i = paramMathContext.precision;
/* 4664 */     int j = paramMathContext.roundingMode.oldMode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4670 */     int k = checkScaleNonZero(paramLong2 + paramInt2 - paramInt1 + i);
/* 4671 */     int m; BigDecimal localBigDecimal; if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4672 */       m = checkScaleNonZero(i + paramInt2 - paramInt1);
/* 4673 */       BigInteger localBigInteger1 = bigMultiplyPowerTen(paramBigInteger, m);
/* 4674 */       localBigDecimal = divideAndRound(localBigInteger1, paramLong1, k, j, checkScaleNonZero(paramLong2));
/*      */     } else {
/* 4676 */       m = checkScaleNonZero(paramInt1 - i);
/*      */       
/* 4678 */       if (m == paramInt2) {
/* 4679 */         localBigDecimal = divideAndRound(paramBigInteger, paramLong1, k, j, checkScaleNonZero(paramLong2));
/*      */       } else {
/* 4681 */         int n = checkScaleNonZero(m - paramInt2);
/*      */         long l;
/* 4683 */         if ((l = longMultiplyPowerTen(paramLong1, n)) == Long.MIN_VALUE) {
/* 4684 */           BigInteger localBigInteger2 = bigMultiplyPowerTen(paramLong1, n);
/* 4685 */           localBigDecimal = divideAndRound(paramBigInteger, localBigInteger2, k, j, checkScaleNonZero(paramLong2));
/*      */         } else {
/* 4687 */           localBigDecimal = divideAndRound(paramBigInteger, l, k, j, checkScaleNonZero(paramLong2));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4692 */     return doRound(localBigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal divide(long paramLong1, int paramInt1, BigInteger paramBigInteger, int paramInt2, long paramLong2, MathContext paramMathContext)
/*      */   {
/* 4701 */     if (compareMagnitudeNormalized(paramLong1, paramInt1, paramBigInteger, paramInt2) > 0) {
/* 4702 */       paramInt2--;
/*      */     }
/* 4704 */     int i = paramMathContext.precision;
/* 4705 */     int j = paramMathContext.roundingMode.oldMode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4711 */     int k = checkScaleNonZero(paramLong2 + paramInt2 - paramInt1 + i);
/* 4712 */     int m; BigDecimal localBigDecimal; if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4713 */       m = checkScaleNonZero(i + paramInt2 - paramInt1);
/* 4714 */       BigInteger localBigInteger1 = bigMultiplyPowerTen(paramLong1, m);
/* 4715 */       localBigDecimal = divideAndRound(localBigInteger1, paramBigInteger, k, j, checkScaleNonZero(paramLong2));
/*      */     } else {
/* 4717 */       m = checkScaleNonZero(paramInt1 - i);
/* 4718 */       int n = checkScaleNonZero(m - paramInt2);
/* 4719 */       BigInteger localBigInteger2 = bigMultiplyPowerTen(paramBigInteger, n);
/* 4720 */       localBigDecimal = divideAndRound(BigInteger.valueOf(paramLong1), localBigInteger2, k, j, checkScaleNonZero(paramLong2));
/*      */     }
/*      */     
/* 4723 */     return doRound(localBigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal divide(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2, long paramLong, MathContext paramMathContext)
/*      */   {
/* 4732 */     if (compareMagnitudeNormalized(paramBigInteger1, paramInt1, paramBigInteger2, paramInt2) > 0) {
/* 4733 */       paramInt2--;
/*      */     }
/* 4735 */     int i = paramMathContext.precision;
/* 4736 */     int j = paramMathContext.roundingMode.oldMode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4742 */     int k = checkScaleNonZero(paramLong + paramInt2 - paramInt1 + i);
/* 4743 */     int m; BigDecimal localBigDecimal; if (checkScaleNonZero(i + paramInt2 - paramInt1) > 0) {
/* 4744 */       m = checkScaleNonZero(i + paramInt2 - paramInt1);
/* 4745 */       BigInteger localBigInteger1 = bigMultiplyPowerTen(paramBigInteger1, m);
/* 4746 */       localBigDecimal = divideAndRound(localBigInteger1, paramBigInteger2, k, j, checkScaleNonZero(paramLong));
/*      */     } else {
/* 4748 */       m = checkScaleNonZero(paramInt1 - i);
/* 4749 */       int n = checkScaleNonZero(m - paramInt2);
/* 4750 */       BigInteger localBigInteger2 = bigMultiplyPowerTen(paramBigInteger2, n);
/* 4751 */       localBigDecimal = divideAndRound(paramBigInteger1, localBigInteger2, k, j, checkScaleNonZero(paramLong));
/*      */     }
/*      */     
/* 4754 */     return doRound(localBigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal multiplyDivideAndRound(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 4763 */     int i = Long.signum(paramLong1) * Long.signum(paramLong2) * Long.signum(paramLong3);
/* 4764 */     paramLong1 = Math.abs(paramLong1);
/* 4765 */     paramLong2 = Math.abs(paramLong2);
/* 4766 */     paramLong3 = Math.abs(paramLong3);
/*      */     
/* 4768 */     long l1 = paramLong1 >>> 32;
/* 4769 */     long l2 = paramLong1 & 0xFFFFFFFF;
/* 4770 */     long l3 = paramLong2 >>> 32;
/* 4771 */     long l4 = paramLong2 & 0xFFFFFFFF;
/* 4772 */     long l5 = l2 * l4;
/* 4773 */     long l6 = l5 & 0xFFFFFFFF;
/* 4774 */     long l7 = l5 >>> 32;
/* 4775 */     l5 = l1 * l4 + l7;
/* 4776 */     l7 = l5 & 0xFFFFFFFF;
/* 4777 */     long l8 = l5 >>> 32;
/* 4778 */     l5 = l2 * l3 + l7;
/* 4779 */     l7 = l5 & 0xFFFFFFFF;
/* 4780 */     l8 += (l5 >>> 32);
/* 4781 */     long l9 = l8 >>> 32;
/* 4782 */     l8 &= 0xFFFFFFFF;
/* 4783 */     l5 = l1 * l3 + l8;
/* 4784 */     l8 = l5 & 0xFFFFFFFF;
/* 4785 */     l9 = (l5 >>> 32) + l9 & 0xFFFFFFFF;
/* 4786 */     long l10 = make64(l9, l8);
/* 4787 */     long l11 = make64(l7, l6);
/*      */     
/* 4789 */     return divideAndRound128(l10, l11, paramLong3, i, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal divideAndRound128(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 4801 */     if (paramLong1 >= paramLong3) {
/* 4802 */       return null;
/*      */     }
/* 4804 */     int i = Long.numberOfLeadingZeros(paramLong3);
/* 4805 */     paramLong3 <<= i;
/*      */     
/* 4807 */     long l1 = paramLong3 >>> 32;
/* 4808 */     long l2 = paramLong3 & 0xFFFFFFFF;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4813 */     long l6 = paramLong2 << i;
/* 4814 */     long l7 = l6 >>> 32;
/* 4815 */     long l8 = l6 & 0xFFFFFFFF;
/*      */     
/* 4817 */     l6 = paramLong1 << i | paramLong2 >>> 64 - i;
/* 4818 */     long l9 = l6 & 0xFFFFFFFF;
/* 4819 */     l6 = divWord(l6, l1);
/* 4820 */     long l3 = l6 & 0xFFFFFFFF;
/* 4821 */     long l5 = l6 >>> 32;
/* 4822 */     while ((l3 >= 4294967296L) || (unsignedLongCompare(l3 * l2, make64(l5, l7)))) {
/* 4823 */       l3 -= 1L;
/* 4824 */       l5 += l1;
/* 4825 */       if (l5 >= 4294967296L)
/*      */         break;
/*      */     }
/* 4828 */     l6 = mulsub(l9, l7, l1, l2, l3);
/* 4829 */     l7 = l6 & 0xFFFFFFFF;
/* 4830 */     l6 = divWord(l6, l1);
/* 4831 */     long l4 = l6 & 0xFFFFFFFF;
/* 4832 */     l5 = l6 >>> 32;
/* 4833 */     while ((l4 >= 4294967296L) || (unsignedLongCompare(l4 * l2, make64(l5, l8)))) {
/* 4834 */       l4 -= 1L;
/* 4835 */       l5 += l1;
/* 4836 */       if (l5 >= 4294967296L)
/*      */         break;
/*      */     }
/* 4839 */     if ((int)l3 < 0)
/*      */     {
/*      */ 
/* 4842 */       MutableBigInteger localMutableBigInteger = new MutableBigInteger(new int[] { (int)l3, (int)l4 });
/* 4843 */       if ((paramInt3 == 1) && (paramInt2 == paramInt4)) {
/* 4844 */         return localMutableBigInteger.toBigDecimal(paramInt1, paramInt2);
/*      */       }
/* 4846 */       long l11 = mulsub(l7, l8, l1, l2, l4) >>> i;
/* 4847 */       if (l11 != 0L) {
/* 4848 */         if (needIncrement(paramLong3 >>> i, paramInt3, paramInt1, localMutableBigInteger, l11)) {
/* 4849 */           localMutableBigInteger.add(MutableBigInteger.ONE);
/*      */         }
/* 4851 */         return localMutableBigInteger.toBigDecimal(paramInt1, paramInt2);
/*      */       }
/* 4853 */       if (paramInt4 != paramInt2) {
/* 4854 */         BigInteger localBigInteger = localMutableBigInteger.toBigInteger(paramInt1);
/* 4855 */         return createAndStripZerosToMatchScale(localBigInteger, paramInt2, paramInt4);
/*      */       }
/* 4857 */       return localMutableBigInteger.toBigDecimal(paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */ 
/* 4861 */     long l10 = make64(l3, l4);
/* 4862 */     l10 *= paramInt1;
/* 4863 */     if ((paramInt3 == 1) && (paramInt2 == paramInt4))
/* 4864 */       return valueOf(l10, paramInt2);
/* 4865 */     long l12 = mulsub(l7, l8, l1, l2, l4) >>> i;
/* 4866 */     if (l12 != 0L) {
/* 4867 */       boolean bool = needIncrement(paramLong3 >>> i, paramInt3, paramInt1, l10, l12);
/* 4868 */       return valueOf(bool ? l10 + paramInt1 : l10, paramInt2);
/*      */     }
/* 4870 */     if (paramInt4 != paramInt2) {
/* 4871 */       return createAndStripZerosToMatchScale(l10, paramInt2, paramInt4);
/*      */     }
/* 4873 */     return valueOf(l10, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal roundedTenPower(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 4883 */     if (paramInt3 > paramInt4) {
/* 4884 */       int i = paramInt3 - paramInt4;
/* 4885 */       if (i < paramInt2) {
/* 4886 */         return scaledTenPow(paramInt2 - i, paramInt1, paramInt4);
/*      */       }
/* 4888 */       return valueOf(paramInt1, paramInt3 - paramInt2);
/*      */     }
/*      */     
/* 4891 */     return scaledTenPow(paramInt2, paramInt1, paramInt3);
/*      */   }
/*      */   
/*      */   static BigDecimal scaledTenPow(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 4896 */     if (paramInt1 < LONG_TEN_POWERS_TABLE.length) {
/* 4897 */       return valueOf(paramInt2 * LONG_TEN_POWERS_TABLE[paramInt1], paramInt3);
/*      */     }
/* 4899 */     BigInteger localBigInteger = bigTenToThe(paramInt1);
/* 4900 */     if (paramInt2 == -1) {
/* 4901 */       localBigInteger = localBigInteger.negate();
/*      */     }
/* 4903 */     return new BigDecimal(localBigInteger, Long.MIN_VALUE, paramInt3, paramInt1 + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static long divWord(long paramLong1, long paramLong2)
/*      */   {
/* 4910 */     if (paramLong2 == 1L) {
/* 4911 */       l2 = (int)paramLong1;
/* 4912 */       return l2 & 0xFFFFFFFF;
/*      */     }
/*      */     
/* 4915 */     long l2 = (paramLong1 >>> 1) / (paramLong2 >>> 1);
/* 4916 */     long l1 = paramLong1 - l2 * paramLong2;
/*      */     
/*      */ 
/* 4919 */     while (l1 < 0L) {
/* 4920 */       l1 += paramLong2;
/* 4921 */       l2 -= 1L;
/*      */     }
/* 4923 */     while (l1 >= paramLong2) {
/* 4924 */       l1 -= paramLong2;
/* 4925 */       l2 += 1L;
/*      */     }
/*      */     
/* 4928 */     return l1 << 32 | l2 & 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */   private static long make64(long paramLong1, long paramLong2) {
/* 4932 */     return paramLong1 << 32 | paramLong2;
/*      */   }
/*      */   
/*      */   private static long mulsub(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5) {
/* 4936 */     long l = paramLong2 - paramLong5 * paramLong4;
/* 4937 */     return make64(paramLong1 + (l >>> 32) - paramLong5 * paramLong3, l & 0xFFFFFFFF);
/*      */   }
/*      */   
/*      */   private static boolean unsignedLongCompare(long paramLong1, long paramLong2) {
/* 4941 */     return paramLong1 + Long.MIN_VALUE > paramLong2 + Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private static boolean unsignedLongCompareEq(long paramLong1, long paramLong2) {
/* 4945 */     return paramLong1 + Long.MIN_VALUE >= paramLong2 + Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int compareMagnitudeNormalized(long paramLong1, int paramInt1, long paramLong2, int paramInt2)
/*      */   {
/* 4952 */     int i = paramInt1 - paramInt2;
/* 4953 */     if (i != 0) {
/* 4954 */       if (i < 0) {
/* 4955 */         paramLong1 = longMultiplyPowerTen(paramLong1, -i);
/*      */       } else {
/* 4957 */         paramLong2 = longMultiplyPowerTen(paramLong2, i);
/*      */       }
/*      */     }
/* 4960 */     if (paramLong1 != Long.MIN_VALUE) {
/* 4961 */       return paramLong2 != Long.MIN_VALUE ? longCompareMagnitude(paramLong1, paramLong2) : -1;
/*      */     }
/* 4963 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */   private static int compareMagnitudeNormalized(long paramLong, int paramInt1, BigInteger paramBigInteger, int paramInt2)
/*      */   {
/* 4969 */     if (paramLong == 0L)
/* 4970 */       return -1;
/* 4971 */     int i = paramInt1 - paramInt2;
/* 4972 */     if ((i < 0) && 
/* 4973 */       (longMultiplyPowerTen(paramLong, -i) == Long.MIN_VALUE)) {
/* 4974 */       return bigMultiplyPowerTen(paramLong, -i).compareMagnitude(paramBigInteger);
/*      */     }
/*      */     
/* 4977 */     return -1;
/*      */   }
/*      */   
/*      */   private static int compareMagnitudeNormalized(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2)
/*      */   {
/* 4982 */     int i = paramInt1 - paramInt2;
/* 4983 */     if (i < 0) {
/* 4984 */       return bigMultiplyPowerTen(paramBigInteger1, -i).compareMagnitude(paramBigInteger2);
/*      */     }
/* 4986 */     return paramBigInteger1.compareMagnitude(bigMultiplyPowerTen(paramBigInteger2, i));
/*      */   }
/*      */   
/*      */   private static long multiply(long paramLong1, long paramLong2)
/*      */   {
/* 4991 */     long l1 = paramLong1 * paramLong2;
/* 4992 */     long l2 = Math.abs(paramLong1);
/* 4993 */     long l3 = Math.abs(paramLong2);
/* 4994 */     if (((l2 | l3) >>> 31 == 0L) || (paramLong2 == 0L) || (l1 / paramLong2 == paramLong1)) {
/* 4995 */       return l1;
/*      */     }
/* 4997 */     return Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private static BigDecimal multiply(long paramLong1, long paramLong2, int paramInt) {
/* 5001 */     long l = multiply(paramLong1, paramLong2);
/* 5002 */     if (l != Long.MIN_VALUE) {
/* 5003 */       return valueOf(l, paramInt);
/*      */     }
/* 5005 */     return new BigDecimal(BigInteger.valueOf(paramLong1).multiply(paramLong2), Long.MIN_VALUE, paramInt, 0);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiply(long paramLong, BigInteger paramBigInteger, int paramInt) {
/* 5009 */     if (paramLong == 0L) {
/* 5010 */       return zeroValueOf(paramInt);
/*      */     }
/* 5012 */     return new BigDecimal(paramBigInteger.multiply(paramLong), Long.MIN_VALUE, paramInt, 0);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiply(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt) {
/* 5016 */     return new BigDecimal(paramBigInteger1.multiply(paramBigInteger2), Long.MIN_VALUE, paramInt, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static BigDecimal multiplyAndRound(long paramLong1, long paramLong2, int paramInt, MathContext paramMathContext)
/*      */   {
/* 5023 */     long l1 = multiply(paramLong1, paramLong2);
/* 5024 */     if (l1 != Long.MIN_VALUE) {
/* 5025 */       return doRound(l1, paramInt, paramMathContext);
/*      */     }
/*      */     
/* 5028 */     int i = 1;
/* 5029 */     if (paramLong1 < 0L) {
/* 5030 */       paramLong1 = -paramLong1;
/* 5031 */       i = -1;
/*      */     }
/* 5033 */     if (paramLong2 < 0L) {
/* 5034 */       paramLong2 = -paramLong2;
/* 5035 */       i *= -1;
/*      */     }
/*      */     
/* 5038 */     long l2 = paramLong1 >>> 32;
/* 5039 */     long l3 = paramLong1 & 0xFFFFFFFF;
/* 5040 */     long l4 = paramLong2 >>> 32;
/* 5041 */     long l5 = paramLong2 & 0xFFFFFFFF;
/* 5042 */     l1 = l3 * l5;
/* 5043 */     long l6 = l1 & 0xFFFFFFFF;
/* 5044 */     long l7 = l1 >>> 32;
/* 5045 */     l1 = l2 * l5 + l7;
/* 5046 */     l7 = l1 & 0xFFFFFFFF;
/* 5047 */     long l8 = l1 >>> 32;
/* 5048 */     l1 = l3 * l4 + l7;
/* 5049 */     l7 = l1 & 0xFFFFFFFF;
/* 5050 */     l8 += (l1 >>> 32);
/* 5051 */     long l9 = l8 >>> 32;
/* 5052 */     l8 &= 0xFFFFFFFF;
/* 5053 */     l1 = l2 * l4 + l8;
/* 5054 */     l8 = l1 & 0xFFFFFFFF;
/* 5055 */     l9 = (l1 >>> 32) + l9 & 0xFFFFFFFF;
/* 5056 */     long l10 = make64(l9, l8);
/* 5057 */     long l11 = make64(l7, l6);
/* 5058 */     BigDecimal localBigDecimal = doRound128(l10, l11, i, paramInt, paramMathContext);
/* 5059 */     if (localBigDecimal != null) {
/* 5060 */       return localBigDecimal;
/*      */     }
/* 5062 */     localBigDecimal = new BigDecimal(BigInteger.valueOf(paramLong1).multiply(paramLong2 * i), Long.MIN_VALUE, paramInt, 0);
/* 5063 */     return doRound(localBigDecimal, paramMathContext);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiplyAndRound(long paramLong, BigInteger paramBigInteger, int paramInt, MathContext paramMathContext) {
/* 5067 */     if (paramLong == 0L) {
/* 5068 */       return zeroValueOf(paramInt);
/*      */     }
/* 5070 */     return doRound(paramBigInteger.multiply(paramLong), paramInt, paramMathContext);
/*      */   }
/*      */   
/*      */   private static BigDecimal multiplyAndRound(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt, MathContext paramMathContext) {
/* 5074 */     return doRound(paramBigInteger1.multiply(paramBigInteger2), paramInt, paramMathContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigDecimal doRound128(long paramLong1, long paramLong2, int paramInt1, int paramInt2, MathContext paramMathContext)
/*      */   {
/* 5082 */     int i = paramMathContext.precision;
/*      */     
/* 5084 */     BigDecimal localBigDecimal = null;
/* 5085 */     int j; if (((j = precision(paramLong1, paramLong2) - i) > 0) && (j < LONG_TEN_POWERS_TABLE.length)) {
/* 5086 */       paramInt2 = checkScaleNonZero(paramInt2 - j);
/* 5087 */       localBigDecimal = divideAndRound128(paramLong1, paramLong2, LONG_TEN_POWERS_TABLE[j], paramInt1, paramInt2, paramMathContext.roundingMode.oldMode, paramInt2);
/*      */     }
/* 5089 */     if (localBigDecimal != null) {
/* 5090 */       return doRound(localBigDecimal, paramMathContext);
/*      */     }
/* 5092 */     return null;
/*      */   }
/*      */   
/* 5095 */   private static final long[][] LONGLONG_TEN_POWERS_TABLE = { { 0L, -8446744073709551616L }, { 5L, 7766279631452241920L }, { 54L, 3875820019684212736L }, { 542L, 1864712049423024128L }, { 5421L, 200376420520689664L }, { 54210L, 2003764205206896640L }, { 542101L, 1590897978359414784L }, { 5421010L, -2537764290115403776L }, { 54210108L, -6930898827444486144L }, { 542101086L, 4477988020393345024L }, { 5421010862L, 7886392056514347008L }, { 54210108624L, 5076944270305263616L }, { 542101086242L, -4570789518076018688L }, { 5421010862427L, -8814407033341083648L }, { 54210108624275L, 4089650035136921600L }, { 542101086242752L, 4003012203950112768L }, { 5421010862427522L, 3136633892082024448L }, { 54210108624275221L, -5527149226598858752L }, { 542101086242752217L, 68739955140067328L }, { 5421010862427522170L, 687399551400673280L } };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int precision(long paramLong1, long paramLong2)
/*      */   {
/* 5122 */     if (paramLong1 == 0L) {
/* 5123 */       if (paramLong2 >= 0L) {
/* 5124 */         return longDigitLength(paramLong2);
/*      */       }
/* 5126 */       return unsignedLongCompareEq(paramLong2, LONGLONG_TEN_POWERS_TABLE[0][1]) ? 20 : 19;
/*      */     }
/*      */     
/* 5129 */     int i = (128 - Long.numberOfLeadingZeros(paramLong1) + 1) * 1233 >>> 12;
/* 5130 */     int j = i - 19;
/* 5131 */     return (j >= LONGLONG_TEN_POWERS_TABLE.length) || (longLongCompareMagnitude(paramLong1, paramLong2, LONGLONG_TEN_POWERS_TABLE[j][0], LONGLONG_TEN_POWERS_TABLE[j][1])) ? i : i + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean longLongCompareMagnitude(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*      */   {
/* 5140 */     if (paramLong1 != paramLong3) {
/* 5141 */       return paramLong1 < paramLong3;
/*      */     }
/* 5143 */     return paramLong2 + Long.MIN_VALUE < paramLong4 + Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private static BigDecimal divide(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, int paramInt4) {
/* 5147 */     if (checkScale(paramLong1, paramInt3 + paramInt2) > paramInt1) {
/* 5148 */       i = paramInt3 + paramInt2;
/* 5149 */       j = i - paramInt1;
/* 5150 */       if (j < LONG_TEN_POWERS_TABLE.length) {
/* 5151 */         long l1 = paramLong1;
/* 5152 */         if ((l1 = longMultiplyPowerTen(l1, j)) != Long.MIN_VALUE) {
/* 5153 */           return divideAndRound(l1, paramLong2, paramInt3, paramInt4, paramInt3);
/*      */         }
/* 5155 */         BigDecimal localBigDecimal = multiplyDivideAndRound(LONG_TEN_POWERS_TABLE[j], paramLong1, paramLong2, paramInt3, paramInt4, paramInt3);
/* 5156 */         if (localBigDecimal != null) {
/* 5157 */           return localBigDecimal;
/*      */         }
/*      */       }
/* 5160 */       BigInteger localBigInteger1 = bigMultiplyPowerTen(paramLong1, j);
/* 5161 */       return divideAndRound(localBigInteger1, paramLong2, paramInt3, paramInt4, paramInt3);
/*      */     }
/* 5163 */     int i = checkScale(paramLong2, paramInt1 - paramInt3);
/* 5164 */     int j = i - paramInt2;
/* 5165 */     if (j < LONG_TEN_POWERS_TABLE.length) {
/* 5166 */       long l2 = paramLong2;
/* 5167 */       if ((l2 = longMultiplyPowerTen(l2, j)) != Long.MIN_VALUE) {
/* 5168 */         return divideAndRound(paramLong1, l2, paramInt3, paramInt4, paramInt3);
/*      */       }
/*      */     }
/* 5171 */     BigInteger localBigInteger2 = bigMultiplyPowerTen(paramLong2, j);
/* 5172 */     return divideAndRound(BigInteger.valueOf(paramLong1), localBigInteger2, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */   
/*      */   private static BigDecimal divide(BigInteger paramBigInteger, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 5177 */     if (checkScale(paramBigInteger, paramInt3 + paramInt2) > paramInt1) {
/* 5178 */       i = paramInt3 + paramInt2;
/* 5179 */       j = i - paramInt1;
/* 5180 */       BigInteger localBigInteger1 = bigMultiplyPowerTen(paramBigInteger, j);
/* 5181 */       return divideAndRound(localBigInteger1, paramLong, paramInt3, paramInt4, paramInt3);
/*      */     }
/* 5183 */     int i = checkScale(paramLong, paramInt1 - paramInt3);
/* 5184 */     int j = i - paramInt2;
/* 5185 */     if (j < LONG_TEN_POWERS_TABLE.length) {
/* 5186 */       long l = paramLong;
/* 5187 */       if ((l = longMultiplyPowerTen(l, j)) != Long.MIN_VALUE) {
/* 5188 */         return divideAndRound(paramBigInteger, l, paramInt3, paramInt4, paramInt3);
/*      */       }
/*      */     }
/* 5191 */     BigInteger localBigInteger2 = bigMultiplyPowerTen(paramLong, j);
/* 5192 */     return divideAndRound(paramBigInteger, localBigInteger2, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */   
/*      */   private static BigDecimal divide(long paramLong, int paramInt1, BigInteger paramBigInteger, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 5197 */     if (checkScale(paramLong, paramInt3 + paramInt2) > paramInt1) {
/* 5198 */       i = paramInt3 + paramInt2;
/* 5199 */       j = i - paramInt1;
/* 5200 */       localBigInteger = bigMultiplyPowerTen(paramLong, j);
/* 5201 */       return divideAndRound(localBigInteger, paramBigInteger, paramInt3, paramInt4, paramInt3);
/*      */     }
/* 5203 */     int i = checkScale(paramBigInteger, paramInt1 - paramInt3);
/* 5204 */     int j = i - paramInt2;
/* 5205 */     BigInteger localBigInteger = bigMultiplyPowerTen(paramBigInteger, j);
/* 5206 */     return divideAndRound(BigInteger.valueOf(paramLong), localBigInteger, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */   
/*      */   private static BigDecimal divide(BigInteger paramBigInteger1, int paramInt1, BigInteger paramBigInteger2, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 5211 */     if (checkScale(paramBigInteger1, paramInt3 + paramInt2) > paramInt1) {
/* 5212 */       i = paramInt3 + paramInt2;
/* 5213 */       j = i - paramInt1;
/* 5214 */       localBigInteger = bigMultiplyPowerTen(paramBigInteger1, j);
/* 5215 */       return divideAndRound(localBigInteger, paramBigInteger2, paramInt3, paramInt4, paramInt3);
/*      */     }
/* 5217 */     int i = checkScale(paramBigInteger2, paramInt1 - paramInt3);
/* 5218 */     int j = i - paramInt2;
/* 5219 */     BigInteger localBigInteger = bigMultiplyPowerTen(paramBigInteger2, j);
/* 5220 */     return divideAndRound(paramBigInteger1, localBigInteger, paramInt3, paramInt4, paramInt3);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/math/BigDecimal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */