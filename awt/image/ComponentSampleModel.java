/*      */ package java.awt.image;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ComponentSampleModel
/*      */   extends SampleModel
/*      */ {
/*      */   protected int[] bandOffsets;
/*      */   protected int[] bankIndices;
/*   88 */   protected int numBands = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   94 */   protected int numBanks = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int scanlineStride;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int pixelStride;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*  109 */     ColorModel.loadLibraries();
/*  110 */     initIDs();
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
/*      */   public ComponentSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
/*      */   {
/*  146 */     super(paramInt1, paramInt2, paramInt3, paramArrayOfInt.length);
/*  147 */     this.dataType = paramInt1;
/*  148 */     this.pixelStride = paramInt4;
/*  149 */     this.scanlineStride = paramInt5;
/*  150 */     this.bandOffsets = ((int[])paramArrayOfInt.clone());
/*  151 */     this.numBands = this.bandOffsets.length;
/*  152 */     if (paramInt4 < 0) {
/*  153 */       throw new IllegalArgumentException("Pixel stride must be >= 0");
/*      */     }
/*      */     
/*  156 */     if (paramInt5 < 0) {
/*  157 */       throw new IllegalArgumentException("Scanline stride must be >= 0");
/*      */     }
/*  159 */     if (this.numBands < 1) {
/*  160 */       throw new IllegalArgumentException("Must have at least one band.");
/*      */     }
/*  162 */     if ((paramInt1 < 0) || (paramInt1 > 5))
/*      */     {
/*  164 */       throw new IllegalArgumentException("Unsupported dataType.");
/*      */     }
/*  166 */     this.bankIndices = new int[this.numBands];
/*  167 */     for (int i = 0; i < this.numBands; i++) {
/*  168 */       this.bankIndices[i] = 0;
/*      */     }
/*  170 */     verify();
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
/*      */   public ComponentSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  210 */     super(paramInt1, paramInt2, paramInt3, paramArrayOfInt2.length);
/*  211 */     this.dataType = paramInt1;
/*  212 */     this.pixelStride = paramInt4;
/*  213 */     this.scanlineStride = paramInt5;
/*  214 */     this.bandOffsets = ((int[])paramArrayOfInt2.clone());
/*  215 */     this.bankIndices = ((int[])paramArrayOfInt1.clone());
/*  216 */     if (paramInt4 < 0) {
/*  217 */       throw new IllegalArgumentException("Pixel stride must be >= 0");
/*      */     }
/*      */     
/*  220 */     if (paramInt5 < 0) {
/*  221 */       throw new IllegalArgumentException("Scanline stride must be >= 0");
/*      */     }
/*  223 */     if ((paramInt1 < 0) || (paramInt1 > 5))
/*      */     {
/*  225 */       throw new IllegalArgumentException("Unsupported dataType.");
/*      */     }
/*  227 */     int i = this.bankIndices[0];
/*  228 */     if (i < 0) {
/*  229 */       throw new IllegalArgumentException("Index of bank 0 is less than 0 (" + i + ")");
/*      */     }
/*      */     
/*  232 */     for (int j = 1; j < this.bankIndices.length; j++) {
/*  233 */       if (this.bankIndices[j] > i) {
/*  234 */         i = this.bankIndices[j];
/*      */       }
/*  236 */       else if (this.bankIndices[j] < 0) {
/*  237 */         throw new IllegalArgumentException("Index of bank " + j + " is less than 0 (" + i + ")");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  242 */     this.numBanks = (i + 1);
/*  243 */     this.numBands = this.bandOffsets.length;
/*  244 */     if (this.bandOffsets.length != this.bankIndices.length) {
/*  245 */       throw new IllegalArgumentException("Length of bandOffsets must equal length of bankIndices.");
/*      */     }
/*      */     
/*  248 */     verify();
/*      */   }
/*      */   
/*      */   private void verify() {
/*  252 */     int i = getBufferSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getBufferSize()
/*      */   {
/*  260 */     int i = this.bandOffsets[0];
/*  261 */     for (int j = 1; j < this.bandOffsets.length; j++) {
/*  262 */       i = Math.max(i, this.bandOffsets[j]);
/*      */     }
/*      */     
/*  265 */     if ((i < 0) || (i > 2147483646)) {
/*  266 */       throw new IllegalArgumentException("Invalid band offset");
/*      */     }
/*      */     
/*  269 */     if ((this.pixelStride < 0) || (this.pixelStride > Integer.MAX_VALUE / this.width)) {
/*  270 */       throw new IllegalArgumentException("Invalid pixel stride");
/*      */     }
/*      */     
/*  273 */     if ((this.scanlineStride < 0) || (this.scanlineStride > Integer.MAX_VALUE / this.height)) {
/*  274 */       throw new IllegalArgumentException("Invalid scanline stride");
/*      */     }
/*      */     
/*  277 */     j = i + 1;
/*      */     
/*  279 */     int k = this.pixelStride * (this.width - 1);
/*      */     
/*  281 */     if (k > Integer.MAX_VALUE - j) {
/*  282 */       throw new IllegalArgumentException("Invalid pixel stride");
/*      */     }
/*      */     
/*  285 */     j += k;
/*      */     
/*  287 */     k = this.scanlineStride * (this.height - 1);
/*      */     
/*  289 */     if (k > Integer.MAX_VALUE - j) {
/*  290 */       throw new IllegalArgumentException("Invalid scan stride");
/*      */     }
/*      */     
/*  293 */     j += k;
/*      */     
/*  295 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   int[] orderBands(int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  302 */     int[] arrayOfInt1 = new int[paramArrayOfInt.length];
/*  303 */     int[] arrayOfInt2 = new int[paramArrayOfInt.length];
/*      */     
/*  305 */     for (int i = 0; i < arrayOfInt1.length; i++) { arrayOfInt1[i] = i;
/*      */     }
/*  307 */     for (i = 0; i < arrayOfInt2.length; i++) {
/*  308 */       int j = i;
/*  309 */       for (int k = i + 1; k < arrayOfInt2.length; k++) {
/*  310 */         if (paramArrayOfInt[arrayOfInt1[j]] > paramArrayOfInt[arrayOfInt1[k]]) {
/*  311 */           j = k;
/*      */         }
/*      */       }
/*  314 */       arrayOfInt2[arrayOfInt1[j]] = (i * paramInt);
/*  315 */       arrayOfInt1[j] = arrayOfInt1[i];
/*      */     }
/*  317 */     return arrayOfInt2;
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
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2)
/*      */   {
/*  332 */     Object localObject = null;
/*      */     
/*  334 */     int i = this.bandOffsets[0];
/*  335 */     int j = this.bandOffsets[0];
/*  336 */     for (int k = 1; k < this.bandOffsets.length; k++) {
/*  337 */       i = Math.min(i, this.bandOffsets[k]);
/*  338 */       j = Math.max(j, this.bandOffsets[k]);
/*      */     }
/*  340 */     j -= i;
/*      */     
/*  342 */     k = this.bandOffsets.length;
/*      */     
/*  344 */     int m = Math.abs(this.pixelStride);
/*  345 */     int n = Math.abs(this.scanlineStride);
/*  346 */     int i1 = Math.abs(j);
/*      */     int[] arrayOfInt;
/*  348 */     if (m > n) {
/*  349 */       if (m > i1) {
/*  350 */         if (n > i1) {
/*  351 */           arrayOfInt = new int[this.bandOffsets.length];
/*  352 */           for (i2 = 0; i2 < k; i2++)
/*  353 */             arrayOfInt[i2] = (this.bandOffsets[i2] - i);
/*  354 */           n = i1 + 1;
/*  355 */           m = n * paramInt2;
/*      */         } else {
/*  357 */           arrayOfInt = orderBands(this.bandOffsets, n * paramInt2);
/*  358 */           m = k * n * paramInt2;
/*      */         }
/*      */       } else {
/*  361 */         m = n * paramInt2;
/*  362 */         arrayOfInt = orderBands(this.bandOffsets, m * paramInt1);
/*      */       }
/*      */     }
/*  365 */     else if (m > i1) {
/*  366 */       arrayOfInt = new int[this.bandOffsets.length];
/*  367 */       for (i2 = 0; i2 < k; i2++)
/*  368 */         arrayOfInt[i2] = (this.bandOffsets[i2] - i);
/*  369 */       m = i1 + 1;
/*  370 */       n = m * paramInt1;
/*      */     }
/*  372 */     else if (n > i1) {
/*  373 */       arrayOfInt = orderBands(this.bandOffsets, m * paramInt1);
/*  374 */       n = k * m * paramInt1;
/*      */     } else {
/*  376 */       n = m * paramInt1;
/*  377 */       arrayOfInt = orderBands(this.bandOffsets, n * paramInt2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  383 */     int i2 = 0;
/*  384 */     if (this.scanlineStride < 0) {
/*  385 */       i2 += n * paramInt2;
/*  386 */       n *= -1;
/*      */     }
/*  388 */     if (this.pixelStride < 0) {
/*  389 */       i2 += m * paramInt1;
/*  390 */       m *= -1;
/*      */     }
/*      */     
/*  393 */     for (int i3 = 0; i3 < k; i3++)
/*  394 */       arrayOfInt[i3] += i2;
/*  395 */     return new ComponentSampleModel(this.dataType, paramInt1, paramInt2, m, n, this.bankIndices, arrayOfInt);
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
/*      */   public SampleModel createSubsetSampleModel(int[] paramArrayOfInt)
/*      */   {
/*  412 */     if (paramArrayOfInt.length > this.bankIndices.length) {
/*  413 */       throw new RasterFormatException("There are only " + this.bankIndices.length + " bands");
/*      */     }
/*      */     
/*  416 */     int[] arrayOfInt1 = new int[paramArrayOfInt.length];
/*  417 */     int[] arrayOfInt2 = new int[paramArrayOfInt.length];
/*      */     
/*  419 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  420 */       arrayOfInt1[i] = this.bankIndices[paramArrayOfInt[i]];
/*  421 */       arrayOfInt2[i] = this.bandOffsets[paramArrayOfInt[i]];
/*      */     }
/*      */     
/*  424 */     return new ComponentSampleModel(this.dataType, this.width, this.height, this.pixelStride, this.scanlineStride, arrayOfInt1, arrayOfInt2);
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
/*      */   public DataBuffer createDataBuffer()
/*      */   {
/*  440 */     Object localObject = null;
/*      */     
/*  442 */     int i = getBufferSize();
/*  443 */     switch (this.dataType) {
/*      */     case 0: 
/*  445 */       localObject = new DataBufferByte(i, this.numBanks);
/*  446 */       break;
/*      */     case 1: 
/*  448 */       localObject = new DataBufferUShort(i, this.numBanks);
/*  449 */       break;
/*      */     case 2: 
/*  451 */       localObject = new DataBufferShort(i, this.numBanks);
/*  452 */       break;
/*      */     case 3: 
/*  454 */       localObject = new DataBufferInt(i, this.numBanks);
/*  455 */       break;
/*      */     case 4: 
/*  457 */       localObject = new DataBufferFloat(i, this.numBanks);
/*  458 */       break;
/*      */     case 5: 
/*  460 */       localObject = new DataBufferDouble(i, this.numBanks);
/*      */     }
/*      */     
/*      */     
/*  464 */     return (DataBuffer)localObject;
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
/*      */   public int getOffset(int paramInt1, int paramInt2)
/*      */   {
/*  481 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[0];
/*  482 */     return i;
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
/*      */   public int getOffset(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  498 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3];
/*  499 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int[] getSampleSize()
/*      */   {
/*  508 */     int[] arrayOfInt = new int[this.numBands];
/*  509 */     int i = getSampleSize(0);
/*      */     
/*  511 */     for (int j = 0; j < this.numBands; j++) {
/*  512 */       arrayOfInt[j] = i;
/*      */     }
/*  514 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getSampleSize(int paramInt)
/*      */   {
/*  522 */     return DataBuffer.getDataTypeSize(this.dataType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final int[] getBankIndices()
/*      */   {
/*  529 */     return (int[])this.bankIndices.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final int[] getBandOffsets()
/*      */   {
/*  536 */     return (int[])this.bandOffsets.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final int getScanlineStride()
/*      */   {
/*  543 */     return this.scanlineStride;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final int getPixelStride()
/*      */   {
/*  550 */     return this.pixelStride;
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
/*      */   public final int getNumDataElements()
/*      */   {
/*  568 */     return getNumBands();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer)
/*      */   {
/*  620 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/*  621 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */ 
/*  625 */     int i = getTransferType();
/*  626 */     int j = getNumDataElements();
/*  627 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*      */     
/*  629 */     switch (i)
/*      */     {
/*      */     case 0: 
/*      */       byte[] arrayOfByte;
/*      */       
/*      */ 
/*  635 */       if (paramObject == null) {
/*  636 */         arrayOfByte = new byte[j];
/*      */       } else {
/*  638 */         arrayOfByte = (byte[])paramObject;
/*      */       }
/*  640 */       for (int m = 0; m < j; m++) {
/*  641 */         arrayOfByte[m] = ((byte)paramDataBuffer.getElem(this.bankIndices[m], k + this.bandOffsets[m]));
/*      */       }
/*      */       
/*      */ 
/*  645 */       paramObject = arrayOfByte;
/*  646 */       break;
/*      */     case 1: 
/*      */     case 2: 
/*      */       short[] arrayOfShort;
/*      */       
/*      */ 
/*      */ 
/*  653 */       if (paramObject == null) {
/*  654 */         arrayOfShort = new short[j];
/*      */       } else {
/*  656 */         arrayOfShort = (short[])paramObject;
/*      */       }
/*  658 */       for (int n = 0; n < j; n++) {
/*  659 */         arrayOfShort[n] = ((short)paramDataBuffer.getElem(this.bankIndices[n], k + this.bandOffsets[n]));
/*      */       }
/*      */       
/*      */ 
/*  663 */       paramObject = arrayOfShort;
/*  664 */       break;
/*      */     case 3: 
/*      */       int[] arrayOfInt;
/*      */       
/*      */ 
/*      */ 
/*  670 */       if (paramObject == null) {
/*  671 */         arrayOfInt = new int[j];
/*      */       } else {
/*  673 */         arrayOfInt = (int[])paramObject;
/*      */       }
/*  675 */       for (int i1 = 0; i1 < j; i1++) {
/*  676 */         arrayOfInt[i1] = paramDataBuffer.getElem(this.bankIndices[i1], k + this.bandOffsets[i1]);
/*      */       }
/*      */       
/*      */ 
/*  680 */       paramObject = arrayOfInt;
/*  681 */       break;
/*      */     case 4: 
/*      */       float[] arrayOfFloat;
/*      */       
/*      */ 
/*      */ 
/*  687 */       if (paramObject == null) {
/*  688 */         arrayOfFloat = new float[j];
/*      */       } else {
/*  690 */         arrayOfFloat = (float[])paramObject;
/*      */       }
/*  692 */       for (int i2 = 0; i2 < j; i2++) {
/*  693 */         arrayOfFloat[i2] = paramDataBuffer.getElemFloat(this.bankIndices[i2], k + this.bandOffsets[i2]);
/*      */       }
/*      */       
/*      */ 
/*  697 */       paramObject = arrayOfFloat;
/*  698 */       break;
/*      */     case 5: 
/*      */       double[] arrayOfDouble;
/*      */       
/*      */ 
/*      */ 
/*  704 */       if (paramObject == null) {
/*  705 */         arrayOfDouble = new double[j];
/*      */       } else {
/*  707 */         arrayOfDouble = (double[])paramObject;
/*      */       }
/*  709 */       for (int i3 = 0; i3 < j; i3++) {
/*  710 */         arrayOfDouble[i3] = paramDataBuffer.getElemDouble(this.bankIndices[i3], k + this.bandOffsets[i3]);
/*      */       }
/*      */       
/*      */ 
/*  714 */       paramObject = arrayOfDouble;
/*      */     }
/*      */     
/*      */     
/*  718 */     return paramObject;
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
/*      */   public int[] getPixel(int paramInt1, int paramInt2, int[] paramArrayOfInt, DataBuffer paramDataBuffer)
/*      */   {
/*  738 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/*  739 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */     int[] arrayOfInt;
/*  743 */     if (paramArrayOfInt != null) {
/*  744 */       arrayOfInt = paramArrayOfInt;
/*      */     } else {
/*  746 */       arrayOfInt = new int[this.numBands];
/*      */     }
/*  748 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  749 */     for (int j = 0; j < this.numBands; j++) {
/*  750 */       arrayOfInt[j] = paramDataBuffer.getElem(this.bankIndices[j], i + this.bandOffsets[j]);
/*      */     }
/*      */     
/*  753 */     return arrayOfInt;
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
/*      */   public int[] getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, DataBuffer paramDataBuffer)
/*      */   {
/*  772 */     int i = paramInt1 + paramInt3;
/*  773 */     int j = paramInt2 + paramInt4;
/*      */     
/*  775 */     if ((paramInt1 < 0) || (paramInt1 >= this.width) || (paramInt3 > this.width) || (i < 0) || (i > this.width) || (paramInt2 < 0) || (paramInt2 >= this.height) || (paramInt2 > this.height) || (j < 0) || (j > this.height))
/*      */     {
/*      */ 
/*  778 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */     int[] arrayOfInt;
/*  782 */     if (paramArrayOfInt != null) {
/*  783 */       arrayOfInt = paramArrayOfInt;
/*      */     } else {
/*  785 */       arrayOfInt = new int[paramInt3 * paramInt4 * this.numBands];
/*      */     }
/*  787 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*  788 */     int m = 0;
/*      */     
/*  790 */     for (int n = 0; n < paramInt4; n++) {
/*  791 */       int i1 = k;
/*  792 */       for (int i2 = 0; i2 < paramInt3; i2++) {
/*  793 */         for (int i3 = 0; i3 < this.numBands; i3++)
/*      */         {
/*  795 */           arrayOfInt[(m++)] = paramDataBuffer.getElem(this.bankIndices[i3], i1 + this.bandOffsets[i3]);
/*      */         }
/*  797 */         i1 += this.pixelStride;
/*      */       }
/*  799 */       k += this.scanlineStride;
/*      */     }
/*  801 */     return arrayOfInt;
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
/*      */   public int getSample(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer)
/*      */   {
/*  818 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/*  819 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*  822 */     int i = paramDataBuffer.getElem(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3]);
/*      */     
/*      */ 
/*  825 */     return i;
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
/*      */   public float getSampleFloat(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer)
/*      */   {
/*  842 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/*  843 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */ 
/*  847 */     float f = paramDataBuffer.getElemFloat(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3]);
/*      */     
/*      */ 
/*  850 */     return f;
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
/*      */   public double getSampleDouble(int paramInt1, int paramInt2, int paramInt3, DataBuffer paramDataBuffer)
/*      */   {
/*  867 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/*  868 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */ 
/*  872 */     double d = paramDataBuffer.getElemDouble(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3]);
/*      */     
/*      */ 
/*  875 */     return d;
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
/*      */   public int[] getSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt, DataBuffer paramDataBuffer)
/*      */   {
/*  897 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 + paramInt3 > this.width) || (paramInt2 + paramInt4 > this.height)) {
/*  898 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */     int[] arrayOfInt;
/*  902 */     if (paramArrayOfInt != null) {
/*  903 */       arrayOfInt = paramArrayOfInt;
/*      */     } else {
/*  905 */       arrayOfInt = new int[paramInt3 * paramInt4];
/*      */     }
/*  907 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt5];
/*  908 */     int j = 0;
/*      */     
/*  910 */     for (int k = 0; k < paramInt4; k++) {
/*  911 */       int m = i;
/*  912 */       for (int n = 0; n < paramInt3; n++) {
/*  913 */         arrayOfInt[(j++)] = paramDataBuffer.getElem(this.bankIndices[paramInt5], m);
/*      */         
/*  915 */         m += this.pixelStride;
/*      */       }
/*  917 */       i += this.scanlineStride;
/*      */     }
/*  919 */     return arrayOfInt;
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
/*      */   public void setDataElements(int paramInt1, int paramInt2, Object paramObject, DataBuffer paramDataBuffer)
/*      */   {
/*  960 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/*  961 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */ 
/*  965 */     int i = getTransferType();
/*  966 */     int j = getNumDataElements();
/*  967 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/*      */     
/*  969 */     switch (i)
/*      */     {
/*      */ 
/*      */     case 0: 
/*  973 */       byte[] arrayOfByte = (byte[])paramObject;
/*      */       
/*  975 */       for (int m = 0; m < j; m++) {
/*  976 */         paramDataBuffer.setElem(this.bankIndices[m], k + this.bandOffsets[m], arrayOfByte[m] & 0xFF);
/*      */       }
/*      */       
/*  979 */       break;
/*      */     
/*      */ 
/*      */     case 1: 
/*      */     case 2: 
/*  984 */       short[] arrayOfShort = (short[])paramObject;
/*      */       
/*  986 */       for (int n = 0; n < j; n++) {
/*  987 */         paramDataBuffer.setElem(this.bankIndices[n], k + this.bandOffsets[n], arrayOfShort[n] & 0xFFFF);
/*      */       }
/*      */       
/*  990 */       break;
/*      */     
/*      */ 
/*      */     case 3: 
/*  994 */       int[] arrayOfInt = (int[])paramObject;
/*      */       
/*  996 */       for (int i1 = 0; i1 < j; i1++) {
/*  997 */         paramDataBuffer.setElem(this.bankIndices[i1], k + this.bandOffsets[i1], arrayOfInt[i1]);
/*      */       }
/*      */       
/* 1000 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/* 1004 */       float[] arrayOfFloat = (float[])paramObject;
/*      */       
/* 1006 */       for (int i2 = 0; i2 < j; i2++) {
/* 1007 */         paramDataBuffer.setElemFloat(this.bankIndices[i2], k + this.bandOffsets[i2], arrayOfFloat[i2]);
/*      */       }
/*      */       
/* 1010 */       break;
/*      */     
/*      */ 
/*      */     case 5: 
/* 1014 */       double[] arrayOfDouble = (double[])paramObject;
/*      */       
/* 1016 */       for (int i3 = 0; i3 < j; i3++) {
/* 1017 */         paramDataBuffer.setElemDouble(this.bankIndices[i3], k + this.bandOffsets[i3], arrayOfDouble[i3]);
/*      */       }
/*      */     }
/*      */     
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
/*      */   public void setPixel(int paramInt1, int paramInt2, int[] paramArrayOfInt, DataBuffer paramDataBuffer)
/*      */   {
/* 1037 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/* 1038 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1041 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/* 1042 */     for (int j = 0; j < this.numBands; j++) {
/* 1043 */       paramDataBuffer.setElem(this.bankIndices[j], i + this.bandOffsets[j], paramArrayOfInt[j]);
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
/*      */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, DataBuffer paramDataBuffer)
/*      */   {
/* 1063 */     int i = paramInt1 + paramInt3;
/* 1064 */     int j = paramInt2 + paramInt4;
/*      */     
/* 1066 */     if ((paramInt1 < 0) || (paramInt1 >= this.width) || (paramInt3 > this.width) || (i < 0) || (i > this.width) || (paramInt2 < 0) || (paramInt2 >= this.height) || (paramInt4 > this.height) || (j < 0) || (j > this.height))
/*      */     {
/*      */ 
/* 1069 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/*      */ 
/* 1073 */     int k = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride;
/* 1074 */     int m = 0;
/*      */     
/* 1076 */     for (int n = 0; n < paramInt4; n++) {
/* 1077 */       int i1 = k;
/* 1078 */       for (int i2 = 0; i2 < paramInt3; i2++) {
/* 1079 */         for (int i3 = 0; i3 < this.numBands; i3++) {
/* 1080 */           paramDataBuffer.setElem(this.bankIndices[i3], i1 + this.bandOffsets[i3], paramArrayOfInt[(m++)]);
/*      */         }
/*      */         
/* 1083 */         i1 += this.pixelStride;
/*      */       }
/* 1085 */       k += this.scanlineStride;
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, int paramInt4, DataBuffer paramDataBuffer)
/*      */   {
/* 1104 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/* 1105 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1108 */     paramDataBuffer.setElem(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3], paramInt4);
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat, DataBuffer paramDataBuffer)
/*      */   {
/* 1128 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/* 1129 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1132 */     paramDataBuffer.setElemFloat(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3], paramFloat);
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
/*      */   public void setSample(int paramInt1, int paramInt2, int paramInt3, double paramDouble, DataBuffer paramDataBuffer)
/*      */   {
/* 1153 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
/* 1154 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1157 */     paramDataBuffer.setElemDouble(this.bankIndices[paramInt3], paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt3], paramDouble);
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
/*      */   public void setSamples(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt, DataBuffer paramDataBuffer)
/*      */   {
/* 1179 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 + paramInt3 > this.width) || (paramInt2 + paramInt4 > this.height)) {
/* 1180 */       throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");
/*      */     }
/*      */     
/* 1183 */     int i = paramInt2 * this.scanlineStride + paramInt1 * this.pixelStride + this.bandOffsets[paramInt5];
/* 1184 */     int j = 0;
/*      */     
/* 1186 */     for (int k = 0; k < paramInt4; k++) {
/* 1187 */       int m = i;
/* 1188 */       for (int n = 0; n < paramInt3; n++) {
/* 1189 */         paramDataBuffer.setElem(this.bankIndices[paramInt5], m, paramArrayOfInt[(j++)]);
/* 1190 */         m += this.pixelStride;
/*      */       }
/* 1192 */       i += this.scanlineStride;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 1197 */     if ((paramObject == null) || (!(paramObject instanceof ComponentSampleModel))) {
/* 1198 */       return false;
/*      */     }
/*      */     
/* 1201 */     ComponentSampleModel localComponentSampleModel = (ComponentSampleModel)paramObject;
/* 1202 */     if ((this.width == localComponentSampleModel.width) && (this.height == localComponentSampleModel.height) && (this.numBands == localComponentSampleModel.numBands) && (this.dataType == localComponentSampleModel.dataType)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1207 */     return (Arrays.equals(this.bandOffsets, localComponentSampleModel.bandOffsets)) && (Arrays.equals(this.bankIndices, localComponentSampleModel.bankIndices)) && (this.numBands == localComponentSampleModel.numBands) && (this.numBanks == localComponentSampleModel.numBanks) && (this.scanlineStride == localComponentSampleModel.scanlineStride) && (this.pixelStride == localComponentSampleModel.pixelStride);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1216 */     int i = 0;
/* 1217 */     i = this.width;
/* 1218 */     i <<= 8;
/* 1219 */     i ^= this.height;
/* 1220 */     i <<= 8;
/* 1221 */     i ^= this.numBands;
/* 1222 */     i <<= 8;
/* 1223 */     i ^= this.dataType;
/* 1224 */     i <<= 8;
/* 1225 */     for (int j = 0; j < this.bandOffsets.length; j++) {
/* 1226 */       i ^= this.bandOffsets[j];
/* 1227 */       i <<= 8;
/*      */     }
/* 1229 */     for (j = 0; j < this.bankIndices.length; j++) {
/* 1230 */       i ^= this.bankIndices[j];
/* 1231 */       i <<= 8;
/*      */     }
/* 1233 */     i ^= this.numBands;
/* 1234 */     i <<= 8;
/* 1235 */     i ^= this.numBanks;
/* 1236 */     i <<= 8;
/* 1237 */     i ^= this.scanlineStride;
/* 1238 */     i <<= 8;
/* 1239 */     i ^= this.pixelStride;
/* 1240 */     return i;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/ComponentSampleModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */