/*     */ package java.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UUID
/*     */   implements Serializable, Comparable<UUID>
/*     */ {
/*     */   private static final long serialVersionUID = -4856846361193249489L;
/*     */   private final long mostSigBits;
/*     */   private final long leastSigBits;
/*     */   
/*     */   private static class Holder
/*     */   {
/*  96 */     static final SecureRandom numberGenerator = new SecureRandom();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private UUID(byte[] paramArrayOfByte)
/*     */   {
/* 105 */     long l1 = 0L;
/* 106 */     long l2 = 0L;
/* 107 */     assert (paramArrayOfByte.length == 16) : "data must be 16 bytes in length";
/* 108 */     for (int i = 0; i < 8; i++)
/* 109 */       l1 = l1 << 8 | paramArrayOfByte[i] & 0xFF;
/* 110 */     for (i = 8; i < 16; i++)
/* 111 */       l2 = l2 << 8 | paramArrayOfByte[i] & 0xFF;
/* 112 */     this.mostSigBits = l1;
/* 113 */     this.leastSigBits = l2;
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
/*     */   public UUID(long paramLong1, long paramLong2)
/*     */   {
/* 129 */     this.mostSigBits = paramLong1;
/* 130 */     this.leastSigBits = paramLong2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UUID randomUUID()
/*     */   {
/* 142 */     SecureRandom localSecureRandom = Holder.numberGenerator;
/*     */     
/* 144 */     byte[] arrayOfByte = new byte[16];
/* 145 */     localSecureRandom.nextBytes(arrayOfByte); byte[] 
/* 146 */       tmp17_14 = arrayOfByte;tmp17_14[6] = ((byte)(tmp17_14[6] & 0xF)); byte[] 
/* 147 */       tmp27_24 = arrayOfByte;tmp27_24[6] = ((byte)(tmp27_24[6] | 0x40)); byte[] 
/* 148 */       tmp37_34 = arrayOfByte;tmp37_34[8] = ((byte)(tmp37_34[8] & 0x3F)); byte[] 
/* 149 */       tmp47_44 = arrayOfByte;tmp47_44[8] = ((byte)(tmp47_44[8] | 0x80));
/* 150 */     return new UUID(arrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UUID nameUUIDFromBytes(byte[] paramArrayOfByte)
/*     */   {
/*     */     MessageDigest localMessageDigest;
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 165 */       localMessageDigest = MessageDigest.getInstance("MD5");
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 167 */       throw new InternalError("MD5 not supported", localNoSuchAlgorithmException);
/*     */     }
/* 169 */     byte[] arrayOfByte = localMessageDigest.digest(paramArrayOfByte); byte[] 
/* 170 */       tmp30_27 = arrayOfByte;tmp30_27[6] = ((byte)(tmp30_27[6] & 0xF)); byte[] 
/* 171 */       tmp40_37 = arrayOfByte;tmp40_37[6] = ((byte)(tmp40_37[6] | 0x30)); byte[] 
/* 172 */       tmp50_47 = arrayOfByte;tmp50_47[8] = ((byte)(tmp50_47[8] & 0x3F)); byte[] 
/* 173 */       tmp60_57 = arrayOfByte;tmp60_57[8] = ((byte)(tmp60_57[8] | 0x80));
/* 174 */     return new UUID(arrayOfByte);
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
/*     */   public static UUID fromString(String paramString)
/*     */   {
/* 192 */     String[] arrayOfString = paramString.split("-");
/* 193 */     if (arrayOfString.length != 5)
/* 194 */       throw new IllegalArgumentException("Invalid UUID string: " + paramString);
/* 195 */     for (int i = 0; i < 5; i++) {
/* 196 */       arrayOfString[i] = ("0x" + arrayOfString[i]);
/*     */     }
/* 198 */     long l1 = Long.decode(arrayOfString[0]).longValue();
/* 199 */     l1 <<= 16;
/* 200 */     l1 |= Long.decode(arrayOfString[1]).longValue();
/* 201 */     l1 <<= 16;
/* 202 */     l1 |= Long.decode(arrayOfString[2]).longValue();
/*     */     
/* 204 */     long l2 = Long.decode(arrayOfString[3]).longValue();
/* 205 */     l2 <<= 48;
/* 206 */     l2 |= Long.decode(arrayOfString[4]).longValue();
/*     */     
/* 208 */     return new UUID(l1, l2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLeastSignificantBits()
/*     */   {
/* 219 */     return this.leastSigBits;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getMostSignificantBits()
/*     */   {
/* 228 */     return this.mostSigBits;
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
/*     */   public int version()
/*     */   {
/* 247 */     return (int)(this.mostSigBits >> 12 & 0xF);
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
/*     */   public int variant()
/*     */   {
/* 271 */     return (int)(this.leastSigBits >>> (int)(64L - (this.leastSigBits >>> 62)) & this.leastSigBits >> 63);
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
/*     */   public long timestamp()
/*     */   {
/* 292 */     if (version() != 1) {
/* 293 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/*     */     
/* 296 */     return (this.mostSigBits & 0xFFF) << 48 | (this.mostSigBits >> 16 & 0xFFFF) << 32 | this.mostSigBits >>> 32;
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
/*     */   public int clockSequence()
/*     */   {
/* 318 */     if (version() != 1) {
/* 319 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/*     */     
/* 322 */     return (int)((this.leastSigBits & 0x3FFF000000000000) >>> 48);
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
/*     */   public long node()
/*     */   {
/* 342 */     if (version() != 1) {
/* 343 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/*     */     
/* 346 */     return this.leastSigBits & 0xFFFFFFFFFFFF;
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
/*     */   public String toString()
/*     */   {
/* 380 */     return digits(this.mostSigBits >> 32, 8) + "-" + digits(this.mostSigBits >> 16, 4) + "-" + digits(this.mostSigBits, 4) + "-" + digits(this.leastSigBits >> 48, 4) + "-" + digits(this.leastSigBits, 12);
/*     */   }
/*     */   
/*     */   private static String digits(long paramLong, int paramInt)
/*     */   {
/* 385 */     long l = 1L << paramInt * 4;
/* 386 */     return Long.toHexString(l | paramLong & l - 1L).substring(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 395 */     long l = this.mostSigBits ^ this.leastSigBits;
/* 396 */     return (int)(l >> 32) ^ (int)l;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 412 */     if ((null == paramObject) || (paramObject.getClass() != UUID.class))
/* 413 */       return false;
/* 414 */     UUID localUUID = (UUID)paramObject;
/* 415 */     return (this.mostSigBits == localUUID.mostSigBits) && (this.leastSigBits == localUUID.leastSigBits);
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
/*     */   public int compareTo(UUID paramUUID)
/*     */   {
/* 438 */     return this.leastSigBits > paramUUID.leastSigBits ? 1 : this.leastSigBits < paramUUID.leastSigBits ? -1 : this.mostSigBits > paramUUID.mostSigBits ? 1 : this.mostSigBits < paramUUID.mostSigBits ? -1 : 0;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/UUID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */