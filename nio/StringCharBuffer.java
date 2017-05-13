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
/*     */ class StringCharBuffer
/*     */   extends CharBuffer
/*     */ {
/*     */   CharSequence str;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   StringCharBuffer(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/*  37 */     super(-1, paramInt1, paramInt2, paramCharSequence.length());
/*  38 */     int i = paramCharSequence.length();
/*  39 */     if ((paramInt1 < 0) || (paramInt1 > i) || (paramInt2 < paramInt1) || (paramInt2 > i))
/*  40 */       throw new IndexOutOfBoundsException();
/*  41 */     this.str = paramCharSequence;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharBuffer slice()
/*     */   {
/*  50 */     return new StringCharBuffer(this.str, -1, 0, remaining(), remaining(), this.offset + position());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private StringCharBuffer(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  59 */     super(paramInt1, paramInt2, paramInt3, paramInt4, null, paramInt5);
/*  60 */     this.str = paramCharSequence;
/*     */   }
/*     */   
/*     */   public CharBuffer duplicate()
/*     */   {
/*  65 */     return new StringCharBuffer(this.str, markValue(), position(), limit(), capacity(), this.offset);
/*     */   }
/*     */   
/*     */   public CharBuffer asReadOnlyBuffer() {
/*  69 */     return duplicate();
/*     */   }
/*     */   
/*     */   public final char get() {
/*  73 */     return this.str.charAt(nextGetIndex() + this.offset);
/*     */   }
/*     */   
/*     */   public final char get(int paramInt) {
/*  77 */     return this.str.charAt(checkIndex(paramInt) + this.offset);
/*     */   }
/*     */   
/*     */   char getUnchecked(int paramInt) {
/*  81 */     return this.str.charAt(paramInt + this.offset);
/*     */   }
/*     */   
/*     */ 
/*     */   public final CharBuffer put(char paramChar)
/*     */   {
/*  87 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public final CharBuffer put(int paramInt, char paramChar) {
/*  91 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public final CharBuffer compact() {
/*  95 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public final boolean isReadOnly() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   final String toString(int paramInt1, int paramInt2) {
/* 103 */     return this.str.toString().substring(paramInt1 + this.offset, paramInt2 + this.offset);
/*     */   }
/*     */   
/*     */   public final CharBuffer subSequence(int paramInt1, int paramInt2) {
/*     */     try {
/* 108 */       int i = position();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 113 */       return new StringCharBuffer(this.str, -1, i + checkIndex(paramInt1, i), i + checkIndex(paramInt2, i), capacity(), this.offset);
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException) {
/* 116 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public ByteOrder order() {
/* 125 */     return ByteOrder.nativeOrder();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/StringCharBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */