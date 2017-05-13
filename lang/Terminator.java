/*    */ package java.lang;
/*    */ 
/*    */ import sun.misc.Signal;
/*    */ import sun.misc.SignalHandler;
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
/*    */ class Terminator
/*    */ {
/* 42 */   private static SignalHandler handler = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   static void setup()
/*    */   {
/* 49 */     if (handler != null) return;
/* 50 */     SignalHandler local1 = new SignalHandler() {
/*    */       public void handle(Signal paramAnonymousSignal) {
/* 52 */         Shutdown.exit(paramAnonymousSignal.getNumber() + 128);
/*    */       }
/* 54 */     };
/* 55 */     handler = local1;
/*    */     
/*    */ 
/*    */     try
/*    */     {
/* 60 */       Signal.handle(new Signal("HUP"), local1);
/*    */     }
/*    */     catch (IllegalArgumentException localIllegalArgumentException1) {}
/*    */     try {
/* 64 */       Signal.handle(new Signal("INT"), local1);
/*    */     }
/*    */     catch (IllegalArgumentException localIllegalArgumentException2) {}
/*    */     try {
/* 68 */       Signal.handle(new Signal("TERM"), local1);
/*    */     }
/*    */     catch (IllegalArgumentException localIllegalArgumentException3) {}
/*    */   }
/*    */   
/*    */   static void teardown() {}
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Terminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */