/*      */ package java.io;
/*      */ 
/*      */ import java.nio.channels.FileChannel;
/*      */ import sun.nio.ch.FileChannelImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RandomAccessFile
/*      */   implements DataOutput, DataInput, Closeable
/*      */ {
/*      */   private FileDescriptor fd;
/*   62 */   private FileChannel channel = null;
/*      */   
/*      */ 
/*      */   private boolean rw;
/*      */   
/*      */ 
/*      */   private final String path;
/*      */   
/*      */ 
/*   71 */   private Object closeLock = new Object();
/*   72 */   private volatile boolean closed = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int O_RDONLY = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int O_RDWR = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int O_SYNC = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int O_DSYNC = 8;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public RandomAccessFile(String paramString1, String paramString2)
/*      */     throws FileNotFoundException
/*      */   {
/*  124 */     this(paramString1 != null ? new File(paramString1) : null, paramString2);
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
/*      */   public RandomAccessFile(File paramFile, String paramString)
/*      */     throws FileNotFoundException
/*      */   {
/*  206 */     String str = paramFile != null ? paramFile.getPath() : null;
/*  207 */     int i = -1;
/*  208 */     if (paramString.equals("r")) {
/*  209 */       i = 1;
/*  210 */     } else if (paramString.startsWith("rw")) {
/*  211 */       i = 2;
/*  212 */       this.rw = true;
/*  213 */       if (paramString.length() > 2) {
/*  214 */         if (paramString.equals("rws")) {
/*  215 */           i |= 0x4;
/*  216 */         } else if (paramString.equals("rwd")) {
/*  217 */           i |= 0x8;
/*      */         } else
/*  219 */           i = -1;
/*      */       }
/*      */     }
/*  222 */     if (i < 0) {
/*  223 */       throw new IllegalArgumentException("Illegal mode \"" + paramString + "\" must be one of " + "\"r\", \"rw\", \"rws\"," + " or \"rwd\"");
/*      */     }
/*      */     
/*      */ 
/*  227 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  228 */     if (localSecurityManager != null) {
/*  229 */       localSecurityManager.checkRead(str);
/*  230 */       if (this.rw) {
/*  231 */         localSecurityManager.checkWrite(str);
/*      */       }
/*      */     }
/*  234 */     if (str == null) {
/*  235 */       throw new NullPointerException();
/*      */     }
/*  237 */     if (paramFile.isInvalid()) {
/*  238 */       throw new FileNotFoundException("Invalid file path");
/*      */     }
/*  240 */     this.fd = new FileDescriptor();
/*  241 */     this.fd.attach(this);
/*  242 */     this.path = str;
/*  243 */     open(str, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final FileDescriptor getFD()
/*      */     throws IOException
/*      */   {
/*  255 */     if (this.fd != null) {
/*  256 */       return this.fd;
/*      */     }
/*  258 */     throw new IOException();
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
/*      */   public final FileChannel getChannel()
/*      */   {
/*  280 */     synchronized (this) {
/*  281 */       if (this.channel == null) {
/*  282 */         this.channel = FileChannelImpl.open(this.fd, this.path, true, this.rw, this);
/*      */       }
/*  284 */       return this.channel;
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
/*      */   private native void open(String paramString, int paramInt)
/*      */     throws FileNotFoundException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int read()
/*      */     throws IOException
/*      */   {
/*  320 */     return read0();
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
/*      */   private native int read0()
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native int readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  360 */     return readBytes(paramArrayOfByte, paramInt1, paramInt2);
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
/*      */   public int read(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  383 */     return readBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public final void readFully(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  399 */     readFully(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public final void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  417 */     int i = 0;
/*      */     do {
/*  419 */       int j = read(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
/*  420 */       if (j < 0)
/*  421 */         throw new EOFException();
/*  422 */       i += j;
/*  423 */     } while (i < paramInt2);
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
/*      */   public int skipBytes(int paramInt)
/*      */     throws IOException
/*      */   {
/*  447 */     if (paramInt <= 0) {
/*  448 */       return 0;
/*      */     }
/*  450 */     long l1 = getFilePointer();
/*  451 */     long l2 = length();
/*  452 */     long l3 = l1 + paramInt;
/*  453 */     if (l3 > l2) {
/*  454 */       l3 = l2;
/*      */     }
/*  456 */     seek(l3);
/*      */     
/*      */ 
/*  459 */     return (int)(l3 - l1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void write(int paramInt)
/*      */     throws IOException
/*      */   {
/*  472 */     write0(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native void write0(int paramInt)
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native void writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void write(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  495 */     writeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  508 */     writeBytes(paramArrayOfByte, paramInt1, paramInt2);
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
/*      */   public native long getFilePointer()
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void seek(long paramLong)
/*      */     throws IOException
/*      */   {
/*  537 */     if (paramLong < 0L) {
/*  538 */       throw new IOException("Negative seek offset");
/*      */     }
/*  540 */     seek0(paramLong);
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
/*      */   private native void seek0(long paramLong)
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native long length()
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native void setLength(long paramLong)
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  590 */     synchronized (this.closeLock) {
/*  591 */       if (this.closed) {
/*  592 */         return;
/*      */       }
/*  594 */       this.closed = true;
/*      */     }
/*  596 */     if (this.channel != null) {
/*  597 */       this.channel.close();
/*      */     }
/*      */     
/*  600 */     this.fd.closeAll(new Closeable() {
/*      */       public void close() throws IOException {
/*  602 */         RandomAccessFile.this.close0();
/*      */       }
/*      */     });
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
/*      */   public final boolean readBoolean()
/*      */     throws IOException
/*      */   {
/*  625 */     int i = read();
/*  626 */     if (i < 0)
/*  627 */       throw new EOFException();
/*  628 */     return i != 0;
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
/*      */   public final byte readByte()
/*      */     throws IOException
/*      */   {
/*  650 */     int i = read();
/*  651 */     if (i < 0)
/*  652 */       throw new EOFException();
/*  653 */     return (byte)i;
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
/*      */   public final int readUnsignedByte()
/*      */     throws IOException
/*      */   {
/*  670 */     int i = read();
/*  671 */     if (i < 0)
/*  672 */       throw new EOFException();
/*  673 */     return i;
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
/*      */   public final short readShort()
/*      */     throws IOException
/*      */   {
/*  697 */     int i = read();
/*  698 */     int j = read();
/*  699 */     if ((i | j) < 0)
/*  700 */       throw new EOFException();
/*  701 */     return (short)((i << 8) + (j << 0));
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
/*      */   public final int readUnsignedShort()
/*      */     throws IOException
/*      */   {
/*  725 */     int i = read();
/*  726 */     int j = read();
/*  727 */     if ((i | j) < 0)
/*  728 */       throw new EOFException();
/*  729 */     return (i << 8) + (j << 0);
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
/*      */   public final char readChar()
/*      */     throws IOException
/*      */   {
/*  753 */     int i = read();
/*  754 */     int j = read();
/*  755 */     if ((i | j) < 0)
/*  756 */       throw new EOFException();
/*  757 */     return (char)((i << 8) + (j << 0));
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
/*      */   public final int readInt()
/*      */     throws IOException
/*      */   {
/*  781 */     int i = read();
/*  782 */     int j = read();
/*  783 */     int k = read();
/*  784 */     int m = read();
/*  785 */     if ((i | j | k | m) < 0)
/*  786 */       throw new EOFException();
/*  787 */     return (i << 24) + (j << 16) + (k << 8) + (m << 0);
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
/*      */   public final long readLong()
/*      */     throws IOException
/*      */   {
/*  819 */     return (readInt() << 32) + (readInt() & 0xFFFFFFFF);
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
/*      */   public final float readFloat()
/*      */     throws IOException
/*      */   {
/*  842 */     return Float.intBitsToFloat(readInt());
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
/*      */   public final double readDouble()
/*      */     throws IOException
/*      */   {
/*  865 */     return Double.longBitsToDouble(readLong());
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
/*      */   public final String readLine()
/*      */     throws IOException
/*      */   {
/*  893 */     StringBuffer localStringBuffer = new StringBuffer();
/*  894 */     int i = -1;
/*  895 */     int j = 0;
/*      */     
/*  897 */     while (j == 0) {
/*  898 */       switch (i = read()) {
/*      */       case -1: 
/*      */       case 10: 
/*  901 */         j = 1;
/*  902 */         break;
/*      */       case 13: 
/*  904 */         j = 1;
/*  905 */         long l = getFilePointer();
/*  906 */         if (read() != 10) {
/*  907 */           seek(l);
/*      */         }
/*      */         break;
/*      */       default: 
/*  911 */         localStringBuffer.append((char)i);
/*      */       }
/*      */       
/*      */     }
/*      */     
/*  916 */     if ((i == -1) && (localStringBuffer.length() == 0)) {
/*  917 */       return null;
/*      */     }
/*  919 */     return localStringBuffer.toString();
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
/*      */   public final String readUTF()
/*      */     throws IOException
/*      */   {
/*  948 */     return DataInputStream.readUTF(this);
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
/*      */   public final void writeBoolean(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  962 */     write(paramBoolean ? 1 : 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeByte(int paramInt)
/*      */     throws IOException
/*      */   {
/*  974 */     write(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeShort(int paramInt)
/*      */     throws IOException
/*      */   {
/*  986 */     write(paramInt >>> 8 & 0xFF);
/*  987 */     write(paramInt >>> 0 & 0xFF);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeChar(int paramInt)
/*      */     throws IOException
/*      */   {
/* 1000 */     write(paramInt >>> 8 & 0xFF);
/* 1001 */     write(paramInt >>> 0 & 0xFF);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeInt(int paramInt)
/*      */     throws IOException
/*      */   {
/* 1013 */     write(paramInt >>> 24 & 0xFF);
/* 1014 */     write(paramInt >>> 16 & 0xFF);
/* 1015 */     write(paramInt >>> 8 & 0xFF);
/* 1016 */     write(paramInt >>> 0 & 0xFF);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeLong(long paramLong)
/*      */     throws IOException
/*      */   {
/* 1028 */     write((int)(paramLong >>> 56) & 0xFF);
/* 1029 */     write((int)(paramLong >>> 48) & 0xFF);
/* 1030 */     write((int)(paramLong >>> 40) & 0xFF);
/* 1031 */     write((int)(paramLong >>> 32) & 0xFF);
/* 1032 */     write((int)(paramLong >>> 24) & 0xFF);
/* 1033 */     write((int)(paramLong >>> 16) & 0xFF);
/* 1034 */     write((int)(paramLong >>> 8) & 0xFF);
/* 1035 */     write((int)(paramLong >>> 0) & 0xFF);
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
/*      */   public final void writeFloat(float paramFloat)
/*      */     throws IOException
/*      */   {
/* 1051 */     writeInt(Float.floatToIntBits(paramFloat));
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
/*      */   public final void writeDouble(double paramDouble)
/*      */     throws IOException
/*      */   {
/* 1066 */     writeLong(Double.doubleToLongBits(paramDouble));
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
/*      */   public final void writeBytes(String paramString)
/*      */     throws IOException
/*      */   {
/* 1080 */     int i = paramString.length();
/* 1081 */     byte[] arrayOfByte = new byte[i];
/* 1082 */     paramString.getBytes(0, i, arrayOfByte, 0);
/* 1083 */     writeBytes(arrayOfByte, 0, i);
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
/*      */   public final void writeChars(String paramString)
/*      */     throws IOException
/*      */   {
/* 1097 */     int i = paramString.length();
/* 1098 */     int j = 2 * i;
/* 1099 */     byte[] arrayOfByte = new byte[j];
/* 1100 */     char[] arrayOfChar = new char[i];
/* 1101 */     paramString.getChars(0, i, arrayOfChar, 0);
/* 1102 */     int k = 0; for (int m = 0; k < i; k++) {
/* 1103 */       arrayOfByte[(m++)] = ((byte)(arrayOfChar[k] >>> '\b'));
/* 1104 */       arrayOfByte[(m++)] = ((byte)(arrayOfChar[k] >>> '\000'));
/*      */     }
/* 1106 */     writeBytes(arrayOfByte, 0, j);
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
/*      */   public final void writeUTF(String paramString)
/*      */     throws IOException
/*      */   {
/* 1126 */     DataOutputStream.writeUTF(paramString, this);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   private native void close0()
/*      */     throws IOException;
/*      */   
/*      */   static {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/RandomAccessFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */