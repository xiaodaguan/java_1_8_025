/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Locale.Category;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MessageFormat
/*      */   extends Format
/*      */ {
/*      */   private static final long serialVersionUID = 6479157306784022952L;
/*      */   private Locale locale;
/*      */   
/*      */   public MessageFormat(String paramString)
/*      */   {
/*  361 */     this.locale = Locale.getDefault(Locale.Category.FORMAT);
/*  362 */     applyPattern(paramString);
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
/*      */   public MessageFormat(String paramString, Locale paramLocale)
/*      */   {
/*  379 */     this.locale = paramLocale;
/*  380 */     applyPattern(paramString);
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
/*      */   public void setLocale(Locale paramLocale)
/*      */   {
/*  401 */     this.locale = paramLocale;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Locale getLocale()
/*      */   {
/*  410 */     return this.locale;
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
/*      */   public void applyPattern(String paramString)
/*      */   {
/*  426 */     StringBuilder[] arrayOfStringBuilder = new StringBuilder[4];
/*      */     
/*      */ 
/*  429 */     arrayOfStringBuilder[0] = new StringBuilder();
/*      */     
/*  431 */     int i = 0;
/*  432 */     int j = 0;
/*  433 */     int k = 0;
/*  434 */     int m = 0;
/*  435 */     this.maxOffset = -1;
/*  436 */     for (int n = 0; n < paramString.length(); n++) {
/*  437 */       char c = paramString.charAt(n);
/*  438 */       if (i == 0) {
/*  439 */         if (c == '\'') {
/*  440 */           if ((n + 1 < paramString.length()) && 
/*  441 */             (paramString.charAt(n + 1) == '\'')) {
/*  442 */             arrayOfStringBuilder[i].append(c);
/*  443 */             n++;
/*      */           } else {
/*  445 */             k = k == 0 ? 1 : 0;
/*      */           }
/*  447 */         } else if ((c == '{') && (k == 0)) {
/*  448 */           i = 1;
/*  449 */           if (arrayOfStringBuilder[1] == null) {
/*  450 */             arrayOfStringBuilder[1] = new StringBuilder();
/*      */           }
/*      */         } else {
/*  453 */           arrayOfStringBuilder[i].append(c);
/*      */         }
/*      */       }
/*  456 */       else if (k != 0) {
/*  457 */         arrayOfStringBuilder[i].append(c);
/*  458 */         if (c == '\'') {
/*  459 */           k = 0;
/*      */         }
/*      */       } else {
/*  462 */         switch (c) {
/*      */         case ',': 
/*  464 */           if (i < 3) {
/*  465 */             if (arrayOfStringBuilder[(++i)] == null) {
/*  466 */               arrayOfStringBuilder[i] = new StringBuilder();
/*      */             }
/*      */           } else {
/*  469 */             arrayOfStringBuilder[i].append(c);
/*      */           }
/*  471 */           break;
/*      */         case '{': 
/*  473 */           m++;
/*  474 */           arrayOfStringBuilder[i].append(c);
/*  475 */           break;
/*      */         case '}': 
/*  477 */           if (m == 0) {
/*  478 */             i = 0;
/*  479 */             makeFormat(n, j, arrayOfStringBuilder);
/*  480 */             j++;
/*      */             
/*  482 */             arrayOfStringBuilder[1] = null;
/*  483 */             arrayOfStringBuilder[2] = null;
/*  484 */             arrayOfStringBuilder[3] = null;
/*      */           } else {
/*  486 */             m--;
/*  487 */             arrayOfStringBuilder[i].append(c);
/*      */           }
/*  489 */           break;
/*      */         
/*      */         case ' ': 
/*  492 */           if ((i != 2) || (arrayOfStringBuilder[2].length() > 0)) {
/*  493 */             arrayOfStringBuilder[i].append(c);
/*      */           }
/*      */           break;
/*      */         case '\'': 
/*  497 */           k = 1;
/*      */         
/*      */         default: 
/*  500 */           arrayOfStringBuilder[i].append(c);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     
/*  506 */     if ((m == 0) && (i != 0)) {
/*  507 */       this.maxOffset = -1;
/*  508 */       throw new IllegalArgumentException("Unmatched braces in the pattern.");
/*      */     }
/*  510 */     this.pattern = arrayOfStringBuilder[0].toString();
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
/*      */   public String toPattern()
/*      */   {
/*  523 */     int i = 0;
/*  524 */     StringBuilder localStringBuilder = new StringBuilder();
/*  525 */     for (int j = 0; j <= this.maxOffset; j++) {
/*  526 */       copyAndFixQuotes(this.pattern, i, this.offsets[j], localStringBuilder);
/*  527 */       i = this.offsets[j];
/*  528 */       localStringBuilder.append('{').append(this.argumentNumbers[j]);
/*  529 */       Format localFormat = this.formats[j];
/*  530 */       if (localFormat != null)
/*      */       {
/*  532 */         if ((localFormat instanceof NumberFormat)) {
/*  533 */           if (localFormat.equals(NumberFormat.getInstance(this.locale))) {
/*  534 */             localStringBuilder.append(",number");
/*  535 */           } else if (localFormat.equals(NumberFormat.getCurrencyInstance(this.locale))) {
/*  536 */             localStringBuilder.append(",number,currency");
/*  537 */           } else if (localFormat.equals(NumberFormat.getPercentInstance(this.locale))) {
/*  538 */             localStringBuilder.append(",number,percent");
/*  539 */           } else if (localFormat.equals(NumberFormat.getIntegerInstance(this.locale))) {
/*  540 */             localStringBuilder.append(",number,integer");
/*      */           }
/*  542 */           else if ((localFormat instanceof DecimalFormat)) {
/*  543 */             localStringBuilder.append(",number,").append(((DecimalFormat)localFormat).toPattern());
/*  544 */           } else if ((localFormat instanceof ChoiceFormat)) {
/*  545 */             localStringBuilder.append(",choice,").append(((ChoiceFormat)localFormat).toPattern());
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  550 */         else if ((localFormat instanceof DateFormat))
/*      */         {
/*  552 */           for (int k = 0; k < DATE_TIME_MODIFIERS.length; k++) {
/*  553 */             DateFormat localDateFormat = DateFormat.getDateInstance(DATE_TIME_MODIFIERS[k], this.locale);
/*      */             
/*  555 */             if (localFormat.equals(localDateFormat)) {
/*  556 */               localStringBuilder.append(",date");
/*  557 */               break;
/*      */             }
/*  559 */             localDateFormat = DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[k], this.locale);
/*      */             
/*  561 */             if (localFormat.equals(localDateFormat)) {
/*  562 */               localStringBuilder.append(",time");
/*  563 */               break;
/*      */             }
/*      */           }
/*  566 */           if (k >= DATE_TIME_MODIFIERS.length) {
/*  567 */             if ((localFormat instanceof SimpleDateFormat)) {
/*  568 */               localStringBuilder.append(",date,").append(((SimpleDateFormat)localFormat).toPattern());
/*      */             }
/*      */             
/*      */           }
/*  572 */           else if (k != 0) {
/*  573 */             localStringBuilder.append(',').append(DATE_TIME_MODIFIER_KEYWORDS[k]);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  578 */       localStringBuilder.append('}');
/*      */     }
/*  580 */     copyAndFixQuotes(this.pattern, i, this.pattern.length(), localStringBuilder);
/*  581 */     return localStringBuilder.toString();
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
/*      */   public void setFormatsByArgumentIndex(Format[] paramArrayOfFormat)
/*      */   {
/*  608 */     for (int i = 0; i <= this.maxOffset; i++) {
/*  609 */       int j = this.argumentNumbers[i];
/*  610 */       if (j < paramArrayOfFormat.length) {
/*  611 */         this.formats[i] = paramArrayOfFormat[j];
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
/*      */   public void setFormats(Format[] paramArrayOfFormat)
/*      */   {
/*  639 */     int i = paramArrayOfFormat.length;
/*  640 */     if (i > this.maxOffset + 1) {
/*  641 */       i = this.maxOffset + 1;
/*      */     }
/*  643 */     for (int j = 0; j < i; j++) {
/*  644 */       this.formats[j] = paramArrayOfFormat[j];
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
/*      */   public void setFormatByArgumentIndex(int paramInt, Format paramFormat)
/*      */   {
/*  667 */     for (int i = 0; i <= this.maxOffset; i++) {
/*  668 */       if (this.argumentNumbers[i] == paramInt) {
/*  669 */         this.formats[i] = paramFormat;
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
/*      */   public void setFormat(int paramInt, Format paramFormat)
/*      */   {
/*  692 */     this.formats[paramInt] = paramFormat;
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
/*      */   public Format[] getFormatsByArgumentIndex()
/*      */   {
/*  716 */     int i = -1;
/*  717 */     for (int j = 0; j <= this.maxOffset; j++) {
/*  718 */       if (this.argumentNumbers[j] > i) {
/*  719 */         i = this.argumentNumbers[j];
/*      */       }
/*      */     }
/*  722 */     Format[] arrayOfFormat = new Format[i + 1];
/*  723 */     for (int k = 0; k <= this.maxOffset; k++) {
/*  724 */       arrayOfFormat[this.argumentNumbers[k]] = this.formats[k];
/*      */     }
/*  726 */     return arrayOfFormat;
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
/*      */   public Format[] getFormats()
/*      */   {
/*  746 */     Format[] arrayOfFormat = new Format[this.maxOffset + 1];
/*  747 */     System.arraycopy(this.formats, 0, arrayOfFormat, 0, this.maxOffset + 1);
/*  748 */     return arrayOfFormat;
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
/*      */   public final StringBuffer format(Object[] paramArrayOfObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  821 */     return subformat(paramArrayOfObject, paramStringBuffer, paramFieldPosition, null);
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
/*      */   public static String format(String paramString, Object... paramVarArgs)
/*      */   {
/*  840 */     MessageFormat localMessageFormat = new MessageFormat(paramString);
/*  841 */     return localMessageFormat.format(paramVarArgs);
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
/*      */   public final StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  865 */     return subformat((Object[])paramObject, paramStringBuffer, paramFieldPosition, null);
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
/*      */   public AttributedCharacterIterator formatToCharacterIterator(Object paramObject)
/*      */   {
/*  904 */     StringBuffer localStringBuffer = new StringBuffer();
/*  905 */     ArrayList localArrayList = new ArrayList();
/*      */     
/*  907 */     if (paramObject == null) {
/*  908 */       throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
/*      */     }
/*      */     
/*  911 */     subformat((Object[])paramObject, localStringBuffer, null, localArrayList);
/*  912 */     if (localArrayList.size() == 0) {
/*  913 */       return createAttributedCharacterIterator("");
/*      */     }
/*  915 */     return createAttributedCharacterIterator(
/*  916 */       (AttributedCharacterIterator[])localArrayList.toArray(
/*  917 */       new AttributedCharacterIterator[localArrayList.size()]));
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
/*      */   public Object[] parse(String paramString, ParsePosition paramParsePosition)
/*      */   {
/*  952 */     if (paramString == null) {
/*  953 */       Object[] arrayOfObject1 = new Object[0];
/*  954 */       return arrayOfObject1;
/*      */     }
/*      */     
/*  957 */     int i = -1;
/*  958 */     for (int j = 0; j <= this.maxOffset; j++) {
/*  959 */       if (this.argumentNumbers[j] > i) {
/*  960 */         i = this.argumentNumbers[j];
/*      */       }
/*      */     }
/*  963 */     Object[] arrayOfObject2 = new Object[i + 1];
/*      */     
/*  965 */     int k = 0;
/*  966 */     int m = paramParsePosition.index;
/*  967 */     ParsePosition localParsePosition = new ParsePosition(0);
/*  968 */     for (int n = 0; n <= this.maxOffset; n++)
/*      */     {
/*  970 */       int i1 = this.offsets[n] - k;
/*  971 */       if ((i1 == 0) || (this.pattern.regionMatches(k, paramString, m, i1)))
/*      */       {
/*  973 */         m += i1;
/*  974 */         k += i1;
/*      */       } else {
/*  976 */         paramParsePosition.errorIndex = m;
/*  977 */         return null;
/*      */       }
/*      */       
/*      */ 
/*  981 */       if (this.formats[n] == null)
/*      */       {
/*      */ 
/*      */ 
/*  985 */         int i2 = n != this.maxOffset ? this.offsets[(n + 1)] : this.pattern.length();
/*      */         
/*      */         int i3;
/*  988 */         if (k >= i2) {
/*  989 */           i3 = paramString.length();
/*      */         } else {
/*  991 */           i3 = paramString.indexOf(this.pattern.substring(k, i2), m);
/*      */         }
/*      */         
/*      */ 
/*  995 */         if (i3 < 0) {
/*  996 */           paramParsePosition.errorIndex = m;
/*  997 */           return null;
/*      */         }
/*  999 */         String str = paramString.substring(m, i3);
/* 1000 */         if (!str.equals("{" + this.argumentNumbers[n] + "}"))
/*      */         {
/* 1002 */           arrayOfObject2[this.argumentNumbers[n]] = paramString.substring(m, i3); }
/* 1003 */         m = i3;
/*      */       }
/*      */       else {
/* 1006 */         localParsePosition.index = m;
/* 1007 */         arrayOfObject2[this.argumentNumbers[n]] = this.formats[n]
/* 1008 */           .parseObject(paramString, localParsePosition);
/* 1009 */         if (localParsePosition.index == m) {
/* 1010 */           paramParsePosition.errorIndex = m;
/* 1011 */           return null;
/*      */         }
/* 1013 */         m = localParsePosition.index;
/*      */       }
/*      */     }
/* 1016 */     n = this.pattern.length() - k;
/* 1017 */     if ((n == 0) || (this.pattern.regionMatches(k, paramString, m, n)))
/*      */     {
/* 1019 */       paramParsePosition.index = (m + n);
/*      */     } else {
/* 1021 */       paramParsePosition.errorIndex = m;
/* 1022 */       return null;
/*      */     }
/* 1024 */     return arrayOfObject2;
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
/*      */   public Object[] parse(String paramString)
/*      */     throws ParseException
/*      */   {
/* 1041 */     ParsePosition localParsePosition = new ParsePosition(0);
/* 1042 */     Object[] arrayOfObject = parse(paramString, localParsePosition);
/* 1043 */     if (localParsePosition.index == 0) {
/* 1044 */       throw new ParseException("MessageFormat parse error!", localParsePosition.errorIndex);
/*      */     }
/* 1046 */     return arrayOfObject;
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
/*      */   public Object parseObject(String paramString, ParsePosition paramParsePosition)
/*      */   {
/* 1074 */     return parse(paramString, paramParsePosition);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/* 1083 */     MessageFormat localMessageFormat = (MessageFormat)super.clone();
/*      */     
/*      */ 
/* 1086 */     localMessageFormat.formats = ((Format[])this.formats.clone());
/* 1087 */     for (int i = 0; i < this.formats.length; i++) {
/* 1088 */       if (this.formats[i] != null) {
/* 1089 */         localMessageFormat.formats[i] = ((Format)this.formats[i].clone());
/*      */       }
/*      */     }
/* 1092 */     localMessageFormat.offsets = ((int[])this.offsets.clone());
/* 1093 */     localMessageFormat.argumentNumbers = ((int[])this.argumentNumbers.clone());
/*      */     
/* 1095 */     return localMessageFormat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1102 */     if (this == paramObject)
/* 1103 */       return true;
/* 1104 */     if ((paramObject == null) || (getClass() != paramObject.getClass()))
/* 1105 */       return false;
/* 1106 */     MessageFormat localMessageFormat = (MessageFormat)paramObject;
/* 1107 */     if ((this.maxOffset == localMessageFormat.maxOffset) && 
/* 1108 */       (this.pattern.equals(localMessageFormat.pattern)) && (((this.locale != null) && 
/* 1109 */       (this.locale.equals(localMessageFormat.locale))) || ((this.locale == null) && (localMessageFormat.locale == null)))) {}
/*      */     
/*      */ 
/*      */ 
/* 1113 */     return (Arrays.equals(this.offsets, localMessageFormat.offsets)) && (Arrays.equals(this.argumentNumbers, localMessageFormat.argumentNumbers)) && (Arrays.equals(this.formats, localMessageFormat.formats));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1120 */     return this.pattern.hashCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class Field
/*      */     extends Format.Field
/*      */   {
/*      */     private static final long serialVersionUID = 7899943957617360810L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected Field(String paramString)
/*      */     {
/* 1142 */       super();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected Object readResolve()
/*      */       throws InvalidObjectException
/*      */     {
/* 1153 */       if (getClass() != Field.class) {
/* 1154 */         throw new InvalidObjectException("subclass didn't correctly implement readResolve");
/*      */       }
/*      */       
/* 1157 */       return ARGUMENT;
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
/* 1171 */     public static final Field ARGUMENT = new Field("message argument field");
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
/* 1188 */   private String pattern = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int INITIAL_FORMATS = 10;
/*      */   
/*      */ 
/*      */ 
/* 1197 */   private Format[] formats = new Format[10];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1204 */   private int[] offsets = new int[10];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1212 */   private int[] argumentNumbers = new int[10];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1221 */   private int maxOffset = -1;
/*      */   
/*      */   private static final int SEG_RAW = 0;
/*      */   
/*      */   private static final int SEG_INDEX = 1;
/*      */   
/*      */   private static final int SEG_TYPE = 2;
/*      */   
/*      */   private static final int SEG_MODIFIER = 3;
/*      */   
/*      */   private static final int TYPE_NULL = 0;
/*      */   private static final int TYPE_NUMBER = 1;
/*      */   private static final int TYPE_DATE = 2;
/*      */   private static final int TYPE_TIME = 3;
/*      */   private static final int TYPE_CHOICE = 4;
/*      */   
/*      */   private StringBuffer subformat(Object[] paramArrayOfObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition, List<AttributedCharacterIterator> paramList)
/*      */   {
/* 1239 */     int i = 0;
/* 1240 */     int j = paramStringBuffer.length();
/* 1241 */     for (int k = 0; k <= this.maxOffset; k++) {
/* 1242 */       paramStringBuffer.append(this.pattern.substring(i, this.offsets[k]));
/* 1243 */       i = this.offsets[k];
/* 1244 */       int m = this.argumentNumbers[k];
/* 1245 */       if ((paramArrayOfObject == null) || (m >= paramArrayOfObject.length)) {
/* 1246 */         paramStringBuffer.append('{').append(m).append('}');
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/* 1254 */         Object localObject1 = paramArrayOfObject[m];
/* 1255 */         String str = null;
/* 1256 */         Object localObject2 = null;
/* 1257 */         if (localObject1 == null) {
/* 1258 */           str = "null";
/* 1259 */         } else if (this.formats[k] != null) {
/* 1260 */           localObject2 = this.formats[k];
/* 1261 */           if ((localObject2 instanceof ChoiceFormat)) {
/* 1262 */             str = this.formats[k].format(localObject1);
/* 1263 */             if (str.indexOf('{') >= 0) {
/* 1264 */               localObject2 = new MessageFormat(str, this.locale);
/* 1265 */               localObject1 = paramArrayOfObject;
/* 1266 */               str = null;
/*      */             }
/*      */           }
/* 1269 */         } else if ((localObject1 instanceof Number))
/*      */         {
/* 1271 */           localObject2 = NumberFormat.getInstance(this.locale);
/* 1272 */         } else if ((localObject1 instanceof Date))
/*      */         {
/* 1274 */           localObject2 = DateFormat.getDateTimeInstance(3, 3, this.locale);
/*      */         }
/* 1276 */         else if ((localObject1 instanceof String)) {
/* 1277 */           str = (String)localObject1;
/*      */         }
/*      */         else {
/* 1280 */           str = localObject1.toString();
/* 1281 */           if (str == null) { str = "null";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1288 */         if (paramList != null)
/*      */         {
/*      */ 
/* 1291 */           if (j != paramStringBuffer.length()) {
/* 1292 */             paramList.add(
/* 1293 */               createAttributedCharacterIterator(paramStringBuffer
/* 1294 */               .substring(j)));
/* 1295 */             j = paramStringBuffer.length();
/*      */           }
/* 1297 */           if (localObject2 != null)
/*      */           {
/* 1299 */             AttributedCharacterIterator localAttributedCharacterIterator = ((Format)localObject2).formatToCharacterIterator(localObject1);
/*      */             
/* 1301 */             append(paramStringBuffer, localAttributedCharacterIterator);
/* 1302 */             if (j != paramStringBuffer.length()) {
/* 1303 */               paramList.add(
/* 1304 */                 createAttributedCharacterIterator(localAttributedCharacterIterator, Field.ARGUMENT, 
/*      */                 
/* 1306 */                 Integer.valueOf(m)));
/* 1307 */               j = paramStringBuffer.length();
/*      */             }
/* 1309 */             str = null;
/*      */           }
/* 1311 */           if ((str != null) && (str.length() > 0)) {
/* 1312 */             paramStringBuffer.append(str);
/* 1313 */             paramList.add(
/* 1314 */               createAttributedCharacterIterator(str, Field.ARGUMENT, 
/*      */               
/* 1316 */               Integer.valueOf(m)));
/* 1317 */             j = paramStringBuffer.length();
/*      */           }
/*      */         }
/*      */         else {
/* 1321 */           if (localObject2 != null) {
/* 1322 */             str = ((Format)localObject2).format(localObject1);
/*      */           }
/* 1324 */           j = paramStringBuffer.length();
/* 1325 */           paramStringBuffer.append(str);
/* 1326 */           if ((k == 0) && (paramFieldPosition != null) && (Field.ARGUMENT.equals(paramFieldPosition
/* 1327 */             .getFieldAttribute()))) {
/* 1328 */             paramFieldPosition.setBeginIndex(j);
/* 1329 */             paramFieldPosition.setEndIndex(paramStringBuffer.length());
/*      */           }
/* 1331 */           j = paramStringBuffer.length();
/*      */         }
/*      */       }
/*      */     }
/* 1335 */     paramStringBuffer.append(this.pattern.substring(i, this.pattern.length()));
/* 1336 */     if ((paramList != null) && (j != paramStringBuffer.length())) {
/* 1337 */       paramList.add(createAttributedCharacterIterator(paramStringBuffer
/* 1338 */         .substring(j)));
/*      */     }
/* 1340 */     return paramStringBuffer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void append(StringBuffer paramStringBuffer, CharacterIterator paramCharacterIterator)
/*      */   {
/* 1348 */     if (paramCharacterIterator.first() != 65535)
/*      */     {
/*      */ 
/* 1351 */       paramStringBuffer.append(paramCharacterIterator.first());
/* 1352 */       char c; while ((c = paramCharacterIterator.next()) != 65535) {
/* 1353 */         paramStringBuffer.append(c);
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
/* 1371 */   private static final String[] TYPE_KEYWORDS = { "", "number", "date", "time", "choice" };
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_DEFAULT = 0;
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_CURRENCY = 1;
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_PERCENT = 2;
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_INTEGER = 3;
/*      */   
/* 1385 */   private static final String[] NUMBER_MODIFIER_KEYWORDS = { "", "currency", "percent", "integer" };
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_SHORT = 1;
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_MEDIUM = 2;
/*      */   
/*      */ 
/*      */   private static final int MODIFIER_LONG = 3;
/*      */   
/*      */   private static final int MODIFIER_FULL = 4;
/*      */   
/* 1398 */   private static final String[] DATE_TIME_MODIFIER_KEYWORDS = { "", "short", "medium", "long", "full" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1407 */   private static final int[] DATE_TIME_MODIFIERS = { 2, 3, 2, 1, 0 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void makeFormat(int paramInt1, int paramInt2, StringBuilder[] paramArrayOfStringBuilder)
/*      */   {
/* 1418 */     String[] arrayOfString = new String[paramArrayOfStringBuilder.length];
/* 1419 */     for (int i = 0; i < paramArrayOfStringBuilder.length; i++) {
/* 1420 */       StringBuilder localStringBuilder = paramArrayOfStringBuilder[i];
/* 1421 */       arrayOfString[i] = (localStringBuilder != null ? localStringBuilder.toString() : "");
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1427 */       i = Integer.parseInt(arrayOfString[1]);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 1429 */       throw new IllegalArgumentException("can't parse argument number: " + arrayOfString[1], localNumberFormatException);
/*      */     }
/*      */     
/* 1432 */     if (i < 0) {
/* 1433 */       throw new IllegalArgumentException("negative argument number: " + i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1438 */     if (paramInt2 >= this.formats.length) {
/* 1439 */       j = this.formats.length * 2;
/* 1440 */       localObject = new Format[j];
/* 1441 */       int[] arrayOfInt1 = new int[j];
/* 1442 */       int[] arrayOfInt2 = new int[j];
/* 1443 */       System.arraycopy(this.formats, 0, localObject, 0, this.maxOffset + 1);
/* 1444 */       System.arraycopy(this.offsets, 0, arrayOfInt1, 0, this.maxOffset + 1);
/* 1445 */       System.arraycopy(this.argumentNumbers, 0, arrayOfInt2, 0, this.maxOffset + 1);
/* 1446 */       this.formats = ((Format[])localObject);
/* 1447 */       this.offsets = arrayOfInt1;
/* 1448 */       this.argumentNumbers = arrayOfInt2;
/*      */     }
/* 1450 */     int j = this.maxOffset;
/* 1451 */     this.maxOffset = paramInt2;
/* 1452 */     this.offsets[paramInt2] = arrayOfString[0].length();
/* 1453 */     this.argumentNumbers[paramInt2] = i;
/*      */     
/*      */ 
/* 1456 */     Object localObject = null;
/* 1457 */     if (arrayOfString[2].length() != 0) {
/* 1458 */       int k = findKeyword(arrayOfString[2], TYPE_KEYWORDS);
/* 1459 */       switch (k)
/*      */       {
/*      */       case 0: 
/*      */         break;
/*      */       
/*      */ 
/*      */       case 1: 
/* 1466 */         switch (findKeyword(arrayOfString[3], NUMBER_MODIFIER_KEYWORDS)) {
/*      */         case 0: 
/* 1468 */           localObject = NumberFormat.getInstance(this.locale);
/* 1469 */           break;
/*      */         case 1: 
/* 1471 */           localObject = NumberFormat.getCurrencyInstance(this.locale);
/* 1472 */           break;
/*      */         case 2: 
/* 1474 */           localObject = NumberFormat.getPercentInstance(this.locale);
/* 1475 */           break;
/*      */         case 3: 
/* 1477 */           localObject = NumberFormat.getIntegerInstance(this.locale);
/* 1478 */           break;
/*      */         default: 
/*      */           try
/*      */           {
/* 1482 */             localObject = new DecimalFormat(arrayOfString[3], DecimalFormatSymbols.getInstance(this.locale));
/*      */           } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 1484 */             this.maxOffset = j;
/* 1485 */             throw localIllegalArgumentException1;
/*      */           }
/*      */         }
/*      */         
/*      */         
/*      */         break;
/*      */       case 2: 
/*      */       case 3: 
/* 1493 */         int m = findKeyword(arrayOfString[3], DATE_TIME_MODIFIER_KEYWORDS);
/* 1494 */         if ((m >= 0) && (m < DATE_TIME_MODIFIER_KEYWORDS.length)) {
/* 1495 */           if (k == 2) {
/* 1496 */             localObject = DateFormat.getDateInstance(DATE_TIME_MODIFIERS[m], this.locale);
/*      */           }
/*      */           else {
/* 1499 */             localObject = DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[m], this.locale);
/*      */           }
/*      */         }
/*      */         else {
/*      */           try
/*      */           {
/* 1505 */             localObject = new SimpleDateFormat(arrayOfString[3], this.locale);
/*      */           } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 1507 */             this.maxOffset = j;
/* 1508 */             throw localIllegalArgumentException2;
/*      */           }
/*      */         }
/*      */         
/*      */         break;
/*      */       case 4: 
/*      */         try
/*      */         {
/* 1516 */           localObject = new ChoiceFormat(arrayOfString[3]);
/*      */         } catch (Exception localException) {
/* 1518 */           this.maxOffset = j;
/* 1519 */           throw new IllegalArgumentException("Choice Pattern incorrect: " + arrayOfString[3], localException);
/*      */         }
/*      */       
/*      */ 
/*      */ 
/*      */       default: 
/* 1525 */         this.maxOffset = j;
/* 1526 */         throw new IllegalArgumentException("unknown format type: " + arrayOfString[2]);
/*      */       }
/*      */       
/*      */     }
/* 1530 */     this.formats[paramInt2] = localObject;
/*      */   }
/*      */   
/*      */   private static final int findKeyword(String paramString, String[] paramArrayOfString) {
/* 1534 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 1535 */       if (paramString.equals(paramArrayOfString[i])) {
/* 1536 */         return i;
/*      */       }
/*      */     }
/*      */     
/* 1540 */     String str = paramString.trim().toLowerCase(Locale.ROOT);
/* 1541 */     if (str != paramString) {
/* 1542 */       for (int j = 0; j < paramArrayOfString.length; j++) {
/* 1543 */         if (str.equals(paramArrayOfString[j]))
/* 1544 */           return j;
/*      */       }
/*      */     }
/* 1547 */     return -1;
/*      */   }
/*      */   
/*      */   private static final void copyAndFixQuotes(String paramString, int paramInt1, int paramInt2, StringBuilder paramStringBuilder)
/*      */   {
/* 1552 */     int i = 0;
/*      */     
/* 1554 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 1555 */       char c = paramString.charAt(j);
/* 1556 */       if (c == '{') {
/* 1557 */         if (i == 0) {
/* 1558 */           paramStringBuilder.append('\'');
/* 1559 */           i = 1;
/*      */         }
/* 1561 */         paramStringBuilder.append(c);
/* 1562 */       } else if (c == '\'') {
/* 1563 */         paramStringBuilder.append("''");
/*      */       } else {
/* 1565 */         if (i != 0) {
/* 1566 */           paramStringBuilder.append('\'');
/* 1567 */           i = 0;
/*      */         }
/* 1569 */         paramStringBuilder.append(c);
/*      */       }
/*      */     }
/* 1572 */     if (i != 0) {
/* 1573 */       paramStringBuilder.append('\'');
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1583 */     paramObjectInputStream.defaultReadObject();
/* 1584 */     int i = (this.maxOffset >= -1) && (this.formats.length > this.maxOffset) && (this.offsets.length > this.maxOffset) && (this.argumentNumbers.length > this.maxOffset) ? 1 : 0;
/*      */     
/*      */ 
/*      */ 
/* 1588 */     if (i != 0) {
/* 1589 */       int j = this.pattern.length() + 1;
/* 1590 */       for (int k = this.maxOffset; k >= 0; k--) {
/* 1591 */         if ((this.offsets[k] < 0) || (this.offsets[k] > j)) {
/* 1592 */           i = 0;
/* 1593 */           break;
/*      */         }
/* 1595 */         j = this.offsets[k];
/*      */       }
/*      */     }
/*      */     
/* 1599 */     if (i == 0) {
/* 1600 */       throw new InvalidObjectException("Could not reconstruct MessageFormat from corrupt stream.");
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/MessageFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */