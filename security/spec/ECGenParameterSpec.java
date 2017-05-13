/*    */ package java.security.spec;
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
/*    */ public class ECGenParameterSpec
/*    */   implements AlgorithmParameterSpec
/*    */ {
/*    */   private String name;
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
/*    */   public ECGenParameterSpec(String paramString)
/*    */   {
/* 54 */     if (paramString == null) {
/* 55 */       throw new NullPointerException("stdName is null");
/*    */     }
/* 57 */     this.name = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 66 */     return this.name;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/spec/ECGenParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */