/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.awt.color.ICC_Profile;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import sun.java2d.cmm.CMSManager;
/*      */ import sun.java2d.cmm.ColorTransform;
/*      */ import sun.java2d.cmm.PCMM;
/*      */ import sun.java2d.cmm.ProfileDeferralMgr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ColorConvertOp
/*      */   implements BufferedImageOp, RasterOp
/*      */ {
/*      */   ICC_Profile[] profileList;
/*      */   ColorSpace[] CSList;
/*      */   ColorTransform thisTransform;
/*      */   ColorTransform thisRasterTransform;
/*      */   ICC_Profile thisSrcProfile;
/*      */   ICC_Profile thisDestProfile;
/*      */   RenderingHints hints;
/*      */   boolean gotProfiles;
/*      */   float[] srcMinVals;
/*      */   float[] srcMaxVals;
/*      */   float[] dstMinVals;
/*      */   float[] dstMaxVals;
/*      */   
/*      */   static
/*      */   {
/*   82 */     if (ProfileDeferralMgr.deferring) {
/*   83 */       ProfileDeferralMgr.activateProfiles();
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
/*      */   public ColorConvertOp(RenderingHints paramRenderingHints)
/*      */   {
/*  100 */     this.profileList = new ICC_Profile[0];
/*  101 */     this.hints = paramRenderingHints;
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
/*      */   public ColorConvertOp(ColorSpace paramColorSpace, RenderingHints paramRenderingHints)
/*      */   {
/*  122 */     if (paramColorSpace == null) {
/*  123 */       throw new NullPointerException("ColorSpace cannot be null");
/*      */     }
/*  125 */     if ((paramColorSpace instanceof ICC_ColorSpace)) {
/*  126 */       this.profileList = new ICC_Profile[1];
/*      */       
/*  128 */       this.profileList[0] = ((ICC_ColorSpace)paramColorSpace).getProfile();
/*      */     }
/*      */     else {
/*  131 */       this.CSList = new ColorSpace[1];
/*  132 */       this.CSList[0] = paramColorSpace;
/*      */     }
/*  134 */     this.hints = paramRenderingHints;
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
/*      */   public ColorConvertOp(ColorSpace paramColorSpace1, ColorSpace paramColorSpace2, RenderingHints paramRenderingHints)
/*      */   {
/*  158 */     if ((paramColorSpace1 == null) || (paramColorSpace2 == null)) {
/*  159 */       throw new NullPointerException("ColorSpaces cannot be null");
/*      */     }
/*  161 */     if (((paramColorSpace1 instanceof ICC_ColorSpace)) && ((paramColorSpace2 instanceof ICC_ColorSpace)))
/*      */     {
/*  163 */       this.profileList = new ICC_Profile[2];
/*      */       
/*  165 */       this.profileList[0] = ((ICC_ColorSpace)paramColorSpace1).getProfile();
/*  166 */       this.profileList[1] = ((ICC_ColorSpace)paramColorSpace2).getProfile();
/*      */       
/*  168 */       getMinMaxValsFromColorSpaces(paramColorSpace1, paramColorSpace2);
/*      */     }
/*      */     else {
/*  171 */       this.CSList = new ColorSpace[2];
/*  172 */       this.CSList[0] = paramColorSpace1;
/*  173 */       this.CSList[1] = paramColorSpace2;
/*      */     }
/*  175 */     this.hints = paramRenderingHints;
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
/*      */   public ColorConvertOp(ICC_Profile[] paramArrayOfICC_Profile, RenderingHints paramRenderingHints)
/*      */   {
/*  208 */     if (paramArrayOfICC_Profile == null) {
/*  209 */       throw new NullPointerException("Profiles cannot be null");
/*      */     }
/*  211 */     this.gotProfiles = true;
/*  212 */     this.profileList = new ICC_Profile[paramArrayOfICC_Profile.length];
/*  213 */     for (int i = 0; i < paramArrayOfICC_Profile.length; i++) {
/*  214 */       this.profileList[i] = paramArrayOfICC_Profile[i];
/*      */     }
/*  216 */     this.hints = paramRenderingHints;
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
/*      */   public final ICC_Profile[] getICC_Profiles()
/*      */   {
/*  230 */     if (this.gotProfiles) {
/*  231 */       ICC_Profile[] arrayOfICC_Profile = new ICC_Profile[this.profileList.length];
/*  232 */       for (int i = 0; i < this.profileList.length; i++) {
/*  233 */         arrayOfICC_Profile[i] = this.profileList[i];
/*      */       }
/*  235 */       return arrayOfICC_Profile;
/*      */     }
/*  237 */     return null;
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
/*      */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2)
/*      */   {
/*  256 */     BufferedImage localBufferedImage = null;
/*      */     Object localObject1;
/*  258 */     if ((paramBufferedImage1.getColorModel() instanceof IndexColorModel)) {
/*  259 */       localObject1 = (IndexColorModel)paramBufferedImage1.getColorModel();
/*  260 */       paramBufferedImage1 = ((IndexColorModel)localObject1).convertToIntDiscrete(paramBufferedImage1.getRaster(), true);
/*      */     }
/*  262 */     ColorSpace localColorSpace1 = paramBufferedImage1.getColorModel().getColorSpace();
/*  263 */     ColorSpace localColorSpace2; if (paramBufferedImage2 != null) {
/*  264 */       if ((paramBufferedImage2.getColorModel() instanceof IndexColorModel)) {
/*  265 */         localBufferedImage = paramBufferedImage2;
/*  266 */         paramBufferedImage2 = null;
/*  267 */         localColorSpace2 = null;
/*      */       } else {
/*  269 */         localColorSpace2 = paramBufferedImage2.getColorModel().getColorSpace();
/*      */       }
/*      */     } else {
/*  272 */       localColorSpace2 = null;
/*      */     }
/*      */     
/*  275 */     if ((this.CSList != null) || (!(localColorSpace1 instanceof ICC_ColorSpace)) || ((paramBufferedImage2 != null) && (!(localColorSpace2 instanceof ICC_ColorSpace))))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  280 */       paramBufferedImage2 = nonICCBIFilter(paramBufferedImage1, localColorSpace1, paramBufferedImage2, localColorSpace2);
/*      */     } else {
/*  282 */       paramBufferedImage2 = ICCBIFilter(paramBufferedImage1, localColorSpace1, paramBufferedImage2, localColorSpace2);
/*      */     }
/*      */     
/*  285 */     if (localBufferedImage != null) {
/*  286 */       localObject1 = localBufferedImage.createGraphics();
/*      */       try {
/*  288 */         ((Graphics2D)localObject1).drawImage(paramBufferedImage2, 0, 0, null);
/*      */       } finally {
/*  290 */         ((Graphics2D)localObject1).dispose();
/*      */       }
/*  292 */       return localBufferedImage;
/*      */     }
/*  294 */     return paramBufferedImage2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final BufferedImage ICCBIFilter(BufferedImage paramBufferedImage1, ColorSpace paramColorSpace1, BufferedImage paramBufferedImage2, ColorSpace paramColorSpace2)
/*      */   {
/*  302 */     int i = this.profileList.length;
/*  303 */     ICC_Profile localICC_Profile1 = null;ICC_Profile localICC_Profile2 = null;
/*      */     
/*  305 */     localICC_Profile1 = ((ICC_ColorSpace)paramColorSpace1).getProfile();
/*      */     
/*  307 */     if (paramBufferedImage2 == null)
/*      */     {
/*  309 */       if (i == 0) {
/*  310 */         throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */       }
/*      */       
/*  313 */       localICC_Profile2 = this.profileList[(i - 1)];
/*  314 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*      */     }
/*      */     else {
/*  317 */       if ((paramBufferedImage1.getHeight() != paramBufferedImage2.getHeight()) || 
/*  318 */         (paramBufferedImage1.getWidth() != paramBufferedImage2.getWidth())) {
/*  319 */         throw new IllegalArgumentException("Width or height of BufferedImages do not match");
/*      */       }
/*      */       
/*  322 */       localICC_Profile2 = ((ICC_ColorSpace)paramColorSpace2).getProfile();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  328 */     if (localICC_Profile1 == localICC_Profile2) {
/*  329 */       int j = 1;
/*  330 */       for (int k = 0; k < i; k++) {
/*  331 */         if (localICC_Profile1 != this.profileList[k]) {
/*  332 */           j = 0;
/*  333 */           break;
/*      */         }
/*      */       }
/*  336 */       if (j != 0) {
/*  337 */         Graphics2D localGraphics2D = paramBufferedImage2.createGraphics();
/*      */         try {
/*  339 */           localGraphics2D.drawImage(paramBufferedImage1, 0, 0, null);
/*      */         } finally {
/*  341 */           localGraphics2D.dispose();
/*      */         }
/*      */         
/*  344 */         return paramBufferedImage2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  349 */     if ((this.thisTransform == null) || (this.thisSrcProfile != localICC_Profile1) || (this.thisDestProfile != localICC_Profile2))
/*      */     {
/*  351 */       updateBITransform(localICC_Profile1, localICC_Profile2);
/*      */     }
/*      */     
/*      */ 
/*  355 */     this.thisTransform.colorConvert(paramBufferedImage1, paramBufferedImage2);
/*      */     
/*  357 */     return paramBufferedImage2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void updateBITransform(ICC_Profile paramICC_Profile1, ICC_Profile paramICC_Profile2)
/*      */   {
/*  365 */     int i1 = 0;int i2 = 0;
/*      */     
/*  367 */     int j = this.profileList.length;
/*  368 */     int k = j;
/*  369 */     if ((j == 0) || (paramICC_Profile1 != this.profileList[0])) {
/*  370 */       k++;
/*  371 */       i1 = 1;
/*      */     }
/*  373 */     if ((j == 0) || (paramICC_Profile2 != this.profileList[(j - 1)]) || (k < 2))
/*      */     {
/*  375 */       k++;
/*  376 */       i2 = 1;
/*      */     }
/*      */     
/*      */ 
/*  380 */     ICC_Profile[] arrayOfICC_Profile = new ICC_Profile[k];
/*      */     
/*      */ 
/*  383 */     int i3 = 0;
/*  384 */     if (i1 != 0)
/*      */     {
/*  386 */       arrayOfICC_Profile[(i3++)] = paramICC_Profile1;
/*      */     }
/*      */     
/*  389 */     for (int i = 0; i < j; i++)
/*      */     {
/*  391 */       arrayOfICC_Profile[(i3++)] = this.profileList[i];
/*      */     }
/*      */     
/*  394 */     if (i2 != 0)
/*      */     {
/*  396 */       arrayOfICC_Profile[i3] = paramICC_Profile2;
/*      */     }
/*      */     
/*      */ 
/*  400 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[k];
/*      */     
/*      */     int n;
/*  403 */     if (arrayOfICC_Profile[0].getProfileClass() == 2)
/*      */     {
/*      */ 
/*  406 */       n = 1;
/*      */     }
/*      */     else {
/*  409 */       n = 0;
/*      */     }
/*      */     
/*      */ 
/*  413 */     int m = 1;
/*      */     
/*  415 */     PCMM localPCMM = CMSManager.getModule();
/*      */     
/*      */ 
/*  418 */     for (i = 0; i < k; i++) {
/*  419 */       if (i == k - 1) {
/*  420 */         m = 2;
/*      */ 
/*      */       }
/*  423 */       else if ((m == 4) && 
/*  424 */         (arrayOfICC_Profile[i].getProfileClass() == 5))
/*      */       {
/*  426 */         n = 0;
/*  427 */         m = 1;
/*      */       }
/*      */       
/*      */ 
/*  431 */       arrayOfColorTransform[i] = localPCMM.createTransform(arrayOfICC_Profile[i], n, m);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  436 */       n = getRenderingIntent(arrayOfICC_Profile[i]);
/*      */       
/*      */ 
/*  439 */       m = 4;
/*      */     }
/*      */     
/*      */ 
/*  443 */     this.thisTransform = localPCMM.createTransform(arrayOfColorTransform);
/*      */     
/*      */ 
/*  446 */     this.thisSrcProfile = paramICC_Profile1;
/*  447 */     this.thisDestProfile = paramICC_Profile2;
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
/*      */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster)
/*      */   {
/*  472 */     if (this.CSList != null)
/*      */     {
/*  474 */       return nonICCRasterFilter(paramRaster, paramWritableRaster);
/*      */     }
/*  476 */     int i = this.profileList.length;
/*  477 */     if (i < 2) {
/*  478 */       throw new IllegalArgumentException("Source or Destination ColorSpace is undefined");
/*      */     }
/*      */     
/*  481 */     if (paramRaster.getNumBands() != this.profileList[0].getNumComponents()) {
/*  482 */       throw new IllegalArgumentException("Numbers of source Raster bands and source color space components do not match");
/*      */     }
/*      */     
/*      */ 
/*  486 */     if (paramWritableRaster == null) {
/*  487 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*      */     }
/*      */     else {
/*  490 */       if ((paramRaster.getHeight() != paramWritableRaster.getHeight()) || 
/*  491 */         (paramRaster.getWidth() != paramWritableRaster.getWidth())) {
/*  492 */         throw new IllegalArgumentException("Width or height of Rasters do not match");
/*      */       }
/*      */       
/*      */ 
/*  496 */       if (paramWritableRaster.getNumBands() != this.profileList[(i - 1)].getNumComponents()) {
/*  497 */         throw new IllegalArgumentException("Numbers of destination Raster bands and destination color space components do not match");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  504 */     if (this.thisRasterTransform == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  509 */       ColorTransform[] arrayOfColorTransform = new ColorTransform[i];
/*      */       
/*      */       int m;
/*  512 */       if (this.profileList[0].getProfileClass() == 2)
/*      */       {
/*      */ 
/*  515 */         m = 1;
/*      */       }
/*      */       else {
/*  518 */         m = 0;
/*      */       }
/*      */       
/*      */ 
/*  522 */       k = 1;
/*      */       
/*  524 */       PCMM localPCMM = CMSManager.getModule();
/*      */       
/*      */ 
/*  527 */       for (j = 0; j < i; j++) {
/*  528 */         if (j == i - 1) {
/*  529 */           k = 2;
/*      */ 
/*      */         }
/*  532 */         else if ((k == 4) && 
/*  533 */           (this.profileList[j].getProfileClass() == 5))
/*      */         {
/*  535 */           m = 0;
/*  536 */           k = 1;
/*      */         }
/*      */         
/*      */ 
/*  540 */         arrayOfColorTransform[j] = localPCMM.createTransform(this.profileList[j], m, k);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  545 */         m = getRenderingIntent(this.profileList[j]);
/*      */         
/*      */ 
/*  548 */         k = 4;
/*      */       }
/*      */       
/*      */ 
/*  552 */       this.thisRasterTransform = localPCMM.createTransform(arrayOfColorTransform);
/*      */     }
/*      */     
/*  555 */     int j = paramRaster.getTransferType();
/*  556 */     int k = paramWritableRaster.getTransferType();
/*  557 */     if ((j == 4) || (j == 5) || (k == 4) || (k == 5))
/*      */     {
/*      */ 
/*      */ 
/*  561 */       if (this.srcMinVals == null) {
/*  562 */         getMinMaxValsFromProfiles(this.profileList[0], this.profileList[(i - 1)]);
/*      */       }
/*      */       
/*      */ 
/*  566 */       this.thisRasterTransform.colorConvert(paramRaster, paramWritableRaster, this.srcMinVals, this.srcMaxVals, this.dstMinVals, this.dstMaxVals);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  571 */       this.thisRasterTransform.colorConvert(paramRaster, paramWritableRaster);
/*      */     }
/*      */     
/*      */ 
/*  575 */     return paramWritableRaster;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage)
/*      */   {
/*  587 */     return getBounds2D(paramBufferedImage.getRaster());
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
/*      */   public final Rectangle2D getBounds2D(Raster paramRaster)
/*      */   {
/*  602 */     return paramRaster.getBounds();
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
/*      */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel)
/*      */   {
/*  620 */     Object localObject = null;
/*  621 */     if (paramColorModel == null) { int i;
/*  622 */       if (this.CSList == null)
/*      */       {
/*  624 */         i = this.profileList.length;
/*  625 */         if (i == 0) {
/*  626 */           throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */         }
/*      */         
/*  629 */         ICC_Profile localICC_Profile = this.profileList[(i - 1)];
/*  630 */         localObject = new ICC_ColorSpace(localICC_Profile);
/*      */       }
/*      */       else {
/*  633 */         i = this.CSList.length;
/*  634 */         localObject = this.CSList[(i - 1)];
/*      */       }
/*      */     }
/*  637 */     return createCompatibleDestImage(paramBufferedImage, paramColorModel, (ColorSpace)localObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel, ColorSpace paramColorSpace)
/*      */   {
/*  644 */     if (paramColorModel == null) {
/*  645 */       ColorModel localColorModel = paramBufferedImage.getColorModel();
/*  646 */       j = paramColorSpace.getNumComponents();
/*  647 */       boolean bool = localColorModel.hasAlpha();
/*  648 */       if (bool) {
/*  649 */         j++;
/*      */       }
/*  651 */       int[] arrayOfInt = new int[j];
/*  652 */       for (int k = 0; k < j; k++) {
/*  653 */         arrayOfInt[k] = 8;
/*      */       }
/*      */       
/*      */ 
/*  657 */       paramColorModel = new ComponentColorModel(paramColorSpace, arrayOfInt, bool, localColorModel.isAlphaPremultiplied(), localColorModel.getTransparency(), 0);
/*      */     }
/*      */     
/*  660 */     int i = paramBufferedImage.getWidth();
/*  661 */     int j = paramBufferedImage.getHeight();
/*      */     
/*      */ 
/*  664 */     BufferedImage localBufferedImage = new BufferedImage(paramColorModel, paramColorModel.createCompatibleWritableRaster(i, j), paramColorModel.isAlphaPremultiplied(), null);
/*  665 */     return localBufferedImage;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public WritableRaster createCompatibleDestRaster(Raster paramRaster)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  682 */     if (this.CSList != null)
/*      */     {
/*  684 */       if (this.CSList.length != 2) {
/*  685 */         throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */       }
/*      */       
/*  688 */       i = this.CSList[1].getNumComponents();
/*      */     }
/*      */     else {
/*  691 */       int j = this.profileList.length;
/*  692 */       if (j < 2) {
/*  693 */         throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */       }
/*      */       
/*  696 */       i = this.profileList[(j - 1)].getNumComponents();
/*      */     }
/*      */     
/*      */ 
/*  700 */     WritableRaster localWritableRaster = Raster.createInterleavedRaster(0, paramRaster
/*  701 */       .getWidth(), paramRaster
/*  702 */       .getHeight(), i, new Point(paramRaster
/*      */       
/*  704 */       .getMinX(), paramRaster.getMinY()));
/*  705 */     return localWritableRaster;
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
/*      */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*      */   {
/*  720 */     if (paramPoint2D2 == null) {
/*  721 */       paramPoint2D2 = new Point2D.Float();
/*      */     }
/*  723 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/*      */     
/*  725 */     return paramPoint2D2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getRenderingIntent(ICC_Profile paramICC_Profile)
/*      */   {
/*  733 */     byte[] arrayOfByte = paramICC_Profile.getData(1751474532);
/*  734 */     int i = 64;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  743 */     return (arrayOfByte[(i + 2)] & 0xFF) << 8 | arrayOfByte[(i + 3)] & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final RenderingHints getRenderingHints()
/*      */   {
/*  753 */     return this.hints;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final BufferedImage nonICCBIFilter(BufferedImage paramBufferedImage1, ColorSpace paramColorSpace1, BufferedImage paramBufferedImage2, ColorSpace paramColorSpace2)
/*      */   {
/*  761 */     int i = paramBufferedImage1.getWidth();
/*  762 */     int j = paramBufferedImage1.getHeight();
/*      */     
/*  764 */     ICC_ColorSpace localICC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1001);
/*  765 */     if (paramBufferedImage2 == null) {
/*  766 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*  767 */       paramColorSpace2 = paramBufferedImage2.getColorModel().getColorSpace();
/*      */     }
/*  769 */     else if ((j != paramBufferedImage2.getHeight()) || (i != paramBufferedImage2.getWidth())) {
/*  770 */       throw new IllegalArgumentException("Width or height of BufferedImages do not match");
/*      */     }
/*      */     
/*      */ 
/*  774 */     WritableRaster localWritableRaster1 = paramBufferedImage1.getRaster();
/*  775 */     WritableRaster localWritableRaster2 = paramBufferedImage2.getRaster();
/*  776 */     ColorModel localColorModel1 = paramBufferedImage1.getColorModel();
/*  777 */     ColorModel localColorModel2 = paramBufferedImage2.getColorModel();
/*  778 */     int k = localColorModel1.getNumColorComponents();
/*  779 */     int m = localColorModel2.getNumColorComponents();
/*  780 */     boolean bool = localColorModel2.hasAlpha();
/*  781 */     int n = (localColorModel1.hasAlpha()) && (bool) ? 1 : 0;
/*      */     int i1;
/*  783 */     Object localObject1; Object localObject2; Object localObject3; int i3; if ((this.CSList == null) && (this.profileList.length != 0))
/*      */     {
/*      */ 
/*      */ 
/*  787 */       if (!(paramColorSpace1 instanceof ICC_ColorSpace)) {
/*  788 */         i1 = 1;
/*  789 */         localObject1 = localICC_ColorSpace.getProfile();
/*      */       } else {
/*  791 */         i1 = 0;
/*  792 */         localObject1 = ((ICC_ColorSpace)paramColorSpace1).getProfile(); }
/*      */       int i2;
/*  794 */       if (!(paramColorSpace2 instanceof ICC_ColorSpace)) {
/*  795 */         i2 = 1;
/*  796 */         localObject2 = localICC_ColorSpace.getProfile();
/*      */       } else {
/*  798 */         i2 = 0;
/*  799 */         localObject2 = ((ICC_ColorSpace)paramColorSpace2).getProfile();
/*      */       }
/*      */       
/*  802 */       if ((this.thisTransform == null) || (this.thisSrcProfile != localObject1) || (this.thisDestProfile != localObject2))
/*      */       {
/*  804 */         updateBITransform((ICC_Profile)localObject1, (ICC_Profile)localObject2);
/*      */       }
/*      */       
/*  807 */       float f = 65535.0F;
/*      */       
/*      */ 
/*  810 */       if (i1 != 0) {
/*  811 */         localObject3 = localICC_ColorSpace;
/*  812 */         i3 = 3;
/*      */       } else {
/*  814 */         localObject3 = paramColorSpace1;
/*  815 */         i3 = k;
/*      */       }
/*  817 */       float[] arrayOfFloat3 = new float[i3];
/*  818 */       float[] arrayOfFloat4 = new float[i3];
/*  819 */       for (int i6 = 0; i6 < k; i6++) {
/*  820 */         arrayOfFloat3[i6] = ((ColorSpace)localObject3).getMinValue(i6);
/*  821 */         arrayOfFloat4[i6] = (f / (((ColorSpace)localObject3).getMaxValue(i6) - arrayOfFloat3[i6]));
/*      */       }
/*      */       
/*  824 */       if (i2 != 0) {
/*  825 */         localObject3 = localICC_ColorSpace;
/*  826 */         i6 = 3;
/*      */       } else {
/*  828 */         localObject3 = paramColorSpace2;
/*  829 */         i6 = m;
/*      */       }
/*  831 */       float[] arrayOfFloat5 = new float[i6];
/*  832 */       float[] arrayOfFloat6 = new float[i6];
/*  833 */       for (int i7 = 0; i7 < m; i7++) {
/*  834 */         arrayOfFloat5[i7] = ((ColorSpace)localObject3).getMinValue(i7);
/*  835 */         arrayOfFloat6[i7] = ((((ColorSpace)localObject3).getMaxValue(i7) - arrayOfFloat5[i7]) / f); }
/*      */       int i8;
/*      */       float[] arrayOfFloat7;
/*  838 */       if (bool) {
/*  839 */         i8 = m + 1 > 3 ? m + 1 : 3;
/*  840 */         arrayOfFloat7 = new float[i8];
/*      */       } else {
/*  842 */         i8 = m > 3 ? m : 3;
/*  843 */         arrayOfFloat7 = new float[i8];
/*      */       }
/*  845 */       short[] arrayOfShort1 = new short[i * i3];
/*  846 */       short[] arrayOfShort2 = new short[i * i6];
/*      */       
/*      */ 
/*  849 */       float[] arrayOfFloat9 = null;
/*  850 */       if (n != 0) {
/*  851 */         arrayOfFloat9 = new float[i];
/*      */       }
/*      */       
/*      */ 
/*  855 */       for (int i10 = 0; i10 < j; i10++)
/*      */       {
/*  857 */         Object localObject4 = null;
/*  858 */         float[] arrayOfFloat8 = null;
/*  859 */         int i9 = 0;
/*  860 */         int i12; for (int i11 = 0; i11 < i; i11++) {
/*  861 */           localObject4 = localWritableRaster1.getDataElements(i11, i10, localObject4);
/*  862 */           arrayOfFloat8 = localColorModel1.getNormalizedComponents(localObject4, arrayOfFloat8, 0);
/*  863 */           if (n != 0) {
/*  864 */             arrayOfFloat9[i11] = arrayOfFloat8[k];
/*      */           }
/*  866 */           if (i1 != 0) {
/*  867 */             arrayOfFloat8 = paramColorSpace1.toCIEXYZ(arrayOfFloat8);
/*      */           }
/*  869 */           for (i12 = 0; i12 < i3; i12++) {
/*  870 */             arrayOfShort1[(i9++)] = ((short)(int)((arrayOfFloat8[i12] - arrayOfFloat3[i12]) * arrayOfFloat4[i12] + 0.5F));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  876 */         this.thisTransform.colorConvert(arrayOfShort1, arrayOfShort2);
/*      */         
/*  878 */         localObject4 = null;
/*  879 */         i9 = 0;
/*  880 */         for (i11 = 0; i11 < i; i11++) {
/*  881 */           for (i12 = 0; i12 < i6; i12++) {
/*  882 */             arrayOfFloat7[i12] = ((arrayOfShort2[(i9++)] & 0xFFFF) * arrayOfFloat6[i12] + arrayOfFloat5[i12]);
/*      */           }
/*      */           
/*  885 */           if (i2 != 0) {
/*  886 */             arrayOfFloat8 = paramColorSpace1.fromCIEXYZ(arrayOfFloat7);
/*  887 */             for (i12 = 0; i12 < m; i12++) {
/*  888 */               arrayOfFloat7[i12] = arrayOfFloat8[i12];
/*      */             }
/*      */           }
/*  891 */           if (n != 0) {
/*  892 */             arrayOfFloat7[m] = arrayOfFloat9[i11];
/*  893 */           } else if (bool) {
/*  894 */             arrayOfFloat7[m] = 1.0F;
/*      */           }
/*  896 */           localObject4 = localColorModel2.getDataElements(arrayOfFloat7, 0, localObject4);
/*  897 */           localWritableRaster2.setDataElements(i11, i10, localObject4);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  904 */       if (this.CSList == null) {
/*  905 */         i1 = 0;
/*      */       } else {
/*  907 */         i1 = this.CSList.length;
/*      */       }
/*      */       float[] arrayOfFloat1;
/*  910 */       if (bool) {
/*  911 */         arrayOfFloat1 = new float[m + 1];
/*      */       } else {
/*  913 */         arrayOfFloat1 = new float[m];
/*      */       }
/*  915 */       localObject1 = null;
/*  916 */       localObject2 = null;
/*  917 */       float[] arrayOfFloat2 = null;
/*      */       
/*      */ 
/*  920 */       for (i3 = 0; i3 < j; i3++) {
/*  921 */         for (int i4 = 0; i4 < i; i4++) {
/*  922 */           localObject1 = localWritableRaster1.getDataElements(i4, i3, localObject1);
/*  923 */           arrayOfFloat2 = localColorModel1.getNormalizedComponents(localObject1, arrayOfFloat2, 0);
/*  924 */           localObject3 = paramColorSpace1.toCIEXYZ(arrayOfFloat2);
/*  925 */           for (int i5 = 0; i5 < i1; i5++) {
/*  926 */             localObject3 = this.CSList[i5].fromCIEXYZ((float[])localObject3);
/*  927 */             localObject3 = this.CSList[i5].toCIEXYZ((float[])localObject3);
/*      */           }
/*  929 */           localObject3 = paramColorSpace2.fromCIEXYZ((float[])localObject3);
/*  930 */           for (i5 = 0; i5 < m; i5++) {
/*  931 */             arrayOfFloat1[i5] = localObject3[i5];
/*      */           }
/*  933 */           if (n != 0) {
/*  934 */             arrayOfFloat1[m] = arrayOfFloat2[k];
/*  935 */           } else if (bool) {
/*  936 */             arrayOfFloat1[m] = 1.0F;
/*      */           }
/*  938 */           localObject2 = localColorModel2.getDataElements(arrayOfFloat1, 0, localObject2);
/*  939 */           localWritableRaster2.setDataElements(i4, i3, localObject2);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  945 */     return paramBufferedImage2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final WritableRaster nonICCRasterFilter(Raster paramRaster, WritableRaster paramWritableRaster)
/*      */   {
/*  953 */     if (this.CSList.length != 2) {
/*  954 */       throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */     }
/*      */     
/*  957 */     if (paramRaster.getNumBands() != this.CSList[0].getNumComponents()) {
/*  958 */       throw new IllegalArgumentException("Numbers of source Raster bands and source color space components do not match");
/*      */     }
/*      */     
/*      */ 
/*  962 */     if (paramWritableRaster == null) {
/*  963 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*      */     } else {
/*  965 */       if ((paramRaster.getHeight() != paramWritableRaster.getHeight()) || 
/*  966 */         (paramRaster.getWidth() != paramWritableRaster.getWidth())) {
/*  967 */         throw new IllegalArgumentException("Width or height of Rasters do not match");
/*      */       }
/*      */       
/*  970 */       if (paramWritableRaster.getNumBands() != this.CSList[1].getNumComponents()) {
/*  971 */         throw new IllegalArgumentException("Numbers of destination Raster bands and destination color space components do not match");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  977 */     if (this.srcMinVals == null) {
/*  978 */       getMinMaxValsFromColorSpaces(this.CSList[0], this.CSList[1]);
/*      */     }
/*      */     
/*  981 */     SampleModel localSampleModel1 = paramRaster.getSampleModel();
/*  982 */     SampleModel localSampleModel2 = paramWritableRaster.getSampleModel();
/*      */     
/*  984 */     int k = paramRaster.getTransferType();
/*  985 */     int m = paramWritableRaster.getTransferType();
/*  986 */     int i; if ((k == 4) || (k == 5))
/*      */     {
/*  988 */       i = 1;
/*      */     } else
/*  990 */       i = 0;
/*      */     int j;
/*  992 */     if ((m == 4) || (m == 5))
/*      */     {
/*  994 */       j = 1;
/*      */     } else {
/*  996 */       j = 0;
/*      */     }
/*  998 */     int n = paramRaster.getWidth();
/*  999 */     int i1 = paramRaster.getHeight();
/* 1000 */     int i2 = paramRaster.getNumBands();
/* 1001 */     int i3 = paramWritableRaster.getNumBands();
/* 1002 */     float[] arrayOfFloat1 = null;
/* 1003 */     float[] arrayOfFloat2 = null;
/* 1004 */     if (i == 0) {
/* 1005 */       arrayOfFloat1 = new float[i2];
/* 1006 */       for (i4 = 0; i4 < i2; i4++) {
/* 1007 */         if (k == 2) {
/* 1008 */           arrayOfFloat1[i4] = ((this.srcMaxVals[i4] - this.srcMinVals[i4]) / 32767.0F);
/*      */         }
/*      */         else
/*      */         {
/* 1012 */           arrayOfFloat1[i4] = ((this.srcMaxVals[i4] - this.srcMinVals[i4]) / ((1 << localSampleModel1.getSampleSize(i4)) - 1));
/*      */         }
/*      */       }
/*      */     }
/* 1016 */     if (j == 0) {
/* 1017 */       arrayOfFloat2 = new float[i3];
/* 1018 */       for (i4 = 0; i4 < i3; i4++) {
/* 1019 */         if (m == 2) {
/* 1020 */           arrayOfFloat2[i4] = (32767.0F / (this.dstMaxVals[i4] - this.dstMinVals[i4]));
/*      */         }
/*      */         else
/*      */         {
/* 1024 */           arrayOfFloat2[i4] = (((1 << localSampleModel2.getSampleSize(i4)) - 1) / (this.dstMaxVals[i4] - this.dstMinVals[i4]));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1029 */     int i4 = paramRaster.getMinY();
/* 1030 */     int i5 = paramWritableRaster.getMinY();
/*      */     
/*      */ 
/* 1033 */     float[] arrayOfFloat3 = new float[i2];
/*      */     
/* 1035 */     ColorSpace localColorSpace1 = this.CSList[0];
/* 1036 */     ColorSpace localColorSpace2 = this.CSList[1];
/*      */     
/* 1038 */     for (int i8 = 0; i8 < i1; i5++)
/*      */     {
/* 1040 */       int i6 = paramRaster.getMinX();
/* 1041 */       int i7 = paramWritableRaster.getMinX();
/* 1042 */       for (int i9 = 0; i9 < n; i7++) { float f;
/* 1043 */         for (int i10 = 0; i10 < i2; i10++) {
/* 1044 */           f = paramRaster.getSampleFloat(i6, i4, i10);
/* 1045 */           if (i == 0) {
/* 1046 */             f = f * arrayOfFloat1[i10] + this.srcMinVals[i10];
/*      */           }
/* 1048 */           arrayOfFloat3[i10] = f;
/*      */         }
/* 1050 */         float[] arrayOfFloat4 = localColorSpace1.toCIEXYZ(arrayOfFloat3);
/* 1051 */         arrayOfFloat4 = localColorSpace2.fromCIEXYZ(arrayOfFloat4);
/* 1052 */         for (i10 = 0; i10 < i3; i10++) {
/* 1053 */           f = arrayOfFloat4[i10];
/* 1054 */           if (j == 0) {
/* 1055 */             f = (f - this.dstMinVals[i10]) * arrayOfFloat2[i10];
/*      */           }
/* 1057 */           paramWritableRaster.setSample(i7, i5, i10, f);
/*      */         }
/* 1042 */         i9++;i6++;
/*      */       }
/* 1038 */       i8++;i4++;
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
/* 1061 */     return paramWritableRaster;
/*      */   }
/*      */   
/*      */   private void getMinMaxValsFromProfiles(ICC_Profile paramICC_Profile1, ICC_Profile paramICC_Profile2)
/*      */   {
/* 1066 */     int i = paramICC_Profile1.getColorSpaceType();
/* 1067 */     int j = paramICC_Profile1.getNumComponents();
/* 1068 */     this.srcMinVals = new float[j];
/* 1069 */     this.srcMaxVals = new float[j];
/* 1070 */     setMinMax(i, j, this.srcMinVals, this.srcMaxVals);
/* 1071 */     i = paramICC_Profile2.getColorSpaceType();
/* 1072 */     j = paramICC_Profile2.getNumComponents();
/* 1073 */     this.dstMinVals = new float[j];
/* 1074 */     this.dstMaxVals = new float[j];
/* 1075 */     setMinMax(i, j, this.dstMinVals, this.dstMaxVals);
/*      */   }
/*      */   
/*      */   private void setMinMax(int paramInt1, int paramInt2, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2) {
/* 1079 */     if (paramInt1 == 1) {
/* 1080 */       paramArrayOfFloat1[0] = 0.0F;
/* 1081 */       paramArrayOfFloat2[0] = 100.0F;
/* 1082 */       paramArrayOfFloat1[1] = -128.0F;
/* 1083 */       paramArrayOfFloat2[1] = 127.0F;
/* 1084 */       paramArrayOfFloat1[2] = -128.0F;
/* 1085 */       paramArrayOfFloat2[2] = 127.0F;
/* 1086 */     } else if (paramInt1 == 0) {
/* 1087 */       paramArrayOfFloat1[0] = (paramArrayOfFloat1[1] = paramArrayOfFloat1[2] = 0.0F);
/* 1088 */       paramArrayOfFloat2[0] = (paramArrayOfFloat2[1] = paramArrayOfFloat2[2] = 1.9999695F);
/*      */     } else {
/* 1090 */       for (int i = 0; i < paramInt2; i++) {
/* 1091 */         paramArrayOfFloat1[i] = 0.0F;
/* 1092 */         paramArrayOfFloat2[i] = 1.0F;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void getMinMaxValsFromColorSpaces(ColorSpace paramColorSpace1, ColorSpace paramColorSpace2)
/*      */   {
/* 1099 */     int i = paramColorSpace1.getNumComponents();
/* 1100 */     this.srcMinVals = new float[i];
/* 1101 */     this.srcMaxVals = new float[i];
/* 1102 */     for (int j = 0; j < i; j++) {
/* 1103 */       this.srcMinVals[j] = paramColorSpace1.getMinValue(j);
/* 1104 */       this.srcMaxVals[j] = paramColorSpace1.getMaxValue(j);
/*      */     }
/* 1106 */     i = paramColorSpace2.getNumComponents();
/* 1107 */     this.dstMinVals = new float[i];
/* 1108 */     this.dstMaxVals = new float[i];
/* 1109 */     for (j = 0; j < i; j++) {
/* 1110 */       this.dstMinVals[j] = paramColorSpace2.getMinValue(j);
/* 1111 */       this.dstMaxVals[j] = paramColorSpace2.getMaxValue(j);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/ColorConvertOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */