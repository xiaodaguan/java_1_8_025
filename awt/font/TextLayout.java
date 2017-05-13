/*      */ package java.awt.font;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.Rectangle2D.Float;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedCharacterIterator.Attribute;
/*      */ import java.text.AttributedString;
/*      */ import java.util.Map;
/*      */ import sun.font.AttributeValues;
/*      */ import sun.font.CoreMetrics;
/*      */ import sun.font.FontResolver;
/*      */ import sun.font.GraphicComponent;
/*      */ import sun.font.LayoutPathImpl;
/*      */ import sun.text.CodePointIterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TextLayout
/*      */   implements Cloneable
/*      */ {
/*      */   private int characterCount;
/*  242 */   private boolean isVerticalLine = false;
/*      */   
/*      */   private byte baseline;
/*      */   
/*      */   private float[] baselineOffsets;
/*      */   
/*      */   private TextLine textLine;
/*  249 */   private TextLine.TextLineMetrics lineMetrics = null;
/*      */   
/*      */ 
/*      */   private float visibleAdvance;
/*      */   
/*      */ 
/*      */   private int hashCodeCache;
/*      */   
/*      */ 
/*  258 */   private boolean cacheIsValid = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private float justifyRatio;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final float ALREADY_JUSTIFIED = -53.9F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static float dx;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static float dy;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  286 */   private Rectangle2D naturalBounds = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  292 */   private Rectangle2D boundsRect = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  298 */   private boolean caretsInLigaturesAreAllowed = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class CaretPolicy
/*      */   {
/*      */     public TextHitInfo getStrongCaret(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2, TextLayout paramTextLayout)
/*      */     {
/*  341 */       return paramTextLayout.getStrongHit(paramTextHitInfo1, paramTextHitInfo2);
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
/*  353 */   public static final CaretPolicy DEFAULT_CARET_POLICY = new CaretPolicy();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TextLayout(String paramString, Font paramFont, FontRenderContext paramFontRenderContext)
/*      */   {
/*  374 */     if (paramFont == null) {
/*  375 */       throw new IllegalArgumentException("Null font passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  378 */     if (paramString == null) {
/*  379 */       throw new IllegalArgumentException("Null string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  382 */     if (paramString.length() == 0) {
/*  383 */       throw new IllegalArgumentException("Zero length string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  386 */     Map localMap = null;
/*  387 */     if (paramFont.hasLayoutAttributes()) {
/*  388 */       localMap = paramFont.getAttributes();
/*      */     }
/*      */     
/*  391 */     char[] arrayOfChar = paramString.toCharArray();
/*  392 */     if (sameBaselineUpTo(paramFont, arrayOfChar, 0, arrayOfChar.length) == arrayOfChar.length) {
/*  393 */       fastInit(arrayOfChar, paramFont, localMap, paramFontRenderContext);
/*      */     } else {
/*  395 */       AttributedString localAttributedString = localMap == null ? new AttributedString(paramString) : new AttributedString(paramString, localMap);
/*      */       
/*      */ 
/*  398 */       localAttributedString.addAttribute(TextAttribute.FONT, paramFont);
/*  399 */       standardInit(localAttributedString.getIterator(), arrayOfChar, paramFontRenderContext);
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
/*      */   public TextLayout(String paramString, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, FontRenderContext paramFontRenderContext)
/*      */   {
/*  423 */     if (paramString == null) {
/*  424 */       throw new IllegalArgumentException("Null string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  427 */     if (paramMap == null) {
/*  428 */       throw new IllegalArgumentException("Null map passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  431 */     if (paramString.length() == 0) {
/*  432 */       throw new IllegalArgumentException("Zero length string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  435 */     char[] arrayOfChar = paramString.toCharArray();
/*  436 */     Font localFont = singleFont(arrayOfChar, 0, arrayOfChar.length, paramMap);
/*  437 */     if (localFont != null) {
/*  438 */       fastInit(arrayOfChar, localFont, paramMap, paramFontRenderContext);
/*      */     } else {
/*  440 */       AttributedString localAttributedString = new AttributedString(paramString, paramMap);
/*  441 */       standardInit(localAttributedString.getIterator(), arrayOfChar, paramFontRenderContext);
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
/*      */   private static Font singleFont(char[] paramArrayOfChar, int paramInt1, int paramInt2, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*      */   {
/*  457 */     if (paramMap.get(TextAttribute.CHAR_REPLACEMENT) != null) {
/*  458 */       return null;
/*      */     }
/*      */     
/*  461 */     Font localFont = null;
/*      */     try {
/*  463 */       localFont = (Font)paramMap.get(TextAttribute.FONT);
/*      */     }
/*      */     catch (ClassCastException localClassCastException) {}
/*      */     
/*  467 */     if (localFont == null) {
/*  468 */       if (paramMap.get(TextAttribute.FAMILY) != null) {
/*  469 */         localFont = Font.getFont(paramMap);
/*  470 */         if (localFont.canDisplayUpTo(paramArrayOfChar, paramInt1, paramInt2) != -1) {
/*  471 */           return null;
/*      */         }
/*      */       } else {
/*  474 */         FontResolver localFontResolver = FontResolver.getInstance();
/*  475 */         CodePointIterator localCodePointIterator = CodePointIterator.create(paramArrayOfChar, paramInt1, paramInt2);
/*  476 */         int i = localFontResolver.nextFontRunIndex(localCodePointIterator);
/*  477 */         if (localCodePointIterator.charIndex() == paramInt2) {
/*  478 */           localFont = localFontResolver.getFont(i, paramMap);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  483 */     if (sameBaselineUpTo(localFont, paramArrayOfChar, paramInt1, paramInt2) != paramInt2) {
/*  484 */       return null;
/*      */     }
/*      */     
/*  487 */     return localFont;
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
/*      */   public TextLayout(AttributedCharacterIterator paramAttributedCharacterIterator, FontRenderContext paramFontRenderContext)
/*      */   {
/*  506 */     if (paramAttributedCharacterIterator == null) {
/*  507 */       throw new IllegalArgumentException("Null iterator passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  510 */     int i = paramAttributedCharacterIterator.getBeginIndex();
/*  511 */     int j = paramAttributedCharacterIterator.getEndIndex();
/*  512 */     if (i == j) {
/*  513 */       throw new IllegalArgumentException("Zero length iterator passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  516 */     int k = j - i;
/*  517 */     paramAttributedCharacterIterator.first();
/*  518 */     char[] arrayOfChar = new char[k];
/*  519 */     int m = 0;
/*  520 */     for (int n = paramAttributedCharacterIterator.first(); 
/*  521 */         n != 65535; 
/*  522 */         n = paramAttributedCharacterIterator.next())
/*      */     {
/*  524 */       arrayOfChar[(m++)] = n;
/*      */     }
/*      */     
/*  527 */     paramAttributedCharacterIterator.first();
/*  528 */     if (paramAttributedCharacterIterator.getRunLimit() == j)
/*      */     {
/*  530 */       Map localMap = paramAttributedCharacterIterator.getAttributes();
/*  531 */       Font localFont = singleFont(arrayOfChar, 0, k, localMap);
/*  532 */       if (localFont != null) {
/*  533 */         fastInit(arrayOfChar, localFont, localMap, paramFontRenderContext);
/*  534 */         return;
/*      */       }
/*      */     }
/*      */     
/*  538 */     standardInit(paramAttributedCharacterIterator, arrayOfChar, paramFontRenderContext);
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
/*      */   TextLayout(TextLine paramTextLine, byte paramByte, float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/*  558 */     this.characterCount = paramTextLine.characterCount();
/*  559 */     this.baseline = paramByte;
/*  560 */     this.baselineOffsets = paramArrayOfFloat;
/*  561 */     this.textLine = paramTextLine;
/*  562 */     this.justifyRatio = paramFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void paragraphInit(byte paramByte, CoreMetrics paramCoreMetrics, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, char[] paramArrayOfChar)
/*      */   {
/*  572 */     this.baseline = paramByte;
/*      */     
/*      */ 
/*  575 */     this.baselineOffsets = TextLine.getNormalizedOffsets(paramCoreMetrics.baselineOffsets, this.baseline);
/*      */     
/*  577 */     this.justifyRatio = AttributeValues.getJustification(paramMap);
/*  578 */     NumericShaper localNumericShaper = AttributeValues.getNumericShaping(paramMap);
/*  579 */     if (localNumericShaper != null) {
/*  580 */       localNumericShaper.shape(paramArrayOfChar, 0, paramArrayOfChar.length);
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
/*      */   private void fastInit(char[] paramArrayOfChar, Font paramFont, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, FontRenderContext paramFontRenderContext)
/*      */   {
/*  596 */     this.isVerticalLine = false;
/*      */     
/*  598 */     LineMetrics localLineMetrics = paramFont.getLineMetrics(paramArrayOfChar, 0, paramArrayOfChar.length, paramFontRenderContext);
/*  599 */     CoreMetrics localCoreMetrics = CoreMetrics.get(localLineMetrics);
/*  600 */     byte b = (byte)localCoreMetrics.baselineIndex;
/*      */     
/*  602 */     if (paramMap == null) {
/*  603 */       this.baseline = b;
/*  604 */       this.baselineOffsets = localCoreMetrics.baselineOffsets;
/*  605 */       this.justifyRatio = 1.0F;
/*      */     } else {
/*  607 */       paragraphInit(b, localCoreMetrics, paramMap, paramArrayOfChar);
/*      */     }
/*      */     
/*  610 */     this.characterCount = paramArrayOfChar.length;
/*      */     
/*  612 */     this.textLine = TextLine.fastCreateTextLine(paramFontRenderContext, paramArrayOfChar, paramFont, localCoreMetrics, paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void standardInit(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfChar, FontRenderContext paramFontRenderContext)
/*      */   {
/*  622 */     this.characterCount = paramArrayOfChar.length;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  631 */     Map localMap = paramAttributedCharacterIterator.getAttributes();
/*      */     
/*  633 */     boolean bool = TextLine.advanceToFirstFont(paramAttributedCharacterIterator);
/*      */     Object localObject1;
/*  635 */     int i; Object localObject2; if (bool) {
/*  636 */       localObject1 = TextLine.getFontAtCurrentPos(paramAttributedCharacterIterator);
/*  637 */       i = paramAttributedCharacterIterator.getIndex() - paramAttributedCharacterIterator.getBeginIndex();
/*  638 */       localObject2 = ((Font)localObject1).getLineMetrics(paramArrayOfChar, i, i + 1, paramFontRenderContext);
/*  639 */       CoreMetrics localCoreMetrics = CoreMetrics.get((LineMetrics)localObject2);
/*  640 */       paragraphInit((byte)localCoreMetrics.baselineIndex, localCoreMetrics, localMap, paramArrayOfChar);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  647 */       localObject1 = (GraphicAttribute)localMap.get(TextAttribute.CHAR_REPLACEMENT);
/*  648 */       i = getBaselineFromGraphic((GraphicAttribute)localObject1);
/*  649 */       localObject2 = GraphicComponent.createCoreMetrics((GraphicAttribute)localObject1);
/*  650 */       paragraphInit(i, (CoreMetrics)localObject2, localMap, paramArrayOfChar);
/*      */     }
/*      */     
/*      */ 
/*  654 */     this.textLine = TextLine.standardCreateTextLine(paramFontRenderContext, paramAttributedCharacterIterator, paramArrayOfChar, this.baselineOffsets);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void ensureCache()
/*      */   {
/*  663 */     if (!this.cacheIsValid) {
/*  664 */       buildCache();
/*      */     }
/*      */   }
/*      */   
/*      */   private void buildCache() {
/*  669 */     this.lineMetrics = this.textLine.getMetrics();
/*      */     int i;
/*      */     int j;
/*  672 */     if (this.textLine.isDirectionLTR())
/*      */     {
/*  674 */       i = this.characterCount - 1;
/*  675 */       while (i != -1) {
/*  676 */         j = this.textLine.visualToLogical(i);
/*  677 */         if (!this.textLine.isCharSpace(j)) {
/*      */           break;
/*      */         }
/*      */         
/*  681 */         i--;
/*      */       }
/*      */       
/*  684 */       if (i == this.characterCount - 1) {
/*  685 */         this.visibleAdvance = this.lineMetrics.advance;
/*      */       }
/*  687 */       else if (i == -1) {
/*  688 */         this.visibleAdvance = 0.0F;
/*      */       }
/*      */       else {
/*  691 */         j = this.textLine.visualToLogical(i);
/*      */         
/*  693 */         this.visibleAdvance = (this.textLine.getCharLinePosition(j) + this.textLine.getCharAdvance(j));
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  698 */       i = 0;
/*  699 */       while (i != this.characterCount) {
/*  700 */         j = this.textLine.visualToLogical(i);
/*  701 */         if (!this.textLine.isCharSpace(j)) {
/*      */           break;
/*      */         }
/*      */         
/*  705 */         i++;
/*      */       }
/*      */       
/*  708 */       if (i == this.characterCount) {
/*  709 */         this.visibleAdvance = 0.0F;
/*      */       }
/*  711 */       else if (i == 0) {
/*  712 */         this.visibleAdvance = this.lineMetrics.advance;
/*      */       }
/*      */       else {
/*  715 */         j = this.textLine.visualToLogical(i);
/*  716 */         float f = this.textLine.getCharLinePosition(j);
/*  717 */         this.visibleAdvance = (this.lineMetrics.advance - f);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  722 */     this.naturalBounds = null;
/*  723 */     this.boundsRect = null;
/*      */     
/*      */ 
/*  726 */     this.hashCodeCache = 0;
/*      */     
/*  728 */     this.cacheIsValid = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Rectangle2D getNaturalBounds()
/*      */   {
/*  736 */     ensureCache();
/*      */     
/*  738 */     if (this.naturalBounds == null) {
/*  739 */       this.naturalBounds = this.textLine.getItalicBounds();
/*      */     }
/*      */     
/*  742 */     return this.naturalBounds;
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
/*      */   protected Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  762 */       return super.clone();
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  765 */       throw new InternalError(localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkTextHit(TextHitInfo paramTextHitInfo)
/*      */   {
/*  774 */     if (paramTextHitInfo == null) {
/*  775 */       throw new IllegalArgumentException("TextHitInfo is null.");
/*      */     }
/*      */     
/*  778 */     if ((paramTextHitInfo.getInsertionIndex() < 0) || 
/*  779 */       (paramTextHitInfo.getInsertionIndex() > this.characterCount)) {
/*  780 */       throw new IllegalArgumentException("TextHitInfo is out of range");
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
/*      */   public TextLayout getJustifiedLayout(float paramFloat)
/*      */   {
/*  801 */     if (paramFloat <= 0.0F) {
/*  802 */       throw new IllegalArgumentException("justificationWidth <= 0 passed to TextLayout.getJustifiedLayout()");
/*      */     }
/*      */     
/*  805 */     if (this.justifyRatio == -53.9F) {
/*  806 */       throw new Error("Can't justify again.");
/*      */     }
/*      */     
/*  809 */     ensureCache();
/*      */     
/*      */ 
/*  812 */     int i = this.characterCount;
/*  813 */     while ((i > 0) && (this.textLine.isCharWhitespace(i - 1))) {
/*  814 */       i--;
/*      */     }
/*      */     
/*  817 */     TextLine localTextLine = this.textLine.getJustifiedLine(paramFloat, this.justifyRatio, 0, i);
/*  818 */     if (localTextLine != null) {
/*  819 */       return new TextLayout(localTextLine, this.baseline, this.baselineOffsets, -53.9F);
/*      */     }
/*      */     
/*  822 */     return this;
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
/*      */   protected void handleJustify(float paramFloat) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte getBaseline()
/*      */   {
/*  862 */     return this.baseline;
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
/*      */   public float[] getBaselineOffsets()
/*      */   {
/*  881 */     float[] arrayOfFloat = new float[this.baselineOffsets.length];
/*  882 */     System.arraycopy(this.baselineOffsets, 0, arrayOfFloat, 0, arrayOfFloat.length);
/*  883 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getAdvance()
/*      */   {
/*  894 */     ensureCache();
/*  895 */     return this.lineMetrics.advance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getVisibleAdvance()
/*      */   {
/*  906 */     ensureCache();
/*  907 */     return this.visibleAdvance;
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
/*      */   public float getAscent()
/*      */   {
/*  922 */     ensureCache();
/*  923 */     return this.lineMetrics.ascent;
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
/*      */   public float getDescent()
/*      */   {
/*  937 */     ensureCache();
/*  938 */     return this.lineMetrics.descent;
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
/*      */   public float getLeading()
/*      */   {
/*  963 */     ensureCache();
/*  964 */     return this.lineMetrics.leading;
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
/*      */   public Rectangle2D getBounds()
/*      */   {
/*  978 */     ensureCache();
/*      */     
/*  980 */     if (this.boundsRect == null) {
/*  981 */       localObject = this.textLine.getVisualBounds();
/*  982 */       if ((dx != 0.0F) || (dy != 0.0F)) {
/*  983 */         ((Rectangle2D)localObject).setRect(((Rectangle2D)localObject).getX() - dx, ((Rectangle2D)localObject)
/*  984 */           .getY() - dy, ((Rectangle2D)localObject)
/*  985 */           .getWidth(), ((Rectangle2D)localObject)
/*  986 */           .getHeight());
/*      */       }
/*  988 */       this.boundsRect = ((Rectangle2D)localObject);
/*      */     }
/*      */     
/*  991 */     Object localObject = new Rectangle2D.Float();
/*  992 */     ((Rectangle2D)localObject).setRect(this.boundsRect);
/*      */     
/*  994 */     return (Rectangle2D)localObject;
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
/*      */   public Rectangle getPixelBounds(FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2)
/*      */   {
/* 1014 */     return this.textLine.getPixelBounds(paramFontRenderContext, paramFloat1, paramFloat2);
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
/*      */   public boolean isLeftToRight()
/*      */   {
/* 1034 */     return this.textLine.isDirectionLTR();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isVertical()
/*      */   {
/* 1043 */     return this.isVerticalLine;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getCharacterCount()
/*      */   {
/* 1052 */     return this.characterCount;
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
/*      */   private float[] getCaretInfo(int paramInt, Rectangle2D paramRectangle2D, float[] paramArrayOfFloat)
/*      */   {
/*      */     float f8;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     float f2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     float f1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     float f4;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     float f3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1132 */     if ((paramInt == 0) || (paramInt == this.characterCount))
/*      */     {
/*      */       int j;
/*      */       float f5;
/* 1136 */       if (paramInt == this.characterCount) {
/* 1137 */         j = this.textLine.visualToLogical(this.characterCount - 1);
/*      */         
/* 1139 */         f5 = this.textLine.getCharLinePosition(j) + this.textLine.getCharAdvance(j);
/*      */       }
/*      */       else {
/* 1142 */         j = this.textLine.visualToLogical(paramInt);
/* 1143 */         f5 = this.textLine.getCharLinePosition(j);
/*      */       }
/* 1145 */       f8 = this.textLine.getCharAngle(j);
/* 1146 */       float f9 = this.textLine.getCharShift(j);
/* 1147 */       f5 += f8 * f9;
/* 1148 */       f1 = f2 = f5 + f8 * this.textLine.getCharAscent(j);
/* 1149 */       f3 = f4 = f5 - f8 * this.textLine.getCharDescent(j);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1154 */       int i = this.textLine.visualToLogical(paramInt - 1);
/* 1155 */       f7 = this.textLine.getCharAngle(i);
/*      */       
/* 1157 */       f8 = this.textLine.getCharLinePosition(i) + this.textLine.getCharAdvance(i);
/* 1158 */       if (f7 != 0.0F) {
/* 1159 */         f8 += f7 * this.textLine.getCharShift(i);
/* 1160 */         f1 = f8 + f7 * this.textLine.getCharAscent(i);
/* 1161 */         f3 = f8 - f7 * this.textLine.getCharDescent(i);
/*      */       }
/*      */       else {
/* 1164 */         f1 = f3 = f8;
/*      */       }
/*      */       
/*      */ 
/* 1168 */       i = this.textLine.visualToLogical(paramInt);
/* 1169 */       f7 = this.textLine.getCharAngle(i);
/* 1170 */       f8 = this.textLine.getCharLinePosition(i);
/* 1171 */       if (f7 != 0.0F) {
/* 1172 */         f8 += f7 * this.textLine.getCharShift(i);
/* 1173 */         f2 = f8 + f7 * this.textLine.getCharAscent(i);
/* 1174 */         f4 = f8 - f7 * this.textLine.getCharDescent(i);
/*      */       }
/*      */       else {
/* 1177 */         f2 = f4 = f8;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1182 */     float f6 = (f1 + f2) / 2.0F;
/* 1183 */     float f7 = (f3 + f4) / 2.0F;
/*      */     
/* 1185 */     if (paramArrayOfFloat == null) {
/* 1186 */       paramArrayOfFloat = new float[2];
/*      */     }
/*      */     
/* 1189 */     if (this.isVerticalLine) {
/* 1190 */       paramArrayOfFloat[1] = ((float)((f6 - f7) / paramRectangle2D.getWidth()));
/* 1191 */       paramArrayOfFloat[0] = ((float)(f6 + paramArrayOfFloat[1] * paramRectangle2D.getX()));
/*      */     }
/*      */     else {
/* 1194 */       paramArrayOfFloat[1] = ((float)((f6 - f7) / paramRectangle2D.getHeight()));
/* 1195 */       paramArrayOfFloat[0] = ((float)(f7 + paramArrayOfFloat[1] * paramRectangle2D.getMaxY()));
/*      */     }
/*      */     
/* 1198 */     return paramArrayOfFloat;
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
/*      */   public float[] getCaretInfo(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D)
/*      */   {
/* 1219 */     ensureCache();
/* 1220 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1222 */     return getCaretInfoTestInternal(paramTextHitInfo, paramRectangle2D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private float[] getCaretInfoTestInternal(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D)
/*      */   {
/* 1233 */     ensureCache();
/* 1234 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1236 */     float[] arrayOfFloat = new float[6];
/*      */     
/*      */ 
/* 1239 */     getCaretInfo(hitToCaret(paramTextHitInfo), paramRectangle2D, arrayOfFloat);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1244 */     int i = paramTextHitInfo.getCharIndex();
/* 1245 */     boolean bool1 = paramTextHitInfo.isLeadingEdge();
/* 1246 */     boolean bool2 = this.textLine.isDirectionLTR();
/* 1247 */     int j = !isVertical() ? 1 : 0;
/*      */     Object localObject;
/* 1249 */     double d1; double d5; double d3; double d4; double d6; if ((i == -1) || (i == this.characterCount))
/*      */     {
/*      */ 
/* 1252 */       localObject = this.textLine.getMetrics();
/* 1253 */       int k = bool2 == (i == -1) ? 1 : 0;
/* 1254 */       d1 = 0.0D;
/* 1255 */       if (j != 0) {
/* 1256 */         d3 = d5 = k != 0 ? 0.0D : ((TextLine.TextLineMetrics)localObject).advance;
/* 1257 */         d4 = -((TextLine.TextLineMetrics)localObject).ascent;
/* 1258 */         d6 = ((TextLine.TextLineMetrics)localObject).descent;
/*      */       } else {
/* 1260 */         d4 = d6 = k != 0 ? 0.0D : ((TextLine.TextLineMetrics)localObject).advance;
/* 1261 */         d3 = ((TextLine.TextLineMetrics)localObject).descent;
/* 1262 */         d5 = ((TextLine.TextLineMetrics)localObject).ascent;
/*      */       }
/*      */     } else {
/* 1265 */       localObject = this.textLine.getCoreMetricsAt(i);
/* 1266 */       d1 = ((CoreMetrics)localObject).italicAngle;
/* 1267 */       double d2 = this.textLine.getCharLinePosition(i, bool1);
/* 1268 */       if (((CoreMetrics)localObject).baselineIndex < 0)
/*      */       {
/* 1270 */         TextLine.TextLineMetrics localTextLineMetrics = this.textLine.getMetrics();
/* 1271 */         if (j != 0) {
/* 1272 */           d3 = d5 = d2;
/* 1273 */           if (((CoreMetrics)localObject).baselineIndex == -1) {
/* 1274 */             d4 = -localTextLineMetrics.ascent;
/* 1275 */             d6 = d4 + ((CoreMetrics)localObject).height;
/*      */           } else {
/* 1277 */             d6 = localTextLineMetrics.descent;
/* 1278 */             d4 = d6 - ((CoreMetrics)localObject).height;
/*      */           }
/*      */         } else {
/* 1281 */           d4 = d6 = d2;
/* 1282 */           d3 = localTextLineMetrics.descent;
/* 1283 */           d5 = localTextLineMetrics.ascent;
/*      */         }
/*      */       }
/*      */       else {
/* 1287 */         float f = this.baselineOffsets[localObject.baselineIndex];
/* 1288 */         if (j != 0) {
/* 1289 */           d2 += d1 * ((CoreMetrics)localObject).ssOffset;
/* 1290 */           d3 = d2 + d1 * ((CoreMetrics)localObject).ascent;
/* 1291 */           d5 = d2 - d1 * ((CoreMetrics)localObject).descent;
/* 1292 */           d4 = f - ((CoreMetrics)localObject).ascent;
/* 1293 */           d6 = f + ((CoreMetrics)localObject).descent;
/*      */         } else {
/* 1295 */           d2 -= d1 * ((CoreMetrics)localObject).ssOffset;
/* 1296 */           d4 = d2 + d1 * ((CoreMetrics)localObject).ascent;
/* 1297 */           d6 = d2 - d1 * ((CoreMetrics)localObject).descent;
/* 1298 */           d3 = f + ((CoreMetrics)localObject).ascent;
/* 1299 */           d5 = f + ((CoreMetrics)localObject).descent;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1304 */     arrayOfFloat[2] = ((float)d3);
/* 1305 */     arrayOfFloat[3] = ((float)d4);
/* 1306 */     arrayOfFloat[4] = ((float)d5);
/* 1307 */     arrayOfFloat[5] = ((float)d6);
/*      */     
/* 1309 */     return arrayOfFloat;
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
/*      */   public float[] getCaretInfo(TextHitInfo paramTextHitInfo)
/*      */   {
/* 1322 */     return getCaretInfo(paramTextHitInfo, getNaturalBounds());
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
/*      */   private int hitToCaret(TextHitInfo paramTextHitInfo)
/*      */   {
/* 1335 */     int i = paramTextHitInfo.getCharIndex();
/*      */     
/* 1337 */     if (i < 0)
/* 1338 */       return this.textLine.isDirectionLTR() ? 0 : this.characterCount;
/* 1339 */     if (i >= this.characterCount) {
/* 1340 */       return this.textLine.isDirectionLTR() ? this.characterCount : 0;
/*      */     }
/*      */     
/* 1343 */     int j = this.textLine.logicalToVisual(i);
/*      */     
/* 1345 */     if (paramTextHitInfo.isLeadingEdge() != this.textLine.isCharLTR(i)) {
/* 1346 */       j++;
/*      */     }
/*      */     
/* 1349 */     return j;
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
/*      */   private TextHitInfo caretToHit(int paramInt)
/*      */   {
/* 1362 */     if ((paramInt == 0) || (paramInt == this.characterCount))
/*      */     {
/* 1364 */       if ((paramInt == this.characterCount) == this.textLine.isDirectionLTR()) {
/* 1365 */         return TextHitInfo.leading(this.characterCount);
/*      */       }
/*      */       
/* 1368 */       return TextHitInfo.trailing(-1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1373 */     int i = this.textLine.visualToLogical(paramInt);
/* 1374 */     boolean bool = this.textLine.isCharLTR(i);
/*      */     
/*      */ 
/* 1377 */     return bool ? TextHitInfo.leading(i) : TextHitInfo.trailing(i);
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean caretIsValid(int paramInt)
/*      */   {
/* 1383 */     if ((paramInt == this.characterCount) || (paramInt == 0)) {
/* 1384 */       return true;
/*      */     }
/*      */     
/* 1387 */     int i = this.textLine.visualToLogical(paramInt);
/*      */     
/* 1389 */     if (!this.textLine.isCharLTR(i)) {
/* 1390 */       i = this.textLine.visualToLogical(paramInt - 1);
/* 1391 */       if (this.textLine.isCharLTR(i)) {
/* 1392 */         return true;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1399 */     return this.textLine.caretAtOffsetIsValid(i);
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
/*      */   public TextHitInfo getNextRightHit(TextHitInfo paramTextHitInfo)
/*      */   {
/* 1412 */     ensureCache();
/* 1413 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1415 */     int i = hitToCaret(paramTextHitInfo);
/*      */     
/* 1417 */     if (i == this.characterCount) {
/* 1418 */       return null;
/*      */     }
/*      */     do
/*      */     {
/* 1422 */       i++;
/* 1423 */     } while (!caretIsValid(i));
/*      */     
/* 1425 */     return caretToHit(i);
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
/*      */   public TextHitInfo getNextRightHit(int paramInt, CaretPolicy paramCaretPolicy)
/*      */   {
/* 1444 */     if ((paramInt < 0) || (paramInt > this.characterCount)) {
/* 1445 */       throw new IllegalArgumentException("Offset out of bounds in TextLayout.getNextRightHit()");
/*      */     }
/*      */     
/* 1448 */     if (paramCaretPolicy == null) {
/* 1449 */       throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getNextRightHit()");
/*      */     }
/*      */     
/* 1452 */     TextHitInfo localTextHitInfo1 = TextHitInfo.afterOffset(paramInt);
/* 1453 */     TextHitInfo localTextHitInfo2 = localTextHitInfo1.getOtherHit();
/*      */     
/* 1455 */     TextHitInfo localTextHitInfo3 = getNextRightHit(paramCaretPolicy.getStrongCaret(localTextHitInfo1, localTextHitInfo2, this));
/*      */     
/* 1457 */     if (localTextHitInfo3 != null) {
/* 1458 */       TextHitInfo localTextHitInfo4 = getVisualOtherHit(localTextHitInfo3);
/* 1459 */       return paramCaretPolicy.getStrongCaret(localTextHitInfo4, localTextHitInfo3, this);
/*      */     }
/*      */     
/* 1462 */     return null;
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
/*      */   public TextHitInfo getNextRightHit(int paramInt)
/*      */   {
/* 1481 */     return getNextRightHit(paramInt, DEFAULT_CARET_POLICY);
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
/*      */   public TextHitInfo getNextLeftHit(TextHitInfo paramTextHitInfo)
/*      */   {
/* 1494 */     ensureCache();
/* 1495 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1497 */     int i = hitToCaret(paramTextHitInfo);
/*      */     
/* 1499 */     if (i == 0) {
/* 1500 */       return null;
/*      */     }
/*      */     do
/*      */     {
/* 1504 */       i--;
/* 1505 */     } while (!caretIsValid(i));
/*      */     
/* 1507 */     return caretToHit(i);
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
/*      */   public TextHitInfo getNextLeftHit(int paramInt, CaretPolicy paramCaretPolicy)
/*      */   {
/* 1526 */     if (paramCaretPolicy == null) {
/* 1527 */       throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getNextLeftHit()");
/*      */     }
/*      */     
/* 1530 */     if ((paramInt < 0) || (paramInt > this.characterCount)) {
/* 1531 */       throw new IllegalArgumentException("Offset out of bounds in TextLayout.getNextLeftHit()");
/*      */     }
/*      */     
/* 1534 */     TextHitInfo localTextHitInfo1 = TextHitInfo.afterOffset(paramInt);
/* 1535 */     TextHitInfo localTextHitInfo2 = localTextHitInfo1.getOtherHit();
/*      */     
/* 1537 */     TextHitInfo localTextHitInfo3 = getNextLeftHit(paramCaretPolicy.getStrongCaret(localTextHitInfo1, localTextHitInfo2, this));
/*      */     
/* 1539 */     if (localTextHitInfo3 != null) {
/* 1540 */       TextHitInfo localTextHitInfo4 = getVisualOtherHit(localTextHitInfo3);
/* 1541 */       return paramCaretPolicy.getStrongCaret(localTextHitInfo4, localTextHitInfo3, this);
/*      */     }
/*      */     
/* 1544 */     return null;
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
/*      */   public TextHitInfo getNextLeftHit(int paramInt)
/*      */   {
/* 1563 */     return getNextLeftHit(paramInt, DEFAULT_CARET_POLICY);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TextHitInfo getVisualOtherHit(TextHitInfo paramTextHitInfo)
/*      */   {
/* 1574 */     ensureCache();
/* 1575 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1577 */     int i = paramTextHitInfo.getCharIndex();
/*      */     
/*      */     int k;
/*      */     int j;
/*      */     boolean bool;
/* 1582 */     if ((i == -1) || (i == this.characterCount))
/*      */     {
/*      */ 
/* 1585 */       if (this.textLine.isDirectionLTR() == (i == -1)) {
/* 1586 */         k = 0;
/*      */       }
/*      */       else {
/* 1589 */         k = this.characterCount - 1;
/*      */       }
/*      */       
/* 1592 */       j = this.textLine.visualToLogical(k);
/*      */       
/* 1594 */       if (this.textLine.isDirectionLTR() == (i == -1))
/*      */       {
/* 1596 */         bool = this.textLine.isCharLTR(j);
/*      */       }
/*      */       else
/*      */       {
/* 1600 */         bool = !this.textLine.isCharLTR(j);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1605 */       k = this.textLine.logicalToVisual(i);
/*      */       
/*      */       int m;
/* 1608 */       if (this.textLine.isCharLTR(i) == paramTextHitInfo.isLeadingEdge()) {
/* 1609 */         k--;
/* 1610 */         m = 0;
/*      */       }
/*      */       else {
/* 1613 */         k++;
/* 1614 */         m = 1;
/*      */       }
/*      */       
/* 1617 */       if ((k > -1) && (k < this.characterCount)) {
/* 1618 */         j = this.textLine.visualToLogical(k);
/* 1619 */         bool = m == this.textLine.isCharLTR(j);
/*      */       }
/*      */       else
/*      */       {
/* 1623 */         j = m == this.textLine.isDirectionLTR() ? this.characterCount : -1;
/* 1624 */         bool = j == this.characterCount;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1629 */     return bool ? TextHitInfo.leading(j) : TextHitInfo.trailing(j);
/*      */   }
/*      */   
/*      */   private double[] getCaretPath(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D) {
/* 1633 */     float[] arrayOfFloat = getCaretInfo(paramTextHitInfo, paramRectangle2D);
/* 1634 */     return new double[] { arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5] };
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
/*      */   private double[] getCaretPath(int paramInt, Rectangle2D paramRectangle2D, boolean paramBoolean)
/*      */   {
/* 1649 */     float[] arrayOfFloat = getCaretInfo(paramInt, paramRectangle2D, null);
/*      */     
/* 1651 */     double d1 = arrayOfFloat[0];
/* 1652 */     double d2 = arrayOfFloat[1];
/*      */     
/*      */ 
/* 1655 */     double d7 = -3141.59D;double d8 = -2.7D;
/*      */     
/* 1657 */     double d9 = paramRectangle2D.getX();
/* 1658 */     double d10 = d9 + paramRectangle2D.getWidth();
/* 1659 */     double d11 = paramRectangle2D.getY();
/* 1660 */     double d12 = d11 + paramRectangle2D.getHeight();
/*      */     
/* 1662 */     int i = 0;
/*      */     double d3;
/* 1664 */     double d5; double d4; double d6; if (this.isVerticalLine)
/*      */     {
/* 1666 */       if (d2 >= 0.0D) {
/* 1667 */         d3 = d9;
/* 1668 */         d5 = d10;
/*      */       }
/*      */       else {
/* 1671 */         d5 = d9;
/* 1672 */         d3 = d10;
/*      */       }
/*      */       
/* 1675 */       d4 = d1 + d3 * d2;
/* 1676 */       d6 = d1 + d5 * d2;
/*      */       
/*      */ 
/*      */ 
/* 1680 */       if (paramBoolean) {
/* 1681 */         if (d4 < d11) {
/* 1682 */           if ((d2 <= 0.0D) || (d6 <= d11)) {
/* 1683 */             d4 = d6 = d11;
/*      */           }
/*      */           else {
/* 1686 */             i = 1;
/* 1687 */             d4 = d11;
/* 1688 */             d8 = d11;
/* 1689 */             d7 = d5 + (d11 - d6) / d2;
/* 1690 */             if (d6 > d12) {
/* 1691 */               d6 = d12;
/*      */             }
/*      */           }
/*      */         }
/* 1695 */         else if (d6 > d12) {
/* 1696 */           if ((d2 >= 0.0D) || (d4 >= d12)) {
/* 1697 */             d4 = d6 = d12;
/*      */           }
/*      */           else {
/* 1700 */             i = 1;
/* 1701 */             d6 = d12;
/* 1702 */             d8 = d12;
/* 1703 */             d7 = d3 + (d12 - d5) / d2;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1711 */       if (d2 >= 0.0D) {
/* 1712 */         d4 = d12;
/* 1713 */         d6 = d11;
/*      */       }
/*      */       else {
/* 1716 */         d6 = d12;
/* 1717 */         d4 = d11;
/*      */       }
/*      */       
/* 1720 */       d3 = d1 - d4 * d2;
/* 1721 */       d5 = d1 - d6 * d2;
/*      */       
/*      */ 
/*      */ 
/* 1725 */       if (paramBoolean) {
/* 1726 */         if (d3 < d9) {
/* 1727 */           if ((d2 <= 0.0D) || (d5 <= d9)) {
/* 1728 */             d3 = d5 = d9;
/*      */           }
/*      */           else {
/* 1731 */             i = 1;
/* 1732 */             d3 = d9;
/* 1733 */             d7 = d9;
/* 1734 */             d8 = d6 - (d9 - d5) / d2;
/* 1735 */             if (d5 > d10) {
/* 1736 */               d5 = d10;
/*      */             }
/*      */           }
/*      */         }
/* 1740 */         else if (d5 > d10) {
/* 1741 */           if ((d2 >= 0.0D) || (d3 >= d10)) {
/* 1742 */             d3 = d5 = d10;
/*      */           }
/*      */           else {
/* 1745 */             i = 1;
/* 1746 */             d5 = d10;
/* 1747 */             d7 = d10;
/* 1748 */             d8 = d4 - (d10 - d3) / d2;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1754 */     return new double[] { d3, d4, d5, i != 0 ? new double[] { d3, d4, d7, d8, d5, d6 } : d6 };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static GeneralPath pathToShape(double[] paramArrayOfDouble, boolean paramBoolean, LayoutPathImpl paramLayoutPathImpl)
/*      */   {
/* 1761 */     GeneralPath localGeneralPath = new GeneralPath(0, paramArrayOfDouble.length);
/* 1762 */     localGeneralPath.moveTo((float)paramArrayOfDouble[0], (float)paramArrayOfDouble[1]);
/* 1763 */     for (int i = 2; i < paramArrayOfDouble.length; i += 2) {
/* 1764 */       localGeneralPath.lineTo((float)paramArrayOfDouble[i], (float)paramArrayOfDouble[(i + 1)]);
/*      */     }
/* 1766 */     if (paramBoolean) {
/* 1767 */       localGeneralPath.closePath();
/*      */     }
/*      */     
/* 1770 */     if (paramLayoutPathImpl != null) {
/* 1771 */       localGeneralPath = (GeneralPath)paramLayoutPathImpl.mapShape(localGeneralPath);
/*      */     }
/* 1773 */     return localGeneralPath;
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
/*      */   public Shape getCaretShape(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D)
/*      */   {
/* 1787 */     ensureCache();
/* 1788 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1790 */     if (paramRectangle2D == null) {
/* 1791 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getCaret()");
/*      */     }
/*      */     
/* 1794 */     return pathToShape(getCaretPath(paramTextHitInfo, paramRectangle2D), false, this.textLine.getLayoutPath());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Shape getCaretShape(TextHitInfo paramTextHitInfo)
/*      */   {
/* 1806 */     return getCaretShape(paramTextHitInfo, getNaturalBounds());
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
/*      */   private final TextHitInfo getStrongHit(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2)
/*      */   {
/* 1823 */     int i = getCharacterLevel(paramTextHitInfo1.getCharIndex());
/* 1824 */     int j = getCharacterLevel(paramTextHitInfo2.getCharIndex());
/*      */     
/* 1826 */     if (i == j) {
/* 1827 */       if ((paramTextHitInfo2.isLeadingEdge()) && (!paramTextHitInfo1.isLeadingEdge())) {
/* 1828 */         return paramTextHitInfo2;
/*      */       }
/*      */       
/* 1831 */       return paramTextHitInfo1;
/*      */     }
/*      */     
/*      */ 
/* 1835 */     return i < j ? paramTextHitInfo1 : paramTextHitInfo2;
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
/*      */   public byte getCharacterLevel(int paramInt)
/*      */   {
/* 1849 */     if ((paramInt < -1) || (paramInt > this.characterCount)) {
/* 1850 */       throw new IllegalArgumentException("Index is out of range in getCharacterLevel.");
/*      */     }
/*      */     
/* 1853 */     ensureCache();
/* 1854 */     if ((paramInt == -1) || (paramInt == this.characterCount)) {
/* 1855 */       return (byte)(this.textLine.isDirectionLTR() ? 0 : 1);
/*      */     }
/*      */     
/* 1858 */     return this.textLine.getCharLevel(paramInt);
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
/*      */   public Shape[] getCaretShapes(int paramInt, Rectangle2D paramRectangle2D, CaretPolicy paramCaretPolicy)
/*      */   {
/* 1874 */     ensureCache();
/*      */     
/* 1876 */     if ((paramInt < 0) || (paramInt > this.characterCount)) {
/* 1877 */       throw new IllegalArgumentException("Offset out of bounds in TextLayout.getCaretShapes()");
/*      */     }
/*      */     
/* 1880 */     if (paramRectangle2D == null) {
/* 1881 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getCaretShapes()");
/*      */     }
/*      */     
/* 1884 */     if (paramCaretPolicy == null) {
/* 1885 */       throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getCaretShapes()");
/*      */     }
/*      */     
/* 1888 */     Shape[] arrayOfShape = new Shape[2];
/*      */     
/* 1890 */     TextHitInfo localTextHitInfo1 = TextHitInfo.afterOffset(paramInt);
/*      */     
/* 1892 */     int i = hitToCaret(localTextHitInfo1);
/*      */     
/* 1894 */     LayoutPathImpl localLayoutPathImpl = this.textLine.getLayoutPath();
/* 1895 */     GeneralPath localGeneralPath1 = pathToShape(getCaretPath(localTextHitInfo1, paramRectangle2D), false, localLayoutPathImpl);
/* 1896 */     TextHitInfo localTextHitInfo2 = localTextHitInfo1.getOtherHit();
/* 1897 */     int j = hitToCaret(localTextHitInfo2);
/*      */     
/* 1899 */     if (i == j) {
/* 1900 */       arrayOfShape[0] = localGeneralPath1;
/*      */     }
/*      */     else {
/* 1903 */       GeneralPath localGeneralPath2 = pathToShape(getCaretPath(localTextHitInfo2, paramRectangle2D), false, localLayoutPathImpl);
/*      */       
/* 1905 */       TextHitInfo localTextHitInfo3 = paramCaretPolicy.getStrongCaret(localTextHitInfo1, localTextHitInfo2, this);
/* 1906 */       boolean bool = localTextHitInfo3.equals(localTextHitInfo1);
/*      */       
/* 1908 */       if (bool) {
/* 1909 */         arrayOfShape[0] = localGeneralPath1;
/* 1910 */         arrayOfShape[1] = localGeneralPath2;
/*      */       }
/*      */       else {
/* 1913 */         arrayOfShape[0] = localGeneralPath2;
/* 1914 */         arrayOfShape[1] = localGeneralPath1;
/*      */       }
/*      */     }
/*      */     
/* 1918 */     return arrayOfShape;
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
/*      */   public Shape[] getCaretShapes(int paramInt, Rectangle2D paramRectangle2D)
/*      */   {
/* 1934 */     return getCaretShapes(paramInt, paramRectangle2D, DEFAULT_CARET_POLICY);
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
/*      */   public Shape[] getCaretShapes(int paramInt)
/*      */   {
/* 1949 */     return getCaretShapes(paramInt, getNaturalBounds(), DEFAULT_CARET_POLICY);
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
/*      */   private GeneralPath boundingShape(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*      */   {
/* 1964 */     GeneralPath localGeneralPath = pathToShape(paramArrayOfDouble1, false, null);
/*      */     
/*      */     int i;
/*      */     
/* 1968 */     if (this.isVerticalLine) {
/* 1969 */       i = (paramArrayOfDouble1[1] > paramArrayOfDouble1[(paramArrayOfDouble1.length - 1)] ? 1 : 0) == (paramArrayOfDouble2[1] > paramArrayOfDouble2[(paramArrayOfDouble2.length - 1)] ? 1 : 0) ? 1 : 0;
/*      */     }
/*      */     else
/*      */     {
/* 1973 */       i = (paramArrayOfDouble1[0] > paramArrayOfDouble1[(paramArrayOfDouble1.length - 2)] ? 1 : 0) == (paramArrayOfDouble2[0] > paramArrayOfDouble2[(paramArrayOfDouble2.length - 2)] ? 1 : 0) ? 1 : 0;
/*      */     }
/*      */     
/*      */     int j;
/*      */     
/*      */     int k;
/*      */     
/*      */     int m;
/* 1981 */     if (i != 0) {
/* 1982 */       j = paramArrayOfDouble2.length - 2;
/* 1983 */       k = -2;
/* 1984 */       m = -2;
/*      */     }
/*      */     else {
/* 1987 */       j = 0;
/* 1988 */       k = paramArrayOfDouble2.length;
/* 1989 */       m = 2;
/*      */     }
/*      */     
/* 1992 */     for (int n = j; n != k; n += m) {
/* 1993 */       localGeneralPath.lineTo((float)paramArrayOfDouble2[n], (float)paramArrayOfDouble2[(n + 1)]);
/*      */     }
/*      */     
/* 1996 */     localGeneralPath.closePath();
/*      */     
/* 1998 */     return localGeneralPath;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private GeneralPath caretBoundingShape(int paramInt1, int paramInt2, Rectangle2D paramRectangle2D)
/*      */   {
/* 2007 */     if (paramInt1 > paramInt2) {
/* 2008 */       int i = paramInt1;
/* 2009 */       paramInt1 = paramInt2;
/* 2010 */       paramInt2 = i;
/*      */     }
/*      */     
/* 2013 */     return boundingShape(getCaretPath(paramInt1, paramRectangle2D, true), 
/* 2014 */       getCaretPath(paramInt2, paramRectangle2D, true));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private GeneralPath leftShape(Rectangle2D paramRectangle2D)
/*      */   {
/*      */     double[] arrayOfDouble1;
/*      */     
/*      */ 
/* 2025 */     if (this.isVerticalLine)
/*      */     {
/*      */ 
/* 2028 */       arrayOfDouble1 = new double[] { paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() };
/*      */     }
/*      */     else
/*      */     {
/* 2032 */       arrayOfDouble1 = new double[] { paramRectangle2D.getX(), paramRectangle2D.getY() + paramRectangle2D.getHeight(), paramRectangle2D.getX(), paramRectangle2D.getY() };
/*      */     }
/*      */     
/* 2035 */     double[] arrayOfDouble2 = getCaretPath(0, paramRectangle2D, true);
/*      */     
/* 2037 */     return boundingShape(arrayOfDouble1, arrayOfDouble2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private GeneralPath rightShape(Rectangle2D paramRectangle2D)
/*      */   {
/*      */     double[] arrayOfDouble1;
/*      */     
/* 2046 */     if (this.isVerticalLine)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2051 */       arrayOfDouble1 = new double[] {paramRectangle2D.getX(), paramRectangle2D.getY() + paramRectangle2D.getHeight(), paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() + paramRectangle2D.getHeight() };
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 2058 */       arrayOfDouble1 = new double[] {paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() + paramRectangle2D.getHeight(), paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() };
/*      */     }
/*      */     
/*      */ 
/* 2062 */     double[] arrayOfDouble2 = getCaretPath(this.characterCount, paramRectangle2D, true);
/*      */     
/* 2064 */     return boundingShape(arrayOfDouble2, arrayOfDouble1);
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
/*      */   public int[] getLogicalRangesForVisualSelection(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2)
/*      */   {
/* 2078 */     ensureCache();
/*      */     
/* 2080 */     checkTextHit(paramTextHitInfo1);
/* 2081 */     checkTextHit(paramTextHitInfo2);
/*      */     
/*      */ 
/*      */ 
/* 2085 */     boolean[] arrayOfBoolean = new boolean[this.characterCount];
/*      */     
/* 2087 */     int i = hitToCaret(paramTextHitInfo1);
/* 2088 */     int j = hitToCaret(paramTextHitInfo2);
/*      */     
/* 2090 */     if (i > j) {
/* 2091 */       k = i;
/* 2092 */       i = j;
/* 2093 */       j = k;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2103 */     if (i < j) {
/* 2104 */       k = i;
/* 2105 */       while (k < j) {
/* 2106 */         arrayOfBoolean[this.textLine.visualToLogical(k)] = true;
/* 2107 */         k++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2115 */     int k = 0;
/* 2116 */     int m = 0;
/* 2117 */     for (int n = 0; n < this.characterCount; n++) {
/* 2118 */       if (arrayOfBoolean[n] != m) {
/* 2119 */         m = m == 0 ? 1 : 0;
/* 2120 */         if (m != 0) {
/* 2121 */           k++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2126 */     int[] arrayOfInt = new int[k * 2];
/* 2127 */     k = 0;
/* 2128 */     m = 0;
/* 2129 */     for (int i1 = 0; i1 < this.characterCount; i1++) {
/* 2130 */       if (arrayOfBoolean[i1] != m) {
/* 2131 */         arrayOfInt[(k++)] = i1;
/* 2132 */         m = m == 0 ? 1 : 0;
/*      */       }
/*      */     }
/* 2135 */     if (m != 0) {
/* 2136 */       arrayOfInt[(k++)] = this.characterCount;
/*      */     }
/*      */     
/* 2139 */     return arrayOfInt;
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
/*      */   public Shape getVisualHighlightShape(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2, Rectangle2D paramRectangle2D)
/*      */   {
/* 2182 */     ensureCache();
/*      */     
/* 2184 */     checkTextHit(paramTextHitInfo1);
/* 2185 */     checkTextHit(paramTextHitInfo2);
/*      */     
/* 2187 */     if (paramRectangle2D == null) {
/* 2188 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getVisualHighlightShape()");
/*      */     }
/*      */     
/* 2191 */     GeneralPath localGeneralPath = new GeneralPath(0);
/*      */     
/* 2193 */     int i = hitToCaret(paramTextHitInfo1);
/* 2194 */     int j = hitToCaret(paramTextHitInfo2);
/*      */     
/* 2196 */     localGeneralPath.append(caretBoundingShape(i, j, paramRectangle2D), false);
/*      */     
/*      */ 
/* 2199 */     if ((i == 0) || (j == 0)) {
/* 2200 */       localObject = leftShape(paramRectangle2D);
/* 2201 */       if (!((GeneralPath)localObject).getBounds().isEmpty()) {
/* 2202 */         localGeneralPath.append((Shape)localObject, false);
/*      */       }
/*      */     }
/* 2205 */     if ((i == this.characterCount) || (j == this.characterCount)) {
/* 2206 */       localObject = rightShape(paramRectangle2D);
/* 2207 */       if (!((GeneralPath)localObject).getBounds().isEmpty()) {
/* 2208 */         localGeneralPath.append((Shape)localObject, false);
/*      */       }
/*      */     }
/*      */     
/* 2212 */     Object localObject = this.textLine.getLayoutPath();
/* 2213 */     if (localObject != null) {
/* 2214 */       localGeneralPath = (GeneralPath)((LayoutPathImpl)localObject).mapShape(localGeneralPath);
/*      */     }
/*      */     
/* 2217 */     return localGeneralPath;
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
/*      */   public Shape getVisualHighlightShape(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2)
/*      */   {
/* 2232 */     return getVisualHighlightShape(paramTextHitInfo1, paramTextHitInfo2, getNaturalBounds());
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
/*      */   public Shape getLogicalHighlightShape(int paramInt1, int paramInt2, Rectangle2D paramRectangle2D)
/*      */   {
/* 2277 */     if (paramRectangle2D == null) {
/* 2278 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getLogicalHighlightShape()");
/*      */     }
/*      */     
/* 2281 */     ensureCache();
/*      */     
/* 2283 */     if (paramInt1 > paramInt2) {
/* 2284 */       int i = paramInt1;
/* 2285 */       paramInt1 = paramInt2;
/* 2286 */       paramInt2 = i;
/*      */     }
/*      */     
/* 2289 */     if ((paramInt1 < 0) || (paramInt2 > this.characterCount)) {
/* 2290 */       throw new IllegalArgumentException("Range is invalid in TextLayout.getLogicalHighlightShape()");
/*      */     }
/*      */     
/* 2293 */     GeneralPath localGeneralPath = new GeneralPath(0);
/*      */     
/* 2295 */     Object localObject1 = new int[10];
/* 2296 */     int j = 0;
/*      */     
/* 2298 */     if (paramInt1 < paramInt2) {
/* 2299 */       k = paramInt1;
/*      */       do {
/* 2301 */         localObject1[(j++)] = hitToCaret(TextHitInfo.leading(k));
/* 2302 */         boolean bool = this.textLine.isCharLTR(k);
/*      */         do
/*      */         {
/* 2305 */           k++;
/* 2306 */         } while ((k < paramInt2) && (this.textLine.isCharLTR(k) == bool));
/*      */         
/* 2308 */         int m = k;
/* 2309 */         localObject1[(j++)] = hitToCaret(TextHitInfo.trailing(m - 1));
/*      */         
/* 2311 */         if (j == localObject1.length) {
/* 2312 */           int[] arrayOfInt = new int[localObject1.length + 10];
/* 2313 */           System.arraycopy(localObject1, 0, arrayOfInt, 0, j);
/* 2314 */           localObject1 = arrayOfInt;
/*      */         }
/* 2316 */       } while (k < paramInt2);
/*      */     }
/*      */     else {
/* 2319 */       j = 2;
/* 2320 */       localObject1[0] = (localObject1[1] = hitToCaret(TextHitInfo.leading(paramInt1)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2325 */     for (int k = 0; k < j; k += 2) {
/* 2326 */       localGeneralPath.append(caretBoundingShape(localObject1[k], localObject1[(k + 1)], paramRectangle2D), false);
/*      */     }
/*      */     
/*      */ 
/* 2330 */     if (paramInt1 != paramInt2) {
/* 2331 */       if (((this.textLine.isDirectionLTR()) && (paramInt1 == 0)) || ((!this.textLine.isDirectionLTR()) && (paramInt2 == this.characterCount)))
/*      */       {
/* 2333 */         localObject2 = leftShape(paramRectangle2D);
/* 2334 */         if (!((GeneralPath)localObject2).getBounds().isEmpty()) {
/* 2335 */           localGeneralPath.append((Shape)localObject2, false);
/*      */         }
/*      */       }
/*      */       
/* 2339 */       if (((this.textLine.isDirectionLTR()) && (paramInt2 == this.characterCount)) || (
/* 2340 */         (!this.textLine.isDirectionLTR()) && (paramInt1 == 0)))
/*      */       {
/* 2342 */         localObject2 = rightShape(paramRectangle2D);
/* 2343 */         if (!((GeneralPath)localObject2).getBounds().isEmpty()) {
/* 2344 */           localGeneralPath.append((Shape)localObject2, false);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2349 */     Object localObject2 = this.textLine.getLayoutPath();
/* 2350 */     if (localObject2 != null) {
/* 2351 */       localGeneralPath = (GeneralPath)((LayoutPathImpl)localObject2).mapShape(localGeneralPath);
/*      */     }
/* 2353 */     return localGeneralPath;
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
/*      */   public Shape getLogicalHighlightShape(int paramInt1, int paramInt2)
/*      */   {
/* 2372 */     return getLogicalHighlightShape(paramInt1, paramInt2, getNaturalBounds());
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
/*      */   public Shape getBlackBoxBounds(int paramInt1, int paramInt2)
/*      */   {
/* 2387 */     ensureCache();
/*      */     
/* 2389 */     if (paramInt1 > paramInt2) {
/* 2390 */       int i = paramInt1;
/* 2391 */       paramInt1 = paramInt2;
/* 2392 */       paramInt2 = i;
/*      */     }
/*      */     
/* 2395 */     if ((paramInt1 < 0) || (paramInt2 > this.characterCount)) {
/* 2396 */       throw new IllegalArgumentException("Invalid range passed to TextLayout.getBlackBoxBounds()");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2404 */     GeneralPath localGeneralPath = new GeneralPath(1);
/*      */     
/* 2406 */     if (paramInt1 < this.characterCount) {
/* 2407 */       for (int j = paramInt1; 
/* 2408 */           j < paramInt2; 
/* 2409 */           j++)
/*      */       {
/* 2411 */         Rectangle2D localRectangle2D = this.textLine.getCharBounds(j);
/* 2412 */         if (!localRectangle2D.isEmpty()) {
/* 2413 */           localGeneralPath.append(localRectangle2D, false);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2418 */     if ((dx != 0.0F) || (dy != 0.0F)) {
/* 2419 */       localObject = AffineTransform.getTranslateInstance(dx, dy);
/* 2420 */       localGeneralPath = (GeneralPath)((AffineTransform)localObject).createTransformedShape(localGeneralPath);
/*      */     }
/* 2422 */     Object localObject = this.textLine.getLayoutPath();
/* 2423 */     if (localObject != null) {
/* 2424 */       localGeneralPath = (GeneralPath)((LayoutPathImpl)localObject).mapShape(localGeneralPath);
/*      */     }
/*      */     
/*      */ 
/* 2428 */     return localGeneralPath;
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
/*      */   private float caretToPointDistance(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2)
/*      */   {
/* 2441 */     float f1 = this.isVerticalLine ? paramFloat2 : paramFloat1;
/* 2442 */     float f2 = this.isVerticalLine ? -paramFloat1 : paramFloat2;
/*      */     
/* 2444 */     return f1 - paramArrayOfFloat[0] + f2 * paramArrayOfFloat[1];
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
/*      */   public TextHitInfo hitTestChar(float paramFloat1, float paramFloat2, Rectangle2D paramRectangle2D)
/*      */   {
/* 2468 */     LayoutPathImpl localLayoutPathImpl = this.textLine.getLayoutPath();
/* 2469 */     boolean bool = false;
/* 2470 */     if (localLayoutPathImpl != null) {
/* 2471 */       Point2D.Float localFloat = new Point2D.Float(paramFloat1, paramFloat2);
/* 2472 */       bool = localLayoutPathImpl.pointToPath(localFloat, localFloat);
/* 2473 */       paramFloat1 = localFloat.x;
/* 2474 */       paramFloat2 = localFloat.y;
/*      */     }
/*      */     
/* 2477 */     if (isVertical()) {
/* 2478 */       if (paramFloat2 < paramRectangle2D.getMinY())
/* 2479 */         return TextHitInfo.leading(0);
/* 2480 */       if (paramFloat2 >= paramRectangle2D.getMaxY()) {
/* 2481 */         return TextHitInfo.trailing(this.characterCount - 1);
/*      */       }
/*      */     } else {
/* 2484 */       if (paramFloat1 < paramRectangle2D.getMinX())
/* 2485 */         return isLeftToRight() ? TextHitInfo.leading(0) : TextHitInfo.trailing(this.characterCount - 1);
/* 2486 */       if (paramFloat1 >= paramRectangle2D.getMaxX()) {
/* 2487 */         return isLeftToRight() ? TextHitInfo.trailing(this.characterCount - 1) : TextHitInfo.leading(0);
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
/* 2501 */     double d1 = Double.MAX_VALUE;
/* 2502 */     int i = 0;
/* 2503 */     int j = -1;
/* 2504 */     Object localObject = null;
/* 2505 */     float f1 = 0.0F;float f2 = 0.0F;float f3 = 0.0F;float f4 = 0.0F;float f5 = 0.0F;float f6 = 0.0F;
/*      */     
/* 2507 */     for (int k = 0; k < this.characterCount; k++)
/* 2508 */       if (this.textLine.caretAtOffsetIsValid(k))
/*      */       {
/*      */ 
/* 2511 */         if (j == -1) {
/* 2512 */           j = k;
/*      */         }
/* 2514 */         CoreMetrics localCoreMetrics = this.textLine.getCoreMetricsAt(k);
/* 2515 */         if (localCoreMetrics != localObject) {
/* 2516 */           localObject = localCoreMetrics;
/*      */           
/* 2518 */           if (localCoreMetrics.baselineIndex == -1) {
/* 2519 */             f4 = -(this.textLine.getMetrics().ascent - localCoreMetrics.ascent) + localCoreMetrics.ssOffset;
/* 2520 */           } else if (localCoreMetrics.baselineIndex == -2) {
/* 2521 */             f4 = this.textLine.getMetrics().descent - localCoreMetrics.descent + localCoreMetrics.ssOffset;
/*      */           } else {
/* 2523 */             f4 = localCoreMetrics.effectiveBaselineOffset(this.baselineOffsets) + localCoreMetrics.ssOffset;
/*      */           }
/* 2525 */           f7 = (localCoreMetrics.descent - localCoreMetrics.ascent) / 2.0F - f4;
/* 2526 */           f5 = f7 * localCoreMetrics.italicAngle;
/* 2527 */           f4 += f7;
/* 2528 */           f6 = (f4 - paramFloat2) * (f4 - paramFloat2);
/*      */         }
/* 2530 */         float f7 = this.textLine.getCharXPosition(k);
/* 2531 */         float f8 = this.textLine.getCharAdvance(k);
/* 2532 */         float f9 = f8 / 2.0F;
/* 2533 */         f7 += f9 - f5;
/*      */         
/*      */ 
/* 2536 */         double d2 = Math.sqrt(4.0F * (f7 - paramFloat1) * (f7 - paramFloat1) + f6);
/* 2537 */         if (d2 < d1) {
/* 2538 */           d1 = d2;
/* 2539 */           i = k;
/* 2540 */           j = -1;
/* 2541 */           f1 = f7;f2 = f4;f3 = localCoreMetrics.italicAngle;
/*      */         }
/*      */       }
/* 2544 */     k = paramFloat1 < f1 - (paramFloat2 - f2) * f3 ? 1 : 0;
/* 2545 */     int m = this.textLine.isCharLTR(i) == k ? 1 : 0;
/* 2546 */     if (j == -1) {
/* 2547 */       j = this.characterCount;
/*      */     }
/*      */     
/* 2550 */     TextHitInfo localTextHitInfo = m != 0 ? TextHitInfo.leading(i) : TextHitInfo.trailing(j - 1);
/* 2551 */     return localTextHitInfo;
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
/*      */   public TextHitInfo hitTestChar(float paramFloat1, float paramFloat2)
/*      */   {
/* 2568 */     return hitTestChar(paramFloat1, paramFloat2, getNaturalBounds());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 2576 */     if (this.hashCodeCache == 0) {
/* 2577 */       ensureCache();
/* 2578 */       this.hashCodeCache = this.textLine.hashCode();
/*      */     }
/* 2580 */     return this.hashCodeCache;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 2593 */     return ((paramObject instanceof TextLayout)) && (equals((TextLayout)paramObject));
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
/*      */   public boolean equals(TextLayout paramTextLayout)
/*      */   {
/* 2607 */     if (paramTextLayout == null) {
/* 2608 */       return false;
/*      */     }
/* 2610 */     if (paramTextLayout == this) {
/* 2611 */       return true;
/*      */     }
/*      */     
/* 2614 */     ensureCache();
/* 2615 */     return this.textLine.equals(paramTextLayout.textLine);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2624 */     ensureCache();
/* 2625 */     return this.textLine.toString();
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
/*      */   public void draw(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2)
/*      */   {
/* 2643 */     if (paramGraphics2D == null) {
/* 2644 */       throw new IllegalArgumentException("Null Graphics2D passed to TextLayout.draw()");
/*      */     }
/*      */     
/* 2647 */     this.textLine.draw(paramGraphics2D, paramFloat1 - dx, paramFloat2 - dy);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   TextLine getTextLineForTesting()
/*      */   {
/* 2655 */     return this.textLine;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int sameBaselineUpTo(Font paramFont, char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 2667 */     return paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static byte getBaselineFromGraphic(GraphicAttribute paramGraphicAttribute)
/*      */   {
/* 2679 */     byte b = (byte)paramGraphicAttribute.getAlignment();
/*      */     
/* 2681 */     if ((b == -2) || (b == -1))
/*      */     {
/*      */ 
/* 2684 */       return 0;
/*      */     }
/*      */     
/* 2687 */     return b;
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
/*      */   public Shape getOutline(AffineTransform paramAffineTransform)
/*      */   {
/* 2700 */     ensureCache();
/* 2701 */     Shape localShape = this.textLine.getOutline(paramAffineTransform);
/* 2702 */     LayoutPathImpl localLayoutPathImpl = this.textLine.getLayoutPath();
/* 2703 */     if (localLayoutPathImpl != null) {
/* 2704 */       localShape = localLayoutPathImpl.mapShape(localShape);
/*      */     }
/* 2706 */     return localShape;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LayoutPath getLayoutPath()
/*      */   {
/* 2716 */     return this.textLine.getLayoutPath();
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
/*      */   public void hitToPoint(TextHitInfo paramTextHitInfo, Point2D paramPoint2D)
/*      */   {
/* 2735 */     if ((paramTextHitInfo == null) || (paramPoint2D == null)) {
/* 2736 */       throw new NullPointerException((paramTextHitInfo == null ? "hit" : "point") + " can't be null");
/*      */     }
/*      */     
/* 2739 */     ensureCache();
/* 2740 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 2742 */     float f1 = 0.0F;
/* 2743 */     float f2 = 0.0F;
/*      */     
/* 2745 */     int i = paramTextHitInfo.getCharIndex();
/* 2746 */     boolean bool1 = paramTextHitInfo.isLeadingEdge();
/*      */     boolean bool2;
/* 2748 */     if ((i == -1) || (i == this.textLine.characterCount())) {
/* 2749 */       bool2 = this.textLine.isDirectionLTR();
/* 2750 */       f1 = bool2 == (i == -1) ? 0.0F : this.lineMetrics.advance;
/*      */     } else {
/* 2752 */       bool2 = this.textLine.isCharLTR(i);
/* 2753 */       f1 = this.textLine.getCharLinePosition(i, bool1);
/* 2754 */       f2 = this.textLine.getCharYPosition(i);
/*      */     }
/* 2756 */     paramPoint2D.setLocation(f1, f2);
/* 2757 */     LayoutPathImpl localLayoutPathImpl = this.textLine.getLayoutPath();
/* 2758 */     if (localLayoutPathImpl != null) {
/* 2759 */       localLayoutPathImpl.pathToPoint(paramPoint2D, bool2 != bool1, paramPoint2D);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/font/TextLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */