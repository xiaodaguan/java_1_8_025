/*      */ package java.net;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import sun.net.ApplicationProxy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Socket
/*      */   implements Closeable
/*      */ {
/*   58 */   private boolean created = false;
/*   59 */   private boolean bound = false;
/*   60 */   private boolean connected = false;
/*   61 */   private boolean closed = false;
/*   62 */   private Object closeLock = new Object();
/*   63 */   private boolean shutIn = false;
/*   64 */   private boolean shutOut = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   SocketImpl impl;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   74 */   private boolean oldImpl = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Socket()
/*      */   {
/*   84 */     setImpl();
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
/*      */   public Socket(Proxy paramProxy)
/*      */   {
/*  117 */     if (paramProxy == null) {
/*  118 */       throw new IllegalArgumentException("Invalid Proxy");
/*      */     }
/*      */     
/*  121 */     ApplicationProxy localApplicationProxy = paramProxy == Proxy.NO_PROXY ? Proxy.NO_PROXY : ApplicationProxy.create(paramProxy);
/*  122 */     Proxy.Type localType = localApplicationProxy.type();
/*  123 */     if ((localType == Proxy.Type.SOCKS) || (localType == Proxy.Type.HTTP)) {
/*  124 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*  125 */       InetSocketAddress localInetSocketAddress = (InetSocketAddress)localApplicationProxy.address();
/*  126 */       if (localInetSocketAddress.getAddress() != null) {
/*  127 */         checkAddress(localInetSocketAddress.getAddress(), "Socket");
/*      */       }
/*  129 */       if (localSecurityManager != null) {
/*  130 */         if (localInetSocketAddress.isUnresolved())
/*  131 */           localInetSocketAddress = new InetSocketAddress(localInetSocketAddress.getHostName(), localInetSocketAddress.getPort());
/*  132 */         if (localInetSocketAddress.isUnresolved()) {
/*  133 */           localSecurityManager.checkConnect(localInetSocketAddress.getHostName(), localInetSocketAddress.getPort());
/*      */         } else
/*  135 */           localSecurityManager.checkConnect(localInetSocketAddress.getAddress().getHostAddress(), localInetSocketAddress
/*  136 */             .getPort());
/*      */       }
/*  138 */       this.impl = (localType == Proxy.Type.SOCKS ? new SocksSocketImpl(localApplicationProxy) : new HttpConnectSocketImpl(localApplicationProxy));
/*      */       
/*  140 */       this.impl.setSocket(this);
/*      */     }
/*  142 */     else if (localApplicationProxy == Proxy.NO_PROXY) {
/*  143 */       if (factory == null) {
/*  144 */         this.impl = new PlainSocketImpl();
/*  145 */         this.impl.setSocket(this);
/*      */       } else {
/*  147 */         setImpl();
/*      */       }
/*  149 */     } else { throw new IllegalArgumentException("Invalid Proxy");
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
/*      */   protected Socket(SocketImpl paramSocketImpl)
/*      */     throws SocketException
/*      */   {
/*  165 */     this.impl = paramSocketImpl;
/*  166 */     if (paramSocketImpl != null) {
/*  167 */       checkOldImpl();
/*  168 */       this.impl.setSocket(this);
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
/*      */   public Socket(String paramString, int paramInt)
/*      */     throws UnknownHostException, IOException
/*      */   {
/*  211 */     this(paramString != null ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(
/*  212 */       InetAddress.getByName(null), paramInt), (SocketAddress)null, true);
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
/*      */   public Socket(InetAddress paramInetAddress, int paramInt)
/*      */     throws IOException
/*      */   {
/*  244 */     this(paramInetAddress != null ? new InetSocketAddress(paramInetAddress, paramInt) : null, (SocketAddress)null, true);
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
/*      */   public Socket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  286 */     this(paramString != null ? new InetSocketAddress(paramString, paramInt1) : new InetSocketAddress(
/*  287 */       InetAddress.getByName(null), paramInt1), new InetSocketAddress(paramInetAddress, paramInt2), true);
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
/*      */   public Socket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  328 */     this(paramInetAddress1 != null ? new InetSocketAddress(paramInetAddress1, paramInt1) : null, new InetSocketAddress(paramInetAddress2, paramInt2), true);
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
/*      */   @Deprecated
/*      */   public Socket(String paramString, int paramInt, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  375 */     this(paramString != null ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(
/*  376 */       InetAddress.getByName(null), paramInt), (SocketAddress)null, paramBoolean);
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
/*      */   @Deprecated
/*      */   public Socket(InetAddress paramInetAddress, int paramInt, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  418 */     this(paramInetAddress != null ? new InetSocketAddress(paramInetAddress, paramInt) : null, new InetSocketAddress(0), paramBoolean);
/*      */   }
/*      */   
/*      */   private Socket(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  424 */     setImpl();
/*      */     
/*      */ 
/*  427 */     if (paramSocketAddress1 == null) {
/*  428 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  431 */       createImpl(paramBoolean);
/*  432 */       if (paramSocketAddress2 != null)
/*  433 */         bind(paramSocketAddress2);
/*  434 */       connect(paramSocketAddress1);
/*      */     } catch (IOException|IllegalArgumentException|SecurityException localIOException1) {
/*      */       try {
/*  437 */         close();
/*      */       } catch (IOException localIOException2) {
/*  439 */         localIOException1.addSuppressed(localIOException2);
/*      */       }
/*  441 */       throw localIOException1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void createImpl(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/*  454 */     if (this.impl == null)
/*  455 */       setImpl();
/*      */     try {
/*  457 */       this.impl.create(paramBoolean);
/*  458 */       this.created = true;
/*      */     } catch (IOException localIOException) {
/*  460 */       throw new SocketException(localIOException.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkOldImpl() {
/*  465 */     if (this.impl == null) {
/*  466 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  471 */     this.oldImpl = ((Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Boolean run() {
/*  473 */         Class localClass = Socket.this.impl.getClass();
/*      */         for (;;) {
/*      */           try {
/*  476 */             localClass.getDeclaredMethod("connect", new Class[] { SocketAddress.class, Integer.TYPE });
/*  477 */             return Boolean.FALSE;
/*      */           } catch (NoSuchMethodException localNoSuchMethodException) {
/*  479 */             localClass = localClass.getSuperclass();
/*      */             
/*      */ 
/*      */ 
/*  483 */             if (localClass.equals(SocketImpl.class)) {
/*  484 */               return Boolean.TRUE;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }))
/*  471 */       .booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void setImpl()
/*      */   {
/*  497 */     if (factory != null) {
/*  498 */       this.impl = factory.createSocketImpl();
/*  499 */       checkOldImpl();
/*      */     }
/*      */     else
/*      */     {
/*  503 */       this.impl = new SocksSocketImpl();
/*      */     }
/*  505 */     if (this.impl != null) {
/*  506 */       this.impl.setSocket(this);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   SocketImpl getImpl()
/*      */     throws SocketException
/*      */   {
/*  519 */     if (!this.created)
/*  520 */       createImpl(true);
/*  521 */     return this.impl;
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
/*      */   public void connect(SocketAddress paramSocketAddress)
/*      */     throws IOException
/*      */   {
/*  538 */     connect(paramSocketAddress, 0);
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
/*      */   public void connect(SocketAddress paramSocketAddress, int paramInt)
/*      */     throws IOException
/*      */   {
/*  559 */     if (paramSocketAddress == null) {
/*  560 */       throw new IllegalArgumentException("connect: The address can't be null");
/*      */     }
/*  562 */     if (paramInt < 0) {
/*  563 */       throw new IllegalArgumentException("connect: timeout can't be negative");
/*      */     }
/*  565 */     if (isClosed()) {
/*  566 */       throw new SocketException("Socket is closed");
/*      */     }
/*  568 */     if ((!this.oldImpl) && (isConnected())) {
/*  569 */       throw new SocketException("already connected");
/*      */     }
/*  571 */     if (!(paramSocketAddress instanceof InetSocketAddress)) {
/*  572 */       throw new IllegalArgumentException("Unsupported address type");
/*      */     }
/*  574 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  575 */     InetAddress localInetAddress = localInetSocketAddress.getAddress();
/*  576 */     int i = localInetSocketAddress.getPort();
/*  577 */     checkAddress(localInetAddress, "connect");
/*      */     
/*  579 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  580 */     if (localSecurityManager != null) {
/*  581 */       if (localInetSocketAddress.isUnresolved()) {
/*  582 */         localSecurityManager.checkConnect(localInetSocketAddress.getHostName(), i);
/*      */       } else
/*  584 */         localSecurityManager.checkConnect(localInetAddress.getHostAddress(), i);
/*      */     }
/*  586 */     if (!this.created)
/*  587 */       createImpl(true);
/*  588 */     if (!this.oldImpl) {
/*  589 */       this.impl.connect(localInetSocketAddress, paramInt);
/*  590 */     } else if (paramInt == 0) {
/*  591 */       if (localInetSocketAddress.isUnresolved()) {
/*  592 */         this.impl.connect(localInetAddress.getHostName(), i);
/*      */       } else
/*  594 */         this.impl.connect(localInetAddress, i);
/*      */     } else
/*  596 */       throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)");
/*  597 */     this.connected = true;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  602 */     this.bound = true;
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
/*      */   public void bind(SocketAddress paramSocketAddress)
/*      */     throws IOException
/*      */   {
/*  624 */     if (isClosed())
/*  625 */       throw new SocketException("Socket is closed");
/*  626 */     if ((!this.oldImpl) && (isBound())) {
/*  627 */       throw new SocketException("Already bound");
/*      */     }
/*  629 */     if ((paramSocketAddress != null) && (!(paramSocketAddress instanceof InetSocketAddress)))
/*  630 */       throw new IllegalArgumentException("Unsupported address type");
/*  631 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  632 */     if ((localInetSocketAddress != null) && (localInetSocketAddress.isUnresolved()))
/*  633 */       throw new SocketException("Unresolved address");
/*  634 */     if (localInetSocketAddress == null) {
/*  635 */       localInetSocketAddress = new InetSocketAddress(0);
/*      */     }
/*  637 */     InetAddress localInetAddress = localInetSocketAddress.getAddress();
/*  638 */     int i = localInetSocketAddress.getPort();
/*  639 */     checkAddress(localInetAddress, "bind");
/*  640 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  641 */     if (localSecurityManager != null) {
/*  642 */       localSecurityManager.checkListen(i);
/*      */     }
/*  644 */     getImpl().bind(localInetAddress, i);
/*  645 */     this.bound = true;
/*      */   }
/*      */   
/*      */   private void checkAddress(InetAddress paramInetAddress, String paramString) {
/*  649 */     if (paramInetAddress == null) {
/*  650 */       return;
/*      */     }
/*  652 */     if ((!(paramInetAddress instanceof Inet4Address)) && (!(paramInetAddress instanceof Inet6Address))) {
/*  653 */       throw new IllegalArgumentException(paramString + ": invalid address type");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void postAccept()
/*      */   {
/*  661 */     this.connected = true;
/*  662 */     this.created = true;
/*  663 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void setCreated() {
/*  667 */     this.created = true;
/*      */   }
/*      */   
/*      */   void setBound() {
/*  671 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void setConnected() {
/*  675 */     this.connected = true;
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
/*  689 */     if (!isConnected())
/*  690 */       return null;
/*      */     try {
/*  692 */       return getImpl().getInetAddress();
/*      */     }
/*      */     catch (SocketException localSocketException) {}
/*  695 */     return null;
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
/*      */   public InetAddress getLocalAddress()
/*      */   {
/*  715 */     if (!isBound())
/*  716 */       return InetAddress.anyLocalAddress();
/*  717 */     InetAddress localInetAddress = null;
/*      */     try {
/*  719 */       localInetAddress = (InetAddress)getImpl().getOption(15);
/*  720 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*  721 */       if (localSecurityManager != null)
/*  722 */         localSecurityManager.checkConnect(localInetAddress.getHostAddress(), -1);
/*  723 */       if (localInetAddress.isAnyLocalAddress()) {
/*  724 */         localInetAddress = InetAddress.anyLocalAddress();
/*      */       }
/*      */     } catch (SecurityException localSecurityException) {
/*  727 */       localInetAddress = InetAddress.getLoopbackAddress();
/*      */     } catch (Exception localException) {
/*  729 */       localInetAddress = InetAddress.anyLocalAddress();
/*      */     }
/*  731 */     return localInetAddress;
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
/*  745 */     if (!isConnected())
/*  746 */       return 0;
/*      */     try {
/*  748 */       return getImpl().getPort();
/*      */     }
/*      */     catch (SocketException localSocketException) {}
/*      */     
/*  752 */     return -1;
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
/*      */   public int getLocalPort()
/*      */   {
/*  766 */     if (!isBound())
/*  767 */       return -1;
/*      */     try {
/*  769 */       return getImpl().getLocalPort();
/*      */     }
/*      */     catch (SocketException localSocketException) {}
/*      */     
/*  773 */     return -1;
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
/*      */   public SocketAddress getRemoteSocketAddress()
/*      */   {
/*  794 */     if (!isConnected())
/*  795 */       return null;
/*  796 */     return new InetSocketAddress(getInetAddress(), getPort());
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
/*      */   public SocketAddress getLocalSocketAddress()
/*      */   {
/*  830 */     if (!isBound())
/*  831 */       return null;
/*  832 */     return new InetSocketAddress(getLocalAddress(), getLocalPort());
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
/*      */   public SocketChannel getChannel()
/*      */   {
/*  853 */     return null;
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
/*      */   public InputStream getInputStream()
/*      */     throws IOException
/*      */   {
/*  902 */     if (isClosed())
/*  903 */       throw new SocketException("Socket is closed");
/*  904 */     if (!isConnected())
/*  905 */       throw new SocketException("Socket is not connected");
/*  906 */     if (isInputShutdown())
/*  907 */       throw new SocketException("Socket input is shutdown");
/*  908 */     Socket localSocket = this;
/*  909 */     InputStream localInputStream = null;
/*      */     try {
/*  911 */       localInputStream = (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */       {
/*      */         public InputStream run() throws IOException {
/*  914 */           return Socket.this.impl.getInputStream();
/*      */         }
/*      */       });
/*      */     } catch (PrivilegedActionException localPrivilegedActionException) {
/*  918 */       throw ((IOException)localPrivilegedActionException.getException());
/*      */     }
/*  920 */     return localInputStream;
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
/*      */   public OutputStream getOutputStream()
/*      */     throws IOException
/*      */   {
/*  942 */     if (isClosed())
/*  943 */       throw new SocketException("Socket is closed");
/*  944 */     if (!isConnected())
/*  945 */       throw new SocketException("Socket is not connected");
/*  946 */     if (isOutputShutdown())
/*  947 */       throw new SocketException("Socket output is shutdown");
/*  948 */     Socket localSocket = this;
/*  949 */     OutputStream localOutputStream = null;
/*      */     try {
/*  951 */       localOutputStream = (OutputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */       {
/*      */         public OutputStream run() throws IOException {
/*  954 */           return Socket.this.impl.getOutputStream();
/*      */         }
/*      */       });
/*      */     } catch (PrivilegedActionException localPrivilegedActionException) {
/*  958 */       throw ((IOException)localPrivilegedActionException.getException());
/*      */     }
/*  960 */     return localOutputStream;
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
/*      */   public void setTcpNoDelay(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/*  978 */     if (isClosed())
/*  979 */       throw new SocketException("Socket is closed");
/*  980 */     getImpl().setOption(1, Boolean.valueOf(paramBoolean));
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
/*      */   public boolean getTcpNoDelay()
/*      */     throws SocketException
/*      */   {
/*  994 */     if (isClosed())
/*  995 */       throw new SocketException("Socket is closed");
/*  996 */     return ((Boolean)getImpl().getOption(1)).booleanValue();
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
/*      */   public void setSoLinger(boolean paramBoolean, int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1015 */     if (isClosed())
/* 1016 */       throw new SocketException("Socket is closed");
/* 1017 */     if (!paramBoolean) {
/* 1018 */       getImpl().setOption(128, new Boolean(paramBoolean));
/*      */     } else {
/* 1020 */       if (paramInt < 0) {
/* 1021 */         throw new IllegalArgumentException("invalid value for SO_LINGER");
/*      */       }
/* 1023 */       if (paramInt > 65535)
/* 1024 */         paramInt = 65535;
/* 1025 */       getImpl().setOption(128, new Integer(paramInt));
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
/*      */   public int getSoLinger()
/*      */     throws SocketException
/*      */   {
/* 1043 */     if (isClosed())
/* 1044 */       throw new SocketException("Socket is closed");
/* 1045 */     Object localObject = getImpl().getOption(128);
/* 1046 */     if ((localObject instanceof Integer)) {
/* 1047 */       return ((Integer)localObject).intValue();
/*      */     }
/* 1049 */     return -1;
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
/*      */   public void sendUrgentData(int paramInt)
/*      */     throws IOException
/*      */   {
/* 1064 */     if (!getImpl().supportsUrgentData()) {
/* 1065 */       throw new SocketException("Urgent data not supported");
/*      */     }
/* 1067 */     getImpl().sendUrgentData(paramInt);
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
/*      */   public void setOOBInline(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/* 1096 */     if (isClosed())
/* 1097 */       throw new SocketException("Socket is closed");
/* 1098 */     getImpl().setOption(4099, Boolean.valueOf(paramBoolean));
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
/*      */   public boolean getOOBInline()
/*      */     throws SocketException
/*      */   {
/* 1113 */     if (isClosed())
/* 1114 */       throw new SocketException("Socket is closed");
/* 1115 */     return ((Boolean)getImpl().getOption(4099)).booleanValue();
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
/*      */   public synchronized void setSoTimeout(int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1136 */     if (isClosed())
/* 1137 */       throw new SocketException("Socket is closed");
/* 1138 */     if (paramInt < 0) {
/* 1139 */       throw new IllegalArgumentException("timeout can't be negative");
/*      */     }
/* 1141 */     getImpl().setOption(4102, new Integer(paramInt));
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
/*      */   public synchronized int getSoTimeout()
/*      */     throws SocketException
/*      */   {
/* 1156 */     if (isClosed())
/* 1157 */       throw new SocketException("Socket is closed");
/* 1158 */     Object localObject = getImpl().getOption(4102);
/*      */     
/* 1160 */     if ((localObject instanceof Integer)) {
/* 1161 */       return ((Integer)localObject).intValue();
/*      */     }
/* 1163 */     return 0;
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
/*      */   public synchronized void setSendBufferSize(int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1192 */     if (paramInt <= 0) {
/* 1193 */       throw new IllegalArgumentException("negative send size");
/*      */     }
/* 1195 */     if (isClosed())
/* 1196 */       throw new SocketException("Socket is closed");
/* 1197 */     getImpl().setOption(4097, new Integer(paramInt));
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
/*      */   public synchronized int getSendBufferSize()
/*      */     throws SocketException
/*      */   {
/* 1214 */     if (isClosed())
/* 1215 */       throw new SocketException("Socket is closed");
/* 1216 */     int i = 0;
/* 1217 */     Object localObject = getImpl().getOption(4097);
/* 1218 */     if ((localObject instanceof Integer)) {
/* 1219 */       i = ((Integer)localObject).intValue();
/*      */     }
/* 1221 */     return i;
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
/*      */   public synchronized void setReceiveBufferSize(int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1266 */     if (paramInt <= 0) {
/* 1267 */       throw new IllegalArgumentException("invalid receive size");
/*      */     }
/* 1269 */     if (isClosed())
/* 1270 */       throw new SocketException("Socket is closed");
/* 1271 */     getImpl().setOption(4098, new Integer(paramInt));
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
/*      */   public synchronized int getReceiveBufferSize()
/*      */     throws SocketException
/*      */   {
/* 1288 */     if (isClosed())
/* 1289 */       throw new SocketException("Socket is closed");
/* 1290 */     int i = 0;
/* 1291 */     Object localObject = getImpl().getOption(4098);
/* 1292 */     if ((localObject instanceof Integer)) {
/* 1293 */       i = ((Integer)localObject).intValue();
/*      */     }
/* 1295 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setKeepAlive(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/* 1308 */     if (isClosed())
/* 1309 */       throw new SocketException("Socket is closed");
/* 1310 */     getImpl().setOption(8, Boolean.valueOf(paramBoolean));
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
/*      */   public boolean getKeepAlive()
/*      */     throws SocketException
/*      */   {
/* 1324 */     if (isClosed())
/* 1325 */       throw new SocketException("Socket is closed");
/* 1326 */     return ((Boolean)getImpl().getOption(8)).booleanValue();
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
/*      */   public void setTrafficClass(int paramInt)
/*      */     throws SocketException
/*      */   {
/* 1376 */     if ((paramInt < 0) || (paramInt > 255)) {
/* 1377 */       throw new IllegalArgumentException("tc is not in range 0 -- 255");
/*      */     }
/* 1379 */     if (isClosed())
/* 1380 */       throw new SocketException("Socket is closed");
/* 1381 */     getImpl().setOption(3, new Integer(paramInt));
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
/*      */   public int getTrafficClass()
/*      */     throws SocketException
/*      */   {
/* 1401 */     return ((Integer)getImpl().getOption(3)).intValue();
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
/*      */   public void setReuseAddress(boolean paramBoolean)
/*      */     throws SocketException
/*      */   {
/* 1440 */     if (isClosed())
/* 1441 */       throw new SocketException("Socket is closed");
/* 1442 */     getImpl().setOption(4, Boolean.valueOf(paramBoolean));
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
/*      */   public boolean getReuseAddress()
/*      */     throws SocketException
/*      */   {
/* 1456 */     if (isClosed())
/* 1457 */       throw new SocketException("Socket is closed");
/* 1458 */     return ((Boolean)getImpl().getOption(4)).booleanValue();
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
/*      */   public synchronized void close()
/*      */     throws IOException
/*      */   {
/* 1484 */     synchronized (this.closeLock) {
/* 1485 */       if (isClosed())
/* 1486 */         return;
/* 1487 */       if (this.created)
/* 1488 */         this.impl.close();
/* 1489 */       this.closed = true;
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
/*      */   public void shutdownInput()
/*      */     throws IOException
/*      */   {
/* 1513 */     if (isClosed())
/* 1514 */       throw new SocketException("Socket is closed");
/* 1515 */     if (!isConnected())
/* 1516 */       throw new SocketException("Socket is not connected");
/* 1517 */     if (isInputShutdown())
/* 1518 */       throw new SocketException("Socket input is already shutdown");
/* 1519 */     getImpl().shutdownInput();
/* 1520 */     this.shutIn = true;
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
/*      */   public void shutdownOutput()
/*      */     throws IOException
/*      */   {
/* 1543 */     if (isClosed())
/* 1544 */       throw new SocketException("Socket is closed");
/* 1545 */     if (!isConnected())
/* 1546 */       throw new SocketException("Socket is not connected");
/* 1547 */     if (isOutputShutdown())
/* 1548 */       throw new SocketException("Socket output is already shutdown");
/* 1549 */     getImpl().shutdownOutput();
/* 1550 */     this.shutOut = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*      */     try
/*      */     {
/* 1560 */       if (isConnected())
/*      */       {
/*      */ 
/* 1563 */         return "Socket[addr=" + getImpl().getInetAddress() + ",port=" + getImpl().getPort() + ",localport=" + getImpl().getLocalPort() + "]";
/*      */       }
/*      */     } catch (SocketException localSocketException) {}
/* 1566 */     return "Socket[unconnected]";
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
/*      */   public boolean isConnected()
/*      */   {
/* 1582 */     return (this.connected) || (this.oldImpl);
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
/*      */   public boolean isBound()
/*      */   {
/* 1599 */     return (this.bound) || (this.oldImpl);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean isClosed()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 7	java/net/Socket:closeLock	Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 5	java/net/Socket:closed	Z
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: ireturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1610	-> byte code offset #0
/*      */     //   Java source line #1611	-> byte code offset #7
/*      */     //   Java source line #1612	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Socket
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   public boolean isInputShutdown()
/*      */   {
/* 1623 */     return this.shutIn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isOutputShutdown()
/*      */   {
/* 1634 */     return this.shutOut;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1640 */   private static SocketImplFactory factory = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static synchronized void setSocketImplFactory(SocketImplFactory paramSocketImplFactory)
/*      */     throws IOException
/*      */   {
/* 1669 */     if (factory != null) {
/* 1670 */       throw new SocketException("factory already defined");
/*      */     }
/* 1672 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1673 */     if (localSecurityManager != null) {
/* 1674 */       localSecurityManager.checkSetFactory();
/*      */     }
/* 1676 */     factory = paramSocketImplFactory;
/*      */   }
/*      */   
/*      */   public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/Socket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */