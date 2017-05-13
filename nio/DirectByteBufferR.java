/*      */ package java.nio;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.nio.ch.DirectBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class DirectByteBufferR
/*      */   extends DirectByteBuffer
/*      */   implements DirectBuffer
/*      */ {
/*      */   DirectByteBufferR(int paramInt)
/*      */   {
/*  142 */     super(paramInt);
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
/*      */   protected DirectByteBufferR(int paramInt, long paramLong, FileDescriptor paramFileDescriptor, Runnable paramRunnable)
/*      */   {
/*  182 */     super(paramInt, paramLong, paramFileDescriptor, paramRunnable);
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
/*      */   DirectByteBufferR(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/*  202 */     super(paramDirectBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */   
/*      */   public ByteBuffer slice()
/*      */   {
/*  207 */     int i = position();
/*  208 */     int j = limit();
/*  209 */     assert (i <= j);
/*  210 */     int k = i <= j ? j - i : 0;
/*  211 */     int m = i << 0;
/*  212 */     assert (m >= 0);
/*  213 */     return new DirectByteBufferR(this, -1, 0, k, k, m);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer duplicate()
/*      */   {
/*  221 */     return new DirectByteBufferR(this, markValue(), position(), limit(), capacity(), 0);
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
/*      */   public ByteBuffer asReadOnlyBuffer()
/*      */   {
/*  234 */     return duplicate();
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
/*      */   public ByteBuffer put(byte paramByte)
/*      */   {
/*  300 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer put(int paramInt, byte paramByte)
/*      */   {
/*  309 */     throw new ReadOnlyBufferException();
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
/*      */   public ByteBuffer put(ByteBuffer paramByteBuffer)
/*      */   {
/*  350 */     throw new ReadOnlyBufferException();
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
/*      */   public ByteBuffer put(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  379 */     throw new ReadOnlyBufferException();
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
/*      */   public ByteBuffer compact()
/*      */   {
/*  396 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public boolean isDirect()
/*      */   {
/*  401 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isReadOnly() {
/*  405 */     return true;
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
/*      */   byte _get(int paramInt)
/*      */   {
/*  472 */     return unsafe.getByte(this.address + paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void _put(int paramInt, byte paramByte)
/*      */   {
/*  479 */     throw new ReadOnlyBufferException();
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
/*      */   private ByteBuffer putChar(long paramLong, char paramChar)
/*      */   {
/*  514 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putChar(char paramChar)
/*      */   {
/*  523 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putChar(int paramInt, char paramChar)
/*      */   {
/*  532 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public CharBuffer asCharBuffer()
/*      */   {
/*  537 */     int i = position();
/*  538 */     int j = limit();
/*  539 */     assert (i <= j);
/*  540 */     int k = i <= j ? j - i : 0;
/*      */     
/*  542 */     int m = k >> 1;
/*  543 */     if ((!unaligned) && ((this.address + i) % 2L != 0L)) {
/*  544 */       return this.bigEndian ? new ByteBufferAsCharBufferRB(this, -1, 0, m, m, i) : new ByteBufferAsCharBufferRL(this, -1, 0, m, m, i);
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
/*  558 */     return this.nativeByteOrder ? new DirectCharBufferRU(this, -1, 0, m, m, i) : new DirectCharBufferRS(this, -1, 0, m, m, i);
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
/*      */   private ByteBuffer putShort(long paramLong, short paramShort)
/*      */   {
/*  605 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putShort(short paramShort)
/*      */   {
/*  614 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putShort(int paramInt, short paramShort)
/*      */   {
/*  623 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public ShortBuffer asShortBuffer()
/*      */   {
/*  628 */     int i = position();
/*  629 */     int j = limit();
/*  630 */     assert (i <= j);
/*  631 */     int k = i <= j ? j - i : 0;
/*      */     
/*  633 */     int m = k >> 1;
/*  634 */     if ((!unaligned) && ((this.address + i) % 2L != 0L)) {
/*  635 */       return this.bigEndian ? new ByteBufferAsShortBufferRB(this, -1, 0, m, m, i) : new ByteBufferAsShortBufferRL(this, -1, 0, m, m, i);
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
/*  649 */     return this.nativeByteOrder ? new DirectShortBufferRU(this, -1, 0, m, m, i) : new DirectShortBufferRS(this, -1, 0, m, m, i);
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
/*      */   private ByteBuffer putInt(long paramLong, int paramInt)
/*      */   {
/*  696 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putInt(int paramInt)
/*      */   {
/*  705 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putInt(int paramInt1, int paramInt2)
/*      */   {
/*  714 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public IntBuffer asIntBuffer()
/*      */   {
/*  719 */     int i = position();
/*  720 */     int j = limit();
/*  721 */     assert (i <= j);
/*  722 */     int k = i <= j ? j - i : 0;
/*      */     
/*  724 */     int m = k >> 2;
/*  725 */     if ((!unaligned) && ((this.address + i) % 4L != 0L)) {
/*  726 */       return this.bigEndian ? new ByteBufferAsIntBufferRB(this, -1, 0, m, m, i) : new ByteBufferAsIntBufferRL(this, -1, 0, m, m, i);
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
/*  740 */     return this.nativeByteOrder ? new DirectIntBufferRU(this, -1, 0, m, m, i) : new DirectIntBufferRS(this, -1, 0, m, m, i);
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
/*      */   private ByteBuffer putLong(long paramLong1, long paramLong2)
/*      */   {
/*  787 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putLong(long paramLong)
/*      */   {
/*  796 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putLong(int paramInt, long paramLong)
/*      */   {
/*  805 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public LongBuffer asLongBuffer()
/*      */   {
/*  810 */     int i = position();
/*  811 */     int j = limit();
/*  812 */     assert (i <= j);
/*  813 */     int k = i <= j ? j - i : 0;
/*      */     
/*  815 */     int m = k >> 3;
/*  816 */     if ((!unaligned) && ((this.address + i) % 8L != 0L)) {
/*  817 */       return this.bigEndian ? new ByteBufferAsLongBufferRB(this, -1, 0, m, m, i) : new ByteBufferAsLongBufferRL(this, -1, 0, m, m, i);
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
/*  831 */     return this.nativeByteOrder ? new DirectLongBufferRU(this, -1, 0, m, m, i) : new DirectLongBufferRS(this, -1, 0, m, m, i);
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
/*      */   private ByteBuffer putFloat(long paramLong, float paramFloat)
/*      */   {
/*  878 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putFloat(float paramFloat)
/*      */   {
/*  887 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putFloat(int paramInt, float paramFloat)
/*      */   {
/*  896 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public FloatBuffer asFloatBuffer()
/*      */   {
/*  901 */     int i = position();
/*  902 */     int j = limit();
/*  903 */     assert (i <= j);
/*  904 */     int k = i <= j ? j - i : 0;
/*      */     
/*  906 */     int m = k >> 2;
/*  907 */     if ((!unaligned) && ((this.address + i) % 4L != 0L)) {
/*  908 */       return this.bigEndian ? new ByteBufferAsFloatBufferRB(this, -1, 0, m, m, i) : new ByteBufferAsFloatBufferRL(this, -1, 0, m, m, i);
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
/*  922 */     return this.nativeByteOrder ? new DirectFloatBufferRU(this, -1, 0, m, m, i) : new DirectFloatBufferRS(this, -1, 0, m, m, i);
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
/*      */   private ByteBuffer putDouble(long paramLong, double paramDouble)
/*      */   {
/*  969 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putDouble(double paramDouble)
/*      */   {
/*  978 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putDouble(int paramInt, double paramDouble)
/*      */   {
/*  987 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */   
/*      */   public DoubleBuffer asDoubleBuffer()
/*      */   {
/*  992 */     int i = position();
/*  993 */     int j = limit();
/*  994 */     assert (i <= j);
/*  995 */     int k = i <= j ? j - i : 0;
/*      */     
/*  997 */     int m = k >> 3;
/*  998 */     if ((!unaligned) && ((this.address + i) % 8L != 0L)) {
/*  999 */       return this.bigEndian ? new ByteBufferAsDoubleBufferRB(this, -1, 0, m, m, i) : new ByteBufferAsDoubleBufferRL(this, -1, 0, m, m, i);
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
/* 1013 */     return this.nativeByteOrder ? new DirectDoubleBufferRU(this, -1, 0, m, m, i) : new DirectDoubleBufferRS(this, -1, 0, m, m, i);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/DirectByteBufferR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */