/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.io.PrintStream;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.Bidi;
/*     */ import java.text.BreakIterator;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import sun.font.AttributeValues;
/*     */ import sun.font.BidiUtils;
/*     */ import sun.font.TextLabelFactory;
/*     */ import sun.font.TextLineComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TextMeasurer
/*     */   implements Cloneable
/*     */ {
/* 101 */   private static float EST_LINES = 2.1F;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private FontRenderContext fFrc;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int fStart;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] fChars;
/*     */   
/*     */ 
/*     */ 
/*     */   private Bidi fBidi;
/*     */   
/*     */ 
/*     */ 
/*     */   private byte[] fLevels;
/*     */   
/*     */ 
/*     */ 
/*     */   private TextLineComponent[] fComponents;
/*     */   
/*     */ 
/*     */ 
/*     */   private int fComponentStart;
/*     */   
/*     */ 
/*     */ 
/*     */   private int fComponentLimit;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean haveLayoutWindow;
/*     */   
/*     */ 
/*     */ 
/* 144 */   private BreakIterator fLineBreak = null;
/* 145 */   private CharArrayIterator charIter = null;
/* 146 */   int layoutCount = 0;
/* 147 */   int layoutCharCount = 0;
/*     */   
/*     */   private StyledParagraph fParagraph;
/*     */   
/*     */   private boolean fIsDirectionLTR;
/*     */   
/*     */   private byte fBaseline;
/*     */   
/*     */   private float[] fBaselineOffsets;
/* 156 */   private float fJustifyRatio = 1.0F;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextMeasurer(AttributedCharacterIterator paramAttributedCharacterIterator, FontRenderContext paramFontRenderContext)
/*     */   {
/* 167 */     this.fFrc = paramFontRenderContext;
/* 168 */     initAll(paramAttributedCharacterIterator);
/*     */   }
/*     */   
/*     */   protected Object clone() {
/*     */     TextMeasurer localTextMeasurer;
/*     */     try {
/* 174 */       localTextMeasurer = (TextMeasurer)super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 177 */       throw new Error();
/*     */     }
/* 179 */     if (this.fComponents != null) {
/* 180 */       localTextMeasurer.fComponents = ((TextLineComponent[])this.fComponents.clone());
/*     */     }
/* 182 */     return localTextMeasurer;
/*     */   }
/*     */   
/*     */   private void invalidateComponents() {
/* 186 */     this.fComponentStart = (this.fComponentLimit = this.fChars.length);
/* 187 */     this.fComponents = null;
/* 188 */     this.haveLayoutWindow = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initAll(AttributedCharacterIterator paramAttributedCharacterIterator)
/*     */   {
/* 197 */     this.fStart = paramAttributedCharacterIterator.getBeginIndex();
/*     */     
/*     */ 
/* 200 */     this.fChars = new char[paramAttributedCharacterIterator.getEndIndex() - this.fStart];
/*     */     
/* 202 */     int i = 0;
/* 203 */     for (int j = paramAttributedCharacterIterator.first(); 
/* 204 */         j != 65535; 
/* 205 */         j = paramAttributedCharacterIterator.next())
/*     */     {
/* 207 */       this.fChars[(i++)] = j;
/*     */     }
/*     */     
/* 210 */     paramAttributedCharacterIterator.first();
/*     */     
/* 212 */     this.fBidi = new Bidi(paramAttributedCharacterIterator);
/* 213 */     if (this.fBidi.isLeftToRight()) {
/* 214 */       this.fBidi = null;
/*     */     }
/*     */     
/* 217 */     paramAttributedCharacterIterator.first();
/* 218 */     Map localMap = paramAttributedCharacterIterator.getAttributes();
/* 219 */     NumericShaper localNumericShaper = AttributeValues.getNumericShaping(localMap);
/* 220 */     if (localNumericShaper != null) {
/* 221 */       localNumericShaper.shape(this.fChars, 0, this.fChars.length);
/*     */     }
/*     */     
/* 224 */     this.fParagraph = new StyledParagraph(paramAttributedCharacterIterator, this.fChars);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 232 */     this.fJustifyRatio = AttributeValues.getJustification(localMap);
/*     */     
/* 234 */     boolean bool = TextLine.advanceToFirstFont(paramAttributedCharacterIterator);
/*     */     Object localObject1;
/* 236 */     Object localObject2; if (bool) {
/* 237 */       localObject1 = TextLine.getFontAtCurrentPos(paramAttributedCharacterIterator);
/* 238 */       int k = paramAttributedCharacterIterator.getIndex() - paramAttributedCharacterIterator.getBeginIndex();
/* 239 */       localObject2 = ((Font)localObject1).getLineMetrics(this.fChars, k, k + 1, this.fFrc);
/* 240 */       this.fBaseline = ((byte)((LineMetrics)localObject2).getBaselineIndex());
/* 241 */       this.fBaselineOffsets = ((LineMetrics)localObject2).getBaselineOffsets();
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 248 */       localObject1 = (GraphicAttribute)localMap.get(TextAttribute.CHAR_REPLACEMENT);
/* 249 */       this.fBaseline = TextLayout.getBaselineFromGraphic((GraphicAttribute)localObject1);
/* 250 */       Hashtable localHashtable = new Hashtable(5, 0.9F);
/* 251 */       localObject2 = new Font(localHashtable);
/* 252 */       LineMetrics localLineMetrics = ((Font)localObject2).getLineMetrics(" ", 0, 1, this.fFrc);
/* 253 */       this.fBaselineOffsets = localLineMetrics.getBaselineOffsets();
/*     */     }
/* 255 */     this.fBaselineOffsets = TextLine.getNormalizedOffsets(this.fBaselineOffsets, this.fBaseline);
/*     */     
/*     */ 
/* 258 */     invalidateComponents();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void generateComponents(int paramInt1, int paramInt2)
/*     */   {
/* 267 */     if (this.collectStats) {
/* 268 */       this.formattedChars += paramInt2 - paramInt1;
/*     */     }
/* 270 */     int i = 0;
/* 271 */     TextLabelFactory localTextLabelFactory = new TextLabelFactory(this.fFrc, this.fChars, this.fBidi, i);
/*     */     
/* 273 */     int[] arrayOfInt1 = null;
/*     */     
/* 275 */     if (this.fBidi != null) {
/* 276 */       this.fLevels = BidiUtils.getLevels(this.fBidi);
/* 277 */       int[] arrayOfInt2 = BidiUtils.createVisualToLogicalMap(this.fLevels);
/* 278 */       arrayOfInt1 = BidiUtils.createInverseMap(arrayOfInt2);
/* 279 */       this.fIsDirectionLTR = this.fBidi.baseIsLeftToRight();
/*     */     }
/*     */     else {
/* 282 */       this.fLevels = null;
/* 283 */       this.fIsDirectionLTR = true;
/*     */     }
/*     */     try
/*     */     {
/* 287 */       this.fComponents = TextLine.getComponents(this.fParagraph, this.fChars, paramInt1, paramInt2, arrayOfInt1, this.fLevels, localTextLabelFactory);
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException)
/*     */     {
/* 291 */       System.out.println("startingAt=" + paramInt1 + "; endingAt=" + paramInt2);
/* 292 */       System.out.println("fComponentLimit=" + this.fComponentLimit);
/* 293 */       throw localIllegalArgumentException;
/*     */     }
/*     */     
/* 296 */     this.fComponentStart = paramInt1;
/* 297 */     this.fComponentLimit = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int calcLineBreak(int paramInt, float paramFloat)
/*     */   {
/* 307 */     int i = paramInt;
/* 308 */     float f = paramFloat;
/*     */     
/*     */ 
/* 311 */     int k = this.fComponentStart;
/*     */     
/* 313 */     for (int j = 0; j < this.fComponents.length; j++) {
/* 314 */       int m = k + this.fComponents[j].getNumCharacters();
/* 315 */       if (m > i) {
/*     */         break;
/*     */       }
/*     */       
/* 319 */       k = m;
/*     */     }
/* 325 */     for (; 
/*     */         
/*     */ 
/*     */ 
/* 325 */         j < this.fComponents.length; j++)
/*     */     {
/* 327 */       TextLineComponent localTextLineComponent = this.fComponents[j];
/* 328 */       int n = localTextLineComponent.getNumCharacters();
/*     */       
/* 330 */       int i1 = localTextLineComponent.getLineBreakIndex(i - k, f);
/* 331 */       if ((i1 == n) && (j < this.fComponents.length)) {
/* 332 */         f -= localTextLineComponent.getAdvanceBetween(i - k, i1);
/* 333 */         k += n;
/* 334 */         i = k;
/*     */       }
/*     */       else {
/* 337 */         return k + i1;
/*     */       }
/*     */     }
/*     */     
/* 341 */     if (this.fComponentLimit < this.fChars.length)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 347 */       generateComponents(paramInt, this.fChars.length);
/* 348 */       return calcLineBreak(paramInt, paramFloat);
/*     */     }
/*     */     
/* 351 */     return this.fChars.length;
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
/*     */   private int trailingCdWhitespaceStart(int paramInt1, int paramInt2)
/*     */   {
/* 365 */     if (this.fLevels != null)
/*     */     {
/* 367 */       int i = (byte)(this.fIsDirectionLTR ? 0 : 1);
/* 368 */       int j = paramInt2; do { j--; if (j < paramInt1) break;
/* 369 */       } while ((this.fLevels[j] % 2 != i) && 
/* 370 */         (Character.getDirectionality(this.fChars[j]) == 12));
/* 371 */       j++;return j;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 376 */     return paramInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private TextLineComponent[] makeComponentsOnRange(int paramInt1, int paramInt2)
/*     */   {
/* 386 */     int i = trailingCdWhitespaceStart(paramInt1, paramInt2);
/*     */     
/*     */ 
/* 389 */     int k = this.fComponentStart;
/*     */     
/* 391 */     for (int j = 0; j < this.fComponents.length; j++) {
/* 392 */       m = k + this.fComponents[j].getNumCharacters();
/* 393 */       if (m > paramInt1) {
/*     */         break;
/*     */       }
/*     */       
/* 397 */       k = m;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 405 */     int n = 0;
/* 406 */     int i1 = k;
/* 407 */     int i2 = j;
/* 408 */     int i4; for (int i3 = 1; i3 != 0; i2++) {
/* 409 */       i4 = i1 + this.fComponents[i2].getNumCharacters();
/* 410 */       if ((i > Math.max(i1, paramInt1)) && 
/* 411 */         (i < Math.min(i4, paramInt2))) {
/* 412 */         n = 1;
/*     */       }
/* 414 */       if (i4 >= paramInt2) {
/* 415 */         i3 = 0;
/*     */       }
/*     */       else {
/* 418 */         i1 = i4;
/*     */       }
/*     */     }
/* 421 */     int m = i2 - j;
/* 422 */     if (n != 0) {
/* 423 */       m++;
/*     */     }
/*     */     
/*     */ 
/* 427 */     TextLineComponent[] arrayOfTextLineComponent = new TextLineComponent[m];
/* 428 */     i1 = 0;
/* 429 */     i2 = paramInt1;
/*     */     
/* 431 */     i3 = i;
/*     */     
/*     */ 
/* 434 */     if (i3 == paramInt1) {
/* 435 */       i4 = this.fIsDirectionLTR ? 0 : 1;
/*     */       
/* 437 */       i3 = paramInt2;
/*     */     }
/*     */     else {
/* 440 */       i4 = 2;
/*     */     }
/*     */     
/* 443 */     while (i2 < paramInt2)
/*     */     {
/* 445 */       int i5 = this.fComponents[j].getNumCharacters();
/* 446 */       int i6 = k + i5;
/*     */       
/* 448 */       int i7 = Math.max(i2, k);
/* 449 */       int i8 = Math.min(i3, i6);
/*     */       
/* 451 */       arrayOfTextLineComponent[(i1++)] = this.fComponents[j].getSubset(i7 - k, i8 - k, i4);
/*     */       
/*     */ 
/*     */ 
/* 455 */       i2 += i8 - i7;
/* 456 */       if (i2 == i3) {
/* 457 */         i3 = paramInt2;
/* 458 */         i4 = this.fIsDirectionLTR ? 0 : 1;
/*     */       }
/*     */       
/* 461 */       if (i2 == i6) {
/* 462 */         j++;
/* 463 */         k = i6;
/*     */       }
/*     */     }
/*     */     
/* 467 */     return arrayOfTextLineComponent;
/*     */   }
/*     */   
/*     */   private TextLine makeTextLineOnRange(int paramInt1, int paramInt2)
/*     */   {
/* 472 */     int[] arrayOfInt1 = null;
/* 473 */     byte[] arrayOfByte = null;
/*     */     
/* 475 */     if (this.fBidi != null) {
/* 476 */       localObject = this.fBidi.createLineBidi(paramInt1, paramInt2);
/* 477 */       arrayOfByte = BidiUtils.getLevels((Bidi)localObject);
/* 478 */       int[] arrayOfInt2 = BidiUtils.createVisualToLogicalMap(arrayOfByte);
/* 479 */       arrayOfInt1 = BidiUtils.createInverseMap(arrayOfInt2);
/*     */     }
/*     */     
/* 482 */     Object localObject = makeComponentsOnRange(paramInt1, paramInt2);
/*     */     
/* 484 */     return new TextLine(this.fFrc, (TextLineComponent[])localObject, this.fBaselineOffsets, this.fChars, paramInt1, paramInt2, arrayOfInt1, arrayOfByte, this.fIsDirectionLTR);
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
/*     */   private void ensureComponents(int paramInt1, int paramInt2)
/*     */   {
/* 498 */     if ((paramInt1 < this.fComponentStart) || (paramInt2 > this.fComponentLimit)) {
/* 499 */       generateComponents(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void makeLayoutWindow(int paramInt)
/*     */   {
/* 505 */     int i = paramInt;
/* 506 */     int j = this.fChars.length;
/*     */     
/*     */ 
/* 509 */     if ((this.layoutCount > 0) && (!this.haveLayoutWindow)) {
/* 510 */       float f = Math.max(this.layoutCharCount / this.layoutCount, 1);
/* 511 */       j = Math.min(paramInt + (int)(f * EST_LINES), this.fChars.length);
/*     */     }
/*     */     
/* 514 */     if ((paramInt > 0) || (j < this.fChars.length)) {
/* 515 */       if (this.charIter == null) {
/* 516 */         this.charIter = new CharArrayIterator(this.fChars);
/*     */       }
/*     */       else {
/* 519 */         this.charIter.reset(this.fChars);
/*     */       }
/* 521 */       if (this.fLineBreak == null) {
/* 522 */         this.fLineBreak = BreakIterator.getLineInstance();
/*     */       }
/* 524 */       this.fLineBreak.setText(this.charIter);
/* 525 */       if ((paramInt > 0) && 
/* 526 */         (!this.fLineBreak.isBoundary(paramInt))) {
/* 527 */         i = this.fLineBreak.preceding(paramInt);
/*     */       }
/*     */       
/* 530 */       if ((j < this.fChars.length) && 
/* 531 */         (!this.fLineBreak.isBoundary(j))) {
/* 532 */         j = this.fLineBreak.following(j);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 537 */     ensureComponents(i, j);
/* 538 */     this.haveLayoutWindow = true;
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
/*     */   public int getLineBreakIndex(int paramInt, float paramFloat)
/*     */   {
/* 558 */     int i = paramInt - this.fStart;
/*     */     
/* 560 */     if ((!this.haveLayoutWindow) || (i < this.fComponentStart) || (i >= this.fComponentLimit))
/*     */     {
/*     */ 
/* 563 */       makeLayoutWindow(i);
/*     */     }
/*     */     
/* 566 */     return calcLineBreak(i, paramFloat) + this.fStart;
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
/*     */   public float getAdvanceBetween(int paramInt1, int paramInt2)
/*     */   {
/* 587 */     int i = paramInt1 - this.fStart;
/* 588 */     int j = paramInt2 - this.fStart;
/*     */     
/* 590 */     ensureComponents(i, j);
/* 591 */     TextLine localTextLine = makeTextLineOnRange(i, j);
/* 592 */     return localTextLine.getMetrics().advance;
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
/*     */   public TextLayout getLayout(int paramInt1, int paramInt2)
/*     */   {
/* 612 */     int i = paramInt1 - this.fStart;
/* 613 */     int j = paramInt2 - this.fStart;
/*     */     
/* 615 */     ensureComponents(i, j);
/* 616 */     TextLine localTextLine = makeTextLineOnRange(i, j);
/*     */     
/* 618 */     if (j < this.fChars.length) {
/* 619 */       this.layoutCharCount += paramInt2 - paramInt1;
/* 620 */       this.layoutCount += 1;
/*     */     }
/*     */     
/* 623 */     return new TextLayout(localTextLine, this.fBaseline, this.fBaselineOffsets, this.fJustifyRatio);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 629 */   private int formattedChars = 0;
/* 630 */   private static boolean wantStats = false;
/* 631 */   private boolean collectStats = false;
/*     */   
/*     */   private void printStats() {
/* 634 */     System.out.println("formattedChars: " + this.formattedChars);
/*     */     
/* 636 */     this.collectStats = false;
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
/*     */   public void insertChar(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt)
/*     */   {
/* 663 */     if (this.collectStats) {
/* 664 */       printStats();
/*     */     }
/* 666 */     if (wantStats) {
/* 667 */       this.collectStats = true;
/*     */     }
/*     */     
/* 670 */     this.fStart = paramAttributedCharacterIterator.getBeginIndex();
/* 671 */     int i = paramAttributedCharacterIterator.getEndIndex();
/* 672 */     if (i - this.fStart != this.fChars.length + 1) {
/* 673 */       initAll(paramAttributedCharacterIterator);
/*     */     }
/*     */     
/* 676 */     char[] arrayOfChar = new char[i - this.fStart];
/* 677 */     int j = paramInt - this.fStart;
/* 678 */     System.arraycopy(this.fChars, 0, arrayOfChar, 0, j);
/*     */     
/* 680 */     int k = paramAttributedCharacterIterator.setIndex(paramInt);
/* 681 */     arrayOfChar[j] = k;
/* 682 */     System.arraycopy(this.fChars, j, arrayOfChar, j + 1, i - paramInt - 1);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 687 */     this.fChars = arrayOfChar;
/*     */     
/* 689 */     if ((this.fBidi != null) || (Bidi.requiresBidi(arrayOfChar, j, j + 1)) || 
/* 690 */       (paramAttributedCharacterIterator.getAttribute(TextAttribute.BIDI_EMBEDDING) != null))
/*     */     {
/* 692 */       this.fBidi = new Bidi(paramAttributedCharacterIterator);
/* 693 */       if (this.fBidi.isLeftToRight()) {
/* 694 */         this.fBidi = null;
/*     */       }
/*     */     }
/*     */     
/* 698 */     this.fParagraph = StyledParagraph.insertChar(paramAttributedCharacterIterator, this.fChars, paramInt, this.fParagraph);
/*     */     
/*     */ 
/*     */ 
/* 702 */     invalidateComponents();
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
/*     */   public void deleteChar(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt)
/*     */   {
/* 729 */     this.fStart = paramAttributedCharacterIterator.getBeginIndex();
/* 730 */     int i = paramAttributedCharacterIterator.getEndIndex();
/* 731 */     if (i - this.fStart != this.fChars.length - 1) {
/* 732 */       initAll(paramAttributedCharacterIterator);
/*     */     }
/*     */     
/* 735 */     char[] arrayOfChar = new char[i - this.fStart];
/* 736 */     int j = paramInt - this.fStart;
/*     */     
/* 738 */     System.arraycopy(this.fChars, 0, arrayOfChar, 0, paramInt - this.fStart);
/* 739 */     System.arraycopy(this.fChars, j + 1, arrayOfChar, j, i - paramInt);
/* 740 */     this.fChars = arrayOfChar;
/*     */     
/* 742 */     if (this.fBidi != null) {
/* 743 */       this.fBidi = new Bidi(paramAttributedCharacterIterator);
/* 744 */       if (this.fBidi.isLeftToRight()) {
/* 745 */         this.fBidi = null;
/*     */       }
/*     */     }
/*     */     
/* 749 */     this.fParagraph = StyledParagraph.deleteChar(paramAttributedCharacterIterator, this.fChars, paramInt, this.fParagraph);
/*     */     
/*     */ 
/*     */ 
/* 753 */     invalidateComponents();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   char[] getChars()
/*     */   {
/* 762 */     return this.fChars;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/font/TextMeasurer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */