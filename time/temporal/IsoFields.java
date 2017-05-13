/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.Duration;
/*     */ import java.time.LocalDate;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.ResourceBundle;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleResources;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IsoFields
/*     */ {
/* 195 */   public static final TemporalField DAY_OF_QUARTER = Field.DAY_OF_QUARTER;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 210 */   public static final TemporalField QUARTER_OF_YEAR = Field.QUARTER_OF_YEAR;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */   public static final TemporalField WEEK_OF_WEEK_BASED_YEAR = Field.WEEK_OF_WEEK_BASED_YEAR;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 254 */   public static final TemporalField WEEK_BASED_YEAR = Field.WEEK_BASED_YEAR;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 269 */   public static final TemporalUnit WEEK_BASED_YEARS = Unit.WEEK_BASED_YEARS;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 277 */   public static final TemporalUnit QUARTER_YEARS = Unit.QUARTER_YEARS;
/*     */   
/*     */ 
/*     */ 
/*     */   private IsoFields()
/*     */   {
/* 283 */     throw new AssertionError("Not instantiable");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static abstract enum Field
/*     */     implements TemporalField
/*     */   {
/* 291 */     DAY_OF_QUARTER, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 380 */     QUARTER_OF_YEAR, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 418 */     WEEK_OF_WEEK_BASED_YEAR, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 508 */     WEEK_BASED_YEAR;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Field() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isDateBased()
/*     */     {
/* 558 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isTimeBased()
/*     */     {
/* 563 */       return false;
/*     */     }
/*     */     
/*     */     public ValueRange rangeRefinedBy(TemporalAccessor paramTemporalAccessor)
/*     */     {
/* 568 */       return range();
/*     */     }
/*     */     
/*     */ 
/* 572 */     private static final int[] QUARTER_DAYS = { 0, 90, 181, 273, 0, 91, 182, 274 };
/*     */     
/*     */     private static boolean isIso(TemporalAccessor paramTemporalAccessor) {
/* 575 */       return Chronology.from(paramTemporalAccessor).equals(IsoChronology.INSTANCE);
/*     */     }
/*     */     
/*     */     private static void ensureIso(TemporalAccessor paramTemporalAccessor) {
/* 579 */       if (!isIso(paramTemporalAccessor)) {
/* 580 */         throw new DateTimeException("Resolve requires IsoChronology");
/*     */       }
/*     */     }
/*     */     
/*     */     private static ValueRange getWeekRange(LocalDate paramLocalDate) {
/* 585 */       int i = getWeekBasedYear(paramLocalDate);
/* 586 */       return ValueRange.of(1L, getWeekRange(i));
/*     */     }
/*     */     
/*     */     private static int getWeekRange(int paramInt) {
/* 590 */       LocalDate localLocalDate = LocalDate.of(paramInt, 1, 1);
/*     */       
/* 592 */       if ((localLocalDate.getDayOfWeek() == DayOfWeek.THURSDAY) || ((localLocalDate.getDayOfWeek() == DayOfWeek.WEDNESDAY) && (localLocalDate.isLeapYear()))) {
/* 593 */         return 53;
/*     */       }
/* 595 */       return 52;
/*     */     }
/*     */     
/*     */     private static int getWeek(LocalDate paramLocalDate) {
/* 599 */       int i = paramLocalDate.getDayOfWeek().ordinal();
/* 600 */       int j = paramLocalDate.getDayOfYear() - 1;
/* 601 */       int k = j + (3 - i);
/* 602 */       int m = k / 7;
/* 603 */       int n = k - m * 7;
/* 604 */       int i1 = n - 3;
/* 605 */       if (i1 < -3) {
/* 606 */         i1 += 7;
/*     */       }
/* 608 */       if (j < i1) {
/* 609 */         return (int)getWeekRange(paramLocalDate.withDayOfYear(180).minusYears(1L)).getMaximum();
/*     */       }
/* 611 */       int i2 = (j - i1) / 7 + 1;
/* 612 */       if (i2 == 53) {
/* 613 */         if (((i1 == -3) || ((i1 == -2) && (paramLocalDate.isLeapYear())) ? 1 : 0) == 0) {
/* 614 */           i2 = 1;
/*     */         }
/*     */       }
/* 617 */       return i2;
/*     */     }
/*     */     
/*     */     private static int getWeekBasedYear(LocalDate paramLocalDate) {
/* 621 */       int i = paramLocalDate.getYear();
/* 622 */       int j = paramLocalDate.getDayOfYear();
/* 623 */       int k; if (j <= 3) {
/* 624 */         k = paramLocalDate.getDayOfWeek().ordinal();
/* 625 */         if (j - k < -2) {
/* 626 */           i--;
/*     */         }
/* 628 */       } else if (j >= 363) {
/* 629 */         k = paramLocalDate.getDayOfWeek().ordinal();
/* 630 */         j = j - 363 - (paramLocalDate.isLeapYear() ? 1 : 0);
/* 631 */         if (j - k >= 0) {
/* 632 */           i++;
/*     */         }
/*     */       }
/* 635 */       return i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static enum Unit
/*     */     implements TemporalUnit
/*     */   {
/* 648 */     WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952L)), 
/*     */     
/*     */ 
/*     */ 
/* 652 */     QUARTER_YEARS("QuarterYears", Duration.ofSeconds(7889238L));
/*     */     
/*     */     private final String name;
/*     */     private final Duration duration;
/*     */     
/*     */     private Unit(String paramString, Duration paramDuration) {
/* 658 */       this.name = paramString;
/* 659 */       this.duration = paramDuration;
/*     */     }
/*     */     
/*     */     public Duration getDuration()
/*     */     {
/* 664 */       return this.duration;
/*     */     }
/*     */     
/*     */     public boolean isDurationEstimated()
/*     */     {
/* 669 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isDateBased()
/*     */     {
/* 674 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isTimeBased()
/*     */     {
/* 679 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isSupportedBy(Temporal paramTemporal)
/*     */     {
/* 684 */       return paramTemporal.isSupported(ChronoField.EPOCH_DAY);
/*     */     }
/*     */     
/*     */ 
/*     */     public <R extends Temporal> R addTo(R paramR, long paramLong)
/*     */     {
/* 690 */       switch (IsoFields.1.$SwitchMap$java$time$temporal$IsoFields$Unit[ordinal()]) {
/*     */       case 1: 
/* 692 */         return paramR.with(IsoFields.WEEK_BASED_YEAR, 
/* 693 */           Math.addExact(paramR.get(IsoFields.WEEK_BASED_YEAR), paramLong));
/*     */       
/*     */ 
/*     */       case 2: 
/* 697 */         return paramR.plus(paramLong / 256L, ChronoUnit.YEARS).plus(paramLong % 256L * 3L, ChronoUnit.MONTHS);
/*     */       }
/* 699 */       throw new IllegalStateException("Unreachable");
/*     */     }
/*     */     
/*     */ 
/*     */     public long between(Temporal paramTemporal1, Temporal paramTemporal2)
/*     */     {
/* 705 */       if (paramTemporal1.getClass() != paramTemporal2.getClass()) {
/* 706 */         return paramTemporal1.until(paramTemporal2, this);
/*     */       }
/* 708 */       switch (IsoFields.1.$SwitchMap$java$time$temporal$IsoFields$Unit[ordinal()]) {
/*     */       case 1: 
/* 710 */         return Math.subtractExact(paramTemporal2.getLong(IsoFields.WEEK_BASED_YEAR), paramTemporal1
/* 711 */           .getLong(IsoFields.WEEK_BASED_YEAR));
/*     */       case 2: 
/* 713 */         return paramTemporal1.until(paramTemporal2, ChronoUnit.MONTHS) / 3L;
/*     */       }
/* 715 */       throw new IllegalStateException("Unreachable");
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 721 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/temporal/IsoFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */