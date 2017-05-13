/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Float;
/*     */ import java.awt.geom.Rectangle2D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConvolveOp
/*     */   implements BufferedImageOp, RasterOp
/*     */ {
/*     */   Kernel kernel;
/*     */   int edgeHint;
/*     */   RenderingHints hints;
/*     */   public static final int EDGE_ZERO_FILL = 0;
/*     */   public static final int EDGE_NO_OP = 1;
/*     */   
/*     */   public ConvolveOp(Kernel paramKernel, int paramInt, RenderingHints paramRenderingHints)
/*     */   {
/* 102 */     this.kernel = paramKernel;
/* 103 */     this.edgeHint = paramInt;
/* 104 */     this.hints = paramRenderingHints;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConvolveOp(Kernel paramKernel)
/*     */   {
/* 115 */     this.kernel = paramKernel;
/* 116 */     this.edgeHint = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEdgeCondition()
/*     */   {
/* 126 */     return this.edgeHint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Kernel getKernel()
/*     */   {
/* 134 */     return (Kernel)this.kernel.clone();
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
/*     */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2)
/*     */   {
/* 157 */     if (paramBufferedImage1 == null) {
/* 158 */       throw new NullPointerException("src image is null");
/*     */     }
/* 160 */     if (paramBufferedImage1 == paramBufferedImage2) {
/* 161 */       throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */     }
/*     */     
/*     */ 
/* 165 */     int i = 0;
/* 166 */     ColorModel localColorModel1 = paramBufferedImage1.getColorModel();
/*     */     
/* 168 */     BufferedImage localBufferedImage = paramBufferedImage2;
/*     */     
/*     */     Object localObject1;
/* 171 */     if ((localColorModel1 instanceof IndexColorModel)) {
/* 172 */       localObject1 = (IndexColorModel)localColorModel1;
/* 173 */       paramBufferedImage1 = ((IndexColorModel)localObject1).convertToIntDiscrete(paramBufferedImage1.getRaster(), false);
/* 174 */       localColorModel1 = paramBufferedImage1.getColorModel();
/*     */     }
/*     */     ColorModel localColorModel2;
/* 177 */     if (paramBufferedImage2 == null) {
/* 178 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 179 */       localColorModel2 = localColorModel1;
/* 180 */       localBufferedImage = paramBufferedImage2;
/*     */     }
/*     */     else {
/* 183 */       localColorModel2 = paramBufferedImage2.getColorModel();
/*     */       
/* 185 */       if (localColorModel1.getColorSpace().getType() != localColorModel2.getColorSpace().getType())
/*     */       {
/* 187 */         i = 1;
/* 188 */         paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 189 */         localColorModel2 = paramBufferedImage2.getColorModel();
/*     */       }
/* 191 */       else if ((localColorModel2 instanceof IndexColorModel)) {
/* 192 */         paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 193 */         localColorModel2 = paramBufferedImage2.getColorModel();
/*     */       }
/*     */     }
/*     */     
/* 197 */     if (ImagingLib.filter(this, paramBufferedImage1, paramBufferedImage2) == null) {
/* 198 */       throw new ImagingOpException("Unable to convolve src image");
/*     */     }
/*     */     
/* 201 */     if (i != 0) {
/* 202 */       localObject1 = new ColorConvertOp(this.hints);
/* 203 */       ((ColorConvertOp)localObject1).filter(paramBufferedImage2, localBufferedImage);
/*     */     }
/* 205 */     else if (localBufferedImage != paramBufferedImage2) {
/* 206 */       localObject1 = localBufferedImage.createGraphics();
/*     */       try {
/* 208 */         ((Graphics2D)localObject1).drawImage(paramBufferedImage2, 0, 0, null);
/*     */       } finally {
/* 210 */         ((Graphics2D)localObject1).dispose();
/*     */       }
/*     */     }
/*     */     
/* 214 */     return localBufferedImage;
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
/*     */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster)
/*     */   {
/* 236 */     if (paramWritableRaster == null) {
/* 237 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     } else {
/* 239 */       if (paramRaster == paramWritableRaster) {
/* 240 */         throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */       }
/*     */       
/* 243 */       if (paramRaster.getNumBands() != paramWritableRaster.getNumBands()) {
/* 244 */         throw new ImagingOpException("Different number of bands in src  and dst Rasters");
/*     */       }
/*     */     }
/*     */     
/* 248 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) == null) {
/* 249 */       throw new ImagingOpException("Unable to convolve src image");
/*     */     }
/*     */     
/* 252 */     return paramWritableRaster;
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
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel)
/*     */   {
/* 267 */     int i = paramBufferedImage.getWidth();
/* 268 */     int j = paramBufferedImage.getHeight();
/*     */     
/* 270 */     WritableRaster localWritableRaster = null;
/*     */     
/* 272 */     if (paramColorModel == null) {
/* 273 */       paramColorModel = paramBufferedImage.getColorModel();
/*     */       
/* 275 */       if ((paramColorModel instanceof IndexColorModel)) {
/* 276 */         paramColorModel = ColorModel.getRGBdefault();
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 281 */         localWritableRaster = paramBufferedImage.getData().createCompatibleWritableRaster(i, j);
/*     */       }
/*     */     }
/*     */     
/* 285 */     if (localWritableRaster == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 292 */       localWritableRaster = paramColorModel.createCompatibleWritableRaster(i, j);
/*     */     }
/*     */     
/*     */ 
/* 296 */     BufferedImage localBufferedImage = new BufferedImage(paramColorModel, localWritableRaster, paramColorModel.isAlphaPremultiplied(), null);
/*     */     
/* 298 */     return localBufferedImage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster)
/*     */   {
/* 306 */     return paramRaster.createCompatibleWritableRaster();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage)
/*     */   {
/* 315 */     return getBounds2D(paramBufferedImage.getRaster());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster)
/*     */   {
/* 324 */     return paramRaster.getBounds();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 334 */     if (paramPoint2D2 == null) {
/* 335 */       paramPoint2D2 = new Point2D.Float();
/*     */     }
/* 337 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/*     */     
/* 339 */     return paramPoint2D2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final RenderingHints getRenderingHints()
/*     */   {
/* 346 */     return this.hints;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/ConvolveOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */