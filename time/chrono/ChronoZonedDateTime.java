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
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
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
/*     */ public abstract interface ChronoZonedDateTime<D extends ChronoLocalDate>
/*     */   extends Temporal, Comparable<ChronoZonedDateTime<?>>
/*     */ {
/*     */   public static Comparator<ChronoZonedDateTime<?>> timeLineOrder()
/*     */   {
/* 141 */     return AbstractChronology.INSTANT_ORDER;
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
/*     */   public static ChronoZonedDateTime<?> from(TemporalAccessor paramTemporalAccessor)
/*     */   {
/* 167 */     if ((paramTemporalAccessor instanceof ChronoZonedDateTime)) {
/* 168 */       return (ChronoZonedDateTime)paramTemporalAccessor;
/*     */     }
/* 170 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 171 */     Chronology localChronology = (Chronology)paramTemporalAccessor.query(TemporalQueries.chronology());
/* 172 */     if (localChronology == null) {
/* 173 */       throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass());
/*     */     }
/* 175 */     return localChronology.zonedDateTime(paramTemporalAccessor);
/*     */   }
/*     */   
/*     */ 
/*     */   public ValueRange range(TemporalField paramTemporalField)
/*     */   {
/* 181 */     if ((paramTemporalField instanceof ChronoField)) {
/* 182 */       if ((paramTemporalField == ChronoField.INSTANT_SECONDS) || (paramTemporalField == ChronoField.OFFSET_SECONDS)) {
/* 183 */         return paramTemporalField.range();
/*     */       }
/* 185 */       return toLocalDateTime().range(paramTemporalField);
/*     */     }
/* 187 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */   
/*     */   public int get(TemporalField paramTemporalField)
/*     */   {
/* 192 */     if ((paramTemporalField instanceof ChronoField)) {
/* 193 */       switch ((ChronoField)paramTemporalField) {
/*     */       case INSTANT_SECONDS: 
/* 195 */         throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
/*     */       case OFFSET_SECONDS: 
/* 197 */         return getOffset().getTotalSeconds();
/*     */       }
/* 199 */       return toLocalDateTime().get(paramTemporalField);
/*     */     }
/* 201 */     return super.get(paramTemporalField);
/*     */   }
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField)
/*     */   {
/* 206 */     if ((paramTemporalField instanceof ChronoField)) {
/* 207 */       switch ((ChronoField)paramTemporalField) {
/* 208 */       case INSTANT_SECONDS:  return toEpochSecond();
/* 209 */       case OFFSET_SECONDS:  return getOffset().getTotalSeconds();
/*     */       }
/* 211 */       return toLocalDateTime().getLong(paramTemporalField);
/*     */     }
/* 213 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public D toLocalDate()
/*     */   {
/* 225 */     return toLocalDateTime().toLocalDate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LocalTime toLocalTime()
/*     */   {
/* 237 */     return toLocalDateTime().toLocalTime();
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
/*     */   public abstract ChronoLocalDateTime<D> toLocalDateTime();
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
/* 259 */     return toLocalDate().getChronology();
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
/*     */   public abstract ZoneOffset getOffset();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ZoneId getZone();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoZonedDateTime<D> withEarlierOffsetAtOverlap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoZonedDateTime<D> withLaterOffsetAtOverlap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoZonedDateTime<D> withZoneSameLocal(ZoneId paramZoneId);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoZonedDateTime<D> withZoneSameInstant(ZoneId paramZoneId);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   public boolean isSupported(TemporalUnit paramTemporalUnit)
/*     */   {
/* 399 */     if ((paramTemporalUnit instanceof ChronoUnit)) {
/* 400 */       return paramTemporalUnit != ChronoUnit.FOREVER;
/*     */     }
/* 402 */     return (paramTemporalUnit != null) && (paramTemporalUnit.isSupportedBy(this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoZonedDateTime<D> with(TemporalAdjuster paramTemporalAdjuster)
/*     */   {
/* 414 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.with(paramTemporalAdjuster));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoZonedDateTime<D> with(TemporalField paramTemporalField, long paramLong);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoZonedDateTime<D> plus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 432 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.plus(paramTemporalAmount));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoZonedDateTime<D> plus(long paramLong, TemporalUnit paramTemporalUnit);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoZonedDateTime<D> minus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 450 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.minus(paramTemporalAmount));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoZonedDateTime<D> minus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 460 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.minus(paramLong, paramTemporalUnit));
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
/* 485 */     if ((paramTemporalQuery == TemporalQueries.zone()) || (paramTemporalQuery == TemporalQueries.zoneId()))
/* 486 */       return getZone();
/* 487 */     if (paramTemporalQuery == TemporalQueries.offset())
/* 488 */       return getOffset();
/* 489 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 490 */       return toLocalTime();
/* 491 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 492 */       return getChronology();
/* 493 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 494 */       return ChronoUnit.NANOS;
/*     */     }
/*     */     
/*     */ 
/* 498 */     return (R)paramTemporalQuery.queryFrom(this);
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
/* 516 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 517 */     return paramDateTimeFormatter.format(this);
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
/*     */   public Instant toInstant()
/*     */   {
/* 532 */     return Instant.ofEpochSecond(toEpochSecond(), toLocalTime().getNano());
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
/*     */   public long toEpochSecond()
/*     */   {
/* 547 */     long l1 = toLocalDate().toEpochDay();
/* 548 */     long l2 = l1 * 86400L + toLocalTime().toSecondOfDay();
/* 549 */     l2 -= getOffset().getTotalSeconds();
/* 550 */     return l2;
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
/*     */   public int compareTo(ChronoZonedDateTime<?> paramChronoZonedDateTime)
/*     */   {
/* 571 */     int i = Long.compare(toEpochSecond(), paramChronoZonedDateTime.toEpochSecond());
/* 572 */     if (i == 0) {
/* 573 */       i = toLocalTime().getNano() - paramChronoZonedDateTime.toLocalTime().getNano();
/* 574 */       if (i == 0) {
/* 575 */         i = toLocalDateTime().compareTo(paramChronoZonedDateTime.toLocalDateTime());
/* 576 */         if (i == 0) {
/* 577 */           i = getZone().getId().compareTo(paramChronoZonedDateTime.getZone().getId());
/* 578 */           if (i == 0) {
/* 579 */             i = getChronology().compareTo(paramChronoZonedDateTime.getChronology());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 584 */     return i;
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
/*     */   public boolean isBefore(ChronoZonedDateTime<?> paramChronoZonedDateTime)
/*     */   {
/* 601 */     long l1 = toEpochSecond();
/* 602 */     long l2 = paramChronoZonedDateTime.toEpochSecond();
/*     */     
/* 604 */     return (l1 < l2) || ((l1 == l2) && (toLocalTime().getNano() < paramChronoZonedDateTime.toLocalTime().getNano()));
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
/*     */   public boolean isAfter(ChronoZonedDateTime<?> paramChronoZonedDateTime)
/*     */   {
/* 621 */     long l1 = toEpochSecond();
/* 622 */     long l2 = paramChronoZonedDateTime.toEpochSecond();
/*     */     
/* 624 */     return (l1 > l2) || ((l1 == l2) && (toLocalTime().getNano() > paramChronoZonedDateTime.toLocalTime().getNano()));
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
/*     */   public boolean isEqual(ChronoZonedDateTime<?> paramChronoZonedDateTime)
/*     */   {
/* 642 */     return (toEpochSecond() == paramChronoZonedDateTime.toEpochSecond()) && (toLocalTime().getNano() == paramChronoZonedDateTime.toLocalTime().getNano());
/*     */   }
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ChronoZonedDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */