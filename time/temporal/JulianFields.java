/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JulianFields
/*     */ {
/*     */   private static final long JULIAN_DAY_OFFSET = 2440588L;
/* 141 */   public static final TemporalField JULIAN_DAY = Field.JULIAN_DAY;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 182 */   public static final TemporalField MODIFIED_JULIAN_DAY = Field.MODIFIED_JULIAN_DAY;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 202 */   public static final TemporalField RATA_DIE = Field.RATA_DIE;
/*     */   
/*     */ 
/*     */ 
/*     */   private JulianFields()
/*     */   {
/* 208 */     throw new AssertionError("Not instantiable");
/*     */   }
/*     */   
/*     */ 
/*     */   private static enum Field
/*     */     implements TemporalField
/*     */   {
/* 215 */     JULIAN_DAY("JulianDay", ChronoUnit.DAYS, ChronoUnit.FOREVER, 2440588L), 
/* 216 */     MODIFIED_JULIAN_DAY("ModifiedJulianDay", ChronoUnit.DAYS, ChronoUnit.FOREVER, 40587L), 
/* 217 */     RATA_DIE("RataDie", ChronoUnit.DAYS, ChronoUnit.FOREVER, 719163L);
/*     */     
/*     */     private static final long serialVersionUID = -7501623920830201812L;
/*     */     private final transient String name;
/*     */     private final transient TemporalUnit baseUnit;
/*     */     private final transient TemporalUnit rangeUnit;
/*     */     private final transient ValueRange range;
/*     */     private final transient long offset;
/*     */     
/*     */     private Field(String paramString, TemporalUnit paramTemporalUnit1, TemporalUnit paramTemporalUnit2, long paramLong)
/*     */     {
/* 228 */       this.name = paramString;
/* 229 */       this.baseUnit = paramTemporalUnit1;
/* 230 */       this.rangeUnit = paramTemporalUnit2;
/* 231 */       this.range = ValueRange.of(-365243219162L + paramLong, 365241780471L + paramLong);
/* 232 */       this.offset = paramLong;
/*     */     }
/*     */     
/*     */ 
/*     */     public TemporalUnit getBaseUnit()
/*     */     {
/* 238 */       return this.baseUnit;
/*     */     }
/*     */     
/*     */     public TemporalUnit getRangeUnit()
/*     */     {
/* 243 */       return this.rangeUnit;
/*     */     }
/*     */     
/*     */     public boolean isDateBased()
/*     */     {
/* 248 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isTimeBased()
/*     */     {
/* 253 */       return false;
/*     */     }
/*     */     
/*     */     public ValueRange range()
/*     */     {
/* 258 */       return this.range;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isSupportedBy(TemporalAccessor paramTemporalAccessor)
/*     */     {
/* 264 */       return paramTemporalAccessor.isSupported(ChronoField.EPOCH_DAY);
/*     */     }
/*     */     
/*     */     public ValueRange rangeRefinedBy(TemporalAccessor paramTemporalAccessor)
/*     */     {
/* 269 */       if (!isSupportedBy(paramTemporalAccessor)) {
/* 270 */         throw new DateTimeException("Unsupported field: " + this);
/*     */       }
/* 272 */       return range();
/*     */     }
/*     */     
/*     */     public long getFrom(TemporalAccessor paramTemporalAccessor)
/*     */     {
/* 277 */       return paramTemporalAccessor.getLong(ChronoField.EPOCH_DAY) + this.offset;
/*     */     }
/*     */     
/*     */ 
/*     */     public <R extends Temporal> R adjustInto(R paramR, long paramLong)
/*     */     {
/* 283 */       if (!range().isValidValue(paramLong)) {
/* 284 */         throw new DateTimeException("Invalid value: " + this.name + " " + paramLong);
/*     */       }
/* 286 */       return paramR.with(ChronoField.EPOCH_DAY, Math.subtractExact(paramLong, this.offset));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public ChronoLocalDate resolve(Map<TemporalField, Long> paramMap, TemporalAccessor paramTemporalAccessor, ResolverStyle paramResolverStyle)
/*     */     {
/* 293 */       long l = ((Long)paramMap.remove(this)).longValue();
/* 294 */       Chronology localChronology = Chronology.from(paramTemporalAccessor);
/* 295 */       if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 296 */         return localChronology.dateEpochDay(Math.subtractExact(l, this.offset));
/*     */       }
/* 298 */       range().checkValidValue(l, this);
/* 299 */       return localChronology.dateEpochDay(l - this.offset);
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 305 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/temporal/JulianFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */