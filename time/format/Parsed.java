/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.Period;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.ChronoLocalDateTime;
/*     */ import java.time.chrono.ChronoZonedDateTime;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Parsed
/*     */   implements TemporalAccessor
/*     */ {
/* 129 */   final Map<TemporalField, Long> fieldValues = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   ZoneId zone;
/*     */   
/*     */ 
/*     */ 
/*     */   Chronology chrono;
/*     */   
/*     */ 
/*     */ 
/*     */   boolean leapSecond;
/*     */   
/*     */ 
/*     */ 
/*     */   private ResolverStyle resolverStyle;
/*     */   
/*     */ 
/*     */ 
/*     */   private ChronoLocalDate date;
/*     */   
/*     */ 
/*     */ 
/*     */   private LocalTime time;
/*     */   
/*     */ 
/*     */ 
/* 157 */   Period excessDays = Period.ZERO;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Parsed copy()
/*     */   {
/* 170 */     Parsed localParsed = new Parsed();
/* 171 */     localParsed.fieldValues.putAll(this.fieldValues);
/* 172 */     localParsed.zone = this.zone;
/* 173 */     localParsed.chrono = this.chrono;
/* 174 */     localParsed.leapSecond = this.leapSecond;
/* 175 */     return localParsed;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isSupported(TemporalField paramTemporalField)
/*     */   {
/* 181 */     if ((this.fieldValues.containsKey(paramTemporalField)) || ((this.date != null) && 
/* 182 */       (this.date.isSupported(paramTemporalField))) || ((this.time != null) && 
/* 183 */       (this.time.isSupported(paramTemporalField)))) {
/* 184 */       return true;
/*     */     }
/* 186 */     return (paramTemporalField != null) && (!(paramTemporalField instanceof ChronoField)) && (paramTemporalField.isSupportedBy(this));
/*     */   }
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField)
/*     */   {
/* 191 */     Objects.requireNonNull(paramTemporalField, "field");
/* 192 */     Long localLong = (Long)this.fieldValues.get(paramTemporalField);
/* 193 */     if (localLong != null) {
/* 194 */       return localLong.longValue();
/*     */     }
/* 196 */     if ((this.date != null) && (this.date.isSupported(paramTemporalField))) {
/* 197 */       return this.date.getLong(paramTemporalField);
/*     */     }
/* 199 */     if ((this.time != null) && (this.time.isSupported(paramTemporalField))) {
/* 200 */       return this.time.getLong(paramTemporalField);
/*     */     }
/* 202 */     if ((paramTemporalField instanceof ChronoField)) {
/* 203 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 205 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public <R> R query(TemporalQuery<R> paramTemporalQuery)
/*     */   {
/* 211 */     if (paramTemporalQuery == TemporalQueries.zoneId())
/* 212 */       return this.zone;
/* 213 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 214 */       return this.chrono;
/* 215 */     if (paramTemporalQuery == TemporalQueries.localDate())
/* 216 */       return this.date != null ? LocalDate.from(this.date) : null;
/* 217 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 218 */       return this.time;
/* 219 */     if ((paramTemporalQuery == TemporalQueries.zone()) || (paramTemporalQuery == TemporalQueries.offset()))
/* 220 */       return (R)paramTemporalQuery.queryFrom(this);
/* 221 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 222 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 226 */     return (R)paramTemporalQuery.queryFrom(this);
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
/*     */   TemporalAccessor resolve(ResolverStyle paramResolverStyle, Set<TemporalField> paramSet)
/*     */   {
/* 240 */     if (paramSet != null) {
/* 241 */       this.fieldValues.keySet().retainAll(paramSet);
/*     */     }
/* 243 */     this.resolverStyle = paramResolverStyle;
/* 244 */     resolveFields();
/* 245 */     resolveTimeLenient();
/* 246 */     crossCheck();
/* 247 */     resolvePeriod();
/* 248 */     resolveFractional();
/* 249 */     resolveInstant();
/* 250 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   private void resolveFields()
/*     */   {
/* 256 */     resolveInstantFields();
/* 257 */     resolveDateFields();
/* 258 */     resolveTimeFields();
/*     */     
/*     */ 
/*     */ 
/* 262 */     if (this.fieldValues.size() > 0) {
/* 263 */       int i = 0;
/*     */       label320:
/* 265 */       if (i < 50) {
/* 266 */         Iterator localIterator = this.fieldValues.entrySet().iterator(); for (;;) { if (!localIterator.hasNext()) break label320; Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 267 */           TemporalField localTemporalField = (TemporalField)localEntry.getKey();
/* 268 */           Object localObject1 = localTemporalField.resolve(this.fieldValues, this, this.resolverStyle);
/* 269 */           if (localObject1 != null) { Object localObject2;
/* 270 */             if ((localObject1 instanceof ChronoZonedDateTime)) {
/* 271 */               localObject2 = (ChronoZonedDateTime)localObject1;
/* 272 */               if (this.zone == null) {
/* 273 */                 this.zone = ((ChronoZonedDateTime)localObject2).getZone();
/* 274 */               } else if (!this.zone.equals(((ChronoZonedDateTime)localObject2).getZone())) {
/* 275 */                 throw new DateTimeException("ChronoZonedDateTime must use the effective parsed zone: " + this.zone);
/*     */               }
/* 277 */               localObject1 = ((ChronoZonedDateTime)localObject2).toLocalDateTime();
/*     */             }
/* 279 */             if ((localObject1 instanceof ChronoLocalDateTime)) {
/* 280 */               localObject2 = (ChronoLocalDateTime)localObject1;
/* 281 */               updateCheckConflict(((ChronoLocalDateTime)localObject2).toLocalTime(), Period.ZERO);
/* 282 */               updateCheckConflict(((ChronoLocalDateTime)localObject2).toLocalDate());
/* 283 */               i++;
/* 284 */               break;
/*     */             }
/* 286 */             if ((localObject1 instanceof ChronoLocalDate)) {
/* 287 */               updateCheckConflict((ChronoLocalDate)localObject1);
/* 288 */               i++;
/* 289 */               break;
/*     */             }
/* 291 */             if ((localObject1 instanceof LocalTime)) {
/* 292 */               updateCheckConflict((LocalTime)localObject1, Period.ZERO);
/* 293 */               i++;
/* 294 */               break;
/*     */             }
/* 296 */             throw new DateTimeException("Method resolve() can only return ChronoZonedDateTime, ChronoLocalDateTime, ChronoLocalDate or LocalTime");
/*     */           }
/* 298 */           if (!this.fieldValues.containsKey(localTemporalField)) {
/* 299 */             i++;
/* 300 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 305 */       if (i == 50) {
/* 306 */         throw new DateTimeException("One of the parsed fields has an incorrectly implemented resolve method");
/*     */       }
/*     */       
/* 309 */       if (i > 0) {
/* 310 */         resolveInstantFields();
/* 311 */         resolveDateFields();
/* 312 */         resolveTimeFields();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateCheckConflict(TemporalField paramTemporalField1, TemporalField paramTemporalField2, Long paramLong) {
/* 318 */     Long localLong = (Long)this.fieldValues.put(paramTemporalField2, paramLong);
/* 319 */     if ((localLong != null) && (localLong.longValue() != paramLong.longValue())) {
/* 320 */       throw new DateTimeException("Conflict found: " + paramTemporalField2 + " " + localLong + " differs from " + paramTemporalField2 + " " + paramLong + " while resolving  " + paramTemporalField1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void resolveInstantFields()
/*     */   {
/* 329 */     if (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS)) {
/* 330 */       if (this.zone != null) {
/* 331 */         resolveInstantFields0(this.zone);
/*     */       } else {
/* 333 */         Long localLong = (Long)this.fieldValues.get(ChronoField.OFFSET_SECONDS);
/* 334 */         if (localLong != null) {
/* 335 */           ZoneOffset localZoneOffset = ZoneOffset.ofTotalSeconds(localLong.intValue());
/* 336 */           resolveInstantFields0(localZoneOffset);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void resolveInstantFields0(ZoneId paramZoneId) {
/* 343 */     Instant localInstant = Instant.ofEpochSecond(((Long)this.fieldValues.remove(ChronoField.INSTANT_SECONDS)).longValue());
/* 344 */     ChronoZonedDateTime localChronoZonedDateTime = this.chrono.zonedDateTime(localInstant, paramZoneId);
/* 345 */     updateCheckConflict(localChronoZonedDateTime.toLocalDate());
/* 346 */     updateCheckConflict(ChronoField.INSTANT_SECONDS, ChronoField.SECOND_OF_DAY, Long.valueOf(localChronoZonedDateTime.toLocalTime().toSecondOfDay()));
/*     */   }
/*     */   
/*     */   private void resolveDateFields()
/*     */   {
/* 351 */     updateCheckConflict(this.chrono.resolveDate(this.fieldValues, this.resolverStyle));
/*     */   }
/*     */   
/*     */   private void updateCheckConflict(ChronoLocalDate paramChronoLocalDate) {
/* 355 */     if (this.date != null) {
/* 356 */       if ((paramChronoLocalDate != null) && (!this.date.equals(paramChronoLocalDate))) {
/* 357 */         throw new DateTimeException("Conflict found: Fields resolved to two different dates: " + this.date + " " + paramChronoLocalDate);
/*     */       }
/* 359 */     } else if (paramChronoLocalDate != null) {
/* 360 */       if (!this.chrono.equals(paramChronoLocalDate.getChronology())) {
/* 361 */         throw new DateTimeException("ChronoLocalDate must use the effective parsed chronology: " + this.chrono);
/*     */       }
/* 363 */       this.date = paramChronoLocalDate;
/*     */     }
/*     */   }
/*     */   
/*     */   private void resolveTimeFields()
/*     */   {
/*     */     long l1;
/* 370 */     if (this.fieldValues.containsKey(ChronoField.CLOCK_HOUR_OF_DAY))
/*     */     {
/* 372 */       l1 = ((Long)this.fieldValues.remove(ChronoField.CLOCK_HOUR_OF_DAY)).longValue();
/* 373 */       if ((this.resolverStyle == ResolverStyle.STRICT) || ((this.resolverStyle == ResolverStyle.SMART) && (l1 != 0L))) {
/* 374 */         ChronoField.CLOCK_HOUR_OF_DAY.checkValidValue(l1);
/*     */       }
/* 376 */       updateCheckConflict(ChronoField.CLOCK_HOUR_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(l1 == 24L ? 0L : l1));
/*     */     }
/* 378 */     if (this.fieldValues.containsKey(ChronoField.CLOCK_HOUR_OF_AMPM))
/*     */     {
/* 380 */       l1 = ((Long)this.fieldValues.remove(ChronoField.CLOCK_HOUR_OF_AMPM)).longValue();
/* 381 */       if ((this.resolverStyle == ResolverStyle.STRICT) || ((this.resolverStyle == ResolverStyle.SMART) && (l1 != 0L))) {
/* 382 */         ChronoField.CLOCK_HOUR_OF_AMPM.checkValidValue(l1);
/*     */       }
/* 384 */       updateCheckConflict(ChronoField.CLOCK_HOUR_OF_AMPM, ChronoField.HOUR_OF_AMPM, Long.valueOf(l1 == 12L ? 0L : l1)); }
/*     */     long l2;
/* 386 */     if ((this.fieldValues.containsKey(ChronoField.AMPM_OF_DAY)) && (this.fieldValues.containsKey(ChronoField.HOUR_OF_AMPM))) {
/* 387 */       l1 = ((Long)this.fieldValues.remove(ChronoField.AMPM_OF_DAY)).longValue();
/* 388 */       l2 = ((Long)this.fieldValues.remove(ChronoField.HOUR_OF_AMPM)).longValue();
/* 389 */       if (this.resolverStyle == ResolverStyle.LENIENT) {
/* 390 */         updateCheckConflict(ChronoField.AMPM_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(Math.addExact(Math.multiplyExact(l1, 12L), l2)));
/*     */       } else {
/* 392 */         ChronoField.AMPM_OF_DAY.checkValidValue(l1);
/* 393 */         ChronoField.HOUR_OF_AMPM.checkValidValue(l1);
/* 394 */         updateCheckConflict(ChronoField.AMPM_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(l1 * 12L + l2));
/*     */       }
/*     */     }
/* 397 */     if (this.fieldValues.containsKey(ChronoField.NANO_OF_DAY)) {
/* 398 */       l1 = ((Long)this.fieldValues.remove(ChronoField.NANO_OF_DAY)).longValue();
/* 399 */       if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 400 */         ChronoField.NANO_OF_DAY.checkValidValue(l1);
/*     */       }
/* 402 */       updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(l1 / 3600000000000L));
/* 403 */       updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.MINUTE_OF_HOUR, Long.valueOf(l1 / 60000000000L % 60L));
/* 404 */       updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.SECOND_OF_MINUTE, Long.valueOf(l1 / 1000000000L % 60L));
/* 405 */       updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.NANO_OF_SECOND, Long.valueOf(l1 % 1000000000L));
/*     */     }
/* 407 */     if (this.fieldValues.containsKey(ChronoField.MICRO_OF_DAY)) {
/* 408 */       l1 = ((Long)this.fieldValues.remove(ChronoField.MICRO_OF_DAY)).longValue();
/* 409 */       if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 410 */         ChronoField.MICRO_OF_DAY.checkValidValue(l1);
/*     */       }
/* 412 */       updateCheckConflict(ChronoField.MICRO_OF_DAY, ChronoField.SECOND_OF_DAY, Long.valueOf(l1 / 1000000L));
/* 413 */       updateCheckConflict(ChronoField.MICRO_OF_DAY, ChronoField.MICRO_OF_SECOND, Long.valueOf(l1 % 1000000L));
/*     */     }
/* 415 */     if (this.fieldValues.containsKey(ChronoField.MILLI_OF_DAY)) {
/* 416 */       l1 = ((Long)this.fieldValues.remove(ChronoField.MILLI_OF_DAY)).longValue();
/* 417 */       if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 418 */         ChronoField.MILLI_OF_DAY.checkValidValue(l1);
/*     */       }
/* 420 */       updateCheckConflict(ChronoField.MILLI_OF_DAY, ChronoField.SECOND_OF_DAY, Long.valueOf(l1 / 1000L));
/* 421 */       updateCheckConflict(ChronoField.MILLI_OF_DAY, ChronoField.MILLI_OF_SECOND, Long.valueOf(l1 % 1000L));
/*     */     }
/* 423 */     if (this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY)) {
/* 424 */       l1 = ((Long)this.fieldValues.remove(ChronoField.SECOND_OF_DAY)).longValue();
/* 425 */       if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 426 */         ChronoField.SECOND_OF_DAY.checkValidValue(l1);
/*     */       }
/* 428 */       updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(l1 / 3600L));
/* 429 */       updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.MINUTE_OF_HOUR, Long.valueOf(l1 / 60L % 60L));
/* 430 */       updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.SECOND_OF_MINUTE, Long.valueOf(l1 % 60L));
/*     */     }
/* 432 */     if (this.fieldValues.containsKey(ChronoField.MINUTE_OF_DAY)) {
/* 433 */       l1 = ((Long)this.fieldValues.remove(ChronoField.MINUTE_OF_DAY)).longValue();
/* 434 */       if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 435 */         ChronoField.MINUTE_OF_DAY.checkValidValue(l1);
/*     */       }
/* 437 */       updateCheckConflict(ChronoField.MINUTE_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(l1 / 60L));
/* 438 */       updateCheckConflict(ChronoField.MINUTE_OF_DAY, ChronoField.MINUTE_OF_HOUR, Long.valueOf(l1 % 60L));
/*     */     }
/*     */     
/*     */ 
/* 442 */     if (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
/* 443 */       l1 = ((Long)this.fieldValues.get(ChronoField.NANO_OF_SECOND)).longValue();
/* 444 */       if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 445 */         ChronoField.NANO_OF_SECOND.checkValidValue(l1);
/*     */       }
/* 447 */       if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
/* 448 */         l2 = ((Long)this.fieldValues.remove(ChronoField.MICRO_OF_SECOND)).longValue();
/* 449 */         if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 450 */           ChronoField.MICRO_OF_SECOND.checkValidValue(l2);
/*     */         }
/* 452 */         l1 = l2 * 1000L + l1 % 1000L;
/* 453 */         updateCheckConflict(ChronoField.MICRO_OF_SECOND, ChronoField.NANO_OF_SECOND, Long.valueOf(l1));
/*     */       }
/* 455 */       if (this.fieldValues.containsKey(ChronoField.MILLI_OF_SECOND)) {
/* 456 */         l2 = ((Long)this.fieldValues.remove(ChronoField.MILLI_OF_SECOND)).longValue();
/* 457 */         if (this.resolverStyle != ResolverStyle.LENIENT) {
/* 458 */           ChronoField.MILLI_OF_SECOND.checkValidValue(l2);
/*     */         }
/* 460 */         updateCheckConflict(ChronoField.MILLI_OF_SECOND, ChronoField.NANO_OF_SECOND, Long.valueOf(l2 * 1000000L + l1 % 1000000L));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 465 */     if ((this.fieldValues.containsKey(ChronoField.HOUR_OF_DAY)) && (this.fieldValues.containsKey(ChronoField.MINUTE_OF_HOUR)) && 
/* 466 */       (this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE)) && (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND))) {
/* 467 */       l1 = ((Long)this.fieldValues.remove(ChronoField.HOUR_OF_DAY)).longValue();
/* 468 */       l2 = ((Long)this.fieldValues.remove(ChronoField.MINUTE_OF_HOUR)).longValue();
/* 469 */       long l3 = ((Long)this.fieldValues.remove(ChronoField.SECOND_OF_MINUTE)).longValue();
/* 470 */       long l4 = ((Long)this.fieldValues.remove(ChronoField.NANO_OF_SECOND)).longValue();
/* 471 */       resolveTime(l1, l2, l3, l4);
/*     */     }
/*     */   }
/*     */   
/*     */   private void resolveTimeLenient()
/*     */   {
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 480 */     if (this.time == null) {
/*     */       long l1;
/* 482 */       if (this.fieldValues.containsKey(ChronoField.MILLI_OF_SECOND)) {
/* 483 */         l1 = ((Long)this.fieldValues.remove(ChronoField.MILLI_OF_SECOND)).longValue();
/* 484 */         if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND))
/*     */         {
/* 486 */           long l2 = l1 * 1000L + ((Long)this.fieldValues.get(ChronoField.MICRO_OF_SECOND)).longValue() % 1000L;
/* 487 */           updateCheckConflict(ChronoField.MILLI_OF_SECOND, ChronoField.MICRO_OF_SECOND, Long.valueOf(l2));
/* 488 */           this.fieldValues.remove(ChronoField.MICRO_OF_SECOND);
/* 489 */           this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(l2 * 1000L));
/*     */         }
/*     */         else {
/* 492 */           this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(l1 * 1000000L));
/*     */         }
/* 494 */       } else if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND))
/*     */       {
/* 496 */         l1 = ((Long)this.fieldValues.remove(ChronoField.MICRO_OF_SECOND)).longValue();
/* 497 */         this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(l1 * 1000L));
/*     */       }
/*     */       
/*     */ 
/* 501 */       localObject1 = (Long)this.fieldValues.get(ChronoField.HOUR_OF_DAY);
/* 502 */       if (localObject1 != null) {
/* 503 */         localObject2 = (Long)this.fieldValues.get(ChronoField.MINUTE_OF_HOUR);
/* 504 */         localObject3 = (Long)this.fieldValues.get(ChronoField.SECOND_OF_MINUTE);
/* 505 */         Long localLong = (Long)this.fieldValues.get(ChronoField.NANO_OF_SECOND);
/*     */         
/*     */ 
/* 508 */         if (((localObject2 == null) && ((localObject3 != null) || (localLong != null))) || ((localObject2 != null) && (localObject3 == null) && (localLong != null)))
/*     */         {
/* 510 */           return;
/*     */         }
/*     */         
/*     */ 
/* 514 */         long l3 = localObject2 != null ? ((Long)localObject2).longValue() : 0L;
/* 515 */         long l4 = localObject3 != null ? ((Long)localObject3).longValue() : 0L;
/* 516 */         long l5 = localLong != null ? localLong.longValue() : 0L;
/* 517 */         resolveTime(((Long)localObject1).longValue(), l3, l4, l5);
/* 518 */         this.fieldValues.remove(ChronoField.HOUR_OF_DAY);
/* 519 */         this.fieldValues.remove(ChronoField.MINUTE_OF_HOUR);
/* 520 */         this.fieldValues.remove(ChronoField.SECOND_OF_MINUTE);
/* 521 */         this.fieldValues.remove(ChronoField.NANO_OF_SECOND);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 526 */     if ((this.resolverStyle != ResolverStyle.LENIENT) && (this.fieldValues.size() > 0)) {
/* 527 */       for (localObject1 = this.fieldValues.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/* 528 */         localObject3 = (TemporalField)((Map.Entry)localObject2).getKey();
/* 529 */         if (((localObject3 instanceof ChronoField)) && (((TemporalField)localObject3).isTimeBased()))
/* 530 */           ((ChronoField)localObject3).checkValidValue(((Long)((Map.Entry)localObject2).getValue()).longValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void resolveTime(long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
/*     */     int k;
/* 537 */     if (this.resolverStyle == ResolverStyle.LENIENT) {
/* 538 */       long l1 = Math.multiplyExact(paramLong1, 3600000000000L);
/* 539 */       l1 = Math.addExact(l1, Math.multiplyExact(paramLong2, 60000000000L));
/* 540 */       l1 = Math.addExact(l1, Math.multiplyExact(paramLong3, 1000000000L));
/* 541 */       l1 = Math.addExact(l1, paramLong4);
/* 542 */       k = (int)Math.floorDiv(l1, 86400000000000L);
/* 543 */       long l2 = Math.floorMod(l1, 86400000000000L);
/* 544 */       updateCheckConflict(LocalTime.ofNanoOfDay(l2), Period.ofDays(k));
/*     */     } else {
/* 546 */       int i = ChronoField.MINUTE_OF_HOUR.checkValidIntValue(paramLong2);
/* 547 */       int j = ChronoField.NANO_OF_SECOND.checkValidIntValue(paramLong4);
/*     */       
/* 549 */       if ((this.resolverStyle == ResolverStyle.SMART) && (paramLong1 == 24L) && (i == 0) && (paramLong3 == 0L) && (j == 0)) {
/* 550 */         updateCheckConflict(LocalTime.MIDNIGHT, Period.ofDays(1));
/*     */       } else {
/* 552 */         k = ChronoField.HOUR_OF_DAY.checkValidIntValue(paramLong1);
/* 553 */         int m = ChronoField.SECOND_OF_MINUTE.checkValidIntValue(paramLong3);
/* 554 */         updateCheckConflict(LocalTime.of(k, i, m, j), Period.ZERO);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void resolvePeriod()
/*     */   {
/* 561 */     if ((this.date != null) && (this.time != null) && (!this.excessDays.isZero())) {
/* 562 */       this.date = this.date.plus(this.excessDays);
/* 563 */       this.excessDays = Period.ZERO;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void resolveFractional()
/*     */   {
/* 570 */     if ((this.time == null) && (
/* 571 */       (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS)) || 
/* 572 */       (this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY)) || 
/* 573 */       (this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE)))) {
/* 574 */       if (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
/* 575 */         long l = ((Long)this.fieldValues.get(ChronoField.NANO_OF_SECOND)).longValue();
/* 576 */         this.fieldValues.put(ChronoField.MICRO_OF_SECOND, Long.valueOf(l / 1000L));
/* 577 */         this.fieldValues.put(ChronoField.MILLI_OF_SECOND, Long.valueOf(l / 1000000L));
/*     */       } else {
/* 579 */         this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(0L));
/* 580 */         this.fieldValues.put(ChronoField.MICRO_OF_SECOND, Long.valueOf(0L));
/* 581 */         this.fieldValues.put(ChronoField.MILLI_OF_SECOND, Long.valueOf(0L));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void resolveInstant()
/*     */   {
/* 588 */     if ((this.date != null) && (this.time != null)) {
/* 589 */       if (this.zone != null) {
/* 590 */         long l1 = this.date.atTime(this.time).atZone(this.zone).getLong(ChronoField.INSTANT_SECONDS);
/* 591 */         this.fieldValues.put(ChronoField.INSTANT_SECONDS, Long.valueOf(l1));
/*     */       } else {
/* 593 */         Long localLong = (Long)this.fieldValues.get(ChronoField.OFFSET_SECONDS);
/* 594 */         if (localLong != null) {
/* 595 */           ZoneOffset localZoneOffset = ZoneOffset.ofTotalSeconds(localLong.intValue());
/* 596 */           long l2 = this.date.atTime(this.time).atZone(localZoneOffset).getLong(ChronoField.INSTANT_SECONDS);
/* 597 */           this.fieldValues.put(ChronoField.INSTANT_SECONDS, Long.valueOf(l2));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateCheckConflict(LocalTime paramLocalTime, Period paramPeriod) {
/* 604 */     if (this.time != null) {
/* 605 */       if (!this.time.equals(paramLocalTime)) {
/* 606 */         throw new DateTimeException("Conflict found: Fields resolved to different times: " + this.time + " " + paramLocalTime);
/*     */       }
/* 608 */       if ((!this.excessDays.isZero()) && (!paramPeriod.isZero()) && (!this.excessDays.equals(paramPeriod))) {
/* 609 */         throw new DateTimeException("Conflict found: Fields resolved to different excess periods: " + this.excessDays + " " + paramPeriod);
/*     */       }
/* 611 */       this.excessDays = paramPeriod;
/*     */     }
/*     */     else {
/* 614 */       this.time = paramLocalTime;
/* 615 */       this.excessDays = paramPeriod;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void crossCheck()
/*     */   {
/* 623 */     if (this.date != null) {
/* 624 */       crossCheck(this.date);
/*     */     }
/* 626 */     if (this.time != null) {
/* 627 */       crossCheck(this.time);
/* 628 */       if ((this.date != null) && (this.fieldValues.size() > 0)) {
/* 629 */         crossCheck(this.date.atTime(this.time));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void crossCheck(TemporalAccessor paramTemporalAccessor) {
/* 635 */     for (Iterator localIterator = this.fieldValues.entrySet().iterator(); localIterator.hasNext();) {
/* 636 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 637 */       TemporalField localTemporalField = (TemporalField)localEntry.getKey();
/* 638 */       if (paramTemporalAccessor.isSupported(localTemporalField)) {
/*     */         long l1;
/*     */         try {
/* 641 */           l1 = paramTemporalAccessor.getLong(localTemporalField);
/*     */         } catch (RuntimeException localRuntimeException) {}
/* 643 */         continue;
/*     */         
/* 645 */         long l2 = ((Long)localEntry.getValue()).longValue();
/* 646 */         if (l1 != l2) {
/* 647 */           throw new DateTimeException("Conflict found: Field " + localTemporalField + " " + l1 + " differs from " + localTemporalField + " " + l2 + " derived from " + paramTemporalAccessor);
/*     */         }
/*     */         
/* 650 */         localIterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 658 */     StringBuilder localStringBuilder = new StringBuilder(64);
/* 659 */     localStringBuilder.append(this.fieldValues).append(',').append(this.chrono);
/* 660 */     if (this.zone != null) {
/* 661 */       localStringBuilder.append(',').append(this.zone);
/*     */     }
/* 663 */     if ((this.date != null) || (this.time != null)) {
/* 664 */       localStringBuilder.append(" resolved to ");
/* 665 */       if (this.date != null) {
/* 666 */         localStringBuilder.append(this.date);
/* 667 */         if (this.time != null) {
/* 668 */           localStringBuilder.append('T').append(this.time);
/*     */         }
/*     */       } else {
/* 671 */         localStringBuilder.append(this.time);
/*     */       }
/*     */     }
/* 674 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/format/Parsed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */