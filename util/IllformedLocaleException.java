/*    */ package java.util;
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
/*    */ public class IllformedLocaleException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -5245986824925681401L;
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
/* 46 */   private int _errIdx = -1;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IllformedLocaleException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IllformedLocaleException(String paramString)
/*    */   {
/* 63 */     super(paramString);
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
/*    */ 
/*    */   public IllformedLocaleException(String paramString, int paramInt)
/*    */   {
/* 77 */     super(paramString + (paramInt < 0 ? "" : new StringBuilder().append(" [at index ").append(paramInt).append("]").toString()));
/* 78 */     this._errIdx = paramInt;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getErrorIndex()
/*    */   {
/* 88 */     return this._errIdx;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/IllformedLocaleException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */