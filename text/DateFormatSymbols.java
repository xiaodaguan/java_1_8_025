/*     */ package java.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Locale.Category;
/*     */ import java.util.Objects;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ import sun.util.locale.provider.ResourceBundleBasedAdapter;
/*     */ import sun.util.locale.provider.TimeZoneNameUtility;
/*     */ import sun.util.resources.LocaleData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateFormatSymbols
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   public DateFormatSymbols()
/*     */   {
/* 126 */     initializeData(Locale.getDefault(Locale.Category.FORMAT));
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
/*     */   public DateFormatSymbols(Locale paramLocale)
/*     */   {
/* 146 */     initializeData(paramLocale);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 154 */   String[] eras = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 162 */   String[] months = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */   String[] shortMonths = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 180 */   String[] weekdays = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 189 */   String[] shortWeekdays = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 197 */   String[] ampms = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 224 */   String[][] zoneStrings = (String[][])null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 229 */   transient boolean isZoneStringsSet = false;
/*     */   
/*     */   static final String patternChars = "GyMdkHmsSEDFwWahKzZYuXL";
/*     */   
/*     */   static final int PATTERN_ERA = 0;
/*     */   
/*     */   static final int PATTERN_YEAR = 1;
/*     */   
/*     */   static final int PATTERN_MONTH = 2;
/*     */   
/*     */   static final int PATTERN_DAY_OF_MONTH = 3;
/*     */   
/*     */   static final int PATTERN_HOUR_OF_DAY1 = 4;
/*     */   
/*     */   static final int PATTERN_HOUR_OF_DAY0 = 5;
/*     */   
/*     */   static final int PATTERN_MINUTE = 6;
/*     */   
/*     */   static final int PATTERN_SECOND = 7;
/*     */   
/*     */   static final int PATTERN_MILLISECOND = 8;
/*     */   
/*     */   static final int PATTERN_DAY_OF_WEEK = 9;
/*     */   
/*     */   static final int PATTERN_DAY_OF_YEAR = 10;
/*     */   
/*     */   static final int PATTERN_DAY_OF_WEEK_IN_MONTH = 11;
/*     */   
/*     */   static final int PATTERN_WEEK_OF_YEAR = 12;
/*     */   
/*     */   static final int PATTERN_WEEK_OF_MONTH = 13;
/*     */   
/*     */   static final int PATTERN_AM_PM = 14;
/*     */   
/*     */   static final int PATTERN_HOUR1 = 15;
/*     */   static final int PATTERN_HOUR0 = 16;
/*     */   static final int PATTERN_ZONE_NAME = 17;
/*     */   static final int PATTERN_ZONE_VALUE = 18;
/*     */   static final int PATTERN_WEEK_YEAR = 19;
/*     */   static final int PATTERN_ISO_DAY_OF_WEEK = 20;
/*     */   static final int PATTERN_ISO_ZONE = 21;
/*     */   static final int PATTERN_MONTH_STANDALONE = 22;
/* 271 */   String localPatternChars = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 279 */   Locale locale = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final long serialVersionUID = -5987973545549424702L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int millisPerHour = 3600000;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Locale[] getAvailableLocales()
/*     */   {
/* 300 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(DateFormatSymbolsProvider.class);
/* 301 */     return localLocaleServiceProviderPool.getAvailableLocales();
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
/*     */   public static final DateFormatSymbols getInstance()
/*     */   {
/* 319 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT));
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
/*     */   public static final DateFormatSymbols getInstance(Locale paramLocale)
/*     */   {
/* 335 */     DateFormatSymbols localDateFormatSymbols = getProviderInstance(paramLocale);
/* 336 */     if (localDateFormatSymbols != null) {
/* 337 */       return localDateFormatSymbols;
/*     */     }
/* 339 */     throw new RuntimeException("DateFormatSymbols instance creation failed.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final DateFormatSymbols getInstanceRef(Locale paramLocale)
/*     */   {
/* 349 */     DateFormatSymbols localDateFormatSymbols = getProviderInstance(paramLocale);
/* 350 */     if (localDateFormatSymbols != null) {
/* 351 */       return localDateFormatSymbols;
/*     */     }
/* 353 */     throw new RuntimeException("DateFormatSymbols instance creation failed.");
/*     */   }
/*     */   
/*     */   private static DateFormatSymbols getProviderInstance(Locale paramLocale) {
/* 357 */     LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(DateFormatSymbolsProvider.class, paramLocale);
/* 358 */     DateFormatSymbolsProvider localDateFormatSymbolsProvider = localLocaleProviderAdapter.getDateFormatSymbolsProvider();
/* 359 */     DateFormatSymbols localDateFormatSymbols = localDateFormatSymbolsProvider.getInstance(paramLocale);
/* 360 */     if (localDateFormatSymbols == null) {
/* 361 */       localDateFormatSymbolsProvider = LocaleProviderAdapter.forJRE().getDateFormatSymbolsProvider();
/* 362 */       localDateFormatSymbols = localDateFormatSymbolsProvider.getInstance(paramLocale);
/*     */     }
/* 364 */     return localDateFormatSymbols;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getEras()
/*     */   {
/* 372 */     return (String[])Arrays.copyOf(this.eras, this.eras.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEras(String[] paramArrayOfString)
/*     */   {
/* 380 */     this.eras = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/* 381 */     this.cachedHashCode = 0;
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
/*     */   public String[] getMonths()
/*     */   {
/* 400 */     return (String[])Arrays.copyOf(this.months, this.months.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMonths(String[] paramArrayOfString)
/*     */   {
/* 408 */     this.months = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/* 409 */     this.cachedHashCode = 0;
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
/*     */   public String[] getShortMonths()
/*     */   {
/* 428 */     return (String[])Arrays.copyOf(this.shortMonths, this.shortMonths.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setShortMonths(String[] paramArrayOfString)
/*     */   {
/* 436 */     this.shortMonths = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/* 437 */     this.cachedHashCode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getWeekdays()
/*     */   {
/* 446 */     return (String[])Arrays.copyOf(this.weekdays, this.weekdays.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWeekdays(String[] paramArrayOfString)
/*     */   {
/* 456 */     this.weekdays = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/* 457 */     this.cachedHashCode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getShortWeekdays()
/*     */   {
/* 466 */     return (String[])Arrays.copyOf(this.shortWeekdays, this.shortWeekdays.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setShortWeekdays(String[] paramArrayOfString)
/*     */   {
/* 476 */     this.shortWeekdays = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/* 477 */     this.cachedHashCode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getAmPmStrings()
/*     */   {
/* 485 */     return (String[])Arrays.copyOf(this.ampms, this.ampms.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAmPmStrings(String[] paramArrayOfString)
/*     */   {
/* 493 */     this.ampms = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/* 494 */     this.cachedHashCode = 0;
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
/*     */   public String[][] getZoneStrings()
/*     */   {
/* 536 */     return getZoneStringsImpl(true);
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
/*     */   public void setZoneStrings(String[][] paramArrayOfString)
/*     */   {
/* 569 */     String[][] arrayOfString = new String[paramArrayOfString.length][];
/* 570 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 571 */       int j = paramArrayOfString[i].length;
/* 572 */       if (j < 5) {
/* 573 */         throw new IllegalArgumentException();
/*     */       }
/* 575 */       arrayOfString[i] = ((String[])Arrays.copyOf(paramArrayOfString[i], j));
/*     */     }
/* 577 */     this.zoneStrings = arrayOfString;
/* 578 */     this.isZoneStringsSet = true;
/* 579 */     this.cachedHashCode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLocalPatternChars()
/*     */   {
/* 587 */     return this.localPatternChars;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocalPatternChars(String paramString)
/*     */   {
/* 597 */     this.localPatternChars = paramString.toString();
/* 598 */     this.cachedHashCode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 608 */       DateFormatSymbols localDateFormatSymbols = (DateFormatSymbols)super.clone();
/* 609 */       copyMembers(this, localDateFormatSymbols);
/* 610 */       return localDateFormatSymbols;
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 612 */       throw new InternalError(localCloneNotSupportedException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 622 */     int i = this.cachedHashCode;
/* 623 */     if (i == 0) {
/* 624 */       i = 5;
/* 625 */       i = 11 * i + Arrays.hashCode(this.eras);
/* 626 */       i = 11 * i + Arrays.hashCode(this.months);
/* 627 */       i = 11 * i + Arrays.hashCode(this.shortMonths);
/* 628 */       i = 11 * i + Arrays.hashCode(this.weekdays);
/* 629 */       i = 11 * i + Arrays.hashCode(this.shortWeekdays);
/* 630 */       i = 11 * i + Arrays.hashCode(this.ampms);
/* 631 */       i = 11 * i + Arrays.deepHashCode(getZoneStringsWrapper());
/* 632 */       i = 11 * i + Objects.hashCode(this.localPatternChars);
/* 633 */       this.cachedHashCode = i;
/*     */     }
/*     */     
/* 636 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 644 */     if (this == paramObject) return true;
/* 645 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) return false;
/* 646 */     DateFormatSymbols localDateFormatSymbols = (DateFormatSymbols)paramObject;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 655 */     return (Arrays.equals(this.eras, localDateFormatSymbols.eras)) && (Arrays.equals(this.months, localDateFormatSymbols.months)) && (Arrays.equals(this.shortMonths, localDateFormatSymbols.shortMonths)) && (Arrays.equals(this.weekdays, localDateFormatSymbols.weekdays)) && (Arrays.equals(this.shortWeekdays, localDateFormatSymbols.shortWeekdays)) && (Arrays.equals(this.ampms, localDateFormatSymbols.ampms)) && (Arrays.deepEquals(getZoneStringsWrapper(), localDateFormatSymbols.getZoneStringsWrapper())) && (((this.localPatternChars != null) && (this.localPatternChars.equals(localDateFormatSymbols.localPatternChars))) || ((this.localPatternChars == null) && (localDateFormatSymbols.localPatternChars == null)));
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
/* 670 */   private static final ConcurrentMap<Locale, SoftReference<DateFormatSymbols>> cachedInstances = new ConcurrentHashMap(3);
/*     */   
/*     */ 
/* 673 */   private transient int lastZoneIndex = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 678 */   volatile transient int cachedHashCode = 0;
/*     */   
/*     */   private void initializeData(Locale paramLocale) {
/* 681 */     this.locale = paramLocale;
/*     */     
/*     */ 
/* 684 */     SoftReference localSoftReference1 = (SoftReference)cachedInstances.get(this.locale);
/*     */     DateFormatSymbols localDateFormatSymbols1;
/* 686 */     if ((localSoftReference1 != null) && ((localDateFormatSymbols1 = (DateFormatSymbols)localSoftReference1.get()) != null)) {
/* 687 */       copyMembers(localDateFormatSymbols1, this);
/* 688 */       return;
/*     */     }
/*     */     
/*     */ 
/* 692 */     LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(DateFormatSymbolsProvider.class, this.locale);
/*     */     
/* 694 */     if (!(localLocaleProviderAdapter instanceof ResourceBundleBasedAdapter)) {
/* 695 */       localLocaleProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
/*     */     }
/* 697 */     ResourceBundle localResourceBundle = ((ResourceBundleBasedAdapter)localLocaleProviderAdapter).getLocaleData().getDateFormatData(this.locale);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 702 */     if (localResourceBundle.containsKey("Eras")) {
/* 703 */       this.eras = localResourceBundle.getStringArray("Eras");
/* 704 */     } else if (localResourceBundle.containsKey("long.Eras")) {
/* 705 */       this.eras = localResourceBundle.getStringArray("long.Eras");
/* 706 */     } else if (localResourceBundle.containsKey("short.Eras")) {
/* 707 */       this.eras = localResourceBundle.getStringArray("short.Eras");
/*     */     }
/* 709 */     this.months = localResourceBundle.getStringArray("MonthNames");
/* 710 */     this.shortMonths = localResourceBundle.getStringArray("MonthAbbreviations");
/* 711 */     this.ampms = localResourceBundle.getStringArray("AmPmMarkers");
/* 712 */     this.localPatternChars = localResourceBundle.getString("DateTimePatternChars");
/*     */     
/*     */ 
/* 715 */     this.weekdays = toOneBasedArray(localResourceBundle.getStringArray("DayNames"));
/* 716 */     this.shortWeekdays = toOneBasedArray(localResourceBundle.getStringArray("DayAbbreviations"));
/*     */     
/*     */ 
/* 719 */     localSoftReference1 = new SoftReference((DateFormatSymbols)clone());
/* 720 */     SoftReference localSoftReference2 = (SoftReference)cachedInstances.putIfAbsent(this.locale, localSoftReference1);
/* 721 */     if (localSoftReference2 != null) {
/* 722 */       DateFormatSymbols localDateFormatSymbols2 = (DateFormatSymbols)localSoftReference2.get();
/* 723 */       if (localDateFormatSymbols2 == null)
/*     */       {
/* 725 */         cachedInstances.put(this.locale, localSoftReference1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static String[] toOneBasedArray(String[] paramArrayOfString) {
/* 731 */     int i = paramArrayOfString.length;
/* 732 */     String[] arrayOfString = new String[i + 1];
/* 733 */     arrayOfString[0] = "";
/* 734 */     for (int j = 0; j < i; j++) {
/* 735 */       arrayOfString[(j + 1)] = paramArrayOfString[j];
/*     */     }
/* 737 */     return arrayOfString;
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
/*     */   final int getZoneIndex(String paramString)
/*     */   {
/* 751 */     String[][] arrayOfString = getZoneStringsWrapper();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 758 */     if ((this.lastZoneIndex < arrayOfString.length) && (paramString.equals(arrayOfString[this.lastZoneIndex][0]))) {
/* 759 */       return this.lastZoneIndex;
/*     */     }
/*     */     
/*     */ 
/* 763 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 764 */       if (paramString.equals(arrayOfString[i][0])) {
/* 765 */         this.lastZoneIndex = i;
/* 766 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 770 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final String[][] getZoneStringsWrapper()
/*     */   {
/* 779 */     if (isSubclassObject()) {
/* 780 */       return getZoneStrings();
/*     */     }
/* 782 */     return getZoneStringsImpl(false);
/*     */   }
/*     */   
/*     */   private String[][] getZoneStringsImpl(boolean paramBoolean)
/*     */   {
/* 787 */     if (this.zoneStrings == null) {
/* 788 */       this.zoneStrings = TimeZoneNameUtility.getZoneStrings(this.locale);
/*     */     }
/*     */     
/* 791 */     if (!paramBoolean) {
/* 792 */       return this.zoneStrings;
/*     */     }
/*     */     
/* 795 */     int i = this.zoneStrings.length;
/* 796 */     String[][] arrayOfString = new String[i][];
/* 797 */     for (int j = 0; j < i; j++) {
/* 798 */       arrayOfString[j] = ((String[])Arrays.copyOf(this.zoneStrings[j], this.zoneStrings[j].length));
/*     */     }
/* 800 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private boolean isSubclassObject() {
/* 804 */     return !getClass().getName().equals("java.text.DateFormatSymbols");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void copyMembers(DateFormatSymbols paramDateFormatSymbols1, DateFormatSymbols paramDateFormatSymbols2)
/*     */   {
/* 815 */     paramDateFormatSymbols2.eras = ((String[])Arrays.copyOf(paramDateFormatSymbols1.eras, paramDateFormatSymbols1.eras.length));
/* 816 */     paramDateFormatSymbols2.months = ((String[])Arrays.copyOf(paramDateFormatSymbols1.months, paramDateFormatSymbols1.months.length));
/* 817 */     paramDateFormatSymbols2.shortMonths = ((String[])Arrays.copyOf(paramDateFormatSymbols1.shortMonths, paramDateFormatSymbols1.shortMonths.length));
/* 818 */     paramDateFormatSymbols2.weekdays = ((String[])Arrays.copyOf(paramDateFormatSymbols1.weekdays, paramDateFormatSymbols1.weekdays.length));
/* 819 */     paramDateFormatSymbols2.shortWeekdays = ((String[])Arrays.copyOf(paramDateFormatSymbols1.shortWeekdays, paramDateFormatSymbols1.shortWeekdays.length));
/* 820 */     paramDateFormatSymbols2.ampms = ((String[])Arrays.copyOf(paramDateFormatSymbols1.ampms, paramDateFormatSymbols1.ampms.length));
/* 821 */     if (paramDateFormatSymbols1.zoneStrings != null) {
/* 822 */       paramDateFormatSymbols2.zoneStrings = paramDateFormatSymbols1.getZoneStringsImpl(true);
/*     */     } else {
/* 824 */       paramDateFormatSymbols2.zoneStrings = ((String[][])null);
/*     */     }
/* 826 */     paramDateFormatSymbols2.localPatternChars = paramDateFormatSymbols1.localPatternChars;
/* 827 */     paramDateFormatSymbols2.cachedHashCode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 838 */     if (this.zoneStrings == null) {
/* 839 */       this.zoneStrings = TimeZoneNameUtility.getZoneStrings(this.locale);
/*     */     }
/* 841 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/DateFormatSymbols.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */