/*    */ package java.rmi;
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
/*    */ public class ServerException
/*    */   extends RemoteException
/*    */ {
/*    */   private static final long serialVersionUID = -4775845313121906682L;
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
/*    */   public ServerException(String paramString)
/*    */   {
/* 53 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ServerException(String paramString, Exception paramException)
/*    */   {
/* 65 */     super(paramString, paramException);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/ServerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */