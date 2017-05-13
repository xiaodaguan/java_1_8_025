/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
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
/*     */ public final class SerializedLambda
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8025925345765570181L;
/*     */   private final Class<?> capturingClass;
/*     */   private final String functionalInterfaceClass;
/*     */   private final String functionalInterfaceMethodName;
/*     */   private final String functionalInterfaceMethodSignature;
/*     */   private final String implClass;
/*     */   private final String implMethodName;
/*     */   private final String implMethodSignature;
/*     */   private final int implMethodKind;
/*     */   private final String instantiatedMethodType;
/*     */   private final Object[] capturedArgs;
/*     */   
/*     */   public SerializedLambda(Class<?> paramClass, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, Object[] paramArrayOfObject)
/*     */   {
/* 107 */     this.capturingClass = paramClass;
/* 108 */     this.functionalInterfaceClass = paramString1;
/* 109 */     this.functionalInterfaceMethodName = paramString2;
/* 110 */     this.functionalInterfaceMethodSignature = paramString3;
/* 111 */     this.implMethodKind = paramInt;
/* 112 */     this.implClass = paramString4;
/* 113 */     this.implMethodName = paramString5;
/* 114 */     this.implMethodSignature = paramString6;
/* 115 */     this.instantiatedMethodType = paramString7;
/* 116 */     this.capturedArgs = ((Object[])((Object[])Objects.requireNonNull(paramArrayOfObject)).clone());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCapturingClass()
/*     */   {
/* 124 */     return this.capturingClass.getName().replace('.', '/');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFunctionalInterfaceClass()
/*     */   {
/* 134 */     return this.functionalInterfaceClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFunctionalInterfaceMethodName()
/*     */   {
/* 143 */     return this.functionalInterfaceMethodName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFunctionalInterfaceMethodSignature()
/*     */   {
/* 153 */     return this.functionalInterfaceMethodSignature;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getImplClass()
/*     */   {
/* 163 */     return this.implClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getImplMethodName()
/*     */   {
/* 171 */     return this.implMethodName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getImplMethodSignature()
/*     */   {
/* 179 */     return this.implMethodSignature;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getImplMethodKind()
/*     */   {
/* 188 */     return this.implMethodKind;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getInstantiatedMethodType()
/*     */   {
/* 199 */     return this.instantiatedMethodType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCapturedArgCount()
/*     */   {
/* 207 */     return this.capturedArgs.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getCapturedArg(int paramInt)
/*     */   {
/* 216 */     return this.capturedArgs[paramInt];
/*     */   }
/*     */   
/*     */   private Object readResolve() throws ReflectiveOperationException {
/*     */     try {
/* 221 */       Method localMethod = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Method run() throws Exception {
/* 224 */           Method localMethod = SerializedLambda.this.capturingClass.getDeclaredMethod("$deserializeLambda$", new Class[] { SerializedLambda.class });
/* 225 */           localMethod.setAccessible(true);
/* 226 */           return localMethod;
/*     */         }
/*     */         
/* 229 */       });
/* 230 */       return localMethod.invoke(null, new Object[] { this });
/*     */     }
/*     */     catch (PrivilegedActionException localPrivilegedActionException) {
/* 233 */       Exception localException = localPrivilegedActionException.getException();
/* 234 */       if ((localException instanceof ReflectiveOperationException))
/* 235 */         throw ((ReflectiveOperationException)localException);
/* 236 */       if ((localException instanceof RuntimeException)) {
/* 237 */         throw ((RuntimeException)localException);
/*     */       }
/* 239 */       throw new RuntimeException("Exception in SerializedLambda.readResolve", localPrivilegedActionException);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 245 */     String str = MethodHandleInfo.referenceKindToString(this.implMethodKind);
/* 246 */     return String.format("SerializedLambda[%s=%s, %s=%s.%s:%s, %s=%s %s.%s:%s, %s=%s, %s=%d]", new Object[] { "capturingClass", this.capturingClass, "functionalInterfaceMethod", this.functionalInterfaceClass, this.functionalInterfaceMethodName, this.functionalInterfaceMethodSignature, "implementation", str, this.implClass, this.implMethodName, this.implMethodSignature, "instantiatedMethodType", this.instantiatedMethodType, "numCaptured", 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 256 */       Integer.valueOf(this.capturedArgs.length) });
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/SerializedLambda.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */