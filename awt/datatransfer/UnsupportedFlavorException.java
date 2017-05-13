/*    */ package java.awt.datatransfer;
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
/*    */ public class UnsupportedFlavorException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 5383814944251665601L;
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
/*    */   public UnsupportedFlavorException(DataFlavor paramDataFlavor)
/*    */   {
/* 48 */     super(paramDataFlavor != null ? paramDataFlavor.getHumanPresentableName() : null);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/UnsupportedFlavorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */