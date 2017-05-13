/*    */ package java.net;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.security.AccessController;
/*    */ import sun.security.action.GetPropertyAction;
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
/*    */ class DefaultDatagramSocketImplFactory
/*    */ {
/* 38 */   static Class<?> prefixImplClass = null;
/*    */   
/*    */   static {
/* 41 */     String str = null;
/*    */     try {
/* 43 */       str = (String)AccessController.doPrivileged(new GetPropertyAction("impl.prefix", null));
/*    */       
/* 45 */       if (str != null)
/* 46 */         prefixImplClass = Class.forName("java.net." + str + "DatagramSocketImpl");
/*    */     } catch (Exception localException) {
/* 48 */       System.err.println("Can't find class: java.net." + str + "DatagramSocketImpl: check impl.prefix property");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static DatagramSocketImpl createDatagramSocketImpl(boolean paramBoolean)
/*    */     throws SocketException
/*    */   {
/* 63 */     if (prefixImplClass != null) {
/*    */       try {
/* 65 */         return (DatagramSocketImpl)prefixImplClass.newInstance();
/*    */       } catch (Exception localException) {
/* 67 */         throw new SocketException("can't instantiate DatagramSocketImpl");
/*    */       }
/*    */     }
/* 70 */     return new PlainDatagramSocketImpl();
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/DefaultDatagramSocketImplFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */