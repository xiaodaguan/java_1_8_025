/*     */ package java.net;
/*     */ 
/*     */ import java.util.Formatter;
/*     */ import java.util.Locale;
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
/*     */ class HostPortrange
/*     */ {
/*     */   String hostname;
/*     */   String scheme;
/*     */   int[] portrange;
/*     */   boolean wildcard;
/*     */   boolean literal;
/*     */   boolean ipv6;
/*     */   boolean ipv4;
/*     */   static final int PORT_MIN = 0;
/*     */   static final int PORT_MAX = 65535;
/*     */   static final int CASE_DIFF = -32;
/*     */   
/*     */   boolean equals(HostPortrange paramHostPortrange)
/*     */   {
/*  49 */     return (this.hostname.equals(paramHostPortrange.hostname)) && (this.portrange[0] == paramHostPortrange.portrange[0]) && (this.portrange[1] == paramHostPortrange.portrange[1]) && (this.wildcard == paramHostPortrange.wildcard) && (this.literal == paramHostPortrange.literal);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  57 */     return this.hostname.hashCode() + this.portrange[0] + this.portrange[1];
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
/*     */   HostPortrange(String paramString1, String paramString2)
/*     */   {
/*  71 */     String str2 = null;
/*  72 */     this.scheme = paramString1;
/*     */     int i;
/*     */     String str1;
/*  75 */     int j; if (paramString2.charAt(0) == '[') {
/*  76 */       this.ipv6 = (this.literal = 1);
/*  77 */       i = paramString2.indexOf(']');
/*  78 */       if (i != -1) {
/*  79 */         str1 = paramString2.substring(1, i);
/*     */       } else {
/*  81 */         throw new IllegalArgumentException("invalid IPv6 address: " + paramString2);
/*     */       }
/*  83 */       j = paramString2.indexOf(':', i + 1);
/*  84 */       if ((j != -1) && (paramString2.length() > j)) {
/*  85 */         str2 = paramString2.substring(j + 1);
/*     */       }
/*     */       
/*  88 */       byte[] arrayOfByte1 = IPAddressUtil.textToNumericFormatV6(str1);
/*  89 */       if (arrayOfByte1 == null) {
/*  90 */         throw new IllegalArgumentException("illegal IPv6 address");
/*     */       }
/*  92 */       StringBuilder localStringBuilder1 = new StringBuilder();
/*  93 */       Formatter localFormatter1 = new Formatter(localStringBuilder1, Locale.US);
/*  94 */       localFormatter1.format("%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x", new Object[] {
/*     */       
/*  96 */         Byte.valueOf(arrayOfByte1[0]), Byte.valueOf(arrayOfByte1[1]), Byte.valueOf(arrayOfByte1[2]), Byte.valueOf(arrayOfByte1[3]), Byte.valueOf(arrayOfByte1[4]), Byte.valueOf(arrayOfByte1[5]), Byte.valueOf(arrayOfByte1[6]), Byte.valueOf(arrayOfByte1[7]), Byte.valueOf(arrayOfByte1[8]), 
/*  97 */         Byte.valueOf(arrayOfByte1[9]), Byte.valueOf(arrayOfByte1[10]), Byte.valueOf(arrayOfByte1[11]), Byte.valueOf(arrayOfByte1[12]), Byte.valueOf(arrayOfByte1[13]), Byte.valueOf(arrayOfByte1[14]), Byte.valueOf(arrayOfByte1[15]) });
/*  98 */       this.hostname = localStringBuilder1.toString();
/*     */     }
/*     */     else
/*     */     {
/* 102 */       i = paramString2.indexOf(':');
/* 103 */       if ((i != -1) && (paramString2.length() > i)) {
/* 104 */         str1 = paramString2.substring(0, i);
/* 105 */         str2 = paramString2.substring(i + 1);
/*     */       } else {
/* 107 */         str1 = i == -1 ? paramString2 : paramString2.substring(0, i);
/*     */       }
/*     */       
/* 110 */       if (str1.lastIndexOf('*') > 0)
/* 111 */         throw new IllegalArgumentException("invalid host wildcard specification");
/* 112 */       if (str1.startsWith("*")) {
/* 113 */         this.wildcard = true;
/* 114 */         if (str1.equals("*")) {
/* 115 */           str1 = "";
/* 116 */         } else if (str1.startsWith("*.")) {
/* 117 */           str1 = toLowerCase(str1.substring(1));
/*     */         } else {
/* 119 */           throw new IllegalArgumentException("invalid host wildcard specification");
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 127 */         j = str1.lastIndexOf('.');
/* 128 */         if ((j != -1) && (str1.length() > 1)) {
/* 129 */           int k = 1;
/*     */           
/* 131 */           int m = j + 1; for (int n = str1.length(); m < n; m++) {
/* 132 */             int i1 = str1.charAt(m);
/* 133 */             if ((i1 < 48) || (i1 > 57)) {
/* 134 */               k = 0;
/* 135 */               break;
/*     */             }
/*     */           }
/* 138 */           this.ipv4 = (this.literal = k);
/* 139 */           if (k != 0) {
/* 140 */             byte[] arrayOfByte2 = IPAddressUtil.textToNumericFormatV4(str1);
/* 141 */             if (arrayOfByte2 == null) {
/* 142 */               throw new IllegalArgumentException("illegal IPv4 address");
/*     */             }
/* 144 */             StringBuilder localStringBuilder2 = new StringBuilder();
/* 145 */             Formatter localFormatter2 = new Formatter(localStringBuilder2, Locale.US);
/* 146 */             localFormatter2.format("%d.%d.%d.%d", new Object[] { Byte.valueOf(arrayOfByte2[0]), Byte.valueOf(arrayOfByte2[1]), Byte.valueOf(arrayOfByte2[2]), Byte.valueOf(arrayOfByte2[3]) });
/* 147 */             str1 = localStringBuilder2.toString();
/*     */           }
/*     */           else {
/* 150 */             str1 = toLowerCase(str1);
/*     */           }
/*     */         }
/*     */       }
/* 154 */       this.hostname = str1;
/*     */     }
/*     */     try
/*     */     {
/* 158 */       this.portrange = parsePort(str2);
/*     */     } catch (Exception localException) {
/* 160 */       throw new IllegalArgumentException("invalid port range: " + str2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String toLowerCase(String paramString)
/*     */   {
/* 171 */     int i = paramString.length();
/* 172 */     StringBuilder localStringBuilder = null;
/*     */     
/* 174 */     for (int j = 0; j < i; j++) {
/* 175 */       int k = paramString.charAt(j);
/* 176 */       if (((k >= 97) && (k <= 122)) || (k == 46)) {
/* 177 */         if (localStringBuilder != null)
/* 178 */           localStringBuilder.append(k);
/* 179 */       } else if (((k >= 48) && (k <= 57)) || (k == 45)) {
/* 180 */         if (localStringBuilder != null)
/* 181 */           localStringBuilder.append(k);
/* 182 */       } else if ((k >= 65) && (k <= 90)) {
/* 183 */         if (localStringBuilder == null) {
/* 184 */           localStringBuilder = new StringBuilder(i);
/* 185 */           localStringBuilder.append(paramString, 0, j);
/*     */         }
/* 187 */         localStringBuilder.append((char)(k - -32));
/*     */       } else {
/* 189 */         throw new IllegalArgumentException("Invalid characters in hostname");
/*     */       }
/*     */     }
/* 192 */     return localStringBuilder == null ? paramString : localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public boolean literal()
/*     */   {
/* 197 */     return this.literal;
/*     */   }
/*     */   
/*     */   public boolean ipv4Literal() {
/* 201 */     return this.ipv4;
/*     */   }
/*     */   
/*     */   public boolean ipv6Literal() {
/* 205 */     return this.ipv6;
/*     */   }
/*     */   
/*     */   public String hostname() {
/* 209 */     return this.hostname;
/*     */   }
/*     */   
/*     */   public int[] portrange() {
/* 213 */     return this.portrange;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean wildcard()
/*     */   {
/* 224 */     return this.wildcard;
/*     */   }
/*     */   
/*     */ 
/* 228 */   static final int[] HTTP_PORT = { 80, 80 };
/* 229 */   static final int[] HTTPS_PORT = { 443, 443 };
/* 230 */   static final int[] NO_PORT = { -1, -1 };
/*     */   
/*     */   int[] defaultPort() {
/* 233 */     if (this.scheme.equals("http"))
/* 234 */       return HTTP_PORT;
/* 235 */     if (this.scheme.equals("https")) {
/* 236 */       return HTTPS_PORT;
/*     */     }
/* 238 */     return NO_PORT;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] parsePort(String paramString)
/*     */   {
/* 244 */     if ((paramString == null) || (paramString.equals(""))) {
/* 245 */       return defaultPort();
/*     */     }
/*     */     
/* 248 */     if (paramString.equals("*")) {
/* 249 */       return new int[] { 0, 65535 };
/*     */     }
/*     */     try
/*     */     {
/* 253 */       int i = paramString.indexOf('-');
/*     */       
/* 255 */       if (i == -1) {
/* 256 */         int j = Integer.parseInt(paramString);
/* 257 */         return new int[] { j, j };
/*     */       }
/* 259 */       String str1 = paramString.substring(0, i);
/* 260 */       String str2 = paramString.substring(i + 1);
/*     */       
/*     */       int k;
/* 263 */       if (str1.equals("")) {
/* 264 */         k = 0;
/*     */       } else {
/* 266 */         k = Integer.parseInt(str1);
/*     */       }
/*     */       int m;
/* 269 */       if (str2.equals("")) {
/* 270 */         m = 65535;
/*     */       } else {
/* 272 */         m = Integer.parseInt(str2);
/*     */       }
/* 274 */       if ((k < 0) || (m < 0) || (m < k)) {
/* 275 */         return defaultPort();
/*     */       }
/* 277 */       return new int[] { k, m };
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException) {}
/* 280 */     return defaultPort();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/HostPortrange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */