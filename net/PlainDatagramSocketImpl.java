/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import jdk.net.ExtendedSocketOptions;
/*    */ import jdk.net.SocketFlow;
/*    */ import sun.net.ExtendedOptionsImpl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlainDatagramSocketImpl
/*    */   extends AbstractPlainDatagramSocketImpl
/*    */ {
/*    */   protected <T> void setOption(SocketOption<T> paramSocketOption, T paramT)
/*    */     throws IOException
/*    */   {
/* 47 */     if (!paramSocketOption.equals(ExtendedSocketOptions.SO_FLOW_SLA)) {
/* 48 */       super.setOption(paramSocketOption, paramT);
/*    */     } else {
/* 50 */       if (isClosed()) {
/* 51 */         throw new SocketException("Socket closed");
/*    */       }
/* 53 */       ExtendedOptionsImpl.checkSetOptionPermission(paramSocketOption);
/* 54 */       ExtendedOptionsImpl.checkValueType(paramT, SocketFlow.class);
/* 55 */       ExtendedOptionsImpl.setFlowOption(getFileDescriptor(), (SocketFlow)paramT);
/*    */     }
/*    */   }
/*    */   
/*    */   protected <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/* 60 */     if (!paramSocketOption.equals(ExtendedSocketOptions.SO_FLOW_SLA)) {
/* 61 */       return (T)super.getOption(paramSocketOption);
/*    */     }
/* 63 */     if (isClosed()) {
/* 64 */       throw new SocketException("Socket closed");
/*    */     }
/* 66 */     ExtendedOptionsImpl.checkGetOptionPermission(paramSocketOption);
/* 67 */     SocketFlow localSocketFlow = SocketFlow.create();
/* 68 */     ExtendedOptionsImpl.getFlowOption(getFileDescriptor(), localSocketFlow);
/* 69 */     return localSocketFlow;
/*    */   }
/*    */   
/*    */   protected synchronized native void bind0(int paramInt, InetAddress paramInetAddress)
/*    */     throws SocketException;
/*    */   
/*    */   protected native void send(DatagramPacket paramDatagramPacket)
/*    */     throws IOException;
/*    */   
/*    */   protected synchronized native int peek(InetAddress paramInetAddress)
/*    */     throws IOException;
/*    */   
/*    */   protected synchronized native int peekData(DatagramPacket paramDatagramPacket)
/*    */     throws IOException;
/*    */   
/*    */   protected synchronized native void receive0(DatagramPacket paramDatagramPacket)
/*    */     throws IOException;
/*    */   
/*    */   protected native void setTimeToLive(int paramInt)
/*    */     throws IOException;
/*    */   
/*    */   protected native int getTimeToLive()
/*    */     throws IOException;
/*    */   
/*    */   @Deprecated
/*    */   protected native void setTTL(byte paramByte)
/*    */     throws IOException;
/*    */   
/*    */   @Deprecated
/*    */   protected native byte getTTL()
/*    */     throws IOException;
/*    */   
/*    */   protected native void join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface)
/*    */     throws IOException;
/*    */   
/*    */   protected native void leave(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface)
/*    */     throws IOException;
/*    */   
/*    */   protected native void datagramSocketCreate()
/*    */     throws SocketException;
/*    */   
/*    */   protected native void datagramSocketClose();
/*    */   
/*    */   protected native void socketSetOption(int paramInt, Object paramObject)
/*    */     throws SocketException;
/*    */   
/*    */   protected native Object socketGetOption(int paramInt)
/*    */     throws SocketException;
/*    */   
/*    */   protected native void connect0(InetAddress paramInetAddress, int paramInt)
/*    */     throws SocketException;
/*    */   
/*    */   protected native void disconnect0(int paramInt);
/*    */   
/*    */   private static native void init();
/*    */   
/*    */   static {}
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/PlainDatagramSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */