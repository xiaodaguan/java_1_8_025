/*     */ package java.io;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ import java.util.Formatter;
/*     */ import sun.misc.JavaIOAccess;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.nio.cs.StreamDecoder;
/*     */ import sun.nio.cs.StreamEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Console
/*     */   implements Flushable
/*     */ {
/*     */   private Object readLock;
/*     */   private Object writeLock;
/*     */   private Reader reader;
/*     */   private Writer out;
/*     */   private PrintWriter pw;
/*     */   private Formatter formatter;
/*     */   private Charset cs;
/*     */   private char[] rcb;
/*     */   private static boolean echoOff;
/*     */   private static Console cons;
/*     */   
/*     */   public PrintWriter writer()
/*     */   {
/* 101 */     return this.pw;
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
/*     */   public Reader reader()
/*     */   {
/* 136 */     return this.reader;
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
/*     */   public Console format(String paramString, Object... paramVarArgs)
/*     */   {
/* 170 */     this.formatter.format(paramString, paramVarArgs).flush();
/* 171 */     return this;
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
/*     */   public Console printf(String paramString, Object... paramVarArgs)
/*     */   {
/* 209 */     return format(paramString, paramVarArgs);
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
/*     */   public String readLine(String paramString, Object... paramVarArgs)
/*     */   {
/* 244 */     String str = null;
/* 245 */     synchronized (this.writeLock) {
/* 246 */       synchronized (this.readLock) {
/* 247 */         if (paramString.length() != 0)
/* 248 */           this.pw.format(paramString, paramVarArgs);
/*     */         try {
/* 250 */           char[] arrayOfChar = readline(false);
/* 251 */           if (arrayOfChar != null)
/* 252 */             str = new String(arrayOfChar);
/*     */         } catch (IOException localIOException) {
/* 254 */           throw new IOError(localIOException);
/*     */         }
/*     */       }
/*     */     }
/* 258 */     return str;
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
/*     */   {
/* 272 */     return readLine("", new Object[0]);
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
/*     */   public char[] readPassword(String paramString, Object... paramVarArgs)
/*     */   {
/* 308 */     char[] arrayOfChar = null;
/* 309 */     synchronized (this.writeLock) {
/* 310 */       synchronized (this.readLock) {
/*     */         try {
/* 312 */           echoOff = echo(false);
/*     */         } catch (IOException localIOException1) {
/* 314 */           throw new IOError(localIOException1);
/*     */         }
/* 316 */         IOError localIOError = null;
/*     */         try {
/* 318 */           if (paramString.length() != 0)
/* 319 */             this.pw.format(paramString, paramVarArgs);
/* 320 */           arrayOfChar = readline(true);
/*     */         } catch (IOException localIOException3) {
/* 322 */           localIOError = new IOError(localIOException3);
/*     */         } finally {
/*     */           try {
/* 325 */             echoOff = echo(true);
/*     */           } catch (IOException localIOException5) {
/* 327 */             if (localIOError == null) {
/* 328 */               localIOError = new IOError(localIOException5);
/*     */             } else
/* 330 */               localIOError.addSuppressed(localIOException5);
/*     */           }
/* 332 */           if (localIOError != null)
/* 333 */             throw localIOError;
/*     */         }
/* 335 */         this.pw.println();
/*     */       }
/*     */     }
/* 338 */     return arrayOfChar;
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
/*     */   public char[] readPassword()
/*     */   {
/* 352 */     return readPassword("", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */   {
/* 360 */     this.pw.flush();
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
/*     */   private char[] readline(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 376 */     int i = this.reader.read(this.rcb, 0, this.rcb.length);
/* 377 */     if (i < 0)
/* 378 */       return null;
/* 379 */     if (this.rcb[(i - 1)] == '\r') {
/* 380 */       i--;
/* 381 */     } else if (this.rcb[(i - 1)] == '\n') {
/* 382 */       i--;
/* 383 */       if ((i > 0) && (this.rcb[(i - 1)] == '\r'))
/* 384 */         i--;
/*     */     }
/* 386 */     char[] arrayOfChar = new char[i];
/* 387 */     if (i > 0) {
/* 388 */       System.arraycopy(this.rcb, 0, arrayOfChar, 0, i);
/* 389 */       if (paramBoolean) {
/* 390 */         Arrays.fill(this.rcb, 0, i, ' ');
/*     */       }
/*     */     }
/* 393 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   private char[] grow() {
/* 397 */     assert (Thread.holdsLock(this.readLock));
/* 398 */     char[] arrayOfChar = new char[this.rcb.length * 2];
/* 399 */     System.arraycopy(this.rcb, 0, arrayOfChar, 0, this.rcb.length);
/* 400 */     this.rcb = arrayOfChar;
/* 401 */     return this.rcb;
/*     */   }
/*     */   
/*     */   class LineReader extends Reader { private Reader in;
/*     */     private char[] cb;
/*     */     private int nChars;
/*     */     private int nextChar;
/*     */     boolean leftoverLF;
/*     */     
/* 410 */     LineReader(Reader paramReader) { this.in = paramReader;
/* 411 */       this.cb = new char['Ѐ'];
/* 412 */       this.nextChar = (this.nChars = 0);
/* 413 */       this.leftoverLF = false;
/*     */     }
/*     */     
/*     */     public void close() {}
/*     */     
/* 418 */     public boolean ready() throws IOException { return this.in.ready(); }
/*     */     
/*     */ 
/*     */     public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */       throws IOException
/*     */     {
/* 424 */       int i = paramInt1;
/* 425 */       int j = paramInt1 + paramInt2;
/* 426 */       if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt2 < 0) || (j < 0) || (j > paramArrayOfChar.length))
/*     */       {
/* 428 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 430 */       synchronized (Console.this.readLock) {
/* 431 */         int k = 0;
/* 432 */         int m = 0;
/*     */         do {
/* 434 */           if (this.nextChar >= this.nChars) {
/* 435 */             int n = 0;
/*     */             do {
/* 437 */               n = this.in.read(this.cb, 0, this.cb.length);
/* 438 */             } while (n == 0);
/* 439 */             if (n > 0) {
/* 440 */               this.nChars = n;
/* 441 */               this.nextChar = 0;
/* 442 */               if ((n < this.cb.length) && (this.cb[(n - 1)] != '\n') && (this.cb[(n - 1)] != '\r'))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 449 */                 k = 1;
/*     */               }
/*     */             } else {
/* 452 */               if (i - paramInt1 == 0)
/* 453 */                 return -1;
/* 454 */               return i - paramInt1;
/*     */             }
/*     */           }
/* 457 */           if ((this.leftoverLF) && (paramArrayOfChar == Console.this.rcb) && (this.cb[this.nextChar] == '\n'))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 462 */             this.nextChar += 1;
/*     */           }
/* 464 */           this.leftoverLF = false;
/* 465 */           while (this.nextChar < this.nChars) {
/* 466 */             m = paramArrayOfChar[(i++)] = this.cb[this.nextChar];
/* 467 */             this.cb[(this.nextChar++)] = '\000';
/* 468 */             if (m == 10)
/* 469 */               return i - paramInt1;
/* 470 */             if (m == 13) {
/* 471 */               if (i == j)
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/* 476 */                 if (paramArrayOfChar == Console.this.rcb) {
/* 477 */                   paramArrayOfChar = Console.this.grow();
/* 478 */                   j = paramArrayOfChar.length;
/*     */                 } else {
/* 480 */                   this.leftoverLF = true;
/* 481 */                   return i - paramInt1;
/*     */                 }
/*     */               }
/* 484 */               if ((this.nextChar == this.nChars) && (this.in.ready()))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 492 */                 this.nChars = this.in.read(this.cb, 0, this.cb.length);
/* 493 */                 this.nextChar = 0;
/*     */               }
/* 495 */               if ((this.nextChar < this.nChars) && (this.cb[this.nextChar] == '\n')) {
/* 496 */                 paramArrayOfChar[(i++)] = '\n';
/* 497 */                 this.nextChar += 1;
/*     */               }
/* 499 */               return i - paramInt1; }
/* 500 */             if (i == j) {
/* 501 */               if (paramArrayOfChar == Console.this.rcb) {
/* 502 */                 paramArrayOfChar = Console.this.grow();
/* 503 */                 j = paramArrayOfChar.length;
/*     */               } else {
/* 505 */                 return i - paramInt1;
/*     */               }
/*     */             }
/*     */           }
/* 509 */         } while (k == 0);
/* 510 */         return i - paramInt1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 522 */       SharedSecrets.getJavaLangAccess().registerShutdownHook(0, false, new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try {
/* 527 */             if (Console.echoOff) {
/* 528 */               Console.echo(true);
/*     */             }
/*     */           }
/*     */           catch (IOException localIOException) {}
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (IllegalStateException localIllegalStateException) {}
/*     */     
/*     */ 
/* 538 */     SharedSecrets.setJavaIOAccess(new JavaIOAccess() {
/*     */       public Console console() {
/* 540 */         if (Console.access$500()) {
/* 541 */           if (Console.cons == null)
/* 542 */             Console.access$602(new Console(null));
/* 543 */           return Console.cons;
/*     */         }
/* 545 */         return null;
/*     */       }
/*     */       
/*     */ 
/*     */       public Charset charset()
/*     */       {
/* 551 */         return Console.cons.cs;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private Console()
/*     */   {
/* 558 */     this.readLock = new Object();
/* 559 */     this.writeLock = new Object();
/* 560 */     String str = encoding();
/* 561 */     if (str != null) {
/*     */       try {
/* 563 */         this.cs = Charset.forName(str);
/*     */       } catch (Exception localException) {}
/*     */     }
/* 566 */     if (this.cs == null)
/* 567 */       this.cs = Charset.defaultCharset();
/* 568 */     this.out = StreamEncoder.forOutputStreamWriter(new FileOutputStream(FileDescriptor.out), this.writeLock, this.cs);
/*     */     
/*     */ 
/*     */ 
/* 572 */     this.pw = new PrintWriter(this.out, true) { public void close() {} };
/* 573 */     this.formatter = new Formatter(this.out);
/* 574 */     this.reader = new LineReader(StreamDecoder.forInputStreamReader(new FileInputStream(FileDescriptor.in), this.readLock, this.cs));
/*     */     
/*     */ 
/*     */ 
/* 578 */     this.rcb = new char['Ѐ'];
/*     */   }
/*     */   
/*     */   private static native String encoding();
/*     */   
/*     */   private static native boolean echo(boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */   private static native boolean istty();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/Console.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */