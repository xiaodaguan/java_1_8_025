/*     */ package java.nio.charset;
/*     */ 
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CharsetDecoder
/*     */ {
/*     */   private final Charset charset;
/*     */   private final float averageCharsPerByte;
/*     */   private final float maxCharsPerByte;
/*     */   private String replacement;
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
/*     */   private CharsetDecoder(Charset paramCharset, float paramFloat1, float paramFloat2, String paramString)
/*     */   {
/* 191 */     this.charset = paramCharset;
/* 192 */     if (paramFloat1 <= 0.0F) {
/* 193 */       throw new IllegalArgumentException("Non-positive averageCharsPerByte");
/*     */     }
/* 195 */     if (paramFloat2 <= 0.0F) {
/* 196 */       throw new IllegalArgumentException("Non-positive maxCharsPerByte");
/*     */     }
/* 198 */     if ((!Charset.atBugLevel("1.4")) && 
/* 199 */       (paramFloat1 > paramFloat2)) {
/* 200 */       throw new IllegalArgumentException("averageCharsPerByte exceeds maxCharsPerByte");
/*     */     }
/*     */     
/*     */ 
/* 204 */     this.replacement = paramString;
/* 205 */     this.averageCharsPerByte = paramFloat1;
/* 206 */     this.maxCharsPerByte = paramFloat2;
/* 207 */     replaceWith(paramString);
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
/*     */   protected CharsetDecoder(Charset paramCharset, float paramFloat1, float paramFloat2)
/*     */   {
/* 233 */     this(paramCharset, paramFloat1, paramFloat2, "�");
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
/*     */   public final String replacement()
/*     */   {
/* 255 */     return this.replacement;
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
/*     */   public final CharsetDecoder replaceWith(String paramString)
/*     */   {
/* 288 */     if (paramString == null)
/* 289 */       throw new IllegalArgumentException("Null replacement");
/* 290 */     int i = paramString.length();
/* 291 */     if (i == 0)
/* 292 */       throw new IllegalArgumentException("Empty replacement");
/* 293 */     if (i > this.maxCharsPerByte) {
/* 294 */       throw new IllegalArgumentException("Replacement too long");
/*     */     }
/* 296 */     this.replacement = paramString;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void implReplaceWith(String paramString) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public final CharsetDecoder onMalformedInput(CodingErrorAction paramCodingErrorAction)
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
/*     */   public final CharsetDecoder onUnmappableCharacter(CodingErrorAction paramCodingErrorAction)
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
/*     */   public final float averageCharsPerByte()
/*     */   {
/* 453 */     return this.averageCharsPerByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final float maxCharsPerByte()
/*     */   {
/* 465 */     return this.maxCharsPerByte;
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
/*     */   public final CoderResult decode(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer, boolean paramBoolean)
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
/* 579 */         localCoderResult = decodeLoop(paramByteBuffer, paramCharBuffer);
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
/* 590 */         if ((paramBoolean) && (paramByteBuffer.hasRemaining())) {
/* 591 */           localCoderResult = CoderResult.malformedForLength(paramByteBuffer.remaining());
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
/* 610 */         if (paramCharBuffer.remaining() < this.replacement.length())
/* 611 */           return CoderResult.OVERFLOW;
/* 612 */         paramCharBuffer.put(this.replacement);
/*     */       }
/*     */       
/* 615 */       if ((localCodingErrorAction == CodingErrorAction.IGNORE) || (localCodingErrorAction == CodingErrorAction.REPLACE))
/*     */       {
/*     */ 
/* 618 */         paramByteBuffer.position(paramByteBuffer.position() + localCoderResult.length());
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
/*     */   public final CoderResult flush(CharBuffer paramCharBuffer)
/*     */   {
/* 667 */     if (this.state == 2) {
/* 668 */       CoderResult localCoderResult = implFlush(paramCharBuffer);
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
/*     */   protected CoderResult implFlush(CharBuffer paramCharBuffer)
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
/*     */   public final CharsetDecoder reset()
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
/*     */   protected abstract CoderResult decodeLoop(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CharBuffer decode(ByteBuffer paramByteBuffer)
/*     */     throws CharacterCodingException
/*     */   {
/* 794 */     int i = (int)(paramByteBuffer.remaining() * averageCharsPerByte());
/* 795 */     Object localObject = CharBuffer.allocate(i);
/*     */     
/* 797 */     if ((i == 0) && (paramByteBuffer.remaining() == 0))
/* 798 */       return (CharBuffer)localObject;
/* 799 */     reset();
/*     */     for (;;)
/*     */     {
/* 802 */       CoderResult localCoderResult = paramByteBuffer.hasRemaining() ? decode(paramByteBuffer, (CharBuffer)localObject, true) : CoderResult.UNDERFLOW;
/* 803 */       if (localCoderResult.isUnderflow()) {
/* 804 */         localCoderResult = flush((CharBuffer)localObject);
/*     */       }
/* 806 */       if (localCoderResult.isUnderflow())
/*     */         break;
/* 808 */       if (localCoderResult.isOverflow()) {
/* 809 */         i = 2 * i + 1;
/* 810 */         CharBuffer localCharBuffer = CharBuffer.allocate(i);
/* 811 */         ((CharBuffer)localObject).flip();
/* 812 */         localCharBuffer.put((CharBuffer)localObject);
/* 813 */         localObject = localCharBuffer;
/*     */       }
/*     */       else {
/* 816 */         localCoderResult.throwException();
/*     */       } }
/* 818 */     ((CharBuffer)localObject).flip();
/* 819 */     return (CharBuffer)localObject;
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
/*     */   public boolean isAutoDetecting()
/*     */   {
/* 835 */     return false;
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
/*     */   public boolean isCharsetDetected()
/*     */   {
/* 865 */     throw new UnsupportedOperationException();
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
/*     */   public Charset detectedCharset()
/*     */   {
/* 893 */     throw new UnsupportedOperationException();
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
/*     */   private void throwIllegalStateException(int paramInt1, int paramInt2)
/*     */   {
/* 992 */     throw new IllegalStateException("Current state = " + stateNames[paramInt1] + ", new state = " + stateNames[paramInt2]);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/charset/CharsetDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */