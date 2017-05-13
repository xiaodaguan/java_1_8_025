/*    */ package java.nio.channels.spi;
/*    */ 
/*    */ import java.nio.channels.SelectionKey;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractSelectionKey
/*    */   extends SelectionKey
/*    */ {
/* 50 */   private volatile boolean valid = true;
/*    */   
/*    */   public final boolean isValid() {
/* 53 */     return this.valid;
/*    */   }
/*    */   
/*    */   void invalidate() {
/* 57 */     this.valid = false;
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
/*    */   public final void cancel()
/*    */   {
/* 70 */     synchronized (this) {
/* 71 */       if (this.valid) {
/* 72 */         this.valid = false;
/* 73 */         ((AbstractSelector)selector()).cancel(this);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/spi/AbstractSelectionKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */