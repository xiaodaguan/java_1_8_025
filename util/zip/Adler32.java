/*     */ package java.util.zip;
/*     */ 
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class Adler32
/*     */   implements Checksum
/*     */ {
/*  45 */   private int adler = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(int paramInt)
/*     */   {
/*  60 */     this.adler = update(this.adler, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  72 */     if (paramArrayOfByte == null) {
/*  73 */       throw new NullPointerException();
/*     */     }
/*  75 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2)) {
/*  76 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/*  78 */     this.adler = updateBytes(this.adler, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(byte[] paramArrayOfByte)
/*     */   {
/*  87 */     this.adler = updateBytes(this.adler, paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public void update(ByteBuffer paramByteBuffer)
/*     */   {
/* 105 */     int i = paramByteBuffer.position();
/* 106 */     int j = paramByteBuffer.limit();
/* 107 */     assert (i <= j);
/* 108 */     int k = j - i;
/* 109 */     if (k <= 0)
/* 110 */       return;
/* 111 */     if ((paramByteBuffer instanceof DirectBuffer)) {
/* 112 */       this.adler = updateByteBuffer(this.adler, ((DirectBuffer)paramByteBuffer).address(), i, k);
/* 113 */     } else if (paramByteBuffer.hasArray()) {
/* 114 */       this.adler = updateBytes(this.adler, paramByteBuffer.array(), i + paramByteBuffer.arrayOffset(), k);
/*     */     } else {
/* 116 */       byte[] arrayOfByte = new byte[k];
/* 117 */       paramByteBuffer.get(arrayOfByte);
/* 118 */       this.adler = updateBytes(this.adler, arrayOfByte, 0, arrayOfByte.length);
/*     */     }
/* 120 */     paramByteBuffer.position(j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 127 */     this.adler = 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getValue()
/*     */   {
/* 134 */     return this.adler & 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   private static native int update(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native int updateBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int updateByteBuffer(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/Adler32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */