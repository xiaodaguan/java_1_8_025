/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.security.Permission;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class URLPermission
/*     */   extends Permission
/*     */ {
/*     */   private static final long serialVersionUID = -2702463814894478682L;
/*     */   private transient String scheme;
/*     */   private transient String ssp;
/*     */   private transient String path;
/*     */   private transient List<String> methods;
/*     */   private transient List<String> requestHeaders;
/*     */   private transient Authority authority;
/*     */   private String actions;
/*     */   
/*     */   public URLPermission(String paramString1, String paramString2)
/*     */   {
/* 165 */     super(paramString1);
/* 166 */     init(paramString2);
/*     */   }
/*     */   
/*     */   private void init(String paramString) {
/* 170 */     parseURI(getName());
/* 171 */     int i = paramString.indexOf(':');
/* 172 */     if (paramString.lastIndexOf(':') != i) {
/* 173 */       throw new IllegalArgumentException("invalid actions string");
/*     */     }
/*     */     String str1;
/*     */     String str2;
/* 177 */     if (i == -1) {
/* 178 */       str1 = paramString;
/* 179 */       str2 = "";
/*     */     } else {
/* 181 */       str1 = paramString.substring(0, i);
/* 182 */       str2 = paramString.substring(i + 1);
/*     */     }
/*     */     
/* 185 */     List localList = normalizeMethods(str1);
/* 186 */     Collections.sort(localList);
/* 187 */     this.methods = Collections.unmodifiableList(localList);
/*     */     
/* 189 */     localList = normalizeHeaders(str2);
/* 190 */     Collections.sort(localList);
/* 191 */     this.requestHeaders = Collections.unmodifiableList(localList);
/*     */     
/* 193 */     this.actions = actions();
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
/*     */   public URLPermission(String paramString)
/*     */   {
/* 206 */     this(paramString, "*:*");
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
/*     */   public String getActions()
/*     */   {
/* 222 */     return this.actions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean implies(Permission paramPermission)
/*     */   {
/* 261 */     if (!(paramPermission instanceof URLPermission)) {
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     URLPermission localURLPermission = (URLPermission)paramPermission;
/*     */     
/* 267 */     if ((!((String)this.methods.get(0)).equals("*")) && 
/* 268 */       (Collections.indexOfSubList(this.methods, localURLPermission.methods) == -1)) {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     if ((this.requestHeaders.isEmpty()) && (!localURLPermission.requestHeaders.isEmpty())) {
/* 273 */       return false;
/*     */     }
/*     */     
/* 276 */     if ((!this.requestHeaders.isEmpty()) && 
/* 277 */       (!((String)this.requestHeaders.get(0)).equals("*")) && 
/* 278 */       (Collections.indexOfSubList(this.requestHeaders, localURLPermission.requestHeaders) == -1))
/*     */     {
/* 280 */       return false;
/*     */     }
/*     */     
/* 283 */     if (!this.scheme.equals(localURLPermission.scheme)) {
/* 284 */       return false;
/*     */     }
/*     */     
/* 287 */     if (this.ssp.equals("*")) {
/* 288 */       return true;
/*     */     }
/*     */     
/* 291 */     if (!this.authority.implies(localURLPermission.authority)) {
/* 292 */       return false;
/*     */     }
/*     */     
/* 295 */     if (this.path == null) {
/* 296 */       return localURLPermission.path == null;
/*     */     }
/* 298 */     if (localURLPermission.path == null) {
/* 299 */       return false;
/*     */     }
/*     */     String str1;
/* 302 */     if (this.path.endsWith("/-")) {
/* 303 */       str1 = this.path.substring(0, this.path.length() - 1);
/* 304 */       return localURLPermission.path.startsWith(str1);
/*     */     }
/*     */     
/* 307 */     if (this.path.endsWith("/*")) {
/* 308 */       str1 = this.path.substring(0, this.path.length() - 1);
/* 309 */       if (!localURLPermission.path.startsWith(str1)) {
/* 310 */         return false;
/*     */       }
/* 312 */       String str2 = localURLPermission.path.substring(str1.length());
/*     */       
/* 314 */       if (str2.indexOf('/') != -1) {
/* 315 */         return false;
/*     */       }
/* 317 */       if (str2.equals("-")) {
/* 318 */         return false;
/*     */       }
/* 320 */       return true;
/*     */     }
/* 322 */     return this.path.equals(localURLPermission.path);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 331 */     if (!(paramObject instanceof URLPermission)) {
/* 332 */       return false;
/*     */     }
/* 334 */     URLPermission localURLPermission = (URLPermission)paramObject;
/* 335 */     if (!this.scheme.equals(localURLPermission.scheme)) {
/* 336 */       return false;
/*     */     }
/* 338 */     if (!getActions().equals(localURLPermission.getActions())) {
/* 339 */       return false;
/*     */     }
/* 341 */     if (!this.authority.equals(localURLPermission.authority)) {
/* 342 */       return false;
/*     */     }
/* 344 */     if (this.path != null) {
/* 345 */       return this.path.equals(localURLPermission.path);
/*     */     }
/* 347 */     return localURLPermission.path == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 359 */     return getActions().hashCode() + this.scheme.hashCode() + this.authority.hashCode() + (this.path == null ? 0 : this.path.hashCode());
/*     */   }
/*     */   
/*     */   private List<String> normalizeMethods(String paramString)
/*     */   {
/* 364 */     ArrayList localArrayList = new ArrayList();
/* 365 */     StringBuilder localStringBuilder = new StringBuilder();
/* 366 */     for (int i = 0; i < paramString.length(); i++) {
/* 367 */       char c = paramString.charAt(i);
/* 368 */       if (c == ',') {
/* 369 */         String str2 = localStringBuilder.toString();
/* 370 */         if (str2.length() > 0)
/* 371 */           localArrayList.add(str2);
/* 372 */         localStringBuilder = new StringBuilder();
/* 373 */       } else { if ((c == ' ') || (c == '\t')) {
/* 374 */           throw new IllegalArgumentException("white space not allowed");
/*     */         }
/* 376 */         if ((c >= 'a') && (c <= 'z')) {
/* 377 */           c = (char)(c - ' ');
/*     */         }
/* 379 */         localStringBuilder.append(c);
/*     */       }
/*     */     }
/* 382 */     String str1 = localStringBuilder.toString();
/* 383 */     if (str1.length() > 0)
/* 384 */       localArrayList.add(str1);
/* 385 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private List<String> normalizeHeaders(String paramString) {
/* 389 */     ArrayList localArrayList = new ArrayList();
/* 390 */     StringBuilder localStringBuilder = new StringBuilder();
/* 391 */     int i = 1;
/* 392 */     for (int j = 0; j < paramString.length(); j++) {
/* 393 */       char c = paramString.charAt(j);
/* 394 */       if ((c >= 'a') && (c <= 'z')) {
/* 395 */         if (i != 0) {
/* 396 */           c = (char)(c - ' ');
/* 397 */           i = 0;
/*     */         }
/* 399 */         localStringBuilder.append(c);
/* 400 */       } else { if ((c == ' ') || (c == '\t'))
/* 401 */           throw new IllegalArgumentException("white space not allowed");
/* 402 */         if (c == '-') {
/* 403 */           i = 1;
/* 404 */           localStringBuilder.append(c);
/* 405 */         } else if (c == ',') {
/* 406 */           String str2 = localStringBuilder.toString();
/* 407 */           if (str2.length() > 0)
/* 408 */             localArrayList.add(str2);
/* 409 */           localStringBuilder = new StringBuilder();
/* 410 */           i = 1;
/*     */         } else {
/* 412 */           i = 0;
/* 413 */           localStringBuilder.append(c);
/*     */         }
/*     */       } }
/* 416 */     String str1 = localStringBuilder.toString();
/* 417 */     if (str1.length() > 0)
/* 418 */       localArrayList.add(str1);
/* 419 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private void parseURI(String paramString) {
/* 423 */     int i = paramString.length();
/* 424 */     int j = paramString.indexOf(':');
/* 425 */     if ((j == -1) || (j + 1 == i)) {
/* 426 */       throw new IllegalArgumentException("invalid URL string");
/*     */     }
/* 428 */     this.scheme = paramString.substring(0, j).toLowerCase();
/* 429 */     this.ssp = paramString.substring(j + 1);
/*     */     
/* 431 */     if (!this.ssp.startsWith("//")) {
/* 432 */       if (!this.ssp.equals("*")) {
/* 433 */         throw new IllegalArgumentException("invalid URL string");
/*     */       }
/* 435 */       this.authority = new Authority(this.scheme, "*");
/* 436 */       return;
/*     */     }
/* 438 */     String str1 = this.ssp.substring(2);
/*     */     
/* 440 */     j = str1.indexOf('/');
/*     */     String str2;
/* 442 */     if (j == -1) {
/* 443 */       this.path = "";
/* 444 */       str2 = str1;
/*     */     } else {
/* 446 */       str2 = str1.substring(0, j);
/* 447 */       this.path = str1.substring(j);
/*     */     }
/* 449 */     this.authority = new Authority(this.scheme, str2.toLowerCase());
/*     */   }
/*     */   
/*     */   private String actions() {
/* 453 */     StringBuilder localStringBuilder = new StringBuilder();
/* 454 */     for (Iterator localIterator = this.methods.iterator(); localIterator.hasNext();) { str = (String)localIterator.next();
/* 455 */       localStringBuilder.append(str); }
/*     */     String str;
/* 457 */     localStringBuilder.append(":");
/* 458 */     for (localIterator = this.requestHeaders.iterator(); localIterator.hasNext();) { str = (String)localIterator.next();
/* 459 */       localStringBuilder.append(str);
/*     */     }
/* 461 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 469 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 470 */     String str = (String)localGetField.get("actions", null);
/*     */     
/* 472 */     init(str);
/*     */   }
/*     */   
/*     */   static class Authority {
/*     */     HostPortrange p;
/*     */     
/*     */     Authority(String paramString1, String paramString2) {
/* 479 */       int i = paramString2.indexOf('@');
/* 480 */       if (i == -1) {
/* 481 */         this.p = new HostPortrange(paramString1, paramString2);
/*     */       } else {
/* 483 */         this.p = new HostPortrange(paramString1, paramString2.substring(i + 1));
/*     */       }
/*     */     }
/*     */     
/*     */     boolean implies(Authority paramAuthority) {
/* 488 */       return (impliesHostrange(paramAuthority)) && (impliesPortrange(paramAuthority));
/*     */     }
/*     */     
/*     */     private boolean impliesHostrange(Authority paramAuthority) {
/* 492 */       String str1 = this.p.hostname();
/* 493 */       String str2 = paramAuthority.p.hostname();
/*     */       
/* 495 */       if ((this.p.wildcard()) && (str1.equals("")))
/*     */       {
/* 497 */         return true;
/*     */       }
/* 499 */       if ((paramAuthority.p.wildcard()) && (str2.equals("")))
/*     */       {
/* 501 */         return false;
/*     */       }
/* 503 */       if (str1.equals(str2))
/*     */       {
/*     */ 
/* 506 */         return true;
/*     */       }
/* 508 */       if (this.p.wildcard())
/*     */       {
/* 510 */         return str2.endsWith(str1);
/*     */       }
/* 512 */       return false;
/*     */     }
/*     */     
/*     */     private boolean impliesPortrange(Authority paramAuthority) {
/* 516 */       int[] arrayOfInt1 = this.p.portrange();
/* 517 */       int[] arrayOfInt2 = paramAuthority.p.portrange();
/* 518 */       if (arrayOfInt1[0] == -1)
/*     */       {
/* 520 */         return true;
/*     */       }
/* 522 */       return (arrayOfInt1[0] <= arrayOfInt2[0]) && (arrayOfInt1[1] >= arrayOfInt2[1]);
/*     */     }
/*     */     
/*     */     boolean equals(Authority paramAuthority)
/*     */     {
/* 527 */       return this.p.equals(paramAuthority.p);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 531 */       return this.p.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/URLPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */