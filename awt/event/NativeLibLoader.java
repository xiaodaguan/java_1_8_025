/*    */ package java.awt.event;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NativeLibLoader
/*    */ {
/*    */   static void loadLibraries()
/*    */   {
/* 56 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 59 */         System.loadLibrary("awt");
/* 60 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/NativeLibLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */