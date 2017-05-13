/*     */ package java.nio.charset;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
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
/*     */ public abstract class CharsetEncoder
/*     */ {
/*     */   private final Charset charset;
/*     */   private final float averageBytesPerChar;
/*     */   private final float maxBytesPerChar;
/*     */   private byte[] replacement;
/* 144 */   private CodingErrorAction malformedInputAction = CodingErrorAction.REPORT;
/*     */   
/* 146 */   private CodingErrorAction unmappableCharacterAction = CodingErrorAction.REPORT;
/*     */   
/*     */   private static final int ST_RESET = 0;
/*     */   
/*     */   private static final int ST_CODING = 1;
/*     */   
/*     */   private static final int ST_END = 2;
/*     */   
/*     */   private static final int ST_FLUSHED = 3;
/*     */   
/* 156 */   private int state = 0;
/*     */   
/* 158 */   private static String[] stateNames = { "RESET", "CODING", "CODING_END", "FLUSHED" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CharsetEncoder(Charset paramCharset, float paramFloat1, float paramFloat2, byte[] paramArrayOfByte)
/*     */   {
/* 191 */     this.charset = paramCharset;
/* 192 */     if (paramFloat1 <= 0.0F) {
/* 193 */       throw new IllegalArgumentException("Non-positive averageBytesPerChar");
/*     */     }
/* 195 */     if (paramFloat2 <= 0.0F) {
/* 196 */       throw new IllegalArgumentException("Non-positive maxBytesPerChar");
/*     */     }
/* 198 */     if ((!Charset.atBugLevel("1.4")) && 
/* 199 */       (paramFloat1 > paramFloat2)) {
/* 200 */       throw new IllegalArgumentException("averageBytesPerChar exceeds maxBytesPerChar");
/*     */     }
/*     */     
/*     */ 
/* 204 */     this.replacement = paramArrayOfByte;
/* 205 */     this.averageBytesPerChar = paramFloat1;
/* 206 */     this.maxBytesPerChar = paramFloat2;
/* 207 */     replaceWith(paramArrayOfByte);
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
/*     */   protected CharsetEncoder(Charset paramCharset, float paramFloat1, float paramFloat2)
/*     */   {
/* 233 */     this(paramCharset, paramFloat1, paramFloat2, new byte[] { 63 });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Charset charset()
/*     */   {
/* 244 */     return this.charset;
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
/*     */   public final byte[] replacement()
/*     */   {
/* 258 */     return Arrays.copyOf(this.replacement, this.replacement.length);
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
/*     */   public final CharsetEncoder replaceWith(byte[] paramArrayOfByte)
/*     */   {
/* 288 */     if (paramArrayOfByte == null)
/* 289 */       throw new IllegalArgumentException("Null replacement");
/* 290 */     int i = paramArrayOfByte.length;
/* 291 */     if (i == 0)
/* 292 */       throw new IllegalArgumentException("Empty replacement");
/* 293 */     if (i > this.maxBytesPerChar) {
/* 294 */       throw new IllegalArgumentException("Replacement too long");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 299 */     if (!isLegalReplacement(paramArrayOfByte))
/* 300 */       throw new IllegalArgumentException("Illegal replacement");
/* 301 */     this.replacement = Arrays.copyOf(paramArrayOfByte, paramArrayOfByte.length);
/*     */     
/* 303 */     implReplaceWith(this.replacement);
/* 304 */     return this;
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
/* 321 */   private WeakReference<CharsetDecoder> cachedDecoder = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void implReplaceWith(byte[] paramArrayOfByte) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLegalReplacement(byte[] paramArrayOfByte)
/*     */   {
/* 340 */     WeakReference localWeakReference = this.cachedDecoder;
/* 341 */     CharsetDecoder localCharsetDecoder = null;
/* 342 */     if ((localWeakReference == null) || ((localCharsetDecoder = (CharsetDecoder)localWeakReference.get()) == null)) {
/* 343 */       localCharsetDecoder = charset().newDecoder();
/* 344 */       localCharsetDecoder.onMalformedInput(CodingErrorAction.REPORT);
/* 345 */       localCharsetDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
/* 346 */       this.cachedDecoder = new WeakReference(localCharsetDecoder);
/*     */     } else {
/* 348 */       localCharsetDecoder.reset();
/*     */     }
/* 350 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
/* 351 */     CharBuffer localCharBuffer = CharBuffer.allocate(
/* 352 */       (int)(localByteBuffer.remaining() * localCharsetDecoder.maxCharsPerByte()));
/* 353 */     CoderResult localCoderResult = localCharsetDecoder.decode(localByteBuffer, localCharBuffer, true);
/* 354 */     return !localCoderResult.isError();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CodingErrorAction malformedInputAction()
/*     */   {
/* 365 */     return this.malformedInputAction;
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
/*     */   public final CharsetEncoder onMalformedInput(CodingErrorAction paramCodingErrorAction)
/*     */   {
/* 382 */     if (paramCodingErrorAction == null)
/* 383 */       throw new IllegalArgumentException("Null action");
/* 384 */     this.malformedInputAction = paramCodingErrorAction;
/* 385 */     implOnMalformedInput(paramCodingErrorAction);
/* 386 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void implOnMalformedInput(CodingErrorAction paramCodingErrorAction) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CodingErrorAction unmappableCharacterAction()
/*     */   {
/* 407 */     return this.unmappableCharacterAction;
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
/*     */   public final CharsetEncoder onUnmappableCharacter(CodingErrorAction paramCodingErrorAction)
/*     */   {
/* 426 */     if (paramCodingErrorAction == null)
/* 427 */       throw new IllegalArgumentException("Null action");
/* 428 */     this.unmappableCharacterAction = paramCodingErrorAction;
/* 429 */     implOnUnmappableCharacter(paramCodingErrorAction);
/* 430 */     return this;
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
/*     */   protected void implOnUnmappableCharacter(CodingErrorAction paramCodingErrorAction) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final float averageBytesPerChar()
/*     */   {
/* 453 */     return this.averageBytesPerChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final float maxBytesPerChar()
/*     */   {
/* 465 */     return this.maxBytesPerChar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CoderResult encode(CharBuffer paramCharBuffer, ByteBuffer paramByteBuffer, boolean paramBoolean)
/*     */   {
/* 569 */     int i = paramBoolean ? 2 : 1;
/* 570 */     if ((this.state != 0) && (this.state != 1) && ((!paramBoolean) || (this.state != 2)))
/*     */     {
/* 572 */       throwIllegalStateException(this.state, i); }
/* 573 */     this.state = i;
/*     */     for (;;)
/*     */     {
/*     */       CoderResult localCoderResult;
/*     */       try
/*     */       {
/* 579 */         localCoderResult = encodeLoop(paramCharBuffer, paramByteBuffer);
/*     */       } catch (BufferUnderflowException localBufferUnderflowException) {
/* 581 */         throw new CoderMalfunctionError(localBufferUnderflowException);
/*     */       } catch (BufferOverflowException localBufferOverflowException) {
/* 583 */         throw new CoderMalfunctionError(localBufferOverflowException);
/*     */       }
/*     */       
/* 586 */       if (localCoderResult.isOverflow()) {
/* 587 */         return localCoderResult;
/*     */       }
/* 589 */       if (localCoderResult.isUnderflow()) {
/* 590 */         if ((paramBoolean) && (paramCharBuffer.hasRemaining())) {
/* 591 */           localCoderResult = CoderResult.malformedForLength(paramCharBuffer.remaining());
/*     */         }
/*     */         else {
/* 594 */           return localCoderResult;
/*     */         }
/*     */       }
/*     */       
/* 598 */       CodingErrorAction localCodingErrorAction = null;
/* 599 */       if (localCoderResult.isMalformed()) {
/* 600 */         localCodingErrorAction = this.malformedInputAction;
/* 601 */       } else if (localCoderResult.isUnmappable()) {
/* 602 */         localCodingErrorAction = this.unmappableCharacterAction;
/*     */       }
/* 604 */       else if (!$assertionsDisabled) { throw new AssertionError(localCoderResult.toString());
/*     */       }
/* 606 */       if (localCodingErrorAction == CodingErrorAction.REPORT) {
/* 607 */         return localCoderResult;
/*     */       }
/* 609 */       if (localCodingErrorAction == CodingErrorAction.REPLACE) {
/* 610 */         if (paramByteBuffer.remaining() < this.replacement.length)
/* 611 */           return CoderResult.OVERFLOW;
/* 612 */         paramByteBuffer.put(this.replacement);
/*     */       }
/*     */       
/* 615 */       if ((localCodingErrorAction == CodingErrorAction.IGNORE) || (localCodingErrorAction == CodingErrorAction.REPLACE))
/*     */       {
/*     */ 
/* 618 */         paramCharBuffer.position(paramCharBuffer.position() + localCoderResult.length());
/*     */ 
/*     */ 
/*     */       }
/* 622 */       else if (!$assertionsDisabled) { throw new AssertionError();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CoderResult flush(ByteBuffer paramByteBuffer)
/*     */   {
/* 667 */     if (this.state == 2) {
/* 668 */       CoderResult localCoderResult = implFlush(paramByteBuffer);
/* 669 */       if (localCoderResult.isUnderflow())
/* 670 */         this.state = 3;
/* 671 */       return localCoderResult;
/*     */     }
/*     */     
/* 674 */     if (this.state != 3) {
/* 675 */       throwIllegalStateException(this.state, 3);
/*     */     }
/* 677 */     return CoderResult.UNDERFLOW;
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
/*     */   protected CoderResult implFlush(ByteBuffer paramByteBuffer)
/*     */   {
/* 695 */     return CoderResult.UNDERFLOW;
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
/*     */   public final CharsetEncoder reset()
/*     */   {
/* 709 */     implReset();
/* 710 */     this.state = 0;
/* 711 */     return this;
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
/*     */   protected void implReset() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract CoderResult encodeLoop(CharBuffer paramCharBuffer, ByteBuffer paramByteBuffer);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ByteBuffer encode(CharBuffer paramCharBuffer)
/*     */     throws CharacterCodingException
/*     */   {
/* 794 */     int i = (int)(paramCharBuffer.remaining() * averageBytesPerChar());
/* 795 */     Object localObject = ByteBuffer.allocate(i);
/*     */     
/* 797 */     if ((i == 0) && (paramCharBuffer.remaining() == 0))
/* 798 */       return (ByteBuffer)localObject;
/* 799 */     reset();
/*     */     for (;;)
/*     */     {
/* 802 */       CoderResult localCoderResult = paramCharBuffer.hasRemaining() ? encode(paramCharBuffer, (ByteBuffer)localObject, true) : CoderResult.UNDERFLOW;
/* 803 */       if (localCoderResult.isUnderflow()) {
/* 804 */         localCoderResult = flush((ByteBuffer)localObject);
/*     */       }
/* 806 */       if (localCoderResult.isUnderflow())
/*     */         break;
/* 808 */       if (localCoderResult.isOverflow()) {
/* 809 */         i = 2 * i + 1;
/* 810 */         ByteBuffer localByteBuffer = ByteBuffer.allocate(i);
/* 811 */         ((ByteBuffer)localObject).flip();
/* 812 */         localByteBuffer.put((ByteBuffer)localObject);
/* 813 */         localObject = localByteBuffer;
/*     */       }
/*     */       else {
/* 816 */         localCoderResult.throwException();
/*     */       } }
/* 818 */     ((ByteBuffer)localObject).flip();
/* 819 */     return (ByteBuffer)localObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean canEncode(CharBuffer paramCharBuffer)
/*     */   {
/* 901 */     if (this.state == 3) {
/* 902 */       reset();
/* 903 */     } else if (this.state != 0)
/* 904 */       throwIllegalStateException(this.state, 1);
/* 905 */     CodingErrorAction localCodingErrorAction1 = malformedInputAction();
/* 906 */     CodingErrorAction localCodingErrorAction2 = unmappableCharacterAction();
/*     */     try {
/* 908 */       onMalformedInput(CodingErrorAction.REPORT);
/* 909 */       onUnmappableCharacter(CodingErrorAction.REPORT);
/* 910 */       encode(paramCharBuffer);
/*     */     } catch (CharacterCodingException localCharacterCodingException) {
/* 912 */       return false;
/*     */     } finally {
/* 914 */       onMalformedInput(localCodingErrorAction1);
/* 915 */       onUnmappableCharacter(localCodingErrorAction2);
/* 916 */       reset();
/*     */     }
/* 918 */     return true;
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
/*     */   public boolean canEncode(char paramChar)
/*     */   {
/* 948 */     CharBuffer localCharBuffer = CharBuffer.allocate(1);
/* 949 */     localCharBuffer.put(paramChar);
/* 950 */     localCharBuffer.flip();
/* 951 */     return canEncode(localCharBuffer);
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
/*     */   public boolean canEncode(CharSequence paramCharSequence)
/*     */   {
/*     */     CharBuffer localCharBuffer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 981 */     if ((paramCharSequence instanceof CharBuffer)) {
/* 982 */       localCharBuffer = ((CharBuffer)paramCharSequence).duplicate();
/*     */     } else
/* 984 */       localCharBuffer = CharBuffer.wrap(paramCharSequence.toString());
/* 985 */     return canEncode(localCharBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void throwIllegalStateException(int paramInt1, int paramInt2)
/*     */   {
/* 992 */     throw new IllegalStateException("Current state = " + stateNames[paramInt1] + ", new state = " + stateNames[paramInt2]);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/charset/CharsetEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */