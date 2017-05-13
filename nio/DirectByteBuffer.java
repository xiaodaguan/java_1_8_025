/*      */ package java.nio;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import sun.misc.Cleaner;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.misc.VM;
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
/*      */ class DirectByteBuffer
/*      */   extends MappedByteBuffer
/*      */   implements DirectBuffer
/*      */ {
/*   49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*      */   
/*      */ 
/*   52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
/*      */   
/*      */ 
/*   55 */   protected static final boolean unaligned = Bits.unaligned();
/*      */   
/*      */ 
/*      */   private final Object att;
/*      */   
/*      */ 
/*      */   private final Cleaner cleaner;
/*      */   
/*      */ 
/*      */ 
/*      */   public Object attachment()
/*      */   {
/*   67 */     return this.att;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class Deallocator
/*      */     implements Runnable
/*      */   {
/*   76 */     private static Unsafe unsafe = Unsafe.getUnsafe();
/*      */     private long address;
/*      */     private long size;
/*      */     private int capacity;
/*      */     
/*      */     private Deallocator(long paramLong1, long paramLong2, int paramInt)
/*      */     {
/*   83 */       assert (paramLong1 != 0L);
/*   84 */       this.address = paramLong1;
/*   85 */       this.size = paramLong2;
/*   86 */       this.capacity = paramInt;
/*      */     }
/*      */     
/*      */     public void run() {
/*   90 */       if (this.address == 0L)
/*      */       {
/*   92 */         return;
/*      */       }
/*   94 */       unsafe.freeMemory(this.address);
/*   95 */       this.address = 0L;
/*   96 */       Bits.unreserveMemory(this.size, this.capacity);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public Cleaner cleaner()
/*      */   {
/*  103 */     return this.cleaner;
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
/*      */   DirectByteBuffer(int paramInt)
/*      */   {
/*  119 */     super(-1, 0, paramInt, paramInt);
/*  120 */     boolean bool = VM.isDirectMemoryPageAligned();
/*  121 */     int i = Bits.pageSize();
/*  122 */     long l1 = Math.max(1L, paramInt + (bool ? i : 0));
/*  123 */     Bits.reserveMemory(l1, paramInt);
/*      */     
/*  125 */     long l2 = 0L;
/*      */     try {
/*  127 */       l2 = unsafe.allocateMemory(l1);
/*      */     } catch (OutOfMemoryError localOutOfMemoryError) {
/*  129 */       Bits.unreserveMemory(l1, paramInt);
/*  130 */       throw localOutOfMemoryError;
/*      */     }
/*  132 */     unsafe.setMemory(l2, l1, (byte)0);
/*  133 */     if ((bool) && (l2 % i != 0L))
/*      */     {
/*  135 */       this.address = (l2 + i - (l2 & i - 1));
/*      */     } else {
/*  137 */       this.address = l2;
/*      */     }
/*  139 */     this.cleaner = Cleaner.create(this, new Deallocator(l2, l1, paramInt, null));
/*  140 */     this.att = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   DirectByteBuffer(long paramLong, int paramInt, Object paramObject)
/*      */   {
/*  152 */     super(-1, 0, paramInt, paramInt);
/*  153 */     this.address = paramLong;
/*  154 */     this.cleaner = null;
/*  155 */     this.att = paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private DirectByteBuffer(long paramLong, int paramInt)
/*      */   {
/*  162 */     super(-1, 0, paramInt, paramInt);
/*  163 */     this.address = paramLong;
/*  164 */     this.cleaner = null;
/*  165 */     this.att = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected DirectByteBuffer(int paramInt, long paramLong, FileDescriptor paramFileDescriptor, Runnable paramRunnable)
/*      */   {
/*  177 */     super(-1, 0, paramInt, paramInt, paramFileDescriptor);
/*  178 */     this.address = paramLong;
/*  179 */     this.cleaner = Cleaner.create(this, paramRunnable);
/*  180 */     this.att = null;
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
/*      */   DirectByteBuffer(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */   {
/*  195 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  196 */     this.address = (paramDirectBuffer.address() + paramInt5);
/*      */     
/*  198 */     this.cleaner = null;
/*      */     
/*  200 */     this.att = paramDirectBuffer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ByteBuffer slice()
/*      */   {
/*  207 */     int i = position();
/*  208 */     int j = limit();
/*  209 */     assert (i <= j);
/*  210 */     int k = i <= j ? j - i : 0;
/*  211 */     int m = i << 0;
/*  212 */     assert (m >= 0);
/*  213 */     return new DirectByteBuffer(this, -1, 0, k, k, m);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer duplicate()
/*      */   {
/*  221 */     return new DirectByteBuffer(this, markValue(), position(), limit(), capacity(), 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer asReadOnlyBuffer()
/*      */   {
/*  231 */     return new DirectByteBufferR(this, markValue(), position(), limit(), capacity(), 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long address()
/*      */   {
/*  241 */     return this.address;
/*      */   }
/*      */   
/*      */   private long ix(int paramInt) {
/*  245 */     return this.address + (paramInt << 0);
/*      */   }
/*      */   
/*      */   public byte get() {
/*  249 */     return unsafe.getByte(ix(nextGetIndex()));
/*      */   }
/*      */   
/*      */   public byte get(int paramInt) {
/*  253 */     return unsafe.getByte(ix(checkIndex(paramInt)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer get(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  264 */     if (paramInt2 << 0 > 6) {
/*  265 */       checkBounds(paramInt1, paramInt2, paramArrayOfByte.length);
/*  266 */       int i = position();
/*  267 */       int j = limit();
/*  268 */       assert (i <= j);
/*  269 */       int k = i <= j ? j - i : 0;
/*  270 */       if (paramInt2 > k) {
/*  271 */         throw new BufferUnderflowException();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  280 */       Bits.copyToArray(ix(i), paramArrayOfByte, arrayBaseOffset, paramInt1 << 0, paramInt2 << 0);
/*      */       
/*      */ 
/*  283 */       position(i + paramInt2);
/*      */     } else {
/*  285 */       super.get(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*  287 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer put(byte paramByte)
/*      */   {
/*  297 */     unsafe.putByte(ix(nextPutIndex()), paramByte);
/*  298 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer put(int paramInt, byte paramByte)
/*      */   {
/*  306 */     unsafe.putByte(ix(checkIndex(paramInt)), paramByte);
/*  307 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */   public ByteBuffer put(ByteBuffer paramByteBuffer)
/*      */   {
/*      */     int j;
/*      */     int k;
/*  315 */     if ((paramByteBuffer instanceof DirectByteBuffer)) {
/*  316 */       if (paramByteBuffer == this)
/*  317 */         throw new IllegalArgumentException();
/*  318 */       DirectByteBuffer localDirectByteBuffer = (DirectByteBuffer)paramByteBuffer;
/*      */       
/*  320 */       j = localDirectByteBuffer.position();
/*  321 */       k = localDirectByteBuffer.limit();
/*  322 */       assert (j <= k);
/*  323 */       int m = j <= k ? k - j : 0;
/*      */       
/*  325 */       int n = position();
/*  326 */       int i1 = limit();
/*  327 */       assert (n <= i1);
/*  328 */       int i2 = n <= i1 ? i1 - n : 0;
/*      */       
/*  330 */       if (m > i2)
/*  331 */         throw new BufferOverflowException();
/*  332 */       unsafe.copyMemory(localDirectByteBuffer.ix(j), ix(n), m << 0);
/*  333 */       localDirectByteBuffer.position(j + m);
/*  334 */       position(n + m);
/*  335 */     } else if (paramByteBuffer.hb != null)
/*      */     {
/*  337 */       int i = paramByteBuffer.position();
/*  338 */       j = paramByteBuffer.limit();
/*  339 */       assert (i <= j);
/*  340 */       k = i <= j ? j - i : 0;
/*      */       
/*  342 */       put(paramByteBuffer.hb, paramByteBuffer.offset + i, k);
/*  343 */       paramByteBuffer.position(i + k);
/*      */     }
/*      */     else {
/*  346 */       super.put(paramByteBuffer);
/*      */     }
/*  348 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer put(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  356 */     if (paramInt2 << 0 > 6) {
/*  357 */       checkBounds(paramInt1, paramInt2, paramArrayOfByte.length);
/*  358 */       int i = position();
/*  359 */       int j = limit();
/*  360 */       assert (i <= j);
/*  361 */       int k = i <= j ? j - i : 0;
/*  362 */       if (paramInt2 > k) {
/*  363 */         throw new BufferOverflowException();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  371 */       Bits.copyFromArray(paramArrayOfByte, arrayBaseOffset, paramInt1 << 0, 
/*  372 */         ix(i), paramInt2 << 0);
/*  373 */       position(i + paramInt2);
/*      */     } else {
/*  375 */       super.put(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*  377 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer compact()
/*      */   {
/*  385 */     int i = position();
/*  386 */     int j = limit();
/*  387 */     assert (i <= j);
/*  388 */     int k = i <= j ? j - i : 0;
/*      */     
/*  390 */     unsafe.copyMemory(ix(i), ix(0), k << 0);
/*  391 */     position(k);
/*  392 */     limit(capacity());
/*  393 */     discardMark();
/*  394 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isDirect()
/*      */   {
/*  401 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isReadOnly() {
/*  405 */     return false;
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
/*      */   void _put(int paramInt, byte paramByte)
/*      */   {
/*  477 */     unsafe.putByte(this.address + paramInt, paramByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private char getChar(long paramLong)
/*      */   {
/*  487 */     if (unaligned) {
/*  488 */       char c = unsafe.getChar(paramLong);
/*  489 */       return this.nativeByteOrder ? c : Bits.swap(c);
/*      */     }
/*  491 */     return Bits.getChar(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public char getChar() {
/*  495 */     return getChar(ix(nextGetIndex(2)));
/*      */   }
/*      */   
/*      */   public char getChar(int paramInt) {
/*  499 */     return getChar(ix(checkIndex(paramInt, 2)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ByteBuffer putChar(long paramLong, char paramChar)
/*      */   {
/*  506 */     if (unaligned) {
/*  507 */       char c = paramChar;
/*  508 */       unsafe.putChar(paramLong, this.nativeByteOrder ? c : Bits.swap(c));
/*      */     } else {
/*  510 */       Bits.putChar(paramLong, paramChar, this.bigEndian);
/*      */     }
/*  512 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putChar(char paramChar)
/*      */   {
/*  520 */     putChar(ix(nextPutIndex(2)), paramChar);
/*  521 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putChar(int paramInt, char paramChar)
/*      */   {
/*  529 */     putChar(ix(checkIndex(paramInt, 2)), paramChar);
/*  530 */     return this;
/*      */   }
/*      */   
/*      */ 
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
/*  544 */       return this.bigEndian ? new ByteBufferAsCharBufferB(this, -1, 0, m, m, i) : new ByteBufferAsCharBufferL(this, -1, 0, m, m, i);
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
/*  558 */     return this.nativeByteOrder ? new DirectCharBufferU(this, -1, 0, m, m, i) : new DirectCharBufferS(this, -1, 0, m, m, i);
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
/*      */   private short getShort(long paramLong)
/*      */   {
/*  578 */     if (unaligned) {
/*  579 */       short s = unsafe.getShort(paramLong);
/*  580 */       return this.nativeByteOrder ? s : Bits.swap(s);
/*      */     }
/*  582 */     return Bits.getShort(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public short getShort() {
/*  586 */     return getShort(ix(nextGetIndex(2)));
/*      */   }
/*      */   
/*      */   public short getShort(int paramInt) {
/*  590 */     return getShort(ix(checkIndex(paramInt, 2)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ByteBuffer putShort(long paramLong, short paramShort)
/*      */   {
/*  597 */     if (unaligned) {
/*  598 */       short s = paramShort;
/*  599 */       unsafe.putShort(paramLong, this.nativeByteOrder ? s : Bits.swap(s));
/*      */     } else {
/*  601 */       Bits.putShort(paramLong, paramShort, this.bigEndian);
/*      */     }
/*  603 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putShort(short paramShort)
/*      */   {
/*  611 */     putShort(ix(nextPutIndex(2)), paramShort);
/*  612 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putShort(int paramInt, short paramShort)
/*      */   {
/*  620 */     putShort(ix(checkIndex(paramInt, 2)), paramShort);
/*  621 */     return this;
/*      */   }
/*      */   
/*      */ 
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
/*  635 */       return this.bigEndian ? new ByteBufferAsShortBufferB(this, -1, 0, m, m, i) : new ByteBufferAsShortBufferL(this, -1, 0, m, m, i);
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
/*  649 */     return this.nativeByteOrder ? new DirectShortBufferU(this, -1, 0, m, m, i) : new DirectShortBufferS(this, -1, 0, m, m, i);
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
/*      */   private int getInt(long paramLong)
/*      */   {
/*  669 */     if (unaligned) {
/*  670 */       int i = unsafe.getInt(paramLong);
/*  671 */       return this.nativeByteOrder ? i : Bits.swap(i);
/*      */     }
/*  673 */     return Bits.getInt(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public int getInt() {
/*  677 */     return getInt(ix(nextGetIndex(4)));
/*      */   }
/*      */   
/*      */   public int getInt(int paramInt) {
/*  681 */     return getInt(ix(checkIndex(paramInt, 4)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ByteBuffer putInt(long paramLong, int paramInt)
/*      */   {
/*  688 */     if (unaligned) {
/*  689 */       int i = paramInt;
/*  690 */       unsafe.putInt(paramLong, this.nativeByteOrder ? i : Bits.swap(i));
/*      */     } else {
/*  692 */       Bits.putInt(paramLong, paramInt, this.bigEndian);
/*      */     }
/*  694 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putInt(int paramInt)
/*      */   {
/*  702 */     putInt(ix(nextPutIndex(4)), paramInt);
/*  703 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putInt(int paramInt1, int paramInt2)
/*      */   {
/*  711 */     putInt(ix(checkIndex(paramInt1, 4)), paramInt2);
/*  712 */     return this;
/*      */   }
/*      */   
/*      */ 
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
/*  726 */       return this.bigEndian ? new ByteBufferAsIntBufferB(this, -1, 0, m, m, i) : new ByteBufferAsIntBufferL(this, -1, 0, m, m, i);
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
/*  740 */     return this.nativeByteOrder ? new DirectIntBufferU(this, -1, 0, m, m, i) : new DirectIntBufferS(this, -1, 0, m, m, i);
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
/*      */   private long getLong(long paramLong)
/*      */   {
/*  760 */     if (unaligned) {
/*  761 */       long l = unsafe.getLong(paramLong);
/*  762 */       return this.nativeByteOrder ? l : Bits.swap(l);
/*      */     }
/*  764 */     return Bits.getLong(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public long getLong() {
/*  768 */     return getLong(ix(nextGetIndex(8)));
/*      */   }
/*      */   
/*      */   public long getLong(int paramInt) {
/*  772 */     return getLong(ix(checkIndex(paramInt, 8)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ByteBuffer putLong(long paramLong1, long paramLong2)
/*      */   {
/*  779 */     if (unaligned) {
/*  780 */       long l = paramLong2;
/*  781 */       unsafe.putLong(paramLong1, this.nativeByteOrder ? l : Bits.swap(l));
/*      */     } else {
/*  783 */       Bits.putLong(paramLong1, paramLong2, this.bigEndian);
/*      */     }
/*  785 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putLong(long paramLong)
/*      */   {
/*  793 */     putLong(ix(nextPutIndex(8)), paramLong);
/*  794 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putLong(int paramInt, long paramLong)
/*      */   {
/*  802 */     putLong(ix(checkIndex(paramInt, 8)), paramLong);
/*  803 */     return this;
/*      */   }
/*      */   
/*      */ 
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
/*  817 */       return this.bigEndian ? new ByteBufferAsLongBufferB(this, -1, 0, m, m, i) : new ByteBufferAsLongBufferL(this, -1, 0, m, m, i);
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
/*  831 */     return this.nativeByteOrder ? new DirectLongBufferU(this, -1, 0, m, m, i) : new DirectLongBufferS(this, -1, 0, m, m, i);
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
/*      */   private float getFloat(long paramLong)
/*      */   {
/*  851 */     if (unaligned) {
/*  852 */       int i = unsafe.getInt(paramLong);
/*  853 */       return Float.intBitsToFloat(this.nativeByteOrder ? i : Bits.swap(i));
/*      */     }
/*  855 */     return Bits.getFloat(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public float getFloat() {
/*  859 */     return getFloat(ix(nextGetIndex(4)));
/*      */   }
/*      */   
/*      */   public float getFloat(int paramInt) {
/*  863 */     return getFloat(ix(checkIndex(paramInt, 4)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ByteBuffer putFloat(long paramLong, float paramFloat)
/*      */   {
/*  870 */     if (unaligned) {
/*  871 */       int i = Float.floatToRawIntBits(paramFloat);
/*  872 */       unsafe.putInt(paramLong, this.nativeByteOrder ? i : Bits.swap(i));
/*      */     } else {
/*  874 */       Bits.putFloat(paramLong, paramFloat, this.bigEndian);
/*      */     }
/*  876 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putFloat(float paramFloat)
/*      */   {
/*  884 */     putFloat(ix(nextPutIndex(4)), paramFloat);
/*  885 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putFloat(int paramInt, float paramFloat)
/*      */   {
/*  893 */     putFloat(ix(checkIndex(paramInt, 4)), paramFloat);
/*  894 */     return this;
/*      */   }
/*      */   
/*      */ 
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
/*  908 */       return this.bigEndian ? new ByteBufferAsFloatBufferB(this, -1, 0, m, m, i) : new ByteBufferAsFloatBufferL(this, -1, 0, m, m, i);
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
/*  922 */     return this.nativeByteOrder ? new DirectFloatBufferU(this, -1, 0, m, m, i) : new DirectFloatBufferS(this, -1, 0, m, m, i);
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
/*      */   private double getDouble(long paramLong)
/*      */   {
/*  942 */     if (unaligned) {
/*  943 */       long l = unsafe.getLong(paramLong);
/*  944 */       return Double.longBitsToDouble(this.nativeByteOrder ? l : Bits.swap(l));
/*      */     }
/*  946 */     return Bits.getDouble(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public double getDouble() {
/*  950 */     return getDouble(ix(nextGetIndex(8)));
/*      */   }
/*      */   
/*      */   public double getDouble(int paramInt) {
/*  954 */     return getDouble(ix(checkIndex(paramInt, 8)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ByteBuffer putDouble(long paramLong, double paramDouble)
/*      */   {
/*  961 */     if (unaligned) {
/*  962 */       long l = Double.doubleToRawLongBits(paramDouble);
/*  963 */       unsafe.putLong(paramLong, this.nativeByteOrder ? l : Bits.swap(l));
/*      */     } else {
/*  965 */       Bits.putDouble(paramLong, paramDouble, this.bigEndian);
/*      */     }
/*  967 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putDouble(double paramDouble)
/*      */   {
/*  975 */     putDouble(ix(nextPutIndex(8)), paramDouble);
/*  976 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer putDouble(int paramInt, double paramDouble)
/*      */   {
/*  984 */     putDouble(ix(checkIndex(paramInt, 8)), paramDouble);
/*  985 */     return this;
/*      */   }
/*      */   
/*      */ 
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
/*  999 */       return this.bigEndian ? new ByteBufferAsDoubleBufferB(this, -1, 0, m, m, i) : new ByteBufferAsDoubleBufferL(this, -1, 0, m, m, i);
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
/* 1013 */     return this.nativeByteOrder ? new DirectDoubleBufferU(this, -1, 0, m, m, i) : new DirectDoubleBufferS(this, -1, 0, m, m, i);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/DirectByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */