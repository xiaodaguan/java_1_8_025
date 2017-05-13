/*     */ package java.nio;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HeapLongBuffer
/*     */   extends LongBuffer
/*     */ {
/*     */   HeapLongBuffer(int paramInt1, int paramInt2)
/*     */   {
/*  57 */     super(-1, 0, paramInt2, paramInt1, new long[paramInt1], 0);
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
/*     */   HeapLongBuffer(long[] paramArrayOfLong, int paramInt1, int paramInt2)
/*     */   {
/*  70 */     super(-1, paramInt1, paramInt1 + paramInt2, paramArrayOfLong.length, paramArrayOfLong, 0);
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
/*     */   protected HeapLongBuffer(long[] paramArrayOfLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  86 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfLong, paramInt5);
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
/*     */   public LongBuffer slice()
/*     */   {
/* 103 */     return new HeapLongBuffer(this.hb, -1, 0, remaining(), remaining(), position() + this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer duplicate()
/*     */   {
/* 111 */     return new HeapLongBuffer(this.hb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer asReadOnlyBuffer()
/*     */   {
/* 121 */     return new HeapLongBufferR(this.hb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int ix(int paramInt)
/*     */   {
/* 131 */     return paramInt + this.offset;
/*     */   }
/*     */   
/*     */   public long get() {
/* 135 */     return this.hb[ix(nextGetIndex())];
/*     */   }
/*     */   
/*     */   public long get(int paramInt) {
/* 139 */     return this.hb[ix(checkIndex(paramInt))];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer get(long[] paramArrayOfLong, int paramInt1, int paramInt2)
/*     */   {
/* 149 */     checkBounds(paramInt1, paramInt2, paramArrayOfLong.length);
/* 150 */     if (paramInt2 > remaining())
/* 151 */       throw new BufferUnderflowException();
/* 152 */     System.arraycopy(this.hb, ix(position()), paramArrayOfLong, paramInt1, paramInt2);
/* 153 */     position(position() + paramInt2);
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 158 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isReadOnly()
/*     */   {
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public LongBuffer put(long paramLong)
/*     */   {
/* 169 */     this.hb[ix(nextPutIndex())] = paramLong;
/* 170 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer put(int paramInt, long paramLong)
/*     */   {
/* 178 */     this.hb[ix(checkIndex(paramInt))] = paramLong;
/* 179 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer put(long[] paramArrayOfLong, int paramInt1, int paramInt2)
/*     */   {
/* 187 */     checkBounds(paramInt1, paramInt2, paramArrayOfLong.length);
/* 188 */     if (paramInt2 > remaining())
/* 189 */       throw new BufferOverflowException();
/* 190 */     System.arraycopy(paramArrayOfLong, paramInt1, this.hb, ix(position()), paramInt2);
/* 191 */     position(position() + paramInt2);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer put(LongBuffer paramLongBuffer)
/*     */   {
/* 200 */     if ((paramLongBuffer instanceof HeapLongBuffer)) {
/* 201 */       if (paramLongBuffer == this)
/* 202 */         throw new IllegalArgumentException();
/* 203 */       HeapLongBuffer localHeapLongBuffer = (HeapLongBuffer)paramLongBuffer;
/* 204 */       int j = localHeapLongBuffer.remaining();
/* 205 */       if (j > remaining())
/* 206 */         throw new BufferOverflowException();
/* 207 */       System.arraycopy(localHeapLongBuffer.hb, localHeapLongBuffer.ix(localHeapLongBuffer.position()), this.hb, 
/* 208 */         ix(position()), j);
/* 209 */       localHeapLongBuffer.position(localHeapLongBuffer.position() + j);
/* 210 */       position(position() + j);
/* 211 */     } else if (paramLongBuffer.isDirect()) {
/* 212 */       int i = paramLongBuffer.remaining();
/* 213 */       if (i > remaining())
/* 214 */         throw new BufferOverflowException();
/* 215 */       paramLongBuffer.get(this.hb, ix(position()), i);
/* 216 */       position(position() + i);
/*     */     } else {
/* 218 */       super.put(paramLongBuffer);
/*     */     }
/* 220 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LongBuffer compact()
/*     */   {
/* 228 */     System.arraycopy(this.hb, ix(position()), this.hb, ix(0), remaining());
/* 229 */     position(remaining());
/* 230 */     limit(capacity());
/* 231 */     discardMark();
/* 232 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 596 */     return ByteOrder.nativeOrder();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/HeapLongBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */