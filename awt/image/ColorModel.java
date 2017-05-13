/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Transparency;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.java2d.cmm.CMSManager;
/*      */ import sun.java2d.cmm.ColorTransform;
/*      */ import sun.java2d.cmm.PCMM;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ColorModel
/*      */   implements Transparency
/*      */ {
/*      */   private long pData;
/*      */   protected int pixel_bits;
/*      */   int[] nBits;
/*  164 */   int transparency = 3;
/*  165 */   boolean supportsAlpha = true;
/*  166 */   boolean isAlphaPremultiplied = false;
/*  167 */   int numComponents = -1;
/*  168 */   int numColorComponents = -1;
/*  169 */   ColorSpace colorSpace = ColorSpace.getInstance(1000);
/*  170 */   int colorSpaceType = 5;
/*      */   int maxBits;
/*  172 */   boolean is_sRGB = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int transferType;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  203 */   private static boolean loaded = false;
/*      */   
/*  205 */   static void loadLibraries() { if (!loaded) {
/*  206 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*  209 */           System.loadLibrary("awt");
/*  210 */           return null;
/*      */         }
/*  212 */       });
/*  213 */       loaded = true;
/*      */     }
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  219 */     loadLibraries();
/*  220 */     initIDs();
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
/*      */   public static ColorModel getRGBdefault()
/*      */   {
/*  241 */     if (RGBdefault == null) {
/*  242 */       RGBdefault = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  249 */     return RGBdefault;
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
/*      */   public ColorModel(int paramInt)
/*      */   {
/*  273 */     this.pixel_bits = paramInt;
/*  274 */     if (paramInt < 1) {
/*  275 */       throw new IllegalArgumentException("Number of bits must be > 0");
/*      */     }
/*  277 */     this.numComponents = 4;
/*  278 */     this.numColorComponents = 3;
/*  279 */     this.maxBits = paramInt;
/*      */     
/*  281 */     this.transferType = getDefaultTransferType(paramInt);
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
/*      */ 
/*      */ 
/*      */   protected ColorModel(int paramInt1, int[] paramArrayOfInt, ColorSpace paramColorSpace, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, int paramInt3)
/*      */   {
/*  335 */     this.colorSpace = paramColorSpace;
/*  336 */     this.colorSpaceType = paramColorSpace.getType();
/*  337 */     this.numColorComponents = paramColorSpace.getNumComponents();
/*  338 */     this.numComponents = (this.numColorComponents + (paramBoolean1 ? 1 : 0));
/*  339 */     this.supportsAlpha = paramBoolean1;
/*  340 */     if (paramArrayOfInt.length < this.numComponents) {
/*  341 */       throw new IllegalArgumentException("Number of color/alpha components should be " + this.numComponents + " but length of bits array is " + paramArrayOfInt.length);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  349 */     if ((paramInt2 < 1) || (paramInt2 > 3))
/*      */     {
/*      */ 
/*  352 */       throw new IllegalArgumentException("Unknown transparency: " + paramInt2);
/*      */     }
/*      */     
/*      */ 
/*  356 */     if (!this.supportsAlpha) {
/*  357 */       this.isAlphaPremultiplied = false;
/*  358 */       this.transparency = 1;
/*      */     }
/*      */     else {
/*  361 */       this.isAlphaPremultiplied = paramBoolean2;
/*  362 */       this.transparency = paramInt2;
/*      */     }
/*      */     
/*  365 */     this.nBits = ((int[])paramArrayOfInt.clone());
/*  366 */     this.pixel_bits = paramInt1;
/*  367 */     if (paramInt1 <= 0) {
/*  368 */       throw new IllegalArgumentException("Number of pixel bits must be > 0");
/*      */     }
/*      */     
/*      */ 
/*  372 */     this.maxBits = 0;
/*  373 */     for (int i = 0; i < paramArrayOfInt.length; i++)
/*      */     {
/*  375 */       if (paramArrayOfInt[i] < 0) {
/*  376 */         throw new IllegalArgumentException("Number of bits must be >= 0");
/*      */       }
/*      */       
/*  379 */       if (this.maxBits < paramArrayOfInt[i]) {
/*  380 */         this.maxBits = paramArrayOfInt[i];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  385 */     if (this.maxBits == 0) {
/*  386 */       throw new IllegalArgumentException("There must be at least one component with > 0 pixel bits.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  392 */     if (paramColorSpace != ColorSpace.getInstance(1000)) {
/*  393 */       this.is_sRGB = false;
/*      */     }
/*      */     
/*      */ 
/*  397 */     this.transferType = paramInt3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean hasAlpha()
/*      */   {
/*  407 */     return this.supportsAlpha;
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
/*      */   public final boolean isAlphaPremultiplied()
/*      */   {
/*  423 */     return this.isAlphaPremultiplied;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getTransferType()
/*      */   {
/*  434 */     return this.transferType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getPixelSize()
/*      */   {
/*  443 */     return this.pixel_bits;
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
/*      */   public int getComponentSize(int paramInt)
/*      */   {
/*  466 */     if (this.nBits == null) {
/*  467 */       throw new NullPointerException("Number of bits array is null.");
/*      */     }
/*      */     
/*  470 */     return this.nBits[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getComponentSize()
/*      */   {
/*  481 */     if (this.nBits != null) {
/*  482 */       return (int[])this.nBits.clone();
/*      */     }
/*      */     
/*  485 */     return null;
/*      */   }
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
/*  497 */     return this.transparency;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getNumComponents()
/*      */   {
/*  507 */     return this.numComponents;
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
/*      */   public int getNumColorComponents()
/*      */   {
/*  520 */     return this.numColorComponents;
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
/*      */   private static ColorModel RGBdefault;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getRGB(int paramInt)
/*      */   {
/*  598 */     return getAlpha(paramInt) << 24 | getRed(paramInt) << 16 | getGreen(paramInt) << 8 | getBlue(paramInt) << 0;
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
/*      */   public int getRed(Object paramObject)
/*      */   {
/*  638 */     int i = 0;int j = 0;
/*  639 */     switch (this.transferType) {
/*      */     case 0: 
/*  641 */       byte[] arrayOfByte = (byte[])paramObject;
/*  642 */       i = arrayOfByte[0] & 0xFF;
/*  643 */       j = arrayOfByte.length;
/*  644 */       break;
/*      */     case 1: 
/*  646 */       short[] arrayOfShort = (short[])paramObject;
/*  647 */       i = arrayOfShort[0] & 0xFFFF;
/*  648 */       j = arrayOfShort.length;
/*  649 */       break;
/*      */     case 3: 
/*  651 */       int[] arrayOfInt = (int[])paramObject;
/*  652 */       i = arrayOfInt[0];
/*  653 */       j = arrayOfInt.length;
/*  654 */       break;
/*      */     case 2: default: 
/*  656 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/*  659 */     if (j == 1) {
/*  660 */       return getRed(i);
/*      */     }
/*      */     
/*  663 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getGreen(Object paramObject)
/*      */   {
/*  705 */     int i = 0;int j = 0;
/*  706 */     switch (this.transferType) {
/*      */     case 0: 
/*  708 */       byte[] arrayOfByte = (byte[])paramObject;
/*  709 */       i = arrayOfByte[0] & 0xFF;
/*  710 */       j = arrayOfByte.length;
/*  711 */       break;
/*      */     case 1: 
/*  713 */       short[] arrayOfShort = (short[])paramObject;
/*  714 */       i = arrayOfShort[0] & 0xFFFF;
/*  715 */       j = arrayOfShort.length;
/*  716 */       break;
/*      */     case 3: 
/*  718 */       int[] arrayOfInt = (int[])paramObject;
/*  719 */       i = arrayOfInt[0];
/*  720 */       j = arrayOfInt.length;
/*  721 */       break;
/*      */     case 2: default: 
/*  723 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/*  726 */     if (j == 1) {
/*  727 */       return getGreen(i);
/*      */     }
/*      */     
/*  730 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getBlue(Object paramObject)
/*      */   {
/*  772 */     int i = 0;int j = 0;
/*  773 */     switch (this.transferType) {
/*      */     case 0: 
/*  775 */       byte[] arrayOfByte = (byte[])paramObject;
/*  776 */       i = arrayOfByte[0] & 0xFF;
/*  777 */       j = arrayOfByte.length;
/*  778 */       break;
/*      */     case 1: 
/*  780 */       short[] arrayOfShort = (short[])paramObject;
/*  781 */       i = arrayOfShort[0] & 0xFFFF;
/*  782 */       j = arrayOfShort.length;
/*  783 */       break;
/*      */     case 3: 
/*  785 */       int[] arrayOfInt = (int[])paramObject;
/*  786 */       i = arrayOfInt[0];
/*  787 */       j = arrayOfInt.length;
/*  788 */       break;
/*      */     case 2: default: 
/*  790 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/*  793 */     if (j == 1) {
/*  794 */       return getBlue(i);
/*      */     }
/*      */     
/*  797 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getAlpha(Object paramObject)
/*      */   {
/*  835 */     int i = 0;int j = 0;
/*  836 */     switch (this.transferType) {
/*      */     case 0: 
/*  838 */       byte[] arrayOfByte = (byte[])paramObject;
/*  839 */       i = arrayOfByte[0] & 0xFF;
/*  840 */       j = arrayOfByte.length;
/*  841 */       break;
/*      */     case 1: 
/*  843 */       short[] arrayOfShort = (short[])paramObject;
/*  844 */       i = arrayOfShort[0] & 0xFFFF;
/*  845 */       j = arrayOfShort.length;
/*  846 */       break;
/*      */     case 3: 
/*  848 */       int[] arrayOfInt = (int[])paramObject;
/*  849 */       i = arrayOfInt[0];
/*  850 */       j = arrayOfInt.length;
/*  851 */       break;
/*      */     case 2: default: 
/*  853 */       throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     }
/*      */     
/*  856 */     if (j == 1) {
/*  857 */       return getAlpha(i);
/*      */     }
/*      */     
/*  860 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getRGB(Object paramObject)
/*      */   {
/*  886 */     return getAlpha(paramObject) << 24 | getRed(paramObject) << 16 | getGreen(paramObject) << 8 | getBlue(paramObject) << 0;
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
/*      */   public Object getDataElements(int paramInt, Object paramObject)
/*      */   {
/*  927 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */   public int[] getComponents(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*      */   {
/*  964 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */   public int[] getComponents(Object paramObject, int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 1006 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getUnnormalizedComponents(float[] paramArrayOfFloat, int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*      */   {
/* 1061 */     if (this.colorSpace == null) {
/* 1062 */       throw new UnsupportedOperationException("This method is not supported by this color model.");
/*      */     }
/*      */     
/*      */ 
/* 1066 */     if (this.nBits == null) {
/* 1067 */       throw new UnsupportedOperationException("This method is not supported.  Unable to determine #bits per component.");
/*      */     }
/*      */     
/*      */ 
/* 1071 */     if (paramArrayOfFloat.length - paramInt1 < this.numComponents) {
/* 1072 */       throw new IllegalArgumentException("Incorrect number of components.  Expecting " + this.numComponents);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1078 */     if (paramArrayOfInt == null) {
/* 1079 */       paramArrayOfInt = new int[paramInt2 + this.numComponents];
/*      */     }
/*      */     
/* 1082 */     if ((this.supportsAlpha) && (this.isAlphaPremultiplied)) {
/* 1083 */       float f = paramArrayOfFloat[(paramInt1 + this.numColorComponents)];
/* 1084 */       for (int j = 0; j < this.numColorComponents; j++) {
/* 1085 */         paramArrayOfInt[(paramInt2 + j)] = ((int)(paramArrayOfFloat[(paramInt1 + j)] * ((1 << this.nBits[j]) - 1) * f + 0.5F));
/*      */       }
/*      */       
/*      */ 
/* 1089 */       paramArrayOfInt[(paramInt2 + this.numColorComponents)] = ((int)(f * ((1 << this.nBits[this.numColorComponents]) - 1) + 0.5F));
/*      */     }
/*      */     else
/*      */     {
/* 1093 */       for (int i = 0; i < this.numComponents; i++) {
/* 1094 */         paramArrayOfInt[(paramInt2 + i)] = ((int)(paramArrayOfFloat[(paramInt1 + i)] * ((1 << this.nBits[i]) - 1) + 0.5F));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1099 */     return paramArrayOfInt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getNormalizedComponents(int[] paramArrayOfInt, int paramInt1, float[] paramArrayOfFloat, int paramInt2)
/*      */   {
/* 1157 */     if (this.colorSpace == null) {
/* 1158 */       throw new UnsupportedOperationException("This method is not supported by this color model.");
/*      */     }
/*      */     
/* 1161 */     if (this.nBits == null) {
/* 1162 */       throw new UnsupportedOperationException("This method is not supported.  Unable to determine #bits per component.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1167 */     if (paramArrayOfInt.length - paramInt1 < this.numComponents) {
/* 1168 */       throw new IllegalArgumentException("Incorrect number of components.  Expecting " + this.numComponents);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1174 */     if (paramArrayOfFloat == null) {
/* 1175 */       paramArrayOfFloat = new float[this.numComponents + paramInt2];
/*      */     }
/*      */     
/* 1178 */     if ((this.supportsAlpha) && (this.isAlphaPremultiplied))
/*      */     {
/* 1180 */       float f = paramArrayOfInt[(paramInt1 + this.numColorComponents)];
/* 1181 */       f /= ((1 << this.nBits[this.numColorComponents]) - 1);
/* 1182 */       int j; if (f != 0.0F) {
/* 1183 */         for (j = 0; j < this.numColorComponents; j++) {
/* 1184 */           paramArrayOfFloat[(paramInt2 + j)] = (paramArrayOfInt[(paramInt1 + j)] / (f * ((1 << this.nBits[j]) - 1)));
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 1189 */         for (j = 0; j < this.numColorComponents; j++) {
/* 1190 */           paramArrayOfFloat[(paramInt2 + j)] = 0.0F;
/*      */         }
/*      */       }
/* 1193 */       paramArrayOfFloat[(paramInt2 + this.numColorComponents)] = f;
/*      */     }
/*      */     else {
/* 1196 */       for (int i = 0; i < this.numComponents; i++) {
/* 1197 */         paramArrayOfFloat[(paramInt2 + i)] = (paramArrayOfInt[(paramInt1 + i)] / ((1 << this.nBits[i]) - 1));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1202 */     return paramArrayOfFloat;
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
/*      */   public int getDataElement(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 1241 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */   public Object getDataElements(int[] paramArrayOfInt, int paramInt, Object paramObject)
/*      */   {
/* 1292 */     throw new UnsupportedOperationException("This method has not been implemented for this color model.");
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
/*      */   public int getDataElement(float[] paramArrayOfFloat, int paramInt)
/*      */   {
/* 1329 */     int[] arrayOfInt = getUnnormalizedComponents(paramArrayOfFloat, paramInt, null, 0);
/*      */     
/* 1331 */     return getDataElement(arrayOfInt, 0);
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
/*      */   public Object getDataElements(float[] paramArrayOfFloat, int paramInt, Object paramObject)
/*      */   {
/* 1377 */     int[] arrayOfInt = getUnnormalizedComponents(paramArrayOfFloat, paramInt, null, 0);
/*      */     
/* 1379 */     return getDataElements(arrayOfInt, 0, paramObject);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getNormalizedComponents(Object paramObject, float[] paramArrayOfFloat, int paramInt)
/*      */   {
/* 1438 */     int[] arrayOfInt = getComponents(paramObject, null, 0);
/* 1439 */     return getNormalizedComponents(arrayOfInt, 0, paramArrayOfFloat, paramInt);
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1453 */     if (!(paramObject instanceof ColorModel)) {
/* 1454 */       return false;
/*      */     }
/* 1456 */     ColorModel localColorModel = (ColorModel)paramObject;
/*      */     
/* 1458 */     if (this == localColorModel) {
/* 1459 */       return true;
/*      */     }
/* 1461 */     if ((this.supportsAlpha != localColorModel.hasAlpha()) || 
/* 1462 */       (this.isAlphaPremultiplied != localColorModel.isAlphaPremultiplied()) || 
/* 1463 */       (this.pixel_bits != localColorModel.getPixelSize()) || 
/* 1464 */       (this.transparency != localColorModel.getTransparency()) || 
/* 1465 */       (this.numComponents != localColorModel.getNumComponents()))
/*      */     {
/* 1467 */       return false;
/*      */     }
/*      */     
/* 1470 */     int[] arrayOfInt = localColorModel.getComponentSize();
/*      */     
/* 1472 */     if ((this.nBits != null) && (arrayOfInt != null)) {
/* 1473 */       for (int i = 0; i < this.numComponents; i++) {
/* 1474 */         if (this.nBits[i] != arrayOfInt[i]) {
/* 1475 */           return false;
/*      */         }
/*      */       }
/*      */     } else {
/* 1479 */       return (this.nBits == null) && (arrayOfInt == null);
/*      */     }
/*      */     
/* 1482 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1492 */     int i = 0;
/*      */     
/* 1494 */     i = (this.supportsAlpha ? 2 : 3) + (this.isAlphaPremultiplied ? 4 : 5) + this.pixel_bits * 6 + this.transparency * 7 + this.numComponents * 8;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1500 */     if (this.nBits != null) {
/* 1501 */       for (int j = 0; j < this.numComponents; j++) {
/* 1502 */         i += this.nBits[j] * (j + 9);
/*      */       }
/*      */     }
/*      */     
/* 1506 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ColorSpace getColorSpace()
/*      */   {
/* 1516 */     return this.colorSpace;
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
/*      */   public ColorModel coerceData(WritableRaster paramWritableRaster, boolean paramBoolean)
/*      */   {
/* 1542 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public boolean isCompatibleRaster(Raster paramRaster)
/*      */   {
/* 1562 */     throw new UnsupportedOperationException("This method has not been implemented for this ColorModel.");
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
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2)
/*      */   {
/* 1584 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2)
/*      */   {
/* 1605 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public boolean isCompatibleSampleModel(SampleModel paramSampleModel)
/*      */   {
/* 1624 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public WritableRaster getAlphaRaster(WritableRaster paramWritableRaster)
/*      */   {
/* 1663 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1673 */     return new String("ColorModel: #pixelBits = " + this.pixel_bits + " numComponents = " + this.numComponents + " color space = " + this.colorSpace + " transparency = " + this.transparency + " has alpha = " + this.supportsAlpha + " isAlphaPre = " + this.isAlphaPremultiplied);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static int getDefaultTransferType(int paramInt)
/*      */   {
/* 1683 */     if (paramInt <= 8)
/* 1684 */       return 0;
/* 1685 */     if (paramInt <= 16)
/* 1686 */       return 1;
/* 1687 */     if (paramInt <= 32) {
/* 1688 */       return 3;
/*      */     }
/* 1690 */     return 32;
/*      */   }
/*      */   
/*      */ 
/* 1694 */   static byte[] l8Tos8 = null;
/* 1695 */   static byte[] s8Tol8 = null;
/* 1696 */   static byte[] l16Tos8 = null;
/* 1697 */   static short[] s8Tol16 = null;
/*      */   
/*      */ 
/* 1700 */   static Map<ICC_ColorSpace, byte[]> g8Tos8Map = null;
/* 1701 */   static Map<ICC_ColorSpace, byte[]> lg16Toog8Map = null;
/* 1702 */   static Map<ICC_ColorSpace, byte[]> g16Tos8Map = null;
/* 1703 */   static Map<ICC_ColorSpace, short[]> lg16Toog16Map = null;
/*      */   
/*      */ 
/*      */   static boolean isLinearRGBspace(ColorSpace paramColorSpace)
/*      */   {
/* 1708 */     return paramColorSpace == CMSManager.LINEAR_RGBspace;
/*      */   }
/*      */   
/*      */ 
/*      */   static boolean isLinearGRAYspace(ColorSpace paramColorSpace)
/*      */   {
/* 1714 */     return paramColorSpace == CMSManager.GRAYspace;
/*      */   }
/*      */   
/*      */   static byte[] getLinearRGB8TosRGB8LUT() {
/* 1718 */     if (l8Tos8 == null) {
/* 1719 */       l8Tos8 = new byte['Ā'];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1726 */       for (int i = 0; i <= 255; i++) {
/* 1727 */         float f1 = i / 255.0F;
/* 1728 */         float f2; if (f1 <= 0.0031308F) {
/* 1729 */           f2 = f1 * 12.92F;
/*      */         } else {
/* 1731 */           f2 = 1.055F * (float)Math.pow(f1, 0.4166666666666667D) - 0.055F;
/*      */         }
/*      */         
/* 1734 */         l8Tos8[i] = ((byte)Math.round(f2 * 255.0F));
/*      */       }
/*      */     }
/* 1737 */     return l8Tos8;
/*      */   }
/*      */   
/*      */   static byte[] getsRGB8ToLinearRGB8LUT() {
/* 1741 */     if (s8Tol8 == null) {
/* 1742 */       s8Tol8 = new byte['Ā'];
/*      */       
/*      */ 
/* 1745 */       for (int i = 0; i <= 255; i++) {
/* 1746 */         float f1 = i / 255.0F;
/* 1747 */         float f2; if (f1 <= 0.04045F) {
/* 1748 */           f2 = f1 / 12.92F;
/*      */         } else {
/* 1750 */           f2 = (float)Math.pow((f1 + 0.055F) / 1.055F, 2.4D);
/*      */         }
/* 1752 */         s8Tol8[i] = ((byte)Math.round(f2 * 255.0F));
/*      */       }
/*      */     }
/* 1755 */     return s8Tol8;
/*      */   }
/*      */   
/*      */   static byte[] getLinearRGB16TosRGB8LUT() {
/* 1759 */     if (l16Tos8 == null) {
/* 1760 */       l16Tos8 = new byte[65536];
/*      */       
/*      */ 
/* 1763 */       for (int i = 0; i <= 65535; i++) {
/* 1764 */         float f1 = i / 65535.0F;
/* 1765 */         float f2; if (f1 <= 0.0031308F) {
/* 1766 */           f2 = f1 * 12.92F;
/*      */         } else {
/* 1768 */           f2 = 1.055F * (float)Math.pow(f1, 0.4166666666666667D) - 0.055F;
/*      */         }
/*      */         
/* 1771 */         l16Tos8[i] = ((byte)Math.round(f2 * 255.0F));
/*      */       }
/*      */     }
/* 1774 */     return l16Tos8;
/*      */   }
/*      */   
/*      */   static short[] getsRGB8ToLinearRGB16LUT() {
/* 1778 */     if (s8Tol16 == null) {
/* 1779 */       s8Tol16 = new short['Ā'];
/*      */       
/*      */ 
/* 1782 */       for (int i = 0; i <= 255; i++) {
/* 1783 */         float f1 = i / 255.0F;
/* 1784 */         float f2; if (f1 <= 0.04045F) {
/* 1785 */           f2 = f1 / 12.92F;
/*      */         } else {
/* 1787 */           f2 = (float)Math.pow((f1 + 0.055F) / 1.055F, 2.4D);
/*      */         }
/* 1789 */         s8Tol16[i] = ((short)Math.round(f2 * 65535.0F));
/*      */       }
/*      */     }
/* 1792 */     return s8Tol16;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static byte[] getGray8TosRGB8LUT(ICC_ColorSpace paramICC_ColorSpace)
/*      */   {
/* 1803 */     if (isLinearGRAYspace(paramICC_ColorSpace)) {
/* 1804 */       return getLinearRGB8TosRGB8LUT();
/*      */     }
/* 1806 */     if (g8Tos8Map != null) {
/* 1807 */       arrayOfByte1 = (byte[])g8Tos8Map.get(paramICC_ColorSpace);
/* 1808 */       if (arrayOfByte1 != null) {
/* 1809 */         return arrayOfByte1;
/*      */       }
/*      */     }
/* 1812 */     byte[] arrayOfByte1 = new byte['Ā'];
/* 1813 */     for (int i = 0; i <= 255; i++) {
/* 1814 */       arrayOfByte1[i] = ((byte)i);
/*      */     }
/* 1816 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1817 */     PCMM localPCMM = CMSManager.getModule();
/*      */     
/* 1819 */     ICC_ColorSpace localICC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1000);
/* 1820 */     arrayOfColorTransform[0] = localPCMM.createTransform(paramICC_ColorSpace
/* 1821 */       .getProfile(), -1, 1);
/* 1822 */     arrayOfColorTransform[1] = localPCMM.createTransform(localICC_ColorSpace
/* 1823 */       .getProfile(), -1, 2);
/* 1824 */     ColorTransform localColorTransform = localPCMM.createTransform(arrayOfColorTransform);
/* 1825 */     byte[] arrayOfByte2 = localColorTransform.colorConvert(arrayOfByte1, null);
/* 1826 */     int j = 0; for (int k = 2; j <= 255; k += 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1832 */       arrayOfByte1[j] = arrayOfByte2[k];j++;
/*      */     }
/* 1834 */     if (g8Tos8Map == null) {
/* 1835 */       g8Tos8Map = Collections.synchronizedMap(new WeakHashMap(2));
/*      */     }
/* 1837 */     g8Tos8Map.put(paramICC_ColorSpace, arrayOfByte1);
/* 1838 */     return arrayOfByte1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static byte[] getLinearGray16ToOtherGray8LUT(ICC_ColorSpace paramICC_ColorSpace)
/*      */   {
/* 1847 */     if (lg16Toog8Map != null) {
/* 1848 */       localObject = (byte[])lg16Toog8Map.get(paramICC_ColorSpace);
/* 1849 */       if (localObject != null) {
/* 1850 */         return (byte[])localObject;
/*      */       }
/*      */     }
/* 1853 */     Object localObject = new short[65536];
/* 1854 */     for (int i = 0; i <= 65535; i++) {
/* 1855 */       localObject[i] = ((short)i);
/*      */     }
/* 1857 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1858 */     PCMM localPCMM = CMSManager.getModule();
/*      */     
/* 1860 */     ICC_ColorSpace localICC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1003);
/* 1861 */     arrayOfColorTransform[0] = localPCMM.createTransform(localICC_ColorSpace
/* 1862 */       .getProfile(), -1, 1);
/* 1863 */     arrayOfColorTransform[1] = localPCMM.createTransform(paramICC_ColorSpace
/* 1864 */       .getProfile(), -1, 2);
/* 1865 */     ColorTransform localColorTransform = localPCMM.createTransform(arrayOfColorTransform);
/* 1866 */     localObject = localColorTransform.colorConvert((short[])localObject, null);
/* 1867 */     byte[] arrayOfByte = new byte[65536];
/* 1868 */     for (int j = 0; j <= 65535; j++)
/*      */     {
/* 1870 */       arrayOfByte[j] = ((byte)(int)((localObject[j] & 0xFFFF) * 0.0038910506F + 0.5F));
/*      */     }
/*      */     
/* 1873 */     if (lg16Toog8Map == null) {
/* 1874 */       lg16Toog8Map = Collections.synchronizedMap(new WeakHashMap(2));
/*      */     }
/* 1876 */     lg16Toog8Map.put(paramICC_ColorSpace, arrayOfByte);
/* 1877 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static byte[] getGray16TosRGB8LUT(ICC_ColorSpace paramICC_ColorSpace)
/*      */   {
/* 1888 */     if (isLinearGRAYspace(paramICC_ColorSpace)) {
/* 1889 */       return getLinearRGB16TosRGB8LUT();
/*      */     }
/* 1891 */     if (g16Tos8Map != null) {
/* 1892 */       localObject = (byte[])g16Tos8Map.get(paramICC_ColorSpace);
/* 1893 */       if (localObject != null) {
/* 1894 */         return (byte[])localObject;
/*      */       }
/*      */     }
/* 1897 */     Object localObject = new short[65536];
/* 1898 */     for (int i = 0; i <= 65535; i++) {
/* 1899 */       localObject[i] = ((short)i);
/*      */     }
/* 1901 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1902 */     PCMM localPCMM = CMSManager.getModule();
/*      */     
/* 1904 */     ICC_ColorSpace localICC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1000);
/* 1905 */     arrayOfColorTransform[0] = localPCMM.createTransform(paramICC_ColorSpace
/* 1906 */       .getProfile(), -1, 1);
/* 1907 */     arrayOfColorTransform[1] = localPCMM.createTransform(localICC_ColorSpace
/* 1908 */       .getProfile(), -1, 2);
/* 1909 */     ColorTransform localColorTransform = localPCMM.createTransform(arrayOfColorTransform);
/* 1910 */     localObject = localColorTransform.colorConvert((short[])localObject, null);
/* 1911 */     byte[] arrayOfByte = new byte[65536];
/* 1912 */     int j = 0; for (int k = 2; j <= 65535; k += 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1920 */       arrayOfByte[j] = ((byte)(int)((localObject[k] & 0xFFFF) * 0.0038910506F + 0.5F));j++;
/*      */     }
/*      */     
/* 1923 */     if (g16Tos8Map == null) {
/* 1924 */       g16Tos8Map = Collections.synchronizedMap(new WeakHashMap(2));
/*      */     }
/* 1926 */     g16Tos8Map.put(paramICC_ColorSpace, arrayOfByte);
/* 1927 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static short[] getLinearGray16ToOtherGray16LUT(ICC_ColorSpace paramICC_ColorSpace)
/*      */   {
/* 1936 */     if (lg16Toog16Map != null) {
/* 1937 */       arrayOfShort1 = (short[])lg16Toog16Map.get(paramICC_ColorSpace);
/* 1938 */       if (arrayOfShort1 != null) {
/* 1939 */         return arrayOfShort1;
/*      */       }
/*      */     }
/* 1942 */     short[] arrayOfShort1 = new short[65536];
/* 1943 */     for (int i = 0; i <= 65535; i++) {
/* 1944 */       arrayOfShort1[i] = ((short)i);
/*      */     }
/* 1946 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1947 */     PCMM localPCMM = CMSManager.getModule();
/*      */     
/* 1949 */     ICC_ColorSpace localICC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1003);
/* 1950 */     arrayOfColorTransform[0] = localPCMM.createTransform(localICC_ColorSpace
/* 1951 */       .getProfile(), -1, 1);
/* 1952 */     arrayOfColorTransform[1] = localPCMM.createTransform(paramICC_ColorSpace
/* 1953 */       .getProfile(), -1, 2);
/* 1954 */     ColorTransform localColorTransform = localPCMM.createTransform(arrayOfColorTransform);
/*      */     
/* 1956 */     short[] arrayOfShort2 = localColorTransform.colorConvert(arrayOfShort1, null);
/* 1957 */     if (lg16Toog16Map == null) {
/* 1958 */       lg16Toog16Map = Collections.synchronizedMap(new WeakHashMap(2));
/*      */     }
/* 1960 */     lg16Toog16Map.put(paramICC_ColorSpace, arrayOfShort2);
/* 1961 */     return arrayOfShort2;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   public abstract int getRed(int paramInt);
/*      */   
/*      */   public abstract int getGreen(int paramInt);
/*      */   
/*      */   public abstract int getBlue(int paramInt);
/*      */   
/*      */   public abstract int getAlpha(int paramInt);
/*      */   
/*      */   public void finalize() {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/ColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */