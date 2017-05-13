/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LambdaMetafactory
/*     */ {
/*     */   public static final int FLAG_SERIALIZABLE = 1;
/*     */   public static final int FLAG_MARKERS = 2;
/*     */   public static final int FLAG_BRIDGES = 4;
/* 234 */   private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
/* 235 */   private static final MethodType[] EMPTY_MT_ARRAY = new MethodType[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CallSite metafactory(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType1, MethodType paramMethodType2, MethodHandle paramMethodHandle, MethodType paramMethodType3)
/*     */     throws LambdaConversionException
/*     */   {
/* 299 */     InnerClassLambdaMetafactory localInnerClassLambdaMetafactory = new InnerClassLambdaMetafactory(paramLookup, paramMethodType1, paramString, paramMethodType2, paramMethodHandle, paramMethodType3, false, EMPTY_CLASS_ARRAY, EMPTY_MT_ARRAY);
/*     */     
/*     */ 
/*     */ 
/* 303 */     localInnerClassLambdaMetafactory.validateMetafactoryArgs();
/* 304 */     return localInnerClassLambdaMetafactory.buildCallSite();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CallSite altMetafactory(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType, Object... paramVarArgs)
/*     */     throws LambdaConversionException
/*     */   {
/* 431 */     MethodType localMethodType1 = (MethodType)paramVarArgs[0];
/* 432 */     MethodHandle localMethodHandle = (MethodHandle)paramVarArgs[1];
/* 433 */     MethodType localMethodType2 = (MethodType)paramVarArgs[2];
/* 434 */     int i = ((Integer)paramVarArgs[3]).intValue();
/*     */     
/*     */ 
/* 437 */     int j = 4;
/* 438 */     Class[] arrayOfClass1; if ((i & 0x2) != 0) {
/* 439 */       k = ((Integer)paramVarArgs[(j++)]).intValue();
/* 440 */       arrayOfClass1 = new Class[k];
/* 441 */       System.arraycopy(paramVarArgs, j, arrayOfClass1, 0, k);
/* 442 */       j += k;
/*     */     }
/*     */     else {
/* 445 */       arrayOfClass1 = EMPTY_CLASS_ARRAY; }
/* 446 */     MethodType[] arrayOfMethodType; if ((i & 0x4) != 0) {
/* 447 */       k = ((Integer)paramVarArgs[(j++)]).intValue();
/* 448 */       arrayOfMethodType = new MethodType[k];
/* 449 */       System.arraycopy(paramVarArgs, j, arrayOfMethodType, 0, k);
/* 450 */       j += k;
/*     */     }
/*     */     else {
/* 453 */       arrayOfMethodType = EMPTY_MT_ARRAY;
/*     */     }
/* 455 */     int k = (i & 0x1) != 0 ? 1 : 0;
/* 456 */     if (k != 0) {
/* 457 */       boolean bool = Serializable.class.isAssignableFrom(paramMethodType.returnType());
/* 458 */       for (Class localClass : arrayOfClass1)
/* 459 */         bool |= Serializable.class.isAssignableFrom(localClass);
/* 460 */       if (!bool) {
/* 461 */         arrayOfClass1 = (Class[])Arrays.copyOf(arrayOfClass1, arrayOfClass1.length + 1);
/* 462 */         arrayOfClass1[(arrayOfClass1.length - 1)] = Serializable.class;
/*     */       }
/*     */     }
/*     */     
/* 466 */     InnerClassLambdaMetafactory localInnerClassLambdaMetafactory = new InnerClassLambdaMetafactory(paramLookup, paramMethodType, paramString, localMethodType1, localMethodHandle, localMethodType2, k, arrayOfClass1, arrayOfMethodType);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 473 */     localInnerClassLambdaMetafactory.validateMetafactoryArgs();
/* 474 */     return localInnerClassLambdaMetafactory.buildCallSite();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/LambdaMetafactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */