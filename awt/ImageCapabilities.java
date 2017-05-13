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
/*    */ public class ImageCapabilities
/*    */   implements Cloneable
/*    */ {
/* 35 */   private boolean accelerated = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ImageCapabilities(boolean paramBoolean)
/*    */   {
/* 42 */     this.accelerated = paramBoolean;
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
/*    */   public boolean isAccelerated()
/*    */   {
/* 55 */     return this.accelerated;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isTrueVolatile()
/*    */   {
/* 66 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public Object clone()
/*    */   {
/*    */     try
/*    */     {
/* 74 */       return super.clone();
/*    */     }
/*    */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 77 */       throw new InternalError(localCloneNotSupportedException);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/ImageCapabilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */