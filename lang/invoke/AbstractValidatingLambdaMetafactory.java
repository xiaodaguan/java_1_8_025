/*     */ package java.lang.invoke;
/*     */ 
/*     */ import sun.invoke.util.Wrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractValidatingLambdaMetafactory
/*     */ {
/*     */   final Class<?> targetClass;
/*     */   final MethodType invokedType;
/*     */   final Class<?> samBase;
/*     */   final String samMethodName;
/*     */   final MethodType samMethodType;
/*     */   final MethodHandle implMethod;
/*     */   final MethodHandleInfo implInfo;
/*     */   final int implKind;
/*     */   final boolean implIsInstanceMethod;
/*     */   final Class<?> implDefiningClass;
/*     */   final MethodType implMethodType;
/*     */   final MethodType instantiatedMethodType;
/*     */   final boolean isSerializable;
/*     */   final Class<?>[] markerInterfaces;
/*     */   final MethodType[] additionalBridges;
/*     */   
/*     */   AbstractValidatingLambdaMetafactory(MethodHandles.Lookup paramLookup, MethodType paramMethodType1, String paramString, MethodType paramMethodType2, MethodHandle paramMethodHandle, MethodType paramMethodType3, boolean paramBoolean, Class<?>[] paramArrayOfClass, MethodType[] paramArrayOfMethodType)
/*     */     throws LambdaConversionException
/*     */   {
/* 117 */     if ((paramLookup.lookupModes() & 0x2) == 0) {
/* 118 */       throw new LambdaConversionException(String.format("Invalid caller: %s", new Object[] {paramLookup
/*     */       
/* 120 */         .lookupClass().getName() }));
/*     */     }
/* 122 */     this.targetClass = paramLookup.lookupClass();
/* 123 */     this.invokedType = paramMethodType1;
/*     */     
/* 125 */     this.samBase = paramMethodType1.returnType();
/*     */     
/* 127 */     this.samMethodName = paramString;
/* 128 */     this.samMethodType = paramMethodType2;
/*     */     
/* 130 */     this.implMethod = paramMethodHandle;
/* 131 */     this.implInfo = paramLookup.revealDirect(paramMethodHandle);
/* 132 */     this.implKind = this.implInfo.getReferenceKind();
/* 133 */     this.implIsInstanceMethod = ((this.implKind == 5) || (this.implKind == 7) || (this.implKind == 9));
/*     */     
/*     */ 
/*     */ 
/* 137 */     this.implDefiningClass = this.implInfo.getDeclaringClass();
/* 138 */     this.implMethodType = this.implInfo.getMethodType();
/* 139 */     this.instantiatedMethodType = paramMethodType3;
/* 140 */     this.isSerializable = paramBoolean;
/* 141 */     this.markerInterfaces = paramArrayOfClass;
/* 142 */     this.additionalBridges = paramArrayOfMethodType;
/*     */     
/* 144 */     if (!this.samBase.isInterface()) {
/* 145 */       throw new LambdaConversionException(String.format("Functional interface %s is not an interface", new Object[] {this.samBase
/*     */       
/* 147 */         .getName() }));
/*     */     }
/*     */     
/* 150 */     for (Class<?> localClass : paramArrayOfClass) {
/* 151 */       if (!localClass.isInterface()) {
/* 152 */         throw new LambdaConversionException(String.format("Marker interface %s is not an interface", new Object[] {localClass
/*     */         
/* 154 */           .getName() }));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   abstract CallSite buildCallSite()
/*     */     throws LambdaConversionException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void validateMetafactoryArgs()
/*     */     throws LambdaConversionException
/*     */   {
/* 174 */     switch (this.implKind) {
/*     */     case 5: 
/*     */     case 6: 
/*     */     case 7: 
/*     */     case 8: 
/*     */     case 9: 
/*     */       break;
/*     */     default: 
/* 182 */       throw new LambdaConversionException(String.format("Unsupported MethodHandle kind: %s", new Object[] { this.implInfo }));
/*     */     }
/*     */     
/*     */     
/* 186 */     Class localClass1 = this.implMethodType.parameterCount();
/* 187 */     int i = this.implIsInstanceMethod ? 1 : 0;
/* 188 */     int j = this.invokedType.parameterCount();
/* 189 */     int k = this.samMethodType.parameterCount();
/* 190 */     int m = this.instantiatedMethodType.parameterCount();
/* 191 */     if (localClass1 + i != j + k)
/*     */     {
/* 193 */       throw new LambdaConversionException(String.format("Incorrect number of parameters for %s method %s; %d captured parameters, %d functional interface method parameters, %d implementation parameters", new Object[] { this.implIsInstanceMethod ? "instance" : "static", this.implInfo, 
/*     */       
/* 195 */         Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(localClass1) }));
/*     */     }
/* 197 */     if (m != k)
/*     */     {
/* 199 */       throw new LambdaConversionException(String.format("Incorrect number of parameters for %s method %s; %d instantiated parameters, %d functional interface method parameters", new Object[] { this.implIsInstanceMethod ? "instance" : "static", this.implInfo, 
/*     */       
/* 201 */         Integer.valueOf(m), Integer.valueOf(k) })); }
/*     */     Object localObject;
/* 203 */     for (localObject : this.additionalBridges) {
/* 204 */       if (((MethodType)localObject).parameterCount() != k)
/*     */       {
/* 206 */         throw new LambdaConversionException(String.format("Incorrect number of parameters for bridge signature %s; incompatible with %s", new Object[] { localObject, this.samMethodType }));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     int n;
/*     */     
/*     */ 
/* 214 */     if (this.implIsInstanceMethod)
/*     */     {
/*     */       Class localClass2;
/*     */       
/* 218 */       if (j == 0)
/*     */       {
/* 220 */         n = 0;
/* 221 */         ??? = 1;
/* 222 */         localClass2 = this.instantiatedMethodType.parameterType(0);
/*     */       }
/*     */       else {
/* 225 */         n = 1;
/* 226 */         ??? = 0;
/* 227 */         localClass2 = this.invokedType.parameterType(0);
/*     */       }
/*     */       
/*     */ 
/* 231 */       if (!this.implDefiningClass.isAssignableFrom(localClass2))
/*     */       {
/* 233 */         throw new LambdaConversionException(String.format("Invalid receiver type %s; not a subtype of implementation type %s", new Object[] { localClass2, this.implDefiningClass }));
/*     */       }
/*     */       
/*     */ 
/* 237 */       localObject = this.implMethod.type().parameterType(0);
/* 238 */       if ((localObject != this.implDefiningClass) && (!((Class)localObject).isAssignableFrom(localClass2)))
/*     */       {
/* 240 */         throw new LambdaConversionException(String.format("Invalid receiver type %s; not a subtype of implementation receiver type %s", new Object[] { localClass2, localObject }));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 245 */       n = 0;
/* 246 */       ??? = 0;
/*     */     }
/*     */     
/*     */ 
/* 250 */     Class localClass3 = j - n;
/* 251 */     for (Class localClass4 = 0; localClass4 < localClass3; localClass4++) {
/* 252 */       localClass5 = this.implMethodType.parameterType(localClass4);
/* 253 */       localClass6 = this.invokedType.parameterType(localClass4 + n);
/* 254 */       if (!localClass6.equals(localClass5))
/*     */       {
/* 256 */         throw new LambdaConversionException(String.format("Type mismatch in captured lambda parameter %d: expecting %s, found %s", new Object[] {
/* 257 */           Integer.valueOf(localClass4), localClass6, localClass5 }));
/*     */       }
/*     */     }
/*     */     
/* 261 */     localClass4 = ??? - localClass3;
/* 262 */     for (Class localClass5 = localClass3; localClass5 < localClass1; localClass5++) {
/* 263 */       localClass6 = this.implMethodType.parameterType(localClass5);
/* 264 */       localClass7 = this.instantiatedMethodType.parameterType(localClass5 + localClass4);
/* 265 */       if (!isAdaptableTo(localClass7, localClass6, true))
/*     */       {
/* 267 */         throw new LambdaConversionException(String.format("Type mismatch for lambda argument %d: %s is not convertible to %s", new Object[] {
/* 268 */           Integer.valueOf(localClass5), localClass7, localClass6 }));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 273 */     localClass5 = this.instantiatedMethodType.returnType();
/*     */     
/*     */ 
/*     */ 
/* 277 */     Class localClass6 = this.implKind == 8 ? this.implDefiningClass : this.implMethodType.returnType();
/* 278 */     Class localClass7 = this.samMethodType.returnType();
/* 279 */     if (!isAdaptableToAsReturn(localClass6, localClass5))
/*     */     {
/* 281 */       throw new LambdaConversionException(String.format("Type mismatch for lambda return: %s is not convertible to %s", new Object[] { localClass6, localClass5 }));
/*     */     }
/*     */     
/* 284 */     if (!isAdaptableToAsReturnStrict(localClass5, localClass7))
/*     */     {
/* 286 */       throw new LambdaConversionException(String.format("Type mismatch for lambda expected return: %s is not convertible to %s", new Object[] { localClass5, localClass7 }));
/*     */     }
/*     */     
/* 289 */     for (MethodType localMethodType : this.additionalBridges) {
/* 290 */       if (!isAdaptableToAsReturnStrict(localClass5, localMethodType.returnType()))
/*     */       {
/* 292 */         throw new LambdaConversionException(String.format("Type mismatch for lambda expected return: %s is not convertible to %s", new Object[] { localClass5, localMethodType
/* 293 */           .returnType() }));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isAdaptableTo(Class<?> paramClass1, Class<?> paramClass2, boolean paramBoolean)
/*     */   {
/* 306 */     if (paramClass1.equals(paramClass2))
/* 307 */       return true;
/*     */     Wrapper localWrapper1;
/* 309 */     Wrapper localWrapper2; if (paramClass1.isPrimitive()) {
/* 310 */       localWrapper1 = Wrapper.forPrimitiveType(paramClass1);
/* 311 */       if (paramClass2.isPrimitive())
/*     */       {
/* 313 */         localWrapper2 = Wrapper.forPrimitiveType(paramClass2);
/* 314 */         return localWrapper2.isConvertibleFrom(localWrapper1);
/*     */       }
/*     */       
/* 317 */       return paramClass2.isAssignableFrom(localWrapper1.wrapperType());
/*     */     }
/*     */     
/* 320 */     if (paramClass2.isPrimitive())
/*     */     {
/*     */ 
/* 323 */       if ((Wrapper.isWrapperType(paramClass1)) && ((localWrapper1 = Wrapper.forWrapperType(paramClass1)).primitiveType().isPrimitive()))
/*     */       {
/* 325 */         localWrapper2 = Wrapper.forPrimitiveType(paramClass2);
/* 326 */         return localWrapper2.isConvertibleFrom(localWrapper1);
/*     */       }
/*     */       
/* 329 */       return !paramBoolean;
/*     */     }
/*     */     
/*     */ 
/* 333 */     return (!paramBoolean) || (paramClass2.isAssignableFrom(paramClass1));
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
/* 345 */   private boolean isAdaptableToAsReturn(Class<?> paramClass1, Class<?> paramClass2) { return (paramClass2.equals(Void.TYPE)) || ((!paramClass1.equals(Void.TYPE)) && (isAdaptableTo(paramClass1, paramClass2, false))); }
/*     */   
/*     */   private boolean isAdaptableToAsReturnStrict(Class<?> paramClass1, Class<?> paramClass2) {
/* 348 */     if (paramClass1.equals(Void.TYPE)) return paramClass2.equals(Void.TYPE);
/* 349 */     return isAdaptableTo(paramClass1, paramClass2, true);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/AbstractValidatingLambdaMetafactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */