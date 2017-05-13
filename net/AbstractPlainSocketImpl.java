/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.net.ConnectionResetException;
/*     */ import sun.net.NetHooks;
/*     */ import sun.net.ResourceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPlainSocketImpl
/*     */   extends SocketImpl
/*     */ {
/*     */   int timeout;
/*     */   private int trafficClass;
/*  51 */   private boolean shut_rd = false;
/*  52 */   private boolean shut_wr = false;
/*     */   
/*  54 */   private SocketInputStream socketInputStream = null;
/*  55 */   private SocketOutputStream socketOutputStream = null;
/*     */   
/*     */ 
/*  58 */   protected int fdUseCount = 0;
/*     */   
/*     */ 
/*  61 */   protected final Object fdLock = new Object();
/*     */   
/*     */ 
/*  64 */   protected boolean closePending = false;
/*     */   
/*     */ 
/*  67 */   private int CONNECTION_NOT_RESET = 0;
/*  68 */   private int CONNECTION_RESET_PENDING = 1;
/*  69 */   private int CONNECTION_RESET = 2;
/*     */   private int resetState;
/*  71 */   private final Object resetLock = new Object();
/*     */   
/*     */   protected boolean stream;
/*     */   
/*     */   public static final int SHUT_RD = 0;
/*     */   
/*     */   public static final int SHUT_WR = 1;
/*     */   
/*     */   static
/*     */   {
/*  81 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  84 */         System.loadLibrary("net");
/*  85 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected synchronized void create(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  95 */     this.stream = paramBoolean;
/*  96 */     if (!paramBoolean) {
/*  97 */       ResourceManager.beforeUdpCreate();
/*     */       
/*  99 */       this.fd = new FileDescriptor();
/*     */       try {
/* 101 */         socketCreate(false);
/*     */       } catch (IOException localIOException) {
/* 103 */         ResourceManager.afterUdpClose();
/* 104 */         this.fd = null;
/* 105 */         throw localIOException;
/*     */       }
/*     */     } else {
/* 108 */       this.fd = new FileDescriptor();
/* 109 */       socketCreate(true);
/*     */     }
/* 111 */     if (this.socket != null)
/* 112 */       this.socket.setCreated();
/* 113 */     if (this.serverSocket != null) {
/* 114 */       this.serverSocket.setCreated();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void connect(String paramString, int paramInt)
/*     */     throws UnknownHostException, IOException
/*     */   {
/* 126 */     int i = 0;
/*     */     try {
/* 128 */       InetAddress localInetAddress = InetAddress.getByName(paramString);
/* 129 */       this.port = paramInt;
/* 130 */       this.address = localInetAddress;
/*     */       
/* 132 */       connectToAddress(localInetAddress, paramInt, this.timeout);
/* 133 */       i = 1; return;
/*     */     } finally {
/* 135 */       if (i == 0) {
/*     */         try {
/* 137 */           close();
/*     */         }
/*     */         catch (IOException localIOException2) {}
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
/*     */   protected void connect(InetAddress paramInetAddress, int paramInt)
/*     */     throws IOException
/*     */   {
/* 153 */     this.port = paramInt;
/* 154 */     this.address = paramInetAddress;
/*     */     try
/*     */     {
/* 157 */       connectToAddress(paramInetAddress, paramInt, this.timeout);
/* 158 */       return;
/*     */     }
/*     */     catch (IOException localIOException) {
/* 161 */       close();
/* 162 */       throw localIOException;
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
/*     */   protected void connect(SocketAddress paramSocketAddress, int paramInt)
/*     */     throws IOException
/*     */   {
/* 178 */     int i = 0;
/*     */     try {
/* 180 */       if ((paramSocketAddress == null) || (!(paramSocketAddress instanceof InetSocketAddress)))
/* 181 */         throw new IllegalArgumentException("unsupported address type");
/* 182 */       InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 183 */       if (localInetSocketAddress.isUnresolved())
/* 184 */         throw new UnknownHostException(localInetSocketAddress.getHostName());
/* 185 */       this.port = localInetSocketAddress.getPort();
/* 186 */       this.address = localInetSocketAddress.getAddress();
/*     */       
/* 188 */       connectToAddress(this.address, this.port, paramInt);
/* 189 */       i = 1; return;
/*     */     } finally {
/* 191 */       if (i == 0) {
/*     */         try {
/* 193 */           close();
/*     */         }
/*     */         catch (IOException localIOException2) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void connectToAddress(InetAddress paramInetAddress, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 203 */     if (paramInetAddress.isAnyLocalAddress()) {
/* 204 */       doConnect(InetAddress.getLocalHost(), paramInt1, paramInt2);
/*     */     } else {
/* 206 */       doConnect(paramInetAddress, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOption(int paramInt, Object paramObject) throws SocketException {
/* 211 */     if (isClosedOrPending()) {
/* 212 */       throw new SocketException("Socket Closed");
/*     */     }
/* 214 */     boolean bool = true;
/* 215 */     switch (paramInt)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */     case 128: 
/* 221 */       if ((paramObject == null) || ((!(paramObject instanceof Integer)) && (!(paramObject instanceof Boolean))))
/* 222 */         throw new SocketException("Bad parameter for option");
/* 223 */       if ((paramObject instanceof Boolean))
/*     */       {
/* 225 */         bool = false;
/*     */       }
/*     */       break;
/*     */     case 4102: 
/* 229 */       if ((paramObject == null) || (!(paramObject instanceof Integer)))
/* 230 */         throw new SocketException("Bad parameter for SO_TIMEOUT");
/* 231 */       int i = ((Integer)paramObject).intValue();
/* 232 */       if (i < 0)
/* 233 */         throw new IllegalArgumentException("timeout < 0");
/* 234 */       this.timeout = i;
/* 235 */       break;
/*     */     case 3: 
/* 237 */       if ((paramObject == null) || (!(paramObject instanceof Integer))) {
/* 238 */         throw new SocketException("bad argument for IP_TOS");
/*     */       }
/* 240 */       this.trafficClass = ((Integer)paramObject).intValue();
/* 241 */       break;
/*     */     case 15: 
/* 243 */       throw new SocketException("Cannot re-bind socket");
/*     */     case 1: 
/* 245 */       if ((paramObject == null) || (!(paramObject instanceof Boolean)))
/* 246 */         throw new SocketException("bad parameter for TCP_NODELAY");
/* 247 */       bool = ((Boolean)paramObject).booleanValue();
/* 248 */       break;
/*     */     case 4097: 
/*     */     case 4098: 
/* 251 */       if ((paramObject == null) || (!(paramObject instanceof Integer)) || 
/* 252 */         (((Integer)paramObject).intValue() <= 0)) {
/* 253 */         throw new SocketException("bad parameter for SO_SNDBUF or SO_RCVBUF");
/*     */       }
/*     */       
/*     */       break;
/*     */     case 8: 
/* 258 */       if ((paramObject == null) || (!(paramObject instanceof Boolean)))
/* 259 */         throw new SocketException("bad parameter for SO_KEEPALIVE");
/* 260 */       bool = ((Boolean)paramObject).booleanValue();
/* 261 */       break;
/*     */     case 4099: 
/* 263 */       if ((paramObject == null) || (!(paramObject instanceof Boolean)))
/* 264 */         throw new SocketException("bad parameter for SO_OOBINLINE");
/* 265 */       bool = ((Boolean)paramObject).booleanValue();
/* 266 */       break;
/*     */     case 4: 
/* 268 */       if ((paramObject == null) || (!(paramObject instanceof Boolean)))
/* 269 */         throw new SocketException("bad parameter for SO_REUSEADDR");
/* 270 */       bool = ((Boolean)paramObject).booleanValue();
/* 271 */       break;
/*     */     default: 
/* 273 */       throw new SocketException("unrecognized TCP option: " + paramInt); }
/*     */     
/* 275 */     socketSetOption(paramInt, bool, paramObject);
/*     */   }
/*     */   
/* 278 */   public Object getOption(int paramInt) throws SocketException { if (isClosedOrPending()) {
/* 279 */       throw new SocketException("Socket Closed");
/*     */     }
/* 281 */     if (paramInt == 4102) {
/* 282 */       return new Integer(this.timeout);
/*     */     }
/* 284 */     int i = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 293 */     switch (paramInt) {
/*     */     case 1: 
/* 295 */       i = socketGetOption(paramInt, null);
/* 296 */       return Boolean.valueOf(i != -1);
/*     */     case 4099: 
/* 298 */       i = socketGetOption(paramInt, null);
/* 299 */       return Boolean.valueOf(i != -1);
/*     */     case 128: 
/* 301 */       i = socketGetOption(paramInt, null);
/* 302 */       return i == -1 ? Boolean.FALSE : new Integer(i);
/*     */     case 4: 
/* 304 */       i = socketGetOption(paramInt, null);
/* 305 */       return Boolean.valueOf(i != -1);
/*     */     case 15: 
/* 307 */       InetAddressContainer localInetAddressContainer = new InetAddressContainer();
/* 308 */       i = socketGetOption(paramInt, localInetAddressContainer);
/* 309 */       return localInetAddressContainer.addr;
/*     */     case 4097: 
/*     */     case 4098: 
/* 312 */       i = socketGetOption(paramInt, null);
/* 313 */       return new Integer(i);
/*     */     case 3: 
/* 315 */       i = socketGetOption(paramInt, null);
/* 316 */       if (i == -1) {
/* 317 */         return new Integer(this.trafficClass);
/*     */       }
/* 319 */       return new Integer(i);
/*     */     
/*     */     case 8: 
/* 322 */       i = socketGetOption(paramInt, null);
/* 323 */       return Boolean.valueOf(i != -1);
/*     */     }
/*     */     
/* 326 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   synchronized void doConnect(InetAddress paramInetAddress, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 337 */     synchronized (this.fdLock) {
/* 338 */       if ((!this.closePending) && ((this.socket == null) || (!this.socket.isBound()))) {
/* 339 */         NetHooks.beforeTcpConnect(this.fd, paramInetAddress, paramInt1);
/*     */       }
/*     */     }
/*     */     try {
/* 343 */       acquireFD();
/*     */       try {
/* 345 */         socketConnect(paramInetAddress, paramInt1, paramInt2);
/*     */         
/* 347 */         synchronized (this.fdLock) {
/* 348 */           if (this.closePending) {
/* 349 */             throw new SocketException("Socket closed");
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 356 */         if (this.socket != null) {
/* 357 */           this.socket.setBound();
/* 358 */           this.socket.setConnected();
/*     */         }
/*     */       } finally {
/* 361 */         releaseFD();
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 364 */       close();
/* 365 */       throw localIOException;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized void bind(InetAddress paramInetAddress, int paramInt)
/*     */     throws IOException
/*     */   {
/* 377 */     synchronized (this.fdLock) {
/* 378 */       if ((!this.closePending) && ((this.socket == null) || (!this.socket.isBound()))) {
/* 379 */         NetHooks.beforeTcpBind(this.fd, paramInetAddress, paramInt);
/*     */       }
/*     */     }
/* 382 */     socketBind(paramInetAddress, paramInt);
/* 383 */     if (this.socket != null)
/* 384 */       this.socket.setBound();
/* 385 */     if (this.serverSocket != null) {
/* 386 */       this.serverSocket.setBound();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected synchronized void listen(int paramInt)
/*     */     throws IOException
/*     */   {
/* 394 */     socketListen(paramInt);
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
/*     */   protected synchronized InputStream getInputStream()
/*     */     throws IOException
/*     */   {
/* 414 */     synchronized (this.fdLock) {
/* 415 */       if (isClosedOrPending())
/* 416 */         throw new IOException("Socket Closed");
/* 417 */       if (this.shut_rd)
/* 418 */         throw new IOException("Socket input is shutdown");
/* 419 */       if (this.socketInputStream == null)
/* 420 */         this.socketInputStream = new SocketInputStream(this);
/*     */     }
/* 422 */     return this.socketInputStream;
/*     */   }
/*     */   
/*     */   void setInputStream(SocketInputStream paramSocketInputStream) {
/* 426 */     this.socketInputStream = paramSocketInputStream;
/*     */   }
/*     */   
/*     */ 
/*     */   protected synchronized OutputStream getOutputStream()
/*     */     throws IOException
/*     */   {
/* 433 */     synchronized (this.fdLock) {
/* 434 */       if (isClosedOrPending())
/* 435 */         throw new IOException("Socket Closed");
/* 436 */       if (this.shut_wr)
/* 437 */         throw new IOException("Socket output is shutdown");
/* 438 */       if (this.socketOutputStream == null)
/* 439 */         this.socketOutputStream = new SocketOutputStream(this);
/*     */     }
/* 441 */     return this.socketOutputStream;
/*     */   }
/*     */   
/*     */   void setFileDescriptor(FileDescriptor paramFileDescriptor) {
/* 445 */     this.fd = paramFileDescriptor;
/*     */   }
/*     */   
/*     */   void setAddress(InetAddress paramInetAddress) {
/* 449 */     this.address = paramInetAddress;
/*     */   }
/*     */   
/*     */   void setPort(int paramInt) {
/* 453 */     this.port = paramInt;
/*     */   }
/*     */   
/*     */   void setLocalPort(int paramInt) {
/* 457 */     this.localport = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */   protected synchronized int available()
/*     */     throws IOException
/*     */   {
/* 464 */     if (isClosedOrPending()) {
/* 465 */       throw new IOException("Stream closed.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 472 */     if ((isConnectionReset()) || (this.shut_rd)) {
/* 473 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 483 */     int i = 0;
/*     */     try {
/* 485 */       i = socketAvailable();
/* 486 */       if ((i == 0) && (isConnectionResetPending())) {
/* 487 */         setConnectionReset();
/*     */       }
/*     */     } catch (ConnectionResetException localConnectionResetException1) {
/* 490 */       setConnectionResetPending();
/*     */       try {
/* 492 */         i = socketAvailable();
/* 493 */         if (i == 0) {
/* 494 */           setConnectionReset();
/*     */         }
/*     */       }
/*     */       catch (ConnectionResetException localConnectionResetException2) {}
/*     */     }
/* 499 */     return i;
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
/*     */   void reset()
/*     */     throws IOException
/*     */   {
/* 549 */     if (this.fd != null) {
/* 550 */       socketClose();
/*     */     }
/* 552 */     this.fd = null;
/* 553 */     super.reset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void shutdownInput()
/*     */     throws IOException
/*     */   {
/* 561 */     if (this.fd != null) {
/* 562 */       socketShutdown(0);
/* 563 */       if (this.socketInputStream != null) {
/* 564 */         this.socketInputStream.setEOF(true);
/*     */       }
/* 566 */       this.shut_rd = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void shutdownOutput()
/*     */     throws IOException
/*     */   {
/* 574 */     if (this.fd != null) {
/* 575 */       socketShutdown(1);
/* 576 */       this.shut_wr = true;
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean supportsUrgentData() {
/* 581 */     return true;
/*     */   }
/*     */   
/*     */   protected void sendUrgentData(int paramInt) throws IOException {
/* 585 */     if (this.fd == null) {
/* 586 */       throw new IOException("Socket Closed");
/*     */     }
/* 588 */     socketSendUrgentData(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void finalize()
/*     */     throws IOException
/*     */   {
/* 595 */     close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   FileDescriptor acquireFD()
/*     */   {
/* 605 */     synchronized (this.fdLock) {
/* 606 */       this.fdUseCount += 1;
/* 607 */       return this.fd;
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
/*     */   public boolean isConnectionReset()
/*     */   {
/* 633 */     synchronized (this.resetLock) {
/* 634 */       return this.resetState == this.CONNECTION_RESET;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isConnectionResetPending() {
/* 639 */     synchronized (this.resetLock) {
/* 640 */       return this.resetState == this.CONNECTION_RESET_PENDING;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setConnectionReset() {
/* 645 */     synchronized (this.resetLock) {
/* 646 */       this.resetState = this.CONNECTION_RESET;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setConnectionResetPending() {
/* 651 */     synchronized (this.resetLock) {
/* 652 */       if (this.resetState == this.CONNECTION_NOT_RESET) {
/* 653 */         this.resetState = this.CONNECTION_RESET_PENDING;
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
/*     */   public boolean isClosedOrPending()
/*     */   {
/* 667 */     synchronized (this.fdLock) {
/* 668 */       if ((this.closePending) || (this.fd == null)) {
/* 669 */         return true;
/*     */       }
/* 671 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTimeout()
/*     */   {
/* 680 */     return this.timeout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void socketPreClose()
/*     */     throws IOException
/*     */   {
/* 688 */     socketClose0(true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void socketClose()
/*     */     throws IOException
/*     */   {
/* 695 */     socketClose0(false);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   protected void accept(SocketImpl paramSocketImpl)
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 82	java/net/AbstractPlainSocketImpl:acquireFD	()Ljava/io/FileDescriptor;
/*     */     //   4: pop
/*     */     //   5: aload_0
/*     */     //   6: aload_1
/*     */     //   7: invokevirtual 92	java/net/AbstractPlainSocketImpl:socketAccept	(Ljava/net/SocketImpl;)V
/*     */     //   10: aload_0
/*     */     //   11: invokevirtual 87	java/net/AbstractPlainSocketImpl:releaseFD	()V
/*     */     //   14: goto +10 -> 24
/*     */     //   17: astore_2
/*     */     //   18: aload_0
/*     */     //   19: invokevirtual 87	java/net/AbstractPlainSocketImpl:releaseFD	()V
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     //   24: return
/*     */     // Line number table:
/*     */     //   Java source line #402	-> byte code offset #0
/*     */     //   Java source line #404	-> byte code offset #5
/*     */     //   Java source line #406	-> byte code offset #10
/*     */     //   Java source line #407	-> byte code offset #14
/*     */     //   Java source line #406	-> byte code offset #17
/*     */     //   Java source line #408	-> byte code offset #24
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	AbstractPlainSocketImpl
/*     */     //   0	25	1	paramSocketImpl	SocketImpl
/*     */     //   17	6	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	10	17	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   protected void close()
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 9	java/net/AbstractPlainSocketImpl:fdLock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 19	java/net/AbstractPlainSocketImpl:fd	Ljava/io/FileDescriptor;
/*     */     //   11: ifnull +87 -> 98
/*     */     //   14: aload_0
/*     */     //   15: getfield 15	java/net/AbstractPlainSocketImpl:stream	Z
/*     */     //   18: ifne +6 -> 24
/*     */     //   21: invokestatic 22	sun/net/ResourceManager:afterUdpClose	()V
/*     */     //   24: aload_0
/*     */     //   25: getfield 6	java/net/AbstractPlainSocketImpl:fdUseCount	I
/*     */     //   28: ifne +44 -> 72
/*     */     //   31: aload_0
/*     */     //   32: getfield 10	java/net/AbstractPlainSocketImpl:closePending	Z
/*     */     //   35: ifeq +6 -> 41
/*     */     //   38: aload_1
/*     */     //   39: monitorexit
/*     */     //   40: return
/*     */     //   41: aload_0
/*     */     //   42: iconst_1
/*     */     //   43: putfield 10	java/net/AbstractPlainSocketImpl:closePending	Z
/*     */     //   46: aload_0
/*     */     //   47: invokespecial 108	java/net/AbstractPlainSocketImpl:socketPreClose	()V
/*     */     //   50: aload_0
/*     */     //   51: invokevirtual 109	java/net/AbstractPlainSocketImpl:socketClose	()V
/*     */     //   54: goto +10 -> 64
/*     */     //   57: astore_2
/*     */     //   58: aload_0
/*     */     //   59: invokevirtual 109	java/net/AbstractPlainSocketImpl:socketClose	()V
/*     */     //   62: aload_2
/*     */     //   63: athrow
/*     */     //   64: aload_0
/*     */     //   65: aconst_null
/*     */     //   66: putfield 19	java/net/AbstractPlainSocketImpl:fd	Ljava/io/FileDescriptor;
/*     */     //   69: aload_1
/*     */     //   70: monitorexit
/*     */     //   71: return
/*     */     //   72: aload_0
/*     */     //   73: getfield 10	java/net/AbstractPlainSocketImpl:closePending	Z
/*     */     //   76: ifne +22 -> 98
/*     */     //   79: aload_0
/*     */     //   80: iconst_1
/*     */     //   81: putfield 10	java/net/AbstractPlainSocketImpl:closePending	Z
/*     */     //   84: aload_0
/*     */     //   85: dup
/*     */     //   86: getfield 6	java/net/AbstractPlainSocketImpl:fdUseCount	I
/*     */     //   89: iconst_1
/*     */     //   90: isub
/*     */     //   91: putfield 6	java/net/AbstractPlainSocketImpl:fdUseCount	I
/*     */     //   94: aload_0
/*     */     //   95: invokespecial 108	java/net/AbstractPlainSocketImpl:socketPreClose	()V
/*     */     //   98: aload_1
/*     */     //   99: monitorexit
/*     */     //   100: goto +8 -> 108
/*     */     //   103: astore_3
/*     */     //   104: aload_1
/*     */     //   105: monitorexit
/*     */     //   106: aload_3
/*     */     //   107: athrow
/*     */     //   108: return
/*     */     // Line number table:
/*     */     //   Java source line #506	-> byte code offset #0
/*     */     //   Java source line #507	-> byte code offset #7
/*     */     //   Java source line #508	-> byte code offset #14
/*     */     //   Java source line #509	-> byte code offset #21
/*     */     //   Java source line #511	-> byte code offset #24
/*     */     //   Java source line #512	-> byte code offset #31
/*     */     //   Java source line #513	-> byte code offset #38
/*     */     //   Java source line #515	-> byte code offset #41
/*     */     //   Java source line #525	-> byte code offset #46
/*     */     //   Java source line #527	-> byte code offset #50
/*     */     //   Java source line #528	-> byte code offset #54
/*     */     //   Java source line #527	-> byte code offset #57
/*     */     //   Java source line #529	-> byte code offset #64
/*     */     //   Java source line #530	-> byte code offset #69
/*     */     //   Java source line #538	-> byte code offset #72
/*     */     //   Java source line #539	-> byte code offset #79
/*     */     //   Java source line #540	-> byte code offset #84
/*     */     //   Java source line #541	-> byte code offset #94
/*     */     //   Java source line #545	-> byte code offset #98
/*     */     //   Java source line #546	-> byte code offset #108
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	109	0	this	AbstractPlainSocketImpl
/*     */     //   5	100	1	Ljava/lang/Object;	Object
/*     */     //   57	6	2	localObject1	Object
/*     */     //   103	4	3	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   46	50	57	finally
/*     */     //   7	40	103	finally
/*     */     //   41	71	103	finally
/*     */     //   72	100	103	finally
/*     */     //   103	106	103	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   void releaseFD()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 9	java/net/AbstractPlainSocketImpl:fdLock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: dup
/*     */     //   9: getfield 6	java/net/AbstractPlainSocketImpl:fdUseCount	I
/*     */     //   12: iconst_1
/*     */     //   13: isub
/*     */     //   14: putfield 6	java/net/AbstractPlainSocketImpl:fdUseCount	I
/*     */     //   17: aload_0
/*     */     //   18: getfield 6	java/net/AbstractPlainSocketImpl:fdUseCount	I
/*     */     //   21: iconst_m1
/*     */     //   22: if_icmpne +39 -> 61
/*     */     //   25: aload_0
/*     */     //   26: getfield 19	java/net/AbstractPlainSocketImpl:fd	Ljava/io/FileDescriptor;
/*     */     //   29: ifnull +32 -> 61
/*     */     //   32: aload_0
/*     */     //   33: invokevirtual 109	java/net/AbstractPlainSocketImpl:socketClose	()V
/*     */     //   36: aload_0
/*     */     //   37: aconst_null
/*     */     //   38: putfield 19	java/net/AbstractPlainSocketImpl:fd	Ljava/io/FileDescriptor;
/*     */     //   41: goto +20 -> 61
/*     */     //   44: astore_2
/*     */     //   45: aload_0
/*     */     //   46: aconst_null
/*     */     //   47: putfield 19	java/net/AbstractPlainSocketImpl:fd	Ljava/io/FileDescriptor;
/*     */     //   50: goto +11 -> 61
/*     */     //   53: astore_3
/*     */     //   54: aload_0
/*     */     //   55: aconst_null
/*     */     //   56: putfield 19	java/net/AbstractPlainSocketImpl:fd	Ljava/io/FileDescriptor;
/*     */     //   59: aload_3
/*     */     //   60: athrow
/*     */     //   61: aload_1
/*     */     //   62: monitorexit
/*     */     //   63: goto +10 -> 73
/*     */     //   66: astore 4
/*     */     //   68: aload_1
/*     */     //   69: monitorexit
/*     */     //   70: aload 4
/*     */     //   72: athrow
/*     */     //   73: return
/*     */     // Line number table:
/*     */     //   Java source line #617	-> byte code offset #0
/*     */     //   Java source line #618	-> byte code offset #7
/*     */     //   Java source line #619	-> byte code offset #17
/*     */     //   Java source line #620	-> byte code offset #25
/*     */     //   Java source line #622	-> byte code offset #32
/*     */     //   Java source line #625	-> byte code offset #36
/*     */     //   Java source line #626	-> byte code offset #41
/*     */     //   Java source line #623	-> byte code offset #44
/*     */     //   Java source line #625	-> byte code offset #45
/*     */     //   Java source line #626	-> byte code offset #50
/*     */     //   Java source line #625	-> byte code offset #53
/*     */     //   Java source line #629	-> byte code offset #61
/*     */     //   Java source line #630	-> byte code offset #73
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	74	0	this	AbstractPlainSocketImpl
/*     */     //   5	64	1	Ljava/lang/Object;	Object
/*     */     //   44	1	2	localIOException	IOException
/*     */     //   53	7	3	localObject1	Object
/*     */     //   66	5	4	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   32	36	44	java/io/IOException
/*     */     //   32	36	53	finally
/*     */     //   7	63	66	finally
/*     */     //   66	70	66	finally
/*     */   }
/*     */   
/*     */   abstract void socketCreate(boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketConnect(InetAddress paramInetAddress, int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketBind(InetAddress paramInetAddress, int paramInt)
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketListen(int paramInt)
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketAccept(SocketImpl paramSocketImpl)
/*     */     throws IOException;
/*     */   
/*     */   abstract int socketAvailable()
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketClose0(boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketShutdown(int paramInt)
/*     */     throws IOException;
/*     */   
/*     */   abstract void socketSetOption(int paramInt, boolean paramBoolean, Object paramObject)
/*     */     throws SocketException;
/*     */   
/*     */   abstract int socketGetOption(int paramInt, Object paramObject)
/*     */     throws SocketException;
/*     */   
/*     */   abstract void socketSendUrgentData(int paramInt)
/*     */     throws IOException;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/AbstractPlainSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */