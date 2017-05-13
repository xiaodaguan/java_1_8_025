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
/*     */ class ByteBufferAsCharBufferL
/*     */   extends CharBuffer
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
/*     */   ByteBufferAsCharBufferL(ByteBuffer paramByteBuffer)
/*     */   {
/*  44 */     super(-1, 0, paramByteBuffer
/*  45 */       .remaining() >> 1, paramByteBuffer
/*  46 */       .remaining() >> 1);
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
/*     */   ByteBufferAsCharBufferL(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  64 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  65 */     this.bb = paramByteBuffer;
/*  66 */     this.offset = paramInt5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharBuffer slice()
/*     */   {
/*  73 */     int i = position();
/*  74 */     int j = limit();
/*  75 */     assert (i <= j);
/*  76 */     int k = i <= j ? j - i : 0;
/*  77 */     int m = (i << 1) + this.offset;
/*  78 */     assert (m >= 0);
/*  79 */     return new ByteBufferAsCharBufferL(this.bb, -1, 0, k, k, m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer duplicate()
/*     */   {
/*  87 */     return new ByteBufferAsCharBufferL(this.bb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer asReadOnlyBuffer()
/*     */   {
/*  97 */     return new ByteBufferAsCharBufferRL(this.bb, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int ix(int paramInt)
/*     */   {
/* 107 */     return (paramInt << 1) + this.offset;
/*     */   }
/*     */   
/*     */   public char get() {
/* 111 */     return Bits.getCharL(this.bb, ix(nextGetIndex()));
/*     */   }
/*     */   
/*     */   public char get(int paramInt) {
/* 115 */     return Bits.getCharL(this.bb, ix(checkIndex(paramInt)));
/*     */   }
/*     */   
/*     */   char getUnchecked(int paramInt)
/*     */   {
/* 120 */     return Bits.getCharL(this.bb, ix(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer put(char paramChar)
/*     */   {
/* 128 */     Bits.putCharL(this.bb, ix(nextPutIndex()), paramChar);
/* 129 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer put(int paramInt, char paramChar)
/*     */   {
/* 137 */     Bits.putCharL(this.bb, ix(checkIndex(paramInt)), paramChar);
/* 138 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer compact()
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
/* 155 */     localByteBuffer2.position(i << 1);
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
/*     */   public String toString(int paramInt1, int paramInt2)
/*     */   {
/* 177 */     if ((paramInt2 > limit()) || (paramInt1 > paramInt2))
/* 178 */       throw new IndexOutOfBoundsException();
/*     */     try {
/* 180 */       int i = paramInt2 - paramInt1;
/* 181 */       char[] arrayOfChar = new char[i];
/* 182 */       CharBuffer localCharBuffer1 = CharBuffer.wrap(arrayOfChar);
/* 183 */       CharBuffer localCharBuffer2 = duplicate();
/* 184 */       localCharBuffer2.position(paramInt1);
/* 185 */       localCharBuffer2.limit(paramInt2);
/* 186 */       localCharBuffer1.put(localCharBuffer2);
/* 187 */       return new String(arrayOfChar);
/*     */     } catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException) {
/* 189 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharBuffer subSequence(int paramInt1, int paramInt2)
/*     */   {
/* 197 */     int i = position();
/* 198 */     int j = limit();
/* 199 */     assert (i <= j);
/* 200 */     i = i <= j ? i : j;
/* 201 */     int k = j - i;
/*     */     
/* 203 */     if ((paramInt1 < 0) || (paramInt2 > k) || (paramInt1 > paramInt2)) {
/* 204 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 209 */     return new ByteBufferAsCharBufferL(this.bb, -1, i + paramInt1, i + paramInt2, capacity(), this.offset);
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
/* 221 */     return ByteOrder.LITTLE_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/ByteBufferAsCharBufferL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */