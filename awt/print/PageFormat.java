/*     */ package java.awt.print;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageFormat
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int LANDSCAPE = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int PORTRAIT = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int REVERSE_LANDSCAPE = 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Paper mPaper;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */   private int mOrientation = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PageFormat()
/*     */   {
/*  87 */     this.mPaper = new Paper();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     PageFormat localPageFormat;
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 101 */       localPageFormat = (PageFormat)super.clone();
/* 102 */       localPageFormat.mPaper = ((Paper)this.mPaper.clone());
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 105 */       localCloneNotSupportedException.printStackTrace();
/* 106 */       localPageFormat = null;
/*     */     }
/*     */     
/* 109 */     return localPageFormat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getWidth()
/*     */   {
/* 121 */     int i = getOrientation();
/*     */     double d;
/* 123 */     if (i == 1) {
/* 124 */       d = this.mPaper.getWidth();
/*     */     } else {
/* 126 */       d = this.mPaper.getHeight();
/*     */     }
/*     */     
/* 129 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getHeight()
/*     */   {
/* 140 */     int i = getOrientation();
/*     */     double d;
/* 142 */     if (i == 1) {
/* 143 */       d = this.mPaper.getHeight();
/*     */     } else {
/* 145 */       d = this.mPaper.getWidth();
/*     */     }
/*     */     
/* 148 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getImageableX()
/*     */   {
/*     */     double d;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 164 */     switch (getOrientation())
/*     */     {
/*     */ 
/*     */     case 0: 
/* 168 */       d = this.mPaper.getHeight() - (this.mPaper.getImageableY() + this.mPaper.getImageableHeight());
/* 169 */       break;
/*     */     
/*     */     case 1: 
/* 172 */       d = this.mPaper.getImageableX();
/* 173 */       break;
/*     */     
/*     */     case 2: 
/* 176 */       d = this.mPaper.getImageableY();
/* 177 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     default: 
/* 183 */       throw new InternalError("unrecognized orientation");
/*     */     }
/*     */     
/*     */     
/* 187 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getImageableY()
/*     */   {
/*     */     double d;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 203 */     switch (getOrientation())
/*     */     {
/*     */     case 0: 
/* 206 */       d = this.mPaper.getImageableX();
/* 207 */       break;
/*     */     
/*     */     case 1: 
/* 210 */       d = this.mPaper.getImageableY();
/* 211 */       break;
/*     */     
/*     */ 
/*     */     case 2: 
/* 215 */       d = this.mPaper.getWidth() - (this.mPaper.getImageableX() + this.mPaper.getImageableWidth());
/* 216 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     default: 
/* 222 */       throw new InternalError("unrecognized orientation");
/*     */     }
/*     */     
/*     */     
/* 226 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getImageableWidth()
/*     */   {
/*     */     double d;
/*     */     
/*     */ 
/*     */ 
/* 238 */     if (getOrientation() == 1) {
/* 239 */       d = this.mPaper.getImageableWidth();
/*     */     } else {
/* 241 */       d = this.mPaper.getImageableHeight();
/*     */     }
/*     */     
/* 244 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getImageableHeight()
/*     */   {
/*     */     double d;
/*     */     
/*     */ 
/*     */ 
/* 256 */     if (getOrientation() == 1) {
/* 257 */       d = this.mPaper.getImageableHeight();
/*     */     } else {
/* 259 */       d = this.mPaper.getImageableWidth();
/*     */     }
/*     */     
/* 262 */     return d;
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
/*     */   public Paper getPaper()
/*     */   {
/* 281 */     return (Paper)this.mPaper.clone();
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
/*     */   public void setPaper(Paper paramPaper)
/*     */   {
/* 294 */     this.mPaper = ((Paper)paramPaper.clone());
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
/*     */   public void setOrientation(int paramInt)
/*     */     throws IllegalArgumentException
/*     */   {
/* 308 */     if ((0 <= paramInt) && (paramInt <= 2)) {
/* 309 */       this.mOrientation = paramInt;
/*     */     } else {
/* 311 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/* 321 */     return this.mOrientation;
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
/*     */   public double[] getMatrix()
/*     */   {
/* 337 */     double[] arrayOfDouble = new double[6];
/*     */     
/* 339 */     switch (this.mOrientation)
/*     */     {
/*     */     case 0: 
/* 342 */       arrayOfDouble[0] = 0.0D;arrayOfDouble[1] = -1.0D;
/* 343 */       arrayOfDouble[2] = 1.0D;arrayOfDouble[3] = 0.0D;
/* 344 */       arrayOfDouble[4] = 0.0D;arrayOfDouble[5] = this.mPaper.getHeight();
/* 345 */       break;
/*     */     
/*     */     case 1: 
/* 348 */       arrayOfDouble[0] = 1.0D;arrayOfDouble[1] = 0.0D;
/* 349 */       arrayOfDouble[2] = 0.0D;arrayOfDouble[3] = 1.0D;
/* 350 */       arrayOfDouble[4] = 0.0D;arrayOfDouble[5] = 0.0D;
/* 351 */       break;
/*     */     
/*     */     case 2: 
/* 354 */       arrayOfDouble[0] = 0.0D;arrayOfDouble[1] = 1.0D;
/* 355 */       arrayOfDouble[2] = -1.0D;arrayOfDouble[3] = 0.0D;
/* 356 */       arrayOfDouble[4] = this.mPaper.getWidth();arrayOfDouble[5] = 0.0D;
/* 357 */       break;
/*     */     
/*     */     default: 
/* 360 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 363 */     return arrayOfDouble;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/print/PageFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */