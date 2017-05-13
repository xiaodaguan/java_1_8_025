/*    */ package java.lang;
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
/*    */ public class NumberFormatException
/*    */   extends IllegalArgumentException
/*    */ {
/*    */   static final long serialVersionUID = -2848938806368998894L;
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
/*    */   public NumberFormatException() {}
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
/*    */   public NumberFormatException(String paramString)
/*    */   {
/* 55 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static NumberFormatException forInputString(String paramString)
/*    */   {
/* 65 */     return new NumberFormatException("For input string: \"" + paramString + "\"");
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/NumberFormatException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */