/*    */ package java.lang.ref;
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
/*    */ class FinalReference<T>
/*    */   extends Reference<T>
/*    */ {
/*    */   public FinalReference(T paramT, ReferenceQueue<? super T> paramReferenceQueue)
/*    */   {
/* 34 */     super(paramT, paramReferenceQueue);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ref/FinalReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */