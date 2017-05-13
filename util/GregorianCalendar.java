/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.time.Instant;
/*      */ import java.time.ZonedDateTime;
/*      */ import java.time.temporal.ChronoField;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.calendar.BaseCalendar.Date;
/*      */ import sun.util.calendar.CalendarDate;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.Era;
/*      */ import sun.util.calendar.Gregorian;
/*      */ import sun.util.calendar.JulianCalendar;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GregorianCalendar
/*      */   extends Calendar
/*      */ {
/*      */   public static final int BC = 0;
/*      */   static final int BCE = 0;
/*      */   public static final int AD = 1;
/*      */   static final int CE = 1;
/*      */   private static final int EPOCH_OFFSET = 719163;
/*      */   private static final int EPOCH_YEAR = 1970;
/*  400 */   static final int[] MONTH_LENGTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*      */   
/*  402 */   static final int[] LEAP_MONTH_LENGTH = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int ONE_SECOND = 1000;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int ONE_MINUTE = 60000;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int ONE_HOUR = 3600000;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long ONE_DAY = 86400000L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long ONE_WEEK = 604800000L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  439 */   static final int[] MIN_VALUES = { 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  458 */   static final int[] LEAST_MAX_VALUES = { 1, 292269054, 11, 52, 4, 28, 365, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  477 */   static final int[] MAX_VALUES = { 1, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final long serialVersionUID = -8125100834729963327L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  503 */   private static final Gregorian gcal = CalendarSystem.getGregorianCalendar();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static JulianCalendar jcal;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Era[] jeras;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  527 */   private long gregorianCutover = -12219292800000L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  532 */   private transient long gregorianCutoverDate = 577736L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  539 */   private transient int gregorianCutoverYear = 1582;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  545 */   private transient int gregorianCutoverYearJulian = 1582;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient BaseCalendar.Date gdate;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient BaseCalendar.Date cdate;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient BaseCalendar calsys;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient int[] zoneOffsets;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient int[] originalFields;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GregorianCalendar()
/*      */   {
/*  591 */     this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
/*  592 */     setZoneShared(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GregorianCalendar(TimeZone paramTimeZone)
/*      */   {
/*  603 */     this(paramTimeZone, Locale.getDefault(Locale.Category.FORMAT));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GregorianCalendar(Locale paramLocale)
/*      */   {
/*  613 */     this(TimeZone.getDefaultRef(), paramLocale);
/*  614 */     setZoneShared(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GregorianCalendar(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/*  625 */     super(paramTimeZone, paramLocale);
/*  626 */     this.gdate = gcal.newCalendarDate(paramTimeZone);
/*  627 */     setTimeInMillis(System.currentTimeMillis());
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
/*      */   public GregorianCalendar(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  640 */     this(paramInt1, paramInt2, paramInt3, 0, 0, 0, 0);
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
/*      */   public GregorianCalendar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/*  658 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0, 0);
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
/*      */   public GregorianCalendar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */   {
/*  678 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0);
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
/*      */   GregorianCalendar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*      */   {
/*  700 */     this.gdate = gcal.newCalendarDate(getZone());
/*  701 */     set(1, paramInt1);
/*  702 */     set(2, paramInt2);
/*  703 */     set(5, paramInt3);
/*      */     
/*      */ 
/*      */ 
/*  707 */     if ((paramInt4 >= 12) && (paramInt4 <= 23))
/*      */     {
/*      */ 
/*      */ 
/*  711 */       internalSet(9, 1);
/*  712 */       internalSet(10, paramInt4 - 12);
/*      */     }
/*      */     else
/*      */     {
/*  716 */       internalSet(10, paramInt4);
/*      */     }
/*      */     
/*  719 */     setFieldsComputed(1536);
/*      */     
/*  721 */     set(11, paramInt4);
/*  722 */     set(12, paramInt5);
/*  723 */     set(13, paramInt6);
/*      */     
/*      */ 
/*  726 */     internalSet(14, paramInt7);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   GregorianCalendar(TimeZone paramTimeZone, Locale paramLocale, boolean paramBoolean)
/*      */   {
/*  737 */     super(paramTimeZone, paramLocale);
/*  738 */     this.gdate = gcal.newCalendarDate(getZone());
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
/*      */   public void setGregorianChange(Date paramDate)
/*      */   {
/*  757 */     long l = paramDate.getTime();
/*  758 */     if (l == this.gregorianCutover) {
/*  759 */       return;
/*      */     }
/*      */     
/*      */ 
/*  763 */     complete();
/*  764 */     setGregorianChange(l);
/*      */   }
/*      */   
/*      */   private void setGregorianChange(long paramLong) {
/*  768 */     this.gregorianCutover = paramLong;
/*  769 */     this.gregorianCutoverDate = (CalendarUtils.floorDivide(paramLong, 86400000L) + 719163L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  777 */     if (paramLong == Long.MAX_VALUE) {
/*  778 */       this.gregorianCutoverDate += 1L;
/*      */     }
/*      */     
/*  781 */     BaseCalendar.Date localDate = getGregorianCutoverDate();
/*      */     
/*      */ 
/*  784 */     this.gregorianCutoverYear = localDate.getYear();
/*      */     
/*  786 */     BaseCalendar localBaseCalendar = getJulianCalendarSystem();
/*  787 */     localDate = (BaseCalendar.Date)localBaseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
/*  788 */     localBaseCalendar.getCalendarDateFromFixedDate(localDate, this.gregorianCutoverDate - 1L);
/*  789 */     this.gregorianCutoverYearJulian = localDate.getNormalizedYear();
/*      */     
/*  791 */     if (this.time < this.gregorianCutover)
/*      */     {
/*      */ 
/*  794 */       setUnnormalized();
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
/*      */   public final Date getGregorianChange()
/*      */   {
/*  807 */     return new Date(this.gregorianCutover);
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
/*      */   public boolean isLeapYear(int paramInt)
/*      */   {
/*  820 */     if ((paramInt & 0x3) != 0) {
/*  821 */       return false;
/*      */     }
/*      */     
/*  824 */     if (paramInt > this.gregorianCutoverYear) {
/*  825 */       return (paramInt % 100 != 0) || (paramInt % 400 == 0);
/*      */     }
/*  827 */     if (paramInt < this.gregorianCutoverYearJulian) {
/*  828 */       return true;
/*      */     }
/*      */     
/*      */     int i;
/*      */     
/*  833 */     if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
/*  834 */       BaseCalendar.Date localDate = getCalendarDate(this.gregorianCutoverDate);
/*  835 */       i = localDate.getMonth() < 3 ? 1 : 0;
/*      */     } else {
/*  837 */       i = paramInt == this.gregorianCutoverYear ? 1 : 0;
/*      */     }
/*  839 */     return (paramInt % 100 != 0) || (paramInt % 400 == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getCalendarType()
/*      */   {
/*  850 */     return "gregory";
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  870 */     return ((paramObject instanceof GregorianCalendar)) && (super.equals(paramObject)) && (this.gregorianCutover == ((GregorianCalendar)paramObject).gregorianCutover);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  879 */     return super.hashCode() ^ (int)this.gregorianCutoverDate;
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
/*      */   public void add(int paramInt1, int paramInt2)
/*      */   {
/*  914 */     if (paramInt2 == 0) {
/*  915 */       return;
/*      */     }
/*      */     
/*  918 */     if ((paramInt1 < 0) || (paramInt1 >= 15)) {
/*  919 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*  923 */     complete();
/*      */     int i;
/*  925 */     if (paramInt1 == 1) {
/*  926 */       i = internalGet(1);
/*  927 */       if (internalGetEra() == 1) {
/*  928 */         i += paramInt2;
/*  929 */         if (i > 0) {
/*  930 */           set(1, i);
/*      */         } else {
/*  932 */           set(1, 1 - i);
/*      */           
/*  934 */           set(0, 0);
/*      */         }
/*      */       }
/*      */       else {
/*  938 */         i -= paramInt2;
/*  939 */         if (i > 0) {
/*  940 */           set(1, i);
/*      */         } else {
/*  942 */           set(1, 1 - i);
/*      */           
/*  944 */           set(0, 1);
/*      */         }
/*      */       }
/*  947 */       pinDayOfMonth();
/*  948 */     } else if (paramInt1 == 2) {
/*  949 */       i = internalGet(2) + paramInt2;
/*  950 */       int j = internalGet(1);
/*      */       
/*      */       int k;
/*  953 */       if (i >= 0) {
/*  954 */         k = i / 12;
/*      */       } else {
/*  956 */         k = (i + 1) / 12 - 1;
/*      */       }
/*  958 */       if (k != 0) {
/*  959 */         if (internalGetEra() == 1) {
/*  960 */           j += k;
/*  961 */           if (j > 0) {
/*  962 */             set(1, j);
/*      */           } else {
/*  964 */             set(1, 1 - j);
/*      */             
/*  966 */             set(0, 0);
/*      */           }
/*      */         }
/*      */         else {
/*  970 */           j -= k;
/*  971 */           if (j > 0) {
/*  972 */             set(1, j);
/*      */           } else {
/*  974 */             set(1, 1 - j);
/*      */             
/*  976 */             set(0, 1);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  981 */       if (i >= 0) {
/*  982 */         set(2, i % 12);
/*      */       }
/*      */       else {
/*  985 */         i %= 12;
/*  986 */         if (i < 0) {
/*  987 */           i += 12;
/*      */         }
/*  989 */         set(2, 0 + i);
/*      */       }
/*  991 */       pinDayOfMonth();
/*  992 */     } else if (paramInt1 == 0) {
/*  993 */       i = internalGet(0) + paramInt2;
/*  994 */       if (i < 0) {
/*  995 */         i = 0;
/*      */       }
/*  997 */       if (i > 1) {
/*  998 */         i = 1;
/*      */       }
/* 1000 */       set(0, i);
/*      */     } else {
/* 1002 */       long l1 = paramInt2;
/* 1003 */       long l2 = 0L;
/* 1004 */       switch (paramInt1)
/*      */       {
/*      */ 
/*      */       case 10: 
/*      */       case 11: 
/* 1009 */         l1 *= 3600000L;
/* 1010 */         break;
/*      */       
/*      */       case 12: 
/* 1013 */         l1 *= 60000L;
/* 1014 */         break;
/*      */       
/*      */       case 13: 
/* 1017 */         l1 *= 1000L;
/* 1018 */         break;
/*      */       
/*      */ 
/*      */       case 14: 
/*      */         break;
/*      */       
/*      */ 
/*      */ 
/*      */       case 3: 
/*      */       case 4: 
/*      */       case 8: 
/* 1029 */         l1 *= 7L;
/* 1030 */         break;
/*      */       
/*      */ 
/*      */       case 5: 
/*      */       case 6: 
/*      */       case 7: 
/*      */         break;
/*      */       
/*      */ 
/*      */       case 9: 
/* 1040 */         l1 = paramInt2 / 2;
/* 1041 */         l2 = 12 * (paramInt2 % 2);
/*      */       }
/*      */       
/*      */       
/*      */ 
/*      */ 
/* 1047 */       if (paramInt1 >= 10) {
/* 1048 */         setTimeInMillis(this.time + l1);
/* 1049 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1058 */       long l3 = getCurrentFixedDate();
/* 1059 */       l2 += internalGet(11);
/* 1060 */       l2 *= 60L;
/* 1061 */       l2 += internalGet(12);
/* 1062 */       l2 *= 60L;
/* 1063 */       l2 += internalGet(13);
/* 1064 */       l2 *= 1000L;
/* 1065 */       l2 += internalGet(14);
/* 1066 */       if (l2 >= 86400000L) {
/* 1067 */         l3 += 1L;
/* 1068 */         l2 -= 86400000L;
/* 1069 */       } else if (l2 < 0L) {
/* 1070 */         l3 -= 1L;
/* 1071 */         l2 += 86400000L;
/*      */       }
/*      */       
/* 1074 */       l3 += l1;
/* 1075 */       int m = internalGet(15) + internalGet(16);
/* 1076 */       setTimeInMillis((l3 - 719163L) * 86400000L + l2 - m);
/* 1077 */       m -= internalGet(15) + internalGet(16);
/*      */       
/* 1079 */       if (m != 0) {
/* 1080 */         setTimeInMillis(this.time + m);
/* 1081 */         long l4 = getCurrentFixedDate();
/*      */         
/*      */ 
/* 1084 */         if (l4 != l3) {
/* 1085 */           setTimeInMillis(this.time - m);
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public void roll(int paramInt, boolean paramBoolean)
/*      */   {
/* 1111 */     roll(paramInt, paramBoolean ? 1 : -1);
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
/*      */   public void roll(int paramInt1, int paramInt2)
/*      */   {
/* 1162 */     if (paramInt2 == 0) {
/* 1163 */       return;
/*      */     }
/*      */     
/* 1166 */     if ((paramInt1 < 0) || (paramInt1 >= 15)) {
/* 1167 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/* 1171 */     complete();
/*      */     
/* 1173 */     int i = getMinimum(paramInt1);
/* 1174 */     int j = getMaximum(paramInt1);
/*      */     int k;
/* 1176 */     int i1; int i2; long l7; Object localObject1; Object localObject2; long l4; int i12; long l1; int i7; switch (paramInt1)
/*      */     {
/*      */     case 0: 
/*      */     case 1: 
/*      */     case 9: 
/*      */     case 12: 
/*      */     case 13: 
/*      */     case 14: 
/*      */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 10: 
/*      */     case 11: 
/* 1192 */       k = j + 1;
/* 1193 */       i1 = internalGet(paramInt1);
/* 1194 */       i2 = (i1 + paramInt2) % k;
/* 1195 */       if (i2 < 0) {
/* 1196 */         i2 += k;
/*      */       }
/* 1198 */       this.time += 3600000 * (i2 - i1);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1204 */       CalendarDate localCalendarDate = this.calsys.getCalendarDate(this.time, getZone());
/* 1205 */       if (internalGet(5) != localCalendarDate.getDayOfMonth()) {
/* 1206 */         localCalendarDate.setDate(internalGet(1), 
/* 1207 */           internalGet(2) + 1, 
/* 1208 */           internalGet(5));
/* 1209 */         if (paramInt1 == 10) {
/* 1210 */           assert (internalGet(9) == 1);
/* 1211 */           localCalendarDate.addHours(12);
/*      */         }
/* 1213 */         this.time = this.calsys.getTime(localCalendarDate);
/*      */       }
/* 1215 */       int i6 = localCalendarDate.getHours();
/* 1216 */       internalSet(paramInt1, i6 % k);
/* 1217 */       if (paramInt1 == 10) {
/* 1218 */         internalSet(11, i6);
/*      */       } else {
/* 1220 */         internalSet(9, i6 / 12);
/* 1221 */         internalSet(10, i6 % 12);
/*      */       }
/*      */       
/*      */ 
/* 1225 */       int i9 = localCalendarDate.getZoneOffset();
/* 1226 */       int i11 = localCalendarDate.getDaylightSaving();
/* 1227 */       internalSet(15, i9 - i11);
/* 1228 */       internalSet(16, i11);
/* 1229 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 2: 
/* 1238 */       if (!isCutoverYear(this.cdate.getNormalizedYear())) {
/* 1239 */         k = (internalGet(2) + paramInt2) % 12;
/* 1240 */         if (k < 0) {
/* 1241 */           k += 12;
/*      */         }
/* 1243 */         set(2, k);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1248 */         i1 = monthLength(k);
/* 1249 */         if (internalGet(5) > i1) {
/* 1250 */           set(5, i1);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1255 */         k = getActualMaximum(2) + 1;
/* 1256 */         i1 = (internalGet(2) + paramInt2) % k;
/* 1257 */         if (i1 < 0) {
/* 1258 */           i1 += k;
/*      */         }
/* 1260 */         set(2, i1);
/* 1261 */         i2 = getActualMaximum(5);
/* 1262 */         if (internalGet(5) > i2) {
/* 1263 */           set(5, i2);
/*      */         }
/*      */       }
/* 1266 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 3: 
/* 1271 */       k = this.cdate.getNormalizedYear();
/* 1272 */       j = getActualMaximum(3);
/* 1273 */       set(7, internalGet(7));
/* 1274 */       i1 = internalGet(3);
/* 1275 */       i2 = i1 + paramInt2;
/* 1276 */       if (!isCutoverYear(k)) {
/* 1277 */         int i4 = getWeekYear();
/* 1278 */         if (i4 == k)
/*      */         {
/*      */ 
/* 1281 */           if ((i2 > i) && (i2 < j)) {
/* 1282 */             set(3, i2);
/* 1283 */             return;
/*      */           }
/* 1285 */           l7 = getCurrentFixedDate();
/*      */           
/*      */ 
/* 1288 */           l8 = l7 - 7 * (i1 - i);
/* 1289 */           if (this.calsys.getYearFromFixedDate(l8) != k) {
/* 1290 */             i++;
/*      */           }
/*      */           
/*      */ 
/* 1294 */           l7 += 7 * (j - internalGet(3));
/* 1295 */           if (this.calsys.getYearFromFixedDate(l7) != k) {
/* 1296 */             j--;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1301 */         else if (i4 > k) {
/* 1302 */           if (paramInt2 < 0) {
/* 1303 */             paramInt2++;
/*      */           }
/* 1305 */           i1 = j;
/*      */         } else {
/* 1307 */           if (paramInt2 > 0) {
/* 1308 */             paramInt2 -= i1 - j;
/*      */           }
/* 1310 */           i1 = i;
/*      */         }
/*      */         
/* 1313 */         set(paramInt1, getRolledValue(i1, paramInt2, i, j));
/* 1314 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1318 */       long l6 = getCurrentFixedDate();
/*      */       
/* 1320 */       if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
/* 1321 */         localObject1 = getCutoverCalendarSystem();
/* 1322 */       } else if (k == this.gregorianCutoverYear) {
/* 1323 */         localObject1 = gcal;
/*      */       } else {
/* 1325 */         localObject1 = getJulianCalendarSystem();
/*      */       }
/* 1327 */       long l8 = l6 - 7 * (i1 - i);
/*      */       
/* 1329 */       if (((BaseCalendar)localObject1).getYearFromFixedDate(l8) != k) {
/* 1330 */         i++;
/*      */       }
/*      */       
/*      */ 
/* 1334 */       l6 += 7 * (j - i1);
/* 1335 */       localObject1 = l6 >= this.gregorianCutoverDate ? gcal : getJulianCalendarSystem();
/* 1336 */       if (((BaseCalendar)localObject1).getYearFromFixedDate(l6) != k) {
/* 1337 */         j--;
/*      */       }
/*      */       
/*      */ 
/* 1341 */       i2 = getRolledValue(i1, paramInt2, i, j) - 1;
/* 1342 */       localObject2 = getCalendarDate(l8 + i2 * 7);
/* 1343 */       set(2, ((BaseCalendar.Date)localObject2).getMonth() - 1);
/* 1344 */       set(5, ((BaseCalendar.Date)localObject2).getDayOfMonth());
/* 1345 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 4: 
/* 1350 */       boolean bool = isCutoverYear(this.cdate.getNormalizedYear());
/*      */       
/* 1352 */       i1 = internalGet(7) - getFirstDayOfWeek();
/* 1353 */       if (i1 < 0) {
/* 1354 */         i1 += 7;
/*      */       }
/*      */       
/* 1357 */       l4 = getCurrentFixedDate();
/*      */       
/*      */ 
/* 1360 */       if (bool) {
/* 1361 */         l7 = getFixedDateMonth1(this.cdate, l4);
/* 1362 */         i12 = actualMonthLength();
/*      */       } else {
/* 1364 */         l7 = l4 - internalGet(5) + 1L;
/* 1365 */         i12 = this.calsys.getMonthLength(this.cdate);
/*      */       }
/*      */       
/*      */ 
/* 1369 */       long l9 = BaseCalendar.getDayOfWeekDateOnOrBefore(l7 + 6L, 
/* 1370 */         getFirstDayOfWeek());
/*      */       
/*      */ 
/* 1373 */       if ((int)(l9 - l7) >= getMinimalDaysInFirstWeek()) {
/* 1374 */         l9 -= 7L;
/*      */       }
/* 1376 */       j = getActualMaximum(paramInt1);
/*      */       
/*      */ 
/* 1379 */       int i14 = getRolledValue(internalGet(paramInt1), paramInt2, 1, j) - 1;
/*      */       
/*      */ 
/* 1382 */       long l10 = l9 + i14 * 7 + i1;
/*      */       
/*      */ 
/*      */ 
/* 1386 */       if (l10 < l7) {
/* 1387 */         l10 = l7;
/* 1388 */       } else if (l10 >= l7 + i12) {
/* 1389 */         l10 = l7 + i12 - 1L;
/*      */       }
/*      */       int i15;
/* 1392 */       if (bool)
/*      */       {
/*      */ 
/* 1395 */         BaseCalendar.Date localDate3 = getCalendarDate(l10);
/* 1396 */         i15 = localDate3.getDayOfMonth();
/*      */       } else {
/* 1398 */         i15 = (int)(l10 - l7) + 1;
/*      */       }
/* 1400 */       set(5, i15);
/* 1401 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 5: 
/* 1406 */       if (!isCutoverYear(this.cdate.getNormalizedYear())) {
/* 1407 */         j = this.calsys.getMonthLength(this.cdate);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1412 */         l1 = getCurrentFixedDate();
/* 1413 */         l4 = getFixedDateMonth1(this.cdate, l1);
/*      */         
/*      */ 
/*      */ 
/* 1417 */         i7 = getRolledValue((int)(l1 - l4), paramInt2, 0, actualMonthLength() - 1);
/* 1418 */         localObject1 = getCalendarDate(l4 + i7);
/* 1419 */         assert (((BaseCalendar.Date)localObject1).getMonth() - 1 == internalGet(2));
/* 1420 */         set(5, ((BaseCalendar.Date)localObject1).getDayOfMonth()); return;
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     case 6: 
/* 1426 */       j = getActualMaximum(paramInt1);
/* 1427 */       if (isCutoverYear(this.cdate.getNormalizedYear()))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1432 */         l1 = getCurrentFixedDate();
/* 1433 */         l4 = l1 - internalGet(6) + 1L;
/* 1434 */         i7 = getRolledValue((int)(l1 - l4) + 1, paramInt2, i, j);
/* 1435 */         localObject1 = getCalendarDate(l4 + i7 - 1L);
/* 1436 */         set(2, ((BaseCalendar.Date)localObject1).getMonth() - 1);
/* 1437 */         set(5, ((BaseCalendar.Date)localObject1).getDayOfMonth()); return;
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     case 7: 
/* 1443 */       if (!isCutoverYear(this.cdate.getNormalizedYear()))
/*      */       {
/*      */ 
/* 1446 */         int m = internalGet(3);
/* 1447 */         if ((m > 1) && (m < 52)) {
/* 1448 */           set(3, m);
/* 1449 */           j = 7;
/* 1450 */           break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1458 */       paramInt2 %= 7;
/* 1459 */       if (paramInt2 == 0) {
/* 1460 */         return;
/*      */       }
/* 1462 */       long l2 = getCurrentFixedDate();
/* 1463 */       l4 = BaseCalendar.getDayOfWeekDateOnOrBefore(l2, getFirstDayOfWeek());
/* 1464 */       l2 += paramInt2;
/* 1465 */       if (l2 < l4) {
/* 1466 */         l2 += 7L;
/* 1467 */       } else if (l2 >= l4 + 7L) {
/* 1468 */         l2 -= 7L;
/*      */       }
/* 1470 */       BaseCalendar.Date localDate1 = getCalendarDate(l2);
/* 1471 */       set(0, localDate1.getNormalizedYear() <= 0 ? 0 : 1);
/* 1472 */       set(localDate1.getYear(), localDate1.getMonth() - 1, localDate1.getDayOfMonth());
/* 1473 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 8: 
/* 1478 */       i = 1;
/* 1479 */       if (!isCutoverYear(this.cdate.getNormalizedYear())) {
/* 1480 */         int n = internalGet(5);
/* 1481 */         i1 = this.calsys.getMonthLength(this.cdate);
/* 1482 */         int i3 = i1 % 7;
/* 1483 */         j = i1 / 7;
/* 1484 */         int i5 = (n - 1) % 7;
/* 1485 */         if (i5 < i3) {
/* 1486 */           j++;
/*      */         }
/* 1488 */         set(7, internalGet(7));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1493 */         long l3 = getCurrentFixedDate();
/* 1494 */         long l5 = getFixedDateMonth1(this.cdate, l3);
/* 1495 */         int i8 = actualMonthLength();
/* 1496 */         int i10 = i8 % 7;
/* 1497 */         j = i8 / 7;
/* 1498 */         i12 = (int)(l3 - l5) % 7;
/* 1499 */         if (i12 < i10) {
/* 1500 */           j++;
/*      */         }
/* 1502 */         int i13 = getRolledValue(internalGet(paramInt1), paramInt2, i, j) - 1;
/* 1503 */         l3 = l5 + i13 * 7 + i12;
/* 1504 */         localObject2 = l3 >= this.gregorianCutoverDate ? gcal : getJulianCalendarSystem();
/* 1505 */         BaseCalendar.Date localDate2 = (BaseCalendar.Date)((BaseCalendar)localObject2).newCalendarDate(TimeZone.NO_TIMEZONE);
/* 1506 */         ((BaseCalendar)localObject2).getCalendarDateFromFixedDate(localDate2, l3);
/* 1507 */         set(5, localDate2.getDayOfMonth()); return;
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/* 1512 */     set(paramInt1, getRolledValue(internalGet(paramInt1), paramInt2, i, j));
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
/*      */   public int getMinimum(int paramInt)
/*      */   {
/* 1536 */     return MIN_VALUES[paramInt];
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
/*      */   public int getMaximum(int paramInt)
/*      */   {
/* 1560 */     switch (paramInt)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */     case 1: 
/*      */     case 2: 
/*      */     case 3: 
/*      */     case 4: 
/*      */     case 5: 
/*      */     case 6: 
/*      */     case 8: 
/* 1572 */       if (this.gregorianCutoverYear <= 200)
/*      */       {
/*      */ 
/*      */ 
/* 1576 */         GregorianCalendar localGregorianCalendar = (GregorianCalendar)clone();
/* 1577 */         localGregorianCalendar.setLenient(true);
/* 1578 */         localGregorianCalendar.setTimeInMillis(this.gregorianCutover);
/* 1579 */         int i = localGregorianCalendar.getActualMaximum(paramInt);
/* 1580 */         localGregorianCalendar.setTimeInMillis(this.gregorianCutover - 1L);
/* 1581 */         int j = localGregorianCalendar.getActualMaximum(paramInt);
/* 1582 */         return Math.max(MAX_VALUES[paramInt], Math.max(i, j));
/*      */       }
/*      */       break; }
/* 1585 */     return MAX_VALUES[paramInt];
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
/*      */   public int getGreatestMinimum(int paramInt)
/*      */   {
/* 1609 */     if (paramInt == 5) {
/* 1610 */       BaseCalendar.Date localDate = getGregorianCutoverDate();
/* 1611 */       long l = getFixedDateMonth1(localDate, this.gregorianCutoverDate);
/* 1612 */       localDate = getCalendarDate(l);
/* 1613 */       return Math.max(MIN_VALUES[paramInt], localDate.getDayOfMonth());
/*      */     }
/* 1615 */     return MIN_VALUES[paramInt];
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
/*      */   public int getLeastMaximum(int paramInt)
/*      */   {
/* 1639 */     switch (paramInt)
/*      */     {
/*      */     case 1: 
/*      */     case 2: 
/*      */     case 3: 
/*      */     case 4: 
/*      */     case 5: 
/*      */     case 6: 
/*      */     case 8: 
/* 1648 */       GregorianCalendar localGregorianCalendar = (GregorianCalendar)clone();
/* 1649 */       localGregorianCalendar.setLenient(true);
/* 1650 */       localGregorianCalendar.setTimeInMillis(this.gregorianCutover);
/* 1651 */       int i = localGregorianCalendar.getActualMaximum(paramInt);
/* 1652 */       localGregorianCalendar.setTimeInMillis(this.gregorianCutover - 1L);
/* 1653 */       int j = localGregorianCalendar.getActualMaximum(paramInt);
/* 1654 */       return Math.min(LEAST_MAX_VALUES[paramInt], Math.min(i, j));
/*      */     }
/*      */     
/* 1657 */     return LEAST_MAX_VALUES[paramInt];
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
/*      */   public int getActualMinimum(int paramInt)
/*      */   {
/* 1689 */     if (paramInt == 5) {
/* 1690 */       GregorianCalendar localGregorianCalendar = getNormalizedCalendar();
/* 1691 */       int i = localGregorianCalendar.cdate.getNormalizedYear();
/* 1692 */       if ((i == this.gregorianCutoverYear) || (i == this.gregorianCutoverYearJulian)) {
/* 1693 */         long l = getFixedDateMonth1(localGregorianCalendar.cdate, localGregorianCalendar.calsys.getFixedDate(localGregorianCalendar.cdate));
/* 1694 */         BaseCalendar.Date localDate = getCalendarDate(l);
/* 1695 */         return localDate.getDayOfMonth();
/*      */       }
/*      */     }
/* 1698 */     return getMinimum(paramInt);
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
/*      */   public int getActualMaximum(int paramInt)
/*      */   {
/* 1736 */     if ((0x1FE81 & 1 << paramInt) != 0) {
/* 1737 */       return getMaximum(paramInt);
/*      */     }
/*      */     
/* 1740 */     GregorianCalendar localGregorianCalendar = getNormalizedCalendar();
/* 1741 */     BaseCalendar.Date localDate1 = localGregorianCalendar.cdate;
/* 1742 */     BaseCalendar localBaseCalendar1 = localGregorianCalendar.calsys;
/* 1743 */     int i = localDate1.getNormalizedYear();
/*      */     
/* 1745 */     int j = -1;
/* 1746 */     long l1; int n; int i2; int m; switch (paramInt)
/*      */     {
/*      */     case 2: 
/* 1749 */       if (!localGregorianCalendar.isCutoverYear(i)) {
/* 1750 */         j = 11;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */         do
/*      */         {
/* 1757 */           l1 = gcal.getFixedDate(++i, 1, 1, null);
/* 1758 */         } while (l1 < this.gregorianCutoverDate);
/* 1759 */         BaseCalendar.Date localDate2 = (BaseCalendar.Date)localDate1.clone();
/* 1760 */         localBaseCalendar1.getCalendarDateFromFixedDate(localDate2, l1 - 1L);
/* 1761 */         j = localDate2.getMonth() - 1;
/*      */       }
/* 1763 */       break;
/*      */     
/*      */ 
/*      */     case 5: 
/* 1767 */       j = localBaseCalendar1.getMonthLength(localDate1);
/* 1768 */       if ((localGregorianCalendar.isCutoverYear(i)) && (localDate1.getDayOfMonth() != j))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1773 */         l1 = localGregorianCalendar.getCurrentFixedDate();
/* 1774 */         if (l1 < this.gregorianCutoverDate)
/*      */         {
/*      */ 
/* 1777 */           int i1 = localGregorianCalendar.actualMonthLength();
/* 1778 */           long l5 = localGregorianCalendar.getFixedDateMonth1(localGregorianCalendar.cdate, l1) + i1 - 1L;
/*      */           
/* 1780 */           BaseCalendar.Date localDate4 = localGregorianCalendar.getCalendarDate(l5);
/* 1781 */           j = localDate4.getDayOfMonth();
/*      */         } }
/* 1783 */       break;
/*      */     
/*      */ 
/*      */     case 6: 
/* 1787 */       if (!localGregorianCalendar.isCutoverYear(i)) {
/* 1788 */         j = localBaseCalendar1.getYearLength(localDate1);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 1794 */         if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
/* 1795 */           BaseCalendar localBaseCalendar2 = localGregorianCalendar.getCutoverCalendarSystem();
/* 1796 */           l1 = localBaseCalendar2.getFixedDate(i, 1, 1, null);
/* 1797 */         } else if (i == this.gregorianCutoverYearJulian) {
/* 1798 */           l1 = localBaseCalendar1.getFixedDate(i, 1, 1, null);
/*      */         } else {
/* 1800 */           l1 = this.gregorianCutoverDate;
/*      */         }
/*      */         
/* 1803 */         long l3 = gcal.getFixedDate(++i, 1, 1, null);
/* 1804 */         if (l3 < this.gregorianCutoverDate) {
/* 1805 */           l3 = this.gregorianCutoverDate;
/*      */         }
/* 1807 */         assert (l1 <= localBaseCalendar1.getFixedDate(localDate1.getNormalizedYear(), localDate1.getMonth(), localDate1
/* 1808 */           .getDayOfMonth(), localDate1));
/* 1809 */         assert (l3 >= localBaseCalendar1.getFixedDate(localDate1.getNormalizedYear(), localDate1.getMonth(), localDate1
/* 1810 */           .getDayOfMonth(), localDate1));
/* 1811 */         j = (int)(l3 - l1);
/*      */       }
/* 1813 */       break;
/*      */     
/*      */ 
/*      */     case 3: 
/* 1817 */       if (!localGregorianCalendar.isCutoverYear(i))
/*      */       {
/* 1819 */         CalendarDate localCalendarDate1 = localBaseCalendar1.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 1820 */         localCalendarDate1.setDate(localDate1.getYear(), 1, 1);
/* 1821 */         n = localBaseCalendar1.getDayOfWeek(localCalendarDate1);
/*      */         
/* 1823 */         n -= getFirstDayOfWeek();
/* 1824 */         if (n < 0) {
/* 1825 */           n += 7;
/*      */         }
/* 1827 */         j = 52;
/* 1828 */         i2 = n + getMinimalDaysInFirstWeek() - 1;
/* 1829 */         if ((i2 == 6) || (
/* 1830 */           (localDate1.isLeapYear()) && ((i2 == 5) || (i2 == 12)))) {
/* 1831 */           j++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1836 */         if (localGregorianCalendar == this) {
/* 1837 */           localGregorianCalendar = (GregorianCalendar)localGregorianCalendar.clone();
/*      */         }
/* 1839 */         int k = getActualMaximum(6);
/* 1840 */         localGregorianCalendar.set(6, k);
/* 1841 */         j = localGregorianCalendar.get(3);
/* 1842 */         if (internalGet(1) != localGregorianCalendar.getWeekYear()) {
/* 1843 */           localGregorianCalendar.set(6, k - 7);
/* 1844 */           j = localGregorianCalendar.get(3);
/*      */         }
/*      */       }
/* 1847 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/* 1851 */       if (!localGregorianCalendar.isCutoverYear(i)) {
/* 1852 */         CalendarDate localCalendarDate2 = localBaseCalendar1.newCalendarDate(null);
/* 1853 */         localCalendarDate2.setDate(localDate1.getYear(), localDate1.getMonth(), 1);
/* 1854 */         n = localBaseCalendar1.getDayOfWeek(localCalendarDate2);
/* 1855 */         i2 = localBaseCalendar1.getMonthLength(localCalendarDate2);
/* 1856 */         n -= getFirstDayOfWeek();
/* 1857 */         if (n < 0) {
/* 1858 */           n += 7;
/*      */         }
/* 1860 */         int i3 = 7 - n;
/* 1861 */         j = 3;
/* 1862 */         if (i3 >= getMinimalDaysInFirstWeek()) {
/* 1863 */           j++;
/*      */         }
/* 1865 */         i2 -= i3 + 21;
/* 1866 */         if (i2 > 0) {
/* 1867 */           j++;
/* 1868 */           if (i2 > 7) {
/* 1869 */             j++;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1876 */         if (localGregorianCalendar == this) {
/* 1877 */           localGregorianCalendar = (GregorianCalendar)localGregorianCalendar.clone();
/*      */         }
/* 1879 */         m = localGregorianCalendar.internalGet(1);
/* 1880 */         n = localGregorianCalendar.internalGet(2);
/*      */         do {
/* 1882 */           j = localGregorianCalendar.get(4);
/* 1883 */           localGregorianCalendar.add(4, 1);
/* 1884 */         } while ((localGregorianCalendar.get(1) == m) && (localGregorianCalendar.get(2) == n));
/*      */       }
/* 1886 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 8: 
/* 1892 */       i2 = localDate1.getDayOfWeek();
/* 1893 */       if (!localGregorianCalendar.isCutoverYear(i)) {
/* 1894 */         BaseCalendar.Date localDate3 = (BaseCalendar.Date)localDate1.clone();
/* 1895 */         m = localBaseCalendar1.getMonthLength(localDate3);
/* 1896 */         localDate3.setDayOfMonth(1);
/* 1897 */         localBaseCalendar1.normalize(localDate3);
/* 1898 */         n = localDate3.getDayOfWeek();
/*      */       }
/*      */       else {
/* 1901 */         if (localGregorianCalendar == this) {
/* 1902 */           localGregorianCalendar = (GregorianCalendar)clone();
/*      */         }
/* 1904 */         m = localGregorianCalendar.actualMonthLength();
/* 1905 */         localGregorianCalendar.set(5, localGregorianCalendar.getActualMinimum(5));
/* 1906 */         n = localGregorianCalendar.get(7);
/*      */       }
/* 1908 */       int i4 = i2 - n;
/* 1909 */       if (i4 < 0) {
/* 1910 */         i4 += 7;
/*      */       }
/* 1912 */       m -= i4;
/* 1913 */       j = (m + 6) / 7;
/*      */       
/* 1915 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 1: 
/* 1938 */       if (localGregorianCalendar == this) {
/* 1939 */         localGregorianCalendar = (GregorianCalendar)clone();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1946 */       long l2 = localGregorianCalendar.getYearOffsetInMillis();
/*      */       
/* 1948 */       if (localGregorianCalendar.internalGetEra() == 1) {
/* 1949 */         localGregorianCalendar.setTimeInMillis(Long.MAX_VALUE);
/* 1950 */         j = localGregorianCalendar.get(1);
/* 1951 */         long l4 = localGregorianCalendar.getYearOffsetInMillis();
/* 1952 */         if (l2 > l4) {
/* 1953 */           j--;
/*      */         }
/*      */       }
/*      */       else {
/* 1957 */         BaseCalendar localBaseCalendar3 = localGregorianCalendar.getTimeInMillis() >= this.gregorianCutover ? gcal : getJulianCalendarSystem();
/* 1958 */         CalendarDate localCalendarDate3 = localBaseCalendar3.getCalendarDate(Long.MIN_VALUE, getZone());
/* 1959 */         long l6 = (localBaseCalendar1.getDayOfYear(localCalendarDate3) - 1L) * 24L + localCalendarDate3.getHours();
/* 1960 */         l6 *= 60L;
/* 1961 */         l6 += localCalendarDate3.getMinutes();
/* 1962 */         l6 *= 60L;
/* 1963 */         l6 += localCalendarDate3.getSeconds();
/* 1964 */         l6 *= 1000L;
/* 1965 */         l6 += localCalendarDate3.getMillis();
/* 1966 */         j = localCalendarDate3.getYear();
/* 1967 */         if (j <= 0) {
/* 1968 */           assert (localBaseCalendar3 == gcal);
/* 1969 */           j = 1 - j;
/*      */         }
/* 1971 */         if (l2 < l6) {
/* 1972 */           j--;
/*      */         }
/*      */       }
/*      */       
/* 1976 */       break;
/*      */     case 7: 
/*      */     default: 
/* 1979 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/* 1981 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long getYearOffsetInMillis()
/*      */   {
/* 1989 */     long l = (internalGet(6) - 1) * 24;
/* 1990 */     l += internalGet(11);
/* 1991 */     l *= 60L;
/* 1992 */     l += internalGet(12);
/* 1993 */     l *= 60L;
/* 1994 */     l += internalGet(13);
/* 1995 */     l *= 1000L;
/*      */     
/* 1997 */     return l + internalGet(14) - (internalGet(15) + internalGet(16));
/*      */   }
/*      */   
/*      */ 
/*      */   public Object clone()
/*      */   {
/* 2003 */     GregorianCalendar localGregorianCalendar = (GregorianCalendar)super.clone();
/*      */     
/* 2005 */     localGregorianCalendar.gdate = ((BaseCalendar.Date)this.gdate.clone());
/* 2006 */     if (this.cdate != null) {
/* 2007 */       if (this.cdate != this.gdate) {
/* 2008 */         localGregorianCalendar.cdate = ((BaseCalendar.Date)this.cdate.clone());
/*      */       } else {
/* 2010 */         localGregorianCalendar.cdate = localGregorianCalendar.gdate;
/*      */       }
/*      */     }
/* 2013 */     localGregorianCalendar.originalFields = null;
/* 2014 */     localGregorianCalendar.zoneOffsets = null;
/* 2015 */     return localGregorianCalendar;
/*      */   }
/*      */   
/*      */   public TimeZone getTimeZone()
/*      */   {
/* 2020 */     TimeZone localTimeZone = super.getTimeZone();
/*      */     
/* 2022 */     this.gdate.setZone(localTimeZone);
/* 2023 */     if ((this.cdate != null) && (this.cdate != this.gdate)) {
/* 2024 */       this.cdate.setZone(localTimeZone);
/*      */     }
/* 2026 */     return localTimeZone;
/*      */   }
/*      */   
/*      */   public void setTimeZone(TimeZone paramTimeZone)
/*      */   {
/* 2031 */     super.setTimeZone(paramTimeZone);
/*      */     
/* 2033 */     this.gdate.setZone(paramTimeZone);
/* 2034 */     if ((this.cdate != null) && (this.cdate != this.gdate)) {
/* 2035 */       this.cdate.setZone(paramTimeZone);
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
/*      */   public final boolean isWeekDateSupported()
/*      */   {
/* 2051 */     return true;
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
/*      */   public int getWeekYear()
/*      */   {
/* 2078 */     int i = get(1);
/* 2079 */     if (internalGetEra() == 0) {
/* 2080 */       i = 1 - i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2085 */     if (i > this.gregorianCutoverYear + 1) {
/* 2086 */       j = internalGet(3);
/* 2087 */       if (internalGet(2) == 0) {
/* 2088 */         if (j >= 52) {
/* 2089 */           i--;
/*      */         }
/*      */       }
/* 2092 */       else if (j == 1) {
/* 2093 */         i++;
/*      */       }
/*      */       
/* 2096 */       return i;
/*      */     }
/*      */     
/*      */ 
/* 2100 */     int j = internalGet(6);
/* 2101 */     int k = getActualMaximum(6);
/* 2102 */     int m = getMinimalDaysInFirstWeek();
/*      */     
/*      */ 
/*      */ 
/* 2106 */     if ((j > m) && (j < k - 6)) {
/* 2107 */       return i;
/*      */     }
/*      */     
/*      */ 
/* 2111 */     GregorianCalendar localGregorianCalendar = (GregorianCalendar)clone();
/* 2112 */     localGregorianCalendar.setLenient(true);
/*      */     
/*      */ 
/* 2115 */     localGregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
/*      */     
/* 2117 */     localGregorianCalendar.set(6, 1);
/* 2118 */     localGregorianCalendar.complete();
/*      */     
/*      */ 
/* 2121 */     int n = getFirstDayOfWeek() - localGregorianCalendar.get(7);
/* 2122 */     if (n != 0) {
/* 2123 */       if (n < 0) {
/* 2124 */         n += 7;
/*      */       }
/* 2126 */       localGregorianCalendar.add(6, n);
/*      */     }
/* 2128 */     int i1 = localGregorianCalendar.get(6);
/* 2129 */     if (j < i1) {
/* 2130 */       if (i1 <= m) {
/* 2131 */         i--;
/*      */       }
/*      */     } else {
/* 2134 */       localGregorianCalendar.set(1, i + 1);
/* 2135 */       localGregorianCalendar.set(6, 1);
/* 2136 */       localGregorianCalendar.complete();
/* 2137 */       int i2 = getFirstDayOfWeek() - localGregorianCalendar.get(7);
/* 2138 */       if (i2 != 0) {
/* 2139 */         if (i2 < 0) {
/* 2140 */           i2 += 7;
/*      */         }
/* 2142 */         localGregorianCalendar.add(6, i2);
/*      */       }
/* 2144 */       i1 = localGregorianCalendar.get(6) - 1;
/* 2145 */       if (i1 == 0) {
/* 2146 */         i1 = 7;
/*      */       }
/* 2148 */       if (i1 >= m) {
/* 2149 */         int i3 = k - j + 1;
/* 2150 */         if (i3 <= 7 - i1) {
/* 2151 */           i++;
/*      */         }
/*      */       }
/*      */     }
/* 2155 */     return i;
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
/*      */   public void setWeekDate(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2199 */     if ((paramInt3 < 1) || (paramInt3 > 7)) {
/* 2200 */       throw new IllegalArgumentException("invalid dayOfWeek: " + paramInt3);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2205 */     GregorianCalendar localGregorianCalendar = (GregorianCalendar)clone();
/* 2206 */     localGregorianCalendar.setLenient(true);
/* 2207 */     int i = localGregorianCalendar.get(0);
/* 2208 */     localGregorianCalendar.clear();
/* 2209 */     localGregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 2210 */     localGregorianCalendar.set(0, i);
/* 2211 */     localGregorianCalendar.set(1, paramInt1);
/* 2212 */     localGregorianCalendar.set(3, 1);
/* 2213 */     localGregorianCalendar.set(7, getFirstDayOfWeek());
/* 2214 */     int j = paramInt3 - getFirstDayOfWeek();
/* 2215 */     if (j < 0) {
/* 2216 */       j += 7;
/*      */     }
/* 2218 */     j += 7 * (paramInt2 - 1);
/* 2219 */     if (j != 0) {
/* 2220 */       localGregorianCalendar.add(6, j);
/*      */     } else {
/* 2222 */       localGregorianCalendar.complete();
/*      */     }
/*      */     
/* 2225 */     if ((!isLenient()) && (
/* 2226 */       (localGregorianCalendar.getWeekYear() != paramInt1) || 
/* 2227 */       (localGregorianCalendar.internalGet(3) != paramInt2) || 
/* 2228 */       (localGregorianCalendar.internalGet(7) != paramInt3))) {
/* 2229 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 2232 */     set(0, localGregorianCalendar.internalGet(0));
/* 2233 */     set(1, localGregorianCalendar.internalGet(1));
/* 2234 */     set(2, localGregorianCalendar.internalGet(2));
/* 2235 */     set(5, localGregorianCalendar.internalGet(5));
/*      */     
/*      */ 
/*      */ 
/* 2239 */     internalSet(3, paramInt2);
/* 2240 */     complete();
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
/*      */   public int getWeeksInWeekYear()
/*      */   {
/* 2262 */     GregorianCalendar localGregorianCalendar = getNormalizedCalendar();
/* 2263 */     int i = localGregorianCalendar.getWeekYear();
/* 2264 */     if (i == localGregorianCalendar.internalGet(1)) {
/* 2265 */       return localGregorianCalendar.getActualMaximum(3);
/*      */     }
/*      */     
/*      */ 
/* 2269 */     if (localGregorianCalendar == this) {
/* 2270 */       localGregorianCalendar = (GregorianCalendar)localGregorianCalendar.clone();
/*      */     }
/* 2272 */     localGregorianCalendar.setWeekDate(i, 2, internalGet(7));
/* 2273 */     return localGregorianCalendar.getActualMaximum(3);
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
/* 2285 */   private transient long cachedFixedDate = Long.MIN_VALUE;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void computeFields()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2299 */     if (isPartiallyNormalized())
/*      */     {
/* 2301 */       i = getSetStateFields();
/* 2302 */       int j = (i ^ 0xFFFFFFFF) & 0x1FFFF;
/*      */       
/*      */ 
/* 2305 */       if ((j != 0) || (this.calsys == null)) {
/* 2306 */         i |= computeFields(j, i & 0x18000);
/*      */         
/* 2308 */         assert (i == 131071);
/*      */       }
/*      */     } else {
/* 2311 */       i = 131071;
/* 2312 */       computeFields(i, 0);
/*      */     }
/*      */     
/* 2315 */     setFieldsComputed(i);
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
/*      */   private int computeFields(int paramInt1, int paramInt2)
/*      */   {
/* 2333 */     int i = 0;
/* 2334 */     TimeZone localTimeZone = getZone();
/* 2335 */     if (this.zoneOffsets == null) {
/* 2336 */       this.zoneOffsets = new int[2];
/*      */     }
/* 2338 */     if (paramInt2 != 98304) {
/* 2339 */       if ((localTimeZone instanceof ZoneInfo)) {
/* 2340 */         i = ((ZoneInfo)localTimeZone).getOffsets(this.time, this.zoneOffsets);
/*      */       } else {
/* 2342 */         i = localTimeZone.getOffset(this.time);
/* 2343 */         this.zoneOffsets[0] = localTimeZone.getRawOffset();
/* 2344 */         this.zoneOffsets[1] = (i - this.zoneOffsets[0]);
/*      */       }
/*      */     }
/* 2347 */     if (paramInt2 != 0) {
/* 2348 */       if (isFieldSet(paramInt2, 15)) {
/* 2349 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 2351 */       if (isFieldSet(paramInt2, 16)) {
/* 2352 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/* 2354 */       i = this.zoneOffsets[0] + this.zoneOffsets[1];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2360 */     long l1 = i / 86400000L;
/* 2361 */     int j = i % 86400000;
/* 2362 */     l1 += this.time / 86400000L;
/* 2363 */     j += (int)(this.time % 86400000L);
/* 2364 */     if (j >= 86400000L) {
/* 2365 */       j = (int)(j - 86400000L);
/* 2366 */       l1 += 1L;
/*      */     } else {
/* 2368 */       while (j < 0) {
/* 2369 */         j = (int)(j + 86400000L);
/* 2370 */         l1 -= 1L;
/*      */       }
/*      */     }
/* 2373 */     l1 += 719163L;
/*      */     
/* 2375 */     int k = 1;
/*      */     int m;
/* 2377 */     if (l1 >= this.gregorianCutoverDate)
/*      */     {
/* 2379 */       assert ((this.cachedFixedDate == Long.MIN_VALUE) || (this.gdate.isNormalized())) : "cache control: not normalized";
/*      */       
/* 2381 */       assert ((this.cachedFixedDate == Long.MIN_VALUE) || 
/* 2382 */         (gcal.getFixedDate(this.gdate.getNormalizedYear(), this.gdate
/* 2383 */         .getMonth(), this.gdate
/* 2384 */         .getDayOfMonth(), this.gdate) == this.cachedFixedDate)) : 
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2389 */         ("cache control: inconsictency, cachedFixedDate=" + this.cachedFixedDate + ", computed=" + gcal.getFixedDate(this.gdate.getNormalizedYear(), this.gdate
/* 2390 */         .getMonth(), this.gdate
/* 2391 */         .getDayOfMonth(), this.gdate) + ", date=" + this.gdate);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2396 */       if (l1 != this.cachedFixedDate) {
/* 2397 */         gcal.getCalendarDateFromFixedDate(this.gdate, l1);
/* 2398 */         this.cachedFixedDate = l1;
/*      */       }
/*      */       
/* 2401 */       m = this.gdate.getYear();
/* 2402 */       if (m <= 0) {
/* 2403 */         m = 1 - m;
/* 2404 */         k = 0;
/*      */       }
/* 2406 */       this.calsys = gcal;
/* 2407 */       this.cdate = this.gdate;
/* 2408 */       if ((!$assertionsDisabled) && (this.cdate.getDayOfWeek() <= 0)) throw new AssertionError("dow=" + this.cdate.getDayOfWeek() + ", date=" + this.cdate);
/*      */     }
/*      */     else {
/* 2411 */       this.calsys = getJulianCalendarSystem();
/* 2412 */       this.cdate = jcal.newCalendarDate(getZone());
/* 2413 */       jcal.getCalendarDateFromFixedDate(this.cdate, l1);
/* 2414 */       Era localEra = this.cdate.getEra();
/* 2415 */       if (localEra == jeras[0]) {
/* 2416 */         k = 0;
/*      */       }
/* 2418 */       m = this.cdate.getYear();
/*      */     }
/*      */     
/*      */ 
/* 2422 */     internalSet(0, k);
/* 2423 */     internalSet(1, m);
/* 2424 */     int n = paramInt1 | 0x3;
/*      */     
/* 2426 */     int i1 = this.cdate.getMonth() - 1;
/* 2427 */     int i2 = this.cdate.getDayOfMonth();
/*      */     
/*      */ 
/* 2430 */     if ((paramInt1 & 0xA4) != 0)
/*      */     {
/* 2432 */       internalSet(2, i1);
/* 2433 */       internalSet(5, i2);
/* 2434 */       internalSet(7, this.cdate.getDayOfWeek());
/* 2435 */       n |= 0xA4;
/*      */     }
/*      */     int i3;
/* 2438 */     if ((paramInt1 & 0x7E00) != 0)
/*      */     {
/* 2440 */       if (j != 0) {
/* 2441 */         i3 = j / 3600000;
/* 2442 */         internalSet(11, i3);
/* 2443 */         internalSet(9, i3 / 12);
/* 2444 */         internalSet(10, i3 % 12);
/* 2445 */         int i4 = j % 3600000;
/* 2446 */         internalSet(12, i4 / 60000);
/* 2447 */         i4 %= 60000;
/* 2448 */         internalSet(13, i4 / 1000);
/* 2449 */         internalSet(14, i4 % 1000);
/*      */       } else {
/* 2451 */         internalSet(11, 0);
/* 2452 */         internalSet(9, 0);
/* 2453 */         internalSet(10, 0);
/* 2454 */         internalSet(12, 0);
/* 2455 */         internalSet(13, 0);
/* 2456 */         internalSet(14, 0);
/*      */       }
/* 2458 */       n |= 0x7E00;
/*      */     }
/*      */     
/*      */ 
/* 2462 */     if ((paramInt1 & 0x18000) != 0) {
/* 2463 */       internalSet(15, this.zoneOffsets[0]);
/* 2464 */       internalSet(16, this.zoneOffsets[1]);
/* 2465 */       n |= 0x18000;
/*      */     }
/*      */     
/* 2468 */     if ((paramInt1 & 0x158) != 0) {
/* 2469 */       i3 = this.cdate.getNormalizedYear();
/* 2470 */       long l2 = this.calsys.getFixedDate(i3, 1, 1, this.cdate);
/* 2471 */       int i5 = (int)(l1 - l2) + 1;
/* 2472 */       long l3 = l1 - i2 + 1L;
/* 2473 */       int i6 = 0;
/* 2474 */       int i7 = this.calsys == gcal ? this.gregorianCutoverYear : this.gregorianCutoverYearJulian;
/* 2475 */       int i8 = i2 - 1;
/*      */       
/*      */ 
/* 2478 */       if (i3 == i7)
/*      */       {
/* 2480 */         if (this.gregorianCutoverYearJulian <= this.gregorianCutoverYear)
/*      */         {
/*      */ 
/*      */ 
/* 2484 */           l2 = getFixedDateJan1(this.cdate, l1);
/* 2485 */           if (l1 >= this.gregorianCutoverDate) {
/* 2486 */             l3 = getFixedDateMonth1(this.cdate, l1);
/*      */           }
/*      */         }
/* 2489 */         i9 = (int)(l1 - l2) + 1;
/* 2490 */         i6 = i5 - i9;
/* 2491 */         i5 = i9;
/* 2492 */         i8 = (int)(l1 - l3);
/*      */       }
/* 2494 */       internalSet(6, i5);
/* 2495 */       internalSet(8, i8 / 7 + 1);
/*      */       
/* 2497 */       int i9 = getWeekNumber(l2, l1);
/*      */       
/*      */       long l4;
/*      */       long l5;
/* 2501 */       if (i9 == 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2509 */         l4 = l2 - 1L;
/* 2510 */         l5 = l2 - 365L;
/* 2511 */         if (i3 > i7 + 1) {
/* 2512 */           if (CalendarUtils.isGregorianLeapYear(i3 - 1)) {
/* 2513 */             l5 -= 1L;
/*      */           }
/* 2515 */         } else if (i3 <= this.gregorianCutoverYearJulian) {
/* 2516 */           if (CalendarUtils.isJulianLeapYear(i3 - 1)) {
/* 2517 */             l5 -= 1L;
/*      */           }
/*      */         } else {
/* 2520 */           Object localObject2 = this.calsys;
/*      */           
/* 2522 */           int i12 = getCalendarDate(l4).getNormalizedYear();
/* 2523 */           if (i12 == this.gregorianCutoverYear) {
/* 2524 */             localObject2 = getCutoverCalendarSystem();
/* 2525 */             if (localObject2 == jcal) {
/* 2526 */               l5 = ((BaseCalendar)localObject2).getFixedDate(i12, 1, 1, null);
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 2531 */               l5 = this.gregorianCutoverDate;
/* 2532 */               localObject2 = gcal;
/*      */             }
/* 2534 */           } else if (i12 <= this.gregorianCutoverYearJulian) {
/* 2535 */             localObject2 = getJulianCalendarSystem();
/* 2536 */             l5 = ((BaseCalendar)localObject2).getFixedDate(i12, 1, 1, null);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2542 */         i9 = getWeekNumber(l5, l4);
/*      */       }
/* 2544 */       else if ((i3 > this.gregorianCutoverYear) || (i3 < this.gregorianCutoverYearJulian - 1))
/*      */       {
/*      */ 
/* 2547 */         if (i9 >= 52) {
/* 2548 */           l4 = l2 + 365L;
/* 2549 */           if (this.cdate.isLeapYear()) {
/* 2550 */             l4 += 1L;
/*      */           }
/* 2552 */           l5 = BaseCalendar.getDayOfWeekDateOnOrBefore(l4 + 6L, 
/* 2553 */             getFirstDayOfWeek());
/* 2554 */           int i11 = (int)(l5 - l4);
/* 2555 */           if ((i11 >= getMinimalDaysInFirstWeek()) && (l1 >= l5 - 7L))
/*      */           {
/* 2557 */             i9 = 1;
/*      */           }
/*      */         }
/*      */       } else {
/* 2561 */         Object localObject1 = this.calsys;
/* 2562 */         int i10 = i3 + 1;
/* 2563 */         if ((i10 == this.gregorianCutoverYearJulian + 1) && (i10 < this.gregorianCutoverYear))
/*      */         {
/*      */ 
/* 2566 */           i10 = this.gregorianCutoverYear;
/*      */         }
/* 2568 */         if (i10 == this.gregorianCutoverYear) {
/* 2569 */           localObject1 = getCutoverCalendarSystem();
/*      */         }
/*      */         
/*      */ 
/* 2573 */         if ((i10 > this.gregorianCutoverYear) || (this.gregorianCutoverYearJulian == this.gregorianCutoverYear) || (i10 == this.gregorianCutoverYearJulian))
/*      */         {
/*      */ 
/* 2576 */           l5 = ((BaseCalendar)localObject1).getFixedDate(i10, 1, 1, null);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2581 */           l5 = this.gregorianCutoverDate;
/* 2582 */           localObject1 = gcal;
/*      */         }
/*      */         
/* 2585 */         long l6 = BaseCalendar.getDayOfWeekDateOnOrBefore(l5 + 6L, 
/* 2586 */           getFirstDayOfWeek());
/* 2587 */         int i13 = (int)(l6 - l5);
/* 2588 */         if ((i13 >= getMinimalDaysInFirstWeek()) && (l1 >= l6 - 7L))
/*      */         {
/* 2590 */           i9 = 1;
/*      */         }
/*      */       }
/*      */       
/* 2594 */       internalSet(3, i9);
/* 2595 */       internalSet(4, getWeekNumber(l3, l1));
/* 2596 */       n |= 0x158;
/*      */     }
/* 2598 */     return n;
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
/*      */   private int getWeekNumber(long paramLong1, long paramLong2)
/*      */   {
/* 2613 */     long l = Gregorian.getDayOfWeekDateOnOrBefore(paramLong1 + 6L, 
/* 2614 */       getFirstDayOfWeek());
/* 2615 */     int i = (int)(l - paramLong1);
/* 2616 */     assert (i <= 7);
/* 2617 */     if (i >= getMinimalDaysInFirstWeek()) {
/* 2618 */       l -= 7L;
/*      */     }
/* 2620 */     int j = (int)(paramLong2 - l);
/* 2621 */     if (j >= 0) {
/* 2622 */       return j / 7 + 1;
/*      */     }
/* 2624 */     return CalendarUtils.floorDivide(j, 7) + 1;
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
/*      */   protected void computeTime()
/*      */   {
/* 2639 */     if (!isLenient()) {
/* 2640 */       if (this.originalFields == null) {
/* 2641 */         this.originalFields = new int[17];
/*      */       }
/* 2643 */       for (i = 0; i < 17; i++) {
/* 2644 */         j = internalGet(i);
/* 2645 */         if (isExternallySet(i))
/*      */         {
/* 2647 */           if ((j < getMinimum(i)) || (j > getMaximum(i))) {
/* 2648 */             throw new IllegalArgumentException(getFieldName(i));
/*      */           }
/*      */         }
/* 2651 */         this.originalFields[i] = j;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2657 */     int i = selectFields();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2662 */     int j = isSet(1) ? internalGet(1) : 1970;
/*      */     
/* 2664 */     int k = internalGetEra();
/* 2665 */     if (k == 0) {
/* 2666 */       j = 1 - j;
/* 2667 */     } else if (k != 1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2672 */       throw new IllegalArgumentException("Invalid era");
/*      */     }
/*      */     
/*      */ 
/* 2676 */     if ((j <= 0) && (!isSet(0))) {
/* 2677 */       i |= 0x1;
/* 2678 */       setFieldsComputed(1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2683 */     long l1 = 0L;
/* 2684 */     if (isFieldSet(i, 11)) {
/* 2685 */       l1 += internalGet(11);
/*      */     } else {
/* 2687 */       l1 += internalGet(10);
/*      */       
/* 2689 */       if (isFieldSet(i, 9)) {
/* 2690 */         l1 += 12 * internalGet(9);
/*      */       }
/*      */     }
/* 2693 */     l1 *= 60L;
/* 2694 */     l1 += internalGet(12);
/* 2695 */     l1 *= 60L;
/* 2696 */     l1 += internalGet(13);
/* 2697 */     l1 *= 1000L;
/* 2698 */     l1 += internalGet(14);
/*      */     
/*      */ 
/*      */ 
/* 2702 */     long l2 = l1 / 86400000L;
/* 2703 */     l1 %= 86400000L;
/* 2704 */     while (l1 < 0L) {
/* 2705 */       l1 += 86400000L;
/* 2706 */       l2 -= 1L;
/*      */     }
/*      */     
/*      */ 
/*      */     long l4;
/*      */     
/* 2712 */     if ((j > this.gregorianCutoverYear) && (j > this.gregorianCutoverYearJulian)) {
/* 2713 */       l3 = l2 + getFixedDate(gcal, j, i);
/* 2714 */       if (l3 >= this.gregorianCutoverDate) {
/* 2715 */         l2 = l3;
/*      */         break label619;
/*      */       }
/* 2718 */       l4 = l2 + getFixedDate(getJulianCalendarSystem(), j, i);
/* 2719 */     } else if ((j < this.gregorianCutoverYear) && (j < this.gregorianCutoverYearJulian)) {
/* 2720 */       l4 = l2 + getFixedDate(getJulianCalendarSystem(), j, i);
/* 2721 */       if (l4 < this.gregorianCutoverDate) {
/* 2722 */         l2 = l4;
/*      */         break label619;
/*      */       }
/* 2725 */       l3 = l4;
/*      */     } else {
/* 2727 */       l4 = l2 + getFixedDate(getJulianCalendarSystem(), j, i);
/* 2728 */       l3 = l2 + getFixedDate(gcal, j, i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2735 */     if ((isFieldSet(i, 6)) || (isFieldSet(i, 3))) {
/* 2736 */       if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
/* 2737 */         l2 = l4;
/*      */         break label619; }
/* 2739 */       if (j == this.gregorianCutoverYear) {
/* 2740 */         l2 = l3;
/*      */         
/*      */         break label619;
/*      */       }
/*      */     }
/* 2745 */     if (l3 >= this.gregorianCutoverDate) {
/* 2746 */       if (l4 >= this.gregorianCutoverDate) {
/* 2747 */         l2 = l3;
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/* 2752 */       else if ((this.calsys == gcal) || (this.calsys == null)) {
/* 2753 */         l2 = l3;
/*      */       } else {
/* 2755 */         l2 = l4;
/*      */       }
/*      */       
/*      */     }
/* 2759 */     else if (l4 < this.gregorianCutoverDate) {
/* 2760 */       l2 = l4;
/*      */     }
/*      */     else {
/* 2763 */       if (!isLenient()) {
/* 2764 */         throw new IllegalArgumentException("the specified date doesn't exist");
/*      */       }
/*      */       
/*      */ 
/* 2768 */       l2 = l4;
/*      */     }
/*      */     
/*      */ 
/*      */     label619:
/*      */     
/* 2774 */     long l3 = (l2 - 719163L) * 86400000L + l1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2789 */     TimeZone localTimeZone = getZone();
/* 2790 */     if (this.zoneOffsets == null) {
/* 2791 */       this.zoneOffsets = new int[2];
/*      */     }
/* 2793 */     int m = i & 0x18000;
/* 2794 */     if (m != 98304) {
/* 2795 */       if ((localTimeZone instanceof ZoneInfo)) {
/* 2796 */         ((ZoneInfo)localTimeZone).getOffsetsByWall(l3, this.zoneOffsets);
/*      */       }
/*      */       else {
/* 2799 */         n = isFieldSet(i, 15) ? internalGet(15) : localTimeZone.getRawOffset();
/* 2800 */         localTimeZone.getOffsets(l3 - n, this.zoneOffsets);
/*      */       }
/*      */     }
/* 2803 */     if (m != 0) {
/* 2804 */       if (isFieldSet(m, 15)) {
/* 2805 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 2807 */       if (isFieldSet(m, 16)) {
/* 2808 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2813 */     l3 -= this.zoneOffsets[0] + this.zoneOffsets[1];
/*      */     
/*      */ 
/* 2816 */     this.time = l3;
/*      */     
/* 2818 */     int n = computeFields(i | getSetStateFields(), m);
/*      */     
/* 2820 */     if (!isLenient()) {
/* 2821 */       for (int i1 = 0; i1 < 17; i1++) {
/* 2822 */         if (isExternallySet(i1))
/*      */         {
/*      */ 
/* 2825 */           if (this.originalFields[i1] != internalGet(i1)) {
/* 2826 */             String str = this.originalFields[i1] + " -> " + internalGet(i1);
/*      */             
/* 2828 */             System.arraycopy(this.originalFields, 0, this.fields, 0, this.fields.length);
/* 2829 */             throw new IllegalArgumentException(getFieldName(i1) + ": " + str);
/*      */           } }
/*      */       }
/*      */     }
/* 2833 */     setFieldsNormalized(n);
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
/*      */   private long getFixedDate(BaseCalendar paramBaseCalendar, int paramInt1, int paramInt2)
/*      */   {
/* 2848 */     int i = 0;
/* 2849 */     if (isFieldSet(paramInt2, 2))
/*      */     {
/*      */ 
/* 2852 */       i = internalGet(2);
/*      */       
/*      */ 
/* 2855 */       if (i > 11) {
/* 2856 */         paramInt1 += i / 12;
/* 2857 */         i %= 12;
/* 2858 */       } else if (i < 0) {
/* 2859 */         int[] arrayOfInt = new int[1];
/* 2860 */         paramInt1 += CalendarUtils.floorDivide(i, 12, arrayOfInt);
/* 2861 */         i = arrayOfInt[0];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2867 */     long l1 = paramBaseCalendar.getFixedDate(paramInt1, i + 1, 1, paramBaseCalendar == gcal ? this.gdate : null);
/*      */     int m;
/* 2869 */     if (isFieldSet(paramInt2, 2))
/*      */     {
/* 2871 */       if (isFieldSet(paramInt2, 5))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2878 */         if (isSet(5))
/*      */         {
/*      */ 
/* 2881 */           l1 += internalGet(5);
/* 2882 */           l1 -= 1L;
/*      */         }
/*      */       }
/* 2885 */       else if (isFieldSet(paramInt2, 4)) {
/* 2886 */         long l2 = BaseCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, 
/* 2887 */           getFirstDayOfWeek());
/*      */         
/*      */ 
/* 2890 */         if (l2 - l1 >= getMinimalDaysInFirstWeek()) {
/* 2891 */           l2 -= 7L;
/*      */         }
/* 2893 */         if (isFieldSet(paramInt2, 7)) {
/* 2894 */           l2 = BaseCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, 
/* 2895 */             internalGet(7));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2900 */         l1 = l2 + 7 * (internalGet(4) - 1);
/*      */       } else {
/*      */         int j;
/* 2903 */         if (isFieldSet(paramInt2, 7)) {
/* 2904 */           j = internalGet(7);
/*      */         } else {
/* 2906 */           j = getFirstDayOfWeek();
/*      */         }
/*      */         
/*      */ 
/*      */         int k;
/*      */         
/* 2912 */         if (isFieldSet(paramInt2, 8)) {
/* 2913 */           k = internalGet(8);
/*      */         } else {
/* 2915 */           k = 1;
/*      */         }
/* 2917 */         if (k >= 0) {
/* 2918 */           l1 = BaseCalendar.getDayOfWeekDateOnOrBefore(l1 + 7 * k - 1L, j);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2923 */           m = monthLength(i, paramInt1) + 7 * (k + 1);
/*      */           
/* 2925 */           l1 = BaseCalendar.getDayOfWeekDateOnOrBefore(l1 + m - 1L, j);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2931 */       if ((paramInt1 == this.gregorianCutoverYear) && (paramBaseCalendar == gcal) && (l1 < this.gregorianCutoverDate) && (this.gregorianCutoverYear != this.gregorianCutoverYearJulian))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2937 */         l1 = this.gregorianCutoverDate;
/*      */       }
/*      */       
/* 2940 */       if (isFieldSet(paramInt2, 6))
/*      */       {
/* 2942 */         l1 += internalGet(6);
/* 2943 */         l1 -= 1L;
/*      */       } else {
/* 2945 */         long l3 = BaseCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, 
/* 2946 */           getFirstDayOfWeek());
/*      */         
/*      */ 
/* 2949 */         if (l3 - l1 >= getMinimalDaysInFirstWeek()) {
/* 2950 */           l3 -= 7L;
/*      */         }
/* 2952 */         if (isFieldSet(paramInt2, 7)) {
/* 2953 */           m = internalGet(7);
/* 2954 */           if (m != getFirstDayOfWeek()) {
/* 2955 */             l3 = BaseCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, m);
/*      */           }
/*      */         }
/*      */         
/* 2959 */         l1 = l3 + 7L * (internalGet(3) - 1L);
/*      */       }
/*      */     }
/*      */     
/* 2963 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private GregorianCalendar getNormalizedCalendar()
/*      */   {
/*      */     GregorianCalendar localGregorianCalendar;
/*      */     
/*      */ 
/* 2973 */     if (isFullyNormalized()) {
/* 2974 */       localGregorianCalendar = this;
/*      */     }
/*      */     else {
/* 2977 */       localGregorianCalendar = (GregorianCalendar)clone();
/* 2978 */       localGregorianCalendar.setLenient(true);
/* 2979 */       localGregorianCalendar.complete();
/*      */     }
/* 2981 */     return localGregorianCalendar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static synchronized BaseCalendar getJulianCalendarSystem()
/*      */   {
/* 2989 */     if (jcal == null) {
/* 2990 */       jcal = (JulianCalendar)CalendarSystem.forName("julian");
/* 2991 */       jeras = jcal.getEras();
/*      */     }
/* 2993 */     return jcal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BaseCalendar getCutoverCalendarSystem()
/*      */   {
/* 3002 */     if (this.gregorianCutoverYearJulian < this.gregorianCutoverYear) {
/* 3003 */       return gcal;
/*      */     }
/* 3005 */     return getJulianCalendarSystem();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isCutoverYear(int paramInt)
/*      */   {
/* 3013 */     int i = this.calsys == gcal ? this.gregorianCutoverYear : this.gregorianCutoverYearJulian;
/* 3014 */     return paramInt == i;
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
/*      */   private long getFixedDateJan1(BaseCalendar.Date paramDate, long paramLong)
/*      */   {
/* 3027 */     assert ((paramDate.getNormalizedYear() == this.gregorianCutoverYear) || 
/* 3028 */       (paramDate.getNormalizedYear() == this.gregorianCutoverYearJulian));
/* 3029 */     if ((this.gregorianCutoverYear != this.gregorianCutoverYearJulian) && 
/* 3030 */       (paramLong >= this.gregorianCutoverDate))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 3035 */       return this.gregorianCutoverDate;
/*      */     }
/*      */     
/*      */ 
/* 3039 */     BaseCalendar localBaseCalendar = getJulianCalendarSystem();
/* 3040 */     return localBaseCalendar.getFixedDate(paramDate.getNormalizedYear(), 1, 1, null);
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
/*      */   private long getFixedDateMonth1(BaseCalendar.Date paramDate, long paramLong)
/*      */   {
/* 3053 */     assert ((paramDate.getNormalizedYear() == this.gregorianCutoverYear) || 
/* 3054 */       (paramDate.getNormalizedYear() == this.gregorianCutoverYearJulian));
/* 3055 */     BaseCalendar.Date localDate1 = getGregorianCutoverDate();
/* 3056 */     if ((localDate1.getMonth() == 1) && 
/* 3057 */       (localDate1.getDayOfMonth() == 1))
/*      */     {
/* 3059 */       return paramLong - paramDate.getDayOfMonth() + 1L;
/*      */     }
/*      */     
/*      */     long l;
/*      */     
/* 3064 */     if (paramDate.getMonth() == localDate1.getMonth())
/*      */     {
/* 3066 */       BaseCalendar.Date localDate2 = getLastJulianDate();
/* 3067 */       if ((this.gregorianCutoverYear == this.gregorianCutoverYearJulian) && 
/* 3068 */         (localDate1.getMonth() == localDate2.getMonth()))
/*      */       {
/* 3070 */         l = jcal.getFixedDate(paramDate.getNormalizedYear(), paramDate
/* 3071 */           .getMonth(), 1, null);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 3076 */         l = this.gregorianCutoverDate;
/*      */       }
/*      */     }
/*      */     else {
/* 3080 */       l = paramLong - paramDate.getDayOfMonth() + 1L;
/*      */     }
/*      */     
/* 3083 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BaseCalendar.Date getCalendarDate(long paramLong)
/*      */   {
/* 3092 */     BaseCalendar localBaseCalendar = paramLong >= this.gregorianCutoverDate ? gcal : getJulianCalendarSystem();
/* 3093 */     BaseCalendar.Date localDate = (BaseCalendar.Date)localBaseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 3094 */     localBaseCalendar.getCalendarDateFromFixedDate(localDate, paramLong);
/* 3095 */     return localDate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BaseCalendar.Date getGregorianCutoverDate()
/*      */   {
/* 3103 */     return getCalendarDate(this.gregorianCutoverDate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BaseCalendar.Date getLastJulianDate()
/*      */   {
/* 3111 */     return getCalendarDate(this.gregorianCutoverDate - 1L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int monthLength(int paramInt1, int paramInt2)
/*      */   {
/* 3121 */     return isLeapYear(paramInt2) ? LEAP_MONTH_LENGTH[paramInt1] : MONTH_LENGTH[paramInt1];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int monthLength(int paramInt)
/*      */   {
/* 3131 */     int i = internalGet(1);
/* 3132 */     if (internalGetEra() == 0) {
/* 3133 */       i = 1 - i;
/*      */     }
/* 3135 */     return monthLength(paramInt, i);
/*      */   }
/*      */   
/*      */   private int actualMonthLength() {
/* 3139 */     int i = this.cdate.getNormalizedYear();
/* 3140 */     if ((i != this.gregorianCutoverYear) && (i != this.gregorianCutoverYearJulian)) {
/* 3141 */       return this.calsys.getMonthLength(this.cdate);
/*      */     }
/* 3143 */     Object localObject = (BaseCalendar.Date)this.cdate.clone();
/* 3144 */     long l1 = this.calsys.getFixedDate((CalendarDate)localObject);
/* 3145 */     long l2 = getFixedDateMonth1((BaseCalendar.Date)localObject, l1);
/* 3146 */     long l3 = l2 + this.calsys.getMonthLength((CalendarDate)localObject);
/* 3147 */     if (l3 < this.gregorianCutoverDate) {
/* 3148 */       return (int)(l3 - l2);
/*      */     }
/* 3150 */     if (this.cdate != this.gdate) {
/* 3151 */       localObject = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/*      */     }
/* 3153 */     gcal.getCalendarDateFromFixedDate((CalendarDate)localObject, l3);
/* 3154 */     l3 = getFixedDateMonth1((BaseCalendar.Date)localObject, l3);
/* 3155 */     return (int)(l3 - l2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int yearLength(int paramInt)
/*      */   {
/* 3163 */     return isLeapYear(paramInt) ? 366 : 365;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int yearLength()
/*      */   {
/* 3171 */     int i = internalGet(1);
/* 3172 */     if (internalGetEra() == 0) {
/* 3173 */       i = 1 - i;
/*      */     }
/* 3175 */     return yearLength(i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void pinDayOfMonth()
/*      */   {
/* 3185 */     int i = internalGet(1);
/*      */     int j;
/* 3187 */     if ((i > this.gregorianCutoverYear) || (i < this.gregorianCutoverYearJulian)) {
/* 3188 */       j = monthLength(internalGet(2));
/*      */     } else {
/* 3190 */       GregorianCalendar localGregorianCalendar = getNormalizedCalendar();
/* 3191 */       j = localGregorianCalendar.getActualMaximum(5);
/*      */     }
/* 3193 */     int k = internalGet(5);
/* 3194 */     if (k > j) {
/* 3195 */       set(5, j);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long getCurrentFixedDate()
/*      */   {
/* 3204 */     return this.calsys == gcal ? this.cachedFixedDate : this.calsys.getFixedDate(this.cdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int getRolledValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 3211 */     assert ((paramInt1 >= paramInt3) && (paramInt1 <= paramInt4));
/* 3212 */     int i = paramInt4 - paramInt3 + 1;
/* 3213 */     paramInt2 %= i;
/* 3214 */     int j = paramInt1 + paramInt2;
/* 3215 */     if (j > paramInt4) {
/* 3216 */       j -= i;
/* 3217 */     } else if (j < paramInt3) {
/* 3218 */       j += i;
/*      */     }
/* 3220 */     assert ((j >= paramInt3) && (j <= paramInt4));
/* 3221 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int internalGetEra()
/*      */   {
/* 3229 */     return isSet(0) ? internalGet(0) : 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 3237 */     paramObjectInputStream.defaultReadObject();
/* 3238 */     if (this.gdate == null) {
/* 3239 */       this.gdate = gcal.newCalendarDate(getZone());
/* 3240 */       this.cachedFixedDate = Long.MIN_VALUE;
/*      */     }
/* 3242 */     setGregorianChange(this.gregorianCutover);
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
/*      */   public ZonedDateTime toZonedDateTime()
/*      */   {
/* 3260 */     return ZonedDateTime.ofInstant(Instant.ofEpochMilli(getTimeInMillis()), 
/* 3261 */       getTimeZone().toZoneId());
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
/*      */   public static GregorianCalendar from(ZonedDateTime paramZonedDateTime)
/*      */   {
/* 3289 */     GregorianCalendar localGregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(paramZonedDateTime.getZone()));
/* 3290 */     localGregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
/* 3291 */     localGregorianCalendar.setFirstDayOfWeek(2);
/* 3292 */     localGregorianCalendar.setMinimalDaysInFirstWeek(4);
/*      */     try {
/* 3294 */       localGregorianCalendar.setTimeInMillis(Math.addExact(Math.multiplyExact(paramZonedDateTime.toEpochSecond(), 1000L), paramZonedDateTime
/* 3295 */         .get(ChronoField.MILLI_OF_SECOND)));
/*      */     } catch (ArithmeticException localArithmeticException) {
/* 3297 */       throw new IllegalArgumentException(localArithmeticException);
/*      */     }
/* 3299 */     return localGregorianCalendar;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/GregorianCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */