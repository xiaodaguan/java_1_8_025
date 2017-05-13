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
/*     */ class HeapFloatBuffer
/*     */   extends FloatBuffer
/*     */ {
/*     */   HeapFloatBuffer(int paramInt1, int paramInt2)
/*     */   {
/*  57 */     super(-1, 0, paramInt2, paramInt1, new float[paramInt1], 0);
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
/*     */   HeapFloatBuffer(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/*  70 */     super(-1, paramInt1, paramInt1 + paramInt2, paramArrayOfFloat.length, paramArrayOfFloat, 0);
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
/*     */   protected HeapFloatBuffer(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  86 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfFloat, paramInt5);
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
/*     */   public FloatBuffer slice()
/*     */   {
/* 103 */     return new HeapFloatBuffer(this.hb, -1, 0, remaining(), remaining(), position() + this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer duplicate()
/*     */   {
/* 111 */     return new HeapFloatBuffer(this.hb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer asReadOnlyBuffer()
/*     */   {
/* 121 */     return new HeapFloatBufferR(this.hb, markValue(), position(), limit(), capacity(), this.offset);
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
/*     */   public float get() {
/* 135 */     return this.hb[ix(nextGetIndex())];
/*     */   }
/*     */   
/*     */   public float get(int paramInt) {
/* 139 */     return this.hb[ix(checkIndex(paramInt))];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer get(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 149 */     checkBounds(paramInt1, paramInt2, paramArrayOfFloat.length);
/* 150 */     if (paramInt2 > remaining())
/* 151 */       throw new BufferUnderflowException();
/* 152 */     System.arraycopy(this.hb, ix(position()), paramArrayOfFloat, paramInt1, paramInt2);
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
/*     */   public FloatBuffer put(float paramFloat)
/*     */   {
/* 169 */     this.hb[ix(nextPutIndex())] = paramFloat;
/* 170 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer put(int paramInt, float paramFloat)
/*     */   {
/* 178 */     this.hb[ix(checkIndex(paramInt))] = paramFloat;
/* 179 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer put(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 187 */     checkBounds(paramInt1, paramInt2, paramArrayOfFloat.length);
/* 188 */     if (paramInt2 > remaining())
/* 189 */       throw new BufferOverflowException();
/* 190 */     System.arraycopy(paramArrayOfFloat, paramInt1, this.hb, ix(position()), paramInt2);
/* 191 */     position(position() + paramInt2);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer put(FloatBuffer paramFloatBuffer)
/*     */   {
/* 200 */     if ((paramFloatBuffer instanceof HeapFloatBuffer)) {
/* 201 */       if (paramFloatBuffer == this)
/* 202 */         throw new IllegalArgumentException();
/* 203 */       HeapFloatBuffer localHeapFloatBuffer = (HeapFloatBuffer)paramFloatBuffer;
/* 204 */       int j = localHeapFloatBuffer.remaining();
/* 205 */       if (j > remaining())
/* 206 */         throw new BufferOverflowException();
/* 207 */       System.arraycopy(localHeapFloatBuffer.hb, localHeapFloatBuffer.ix(localHeapFloatBuffer.position()), this.hb, 
/* 208 */         ix(position()), j);
/* 209 */       localHeapFloatBuffer.position(localHeapFloatBuffer.position() + j);
/* 210 */       position(position() + j);
/* 211 */     } else if (paramFloatBuffer.isDirect()) {
/* 212 */       int i = paramFloatBuffer.remaining();
/* 213 */       if (i > remaining())
/* 214 */         throw new BufferOverflowException();
/* 215 */       paramFloatBuffer.get(this.hb, ix(position()), i);
/* 216 */       position(position() + i);
/*     */     } else {
/* 218 */       super.put(paramFloatBuffer);
/*     */     }
/* 220 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FloatBuffer compact()
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


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/HeapFloatBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */