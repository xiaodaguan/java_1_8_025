/*      */ package java.awt.geom;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
/*      */ import sun.awt.geom.Curve;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Path2D
/*      */   implements Shape, Cloneable
/*      */ {
/*      */   public static final int WIND_EVEN_ODD = 0;
/*      */   public static final int WIND_NON_ZERO = 1;
/*      */   private static final byte SEG_MOVETO = 0;
/*      */   private static final byte SEG_LINETO = 1;
/*      */   private static final byte SEG_QUADTO = 2;
/*      */   private static final byte SEG_CUBICTO = 3;
/*      */   private static final byte SEG_CLOSE = 4;
/*      */   transient byte[] pointTypes;
/*      */   transient int numTypes;
/*      */   transient int numCoords;
/*      */   transient int windingRule;
/*      */   static final int INIT_SIZE = 20;
/*      */   static final int EXPAND_MAX = 500;
/*      */   private static final byte SERIAL_STORAGE_FLT_ARRAY = 48;
/*      */   private static final byte SERIAL_STORAGE_DBL_ARRAY = 49;
/*      */   private static final byte SERIAL_SEG_FLT_MOVETO = 64;
/*      */   private static final byte SERIAL_SEG_FLT_LINETO = 65;
/*      */   private static final byte SERIAL_SEG_FLT_QUADTO = 66;
/*      */   private static final byte SERIAL_SEG_FLT_CUBICTO = 67;
/*      */   private static final byte SERIAL_SEG_DBL_MOVETO = 80;
/*      */   private static final byte SERIAL_SEG_DBL_LINETO = 81;
/*      */   private static final byte SERIAL_SEG_DBL_QUADTO = 82;
/*      */   private static final byte SERIAL_SEG_DBL_CUBICTO = 83;
/*      */   private static final byte SERIAL_SEG_CLOSE = 96;
/*      */   private static final byte SERIAL_PATH_END = 97;
/*      */   
/*      */   Path2D() {}
/*      */   
/*      */   Path2D(int paramInt1, int paramInt2)
/*      */   {
/*  130 */     setWindingRule(paramInt1);
/*  131 */     this.pointTypes = new byte[paramInt2]; }
/*      */   
/*      */   abstract float[] cloneCoordsFloat(AffineTransform paramAffineTransform);
/*      */   
/*      */   abstract double[] cloneCoordsDouble(AffineTransform paramAffineTransform);
/*      */   
/*      */   abstract void append(float paramFloat1, float paramFloat2);
/*      */   
/*      */   abstract void append(double paramDouble1, double paramDouble2);
/*      */   
/*      */   abstract Point2D getPoint(int paramInt);
/*      */   
/*      */   abstract void needRoom(boolean paramBoolean, int paramInt);
/*      */   
/*      */   abstract int pointCrossings(double paramDouble1, double paramDouble2);
/*      */   
/*      */   abstract int rectCrossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   public abstract void moveTo(double paramDouble1, double paramDouble2);
/*      */   
/*      */   public abstract void lineTo(double paramDouble1, double paramDouble2);
/*      */   
/*      */   public abstract void quadTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   public abstract void curveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*      */   
/*      */   public static class Float extends Path2D implements Serializable { transient float[] floatCoords;
/*      */     private static final long serialVersionUID = 6990832515060788886L;
/*      */     
/*  160 */     public Float() { this(1, 20); }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Float(int paramInt)
/*      */     {
/*  174 */       this(paramInt, 20);
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
/*      */     public Float(int paramInt1, int paramInt2)
/*      */     {
/*  193 */       super(paramInt2);
/*  194 */       this.floatCoords = new float[paramInt2 * 2];
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
/*      */     public Float(Shape paramShape)
/*      */     {
/*  207 */       this(paramShape, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Float(Shape paramShape, AffineTransform paramAffineTransform)
/*      */     {
/*      */       Object localObject;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  223 */       if ((paramShape instanceof Path2D)) {
/*  224 */         localObject = (Path2D)paramShape;
/*  225 */         setWindingRule(((Path2D)localObject).windingRule);
/*  226 */         this.numTypes = ((Path2D)localObject).numTypes;
/*  227 */         this.pointTypes = Arrays.copyOf(((Path2D)localObject).pointTypes, ((Path2D)localObject).pointTypes.length);
/*      */         
/*  229 */         this.numCoords = ((Path2D)localObject).numCoords;
/*  230 */         this.floatCoords = ((Path2D)localObject).cloneCoordsFloat(paramAffineTransform);
/*      */       } else {
/*  232 */         localObject = paramShape.getPathIterator(paramAffineTransform);
/*  233 */         setWindingRule(((PathIterator)localObject).getWindingRule());
/*  234 */         this.pointTypes = new byte[20];
/*  235 */         this.floatCoords = new float[40];
/*  236 */         append((PathIterator)localObject, false);
/*      */       }
/*      */     }
/*      */     
/*      */     float[] cloneCoordsFloat(AffineTransform paramAffineTransform) {
/*      */       float[] arrayOfFloat;
/*  242 */       if (paramAffineTransform == null) {
/*  243 */         arrayOfFloat = Arrays.copyOf(this.floatCoords, this.floatCoords.length);
/*      */       } else {
/*  245 */         arrayOfFloat = new float[this.floatCoords.length];
/*  246 */         paramAffineTransform.transform(this.floatCoords, 0, arrayOfFloat, 0, this.numCoords / 2);
/*      */       }
/*  248 */       return arrayOfFloat;
/*      */     }
/*      */     
/*      */     double[] cloneCoordsDouble(AffineTransform paramAffineTransform) {
/*  252 */       double[] arrayOfDouble = new double[this.floatCoords.length];
/*  253 */       if (paramAffineTransform == null) {
/*  254 */         for (int i = 0; i < this.numCoords; i++) {
/*  255 */           arrayOfDouble[i] = this.floatCoords[i];
/*      */         }
/*      */       } else {
/*  258 */         paramAffineTransform.transform(this.floatCoords, 0, arrayOfDouble, 0, this.numCoords / 2);
/*      */       }
/*  260 */       return arrayOfDouble;
/*      */     }
/*      */     
/*      */     void append(float paramFloat1, float paramFloat2) {
/*  264 */       this.floatCoords[(this.numCoords++)] = paramFloat1;
/*  265 */       this.floatCoords[(this.numCoords++)] = paramFloat2;
/*      */     }
/*      */     
/*      */     void append(double paramDouble1, double paramDouble2) {
/*  269 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble1);
/*  270 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble2);
/*      */     }
/*      */     
/*      */     Point2D getPoint(int paramInt) {
/*  274 */       return new Point2D.Float(this.floatCoords[paramInt], this.floatCoords[(paramInt + 1)]);
/*      */     }
/*      */     
/*      */     void needRoom(boolean paramBoolean, int paramInt)
/*      */     {
/*  279 */       if ((paramBoolean) && (this.numTypes == 0)) {
/*  280 */         throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */       }
/*      */       
/*  283 */       int i = this.pointTypes.length;
/*  284 */       int j; if (this.numTypes >= i) {
/*  285 */         j = i;
/*  286 */         if (j > 500) {
/*  287 */           j = 500;
/*  288 */         } else if (j == 0) {
/*  289 */           j = 1;
/*      */         }
/*  291 */         this.pointTypes = Arrays.copyOf(this.pointTypes, i + j);
/*      */       }
/*  293 */       i = this.floatCoords.length;
/*  294 */       if (this.numCoords + paramInt > i) {
/*  295 */         j = i;
/*  296 */         if (j > 1000) {
/*  297 */           j = 1000;
/*      */         }
/*  299 */         if (j < paramInt) {
/*  300 */           j = paramInt;
/*      */         }
/*  302 */         this.floatCoords = Arrays.copyOf(this.floatCoords, i + j);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void moveTo(double paramDouble1, double paramDouble2)
/*      */     {
/*  311 */       if ((this.numTypes > 0) && (this.pointTypes[(this.numTypes - 1)] == 0)) {
/*  312 */         this.floatCoords[(this.numCoords - 2)] = ((float)paramDouble1);
/*  313 */         this.floatCoords[(this.numCoords - 1)] = ((float)paramDouble2);
/*      */       } else {
/*  315 */         needRoom(false, 2);
/*  316 */         this.pointTypes[(this.numTypes++)] = 0;
/*  317 */         this.floatCoords[(this.numCoords++)] = ((float)paramDouble1);
/*  318 */         this.floatCoords[(this.numCoords++)] = ((float)paramDouble2);
/*      */       }
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
/*      */     public final synchronized void moveTo(float paramFloat1, float paramFloat2)
/*      */     {
/*  336 */       if ((this.numTypes > 0) && (this.pointTypes[(this.numTypes - 1)] == 0)) {
/*  337 */         this.floatCoords[(this.numCoords - 2)] = paramFloat1;
/*  338 */         this.floatCoords[(this.numCoords - 1)] = paramFloat2;
/*      */       } else {
/*  340 */         needRoom(false, 2);
/*  341 */         this.pointTypes[(this.numTypes++)] = 0;
/*  342 */         this.floatCoords[(this.numCoords++)] = paramFloat1;
/*  343 */         this.floatCoords[(this.numCoords++)] = paramFloat2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void lineTo(double paramDouble1, double paramDouble2)
/*      */     {
/*  352 */       needRoom(true, 2);
/*  353 */       this.pointTypes[(this.numTypes++)] = 1;
/*  354 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble1);
/*  355 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble2);
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
/*      */     public final synchronized void lineTo(float paramFloat1, float paramFloat2)
/*      */     {
/*  373 */       needRoom(true, 2);
/*  374 */       this.pointTypes[(this.numTypes++)] = 1;
/*  375 */       this.floatCoords[(this.numCoords++)] = paramFloat1;
/*  376 */       this.floatCoords[(this.numCoords++)] = paramFloat2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void quadTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */     {
/*  386 */       needRoom(true, 4);
/*  387 */       this.pointTypes[(this.numTypes++)] = 2;
/*  388 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble1);
/*  389 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble2);
/*  390 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble3);
/*  391 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble4);
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
/*      */     public final synchronized void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */     {
/*  416 */       needRoom(true, 4);
/*  417 */       this.pointTypes[(this.numTypes++)] = 2;
/*  418 */       this.floatCoords[(this.numCoords++)] = paramFloat1;
/*  419 */       this.floatCoords[(this.numCoords++)] = paramFloat2;
/*  420 */       this.floatCoords[(this.numCoords++)] = paramFloat3;
/*  421 */       this.floatCoords[(this.numCoords++)] = paramFloat4;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void curveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */     {
/*  432 */       needRoom(true, 6);
/*  433 */       this.pointTypes[(this.numTypes++)] = 3;
/*  434 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble1);
/*  435 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble2);
/*  436 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble3);
/*  437 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble4);
/*  438 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble5);
/*  439 */       this.floatCoords[(this.numCoords++)] = ((float)paramDouble6);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */     {
/*  467 */       needRoom(true, 6);
/*  468 */       this.pointTypes[(this.numTypes++)] = 3;
/*  469 */       this.floatCoords[(this.numCoords++)] = paramFloat1;
/*  470 */       this.floatCoords[(this.numCoords++)] = paramFloat2;
/*  471 */       this.floatCoords[(this.numCoords++)] = paramFloat3;
/*  472 */       this.floatCoords[(this.numCoords++)] = paramFloat4;
/*  473 */       this.floatCoords[(this.numCoords++)] = paramFloat5;
/*  474 */       this.floatCoords[(this.numCoords++)] = paramFloat6;
/*      */     }
/*      */     
/*      */     int pointCrossings(double paramDouble1, double paramDouble2)
/*      */     {
/*  479 */       float[] arrayOfFloat = this.floatCoords;
/*  480 */       double d1; double d3 = d1 = arrayOfFloat[0];
/*  481 */       double d2; double d4 = d2 = arrayOfFloat[1];
/*  482 */       int i = 0;
/*  483 */       int j = 2;
/*  484 */       for (int k = 1; k < this.numTypes; k++) { double d5;
/*  485 */         double d6; switch (this.pointTypes[k]) {
/*      */         case 0: 
/*  487 */           if (d4 != d2)
/*      */           {
/*  489 */             i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d1, d2);
/*      */           }
/*      */           
/*      */ 
/*  493 */           d1 = d3 = arrayOfFloat[(j++)];
/*  494 */           d2 = d4 = arrayOfFloat[(j++)];
/*  495 */           break;
/*      */         
/*      */         case 1: 
/*  498 */           i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d5 = arrayOfFloat[(j++)], d6 = arrayOfFloat[(j++)]);
/*      */           
/*      */ 
/*      */ 
/*  502 */           d3 = d5;
/*  503 */           d4 = d6;
/*  504 */           break;
/*      */         
/*      */         case 2: 
/*  507 */           i = i + Curve.pointCrossingsForQuad(paramDouble1, paramDouble2, d3, d4, arrayOfFloat[(j++)], arrayOfFloat[(j++)], d5 = arrayOfFloat[(j++)], d6 = arrayOfFloat[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  514 */           d3 = d5;
/*  515 */           d4 = d6;
/*  516 */           break;
/*      */         
/*      */         case 3: 
/*  519 */           i = i + Curve.pointCrossingsForCubic(paramDouble1, paramDouble2, d3, d4, arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], d5 = arrayOfFloat[(j++)], d6 = arrayOfFloat[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  528 */           d3 = d5;
/*  529 */           d4 = d6;
/*  530 */           break;
/*      */         case 4: 
/*  532 */           if (d4 != d2)
/*      */           {
/*  534 */             i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d1, d2);
/*      */           }
/*      */           
/*      */ 
/*  538 */           d3 = d1;
/*  539 */           d4 = d2;
/*      */         }
/*      */         
/*      */       }
/*  543 */       if (d4 != d2)
/*      */       {
/*  545 */         i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d1, d2);
/*      */       }
/*      */       
/*      */ 
/*  549 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */     int rectCrossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */     {
/*  555 */       float[] arrayOfFloat = this.floatCoords;
/*      */       double d3;
/*  557 */       double d1 = d3 = arrayOfFloat[0];
/*  558 */       double d4; double d2 = d4 = arrayOfFloat[1];
/*  559 */       int i = 0;
/*  560 */       int j = 2;
/*  561 */       for (int k = 1; 
/*  562 */           (i != Integer.MIN_VALUE) && (k < this.numTypes); 
/*  563 */           k++) { double d5;
/*      */         double d6;
/*  565 */         switch (this.pointTypes[k]) {
/*      */         case 0: 
/*  567 */           if ((d1 != d3) || (d2 != d4))
/*      */           {
/*  569 */             i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  577 */           d3 = d1 = arrayOfFloat[(j++)];
/*  578 */           d4 = d2 = arrayOfFloat[(j++)];
/*  579 */           break;
/*      */         
/*      */         case 1: 
/*  582 */           i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d5 = arrayOfFloat[(j++)], d6 = arrayOfFloat[(j++)]);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  588 */           d1 = d5;
/*  589 */           d2 = d6;
/*  590 */           break;
/*      */         
/*      */         case 2: 
/*  593 */           i = Curve.rectCrossingsForQuad(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, arrayOfFloat[(j++)], arrayOfFloat[(j++)], d5 = arrayOfFloat[(j++)], d6 = arrayOfFloat[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  602 */           d1 = d5;
/*  603 */           d2 = d6;
/*  604 */           break;
/*      */         
/*      */         case 3: 
/*  607 */           i = Curve.rectCrossingsForCubic(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], d5 = arrayOfFloat[(j++)], d6 = arrayOfFloat[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  618 */           d1 = d5;
/*  619 */           d2 = d6;
/*  620 */           break;
/*      */         case 4: 
/*  622 */           if ((d1 != d3) || (d2 != d4))
/*      */           {
/*  624 */             i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  630 */           d1 = d3;
/*  631 */           d2 = d4;
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*  637 */       if ((i != Integer.MIN_VALUE) && ((d1 != d3) || (d2 != d4)))
/*      */       {
/*      */ 
/*      */ 
/*  641 */         i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  649 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void append(PathIterator paramPathIterator, boolean paramBoolean)
/*      */     {
/*  657 */       float[] arrayOfFloat = new float[6];
/*  658 */       while (!paramPathIterator.isDone()) {
/*  659 */         switch (paramPathIterator.currentSegment(arrayOfFloat)) {
/*      */         case 0: 
/*  661 */           if ((!paramBoolean) || (this.numTypes < 1) || (this.numCoords < 1)) {
/*  662 */             moveTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */ 
/*      */           }
/*  665 */           else if ((this.pointTypes[(this.numTypes - 1)] == 4) || (this.floatCoords[(this.numCoords - 2)] != arrayOfFloat[0]) || (this.floatCoords[(this.numCoords - 1)] != arrayOfFloat[1]))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  672 */             lineTo(arrayOfFloat[0], arrayOfFloat[1]); }
/*  673 */           break;
/*      */         case 1: 
/*  675 */           lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/*  676 */           break;
/*      */         case 2: 
/*  678 */           quadTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*      */           
/*  680 */           break;
/*      */         case 3: 
/*  682 */           curveTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*      */           
/*      */ 
/*  685 */           break;
/*      */         case 4: 
/*  687 */           closePath();
/*      */         }
/*      */         
/*  690 */         paramPathIterator.next();
/*  691 */         paramBoolean = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void transform(AffineTransform paramAffineTransform)
/*      */     {
/*  700 */       paramAffineTransform.transform(this.floatCoords, 0, this.floatCoords, 0, this.numCoords / 2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized Rectangle2D getBounds2D()
/*      */     {
/*  709 */       int i = this.numCoords;
/*  710 */       float f4; float f2; float f3; if (i > 0) {
/*  711 */         f2 = f4 = this.floatCoords[(--i)];
/*  712 */         f1 = f3 = this.floatCoords[(--i)];
/*  713 */         while (i > 0) {
/*  714 */           float f5 = this.floatCoords[(--i)];
/*  715 */           float f6 = this.floatCoords[(--i)];
/*  716 */           if (f6 < f1) f1 = f6;
/*  717 */           if (f5 < f2) f2 = f5;
/*  718 */           if (f6 > f3) f3 = f6;
/*  719 */           if (f5 > f4) f4 = f5;
/*      */         }
/*      */       }
/*  722 */       float f1 = f2 = f3 = f4 = 0.0F;
/*      */       
/*  724 */       return new Rectangle2D.Float(f1, f2, f3 - f1, f4 - f2);
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
/*      */     public final PathIterator getPathIterator(AffineTransform paramAffineTransform)
/*      */     {
/*  739 */       if (paramAffineTransform == null) {
/*  740 */         return new CopyIterator(this);
/*      */       }
/*  742 */       return new TxIterator(this, paramAffineTransform);
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
/*      */     public final Object clone()
/*      */     {
/*  760 */       if ((this instanceof GeneralPath)) {
/*  761 */         return new GeneralPath(this);
/*      */       }
/*  763 */       return new Float(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */       throws IOException
/*      */     {
/*  896 */       super.writeObject(paramObjectOutputStream, false);
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
/*      */     private void readObject(ObjectInputStream paramObjectInputStream)
/*      */       throws ClassNotFoundException, IOException
/*      */     {
/*  915 */       super.readObject(paramObjectInputStream, false);
/*      */     }
/*      */     
/*      */     static class CopyIterator extends Path2D.Iterator {
/*      */       float[] floatCoords;
/*      */       
/*      */       CopyIterator(Path2D.Float paramFloat) {
/*  922 */         super();
/*  923 */         this.floatCoords = paramFloat.floatCoords;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] paramArrayOfFloat) {
/*  927 */         int i = this.path.pointTypes[this.typeIdx];
/*  928 */         int j = curvecoords[i];
/*  929 */         if (j > 0) {
/*  930 */           System.arraycopy(this.floatCoords, this.pointIdx, paramArrayOfFloat, 0, j);
/*      */         }
/*      */         
/*  933 */         return i;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] paramArrayOfDouble) {
/*  937 */         int i = this.path.pointTypes[this.typeIdx];
/*  938 */         int j = curvecoords[i];
/*  939 */         if (j > 0) {
/*  940 */           for (int k = 0; k < j; k++) {
/*  941 */             paramArrayOfDouble[k] = this.floatCoords[(this.pointIdx + k)];
/*      */           }
/*      */         }
/*  944 */         return i;
/*      */       }
/*      */     }
/*      */     
/*      */     static class TxIterator extends Path2D.Iterator {
/*      */       float[] floatCoords;
/*      */       AffineTransform affine;
/*      */       
/*      */       TxIterator(Path2D.Float paramFloat, AffineTransform paramAffineTransform) {
/*  953 */         super();
/*  954 */         this.floatCoords = paramFloat.floatCoords;
/*  955 */         this.affine = paramAffineTransform;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] paramArrayOfFloat) {
/*  959 */         int i = this.path.pointTypes[this.typeIdx];
/*  960 */         int j = curvecoords[i];
/*  961 */         if (j > 0) {
/*  962 */           this.affine.transform(this.floatCoords, this.pointIdx, paramArrayOfFloat, 0, j / 2);
/*      */         }
/*      */         
/*  965 */         return i;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] paramArrayOfDouble) {
/*  969 */         int i = this.path.pointTypes[this.typeIdx];
/*  970 */         int j = curvecoords[i];
/*  971 */         if (j > 0) {
/*  972 */           this.affine.transform(this.floatCoords, this.pointIdx, paramArrayOfDouble, 0, j / 2);
/*      */         }
/*      */         
/*  975 */         return i;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class Double
/*      */     extends Path2D
/*      */     implements Serializable
/*      */   {
/*      */     transient double[] doubleCoords;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final long serialVersionUID = 1826762518450014216L;
/*      */     
/*      */ 
/*      */ 
/*      */     public Double()
/*      */     {
/*  997 */       this(1, 20);
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
/*      */     public Double(int paramInt)
/*      */     {
/* 1011 */       this(paramInt, 20);
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
/*      */     public Double(int paramInt1, int paramInt2)
/*      */     {
/* 1030 */       super(paramInt2);
/* 1031 */       this.doubleCoords = new double[paramInt2 * 2];
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
/*      */     public Double(Shape paramShape)
/*      */     {
/* 1044 */       this(paramShape, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Double(Shape paramShape, AffineTransform paramAffineTransform)
/*      */     {
/*      */       Object localObject;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1060 */       if ((paramShape instanceof Path2D)) {
/* 1061 */         localObject = (Path2D)paramShape;
/* 1062 */         setWindingRule(((Path2D)localObject).windingRule);
/* 1063 */         this.numTypes = ((Path2D)localObject).numTypes;
/* 1064 */         this.pointTypes = Arrays.copyOf(((Path2D)localObject).pointTypes, ((Path2D)localObject).pointTypes.length);
/*      */         
/* 1066 */         this.numCoords = ((Path2D)localObject).numCoords;
/* 1067 */         this.doubleCoords = ((Path2D)localObject).cloneCoordsDouble(paramAffineTransform);
/*      */       } else {
/* 1069 */         localObject = paramShape.getPathIterator(paramAffineTransform);
/* 1070 */         setWindingRule(((PathIterator)localObject).getWindingRule());
/* 1071 */         this.pointTypes = new byte[20];
/* 1072 */         this.doubleCoords = new double[40];
/* 1073 */         append((PathIterator)localObject, false);
/*      */       }
/*      */     }
/*      */     
/*      */     float[] cloneCoordsFloat(AffineTransform paramAffineTransform) {
/* 1078 */       float[] arrayOfFloat = new float[this.doubleCoords.length];
/* 1079 */       if (paramAffineTransform == null) {
/* 1080 */         for (int i = 0; i < this.numCoords; i++) {
/* 1081 */           arrayOfFloat[i] = ((float)this.doubleCoords[i]);
/*      */         }
/*      */       } else {
/* 1084 */         paramAffineTransform.transform(this.doubleCoords, 0, arrayOfFloat, 0, this.numCoords / 2);
/*      */       }
/* 1086 */       return arrayOfFloat;
/*      */     }
/*      */     
/*      */     double[] cloneCoordsDouble(AffineTransform paramAffineTransform) {
/*      */       double[] arrayOfDouble;
/* 1091 */       if (paramAffineTransform == null) {
/* 1092 */         arrayOfDouble = Arrays.copyOf(this.doubleCoords, this.doubleCoords.length);
/*      */       }
/*      */       else {
/* 1095 */         arrayOfDouble = new double[this.doubleCoords.length];
/* 1096 */         paramAffineTransform.transform(this.doubleCoords, 0, arrayOfDouble, 0, this.numCoords / 2);
/*      */       }
/* 1098 */       return arrayOfDouble;
/*      */     }
/*      */     
/*      */     void append(float paramFloat1, float paramFloat2) {
/* 1102 */       this.doubleCoords[(this.numCoords++)] = paramFloat1;
/* 1103 */       this.doubleCoords[(this.numCoords++)] = paramFloat2;
/*      */     }
/*      */     
/*      */     void append(double paramDouble1, double paramDouble2) {
/* 1107 */       this.doubleCoords[(this.numCoords++)] = paramDouble1;
/* 1108 */       this.doubleCoords[(this.numCoords++)] = paramDouble2;
/*      */     }
/*      */     
/*      */     Point2D getPoint(int paramInt) {
/* 1112 */       return new Point2D.Double(this.doubleCoords[paramInt], this.doubleCoords[(paramInt + 1)]);
/*      */     }
/*      */     
/*      */     void needRoom(boolean paramBoolean, int paramInt)
/*      */     {
/* 1117 */       if ((paramBoolean) && (this.numTypes == 0)) {
/* 1118 */         throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */       }
/*      */       
/* 1121 */       int i = this.pointTypes.length;
/* 1122 */       int j; if (this.numTypes >= i) {
/* 1123 */         j = i;
/* 1124 */         if (j > 500) {
/* 1125 */           j = 500;
/* 1126 */         } else if (j == 0) {
/* 1127 */           j = 1;
/*      */         }
/* 1129 */         this.pointTypes = Arrays.copyOf(this.pointTypes, i + j);
/*      */       }
/* 1131 */       i = this.doubleCoords.length;
/* 1132 */       if (this.numCoords + paramInt > i) {
/* 1133 */         j = i;
/* 1134 */         if (j > 1000) {
/* 1135 */           j = 1000;
/*      */         }
/* 1137 */         if (j < paramInt) {
/* 1138 */           j = paramInt;
/*      */         }
/* 1140 */         this.doubleCoords = Arrays.copyOf(this.doubleCoords, i + j);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void moveTo(double paramDouble1, double paramDouble2)
/*      */     {
/* 1149 */       if ((this.numTypes > 0) && (this.pointTypes[(this.numTypes - 1)] == 0)) {
/* 1150 */         this.doubleCoords[(this.numCoords - 2)] = paramDouble1;
/* 1151 */         this.doubleCoords[(this.numCoords - 1)] = paramDouble2;
/*      */       } else {
/* 1153 */         needRoom(false, 2);
/* 1154 */         this.pointTypes[(this.numTypes++)] = 0;
/* 1155 */         this.doubleCoords[(this.numCoords++)] = paramDouble1;
/* 1156 */         this.doubleCoords[(this.numCoords++)] = paramDouble2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void lineTo(double paramDouble1, double paramDouble2)
/*      */     {
/* 1165 */       needRoom(true, 2);
/* 1166 */       this.pointTypes[(this.numTypes++)] = 1;
/* 1167 */       this.doubleCoords[(this.numCoords++)] = paramDouble1;
/* 1168 */       this.doubleCoords[(this.numCoords++)] = paramDouble2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void quadTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */     {
/* 1178 */       needRoom(true, 4);
/* 1179 */       this.pointTypes[(this.numTypes++)] = 2;
/* 1180 */       this.doubleCoords[(this.numCoords++)] = paramDouble1;
/* 1181 */       this.doubleCoords[(this.numCoords++)] = paramDouble2;
/* 1182 */       this.doubleCoords[(this.numCoords++)] = paramDouble3;
/* 1183 */       this.doubleCoords[(this.numCoords++)] = paramDouble4;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized void curveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */     {
/* 1194 */       needRoom(true, 6);
/* 1195 */       this.pointTypes[(this.numTypes++)] = 3;
/* 1196 */       this.doubleCoords[(this.numCoords++)] = paramDouble1;
/* 1197 */       this.doubleCoords[(this.numCoords++)] = paramDouble2;
/* 1198 */       this.doubleCoords[(this.numCoords++)] = paramDouble3;
/* 1199 */       this.doubleCoords[(this.numCoords++)] = paramDouble4;
/* 1200 */       this.doubleCoords[(this.numCoords++)] = paramDouble5;
/* 1201 */       this.doubleCoords[(this.numCoords++)] = paramDouble6;
/*      */     }
/*      */     
/*      */     int pointCrossings(double paramDouble1, double paramDouble2)
/*      */     {
/* 1206 */       double[] arrayOfDouble = this.doubleCoords;
/* 1207 */       double d1; double d3 = d1 = arrayOfDouble[0];
/* 1208 */       double d2; double d4 = d2 = arrayOfDouble[1];
/* 1209 */       int i = 0;
/* 1210 */       int j = 2;
/* 1211 */       for (int k = 1; k < this.numTypes; k++) { double d5;
/* 1212 */         double d6; switch (this.pointTypes[k]) {
/*      */         case 0: 
/* 1214 */           if (d4 != d2)
/*      */           {
/* 1216 */             i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d1, d2);
/*      */           }
/*      */           
/*      */ 
/* 1220 */           d1 = d3 = arrayOfDouble[(j++)];
/* 1221 */           d2 = d4 = arrayOfDouble[(j++)];
/* 1222 */           break;
/*      */         
/*      */         case 1: 
/* 1225 */           i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d5 = arrayOfDouble[(j++)], d6 = arrayOfDouble[(j++)]);
/*      */           
/*      */ 
/*      */ 
/* 1229 */           d3 = d5;
/* 1230 */           d4 = d6;
/* 1231 */           break;
/*      */         
/*      */         case 2: 
/* 1234 */           i = i + Curve.pointCrossingsForQuad(paramDouble1, paramDouble2, d3, d4, arrayOfDouble[(j++)], arrayOfDouble[(j++)], d5 = arrayOfDouble[(j++)], d6 = arrayOfDouble[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1241 */           d3 = d5;
/* 1242 */           d4 = d6;
/* 1243 */           break;
/*      */         
/*      */         case 3: 
/* 1246 */           i = i + Curve.pointCrossingsForCubic(paramDouble1, paramDouble2, d3, d4, arrayOfDouble[(j++)], arrayOfDouble[(j++)], arrayOfDouble[(j++)], arrayOfDouble[(j++)], d5 = arrayOfDouble[(j++)], d6 = arrayOfDouble[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1255 */           d3 = d5;
/* 1256 */           d4 = d6;
/* 1257 */           break;
/*      */         case 4: 
/* 1259 */           if (d4 != d2)
/*      */           {
/* 1261 */             i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d1, d2);
/*      */           }
/*      */           
/*      */ 
/* 1265 */           d3 = d1;
/* 1266 */           d4 = d2;
/*      */         }
/*      */         
/*      */       }
/* 1270 */       if (d4 != d2)
/*      */       {
/* 1272 */         i = i + Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d3, d4, d1, d2);
/*      */       }
/*      */       
/*      */ 
/* 1276 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */     int rectCrossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */     {
/* 1282 */       double[] arrayOfDouble = this.doubleCoords;
/*      */       double d3;
/* 1284 */       double d1 = d3 = arrayOfDouble[0];
/* 1285 */       double d4; double d2 = d4 = arrayOfDouble[1];
/* 1286 */       int i = 0;
/* 1287 */       int j = 2;
/* 1288 */       for (int k = 1; 
/* 1289 */           (i != Integer.MIN_VALUE) && (k < this.numTypes); 
/* 1290 */           k++) { double d5;
/*      */         double d6;
/* 1292 */         switch (this.pointTypes[k]) {
/*      */         case 0: 
/* 1294 */           if ((d1 != d3) || (d2 != d4))
/*      */           {
/* 1296 */             i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1304 */           d3 = d1 = arrayOfDouble[(j++)];
/* 1305 */           d4 = d2 = arrayOfDouble[(j++)];
/* 1306 */           break;
/*      */         case 1: 
/* 1308 */           d5 = arrayOfDouble[(j++)];
/* 1309 */           d6 = arrayOfDouble[(j++)];
/*      */           
/* 1311 */           i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d5, d6);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1316 */           d1 = d5;
/* 1317 */           d2 = d6;
/* 1318 */           break;
/*      */         
/*      */         case 2: 
/* 1321 */           i = Curve.rectCrossingsForQuad(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, arrayOfDouble[(j++)], arrayOfDouble[(j++)], d5 = arrayOfDouble[(j++)], d6 = arrayOfDouble[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1330 */           d1 = d5;
/* 1331 */           d2 = d6;
/* 1332 */           break;
/*      */         
/*      */         case 3: 
/* 1335 */           i = Curve.rectCrossingsForCubic(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, arrayOfDouble[(j++)], arrayOfDouble[(j++)], arrayOfDouble[(j++)], arrayOfDouble[(j++)], d5 = arrayOfDouble[(j++)], d6 = arrayOfDouble[(j++)], 0);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1346 */           d1 = d5;
/* 1347 */           d2 = d6;
/* 1348 */           break;
/*      */         case 4: 
/* 1350 */           if ((d1 != d3) || (d2 != d4))
/*      */           {
/* 1352 */             i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1358 */           d1 = d3;
/* 1359 */           d2 = d4;
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/* 1365 */       if ((i != Integer.MIN_VALUE) && ((d1 != d3) || (d2 != d4)))
/*      */       {
/*      */ 
/*      */ 
/* 1369 */         i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1377 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void append(PathIterator paramPathIterator, boolean paramBoolean)
/*      */     {
/* 1385 */       double[] arrayOfDouble = new double[6];
/* 1386 */       while (!paramPathIterator.isDone()) {
/* 1387 */         switch (paramPathIterator.currentSegment(arrayOfDouble)) {
/*      */         case 0: 
/* 1389 */           if ((!paramBoolean) || (this.numTypes < 1) || (this.numCoords < 1)) {
/* 1390 */             moveTo(arrayOfDouble[0], arrayOfDouble[1]);
/*      */ 
/*      */           }
/* 1393 */           else if ((this.pointTypes[(this.numTypes - 1)] == 4) || (this.doubleCoords[(this.numCoords - 2)] != arrayOfDouble[0]) || (this.doubleCoords[(this.numCoords - 1)] != arrayOfDouble[1]))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1400 */             lineTo(arrayOfDouble[0], arrayOfDouble[1]); }
/* 1401 */           break;
/*      */         case 1: 
/* 1403 */           lineTo(arrayOfDouble[0], arrayOfDouble[1]);
/* 1404 */           break;
/*      */         case 2: 
/* 1406 */           quadTo(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3]);
/*      */           
/* 1408 */           break;
/*      */         case 3: 
/* 1410 */           curveTo(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3], arrayOfDouble[4], arrayOfDouble[5]);
/*      */           
/*      */ 
/* 1413 */           break;
/*      */         case 4: 
/* 1415 */           closePath();
/*      */         }
/*      */         
/* 1418 */         paramPathIterator.next();
/* 1419 */         paramBoolean = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void transform(AffineTransform paramAffineTransform)
/*      */     {
/* 1428 */       paramAffineTransform.transform(this.doubleCoords, 0, this.doubleCoords, 0, this.numCoords / 2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final synchronized Rectangle2D getBounds2D()
/*      */     {
/* 1437 */       int i = this.numCoords;
/* 1438 */       double d4; double d2; double d3; if (i > 0) {
/* 1439 */         d2 = d4 = this.doubleCoords[(--i)];
/* 1440 */         d1 = d3 = this.doubleCoords[(--i)];
/* 1441 */         while (i > 0) {
/* 1442 */           double d5 = this.doubleCoords[(--i)];
/* 1443 */           double d6 = this.doubleCoords[(--i)];
/* 1444 */           if (d6 < d1) d1 = d6;
/* 1445 */           if (d5 < d2) d2 = d5;
/* 1446 */           if (d6 > d3) d3 = d6;
/* 1447 */           if (d5 > d4) d4 = d5;
/*      */         }
/*      */       }
/* 1450 */       double d1 = d2 = d3 = d4 = 0.0D;
/*      */       
/* 1452 */       return new Rectangle2D.Double(d1, d2, d3 - d1, d4 - d2);
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
/*      */     public final PathIterator getPathIterator(AffineTransform paramAffineTransform)
/*      */     {
/* 1471 */       if (paramAffineTransform == null) {
/* 1472 */         return new CopyIterator(this);
/*      */       }
/* 1474 */       return new TxIterator(this, paramAffineTransform);
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
/*      */     public final Object clone()
/*      */     {
/* 1492 */       return new Double(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */       throws IOException
/*      */     {
/* 1624 */       super.writeObject(paramObjectOutputStream, true);
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
/*      */     private void readObject(ObjectInputStream paramObjectInputStream)
/*      */       throws ClassNotFoundException, IOException
/*      */     {
/* 1643 */       super.readObject(paramObjectInputStream, true);
/*      */     }
/*      */     
/*      */     static class CopyIterator extends Path2D.Iterator {
/*      */       double[] doubleCoords;
/*      */       
/*      */       CopyIterator(Path2D.Double paramDouble) {
/* 1650 */         super();
/* 1651 */         this.doubleCoords = paramDouble.doubleCoords;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] paramArrayOfFloat) {
/* 1655 */         int i = this.path.pointTypes[this.typeIdx];
/* 1656 */         int j = curvecoords[i];
/* 1657 */         if (j > 0) {
/* 1658 */           for (int k = 0; k < j; k++) {
/* 1659 */             paramArrayOfFloat[k] = ((float)this.doubleCoords[(this.pointIdx + k)]);
/*      */           }
/*      */         }
/* 1662 */         return i;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] paramArrayOfDouble) {
/* 1666 */         int i = this.path.pointTypes[this.typeIdx];
/* 1667 */         int j = curvecoords[i];
/* 1668 */         if (j > 0) {
/* 1669 */           System.arraycopy(this.doubleCoords, this.pointIdx, paramArrayOfDouble, 0, j);
/*      */         }
/*      */         
/* 1672 */         return i;
/*      */       }
/*      */     }
/*      */     
/*      */     static class TxIterator extends Path2D.Iterator {
/*      */       double[] doubleCoords;
/*      */       AffineTransform affine;
/*      */       
/*      */       TxIterator(Path2D.Double paramDouble, AffineTransform paramAffineTransform) {
/* 1681 */         super();
/* 1682 */         this.doubleCoords = paramDouble.doubleCoords;
/* 1683 */         this.affine = paramAffineTransform;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] paramArrayOfFloat) {
/* 1687 */         int i = this.path.pointTypes[this.typeIdx];
/* 1688 */         int j = curvecoords[i];
/* 1689 */         if (j > 0) {
/* 1690 */           this.affine.transform(this.doubleCoords, this.pointIdx, paramArrayOfFloat, 0, j / 2);
/*      */         }
/*      */         
/* 1693 */         return i;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] paramArrayOfDouble) {
/* 1697 */         int i = this.path.pointTypes[this.typeIdx];
/* 1698 */         int j = curvecoords[i];
/* 1699 */         if (j > 0) {
/* 1700 */           this.affine.transform(this.doubleCoords, this.pointIdx, paramArrayOfDouble, 0, j / 2);
/*      */         }
/*      */         
/* 1703 */         return i;
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized void closePath()
/*      */   {
/* 1774 */     if ((this.numTypes == 0) || (this.pointTypes[(this.numTypes - 1)] != 4)) {
/* 1775 */       needRoom(true, 0);
/* 1776 */       this.pointTypes[(this.numTypes++)] = 4;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void append(Shape paramShape, boolean paramBoolean)
/*      */   {
/* 1803 */     append(paramShape.getPathIterator(null), paramBoolean);
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
/*      */   public abstract void append(PathIterator paramPathIterator, boolean paramBoolean);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized int getWindingRule()
/*      */   {
/* 1841 */     return this.windingRule;
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
/*      */   public final void setWindingRule(int paramInt)
/*      */   {
/* 1857 */     if ((paramInt != 0) && (paramInt != 1)) {
/* 1858 */       throw new IllegalArgumentException("winding rule must be WIND_EVEN_ODD or WIND_NON_ZERO");
/*      */     }
/*      */     
/*      */ 
/* 1862 */     this.windingRule = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized Point2D getCurrentPoint()
/*      */   {
/* 1874 */     int i = this.numCoords;
/* 1875 */     if ((this.numTypes < 1) || (i < 1)) {
/* 1876 */       return null;
/*      */     }
/* 1878 */     if (this.pointTypes[(this.numTypes - 1)] == 4)
/*      */     {
/* 1880 */       for (int j = this.numTypes - 2; j > 0; j--) {
/* 1881 */         switch (this.pointTypes[j]) {
/*      */         case 0: 
/*      */           break;
/*      */         case 1: 
/* 1885 */           i -= 2;
/* 1886 */           break;
/*      */         case 2: 
/* 1888 */           i -= 4;
/* 1889 */           break;
/*      */         case 3: 
/* 1891 */           i -= 6;
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1898 */     return getPoint(i - 2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized void reset()
/*      */   {
/* 1909 */     this.numTypes = (this.numCoords = 0);
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
/*      */   public abstract void transform(AffineTransform paramAffineTransform);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized Shape createTransformedShape(AffineTransform paramAffineTransform)
/*      */   {
/* 1945 */     Path2D localPath2D = (Path2D)clone();
/* 1946 */     if (paramAffineTransform != null) {
/* 1947 */       localPath2D.transform(paramAffineTransform);
/*      */     }
/* 1949 */     return localPath2D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Rectangle getBounds()
/*      */   {
/* 1957 */     return getBounds2D().getBounds();
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
/*      */   public static boolean contains(PathIterator paramPathIterator, double paramDouble1, double paramDouble2)
/*      */   {
/* 1976 */     if (paramDouble1 * 0.0D + paramDouble2 * 0.0D == 0.0D)
/*      */     {
/*      */ 
/*      */ 
/* 1980 */       int i = paramPathIterator.getWindingRule() == 1 ? -1 : 1;
/* 1981 */       int j = Curve.pointCrossingsForPath(paramPathIterator, paramDouble1, paramDouble2);
/* 1982 */       return (j & i) != 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1989 */     return false;
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
/*      */   public static boolean contains(PathIterator paramPathIterator, Point2D paramPoint2D)
/*      */   {
/* 2008 */     return contains(paramPathIterator, paramPoint2D.getX(), paramPoint2D.getY());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean contains(double paramDouble1, double paramDouble2)
/*      */   {
/* 2016 */     if (paramDouble1 * 0.0D + paramDouble2 * 0.0D == 0.0D)
/*      */     {
/*      */ 
/*      */ 
/* 2020 */       if (this.numTypes < 2) {
/* 2021 */         return false;
/*      */       }
/* 2023 */       int i = this.windingRule == 1 ? -1 : 1;
/* 2024 */       return (pointCrossings(paramDouble1, paramDouble2) & i) != 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2031 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean contains(Point2D paramPoint2D)
/*      */   {
/* 2040 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
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
/*      */   public static boolean contains(PathIterator paramPathIterator, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 2077 */     if ((Double.isNaN(paramDouble1 + paramDouble3)) || (Double.isNaN(paramDouble2 + paramDouble4)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2086 */       return false;
/*      */     }
/* 2088 */     if ((paramDouble3 <= 0.0D) || (paramDouble4 <= 0.0D)) {
/* 2089 */       return false;
/*      */     }
/* 2091 */     int i = paramPathIterator.getWindingRule() == 1 ? -1 : 2;
/* 2092 */     int j = Curve.rectCrossingsForPath(paramPathIterator, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2093 */     return (j != Integer.MIN_VALUE) && ((j & i) != 0);
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
/*      */   public static boolean contains(PathIterator paramPathIterator, Rectangle2D paramRectangle2D)
/*      */   {
/* 2126 */     return contains(paramPathIterator, paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public final boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 2149 */     if ((Double.isNaN(paramDouble1 + paramDouble3)) || (Double.isNaN(paramDouble2 + paramDouble4)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2158 */       return false;
/*      */     }
/* 2160 */     if ((paramDouble3 <= 0.0D) || (paramDouble4 <= 0.0D)) {
/* 2161 */       return false;
/*      */     }
/* 2163 */     int i = this.windingRule == 1 ? -1 : 2;
/* 2164 */     int j = rectCrossings(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2165 */     return (j != Integer.MIN_VALUE) && ((j & i) != 0);
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
/*      */   public final boolean contains(Rectangle2D paramRectangle2D)
/*      */   {
/* 2189 */     return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public static boolean intersects(PathIterator paramPathIterator, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 2227 */     if ((Double.isNaN(paramDouble1 + paramDouble3)) || (Double.isNaN(paramDouble2 + paramDouble4)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2236 */       return false;
/*      */     }
/* 2238 */     if ((paramDouble3 <= 0.0D) || (paramDouble4 <= 0.0D)) {
/* 2239 */       return false;
/*      */     }
/* 2241 */     int i = paramPathIterator.getWindingRule() == 1 ? -1 : 2;
/* 2242 */     int j = Curve.rectCrossingsForPath(paramPathIterator, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2243 */     return (j == Integer.MIN_VALUE) || ((j & i) != 0);
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
/*      */   public static boolean intersects(PathIterator paramPathIterator, Rectangle2D paramRectangle2D)
/*      */   {
/* 2276 */     return intersects(paramPathIterator, paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public final boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 2298 */     if ((Double.isNaN(paramDouble1 + paramDouble3)) || (Double.isNaN(paramDouble2 + paramDouble4)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2307 */       return false;
/*      */     }
/* 2309 */     if ((paramDouble3 <= 0.0D) || (paramDouble4 <= 0.0D)) {
/* 2310 */       return false;
/*      */     }
/* 2312 */     int i = this.windingRule == 1 ? -1 : 2;
/* 2313 */     int j = rectCrossings(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2314 */     return (j == Integer.MIN_VALUE) || ((j & i) != 0);
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
/*      */   public final boolean intersects(Rectangle2D paramRectangle2D)
/*      */   {
/* 2337 */     return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public final PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble)
/*      */   {
/* 2354 */     return new FlatteningPathIterator(getPathIterator(paramAffineTransform), paramDouble);
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
/*      */   public abstract Object clone();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void writeObject(ObjectOutputStream paramObjectOutputStream, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2394 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*      */     double[] arrayOfDouble;
/*      */     
/*      */     float[] arrayOfFloat;
/* 2399 */     if (paramBoolean) {
/* 2400 */       arrayOfDouble = ((Double)this).doubleCoords;
/* 2401 */       arrayOfFloat = null;
/*      */     } else {
/* 2403 */       arrayOfFloat = ((Float)this).floatCoords;
/* 2404 */       arrayOfDouble = null;
/*      */     }
/*      */     
/* 2407 */     int i = this.numTypes;
/*      */     
/* 2409 */     paramObjectOutputStream.writeByte(paramBoolean ? 49 : 48);
/*      */     
/*      */ 
/* 2412 */     paramObjectOutputStream.writeInt(i);
/* 2413 */     paramObjectOutputStream.writeInt(this.numCoords);
/* 2414 */     paramObjectOutputStream.writeByte((byte)this.windingRule);
/*      */     
/* 2416 */     int j = 0;
/* 2417 */     for (int k = 0; k < i; k++) {
/*      */       int m;
/*      */       int n;
/* 2420 */       switch (this.pointTypes[k]) {
/*      */       case 0: 
/* 2422 */         m = 1;
/* 2423 */         n = paramBoolean ? 80 : 64;
/*      */         
/*      */ 
/* 2426 */         break;
/*      */       case 1: 
/* 2428 */         m = 1;
/* 2429 */         n = paramBoolean ? 81 : 65;
/*      */         
/*      */ 
/* 2432 */         break;
/*      */       case 2: 
/* 2434 */         m = 2;
/* 2435 */         n = paramBoolean ? 82 : 66;
/*      */         
/*      */ 
/* 2438 */         break;
/*      */       case 3: 
/* 2440 */         m = 3;
/* 2441 */         n = paramBoolean ? 83 : 67;
/*      */         
/*      */ 
/* 2444 */         break;
/*      */       case 4: 
/* 2446 */         m = 0;
/* 2447 */         n = 96;
/* 2448 */         break;
/*      */       
/*      */ 
/*      */       default: 
/* 2452 */         throw new InternalError("unrecognized path type");
/*      */       }
/* 2454 */       paramObjectOutputStream.writeByte(n);
/* 2455 */       for (;;) { m--; if (m < 0) break;
/* 2456 */         if (paramBoolean) {
/* 2457 */           paramObjectOutputStream.writeDouble(arrayOfDouble[(j++)]);
/* 2458 */           paramObjectOutputStream.writeDouble(arrayOfDouble[(j++)]);
/*      */         } else {
/* 2460 */           paramObjectOutputStream.writeFloat(arrayOfFloat[(j++)]);
/* 2461 */           paramObjectOutputStream.writeFloat(arrayOfFloat[(j++)]);
/*      */         }
/*      */       }
/*      */     }
/* 2465 */     paramObjectOutputStream.writeByte(97);
/*      */   }
/*      */   
/*      */   final void readObject(ObjectInputStream paramObjectInputStream, boolean paramBoolean)
/*      */     throws ClassNotFoundException, IOException
/*      */   {
/* 2471 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2476 */     paramObjectInputStream.readByte();
/* 2477 */     int i = paramObjectInputStream.readInt();
/* 2478 */     int j = paramObjectInputStream.readInt();
/*      */     try {
/* 2480 */       setWindingRule(paramObjectInputStream.readByte());
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 2482 */       throw new InvalidObjectException(localIllegalArgumentException.getMessage());
/*      */     }
/*      */     
/* 2485 */     this.pointTypes = new byte[i < 0 ? 20 : i];
/* 2486 */     if (j < 0) {
/* 2487 */       j = 40;
/*      */     }
/* 2489 */     if (paramBoolean) {
/* 2490 */       ((Double)this).doubleCoords = new double[j];
/*      */     } else {
/* 2492 */       ((Float)this).floatCoords = new float[j];
/*      */     }
/*      */     
/*      */ 
/* 2496 */     for (int k = 0; (i < 0) || (k < i); k++)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2501 */       int i2 = paramObjectInputStream.readByte();
/* 2502 */       int m; int n; int i1; switch (i2) {
/*      */       case 64: 
/* 2504 */         m = 0;
/* 2505 */         n = 1;
/* 2506 */         i1 = 0;
/* 2507 */         break;
/*      */       case 65: 
/* 2509 */         m = 0;
/* 2510 */         n = 1;
/* 2511 */         i1 = 1;
/* 2512 */         break;
/*      */       case 66: 
/* 2514 */         m = 0;
/* 2515 */         n = 2;
/* 2516 */         i1 = 2;
/* 2517 */         break;
/*      */       case 67: 
/* 2519 */         m = 0;
/* 2520 */         n = 3;
/* 2521 */         i1 = 3;
/* 2522 */         break;
/*      */       
/*      */       case 80: 
/* 2525 */         m = 1;
/* 2526 */         n = 1;
/* 2527 */         i1 = 0;
/* 2528 */         break;
/*      */       case 81: 
/* 2530 */         m = 1;
/* 2531 */         n = 1;
/* 2532 */         i1 = 1;
/* 2533 */         break;
/*      */       case 82: 
/* 2535 */         m = 1;
/* 2536 */         n = 2;
/* 2537 */         i1 = 2;
/* 2538 */         break;
/*      */       case 83: 
/* 2540 */         m = 1;
/* 2541 */         n = 3;
/* 2542 */         i1 = 3;
/* 2543 */         break;
/*      */       
/*      */       case 96: 
/* 2546 */         m = 0;
/* 2547 */         n = 0;
/* 2548 */         i1 = 4;
/* 2549 */         break;
/*      */       
/*      */       case 97: 
/* 2552 */         if (i < 0) {
/*      */           break label500;
/*      */         }
/* 2555 */         throw new StreamCorruptedException("unexpected PATH_END");
/*      */       case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 84: 
/*      */       case 85: case 86: case 87: case 88: case 89: case 90: case 91: case 92: case 93: case 94: case 95: default: 
/* 2558 */         throw new StreamCorruptedException("unrecognized path type");
/*      */       }
/* 2560 */       needRoom(i1 != 0, n * 2);
/* 2561 */       if (m != 0)
/* 2562 */         for (;;) { n--; if (n < 0) break;
/* 2563 */           append(paramObjectInputStream.readDouble(), paramObjectInputStream.readDouble());
/*      */         }
/*      */       for (;;) {
/* 2566 */         n--; if (n < 0) break;
/* 2567 */         append(paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat());
/*      */       }
/*      */       
/* 2570 */       this.pointTypes[(this.numTypes++)] = i1; }
/*      */     label500:
/* 2572 */     if ((i >= 0) && (paramObjectInputStream.readByte() != 97)) {
/* 2573 */       throw new StreamCorruptedException("missing PATH_END");
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Iterator implements PathIterator
/*      */   {
/*      */     int typeIdx;
/*      */     int pointIdx;
/*      */     Path2D path;
/* 2582 */     static final int[] curvecoords = { 2, 2, 4, 6, 0 };
/*      */     
/*      */     Iterator(Path2D paramPath2D) {
/* 2585 */       this.path = paramPath2D;
/*      */     }
/*      */     
/*      */     public int getWindingRule() {
/* 2589 */       return this.path.getWindingRule();
/*      */     }
/*      */     
/*      */     public boolean isDone() {
/* 2593 */       return this.typeIdx >= this.path.numTypes;
/*      */     }
/*      */     
/*      */     public void next() {
/* 2597 */       int i = this.path.pointTypes[(this.typeIdx++)];
/* 2598 */       this.pointIdx += curvecoords[i];
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/geom/Path2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */