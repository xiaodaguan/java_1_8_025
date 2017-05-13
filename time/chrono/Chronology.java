/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.Clock;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.time.format.TextStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface Chronology
/*     */   extends Comparable<Chronology>
/*     */ {
/*     */   public static Chronology from(TemporalAccessor paramTemporalAccessor)
/*     */   {
/* 177 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 178 */     Chronology localChronology = (Chronology)paramTemporalAccessor.query(TemporalQueries.chronology());
/* 179 */     return localChronology != null ? localChronology : IsoChronology.INSTANCE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Chronology ofLocale(Locale paramLocale)
/*     */   {
/* 224 */     return AbstractChronology.ofLocale(paramLocale);
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
/*     */   public static Chronology of(String paramString)
/*     */   {
/* 249 */     return AbstractChronology.of(paramString);
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
/*     */   public static Set<Chronology> getAvailableChronologies()
/*     */   {
/* 263 */     return AbstractChronology.getAvailableChronologies();
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
/*     */   public abstract String getId();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getCalendarType();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 311 */     return date(prolepticYear(paramEra, paramInt1), paramInt2, paramInt3);
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
/*     */   public abstract ChronoLocalDate date(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate dateYearDay(Era paramEra, int paramInt1, int paramInt2)
/*     */   {
/* 342 */     return dateYearDay(prolepticYear(paramEra, paramInt1), paramInt2);
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
/*     */   public abstract ChronoLocalDate dateYearDay(int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoLocalDate dateEpochDay(long paramLong);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDate dateNow()
/*     */   {
/* 385 */     return dateNow(Clock.systemDefaultZone());
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
/*     */   public ChronoLocalDate dateNow(ZoneId paramZoneId)
/*     */   {
/* 405 */     return dateNow(Clock.system(paramZoneId));
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
/*     */   public ChronoLocalDate dateNow(Clock paramClock)
/*     */   {
/* 423 */     Objects.requireNonNull(paramClock, "clock");
/* 424 */     return date(LocalDate.now(paramClock));
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
/*     */   public abstract ChronoLocalDate date(TemporalAccessor paramTemporalAccessor);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoLocalDateTime<? extends ChronoLocalDate> localDateTime(TemporalAccessor paramTemporalAccessor)
/*     */   {
/*     */     try
/*     */     {
/* 471 */       return date(paramTemporalAccessor).atTime(LocalTime.from(paramTemporalAccessor));
/*     */     } catch (DateTimeException localDateTimeException) {
/* 473 */       throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass(), localDateTimeException);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(TemporalAccessor paramTemporalAccessor)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokestatic 30	java/time/ZoneId:from	(Ljava/time/temporal/TemporalAccessor;)Ljava/time/ZoneId;
/*     */     //   4: astore_2
/*     */     //   5: aload_1
/*     */     //   6: invokestatic 31	java/time/Instant:from	(Ljava/time/temporal/TemporalAccessor;)Ljava/time/Instant;
/*     */     //   9: astore_3
/*     */     //   10: aload_0
/*     */     //   11: aload_3
/*     */     //   12: aload_2
/*     */     //   13: invokeinterface 32 3 0
/*     */     //   18: areturn
/*     */     //   19: astore_3
/*     */     //   20: aload_0
/*     */     //   21: aload_0
/*     */     //   22: aload_1
/*     */     //   23: invokeinterface 33 2 0
/*     */     //   28: invokestatic 34	java/time/chrono/ChronoLocalDateTimeImpl:ensureValid	(Ljava/time/chrono/Chronology;Ljava/time/temporal/Temporal;)Ljava/time/chrono/ChronoLocalDateTimeImpl;
/*     */     //   31: astore 4
/*     */     //   33: aload 4
/*     */     //   35: aload_2
/*     */     //   36: aconst_null
/*     */     //   37: invokestatic 35	java/time/chrono/ChronoZonedDateTimeImpl:ofBest	(Ljava/time/chrono/ChronoLocalDateTimeImpl;Ljava/time/ZoneId;Ljava/time/ZoneOffset;)Ljava/time/chrono/ChronoZonedDateTime;
/*     */     //   40: areturn
/*     */     //   41: astore_2
/*     */     //   42: new 21	java/time/DateTimeException
/*     */     //   45: dup
/*     */     //   46: new 22	java/lang/StringBuilder
/*     */     //   49: dup
/*     */     //   50: invokespecial 23	java/lang/StringBuilder:<init>	()V
/*     */     //   53: ldc 36
/*     */     //   55: invokevirtual 25	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   58: aload_1
/*     */     //   59: invokevirtual 26	java/lang/Object:getClass	()Ljava/lang/Class;
/*     */     //   62: invokevirtual 27	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   65: invokevirtual 28	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   68: aload_2
/*     */     //   69: invokespecial 29	java/time/DateTimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   72: athrow
/*     */     // Line number table:
/*     */     //   Java source line #503	-> byte code offset #0
/*     */     //   Java source line #505	-> byte code offset #5
/*     */     //   Java source line #506	-> byte code offset #10
/*     */     //   Java source line #508	-> byte code offset #19
/*     */     //   Java source line #509	-> byte code offset #20
/*     */     //   Java source line #510	-> byte code offset #33
/*     */     //   Java source line #512	-> byte code offset #41
/*     */     //   Java source line #513	-> byte code offset #42
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	73	0	this	Chronology
/*     */     //   0	73	1	paramTemporalAccessor	TemporalAccessor
/*     */     //   4	32	2	localZoneId	ZoneId
/*     */     //   41	28	2	localDateTimeException1	DateTimeException
/*     */     //   9	3	3	localInstant	Instant
/*     */     //   19	1	3	localDateTimeException2	DateTimeException
/*     */     //   31	3	4	localChronoLocalDateTimeImpl	ChronoLocalDateTimeImpl
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	18	19	java/time/DateTimeException
/*     */     //   0	18	41	java/time/DateTimeException
/*     */     //   19	40	41	java/time/DateTimeException
/*     */   }
/*     */   
/*     */   public ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(Instant paramInstant, ZoneId paramZoneId)
/*     */   {
/* 528 */     return ChronoZonedDateTimeImpl.ofInstant(this, paramInstant, paramZoneId);
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
/*     */   public abstract boolean isLeapYear(long paramLong);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int prolepticYear(Era paramEra, int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Era eraOf(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<Era> eras();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ValueRange range(ChronoField paramChronoField);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDisplayName(TextStyle paramTextStyle, Locale paramLocale)
/*     */   {
/* 633 */     TemporalAccessor local1 = new TemporalAccessor()
/*     */     {
/*     */       public boolean isSupported(TemporalField paramAnonymousTemporalField) {
/* 636 */         return false;
/*     */       }
/*     */       
/*     */       public long getLong(TemporalField paramAnonymousTemporalField) {
/* 640 */         throw new UnsupportedTemporalTypeException("Unsupported field: " + paramAnonymousTemporalField);
/*     */       }
/*     */       
/*     */       public <R> R query(TemporalQuery<R> paramAnonymousTemporalQuery)
/*     */       {
/* 645 */         if (paramAnonymousTemporalQuery == TemporalQueries.chronology()) {
/* 646 */           return Chronology.this;
/*     */         }
/* 648 */         return (R)super.query(paramAnonymousTemporalQuery);
/*     */       }
/* 650 */     };
/* 651 */     return new DateTimeFormatterBuilder().appendChronologyText(paramTextStyle).toFormatter(paramLocale).format(local1);
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
/*     */   public abstract ChronoLocalDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoPeriod period(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 704 */     return new ChronoPeriodImpl(this, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public abstract int compareTo(Chronology paramChronology);
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/Chronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */