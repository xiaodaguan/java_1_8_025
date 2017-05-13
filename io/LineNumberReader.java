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
/*     */ 
/*     */ 
/*     */ public class LineNumberReader
/*     */   extends BufferedReader
/*     */ {
/*  53 */   private int lineNumber = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   private int markedLineNumber;
/*     */   
/*     */ 
/*     */   private boolean skipLF;
/*     */   
/*     */ 
/*     */   private boolean markedSkipLF;
/*     */   
/*     */ 
/*     */   private static final int maxSkipBufferSize = 8192;
/*     */   
/*     */ 
/*     */ 
/*     */   public LineNumberReader(Reader paramReader)
/*     */   {
/*  72 */     super(paramReader);
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
/*     */   public LineNumberReader(Reader paramReader, int paramInt)
/*     */   {
/*  86 */     super(paramReader, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLineNumber(int paramInt)
/*     */   {
/*  98 */     this.lineNumber = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLineNumber()
/*     */   {
/* 109 */     return this.lineNumber;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 125 */     synchronized (this.lock) {
/* 126 */       int i = super.read();
/* 127 */       if (this.skipLF) {
/* 128 */         if (i == 10)
/* 129 */           i = super.read();
/* 130 */         this.skipLF = false;
/*     */       }
/* 132 */       switch (i) {
/*     */       case 13: 
/* 134 */         this.skipLF = true;
/*     */       case 10: 
/* 136 */         this.lineNumber += 1;
/* 137 */         return 10;
/*     */       }
/* 139 */       return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 165 */     synchronized (this.lock) {
/* 166 */       int i = super.read(paramArrayOfChar, paramInt1, paramInt2);
/*     */       
/* 168 */       for (int j = paramInt1; j < paramInt1 + i; j++) {
/* 169 */         int k = paramArrayOfChar[j];
/* 170 */         if (this.skipLF) {
/* 171 */           this.skipLF = false;
/* 172 */           if (k == 10) {}
/*     */         }
/*     */         else {
/* 175 */           switch (k) {
/*     */           case 13: 
/* 177 */             this.skipLF = true;
/*     */           case 10: 
/* 179 */             this.lineNumber += 1;
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 184 */       return i;
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
/*     */   public String readLine()
/*     */     throws IOException
/*     */   {
/* 200 */     synchronized (this.lock) {
/* 201 */       String str = super.readLine(this.skipLF);
/* 202 */       this.skipLF = false;
/* 203 */       if (str != null)
/* 204 */         this.lineNumber += 1;
/* 205 */       return str;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */   private char[] skipBuffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
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
/* 230 */     if (paramLong < 0L)
/* 231 */       throw new IllegalArgumentException("skip() value is negative");
/* 232 */     int i = (int)Math.min(paramLong, 8192L);
/* 233 */     synchronized (this.lock) {
/* 234 */       if ((this.skipBuffer == null) || (this.skipBuffer.length < i))
/* 235 */         this.skipBuffer = new char[i];
/* 236 */       long l = paramLong;
/* 237 */       while (l > 0L) {
/* 238 */         int j = read(this.skipBuffer, 0, (int)Math.min(l, i));
/* 239 */         if (j == -1)
/*     */           break;
/* 241 */         l -= j;
/*     */       }
/* 243 */       return paramLong - l;
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
/*     */   public void mark(int paramInt)
/*     */     throws IOException
/*     */   {
/* 261 */     synchronized (this.lock) {
/* 262 */       super.mark(paramInt);
/* 263 */       this.markedLineNumber = this.lineNumber;
/* 264 */       this.markedSkipLF = this.skipLF;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */     throws IOException
/*     */   {
/* 276 */     synchronized (this.lock) {
/* 277 */       super.reset();
/* 278 */       this.lineNumber = this.markedLineNumber;
/* 279 */       this.skipLF = this.markedSkipLF;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/LineNumberReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */