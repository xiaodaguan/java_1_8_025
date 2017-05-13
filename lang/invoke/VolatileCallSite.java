/*     */ package java.lang.invoke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VolatileCallSite
/*     */   extends CallSite
/*     */ {
/*     */   public VolatileCallSite(MethodType paramMethodType)
/*     */   {
/*  53 */     super(paramMethodType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public VolatileCallSite(MethodHandle paramMethodHandle)
/*     */   {
/*  63 */     super(paramMethodHandle);
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
/*     */   public final MethodHandle getTarget()
/*     */   {
/*  81 */     return getTargetVolatile();
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
/*     */   public void setTarget(MethodHandle paramMethodHandle)
/*     */   {
/*  98 */     checkTargetChange(getTargetVolatile(), paramMethodHandle);
/*  99 */     setTargetVolatile(paramMethodHandle);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final MethodHandle dynamicInvoker()
/*     */   {
/* 107 */     return makeDynamicInvoker();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/VolatileCallSite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */