/*    */ package java.lang.invoke;
/*    */ 
/*    */ import java.util.List;
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
/*    */ final class SimpleMethodHandle
/*    */   extends MethodHandle
/*    */ {
/*    */   private SimpleMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm)
/*    */   {
/* 39 */     super(paramMethodType, paramLambdaForm);
/*    */   }
/*    */   
/*    */   static SimpleMethodHandle make(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/* 43 */     return new SimpleMethodHandle(paramMethodType, paramLambdaForm);
/*    */   }
/*    */   
/*    */   MethodHandle bindArgument(int paramInt, char paramChar, Object paramObject)
/*    */   {
/* 48 */     MethodType localMethodType = type().dropParameterTypes(paramInt, paramInt + 1);
/* 49 */     LambdaForm localLambdaForm = internalForm().bind(1 + paramInt, BoundMethodHandle.SpeciesData.EMPTY);
/* 50 */     return BoundMethodHandle.bindSingle(localMethodType, localLambdaForm, paramChar, paramObject);
/*    */   }
/*    */   
/*    */   MethodHandle dropArguments(MethodType paramMethodType, int paramInt1, int paramInt2)
/*    */   {
/* 55 */     LambdaForm localLambdaForm = internalForm().addArguments(paramInt1, paramMethodType.parameterList().subList(paramInt1, paramInt1 + paramInt2));
/* 56 */     return new SimpleMethodHandle(paramMethodType, localLambdaForm);
/*    */   }
/*    */   
/*    */   MethodHandle permuteArguments(MethodType paramMethodType, int[] paramArrayOfInt)
/*    */   {
/* 61 */     LambdaForm localLambdaForm = internalForm().permuteArguments(1, paramArrayOfInt, LambdaForm.basicTypes(paramMethodType.parameterList()));
/* 62 */     return new SimpleMethodHandle(paramMethodType, localLambdaForm);
/*    */   }
/*    */   
/*    */   MethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm)
/*    */   {
/* 67 */     return new SimpleMethodHandle(paramMethodType, paramLambdaForm);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/SimpleMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */