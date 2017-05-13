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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class LineNumberInputStream
/*     */   extends FilterInputStream
/*     */ {
/*  52 */   int pushBack = -1;
/*     */   int lineNumber;
/*     */   int markLineNumber;
/*  55 */   int markPushBack = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LineNumberInputStream(InputStream paramInputStream)
/*     */   {
/*  64 */     super(paramInputStream);
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  92 */     int i = this.pushBack;
/*     */     
/*  94 */     if (i != -1) {
/*  95 */       this.pushBack = -1;
/*     */     } else {
/*  97 */       i = this.in.read();
/*     */     }
/*     */     
/* 100 */     switch (i) {
/*     */     case 13: 
/* 102 */       this.pushBack = this.in.read();
/* 103 */       if (this.pushBack == 10) {
/* 104 */         this.pushBack = -1;
/*     */       }
/*     */     case 10: 
/* 107 */       this.lineNumber += 1;
/* 108 */       return 10;
/*     */     }
/* 110 */     return i;
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 131 */     if (paramArrayOfByte == null)
/* 132 */       throw new NullPointerException();
/* 133 */     if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0))
/*     */     {
/* 135 */       throw new IndexOutOfBoundsException(); }
/* 136 */     if (paramInt2 == 0) {
/* 137 */       return 0;
/*     */     }
/*     */     
/* 140 */     int i = read();
/* 141 */     if (i == -1) {
/* 142 */       return -1;
/*     */     }
/* 144 */     paramArrayOfByte[paramInt1] = ((byte)i);
/*     */     
/* 146 */     int j = 1;
/*     */     try {
/* 148 */       for (; j < paramInt2; j++) {
/* 149 */         i = read();
/* 150 */         if (i == -1) {
/*     */           break;
/*     */         }
/* 153 */         if (paramArrayOfByte != null) {
/* 154 */           paramArrayOfByte[(paramInt1 + j)] = ((byte)i);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/* 159 */     return j;
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
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 180 */     int i = 2048;
/* 181 */     long l = paramLong;
/*     */     
/*     */ 
/*     */ 
/* 185 */     if (paramLong <= 0L) {
/* 186 */       return 0L;
/*     */     }
/*     */     
/* 189 */     byte[] arrayOfByte = new byte[i];
/* 190 */     while (l > 0L) {
/* 191 */       int j = read(arrayOfByte, 0, (int)Math.min(i, l));
/* 192 */       if (j < 0) {
/*     */         break;
/*     */       }
/* 195 */       l -= j;
/*     */     }
/*     */     
/* 198 */     return paramLong - l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLineNumber(int paramInt)
/*     */   {
/* 208 */     this.lineNumber = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLineNumber()
/*     */   {
/* 218 */     return this.lineNumber;
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
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/* 241 */     return this.pushBack == -1 ? super.available() / 2 : super.available() / 2 + 1;
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
/*     */   public void mark(int paramInt)
/*     */   {
/* 260 */     this.markLineNumber = this.lineNumber;
/* 261 */     this.markPushBack = this.pushBack;
/* 262 */     this.in.mark(paramInt);
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
/*     */   public void reset()
/*     */     throws IOException
/*     */   {
/* 289 */     this.lineNumber = this.markLineNumber;
/* 290 */     this.pushBack = this.markPushBack;
/* 291 */     this.in.reset();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/LineNumberInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */