/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.VolatileImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GraphicsConfiguration
/*     */ {
/*     */   private static BufferCapabilities defaultBufferCaps;
/*     */   private static ImageCapabilities defaultImageCaps;
/*     */   
/*     */   public abstract GraphicsDevice getDevice();
/*     */   
/*     */   public BufferedImage createCompatibleImage(int paramInt1, int paramInt2)
/*     */   {
/* 148 */     ColorModel localColorModel = getColorModel();
/*     */     
/* 150 */     WritableRaster localWritableRaster = localColorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */     
/* 152 */     return new BufferedImage(localColorModel, localWritableRaster, localColorModel.isAlphaPremultiplied(), null);
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
/*     */   public BufferedImage createCompatibleImage(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 177 */     if (getColorModel().getTransparency() == paramInt3) {
/* 178 */       return createCompatibleImage(paramInt1, paramInt2);
/*     */     }
/*     */     
/* 181 */     ColorModel localColorModel = getColorModel(paramInt3);
/* 182 */     if (localColorModel == null) {
/* 183 */       throw new IllegalArgumentException("Unknown transparency: " + paramInt3);
/*     */     }
/*     */     
/* 186 */     WritableRaster localWritableRaster = localColorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/* 187 */     return new BufferedImage(localColorModel, localWritableRaster, localColorModel.isAlphaPremultiplied(), null);
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
/*     */   public VolatileImage createCompatibleVolatileImage(int paramInt1, int paramInt2)
/*     */   {
/* 206 */     VolatileImage localVolatileImage = null;
/*     */     try {
/* 208 */       localVolatileImage = createCompatibleVolatileImage(paramInt1, paramInt2, null, 1);
/*     */     }
/*     */     catch (AWTException localAWTException)
/*     */     {
/* 212 */       if (!$assertionsDisabled) throw new AssertionError();
/*     */     }
/* 214 */     return localVolatileImage;
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
/*     */   public VolatileImage createCompatibleVolatileImage(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 239 */     VolatileImage localVolatileImage = null;
/*     */     try {
/* 241 */       localVolatileImage = createCompatibleVolatileImage(paramInt1, paramInt2, null, paramInt3);
/*     */     }
/*     */     catch (AWTException localAWTException) {
/* 244 */       if (!$assertionsDisabled) throw new AssertionError();
/*     */     }
/* 246 */     return localVolatileImage;
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
/*     */   public VolatileImage createCompatibleVolatileImage(int paramInt1, int paramInt2, ImageCapabilities paramImageCapabilities)
/*     */     throws AWTException
/*     */   {
/* 273 */     return createCompatibleVolatileImage(paramInt1, paramInt2, paramImageCapabilities, 1);
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
/*     */   public VolatileImage createCompatibleVolatileImage(int paramInt1, int paramInt2, ImageCapabilities paramImageCapabilities, int paramInt3)
/*     */     throws AWTException
/*     */   {
/* 307 */     SunVolatileImage localSunVolatileImage = new SunVolatileImage(this, paramInt1, paramInt2, paramInt3, paramImageCapabilities);
/*     */     
/* 309 */     if ((paramImageCapabilities != null) && (paramImageCapabilities.isAccelerated()) && 
/* 310 */       (!localSunVolatileImage.getCapabilities().isAccelerated()))
/*     */     {
/* 312 */       throw new AWTException("Supplied image capabilities could not be met by this graphics configuration.");
/*     */     }
/*     */     
/* 315 */     return localSunVolatileImage;
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
/*     */   public abstract ColorModel getColorModel();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ColorModel getColorModel(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract AffineTransform getDefaultTransform();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract AffineTransform getNormalizingTransform();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Rectangle getBounds();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class DefaultBufferCapabilities
/*     */     extends BufferCapabilities
/*     */   {
/*     */     public DefaultBufferCapabilities(ImageCapabilities paramImageCapabilities)
/*     */     {
/* 405 */       super(paramImageCapabilities, null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BufferCapabilities getBufferCapabilities()
/*     */   {
/* 417 */     if (defaultBufferCaps == null)
/*     */     {
/* 419 */       defaultBufferCaps = new DefaultBufferCapabilities(getImageCapabilities());
/*     */     }
/* 421 */     return defaultBufferCaps;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ImageCapabilities getImageCapabilities()
/*     */   {
/* 432 */     if (defaultImageCaps == null) {
/* 433 */       defaultImageCaps = new ImageCapabilities(false);
/*     */     }
/* 435 */     return defaultImageCaps;
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
/*     */   public boolean isTranslucencyCapable()
/*     */   {
/* 452 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/GraphicsConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */