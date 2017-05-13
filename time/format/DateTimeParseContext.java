/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.ZoneId;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DateTimeParseContext
/*     */ {
/*     */   private DateTimeFormatter formatter;
/* 101 */   private boolean caseSensitive = true;
/*     */   
/*     */ 
/*     */ 
/* 105 */   private boolean strict = true;
/*     */   
/*     */ 
/*     */ 
/* 109 */   private final ArrayList<Parsed> parsed = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/* 113 */   private ArrayList<Consumer<Chronology>> chronoListeners = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   DateTimeParseContext(DateTimeFormatter paramDateTimeFormatter)
/*     */   {
/* 122 */     this.formatter = paramDateTimeFormatter;
/* 123 */     this.parsed.add(new Parsed());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   DateTimeParseContext copy()
/*     */   {
/* 131 */     DateTimeParseContext localDateTimeParseContext = new DateTimeParseContext(this.formatter);
/* 132 */     localDateTimeParseContext.caseSensitive = this.caseSensitive;
/* 133 */     localDateTimeParseContext.strict = this.strict;
/* 134 */     return localDateTimeParseContext;
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
/*     */   Locale getLocale()
/*     */   {
/* 147 */     return this.formatter.getLocale();
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
/* 158 */     return this.formatter.getDecimalStyle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Chronology getEffectiveChronology()
/*     */   {
/* 167 */     Object localObject = currentParsed().chrono;
/* 168 */     if (localObject == null) {
/* 169 */       localObject = this.formatter.getChronology();
/* 170 */       if (localObject == null) {
/* 171 */         localObject = IsoChronology.INSTANCE;
/*     */       }
/*     */     }
/* 174 */     return (Chronology)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isCaseSensitive()
/*     */   {
/* 184 */     return this.caseSensitive;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setCaseSensitive(boolean paramBoolean)
/*     */   {
/* 193 */     this.caseSensitive = paramBoolean;
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
/*     */   boolean subSequenceEquals(CharSequence paramCharSequence1, int paramInt1, CharSequence paramCharSequence2, int paramInt2, int paramInt3)
/*     */   {
/* 209 */     if ((paramInt1 + paramInt3 > paramCharSequence1.length()) || (paramInt2 + paramInt3 > paramCharSequence2.length()))
/* 210 */       return false;
/*     */     int i;
/* 212 */     char c1; char c2; if (isCaseSensitive()) {
/* 213 */       for (i = 0; i < paramInt3; i++) {
/* 214 */         c1 = paramCharSequence1.charAt(paramInt1 + i);
/* 215 */         c2 = paramCharSequence2.charAt(paramInt2 + i);
/* 216 */         if (c1 != c2) {
/* 217 */           return false;
/*     */         }
/*     */       }
/*     */     } else {
/* 221 */       for (i = 0; i < paramInt3; i++) {
/* 222 */         c1 = paramCharSequence1.charAt(paramInt1 + i);
/* 223 */         c2 = paramCharSequence2.charAt(paramInt2 + i);
/* 224 */         if ((c1 != c2) && (Character.toUpperCase(c1) != Character.toUpperCase(c2)) && 
/* 225 */           (Character.toLowerCase(c1) != Character.toLowerCase(c2))) {
/* 226 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 230 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean charEquals(char paramChar1, char paramChar2)
/*     */   {
/* 242 */     if (isCaseSensitive()) {
/* 243 */       return paramChar1 == paramChar2;
/*     */     }
/* 245 */     return charEqualsIgnoreCase(paramChar1, paramChar2);
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
/*     */   static boolean charEqualsIgnoreCase(char paramChar1, char paramChar2)
/*     */   {
/* 258 */     return (paramChar1 == paramChar2) || (Character.toUpperCase(paramChar1) == Character.toUpperCase(paramChar2)) || (Character.toLowerCase(paramChar1) == Character.toLowerCase(paramChar2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isStrict()
/*     */   {
/* 270 */     return this.strict;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setStrict(boolean paramBoolean)
/*     */   {
/* 279 */     this.strict = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void startOptional()
/*     */   {
/* 287 */     this.parsed.add(currentParsed().copy());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void endOptional(boolean paramBoolean)
/*     */   {
/* 296 */     if (paramBoolean) {
/* 297 */       this.parsed.remove(this.parsed.size() - 2);
/*     */     } else {
/* 299 */       this.parsed.remove(this.parsed.size() - 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Parsed currentParsed()
/*     */   {
/* 310 */     return (Parsed)this.parsed.get(this.parsed.size() - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Parsed toUnresolved()
/*     */   {
/* 319 */     return currentParsed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   TemporalAccessor toResolved(ResolverStyle paramResolverStyle, Set<TemporalField> paramSet)
/*     */   {
/* 328 */     Parsed localParsed = currentParsed();
/* 329 */     localParsed.chrono = getEffectiveChronology();
/* 330 */     localParsed.zone = (localParsed.zone != null ? localParsed.zone : this.formatter.getZone());
/* 331 */     return localParsed.resolve(paramResolverStyle, paramSet);
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
/*     */   Long getParsed(TemporalField paramTemporalField)
/*     */   {
/* 348 */     return (Long)currentParsed().fieldValues.get(paramTemporalField);
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
/*     */   int setParsedField(TemporalField paramTemporalField, long paramLong, int paramInt1, int paramInt2)
/*     */   {
/* 364 */     Objects.requireNonNull(paramTemporalField, "field");
/* 365 */     Long localLong = (Long)currentParsed().fieldValues.put(paramTemporalField, Long.valueOf(paramLong));
/* 366 */     return (localLong != null) && (localLong.longValue() != paramLong) ? paramInt1 ^ 0xFFFFFFFF : paramInt2;
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
/*     */   void setParsed(Chronology paramChronology)
/*     */   {
/* 382 */     Objects.requireNonNull(paramChronology, "chrono");
/* 383 */     currentParsed().chrono = paramChronology;
/* 384 */     if ((this.chronoListeners != null) && (!this.chronoListeners.isEmpty()))
/*     */     {
/* 386 */       Consumer[] arrayOfConsumer1 = new Consumer[1];
/* 387 */       Consumer[] arrayOfConsumer2 = (Consumer[])this.chronoListeners.toArray(arrayOfConsumer1);
/* 388 */       this.chronoListeners.clear();
/* 389 */       for (Consumer localConsumer : arrayOfConsumer2) {
/* 390 */         localConsumer.accept(paramChronology);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void addChronoChangedListener(Consumer<Chronology> paramConsumer)
/*     */   {
/* 401 */     if (this.chronoListeners == null) {
/* 402 */       this.chronoListeners = new ArrayList();
/*     */     }
/* 404 */     this.chronoListeners.add(paramConsumer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setParsed(ZoneId paramZoneId)
/*     */   {
/* 416 */     Objects.requireNonNull(paramZoneId, "zone");
/* 417 */     currentParsed().zone = paramZoneId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setParsedLeapSecond()
/*     */   {
/* 424 */     currentParsed().leapSecond = true;
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
/* 435 */     return currentParsed().toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/format/DateTimeParseContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */