/*     */ package java.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.spi.DecimalFormatSymbolsProvider;
/*     */ import java.util.Currency;
/*     */ import java.util.Locale;
/*     */ import java.util.Locale.Category;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleResources;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ import sun.util.locale.provider.ResourceBundleBasedAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DecimalFormatSymbols
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private char zeroDigit;
/*     */   private char groupingSeparator;
/*     */   private char decimalSeparator;
/*     */   private char perMill;
/*     */   private char percent;
/*     */   private char digit;
/*     */   private char patternSeparator;
/*     */   private String infinity;
/*     */   private String NaN;
/*     */   private char minusSign;
/*     */   private String currencySymbol;
/*     */   private String intlCurrencySymbol;
/*     */   private char monetarySeparator;
/*     */   private char exponential;
/*     */   private String exponentialSeparator;
/*     */   private Locale locale;
/*     */   private transient Currency currency;
/*     */   static final long serialVersionUID = 5772796243397350300L;
/*     */   private static final int currentSerialVersion = 3;
/*     */   
/*     */   public DecimalFormatSymbols()
/*     */   {
/*  89 */     initialize(Locale.getDefault(Locale.Category.FORMAT));
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
/*     */   public DecimalFormatSymbols(Locale paramLocale)
/*     */   {
/* 113 */     initialize(paramLocale);
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
/*     */   public static Locale[] getAvailableLocales()
/*     */   {
/* 132 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(DecimalFormatSymbolsProvider.class);
/* 133 */     return localLocaleServiceProviderPool.getAvailableLocales();
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
/*     */   public static final DecimalFormatSymbols getInstance()
/*     */   {
/* 152 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT));
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
/*     */   public static final DecimalFormatSymbols getInstance(Locale paramLocale)
/*     */   {
/* 178 */     LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(DecimalFormatSymbolsProvider.class, paramLocale);
/* 179 */     DecimalFormatSymbolsProvider localDecimalFormatSymbolsProvider = localLocaleProviderAdapter.getDecimalFormatSymbolsProvider();
/* 180 */     DecimalFormatSymbols localDecimalFormatSymbols = localDecimalFormatSymbolsProvider.getInstance(paramLocale);
/* 181 */     if (localDecimalFormatSymbols == null) {
/* 182 */       localDecimalFormatSymbolsProvider = LocaleProviderAdapter.forJRE().getDecimalFormatSymbolsProvider();
/* 183 */       localDecimalFormatSymbols = localDecimalFormatSymbolsProvider.getInstance(paramLocale);
/*     */     }
/* 185 */     return localDecimalFormatSymbols;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getZeroDigit()
/*     */   {
/* 194 */     return this.zeroDigit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZeroDigit(char paramChar)
/*     */   {
/* 203 */     this.zeroDigit = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getGroupingSeparator()
/*     */   {
/* 212 */     return this.groupingSeparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGroupingSeparator(char paramChar)
/*     */   {
/* 221 */     this.groupingSeparator = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getDecimalSeparator()
/*     */   {
/* 230 */     return this.decimalSeparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDecimalSeparator(char paramChar)
/*     */   {
/* 239 */     this.decimalSeparator = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getPerMill()
/*     */   {
/* 248 */     return this.perMill;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPerMill(char paramChar)
/*     */   {
/* 257 */     this.perMill = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getPercent()
/*     */   {
/* 266 */     return this.percent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPercent(char paramChar)
/*     */   {
/* 275 */     this.percent = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getDigit()
/*     */   {
/* 284 */     return this.digit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDigit(char paramChar)
/*     */   {
/* 293 */     this.digit = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getPatternSeparator()
/*     */   {
/* 303 */     return this.patternSeparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPatternSeparator(char paramChar)
/*     */   {
/* 313 */     this.patternSeparator = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getInfinity()
/*     */   {
/* 323 */     return this.infinity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInfinity(String paramString)
/*     */   {
/* 333 */     this.infinity = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getNaN()
/*     */   {
/* 343 */     return this.NaN;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNaN(String paramString)
/*     */   {
/* 353 */     this.NaN = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getMinusSign()
/*     */   {
/* 364 */     return this.minusSign;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMinusSign(char paramChar)
/*     */   {
/* 375 */     this.minusSign = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCurrencySymbol()
/*     */   {
/* 387 */     return this.currencySymbol;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCurrencySymbol(String paramString)
/*     */   {
/* 399 */     this.currencySymbol = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getInternationalCurrencySymbol()
/*     */   {
/* 411 */     return this.intlCurrencySymbol;
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
/*     */   public void setInternationalCurrencySymbol(String paramString)
/*     */   {
/* 432 */     this.intlCurrencySymbol = paramString;
/* 433 */     this.currency = null;
/* 434 */     if (paramString != null) {
/*     */       try {
/* 436 */         this.currency = Currency.getInstance(paramString);
/* 437 */         this.currencySymbol = this.currency.getSymbol();
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {}
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
/*     */   public Currency getCurrency()
/*     */   {
/* 452 */     return this.currency;
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
/*     */   public void setCurrency(Currency paramCurrency)
/*     */   {
/* 468 */     if (paramCurrency == null) {
/* 469 */       throw new NullPointerException();
/*     */     }
/* 471 */     this.currency = paramCurrency;
/* 472 */     this.intlCurrencySymbol = paramCurrency.getCurrencyCode();
/* 473 */     this.currencySymbol = paramCurrency.getSymbol(this.locale);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getMonetaryDecimalSeparator()
/*     */   {
/* 485 */     return this.monetarySeparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMonetaryDecimalSeparator(char paramChar)
/*     */   {
/* 496 */     this.monetarySeparator = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   char getExponentialSymbol()
/*     */   {
/* 508 */     return this.exponential;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getExponentSeparator()
/*     */   {
/* 520 */     return this.exponentialSeparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setExponentialSymbol(char paramChar)
/*     */   {
/* 528 */     this.exponential = paramChar;
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
/*     */   public void setExponentSeparator(String paramString)
/*     */   {
/* 542 */     if (paramString == null) {
/* 543 */       throw new NullPointerException();
/*     */     }
/* 545 */     this.exponentialSeparator = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 559 */       return (DecimalFormatSymbols)super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 562 */       throw new InternalError(localCloneNotSupportedException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 571 */     if (paramObject == null) return false;
/* 572 */     if (this == paramObject) return true;
/* 573 */     if (getClass() != paramObject.getClass()) return false;
/* 574 */     DecimalFormatSymbols localDecimalFormatSymbols = (DecimalFormatSymbols)paramObject;
/* 575 */     if ((this.zeroDigit == localDecimalFormatSymbols.zeroDigit) && (this.groupingSeparator == localDecimalFormatSymbols.groupingSeparator) && (this.decimalSeparator == localDecimalFormatSymbols.decimalSeparator) && (this.percent == localDecimalFormatSymbols.percent) && (this.perMill == localDecimalFormatSymbols.perMill) && (this.digit == localDecimalFormatSymbols.digit) && (this.minusSign == localDecimalFormatSymbols.minusSign) && (this.patternSeparator == localDecimalFormatSymbols.patternSeparator))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 583 */       if ((!this.infinity.equals(localDecimalFormatSymbols.infinity)) || 
/* 584 */         (!this.NaN.equals(localDecimalFormatSymbols.NaN)) || 
/* 585 */         (!this.currencySymbol.equals(localDecimalFormatSymbols.currencySymbol)) || 
/* 586 */         (!this.intlCurrencySymbol.equals(localDecimalFormatSymbols.intlCurrencySymbol)) || (this.currency != localDecimalFormatSymbols.currency) || (this.monetarySeparator != localDecimalFormatSymbols.monetarySeparator)) {}
/*     */     }
/*     */     
/*     */ 
/* 590 */     return (this.exponentialSeparator.equals(localDecimalFormatSymbols.exponentialSeparator)) && (this.locale.equals(localDecimalFormatSymbols.locale));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 598 */     int i = this.zeroDigit;
/* 599 */     i = i * 37 + this.groupingSeparator;
/* 600 */     i = i * 37 + this.decimalSeparator;
/* 601 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void initialize(Locale paramLocale)
/*     */   {
/* 608 */     this.locale = paramLocale;
/*     */     
/*     */ 
/* 611 */     LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(DecimalFormatSymbolsProvider.class, paramLocale);
/*     */     
/* 613 */     if (!(localLocaleProviderAdapter instanceof ResourceBundleBasedAdapter)) {
/* 614 */       localLocaleProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
/*     */     }
/* 616 */     Object[] arrayOfObject = localLocaleProviderAdapter.getLocaleResources(paramLocale).getDecimalFormatSymbolsData();
/* 617 */     String[] arrayOfString = (String[])arrayOfObject[0];
/*     */     
/* 619 */     this.decimalSeparator = arrayOfString[0].charAt(0);
/* 620 */     this.groupingSeparator = arrayOfString[1].charAt(0);
/* 621 */     this.patternSeparator = arrayOfString[2].charAt(0);
/* 622 */     this.percent = arrayOfString[3].charAt(0);
/* 623 */     this.zeroDigit = arrayOfString[4].charAt(0);
/* 624 */     this.digit = arrayOfString[5].charAt(0);
/* 625 */     this.minusSign = arrayOfString[6].charAt(0);
/* 626 */     this.exponential = arrayOfString[7].charAt(0);
/* 627 */     this.exponentialSeparator = arrayOfString[7];
/* 628 */     this.perMill = arrayOfString[8].charAt(0);
/* 629 */     this.infinity = arrayOfString[9];
/* 630 */     this.NaN = arrayOfString[10];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 636 */     if (paramLocale.getCountry().length() > 0) {
/*     */       try {
/* 638 */         this.currency = Currency.getInstance(paramLocale);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException1) {}
/*     */     }
/*     */     
/* 643 */     if (this.currency != null) {
/* 644 */       this.intlCurrencySymbol = this.currency.getCurrencyCode();
/* 645 */       if ((arrayOfObject[1] != null) && (arrayOfObject[1] == this.intlCurrencySymbol)) {
/* 646 */         this.currencySymbol = ((String)arrayOfObject[2]);
/*     */       } else {
/* 648 */         this.currencySymbol = this.currency.getSymbol(paramLocale);
/* 649 */         arrayOfObject[1] = this.intlCurrencySymbol;
/* 650 */         arrayOfObject[2] = this.currencySymbol;
/*     */       }
/*     */     }
/*     */     else {
/* 654 */       this.intlCurrencySymbol = "XXX";
/*     */       try {
/* 656 */         this.currency = Currency.getInstance(this.intlCurrencySymbol);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException2) {}
/* 659 */       this.currencySymbol = "¤";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 664 */     this.monetarySeparator = this.decimalSeparator;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 686 */     paramObjectInputStream.defaultReadObject();
/* 687 */     if (this.serialVersionOnStream < 1)
/*     */     {
/*     */ 
/* 690 */       this.monetarySeparator = this.decimalSeparator;
/* 691 */       this.exponential = 'E';
/*     */     }
/* 693 */     if (this.serialVersionOnStream < 2)
/*     */     {
/* 695 */       this.locale = Locale.ROOT;
/*     */     }
/* 697 */     if (this.serialVersionOnStream < 3)
/*     */     {
/* 699 */       this.exponentialSeparator = Character.toString(this.exponential);
/*     */     }
/* 701 */     this.serialVersionOnStream = 3;
/*     */     
/* 703 */     if (this.intlCurrencySymbol != null) {
/*     */       try {
/* 705 */         this.currency = Currency.getInstance(this.intlCurrencySymbol);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 877 */   private int serialVersionOnStream = 3;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/DecimalFormatSymbols.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */