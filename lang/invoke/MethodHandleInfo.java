/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface MethodHandleInfo
/*     */ {
/*     */   public static final int REF_getField = 1;
/*     */   public static final int REF_getStatic = 2;
/*     */   public static final int REF_putField = 3;
/*     */   public static final int REF_putStatic = 4;
/*     */   public static final int REF_invokeVirtual = 5;
/*     */   public static final int REF_invokeStatic = 6;
/*     */   public static final int REF_invokeSpecial = 7;
/*     */   public static final int REF_newInvokeSpecial = 8;
/*     */   public static final int REF_invokeInterface = 9;
/*     */   
/*     */   public abstract int getReferenceKind();
/*     */   
/*     */   public abstract Class<?> getDeclaringClass();
/*     */   
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract MethodType getMethodType();
/*     */   
/*     */   public abstract <T extends Member> T reflectAs(Class<T> paramClass, MethodHandles.Lookup paramLookup);
/*     */   
/*     */   public abstract int getModifiers();
/*     */   
/*     */   public boolean isVarArgs()
/*     */   {
/* 227 */     if (MethodHandleNatives.refKindIsField((byte)getReferenceKind())) {
/* 228 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 232 */     return Modifier.isTransient(getModifiers());
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
/*     */   public static String referenceKindToString(int paramInt)
/*     */   {
/* 245 */     if (!MethodHandleNatives.refKindIsValid(paramInt))
/* 246 */       throw MethodHandleStatics.newIllegalArgumentException("invalid reference kind", Integer.valueOf(paramInt));
/* 247 */     return MethodHandleNatives.refKindName((byte)paramInt);
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
/*     */   public static String toString(int paramInt, Class<?> paramClass, String paramString, MethodType paramMethodType)
/*     */   {
/* 281 */     Objects.requireNonNull(paramString);Objects.requireNonNull(paramMethodType);
/* 282 */     return String.format("%s %s.%s:%s", new Object[] { referenceKindToString(paramInt), paramClass.getName(), paramString, paramMethodType });
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */