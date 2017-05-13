/*      */ package java.lang;
/*      */ 
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Formatter;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.StringJoiner;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class String
/*      */   implements Serializable, Comparable<String>, CharSequence
/*      */ {
/*      */   private final char[] value;
/*      */   private int hash;
/*      */   private static final long serialVersionUID = -6849794470754667710L;
/*  129 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String()
/*      */   {
/*  138 */     this.value = new char[0];
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
/*      */   public String(String paramString)
/*      */   {
/*  152 */     this.value = paramString.value;
/*  153 */     this.hash = paramString.hash;
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
/*      */   public String(char[] paramArrayOfChar)
/*      */   {
/*  166 */     this.value = Arrays.copyOf(paramArrayOfChar, paramArrayOfChar.length);
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
/*      */   public String(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  191 */     if (paramInt1 < 0) {
/*  192 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  194 */     if (paramInt2 < 0) {
/*  195 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*      */     
/*  198 */     if (paramInt1 > paramArrayOfChar.length - paramInt2) {
/*  199 */       throw new StringIndexOutOfBoundsException(paramInt1 + paramInt2);
/*      */     }
/*  201 */     this.value = Arrays.copyOfRange(paramArrayOfChar, paramInt1, paramInt1 + paramInt2);
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
/*      */   public String(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/*  233 */     if (paramInt1 < 0) {
/*  234 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  236 */     if (paramInt2 < 0) {
/*  237 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*      */     
/*  240 */     if (paramInt1 > paramArrayOfInt.length - paramInt2) {
/*  241 */       throw new StringIndexOutOfBoundsException(paramInt1 + paramInt2);
/*      */     }
/*      */     
/*  244 */     int i = paramInt1 + paramInt2;
/*      */     
/*      */ 
/*  247 */     int j = paramInt2;
/*  248 */     for (int k = paramInt1; k < i; k++) {
/*  249 */       m = paramArrayOfInt[k];
/*  250 */       if (!Character.isBmpCodePoint(m))
/*      */       {
/*  252 */         if (Character.isValidCodePoint(m))
/*  253 */           j++; else {
/*  254 */           throw new IllegalArgumentException(Integer.toString(m));
/*      */         }
/*      */       }
/*      */     }
/*  258 */     char[] arrayOfChar = new char[j];
/*      */     
/*  260 */     int m = paramInt1; for (int n = 0; m < i; n++) {
/*  261 */       int i1 = paramArrayOfInt[m];
/*  262 */       if (Character.isBmpCodePoint(i1)) {
/*  263 */         arrayOfChar[n] = ((char)i1);
/*      */       } else {
/*  265 */         Character.toSurrogates(i1, arrayOfChar, n++);
/*      */       }
/*  260 */       m++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  268 */     this.value = arrayOfChar;
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
/*      */   @Deprecated
/*      */   public String(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  312 */     checkBounds(paramArrayOfByte, paramInt2, paramInt3);
/*  313 */     char[] arrayOfChar = new char[paramInt3];
/*      */     int i;
/*  315 */     if (paramInt1 == 0) {
/*  316 */       for (i = paramInt3; i-- > 0;) {
/*  317 */         arrayOfChar[i] = ((char)(paramArrayOfByte[(i + paramInt2)] & 0xFF));
/*      */       }
/*      */     } else {
/*  320 */       paramInt1 <<= 8;
/*  321 */       for (i = paramInt3; i-- > 0;) {
/*  322 */         arrayOfChar[i] = ((char)(paramInt1 | paramArrayOfByte[(i + paramInt2)] & 0xFF));
/*      */       }
/*      */     }
/*  325 */     this.value = arrayOfChar;
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
/*      */   @Deprecated
/*      */   public String(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/*  360 */     this(paramArrayOfByte, paramInt, 0, paramArrayOfByte.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void checkBounds(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  368 */     if (paramInt2 < 0)
/*  369 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*  370 */     if (paramInt1 < 0)
/*  371 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*  372 */     if (paramInt1 > paramArrayOfByte.length - paramInt2) {
/*  373 */       throw new StringIndexOutOfBoundsException(paramInt1 + paramInt2);
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
/*      */   public String(byte[] paramArrayOfByte, int paramInt1, int paramInt2, String paramString)
/*      */     throws UnsupportedEncodingException
/*      */   {
/*  411 */     if (paramString == null)
/*  412 */       throw new NullPointerException("charsetName");
/*  413 */     checkBounds(paramArrayOfByte, paramInt1, paramInt2);
/*  414 */     this.value = StringCoding.decode(paramString, paramArrayOfByte, paramInt1, paramInt2);
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
/*      */   public String(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Charset paramCharset)
/*      */   {
/*  448 */     if (paramCharset == null)
/*  449 */       throw new NullPointerException("charset");
/*  450 */     checkBounds(paramArrayOfByte, paramInt1, paramInt2);
/*  451 */     this.value = StringCoding.decode(paramCharset, paramArrayOfByte, paramInt1, paramInt2);
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
/*      */   public String(byte[] paramArrayOfByte, String paramString)
/*      */     throws UnsupportedEncodingException
/*      */   {
/*  479 */     this(paramArrayOfByte, 0, paramArrayOfByte.length, paramString);
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
/*      */   public String(byte[] paramArrayOfByte, Charset paramCharset)
/*      */   {
/*  503 */     this(paramArrayOfByte, 0, paramArrayOfByte.length, paramCharset);
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
/*      */   public String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  533 */     checkBounds(paramArrayOfByte, paramInt1, paramInt2);
/*  534 */     this.value = StringCoding.decode(paramArrayOfByte, paramInt1, paramInt2);
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
/*      */   public String(byte[] paramArrayOfByte)
/*      */   {
/*  554 */     this(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public String(StringBuffer paramStringBuffer)
/*      */   {
/*  567 */     synchronized (paramStringBuffer) {
/*  568 */       this.value = Arrays.copyOf(paramStringBuffer.getValue(), paramStringBuffer.length());
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
/*      */   public String(StringBuilder paramStringBuilder)
/*      */   {
/*  588 */     this.value = Arrays.copyOf(paramStringBuilder.getValue(), paramStringBuilder.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   String(char[] paramArrayOfChar, boolean paramBoolean)
/*      */   {
/*  599 */     this.value = paramArrayOfChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int length()
/*      */   {
/*  611 */     return this.value.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  623 */     return this.value.length == 0;
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
/*  645 */     if ((paramInt < 0) || (paramInt >= this.value.length)) {
/*  646 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  648 */     return this.value[paramInt];
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
/*      */   public int codePointAt(int paramInt)
/*      */   {
/*  674 */     if ((paramInt < 0) || (paramInt >= this.value.length)) {
/*  675 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  677 */     return Character.codePointAtImpl(this.value, paramInt, this.value.length);
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
/*      */   public int codePointBefore(int paramInt)
/*      */   {
/*  703 */     int i = paramInt - 1;
/*  704 */     if ((i < 0) || (i >= this.value.length)) {
/*  705 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  707 */     return Character.codePointBeforeImpl(this.value, paramInt, 0);
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
/*      */   public int codePointCount(int paramInt1, int paramInt2)
/*      */   {
/*  732 */     if ((paramInt1 < 0) || (paramInt2 > this.value.length) || (paramInt1 > paramInt2)) {
/*  733 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  735 */     return Character.codePointCountImpl(this.value, paramInt1, paramInt2 - paramInt1);
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
/*      */   public int offsetByCodePoints(int paramInt1, int paramInt2)
/*      */   {
/*  759 */     if ((paramInt1 < 0) || (paramInt1 > this.value.length)) {
/*  760 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  762 */     return Character.offsetByCodePointsImpl(this.value, 0, this.value.length, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void getChars(char[] paramArrayOfChar, int paramInt)
/*      */   {
/*  771 */     System.arraycopy(this.value, 0, paramArrayOfChar, paramInt, this.value.length);
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
/*  805 */     if (paramInt1 < 0) {
/*  806 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  808 */     if (paramInt2 > this.value.length) {
/*  809 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  811 */     if (paramInt1 > paramInt2) {
/*  812 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*      */     }
/*  814 */     System.arraycopy(this.value, paramInt1, paramArrayOfChar, paramInt3, paramInt2 - paramInt1);
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
/*      */   @Deprecated
/*      */   public void getBytes(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
/*      */   {
/*  862 */     if (paramInt1 < 0) {
/*  863 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  865 */     if (paramInt2 > this.value.length) {
/*  866 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  868 */     if (paramInt1 > paramInt2) {
/*  869 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*      */     }
/*  871 */     Objects.requireNonNull(paramArrayOfByte);
/*      */     
/*  873 */     int i = paramInt3;
/*  874 */     int j = paramInt2;
/*  875 */     int k = paramInt1;
/*  876 */     char[] arrayOfChar = this.value;
/*      */     
/*  878 */     while (k < j) {
/*  879 */       paramArrayOfByte[(i++)] = ((byte)arrayOfChar[(k++)]);
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
/*      */   public byte[] getBytes(String paramString)
/*      */     throws UnsupportedEncodingException
/*      */   {
/*  905 */     if (paramString == null) throw new NullPointerException();
/*  906 */     return StringCoding.encode(paramString, this.value, 0, this.value.length);
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
/*      */   public byte[] getBytes(Charset paramCharset)
/*      */   {
/*  928 */     if (paramCharset == null) throw new NullPointerException();
/*  929 */     return StringCoding.encode(paramCharset, this.value, 0, this.value.length);
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
/*      */   public byte[] getBytes()
/*      */   {
/*  946 */     return StringCoding.encode(this.value, 0, this.value.length);
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  965 */     if (this == paramObject) {
/*  966 */       return true;
/*      */     }
/*  968 */     if ((paramObject instanceof String)) {
/*  969 */       String str = (String)paramObject;
/*  970 */       int i = this.value.length;
/*  971 */       if (i == str.value.length) {
/*  972 */         char[] arrayOfChar1 = this.value;
/*  973 */         char[] arrayOfChar2 = str.value;
/*  974 */         int j = 0;
/*  975 */         while (i-- != 0) {
/*  976 */           if (arrayOfChar1[j] != arrayOfChar2[j])
/*  977 */             return false;
/*  978 */           j++;
/*      */         }
/*  980 */         return true;
/*      */       }
/*      */     }
/*  983 */     return false;
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
/*      */   public boolean contentEquals(StringBuffer paramStringBuffer)
/*      */   {
/* 1002 */     return contentEquals(paramStringBuffer);
/*      */   }
/*      */   
/*      */   private boolean nonSyncContentEquals(AbstractStringBuilder paramAbstractStringBuilder) {
/* 1006 */     char[] arrayOfChar1 = this.value;
/* 1007 */     char[] arrayOfChar2 = paramAbstractStringBuilder.getValue();
/* 1008 */     int i = arrayOfChar1.length;
/* 1009 */     if (i != paramAbstractStringBuilder.length()) {
/* 1010 */       return false;
/*      */     }
/* 1012 */     for (int j = 0; j < i; j++) {
/* 1013 */       if (arrayOfChar1[j] != arrayOfChar2[j]) {
/* 1014 */         return false;
/*      */       }
/*      */     }
/* 1017 */     return true;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean contentEquals(CharSequence paramCharSequence)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: instanceof 46
/*      */     //   4: ifeq +39 -> 43
/*      */     //   7: aload_1
/*      */     //   8: instanceof 47
/*      */     //   11: ifeq +23 -> 34
/*      */     //   14: aload_1
/*      */     //   15: dup
/*      */     //   16: astore_2
/*      */     //   17: monitorenter
/*      */     //   18: aload_0
/*      */     //   19: aload_1
/*      */     //   20: checkcast 46	java/lang/AbstractStringBuilder
/*      */     //   23: invokespecial 48	java/lang/String:nonSyncContentEquals	(Ljava/lang/AbstractStringBuilder;)Z
/*      */     //   26: aload_2
/*      */     //   27: monitorexit
/*      */     //   28: ireturn
/*      */     //   29: astore_3
/*      */     //   30: aload_2
/*      */     //   31: monitorexit
/*      */     //   32: aload_3
/*      */     //   33: athrow
/*      */     //   34: aload_0
/*      */     //   35: aload_1
/*      */     //   36: checkcast 46	java/lang/AbstractStringBuilder
/*      */     //   39: invokespecial 48	java/lang/String:nonSyncContentEquals	(Ljava/lang/AbstractStringBuilder;)Z
/*      */     //   42: ireturn
/*      */     //   43: aload_1
/*      */     //   44: aload_0
/*      */     //   45: invokevirtual 49	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*      */     //   48: ifeq +5 -> 53
/*      */     //   51: iconst_1
/*      */     //   52: ireturn
/*      */     //   53: aload_0
/*      */     //   54: getfield 2	java/lang/String:value	[C
/*      */     //   57: astore_2
/*      */     //   58: aload_2
/*      */     //   59: arraylength
/*      */     //   60: istore_3
/*      */     //   61: iload_3
/*      */     //   62: aload_1
/*      */     //   63: invokeinterface 50 1 0
/*      */     //   68: if_icmpeq +5 -> 73
/*      */     //   71: iconst_0
/*      */     //   72: ireturn
/*      */     //   73: iconst_0
/*      */     //   74: istore 4
/*      */     //   76: iload 4
/*      */     //   78: iload_3
/*      */     //   79: if_icmpge +26 -> 105
/*      */     //   82: aload_2
/*      */     //   83: iload 4
/*      */     //   85: caload
/*      */     //   86: aload_1
/*      */     //   87: iload 4
/*      */     //   89: invokeinterface 51 2 0
/*      */     //   94: if_icmpeq +5 -> 99
/*      */     //   97: iconst_0
/*      */     //   98: ireturn
/*      */     //   99: iinc 4 1
/*      */     //   102: goto -26 -> 76
/*      */     //   105: iconst_1
/*      */     //   106: ireturn
/*      */     // Line number table:
/*      */     //   Java source line #1038	-> byte code offset #0
/*      */     //   Java source line #1039	-> byte code offset #7
/*      */     //   Java source line #1040	-> byte code offset #14
/*      */     //   Java source line #1041	-> byte code offset #18
/*      */     //   Java source line #1042	-> byte code offset #29
/*      */     //   Java source line #1044	-> byte code offset #34
/*      */     //   Java source line #1048	-> byte code offset #43
/*      */     //   Java source line #1049	-> byte code offset #51
/*      */     //   Java source line #1051	-> byte code offset #53
/*      */     //   Java source line #1052	-> byte code offset #58
/*      */     //   Java source line #1053	-> byte code offset #61
/*      */     //   Java source line #1054	-> byte code offset #71
/*      */     //   Java source line #1056	-> byte code offset #73
/*      */     //   Java source line #1057	-> byte code offset #82
/*      */     //   Java source line #1058	-> byte code offset #97
/*      */     //   Java source line #1056	-> byte code offset #99
/*      */     //   Java source line #1061	-> byte code offset #105
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	107	0	this	String
/*      */     //   0	107	1	paramCharSequence	CharSequence
/*      */     //   16	67	2	Ljava/lang/Object;	Object
/*      */     //   29	4	3	localObject1	Object
/*      */     //   60	20	3	i	int
/*      */     //   74	26	4	j	int
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   18	28	29	finally
/*      */     //   29	32	29	finally
/*      */   }
/*      */   
/*      */   public boolean equalsIgnoreCase(String paramString)
/*      */   {
/* 1093 */     if ((paramString != null) && (paramString.value.length == this.value.length)) {}
/*      */     
/*      */ 
/* 1096 */     return this == paramString;
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
/*      */   public int compareTo(String paramString)
/*      */   {
/* 1141 */     int i = this.value.length;
/* 1142 */     int j = paramString.value.length;
/* 1143 */     int k = Math.min(i, j);
/* 1144 */     char[] arrayOfChar1 = this.value;
/* 1145 */     char[] arrayOfChar2 = paramString.value;
/*      */     
/* 1147 */     int m = 0;
/* 1148 */     while (m < k) {
/* 1149 */       int n = arrayOfChar1[m];
/* 1150 */       int i1 = arrayOfChar2[m];
/* 1151 */       if (n != i1) {
/* 1152 */         return n - i1;
/*      */       }
/* 1154 */       m++;
/*      */     }
/* 1156 */     return i - j;
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
/* 1171 */   public static final Comparator<String> CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator(null);
/*      */   
/*      */   private static class CaseInsensitiveComparator implements Comparator<String>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 8575799808933029326L;
/*      */     
/*      */     public int compare(String paramString1, String paramString2)
/*      */     {
/* 1179 */       int i = paramString1.length();
/* 1180 */       int j = paramString2.length();
/* 1181 */       int k = Math.min(i, j);
/* 1182 */       for (int m = 0; m < k; m++) {
/* 1183 */         char c1 = paramString1.charAt(m);
/* 1184 */         char c2 = paramString2.charAt(m);
/* 1185 */         if (c1 != c2) {
/* 1186 */           c1 = Character.toUpperCase(c1);
/* 1187 */           c2 = Character.toUpperCase(c2);
/* 1188 */           if (c1 != c2) {
/* 1189 */             c1 = Character.toLowerCase(c1);
/* 1190 */             c2 = Character.toLowerCase(c2);
/* 1191 */             if (c1 != c2)
/*      */             {
/* 1193 */               return c1 - c2;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1198 */       return i - j;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 1202 */       return String.CASE_INSENSITIVE_ORDER;
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
/*      */   public int compareToIgnoreCase(String paramString)
/*      */   {
/* 1226 */     return CASE_INSENSITIVE_ORDER.compare(this, paramString);
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
/*      */   public boolean regionMatches(int paramInt1, String paramString, int paramInt2, int paramInt3)
/*      */   {
/* 1263 */     char[] arrayOfChar1 = this.value;
/* 1264 */     int i = paramInt1;
/* 1265 */     char[] arrayOfChar2 = paramString.value;
/* 1266 */     int j = paramInt2;
/*      */     
/* 1268 */     if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt1 > this.value.length - paramInt3) || (paramInt2 > paramString.value.length - paramInt3))
/*      */     {
/*      */ 
/* 1271 */       return false;
/*      */     }
/* 1273 */     while (paramInt3-- > 0) {
/* 1274 */       if (arrayOfChar1[(i++)] != arrayOfChar2[(j++)]) {
/* 1275 */         return false;
/*      */       }
/*      */     }
/* 1278 */     return true;
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
/*      */   public boolean regionMatches(boolean paramBoolean, int paramInt1, String paramString, int paramInt2, int paramInt3)
/*      */   {
/* 1333 */     char[] arrayOfChar1 = this.value;
/* 1334 */     int i = paramInt1;
/* 1335 */     char[] arrayOfChar2 = paramString.value;
/* 1336 */     int j = paramInt2;
/*      */     
/* 1338 */     if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt1 > this.value.length - paramInt3) || (paramInt2 > paramString.value.length - paramInt3))
/*      */     {
/*      */ 
/* 1341 */       return false;
/*      */     }
/* 1343 */     while (paramInt3-- > 0) {
/* 1344 */       char c1 = arrayOfChar1[(i++)];
/* 1345 */       char c2 = arrayOfChar2[(j++)];
/* 1346 */       if (c1 != c2)
/*      */       {
/*      */ 
/* 1349 */         if (paramBoolean)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1354 */           char c3 = Character.toUpperCase(c1);
/* 1355 */           char c4 = Character.toUpperCase(c2);
/* 1356 */           if ((c3 == c4) || 
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1363 */             (Character.toLowerCase(c3) == Character.toLowerCase(c4))) {
/*      */             break;
/*      */           }
/*      */         } else {
/* 1367 */           return false;
/*      */         } } }
/* 1369 */     return true;
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
/*      */   public boolean startsWith(String paramString, int paramInt)
/*      */   {
/* 1390 */     char[] arrayOfChar1 = this.value;
/* 1391 */     int i = paramInt;
/* 1392 */     char[] arrayOfChar2 = paramString.value;
/* 1393 */     int j = 0;
/* 1394 */     int k = paramString.value.length;
/*      */     
/* 1396 */     if ((paramInt < 0) || (paramInt > this.value.length - k))
/* 1397 */       return false;
/*      */     do {
/* 1399 */       k--; if (k < 0) break;
/* 1400 */     } while (arrayOfChar1[(i++)] == arrayOfChar2[(j++)]);
/* 1401 */     return false;
/*      */     
/*      */ 
/* 1404 */     return true;
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
/*      */   public boolean startsWith(String paramString)
/*      */   {
/* 1421 */     return startsWith(paramString, 0);
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
/*      */   public boolean endsWith(String paramString)
/*      */   {
/* 1436 */     return startsWith(paramString, this.value.length - paramString.value.length);
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
/*      */   public int hashCode()
/*      */   {
/* 1453 */     int i = this.hash;
/* 1454 */     if ((i == 0) && (this.value.length > 0)) {
/* 1455 */       char[] arrayOfChar = this.value;
/*      */       
/* 1457 */       for (int j = 0; j < this.value.length; j++) {
/* 1458 */         i = 31 * i + arrayOfChar[j];
/*      */       }
/* 1460 */       this.hash = i;
/*      */     }
/* 1462 */     return i;
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
/*      */   public int indexOf(int paramInt)
/*      */   {
/* 1490 */     return indexOf(paramInt, 0);
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
/*      */   public int indexOf(int paramInt1, int paramInt2)
/*      */   {
/* 1533 */     int i = this.value.length;
/* 1534 */     if (paramInt2 < 0) {
/* 1535 */       paramInt2 = 0;
/* 1536 */     } else if (paramInt2 >= i)
/*      */     {
/* 1538 */       return -1;
/*      */     }
/*      */     
/* 1541 */     if (paramInt1 < 65536)
/*      */     {
/*      */ 
/* 1544 */       char[] arrayOfChar = this.value;
/* 1545 */       for (int j = paramInt2; j < i; j++) {
/* 1546 */         if (arrayOfChar[j] == paramInt1) {
/* 1547 */           return j;
/*      */         }
/*      */       }
/* 1550 */       return -1;
/*      */     }
/* 1552 */     return indexOfSupplementary(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int indexOfSupplementary(int paramInt1, int paramInt2)
/*      */   {
/* 1560 */     if (Character.isValidCodePoint(paramInt1)) {
/* 1561 */       char[] arrayOfChar = this.value;
/* 1562 */       int i = Character.highSurrogate(paramInt1);
/* 1563 */       int j = Character.lowSurrogate(paramInt1);
/* 1564 */       int k = arrayOfChar.length - 1;
/* 1565 */       for (int m = paramInt2; m < k; m++) {
/* 1566 */         if ((arrayOfChar[m] == i) && (arrayOfChar[(m + 1)] == j)) {
/* 1567 */           return m;
/*      */         }
/*      */       }
/*      */     }
/* 1571 */     return -1;
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
/*      */   public int lastIndexOf(int paramInt)
/*      */   {
/* 1598 */     return lastIndexOf(paramInt, this.value.length - 1);
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
/*      */   public int lastIndexOf(int paramInt1, int paramInt2)
/*      */   {
/* 1636 */     if (paramInt1 < 65536)
/*      */     {
/*      */ 
/* 1639 */       char[] arrayOfChar = this.value;
/* 1640 */       for (int i = Math.min(paramInt2, arrayOfChar.length - 1); 
/* 1641 */           i >= 0; i--) {
/* 1642 */         if (arrayOfChar[i] == paramInt1) {
/* 1643 */           return i;
/*      */         }
/*      */       }
/* 1646 */       return -1;
/*      */     }
/* 1648 */     return lastIndexOfSupplementary(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int lastIndexOfSupplementary(int paramInt1, int paramInt2)
/*      */   {
/* 1656 */     if (Character.isValidCodePoint(paramInt1)) {
/* 1657 */       char[] arrayOfChar = this.value;
/* 1658 */       int i = Character.highSurrogate(paramInt1);
/* 1659 */       int j = Character.lowSurrogate(paramInt1);
/* 1660 */       for (int k = Math.min(paramInt2, arrayOfChar.length - 2); 
/* 1661 */           k >= 0; k--) {
/* 1662 */         if ((arrayOfChar[k] == i) && (arrayOfChar[(k + 1)] == j)) {
/* 1663 */           return k;
/*      */         }
/*      */       }
/*      */     }
/* 1667 */     return -1;
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
/*      */   public int indexOf(String paramString)
/*      */   {
/* 1685 */     return indexOf(paramString, 0);
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
/*      */   public int indexOf(String paramString, int paramInt)
/*      */   {
/* 1705 */     return indexOf(this.value, 0, this.value.length, paramString.value, 0, paramString.value.length, paramInt);
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
/*      */   static int indexOf(char[] paramArrayOfChar, int paramInt1, int paramInt2, String paramString, int paramInt3)
/*      */   {
/* 1722 */     return indexOf(paramArrayOfChar, paramInt1, paramInt2, paramString.value, 0, paramString.value.length, paramInt3);
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
/*      */   static int indexOf(char[] paramArrayOfChar1, int paramInt1, int paramInt2, char[] paramArrayOfChar2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/* 1743 */     if (paramInt5 >= paramInt2) {
/* 1744 */       return paramInt4 == 0 ? paramInt2 : -1;
/*      */     }
/* 1746 */     if (paramInt5 < 0) {
/* 1747 */       paramInt5 = 0;
/*      */     }
/* 1749 */     if (paramInt4 == 0) {
/* 1750 */       return paramInt5;
/*      */     }
/*      */     
/* 1753 */     int i = paramArrayOfChar2[paramInt3];
/* 1754 */     int j = paramInt1 + (paramInt2 - paramInt4);
/*      */     
/* 1756 */     for (int k = paramInt1 + paramInt5; k <= j; k++)
/*      */     {
/* 1758 */       if (paramArrayOfChar1[k] != i) {
/* 1759 */         do { k++; } while ((k <= j) && (paramArrayOfChar1[k] != i));
/*      */       }
/*      */       
/*      */ 
/* 1763 */       if (k <= j) {
/* 1764 */         int m = k + 1;
/* 1765 */         int n = m + paramInt4 - 1;
/* 1766 */         for (int i1 = paramInt3 + 1; (m < n) && (paramArrayOfChar1[m] == paramArrayOfChar2[i1]); 
/* 1767 */             i1++) { m++;
/*      */         }
/* 1769 */         if (m == n)
/*      */         {
/* 1771 */           return k - paramInt1;
/*      */         }
/*      */       }
/*      */     }
/* 1775 */     return -1;
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
/*      */   public int lastIndexOf(String paramString)
/*      */   {
/* 1794 */     return lastIndexOf(paramString, this.value.length);
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
/*      */   public int lastIndexOf(String paramString, int paramInt)
/*      */   {
/* 1814 */     return lastIndexOf(this.value, 0, this.value.length, paramString.value, 0, paramString.value.length, paramInt);
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
/*      */   static int lastIndexOf(char[] paramArrayOfChar, int paramInt1, int paramInt2, String paramString, int paramInt3)
/*      */   {
/* 1831 */     return lastIndexOf(paramArrayOfChar, paramInt1, paramInt2, paramString.value, 0, paramString.value.length, paramInt3);
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
/*      */   static int lastIndexOf(char[] paramArrayOfChar1, int paramInt1, int paramInt2, char[] paramArrayOfChar2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/* 1856 */     int i = paramInt2 - paramInt4;
/* 1857 */     if (paramInt5 < 0) {
/* 1858 */       return -1;
/*      */     }
/* 1860 */     if (paramInt5 > i) {
/* 1861 */       paramInt5 = i;
/*      */     }
/*      */     
/* 1864 */     if (paramInt4 == 0) {
/* 1865 */       return paramInt5;
/*      */     }
/*      */     
/* 1868 */     int j = paramInt3 + paramInt4 - 1;
/* 1869 */     int k = paramArrayOfChar2[j];
/* 1870 */     int m = paramInt1 + paramInt4 - 1;
/* 1871 */     int n = m + paramInt5;
/*      */     int i2;
/*      */     for (;;)
/*      */     {
/* 1875 */       if ((n >= m) && (paramArrayOfChar1[n] != k)) {
/* 1876 */         n--;
/*      */       } else {
/* 1878 */         if (n < m) {
/* 1879 */           return -1;
/*      */         }
/* 1881 */         int i1 = n - 1;
/* 1882 */         i2 = i1 - (paramInt4 - 1);
/* 1883 */         int i3 = j - 1;
/*      */         do {
/* 1885 */           if (i1 <= i2) break;
/* 1886 */         } while (paramArrayOfChar1[(i1--)] == paramArrayOfChar2[(i3--)]);
/* 1887 */         n--;
/*      */       }
/*      */     }
/*      */     
/* 1891 */     return i2 - paramInt1 + 1;
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
/*      */   public String substring(int paramInt)
/*      */   {
/* 1913 */     if (paramInt < 0) {
/* 1914 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/* 1916 */     int i = this.value.length - paramInt;
/* 1917 */     if (i < 0) {
/* 1918 */       throw new StringIndexOutOfBoundsException(i);
/*      */     }
/* 1920 */     return paramInt == 0 ? this : new String(this.value, paramInt, i);
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
/*      */   public String substring(int paramInt1, int paramInt2)
/*      */   {
/* 1946 */     if (paramInt1 < 0) {
/* 1947 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/* 1949 */     if (paramInt2 > this.value.length) {
/* 1950 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/* 1952 */     int i = paramInt2 - paramInt1;
/* 1953 */     if (i < 0) {
/* 1954 */       throw new StringIndexOutOfBoundsException(i);
/*      */     }
/* 1956 */     return (paramInt1 == 0) && (paramInt2 == this.value.length) ? this : new String(this.value, paramInt1, i);
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
/*      */   public CharSequence subSequence(int paramInt1, int paramInt2)
/*      */   {
/* 1990 */     return substring(paramInt1, paramInt2);
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
/*      */   public String concat(String paramString)
/*      */   {
/* 2014 */     int i = paramString.length();
/* 2015 */     if (i == 0) {
/* 2016 */       return this;
/*      */     }
/* 2018 */     int j = this.value.length;
/* 2019 */     char[] arrayOfChar = Arrays.copyOf(this.value, j + i);
/* 2020 */     paramString.getChars(arrayOfChar, j);
/* 2021 */     return new String(arrayOfChar, true);
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
/*      */   public String replace(char paramChar1, char paramChar2)
/*      */   {
/* 2054 */     if (paramChar1 != paramChar2) {
/* 2055 */       int i = this.value.length;
/* 2056 */       int j = -1;
/* 2057 */       char[] arrayOfChar1 = this.value;
/*      */       for (;;) {
/* 2059 */         j++; if (j < i) {
/* 2060 */           if (arrayOfChar1[j] == paramChar1)
/*      */             break;
/*      */         }
/*      */       }
/* 2064 */       if (j < i) {
/* 2065 */         char[] arrayOfChar2 = new char[i];
/* 2066 */         for (char c = '\000'; c < j; c++) {
/* 2067 */           arrayOfChar2[c] = arrayOfChar1[c];
/*      */         }
/* 2069 */         while (j < i) {
/* 2070 */           c = arrayOfChar1[j];
/* 2071 */           arrayOfChar2[j] = (c == paramChar1 ? paramChar2 : c);
/* 2072 */           j++;
/*      */         }
/* 2074 */         return new String(arrayOfChar2, true);
/*      */       }
/*      */     }
/* 2077 */     return this;
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
/*      */   public boolean matches(String paramString)
/*      */   {
/* 2108 */     return Pattern.matches(paramString, this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(CharSequence paramCharSequence)
/*      */   {
/* 2120 */     return indexOf(paramCharSequence.toString()) > -1;
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
/*      */   public String replaceFirst(String paramString1, String paramString2)
/*      */   {
/* 2165 */     return Pattern.compile(paramString1).matcher(this).replaceFirst(paramString2);
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
/*      */   public String replaceAll(String paramString1, String paramString2)
/*      */   {
/* 2210 */     return Pattern.compile(paramString1).matcher(this).replaceAll(paramString2);
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
/*      */   public String replace(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 2227 */     return Pattern.compile(paramCharSequence1.toString(), 16).matcher(this).replaceAll(Matcher.quoteReplacement(paramCharSequence2.toString()));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] split(String paramString, int paramInt)
/*      */   {
/* 2323 */     int i = 0;
/* 2324 */     if (((paramString.value.length == 1) && 
/* 2325 */       (".$|()[{^?*+\\".indexOf(i = paramString.charAt(0)) == -1)) || (
/* 2326 */       (paramString.length() == 2) && 
/* 2327 */       (paramString.charAt(0) == '\\') && 
/* 2328 */       (((i = paramString.charAt(1)) - '0' | 57 - i) < 0) && ((i - 97 | 122 - i) < 0) && ((i - 65 | 90 - i) < 0) && ((i < 55296) || (i > 57343))))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2334 */       int j = 0;
/* 2335 */       int k = 0;
/* 2336 */       int m = paramInt > 0 ? 1 : 0;
/* 2337 */       ArrayList localArrayList = new ArrayList();
/* 2338 */       while ((k = indexOf(i, j)) != -1) {
/* 2339 */         if ((m == 0) || (localArrayList.size() < paramInt - 1)) {
/* 2340 */           localArrayList.add(substring(j, k));
/* 2341 */           j = k + 1;
/*      */         }
/*      */         else {
/* 2344 */           localArrayList.add(substring(j, this.value.length));
/* 2345 */           j = this.value.length;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2350 */       if (j == 0) {
/* 2351 */         return new String[] { this };
/*      */       }
/*      */       
/* 2354 */       if ((m == 0) || (localArrayList.size() < paramInt)) {
/* 2355 */         localArrayList.add(substring(j, this.value.length));
/*      */       }
/*      */       
/* 2358 */       int n = localArrayList.size();
/* 2359 */       if (paramInt == 0) {
/* 2360 */         while ((n > 0) && (((String)localArrayList.get(n - 1)).length() == 0)) {
/* 2361 */           n--;
/*      */         }
/*      */       }
/* 2364 */       String[] arrayOfString = new String[n];
/* 2365 */       return (String[])localArrayList.subList(0, n).toArray(arrayOfString);
/*      */     }
/* 2367 */     return Pattern.compile(paramString).split(this, paramInt);
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
/*      */   public String[] split(String paramString)
/*      */   {
/* 2409 */     return split(paramString, 0);
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
/*      */   public static String join(CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 2438 */     Objects.requireNonNull(paramCharSequence);
/* 2439 */     Objects.requireNonNull(paramVarArgs);
/*      */     
/* 2441 */     StringJoiner localStringJoiner = new StringJoiner(paramCharSequence);
/* 2442 */     for (CharSequence localCharSequence : paramVarArgs) {
/* 2443 */       localStringJoiner.add(localCharSequence);
/*      */     }
/* 2445 */     return localStringJoiner.toString();
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
/*      */   public static String join(CharSequence paramCharSequence, Iterable<? extends CharSequence> paramIterable)
/*      */   {
/* 2487 */     Objects.requireNonNull(paramCharSequence);
/* 2488 */     Objects.requireNonNull(paramIterable);
/* 2489 */     StringJoiner localStringJoiner = new StringJoiner(paramCharSequence);
/* 2490 */     for (CharSequence localCharSequence : paramIterable) {
/* 2491 */       localStringJoiner.add(localCharSequence);
/*      */     }
/* 2493 */     return localStringJoiner.toString();
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
/*      */   public String toLowerCase(Locale paramLocale)
/*      */   {
/* 2549 */     if (paramLocale == null) {
/* 2550 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/* 2554 */     int j = this.value.length;
/*      */     
/*      */ 
/*      */ 
/* 2558 */     for (int i = 0; i < j;) {
/* 2559 */       int k = this.value[i];
/* 2560 */       if ((k >= 55296) && (k <= 56319))
/*      */       {
/* 2562 */         m = codePointAt(i);
/* 2563 */         if (m != Character.toLowerCase(m)) {
/*      */           break label99;
/*      */         }
/* 2566 */         i += Character.charCount(m);
/*      */       } else {
/* 2568 */         if (k != Character.toLowerCase(k)) {
/*      */           break label99;
/*      */         }
/* 2571 */         i++;
/*      */       }
/*      */     }
/* 2574 */     return this;
/*      */     
/*      */     label99:
/* 2577 */     Object localObject = new char[j];
/* 2578 */     int m = 0;
/*      */     
/*      */ 
/*      */ 
/* 2582 */     System.arraycopy(this.value, 0, localObject, 0, i);
/*      */     
/* 2584 */     String str = paramLocale.getLanguage();
/* 2585 */     int n = (str == "tr") || (str == "az") || (str == "lt") ? 1 : 0;
/*      */     
/*      */ 
/*      */     int i3;
/*      */     
/*      */ 
/* 2591 */     for (int i4 = i; i4 < j; i4 += i3) {
/* 2592 */       int i2 = this.value[i4];
/* 2593 */       if (((char)i2 >= 55296) && ((char)i2 <= 56319))
/*      */       {
/* 2595 */         i2 = codePointAt(i4);
/* 2596 */         i3 = Character.charCount(i2);
/*      */       } else {
/* 2598 */         i3 = 1; }
/*      */       int i1;
/* 2600 */       if ((n != 0) || (i2 == 931) || (i2 == 304))
/*      */       {
/*      */ 
/* 2603 */         i1 = ConditionalSpecialCasing.toLowerCaseEx(this, i4, paramLocale);
/*      */       } else {
/* 2605 */         i1 = Character.toLowerCase(i2);
/*      */       }
/* 2607 */       if ((i1 == -1) || (i1 >= 65536)) {
/*      */         char[] arrayOfChar1;
/* 2609 */         if (i1 == -1)
/*      */         {
/* 2611 */           arrayOfChar1 = ConditionalSpecialCasing.toLowerCaseCharArray(this, i4, paramLocale);
/* 2612 */         } else { if (i3 == 2) {
/* 2613 */             m += Character.toChars(i1, (char[])localObject, i4 + m) - i3;
/* 2614 */             continue;
/*      */           }
/* 2616 */           arrayOfChar1 = Character.toChars(i1);
/*      */         }
/*      */         
/*      */ 
/* 2620 */         int i5 = arrayOfChar1.length;
/* 2621 */         if (i5 > i3) {
/* 2622 */           char[] arrayOfChar2 = new char[localObject.length + i5 - i3];
/* 2623 */           System.arraycopy(localObject, 0, arrayOfChar2, 0, i4 + m);
/* 2624 */           localObject = arrayOfChar2;
/*      */         }
/* 2626 */         for (int i6 = 0; i6 < i5; i6++) {
/* 2627 */           localObject[(i4 + m + i6)] = arrayOfChar1[i6];
/*      */         }
/* 2629 */         m += i5 - i3;
/*      */       } else {
/* 2631 */         localObject[(i4 + m)] = ((char)i1);
/*      */       }
/*      */     }
/* 2634 */     return new String((char[])localObject, 0, j + m);
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
/*      */   public String toLowerCase()
/*      */   {
/* 2657 */     return toLowerCase(Locale.getDefault());
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
/*      */   public String toUpperCase(Locale paramLocale)
/*      */   {
/* 2709 */     if (paramLocale == null) {
/* 2710 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/* 2714 */     int j = this.value.length;
/*      */     
/*      */ 
/*      */ 
/* 2718 */     for (int i = 0; i < j;) {
/* 2719 */       k = this.value[i];
/*      */       int m;
/* 2721 */       if ((k >= 55296) && (k <= 56319))
/*      */       {
/* 2723 */         k = codePointAt(i);
/* 2724 */         m = Character.charCount(k);
/*      */       } else {
/* 2726 */         m = 1;
/*      */       }
/* 2728 */       int n = Character.toUpperCaseEx(k);
/* 2729 */       if ((n == -1) || (k != n)) {
/*      */         break label100;
/*      */       }
/*      */       
/* 2733 */       i += m;
/*      */     }
/* 2735 */     return this;
/*      */     
/*      */     label100:
/*      */     
/* 2739 */     int k = 0;
/* 2740 */     Object localObject = new char[j];
/*      */     
/*      */ 
/* 2743 */     System.arraycopy(this.value, 0, localObject, 0, i);
/*      */     
/* 2745 */     String str = paramLocale.getLanguage();
/* 2746 */     int i1 = (str == "tr") || (str == "az") || (str == "lt") ? 1 : 0;
/*      */     
/*      */ 
/*      */     int i4;
/*      */     
/*      */ 
/* 2752 */     for (int i5 = i; i5 < j; i5 += i4) {
/* 2753 */       int i3 = this.value[i5];
/* 2754 */       if (((char)i3 >= 55296) && ((char)i3 <= 56319))
/*      */       {
/* 2756 */         i3 = codePointAt(i5);
/* 2757 */         i4 = Character.charCount(i3);
/*      */       } else {
/* 2759 */         i4 = 1; }
/*      */       int i2;
/* 2761 */       if (i1 != 0) {
/* 2762 */         i2 = ConditionalSpecialCasing.toUpperCaseEx(this, i5, paramLocale);
/*      */       } else {
/* 2764 */         i2 = Character.toUpperCaseEx(i3);
/*      */       }
/* 2766 */       if ((i2 == -1) || (i2 >= 65536)) {
/*      */         char[] arrayOfChar1;
/* 2768 */         if (i2 == -1) {
/* 2769 */           if (i1 != 0)
/*      */           {
/* 2771 */             arrayOfChar1 = ConditionalSpecialCasing.toUpperCaseCharArray(this, i5, paramLocale);
/*      */           } else
/* 2773 */             arrayOfChar1 = Character.toUpperCaseCharArray(i3);
/*      */         } else {
/* 2775 */           if (i4 == 2) {
/* 2776 */             k += Character.toChars(i2, (char[])localObject, i5 + k) - i4;
/* 2777 */             continue;
/*      */           }
/* 2779 */           arrayOfChar1 = Character.toChars(i2);
/*      */         }
/*      */         
/*      */ 
/* 2783 */         int i6 = arrayOfChar1.length;
/* 2784 */         if (i6 > i4) {
/* 2785 */           char[] arrayOfChar2 = new char[localObject.length + i6 - i4];
/* 2786 */           System.arraycopy(localObject, 0, arrayOfChar2, 0, i5 + k);
/* 2787 */           localObject = arrayOfChar2;
/*      */         }
/* 2789 */         for (int i7 = 0; i7 < i6; i7++) {
/* 2790 */           localObject[(i5 + k + i7)] = arrayOfChar1[i7];
/*      */         }
/* 2792 */         k += i6 - i4;
/*      */       } else {
/* 2794 */         localObject[(i5 + k)] = ((char)i2);
/*      */       }
/*      */     }
/* 2797 */     return new String((char[])localObject, 0, j + k);
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
/*      */   public String toUpperCase()
/*      */   {
/* 2820 */     return toUpperCase(Locale.getDefault());
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
/*      */   public String trim()
/*      */   {
/* 2855 */     int i = this.value.length;
/* 2856 */     int j = 0;
/* 2857 */     char[] arrayOfChar = this.value;
/*      */     
/* 2859 */     while ((j < i) && (arrayOfChar[j] <= ' ')) {
/* 2860 */       j++;
/*      */     }
/* 2862 */     while ((j < i) && (arrayOfChar[(i - 1)] <= ' ')) {
/* 2863 */       i--;
/*      */     }
/* 2865 */     return (j > 0) || (i < this.value.length) ? substring(j, i) : this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2874 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public char[] toCharArray()
/*      */   {
/* 2886 */     char[] arrayOfChar = new char[this.value.length];
/* 2887 */     System.arraycopy(this.value, 0, arrayOfChar, 0, this.value.length);
/* 2888 */     return arrayOfChar;
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
/*      */   public static String format(String paramString, Object... paramVarArgs)
/*      */   {
/* 2927 */     return new Formatter().format(paramString, paramVarArgs).toString();
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
/*      */   public static String format(Locale paramLocale, String paramString, Object... paramVarArgs)
/*      */   {
/* 2968 */     return new Formatter(paramLocale).format(paramString, paramVarArgs).toString();
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
/*      */   public static String valueOf(Object paramObject)
/*      */   {
/* 2981 */     return paramObject == null ? "null" : paramObject.toString();
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
/*      */   public static String valueOf(char[] paramArrayOfChar)
/*      */   {
/* 2995 */     return new String(paramArrayOfChar);
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
/*      */   public static String valueOf(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 3019 */     return new String(paramArrayOfChar, paramInt1, paramInt2);
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
/*      */   public static String copyValueOf(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 3036 */     return new String(paramArrayOfChar, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String copyValueOf(char[] paramArrayOfChar)
/*      */   {
/* 3047 */     return new String(paramArrayOfChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String valueOf(boolean paramBoolean)
/*      */   {
/* 3059 */     return paramBoolean ? "true" : "false";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String valueOf(char paramChar)
/*      */   {
/* 3071 */     char[] arrayOfChar = { paramChar };
/* 3072 */     return new String(arrayOfChar, true);
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
/*      */   public static String valueOf(int paramInt)
/*      */   {
/* 3086 */     return Integer.toString(paramInt);
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
/*      */   public static String valueOf(long paramLong)
/*      */   {
/* 3100 */     return Long.toString(paramLong);
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
/*      */   public static String valueOf(float paramFloat)
/*      */   {
/* 3114 */     return Float.toString(paramFloat);
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
/*      */   public static String valueOf(double paramDouble)
/*      */   {
/* 3128 */     return Double.toString(paramDouble);
/*      */   }
/*      */   
/*      */   public native String intern();
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/String.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */