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
/*     */ class HeapIntBuffer
/*     */   extends IntBuffer
/*     */ {
/*     */   HeapIntBuffer(int paramInt1, int paramInt2)
/*     */   {
/*  57 */     super(-1, 0, paramInt2, paramInt1, new int[paramInt1], 0);
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
/*     */   HeapIntBuffer(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */   {
/*  70 */     super(-1, paramInt1, paramInt1 + paramInt2, paramArrayOfInt.length, paramArrayOfInt, 0);
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
/*     */   protected HeapIntBuffer(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  86 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5);
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
/*     */   public IntBuffer slice()
/*     */   {
/* 103 */     return new HeapIntBuffer(this.hb, -1, 0, remaining(), remaining(), position() + this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer duplicate()
/*     */   {
/* 111 */     return new HeapIntBuffer(this.hb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer asReadOnlyBuffer()
/*     */   {
/* 121 */     return new HeapIntBufferR(this.hb, markValue(), position(), limit(), capacity(), this.offset);
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
/*     */   public int get() {
/* 135 */     return this.hb[ix(nextGetIndex())];
/*     */   }
/*     */   
/*     */   public int get(int paramInt) {
/* 139 */     return this.hb[ix(checkIndex(paramInt))];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer get(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */   {
/* 149 */     checkBounds(paramInt1, paramInt2, paramArrayOfInt.length);
/* 150 */     if (paramInt2 > remaining())
/* 151 */       throw new BufferUnderflowException();
/* 152 */     System.arraycopy(this.hb, ix(position()), paramArrayOfInt, paramInt1, paramInt2);
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
/*     */   public IntBuffer put(int paramInt)
/*     */   {
/* 169 */     this.hb[ix(nextPutIndex())] = paramInt;
/* 170 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer put(int paramInt1, int paramInt2)
/*     */   {
/* 178 */     this.hb[ix(checkIndex(paramInt1))] = paramInt2;
/* 179 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer put(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */   {
/* 187 */     checkBounds(paramInt1, paramInt2, paramArrayOfInt.length);
/* 188 */     if (paramInt2 > remaining())
/* 189 */       throw new BufferOverflowException();
/* 190 */     System.arraycopy(paramArrayOfInt, paramInt1, this.hb, ix(position()), paramInt2);
/* 191 */     position(position() + paramInt2);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer put(IntBuffer paramIntBuffer)
/*     */   {
/* 200 */     if ((paramIntBuffer instanceof HeapIntBuffer)) {
/* 201 */       if (paramIntBuffer == this)
/* 202 */         throw new IllegalArgumentException();
/* 203 */       HeapIntBuffer localHeapIntBuffer = (HeapIntBuffer)paramIntBuffer;
/* 204 */       int j = localHeapIntBuffer.remaining();
/* 205 */       if (j > remaining())
/* 206 */         throw new BufferOverflowException();
/* 207 */       System.arraycopy(localHeapIntBuffer.hb, localHeapIntBuffer.ix(localHeapIntBuffer.position()), this.hb, 
/* 208 */         ix(position()), j);
/* 209 */       localHeapIntBuffer.position(localHeapIntBuffer.position() + j);
/* 210 */       position(position() + j);
/* 211 */     } else if (paramIntBuffer.isDirect()) {
/* 212 */       int i = paramIntBuffer.remaining();
/* 213 */       if (i > remaining())
/* 214 */         throw new BufferOverflowException();
/* 215 */       paramIntBuffer.get(this.hb, ix(position()), i);
/* 216 */       position(position() + i);
/*     */     } else {
/* 218 */       super.put(paramIntBuffer);
/*     */     }
/* 220 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntBuffer compact()
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


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/HeapIntBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */