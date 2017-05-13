/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormat;
/*      */ import java.time.Instant;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.calendar.BaseCalendar.Date;
/*      */ import sun.util.calendar.CalendarDate;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
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
/*      */ public class Date
/*      */   implements Serializable, Cloneable, Comparable<Date>
/*      */ {
/*  135 */   private static final BaseCalendar gcal = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BaseCalendar jcal;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient long fastTime;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient BaseCalendar.Date cdate;
/*      */   
/*      */ 
/*      */ 
/*      */   private static int defaultCenturyStart;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 7523967970034938905L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Date()
/*      */   {
/*  165 */     this(System.currentTimeMillis());
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
/*      */   public Date(long paramLong)
/*      */   {
/*  178 */     this.fastTime = paramLong;
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
/*      */   @Deprecated
/*      */   public Date(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  197 */     this(paramInt1, paramInt2, paramInt3, 0, 0, 0);
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
/*      */   @Deprecated
/*      */   public Date(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/*  220 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0);
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
/*      */   @Deprecated
/*      */   public Date(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */   {
/*  244 */     int i = paramInt1 + 1900;
/*      */     
/*  246 */     if (paramInt2 >= 12) {
/*  247 */       i += paramInt2 / 12;
/*  248 */       paramInt2 %= 12;
/*  249 */     } else if (paramInt2 < 0) {
/*  250 */       i += CalendarUtils.floorDivide(paramInt2, 12);
/*  251 */       paramInt2 = CalendarUtils.mod(paramInt2, 12);
/*      */     }
/*  253 */     BaseCalendar localBaseCalendar = getCalendarSystem(i);
/*  254 */     this.cdate = ((BaseCalendar.Date)localBaseCalendar.newCalendarDate(TimeZone.getDefaultRef()));
/*  255 */     this.cdate.setNormalizedDate(i, paramInt2 + 1, paramInt3).setTimeOfDay(paramInt4, paramInt5, paramInt6, 0);
/*  256 */     getTimeImpl();
/*  257 */     this.cdate = null;
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
/*      */   @Deprecated
/*      */   public Date(String paramString)
/*      */   {
/*  274 */     this(parse(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*  281 */     Date localDate = null;
/*      */     try {
/*  283 */       localDate = (Date)super.clone();
/*  284 */       if (this.cdate != null) {
/*  285 */         localDate.cdate = ((BaseCalendar.Date)this.cdate.clone());
/*      */       }
/*      */     } catch (CloneNotSupportedException localCloneNotSupportedException) {}
/*  288 */     return localDate;
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
/*      */   @Deprecated
/*      */   public static long UTC(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */   {
/*  319 */     int i = paramInt1 + 1900;
/*      */     
/*  321 */     if (paramInt2 >= 12) {
/*  322 */       i += paramInt2 / 12;
/*  323 */       paramInt2 %= 12;
/*  324 */     } else if (paramInt2 < 0) {
/*  325 */       i += CalendarUtils.floorDivide(paramInt2, 12);
/*  326 */       paramInt2 = CalendarUtils.mod(paramInt2, 12);
/*      */     }
/*  328 */     int j = paramInt2 + 1;
/*  329 */     BaseCalendar localBaseCalendar = getCalendarSystem(i);
/*  330 */     BaseCalendar.Date localDate = (BaseCalendar.Date)localBaseCalendar.newCalendarDate(null);
/*  331 */     localDate.setNormalizedDate(i, j, paramInt3).setTimeOfDay(paramInt4, paramInt5, paramInt6, 0);
/*      */     
/*      */ 
/*      */ 
/*  335 */     Date localDate1 = new Date(0L);
/*  336 */     localDate1.normalize(localDate);
/*  337 */     return localDate1.fastTime;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static long parse(String paramString)
/*      */   {
/*  455 */     int i = Integer.MIN_VALUE;
/*  456 */     int j = -1;
/*  457 */     int k = -1;
/*  458 */     int m = -1;
/*  459 */     int n = -1;
/*  460 */     int i1 = -1;
/*  461 */     int i2 = -1;
/*  462 */     int i3 = -1;
/*  463 */     int i4 = 0;
/*  464 */     int i5 = -1;
/*  465 */     int i6 = -1;
/*  466 */     int i7 = -1;
/*  467 */     int i8 = 0;
/*      */     
/*      */ 
/*  470 */     if (paramString != null)
/*      */     {
/*  472 */       int i9 = paramString.length();
/*  473 */       for (;;) { if (i4 >= i9) break label783;
/*  474 */         i3 = paramString.charAt(i4);
/*  475 */         i4++;
/*  476 */         if ((i3 > 32) && (i3 != 44)) {
/*      */           int i10;
/*  478 */           if (i3 == 40) {
/*  479 */             for (i10 = 1; 
/*  480 */                 i4 < i9; 
/*      */                 
/*      */ 
/*  483 */                 i10++)
/*      */             {
/*  481 */               i3 = paramString.charAt(i4);
/*  482 */               i4++;
/*  483 */               if (i3 != 40) break label126; }
/*      */             continue; label126: if (i3 != 41) break;
/*  485 */             i10--; if (i10 > 0) break;
/*  486 */             continue;
/*      */           }
/*      */           
/*      */ 
/*  490 */           if ((48 <= i3) && (i3 <= 57)) {
/*  491 */             i5 = i3 - 48;
/*  492 */             while ((i4 < i9) && ('0' <= (i3 = paramString.charAt(i4))) && (i3 <= 57)) {
/*  493 */               i5 = i5 * 10 + i3 - 48;
/*  494 */               i4++;
/*      */             }
/*  496 */             if ((i8 == 43) || ((i8 == 45) && (i != Integer.MIN_VALUE)))
/*      */             {
/*  498 */               if (i5 < 24) {
/*  499 */                 i5 *= 60;
/*      */               } else
/*  501 */                 i5 = i5 % 100 + i5 / 100 * 60;
/*  502 */               if (i8 == 43)
/*  503 */                 i5 = -i5;
/*  504 */               if ((i7 != 0) && (i7 != -1))
/*      */                 break label1000;
/*  506 */               i7 = i5;
/*  507 */             } else if (i5 >= 70) {
/*  508 */               if (i != Integer.MIN_VALUE)
/*      */                 break label1000;
/*  510 */               if ((i3 > 32) && (i3 != 44) && (i3 != 47) && (i4 < i9))
/*      */                 break label1000;
/*  512 */               i = i5;
/*      */ 
/*      */             }
/*  515 */             else if (i3 == 58) {
/*  516 */               if (m < 0) {
/*  517 */                 m = (byte)i5;
/*  518 */               } else { if (n >= 0) break label1000;
/*  519 */                 n = (byte)i5;
/*      */               }
/*      */             }
/*  522 */             else if (i3 == 47) {
/*  523 */               if (j < 0) {
/*  524 */                 j = (byte)(i5 - 1);
/*  525 */               } else { if (k >= 0) break label1000;
/*  526 */                 k = (byte)i5;
/*      */               }
/*      */             } else {
/*  529 */               if ((i4 < i9) && (i3 != 44) && (i3 > 32) && (i3 != 45))
/*      */                 break label1000;
/*  531 */               if ((m >= 0) && (n < 0)) {
/*  532 */                 n = (byte)i5;
/*  533 */               } else if ((n >= 0) && (i1 < 0)) {
/*  534 */                 i1 = (byte)i5;
/*  535 */               } else if (k < 0) {
/*  536 */                 k = (byte)i5;
/*      */               } else {
/*  538 */                 if ((i != Integer.MIN_VALUE) || (j < 0) || (k < 0)) break label1000;
/*  539 */                 i = i5;
/*      */               }
/*      */             }
/*  542 */             i8 = 0;
/*  543 */           } else if ((i3 == 47) || (i3 == 58) || (i3 == 43) || (i3 == 45)) {
/*  544 */             i8 = i3;
/*      */           } else {
/*  546 */             i10 = i4 - 1;
/*  547 */             while (i4 < i9) {
/*  548 */               i3 = paramString.charAt(i4);
/*  549 */               if (((65 > i3) || (i3 > 90)) && ((97 > i3) || (i3 > 122)))
/*      */                 break;
/*  551 */               i4++;
/*      */             }
/*  553 */             if (i4 <= i10 + 1) {
/*      */               break label1000;
/*      */             }
/*  556 */             int i11 = wtb.length; do { i11--; if (i11 < 0) break;
/*  557 */             } while (!wtb[i11].regionMatches(true, 0, paramString, i10, i4 - i10));
/*  558 */             int i12 = ttb[i11];
/*  559 */             if (i12 != 0) {
/*  560 */               if (i12 == 1) {
/*  561 */                 if ((m > 12) || (m < 1))
/*      */                   break label1000;
/*  563 */                 if (m < 12)
/*  564 */                   m += 12;
/*  565 */               } else if (i12 == 14) {
/*  566 */                 if ((m > 12) || (m < 1))
/*      */                   break label1000;
/*  568 */                 if (m == 12)
/*  569 */                   m = 0;
/*  570 */               } else if (i12 <= 13) {
/*  571 */                 if (j >= 0) break label1000;
/*  572 */                 j = (byte)(i12 - 2);
/*      */               }
/*      */               else
/*      */               {
/*  576 */                 i7 = i12 - 10000;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  581 */             if (i11 < 0)
/*      */               break label1000;
/*  583 */             i8 = 0;
/*      */           } } }
/*      */       label783:
/*  586 */       if ((i != Integer.MIN_VALUE) && (j >= 0) && (k >= 0))
/*      */       {
/*      */ 
/*  589 */         if (i < 100) {
/*  590 */           synchronized (Date.class) {
/*  591 */             if (defaultCenturyStart == 0) {
/*  592 */               defaultCenturyStart = gcal.getCalendarDate().getYear() - 80;
/*      */             }
/*      */           }
/*  595 */           i += defaultCenturyStart / 100 * 100;
/*  596 */           if (i < defaultCenturyStart) i += 100;
/*      */         }
/*  598 */         if (i1 < 0)
/*  599 */           i1 = 0;
/*  600 */         if (n < 0)
/*  601 */           n = 0;
/*  602 */         if (m < 0)
/*  603 */           m = 0;
/*  604 */         ??? = getCalendarSystem(i);
/*  605 */         if (i7 == -1) {
/*  606 */           localDate = (BaseCalendar.Date)((BaseCalendar)???).newCalendarDate(TimeZone.getDefaultRef());
/*  607 */           localDate.setDate(i, j + 1, k);
/*  608 */           localDate.setTimeOfDay(m, n, i1, 0);
/*  609 */           return ((BaseCalendar)???).getTime(localDate);
/*      */         }
/*  611 */         BaseCalendar.Date localDate = (BaseCalendar.Date)((BaseCalendar)???).newCalendarDate(null);
/*  612 */         localDate.setDate(i, j + 1, k);
/*  613 */         localDate.setTimeOfDay(m, n, i1, 0);
/*  614 */         return ((BaseCalendar)???).getTime(localDate) + i7 * 60000;
/*      */       } }
/*      */     label1000:
/*  617 */     throw new IllegalArgumentException(); }
/*      */   
/*  619 */   private static final String[] wtb = { "am", "pm", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  628 */   private static final int[] ttb = { 14, 1, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 10000, 10000, 10000, 10300, 10240, 10360, 10300, 10420, 10360, 10480, 10420 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int getYear()
/*      */   {
/*  651 */     return normalize().getYear() - 1900;
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
/*      */   @Deprecated
/*      */   public void setYear(int paramInt)
/*      */   {
/*  671 */     getCalendarDate().setNormalizedYear(paramInt + 1900);
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
/*      */   @Deprecated
/*      */   public int getMonth()
/*      */   {
/*  687 */     return normalize().getMonth() - 1;
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
/*      */   @Deprecated
/*      */   public void setMonth(int paramInt)
/*      */   {
/*  706 */     int i = 0;
/*  707 */     if (paramInt >= 12) {
/*  708 */       i = paramInt / 12;
/*  709 */       paramInt %= 12;
/*  710 */     } else if (paramInt < 0) {
/*  711 */       i = CalendarUtils.floorDivide(paramInt, 12);
/*  712 */       paramInt = CalendarUtils.mod(paramInt, 12);
/*      */     }
/*  714 */     BaseCalendar.Date localDate = getCalendarDate();
/*  715 */     if (i != 0) {
/*  716 */       localDate.setNormalizedYear(localDate.getNormalizedYear() + i);
/*      */     }
/*  718 */     localDate.setMonth(paramInt + 1);
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
/*      */   @Deprecated
/*      */   public int getDate()
/*      */   {
/*  736 */     return normalize().getDayOfMonth();
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
/*      */   @Deprecated
/*      */   public void setDate(int paramInt)
/*      */   {
/*  756 */     getCalendarDate().setDayOfMonth(paramInt);
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
/*      */   @Deprecated
/*      */   public int getDay()
/*      */   {
/*  775 */     return normalize().getDayOfWeek() - 1;
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
/*      */   @Deprecated
/*      */   public int getHours()
/*      */   {
/*  792 */     return normalize().getHours();
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
/*      */   @Deprecated
/*      */   public void setHours(int paramInt)
/*      */   {
/*  809 */     getCalendarDate().setHours(paramInt);
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
/*      */   @Deprecated
/*      */   public int getMinutes()
/*      */   {
/*  824 */     return normalize().getMinutes();
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
/*      */   @Deprecated
/*      */   public void setMinutes(int paramInt)
/*      */   {
/*  841 */     getCalendarDate().setMinutes(paramInt);
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
/*      */   @Deprecated
/*      */   public int getSeconds()
/*      */   {
/*  857 */     return normalize().getSeconds();
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
/*      */   @Deprecated
/*      */   public void setSeconds(int paramInt)
/*      */   {
/*  874 */     getCalendarDate().setSeconds(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getTime()
/*      */   {
/*  885 */     return getTimeImpl();
/*      */   }
/*      */   
/*      */   private final long getTimeImpl() {
/*  889 */     if ((this.cdate != null) && (!this.cdate.isNormalized())) {
/*  890 */       normalize();
/*      */     }
/*  892 */     return this.fastTime;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTime(long paramLong)
/*      */   {
/*  902 */     this.fastTime = paramLong;
/*  903 */     this.cdate = null;
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
/*      */   public boolean before(Date paramDate)
/*      */   {
/*  917 */     return getMillisOf(this) < getMillisOf(paramDate);
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
/*      */   public boolean after(Date paramDate)
/*      */   {
/*  931 */     return getMillisOf(this) > getMillisOf(paramDate);
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  950 */     return ((paramObject instanceof Date)) && (getTime() == ((Date)paramObject).getTime());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final long getMillisOf(Date paramDate)
/*      */   {
/*  958 */     if ((paramDate.cdate == null) || (paramDate.cdate.isNormalized())) {
/*  959 */       return paramDate.fastTime;
/*      */     }
/*  961 */     BaseCalendar.Date localDate = (BaseCalendar.Date)paramDate.cdate.clone();
/*  962 */     return gcal.getTime(localDate);
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
/*      */   public int compareTo(Date paramDate)
/*      */   {
/*  977 */     long l1 = getMillisOf(this);
/*  978 */     long l2 = getMillisOf(paramDate);
/*  979 */     return l1 == l2 ? 0 : l1 < l2 ? -1 : 1;
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
/*  994 */     long l = getTime();
/*  995 */     return (int)l ^ (int)(l >> 32);
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
/*      */   public String toString()
/*      */   {
/* 1030 */     BaseCalendar.Date localDate = normalize();
/* 1031 */     StringBuilder localStringBuilder = new StringBuilder(28);
/* 1032 */     int i = localDate.getDayOfWeek();
/* 1033 */     if (i == 1) {
/* 1034 */       i = 8;
/*      */     }
/* 1036 */     convertToAbbr(localStringBuilder, wtb[i]).append(' ');
/* 1037 */     convertToAbbr(localStringBuilder, wtb[(localDate.getMonth() - 1 + 2 + 7)]).append(' ');
/* 1038 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getDayOfMonth(), 2).append(' ');
/*      */     
/* 1040 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getHours(), 2).append(':');
/* 1041 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getMinutes(), 2).append(':');
/* 1042 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getSeconds(), 2).append(' ');
/* 1043 */     TimeZone localTimeZone = localDate.getZone();
/* 1044 */     if (localTimeZone != null) {
/* 1045 */       localStringBuilder.append(localTimeZone.getDisplayName(localDate.isDaylightTime(), 0, Locale.US));
/*      */     } else {
/* 1047 */       localStringBuilder.append("GMT");
/*      */     }
/* 1049 */     localStringBuilder.append(' ').append(localDate.getYear());
/* 1050 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final StringBuilder convertToAbbr(StringBuilder paramStringBuilder, String paramString)
/*      */   {
/* 1059 */     paramStringBuilder.append(Character.toUpperCase(paramString.charAt(0)));
/* 1060 */     paramStringBuilder.append(paramString.charAt(1)).append(paramString.charAt(2));
/* 1061 */     return paramStringBuilder;
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
/*      */   @Deprecated
/*      */   public String toLocaleString()
/*      */   {
/* 1082 */     DateFormat localDateFormat = DateFormat.getDateTimeInstance();
/* 1083 */     return localDateFormat.format(this);
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
/*      */   @Deprecated
/*      */   public String toGMTString()
/*      */   {
/* 1120 */     long l = getTime();
/* 1121 */     BaseCalendar localBaseCalendar = getCalendarSystem(l);
/*      */     
/* 1123 */     BaseCalendar.Date localDate = (BaseCalendar.Date)localBaseCalendar.getCalendarDate(getTime(), (TimeZone)null);
/* 1124 */     StringBuilder localStringBuilder = new StringBuilder(32);
/* 1125 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getDayOfMonth(), 1).append(' ');
/* 1126 */     convertToAbbr(localStringBuilder, wtb[(localDate.getMonth() - 1 + 2 + 7)]).append(' ');
/* 1127 */     localStringBuilder.append(localDate.getYear()).append(' ');
/* 1128 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getHours(), 2).append(':');
/* 1129 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getMinutes(), 2).append(':');
/* 1130 */     CalendarUtils.sprintf0d(localStringBuilder, localDate.getSeconds(), 2);
/* 1131 */     localStringBuilder.append(" GMT");
/* 1132 */     return localStringBuilder.toString();
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
/*      */   @Deprecated
/*      */   public int getTimezoneOffset()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1170 */     if (this.cdate == null) {
/* 1171 */       TimeZone localTimeZone = TimeZone.getDefaultRef();
/* 1172 */       if ((localTimeZone instanceof ZoneInfo)) {
/* 1173 */         i = ((ZoneInfo)localTimeZone).getOffsets(this.fastTime, null);
/*      */       } else {
/* 1175 */         i = localTimeZone.getOffset(this.fastTime);
/*      */       }
/*      */     } else {
/* 1178 */       normalize();
/* 1179 */       i = this.cdate.getZoneOffset();
/*      */     }
/* 1181 */     return -i / 60000;
/*      */   }
/*      */   
/*      */   private final BaseCalendar.Date getCalendarDate() {
/* 1185 */     if (this.cdate == null) {
/* 1186 */       BaseCalendar localBaseCalendar = getCalendarSystem(this.fastTime);
/* 1187 */       this.cdate = ((BaseCalendar.Date)localBaseCalendar.getCalendarDate(this.fastTime, 
/* 1188 */         TimeZone.getDefaultRef()));
/*      */     }
/* 1190 */     return this.cdate;
/*      */   }
/*      */   
/*      */   private final BaseCalendar.Date normalize() {
/* 1194 */     if (this.cdate == null) {
/* 1195 */       localObject = getCalendarSystem(this.fastTime);
/* 1196 */       this.cdate = ((BaseCalendar.Date)((BaseCalendar)localObject).getCalendarDate(this.fastTime, 
/* 1197 */         TimeZone.getDefaultRef()));
/* 1198 */       return this.cdate;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1203 */     if (!this.cdate.isNormalized()) {
/* 1204 */       this.cdate = normalize(this.cdate);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1209 */     Object localObject = TimeZone.getDefaultRef();
/* 1210 */     if (localObject != this.cdate.getZone()) {
/* 1211 */       this.cdate.setZone((TimeZone)localObject);
/* 1212 */       BaseCalendar localBaseCalendar = getCalendarSystem(this.cdate);
/* 1213 */       localBaseCalendar.getCalendarDate(this.fastTime, this.cdate);
/*      */     }
/* 1215 */     return this.cdate;
/*      */   }
/*      */   
/*      */   private final BaseCalendar.Date normalize(BaseCalendar.Date paramDate)
/*      */   {
/* 1220 */     int i = paramDate.getNormalizedYear();
/* 1221 */     int j = paramDate.getMonth();
/* 1222 */     int k = paramDate.getDayOfMonth();
/* 1223 */     int m = paramDate.getHours();
/* 1224 */     int n = paramDate.getMinutes();
/* 1225 */     int i1 = paramDate.getSeconds();
/* 1226 */     int i2 = paramDate.getMillis();
/* 1227 */     TimeZone localTimeZone = paramDate.getZone();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1237 */     if ((i == 1582) || (i > 280000000) || (i < -280000000)) {
/* 1238 */       if (localTimeZone == null) {
/* 1239 */         localTimeZone = TimeZone.getTimeZone("GMT");
/*      */       }
/* 1241 */       localObject = new GregorianCalendar(localTimeZone);
/* 1242 */       ((GregorianCalendar)localObject).clear();
/* 1243 */       ((GregorianCalendar)localObject).set(14, i2);
/* 1244 */       ((GregorianCalendar)localObject).set(i, j - 1, k, m, n, i1);
/* 1245 */       this.fastTime = ((GregorianCalendar)localObject).getTimeInMillis();
/* 1246 */       localBaseCalendar = getCalendarSystem(this.fastTime);
/* 1247 */       paramDate = (BaseCalendar.Date)localBaseCalendar.getCalendarDate(this.fastTime, localTimeZone);
/* 1248 */       return paramDate;
/*      */     }
/*      */     
/* 1251 */     Object localObject = getCalendarSystem(i);
/* 1252 */     if (localObject != getCalendarSystem(paramDate)) {
/* 1253 */       paramDate = (BaseCalendar.Date)((BaseCalendar)localObject).newCalendarDate(localTimeZone);
/* 1254 */       paramDate.setNormalizedDate(i, j, k).setTimeOfDay(m, n, i1, i2);
/*      */     }
/*      */     
/* 1257 */     this.fastTime = ((BaseCalendar)localObject).getTime(paramDate);
/*      */     
/*      */ 
/*      */ 
/* 1261 */     BaseCalendar localBaseCalendar = getCalendarSystem(this.fastTime);
/* 1262 */     if (localBaseCalendar != localObject) {
/* 1263 */       paramDate = (BaseCalendar.Date)localBaseCalendar.newCalendarDate(localTimeZone);
/* 1264 */       paramDate.setNormalizedDate(i, j, k).setTimeOfDay(m, n, i1, i2);
/* 1265 */       this.fastTime = localBaseCalendar.getTime(paramDate);
/*      */     }
/* 1267 */     return paramDate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final BaseCalendar getCalendarSystem(int paramInt)
/*      */   {
/* 1278 */     if (paramInt >= 1582) {
/* 1279 */       return gcal;
/*      */     }
/* 1281 */     return getJulianCalendar();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final BaseCalendar getCalendarSystem(long paramLong)
/*      */   {
/* 1288 */     if ((paramLong >= 0L) || 
/*      */     
/* 1290 */       (paramLong >= -12219292800000L - TimeZone.getDefaultRef().getOffset(paramLong))) {
/* 1291 */       return gcal;
/*      */     }
/* 1293 */     return getJulianCalendar();
/*      */   }
/*      */   
/*      */   private static final BaseCalendar getCalendarSystem(BaseCalendar.Date paramDate) {
/* 1297 */     if (jcal == null) {
/* 1298 */       return gcal;
/*      */     }
/* 1300 */     if (paramDate.getEra() != null) {
/* 1301 */       return jcal;
/*      */     }
/* 1303 */     return gcal;
/*      */   }
/*      */   
/*      */   private static final synchronized BaseCalendar getJulianCalendar() {
/* 1307 */     if (jcal == null) {
/* 1308 */       jcal = (BaseCalendar)CalendarSystem.forName("julian");
/*      */     }
/* 1310 */     return jcal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1323 */     paramObjectOutputStream.writeLong(getTimeImpl());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1332 */     this.fastTime = paramObjectInputStream.readLong();
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
/*      */   public static Date from(Instant paramInstant)
/*      */   {
/*      */     try
/*      */     {
/* 1357 */       return new Date(paramInstant.toEpochMilli());
/*      */     } catch (ArithmeticException localArithmeticException) {
/* 1359 */       throw new IllegalArgumentException(localArithmeticException);
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
/*      */   public Instant toInstant()
/*      */   {
/* 1374 */     return Instant.ofEpochMilli(getTime());
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Date.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */