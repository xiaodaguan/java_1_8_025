/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
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
/*     */ public abstract interface ChronoLocalDateTime<D extends ChronoLocalDate>
/*     */   extends Temporal, TemporalAdjuster, Comparable<ChronoLocalDateTime<?>>
/*     */ {
/*     */   public static Comparator<ChronoLocalDateTime<?>> timeLineOrder()
/*     */   {
/* 140 */     return AbstractChronology.DATE_TIME_ORDER;
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
/*     */   public static ChronoLocalDateTime<?> from(TemporalAccessor paramTemporalAccessor)
/*     */   {
/* 166 */     if ((paramTemporalAccessor instanceof ChronoLocalDateTime)) {
/* 167 */       return (ChronoLocalDateTime)paramTemporalAccessor;
/*     */     }
/* 169 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 170 */     Chronology localChronology = (Chronology)paramTemporalAccessor.query(TemporalQueries.chronology());
/* 171 */     if (localChronology == null) {
/* 172 */       throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass());
/*     */     }
/* 174 */     return localChronology.localDateTime(paramTemporalAccessor);
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
/*     */   public Chronology getChronology()
/*     */   {
/* 187 */     return toLocalDate().getChronology();
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
/*     */   public abstract D toLocalDate();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract LocalTime toLocalTime();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean isSupported(TemporalField paramTemporalField);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 252 */     if ((paramTemporalUnit instanceof ChronoUnit)) {
/* 253 */       return paramTemporalUnit != ChronoUnit.FOREVER;
/*     */     }
/* 255 */     return (paramTemporalUnit != null) && (paramTemporalUnit.isSupportedBy(this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDateTime<D> with(TemporalAdjuster paramTemporalAdjuster)
/*     */   {
/* 267 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.with(paramTemporalAdjuster));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoLocalDateTime<D> with(TemporalField paramTemporalField, long paramLong);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDateTime<D> plus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 285 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.plus(paramTemporalAmount));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoLocalDateTime<D> plus(long paramLong, TemporalUnit paramTemporalUnit);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDateTime<D> minus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 303 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.minus(paramTemporalAmount));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDateTime<D> minus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 313 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.minus(paramLong, paramTemporalUnit));
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
/* 338 */     if ((paramTemporalQuery == TemporalQueries.zoneId()) || (paramTemporalQuery == TemporalQueries.zone()) || (paramTemporalQuery == TemporalQueries.offset()))
/* 339 */       return null;
/* 340 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 341 */       return toLocalTime();
/* 342 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 343 */       return getChronology();
/* 344 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 345 */       return ChronoUnit.NANOS;
/*     */     }
/*     */     
/*     */ 
/* 349 */     return (R)paramTemporalQuery.queryFrom(this);
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
/*     */   public Temporal adjustInto(Temporal paramTemporal)
/*     */   {
/* 381 */     return paramTemporal.with(ChronoField.EPOCH_DAY, toLocalDate().toEpochDay()).with(ChronoField.NANO_OF_DAY, toLocalTime().toNanoOfDay());
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
/*     */   public String format(DateTimeFormatter paramDateTimeFormatter)
/*     */   {
/* 399 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 400 */     return paramDateTimeFormatter.format(this);
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
/*     */   public abstract ChronoZonedDateTime<D> atZone(ZoneId paramZoneId);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Instant toInstant(ZoneOffset paramZoneOffset)
/*     */   {
/* 447 */     return Instant.ofEpochSecond(toEpochSecond(paramZoneOffset), toLocalTime().getNano());
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
/*     */   public long toEpochSecond(ZoneOffset paramZoneOffset)
/*     */   {
/* 465 */     Objects.requireNonNull(paramZoneOffset, "offset");
/* 466 */     long l1 = toLocalDate().toEpochDay();
/* 467 */     long l2 = l1 * 86400L + toLocalTime().toSecondOfDay();
/* 468 */     l2 -= paramZoneOffset.getTotalSeconds();
/* 469 */     return l2;
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
/*     */   public int compareTo(ChronoLocalDateTime<?> paramChronoLocalDateTime)
/*     */   {
/* 501 */     int i = toLocalDate().compareTo(paramChronoLocalDateTime.toLocalDate());
/* 502 */     if (i == 0) {
/* 503 */       i = toLocalTime().compareTo(paramChronoLocalDateTime.toLocalTime());
/* 504 */       if (i == 0) {
/* 505 */         i = getChronology().compareTo(paramChronoLocalDateTime.getChronology());
/*     */       }
/*     */     }
/* 508 */     return i;
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
/*     */   public boolean isAfter(ChronoLocalDateTime<?> paramChronoLocalDateTime)
/*     */   {
/* 526 */     long l1 = toLocalDate().toEpochDay();
/* 527 */     long l2 = paramChronoLocalDateTime.toLocalDate().toEpochDay();
/*     */     
/* 529 */     return (l1 > l2) || ((l1 == l2) && (toLocalTime().toNanoOfDay() > paramChronoLocalDateTime.toLocalTime().toNanoOfDay()));
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
/*     */   public boolean isBefore(ChronoLocalDateTime<?> paramChronoLocalDateTime)
/*     */   {
/* 547 */     long l1 = toLocalDate().toEpochDay();
/* 548 */     long l2 = paramChronoLocalDateTime.toLocalDate().toEpochDay();
/*     */     
/* 550 */     return (l1 < l2) || ((l1 == l2) && (toLocalTime().toNanoOfDay() < paramChronoLocalDateTime.toLocalTime().toNanoOfDay()));
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
/*     */   public boolean isEqual(ChronoLocalDateTime<?> paramChronoLocalDateTime)
/*     */   {
/* 570 */     return (toLocalTime().toNanoOfDay() == paramChronoLocalDateTime.toLocalTime().toNanoOfDay()) && (toLocalDate().toEpochDay() == paramChronoLocalDateTime.toLocalDate().toEpochDay());
/*     */   }
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ChronoLocalDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */