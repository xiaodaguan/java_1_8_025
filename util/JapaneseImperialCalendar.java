/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import sun.util.calendar.BaseCalendar.Date;
/*      */ import sun.util.calendar.CalendarDate;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.Era;
/*      */ import sun.util.calendar.Gregorian;
/*      */ import sun.util.calendar.Gregorian.Date;
/*      */ import sun.util.calendar.LocalGregorianCalendar;
/*      */ import sun.util.calendar.LocalGregorianCalendar.Date;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class JapaneseImperialCalendar
/*      */   extends Calendar
/*      */ {
/*      */   public static final int BEFORE_MEIJI = 0;
/*      */   public static final int MEIJI = 1;
/*      */   public static final int TAISHO = 2;
/*      */   public static final int SHOWA = 3;
/*      */   public static final int HEISEI = 4;
/*      */   private static final int EPOCH_OFFSET = 719163;
/*      */   private static final int EPOCH_YEAR = 1970;
/*      */   private static final int ONE_SECOND = 1000;
/*      */   private static final int ONE_MINUTE = 60000;
/*      */   private static final int ONE_HOUR = 3600000;
/*      */   private static final long ONE_DAY = 86400000L;
/*      */   private static final long ONE_WEEK = 604800000L;
/*      */   private static final LocalGregorianCalendar jcal;
/*      */   private static final Gregorian gcal;
/*      */   private static final Era BEFORE_MEIJI_ERA;
/*      */   private static final Era[] eras;
/*      */   private static final long[] sinceFixedDates;
/*      */   static final int[] MIN_VALUES;
/*      */   static final int[] LEAST_MAX_VALUES;
/*      */   static final int[] MAX_VALUES;
/*      */   private static final long serialVersionUID = -3364572813905467929L;
/*      */   private transient LocalGregorianCalendar.Date jdate;
/*      */   private transient int[] zoneOffsets;
/*      */   private transient int[] originalFields;
/*      */   
/*      */   static
/*      */   {
/*  118 */     jcal = (LocalGregorianCalendar)CalendarSystem.forName("japanese");
/*      */     
/*      */ 
/*      */ 
/*  122 */     gcal = CalendarSystem.getGregorianCalendar();
/*      */     
/*      */ 
/*  125 */     BEFORE_MEIJI_ERA = new Era("BeforeMeiji", "BM", Long.MIN_VALUE, false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  161 */     MIN_VALUES = new int[] { 0, -292275055, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0 };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  180 */     LEAST_MAX_VALUES = new int[] { 0, 0, 0, 0, 4, 28, 0, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000 };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  199 */     MAX_VALUES = new int[] { 0, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000 };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  223 */     Era[] arrayOfEra1 = jcal.getEras();
/*  224 */     int i = arrayOfEra1.length + 1;
/*  225 */     eras = new Era[i];
/*  226 */     sinceFixedDates = new long[i];
/*      */     
/*      */ 
/*      */ 
/*  230 */     int j = 0;
/*  231 */     sinceFixedDates[j] = gcal.getFixedDate(BEFORE_MEIJI_ERA.getSinceDate());
/*  232 */     eras[(j++)] = BEFORE_MEIJI_ERA;
/*  233 */     for (Era localEra : arrayOfEra1) {
/*  234 */       CalendarDate localCalendarDate1 = localEra.getSinceDate();
/*  235 */       sinceFixedDates[j] = gcal.getFixedDate(localCalendarDate1);
/*  236 */       eras[(j++)] = localEra;
/*      */     }
/*      */     
/*  239 */     LEAST_MAX_VALUES[0] = (MAX_VALUES[0] = eras.length - 1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  244 */     int k = Integer.MAX_VALUE;
/*  245 */     ??? = Integer.MAX_VALUE;
/*  246 */     Gregorian.Date localDate = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/*  247 */     for (int i1 = 1; i1 < eras.length; i1++) {
/*  248 */       long l1 = sinceFixedDates[i1];
/*  249 */       CalendarDate localCalendarDate2 = eras[i1].getSinceDate();
/*  250 */       localDate.setDate(localCalendarDate2.getYear(), 1, 1);
/*  251 */       long l2 = gcal.getFixedDate(localDate);
/*  252 */       if (l1 != l2) {
/*  253 */         ??? = Math.min((int)(l1 - l2) + 1, ???);
/*      */       }
/*  255 */       localDate.setDate(localCalendarDate2.getYear(), 12, 31);
/*  256 */       l2 = gcal.getFixedDate(localDate);
/*  257 */       if (l1 != l2) {
/*  258 */         ??? = Math.min((int)(l2 - l1) + 1, ???);
/*      */       }
/*  260 */       LocalGregorianCalendar.Date localDate1 = getCalendarDate(l1 - 1L);
/*  261 */       int i2 = localDate1.getYear();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  266 */       if ((localDate1.getMonth() != 1) || (localDate1.getDayOfMonth() != 1)) {
/*  267 */         i2--;
/*      */       }
/*  269 */       k = Math.min(i2, k);
/*      */     }
/*  271 */     LEAST_MAX_VALUES[1] = k;
/*  272 */     LEAST_MAX_VALUES[6] = ???;
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
/*      */   JapaneseImperialCalendar(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/*  302 */     super(paramTimeZone, paramLocale);
/*  303 */     this.jdate = jcal.newCalendarDate(paramTimeZone);
/*  304 */     setTimeInMillis(System.currentTimeMillis());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JapaneseImperialCalendar(TimeZone paramTimeZone, Locale paramLocale, boolean paramBoolean)
/*      */   {
/*  315 */     super(paramTimeZone, paramLocale);
/*  316 */     this.jdate = jcal.newCalendarDate(paramTimeZone);
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
/*  327 */     return "japanese";
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  345 */     return ((paramObject instanceof JapaneseImperialCalendar)) && (super.equals(paramObject));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  353 */     return super.hashCode() ^ this.jdate.hashCode();
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
/*      */   public void add(int paramInt1, int paramInt2)
/*      */   {
/*  387 */     if (paramInt2 == 0) {
/*  388 */       return;
/*      */     }
/*      */     
/*  391 */     if ((paramInt1 < 0) || (paramInt1 >= 15)) {
/*  392 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*  396 */     complete();
/*      */     LocalGregorianCalendar.Date localDate;
/*  398 */     if (paramInt1 == 1) {
/*  399 */       localDate = (LocalGregorianCalendar.Date)this.jdate.clone();
/*  400 */       localDate.addYear(paramInt2);
/*  401 */       pinDayOfMonth(localDate);
/*  402 */       set(0, getEraIndex(localDate));
/*  403 */       set(1, localDate.getYear());
/*  404 */       set(2, localDate.getMonth() - 1);
/*  405 */       set(5, localDate.getDayOfMonth());
/*  406 */     } else if (paramInt1 == 2) {
/*  407 */       localDate = (LocalGregorianCalendar.Date)this.jdate.clone();
/*  408 */       localDate.addMonth(paramInt2);
/*  409 */       pinDayOfMonth(localDate);
/*  410 */       set(0, getEraIndex(localDate));
/*  411 */       set(1, localDate.getYear());
/*  412 */       set(2, localDate.getMonth() - 1);
/*  413 */       set(5, localDate.getDayOfMonth());
/*  414 */     } else if (paramInt1 == 0) {
/*  415 */       int i = internalGet(0) + paramInt2;
/*  416 */       if (i < 0) {
/*  417 */         i = 0;
/*  418 */       } else if (i > eras.length - 1) {
/*  419 */         i = eras.length - 1;
/*      */       }
/*  421 */       set(0, i);
/*      */     } else {
/*  423 */       long l1 = paramInt2;
/*  424 */       long l2 = 0L;
/*  425 */       switch (paramInt1)
/*      */       {
/*      */ 
/*      */       case 10: 
/*      */       case 11: 
/*  430 */         l1 *= 3600000L;
/*  431 */         break;
/*      */       
/*      */       case 12: 
/*  434 */         l1 *= 60000L;
/*  435 */         break;
/*      */       
/*      */       case 13: 
/*  438 */         l1 *= 1000L;
/*  439 */         break;
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
/*  450 */         l1 *= 7L;
/*  451 */         break;
/*      */       
/*      */ 
/*      */       case 5: 
/*      */       case 6: 
/*      */       case 7: 
/*      */         break;
/*      */       
/*      */ 
/*      */       case 9: 
/*  461 */         l1 = paramInt2 / 2;
/*  462 */         l2 = 12 * (paramInt2 % 2);
/*      */       }
/*      */       
/*      */       
/*      */ 
/*      */ 
/*  468 */       if (paramInt1 >= 10) {
/*  469 */         setTimeInMillis(this.time + l1);
/*  470 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  479 */       long l3 = this.cachedFixedDate;
/*  480 */       l2 += internalGet(11);
/*  481 */       l2 *= 60L;
/*  482 */       l2 += internalGet(12);
/*  483 */       l2 *= 60L;
/*  484 */       l2 += internalGet(13);
/*  485 */       l2 *= 1000L;
/*  486 */       l2 += internalGet(14);
/*  487 */       if (l2 >= 86400000L) {
/*  488 */         l3 += 1L;
/*  489 */         l2 -= 86400000L;
/*  490 */       } else if (l2 < 0L) {
/*  491 */         l3 -= 1L;
/*  492 */         l2 += 86400000L;
/*      */       }
/*      */       
/*  495 */       l3 += l1;
/*  496 */       int j = internalGet(15) + internalGet(16);
/*  497 */       setTimeInMillis((l3 - 719163L) * 86400000L + l2 - j);
/*  498 */       j -= internalGet(15) + internalGet(16);
/*      */       
/*  500 */       if (j != 0) {
/*  501 */         setTimeInMillis(this.time + j);
/*  502 */         long l4 = this.cachedFixedDate;
/*      */         
/*      */ 
/*  505 */         if (l4 != l3) {
/*  506 */           setTimeInMillis(this.time - j);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void roll(int paramInt, boolean paramBoolean) {
/*  513 */     roll(paramInt, paramBoolean ? 1 : -1);
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
/*      */   public void roll(int paramInt1, int paramInt2)
/*      */   {
/*  539 */     if (paramInt2 == 0) {
/*  540 */       return;
/*      */     }
/*      */     
/*  543 */     if ((paramInt1 < 0) || (paramInt1 >= 15)) {
/*  544 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*  548 */     complete();
/*      */     
/*  550 */     int i = getMinimum(paramInt1);
/*  551 */     int j = getMaximum(paramInt1);
/*      */     int k;
/*  553 */     int i8; int i6; int i2; long l9; LocalGregorianCalendar.Date localDate7; int i15; int i7; LocalGregorianCalendar.Date localDate4; int m; switch (paramInt1)
/*      */     {
/*      */     case 0: 
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
/*  568 */       k = j + 1;
/*  569 */       int n = internalGet(paramInt1);
/*  570 */       int i5 = (n + paramInt2) % k;
/*  571 */       if (i5 < 0) {
/*  572 */         i5 += k;
/*      */       }
/*  574 */       this.time += 3600000 * (i5 - n);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  580 */       LocalGregorianCalendar.Date localDate3 = jcal.getCalendarDate(this.time, getZone());
/*  581 */       if (internalGet(5) != localDate3.getDayOfMonth()) {
/*  582 */         localDate3.setEra(this.jdate.getEra());
/*  583 */         localDate3.setDate(internalGet(1), 
/*  584 */           internalGet(2) + 1, 
/*  585 */           internalGet(5));
/*  586 */         if (paramInt1 == 10) {
/*  587 */           assert (internalGet(9) == 1);
/*  588 */           localDate3.addHours(12);
/*      */         }
/*  590 */         this.time = jcal.getTime(localDate3);
/*      */       }
/*  592 */       int i10 = localDate3.getHours();
/*  593 */       internalSet(paramInt1, i10 % k);
/*  594 */       if (paramInt1 == 10) {
/*  595 */         internalSet(11, i10);
/*      */       } else {
/*  597 */         internalSet(9, i10 / 12);
/*  598 */         internalSet(10, i10 % 12);
/*      */       }
/*      */       
/*      */ 
/*  602 */       int i12 = localDate3.getZoneOffset();
/*  603 */       int i14 = localDate3.getDaylightSaving();
/*  604 */       internalSet(15, i12 - i14);
/*  605 */       internalSet(16, i14);
/*  606 */       return;
/*      */     
/*      */ 
/*      */     case 1: 
/*  610 */       i = getActualMinimum(paramInt1);
/*  611 */       j = getActualMaximum(paramInt1);
/*  612 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 2: 
/*  620 */       if (!isTransitionYear(this.jdate.getNormalizedYear())) {
/*  621 */         k = this.jdate.getYear();
/*  622 */         LocalGregorianCalendar.Date localDate1; LocalGregorianCalendar.Date localDate2; if (k == getMaximum(1)) {
/*  623 */           localDate1 = jcal.getCalendarDate(this.time, getZone());
/*  624 */           localDate2 = jcal.getCalendarDate(Long.MAX_VALUE, getZone());
/*  625 */           j = localDate2.getMonth() - 1;
/*  626 */           i8 = getRolledValue(internalGet(paramInt1), paramInt2, i, j);
/*  627 */           if (i8 == j)
/*      */           {
/*  629 */             localDate1.addYear(65136);
/*  630 */             localDate1.setMonth(i8 + 1);
/*  631 */             if (localDate1.getDayOfMonth() > localDate2.getDayOfMonth()) {
/*  632 */               localDate1.setDayOfMonth(localDate2.getDayOfMonth());
/*  633 */               jcal.normalize(localDate1);
/*      */             }
/*  635 */             if ((localDate1.getDayOfMonth() == localDate2.getDayOfMonth()) && 
/*  636 */               (localDate1.getTimeOfDay() > localDate2.getTimeOfDay())) {
/*  637 */               localDate1.setMonth(i8 + 1);
/*  638 */               localDate1.setDayOfMonth(localDate2.getDayOfMonth() - 1);
/*  639 */               jcal.normalize(localDate1);
/*      */               
/*  641 */               i8 = localDate1.getMonth() - 1;
/*      */             }
/*  643 */             set(5, localDate1.getDayOfMonth());
/*      */           }
/*  645 */           set(2, i8);
/*  646 */         } else if (k == getMinimum(1)) {
/*  647 */           localDate1 = jcal.getCalendarDate(this.time, getZone());
/*  648 */           localDate2 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/*  649 */           i = localDate2.getMonth() - 1;
/*  650 */           i8 = getRolledValue(internalGet(paramInt1), paramInt2, i, j);
/*  651 */           if (i8 == i)
/*      */           {
/*  653 */             localDate1.addYear(400);
/*  654 */             localDate1.setMonth(i8 + 1);
/*  655 */             if (localDate1.getDayOfMonth() < localDate2.getDayOfMonth()) {
/*  656 */               localDate1.setDayOfMonth(localDate2.getDayOfMonth());
/*  657 */               jcal.normalize(localDate1);
/*      */             }
/*  659 */             if ((localDate1.getDayOfMonth() == localDate2.getDayOfMonth()) && 
/*  660 */               (localDate1.getTimeOfDay() < localDate2.getTimeOfDay())) {
/*  661 */               localDate1.setMonth(i8 + 1);
/*  662 */               localDate1.setDayOfMonth(localDate2.getDayOfMonth() + 1);
/*  663 */               jcal.normalize(localDate1);
/*      */               
/*  665 */               i8 = localDate1.getMonth() - 1;
/*      */             }
/*  667 */             set(5, localDate1.getDayOfMonth());
/*      */           }
/*  669 */           set(2, i8);
/*      */         } else {
/*  671 */           int i1 = (internalGet(2) + paramInt2) % 12;
/*  672 */           if (i1 < 0) {
/*  673 */             i1 += 12;
/*      */           }
/*  675 */           set(2, i1);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  681 */           i6 = monthLength(i1);
/*  682 */           if (internalGet(5) > i6) {
/*  683 */             set(5, i6);
/*      */           }
/*      */         }
/*      */       } else {
/*  687 */         k = getEraIndex(this.jdate);
/*  688 */         CalendarDate localCalendarDate = null;
/*  689 */         if (this.jdate.getYear() == 1) {
/*  690 */           localCalendarDate = eras[k].getSinceDate();
/*  691 */           i = localCalendarDate.getMonth() - 1;
/*      */         }
/*  693 */         else if (k < eras.length - 1) {
/*  694 */           localCalendarDate = eras[(k + 1)].getSinceDate();
/*  695 */           if (localCalendarDate.getYear() == this.jdate.getNormalizedYear()) {
/*  696 */             j = localCalendarDate.getMonth() - 1;
/*  697 */             if (localCalendarDate.getDayOfMonth() == 1) {
/*  698 */               j--;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  704 */         if (i == j)
/*      */         {
/*      */ 
/*      */ 
/*  708 */           return;
/*      */         }
/*  710 */         i6 = getRolledValue(internalGet(paramInt1), paramInt2, i, j);
/*  711 */         set(2, i6);
/*  712 */         if (i6 == i) {
/*  713 */           if (((localCalendarDate.getMonth() != 1) || 
/*  714 */             (localCalendarDate.getDayOfMonth() != 1)) && 
/*  715 */             (this.jdate.getDayOfMonth() < localCalendarDate.getDayOfMonth())) {
/*  716 */             set(5, localCalendarDate.getDayOfMonth());
/*      */           }
/*      */         }
/*  719 */         else if ((i6 == j) && (localCalendarDate.getMonth() - 1 == i6)) {
/*  720 */           i8 = localCalendarDate.getDayOfMonth();
/*  721 */           if (this.jdate.getDayOfMonth() >= i8) {
/*  722 */             set(5, i8 - 1);
/*      */           }
/*      */         }
/*      */       }
/*  726 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 3: 
/*  731 */       k = this.jdate.getNormalizedYear();
/*  732 */       j = getActualMaximum(3);
/*  733 */       set(7, internalGet(7));
/*  734 */       i2 = internalGet(3);
/*  735 */       i6 = i2 + paramInt2;
/*  736 */       if (!isTransitionYear(this.jdate.getNormalizedYear())) {
/*  737 */         i8 = this.jdate.getYear();
/*  738 */         if (i8 == getMaximum(1)) {
/*  739 */           j = getActualMaximum(3);
/*  740 */         } else if (i8 == getMinimum(1)) {
/*  741 */           i = getActualMinimum(3);
/*  742 */           j = getActualMaximum(3);
/*  743 */           if ((i6 > i) && (i6 < j)) {
/*  744 */             set(3, i6);
/*  745 */             return;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  751 */         if ((i6 > i) && (i6 < j)) {
/*  752 */           set(3, i6);
/*  753 */           return;
/*      */         }
/*  755 */         l9 = this.cachedFixedDate;
/*      */         
/*  757 */         long l11 = l9 - 7 * (i2 - i);
/*  758 */         if (i8 != getMinimum(1)) {
/*  759 */           if (gcal.getYearFromFixedDate(l11) != k) {
/*  760 */             i++;
/*      */           }
/*      */         } else {
/*  763 */           localDate7 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/*  764 */           if (l11 < jcal.getFixedDate(localDate7)) {
/*  765 */             i++;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  770 */         l9 += 7 * (j - internalGet(3));
/*  771 */         if (gcal.getYearFromFixedDate(l9) != k) {
/*  772 */           j--;
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  778 */         long l7 = this.cachedFixedDate;
/*  779 */         long l10 = l7 - 7 * (i2 - i);
/*      */         
/*  781 */         LocalGregorianCalendar.Date localDate6 = getCalendarDate(l10);
/*  782 */         if ((localDate6.getEra() != this.jdate.getEra()) || (localDate6.getYear() != this.jdate.getYear())) {
/*  783 */           i++;
/*      */         }
/*      */         
/*      */ 
/*  787 */         l7 += 7 * (j - i2);
/*  788 */         jcal.getCalendarDateFromFixedDate(localDate6, l7);
/*  789 */         if ((localDate6.getEra() != this.jdate.getEra()) || (localDate6.getYear() != this.jdate.getYear())) {
/*  790 */           j--;
/*      */         }
/*      */         
/*      */ 
/*  794 */         i6 = getRolledValue(i2, paramInt2, i, j) - 1;
/*  795 */         localDate6 = getCalendarDate(l10 + i6 * 7);
/*  796 */         set(2, localDate6.getMonth() - 1);
/*  797 */         set(5, localDate6.getDayOfMonth()); return;
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     case 4: 
/*  803 */       boolean bool = isTransitionYear(this.jdate.getNormalizedYear());
/*      */       
/*  805 */       i2 = internalGet(7) - getFirstDayOfWeek();
/*  806 */       if (i2 < 0) {
/*  807 */         i2 += 7;
/*      */       }
/*      */       
/*  810 */       long l5 = this.cachedFixedDate;
/*      */       
/*      */ 
/*  813 */       if (bool) {
/*  814 */         l9 = getFixedDateMonth1(this.jdate, l5);
/*  815 */         i15 = actualMonthLength();
/*      */       } else {
/*  817 */         l9 = l5 - internalGet(5) + 1L;
/*  818 */         i15 = jcal.getMonthLength(this.jdate);
/*      */       }
/*      */       
/*      */ 
/*  822 */       long l12 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l9 + 6L, 
/*  823 */         getFirstDayOfWeek());
/*      */       
/*      */ 
/*  826 */       if ((int)(l12 - l9) >= getMinimalDaysInFirstWeek()) {
/*  827 */         l12 -= 7L;
/*      */       }
/*  829 */       j = getActualMaximum(paramInt1);
/*      */       
/*      */ 
/*  832 */       int i17 = getRolledValue(internalGet(paramInt1), paramInt2, 1, j) - 1;
/*      */       
/*      */ 
/*  835 */       long l13 = l12 + i17 * 7 + i2;
/*      */       
/*      */ 
/*      */ 
/*  839 */       if (l13 < l9) {
/*  840 */         l13 = l9;
/*  841 */       } else if (l13 >= l9 + i15) {
/*  842 */         l13 = l9 + i15 - 1L;
/*      */       }
/*  844 */       set(5, (int)(l13 - l9) + 1);
/*  845 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 5: 
/*  850 */       if (!isTransitionYear(this.jdate.getNormalizedYear())) {
/*  851 */         j = jcal.getMonthLength(this.jdate);
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*  859 */         long l1 = getFixedDateMonth1(this.jdate, this.cachedFixedDate);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  864 */         i7 = getRolledValue((int)(this.cachedFixedDate - l1), paramInt2, 0, 
/*  865 */           actualMonthLength() - 1);
/*  866 */         localDate4 = getCalendarDate(l1 + i7);
/*  867 */         assert ((getEraIndex(localDate4) == internalGetEra()) && 
/*  868 */           (localDate4.getYear() == internalGet(1)) && (localDate4.getMonth() - 1 == internalGet(2)));
/*  869 */         set(5, localDate4.getDayOfMonth()); return;
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     case 6: 
/*  875 */       j = getActualMaximum(paramInt1);
/*  876 */       if (isTransitionYear(this.jdate.getNormalizedYear()))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  882 */         m = getRolledValue(internalGet(6), paramInt2, i, j);
/*  883 */         long l3 = this.cachedFixedDate - internalGet(6);
/*  884 */         localDate4 = getCalendarDate(l3 + m);
/*  885 */         assert ((getEraIndex(localDate4) == internalGetEra()) && (localDate4.getYear() == internalGet(1)));
/*  886 */         set(2, localDate4.getMonth() - 1);
/*  887 */         set(5, localDate4.getDayOfMonth()); return;
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     case 7: 
/*  893 */       m = this.jdate.getNormalizedYear();
/*  894 */       if ((!isTransitionYear(m)) && (!isTransitionYear(m - 1)))
/*      */       {
/*      */ 
/*  897 */         int i3 = internalGet(3);
/*  898 */         if ((i3 > 1) && (i3 < 52)) {
/*  899 */           set(3, internalGet(3));
/*  900 */           j = 7;
/*  901 */           break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  909 */       paramInt2 %= 7;
/*  910 */       if (paramInt2 == 0) {
/*  911 */         return;
/*      */       }
/*  913 */       long l4 = this.cachedFixedDate;
/*  914 */       long l8 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l4, getFirstDayOfWeek());
/*  915 */       l4 += paramInt2;
/*  916 */       if (l4 < l8) {
/*  917 */         l4 += 7L;
/*  918 */       } else if (l4 >= l8 + 7L) {
/*  919 */         l4 -= 7L;
/*      */       }
/*  921 */       LocalGregorianCalendar.Date localDate5 = getCalendarDate(l4);
/*  922 */       set(0, getEraIndex(localDate5));
/*  923 */       set(localDate5.getYear(), localDate5.getMonth() - 1, localDate5.getDayOfMonth());
/*  924 */       return;
/*      */     
/*      */ 
/*      */ 
/*      */     case 8: 
/*  929 */       i = 1;
/*  930 */       if (!isTransitionYear(this.jdate.getNormalizedYear())) {
/*  931 */         m = internalGet(5);
/*  932 */         int i4 = jcal.getMonthLength(this.jdate);
/*  933 */         i7 = i4 % 7;
/*  934 */         j = i4 / 7;
/*  935 */         int i9 = (m - 1) % 7;
/*  936 */         if (i9 < i7) {
/*  937 */           j++;
/*      */         }
/*  939 */         set(7, internalGet(7));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  944 */         long l2 = this.cachedFixedDate;
/*  945 */         long l6 = getFixedDateMonth1(this.jdate, l2);
/*  946 */         int i11 = actualMonthLength();
/*  947 */         int i13 = i11 % 7;
/*  948 */         j = i11 / 7;
/*  949 */         i15 = (int)(l2 - l6) % 7;
/*  950 */         if (i15 < i13) {
/*  951 */           j++;
/*      */         }
/*  953 */         int i16 = getRolledValue(internalGet(paramInt1), paramInt2, i, j) - 1;
/*  954 */         l2 = l6 + i16 * 7 + i15;
/*  955 */         localDate7 = getCalendarDate(l2);
/*  956 */         set(5, localDate7.getDayOfMonth()); return;
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/*  961 */     set(paramInt1, getRolledValue(internalGet(paramInt1), paramInt2, i, j));
/*      */   }
/*      */   
/*      */   public String getDisplayName(int paramInt1, int paramInt2, Locale paramLocale)
/*      */   {
/*  966 */     if (!checkDisplayNameParams(paramInt1, paramInt2, 1, 4, paramLocale, 647))
/*      */     {
/*  968 */       return null;
/*      */     }
/*      */     
/*  971 */     int i = get(paramInt1);
/*      */     
/*      */ 
/*  974 */     if ((paramInt1 == 1) && (
/*  975 */       (getBaseStyle(paramInt2) != 2) || (i != 1) || (get(0) == 0))) {
/*  976 */       return null;
/*      */     }
/*      */     
/*  979 */     String str = CalendarDataUtility.retrieveFieldValueName(getCalendarType(), paramInt1, i, paramInt2, paramLocale);
/*      */     
/*      */ 
/*      */ 
/*  983 */     if ((str == null) && (paramInt1 == 0) && (i < eras.length)) {
/*  984 */       Era localEra = eras[i];
/*  985 */       str = paramInt2 == 1 ? localEra.getAbbreviation() : localEra.getName();
/*      */     }
/*  987 */     return str;
/*      */   }
/*      */   
/*      */   public Map<String, Integer> getDisplayNames(int paramInt1, int paramInt2, Locale paramLocale)
/*      */   {
/*  992 */     if (!checkDisplayNameParams(paramInt1, paramInt2, 0, 4, paramLocale, 647))
/*      */     {
/*  994 */       return null;
/*      */     }
/*      */     
/*  997 */     Map localMap = CalendarDataUtility.retrieveFieldValueNames(getCalendarType(), paramInt1, paramInt2, paramLocale);
/*      */     
/*  999 */     if ((localMap != null) && 
/* 1000 */       (paramInt1 == 0)) {
/* 1001 */       Iterator localIterator1 = localMap.size();
/* 1002 */       Iterator localIterator2; Object localObject; if (paramInt2 == 0) {
/* 1003 */         HashSet localHashSet = new HashSet();
/*      */         
/* 1005 */         for (localIterator2 = localMap.keySet().iterator(); localIterator2.hasNext();) { localObject = (String)localIterator2.next();
/* 1006 */           localHashSet.add(localMap.get(localObject));
/*      */         }
/* 1008 */         localIterator1 = localHashSet.size();
/*      */       }
/* 1010 */       if (localIterator1 < eras.length) {
/* 1011 */         int i = getBaseStyle(paramInt2);
/* 1012 */         for (localIterator2 = localIterator1; localIterator2 < eras.length; localIterator2++) {
/* 1013 */           localObject = eras[localIterator2];
/* 1014 */           if ((i == 0) || (i == 1) || (i == 4))
/*      */           {
/* 1016 */             localMap.put(((Era)localObject).getAbbreviation(), Integer.valueOf(localIterator2));
/*      */           }
/* 1018 */           if ((i == 0) || (i == 2)) {
/* 1019 */             localMap.put(((Era)localObject).getName(), Integer.valueOf(localIterator2));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1025 */     return localMap;
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
/*      */   public int getMinimum(int paramInt)
/*      */   {
/* 1047 */     return MIN_VALUES[paramInt];
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
/*      */   public int getMaximum(int paramInt)
/*      */   {
/* 1069 */     switch (paramInt)
/*      */     {
/*      */ 
/*      */     case 1: 
/* 1073 */       LocalGregorianCalendar.Date localDate = jcal.getCalendarDate(Long.MAX_VALUE, 
/* 1074 */         getZone());
/* 1075 */       return Math.max(LEAST_MAX_VALUES[1], localDate.getYear());
/*      */     }
/*      */     
/* 1078 */     return MAX_VALUES[paramInt];
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
/*      */   public int getGreatestMinimum(int paramInt)
/*      */   {
/* 1100 */     return paramInt == 1 ? 1 : MIN_VALUES[paramInt];
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
/*      */   public int getLeastMaximum(int paramInt)
/*      */   {
/* 1122 */     switch (paramInt)
/*      */     {
/*      */     case 1: 
/* 1125 */       return Math.min(LEAST_MAX_VALUES[1], getMaximum(1));
/*      */     }
/*      */     
/* 1128 */     return LEAST_MAX_VALUES[paramInt];
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
/*      */   public int getActualMinimum(int paramInt)
/*      */   {
/* 1149 */     if (!isFieldSet(14, paramInt)) {
/* 1150 */       return getMinimum(paramInt);
/*      */     }
/*      */     
/* 1153 */     int i = 0;
/* 1154 */     JapaneseImperialCalendar localJapaneseImperialCalendar = getNormalizedCalendar();
/*      */     
/*      */ 
/* 1157 */     LocalGregorianCalendar.Date localDate1 = jcal.getCalendarDate(localJapaneseImperialCalendar.getTimeInMillis(), 
/* 1158 */       getZone());
/* 1159 */     int j = getEraIndex(localDate1);
/* 1160 */     LocalGregorianCalendar.Date localDate4; switch (paramInt)
/*      */     {
/*      */     case 1: 
/* 1163 */       if (j > 0) {
/* 1164 */         i = 1;
/* 1165 */         long l1 = eras[j].getSince(getZone());
/* 1166 */         localDate4 = jcal.getCalendarDate(l1, getZone());
/*      */         
/*      */ 
/*      */ 
/* 1170 */         localDate1.setYear(localDate4.getYear());
/* 1171 */         jcal.normalize(localDate1);
/* 1172 */         assert (localDate1.isLeapYear() == localDate4.isLeapYear());
/* 1173 */         if (getYearOffsetInMillis(localDate1) < getYearOffsetInMillis(localDate4)) {
/* 1174 */           i++;
/*      */         }
/*      */       } else {
/* 1177 */         i = getMinimum(paramInt);
/* 1178 */         LocalGregorianCalendar.Date localDate2 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/*      */         
/*      */ 
/*      */ 
/* 1182 */         int k = localDate2.getYear();
/* 1183 */         if (k > 400) {
/* 1184 */           k -= 400;
/*      */         }
/* 1186 */         localDate1.setYear(k);
/* 1187 */         jcal.normalize(localDate1);
/* 1188 */         if (getYearOffsetInMillis(localDate1) < getYearOffsetInMillis(localDate2)) {
/* 1189 */           i++;
/*      */         }
/*      */       }
/*      */       
/* 1193 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 2: 
/* 1198 */       if ((j > 1) && (localDate1.getYear() == 1)) {
/* 1199 */         long l2 = eras[j].getSince(getZone());
/* 1200 */         localDate4 = jcal.getCalendarDate(l2, getZone());
/* 1201 */         i = localDate4.getMonth() - 1;
/* 1202 */         if (localDate1.getDayOfMonth() < localDate4.getDayOfMonth())
/* 1203 */           i++;
/*      */       }
/* 1205 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 3: 
/* 1211 */       i = 1;
/* 1212 */       LocalGregorianCalendar.Date localDate3 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/*      */       
/* 1214 */       localDate3.addYear(400);
/* 1215 */       jcal.normalize(localDate3);
/* 1216 */       localDate1.setEra(localDate3.getEra());
/* 1217 */       localDate1.setYear(localDate3.getYear());
/* 1218 */       jcal.normalize(localDate1);
/*      */       
/* 1220 */       long l3 = jcal.getFixedDate(localDate3);
/* 1221 */       long l4 = jcal.getFixedDate(localDate1);
/* 1222 */       int m = getWeekNumber(l3, l4);
/* 1223 */       long l5 = l4 - 7 * (m - 1);
/* 1224 */       if (l5 >= l3) { if (l5 == l3)
/*      */         {
/* 1226 */           if (localDate1.getTimeOfDay() >= localDate3.getTimeOfDay()) break; }
/* 1227 */       } else { i++;
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/* 1232 */     return i;
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
/*      */   public int getActualMaximum(int paramInt)
/*      */   {
/* 1261 */     if ((0x1FE81 & 1 << paramInt) != 0) {
/* 1262 */       return getMaximum(paramInt);
/*      */     }
/*      */     
/* 1265 */     JapaneseImperialCalendar localJapaneseImperialCalendar = getNormalizedCalendar();
/* 1266 */     LocalGregorianCalendar.Date localDate1 = localJapaneseImperialCalendar.jdate;
/* 1267 */     int i = localDate1.getNormalizedYear();
/*      */     
/* 1269 */     int j = -1;
/* 1270 */     long l1; long l5; Object localObject2; LocalGregorianCalendar.Date localDate3; long l4; long l7; Object localObject1; int i3; int i4; int i5; switch (paramInt)
/*      */     {
/*      */     case 2: 
/* 1273 */       j = 11;
/* 1274 */       if (isTransitionYear(localDate1.getNormalizedYear()))
/*      */       {
/* 1276 */         int k = getEraIndex(localDate1);
/* 1277 */         if (localDate1.getYear() != 1) {
/* 1278 */           k++;
/* 1279 */           assert (k < eras.length);
/*      */         }
/* 1281 */         l1 = sinceFixedDates[k];
/* 1282 */         l5 = localJapaneseImperialCalendar.cachedFixedDate;
/* 1283 */         if (l5 < l1)
/*      */         {
/* 1285 */           localObject2 = (LocalGregorianCalendar.Date)localDate1.clone();
/* 1286 */           jcal.getCalendarDateFromFixedDate((CalendarDate)localObject2, l1 - 1L);
/* 1287 */           j = ((LocalGregorianCalendar.Date)localObject2).getMonth() - 1;
/*      */         }
/*      */       } else {
/* 1290 */         LocalGregorianCalendar.Date localDate2 = jcal.getCalendarDate(Long.MAX_VALUE, 
/* 1291 */           getZone());
/* 1292 */         if ((localDate1.getEra() == localDate2.getEra()) && (localDate1.getYear() == localDate2.getYear())) {
/* 1293 */           j = localDate2.getMonth() - 1;
/*      */         }
/*      */       }
/*      */       
/* 1297 */       break;
/*      */     
/*      */     case 5: 
/* 1300 */       j = jcal.getMonthLength(localDate1);
/* 1301 */       break;
/*      */     
/*      */ 
/*      */     case 6: 
/* 1305 */       if (isTransitionYear(localDate1.getNormalizedYear()))
/*      */       {
/*      */ 
/* 1308 */         int m = getEraIndex(localDate1);
/* 1309 */         if (localDate1.getYear() != 1) {
/* 1310 */           m++;
/* 1311 */           assert (m < eras.length);
/*      */         }
/* 1313 */         l1 = sinceFixedDates[m];
/* 1314 */         l5 = localJapaneseImperialCalendar.cachedFixedDate;
/* 1315 */         localObject2 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 1316 */         ((CalendarDate)localObject2).setDate(localDate1.getNormalizedYear(), 1, 1);
/* 1317 */         if (l5 < l1) {
/* 1318 */           j = (int)(l1 - gcal.getFixedDate((CalendarDate)localObject2));
/*      */         } else {
/* 1320 */           ((CalendarDate)localObject2).addYear(1);
/* 1321 */           j = (int)(gcal.getFixedDate((CalendarDate)localObject2) - l1);
/*      */         }
/*      */       } else {
/* 1324 */         localDate3 = jcal.getCalendarDate(Long.MAX_VALUE, 
/* 1325 */           getZone());
/* 1326 */         if ((localDate1.getEra() == localDate3.getEra()) && (localDate1.getYear() == localDate3.getYear())) {
/* 1327 */           l1 = jcal.getFixedDate(localDate3);
/* 1328 */           l5 = getFixedDateJan1(localDate3, l1);
/* 1329 */           j = (int)(l1 - l5) + 1;
/* 1330 */         } else if (localDate1.getYear() == getMinimum(1)) {
/* 1331 */           LocalGregorianCalendar.Date localDate6 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 1332 */           l4 = jcal.getFixedDate(localDate6);
/* 1333 */           localDate6.addYear(1);
/* 1334 */           localDate6.setMonth(1).setDayOfMonth(1);
/* 1335 */           jcal.normalize(localDate6);
/* 1336 */           l7 = jcal.getFixedDate(localDate6);
/* 1337 */           j = (int)(l7 - l4);
/*      */         } else {
/* 1339 */           j = jcal.getYearLength(localDate1);
/*      */         }
/*      */       }
/*      */       
/* 1343 */       break;
/*      */     
/*      */ 
/*      */     case 3: 
/* 1347 */       if (!isTransitionYear(localDate1.getNormalizedYear())) {
/* 1348 */         localDate3 = jcal.getCalendarDate(Long.MAX_VALUE, 
/* 1349 */           getZone());
/* 1350 */         if ((localDate1.getEra() == localDate3.getEra()) && (localDate1.getYear() == localDate3.getYear())) {
/* 1351 */           long l2 = jcal.getFixedDate(localDate3);
/* 1352 */           l5 = getFixedDateJan1(localDate3, l2);
/* 1353 */           j = getWeekNumber(l5, l2);
/* 1354 */         } else if ((localDate1.getEra() == null) && (localDate1.getYear() == getMinimum(1))) {
/* 1355 */           localObject1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/*      */           
/* 1357 */           ((CalendarDate)localObject1).addYear(400);
/* 1358 */           jcal.normalize((CalendarDate)localObject1);
/* 1359 */           localDate3.setEra(((CalendarDate)localObject1).getEra());
/* 1360 */           localDate3.setDate(((CalendarDate)localObject1).getYear() + 1, 1, 1);
/* 1361 */           jcal.normalize(localDate3);
/* 1362 */           l4 = jcal.getFixedDate((CalendarDate)localObject1);
/* 1363 */           l7 = jcal.getFixedDate(localDate3);
/* 1364 */           long l8 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l7 + 6L, 
/* 1365 */             getFirstDayOfWeek());
/* 1366 */           int i6 = (int)(l8 - l7);
/* 1367 */           if (i6 >= getMinimalDaysInFirstWeek()) {
/* 1368 */             l8 -= 7L;
/*      */           }
/* 1370 */           j = getWeekNumber(l4, l8);
/*      */         }
/*      */         else {
/* 1373 */           localObject1 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 1374 */           ((CalendarDate)localObject1).setDate(localDate1.getNormalizedYear(), 1, 1);
/* 1375 */           i3 = gcal.getDayOfWeek((CalendarDate)localObject1);
/*      */           
/* 1377 */           i3 -= getFirstDayOfWeek();
/* 1378 */           if (i3 < 0) {
/* 1379 */             i3 += 7;
/*      */           }
/* 1381 */           j = 52;
/* 1382 */           i4 = i3 + getMinimalDaysInFirstWeek() - 1;
/* 1383 */           if ((i4 == 6) || (
/* 1384 */             (localDate1.isLeapYear()) && ((i4 == 5) || (i4 == 12)))) {
/* 1385 */             j++;
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1391 */         if (localJapaneseImperialCalendar == this) {
/* 1392 */           localJapaneseImperialCalendar = (JapaneseImperialCalendar)localJapaneseImperialCalendar.clone();
/*      */         }
/* 1394 */         int n = getActualMaximum(6);
/* 1395 */         localJapaneseImperialCalendar.set(6, n);
/* 1396 */         j = localJapaneseImperialCalendar.get(3);
/* 1397 */         if ((j == 1) && (n > 7)) {
/* 1398 */           localJapaneseImperialCalendar.add(3, -1);
/* 1399 */           j = localJapaneseImperialCalendar.get(3);
/*      */         }
/*      */       }
/* 1402 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/* 1406 */       LocalGregorianCalendar.Date localDate4 = jcal.getCalendarDate(Long.MAX_VALUE, 
/* 1407 */         getZone());
/* 1408 */       if ((localDate1.getEra() != localDate4.getEra()) || (localDate1.getYear() != localDate4.getYear())) {
/* 1409 */         localObject1 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 1410 */         ((CalendarDate)localObject1).setDate(localDate1.getNormalizedYear(), localDate1.getMonth(), 1);
/* 1411 */         i3 = gcal.getDayOfWeek((CalendarDate)localObject1);
/* 1412 */         i4 = gcal.getMonthLength((CalendarDate)localObject1);
/* 1413 */         i3 -= getFirstDayOfWeek();
/* 1414 */         if (i3 < 0) {
/* 1415 */           i3 += 7;
/*      */         }
/* 1417 */         i5 = 7 - i3;
/* 1418 */         j = 3;
/* 1419 */         if (i5 >= getMinimalDaysInFirstWeek()) {
/* 1420 */           j++;
/*      */         }
/* 1422 */         i4 -= i5 + 21;
/* 1423 */         if (i4 > 0) {
/* 1424 */           j++;
/* 1425 */           if (i4 > 7) {
/* 1426 */             j++;
/*      */           }
/*      */         }
/*      */       } else {
/* 1430 */         long l3 = jcal.getFixedDate(localDate4);
/* 1431 */         long l6 = l3 - localDate4.getDayOfMonth() + 1L;
/* 1432 */         j = getWeekNumber(l6, l3);
/*      */       }
/*      */       
/* 1435 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 8: 
/* 1440 */       i3 = localDate1.getDayOfWeek();
/* 1441 */       BaseCalendar.Date localDate = (BaseCalendar.Date)localDate1.clone();
/* 1442 */       int i1 = jcal.getMonthLength(localDate);
/* 1443 */       localDate.setDayOfMonth(1);
/* 1444 */       jcal.normalize(localDate);
/* 1445 */       int i2 = localDate.getDayOfWeek();
/* 1446 */       i5 = i3 - i2;
/* 1447 */       if (i5 < 0) {
/* 1448 */         i5 += 7;
/*      */       }
/* 1450 */       i1 -= i5;
/* 1451 */       j = (i1 + 6) / 7;
/*      */       
/* 1453 */       break;
/*      */     
/*      */ 
/*      */     case 1: 
/* 1457 */       LocalGregorianCalendar.Date localDate5 = jcal.getCalendarDate(localJapaneseImperialCalendar.getTimeInMillis(), getZone());
/*      */       
/* 1459 */       i3 = getEraIndex(localDate1);
/* 1460 */       LocalGregorianCalendar.Date localDate7; if (i3 == eras.length - 1) {
/* 1461 */         localDate7 = jcal.getCalendarDate(Long.MAX_VALUE, getZone());
/* 1462 */         j = localDate7.getYear();
/*      */         
/*      */ 
/* 1465 */         if (j > 400) {
/* 1466 */           localDate5.setYear(j - 400);
/*      */         }
/*      */       } else {
/* 1469 */         localDate7 = jcal.getCalendarDate(eras[(i3 + 1)].getSince(getZone()) - 1L, 
/* 1470 */           getZone());
/* 1471 */         j = localDate7.getYear();
/*      */         
/*      */ 
/* 1474 */         localDate5.setYear(j);
/*      */       }
/* 1476 */       jcal.normalize(localDate5);
/* 1477 */       if (getYearOffsetInMillis(localDate5) > getYearOffsetInMillis(localDate7)) {
/* 1478 */         j--;
/*      */       }
/*      */       
/* 1481 */       break;
/*      */     case 7: 
/*      */     default: 
/* 1484 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */     }
/* 1486 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long getYearOffsetInMillis(CalendarDate paramCalendarDate)
/*      */   {
/* 1496 */     long l = (jcal.getDayOfYear(paramCalendarDate) - 1L) * 86400000L;
/* 1497 */     return l + paramCalendarDate.getTimeOfDay() - paramCalendarDate.getZoneOffset();
/*      */   }
/*      */   
/*      */   public Object clone() {
/* 1501 */     JapaneseImperialCalendar localJapaneseImperialCalendar = (JapaneseImperialCalendar)super.clone();
/*      */     
/* 1503 */     localJapaneseImperialCalendar.jdate = ((LocalGregorianCalendar.Date)this.jdate.clone());
/* 1504 */     localJapaneseImperialCalendar.originalFields = null;
/* 1505 */     localJapaneseImperialCalendar.zoneOffsets = null;
/* 1506 */     return localJapaneseImperialCalendar;
/*      */   }
/*      */   
/*      */   public TimeZone getTimeZone() {
/* 1510 */     TimeZone localTimeZone = super.getTimeZone();
/*      */     
/* 1512 */     this.jdate.setZone(localTimeZone);
/* 1513 */     return localTimeZone;
/*      */   }
/*      */   
/*      */   public void setTimeZone(TimeZone paramTimeZone) {
/* 1517 */     super.setTimeZone(paramTimeZone);
/*      */     
/* 1519 */     this.jdate.setZone(paramTimeZone);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1526 */   private transient long cachedFixedDate = Long.MIN_VALUE;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void computeFields()
/*      */   {
/* 1538 */     int i = 0;
/* 1539 */     if (isPartiallyNormalized())
/*      */     {
/* 1541 */       i = getSetStateFields();
/* 1542 */       int j = (i ^ 0xFFFFFFFF) & 0x1FFFF;
/* 1543 */       if ((j != 0) || (this.cachedFixedDate == Long.MIN_VALUE)) {
/* 1544 */         i |= computeFields(j, i & 0x18000);
/*      */         
/* 1546 */         assert (i == 131071);
/*      */       }
/*      */     }
/*      */     else {
/* 1550 */       i = 131071;
/* 1551 */       computeFields(i, 0);
/*      */     }
/*      */     
/* 1554 */     setFieldsComputed(i);
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
/* 1572 */     int i = 0;
/* 1573 */     TimeZone localTimeZone = getZone();
/* 1574 */     if (this.zoneOffsets == null) {
/* 1575 */       this.zoneOffsets = new int[2];
/*      */     }
/* 1577 */     if (paramInt2 != 98304) {
/* 1578 */       if ((localTimeZone instanceof ZoneInfo)) {
/* 1579 */         i = ((ZoneInfo)localTimeZone).getOffsets(this.time, this.zoneOffsets);
/*      */       } else {
/* 1581 */         i = localTimeZone.getOffset(this.time);
/* 1582 */         this.zoneOffsets[0] = localTimeZone.getRawOffset();
/* 1583 */         this.zoneOffsets[1] = (i - this.zoneOffsets[0]);
/*      */       }
/*      */     }
/* 1586 */     if (paramInt2 != 0) {
/* 1587 */       if (isFieldSet(paramInt2, 15)) {
/* 1588 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 1590 */       if (isFieldSet(paramInt2, 16)) {
/* 1591 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/* 1593 */       i = this.zoneOffsets[0] + this.zoneOffsets[1];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1599 */     long l1 = i / 86400000L;
/* 1600 */     int j = i % 86400000;
/* 1601 */     l1 += this.time / 86400000L;
/* 1602 */     j += (int)(this.time % 86400000L);
/* 1603 */     if (j >= 86400000L) {
/* 1604 */       j = (int)(j - 86400000L);
/* 1605 */       l1 += 1L;
/*      */     } else {
/* 1607 */       while (j < 0) {
/* 1608 */         j = (int)(j + 86400000L);
/* 1609 */         l1 -= 1L;
/*      */       }
/*      */     }
/* 1612 */     l1 += 719163L;
/*      */     
/*      */ 
/* 1615 */     if ((l1 != this.cachedFixedDate) || (l1 < 0L)) {
/* 1616 */       jcal.getCalendarDateFromFixedDate(this.jdate, l1);
/* 1617 */       this.cachedFixedDate = l1;
/*      */     }
/* 1619 */     int k = getEraIndex(this.jdate);
/* 1620 */     int m = this.jdate.getYear();
/*      */     
/*      */ 
/* 1623 */     internalSet(0, k);
/* 1624 */     internalSet(1, m);
/* 1625 */     int n = paramInt1 | 0x3;
/*      */     
/* 1627 */     int i1 = this.jdate.getMonth() - 1;
/* 1628 */     int i2 = this.jdate.getDayOfMonth();
/*      */     
/*      */ 
/* 1631 */     if ((paramInt1 & 0xA4) != 0)
/*      */     {
/* 1633 */       internalSet(2, i1);
/* 1634 */       internalSet(5, i2);
/* 1635 */       internalSet(7, this.jdate.getDayOfWeek());
/* 1636 */       n |= 0xA4;
/*      */     }
/*      */     int i3;
/* 1639 */     if ((paramInt1 & 0x7E00) != 0)
/*      */     {
/* 1641 */       if (j != 0) {
/* 1642 */         i3 = j / 3600000;
/* 1643 */         internalSet(11, i3);
/* 1644 */         internalSet(9, i3 / 12);
/* 1645 */         internalSet(10, i3 % 12);
/* 1646 */         int i4 = j % 3600000;
/* 1647 */         internalSet(12, i4 / 60000);
/* 1648 */         i4 %= 60000;
/* 1649 */         internalSet(13, i4 / 1000);
/* 1650 */         internalSet(14, i4 % 1000);
/*      */       } else {
/* 1652 */         internalSet(11, 0);
/* 1653 */         internalSet(9, 0);
/* 1654 */         internalSet(10, 0);
/* 1655 */         internalSet(12, 0);
/* 1656 */         internalSet(13, 0);
/* 1657 */         internalSet(14, 0);
/*      */       }
/* 1659 */       n |= 0x7E00;
/*      */     }
/*      */     
/*      */ 
/* 1663 */     if ((paramInt1 & 0x18000) != 0) {
/* 1664 */       internalSet(15, this.zoneOffsets[0]);
/* 1665 */       internalSet(16, this.zoneOffsets[1]);
/* 1666 */       n |= 0x18000;
/*      */     }
/*      */     
/* 1669 */     if ((paramInt1 & 0x158) != 0)
/*      */     {
/* 1671 */       i3 = this.jdate.getNormalizedYear();
/*      */       
/*      */ 
/* 1674 */       boolean bool = isTransitionYear(this.jdate.getNormalizedYear());
/*      */       long l2;
/*      */       int i5;
/* 1677 */       if (bool) {
/* 1678 */         l2 = getFixedDateJan1(this.jdate, l1);
/* 1679 */         i5 = (int)(l1 - l2) + 1;
/* 1680 */       } else if (i3 == MIN_VALUES[1]) {
/* 1681 */         LocalGregorianCalendar.Date localDate1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 1682 */         l2 = jcal.getFixedDate(localDate1);
/* 1683 */         i5 = (int)(l1 - l2) + 1;
/*      */       } else {
/* 1685 */         i5 = (int)jcal.getDayOfYear(this.jdate);
/* 1686 */         l2 = l1 - i5 + 1L;
/*      */       }
/*      */       
/* 1689 */       long l3 = bool ? getFixedDateMonth1(this.jdate, l1) : l1 - i2 + 1L;
/*      */       
/* 1691 */       internalSet(6, i5);
/* 1692 */       internalSet(8, (i2 - 1) / 7 + 1);
/*      */       
/* 1694 */       int i6 = getWeekNumber(l2, l1);
/*      */       
/*      */       long l4;
/*      */       long l6;
/* 1698 */       if (i6 == 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1706 */         l4 = l2 - 1L;
/*      */         
/* 1708 */         LocalGregorianCalendar.Date localDate3 = getCalendarDate(l4);
/* 1709 */         if ((!bool) && (!isTransitionYear(localDate3.getNormalizedYear()))) {
/* 1710 */           l6 = l2 - 365L;
/* 1711 */           if (localDate3.isLeapYear())
/* 1712 */             l6 -= 1L;
/*      */         } else { CalendarDate localCalendarDate2;
/* 1714 */           if (bool) {
/* 1715 */             if (this.jdate.getYear() == 1)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1721 */               if (k > 4) {
/* 1722 */                 localCalendarDate2 = eras[(k - 1)].getSinceDate();
/* 1723 */                 if (i3 == localCalendarDate2.getYear()) {
/* 1724 */                   localDate3.setMonth(localCalendarDate2.getMonth()).setDayOfMonth(localCalendarDate2.getDayOfMonth());
/*      */                 }
/*      */               } else {
/* 1727 */                 localDate3.setMonth(1).setDayOfMonth(1);
/*      */               }
/* 1729 */               jcal.normalize(localDate3);
/* 1730 */               l6 = jcal.getFixedDate(localDate3);
/*      */             } else {
/* 1732 */               l6 = l2 - 365L;
/* 1733 */               if (localDate3.isLeapYear()) {
/* 1734 */                 l6 -= 1L;
/*      */               }
/*      */             }
/*      */           } else {
/* 1738 */             localCalendarDate2 = eras[getEraIndex(this.jdate)].getSinceDate();
/* 1739 */             localDate3.setMonth(localCalendarDate2.getMonth()).setDayOfMonth(localCalendarDate2.getDayOfMonth());
/* 1740 */             jcal.normalize(localDate3);
/* 1741 */             l6 = jcal.getFixedDate(localDate3);
/*      */           } }
/* 1743 */         i6 = getWeekNumber(l6, l4);
/*      */       }
/* 1745 */       else if (!bool)
/*      */       {
/* 1747 */         if (i6 >= 52) {
/* 1748 */           l4 = l2 + 365L;
/* 1749 */           if (this.jdate.isLeapYear()) {
/* 1750 */             l4 += 1L;
/*      */           }
/* 1752 */           l6 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l4 + 6L, 
/* 1753 */             getFirstDayOfWeek());
/* 1754 */           int i8 = (int)(l6 - l4);
/* 1755 */           if ((i8 >= getMinimalDaysInFirstWeek()) && (l1 >= l6 - 7L))
/*      */           {
/* 1757 */             i6 = 1;
/*      */           }
/*      */         }
/*      */       } else {
/* 1761 */         LocalGregorianCalendar.Date localDate2 = (LocalGregorianCalendar.Date)this.jdate.clone();
/*      */         long l5;
/* 1763 */         if (this.jdate.getYear() == 1) {
/* 1764 */           localDate2.addYear(1);
/* 1765 */           localDate2.setMonth(1).setDayOfMonth(1);
/* 1766 */           l5 = jcal.getFixedDate(localDate2);
/*      */         } else {
/* 1768 */           int i7 = getEraIndex(localDate2) + 1;
/* 1769 */           CalendarDate localCalendarDate1 = eras[i7].getSinceDate();
/* 1770 */           localDate2.setEra(eras[i7]);
/* 1771 */           localDate2.setDate(1, localCalendarDate1.getMonth(), localCalendarDate1.getDayOfMonth());
/* 1772 */           jcal.normalize(localDate2);
/* 1773 */           l5 = jcal.getFixedDate(localDate2);
/*      */         }
/* 1775 */         long l7 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l5 + 6L, 
/* 1776 */           getFirstDayOfWeek());
/* 1777 */         int i9 = (int)(l7 - l5);
/* 1778 */         if ((i9 >= getMinimalDaysInFirstWeek()) && (l1 >= l7 - 7L))
/*      */         {
/* 1780 */           i6 = 1;
/*      */         }
/*      */       }
/*      */       
/* 1784 */       internalSet(3, i6);
/* 1785 */       internalSet(4, getWeekNumber(l3, l1));
/* 1786 */       n |= 0x158;
/*      */     }
/* 1788 */     return n;
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
/* 1803 */     long l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(paramLong1 + 6L, 
/* 1804 */       getFirstDayOfWeek());
/* 1805 */     int i = (int)(l - paramLong1);
/* 1806 */     assert (i <= 7);
/* 1807 */     if (i >= getMinimalDaysInFirstWeek()) {
/* 1808 */       l -= 7L;
/*      */     }
/* 1810 */     int j = (int)(paramLong2 - l);
/* 1811 */     if (j >= 0) {
/* 1812 */       return j / 7 + 1;
/*      */     }
/* 1814 */     return CalendarUtils.floorDivide(j, 7) + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void computeTime()
/*      */   {
/*      */     int j;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1828 */     if (!isLenient()) {
/* 1829 */       if (this.originalFields == null) {
/* 1830 */         this.originalFields = new int[17];
/*      */       }
/* 1832 */       for (i = 0; i < 17; i++) {
/* 1833 */         j = internalGet(i);
/* 1834 */         if (isExternallySet(i))
/*      */         {
/* 1836 */           if ((j < getMinimum(i)) || (j > getMaximum(i))) {
/* 1837 */             throw new IllegalArgumentException(getFieldName(i));
/*      */           }
/*      */         }
/* 1840 */         this.originalFields[i] = j;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1846 */     int i = selectFields();
/*      */     
/*      */ 
/*      */     int k;
/*      */     
/* 1851 */     if (isSet(0)) {
/* 1852 */       k = internalGet(0);
/* 1853 */       j = isSet(1) ? internalGet(1) : 1;
/*      */     }
/* 1855 */     else if (isSet(1)) {
/* 1856 */       k = eras.length - 1;
/* 1857 */       j = internalGet(1);
/*      */     }
/*      */     else {
/* 1860 */       k = 3;
/* 1861 */       j = 45;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1867 */     long l1 = 0L;
/* 1868 */     if (isFieldSet(i, 11)) {
/* 1869 */       l1 += internalGet(11);
/*      */     } else {
/* 1871 */       l1 += internalGet(10);
/*      */       
/* 1873 */       if (isFieldSet(i, 9)) {
/* 1874 */         l1 += 12 * internalGet(9);
/*      */       }
/*      */     }
/* 1877 */     l1 *= 60L;
/* 1878 */     l1 += internalGet(12);
/* 1879 */     l1 *= 60L;
/* 1880 */     l1 += internalGet(13);
/* 1881 */     l1 *= 1000L;
/* 1882 */     l1 += internalGet(14);
/*      */     
/*      */ 
/*      */ 
/* 1886 */     long l2 = l1 / 86400000L;
/* 1887 */     l1 %= 86400000L;
/* 1888 */     while (l1 < 0L) {
/* 1889 */       l1 += 86400000L;
/* 1890 */       l2 -= 1L;
/*      */     }
/*      */     
/*      */ 
/* 1894 */     l2 += getFixedDate(k, j, i);
/*      */     
/*      */ 
/* 1897 */     long l3 = (l2 - 719163L) * 86400000L + l1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1912 */     TimeZone localTimeZone = getZone();
/* 1913 */     if (this.zoneOffsets == null) {
/* 1914 */       this.zoneOffsets = new int[2];
/*      */     }
/* 1916 */     int m = i & 0x18000;
/* 1917 */     if (m != 98304) {
/* 1918 */       if ((localTimeZone instanceof ZoneInfo)) {
/* 1919 */         ((ZoneInfo)localTimeZone).getOffsetsByWall(l3, this.zoneOffsets);
/*      */       } else {
/* 1921 */         localTimeZone.getOffsets(l3 - localTimeZone.getRawOffset(), this.zoneOffsets);
/*      */       }
/*      */     }
/* 1924 */     if (m != 0) {
/* 1925 */       if (isFieldSet(m, 15)) {
/* 1926 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 1928 */       if (isFieldSet(m, 16)) {
/* 1929 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1934 */     l3 -= this.zoneOffsets[0] + this.zoneOffsets[1];
/*      */     
/*      */ 
/* 1937 */     this.time = l3;
/*      */     
/* 1939 */     int n = computeFields(i | getSetStateFields(), m);
/*      */     
/* 1941 */     if (!isLenient()) {
/* 1942 */       for (int i1 = 0; i1 < 17; i1++) {
/* 1943 */         if (isExternallySet(i1))
/*      */         {
/*      */ 
/* 1946 */           if (this.originalFields[i1] != internalGet(i1)) {
/* 1947 */             int i2 = internalGet(i1);
/*      */             
/* 1949 */             System.arraycopy(this.originalFields, 0, this.fields, 0, this.fields.length);
/* 1950 */             throw new IllegalArgumentException(getFieldName(i1) + "=" + i2 + ", expected " + this.originalFields[i1]);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1955 */     setFieldsNormalized(n);
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
/*      */   private long getFixedDate(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1970 */     int i = 0;
/* 1971 */     int j = 1;
/* 1972 */     if (isFieldSet(paramInt3, 2))
/*      */     {
/*      */ 
/* 1975 */       i = internalGet(2);
/*      */       
/*      */ 
/* 1978 */       if (i > 11) {
/* 1979 */         paramInt2 += i / 12;
/* 1980 */         i %= 12;
/* 1981 */       } else if (i < 0) {
/* 1982 */         localObject = new int[1];
/* 1983 */         paramInt2 += CalendarUtils.floorDivide(i, 12, (int[])localObject);
/* 1984 */         i = localObject[0];
/*      */       }
/*      */     }
/* 1987 */     else if ((paramInt2 == 1) && (paramInt1 != 0)) {
/* 1988 */       localObject = eras[paramInt1].getSinceDate();
/* 1989 */       i = ((CalendarDate)localObject).getMonth() - 1;
/* 1990 */       j = ((CalendarDate)localObject).getDayOfMonth();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1995 */     if (paramInt2 == MIN_VALUES[1]) {
/* 1996 */       localObject = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 1997 */       int k = ((CalendarDate)localObject).getMonth() - 1;
/* 1998 */       if (i < k) {
/* 1999 */         i = k;
/*      */       }
/* 2001 */       if (i == k) {
/* 2002 */         j = ((CalendarDate)localObject).getDayOfMonth();
/*      */       }
/*      */     }
/*      */     
/* 2006 */     Object localObject = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2007 */     ((LocalGregorianCalendar.Date)localObject).setEra(paramInt1 > 0 ? eras[paramInt1] : null);
/* 2008 */     ((LocalGregorianCalendar.Date)localObject).setDate(paramInt2, i + 1, j);
/* 2009 */     jcal.normalize((CalendarDate)localObject);
/*      */     
/*      */ 
/*      */ 
/* 2013 */     long l1 = jcal.getFixedDate((CalendarDate)localObject);
/*      */     int i1;
/* 2015 */     if (isFieldSet(paramInt3, 2))
/*      */     {
/* 2017 */       if (isFieldSet(paramInt3, 5))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2024 */         if (isSet(5))
/*      */         {
/*      */ 
/* 2027 */           l1 += internalGet(5);
/* 2028 */           l1 -= j;
/*      */         }
/*      */       }
/* 2031 */       else if (isFieldSet(paramInt3, 4)) {
/* 2032 */         long l2 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, 
/* 2033 */           getFirstDayOfWeek());
/*      */         
/*      */ 
/* 2036 */         if (l2 - l1 >= getMinimalDaysInFirstWeek()) {
/* 2037 */           l2 -= 7L;
/*      */         }
/* 2039 */         if (isFieldSet(paramInt3, 7)) {
/* 2040 */           l2 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, 
/* 2041 */             internalGet(7));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2046 */         l1 = l2 + 7 * (internalGet(4) - 1);
/*      */       } else {
/*      */         int m;
/* 2049 */         if (isFieldSet(paramInt3, 7)) {
/* 2050 */           m = internalGet(7);
/*      */         } else {
/* 2052 */           m = getFirstDayOfWeek();
/*      */         }
/*      */         
/*      */ 
/*      */         int n;
/*      */         
/* 2058 */         if (isFieldSet(paramInt3, 8)) {
/* 2059 */           n = internalGet(8);
/*      */         } else {
/* 2061 */           n = 1;
/*      */         }
/* 2063 */         if (n >= 0) {
/* 2064 */           l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 7 * n - 1L, m);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2069 */           i1 = monthLength(i, paramInt2) + 7 * (n + 1);
/*      */           
/* 2071 */           l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + i1 - 1L, m);
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 2078 */     else if (isFieldSet(paramInt3, 6)) {
/* 2079 */       if (isTransitionYear(((LocalGregorianCalendar.Date)localObject).getNormalizedYear())) {
/* 2080 */         l1 = getFixedDateJan1((LocalGregorianCalendar.Date)localObject, l1);
/*      */       }
/*      */       
/* 2083 */       l1 += internalGet(6);
/* 2084 */       l1 -= 1L;
/*      */     } else {
/* 2086 */       long l3 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, 
/* 2087 */         getFirstDayOfWeek());
/*      */       
/*      */ 
/* 2090 */       if (l3 - l1 >= getMinimalDaysInFirstWeek()) {
/* 2091 */         l3 -= 7L;
/*      */       }
/* 2093 */       if (isFieldSet(paramInt3, 7)) {
/* 2094 */         i1 = internalGet(7);
/* 2095 */         if (i1 != getFirstDayOfWeek()) {
/* 2096 */           l3 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, i1);
/*      */         }
/*      */       }
/*      */       
/* 2100 */       l1 = l3 + 7L * (internalGet(3) - 1L);
/*      */     }
/*      */     
/* 2103 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long getFixedDateJan1(LocalGregorianCalendar.Date paramDate, long paramLong)
/*      */   {
/* 2115 */     Era localEra = paramDate.getEra();
/* 2116 */     if ((paramDate.getEra() != null) && (paramDate.getYear() == 1)) {
/* 2117 */       for (int i = getEraIndex(paramDate); i > 0; i--) {
/* 2118 */         CalendarDate localCalendarDate = eras[i].getSinceDate();
/* 2119 */         long l = gcal.getFixedDate(localCalendarDate);
/*      */         
/* 2121 */         if (l <= paramLong)
/*      */         {
/*      */ 
/* 2124 */           return l; }
/*      */       }
/*      */     }
/* 2127 */     Gregorian.Date localDate = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2128 */     localDate.setDate(paramDate.getNormalizedYear(), 1, 1);
/* 2129 */     return gcal.getFixedDate(localDate);
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
/*      */   private long getFixedDateMonth1(LocalGregorianCalendar.Date paramDate, long paramLong)
/*      */   {
/* 2142 */     int i = getTransitionEraIndex(paramDate);
/* 2143 */     if (i != -1) {
/* 2144 */       long l = sinceFixedDates[i];
/*      */       
/*      */ 
/* 2147 */       if (l <= paramLong) {
/* 2148 */         return l;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2153 */     return paramLong - paramDate.getDayOfMonth() + 1L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static LocalGregorianCalendar.Date getCalendarDate(long paramLong)
/*      */   {
/* 2162 */     LocalGregorianCalendar.Date localDate = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2163 */     jcal.getCalendarDateFromFixedDate(localDate, paramLong);
/* 2164 */     return localDate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int monthLength(int paramInt1, int paramInt2)
/*      */   {
/* 2174 */     return CalendarUtils.isGregorianLeapYear(paramInt2) ? GregorianCalendar.LEAP_MONTH_LENGTH[paramInt1] : GregorianCalendar.MONTH_LENGTH[paramInt1];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int monthLength(int paramInt)
/*      */   {
/* 2185 */     assert (this.jdate.isNormalized());
/* 2186 */     return this.jdate.isLeapYear() ? GregorianCalendar.LEAP_MONTH_LENGTH[paramInt] : GregorianCalendar.MONTH_LENGTH[paramInt];
/*      */   }
/*      */   
/*      */   private int actualMonthLength()
/*      */   {
/* 2191 */     int i = jcal.getMonthLength(this.jdate);
/* 2192 */     int j = getTransitionEraIndex(this.jdate);
/* 2193 */     if (j == -1) {
/* 2194 */       long l = sinceFixedDates[j];
/* 2195 */       CalendarDate localCalendarDate = eras[j].getSinceDate();
/* 2196 */       if (l <= this.cachedFixedDate) {
/* 2197 */         i -= localCalendarDate.getDayOfMonth() - 1;
/*      */       } else {
/* 2199 */         i = localCalendarDate.getDayOfMonth() - 1;
/*      */       }
/*      */     }
/* 2202 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int getTransitionEraIndex(LocalGregorianCalendar.Date paramDate)
/*      */   {
/* 2214 */     int i = getEraIndex(paramDate);
/* 2215 */     CalendarDate localCalendarDate = eras[i].getSinceDate();
/* 2216 */     if ((localCalendarDate.getYear() == paramDate.getNormalizedYear()) && 
/* 2217 */       (localCalendarDate.getMonth() == paramDate.getMonth())) {
/* 2218 */       return i;
/*      */     }
/* 2220 */     if (i < eras.length - 1) {
/* 2221 */       localCalendarDate = eras[(++i)].getSinceDate();
/* 2222 */       if ((localCalendarDate.getYear() == paramDate.getNormalizedYear()) && 
/* 2223 */         (localCalendarDate.getMonth() == paramDate.getMonth())) {
/* 2224 */         return i;
/*      */       }
/*      */     }
/* 2227 */     return -1;
/*      */   }
/*      */   
/*      */   private boolean isTransitionYear(int paramInt) {
/* 2231 */     for (int i = eras.length - 1; i > 0; i--) {
/* 2232 */       int j = eras[i].getSinceDate().getYear();
/* 2233 */       if (paramInt == j) {
/* 2234 */         return true;
/*      */       }
/* 2236 */       if (paramInt > j) {
/*      */         break;
/*      */       }
/*      */     }
/* 2240 */     return false;
/*      */   }
/*      */   
/*      */   private static int getEraIndex(LocalGregorianCalendar.Date paramDate) {
/* 2244 */     Era localEra = paramDate.getEra();
/* 2245 */     for (int i = eras.length - 1; i > 0; i--) {
/* 2246 */       if (eras[i] == localEra) {
/* 2247 */         return i;
/*      */       }
/*      */     }
/* 2250 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private JapaneseImperialCalendar getNormalizedCalendar()
/*      */   {
/*      */     JapaneseImperialCalendar localJapaneseImperialCalendar;
/*      */     
/*      */ 
/* 2260 */     if (isFullyNormalized()) {
/* 2261 */       localJapaneseImperialCalendar = this;
/*      */     }
/*      */     else {
/* 2264 */       localJapaneseImperialCalendar = (JapaneseImperialCalendar)clone();
/* 2265 */       localJapaneseImperialCalendar.setLenient(true);
/* 2266 */       localJapaneseImperialCalendar.complete();
/*      */     }
/* 2268 */     return localJapaneseImperialCalendar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void pinDayOfMonth(LocalGregorianCalendar.Date paramDate)
/*      */   {
/* 2278 */     int i = paramDate.getYear();
/* 2279 */     int j = paramDate.getDayOfMonth();
/* 2280 */     if (i != getMinimum(1)) {
/* 2281 */       paramDate.setDayOfMonth(1);
/* 2282 */       jcal.normalize(paramDate);
/* 2283 */       int k = jcal.getMonthLength(paramDate);
/* 2284 */       if (j > k) {
/* 2285 */         paramDate.setDayOfMonth(k);
/*      */       } else {
/* 2287 */         paramDate.setDayOfMonth(j);
/*      */       }
/* 2289 */       jcal.normalize(paramDate);
/*      */     } else {
/* 2291 */       LocalGregorianCalendar.Date localDate1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 2292 */       LocalGregorianCalendar.Date localDate2 = jcal.getCalendarDate(this.time, getZone());
/* 2293 */       long l = localDate2.getTimeOfDay();
/*      */       
/* 2295 */       localDate2.addYear(400);
/* 2296 */       localDate2.setMonth(paramDate.getMonth());
/* 2297 */       localDate2.setDayOfMonth(1);
/* 2298 */       jcal.normalize(localDate2);
/* 2299 */       int m = jcal.getMonthLength(localDate2);
/* 2300 */       if (j > m) {
/* 2301 */         localDate2.setDayOfMonth(m);
/*      */       }
/* 2303 */       else if (j < localDate1.getDayOfMonth()) {
/* 2304 */         localDate2.setDayOfMonth(localDate1.getDayOfMonth());
/*      */       } else {
/* 2306 */         localDate2.setDayOfMonth(j);
/*      */       }
/*      */       
/* 2309 */       if ((localDate2.getDayOfMonth() == localDate1.getDayOfMonth()) && (l < localDate1.getTimeOfDay())) {
/* 2310 */         localDate2.setDayOfMonth(Math.min(j + 1, m));
/*      */       }
/*      */       
/* 2313 */       paramDate.setDate(i, localDate2.getMonth(), localDate2.getDayOfMonth());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int getRolledValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 2322 */     assert ((paramInt1 >= paramInt3) && (paramInt1 <= paramInt4));
/* 2323 */     int i = paramInt4 - paramInt3 + 1;
/* 2324 */     paramInt2 %= i;
/* 2325 */     int j = paramInt1 + paramInt2;
/* 2326 */     if (j > paramInt4) {
/* 2327 */       j -= i;
/* 2328 */     } else if (j < paramInt3) {
/* 2329 */       j += i;
/*      */     }
/* 2331 */     assert ((j >= paramInt3) && (j <= paramInt4));
/* 2332 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int internalGetEra()
/*      */   {
/* 2340 */     return isSet(0) ? internalGet(0) : eras.length - 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 2348 */     paramObjectInputStream.defaultReadObject();
/* 2349 */     if (this.jdate == null) {
/* 2350 */       this.jdate = jcal.newCalendarDate(getZone());
/* 2351 */       this.cachedFixedDate = Long.MIN_VALUE;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/JapaneseImperialCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */