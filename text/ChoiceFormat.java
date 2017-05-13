/*     */ package java.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChoiceFormat
/*     */   extends NumberFormat
/*     */ {
/*     */   private static final long serialVersionUID = 1795184449645032964L;
/*     */   private double[] choiceLimits;
/*     */   private String[] choiceFormats;
/*     */   static final long SIGN = Long.MIN_VALUE;
/*     */   static final long EXPONENT = 9218868437227405312L;
/*     */   static final long POSITIVEINFINITY = 9218868437227405312L;
/*     */   
/*     */   public void applyPattern(String paramString)
/*     */   {
/* 177 */     StringBuffer[] arrayOfStringBuffer = new StringBuffer[2];
/* 178 */     for (int i = 0; i < arrayOfStringBuffer.length; i++) {
/* 179 */       arrayOfStringBuffer[i] = new StringBuffer();
/*     */     }
/* 181 */     double[] arrayOfDouble = new double[30];
/* 182 */     String[] arrayOfString = new String[30];
/* 183 */     int j = 0;
/* 184 */     int k = 0;
/* 185 */     double d1 = 0.0D;
/* 186 */     double d2 = NaN.0D;
/* 187 */     int m = 0;
/* 188 */     for (int n = 0; n < paramString.length(); n++) {
/* 189 */       char c = paramString.charAt(n);
/* 190 */       if (c == '\'')
/*     */       {
/* 192 */         if ((n + 1 < paramString.length()) && (paramString.charAt(n + 1) == c)) {
/* 193 */           arrayOfStringBuffer[k].append(c);
/* 194 */           n++;
/*     */         } else {
/* 196 */           m = m == 0 ? 1 : 0;
/*     */         }
/* 198 */       } else if (m != 0) {
/* 199 */         arrayOfStringBuffer[k].append(c);
/* 200 */       } else if ((c == '<') || (c == '#') || (c == '≤')) {
/* 201 */         if (arrayOfStringBuffer[0].length() == 0) {
/* 202 */           throw new IllegalArgumentException();
/*     */         }
/*     */         try {
/* 205 */           String str = arrayOfStringBuffer[0].toString();
/* 206 */           if (str.equals("∞")) {
/* 207 */             d1 = Double.POSITIVE_INFINITY;
/* 208 */           } else if (str.equals("-∞")) {
/* 209 */             d1 = Double.NEGATIVE_INFINITY;
/*     */           } else {
/* 211 */             d1 = Double.valueOf(arrayOfStringBuffer[0].toString()).doubleValue();
/*     */           }
/*     */         } catch (Exception localException) {
/* 214 */           throw new IllegalArgumentException();
/*     */         }
/* 216 */         if ((c == '<') && (d1 != Double.POSITIVE_INFINITY) && (d1 != Double.NEGATIVE_INFINITY))
/*     */         {
/* 218 */           d1 = nextDouble(d1);
/*     */         }
/* 220 */         if (d1 <= d2) {
/* 221 */           throw new IllegalArgumentException();
/*     */         }
/* 223 */         arrayOfStringBuffer[0].setLength(0);
/* 224 */         k = 1;
/* 225 */       } else if (c == '|') {
/* 226 */         if (j == arrayOfDouble.length) {
/* 227 */           arrayOfDouble = doubleArraySize(arrayOfDouble);
/* 228 */           arrayOfString = doubleArraySize(arrayOfString);
/*     */         }
/* 230 */         arrayOfDouble[j] = d1;
/* 231 */         arrayOfString[j] = arrayOfStringBuffer[1].toString();
/* 232 */         j++;
/* 233 */         d2 = d1;
/* 234 */         arrayOfStringBuffer[1].setLength(0);
/* 235 */         k = 0;
/*     */       } else {
/* 237 */         arrayOfStringBuffer[k].append(c);
/*     */       }
/*     */     }
/*     */     
/* 241 */     if (k == 1) {
/* 242 */       if (j == arrayOfDouble.length) {
/* 243 */         arrayOfDouble = doubleArraySize(arrayOfDouble);
/* 244 */         arrayOfString = doubleArraySize(arrayOfString);
/*     */       }
/* 246 */       arrayOfDouble[j] = d1;
/* 247 */       arrayOfString[j] = arrayOfStringBuffer[1].toString();
/* 248 */       j++;
/*     */     }
/* 250 */     this.choiceLimits = new double[j];
/* 251 */     System.arraycopy(arrayOfDouble, 0, this.choiceLimits, 0, j);
/* 252 */     this.choiceFormats = new String[j];
/* 253 */     System.arraycopy(arrayOfString, 0, this.choiceFormats, 0, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toPattern()
/*     */   {
/* 262 */     StringBuffer localStringBuffer = new StringBuffer();
/* 263 */     for (int i = 0; i < this.choiceLimits.length; i++) {
/* 264 */       if (i != 0) {
/* 265 */         localStringBuffer.append('|');
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 270 */       double d1 = previousDouble(this.choiceLimits[i]);
/* 271 */       double d2 = Math.abs(Math.IEEEremainder(this.choiceLimits[i], 1.0D));
/* 272 */       double d3 = Math.abs(Math.IEEEremainder(d1, 1.0D));
/*     */       
/* 274 */       if (d2 < d3) {
/* 275 */         localStringBuffer.append("" + this.choiceLimits[i]);
/* 276 */         localStringBuffer.append('#');
/*     */       } else {
/* 278 */         if (this.choiceLimits[i] == Double.POSITIVE_INFINITY) {
/* 279 */           localStringBuffer.append("∞");
/* 280 */         } else if (this.choiceLimits[i] == Double.NEGATIVE_INFINITY) {
/* 281 */           localStringBuffer.append("-∞");
/*     */         } else {
/* 283 */           localStringBuffer.append("" + d1);
/*     */         }
/* 285 */         localStringBuffer.append('<');
/*     */       }
/*     */       
/*     */ 
/* 289 */       String str = this.choiceFormats[i];
/*     */       
/*     */ 
/*     */ 
/* 293 */       int j = (str.indexOf('<') >= 0) || (str.indexOf('#') >= 0) || (str.indexOf('≤') >= 0) || (str.indexOf('|') >= 0) ? 1 : 0;
/* 294 */       if (j != 0) localStringBuffer.append('\'');
/* 295 */       if (str.indexOf('\'') < 0) { localStringBuffer.append(str);
/*     */       } else {
/* 297 */         for (int k = 0; k < str.length(); k++) {
/* 298 */           char c = str.charAt(k);
/* 299 */           localStringBuffer.append(c);
/* 300 */           if (c == '\'') localStringBuffer.append(c);
/*     */         }
/*     */       }
/* 303 */       if (j != 0) localStringBuffer.append('\'');
/*     */     }
/* 305 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChoiceFormat(String paramString)
/*     */   {
/* 315 */     applyPattern(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChoiceFormat(double[] paramArrayOfDouble, String[] paramArrayOfString)
/*     */   {
/* 326 */     setChoices(paramArrayOfDouble, paramArrayOfString);
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
/*     */   public void setChoices(double[] paramArrayOfDouble, String[] paramArrayOfString)
/*     */   {
/* 344 */     if (paramArrayOfDouble.length != paramArrayOfString.length) {
/* 345 */       throw new IllegalArgumentException("Array and limit arrays must be of the same length.");
/*     */     }
/*     */     
/* 348 */     this.choiceLimits = Arrays.copyOf(paramArrayOfDouble, paramArrayOfDouble.length);
/* 349 */     this.choiceFormats = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double[] getLimits()
/*     */   {
/* 357 */     double[] arrayOfDouble = Arrays.copyOf(this.choiceLimits, this.choiceLimits.length);
/* 358 */     return arrayOfDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getFormats()
/*     */   {
/* 366 */     Object[] arrayOfObject = Arrays.copyOf(this.choiceFormats, this.choiceFormats.length);
/* 367 */     return arrayOfObject;
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
/*     */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*     */   {
/* 381 */     return format(paramLong, paramStringBuffer, paramFieldPosition);
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
/*     */   public StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*     */   {
/* 394 */     for (int i = 0; i < this.choiceLimits.length; i++) {
/* 395 */       if (paramDouble < this.choiceLimits[i]) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 400 */     i--;
/* 401 */     if (i < 0) { i = 0;
/*     */     }
/* 403 */     return paramStringBuffer.append(this.choiceFormats[i]);
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
/*     */   public Number parse(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 420 */     int i = paramParsePosition.index;
/* 421 */     int j = i;
/* 422 */     double d1 = NaN.0D;
/* 423 */     double d2 = 0.0D;
/* 424 */     for (int k = 0; k < this.choiceFormats.length; k++) {
/* 425 */       String str = this.choiceFormats[k];
/* 426 */       if (paramString.regionMatches(i, str, 0, str.length())) {
/* 427 */         paramParsePosition.index = (i + str.length());
/* 428 */         d2 = this.choiceLimits[k];
/* 429 */         if (paramParsePosition.index > j) {
/* 430 */           j = paramParsePosition.index;
/* 431 */           d1 = d2;
/* 432 */           if (j == paramString.length()) break;
/*     */         }
/*     */       }
/*     */     }
/* 436 */     paramParsePosition.index = j;
/* 437 */     if (paramParsePosition.index == i) {
/* 438 */       paramParsePosition.errorIndex = j;
/*     */     }
/* 440 */     return new Double(d1);
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
/*     */   public static final double nextDouble(double paramDouble)
/*     */   {
/* 453 */     return nextDouble(paramDouble, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final double previousDouble(double paramDouble)
/*     */   {
/* 465 */     return nextDouble(paramDouble, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 473 */     ChoiceFormat localChoiceFormat = (ChoiceFormat)super.clone();
/*     */     
/* 475 */     localChoiceFormat.choiceLimits = ((double[])this.choiceLimits.clone());
/* 476 */     localChoiceFormat.choiceFormats = ((String[])this.choiceFormats.clone());
/* 477 */     return localChoiceFormat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 484 */     int i = this.choiceLimits.length;
/* 485 */     if (this.choiceFormats.length > 0)
/*     */     {
/* 487 */       i ^= this.choiceFormats[(this.choiceFormats.length - 1)].hashCode();
/*     */     }
/* 489 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 496 */     if (paramObject == null) return false;
/* 497 */     if (this == paramObject)
/* 498 */       return true;
/* 499 */     if (getClass() != paramObject.getClass())
/* 500 */       return false;
/* 501 */     ChoiceFormat localChoiceFormat = (ChoiceFormat)paramObject;
/*     */     
/* 503 */     return (Arrays.equals(this.choiceLimits, localChoiceFormat.choiceLimits)) && (Arrays.equals(this.choiceFormats, localChoiceFormat.choiceFormats));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 512 */     paramObjectInputStream.defaultReadObject();
/* 513 */     if (this.choiceLimits.length != this.choiceFormats.length) {
/* 514 */       throw new InvalidObjectException("limits and format arrays of different length.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double nextDouble(double paramDouble, boolean paramBoolean)
/*     */   {
/* 588 */     if (Double.isNaN(paramDouble)) {
/* 589 */       return paramDouble;
/*     */     }
/*     */     
/*     */ 
/* 593 */     if (paramDouble == 0.0D) {
/* 594 */       double d = Double.longBitsToDouble(1L);
/* 595 */       if (paramBoolean) {
/* 596 */         return d;
/*     */       }
/* 598 */       return -d;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 605 */     long l1 = Double.doubleToLongBits(paramDouble);
/*     */     
/*     */ 
/* 608 */     long l2 = l1 & 0x7FFFFFFFFFFFFFFF;
/*     */     
/*     */ 
/* 611 */     if (l1 > 0L == paramBoolean) {
/* 612 */       if (l2 != 9218868437227405312L) {
/* 613 */         l2 += 1L;
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 618 */       l2 -= 1L;
/*     */     }
/*     */     
/*     */ 
/* 622 */     long l3 = l1 & 0x8000000000000000;
/* 623 */     return Double.longBitsToDouble(l2 | l3);
/*     */   }
/*     */   
/*     */   private static double[] doubleArraySize(double[] paramArrayOfDouble) {
/* 627 */     int i = paramArrayOfDouble.length;
/* 628 */     double[] arrayOfDouble = new double[i * 2];
/* 629 */     System.arraycopy(paramArrayOfDouble, 0, arrayOfDouble, 0, i);
/* 630 */     return arrayOfDouble;
/*     */   }
/*     */   
/*     */   private String[] doubleArraySize(String[] paramArrayOfString) {
/* 634 */     int i = paramArrayOfString.length;
/* 635 */     String[] arrayOfString = new String[i * 2];
/* 636 */     System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, i);
/* 637 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/ChoiceFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */