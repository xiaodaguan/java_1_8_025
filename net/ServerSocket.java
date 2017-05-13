/*     */ package java.net;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSocket
/*     */   implements Closeable
/*     */ {
/*  56 */   private boolean created = false;
/*  57 */   private boolean bound = false;
/*  58 */   private boolean closed = false;
/*  59 */   private Object closeLock = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private SocketImpl impl;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  69 */   private boolean oldImpl = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   ServerSocket(SocketImpl paramSocketImpl)
/*     */   {
/*  76 */     this.impl = paramSocketImpl;
/*  77 */     paramSocketImpl.setServerSocket(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerSocket()
/*     */     throws IOException
/*     */   {
/*  87 */     setImpl();
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
/*     */   public ServerSocket(int paramInt)
/*     */     throws IOException
/*     */   {
/* 128 */     this(paramInt, 50, null);
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
/*     */   public ServerSocket(int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 181 */     this(paramInt1, paramInt2, null);
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
/*     */   public ServerSocket(int paramInt1, int paramInt2, InetAddress paramInetAddress)
/*     */     throws IOException
/*     */   {
/* 230 */     setImpl();
/* 231 */     if ((paramInt1 < 0) || (paramInt1 > 65535)) {
/* 232 */       throw new IllegalArgumentException("Port value out of range: " + paramInt1);
/*     */     }
/* 234 */     if (paramInt2 < 1)
/* 235 */       paramInt2 = 50;
/*     */     try {
/* 237 */       bind(new InetSocketAddress(paramInetAddress, paramInt1), paramInt2);
/*     */     } catch (SecurityException localSecurityException) {
/* 239 */       close();
/* 240 */       throw localSecurityException;
/*     */     } catch (IOException localIOException) {
/* 242 */       close();
/* 243 */       throw localIOException;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   SocketImpl getImpl()
/*     */     throws SocketException
/*     */   {
/* 256 */     if (!this.created)
/* 257 */       createImpl();
/* 258 */     return this.impl;
/*     */   }
/*     */   
/*     */   private void checkOldImpl() {
/* 262 */     if (this.impl == null) {
/* 263 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 267 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Void run() throws NoSuchMethodException {
/* 270 */           ServerSocket.this.impl.getClass().getDeclaredMethod("connect", new Class[] { SocketAddress.class, Integer.TYPE });
/*     */           
/*     */ 
/* 273 */           return null;
/*     */         }
/*     */       });
/*     */     } catch (PrivilegedActionException localPrivilegedActionException) {
/* 277 */       this.oldImpl = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void setImpl() {
/* 282 */     if (factory != null) {
/* 283 */       this.impl = factory.createSocketImpl();
/* 284 */       checkOldImpl();
/*     */     }
/*     */     else
/*     */     {
/* 288 */       this.impl = new SocksSocketImpl();
/*     */     }
/* 290 */     if (this.impl != null) {
/* 291 */       this.impl.setServerSocket(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void createImpl()
/*     */     throws SocketException
/*     */   {
/* 301 */     if (this.impl == null)
/* 302 */       setImpl();
/*     */     try {
/* 304 */       this.impl.create(true);
/* 305 */       this.created = true;
/*     */     } catch (IOException localIOException) {
/* 307 */       throw new SocketException(localIOException.getMessage());
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
/*     */   public void bind(SocketAddress paramSocketAddress)
/*     */     throws IOException
/*     */   {
/* 329 */     bind(paramSocketAddress, 50);
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
/*     */   public void bind(SocketAddress paramSocketAddress, int paramInt)
/*     */     throws IOException
/*     */   {
/* 358 */     if (isClosed())
/* 359 */       throw new SocketException("Socket is closed");
/* 360 */     if ((!this.oldImpl) && (isBound()))
/* 361 */       throw new SocketException("Already bound");
/* 362 */     if (paramSocketAddress == null)
/* 363 */       paramSocketAddress = new InetSocketAddress(0);
/* 364 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/* 365 */       throw new IllegalArgumentException("Unsupported address type");
/* 366 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 367 */     if (localInetSocketAddress.isUnresolved())
/* 368 */       throw new SocketException("Unresolved address");
/* 369 */     if (paramInt < 1)
/* 370 */       paramInt = 50;
/*     */     try {
/* 372 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 373 */       if (localSecurityManager != null)
/* 374 */         localSecurityManager.checkListen(localInetSocketAddress.getPort());
/* 375 */       getImpl().bind(localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/* 376 */       getImpl().listen(paramInt);
/* 377 */       this.bound = true;
/*     */     } catch (SecurityException localSecurityException) {
/* 379 */       this.bound = false;
/* 380 */       throw localSecurityException;
/*     */     } catch (IOException localIOException) {
/* 382 */       this.bound = false;
/* 383 */       throw localIOException;
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
/*     */   public InetAddress getInetAddress()
/*     */   {
/* 406 */     if (!isBound())
/* 407 */       return null;
/*     */     try {
/* 409 */       InetAddress localInetAddress = getImpl().getInetAddress();
/* 410 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 411 */       if (localSecurityManager != null)
/* 412 */         localSecurityManager.checkConnect(localInetAddress.getHostAddress(), -1);
/* 413 */       return localInetAddress;
/*     */     } catch (SecurityException localSecurityException) {
/* 415 */       return InetAddress.getLoopbackAddress();
/*     */     }
/*     */     catch (SocketException localSocketException) {}
/*     */     
/*     */ 
/*     */ 
/* 421 */     return null;
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
/*     */   public int getLocalPort()
/*     */   {
/* 435 */     if (!isBound())
/* 436 */       return -1;
/*     */     try {
/* 438 */       return getImpl().getLocalPort();
/*     */     }
/*     */     catch (SocketException localSocketException) {}
/*     */     
/*     */ 
/*     */ 
/* 444 */     return -1;
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
/*     */   public SocketAddress getLocalSocketAddress()
/*     */   {
/* 474 */     if (!isBound())
/* 475 */       return null;
/* 476 */     return new InetSocketAddress(getInetAddress(), getLocalPort());
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
/*     */   public Socket accept()
/*     */     throws IOException
/*     */   {
/* 508 */     if (isClosed())
/* 509 */       throw new SocketException("Socket is closed");
/* 510 */     if (!isBound())
/* 511 */       throw new SocketException("Socket is not bound yet");
/* 512 */     Socket localSocket = new Socket((SocketImpl)null);
/* 513 */     implAccept(localSocket);
/* 514 */     return localSocket;
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
/*     */   protected final void implAccept(Socket paramSocket)
/*     */     throws IOException
/*     */   {
/* 534 */     SocketImpl localSocketImpl = null;
/*     */     try {
/* 536 */       if (paramSocket.impl == null) {
/* 537 */         paramSocket.setImpl();
/*     */       } else {
/* 539 */         paramSocket.impl.reset();
/*     */       }
/* 541 */       localSocketImpl = paramSocket.impl;
/* 542 */       paramSocket.impl = null;
/* 543 */       localSocketImpl.address = new InetAddress();
/* 544 */       localSocketImpl.fd = new FileDescriptor();
/* 545 */       getImpl().accept(localSocketImpl);
/*     */       
/* 547 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 548 */       if (localSecurityManager != null) {
/* 549 */         localSecurityManager.checkAccept(localSocketImpl.getInetAddress().getHostAddress(), localSocketImpl
/* 550 */           .getPort());
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 553 */       if (localSocketImpl != null)
/* 554 */         localSocketImpl.reset();
/* 555 */       paramSocket.impl = localSocketImpl;
/* 556 */       throw localIOException;
/*     */     } catch (SecurityException localSecurityException) {
/* 558 */       if (localSocketImpl != null)
/* 559 */         localSocketImpl.reset();
/* 560 */       paramSocket.impl = localSocketImpl;
/* 561 */       throw localSecurityException;
/*     */     }
/* 563 */     paramSocket.impl = localSocketImpl;
/* 564 */     paramSocket.postAccept();
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 581 */     synchronized (this.closeLock) {
/* 582 */       if (isClosed())
/* 583 */         return;
/* 584 */       if (this.created)
/* 585 */         this.impl.close();
/* 586 */       this.closed = true;
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
/*     */   public ServerSocketChannel getChannel()
/*     */   {
/* 607 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 618 */     return (this.bound) || (this.oldImpl);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean isClosed()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 7	java/net/ServerSocket:closeLock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 5	java/net/ServerSocket:closed	Z
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: ireturn
/*     */     //   14: astore_2
/*     */     //   15: aload_1
/*     */     //   16: monitorexit
/*     */     //   17: aload_2
/*     */     //   18: athrow
/*     */     // Line number table:
/*     */     //   Java source line #628	-> byte code offset #0
/*     */     //   Java source line #629	-> byte code offset #7
/*     */     //   Java source line #630	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	19	0	this	ServerSocket
/*     */     //   5	11	1	Ljava/lang/Object;	Object
/*     */     //   14	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	17	14	finally
/*     */   }
/*     */   
/*     */   public synchronized void setSoTimeout(int paramInt)
/*     */     throws SocketException
/*     */   {
/* 650 */     if (isClosed())
/* 651 */       throw new SocketException("Socket is closed");
/* 652 */     getImpl().setOption(4102, new Integer(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getSoTimeout()
/*     */     throws IOException
/*     */   {
/* 664 */     if (isClosed())
/* 665 */       throw new SocketException("Socket is closed");
/* 666 */     Object localObject = getImpl().getOption(4102);
/*     */     
/* 668 */     if ((localObject instanceof Integer)) {
/* 669 */       return ((Integer)localObject).intValue();
/*     */     }
/* 671 */     return 0;
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
/*     */   public void setReuseAddress(boolean paramBoolean)
/*     */     throws SocketException
/*     */   {
/* 712 */     if (isClosed())
/* 713 */       throw new SocketException("Socket is closed");
/* 714 */     getImpl().setOption(4, Boolean.valueOf(paramBoolean));
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
/*     */   public boolean getReuseAddress()
/*     */     throws SocketException
/*     */   {
/* 728 */     if (isClosed())
/* 729 */       throw new SocketException("Socket is closed");
/* 730 */     return ((Boolean)getImpl().getOption(4)).booleanValue();
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
/*     */   public String toString()
/*     */   {
/* 747 */     if (!isBound())
/* 748 */       return "ServerSocket[unbound]";
/*     */     InetAddress localInetAddress;
/* 750 */     if (System.getSecurityManager() != null) {
/* 751 */       localInetAddress = InetAddress.getLoopbackAddress();
/*     */     } else {
/* 753 */       localInetAddress = this.impl.getInetAddress();
/*     */     }
/* 755 */     return "ServerSocket[addr=" + localInetAddress + ",localport=" + this.impl.getLocalPort() + "]";
/*     */   }
/*     */   
/*     */   void setBound() {
/* 759 */     this.bound = true;
/*     */   }
/*     */   
/*     */   void setCreated() {
/* 763 */     this.created = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 769 */   private static SocketImplFactory factory = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized void setSocketFactory(SocketImplFactory paramSocketImplFactory)
/*     */     throws IOException
/*     */   {
/* 797 */     if (factory != null) {
/* 798 */       throw new SocketException("factory already defined");
/*     */     }
/* 800 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 801 */     if (localSecurityManager != null) {
/* 802 */       localSecurityManager.checkSetFactory();
/*     */     }
/* 804 */     factory = paramSocketImplFactory;
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
/*     */   public synchronized void setReceiveBufferSize(int paramInt)
/*     */     throws SocketException
/*     */   {
/* 844 */     if (paramInt <= 0) {
/* 845 */       throw new IllegalArgumentException("negative receive size");
/*     */     }
/* 847 */     if (isClosed())
/* 848 */       throw new SocketException("Socket is closed");
/* 849 */     getImpl().setOption(4098, new Integer(paramInt));
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
/*     */   public synchronized int getReceiveBufferSize()
/*     */     throws SocketException
/*     */   {
/* 868 */     if (isClosed())
/* 869 */       throw new SocketException("Socket is closed");
/* 870 */     int i = 0;
/* 871 */     Object localObject = getImpl().getOption(4098);
/* 872 */     if ((localObject instanceof Integer)) {
/* 873 */       i = ((Integer)localObject).intValue();
/*     */     }
/* 875 */     return i;
/*     */   }
/*     */   
/*     */   public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/ServerSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */