/*    */ package java.text;
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
/*    */ class DontCareFieldPosition
/*    */   extends FieldPosition
/*    */ {
/* 35 */   static final FieldPosition INSTANCE = new DontCareFieldPosition();
/*    */   
/* 37 */   private final Format.FieldDelegate noDelegate = new Format.FieldDelegate()
/*    */   {
/*    */     public void formatted(Format.Field paramAnonymousField, Object paramAnonymousObject, int paramAnonymousInt1, int paramAnonymousInt2, StringBuffer paramAnonymousStringBuffer) {}
/*    */     
/*    */ 
/*    */     public void formatted(int paramAnonymousInt1, Format.Field paramAnonymousField, Object paramAnonymousObject, int paramAnonymousInt2, int paramAnonymousInt3, StringBuffer paramAnonymousStringBuffer) {}
/*    */   };
/*    */   
/*    */   private DontCareFieldPosition()
/*    */   {
/* 47 */     super(0);
/*    */   }
/*    */   
/*    */   Format.FieldDelegate getFieldDelegate() {
/* 51 */     return this.noDelegate;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/DontCareFieldPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */