/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.awt.peer.RobotPeer;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import sun.awt.ComponentFactory;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ import sun.security.util.SecurityConstants.AWT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Robot
/*     */ {
/*     */   private static final int MAX_DELAY = 60000;
/*     */   private RobotPeer peer;
/*  71 */   private boolean isAutoWaitForIdle = false;
/*  72 */   private int autoDelay = 0;
/*  73 */   private static int LEGAL_BUTTON_MASK = 0;
/*     */   
/*  75 */   private DirectColorModel screenCapCM = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Robot()
/*     */     throws AWTException
/*     */   {
/*  90 */     if (GraphicsEnvironment.isHeadless()) {
/*  91 */       throw new AWTException("headless environment");
/*     */     }
/*  93 */     init(GraphicsEnvironment.getLocalGraphicsEnvironment()
/*  94 */       .getDefaultScreenDevice());
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
/*     */   public Robot(GraphicsDevice paramGraphicsDevice)
/*     */     throws AWTException
/*     */   {
/* 126 */     checkIsScreenDevice(paramGraphicsDevice);
/* 127 */     init(paramGraphicsDevice);
/*     */   }
/*     */   
/*     */   private void init(GraphicsDevice paramGraphicsDevice) throws AWTException {
/* 131 */     checkRobotAllowed();
/* 132 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 133 */     if ((localToolkit instanceof ComponentFactory)) {
/* 134 */       this.peer = ((ComponentFactory)localToolkit).createRobot(this, paramGraphicsDevice);
/* 135 */       this.disposer = new RobotDisposer(this.peer);
/* 136 */       Disposer.addRecord(this.anchor, this.disposer);
/*     */     }
/* 138 */     initLegalButtonMask();
/*     */   }
/*     */   
/*     */   private static synchronized void initLegalButtonMask() {
/* 142 */     if (LEGAL_BUTTON_MASK != 0) { return;
/*     */     }
/* 144 */     int i = 0;
/* 145 */     if ((Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled()) && 
/* 146 */       ((Toolkit.getDefaultToolkit() instanceof SunToolkit))) {
/* 147 */       int j = ((SunToolkit)Toolkit.getDefaultToolkit()).getNumberOfButtons();
/* 148 */       for (int k = 0; k < j; k++) {
/* 149 */         i |= InputEvent.getMaskForButton(k + 1);
/*     */       }
/*     */     }
/*     */     
/* 153 */     i |= 0x1C1C;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 159 */     LEGAL_BUTTON_MASK = i;
/*     */   }
/*     */   
/*     */   private void checkRobotAllowed()
/*     */   {
/* 164 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 165 */     if (localSecurityManager != null) {
/* 166 */       localSecurityManager.checkPermission(SecurityConstants.AWT.CREATE_ROBOT_PERMISSION);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkIsScreenDevice(GraphicsDevice paramGraphicsDevice)
/*     */   {
/* 172 */     if ((paramGraphicsDevice == null) || (paramGraphicsDevice.getType() != 0)) {
/* 173 */       throw new IllegalArgumentException("not a valid screen device");
/*     */     }
/*     */   }
/*     */   
/* 177 */   private transient Object anchor = new Object();
/*     */   private transient RobotDisposer disposer;
/*     */   
/*     */   static class RobotDisposer implements DisposerRecord { private final RobotPeer peer;
/*     */     
/* 182 */     public RobotDisposer(RobotPeer paramRobotPeer) { this.peer = paramRobotPeer; }
/*     */     
/*     */     public void dispose() {
/* 185 */       if (this.peer != null) {
/* 186 */         this.peer.dispose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mouseMove(int paramInt1, int paramInt2)
/*     */   {
/* 199 */     this.peer.mouseMove(paramInt1, paramInt2);
/* 200 */     afterEvent();
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
/*     */   public synchronized void mousePress(int paramInt)
/*     */   {
/* 256 */     checkButtonsArgument(paramInt);
/* 257 */     this.peer.mousePress(paramInt);
/* 258 */     afterEvent();
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
/*     */   public synchronized void mouseRelease(int paramInt)
/*     */   {
/* 313 */     checkButtonsArgument(paramInt);
/* 314 */     this.peer.mouseRelease(paramInt);
/* 315 */     afterEvent();
/*     */   }
/*     */   
/*     */   private void checkButtonsArgument(int paramInt) {
/* 319 */     if ((paramInt | LEGAL_BUTTON_MASK) != LEGAL_BUTTON_MASK) {
/* 320 */       throw new IllegalArgumentException("Invalid combination of button flags");
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
/*     */   public synchronized void mouseWheel(int paramInt)
/*     */   {
/* 334 */     this.peer.mouseWheel(paramInt);
/* 335 */     afterEvent();
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
/*     */   public synchronized void keyPress(int paramInt)
/*     */   {
/* 353 */     checkKeycodeArgument(paramInt);
/* 354 */     this.peer.keyPress(paramInt);
/* 355 */     afterEvent();
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
/*     */   public synchronized void keyRelease(int paramInt)
/*     */   {
/* 372 */     checkKeycodeArgument(paramInt);
/* 373 */     this.peer.keyRelease(paramInt);
/* 374 */     afterEvent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void checkKeycodeArgument(int paramInt)
/*     */   {
/* 382 */     if (paramInt == 0) {
/* 383 */       throw new IllegalArgumentException("Invalid key code");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Color getPixelColor(int paramInt1, int paramInt2)
/*     */   {
/* 394 */     Color localColor = new Color(this.peer.getRGBPixel(paramInt1, paramInt2));
/* 395 */     return localColor;
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
/*     */   public synchronized BufferedImage createScreenCapture(Rectangle paramRectangle)
/*     */   {
/* 409 */     checkScreenCaptureAllowed();
/*     */     
/* 411 */     checkValidRect(paramRectangle);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 417 */     if (this.screenCapCM == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 424 */       this.screenCapCM = new DirectColorModel(24, 16711680, 65280, 255);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 432 */     Toolkit.getDefaultToolkit().sync();
/*     */     
/*     */ 
/* 435 */     int[] arrayOfInt2 = new int[3];
/*     */     
/* 437 */     int[] arrayOfInt1 = this.peer.getRGBPixels(paramRectangle);
/* 438 */     DataBufferInt localDataBufferInt = new DataBufferInt(arrayOfInt1, arrayOfInt1.length);
/*     */     
/* 440 */     arrayOfInt2[0] = this.screenCapCM.getRedMask();
/* 441 */     arrayOfInt2[1] = this.screenCapCM.getGreenMask();
/* 442 */     arrayOfInt2[2] = this.screenCapCM.getBlueMask();
/*     */     
/* 444 */     WritableRaster localWritableRaster = Raster.createPackedRaster(localDataBufferInt, paramRectangle.width, paramRectangle.height, paramRectangle.width, arrayOfInt2, null);
/* 445 */     SunWritableRaster.makeTrackable(localDataBufferInt);
/*     */     
/* 447 */     BufferedImage localBufferedImage = new BufferedImage(this.screenCapCM, localWritableRaster, false, null);
/*     */     
/* 449 */     return localBufferedImage;
/*     */   }
/*     */   
/*     */   private static void checkValidRect(Rectangle paramRectangle) {
/* 453 */     if ((paramRectangle.width <= 0) || (paramRectangle.height <= 0)) {
/* 454 */       throw new IllegalArgumentException("Rectangle width and height must be > 0");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkScreenCaptureAllowed() {
/* 459 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 460 */     if (localSecurityManager != null) {
/* 461 */       localSecurityManager.checkPermission(SecurityConstants.AWT.READ_DISPLAY_PIXELS_PERMISSION);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void afterEvent()
/*     */   {
/* 470 */     autoWaitForIdle();
/* 471 */     autoDelay();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized boolean isAutoWaitForIdle()
/*     */   {
/* 480 */     return this.isAutoWaitForIdle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setAutoWaitForIdle(boolean paramBoolean)
/*     */   {
/* 489 */     this.isAutoWaitForIdle = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void autoWaitForIdle()
/*     */   {
/* 496 */     if (this.isAutoWaitForIdle) {
/* 497 */       waitForIdle();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized int getAutoDelay()
/*     */   {
/* 505 */     return this.autoDelay;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setAutoDelay(int paramInt)
/*     */   {
/* 513 */     checkDelayArgument(paramInt);
/* 514 */     this.autoDelay = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void autoDelay()
/*     */   {
/* 521 */     delay(this.autoDelay);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void delay(int paramInt)
/*     */   {
/* 533 */     checkDelayArgument(paramInt);
/*     */     try {
/* 535 */       Thread.sleep(paramInt);
/*     */     } catch (InterruptedException localInterruptedException) {
/* 537 */       localInterruptedException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkDelayArgument(int paramInt) {
/* 542 */     if ((paramInt < 0) || (paramInt > 60000)) {
/* 543 */       throw new IllegalArgumentException("Delay must be to 0 to 60,000ms");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void waitForIdle()
/*     */   {
/* 552 */     checkNotDispatchThread();
/*     */     
/*     */     try
/*     */     {
/* 556 */       SunToolkit.flushPendingEvents();
/* 557 */       EventQueue.invokeAndWait(new Runnable()
/*     */       {
/*     */         public void run() {}
/*     */       });
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 563 */       System.err.println("Robot.waitForIdle, non-fatal exception caught:");
/* 564 */       localInterruptedException.printStackTrace();
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 566 */       System.err.println("Robot.waitForIdle, non-fatal exception caught:");
/* 567 */       localInvocationTargetException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkNotDispatchThread() {
/* 572 */     if (EventQueue.isDispatchThread()) {
/* 573 */       throw new IllegalThreadStateException("Cannot call method from the event dispatcher thread");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized String toString()
/*     */   {
/* 583 */     String str = "autoDelay = " + getAutoDelay() + ", " + "autoWaitForIdle = " + isAutoWaitForIdle();
/* 584 */     return getClass().getName() + "[ " + str + " ]";
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Robot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */