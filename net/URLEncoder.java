/*     */ package java.net;
/*     */ 
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.security.AccessController;
/*     */ import java.util.BitSet;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLEncoder
/*     */ {
/*     */   static BitSet dontNeedEncoding;
/*     */   static final int caseDiff = 32;
/*     */   
/*     */   static
/*     */   {
/* 125 */     dontNeedEncoding = new BitSet(256);
/*     */     
/* 127 */     for (int i = 97; i <= 122; i++) {
/* 128 */       dontNeedEncoding.set(i);
/*     */     }
/* 130 */     for (i = 65; i <= 90; i++) {
/* 131 */       dontNeedEncoding.set(i);
/*     */     }
/* 133 */     for (i = 48; i <= 57; i++) {
/* 134 */       dontNeedEncoding.set(i);
/*     */     }
/* 136 */     dontNeedEncoding.set(32);
/*     */     
/* 138 */     dontNeedEncoding.set(45);
/* 139 */     dontNeedEncoding.set(95);
/* 140 */     dontNeedEncoding.set(46);
/* 141 */     dontNeedEncoding.set(42); }
/*     */   
/* 143 */   static String dfltEncName = (String)AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static String encode(String paramString)
/*     */   {
/* 167 */     String str = null;
/*     */     try
/*     */     {
/* 170 */       str = encode(paramString, dfltEncName);
/*     */     }
/*     */     catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/*     */     
/*     */ 
/* 175 */     return str;
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
/*     */   public static String encode(String paramString1, String paramString2)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 203 */     int i = 0;
/* 204 */     StringBuffer localStringBuffer = new StringBuffer(paramString1.length());
/*     */     
/* 206 */     CharArrayWriter localCharArrayWriter = new CharArrayWriter();
/*     */     
/* 208 */     if (paramString2 == null)
/* 209 */       throw new NullPointerException("charsetName");
/*     */     Charset localCharset;
/*     */     try {
/* 212 */       localCharset = Charset.forName(paramString2);
/*     */     } catch (IllegalCharsetNameException localIllegalCharsetNameException) {
/* 214 */       throw new UnsupportedEncodingException(paramString2);
/*     */     } catch (UnsupportedCharsetException localUnsupportedCharsetException) {
/* 216 */       throw new UnsupportedEncodingException(paramString2);
/*     */     }
/*     */     
/* 219 */     for (int j = 0; j < paramString1.length();) {
/* 220 */       int k = paramString1.charAt(j);
/*     */       
/* 222 */       if (dontNeedEncoding.get(k)) {
/* 223 */         if (k == 32) {
/* 224 */           k = 43;
/* 225 */           i = 1;
/*     */         }
/*     */         
/* 228 */         localStringBuffer.append((char)k);
/* 229 */         j++;
/*     */       }
/*     */       else {
/*     */         do {
/* 233 */           localCharArrayWriter.write(k);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 242 */           if ((k >= 55296) && (k <= 56319))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 247 */             if (j + 1 < paramString1.length()) {
/* 248 */               int m = paramString1.charAt(j + 1);
/*     */               
/*     */ 
/*     */ 
/*     */ 
/* 253 */               if ((m >= 56320) && (m <= 57343))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 259 */                 localCharArrayWriter.write(m);
/* 260 */                 j++;
/*     */               }
/*     */             }
/*     */           }
/* 264 */           j++;
/* 265 */         } while ((j < paramString1.length()) && (!dontNeedEncoding.get(k = paramString1.charAt(j))));
/*     */         
/* 267 */         localCharArrayWriter.flush();
/* 268 */         String str = new String(localCharArrayWriter.toCharArray());
/* 269 */         byte[] arrayOfByte = str.getBytes(localCharset);
/* 270 */         for (int n = 0; n < arrayOfByte.length; n++) {
/* 271 */           localStringBuffer.append('%');
/* 272 */           char c = Character.forDigit(arrayOfByte[n] >> 4 & 0xF, 16);
/*     */           
/*     */ 
/* 275 */           if (Character.isLetter(c)) {
/* 276 */             c = (char)(c - ' ');
/*     */           }
/* 278 */           localStringBuffer.append(c);
/* 279 */           c = Character.forDigit(arrayOfByte[n] & 0xF, 16);
/* 280 */           if (Character.isLetter(c)) {
/* 281 */             c = (char)(c - ' ');
/*     */           }
/* 283 */           localStringBuffer.append(c);
/*     */         }
/* 285 */         localCharArrayWriter.reset();
/* 286 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/* 290 */     return i != 0 ? localStringBuffer.toString() : paramString1;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/URLEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */