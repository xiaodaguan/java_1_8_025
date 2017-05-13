/*     */ package java.util;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Base64
/*     */ {
/*     */   public static Encoder getEncoder()
/*     */   {
/*  88 */     return Encoder.RFC4648;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Encoder getUrlEncoder()
/*     */   {
/*  99 */     return Encoder.RFC4648_URLSAFE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Encoder getMimeEncoder()
/*     */   {
/* 109 */     return Encoder.RFC2045;
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
/*     */   public static Encoder getMimeEncoder(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 131 */     Objects.requireNonNull(paramArrayOfByte);
/* 132 */     int[] arrayOfInt = Decoder.fromBase64;
/* 133 */     for (int k : paramArrayOfByte) {
/* 134 */       if (arrayOfInt[(k & 0xFF)] != -1)
/*     */       {
/* 136 */         throw new IllegalArgumentException("Illegal base64 line separator character 0x" + Integer.toString(k, 16)); }
/*     */     }
/* 138 */     if (paramInt <= 0) {
/* 139 */       return Encoder.RFC4648;
/*     */     }
/* 141 */     return new Encoder(false, paramArrayOfByte, paramInt >> 2 << 2, true, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Decoder getDecoder()
/*     */   {
/* 151 */     return Decoder.RFC4648;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Decoder getUrlDecoder()
/*     */   {
/* 162 */     return Decoder.RFC4648_URLSAFE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Decoder getMimeDecoder()
/*     */   {
/* 172 */     return Decoder.RFC2045;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Encoder
/*     */   {
/*     */     private final byte[] newline;
/*     */     
/*     */ 
/*     */ 
/*     */     private final int linemax;
/*     */     
/*     */ 
/*     */ 
/*     */     private final boolean isURL;
/*     */     
/*     */ 
/*     */ 
/*     */     private final boolean doPadding;
/*     */     
/*     */ 
/*     */ 
/*     */     private Encoder(boolean paramBoolean1, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean2)
/*     */     {
/* 198 */       this.isURL = paramBoolean1;
/* 199 */       this.newline = paramArrayOfByte;
/* 200 */       this.linemax = paramInt;
/* 201 */       this.doPadding = paramBoolean2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */     private static final char[] toBase64 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 222 */     private static final char[] toBase64URL = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private static final int MIMELINEMAX = 76;
/*     */     
/*     */ 
/*     */ 
/* 231 */     private static final byte[] CRLF = { 13, 10 };
/*     */     
/* 233 */     static final Encoder RFC4648 = new Encoder(false, null, -1, true);
/* 234 */     static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);
/* 235 */     static final Encoder RFC2045 = new Encoder(false, CRLF, 76, true);
/*     */     
/*     */     private final int outLength(int paramInt) {
/* 238 */       int i = 0;
/* 239 */       if (this.doPadding) {
/* 240 */         i = 4 * ((paramInt + 2) / 3);
/*     */       } else {
/* 242 */         int j = paramInt % 3;
/* 243 */         i = 4 * (paramInt / 3) + (j == 0 ? 0 : j + 1);
/*     */       }
/* 245 */       if (this.linemax > 0)
/* 246 */         i += (i - 1) / this.linemax * this.newline.length;
/* 247 */       return i;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public byte[] encode(byte[] paramArrayOfByte)
/*     */     {
/* 261 */       int i = outLength(paramArrayOfByte.length);
/* 262 */       byte[] arrayOfByte = new byte[i];
/* 263 */       int j = encode0(paramArrayOfByte, 0, paramArrayOfByte.length, arrayOfByte);
/* 264 */       if (j != arrayOfByte.length)
/* 265 */         return Arrays.copyOf(arrayOfByte, j);
/* 266 */       return arrayOfByte;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int encode(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */     {
/* 289 */       int i = outLength(paramArrayOfByte1.length);
/* 290 */       if (paramArrayOfByte2.length < i) {
/* 291 */         throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
/*     */       }
/* 293 */       return encode0(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String encodeToString(byte[] paramArrayOfByte)
/*     */     {
/* 315 */       byte[] arrayOfByte = encode(paramArrayOfByte);
/* 316 */       return new String(arrayOfByte, 0, 0, arrayOfByte.length);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public ByteBuffer encode(ByteBuffer paramByteBuffer)
/*     */     {
/* 334 */       int i = outLength(paramByteBuffer.remaining());
/* 335 */       byte[] arrayOfByte1 = new byte[i];
/* 336 */       int j = 0;
/* 337 */       if (paramByteBuffer.hasArray()) {
/* 338 */         j = encode0(paramByteBuffer.array(), paramByteBuffer
/* 339 */           .arrayOffset() + paramByteBuffer.position(), paramByteBuffer
/* 340 */           .arrayOffset() + paramByteBuffer.limit(), arrayOfByte1);
/*     */         
/* 342 */         paramByteBuffer.position(paramByteBuffer.limit());
/*     */       } else {
/* 344 */         byte[] arrayOfByte2 = new byte[paramByteBuffer.remaining()];
/* 345 */         paramByteBuffer.get(arrayOfByte2);
/* 346 */         j = encode0(arrayOfByte2, 0, arrayOfByte2.length, arrayOfByte1);
/*     */       }
/* 348 */       if (j != arrayOfByte1.length)
/* 349 */         arrayOfByte1 = Arrays.copyOf(arrayOfByte1, j);
/* 350 */       return ByteBuffer.wrap(arrayOfByte1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public OutputStream wrap(OutputStream paramOutputStream)
/*     */     {
/* 368 */       Objects.requireNonNull(paramOutputStream);
/* 369 */       return new Base64.EncOutputStream(paramOutputStream, this.isURL ? toBase64URL : toBase64, this.newline, this.linemax, this.doPadding);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Encoder withoutPadding()
/*     */     {
/* 386 */       if (!this.doPadding)
/* 387 */         return this;
/* 388 */       return new Encoder(this.isURL, this.newline, this.linemax, false);
/*     */     }
/*     */     
/*     */     private int encode0(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2) {
/* 392 */       char[] arrayOfChar = this.isURL ? toBase64URL : toBase64;
/* 393 */       int i = paramInt1;
/* 394 */       int j = (paramInt2 - paramInt1) / 3 * 3;
/* 395 */       int k = paramInt1 + j;
/* 396 */       if ((this.linemax > 0) && (j > this.linemax / 4 * 3))
/* 397 */         j = this.linemax / 4 * 3;
/* 398 */       int m = 0;
/* 399 */       int n; int i1; while (i < k) {
/* 400 */         n = Math.min(i + j, k);
/* 401 */         i1 = i; for (int i2 = m; i1 < n;) {
/* 402 */           i3 = (paramArrayOfByte1[(i1++)] & 0xFF) << 16 | (paramArrayOfByte1[(i1++)] & 0xFF) << 8 | paramArrayOfByte1[(i1++)] & 0xFF;
/*     */           
/*     */ 
/* 405 */           paramArrayOfByte2[(i2++)] = ((byte)arrayOfChar[(i3 >>> 18 & 0x3F)]);
/* 406 */           paramArrayOfByte2[(i2++)] = ((byte)arrayOfChar[(i3 >>> 12 & 0x3F)]);
/* 407 */           paramArrayOfByte2[(i2++)] = ((byte)arrayOfChar[(i3 >>> 6 & 0x3F)]);
/* 408 */           paramArrayOfByte2[(i2++)] = ((byte)arrayOfChar[(i3 & 0x3F)]); }
/*     */         int i3;
/* 410 */         i1 = (n - i) / 3 * 4;
/* 411 */         m += i1;
/* 412 */         i = n;
/* 413 */         if ((i1 == this.linemax) && (i < paramInt2)) {
/* 414 */           for (int i5 : this.newline) {
/* 415 */             paramArrayOfByte2[(m++)] = i5;
/*     */           }
/*     */         }
/*     */       }
/* 419 */       if (i < paramInt2) {
/* 420 */         n = paramArrayOfByte1[(i++)] & 0xFF;
/* 421 */         paramArrayOfByte2[(m++)] = ((byte)arrayOfChar[(n >> 2)]);
/* 422 */         if (i == paramInt2) {
/* 423 */           paramArrayOfByte2[(m++)] = ((byte)arrayOfChar[(n << 4 & 0x3F)]);
/* 424 */           if (this.doPadding) {
/* 425 */             paramArrayOfByte2[(m++)] = 61;
/* 426 */             paramArrayOfByte2[(m++)] = 61;
/*     */           }
/*     */         } else {
/* 429 */           i1 = paramArrayOfByte1[(i++)] & 0xFF;
/* 430 */           paramArrayOfByte2[(m++)] = ((byte)arrayOfChar[(n << 4 & 0x3F | i1 >> 4)]);
/* 431 */           paramArrayOfByte2[(m++)] = ((byte)arrayOfChar[(i1 << 2 & 0x3F)]);
/* 432 */           if (this.doPadding) {
/* 433 */             paramArrayOfByte2[(m++)] = 61;
/*     */           }
/*     */         }
/*     */       }
/* 437 */       return m;
/*     */     }
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
/*     */   public static class Decoder
/*     */   {
/*     */     private final boolean isURL;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final boolean isMIME;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Decoder(boolean paramBoolean1, boolean paramBoolean2)
/*     */     {
/* 473 */       this.isURL = paramBoolean1;
/* 474 */       this.isMIME = paramBoolean2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 485 */     private static final int[] fromBase64 = new int['Ā'];
/*     */     
/* 487 */     static { Arrays.fill(fromBase64, -1);
/* 488 */       for (int i = 0; i < Base64.Encoder.access$200().length; i++)
/* 489 */         fromBase64[Base64.Encoder.access$200()[i]] = i;
/* 490 */       fromBase64[61] = -2;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 497 */       fromBase64URL = new int['Ā'];
/*     */       
/*     */ 
/* 500 */       Arrays.fill(fromBase64URL, -1);
/* 501 */       for (i = 0; i < Base64.Encoder.access$300().length; i++)
/* 502 */         fromBase64URL[Base64.Encoder.access$300()[i]] = i;
/* 503 */       fromBase64URL[61] = -2; }
/*     */     
/*     */     private static final int[] fromBase64URL;
/* 506 */     static final Decoder RFC4648 = new Decoder(false, false);
/* 507 */     static final Decoder RFC4648_URLSAFE = new Decoder(true, false);
/* 508 */     static final Decoder RFC2045 = new Decoder(false, true);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public byte[] decode(byte[] paramArrayOfByte)
/*     */     {
/* 525 */       byte[] arrayOfByte = new byte[outLength(paramArrayOfByte, 0, paramArrayOfByte.length)];
/* 526 */       int i = decode0(paramArrayOfByte, 0, paramArrayOfByte.length, arrayOfByte);
/* 527 */       if (i != arrayOfByte.length) {
/* 528 */         arrayOfByte = Arrays.copyOf(arrayOfByte, i);
/*     */       }
/* 530 */       return arrayOfByte;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public byte[] decode(String paramString)
/*     */     {
/* 549 */       return decode(paramString.getBytes(StandardCharsets.ISO_8859_1));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int decode(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */     {
/* 578 */       int i = outLength(paramArrayOfByte1, 0, paramArrayOfByte1.length);
/* 579 */       if (paramArrayOfByte2.length < i) {
/* 580 */         throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
/*     */       }
/* 582 */       return decode0(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public ByteBuffer decode(ByteBuffer paramByteBuffer)
/*     */     {
/* 607 */       int i = paramByteBuffer.position();
/*     */       try { byte[] arrayOfByte1;
/*     */         int j;
/*     */         int k;
/* 611 */         if (paramByteBuffer.hasArray()) {
/* 612 */           arrayOfByte1 = paramByteBuffer.array();
/* 613 */           j = paramByteBuffer.arrayOffset() + paramByteBuffer.position();
/* 614 */           k = paramByteBuffer.arrayOffset() + paramByteBuffer.limit();
/* 615 */           paramByteBuffer.position(paramByteBuffer.limit());
/*     */         } else {
/* 617 */           arrayOfByte1 = new byte[paramByteBuffer.remaining()];
/* 618 */           paramByteBuffer.get(arrayOfByte1);
/* 619 */           j = 0;
/* 620 */           k = arrayOfByte1.length;
/*     */         }
/* 622 */         byte[] arrayOfByte2 = new byte[outLength(arrayOfByte1, j, k)];
/* 623 */         return ByteBuffer.wrap(arrayOfByte2, 0, decode0(arrayOfByte1, j, k, arrayOfByte2));
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 625 */         paramByteBuffer.position(i);
/* 626 */         throw localIllegalArgumentException;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public InputStream wrap(InputStream paramInputStream)
/*     */     {
/* 646 */       Objects.requireNonNull(paramInputStream);
/* 647 */       return new Base64.DecInputStream(paramInputStream, this.isURL ? fromBase64URL : fromBase64, this.isMIME);
/*     */     }
/*     */     
/*     */     private int outLength(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 651 */       int[] arrayOfInt = this.isURL ? fromBase64URL : fromBase64;
/* 652 */       int i = 0;
/* 653 */       int j = paramInt2 - paramInt1;
/* 654 */       if (j == 0)
/* 655 */         return 0;
/* 656 */       if (j < 2) {
/* 657 */         if ((this.isMIME) && (arrayOfInt[0] == -1))
/* 658 */           return 0;
/* 659 */         throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
/*     */       }
/*     */       
/* 662 */       if (this.isMIME)
/*     */       {
/*     */ 
/* 665 */         int k = 0;
/* 666 */         while (paramInt1 < paramInt2) {
/* 667 */           int m = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 668 */           if (m == 61) {
/* 669 */             j -= paramInt2 - paramInt1 + 1;
/* 670 */             break;
/*     */           }
/* 672 */           if ((m = arrayOfInt[m]) == -1)
/* 673 */             k++;
/*     */         }
/* 675 */         j -= k;
/*     */       }
/* 677 */       else if (paramArrayOfByte[(paramInt2 - 1)] == 61) {
/* 678 */         i++;
/* 679 */         if (paramArrayOfByte[(paramInt2 - 2)] == 61) {
/* 680 */           i++;
/*     */         }
/*     */       }
/* 683 */       if ((i == 0) && ((j & 0x3) != 0))
/* 684 */         i = 4 - (j & 0x3);
/* 685 */       return 3 * ((j + 3) / 4) - i;
/*     */     }
/*     */     
/*     */     private int decode0(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2) {
/* 689 */       int[] arrayOfInt = this.isURL ? fromBase64URL : fromBase64;
/* 690 */       int i = 0;
/* 691 */       int j = 0;
/* 692 */       int k = 18;
/* 693 */       while (paramInt1 < paramInt2) {
/* 694 */         int m = paramArrayOfByte1[(paramInt1++)] & 0xFF;
/* 695 */         if ((m = arrayOfInt[m]) < 0) {
/* 696 */           if (m == -2)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 702 */             if (((k != 6) || ((paramInt1 != paramInt2) && (paramArrayOfByte1[(paramInt1++)] == 61))) && (k != 18))
/*     */               break;
/* 704 */             throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 709 */           if (!this.isMIME)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 714 */             throw new IllegalArgumentException("Illegal base64 character " + Integer.toString(paramArrayOfByte1[(paramInt1 - 1)], 16)); }
/*     */         } else {
/* 716 */           j |= m << k;
/* 717 */           k -= 6;
/* 718 */           if (k < 0) {
/* 719 */             paramArrayOfByte2[(i++)] = ((byte)(j >> 16));
/* 720 */             paramArrayOfByte2[(i++)] = ((byte)(j >> 8));
/* 721 */             paramArrayOfByte2[(i++)] = ((byte)j);
/* 722 */             k = 18;
/* 723 */             j = 0;
/*     */           }
/*     */         }
/*     */       }
/* 727 */       if (k == 6) {
/* 728 */         paramArrayOfByte2[(i++)] = ((byte)(j >> 16));
/* 729 */       } else if (k == 0) {
/* 730 */         paramArrayOfByte2[(i++)] = ((byte)(j >> 16));
/* 731 */         paramArrayOfByte2[(i++)] = ((byte)(j >> 8));
/* 732 */       } else if (k == 12)
/*     */       {
/* 734 */         throw new IllegalArgumentException("Last unit does not have enough valid bits");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 739 */       while (paramInt1 < paramInt2) {
/* 740 */         if ((!this.isMIME) || (arrayOfInt[paramArrayOfByte1[(paramInt1++)]] >= 0))
/*     */         {
/* 742 */           throw new IllegalArgumentException("Input byte array has incorrect ending byte at " + paramInt1);
/*     */         }
/*     */       }
/* 745 */       return i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class EncOutputStream
/*     */     extends FilterOutputStream
/*     */   {
/* 754 */     private int leftover = 0;
/*     */     private int b0;
/* 756 */     private int b1; private int b2; private boolean closed = false;
/*     */     
/*     */     private final char[] base64;
/*     */     private final byte[] newline;
/*     */     private final int linemax;
/*     */     private final boolean doPadding;
/* 762 */     private int linepos = 0;
/*     */     
/*     */     EncOutputStream(OutputStream paramOutputStream, char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
/*     */     {
/* 766 */       super();
/* 767 */       this.base64 = paramArrayOfChar;
/* 768 */       this.newline = paramArrayOfByte;
/* 769 */       this.linemax = paramInt;
/* 770 */       this.doPadding = paramBoolean;
/*     */     }
/*     */     
/*     */     public void write(int paramInt) throws IOException
/*     */     {
/* 775 */       byte[] arrayOfByte = new byte[1];
/* 776 */       arrayOfByte[0] = ((byte)(paramInt & 0xFF));
/* 777 */       write(arrayOfByte, 0, 1);
/*     */     }
/*     */     
/*     */     private void checkNewline() throws IOException {
/* 781 */       if (this.linepos == this.linemax) {
/* 782 */         this.out.write(this.newline);
/* 783 */         this.linepos = 0;
/*     */       }
/*     */     }
/*     */     
/*     */     public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */     {
/* 789 */       if (this.closed)
/* 790 */         throw new IOException("Stream is closed");
/* 791 */       if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length))
/* 792 */         throw new ArrayIndexOutOfBoundsException();
/* 793 */       if (paramInt2 == 0)
/* 794 */         return;
/* 795 */       if (this.leftover != 0) {
/* 796 */         if (this.leftover == 1) {
/* 797 */           this.b1 = (paramArrayOfByte[(paramInt1++)] & 0xFF);
/* 798 */           paramInt2--;
/* 799 */           if (paramInt2 == 0) {
/* 800 */             this.leftover += 1;
/* 801 */             return;
/*     */           }
/*     */         }
/* 804 */         this.b2 = (paramArrayOfByte[(paramInt1++)] & 0xFF);
/* 805 */         paramInt2--;
/* 806 */         checkNewline();
/* 807 */         this.out.write(this.base64[(this.b0 >> 2)]);
/* 808 */         this.out.write(this.base64[(this.b0 << 4 & 0x3F | this.b1 >> 4)]);
/* 809 */         this.out.write(this.base64[(this.b1 << 2 & 0x3F | this.b2 >> 6)]);
/* 810 */         this.out.write(this.base64[(this.b2 & 0x3F)]);
/* 811 */         this.linepos += 4;
/*     */       }
/* 813 */       int i = paramInt2 / 3;
/* 814 */       this.leftover = (paramInt2 - i * 3);
/* 815 */       while (i-- > 0) {
/* 816 */         checkNewline();
/* 817 */         int j = (paramArrayOfByte[(paramInt1++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1++)] & 0xFF) << 8 | paramArrayOfByte[(paramInt1++)] & 0xFF;
/*     */         
/*     */ 
/* 820 */         this.out.write(this.base64[(j >>> 18 & 0x3F)]);
/* 821 */         this.out.write(this.base64[(j >>> 12 & 0x3F)]);
/* 822 */         this.out.write(this.base64[(j >>> 6 & 0x3F)]);
/* 823 */         this.out.write(this.base64[(j & 0x3F)]);
/* 824 */         this.linepos += 4;
/*     */       }
/* 826 */       if (this.leftover == 1) {
/* 827 */         this.b0 = (paramArrayOfByte[(paramInt1++)] & 0xFF);
/* 828 */       } else if (this.leftover == 2) {
/* 829 */         this.b0 = (paramArrayOfByte[(paramInt1++)] & 0xFF);
/* 830 */         this.b1 = (paramArrayOfByte[(paramInt1++)] & 0xFF);
/*     */       }
/*     */     }
/*     */     
/*     */     public void close() throws IOException
/*     */     {
/* 836 */       if (!this.closed) {
/* 837 */         this.closed = true;
/* 838 */         if (this.leftover == 1) {
/* 839 */           checkNewline();
/* 840 */           this.out.write(this.base64[(this.b0 >> 2)]);
/* 841 */           this.out.write(this.base64[(this.b0 << 4 & 0x3F)]);
/* 842 */           if (this.doPadding) {
/* 843 */             this.out.write(61);
/* 844 */             this.out.write(61);
/*     */           }
/* 846 */         } else if (this.leftover == 2) {
/* 847 */           checkNewline();
/* 848 */           this.out.write(this.base64[(this.b0 >> 2)]);
/* 849 */           this.out.write(this.base64[(this.b0 << 4 & 0x3F | this.b1 >> 4)]);
/* 850 */           this.out.write(this.base64[(this.b1 << 2 & 0x3F)]);
/* 851 */           if (this.doPadding) {
/* 852 */             this.out.write(61);
/*     */           }
/*     */         }
/* 855 */         this.leftover = 0;
/* 856 */         this.out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class DecInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final InputStream is;
/*     */     
/*     */     private final boolean isMIME;
/*     */     private final int[] base64;
/* 869 */     private int bits = 0;
/* 870 */     private int nextin = 18;
/*     */     
/* 872 */     private int nextout = -8;
/*     */     
/* 874 */     private boolean eof = false;
/* 875 */     private boolean closed = false;
/*     */     
/*     */     DecInputStream(InputStream paramInputStream, int[] paramArrayOfInt, boolean paramBoolean) {
/* 878 */       this.is = paramInputStream;
/* 879 */       this.base64 = paramArrayOfInt;
/* 880 */       this.isMIME = paramBoolean;
/*     */     }
/*     */     
/* 883 */     private byte[] sbBuf = new byte[1];
/*     */     
/*     */     public int read() throws IOException
/*     */     {
/* 887 */       return read(this.sbBuf, 0, 1) == -1 ? -1 : this.sbBuf[0] & 0xFF;
/*     */     }
/*     */     
/*     */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */     {
/* 892 */       if (this.closed)
/* 893 */         throw new IOException("Stream is closed");
/* 894 */       if ((this.eof) && (this.nextout < 0))
/* 895 */         return -1;
/* 896 */       if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1))
/* 897 */         throw new IndexOutOfBoundsException();
/* 898 */       int i = paramInt1;
/* 899 */       if (this.nextout >= 0) {
/*     */         do {
/* 901 */           if (paramInt2 == 0)
/* 902 */             return paramInt1 - i;
/* 903 */           paramArrayOfByte[(paramInt1++)] = ((byte)(this.bits >> this.nextout));
/* 904 */           paramInt2--;
/* 905 */           this.nextout -= 8;
/* 906 */         } while (this.nextout >= 0);
/* 907 */         this.bits = 0;
/*     */       }
/* 909 */       while (paramInt2 > 0) {
/* 910 */         int j = this.is.read();
/* 911 */         if (j == -1) {
/* 912 */           this.eof = true;
/* 913 */           if (this.nextin != 18) {
/* 914 */             if (this.nextin == 12) {
/* 915 */               throw new IOException("Base64 stream has one un-decoded dangling byte.");
/*     */             }
/*     */             
/* 918 */             paramArrayOfByte[(paramInt1++)] = ((byte)(this.bits >> 16));
/* 919 */             paramInt2--;
/* 920 */             if (this.nextin == 0) {
/* 921 */               if (paramInt2 == 0) {
/* 922 */                 this.bits >>= 8;
/* 923 */                 this.nextout = 0;
/*     */               } else {
/* 925 */                 paramArrayOfByte[(paramInt1++)] = ((byte)(this.bits >> 8));
/*     */               }
/*     */             }
/*     */           }
/* 929 */           if (paramInt1 == i) {
/* 930 */             return -1;
/*     */           }
/* 932 */           return paramInt1 - i;
/*     */         }
/* 934 */         if (j == 61)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 939 */           if ((this.nextin == 18) || (this.nextin == 12) || ((this.nextin == 6) && 
/* 940 */             (this.is.read() != 61))) {
/* 941 */             throw new IOException("Illegal base64 ending sequence:" + this.nextin);
/*     */           }
/* 943 */           paramArrayOfByte[(paramInt1++)] = ((byte)(this.bits >> 16));
/* 944 */           paramInt2--;
/* 945 */           if (this.nextin == 0) {
/* 946 */             if (paramInt2 == 0) {
/* 947 */               this.bits >>= 8;
/* 948 */               this.nextout = 0;
/*     */             } else {
/* 950 */               paramArrayOfByte[(paramInt1++)] = ((byte)(this.bits >> 8));
/*     */             }
/*     */           }
/* 953 */           this.eof = true;
/* 954 */           break;
/*     */         }
/* 956 */         if ((j = this.base64[j]) == -1) {
/* 957 */           if (!this.isMIME)
/*     */           {
/*     */ 
/*     */ 
/* 961 */             throw new IOException("Illegal base64 character " + Integer.toString(j, 16)); }
/*     */         } else {
/* 963 */           this.bits |= j << this.nextin;
/* 964 */           if (this.nextin == 0) {
/* 965 */             this.nextin = 18;
/* 966 */             this.nextout = 16;
/* 967 */             while (this.nextout >= 0) {
/* 968 */               paramArrayOfByte[(paramInt1++)] = ((byte)(this.bits >> this.nextout));
/* 969 */               paramInt2--;
/* 970 */               this.nextout -= 8;
/* 971 */               if ((paramInt2 == 0) && (this.nextout >= 0)) {
/* 972 */                 return paramInt1 - i;
/*     */               }
/*     */             }
/* 975 */             this.bits = 0;
/*     */           } else {
/* 977 */             this.nextin -= 6;
/*     */           }
/*     */         } }
/* 980 */       return paramInt1 - i;
/*     */     }
/*     */     
/*     */     public int available() throws IOException
/*     */     {
/* 985 */       if (this.closed)
/* 986 */         throw new IOException("Stream is closed");
/* 987 */       return this.is.available();
/*     */     }
/*     */     
/*     */     public void close() throws IOException
/*     */     {
/* 992 */       if (!this.closed) {
/* 993 */         this.closed = true;
/* 994 */         this.is.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */