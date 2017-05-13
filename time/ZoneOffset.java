/*     */ package java.time;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.time.zone.ZoneRules;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZoneOffset
/*     */   extends ZoneId
/*     */   implements TemporalAccessor, TemporalAdjuster, Comparable<ZoneOffset>, Serializable
/*     */ {
/* 135 */   private static final ConcurrentMap<Integer, ZoneOffset> SECONDS_CACHE = new ConcurrentHashMap(16, 0.75F, 4);
/*     */   
/* 137 */   private static final ConcurrentMap<String, ZoneOffset> ID_CACHE = new ConcurrentHashMap(16, 0.75F, 4);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int MAX_SECONDS = 64800;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 2357656521762053153L;
/*     */   
/*     */ 
/*     */ 
/* 151 */   public static final ZoneOffset UTC = ofTotalSeconds(0);
/*     */   
/*     */ 
/*     */ 
/* 155 */   public static final ZoneOffset MIN = ofTotalSeconds(-64800);
/*     */   
/*     */ 
/*     */ 
/* 159 */   public static final ZoneOffset MAX = ofTotalSeconds(64800);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int totalSeconds;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final transient String id;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ZoneOffset of(String paramString)
/*     */   {
/* 203 */     Objects.requireNonNull(paramString, "offsetId");
/*     */     
/* 205 */     ZoneOffset localZoneOffset = (ZoneOffset)ID_CACHE.get(paramString);
/* 206 */     if (localZoneOffset != null) {
/* 207 */       return localZoneOffset;
/*     */     }
/*     */     int i;
/*     */     int j;
/*     */     int k;
/* 212 */     switch (paramString.length()) {
/*     */     case 2: 
/* 214 */       paramString = paramString.charAt(0) + "0" + paramString.charAt(1);
/*     */     case 3: 
/* 216 */       i = parseNumber(paramString, 1, false);
/* 217 */       j = 0;
/* 218 */       k = 0;
/* 219 */       break;
/*     */     case 5: 
/* 221 */       i = parseNumber(paramString, 1, false);
/* 222 */       j = parseNumber(paramString, 3, false);
/* 223 */       k = 0;
/* 224 */       break;
/*     */     case 6: 
/* 226 */       i = parseNumber(paramString, 1, false);
/* 227 */       j = parseNumber(paramString, 4, true);
/* 228 */       k = 0;
/* 229 */       break;
/*     */     case 7: 
/* 231 */       i = parseNumber(paramString, 1, false);
/* 232 */       j = parseNumber(paramString, 3, false);
/* 233 */       k = parseNumber(paramString, 5, false);
/* 234 */       break;
/*     */     case 9: 
/* 236 */       i = parseNumber(paramString, 1, false);
/* 237 */       j = parseNumber(paramString, 4, true);
/* 238 */       k = parseNumber(paramString, 7, true);
/* 239 */       break;
/*     */     case 4: case 8: default: 
/* 241 */       throw new DateTimeException("Invalid ID for ZoneOffset, invalid format: " + paramString);
/*     */     }
/* 243 */     int m = paramString.charAt(0);
/* 244 */     if ((m != 43) && (m != 45)) {
/* 245 */       throw new DateTimeException("Invalid ID for ZoneOffset, plus/minus not found when expected: " + paramString);
/*     */     }
/* 247 */     if (m == 45) {
/* 248 */       return ofHoursMinutesSeconds(-i, -j, -k);
/*     */     }
/* 250 */     return ofHoursMinutesSeconds(i, j, k);
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
/*     */   private static int parseNumber(CharSequence paramCharSequence, int paramInt, boolean paramBoolean)
/*     */   {
/* 263 */     if ((paramBoolean) && (paramCharSequence.charAt(paramInt - 1) != ':')) {
/* 264 */       throw new DateTimeException("Invalid ID for ZoneOffset, colon not found when expected: " + paramCharSequence);
/*     */     }
/* 266 */     int i = paramCharSequence.charAt(paramInt);
/* 267 */     int j = paramCharSequence.charAt(paramInt + 1);
/* 268 */     if ((i < 48) || (i > 57) || (j < 48) || (j > 57)) {
/* 269 */       throw new DateTimeException("Invalid ID for ZoneOffset, non numeric characters found: " + paramCharSequence);
/*     */     }
/* 271 */     return (i - 48) * 10 + (j - 48);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ZoneOffset ofHours(int paramInt)
/*     */   {
/* 283 */     return ofHoursMinutesSeconds(paramInt, 0, 0);
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
/*     */   public static ZoneOffset ofHoursMinutes(int paramInt1, int paramInt2)
/*     */   {
/* 300 */     return ofHoursMinutesSeconds(paramInt1, paramInt2, 0);
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
/*     */   public static ZoneOffset ofHoursMinutesSeconds(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 317 */     validate(paramInt1, paramInt2, paramInt3);
/* 318 */     int i = totalSeconds(paramInt1, paramInt2, paramInt3);
/* 319 */     return ofTotalSeconds(i);
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
/*     */   public static ZoneOffset from(TemporalAccessor paramTemporalAccessor)
/*     */   {
/* 344 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 345 */     ZoneOffset localZoneOffset = (ZoneOffset)paramTemporalAccessor.query(TemporalQueries.offset());
/* 346 */     if (localZoneOffset == null)
/*     */     {
/* 348 */       throw new DateTimeException("Unable to obtain ZoneOffset from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor.getClass().getName());
/*     */     }
/* 350 */     return localZoneOffset;
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
/*     */   private static void validate(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 363 */     if ((paramInt1 < -18) || (paramInt1 > 18)) {
/* 364 */       throw new DateTimeException("Zone offset hours not in valid range: value " + paramInt1 + " is not in the range -18 to 18");
/*     */     }
/*     */     
/* 367 */     if (paramInt1 > 0) {
/* 368 */       if ((paramInt2 < 0) || (paramInt3 < 0)) {
/* 369 */         throw new DateTimeException("Zone offset minutes and seconds must be positive because hours is positive");
/*     */       }
/* 371 */     } else if (paramInt1 < 0) {
/* 372 */       if ((paramInt2 > 0) || (paramInt3 > 0)) {
/* 373 */         throw new DateTimeException("Zone offset minutes and seconds must be negative because hours is negative");
/*     */       }
/* 375 */     } else if (((paramInt2 > 0) && (paramInt3 < 0)) || ((paramInt2 < 0) && (paramInt3 > 0))) {
/* 376 */       throw new DateTimeException("Zone offset minutes and seconds must have the same sign");
/*     */     }
/* 378 */     if (Math.abs(paramInt2) > 59)
/*     */     {
/* 380 */       throw new DateTimeException("Zone offset minutes not in valid range: abs(value) " + Math.abs(paramInt2) + " is not in the range 0 to 59");
/*     */     }
/* 382 */     if (Math.abs(paramInt3) > 59)
/*     */     {
/* 384 */       throw new DateTimeException("Zone offset seconds not in valid range: abs(value) " + Math.abs(paramInt3) + " is not in the range 0 to 59");
/*     */     }
/* 386 */     if ((Math.abs(paramInt1) == 18) && ((Math.abs(paramInt2) > 0) || (Math.abs(paramInt3) > 0))) {
/* 387 */       throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int totalSeconds(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 400 */     return paramInt1 * 3600 + paramInt2 * 60 + paramInt3;
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
/*     */   public static ZoneOffset ofTotalSeconds(int paramInt)
/*     */   {
/* 414 */     if (Math.abs(paramInt) > 64800) {
/* 415 */       throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
/*     */     }
/* 417 */     if (paramInt % 900 == 0) {
/* 418 */       Integer localInteger = Integer.valueOf(paramInt);
/* 419 */       ZoneOffset localZoneOffset = (ZoneOffset)SECONDS_CACHE.get(localInteger);
/* 420 */       if (localZoneOffset == null) {
/* 421 */         localZoneOffset = new ZoneOffset(paramInt);
/* 422 */         SECONDS_CACHE.putIfAbsent(localInteger, localZoneOffset);
/* 423 */         localZoneOffset = (ZoneOffset)SECONDS_CACHE.get(localInteger);
/* 424 */         ID_CACHE.putIfAbsent(localZoneOffset.getId(), localZoneOffset);
/*     */       }
/* 426 */       return localZoneOffset;
/*     */     }
/* 428 */     return new ZoneOffset(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ZoneOffset(int paramInt)
/*     */   {
/* 440 */     this.totalSeconds = paramInt;
/* 441 */     this.id = buildId(paramInt);
/*     */   }
/*     */   
/*     */   private static String buildId(int paramInt) {
/* 445 */     if (paramInt == 0) {
/* 446 */       return "Z";
/*     */     }
/* 448 */     int i = Math.abs(paramInt);
/* 449 */     StringBuilder localStringBuilder = new StringBuilder();
/* 450 */     int j = i / 3600;
/* 451 */     int k = i / 60 % 60;
/* 452 */     localStringBuilder.append(paramInt < 0 ? "-" : "+")
/* 453 */       .append(j < 10 ? "0" : "").append(j)
/* 454 */       .append(k < 10 ? ":0" : ":").append(k);
/* 455 */     int m = i % 60;
/* 456 */     if (m != 0) {
/* 457 */       localStringBuilder.append(m < 10 ? ":0" : ":").append(m);
/*     */     }
/* 459 */     return localStringBuilder.toString();
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
/*     */   public int getTotalSeconds()
/*     */   {
/* 474 */     return this.totalSeconds;
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
/*     */   public String getId()
/*     */   {
/* 492 */     return this.id;
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
/*     */   public ZoneRules getRules()
/*     */   {
/* 505 */     return ZoneRules.of(this);
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
/*     */   public boolean isSupported(TemporalField paramTemporalField)
/*     */   {
/* 530 */     if ((paramTemporalField instanceof ChronoField)) {
/* 531 */       return paramTemporalField == ChronoField.OFFSET_SECONDS;
/*     */     }
/* 533 */     return (paramTemporalField != null) && (paramTemporalField.isSupportedBy(this));
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
/*     */   public ValueRange range(TemporalField paramTemporalField)
/*     */   {
/* 561 */     return super.range(paramTemporalField);
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
/*     */   public int get(TemporalField paramTemporalField)
/*     */   {
/* 591 */     if (paramTemporalField == ChronoField.OFFSET_SECONDS)
/* 592 */       return this.totalSeconds;
/* 593 */     if ((paramTemporalField instanceof ChronoField)) {
/* 594 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 596 */     return range(paramTemporalField).checkValidIntValue(getLong(paramTemporalField), paramTemporalField);
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
/*     */   public long getLong(TemporalField paramTemporalField)
/*     */   {
/* 623 */     if (paramTemporalField == ChronoField.OFFSET_SECONDS)
/* 624 */       return this.totalSeconds;
/* 625 */     if ((paramTemporalField instanceof ChronoField)) {
/* 626 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 628 */     return paramTemporalField.getFrom(this);
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
/* 653 */     if ((paramTemporalQuery == TemporalQueries.offset()) || (paramTemporalQuery == TemporalQueries.zone())) {
/* 654 */       return this;
/*     */     }
/* 656 */     return (R)super.query(paramTemporalQuery);
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
/* 685 */     return paramTemporal.with(ChronoField.OFFSET_SECONDS, this.totalSeconds);
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
/*     */   public int compareTo(ZoneOffset paramZoneOffset)
/*     */   {
/* 704 */     return paramZoneOffset.totalSeconds - this.totalSeconds;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 719 */     if (this == paramObject) {
/* 720 */       return true;
/*     */     }
/* 722 */     if ((paramObject instanceof ZoneOffset)) {
/* 723 */       return this.totalSeconds == ((ZoneOffset)paramObject).totalSeconds;
/*     */     }
/* 725 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 735 */     return this.totalSeconds;
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
/* 746 */     return this.id;
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
/*     */   private Object writeReplace()
/*     */   {
/* 766 */     return new Ser((byte)8, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 776 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void write(DataOutput paramDataOutput) throws IOException
/*     */   {
/* 781 */     paramDataOutput.writeByte(8);
/* 782 */     writeExternal(paramDataOutput);
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 786 */     int i = this.totalSeconds;
/* 787 */     int j = i % 900 == 0 ? i / 900 : 127;
/* 788 */     paramDataOutput.writeByte(j);
/* 789 */     if (j == 127) {
/* 790 */       paramDataOutput.writeInt(i);
/*     */     }
/*     */   }
/*     */   
/*     */   static ZoneOffset readExternal(DataInput paramDataInput) throws IOException {
/* 795 */     int i = paramDataInput.readByte();
/* 796 */     return i == 127 ? ofTotalSeconds(paramDataInput.readInt()) : ofTotalSeconds(i * 900);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/ZoneOffset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */