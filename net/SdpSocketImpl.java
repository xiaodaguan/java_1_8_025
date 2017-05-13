/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.net.sdp.SdpSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SdpSocketImpl
/*    */   extends PlainSocketImpl
/*    */ {
/*    */   protected void create(boolean paramBoolean)
/*    */     throws IOException
/*    */   {
/* 41 */     if (!paramBoolean)
/* 42 */       throw new UnsupportedOperationException("Must be a stream socket");
/* 43 */     this.fd = SdpSupport.createSocket();
/* 44 */     if (this.socket != null)
/* 45 */       this.socket.setCreated();
/* 46 */     if (this.serverSocket != null) {
/* 47 */       this.serverSocket.setCreated();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/SdpSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */