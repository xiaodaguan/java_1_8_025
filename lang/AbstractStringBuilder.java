/*      */ package java.lang;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import sun.misc.FloatingDecimal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ abstract class AbstractStringBuilder
/*      */   implements Appendable, CharSequence
/*      */ {
/*      */   char[] value;
/*      */   int count;
/*      */   
/*      */   AbstractStringBuilder() {}
/*      */   
/*      */   AbstractStringBuilder(int paramInt)
/*      */   {
/*   68 */     this.value = new char[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int length()
/*      */   {
/*   79 */     return this.count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int capacity()
/*      */   {
/*   90 */     return this.value.length;
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
/*      */   public void ensureCapacity(int paramInt)
/*      */   {
/*  110 */     if (paramInt > 0) {
/*  111 */       ensureCapacityInternal(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void ensureCapacityInternal(int paramInt)
/*      */   {
/*  120 */     if (paramInt - this.value.length > 0) {
/*  121 */       expandCapacity(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void expandCapacity(int paramInt)
/*      */   {
/*  129 */     int i = this.value.length * 2 + 2;
/*  130 */     if (i - paramInt < 0)
/*  131 */       i = paramInt;
/*  132 */     if (i < 0) {
/*  133 */       if (paramInt < 0)
/*  134 */         throw new OutOfMemoryError();
/*  135 */       i = Integer.MAX_VALUE;
/*      */     }
/*  137 */     this.value = Arrays.copyOf(this.value, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trimToSize()
/*      */   {
/*  148 */     if (this.count < this.value.length) {
/*  149 */       this.value = Arrays.copyOf(this.value, this.count);
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
/*      */   public void setLength(int paramInt)
/*      */   {
/*  179 */     if (paramInt < 0)
/*  180 */       throw new StringIndexOutOfBoundsException(paramInt);
/*  181 */     ensureCapacityInternal(paramInt);
/*      */     
/*  183 */     if (this.count < paramInt) {
/*  184 */       Arrays.fill(this.value, this.count, paramInt, '\000');
/*      */     }
/*      */     
/*  187 */     this.count = paramInt;
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
/*      */   public char charAt(int paramInt)
/*      */   {
/*  209 */     if ((paramInt < 0) || (paramInt >= this.count))
/*  210 */       throw new StringIndexOutOfBoundsException(paramInt);
/*  211 */     return this.value[paramInt];
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
/*      */   public int codePointAt(int paramInt)
/*      */   {
/*  236 */     if ((paramInt < 0) || (paramInt >= this.count)) {
/*  237 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  239 */     return Character.codePointAtImpl(this.value, paramInt, this.count);
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
/*      */   public int codePointBefore(int paramInt)
/*      */   {
/*  264 */     int i = paramInt - 1;
/*  265 */     if ((i < 0) || (i >= this.count)) {
/*  266 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  268 */     return Character.codePointBeforeImpl(this.value, paramInt, 0);
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
/*      */   public int codePointCount(int paramInt1, int paramInt2)
/*      */   {
/*  292 */     if ((paramInt1 < 0) || (paramInt2 > this.count) || (paramInt1 > paramInt2)) {
/*  293 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  295 */     return Character.codePointCountImpl(this.value, paramInt1, paramInt2 - paramInt1);
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
/*      */   public int offsetByCodePoints(int paramInt1, int paramInt2)
/*      */   {
/*  318 */     if ((paramInt1 < 0) || (paramInt1 > this.count)) {
/*  319 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  321 */     return Character.offsetByCodePointsImpl(this.value, 0, this.count, paramInt1, paramInt2);
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
/*      */   public void getChars(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
/*      */   {
/*  355 */     if (paramInt1 < 0)
/*  356 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*  357 */     if ((paramInt2 < 0) || (paramInt2 > this.count))
/*  358 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*  359 */     if (paramInt1 > paramInt2)
/*  360 */       throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
/*  361 */     System.arraycopy(this.value, paramInt1, paramArrayOfChar, paramInt3, paramInt2 - paramInt1);
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
/*      */   public void setCharAt(int paramInt, char paramChar)
/*      */   {
/*  379 */     if ((paramInt < 0) || (paramInt >= this.count))
/*  380 */       throw new StringIndexOutOfBoundsException(paramInt);
/*  381 */     this.value[paramInt] = paramChar;
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
/*      */   public AbstractStringBuilder append(Object paramObject)
/*      */   {
/*  396 */     return append(String.valueOf(paramObject));
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
/*      */   public AbstractStringBuilder append(String paramString)
/*      */   {
/*  418 */     if (paramString == null)
/*  419 */       return appendNull();
/*  420 */     int i = paramString.length();
/*  421 */     ensureCapacityInternal(this.count + i);
/*  422 */     paramString.getChars(0, i, this.value, this.count);
/*  423 */     this.count += i;
/*  424 */     return this;
/*      */   }
/*      */   
/*      */   public AbstractStringBuilder append(StringBuffer paramStringBuffer)
/*      */   {
/*  429 */     if (paramStringBuffer == null)
/*  430 */       return appendNull();
/*  431 */     int i = paramStringBuffer.length();
/*  432 */     ensureCapacityInternal(this.count + i);
/*  433 */     paramStringBuffer.getChars(0, i, this.value, this.count);
/*  434 */     this.count += i;
/*  435 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   AbstractStringBuilder append(AbstractStringBuilder paramAbstractStringBuilder)
/*      */   {
/*  442 */     if (paramAbstractStringBuilder == null)
/*  443 */       return appendNull();
/*  444 */     int i = paramAbstractStringBuilder.length();
/*  445 */     ensureCapacityInternal(this.count + i);
/*  446 */     paramAbstractStringBuilder.getChars(0, i, this.value, this.count);
/*  447 */     this.count += i;
/*  448 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */   public AbstractStringBuilder append(CharSequence paramCharSequence)
/*      */   {
/*  454 */     if (paramCharSequence == null)
/*  455 */       return appendNull();
/*  456 */     if ((paramCharSequence instanceof String))
/*  457 */       return append((String)paramCharSequence);
/*  458 */     if ((paramCharSequence instanceof AbstractStringBuilder)) {
/*  459 */       return append((AbstractStringBuilder)paramCharSequence);
/*      */     }
/*  461 */     return append(paramCharSequence, 0, paramCharSequence.length());
/*      */   }
/*      */   
/*      */   private AbstractStringBuilder appendNull() {
/*  465 */     int i = this.count;
/*  466 */     ensureCapacityInternal(i + 4);
/*  467 */     char[] arrayOfChar = this.value;
/*  468 */     arrayOfChar[(i++)] = 'n';
/*  469 */     arrayOfChar[(i++)] = 'u';
/*  470 */     arrayOfChar[(i++)] = 'l';
/*  471 */     arrayOfChar[(i++)] = 'l';
/*  472 */     this.count = i;
/*  473 */     return this;
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
/*      */   public AbstractStringBuilder append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*      */   {
/*  507 */     if (paramCharSequence == null)
/*  508 */       paramCharSequence = "null";
/*  509 */     if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 > paramCharSequence.length()))
/*      */     {
/*      */ 
/*  512 */       throw new IndexOutOfBoundsException("start " + paramInt1 + ", end " + paramInt2 + ", s.length() " + paramCharSequence.length()); }
/*  513 */     int i = paramInt2 - paramInt1;
/*  514 */     ensureCapacityInternal(this.count + i);
/*  515 */     int j = paramInt1; for (int k = this.count; j < paramInt2; k++) {
/*  516 */       this.value[k] = paramCharSequence.charAt(j);j++; }
/*  517 */     this.count += i;
/*  518 */     return this;
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
/*      */   public AbstractStringBuilder append(char[] paramArrayOfChar)
/*      */   {
/*  538 */     int i = paramArrayOfChar.length;
/*  539 */     ensureCapacityInternal(this.count + i);
/*  540 */     System.arraycopy(paramArrayOfChar, 0, this.value, this.count, i);
/*  541 */     this.count += i;
/*  542 */     return this;
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
/*      */   public AbstractStringBuilder append(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  568 */     if (paramInt2 > 0)
/*  569 */       ensureCapacityInternal(this.count + paramInt2);
/*  570 */     System.arraycopy(paramArrayOfChar, paramInt1, this.value, this.count, paramInt2);
/*  571 */     this.count += paramInt2;
/*  572 */     return this;
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
/*      */   public AbstractStringBuilder append(boolean paramBoolean)
/*      */   {
/*  588 */     if (paramBoolean) {
/*  589 */       ensureCapacityInternal(this.count + 4);
/*  590 */       this.value[(this.count++)] = 't';
/*  591 */       this.value[(this.count++)] = 'r';
/*  592 */       this.value[(this.count++)] = 'u';
/*  593 */       this.value[(this.count++)] = 'e';
/*      */     } else {
/*  595 */       ensureCapacityInternal(this.count + 5);
/*  596 */       this.value[(this.count++)] = 'f';
/*  597 */       this.value[(this.count++)] = 'a';
/*  598 */       this.value[(this.count++)] = 'l';
/*  599 */       this.value[(this.count++)] = 's';
/*  600 */       this.value[(this.count++)] = 'e';
/*      */     }
/*  602 */     return this;
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
/*      */   public AbstractStringBuilder append(char paramChar)
/*      */   {
/*  622 */     ensureCapacityInternal(this.count + 1);
/*  623 */     this.value[(this.count++)] = paramChar;
/*  624 */     return this;
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
/*      */   public AbstractStringBuilder append(int paramInt)
/*      */   {
/*  640 */     if (paramInt == Integer.MIN_VALUE) {
/*  641 */       append("-2147483648");
/*  642 */       return this;
/*      */     }
/*      */     
/*  645 */     int i = paramInt < 0 ? Integer.stringSize(-paramInt) + 1 : Integer.stringSize(paramInt);
/*  646 */     int j = this.count + i;
/*  647 */     ensureCapacityInternal(j);
/*  648 */     Integer.getChars(paramInt, j, this.value);
/*  649 */     this.count = j;
/*  650 */     return this;
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
/*      */   public AbstractStringBuilder append(long paramLong)
/*      */   {
/*  666 */     if (paramLong == Long.MIN_VALUE) {
/*  667 */       append("-9223372036854775808");
/*  668 */       return this;
/*      */     }
/*      */     
/*  671 */     int i = paramLong < 0L ? Long.stringSize(-paramLong) + 1 : Long.stringSize(paramLong);
/*  672 */     int j = this.count + i;
/*  673 */     ensureCapacityInternal(j);
/*  674 */     Long.getChars(paramLong, j, this.value);
/*  675 */     this.count = j;
/*  676 */     return this;
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
/*      */   public AbstractStringBuilder append(float paramFloat)
/*      */   {
/*  692 */     FloatingDecimal.appendTo(paramFloat, this);
/*  693 */     return this;
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
/*      */   public AbstractStringBuilder append(double paramDouble)
/*      */   {
/*  709 */     FloatingDecimal.appendTo(paramDouble, this);
/*  710 */     return this;
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
/*      */   public AbstractStringBuilder delete(int paramInt1, int paramInt2)
/*      */   {
/*  728 */     if (paramInt1 < 0)
/*  729 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*  730 */     if (paramInt2 > this.count)
/*  731 */       paramInt2 = this.count;
/*  732 */     if (paramInt1 > paramInt2)
/*  733 */       throw new StringIndexOutOfBoundsException();
/*  734 */     int i = paramInt2 - paramInt1;
/*  735 */     if (i > 0) {
/*  736 */       System.arraycopy(this.value, paramInt1 + i, this.value, paramInt1, this.count - paramInt2);
/*  737 */       this.count -= i;
/*      */     }
/*  739 */     return this;
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
/*      */   public AbstractStringBuilder appendCodePoint(int paramInt)
/*      */   {
/*  762 */     int i = this.count;
/*      */     
/*  764 */     if (Character.isBmpCodePoint(paramInt)) {
/*  765 */       ensureCapacityInternal(i + 1);
/*  766 */       this.value[i] = ((char)paramInt);
/*  767 */       this.count = (i + 1);
/*  768 */     } else if (Character.isValidCodePoint(paramInt)) {
/*  769 */       ensureCapacityInternal(i + 2);
/*  770 */       Character.toSurrogates(paramInt, this.value, i);
/*  771 */       this.count = (i + 2);
/*      */     } else {
/*  773 */       throw new IllegalArgumentException();
/*      */     }
/*  775 */     return this;
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
/*      */   public AbstractStringBuilder deleteCharAt(int paramInt)
/*      */   {
/*  796 */     if ((paramInt < 0) || (paramInt >= this.count))
/*  797 */       throw new StringIndexOutOfBoundsException(paramInt);
/*  798 */     System.arraycopy(this.value, paramInt + 1, this.value, paramInt, this.count - paramInt - 1);
/*  799 */     this.count -= 1;
/*  800 */     return this;
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
/*      */   public AbstractStringBuilder replace(int paramInt1, int paramInt2, String paramString)
/*      */   {
/*  823 */     if (paramInt1 < 0)
/*  824 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*  825 */     if (paramInt1 > this.count)
/*  826 */       throw new StringIndexOutOfBoundsException("start > length()");
/*  827 */     if (paramInt1 > paramInt2) {
/*  828 */       throw new StringIndexOutOfBoundsException("start > end");
/*      */     }
/*  830 */     if (paramInt2 > this.count)
/*  831 */       paramInt2 = this.count;
/*  832 */     int i = paramString.length();
/*  833 */     int j = this.count + i - (paramInt2 - paramInt1);
/*  834 */     ensureCapacityInternal(j);
/*      */     
/*  836 */     System.arraycopy(this.value, paramInt2, this.value, paramInt1 + i, this.count - paramInt2);
/*  837 */     paramString.getChars(this.value, paramInt1);
/*  838 */     this.count = j;
/*  839 */     return this;
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
/*      */   public String substring(int paramInt)
/*      */   {
/*  854 */     return substring(paramInt, this.count);
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
/*      */   public CharSequence subSequence(int paramInt1, int paramInt2)
/*      */   {
/*  885 */     return substring(paramInt1, paramInt2);
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
/*      */   public String substring(int paramInt1, int paramInt2)
/*      */   {
/*  903 */     if (paramInt1 < 0)
/*  904 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*  905 */     if (paramInt2 > this.count)
/*  906 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*  907 */     if (paramInt1 > paramInt2)
/*  908 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*  909 */     return new String(this.value, paramInt1, paramInt2 - paramInt1);
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
/*      */   public AbstractStringBuilder insert(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*      */   {
/*  936 */     if ((paramInt1 < 0) || (paramInt1 > length()))
/*  937 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*  938 */     if ((paramInt2 < 0) || (paramInt3 < 0) || (paramInt2 > paramArrayOfChar.length - paramInt3)) {
/*  939 */       throw new StringIndexOutOfBoundsException("offset " + paramInt2 + ", len " + paramInt3 + ", str.length " + paramArrayOfChar.length);
/*      */     }
/*      */     
/*  942 */     ensureCapacityInternal(this.count + paramInt3);
/*  943 */     System.arraycopy(this.value, paramInt1, this.value, paramInt1 + paramInt3, this.count - paramInt1);
/*  944 */     System.arraycopy(paramArrayOfChar, paramInt2, this.value, paramInt1, paramInt3);
/*  945 */     this.count += paramInt3;
/*  946 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, Object paramObject)
/*      */   {
/*  969 */     return insert(paramInt, String.valueOf(paramObject));
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
/*      */   public AbstractStringBuilder insert(int paramInt, String paramString)
/*      */   {
/* 1004 */     if ((paramInt < 0) || (paramInt > length()))
/* 1005 */       throw new StringIndexOutOfBoundsException(paramInt);
/* 1006 */     if (paramString == null)
/* 1007 */       paramString = "null";
/* 1008 */     int i = paramString.length();
/* 1009 */     ensureCapacityInternal(this.count + i);
/* 1010 */     System.arraycopy(this.value, paramInt, this.value, paramInt + i, this.count - paramInt);
/* 1011 */     paramString.getChars(this.value, paramInt);
/* 1012 */     this.count += i;
/* 1013 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, char[] paramArrayOfChar)
/*      */   {
/* 1041 */     if ((paramInt < 0) || (paramInt > length()))
/* 1042 */       throw new StringIndexOutOfBoundsException(paramInt);
/* 1043 */     int i = paramArrayOfChar.length;
/* 1044 */     ensureCapacityInternal(this.count + i);
/* 1045 */     System.arraycopy(this.value, paramInt, this.value, paramInt + i, this.count - paramInt);
/* 1046 */     System.arraycopy(paramArrayOfChar, 0, this.value, paramInt, i);
/* 1047 */     this.count += i;
/* 1048 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, CharSequence paramCharSequence)
/*      */   {
/* 1073 */     if (paramCharSequence == null)
/* 1074 */       paramCharSequence = "null";
/* 1075 */     if ((paramCharSequence instanceof String))
/* 1076 */       return insert(paramInt, (String)paramCharSequence);
/* 1077 */     return insert(paramInt, paramCharSequence, 0, paramCharSequence.length());
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
/*      */   public AbstractStringBuilder insert(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3)
/*      */   {
/* 1126 */     if (paramCharSequence == null)
/* 1127 */       paramCharSequence = "null";
/* 1128 */     if ((paramInt1 < 0) || (paramInt1 > length()))
/* 1129 */       throw new IndexOutOfBoundsException("dstOffset " + paramInt1);
/* 1130 */     if ((paramInt2 < 0) || (paramInt3 < 0) || (paramInt2 > paramInt3) || (paramInt3 > paramCharSequence.length()))
/*      */     {
/*      */ 
/* 1133 */       throw new IndexOutOfBoundsException("start " + paramInt2 + ", end " + paramInt3 + ", s.length() " + paramCharSequence.length()); }
/* 1134 */     int i = paramInt3 - paramInt2;
/* 1135 */     ensureCapacityInternal(this.count + i);
/* 1136 */     System.arraycopy(this.value, paramInt1, this.value, paramInt1 + i, this.count - paramInt1);
/*      */     
/* 1138 */     for (int j = paramInt2; j < paramInt3; j++)
/* 1139 */       this.value[(paramInt1++)] = paramCharSequence.charAt(j);
/* 1140 */     this.count += i;
/* 1141 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, boolean paramBoolean)
/*      */   {
/* 1164 */     return insert(paramInt, String.valueOf(paramBoolean));
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
/*      */   public AbstractStringBuilder insert(int paramInt, char paramChar)
/*      */   {
/* 1187 */     ensureCapacityInternal(this.count + 1);
/* 1188 */     System.arraycopy(this.value, paramInt, this.value, paramInt + 1, this.count - paramInt);
/* 1189 */     this.value[paramInt] = paramChar;
/* 1190 */     this.count += 1;
/* 1191 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt1, int paramInt2)
/*      */   {
/* 1214 */     return insert(paramInt1, String.valueOf(paramInt2));
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
/*      */   public AbstractStringBuilder insert(int paramInt, long paramLong)
/*      */   {
/* 1237 */     return insert(paramInt, String.valueOf(paramLong));
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
/*      */   public AbstractStringBuilder insert(int paramInt, float paramFloat)
/*      */   {
/* 1260 */     return insert(paramInt, String.valueOf(paramFloat));
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
/*      */   public AbstractStringBuilder insert(int paramInt, double paramDouble)
/*      */   {
/* 1283 */     return insert(paramInt, String.valueOf(paramDouble));
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
/*      */   public int indexOf(String paramString)
/*      */   {
/* 1302 */     return indexOf(paramString, 0);
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
/*      */   public int indexOf(String paramString, int paramInt)
/*      */   {
/* 1321 */     return String.indexOf(this.value, 0, this.count, paramString, paramInt);
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
/*      */   public int lastIndexOf(String paramString)
/*      */   {
/* 1341 */     return lastIndexOf(paramString, this.count);
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
/*      */   public int lastIndexOf(String paramString, int paramInt)
/*      */   {
/* 1360 */     return String.lastIndexOf(this.value, 0, this.count, paramString, paramInt);
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
/*      */   public AbstractStringBuilder reverse()
/*      */   {
/* 1386 */     int i = 0;
/* 1387 */     int j = this.count - 1;
/* 1388 */     for (int k = j - 1 >> 1; k >= 0; k--) {
/* 1389 */       int m = j - k;
/* 1390 */       char c1 = this.value[k];
/* 1391 */       char c2 = this.value[m];
/* 1392 */       this.value[k] = c2;
/* 1393 */       this.value[m] = c1;
/* 1394 */       if ((Character.isSurrogate(c1)) || 
/* 1395 */         (Character.isSurrogate(c2))) {
/* 1396 */         i = 1;
/*      */       }
/*      */     }
/* 1399 */     if (i != 0) {
/* 1400 */       reverseAllValidSurrogatePairs();
/*      */     }
/* 1402 */     return this;
/*      */   }
/*      */   
/*      */   private void reverseAllValidSurrogatePairs()
/*      */   {
/* 1407 */     for (int i = 0; i < this.count - 1; i++) {
/* 1408 */       char c1 = this.value[i];
/* 1409 */       if (Character.isLowSurrogate(c1)) {
/* 1410 */         char c2 = this.value[(i + 1)];
/* 1411 */         if (Character.isHighSurrogate(c2)) {
/* 1412 */           this.value[(i++)] = c2;
/* 1413 */           this.value[i] = c1;
/*      */         }
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
/*      */   public abstract String toString();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final char[] getValue()
/*      */   {
/* 1436 */     return this.value;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/AbstractStringBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */