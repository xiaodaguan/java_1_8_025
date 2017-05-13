/*     */ package java.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool.LocalizedObjectGetter;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ import sun.util.logging.PlatformLogger.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Currency
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -158308464356906721L;
/*     */   private final String currencyCode;
/*     */   private final transient int defaultFractionDigits;
/*     */   private final transient int numericCode;
/* 123 */   private static ConcurrentMap<String, Currency> instances = new ConcurrentHashMap(7);
/*     */   
/*     */ 
/*     */   private static HashSet<Currency> available;
/*     */   
/*     */ 
/*     */   static int formatVersion;
/*     */   
/*     */ 
/*     */   static int dataVersion;
/*     */   
/*     */ 
/*     */   static int[] mainTable;
/*     */   
/*     */ 
/*     */   static long[] scCutOverTimes;
/*     */   
/*     */ 
/*     */   static String[] scOldCurrencies;
/*     */   
/*     */ 
/*     */   static String[] scNewCurrencies;
/*     */   
/*     */ 
/*     */   static int[] scOldCurrenciesDFD;
/*     */   
/*     */ 
/*     */   static int[] scNewCurrenciesDFD;
/*     */   
/*     */ 
/*     */   static int[] scOldCurrenciesNumericCode;
/*     */   
/*     */ 
/*     */   static int[] scNewCurrenciesNumericCode;
/*     */   
/*     */ 
/*     */   static String otherCurrencies;
/*     */   
/*     */ 
/*     */   static int[] otherCurrenciesDFD;
/*     */   
/*     */ 
/*     */   static int[] otherCurrenciesNumericCode;
/*     */   
/*     */ 
/*     */   private static final int MAGIC_NUMBER = 1131770436;
/*     */   
/*     */ 
/*     */   private static final int A_TO_Z = 26;
/*     */   
/*     */ 
/*     */   private static final int INVALID_COUNTRY_ENTRY = 127;
/*     */   
/*     */ 
/*     */   private static final int COUNTRY_WITHOUT_CURRENCY_ENTRY = 128;
/*     */   
/*     */ 
/*     */   private static final int SIMPLE_CASE_COUNTRY_MASK = 0;
/*     */   
/*     */ 
/*     */   private static final int SIMPLE_CASE_COUNTRY_FINAL_CHAR_MASK = 31;
/*     */   
/*     */ 
/*     */   private static final int SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_MASK = 96;
/*     */   
/*     */ 
/*     */   private static final int SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_SHIFT = 5;
/*     */   
/*     */   private static final int SPECIAL_CASE_COUNTRY_MASK = 128;
/*     */   
/*     */   private static final int SPECIAL_CASE_COUNTRY_INDEX_MASK = 31;
/*     */   
/*     */   private static final int SPECIAL_CASE_COUNTRY_INDEX_DELTA = 1;
/*     */   
/*     */   private static final int COUNTRY_TYPE_MASK = 128;
/*     */   
/*     */   private static final int NUMERIC_CODE_MASK = 261888;
/*     */   
/*     */   private static final int NUMERIC_CODE_SHIFT = 8;
/*     */   
/*     */   private static final int VALID_FORMAT_VERSION = 1;
/*     */   
/*     */   private static final int SYMBOL = 0;
/*     */   
/*     */   private static final int DISPLAYNAME = 1;
/*     */   
/*     */ 
/*     */   static
/*     */   {
/* 212 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 215 */         String str1 = System.getProperty("java.home");
/*     */         Object localObject1;
/* 217 */         Object localObject2; try { String str2 = str1 + File.separator + "lib" + File.separator + "currency.data";
/*     */           
/* 219 */           localObject1 = new DataInputStream(new BufferedInputStream(new FileInputStream(str2)));localObject2 = null;
/*     */           try
/*     */           {
/* 222 */             if (((DataInputStream)localObject1).readInt() != 1131770436) {
/* 223 */               throw new InternalError("Currency data is possibly corrupted");
/*     */             }
/* 225 */             Currency.formatVersion = ((DataInputStream)localObject1).readInt();
/* 226 */             if (Currency.formatVersion != 1) {
/* 227 */               throw new InternalError("Currency data format is incorrect");
/*     */             }
/* 229 */             Currency.dataVersion = ((DataInputStream)localObject1).readInt();
/* 230 */             Currency.mainTable = Currency.readIntArray((DataInputStream)localObject1, 676);
/* 231 */             int i = ((DataInputStream)localObject1).readInt();
/* 232 */             Currency.scCutOverTimes = Currency.readLongArray((DataInputStream)localObject1, i);
/* 233 */             Currency.scOldCurrencies = Currency.readStringArray((DataInputStream)localObject1, i);
/* 234 */             Currency.scNewCurrencies = Currency.readStringArray((DataInputStream)localObject1, i);
/* 235 */             Currency.scOldCurrenciesDFD = Currency.readIntArray((DataInputStream)localObject1, i);
/* 236 */             Currency.scNewCurrenciesDFD = Currency.readIntArray((DataInputStream)localObject1, i);
/* 237 */             Currency.scOldCurrenciesNumericCode = Currency.readIntArray((DataInputStream)localObject1, i);
/* 238 */             Currency.scNewCurrenciesNumericCode = Currency.readIntArray((DataInputStream)localObject1, i);
/* 239 */             int j = ((DataInputStream)localObject1).readInt();
/* 240 */             Currency.otherCurrencies = ((DataInputStream)localObject1).readUTF();
/* 241 */             Currency.otherCurrenciesDFD = Currency.readIntArray((DataInputStream)localObject1, j);
/* 242 */             Currency.otherCurrenciesNumericCode = Currency.readIntArray((DataInputStream)localObject1, j);
/*     */           }
/*     */           catch (Throwable localThrowable2)
/*     */           {
/* 219 */             localObject2 = localThrowable2;throw localThrowable2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           finally
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 243 */             if (localObject1 != null) if (localObject2 != null) try { ((DataInputStream)localObject1).close(); } catch (Throwable localThrowable5) { ((Throwable)localObject2).addSuppressed(localThrowable5); } else ((DataInputStream)localObject1).close();
/*     */           }
/* 245 */         } catch (IOException localIOException1) { throw new InternalError(localIOException1);
/*     */         }
/*     */         
/*     */ 
/* 249 */         String str3 = System.getProperty("java.util.currency.data");
/* 250 */         if (str3 == null) {
/* 251 */           str3 = str1 + File.separator + "lib" + File.separator + "currency.properties";
/*     */         }
/*     */         try
/*     */         {
/* 255 */           localObject1 = new File(str3);
/* 256 */           if (((File)localObject1).exists()) {
/* 257 */             localObject2 = new Properties();
/* 258 */             Object localObject3 = new FileReader((File)localObject1);localObject4 = null;
/* 259 */             try { ((Properties)localObject2).load((Reader)localObject3);
/*     */             }
/*     */             catch (Throwable localThrowable4)
/*     */             {
/* 258 */               localObject4 = localThrowable4;throw localThrowable4;
/*     */             } finally {
/* 260 */               if (localObject3 != null) if (localObject4 != null) try { ((FileReader)localObject3).close(); } catch (Throwable localThrowable6) { ((Throwable)localObject4).addSuppressed(localThrowable6); } else ((FileReader)localObject3).close(); }
/* 261 */             localObject3 = ((Properties)localObject2).stringPropertyNames();
/*     */             
/* 263 */             localObject4 = Pattern.compile("([A-Z]{3})\\s*,\\s*(\\d{3})\\s*,\\s*([0-3])\\s*,?\\s*(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})?");
/*     */             
/*     */ 
/* 266 */             for (String str4 : (Set)localObject3)
/* 267 */               Currency.replaceCurrencyData((Pattern)localObject4, str4
/* 268 */                 .toUpperCase(Locale.ROOT), ((Properties)localObject2)
/* 269 */                 .getProperty(str4).toUpperCase(Locale.ROOT));
/*     */           }
/*     */         } catch (IOException localIOException2) {
/*     */           Object localObject4;
/* 273 */           Currency.info("currency.properties is ignored because of an IOException", localIOException2);
/*     */         }
/* 275 */         return null;
/*     */       }
/*     */     });
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
/*     */   private Currency(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 293 */     this.currencyCode = paramString;
/* 294 */     this.defaultFractionDigits = paramInt1;
/* 295 */     this.numericCode = paramInt2;
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
/*     */   public static Currency getInstance(String paramString)
/*     */   {
/* 308 */     return getInstance(paramString, Integer.MIN_VALUE, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Currency getInstance(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 316 */     Currency localCurrency1 = (Currency)instances.get(paramString);
/* 317 */     if (localCurrency1 != null) {
/* 318 */       return localCurrency1;
/*     */     }
/*     */     
/* 321 */     if (paramInt1 == Integer.MIN_VALUE)
/*     */     {
/*     */ 
/*     */ 
/* 325 */       if (paramString.length() != 3) {
/* 326 */         throw new IllegalArgumentException();
/*     */       }
/* 328 */       char c1 = paramString.charAt(0);
/* 329 */       char c2 = paramString.charAt(1);
/* 330 */       int i = getMainTableEntry(c1, c2);
/* 331 */       if (((i & 0x80) == 0) && (i != 127))
/*     */       {
/* 333 */         if (paramString.charAt(2) - 'A' == (i & 0x1F)) {
/* 334 */           paramInt1 = (i & 0x60) >> 5;
/* 335 */           paramInt2 = (i & 0x3FF00) >> 8;
/*     */           break label175;
/*     */         } }
/* 338 */       if (paramString.charAt(2) == '-') {
/* 339 */         throw new IllegalArgumentException();
/*     */       }
/* 341 */       int j = otherCurrencies.indexOf(paramString);
/* 342 */       if (j == -1) {
/* 343 */         throw new IllegalArgumentException();
/*     */       }
/* 345 */       paramInt1 = otherCurrenciesDFD[(j / 4)];
/* 346 */       paramInt2 = otherCurrenciesNumericCode[(j / 4)];
/*     */     }
/*     */     
/*     */     label175:
/* 350 */     Currency localCurrency2 = new Currency(paramString, paramInt1, paramInt2);
/*     */     
/* 352 */     localCurrency1 = (Currency)instances.putIfAbsent(paramString, localCurrency2);
/* 353 */     return localCurrency1 != null ? localCurrency1 : localCurrency2;
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
/*     */   public static Currency getInstance(Locale paramLocale)
/*     */   {
/* 378 */     String str = paramLocale.getCountry();
/* 379 */     if (str == null) {
/* 380 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 383 */     if (str.length() != 2) {
/* 384 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 387 */     char c1 = str.charAt(0);
/* 388 */     char c2 = str.charAt(1);
/* 389 */     int i = getMainTableEntry(c1, c2);
/* 390 */     if (((i & 0x80) == 0) && (i != 127))
/*     */     {
/* 392 */       char c3 = (char)((i & 0x1F) + 65);
/* 393 */       int k = (i & 0x60) >> 5;
/* 394 */       int m = (i & 0x3FF00) >> 8;
/* 395 */       StringBuilder localStringBuilder = new StringBuilder(str);
/* 396 */       localStringBuilder.append(c3);
/* 397 */       return getInstance(localStringBuilder.toString(), k, m);
/*     */     }
/*     */     
/* 400 */     if (i == 127) {
/* 401 */       throw new IllegalArgumentException();
/*     */     }
/* 403 */     if (i == 128) {
/* 404 */       return null;
/*     */     }
/* 406 */     int j = (i & 0x1F) - 1;
/* 407 */     if ((scCutOverTimes[j] == Long.MAX_VALUE) || (System.currentTimeMillis() < scCutOverTimes[j])) {
/* 408 */       return getInstance(scOldCurrencies[j], scOldCurrenciesDFD[j], scOldCurrenciesNumericCode[j]);
/*     */     }
/*     */     
/* 411 */     return getInstance(scNewCurrencies[j], scNewCurrenciesDFD[j], scNewCurrenciesNumericCode[j]);
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
/*     */   public static Set<Currency> getAvailableCurrencies()
/*     */   {
/* 429 */     synchronized (Currency.class) {
/* 430 */       if (available == null) {
/* 431 */         available = new HashSet(256);
/*     */         
/*     */ 
/* 434 */         for (char c1 = 'A'; c1 <= 'Z'; c1 = (char)(c1 + '\001')) {
/* 435 */           for (char c2 = 'A'; c2 <= 'Z'; c2 = (char)(c2 + '\001')) {
/* 436 */             int i = getMainTableEntry(c1, c2);
/* 437 */             if (((i & 0x80) == 0) && (i != 127))
/*     */             {
/* 439 */               char c3 = (char)((i & 0x1F) + 65);
/* 440 */               int j = (i & 0x60) >> 5;
/* 441 */               int k = (i & 0x3FF00) >> 8;
/* 442 */               StringBuilder localStringBuilder = new StringBuilder();
/* 443 */               localStringBuilder.append(c1);
/* 444 */               localStringBuilder.append(c2);
/* 445 */               localStringBuilder.append(c3);
/* 446 */               available.add(getInstance(localStringBuilder.toString(), j, k));
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 452 */         StringTokenizer localStringTokenizer = new StringTokenizer(otherCurrencies, "-");
/* 453 */         while (localStringTokenizer.hasMoreElements()) {
/* 454 */           available.add(getInstance((String)localStringTokenizer.nextElement()));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 460 */     ??? = (Set)available.clone();
/* 461 */     return (Set<Currency>)???;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCurrencyCode()
/*     */   {
/* 470 */     return this.currencyCode;
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
/*     */   public String getSymbol()
/*     */   {
/* 488 */     return getSymbol(Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public String getSymbol(Locale paramLocale)
/*     */   {
/* 504 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(CurrencyNameProvider.class);
/* 505 */     String str = (String)localLocaleServiceProviderPool.getLocalizedObject(
/* 506 */       CurrencyNameGetter.INSTANCE, paramLocale, this.currencyCode, new Object[] {
/* 507 */       Integer.valueOf(0) });
/* 508 */     if (str != null) {
/* 509 */       return str;
/*     */     }
/*     */     
/*     */ 
/* 513 */     return this.currencyCode;
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
/*     */   public int getDefaultFractionDigits()
/*     */   {
/* 526 */     return this.defaultFractionDigits;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNumericCode()
/*     */   {
/* 536 */     return this.numericCode;
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
/*     */   public String getDisplayName()
/*     */   {
/* 554 */     return getDisplayName(Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public String getDisplayName(Locale paramLocale)
/*     */   {
/* 570 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(CurrencyNameProvider.class);
/* 571 */     String str = (String)localLocaleServiceProviderPool.getLocalizedObject(
/* 572 */       CurrencyNameGetter.INSTANCE, paramLocale, this.currencyCode, new Object[] {
/* 573 */       Integer.valueOf(1) });
/* 574 */     if (str != null) {
/* 575 */       return str;
/*     */     }
/*     */     
/*     */ 
/* 579 */     return this.currencyCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 589 */     return this.currencyCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Object readResolve()
/*     */   {
/* 596 */     return getInstance(this.currencyCode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int getMainTableEntry(char paramChar1, char paramChar2)
/*     */   {
/* 604 */     if ((paramChar1 < 'A') || (paramChar1 > 'Z') || (paramChar2 < 'A') || (paramChar2 > 'Z')) {
/* 605 */       throw new IllegalArgumentException();
/*     */     }
/* 607 */     return mainTable[((paramChar1 - 'A') * 26 + (paramChar2 - 'A'))];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void setMainTableEntry(char paramChar1, char paramChar2, int paramInt)
/*     */   {
/* 615 */     if ((paramChar1 < 'A') || (paramChar1 > 'Z') || (paramChar2 < 'A') || (paramChar2 > 'Z')) {
/* 616 */       throw new IllegalArgumentException();
/*     */     }
/* 618 */     mainTable[((paramChar1 - 'A') * 26 + (paramChar2 - 'A'))] = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class CurrencyNameGetter
/*     */     implements LocaleServiceProviderPool.LocalizedObjectGetter<CurrencyNameProvider, String>
/*     */   {
/* 628 */     private static final CurrencyNameGetter INSTANCE = new CurrencyNameGetter();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getObject(CurrencyNameProvider paramCurrencyNameProvider, Locale paramLocale, String paramString, Object... paramVarArgs)
/*     */     {
/* 635 */       assert (paramVarArgs.length == 1);
/* 636 */       int i = ((Integer)paramVarArgs[0]).intValue();
/*     */       
/* 638 */       switch (i) {
/*     */       case 0: 
/* 640 */         return paramCurrencyNameProvider.getSymbol(paramString, paramLocale);
/*     */       case 1: 
/* 642 */         return paramCurrencyNameProvider.getDisplayName(paramString, paramLocale);
/*     */       }
/* 644 */       if (!$assertionsDisabled) { throw new AssertionError();
/*     */       }
/*     */       
/* 647 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static int[] readIntArray(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 652 */     int[] arrayOfInt = new int[paramInt];
/* 653 */     for (int i = 0; i < paramInt; i++) {
/* 654 */       arrayOfInt[i] = paramDataInputStream.readInt();
/*     */     }
/*     */     
/* 657 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   private static long[] readLongArray(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 661 */     long[] arrayOfLong = new long[paramInt];
/* 662 */     for (int i = 0; i < paramInt; i++) {
/* 663 */       arrayOfLong[i] = paramDataInputStream.readLong();
/*     */     }
/*     */     
/* 666 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   private static String[] readStringArray(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 670 */     String[] arrayOfString = new String[paramInt];
/* 671 */     for (int i = 0; i < paramInt; i++) {
/* 672 */       arrayOfString[i] = paramDataInputStream.readUTF();
/*     */     }
/*     */     
/* 675 */     return arrayOfString;
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
/*     */   private static void replaceCurrencyData(Pattern paramPattern, String paramString1, String paramString2)
/*     */   {
/* 694 */     if (paramString1.length() != 2)
/*     */     {
/* 696 */       info("currency.properties entry for " + paramString1 + " is ignored because of the invalid country code.", null);
/*     */       
/* 698 */       return;
/*     */     }
/*     */     
/* 701 */     Matcher localMatcher = paramPattern.matcher(paramString2);
/* 702 */     if ((!localMatcher.find()) || ((localMatcher.group(4) == null) && (countOccurrences(paramString2, ',') >= 3)))
/*     */     {
/*     */ 
/* 705 */       info("currency.properties entry for " + paramString1 + " ignored because the value format is not recognized.", null);
/*     */       
/* 707 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 711 */       if ((localMatcher.group(4) != null) && (!isPastCutoverDate(localMatcher.group(4)))) {
/* 712 */         info("currency.properties entry for " + paramString1 + " ignored since cutover date has not passed :" + paramString2, null);
/*     */         
/* 714 */         return;
/*     */       }
/*     */     } catch (ParseException localParseException) {
/* 717 */       info(
/* 718 */         "currency.properties entry for " + paramString1 + " ignored since exception encountered :" + localParseException.getMessage(), null);
/* 719 */       return;
/*     */     }
/*     */     
/* 722 */     String str = localMatcher.group(1);
/* 723 */     int i = Integer.parseInt(localMatcher.group(2));
/* 724 */     int j = Integer.parseInt(localMatcher.group(3));
/* 725 */     int k = i << 8;
/*     */     
/*     */ 
/* 728 */     for (int m = 0; m < scOldCurrencies.length; m++) {
/* 729 */       if (scOldCurrencies[m].equals(str)) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 734 */     if (m == scOldCurrencies.length)
/*     */     {
/*     */ 
/* 737 */       k = k | j << 5 | str.charAt(2) - 'A';
/*     */     }
/*     */     else {
/* 740 */       k |= 0x80 | m + 1;
/*     */     }
/*     */     
/* 743 */     setMainTableEntry(paramString1.charAt(0), paramString1.charAt(1), k);
/*     */   }
/*     */   
/*     */   private static boolean isPastCutoverDate(String paramString) throws ParseException {
/* 747 */     SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);
/* 748 */     localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
/* 749 */     localSimpleDateFormat.setLenient(false);
/* 750 */     long l = localSimpleDateFormat.parse(paramString.trim()).getTime();
/* 751 */     return System.currentTimeMillis() > l;
/*     */   }
/*     */   
/*     */   private static int countOccurrences(String paramString, char paramChar)
/*     */   {
/* 756 */     int i = 0;
/* 757 */     for (char c : paramString.toCharArray()) {
/* 758 */       if (c == paramChar) {
/* 759 */         i++;
/*     */       }
/*     */     }
/* 762 */     return i;
/*     */   }
/*     */   
/*     */   private static void info(String paramString, Throwable paramThrowable) {
/* 766 */     PlatformLogger localPlatformLogger = PlatformLogger.getLogger("java.util.Currency");
/* 767 */     if (localPlatformLogger.isLoggable(PlatformLogger.Level.INFO)) {
/* 768 */       if (paramThrowable != null) {
/* 769 */         localPlatformLogger.info(paramString, paramThrowable);
/*     */       } else {
/* 771 */         localPlatformLogger.info(paramString);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Currency.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */