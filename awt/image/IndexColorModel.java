/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Arrays;
/*      */ import sun.awt.image.BufImgSurfaceData.ICMColorData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IndexColorModel
/*      */   extends ColorModel
/*      */ {
/*      */   private int[] rgb;
/*      */   private int map_size;
/*      */   private int pixel_mask;
/*  129 */   private int transparent_index = -1;
/*      */   
/*      */   private boolean allgrayopaque;
/*      */   private BigInteger validBits;
/*  133 */   private BufImgSurfaceData.ICMColorData colorData = null;
/*      */   
/*  135 */   private static int[] opaqueBits = { 8, 8, 8 };
/*  136 */   private static int[] alphaBits = { 8, 8, 8, 8 };
/*      */   private static final int CACHESIZE = 40;
/*      */   
/*      */   static {
/*  140 */     ColorModel.loadLibraries();
/*  141 */     initIDs();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/*      */   {
/*  168 */     super(paramInt1, opaqueBits, 
/*  169 */       ColorSpace.getInstance(1000), false, false, 1, 
/*      */       
/*  171 */       ColorModel.getDefaultTransferType(paramInt1));
/*  172 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  173 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */     
/*  176 */     setRGBs(paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, null);
/*  177 */     calculatePixelMask();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt3)
/*      */   {
/*  208 */     super(paramInt1, opaqueBits, 
/*  209 */       ColorSpace.getInstance(1000), false, false, 1, 
/*      */       
/*  211 */       ColorModel.getDefaultTransferType(paramInt1));
/*  212 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  213 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */     
/*  216 */     setRGBs(paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, null);
/*  217 */     setTransparentPixel(paramInt3);
/*  218 */     calculatePixelMask();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/*      */   {
/*  247 */     super(paramInt1, alphaBits, 
/*  248 */       ColorSpace.getInstance(1000), true, false, 3, 
/*      */       
/*  250 */       ColorModel.getDefaultTransferType(paramInt1));
/*  251 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  252 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */     
/*  255 */     setRGBs(paramInt2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramArrayOfByte4);
/*  256 */     calculatePixelMask();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, boolean paramBoolean)
/*      */   {
/*  287 */     this(paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramBoolean, -1);
/*  288 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  289 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, boolean paramBoolean, int paramInt4)
/*      */   {
/*  325 */     super(paramInt1, opaqueBits, 
/*  326 */       ColorSpace.getInstance(1000), false, false, 1, 
/*      */       
/*  328 */       ColorModel.getDefaultTransferType(paramInt1));
/*      */     
/*  330 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  331 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */     
/*  334 */     if (paramInt2 < 1) {
/*  335 */       throw new IllegalArgumentException("Map size (" + paramInt2 + ") must be >= 1");
/*      */     }
/*      */     
/*  338 */     this.map_size = paramInt2;
/*  339 */     this.rgb = new int[calcRealMapSize(paramInt1, paramInt2)];
/*  340 */     int i = paramInt3;
/*  341 */     int j = 255;
/*  342 */     boolean bool = true;
/*  343 */     int k = 1;
/*  344 */     for (int m = 0; m < paramInt2; m++) {
/*  345 */       int n = paramArrayOfByte[(i++)] & 0xFF;
/*  346 */       int i1 = paramArrayOfByte[(i++)] & 0xFF;
/*  347 */       int i2 = paramArrayOfByte[(i++)] & 0xFF;
/*  348 */       bool = (bool) && (n == i1) && (i1 == i2);
/*  349 */       if (paramBoolean) {
/*  350 */         j = paramArrayOfByte[(i++)] & 0xFF;
/*  351 */         if (j != 255) {
/*  352 */           if (j == 0) {
/*  353 */             if (k == 1) {
/*  354 */               k = 2;
/*      */             }
/*  356 */             if (this.transparent_index < 0) {
/*  357 */               this.transparent_index = m;
/*      */             }
/*      */           } else {
/*  360 */             k = 3;
/*      */           }
/*  362 */           bool = false;
/*      */         }
/*      */       }
/*  365 */       this.rgb[m] = (j << 24 | n << 16 | i1 << 8 | i2);
/*      */     }
/*  367 */     this.allgrayopaque = bool;
/*  368 */     setTransparency(k);
/*  369 */     setTransparentPixel(paramInt4);
/*  370 */     calculatePixelMask();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5)
/*      */   {
/*  410 */     super(paramInt1, opaqueBits, 
/*  411 */       ColorSpace.getInstance(1000), false, false, 1, paramInt5);
/*      */     
/*      */ 
/*      */ 
/*  415 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  416 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */     
/*  419 */     if (paramInt2 < 1) {
/*  420 */       throw new IllegalArgumentException("Map size (" + paramInt2 + ") must be >= 1");
/*      */     }
/*      */     
/*  423 */     if ((paramInt5 != 0) && (paramInt5 != 1))
/*      */     {
/*  425 */       throw new IllegalArgumentException("transferType must be eitherDataBuffer.TYPE_BYTE or DataBuffer.TYPE_USHORT");
/*      */     }
/*      */     
/*      */ 
/*  429 */     setRGBs(paramInt2, paramArrayOfInt, paramInt3, paramBoolean);
/*  430 */     setTransparentPixel(paramInt4);
/*  431 */     calculatePixelMask();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IndexColorModel(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, BigInteger paramBigInteger)
/*      */   {
/*  475 */     super(paramInt1, alphaBits, 
/*  476 */       ColorSpace.getInstance(1000), true, false, 3, paramInt4);
/*      */     
/*      */ 
/*      */ 
/*  480 */     if ((paramInt1 < 1) || (paramInt1 > 16)) {
/*  481 */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");
/*      */     }
/*      */     
/*  484 */     if (paramInt2 < 1) {
/*  485 */       throw new IllegalArgumentException("Map size (" + paramInt2 + ") must be >= 1");
/*      */     }
/*      */     
/*  488 */     if ((paramInt4 != 0) && (paramInt4 != 1))
/*      */     {
/*  490 */       throw new IllegalArgumentException("transferType must be eitherDataBuffer.TYPE_BYTE or DataBuffer.TYPE_USHORT");
/*      */     }
/*      */     
/*      */ 
/*  494 */     if (paramBigInteger != null)
/*      */     {
/*  496 */       for (int i = 0; i < paramInt2; i++) {
/*  497 */         if (!paramBigInteger.testBit(i)) {
/*  498 */           this.validBits = paramBigInteger;
/*  499 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  504 */     setRGBs(paramInt2, paramArrayOfInt, paramInt3, true);
/*  505 */     calculatePixelMask();
/*      */   }
/*      */   
/*      */   private void setRGBs(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4) {
/*  509 */     if (paramInt < 1) {
/*  510 */       throw new IllegalArgumentException("Map size (" + paramInt + ") must be >= 1");
/*      */     }
/*      */     
/*  513 */     this.map_size = paramInt;
/*  514 */     this.rgb = new int[calcRealMapSize(this.pixel_bits, paramInt)];
/*  515 */     int i = 255;
/*  516 */     int j = 1;
/*  517 */     boolean bool = true;
/*  518 */     for (int k = 0; k < paramInt; k++) {
/*  519 */       int m = paramArrayOfByte1[k] & 0xFF;
/*  520 */       int n = paramArrayOfByte2[k] & 0xFF;
/*  521 */       int i1 = paramArrayOfByte3[k] & 0xFF;
/*  522 */       bool = (bool) && (m == n) && (n == i1);
/*  523 */       if (paramArrayOfByte4 != null) {
/*  524 */         i = paramArrayOfByte4[k] & 0xFF;
/*  525 */         if (i != 255) {
/*  526 */           if (i == 0) {
/*  527 */             if (j == 1) {
/*  528 */               j = 2;
/*      */             }
/*  530 */             if (this.transparent_index < 0) {
/*  531 */               this.transparent_index = k;
/*      */             }
/*      */           } else {
/*  534 */             j = 3;
/*      */           }
/*  536 */           bool = false;
/*      */         }
/*      */       }
/*  539 */       this.rgb[k] = (i << 24 | m << 16 | n << 8 | i1);
/*      */     }
/*  541 */     this.allgrayopaque = bool;
/*  542 */     setTransparency(j);
/*      */   }
/*      */   
/*      */   private void setRGBs(int paramInt1, int[] paramArrayOfInt, int paramInt2, boolean paramBoolean) {
/*  546 */     this.map_size = paramInt1;
/*  547 */     this.rgb = new int[calcRealMapSize(this.pixel_bits, paramInt1)];
/*  548 */     int i = paramInt2;
/*  549 */     int j = 1;
/*  550 */     boolean bool = true;
/*  551 */     BigInteger localBigInteger = this.validBits;
/*  552 */     for (int k = 0; k < paramInt1; i++) {
/*  553 */       if ((localBigInteger == null) || (localBigInteger.testBit(k)))
/*      */       {
/*      */ 
/*  556 */         int m = paramArrayOfInt[i];
/*  557 */         int n = m >> 16 & 0xFF;
/*  558 */         int i1 = m >> 8 & 0xFF;
/*  559 */         int i2 = m & 0xFF;
/*  560 */         bool = (bool) && (n == i1) && (i1 == i2);
/*  561 */         if (paramBoolean) {
/*  562 */           int i3 = m >>> 24;
/*  563 */           if (i3 != 255) {
/*  564 */             if (i3 == 0) {
/*  565 */               if (j == 1) {
/*  566 */                 j = 2;
/*      */               }
/*  568 */               if (this.transparent_index < 0) {
/*  569 */                 this.transparent_index = k;
/*      */               }
/*      */             } else {
/*  572 */               j = 3;
/*      */             }
/*  574 */             bool = false;
/*      */           }
/*      */         } else {
/*  577 */           m |= 0xFF000000;
/*      */         }
/*  579 */         this.rgb[k] = m;
/*      */       }
/*  552 */       k++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  581 */     this.allgrayopaque = bool;
/*  582 */     setTransparency(j);
/*      */   }
/*      */   
/*      */   private int calcRealMapSize(int paramInt1, int paramInt2) {
/*  586 */     int i = Math.max(1 << paramInt1, paramInt2);
/*  587 */     return Math.max(i, 256);
/*      */   }
/*      */   
/*      */   private BigInteger getAllValid() {
/*  591 */     int i = (this.map_size + 7) / 8;
/*  592 */     byte[] arrayOfByte = new byte[i];
/*  593 */     Arrays.fill(arrayOfByte, (byte)-1);
/*  594 */     arrayOfByte[0] = ((byte)(255 >>> i * 8 - this.map_size));
/*      */     
/*  596 */     return new BigInteger(1, arrayOfByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTransparency()
/*      */   {
/*  608 */     return this.transparency;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getComponentSize()
/*      */   {
/*  619 */     if (this.nBits == null) {
/*  620 */       if (this.supportsAlpha) {
/*  621 */         this.nBits = new int[4];
/*  622 */         this.nBits[3] = 8;
/*      */       }
/*      */       else {
/*  625 */         this.nBits = new int[3];
/*      */       }
/*  627 */       this.nBits[0] = (this.nBits[1] = this.nBits[2] = 8);
/*      */     }
/*  629 */     return (int[])this.nBits.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getMapSize()
/*      */   {
/*  638 */     return this.map_size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getTransparentPixel()
/*      */   {
/*  654 */     return this.transparent_index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void getReds(byte[] paramArrayOfByte)
/*      */   {
/*  665 */     for (int i = 0; i < this.map_size; i++) {
/*  666 */       paramArrayOfByte[i] = ((byte)(this.rgb[i] >> 16));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void getGreens(byte[] paramArrayOfByte)
/*      */   {
/*  678 */     for (int i = 0; i < this.map_size; i++) {
/*  679 */       paramArrayOfByte[i] = ((byte)(this.rgb[i] >> 8));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void getBlues(byte[] paramArrayOfByte)
/*      */   {
/*  691 */     for (int i = 0; i < this.map_size; i++) {
/*  692 */       paramArrayOfByte[i] = ((byte)this.rgb[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void getAlphas(byte[] paramArrayOfByte)
/*      */   {
/*  704 */     for (int i = 0; i < this.map_size; i++) {
/*  705 */       paramArrayOfByte[i] = ((byte)(this.rgb[i] >> 24));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void getRGBs(int[] paramArrayOfInt)
/*      */   {
/*  721 */     System.arraycopy(this.rgb, 0, paramArrayOfInt, 0, this.map_size);
/*      */   }
/*      */   
/*      */   private void setTransparentPixel(int paramInt) {
/*  725 */     if ((paramInt >= 0) && (paramInt < this.map_size)) {
/*  726 */       this.rgb[paramInt] &= 0xFFFFFF;
/*  727 */       this.transparent_index = paramInt;
/*  728 */       this.allgrayopaque = false;
/*  729 */       if (this.transparency == 1) {
/*  730 */         setTransparency(2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setTransparency(int paramInt) {
/*  736 */     if (this.transparency != paramInt) {
/*  737 */       this.transparency = paramInt;
/*  738 */       if (paramInt == 1) {
/*  739 */         this.supportsAlpha = false;
/*  740 */         this.numComponents = 3;
/*  741 */         this.nBits = opaqueBits;
/*      */       } else {
/*  743 */         this.supportsAlpha = true;
/*  744 */         this.numComponents = 4;
/*  745 */         this.nBits = alphaBits;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void calculatePixelMask()
/*      */   {
/*  759 */     int i = this.pixel_bits;
/*  760 */     if (i == 3) {
/*  761 */       i = 4;
/*  762 */     } else if ((i > 4) && (i < 8)) {
/*  763 */       i = 8;
/*      */     }
/*  765 */     this.pixel_mask = ((1 << i) - 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getRed(int paramInt)
/*      */   {
/*  780 */     return this.rgb[(paramInt & this.pixel_mask)] >> 16 & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getGreen(int paramInt)
/*      */   {
/*  795 */     return this.rgb[(paramInt & this.pixel_mask)] >> 8 & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getBlue(int paramInt)
/*      */   {
/*  810 */     return this.rgb[(paramInt & this.pixel_mask)] & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getAlpha(int paramInt)
/*      */   {
/*  823 */     return this.rgb[(paramInt & this.pixel_mask)] >> 24 & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getRGB(int paramInt)
/*      */   {
/*  838 */     return this.rgb[(paramInt & this.pixel_mask)];
/*      */   }
/*      */   
/*      */ 
/*  842 */   private int[] lookupcache = new int[40];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Object getDataElements(int paramInt, Object paramObject)
/*      */   {
/*  879 */     int i = paramInt >> 16 & 0xFF;
/*  880 */     int j = paramInt >> 8 & 0xFF;
/*  881 */     int k = paramInt & 0xFF;
/*  882 */     int m = paramInt >>> 24;
/*  883 */     int n = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  890 */     for (int i1 = 38; i1 >= 0; i1 -= 2) {
/*  891 */       if ((n = this.lookupcache[i1]) == 0) {
/*      */         break;
/*      */       }
/*  894 */       if (paramInt == this.lookupcache[(i1 + 1)])
/*  895 */         return installpixel(paramObject, n ^ 0xFFFFFFFF);
/*      */     }
/*      */     int i3;
/*      */     int i4;
/*  899 */     if (this.allgrayopaque)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  910 */       i1 = 256;
/*      */       
/*  912 */       i3 = (i * 77 + j * 150 + k * 29 + 128) / 256;
/*      */       
/*  914 */       for (i4 = 0; i4 < this.map_size; i4++)
/*  915 */         if (this.rgb[i4] != 0)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  921 */           int i2 = (this.rgb[i4] & 0xFF) - i3;
/*  922 */           if (i2 < 0) i2 = -i2;
/*  923 */           if (i2 < i1) {
/*  924 */             n = i4;
/*  925 */             if (i2 == 0) {
/*      */               break;
/*      */             }
/*  928 */             i1 = i2; } } } else { int[] arrayOfInt;
/*      */       int i5;
/*      */       int i6;
/*  931 */       if (this.transparency == 1)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  945 */         i1 = Integer.MAX_VALUE;
/*  946 */         arrayOfInt = this.rgb;
/*      */         
/*  948 */         for (i4 = 0; i4 < this.map_size; i4++) {
/*  949 */           i3 = arrayOfInt[i4];
/*  950 */           if ((i3 == paramInt) && (i3 != 0)) {
/*  951 */             n = i4;
/*  952 */             i1 = 0;
/*  953 */             break;
/*      */           }
/*      */         }
/*      */         
/*  957 */         if (i1 != 0)
/*  958 */           for (i4 = 0; i4 < this.map_size; i4++) {
/*  959 */             i3 = arrayOfInt[i4];
/*  960 */             if (i3 != 0)
/*      */             {
/*      */ 
/*      */ 
/*  964 */               i5 = (i3 >> 16 & 0xFF) - i;
/*  965 */               i6 = i5 * i5;
/*  966 */               if (i6 < i1) {
/*  967 */                 i5 = (i3 >> 8 & 0xFF) - j;
/*  968 */                 i6 += i5 * i5;
/*  969 */                 if (i6 < i1) {
/*  970 */                   i5 = (i3 & 0xFF) - k;
/*  971 */                   i6 += i5 * i5;
/*  972 */                   if (i6 < i1) {
/*  973 */                     n = i4;
/*  974 */                     i1 = i6;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*  980 */       } else if ((m == 0) && (this.transparent_index >= 0))
/*      */       {
/*      */ 
/*      */ 
/*  984 */         n = this.transparent_index;
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*  992 */         i1 = Integer.MAX_VALUE;
/*  993 */         arrayOfInt = this.rgb;
/*  994 */         for (i3 = 0; i3 < this.map_size; i3++) {
/*  995 */           i4 = arrayOfInt[i3];
/*  996 */           if (i4 == paramInt) {
/*  997 */             if ((this.validBits == null) || (this.validBits.testBit(i3)))
/*      */             {
/*      */ 
/* 1000 */               n = i3;
/* 1001 */               break;
/*      */             }
/*      */           } else {
/* 1004 */             i5 = (i4 >> 16 & 0xFF) - i;
/* 1005 */             i6 = i5 * i5;
/* 1006 */             if (i6 < i1) {
/* 1007 */               i5 = (i4 >> 8 & 0xFF) - j;
/* 1008 */               i6 += i5 * i5;
/* 1009 */               if (i6 < i1) {
/* 1010 */                 i5 = (i4 & 0xFF) - k;
/* 1011 */                 i6 += i5 * i5;
/* 1012 */                 if (i6 < i1) {
/* 1013 */                   i5 = (i4 >>> 24) - m;
/* 1014 */                   i6 += i5 * i5;
/* 1015 */                   if ((i6 < i1) && ((this.validBits == null) || 
/* 1016 */                     (this.validBits.testBit(i3))))
/*      */                   {
/* 1018 */                     n = i3;
/* 1019 */                     i1 = i6;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         } } }
/* 1026 */     System.arraycopy(this.lookupcache, 2, this.lookupcache, 0, 38);
/* 1027 */     this.lookupcache[39] = paramInt;
/* 1028 */     this.lookupcache[38] = (n ^ 0xFFFFFFFF);
/* 1029 */     return installpixel(paramObject, n);
/*      */   }
/*      */   
/*      */   private Object installpixel(Object paramObject, int paramInt) {
/* 1033 */     switch (this.transferType) {
/*      */     case 3: 
/*      */       int[] arrayOfInt;
/* 1036 */       if (paramObject == null) {
/* 1037 */         paramObject = arrayOfInt = new int[1];
/*      */       } else {
/* 1039 */         arrayOfInt = (int[])paramObject;
/*      */       }
/* 1041 */       arrayOfInt[0] = paramInt;
/* 1042 */       break;
/*      */     case 0: 
/*      */       byte[] arrayOfByte;
/* 1045 */       if (paramObject == null) {
/* 1046 */         paramObject = arrayOfByte = new byte[1];
/*      */       } else {
/* 1048 */         arrayOfByte = (byte[])paramObject;
/*      */       }
/* 1050 */       arrayOfByte[0] = ((byte)paramInt);
/* 1051 */       break;
/*      */     case 1: 
/*      */       short[] arrayOfShort;
/* 1054 */       if (paramObject == null) {
/* 1055 */         paramObject = arrayOfShort = new short[1];
/*      */       } else {
/* 1057 */         arrayOfShort = (short[])paramObject;
/*      */       }
/* 1059 */       arrayOfShort[0] = ((short)paramInt);
/* 1060 */       break;
/*      */     case 2: default: 
/* 1062 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/* 1065 */     return paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getComponents(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*      */   {
/* 1094 */     if (paramArrayOfInt == null) {
/* 1095 */       paramArrayOfInt = new int[paramInt2 + this.numComponents];
/*      */     }
/*      */     
/*      */ 
/* 1099 */     paramArrayOfInt[(paramInt2 + 0)] = getRed(paramInt1);
/* 1100 */     paramArrayOfInt[(paramInt2 + 1)] = getGreen(paramInt1);
/* 1101 */     paramArrayOfInt[(paramInt2 + 2)] = getBlue(paramInt1);
/* 1102 */     if ((this.supportsAlpha) && (paramArrayOfInt.length - paramInt2 > 3)) {
/* 1103 */       paramArrayOfInt[(paramInt2 + 3)] = getAlpha(paramInt1);
/*      */     }
/*      */     
/* 1106 */     return paramArrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getComponents(Object paramObject, int[] paramArrayOfInt, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1161 */     switch (this.transferType) {
/*      */     case 0: 
/* 1163 */       byte[] arrayOfByte = (byte[])paramObject;
/* 1164 */       i = arrayOfByte[0] & 0xFF;
/* 1165 */       break;
/*      */     case 1: 
/* 1167 */       short[] arrayOfShort = (short[])paramObject;
/* 1168 */       i = arrayOfShort[0] & 0xFFFF;
/* 1169 */       break;
/*      */     case 3: 
/* 1171 */       int[] arrayOfInt = (int[])paramObject;
/* 1172 */       i = arrayOfInt[0];
/* 1173 */       break;
/*      */     case 2: default: 
/* 1175 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/* 1178 */     return getComponents(i, paramArrayOfInt, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getDataElement(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 1206 */     int i = paramArrayOfInt[(paramInt + 0)] << 16 | paramArrayOfInt[(paramInt + 1)] << 8 | paramArrayOfInt[(paramInt + 2)];
/*      */     
/* 1208 */     if (this.supportsAlpha) {
/* 1209 */       i |= paramArrayOfInt[(paramInt + 3)] << 24;
/*      */     }
/*      */     else {
/* 1212 */       i |= 0xFF000000;
/*      */     }
/* 1214 */     Object localObject = getDataElements(i, null);
/*      */     int j;
/* 1216 */     switch (this.transferType) {
/*      */     case 0: 
/* 1218 */       byte[] arrayOfByte = (byte[])localObject;
/* 1219 */       j = arrayOfByte[0] & 0xFF;
/* 1220 */       break;
/*      */     case 1: 
/* 1222 */       short[] arrayOfShort = (short[])localObject;
/* 1223 */       j = arrayOfShort[0];
/* 1224 */       break;
/*      */     case 3: 
/* 1226 */       int[] arrayOfInt = (int[])localObject;
/* 1227 */       j = arrayOfInt[0];
/* 1228 */       break;
/*      */     case 2: default: 
/* 1230 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/* 1233 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getDataElements(int[] paramArrayOfInt, int paramInt, Object paramObject)
/*      */   {
/* 1279 */     int i = paramArrayOfInt[(paramInt + 0)] << 16 | paramArrayOfInt[(paramInt + 1)] << 8 | paramArrayOfInt[(paramInt + 2)];
/*      */     
/* 1281 */     if (this.supportsAlpha) {
/* 1282 */       i |= paramArrayOfInt[(paramInt + 3)] << 24;
/*      */     }
/*      */     else {
/* 1285 */       i &= 0xFF000000;
/*      */     }
/* 1287 */     return getDataElements(i, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2)
/*      */   {
/*      */     WritableRaster localWritableRaster;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1312 */     if ((this.pixel_bits == 1) || (this.pixel_bits == 2) || (this.pixel_bits == 4))
/*      */     {
/* 1314 */       localWritableRaster = Raster.createPackedRaster(0, paramInt1, paramInt2, 1, this.pixel_bits, null);
/*      */ 
/*      */     }
/* 1317 */     else if (this.pixel_bits <= 8) {
/* 1318 */       localWritableRaster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, 1, null);
/*      */ 
/*      */     }
/* 1321 */     else if (this.pixel_bits <= 16) {
/* 1322 */       localWritableRaster = Raster.createInterleavedRaster(1, paramInt1, paramInt2, 1, null);
/*      */     }
/*      */     else
/*      */     {
/* 1326 */       throw new UnsupportedOperationException("This method is not supported  for pixel bits > 16.");
/*      */     }
/*      */     
/*      */ 
/* 1330 */     return localWritableRaster;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isCompatibleRaster(Raster paramRaster)
/*      */   {
/* 1344 */     int i = paramRaster.getSampleModel().getSampleSize(0);
/*      */     
/* 1346 */     return (paramRaster.getTransferType() == this.transferType) && (paramRaster.getNumBands() == 1) && (1 << i >= this.map_size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2)
/*      */   {
/* 1362 */     int[] arrayOfInt = new int[1];
/* 1363 */     arrayOfInt[0] = 0;
/* 1364 */     if ((this.pixel_bits == 1) || (this.pixel_bits == 2) || (this.pixel_bits == 4)) {
/* 1365 */       return new MultiPixelPackedSampleModel(this.transferType, paramInt1, paramInt2, this.pixel_bits);
/*      */     }
/*      */     
/*      */ 
/* 1369 */     return new ComponentSampleModel(this.transferType, paramInt1, paramInt2, 1, paramInt1, arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isCompatibleSampleModel(SampleModel paramSampleModel)
/*      */   {
/* 1387 */     if ((!(paramSampleModel instanceof ComponentSampleModel)) && (!(paramSampleModel instanceof MultiPixelPackedSampleModel)))
/*      */     {
/* 1389 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1393 */     if (paramSampleModel.getTransferType() != this.transferType) {
/* 1394 */       return false;
/*      */     }
/*      */     
/* 1397 */     if (paramSampleModel.getNumBands() != 1) {
/* 1398 */       return false;
/*      */     }
/*      */     
/* 1401 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BufferedImage convertToIntDiscrete(Raster paramRaster, boolean paramBoolean)
/*      */   {
/* 1429 */     if (!isCompatibleRaster(paramRaster)) {
/* 1430 */       throw new IllegalArgumentException("This raster is not compatiblewith this IndexColorModel.");
/*      */     }
/*      */     Object localObject1;
/* 1433 */     if ((paramBoolean) || (this.transparency == 3)) {
/* 1434 */       localObject1 = ColorModel.getRGBdefault();
/*      */     }
/* 1436 */     else if (this.transparency == 2) {
/* 1437 */       localObject1 = new DirectColorModel(25, 16711680, 65280, 255, 16777216);
/*      */     }
/*      */     else
/*      */     {
/* 1441 */       localObject1 = new DirectColorModel(24, 16711680, 65280, 255);
/*      */     }
/*      */     
/* 1444 */     int i = paramRaster.getWidth();
/* 1445 */     int j = paramRaster.getHeight();
/*      */     
/* 1447 */     WritableRaster localWritableRaster = ((ColorModel)localObject1).createCompatibleWritableRaster(i, j);
/* 1448 */     Object localObject2 = null;
/* 1449 */     int[] arrayOfInt = null;
/*      */     
/* 1451 */     int k = paramRaster.getMinX();
/* 1452 */     int m = paramRaster.getMinY();
/*      */     
/* 1454 */     for (int n = 0; n < j; m++) {
/* 1455 */       localObject2 = paramRaster.getDataElements(k, m, i, 1, localObject2);
/* 1456 */       if ((localObject2 instanceof int[])) {
/* 1457 */         arrayOfInt = (int[])localObject2;
/*      */       } else {
/* 1459 */         arrayOfInt = DataBuffer.toIntArray(localObject2);
/*      */       }
/* 1461 */       for (int i1 = 0; i1 < i; i1++) {
/* 1462 */         arrayOfInt[i1] = this.rgb[(arrayOfInt[i1] & this.pixel_mask)];
/*      */       }
/* 1464 */       localWritableRaster.setDataElements(0, n, i, 1, arrayOfInt);n++;
/*      */     }
/*      */     
/* 1467 */     return new BufferedImage((ColorModel)localObject1, localWritableRaster, false, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isValid(int paramInt)
/*      */   {
/* 1479 */     return (paramInt >= 0) && (paramInt < this.map_size) && ((this.validBits == null) || (this.validBits.testBit(paramInt)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isValid()
/*      */   {
/* 1489 */     return this.validBits == null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger getValidPixels()
/*      */   {
/* 1503 */     if (this.validBits == null) {
/* 1504 */       return getAllValid();
/*      */     }
/*      */     
/* 1507 */     return this.validBits;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1526 */     return new String("IndexColorModel: #pixelBits = " + this.pixel_bits + " numComponents = " + this.numComponents + " color space = " + this.colorSpace + " transparency = " + this.transparency + " transIndex   = " + this.transparent_index + " has alpha = " + this.supportsAlpha + " isAlphaPre = " + this.isAlphaPremultiplied);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   public void finalize() {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/IndexColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */