/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Locale.Category;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SimpleTimeZone;
/*      */ import java.util.SortedMap;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.ZoneInfoFile;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SimpleDateFormat
/*      */   extends DateFormat
/*      */ {
/*      */   static final long serialVersionUID = 4774881970558875024L;
/*      */   static final int currentSerialVersion = 1;
/*  446 */   private int serialVersionOnStream = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String pattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient NumberFormat originalNumberFormat;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient String originalNumberPattern;
/*      */   
/*      */ 
/*      */ 
/*  465 */   private transient char minusSign = '-';
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  471 */   private transient boolean hasFollowingMinusSign = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  476 */   private transient boolean forceStandaloneForm = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient char[] compiledPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int TAG_QUOTE_ASCII_CHAR = 100;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int TAG_QUOTE_CHARS = 101;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient char zeroDigit;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private DateFormatSymbols formatData;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Date defaultCenturyStart;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient int defaultCenturyStartYear;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MILLIS_PER_MINUTE = 60000;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String GMT = "GMT";
/*      */   
/*      */ 
/*      */ 
/*  524 */   private static final ConcurrentMap<Locale, NumberFormat> cachedNumberFormatData = new ConcurrentHashMap(3);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Locale locale;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   transient boolean useDateFormatSymbols;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SimpleDateFormat()
/*      */   {
/*  556 */     this("", Locale.getDefault(Locale.Category.FORMAT));
/*  557 */     applyPatternImpl(LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(this.locale)
/*  558 */       .getDateTimePattern(3, 3, this.calendar));
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
/*      */   public SimpleDateFormat(String paramString)
/*      */   {
/*  580 */     this(paramString, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public SimpleDateFormat(String paramString, Locale paramLocale)
/*      */   {
/*  597 */     if ((paramString == null) || (paramLocale == null)) {
/*  598 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  601 */     initializeCalendar(paramLocale);
/*  602 */     this.pattern = paramString;
/*  603 */     this.formatData = DateFormatSymbols.getInstanceRef(paramLocale);
/*  604 */     this.locale = paramLocale;
/*  605 */     initialize(paramLocale);
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
/*      */   public SimpleDateFormat(String paramString, DateFormatSymbols paramDateFormatSymbols)
/*      */   {
/*  619 */     if ((paramString == null) || (paramDateFormatSymbols == null)) {
/*  620 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  623 */     this.pattern = paramString;
/*  624 */     this.formatData = ((DateFormatSymbols)paramDateFormatSymbols.clone());
/*  625 */     this.locale = Locale.getDefault(Locale.Category.FORMAT);
/*  626 */     initializeCalendar(this.locale);
/*  627 */     initialize(this.locale);
/*  628 */     this.useDateFormatSymbols = true;
/*      */   }
/*      */   
/*      */ 
/*      */   private void initialize(Locale paramLocale)
/*      */   {
/*  634 */     this.compiledPattern = compile(this.pattern);
/*      */     
/*      */ 
/*  637 */     this.numberFormat = ((NumberFormat)cachedNumberFormatData.get(paramLocale));
/*  638 */     if (this.numberFormat == null) {
/*  639 */       this.numberFormat = NumberFormat.getIntegerInstance(paramLocale);
/*  640 */       this.numberFormat.setGroupingUsed(false);
/*      */       
/*      */ 
/*  643 */       cachedNumberFormatData.putIfAbsent(paramLocale, this.numberFormat);
/*      */     }
/*  645 */     this.numberFormat = ((NumberFormat)this.numberFormat.clone());
/*      */     
/*  647 */     initializeDefaultCentury();
/*      */   }
/*      */   
/*      */   private void initializeCalendar(Locale paramLocale) {
/*  651 */     if (this.calendar == null) {
/*  652 */       assert (paramLocale != null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  657 */       this.calendar = Calendar.getInstance(TimeZone.getDefault(), paramLocale);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private char[] compile(String paramString)
/*      */   {
/*  726 */     int i = paramString.length();
/*  727 */     int j = 0;
/*  728 */     StringBuilder localStringBuilder1 = new StringBuilder(i * 2);
/*  729 */     StringBuilder localStringBuilder2 = null;
/*  730 */     int k = 0;int m = 0;
/*  731 */     int n = -1;int i1 = -1;
/*      */     
/*  733 */     for (int i2 = 0; i2 < i; i2++) {
/*  734 */       char c1 = paramString.charAt(i2);
/*      */       int i3;
/*  736 */       char c2; if (c1 == '\'')
/*      */       {
/*      */ 
/*  739 */         if (i2 + 1 < i) {
/*  740 */           c1 = paramString.charAt(i2 + 1);
/*  741 */           if (c1 == '\'') {
/*  742 */             i2++;
/*  743 */             if (k != 0) {
/*  744 */               encode(n, k, localStringBuilder1);
/*  745 */               m++;
/*  746 */               i1 = n;
/*  747 */               n = -1;
/*  748 */               k = 0;
/*      */             }
/*  750 */             if (j != 0) {
/*  751 */               localStringBuilder2.append(c1); continue;
/*      */             }
/*  753 */             localStringBuilder1.append((char)(0x6400 | c1));
/*      */             
/*  755 */             continue;
/*      */           }
/*      */         }
/*  758 */         if (j == 0) {
/*  759 */           if (k != 0) {
/*  760 */             encode(n, k, localStringBuilder1);
/*  761 */             m++;
/*  762 */             i1 = n;
/*  763 */             n = -1;
/*  764 */             k = 0;
/*      */           }
/*  766 */           if (localStringBuilder2 == null) {
/*  767 */             localStringBuilder2 = new StringBuilder(i);
/*      */           } else {
/*  769 */             localStringBuilder2.setLength(0);
/*      */           }
/*  771 */           j = 1;
/*      */         } else {
/*  773 */           i3 = localStringBuilder2.length();
/*  774 */           if (i3 == 1) {
/*  775 */             c2 = localStringBuilder2.charAt(0);
/*  776 */             if (c2 < '') {
/*  777 */               localStringBuilder1.append((char)(0x6400 | c2));
/*      */             } else {
/*  779 */               localStringBuilder1.append('攁');
/*  780 */               localStringBuilder1.append(c2);
/*      */             }
/*      */           } else {
/*  783 */             encode(101, i3, localStringBuilder1);
/*  784 */             localStringBuilder1.append(localStringBuilder2);
/*      */           }
/*  786 */           j = 0;
/*      */         }
/*      */         
/*      */       }
/*  790 */       else if (j != 0) {
/*  791 */         localStringBuilder2.append(c1);
/*      */ 
/*      */       }
/*  794 */       else if (((c1 < 'a') || (c1 > 'z')) && ((c1 < 'A') || (c1 > 'Z'))) {
/*  795 */         if (k != 0) {
/*  796 */           encode(n, k, localStringBuilder1);
/*  797 */           m++;
/*  798 */           i1 = n;
/*  799 */           n = -1;
/*  800 */           k = 0;
/*      */         }
/*  802 */         if (c1 < '')
/*      */         {
/*  804 */           localStringBuilder1.append((char)(0x6400 | c1));
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  809 */           for (i3 = i2 + 1; i3 < i; i3++) {
/*  810 */             c2 = paramString.charAt(i3);
/*  811 */             if ((c2 == '\'') || ((c2 >= 'a') && (c2 <= 'z')) || ((c2 >= 'A') && (c2 <= 'Z'))) {
/*      */               break;
/*      */             }
/*      */           }
/*  815 */           localStringBuilder1.append((char)(0x6500 | i3 - i2));
/*  816 */           for (; i2 < i3; i2++) {
/*  817 */             localStringBuilder1.append(paramString.charAt(i2));
/*      */           }
/*  819 */           i2--;
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  825 */         if ((i3 = "GyMdkHmsSEDFwWahKzZYuXL".indexOf(c1)) == -1) {
/*  826 */           throw new IllegalArgumentException("Illegal pattern character '" + c1 + "'");
/*      */         }
/*      */         
/*  829 */         if ((n == -1) || (n == i3)) {
/*  830 */           n = i3;
/*  831 */           k++;
/*      */         }
/*      */         else {
/*  834 */           encode(n, k, localStringBuilder1);
/*  835 */           m++;
/*  836 */           i1 = n;
/*  837 */           n = i3;
/*  838 */           k = 1;
/*      */         }
/*      */       } }
/*  841 */     if (j != 0) {
/*  842 */       throw new IllegalArgumentException("Unterminated quote");
/*      */     }
/*      */     
/*  845 */     if (k != 0) {
/*  846 */       encode(n, k, localStringBuilder1);
/*  847 */       m++;
/*  848 */       i1 = n;
/*      */     }
/*      */     
/*  851 */     this.forceStandaloneForm = ((m == 1) && (i1 == 2));
/*      */     
/*      */ 
/*  854 */     i2 = localStringBuilder1.length();
/*  855 */     char[] arrayOfChar = new char[i2];
/*  856 */     localStringBuilder1.getChars(0, i2, arrayOfChar, 0);
/*  857 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void encode(int paramInt1, int paramInt2, StringBuilder paramStringBuilder)
/*      */   {
/*  864 */     if ((paramInt1 == 21) && (paramInt2 >= 4)) {
/*  865 */       throw new IllegalArgumentException("invalid ISO 8601 format: length=" + paramInt2);
/*      */     }
/*  867 */     if (paramInt2 < 255) {
/*  868 */       paramStringBuilder.append((char)(paramInt1 << 8 | paramInt2));
/*      */     } else {
/*  870 */       paramStringBuilder.append((char)(paramInt1 << 8 | 0xFF));
/*  871 */       paramStringBuilder.append((char)(paramInt2 >>> 16));
/*  872 */       paramStringBuilder.append((char)(paramInt2 & 0xFFFF));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void initializeDefaultCentury()
/*      */   {
/*  880 */     this.calendar.setTimeInMillis(System.currentTimeMillis());
/*  881 */     this.calendar.add(1, -80);
/*  882 */     parseAmbiguousDatesAsAfter(this.calendar.getTime());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void parseAmbiguousDatesAsAfter(Date paramDate)
/*      */   {
/*  889 */     this.defaultCenturyStart = paramDate;
/*  890 */     this.calendar.setTime(paramDate);
/*  891 */     this.defaultCenturyStartYear = this.calendar.get(1);
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
/*      */   public void set2DigitYearStart(Date paramDate)
/*      */   {
/*  904 */     parseAmbiguousDatesAsAfter(new Date(paramDate.getTime()));
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
/*      */   public Date get2DigitYearStart()
/*      */   {
/*  917 */     return (Date)this.defaultCenturyStart.clone();
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
/*      */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  935 */     paramFieldPosition.beginIndex = (paramFieldPosition.endIndex = 0);
/*  936 */     return format(paramDate, paramStringBuffer, paramFieldPosition.getFieldDelegate());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate)
/*      */   {
/*  943 */     this.calendar.setTime(paramDate);
/*      */     
/*  945 */     boolean bool = useDateFormatSymbols();
/*      */     
/*  947 */     for (int i = 0; i < this.compiledPattern.length;) {
/*  948 */       int j = this.compiledPattern[i] >>> '\b';
/*  949 */       int k = this.compiledPattern[(i++)] & 0xFF;
/*  950 */       if (k == 255) {
/*  951 */         k = this.compiledPattern[(i++)] << '\020';
/*  952 */         k |= this.compiledPattern[(i++)];
/*      */       }
/*      */       
/*  955 */       switch (j) {
/*      */       case 100: 
/*  957 */         paramStringBuffer.append((char)k);
/*  958 */         break;
/*      */       
/*      */       case 101: 
/*  961 */         paramStringBuffer.append(this.compiledPattern, i, k);
/*  962 */         i += k;
/*  963 */         break;
/*      */       
/*      */       default: 
/*  966 */         subFormat(j, k, paramFieldDelegate, paramStringBuffer, bool);
/*      */       }
/*      */       
/*      */     }
/*  970 */     return paramStringBuffer;
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
/*      */   public AttributedCharacterIterator formatToCharacterIterator(Object paramObject)
/*      */   {
/*  992 */     StringBuffer localStringBuffer = new StringBuffer();
/*  993 */     CharacterIteratorFieldDelegate localCharacterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
/*      */     
/*      */ 
/*  996 */     if ((paramObject instanceof Date)) {
/*  997 */       format((Date)paramObject, localStringBuffer, localCharacterIteratorFieldDelegate);
/*      */     }
/*  999 */     else if ((paramObject instanceof Number)) {
/* 1000 */       format(new Date(((Number)paramObject).longValue()), localStringBuffer, localCharacterIteratorFieldDelegate);
/*      */     } else {
/* 1002 */       if (paramObject == null) {
/* 1003 */         throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
/*      */       }
/*      */       
/*      */ 
/* 1007 */       throw new IllegalArgumentException("Cannot format given Object as a Date");
/*      */     }
/*      */     
/* 1010 */     return localCharacterIteratorFieldDelegate.getIterator(localStringBuffer.toString());
/*      */   }
/*      */   
/*      */ 
/* 1014 */   private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = { 0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 15, 17, 1000, 15, 2 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1041 */   private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 1, 9, 17, 2 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1068 */   private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID = { DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.TIME_ZONE, DateFormat.Field.MONTH };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void subFormat(int paramInt1, int paramInt2, Format.FieldDelegate paramFieldDelegate, StringBuffer paramStringBuffer, boolean paramBoolean)
/*      */   {
/* 1101 */     int i = Integer.MAX_VALUE;
/* 1102 */     String str = null;
/* 1103 */     int j = paramStringBuffer.length();
/*      */     
/* 1105 */     int k = PATTERN_INDEX_TO_CALENDAR_FIELD[paramInt1];
/*      */     int m;
/* 1107 */     if (k == 17) {
/* 1108 */       if (this.calendar.isWeekDateSupported()) {
/* 1109 */         m = this.calendar.getWeekYear();
/*      */       }
/*      */       else {
/* 1112 */         paramInt1 = 1;
/* 1113 */         k = PATTERN_INDEX_TO_CALENDAR_FIELD[paramInt1];
/* 1114 */         m = this.calendar.get(k);
/*      */       }
/* 1116 */     } else if (k == 1000) {
/* 1117 */       m = CalendarBuilder.toISODayOfWeek(this.calendar.get(7));
/*      */     } else {
/* 1119 */       m = this.calendar.get(k);
/*      */     }
/*      */     
/* 1122 */     int n = paramInt2 >= 4 ? 2 : 1;
/* 1123 */     if ((!paramBoolean) && (k < 15) && (paramInt1 != 22))
/*      */     {
/* 1125 */       str = this.calendar.getDisplayName(k, n, this.locale);
/*      */     }
/*      */     
/*      */ 
/*      */     String[] arrayOfString;
/*      */     
/*      */ 
/* 1132 */     switch (paramInt1) {
/*      */     case 0: 
/* 1134 */       if (paramBoolean) {
/* 1135 */         arrayOfString = this.formatData.getEras();
/* 1136 */         if (m < arrayOfString.length) {
/* 1137 */           str = arrayOfString[m];
/*      */         }
/*      */       }
/* 1140 */       if (str == null) {
/* 1141 */         str = "";
/*      */       }
/*      */       
/*      */       break;
/*      */     case 1: 
/*      */     case 19: 
/* 1147 */       if ((this.calendar instanceof GregorianCalendar)) {
/* 1148 */         if (paramInt2 != 2) {
/* 1149 */           zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */         } else {
/* 1151 */           zeroPaddingNumber(m, 2, 2, paramStringBuffer);
/*      */         }
/*      */       }
/* 1154 */       else if (str == null) {
/* 1155 */         zeroPaddingNumber(m, n == 2 ? 1 : paramInt2, i, paramStringBuffer);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       break;
/*      */     case 2: 
/* 1162 */       if (paramBoolean)
/*      */       {
/* 1164 */         if (paramInt2 >= 4) {
/* 1165 */           arrayOfString = this.formatData.getMonths();
/* 1166 */           str = arrayOfString[m];
/* 1167 */         } else if (paramInt2 == 3) {
/* 1168 */           arrayOfString = this.formatData.getShortMonths();
/* 1169 */           str = arrayOfString[m];
/*      */         }
/*      */       }
/* 1172 */       else if (paramInt2 < 3) {
/* 1173 */         str = null;
/* 1174 */       } else if (this.forceStandaloneForm) {
/* 1175 */         str = this.calendar.getDisplayName(k, n | 0x8000, this.locale);
/* 1176 */         if (str == null) {
/* 1177 */           str = this.calendar.getDisplayName(k, n, this.locale);
/*      */         }
/*      */       }
/*      */       
/* 1181 */       if (str == null) {
/* 1182 */         zeroPaddingNumber(m + 1, paramInt2, i, paramStringBuffer);
/*      */       }
/*      */       
/*      */       break;
/*      */     case 22: 
/* 1187 */       assert (str == null);
/* 1188 */       if (this.locale == null)
/*      */       {
/* 1190 */         if (paramInt2 >= 4) {
/* 1191 */           arrayOfString = this.formatData.getMonths();
/* 1192 */           str = arrayOfString[m];
/* 1193 */         } else if (paramInt2 == 3) {
/* 1194 */           arrayOfString = this.formatData.getShortMonths();
/* 1195 */           str = arrayOfString[m];
/*      */         }
/*      */       }
/* 1198 */       else if (paramInt2 >= 3) {
/* 1199 */         str = this.calendar.getDisplayName(k, n | 0x8000, this.locale);
/*      */       }
/*      */       
/* 1202 */       if (str == null) {
/* 1203 */         zeroPaddingNumber(m + 1, paramInt2, i, paramStringBuffer);
/*      */       }
/*      */       
/*      */       break;
/*      */     case 4: 
/* 1208 */       if (str == null) {
/* 1209 */         if (m == 0) {
/* 1210 */           zeroPaddingNumber(this.calendar.getMaximum(11) + 1, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */         else {
/* 1213 */           zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */       }
/*      */       
/*      */       break;
/*      */     case 9: 
/* 1219 */       if (paramBoolean)
/*      */       {
/* 1221 */         if (paramInt2 >= 4) {
/* 1222 */           arrayOfString = this.formatData.getWeekdays();
/* 1223 */           str = arrayOfString[m];
/*      */         } else {
/* 1225 */           arrayOfString = this.formatData.getShortWeekdays();
/* 1226 */           str = arrayOfString[m];
/*      */         } }
/* 1228 */       break;
/*      */     
/*      */ 
/*      */     case 14: 
/* 1232 */       if (paramBoolean) {
/* 1233 */         arrayOfString = this.formatData.getAmPmStrings();
/* 1234 */         str = arrayOfString[m]; }
/* 1235 */       break;
/*      */     
/*      */ 
/*      */     case 15: 
/* 1239 */       if (str == null) {
/* 1240 */         if (m == 0) {
/* 1241 */           zeroPaddingNumber(this.calendar.getLeastMaximum(10) + 1, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */         else {
/* 1244 */           zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */       }
/*      */       
/*      */       break;
/*      */     case 17: 
/* 1250 */       if (str == null) { int i3;
/* 1251 */         if ((this.formatData.locale == null) || (this.formatData.isZoneStringsSet))
/*      */         {
/* 1253 */           int i1 = this.formatData.getZoneIndex(this.calendar.getTimeZone().getID());
/* 1254 */           if (i1 == -1)
/*      */           {
/* 1256 */             m = this.calendar.get(15) + this.calendar.get(16);
/* 1257 */             paramStringBuffer.append(ZoneInfoFile.toCustomID(m));
/*      */           } else {
/* 1259 */             i3 = this.calendar.get(16) == 0 ? 1 : 3;
/* 1260 */             if (paramInt2 < 4)
/*      */             {
/* 1262 */               i3++;
/*      */             }
/* 1264 */             String[][] arrayOfString1 = this.formatData.getZoneStringsWrapper();
/* 1265 */             paramStringBuffer.append(arrayOfString1[i1][i3]);
/*      */           }
/*      */         } else {
/* 1268 */           TimeZone localTimeZone = this.calendar.getTimeZone();
/* 1269 */           i3 = this.calendar.get(16) != 0 ? 1 : 0;
/* 1270 */           int i5 = paramInt2 < 4 ? 0 : 1;
/* 1271 */           paramStringBuffer.append(localTimeZone.getDisplayName(i3, i5, this.formatData.locale)); } }
/* 1272 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 18: 
/* 1278 */       m = (this.calendar.get(15) + this.calendar.get(16)) / 60000;
/*      */       
/* 1280 */       i2 = 4;
/* 1281 */       if (m >= 0) {
/* 1282 */         paramStringBuffer.append('+');
/*      */       } else {
/* 1284 */         i2++;
/*      */       }
/*      */       
/* 1287 */       int i4 = m / 60 * 100 + m % 60;
/* 1288 */       CalendarUtils.sprintf0d(paramStringBuffer, i4, i2);
/* 1289 */       break;
/*      */     
/*      */ 
/*      */     case 21: 
/* 1293 */       m = this.calendar.get(15) + this.calendar.get(16);
/*      */       
/* 1295 */       if (m == 0) {
/* 1296 */         paramStringBuffer.append('Z');
/*      */       }
/*      */       else
/*      */       {
/* 1300 */         m /= 60000;
/* 1301 */         if (m >= 0) {
/* 1302 */           paramStringBuffer.append('+');
/*      */         } else {
/* 1304 */           paramStringBuffer.append('-');
/* 1305 */           m = -m;
/*      */         }
/*      */         
/* 1308 */         CalendarUtils.sprintf0d(paramStringBuffer, m / 60, 2);
/* 1309 */         if (paramInt2 != 1)
/*      */         {
/*      */ 
/*      */ 
/* 1313 */           if (paramInt2 == 3) {
/* 1314 */             paramStringBuffer.append(':');
/*      */           }
/* 1316 */           CalendarUtils.sprintf0d(paramStringBuffer, m % 60, 2); } }
/* 1317 */       break;
/*      */     
/*      */     case 3: 
/*      */     case 5: 
/*      */     case 6: 
/*      */     case 7: 
/*      */     case 8: 
/*      */     case 10: 
/*      */     case 11: 
/*      */     case 12: 
/*      */     case 13: 
/*      */     case 16: 
/*      */     case 20: 
/*      */     default: 
/* 1331 */       if (str == null) {
/* 1332 */         zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/* 1337 */     if (str != null) {
/* 1338 */       paramStringBuffer.append(str);
/*      */     }
/*      */     
/* 1341 */     int i2 = PATTERN_INDEX_TO_DATE_FORMAT_FIELD[paramInt1];
/* 1342 */     DateFormat.Field localField = PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID[paramInt1];
/*      */     
/* 1344 */     paramFieldDelegate.formatted(i2, localField, localField, j, paramStringBuffer.length(), paramStringBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void zeroPaddingNumber(int paramInt1, int paramInt2, int paramInt3, StringBuffer paramStringBuffer)
/*      */   {
/*      */     try
/*      */     {
/* 1357 */       if (this.zeroDigit == 0) {
/* 1358 */         this.zeroDigit = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getZeroDigit();
/*      */       }
/* 1360 */       if (paramInt1 >= 0) {
/* 1361 */         if ((paramInt1 < 100) && (paramInt2 >= 1) && (paramInt2 <= 2)) {
/* 1362 */           if (paramInt1 < 10) {
/* 1363 */             if (paramInt2 == 2) {
/* 1364 */               paramStringBuffer.append(this.zeroDigit);
/*      */             }
/* 1366 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1));
/*      */           } else {
/* 1368 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 10));
/* 1369 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 % 10));
/*      */           }
/* 1371 */           return; }
/* 1372 */         if ((paramInt1 >= 1000) && (paramInt1 < 10000)) {
/* 1373 */           if (paramInt2 == 4) {
/* 1374 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 1000));
/* 1375 */             paramInt1 %= 1000;
/* 1376 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 100));
/* 1377 */             paramInt1 %= 100;
/* 1378 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 10));
/* 1379 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 % 10));
/* 1380 */             return;
/*      */           }
/* 1382 */           if ((paramInt2 == 2) && (paramInt3 == 2)) {
/* 1383 */             zeroPaddingNumber(paramInt1 % 100, 2, 2, paramStringBuffer);
/* 1384 */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/* 1391 */     this.numberFormat.setMinimumIntegerDigits(paramInt2);
/* 1392 */     this.numberFormat.setMaximumIntegerDigits(paramInt3);
/* 1393 */     this.numberFormat.format(paramInt1, paramStringBuffer, DontCareFieldPosition.INSTANCE);
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
/*      */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*      */   {
/* 1435 */     checkNegativeNumberExpression();
/*      */     
/* 1437 */     int i = paramParsePosition.index;
/* 1438 */     int j = i;
/* 1439 */     int k = paramString.length();
/*      */     
/* 1441 */     boolean[] arrayOfBoolean = { false };
/*      */     
/* 1443 */     CalendarBuilder localCalendarBuilder = new CalendarBuilder();
/*      */     
/* 1445 */     for (int m = 0; m < this.compiledPattern.length;) {
/* 1446 */       int n = this.compiledPattern[m] >>> '\b';
/* 1447 */       int i1 = this.compiledPattern[(m++)] & 0xFF;
/* 1448 */       if (i1 == 255) {
/* 1449 */         i1 = this.compiledPattern[(m++)] << '\020';
/* 1450 */         i1 |= this.compiledPattern[(m++)];
/*      */       }
/*      */       
/* 1453 */       switch (n) {
/*      */       case 100: 
/* 1455 */         if ((i >= k) || (paramString.charAt(i) != (char)i1)) {
/* 1456 */           paramParsePosition.index = j;
/* 1457 */           paramParsePosition.errorIndex = i;
/* 1458 */           return null;
/*      */         }
/* 1460 */         i++;
/* 1461 */         break;
/*      */       case 101: 
/*      */       default: 
/* 1464 */         while (i1-- > 0) {
/* 1465 */           if ((i >= k) || (paramString.charAt(i) != this.compiledPattern[(m++)])) {
/* 1466 */             paramParsePosition.index = j;
/* 1467 */             paramParsePosition.errorIndex = i;
/* 1468 */             return null;
/*      */           }
/* 1470 */           i++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1480 */           boolean bool1 = false;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1490 */           boolean bool2 = false;
/*      */           
/* 1492 */           if (m < this.compiledPattern.length) {
/* 1493 */             int i2 = this.compiledPattern[m] >>> '\b';
/* 1494 */             if ((i2 != 100) && (i2 != 101))
/*      */             {
/* 1496 */               bool1 = true;
/*      */             }
/*      */             
/* 1499 */             if ((this.hasFollowingMinusSign) && ((i2 == 100) || (i2 == 101)))
/*      */             {
/*      */               int i3;
/*      */               
/* 1503 */               if (i2 == 100) {
/* 1504 */                 i3 = this.compiledPattern[m] & 0xFF;
/*      */               } else {
/* 1506 */                 i3 = this.compiledPattern[(m + 1)];
/*      */               }
/*      */               
/* 1509 */               if (i3 == this.minusSign) {
/* 1510 */                 bool2 = true;
/*      */               }
/*      */             }
/*      */           }
/* 1514 */           i = subParse(paramString, i, n, i1, bool1, arrayOfBoolean, paramParsePosition, bool2, localCalendarBuilder);
/*      */           
/*      */ 
/* 1517 */           if (i < 0) {
/* 1518 */             paramParsePosition.index = j;
/* 1519 */             return null;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1528 */     paramParsePosition.index = i;
/*      */     Date localDate;
/*      */     try
/*      */     {
/* 1532 */       localDate = localCalendarBuilder.establish(this.calendar).getTime();
/*      */       
/*      */ 
/* 1535 */       if ((arrayOfBoolean[0] != 0) && 
/* 1536 */         (localDate.before(this.defaultCenturyStart))) {
/* 1537 */         localDate = localCalendarBuilder.addYear(100).establish(this.calendar).getTime();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (IllegalArgumentException localIllegalArgumentException)
/*      */     {
/* 1544 */       paramParsePosition.errorIndex = i;
/* 1545 */       paramParsePosition.index = j;
/* 1546 */       return null;
/*      */     }
/*      */     
/* 1549 */     return localDate;
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
/*      */   private int matchString(String paramString, int paramInt1, int paramInt2, String[] paramArrayOfString, CalendarBuilder paramCalendarBuilder)
/*      */   {
/* 1563 */     int i = 0;
/* 1564 */     int j = paramArrayOfString.length;
/*      */     
/* 1566 */     if (paramInt2 == 7) {
/* 1567 */       i = 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1574 */     int k = 0;int m = -1;
/* 1575 */     for (; i < j; i++)
/*      */     {
/* 1577 */       int n = paramArrayOfString[i].length();
/*      */       
/*      */ 
/* 1580 */       if ((n > k) && 
/* 1581 */         (paramString.regionMatches(true, paramInt1, paramArrayOfString[i], 0, n)))
/*      */       {
/* 1583 */         m = i;
/* 1584 */         k = n;
/*      */       }
/*      */     }
/* 1587 */     if (m >= 0)
/*      */     {
/* 1589 */       paramCalendarBuilder.set(paramInt2, m);
/* 1590 */       return paramInt1 + k;
/*      */     }
/* 1592 */     return -paramInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int matchString(String paramString, int paramInt1, int paramInt2, Map<String, Integer> paramMap, CalendarBuilder paramCalendarBuilder)
/*      */   {
/* 1602 */     if (paramMap != null)
/*      */     {
/* 1604 */       if ((paramMap instanceof SortedMap)) {
/* 1605 */         for (localObject1 = paramMap.keySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (String)((Iterator)localObject1).next();
/* 1606 */           if (paramString.regionMatches(true, paramInt1, (String)localObject2, 0, ((String)localObject2).length())) {
/* 1607 */             paramCalendarBuilder.set(paramInt2, ((Integer)paramMap.get(localObject2)).intValue());
/* 1608 */             return paramInt1 + ((String)localObject2).length();
/*      */           }
/*      */         }
/* 1611 */         return -paramInt1;
/*      */       }
/*      */       
/* 1614 */       Object localObject1 = null;
/*      */       
/* 1616 */       for (Object localObject2 = paramMap.keySet().iterator(); ((Iterator)localObject2).hasNext();) { String str = (String)((Iterator)localObject2).next();
/* 1617 */         int i = str.length();
/* 1618 */         if (((localObject1 == null) || (i > ((String)localObject1).length())) && 
/* 1619 */           (paramString.regionMatches(true, paramInt1, str, 0, i))) {
/* 1620 */           localObject1 = str;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1625 */       if (localObject1 != null) {
/* 1626 */         paramCalendarBuilder.set(paramInt2, ((Integer)paramMap.get(localObject1)).intValue());
/* 1627 */         return paramInt1 + ((String)localObject1).length();
/*      */       }
/*      */     }
/* 1630 */     return -paramInt1;
/*      */   }
/*      */   
/*      */   private int matchZoneString(String paramString, int paramInt, String[] paramArrayOfString) {
/* 1634 */     for (int i = 1; i <= 4; i++)
/*      */     {
/*      */ 
/* 1637 */       String str = paramArrayOfString[i];
/* 1638 */       if (paramString.regionMatches(true, paramInt, str, 0, str
/* 1639 */         .length())) {
/* 1640 */         return i;
/*      */       }
/*      */     }
/* 1643 */     return -1;
/*      */   }
/*      */   
/*      */   private boolean matchDSTString(String paramString, int paramInt1, int paramInt2, int paramInt3, String[][] paramArrayOfString)
/*      */   {
/* 1648 */     int i = paramInt3 + 2;
/* 1649 */     String str = paramArrayOfString[paramInt2][i];
/* 1650 */     if (paramString.regionMatches(true, paramInt1, str, 0, str
/* 1651 */       .length())) {
/* 1652 */       return true;
/*      */     }
/* 1654 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int subParseZoneString(String paramString, int paramInt, CalendarBuilder paramCalendarBuilder)
/*      */   {
/* 1662 */     boolean bool = false;
/* 1663 */     TimeZone localTimeZone1 = getTimeZone();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1668 */     int i = this.formatData.getZoneIndex(localTimeZone1.getID());
/* 1669 */     TimeZone localTimeZone2 = null;
/* 1670 */     String[][] arrayOfString = this.formatData.getZoneStringsWrapper();
/* 1671 */     String[] arrayOfString1 = null;
/* 1672 */     int j = 0;
/* 1673 */     if (i != -1) {
/* 1674 */       arrayOfString1 = arrayOfString[i];
/* 1675 */       if ((j = matchZoneString(paramString, paramInt, arrayOfString1)) > 0) {
/* 1676 */         if (j <= 2)
/*      */         {
/* 1678 */           bool = arrayOfString1[j].equalsIgnoreCase(arrayOfString1[(j + 2)]);
/*      */         }
/* 1680 */         localTimeZone2 = TimeZone.getTimeZone(arrayOfString1[0]);
/*      */       }
/*      */     }
/* 1683 */     if (localTimeZone2 == null) {
/* 1684 */       i = this.formatData.getZoneIndex(TimeZone.getDefault().getID());
/* 1685 */       if (i != -1) {
/* 1686 */         arrayOfString1 = arrayOfString[i];
/* 1687 */         if ((j = matchZoneString(paramString, paramInt, arrayOfString1)) > 0) {
/* 1688 */           if (j <= 2) {
/* 1689 */             bool = arrayOfString1[j].equalsIgnoreCase(arrayOfString1[(j + 2)]);
/*      */           }
/* 1691 */           localTimeZone2 = TimeZone.getTimeZone(arrayOfString1[0]);
/*      */         }
/*      */       }
/*      */     }
/*      */     int k;
/* 1696 */     if (localTimeZone2 == null) {
/* 1697 */       k = arrayOfString.length;
/* 1698 */       for (int m = 0; m < k; m++) {
/* 1699 */         arrayOfString1 = arrayOfString[m];
/* 1700 */         if ((j = matchZoneString(paramString, paramInt, arrayOfString1)) > 0) {
/* 1701 */           if (j <= 2) {
/* 1702 */             bool = arrayOfString1[j].equalsIgnoreCase(arrayOfString1[(j + 2)]);
/*      */           }
/* 1704 */           localTimeZone2 = TimeZone.getTimeZone(arrayOfString1[0]);
/* 1705 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1709 */     if (localTimeZone2 != null) {
/* 1710 */       if (!localTimeZone2.equals(localTimeZone1)) {
/* 1711 */         setTimeZone(localTimeZone2);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1719 */       k = j >= 3 ? localTimeZone2.getDSTSavings() : 0;
/* 1720 */       if ((!bool) && ((j < 3) || (k != 0))) {
/* 1721 */         paramCalendarBuilder.clear(15).set(16, k);
/*      */       }
/* 1723 */       return paramInt + arrayOfString1[j].length();
/*      */     }
/* 1725 */     return 0;
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
/*      */   private int subParseNumericZone(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, CalendarBuilder paramCalendarBuilder)
/*      */   {
/* 1742 */     int i = paramInt1;
/*      */     
/*      */     try
/*      */     {
/* 1746 */       char c = paramString.charAt(i++);
/*      */       
/*      */ 
/* 1749 */       if (isDigit(c))
/*      */       {
/*      */ 
/* 1752 */         int j = c - '0';
/* 1753 */         c = paramString.charAt(i++);
/* 1754 */         if (isDigit(c)) {
/* 1755 */           j = j * 10 + (c - '0');
/*      */         }
/*      */         else
/*      */         {
/* 1759 */           if ((paramInt3 <= 0) && (!paramBoolean)) {
/*      */             break label242;
/*      */           }
/* 1762 */           i--;
/*      */         }
/* 1764 */         if (j <= 23)
/*      */         {
/*      */ 
/* 1767 */           int k = 0;
/* 1768 */           if (paramInt3 != 1)
/*      */           {
/* 1770 */             c = paramString.charAt(i++);
/* 1771 */             if (paramBoolean) {
/* 1772 */               if (c == ':')
/*      */               {
/*      */ 
/* 1775 */                 c = paramString.charAt(i++);
/*      */               }
/* 1777 */             } else if (isDigit(c))
/*      */             {
/*      */ 
/* 1780 */               k = c - '0';
/* 1781 */               c = paramString.charAt(i++);
/* 1782 */               if (isDigit(c))
/*      */               {
/*      */ 
/* 1785 */                 k = k * 10 + (c - '0');
/* 1786 */                 if (k > 59) {}
/*      */               }
/*      */             }
/*      */           } else {
/* 1790 */             k += j * 60;
/* 1791 */             paramCalendarBuilder.set(15, k * 60000 * paramInt2)
/* 1792 */               .set(16, 0);
/* 1793 */             return i;
/*      */           } } } } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
/*      */     label242:
/* 1796 */     return 1 - i;
/*      */   }
/*      */   
/*      */   private boolean isDigit(char paramChar) {
/* 1800 */     return (paramChar >= '0') && (paramChar <= '9');
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
/*      */   private int subParse(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean[] paramArrayOfBoolean, ParsePosition paramParsePosition, boolean paramBoolean2, CalendarBuilder paramCalendarBuilder)
/*      */   {
/* 1825 */     int i = 0;
/* 1826 */     ParsePosition localParsePosition = new ParsePosition(0);
/* 1827 */     localParsePosition.index = paramInt1;
/* 1828 */     if ((paramInt2 == 19) && (!this.calendar.isWeekDateSupported()))
/*      */     {
/* 1830 */       paramInt2 = 1;
/*      */     }
/* 1832 */     int j = PATTERN_INDEX_TO_CALENDAR_FIELD[paramInt2];
/*      */     
/*      */ 
/*      */     for (;;)
/*      */     {
/* 1837 */       if (localParsePosition.index >= paramString.length()) {
/* 1838 */         paramParsePosition.errorIndex = paramInt1;
/* 1839 */         return -1;
/*      */       }
/* 1841 */       k = paramString.charAt(localParsePosition.index);
/* 1842 */       if ((k != 32) && (k != 9)) {
/*      */         break;
/*      */       }
/* 1845 */       localParsePosition.index += 1;
/*      */     }
/*      */     
/* 1848 */     int k = localParsePosition.index;
/*      */     
/*      */ 
/*      */ 
/*      */     Number localNumber;
/*      */     
/*      */ 
/*      */ 
/* 1856 */     if ((paramInt2 == 4) || (paramInt2 == 15) || ((paramInt2 == 2) && (paramInt3 <= 2)) || (paramInt2 == 1) || (paramInt2 == 19))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1863 */       if (paramBoolean1) {
/* 1864 */         if (paramInt1 + paramInt3 > paramString.length()) {
/*      */           break label1753;
/*      */         }
/* 1867 */         localNumber = this.numberFormat.parse(paramString.substring(0, paramInt1 + paramInt3), localParsePosition);
/*      */       } else {
/* 1869 */         localNumber = this.numberFormat.parse(paramString, localParsePosition);
/*      */       }
/* 1871 */       if (localNumber == null) {
/* 1872 */         if (paramInt2 != 1) break label1753; if ((this.calendar instanceof GregorianCalendar)) {
/*      */           break label1753;
/*      */         }
/*      */       } else {
/* 1876 */         i = localNumber.intValue();
/*      */         
/* 1878 */         if ((paramBoolean2) && (i < 0) && (
/* 1879 */           ((localParsePosition.index < paramString.length()) && 
/* 1880 */           (paramString.charAt(localParsePosition.index) != this.minusSign)) || (
/* 1881 */           (localParsePosition.index == paramString.length()) && 
/* 1882 */           (paramString.charAt(localParsePosition.index - 1) == this.minusSign)))) {
/* 1883 */           i = -i;
/* 1884 */           localParsePosition.index -= 1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1889 */     boolean bool = useDateFormatSymbols();
/*      */     int m;
/*      */     int n;
/* 1892 */     Object localObject2; Object localObject1; int i2; switch (paramInt2) {
/*      */     case 0: 
/* 1894 */       if (bool) {
/* 1895 */         if ((m = matchString(paramString, paramInt1, 0, this.formatData.getEras(), paramCalendarBuilder)) > 0) {
/* 1896 */           return m;
/*      */         }
/*      */       } else {
/* 1899 */         Map localMap1 = getDisplayNamesMap(j, this.locale);
/* 1900 */         if ((m = matchString(paramString, paramInt1, j, localMap1, paramCalendarBuilder)) > 0) {
/* 1901 */           return m;
/*      */         }
/*      */       }
/* 1904 */       break;
/*      */     
/*      */     case 1: 
/*      */     case 19: 
/* 1908 */       if (!(this.calendar instanceof GregorianCalendar))
/*      */       {
/*      */ 
/* 1911 */         n = paramInt3 >= 4 ? 2 : 1;
/* 1912 */         localObject2 = this.calendar.getDisplayNames(j, n, this.locale);
/* 1913 */         if ((localObject2 != null) && 
/* 1914 */           ((m = matchString(paramString, paramInt1, j, (Map)localObject2, paramCalendarBuilder)) > 0)) {
/* 1915 */           return m;
/*      */         }
/*      */         
/* 1918 */         paramCalendarBuilder.set(j, i);
/* 1919 */         return localParsePosition.index;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1928 */       if ((paramInt3 <= 2) && (localParsePosition.index - k == 2) && 
/* 1929 */         (Character.isDigit(paramString.charAt(k))) && 
/* 1930 */         (Character.isDigit(paramString.charAt(k + 1))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1939 */         n = this.defaultCenturyStartYear % 100;
/* 1940 */         paramArrayOfBoolean[0] = (i == n ? 1 : false);
/* 1941 */         i += this.defaultCenturyStartYear / 100 * 100 + (i < n ? 100 : 0);
/*      */       }
/*      */       
/* 1944 */       paramCalendarBuilder.set(j, i);
/* 1945 */       return localParsePosition.index;
/*      */     
/*      */     case 2: 
/* 1948 */       if (paramInt3 <= 2)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1953 */         paramCalendarBuilder.set(2, i - 1);
/* 1954 */         return localParsePosition.index;
/*      */       }
/*      */       
/* 1957 */       if (bool)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1962 */         if ((n = matchString(paramString, paramInt1, 2, this.formatData
/* 1963 */           .getMonths(), paramCalendarBuilder)) > 0) {
/* 1964 */           return n;
/*      */         }
/*      */         
/* 1967 */         if ((m = matchString(paramString, paramInt1, 2, this.formatData
/* 1968 */           .getShortMonths(), paramCalendarBuilder)) > 0) {
/* 1969 */           return m;
/*      */         }
/*      */       } else {
/* 1972 */         Map localMap2 = getDisplayNamesMap(j, this.locale);
/* 1973 */         if ((m = matchString(paramString, paramInt1, j, localMap2, paramCalendarBuilder)) > 0) {
/* 1974 */           return m;
/*      */         }
/*      */       }
/* 1977 */       break;
/*      */     
/*      */     case 4: 
/* 1980 */       if ((isLenient()) || (
/*      */       
/* 1982 */         (i >= 1) && (i <= 24)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1987 */         if (i == this.calendar.getMaximum(11) + 1) {
/* 1988 */           i = 0;
/*      */         }
/* 1990 */         paramCalendarBuilder.set(11, i);
/* 1991 */         return localParsePosition.index;
/*      */       }
/*      */       break;
/*      */     case 9: 
/* 1995 */       if (bool)
/*      */       {
/*      */         int i1;
/*      */         
/* 1999 */         if ((i1 = matchString(paramString, paramInt1, 7, this.formatData
/* 2000 */           .getWeekdays(), paramCalendarBuilder)) > 0) {
/* 2001 */           return i1;
/*      */         }
/*      */         
/* 2004 */         if ((m = matchString(paramString, paramInt1, 7, this.formatData
/* 2005 */           .getShortWeekdays(), paramCalendarBuilder)) > 0) {
/* 2006 */           return m;
/*      */         }
/*      */       } else {
/* 2009 */         localObject1 = new int[] { 2, 1 };
/* 2010 */         for (int i7 : localObject1) {
/* 2011 */           Map localMap3 = this.calendar.getDisplayNames(j, i7, this.locale);
/* 2012 */           if ((m = matchString(paramString, paramInt1, j, localMap3, paramCalendarBuilder)) > 0) {
/* 2013 */             return m;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2018 */       break;
/*      */     
/*      */     case 14: 
/* 2021 */       if (bool) {
/* 2022 */         if ((m = matchString(paramString, paramInt1, 9, this.formatData
/* 2023 */           .getAmPmStrings(), paramCalendarBuilder)) > 0) {
/* 2024 */           return m;
/*      */         }
/*      */       } else {
/* 2027 */         localObject1 = getDisplayNamesMap(j, this.locale);
/* 2028 */         if ((m = matchString(paramString, paramInt1, j, (Map)localObject1, paramCalendarBuilder)) > 0) {
/* 2029 */           return m;
/*      */         }
/*      */       }
/* 2032 */       break;
/*      */     
/*      */     case 15: 
/* 2035 */       if ((isLenient()) || (
/*      */       
/* 2037 */         (i >= 1) && (i <= 12)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2042 */         if (i == this.calendar.getLeastMaximum(10) + 1) {
/* 2043 */           i = 0;
/*      */         }
/* 2045 */         paramCalendarBuilder.set(10, i);
/* 2046 */         return localParsePosition.index;
/*      */       }
/*      */       break;
/*      */     case 17: 
/*      */     case 18: 
/* 2051 */       i2 = 0;
/*      */       try {
/* 2053 */         int i3 = paramString.charAt(localParsePosition.index);
/* 2054 */         if (i3 == 43) {
/* 2055 */           i2 = 1;
/* 2056 */         } else if (i3 == 45) {
/* 2057 */           i2 = -1;
/*      */         }
/* 2059 */         if (i2 == 0)
/*      */         {
/* 2061 */           if (((i3 == 71) || (i3 == 103)) && 
/* 2062 */             (paramString.length() - paramInt1 >= "GMT".length()) && 
/* 2063 */             (paramString.regionMatches(true, paramInt1, "GMT", 0, "GMT".length()))) {
/* 2064 */             localParsePosition.index = (paramInt1 + "GMT".length());
/*      */             
/* 2066 */             if (paramString.length() - localParsePosition.index > 0) {
/* 2067 */               i3 = paramString.charAt(localParsePosition.index);
/* 2068 */               if (i3 == 43) {
/* 2069 */                 i2 = 1;
/* 2070 */               } else if (i3 == 45) {
/* 2071 */                 i2 = -1;
/*      */               }
/*      */             }
/*      */             
/* 2075 */             if (i2 == 0)
/*      */             {
/* 2077 */               paramCalendarBuilder.set(15, 0).set(16, 0);
/* 2078 */               return localParsePosition.index;
/*      */             }
/*      */             
/*      */ 
/* 2082 */             ??? = subParseNumericZone(paramString, ++localParsePosition.index, i2, 0, true, paramCalendarBuilder);
/*      */             
/* 2084 */             if (??? > 0) {
/* 2085 */               return ???;
/*      */             }
/* 2087 */             localParsePosition.index = (-???);
/*      */           }
/*      */           else
/*      */           {
/* 2091 */             ??? = subParseZoneString(paramString, localParsePosition.index, paramCalendarBuilder);
/* 2092 */             if (??? > 0) {
/* 2093 */               return ???;
/*      */             }
/* 2095 */             localParsePosition.index = (-???);
/*      */           }
/*      */         }
/*      */         else {
/* 2099 */           ??? = subParseNumericZone(paramString, ++localParsePosition.index, i2, 0, false, paramCalendarBuilder);
/*      */           
/* 2101 */           if (??? > 0) {
/* 2102 */             return ???;
/*      */           }
/* 2104 */           localParsePosition.index = (-???);
/*      */         }
/*      */       }
/*      */       catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
/*      */       
/* 2109 */       break;
/*      */     
/*      */ 
/*      */     case 21: 
/* 2113 */       if (paramString.length() - localParsePosition.index > 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2118 */         int i4 = paramString.charAt(localParsePosition.index);
/* 2119 */         if (i4 == 90) {
/* 2120 */           paramCalendarBuilder.set(15, 0).set(16, 0);
/* 2121 */           return ++localParsePosition.index;
/*      */         }
/*      */         
/*      */ 
/* 2125 */         if (i4 == 43) {
/* 2126 */           i2 = 1;
/* 2127 */         } else if (i4 == 45) {
/* 2128 */           i2 = -1;
/*      */         } else {
/* 2130 */           localParsePosition.index += 1;
/* 2131 */           break;
/*      */         }
/* 2133 */         ??? = subParseNumericZone(paramString, ++localParsePosition.index, i2, paramInt3, paramInt3 == 3, paramCalendarBuilder);
/*      */         
/* 2135 */         if (??? > 0) {
/* 2136 */           return ???;
/*      */         }
/* 2138 */         localParsePosition.index = (-???);
/*      */       }
/* 2140 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 3: 
/*      */     case 5: 
/*      */     case 6: 
/*      */     case 7: 
/*      */     case 8: 
/*      */     case 10: 
/*      */     case 11: 
/*      */     case 12: 
/*      */     case 13: 
/*      */     case 16: 
/*      */     case 20: 
/*      */     default: 
/* 2156 */       if (paramBoolean1) {
/* 2157 */         if (paramInt1 + paramInt3 > paramString.length()) {
/*      */           break;
/*      */         }
/* 2160 */         localNumber = this.numberFormat.parse(paramString.substring(0, paramInt1 + paramInt3), localParsePosition);
/*      */       } else {
/* 2162 */         localNumber = this.numberFormat.parse(paramString, localParsePosition);
/*      */       }
/* 2164 */       if (localNumber != null) {
/* 2165 */         i = localNumber.intValue();
/*      */         
/* 2167 */         if ((paramBoolean2) && (i < 0) && (
/* 2168 */           ((localParsePosition.index < paramString.length()) && 
/* 2169 */           (paramString.charAt(localParsePosition.index) != this.minusSign)) || (
/* 2170 */           (localParsePosition.index == paramString.length()) && 
/* 2171 */           (paramString.charAt(localParsePosition.index - 1) == this.minusSign)))) {
/* 2172 */           i = -i;
/* 2173 */           localParsePosition.index -= 1;
/*      */         }
/*      */         
/* 2176 */         paramCalendarBuilder.set(j, i);
/* 2177 */         return localParsePosition.index;
/*      */       }
/*      */       
/*      */       break;
/*      */     }
/*      */     
/*      */     label1753:
/* 2184 */     paramParsePosition.errorIndex = localParsePosition.index;
/* 2185 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean useDateFormatSymbols()
/*      */   {
/* 2193 */     return (this.useDateFormatSymbols) || (this.locale == null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String translatePattern(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 2203 */     StringBuilder localStringBuilder = new StringBuilder();
/* 2204 */     int i = 0;
/* 2205 */     for (int j = 0; j < paramString1.length(); j++) {
/* 2206 */       char c = paramString1.charAt(j);
/* 2207 */       if (i != 0) {
/* 2208 */         if (c == '\'') {
/* 2209 */           i = 0;
/*      */         }
/*      */         
/*      */       }
/* 2213 */       else if (c == '\'') {
/* 2214 */         i = 1;
/* 2215 */       } else if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))) {
/* 2216 */         int k = paramString2.indexOf(c);
/* 2217 */         if (k >= 0)
/*      */         {
/*      */ 
/*      */ 
/* 2221 */           if (k < paramString3.length()) {
/* 2222 */             c = paramString3.charAt(k);
/*      */           }
/*      */         } else {
/* 2225 */           throw new IllegalArgumentException("Illegal pattern  character '" + c + "'");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2231 */       localStringBuilder.append(c);
/*      */     }
/* 2233 */     if (i != 0) {
/* 2234 */       throw new IllegalArgumentException("Unfinished quote in pattern");
/*      */     }
/* 2236 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toPattern()
/*      */   {
/* 2245 */     return this.pattern;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toLocalizedPattern()
/*      */   {
/* 2254 */     return translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzZYuXL", this.formatData
/*      */     
/* 2256 */       .getLocalPatternChars());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyPattern(String paramString)
/*      */   {
/* 2268 */     applyPatternImpl(paramString);
/*      */   }
/*      */   
/*      */   private void applyPatternImpl(String paramString) {
/* 2272 */     this.compiledPattern = compile(paramString);
/* 2273 */     this.pattern = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyLocalizedPattern(String paramString)
/*      */   {
/* 2285 */     String str = translatePattern(paramString, this.formatData
/* 2286 */       .getLocalPatternChars(), "GyMdkHmsSEDFwWahKzZYuXL");
/*      */     
/* 2288 */     this.compiledPattern = compile(str);
/* 2289 */     this.pattern = str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DateFormatSymbols getDateFormatSymbols()
/*      */   {
/* 2300 */     return (DateFormatSymbols)this.formatData.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDateFormatSymbols(DateFormatSymbols paramDateFormatSymbols)
/*      */   {
/* 2312 */     this.formatData = ((DateFormatSymbols)paramDateFormatSymbols.clone());
/* 2313 */     this.useDateFormatSymbols = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/* 2324 */     SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat)super.clone();
/* 2325 */     localSimpleDateFormat.formatData = ((DateFormatSymbols)this.formatData.clone());
/* 2326 */     return localSimpleDateFormat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 2337 */     return this.pattern.hashCode();
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 2351 */     if (!super.equals(paramObject)) {
/* 2352 */       return false;
/*      */     }
/* 2354 */     SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat)paramObject;
/*      */     
/* 2356 */     return (this.pattern.equals(localSimpleDateFormat.pattern)) && (this.formatData.equals(localSimpleDateFormat.formatData));
/*      */   }
/*      */   
/* 2359 */   private static final int[] REST_OF_STYLES = { 32769, 2, 32770 };
/*      */   
/*      */   private Map<String, Integer> getDisplayNamesMap(int paramInt, Locale paramLocale)
/*      */   {
/* 2363 */     Map localMap1 = this.calendar.getDisplayNames(paramInt, 1, paramLocale);
/*      */     
/* 2365 */     for (int k : REST_OF_STYLES) {
/* 2366 */       Map localMap2 = this.calendar.getDisplayNames(paramInt, k, paramLocale);
/* 2367 */       if (localMap2 != null) {
/* 2368 */         localMap1.putAll(localMap2);
/*      */       }
/*      */     }
/* 2371 */     return localMap1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 2382 */     paramObjectInputStream.defaultReadObject();
/*      */     try
/*      */     {
/* 2385 */       this.compiledPattern = compile(this.pattern);
/*      */     } catch (Exception localException) {
/* 2387 */       throw new InvalidObjectException("invalid pattern");
/*      */     }
/*      */     
/* 2390 */     if (this.serialVersionOnStream < 1)
/*      */     {
/* 2392 */       initializeDefaultCentury();
/*      */     }
/*      */     else
/*      */     {
/* 2396 */       parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
/*      */     }
/* 2398 */     this.serialVersionOnStream = 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2404 */     TimeZone localTimeZone1 = getTimeZone();
/* 2405 */     if ((localTimeZone1 instanceof SimpleTimeZone)) {
/* 2406 */       String str = localTimeZone1.getID();
/* 2407 */       TimeZone localTimeZone2 = TimeZone.getTimeZone(str);
/* 2408 */       if ((localTimeZone2 != null) && (localTimeZone2.hasSameRules(localTimeZone1)) && (localTimeZone2.getID().equals(str))) {
/* 2409 */         setTimeZone(localTimeZone2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkNegativeNumberExpression()
/*      */   {
/* 2419 */     if (((this.numberFormat instanceof DecimalFormat)) && 
/* 2420 */       (!this.numberFormat.equals(this.originalNumberFormat))) {
/* 2421 */       String str = ((DecimalFormat)this.numberFormat).toPattern();
/* 2422 */       if (!str.equals(this.originalNumberPattern)) {
/* 2423 */         this.hasFollowingMinusSign = false;
/*      */         
/* 2425 */         int i = str.indexOf(';');
/*      */         
/*      */ 
/* 2428 */         if (i > -1) {
/* 2429 */           int j = str.indexOf('-', i);
/* 2430 */           if ((j > str.lastIndexOf('0')) && 
/* 2431 */             (j > str.lastIndexOf('#'))) {
/* 2432 */             this.hasFollowingMinusSign = true;
/* 2433 */             this.minusSign = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getMinusSign();
/*      */           }
/*      */         }
/* 2436 */         this.originalNumberPattern = str;
/*      */       }
/* 2438 */       this.originalNumberFormat = this.numberFormat;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/SimpleDateFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */