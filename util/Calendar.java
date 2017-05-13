/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.text.DateFormat;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.time.Instant;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.BuddhistCalendar;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.spi.CalendarProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Calendar
/*      */   implements Serializable, Cloneable, Comparable<Calendar>
/*      */ {
/*      */   public static final int ERA = 0;
/*      */   public static final int YEAR = 1;
/*      */   public static final int MONTH = 2;
/*      */   public static final int WEEK_OF_YEAR = 3;
/*      */   public static final int WEEK_OF_MONTH = 4;
/*      */   public static final int DATE = 5;
/*      */   public static final int DAY_OF_MONTH = 5;
/*      */   public static final int DAY_OF_YEAR = 6;
/*      */   public static final int DAY_OF_WEEK = 7;
/*      */   public static final int DAY_OF_WEEK_IN_MONTH = 8;
/*      */   public static final int AM_PM = 9;
/*      */   public static final int HOUR = 10;
/*      */   public static final int HOUR_OF_DAY = 11;
/*      */   public static final int MINUTE = 12;
/*      */   public static final int SECOND = 13;
/*      */   public static final int MILLISECOND = 14;
/*      */   public static final int ZONE_OFFSET = 15;
/*      */   public static final int DST_OFFSET = 16;
/*      */   public static final int FIELD_COUNT = 17;
/*      */   public static final int SUNDAY = 1;
/*      */   public static final int MONDAY = 2;
/*      */   public static final int TUESDAY = 3;
/*      */   public static final int WEDNESDAY = 4;
/*      */   public static final int THURSDAY = 5;
/*      */   public static final int FRIDAY = 6;
/*      */   public static final int SATURDAY = 7;
/*      */   public static final int JANUARY = 0;
/*      */   public static final int FEBRUARY = 1;
/*      */   public static final int MARCH = 2;
/*      */   public static final int APRIL = 3;
/*      */   public static final int MAY = 4;
/*      */   public static final int JUNE = 5;
/*      */   public static final int JULY = 6;
/*      */   public static final int AUGUST = 7;
/*      */   public static final int SEPTEMBER = 8;
/*      */   public static final int OCTOBER = 9;
/*      */   public static final int NOVEMBER = 10;
/*      */   public static final int DECEMBER = 11;
/*      */   public static final int UNDECIMBER = 12;
/*      */   public static final int AM = 0;
/*      */   public static final int PM = 1;
/*      */   public static final int ALL_STYLES = 0;
/*      */   static final int STANDALONE_MASK = 32768;
/*      */   public static final int SHORT = 1;
/*      */   public static final int LONG = 2;
/*      */   public static final int NARROW_FORMAT = 4;
/*      */   public static final int NARROW_STANDALONE = 32772;
/*      */   public static final int SHORT_FORMAT = 1;
/*      */   public static final int LONG_FORMAT = 2;
/*      */   public static final int SHORT_STANDALONE = 32769;
/*      */   public static final int LONG_STANDALONE = 32770;
/*      */   protected int[] fields;
/*      */   protected boolean[] isSet;
/*      */   private transient int[] stamp;
/*      */   protected long time;
/*      */   protected boolean isTimeSet;
/*      */   protected boolean areFieldsSet;
/*      */   transient boolean areAllFieldsSet;
/*  902 */   private boolean lenient = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private TimeZone zone;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  914 */   private transient boolean sharedZone = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int firstDayOfWeek;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int minimalDaysInFirstWeek;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  934 */   private static final ConcurrentMap<Locale, int[]> cachedLocaleData = new ConcurrentHashMap(3);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int UNSET = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int COMPUTED = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MINIMUM_USER_STAMP = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int ALL_FIELDS = 131071;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  967 */   private int nextStamp = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int currentSerialVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  999 */   private int serialVersionOnStream = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   static final long serialVersionUID = -1807547505821590642L;
/*      */   
/*      */ 
/*      */ 
/*      */   static final int ERA_MASK = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   static final int YEAR_MASK = 2;
/*      */   
/*      */ 
/*      */   static final int MONTH_MASK = 4;
/*      */   
/*      */ 
/*      */   static final int WEEK_OF_YEAR_MASK = 8;
/*      */   
/*      */ 
/*      */   static final int WEEK_OF_MONTH_MASK = 16;
/*      */   
/*      */ 
/*      */   static final int DAY_OF_MONTH_MASK = 32;
/*      */   
/*      */ 
/*      */   static final int DATE_MASK = 32;
/*      */   
/*      */ 
/*      */   static final int DAY_OF_YEAR_MASK = 64;
/*      */   
/*      */ 
/*      */   static final int DAY_OF_WEEK_MASK = 128;
/*      */   
/*      */ 
/*      */   static final int DAY_OF_WEEK_IN_MONTH_MASK = 256;
/*      */   
/*      */ 
/*      */   static final int AM_PM_MASK = 512;
/*      */   
/*      */ 
/*      */   static final int HOUR_MASK = 1024;
/*      */   
/*      */ 
/*      */   static final int HOUR_OF_DAY_MASK = 2048;
/*      */   
/*      */ 
/*      */   static final int MINUTE_MASK = 4096;
/*      */   
/*      */ 
/*      */   static final int SECOND_MASK = 8192;
/*      */   
/*      */ 
/*      */   static final int MILLISECOND_MASK = 16384;
/*      */   
/*      */ 
/*      */   static final int ZONE_OFFSET_MASK = 32768;
/*      */   
/*      */ 
/*      */   static final int DST_OFFSET_MASK = 65536;
/*      */   
/*      */ 
/*      */ 
/*      */   public static class Builder
/*      */   {
/*      */     private static final int NFIELDS = 18;
/*      */     
/*      */ 
/*      */     private static final int WEEK_YEAR = 17;
/*      */     
/*      */ 
/*      */     private long instant;
/*      */     
/*      */ 
/*      */     private int[] fields;
/*      */     
/*      */ 
/*      */     private int nextStamp;
/*      */     
/*      */ 
/*      */     private int maxFieldIndex;
/*      */     
/*      */ 
/*      */     private String type;
/*      */     
/*      */ 
/*      */     private TimeZone zone;
/*      */     
/*      */ 
/* 1089 */     private boolean lenient = true;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Locale locale;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private int firstDayOfWeek;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private int minimalDaysInFirstWeek;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setInstant(long paramLong)
/*      */     {
/* 1113 */       if (this.fields != null) {
/* 1114 */         throw new IllegalStateException();
/*      */       }
/* 1116 */       this.instant = paramLong;
/* 1117 */       this.nextStamp = 1;
/* 1118 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setInstant(Date paramDate)
/*      */     {
/* 1137 */       return setInstant(paramDate.getTime());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder set(int paramInt1, int paramInt2)
/*      */     {
/* 1159 */       if ((paramInt1 < 0) || (paramInt1 >= 17)) {
/* 1160 */         throw new IllegalArgumentException("field is invalid");
/*      */       }
/* 1162 */       if (isInstantSet()) {
/* 1163 */         throw new IllegalStateException("instant has been set");
/*      */       }
/* 1165 */       allocateFields();
/* 1166 */       internalSet(paramInt1, paramInt2);
/* 1167 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setFields(int... paramVarArgs)
/*      */     {
/* 1195 */       int i = paramVarArgs.length;
/* 1196 */       if (i % 2 != 0) {
/* 1197 */         throw new IllegalArgumentException();
/*      */       }
/* 1199 */       if (isInstantSet()) {
/* 1200 */         throw new IllegalStateException("instant has been set");
/*      */       }
/* 1202 */       if (this.nextStamp + i / 2 < 0) {
/* 1203 */         throw new IllegalStateException("stamp counter overflow");
/*      */       }
/* 1205 */       allocateFields();
/* 1206 */       for (int j = 0; j < i;) {
/* 1207 */         int k = paramVarArgs[(j++)];
/*      */         
/* 1209 */         if ((k < 0) || (k >= 17)) {
/* 1210 */           throw new IllegalArgumentException("field is invalid");
/*      */         }
/* 1212 */         internalSet(k, paramVarArgs[(j++)]);
/*      */       }
/* 1214 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setDate(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1233 */       return setFields(new int[] { 1, paramInt1, 2, paramInt2, 5, paramInt3 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setTimeOfDay(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1250 */       return setTimeOfDay(paramInt1, paramInt2, paramInt3, 0);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setTimeOfDay(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1271 */       return setFields(new int[] { 11, paramInt1, 12, paramInt2, 13, paramInt3, 14, paramInt4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setWeekDate(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1292 */       allocateFields();
/* 1293 */       internalSet(17, paramInt1);
/* 1294 */       internalSet(3, paramInt2);
/* 1295 */       internalSet(7, paramInt3);
/* 1296 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setTimeZone(TimeZone paramTimeZone)
/*      */     {
/* 1312 */       if (paramTimeZone == null) {
/* 1313 */         throw new NullPointerException();
/*      */       }
/* 1315 */       this.zone = paramTimeZone;
/* 1316 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setLenient(boolean paramBoolean)
/*      */     {
/* 1330 */       this.lenient = paramBoolean;
/* 1331 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setCalendarType(String paramString)
/*      */     {
/* 1354 */       if (paramString.equals("gregorian")) {
/* 1355 */         paramString = "gregory";
/*      */       }
/* 1357 */       if ((!Calendar.getAvailableCalendarTypes().contains(paramString)) && 
/* 1358 */         (!paramString.equals("iso8601"))) {
/* 1359 */         throw new IllegalArgumentException("unknown calendar type: " + paramString);
/*      */       }
/* 1361 */       if (this.type == null) {
/* 1362 */         this.type = paramString;
/*      */       }
/* 1364 */       else if (!this.type.equals(paramString)) {
/* 1365 */         throw new IllegalStateException("calendar type override");
/*      */       }
/*      */       
/* 1368 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setLocale(Locale paramLocale)
/*      */     {
/* 1392 */       if (paramLocale == null) {
/* 1393 */         throw new NullPointerException();
/*      */       }
/* 1395 */       this.locale = paramLocale;
/* 1396 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setWeekDefinition(int paramInt1, int paramInt2)
/*      */     {
/* 1418 */       if ((!isValidWeekParameter(paramInt1)) || 
/* 1419 */         (!isValidWeekParameter(paramInt2))) {
/* 1420 */         throw new IllegalArgumentException();
/*      */       }
/* 1422 */       this.firstDayOfWeek = paramInt1;
/* 1423 */       this.minimalDaysInFirstWeek = paramInt2;
/* 1424 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Calendar build()
/*      */     {
/* 1462 */       if (this.locale == null) {
/* 1463 */         this.locale = Locale.getDefault();
/*      */       }
/* 1465 */       if (this.zone == null) {
/* 1466 */         this.zone = TimeZone.getDefault();
/*      */       }
/*      */       
/* 1469 */       if (this.type == null) {
/* 1470 */         this.type = this.locale.getUnicodeLocaleType("ca");
/*      */       }
/* 1472 */       if (this.type == null) {
/* 1473 */         if ((this.locale.getCountry() == "TH") && 
/* 1474 */           (this.locale.getLanguage() == "th")) {
/* 1475 */           this.type = "buddhist";
/*      */         } else
/* 1477 */           this.type = "gregory";
/*      */       }
/*      */       Object localObject;
/* 1480 */       switch (this.type) {
/*      */       case "gregory": 
/* 1482 */         localObject = new GregorianCalendar(this.zone, this.locale, true);
/* 1483 */         break;
/*      */       case "iso8601": 
/* 1485 */         GregorianCalendar localGregorianCalendar = new GregorianCalendar(this.zone, this.locale, true);
/*      */         
/* 1487 */         localGregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
/*      */         
/* 1489 */         setWeekDefinition(2, 4);
/* 1490 */         localObject = localGregorianCalendar;
/* 1491 */         break;
/*      */       case "buddhist": 
/* 1493 */         localObject = new BuddhistCalendar(this.zone, this.locale);
/* 1494 */         ((Calendar)localObject).clear();
/* 1495 */         break;
/*      */       case "japanese": 
/* 1497 */         localObject = new JapaneseImperialCalendar(this.zone, this.locale, true);
/* 1498 */         break;
/*      */       default: 
/* 1500 */         throw new IllegalArgumentException("unknown calendar type: " + this.type);
/*      */       }
/* 1502 */       ((Calendar)localObject).setLenient(this.lenient);
/* 1503 */       if (this.firstDayOfWeek != 0) {
/* 1504 */         ((Calendar)localObject).setFirstDayOfWeek(this.firstDayOfWeek);
/* 1505 */         ((Calendar)localObject).setMinimalDaysInFirstWeek(this.minimalDaysInFirstWeek);
/*      */       }
/* 1507 */       if (isInstantSet()) {
/* 1508 */         ((Calendar)localObject).setTimeInMillis(this.instant);
/* 1509 */         ((Calendar)localObject).complete();
/* 1510 */         return (Calendar)localObject;
/*      */       }
/*      */       
/* 1513 */       if (this.fields != null) {
/* 1514 */         int i = (isSet(17)) && (this.fields[17] > this.fields[1]) ? 1 : 0;
/*      */         
/* 1516 */         if ((i != 0) && (!((Calendar)localObject).isWeekDateSupported())) {
/* 1517 */           throw new IllegalArgumentException("week date is unsupported by " + this.type);
/*      */         }
/*      */         
/*      */         int k;
/*      */         
/* 1522 */         for (??? = 2; ??? < this.nextStamp; ???++) {
/* 1523 */           for (k = 0; k <= this.maxFieldIndex; k++) {
/* 1524 */             if (this.fields[k] == ???) {
/* 1525 */               ((Calendar)localObject).set(k, this.fields[(18 + k)]);
/* 1526 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1531 */         if (i != 0) {
/* 1532 */           ??? = isSet(3) ? this.fields[21] : 1;
/*      */           
/* 1534 */           k = isSet(7) ? this.fields[25] : ((Calendar)localObject).getFirstDayOfWeek();
/* 1535 */           ((Calendar)localObject).setWeekDate(this.fields[35], ???, k);
/*      */         }
/* 1537 */         ((Calendar)localObject).complete();
/*      */       }
/*      */       
/* 1540 */       return (Calendar)localObject;
/*      */     }
/*      */     
/*      */     private void allocateFields() {
/* 1544 */       if (this.fields == null) {
/* 1545 */         this.fields = new int[36];
/* 1546 */         this.nextStamp = 2;
/* 1547 */         this.maxFieldIndex = -1;
/*      */       }
/*      */     }
/*      */     
/*      */     private void internalSet(int paramInt1, int paramInt2) {
/* 1552 */       this.fields[paramInt1] = (this.nextStamp++);
/* 1553 */       if (this.nextStamp < 0) {
/* 1554 */         throw new IllegalStateException("stamp counter overflow");
/*      */       }
/* 1556 */       this.fields[(18 + paramInt1)] = paramInt2;
/* 1557 */       if ((paramInt1 > this.maxFieldIndex) && (paramInt1 < 17)) {
/* 1558 */         this.maxFieldIndex = paramInt1;
/*      */       }
/*      */     }
/*      */     
/*      */     private boolean isInstantSet() {
/* 1563 */       return this.nextStamp == 1;
/*      */     }
/*      */     
/*      */     private boolean isSet(int paramInt) {
/* 1567 */       return (this.fields != null) && (this.fields[paramInt] > 0);
/*      */     }
/*      */     
/*      */     private boolean isValidWeekParameter(int paramInt) {
/* 1571 */       return (paramInt > 0) && (paramInt <= 7);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Calendar()
/*      */   {
/* 1583 */     this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
/* 1584 */     this.sharedZone = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Calendar(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/* 1595 */     this.fields = new int[17];
/* 1596 */     this.isSet = new boolean[17];
/* 1597 */     this.stamp = new int[17];
/*      */     
/* 1599 */     this.zone = paramTimeZone;
/* 1600 */     setWeekCountData(paramLocale);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar getInstance()
/*      */   {
/* 1613 */     return createCalendar(TimeZone.getDefault(), Locale.getDefault(Locale.Category.FORMAT));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar getInstance(TimeZone paramTimeZone)
/*      */   {
/* 1627 */     return createCalendar(paramTimeZone, Locale.getDefault(Locale.Category.FORMAT));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar getInstance(Locale paramLocale)
/*      */   {
/* 1640 */     return createCalendar(TimeZone.getDefault(), paramLocale);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar getInstance(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/* 1655 */     return createCalendar(paramTimeZone, paramLocale);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Calendar createCalendar(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/* 1663 */     CalendarProvider localCalendarProvider = LocaleProviderAdapter.getAdapter(CalendarProvider.class, paramLocale).getCalendarProvider();
/* 1664 */     if (localCalendarProvider != null) {
/*      */       try {
/* 1666 */         return localCalendarProvider.getInstance(paramTimeZone, paramLocale);
/*      */       }
/*      */       catch (IllegalArgumentException localIllegalArgumentException) {}
/*      */     }
/*      */     
/*      */ 
/* 1672 */     Object localObject = null;
/*      */     
/* 1674 */     if (paramLocale.hasExtensions()) {
/* 1675 */       String str1 = paramLocale.getUnicodeLocaleType("ca");
/* 1676 */       if (str1 != null) {
/* 1677 */         switch (str1) {
/*      */         case "buddhist": 
/* 1679 */           localObject = new BuddhistCalendar(paramTimeZone, paramLocale);
/* 1680 */           break;
/*      */         case "japanese": 
/* 1682 */           localObject = new JapaneseImperialCalendar(paramTimeZone, paramLocale);
/* 1683 */           break;
/*      */         case "gregory": 
/* 1685 */           localObject = new GregorianCalendar(paramTimeZone, paramLocale);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/* 1690 */     if (localObject == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1697 */       if ((paramLocale.getLanguage() == "th") && (paramLocale.getCountry() == "TH")) {
/* 1698 */         localObject = new BuddhistCalendar(paramTimeZone, paramLocale);
/* 1699 */       } else if ((paramLocale.getVariant() == "JP") && (paramLocale.getLanguage() == "ja") && 
/* 1700 */         (paramLocale.getCountry() == "JP")) {
/* 1701 */         localObject = new JapaneseImperialCalendar(paramTimeZone, paramLocale);
/*      */       } else {
/* 1703 */         localObject = new GregorianCalendar(paramTimeZone, paramLocale);
/*      */       }
/*      */     }
/* 1706 */     return (Calendar)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static synchronized Locale[] getAvailableLocales()
/*      */   {
/* 1720 */     return DateFormat.getAvailableLocales();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void computeTime();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void computeFields();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Date getTime()
/*      */   {
/* 1755 */     return new Date(getTimeInMillis());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setTime(Date paramDate)
/*      */   {
/* 1770 */     setTimeInMillis(paramDate.getTime());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getTimeInMillis()
/*      */   {
/* 1781 */     if (!this.isTimeSet) {
/* 1782 */       updateTime();
/*      */     }
/* 1784 */     return this.time;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTimeInMillis(long paramLong)
/*      */   {
/* 1797 */     if ((this.time == paramLong) && (this.isTimeSet) && (this.areFieldsSet) && (this.areAllFieldsSet) && ((this.zone instanceof ZoneInfo)) && 
/* 1798 */       (!((ZoneInfo)this.zone).isDirty())) {
/* 1799 */       return;
/*      */     }
/* 1801 */     this.time = paramLong;
/* 1802 */     this.isTimeSet = true;
/* 1803 */     this.areFieldsSet = false;
/* 1804 */     computeFields();
/* 1805 */     this.areAllFieldsSet = (this.areFieldsSet = 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int get(int paramInt)
/*      */   {
/* 1826 */     complete();
/* 1827 */     return internalGet(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final int internalGet(int paramInt)
/*      */   {
/* 1840 */     return this.fields[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void internalSet(int paramInt1, int paramInt2)
/*      */   {
/* 1857 */     this.fields[paramInt1] = paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void set(int paramInt1, int paramInt2)
/*      */   {
/* 1878 */     if ((this.areFieldsSet) && (!this.areAllFieldsSet)) {
/* 1879 */       computeFields();
/*      */     }
/* 1881 */     internalSet(paramInt1, paramInt2);
/* 1882 */     this.isTimeSet = false;
/* 1883 */     this.areFieldsSet = false;
/* 1884 */     this.isSet[paramInt1] = true;
/* 1885 */     this.stamp[paramInt1] = (this.nextStamp++);
/* 1886 */     if (this.nextStamp == Integer.MAX_VALUE) {
/* 1887 */       adjustStamp();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void set(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1907 */     set(1, paramInt1);
/* 1908 */     set(2, paramInt2);
/* 1909 */     set(5, paramInt3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/* 1931 */     set(1, paramInt1);
/* 1932 */     set(2, paramInt2);
/* 1933 */     set(5, paramInt3);
/* 1934 */     set(11, paramInt4);
/* 1935 */     set(12, paramInt5);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */   {
/* 1959 */     set(1, paramInt1);
/* 1960 */     set(2, paramInt2);
/* 1961 */     set(5, paramInt3);
/* 1962 */     set(11, paramInt4);
/* 1963 */     set(12, paramInt5);
/* 1964 */     set(13, paramInt6);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void clear()
/*      */   {
/* 1983 */     for (int i = 0; i < this.fields.length;) {
/* 1984 */       this.stamp[i] = (this.fields[i] = 0);
/* 1985 */       this.isSet[(i++)] = false;
/*      */     }
/* 1987 */     this.areAllFieldsSet = (this.areFieldsSet = 0);
/* 1988 */     this.isTimeSet = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void clear(int paramInt)
/*      */   {
/* 2014 */     this.fields[paramInt] = 0;
/* 2015 */     this.stamp[paramInt] = 0;
/* 2016 */     this.isSet[paramInt] = false;
/*      */     
/* 2018 */     this.areAllFieldsSet = (this.areFieldsSet = 0);
/* 2019 */     this.isTimeSet = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isSet(int paramInt)
/*      */   {
/* 2033 */     return this.stamp[paramInt] != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDisplayName(int paramInt1, int paramInt2, Locale paramLocale)
/*      */   {
/* 2081 */     if (!checkDisplayNameParams(paramInt1, paramInt2, 1, 4, paramLocale, 645))
/*      */     {
/* 2083 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2087 */     if ((isStandaloneStyle(paramInt2)) || (isNarrowStyle(paramInt2))) {
/* 2088 */       return CalendarDataUtility.retrieveFieldValueName(getCalendarType(), paramInt1, 
/* 2089 */         get(paramInt1), paramInt2, paramLocale);
/*      */     }
/*      */     
/*      */ 
/* 2093 */     DateFormatSymbols localDateFormatSymbols = DateFormatSymbols.getInstance(paramLocale);
/* 2094 */     String[] arrayOfString = getFieldStrings(paramInt1, paramInt2, localDateFormatSymbols);
/* 2095 */     if (arrayOfString != null) {
/* 2096 */       int i = get(paramInt1);
/* 2097 */       if (i < arrayOfString.length) {
/* 2098 */         return arrayOfString[i];
/*      */       }
/*      */     }
/* 2101 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Map<String, Integer> getDisplayNames(int paramInt1, int paramInt2, Locale paramLocale)
/*      */   {
/* 2154 */     if (!checkDisplayNameParams(paramInt1, paramInt2, 0, 4, paramLocale, 645))
/*      */     {
/* 2156 */       return null;
/*      */     }
/* 2158 */     if ((paramInt2 == 0) || (isStandaloneStyle(paramInt2))) {
/* 2159 */       return CalendarDataUtility.retrieveFieldValueNames(getCalendarType(), paramInt1, paramInt2, paramLocale);
/*      */     }
/*      */     
/* 2162 */     return getDisplayNamesImpl(paramInt1, paramInt2, paramLocale);
/*      */   }
/*      */   
/*      */   private Map<String, Integer> getDisplayNamesImpl(int paramInt1, int paramInt2, Locale paramLocale) {
/* 2166 */     DateFormatSymbols localDateFormatSymbols = DateFormatSymbols.getInstance(paramLocale);
/* 2167 */     String[] arrayOfString = getFieldStrings(paramInt1, paramInt2, localDateFormatSymbols);
/* 2168 */     if (arrayOfString != null) {
/* 2169 */       HashMap localHashMap = new HashMap();
/* 2170 */       for (int i = 0; i < arrayOfString.length; i++) {
/* 2171 */         if (arrayOfString[i].length() != 0)
/*      */         {
/*      */ 
/* 2174 */           localHashMap.put(arrayOfString[i], Integer.valueOf(i)); }
/*      */       }
/* 2176 */       return localHashMap;
/*      */     }
/* 2178 */     return null;
/*      */   }
/*      */   
/*      */   boolean checkDisplayNameParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Locale paramLocale, int paramInt5)
/*      */   {
/* 2183 */     int i = getBaseStyle(paramInt2);
/* 2184 */     if ((paramInt1 < 0) || (paramInt1 >= this.fields.length) || (i < paramInt3) || (i > paramInt4))
/*      */     {
/* 2186 */       throw new IllegalArgumentException();
/*      */     }
/* 2188 */     if (paramLocale == null) {
/* 2189 */       throw new NullPointerException();
/*      */     }
/* 2191 */     return isFieldSet(paramInt5, paramInt1);
/*      */   }
/*      */   
/*      */   private String[] getFieldStrings(int paramInt1, int paramInt2, DateFormatSymbols paramDateFormatSymbols) {
/* 2195 */     int i = getBaseStyle(paramInt2);
/*      */     
/*      */ 
/* 2198 */     if (i == 4) {
/* 2199 */       return null;
/*      */     }
/*      */     
/* 2202 */     String[] arrayOfString = null;
/* 2203 */     switch (paramInt1) {
/*      */     case 0: 
/* 2205 */       arrayOfString = paramDateFormatSymbols.getEras();
/* 2206 */       break;
/*      */     
/*      */     case 2: 
/* 2209 */       arrayOfString = i == 2 ? paramDateFormatSymbols.getMonths() : paramDateFormatSymbols.getShortMonths();
/* 2210 */       break;
/*      */     
/*      */     case 7: 
/* 2213 */       arrayOfString = i == 2 ? paramDateFormatSymbols.getWeekdays() : paramDateFormatSymbols.getShortWeekdays();
/* 2214 */       break;
/*      */     
/*      */     case 9: 
/* 2217 */       arrayOfString = paramDateFormatSymbols.getAmPmStrings();
/*      */     }
/*      */     
/* 2220 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void complete()
/*      */   {
/* 2232 */     if (!this.isTimeSet) {
/* 2233 */       updateTime();
/*      */     }
/* 2235 */     if ((!this.areFieldsSet) || (!this.areAllFieldsSet)) {
/* 2236 */       computeFields();
/* 2237 */       this.areAllFieldsSet = (this.areFieldsSet = 1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean isExternallySet(int paramInt)
/*      */   {
/* 2255 */     return this.stamp[paramInt] >= 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int getSetStateFields()
/*      */   {
/* 2265 */     int i = 0;
/* 2266 */     for (int j = 0; j < this.fields.length; j++) {
/* 2267 */       if (this.stamp[j] != 0) {
/* 2268 */         i |= 1 << j;
/*      */       }
/*      */     }
/* 2271 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void setFieldsComputed(int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2288 */     if (paramInt == 131071) {
/* 2289 */       for (i = 0; i < this.fields.length; i++) {
/* 2290 */         this.stamp[i] = 1;
/* 2291 */         this.isSet[i] = true;
/*      */       }
/* 2293 */       this.areFieldsSet = (this.areAllFieldsSet = 1);
/*      */     } else {
/* 2295 */       for (i = 0; i < this.fields.length; i++) {
/* 2296 */         if ((paramInt & 0x1) == 1) {
/* 2297 */           this.stamp[i] = 1;
/* 2298 */           this.isSet[i] = true;
/*      */         }
/* 2300 */         else if ((this.areAllFieldsSet) && (this.isSet[i] == 0)) {
/* 2301 */           this.areAllFieldsSet = false;
/*      */         }
/*      */         
/* 2304 */         paramInt >>>= 1;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void setFieldsNormalized(int paramInt)
/*      */   {
/* 2325 */     if (paramInt != 131071) {
/* 2326 */       for (int i = 0; i < this.fields.length; i++) {
/* 2327 */         if ((paramInt & 0x1) == 0) {
/* 2328 */           this.stamp[i] = (this.fields[i] = 0);
/* 2329 */           this.isSet[i] = false;
/*      */         }
/* 2331 */         paramInt >>= 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2337 */     this.areFieldsSet = true;
/* 2338 */     this.areAllFieldsSet = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean isPartiallyNormalized()
/*      */   {
/* 2346 */     return (this.areFieldsSet) && (!this.areAllFieldsSet);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean isFullyNormalized()
/*      */   {
/* 2354 */     return (this.areFieldsSet) && (this.areAllFieldsSet);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void setUnnormalized()
/*      */   {
/* 2361 */     this.areFieldsSet = (this.areAllFieldsSet = 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static boolean isFieldSet(int paramInt1, int paramInt2)
/*      */   {
/* 2369 */     return (paramInt1 & 1 << paramInt2) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int selectFields()
/*      */   {
/* 2399 */     int i = 2;
/*      */     
/* 2401 */     if (this.stamp[0] != 0) {
/* 2402 */       i |= 0x1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2416 */     int j = this.stamp[7];
/* 2417 */     int k = this.stamp[2];
/* 2418 */     int m = this.stamp[5];
/* 2419 */     int n = aggregateStamp(this.stamp[4], j);
/* 2420 */     int i1 = aggregateStamp(this.stamp[8], j);
/* 2421 */     int i2 = this.stamp[6];
/* 2422 */     int i3 = aggregateStamp(this.stamp[3], j);
/*      */     
/* 2424 */     int i4 = m;
/* 2425 */     if (n > i4) {
/* 2426 */       i4 = n;
/*      */     }
/* 2428 */     if (i1 > i4) {
/* 2429 */       i4 = i1;
/*      */     }
/* 2431 */     if (i2 > i4) {
/* 2432 */       i4 = i2;
/*      */     }
/* 2434 */     if (i3 > i4) {
/* 2435 */       i4 = i3;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2442 */     if (i4 == 0) {
/* 2443 */       n = this.stamp[4];
/* 2444 */       i1 = Math.max(this.stamp[8], j);
/* 2445 */       i3 = this.stamp[3];
/* 2446 */       i4 = Math.max(Math.max(n, i1), i3);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2452 */       if (i4 == 0) {
/* 2453 */         i4 = m = k;
/*      */       }
/*      */     }
/*      */     
/* 2457 */     if ((i4 == m) || ((i4 == n) && (this.stamp[4] >= this.stamp[3])) || ((i4 == i1) && (this.stamp[8] >= this.stamp[3])))
/*      */     {
/*      */ 
/* 2460 */       i |= 0x4;
/* 2461 */       if (i4 == m) {
/* 2462 */         i |= 0x20;
/*      */       } else {
/* 2464 */         assert ((i4 == n) || (i4 == i1));
/* 2465 */         if (j != 0) {
/* 2466 */           i |= 0x80;
/*      */         }
/* 2468 */         if (n == i1)
/*      */         {
/*      */ 
/* 2471 */           if (this.stamp[4] >= this.stamp[8]) {
/* 2472 */             i |= 0x10;
/*      */           } else {
/* 2474 */             i |= 0x100;
/*      */           }
/*      */         }
/* 2477 */         else if (i4 == n) {
/* 2478 */           i |= 0x10;
/*      */         } else {
/* 2480 */           assert (i4 == i1);
/* 2481 */           if (this.stamp[8] != 0) {
/* 2482 */             i |= 0x100;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 2488 */       assert ((i4 == i2) || (i4 == i3) || (i4 == 0));
/*      */       
/* 2490 */       if (i4 == i2) {
/* 2491 */         i |= 0x40;
/*      */       } else {
/* 2493 */         assert (i4 == i3);
/* 2494 */         if (j != 0) {
/* 2495 */           i |= 0x80;
/*      */         }
/* 2497 */         i |= 0x8;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2504 */     int i5 = this.stamp[11];
/* 2505 */     int i6 = aggregateStamp(this.stamp[10], this.stamp[9]);
/* 2506 */     i4 = i6 > i5 ? i6 : i5;
/*      */     
/*      */ 
/* 2509 */     if (i4 == 0) {
/* 2510 */       i4 = Math.max(this.stamp[10], this.stamp[9]);
/*      */     }
/*      */     
/*      */ 
/* 2514 */     if (i4 != 0) {
/* 2515 */       if (i4 == i5) {
/* 2516 */         i |= 0x800;
/*      */       } else {
/* 2518 */         i |= 0x400;
/* 2519 */         if (this.stamp[9] != 0) {
/* 2520 */           i |= 0x200;
/*      */         }
/*      */       }
/*      */     }
/* 2524 */     if (this.stamp[12] != 0) {
/* 2525 */       i |= 0x1000;
/*      */     }
/* 2527 */     if (this.stamp[13] != 0) {
/* 2528 */       i |= 0x2000;
/*      */     }
/* 2530 */     if (this.stamp[14] != 0) {
/* 2531 */       i |= 0x4000;
/*      */     }
/* 2533 */     if (this.stamp[15] >= 2) {
/* 2534 */       i |= 0x8000;
/*      */     }
/* 2536 */     if (this.stamp[16] >= 2) {
/* 2537 */       i |= 0x10000;
/*      */     }
/*      */     
/* 2540 */     return i;
/*      */   }
/*      */   
/*      */   int getBaseStyle(int paramInt) {
/* 2544 */     return paramInt & 0xFFFF7FFF;
/*      */   }
/*      */   
/*      */   boolean isStandaloneStyle(int paramInt) {
/* 2548 */     return (paramInt & 0x8000) != 0;
/*      */   }
/*      */   
/*      */   boolean isNarrowStyle(int paramInt) {
/* 2552 */     return (paramInt == 4) || (paramInt == 32772);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int aggregateStamp(int paramInt1, int paramInt2)
/*      */   {
/* 2562 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/* 2563 */       return 0;
/*      */     }
/* 2565 */     return paramInt1 > paramInt2 ? paramInt1 : paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2584 */   public static Set<String> getAvailableCalendarTypes() { return AvailableCalendarTypes.SET; }
/*      */   
/*      */   private static class AvailableCalendarTypes {
/*      */     private static final Set<String> SET;
/*      */     
/*      */     static {
/* 2590 */       HashSet localHashSet = new HashSet(3);
/* 2591 */       localHashSet.add("gregory");
/* 2592 */       localHashSet.add("buddhist");
/* 2593 */       localHashSet.add("japanese");
/* 2594 */       SET = Collections.unmodifiableSet(localHashSet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getCalendarType()
/*      */   {
/* 2618 */     return getClass().getName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 2646 */     if (this == paramObject) {
/* 2647 */       return true;
/*      */     }
/*      */     try {
/* 2650 */       Calendar localCalendar = (Calendar)paramObject;
/* 2651 */       if ((compareTo(getMillisOf(localCalendar)) == 0) && (this.lenient == localCalendar.lenient) && (this.firstDayOfWeek == localCalendar.firstDayOfWeek) && (this.minimalDaysInFirstWeek == localCalendar.minimalDaysInFirstWeek)) {}
/*      */       
/*      */ 
/*      */ 
/* 2655 */       return this.zone.equals(localCalendar.zone);
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/*      */ 
/* 2661 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 2676 */     int i = (this.lenient ? 1 : 0) | this.firstDayOfWeek << 1 | this.minimalDaysInFirstWeek << 4 | this.zone.hashCode() << 7;
/* 2677 */     long l = getMillisOf(this);
/* 2678 */     return (int)l ^ (int)(l >> 32) ^ i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean before(Object paramObject)
/*      */   {
/* 2699 */     return ((paramObject instanceof Calendar)) && (compareTo((Calendar)paramObject) < 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean after(Object paramObject)
/*      */   {
/* 2720 */     return ((paramObject instanceof Calendar)) && (compareTo((Calendar)paramObject) > 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int compareTo(Calendar paramCalendar)
/*      */   {
/* 2744 */     return compareTo(getMillisOf(paramCalendar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void add(int paramInt1, int paramInt2);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void roll(int paramInt, boolean paramBoolean);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void roll(int paramInt1, int paramInt2)
/*      */   {
/* 2803 */     while (paramInt2 > 0) {
/* 2804 */       roll(paramInt1, true);
/* 2805 */       paramInt2--;
/*      */     }
/* 2807 */     while (paramInt2 < 0) {
/* 2808 */       roll(paramInt1, false);
/* 2809 */       paramInt2++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTimeZone(TimeZone paramTimeZone)
/*      */   {
/* 2820 */     this.zone = paramTimeZone;
/* 2821 */     this.sharedZone = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2831 */     this.areAllFieldsSet = (this.areFieldsSet = 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TimeZone getTimeZone()
/*      */   {
/* 2843 */     if (this.sharedZone) {
/* 2844 */       this.zone = ((TimeZone)this.zone.clone());
/* 2845 */       this.sharedZone = false;
/*      */     }
/* 2847 */     return this.zone;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   TimeZone getZone()
/*      */   {
/* 2854 */     return this.zone;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void setZoneShared(boolean paramBoolean)
/*      */   {
/* 2861 */     this.sharedZone = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLenient(boolean paramBoolean)
/*      */   {
/* 2878 */     this.lenient = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLenient()
/*      */   {
/* 2890 */     return this.lenient;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFirstDayOfWeek(int paramInt)
/*      */   {
/* 2903 */     if (this.firstDayOfWeek == paramInt) {
/* 2904 */       return;
/*      */     }
/* 2906 */     this.firstDayOfWeek = paramInt;
/* 2907 */     invalidateWeekFields();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getFirstDayOfWeek()
/*      */   {
/* 2920 */     return this.firstDayOfWeek;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMinimalDaysInFirstWeek(int paramInt)
/*      */   {
/* 2935 */     if (this.minimalDaysInFirstWeek == paramInt) {
/* 2936 */       return;
/*      */     }
/* 2938 */     this.minimalDaysInFirstWeek = paramInt;
/* 2939 */     invalidateWeekFields();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinimalDaysInFirstWeek()
/*      */   {
/* 2954 */     return this.minimalDaysInFirstWeek;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWeekDateSupported()
/*      */   {
/* 2970 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWeekYear()
/*      */   {
/* 2992 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWeekDate(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 3028 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWeeksInWeekYear()
/*      */   {
/* 3049 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getMinimum(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getMaximum(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getGreatestMinimum(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getLeastMaximum(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getActualMinimum(int paramInt)
/*      */   {
/* 3147 */     int i = getGreatestMinimum(paramInt);
/* 3148 */     int j = getMinimum(paramInt);
/*      */     
/*      */ 
/* 3151 */     if (i == j) {
/* 3152 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3157 */     Calendar localCalendar = (Calendar)clone();
/* 3158 */     localCalendar.setLenient(true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3163 */     int k = i;
/*      */     do
/*      */     {
/* 3166 */       localCalendar.set(paramInt, i);
/* 3167 */       if (localCalendar.get(paramInt) != i) {
/*      */         break;
/*      */       }
/* 3170 */       k = i;
/* 3171 */       i--;
/*      */     }
/* 3173 */     while (i >= j);
/*      */     
/* 3175 */     return k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getActualMaximum(int paramInt)
/*      */   {
/* 3201 */     int i = getLeastMaximum(paramInt);
/* 3202 */     int j = getMaximum(paramInt);
/*      */     
/*      */ 
/* 3205 */     if (i == j) {
/* 3206 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3211 */     Calendar localCalendar = (Calendar)clone();
/* 3212 */     localCalendar.setLenient(true);
/*      */     
/*      */ 
/*      */ 
/* 3216 */     if ((paramInt == 3) || (paramInt == 4)) {
/* 3217 */       localCalendar.set(7, this.firstDayOfWeek);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3223 */     int k = i;
/*      */     do
/*      */     {
/* 3226 */       localCalendar.set(paramInt, i);
/* 3227 */       if (localCalendar.get(paramInt) != i) {
/*      */         break;
/*      */       }
/* 3230 */       k = i;
/* 3231 */       i++;
/*      */     }
/* 3233 */     while (i <= j);
/*      */     
/* 3235 */     return k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/* 3247 */       Calendar localCalendar = (Calendar)super.clone();
/*      */       
/* 3249 */       localCalendar.fields = new int[17];
/* 3250 */       localCalendar.isSet = new boolean[17];
/* 3251 */       localCalendar.stamp = new int[17];
/* 3252 */       for (int i = 0; i < 17; i++) {
/* 3253 */         localCalendar.fields[i] = this.fields[i];
/* 3254 */         localCalendar.stamp[i] = this.stamp[i];
/* 3255 */         localCalendar.isSet[i] = this.isSet[i];
/*      */       }
/* 3257 */       localCalendar.zone = ((TimeZone)this.zone.clone());
/* 3258 */       return localCalendar;
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException)
/*      */     {
/* 3262 */       throw new InternalError(localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */   
/* 3266 */   private static final String[] FIELD_NAME = { "ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static String getFieldName(int paramInt)
/*      */   {
/* 3282 */     return FIELD_NAME[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 3299 */     StringBuilder localStringBuilder = new StringBuilder(800);
/* 3300 */     localStringBuilder.append(getClass().getName()).append('[');
/* 3301 */     appendValue(localStringBuilder, "time", this.isTimeSet, this.time);
/* 3302 */     localStringBuilder.append(",areFieldsSet=").append(this.areFieldsSet);
/* 3303 */     localStringBuilder.append(",areAllFieldsSet=").append(this.areAllFieldsSet);
/* 3304 */     localStringBuilder.append(",lenient=").append(this.lenient);
/* 3305 */     localStringBuilder.append(",zone=").append(this.zone);
/* 3306 */     appendValue(localStringBuilder, ",firstDayOfWeek", true, this.firstDayOfWeek);
/* 3307 */     appendValue(localStringBuilder, ",minimalDaysInFirstWeek", true, this.minimalDaysInFirstWeek);
/* 3308 */     for (int i = 0; i < 17; i++) {
/* 3309 */       localStringBuilder.append(',');
/* 3310 */       appendValue(localStringBuilder, FIELD_NAME[i], isSet(i), this.fields[i]);
/*      */     }
/* 3312 */     localStringBuilder.append(']');
/* 3313 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   private static void appendValue(StringBuilder paramStringBuilder, String paramString, boolean paramBoolean, long paramLong)
/*      */   {
/* 3319 */     paramStringBuilder.append(paramString).append('=');
/* 3320 */     if (paramBoolean) {
/* 3321 */       paramStringBuilder.append(paramLong);
/*      */     } else {
/* 3323 */       paramStringBuilder.append('?');
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setWeekCountData(Locale paramLocale)
/*      */   {
/* 3336 */     int[] arrayOfInt = (int[])cachedLocaleData.get(paramLocale);
/* 3337 */     if (arrayOfInt == null) {
/* 3338 */       arrayOfInt = new int[2];
/* 3339 */       arrayOfInt[0] = CalendarDataUtility.retrieveFirstDayOfWeek(paramLocale);
/* 3340 */       arrayOfInt[1] = CalendarDataUtility.retrieveMinimalDaysInFirstWeek(paramLocale);
/* 3341 */       cachedLocaleData.putIfAbsent(paramLocale, arrayOfInt);
/*      */     }
/* 3343 */     this.firstDayOfWeek = arrayOfInt[0];
/* 3344 */     this.minimalDaysInFirstWeek = arrayOfInt[1];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void updateTime()
/*      */   {
/* 3353 */     computeTime();
/*      */     
/*      */ 
/* 3356 */     this.isTimeSet = true;
/*      */   }
/*      */   
/*      */   private int compareTo(long paramLong) {
/* 3360 */     long l = getMillisOf(this);
/* 3361 */     return l == paramLong ? 0 : l > paramLong ? 1 : -1;
/*      */   }
/*      */   
/*      */   private static long getMillisOf(Calendar paramCalendar) {
/* 3365 */     if (paramCalendar.isTimeSet) {
/* 3366 */       return paramCalendar.time;
/*      */     }
/* 3368 */     Calendar localCalendar = (Calendar)paramCalendar.clone();
/* 3369 */     localCalendar.setLenient(true);
/* 3370 */     return localCalendar.getTimeInMillis();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void adjustStamp()
/*      */   {
/* 3378 */     int i = 2;
/* 3379 */     int j = 2;
/*      */     for (;;)
/*      */     {
/* 3382 */       int k = Integer.MAX_VALUE;
/* 3383 */       for (int m = 0; m < this.stamp.length; m++) {
/* 3384 */         int n = this.stamp[m];
/* 3385 */         if ((n >= j) && (k > n)) {
/* 3386 */           k = n;
/*      */         }
/* 3388 */         if (i < n) {
/* 3389 */           i = n;
/*      */         }
/*      */       }
/* 3392 */       if ((i != k) && (k == Integer.MAX_VALUE)) {
/*      */         break;
/*      */       }
/* 3395 */       for (m = 0; m < this.stamp.length; m++) {
/* 3396 */         if (this.stamp[m] == k) {
/* 3397 */           this.stamp[m] = j;
/*      */         }
/*      */       }
/* 3400 */       j++;
/* 3401 */       if (k == i) {
/*      */         break;
/*      */       }
/*      */     }
/* 3405 */     this.nextStamp = j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void invalidateWeekFields()
/*      */   {
/* 3414 */     if ((this.stamp[4] != 1) && (this.stamp[3] != 1))
/*      */     {
/* 3416 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3422 */     Calendar localCalendar = (Calendar)clone();
/* 3423 */     localCalendar.setLenient(true);
/* 3424 */     localCalendar.clear(4);
/* 3425 */     localCalendar.clear(3);
/*      */     int i;
/* 3427 */     if (this.stamp[4] == 1) {
/* 3428 */       i = localCalendar.get(4);
/* 3429 */       if (this.fields[4] != i) {
/* 3430 */         this.fields[4] = i;
/*      */       }
/*      */     }
/*      */     
/* 3434 */     if (this.stamp[3] == 1) {
/* 3435 */       i = localCalendar.get(3);
/* 3436 */       if (this.fields[3] != i) {
/* 3437 */         this.fields[3] = i;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 3460 */     if (!this.isTimeSet) {
/*      */       try {
/* 3462 */         updateTime();
/*      */       }
/*      */       catch (IllegalArgumentException localIllegalArgumentException) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3470 */     TimeZone localTimeZone = null;
/* 3471 */     if ((this.zone instanceof ZoneInfo)) {
/* 3472 */       SimpleTimeZone localSimpleTimeZone = ((ZoneInfo)this.zone).getLastRuleInstance();
/* 3473 */       if (localSimpleTimeZone == null) {
/* 3474 */         localSimpleTimeZone = new SimpleTimeZone(this.zone.getRawOffset(), this.zone.getID());
/*      */       }
/* 3476 */       localTimeZone = this.zone;
/* 3477 */       this.zone = localSimpleTimeZone;
/*      */     }
/*      */     
/*      */ 
/* 3481 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3486 */     paramObjectOutputStream.writeObject(localTimeZone);
/* 3487 */     if (localTimeZone != null)
/* 3488 */       this.zone = localTimeZone;
/*      */   }
/*      */   
/*      */   private static class CalendarAccessControlContext {
/*      */     private static final AccessControlContext INSTANCE;
/*      */     
/*      */     static {
/* 3495 */       RuntimePermission localRuntimePermission = new RuntimePermission("accessClassInPackage.sun.util.calendar");
/* 3496 */       PermissionCollection localPermissionCollection = localRuntimePermission.newPermissionCollection();
/* 3497 */       localPermissionCollection.add(localRuntimePermission);
/* 3498 */       INSTANCE = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, localPermissionCollection) });
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 3512 */     final ObjectInputStream localObjectInputStream = paramObjectInputStream;
/* 3513 */     localObjectInputStream.defaultReadObject();
/*      */     
/* 3515 */     this.stamp = new int[17];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3520 */     if (this.serialVersionOnStream >= 2)
/*      */     {
/* 3522 */       this.isTimeSet = true;
/* 3523 */       if (this.fields == null) {
/* 3524 */         this.fields = new int[17];
/*      */       }
/* 3526 */       if (this.isSet == null) {
/* 3527 */         this.isSet = new boolean[17];
/*      */       }
/*      */     }
/* 3530 */     else if (this.serialVersionOnStream >= 0)
/*      */     {
/* 3532 */       for (int i = 0; i < 17; i++) {
/* 3533 */         this.stamp[i] = (this.isSet[i] != 0 ? 1 : 0);
/*      */       }
/*      */     }
/*      */     
/* 3537 */     this.serialVersionOnStream = 1;
/*      */     
/*      */ 
/* 3540 */     ZoneInfo localZoneInfo = null;
/*      */     Object localObject;
/* 3542 */     try { localZoneInfo = (ZoneInfo)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */       {
/*      */         public ZoneInfo run() throws Exception
/*      */         {
/* 3546 */           return (ZoneInfo)localObjectInputStream.readObject();
/*      */         }
/*      */         
/* 3549 */       }, CalendarAccessControlContext.INSTANCE);
/*      */     } catch (PrivilegedActionException localPrivilegedActionException) {
/* 3551 */       localObject = localPrivilegedActionException.getException();
/* 3552 */       if (!(localObject instanceof OptionalDataException)) {
/* 3553 */         if ((localObject instanceof RuntimeException))
/* 3554 */           throw ((RuntimeException)localObject);
/* 3555 */         if ((localObject instanceof IOException))
/* 3556 */           throw ((IOException)localObject);
/* 3557 */         if ((localObject instanceof ClassNotFoundException)) {
/* 3558 */           throw ((ClassNotFoundException)localObject);
/*      */         }
/* 3560 */         throw new RuntimeException((Throwable)localObject);
/*      */       }
/*      */     }
/* 3563 */     if (localZoneInfo != null) {
/* 3564 */       this.zone = localZoneInfo;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3571 */     if ((this.zone instanceof SimpleTimeZone)) {
/* 3572 */       String str = this.zone.getID();
/* 3573 */       localObject = TimeZone.getTimeZone(str);
/* 3574 */       if ((localObject != null) && (((TimeZone)localObject).hasSameRules(this.zone)) && (((TimeZone)localObject).getID().equals(str))) {
/* 3575 */         this.zone = ((TimeZone)localObject);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Instant toInstant()
/*      */   {
/* 3590 */     return Instant.ofEpochMilli(getTimeInMillis());
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Calendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */