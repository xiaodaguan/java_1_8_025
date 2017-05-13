/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.Serializable;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.time.zone.ZoneOffsetTransition;
/*     */ import java.time.zone.ZoneRules;
/*     */ import java.util.List;
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
/*     */ final class ChronoZonedDateTimeImpl<D extends ChronoLocalDate>
/*     */   implements ChronoZonedDateTime<D>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5261813987200935591L;
/*     */   private final transient ChronoLocalDateTimeImpl<D> dateTime;
/*     */   private final transient ZoneOffset offset;
/*     */   private final transient ZoneId zone;
/*     */   
/*     */   static <R extends ChronoLocalDate> ChronoZonedDateTime<R> ofBest(ChronoLocalDateTimeImpl<R> paramChronoLocalDateTimeImpl, ZoneId paramZoneId, ZoneOffset paramZoneOffset)
/*     */   {
/* 136 */     Objects.requireNonNull(paramChronoLocalDateTimeImpl, "localDateTime");
/* 137 */     Objects.requireNonNull(paramZoneId, "zone");
/* 138 */     if ((paramZoneId instanceof ZoneOffset)) {
/* 139 */       return new ChronoZonedDateTimeImpl(paramChronoLocalDateTimeImpl, (ZoneOffset)paramZoneId, paramZoneId);
/*     */     }
/* 141 */     ZoneRules localZoneRules = paramZoneId.getRules();
/* 142 */     LocalDateTime localLocalDateTime = LocalDateTime.from(paramChronoLocalDateTimeImpl);
/* 143 */     List localList = localZoneRules.getValidOffsets(localLocalDateTime);
/*     */     ZoneOffset localZoneOffset;
/* 145 */     if (localList.size() == 1) {
/* 146 */       localZoneOffset = (ZoneOffset)localList.get(0);
/* 147 */     } else if (localList.size() == 0) {
/* 148 */       ZoneOffsetTransition localZoneOffsetTransition = localZoneRules.getTransition(localLocalDateTime);
/* 149 */       paramChronoLocalDateTimeImpl = paramChronoLocalDateTimeImpl.plusSeconds(localZoneOffsetTransition.getDuration().getSeconds());
/* 150 */       localZoneOffset = localZoneOffsetTransition.getOffsetAfter();
/*     */     }
/* 152 */     else if ((paramZoneOffset != null) && (localList.contains(paramZoneOffset))) {
/* 153 */       localZoneOffset = paramZoneOffset;
/*     */     } else {
/* 155 */       localZoneOffset = (ZoneOffset)localList.get(0);
/*     */     }
/*     */     
/* 158 */     Objects.requireNonNull(localZoneOffset, "offset");
/* 159 */     return new ChronoZonedDateTimeImpl(paramChronoLocalDateTimeImpl, localZoneOffset, paramZoneId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static ChronoZonedDateTimeImpl<?> ofInstant(Chronology paramChronology, Instant paramInstant, ZoneId paramZoneId)
/*     */   {
/* 171 */     ZoneRules localZoneRules = paramZoneId.getRules();
/* 172 */     ZoneOffset localZoneOffset = localZoneRules.getOffset(paramInstant);
/* 173 */     Objects.requireNonNull(localZoneOffset, "offset");
/* 174 */     LocalDateTime localLocalDateTime = LocalDateTime.ofEpochSecond(paramInstant.getEpochSecond(), paramInstant.getNano(), localZoneOffset);
/* 175 */     ChronoLocalDateTimeImpl localChronoLocalDateTimeImpl = (ChronoLocalDateTimeImpl)paramChronology.localDateTime(localLocalDateTime);
/* 176 */     return new ChronoZonedDateTimeImpl(localChronoLocalDateTimeImpl, localZoneOffset, paramZoneId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ChronoZonedDateTimeImpl<D> create(Instant paramInstant, ZoneId paramZoneId)
/*     */   {
/* 188 */     return ofInstant(getChronology(), paramInstant, paramZoneId);
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
/*     */   static <R extends ChronoLocalDate> ChronoZonedDateTimeImpl<R> ensureValid(Chronology paramChronology, Temporal paramTemporal)
/*     */   {
/* 202 */     ChronoZonedDateTimeImpl localChronoZonedDateTimeImpl = (ChronoZonedDateTimeImpl)paramTemporal;
/* 203 */     if (!paramChronology.equals(localChronoZonedDateTimeImpl.getChronology()))
/*     */     {
/* 205 */       throw new ClassCastException("Chronology mismatch, required: " + paramChronology.getId() + ", actual: " + localChronoZonedDateTimeImpl.getChronology().getId());
/*     */     }
/* 207 */     return localChronoZonedDateTimeImpl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ChronoZonedDateTimeImpl(ChronoLocalDateTimeImpl<D> paramChronoLocalDateTimeImpl, ZoneOffset paramZoneOffset, ZoneId paramZoneId)
/*     */   {
/* 219 */     this.dateTime = ((ChronoLocalDateTimeImpl)Objects.requireNonNull(paramChronoLocalDateTimeImpl, "dateTime"));
/* 220 */     this.offset = ((ZoneOffset)Objects.requireNonNull(paramZoneOffset, "offset"));
/* 221 */     this.zone = ((ZoneId)Objects.requireNonNull(paramZoneId, "zone"));
/*     */   }
/*     */   
/*     */ 
/*     */   public ZoneOffset getOffset()
/*     */   {
/* 227 */     return this.offset;
/*     */   }
/*     */   
/*     */   public ChronoZonedDateTime<D> withEarlierOffsetAtOverlap()
/*     */   {
/* 232 */     ZoneOffsetTransition localZoneOffsetTransition = getZone().getRules().getTransition(LocalDateTime.from(this));
/* 233 */     if ((localZoneOffsetTransition != null) && (localZoneOffsetTransition.isOverlap())) {
/* 234 */       ZoneOffset localZoneOffset = localZoneOffsetTransition.getOffsetBefore();
/* 235 */       if (!localZoneOffset.equals(this.offset)) {
/* 236 */         return new ChronoZonedDateTimeImpl(this.dateTime, localZoneOffset, this.zone);
/*     */       }
/*     */     }
/* 239 */     return this;
/*     */   }
/*     */   
/*     */   public ChronoZonedDateTime<D> withLaterOffsetAtOverlap()
/*     */   {
/* 244 */     ZoneOffsetTransition localZoneOffsetTransition = getZone().getRules().getTransition(LocalDateTime.from(this));
/* 245 */     if (localZoneOffsetTransition != null) {
/* 246 */       ZoneOffset localZoneOffset = localZoneOffsetTransition.getOffsetAfter();
/* 247 */       if (!localZoneOffset.equals(getOffset())) {
/* 248 */         return new ChronoZonedDateTimeImpl(this.dateTime, localZoneOffset, this.zone);
/*     */       }
/*     */     }
/* 251 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChronoLocalDateTime<D> toLocalDateTime()
/*     */   {
/* 257 */     return this.dateTime;
/*     */   }
/*     */   
/*     */   public ZoneId getZone()
/*     */   {
/* 262 */     return this.zone;
/*     */   }
/*     */   
/*     */   public ChronoZonedDateTime<D> withZoneSameLocal(ZoneId paramZoneId)
/*     */   {
/* 267 */     return ofBest(this.dateTime, paramZoneId, this.offset);
/*     */   }
/*     */   
/*     */   public ChronoZonedDateTime<D> withZoneSameInstant(ZoneId paramZoneId)
/*     */   {
/* 272 */     Objects.requireNonNull(paramZoneId, "zone");
/* 273 */     return this.zone.equals(paramZoneId) ? this : create(this.dateTime.toInstant(this.offset), paramZoneId);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isSupported(TemporalField paramTemporalField)
/*     */   {
/* 279 */     return ((paramTemporalField instanceof ChronoField)) || ((paramTemporalField != null) && (paramTemporalField.isSupportedBy(this)));
/*     */   }
/*     */   
/*     */ 
/*     */   public ChronoZonedDateTime<D> with(TemporalField paramTemporalField, long paramLong)
/*     */   {
/* 285 */     if ((paramTemporalField instanceof ChronoField)) {
/* 286 */       ChronoField localChronoField = (ChronoField)paramTemporalField;
/* 287 */       switch (localChronoField) {
/* 288 */       case INSTANT_SECONDS:  return plus(paramLong - toEpochSecond(), ChronoUnit.SECONDS);
/*     */       case OFFSET_SECONDS: 
/* 290 */         ZoneOffset localZoneOffset = ZoneOffset.ofTotalSeconds(localChronoField.checkValidIntValue(paramLong));
/* 291 */         return create(this.dateTime.toInstant(localZoneOffset), this.zone);
/*     */       }
/*     */       
/* 294 */       return ofBest(this.dateTime.with(paramTemporalField, paramLong), this.zone, this.offset);
/*     */     }
/* 296 */     return ensureValid(getChronology(), paramTemporalField.adjustInto(this, paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */   public ChronoZonedDateTime<D> plus(long paramLong, TemporalUnit paramTemporalUnit)
/*     */   {
/* 302 */     if ((paramTemporalUnit instanceof ChronoUnit)) {
/* 303 */       return with(this.dateTime.plus(paramLong, paramTemporalUnit));
/*     */     }
/* 305 */     return ensureValid(getChronology(), paramTemporalUnit.addTo(this, paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit)
/*     */   {
/* 311 */     Objects.requireNonNull(paramTemporal, "endExclusive");
/*     */     
/* 313 */     ChronoZonedDateTime localChronoZonedDateTime = getChronology().zonedDateTime(paramTemporal);
/* 314 */     if ((paramTemporalUnit instanceof ChronoUnit)) {
/* 315 */       localChronoZonedDateTime = localChronoZonedDateTime.withZoneSameInstant(this.offset);
/* 316 */       return this.dateTime.until(localChronoZonedDateTime.toLocalDateTime(), paramTemporalUnit);
/*     */     }
/* 318 */     Objects.requireNonNull(paramTemporalUnit, "unit");
/* 319 */     return paramTemporalUnit.between(this, localChronoZonedDateTime);
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
/*     */   private Object writeReplace()
/*     */   {
/* 337 */     return new Ser((byte)3, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 347 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 351 */     paramObjectOutput.writeObject(this.dateTime);
/* 352 */     paramObjectOutput.writeObject(this.offset);
/* 353 */     paramObjectOutput.writeObject(this.zone);
/*     */   }
/*     */   
/*     */   static ChronoZonedDateTime<?> readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 357 */     ChronoLocalDateTime localChronoLocalDateTime = (ChronoLocalDateTime)paramObjectInput.readObject();
/* 358 */     ZoneOffset localZoneOffset = (ZoneOffset)paramObjectInput.readObject();
/* 359 */     ZoneId localZoneId = (ZoneId)paramObjectInput.readObject();
/* 360 */     return localChronoLocalDateTime.atZone(localZoneOffset).withZoneSameLocal(localZoneId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 367 */     if (this == paramObject) {
/* 368 */       return true;
/*     */     }
/* 370 */     if ((paramObject instanceof ChronoZonedDateTime)) {
/* 371 */       return compareTo((ChronoZonedDateTime)paramObject) == 0;
/*     */     }
/* 373 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 378 */     return toLocalDateTime().hashCode() ^ getOffset().hashCode() ^ Integer.rotateLeft(getZone().hashCode(), 3);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 383 */     String str = toLocalDateTime().toString() + getOffset().toString();
/* 384 */     if (getOffset() != getZone()) {
/* 385 */       str = str + '[' + getZone().toString() + ']';
/*     */     }
/* 387 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ChronoZonedDateTimeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */