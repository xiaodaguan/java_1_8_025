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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CRC32
/*     */   implements Checksum
/*     */ {
/*     */   private int crc;
/*     */   
/*     */   public void update(int paramInt)
/*     */   {
/*  58 */     this.crc = update(this.crc, paramInt);
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
/*  70 */     if (paramArrayOfByte == null) {
/*  71 */       throw new NullPointerException();
/*     */     }
/*  73 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2)) {
/*  74 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/*  76 */     this.crc = updateBytes(this.crc, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(byte[] paramArrayOfByte)
/*     */   {
/*  85 */     this.crc = updateBytes(this.crc, paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public void update(ByteBuffer paramByteBuffer)
/*     */   {
/* 102 */     int i = paramByteBuffer.position();
/* 103 */     int j = paramByteBuffer.limit();
/* 104 */     assert (i <= j);
/* 105 */     int k = j - i;
/* 106 */     if (k <= 0)
/* 107 */       return;
/* 108 */     if ((paramByteBuffer instanceof DirectBuffer)) {
/* 109 */       this.crc = updateByteBuffer(this.crc, ((DirectBuffer)paramByteBuffer).address(), i, k);
/* 110 */     } else if (paramByteBuffer.hasArray()) {
/* 111 */       this.crc = updateBytes(this.crc, paramByteBuffer.array(), i + paramByteBuffer.arrayOffset(), k);
/*     */     } else {
/* 113 */       byte[] arrayOfByte = new byte[k];
/* 114 */       paramByteBuffer.get(arrayOfByte);
/* 115 */       this.crc = updateBytes(this.crc, arrayOfByte, 0, arrayOfByte.length);
/*     */     }
/* 117 */     paramByteBuffer.position(j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 124 */     this.crc = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getValue()
/*     */   {
/* 131 */     return this.crc & 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   private static native int update(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native int updateBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int updateByteBuffer(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/CRC32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */