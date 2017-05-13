/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpConnectSocketImpl
/*     */   extends PlainSocketImpl
/*     */ {
/*     */   private static final String httpURLClazzStr = "sun.net.www.protocol.http.HttpURLConnection";
/*     */   private static final String netClientClazzStr = "sun.net.NetworkClient";
/*     */   private static final String doTunnelingStr = "doTunneling";
/*     */   private static final Field httpField;
/*     */   private static final Field serverSocketField;
/*     */   private static final Method doTunneling;
/*     */   private final String server;
/*     */   private InetSocketAddress external_address;
/*  56 */   private HashMap<Integer, Object> optionsMap = new HashMap();
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       Class localClass1 = Class.forName("sun.net.www.protocol.http.HttpURLConnection", true, null);
/*  61 */       httpField = localClass1.getDeclaredField("http");
/*  62 */       doTunneling = localClass1.getDeclaredMethod("doTunneling", new Class[0]);
/*  63 */       Class localClass2 = Class.forName("sun.net.NetworkClient", true, null);
/*  64 */       serverSocketField = localClass2.getDeclaredField("serverSocket");
/*     */       
/*  66 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Void run() {
/*  69 */           HttpConnectSocketImpl.httpField.setAccessible(true);
/*  70 */           HttpConnectSocketImpl.serverSocketField.setAccessible(true);
/*  71 */           return null;
/*     */         }
/*     */       });
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/*  75 */       throw new InternalError("Should not reach here", localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */   
/*     */   HttpConnectSocketImpl(String paramString, int paramInt) {
/*  80 */     this.server = paramString;
/*  81 */     this.port = paramInt;
/*     */   }
/*     */   
/*     */   HttpConnectSocketImpl(Proxy paramProxy) {
/*  85 */     SocketAddress localSocketAddress = paramProxy.address();
/*  86 */     if (!(localSocketAddress instanceof InetSocketAddress)) {
/*  87 */       throw new IllegalArgumentException("Unsupported address type");
/*     */     }
/*  89 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)localSocketAddress;
/*  90 */     this.server = localInetSocketAddress.getHostString();
/*  91 */     this.port = localInetSocketAddress.getPort();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void connect(SocketAddress paramSocketAddress, int paramInt)
/*     */     throws IOException
/*     */   {
/*  98 */     if ((paramSocketAddress == null) || (!(paramSocketAddress instanceof InetSocketAddress)))
/*  99 */       throw new IllegalArgumentException("Unsupported address type");
/* 100 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*     */     
/* 102 */     String str1 = localInetSocketAddress.isUnresolved() ? localInetSocketAddress.getHostName() : localInetSocketAddress.getAddress().getHostAddress();
/* 103 */     int i = localInetSocketAddress.getPort();
/*     */     
/* 105 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 106 */     if (localSecurityManager != null) {
/* 107 */       localSecurityManager.checkConnect(str1, i);
/*     */     }
/*     */     
/* 110 */     String str2 = "http://" + str1 + ":" + i;
/* 111 */     Socket localSocket = privilegedDoTunnel(str2, paramInt);
/*     */     
/*     */ 
/* 114 */     this.external_address = localInetSocketAddress;
/*     */     
/*     */ 
/* 117 */     close();
/*     */     
/*     */ 
/* 120 */     AbstractPlainSocketImpl localAbstractPlainSocketImpl = (AbstractPlainSocketImpl)localSocket.impl;
/* 121 */     getSocket().impl = localAbstractPlainSocketImpl;
/*     */     
/*     */ 
/* 124 */     Set localSet = this.optionsMap.entrySet();
/*     */     try {
/* 126 */       for (Map.Entry localEntry : localSet) {
/* 127 */         localAbstractPlainSocketImpl.setOption(((Integer)localEntry.getKey()).intValue(), localEntry.getValue());
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */   }
/*     */   
/*     */   public void setOption(int paramInt, Object paramObject) throws SocketException {
/* 134 */     super.setOption(paramInt, paramObject);
/*     */     
/* 136 */     if (this.external_address != null) {
/* 137 */       return;
/*     */     }
/*     */     
/* 140 */     this.optionsMap.put(Integer.valueOf(paramInt), paramObject);
/*     */   }
/*     */   
/*     */   private Socket privilegedDoTunnel(final String paramString, final int paramInt)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 148 */       (Socket)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Socket run() throws IOException {
/* 151 */           return HttpConnectSocketImpl.this.doTunnel(paramString, paramInt);
/*     */         }
/*     */       });
/*     */     } catch (PrivilegedActionException localPrivilegedActionException) {
/* 155 */       throw ((IOException)localPrivilegedActionException.getException());
/*     */     }
/*     */   }
/*     */   
/*     */   private Socket doTunnel(String paramString, int paramInt)
/*     */     throws IOException
/*     */   {
/* 162 */     Proxy localProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.server, this.port));
/* 163 */     URL localURL = new URL(paramString);
/* 164 */     HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection(localProxy);
/* 165 */     localHttpURLConnection.setConnectTimeout(paramInt);
/* 166 */     localHttpURLConnection.setReadTimeout(this.timeout);
/* 167 */     localHttpURLConnection.connect();
/* 168 */     doTunneling(localHttpURLConnection);
/*     */     try {
/* 170 */       Object localObject = httpField.get(localHttpURLConnection);
/* 171 */       return (Socket)serverSocketField.get(localObject);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 173 */       throw new InternalError("Should not reach here", localIllegalAccessException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void doTunneling(HttpURLConnection paramHttpURLConnection) {
/*     */     try {
/* 179 */       doTunneling.invoke(paramHttpURLConnection, new Object[0]);
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 181 */       throw new InternalError("Should not reach here", localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */   
/*     */   protected InetAddress getInetAddress()
/*     */   {
/* 187 */     if (this.external_address != null) {
/* 188 */       return this.external_address.getAddress();
/*     */     }
/* 190 */     return super.getInetAddress();
/*     */   }
/*     */   
/*     */   protected int getPort()
/*     */   {
/* 195 */     if (this.external_address != null) {
/* 196 */       return this.external_address.getPort();
/*     */     }
/* 198 */     return super.getPort();
/*     */   }
/*     */   
/*     */   protected int getLocalPort()
/*     */   {
/* 203 */     if (this.socket != null)
/* 204 */       return super.getLocalPort();
/* 205 */     if (this.external_address != null) {
/* 206 */       return this.external_address.getPort();
/*     */     }
/* 208 */     return super.getLocalPort();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/HttpConnectSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */