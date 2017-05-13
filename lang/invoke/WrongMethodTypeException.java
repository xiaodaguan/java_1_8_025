/*    */ package java.lang.invoke;
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
/*    */ public class WrongMethodTypeException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 292L;
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
/*    */   public WrongMethodTypeException() {}
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
/*    */   public WrongMethodTypeException(String paramString)
/*    */   {
/* 60 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   WrongMethodTypeException(String paramString, Throwable paramThrowable)
/*    */   {
/* 72 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   WrongMethodTypeException(Throwable paramThrowable)
/*    */   {
/* 83 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/WrongMethodTypeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */