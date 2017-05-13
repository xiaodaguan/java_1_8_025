/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Level
/*     */   implements Serializable
/*     */ {
/*     */   private static final String defaultBundle = "sun.util.logging.resources.logging";
/*     */   private final String name;
/*     */   private final int value;
/*     */   private final String resourceBundleName;
/*     */   private transient String localizedLevelName;
/*     */   private transient Locale cachedLocale;
/*  92 */   public static final Level OFF = new Level("OFF", Integer.MAX_VALUE, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */   public static final Level SEVERE = new Level("SEVERE", 1000, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */   public static final Level WARNING = new Level("WARNING", 900, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 124 */   public static final Level INFO = new Level("INFO", 800, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */   public static final Level CONFIG = new Level("CONFIG", 700, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */   public static final Level FINE = new Level("FINE", 500, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 165 */   public static final Level FINER = new Level("FINER", 400, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */   public static final Level FINEST = new Level("FINEST", 300, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */   public static final Level ALL = new Level("ALL", Integer.MIN_VALUE, "sun.util.logging.resources.logging");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -8176160795706313070L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Level(String paramString, int paramInt)
/*     */   {
/* 192 */     this(paramString, paramInt, null);
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
/*     */   protected Level(String paramString1, int paramInt, String paramString2)
/*     */   {
/* 207 */     this(paramString1, paramInt, paramString2, true);
/*     */   }
/*     */   
/*     */ 
/*     */   private Level(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
/*     */   {
/* 213 */     if (paramString1 == null) {
/* 214 */       throw new NullPointerException();
/*     */     }
/* 216 */     this.name = paramString1;
/* 217 */     this.value = paramInt;
/* 218 */     this.resourceBundleName = paramString2;
/* 219 */     this.localizedLevelName = (paramString2 == null ? paramString1 : null);
/* 220 */     this.cachedLocale = null;
/* 221 */     if (paramBoolean) {
/* 222 */       KnownLevel.add(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getResourceBundleName()
/*     */   {
/* 233 */     return this.resourceBundleName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 242 */     return this.name;
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
/*     */   public String getLocalizedName()
/*     */   {
/* 255 */     return getLocalizedLevelName();
/*     */   }
/*     */   
/*     */ 
/*     */   final String getLevelName()
/*     */   {
/* 261 */     return this.name;
/*     */   }
/*     */   
/*     */   private String computeLocalizedLevelName(Locale paramLocale) {
/* 265 */     ResourceBundle localResourceBundle = ResourceBundle.getBundle(this.resourceBundleName, paramLocale);
/* 266 */     String str = localResourceBundle.getString(this.name);
/*     */     
/* 268 */     boolean bool = "sun.util.logging.resources.logging".equals(this.resourceBundleName);
/* 269 */     if (!bool) { return str;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 274 */     Locale localLocale1 = localResourceBundle.getLocale();
/*     */     
/*     */ 
/* 277 */     Locale localLocale2 = (Locale.ROOT.equals(localLocale1)) || (this.name.equals(str.toUpperCase(Locale.ROOT))) ? Locale.ROOT : localLocale1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 284 */     return Locale.ROOT.equals(localLocale2) ? this.name : str.toUpperCase(localLocale2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final String getCachedLocalizedLevelName()
/*     */   {
/* 291 */     if ((this.localizedLevelName != null) && 
/* 292 */       (this.cachedLocale != null) && 
/* 293 */       (this.cachedLocale.equals(Locale.getDefault())))
/*     */     {
/*     */ 
/* 296 */       return this.localizedLevelName;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 301 */     if (this.resourceBundleName == null)
/*     */     {
/* 303 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 309 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   final synchronized String getLocalizedLevelName()
/*     */   {
/* 315 */     String str = getCachedLocalizedLevelName();
/* 316 */     if (str != null) {
/* 317 */       return str;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 322 */     Locale localLocale = Locale.getDefault();
/*     */     try {
/* 324 */       this.localizedLevelName = computeLocalizedLevelName(localLocale);
/*     */     } catch (Exception localException) {
/* 326 */       this.localizedLevelName = this.name;
/*     */     }
/* 328 */     this.cachedLocale = localLocale;
/* 329 */     return this.localizedLevelName;
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
/*     */   static Level findLevel(String paramString)
/*     */   {
/* 344 */     if (paramString == null) {
/* 345 */       throw new NullPointerException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 351 */     KnownLevel localKnownLevel = KnownLevel.findByName(paramString);
/* 352 */     if (localKnownLevel != null) {
/* 353 */       return localKnownLevel.mirroredLevel;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 360 */       int i = Integer.parseInt(paramString);
/* 361 */       localKnownLevel = KnownLevel.findByValue(i);
/* 362 */       if (localKnownLevel == null)
/*     */       {
/* 364 */         Level localLevel = new Level(paramString, i);
/* 365 */         localKnownLevel = KnownLevel.findByValue(i);
/*     */       }
/* 367 */       return localKnownLevel.mirroredLevel;
/*     */ 
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/*     */ 
/* 373 */       localKnownLevel = KnownLevel.findByLocalizedLevelName(paramString);
/* 374 */       if (localKnownLevel != null) {
/* 375 */         return localKnownLevel.mirroredLevel;
/*     */       }
/*     */     }
/* 378 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String toString()
/*     */   {
/* 388 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int intValue()
/*     */   {
/* 398 */     return this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object readResolve()
/*     */   {
/* 406 */     KnownLevel localKnownLevel = KnownLevel.matches(this);
/* 407 */     if (localKnownLevel != null) {
/* 408 */       return localKnownLevel.levelObject;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 413 */     Level localLevel = new Level(this.name, this.value, this.resourceBundleName);
/* 414 */     return localLevel;
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
/*     */   public static synchronized Level parse(String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/* 446 */     paramString.length();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 451 */     KnownLevel localKnownLevel = KnownLevel.findByName(paramString);
/* 452 */     if (localKnownLevel != null) {
/* 453 */       return localKnownLevel.levelObject;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 460 */       int i = Integer.parseInt(paramString);
/* 461 */       localKnownLevel = KnownLevel.findByValue(i);
/* 462 */       if (localKnownLevel == null)
/*     */       {
/* 464 */         Level localLevel = new Level(paramString, i);
/* 465 */         localKnownLevel = KnownLevel.findByValue(i);
/*     */       }
/* 467 */       return localKnownLevel.levelObject;
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/*     */ 
/*     */ 
/* 476 */       localKnownLevel = KnownLevel.findByLocalizedLevelName(paramString);
/* 477 */       if (localKnownLevel != null) {
/* 478 */         return localKnownLevel.levelObject;
/*     */       }
/*     */       
/*     */ 
/* 482 */       throw new IllegalArgumentException("Bad level \"" + paramString + "\"");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/* 492 */       Level localLevel = (Level)paramObject;
/* 493 */       return localLevel.value == this.value;
/*     */     } catch (Exception localException) {}
/* 495 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 505 */     return this.value;
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
/*     */   static final class KnownLevel
/*     */   {
/* 529 */     private static Map<String, List<KnownLevel>> nameToLevels = new HashMap();
/* 530 */     private static Map<Integer, List<KnownLevel>> intToLevels = new HashMap();
/*     */     final Level levelObject;
/*     */     final Level mirroredLevel;
/*     */     
/* 534 */     KnownLevel(Level paramLevel) { this.levelObject = paramLevel;
/* 535 */       if (paramLevel.getClass() == Level.class) {
/* 536 */         this.mirroredLevel = paramLevel;
/*     */       }
/*     */       else {
/* 539 */         this.mirroredLevel = new Level(paramLevel.name, paramLevel.value, paramLevel.resourceBundleName, false, null);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     static synchronized void add(Level paramLevel)
/*     */     {
/* 546 */       KnownLevel localKnownLevel = new KnownLevel(paramLevel);
/* 547 */       Object localObject = (List)nameToLevels.get(paramLevel.name);
/* 548 */       if (localObject == null) {
/* 549 */         localObject = new ArrayList();
/* 550 */         nameToLevels.put(paramLevel.name, localObject);
/*     */       }
/* 552 */       ((List)localObject).add(localKnownLevel);
/*     */       
/* 554 */       localObject = (List)intToLevels.get(Integer.valueOf(paramLevel.value));
/* 555 */       if (localObject == null) {
/* 556 */         localObject = new ArrayList();
/* 557 */         intToLevels.put(Integer.valueOf(paramLevel.value), localObject);
/*     */       }
/* 559 */       ((List)localObject).add(localKnownLevel);
/*     */     }
/*     */     
/*     */     static synchronized KnownLevel findByName(String paramString)
/*     */     {
/* 564 */       List localList = (List)nameToLevels.get(paramString);
/* 565 */       if (localList != null) {
/* 566 */         return (KnownLevel)localList.get(0);
/*     */       }
/* 568 */       return null;
/*     */     }
/*     */     
/*     */     static synchronized KnownLevel findByValue(int paramInt)
/*     */     {
/* 573 */       List localList = (List)intToLevels.get(Integer.valueOf(paramInt));
/* 574 */       if (localList != null) {
/* 575 */         return (KnownLevel)localList.get(0);
/*     */       }
/* 577 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static synchronized KnownLevel findByLocalizedLevelName(String paramString)
/*     */     {
/* 586 */       for (List localList : nameToLevels.values()) {
/* 587 */         for (KnownLevel localKnownLevel : localList) {
/* 588 */           String str = localKnownLevel.levelObject.getLocalizedLevelName();
/* 589 */           if (paramString.equals(str)) {
/* 590 */             return localKnownLevel;
/*     */           }
/*     */         }
/*     */       }
/* 594 */       return null;
/*     */     }
/*     */     
/*     */     static synchronized KnownLevel matches(Level paramLevel) {
/* 598 */       List localList = (List)nameToLevels.get(paramLevel.name);
/* 599 */       if (localList != null) {
/* 600 */         for (KnownLevel localKnownLevel : localList) {
/* 601 */           Level localLevel = localKnownLevel.mirroredLevel;
/* 602 */           if ((paramLevel.value == localLevel.value) && (
/* 603 */             (paramLevel.resourceBundleName == localLevel.resourceBundleName) || (
/* 604 */             (paramLevel.resourceBundleName != null) && 
/* 605 */             (paramLevel.resourceBundleName.equals(localLevel.resourceBundleName))))) {
/* 606 */             return localKnownLevel;
/*     */           }
/*     */         }
/*     */       }
/* 610 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/Level.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */