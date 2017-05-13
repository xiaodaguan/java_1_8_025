/*      */ package java.time.temporal;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.DayOfWeek;
/*      */ import java.time.chrono.ChronoLocalDate;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.format.ResolverStyle;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class WeekFields
/*      */   implements Serializable
/*      */ {
/*  191 */   private static final ConcurrentMap<String, WeekFields> CACHE = new ConcurrentHashMap(4, 0.75F, 2);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  205 */   public static final WeekFields ISO = new WeekFields(DayOfWeek.MONDAY, 4);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  214 */   public static final WeekFields SUNDAY_START = of(DayOfWeek.SUNDAY, 1);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  232 */   public static final TemporalUnit WEEK_BASED_YEARS = IsoFields.WEEK_BASED_YEARS;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = -1177360819670808121L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final DayOfWeek firstDayOfWeek;
/*      */   
/*      */ 
/*      */ 
/*      */   private final int minimalDays;
/*      */   
/*      */ 
/*      */ 
/*  250 */   private final transient TemporalField dayOfWeek = ComputedDayOfField.ofDayOfWeekField(this);
/*      */   
/*      */ 
/*      */ 
/*  254 */   private final transient TemporalField weekOfMonth = ComputedDayOfField.ofWeekOfMonthField(this);
/*      */   
/*      */ 
/*      */ 
/*  258 */   private final transient TemporalField weekOfYear = ComputedDayOfField.ofWeekOfYearField(this);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  266 */   private final transient TemporalField weekOfWeekBasedYear = ComputedDayOfField.ofWeekOfWeekBasedYearField(this);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  274 */   private final transient TemporalField weekBasedYear = ComputedDayOfField.ofWeekBasedYearField(this);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static WeekFields of(Locale paramLocale)
/*      */   {
/*  286 */     Objects.requireNonNull(paramLocale, "locale");
/*  287 */     paramLocale = new Locale(paramLocale.getLanguage(), paramLocale.getCountry());
/*      */     
/*  289 */     int i = CalendarDataUtility.retrieveFirstDayOfWeek(paramLocale);
/*  290 */     DayOfWeek localDayOfWeek = DayOfWeek.SUNDAY.plus(i - 1);
/*  291 */     int j = CalendarDataUtility.retrieveMinimalDaysInFirstWeek(paramLocale);
/*  292 */     return of(localDayOfWeek, j);
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
/*      */   public static WeekFields of(DayOfWeek paramDayOfWeek, int paramInt)
/*      */   {
/*  316 */     String str = paramDayOfWeek.toString() + paramInt;
/*  317 */     WeekFields localWeekFields = (WeekFields)CACHE.get(str);
/*  318 */     if (localWeekFields == null) {
/*  319 */       localWeekFields = new WeekFields(paramDayOfWeek, paramInt);
/*  320 */       CACHE.putIfAbsent(str, localWeekFields);
/*  321 */       localWeekFields = (WeekFields)CACHE.get(str);
/*      */     }
/*  323 */     return localWeekFields;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private WeekFields(DayOfWeek paramDayOfWeek, int paramInt)
/*      */   {
/*  335 */     Objects.requireNonNull(paramDayOfWeek, "firstDayOfWeek");
/*  336 */     if ((paramInt < 1) || (paramInt > 7)) {
/*  337 */       throw new IllegalArgumentException("Minimal number of days is invalid");
/*      */     }
/*  339 */     this.firstDayOfWeek = paramDayOfWeek;
/*  340 */     this.minimalDays = paramInt;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException, InvalidObjectException
/*      */   {
/*  356 */     paramObjectInputStream.defaultReadObject();
/*  357 */     if (this.firstDayOfWeek == null) {
/*  358 */       throw new InvalidObjectException("firstDayOfWeek is null");
/*      */     }
/*      */     
/*  361 */     if ((this.minimalDays < 1) || (this.minimalDays > 7)) {
/*  362 */       throw new InvalidObjectException("Minimal number of days is invalid");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object readResolve()
/*      */     throws InvalidObjectException
/*      */   {
/*      */     try
/*      */     {
/*  375 */       return of(this.firstDayOfWeek, this.minimalDays);
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*  377 */       throw new InvalidObjectException("Invalid serialized WeekFields: " + localIllegalArgumentException.getMessage());
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
/*      */   public DayOfWeek getFirstDayOfWeek()
/*      */   {
/*  392 */     return this.firstDayOfWeek;
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
/*      */   public int getMinimalDaysInFirstWeek()
/*      */   {
/*  406 */     return this.minimalDays;
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
/*      */   public TemporalField dayOfWeek()
/*      */   {
/*  429 */     return this.dayOfWeek;
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
/*      */   public TemporalField weekOfMonth()
/*      */   {
/*  475 */     return this.weekOfMonth;
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
/*      */   public TemporalField weekOfYear()
/*      */   {
/*  520 */     return this.weekOfYear;
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
/*      */   public TemporalField weekOfWeekBasedYear()
/*      */   {
/*  570 */     return this.weekOfWeekBasedYear;
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
/*      */   public TemporalField weekBasedYear()
/*      */   {
/*  612 */     return this.weekBasedYear;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  627 */     if (this == paramObject) {
/*  628 */       return true;
/*      */     }
/*  630 */     if ((paramObject instanceof WeekFields)) {
/*  631 */       return hashCode() == paramObject.hashCode();
/*      */     }
/*  633 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  643 */     return this.firstDayOfWeek.ordinal() * 7 + this.minimalDays;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  654 */     return "WeekFields[" + this.firstDayOfWeek + ',' + this.minimalDays + ']';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static class ComputedDayOfField
/*      */     implements TemporalField
/*      */   {
/*      */     private final String name;
/*      */     
/*      */ 
/*      */     private final WeekFields weekDef;
/*      */     
/*      */     private final TemporalUnit baseUnit;
/*      */     
/*      */     private final TemporalUnit rangeUnit;
/*      */     
/*      */     private final ValueRange range;
/*      */     
/*      */ 
/*      */     static ComputedDayOfField ofDayOfWeekField(WeekFields paramWeekFields)
/*      */     {
/*  676 */       return new ComputedDayOfField("DayOfWeek", paramWeekFields, ChronoUnit.DAYS, ChronoUnit.WEEKS, DAY_OF_WEEK_RANGE);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static ComputedDayOfField ofWeekOfMonthField(WeekFields paramWeekFields)
/*      */     {
/*  685 */       return new ComputedDayOfField("WeekOfMonth", paramWeekFields, ChronoUnit.WEEKS, ChronoUnit.MONTHS, WEEK_OF_MONTH_RANGE);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static ComputedDayOfField ofWeekOfYearField(WeekFields paramWeekFields)
/*      */     {
/*  694 */       return new ComputedDayOfField("WeekOfYear", paramWeekFields, ChronoUnit.WEEKS, ChronoUnit.YEARS, WEEK_OF_YEAR_RANGE);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static ComputedDayOfField ofWeekOfWeekBasedYearField(WeekFields paramWeekFields)
/*      */     {
/*  703 */       return new ComputedDayOfField("WeekOfWeekBasedYear", paramWeekFields, ChronoUnit.WEEKS, IsoFields.WEEK_BASED_YEARS, WEEK_OF_WEEK_BASED_YEAR_RANGE);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static ComputedDayOfField ofWeekBasedYearField(WeekFields paramWeekFields)
/*      */     {
/*  712 */       return new ComputedDayOfField("WeekBasedYear", paramWeekFields, IsoFields.WEEK_BASED_YEARS, ChronoUnit.FOREVER, ChronoField.YEAR.range());
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
/*      */     private ChronoLocalDate ofWeekBasedYear(Chronology paramChronology, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/*  726 */       ChronoLocalDate localChronoLocalDate = paramChronology.date(paramInt1, 1, 1);
/*  727 */       int i = localizedDayOfWeek(localChronoLocalDate);
/*  728 */       int j = startOfWeekOffset(1, i);
/*      */       
/*      */ 
/*  731 */       int k = localChronoLocalDate.lengthOfYear();
/*  732 */       int m = computeWeek(j, k + this.weekDef.getMinimalDaysInFirstWeek());
/*  733 */       paramInt2 = Math.min(paramInt2, m - 1);
/*      */       
/*  735 */       int n = -j + (paramInt3 - 1) + (paramInt2 - 1) * 7;
/*  736 */       return localChronoLocalDate.plus(n, ChronoUnit.DAYS);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private ComputedDayOfField(String paramString, WeekFields paramWeekFields, TemporalUnit paramTemporalUnit1, TemporalUnit paramTemporalUnit2, ValueRange paramValueRange)
/*      */     {
/*  746 */       this.name = paramString;
/*  747 */       this.weekDef = paramWeekFields;
/*  748 */       this.baseUnit = paramTemporalUnit1;
/*  749 */       this.rangeUnit = paramTemporalUnit2;
/*  750 */       this.range = paramValueRange;
/*      */     }
/*      */     
/*  753 */     private static final ValueRange DAY_OF_WEEK_RANGE = ValueRange.of(1L, 7L);
/*  754 */     private static final ValueRange WEEK_OF_MONTH_RANGE = ValueRange.of(0L, 1L, 4L, 6L);
/*  755 */     private static final ValueRange WEEK_OF_YEAR_RANGE = ValueRange.of(0L, 1L, 52L, 54L);
/*  756 */     private static final ValueRange WEEK_OF_WEEK_BASED_YEAR_RANGE = ValueRange.of(1L, 52L, 53L);
/*      */     
/*      */     public long getFrom(TemporalAccessor paramTemporalAccessor)
/*      */     {
/*  760 */       if (this.rangeUnit == ChronoUnit.WEEKS)
/*  761 */         return localizedDayOfWeek(paramTemporalAccessor);
/*  762 */       if (this.rangeUnit == ChronoUnit.MONTHS)
/*  763 */         return localizedWeekOfMonth(paramTemporalAccessor);
/*  764 */       if (this.rangeUnit == ChronoUnit.YEARS)
/*  765 */         return localizedWeekOfYear(paramTemporalAccessor);
/*  766 */       if (this.rangeUnit == WeekFields.WEEK_BASED_YEARS)
/*  767 */         return localizedWeekOfWeekBasedYear(paramTemporalAccessor);
/*  768 */       if (this.rangeUnit == ChronoUnit.FOREVER) {
/*  769 */         return localizedWeekBasedYear(paramTemporalAccessor);
/*      */       }
/*  771 */       throw new IllegalStateException("unreachable, rangeUnit: " + this.rangeUnit + ", this: " + this);
/*      */     }
/*      */     
/*      */     private int localizedDayOfWeek(TemporalAccessor paramTemporalAccessor)
/*      */     {
/*  776 */       int i = this.weekDef.getFirstDayOfWeek().getValue();
/*  777 */       int j = paramTemporalAccessor.get(ChronoField.DAY_OF_WEEK);
/*  778 */       return Math.floorMod(j - i, 7) + 1;
/*      */     }
/*      */     
/*      */     private int localizedDayOfWeek(int paramInt) {
/*  782 */       int i = this.weekDef.getFirstDayOfWeek().getValue();
/*  783 */       return Math.floorMod(paramInt - i, 7) + 1;
/*      */     }
/*      */     
/*      */     private long localizedWeekOfMonth(TemporalAccessor paramTemporalAccessor) {
/*  787 */       int i = localizedDayOfWeek(paramTemporalAccessor);
/*  788 */       int j = paramTemporalAccessor.get(ChronoField.DAY_OF_MONTH);
/*  789 */       int k = startOfWeekOffset(j, i);
/*  790 */       return computeWeek(k, j);
/*      */     }
/*      */     
/*      */     private long localizedWeekOfYear(TemporalAccessor paramTemporalAccessor) {
/*  794 */       int i = localizedDayOfWeek(paramTemporalAccessor);
/*  795 */       int j = paramTemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/*  796 */       int k = startOfWeekOffset(j, i);
/*  797 */       return computeWeek(k, j);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int localizedWeekBasedYear(TemporalAccessor paramTemporalAccessor)
/*      */     {
/*  807 */       int i = localizedDayOfWeek(paramTemporalAccessor);
/*  808 */       int j = paramTemporalAccessor.get(ChronoField.YEAR);
/*  809 */       int k = paramTemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/*  810 */       int m = startOfWeekOffset(k, i);
/*  811 */       int n = computeWeek(m, k);
/*  812 */       if (n == 0)
/*      */       {
/*  814 */         return j - 1;
/*      */       }
/*      */       
/*      */ 
/*  818 */       ValueRange localValueRange = paramTemporalAccessor.range(ChronoField.DAY_OF_YEAR);
/*  819 */       int i1 = (int)localValueRange.getMaximum();
/*  820 */       int i2 = computeWeek(m, i1 + this.weekDef.getMinimalDaysInFirstWeek());
/*  821 */       if (n >= i2) {
/*  822 */         return j + 1;
/*      */       }
/*      */       
/*  825 */       return j;
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
/*      */     private int localizedWeekOfWeekBasedYear(TemporalAccessor paramTemporalAccessor)
/*      */     {
/*  838 */       int i = localizedDayOfWeek(paramTemporalAccessor);
/*  839 */       int j = paramTemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/*  840 */       int k = startOfWeekOffset(j, i);
/*  841 */       int m = computeWeek(k, j);
/*  842 */       Object localObject; if (m == 0)
/*      */       {
/*      */ 
/*  845 */         localObject = Chronology.from(paramTemporalAccessor).date(paramTemporalAccessor);
/*  846 */         localObject = ((ChronoLocalDate)localObject).minus(j, ChronoUnit.DAYS);
/*  847 */         return localizedWeekOfWeekBasedYear((TemporalAccessor)localObject); }
/*  848 */       if (m > 50)
/*      */       {
/*      */ 
/*  851 */         localObject = paramTemporalAccessor.range(ChronoField.DAY_OF_YEAR);
/*  852 */         int n = (int)((ValueRange)localObject).getMaximum();
/*  853 */         int i1 = computeWeek(k, n + this.weekDef.getMinimalDaysInFirstWeek());
/*  854 */         if (m >= i1)
/*      */         {
/*  856 */           m = m - i1 + 1;
/*      */         }
/*      */       }
/*  859 */       return m;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int startOfWeekOffset(int paramInt1, int paramInt2)
/*      */     {
/*  871 */       int i = Math.floorMod(paramInt1 - paramInt2, 7);
/*  872 */       int j = -i;
/*  873 */       if (i + 1 > this.weekDef.getMinimalDaysInFirstWeek())
/*      */       {
/*  875 */         j = 7 - i;
/*      */       }
/*  877 */       return j;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int computeWeek(int paramInt1, int paramInt2)
/*      */     {
/*  889 */       return (7 + paramInt1 + (paramInt2 - 1)) / 7;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public <R extends Temporal> R adjustInto(R paramR, long paramLong)
/*      */     {
/*  896 */       int i = this.range.checkValidIntValue(paramLong, this);
/*  897 */       int j = paramR.get(this);
/*  898 */       if (i == j) {
/*  899 */         return paramR;
/*      */       }
/*      */       
/*  902 */       if (this.rangeUnit == ChronoUnit.FOREVER)
/*      */       {
/*      */ 
/*  905 */         int k = paramR.get(this.weekDef.dayOfWeek);
/*  906 */         int m = paramR.get(this.weekDef.weekOfWeekBasedYear);
/*  907 */         return ofWeekBasedYear(Chronology.from(paramR), (int)paramLong, m, k);
/*      */       }
/*      */       
/*  910 */       return paramR.plus(i - j, this.baseUnit);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public ChronoLocalDate resolve(Map<TemporalField, Long> paramMap, TemporalAccessor paramTemporalAccessor, ResolverStyle paramResolverStyle)
/*      */     {
/*  917 */       long l1 = ((Long)paramMap.get(this)).longValue();
/*  918 */       int i = Math.toIntExact(l1);
/*      */       
/*      */ 
/*      */ 
/*  922 */       if (this.rangeUnit == ChronoUnit.WEEKS) {
/*  923 */         j = this.range.checkValidIntValue(l1, this);
/*  924 */         k = this.weekDef.getFirstDayOfWeek().getValue();
/*  925 */         long l2 = Math.floorMod(k - 1 + (j - 1), 7) + 1;
/*  926 */         paramMap.remove(this);
/*  927 */         paramMap.put(ChronoField.DAY_OF_WEEK, Long.valueOf(l2));
/*  928 */         return null;
/*      */       }
/*      */       
/*      */ 
/*  932 */       if (!paramMap.containsKey(ChronoField.DAY_OF_WEEK)) {
/*  933 */         return null;
/*      */       }
/*  935 */       int j = ChronoField.DAY_OF_WEEK.checkValidIntValue(((Long)paramMap.get(ChronoField.DAY_OF_WEEK)).longValue());
/*  936 */       int k = localizedDayOfWeek(j);
/*      */       
/*      */ 
/*  939 */       Chronology localChronology = Chronology.from(paramTemporalAccessor);
/*  940 */       if (paramMap.containsKey(ChronoField.YEAR)) {
/*  941 */         int m = ChronoField.YEAR.checkValidIntValue(((Long)paramMap.get(ChronoField.YEAR)).longValue());
/*  942 */         if ((this.rangeUnit == ChronoUnit.MONTHS) && (paramMap.containsKey(ChronoField.MONTH_OF_YEAR))) {
/*  943 */           long l3 = ((Long)paramMap.get(ChronoField.MONTH_OF_YEAR)).longValue();
/*  944 */           return resolveWoM(paramMap, localChronology, m, l3, i, k, paramResolverStyle);
/*      */         }
/*  946 */         if (this.rangeUnit == ChronoUnit.YEARS) {
/*  947 */           return resolveWoY(paramMap, localChronology, m, i, k, paramResolverStyle);
/*      */         }
/*  949 */       } else if (((this.rangeUnit == WeekFields.WEEK_BASED_YEARS) || (this.rangeUnit == ChronoUnit.FOREVER)) && 
/*  950 */         (paramMap.containsKey(this.weekDef.weekBasedYear)) && 
/*  951 */         (paramMap.containsKey(this.weekDef.weekOfWeekBasedYear))) {
/*  952 */         return resolveWBY(paramMap, localChronology, k, paramResolverStyle);
/*      */       }
/*  954 */       return null;
/*      */     }
/*      */     
/*      */     private ChronoLocalDate resolveWoM(Map<TemporalField, Long> paramMap, Chronology paramChronology, int paramInt1, long paramLong1, long paramLong2, int paramInt2, ResolverStyle paramResolverStyle) {
/*      */       ChronoLocalDate localChronoLocalDate;
/*      */       int k;
/*  960 */       if (paramResolverStyle == ResolverStyle.LENIENT) {
/*  961 */         localChronoLocalDate = paramChronology.date(paramInt1, 1, 1).plus(Math.subtractExact(paramLong1, 1L), ChronoUnit.MONTHS);
/*  962 */         long l = Math.subtractExact(paramLong2, localizedWeekOfMonth(localChronoLocalDate));
/*  963 */         k = paramInt2 - localizedDayOfWeek(localChronoLocalDate);
/*  964 */         localChronoLocalDate = localChronoLocalDate.plus(Math.addExact(Math.multiplyExact(l, 7L), k), ChronoUnit.DAYS);
/*      */       } else {
/*  966 */         int i = ChronoField.MONTH_OF_YEAR.checkValidIntValue(paramLong1);
/*  967 */         localChronoLocalDate = paramChronology.date(paramInt1, i, 1);
/*  968 */         int j = this.range.checkValidIntValue(paramLong2, this);
/*  969 */         k = (int)(j - localizedWeekOfMonth(localChronoLocalDate));
/*  970 */         int m = paramInt2 - localizedDayOfWeek(localChronoLocalDate);
/*  971 */         localChronoLocalDate = localChronoLocalDate.plus(k * 7 + m, ChronoUnit.DAYS);
/*  972 */         if ((paramResolverStyle == ResolverStyle.STRICT) && (localChronoLocalDate.getLong(ChronoField.MONTH_OF_YEAR) != paramLong1)) {
/*  973 */           throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
/*      */         }
/*      */       }
/*  976 */       paramMap.remove(this);
/*  977 */       paramMap.remove(ChronoField.YEAR);
/*  978 */       paramMap.remove(ChronoField.MONTH_OF_YEAR);
/*  979 */       paramMap.remove(ChronoField.DAY_OF_WEEK);
/*  980 */       return localChronoLocalDate;
/*      */     }
/*      */     
/*      */     private ChronoLocalDate resolveWoY(Map<TemporalField, Long> paramMap, Chronology paramChronology, int paramInt1, long paramLong, int paramInt2, ResolverStyle paramResolverStyle)
/*      */     {
/*  985 */       ChronoLocalDate localChronoLocalDate = paramChronology.date(paramInt1, 1, 1);
/*  986 */       int k; if (paramResolverStyle == ResolverStyle.LENIENT) {
/*  987 */         long l = Math.subtractExact(paramLong, localizedWeekOfYear(localChronoLocalDate));
/*  988 */         k = paramInt2 - localizedDayOfWeek(localChronoLocalDate);
/*  989 */         localChronoLocalDate = localChronoLocalDate.plus(Math.addExact(Math.multiplyExact(l, 7L), k), ChronoUnit.DAYS);
/*      */       } else {
/*  991 */         int i = this.range.checkValidIntValue(paramLong, this);
/*  992 */         int j = (int)(i - localizedWeekOfYear(localChronoLocalDate));
/*  993 */         k = paramInt2 - localizedDayOfWeek(localChronoLocalDate);
/*  994 */         localChronoLocalDate = localChronoLocalDate.plus(j * 7 + k, ChronoUnit.DAYS);
/*  995 */         if ((paramResolverStyle == ResolverStyle.STRICT) && (localChronoLocalDate.getLong(ChronoField.YEAR) != paramInt1)) {
/*  996 */           throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
/*      */         }
/*      */       }
/*  999 */       paramMap.remove(this);
/* 1000 */       paramMap.remove(ChronoField.YEAR);
/* 1001 */       paramMap.remove(ChronoField.DAY_OF_WEEK);
/* 1002 */       return localChronoLocalDate;
/*      */     }
/*      */     
/*      */     private ChronoLocalDate resolveWBY(Map<TemporalField, Long> paramMap, Chronology paramChronology, int paramInt, ResolverStyle paramResolverStyle)
/*      */     {
/* 1007 */       int i = this.weekDef.weekBasedYear.range().checkValidIntValue(
/* 1008 */         ((Long)paramMap.get(this.weekDef.weekBasedYear)).longValue(), this.weekDef.weekBasedYear);
/*      */       ChronoLocalDate localChronoLocalDate;
/* 1010 */       if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 1011 */         localChronoLocalDate = ofWeekBasedYear(paramChronology, i, 1, paramInt);
/* 1012 */         long l1 = ((Long)paramMap.get(this.weekDef.weekOfWeekBasedYear)).longValue();
/* 1013 */         long l2 = Math.subtractExact(l1, 1L);
/* 1014 */         localChronoLocalDate = localChronoLocalDate.plus(l2, ChronoUnit.WEEKS);
/*      */       } else {
/* 1016 */         int j = this.weekDef.weekOfWeekBasedYear.range().checkValidIntValue(
/* 1017 */           ((Long)paramMap.get(this.weekDef.weekOfWeekBasedYear)).longValue(), this.weekDef.weekOfWeekBasedYear);
/* 1018 */         localChronoLocalDate = ofWeekBasedYear(paramChronology, i, j, paramInt);
/* 1019 */         if ((paramResolverStyle == ResolverStyle.STRICT) && (localizedWeekBasedYear(localChronoLocalDate) != i)) {
/* 1020 */           throw new DateTimeException("Strict mode rejected resolved date as it is in a different week-based-year");
/*      */         }
/*      */       }
/* 1023 */       paramMap.remove(this);
/* 1024 */       paramMap.remove(this.weekDef.weekBasedYear);
/* 1025 */       paramMap.remove(this.weekDef.weekOfWeekBasedYear);
/* 1026 */       paramMap.remove(ChronoField.DAY_OF_WEEK);
/* 1027 */       return localChronoLocalDate;
/*      */     }
/*      */     
/*      */ 
/*      */     public String getDisplayName(Locale paramLocale)
/*      */     {
/* 1033 */       Objects.requireNonNull(paramLocale, "locale");
/* 1034 */       if (this.rangeUnit == ChronoUnit.YEARS)
/*      */       {
/* 1036 */         LocaleResources localLocaleResources = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(paramLocale);
/* 1037 */         ResourceBundle localResourceBundle = localLocaleResources.getJavaTimeFormatData();
/* 1038 */         return localResourceBundle.containsKey("field.week") ? localResourceBundle.getString("field.week") : this.name;
/*      */       }
/* 1040 */       return this.name;
/*      */     }
/*      */     
/*      */     public TemporalUnit getBaseUnit()
/*      */     {
/* 1045 */       return this.baseUnit;
/*      */     }
/*      */     
/*      */     public TemporalUnit getRangeUnit()
/*      */     {
/* 1050 */       return this.rangeUnit;
/*      */     }
/*      */     
/*      */     public boolean isDateBased()
/*      */     {
/* 1055 */       return true;
/*      */     }
/*      */     
/*      */     public boolean isTimeBased()
/*      */     {
/* 1060 */       return false;
/*      */     }
/*      */     
/*      */     public ValueRange range()
/*      */     {
/* 1065 */       return this.range;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isSupportedBy(TemporalAccessor paramTemporalAccessor)
/*      */     {
/* 1071 */       if (paramTemporalAccessor.isSupported(ChronoField.DAY_OF_WEEK)) {
/* 1072 */         if (this.rangeUnit == ChronoUnit.WEEKS)
/* 1073 */           return true;
/* 1074 */         if (this.rangeUnit == ChronoUnit.MONTHS)
/* 1075 */           return paramTemporalAccessor.isSupported(ChronoField.DAY_OF_MONTH);
/* 1076 */         if (this.rangeUnit == ChronoUnit.YEARS)
/* 1077 */           return paramTemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR);
/* 1078 */         if (this.rangeUnit == WeekFields.WEEK_BASED_YEARS)
/* 1079 */           return paramTemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR);
/* 1080 */         if (this.rangeUnit == ChronoUnit.FOREVER) {
/* 1081 */           return paramTemporalAccessor.isSupported(ChronoField.YEAR);
/*      */         }
/*      */       }
/* 1084 */       return false;
/*      */     }
/*      */     
/*      */     public ValueRange rangeRefinedBy(TemporalAccessor paramTemporalAccessor)
/*      */     {
/* 1089 */       if (this.rangeUnit == ChronoUnit.WEEKS)
/* 1090 */         return this.range;
/* 1091 */       if (this.rangeUnit == ChronoUnit.MONTHS)
/* 1092 */         return rangeByWeek(paramTemporalAccessor, ChronoField.DAY_OF_MONTH);
/* 1093 */       if (this.rangeUnit == ChronoUnit.YEARS)
/* 1094 */         return rangeByWeek(paramTemporalAccessor, ChronoField.DAY_OF_YEAR);
/* 1095 */       if (this.rangeUnit == WeekFields.WEEK_BASED_YEARS)
/* 1096 */         return rangeWeekOfWeekBasedYear(paramTemporalAccessor);
/* 1097 */       if (this.rangeUnit == ChronoUnit.FOREVER) {
/* 1098 */         return ChronoField.YEAR.range();
/*      */       }
/* 1100 */       throw new IllegalStateException("unreachable, rangeUnit: " + this.rangeUnit + ", this: " + this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private ValueRange rangeByWeek(TemporalAccessor paramTemporalAccessor, TemporalField paramTemporalField)
/*      */     {
/* 1111 */       int i = localizedDayOfWeek(paramTemporalAccessor);
/* 1112 */       int j = startOfWeekOffset(paramTemporalAccessor.get(paramTemporalField), i);
/* 1113 */       ValueRange localValueRange = paramTemporalAccessor.range(paramTemporalField);
/* 1114 */       return ValueRange.of(computeWeek(j, (int)localValueRange.getMinimum()), 
/* 1115 */         computeWeek(j, (int)localValueRange.getMaximum()));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private ValueRange rangeWeekOfWeekBasedYear(TemporalAccessor paramTemporalAccessor)
/*      */     {
/* 1124 */       if (!paramTemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR)) {
/* 1125 */         return WEEK_OF_YEAR_RANGE;
/*      */       }
/* 1127 */       int i = localizedDayOfWeek(paramTemporalAccessor);
/* 1128 */       int j = paramTemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/* 1129 */       int k = startOfWeekOffset(j, i);
/* 1130 */       int m = computeWeek(k, j);
/* 1131 */       if (m == 0)
/*      */       {
/*      */ 
/* 1134 */         localObject = Chronology.from(paramTemporalAccessor).date(paramTemporalAccessor);
/* 1135 */         localObject = ((ChronoLocalDate)localObject).minus(j + 7, ChronoUnit.DAYS);
/* 1136 */         return rangeWeekOfWeekBasedYear((TemporalAccessor)localObject);
/*      */       }
/*      */       
/* 1139 */       Object localObject = paramTemporalAccessor.range(ChronoField.DAY_OF_YEAR);
/* 1140 */       int n = (int)((ValueRange)localObject).getMaximum();
/* 1141 */       int i1 = computeWeek(k, n + this.weekDef.getMinimalDaysInFirstWeek());
/*      */       
/* 1143 */       if (m >= i1)
/*      */       {
/* 1145 */         ChronoLocalDate localChronoLocalDate = Chronology.from(paramTemporalAccessor).date(paramTemporalAccessor);
/* 1146 */         localChronoLocalDate = localChronoLocalDate.plus(n - j + 1 + 7, ChronoUnit.DAYS);
/* 1147 */         return rangeWeekOfWeekBasedYear(localChronoLocalDate);
/*      */       }
/* 1149 */       return ValueRange.of(1L, i1 - 1);
/*      */     }
/*      */     
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1155 */       return this.name + "[" + this.weekDef.toString() + "]";
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/temporal/WeekFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */