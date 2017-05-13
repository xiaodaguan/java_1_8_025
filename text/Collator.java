/*     */ package java.text;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.util.Comparator;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Collator
/*     */   implements Comparator<Object>, Cloneable
/*     */ {
/*     */   public static final int PRIMARY = 0;
/*     */   public static final int SECONDARY = 1;
/*     */   public static final int TERTIARY = 2;
/*     */   public static final int IDENTICAL = 3;
/*     */   public static final int NO_DECOMPOSITION = 0;
/*     */   public static final int CANONICAL_DECOMPOSITION = 1;
/*     */   public static final int FULL_DECOMPOSITION = 2;
/*     */   
/*     */   public static synchronized Collator getInstance()
/*     */   {
/* 224 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Collator getInstance(Locale paramLocale)
/*     */   {
/* 235 */     SoftReference localSoftReference = (SoftReference)cache.get(paramLocale);
/* 236 */     Object localObject = localSoftReference != null ? (Collator)localSoftReference.get() : null;
/* 237 */     if (localObject == null)
/*     */     {
/* 239 */       LocaleProviderAdapter localLocaleProviderAdapter = LocaleProviderAdapter.getAdapter(CollatorProvider.class, paramLocale);
/*     */       
/* 241 */       CollatorProvider localCollatorProvider = localLocaleProviderAdapter.getCollatorProvider();
/* 242 */       localObject = localCollatorProvider.getInstance(paramLocale);
/* 243 */       if (localObject == null)
/*     */       {
/* 245 */         localObject = LocaleProviderAdapter.forJRE().getCollatorProvider().getInstance(paramLocale);
/*     */       }
/*     */       for (;;) {
/* 248 */         if (localSoftReference != null)
/*     */         {
/* 250 */           cache.remove(paramLocale, localSoftReference);
/*     */         }
/* 252 */         localSoftReference = (SoftReference)cache.putIfAbsent(paramLocale, new SoftReference(localObject));
/* 253 */         if (localSoftReference == null) {
/*     */           break;
/*     */         }
/* 256 */         Collator localCollator = (Collator)localSoftReference.get();
/* 257 */         if (localCollator != null) {
/* 258 */           localObject = localCollator;
/* 259 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 263 */     return (Collator)((Collator)localObject).clone();
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
/*     */   public abstract int compare(String paramString1, String paramString2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int compare(Object paramObject1, Object paramObject2)
/*     */   {
/* 304 */     return compare((String)paramObject1, (String)paramObject2);
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
/*     */   public abstract CollationKey getCollationKey(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(String paramString1, String paramString2)
/*     */   {
/* 331 */     return compare(paramString1, paramString2) == 0;
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
/*     */   public synchronized int getStrength()
/*     */   {
/* 347 */     return this.strength;
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
/*     */   public synchronized void setStrength(int paramInt)
/*     */   {
/* 364 */     if ((paramInt != 0) && (paramInt != 1) && (paramInt != 2) && (paramInt != 3))
/*     */     {
/*     */ 
/*     */ 
/* 368 */       throw new IllegalArgumentException("Incorrect comparison level.");
/*     */     }
/* 370 */     this.strength = paramInt;
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
/*     */   public synchronized int getDecomposition()
/*     */   {
/* 394 */     return this.decmp;
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
/*     */   public synchronized void setDecomposition(int paramInt)
/*     */   {
/* 408 */     if ((paramInt != 0) && (paramInt != 1) && (paramInt != 2))
/*     */     {
/*     */ 
/* 411 */       throw new IllegalArgumentException("Wrong decomposition mode.");
/*     */     }
/* 413 */     this.decmp = paramInt;
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
/*     */   public static synchronized Locale[] getAvailableLocales()
/*     */   {
/* 431 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(CollatorProvider.class);
/* 432 */     return localLocaleServiceProviderPool.getAvailableLocales();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 442 */       return (Collator)super.clone();
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 444 */       throw new InternalError(localCloneNotSupportedException);
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 457 */     if (this == paramObject) {
/* 458 */       return true;
/*     */     }
/* 460 */     if (paramObject == null) {
/* 461 */       return false;
/*     */     }
/* 463 */     if (getClass() != paramObject.getClass()) {
/* 464 */       return false;
/*     */     }
/* 466 */     Collator localCollator = (Collator)paramObject;
/* 467 */     return (this.strength == localCollator.strength) && (this.decmp == localCollator.decmp);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int hashCode();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Collator()
/*     */   {
/* 485 */     this.strength = 2;
/* 486 */     this.decmp = 1;
/*     */   }
/*     */   
/* 489 */   private int strength = 0;
/* 490 */   private int decmp = 0;
/* 491 */   private static final ConcurrentMap<Locale, SoftReference<Collator>> cache = new ConcurrentHashMap();
/*     */   static final int LESS = -1;
/*     */   static final int EQUAL = 0;
/*     */   static final int GREATER = 1;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/Collator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */