/*     */ package java.util.zip;
/*     */ 
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ZipUtils
/*     */ {
/*     */   private static final long WINDOWS_EPOCH_IN_MICROSECONDS = -11644473600000000L;
/*     */   
/*     */   public static final FileTime winTimeToFileTime(long paramLong)
/*     */   {
/*  44 */     return FileTime.from(paramLong / 10L + -11644473600000000L, TimeUnit.MICROSECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long fileTimeToWinTime(FileTime paramFileTime)
/*     */   {
/*  52 */     return (paramFileTime.to(TimeUnit.MICROSECONDS) - -11644473600000000L) * 10L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static final FileTime unixTimeToFileTime(long paramLong)
/*     */   {
/*  59 */     return FileTime.from(paramLong, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static final long fileTimeToUnixTime(FileTime paramFileTime)
/*     */   {
/*  66 */     return paramFileTime.to(TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long dosToJavaTime(long paramLong)
/*     */   {
/*  74 */     Date localDate = new Date((int)((paramLong >> 25 & 0x7F) + 80L), (int)((paramLong >> 21 & 0xF) - 1L), (int)(paramLong >> 16 & 0x1F), (int)(paramLong >> 11 & 0x1F), (int)(paramLong >> 5 & 0x3F), (int)(paramLong << 1 & 0x3E));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  80 */     return localDate.getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long javaToDosTime(long paramLong)
/*     */   {
/*  88 */     Date localDate = new Date(paramLong);
/*  89 */     int i = localDate.getYear() + 1900;
/*  90 */     if (i < 1980) {
/*  91 */       return 2162688L;
/*     */     }
/*     */     
/*     */ 
/*  95 */     return i - 1980 << 25 | localDate.getMonth() + 1 << 21 | localDate.getDate() << 16 | localDate.getHours() << 11 | localDate.getMinutes() << 5 | localDate.getSeconds() >> 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int get16(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 103 */     return Byte.toUnsignedInt(paramArrayOfByte[paramInt]) | Byte.toUnsignedInt(paramArrayOfByte[(paramInt + 1)]) << 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long get32(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 111 */     return (get16(paramArrayOfByte, paramInt) | get16(paramArrayOfByte, paramInt + 2) << 16) & 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long get64(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 119 */     return get32(paramArrayOfByte, paramInt) | get32(paramArrayOfByte, paramInt + 4) << 32;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/ZipUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */