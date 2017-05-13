/*     */ package java.nio;
/*     */ 
/*     */ import sun.misc.Cleaner;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.nio.ch.DirectBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DirectDoubleBufferU
/*     */   extends DoubleBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */   
/*     */ 
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(double[].class);
/*     */   
/*     */ 
/*  55 */   protected static final boolean unaligned = Bits.unaligned();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Object att;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object attachment()
/*     */   {
/*  67 */     return this.att;
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
/*     */   public Cleaner cleaner()
/*     */   {
/* 107 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   DirectDoubleBufferU(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 195 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/* 196 */     this.address = (paramDirectBuffer.address() + paramInt5);
/*     */     
/*     */ 
/*     */ 
/* 200 */     this.att = paramDirectBuffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DoubleBuffer slice()
/*     */   {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert (i <= j);
/* 210 */     int k = i <= j ? j - i : 0;
/* 211 */     int m = i << 3;
/* 212 */     assert (m >= 0);
/* 213 */     return new DirectDoubleBufferU(this, -1, 0, k, k, m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer duplicate()
/*     */   {
/* 221 */     return new DirectDoubleBufferU(this, markValue(), position(), limit(), capacity(), 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer asReadOnlyBuffer()
/*     */   {
/* 231 */     return new DirectDoubleBufferRU(this, markValue(), position(), limit(), capacity(), 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long address()
/*     */   {
/* 241 */     return this.address;
/*     */   }
/*     */   
/*     */   private long ix(int paramInt) {
/* 245 */     return this.address + (paramInt << 3);
/*     */   }
/*     */   
/*     */   public double get() {
/* 249 */     return unsafe.getDouble(ix(nextGetIndex()));
/*     */   }
/*     */   
/*     */   public double get(int paramInt) {
/* 253 */     return unsafe.getDouble(ix(checkIndex(paramInt)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer get(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*     */   {
/* 264 */     if (paramInt2 << 3 > 6) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOfDouble.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert (i <= j);
/* 269 */       int k = i <= j ? j - i : 0;
/* 270 */       if (paramInt2 > k) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToLongArray(ix(i), paramArrayOfDouble, paramInt1 << 3, paramInt2 << 3);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 280 */         Bits.copyToArray(ix(i), paramArrayOfDouble, arrayBaseOffset, paramInt1 << 3, paramInt2 << 3);
/*     */       }
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOfDouble, paramInt1, paramInt2);
/*     */     }
/* 287 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(double paramDouble)
/*     */   {
/* 297 */     unsafe.putDouble(ix(nextPutIndex()), paramDouble);
/* 298 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(int paramInt, double paramDouble)
/*     */   {
/* 306 */     unsafe.putDouble(ix(checkIndex(paramInt)), paramDouble);
/* 307 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public DoubleBuffer put(DoubleBuffer paramDoubleBuffer)
/*     */   {
/*     */     int j;
/*     */     int k;
/* 315 */     if ((paramDoubleBuffer instanceof DirectDoubleBufferU)) {
/* 316 */       if (paramDoubleBuffer == this)
/* 317 */         throw new IllegalArgumentException();
/* 318 */       DirectDoubleBufferU localDirectDoubleBufferU = (DirectDoubleBufferU)paramDoubleBuffer;
/*     */       
/* 320 */       j = localDirectDoubleBufferU.position();
/* 321 */       k = localDirectDoubleBufferU.limit();
/* 322 */       assert (j <= k);
/* 323 */       int m = j <= k ? k - j : 0;
/*     */       
/* 325 */       int n = position();
/* 326 */       int i1 = limit();
/* 327 */       assert (n <= i1);
/* 328 */       int i2 = n <= i1 ? i1 - n : 0;
/*     */       
/* 330 */       if (m > i2)
/* 331 */         throw new BufferOverflowException();
/* 332 */       unsafe.copyMemory(localDirectDoubleBufferU.ix(j), ix(n), m << 3);
/* 333 */       localDirectDoubleBufferU.position(j + m);
/* 334 */       position(n + m);
/* 335 */     } else if (paramDoubleBuffer.hb != null)
/*     */     {
/* 337 */       int i = paramDoubleBuffer.position();
/* 338 */       j = paramDoubleBuffer.limit();
/* 339 */       assert (i <= j);
/* 340 */       k = i <= j ? j - i : 0;
/*     */       
/* 342 */       put(paramDoubleBuffer.hb, paramDoubleBuffer.offset + i, k);
/* 343 */       paramDoubleBuffer.position(i + k);
/*     */     }
/*     */     else {
/* 346 */       super.put(paramDoubleBuffer);
/*     */     }
/* 348 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*     */   {
/* 356 */     if (paramInt2 << 3 > 6) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOfDouble.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert (i <= j);
/* 361 */       int k = i <= j ? j - i : 0;
/* 362 */       if (paramInt2 > k) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromLongArray(paramArrayOfDouble, paramInt1 << 3, 
/* 368 */           ix(i), paramInt2 << 3);
/*     */       }
/*     */       else
/* 371 */         Bits.copyFromArray(paramArrayOfDouble, arrayBaseOffset, paramInt1 << 3, 
/* 372 */           ix(i), paramInt2 << 3);
/* 373 */       position(i + paramInt2);
/*     */     } else {
/* 375 */       super.put(paramArrayOfDouble, paramInt1, paramInt2);
/*     */     }
/* 377 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer compact()
/*     */   {
/* 385 */     int i = position();
/* 386 */     int j = limit();
/* 387 */     assert (i <= j);
/* 388 */     int k = i <= j ? j - i : 0;
/*     */     
/* 390 */     unsafe.copyMemory(ix(i), ix(0), k << 3);
/* 391 */     position(k);
/* 392 */     limit(capacity());
/* 393 */     discardMark();
/* 394 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isDirect()
/*     */   {
/* 401 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 405 */     return false;
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
/*     */   public ByteOrder order()
/*     */   {
/* 460 */     return ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/DirectDoubleBufferU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */