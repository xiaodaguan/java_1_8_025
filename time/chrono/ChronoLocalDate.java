/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.LocalTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface ChronoLocalDate
/*     */   extends Temporal, TemporalAdjuster, Comparable<ChronoLocalDate>
/*     */ {
/*     */   public static Comparator<ChronoLocalDate> timeLineOrder()
/*     */   {
/* 259 */     return AbstractChronology.DATE_ORDER;
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
/*     */   public static ChronoLocalDate from(TemporalAccessor paramTemporalAccessor)
/*     */   {
/* 285 */     if ((paramTemporalAccessor instanceof ChronoLocalDate)) {
/* 286 */       return (ChronoLocalDate)paramTemporalAccessor;
/*     */     }
/* 288 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 289 */     Chronology localChronology = (Chronology)paramTemporalAccessor.query(TemporalQueries.chronology());
/* 290 */     if (localChronology == null) {
/* 291 */       throw new DateTimeException("Unable to obtain ChronoLocalDate from TemporalAccessor: " + paramTemporalAccessor.getClass());
/*     */     }
/* 293 */     return localChronology.date(paramTemporalAccessor);
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
/*     */   public abstract Chronology getChronology();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Era getEra()
/*     */   {
/* 323 */     return getChronology().eraOf(get(ChronoField.ERA));
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
/*     */   public boolean isLeapYear()
/*     */   {
/* 338 */     return getChronology().isLeapYear(getLong(ChronoField.YEAR));
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
/*     */   public abstract int lengthOfMonth();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int lengthOfYear()
/*     */   {
/* 360 */     return isLeapYear() ? 366 : 365;
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
/*     */   public boolean isSupported(TemporalField paramTemporalField)
/*     */   {
/* 384 */     if ((paramTemporalField instanceof ChronoField)) {
/* 385 */       return paramTemporalField.isDateBased();
/*     */     }
/* 387 */     return (paramTemporalField != null) && (paramTemporalField.isSupportedBy(this));
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
/*     */   public boolean isSupported(TemporalUnit paramTemporalUnit)
/*     */   {
/* 410 */     if ((paramTemporalUnit instanceof ChronoUnit)) {
/* 411 */       return paramTemporalUnit.isDateBased();
/*     */     }
/* 413 */     return (paramTemporalUnit != null) && (paramTemporalUnit.isSupportedBy(this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate with(TemporalAdjuster paramTemporalAdjuster)
/*     */   {
/* 425 */     return ChronoLocalDateImpl.ensureValid(getChronology(), super.with(paramTemporalAdjuster));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate with(TemporalField paramTemporalField, long paramLong)
/*     */   {
/* 436 */     if ((paramTemporalField instanceof ChronoField)) {
/* 437 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 439 */     return ChronoLocalDateImpl.ensureValid(getChronology(), paramTemporalField.adjustInto(this, paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate plus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 449 */     return ChronoLocalDateImpl.ensureValid(getChronology(), super.plus(paramTemporalAmount));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate plus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 459 */     if ((paramTemporalUnit instanceof ChronoUnit)) {
/* 460 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*     */     }
/* 462 */     return ChronoLocalDateImpl.ensureValid(getChronology(), paramTemporalUnit.addTo(this, paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate minus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 472 */     return ChronoLocalDateImpl.ensureValid(getChronology(), super.minus(paramTemporalAmount));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate minus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 483 */     return ChronoLocalDateImpl.ensureValid(getChronology(), super.minus(paramLong, paramTemporalUnit));
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
/*     */   public <R> R query(TemporalQuery<R> paramTemporalQuery)
/*     */   {
/* 508 */     if ((paramTemporalQuery == TemporalQueries.zoneId()) || (paramTemporalQuery == TemporalQueries.zone()) || (paramTemporalQuery == TemporalQueries.offset()))
/* 509 */       return null;
/* 510 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 511 */       return null;
/* 512 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 513 */       return getChronology();
/* 514 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 515 */       return ChronoUnit.DAYS;
/*     */     }
/*     */     
/*     */ 
/* 519 */     return (R)paramTemporalQuery.queryFrom(this);
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
/*     */   public Temporal adjustInto(Temporal paramTemporal)
/*     */   {
/* 548 */     return paramTemporal.with(ChronoField.EPOCH_DAY, toEpochDay());
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
/*     */   public abstract long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoPeriod until(ChronoLocalDate paramChronoLocalDate);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String format(DateTimeFormatter paramDateTimeFormatter)
/*     */   {
/* 638 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 639 */     return paramDateTimeFormatter.format(this);
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
/*     */   public ChronoLocalDateTime<?> atTime(LocalTime paramLocalTime)
/*     */   {
/* 654 */     return ChronoLocalDateTimeImpl.of(this, paramLocalTime);
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
/*     */   public long toEpochDay()
/*     */   {
/* 670 */     return getLong(ChronoField.EPOCH_DAY);
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
/*     */   public int compareTo(ChronoLocalDate paramChronoLocalDate)
/*     */   {
/* 704 */     int i = Long.compare(toEpochDay(), paramChronoLocalDate.toEpochDay());
/* 705 */     if (i == 0) {
/* 706 */       i = getChronology().compareTo(paramChronoLocalDate.getChronology());
/*     */     }
/* 708 */     return i;
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
/*     */   public boolean isAfter(ChronoLocalDate paramChronoLocalDate)
/*     */   {
/* 726 */     return toEpochDay() > paramChronoLocalDate.toEpochDay();
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
/*     */   public boolean isBefore(ChronoLocalDate paramChronoLocalDate)
/*     */   {
/* 744 */     return toEpochDay() < paramChronoLocalDate.toEpochDay();
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
/*     */   public boolean isEqual(ChronoLocalDate paramChronoLocalDate)
/*     */   {
/* 762 */     return toEpochDay() == paramChronoLocalDate.toEpochDay();
/*     */   }
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ChronoLocalDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */