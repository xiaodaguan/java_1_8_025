/*     */ package java.net;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InMemoryCookieStore
/*     */   implements CookieStore
/*     */ {
/*  48 */   private List<HttpCookie> cookieJar = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */   private Map<String, List<HttpCookie>> domainIndex = null;
/*  55 */   private Map<URI, List<HttpCookie>> uriIndex = null;
/*     */   
/*     */ 
/*  58 */   private ReentrantLock lock = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public InMemoryCookieStore()
/*     */   {
/*  65 */     this.cookieJar = new ArrayList();
/*  66 */     this.domainIndex = new HashMap();
/*  67 */     this.uriIndex = new HashMap();
/*     */     
/*  69 */     this.lock = new ReentrantLock(false);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void add(URI paramURI, HttpCookie paramHttpCookie)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: ifnonnull +13 -> 14
/*     */     //   4: new 12	java/lang/NullPointerException
/*     */     //   7: dup
/*     */     //   8: ldc 13
/*     */     //   10: invokespecial 14	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
/*     */     //   13: athrow
/*     */     //   14: aload_0
/*     */     //   15: getfield 5	java/net/InMemoryCookieStore:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   18: invokevirtual 15	java/util/concurrent/locks/ReentrantLock:lock	()V
/*     */     //   21: aload_0
/*     */     //   22: getfield 2	java/net/InMemoryCookieStore:cookieJar	Ljava/util/List;
/*     */     //   25: aload_2
/*     */     //   26: invokeinterface 16 2 0
/*     */     //   31: pop
/*     */     //   32: aload_2
/*     */     //   33: invokevirtual 17	java/net/HttpCookie:getMaxAge	()J
/*     */     //   36: lconst_0
/*     */     //   37: lcmp
/*     */     //   38: ifeq +52 -> 90
/*     */     //   41: aload_0
/*     */     //   42: getfield 2	java/net/InMemoryCookieStore:cookieJar	Ljava/util/List;
/*     */     //   45: aload_2
/*     */     //   46: invokeinterface 18 2 0
/*     */     //   51: pop
/*     */     //   52: aload_2
/*     */     //   53: invokevirtual 19	java/net/HttpCookie:getDomain	()Ljava/lang/String;
/*     */     //   56: ifnull +16 -> 72
/*     */     //   59: aload_0
/*     */     //   60: aload_0
/*     */     //   61: getfield 3	java/net/InMemoryCookieStore:domainIndex	Ljava/util/Map;
/*     */     //   64: aload_2
/*     */     //   65: invokevirtual 19	java/net/HttpCookie:getDomain	()Ljava/lang/String;
/*     */     //   68: aload_2
/*     */     //   69: invokespecial 20	java/net/InMemoryCookieStore:addIndex	(Ljava/util/Map;Ljava/lang/Object;Ljava/net/HttpCookie;)V
/*     */     //   72: aload_1
/*     */     //   73: ifnull +17 -> 90
/*     */     //   76: aload_0
/*     */     //   77: aload_0
/*     */     //   78: getfield 4	java/net/InMemoryCookieStore:uriIndex	Ljava/util/Map;
/*     */     //   81: aload_0
/*     */     //   82: aload_1
/*     */     //   83: invokespecial 21	java/net/InMemoryCookieStore:getEffectiveURI	(Ljava/net/URI;)Ljava/net/URI;
/*     */     //   86: aload_2
/*     */     //   87: invokespecial 20	java/net/InMemoryCookieStore:addIndex	(Ljava/util/Map;Ljava/lang/Object;Ljava/net/HttpCookie;)V
/*     */     //   90: aload_0
/*     */     //   91: getfield 5	java/net/InMemoryCookieStore:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   94: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   97: goto +13 -> 110
/*     */     //   100: astore_3
/*     */     //   101: aload_0
/*     */     //   102: getfield 5	java/net/InMemoryCookieStore:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   105: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   108: aload_3
/*     */     //   109: athrow
/*     */     //   110: return
/*     */     // Line number table:
/*     */     //   Java source line #77	-> byte code offset #0
/*     */     //   Java source line #78	-> byte code offset #4
/*     */     //   Java source line #82	-> byte code offset #14
/*     */     //   Java source line #85	-> byte code offset #21
/*     */     //   Java source line #88	-> byte code offset #32
/*     */     //   Java source line #89	-> byte code offset #41
/*     */     //   Java source line #91	-> byte code offset #52
/*     */     //   Java source line #92	-> byte code offset #59
/*     */     //   Java source line #94	-> byte code offset #72
/*     */     //   Java source line #96	-> byte code offset #76
/*     */     //   Java source line #100	-> byte code offset #90
/*     */     //   Java source line #101	-> byte code offset #97
/*     */     //   Java source line #100	-> byte code offset #100
/*     */     //   Java source line #102	-> byte code offset #110
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	111	0	this	InMemoryCookieStore
/*     */     //   0	111	1	paramURI	URI
/*     */     //   0	111	2	paramHttpCookie	HttpCookie
/*     */     //   100	9	3	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   21	90	100	finally
/*     */   }
/*     */   
/*     */   public List<HttpCookie> get(URI paramURI)
/*     */   {
/* 114 */     if (paramURI == null) {
/* 115 */       throw new NullPointerException("uri is null");
/*     */     }
/*     */     
/* 118 */     ArrayList localArrayList = new ArrayList();
/* 119 */     boolean bool = "https".equalsIgnoreCase(paramURI.getScheme());
/* 120 */     this.lock.lock();
/*     */     try
/*     */     {
/* 123 */       getInternal1(localArrayList, this.domainIndex, paramURI.getHost(), bool);
/*     */       
/* 125 */       getInternal2(localArrayList, this.uriIndex, getEffectiveURI(paramURI), bool);
/*     */     } finally {
/* 127 */       this.lock.unlock();
/*     */     }
/*     */     
/* 130 */     return localArrayList;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public List<HttpCookie> getCookies()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 5	java/net/InMemoryCookieStore:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   4: invokevirtual 15	java/util/concurrent/locks/ReentrantLock:lock	()V
/*     */     //   7: aload_0
/*     */     //   8: getfield 2	java/net/InMemoryCookieStore:cookieJar	Ljava/util/List;
/*     */     //   11: invokeinterface 30 1 0
/*     */     //   16: astore_2
/*     */     //   17: aload_2
/*     */     //   18: invokeinterface 31 1 0
/*     */     //   23: ifeq +27 -> 50
/*     */     //   26: aload_2
/*     */     //   27: invokeinterface 32 1 0
/*     */     //   32: checkcast 33	java/net/HttpCookie
/*     */     //   35: invokevirtual 34	java/net/HttpCookie:hasExpired	()Z
/*     */     //   38: ifeq -21 -> 17
/*     */     //   41: aload_2
/*     */     //   42: invokeinterface 35 1 0
/*     */     //   47: goto -30 -> 17
/*     */     //   50: aload_0
/*     */     //   51: getfield 2	java/net/InMemoryCookieStore:cookieJar	Ljava/util/List;
/*     */     //   54: invokestatic 36	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
/*     */     //   57: astore_1
/*     */     //   58: aload_0
/*     */     //   59: getfield 5	java/net/InMemoryCookieStore:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   62: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   65: goto +21 -> 86
/*     */     //   68: astore_3
/*     */     //   69: aload_0
/*     */     //   70: getfield 2	java/net/InMemoryCookieStore:cookieJar	Ljava/util/List;
/*     */     //   73: invokestatic 36	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
/*     */     //   76: astore_1
/*     */     //   77: aload_0
/*     */     //   78: getfield 5	java/net/InMemoryCookieStore:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   81: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   84: aload_3
/*     */     //   85: athrow
/*     */     //   86: aload_1
/*     */     //   87: areturn
/*     */     // Line number table:
/*     */     //   Java source line #139	-> byte code offset #0
/*     */     //   Java source line #141	-> byte code offset #7
/*     */     //   Java source line #142	-> byte code offset #17
/*     */     //   Java source line #143	-> byte code offset #26
/*     */     //   Java source line #144	-> byte code offset #41
/*     */     //   Java source line #148	-> byte code offset #50
/*     */     //   Java source line #149	-> byte code offset #58
/*     */     //   Java source line #150	-> byte code offset #65
/*     */     //   Java source line #148	-> byte code offset #68
/*     */     //   Java source line #149	-> byte code offset #77
/*     */     //   Java source line #152	-> byte code offset #86
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	88	0	this	InMemoryCookieStore
/*     */     //   57	30	1	localList	List
/*     */     //   16	26	2	localIterator	Iterator
/*     */     //   68	17	3	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	50	68	finally
/*     */   }
/*     */   
/*     */   public List<URI> getURIs()
/*     */   {
/* 160 */     ArrayList localArrayList = new ArrayList();
/*     */     
/* 162 */     this.lock.lock();
/*     */     try {
/* 164 */       Iterator localIterator = this.uriIndex.keySet().iterator();
/* 165 */       while (localIterator.hasNext()) {
/* 166 */         URI localURI = (URI)localIterator.next();
/* 167 */         List localList = (List)this.uriIndex.get(localURI);
/* 168 */         if ((localList == null) || (localList.size() == 0))
/*     */         {
/*     */ 
/* 171 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     } finally {
/* 175 */       localArrayList.addAll(this.uriIndex.keySet());
/* 176 */       this.lock.unlock();
/*     */     }
/*     */     
/* 179 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(URI paramURI, HttpCookie paramHttpCookie)
/*     */   {
/* 188 */     if (paramHttpCookie == null) {
/* 189 */       throw new NullPointerException("cookie is null");
/*     */     }
/*     */     
/* 192 */     boolean bool = false;
/* 193 */     this.lock.lock();
/*     */     try {
/* 195 */       bool = this.cookieJar.remove(paramHttpCookie);
/*     */     } finally {
/* 197 */       this.lock.unlock();
/*     */     }
/*     */     
/* 200 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeAll()
/*     */   {
/* 208 */     this.lock.lock();
/*     */     try {
/* 210 */       if (this.cookieJar.isEmpty()) {
/* 211 */         return false;
/*     */       }
/* 213 */       this.cookieJar.clear();
/* 214 */       this.domainIndex.clear();
/* 215 */       this.uriIndex.clear();
/*     */     } finally {
/* 217 */       this.lock.unlock();
/*     */     }
/*     */     
/* 220 */     return true;
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
/*     */   private boolean netscapeDomainMatches(String paramString1, String paramString2)
/*     */   {
/* 240 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 241 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 245 */     boolean bool = ".local".equalsIgnoreCase(paramString1);
/* 246 */     int i = paramString1.indexOf('.');
/* 247 */     if (i == 0) {
/* 248 */       i = paramString1.indexOf('.', 1);
/*     */     }
/* 250 */     if ((!bool) && ((i == -1) || (i == paramString1.length() - 1))) {
/* 251 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 255 */     int j = paramString2.indexOf('.');
/* 256 */     if ((j == -1) && (bool)) {
/* 257 */       return true;
/*     */     }
/*     */     
/* 260 */     int k = paramString1.length();
/* 261 */     int m = paramString2.length() - k;
/* 262 */     if (m == 0)
/*     */     {
/* 264 */       return paramString2.equalsIgnoreCase(paramString1); }
/* 265 */     if (m > 0)
/*     */     {
/* 267 */       String str1 = paramString2.substring(0, m);
/* 268 */       String str2 = paramString2.substring(m);
/*     */       
/* 270 */       return str2.equalsIgnoreCase(paramString1); }
/* 271 */     if (m == -1)
/*     */     {
/*     */ 
/* 274 */       return (paramString1.charAt(0) == '.') && (paramString2.equalsIgnoreCase(paramString1.substring(1)));
/*     */     }
/*     */     
/* 277 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void getInternal1(List<HttpCookie> paramList, Map<String, List<HttpCookie>> paramMap, String paramString, boolean paramBoolean)
/*     */   {
/* 284 */     ArrayList localArrayList = new ArrayList();
/* 285 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 286 */       String str = (String)localEntry.getKey();
/* 287 */       List localList = (List)localEntry.getValue();
/* 288 */       for (Iterator localIterator2 = localList.iterator(); localIterator2.hasNext();) { localHttpCookie = (HttpCookie)localIterator2.next();
/* 289 */         if (((localHttpCookie.getVersion() == 0) && (netscapeDomainMatches(str, paramString))) || (
/* 290 */           (localHttpCookie.getVersion() == 1) && (HttpCookie.domainMatches(str, paramString)))) {
/* 291 */           if (this.cookieJar.indexOf(localHttpCookie) != -1)
/*     */           {
/* 293 */             if (!localHttpCookie.hasExpired())
/*     */             {
/*     */ 
/* 296 */               if (((paramBoolean) || (!localHttpCookie.getSecure())) && 
/* 297 */                 (!paramList.contains(localHttpCookie))) {
/* 298 */                 paramList.add(localHttpCookie);
/*     */               }
/*     */             } else {
/* 301 */               localArrayList.add(localHttpCookie);
/*     */             }
/*     */             
/*     */           }
/*     */           else {
/* 306 */             localArrayList.add(localHttpCookie);
/*     */           }
/*     */         }
/*     */       }
/*     */       HttpCookie localHttpCookie;
/* 311 */       for (localIterator2 = localArrayList.iterator(); localIterator2.hasNext();) { localHttpCookie = (HttpCookie)localIterator2.next();
/* 312 */         localList.remove(localHttpCookie);
/* 313 */         this.cookieJar.remove(localHttpCookie);
/*     */       }
/*     */       
/* 316 */       localArrayList.clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private <T> void getInternal2(List<HttpCookie> paramList, Map<T, List<HttpCookie>> paramMap, Comparable<T> paramComparable, boolean paramBoolean)
/*     */   {
/* 328 */     for (Object localObject : paramMap.keySet()) {
/* 329 */       if (paramComparable.compareTo(localObject) == 0) {
/* 330 */         List localList = (List)paramMap.get(localObject);
/*     */         
/* 332 */         if (localList != null) {
/* 333 */           Iterator localIterator2 = localList.iterator();
/* 334 */           while (localIterator2.hasNext()) {
/* 335 */             HttpCookie localHttpCookie = (HttpCookie)localIterator2.next();
/* 336 */             if (this.cookieJar.indexOf(localHttpCookie) != -1)
/*     */             {
/* 338 */               if (!localHttpCookie.hasExpired())
/*     */               {
/* 340 */                 if (((paramBoolean) || (!localHttpCookie.getSecure())) && 
/* 341 */                   (!paramList.contains(localHttpCookie)))
/* 342 */                   paramList.add(localHttpCookie);
/*     */               } else {
/* 344 */                 localIterator2.remove();
/* 345 */                 this.cookieJar.remove(localHttpCookie);
/*     */               }
/*     */               
/*     */             }
/*     */             else {
/* 350 */               localIterator2.remove();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private <T> void addIndex(Map<T, List<HttpCookie>> paramMap, T paramT, HttpCookie paramHttpCookie)
/*     */   {
/* 363 */     if (paramT != null) {
/* 364 */       Object localObject = (List)paramMap.get(paramT);
/* 365 */       if (localObject != null)
/*     */       {
/* 367 */         ((List)localObject).remove(paramHttpCookie);
/*     */         
/* 369 */         ((List)localObject).add(paramHttpCookie);
/*     */       } else {
/* 371 */         localObject = new ArrayList();
/* 372 */         ((List)localObject).add(paramHttpCookie);
/* 373 */         paramMap.put(paramT, localObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private URI getEffectiveURI(URI paramURI)
/*     */   {
/* 384 */     URI localURI = null;
/*     */     try
/*     */     {
/* 387 */       localURI = new URI("http", paramURI.getHost(), null, null, null);
/*     */ 
/*     */     }
/*     */     catch (URISyntaxException localURISyntaxException)
/*     */     {
/*     */ 
/* 393 */       localURI = paramURI;
/*     */     }
/*     */     
/* 396 */     return localURI;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/InMemoryCookieStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */