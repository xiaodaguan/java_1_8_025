/*     */ package java.time.format;
/*     */ 
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Locale.Category;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DecimalStyle
/*     */ {
/*  92 */   public static final DecimalStyle STANDARD = new DecimalStyle('0', '+', '-', '.');
/*     */   
/*     */ 
/*     */ 
/*  96 */   private static final ConcurrentMap<Locale, DecimalStyle> CACHE = new ConcurrentHashMap(16, 0.75F, 2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final char zeroDigit;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final char positiveSign;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final char negativeSign;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final char decimalSeparator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Set<Locale> getAvailableLocales()
/*     */   {
/* 124 */     Locale[] arrayOfLocale = DecimalFormatSymbols.getAvailableLocales();
/* 125 */     HashSet localHashSet = new HashSet(arrayOfLocale.length);
/* 126 */     Collections.addAll(localHashSet, arrayOfLocale);
/* 127 */     return localHashSet;
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
/*     */   public static DecimalStyle ofDefaultLocale()
/*     */   {
/* 144 */     return of(Locale.getDefault(Locale.Category.FORMAT));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DecimalStyle of(Locale paramLocale)
/*     */   {
/* 156 */     Objects.requireNonNull(paramLocale, "locale");
/* 157 */     DecimalStyle localDecimalStyle = (DecimalStyle)CACHE.get(paramLocale);
/* 158 */     if (localDecimalStyle == null) {
/* 159 */       localDecimalStyle = create(paramLocale);
/* 160 */       CACHE.putIfAbsent(paramLocale, localDecimalStyle);
/* 161 */       localDecimalStyle = (DecimalStyle)CACHE.get(paramLocale);
/*     */     }
/* 163 */     return localDecimalStyle;
/*     */   }
/*     */   
/*     */   private static DecimalStyle create(Locale paramLocale) {
/* 167 */     DecimalFormatSymbols localDecimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 168 */     char c1 = localDecimalFormatSymbols.getZeroDigit();
/* 169 */     char c2 = '+';
/* 170 */     char c3 = localDecimalFormatSymbols.getMinusSign();
/* 171 */     char c4 = localDecimalFormatSymbols.getDecimalSeparator();
/* 172 */     if ((c1 == '0') && (c3 == '-') && (c4 == '.')) {
/* 173 */       return STANDARD;
/*     */     }
/* 175 */     return new DecimalStyle(c1, c2, c3, c4);
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
/*     */   private DecimalStyle(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
/*     */   {
/* 188 */     this.zeroDigit = paramChar1;
/* 189 */     this.positiveSign = paramChar2;
/* 190 */     this.negativeSign = paramChar3;
/* 191 */     this.decimalSeparator = paramChar4;
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
/*     */   public char getZeroDigit()
/*     */   {
/* 204 */     return this.zeroDigit;
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
/*     */   public DecimalStyle withZeroDigit(char paramChar)
/*     */   {
/* 218 */     if (paramChar == this.zeroDigit) {
/* 219 */       return this;
/*     */     }
/* 221 */     return new DecimalStyle(paramChar, this.positiveSign, this.negativeSign, this.decimalSeparator);
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
/*     */   public char getPositiveSign()
/*     */   {
/* 234 */     return this.positiveSign;
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
/*     */   public DecimalStyle withPositiveSign(char paramChar)
/*     */   {
/* 247 */     if (paramChar == this.positiveSign) {
/* 248 */       return this;
/*     */     }
/* 250 */     return new DecimalStyle(this.zeroDigit, paramChar, this.negativeSign, this.decimalSeparator);
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
/*     */   public char getNegativeSign()
/*     */   {
/* 263 */     return this.negativeSign;
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
/*     */   public DecimalStyle withNegativeSign(char paramChar)
/*     */   {
/* 276 */     if (paramChar == this.negativeSign) {
/* 277 */       return this;
/*     */     }
/* 279 */     return new DecimalStyle(this.zeroDigit, this.positiveSign, paramChar, this.decimalSeparator);
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
/*     */   public char getDecimalSeparator()
/*     */   {
/* 292 */     return this.decimalSeparator;
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
/*     */   public DecimalStyle withDecimalSeparator(char paramChar)
/*     */   {
/* 305 */     if (paramChar == this.decimalSeparator) {
/* 306 */       return this;
/*     */     }
/* 308 */     return new DecimalStyle(this.zeroDigit, this.positiveSign, this.negativeSign, paramChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int convertToDigit(char paramChar)
/*     */   {
/* 319 */     int i = paramChar - this.zeroDigit;
/* 320 */     return (i >= 0) && (i <= 9) ? i : -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String convertNumberToI18N(String paramString)
/*     */   {
/* 330 */     if (this.zeroDigit == '0') {
/* 331 */       return paramString;
/*     */     }
/* 333 */     int i = this.zeroDigit - '0';
/* 334 */     char[] arrayOfChar = paramString.toCharArray();
/* 335 */     for (int j = 0; j < arrayOfChar.length; j++) {
/* 336 */       arrayOfChar[j] = ((char)(arrayOfChar[j] + i));
/*     */     }
/* 338 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 350 */     if (this == paramObject) {
/* 351 */       return true;
/*     */     }
/* 353 */     if ((paramObject instanceof DecimalStyle)) {
/* 354 */       DecimalStyle localDecimalStyle = (DecimalStyle)paramObject;
/* 355 */       return (this.zeroDigit == localDecimalStyle.zeroDigit) && (this.positiveSign == localDecimalStyle.positiveSign) && (this.negativeSign == localDecimalStyle.negativeSign) && (this.decimalSeparator == localDecimalStyle.decimalSeparator);
/*     */     }
/*     */     
/* 358 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 368 */     return this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 379 */     return "DecimalStyle[" + this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator + "]";
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/format/DecimalStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */