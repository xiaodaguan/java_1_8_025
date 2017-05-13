/*     */ package java.util.regex;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */  enum UnicodeProp
/*     */ {
/*  33 */   ALPHABETIC, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  39 */   LETTER, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  45 */   IDEOGRAPHIC, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  51 */   LOWERCASE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */   UPPERCASE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  63 */   TITLECASE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */   WHITE_SPACE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */   CONTROL, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */   PUNCTUATION, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 100 */   HEX_DIGIT, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */   ASSIGNED, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 120 */   NONCHARACTER_CODE_POINT, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 127 */   DIGIT, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */   ALNUM, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 142 */   BLANK, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 153 */   GRAPH, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 170 */   PRINT, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 179 */   WORD, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 198 */   JOIN_CONTROL;
/*     */   
/*     */   private static final HashMap<String, String> posix;
/*     */   private static final HashMap<String, String> aliases;
/*     */   
/*     */   static
/*     */   {
/* 205 */     posix = new HashMap();
/* 206 */     aliases = new HashMap();
/*     */     
/* 208 */     posix.put("ALPHA", "ALPHABETIC");
/* 209 */     posix.put("LOWER", "LOWERCASE");
/* 210 */     posix.put("UPPER", "UPPERCASE");
/* 211 */     posix.put("SPACE", "WHITE_SPACE");
/* 212 */     posix.put("PUNCT", "PUNCTUATION");
/* 213 */     posix.put("XDIGIT", "HEX_DIGIT");
/* 214 */     posix.put("ALNUM", "ALNUM");
/* 215 */     posix.put("CNTRL", "CONTROL");
/* 216 */     posix.put("DIGIT", "DIGIT");
/* 217 */     posix.put("BLANK", "BLANK");
/* 218 */     posix.put("GRAPH", "GRAPH");
/* 219 */     posix.put("PRINT", "PRINT");
/*     */     
/* 221 */     aliases.put("WHITESPACE", "WHITE_SPACE");
/* 222 */     aliases.put("HEXDIGIT", "HEX_DIGIT");
/* 223 */     aliases.put("NONCHARACTERCODEPOINT", "NONCHARACTER_CODE_POINT");
/* 224 */     aliases.put("JOINCONTROL", "JOIN_CONTROL");
/*     */   }
/*     */   
/*     */   public static UnicodeProp forName(String paramString) {
/* 228 */     paramString = paramString.toUpperCase(Locale.ENGLISH);
/* 229 */     String str = (String)aliases.get(paramString);
/* 230 */     if (str != null)
/* 231 */       paramString = str;
/*     */     try {
/* 233 */       return valueOf(paramString);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 235 */     return null;
/*     */   }
/*     */   
/*     */   public static UnicodeProp forPOSIXName(String paramString) {
/* 239 */     paramString = (String)posix.get(paramString.toUpperCase(Locale.ENGLISH));
/* 240 */     if (paramString == null)
/* 241 */       return null;
/* 242 */     return valueOf(paramString);
/*     */   }
/*     */   
/*     */   private UnicodeProp() {}
/*     */   
/*     */   public abstract boolean is(int paramInt);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/regex/UnicodeProp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */