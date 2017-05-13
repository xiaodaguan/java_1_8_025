/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.im.InputMethodHighlight;
/*     */ import java.text.Annotation;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.AttributedCharacterIterator.Attribute;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import sun.font.Decoration;
/*     */ import sun.font.FontResolver;
/*     */ import sun.text.CodePointIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StyledParagraph
/*     */ {
/*     */   private int length;
/*     */   private Decoration decoration;
/*     */   private Object font;
/*     */   private Vector<Decoration> decorations;
/*     */   int[] decorationStarts;
/*     */   private Vector<Object> fonts;
/*     */   int[] fontStarts;
/*  87 */   private static int INITIAL_SIZE = 8;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StyledParagraph(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfChar)
/*     */   {
/*  97 */     int i = paramAttributedCharacterIterator.getBeginIndex();
/*  98 */     int j = paramAttributedCharacterIterator.getEndIndex();
/*  99 */     this.length = (j - i);
/*     */     
/* 101 */     int k = i;
/* 102 */     paramAttributedCharacterIterator.first();
/*     */     do
/*     */     {
/* 105 */       int m = paramAttributedCharacterIterator.getRunLimit();
/* 106 */       int n = k - i;
/*     */       
/* 108 */       Map localMap = paramAttributedCharacterIterator.getAttributes();
/* 109 */       localMap = addInputMethodAttrs(localMap);
/* 110 */       Decoration localDecoration = Decoration.getDecoration(localMap);
/* 111 */       addDecoration(localDecoration, n);
/*     */       
/* 113 */       Object localObject = getGraphicOrFont(localMap);
/* 114 */       if (localObject == null) {
/* 115 */         addFonts(paramArrayOfChar, localMap, n, m - i);
/*     */       }
/*     */       else {
/* 118 */         addFont(localObject, n);
/*     */       }
/*     */       
/* 121 */       paramAttributedCharacterIterator.setIndex(m);
/* 122 */       k = m;
/*     */     }
/* 124 */     while (k < j);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 129 */     if (this.decorations != null) {
/* 130 */       this.decorationStarts = addToVector(this, this.length, this.decorations, this.decorationStarts);
/*     */     }
/* 132 */     if (this.fonts != null) {
/* 133 */       this.fontStarts = addToVector(this, this.length, this.fonts, this.fontStarts);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void insertInto(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*     */   {
/* 143 */     while (paramArrayOfInt[(--paramInt2)] > paramInt1) {
/* 144 */       paramArrayOfInt[paramInt2] += 1;
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
/*     */   public static StyledParagraph insertChar(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfChar, int paramInt, StyledParagraph paramStyledParagraph)
/*     */   {
/* 169 */     char c = paramAttributedCharacterIterator.setIndex(paramInt);
/* 170 */     int i = Math.max(paramInt - paramAttributedCharacterIterator.getBeginIndex() - 1, 0);
/*     */     
/*     */ 
/* 173 */     Map localMap = addInputMethodAttrs(paramAttributedCharacterIterator.getAttributes());
/* 174 */     Decoration localDecoration = Decoration.getDecoration(localMap);
/* 175 */     if (!paramStyledParagraph.getDecorationAt(i).equals(localDecoration)) {
/* 176 */       return new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfChar);
/*     */     }
/* 178 */     Object localObject = getGraphicOrFont(localMap);
/* 179 */     if (localObject == null) {
/* 180 */       FontResolver localFontResolver = FontResolver.getInstance();
/* 181 */       int j = localFontResolver.getFontIndex(c);
/* 182 */       localObject = localFontResolver.getFont(j, localMap);
/*     */     }
/* 184 */     if (!paramStyledParagraph.getFontOrGraphicAt(i).equals(localObject)) {
/* 185 */       return new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfChar);
/*     */     }
/*     */     
/*     */ 
/* 189 */     paramStyledParagraph.length += 1;
/* 190 */     if (paramStyledParagraph.decorations != null) {
/* 191 */       insertInto(i, paramStyledParagraph.decorationStarts, paramStyledParagraph.decorations
/*     */       
/* 193 */         .size());
/*     */     }
/* 195 */     if (paramStyledParagraph.fonts != null) {
/* 196 */       insertInto(i, paramStyledParagraph.fontStarts, paramStyledParagraph.fonts
/*     */       
/* 198 */         .size());
/*     */     }
/* 200 */     return paramStyledParagraph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void deleteFrom(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*     */   {
/* 211 */     while (paramArrayOfInt[(--paramInt2)] > paramInt1) {
/* 212 */       paramArrayOfInt[paramInt2] -= 1;
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
/*     */   public static StyledParagraph deleteChar(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfChar, int paramInt, StyledParagraph paramStyledParagraph)
/*     */   {
/* 236 */     paramInt -= paramAttributedCharacterIterator.getBeginIndex();
/*     */     
/* 238 */     if ((paramStyledParagraph.decorations == null) && (paramStyledParagraph.fonts == null)) {
/* 239 */       paramStyledParagraph.length -= 1;
/* 240 */       return paramStyledParagraph;
/*     */     }
/*     */     
/* 243 */     if ((paramStyledParagraph.getRunLimit(paramInt) == paramInt + 1) && (
/* 244 */       (paramInt == 0) || (paramStyledParagraph.getRunLimit(paramInt - 1) == paramInt))) {
/* 245 */       return new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfChar);
/*     */     }
/*     */     
/*     */ 
/* 249 */     paramStyledParagraph.length -= 1;
/* 250 */     if (paramStyledParagraph.decorations != null) {
/* 251 */       deleteFrom(paramInt, paramStyledParagraph.decorationStarts, paramStyledParagraph.decorations
/*     */       
/* 253 */         .size());
/*     */     }
/* 255 */     if (paramStyledParagraph.fonts != null) {
/* 256 */       deleteFrom(paramInt, paramStyledParagraph.fontStarts, paramStyledParagraph.fonts
/*     */       
/* 258 */         .size());
/*     */     }
/* 260 */     return paramStyledParagraph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRunLimit(int paramInt)
/*     */   {
/* 272 */     if ((paramInt < 0) || (paramInt >= this.length)) {
/* 273 */       throw new IllegalArgumentException("index out of range");
/*     */     }
/* 275 */     int i = this.length;
/* 276 */     if (this.decorations != null) {
/* 277 */       j = findRunContaining(paramInt, this.decorationStarts);
/* 278 */       i = this.decorationStarts[(j + 1)];
/*     */     }
/* 280 */     int j = this.length;
/* 281 */     if (this.fonts != null) {
/* 282 */       int k = findRunContaining(paramInt, this.fontStarts);
/* 283 */       j = this.fontStarts[(k + 1)];
/*     */     }
/* 285 */     return Math.min(i, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Decoration getDecorationAt(int paramInt)
/*     */   {
/* 295 */     if ((paramInt < 0) || (paramInt >= this.length)) {
/* 296 */       throw new IllegalArgumentException("index out of range");
/*     */     }
/* 298 */     if (this.decorations == null) {
/* 299 */       return this.decoration;
/*     */     }
/* 301 */     int i = findRunContaining(paramInt, this.decorationStarts);
/* 302 */     return (Decoration)this.decorations.elementAt(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFontOrGraphicAt(int paramInt)
/*     */   {
/* 314 */     if ((paramInt < 0) || (paramInt >= this.length)) {
/* 315 */       throw new IllegalArgumentException("index out of range");
/*     */     }
/* 317 */     if (this.fonts == null) {
/* 318 */       return this.font;
/*     */     }
/* 320 */     int i = findRunContaining(paramInt, this.fontStarts);
/* 321 */     return this.fonts.elementAt(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int findRunContaining(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 331 */     for (int i = 1;; i++) {
/* 332 */       if (paramArrayOfInt[i] > paramInt) {
/* 333 */         return i - 1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int[] addToVector(Object paramObject, int paramInt, Vector paramVector, int[] paramArrayOfInt)
/*     */   {
/* 350 */     if (!paramVector.lastElement().equals(paramObject)) {
/* 351 */       paramVector.addElement(paramObject);
/* 352 */       int i = paramVector.size();
/* 353 */       if (paramArrayOfInt.length == i) {
/* 354 */         int[] arrayOfInt = new int[paramArrayOfInt.length * 2];
/* 355 */         System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
/* 356 */         paramArrayOfInt = arrayOfInt;
/*     */       }
/* 358 */       paramArrayOfInt[(i - 1)] = paramInt;
/*     */     }
/* 360 */     return paramArrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addDecoration(Decoration paramDecoration, int paramInt)
/*     */   {
/* 369 */     if (this.decorations != null) {
/* 370 */       this.decorationStarts = addToVector(paramDecoration, paramInt, this.decorations, this.decorationStarts);
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 375 */     else if (this.decoration == null) {
/* 376 */       this.decoration = paramDecoration;
/*     */ 
/*     */     }
/* 379 */     else if (!this.decoration.equals(paramDecoration)) {
/* 380 */       this.decorations = new Vector(INITIAL_SIZE);
/* 381 */       this.decorations.addElement(this.decoration);
/* 382 */       this.decorations.addElement(paramDecoration);
/* 383 */       this.decorationStarts = new int[INITIAL_SIZE];
/* 384 */       this.decorationStarts[0] = 0;
/* 385 */       this.decorationStarts[1] = paramInt;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addFont(Object paramObject, int paramInt)
/*     */   {
/* 396 */     if (this.fonts != null) {
/* 397 */       this.fontStarts = addToVector(paramObject, paramInt, this.fonts, this.fontStarts);
/*     */     }
/* 399 */     else if (this.font == null) {
/* 400 */       this.font = paramObject;
/*     */ 
/*     */     }
/* 403 */     else if (!this.font.equals(paramObject)) {
/* 404 */       this.fonts = new Vector(INITIAL_SIZE);
/* 405 */       this.fonts.addElement(this.font);
/* 406 */       this.fonts.addElement(paramObject);
/* 407 */       this.fontStarts = new int[INITIAL_SIZE];
/* 408 */       this.fontStarts[0] = 0;
/* 409 */       this.fontStarts[1] = paramInt;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addFonts(char[] paramArrayOfChar, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, int paramInt1, int paramInt2)
/*     */   {
/* 421 */     FontResolver localFontResolver = FontResolver.getInstance();
/* 422 */     CodePointIterator localCodePointIterator = CodePointIterator.create(paramArrayOfChar, paramInt1, paramInt2);
/* 423 */     for (int i = localCodePointIterator.charIndex(); i < paramInt2; i = localCodePointIterator.charIndex()) {
/* 424 */       int j = localFontResolver.nextFontRunIndex(localCodePointIterator);
/* 425 */       addFont(localFontResolver.getFont(j, paramMap), i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Map<? extends AttributedCharacterIterator.Attribute, ?> addInputMethodAttrs(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*     */   {
/* 436 */     Object localObject1 = paramMap.get(TextAttribute.INPUT_METHOD_HIGHLIGHT);
/*     */     try
/*     */     {
/* 439 */       if (localObject1 != null) {
/* 440 */         if ((localObject1 instanceof Annotation)) {
/* 441 */           localObject1 = ((Annotation)localObject1).getValue();
/*     */         }
/*     */         
/*     */ 
/* 445 */         InputMethodHighlight localInputMethodHighlight = (InputMethodHighlight)localObject1;
/*     */         
/* 447 */         Map localMap = null;
/*     */         try {
/* 449 */           localMap = localInputMethodHighlight.getStyle();
/*     */         }
/*     */         catch (NoSuchMethodError localNoSuchMethodError) {}
/*     */         Object localObject2;
/* 453 */         if (localMap == null) {
/* 454 */           localObject2 = Toolkit.getDefaultToolkit();
/* 455 */           localMap = ((Toolkit)localObject2).mapInputMethodHighlight(localInputMethodHighlight);
/*     */         }
/*     */         
/* 458 */         if (localMap != null)
/*     */         {
/* 460 */           localObject2 = new HashMap(5, 0.9F);
/* 461 */           ((HashMap)localObject2).putAll(paramMap);
/*     */           
/* 463 */           ((HashMap)localObject2).putAll(localMap);
/*     */           
/* 465 */           return (Map<? extends AttributedCharacterIterator.Attribute, ?>)localObject2;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ClassCastException localClassCastException) {}
/*     */     
/*     */ 
/* 472 */     return paramMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Object getGraphicOrFont(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*     */   {
/* 483 */     Object localObject = paramMap.get(TextAttribute.CHAR_REPLACEMENT);
/* 484 */     if (localObject != null) {
/* 485 */       return localObject;
/*     */     }
/* 487 */     localObject = paramMap.get(TextAttribute.FONT);
/* 488 */     if (localObject != null) {
/* 489 */       return localObject;
/*     */     }
/*     */     
/* 492 */     if (paramMap.get(TextAttribute.FAMILY) != null) {
/* 493 */       return Font.getFont(paramMap);
/*     */     }
/*     */     
/* 496 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/font/StyledParagraph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */