/*    */ package java.util.logging;
/*    */ 
/*    */ import java.io.PrintStream;
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
/*    */ public class ErrorManager
/*    */ {
/* 40 */   private boolean reported = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int GENERIC_FAILURE = 0;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int WRITE_FAILURE = 1;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int FLUSH_FAILURE = 2;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int CLOSE_FAILURE = 3;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int OPEN_FAILURE = 4;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int FORMAT_FAILURE = 5;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public synchronized void error(String paramString, Exception paramException, int paramInt)
/*    */   {
/* 84 */     if (this.reported)
/*    */     {
/*    */ 
/* 87 */       return;
/*    */     }
/* 89 */     this.reported = true;
/* 90 */     String str = "java.util.logging.ErrorManager: " + paramInt;
/* 91 */     if (paramString != null) {
/* 92 */       str = str + ": " + paramString;
/*    */     }
/* 94 */     System.err.println(str);
/* 95 */     if (paramException != null) {
/* 96 */       paramException.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/ErrorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */