/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.time.zone.ZoneRules;
/*     */ import java.util.Locale;
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
/*     */ final class DateTimePrintContext
/*     */ {
/*     */   private TemporalAccessor temporal;
/*     */   private DateTimeFormatter formatter;
/*     */   private int optional;
/*     */   
/*     */   DateTimePrintContext(TemporalAccessor paramTemporalAccessor, DateTimeFormatter paramDateTimeFormatter)
/*     */   {
/* 119 */     this.temporal = adjust(paramTemporalAccessor, paramDateTimeFormatter);
/* 120 */     this.formatter = paramDateTimeFormatter;
/*     */   }
/*     */   
/*     */   private static TemporalAccessor adjust(final TemporalAccessor paramTemporalAccessor, DateTimeFormatter paramDateTimeFormatter)
/*     */   {
/* 125 */     Chronology localChronology1 = paramDateTimeFormatter.getChronology();
/* 126 */     ZoneId localZoneId1 = paramDateTimeFormatter.getZone();
/* 127 */     if ((localChronology1 == null) && (localZoneId1 == null)) {
/* 128 */       return paramTemporalAccessor;
/*     */     }
/*     */     
/*     */ 
/* 132 */     Chronology localChronology2 = (Chronology)paramTemporalAccessor.query(TemporalQueries.chronology());
/* 133 */     ZoneId localZoneId2 = (ZoneId)paramTemporalAccessor.query(TemporalQueries.zoneId());
/* 134 */     if (Objects.equals(localChronology1, localChronology2)) {
/* 135 */       localChronology1 = null;
/*     */     }
/* 137 */     if (Objects.equals(localZoneId1, localZoneId2)) {
/* 138 */       localZoneId1 = null;
/*     */     }
/* 140 */     if ((localChronology1 == null) && (localZoneId1 == null)) {
/* 141 */       return paramTemporalAccessor;
/*     */     }
/*     */     
/*     */ 
/* 145 */     final Chronology localChronology3 = localChronology1 != null ? localChronology1 : localChronology2;
/* 146 */     if (localZoneId1 != null)
/*     */     {
/* 148 */       if (paramTemporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
/* 149 */         localObject = localChronology3 != null ? localChronology3 : IsoChronology.INSTANCE;
/* 150 */         return ((Chronology)localObject).zonedDateTime(Instant.from(paramTemporalAccessor), localZoneId1);
/*     */       }
/*     */       
/* 153 */       if (((localZoneId1.normalized() instanceof ZoneOffset)) && (paramTemporalAccessor.isSupported(ChronoField.OFFSET_SECONDS)) && 
/* 154 */         (paramTemporalAccessor.get(ChronoField.OFFSET_SECONDS) != localZoneId1.getRules().getOffset(Instant.EPOCH).getTotalSeconds())) {
/* 155 */         throw new DateTimeException("Unable to apply override zone '" + localZoneId1 + "' because the temporal object being formatted has a different offset but" + " does not represent an instant: " + paramTemporalAccessor);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 160 */     final Object localObject = localZoneId1 != null ? localZoneId1 : localZoneId2;
/*     */     ChronoLocalDate localChronoLocalDate;
/* 162 */     if (localChronology1 != null) {
/* 163 */       if (paramTemporalAccessor.isSupported(ChronoField.EPOCH_DAY)) {
/* 164 */         localChronoLocalDate = localChronology3.date(paramTemporalAccessor);
/*     */       }
/*     */       else {
/* 167 */         if ((localChronology1 != IsoChronology.INSTANCE) || (localChronology2 != null)) {
/* 168 */           for (ChronoField localChronoField : ChronoField.values()) {
/* 169 */             if ((localChronoField.isDateBased()) && (paramTemporalAccessor.isSupported(localChronoField))) {
/* 170 */               throw new DateTimeException("Unable to apply override chronology '" + localChronology1 + "' because the temporal object being formatted contains date fields but" + " does not represent a whole date: " + paramTemporalAccessor);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 176 */         localChronoLocalDate = null;
/*     */       }
/*     */     } else {
/* 179 */       localChronoLocalDate = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 185 */     new TemporalAccessor()
/*     */     {
/*     */       public boolean isSupported(TemporalField paramAnonymousTemporalField) {
/* 188 */         if ((this.val$effectiveDate != null) && (paramAnonymousTemporalField.isDateBased())) {
/* 189 */           return this.val$effectiveDate.isSupported(paramAnonymousTemporalField);
/*     */         }
/* 191 */         return paramTemporalAccessor.isSupported(paramAnonymousTemporalField);
/*     */       }
/*     */       
/*     */       public ValueRange range(TemporalField paramAnonymousTemporalField) {
/* 195 */         if ((this.val$effectiveDate != null) && (paramAnonymousTemporalField.isDateBased())) {
/* 196 */           return this.val$effectiveDate.range(paramAnonymousTemporalField);
/*     */         }
/* 198 */         return paramTemporalAccessor.range(paramAnonymousTemporalField);
/*     */       }
/*     */       
/*     */       public long getLong(TemporalField paramAnonymousTemporalField) {
/* 202 */         if ((this.val$effectiveDate != null) && (paramAnonymousTemporalField.isDateBased())) {
/* 203 */           return this.val$effectiveDate.getLong(paramAnonymousTemporalField);
/*     */         }
/* 205 */         return paramTemporalAccessor.getLong(paramAnonymousTemporalField);
/*     */       }
/*     */       
/*     */       public <R> R query(TemporalQuery<R> paramAnonymousTemporalQuery)
/*     */       {
/* 210 */         if (paramAnonymousTemporalQuery == TemporalQueries.chronology()) {
/* 211 */           return localChronology3;
/*     */         }
/* 213 */         if (paramAnonymousTemporalQuery == TemporalQueries.zoneId()) {
/* 214 */           return localObject;
/*     */         }
/* 216 */         if (paramAnonymousTemporalQuery == TemporalQueries.precision()) {
/* 217 */           return (R)paramTemporalAccessor.query(paramAnonymousTemporalQuery);
/*     */         }
/* 219 */         return (R)paramAnonymousTemporalQuery.queryFrom(this);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   TemporalAccessor getTemporal()
/*     */   {
/* 231 */     return this.temporal;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Locale getLocale()
/*     */   {
/* 243 */     return this.formatter.getLocale();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   DecimalStyle getDecimalStyle()
/*     */   {
/* 254 */     return this.formatter.getDecimalStyle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void startOptional()
/*     */   {
/* 262 */     this.optional += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void endOptional()
/*     */   {
/* 269 */     this.optional -= 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   <R> R getValue(TemporalQuery<R> paramTemporalQuery)
/*     */   {
/* 280 */     Object localObject = this.temporal.query(paramTemporalQuery);
/* 281 */     if ((localObject == null) && (this.optional == 0)) {
/* 282 */       throw new DateTimeException("Unable to extract value: " + this.temporal.getClass());
/*     */     }
/* 284 */     return (R)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Long getValue(TemporalField paramTemporalField)
/*     */   {
/*     */     try
/*     */     {
/* 298 */       return Long.valueOf(this.temporal.getLong(paramTemporalField));
/*     */     } catch (DateTimeException localDateTimeException) {
/* 300 */       if (this.optional > 0) {
/* 301 */         return null;
/*     */       }
/* 303 */       throw localDateTimeException;
/*     */     }
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
/* 315 */     return this.temporal.toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/format/DateTimePrintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */