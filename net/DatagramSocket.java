/*      */ package java.net;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DatagramSocket
/*      */   implements Closeable
/*      */ {
/*   71 */   private boolean created = false;
/*   72 */   private boolean bound = false;
/*   73 */   private boolean closed = false;
/*   74 */   private Object closeLock = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   DatagramSocketImpl impl;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   84 */   boolean oldImpl = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   95 */   private boolean explicitFilter = false;
/*      */   
/*      */ 
/*      */   private int bytesLeftToFilter;
/*      */   
/*      */ 
/*      */   static final int ST_NOT_CONNECTED = 0;
/*      */   
/*      */   static final int ST_CONNECTED = 1;
/*      */   
/*      */   static final int ST_CONNECTED_NO_IMPL = 2;
/*      */   
/*  107 */   int connectState = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  112 */   InetAddress connectedAddress = null;
/*  113 */   int connectedPort = -1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void connectInternal(InetAddress paramInetAddress, int paramInt)
/*      */     throws SocketException
/*      */   {
/*  124 */     if ((paramInt < 0) || (paramInt > 65535)) {
/*  125 */       throw new IllegalArgumentException("connect: " + paramInt);
/*      */     }
/*  127 */     if (paramInetAddress == null) {
/*  128 */       throw new IllegalArgumentException("connect: null address");
/*      */     }
/*  130 */     checkAddress(paramInetAddress, "connect");
/*  131 */     if (isClosed())
/*  132 */       return;
/*  133 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  134 */     if (localSecurityManager != null) {
/*  135 */       if (paramInetAddress.isMulticastAddress()) {
/*  136 */         localSecurityManager.checkMulticast(paramInetAddress);
/*      */       } else {
/*  138 */         localSecurityManager.checkConnect(paramInetAddress.getHostAddress(), paramInt);
/*  139 */         localSecurityManager.checkAccept(paramInetAddress.getHostAddress(), paramInt);
/*      */       }
/*      */     }
/*      */     
/*  143 */     if (!isBound()) {
/*  144 */       bind(new InetSocketAddress(0));
/*      */     }
/*      */     
/*  147 */     if ((this.oldImpl) || (((this.impl instanceof AbstractPlainDatagramSocketImpl)) && 
/*  148 */       (((AbstractPlainDatagramSocketImpl)this.impl).nativeConnectDisabled()))) {
/*  149 */       this.connectState = 2;
/*      */     } else {
/*      */       try {
/*  152 */         getImpl().connect(paramInetAddress, paramInt);
/*      */         
/*      */ 
/*  155 */         this.connectState = 1;
/*      */         
/*  157 */         int i = getImpl().dataAvailable();
/*  158 */         if (i == -1) {
/*  159 */           throw new SocketException();
/*      */         }
/*  161 */         this.explicitFilter = (i > 0);
/*  162 */         if (this.explicitFilter) {
/*  163 */           this.bytesLeftToFilter = getReceiveBufferSize();
/*      */         }
/*      */       }
/*      */       catch (SocketException localSocketException)
/*      */       {
/*  168 */         this.connectState = 2;
/*      */       }
/*      */     }
/*      */     
/*  172 */     this.connectedAddress = paramInetAddress;
/*  173 */     this.connectedPort = paramInt;
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
/*      */   public DatagramSocket()
/*      */     throws SocketException
/*      */   {
/*  196 */     this(new InetSocketAddress(0));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected DatagramSocket(DatagramSocketImpl paramDatagramSocketImpl)
/*      */   {
/*  208 */     if (paramDatagramSocketImpl == null)
/*  209 */       throw new NullPointerException();
/*  210 */     this.impl = paramDatagramSocketImpl;
/*  211 */     checkOldImpl();
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public DatagramSocket(SocketAddress paramSocketAddress)
/*      */     throws SocketException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial 41	java/lang/Object:<init>	()V
/*      */     //   4: aload_0
/*      */     //   5: iconst_0
/*      */     //   6: putfield 42	java/net/DatagramSocket:created	Z
/*      */     //   9: aload_0
/*      */     //   10: iconst_0
/*      */     //   11: putfield 43	java/net/DatagramSocket:bound	Z
/*      */     //   14: aload_0
/*      */     //   15: iconst_0
/*      */     //   16: putfield 44	java/net/DatagramSocket:closed	Z
/*      */     //   19: aload_0
/*      */     //   20: new 45	java/lang/Object
/*      */     //   23: dup
/*      */     //   24: invokespecial 41	java/lang/Object:<init>	()V
/*      */     //   27: putfield 46	java/net/DatagramSocket:closeLock	Ljava/lang/Object;
/*      */     //   30: aload_0
/*      */     //   31: iconst_0
/*      */     //   32: putfield 24	java/net/DatagramSocket:oldImpl	Z
/*      */     //   35: aload_0
/*      */     //   36: iconst_0
/*      */     //   37: putfield 35	java/net/DatagramSocket:explicitFilter	Z
/*      */     //   40: aload_0
/*      */     //   41: iconst_0
/*      */     //   42: putfield 29	java/net/DatagramSocket:connectState	I
/*      */     //   45: aload_0
/*      */     //   46: aconst_null
/*      */     //   47: putfield 38	java/net/DatagramSocket:connectedAddress	Ljava/net/InetAddress;
/*      */     //   50: aload_0
/*      */     //   51: iconst_m1
/*      */     //   52: putfield 39	java/net/DatagramSocket:connectedPort	I
/*      */     //   55: aload_0
/*      */     //   56: invokevirtual 50	java/net/DatagramSocket:createImpl	()V
/*      */     //   59: aload_1
/*      */     //   60: ifnull +36 -> 96
/*      */     //   63: aload_0
/*      */     //   64: aload_1
/*      */     //   65: invokevirtual 23	java/net/DatagramSocket:bind	(Ljava/net/SocketAddress;)V
/*      */     //   68: aload_0
/*      */     //   69: invokevirtual 20	java/net/DatagramSocket:isBound	()Z
/*      */     //   72: ifne +24 -> 96
/*      */     //   75: aload_0
/*      */     //   76: invokevirtual 51	java/net/DatagramSocket:close	()V
/*      */     //   79: goto +17 -> 96
/*      */     //   82: astore_2
/*      */     //   83: aload_0
/*      */     //   84: invokevirtual 20	java/net/DatagramSocket:isBound	()Z
/*      */     //   87: ifne +7 -> 94
/*      */     //   90: aload_0
/*      */     //   91: invokevirtual 51	java/net/DatagramSocket:close	()V
/*      */     //   94: aload_2
/*      */     //   95: athrow
/*      */     //   96: return
/*      */     // Line number table:
/*      */     //   Java source line #237	-> byte code offset #0
/*      */     //   Java source line #71	-> byte code offset #4
/*      */     //   Java source line #72	-> byte code offset #9
/*      */     //   Java source line #73	-> byte code offset #14
/*      */     //   Java source line #74	-> byte code offset #19
/*      */     //   Java source line #84	-> byte code offset #30
/*      */     //   Java source line #95	-> byte code offset #35
/*      */     //   Java source line #107	-> byte code offset #40
/*      */     //   Java source line #112	-> byte code offset #45
/*      */     //   Java source line #113	-> byte code offset #50
/*      */     //   Java source line #239	-> byte code offset #55
/*      */     //   Java source line #240	-> byte code offset #59
/*      */     //   Java source line #242	-> byte code offset #63
/*      */     //   Java source line #244	-> byte code offset #68
/*      */     //   Java source line #245	-> byte code offset #75
/*      */     //   Java source line #244	-> byte code offset #82
/*      */     //   Java source line #245	-> byte code offset #90
/*      */     //   Java source line #248	-> byte code offset #96
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	97	0	this	DatagramSocket
/*      */     //   0	97	1	paramSocketAddress	SocketAddress
/*      */     //   82	13	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   63	68	82	finally
/*      */   }
/*      */   
/*      */   public DatagramSocket(int paramInt)
/*      */     throws SocketException
/*      */   {
/*  271 */     this(paramInt, null);
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
/*      */   public DatagramSocket(int paramInt, InetAddress paramInetAddress)
/*      */     throws SocketException
/*      */   {
/*  299 */     this(new InetSocketAddress(paramInetAddress, paramInt));
/*      */   }
/*      */   
/*      */   private void checkOldImpl() {
/*  303 */     if (this.impl == null) {
/*  304 */       return;
/*      */     }
/*      */     try
/*      */     {
/*  308 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */       {
/*      */         public Void run() throws NoSuchMethodException {
/*  311 */           Class[] arrayOfClass = new Class[1];
/*  312 */           arrayOfClass[0] = DatagramPacket.class;
/*  313 */           DatagramSocket.this.impl.getClass().getDeclaredMethod("peekData", arrayOfClass);
/*  314 */           return null;
/*      */         }
/*      */       });
/*      */     } catch (PrivilegedActionException localPrivilegedActionException) {
/*  318 */       this.oldImpl = true;
/*      */     }
/*      */   }
/*      */   
/*  322 */   static Class<?> implClass = null;
/*      */   static DatagramSocketImplFactory factory;
/*      */   
/*  325 */   void createImpl() throws SocketException { if (this.impl == null) {
/*  326 */       if (factory != null) {
/*  327 */         this.impl = factory.createDatagramSocketImpl();
/*  328 */         checkOldImpl();
/*      */       } else {
/*  330 */         boolean bool = (this instanceof MulticastSocket);
/*  331 */         this.impl = DefaultDatagramSocketImplFactory.createDatagramSocketImpl(bool);
/*      */         
/*  333 */         checkOldImpl();
/*      */       }
/*      */     }
/*      */     
/*  337 */     this.impl.create();
/*  338 */     this.impl.setDatagramSocket(this);
/*  339 */     this.created = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   DatagramSocketImpl getImpl()
/*      */     throws SocketException
/*      */   {
/*  352 */     if (!this.created)
/*  353 */       createImpl();
/*  354 */     return this.impl;
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
/*      */   public synchronized void bind(SocketAddress paramSocketAddress)
/*      */     throws SocketException
/*      */   {
/*  373 */     if (isClosed())
/*  374 */       throw new SocketException("Socket is closed");
/*  375 */     if (isBound())
/*  376 */       throw new SocketException("already bound");
/*  377 */     if (paramSocketAddress == null)
/*  378 */       paramSocketAddress = new InetSocketAddress(0);
/*  379 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/*  380 */       throw new IllegalArgumentException("Unsupported address type!");
/*  381 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  382 */     if (localInetSocketAddress.isUnresolved())
/*  383 */       throw new SocketException("Unresolved address");
/*  384 */     InetAddress localInetAddress = localInetSocketAddress.getAddress();
/*  385 */     int i = localInetSocketAddress.getPort();
/*  386 */     checkAddress(localInetAddress, "bind");
/*  387 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  388 */     if (localSecurityManager != null) {
/*  389 */       localSecurityManager.checkListen(i);
/*      */     }
/*      */     try {
/*  392 */       getImpl().bind(i, localInetAddress);
/*      */     } catch (SocketException localSocketException) {
/*  394 */       getImpl().close();
/*  395 */       throw localSocketException;
/*      */     }
/*  397 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void checkAddress(InetAddress paramInetAddress, String paramString) {
/*  401 */     if (paramInetAddress == null) {
/*  402 */       return;
/*      */     }
/*  404 */     if ((!(paramInetAddress instanceof Inet4Address)) && (!(paramInetAddress instanceof Inet6Address))) {
/*  405 */       throw new IllegalArgumentException(paramString + ": invalid address type");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void connect(InetAddress paramInetAddress, int paramInt)
/*      */   {
/*      */     try
/*      */     {
/*  458 */       connectInternal(paramInetAddress, paramInt);
/*      */     } catch (SocketException localSocketException) {
/*  460 */       throw new Error("connect failed", localSocketException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void connect(SocketAddress paramSocketAddress)
/*      */     throws SocketException
/*      */   {
/*  487 */     if (paramSocketAddress == null)
/*  488 */       throw new IllegalArgumentException("Address can't be null");
/*  489 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/*  490 */       throw new IllegalArgumentException("Unsupported address type");
/*  491 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  492 */     if (localInetSocketAddress.isUnresolved())
/*  493 */       throw new SocketException("Unresolved address");
/*  494 */     connectInternal(localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void disconnect()
/*      */   {
/*  504 */     synchronized (this) {
/*  505 */       if (isClosed())
/*  506 */         return;
/*  507 */       if (this.connectState == 1) {
/*  508 */         this.impl.disconnect();
/*      */       }
/*  510 */       this.connectedAddress = null;
/*  511 */       this.connectedPort = -1;
/*  512 */       this.connectState = 0;
/*  513 */       this.explicitFilter = false;
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
/*      */   public boolean isBound()
/*      */   {
/*  528 */     return this.bound;
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
/*      */   public boolean isConnected()
/*      */   {
/*  542 */     return this.connectState != 0;
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
/*      */   public InetAddress getInetAddress()
/*      */   {
/*  556 */     return this.connectedAddress;
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
/*      */   public int getPort()
/*      */   {
/*  570 */     return this.connectedPort;
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
/*      */   public SocketAddress getRemoteSocketAddress()
/*      */   {
/*  590 */     if (!isConnected())
/*  591 */       return null;
/*  592 */     return new InetSocketAddress(getInetAddress(), getPort());
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
/*      */   public SocketAddress getLocalSocketAddress()
/*      */   {
/*  607 */     if (isClosed())
/*  608 */       return null;
/*  609 */     if (!isBound())
/*  610 */       return null;
/*  611 */     return new InetSocketAddress(getLocalAddress(), getLocalPort());
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
/*      */   public void send(DatagramPacket paramDatagramPacket)
/*      */     throws IOException
/*      */   {
/*  655 */     InetAddress localInetAddress = null;
/*  656 */     synchronized (paramDatagramPacket) {
/*  657 */       if (isClosed())
/*  658 */         throw new SocketException("Socket is closed");
/*  659 */       checkAddress(paramDatagramPacket.getAddress(), "send");
/*  660 */       if (this.connectState == 0)
/*      */       {
/*  662 */         SecurityManager localSecurityManager = System.getSecurityManager();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  668 */         if (localSecurityManager != null) {
/*  669 */           if (paramDatagramPacket.getAddress().isMulticastAddress()) {
/*  670 */             localSecurityManager.checkMulticast(paramDatagramPacket.getAddress());
/*      */           } else {
/*  672 */             localSecurityManager.checkConnect(paramDatagramPacket.getAddress().getHostAddress(), paramDatagramPacket
/*  673 */               .getPort());
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  678 */         localInetAddress = paramDatagramPacket.getAddress();
/*  679 */         if (localInetAddress == null) {
/*  680 */           paramDatagramPacket.setAddress(this.connectedAddress);
/*  681 */           paramDatagramPacket.setPort(this.connectedPort);
/*  682 */         } else if ((!localInetAddress.equals(this.connectedAddress)) || 
/*  683 */           (paramDatagramPacket.getPort() != this.connectedPort)) {
/*  684 */           throw new IllegalArgumentException("connected address and packet address differ");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  690 */       if (!isBound()) {
/*  691 */         bind(new InetSocketAddress(0));
/*      */       }
/*  693 */       getImpl().send(paramDatagramPacket);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void receive(DatagramPacket paramDatagramPacket)
/*      */     throws IOException
/*      */   {
/*  729 */     synchronized (paramDatagramPacket) {
/*  730 */       if (!isBound())
/*  731 */         bind(new InetSocketAddress(0));
/*  732 */       DatagramPacket localDatagramPacket; if (this.connectState == 0)
/*      */       {
/*  734 */         localObject1 = System.getSecurityManager();
/*  735 */         if (localObject1 != null) {
/*      */           for (;;) {
/*  737 */             String str = null;
/*  738 */             int j = 0;
/*      */             Object localObject2;
/*  740 */             if (!this.oldImpl)
/*      */             {
/*  742 */               localObject2 = new DatagramPacket(new byte[1], 1);
/*  743 */               j = getImpl().peekData((DatagramPacket)localObject2);
/*  744 */               str = ((DatagramPacket)localObject2).getAddress().getHostAddress();
/*      */             } else {
/*  746 */               localObject2 = new InetAddress();
/*  747 */               j = getImpl().peek((InetAddress)localObject2);
/*  748 */               str = ((InetAddress)localObject2).getHostAddress();
/*      */             }
/*      */             try {
/*  751 */               ((SecurityManager)localObject1).checkAccept(str, j);
/*      */ 
/*      */ 
/*      */             }
/*      */             catch (SecurityException localSecurityException)
/*      */             {
/*      */ 
/*  758 */               localDatagramPacket = new DatagramPacket(new byte[1], 1);
/*  759 */               getImpl().receive(localDatagramPacket);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  772 */       Object localObject1 = null;
/*  773 */       if ((this.connectState == 2) || (this.explicitFilter))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  779 */         int i = 0;
/*  780 */         while (i == 0) {
/*  781 */           InetAddress localInetAddress = null;
/*  782 */           int k = -1;
/*      */           
/*  784 */           if (!this.oldImpl)
/*      */           {
/*  786 */             localDatagramPacket = new DatagramPacket(new byte[1], 1);
/*  787 */             k = getImpl().peekData(localDatagramPacket);
/*  788 */             localInetAddress = localDatagramPacket.getAddress();
/*      */           }
/*      */           else {
/*  791 */             localInetAddress = new InetAddress();
/*  792 */             k = getImpl().peek(localInetAddress);
/*      */           }
/*  794 */           if ((!this.connectedAddress.equals(localInetAddress)) || (this.connectedPort != k))
/*      */           {
/*      */ 
/*  797 */             localObject1 = new DatagramPacket(new byte['Ѐ'], 1024);
/*      */             
/*  799 */             getImpl().receive((DatagramPacket)localObject1);
/*  800 */             if ((this.explicitFilter) && 
/*  801 */               (checkFiltering((DatagramPacket)localObject1))) {
/*  802 */               i = 1;
/*      */             }
/*      */           }
/*      */           else {
/*  806 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  812 */       getImpl().receive(paramDatagramPacket);
/*  813 */       if ((this.explicitFilter) && (localObject1 == null))
/*      */       {
/*  815 */         checkFiltering(paramDatagramPacket);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean checkFiltering(DatagramPacket paramDatagramPacket) throws SocketException {
/*  821 */     this.bytesLeftToFilter -= paramDatagramPacket.getLength();
/*  822 */     if ((this.bytesLeftToFilter <= 0) || (getImpl().dataAvailable() <= 0)) {
/*  823 */       this.explicitFilter = false;
/*  824 */       return true;
/*      */     }
/*  826 */     return false;
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
/*      */   public InetAddress getLocalAddress()
/*      */   {
/*  848 */     if (isClosed())
/*  849 */       return null;
/*  850 */     InetAddress localInetAddress = null;
/*      */     try {
/*  852 */       localInetAddress = (InetAddress)getImpl().getOption(15);
/*  853 */       if (localInetAddress.isAnyLocalAddress()) {
/*  854 */         localInetAddress = InetAddress.anyLocalAddress();
/*      */       }
/*  856 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*  857 */       if (localSecurityManager != null) {
/*  858 */         localSecurityManager.checkConnect(localInetAddress.getHostAddress(), -1);
/*      */       }
/*      */     } catch (Exception localException) {
/*  861 */       localInetAddress = InetAddress.anyLocalAddress();
/*      */     }
/*  863 */     return localInetAddress;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLocalPort()
/*      */   {
/*  875 */     if (isClosed())
/*  876 */       return -1;
/*      */     try {
/*  878 */       return getImpl().getLocalPort();
/*      */     } catch (Exception localException) {}
/*  880 */     return 0;
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
/*      */   public synchronized void setSoTimeout(int paramInt)
/*      */     throws SocketException
/*      */   {
/*  900 */     if (isClosed())
/*  901 */       throw new SocketException("Socket is closed");
/*  902 */     getImpl().setOption(4102, new Integer(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int getSoTimeout()
/*      */     throws SocketException
/*      */   {
/*  915 */     if (isClosed())
/*  916 */       throw new SocketException("Socket is closed");
/*  917 */     if (getImpl() == null)
/*  918 */       return 0;
/*  919 */     Object localObject = getImpl().getOption(4102);
/*      */     
/*  921 */     if ((localObject instanceof Integer)) {
/*  922 */       return ((Integer)localObject).intValue();
/*      */     }
/*  924 */     return 0;
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
/*      */   public synchronized void setSendBufferSize(int paramInt)
/*      */     throws SocketException
/*      */   {
/*  959 */     if (paramInt <= 0) {
/*  960 */       throw new IllegalArgumentException("negative send size");
/*      */     }
/*  962 */     if (isClosed())
/*  963 */       throw new SocketException("Socket is closed");
/*  964 */     getImpl().setOption(4097, new Integer(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int getSendBufferSize()
/*      */     throws SocketException
/*      */   {
/*  977 */     if (isClosed())
/*  978 */       throw new SocketException("Socket is closed");
/*  979 */     int i = 0;
/*  980 */     Object localObject = getImpl().getOption(4097);
/*  981 */     if ((localObject instanceof Integer)) {
/*  982 */       i = ((Integer)localObject).intValue();
/*      */     }
/*  984 */     return i;
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
/*      */   public synchronized void setReceiveBufferSize(int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1017 */     if (paramInt <= 0) {
/* 1018 */       throw new IllegalArgumentException("invalid receive size");
/*      */     }
/* 1020 */     if (isClosed())
/* 1021 */       throw new SocketException("Socket is closed");
/* 1022 */     getImpl().setOption(4098, new Integer(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int getReceiveBufferSize()
/*      */     throws SocketException
/*      */   {
/* 1035 */     if (isClosed())
/* 1036 */       throw new SocketException("Socket is closed");
/* 1037 */     int i = 0;
/* 1038 */     Object localObject = getImpl().getOption(4098);
/* 1039 */     if ((localObject instanceof Integer)) {
/* 1040 */       i = ((Integer)localObject).intValue();
/*      */     }
/* 1042 */     return i;
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
/*      */   public synchronized void setReuseAddress(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/* 1080 */     if (isClosed()) {
/* 1081 */       throw new SocketException("Socket is closed");
/*      */     }
/* 1083 */     if (this.oldImpl) {
/* 1084 */       getImpl().setOption(4, new Integer(paramBoolean ? -1 : 0));
/*      */     } else {
/* 1086 */       getImpl().setOption(4, Boolean.valueOf(paramBoolean));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean getReuseAddress()
/*      */     throws SocketException
/*      */   {
/* 1099 */     if (isClosed())
/* 1100 */       throw new SocketException("Socket is closed");
/* 1101 */     Object localObject = getImpl().getOption(4);
/* 1102 */     return ((Boolean)localObject).booleanValue();
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
/*      */   public synchronized void setBroadcast(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/* 1123 */     if (isClosed())
/* 1124 */       throw new SocketException("Socket is closed");
/* 1125 */     getImpl().setOption(32, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean getBroadcast()
/*      */     throws SocketException
/*      */   {
/* 1137 */     if (isClosed())
/* 1138 */       throw new SocketException("Socket is closed");
/* 1139 */     return ((Boolean)getImpl().getOption(32)).booleanValue();
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
/*      */   public synchronized void setTrafficClass(int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1180 */     if ((paramInt < 0) || (paramInt > 255)) {
/* 1181 */       throw new IllegalArgumentException("tc is not in range 0 -- 255");
/*      */     }
/* 1183 */     if (isClosed())
/* 1184 */       throw new SocketException("Socket is closed");
/* 1185 */     getImpl().setOption(3, new Integer(paramInt));
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
/*      */   public synchronized int getTrafficClass()
/*      */     throws SocketException
/*      */   {
/* 1205 */     if (isClosed())
/* 1206 */       throw new SocketException("Socket is closed");
/* 1207 */     return ((Integer)getImpl().getOption(3)).intValue();
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
/*      */   public void close()
/*      */   {
/* 1223 */     synchronized (this.closeLock) {
/* 1224 */       if (isClosed())
/* 1225 */         return;
/* 1226 */       this.impl.close();
/* 1227 */       this.closed = true;
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean isClosed()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 46	java/net/DatagramSocket:closeLock	Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 44	java/net/DatagramSocket:closed	Z
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: ireturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1238	-> byte code offset #0
/*      */     //   Java source line #1239	-> byte code offset #7
/*      */     //   Java source line #1240	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	DatagramSocket
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   public DatagramChannel getChannel()
/*      */   {
/* 1258 */     return null;
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
/*      */   public static synchronized void setDatagramSocketImplFactory(DatagramSocketImplFactory paramDatagramSocketImplFactory)
/*      */     throws IOException
/*      */   {
/* 1298 */     if (factory != null) {
/* 1299 */       throw new SocketException("factory already defined");
/*      */     }
/* 1301 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1302 */     if (localSecurityManager != null) {
/* 1303 */       localSecurityManager.checkSetFactory();
/*      */     }
/* 1305 */     factory = paramDatagramSocketImplFactory;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/DatagramSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */