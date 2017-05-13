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
/*     */ class ByteBufferAsCharBufferRB
/*     */   extends ByteBufferAsCharBufferB
/*     */ {
/*     */   ByteBufferAsCharBufferRB(ByteBuffer paramByteBuffer)
/*     */   {
/*  55 */     super(paramByteBuffer);
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
/*     */   ByteBufferAsCharBufferRB(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  68 */     super(paramByteBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */   
/*     */   public CharBuffer slice()
/*     */   {
/*  73 */     int i = position();
/*  74 */     int j = limit();
/*  75 */     assert (i <= j);
/*  76 */     int k = i <= j ? j - i : 0;
/*  77 */     int m = (i << 1) + this.offset;
/*  78 */     assert (m >= 0);
/*  79 */     return new ByteBufferAsCharBufferRB(this.bb, -1, 0, k, k, m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer duplicate()
/*     */   {
/*  87 */     return new ByteBufferAsCharBufferRB(this.bb, markValue(), position(), limit(), capacity(), this.offset);
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
/*     */   public CharBuffer asReadOnlyBuffer()
/*     */   {
/* 100 */     return duplicate();
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
/*     */   public CharBuffer put(char paramChar)
/*     */   {
/* 131 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer put(int paramInt, char paramChar)
/*     */   {
/* 140 */     throw new ReadOnlyBufferException();
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
/*     */   public CharBuffer compact()
/*     */   {
/* 162 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/* 167 */     return this.bb.isDirect();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 171 */     return true;
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
/* 209 */     return new ByteBufferAsCharBufferRB(this.bb, -1, i + paramInt1, i + paramInt2, capacity(), this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteOrder order()
/*     */   {
/* 218 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/ByteBufferAsCharBufferRB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */