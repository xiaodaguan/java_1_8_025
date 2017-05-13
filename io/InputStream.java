/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InputStream
/*     */   implements Closeable
/*     */ {
/*     */   private static final int MAX_SKIP_BUFFER_SIZE = 2048;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int read()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 101 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 162 */     if (paramArrayOfByte == null)
/* 163 */       throw new NullPointerException();
/* 164 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1))
/* 165 */       throw new IndexOutOfBoundsException();
/* 166 */     if (paramInt2 == 0) {
/* 167 */       return 0;
/*     */     }
/*     */     
/* 170 */     int i = read();
/* 171 */     if (i == -1) {
/* 172 */       return -1;
/*     */     }
/* 174 */     paramArrayOfByte[paramInt1] = ((byte)i);
/*     */     
/* 176 */     int j = 1;
/*     */     try {
/* 178 */       for (; j < paramInt2; j++) {
/* 179 */         i = read();
/* 180 */         if (i == -1) {
/*     */           break;
/*     */         }
/* 183 */         paramArrayOfByte[(paramInt1 + j)] = ((byte)i);
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/* 187 */     return j;
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
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 214 */     long l = paramLong;
/*     */     
/*     */ 
/* 217 */     if (paramLong <= 0L) {
/* 218 */       return 0L;
/*     */     }
/*     */     
/* 221 */     int j = (int)Math.min(2048L, l);
/* 222 */     byte[] arrayOfByte = new byte[j];
/* 223 */     while (l > 0L) {
/* 224 */       int i = read(arrayOfByte, 0, (int)Math.min(j, l));
/* 225 */       if (i < 0) {
/*     */         break;
/*     */       }
/* 228 */       l -= i;
/*     */     }
/*     */     
/* 231 */     return paramLong - l;
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
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/* 261 */     return 0;
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 348 */     throw new IOException("mark/reset not supported");
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
/*     */   public boolean markSupported()
/*     */   {
/* 364 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/InputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */