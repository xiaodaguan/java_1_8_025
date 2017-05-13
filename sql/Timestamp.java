/*     */ package java.sql;
/*     */ 
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timestamp
/*     */   extends Date
/*     */ {
/*     */   private int nanos;
/*     */   static final long serialVersionUID = 2745179027874758501L;
/*     */   private static final int MILLIS_PER_SECOND = 1000;
/*     */   
/*     */   @Deprecated
/*     */   public Timestamp(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*     */   {
/*  91 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*  92 */     if ((paramInt7 > 999999999) || (paramInt7 < 0)) {
/*  93 */       throw new IllegalArgumentException("nanos > 999999999 or < 0");
/*     */     }
/*  95 */     this.nanos = paramInt7;
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
/*     */   public Timestamp(long paramLong)
/*     */   {
/* 111 */     super(paramLong / 1000L * 1000L);
/* 112 */     this.nanos = ((int)(paramLong % 1000L * 1000000L));
/* 113 */     if (this.nanos < 0) {
/* 114 */       this.nanos = (1000000000 + this.nanos);
/* 115 */       super.setTime((paramLong / 1000L - 1L) * 1000L);
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
/*     */   public void setTime(long paramLong)
/*     */   {
/* 129 */     super.setTime(paramLong / 1000L * 1000L);
/* 130 */     this.nanos = ((int)(paramLong % 1000L * 1000000L));
/* 131 */     if (this.nanos < 0) {
/* 132 */       this.nanos = (1000000000 + this.nanos);
/* 133 */       super.setTime((paramLong / 1000L - 1L) * 1000L);
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
/*     */   public long getTime()
/*     */   {
/* 146 */     long l = super.getTime();
/* 147 */     return l + this.nanos / 1000000;
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
/*     */   public static Timestamp valueOf(String paramString)
/*     */   {
/* 177 */     int i = 0;
/* 178 */     int j = 0;
/* 179 */     int k = 0;
/*     */     
/*     */ 
/*     */ 
/* 183 */     int i2 = 0;
/*     */     
/*     */ 
/*     */ 
/* 187 */     int i6 = 0;
/* 188 */     int i7 = 0;
/* 189 */     int i8 = 0;
/* 190 */     String str4 = "Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]";
/* 191 */     String str5 = "000000000";
/* 192 */     String str6 = "-";
/* 193 */     String str7 = ":";
/*     */     
/* 195 */     if (paramString == null) { throw new IllegalArgumentException("null string");
/*     */     }
/*     */     
/* 198 */     paramString = paramString.trim();
/* 199 */     int i5 = paramString.indexOf(' ');
/* 200 */     String str1; String str2; if (i5 > 0) {
/* 201 */       str1 = paramString.substring(0, i5);
/* 202 */       str2 = paramString.substring(i5 + 1);
/*     */     } else {
/* 204 */       throw new IllegalArgumentException(str4);
/*     */     }
/*     */     
/*     */ 
/* 208 */     int i3 = str1.indexOf('-');
/* 209 */     int i4 = str1.indexOf('-', i3 + 1);
/*     */     
/*     */ 
/* 212 */     if (str2 == null)
/* 213 */       throw new IllegalArgumentException(str4);
/* 214 */     i6 = str2.indexOf(':');
/* 215 */     i7 = str2.indexOf(':', i6 + 1);
/* 216 */     i8 = str2.indexOf('.', i7 + 1);
/*     */     
/*     */ 
/* 219 */     int i9 = 0;
/* 220 */     if ((i3 > 0) && (i4 > 0) && (i4 < str1.length() - 1)) {
/* 221 */       String str8 = str1.substring(0, i3);
/* 222 */       String str9 = str1.substring(i3 + 1, i4);
/* 223 */       String str10 = str1.substring(i4 + 1);
/* 224 */       if ((str8.length() == 4) && 
/* 225 */         (str9.length() >= 1) && (str9.length() <= 2) && 
/* 226 */         (str10.length() >= 1) && (str10.length() <= 2)) {
/* 227 */         i = Integer.parseInt(str8);
/* 228 */         j = Integer.parseInt(str9);
/* 229 */         k = Integer.parseInt(str10);
/*     */         
/* 231 */         if ((j >= 1) && (j <= 12) && (k >= 1) && (k <= 31)) {
/* 232 */           i9 = 1;
/*     */         }
/*     */       }
/*     */     }
/* 236 */     if (i9 == 0) {
/* 237 */       throw new IllegalArgumentException(str4);
/*     */     }
/*     */     int m;
/*     */     int n;
/*     */     int i1;
/* 242 */     if (((i6 > 0 ? 1 : 0) & (i7 > 0 ? 1 : 0) & (i7 < str2.length() - 1 ? 1 : 0)) != 0) {
/* 243 */       m = Integer.parseInt(str2.substring(0, i6));
/*     */       
/* 245 */       n = Integer.parseInt(str2.substring(i6 + 1, i7));
/* 246 */       if (((i8 > 0 ? 1 : 0) & (i8 < str2.length() - 1 ? 1 : 0)) != 0)
/*     */       {
/* 248 */         i1 = Integer.parseInt(str2.substring(i7 + 1, i8));
/* 249 */         String str3 = str2.substring(i8 + 1);
/* 250 */         if (str3.length() > 9)
/* 251 */           throw new IllegalArgumentException(str4);
/* 252 */         if (!Character.isDigit(str3.charAt(0)))
/* 253 */           throw new IllegalArgumentException(str4);
/* 254 */         str3 = str3 + str5.substring(0, 9 - str3.length());
/* 255 */         i2 = Integer.parseInt(str3);
/* 256 */       } else { if (i8 > 0) {
/* 257 */           throw new IllegalArgumentException(str4);
/*     */         }
/* 259 */         i1 = Integer.parseInt(str2.substring(i7 + 1));
/*     */       }
/*     */     } else {
/* 262 */       throw new IllegalArgumentException(str4);
/*     */     }
/*     */     
/* 265 */     return new Timestamp(i - 1900, j - 1, k, m, n, i1, i2);
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
/*     */   public String toString()
/*     */   {
/* 279 */     int i = super.getYear() + 1900;
/* 280 */     int j = super.getMonth() + 1;
/* 281 */     int k = super.getDate();
/* 282 */     int m = super.getHours();
/* 283 */     int n = super.getMinutes();
/* 284 */     int i1 = super.getSeconds();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 292 */     String str8 = "000000000";
/* 293 */     String str9 = "0000";
/*     */     
/*     */     String str1;
/* 296 */     if (i < 1000)
/*     */     {
/* 298 */       str1 = "" + i;
/* 299 */       str1 = str9.substring(0, 4 - str1.length()) + str1;
/*     */     }
/*     */     else {
/* 302 */       str1 = "" + i; }
/*     */     String str2;
/* 304 */     if (j < 10) {
/* 305 */       str2 = "0" + j;
/*     */     } else
/* 307 */       str2 = Integer.toString(j);
/*     */     String str3;
/* 309 */     if (k < 10) {
/* 310 */       str3 = "0" + k;
/*     */     } else
/* 312 */       str3 = Integer.toString(k);
/*     */     String str4;
/* 314 */     if (m < 10) {
/* 315 */       str4 = "0" + m;
/*     */     } else
/* 317 */       str4 = Integer.toString(m);
/*     */     String str5;
/* 319 */     if (n < 10) {
/* 320 */       str5 = "0" + n;
/*     */     } else
/* 322 */       str5 = Integer.toString(n);
/*     */     String str6;
/* 324 */     if (i1 < 10) {
/* 325 */       str6 = "0" + i1;
/*     */     } else
/* 327 */       str6 = Integer.toString(i1);
/*     */     String str7;
/* 329 */     if (this.nanos == 0) {
/* 330 */       str7 = "0";
/*     */     } else {
/* 332 */       str7 = Integer.toString(this.nanos);
/*     */       
/*     */ 
/* 335 */       str7 = str8.substring(0, 9 - str7.length()) + str7;
/*     */       
/*     */ 
/*     */ 
/* 339 */       char[] arrayOfChar = new char[str7.length()];
/* 340 */       str7.getChars(0, str7.length(), arrayOfChar, 0);
/* 341 */       int i2 = 8;
/* 342 */       while (arrayOfChar[i2] == '0') {
/* 343 */         i2--;
/*     */       }
/*     */       
/* 346 */       str7 = new String(arrayOfChar, 0, i2 + 1);
/*     */     }
/*     */     
/*     */ 
/* 350 */     StringBuffer localStringBuffer = new StringBuffer(20 + str7.length());
/* 351 */     localStringBuffer.append(str1);
/* 352 */     localStringBuffer.append("-");
/* 353 */     localStringBuffer.append(str2);
/* 354 */     localStringBuffer.append("-");
/* 355 */     localStringBuffer.append(str3);
/* 356 */     localStringBuffer.append(" ");
/* 357 */     localStringBuffer.append(str4);
/* 358 */     localStringBuffer.append(":");
/* 359 */     localStringBuffer.append(str5);
/* 360 */     localStringBuffer.append(":");
/* 361 */     localStringBuffer.append(str6);
/* 362 */     localStringBuffer.append(".");
/* 363 */     localStringBuffer.append(str7);
/*     */     
/* 365 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNanos()
/*     */   {
/* 375 */     return this.nanos;
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
/*     */   public void setNanos(int paramInt)
/*     */   {
/* 388 */     if ((paramInt > 999999999) || (paramInt < 0)) {
/* 389 */       throw new IllegalArgumentException("nanos > 999999999 or < 0");
/*     */     }
/* 391 */     this.nanos = paramInt;
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
/*     */   public boolean equals(Timestamp paramTimestamp)
/*     */   {
/* 404 */     if (super.equals(paramTimestamp)) {
/* 405 */       if (this.nanos == paramTimestamp.nanos) {
/* 406 */         return true;
/*     */       }
/* 408 */       return false;
/*     */     }
/*     */     
/* 411 */     return false;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 434 */     if ((paramObject instanceof Timestamp)) {
/* 435 */       return equals((Timestamp)paramObject);
/*     */     }
/* 437 */     return false;
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
/*     */   public boolean before(Timestamp paramTimestamp)
/*     */   {
/* 450 */     return compareTo(paramTimestamp) < 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean after(Timestamp paramTimestamp)
/*     */   {
/* 462 */     return compareTo(paramTimestamp) > 0;
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
/*     */   public int compareTo(Timestamp paramTimestamp)
/*     */   {
/* 479 */     long l1 = getTime();
/* 480 */     long l2 = paramTimestamp.getTime();
/* 481 */     int i = l1 == l2 ? 0 : l1 < l2 ? -1 : 1;
/* 482 */     if (i == 0) {
/* 483 */       if (this.nanos > paramTimestamp.nanos)
/* 484 */         return 1;
/* 485 */       if (this.nanos < paramTimestamp.nanos) {
/* 486 */         return -1;
/*     */       }
/*     */     }
/* 489 */     return i;
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
/*     */   public int compareTo(Date paramDate)
/*     */   {
/* 507 */     if ((paramDate instanceof Timestamp))
/*     */     {
/*     */ 
/*     */ 
/* 511 */       return compareTo((Timestamp)paramDate);
/*     */     }
/*     */     
/*     */ 
/* 515 */     Timestamp localTimestamp = new Timestamp(paramDate.getTime());
/* 516 */     return compareTo(localTimestamp);
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
/*     */   public int hashCode()
/*     */   {
/* 529 */     return super.hashCode();
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
/*     */   public static Timestamp valueOf(LocalDateTime paramLocalDateTime)
/*     */   {
/* 557 */     return new Timestamp(paramLocalDateTime.getYear() - 1900, paramLocalDateTime.getMonthValue() - 1, paramLocalDateTime.getDayOfMonth(), paramLocalDateTime.getHour(), paramLocalDateTime.getMinute(), paramLocalDateTime.getSecond(), paramLocalDateTime.getNano());
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
/*     */   public LocalDateTime toLocalDateTime()
/*     */   {
/* 572 */     return LocalDateTime.of(getYear() + 1900, 
/* 573 */       getMonth() + 1, 
/* 574 */       getDate(), 
/* 575 */       getHours(), 
/* 576 */       getMinutes(), 
/* 577 */       getSeconds(), 
/* 578 */       getNanos());
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
/*     */   public static Timestamp from(Instant paramInstant)
/*     */   {
/*     */     try
/*     */     {
/* 598 */       Timestamp localTimestamp = new Timestamp(paramInstant.getEpochSecond() * 1000L);
/* 599 */       localTimestamp.nanos = paramInstant.getNano();
/* 600 */       return localTimestamp;
/*     */     } catch (ArithmeticException localArithmeticException) {
/* 602 */       throw new IllegalArgumentException(localArithmeticException);
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
/*     */   public Instant toInstant()
/*     */   {
/* 617 */     return Instant.ofEpochSecond(super.getTime() / 1000L, this.nanos);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/Timestamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */