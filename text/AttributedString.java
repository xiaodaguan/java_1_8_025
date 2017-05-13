/*      */ package java.text;
/*      */ 
/*      */ import java.util.AbstractMap;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AttributedString
/*      */ {
/*      */   private static final int ARRAY_SIZE_INCREMENT = 10;
/*      */   String text;
/*      */   int runArraySize;
/*      */   int runCount;
/*      */   int[] runStarts;
/*      */   Vector<AttributedCharacterIterator.Attribute>[] runAttributes;
/*      */   Vector<Object>[] runAttributeValues;
/*      */   
/*      */   AttributedString(AttributedCharacterIterator[] paramArrayOfAttributedCharacterIterator)
/*      */   {
/*   76 */     if (paramArrayOfAttributedCharacterIterator == null) {
/*   77 */       throw new NullPointerException("Iterators must not be null");
/*      */     }
/*   79 */     if (paramArrayOfAttributedCharacterIterator.length == 0) {
/*   80 */       this.text = "";
/*      */     }
/*      */     else
/*      */     {
/*   84 */       StringBuffer localStringBuffer = new StringBuffer();
/*   85 */       for (int i = 0; i < paramArrayOfAttributedCharacterIterator.length; i++) {
/*   86 */         appendContents(localStringBuffer, paramArrayOfAttributedCharacterIterator[i]);
/*      */       }
/*      */       
/*   89 */       this.text = localStringBuffer.toString();
/*      */       
/*   91 */       if (this.text.length() > 0)
/*      */       {
/*      */ 
/*   94 */         i = 0;
/*   95 */         Object localObject = null;
/*      */         
/*   97 */         for (int j = 0; j < paramArrayOfAttributedCharacterIterator.length; j++) {
/*   98 */           AttributedCharacterIterator localAttributedCharacterIterator = paramArrayOfAttributedCharacterIterator[j];
/*   99 */           int k = localAttributedCharacterIterator.getBeginIndex();
/*  100 */           int m = localAttributedCharacterIterator.getEndIndex();
/*  101 */           int n = k;
/*      */           
/*  103 */           while (n < m) {
/*  104 */             localAttributedCharacterIterator.setIndex(n);
/*      */             
/*  106 */             Map localMap = localAttributedCharacterIterator.getAttributes();
/*      */             
/*  108 */             if (mapsDiffer((Map)localObject, localMap)) {
/*  109 */               setAttributes(localMap, n - k + i);
/*      */             }
/*  111 */             localObject = localMap;
/*  112 */             n = localAttributedCharacterIterator.getRunLimit();
/*      */           }
/*  114 */           i += m - k;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AttributedString(String paramString)
/*      */   {
/*  126 */     if (paramString == null) {
/*  127 */       throw new NullPointerException();
/*      */     }
/*  129 */     this.text = paramString;
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
/*      */   public AttributedString(String paramString, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*      */   {
/*  145 */     if ((paramString == null) || (paramMap == null)) {
/*  146 */       throw new NullPointerException();
/*      */     }
/*  148 */     this.text = paramString;
/*      */     
/*  150 */     if (paramString.length() == 0) {
/*  151 */       if (paramMap.isEmpty())
/*  152 */         return;
/*  153 */       throw new IllegalArgumentException("Can't add attribute to 0-length text");
/*      */     }
/*      */     
/*  156 */     int i = paramMap.size();
/*  157 */     if (i > 0) {
/*  158 */       createRunAttributeDataVectors();
/*  159 */       Vector localVector1 = new Vector(i);
/*  160 */       Vector localVector2 = new Vector(i);
/*  161 */       this.runAttributes[0] = localVector1;
/*  162 */       this.runAttributeValues[0] = localVector2;
/*      */       
/*  164 */       Iterator localIterator = paramMap.entrySet().iterator();
/*  165 */       while (localIterator.hasNext()) {
/*  166 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  167 */         localVector1.addElement(localEntry.getKey());
/*  168 */         localVector2.addElement(localEntry.getValue());
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
/*      */   public AttributedString(AttributedCharacterIterator paramAttributedCharacterIterator)
/*      */   {
/*  183 */     this(paramAttributedCharacterIterator, paramAttributedCharacterIterator.getBeginIndex(), paramAttributedCharacterIterator.getEndIndex(), null);
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
/*      */   public AttributedString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2)
/*      */   {
/*  206 */     this(paramAttributedCharacterIterator, paramInt1, paramInt2, null);
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
/*      */   public AttributedString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2, AttributedCharacterIterator.Attribute[] paramArrayOfAttribute)
/*      */   {
/*  235 */     if (paramAttributedCharacterIterator == null) {
/*  236 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/*  240 */     int i = paramAttributedCharacterIterator.getBeginIndex();
/*  241 */     int j = paramAttributedCharacterIterator.getEndIndex();
/*  242 */     if ((paramInt1 < i) || (paramInt2 > j) || (paramInt1 > paramInt2)) {
/*  243 */       throw new IllegalArgumentException("Invalid substring range");
/*      */     }
/*      */     
/*  246 */     StringBuffer localStringBuffer = new StringBuffer();
/*  247 */     paramAttributedCharacterIterator.setIndex(paramInt1);
/*  248 */     for (char c = paramAttributedCharacterIterator.current(); paramAttributedCharacterIterator.getIndex() < paramInt2; c = paramAttributedCharacterIterator.next())
/*  249 */       localStringBuffer.append(c);
/*  250 */     this.text = localStringBuffer.toString();
/*      */     
/*  252 */     if (paramInt1 == paramInt2) {
/*  253 */       return;
/*      */     }
/*      */     
/*  256 */     HashSet localHashSet = new HashSet();
/*  257 */     if (paramArrayOfAttribute == null) {
/*  258 */       localHashSet.addAll(paramAttributedCharacterIterator.getAllAttributeKeys());
/*      */     } else {
/*  260 */       for (int k = 0; k < paramArrayOfAttribute.length; k++)
/*  261 */         localHashSet.add(paramArrayOfAttribute[k]);
/*  262 */       localHashSet.retainAll(paramAttributedCharacterIterator.getAllAttributeKeys());
/*      */     }
/*  264 */     if (localHashSet.isEmpty()) {
/*  265 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  270 */     Iterator localIterator = localHashSet.iterator();
/*  271 */     while (localIterator.hasNext()) {
/*  272 */       AttributedCharacterIterator.Attribute localAttribute = (AttributedCharacterIterator.Attribute)localIterator.next();
/*  273 */       paramAttributedCharacterIterator.setIndex(i);
/*  274 */       while (paramAttributedCharacterIterator.getIndex() < paramInt2) {
/*  275 */         int m = paramAttributedCharacterIterator.getRunStart(localAttribute);
/*  276 */         int n = paramAttributedCharacterIterator.getRunLimit(localAttribute);
/*  277 */         Object localObject = paramAttributedCharacterIterator.getAttribute(localAttribute);
/*      */         
/*  279 */         if (localObject != null) {
/*  280 */           if ((localObject instanceof Annotation)) {
/*  281 */             if ((m >= paramInt1) && (n <= paramInt2)) {
/*  282 */               addAttribute(localAttribute, localObject, m - paramInt1, n - paramInt1);
/*      */             }
/*  284 */             else if (n > paramInt2) {
/*      */               break;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  290 */             if (m >= paramInt2)
/*      */               break;
/*  292 */             if (n > paramInt1)
/*      */             {
/*  294 */               if (m < paramInt1)
/*  295 */                 m = paramInt1;
/*  296 */               if (n > paramInt2)
/*  297 */                 n = paramInt2;
/*  298 */               if (m != n) {
/*  299 */                 addAttribute(localAttribute, localObject, m - paramInt1, n - paramInt1);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  304 */         paramAttributedCharacterIterator.setIndex(n);
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
/*      */   public void addAttribute(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject)
/*      */   {
/*  319 */     if (paramAttribute == null) {
/*  320 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  323 */     int i = length();
/*  324 */     if (i == 0) {
/*  325 */       throw new IllegalArgumentException("Can't add attribute to 0-length text");
/*      */     }
/*      */     
/*  328 */     addAttributeImpl(paramAttribute, paramObject, 0, i);
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
/*      */   public void addAttribute(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject, int paramInt1, int paramInt2)
/*      */   {
/*  345 */     if (paramAttribute == null) {
/*  346 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  349 */     if ((paramInt1 < 0) || (paramInt2 > length()) || (paramInt1 >= paramInt2)) {
/*  350 */       throw new IllegalArgumentException("Invalid substring range");
/*      */     }
/*      */     
/*  353 */     addAttributeImpl(paramAttribute, paramObject, paramInt1, paramInt2);
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
/*      */   public void addAttributes(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, int paramInt1, int paramInt2)
/*      */   {
/*  372 */     if (paramMap == null) {
/*  373 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  376 */     if ((paramInt1 < 0) || (paramInt2 > length()) || (paramInt1 > paramInt2)) {
/*  377 */       throw new IllegalArgumentException("Invalid substring range");
/*      */     }
/*  379 */     if (paramInt1 == paramInt2) {
/*  380 */       if (paramMap.isEmpty())
/*  381 */         return;
/*  382 */       throw new IllegalArgumentException("Can't add attribute to 0-length text");
/*      */     }
/*      */     
/*      */ 
/*  386 */     if (this.runCount == 0) {
/*  387 */       createRunAttributeDataVectors();
/*      */     }
/*      */     
/*      */ 
/*  391 */     int i = ensureRunBreak(paramInt1);
/*  392 */     int j = ensureRunBreak(paramInt2);
/*      */     
/*      */ 
/*  395 */     Iterator localIterator = paramMap.entrySet().iterator();
/*  396 */     while (localIterator.hasNext()) {
/*  397 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  398 */       addAttributeRunData((AttributedCharacterIterator.Attribute)localEntry.getKey(), localEntry.getValue(), i, j);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private synchronized void addAttributeImpl(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject, int paramInt1, int paramInt2)
/*      */   {
/*  406 */     if (this.runCount == 0) {
/*  407 */       createRunAttributeDataVectors();
/*      */     }
/*      */     
/*      */ 
/*  411 */     int i = ensureRunBreak(paramInt1);
/*  412 */     int j = ensureRunBreak(paramInt2);
/*      */     
/*  414 */     addAttributeRunData(paramAttribute, paramObject, i, j);
/*      */   }
/*      */   
/*      */   private final void createRunAttributeDataVectors()
/*      */   {
/*  419 */     int[] arrayOfInt = new int[10];
/*      */     
/*      */ 
/*  422 */     Vector[] arrayOfVector1 = (Vector[])new Vector[10];
/*      */     
/*      */ 
/*  425 */     Vector[] arrayOfVector2 = (Vector[])new Vector[10];
/*      */     
/*  427 */     this.runStarts = arrayOfInt;
/*  428 */     this.runAttributes = arrayOfVector1;
/*  429 */     this.runAttributeValues = arrayOfVector2;
/*  430 */     this.runArraySize = 10;
/*  431 */     this.runCount = 1;
/*      */   }
/*      */   
/*      */   private final int ensureRunBreak(int paramInt)
/*      */   {
/*  436 */     return ensureRunBreak(paramInt, true);
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
/*      */   private final int ensureRunBreak(int paramInt, boolean paramBoolean)
/*      */   {
/*  451 */     if (paramInt == length()) {
/*  452 */       return this.runCount;
/*      */     }
/*      */     
/*      */ 
/*  456 */     int i = 0;
/*  457 */     while ((i < this.runCount) && (this.runStarts[i] < paramInt)) {
/*  458 */       i++;
/*      */     }
/*      */     
/*      */ 
/*  462 */     if ((i < this.runCount) && (this.runStarts[i] == paramInt)) {
/*  463 */       return i;
/*      */     }
/*      */     
/*      */     Object localObject2;
/*      */     Object localObject3;
/*  468 */     if (this.runCount == this.runArraySize) {
/*  469 */       int j = this.runArraySize + 10;
/*  470 */       localObject1 = new int[j];
/*      */       
/*      */ 
/*  473 */       localObject2 = (Vector[])new Vector[j];
/*      */       
/*      */ 
/*  476 */       localObject3 = (Vector[])new Vector[j];
/*      */       
/*  478 */       for (int m = 0; m < this.runArraySize; m++) {
/*  479 */         localObject1[m] = this.runStarts[m];
/*  480 */         localObject2[m] = this.runAttributes[m];
/*  481 */         localObject3[m] = this.runAttributeValues[m];
/*      */       }
/*  483 */       this.runStarts = ((int[])localObject1);
/*  484 */       this.runAttributes = ((Vector[])localObject2);
/*  485 */       this.runAttributeValues = ((Vector[])localObject3);
/*  486 */       this.runArraySize = j;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  491 */     Vector localVector = null;
/*  492 */     Object localObject1 = null;
/*      */     
/*  494 */     if (paramBoolean) {
/*  495 */       localObject2 = this.runAttributes[(i - 1)];
/*  496 */       localObject3 = this.runAttributeValues[(i - 1)];
/*  497 */       if (localObject2 != null) {
/*  498 */         localVector = new Vector((Collection)localObject2);
/*      */       }
/*  500 */       if (localObject3 != null) {
/*  501 */         localObject1 = new Vector((Collection)localObject3);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  506 */     this.runCount += 1;
/*  507 */     for (int k = this.runCount - 1; k > i; k--) {
/*  508 */       this.runStarts[k] = this.runStarts[(k - 1)];
/*  509 */       this.runAttributes[k] = this.runAttributes[(k - 1)];
/*  510 */       this.runAttributeValues[k] = this.runAttributeValues[(k - 1)];
/*      */     }
/*  512 */     this.runStarts[i] = paramInt;
/*  513 */     this.runAttributes[i] = localVector;
/*  514 */     this.runAttributeValues[i] = localObject1;
/*      */     
/*  516 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void addAttributeRunData(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject, int paramInt1, int paramInt2)
/*      */   {
/*  523 */     for (int i = paramInt1; i < paramInt2; i++) {
/*  524 */       int j = -1;
/*  525 */       if (this.runAttributes[i] == null) {
/*  526 */         Vector localVector1 = new Vector();
/*  527 */         Vector localVector2 = new Vector();
/*  528 */         this.runAttributes[i] = localVector1;
/*  529 */         this.runAttributeValues[i] = localVector2;
/*      */       }
/*      */       else {
/*  532 */         j = this.runAttributes[i].indexOf(paramAttribute);
/*      */       }
/*      */       
/*  535 */       if (j == -1)
/*      */       {
/*  537 */         int k = this.runAttributes[i].size();
/*  538 */         this.runAttributes[i].addElement(paramAttribute);
/*      */         try {
/*  540 */           this.runAttributeValues[i].addElement(paramObject);
/*      */         }
/*      */         catch (Exception localException) {
/*  543 */           this.runAttributes[i].setSize(k);
/*  544 */           this.runAttributeValues[i].setSize(k);
/*      */         }
/*      */       }
/*      */       else {
/*  548 */         this.runAttributeValues[i].set(j, paramObject);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AttributedCharacterIterator getIterator()
/*      */   {
/*  560 */     return getIterator(null, 0, length());
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
/*      */   public AttributedCharacterIterator getIterator(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute)
/*      */   {
/*  575 */     return getIterator(paramArrayOfAttribute, 0, length());
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
/*      */   public AttributedCharacterIterator getIterator(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute, int paramInt1, int paramInt2)
/*      */   {
/*  595 */     return new AttributedStringIterator(paramArrayOfAttribute, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int length()
/*      */   {
/*  604 */     return this.text.length();
/*      */   }
/*      */   
/*      */   private char charAt(int paramInt) {
/*  608 */     return this.text.charAt(paramInt);
/*      */   }
/*      */   
/*      */   private synchronized Object getAttribute(AttributedCharacterIterator.Attribute paramAttribute, int paramInt) {
/*  612 */     Vector localVector1 = this.runAttributes[paramInt];
/*  613 */     Vector localVector2 = this.runAttributeValues[paramInt];
/*  614 */     if (localVector1 == null) {
/*  615 */       return null;
/*      */     }
/*  617 */     int i = localVector1.indexOf(paramAttribute);
/*  618 */     if (i != -1) {
/*  619 */       return localVector2.elementAt(i);
/*      */     }
/*      */     
/*  622 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private Object getAttributeCheckRange(AttributedCharacterIterator.Attribute paramAttribute, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  628 */     Object localObject = getAttribute(paramAttribute, paramInt1);
/*  629 */     if ((localObject instanceof Annotation)) {
/*      */       int j;
/*  631 */       if (paramInt2 > 0) {
/*  632 */         i = paramInt1;
/*  633 */         j = this.runStarts[i];
/*  634 */         while ((j >= paramInt2) && 
/*  635 */           (valuesMatch(localObject, getAttribute(paramAttribute, i - 1)))) {
/*  636 */           i--;
/*  637 */           j = this.runStarts[i];
/*      */         }
/*  639 */         if (j < paramInt2)
/*      */         {
/*  641 */           return null;
/*      */         }
/*      */       }
/*  644 */       int i = length();
/*  645 */       if (paramInt3 < i) {
/*  646 */         j = paramInt1;
/*  647 */         int k = j < this.runCount - 1 ? this.runStarts[(j + 1)] : i;
/*  648 */         while ((k <= paramInt3) && 
/*  649 */           (valuesMatch(localObject, getAttribute(paramAttribute, j + 1)))) {
/*  650 */           j++;
/*  651 */           k = j < this.runCount - 1 ? this.runStarts[(j + 1)] : i;
/*      */         }
/*  653 */         if (k > paramInt3)
/*      */         {
/*  655 */           return null;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  661 */     return localObject;
/*      */   }
/*      */   
/*      */   private boolean attributeValuesMatch(Set<? extends AttributedCharacterIterator.Attribute> paramSet, int paramInt1, int paramInt2)
/*      */   {
/*  666 */     Iterator localIterator = paramSet.iterator();
/*  667 */     while (localIterator.hasNext()) {
/*  668 */       AttributedCharacterIterator.Attribute localAttribute = (AttributedCharacterIterator.Attribute)localIterator.next();
/*  669 */       if (!valuesMatch(getAttribute(localAttribute, paramInt1), getAttribute(localAttribute, paramInt2))) {
/*  670 */         return false;
/*      */       }
/*      */     }
/*  673 */     return true;
/*      */   }
/*      */   
/*      */   private static final boolean valuesMatch(Object paramObject1, Object paramObject2)
/*      */   {
/*  678 */     if (paramObject1 == null) {
/*  679 */       return paramObject2 == null;
/*      */     }
/*  681 */     return paramObject1.equals(paramObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void appendContents(StringBuffer paramStringBuffer, CharacterIterator paramCharacterIterator)
/*      */   {
/*  691 */     int i = paramCharacterIterator.getBeginIndex();
/*  692 */     int j = paramCharacterIterator.getEndIndex();
/*      */     
/*  694 */     while (i < j) {
/*  695 */       paramCharacterIterator.setIndex(i++);
/*  696 */       paramStringBuffer.append(paramCharacterIterator.current());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setAttributes(Map<AttributedCharacterIterator.Attribute, Object> paramMap, int paramInt)
/*      */   {
/*  706 */     if (this.runCount == 0) {
/*  707 */       createRunAttributeDataVectors();
/*      */     }
/*      */     
/*  710 */     int i = ensureRunBreak(paramInt, false);
/*      */     
/*      */     int j;
/*  713 */     if ((paramMap != null) && ((j = paramMap.size()) > 0)) {
/*  714 */       Vector localVector1 = new Vector(j);
/*  715 */       Vector localVector2 = new Vector(j);
/*  716 */       Iterator localIterator = paramMap.entrySet().iterator();
/*      */       
/*  718 */       while (localIterator.hasNext()) {
/*  719 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/*      */         
/*  721 */         localVector1.add(localEntry.getKey());
/*  722 */         localVector2.add(localEntry.getValue());
/*      */       }
/*  724 */       this.runAttributes[i] = localVector1;
/*  725 */       this.runAttributeValues[i] = localVector2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static <K, V> boolean mapsDiffer(Map<K, V> paramMap1, Map<K, V> paramMap2)
/*      */   {
/*  733 */     if (paramMap1 == null) {
/*  734 */       return (paramMap2 != null) && (paramMap2.size() > 0);
/*      */     }
/*  736 */     return !paramMap1.equals(paramMap2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final class AttributedStringIterator
/*      */     implements AttributedCharacterIterator
/*      */   {
/*      */     private int beginIndex;
/*      */     
/*      */ 
/*      */     private int endIndex;
/*      */     
/*      */ 
/*      */     private AttributedCharacterIterator.Attribute[] relevantAttributes;
/*      */     
/*      */ 
/*      */     private int currentIndex;
/*      */     
/*      */ 
/*      */     private int currentRunIndex;
/*      */     
/*      */ 
/*      */     private int currentRunStart;
/*      */     
/*      */ 
/*      */     private int currentRunLimit;
/*      */     
/*      */ 
/*      */     AttributedStringIterator(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute, int paramInt1, int paramInt2)
/*      */     {
/*  767 */       if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 > AttributedString.this.length())) {
/*  768 */         throw new IllegalArgumentException("Invalid substring range");
/*      */       }
/*      */       
/*  771 */       this.beginIndex = paramInt1;
/*  772 */       this.endIndex = paramInt2;
/*  773 */       this.currentIndex = paramInt1;
/*  774 */       updateRunInfo();
/*  775 */       if (paramArrayOfAttribute != null) {
/*  776 */         this.relevantAttributes = ((AttributedCharacterIterator.Attribute[])paramArrayOfAttribute.clone());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*  783 */       if (this == paramObject) {
/*  784 */         return true;
/*      */       }
/*  786 */       if (!(paramObject instanceof AttributedStringIterator)) {
/*  787 */         return false;
/*      */       }
/*      */       
/*  790 */       AttributedStringIterator localAttributedStringIterator = (AttributedStringIterator)paramObject;
/*      */       
/*  792 */       if (AttributedString.this != localAttributedStringIterator.getString())
/*  793 */         return false;
/*  794 */       if ((this.currentIndex != localAttributedStringIterator.currentIndex) || (this.beginIndex != localAttributedStringIterator.beginIndex) || (this.endIndex != localAttributedStringIterator.endIndex))
/*  795 */         return false;
/*  796 */       return true;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  800 */       return AttributedString.this.text.hashCode() ^ this.currentIndex ^ this.beginIndex ^ this.endIndex;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*      */       try {
/*  805 */         return (AttributedStringIterator)super.clone();
/*      */       }
/*      */       catch (CloneNotSupportedException localCloneNotSupportedException)
/*      */       {
/*  809 */         throw new InternalError(localCloneNotSupportedException);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     public char first()
/*      */     {
/*  816 */       return internalSetIndex(this.beginIndex);
/*      */     }
/*      */     
/*      */     public char last() {
/*  820 */       if (this.endIndex == this.beginIndex) {
/*  821 */         return internalSetIndex(this.endIndex);
/*      */       }
/*  823 */       return internalSetIndex(this.endIndex - 1);
/*      */     }
/*      */     
/*      */     public char current()
/*      */     {
/*  828 */       if (this.currentIndex == this.endIndex) {
/*  829 */         return 65535;
/*      */       }
/*  831 */       return AttributedString.this.charAt(this.currentIndex);
/*      */     }
/*      */     
/*      */     public char next()
/*      */     {
/*  836 */       if (this.currentIndex < this.endIndex) {
/*  837 */         return internalSetIndex(this.currentIndex + 1);
/*      */       }
/*      */       
/*  840 */       return 65535;
/*      */     }
/*      */     
/*      */     public char previous()
/*      */     {
/*  845 */       if (this.currentIndex > this.beginIndex) {
/*  846 */         return internalSetIndex(this.currentIndex - 1);
/*      */       }
/*      */       
/*  849 */       return 65535;
/*      */     }
/*      */     
/*      */     public char setIndex(int paramInt)
/*      */     {
/*  854 */       if ((paramInt < this.beginIndex) || (paramInt > this.endIndex))
/*  855 */         throw new IllegalArgumentException("Invalid index");
/*  856 */       return internalSetIndex(paramInt);
/*      */     }
/*      */     
/*      */     public int getBeginIndex() {
/*  860 */       return this.beginIndex;
/*      */     }
/*      */     
/*      */     public int getEndIndex() {
/*  864 */       return this.endIndex;
/*      */     }
/*      */     
/*      */     public int getIndex() {
/*  868 */       return this.currentIndex;
/*      */     }
/*      */     
/*      */ 
/*      */     public int getRunStart()
/*      */     {
/*  874 */       return this.currentRunStart;
/*      */     }
/*      */     
/*      */     public int getRunStart(AttributedCharacterIterator.Attribute paramAttribute) {
/*  878 */       if ((this.currentRunStart == this.beginIndex) || (this.currentRunIndex == -1)) {
/*  879 */         return this.currentRunStart;
/*      */       }
/*  881 */       Object localObject = getAttribute(paramAttribute);
/*  882 */       int i = this.currentRunStart;
/*  883 */       int j = this.currentRunIndex;
/*  884 */       while ((i > this.beginIndex) && 
/*  885 */         (AttributedString.valuesMatch(localObject, AttributedString.access$100(AttributedString.this, paramAttribute, j - 1)))) {
/*  886 */         j--;
/*  887 */         i = AttributedString.this.runStarts[j];
/*      */       }
/*  889 */       if (i < this.beginIndex) {
/*  890 */         i = this.beginIndex;
/*      */       }
/*  892 */       return i;
/*      */     }
/*      */     
/*      */     public int getRunStart(Set<? extends AttributedCharacterIterator.Attribute> paramSet)
/*      */     {
/*  897 */       if ((this.currentRunStart == this.beginIndex) || (this.currentRunIndex == -1)) {
/*  898 */         return this.currentRunStart;
/*      */       }
/*  900 */       int i = this.currentRunStart;
/*  901 */       int j = this.currentRunIndex;
/*  902 */       while ((i > this.beginIndex) && 
/*  903 */         (AttributedString.this.attributeValuesMatch(paramSet, this.currentRunIndex, j - 1))) {
/*  904 */         j--;
/*  905 */         i = AttributedString.this.runStarts[j];
/*      */       }
/*  907 */       if (i < this.beginIndex) {
/*  908 */         i = this.beginIndex;
/*      */       }
/*  910 */       return i;
/*      */     }
/*      */     
/*      */     public int getRunLimit()
/*      */     {
/*  915 */       return this.currentRunLimit;
/*      */     }
/*      */     
/*      */     public int getRunLimit(AttributedCharacterIterator.Attribute paramAttribute) {
/*  919 */       if ((this.currentRunLimit == this.endIndex) || (this.currentRunIndex == -1)) {
/*  920 */         return this.currentRunLimit;
/*      */       }
/*  922 */       Object localObject = getAttribute(paramAttribute);
/*  923 */       int i = this.currentRunLimit;
/*  924 */       int j = this.currentRunIndex;
/*  925 */       while ((i < this.endIndex) && 
/*  926 */         (AttributedString.valuesMatch(localObject, AttributedString.access$100(AttributedString.this, paramAttribute, j + 1)))) {
/*  927 */         j++;
/*  928 */         i = j < AttributedString.this.runCount - 1 ? AttributedString.this.runStarts[(j + 1)] : this.endIndex;
/*      */       }
/*  930 */       if (i > this.endIndex) {
/*  931 */         i = this.endIndex;
/*      */       }
/*  933 */       return i;
/*      */     }
/*      */     
/*      */     public int getRunLimit(Set<? extends AttributedCharacterIterator.Attribute> paramSet)
/*      */     {
/*  938 */       if ((this.currentRunLimit == this.endIndex) || (this.currentRunIndex == -1)) {
/*  939 */         return this.currentRunLimit;
/*      */       }
/*  941 */       int i = this.currentRunLimit;
/*  942 */       int j = this.currentRunIndex;
/*  943 */       while ((i < this.endIndex) && 
/*  944 */         (AttributedString.this.attributeValuesMatch(paramSet, this.currentRunIndex, j + 1))) {
/*  945 */         j++;
/*  946 */         i = j < AttributedString.this.runCount - 1 ? AttributedString.this.runStarts[(j + 1)] : this.endIndex;
/*      */       }
/*  948 */       if (i > this.endIndex) {
/*  949 */         i = this.endIndex;
/*      */       }
/*  951 */       return i;
/*      */     }
/*      */     
/*      */     public Map<AttributedCharacterIterator.Attribute, Object> getAttributes()
/*      */     {
/*  956 */       if ((AttributedString.this.runAttributes == null) || (this.currentRunIndex == -1) || (AttributedString.this.runAttributes[this.currentRunIndex] == null))
/*      */       {
/*      */ 
/*  959 */         return new Hashtable();
/*      */       }
/*  961 */       return new AttributedString.AttributeMap(AttributedString.this, this.currentRunIndex, this.beginIndex, this.endIndex);
/*      */     }
/*      */     
/*      */     public Set<AttributedCharacterIterator.Attribute> getAllAttributeKeys()
/*      */     {
/*  966 */       if (AttributedString.this.runAttributes == null)
/*      */       {
/*      */ 
/*  969 */         return new HashSet();
/*      */       }
/*  971 */       synchronized (AttributedString.this)
/*      */       {
/*      */ 
/*  974 */         HashSet localHashSet = new HashSet();
/*  975 */         int i = 0;
/*  976 */         while (i < AttributedString.this.runCount) {
/*  977 */           if ((AttributedString.this.runStarts[i] < this.endIndex) && ((i == AttributedString.this.runCount - 1) || (AttributedString.this.runStarts[(i + 1)] > this.beginIndex))) {
/*  978 */             Vector localVector = AttributedString.this.runAttributes[i];
/*  979 */             if (localVector != null) {
/*  980 */               int j = localVector.size();
/*  981 */               while (j-- > 0) {
/*  982 */                 localHashSet.add(localVector.get(j));
/*      */               }
/*      */             }
/*      */           }
/*  986 */           i++;
/*      */         }
/*  988 */         return localHashSet;
/*      */       }
/*      */     }
/*      */     
/*      */     public Object getAttribute(AttributedCharacterIterator.Attribute paramAttribute) {
/*  993 */       int i = this.currentRunIndex;
/*  994 */       if (i < 0) {
/*  995 */         return null;
/*      */       }
/*  997 */       return AttributedString.this.getAttributeCheckRange(paramAttribute, i, this.beginIndex, this.endIndex);
/*      */     }
/*      */     
/*      */ 
/*      */     private AttributedString getString()
/*      */     {
/* 1003 */       return AttributedString.this;
/*      */     }
/*      */     
/*      */ 
/*      */     private char internalSetIndex(int paramInt)
/*      */     {
/* 1009 */       this.currentIndex = paramInt;
/* 1010 */       if ((paramInt < this.currentRunStart) || (paramInt >= this.currentRunLimit)) {
/* 1011 */         updateRunInfo();
/*      */       }
/* 1013 */       if (this.currentIndex == this.endIndex) {
/* 1014 */         return 65535;
/*      */       }
/* 1016 */       return AttributedString.this.charAt(paramInt);
/*      */     }
/*      */     
/*      */ 
/*      */     private void updateRunInfo()
/*      */     {
/* 1022 */       if (this.currentIndex == this.endIndex) {
/* 1023 */         this.currentRunStart = (this.currentRunLimit = this.endIndex);
/* 1024 */         this.currentRunIndex = -1;
/*      */       } else {
/* 1026 */         synchronized (AttributedString.this) {
/* 1027 */           int i = -1;
/* 1028 */           while ((i < AttributedString.this.runCount - 1) && (AttributedString.this.runStarts[(i + 1)] <= this.currentIndex))
/* 1029 */             i++;
/* 1030 */           this.currentRunIndex = i;
/* 1031 */           if (i >= 0) {
/* 1032 */             this.currentRunStart = AttributedString.this.runStarts[i];
/* 1033 */             if (this.currentRunStart < this.beginIndex) {
/* 1034 */               this.currentRunStart = this.beginIndex;
/*      */             }
/*      */           } else {
/* 1037 */             this.currentRunStart = this.beginIndex;
/*      */           }
/* 1039 */           if (i < AttributedString.this.runCount - 1) {
/* 1040 */             this.currentRunLimit = AttributedString.this.runStarts[(i + 1)];
/* 1041 */             if (this.currentRunLimit > this.endIndex) {
/* 1042 */               this.currentRunLimit = this.endIndex;
/*      */             }
/*      */           } else {
/* 1045 */             this.currentRunLimit = this.endIndex;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private final class AttributeMap
/*      */     extends AbstractMap<AttributedCharacterIterator.Attribute, Object>
/*      */   {
/*      */     int runIndex;
/*      */     int beginIndex;
/*      */     int endIndex;
/*      */     
/*      */     AttributeMap(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1062 */       this.runIndex = paramInt1;
/* 1063 */       this.beginIndex = paramInt2;
/* 1064 */       this.endIndex = paramInt3;
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<AttributedCharacterIterator.Attribute, Object>> entrySet() {
/* 1068 */       HashSet localHashSet = new HashSet();
/* 1069 */       synchronized (AttributedString.this) {
/* 1070 */         int i = AttributedString.this.runAttributes[this.runIndex].size();
/* 1071 */         for (int j = 0; j < i; j++) {
/* 1072 */           AttributedCharacterIterator.Attribute localAttribute = (AttributedCharacterIterator.Attribute)AttributedString.this.runAttributes[this.runIndex].get(j);
/* 1073 */           Object localObject1 = AttributedString.this.runAttributeValues[this.runIndex].get(j);
/* 1074 */           if ((localObject1 instanceof Annotation)) {
/* 1075 */             localObject1 = AttributedString.this.getAttributeCheckRange(localAttribute, this.runIndex, this.beginIndex, this.endIndex);
/*      */             
/* 1077 */             if (localObject1 == null) {}
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1082 */             AttributeEntry localAttributeEntry = new AttributeEntry(localAttribute, localObject1);
/* 1083 */             localHashSet.add(localAttributeEntry);
/*      */           }
/*      */         } }
/* 1086 */       return localHashSet;
/*      */     }
/*      */     
/*      */     public Object get(Object paramObject) {
/* 1090 */       return AttributedString.this.getAttributeCheckRange((AttributedCharacterIterator.Attribute)paramObject, this.runIndex, this.beginIndex, this.endIndex);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/AttributedString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */