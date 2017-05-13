/*    */ package java.lang.management;
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
/*    */ public enum MemoryType
/*    */ {
/* 43 */   HEAP("Heap memory"), 
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
/* 56 */   NON_HEAP("Non-heap memory");
/*    */   
/*    */   private final String description;
/*    */   private static final long serialVersionUID = 6992337162326171013L;
/*    */   
/* 61 */   private MemoryType(String paramString) { this.description = paramString; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 69 */     return this.description;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/MemoryType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */