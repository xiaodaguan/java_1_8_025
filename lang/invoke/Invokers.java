/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import sun.invoke.empty.Empty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Invokers
/*     */ {
/*     */   private final MethodType targetType;
/*     */   private MethodHandle exactInvoker;
/*     */   private MethodHandle basicInvoker;
/*     */   private MethodHandle erasedInvoker;
/*     */   MethodHandle erasedInvokerWithDrops;
/*     */   private MethodHandle generalInvoker;
/*     */   private MethodHandle varargsInvoker;
/*     */   private final MethodHandle[] spreadInvokers;
/*     */   private MethodHandle uninitializedCallSite;
/*     */   private static MethodHandle THROW_UCS;
/*     */   private static final int MH_LINKER_ARG_APPENDED = 1;
/*     */   private static final LambdaForm.NamedFunction NF_checkExactType;
/*     */   private static final LambdaForm.NamedFunction NF_checkGenericType;
/*     */   private static final LambdaForm.NamedFunction NF_asType;
/*     */   private static final LambdaForm.NamedFunction NF_getCallSiteTarget;
/*     */   
/*     */   Invokers(MethodType paramMethodType)
/*     */   {
/*  71 */     this.targetType = paramMethodType;
/*  72 */     this.spreadInvokers = new MethodHandle[paramMethodType.parameterCount() + 1];
/*     */   }
/*     */   
/*     */   MethodHandle exactInvoker() {
/*  76 */     MethodHandle localMethodHandle = this.exactInvoker;
/*  77 */     if (localMethodHandle != null) return localMethodHandle;
/*  78 */     localMethodHandle = makeExactOrGeneralInvoker(true);
/*  79 */     this.exactInvoker = localMethodHandle;
/*  80 */     return localMethodHandle;
/*     */   }
/*     */   
/*     */   MethodHandle generalInvoker() {
/*  84 */     MethodHandle localMethodHandle = this.generalInvoker;
/*  85 */     if (localMethodHandle != null) return localMethodHandle;
/*  86 */     localMethodHandle = makeExactOrGeneralInvoker(false);
/*  87 */     this.generalInvoker = localMethodHandle;
/*  88 */     return localMethodHandle;
/*     */   }
/*     */   
/*     */   private MethodHandle makeExactOrGeneralInvoker(boolean paramBoolean) {
/*  92 */     MethodType localMethodType1 = this.targetType;
/*  93 */     MethodType localMethodType2 = localMethodType1.invokerType();
/*  94 */     int i = paramBoolean ? 10 : 12;
/*  95 */     LambdaForm localLambdaForm = invokeHandleForm(localMethodType1, false, i);
/*  96 */     MethodHandle localMethodHandle = BoundMethodHandle.bindSingle(localMethodType2, localLambdaForm, localMethodType1);
/*  97 */     String str = paramBoolean ? "invokeExact" : "invoke";
/*  98 */     localMethodHandle = localMethodHandle.withInternalMemberName(MemberName.makeMethodHandleInvoke(str, localMethodType1));
/*  99 */     assert (checkInvoker(localMethodHandle));
/* 100 */     maybeCompileToBytecode(localMethodHandle);
/* 101 */     return localMethodHandle;
/*     */   }
/*     */   
/*     */ 
/*     */   private void maybeCompileToBytecode(MethodHandle paramMethodHandle)
/*     */   {
/* 107 */     if ((this.targetType == this.targetType.erase()) && 
/* 108 */       (this.targetType.parameterCount() < 10)) {
/* 109 */       paramMethodHandle.form.compileToBytecode();
/*     */     }
/*     */   }
/*     */   
/*     */   MethodHandle basicInvoker() {
/* 114 */     Object localObject = this.basicInvoker;
/* 115 */     if (localObject != null) return (MethodHandle)localObject;
/* 116 */     MethodType localMethodType = this.targetType.basicType();
/* 117 */     if (localMethodType != this.targetType)
/*     */     {
/* 119 */       return this.basicInvoker = localMethodType.invokers().basicInvoker();
/*     */     }
/* 121 */     MemberName localMemberName = invokeBasicMethod(localMethodType);
/* 122 */     localObject = DirectMethodHandle.make(localMemberName);
/* 123 */     assert (checkInvoker((MethodHandle)localObject));
/* 124 */     this.basicInvoker = ((MethodHandle)localObject);
/* 125 */     return (MethodHandle)localObject;
/*     */   }
/*     */   
/*     */   static MemberName invokeBasicMethod(MethodType paramMethodType)
/*     */   {
/* 130 */     assert (paramMethodType == paramMethodType.basicType());
/*     */     try
/*     */     {
/* 133 */       return MethodHandles.Lookup.IMPL_LOOKUP.resolveOrFail((byte)5, MethodHandle.class, "invokeBasic", paramMethodType);
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 135 */       throw MethodHandleStatics.newInternalError("JVM cannot find invoker for " + paramMethodType, localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkInvoker(MethodHandle paramMethodHandle) {
/* 140 */     if ((!$assertionsDisabled) && (!this.targetType.invokerType().equals(paramMethodHandle.type())))
/* 141 */       throw new AssertionError(Arrays.asList(new Object[] { this.targetType, this.targetType.invokerType(), paramMethodHandle }));
/* 142 */     assert ((paramMethodHandle.internalMemberName() == null) || 
/* 143 */       (paramMethodHandle.internalMemberName().getMethodType().equals(this.targetType)));
/* 144 */     assert (!paramMethodHandle.isVarargsCollector());
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   MethodHandle erasedInvoker()
/*     */   {
/* 150 */     MethodHandle localMethodHandle1 = exactInvoker();
/* 151 */     MethodHandle localMethodHandle2 = this.erasedInvoker;
/* 152 */     if (localMethodHandle2 != null) return localMethodHandle2;
/* 153 */     MethodType localMethodType = this.targetType.erase();
/* 154 */     localMethodHandle2 = localMethodHandle1.asType(localMethodType.invokerType());
/* 155 */     this.erasedInvoker = localMethodHandle2;
/* 156 */     return localMethodHandle2;
/*     */   }
/*     */   
/*     */   MethodHandle spreadInvoker(int paramInt) {
/* 160 */     MethodHandle localMethodHandle1 = this.spreadInvokers[paramInt];
/* 161 */     if (localMethodHandle1 != null) return localMethodHandle1;
/* 162 */     int i = this.targetType.parameterCount() - paramInt;
/*     */     
/* 164 */     MethodType localMethodType = this.targetType.replaceParameterTypes(paramInt, this.targetType.parameterCount(), new Class[] { Object[].class });
/* 165 */     MethodHandle localMethodHandle2; if (this.targetType.parameterSlotCount() <= 253)
/*     */     {
/*     */ 
/* 168 */       localMethodHandle2 = generalInvoker();
/* 169 */       localMethodHandle1 = localMethodHandle2.asSpreader(Object[].class, i);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 174 */       localMethodHandle2 = MethodHandles.exactInvoker(localMethodType);
/*     */       
/*     */       try
/*     */       {
/* 178 */         localMethodHandle3 = MethodHandles.Lookup.IMPL_LOOKUP.findVirtual(MethodHandle.class, "asSpreader", 
/* 179 */           MethodType.methodType(MethodHandle.class, Class.class, new Class[] { Integer.TYPE }));
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 181 */         throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */       }
/* 183 */       MethodHandle localMethodHandle3 = MethodHandles.insertArguments(localMethodHandle3, 1, new Object[] { Object[].class, Integer.valueOf(i) });
/* 184 */       localMethodHandle1 = MethodHandles.filterArgument(localMethodHandle2, 0, localMethodHandle3);
/*     */     }
/* 186 */     assert (localMethodHandle1.type().equals(localMethodType.invokerType()));
/* 187 */     maybeCompileToBytecode(localMethodHandle1);
/* 188 */     this.spreadInvokers[paramInt] = localMethodHandle1;
/* 189 */     return localMethodHandle1;
/*     */   }
/*     */   
/*     */   MethodHandle varargsInvoker() {
/* 193 */     MethodHandle localMethodHandle = this.varargsInvoker;
/* 194 */     if (localMethodHandle != null) return localMethodHandle;
/* 195 */     localMethodHandle = spreadInvoker(0).asType(MethodType.genericMethodType(0, true).invokerType());
/* 196 */     this.varargsInvoker = localMethodHandle;
/* 197 */     return localMethodHandle;
/*     */   }
/*     */   
/*     */ 
/*     */   MethodHandle uninitializedCallSite()
/*     */   {
/* 203 */     MethodHandle localMethodHandle = this.uninitializedCallSite;
/* 204 */     if (localMethodHandle != null) return localMethodHandle;
/* 205 */     if (this.targetType.parameterCount() > 0) {
/* 206 */       MethodType localMethodType = this.targetType.dropParameterTypes(0, this.targetType.parameterCount());
/* 207 */       Invokers localInvokers = localMethodType.invokers();
/* 208 */       localMethodHandle = MethodHandles.dropArguments(localInvokers.uninitializedCallSite(), 0, this.targetType
/* 209 */         .parameterList());
/* 210 */       assert (localMethodHandle.type().equals(this.targetType));
/* 211 */       this.uninitializedCallSite = localMethodHandle;
/* 212 */       return localMethodHandle;
/*     */     }
/* 214 */     localMethodHandle = THROW_UCS;
/* 215 */     if (localMethodHandle == null) {
/*     */       try
/*     */       {
/* 218 */         THROW_UCS = localMethodHandle = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(CallSite.class, "uninitializedCallSite", 
/* 219 */           MethodType.methodType(Empty.class));
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 221 */         throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */       }
/*     */     }
/* 224 */     localMethodHandle = MethodHandles.explicitCastArguments(localMethodHandle, MethodType.methodType(this.targetType.returnType()));
/* 225 */     localMethodHandle = localMethodHandle.dropArguments(this.targetType, 0, this.targetType.parameterCount());
/* 226 */     assert (localMethodHandle.type().equals(this.targetType));
/* 227 */     this.uninitializedCallSite = localMethodHandle;
/* 228 */     return localMethodHandle;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 232 */     return "Invokers" + this.targetType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static MemberName methodHandleInvokeLinkerMethod(String paramString, MethodType paramMethodType, Object[] paramArrayOfObject)
/*     */   {
/* 239 */     Object localObject = paramString;int j = -1; switch (((String)localObject).hashCode()) {case 941760871:  if (((String)localObject).equals("invokeExact")) j = 0; break; case -1183693704:  if (((String)localObject).equals("invoke")) j = 1; break; } int i; switch (j) {
/* 240 */     case 0:  i = 9; break;
/* 241 */     case 1:  i = 11; break;
/* 242 */     default:  throw new InternalError("not invoker: " + paramString);
/*     */     }
/*     */     
/* 245 */     if (paramMethodType.parameterSlotCount() <= 253) {
/* 246 */       localObject = invokeHandleForm(paramMethodType, false, i);
/* 247 */       paramArrayOfObject[0] = paramMethodType;
/*     */     } else {
/* 249 */       localObject = invokeHandleForm(paramMethodType, true, i);
/*     */     }
/* 251 */     return ((LambdaForm)localObject).vmentry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static LambdaForm invokeHandleForm(MethodType paramMethodType, boolean paramBoolean, int paramInt)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 268 */     if (!paramBoolean) {
/* 269 */       paramMethodType = paramMethodType.basicType();
/* 270 */       i = 1;
/*     */     } else {
/* 272 */       i = 0; }
/*     */     int j;
/*     */     int k;
/*     */     String str;
/* 276 */     switch (paramInt) {
/* 277 */     case 9:  j = 1;k = 0;str = "invokeExact_MT"; break;
/* 278 */     case 10:  j = 0;k = 0;str = "exactInvoker"; break;
/* 279 */     case 11:  j = 1;k = 1;str = "invoke_MT"; break;
/* 280 */     case 12:  j = 0;k = 1;str = "invoker"; break;
/* 281 */     default:  throw new InternalError();
/*     */     }
/*     */     
/* 284 */     if (i != 0) {
/* 285 */       localLambdaForm = paramMethodType.form().cachedLambdaForm(paramInt);
/* 286 */       if (localLambdaForm != null) { return localLambdaForm;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 291 */     int m = 0 + (j != 0 ? 0 : 1);
/* 292 */     int n = m + 1;
/* 293 */     int i1 = n + paramMethodType.parameterCount();
/* 294 */     int i2 = i1 + ((j != 0) && (!paramBoolean) ? 1 : 0);
/* 295 */     int i3 = i1;
/* 296 */     int i4 = paramBoolean ? -1 : i3++;
/* 297 */     int i5 = i3++;
/* 298 */     int i6 = i3++;
/* 299 */     MethodType localMethodType = paramMethodType.invokerType();
/* 300 */     if (j != 0) {
/* 301 */       if (!paramBoolean)
/* 302 */         localMethodType = localMethodType.appendParameterTypes(new Class[] { MemberName.class });
/*     */     } else {
/* 304 */       localMethodType = localMethodType.invokerType();
/*     */     }
/* 306 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(i3 - i2, localMethodType);
/* 307 */     if ((!$assertionsDisabled) && (arrayOfName.length != i3))
/* 308 */       throw new AssertionError(Arrays.asList(new Serializable[] { paramMethodType, Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt), Integer.valueOf(i3), Integer.valueOf(arrayOfName.length) }));
/* 309 */     if (i4 >= i2) {
/* 310 */       assert (arrayOfName[i4] == null);
/* 311 */       localObject = BoundMethodHandle.getSpeciesData("L").getterFunction(0);
/* 312 */       arrayOfName[i4] = new LambdaForm.Name((LambdaForm.NamedFunction)localObject, new Object[] { arrayOfName[0] });
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 317 */     Object localObject = paramMethodType.basicType();
/* 318 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, m, i1, Object[].class);
/* 319 */     LambdaForm.Name localName = paramBoolean ? paramMethodType : arrayOfName[i4];
/* 320 */     if (k == 0) {
/* 321 */       arrayOfName[i5] = new LambdaForm.Name(NF_checkExactType, new Object[] { arrayOfName[m], localName });
/*     */     }
/*     */     else {
/* 324 */       arrayOfName[i5] = new LambdaForm.Name(NF_checkGenericType, new Object[] { arrayOfName[m], localName });
/*     */       
/* 326 */       arrayOfObject[0] = arrayOfName[i5];
/*     */     }
/* 328 */     arrayOfName[i6] = new LambdaForm.Name((MethodType)localObject, arrayOfObject);
/* 329 */     LambdaForm localLambdaForm = new LambdaForm(str, i2, arrayOfName);
/* 330 */     if (j != 0)
/* 331 */       localLambdaForm.compileToBytecode();
/* 332 */     if (i != 0)
/* 333 */       localLambdaForm = paramMethodType.form().setCachedLambdaForm(paramInt, localLambdaForm);
/* 334 */     return localLambdaForm;
/*     */   }
/*     */   
/*     */ 
/*     */   static WrongMethodTypeException newWrongMethodTypeException(MethodType paramMethodType1, MethodType paramMethodType2)
/*     */   {
/* 340 */     return new WrongMethodTypeException("expected " + paramMethodType2 + " but found " + paramMethodType1);
/*     */   }
/*     */   
/*     */ 
/*     */   @ForceInline
/*     */   static void checkExactType(Object paramObject1, Object paramObject2)
/*     */   {
/* 347 */     MethodHandle localMethodHandle = (MethodHandle)paramObject1;
/* 348 */     MethodType localMethodType1 = (MethodType)paramObject2;
/* 349 */     MethodType localMethodType2 = localMethodHandle.type();
/* 350 */     if (localMethodType2 != localMethodType1) {
/* 351 */       throw newWrongMethodTypeException(localMethodType1, localMethodType2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @ForceInline
/*     */   static Object checkGenericType(Object paramObject1, Object paramObject2)
/*     */   {
/* 361 */     MethodHandle localMethodHandle1 = (MethodHandle)paramObject1;
/* 362 */     MethodType localMethodType = (MethodType)paramObject2;
/* 363 */     if (localMethodHandle1.type() == localMethodType) return localMethodHandle1;
/* 364 */     MethodHandle localMethodHandle2 = localMethodHandle1.asTypeCache;
/* 365 */     if ((localMethodHandle2 != null) && (localMethodHandle2.type() == localMethodType)) return localMethodHandle2;
/* 366 */     return localMethodHandle1.asType(localMethodType);
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
/*     */   static MemberName linkToCallSiteMethod(MethodType paramMethodType)
/*     */   {
/* 387 */     LambdaForm localLambdaForm = callSiteForm(paramMethodType, false);
/* 388 */     return localLambdaForm.vmentry;
/*     */   }
/*     */   
/*     */   static MemberName linkToTargetMethod(MethodType paramMethodType) {
/* 392 */     LambdaForm localLambdaForm = callSiteForm(paramMethodType, true);
/* 393 */     return localLambdaForm.vmentry;
/*     */   }
/*     */   
/*     */   private static LambdaForm callSiteForm(MethodType paramMethodType, boolean paramBoolean)
/*     */   {
/* 398 */     paramMethodType = paramMethodType.basicType();
/* 399 */     int i = paramBoolean ? 14 : 13;
/* 400 */     LambdaForm localLambdaForm = paramMethodType.form().cachedLambdaForm(i);
/* 401 */     if (localLambdaForm != null) { return localLambdaForm;
/*     */     }
/*     */     
/*     */ 
/* 405 */     int j = 0 + paramMethodType.parameterCount();
/* 406 */     int k = j + 1;
/* 407 */     int m = j;
/* 408 */     int n = m++;
/* 409 */     int i1 = paramBoolean ? -1 : n;
/* 410 */     int i2 = paramBoolean ? n : m++;
/* 411 */     int i3 = m++;
/* 412 */     MethodType localMethodType = paramMethodType.appendParameterTypes(new Class[] { paramBoolean ? MethodHandle.class : CallSite.class });
/* 413 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(m - k, localMethodType);
/* 414 */     assert (arrayOfName.length == m);
/* 415 */     assert (arrayOfName[n] != null);
/* 416 */     if (!paramBoolean) {
/* 417 */       arrayOfName[i2] = new LambdaForm.Name(NF_getCallSiteTarget, new Object[] { arrayOfName[i1] });
/*     */     }
/*     */     
/* 420 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 0, j + 1, Object[].class);
/*     */     
/* 422 */     System.arraycopy(arrayOfObject, 0, arrayOfObject, 1, arrayOfObject.length - 1);
/* 423 */     arrayOfObject[0] = arrayOfName[i2];
/* 424 */     arrayOfName[i3] = new LambdaForm.Name(paramMethodType, arrayOfObject);
/* 425 */     localLambdaForm = new LambdaForm(paramBoolean ? "linkToTargetMethod" : "linkToCallSite", k, arrayOfName);
/* 426 */     localLambdaForm.compileToBytecode();
/* 427 */     localLambdaForm = paramMethodType.form().setCachedLambdaForm(i, localLambdaForm);
/* 428 */     return localLambdaForm;
/*     */   }
/*     */   
/*     */ 
/*     */   @ForceInline
/*     */   static Object getCallSiteTarget(Object paramObject)
/*     */   {
/* 435 */     return ((CallSite)paramObject).getTarget();
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 200 */     THROW_UCS = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 446 */       NF_checkExactType = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("checkExactType", new Class[] { Object.class, Object.class }));
/*     */       
/* 448 */       NF_checkGenericType = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("checkGenericType", new Class[] { Object.class, Object.class }));
/*     */       
/* 450 */       NF_asType = new LambdaForm.NamedFunction(MethodHandle.class.getDeclaredMethod("asType", new Class[] { MethodType.class }));
/*     */       
/* 452 */       NF_getCallSiteTarget = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("getCallSiteTarget", new Class[] { Object.class }));
/* 453 */       NF_checkExactType.resolve();
/* 454 */       NF_checkGenericType.resolve();
/* 455 */       NF_getCallSiteTarget.resolve();
/*     */     }
/*     */     catch (ReflectiveOperationException localReflectiveOperationException) {
/* 458 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/Invokers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */