/*     */ package java.lang.invoke;
/*     */ 
/*     */ import sun.invoke.empty.Empty;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CallSite
/*     */ {
/*     */   MethodHandle target;
/*     */   private static final MethodHandle GET_TARGET;
/*     */   private static final long TARGET_OFFSET;
/*     */   
/*     */   CallSite(MethodType paramMethodType)
/*     */   {
/* 105 */     this.target = paramMethodType.invokers().uninitializedCallSite();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   CallSite(MethodHandle paramMethodHandle)
/*     */   {
/* 115 */     paramMethodHandle.type();
/* 116 */     this.target = paramMethodHandle;
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
/*     */   CallSite(MethodType paramMethodType, MethodHandle paramMethodHandle)
/*     */     throws Throwable
/*     */   {
/* 131 */     this(paramMethodType);
/* 132 */     ConstantCallSite localConstantCallSite = (ConstantCallSite)this;
/* 133 */     MethodHandle localMethodHandle = (MethodHandle)paramMethodHandle.invokeWithArguments(new Object[] { localConstantCallSite });
/* 134 */     checkTargetChange(this.target, localMethodHandle);
/* 135 */     this.target = localMethodHandle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MethodType type()
/*     */   {
/* 147 */     return this.target.type();
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
/*     */   void checkTargetChange(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2)
/*     */   {
/* 187 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 188 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 189 */     if (!localMethodType2.equals(localMethodType1))
/* 190 */       throw wrongTargetType(paramMethodHandle2, localMethodType1);
/*     */   }
/*     */   
/*     */   private static WrongMethodTypeException wrongTargetType(MethodHandle paramMethodHandle, MethodType paramMethodType) {
/* 194 */     return new WrongMethodTypeException(String.valueOf(paramMethodHandle) + " should be of type " + paramMethodType);
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
/*     */   MethodHandle makeDynamicInvoker()
/*     */   {
/* 214 */     MethodHandle localMethodHandle1 = GET_TARGET.bindReceiver(this);
/* 215 */     MethodHandle localMethodHandle2 = MethodHandles.exactInvoker(type());
/* 216 */     return MethodHandles.foldArguments(localMethodHandle2, localMethodHandle1);
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
/*     */   static Empty uninitializedCallSite()
/*     */   {
/* 232 */     throw new IllegalStateException("uninitialized call site");
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*     */     
/*     */     try
/*     */     {
/* 223 */       GET_TARGET = MethodHandles.Lookup.IMPL_LOOKUP.findVirtual(CallSite.class, "getTarget", MethodType.methodType(MethodHandle.class));
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 225 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 239 */       TARGET_OFFSET = MethodHandleStatics.UNSAFE.objectFieldOffset(CallSite.class.getDeclaredField("target"));
/* 240 */     } catch (Exception localException) { throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */   void setTargetNormal(MethodHandle paramMethodHandle) {
/* 245 */     MethodHandleNatives.setCallSiteTargetNormal(this, paramMethodHandle);
/*     */   }
/*     */   
/*     */   MethodHandle getTargetVolatile() {
/* 249 */     return (MethodHandle)MethodHandleStatics.UNSAFE.getObjectVolatile(this, TARGET_OFFSET);
/*     */   }
/*     */   
/*     */   void setTargetVolatile(MethodHandle paramMethodHandle) {
/* 253 */     MethodHandleNatives.setCallSiteTargetVolatile(this, paramMethodHandle);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static CallSite makeSite(MethodHandle paramMethodHandle, String paramString, MethodType paramMethodType, Object paramObject, Class<?> paramClass)
/*     */   {
/* 264 */     MethodHandles.Lookup localLookup = MethodHandles.Lookup.IMPL_LOOKUP.in(paramClass);
/*     */     CallSite localCallSite;
/*     */     try
/*     */     {
/* 268 */       paramObject = maybeReBox(paramObject);
/* 269 */       Object localObject1; if (paramObject == null) {
/* 270 */         localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType);
/* 271 */       } else if (!paramObject.getClass().isArray()) {
/* 272 */         localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, paramObject);
/*     */       } else {
/* 274 */         localObject2 = (Object[])paramObject;
/* 275 */         maybeReBoxElements((Object[])localObject2);
/* 276 */         switch (localObject2.length) {
/*     */         case 0: 
/* 278 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType);
/* 279 */           break;
/*     */         case 1: 
/* 281 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, localObject2[0]);
/*     */           
/* 283 */           break;
/*     */         case 2: 
/* 285 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, localObject2[0], localObject2[1]);
/*     */           
/* 287 */           break;
/*     */         case 3: 
/* 289 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, localObject2[0], localObject2[1], localObject2[2]);
/*     */           
/* 291 */           break;
/*     */         case 4: 
/* 293 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, localObject2[0], localObject2[1], localObject2[2], localObject2[3]);
/*     */           
/* 295 */           break;
/*     */         case 5: 
/* 297 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, localObject2[0], localObject2[1], localObject2[2], localObject2[3], localObject2[4]);
/*     */           
/* 299 */           break;
/*     */         case 6: 
/* 301 */           localObject1 = paramMethodHandle.invoke(localLookup, paramString, paramMethodType, localObject2[0], localObject2[1], localObject2[2], localObject2[3], localObject2[4], localObject2[5]);
/*     */           
/* 303 */           break;
/*     */         
/*     */         default: 
/* 306 */           if (3 + localObject2.length > 254)
/* 307 */             throw new BootstrapMethodError("too many bootstrap method arguments");
/* 308 */           MethodType localMethodType1 = paramMethodHandle.type();
/* 309 */           MethodType localMethodType2 = MethodType.genericMethodType(3 + localObject2.length);
/* 310 */           MethodHandle localMethodHandle1 = paramMethodHandle.asType(localMethodType2);
/* 311 */           MethodHandle localMethodHandle2 = localMethodType2.invokers().spreadInvoker(3);
/* 312 */           localObject1 = localMethodHandle2.invokeExact(localMethodHandle1, localLookup, paramString, paramMethodType, (Object[])localObject2);
/*     */         }
/*     */         
/*     */       }
/* 316 */       if ((localObject1 instanceof CallSite)) {
/* 317 */         localCallSite = (CallSite)localObject1;
/*     */       } else {
/* 319 */         throw new ClassCastException("bootstrap method failed to produce a CallSite");
/*     */       }
/* 321 */       if (!localCallSite.getTarget().type().equals(paramMethodType))
/* 322 */         throw new WrongMethodTypeException("wrong type: " + localCallSite.getTarget());
/*     */     } catch (Throwable localThrowable) {
/*     */       Object localObject2;
/* 325 */       if ((localThrowable instanceof BootstrapMethodError)) {
/* 326 */         localObject2 = (BootstrapMethodError)localThrowable;
/*     */       } else
/* 328 */         localObject2 = new BootstrapMethodError("call site initialization exception", localThrowable);
/* 329 */       throw ((Throwable)localObject2);
/*     */     }
/* 331 */     return localCallSite;
/*     */   }
/*     */   
/*     */   private static Object maybeReBox(Object paramObject) {
/* 335 */     if ((paramObject instanceof Integer)) {
/* 336 */       int i = ((Integer)paramObject).intValue();
/* 337 */       if (i == (byte)i)
/* 338 */         paramObject = Integer.valueOf(i);
/*     */     }
/* 340 */     return paramObject;
/*     */   }
/*     */   
/* 343 */   private static void maybeReBoxElements(Object[] paramArrayOfObject) { for (int i = 0; i < paramArrayOfObject.length; i++) {
/* 344 */       paramArrayOfObject[i] = maybeReBox(paramArrayOfObject[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract MethodHandle getTarget();
/*     */   
/*     */   public abstract void setTarget(MethodHandle paramMethodHandle);
/*     */   
/*     */   public abstract MethodHandle dynamicInvoker();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/CallSite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */