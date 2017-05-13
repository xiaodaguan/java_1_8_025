/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ import sun.util.logging.PlatformLogger.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CookieManager
/*     */   extends CookieHandler
/*     */ {
/*     */   private CookiePolicy policyCallback;
/* 124 */   private CookieStore cookieJar = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CookieManager()
/*     */   {
/* 137 */     this(null, null);
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
/*     */   public CookieManager(CookieStore paramCookieStore, CookiePolicy paramCookiePolicy)
/*     */   {
/* 156 */     this.policyCallback = (paramCookiePolicy == null ? CookiePolicy.ACCEPT_ORIGINAL_SERVER : paramCookiePolicy);
/*     */     
/*     */ 
/*     */ 
/* 160 */     if (paramCookieStore == null) {
/* 161 */       this.cookieJar = new InMemoryCookieStore();
/*     */     } else {
/* 163 */       this.cookieJar = paramCookieStore;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCookiePolicy(CookiePolicy paramCookiePolicy)
/*     */   {
/* 181 */     if (paramCookiePolicy != null) { this.policyCallback = paramCookiePolicy;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CookieStore getCookieStore()
/*     */   {
/* 191 */     return this.cookieJar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap)
/*     */     throws IOException
/*     */   {
/* 200 */     if ((paramURI == null) || (paramMap == null)) {
/* 201 */       throw new IllegalArgumentException("Argument is null");
/*     */     }
/*     */     
/* 204 */     HashMap localHashMap = new HashMap();
/*     */     
/*     */ 
/* 207 */     if (this.cookieJar == null) {
/* 208 */       return Collections.unmodifiableMap(localHashMap);
/*     */     }
/* 210 */     boolean bool = "https".equalsIgnoreCase(paramURI.getScheme());
/* 211 */     ArrayList localArrayList = new ArrayList();
/* 212 */     String str1 = paramURI.getPath();
/* 213 */     if ((str1 == null) || (str1.isEmpty())) {
/* 214 */       str1 = "/";
/*     */     }
/* 216 */     for (Object localObject = this.cookieJar.get(paramURI).iterator(); ((Iterator)localObject).hasNext();) { HttpCookie localHttpCookie = (HttpCookie)((Iterator)localObject).next();
/*     */       
/*     */ 
/*     */ 
/* 220 */       if ((pathMatches(str1, localHttpCookie.getPath())) && ((bool) || 
/* 221 */         (!localHttpCookie.getSecure()))) {
/*     */         String str2;
/* 223 */         if (localHttpCookie.isHttpOnly()) {
/* 224 */           str2 = paramURI.getScheme();
/* 225 */           if ((!"http".equalsIgnoreCase(str2)) && (!"https".equalsIgnoreCase(str2))) {}
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 230 */           str2 = localHttpCookie.getPortlist();
/* 231 */           if ((str2 != null) && (!str2.isEmpty())) {
/* 232 */             int i = paramURI.getPort();
/* 233 */             if (i == -1) {
/* 234 */               i = "https".equals(paramURI.getScheme()) ? 443 : 80;
/*     */             }
/* 236 */             if (isInPortList(str2, i)) {
/* 237 */               localArrayList.add(localHttpCookie);
/*     */             }
/*     */           } else {
/* 240 */             localArrayList.add(localHttpCookie);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 246 */     localObject = sortByPath(localArrayList);
/*     */     
/* 248 */     localHashMap.put("Cookie", localObject);
/* 249 */     return Collections.unmodifiableMap(localHashMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void put(URI paramURI, Map<String, List<String>> paramMap)
/*     */     throws IOException
/*     */   {
/* 257 */     if ((paramURI == null) || (paramMap == null)) {
/* 258 */       throw new IllegalArgumentException("Argument is null");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 263 */     if (this.cookieJar == null) {
/* 264 */       return;
/*     */     }
/* 266 */     PlatformLogger localPlatformLogger = PlatformLogger.getLogger("java.net.CookieManager");
/* 267 */     for (String str1 : paramMap.keySet())
/*     */     {
/*     */ 
/* 270 */       if ((str1 != null) && (
/* 271 */         (str1.equalsIgnoreCase("Set-Cookie2")) || 
/* 272 */         (str1.equalsIgnoreCase("Set-Cookie"))))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 279 */         for (String str2 : (List)paramMap.get(str1)) {
/*     */           try {
/*     */             List localList;
/*     */             try {
/* 283 */               localList = HttpCookie.parse(str2);
/*     */             }
/*     */             catch (IllegalArgumentException localIllegalArgumentException2) {
/* 286 */               localList = Collections.emptyList();
/* 287 */               if (localPlatformLogger.isLoggable(PlatformLogger.Level.SEVERE)) {
/* 288 */                 localPlatformLogger.severe("Invalid cookie for " + paramURI + ": " + str2);
/*     */               }
/*     */             }
/* 291 */             for (HttpCookie localHttpCookie : localList) { int i;
/* 292 */               if (localHttpCookie.getPath() == null)
/*     */               {
/*     */ 
/* 295 */                 str3 = paramURI.getPath();
/* 296 */                 if (!str3.endsWith("/")) {
/* 297 */                   i = str3.lastIndexOf("/");
/* 298 */                   if (i > 0) {
/* 299 */                     str3 = str3.substring(0, i + 1);
/*     */                   } else {
/* 301 */                     str3 = "/";
/*     */                   }
/*     */                 }
/* 304 */                 localHttpCookie.setPath(str3);
/*     */               }
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 311 */               if (localHttpCookie.getDomain() == null) {
/* 312 */                 str3 = paramURI.getHost();
/* 313 */                 if ((str3 != null) && (!str3.contains(".")))
/* 314 */                   str3 = str3 + ".local";
/* 315 */                 localHttpCookie.setDomain(str3);
/*     */               }
/* 317 */               String str3 = localHttpCookie.getPortlist();
/* 318 */               if (str3 != null) {
/* 319 */                 i = paramURI.getPort();
/* 320 */                 if (i == -1) {
/* 321 */                   i = "https".equals(paramURI.getScheme()) ? 443 : 80;
/*     */                 }
/* 323 */                 if (str3.isEmpty())
/*     */                 {
/*     */ 
/* 326 */                   localHttpCookie.setPortlist("" + i);
/* 327 */                   if (shouldAcceptInternal(paramURI, localHttpCookie)) {
/* 328 */                     this.cookieJar.add(paramURI, localHttpCookie);
/*     */ 
/*     */                   }
/*     */                   
/*     */ 
/*     */                 }
/* 334 */                 else if ((isInPortList(str3, i)) && 
/* 335 */                   (shouldAcceptInternal(paramURI, localHttpCookie))) {
/* 336 */                   this.cookieJar.add(paramURI, localHttpCookie);
/*     */                 }
/*     */                 
/*     */               }
/* 340 */               else if (shouldAcceptInternal(paramURI, localHttpCookie)) {
/* 341 */                 this.cookieJar.add(paramURI, localHttpCookie);
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (IllegalArgumentException localIllegalArgumentException1) {}
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean shouldAcceptInternal(URI paramURI, HttpCookie paramHttpCookie)
/*     */   {
/*     */     try
/*     */     {
/* 359 */       return this.policyCallback.shouldAccept(paramURI, paramHttpCookie);
/*     */     } catch (Exception localException) {}
/* 361 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private static boolean isInPortList(String paramString, int paramInt)
/*     */   {
/* 367 */     int i = paramString.indexOf(",");
/* 368 */     int j = -1;
/* 369 */     while (i > 0) {
/*     */       try {
/* 371 */         j = Integer.parseInt(paramString.substring(0, i));
/* 372 */         if (j == paramInt) {
/* 373 */           return true;
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1) {}
/* 377 */       paramString = paramString.substring(i + 1);
/* 378 */       i = paramString.indexOf(",");
/*     */     }
/* 380 */     if (!paramString.isEmpty()) {
/*     */       try {
/* 382 */         j = Integer.parseInt(paramString);
/* 383 */         if (j == paramInt) {
/* 384 */           return true;
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException2) {}
/*     */     }
/* 389 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean pathMatches(String paramString1, String paramString2)
/*     */   {
/* 396 */     if (paramString1 == paramString2)
/* 397 */       return true;
/* 398 */     if ((paramString1 == null) || (paramString2 == null))
/* 399 */       return false;
/* 400 */     if (paramString1.startsWith(paramString2)) {
/* 401 */       return true;
/*     */     }
/* 403 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<String> sortByPath(List<HttpCookie> paramList)
/*     */   {
/* 412 */     Collections.sort(paramList, new CookiePathComparator());
/*     */     
/* 414 */     ArrayList localArrayList = new ArrayList();
/* 415 */     for (HttpCookie localHttpCookie : paramList)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 420 */       if ((paramList.indexOf(localHttpCookie) == 0) && (localHttpCookie.getVersion() > 0)) {
/* 421 */         localArrayList.add("$Version=\"1\"");
/*     */       }
/*     */       
/* 424 */       localArrayList.add(localHttpCookie.toString());
/*     */     }
/* 426 */     return localArrayList;
/*     */   }
/*     */   
/*     */   static class CookiePathComparator implements Comparator<HttpCookie>
/*     */   {
/*     */     public int compare(HttpCookie paramHttpCookie1, HttpCookie paramHttpCookie2) {
/* 432 */       if (paramHttpCookie1 == paramHttpCookie2) return 0;
/* 433 */       if (paramHttpCookie1 == null) return -1;
/* 434 */       if (paramHttpCookie2 == null) { return 1;
/*     */       }
/*     */       
/* 437 */       if (!paramHttpCookie1.getName().equals(paramHttpCookie2.getName())) { return 0;
/*     */       }
/*     */       
/* 440 */       if (paramHttpCookie1.getPath().startsWith(paramHttpCookie2.getPath()))
/* 441 */         return -1;
/* 442 */       if (paramHttpCookie2.getPath().startsWith(paramHttpCookie1.getPath())) {
/* 443 */         return 1;
/*     */       }
/* 445 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/CookieManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */