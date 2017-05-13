/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.LocalDate;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import sun.util.calendar.CalendarDate;
/*     */ import sun.util.calendar.LocalGregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JapaneseEra
/*     */   implements Era, Serializable
/*     */ {
/*     */   static final int ERA_OFFSET = 2;
/*     */   static final sun.util.calendar.Era[] ERA_CONFIG;
/* 111 */   public static final JapaneseEra MEIJI = new JapaneseEra(-1, LocalDate.of(1868, 1, 1));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 116 */   public static final JapaneseEra TAISHO = new JapaneseEra(0, LocalDate.of(1912, 7, 30));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 121 */   public static final JapaneseEra SHOWA = new JapaneseEra(1, LocalDate.of(1926, 12, 25));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 126 */   public static final JapaneseEra HEISEI = new JapaneseEra(2, LocalDate.of(1989, 1, 8));
/*     */   
/*     */ 
/*     */ 
/* 130 */   private static final int N_ERA_CONSTANTS = HEISEI.getValue() + 2 + 1;
/*     */   
/*     */   private static final long serialVersionUID = 1466499369062886794L;
/*     */   
/*     */   private static final JapaneseEra[] KNOWN_ERAS;
/*     */   
/*     */   private final transient int eraValue;
/*     */   private final transient LocalDate since;
/*     */   
/*     */   static
/*     */   {
/* 141 */     ERA_CONFIG = JapaneseChronology.JCAL.getEras();
/*     */     
/* 143 */     KNOWN_ERAS = new JapaneseEra[ERA_CONFIG.length];
/* 144 */     KNOWN_ERAS[0] = MEIJI;
/* 145 */     KNOWN_ERAS[1] = TAISHO;
/* 146 */     KNOWN_ERAS[2] = SHOWA;
/* 147 */     KNOWN_ERAS[3] = HEISEI;
/* 148 */     for (int i = N_ERA_CONSTANTS; i < ERA_CONFIG.length; i++) {
/* 149 */       CalendarDate localCalendarDate = ERA_CONFIG[i].getSinceDate();
/* 150 */       LocalDate localLocalDate = LocalDate.of(localCalendarDate.getYear(), localCalendarDate.getMonth(), localCalendarDate.getDayOfMonth());
/* 151 */       KNOWN_ERAS[i] = new JapaneseEra(i - 2, localLocalDate);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JapaneseEra(int paramInt, LocalDate paramLocalDate)
/*     */   {
/* 171 */     this.eraValue = paramInt;
/* 172 */     this.since = paramLocalDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   sun.util.calendar.Era getPrivateEra()
/*     */   {
/* 182 */     return ERA_CONFIG[ordinal(this.eraValue)];
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
/*     */   public static JapaneseEra of(int paramInt)
/*     */   {
/* 198 */     if ((paramInt < MEIJI.eraValue) || (paramInt + 2 - 1 >= KNOWN_ERAS.length)) {
/* 199 */       throw new DateTimeException("Invalid era: " + paramInt);
/*     */     }
/* 201 */     return KNOWN_ERAS[ordinal(paramInt)];
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
/*     */   public static JapaneseEra valueOf(String paramString)
/*     */   {
/* 215 */     Objects.requireNonNull(paramString, "japaneseEra");
/* 216 */     for (JapaneseEra localJapaneseEra : KNOWN_ERAS) {
/* 217 */       if (localJapaneseEra.getName().equals(paramString)) {
/* 218 */         return localJapaneseEra;
/*     */       }
/*     */     }
/* 221 */     throw new IllegalArgumentException("japaneseEra is invalid");
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
/*     */   public static JapaneseEra[] values()
/*     */   {
/* 236 */     return (JapaneseEra[])Arrays.copyOf(KNOWN_ERAS, KNOWN_ERAS.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static JapaneseEra from(LocalDate paramLocalDate)
/*     */   {
/* 247 */     if (paramLocalDate.isBefore(JapaneseDate.MEIJI_6_ISODATE)) {
/* 248 */       throw new DateTimeException("JapaneseDate before Meiji 6 are not supported");
/*     */     }
/* 250 */     for (int i = KNOWN_ERAS.length - 1; i > 0; i--) {
/* 251 */       JapaneseEra localJapaneseEra = KNOWN_ERAS[i];
/* 252 */       if (paramLocalDate.compareTo(localJapaneseEra.since) >= 0) {
/* 253 */         return localJapaneseEra;
/*     */       }
/*     */     }
/* 256 */     return null;
/*     */   }
/*     */   
/*     */   static JapaneseEra toJapaneseEra(sun.util.calendar.Era paramEra) {
/* 260 */     for (int i = ERA_CONFIG.length - 1; i >= 0; i--) {
/* 261 */       if (ERA_CONFIG[i].equals(paramEra)) {
/* 262 */         return KNOWN_ERAS[i];
/*     */       }
/*     */     }
/* 265 */     return null;
/*     */   }
/*     */   
/*     */   static sun.util.calendar.Era privateEraFrom(LocalDate paramLocalDate) {
/* 269 */     for (int i = KNOWN_ERAS.length - 1; i > 0; i--) {
/* 270 */       JapaneseEra localJapaneseEra = KNOWN_ERAS[i];
/* 271 */       if (paramLocalDate.compareTo(localJapaneseEra.since) >= 0) {
/* 272 */         return ERA_CONFIG[i];
/*     */       }
/*     */     }
/* 275 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int ordinal(int paramInt)
/*     */   {
/* 286 */     return paramInt + 2 - 1;
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
/*     */   public int getValue()
/*     */   {
/* 301 */     return this.eraValue;
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
/*     */   public ValueRange range(TemporalField paramTemporalField)
/*     */   {
/* 332 */     if (paramTemporalField == ChronoField.ERA) {
/* 333 */       return JapaneseChronology.INSTANCE.range(ChronoField.ERA);
/*     */     }
/* 335 */     return super.range(paramTemporalField);
/*     */   }
/*     */   
/*     */   String getAbbreviation()
/*     */   {
/* 340 */     int i = ordinal(getValue());
/* 341 */     if (i == 0) {
/* 342 */       return "";
/*     */     }
/* 344 */     return ERA_CONFIG[i].getAbbreviation();
/*     */   }
/*     */   
/*     */   String getName() {
/* 348 */     return ERA_CONFIG[ordinal(getValue())].getName();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 353 */     return getName();
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
/* 364 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */   private Object writeReplace()
/*     */   {
/* 380 */     return new Ser((byte)5, this);
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 384 */     paramDataOutput.writeByte(getValue());
/*     */   }
/*     */   
/*     */   static JapaneseEra readExternal(DataInput paramDataInput) throws IOException {
/* 388 */     int i = paramDataInput.readByte();
/* 389 */     return of(i);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/JapaneseEra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */