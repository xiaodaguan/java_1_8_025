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
/*     */ class DirectCharBufferS
/*     */   extends CharBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */   
/*     */ 
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(char[].class);
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
/*     */   DirectCharBufferS(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
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
/*     */   public CharBuffer slice()
/*     */   {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert (i <= j);
/* 210 */     int k = i <= j ? j - i : 0;
/* 211 */     int m = i << 1;
/* 212 */     assert (m >= 0);
/* 213 */     return new DirectCharBufferS(this, -1, 0, k, k, m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer duplicate()
/*     */   {
/* 221 */     return new DirectCharBufferS(this, markValue(), position(), limit(), capacity(), 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer asReadOnlyBuffer()
/*     */   {
/* 231 */     return new DirectCharBufferRS(this, markValue(), position(), limit(), capacity(), 0);
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
/* 245 */     return this.address + (paramInt << 1);
/*     */   }
/*     */   
/*     */   public char get() {
/* 249 */     return Bits.swap(unsafe.getChar(ix(nextGetIndex())));
/*     */   }
/*     */   
/*     */   public char get(int paramInt) {
/* 253 */     return Bits.swap(unsafe.getChar(ix(checkIndex(paramInt))));
/*     */   }
/*     */   
/*     */   char getUnchecked(int paramInt)
/*     */   {
/* 258 */     return Bits.swap(unsafe.getChar(ix(paramInt)));
/*     */   }
/*     */   
/*     */ 
/*     */   public CharBuffer get(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 264 */     if (paramInt2 << 1 > 6) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOfChar.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert (i <= j);
/* 269 */       int k = i <= j ? j - i : 0;
/* 270 */       if (paramInt2 > k) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToCharArray(ix(i), paramArrayOfChar, paramInt1 << 1, paramInt2 << 1);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 280 */         Bits.copyToArray(ix(i), paramArrayOfChar, arrayBaseOffset, paramInt1 << 1, paramInt2 << 1);
/*     */       }
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOfChar, paramInt1, paramInt2);
/*     */     }
/* 287 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer put(char paramChar)
/*     */   {
/* 297 */     unsafe.putChar(ix(nextPutIndex()), Bits.swap(paramChar));
/* 298 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer put(int paramInt, char paramChar)
/*     */   {
/* 306 */     unsafe.putChar(ix(checkIndex(paramInt)), Bits.swap(paramChar));
/* 307 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public CharBuffer put(CharBuffer paramCharBuffer)
/*     */   {
/*     */     int j;
/*     */     int k;
/* 315 */     if ((paramCharBuffer instanceof DirectCharBufferS)) {
/* 316 */       if (paramCharBuffer == this)
/* 317 */         throw new IllegalArgumentException();
/* 318 */       DirectCharBufferS localDirectCharBufferS = (DirectCharBufferS)paramCharBuffer;
/*     */       
/* 320 */       j = localDirectCharBufferS.position();
/* 321 */       k = localDirectCharBufferS.limit();
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
/* 332 */       unsafe.copyMemory(localDirectCharBufferS.ix(j), ix(n), m << 1);
/* 333 */       localDirectCharBufferS.position(j + m);
/* 334 */       position(n + m);
/* 335 */     } else if (paramCharBuffer.hb != null)
/*     */     {
/* 337 */       int i = paramCharBuffer.position();
/* 338 */       j = paramCharBuffer.limit();
/* 339 */       assert (i <= j);
/* 340 */       k = i <= j ? j - i : 0;
/*     */       
/* 342 */       put(paramCharBuffer.hb, paramCharBuffer.offset + i, k);
/* 343 */       paramCharBuffer.position(i + k);
/*     */     }
/*     */     else {
/* 346 */       super.put(paramCharBuffer);
/*     */     }
/* 348 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer put(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 356 */     if (paramInt2 << 1 > 6) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOfChar.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert (i <= j);
/* 361 */       int k = i <= j ? j - i : 0;
/* 362 */       if (paramInt2 > k) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromCharArray(paramArrayOfChar, paramInt1 << 1, 
/* 368 */           ix(i), paramInt2 << 1);
/*     */       }
/*     */       else
/* 371 */         Bits.copyFromArray(paramArrayOfChar, arrayBaseOffset, paramInt1 << 1, 
/* 372 */           ix(i), paramInt2 << 1);
/* 373 */       position(i + paramInt2);
/*     */     } else {
/* 375 */       super.put(paramArrayOfChar, paramInt1, paramInt2);
/*     */     }
/* 377 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer compact()
/*     */   {
/* 385 */     int i = position();
/* 386 */     int j = limit();
/* 387 */     assert (i <= j);
/* 388 */     int k = i <= j ? j - i : 0;
/*     */     
/* 390 */     unsafe.copyMemory(ix(i), ix(0), k << 1);
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
/*     */   public String toString(int paramInt1, int paramInt2)
/*     */   {
/* 412 */     if ((paramInt2 > limit()) || (paramInt1 > paramInt2))
/* 413 */       throw new IndexOutOfBoundsException();
/*     */     try {
/* 415 */       int i = paramInt2 - paramInt1;
/* 416 */       char[] arrayOfChar = new char[i];
/* 417 */       CharBuffer localCharBuffer1 = CharBuffer.wrap(arrayOfChar);
/* 418 */       CharBuffer localCharBuffer2 = duplicate();
/* 419 */       localCharBuffer2.position(paramInt1);
/* 420 */       localCharBuffer2.limit(paramInt2);
/* 421 */       localCharBuffer1.put(localCharBuffer2);
/* 422 */       return new String(arrayOfChar);
/*     */     } catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException) {
/* 424 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharBuffer subSequence(int paramInt1, int paramInt2)
/*     */   {
/* 432 */     int i = position();
/* 433 */     int j = limit();
/* 434 */     assert (i <= j);
/* 435 */     i = i <= j ? i : j;
/* 436 */     int k = j - i;
/*     */     
/* 438 */     if ((paramInt1 < 0) || (paramInt2 > k) || (paramInt1 > paramInt2)) {
/* 439 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 444 */     return new DirectCharBufferS(this, -1, i + paramInt1, i + paramInt2, capacity(), this.offset);
/*     */   }
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
/* 456 */     return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/DirectCharBufferS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */