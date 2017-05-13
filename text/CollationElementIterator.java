/*     */ package java.text;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import sun.text.CollatorUtilities;
/*     */ import sun.text.normalizer.NormalizerBase;
/*     */ import sun.text.normalizer.NormalizerBase.Mode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CollationElementIterator
/*     */ {
/*     */   public static final int NULLORDER = -1;
/*     */   static final int UNMAPPEDCHARVALUE = 2147418112;
/*     */   
/*     */   CollationElementIterator(String paramString, RuleBasedCollator paramRuleBasedCollator)
/*     */   {
/* 125 */     this.owner = paramRuleBasedCollator;
/* 126 */     this.ordering = paramRuleBasedCollator.getTables();
/* 127 */     if (paramString.length() != 0)
/*     */     {
/* 129 */       NormalizerBase.Mode localMode = CollatorUtilities.toNormalizerMode(paramRuleBasedCollator.getDecomposition());
/* 130 */       this.text = new NormalizerBase(paramString, localMode);
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
/*     */   CollationElementIterator(CharacterIterator paramCharacterIterator, RuleBasedCollator paramRuleBasedCollator)
/*     */   {
/* 143 */     this.owner = paramRuleBasedCollator;
/* 144 */     this.ordering = paramRuleBasedCollator.getTables();
/*     */     
/* 146 */     NormalizerBase.Mode localMode = CollatorUtilities.toNormalizerMode(paramRuleBasedCollator.getDecomposition());
/* 147 */     this.text = new NormalizerBase(paramCharacterIterator, localMode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 156 */     if (this.text != null) {
/* 157 */       this.text.reset();
/*     */       
/* 159 */       NormalizerBase.Mode localMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
/* 160 */       this.text.setMode(localMode);
/*     */     }
/* 162 */     this.buffer = null;
/* 163 */     this.expIndex = 0;
/* 164 */     this.swapOrder = 0;
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
/*     */   public int next()
/*     */   {
/* 185 */     if (this.text == null) {
/* 186 */       return -1;
/*     */     }
/* 188 */     NormalizerBase.Mode localMode1 = this.text.getMode();
/*     */     
/*     */ 
/* 191 */     NormalizerBase.Mode localMode2 = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
/* 192 */     if (localMode1 != localMode2) {
/* 193 */       this.text.setMode(localMode2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 199 */     if (this.buffer != null) {
/* 200 */       if (this.expIndex < this.buffer.length) {
/* 201 */         return strengthOrder(this.buffer[(this.expIndex++)]);
/*     */       }
/* 203 */       this.buffer = null;
/* 204 */       this.expIndex = 0;
/*     */     }
/* 206 */     else if (this.swapOrder != 0) {
/* 207 */       if (Character.isSupplementaryCodePoint(this.swapOrder)) {
/* 208 */         char[] arrayOfChar = Character.toChars(this.swapOrder);
/* 209 */         this.swapOrder = arrayOfChar[1];
/* 210 */         return arrayOfChar[0] << '\020';
/*     */       }
/* 212 */       i = this.swapOrder << 16;
/* 213 */       this.swapOrder = 0;
/* 214 */       return i;
/*     */     }
/* 216 */     int i = this.text.next();
/*     */     
/*     */ 
/* 219 */     if (i == -1) {
/* 220 */       return -1;
/*     */     }
/*     */     
/* 223 */     int j = this.ordering.getUnicodeOrder(i);
/* 224 */     if (j == -1) {
/* 225 */       this.swapOrder = i;
/* 226 */       return 2147418112;
/*     */     }
/* 228 */     if (j >= 2130706432) {
/* 229 */       j = nextContractChar(i);
/*     */     }
/* 231 */     if (j >= 2113929216) {
/* 232 */       this.buffer = this.ordering.getExpandValueList(j);
/* 233 */       this.expIndex = 0;
/* 234 */       j = this.buffer[(this.expIndex++)];
/*     */     }
/*     */     
/* 237 */     if (this.ordering.isSEAsianSwapping()) {
/*     */       int k;
/* 239 */       if (isThaiPreVowel(i)) {
/* 240 */         k = this.text.next();
/* 241 */         if (isThaiBaseConsonant(k)) {
/* 242 */           this.buffer = makeReorderedBuffer(k, j, this.buffer, true);
/* 243 */           j = this.buffer[0];
/* 244 */           this.expIndex = 1;
/* 245 */         } else if (k != -1) {
/* 246 */           this.text.previous();
/*     */         }
/*     */       }
/* 249 */       if (isLaoPreVowel(i)) {
/* 250 */         k = this.text.next();
/* 251 */         if (isLaoBaseConsonant(k)) {
/* 252 */           this.buffer = makeReorderedBuffer(k, j, this.buffer, true);
/* 253 */           j = this.buffer[0];
/* 254 */           this.expIndex = 1;
/* 255 */         } else if (k != -1) {
/* 256 */           this.text.previous();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 261 */     return strengthOrder(j);
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
/*     */   public int previous()
/*     */   {
/* 283 */     if (this.text == null) {
/* 284 */       return -1;
/*     */     }
/* 286 */     NormalizerBase.Mode localMode1 = this.text.getMode();
/*     */     
/*     */ 
/* 289 */     NormalizerBase.Mode localMode2 = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
/* 290 */     if (localMode1 != localMode2) {
/* 291 */       this.text.setMode(localMode2);
/*     */     }
/* 293 */     if (this.buffer != null) {
/* 294 */       if (this.expIndex > 0) {
/* 295 */         return strengthOrder(this.buffer[(--this.expIndex)]);
/*     */       }
/* 297 */       this.buffer = null;
/* 298 */       this.expIndex = 0;
/*     */     }
/* 300 */     else if (this.swapOrder != 0) {
/* 301 */       if (Character.isSupplementaryCodePoint(this.swapOrder)) {
/* 302 */         char[] arrayOfChar = Character.toChars(this.swapOrder);
/* 303 */         this.swapOrder = arrayOfChar[1];
/* 304 */         return arrayOfChar[0] << '\020';
/*     */       }
/* 306 */       i = this.swapOrder << 16;
/* 307 */       this.swapOrder = 0;
/* 308 */       return i;
/*     */     }
/* 310 */     int i = this.text.previous();
/* 311 */     if (i == -1) {
/* 312 */       return -1;
/*     */     }
/*     */     
/* 315 */     int j = this.ordering.getUnicodeOrder(i);
/*     */     
/* 317 */     if (j == -1) {
/* 318 */       this.swapOrder = 2147418112;
/* 319 */       return i; }
/* 320 */     if (j >= 2130706432) {
/* 321 */       j = prevContractChar(i);
/*     */     }
/* 323 */     if (j >= 2113929216) {
/* 324 */       this.buffer = this.ordering.getExpandValueList(j);
/* 325 */       this.expIndex = this.buffer.length;
/* 326 */       j = this.buffer[(--this.expIndex)];
/*     */     }
/*     */     
/* 329 */     if (this.ordering.isSEAsianSwapping()) {
/*     */       int k;
/* 331 */       if (isThaiBaseConsonant(i)) {
/* 332 */         k = this.text.previous();
/* 333 */         if (isThaiPreVowel(k)) {
/* 334 */           this.buffer = makeReorderedBuffer(k, j, this.buffer, false);
/* 335 */           this.expIndex = (this.buffer.length - 1);
/* 336 */           j = this.buffer[this.expIndex];
/*     */         } else {
/* 338 */           this.text.next();
/*     */         }
/*     */       }
/* 341 */       if (isLaoBaseConsonant(i)) {
/* 342 */         k = this.text.previous();
/* 343 */         if (isLaoPreVowel(k)) {
/* 344 */           this.buffer = makeReorderedBuffer(k, j, this.buffer, false);
/* 345 */           this.expIndex = (this.buffer.length - 1);
/* 346 */           j = this.buffer[this.expIndex];
/*     */         } else {
/* 348 */           this.text.next();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 353 */     return strengthOrder(j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int primaryOrder(int paramInt)
/*     */   {
/* 363 */     paramInt &= 0xFFFF0000;
/* 364 */     return paramInt >>> 16;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final short secondaryOrder(int paramInt)
/*     */   {
/* 373 */     paramInt &= 0xFF00;
/* 374 */     return (short)(paramInt >> 8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final short tertiaryOrder(int paramInt)
/*     */   {
/* 383 */     return (short)(paramInt &= 0xFF);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final int strengthOrder(int paramInt)
/*     */   {
/* 393 */     int i = this.owner.getStrength();
/* 394 */     if (i == 0)
/*     */     {
/* 396 */       paramInt &= 0xFFFF0000;
/* 397 */     } else if (i == 1)
/*     */     {
/* 399 */       paramInt &= 0xFF00;
/*     */     }
/* 401 */     return paramInt;
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
/*     */   public void setOffset(int paramInt)
/*     */   {
/* 422 */     if (this.text != null) {
/* 423 */       if ((paramInt < this.text.getBeginIndex()) || 
/* 424 */         (paramInt >= this.text.getEndIndex())) {
/* 425 */         this.text.setIndexOnly(paramInt);
/*     */       } else {
/* 427 */         int i = this.text.setIndex(paramInt);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 432 */         if (this.ordering.usedInContractSeq(i))
/*     */         {
/*     */ 
/* 435 */           while (this.ordering.usedInContractSeq(i)) {
/* 436 */             i = this.text.previous();
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 442 */           int j = this.text.getIndex();
/* 443 */           while (this.text.getIndex() <= paramInt) {
/* 444 */             j = this.text.getIndex();
/* 445 */             next();
/*     */           }
/* 447 */           this.text.setIndexOnly(j);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 455 */     this.buffer = null;
/* 456 */     this.expIndex = 0;
/* 457 */     this.swapOrder = 0;
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
/*     */   public int getOffset()
/*     */   {
/* 476 */     return this.text != null ? this.text.getIndex() : 0;
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
/*     */   public int getMaxExpansion(int paramInt)
/*     */   {
/* 490 */     return this.ordering.getMaxExpansion(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String paramString)
/*     */   {
/* 501 */     this.buffer = null;
/* 502 */     this.swapOrder = 0;
/* 503 */     this.expIndex = 0;
/*     */     
/* 505 */     NormalizerBase.Mode localMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
/* 506 */     if (this.text == null) {
/* 507 */       this.text = new NormalizerBase(paramString, localMode);
/*     */     } else {
/* 509 */       this.text.setMode(localMode);
/* 510 */       this.text.setText(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(CharacterIterator paramCharacterIterator)
/*     */   {
/* 522 */     this.buffer = null;
/* 523 */     this.swapOrder = 0;
/* 524 */     this.expIndex = 0;
/*     */     
/* 526 */     NormalizerBase.Mode localMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
/* 527 */     if (this.text == null) {
/* 528 */       this.text = new NormalizerBase(paramCharacterIterator, localMode);
/*     */     } else {
/* 530 */       this.text.setMode(localMode);
/* 531 */       this.text.setText(paramCharacterIterator);
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
/*     */   private static final boolean isThaiPreVowel(int paramInt)
/*     */   {
/* 544 */     return (paramInt >= 3648) && (paramInt <= 3652);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final boolean isThaiBaseConsonant(int paramInt)
/*     */   {
/* 551 */     return (paramInt >= 3585) && (paramInt <= 3630);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final boolean isLaoPreVowel(int paramInt)
/*     */   {
/* 559 */     return (paramInt >= 3776) && (paramInt <= 3780);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final boolean isLaoBaseConsonant(int paramInt)
/*     */   {
/* 566 */     return (paramInt >= 3713) && (paramInt <= 3758);
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
/*     */   private int[] makeReorderedBuffer(int paramInt1, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean)
/*     */   {
/* 586 */     int i = this.ordering.getUnicodeOrder(paramInt1);
/* 587 */     if (i >= 2130706432) {
/* 588 */       i = paramBoolean ? nextContractChar(paramInt1) : prevContractChar(paramInt1);
/*     */     }
/*     */     
/* 591 */     int[] arrayOfInt2 = null;
/* 592 */     if (i >= 2113929216) {
/* 593 */       arrayOfInt2 = this.ordering.getExpandValueList(i);
/*     */     }
/*     */     int j;
/* 596 */     if (!paramBoolean) {
/* 597 */       j = i;
/* 598 */       i = paramInt2;
/* 599 */       paramInt2 = j;
/* 600 */       int[] arrayOfInt3 = arrayOfInt2;
/* 601 */       arrayOfInt2 = paramArrayOfInt;
/* 602 */       paramArrayOfInt = arrayOfInt3;
/*     */     }
/*     */     int[] arrayOfInt1;
/* 605 */     if ((arrayOfInt2 == null) && (paramArrayOfInt == null)) {
/* 606 */       arrayOfInt1 = new int[2];
/* 607 */       arrayOfInt1[0] = i;
/* 608 */       arrayOfInt1[1] = paramInt2;
/*     */     }
/*     */     else {
/* 611 */       j = arrayOfInt2 == null ? 1 : arrayOfInt2.length;
/* 612 */       int k = paramArrayOfInt == null ? 1 : paramArrayOfInt.length;
/* 613 */       arrayOfInt1 = new int[j + k];
/*     */       
/* 615 */       if (arrayOfInt2 == null) {
/* 616 */         arrayOfInt1[0] = i;
/*     */       }
/*     */       else {
/* 619 */         System.arraycopy(arrayOfInt2, 0, arrayOfInt1, 0, j);
/*     */       }
/*     */       
/* 622 */       if (paramArrayOfInt == null) {
/* 623 */         arrayOfInt1[j] = paramInt2;
/*     */       }
/*     */       else {
/* 626 */         System.arraycopy(paramArrayOfInt, 0, arrayOfInt1, j, k);
/*     */       }
/*     */     }
/*     */     
/* 630 */     return arrayOfInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final boolean isIgnorable(int paramInt)
/*     */   {
/* 639 */     return primaryOrder(paramInt) == 0;
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
/*     */   private int nextContractChar(int paramInt)
/*     */   {
/* 653 */     Vector localVector = this.ordering.getContractValues(paramInt);
/* 654 */     EntryPair localEntryPair = (EntryPair)localVector.firstElement();
/* 655 */     int i = localEntryPair.value;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 660 */     localEntryPair = (EntryPair)localVector.lastElement();
/* 661 */     int j = localEntryPair.entryName.length();
/*     */     
/*     */ 
/*     */ 
/* 665 */     NormalizerBase localNormalizerBase = (NormalizerBase)this.text.clone();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 670 */     localNormalizerBase.previous();
/* 671 */     this.key.setLength(0);
/* 672 */     int k = localNormalizerBase.next();
/* 673 */     while ((j > 0) && (k != -1)) {
/* 674 */       if (Character.isSupplementaryCodePoint(k)) {
/* 675 */         this.key.append(Character.toChars(k));
/* 676 */         j -= 2;
/*     */       } else {
/* 678 */         this.key.append((char)k);
/* 679 */         j--;
/*     */       }
/* 681 */       k = localNormalizerBase.next();
/*     */     }
/* 683 */     String str = this.key.toString();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 690 */     j = 1;
/* 691 */     for (int m = localVector.size() - 1; m > 0; m--) {
/* 692 */       localEntryPair = (EntryPair)localVector.elementAt(m);
/* 693 */       if (localEntryPair.fwd)
/*     */       {
/*     */ 
/* 696 */         if ((str.startsWith(localEntryPair.entryName)) && (localEntryPair.entryName.length() > j))
/*     */         {
/* 698 */           j = localEntryPair.entryName.length();
/* 699 */           i = localEntryPair.value;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 707 */     while (j > 1) {
/* 708 */       k = this.text.next();
/* 709 */       j -= Character.charCount(k);
/*     */     }
/* 711 */     return i;
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
/*     */   private int prevContractChar(int paramInt)
/*     */   {
/* 729 */     Vector localVector = this.ordering.getContractValues(paramInt);
/* 730 */     EntryPair localEntryPair = (EntryPair)localVector.firstElement();
/* 731 */     int i = localEntryPair.value;
/*     */     
/* 733 */     localEntryPair = (EntryPair)localVector.lastElement();
/* 734 */     int j = localEntryPair.entryName.length();
/*     */     
/* 736 */     NormalizerBase localNormalizerBase = (NormalizerBase)this.text.clone();
/*     */     
/* 738 */     localNormalizerBase.next();
/* 739 */     this.key.setLength(0);
/* 740 */     int k = localNormalizerBase.previous();
/* 741 */     while ((j > 0) && (k != -1)) {
/* 742 */       if (Character.isSupplementaryCodePoint(k)) {
/* 743 */         this.key.append(Character.toChars(k));
/* 744 */         j -= 2;
/*     */       } else {
/* 746 */         this.key.append((char)k);
/* 747 */         j--;
/*     */       }
/* 749 */       k = localNormalizerBase.previous();
/*     */     }
/* 751 */     String str = this.key.toString();
/*     */     
/* 753 */     j = 1;
/* 754 */     for (int m = localVector.size() - 1; m > 0; m--) {
/* 755 */       localEntryPair = (EntryPair)localVector.elementAt(m);
/* 756 */       if (!localEntryPair.fwd)
/*     */       {
/*     */ 
/* 759 */         if ((str.startsWith(localEntryPair.entryName)) && (localEntryPair.entryName.length() > j))
/*     */         {
/* 761 */           j = localEntryPair.entryName.length();
/* 762 */           i = localEntryPair.value;
/*     */         }
/*     */       }
/*     */     }
/* 766 */     while (j > 1) {
/* 767 */       k = this.text.previous();
/* 768 */       j -= Character.charCount(k);
/*     */     }
/* 770 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 775 */   private NormalizerBase text = null;
/* 776 */   private int[] buffer = null;
/* 777 */   private int expIndex = 0;
/* 778 */   private StringBuffer key = new StringBuffer(5);
/* 779 */   private int swapOrder = 0;
/*     */   private RBCollationTables ordering;
/*     */   private RuleBasedCollator owner;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/CollationElementIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */