/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MulticastSocket
/*     */   extends DatagramSocket
/*     */ {
/*     */   private boolean interfaceSet;
/*     */   private Object ttlLock;
/*     */   private Object infLock;
/*     */   private InetAddress infAddress;
/*     */   
/*     */   public MulticastSocket()
/*     */     throws IOException
/*     */   {
/* 111 */     this(new InetSocketAddress(0));
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
/*     */   public MulticastSocket(int paramInt)
/*     */     throws IOException
/*     */   {
/* 136 */     this(new InetSocketAddress(paramInt));
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public MulticastSocket(SocketAddress paramSocketAddress)
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aconst_null
/*     */     //   2: checkcast 4	java/net/SocketAddress
/*     */     //   5: invokespecial 5	java/net/DatagramSocket:<init>	(Ljava/net/SocketAddress;)V
/*     */     //   8: aload_0
/*     */     //   9: new 6	java/lang/Object
/*     */     //   12: dup
/*     */     //   13: invokespecial 7	java/lang/Object:<init>	()V
/*     */     //   16: putfield 8	java/net/MulticastSocket:ttlLock	Ljava/lang/Object;
/*     */     //   19: aload_0
/*     */     //   20: new 6	java/lang/Object
/*     */     //   23: dup
/*     */     //   24: invokespecial 7	java/lang/Object:<init>	()V
/*     */     //   27: putfield 9	java/net/MulticastSocket:infLock	Ljava/lang/Object;
/*     */     //   30: aload_0
/*     */     //   31: aconst_null
/*     */     //   32: putfield 10	java/net/MulticastSocket:infAddress	Ljava/net/InetAddress;
/*     */     //   35: aload_0
/*     */     //   36: iconst_1
/*     */     //   37: invokevirtual 11	java/net/MulticastSocket:setReuseAddress	(Z)V
/*     */     //   40: aload_1
/*     */     //   41: ifnull +36 -> 77
/*     */     //   44: aload_0
/*     */     //   45: aload_1
/*     */     //   46: invokevirtual 12	java/net/MulticastSocket:bind	(Ljava/net/SocketAddress;)V
/*     */     //   49: aload_0
/*     */     //   50: invokevirtual 13	java/net/MulticastSocket:isBound	()Z
/*     */     //   53: ifne +24 -> 77
/*     */     //   56: aload_0
/*     */     //   57: invokevirtual 14	java/net/MulticastSocket:close	()V
/*     */     //   60: goto +17 -> 77
/*     */     //   63: astore_2
/*     */     //   64: aload_0
/*     */     //   65: invokevirtual 13	java/net/MulticastSocket:isBound	()Z
/*     */     //   68: ifne +7 -> 75
/*     */     //   71: aload_0
/*     */     //   72: invokevirtual 14	java/net/MulticastSocket:close	()V
/*     */     //   75: aload_2
/*     */     //   76: athrow
/*     */     //   77: return
/*     */     // Line number table:
/*     */     //   Java source line #165	-> byte code offset #0
/*     */     //   Java source line #184	-> byte code offset #8
/*     */     //   Java source line #190	-> byte code offset #19
/*     */     //   Java source line #195	-> byte code offset #30
/*     */     //   Java source line #168	-> byte code offset #35
/*     */     //   Java source line #170	-> byte code offset #40
/*     */     //   Java source line #172	-> byte code offset #44
/*     */     //   Java source line #174	-> byte code offset #49
/*     */     //   Java source line #175	-> byte code offset #56
/*     */     //   Java source line #174	-> byte code offset #63
/*     */     //   Java source line #175	-> byte code offset #71
/*     */     //   Java source line #178	-> byte code offset #77
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	78	0	this	MulticastSocket
/*     */     //   0	78	1	paramSocketAddress	SocketAddress
/*     */     //   63	13	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   44	49	63	finally
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setTTL(byte paramByte)
/*     */     throws IOException
/*     */   {
/* 215 */     if (isClosed())
/* 216 */       throw new SocketException("Socket is closed");
/* 217 */     getImpl().setTTL(paramByte);
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
/*     */   public void setTimeToLive(int paramInt)
/*     */     throws IOException
/*     */   {
/* 240 */     if ((paramInt < 0) || (paramInt > 255)) {
/* 241 */       throw new IllegalArgumentException("ttl out of range");
/*     */     }
/* 243 */     if (isClosed())
/* 244 */       throw new SocketException("Socket is closed");
/* 245 */     getImpl().setTimeToLive(paramInt);
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
/*     */   @Deprecated
/*     */   public byte getTTL()
/*     */     throws IOException
/*     */   {
/* 261 */     if (isClosed())
/* 262 */       throw new SocketException("Socket is closed");
/* 263 */     return getImpl().getTTL();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTimeToLive()
/*     */     throws IOException
/*     */   {
/* 275 */     if (isClosed())
/* 276 */       throw new SocketException("Socket is closed");
/* 277 */     return getImpl().getTimeToLive();
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
/*     */   public void joinGroup(InetAddress paramInetAddress)
/*     */     throws IOException
/*     */   {
/* 299 */     if (isClosed()) {
/* 300 */       throw new SocketException("Socket is closed");
/*     */     }
/*     */     
/* 303 */     checkAddress(paramInetAddress, "joinGroup");
/* 304 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 305 */     if (localSecurityManager != null) {
/* 306 */       localSecurityManager.checkMulticast(paramInetAddress);
/*     */     }
/*     */     
/* 309 */     if (!paramInetAddress.isMulticastAddress()) {
/* 310 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 317 */     NetworkInterface localNetworkInterface = NetworkInterface.getDefault();
/*     */     
/* 319 */     if ((!this.interfaceSet) && (localNetworkInterface != null)) {
/* 320 */       setNetworkInterface(localNetworkInterface);
/*     */     }
/*     */     
/* 323 */     getImpl().join(paramInetAddress);
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
/*     */   public void leaveGroup(InetAddress paramInetAddress)
/*     */     throws IOException
/*     */   {
/* 344 */     if (isClosed()) {
/* 345 */       throw new SocketException("Socket is closed");
/*     */     }
/*     */     
/* 348 */     checkAddress(paramInetAddress, "leaveGroup");
/* 349 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 350 */     if (localSecurityManager != null) {
/* 351 */       localSecurityManager.checkMulticast(paramInetAddress);
/*     */     }
/*     */     
/* 354 */     if (!paramInetAddress.isMulticastAddress()) {
/* 355 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/* 358 */     getImpl().leave(paramInetAddress);
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
/*     */   public void joinGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface)
/*     */     throws IOException
/*     */   {
/* 387 */     if (isClosed()) {
/* 388 */       throw new SocketException("Socket is closed");
/*     */     }
/* 390 */     if ((paramSocketAddress == null) || (!(paramSocketAddress instanceof InetSocketAddress))) {
/* 391 */       throw new IllegalArgumentException("Unsupported address type");
/*     */     }
/* 393 */     if (this.oldImpl) {
/* 394 */       throw new UnsupportedOperationException();
/*     */     }
/* 396 */     checkAddress(((InetSocketAddress)paramSocketAddress).getAddress(), "joinGroup");
/* 397 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 398 */     if (localSecurityManager != null) {
/* 399 */       localSecurityManager.checkMulticast(((InetSocketAddress)paramSocketAddress).getAddress());
/*     */     }
/*     */     
/* 402 */     if (!((InetSocketAddress)paramSocketAddress).getAddress().isMulticastAddress()) {
/* 403 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/* 406 */     getImpl().joinGroup(paramSocketAddress, paramNetworkInterface);
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
/*     */   public void leaveGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface)
/*     */     throws IOException
/*     */   {
/* 434 */     if (isClosed()) {
/* 435 */       throw new SocketException("Socket is closed");
/*     */     }
/* 437 */     if ((paramSocketAddress == null) || (!(paramSocketAddress instanceof InetSocketAddress))) {
/* 438 */       throw new IllegalArgumentException("Unsupported address type");
/*     */     }
/* 440 */     if (this.oldImpl) {
/* 441 */       throw new UnsupportedOperationException();
/*     */     }
/* 443 */     checkAddress(((InetSocketAddress)paramSocketAddress).getAddress(), "leaveGroup");
/* 444 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 445 */     if (localSecurityManager != null) {
/* 446 */       localSecurityManager.checkMulticast(((InetSocketAddress)paramSocketAddress).getAddress());
/*     */     }
/*     */     
/* 449 */     if (!((InetSocketAddress)paramSocketAddress).getAddress().isMulticastAddress()) {
/* 450 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/* 453 */     getImpl().leaveGroup(paramSocketAddress, paramNetworkInterface);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInterface(InetAddress paramInetAddress)
/*     */     throws SocketException
/*     */   {
/* 466 */     if (isClosed()) {
/* 467 */       throw new SocketException("Socket is closed");
/*     */     }
/* 469 */     checkAddress(paramInetAddress, "setInterface");
/* 470 */     synchronized (this.infLock) {
/* 471 */       getImpl().setOption(16, paramInetAddress);
/* 472 */       this.infAddress = paramInetAddress;
/* 473 */       this.interfaceSet = true;
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
/*     */   public InetAddress getInterface()
/*     */     throws SocketException
/*     */   {
/* 491 */     if (isClosed()) {
/* 492 */       throw new SocketException("Socket is closed");
/*     */     }
/* 494 */     synchronized (this.infLock)
/*     */     {
/* 496 */       InetAddress localInetAddress1 = (InetAddress)getImpl().getOption(16);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 502 */       if (this.infAddress == null) {
/* 503 */         return localInetAddress1;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 509 */       if (localInetAddress1.equals(this.infAddress)) {
/* 510 */         return localInetAddress1;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 519 */         NetworkInterface localNetworkInterface = NetworkInterface.getByInetAddress(localInetAddress1);
/* 520 */         Enumeration localEnumeration = localNetworkInterface.getInetAddresses();
/* 521 */         while (localEnumeration.hasMoreElements()) {
/* 522 */           InetAddress localInetAddress2 = (InetAddress)localEnumeration.nextElement();
/* 523 */           if (localInetAddress2.equals(this.infAddress)) {
/* 524 */             return this.infAddress;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 532 */         this.infAddress = null;
/* 533 */         return localInetAddress1;
/*     */       } catch (Exception localException) {
/* 535 */         return localInetAddress1;
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
/*     */   public void setNetworkInterface(NetworkInterface paramNetworkInterface)
/*     */     throws SocketException
/*     */   {
/* 553 */     synchronized (this.infLock) {
/* 554 */       getImpl().setOption(31, paramNetworkInterface);
/* 555 */       this.infAddress = null;
/* 556 */       this.interfaceSet = true;
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
/*     */   public NetworkInterface getNetworkInterface()
/*     */     throws SocketException
/*     */   {
/* 571 */     NetworkInterface localNetworkInterface = (NetworkInterface)getImpl().getOption(31);
/* 572 */     if (localNetworkInterface.getIndex() == 0) {
/* 573 */       InetAddress[] arrayOfInetAddress = new InetAddress[1];
/* 574 */       arrayOfInetAddress[0] = InetAddress.anyLocalAddress();
/* 575 */       return new NetworkInterface(arrayOfInetAddress[0].getHostName(), 0, arrayOfInetAddress);
/*     */     }
/* 577 */     return localNetworkInterface;
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
/*     */   public void setLoopbackMode(boolean paramBoolean)
/*     */     throws SocketException
/*     */   {
/* 596 */     getImpl().setOption(18, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getLoopbackMode()
/*     */     throws SocketException
/*     */   {
/* 608 */     return ((Boolean)getImpl().getOption(18)).booleanValue();
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
/*     */   @Deprecated
/*     */   public void send(DatagramPacket paramDatagramPacket, byte paramByte)
/*     */     throws IOException
/*     */   {
/* 661 */     if (isClosed())
/* 662 */       throw new SocketException("Socket is closed");
/* 663 */     checkAddress(paramDatagramPacket.getAddress(), "send");
/* 664 */     synchronized (this.ttlLock) {
/* 665 */       synchronized (paramDatagramPacket) { Object localObject1;
/* 666 */         if (this.connectState == 0)
/*     */         {
/*     */ 
/*     */ 
/* 670 */           localObject1 = System.getSecurityManager();
/* 671 */           if (localObject1 != null) {
/* 672 */             if (paramDatagramPacket.getAddress().isMulticastAddress()) {
/* 673 */               ((SecurityManager)localObject1).checkMulticast(paramDatagramPacket.getAddress(), paramByte);
/*     */             } else {
/* 675 */               ((SecurityManager)localObject1).checkConnect(paramDatagramPacket.getAddress().getHostAddress(), paramDatagramPacket
/* 676 */                 .getPort());
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/* 681 */           localObject1 = null;
/* 682 */           localObject1 = paramDatagramPacket.getAddress();
/* 683 */           if (localObject1 == null) {
/* 684 */             paramDatagramPacket.setAddress(this.connectedAddress);
/* 685 */             paramDatagramPacket.setPort(this.connectedPort);
/* 686 */           } else if ((!((InetAddress)localObject1).equals(this.connectedAddress)) || 
/* 687 */             (paramDatagramPacket.getPort() != this.connectedPort)) {
/* 688 */             throw new SecurityException("connected address and packet address differ");
/*     */           }
/*     */         }
/*     */         
/* 692 */         byte b = getTTL();
/*     */         try {
/* 694 */           if (paramByte != b)
/*     */           {
/* 696 */             getImpl().setTTL(paramByte);
/*     */           }
/*     */           
/* 699 */           getImpl().send(paramDatagramPacket);
/*     */         }
/*     */         finally {
/* 702 */           if (paramByte != b) {
/* 703 */             getImpl().setTTL(b);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/MulticastSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */