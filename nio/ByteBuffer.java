/*      */ package java.nio;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ByteBuffer
/*      */   extends Buffer
/*      */   implements Comparable<ByteBuffer>
/*      */ {
/*      */   final byte[] hb;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int offset;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isReadOnly;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   ByteBuffer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5)
/*      */   {
/*  281 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  282 */     this.hb = paramArrayOfByte;
/*  283 */     this.offset = paramInt5;
/*      */   }
/*      */   
/*      */ 
/*      */   ByteBuffer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  289 */     this(paramInt1, paramInt2, paramInt3, paramInt4, null, 0);
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
/*      */   public static ByteBuffer allocateDirect(int paramInt)
/*      */   {
/*  311 */     return new DirectByteBuffer(paramInt);
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
/*      */   public static ByteBuffer allocate(int paramInt)
/*      */   {
/*  333 */     if (paramInt < 0)
/*  334 */       throw new IllegalArgumentException();
/*  335 */     return new HeapByteBuffer(paramInt, paramInt);
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
/*      */   public static ByteBuffer wrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*      */     try
/*      */     {
/*  373 */       return new HeapByteBuffer(paramArrayOfByte, paramInt1, paramInt2);
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*  375 */       throw new IndexOutOfBoundsException();
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
/*      */   public static ByteBuffer wrap(byte[] paramArrayOfByte)
/*      */   {
/*  396 */     return wrap(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public abstract ByteBuffer slice();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract ByteBuffer duplicate();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract ByteBuffer asReadOnlyBuffer();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract byte get();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract ByteBuffer put(byte paramByte);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract byte get(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract ByteBuffer put(int paramInt, byte paramByte);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuffer get(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  686 */     checkBounds(paramInt1, paramInt2, paramArrayOfByte.length);
/*  687 */     if (paramInt2 > remaining())
/*  688 */       throw new BufferUnderflowException();
/*  689 */     int i = paramInt1 + paramInt2;
/*  690 */     for (int j = paramInt1; j < i; j++)
/*  691 */       paramArrayOfByte[j] = get();
/*  692 */     return this;
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
/*      */   public ByteBuffer get(byte[] paramArrayOfByte)
/*      */   {
/*  715 */     return get(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public ByteBuffer put(ByteBuffer paramByteBuffer)
/*      */   {
/*  763 */     if (paramByteBuffer == this)
/*  764 */       throw new IllegalArgumentException();
/*  765 */     if (isReadOnly())
/*  766 */       throw new ReadOnlyBufferException();
/*  767 */     int i = paramByteBuffer.remaining();
/*  768 */     if (i > remaining())
/*  769 */       throw new BufferOverflowException();
/*  770 */     for (int j = 0; j < i; j++)
/*  771 */       put(paramByteBuffer.get());
/*  772 */     return this;
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
/*      */   public ByteBuffer put(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  827 */     checkBounds(paramInt1, paramInt2, paramArrayOfByte.length);
/*  828 */     if (paramInt2 > remaining())
/*  829 */       throw new BufferOverflowException();
/*  830 */     int i = paramInt1 + paramInt2;
/*  831 */     for (int j = paramInt1; j < i; j++)
/*  832 */       put(paramArrayOfByte[j]);
/*  833 */     return this;
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
/*      */   public final ByteBuffer put(byte[] paramArrayOfByte)
/*      */   {
/*  859 */     return put(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean hasArray()
/*      */   {
/*  970 */     return (this.hb != null) && (!this.isReadOnly);
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
/*      */   public final byte[] array()
/*      */   {
/*  993 */     if (this.hb == null)
/*  994 */       throw new UnsupportedOperationException();
/*  995 */     if (this.isReadOnly)
/*  996 */       throw new ReadOnlyBufferException();
/*  997 */     return this.hb;
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
/*      */   public final int arrayOffset()
/*      */   {
/* 1021 */     if (this.hb == null)
/* 1022 */       throw new UnsupportedOperationException();
/* 1023 */     if (this.isReadOnly)
/* 1024 */       throw new ReadOnlyBufferException();
/* 1025 */     return this.offset;
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
/*      */   public abstract ByteBuffer compact();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract boolean isDirect();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1085 */     StringBuffer localStringBuffer = new StringBuffer();
/* 1086 */     localStringBuffer.append(getClass().getName());
/* 1087 */     localStringBuffer.append("[pos=");
/* 1088 */     localStringBuffer.append(position());
/* 1089 */     localStringBuffer.append(" lim=");
/* 1090 */     localStringBuffer.append(limit());
/* 1091 */     localStringBuffer.append(" cap=");
/* 1092 */     localStringBuffer.append(capacity());
/* 1093 */     localStringBuffer.append("]");
/* 1094 */     return localStringBuffer.toString();
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
/*      */   public int hashCode()
/*      */   {
/* 1116 */     int i = 1;
/* 1117 */     int j = position();
/* 1118 */     for (int k = limit() - 1; k >= j; k--)
/*      */     {
/*      */ 
/*      */ 
/* 1122 */       i = 31 * i + get(k);
/*      */     }
/* 1124 */     return i;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1160 */     if (this == paramObject)
/* 1161 */       return true;
/* 1162 */     if (!(paramObject instanceof ByteBuffer))
/* 1163 */       return false;
/* 1164 */     ByteBuffer localByteBuffer = (ByteBuffer)paramObject;
/* 1165 */     if (remaining() != localByteBuffer.remaining())
/* 1166 */       return false;
/* 1167 */     int i = position();
/* 1168 */     int j = limit() - 1; for (int k = localByteBuffer.limit() - 1; j >= i; k--) {
/* 1169 */       if (!equals(get(j), localByteBuffer.get(k))) {
/* 1170 */         return false;
/*      */       }
/* 1168 */       j--;
/*      */     }
/*      */     
/* 1171 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static boolean equals(byte paramByte1, byte paramByte2)
/*      */   {
/* 1178 */     return paramByte1 == paramByte2;
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
/*      */   public int compareTo(ByteBuffer paramByteBuffer)
/*      */   {
/* 1206 */     int i = position() + Math.min(remaining(), paramByteBuffer.remaining());
/* 1207 */     int j = position(); for (int k = paramByteBuffer.position(); j < i; k++) {
/* 1208 */       int m = compare(get(j), paramByteBuffer.get(k));
/* 1209 */       if (m != 0) {
/* 1210 */         return m;
/*      */       }
/* 1207 */       j++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1212 */     return remaining() - paramByteBuffer.remaining();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int compare(byte paramByte1, byte paramByte2)
/*      */   {
/* 1222 */     return Byte.compare(paramByte1, paramByte2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1443 */   boolean bigEndian = true;
/*      */   
/*      */ 
/* 1446 */   boolean nativeByteOrder = Bits.byteOrder() == ByteOrder.BIG_ENDIAN;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ByteOrder order()
/*      */   {
/* 1459 */     return this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
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
/*      */   public final ByteBuffer order(ByteOrder paramByteOrder)
/*      */   {
/* 1473 */     this.bigEndian = (paramByteOrder == ByteOrder.BIG_ENDIAN);
/*      */     
/* 1475 */     this.nativeByteOrder = (this.bigEndian == (Bits.byteOrder() == ByteOrder.BIG_ENDIAN));
/* 1476 */     return this;
/*      */   }
/*      */   
/*      */   abstract byte _get(int paramInt);
/*      */   
/*      */   abstract void _put(int paramInt, byte paramByte);
/*      */   
/*      */   public abstract char getChar();
/*      */   
/*      */   public abstract ByteBuffer putChar(char paramChar);
/*      */   
/*      */   public abstract char getChar(int paramInt);
/*      */   
/*      */   public abstract ByteBuffer putChar(int paramInt, char paramChar);
/*      */   
/*      */   public abstract CharBuffer asCharBuffer();
/*      */   
/*      */   public abstract short getShort();
/*      */   
/*      */   public abstract ByteBuffer putShort(short paramShort);
/*      */   
/*      */   public abstract short getShort(int paramInt);
/*      */   
/*      */   public abstract ByteBuffer putShort(int paramInt, short paramShort);
/*      */   
/*      */   public abstract ShortBuffer asShortBuffer();
/*      */   
/*      */   public abstract int getInt();
/*      */   
/*      */   public abstract ByteBuffer putInt(int paramInt);
/*      */   
/*      */   public abstract int getInt(int paramInt);
/*      */   
/*      */   public abstract ByteBuffer putInt(int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract IntBuffer asIntBuffer();
/*      */   
/*      */   public abstract long getLong();
/*      */   
/*      */   public abstract ByteBuffer putLong(long paramLong);
/*      */   
/*      */   public abstract long getLong(int paramInt);
/*      */   
/*      */   public abstract ByteBuffer putLong(int paramInt, long paramLong);
/*      */   
/*      */   public abstract LongBuffer asLongBuffer();
/*      */   
/*      */   public abstract float getFloat();
/*      */   
/*      */   public abstract ByteBuffer putFloat(float paramFloat);
/*      */   
/*      */   public abstract float getFloat(int paramInt);
/*      */   
/*      */   public abstract ByteBuffer putFloat(int paramInt, float paramFloat);
/*      */   
/*      */   public abstract FloatBuffer asFloatBuffer();
/*      */   
/*      */   public abstract double getDouble();
/*      */   
/*      */   public abstract ByteBuffer putDouble(double paramDouble);
/*      */   
/*      */   public abstract double getDouble(int paramInt);
/*      */   
/*      */   public abstract ByteBuffer putDouble(int paramInt, double paramDouble);
/*      */   
/*      */   public abstract DoubleBuffer asDoubleBuffer();
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/ByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */