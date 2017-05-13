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
/*     */ class ByteBufferAsDoubleBufferL
/*     */   extends DoubleBuffer
/*     */ {
/*     */   protected final ByteBuffer bb;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final int offset;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   ByteBufferAsDoubleBufferL(ByteBuffer paramByteBuffer)
/*     */   {
/*  44 */     super(-1, 0, paramByteBuffer
/*  45 */       .remaining() >> 3, paramByteBuffer
/*  46 */       .remaining() >> 3);
/*  47 */     this.bb = paramByteBuffer;
/*     */     
/*  49 */     int i = capacity();
/*  50 */     limit(i);
/*  51 */     int j = position();
/*  52 */     assert (j <= i);
/*  53 */     this.offset = j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   ByteBufferAsDoubleBufferL(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  64 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  65 */     this.bb = paramByteBuffer;
/*  66 */     this.offset = paramInt5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DoubleBuffer slice()
/*     */   {
/*  73 */     int i = position();
/*  74 */     int j = limit();
/*  75 */     assert (i <= j);
/*  76 */     int k = i <= j ? j - i : 0;
/*  77 */     int m = (i << 3) + this.offset;
/*  78 */     assert (m >= 0);
/*  79 */     return new ByteBufferAsDoubleBufferL(this.bb, -1, 0, k, k, m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer duplicate()
/*     */   {
/*  87 */     return new ByteBufferAsDoubleBufferL(this.bb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer asReadOnlyBuffer()
/*     */   {
/*  97 */     return new ByteBufferAsDoubleBufferRL(this.bb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int ix(int paramInt)
/*     */   {
/* 107 */     return (paramInt << 3) + this.offset;
/*     */   }
/*     */   
/*     */   public double get() {
/* 111 */     return Bits.getDoubleL(this.bb, ix(nextGetIndex()));
/*     */   }
/*     */   
/*     */   public double get(int paramInt) {
/* 115 */     return Bits.getDoubleL(this.bb, ix(checkIndex(paramInt)));
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
/*     */   public DoubleBuffer put(double paramDouble)
/*     */   {
/* 128 */     Bits.putDoubleL(this.bb, ix(nextPutIndex()), paramDouble);
/* 129 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer put(int paramInt, double paramDouble)
/*     */   {
/* 137 */     Bits.putDoubleL(this.bb, ix(checkIndex(paramInt)), paramDouble);
/* 138 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleBuffer compact()
/*     */   {
/* 146 */     int i = position();
/* 147 */     int j = limit();
/* 148 */     assert (i <= j);
/* 149 */     int k = i <= j ? j - i : 0;
/*     */     
/* 151 */     ByteBuffer localByteBuffer1 = this.bb.duplicate();
/* 152 */     localByteBuffer1.limit(ix(j));
/* 153 */     localByteBuffer1.position(ix(0));
/* 154 */     ByteBuffer localByteBuffer2 = localByteBuffer1.slice();
/* 155 */     localByteBuffer2.position(i << 3);
/* 156 */     localByteBuffer2.compact();
/* 157 */     position(k);
/* 158 */     limit(capacity());
/* 159 */     discardMark();
/* 160 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isDirect()
/*     */   {
/* 167 */     return this.bb.isDirect();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 171 */     return false;
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
/*     */   public ByteOrder order()
/*     */   {
/* 221 */     return ByteOrder.LITTLE_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/ByteBufferAsDoubleBufferL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */