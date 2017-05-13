/*     */ package java.io;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ExpiringCache
/*     */ {
/*     */   private long millisUntilExpiration;
/*     */   private Map<String, Entry> map;
/*     */   private int queryCount;
/*  41 */   private int queryOverflow = 300;
/*  42 */   private int MAX_ENTRIES = 200;
/*     */   
/*     */   static class Entry {
/*     */     private long timestamp;
/*     */     private String val;
/*     */     
/*     */     Entry(long paramLong, String paramString) {
/*  49 */       this.timestamp = paramLong;
/*  50 */       this.val = paramString;
/*     */     }
/*     */     
/*  53 */     long timestamp() { return this.timestamp; }
/*  54 */     void setTimestamp(long paramLong) { this.timestamp = paramLong; }
/*     */     
/*  56 */     String val() { return this.val; }
/*  57 */     void setVal(String paramString) { this.val = paramString; }
/*     */   }
/*     */   
/*     */   ExpiringCache() {
/*  61 */     this(30000L);
/*     */   }
/*     */   
/*     */   ExpiringCache(long paramLong)
/*     */   {
/*  66 */     this.millisUntilExpiration = paramLong;
/*  67 */     this.map = new LinkedHashMap() {
/*     */       protected boolean removeEldestEntry(Map.Entry<String, ExpiringCache.Entry> paramAnonymousEntry) {
/*  69 */         return size() > ExpiringCache.this.MAX_ENTRIES;
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   synchronized String get(String paramString) {
/*  75 */     if (++this.queryCount >= this.queryOverflow) {
/*  76 */       cleanup();
/*     */     }
/*  78 */     Entry localEntry = entryFor(paramString);
/*  79 */     if (localEntry != null) {
/*  80 */       return localEntry.val();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   synchronized void put(String paramString1, String paramString2) {
/*  86 */     if (++this.queryCount >= this.queryOverflow) {
/*  87 */       cleanup();
/*     */     }
/*  89 */     Entry localEntry = entryFor(paramString1);
/*  90 */     if (localEntry != null) {
/*  91 */       localEntry.setTimestamp(System.currentTimeMillis());
/*  92 */       localEntry.setVal(paramString2);
/*     */     } else {
/*  94 */       this.map.put(paramString1, new Entry(System.currentTimeMillis(), paramString2));
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized void clear() {
/*  99 */     this.map.clear();
/*     */   }
/*     */   
/*     */   private Entry entryFor(String paramString) {
/* 103 */     Entry localEntry = (Entry)this.map.get(paramString);
/* 104 */     if (localEntry != null) {
/* 105 */       long l = System.currentTimeMillis() - localEntry.timestamp();
/* 106 */       if ((l < 0L) || (l >= this.millisUntilExpiration)) {
/* 107 */         this.map.remove(paramString);
/* 108 */         localEntry = null;
/*     */       }
/*     */     }
/* 111 */     return localEntry;
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 115 */     Set localSet = this.map.keySet();
/*     */     
/* 117 */     String[] arrayOfString = new String[localSet.size()];
/* 118 */     int i = 0;
/* 119 */     for (String str : localSet) {
/* 120 */       arrayOfString[(i++)] = str;
/*     */     }
/* 122 */     for (int j = 0; j < arrayOfString.length; j++) {
/* 123 */       entryFor(arrayOfString[j]);
/*     */     }
/* 125 */     this.queryCount = 0;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/ExpiringCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */