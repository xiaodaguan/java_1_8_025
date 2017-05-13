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
/*     */ class HeapDoubleBuffer
/*     */   extends DoubleBuffer
/*     */ {
/*     */   HeapDoubleBuffer(int paramInt1, int paramInt2)
/*     */   {
/*  57 */     super(-1, 0, paramInt2, paramInt1, new double[paramInt1], 0);
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
/*     */   HeapDoubleBuffer(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*     */   {
/*  70 */     super(-1, paramInt1, paramInt1 + paramInt2, paramArrayOfDouble.length, paramArrayOfDouble, 0);
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
/*     */   protected HeapDoubleBuffer(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  86 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfDouble, paramInt5);
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
/*     */   public DoubleBuffer slice()
/*     */   {
/* 103 */     return new HeapDoubleBuffer(this.hb, -1, 0, remaining(), remaining(), position() + this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer duplicate()
/*     */   {
/* 111 */     return new HeapDoubleBuffer(this.hb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer asReadOnlyBuffer()
/*     */   {
/* 121 */     return new HeapDoubleBufferR(this.hb, markValue(), position(), limit(), capacity(), this.offset);
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
/*     */   public double get() {
/* 135 */     return this.hb[ix(nextGetIndex())];
/*     */   }
/*     */   
/*     */   public double get(int paramInt) {
/* 139 */     return this.hb[ix(checkIndex(paramInt))];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer get(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*     */   {
/* 149 */     checkBounds(paramInt1, paramInt2, paramArrayOfDouble.length);
/* 150 */     if (paramInt2 > remaining())
/* 151 */       throw new BufferUnderflowException();
/* 152 */     System.arraycopy(this.hb, ix(position()), paramArrayOfDouble, paramInt1, paramInt2);
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
/*     */   public DoubleBuffer put(double paramDouble)
/*     */   {
/* 169 */     this.hb[ix(nextPutIndex())] = paramDouble;
/* 170 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(int paramInt, double paramDouble)
/*     */   {
/* 178 */     this.hb[ix(checkIndex(paramInt))] = paramDouble;
/* 179 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*     */   {
/* 187 */     checkBounds(paramInt1, paramInt2, paramArrayOfDouble.length);
/* 188 */     if (paramInt2 > remaining())
/* 189 */       throw new BufferOverflowException();
/* 190 */     System.arraycopy(paramArrayOfDouble, paramInt1, this.hb, ix(position()), paramInt2);
/* 191 */     position(position() + paramInt2);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(DoubleBuffer paramDoubleBuffer)
/*     */   {
/* 200 */     if ((paramDoubleBuffer instanceof HeapDoubleBuffer)) {
/* 201 */       if (paramDoubleBuffer == this)
/* 202 */         throw new IllegalArgumentException();
/* 203 */       HeapDoubleBuffer localHeapDoubleBuffer = (HeapDoubleBuffer)paramDoubleBuffer;
/* 204 */       int j = localHeapDoubleBuffer.remaining();
/* 205 */       if (j > remaining())
/* 206 */         throw new BufferOverflowException();
/* 207 */       System.arraycopy(localHeapDoubleBuffer.hb, localHeapDoubleBuffer.ix(localHeapDoubleBuffer.position()), this.hb, 
/* 208 */         ix(position()), j);
/* 209 */       localHeapDoubleBuffer.position(localHeapDoubleBuffer.position() + j);
/* 210 */       position(position() + j);
/* 211 */     } else if (paramDoubleBuffer.isDirect()) {
/* 212 */       int i = paramDoubleBuffer.remaining();
/* 213 */       if (i > remaining())
/* 214 */         throw new BufferOverflowException();
/* 215 */       paramDoubleBuffer.get(this.hb, ix(position()), i);
/* 216 */       position(position() + i);
/*     */     } else {
/* 218 */       super.put(paramDoubleBuffer);
/*     */     }
/* 220 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer compact()
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


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/HeapDoubleBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */