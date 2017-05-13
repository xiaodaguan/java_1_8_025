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
/*    */ abstract interface EventFilter
/*    */ {
/*    */   public abstract FilterAction acceptEvent(AWTEvent paramAWTEvent);
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
/*    */   public static enum FilterAction
/*    */   {
/* 40 */     ACCEPT, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 46 */     REJECT, 
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
/* 57 */     ACCEPT_IMMEDIATELY;
/*    */     
/*    */     private FilterAction() {}
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/EventFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */