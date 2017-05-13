/*    */ package java.awt;
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
/*    */ public class HeadlessException
/*    */   extends UnsupportedOperationException
/*    */ {
/*    */   private static final long serialVersionUID = 167183644944358563L;
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
/*    */   public HeadlessException() {}
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
/* 43 */   public HeadlessException(String paramString) { super(paramString); }
/*    */   
/*    */   public String getMessage() {
/* 46 */     String str1 = super.getMessage();
/* 47 */     String str2 = GraphicsEnvironment.getHeadlessMessage();
/*    */     
/* 49 */     if (str1 == null)
/* 50 */       return str2;
/* 51 */     if (str2 == null) {
/* 52 */       return str1;
/*    */     }
/* 54 */     return str1 + str2;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/HeadlessException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */