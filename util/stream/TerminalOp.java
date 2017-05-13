/*    */ package java.util.stream;
/*    */ 
/*    */ import java.util.Spliterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract interface TerminalOp<E_IN, R>
/*    */ {
/*    */   public StreamShape inputShape()
/*    */   {
/* 53 */     return StreamShape.REFERENCE;
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
/*    */   public int getOpFlags()
/*    */   {
/* 66 */     return 0;
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
/*    */ 
/*    */ 
/*    */   public <P_IN> R evaluateParallel(PipelineHelper<E_IN> paramPipelineHelper, Spliterator<P_IN> paramSpliterator)
/*    */   {
/* 82 */     if (Tripwire.ENABLED)
/* 83 */       Tripwire.trip(getClass(), "{0} triggering TerminalOp.evaluateParallel serial default");
/* 84 */     return (R)evaluateSequential(paramPipelineHelper, paramSpliterator);
/*    */   }
/*    */   
/*    */   public abstract <P_IN> R evaluateSequential(PipelineHelper<E_IN> paramPipelineHelper, Spliterator<P_IN> paramSpliterator);
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/TerminalOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */