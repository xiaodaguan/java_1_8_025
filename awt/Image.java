/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.AreaAveragingScaleFilter;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.ImageFilter;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.ReplicateScaleFilter;
/*     */ import sun.awt.image.SurfaceManager;
/*     */ import sun.awt.image.SurfaceManager.ImageAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Image
/*     */ {
/*  53 */   private static ImageCapabilities defaultImageCaps = new ImageCapabilities(false);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  63 */   protected float accelerationPriority = 0.5F;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 137 */   public static final Object UndefinedProperty = new Object();
/*     */   
/*     */   public static final int SCALE_DEFAULT = 1;
/*     */   
/*     */   public static final int SCALE_FAST = 2;
/*     */   
/*     */   public static final int SCALE_SMOOTH = 4;
/*     */   
/*     */   public static final int SCALE_REPLICATE = 8;
/*     */   
/*     */   public static final int SCALE_AREA_AVERAGING = 16;
/*     */   
/*     */   SurfaceManager surfaceManager;
/*     */   
/*     */ 
/*     */   public abstract int getWidth(ImageObserver paramImageObserver);
/*     */   
/*     */ 
/*     */   public abstract int getHeight(ImageObserver paramImageObserver);
/*     */   
/*     */ 
/*     */   public abstract ImageProducer getSource();
/*     */   
/*     */ 
/*     */   public abstract Graphics getGraphics();
/*     */   
/*     */ 
/*     */   public abstract Object getProperty(String paramString, ImageObserver paramImageObserver);
/*     */   
/*     */ 
/*     */   public Image getScaledInstance(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*     */     Object localObject;
/*     */     
/* 171 */     if ((paramInt3 & 0x14) != 0) {
/* 172 */       localObject = new AreaAveragingScaleFilter(paramInt1, paramInt2);
/*     */     } else {
/* 174 */       localObject = new ReplicateScaleFilter(paramInt1, paramInt2);
/*     */     }
/*     */     
/* 177 */     FilteredImageSource localFilteredImageSource = new FilteredImageSource(getSource(), (ImageFilter)localObject);
/* 178 */     return Toolkit.getDefaultToolkit().createImage(localFilteredImageSource);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */   {
/* 260 */     if (this.surfaceManager != null) {
/* 261 */       this.surfaceManager.flush();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ImageCapabilities getCapabilities(GraphicsConfiguration paramGraphicsConfiguration)
/*     */   {
/* 288 */     if (this.surfaceManager != null) {
/* 289 */       return this.surfaceManager.getCapabilities(paramGraphicsConfiguration);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 297 */     return defaultImageCaps;
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
/*     */   public void setAccelerationPriority(float paramFloat)
/*     */   {
/* 321 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 322 */       throw new IllegalArgumentException("Priority must be a value between 0 and 1, inclusive");
/*     */     }
/*     */     
/* 325 */     this.accelerationPriority = paramFloat;
/* 326 */     if (this.surfaceManager != null) {
/* 327 */       this.surfaceManager.setAccelerationPriority(this.accelerationPriority);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getAccelerationPriority()
/*     */   {
/* 339 */     return this.accelerationPriority;
/*     */   }
/*     */   
/*     */ 
/*     */   static
/*     */   {
/* 345 */     SurfaceManager.setImageAccessor(new SurfaceManager.ImageAccessor() {
/*     */       public SurfaceManager getSurfaceManager(Image paramAnonymousImage) {
/* 347 */         return paramAnonymousImage.surfaceManager;
/*     */       }
/*     */       
/* 350 */       public void setSurfaceManager(Image paramAnonymousImage, SurfaceManager paramAnonymousSurfaceManager) { paramAnonymousImage.surfaceManager = paramAnonymousSurfaceManager; }
/*     */     });
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Image.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */