/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.Period;
/*     */ import java.time.ZoneId;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
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
/*     */ public final class ThaiBuddhistDate
/*     */   extends ChronoLocalDateImpl<ThaiBuddhistDate>
/*     */   implements ChronoLocalDate, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8722293800195731463L;
/*     */   private final transient LocalDate isoDate;
/*     */   
/*     */   public static ThaiBuddhistDate now()
/*     */   {
/* 133 */     return now(Clock.systemDefaultZone());
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
/*     */   public static ThaiBuddhistDate now(ZoneId paramZoneId)
/*     */   {
/* 149 */     return now(Clock.system(paramZoneId));
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
/*     */   public static ThaiBuddhistDate now(Clock paramClock)
/*     */   {
/* 164 */     return new ThaiBuddhistDate(LocalDate.now(paramClock));
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
/*     */   public static ThaiBuddhistDate of(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 182 */     return new ThaiBuddhistDate(LocalDate.of(paramInt1 - 543, paramInt2, paramInt3));
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
/*     */   public static ThaiBuddhistDate from(TemporalAccessor paramTemporalAccessor)
/*     */   {
/* 203 */     return ThaiBuddhistChronology.INSTANCE.date(paramTemporalAccessor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   ThaiBuddhistDate(LocalDate paramLocalDate)
/*     */   {
/* 213 */     Objects.requireNonNull(paramLocalDate, "isoDate");
/* 214 */     this.isoDate = paramLocalDate;
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
/*     */   public ThaiBuddhistChronology getChronology()
/*     */   {
/* 228 */     return ThaiBuddhistChronology.INSTANCE;
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
/*     */   public ThaiBuddhistEra getEra()
/*     */   {
/* 241 */     return getProlepticYear() >= 1 ? ThaiBuddhistEra.BE : ThaiBuddhistEra.BEFORE_BE;
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
/*     */   public int lengthOfMonth()
/*     */   {
/* 254 */     return this.isoDate.lengthOfMonth();
/*     */   }
/*     */   
/*     */ 
/*     */   public ValueRange range(TemporalField paramTemporalField)
/*     */   {
/* 260 */     if ((paramTemporalField instanceof ChronoField)) {
/* 261 */       if (isSupported(paramTemporalField)) {
/* 262 */         ChronoField localChronoField = (ChronoField)paramTemporalField;
/* 263 */         switch (localChronoField) {
/*     */         case DAY_OF_MONTH: 
/*     */         case DAY_OF_YEAR: 
/*     */         case ALIGNED_WEEK_OF_MONTH: 
/* 267 */           return this.isoDate.range(paramTemporalField);
/*     */         case YEAR_OF_ERA: 
/* 269 */           ValueRange localValueRange = ChronoField.YEAR.range();
/* 270 */           long l = getProlepticYear() <= 0 ? -(localValueRange.getMinimum() + 543L) + 1L : localValueRange.getMaximum() + 543L;
/* 271 */           return ValueRange.of(1L, l);
/*     */         }
/*     */         
/* 274 */         return getChronology().range(localChronoField);
/*     */       }
/* 276 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 278 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField)
/*     */   {
/* 283 */     if ((paramTemporalField instanceof ChronoField)) {
/* 284 */       switch ((ChronoField)paramTemporalField) {
/*     */       case PROLEPTIC_MONTH: 
/* 286 */         return getProlepticMonth();
/*     */       case YEAR_OF_ERA: 
/* 288 */         int i = getProlepticYear();
/* 289 */         return i >= 1 ? i : 1 - i;
/*     */       
/*     */       case YEAR: 
/* 292 */         return getProlepticYear();
/*     */       case ERA: 
/* 294 */         return getProlepticYear() >= 1 ? 1 : 0;
/*     */       }
/* 296 */       return this.isoDate.getLong(paramTemporalField);
/*     */     }
/* 298 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */   
/*     */   private long getProlepticMonth() {
/* 302 */     return getProlepticYear() * 12L + this.isoDate.getMonthValue() - 1L;
/*     */   }
/*     */   
/*     */   private int getProlepticYear() {
/* 306 */     return this.isoDate.getYear() + 543;
/*     */   }
/*     */   
/*     */ 
/*     */   public ThaiBuddhistDate with(TemporalField paramTemporalField, long paramLong)
/*     */   {
/* 312 */     if ((paramTemporalField instanceof ChronoField)) {
/* 313 */       ChronoField localChronoField = (ChronoField)paramTemporalField;
/* 314 */       if (getLong(localChronoField) == paramLong) {
/* 315 */         return this;
/*     */       }
/* 317 */       switch (localChronoField) {
/*     */       case PROLEPTIC_MONTH: 
/* 319 */         getChronology().range(localChronoField).checkValidValue(paramLong, localChronoField);
/* 320 */         return plusMonths(paramLong - getProlepticMonth());
/*     */       case YEAR_OF_ERA: 
/*     */       case YEAR: 
/*     */       case ERA: 
/* 324 */         int i = getChronology().range(localChronoField).checkValidIntValue(paramLong, localChronoField);
/* 325 */         switch (localChronoField) {
/*     */         case YEAR_OF_ERA: 
/* 327 */           return with(this.isoDate.withYear((getProlepticYear() >= 1 ? i : 1 - i) - 543));
/*     */         case YEAR: 
/* 329 */           return with(this.isoDate.withYear(i - 543));
/*     */         case ERA: 
/* 331 */           return with(this.isoDate.withYear(1 - getProlepticYear() - 543));
/*     */         }
/*     */         break;
/*     */       }
/* 335 */       return with(this.isoDate.with(paramTemporalField, paramLong));
/*     */     }
/* 337 */     return (ThaiBuddhistDate)super.with(paramTemporalField, paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThaiBuddhistDate with(TemporalAdjuster paramTemporalAdjuster)
/*     */   {
/* 347 */     return (ThaiBuddhistDate)super.with(paramTemporalAdjuster);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThaiBuddhistDate plus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 357 */     return (ThaiBuddhistDate)super.plus(paramTemporalAmount);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThaiBuddhistDate minus(TemporalAmount paramTemporalAmount)
/*     */   {
/* 367 */     return (ThaiBuddhistDate)super.minus(paramTemporalAmount);
/*     */   }
/*     */   
/*     */ 
/*     */   ThaiBuddhistDate plusYears(long paramLong)
/*     */   {
/* 373 */     return with(this.isoDate.plusYears(paramLong));
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate plusMonths(long paramLong)
/*     */   {
/* 378 */     return with(this.isoDate.plusMonths(paramLong));
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate plusWeeks(long paramLong)
/*     */   {
/* 383 */     return (ThaiBuddhistDate)super.plusWeeks(paramLong);
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate plusDays(long paramLong)
/*     */   {
/* 388 */     return with(this.isoDate.plusDays(paramLong));
/*     */   }
/*     */   
/*     */   public ThaiBuddhistDate plus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 393 */     return (ThaiBuddhistDate)super.plus(paramLong, paramTemporalUnit);
/*     */   }
/*     */   
/*     */   public ThaiBuddhistDate minus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 398 */     return (ThaiBuddhistDate)super.minus(paramLong, paramTemporalUnit);
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate minusYears(long paramLong)
/*     */   {
/* 403 */     return (ThaiBuddhistDate)super.minusYears(paramLong);
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate minusMonths(long paramLong)
/*     */   {
/* 408 */     return (ThaiBuddhistDate)super.minusMonths(paramLong);
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate minusWeeks(long paramLong)
/*     */   {
/* 413 */     return (ThaiBuddhistDate)super.minusWeeks(paramLong);
/*     */   }
/*     */   
/*     */   ThaiBuddhistDate minusDays(long paramLong)
/*     */   {
/* 418 */     return (ThaiBuddhistDate)super.minusDays(paramLong);
/*     */   }
/*     */   
/*     */   private ThaiBuddhistDate with(LocalDate paramLocalDate) {
/* 422 */     return paramLocalDate.equals(this.isoDate) ? this : new ThaiBuddhistDate(paramLocalDate);
/*     */   }
/*     */   
/*     */ 
/*     */   public final ChronoLocalDateTime<ThaiBuddhistDate> atTime(LocalTime paramLocalTime)
/*     */   {
/* 428 */     return super.atTime(paramLocalTime);
/*     */   }
/*     */   
/*     */   public ChronoPeriod until(ChronoLocalDate paramChronoLocalDate)
/*     */   {
/* 433 */     Period localPeriod = this.isoDate.until(paramChronoLocalDate);
/* 434 */     return getChronology().period(localPeriod.getYears(), localPeriod.getMonths(), localPeriod.getDays());
/*     */   }
/*     */   
/*     */   public long toEpochDay()
/*     */   {
/* 439 */     return this.isoDate.toEpochDay();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 457 */     if (this == paramObject) {
/* 458 */       return true;
/*     */     }
/* 460 */     if ((paramObject instanceof ThaiBuddhistDate)) {
/* 461 */       ThaiBuddhistDate localThaiBuddhistDate = (ThaiBuddhistDate)paramObject;
/* 462 */       return this.isoDate.equals(localThaiBuddhistDate.isoDate);
/*     */     }
/* 464 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 474 */     return getChronology().getId().hashCode() ^ this.isoDate.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 485 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */   private Object writeReplace()
/*     */   {
/* 502 */     return new Ser((byte)8, this);
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException
/*     */   {
/* 507 */     paramDataOutput.writeInt(get(ChronoField.YEAR));
/* 508 */     paramDataOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
/* 509 */     paramDataOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
/*     */   }
/*     */   
/*     */   static ThaiBuddhistDate readExternal(DataInput paramDataInput) throws IOException {
/* 513 */     int i = paramDataInput.readInt();
/* 514 */     int j = paramDataInput.readByte();
/* 515 */     int k = paramDataInput.readByte();
/* 516 */     return ThaiBuddhistChronology.INSTANCE.date(i, j, k);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ThaiBuddhistDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */