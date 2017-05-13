/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.Rectangle2D.Float;
/*     */ import sun.awt.image.ImagingLib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AffineTransformOp
/*     */   implements BufferedImageOp, RasterOp
/*     */ {
/*     */   private AffineTransform xform;
/*     */   RenderingHints hints;
/*     */   public static final int TYPE_NEAREST_NEIGHBOR = 1;
/*     */   public static final int TYPE_BILINEAR = 2;
/*     */   public static final int TYPE_BICUBIC = 3;
/*  85 */   int interpolationType = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AffineTransformOp(AffineTransform paramAffineTransform, RenderingHints paramRenderingHints)
/*     */   {
/* 108 */     validateTransform(paramAffineTransform);
/* 109 */     this.xform = ((AffineTransform)paramAffineTransform.clone());
/* 110 */     this.hints = paramRenderingHints;
/*     */     
/* 112 */     if (paramRenderingHints != null) {
/* 113 */       Object localObject = paramRenderingHints.get(RenderingHints.KEY_INTERPOLATION);
/* 114 */       if (localObject == null) {
/* 115 */         localObject = paramRenderingHints.get(RenderingHints.KEY_RENDERING);
/* 116 */         if (localObject == RenderingHints.VALUE_RENDER_SPEED) {
/* 117 */           this.interpolationType = 1;
/*     */         }
/* 119 */         else if (localObject == RenderingHints.VALUE_RENDER_QUALITY) {
/* 120 */           this.interpolationType = 2;
/*     */         }
/*     */       }
/* 123 */       else if (localObject == RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) {
/* 124 */         this.interpolationType = 1;
/*     */       }
/* 126 */       else if (localObject == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
/* 127 */         this.interpolationType = 2;
/*     */       }
/* 129 */       else if (localObject == RenderingHints.VALUE_INTERPOLATION_BICUBIC) {
/* 130 */         this.interpolationType = 3;
/*     */       }
/*     */     }
/*     */     else {
/* 134 */       this.interpolationType = 1;
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
/*     */ 
/*     */ 
/*     */   public AffineTransformOp(AffineTransform paramAffineTransform, int paramInt)
/*     */   {
/* 151 */     validateTransform(paramAffineTransform);
/* 152 */     this.xform = ((AffineTransform)paramAffineTransform.clone());
/* 153 */     switch (paramInt) {
/*     */     case 1: 
/*     */     case 2: 
/*     */     case 3: 
/*     */       break;
/*     */     default: 
/* 159 */       throw new IllegalArgumentException("Unknown interpolation type: " + paramInt);
/*     */     }
/*     */     
/* 162 */     this.interpolationType = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getInterpolationType()
/*     */   {
/* 173 */     return this.interpolationType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2)
/*     */   {
/* 213 */     if (paramBufferedImage1 == null) {
/* 214 */       throw new NullPointerException("src image is null");
/*     */     }
/* 216 */     if (paramBufferedImage1 == paramBufferedImage2) {
/* 217 */       throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */     }
/*     */     
/*     */ 
/* 221 */     int i = 0;
/* 222 */     ColorModel localColorModel1 = paramBufferedImage1.getColorModel();
/*     */     
/* 224 */     BufferedImage localBufferedImage1 = paramBufferedImage2;
/*     */     ColorModel localColorModel2;
/* 226 */     if (paramBufferedImage2 == null) {
/* 227 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 228 */       localColorModel2 = localColorModel1;
/* 229 */       localBufferedImage1 = paramBufferedImage2;
/*     */     }
/*     */     else {
/* 232 */       localColorModel2 = paramBufferedImage2.getColorModel();
/*     */       
/* 234 */       if (localColorModel1.getColorSpace().getType() != localColorModel2.getColorSpace().getType())
/*     */       {
/* 236 */         int j = this.xform.getType();
/* 237 */         int k = (j & (0x18 | 0x20)) != 0 ? 1 : 0;
/*     */         
/*     */         Object localObject2;
/*     */         
/* 241 */         if ((k == 0) && (j != 1) && (j != 0))
/*     */         {
/* 243 */           localObject2 = new double[4];
/* 244 */           this.xform.getMatrix((double[])localObject2);
/*     */           
/*     */ 
/* 247 */           k = (localObject2[0] != (int)localObject2[0]) || (localObject2[3] != (int)localObject2[3]) ? 1 : 0;
/*     */         }
/*     */         
/* 250 */         if ((k != 0) && 
/* 251 */           (localColorModel1.getTransparency() == 1))
/*     */         {
/*     */ 
/* 254 */           localObject2 = new ColorConvertOp(this.hints);
/* 255 */           BufferedImage localBufferedImage2 = null;
/* 256 */           int m = paramBufferedImage1.getWidth();
/* 257 */           int n = paramBufferedImage1.getHeight();
/* 258 */           if (localColorModel2.getTransparency() == 1) {
/* 259 */             localBufferedImage2 = new BufferedImage(m, n, 2);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 264 */             WritableRaster localWritableRaster = localColorModel2.createCompatibleWritableRaster(m, n);
/*     */             
/* 266 */             localBufferedImage2 = new BufferedImage(localColorModel2, localWritableRaster, localColorModel2.isAlphaPremultiplied(), null);
/*     */           }
/*     */           
/* 269 */           paramBufferedImage1 = ((ColorConvertOp)localObject2).filter(paramBufferedImage1, localBufferedImage2);
/*     */         }
/*     */         else {
/* 272 */           i = 1;
/* 273 */           paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 279 */     if ((this.interpolationType != 1) && 
/* 280 */       ((paramBufferedImage2.getColorModel() instanceof IndexColorModel))) {
/* 281 */       paramBufferedImage2 = new BufferedImage(paramBufferedImage2.getWidth(), paramBufferedImage2.getHeight(), 2);
/*     */     }
/*     */     
/* 284 */     if (ImagingLib.filter(this, paramBufferedImage1, paramBufferedImage2) == null) {
/* 285 */       throw new ImagingOpException("Unable to transform src image");
/*     */     }
/*     */     Object localObject1;
/* 288 */     if (i != 0) {
/* 289 */       localObject1 = new ColorConvertOp(this.hints);
/* 290 */       ((ColorConvertOp)localObject1).filter(paramBufferedImage2, localBufferedImage1);
/*     */     }
/* 292 */     else if (localBufferedImage1 != paramBufferedImage2) {
/* 293 */       localObject1 = localBufferedImage1.createGraphics();
/*     */       try {
/* 295 */         ((Graphics2D)localObject1).setComposite(AlphaComposite.Src);
/* 296 */         ((Graphics2D)localObject1).drawImage(paramBufferedImage2, 0, 0, null);
/*     */       } finally {
/* 298 */         ((Graphics2D)localObject1).dispose();
/*     */       }
/*     */     }
/*     */     
/* 302 */     return localBufferedImage1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster)
/*     */   {
/* 339 */     if (paramRaster == null) {
/* 340 */       throw new NullPointerException("src image is null");
/*     */     }
/* 342 */     if (paramWritableRaster == null) {
/* 343 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     }
/* 345 */     if (paramRaster == paramWritableRaster) {
/* 346 */       throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */     }
/*     */     
/* 349 */     if (paramRaster.getNumBands() != paramWritableRaster.getNumBands())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 354 */       throw new IllegalArgumentException("Number of src bands (" + paramRaster.getNumBands() + ") does not match number of " + " dst bands (" + paramWritableRaster.getNumBands() + ")");
/*     */     }
/*     */     
/* 357 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) == null) {
/* 358 */       throw new ImagingOpException("Unable to transform src image");
/*     */     }
/* 360 */     return paramWritableRaster;
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
/*     */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage)
/*     */   {
/* 375 */     return getBounds2D(paramBufferedImage.getRaster());
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
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster)
/*     */   {
/* 390 */     int i = paramRaster.getWidth();
/* 391 */     int j = paramRaster.getHeight();
/*     */     
/*     */ 
/* 394 */     float[] arrayOfFloat = { 0.0F, 0.0F, i, 0.0F, i, j, 0.0F, j };
/* 395 */     this.xform.transform(arrayOfFloat, 0, arrayOfFloat, 0, 4);
/*     */     
/*     */ 
/* 398 */     float f1 = arrayOfFloat[0];
/* 399 */     float f2 = arrayOfFloat[1];
/* 400 */     float f3 = arrayOfFloat[0];
/* 401 */     float f4 = arrayOfFloat[1];
/* 402 */     for (int k = 2; k < 8; k += 2) {
/* 403 */       if (arrayOfFloat[k] > f1) {
/* 404 */         f1 = arrayOfFloat[k];
/*     */       }
/* 406 */       else if (arrayOfFloat[k] < f3) {
/* 407 */         f3 = arrayOfFloat[k];
/*     */       }
/* 409 */       if (arrayOfFloat[(k + 1)] > f2) {
/* 410 */         f2 = arrayOfFloat[(k + 1)];
/*     */       }
/* 412 */       else if (arrayOfFloat[(k + 1)] < f4) {
/* 413 */         f4 = arrayOfFloat[(k + 1)];
/*     */       }
/*     */     }
/*     */     
/* 417 */     return new Rectangle2D.Float(f3, f4, f1 - f3, f2 - f4);
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
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel)
/*     */   {
/* 439 */     Rectangle localRectangle = getBounds2D(paramBufferedImage).getBounds();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 445 */     int i = localRectangle.x + localRectangle.width;
/* 446 */     int j = localRectangle.y + localRectangle.height;
/* 447 */     if (i <= 0) {
/* 448 */       throw new RasterFormatException("Transformed width (" + i + ") is less than or equal to 0.");
/*     */     }
/*     */     
/* 451 */     if (j <= 0) {
/* 452 */       throw new RasterFormatException("Transformed height (" + j + ") is less than or equal to 0.");
/*     */     }
/*     */     
/*     */     BufferedImage localBufferedImage;
/* 456 */     if (paramColorModel == null) {
/* 457 */       ColorModel localColorModel = paramBufferedImage.getColorModel();
/* 458 */       if ((this.interpolationType != 1) && (((localColorModel instanceof IndexColorModel)) || 
/*     */       
/* 460 */         (localColorModel.getTransparency() == 1)))
/*     */       {
/* 462 */         localBufferedImage = new BufferedImage(i, j, 2);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 468 */         localBufferedImage = new BufferedImage(localColorModel, paramBufferedImage.getRaster().createCompatibleWritableRaster(i, j), localColorModel.isAlphaPremultiplied(), null);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 474 */       localBufferedImage = new BufferedImage(paramColorModel, paramColorModel.createCompatibleWritableRaster(i, j), paramColorModel.isAlphaPremultiplied(), null);
/*     */     }
/*     */     
/* 477 */     return localBufferedImage;
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
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster)
/*     */   {
/* 490 */     Rectangle2D localRectangle2D = getBounds2D(paramRaster);
/*     */     
/* 492 */     return paramRaster.createCompatibleWritableRaster((int)localRectangle2D.getX(), 
/* 493 */       (int)localRectangle2D.getY(), 
/* 494 */       (int)localRectangle2D.getWidth(), 
/* 495 */       (int)localRectangle2D.getHeight());
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
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 511 */     return this.xform.transform(paramPoint2D1, paramPoint2D2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final AffineTransform getTransform()
/*     */   {
/* 520 */     return (AffineTransform)this.xform.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final RenderingHints getRenderingHints()
/*     */   {
/* 529 */     if (this.hints == null) {
/*     */       Object localObject;
/* 531 */       switch (this.interpolationType) {
/*     */       case 1: 
/* 533 */         localObject = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/* 534 */         break;
/*     */       case 2: 
/* 536 */         localObject = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/* 537 */         break;
/*     */       case 3: 
/* 539 */         localObject = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
/* 540 */         break;
/*     */       
/*     */       default: 
/* 543 */         throw new InternalError("Unknown interpolation type " + this.interpolationType);
/*     */       }
/*     */       
/*     */       
/* 547 */       this.hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, localObject);
/*     */     }
/*     */     
/* 550 */     return this.hints;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void validateTransform(AffineTransform paramAffineTransform)
/*     */   {
/* 557 */     if (Math.abs(paramAffineTransform.getDeterminant()) <= Double.MIN_VALUE) {
/* 558 */       throw new ImagingOpException("Unable to invert transform " + paramAffineTransform);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/AffineTransformOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */