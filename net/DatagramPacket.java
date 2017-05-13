/*     */ package java.net;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DatagramPacket
/*     */ {
/*     */   byte[] buf;
/*     */   int offset;
/*     */   int length;
/*     */   int bufLength;
/*     */   InetAddress address;
/*     */   int port;
/*     */   
/*     */   static
/*     */   {
/*  49 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  52 */         System.loadLibrary("net");
/*  53 */         return null;
/*     */       }
/*  55 */     });
/*  56 */     init();
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
/*     */   public DatagramPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  84 */     setData(paramArrayOfByte, paramInt1, paramInt2);
/*  85 */     this.address = null;
/*  86 */     this.port = -1;
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
/*     */   public DatagramPacket(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 100 */     this(paramArrayOfByte, 0, paramInt);
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
/*     */   public DatagramPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2, InetAddress paramInetAddress, int paramInt3)
/*     */   {
/* 121 */     setData(paramArrayOfByte, paramInt1, paramInt2);
/* 122 */     setAddress(paramInetAddress);
/* 123 */     setPort(paramInt3);
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
/*     */   public DatagramPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2, SocketAddress paramSocketAddress)
/*     */   {
/* 143 */     setData(paramArrayOfByte, paramInt1, paramInt2);
/* 144 */     setSocketAddress(paramSocketAddress);
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
/*     */   public DatagramPacket(byte[] paramArrayOfByte, int paramInt1, InetAddress paramInetAddress, int paramInt2)
/*     */   {
/* 161 */     this(paramArrayOfByte, 0, paramInt1, paramInetAddress, paramInt2);
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
/*     */   public DatagramPacket(byte[] paramArrayOfByte, int paramInt, SocketAddress paramSocketAddress)
/*     */   {
/* 178 */     this(paramArrayOfByte, 0, paramInt, paramSocketAddress);
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
/*     */   public synchronized InetAddress getAddress()
/*     */   {
/* 191 */     return this.address;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getPort()
/*     */   {
/* 203 */     return this.port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized byte[] getData()
/*     */   {
/* 215 */     return this.buf;
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
/*     */   public synchronized int getOffset()
/*     */   {
/* 228 */     return this.offset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getLength()
/*     */   {
/* 240 */     return this.length;
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
/*     */   public synchronized void setData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 264 */     if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt2 + paramInt1 < 0) || (paramInt2 + paramInt1 > paramArrayOfByte.length))
/*     */     {
/*     */ 
/* 267 */       throw new IllegalArgumentException("illegal length or offset");
/*     */     }
/* 269 */     this.buf = paramArrayOfByte;
/* 270 */     this.length = paramInt2;
/* 271 */     this.bufLength = paramInt2;
/* 272 */     this.offset = paramInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setAddress(InetAddress paramInetAddress)
/*     */   {
/* 283 */     this.address = paramInetAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setPort(int paramInt)
/*     */   {
/* 294 */     if ((paramInt < 0) || (paramInt > 65535)) {
/* 295 */       throw new IllegalArgumentException("Port out of range:" + paramInt);
/*     */     }
/* 297 */     this.port = paramInt;
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
/*     */   public synchronized void setSocketAddress(SocketAddress paramSocketAddress)
/*     */   {
/* 312 */     if ((paramSocketAddress == null) || (!(paramSocketAddress instanceof InetSocketAddress)))
/* 313 */       throw new IllegalArgumentException("unsupported address type");
/* 314 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 315 */     if (localInetSocketAddress.isUnresolved())
/* 316 */       throw new IllegalArgumentException("unresolved address");
/* 317 */     setAddress(localInetSocketAddress.getAddress());
/* 318 */     setPort(localInetSocketAddress.getPort());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized SocketAddress getSocketAddress()
/*     */   {
/* 330 */     return new InetSocketAddress(getAddress(), getPort());
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
/*     */   public synchronized void setData(byte[] paramArrayOfByte)
/*     */   {
/* 348 */     if (paramArrayOfByte == null) {
/* 349 */       throw new NullPointerException("null packet buffer");
/*     */     }
/* 351 */     this.buf = paramArrayOfByte;
/* 352 */     this.offset = 0;
/* 353 */     this.length = paramArrayOfByte.length;
/* 354 */     this.bufLength = paramArrayOfByte.length;
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
/*     */   public synchronized void setLength(int paramInt)
/*     */   {
/* 376 */     if ((paramInt + this.offset > this.buf.length) || (paramInt < 0) || (paramInt + this.offset < 0))
/*     */     {
/* 378 */       throw new IllegalArgumentException("illegal length");
/*     */     }
/* 380 */     this.length = paramInt;
/* 381 */     this.bufLength = this.length;
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/DatagramPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */