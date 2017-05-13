/*    */ package java.rmi.server;
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
/*    */ @Deprecated
/*    */ public abstract class RemoteStub
/*    */   extends RemoteObject
/*    */ {
/*    */   private static final long serialVersionUID = -1585587260594494182L;
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
/*    */   protected RemoteStub() {}
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
/*    */   protected RemoteStub(RemoteRef paramRemoteRef)
/*    */   {
/* 63 */     super(paramRemoteRef);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   protected static void setRef(RemoteStub paramRemoteStub, RemoteRef paramRemoteRef)
/*    */   {
/* 81 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/server/RemoteStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */