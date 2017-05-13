/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import java.text.spi.NumberFormatProvider;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Currency;
/*      */ import java.util.Locale;
/*      */ import java.util.Locale.Category;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ import sun.util.locale.provider.ResourceBundleBasedAdapter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DecimalFormat
/*      */   extends NumberFormat
/*      */ {
/*      */   private transient BigInteger bigIntegerMultiplier;
/*      */   private transient BigDecimal bigDecimalMultiplier;
/*      */   private static final int STATUS_INFINITE = 0;
/*      */   private static final int STATUS_POSITIVE = 1;
/*      */   private static final int STATUS_LENGTH = 2;
/*      */   
/*      */   public DecimalFormat()
/*      */   {
/*  401 */     Locale localLocale = Locale.getDefault(Locale.Category.FORMAT);
/*  402 */     LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(NumberFormatProvider.class, localLocale);
/*  403 */     if (!(localLocaleProviderAdapter instanceof ResourceBundleBasedAdapter)) {
/*  404 */       localLocaleProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
/*      */     }
/*  406 */     String[] arrayOfString = localLocaleProviderAdapter.getLocaleResources(localLocale).getNumberPatterns();
/*      */     
/*      */ 
/*  409 */     this.symbols = DecimalFormatSymbols.getInstance(localLocale);
/*  410 */     applyPattern(arrayOfString[0], false);
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
/*      */   public DecimalFormat(String paramString)
/*      */   {
/*  435 */     this.symbols = DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));
/*  436 */     applyPattern(paramString, false);
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
/*      */   public DecimalFormat(String paramString, DecimalFormatSymbols paramDecimalFormatSymbols)
/*      */   {
/*  463 */     this.symbols = ((DecimalFormatSymbols)paramDecimalFormatSymbols.clone());
/*  464 */     applyPattern(paramString, false);
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
/*      */   public final StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  493 */     if ((!(paramObject instanceof Long)) && (!(paramObject instanceof Integer)) && (!(paramObject instanceof Short)) && (!(paramObject instanceof Byte)) && (!(paramObject instanceof AtomicInteger)) && (!(paramObject instanceof AtomicLong))) { if ((paramObject instanceof BigInteger))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  498 */         if (((BigInteger)paramObject).bitLength() >= 64) {} }
/*  499 */     } else return format(((Number)paramObject).longValue(), paramStringBuffer, paramFieldPosition);
/*  500 */     if ((paramObject instanceof BigDecimal))
/*  501 */       return format((BigDecimal)paramObject, paramStringBuffer, paramFieldPosition);
/*  502 */     if ((paramObject instanceof BigInteger))
/*  503 */       return format((BigInteger)paramObject, paramStringBuffer, paramFieldPosition);
/*  504 */     if ((paramObject instanceof Number)) {
/*  505 */       return format(((Number)paramObject).doubleValue(), paramStringBuffer, paramFieldPosition);
/*      */     }
/*  507 */     throw new IllegalArgumentException("Cannot format given Object as a Number");
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
/*      */   public StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  527 */     int i = 0;
/*  528 */     if (paramFieldPosition == DontCareFieldPosition.INSTANCE) {
/*  529 */       i = 1;
/*      */     } else {
/*  531 */       paramFieldPosition.setBeginIndex(0);
/*  532 */       paramFieldPosition.setEndIndex(0);
/*      */     }
/*      */     
/*  535 */     if (i != 0) {
/*  536 */       String str = fastFormat(paramDouble);
/*  537 */       if (str != null) {
/*  538 */         paramStringBuffer.append(str);
/*  539 */         return paramStringBuffer;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  544 */     return format(paramDouble, paramStringBuffer, paramFieldPosition.getFieldDelegate());
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
/*      */   private StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate)
/*      */   {
/*  558 */     if ((Double.isNaN(paramDouble)) || (
/*  559 */       (Double.isInfinite(paramDouble)) && (this.multiplier == 0))) {
/*  560 */       i = paramStringBuffer.length();
/*  561 */       paramStringBuffer.append(this.symbols.getNaN());
/*  562 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, i, paramStringBuffer
/*  563 */         .length(), paramStringBuffer);
/*  564 */       return paramStringBuffer;
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
/*  577 */     int i = ((paramDouble < 0.0D) || ((paramDouble == 0.0D) && (1.0D / paramDouble < 0.0D)) ? 1 : 0) ^ (this.multiplier < 0 ? 1 : 0);
/*      */     
/*  579 */     if (this.multiplier != 1) {
/*  580 */       paramDouble *= this.multiplier;
/*      */     }
/*      */     
/*  583 */     if (Double.isInfinite(paramDouble)) {
/*  584 */       if (i != 0) {
/*  585 */         append(paramStringBuffer, this.negativePrefix, paramFieldDelegate, 
/*  586 */           getNegativePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       } else {
/*  588 */         append(paramStringBuffer, this.positivePrefix, paramFieldDelegate, 
/*  589 */           getPositivePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       }
/*      */       
/*  592 */       int j = paramStringBuffer.length();
/*  593 */       paramStringBuffer.append(this.symbols.getInfinity());
/*  594 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, paramStringBuffer
/*  595 */         .length(), paramStringBuffer);
/*      */       
/*  597 */       if (i != 0) {
/*  598 */         append(paramStringBuffer, this.negativeSuffix, paramFieldDelegate, 
/*  599 */           getNegativeSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       } else {
/*  601 */         append(paramStringBuffer, this.positiveSuffix, paramFieldDelegate, 
/*  602 */           getPositiveSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       }
/*      */       
/*  605 */       return paramStringBuffer;
/*      */     }
/*      */     
/*  608 */     if (i != 0) {
/*  609 */       paramDouble = -paramDouble;
/*      */     }
/*      */     
/*      */ 
/*  613 */     assert ((paramDouble >= 0.0D) && (!Double.isInfinite(paramDouble)));
/*      */     
/*  615 */     synchronized (this.digitList) {
/*  616 */       int k = super.getMaximumIntegerDigits();
/*  617 */       int m = super.getMinimumIntegerDigits();
/*  618 */       int n = super.getMaximumFractionDigits();
/*  619 */       int i1 = super.getMinimumFractionDigits();
/*      */       
/*  621 */       this.digitList.set(i, paramDouble, this.useExponentialNotation ? k + n : n, !this.useExponentialNotation);
/*      */       
/*      */ 
/*  624 */       return subformat(paramStringBuffer, paramFieldDelegate, i, false, k, m, n, i1);
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
/*      */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  643 */     paramFieldPosition.setBeginIndex(0);
/*  644 */     paramFieldPosition.setEndIndex(0);
/*      */     
/*  646 */     return format(paramLong, paramStringBuffer, paramFieldPosition.getFieldDelegate());
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
/*      */   private StringBuffer format(long paramLong, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate)
/*      */   {
/*  661 */     boolean bool = paramLong < 0L;
/*  662 */     if (bool) {
/*  663 */       paramLong = -paramLong;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  672 */     int i = 0;
/*  673 */     if (paramLong < 0L) {
/*  674 */       if (this.multiplier != 0) {
/*  675 */         i = 1;
/*      */       }
/*  677 */     } else if ((this.multiplier != 1) && (this.multiplier != 0)) {
/*  678 */       long l = Long.MAX_VALUE / this.multiplier;
/*  679 */       if (l < 0L) {
/*  680 */         l = -l;
/*      */       }
/*  682 */       i = paramLong > l ? 1 : 0;
/*      */     }
/*      */     
/*  685 */     if (i != 0) {
/*  686 */       if (bool) {
/*  687 */         paramLong = -paramLong;
/*      */       }
/*  689 */       BigInteger localBigInteger = BigInteger.valueOf(paramLong);
/*  690 */       return format(localBigInteger, paramStringBuffer, paramFieldDelegate, true);
/*      */     }
/*      */     
/*  693 */     paramLong *= this.multiplier;
/*  694 */     if (paramLong == 0L) {
/*  695 */       bool = false;
/*      */     }
/*  697 */     else if (this.multiplier < 0) {
/*  698 */       paramLong = -paramLong;
/*  699 */       bool = !bool;
/*      */     }
/*      */     
/*      */ 
/*  703 */     synchronized (this.digitList) {
/*  704 */       int j = super.getMaximumIntegerDigits();
/*  705 */       int k = super.getMinimumIntegerDigits();
/*  706 */       int m = super.getMaximumFractionDigits();
/*  707 */       int n = super.getMinimumFractionDigits();
/*      */       
/*  709 */       this.digitList.set(bool, paramLong, this.useExponentialNotation ? j + m : 0);
/*      */       
/*      */ 
/*  712 */       return subformat(paramStringBuffer, paramFieldDelegate, bool, true, j, k, m, n);
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
/*      */   private StringBuffer format(BigDecimal paramBigDecimal, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  730 */     paramFieldPosition.setBeginIndex(0);
/*  731 */     paramFieldPosition.setEndIndex(0);
/*  732 */     return format(paramBigDecimal, paramStringBuffer, paramFieldPosition.getFieldDelegate());
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
/*      */   private StringBuffer format(BigDecimal paramBigDecimal, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate)
/*      */   {
/*  746 */     if (this.multiplier != 1) {
/*  747 */       paramBigDecimal = paramBigDecimal.multiply(getBigDecimalMultiplier());
/*      */     }
/*  749 */     boolean bool = paramBigDecimal.signum() == -1;
/*  750 */     if (bool) {
/*  751 */       paramBigDecimal = paramBigDecimal.negate();
/*      */     }
/*      */     
/*  754 */     synchronized (this.digitList) {
/*  755 */       int i = getMaximumIntegerDigits();
/*  756 */       int j = getMinimumIntegerDigits();
/*  757 */       int k = getMaximumFractionDigits();
/*  758 */       int m = getMinimumFractionDigits();
/*  759 */       int n = i + k;
/*      */       
/*  761 */       this.digitList.set(bool, paramBigDecimal, this.useExponentialNotation ? n : n < 0 ? Integer.MAX_VALUE : k, !this.useExponentialNotation);
/*      */       
/*      */ 
/*      */ 
/*  765 */       return subformat(paramStringBuffer, paramFieldDelegate, bool, false, i, j, k, m);
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
/*      */   private StringBuffer format(BigInteger paramBigInteger, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  783 */     paramFieldPosition.setBeginIndex(0);
/*  784 */     paramFieldPosition.setEndIndex(0);
/*      */     
/*  786 */     return format(paramBigInteger, paramStringBuffer, paramFieldPosition.getFieldDelegate(), false);
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
/*      */   private StringBuffer format(BigInteger paramBigInteger, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate, boolean paramBoolean)
/*      */   {
/*  801 */     if (this.multiplier != 1) {
/*  802 */       paramBigInteger = paramBigInteger.multiply(getBigIntegerMultiplier());
/*      */     }
/*  804 */     boolean bool = paramBigInteger.signum() == -1;
/*  805 */     if (bool) {
/*  806 */       paramBigInteger = paramBigInteger.negate();
/*      */     }
/*      */     
/*  809 */     synchronized (this.digitList) { int i;
/*      */       int j;
/*  811 */       int k; int m; int n; if (paramBoolean) {
/*  812 */         i = super.getMaximumIntegerDigits();
/*  813 */         j = super.getMinimumIntegerDigits();
/*  814 */         k = super.getMaximumFractionDigits();
/*  815 */         m = super.getMinimumFractionDigits();
/*  816 */         n = i + k;
/*      */       } else {
/*  818 */         i = getMaximumIntegerDigits();
/*  819 */         j = getMinimumIntegerDigits();
/*  820 */         k = getMaximumFractionDigits();
/*  821 */         m = getMinimumFractionDigits();
/*  822 */         n = i + k;
/*  823 */         if (n < 0) {
/*  824 */           n = Integer.MAX_VALUE;
/*      */         }
/*      */       }
/*      */       
/*  828 */       this.digitList.set(bool, paramBigInteger, this.useExponentialNotation ? n : 0);
/*      */       
/*      */ 
/*  831 */       return subformat(paramStringBuffer, paramFieldDelegate, bool, true, i, j, k, m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public AttributedCharacterIterator formatToCharacterIterator(Object paramObject)
/*      */   {
/*  857 */     CharacterIteratorFieldDelegate localCharacterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
/*      */     
/*  859 */     StringBuffer localStringBuffer = new StringBuffer();
/*      */     
/*  861 */     if (((paramObject instanceof Double)) || ((paramObject instanceof Float))) {
/*  862 */       format(((Number)paramObject).doubleValue(), localStringBuffer, localCharacterIteratorFieldDelegate);
/*  863 */     } else if (((paramObject instanceof Long)) || ((paramObject instanceof Integer)) || ((paramObject instanceof Short)) || ((paramObject instanceof Byte)) || ((paramObject instanceof AtomicInteger)) || ((paramObject instanceof AtomicLong)))
/*      */     {
/*      */ 
/*  866 */       format(((Number)paramObject).longValue(), localStringBuffer, localCharacterIteratorFieldDelegate);
/*  867 */     } else if ((paramObject instanceof BigDecimal)) {
/*  868 */       format((BigDecimal)paramObject, localStringBuffer, localCharacterIteratorFieldDelegate);
/*  869 */     } else if ((paramObject instanceof BigInteger)) {
/*  870 */       format((BigInteger)paramObject, localStringBuffer, localCharacterIteratorFieldDelegate, false);
/*  871 */     } else { if (paramObject == null) {
/*  872 */         throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
/*      */       }
/*      */       
/*  875 */       throw new IllegalArgumentException("Cannot format given Object as a Number");
/*      */     }
/*      */     
/*  878 */     return localCharacterIteratorFieldDelegate.getIterator(localStringBuffer.toString());
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
/*      */   private void checkAndSetFastPathStatus()
/*      */   {
/*  973 */     boolean bool = this.isFastPath;
/*      */     
/*  975 */     if ((this.roundingMode == RoundingMode.HALF_EVEN) && 
/*  976 */       (isGroupingUsed()) && (this.groupingSize == 3) && (this.multiplier == 1) && (!this.decimalSeparatorAlwaysShown) && (!this.useExponentialNotation))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  984 */       this.isFastPath = ((this.minimumIntegerDigits == 1) && (this.maximumIntegerDigits >= 10));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  989 */       if (this.isFastPath) {
/*  990 */         if (this.isCurrencyFormat) {
/*  991 */           if ((this.minimumFractionDigits != 2) || (this.maximumFractionDigits != 2))
/*      */           {
/*  993 */             this.isFastPath = false; }
/*  994 */         } else if ((this.minimumFractionDigits != 0) || (this.maximumFractionDigits != 3))
/*      */         {
/*  996 */           this.isFastPath = false; }
/*      */       }
/*      */     } else {
/*  999 */       this.isFastPath = false;
/*      */     }
/*      */     
/*      */ 
/* 1003 */     if (this.isFastPath)
/*      */     {
/* 1005 */       if (this.fastPathData == null) {
/* 1006 */         this.fastPathData = new FastPathData(null);
/*      */       }
/*      */       
/*      */ 
/* 1010 */       this.fastPathData.zeroDelta = (this.symbols.getZeroDigit() - '0');
/* 1011 */       this.fastPathData.groupingChar = this.symbols.getGroupingSeparator();
/*      */       
/*      */ 
/* 1014 */       this.fastPathData.fractionalMaxIntBound = (this.isCurrencyFormat ? 99 : 999);
/* 1015 */       this.fastPathData.fractionalScaleFactor = (this.isCurrencyFormat ? 100.0D : 1000.0D);
/*      */       
/*      */ 
/*      */ 
/* 1019 */       this.fastPathData.positiveAffixesRequired = ((this.positivePrefix.length() != 0) || (this.positiveSuffix.length() != 0));
/*      */       
/* 1021 */       this.fastPathData.negativeAffixesRequired = ((this.negativePrefix.length() != 0) || (this.negativeSuffix.length() != 0));
/*      */       
/*      */ 
/* 1024 */       int i = 10;
/* 1025 */       int j = 3;
/*      */       
/*      */ 
/*      */ 
/* 1029 */       int k = Math.max(this.positivePrefix.length(), this.negativePrefix.length()) + i + j + 1 + this.maximumFractionDigits + Math.max(this.positiveSuffix.length(), this.negativeSuffix.length());
/*      */       
/* 1031 */       this.fastPathData.fastPathContainer = new char[k];
/*      */       
/*      */ 
/* 1034 */       this.fastPathData.charsPositiveSuffix = this.positiveSuffix.toCharArray();
/* 1035 */       this.fastPathData.charsNegativeSuffix = this.negativeSuffix.toCharArray();
/* 1036 */       this.fastPathData.charsPositivePrefix = this.positivePrefix.toCharArray();
/* 1037 */       this.fastPathData.charsNegativePrefix = this.negativePrefix.toCharArray();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1042 */       int m = Math.max(this.positivePrefix.length(), this.negativePrefix.length());
/* 1043 */       int n = i + j + m;
/*      */       
/*      */ 
/* 1046 */       this.fastPathData.integralLastIndex = (n - 1);
/* 1047 */       this.fastPathData.fractionalFirstIndex = (n + 1);
/* 1048 */       this.fastPathData.fastPathContainer[n] = (this.isCurrencyFormat ? this.symbols
/*      */       
/* 1050 */         .getMonetaryDecimalSeparator() : this.symbols
/* 1051 */         .getDecimalSeparator());
/*      */     }
/* 1053 */     else if (bool)
/*      */     {
/*      */ 
/* 1056 */       this.fastPathData.fastPathContainer = null;
/* 1057 */       this.fastPathData.charsPositiveSuffix = null;
/* 1058 */       this.fastPathData.charsNegativeSuffix = null;
/* 1059 */       this.fastPathData.charsPositivePrefix = null;
/* 1060 */       this.fastPathData.charsNegativePrefix = null;
/*      */     }
/*      */     
/* 1063 */     this.fastPathCheckNeeded = false;
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
/*      */   private boolean exactRoundUp(double paramDouble, int paramInt)
/*      */   {
/* 1146 */     double d4 = 0.0D;
/* 1147 */     double d5 = 0.0D;
/* 1148 */     double d6 = 0.0D;
/*      */     double d1;
/* 1150 */     double d2; double d3; if (this.isCurrencyFormat)
/*      */     {
/*      */ 
/* 1153 */       d1 = paramDouble * 128.0D;
/* 1154 */       d2 = -(paramDouble * 32.0D);
/* 1155 */       d3 = paramDouble * 4.0D;
/*      */     }
/*      */     else
/*      */     {
/* 1159 */       d1 = paramDouble * 1024.0D;
/* 1160 */       d2 = -(paramDouble * 16.0D);
/* 1161 */       d3 = -(paramDouble * 8.0D);
/*      */     }
/*      */     
/*      */ 
/* 1165 */     assert (-d2 >= Math.abs(d3));
/* 1166 */     d4 = d2 + d3;
/* 1167 */     d6 = d4 - d2;
/* 1168 */     d5 = d3 - d6;
/* 1169 */     double d7 = d4;
/* 1170 */     double d8 = d5;
/*      */     
/*      */ 
/* 1173 */     assert (d1 >= Math.abs(d7));
/* 1174 */     d4 = d1 + d7;
/* 1175 */     d6 = d4 - d1;
/* 1176 */     d5 = d7 - d6;
/* 1177 */     double d9 = d5;
/* 1178 */     double d10 = d4;
/* 1179 */     double d11 = d8 + d9;
/*      */     
/*      */ 
/* 1182 */     assert (d10 >= Math.abs(d11));
/* 1183 */     d4 = d10 + d11;
/* 1184 */     d6 = d4 - d10;
/*      */     
/*      */ 
/* 1187 */     double d12 = d11 - d6;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1211 */     if (d12 > 0.0D)
/* 1212 */       return true;
/* 1213 */     if (d12 < 0.0D)
/* 1214 */       return false;
/* 1215 */     if ((paramInt & 0x1) != 0) {
/* 1216 */       return true;
/*      */     }
/*      */     
/* 1219 */     return false;
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
/*      */   private void collectIntegralDigits(int paramInt1, char[] paramArrayOfChar, int paramInt2)
/*      */   {
/* 1240 */     int i = paramInt2;
/*      */     
/*      */ 
/* 1243 */     while (paramInt1 > 999)
/*      */     {
/* 1245 */       int j = paramInt1 / 1000;
/* 1246 */       int k = paramInt1 - (j << 10) + (j << 4) + (j << 3);
/* 1247 */       paramInt1 = j;
/*      */       
/* 1249 */       paramArrayOfChar[(i--)] = DigitArrays.DigitOnes1000[k];
/* 1250 */       paramArrayOfChar[(i--)] = DigitArrays.DigitTens1000[k];
/* 1251 */       paramArrayOfChar[(i--)] = DigitArrays.DigitHundreds1000[k];
/* 1252 */       paramArrayOfChar[(i--)] = this.fastPathData.groupingChar;
/*      */     }
/*      */     
/*      */ 
/* 1256 */     paramArrayOfChar[i] = DigitArrays.DigitOnes1000[paramInt1];
/* 1257 */     if (paramInt1 > 9) {
/* 1258 */       paramArrayOfChar[(--i)] = DigitArrays.DigitTens1000[paramInt1];
/* 1259 */       if (paramInt1 > 99) {
/* 1260 */         paramArrayOfChar[(--i)] = DigitArrays.DigitHundreds1000[paramInt1];
/*      */       }
/*      */     }
/* 1263 */     this.fastPathData.firstUsedIndex = i;
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
/*      */   private void collectFractionalDigits(int paramInt1, char[] paramArrayOfChar, int paramInt2)
/*      */   {
/* 1282 */     int i = paramInt2;
/*      */     
/* 1284 */     int j = DigitArrays.DigitOnes1000[paramInt1];
/* 1285 */     int k = DigitArrays.DigitTens1000[paramInt1];
/*      */     
/* 1287 */     if (this.isCurrencyFormat)
/*      */     {
/* 1289 */       paramArrayOfChar[(i++)] = k;
/* 1290 */       paramArrayOfChar[(i++)] = j;
/* 1291 */     } else if (paramInt1 != 0)
/*      */     {
/* 1293 */       paramArrayOfChar[(i++)] = DigitArrays.DigitHundreds1000[paramInt1];
/*      */       
/*      */ 
/* 1296 */       if (j != 48) {
/* 1297 */         paramArrayOfChar[(i++)] = k;
/* 1298 */         paramArrayOfChar[(i++)] = j;
/* 1299 */       } else if (k != 48) {
/* 1300 */         paramArrayOfChar[(i++)] = k;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1305 */       i--;
/*      */     }
/* 1307 */     this.fastPathData.lastFreeIndex = i;
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
/*      */   private void addAffixes(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3)
/*      */   {
/* 1324 */     int i = paramArrayOfChar2.length;
/* 1325 */     int j = paramArrayOfChar3.length;
/* 1326 */     if (i != 0) prependPrefix(paramArrayOfChar2, i, paramArrayOfChar1);
/* 1327 */     if (j != 0) { appendSuffix(paramArrayOfChar3, j, paramArrayOfChar1);
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
/*      */   private void prependPrefix(char[] paramArrayOfChar1, int paramInt, char[] paramArrayOfChar2)
/*      */   {
/* 1344 */     this.fastPathData.firstUsedIndex -= paramInt;
/* 1345 */     int i = this.fastPathData.firstUsedIndex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */     if (paramInt == 1) {
/* 1352 */       paramArrayOfChar2[i] = paramArrayOfChar1[0];
/* 1353 */     } else if (paramInt <= 4) {
/* 1354 */       int j = i;
/* 1355 */       int k = j + paramInt - 1;
/* 1356 */       int m = paramInt - 1;
/* 1357 */       paramArrayOfChar2[j] = paramArrayOfChar1[0];
/* 1358 */       paramArrayOfChar2[k] = paramArrayOfChar1[m];
/*      */       
/* 1360 */       if (paramInt > 2)
/* 1361 */         paramArrayOfChar2[(++j)] = paramArrayOfChar1[1];
/* 1362 */       if (paramInt == 4)
/* 1363 */         paramArrayOfChar2[(--k)] = paramArrayOfChar1[2];
/*      */     } else {
/* 1365 */       System.arraycopy(paramArrayOfChar1, 0, paramArrayOfChar2, i, paramInt);
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
/*      */   private void appendSuffix(char[] paramArrayOfChar1, int paramInt, char[] paramArrayOfChar2)
/*      */   {
/* 1381 */     int i = this.fastPathData.lastFreeIndex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1387 */     if (paramInt == 1) {
/* 1388 */       paramArrayOfChar2[i] = paramArrayOfChar1[0];
/* 1389 */     } else if (paramInt <= 4) {
/* 1390 */       int j = i;
/* 1391 */       int k = j + paramInt - 1;
/* 1392 */       int m = paramInt - 1;
/* 1393 */       paramArrayOfChar2[j] = paramArrayOfChar1[0];
/* 1394 */       paramArrayOfChar2[k] = paramArrayOfChar1[m];
/*      */       
/* 1396 */       if (paramInt > 2)
/* 1397 */         paramArrayOfChar2[(++j)] = paramArrayOfChar1[1];
/* 1398 */       if (paramInt == 4)
/* 1399 */         paramArrayOfChar2[(--k)] = paramArrayOfChar1[2];
/*      */     } else {
/* 1401 */       System.arraycopy(paramArrayOfChar1, 0, paramArrayOfChar2, i, paramInt);
/*      */     }
/* 1403 */     this.fastPathData.lastFreeIndex += paramInt;
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
/*      */   private void localizeDigits(char[] paramArrayOfChar)
/*      */   {
/* 1423 */     int i = this.fastPathData.lastFreeIndex - this.fastPathData.fractionalFirstIndex;
/*      */     
/*      */ 
/*      */ 
/* 1427 */     if (i < 0) {
/* 1428 */       i = this.groupingSize;
/*      */     }
/*      */     
/* 1431 */     for (int j = this.fastPathData.lastFreeIndex - 1; 
/* 1432 */         j >= this.fastPathData.firstUsedIndex; 
/* 1433 */         j--) {
/* 1434 */       if (i != 0)
/*      */       {
/* 1436 */         int tmp52_51 = j; char[] tmp52_50 = paramArrayOfChar;tmp52_50[tmp52_51] = ((char)(tmp52_50[tmp52_51] + this.fastPathData.zeroDelta));
/* 1437 */         i--;
/*      */       }
/*      */       else {
/* 1440 */         i = this.groupingSize;
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
/*      */   private void fastDoubleFormat(double paramDouble, boolean paramBoolean)
/*      */   {
/* 1458 */     char[] arrayOfChar = this.fastPathData.fastPathContainer;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1481 */     int i = (int)paramDouble;
/*      */     
/*      */ 
/* 1484 */     double d1 = paramDouble - i;
/*      */     
/*      */ 
/* 1487 */     double d2 = d1 * this.fastPathData.fractionalScaleFactor;
/*      */     
/*      */ 
/*      */ 
/* 1491 */     int j = (int)d2;
/*      */     
/*      */ 
/* 1494 */     d2 -= j;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1500 */     boolean bool = false;
/* 1501 */     if (d2 >= 0.5D) {
/* 1502 */       if (d2 == 0.5D)
/*      */       {
/* 1504 */         bool = exactRoundUp(d1, j);
/*      */       } else {
/* 1506 */         bool = true;
/*      */       }
/* 1508 */       if (bool)
/*      */       {
/* 1510 */         if (j < this.fastPathData.fractionalMaxIntBound) {
/* 1511 */           j++;
/*      */         }
/*      */         else {
/* 1514 */           j = 0;
/* 1515 */           i++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1521 */     collectFractionalDigits(j, arrayOfChar, this.fastPathData.fractionalFirstIndex);
/*      */     
/* 1523 */     collectIntegralDigits(i, arrayOfChar, this.fastPathData.integralLastIndex);
/*      */     
/*      */ 
/*      */ 
/* 1527 */     if (this.fastPathData.zeroDelta != 0) {
/* 1528 */       localizeDigits(arrayOfChar);
/*      */     }
/*      */     
/* 1531 */     if (paramBoolean) {
/* 1532 */       if (this.fastPathData.negativeAffixesRequired) {
/* 1533 */         addAffixes(arrayOfChar, this.fastPathData.charsNegativePrefix, this.fastPathData.charsNegativeSuffix);
/*      */       }
/*      */     }
/* 1536 */     else if (this.fastPathData.positiveAffixesRequired) {
/* 1537 */       addAffixes(arrayOfChar, this.fastPathData.charsPositivePrefix, this.fastPathData.charsPositiveSuffix);
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
/*      */   String fastFormat(double paramDouble)
/*      */   {
/* 1558 */     if (this.fastPathCheckNeeded) {
/* 1559 */       checkAndSetFastPathStatus();
/*      */     }
/* 1561 */     if (!this.isFastPath)
/*      */     {
/* 1563 */       return null;
/*      */     }
/* 1565 */     if (!Double.isFinite(paramDouble))
/*      */     {
/* 1567 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1571 */     boolean bool = false;
/* 1572 */     if (paramDouble < 0.0D) {
/* 1573 */       bool = true;
/* 1574 */       paramDouble = -paramDouble;
/* 1575 */     } else if (paramDouble == 0.0D) {
/* 1576 */       bool = Math.copySign(1.0D, paramDouble) == -1.0D;
/* 1577 */       paramDouble = 0.0D;
/*      */     }
/*      */     
/* 1580 */     if (paramDouble > 2.147483647E9D)
/*      */     {
/* 1582 */       return null;
/*      */     }
/* 1584 */     fastDoubleFormat(paramDouble, bool);
/*      */     
/*      */ 
/* 1587 */     return new String(this.fastPathData.fastPathContainer, this.fastPathData.firstUsedIndex, this.fastPathData.lastFreeIndex - this.fastPathData.firstUsedIndex);
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
/*      */   private StringBuffer subformat(StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1617 */     char c1 = this.symbols.getZeroDigit();
/* 1618 */     int i = c1 - '0';
/* 1619 */     char c2 = this.symbols.getGroupingSeparator();
/*      */     
/*      */ 
/* 1622 */     char c3 = this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1630 */     if (this.digitList.isZero()) {
/* 1631 */       this.digitList.decimalAt = 0;
/*      */     }
/*      */     
/* 1634 */     if (paramBoolean1) {
/* 1635 */       append(paramStringBuffer, this.negativePrefix, paramFieldDelegate, 
/* 1636 */         getNegativePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     } else
/* 1638 */       append(paramStringBuffer, this.positivePrefix, paramFieldDelegate, 
/* 1639 */         getPositivePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     int j;
/*      */     int k;
/* 1642 */     int m; int n; int i1; int i2; int i3; int i4; if (this.useExponentialNotation) {
/* 1643 */       j = paramStringBuffer.length();
/* 1644 */       k = -1;
/* 1645 */       m = -1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1658 */       n = this.digitList.decimalAt;
/* 1659 */       i1 = paramInt1;
/* 1660 */       i2 = paramInt2;
/* 1661 */       if ((i1 > 1) && (i1 > paramInt2))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */         if (n >= 1) {
/* 1668 */           n = (n - 1) / i1 * i1;
/*      */         }
/*      */         else {
/* 1671 */           n = (n - i1) / i1 * i1;
/*      */         }
/* 1673 */         i2 = 1;
/*      */       }
/*      */       else {
/* 1676 */         n -= i2;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1683 */       i3 = paramInt2 + paramInt4;
/* 1684 */       if (i3 < 0) {
/* 1685 */         i3 = Integer.MAX_VALUE;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1690 */       i4 = this.digitList.isZero() ? i2 : this.digitList.decimalAt - n;
/*      */       
/* 1692 */       if (i3 < i4) {
/* 1693 */         i3 = i4;
/*      */       }
/* 1695 */       int i5 = this.digitList.count;
/* 1696 */       if (i3 > i5) {
/* 1697 */         i5 = i3;
/*      */       }
/* 1699 */       int i6 = 0;
/*      */       
/* 1701 */       for (int i7 = 0; i7 < i5; i7++) {
/* 1702 */         if (i7 == i4)
/*      */         {
/* 1704 */           k = paramStringBuffer.length();
/*      */           
/* 1706 */           paramStringBuffer.append(c3);
/* 1707 */           i6 = 1;
/*      */           
/*      */ 
/* 1710 */           m = paramStringBuffer.length();
/*      */         }
/* 1712 */         paramStringBuffer.append(i7 < this.digitList.count ? (char)(this.digitList.digits[i7] + i) : c1);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1717 */       if ((this.decimalSeparatorAlwaysShown) && (i5 == i4))
/*      */       {
/* 1719 */         k = paramStringBuffer.length();
/*      */         
/* 1721 */         paramStringBuffer.append(c3);
/* 1722 */         i6 = 1;
/*      */         
/*      */ 
/* 1725 */         m = paramStringBuffer.length();
/*      */       }
/*      */       
/*      */ 
/* 1729 */       if (k == -1) {
/* 1730 */         k = paramStringBuffer.length();
/*      */       }
/* 1732 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, k, paramStringBuffer);
/*      */       
/* 1734 */       if (i6 != 0) {
/* 1735 */         paramFieldDelegate.formatted(NumberFormat.Field.DECIMAL_SEPARATOR, NumberFormat.Field.DECIMAL_SEPARATOR, k, m, paramStringBuffer);
/*      */       }
/*      */       
/*      */ 
/* 1739 */       if (m == -1) {
/* 1740 */         m = paramStringBuffer.length();
/*      */       }
/* 1742 */       paramFieldDelegate.formatted(1, NumberFormat.Field.FRACTION, NumberFormat.Field.FRACTION, m, paramStringBuffer
/* 1743 */         .length(), paramStringBuffer);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1749 */       i7 = paramStringBuffer.length();
/*      */       
/* 1751 */       paramStringBuffer.append(this.symbols.getExponentSeparator());
/*      */       
/* 1753 */       paramFieldDelegate.formatted(NumberFormat.Field.EXPONENT_SYMBOL, NumberFormat.Field.EXPONENT_SYMBOL, i7, paramStringBuffer
/* 1754 */         .length(), paramStringBuffer);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1759 */       if (this.digitList.isZero()) {
/* 1760 */         n = 0;
/*      */       }
/*      */       
/* 1763 */       boolean bool = n < 0;
/* 1764 */       if (bool) {
/* 1765 */         n = -n;
/* 1766 */         i7 = paramStringBuffer.length();
/* 1767 */         paramStringBuffer.append(this.symbols.getMinusSign());
/* 1768 */         paramFieldDelegate.formatted(NumberFormat.Field.EXPONENT_SIGN, NumberFormat.Field.EXPONENT_SIGN, i7, paramStringBuffer
/* 1769 */           .length(), paramStringBuffer);
/*      */       }
/* 1771 */       this.digitList.set(bool, n);
/*      */       
/* 1773 */       int i8 = paramStringBuffer.length();
/*      */       
/* 1775 */       for (int i9 = this.digitList.decimalAt; i9 < this.minExponentDigits; i9++) {
/* 1776 */         paramStringBuffer.append(c1);
/*      */       }
/* 1778 */       for (i9 = 0; i9 < this.digitList.decimalAt; i9++) {
/* 1779 */         paramStringBuffer.append(i9 < this.digitList.count ? (char)(this.digitList.digits[i9] + i) : c1);
/*      */       }
/*      */       
/* 1782 */       paramFieldDelegate.formatted(NumberFormat.Field.EXPONENT, NumberFormat.Field.EXPONENT, i8, paramStringBuffer
/* 1783 */         .length(), paramStringBuffer);
/*      */     } else {
/* 1785 */       j = paramStringBuffer.length();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1791 */       k = paramInt2;
/* 1792 */       m = 0;
/* 1793 */       if ((this.digitList.decimalAt > 0) && (k < this.digitList.decimalAt)) {
/* 1794 */         k = this.digitList.decimalAt;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1801 */       if (k > paramInt1) {
/* 1802 */         k = paramInt1;
/* 1803 */         m = this.digitList.decimalAt - k;
/*      */       }
/*      */       
/* 1806 */       n = paramStringBuffer.length();
/* 1807 */       for (i1 = k - 1; i1 >= 0; i1--) {
/* 1808 */         if ((i1 < this.digitList.decimalAt) && (m < this.digitList.count))
/*      */         {
/* 1810 */           paramStringBuffer.append((char)(this.digitList.digits[(m++)] + i));
/*      */         }
/*      */         else {
/* 1813 */           paramStringBuffer.append(c1);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1819 */         if ((isGroupingUsed()) && (i1 > 0) && (this.groupingSize != 0) && (i1 % this.groupingSize == 0))
/*      */         {
/* 1821 */           i2 = paramStringBuffer.length();
/* 1822 */           paramStringBuffer.append(c2);
/* 1823 */           paramFieldDelegate.formatted(NumberFormat.Field.GROUPING_SEPARATOR, NumberFormat.Field.GROUPING_SEPARATOR, i2, paramStringBuffer
/*      */           
/* 1825 */             .length(), paramStringBuffer);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1831 */       i1 = (paramInt4 > 0) || ((!paramBoolean2) && (m < this.digitList.count)) ? 1 : 0;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1837 */       if ((i1 == 0) && (paramStringBuffer.length() == n)) {
/* 1838 */         paramStringBuffer.append(c1);
/*      */       }
/*      */       
/* 1841 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, paramStringBuffer
/* 1842 */         .length(), paramStringBuffer);
/*      */       
/*      */ 
/* 1845 */       i2 = paramStringBuffer.length();
/* 1846 */       if ((this.decimalSeparatorAlwaysShown) || (i1 != 0)) {
/* 1847 */         paramStringBuffer.append(c3);
/*      */       }
/*      */       
/* 1850 */       if (i2 != paramStringBuffer.length()) {
/* 1851 */         paramFieldDelegate.formatted(NumberFormat.Field.DECIMAL_SEPARATOR, NumberFormat.Field.DECIMAL_SEPARATOR, i2, paramStringBuffer
/*      */         
/* 1853 */           .length(), paramStringBuffer);
/*      */       }
/* 1855 */       i3 = paramStringBuffer.length();
/*      */       
/* 1857 */       for (i4 = 0; i4 < paramInt3; i4++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1864 */         if ((i4 >= paramInt4) && ((paramBoolean2) || (m >= this.digitList.count))) {
/*      */           break;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1872 */         if (-1 - i4 > this.digitList.decimalAt - 1) {
/* 1873 */           paramStringBuffer.append(c1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 1879 */         else if ((!paramBoolean2) && (m < this.digitList.count)) {
/* 1880 */           paramStringBuffer.append((char)(this.digitList.digits[(m++)] + i));
/*      */         } else {
/* 1882 */           paramStringBuffer.append(c1);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1887 */       paramFieldDelegate.formatted(1, NumberFormat.Field.FRACTION, NumberFormat.Field.FRACTION, i3, paramStringBuffer
/* 1888 */         .length(), paramStringBuffer);
/*      */     }
/*      */     
/* 1891 */     if (paramBoolean1) {
/* 1892 */       append(paramStringBuffer, this.negativeSuffix, paramFieldDelegate, 
/* 1893 */         getNegativeSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     } else {
/* 1895 */       append(paramStringBuffer, this.positiveSuffix, paramFieldDelegate, 
/* 1896 */         getPositiveSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     }
/*      */     
/* 1899 */     return paramStringBuffer;
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
/*      */   private void append(StringBuffer paramStringBuffer, String paramString, Format.FieldDelegate paramFieldDelegate, FieldPosition[] paramArrayOfFieldPosition, Format.Field paramField)
/*      */   {
/* 1919 */     int i = paramStringBuffer.length();
/*      */     
/* 1921 */     if (paramString.length() > 0) {
/* 1922 */       paramStringBuffer.append(paramString);
/* 1923 */       int j = 0; for (int k = paramArrayOfFieldPosition.length; j < k; 
/* 1924 */           j++) {
/* 1925 */         FieldPosition localFieldPosition = paramArrayOfFieldPosition[j];
/* 1926 */         Format.Field localField = localFieldPosition.getFieldAttribute();
/*      */         
/* 1928 */         if (localField == NumberFormat.Field.SIGN) {
/* 1929 */           localField = paramField;
/*      */         }
/* 1931 */         paramFieldDelegate.formatted(localField, localField, i + localFieldPosition
/* 1932 */           .getBeginIndex(), i + localFieldPosition
/* 1933 */           .getEndIndex(), paramStringBuffer);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Number parse(String paramString, ParsePosition paramParsePosition)
/*      */   {
/* 1997 */     if (paramString.regionMatches(paramParsePosition.index, this.symbols.getNaN(), 0, this.symbols.getNaN().length())) {
/* 1998 */       paramParsePosition.index += this.symbols.getNaN().length();
/* 1999 */       return new Double(NaN.0D);
/*      */     }
/*      */     
/* 2002 */     boolean[] arrayOfBoolean = new boolean[2];
/* 2003 */     if (!subparse(paramString, paramParsePosition, this.positivePrefix, this.negativePrefix, this.digitList, false, arrayOfBoolean)) {
/* 2004 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2008 */     if (arrayOfBoolean[0] != 0) {
/* 2009 */       if (arrayOfBoolean[1] == (this.multiplier >= 0 ? 1 : 0)) {
/* 2010 */         return new Double(Double.POSITIVE_INFINITY);
/*      */       }
/* 2012 */       return new Double(Double.NEGATIVE_INFINITY);
/*      */     }
/*      */     
/*      */ 
/* 2016 */     if (this.multiplier == 0) {
/* 2017 */       if (this.digitList.isZero())
/* 2018 */         return new Double(NaN.0D);
/* 2019 */       if (arrayOfBoolean[1] != 0) {
/* 2020 */         return new Double(Double.POSITIVE_INFINITY);
/*      */       }
/* 2022 */       return new Double(Double.NEGATIVE_INFINITY);
/*      */     }
/*      */     
/*      */ 
/* 2026 */     if (isParseBigDecimal()) {
/* 2027 */       BigDecimal localBigDecimal = this.digitList.getBigDecimal();
/*      */       
/* 2029 */       if (this.multiplier != 1) {
/*      */         try {
/* 2031 */           localBigDecimal = localBigDecimal.divide(getBigDecimalMultiplier());
/*      */         }
/*      */         catch (ArithmeticException localArithmeticException) {
/* 2034 */           localBigDecimal = localBigDecimal.divide(getBigDecimalMultiplier(), this.roundingMode);
/*      */         }
/*      */       }
/*      */       
/* 2038 */       if (arrayOfBoolean[1] == 0) {
/* 2039 */         localBigDecimal = localBigDecimal.negate();
/*      */       }
/* 2041 */       return localBigDecimal;
/*      */     }
/* 2043 */     int i = 1;
/* 2044 */     int j = 0;
/* 2045 */     double d = 0.0D;
/* 2046 */     long l = 0L;
/*      */     
/*      */ 
/* 2049 */     if (this.digitList.fitsIntoLong(arrayOfBoolean[1], isParseIntegerOnly())) {
/* 2050 */       i = 0;
/* 2051 */       l = this.digitList.getLong();
/* 2052 */       if (l < 0L) {
/* 2053 */         j = 1;
/*      */       }
/*      */     } else {
/* 2056 */       d = this.digitList.getDouble();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2061 */     if (this.multiplier != 1) {
/* 2062 */       if (i != 0) {
/* 2063 */         d /= this.multiplier;
/*      */ 
/*      */       }
/* 2066 */       else if (l % this.multiplier == 0L) {
/* 2067 */         l /= this.multiplier;
/*      */       } else {
/* 2069 */         d = l / this.multiplier;
/* 2070 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2075 */     if ((arrayOfBoolean[1] == 0) && (j == 0)) {
/* 2076 */       d = -d;
/* 2077 */       l = -l;
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
/* 2089 */     if ((this.multiplier != 1) && (i != 0)) {
/* 2090 */       l = d;
/* 2091 */       if ((d != l) || ((d == 0.0D) && (1.0D / d < 0.0D))) {}
/*      */       
/* 2093 */       i = !isParseIntegerOnly() ? 1 : 0;
/*      */     }
/*      */     
/* 2096 */     return i != 0 ? new Double(d) : new Long(l);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getBigIntegerMultiplier()
/*      */   {
/* 2105 */     if (this.bigIntegerMultiplier == null) {
/* 2106 */       this.bigIntegerMultiplier = BigInteger.valueOf(this.multiplier);
/*      */     }
/* 2108 */     return this.bigIntegerMultiplier;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigDecimal getBigDecimalMultiplier()
/*      */   {
/* 2116 */     if (this.bigDecimalMultiplier == null) {
/* 2117 */       this.bigDecimalMultiplier = new BigDecimal(this.multiplier);
/*      */     }
/* 2119 */     return this.bigDecimalMultiplier;
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
/*      */   private final boolean subparse(String paramString1, ParsePosition paramParsePosition, String paramString2, String paramString3, DigitList paramDigitList, boolean paramBoolean, boolean[] paramArrayOfBoolean)
/*      */   {
/* 2143 */     int i = paramParsePosition.index;
/* 2144 */     int j = paramParsePosition.index;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2149 */     boolean bool1 = paramString1.regionMatches(i, paramString2, 0, paramString2
/* 2150 */       .length());
/* 2151 */     boolean bool2 = paramString1.regionMatches(i, paramString3, 0, paramString3
/* 2152 */       .length());
/*      */     
/* 2154 */     if ((bool1) && (bool2)) {
/* 2155 */       if (paramString2.length() > paramString3.length()) {
/* 2156 */         bool2 = false;
/* 2157 */       } else if (paramString2.length() < paramString3.length()) {
/* 2158 */         bool1 = false;
/*      */       }
/*      */     }
/*      */     
/* 2162 */     if (bool1) {
/* 2163 */       i += paramString2.length();
/* 2164 */     } else if (bool2) {
/* 2165 */       i += paramString3.length();
/*      */     } else {
/* 2167 */       paramParsePosition.errorIndex = i;
/* 2168 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 2172 */     paramArrayOfBoolean[0] = false;
/* 2173 */     if ((!paramBoolean) && (paramString1.regionMatches(i, this.symbols.getInfinity(), 0, this.symbols
/* 2174 */       .getInfinity().length()))) {
/* 2175 */       i += this.symbols.getInfinity().length();
/* 2176 */       paramArrayOfBoolean[0] = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/* 2185 */       paramDigitList.decimalAt = (paramDigitList.count = 0);
/* 2186 */       int m = this.symbols.getZeroDigit();
/*      */       
/*      */ 
/* 2189 */       char c1 = this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
/* 2190 */       char c2 = this.symbols.getGroupingSeparator();
/* 2191 */       String str = this.symbols.getExponentSeparator();
/* 2192 */       int n = 0;
/* 2193 */       int i1 = 0;
/* 2194 */       int i2 = 0;
/* 2195 */       int i3 = 0;
/*      */       
/*      */ 
/*      */ 
/* 2199 */       int i4 = 0;
/*      */       
/* 2201 */       int k = -1;
/* 2202 */       for (; i < paramString1.length(); i++) {
/* 2203 */         char c3 = paramString1.charAt(i);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2216 */         int i5 = c3 - m;
/* 2217 */         if ((i5 < 0) || (i5 > 9)) {
/* 2218 */           i5 = Character.digit(c3, 10);
/*      */         }
/*      */         
/* 2221 */         if (i5 == 0)
/*      */         {
/* 2223 */           k = -1;
/* 2224 */           i2 = 1;
/*      */           
/*      */ 
/* 2227 */           if (paramDigitList.count == 0)
/*      */           {
/* 2229 */             if (n != 0)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2237 */               paramDigitList.decimalAt -= 1; }
/*      */           } else {
/* 2239 */             i4++;
/* 2240 */             paramDigitList.append((char)(i5 + 48));
/*      */           }
/* 2242 */         } else if ((i5 > 0) && (i5 <= 9)) {
/* 2243 */           i2 = 1;
/* 2244 */           i4++;
/* 2245 */           paramDigitList.append((char)(i5 + 48));
/*      */           
/*      */ 
/* 2248 */           k = -1;
/* 2249 */         } else if ((!paramBoolean) && (c3 == c1))
/*      */         {
/*      */ 
/* 2252 */           if ((isParseIntegerOnly()) || (n != 0)) {
/*      */             break;
/*      */           }
/* 2255 */           paramDigitList.decimalAt = i4;
/* 2256 */           n = 1;
/* 2257 */         } else if ((!paramBoolean) && (c3 == c2) && (isGroupingUsed())) {
/* 2258 */           if (n != 0) {
/*      */             break;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2264 */           k = i;
/* 2265 */         } else { if ((paramBoolean) || (!paramString1.regionMatches(i, str, 0, str.length())) || (i1 != 0)) {
/*      */             break;
/*      */           }
/* 2268 */           ParsePosition localParsePosition = new ParsePosition(i + str.length());
/* 2269 */           boolean[] arrayOfBoolean = new boolean[2];
/* 2270 */           DigitList localDigitList = new DigitList();
/*      */           
/* 2272 */           if ((!subparse(paramString1, localParsePosition, "", Character.toString(this.symbols.getMinusSign()), localDigitList, true, arrayOfBoolean)) || 
/* 2273 */             (!localDigitList.fitsIntoLong(arrayOfBoolean[1], true))) break;
/* 2274 */           i = localParsePosition.index;
/* 2275 */           i3 = (int)localDigitList.getLong();
/* 2276 */           if (arrayOfBoolean[1] == 0) {
/* 2277 */             i3 = -i3;
/*      */           }
/* 2279 */           i1 = 1; break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2287 */       if (k != -1) {
/* 2288 */         i = k;
/*      */       }
/*      */       
/*      */ 
/* 2292 */       if (n == 0) {
/* 2293 */         paramDigitList.decimalAt = i4;
/*      */       }
/*      */       
/*      */ 
/* 2297 */       paramDigitList.decimalAt += i3;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2303 */       if ((i2 == 0) && (i4 == 0)) {
/* 2304 */         paramParsePosition.index = j;
/* 2305 */         paramParsePosition.errorIndex = j;
/* 2306 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2311 */     if (!paramBoolean) {
/* 2312 */       if (bool1) {
/* 2313 */         bool1 = paramString1.regionMatches(i, this.positiveSuffix, 0, this.positiveSuffix
/* 2314 */           .length());
/*      */       }
/* 2316 */       if (bool2) {
/* 2317 */         bool2 = paramString1.regionMatches(i, this.negativeSuffix, 0, this.negativeSuffix
/* 2318 */           .length());
/*      */       }
/*      */       
/*      */ 
/* 2322 */       if ((bool1) && (bool2)) {
/* 2323 */         if (this.positiveSuffix.length() > this.negativeSuffix.length()) {
/* 2324 */           bool2 = false;
/* 2325 */         } else if (this.positiveSuffix.length() < this.negativeSuffix.length()) {
/* 2326 */           bool1 = false;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2331 */       if (bool1 == bool2) {
/* 2332 */         paramParsePosition.errorIndex = i;
/* 2333 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 2337 */       paramParsePosition.index = (i + (bool1 ? this.positiveSuffix.length() : this.negativeSuffix.length()));
/*      */     } else {
/* 2339 */       paramParsePosition.index = i;
/*      */     }
/*      */     
/* 2342 */     paramArrayOfBoolean[1] = bool1;
/* 2343 */     if (paramParsePosition.index == j) {
/* 2344 */       paramParsePosition.errorIndex = i;
/* 2345 */       return false;
/*      */     }
/* 2347 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DecimalFormatSymbols getDecimalFormatSymbols()
/*      */   {
/*      */     try
/*      */     {
/* 2359 */       return (DecimalFormatSymbols)this.symbols.clone();
/*      */     } catch (Exception localException) {}
/* 2361 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDecimalFormatSymbols(DecimalFormatSymbols paramDecimalFormatSymbols)
/*      */   {
/*      */     try
/*      */     {
/* 2375 */       this.symbols = ((DecimalFormatSymbols)paramDecimalFormatSymbols.clone());
/* 2376 */       expandAffixes();
/* 2377 */       this.fastPathCheckNeeded = true;
/*      */     }
/*      */     catch (Exception localException) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPositivePrefix()
/*      */   {
/* 2390 */     return this.positivePrefix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPositivePrefix(String paramString)
/*      */   {
/* 2400 */     this.positivePrefix = paramString;
/* 2401 */     this.posPrefixPattern = null;
/* 2402 */     this.positivePrefixFieldPositions = null;
/* 2403 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private FieldPosition[] getPositivePrefixFieldPositions()
/*      */   {
/* 2415 */     if (this.positivePrefixFieldPositions == null) {
/* 2416 */       if (this.posPrefixPattern != null) {
/* 2417 */         this.positivePrefixFieldPositions = expandAffix(this.posPrefixPattern);
/*      */       } else {
/* 2419 */         this.positivePrefixFieldPositions = EmptyFieldPositionArray;
/*      */       }
/*      */     }
/* 2422 */     return this.positivePrefixFieldPositions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNegativePrefix()
/*      */   {
/* 2432 */     return this.negativePrefix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNegativePrefix(String paramString)
/*      */   {
/* 2442 */     this.negativePrefix = paramString;
/* 2443 */     this.negPrefixPattern = null;
/* 2444 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private FieldPosition[] getNegativePrefixFieldPositions()
/*      */   {
/* 2456 */     if (this.negativePrefixFieldPositions == null) {
/* 2457 */       if (this.negPrefixPattern != null) {
/* 2458 */         this.negativePrefixFieldPositions = expandAffix(this.negPrefixPattern);
/*      */       } else {
/* 2460 */         this.negativePrefixFieldPositions = EmptyFieldPositionArray;
/*      */       }
/*      */     }
/* 2463 */     return this.negativePrefixFieldPositions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPositiveSuffix()
/*      */   {
/* 2473 */     return this.positiveSuffix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPositiveSuffix(String paramString)
/*      */   {
/* 2483 */     this.positiveSuffix = paramString;
/* 2484 */     this.posSuffixPattern = null;
/* 2485 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private FieldPosition[] getPositiveSuffixFieldPositions()
/*      */   {
/* 2497 */     if (this.positiveSuffixFieldPositions == null) {
/* 2498 */       if (this.posSuffixPattern != null) {
/* 2499 */         this.positiveSuffixFieldPositions = expandAffix(this.posSuffixPattern);
/*      */       } else {
/* 2501 */         this.positiveSuffixFieldPositions = EmptyFieldPositionArray;
/*      */       }
/*      */     }
/* 2504 */     return this.positiveSuffixFieldPositions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNegativeSuffix()
/*      */   {
/* 2514 */     return this.negativeSuffix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNegativeSuffix(String paramString)
/*      */   {
/* 2524 */     this.negativeSuffix = paramString;
/* 2525 */     this.negSuffixPattern = null;
/* 2526 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private FieldPosition[] getNegativeSuffixFieldPositions()
/*      */   {
/* 2538 */     if (this.negativeSuffixFieldPositions == null) {
/* 2539 */       if (this.negSuffixPattern != null) {
/* 2540 */         this.negativeSuffixFieldPositions = expandAffix(this.negSuffixPattern);
/*      */       } else {
/* 2542 */         this.negativeSuffixFieldPositions = EmptyFieldPositionArray;
/*      */       }
/*      */     }
/* 2545 */     return this.negativeSuffixFieldPositions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMultiplier()
/*      */   {
/* 2556 */     return this.multiplier;
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
/*      */   public void setMultiplier(int paramInt)
/*      */   {
/* 2574 */     this.multiplier = paramInt;
/* 2575 */     this.bigDecimalMultiplier = null;
/* 2576 */     this.bigIntegerMultiplier = null;
/* 2577 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGroupingUsed(boolean paramBoolean)
/*      */   {
/* 2585 */     super.setGroupingUsed(paramBoolean);
/* 2586 */     this.fastPathCheckNeeded = true;
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
/*      */   public int getGroupingSize()
/*      */   {
/* 2600 */     return this.groupingSize;
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
/*      */   public void setGroupingSize(int paramInt)
/*      */   {
/* 2616 */     this.groupingSize = ((byte)paramInt);
/* 2617 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDecimalSeparatorAlwaysShown()
/*      */   {
/* 2629 */     return this.decimalSeparatorAlwaysShown;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDecimalSeparatorAlwaysShown(boolean paramBoolean)
/*      */   {
/* 2641 */     this.decimalSeparatorAlwaysShown = paramBoolean;
/* 2642 */     this.fastPathCheckNeeded = true;
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
/*      */   public boolean isParseBigDecimal()
/*      */   {
/* 2655 */     return this.parseBigDecimal;
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
/*      */   public void setParseBigDecimal(boolean paramBoolean)
/*      */   {
/* 2668 */     this.parseBigDecimal = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/* 2676 */     DecimalFormat localDecimalFormat = (DecimalFormat)super.clone();
/* 2677 */     localDecimalFormat.symbols = ((DecimalFormatSymbols)this.symbols.clone());
/* 2678 */     localDecimalFormat.digitList = ((DigitList)this.digitList.clone());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2689 */     localDecimalFormat.fastPathCheckNeeded = true;
/* 2690 */     localDecimalFormat.isFastPath = false;
/* 2691 */     localDecimalFormat.fastPathData = null;
/*      */     
/* 2693 */     return localDecimalFormat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 2702 */     if (paramObject == null)
/* 2703 */       return false;
/* 2704 */     if (!super.equals(paramObject))
/* 2705 */       return false;
/* 2706 */     DecimalFormat localDecimalFormat = (DecimalFormat)paramObject;
/* 2707 */     if ((this.posPrefixPattern != localDecimalFormat.posPrefixPattern) || 
/* 2708 */       (!this.positivePrefix.equals(localDecimalFormat.positivePrefix))) { if (this.posPrefixPattern != null)
/*      */       {
/* 2710 */         if (!this.posPrefixPattern.equals(localDecimalFormat.posPrefixPattern)) {} } } else if ((this.posSuffixPattern != localDecimalFormat.posSuffixPattern) || 
/*      */     
/* 2712 */       (!this.positiveSuffix.equals(localDecimalFormat.positiveSuffix))) { if (this.posSuffixPattern != null)
/*      */       {
/* 2714 */         if (!this.posSuffixPattern.equals(localDecimalFormat.posSuffixPattern)) {} } } else if ((this.negPrefixPattern != localDecimalFormat.negPrefixPattern) || 
/*      */     
/* 2716 */       (!this.negativePrefix.equals(localDecimalFormat.negativePrefix))) { if (this.negPrefixPattern != null)
/*      */       {
/* 2718 */         if (!this.negPrefixPattern.equals(localDecimalFormat.negPrefixPattern)) {} } } else if ((this.negSuffixPattern != localDecimalFormat.negSuffixPattern) || 
/*      */     
/* 2720 */       (!this.negativeSuffix.equals(localDecimalFormat.negativeSuffix))) { if (this.negSuffixPattern != null)
/*      */       {
/* 2722 */         if (!this.negSuffixPattern.equals(localDecimalFormat.negSuffixPattern)) {} } } else if ((this.multiplier != localDecimalFormat.multiplier) || (this.groupingSize != localDecimalFormat.groupingSize) || (this.decimalSeparatorAlwaysShown != localDecimalFormat.decimalSeparatorAlwaysShown) || (this.parseBigDecimal != localDecimalFormat.parseBigDecimal) || (this.useExponentialNotation != localDecimalFormat.useExponentialNotation) || ((this.useExponentialNotation) && (this.minExponentDigits != localDecimalFormat.minExponentDigits)) || (this.maximumIntegerDigits != localDecimalFormat.maximumIntegerDigits) || (this.minimumIntegerDigits != localDecimalFormat.minimumIntegerDigits) || (this.maximumFractionDigits != localDecimalFormat.maximumFractionDigits) || (this.minimumFractionDigits != localDecimalFormat.minimumFractionDigits) || (this.roundingMode != localDecimalFormat.roundingMode)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2735 */     return this.symbols.equals(localDecimalFormat.symbols);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 2743 */     return super.hashCode() * 37 + this.positivePrefix.hashCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toPattern()
/*      */   {
/* 2755 */     return toPattern(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toLocalizedPattern()
/*      */   {
/* 2766 */     return toPattern(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void expandAffixes()
/*      */   {
/* 2777 */     StringBuffer localStringBuffer = new StringBuffer();
/* 2778 */     if (this.posPrefixPattern != null) {
/* 2779 */       this.positivePrefix = expandAffix(this.posPrefixPattern, localStringBuffer);
/* 2780 */       this.positivePrefixFieldPositions = null;
/*      */     }
/* 2782 */     if (this.posSuffixPattern != null) {
/* 2783 */       this.positiveSuffix = expandAffix(this.posSuffixPattern, localStringBuffer);
/* 2784 */       this.positiveSuffixFieldPositions = null;
/*      */     }
/* 2786 */     if (this.negPrefixPattern != null) {
/* 2787 */       this.negativePrefix = expandAffix(this.negPrefixPattern, localStringBuffer);
/* 2788 */       this.negativePrefixFieldPositions = null;
/*      */     }
/* 2790 */     if (this.negSuffixPattern != null) {
/* 2791 */       this.negativeSuffix = expandAffix(this.negSuffixPattern, localStringBuffer);
/* 2792 */       this.negativeSuffixFieldPositions = null;
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
/*      */   private String expandAffix(String paramString, StringBuffer paramStringBuffer)
/*      */   {
/* 2811 */     paramStringBuffer.setLength(0);
/* 2812 */     for (int i = 0; i < paramString.length();) {
/* 2813 */       char c = paramString.charAt(i++);
/* 2814 */       if (c == '\'') {
/* 2815 */         c = paramString.charAt(i++);
/* 2816 */         switch (c) {
/*      */         case '¤': 
/* 2818 */           if ((i < paramString.length()) && 
/* 2819 */             (paramString.charAt(i) == '¤')) {
/* 2820 */             i++;
/* 2821 */             paramStringBuffer.append(this.symbols.getInternationalCurrencySymbol()); continue;
/*      */           }
/* 2823 */           paramStringBuffer.append(this.symbols.getCurrencySymbol());
/*      */           
/* 2825 */           break;
/*      */         case '%': 
/* 2827 */           c = this.symbols.getPercent();
/* 2828 */           break;
/*      */         case '‰': 
/* 2830 */           c = this.symbols.getPerMill();
/* 2831 */           break;
/*      */         case '-': 
/* 2833 */           c = this.symbols.getMinusSign();
/*      */         }
/*      */       }
/*      */       else {
/* 2837 */         paramStringBuffer.append(c);
/*      */       } }
/* 2839 */     return paramStringBuffer.toString();
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
/*      */   private FieldPosition[] expandAffix(String paramString)
/*      */   {
/* 2858 */     ArrayList localArrayList = null;
/* 2859 */     int i = 0;
/* 2860 */     for (int j = 0; j < paramString.length();) {
/* 2861 */       int k = paramString.charAt(j++);
/* 2862 */       if (k == 39) {
/* 2863 */         int m = -1;
/* 2864 */         NumberFormat.Field localField = null;
/* 2865 */         k = paramString.charAt(j++);
/* 2866 */         Object localObject; switch (k)
/*      */         {
/*      */         case 164: 
/* 2869 */           if ((j < paramString.length()) && 
/* 2870 */             (paramString.charAt(j) == '¤')) {
/* 2871 */             j++;
/* 2872 */             localObject = this.symbols.getInternationalCurrencySymbol();
/*      */           } else {
/* 2874 */             localObject = this.symbols.getCurrencySymbol();
/*      */           }
/* 2876 */           if (((String)localObject).length() <= 0) continue;
/* 2877 */           if (localArrayList == null) {
/* 2878 */             localArrayList = new ArrayList(2);
/*      */           }
/* 2880 */           FieldPosition localFieldPosition = new FieldPosition(NumberFormat.Field.CURRENCY);
/* 2881 */           localFieldPosition.setBeginIndex(i);
/* 2882 */           localFieldPosition.setEndIndex(i + ((String)localObject).length());
/* 2883 */           localArrayList.add(localFieldPosition);
/* 2884 */           i += ((String)localObject).length();
/* 2885 */           break;
/*      */         
/*      */         case 37: 
/* 2888 */           k = this.symbols.getPercent();
/* 2889 */           m = -1;
/* 2890 */           localField = NumberFormat.Field.PERCENT;
/* 2891 */           break;
/*      */         case 8240: 
/* 2893 */           k = this.symbols.getPerMill();
/* 2894 */           m = -1;
/* 2895 */           localField = NumberFormat.Field.PERMILLE;
/* 2896 */           break;
/*      */         case 45: 
/* 2898 */           k = this.symbols.getMinusSign();
/* 2899 */           m = -1;
/* 2900 */           localField = NumberFormat.Field.SIGN;
/*      */         
/*      */         default: 
/* 2903 */           if (localField != null) {
/* 2904 */             if (localArrayList == null) {
/* 2905 */               localArrayList = new ArrayList(2);
/*      */             }
/* 2907 */             localObject = new FieldPosition(localField, m);
/* 2908 */             ((FieldPosition)localObject).setBeginIndex(i);
/* 2909 */             ((FieldPosition)localObject).setEndIndex(i + 1);
/* 2910 */             localArrayList.add(localObject);
/*      */           }
/*      */           break; }
/* 2913 */       } else { i++;
/*      */       } }
/* 2915 */     if (localArrayList != null) {
/* 2916 */       return (FieldPosition[])localArrayList.toArray(EmptyFieldPositionArray);
/*      */     }
/* 2918 */     return EmptyFieldPositionArray;
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
/*      */   private void appendAffix(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
/*      */   {
/* 2938 */     if (paramString1 == null) {
/* 2939 */       appendAffix(paramStringBuffer, paramString2, paramBoolean);
/*      */     } else {
/*      */       int i;
/* 2942 */       for (int j = 0; j < paramString1.length(); j = i) {
/* 2943 */         i = paramString1.indexOf('\'', j);
/* 2944 */         if (i < 0) {
/* 2945 */           appendAffix(paramStringBuffer, paramString1.substring(j), paramBoolean);
/* 2946 */           break;
/*      */         }
/* 2948 */         if (i > j) {
/* 2949 */           appendAffix(paramStringBuffer, paramString1.substring(j, i), paramBoolean);
/*      */         }
/* 2951 */         char c = paramString1.charAt(++i);
/* 2952 */         i++;
/* 2953 */         if (c == '\'') {
/* 2954 */           paramStringBuffer.append(c);
/*      */         }
/* 2956 */         else if ((c == '¤') && 
/* 2957 */           (i < paramString1.length()) && 
/* 2958 */           (paramString1.charAt(i) == '¤')) {
/* 2959 */           i++;
/* 2960 */           paramStringBuffer.append(c);
/*      */         }
/* 2962 */         else if (paramBoolean) {
/* 2963 */           switch (c) {
/*      */           case '%': 
/* 2965 */             c = this.symbols.getPercent();
/* 2966 */             break;
/*      */           case '‰': 
/* 2968 */             c = this.symbols.getPerMill();
/* 2969 */             break;
/*      */           case '-': 
/* 2971 */             c = this.symbols.getMinusSign();
/*      */           }
/*      */           
/*      */         }
/* 2975 */         paramStringBuffer.append(c);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void appendAffix(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/* 2987 */     if (paramBoolean)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2996 */       i = (paramString.indexOf(this.symbols.getZeroDigit()) >= 0) || (paramString.indexOf(this.symbols.getGroupingSeparator()) >= 0) || (paramString.indexOf(this.symbols.getDecimalSeparator()) >= 0) || (paramString.indexOf(this.symbols.getPercent()) >= 0) || (paramString.indexOf(this.symbols.getPerMill()) >= 0) || (paramString.indexOf(this.symbols.getDigit()) >= 0) || (paramString.indexOf(this.symbols.getPatternSeparator()) >= 0) || (paramString.indexOf(this.symbols.getMinusSign()) >= 0) || (paramString.indexOf('¤') >= 0) ? 1 : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 3006 */       i = (paramString.indexOf('0') >= 0) || (paramString.indexOf(',') >= 0) || (paramString.indexOf('.') >= 0) || (paramString.indexOf('%') >= 0) || (paramString.indexOf('‰') >= 0) || (paramString.indexOf('#') >= 0) || (paramString.indexOf(';') >= 0) || (paramString.indexOf('-') >= 0) || (paramString.indexOf('¤') >= 0) ? 1 : 0;
/*      */     }
/* 3008 */     if (i != 0) paramStringBuffer.append('\'');
/* 3009 */     if (paramString.indexOf('\'') < 0) { paramStringBuffer.append(paramString);
/*      */     } else {
/* 3011 */       for (int j = 0; j < paramString.length(); j++) {
/* 3012 */         char c = paramString.charAt(j);
/* 3013 */         paramStringBuffer.append(c);
/* 3014 */         if (c == '\'') paramStringBuffer.append(c);
/*      */       }
/*      */     }
/* 3017 */     if (i != 0) { paramStringBuffer.append('\'');
/*      */     }
/*      */   }
/*      */   
/*      */   private String toPattern(boolean paramBoolean)
/*      */   {
/* 3023 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3024 */     for (int i = 1; i >= 0; i--) {
/* 3025 */       if (i == 1)
/* 3026 */         appendAffix(localStringBuffer, this.posPrefixPattern, this.positivePrefix, paramBoolean); else {
/* 3027 */         appendAffix(localStringBuffer, this.negPrefixPattern, this.negativePrefix, paramBoolean);
/*      */       }
/*      */       
/*      */ 
/* 3031 */       int k = this.useExponentialNotation ? getMaximumIntegerDigits() : Math.max(this.groupingSize, getMinimumIntegerDigits()) + 1;
/* 3032 */       for (int j = k; j > 0; j--) {
/* 3033 */         if ((j != k) && (isGroupingUsed()) && (this.groupingSize != 0) && (j % this.groupingSize == 0))
/*      */         {
/* 3035 */           localStringBuffer.append(paramBoolean ? this.symbols.getGroupingSeparator() : ',');
/*      */         }
/*      */         
/* 3038 */         localStringBuffer.append(
/* 3039 */           paramBoolean ? this.symbols
/* 3040 */           .getDigit() : j <= getMinimumIntegerDigits() ? '0' : paramBoolean ? this.symbols.getZeroDigit() : '#');
/*      */       }
/* 3042 */       if ((getMaximumFractionDigits() > 0) || (this.decimalSeparatorAlwaysShown)) {
/* 3043 */         localStringBuffer.append(paramBoolean ? this.symbols.getDecimalSeparator() : '.');
/*      */       }
/* 3045 */       for (j = 0; j < getMaximumFractionDigits(); j++) {
/* 3046 */         if (j < getMinimumFractionDigits()) {
/* 3047 */           localStringBuffer.append(paramBoolean ? this.symbols.getZeroDigit() : '0');
/*      */         }
/*      */         else {
/* 3050 */           localStringBuffer.append(paramBoolean ? this.symbols.getDigit() : '#');
/*      */         }
/*      */       }
/*      */       
/* 3054 */       if (this.useExponentialNotation)
/*      */       {
/* 3056 */         localStringBuffer.append(paramBoolean ? this.symbols.getExponentSeparator() : PATTERN_EXPONENT);
/*      */         
/* 3058 */         for (j = 0; j < this.minExponentDigits; j++) {
/* 3059 */           localStringBuffer.append(paramBoolean ? this.symbols.getZeroDigit() : '0');
/*      */         }
/*      */       }
/* 3062 */       if (i == 1) {
/* 3063 */         appendAffix(localStringBuffer, this.posSuffixPattern, this.positiveSuffix, paramBoolean);
/* 3064 */         if (((this.negSuffixPattern != this.posSuffixPattern) || 
/* 3065 */           (!this.negativeSuffix.equals(this.positiveSuffix))) && ((this.negSuffixPattern != null) && 
/*      */           
/* 3067 */           (this.negSuffixPattern.equals(this.posSuffixPattern)) && (
/* 3068 */           ((this.negPrefixPattern != null) && (this.posPrefixPattern != null) && 
/* 3069 */           (this.negPrefixPattern.equals("'-" + this.posPrefixPattern))) || ((this.negPrefixPattern == this.posPrefixPattern) && 
/*      */           
/* 3071 */           (this.negativePrefix.equals(this.symbols.getMinusSign() + this.positivePrefix)))))) {
/*      */           break;
/*      */         }
/* 3074 */         localStringBuffer.append(paramBoolean ? this.symbols.getPatternSeparator() : ';');
/*      */       } else {
/* 3076 */         appendAffix(localStringBuffer, this.negSuffixPattern, this.negativeSuffix, paramBoolean);
/*      */       } }
/* 3078 */     return localStringBuffer.toString();
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
/*      */   public void applyPattern(String paramString)
/*      */   {
/* 3104 */     applyPattern(paramString, false);
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
/*      */   public void applyLocalizedPattern(String paramString)
/*      */   {
/* 3131 */     applyPattern(paramString, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void applyPattern(String paramString, boolean paramBoolean)
/*      */   {
/* 3138 */     char c1 = '0';
/* 3139 */     char c2 = ',';
/* 3140 */     char c3 = '.';
/* 3141 */     char c4 = '%';
/* 3142 */     char c5 = '‰';
/* 3143 */     char c6 = '#';
/* 3144 */     int i = 59;
/* 3145 */     String str = "E";
/* 3146 */     char c7 = '-';
/* 3147 */     if (paramBoolean) {
/* 3148 */       c1 = this.symbols.getZeroDigit();
/* 3149 */       c2 = this.symbols.getGroupingSeparator();
/* 3150 */       c3 = this.symbols.getDecimalSeparator();
/* 3151 */       c4 = this.symbols.getPercent();
/* 3152 */       c5 = this.symbols.getPerMill();
/* 3153 */       c6 = this.symbols.getDigit();
/* 3154 */       i = this.symbols.getPatternSeparator();
/* 3155 */       str = this.symbols.getExponentSeparator();
/* 3156 */       c7 = this.symbols.getMinusSign();
/*      */     }
/* 3158 */     int j = 0;
/* 3159 */     this.decimalSeparatorAlwaysShown = false;
/* 3160 */     this.isCurrencyFormat = false;
/* 3161 */     this.useExponentialNotation = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3167 */     int k = 0;
/* 3168 */     int m = 0;
/*      */     
/* 3170 */     int n = 0;
/* 3171 */     for (int i1 = 1; (i1 >= 0) && (n < paramString.length()); i1--) {
/* 3172 */       int i2 = 0;
/* 3173 */       StringBuffer localStringBuffer1 = new StringBuffer();
/* 3174 */       StringBuffer localStringBuffer2 = new StringBuffer();
/* 3175 */       int i3 = -1;
/* 3176 */       int i4 = 1;
/* 3177 */       int i5 = 0;int i6 = 0;int i7 = 0;
/* 3178 */       int i8 = -1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3187 */       int i9 = 0;
/*      */       
/*      */ 
/* 3190 */       StringBuffer localStringBuffer3 = localStringBuffer1;
/*      */       int i11;
/* 3192 */       for (int i10 = n; i10 < paramString.length(); i10++) {
/* 3193 */         i11 = paramString.charAt(i10);
/* 3194 */         switch (i9)
/*      */         {
/*      */         case 0: 
/*      */         case 2: 
/* 3198 */           if (i2 != 0)
/*      */           {
/*      */ 
/*      */ 
/* 3202 */             if (i11 == 39) {
/* 3203 */               if ((i10 + 1 < paramString.length()) && 
/* 3204 */                 (paramString.charAt(i10 + 1) == '\'')) {
/* 3205 */                 i10++;
/* 3206 */                 localStringBuffer3.append("''"); continue;
/*      */               }
/* 3208 */               i2 = 0;
/*      */               
/* 3210 */               continue;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3215 */             if ((i11 == c6) || (i11 == c1) || (i11 == c2) || (i11 == c3))
/*      */             {
/*      */ 
/*      */ 
/* 3219 */               i9 = 1;
/* 3220 */               if (i1 == 1) {
/* 3221 */                 k = i10;
/*      */               }
/* 3223 */               i10--;
/* 3224 */               continue; }
/* 3225 */             if (i11 == 164)
/*      */             {
/*      */ 
/*      */ 
/* 3229 */               int i12 = (i10 + 1 < paramString.length()) && (paramString.charAt(i10 + 1) == '¤') ? 1 : 0;
/* 3230 */               if (i12 != 0) {
/* 3231 */                 i10++;
/*      */               }
/* 3233 */               this.isCurrencyFormat = true;
/* 3234 */               localStringBuffer3.append(i12 != 0 ? "'¤¤" : "'¤");
/* 3235 */               continue; }
/* 3236 */             if (i11 == 39)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 3241 */               if (i11 == 39) {
/* 3242 */                 if ((i10 + 1 < paramString.length()) && 
/* 3243 */                   (paramString.charAt(i10 + 1) == '\'')) {
/* 3244 */                   i10++;
/* 3245 */                   localStringBuffer3.append("''"); continue;
/*      */                 }
/* 3247 */                 i2 = 1;
/*      */                 
/* 3249 */                 continue;
/*      */               }
/* 3251 */             } else { if (i11 == i)
/*      */               {
/*      */ 
/*      */ 
/* 3255 */                 if ((i9 == 0) || (i1 == 0)) {
/* 3256 */                   throw new IllegalArgumentException("Unquoted special character '" + i11 + "' in pattern \"" + paramString + '"');
/*      */                 }
/*      */                 
/* 3259 */                 n = i10 + 1;
/* 3260 */                 i10 = paramString.length();
/* 3261 */                 continue;
/*      */               }
/*      */               
/*      */ 
/* 3265 */               if (i11 == c4) {
/* 3266 */                 if (i4 != 1) {
/* 3267 */                   throw new IllegalArgumentException("Too many percent/per mille characters in pattern \"" + paramString + '"');
/*      */                 }
/*      */                 
/* 3270 */                 i4 = 100;
/* 3271 */                 localStringBuffer3.append("'%");
/* 3272 */                 continue; }
/* 3273 */               if (i11 == c5) {
/* 3274 */                 if (i4 != 1) {
/* 3275 */                   throw new IllegalArgumentException("Too many percent/per mille characters in pattern \"" + paramString + '"');
/*      */                 }
/*      */                 
/* 3278 */                 i4 = 1000;
/* 3279 */                 localStringBuffer3.append("'‰");
/* 3280 */                 continue; }
/* 3281 */               if (i11 == c7) {
/* 3282 */                 localStringBuffer3.append("'-");
/* 3283 */                 continue;
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 3289 */           localStringBuffer3.append(i11);
/* 3290 */           break;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         case 1: 
/* 3298 */           if (i1 == 1) {
/* 3299 */             m++;
/*      */           } else {
/* 3301 */             m--; if (m != 0) continue;
/* 3302 */             i9 = 2;
/* 3303 */             localStringBuffer3 = localStringBuffer2; continue;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3317 */           if (i11 == c6) {
/* 3318 */             if (i6 > 0) {
/* 3319 */               i7++;
/*      */             } else {
/* 3321 */               i5++;
/*      */             }
/* 3323 */             if ((i8 >= 0) && (i3 < 0)) {
/* 3324 */               i8 = (byte)(i8 + 1);
/*      */             }
/* 3326 */           } else if (i11 == c1) {
/* 3327 */             if (i7 > 0) {
/* 3328 */               throw new IllegalArgumentException("Unexpected '0' in pattern \"" + paramString + '"');
/*      */             }
/*      */             
/* 3331 */             i6++;
/* 3332 */             if ((i8 >= 0) && (i3 < 0)) {
/* 3333 */               i8 = (byte)(i8 + 1);
/*      */             }
/* 3335 */           } else if (i11 == c2) {
/* 3336 */             i8 = 0;
/* 3337 */           } else if (i11 == c3) {
/* 3338 */             if (i3 >= 0) {
/* 3339 */               throw new IllegalArgumentException("Multiple decimal separators in pattern \"" + paramString + '"');
/*      */             }
/*      */             
/* 3342 */             i3 = i5 + i6 + i7;
/* 3343 */           } else if (paramString.regionMatches(i10, str, 0, str.length())) {
/* 3344 */             if (this.useExponentialNotation) {
/* 3345 */               throw new IllegalArgumentException("Multiple exponential symbols in pattern \"" + paramString + '"');
/*      */             }
/*      */             
/* 3348 */             this.useExponentialNotation = true;
/* 3349 */             this.minExponentDigits = 0;
/*      */             
/*      */ 
/*      */ 
/* 3353 */             i10 += str.length();
/* 3354 */             while ((i10 < paramString.length()) && 
/* 3355 */               (paramString.charAt(i10) == c1)) {
/* 3356 */               this.minExponentDigits = ((byte)(this.minExponentDigits + 1));
/* 3357 */               m++;
/* 3358 */               i10++;
/*      */             }
/*      */             
/* 3361 */             if ((i5 + i6 < 1) || (this.minExponentDigits < 1))
/*      */             {
/* 3363 */               throw new IllegalArgumentException("Malformed exponential pattern \"" + paramString + '"');
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3368 */             i9 = 2;
/* 3369 */             localStringBuffer3 = localStringBuffer2;
/* 3370 */             i10--;
/*      */           }
/*      */           else {
/* 3373 */             i9 = 2;
/* 3374 */             localStringBuffer3 = localStringBuffer2;
/* 3375 */             i10--;
/* 3376 */             m--;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           break;
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3395 */       if ((i6 == 0) && (i5 > 0) && (i3 >= 0))
/*      */       {
/* 3397 */         i10 = i3;
/* 3398 */         if (i10 == 0) {
/* 3399 */           i10++;
/*      */         }
/* 3401 */         i7 = i5 - i10;
/* 3402 */         i5 = i10 - 1;
/* 3403 */         i6 = 1;
/*      */       }
/*      */       
/*      */ 
/* 3407 */       if (((i3 < 0) && (i7 > 0)) || ((i3 >= 0) && ((i3 < i5) || (i3 > i5 + i6))) || (i8 == 0) || (i2 != 0))
/*      */       {
/*      */ 
/*      */ 
/* 3411 */         throw new IllegalArgumentException("Malformed pattern \"" + paramString + '"');
/*      */       }
/*      */       
/*      */ 
/* 3415 */       if (i1 == 1) {
/* 3416 */         this.posPrefixPattern = localStringBuffer1.toString();
/* 3417 */         this.posSuffixPattern = localStringBuffer2.toString();
/* 3418 */         this.negPrefixPattern = this.posPrefixPattern;
/* 3419 */         this.negSuffixPattern = this.posSuffixPattern;
/* 3420 */         i10 = i5 + i6 + i7;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3425 */         i11 = i3 >= 0 ? i3 : i10;
/*      */         
/* 3427 */         setMinimumIntegerDigits(i11 - i5);
/* 3428 */         setMaximumIntegerDigits(this.useExponentialNotation ? i5 + 
/* 3429 */           getMinimumIntegerDigits() : Integer.MAX_VALUE);
/*      */         
/* 3431 */         setMaximumFractionDigits(i3 >= 0 ? i10 - i3 : 0);
/*      */         
/* 3433 */         setMinimumFractionDigits(i3 >= 0 ? i5 + i6 - i3 : 0);
/*      */         
/* 3435 */         setGroupingUsed(i8 > 0);
/* 3436 */         this.groupingSize = (i8 > 0 ? i8 : 0);
/* 3437 */         this.multiplier = i4;
/* 3438 */         setDecimalSeparatorAlwaysShown((i3 == 0) || (i3 == i10));
/*      */       }
/*      */       else {
/* 3441 */         this.negPrefixPattern = localStringBuffer1.toString();
/* 3442 */         this.negSuffixPattern = localStringBuffer2.toString();
/* 3443 */         j = 1;
/*      */       }
/*      */     }
/*      */     
/* 3447 */     if (paramString.length() == 0) {
/* 3448 */       this.posPrefixPattern = (this.posSuffixPattern = "");
/* 3449 */       setMinimumIntegerDigits(0);
/* 3450 */       setMaximumIntegerDigits(Integer.MAX_VALUE);
/* 3451 */       setMinimumFractionDigits(0);
/* 3452 */       setMaximumFractionDigits(Integer.MAX_VALUE);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3458 */     if ((j == 0) || (
/* 3459 */       (this.negPrefixPattern.equals(this.posPrefixPattern)) && 
/* 3460 */       (this.negSuffixPattern.equals(this.posSuffixPattern)))) {
/* 3461 */       this.negSuffixPattern = this.posSuffixPattern;
/* 3462 */       this.negPrefixPattern = ("'-" + this.posPrefixPattern);
/*      */     }
/*      */     
/* 3465 */     expandAffixes();
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
/*      */   public void setMaximumIntegerDigits(int paramInt)
/*      */   {
/* 3478 */     this.maximumIntegerDigits = Math.min(Math.max(0, paramInt), Integer.MAX_VALUE);
/* 3479 */     super.setMaximumIntegerDigits(this.maximumIntegerDigits > 309 ? 309 : this.maximumIntegerDigits);
/*      */     
/* 3481 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/* 3482 */       this.minimumIntegerDigits = this.maximumIntegerDigits;
/* 3483 */       super.setMinimumIntegerDigits(this.minimumIntegerDigits > 309 ? 309 : this.minimumIntegerDigits);
/*      */     }
/*      */     
/* 3486 */     this.fastPathCheckNeeded = true;
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
/*      */   public void setMinimumIntegerDigits(int paramInt)
/*      */   {
/* 3499 */     this.minimumIntegerDigits = Math.min(Math.max(0, paramInt), Integer.MAX_VALUE);
/* 3500 */     super.setMinimumIntegerDigits(this.minimumIntegerDigits > 309 ? 309 : this.minimumIntegerDigits);
/*      */     
/* 3502 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/* 3503 */       this.maximumIntegerDigits = this.minimumIntegerDigits;
/* 3504 */       super.setMaximumIntegerDigits(this.maximumIntegerDigits > 309 ? 309 : this.maximumIntegerDigits);
/*      */     }
/*      */     
/* 3507 */     this.fastPathCheckNeeded = true;
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
/*      */   public void setMaximumFractionDigits(int paramInt)
/*      */   {
/* 3520 */     this.maximumFractionDigits = Math.min(Math.max(0, paramInt), Integer.MAX_VALUE);
/* 3521 */     super.setMaximumFractionDigits(this.maximumFractionDigits > 340 ? 340 : this.maximumFractionDigits);
/*      */     
/* 3523 */     if (this.minimumFractionDigits > this.maximumFractionDigits) {
/* 3524 */       this.minimumFractionDigits = this.maximumFractionDigits;
/* 3525 */       super.setMinimumFractionDigits(this.minimumFractionDigits > 340 ? 340 : this.minimumFractionDigits);
/*      */     }
/*      */     
/* 3528 */     this.fastPathCheckNeeded = true;
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
/*      */   public void setMinimumFractionDigits(int paramInt)
/*      */   {
/* 3541 */     this.minimumFractionDigits = Math.min(Math.max(0, paramInt), Integer.MAX_VALUE);
/* 3542 */     super.setMinimumFractionDigits(this.minimumFractionDigits > 340 ? 340 : this.minimumFractionDigits);
/*      */     
/* 3544 */     if (this.minimumFractionDigits > this.maximumFractionDigits) {
/* 3545 */       this.maximumFractionDigits = this.minimumFractionDigits;
/* 3546 */       super.setMaximumFractionDigits(this.maximumFractionDigits > 340 ? 340 : this.maximumFractionDigits);
/*      */     }
/*      */     
/* 3549 */     this.fastPathCheckNeeded = true;
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
/*      */   public int getMaximumIntegerDigits()
/*      */   {
/* 3562 */     return this.maximumIntegerDigits;
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
/*      */   public int getMinimumIntegerDigits()
/*      */   {
/* 3575 */     return this.minimumIntegerDigits;
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
/*      */   public int getMaximumFractionDigits()
/*      */   {
/* 3588 */     return this.maximumFractionDigits;
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
/*      */   public int getMinimumFractionDigits()
/*      */   {
/* 3601 */     return this.minimumFractionDigits;
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
/*      */   public Currency getCurrency()
/*      */   {
/* 3616 */     return this.symbols.getCurrency();
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
/*      */   public void setCurrency(Currency paramCurrency)
/*      */   {
/* 3633 */     if (paramCurrency != this.symbols.getCurrency()) {
/* 3634 */       this.symbols.setCurrency(paramCurrency);
/* 3635 */       if (this.isCurrencyFormat) {
/* 3636 */         expandAffixes();
/*      */       }
/*      */     }
/* 3639 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public RoundingMode getRoundingMode()
/*      */   {
/* 3651 */     return this.roundingMode;
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
/*      */   public void setRoundingMode(RoundingMode paramRoundingMode)
/*      */   {
/* 3664 */     if (paramRoundingMode == null) {
/* 3665 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3668 */     this.roundingMode = paramRoundingMode;
/* 3669 */     this.digitList.setRoundingMode(paramRoundingMode);
/* 3670 */     this.fastPathCheckNeeded = true;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 3719 */     paramObjectInputStream.defaultReadObject();
/* 3720 */     this.digitList = new DigitList();
/*      */     
/*      */ 
/*      */ 
/* 3724 */     this.fastPathCheckNeeded = true;
/* 3725 */     this.isFastPath = false;
/* 3726 */     this.fastPathData = null;
/*      */     
/* 3728 */     if (this.serialVersionOnStream < 4) {
/* 3729 */       setRoundingMode(RoundingMode.HALF_EVEN);
/*      */     } else {
/* 3731 */       setRoundingMode(getRoundingMode());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3737 */     if ((super.getMaximumIntegerDigits() > 309) || 
/* 3738 */       (super.getMaximumFractionDigits() > 340)) {
/* 3739 */       throw new InvalidObjectException("Digit count out of range");
/*      */     }
/* 3741 */     if (this.serialVersionOnStream < 3) {
/* 3742 */       setMaximumIntegerDigits(super.getMaximumIntegerDigits());
/* 3743 */       setMinimumIntegerDigits(super.getMinimumIntegerDigits());
/* 3744 */       setMaximumFractionDigits(super.getMaximumFractionDigits());
/* 3745 */       setMinimumFractionDigits(super.getMinimumFractionDigits());
/*      */     }
/* 3747 */     if (this.serialVersionOnStream < 1)
/*      */     {
/* 3749 */       this.useExponentialNotation = false;
/*      */     }
/* 3751 */     this.serialVersionOnStream = 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3758 */   private transient DigitList digitList = new DigitList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3766 */   private String positivePrefix = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3775 */   private String positiveSuffix = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3783 */   private String negativePrefix = "-";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3792 */   private String negativeSuffix = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String posPrefixPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String posSuffixPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String negPrefixPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String negSuffixPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3850 */   private int multiplier = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3861 */   private byte groupingSize = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3870 */   private boolean decimalSeparatorAlwaysShown = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3879 */   private boolean parseBigDecimal = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3886 */   private transient boolean isCurrencyFormat = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3897 */   private DecimalFormatSymbols symbols = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean useExponentialNotation;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient FieldPosition[] positivePrefixFieldPositions;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient FieldPosition[] positiveSuffixFieldPositions;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient FieldPosition[] negativePrefixFieldPositions;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient FieldPosition[] negativeSuffixFieldPositions;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private byte minExponentDigits;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3956 */   private int maximumIntegerDigits = super.getMaximumIntegerDigits();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3968 */   private int minimumIntegerDigits = super.getMinimumIntegerDigits();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3980 */   private int maximumFractionDigits = super.getMaximumFractionDigits();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3992 */   private int minimumFractionDigits = super.getMinimumFractionDigits();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4000 */   private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class FastPathData
/*      */   {
/*      */     int lastFreeIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     int firstUsedIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     int zeroDelta;
/*      */     
/*      */ 
/*      */ 
/*      */     char groupingChar;
/*      */     
/*      */ 
/*      */ 
/*      */     int integralLastIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     int fractionalFirstIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     double fractionalScaleFactor;
/*      */     
/*      */ 
/*      */ 
/*      */     int fractionalMaxIntBound;
/*      */     
/*      */ 
/*      */ 
/*      */     char[] fastPathContainer;
/*      */     
/*      */ 
/*      */ 
/*      */     char[] charsPositivePrefix;
/*      */     
/*      */ 
/*      */ 
/*      */     char[] charsNegativePrefix;
/*      */     
/*      */ 
/*      */ 
/*      */     char[] charsPositiveSuffix;
/*      */     
/*      */ 
/*      */ 
/*      */     char[] charsNegativeSuffix;
/*      */     
/*      */ 
/*      */ 
/* 4059 */     boolean positiveAffixesRequired = true;
/* 4060 */     boolean negativeAffixesRequired = true;
/*      */   }
/*      */   
/*      */ 
/* 4064 */   private transient boolean isFastPath = false;
/*      */   
/*      */ 
/* 4067 */   private transient boolean fastPathCheckNeeded = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient FastPathData fastPathData;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int currentSerialVersion = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4100 */   private int serialVersionOnStream = 4;
/*      */   private static final double MAX_INT_AS_DOUBLE = 2.147483647E9D;
/*      */   private static final char PATTERN_ZERO_DIGIT = '0';
/*      */   private static final char PATTERN_GROUPING_SEPARATOR = ',';
/*      */   private static final char PATTERN_DECIMAL_SEPARATOR = '.';
/*      */   private static final char PATTERN_PER_MILLE = '‰';
/*      */   private static final char PATTERN_PERCENT = '%';
/*      */   private static final char PATTERN_DIGIT = '#';
/*      */   private static final char PATTERN_SEPARATOR = ';';
/*      */   private static final String PATTERN_EXPONENT = "E";
/*      */   private static final char PATTERN_MINUS = '-';
/*      */   private static final char CURRENCY_SIGN = '¤';
/*      */   private static final char QUOTE = '\'';
/*      */   
/*      */   private static class DigitArrays
/*      */   {
/* 4116 */     static final char[] DigitOnes1000 = new char['Ϩ'];
/* 4117 */     static final char[] DigitTens1000 = new char['Ϩ'];
/* 4118 */     static final char[] DigitHundreds1000 = new char['Ϩ'];
/*      */     
/*      */     static
/*      */     {
/* 4122 */       int i = 0;
/* 4123 */       int j = 0;
/* 4124 */       int k = 48;
/* 4125 */       int m = 48;
/* 4126 */       int n = 48;
/* 4127 */       for (int i1 = 0; i1 < 1000; i1++)
/*      */       {
/* 4129 */         DigitOnes1000[i1] = k;
/* 4130 */         if (k == 57) {
/* 4131 */           k = 48;
/*      */         } else {
/* 4133 */           k = (char)(k + 1);
/*      */         }
/* 4135 */         DigitTens1000[i1] = m;
/* 4136 */         if (i1 == i + 9) {
/* 4137 */           i += 10;
/* 4138 */           if (m == 57) {
/* 4139 */             m = 48;
/*      */           } else {
/* 4141 */             m = (char)(m + 1);
/*      */           }
/*      */         }
/* 4144 */         DigitHundreds1000[i1] = n;
/* 4145 */         if (i1 == j + 99) {
/* 4146 */           n = (char)(n + 1);
/* 4147 */           j += 100;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4178 */   private static FieldPosition[] EmptyFieldPositionArray = new FieldPosition[0];
/*      */   static final int DOUBLE_INTEGER_DIGITS = 309;
/*      */   static final int DOUBLE_FRACTION_DIGITS = 340;
/*      */   static final int MAXIMUM_INTEGER_DIGITS = Integer.MAX_VALUE;
/*      */   static final int MAXIMUM_FRACTION_DIGITS = Integer.MAX_VALUE;
/*      */   static final long serialVersionUID = 864413376551465018L;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/DecimalFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */