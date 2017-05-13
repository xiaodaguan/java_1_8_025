/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GraphicsDevice
/*     */ {
/*     */   private Window fullScreenWindow;
/*     */   private AppContext fullScreenAppContext;
/*  85 */   private final Object fsAppContextLock = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Rectangle windowedModeBounds;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int TYPE_RASTER_SCREEN = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int TYPE_PRINTER = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int TYPE_IMAGE_BUFFER = 2;
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract int getType();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getIDstring();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract GraphicsConfiguration[] getConfigurations();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract GraphicsConfiguration getDefaultConfiguration();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum WindowTranslucency
/*     */   {
/* 129 */     PERPIXEL_TRANSPARENT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 135 */     TRANSLUCENT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 141 */     PERPIXEL_TRANSLUCENT;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private WindowTranslucency() {}
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
/*     */   public GraphicsConfiguration getBestConfiguration(GraphicsConfigTemplate paramGraphicsConfigTemplate)
/*     */   {
/* 206 */     GraphicsConfiguration[] arrayOfGraphicsConfiguration = getConfigurations();
/* 207 */     return paramGraphicsConfigTemplate.getBestConfiguration(arrayOfGraphicsConfiguration);
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
/*     */   public boolean isFullScreenSupported()
/*     */   {
/* 224 */     return false;
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
/*     */   public void setFullScreenWindow(Window paramWindow)
/*     */   {
/* 286 */     if (paramWindow != null) {
/* 287 */       if (paramWindow.getShape() != null) {
/* 288 */         paramWindow.setShape(null);
/*     */       }
/* 290 */       if (paramWindow.getOpacity() < 1.0F) {
/* 291 */         paramWindow.setOpacity(1.0F);
/*     */       }
/* 293 */       if (!paramWindow.isOpaque()) {
/* 294 */         localObject1 = paramWindow.getBackground();
/*     */         
/* 296 */         localObject1 = new Color(((Color)localObject1).getRed(), ((Color)localObject1).getGreen(), ((Color)localObject1).getBlue(), 255);
/* 297 */         paramWindow.setBackground((Color)localObject1);
/*     */       }
/*     */       
/* 300 */       Object localObject1 = paramWindow.getGraphicsConfiguration();
/* 301 */       if ((localObject1 != null) && (((GraphicsConfiguration)localObject1).getDevice() != this) && 
/* 302 */         (((GraphicsConfiguration)localObject1).getDevice().getFullScreenWindow() == paramWindow)) {
/* 303 */         ((GraphicsConfiguration)localObject1).getDevice().setFullScreenWindow(null);
/*     */       }
/*     */     }
/* 306 */     if ((this.fullScreenWindow != null) && (this.windowedModeBounds != null))
/*     */     {
/*     */ 
/* 309 */       if (this.windowedModeBounds.width == 0) this.windowedModeBounds.width = 1;
/* 310 */       if (this.windowedModeBounds.height == 0) this.windowedModeBounds.height = 1;
/* 311 */       this.fullScreenWindow.setBounds(this.windowedModeBounds);
/*     */     }
/*     */     
/* 314 */     synchronized (this.fsAppContextLock)
/*     */     {
/* 316 */       if (paramWindow == null) {
/* 317 */         this.fullScreenAppContext = null;
/*     */       } else {
/* 319 */         this.fullScreenAppContext = AppContext.getAppContext();
/*     */       }
/* 321 */       this.fullScreenWindow = paramWindow;
/*     */     }
/* 323 */     if (this.fullScreenWindow != null) {
/* 324 */       this.windowedModeBounds = this.fullScreenWindow.getBounds();
/*     */       
/*     */ 
/*     */ 
/* 328 */       ??? = getDefaultConfiguration();
/* 329 */       Rectangle localRectangle = ((GraphicsConfiguration)???).getBounds();
/* 330 */       if (SunToolkit.isDispatchThreadForAppContext(this.fullScreenWindow))
/*     */       {
/*     */ 
/*     */ 
/* 334 */         this.fullScreenWindow.setGraphicsConfiguration((GraphicsConfiguration)???);
/*     */       }
/* 336 */       this.fullScreenWindow.setBounds(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/*     */       
/* 338 */       this.fullScreenWindow.setVisible(true);
/* 339 */       this.fullScreenWindow.toFront();
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
/*     */   public Window getFullScreenWindow()
/*     */   {
/* 353 */     Window localWindow = null;
/* 354 */     synchronized (this.fsAppContextLock)
/*     */     {
/*     */ 
/* 357 */       if (this.fullScreenAppContext == AppContext.getAppContext()) {
/* 358 */         localWindow = this.fullScreenWindow;
/*     */       }
/*     */     }
/* 361 */     return localWindow;
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
/*     */   public boolean isDisplayChangeSupported()
/*     */   {
/* 379 */     return false;
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
/*     */   public void setDisplayMode(DisplayMode paramDisplayMode)
/*     */   {
/* 434 */     throw new UnsupportedOperationException("Cannot change display mode");
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
/*     */   public DisplayMode getDisplayMode()
/*     */   {
/* 450 */     GraphicsConfiguration localGraphicsConfiguration = getDefaultConfiguration();
/* 451 */     Rectangle localRectangle = localGraphicsConfiguration.getBounds();
/* 452 */     ColorModel localColorModel = localGraphicsConfiguration.getColorModel();
/* 453 */     return new DisplayMode(localRectangle.width, localRectangle.height, localColorModel.getPixelSize(), 0);
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
/*     */   public DisplayMode[] getDisplayModes()
/*     */   {
/* 468 */     return new DisplayMode[] { getDisplayMode() };
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
/*     */   public int getAvailableAcceleratedMemory()
/*     */   {
/* 500 */     return -1;
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
/*     */   public boolean isWindowTranslucencySupported(WindowTranslucency paramWindowTranslucency)
/*     */   {
/* 513 */     switch (paramWindowTranslucency) {
/*     */     case PERPIXEL_TRANSPARENT: 
/* 515 */       return isWindowShapingSupported();
/*     */     case TRANSLUCENT: 
/* 517 */       return isWindowOpacitySupported();
/*     */     case PERPIXEL_TRANSLUCENT: 
/* 519 */       return isWindowPerpixelTranslucencySupported();
/*     */     }
/* 521 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isWindowShapingSupported()
/*     */   {
/* 532 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 533 */     if (!(localToolkit instanceof SunToolkit)) {
/* 534 */       return false;
/*     */     }
/* 536 */     return ((SunToolkit)localToolkit).isWindowShapingSupported();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isWindowOpacitySupported()
/*     */   {
/* 547 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 548 */     if (!(localToolkit instanceof SunToolkit)) {
/* 549 */       return false;
/*     */     }
/* 551 */     return ((SunToolkit)localToolkit).isWindowOpacitySupported();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isWindowPerpixelTranslucencySupported()
/*     */   {
/* 563 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 564 */     if (!(localToolkit instanceof SunToolkit)) {
/* 565 */       return false;
/*     */     }
/* 567 */     if (!((SunToolkit)localToolkit).isWindowTranslucencySupported()) {
/* 568 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 572 */     return getTranslucencyCapableGC() != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   GraphicsConfiguration getTranslucencyCapableGC()
/*     */   {
/* 579 */     GraphicsConfiguration localGraphicsConfiguration = getDefaultConfiguration();
/* 580 */     if (localGraphicsConfiguration.isTranslucencyCapable()) {
/* 581 */       return localGraphicsConfiguration;
/*     */     }
/*     */     
/*     */ 
/* 585 */     GraphicsConfiguration[] arrayOfGraphicsConfiguration = getConfigurations();
/* 586 */     for (int i = 0; i < arrayOfGraphicsConfiguration.length; i++) {
/* 587 */       if (arrayOfGraphicsConfiguration[i].isTranslucencyCapable()) {
/* 588 */         return arrayOfGraphicsConfiguration[i];
/*     */       }
/*     */     }
/*     */     
/* 592 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/GraphicsDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */