/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.chrono.JapaneseChronology;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.IsoFields;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.util.AbstractMap.SimpleImmutableEntry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.util.locale.provider.CalendarDataUtility;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleResources;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DateTimeTextProvider
/*     */ {
/* 106 */   private static final ConcurrentMap<Map.Entry<TemporalField, Locale>, Object> CACHE = new ConcurrentHashMap(16, 0.75F, 2);
/*     */   
/* 108 */   private static final Comparator<Map.Entry<String, Long>> COMPARATOR = new Comparator()
/*     */   {
/*     */     public int compare(Map.Entry<String, Long> paramAnonymousEntry1, Map.Entry<String, Long> paramAnonymousEntry2) {
/* 111 */       return ((String)paramAnonymousEntry2.getKey()).length() - ((String)paramAnonymousEntry1.getKey()).length();
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static DateTimeTextProvider getInstance()
/*     */   {
/* 123 */     return new DateTimeTextProvider();
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
/*     */   public String getText(TemporalField paramTemporalField, long paramLong, TextStyle paramTextStyle, Locale paramLocale)
/*     */   {
/* 141 */     Object localObject = findStore(paramTemporalField, paramLocale);
/* 142 */     if ((localObject instanceof LocaleStore)) {
/* 143 */       return ((LocaleStore)localObject).getText(paramLong, paramTextStyle);
/*     */     }
/* 145 */     return null;
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
/*     */   public String getText(Chronology paramChronology, TemporalField paramTemporalField, long paramLong, TextStyle paramTextStyle, Locale paramLocale)
/*     */   {
/* 165 */     if ((paramChronology == IsoChronology.INSTANCE) || (!(paramTemporalField instanceof ChronoField)))
/*     */     {
/* 167 */       return getText(paramTemporalField, paramLong, paramTextStyle, paramLocale);
/*     */     }
/*     */     
/*     */     int i;
/*     */     int j;
/* 172 */     if (paramTemporalField == ChronoField.ERA) {
/* 173 */       i = 0;
/* 174 */       if (paramChronology == JapaneseChronology.INSTANCE) {
/* 175 */         if (paramLong == -999L) {
/* 176 */           j = 0;
/*     */         } else {
/* 178 */           j = (int)paramLong + 2;
/*     */         }
/*     */       } else {
/* 181 */         j = (int)paramLong;
/*     */       }
/* 183 */     } else if (paramTemporalField == ChronoField.MONTH_OF_YEAR) {
/* 184 */       i = 2;
/* 185 */       j = (int)paramLong - 1;
/* 186 */     } else if (paramTemporalField == ChronoField.DAY_OF_WEEK) {
/* 187 */       i = 7;
/* 188 */       j = (int)paramLong + 1;
/* 189 */       if (j > 7) {
/* 190 */         j = 1;
/*     */       }
/* 192 */     } else if (paramTemporalField == ChronoField.AMPM_OF_DAY) {
/* 193 */       i = 9;
/* 194 */       j = (int)paramLong;
/*     */     } else {
/* 196 */       return null;
/*     */     }
/* 198 */     return CalendarDataUtility.retrieveJavaTimeFieldValueName(paramChronology
/* 199 */       .getCalendarType(), i, j, paramTextStyle.toCalendarStyle(), paramLocale);
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
/*     */   public Iterator<Map.Entry<String, Long>> getTextIterator(TemporalField paramTemporalField, TextStyle paramTextStyle, Locale paramLocale)
/*     */   {
/* 219 */     Object localObject = findStore(paramTemporalField, paramLocale);
/* 220 */     if ((localObject instanceof LocaleStore)) {
/* 221 */       return ((LocaleStore)localObject).getTextIterator(paramTextStyle);
/*     */     }
/* 223 */     return null;
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
/*     */   public Iterator<Map.Entry<String, Long>> getTextIterator(Chronology paramChronology, TemporalField paramTemporalField, TextStyle paramTextStyle, Locale paramLocale)
/*     */   {
/* 245 */     if ((paramChronology == IsoChronology.INSTANCE) || (!(paramTemporalField instanceof ChronoField)))
/*     */     {
/* 247 */       return getTextIterator(paramTemporalField, paramTextStyle, paramLocale);
/*     */     }
/*     */     
/*     */     int i;
/* 251 */     switch ((ChronoField)paramTemporalField) {
/*     */     case ERA: 
/* 253 */       i = 0;
/* 254 */       break;
/*     */     case MONTH_OF_YEAR: 
/* 256 */       i = 2;
/* 257 */       break;
/*     */     case DAY_OF_WEEK: 
/* 259 */       i = 7;
/* 260 */       break;
/*     */     case AMPM_OF_DAY: 
/* 262 */       i = 9;
/* 263 */       break;
/*     */     default: 
/* 265 */       return null;
/*     */     }
/*     */     
/* 268 */     int j = paramTextStyle == null ? 0 : paramTextStyle.toCalendarStyle();
/* 269 */     Map localMap = CalendarDataUtility.retrieveJavaTimeFieldValueNames(paramChronology
/* 270 */       .getCalendarType(), i, j, paramLocale);
/* 271 */     if (localMap == null) {
/* 272 */       return null;
/*     */     }
/* 274 */     ArrayList localArrayList = new ArrayList(localMap.size());
/* 275 */     Iterator localIterator; Map.Entry localEntry; switch (i) {
/*     */     case 0: 
/* 277 */       for (localIterator = localMap.entrySet().iterator(); localIterator.hasNext();) { localEntry = (Map.Entry)localIterator.next();
/* 278 */         int k = ((Integer)localEntry.getValue()).intValue();
/* 279 */         if (paramChronology == JapaneseChronology.INSTANCE) {
/* 280 */           if (k == 0) {
/* 281 */             k = 64537;
/*     */           } else {
/* 283 */             k -= 2;
/*     */           }
/*     */         }
/* 286 */         localArrayList.add(createEntry(localEntry.getKey(), Long.valueOf(k)));
/*     */       }
/* 288 */       break;
/*     */     case 2: 
/* 290 */       for (localIterator = localMap.entrySet().iterator(); localIterator.hasNext();) { localEntry = (Map.Entry)localIterator.next();
/* 291 */         localArrayList.add(createEntry(localEntry.getKey(), Long.valueOf(((Integer)localEntry.getValue()).intValue() + 1)));
/*     */       }
/* 293 */       break;
/*     */     case 7: 
/* 295 */       for (localIterator = localMap.entrySet().iterator(); localIterator.hasNext();) { localEntry = (Map.Entry)localIterator.next();
/* 296 */         localArrayList.add(createEntry(localEntry.getKey(), Long.valueOf(toWeekDay(((Integer)localEntry.getValue()).intValue()))));
/*     */       }
/* 298 */       break;
/*     */     default: 
/* 300 */       for (localIterator = localMap.entrySet().iterator(); localIterator.hasNext();) { localEntry = (Map.Entry)localIterator.next();
/* 301 */         localArrayList.add(createEntry(localEntry.getKey(), Long.valueOf(((Integer)localEntry.getValue()).intValue())));
/*     */       }
/*     */     }
/*     */     
/* 305 */     return localArrayList.iterator();
/*     */   }
/*     */   
/*     */   private Object findStore(TemporalField paramTemporalField, Locale paramLocale) {
/* 309 */     Map.Entry localEntry = createEntry(paramTemporalField, paramLocale);
/* 310 */     Object localObject = CACHE.get(localEntry);
/* 311 */     if (localObject == null) {
/* 312 */       localObject = createStore(paramTemporalField, paramLocale);
/* 313 */       CACHE.putIfAbsent(localEntry, localObject);
/* 314 */       localObject = CACHE.get(localEntry);
/*     */     }
/* 316 */     return localObject;
/*     */   }
/*     */   
/*     */   private static int toWeekDay(int paramInt) {
/* 320 */     if (paramInt == 1) {
/* 321 */       return 7;
/*     */     }
/* 323 */     return paramInt - 1;
/*     */   }
/*     */   
/*     */   private Object createStore(TemporalField paramTemporalField, Locale paramLocale)
/*     */   {
/* 328 */     HashMap localHashMap1 = new HashMap();
/* 329 */     Object localObject2; Map localMap; HashMap localHashMap2; Iterator localIterator1; Object localObject3; if (paramTemporalField == ChronoField.ERA) {
/* 330 */       for (localObject2 : TextStyle.values()) {
/* 331 */         if (!((TextStyle)localObject2).isStandalone())
/*     */         {
/*     */ 
/*     */ 
/* 335 */           localMap = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 0, ((TextStyle)localObject2)
/* 336 */             .toCalendarStyle(), paramLocale);
/* 337 */           if (localMap != null) {
/* 338 */             localHashMap2 = new HashMap();
/* 339 */             for (localIterator1 = localMap.entrySet().iterator(); localIterator1.hasNext();) { localObject3 = (Map.Entry)localIterator1.next();
/* 340 */               localHashMap2.put(Long.valueOf(((Integer)((Map.Entry)localObject3).getValue()).intValue()), ((Map.Entry)localObject3).getKey());
/*     */             }
/* 342 */             if (!localHashMap2.isEmpty())
/* 343 */               localHashMap1.put(localObject2, localHashMap2);
/*     */           }
/*     */         }
/*     */       }
/* 347 */       return new LocaleStore(localHashMap1);
/*     */     }
/*     */     
/* 350 */     if (paramTemporalField == ChronoField.MONTH_OF_YEAR) {
/* 351 */       for (localObject2 : TextStyle.values()) {
/* 352 */         localMap = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 2, ((TextStyle)localObject2)
/* 353 */           .toCalendarStyle(), paramLocale);
/* 354 */         localHashMap2 = new HashMap();
/* 355 */         if (localMap != null) {
/* 356 */           for (localIterator1 = localMap.entrySet().iterator(); localIterator1.hasNext();) { localObject3 = (Map.Entry)localIterator1.next();
/* 357 */             localHashMap2.put(Long.valueOf(((Integer)((Map.Entry)localObject3).getValue()).intValue() + 1), ((Map.Entry)localObject3).getKey());
/*     */           }
/*     */           
/*     */         }
/*     */         else
/*     */         {
/* 363 */           for (int m = 0; m <= 11; m++)
/*     */           {
/* 365 */             localObject3 = CalendarDataUtility.retrieveJavaTimeFieldValueName("gregory", 2, m, ((TextStyle)localObject2)
/* 366 */               .toCalendarStyle(), paramLocale);
/* 367 */             if (localObject3 == null) {
/*     */               break;
/*     */             }
/* 370 */             localHashMap2.put(Long.valueOf(m + 1), localObject3);
/*     */           }
/*     */         }
/* 373 */         if (!localHashMap2.isEmpty()) {
/* 374 */           localHashMap1.put(localObject2, localHashMap2);
/*     */         }
/*     */       }
/* 377 */       return new LocaleStore(localHashMap1);
/*     */     }
/*     */     
/* 380 */     if (paramTemporalField == ChronoField.DAY_OF_WEEK) {
/* 381 */       for (localObject2 : TextStyle.values()) {
/* 382 */         localMap = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 7, ((TextStyle)localObject2)
/* 383 */           .toCalendarStyle(), paramLocale);
/* 384 */         localHashMap2 = new HashMap();
/* 385 */         Iterator localIterator2; if (localMap != null) {
/* 386 */           for (localIterator2 = localMap.entrySet().iterator(); localIterator2.hasNext();) { localObject3 = (Map.Entry)localIterator2.next();
/* 387 */             localHashMap2.put(Long.valueOf(toWeekDay(((Integer)((Map.Entry)localObject3).getValue()).intValue())), ((Map.Entry)localObject3).getKey());
/*     */           }
/*     */           
/*     */         }
/*     */         else
/*     */         {
/* 393 */           for (int n = 1; n <= 7; n++)
/*     */           {
/* 395 */             localObject3 = CalendarDataUtility.retrieveJavaTimeFieldValueName("gregory", 7, n, ((TextStyle)localObject2)
/* 396 */               .toCalendarStyle(), paramLocale);
/* 397 */             if (localObject3 == null) {
/*     */               break;
/*     */             }
/* 400 */             localHashMap2.put(Long.valueOf(toWeekDay(n)), localObject3);
/*     */           }
/*     */         }
/* 403 */         if (!localHashMap2.isEmpty()) {
/* 404 */           localHashMap1.put(localObject2, localHashMap2);
/*     */         }
/*     */       }
/* 407 */       return new LocaleStore(localHashMap1);
/*     */     }
/*     */     
/* 410 */     if (paramTemporalField == ChronoField.AMPM_OF_DAY) {
/* 411 */       for (localObject2 : TextStyle.values()) {
/* 412 */         if (!((TextStyle)localObject2).isStandalone())
/*     */         {
/*     */ 
/*     */ 
/* 416 */           localMap = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 9, ((TextStyle)localObject2)
/* 417 */             .toCalendarStyle(), paramLocale);
/* 418 */           if (localMap != null) {
/* 419 */             localHashMap2 = new HashMap();
/* 420 */             for (Iterator localIterator3 = localMap.entrySet().iterator(); localIterator3.hasNext();) { localObject3 = (Map.Entry)localIterator3.next();
/* 421 */               localHashMap2.put(Long.valueOf(((Integer)((Map.Entry)localObject3).getValue()).intValue()), ((Map.Entry)localObject3).getKey());
/*     */             }
/* 423 */             if (!localHashMap2.isEmpty())
/* 424 */               localHashMap1.put(localObject2, localHashMap2);
/*     */           }
/*     */         }
/*     */       }
/* 428 */       return new LocaleStore(localHashMap1);
/*     */     }
/*     */     
/* 431 */     if (paramTemporalField == IsoFields.QUARTER_OF_YEAR)
/*     */     {
/* 433 */       ??? = new String[] { "QuarterNames", "standalone.QuarterNames", "QuarterAbbreviations", "standalone.QuarterAbbreviations", "QuarterNarrows", "standalone.QuarterNarrows" };
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 441 */       for (??? = 0; ??? < ???.length; ???++) {
/* 442 */         String[] arrayOfString = (String[])getLocalizedResource(???[???], paramLocale);
/* 443 */         if (arrayOfString != null) {
/* 444 */           localObject2 = new HashMap();
/* 445 */           for (int k = 0; k < arrayOfString.length; k++) {
/* 446 */             ((Map)localObject2).put(Long.valueOf(k + 1), arrayOfString[k]);
/*     */           }
/* 448 */           localHashMap1.put(TextStyle.values()[???], localObject2);
/*     */         }
/*     */       }
/* 451 */       return new LocaleStore(localHashMap1);
/*     */     }
/*     */     
/* 454 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <A, B> Map.Entry<A, B> createEntry(A paramA, B paramB)
/*     */   {
/* 465 */     return new AbstractMap.SimpleImmutableEntry(paramA, paramB);
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
/*     */   static <T> T getLocalizedResource(String paramString, Locale paramLocale)
/*     */   {
/* 480 */     LocaleResources localLocaleResources = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(paramLocale);
/* 481 */     ResourceBundle localResourceBundle = localLocaleResources.getJavaTimeFormatData();
/* 482 */     return (T)(localResourceBundle.containsKey(paramString) ? localResourceBundle.getObject(paramString) : null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class LocaleStore
/*     */   {
/*     */     private final Map<TextStyle, Map<Long, String>> valueTextMap;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final Map<TextStyle, List<Map.Entry<String, Long>>> parsable;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     LocaleStore(Map<TextStyle, Map<Long, String>> paramMap)
/*     */     {
/* 510 */       this.valueTextMap = paramMap;
/* 511 */       HashMap localHashMap1 = new HashMap();
/* 512 */       ArrayList localArrayList = new ArrayList();
/* 513 */       for (Map.Entry localEntry1 : paramMap.entrySet()) {
/* 514 */         HashMap localHashMap2 = new HashMap();
/* 515 */         for (Object localObject = ((Map)localEntry1.getValue()).entrySet().iterator(); ((Iterator)localObject).hasNext();) { Map.Entry localEntry2 = (Map.Entry)((Iterator)localObject).next();
/* 516 */           if (localHashMap2.put(localEntry2.getValue(), DateTimeTextProvider.createEntry(localEntry2.getValue(), localEntry2.getKey())) != null) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 521 */         localObject = new ArrayList(localHashMap2.values());
/* 522 */         Collections.sort((List)localObject, DateTimeTextProvider.COMPARATOR);
/* 523 */         localHashMap1.put(localEntry1.getKey(), localObject);
/* 524 */         localArrayList.addAll((Collection)localObject);
/* 525 */         localHashMap1.put(null, localArrayList);
/*     */       }
/* 527 */       Collections.sort(localArrayList, DateTimeTextProvider.COMPARATOR);
/* 528 */       this.parsable = localHashMap1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     String getText(long paramLong, TextStyle paramTextStyle)
/*     */     {
/* 540 */       Map localMap = (Map)this.valueTextMap.get(paramTextStyle);
/* 541 */       return localMap != null ? (String)localMap.get(Long.valueOf(paramLong)) : null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Iterator<Map.Entry<String, Long>> getTextIterator(TextStyle paramTextStyle)
/*     */     {
/* 554 */       List localList = (List)this.parsable.get(paramTextStyle);
/* 555 */       return localList != null ? localList.iterator() : null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/format/DateTimeTextProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */