/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.ParseException;
/*     */ import sun.net.idn.Punycode;
/*     */ import sun.net.idn.StringPrep;
/*     */ import sun.text.normalizer.UCharacterIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IDN
/*     */ {
/*     */   public static final int ALLOW_UNASSIGNED = 1;
/*     */   public static final int USE_STD3_ASCII_RULES = 2;
/*     */   private static final String ACE_PREFIX = "xn--";
/*     */   private static final int ACE_PREFIX_LENGTH;
/*     */   private static final int MAX_LABEL_LENGTH = 63;
/*     */   private static StringPrep namePrep;
/*     */   
/*     */   public static String toASCII(String paramString, int paramInt)
/*     */   {
/* 113 */     int i = 0;int j = 0;
/* 114 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */     
/* 116 */     if (isRootLabel(paramString)) {
/* 117 */       return ".";
/*     */     }
/*     */     
/* 120 */     while (i < paramString.length()) {
/* 121 */       j = searchDots(paramString, i);
/* 122 */       localStringBuffer.append(toASCIIInternal(paramString.substring(i, j), paramInt));
/* 123 */       if (j != paramString.length())
/*     */       {
/* 125 */         localStringBuffer.append('.');
/*     */       }
/* 127 */       i = j + 1;
/*     */     }
/*     */     
/* 130 */     return localStringBuffer.toString();
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
/*     */   public static String toASCII(String paramString)
/*     */   {
/* 151 */     return toASCII(paramString, 0);
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
/*     */   public static String toUnicode(String paramString, int paramInt)
/*     */   {
/* 174 */     int i = 0;int j = 0;
/* 175 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */     
/* 177 */     if (isRootLabel(paramString)) {
/* 178 */       return ".";
/*     */     }
/*     */     
/* 181 */     while (i < paramString.length()) {
/* 182 */       j = searchDots(paramString, i);
/* 183 */       localStringBuffer.append(toUnicodeInternal(paramString.substring(i, j), paramInt));
/* 184 */       if (j != paramString.length())
/*     */       {
/* 186 */         localStringBuffer.append('.');
/*     */       }
/* 188 */       i = j + 1;
/*     */     }
/*     */     
/* 191 */     return localStringBuffer.toString();
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
/*     */   public static String toUnicode(String paramString)
/*     */   {
/* 210 */     return toUnicode(paramString, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 218 */     ACE_PREFIX_LENGTH = "xn--".length();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 223 */     namePrep = null;
/*     */     
/*     */ 
/* 226 */     InputStream localInputStream = null;
/*     */     
/*     */     try
/*     */     {
/* 230 */       if (System.getSecurityManager() != null) {
/* 231 */         localInputStream = (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public InputStream run() {
/* 233 */             return StringPrep.class.getResourceAsStream("uidna.spp");
/*     */           }
/*     */         });
/*     */       } else {
/* 237 */         localInputStream = StringPrep.class.getResourceAsStream("uidna.spp");
/*     */       }
/*     */       
/* 240 */       namePrep = new StringPrep(localInputStream);
/* 241 */       localInputStream.close();
/*     */     }
/*     */     catch (IOException localIOException) {
/* 244 */       if (!$assertionsDisabled) { throw new AssertionError();
/*     */       }
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
/*     */ 
/*     */   private static String toASCIIInternal(String paramString, int paramInt)
/*     */   {
/* 264 */     boolean bool = isAllASCII(paramString);
/*     */     
/*     */ 
/*     */     StringBuffer localStringBuffer;
/*     */     
/* 269 */     if (!bool) {
/* 270 */       UCharacterIterator localUCharacterIterator = UCharacterIterator.getInstance(paramString);
/*     */       try {
/* 272 */         localStringBuffer = namePrep.prepare(localUCharacterIterator, paramInt);
/*     */       } catch (ParseException localParseException1) {
/* 274 */         throw new IllegalArgumentException(localParseException1);
/*     */       }
/*     */     } else {
/* 277 */       localStringBuffer = new StringBuffer(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 282 */     if (localStringBuffer.length() == 0) {
/* 283 */       throw new IllegalArgumentException("Empty label is not a legal name");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 291 */     int i = (paramInt & 0x2) != 0 ? 1 : 0;
/* 292 */     if (i != 0) {
/* 293 */       for (int j = 0; j < localStringBuffer.length(); j++) {
/* 294 */         int k = localStringBuffer.charAt(j);
/* 295 */         if (isNonLDHAsciiCodePoint(k)) {
/* 296 */           throw new IllegalArgumentException("Contains non-LDH ASCII characters");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 301 */       if ((localStringBuffer.charAt(0) == '-') || 
/* 302 */         (localStringBuffer.charAt(localStringBuffer.length() - 1) == '-'))
/*     */       {
/* 304 */         throw new IllegalArgumentException("Has leading or trailing hyphen");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 309 */     if (!bool)
/*     */     {
/*     */ 
/* 312 */       if (!isAllASCII(localStringBuffer.toString()))
/*     */       {
/*     */ 
/* 315 */         if (!startsWithACEPrefix(localStringBuffer))
/*     */         {
/*     */ 
/*     */           try
/*     */           {
/* 320 */             localStringBuffer = Punycode.encode(localStringBuffer, null);
/*     */           } catch (ParseException localParseException2) {
/* 322 */             throw new IllegalArgumentException(localParseException2);
/*     */           }
/*     */           
/* 325 */           localStringBuffer = toASCIILower(localStringBuffer);
/*     */           
/*     */ 
/*     */ 
/* 329 */           localStringBuffer.insert(0, "xn--");
/*     */         } else {
/* 331 */           throw new IllegalArgumentException("The input starts with the ACE Prefix");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 339 */     if (localStringBuffer.length() > 63) {
/* 340 */       throw new IllegalArgumentException("The label in the input is too long");
/*     */     }
/*     */     
/* 343 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static String toUnicodeInternal(String paramString, int paramInt)
/*     */   {
/* 350 */     Object localObject = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 355 */     boolean bool = isAllASCII(paramString);
/*     */     StringBuffer localStringBuffer1;
/* 357 */     if (!bool)
/*     */     {
/*     */       try
/*     */       {
/* 361 */         UCharacterIterator localUCharacterIterator = UCharacterIterator.getInstance(paramString);
/* 362 */         localStringBuffer1 = namePrep.prepare(localUCharacterIterator, paramInt);
/*     */       }
/*     */       catch (Exception localException1) {
/* 365 */         return paramString;
/*     */       }
/*     */     } else {
/* 368 */       localStringBuffer1 = new StringBuffer(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 373 */     if (startsWithACEPrefix(localStringBuffer1))
/*     */     {
/*     */ 
/*     */ 
/* 377 */       String str1 = localStringBuffer1.substring(ACE_PREFIX_LENGTH, localStringBuffer1.length());
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 382 */         StringBuffer localStringBuffer2 = Punycode.decode(new StringBuffer(str1), null);
/*     */         
/*     */ 
/*     */ 
/* 386 */         String str2 = toASCII(localStringBuffer2.toString(), paramInt);
/*     */         
/*     */ 
/*     */ 
/* 390 */         if (str2.equalsIgnoreCase(localStringBuffer1.toString()))
/*     */         {
/*     */ 
/* 393 */           return localStringBuffer2.toString();
/*     */         }
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 401 */     return paramString;
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
/*     */   private static boolean isNonLDHAsciiCodePoint(int paramInt)
/*     */   {
/* 415 */     return ((0 <= paramInt) && (paramInt <= 44)) || ((46 <= paramInt) && (paramInt <= 47)) || ((58 <= paramInt) && (paramInt <= 64)) || ((91 <= paramInt) && (paramInt <= 96)) || ((123 <= paramInt) && (paramInt <= 127));
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
/*     */   private static int searchDots(String paramString, int paramInt)
/*     */   {
/* 430 */     for (int i = paramInt; i < paramString.length(); i++) {
/* 431 */       if (isLabelSeparator(paramString.charAt(i))) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 436 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean isRootLabel(String paramString)
/*     */   {
/* 443 */     return (paramString.length() == 1) && (isLabelSeparator(paramString.charAt(0)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean isLabelSeparator(char paramChar)
/*     */   {
/* 450 */     return (paramChar == '.') || (paramChar == '。') || (paramChar == 65294) || (paramChar == 65377);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean isAllASCII(String paramString)
/*     */   {
/* 457 */     boolean bool = true;
/* 458 */     for (int i = 0; i < paramString.length(); i++) {
/* 459 */       int j = paramString.charAt(i);
/* 460 */       if (j > 127) {
/* 461 */         bool = false;
/* 462 */         break;
/*     */       }
/*     */     }
/* 465 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean startsWithACEPrefix(StringBuffer paramStringBuffer)
/*     */   {
/* 472 */     boolean bool = true;
/*     */     
/* 474 */     if (paramStringBuffer.length() < ACE_PREFIX_LENGTH) {
/* 475 */       return false;
/*     */     }
/* 477 */     for (int i = 0; i < ACE_PREFIX_LENGTH; i++) {
/* 478 */       if (toASCIILower(paramStringBuffer.charAt(i)) != "xn--".charAt(i)) {
/* 479 */         bool = false;
/*     */       }
/*     */     }
/* 482 */     return bool;
/*     */   }
/*     */   
/*     */   private static char toASCIILower(char paramChar) {
/* 486 */     if (('A' <= paramChar) && (paramChar <= 'Z')) {
/* 487 */       return (char)(paramChar + 'a' - 65);
/*     */     }
/* 489 */     return paramChar;
/*     */   }
/*     */   
/*     */   private static StringBuffer toASCIILower(StringBuffer paramStringBuffer) {
/* 493 */     StringBuffer localStringBuffer = new StringBuffer();
/* 494 */     for (int i = 0; i < paramStringBuffer.length(); i++) {
/* 495 */       localStringBuffer.append(toASCIILower(paramStringBuffer.charAt(i)));
/*     */     }
/* 497 */     return localStringBuffer;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/IDN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */