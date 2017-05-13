/*     */ package java.util.zip;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Deflater
/*     */ {
/*     */   private final ZStreamRef zsRef;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */   private byte[] buf = new byte[0];
/*     */   
/*     */ 
/*     */ 
/*     */   private int off;
/*     */   
/*     */ 
/*     */ 
/*     */   private int len;
/*     */   
/*     */ 
/*     */ 
/*     */   private int level;
/*     */   
/*     */ 
/*     */ 
/*     */   private int strategy;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean setParams;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean finish;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean finished;
/*     */   
/*     */ 
/*     */ 
/*     */   private long bytesRead;
/*     */   
/*     */ 
/*     */ 
/*     */   private long bytesWritten;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int DEFLATED = 8;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int NO_COMPRESSION = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int BEST_SPEED = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int BEST_COMPRESSION = 9;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int DEFAULT_COMPRESSION = -1;
/*     */   
/*     */ 
/*     */   public static final int FILTERED = 1;
/*     */   
/*     */ 
/*     */   public static final int HUFFMAN_ONLY = 2;
/*     */   
/*     */ 
/*     */   public static final int DEFAULT_STRATEGY = 0;
/*     */   
/*     */ 
/*     */   public static final int NO_FLUSH = 0;
/*     */   
/*     */ 
/*     */   public static final int SYNC_FLUSH = 2;
/*     */   
/*     */ 
/*     */   public static final int FULL_FLUSH = 3;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 157 */     initIDs();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Deflater(int paramInt, boolean paramBoolean)
/*     */   {
/* 169 */     this.level = paramInt;
/* 170 */     this.strategy = 0;
/* 171 */     this.zsRef = new ZStreamRef(init(paramInt, 0, paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Deflater(int paramInt)
/*     */   {
/* 180 */     this(paramInt, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Deflater()
/*     */   {
/* 188 */     this(-1, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInput(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 200 */     if (paramArrayOfByte == null) {
/* 201 */       throw new NullPointerException();
/*     */     }
/* 203 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2)) {
/* 204 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 206 */     synchronized (this.zsRef) {
/* 207 */       this.buf = paramArrayOfByte;
/* 208 */       this.off = paramInt1;
/* 209 */       this.len = paramInt2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInput(byte[] paramArrayOfByte)
/*     */   {
/* 220 */     setInput(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public void setDictionary(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 236 */     if (paramArrayOfByte == null) {
/* 237 */       throw new NullPointerException();
/*     */     }
/* 239 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2)) {
/* 240 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 242 */     synchronized (this.zsRef) {
/* 243 */       ensureOpen();
/* 244 */       setDictionary(this.zsRef.address(), paramArrayOfByte, paramInt1, paramInt2);
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
/*     */   public void setDictionary(byte[] paramArrayOfByte)
/*     */   {
/* 259 */     setDictionary(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public void setStrategy(int paramInt)
/*     */   {
/* 275 */     switch (paramInt) {
/*     */     case 0: 
/*     */     case 1: 
/*     */     case 2: 
/*     */       break;
/*     */     default: 
/* 281 */       throw new IllegalArgumentException();
/*     */     }
/* 283 */     synchronized (this.zsRef) {
/* 284 */       if (this.strategy != paramInt) {
/* 285 */         this.strategy = paramInt;
/* 286 */         this.setParams = true;
/*     */       }
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
/*     */   public void setLevel(int paramInt)
/*     */   {
/* 303 */     if (((paramInt < 0) || (paramInt > 9)) && (paramInt != -1)) {
/* 304 */       throw new IllegalArgumentException("invalid compression level");
/*     */     }
/* 306 */     synchronized (this.zsRef) {
/* 307 */       if (this.level != paramInt) {
/* 308 */         this.level = paramInt;
/* 309 */         this.setParams = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean needsInput()
/*     */   {
/* 321 */     return this.len <= 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void finish()
/*     */   {
/* 329 */     synchronized (this.zsRef) {
/* 330 */       this.finish = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int deflate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 364 */     return deflate(paramArrayOfByte, paramInt1, paramInt2, 0);
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
/*     */   public int deflate(byte[] paramArrayOfByte)
/*     */   {
/* 383 */     return deflate(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
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
/*     */   public int deflate(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 431 */     if (paramArrayOfByte == null) {
/* 432 */       throw new NullPointerException();
/*     */     }
/* 434 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2)) {
/* 435 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 437 */     synchronized (this.zsRef) {
/* 438 */       ensureOpen();
/* 439 */       if ((paramInt3 == 0) || (paramInt3 == 2) || (paramInt3 == 3))
/*     */       {
/* 441 */         int i = this.len;
/* 442 */         int j = deflateBytes(this.zsRef.address(), paramArrayOfByte, paramInt1, paramInt2, paramInt3);
/* 443 */         this.bytesWritten += j;
/* 444 */         this.bytesRead += i - this.len;
/* 445 */         return j;
/*     */       }
/* 447 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAdler()
/*     */   {
/* 456 */     synchronized (this.zsRef) {
/* 457 */       ensureOpen();
/* 458 */       return getAdler(this.zsRef.address());
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
/*     */   public int getTotalIn()
/*     */   {
/* 472 */     return (int)getBytesRead();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getBytesRead()
/*     */   {
/* 482 */     synchronized (this.zsRef) {
/* 483 */       ensureOpen();
/* 484 */       return this.bytesRead;
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
/*     */   public int getTotalOut()
/*     */   {
/* 498 */     return (int)getBytesWritten();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getBytesWritten()
/*     */   {
/* 508 */     synchronized (this.zsRef) {
/* 509 */       ensureOpen();
/* 510 */       return this.bytesWritten;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 519 */     synchronized (this.zsRef) {
/* 520 */       ensureOpen();
/* 521 */       reset(this.zsRef.address());
/* 522 */       this.finish = false;
/* 523 */       this.finished = false;
/* 524 */       this.off = (this.len = 0);
/* 525 */       this.bytesRead = (this.bytesWritten = 0L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void end()
/*     */   {
/* 537 */     synchronized (this.zsRef) {
/* 538 */       long l = this.zsRef.address();
/* 539 */       this.zsRef.clear();
/* 540 */       if (l != 0L) {
/* 541 */         end(l);
/* 542 */         this.buf = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */   {
/* 551 */     end();
/*     */   }
/*     */   
/*     */   private void ensureOpen() {
/* 555 */     assert (Thread.holdsLock(this.zsRef));
/* 556 */     if (this.zsRef.address() == 0L) {
/* 557 */       throw new NullPointerException("Deflater has been closed");
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean finished()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 9	java/util/zip/Deflater:zsRef	Ljava/util/zip/ZStreamRef;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 28	java/util/zip/Deflater:finished	Z
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: ireturn
/*     */     //   14: astore_2
/*     */     //   15: aload_1
/*     */     //   16: monitorexit
/*     */     //   17: aload_2
/*     */     //   18: athrow
/*     */     // Line number table:
/*     */     //   Java source line #341	-> byte code offset #0
/*     */     //   Java source line #342	-> byte code offset #7
/*     */     //   Java source line #343	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	19	0	this	Deflater
/*     */     //   5	11	1	Ljava/lang/Object;	Object
/*     */     //   14	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	17	14	finally
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native long init(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   private static native void setDictionary(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*     */   
/*     */   private native int deflateBytes(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int getAdler(long paramLong);
/*     */   
/*     */   private static native void reset(long paramLong);
/*     */   
/*     */   private static native void end(long paramLong);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/Deflater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */