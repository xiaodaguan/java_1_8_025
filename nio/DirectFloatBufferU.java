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
/*     */ class DirectFloatBufferU
/*     */   extends FloatBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */   
/*     */ 
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(float[].class);
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
/*     */   DirectFloatBufferU(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
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
/*     */   public FloatBuffer slice()
/*     */   {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert (i <= j);
/* 210 */     int k = i <= j ? j - i : 0;
/* 211 */     int m = i << 2;
/* 212 */     assert (m >= 0);
/* 213 */     return new DirectFloatBufferU(this, -1, 0, k, k, m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer duplicate()
/*     */   {
/* 221 */     return new DirectFloatBufferU(this, markValue(), position(), limit(), capacity(), 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer asReadOnlyBuffer()
/*     */   {
/* 231 */     return new DirectFloatBufferRU(this, markValue(), position(), limit(), capacity(), 0);
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
/* 245 */     return this.address + (paramInt << 2);
/*     */   }
/*     */   
/*     */   public float get() {
/* 249 */     return unsafe.getFloat(ix(nextGetIndex()));
/*     */   }
/*     */   
/*     */   public float get(int paramInt) {
/* 253 */     return unsafe.getFloat(ix(checkIndex(paramInt)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer get(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 264 */     if (paramInt2 << 2 > 6) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOfFloat.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert (i <= j);
/* 269 */       int k = i <= j ? j - i : 0;
/* 270 */       if (paramInt2 > k) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToIntArray(ix(i), paramArrayOfFloat, paramInt1 << 2, paramInt2 << 2);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 280 */         Bits.copyToArray(ix(i), paramArrayOfFloat, arrayBaseOffset, paramInt1 << 2, paramInt2 << 2);
/*     */       }
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOfFloat, paramInt1, paramInt2);
/*     */     }
/* 287 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer put(float paramFloat)
/*     */   {
/* 297 */     unsafe.putFloat(ix(nextPutIndex()), paramFloat);
/* 298 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer put(int paramInt, float paramFloat)
/*     */   {
/* 306 */     unsafe.putFloat(ix(checkIndex(paramInt)), paramFloat);
/* 307 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public FloatBuffer put(FloatBuffer paramFloatBuffer)
/*     */   {
/*     */     int j;
/*     */     int k;
/* 315 */     if ((paramFloatBuffer instanceof DirectFloatBufferU)) {
/* 316 */       if (paramFloatBuffer == this)
/* 317 */         throw new IllegalArgumentException();
/* 318 */       DirectFloatBufferU localDirectFloatBufferU = (DirectFloatBufferU)paramFloatBuffer;
/*     */       
/* 320 */       j = localDirectFloatBufferU.position();
/* 321 */       k = localDirectFloatBufferU.limit();
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
/* 332 */       unsafe.copyMemory(localDirectFloatBufferU.ix(j), ix(n), m << 2);
/* 333 */       localDirectFloatBufferU.position(j + m);
/* 334 */       position(n + m);
/* 335 */     } else if (paramFloatBuffer.hb != null)
/*     */     {
/* 337 */       int i = paramFloatBuffer.position();
/* 338 */       j = paramFloatBuffer.limit();
/* 339 */       assert (i <= j);
/* 340 */       k = i <= j ? j - i : 0;
/*     */       
/* 342 */       put(paramFloatBuffer.hb, paramFloatBuffer.offset + i, k);
/* 343 */       paramFloatBuffer.position(i + k);
/*     */     }
/*     */     else {
/* 346 */       super.put(paramFloatBuffer);
/*     */     }
/* 348 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer put(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 356 */     if (paramInt2 << 2 > 6) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOfFloat.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert (i <= j);
/* 361 */       int k = i <= j ? j - i : 0;
/* 362 */       if (paramInt2 > k) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromIntArray(paramArrayOfFloat, paramInt1 << 2, 
/* 368 */           ix(i), paramInt2 << 2);
/*     */       }
/*     */       else
/* 371 */         Bits.copyFromArray(paramArrayOfFloat, arrayBaseOffset, paramInt1 << 2, 
/* 372 */           ix(i), paramInt2 << 2);
/* 373 */       position(i + paramInt2);
/*     */     } else {
/* 375 */       super.put(paramArrayOfFloat, paramInt1, paramInt2);
/*     */     }
/* 377 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer compact()
/*     */   {
/* 385 */     int i = position();
/* 386 */     int j = limit();
/* 387 */     assert (i <= j);
/* 388 */     int k = i <= j ? j - i : 0;
/*     */     
/* 390 */     unsafe.copyMemory(ix(i), ix(0), k << 2);
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


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/DirectFloatBufferU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */