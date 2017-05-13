/*     */ package java.lang;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
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
/*     */ public final class StringBuffer
/*     */   extends AbstractStringBuilder
/*     */   implements Serializable, CharSequence
/*     */ {
/*     */   private transient char[] toStringCache;
/*     */   static final long serialVersionUID = 3388685877147921107L;
/*     */   
/*     */   public StringBuffer()
/*     */   {
/* 116 */     super(16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer(int paramInt)
/*     */   {
/* 128 */     super(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer(String paramString)
/*     */   {
/* 139 */     super(paramString.length() + 16);
/* 140 */     append(paramString);
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
/*     */   public StringBuffer(CharSequence paramCharSequence)
/*     */   {
/* 157 */     this(paramCharSequence.length() + 16);
/* 158 */     append(paramCharSequence);
/*     */   }
/*     */   
/*     */   public synchronized int length()
/*     */   {
/* 163 */     return this.count;
/*     */   }
/*     */   
/*     */   public synchronized int capacity()
/*     */   {
/* 168 */     return this.value.length;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void ensureCapacity(int paramInt)
/*     */   {
/* 174 */     if (paramInt > this.value.length) {
/* 175 */       expandCapacity(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void trimToSize()
/*     */   {
/* 184 */     super.trimToSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setLength(int paramInt)
/*     */   {
/* 193 */     this.toStringCache = null;
/* 194 */     super.setLength(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized char charAt(int paramInt)
/*     */   {
/* 203 */     if ((paramInt < 0) || (paramInt >= this.count))
/* 204 */       throw new StringIndexOutOfBoundsException(paramInt);
/* 205 */     return this.value[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int codePointAt(int paramInt)
/*     */   {
/* 213 */     return super.codePointAt(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int codePointBefore(int paramInt)
/*     */   {
/* 221 */     return super.codePointBefore(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int codePointCount(int paramInt1, int paramInt2)
/*     */   {
/* 229 */     return super.codePointCount(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int offsetByCodePoints(int paramInt1, int paramInt2)
/*     */   {
/* 237 */     return super.offsetByCodePoints(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void getChars(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
/*     */   {
/* 247 */     super.getChars(paramInt1, paramInt2, paramArrayOfChar, paramInt3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setCharAt(int paramInt, char paramChar)
/*     */   {
/* 256 */     if ((paramInt < 0) || (paramInt >= this.count))
/* 257 */       throw new StringIndexOutOfBoundsException(paramInt);
/* 258 */     this.toStringCache = null;
/* 259 */     this.value[paramInt] = paramChar;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(Object paramObject)
/*     */   {
/* 264 */     this.toStringCache = null;
/* 265 */     super.append(String.valueOf(paramObject));
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(String paramString)
/*     */   {
/* 271 */     this.toStringCache = null;
/* 272 */     super.append(paramString);
/* 273 */     return this;
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
/*     */   public synchronized StringBuffer append(StringBuffer paramStringBuffer)
/*     */   {
/* 301 */     this.toStringCache = null;
/* 302 */     super.append(paramStringBuffer);
/* 303 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   synchronized StringBuffer append(AbstractStringBuilder paramAbstractStringBuilder)
/*     */   {
/* 311 */     this.toStringCache = null;
/* 312 */     super.append(paramAbstractStringBuilder);
/* 313 */     return this;
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
/*     */   public synchronized StringBuffer append(CharSequence paramCharSequence)
/*     */   {
/* 339 */     this.toStringCache = null;
/* 340 */     super.append(paramCharSequence);
/* 341 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/* 351 */     this.toStringCache = null;
/* 352 */     super.append(paramCharSequence, paramInt1, paramInt2);
/* 353 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(char[] paramArrayOfChar)
/*     */   {
/* 358 */     this.toStringCache = null;
/* 359 */     super.append(paramArrayOfChar);
/* 360 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer append(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 368 */     this.toStringCache = null;
/* 369 */     super.append(paramArrayOfChar, paramInt1, paramInt2);
/* 370 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(boolean paramBoolean)
/*     */   {
/* 375 */     this.toStringCache = null;
/* 376 */     super.append(paramBoolean);
/* 377 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(char paramChar)
/*     */   {
/* 382 */     this.toStringCache = null;
/* 383 */     super.append(paramChar);
/* 384 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(int paramInt)
/*     */   {
/* 389 */     this.toStringCache = null;
/* 390 */     super.append(paramInt);
/* 391 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer appendCodePoint(int paramInt)
/*     */   {
/* 399 */     this.toStringCache = null;
/* 400 */     super.appendCodePoint(paramInt);
/* 401 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(long paramLong)
/*     */   {
/* 406 */     this.toStringCache = null;
/* 407 */     super.append(paramLong);
/* 408 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(float paramFloat)
/*     */   {
/* 413 */     this.toStringCache = null;
/* 414 */     super.append(paramFloat);
/* 415 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized StringBuffer append(double paramDouble)
/*     */   {
/* 420 */     this.toStringCache = null;
/* 421 */     super.append(paramDouble);
/* 422 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer delete(int paramInt1, int paramInt2)
/*     */   {
/* 431 */     this.toStringCache = null;
/* 432 */     super.delete(paramInt1, paramInt2);
/* 433 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer deleteCharAt(int paramInt)
/*     */   {
/* 442 */     this.toStringCache = null;
/* 443 */     super.deleteCharAt(paramInt);
/* 444 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer replace(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 453 */     this.toStringCache = null;
/* 454 */     super.replace(paramInt1, paramInt2, paramString);
/* 455 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized String substring(int paramInt)
/*     */   {
/* 464 */     return substring(paramInt, this.count);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized CharSequence subSequence(int paramInt1, int paramInt2)
/*     */   {
/* 473 */     return super.substring(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized String substring(int paramInt1, int paramInt2)
/*     */   {
/* 482 */     return super.substring(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer insert(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*     */   {
/* 493 */     this.toStringCache = null;
/* 494 */     super.insert(paramInt1, paramArrayOfChar, paramInt2, paramInt3);
/* 495 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer insert(int paramInt, Object paramObject)
/*     */   {
/* 503 */     this.toStringCache = null;
/* 504 */     super.insert(paramInt, String.valueOf(paramObject));
/* 505 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer insert(int paramInt, String paramString)
/*     */   {
/* 513 */     this.toStringCache = null;
/* 514 */     super.insert(paramInt, paramString);
/* 515 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer insert(int paramInt, char[] paramArrayOfChar)
/*     */   {
/* 523 */     this.toStringCache = null;
/* 524 */     super.insert(paramInt, paramArrayOfChar);
/* 525 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer insert(int paramInt, CharSequence paramCharSequence)
/*     */   {
/* 537 */     super.insert(paramInt, paramCharSequence);
/* 538 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer insert(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3)
/*     */   {
/* 549 */     this.toStringCache = null;
/* 550 */     super.insert(paramInt1, paramCharSequence, paramInt2, paramInt3);
/* 551 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer insert(int paramInt, boolean paramBoolean)
/*     */   {
/* 562 */     super.insert(paramInt, paramBoolean);
/* 563 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer insert(int paramInt, char paramChar)
/*     */   {
/* 571 */     this.toStringCache = null;
/* 572 */     super.insert(paramInt, paramChar);
/* 573 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer insert(int paramInt1, int paramInt2)
/*     */   {
/* 584 */     super.insert(paramInt1, paramInt2);
/* 585 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer insert(int paramInt, long paramLong)
/*     */   {
/* 596 */     super.insert(paramInt, paramLong);
/* 597 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer insert(int paramInt, float paramFloat)
/*     */   {
/* 608 */     super.insert(paramInt, paramFloat);
/* 609 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer insert(int paramInt, double paramDouble)
/*     */   {
/* 620 */     super.insert(paramInt, paramDouble);
/* 621 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int indexOf(String paramString)
/*     */   {
/* 630 */     return super.indexOf(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int indexOf(String paramString, int paramInt)
/*     */   {
/* 638 */     return super.indexOf(paramString, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int lastIndexOf(String paramString)
/*     */   {
/* 647 */     return lastIndexOf(paramString, this.count);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int lastIndexOf(String paramString, int paramInt)
/*     */   {
/* 655 */     return super.lastIndexOf(paramString, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized StringBuffer reverse()
/*     */   {
/* 663 */     this.toStringCache = null;
/* 664 */     super.reverse();
/* 665 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized String toString()
/*     */   {
/* 670 */     if (this.toStringCache == null) {
/* 671 */       this.toStringCache = Arrays.copyOfRange(this.value, 0, this.count);
/*     */     }
/* 673 */     return new String(this.toStringCache, true);
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
/* 687 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("value", char[].class), new ObjectStreamField("count", Integer.TYPE), new ObjectStreamField("shared", Boolean.TYPE) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 700 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 701 */     localPutField.put("value", this.value);
/* 702 */     localPutField.put("count", this.count);
/* 703 */     localPutField.put("shared", false);
/* 704 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 713 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 714 */     this.value = ((char[])localGetField.get("value", null));
/* 715 */     this.count = localGetField.get("count", 0);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/StringBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */