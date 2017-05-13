/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ import sun.util.logging.PlatformLogger.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SplashScreen
/*     */ {
/*     */   private BufferedImage image;
/*     */   private final long splashPtr;
/*     */   
/*     */   SplashScreen(long paramLong)
/*     */   {
/* 100 */     this.splashPtr = paramLong;
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
/*     */   public static SplashScreen getSplashScreen()
/*     */   {
/* 115 */     synchronized (SplashScreen.class) {
/* 116 */       if (GraphicsEnvironment.isHeadless()) {
/* 117 */         throw new HeadlessException();
/*     */       }
/*     */       
/* 120 */       if ((!wasClosed) && (theInstance == null)) {
/* 121 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 124 */             System.loadLibrary("splashscreen");
/* 125 */             return null;
/*     */           }
/* 127 */         });
/* 128 */         long l = _getInstance();
/* 129 */         if ((l != 0L) && (_isVisible(l))) {
/* 130 */           theInstance = new SplashScreen(l);
/*     */         }
/*     */       }
/* 133 */       return theInstance;
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
/*     */   public void setImageURL(URL paramURL)
/*     */     throws NullPointerException, IOException, IllegalStateException
/*     */   {
/* 153 */     checkVisible();
/* 154 */     URLConnection localURLConnection = paramURL.openConnection();
/* 155 */     localURLConnection.connect();
/* 156 */     int i = localURLConnection.getContentLength();
/* 157 */     InputStream localInputStream = localURLConnection.getInputStream();
/* 158 */     byte[] arrayOfByte1 = new byte[i];
/* 159 */     int j = 0;
/*     */     for (;;)
/*     */     {
/* 162 */       int k = localInputStream.available();
/* 163 */       if (k <= 0)
/*     */       {
/*     */ 
/* 166 */         k = 1;
/*     */       }
/*     */       
/*     */ 
/* 170 */       if (j + k > i) {
/* 171 */         i = j * 2;
/* 172 */         if (j + k > i) {
/* 173 */           i = k + j;
/*     */         }
/* 175 */         byte[] arrayOfByte2 = arrayOfByte1;
/* 176 */         arrayOfByte1 = new byte[i];
/* 177 */         System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, j);
/*     */       }
/*     */       
/* 180 */       int m = localInputStream.read(arrayOfByte1, j, k);
/* 181 */       if (m < 0) {
/*     */         break;
/*     */       }
/* 184 */       j += m;
/*     */     }
/* 186 */     synchronized (SplashScreen.class) {
/* 187 */       checkVisible();
/* 188 */       if (!_setImageData(this.splashPtr, arrayOfByte1)) {
/* 189 */         throw new IOException("Bad image format or i/o error when loading image");
/*     */       }
/* 191 */       this.imageURL = paramURL;
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkVisible() {
/* 196 */     if (!isVisible()) {
/* 197 */       throw new IllegalStateException("no splash screen available");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public URL getImageURL()
/*     */     throws IllegalStateException
/*     */   {
/* 207 */     synchronized (SplashScreen.class) {
/* 208 */       checkVisible();
/* 209 */       if (this.imageURL == null) {
/*     */         try {
/* 211 */           String str1 = _getImageFileName(this.splashPtr);
/* 212 */           String str2 = _getImageJarName(this.splashPtr);
/* 213 */           if (str1 != null) {
/* 214 */             if (str2 != null) {
/* 215 */               this.imageURL = new URL("jar:" + new File(str2).toURL().toString() + "!/" + str1);
/*     */             } else {
/* 217 */               this.imageURL = new File(str1).toURL();
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (MalformedURLException localMalformedURLException) {
/* 222 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 223 */             log.fine("MalformedURLException caught in the getImageURL() method", localMalformedURLException);
/*     */           }
/*     */         }
/*     */       }
/* 227 */       return this.imageURL;
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
/*     */   public Rectangle getBounds()
/*     */     throws IllegalStateException
/*     */   {
/* 246 */     synchronized (SplashScreen.class) {
/* 247 */       checkVisible();
/* 248 */       return _getBounds(this.splashPtr);
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
/*     */   public Dimension getSize()
/*     */     throws IllegalStateException
/*     */   {
/* 267 */     return getBounds().getSize();
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
/*     */   public Graphics2D createGraphics()
/*     */     throws IllegalStateException
/*     */   {
/* 288 */     synchronized (SplashScreen.class) {
/* 289 */       if (this.image == null) {
/* 290 */         Dimension localDimension = getSize();
/* 291 */         this.image = new BufferedImage(localDimension.width, localDimension.height, 2);
/*     */       }
/* 293 */       return this.image.createGraphics();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update()
/*     */     throws IllegalStateException
/*     */   {
/*     */     BufferedImage localBufferedImage;
/*     */     
/*     */ 
/* 306 */     synchronized (SplashScreen.class) {
/* 307 */       checkVisible();
/* 308 */       localBufferedImage = this.image;
/*     */     }
/* 310 */     if (localBufferedImage == null) {
/* 311 */       throw new IllegalStateException("no overlay image available");
/*     */     }
/* 313 */     ??? = localBufferedImage.getRaster().getDataBuffer();
/* 314 */     if (!(??? instanceof DataBufferInt)) {
/* 315 */       throw new AssertionError("Overlay image DataBuffer is of invalid type == " + ???.getClass().getName());
/*     */     }
/* 317 */     int i = ((DataBuffer)???).getNumBanks();
/* 318 */     if (i != 1) {
/* 319 */       throw new AssertionError("Invalid number of banks ==" + i + " in overlay image DataBuffer");
/*     */     }
/* 321 */     if (!(localBufferedImage.getSampleModel() instanceof SinglePixelPackedSampleModel)) {
/* 322 */       throw new AssertionError("Overlay image has invalid sample model == " + localBufferedImage.getSampleModel().getClass().getName());
/*     */     }
/* 324 */     SinglePixelPackedSampleModel localSinglePixelPackedSampleModel = (SinglePixelPackedSampleModel)localBufferedImage.getSampleModel();
/* 325 */     int j = localSinglePixelPackedSampleModel.getScanlineStride();
/* 326 */     Rectangle localRectangle = localBufferedImage.getRaster().getBounds();
/*     */     
/*     */ 
/* 329 */     int[] arrayOfInt = SunWritableRaster.stealData((DataBufferInt)???, 0);
/* 330 */     synchronized (SplashScreen.class) {
/* 331 */       checkVisible();
/* 332 */       _update(this.splashPtr, arrayOfInt, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height, j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IllegalStateException
/*     */   {
/* 343 */     synchronized (SplashScreen.class) {
/* 344 */       checkVisible();
/* 345 */       _close(this.splashPtr);
/* 346 */       this.image = null;
/* 347 */       markClosed();
/*     */     }
/*     */   }
/*     */   
/*     */   static void markClosed() {
/* 352 */     synchronized (SplashScreen.class) {
/* 353 */       wasClosed = true;
/* 354 */       theInstance = null;
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
/*     */   public boolean isVisible()
/*     */   {
/* 373 */     synchronized (SplashScreen.class) {
/* 374 */       return (!wasClosed) && (_isVisible(this.splashPtr));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 381 */   private static boolean wasClosed = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private URL imageURL;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 392 */   private static SplashScreen theInstance = null;
/*     */   
/* 394 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.SplashScreen");
/*     */   
/*     */   private static native void _update(long paramLong, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private static native boolean _isVisible(long paramLong);
/*     */   
/*     */   private static native Rectangle _getBounds(long paramLong);
/*     */   
/*     */   private static native long _getInstance();
/*     */   
/*     */   private static native void _close(long paramLong);
/*     */   
/*     */   private static native String _getImageFileName(long paramLong);
/*     */   
/*     */   private static native String _getImageJarName(long paramLong);
/*     */   
/*     */   private static native boolean _setImageData(long paramLong, byte[] paramArrayOfByte);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/SplashScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */