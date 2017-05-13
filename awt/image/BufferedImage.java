/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Transparency;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import sun.awt.image.ByteComponentRaster;
/*      */ import sun.awt.image.BytePackedRaster;
/*      */ import sun.awt.image.IntegerComponentRaster;
/*      */ import sun.awt.image.OffScreenImageSource;
/*      */ import sun.awt.image.ShortComponentRaster;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BufferedImage
/*      */   extends Image
/*      */   implements WritableRenderedImage, Transparency
/*      */ {
/*   75 */   int imageType = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   ColorModel colorModel;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   WritableRaster raster;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   OffScreenImageSource osis;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Hashtable properties;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isAlphaPremultiplied;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_CUSTOM = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_INT_RGB = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_INT_ARGB = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_INT_ARGB_PRE = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_INT_BGR = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_3BYTE_BGR = 5;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_4BYTE_ABGR = 6;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_4BYTE_ABGR_PRE = 7;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_USHORT_565_RGB = 8;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_USHORT_555_RGB = 9;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_BYTE_GRAY = 10;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_USHORT_GRAY = 11;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_BYTE_BINARY = 12;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int TYPE_BYTE_INDEXED = 13;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_RED_MASK = 16711680;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_GREEN_MASK = 65280;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_BLUE_MASK = 255;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_ALPHA_MASK = -16777216;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_565_RED_MASK = 63488;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_565_GRN_MASK = 2016;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_565_BLU_MASK = 31;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_555_RED_MASK = 31744;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_555_GRN_MASK = 992;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_555_BLU_MASK = 31;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_BGR_RED_MASK = 255;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_BGR_GRN_MASK = 65280;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int DCM_BGR_BLU_MASK = 16711680;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*  293 */     ColorModel.loadLibraries();
/*  294 */     initIDs();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BufferedImage(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int[] arrayOfInt1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int[] arrayOfInt2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  322 */     switch (paramInt3)
/*      */     {
/*      */     case 1: 
/*  325 */       this.colorModel = new DirectColorModel(24, 16711680, 65280, 255, 0);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  331 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  334 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/*  338 */       this.colorModel = ColorModel.getRGBdefault();
/*      */       
/*  340 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  343 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 3: 
/*  349 */       this.colorModel = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, true, 3);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  359 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  362 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/*  366 */       this.colorModel = new DirectColorModel(24, 255, 65280, 16711680);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  371 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  374 */       break;
/*      */     
/*      */ 
/*      */     case 5: 
/*  378 */       localObject = ColorSpace.getInstance(1000);
/*  379 */       arrayOfInt1 = new int[] { 8, 8, 8 };
/*  380 */       arrayOfInt2 = new int[] { 2, 1, 0 };
/*  381 */       this.colorModel = new ComponentColorModel((ColorSpace)localObject, arrayOfInt1, false, false, 1, 0);
/*      */       
/*      */ 
/*  384 */       this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, paramInt1 * 3, 3, arrayOfInt2, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  389 */       break;
/*      */     
/*      */ 
/*      */     case 6: 
/*  393 */       localObject = ColorSpace.getInstance(1000);
/*  394 */       arrayOfInt1 = new int[] { 8, 8, 8, 8 };
/*  395 */       arrayOfInt2 = new int[] { 3, 2, 1, 0 };
/*  396 */       this.colorModel = new ComponentColorModel((ColorSpace)localObject, arrayOfInt1, true, false, 3, 0);
/*      */       
/*      */ 
/*  399 */       this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, paramInt1 * 4, 4, arrayOfInt2, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  404 */       break;
/*      */     
/*      */ 
/*      */     case 7: 
/*  408 */       localObject = ColorSpace.getInstance(1000);
/*  409 */       arrayOfInt1 = new int[] { 8, 8, 8, 8 };
/*  410 */       arrayOfInt2 = new int[] { 3, 2, 1, 0 };
/*  411 */       this.colorModel = new ComponentColorModel((ColorSpace)localObject, arrayOfInt1, true, true, 3, 0);
/*      */       
/*      */ 
/*  414 */       this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, paramInt1 * 4, 4, arrayOfInt2, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  419 */       break;
/*      */     
/*      */ 
/*      */     case 10: 
/*  423 */       localObject = ColorSpace.getInstance(1003);
/*  424 */       arrayOfInt1 = new int[] { 8 };
/*  425 */       this.colorModel = new ComponentColorModel((ColorSpace)localObject, arrayOfInt1, false, true, 1, 0);
/*      */       
/*      */ 
/*  428 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  431 */       break;
/*      */     
/*      */ 
/*      */     case 11: 
/*  435 */       localObject = ColorSpace.getInstance(1003);
/*  436 */       arrayOfInt1 = new int[] { 16 };
/*  437 */       this.colorModel = new ComponentColorModel((ColorSpace)localObject, arrayOfInt1, false, true, 1, 1);
/*      */       
/*      */ 
/*  440 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  443 */       break;
/*      */     
/*      */ 
/*      */     case 12: 
/*  447 */       localObject = new byte[] { 0, -1 };
/*      */       
/*  449 */       this.colorModel = new IndexColorModel(1, 2, (byte[])localObject, (byte[])localObject, (byte[])localObject);
/*  450 */       this.raster = Raster.createPackedRaster(0, paramInt1, paramInt2, 1, 1, null);
/*      */       
/*      */ 
/*  453 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 13: 
/*  458 */       localObject = new int['Ā'];
/*  459 */       int i = 0;
/*  460 */       for (int j = 0; j < 256; j += 51) {
/*  461 */         for (k = 0; k < 256; k += 51) {
/*  462 */           for (int m = 0; m < 256; m += 51) {
/*  463 */             localObject[(i++)] = (j << 16 | k << 8 | m);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  468 */       j = 256 / (256 - i);
/*      */       
/*      */ 
/*  471 */       int k = j * 3;
/*  472 */       for (; i < 256; i++) {
/*  473 */         localObject[i] = (k << 16 | k << 8 | k);
/*  474 */         k += j;
/*      */       }
/*      */       
/*  477 */       this.colorModel = new IndexColorModel(8, 256, (int[])localObject, 0, false, -1, 0);
/*      */       
/*  479 */       this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, 1, null);
/*      */       
/*      */ 
/*  482 */       break;
/*      */     
/*      */ 
/*      */     case 8: 
/*  486 */       this.colorModel = new DirectColorModel(16, 63488, 2016, 31);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  491 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  494 */       break;
/*      */     
/*      */ 
/*      */     case 9: 
/*  498 */       this.colorModel = new DirectColorModel(15, 31744, 992, 31);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  503 */       this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */       
/*      */ 
/*  506 */       break;
/*      */     
/*      */     default: 
/*  509 */       throw new IllegalArgumentException("Unknown image type " + paramInt3);
/*      */     }
/*      */     
/*      */     
/*  513 */     this.imageType = paramInt3;
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
/*      */   public BufferedImage(int paramInt1, int paramInt2, int paramInt3, IndexColorModel paramIndexColorModel)
/*      */   {
/*  543 */     if ((paramIndexColorModel.hasAlpha()) && (paramIndexColorModel.isAlphaPremultiplied())) {
/*  544 */       throw new IllegalArgumentException("This image types do not have premultiplied alpha.");
/*      */     }
/*      */     
/*      */ 
/*  548 */     switch (paramInt3)
/*      */     {
/*      */     case 12: 
/*  551 */       int j = paramIndexColorModel.getMapSize();
/*  552 */       int i; if (j <= 2) {
/*  553 */         i = 1;
/*  554 */       } else if (j <= 4) {
/*  555 */         i = 2;
/*  556 */       } else if (j <= 16) {
/*  557 */         i = 4;
/*      */       } else {
/*  559 */         throw new IllegalArgumentException("Color map for TYPE_BYTE_BINARY must have no more than 16 entries");
/*      */       }
/*      */       
/*      */ 
/*  563 */       this.raster = Raster.createPackedRaster(0, paramInt1, paramInt2, 1, i, null);
/*      */       
/*  565 */       break;
/*      */     
/*      */     case 13: 
/*  568 */       this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, 1, null);
/*      */       
/*  570 */       break;
/*      */     default: 
/*  572 */       throw new IllegalArgumentException("Invalid image type (" + paramInt3 + ").  Image type must" + " be either TYPE_BYTE_BINARY or " + " TYPE_BYTE_INDEXED");
/*      */     }
/*      */     
/*      */     
/*      */ 
/*      */ 
/*  578 */     if (!paramIndexColorModel.isCompatibleRaster(this.raster)) {
/*  579 */       throw new IllegalArgumentException("Incompatible image type and IndexColorModel");
/*      */     }
/*      */     
/*  582 */     this.colorModel = paramIndexColorModel;
/*  583 */     this.imageType = paramInt3;
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
/*      */   public BufferedImage(ColorModel paramColorModel, WritableRaster paramWritableRaster, boolean paramBoolean, Hashtable<?, ?> paramHashtable)
/*      */   {
/*  629 */     if (!paramColorModel.isCompatibleRaster(paramWritableRaster)) {
/*  630 */       throw new IllegalArgumentException("Raster " + paramWritableRaster + " is incompatible with ColorModel " + paramColorModel);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  636 */     if ((paramWritableRaster.minX != 0) || (paramWritableRaster.minY != 0)) {
/*  637 */       throw new IllegalArgumentException("Raster " + paramWritableRaster + " has minX or minY not equal to zero: " + paramWritableRaster.minX + " " + paramWritableRaster.minY);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  643 */     this.colorModel = paramColorModel;
/*  644 */     this.raster = paramWritableRaster;
/*  645 */     this.properties = paramHashtable;
/*  646 */     int i = paramWritableRaster.getNumBands();
/*  647 */     boolean bool1 = paramColorModel.isAlphaPremultiplied();
/*  648 */     boolean bool2 = isStandard(paramColorModel, paramWritableRaster);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  653 */     coerceData(paramBoolean);
/*      */     
/*  655 */     SampleModel localSampleModel = paramWritableRaster.getSampleModel();
/*  656 */     ColorSpace localColorSpace = paramColorModel.getColorSpace();
/*  657 */     int j = localColorSpace.getType();
/*  658 */     if (j != 5) {
/*  659 */       if ((j == 6) && (bool2) && ((paramColorModel instanceof ComponentColorModel)))
/*      */       {
/*      */ 
/*      */ 
/*  663 */         if (((localSampleModel instanceof ComponentSampleModel)) && 
/*  664 */           (((ComponentSampleModel)localSampleModel).getPixelStride() != i)) {
/*  665 */           this.imageType = 0;
/*  666 */         } else if (((paramWritableRaster instanceof ByteComponentRaster)) && 
/*  667 */           (paramWritableRaster.getNumBands() == 1) && 
/*  668 */           (paramColorModel.getComponentSize(0) == 8) && 
/*  669 */           (((ByteComponentRaster)paramWritableRaster).getPixelStride() == 1)) {
/*  670 */           this.imageType = 10;
/*  671 */         } else if (((paramWritableRaster instanceof ShortComponentRaster)) && 
/*  672 */           (paramWritableRaster.getNumBands() == 1) && 
/*  673 */           (paramColorModel.getComponentSize(0) == 16) && 
/*  674 */           (((ShortComponentRaster)paramWritableRaster).getPixelStride() == 1)) {
/*  675 */           this.imageType = 11;
/*      */         }
/*      */       } else
/*  678 */         this.imageType = 0;
/*      */       return; }
/*      */     Object localObject1;
/*      */     int k;
/*      */     Object localObject2;
/*  683 */     int i1; if (((paramWritableRaster instanceof IntegerComponentRaster)) && ((i == 3) || (i == 4)))
/*      */     {
/*  685 */       localObject1 = (IntegerComponentRaster)paramWritableRaster;
/*      */       
/*      */ 
/*      */ 
/*  689 */       k = paramColorModel.getPixelSize();
/*  690 */       if ((((IntegerComponentRaster)localObject1).getPixelStride() == 1) && (bool2) && ((paramColorModel instanceof DirectColorModel)) && ((k == 32) || (k == 24)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  696 */         localObject2 = (DirectColorModel)paramColorModel;
/*  697 */         int m = ((DirectColorModel)localObject2).getRedMask();
/*  698 */         int n = ((DirectColorModel)localObject2).getGreenMask();
/*  699 */         i1 = ((DirectColorModel)localObject2).getBlueMask();
/*  700 */         if ((m == 16711680) && (n == 65280) && (i1 == 255))
/*      */         {
/*      */ 
/*  703 */           if (((DirectColorModel)localObject2).getAlphaMask() == -16777216) {
/*  704 */             this.imageType = (bool1 ? 3 : 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  710 */           else if (!((DirectColorModel)localObject2).hasAlpha()) {
/*  711 */             this.imageType = 1;
/*      */           }
/*      */           
/*      */         }
/*  715 */         else if ((m == 255) && (n == 65280) && (i1 == 16711680))
/*      */         {
/*  717 */           if (!((DirectColorModel)localObject2).hasAlpha()) {
/*  718 */             this.imageType = 4;
/*      */           }
/*      */         }
/*      */       }
/*      */     } else {
/*  723 */       if (((paramColorModel instanceof IndexColorModel)) && (i == 1) && (bool2))
/*      */       {
/*  725 */         if ((!paramColorModel.hasAlpha()) || (!bool1))
/*      */         {
/*  727 */           localObject1 = (IndexColorModel)paramColorModel;
/*  728 */           k = ((IndexColorModel)localObject1).getPixelSize();
/*      */           
/*  730 */           if ((paramWritableRaster instanceof BytePackedRaster)) {
/*  731 */             this.imageType = 12;
/*      */           }
/*  733 */           else if ((paramWritableRaster instanceof ByteComponentRaster)) {
/*  734 */             localObject2 = (ByteComponentRaster)paramWritableRaster;
/*  735 */             if ((((ByteComponentRaster)localObject2).getPixelStride() == 1) && (k <= 8)) {
/*  736 */               this.imageType = 13;
/*      */             }
/*      */           }
/*  739 */           return; } }
/*  740 */       if (((paramWritableRaster instanceof ShortComponentRaster)) && ((paramColorModel instanceof DirectColorModel)) && (bool2) && (i == 3))
/*      */       {
/*      */ 
/*      */ 
/*  744 */         if (!paramColorModel.hasAlpha())
/*      */         {
/*  746 */           localObject1 = (DirectColorModel)paramColorModel;
/*  747 */           if (((DirectColorModel)localObject1).getRedMask() == 63488) {
/*  748 */             if ((((DirectColorModel)localObject1).getGreenMask() == 2016) && 
/*  749 */               (((DirectColorModel)localObject1).getBlueMask() == 31)) {
/*  750 */               this.imageType = 8;
/*      */             }
/*      */           }
/*  753 */           else if ((((DirectColorModel)localObject1).getRedMask() == 31744) && 
/*  754 */             (((DirectColorModel)localObject1).getGreenMask() == 992) && 
/*  755 */             (((DirectColorModel)localObject1).getBlueMask() == 31)) {
/*  756 */             this.imageType = 9;
/*      */           }
/*      */           
/*  759 */           return; } }
/*  760 */       if (((paramWritableRaster instanceof ByteComponentRaster)) && ((paramColorModel instanceof ComponentColorModel)) && (bool2))
/*      */       {
/*      */ 
/*  763 */         if (((paramWritableRaster.getSampleModel() instanceof PixelInterleavedSampleModel)) && ((i == 3) || (i == 4)))
/*      */         {
/*      */ 
/*  766 */           localObject1 = (ComponentColorModel)paramColorModel;
/*      */           
/*  768 */           PixelInterleavedSampleModel localPixelInterleavedSampleModel = (PixelInterleavedSampleModel)paramWritableRaster.getSampleModel();
/*  769 */           localObject2 = (ByteComponentRaster)paramWritableRaster;
/*  770 */           int[] arrayOfInt1 = localPixelInterleavedSampleModel.getBandOffsets();
/*  771 */           if (((ComponentColorModel)localObject1).getNumComponents() != i)
/*      */           {
/*      */ 
/*  774 */             throw new RasterFormatException("Number of components in ColorModel (" + ((ComponentColorModel)localObject1).getNumComponents() + ") does not match # in " + " Raster (" + i + ")");
/*      */           }
/*      */           
/*      */ 
/*  778 */           int[] arrayOfInt2 = ((ComponentColorModel)localObject1).getComponentSize();
/*  779 */           i1 = 1;
/*  780 */           for (int i2 = 0; i2 < i; i2++) {
/*  781 */             if (arrayOfInt2[i2] != 8) {
/*  782 */               i1 = 0;
/*  783 */               break;
/*      */             }
/*      */           }
/*  786 */           if ((i1 != 0) && 
/*  787 */             (((ByteComponentRaster)localObject2).getPixelStride() == i) && (arrayOfInt1[0] == i - 1) && (arrayOfInt1[1] == i - 2) && (arrayOfInt1[2] == i - 3))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  792 */             if ((i == 3) && (!((ComponentColorModel)localObject1).hasAlpha())) {
/*  793 */               this.imageType = 5;
/*      */             }
/*  795 */             else if ((arrayOfInt1[3] == 0) && (((ComponentColorModel)localObject1).hasAlpha())) {
/*  796 */               this.imageType = (bool1 ? 7 : 6);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean isStandard(ColorModel paramColorModel, WritableRaster paramWritableRaster) {
/*  805 */     Class localClass1 = paramColorModel.getClass();
/*  806 */     final Class localClass2 = paramWritableRaster.getClass();
/*  807 */     final Class localClass3 = paramWritableRaster.getSampleModel().getClass();
/*      */     
/*  809 */     PrivilegedAction local1 = new PrivilegedAction()
/*      */     {
/*      */ 
/*      */       public Boolean run()
/*      */       {
/*      */ 
/*  815 */         ClassLoader localClassLoader = System.class.getClassLoader();
/*      */         
/*  817 */         return Boolean.valueOf((this.val$cmClass.getClassLoader() == localClassLoader) && 
/*  818 */           (localClass3.getClassLoader() == localClassLoader) && 
/*  819 */           (localClass2.getClassLoader() == localClassLoader));
/*      */       }
/*  821 */     };
/*  822 */     return ((Boolean)AccessController.doPrivileged(local1)).booleanValue();
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
/*      */   public int getType()
/*      */   {
/*  845 */     return this.imageType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ColorModel getColorModel()
/*      */   {
/*  854 */     return this.colorModel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public WritableRaster getRaster()
/*      */   {
/*  863 */     return this.raster;
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
/*      */   public WritableRaster getAlphaRaster()
/*      */   {
/*  889 */     return this.colorModel.getAlphaRaster(this.raster);
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
/*      */   public int getRGB(int paramInt1, int paramInt2)
/*      */   {
/*  918 */     return this.colorModel.getRGB(this.raster.getDataElements(paramInt1, paramInt2, null));
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
/*      */   public int[] getRGB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*      */   {
/*  954 */     int i = paramInt5;
/*      */     
/*      */ 
/*  957 */     int k = this.raster.getNumBands();
/*  958 */     int m = this.raster.getDataBuffer().getDataType();
/*  959 */     Object localObject; switch (m) {
/*      */     case 0: 
/*  961 */       localObject = new byte[k];
/*  962 */       break;
/*      */     case 1: 
/*  964 */       localObject = new short[k];
/*  965 */       break;
/*      */     case 3: 
/*  967 */       localObject = new int[k];
/*  968 */       break;
/*      */     case 4: 
/*  970 */       localObject = new float[k];
/*  971 */       break;
/*      */     case 5: 
/*  973 */       localObject = new double[k];
/*  974 */       break;
/*      */     case 2: default: 
/*  976 */       throw new IllegalArgumentException("Unknown data buffer type: " + m);
/*      */     }
/*      */     
/*      */     
/*  980 */     if (paramArrayOfInt == null) {
/*  981 */       paramArrayOfInt = new int[paramInt5 + paramInt4 * paramInt6];
/*      */     }
/*      */     
/*  984 */     for (int n = paramInt2; n < paramInt2 + paramInt4; i += paramInt6) {
/*  985 */       int j = i;
/*  986 */       for (int i1 = paramInt1; i1 < paramInt1 + paramInt3; i1++) {
/*  987 */         paramArrayOfInt[(j++)] = this.colorModel.getRGB(this.raster.getDataElements(i1, n, localObject));
/*      */       }
/*  984 */       n++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  993 */     return paramArrayOfInt;
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
/*      */   public synchronized void setRGB(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1017 */     this.raster.setDataElements(paramInt1, paramInt2, this.colorModel.getDataElements(paramInt3, null));
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
/*      */   public void setRGB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*      */   {
/* 1052 */     int i = paramInt5;
/*      */     
/* 1054 */     Object localObject = null;
/*      */     
/* 1056 */     for (int k = paramInt2; k < paramInt2 + paramInt4; i += paramInt6) {
/* 1057 */       int j = i;
/* 1058 */       for (int m = paramInt1; m < paramInt1 + paramInt3; m++) {
/* 1059 */         localObject = this.colorModel.getDataElements(paramArrayOfInt[(j++)], localObject);
/* 1060 */         this.raster.setDataElements(m, k, localObject);
/*      */       }
/* 1056 */       k++;
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
/*      */   public int getWidth()
/*      */   {
/* 1071 */     return this.raster.getWidth();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getHeight()
/*      */   {
/* 1079 */     return this.raster.getHeight();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWidth(ImageObserver paramImageObserver)
/*      */   {
/* 1088 */     return this.raster.getWidth();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getHeight(ImageObserver paramImageObserver)
/*      */   {
/* 1097 */     return this.raster.getHeight();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ImageProducer getSource()
/*      */   {
/* 1107 */     if (this.osis == null) {
/* 1108 */       if (this.properties == null) {
/* 1109 */         this.properties = new Hashtable();
/*      */       }
/* 1111 */       this.osis = new OffScreenImageSource(this, this.properties);
/*      */     }
/* 1113 */     return this.osis;
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
/*      */   public Object getProperty(String paramString, ImageObserver paramImageObserver)
/*      */   {
/* 1138 */     return getProperty(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getProperty(String paramString)
/*      */   {
/* 1149 */     if (paramString == null) {
/* 1150 */       throw new NullPointerException("null property name is not allowed");
/*      */     }
/* 1152 */     if (this.properties == null) {
/* 1153 */       return Image.UndefinedProperty;
/*      */     }
/* 1155 */     Object localObject = this.properties.get(paramString);
/* 1156 */     if (localObject == null) {
/* 1157 */       localObject = Image.UndefinedProperty;
/*      */     }
/* 1159 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Graphics getGraphics()
/*      */   {
/* 1171 */     return createGraphics();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Graphics2D createGraphics()
/*      */   {
/* 1182 */     GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 1183 */     return localGraphicsEnvironment.createGraphics(this);
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
/*      */   public BufferedImage getSubimage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1205 */     return new BufferedImage(this.colorModel, this.raster.createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, 0, 0, null), this.colorModel.isAlphaPremultiplied(), this.properties);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAlphaPremultiplied()
/*      */   {
/* 1216 */     return this.colorModel.isAlphaPremultiplied();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void coerceData(boolean paramBoolean)
/*      */   {
/* 1228 */     if ((this.colorModel.hasAlpha()) && 
/* 1229 */       (this.colorModel.isAlphaPremultiplied() != paramBoolean))
/*      */     {
/* 1231 */       this.colorModel = this.colorModel.coerceData(this.raster, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1242 */     return "BufferedImage@" + Integer.toHexString(hashCode()) + ": type = " + this.imageType + " " + this.colorModel + " " + this.raster;
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
/*      */   public Vector<RenderedImage> getSources()
/*      */   {
/* 1263 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getPropertyNames()
/*      */   {
/* 1275 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinX()
/*      */   {
/* 1285 */     return this.raster.getMinX();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinY()
/*      */   {
/* 1295 */     return this.raster.getMinY();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SampleModel getSampleModel()
/*      */   {
/* 1305 */     return this.raster.getSampleModel();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getNumXTiles()
/*      */   {
/* 1314 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getNumYTiles()
/*      */   {
/* 1323 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinTileX()
/*      */   {
/* 1332 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinTileY()
/*      */   {
/* 1341 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTileWidth()
/*      */   {
/* 1349 */     return this.raster.getWidth();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTileHeight()
/*      */   {
/* 1357 */     return this.raster.getHeight();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTileGridXOffset()
/*      */   {
/* 1367 */     return this.raster.getSampleModelTranslateX();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTileGridYOffset()
/*      */   {
/* 1377 */     return this.raster.getSampleModelTranslateY();
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
/*      */   public Raster getTile(int paramInt1, int paramInt2)
/*      */   {
/* 1395 */     if ((paramInt1 == 0) && (paramInt2 == 0)) {
/* 1396 */       return this.raster;
/*      */     }
/* 1398 */     throw new ArrayIndexOutOfBoundsException("BufferedImages only have one tile with index 0,0");
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
/*      */   public Raster getData()
/*      */   {
/* 1414 */     int i = this.raster.getWidth();
/* 1415 */     int j = this.raster.getHeight();
/* 1416 */     int k = this.raster.getMinX();
/* 1417 */     int m = this.raster.getMinY();
/*      */     
/* 1419 */     WritableRaster localWritableRaster = Raster.createWritableRaster(this.raster.getSampleModel(), new Point(this.raster
/* 1420 */       .getSampleModelTranslateX(), this.raster
/* 1421 */       .getSampleModelTranslateY()));
/*      */     
/* 1423 */     Object localObject = null;
/*      */     
/* 1425 */     for (int n = m; n < m + j; n++) {
/* 1426 */       localObject = this.raster.getDataElements(k, n, i, 1, localObject);
/* 1427 */       localWritableRaster.setDataElements(k, n, i, 1, localObject);
/*      */     }
/* 1429 */     return localWritableRaster;
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
/*      */   public Raster getData(Rectangle paramRectangle)
/*      */   {
/* 1444 */     SampleModel localSampleModel1 = this.raster.getSampleModel();
/* 1445 */     SampleModel localSampleModel2 = localSampleModel1.createCompatibleSampleModel(paramRectangle.width, paramRectangle.height);
/*      */     
/* 1447 */     WritableRaster localWritableRaster = Raster.createWritableRaster(localSampleModel2, paramRectangle
/* 1448 */       .getLocation());
/* 1449 */     int i = paramRectangle.width;
/* 1450 */     int j = paramRectangle.height;
/* 1451 */     int k = paramRectangle.x;
/* 1452 */     int m = paramRectangle.y;
/*      */     
/* 1454 */     Object localObject = null;
/*      */     
/* 1456 */     for (int n = m; n < m + j; n++) {
/* 1457 */       localObject = this.raster.getDataElements(k, n, i, 1, localObject);
/* 1458 */       localWritableRaster.setDataElements(k, n, i, 1, localObject);
/*      */     }
/* 1460 */     return localWritableRaster;
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
/*      */   public WritableRaster copyData(WritableRaster paramWritableRaster)
/*      */   {
/* 1479 */     if (paramWritableRaster == null) {
/* 1480 */       return (WritableRaster)getData();
/*      */     }
/* 1482 */     int i = paramWritableRaster.getWidth();
/* 1483 */     int j = paramWritableRaster.getHeight();
/* 1484 */     int k = paramWritableRaster.getMinX();
/* 1485 */     int m = paramWritableRaster.getMinY();
/*      */     
/* 1487 */     Object localObject = null;
/*      */     
/* 1489 */     for (int n = m; n < m + j; n++) {
/* 1490 */       localObject = this.raster.getDataElements(k, n, i, 1, localObject);
/* 1491 */       paramWritableRaster.setDataElements(k, n, i, 1, localObject);
/*      */     }
/*      */     
/* 1494 */     return paramWritableRaster;
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
/*      */   public void setData(Raster paramRaster)
/*      */   {
/* 1508 */     int i = paramRaster.getWidth();
/* 1509 */     int j = paramRaster.getHeight();
/* 1510 */     int k = paramRaster.getMinX();
/* 1511 */     int m = paramRaster.getMinY();
/*      */     
/* 1513 */     int[] arrayOfInt = null;
/*      */     
/*      */ 
/* 1516 */     Rectangle localRectangle1 = new Rectangle(k, m, i, j);
/* 1517 */     Rectangle localRectangle2 = new Rectangle(0, 0, this.raster.width, this.raster.height);
/* 1518 */     Rectangle localRectangle3 = localRectangle1.intersection(localRectangle2);
/* 1519 */     if (localRectangle3.isEmpty()) {
/* 1520 */       return;
/*      */     }
/* 1522 */     i = localRectangle3.width;
/* 1523 */     j = localRectangle3.height;
/* 1524 */     k = localRectangle3.x;
/* 1525 */     m = localRectangle3.y;
/*      */     
/*      */ 
/*      */ 
/* 1529 */     for (int n = m; n < m + j; n++) {
/* 1530 */       arrayOfInt = paramRaster.getPixels(k, n, i, 1, arrayOfInt);
/* 1531 */       this.raster.setPixels(k, n, i, 1, arrayOfInt);
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
/*      */   public boolean isTileWritable(int paramInt1, int paramInt2)
/*      */   {
/* 1565 */     if ((paramInt1 == 0) && (paramInt2 == 0)) {
/* 1566 */       return true;
/*      */     }
/* 1568 */     throw new IllegalArgumentException("Only 1 tile in image");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point[] getWritableTileIndices()
/*      */   {
/* 1580 */     Point[] arrayOfPoint = new Point[1];
/* 1581 */     arrayOfPoint[0] = new Point(0, 0);
/*      */     
/* 1583 */     return arrayOfPoint;
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
/*      */   public boolean hasTileWriters()
/*      */   {
/* 1596 */     return true;
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
/*      */   public WritableRaster getWritableTile(int paramInt1, int paramInt2)
/*      */   {
/* 1609 */     return this.raster;
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
/*      */   public int getTransparency()
/*      */   {
/* 1636 */     return this.colorModel.getTransparency();
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   public void addTileObserver(TileObserver paramTileObserver) {}
/*      */   
/*      */   public void removeTileObserver(TileObserver paramTileObserver) {}
/*      */   
/*      */   public void releaseWritableTile(int paramInt1, int paramInt2) {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/BufferedImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */