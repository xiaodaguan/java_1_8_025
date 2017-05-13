/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import sun.invoke.empty.Empty;
/*     */ import sun.invoke.util.ValueConversions;
/*     */ import sun.invoke.util.VerifyType;
/*     */ import sun.invoke.util.Wrapper;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MethodHandleImpl
/*     */ {
/*     */   static void initStatics()
/*     */   {
/*  51 */     MemberName.Factory.INSTANCE.getClass();
/*     */   }
/*     */   
/*     */   static MethodHandle makeArrayElementAccessor(Class<?> paramClass, boolean paramBoolean) {
/*  55 */     if (!paramClass.isArray())
/*  56 */       throw MethodHandleStatics.newIllegalArgumentException("not an array: " + paramClass);
/*  57 */     MethodHandle localMethodHandle = ArrayAccessor.getAccessor(paramClass, paramBoolean);
/*  58 */     MethodType localMethodType1 = localMethodHandle.type().erase();
/*  59 */     MethodType localMethodType2 = localMethodType1.invokerType();
/*  60 */     LambdaForm.Name[] arrayOfName1 = LambdaForm.arguments(1, localMethodType2);
/*  61 */     LambdaForm.Name[] arrayOfName2 = (LambdaForm.Name[])Arrays.copyOfRange(arrayOfName1, 1, 1 + localMethodType1.parameterCount());
/*  62 */     arrayOfName1[(arrayOfName1.length - 1)] = new LambdaForm.Name(localMethodHandle.asType(localMethodType1), (Object[])arrayOfName2);
/*  63 */     LambdaForm localLambdaForm = new LambdaForm("getElement", localMethodType2.parameterCount(), arrayOfName1);
/*  64 */     Object localObject = SimpleMethodHandle.make(localMethodType1, localLambdaForm);
/*  65 */     if (ArrayAccessor.needCast(paramClass)) {
/*  66 */       localObject = ((MethodHandle)localObject).bindTo(paramClass);
/*     */     }
/*  68 */     localObject = ((MethodHandle)localObject).asType(ArrayAccessor.correctType(paramClass, paramBoolean));
/*  69 */     return (MethodHandle)localObject;
/*     */   }
/*     */   
/*     */   static final class ArrayAccessor
/*     */   {
/*  74 */     static final HashMap<Class<?>, MethodHandle> GETTER_CACHE = new HashMap();
/*  75 */     static final HashMap<Class<?>, MethodHandle> SETTER_CACHE = new HashMap();
/*     */     
/*  77 */     static int getElementI(int[] paramArrayOfInt, int paramInt) { return paramArrayOfInt[paramInt]; }
/*  78 */     static long getElementJ(long[] paramArrayOfLong, int paramInt) { return paramArrayOfLong[paramInt]; }
/*  79 */     static float getElementF(float[] paramArrayOfFloat, int paramInt) { return paramArrayOfFloat[paramInt]; }
/*  80 */     static double getElementD(double[] paramArrayOfDouble, int paramInt) { return paramArrayOfDouble[paramInt]; }
/*  81 */     static boolean getElementZ(boolean[] paramArrayOfBoolean, int paramInt) { return paramArrayOfBoolean[paramInt]; }
/*  82 */     static byte getElementB(byte[] paramArrayOfByte, int paramInt) { return paramArrayOfByte[paramInt]; }
/*  83 */     static short getElementS(short[] paramArrayOfShort, int paramInt) { return paramArrayOfShort[paramInt]; }
/*  84 */     static char getElementC(char[] paramArrayOfChar, int paramInt) { return paramArrayOfChar[paramInt]; }
/*  85 */     static Object getElementL(Object[] paramArrayOfObject, int paramInt) { return paramArrayOfObject[paramInt]; }
/*     */     
/*  87 */     static void setElementI(int[] paramArrayOfInt, int paramInt1, int paramInt2) { paramArrayOfInt[paramInt1] = paramInt2; }
/*  88 */     static void setElementJ(long[] paramArrayOfLong, int paramInt, long paramLong) { paramArrayOfLong[paramInt] = paramLong; }
/*  89 */     static void setElementF(float[] paramArrayOfFloat, int paramInt, float paramFloat) { paramArrayOfFloat[paramInt] = paramFloat; }
/*  90 */     static void setElementD(double[] paramArrayOfDouble, int paramInt, double paramDouble) { paramArrayOfDouble[paramInt] = paramDouble; }
/*  91 */     static void setElementZ(boolean[] paramArrayOfBoolean, int paramInt, boolean paramBoolean) { paramArrayOfBoolean[paramInt] = paramBoolean; }
/*  92 */     static void setElementB(byte[] paramArrayOfByte, int paramInt, byte paramByte) { paramArrayOfByte[paramInt] = paramByte; }
/*  93 */     static void setElementS(short[] paramArrayOfShort, int paramInt, short paramShort) { paramArrayOfShort[paramInt] = paramShort; }
/*  94 */     static void setElementC(char[] paramArrayOfChar, int paramInt, char paramChar) { paramArrayOfChar[paramInt] = paramChar; }
/*  95 */     static void setElementL(Object[] paramArrayOfObject, int paramInt, Object paramObject) { paramArrayOfObject[paramInt] = paramObject; }
/*     */     
/*  97 */     static Object getElementL(Class<?> paramClass, Object[] paramArrayOfObject, int paramInt) { paramClass.cast(paramArrayOfObject);return paramArrayOfObject[paramInt]; }
/*  98 */     static void setElementL(Class<?> paramClass, Object[] paramArrayOfObject, int paramInt, Object paramObject) { paramClass.cast(paramArrayOfObject);paramArrayOfObject[paramInt] = paramObject;
/*     */     }
/*     */     
/* 101 */     static Object getElementL(Object paramObject, int paramInt) { return getElementL((Object[])paramObject, paramInt); }
/* 102 */     static void setElementL(Object paramObject1, int paramInt, Object paramObject2) { setElementL((Object[])paramObject1, paramInt, paramObject2); }
/* 103 */     static Object getElementL(Object paramObject1, Object paramObject2, int paramInt) { return getElementL((Class)paramObject1, (Object[])paramObject2, paramInt); }
/* 104 */     static void setElementL(Object paramObject1, Object paramObject2, int paramInt, Object paramObject3) { setElementL((Class)paramObject1, (Object[])paramObject2, paramInt, paramObject3); }
/*     */     
/*     */     static boolean needCast(Class<?> paramClass) {
/* 107 */       Class localClass = paramClass.getComponentType();
/* 108 */       return (!localClass.isPrimitive()) && (localClass != Object.class);
/*     */     }
/*     */     
/* 111 */     static String name(Class<?> paramClass, boolean paramBoolean) { Class localClass = paramClass.getComponentType();
/* 112 */       if (localClass == null) throw new IllegalArgumentException();
/* 113 */       return (!paramBoolean ? "getElement" : "setElement") + Wrapper.basicTypeChar(localClass);
/*     */     }
/*     */     
/*     */     static MethodType type(Class<?> paramClass, boolean paramBoolean) {
/* 117 */       Class localClass1 = paramClass.getComponentType();
/* 118 */       Object localObject = paramClass;
/* 119 */       if (!localClass1.isPrimitive()) {
/* 120 */         localObject = Object[].class;
/*     */       }
/*     */       
/*     */ 
/* 124 */       if (!needCast(paramClass))
/*     */       {
/*     */ 
/* 127 */         return !paramBoolean ? MethodType.methodType(localClass1, (Class)localObject, new Class[] { Integer.TYPE }) : MethodType.methodType(Void.TYPE, (Class)localObject, new Class[] { Integer.TYPE, localClass1 });
/*     */       }
/* 129 */       Class localClass2 = Class.class;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 134 */       return !paramBoolean ? MethodType.methodType(Object.class, localClass2, new Class[] { localObject, Integer.TYPE }) : MethodType.methodType(Void.TYPE, localClass2, new Class[] { localObject, Integer.TYPE, Object.class });
/*     */     }
/*     */     
/*     */     static MethodType correctType(Class<?> paramClass, boolean paramBoolean) {
/* 138 */       Class localClass = paramClass.getComponentType();
/*     */       
/*     */ 
/* 141 */       return !paramBoolean ? MethodType.methodType(localClass, paramClass, new Class[] { Integer.TYPE }) : MethodType.methodType(Void.TYPE, paramClass, new Class[] { Integer.TYPE, localClass });
/*     */     }
/*     */     
/* 144 */     static MethodHandle getAccessor(Class<?> paramClass, boolean paramBoolean) { String str = name(paramClass, paramBoolean);
/* 145 */       MethodType localMethodType = type(paramClass, paramBoolean);
/*     */       try {
/* 147 */         return MethodHandles.Lookup.IMPL_LOOKUP.findStatic(ArrayAccessor.class, str, localMethodType);
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 149 */         throw MethodHandleStatics.uncaughtException(localReflectiveOperationException);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static final boolean USE_WEAKLY_TYPED_ARRAY_ACCESSORS = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static MethodHandle makePairwiseConvert(MethodHandle paramMethodHandle, MethodType paramMethodType, int paramInt)
/*     */   {
/* 168 */     assert ((paramInt >= 0) && (paramInt <= 2));
/* 169 */     MethodType localMethodType1 = paramMethodHandle.type();
/* 170 */     assert (localMethodType1.parameterCount() == paramMethodHandle.type().parameterCount());
/* 171 */     if (paramMethodType == localMethodType1) {
/* 172 */       return paramMethodHandle;
/*     */     }
/*     */     
/*     */ 
/* 176 */     int i = paramMethodType.parameterCount();
/* 177 */     int j = 0;
/* 178 */     boolean[] arrayOfBoolean = new boolean[1 + i];
/* 179 */     for (int k = 0; k <= i; k++) {
/* 180 */       Class localClass1 = k == i ? localMethodType1.returnType() : paramMethodType.parameterType(k);
/* 181 */       Class localClass2 = k == i ? paramMethodType.returnType() : localMethodType1.parameterType(k);
/* 182 */       if ((!VerifyType.isNullConversion(localClass1, localClass2)) || ((paramInt <= 1) && 
/* 183 */         (localClass2.isInterface()) && (!localClass2.isAssignableFrom(localClass1)))) {
/* 184 */         arrayOfBoolean[k] = true;
/* 185 */         j++;
/*     */       }
/*     */     }
/* 188 */     k = arrayOfBoolean[i];
/*     */     
/*     */ 
/*     */ 
/* 192 */     int m = 1 + i;
/* 193 */     int n = m + j + 1;
/* 194 */     int i1 = k == 0 ? -1 : n - 1;
/* 195 */     int i2 = (k == 0 ? n : i1) - 1;
/*     */     
/*     */ 
/* 198 */     MethodType localMethodType2 = paramMethodType.basicType().invokerType();
/* 199 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(n - m, localMethodType2);
/*     */     
/*     */ 
/*     */ 
/* 203 */     Object[] arrayOfObject = new Object[0 + i];
/*     */     
/* 205 */     int i3 = m;
/* 206 */     Class localClass3; Object localObject2; Object localObject3; Object localObject4; Object localObject5; for (int i4 = 0; i4 < i; i4++) {
/* 207 */       localClass3 = paramMethodType.parameterType(i4);
/* 208 */       localObject2 = localMethodType1.parameterType(i4);
/*     */       
/* 210 */       if (arrayOfBoolean[i4] == 0)
/*     */       {
/* 212 */         arrayOfObject[(0 + i4)] = arrayOfName[(1 + i4)];
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 217 */         localObject3 = null;
/* 218 */         if (localClass3.isPrimitive()) {
/* 219 */           if (((Class)localObject2).isPrimitive()) {
/* 220 */             localObject3 = ValueConversions.convertPrimitive(localClass3, (Class)localObject2);
/*     */           } else {
/* 222 */             localObject4 = Wrapper.forPrimitiveType(localClass3);
/* 223 */             localObject5 = ValueConversions.box((Wrapper)localObject4);
/* 224 */             if (localObject2 == ((Wrapper)localObject4).wrapperType()) {
/* 225 */               localObject3 = localObject5;
/*     */             } else {
/* 227 */               localObject3 = ((MethodHandle)localObject5).asType(MethodType.methodType((Class)localObject2, localClass3));
/*     */             }
/*     */           }
/* 230 */         } else if (((Class)localObject2).isPrimitive())
/*     */         {
/* 232 */           localObject4 = Wrapper.forPrimitiveType((Class)localObject2);
/* 233 */           if ((paramInt == 0) || (VerifyType.isNullConversion(localClass3, ((Wrapper)localObject4).wrapperType()))) {
/* 234 */             localObject3 = ValueConversions.unbox((Class)localObject2);
/* 235 */           } else if ((localClass3 == Object.class) || (!Wrapper.isWrapperType(localClass3)))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 241 */             localObject5 = paramInt == 1 ? ValueConversions.unbox((Class)localObject2) : ValueConversions.unboxCast((Class)localObject2);
/* 242 */             localObject3 = localObject5;
/*     */           }
/*     */           else
/*     */           {
/* 246 */             localObject5 = Wrapper.forWrapperType(localClass3).primitiveType();
/* 247 */             MethodHandle localMethodHandle = ValueConversions.unbox((Class)localObject5);
/*     */             
/* 249 */             localObject3 = localMethodHandle.asType(MethodType.methodType((Class)localObject2, localClass3));
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 256 */           localObject3 = ValueConversions.cast((Class)localObject2, Lazy.MH_castReference);
/*     */         }
/*     */         
/* 259 */         localObject4 = new LambdaForm.Name((MethodHandle)localObject3, new Object[] { arrayOfName[(1 + i4)] });
/* 260 */         assert (arrayOfName[i3] == null);
/* 261 */         arrayOfName[(i3++)] = localObject4;
/* 262 */         assert (arrayOfObject[(0 + i4)] == null);
/* 263 */         arrayOfObject[(0 + i4)] = localObject4;
/*     */       }
/*     */     }
/*     */     
/* 267 */     assert (i3 == i2);
/* 268 */     arrayOfName[i2] = new LambdaForm.Name(paramMethodHandle, arrayOfObject);
/*     */     
/* 270 */     if (i1 < 0) {
/* 271 */       if ((!$assertionsDisabled) && (i2 != arrayOfName.length - 1)) throw new AssertionError();
/*     */     } else {
/* 273 */       localObject1 = paramMethodType.returnType();
/* 274 */       localClass3 = localMethodType1.returnType();
/*     */       
/* 276 */       localObject3 = new Object[] { arrayOfName[i2] };
/* 277 */       if (localClass3 == Void.TYPE)
/*     */       {
/* 279 */         localObject4 = Wrapper.forBasicType((Class)localObject1).zero();
/* 280 */         localObject2 = MethodHandles.constant((Class)localObject1, localObject4);
/* 281 */         localObject3 = new Object[0];
/*     */       } else {
/* 283 */         localObject4 = MethodHandles.identity((Class)localObject1);
/* 284 */         localObject5 = ((MethodHandle)localObject4).type().changeParameterType(0, localClass3);
/* 285 */         localObject2 = makePairwiseConvert((MethodHandle)localObject4, (MethodType)localObject5, paramInt);
/*     */       }
/* 287 */       assert (arrayOfName[i1] == null);
/* 288 */       arrayOfName[i1] = new LambdaForm.Name((MethodHandle)localObject2, (Object[])localObject3);
/* 289 */       assert (i1 == arrayOfName.length - 1);
/*     */     }
/*     */     
/* 292 */     Object localObject1 = new LambdaForm("convert", localMethodType2.parameterCount(), arrayOfName);
/* 293 */     return SimpleMethodHandle.make(paramMethodType, (LambdaForm)localObject1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @ForceInline
/*     */   static <T, U> T castReference(Class<? extends T> paramClass, U paramU)
/*     */   {
/* 306 */     if ((paramU != null) && (!paramClass.isInstance(paramU)))
/* 307 */       throw newClassCastException(paramClass, paramU);
/* 308 */     return paramU;
/*     */   }
/*     */   
/*     */   private static ClassCastException newClassCastException(Class<?> paramClass, Object paramObject) {
/* 312 */     return new ClassCastException("Cannot cast " + paramObject.getClass().getName() + " to " + paramClass.getName());
/*     */   }
/*     */   
/*     */   static MethodHandle makeReferenceIdentity(Class<?> paramClass) {
/* 316 */     MethodType localMethodType = MethodType.genericMethodType(1).invokerType();
/* 317 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(1, localMethodType);
/* 318 */     arrayOfName[(arrayOfName.length - 1)] = new LambdaForm.Name(ValueConversions.identity(), new Object[] { arrayOfName[1] });
/* 319 */     LambdaForm localLambdaForm = new LambdaForm("identity", localMethodType.parameterCount(), arrayOfName);
/* 320 */     return SimpleMethodHandle.make(MethodType.methodType(paramClass, paramClass), localLambdaForm);
/*     */   }
/*     */   
/*     */   static MethodHandle makeVarargsCollector(MethodHandle paramMethodHandle, Class<?> paramClass) {
/* 324 */     MethodType localMethodType = paramMethodHandle.type();
/* 325 */     int i = localMethodType.parameterCount() - 1;
/* 326 */     if (localMethodType.parameterType(i) != paramClass)
/* 327 */       paramMethodHandle = paramMethodHandle.asType(localMethodType.changeParameterType(i, paramClass));
/* 328 */     paramMethodHandle = paramMethodHandle.asFixedArity();
/* 329 */     return new AsVarargsCollector(paramMethodHandle, paramMethodHandle.type(), paramClass);
/*     */   }
/*     */   
/*     */   static class AsVarargsCollector extends MethodHandle {
/*     */     private final MethodHandle target;
/*     */     private final Class<?> arrayType;
/*     */     private MethodHandle asCollectorCache;
/*     */     
/*     */     AsVarargsCollector(MethodHandle paramMethodHandle, MethodType paramMethodType, Class<?> paramClass) {
/* 338 */       super(reinvokerForm(paramMethodHandle));
/* 339 */       this.target = paramMethodHandle;
/* 340 */       this.arrayType = paramClass;
/* 341 */       this.asCollectorCache = paramMethodHandle.asCollector(paramClass, 0);
/*     */     }
/*     */     
/* 344 */     MethodHandle reinvokerTarget() { return this.target; }
/*     */     
/*     */     public boolean isVarargsCollector()
/*     */     {
/* 348 */       return true;
/*     */     }
/*     */     
/*     */     public MethodHandle asFixedArity()
/*     */     {
/* 353 */       return this.target;
/*     */     }
/*     */     
/*     */     public MethodHandle asTypeUncached(MethodType paramMethodType)
/*     */     {
/* 358 */       MethodType localMethodType = type();
/* 359 */       int i = localMethodType.parameterCount() - 1;
/* 360 */       int j = paramMethodType.parameterCount();
/* 361 */       if ((j == i + 1) && 
/* 362 */         (localMethodType.parameterType(i).isAssignableFrom(paramMethodType.parameterType(i))))
/*     */       {
/* 364 */         return this.asTypeCache = asFixedArity().asType(paramMethodType);
/*     */       }
/*     */       
/* 367 */       MethodHandle localMethodHandle1 = this.asCollectorCache;
/* 368 */       if ((localMethodHandle1 != null) && (localMethodHandle1.type().parameterCount() == j)) {
/* 369 */         return this.asTypeCache = localMethodHandle1.asType(paramMethodType);
/*     */       }
/* 371 */       int k = j - i;
/*     */       MethodHandle localMethodHandle2;
/*     */       try {
/* 374 */         localMethodHandle2 = asFixedArity().asCollector(this.arrayType, k);
/* 375 */         if ((!$assertionsDisabled) && (localMethodHandle2.type().parameterCount() != j)) throw new AssertionError("newArity=" + j + " but collector=" + localMethodHandle2);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 377 */         throw new WrongMethodTypeException("cannot build collector", localIllegalArgumentException);
/*     */       }
/* 379 */       this.asCollectorCache = localMethodHandle2;
/* 380 */       return this.asTypeCache = localMethodHandle2.asType(paramMethodType);
/*     */     }
/*     */     
/*     */     MethodHandle setVarargs(MemberName paramMemberName)
/*     */     {
/* 385 */       if (paramMemberName.isVarargs()) return this;
/* 386 */       return asFixedArity();
/*     */     }
/*     */     
/*     */     MethodHandle viewAsType(MethodType paramMethodType)
/*     */     {
/* 391 */       if (paramMethodType.lastParameterType() != type().lastParameterType())
/* 392 */         throw new InternalError();
/* 393 */       MethodHandle localMethodHandle = asFixedArity().viewAsType(paramMethodType);
/*     */       
/* 395 */       return new AsVarargsCollector(localMethodHandle, paramMethodType, this.arrayType);
/*     */     }
/*     */     
/*     */     MemberName internalMemberName()
/*     */     {
/* 400 */       return asFixedArity().internalMemberName();
/*     */     }
/*     */     
/*     */     Class<?> internalCallerClass() {
/* 404 */       return asFixedArity().internalCallerClass();
/*     */     }
/*     */     
/*     */ 
/*     */     boolean isInvokeSpecial()
/*     */     {
/* 410 */       return asFixedArity().isInvokeSpecial();
/*     */     }
/*     */     
/*     */ 
/*     */     MethodHandle bindArgument(int paramInt, char paramChar, Object paramObject)
/*     */     {
/* 416 */       return asFixedArity().bindArgument(paramInt, paramChar, paramObject);
/*     */     }
/*     */     
/*     */     MethodHandle bindReceiver(Object paramObject)
/*     */     {
/* 421 */       return asFixedArity().bindReceiver(paramObject);
/*     */     }
/*     */     
/*     */     MethodHandle dropArguments(MethodType paramMethodType, int paramInt1, int paramInt2)
/*     */     {
/* 426 */       return asFixedArity().dropArguments(paramMethodType, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */     MethodHandle permuteArguments(MethodType paramMethodType, int[] paramArrayOfInt)
/*     */     {
/* 431 */       return asFixedArity().permuteArguments(paramMethodType, paramArrayOfInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static MethodHandle makeSpreadArguments(MethodHandle paramMethodHandle, Class<?> paramClass, int paramInt1, int paramInt2)
/*     */   {
/* 438 */     MethodType localMethodType1 = paramMethodHandle.type();
/*     */     
/* 440 */     for (int i = 0; i < paramInt2; i++) {
/* 441 */       localObject = VerifyType.spreadArgElementType(paramClass, i);
/* 442 */       if (localObject == null) localObject = Object.class;
/* 443 */       localMethodType1 = localMethodType1.changeParameterType(paramInt1 + i, (Class)localObject);
/*     */     }
/* 445 */     paramMethodHandle = paramMethodHandle.asType(localMethodType1);
/*     */     
/*     */ 
/* 448 */     MethodType localMethodType2 = localMethodType1.replaceParameterTypes(paramInt1, paramInt1 + paramInt2, new Class[] { paramClass });
/*     */     
/* 450 */     Object localObject = localMethodType2.invokerType();
/* 451 */     LambdaForm.Name[] arrayOfName1 = LambdaForm.arguments(paramInt2 + 2, (MethodType)localObject);
/* 452 */     int j = ((MethodType)localObject).parameterCount();
/* 453 */     int[] arrayOfInt = new int[localMethodType1.parameterCount()];
/*     */     
/* 455 */     int k = 0; for (int m = 1; k < localMethodType1.parameterCount() + 1; m++) {
/* 456 */       Class localClass = ((MethodType)localObject).parameterType(k);
/* 457 */       if (k == paramInt1)
/*     */       {
/* 459 */         MethodHandle localMethodHandle = MethodHandles.arrayElementGetter(paramClass);
/* 460 */         LambdaForm.Name localName = arrayOfName1[m];
/* 461 */         arrayOfName1[(j++)] = new LambdaForm.Name(Lazy.NF_checkSpreadArgument, new Object[] { localName, Integer.valueOf(paramInt2) });
/* 462 */         for (int i1 = 0; i1 < paramInt2; i1++) {
/* 463 */           arrayOfInt[k] = j;
/* 464 */           arrayOfName1[(j++)] = new LambdaForm.Name(localMethodHandle, new Object[] { localName, Integer.valueOf(i1) });k++;
/*     */         }
/*     */       }
/* 466 */       else if (k < arrayOfInt.length) {
/* 467 */         arrayOfInt[k] = m;
/*     */       }
/* 455 */       k++;
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
/*     */ 
/*     */ 
/*     */ 
/* 470 */     assert (j == arrayOfName1.length - 1);
/*     */     
/*     */ 
/* 473 */     LambdaForm.Name[] arrayOfName2 = new LambdaForm.Name[localMethodType1.parameterCount()];
/* 474 */     for (m = 0; m < localMethodType1.parameterCount(); m++) {
/* 475 */       int n = arrayOfInt[m];
/* 476 */       arrayOfName2[m] = arrayOfName1[n];
/*     */     }
/* 478 */     arrayOfName1[(arrayOfName1.length - 1)] = new LambdaForm.Name(paramMethodHandle, (Object[])arrayOfName2);
/*     */     
/* 480 */     LambdaForm localLambdaForm = new LambdaForm("spread", ((MethodType)localObject).parameterCount(), arrayOfName1);
/* 481 */     return SimpleMethodHandle.make(localMethodType2, localLambdaForm);
/*     */   }
/*     */   
/*     */   static void checkSpreadArgument(Object paramObject, int paramInt) {
/* 485 */     if (paramObject == null) {
/* 486 */       if (paramInt != 0) {} } else { int i;
/* 487 */       if ((paramObject instanceof Object[])) {
/* 488 */         i = ((Object[])paramObject).length;
/* 489 */         if (i == paramInt) return;
/*     */       } else {
/* 491 */         i = Array.getLength(paramObject);
/* 492 */         if (i == paramInt) return;
/*     */       }
/*     */     }
/* 495 */     throw MethodHandleStatics.newIllegalArgumentException("array is not of length " + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class Lazy
/*     */   {
/* 503 */     private static final Class<?> MHI = MethodHandleImpl.class;
/*     */     static final LambdaForm.NamedFunction NF_checkSpreadArgument;
/*     */     static final LambdaForm.NamedFunction NF_guardWithCatch;
/*     */     static final LambdaForm.NamedFunction NF_selectAlternative;
/*     */     static final LambdaForm.NamedFunction NF_throwException;
/*     */     static final MethodHandle MH_castReference;
/*     */     
/*     */     static
/*     */     {
/*     */       try
/*     */       {
/* 514 */         NF_checkSpreadArgument = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("checkSpreadArgument", new Class[] { Object.class, Integer.TYPE }));
/* 515 */         NF_guardWithCatch = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("guardWithCatch", new Class[] { MethodHandle.class, Class.class, MethodHandle.class, Object[].class }));
/*     */         
/* 517 */         NF_selectAlternative = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("selectAlternative", new Class[] { Boolean.TYPE, MethodHandle.class, MethodHandle.class }));
/*     */         
/* 519 */         NF_throwException = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("throwException", new Class[] { Throwable.class }));
/*     */         
/* 521 */         NF_checkSpreadArgument.resolve();
/* 522 */         NF_guardWithCatch.resolve();
/* 523 */         NF_selectAlternative.resolve();
/* 524 */         NF_throwException.resolve();
/*     */         
/* 526 */         MethodType localMethodType = MethodType.methodType(Object.class, Class.class, new Class[] { Object.class });
/* 527 */         MH_castReference = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MHI, "castReference", localMethodType);
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 529 */         throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static MethodHandle makeCollectArguments(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, int paramInt, boolean paramBoolean)
/*     */   {
/* 537 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 538 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 539 */     int i = localMethodType2.parameterCount();
/* 540 */     Class localClass = localMethodType2.returnType();
/* 541 */     int j = localClass == Void.TYPE ? 0 : 1;
/*     */     
/* 543 */     MethodType localMethodType3 = localMethodType1.dropParameterTypes(paramInt, paramInt + j);
/* 544 */     if (!paramBoolean) {
/* 545 */       localMethodType3 = localMethodType3.insertParameterTypes(paramInt, localMethodType2.parameterList());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 552 */     MethodType localMethodType4 = localMethodType3.invokerType();
/* 553 */     LambdaForm.Name[] arrayOfName1 = LambdaForm.arguments(2, localMethodType4);
/* 554 */     int k = arrayOfName1.length - 2;
/* 555 */     int m = arrayOfName1.length - 1;
/*     */     
/* 557 */     LambdaForm.Name[] arrayOfName2 = (LambdaForm.Name[])Arrays.copyOfRange(arrayOfName1, 1 + paramInt, 1 + paramInt + i);
/* 558 */     arrayOfName1[k] = new LambdaForm.Name(paramMethodHandle2, (Object[])arrayOfName2);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 563 */     LambdaForm.Name[] arrayOfName3 = new LambdaForm.Name[localMethodType1.parameterCount()];
/* 564 */     int n = 1;
/* 565 */     int i1 = 0;
/* 566 */     int i2 = paramInt;
/* 567 */     System.arraycopy(arrayOfName1, n, arrayOfName3, i1, i2);
/* 568 */     n += i2;
/* 569 */     i1 += i2;
/* 570 */     if (localClass != Void.TYPE) {
/* 571 */       arrayOfName3[(i1++)] = arrayOfName1[k];
/*     */     }
/* 573 */     i2 = i;
/* 574 */     if (paramBoolean) {
/* 575 */       System.arraycopy(arrayOfName1, n, arrayOfName3, i1, i2);
/* 576 */       i1 += i2;
/*     */     }
/* 578 */     n += i2;
/* 579 */     i2 = arrayOfName3.length - i1;
/* 580 */     System.arraycopy(arrayOfName1, n, arrayOfName3, i1, i2);
/* 581 */     assert (n + i2 == k);
/* 582 */     arrayOfName1[m] = new LambdaForm.Name(paramMethodHandle1, (Object[])arrayOfName3);
/*     */     
/* 584 */     LambdaForm localLambdaForm = new LambdaForm("collect", localMethodType4.parameterCount(), arrayOfName1);
/* 585 */     return SimpleMethodHandle.make(localMethodType3, localLambdaForm);
/*     */   }
/*     */   
/*     */   @LambdaForm.Hidden
/*     */   static MethodHandle selectAlternative(boolean paramBoolean, MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2)
/*     */   {
/* 591 */     return paramBoolean ? paramMethodHandle1 : paramMethodHandle2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static MethodHandle makeGuardWithTest(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, MethodHandle paramMethodHandle3)
/*     */   {
/* 598 */     MethodType localMethodType1 = paramMethodHandle2.type().basicType();
/* 599 */     MethodHandle localMethodHandle = MethodHandles.basicInvoker(localMethodType1);
/* 600 */     int i = localMethodType1.parameterCount();
/* 601 */     int j = 3;
/* 602 */     MethodType localMethodType2 = localMethodType1.invokerType();
/* 603 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j, localMethodType2);
/*     */     
/* 605 */     Object[] arrayOfObject1 = Arrays.copyOfRange(arrayOfName, 1, 1 + i, Object[].class);
/* 606 */     Object[] arrayOfObject2 = Arrays.copyOfRange(arrayOfName, 0, 1 + i, Object[].class);
/*     */     
/*     */ 
/* 609 */     arrayOfName[(i + 1)] = new LambdaForm.Name(paramMethodHandle1, arrayOfObject1);
/*     */     
/*     */ 
/* 612 */     Object[] arrayOfObject3 = { arrayOfName[(i + 1)], paramMethodHandle2, paramMethodHandle3 };
/* 613 */     arrayOfName[(i + 2)] = new LambdaForm.Name(Lazy.NF_selectAlternative, arrayOfObject3);
/* 614 */     arrayOfObject2[0] = arrayOfName[(i + 2)];
/*     */     
/*     */ 
/* 617 */     arrayOfName[(i + 3)] = new LambdaForm.Name(new LambdaForm.NamedFunction(localMethodHandle), arrayOfObject2);
/*     */     
/* 619 */     LambdaForm localLambdaForm = new LambdaForm("guard", localMethodType2.parameterCount(), arrayOfName);
/* 620 */     return SimpleMethodHandle.make(paramMethodHandle2.type(), localLambdaForm);
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
/*     */   private static LambdaForm makeGuardWithCatchForm(MethodType paramMethodType)
/*     */   {
/* 645 */     MethodType localMethodType1 = paramMethodType.invokerType();
/*     */     
/* 647 */     LambdaForm localLambdaForm = paramMethodType.form().cachedLambdaForm(15);
/* 648 */     if (localLambdaForm != null) {
/* 649 */       return localLambdaForm;
/*     */     }
/*     */     
/*     */ 
/* 653 */     int i = 1 + paramMethodType.parameterCount();
/*     */     
/* 655 */     int j = i;
/* 656 */     int k = j++;
/* 657 */     int m = j++;
/* 658 */     int n = j++;
/* 659 */     int i1 = j++;
/* 660 */     int i2 = j++;
/* 661 */     int i3 = j++;
/* 662 */     int i4 = j++;
/* 663 */     int i5 = j++;
/*     */     
/* 665 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j - i, localMethodType1);
/*     */     
/* 667 */     BoundMethodHandle.SpeciesData localSpeciesData = BoundMethodHandle.speciesData_LLLLL();
/* 668 */     arrayOfName[k] = new LambdaForm.Name(localSpeciesData.getterFunction(0), new Object[] { arrayOfName[0] });
/* 669 */     arrayOfName[m] = new LambdaForm.Name(localSpeciesData.getterFunction(1), new Object[] { arrayOfName[0] });
/* 670 */     arrayOfName[n] = new LambdaForm.Name(localSpeciesData.getterFunction(2), new Object[] { arrayOfName[0] });
/* 671 */     arrayOfName[i1] = new LambdaForm.Name(localSpeciesData.getterFunction(3), new Object[] { arrayOfName[0] });
/* 672 */     arrayOfName[i2] = new LambdaForm.Name(localSpeciesData.getterFunction(4), new Object[] { arrayOfName[0] });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 677 */     MethodType localMethodType2 = paramMethodType.changeReturnType(Object.class);
/* 678 */     MethodHandle localMethodHandle1 = MethodHandles.basicInvoker(localMethodType2);
/* 679 */     Object[] arrayOfObject1 = new Object[localMethodHandle1.type().parameterCount()];
/* 680 */     arrayOfObject1[0] = arrayOfName[i1];
/* 681 */     System.arraycopy(arrayOfName, 1, arrayOfObject1, 1, i - 1);
/* 682 */     arrayOfName[i3] = new LambdaForm.Name(new LambdaForm.NamedFunction(localMethodHandle1), arrayOfObject1);
/*     */     
/*     */ 
/* 685 */     Object[] arrayOfObject2 = { arrayOfName[k], arrayOfName[m], arrayOfName[n], arrayOfName[i3] };
/* 686 */     arrayOfName[i4] = new LambdaForm.Name(Lazy.NF_guardWithCatch, arrayOfObject2);
/*     */     
/*     */ 
/* 689 */     MethodHandle localMethodHandle2 = MethodHandles.basicInvoker(MethodType.methodType(paramMethodType.rtype(), Object.class));
/* 690 */     Object[] arrayOfObject3 = { arrayOfName[i2], arrayOfName[i4] };
/* 691 */     arrayOfName[i5] = new LambdaForm.Name(new LambdaForm.NamedFunction(localMethodHandle2), arrayOfObject3);
/*     */     
/* 693 */     localLambdaForm = new LambdaForm("guardWithCatch", localMethodType1.parameterCount(), arrayOfName);
/*     */     
/* 695 */     return paramMethodType.form().setCachedLambdaForm(15, localLambdaForm);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static MethodHandle makeGuardWithCatch(MethodHandle paramMethodHandle1, Class<? extends Throwable> paramClass, MethodHandle paramMethodHandle2)
/*     */   {
/* 702 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 703 */     LambdaForm localLambdaForm = makeGuardWithCatchForm(localMethodType1.basicType());
/*     */     
/*     */ 
/*     */ 
/* 707 */     MethodType localMethodType2 = localMethodType1.changeReturnType(Object[].class);
/*     */     
/* 709 */     MethodHandle localMethodHandle1 = ValueConversions.varargsArray(localMethodType1.parameterCount()).asType(localMethodType2);
/*     */     
/*     */     MethodHandle localMethodHandle2;
/* 712 */     if (localMethodType1.returnType().isPrimitive()) {
/* 713 */       localMethodHandle2 = ValueConversions.unbox(localMethodType1.returnType());
/*     */     } else {
/* 715 */       localMethodHandle2 = ValueConversions.identity();
/*     */     }
/*     */     
/* 718 */     BoundMethodHandle.SpeciesData localSpeciesData = BoundMethodHandle.speciesData_LLLLL();
/*     */     BoundMethodHandle localBoundMethodHandle;
/*     */     try
/*     */     {
/* 722 */       localBoundMethodHandle = localSpeciesData.constructor[0].invokeBasic(localMethodType1, localLambdaForm, paramMethodHandle1, paramClass, paramMethodHandle2, localMethodHandle1, localMethodHandle2);
/*     */     }
/*     */     catch (Throwable localThrowable) {
/* 725 */       throw MethodHandleStatics.uncaughtException(localThrowable);
/*     */     }
/* 727 */     assert (localBoundMethodHandle.type() == localMethodType1);
/* 728 */     return localBoundMethodHandle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @LambdaForm.Hidden
/*     */   static Object guardWithCatch(MethodHandle paramMethodHandle1, Class<? extends Throwable> paramClass, MethodHandle paramMethodHandle2, Object... paramVarArgs)
/*     */     throws Throwable
/*     */   {
/*     */     try
/*     */     {
/* 740 */       return paramMethodHandle1.asFixedArity().invokeWithArguments(paramVarArgs);
/*     */     } catch (Throwable localThrowable) {
/* 742 */       if (!paramClass.isInstance(localThrowable)) throw localThrowable;
/* 743 */       return paramMethodHandle2.asFixedArity().invokeWithArguments(prepend(localThrowable, paramVarArgs));
/*     */     }
/*     */   }
/*     */   
/*     */   @LambdaForm.Hidden
/*     */   private static Object[] prepend(Object paramObject, Object[] paramArrayOfObject)
/*     */   {
/* 750 */     Object[] arrayOfObject = new Object[paramArrayOfObject.length + 1];
/* 751 */     arrayOfObject[0] = paramObject;
/* 752 */     System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 1, paramArrayOfObject.length);
/* 753 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */   static MethodHandle throwException(MethodType paramMethodType)
/*     */   {
/* 758 */     assert (Throwable.class.isAssignableFrom(paramMethodType.parameterType(0)));
/* 759 */     int i = paramMethodType.parameterCount();
/* 760 */     if (i > 1) {
/* 761 */       return throwException(paramMethodType.dropParameterTypes(1, i)).dropArguments(paramMethodType, 1, i - 1);
/*     */     }
/* 763 */     return makePairwiseConvert(Lazy.NF_throwException.resolvedHandle(), paramMethodType, 2);
/*     */   }
/*     */   
/* 766 */   static <T extends Throwable> Empty throwException(T paramT) throws Throwable { throw paramT; }
/*     */   
/* 768 */   static MethodHandle[] FAKE_METHOD_HANDLE_INVOKE = new MethodHandle[2];
/*     */   
/*     */   static MethodHandle fakeMethodHandleInvoke(MemberName paramMemberName) {
/* 771 */     assert (paramMemberName.isMethodHandleInvoke());
/* 772 */     Object localObject = paramMemberName.getName();int j = -1; switch (((String)localObject).hashCode()) {case -1183693704:  if (((String)localObject).equals("invoke")) j = 0; break; case 941760871:  if (((String)localObject).equals("invokeExact")) j = 1; break; } int i; switch (j) {
/* 773 */     case 0:  i = 0; break;
/* 774 */     case 1:  i = 1; break;
/* 775 */     default:  throw new InternalError(paramMemberName.getName());
/*     */     }
/* 777 */     localObject = FAKE_METHOD_HANDLE_INVOKE[i];
/* 778 */     if (localObject != null) return (MethodHandle)localObject;
/* 779 */     MethodType localMethodType = MethodType.methodType(Object.class, UnsupportedOperationException.class, new Class[] { MethodHandle.class, Object[].class });
/*     */     
/* 781 */     localObject = throwException(localMethodType);
/* 782 */     localObject = ((MethodHandle)localObject).bindTo(new UnsupportedOperationException("cannot reflectively invoke MethodHandle"));
/* 783 */     if (!paramMemberName.getInvocationType().equals(((MethodHandle)localObject).type()))
/* 784 */       throw new InternalError(paramMemberName.toString());
/* 785 */     localObject = ((MethodHandle)localObject).withInternalMemberName(paramMemberName);
/* 786 */     localObject = ((MethodHandle)localObject).asVarargsCollector(Object[].class);
/* 787 */     assert (paramMemberName.isVarargs());
/* 788 */     FAKE_METHOD_HANDLE_INVOKE[i] = localObject;
/* 789 */     return (MethodHandle)localObject;
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
/*     */   static MethodHandle bindCaller(MethodHandle paramMethodHandle, Class<?> paramClass)
/*     */   {
/* 802 */     return BindCaller.bindCaller(paramMethodHandle, paramClass);
/*     */   }
/*     */   
/*     */   private static class BindCaller
/*     */   {
/*     */     private static ClassValue<MethodHandle> CV_makeInjectedInvoker;
/*     */     private static final MethodHandle MH_checkCallerClass;
/*     */     
/*     */     static MethodHandle bindCaller(MethodHandle paramMethodHandle, Class<?> paramClass) {
/* 811 */       if ((paramClass == null) || 
/* 812 */         (paramClass.isArray()) || 
/* 813 */         (paramClass.isPrimitive()) || 
/* 814 */         (paramClass.getName().startsWith("java.")) || 
/* 815 */         (paramClass.getName().startsWith("sun."))) {
/* 816 */         throw new InternalError();
/*     */       }
/*     */       
/* 819 */       MethodHandle localMethodHandle1 = prepareForInvoker(paramMethodHandle);
/*     */       
/* 821 */       MethodHandle localMethodHandle2 = (MethodHandle)CV_makeInjectedInvoker.get(paramClass);
/* 822 */       return restoreToType(localMethodHandle2.bindTo(localMethodHandle1), paramMethodHandle.type(), paramMethodHandle.internalMemberName(), paramClass);
/*     */     }
/*     */     
/*     */     private static MethodHandle makeInjectedInvoker(Class<?> paramClass) {
/* 826 */       Class localClass = MethodHandleStatics.UNSAFE.defineAnonymousClass(paramClass, T_BYTES, null);
/* 827 */       if (paramClass.getClassLoader() != localClass.getClassLoader())
/* 828 */         throw new InternalError(paramClass.getName() + " (CL)");
/*     */       try {
/* 830 */         if (paramClass.getProtectionDomain() != localClass.getProtectionDomain()) {
/* 831 */           throw new InternalError(paramClass.getName() + " (PD)");
/*     */         }
/*     */       }
/*     */       catch (SecurityException localSecurityException) {}
/*     */       try
/*     */       {
/* 837 */         MethodHandle localMethodHandle1 = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(localClass, "init", MethodType.methodType(Void.TYPE));
/* 838 */         localMethodHandle1.invokeExact();
/*     */       } catch (Throwable localThrowable1) {
/* 840 */         throw MethodHandleStatics.uncaughtException(localThrowable1);
/*     */       }
/*     */       MethodHandle localMethodHandle2;
/*     */       try {
/* 844 */         MethodType localMethodType = MethodType.methodType(Object.class, MethodHandle.class, new Class[] { Object[].class });
/* 845 */         localMethodHandle2 = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(localClass, "invoke_V", localMethodType);
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 847 */         throw MethodHandleStatics.uncaughtException(localReflectiveOperationException);
/*     */       }
/*     */       try
/*     */       {
/* 851 */         MethodHandle localMethodHandle3 = prepareForInvoker(MH_checkCallerClass);
/* 852 */         Object localObject = localMethodHandle2.invokeExact(localMethodHandle3, new Object[] { paramClass, localClass });
/*     */       } catch (Throwable localThrowable2) {
/* 854 */         throw new InternalError(localThrowable2);
/*     */       }
/* 856 */       return localMethodHandle2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private static MethodHandle prepareForInvoker(MethodHandle paramMethodHandle)
/*     */     {
/* 866 */       paramMethodHandle = paramMethodHandle.asFixedArity();
/* 867 */       MethodType localMethodType = paramMethodHandle.type();
/* 868 */       int i = localMethodType.parameterCount();
/* 869 */       MethodHandle localMethodHandle = paramMethodHandle.asType(localMethodType.generic());
/* 870 */       localMethodHandle.internalForm().compileToBytecode();
/* 871 */       localMethodHandle = localMethodHandle.asSpreader(Object[].class, i);
/* 872 */       localMethodHandle.internalForm().compileToBytecode();
/* 873 */       return localMethodHandle;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private static MethodHandle restoreToType(MethodHandle paramMethodHandle, MethodType paramMethodType, MemberName paramMemberName, Class<?> paramClass)
/*     */     {
/* 880 */       Object localObject = paramMethodHandle.asCollector(Object[].class, paramMethodType.parameterCount());
/* 881 */       localObject = ((MethodHandle)localObject).asType(paramMethodType);
/* 882 */       localObject = new MethodHandleImpl.WrappedMember((MethodHandle)localObject, paramMethodType, paramMemberName, paramClass, null);
/* 883 */       return (MethodHandle)localObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @CallerSensitive
/*     */     private static boolean checkCallerClass(Class<?> paramClass1, Class<?> paramClass2)
/*     */     {
/* 904 */       Class localClass = Reflection.getCallerClass();
/* 905 */       if ((localClass != paramClass1) && (localClass != paramClass2))
/*     */       {
/* 907 */         throw new InternalError("found " + localClass.getName() + ", expected " + paramClass1.getName() + (paramClass1 == paramClass2 ? "" : new StringBuilder().append(", or else ").append(paramClass2.getName()).toString())); }
/* 908 */       return true;
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 858 */       CV_makeInjectedInvoker = new ClassValue() {
/*     */         protected MethodHandle computeValue(Class<?> paramAnonymousClass) {
/* 860 */           return MethodHandleImpl.BindCaller.makeInjectedInvoker(paramAnonymousClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 887 */       };
/* 888 */       Object localObject = BindCaller.class;
/* 889 */       assert (checkCallerClass((Class)localObject, (Class)localObject));
/*     */       try
/*     */       {
/* 892 */         MH_checkCallerClass = MethodHandles.Lookup.IMPL_LOOKUP.findStatic((Class)localObject, "checkCallerClass", 
/* 893 */           MethodType.methodType(Boolean.TYPE, Class.class, new Class[] { Class.class }));
/* 894 */         if ((!$assertionsDisabled) && (!MH_checkCallerClass.invokeExact((Class)localObject, (Class)localObject))) throw new AssertionError();
/*     */       } catch (Throwable localThrowable) {
/* 896 */         throw new InternalError(localThrowable);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 913 */       localObject = new Object[] { null };
/* 914 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Void run() {
/*     */           try {
/* 917 */             Class localClass = MethodHandleImpl.BindCaller.T.class;
/* 918 */             String str1 = localClass.getName();
/* 919 */             String str2 = str1.substring(str1.lastIndexOf('.') + 1) + ".class";
/* 920 */             URLConnection localURLConnection = localClass.getResource(str2).openConnection();
/* 921 */             int i = localURLConnection.getContentLength();
/* 922 */             byte[] arrayOfByte = new byte[i];
/* 923 */             InputStream localInputStream = localURLConnection.getInputStream();Object localObject1 = null;
/* 924 */             try { int j = localInputStream.read(arrayOfByte);
/* 925 */               if (j != i) { throw new IOException(str2);
/*     */               }
/*     */             }
/*     */             catch (Throwable localThrowable2)
/*     */             {
/* 923 */               localObject1 = localThrowable2;throw localThrowable2;
/*     */             }
/*     */             finally {
/* 926 */               if (localInputStream != null) if (localObject1 != null) try { localInputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject1).addSuppressed(localThrowable3); } else localInputStream.close(); }
/* 927 */             this.val$values[0] = arrayOfByte;
/*     */           } catch (IOException localIOException) {
/* 929 */             throw new InternalError(localIOException);
/*     */           }
/* 931 */           return null;
/*     */         } }); }
/*     */     
/* 934 */     private static final byte[] T_BYTES = (byte[])localObject[0];
/*     */     
/*     */     private static class T
/*     */     {
/*     */       static void init() {}
/*     */       
/*     */       static Object invoke_V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable {
/* 941 */         return paramMethodHandle.invokeExact(paramArrayOfObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class WrappedMember extends MethodHandle
/*     */   {
/*     */     private final MethodHandle target;
/*     */     private final MemberName member;
/*     */     private final Class<?> callerClass;
/*     */     
/*     */     private WrappedMember(MethodHandle paramMethodHandle, MethodType paramMethodType, MemberName paramMemberName, Class<?> paramClass)
/*     */     {
/* 954 */       super(reinvokerForm(paramMethodHandle));
/* 955 */       this.target = paramMethodHandle;
/* 956 */       this.member = paramMemberName;
/* 957 */       this.callerClass = paramClass;
/*     */     }
/*     */     
/*     */     MethodHandle reinvokerTarget()
/*     */     {
/* 962 */       return this.target;
/*     */     }
/*     */     
/*     */ 
/*     */     public MethodHandle asTypeUncached(MethodType paramMethodType)
/*     */     {
/* 968 */       return this.asTypeCache = this.target.asType(paramMethodType);
/*     */     }
/*     */     
/*     */     MemberName internalMemberName() {
/* 972 */       return this.member;
/*     */     }
/*     */     
/*     */     Class<?> internalCallerClass() {
/* 976 */       return this.callerClass;
/*     */     }
/*     */     
/*     */     boolean isInvokeSpecial() {
/* 980 */       return this.target.isInvokeSpecial();
/*     */     }
/*     */     
/*     */     MethodHandle viewAsType(MethodType paramMethodType) {
/* 984 */       return new WrappedMember(this.target, paramMethodType, this.member, this.callerClass);
/*     */     }
/*     */   }
/*     */   
/*     */   static MethodHandle makeWrappedMember(MethodHandle paramMethodHandle, MemberName paramMemberName) {
/* 989 */     if (paramMemberName.equals(paramMethodHandle.internalMemberName()))
/* 990 */       return paramMethodHandle;
/* 991 */     return new WrappedMember(paramMethodHandle, paramMethodHandle.type(), paramMemberName, null, null);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */