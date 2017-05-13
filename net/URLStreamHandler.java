/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.net.util.IPAddressUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class URLStreamHandler
/*     */ {
/*     */   protected abstract URLConnection openConnection(URL paramURL)
/*     */     throws IOException;
/*     */   
/*     */   protected URLConnection openConnection(URL paramURL, Proxy paramProxy)
/*     */     throws IOException
/*     */   {
/*  96 */     throw new UnsupportedOperationException("Method not implemented.");
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
/*     */   protected void parseURL(URL paramURL, String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 126 */     String str1 = paramURL.getProtocol();
/* 127 */     String str2 = paramURL.getAuthority();
/* 128 */     String str3 = paramURL.getUserInfo();
/* 129 */     String str4 = paramURL.getHost();
/* 130 */     int i = paramURL.getPort();
/* 131 */     String str5 = paramURL.getPath();
/* 132 */     String str6 = paramURL.getQuery();
/*     */     
/*     */ 
/* 135 */     String str7 = paramURL.getRef();
/*     */     
/* 137 */     int j = 0;
/* 138 */     int k = 0;
/*     */     
/*     */ 
/*     */ 
/* 142 */     if (paramInt1 < paramInt2) {
/* 143 */       m = paramString.indexOf('?');
/* 144 */       k = m == paramInt1 ? 1 : 0;
/* 145 */       if ((m != -1) && (m < paramInt2)) {
/* 146 */         str6 = paramString.substring(m + 1, paramInt2);
/* 147 */         if (paramInt2 > m)
/* 148 */           paramInt2 = m;
/* 149 */         paramString = paramString.substring(0, m);
/*     */       }
/*     */     }
/*     */     
/* 153 */     int m = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 159 */     int n = (paramInt1 <= paramInt2 - 4) && (paramString.charAt(paramInt1) == '/') && (paramString.charAt(paramInt1 + 1) == '/') && (paramString.charAt(paramInt1 + 2) == '/') && (paramString.charAt(paramInt1 + 3) == '/') ? 1 : 0;
/* 160 */     int i1; String str9; if ((n == 0) && (paramInt1 <= paramInt2 - 2) && (paramString.charAt(paramInt1) == '/') && 
/* 161 */       (paramString.charAt(paramInt1 + 1) == '/')) {
/* 162 */       paramInt1 += 2;
/* 163 */       m = paramString.indexOf('/', paramInt1);
/* 164 */       if (m < 0) {
/* 165 */         m = paramString.indexOf('?', paramInt1);
/* 166 */         if (m < 0) {
/* 167 */           m = paramInt2;
/*     */         }
/*     */       }
/* 170 */       str4 = str2 = paramString.substring(paramInt1, m);
/*     */       
/* 172 */       i1 = str2.indexOf('@');
/* 173 */       if (i1 != -1) {
/* 174 */         str3 = str2.substring(0, i1);
/* 175 */         str4 = str2.substring(i1 + 1);
/*     */       } else {
/* 177 */         str3 = null;
/*     */       }
/* 179 */       if (str4 != null)
/*     */       {
/*     */ 
/* 182 */         if ((str4.length() > 0) && (str4.charAt(0) == '[')) {
/* 183 */           if ((i1 = str4.indexOf(']')) > 2)
/*     */           {
/* 185 */             str9 = str4;
/* 186 */             str4 = str9.substring(0, i1 + 1);
/*     */             
/* 188 */             if (!IPAddressUtil.isIPv6LiteralAddress(str4.substring(1, i1))) {
/* 189 */               throw new IllegalArgumentException("Invalid host: " + str4);
/*     */             }
/*     */             
/*     */ 
/* 193 */             i = -1;
/* 194 */             if (str9.length() > i1 + 1) {
/* 195 */               if (str9.charAt(i1 + 1) == ':') {
/* 196 */                 i1++;
/*     */                 
/* 198 */                 if (str9.length() > i1 + 1) {
/* 199 */                   i = Integer.parseInt(str9.substring(i1 + 1));
/*     */                 }
/*     */               } else {
/* 202 */                 throw new IllegalArgumentException("Invalid authority field: " + str2);
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 207 */             throw new IllegalArgumentException("Invalid authority field: " + str2);
/*     */           }
/*     */         }
/*     */         else {
/* 211 */           i1 = str4.indexOf(':');
/* 212 */           i = -1;
/* 213 */           if (i1 >= 0)
/*     */           {
/* 215 */             if (str4.length() > i1 + 1) {
/* 216 */               i = Integer.parseInt(str4.substring(i1 + 1));
/*     */             }
/* 218 */             str4 = str4.substring(0, i1);
/*     */           }
/*     */         }
/*     */       } else {
/* 222 */         str4 = "";
/*     */       }
/* 224 */       if (i < -1) {
/* 225 */         throw new IllegalArgumentException("Invalid port number :" + i);
/*     */       }
/* 227 */       paramInt1 = m;
/*     */       
/*     */ 
/* 230 */       if ((str2 != null) && (str2.length() > 0)) {
/* 231 */         str5 = "";
/*     */       }
/*     */     }
/* 234 */     if (str4 == null) {
/* 235 */       str4 = "";
/*     */     }
/*     */     
/*     */ 
/* 239 */     if (paramInt1 < paramInt2) {
/* 240 */       if (paramString.charAt(paramInt1) == '/') {
/* 241 */         str5 = paramString.substring(paramInt1, paramInt2);
/* 242 */       } else if ((str5 != null) && (str5.length() > 0)) {
/* 243 */         j = 1;
/* 244 */         i1 = str5.lastIndexOf('/');
/* 245 */         str9 = "";
/* 246 */         if ((i1 == -1) && (str2 != null)) {
/* 247 */           str9 = "/";
/*     */         }
/* 249 */         str5 = str5.substring(0, i1 + 1) + str9 + paramString.substring(paramInt1, paramInt2);
/*     */       }
/*     */       else {
/* 252 */         String str8 = str2 != null ? "/" : "";
/* 253 */         str5 = str8 + paramString.substring(paramInt1, paramInt2);
/*     */       }
/* 255 */     } else if ((k != 0) && (str5 != null)) {
/* 256 */       int i2 = str5.lastIndexOf('/');
/* 257 */       if (i2 < 0)
/* 258 */         i2 = 0;
/* 259 */       str5 = str5.substring(0, i2) + "/";
/*     */     }
/* 261 */     if (str5 == null) {
/* 262 */       str5 = "";
/*     */     }
/* 264 */     if (j != 0)
/*     */     {
/* 266 */       while ((m = str5.indexOf("/./")) >= 0) {
/* 267 */         str5 = str5.substring(0, m) + str5.substring(m + 2);
/*     */       }
/*     */       
/* 270 */       m = 0;
/* 271 */       while ((m = str5.indexOf("/../", m)) >= 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 278 */         if ((m > 0) && ((paramInt2 = str5.lastIndexOf('/', m - 1)) >= 0) && 
/* 279 */           (str5.indexOf("/../", paramInt2) != 0)) {
/* 280 */           str5 = str5.substring(0, paramInt2) + str5.substring(m + 3);
/* 281 */           m = 0;
/*     */         } else {
/* 283 */           m += 3;
/*     */         }
/*     */       }
/*     */       
/* 287 */       while (str5.endsWith("/..")) {
/* 288 */         m = str5.indexOf("/..");
/* 289 */         if ((paramInt2 = str5.lastIndexOf('/', m - 1)) < 0) break;
/* 290 */         str5 = str5.substring(0, paramInt2 + 1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 296 */       if ((str5.startsWith("./")) && (str5.length() > 2)) {
/* 297 */         str5 = str5.substring(2);
/*     */       }
/*     */       
/* 300 */       if (str5.endsWith("/.")) {
/* 301 */         str5 = str5.substring(0, str5.length() - 1);
/*     */       }
/*     */     }
/* 304 */     setURL(paramURL, str1, str4, i, str2, str3, str5, str6, str7);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getDefaultPort()
/*     */   {
/* 314 */     return -1;
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
/*     */   protected boolean equals(URL paramURL1, URL paramURL2)
/*     */   {
/* 330 */     String str1 = paramURL1.getRef();
/* 331 */     String str2 = paramURL2.getRef();
/*     */     
/* 333 */     return ((str1 == str2) || ((str1 != null) && (str1.equals(str2)))) && (sameFile(paramURL1, paramURL2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int hashCode(URL paramURL)
/*     */   {
/* 345 */     int i = 0;
/*     */     
/*     */ 
/* 348 */     String str1 = paramURL.getProtocol();
/* 349 */     if (str1 != null) {
/* 350 */       i += str1.hashCode();
/*     */     }
/*     */     
/* 353 */     InetAddress localInetAddress = getHostAddress(paramURL);
/* 354 */     if (localInetAddress != null) {
/* 355 */       i += localInetAddress.hashCode();
/*     */     } else {
/* 357 */       str2 = paramURL.getHost();
/* 358 */       if (str2 != null) {
/* 359 */         i += str2.toLowerCase().hashCode();
/*     */       }
/*     */     }
/*     */     
/* 363 */     String str2 = paramURL.getFile();
/* 364 */     if (str2 != null) {
/* 365 */       i += str2.hashCode();
/*     */     }
/*     */     
/* 368 */     if (paramURL.getPort() == -1) {
/* 369 */       i += getDefaultPort();
/*     */     } else {
/* 371 */       i += paramURL.getPort();
/*     */     }
/*     */     
/* 374 */     String str3 = paramURL.getRef();
/* 375 */     if (str3 != null) {
/* 376 */       i += str3.hashCode();
/*     */     }
/* 378 */     return i;
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
/*     */   protected boolean sameFile(URL paramURL1, URL paramURL2)
/*     */   {
/* 394 */     if ((paramURL1.getProtocol() != paramURL2.getProtocol()) && (
/* 395 */       (paramURL1.getProtocol() == null) || 
/* 396 */       (!paramURL1.getProtocol().equalsIgnoreCase(paramURL2.getProtocol())))) {
/* 397 */       return false;
/*     */     }
/*     */     
/* 400 */     if ((paramURL1.getFile() != paramURL2.getFile()) && (
/* 401 */       (paramURL1.getFile() == null) || (!paramURL1.getFile().equals(paramURL2.getFile())))) {
/* 402 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 406 */     int i = paramURL1.getPort() != -1 ? paramURL1.getPort() : paramURL1.handler.getDefaultPort();
/* 407 */     int j = paramURL2.getPort() != -1 ? paramURL2.getPort() : paramURL2.handler.getDefaultPort();
/* 408 */     if (i != j) {
/* 409 */       return false;
/*     */     }
/*     */     
/* 412 */     if (!hostsEqual(paramURL1, paramURL2)) {
/* 413 */       return false;
/*     */     }
/* 415 */     return true;
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
/*     */   protected synchronized InetAddress getHostAddress(URL paramURL)
/*     */   {
/* 428 */     if (paramURL.hostAddress != null) {
/* 429 */       return paramURL.hostAddress;
/*     */     }
/* 431 */     String str = paramURL.getHost();
/* 432 */     if ((str == null) || (str.equals(""))) {
/* 433 */       return null;
/*     */     }
/*     */     try {
/* 436 */       paramURL.hostAddress = InetAddress.getByName(str);
/*     */     } catch (UnknownHostException localUnknownHostException) {
/* 438 */       return null;
/*     */     } catch (SecurityException localSecurityException) {
/* 440 */       return null;
/*     */     }
/*     */     
/* 443 */     return paramURL.hostAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean hostsEqual(URL paramURL1, URL paramURL2)
/*     */   {
/* 455 */     InetAddress localInetAddress1 = getHostAddress(paramURL1);
/* 456 */     InetAddress localInetAddress2 = getHostAddress(paramURL2);
/*     */     
/* 458 */     if ((localInetAddress1 != null) && (localInetAddress2 != null)) {
/* 459 */       return localInetAddress1.equals(localInetAddress2);
/*     */     }
/* 461 */     if ((paramURL1.getHost() != null) && (paramURL2.getHost() != null)) {
/* 462 */       return paramURL1.getHost().equalsIgnoreCase(paramURL2.getHost());
/*     */     }
/* 464 */     return (paramURL1.getHost() == null) && (paramURL2.getHost() == null);
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
/*     */   protected String toExternalForm(URL paramURL)
/*     */   {
/* 477 */     int i = paramURL.getProtocol().length() + 1;
/* 478 */     if ((paramURL.getAuthority() != null) && (paramURL.getAuthority().length() > 0))
/* 479 */       i += 2 + paramURL.getAuthority().length();
/* 480 */     if (paramURL.getPath() != null) {
/* 481 */       i += paramURL.getPath().length();
/*     */     }
/* 483 */     if (paramURL.getQuery() != null) {
/* 484 */       i += 1 + paramURL.getQuery().length();
/*     */     }
/* 486 */     if (paramURL.getRef() != null) {
/* 487 */       i += 1 + paramURL.getRef().length();
/*     */     }
/* 489 */     StringBuffer localStringBuffer = new StringBuffer(i);
/* 490 */     localStringBuffer.append(paramURL.getProtocol());
/* 491 */     localStringBuffer.append(":");
/* 492 */     if ((paramURL.getAuthority() != null) && (paramURL.getAuthority().length() > 0)) {
/* 493 */       localStringBuffer.append("//");
/* 494 */       localStringBuffer.append(paramURL.getAuthority());
/*     */     }
/* 496 */     if (paramURL.getPath() != null) {
/* 497 */       localStringBuffer.append(paramURL.getPath());
/*     */     }
/* 499 */     if (paramURL.getQuery() != null) {
/* 500 */       localStringBuffer.append('?');
/* 501 */       localStringBuffer.append(paramURL.getQuery());
/*     */     }
/* 503 */     if (paramURL.getRef() != null) {
/* 504 */       localStringBuffer.append("#");
/* 505 */       localStringBuffer.append(paramURL.getRef());
/*     */     }
/* 507 */     return localStringBuffer.toString();
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
/*     */   protected void setURL(URL paramURL, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
/*     */   {
/* 532 */     if (this != paramURL.handler) {
/* 533 */       throw new SecurityException("handler for url different from this handler");
/*     */     }
/*     */     
/*     */ 
/* 537 */     paramURL.set(paramURL.getProtocol(), paramString2, paramInt, paramString3, paramString4, paramString5, paramString6, paramString7);
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
/*     */   @Deprecated
/*     */   protected void setURL(URL paramURL, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
/*     */   {
/* 563 */     String str1 = null;
/* 564 */     String str2 = null;
/* 565 */     if ((paramString2 != null) && (paramString2.length() != 0)) {
/* 566 */       str1 = paramString2 + ":" + paramInt;
/* 567 */       int i = paramString2.lastIndexOf('@');
/* 568 */       if (i != -1) {
/* 569 */         str2 = paramString2.substring(0, i);
/* 570 */         paramString2 = paramString2.substring(i + 1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 577 */     String str3 = null;
/* 578 */     String str4 = null;
/* 579 */     if (paramString3 != null) {
/* 580 */       int j = paramString3.lastIndexOf('?');
/* 581 */       if (j != -1) {
/* 582 */         str4 = paramString3.substring(j + 1);
/* 583 */         str3 = paramString3.substring(0, j);
/*     */       } else {
/* 585 */         str3 = paramString3;
/*     */       } }
/* 587 */     setURL(paramURL, paramString1, paramString2, paramInt, str1, str2, str3, str4, paramString4);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/URLStreamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */