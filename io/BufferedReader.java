/*     */ package java.io;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedReader
/*     */   extends Reader
/*     */ {
/*     */   private Reader in;
/*     */   private char[] cb;
/*     */   private int nChars;
/*     */   private int nextChar;
/*     */   private static final int INVALIDATED = -2;
/*     */   private static final int UNMARKED = -1;
/*  79 */   private int markedChar = -1;
/*  80 */   private int readAheadLimit = 0;
/*     */   
/*     */ 
/*  83 */   private boolean skipLF = false;
/*     */   
/*     */ 
/*  86 */   private boolean markedSkipLF = false;
/*     */   
/*  88 */   private static int defaultCharBufferSize = 8192;
/*  89 */   private static int defaultExpectedLineLength = 80;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BufferedReader(Reader paramReader, int paramInt)
/*     */   {
/* 101 */     super(paramReader);
/* 102 */     if (paramInt <= 0)
/* 103 */       throw new IllegalArgumentException("Buffer size <= 0");
/* 104 */     this.in = paramReader;
/* 105 */     this.cb = new char[paramInt];
/* 106 */     this.nextChar = (this.nChars = 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BufferedReader(Reader paramReader)
/*     */   {
/* 116 */     this(paramReader, defaultCharBufferSize);
/*     */   }
/*     */   
/*     */   private void ensureOpen() throws IOException
/*     */   {
/* 121 */     if (this.in == null) {
/* 122 */       throw new IOException("Stream closed");
/*     */     }
/*     */   }
/*     */   
/*     */   private void fill() throws IOException
/*     */   {
/*     */     int i;
/*     */     int j;
/* 130 */     if (this.markedChar <= -1)
/*     */     {
/* 132 */       i = 0;
/*     */     }
/*     */     else {
/* 135 */       j = this.nextChar - this.markedChar;
/* 136 */       if (j >= this.readAheadLimit)
/*     */       {
/* 138 */         this.markedChar = -2;
/* 139 */         this.readAheadLimit = 0;
/* 140 */         i = 0;
/*     */       } else {
/* 142 */         if (this.readAheadLimit <= this.cb.length)
/*     */         {
/* 144 */           System.arraycopy(this.cb, this.markedChar, this.cb, 0, j);
/* 145 */           this.markedChar = 0;
/* 146 */           i = j;
/*     */         }
/*     */         else {
/* 149 */           char[] arrayOfChar = new char[this.readAheadLimit];
/* 150 */           System.arraycopy(this.cb, this.markedChar, arrayOfChar, 0, j);
/* 151 */           this.cb = arrayOfChar;
/* 152 */           this.markedChar = 0;
/* 153 */           i = j;
/*     */         }
/* 155 */         this.nextChar = (this.nChars = j);
/*     */       }
/*     */     }
/*     */     
/*     */     do
/*     */     {
/* 161 */       j = this.in.read(this.cb, i, this.cb.length - i);
/* 162 */     } while (j == 0);
/* 163 */     if (j > 0) {
/* 164 */       this.nChars = (i + j);
/* 165 */       this.nextChar = i;
/*     */     }
/*     */   }
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
/* 178 */     synchronized (this.lock) {
/* 179 */       ensureOpen();
/*     */       for (;;) {
/* 181 */         if (this.nextChar >= this.nChars) {
/* 182 */           fill();
/* 183 */           if (this.nextChar >= this.nChars)
/* 184 */             return -1;
/*     */         }
/* 186 */         if (!this.skipLF) break;
/* 187 */         this.skipLF = false;
/* 188 */         if (this.cb[this.nextChar] != '\n') break;
/* 189 */         this.nextChar += 1;
/*     */       }
/*     */       
/*     */ 
/* 193 */       return this.cb[(this.nextChar++)];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int read1(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 203 */     if (this.nextChar >= this.nChars)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */       if ((paramInt2 >= this.cb.length) && (this.markedChar <= -1) && (!this.skipLF)) {
/* 210 */         return this.in.read(paramArrayOfChar, paramInt1, paramInt2);
/*     */       }
/* 212 */       fill();
/*     */     }
/* 214 */     if (this.nextChar >= this.nChars) return -1;
/* 215 */     if (this.skipLF) {
/* 216 */       this.skipLF = false;
/* 217 */       if (this.cb[this.nextChar] == '\n') {
/* 218 */         this.nextChar += 1;
/* 219 */         if (this.nextChar >= this.nChars)
/* 220 */           fill();
/* 221 */         if (this.nextChar >= this.nChars)
/* 222 */           return -1;
/*     */       }
/*     */     }
/* 225 */     int i = Math.min(paramInt2, this.nChars - this.nextChar);
/* 226 */     System.arraycopy(this.cb, this.nextChar, paramArrayOfChar, paramInt1, i);
/* 227 */     this.nextChar += i;
/* 228 */     return i;
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
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 277 */     synchronized (this.lock) {
/* 278 */       ensureOpen();
/* 279 */       if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0))
/*     */       {
/* 281 */         throw new IndexOutOfBoundsException(); }
/* 282 */       if (paramInt2 == 0) {
/* 283 */         return 0;
/*     */       }
/*     */       
/* 286 */       int i = read1(paramArrayOfChar, paramInt1, paramInt2);
/* 287 */       if (i <= 0) return i;
/* 288 */       while ((i < paramInt2) && (this.in.ready())) {
/* 289 */         int j = read1(paramArrayOfChar, paramInt1 + i, paramInt2 - i);
/* 290 */         if (j <= 0) break;
/* 291 */         i += j;
/*     */       }
/* 293 */       return i;
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
/*     */   String readLine(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 313 */     StringBuffer localStringBuffer = null;
/*     */     
/*     */ 
/* 316 */     synchronized (this.lock) {
/* 317 */       ensureOpen();
/* 318 */       int j = (paramBoolean) || (this.skipLF) ? 1 : 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 323 */       if (this.nextChar >= this.nChars)
/* 324 */         fill();
/* 325 */       if (this.nextChar >= this.nChars) {
/* 326 */         if ((localStringBuffer != null) && (localStringBuffer.length() > 0)) {
/* 327 */           return localStringBuffer.toString();
/*     */         }
/* 329 */         return null;
/*     */       }
/* 331 */       int k = 0;
/* 332 */       int m = 0;
/*     */       
/*     */ 
/*     */ 
/* 336 */       if ((j != 0) && (this.cb[this.nextChar] == '\n'))
/* 337 */         this.nextChar += 1;
/* 338 */       this.skipLF = false;
/* 339 */       j = 0;
/*     */       
/*     */ 
/* 342 */       for (int n = this.nextChar; n < this.nChars; n++) {
/* 343 */         m = this.cb[n];
/* 344 */         if ((m == 10) || (m == 13)) {
/* 345 */           k = 1;
/* 346 */           break;
/*     */         }
/*     */       }
/*     */       
/* 350 */       int i = this.nextChar;
/* 351 */       this.nextChar = n;
/*     */       
/* 353 */       if (k != 0) {
/*     */         String str;
/* 355 */         if (localStringBuffer == null) {
/* 356 */           str = new String(this.cb, i, n - i);
/*     */         } else {
/* 358 */           localStringBuffer.append(this.cb, i, n - i);
/* 359 */           str = localStringBuffer.toString();
/*     */         }
/* 361 */         this.nextChar += 1;
/* 362 */         if (m == 13) {
/* 363 */           this.skipLF = true;
/*     */         }
/* 365 */         return str;
/*     */       }
/*     */       
/* 368 */       if (localStringBuffer == null)
/* 369 */         localStringBuffer = new StringBuffer(defaultExpectedLineLength);
/* 370 */       localStringBuffer.append(this.cb, i, n - i);
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
/*     */   public String readLine()
/*     */     throws IOException
/*     */   {
/* 389 */     return readLine(false);
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
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 403 */     if (paramLong < 0L) {
/* 404 */       throw new IllegalArgumentException("skip value is negative");
/*     */     }
/* 406 */     synchronized (this.lock) {
/* 407 */       ensureOpen();
/* 408 */       long l1 = paramLong;
/* 409 */       while (l1 > 0L) {
/* 410 */         if (this.nextChar >= this.nChars)
/* 411 */           fill();
/* 412 */         if (this.nextChar >= this.nChars)
/*     */           break;
/* 414 */         if (this.skipLF) {
/* 415 */           this.skipLF = false;
/* 416 */           if (this.cb[this.nextChar] == '\n') {
/* 417 */             this.nextChar += 1;
/*     */           }
/*     */         }
/* 420 */         long l2 = this.nChars - this.nextChar;
/* 421 */         if (l1 <= l2) {
/* 422 */           this.nextChar = ((int)(this.nextChar + l1));
/* 423 */           l1 = 0L;
/* 424 */           break;
/*     */         }
/*     */         
/* 427 */         l1 -= l2;
/* 428 */         this.nextChar = this.nChars;
/*     */       }
/*     */       
/* 431 */       return paramLong - l1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean ready()
/*     */     throws IOException
/*     */   {
/* 443 */     synchronized (this.lock) {
/* 444 */       ensureOpen();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 450 */       if (this.skipLF)
/*     */       {
/*     */ 
/*     */ 
/* 454 */         if ((this.nextChar >= this.nChars) && (this.in.ready())) {
/* 455 */           fill();
/*     */         }
/* 457 */         if (this.nextChar < this.nChars) {
/* 458 */           if (this.cb[this.nextChar] == '\n')
/* 459 */             this.nextChar += 1;
/* 460 */           this.skipLF = false;
/*     */         }
/*     */       }
/* 463 */       return (this.nextChar < this.nChars) || (this.in.ready());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 471 */     return true;
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
/*     */     throws IOException
/*     */   {
/* 491 */     if (paramInt < 0) {
/* 492 */       throw new IllegalArgumentException("Read-ahead limit < 0");
/*     */     }
/* 494 */     synchronized (this.lock) {
/* 495 */       ensureOpen();
/* 496 */       this.readAheadLimit = paramInt;
/* 497 */       this.markedChar = this.nextChar;
/* 498 */       this.markedSkipLF = this.skipLF;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */     throws IOException
/*     */   {
/* 509 */     synchronized (this.lock) {
/* 510 */       ensureOpen();
/* 511 */       if (this.markedChar < 0) {
/* 512 */         throw new IOException(this.markedChar == -2 ? "Mark invalid" : "Stream not marked");
/*     */       }
/*     */       
/* 515 */       this.nextChar = this.markedChar;
/* 516 */       this.skipLF = this.markedSkipLF;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 21	java/io/BufferedReader:lock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 10	java/io/BufferedReader:in	Ljava/io/Reader;
/*     */     //   11: ifnonnull +6 -> 17
/*     */     //   14: aload_1
/*     */     //   15: monitorexit
/*     */     //   16: return
/*     */     //   17: aload_0
/*     */     //   18: getfield 10	java/io/BufferedReader:in	Ljava/io/Reader;
/*     */     //   21: invokevirtual 42	java/io/Reader:close	()V
/*     */     //   24: aload_0
/*     */     //   25: aconst_null
/*     */     //   26: putfield 10	java/io/BufferedReader:in	Ljava/io/Reader;
/*     */     //   29: aload_0
/*     */     //   30: aconst_null
/*     */     //   31: putfield 11	java/io/BufferedReader:cb	[C
/*     */     //   34: goto +16 -> 50
/*     */     //   37: astore_2
/*     */     //   38: aload_0
/*     */     //   39: aconst_null
/*     */     //   40: putfield 10	java/io/BufferedReader:in	Ljava/io/Reader;
/*     */     //   43: aload_0
/*     */     //   44: aconst_null
/*     */     //   45: putfield 11	java/io/BufferedReader:cb	[C
/*     */     //   48: aload_2
/*     */     //   49: athrow
/*     */     //   50: aload_1
/*     */     //   51: monitorexit
/*     */     //   52: goto +8 -> 60
/*     */     //   55: astore_3
/*     */     //   56: aload_1
/*     */     //   57: monitorexit
/*     */     //   58: aload_3
/*     */     //   59: athrow
/*     */     //   60: return
/*     */     // Line number table:
/*     */     //   Java source line #521	-> byte code offset #0
/*     */     //   Java source line #522	-> byte code offset #7
/*     */     //   Java source line #523	-> byte code offset #14
/*     */     //   Java source line #525	-> byte code offset #17
/*     */     //   Java source line #527	-> byte code offset #24
/*     */     //   Java source line #528	-> byte code offset #29
/*     */     //   Java source line #529	-> byte code offset #34
/*     */     //   Java source line #527	-> byte code offset #37
/*     */     //   Java source line #528	-> byte code offset #43
/*     */     //   Java source line #530	-> byte code offset #50
/*     */     //   Java source line #531	-> byte code offset #60
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	61	0	this	BufferedReader
/*     */     //   5	52	1	Ljava/lang/Object;	Object
/*     */     //   37	12	2	localObject1	Object
/*     */     //   55	4	3	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   17	24	37	finally
/*     */     //   7	16	55	finally
/*     */     //   17	52	55	finally
/*     */     //   55	58	55	finally
/*     */   }
/*     */   
/*     */   public Stream<String> lines()
/*     */   {
/* 562 */     Iterator local1 = new Iterator() {
/* 563 */       String nextLine = null;
/*     */       
/*     */       public boolean hasNext()
/*     */       {
/* 567 */         if (this.nextLine != null) {
/* 568 */           return true;
/*     */         }
/*     */         try {
/* 571 */           this.nextLine = BufferedReader.this.readLine();
/* 572 */           return this.nextLine != null;
/*     */         } catch (IOException localIOException) {
/* 574 */           throw new UncheckedIOException(localIOException);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       public String next()
/*     */       {
/* 581 */         if ((this.nextLine != null) || (hasNext())) {
/* 582 */           String str = this.nextLine;
/* 583 */           this.nextLine = null;
/* 584 */           return str;
/*     */         }
/* 586 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 589 */     };
/* 590 */     return StreamSupport.stream(Spliterators.spliteratorUnknownSize(local1, 272), false);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/BufferedReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */