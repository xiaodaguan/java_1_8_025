/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PatternEntry
/*     */ {
/*     */   static final int RESET = -2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int UNSET = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void appendQuotedExtension(StringBuffer paramStringBuffer)
/*     */   {
/*  56 */     appendQuoted(this.extension, paramStringBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void appendQuotedChars(StringBuffer paramStringBuffer)
/*     */   {
/*  63 */     appendQuoted(this.chars, paramStringBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  72 */     if (paramObject == null) return false;
/*  73 */     PatternEntry localPatternEntry = (PatternEntry)paramObject;
/*  74 */     boolean bool = this.chars.equals(localPatternEntry.chars);
/*  75 */     return bool;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  79 */     return this.chars.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer localStringBuffer = new StringBuffer();
/*  87 */     addToBuffer(localStringBuffer, true, false, null);
/*  88 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final int getStrength()
/*     */   {
/*  95 */     return this.strength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final String getExtension()
/*     */   {
/* 102 */     return this.extension;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final String getChars()
/*     */   {
/* 109 */     return this.chars;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void addToBuffer(StringBuffer paramStringBuffer, boolean paramBoolean1, boolean paramBoolean2, PatternEntry paramPatternEntry)
/*     */   {
/* 119 */     if ((paramBoolean2) && (paramStringBuffer.length() > 0))
/* 120 */       if ((this.strength == 0) || (paramPatternEntry != null)) {
/* 121 */         paramStringBuffer.append('\n');
/*     */       } else
/* 123 */         paramStringBuffer.append(' ');
/* 124 */     if (paramPatternEntry != null) {
/* 125 */       paramStringBuffer.append('&');
/* 126 */       if (paramBoolean2)
/* 127 */         paramStringBuffer.append(' ');
/* 128 */       paramPatternEntry.appendQuotedChars(paramStringBuffer);
/* 129 */       appendQuotedExtension(paramStringBuffer);
/* 130 */       if (paramBoolean2)
/* 131 */         paramStringBuffer.append(' ');
/*     */     }
/* 133 */     switch (this.strength) {
/* 134 */     case 3:  paramStringBuffer.append('='); break;
/* 135 */     case 2:  paramStringBuffer.append(','); break;
/* 136 */     case 1:  paramStringBuffer.append(';'); break;
/* 137 */     case 0:  paramStringBuffer.append('<'); break;
/* 138 */     case -2:  paramStringBuffer.append('&'); break;
/* 139 */     case -1:  paramStringBuffer.append('?');
/*     */     }
/* 141 */     if (paramBoolean2)
/* 142 */       paramStringBuffer.append(' ');
/* 143 */     appendQuoted(this.chars, paramStringBuffer);
/* 144 */     if ((paramBoolean1) && (this.extension.length() != 0)) {
/* 145 */       paramStringBuffer.append('/');
/* 146 */       appendQuoted(this.extension, paramStringBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   static void appendQuoted(String paramString, StringBuffer paramStringBuffer) {
/* 151 */     int i = 0;
/* 152 */     char c = paramString.charAt(0);
/* 153 */     if (Character.isSpaceChar(c)) {
/* 154 */       i = 1;
/* 155 */       paramStringBuffer.append('\'');
/*     */     }
/* 157 */     else if (isSpecialChar(c)) {
/* 158 */       i = 1;
/* 159 */       paramStringBuffer.append('\'');
/*     */     } else {
/* 161 */       switch (c) {
/*     */       case '\t': case '\n': case '\f': 
/*     */       case '\r': case '\020': case '@': 
/* 164 */         i = 1;
/* 165 */         paramStringBuffer.append('\'');
/* 166 */         break;
/*     */       case '\'': 
/* 168 */         i = 1;
/* 169 */         paramStringBuffer.append('\'');
/* 170 */         break;
/*     */       default: 
/* 172 */         if (i != 0) {
/* 173 */           i = 0;paramStringBuffer.append('\'');
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/* 179 */     paramStringBuffer.append(paramString);
/* 180 */     if (i != 0) {
/* 181 */       paramStringBuffer.append('\'');
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PatternEntry(int paramInt, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
/*     */   {
/* 192 */     this.strength = paramInt;
/* 193 */     this.chars = paramStringBuffer1.toString();
/* 194 */     this.extension = (paramStringBuffer2.length() > 0 ? paramStringBuffer2.toString() : "");
/*     */   }
/*     */   
/*     */   static class Parser
/*     */   {
/*     */     private String pattern;
/*     */     private int i;
/*     */     
/*     */     public Parser(String paramString) {
/* 203 */       this.pattern = paramString;
/* 204 */       this.i = 0;
/*     */     }
/*     */     
/*     */     public PatternEntry next() throws ParseException {
/* 208 */       int j = -1;
/*     */       
/* 210 */       this.newChars.setLength(0);
/* 211 */       this.newExtension.setLength(0);
/*     */       
/* 213 */       int k = 1;
/* 214 */       int m = 0;
/*     */       
/* 216 */       while (this.i < this.pattern.length()) {
/* 217 */         char c = this.pattern.charAt(this.i);
/* 218 */         if (m != 0) {
/* 219 */           if (c == '\'') {
/* 220 */             m = 0;
/*     */           }
/* 222 */           else if (this.newChars.length() == 0) { this.newChars.append(c);
/* 223 */           } else if (k != 0) this.newChars.append(c); else
/* 224 */             this.newExtension.append(c);
/*     */         } else {
/* 226 */           switch (c) {
/* 227 */           case '=':  if (j != -1) break label546;
/* 228 */             j = 3; break;
/* 229 */           case ',':  if (j != -1) break label546;
/* 230 */             j = 2; break;
/* 231 */           case ';':  if (j != -1) break label546;
/* 232 */             j = 1; break;
/* 233 */           case '<':  if (j != -1) break label546;
/* 234 */             j = 0; break;
/* 235 */           case '&':  if (j != -1) break label546;
/* 236 */             j = -2; break;
/*     */           case '\t': case '\n': 
/*     */           case '\f': case '\r': 
/*     */           case ' ': 
/*     */             break;
/*     */           case '/': 
/* 242 */             k = 0; break;
/*     */           case '\'': 
/* 244 */             m = 1;
/* 245 */             c = this.pattern.charAt(++this.i);
/* 246 */             if (this.newChars.length() == 0) { this.newChars.append(c);
/* 247 */             } else if (k != 0) this.newChars.append(c); else
/* 248 */               this.newExtension.append(c);
/* 249 */             break;
/*     */           default: 
/* 251 */             if (j == -1)
/*     */             {
/*     */ 
/* 254 */               throw new ParseException("missing char (=,;<&) : " + this.pattern.substring(this.i, this.i + 10 < this.pattern
/* 255 */                 .length() ? this.i + 10 : this.pattern
/* 256 */                 .length()), this.i);
/*     */             }
/*     */             
/* 259 */             if ((PatternEntry.isSpecialChar(c)) && (m == 0))
/*     */             {
/* 261 */               throw new ParseException("Unquoted punctuation character : " + Integer.toString(c, 16), this.i); }
/* 262 */             if (k != 0) {
/* 263 */               this.newChars.append(c);
/*     */             } else
/* 265 */               this.newExtension.append(c);
/*     */             break;
/*     */           }
/*     */         }
/* 269 */         this.i += 1; }
/*     */       label546:
/* 271 */       if (j == -1)
/* 272 */         return null;
/* 273 */       if (this.newChars.length() == 0)
/*     */       {
/*     */ 
/* 276 */         throw new ParseException("missing chars (=,;<&): " + this.pattern.substring(this.i, this.i + 10 < this.pattern
/* 277 */           .length() ? this.i + 10 : this.pattern
/* 278 */           .length()), this.i);
/*     */       }
/*     */       
/*     */ 
/* 282 */       return new PatternEntry(j, this.newChars, this.newExtension);
/*     */     }
/*     */     
/*     */ 
/* 286 */     private StringBuffer newChars = new StringBuffer();
/* 287 */     private StringBuffer newExtension = new StringBuffer();
/*     */   }
/*     */   
/*     */   static boolean isSpecialChar(char paramChar)
/*     */   {
/* 292 */     return (paramChar == ' ') || ((paramChar <= '/') && (paramChar >= '"')) || ((paramChar <= '?') && (paramChar >= ':')) || ((paramChar <= '`') && (paramChar >= '[')) || ((paramChar <= '~') && (paramChar >= '{'));
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
/* 303 */   int strength = -1;
/* 304 */   String chars = "";
/* 305 */   String extension = "";
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/PatternEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */