/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RuleBasedCollator
/*     */   extends Collator
/*     */ {
/*     */   static final int CHARINDEX = 1879048192;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int EXPANDCHARINDEX = 2113929216;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int CONTRACTCHARINDEX = 2130706432;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int UNMAPPED = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int COLLATIONKEYOFFSET = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RuleBasedCollator(String paramString)
/*     */     throws ParseException
/*     */   {
/* 281 */     this(paramString, 1);
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
/*     */   RuleBasedCollator(String paramString, int paramInt)
/*     */     throws ParseException
/*     */   {
/* 298 */     setStrength(2);
/* 299 */     setDecomposition(paramInt);
/* 300 */     this.tables = new RBCollationTables(paramString, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private RuleBasedCollator(RuleBasedCollator paramRuleBasedCollator)
/*     */   {
/* 307 */     setStrength(paramRuleBasedCollator.getStrength());
/* 308 */     setDecomposition(paramRuleBasedCollator.getDecomposition());
/* 309 */     this.tables = paramRuleBasedCollator.tables;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRules()
/*     */   {
/* 319 */     return this.tables.getRules();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CollationElementIterator getCollationElementIterator(String paramString)
/*     */   {
/* 330 */     return new CollationElementIterator(paramString, this);
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
/*     */   public CollationElementIterator getCollationElementIterator(CharacterIterator paramCharacterIterator)
/*     */   {
/* 343 */     return new CollationElementIterator(paramCharacterIterator, this);
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
/*     */   public synchronized int compare(String paramString1, String paramString2)
/*     */   {
/* 356 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 357 */       throw new NullPointerException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 379 */     int i = 0;
/*     */     
/* 381 */     if (this.sourceCursor == null) {
/* 382 */       this.sourceCursor = getCollationElementIterator(paramString1);
/*     */     } else {
/* 384 */       this.sourceCursor.setText(paramString1);
/*     */     }
/* 386 */     if (this.targetCursor == null) {
/* 387 */       this.targetCursor = getCollationElementIterator(paramString2);
/*     */     } else {
/* 389 */       this.targetCursor.setText(paramString2);
/*     */     }
/*     */     
/* 392 */     int j = 0;int k = 0;
/*     */     
/* 394 */     int m = getStrength() >= 1 ? 1 : 0;
/* 395 */     int n = m;
/* 396 */     int i1 = getStrength() >= 2 ? 1 : 0;
/*     */     
/* 398 */     int i2 = 1;int i3 = 1;
/*     */     
/*     */     int i4;
/*     */     for (;;)
/*     */     {
/* 403 */       if (i2 != 0) j = this.sourceCursor.next(); else i2 = 1;
/* 404 */       if (i3 != 0) k = this.targetCursor.next(); else { i3 = 1;
/*     */       }
/*     */       
/* 407 */       if ((j == -1) || (k == -1)) {
/*     */         break;
/*     */       }
/*     */       
/* 411 */       i4 = CollationElementIterator.primaryOrder(j);
/* 412 */       int i5 = CollationElementIterator.primaryOrder(k);
/*     */       
/*     */ 
/* 415 */       if (j == k) {
/* 416 */         if ((this.tables.isFrenchSec()) && (i4 != 0) && 
/* 417 */           (n == 0))
/*     */         {
/*     */ 
/* 420 */           n = m;
/*     */           
/*     */ 
/* 423 */           i1 = 0;
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/* 430 */       else if (i4 != i5)
/*     */       {
/* 432 */         if (j == 0)
/*     */         {
/*     */ 
/* 435 */           i3 = 0;
/*     */ 
/*     */         }
/* 438 */         else if (k == 0) {
/* 439 */           i2 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 446 */         else if (i4 == 0)
/*     */         {
/*     */ 
/*     */ 
/* 450 */           if (n != 0) {
/* 451 */             i = 1;
/* 452 */             n = 0;
/*     */           }
/*     */           
/* 455 */           i3 = 0;
/*     */         }
/* 457 */         else if (i5 == 0)
/*     */         {
/*     */ 
/* 460 */           if (n != 0) {
/* 461 */             i = -1;
/* 462 */             n = 0;
/*     */           }
/*     */           
/* 465 */           i2 = 0;
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 470 */           if (i4 < i5) {
/* 471 */             return -1;
/*     */           }
/* 473 */           return 1;
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/* 481 */       else if (n != 0)
/*     */       {
/* 483 */         int i6 = CollationElementIterator.secondaryOrder(j);
/* 484 */         int i7 = CollationElementIterator.secondaryOrder(k);
/* 485 */         if (i6 != i7)
/*     */         {
/* 487 */           i = i6 < i7 ? -1 : 1;
/*     */           
/* 489 */           n = 0;
/*     */ 
/*     */ 
/*     */         }
/* 493 */         else if (i1 != 0)
/*     */         {
/* 495 */           int i8 = CollationElementIterator.tertiaryOrder(j);
/* 496 */           int i9 = CollationElementIterator.tertiaryOrder(k);
/* 497 */           if (i8 != i9)
/*     */           {
/* 499 */             i = i8 < i9 ? -1 : 1;
/*     */             
/* 501 */             i1 = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 510 */     if (j != -1)
/*     */     {
/*     */       do
/*     */       {
/*     */ 
/* 515 */         if (CollationElementIterator.primaryOrder(j) != 0)
/*     */         {
/*     */ 
/* 518 */           return 1;
/*     */         }
/* 520 */         if (CollationElementIterator.secondaryOrder(j) != 0)
/*     */         {
/* 522 */           if (n != 0) {
/* 523 */             i = 1;
/* 524 */             n = 0;
/*     */           }
/*     */         }
/* 527 */       } while ((j = this.sourceCursor.next()) != -1);
/*     */     }
/* 529 */     else if (k != -1) {
/*     */       do
/*     */       {
/* 532 */         if (CollationElementIterator.primaryOrder(k) != 0)
/*     */         {
/*     */ 
/* 535 */           return -1; }
/* 536 */         if (CollationElementIterator.secondaryOrder(k) != 0)
/*     */         {
/* 538 */           if (n != 0) {
/* 539 */             i = -1;
/* 540 */             n = 0;
/*     */           }
/*     */         }
/* 543 */       } while ((k = this.targetCursor.next()) != -1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 548 */     if ((i == 0) && (getStrength() == 3)) {
/* 549 */       i4 = getDecomposition();
/*     */       Normalizer.Form localForm;
/* 551 */       if (i4 == 1) {
/* 552 */         localForm = Normalizer.Form.NFD;
/* 553 */       } else if (i4 == 2) {
/* 554 */         localForm = Normalizer.Form.NFKD;
/*     */       } else {
/* 556 */         return paramString1.compareTo(paramString2);
/*     */       }
/*     */       
/* 559 */       String str1 = Normalizer.normalize(paramString1, localForm);
/* 560 */       String str2 = Normalizer.normalize(paramString2, localForm);
/* 561 */       return str1.compareTo(str2);
/*     */     }
/* 563 */     return i;
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
/*     */   public synchronized CollationKey getCollationKey(String paramString)
/*     */   {
/* 603 */     if (paramString == null) {
/* 604 */       return null;
/*     */     }
/* 606 */     if (this.primResult == null) {
/* 607 */       this.primResult = new StringBuffer();
/* 608 */       this.secResult = new StringBuffer();
/* 609 */       this.terResult = new StringBuffer();
/*     */     } else {
/* 611 */       this.primResult.setLength(0);
/* 612 */       this.secResult.setLength(0);
/* 613 */       this.terResult.setLength(0);
/*     */     }
/* 615 */     int i = 0;
/* 616 */     int j = getStrength() >= 1 ? 1 : 0;
/* 617 */     int k = getStrength() >= 2 ? 1 : 0;
/* 618 */     int m = -1;
/* 619 */     int n = -1;
/* 620 */     int i1 = 0;
/*     */     
/* 622 */     if (this.sourceCursor == null) {
/* 623 */       this.sourceCursor = getCollationElementIterator(paramString);
/*     */     } else {
/* 625 */       this.sourceCursor.setText(paramString);
/*     */     }
/*     */     
/*     */ 
/* 629 */     while ((i = this.sourceCursor.next()) != -1)
/*     */     {
/*     */ 
/* 632 */       m = CollationElementIterator.secondaryOrder(i);
/* 633 */       n = CollationElementIterator.tertiaryOrder(i);
/* 634 */       if (!CollationElementIterator.isIgnorable(i))
/*     */       {
/* 636 */         this.primResult.append((char)(CollationElementIterator.primaryOrder(i) + 1));
/*     */         
/*     */ 
/* 639 */         if (j != 0)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 644 */           if ((this.tables.isFrenchSec()) && (i1 < this.secResult.length()))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 650 */             RBCollationTables.reverse(this.secResult, i1, this.secResult.length());
/*     */           }
/*     */           
/*     */ 
/* 654 */           this.secResult.append((char)(m + 1));
/* 655 */           i1 = this.secResult.length();
/*     */         }
/* 657 */         if (k != 0) {
/* 658 */           this.terResult.append((char)(n + 1));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 663 */         if ((j != 0) && (m != 0))
/* 664 */           this.secResult.append(
/* 665 */             (char)(m + this.tables.getMaxSecOrder() + 1));
/* 666 */         if ((k != 0) && (n != 0))
/* 667 */           this.terResult.append(
/* 668 */             (char)(n + this.tables.getMaxTerOrder() + 1));
/*     */       }
/*     */     }
/* 671 */     if (this.tables.isFrenchSec())
/*     */     {
/* 673 */       if (i1 < this.secResult.length())
/*     */       {
/*     */ 
/* 676 */         RBCollationTables.reverse(this.secResult, i1, this.secResult.length());
/*     */       }
/*     */       
/* 679 */       RBCollationTables.reverse(this.secResult, 0, this.secResult.length());
/*     */     }
/* 681 */     this.primResult.append('\000');
/* 682 */     this.secResult.append('\000');
/* 683 */     this.secResult.append(this.terResult.toString());
/* 684 */     this.primResult.append(this.secResult.toString());
/*     */     
/* 686 */     if (getStrength() == 3) {
/* 687 */       this.primResult.append('\000');
/* 688 */       int i2 = getDecomposition();
/* 689 */       if (i2 == 1) {
/* 690 */         this.primResult.append(Normalizer.normalize(paramString, Normalizer.Form.NFD));
/* 691 */       } else if (i2 == 2) {
/* 692 */         this.primResult.append(Normalizer.normalize(paramString, Normalizer.Form.NFKD));
/*     */       } else {
/* 694 */         this.primResult.append(paramString);
/*     */       }
/*     */     }
/* 697 */     return new RuleBasedCollationKey(paramString, this.primResult.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 707 */     if (getClass() == RuleBasedCollator.class) {
/* 708 */       return new RuleBasedCollator(this);
/*     */     }
/*     */     
/* 711 */     RuleBasedCollator localRuleBasedCollator = (RuleBasedCollator)super.clone();
/* 712 */     localRuleBasedCollator.primResult = null;
/* 713 */     localRuleBasedCollator.secResult = null;
/* 714 */     localRuleBasedCollator.terResult = null;
/* 715 */     localRuleBasedCollator.sourceCursor = null;
/* 716 */     localRuleBasedCollator.targetCursor = null;
/* 717 */     return localRuleBasedCollator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 728 */     if (paramObject == null) return false;
/* 729 */     if (!super.equals(paramObject)) return false;
/* 730 */     RuleBasedCollator localRuleBasedCollator = (RuleBasedCollator)paramObject;
/*     */     
/* 732 */     return getRules().equals(localRuleBasedCollator.getRules());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 739 */     return getRules().hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   RBCollationTables getTables()
/*     */   {
/* 746 */     return this.tables;
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
/* 760 */   private RBCollationTables tables = null;
/*     */   
/*     */ 
/*     */ 
/* 764 */   private StringBuffer primResult = null;
/* 765 */   private StringBuffer secResult = null;
/* 766 */   private StringBuffer terResult = null;
/* 767 */   private CollationElementIterator sourceCursor = null;
/* 768 */   private CollationElementIterator targetCursor = null;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/RuleBasedCollator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */