/*     */ package java.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import sun.rmi.transport.proxy.RMIMasterSocketFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RMISocketFactory
/*     */   implements RMIClientSocketFactory, RMIServerSocketFactory
/*     */ {
/*  95 */   private static RMISocketFactory factory = null;
/*     */   
/*     */   private static RMISocketFactory defaultSocketFactory;
/*     */   
/*  99 */   private static RMIFailureHandler handler = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Socket createSocket(String paramString, int paramInt)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ServerSocket createServerSocket(int paramInt)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized void setSocketFactory(RMISocketFactory paramRMISocketFactory)
/*     */     throws IOException
/*     */   {
/* 150 */     if (factory != null) {
/* 151 */       throw new SocketException("factory already defined");
/*     */     }
/* 153 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 154 */     if (localSecurityManager != null) {
/* 155 */       localSecurityManager.checkSetFactory();
/*     */     }
/* 157 */     factory = paramRMISocketFactory;
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
/*     */   public static synchronized RMISocketFactory getSocketFactory()
/*     */   {
/* 170 */     return factory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized RMISocketFactory getDefaultSocketFactory()
/*     */   {
/* 182 */     if (defaultSocketFactory == null) {
/* 183 */       defaultSocketFactory = new RMIMasterSocketFactory();
/*     */     }
/*     */     
/* 186 */     return defaultSocketFactory;
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
/*     */   public static synchronized void setFailureHandler(RMIFailureHandler paramRMIFailureHandler)
/*     */   {
/* 210 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 211 */     if (localSecurityManager != null) {
/* 212 */       localSecurityManager.checkSetFactory();
/*     */     }
/* 214 */     handler = paramRMIFailureHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized RMIFailureHandler getFailureHandler()
/*     */   {
/* 226 */     return handler;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/server/RMISocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */