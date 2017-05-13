/*     */ package java.net;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetworkInterface
/*     */ {
/*     */   private String name;
/*     */   private String displayName;
/*     */   private int index;
/*     */   private InetAddress[] addrs;
/*     */   private InterfaceAddress[] bindings;
/*     */   private NetworkInterface[] childs;
/*  50 */   private NetworkInterface parent = null;
/*  51 */   private boolean virtual = false;
/*     */   private static final NetworkInterface defaultInterface;
/*     */   private static final int defaultIndex;
/*     */   
/*     */   static {
/*  56 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  59 */         System.loadLibrary("net");
/*  60 */         return null;
/*     */       }
/*     */       
/*  63 */     });
/*  64 */     init();
/*  65 */     defaultInterface = DefaultInterface.getDefault();
/*  66 */     if (defaultInterface != null) {
/*  67 */       defaultIndex = defaultInterface.getIndex();
/*     */     } else {
/*  69 */       defaultIndex = 0;
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
/*     */   NetworkInterface(String paramString, int paramInt, InetAddress[] paramArrayOfInetAddress)
/*     */   {
/*  83 */     this.name = paramString;
/*  84 */     this.index = paramInt;
/*  85 */     this.addrs = paramArrayOfInetAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  94 */     return this.name;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Enumeration<InetAddress> getInetAddresses()
/*     */   {
/* 152 */     new Enumeration()
/*     */     {
/*     */       private InetAddress[] local_addrs;
/* 114 */       private int count = 0; private int i = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       public InetAddress nextElement()
/*     */       {
/* 141 */         if (this.i < this.count) {
/* 142 */           return this.local_addrs[(this.i++)];
/*     */         }
/* 144 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/*     */       public boolean hasMoreElements()
/*     */       {
/* 149 */         return this.i < this.count;
/*     */       }
/*     */     };
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
/*     */   public List<InterfaceAddress> getInterfaceAddresses()
/*     */   {
/* 170 */     ArrayList localArrayList = new ArrayList(1);
/* 171 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 172 */     for (int i = 0; i < this.bindings.length; i++) {
/*     */       try {
/* 174 */         if (localSecurityManager != null) {
/* 175 */           localSecurityManager.checkConnect(this.bindings[i].getAddress().getHostAddress(), -1);
/*     */         }
/* 177 */         localArrayList.add(this.bindings[i]);
/*     */       } catch (SecurityException localSecurityException) {}
/*     */     }
/* 180 */     return localArrayList;
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
/*     */   public Enumeration<NetworkInterface> getSubInterfaces()
/*     */   {
/* 213 */     new Enumeration()
/*     */     {
/* 196 */       private int i = 0;
/*     */       
/*     */ 
/*     */ 
/*     */       public NetworkInterface nextElement()
/*     */       {
/* 202 */         if (this.i < NetworkInterface.this.childs.length) {
/* 203 */           return NetworkInterface.this.childs[(this.i++)];
/*     */         }
/* 205 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/*     */       public boolean hasMoreElements()
/*     */       {
/* 210 */         return this.i < NetworkInterface.this.childs.length;
/*     */       }
/*     */     };
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
/*     */   public NetworkInterface getParent()
/*     */   {
/* 226 */     return this.parent;
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
/*     */   public int getIndex()
/*     */   {
/* 241 */     return this.index;
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
/*     */   public String getDisplayName()
/*     */   {
/* 254 */     return "".equals(this.displayName) ? null : this.displayName;
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
/*     */   public static NetworkInterface getByName(String paramString)
/*     */     throws SocketException
/*     */   {
/* 274 */     if (paramString == null)
/* 275 */       throw new NullPointerException();
/* 276 */     return getByName0(paramString);
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
/*     */   public static NetworkInterface getByIndex(int paramInt)
/*     */     throws SocketException
/*     */   {
/* 291 */     if (paramInt < 0)
/* 292 */       throw new IllegalArgumentException("Interface index can't be negative");
/* 293 */     return getByIndex0(paramInt);
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
/*     */   public static NetworkInterface getByInetAddress(InetAddress paramInetAddress)
/*     */     throws SocketException
/*     */   {
/* 319 */     if (paramInetAddress == null) {
/* 320 */       throw new NullPointerException();
/*     */     }
/* 322 */     if ((!(paramInetAddress instanceof Inet4Address)) && (!(paramInetAddress instanceof Inet6Address))) {
/* 323 */       throw new IllegalArgumentException("invalid address type");
/*     */     }
/* 325 */     return getByInetAddress0(paramInetAddress);
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
/*     */   public static Enumeration<NetworkInterface> getNetworkInterfaces()
/*     */     throws SocketException
/*     */   {
/* 343 */     NetworkInterface[] arrayOfNetworkInterface = getAll();
/*     */     
/*     */ 
/* 346 */     if (arrayOfNetworkInterface == null) {
/* 347 */       return null;
/*     */     }
/* 349 */     new Enumeration() {
/* 350 */       private int i = 0;
/*     */       
/* 352 */       public NetworkInterface nextElement() { if ((this.val$netifs != null) && (this.i < this.val$netifs.length)) {
/* 353 */           NetworkInterface localNetworkInterface = this.val$netifs[(this.i++)];
/* 354 */           return localNetworkInterface;
/*     */         }
/* 356 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/*     */       public boolean hasMoreElements()
/*     */       {
/* 361 */         return (this.val$netifs != null) && (this.i < this.val$netifs.length);
/*     */       }
/*     */     };
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
/*     */   public boolean isUp()
/*     */     throws SocketException
/*     */   {
/* 387 */     return isUp0(this.name, this.index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLoopback()
/*     */     throws SocketException
/*     */   {
/* 399 */     return isLoopback0(this.name, this.index);
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
/*     */   public boolean isPointToPoint()
/*     */     throws SocketException
/*     */   {
/* 414 */     return isP2P0(this.name, this.index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean supportsMulticast()
/*     */     throws SocketException
/*     */   {
/* 426 */     return supportsMulticast0(this.name, this.index);
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
/*     */   public byte[] getHardwareAddress()
/*     */     throws SocketException
/*     */   {
/* 444 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 445 */     if (localSecurityManager != null) {
/*     */       try {
/* 447 */         localSecurityManager.checkPermission(new NetPermission("getNetworkInformation"));
/*     */       } catch (SecurityException localSecurityException) {
/* 449 */         if (!getInetAddresses().hasMoreElements())
/*     */         {
/* 451 */           return null;
/*     */         }
/*     */       }
/*     */     }
/* 455 */     for (InetAddress localInetAddress : this.addrs) {
/* 456 */       if ((localInetAddress instanceof Inet4Address)) {
/* 457 */         return getMacAddr0(((Inet4Address)localInetAddress).getAddress(), this.name, this.index);
/*     */       }
/*     */     }
/* 460 */     return getMacAddr0(null, this.name, this.index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMTU()
/*     */     throws SocketException
/*     */   {
/* 471 */     return getMTU0(this.name, this.index);
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
/*     */   public boolean isVirtual()
/*     */   {
/* 488 */     return this.virtual;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 513 */     if (!(paramObject instanceof NetworkInterface)) {
/* 514 */       return false;
/*     */     }
/* 516 */     NetworkInterface localNetworkInterface = (NetworkInterface)paramObject;
/* 517 */     if (this.name != null) {
/* 518 */       if (!this.name.equals(localNetworkInterface.name)) {
/* 519 */         return false;
/*     */       }
/*     */     }
/* 522 */     else if (localNetworkInterface.name != null) {
/* 523 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 527 */     if (this.addrs == null)
/* 528 */       return localNetworkInterface.addrs == null;
/* 529 */     if (localNetworkInterface.addrs == null) {
/* 530 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 535 */     if (this.addrs.length != localNetworkInterface.addrs.length) {
/* 536 */       return false;
/*     */     }
/*     */     
/* 539 */     InetAddress[] arrayOfInetAddress = localNetworkInterface.addrs;
/* 540 */     int i = arrayOfInetAddress.length;
/*     */     
/* 542 */     for (int j = 0; j < i; j++) {
/* 543 */       int k = 0;
/* 544 */       for (int m = 0; m < i; m++) {
/* 545 */         if (this.addrs[j].equals(arrayOfInetAddress[m])) {
/* 546 */           k = 1;
/* 547 */           break;
/*     */         }
/*     */       }
/* 550 */       if (k == 0) {
/* 551 */         return false;
/*     */       }
/*     */     }
/* 554 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 558 */     return this.name == null ? 0 : this.name.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 562 */     String str = "name:";
/* 563 */     str = str + (this.name == null ? "null" : this.name);
/* 564 */     if (this.displayName != null) {
/* 565 */       str = str + " (" + this.displayName + ")";
/*     */     }
/* 567 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static NetworkInterface getDefault()
/*     */   {
/* 578 */     return defaultInterface;
/*     */   }
/*     */   
/*     */   NetworkInterface() {}
/*     */   
/*     */   private static native NetworkInterface[] getAll()
/*     */     throws SocketException;
/*     */   
/*     */   private static native NetworkInterface getByName0(String paramString)
/*     */     throws SocketException;
/*     */   
/*     */   private static native NetworkInterface getByIndex0(int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native NetworkInterface getByInetAddress0(InetAddress paramInetAddress)
/*     */     throws SocketException;
/*     */   
/*     */   private static native boolean isUp0(String paramString, int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native boolean isLoopback0(String paramString, int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native boolean supportsMulticast0(String paramString, int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native boolean isP2P0(String paramString, int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native byte[] getMacAddr0(byte[] paramArrayOfByte, String paramString, int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native int getMTU0(String paramString, int paramInt)
/*     */     throws SocketException;
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/NetworkInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */