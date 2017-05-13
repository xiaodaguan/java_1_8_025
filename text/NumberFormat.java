/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import java.text.spi.NumberFormatProvider;
/*      */ import java.util.Currency;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Locale.Category;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class NumberFormat
/*      */   extends Format
/*      */ {
/*      */   public static final int INTEGER_FIELD = 0;
/*      */   public static final int FRACTION_FIELD = 1;
/*      */   private static final int NUMBERSTYLE = 0;
/*      */   private static final int CURRENCYSTYLE = 1;
/*      */   private static final int PERCENTSTYLE = 2;
/*      */   private static final int SCIENTIFICSTYLE = 3;
/*      */   private static final int INTEGERSTYLE = 4;
/*      */   
/*      */   public StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  239 */     if ((!(paramObject instanceof Long)) && (!(paramObject instanceof Integer)) && (!(paramObject instanceof Short)) && (!(paramObject instanceof Byte)) && (!(paramObject instanceof AtomicInteger)) && (!(paramObject instanceof AtomicLong))) { if ((paramObject instanceof BigInteger))
/*      */       {
/*      */ 
/*      */ 
/*  243 */         if (((BigInteger)paramObject).bitLength() >= 64) {} }
/*  244 */     } else return format(((Number)paramObject).longValue(), paramStringBuffer, paramFieldPosition);
/*  245 */     if ((paramObject instanceof Number)) {
/*  246 */       return format(((Number)paramObject).doubleValue(), paramStringBuffer, paramFieldPosition);
/*      */     }
/*  248 */     throw new IllegalArgumentException("Cannot format given Object as a Number");
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
/*      */   public final Object parseObject(String paramString, ParsePosition paramParsePosition)
/*      */   {
/*  278 */     return parse(paramString, paramParsePosition);
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
/*      */   public final String format(double paramDouble)
/*      */   {
/*  292 */     String str = fastFormat(paramDouble);
/*  293 */     if (str != null) {
/*  294 */       return str;
/*      */     }
/*      */     
/*  297 */     return format(paramDouble, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   String fastFormat(double paramDouble)
/*      */   {
/*  304 */     return null;
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
/*      */   public final String format(long paramLong)
/*      */   {
/*  317 */     return format(paramLong, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
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
/*      */   public abstract StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract StringBuffer format(long paramLong, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Number parse(String paramString, ParsePosition paramParsePosition);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Number parse(String paramString)
/*      */     throws ParseException
/*      */   {
/*  382 */     ParsePosition localParsePosition = new ParsePosition(0);
/*  383 */     Number localNumber = parse(paramString, localParsePosition);
/*  384 */     if (localParsePosition.index == 0) {
/*  385 */       throw new ParseException("Unparseable number: \"" + paramString + "\"", localParsePosition.errorIndex);
/*      */     }
/*      */     
/*  388 */     return localNumber;
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
/*      */   public boolean isParseIntegerOnly()
/*      */   {
/*  403 */     return this.parseIntegerOnly;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setParseIntegerOnly(boolean paramBoolean)
/*      */   {
/*  414 */     this.parseIntegerOnly = paramBoolean;
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
/*      */   public static final NumberFormat getInstance()
/*      */   {
/*  429 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
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
/*      */   public static NumberFormat getInstance(Locale paramLocale)
/*      */   {
/*  442 */     return getInstance(paramLocale, 0);
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
/*      */   public static final NumberFormat getNumberInstance()
/*      */   {
/*  458 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumberFormat getNumberInstance(Locale paramLocale)
/*      */   {
/*  469 */     return getInstance(paramLocale, 0);
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
/*      */   public static final NumberFormat getIntegerInstance()
/*      */   {
/*  491 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 4);
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
/*      */   public static NumberFormat getIntegerInstance(Locale paramLocale)
/*      */   {
/*  508 */     return getInstance(paramLocale, 4);
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
/*      */   public static final NumberFormat getCurrencyInstance()
/*      */   {
/*  523 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumberFormat getCurrencyInstance(Locale paramLocale)
/*      */   {
/*  533 */     return getInstance(paramLocale, 1);
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
/*      */   public static final NumberFormat getPercentInstance()
/*      */   {
/*  548 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumberFormat getPercentInstance(Locale paramLocale)
/*      */   {
/*  558 */     return getInstance(paramLocale, 2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final NumberFormat getScientificInstance()
/*      */   {
/*  565 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static NumberFormat getScientificInstance(Locale paramLocale)
/*      */   {
/*  574 */     return getInstance(paramLocale, 3);
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
/*      */   public static Locale[] getAvailableLocales()
/*      */   {
/*  592 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(NumberFormatProvider.class);
/*  593 */     return localLocaleServiceProviderPool.getAvailableLocales();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  601 */     return this.maximumIntegerDigits * 37 + this.maxFractionDigits;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  610 */     if (paramObject == null) {
/*  611 */       return false;
/*      */     }
/*  613 */     if (this == paramObject) {
/*  614 */       return true;
/*      */     }
/*  616 */     if (getClass() != paramObject.getClass()) {
/*  617 */       return false;
/*      */     }
/*  619 */     NumberFormat localNumberFormat = (NumberFormat)paramObject;
/*  620 */     return (this.maximumIntegerDigits == localNumberFormat.maximumIntegerDigits) && (this.minimumIntegerDigits == localNumberFormat.minimumIntegerDigits) && (this.maximumFractionDigits == localNumberFormat.maximumFractionDigits) && (this.minimumFractionDigits == localNumberFormat.minimumFractionDigits) && (this.groupingUsed == localNumberFormat.groupingUsed) && (this.parseIntegerOnly == localNumberFormat.parseIntegerOnly);
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
/*      */   public Object clone()
/*      */   {
/*  633 */     NumberFormat localNumberFormat = (NumberFormat)super.clone();
/*  634 */     return localNumberFormat;
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
/*      */   public boolean isGroupingUsed()
/*      */   {
/*  648 */     return this.groupingUsed;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGroupingUsed(boolean paramBoolean)
/*      */   {
/*  659 */     this.groupingUsed = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaximumIntegerDigits()
/*      */   {
/*  670 */     return this.maximumIntegerDigits;
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
/*      */   public void setMaximumIntegerDigits(int paramInt)
/*      */   {
/*  686 */     this.maximumIntegerDigits = Math.max(0, paramInt);
/*  687 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/*  688 */       this.minimumIntegerDigits = this.maximumIntegerDigits;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinimumIntegerDigits()
/*      */   {
/*  700 */     return this.minimumIntegerDigits;
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
/*      */   public void setMinimumIntegerDigits(int paramInt)
/*      */   {
/*  716 */     this.minimumIntegerDigits = Math.max(0, paramInt);
/*  717 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/*  718 */       this.maximumIntegerDigits = this.minimumIntegerDigits;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaximumFractionDigits()
/*      */   {
/*  730 */     return this.maximumFractionDigits;
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
/*      */   public void setMaximumFractionDigits(int paramInt)
/*      */   {
/*  746 */     this.maximumFractionDigits = Math.max(0, paramInt);
/*  747 */     if (this.maximumFractionDigits < this.minimumFractionDigits) {
/*  748 */       this.minimumFractionDigits = this.maximumFractionDigits;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinimumFractionDigits()
/*      */   {
/*  760 */     return this.minimumFractionDigits;
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
/*      */   public void setMinimumFractionDigits(int paramInt)
/*      */   {
/*  776 */     this.minimumFractionDigits = Math.max(0, paramInt);
/*  777 */     if (this.maximumFractionDigits < this.minimumFractionDigits) {
/*  778 */       this.maximumFractionDigits = this.minimumFractionDigits;
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
/*      */   public Currency getCurrency()
/*      */   {
/*  798 */     throw new UnsupportedOperationException();
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
/*      */   public void setCurrency(Currency paramCurrency)
/*      */   {
/*  816 */     throw new UnsupportedOperationException();
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
/*      */   public RoundingMode getRoundingMode()
/*      */   {
/*  833 */     throw new UnsupportedOperationException();
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
/*      */   public void setRoundingMode(RoundingMode paramRoundingMode)
/*      */   {
/*  851 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static NumberFormat getInstance(Locale paramLocale, int paramInt)
/*      */   {
/*  859 */     LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(NumberFormatProvider.class, paramLocale);
/*      */     
/*  861 */     NumberFormat localNumberFormat = getInstance(localLocaleProviderAdapter, paramLocale, paramInt);
/*  862 */     if (localNumberFormat == null) {
/*  863 */       localNumberFormat = getInstance(LocaleProviderAdapter.forJRE(), paramLocale, paramInt);
/*      */     }
/*      */     
/*  866 */     return localNumberFormat;
/*      */   }
/*      */   
/*      */   private static NumberFormat getInstance(LocaleProviderAdapter paramLocaleProviderAdapter, Locale paramLocale, int paramInt)
/*      */   {
/*  871 */     NumberFormatProvider localNumberFormatProvider = paramLocaleProviderAdapter.getNumberFormatProvider();
/*  872 */     NumberFormat localNumberFormat = null;
/*  873 */     switch (paramInt) {
/*      */     case 0: 
/*  875 */       localNumberFormat = localNumberFormatProvider.getNumberInstance(paramLocale);
/*  876 */       break;
/*      */     case 2: 
/*  878 */       localNumberFormat = localNumberFormatProvider.getPercentInstance(paramLocale);
/*  879 */       break;
/*      */     case 1: 
/*  881 */       localNumberFormat = localNumberFormatProvider.getCurrencyInstance(paramLocale);
/*  882 */       break;
/*      */     case 4: 
/*  884 */       localNumberFormat = localNumberFormatProvider.getIntegerInstance(paramLocale);
/*      */     }
/*      */     
/*  887 */     return localNumberFormat;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  913 */     paramObjectInputStream.defaultReadObject();
/*  914 */     if (this.serialVersionOnStream < 1)
/*      */     {
/*  916 */       this.maximumIntegerDigits = this.maxIntegerDigits;
/*  917 */       this.minimumIntegerDigits = this.minIntegerDigits;
/*  918 */       this.maximumFractionDigits = this.maxFractionDigits;
/*  919 */       this.minimumFractionDigits = this.minFractionDigits;
/*      */     }
/*  921 */     if ((this.minimumIntegerDigits > this.maximumIntegerDigits) || (this.minimumFractionDigits > this.maximumFractionDigits) || (this.minimumIntegerDigits < 0) || (this.minimumFractionDigits < 0))
/*      */     {
/*      */ 
/*  924 */       throw new InvalidObjectException("Digit count range invalid");
/*      */     }
/*  926 */     this.serialVersionOnStream = 1;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*  941 */     this.maxIntegerDigits = (this.maximumIntegerDigits > 127 ? Byte.MAX_VALUE : (byte)this.maximumIntegerDigits);
/*      */     
/*  943 */     this.minIntegerDigits = (this.minimumIntegerDigits > 127 ? Byte.MAX_VALUE : (byte)this.minimumIntegerDigits);
/*      */     
/*  945 */     this.maxFractionDigits = (this.maximumFractionDigits > 127 ? Byte.MAX_VALUE : (byte)this.maximumFractionDigits);
/*      */     
/*  947 */     this.minFractionDigits = (this.minimumFractionDigits > 127 ? Byte.MAX_VALUE : (byte)this.minimumFractionDigits);
/*      */     
/*  949 */     paramObjectOutputStream.defaultWriteObject();
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
/*  966 */   private boolean groupingUsed = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  984 */   private byte maxIntegerDigits = 40;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1002 */   private byte minIntegerDigits = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1020 */   private byte maxFractionDigits = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1038 */   private byte minFractionDigits = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1046 */   private boolean parseIntegerOnly = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1059 */   private int maximumIntegerDigits = 40;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1070 */   private int minimumIntegerDigits = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1081 */   private int maximumFractionDigits = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1092 */   private int minimumFractionDigits = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int currentSerialVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1117 */   private int serialVersionOnStream = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final long serialVersionUID = -2308460125733713944L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class Field
/*      */     extends Format.Field
/*      */   {
/*      */     private static final long serialVersionUID = 7494728892700160890L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1141 */     private static final Map<String, Field> instanceMap = new HashMap(11);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected Field(String paramString)
/*      */     {
/* 1150 */       super();
/* 1151 */       if (getClass() == Field.class) {
/* 1152 */         instanceMap.put(paramString, this);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected Object readResolve()
/*      */       throws InvalidObjectException
/*      */     {
/* 1164 */       if (getClass() != Field.class) {
/* 1165 */         throw new InvalidObjectException("subclass didn't correctly implement readResolve");
/*      */       }
/*      */       
/* 1168 */       Object localObject = instanceMap.get(getName());
/* 1169 */       if (localObject != null) {
/* 1170 */         return localObject;
/*      */       }
/* 1172 */       throw new InvalidObjectException("unknown attribute name");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1179 */     public static final Field INTEGER = new Field("integer");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1184 */     public static final Field FRACTION = new Field("fraction");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1189 */     public static final Field EXPONENT = new Field("exponent");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1194 */     public static final Field DECIMAL_SEPARATOR = new Field("decimal separator");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1200 */     public static final Field SIGN = new Field("sign");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1205 */     public static final Field GROUPING_SEPARATOR = new Field("grouping separator");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1211 */     public static final Field EXPONENT_SYMBOL = new Field("exponent symbol");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1217 */     public static final Field PERCENT = new Field("percent");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1222 */     public static final Field PERMILLE = new Field("per mille");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1227 */     public static final Field CURRENCY = new Field("currency");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1232 */     public static final Field EXPONENT_SIGN = new Field("exponent sign");
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/NumberFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */