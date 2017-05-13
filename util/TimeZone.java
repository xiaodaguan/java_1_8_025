/*     */ package java.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.time.ZoneId;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.util.calendar.ZoneInfo;
/*     */ import sun.util.calendar.ZoneInfoFile;
/*     */ import sun.util.locale.provider.TimeZoneNameUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TimeZone
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   public static final int SHORT = 0;
/*     */   public static final int LONG = 1;
/*     */   private static final int ONE_MINUTE = 60000;
/*     */   private static final int ONE_HOUR = 3600000;
/*     */   private static final int ONE_DAY = 86400000;
/*     */   static final long serialVersionUID = 3581463369166924961L;
/*     */   
/*     */   public abstract int getOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   public int getOffset(long paramLong)
/*     */   {
/* 209 */     if (inDaylightTime(new Date(paramLong))) {
/* 210 */       return getRawOffset() + getDSTSavings();
/*     */     }
/* 212 */     return getRawOffset();
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
/*     */   int getOffsets(long paramLong, int[] paramArrayOfInt)
/*     */   {
/* 232 */     int i = getRawOffset();
/* 233 */     int j = 0;
/* 234 */     if (inDaylightTime(new Date(paramLong))) {
/* 235 */       j = getDSTSavings();
/*     */     }
/* 237 */     if (paramArrayOfInt != null) {
/* 238 */       paramArrayOfInt[0] = i;
/* 239 */       paramArrayOfInt[1] = j;
/*     */     }
/* 241 */     return i + j;
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
/*     */   public abstract void setRawOffset(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int getRawOffset();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getID()
/*     */   {
/* 282 */     return this.ID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setID(String paramString)
/*     */   {
/* 292 */     if (paramString == null) {
/* 293 */       throw new NullPointerException();
/*     */     }
/* 295 */     this.ID = paramString;
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
/*     */   public final String getDisplayName()
/*     */   {
/* 315 */     return getDisplayName(false, 1, 
/* 316 */       Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public final String getDisplayName(Locale paramLocale)
/*     */   {
/* 335 */     return getDisplayName(false, 1, paramLocale);
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
/*     */   public final String getDisplayName(boolean paramBoolean, int paramInt)
/*     */   {
/* 363 */     return getDisplayName(paramBoolean, paramInt, 
/* 364 */       Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public String getDisplayName(boolean paramBoolean, int paramInt, Locale paramLocale)
/*     */   {
/* 396 */     if ((paramInt != 0) && (paramInt != 1)) {
/* 397 */       throw new IllegalArgumentException("Illegal style: " + paramInt);
/*     */     }
/* 399 */     String str1 = getID();
/* 400 */     String str2 = TimeZoneNameUtility.retrieveDisplayName(str1, paramBoolean, paramInt, paramLocale);
/* 401 */     if (str2 != null) {
/* 402 */       return str2;
/*     */     }
/*     */     
/* 405 */     if ((str1.startsWith("GMT")) && (str1.length() > 3)) {
/* 406 */       i = str1.charAt(3);
/* 407 */       if ((i == 43) || (i == 45)) {
/* 408 */         return str1;
/*     */       }
/*     */     }
/* 411 */     int i = getRawOffset();
/* 412 */     if (paramBoolean) {
/* 413 */       i += getDSTSavings();
/*     */     }
/* 415 */     return ZoneInfoFile.toCustomID(i);
/*     */   }
/*     */   
/*     */   private static String[] getDisplayNames(String paramString, Locale paramLocale) {
/* 419 */     return TimeZoneNameUtility.retrieveDisplayNames(paramString, paramLocale);
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
/*     */   public int getDSTSavings()
/*     */   {
/* 449 */     if (useDaylightTime()) {
/* 450 */       return 3600000;
/*     */     }
/* 452 */     return 0;
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
/*     */   public abstract boolean useDaylightTime();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean observesDaylightTime()
/*     */   {
/* 491 */     return (useDaylightTime()) || (inDaylightTime(new Date()));
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
/*     */   public abstract boolean inDaylightTime(Date paramDate);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized TimeZone getTimeZone(String paramString)
/*     */   {
/* 516 */     return getTimeZone(paramString, true);
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
/*     */   public static TimeZone getTimeZone(ZoneId paramZoneId)
/*     */   {
/* 529 */     String str = paramZoneId.getId();
/* 530 */     int i = str.charAt(0);
/* 531 */     if ((i == 43) || (i == 45)) {
/* 532 */       str = "GMT" + str;
/* 533 */     } else if ((i == 90) && (str.length() == 1)) {
/* 534 */       str = "UTC";
/*     */     }
/* 536 */     return getTimeZone(str, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ZoneId toZoneId()
/*     */   {
/* 547 */     String str = getID();
/* 548 */     if ((ZoneInfoFile.useOldMapping()) && (str.length() == 3)) {
/* 549 */       if ("EST".equals(str))
/* 550 */         return ZoneId.of("America/New_York");
/* 551 */       if ("MST".equals(str))
/* 552 */         return ZoneId.of("America/Denver");
/* 553 */       if ("HST".equals(str))
/* 554 */         return ZoneId.of("America/Honolulu");
/*     */     }
/* 556 */     return ZoneId.of(str, ZoneId.SHORT_IDS);
/*     */   }
/*     */   
/*     */   private static TimeZone getTimeZone(String paramString, boolean paramBoolean) {
/* 560 */     Object localObject = ZoneInfo.getTimeZone(paramString);
/* 561 */     if (localObject == null) {
/* 562 */       localObject = parseCustomTimeZone(paramString);
/* 563 */       if ((localObject == null) && (paramBoolean)) {
/* 564 */         localObject = new ZoneInfo("GMT", 0);
/*     */       }
/*     */     }
/* 567 */     return (TimeZone)localObject;
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
/*     */   public static synchronized String[] getAvailableIDs(int paramInt)
/*     */   {
/* 580 */     return ZoneInfo.getAvailableIDs(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized String[] getAvailableIDs()
/*     */   {
/* 588 */     return ZoneInfo.getAvailableIDs();
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
/*     */   private static native String getSystemTimeZoneID(String paramString1, String paramString2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static native String getSystemGMTOffsetID();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static TimeZone getDefault()
/*     */   {
/* 626 */     return (TimeZone)getDefaultRef().clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static TimeZone getDefaultRef()
/*     */   {
/* 634 */     TimeZone localTimeZone = defaultTimeZone;
/* 635 */     if (localTimeZone == null)
/*     */     {
/* 637 */       localTimeZone = setDefaultZone();
/* 638 */       assert (localTimeZone != null);
/*     */     }
/*     */     
/* 641 */     return localTimeZone;
/*     */   }
/*     */   
/*     */ 
/*     */   private static synchronized TimeZone setDefaultZone()
/*     */   {
/* 647 */     Object localObject1 = (String)AccessController.doPrivileged(new GetPropertyAction("user.timezone"));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 652 */     if ((localObject1 == null) || (((String)localObject1).isEmpty())) {
/* 653 */       localObject2 = (String)AccessController.doPrivileged(new GetPropertyAction("user.country"));
/*     */       
/* 655 */       String str = (String)AccessController.doPrivileged(new GetPropertyAction("java.home"));
/*     */       try
/*     */       {
/* 658 */         localObject1 = getSystemTimeZoneID(str, (String)localObject2);
/* 659 */         if (localObject1 == null) {
/* 660 */           localObject1 = "GMT";
/*     */         }
/*     */       } catch (NullPointerException localNullPointerException) {
/* 663 */         localObject1 = "GMT";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 669 */     TimeZone localTimeZone = getTimeZone((String)localObject1, false);
/*     */     
/* 671 */     if (localTimeZone == null)
/*     */     {
/*     */ 
/*     */ 
/* 675 */       localObject2 = getSystemGMTOffsetID();
/* 676 */       if (localObject2 != null) {
/* 677 */         localObject1 = localObject2;
/*     */       }
/* 679 */       localTimeZone = getTimeZone((String)localObject1, true);
/*     */     }
/* 681 */     assert (localTimeZone != null);
/*     */     
/* 683 */     Object localObject2 = localObject1;
/* 684 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 687 */         System.setProperty("user.timezone", this.val$id);
/* 688 */         return null;
/*     */       }
/*     */       
/* 691 */     });
/* 692 */     defaultTimeZone = localTimeZone;
/* 693 */     return localTimeZone;
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
/*     */   public static void setDefault(TimeZone paramTimeZone)
/*     */   {
/* 711 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 712 */     if (localSecurityManager != null) {
/* 713 */       localSecurityManager.checkPermission(new PropertyPermission("user.timezone", "write"));
/*     */     }
/*     */     
/* 716 */     defaultTimeZone = paramTimeZone;
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
/*     */   public boolean hasSameRules(TimeZone paramTimeZone)
/*     */   {
/* 730 */     return (paramTimeZone != null) && (getRawOffset() == paramTimeZone.getRawOffset()) && (useDaylightTime() == paramTimeZone.useDaylightTime());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 741 */       TimeZone localTimeZone = (TimeZone)super.clone();
/* 742 */       localTimeZone.ID = this.ID;
/* 743 */       return localTimeZone;
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 745 */       throw new InternalError(localCloneNotSupportedException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 752 */   static final TimeZone NO_TIMEZONE = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private String ID;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static volatile TimeZone defaultTimeZone;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String GMT_ID = "GMT";
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int GMT_ID_LENGTH = 3;
/*     */   
/*     */ 
/*     */ 
/*     */   private static volatile TimeZone mainAppContextDefault;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final TimeZone parseCustomTimeZone(String paramString)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/* 786 */     if (((i = paramString.length()) < 5) || 
/* 787 */       (paramString.indexOf("GMT") != 0)) {
/* 788 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 796 */     ZoneInfo localZoneInfo = ZoneInfoFile.getZoneInfo(paramString);
/* 797 */     if (localZoneInfo != null) {
/* 798 */       return localZoneInfo;
/*     */     }
/*     */     
/* 801 */     int j = 3;
/* 802 */     int k = 0;
/* 803 */     int m = paramString.charAt(j++);
/* 804 */     if (m == 45) {
/* 805 */       k = 1;
/* 806 */     } else if (m != 43) {
/* 807 */       return null;
/*     */     }
/*     */     
/* 810 */     int n = 0;
/* 811 */     int i1 = 0;
/* 812 */     int i2 = 0;
/* 813 */     int i3 = 0;
/* 814 */     while (j < i) {
/* 815 */       m = paramString.charAt(j++);
/* 816 */       if (m == 58) {
/* 817 */         if (i2 > 0) {
/* 818 */           return null;
/*     */         }
/* 820 */         if (i3 > 2) {
/* 821 */           return null;
/*     */         }
/* 823 */         n = i1;
/* 824 */         i2++;
/* 825 */         i1 = 0;
/* 826 */         i3 = 0;
/*     */       }
/*     */       else {
/* 829 */         if ((m < 48) || (m > 57)) {
/* 830 */           return null;
/*     */         }
/* 832 */         i1 = i1 * 10 + (m - 48);
/* 833 */         i3++;
/*     */       } }
/* 835 */     if (j != i) {
/* 836 */       return null;
/*     */     }
/* 838 */     if (i2 == 0) {
/* 839 */       if (i3 <= 2) {
/* 840 */         n = i1;
/* 841 */         i1 = 0;
/*     */       } else {
/* 843 */         n = i1 / 100;
/* 844 */         i1 %= 100;
/*     */       }
/*     */     }
/* 847 */     else if (i3 != 2) {
/* 848 */       return null;
/*     */     }
/*     */     
/* 851 */     if ((n > 23) || (i1 > 59)) {
/* 852 */       return null;
/*     */     }
/* 854 */     int i4 = (n * 60 + i1) * 60 * 1000;
/*     */     
/* 856 */     if (i4 == 0) {
/* 857 */       localZoneInfo = ZoneInfoFile.getZoneInfo("GMT");
/* 858 */       if (k != 0) {
/* 859 */         localZoneInfo.setID("GMT-00:00");
/*     */       } else {
/* 861 */         localZoneInfo.setID("GMT+00:00");
/*     */       }
/*     */     } else {
/* 864 */       localZoneInfo = ZoneInfoFile.getCustomTimeZone(paramString, k != 0 ? -i4 : i4);
/*     */     }
/* 866 */     return localZoneInfo;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/TimeZone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */