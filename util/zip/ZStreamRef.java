/*    */ package java.util.zip;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ZStreamRef
/*    */ {
/*    */   private long address;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   ZStreamRef(long paramLong)
/*    */   {
/* 36 */     this.address = paramLong;
/*    */   }
/*    */   
/*    */   long address() {
/* 40 */     return this.address;
/*    */   }
/*    */   
/*    */   void clear() {
/* 44 */     this.address = 0L;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/ZStreamRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */