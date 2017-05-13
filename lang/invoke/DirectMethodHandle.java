/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import sun.invoke.util.ValueConversions;
/*     */ import sun.invoke.util.VerifyAccess;
/*     */ import sun.invoke.util.VerifyType;
/*     */ import sun.invoke.util.Wrapper;
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
/*     */ class DirectMethodHandle
/*     */   extends MethodHandle
/*     */ {
/*     */   final MemberName member;
/*     */   
/*     */   private DirectMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm, MemberName paramMemberName)
/*     */   {
/*  52 */     super(paramMethodType, paramLambdaForm);
/*  53 */     if (!paramMemberName.isResolved()) { throw new InternalError();
/*     */     }
/*  55 */     if ((paramMemberName.getDeclaringClass().isInterface()) && 
/*  56 */       (paramMemberName.isMethod()) && (!paramMemberName.isAbstract()))
/*     */     {
/*  58 */       MemberName localMemberName = new MemberName(Object.class, paramMemberName.getName(), paramMemberName.getMethodType(), paramMemberName.getReferenceKind());
/*  59 */       localMemberName = MemberName.getFactory().resolveOrNull(localMemberName.getReferenceKind(), localMemberName, null);
/*  60 */       if ((localMemberName != null) && (localMemberName.isPublic())) {
/*  61 */         paramMemberName = localMemberName;
/*     */       }
/*     */     }
/*     */     
/*  65 */     this.member = paramMemberName;
/*     */   }
/*     */   
/*     */   static DirectMethodHandle make(byte paramByte, Class<?> paramClass, MemberName paramMemberName)
/*     */   {
/*  70 */     MethodType localMethodType = paramMemberName.getMethodOrFieldType();
/*  71 */     if (!paramMemberName.isStatic()) {
/*  72 */       if ((!paramMemberName.getDeclaringClass().isAssignableFrom(paramClass)) || (paramMemberName.isConstructor()))
/*  73 */         throw new InternalError(paramMemberName.toString());
/*  74 */       localMethodType = localMethodType.insertParameterTypes(0, new Class[] { paramClass });
/*     */     }
/*  76 */     if (!paramMemberName.isField()) {
/*  77 */       if (paramByte == 7) {
/*  78 */         paramMemberName = paramMemberName.asSpecial();
/*  79 */         localLambdaForm = preparedLambdaForm(paramMemberName);
/*  80 */         return new Special(localMethodType, localLambdaForm, paramMemberName, null);
/*     */       }
/*  82 */       localLambdaForm = preparedLambdaForm(paramMemberName);
/*  83 */       return new DirectMethodHandle(localMethodType, localLambdaForm, paramMemberName);
/*     */     }
/*     */     
/*  86 */     LambdaForm localLambdaForm = preparedFieldLambdaForm(paramMemberName);
/*  87 */     if (paramMemberName.isStatic()) {
/*  88 */       l = MethodHandleNatives.staticFieldOffset(paramMemberName);
/*  89 */       Object localObject = MethodHandleNatives.staticFieldBase(paramMemberName);
/*  90 */       return new StaticAccessor(localMethodType, localLambdaForm, paramMemberName, localObject, l, null);
/*     */     }
/*  92 */     long l = MethodHandleNatives.objectFieldOffset(paramMemberName);
/*  93 */     assert (l == (int)l);
/*  94 */     return new Accessor(localMethodType, localLambdaForm, paramMemberName, (int)l, null);
/*     */   }
/*     */   
/*     */   static DirectMethodHandle make(Class<?> paramClass, MemberName paramMemberName)
/*     */   {
/*  99 */     byte b = paramMemberName.getReferenceKind();
/* 100 */     if (b == 7)
/* 101 */       b = 5;
/* 102 */     return make(b, paramClass, paramMemberName);
/*     */   }
/*     */   
/* 105 */   static DirectMethodHandle make(MemberName paramMemberName) { if (paramMemberName.isConstructor())
/* 106 */       return makeAllocator(paramMemberName);
/* 107 */     return make(paramMemberName.getDeclaringClass(), paramMemberName);
/*     */   }
/*     */   
/* 110 */   static DirectMethodHandle make(Method paramMethod) { return make(paramMethod.getDeclaringClass(), new MemberName(paramMethod)); }
/*     */   
/*     */ 
/* 113 */   static DirectMethodHandle make(Field paramField) { return make(paramField.getDeclaringClass(), new MemberName(paramField)); }
/*     */   
/*     */   private static DirectMethodHandle makeAllocator(MemberName paramMemberName) {
/* 116 */     assert ((paramMemberName.isConstructor()) && (paramMemberName.getName().equals("<init>")));
/* 117 */     Class localClass = paramMemberName.getDeclaringClass();
/* 118 */     paramMemberName = paramMemberName.asConstructor();
/* 119 */     assert ((paramMemberName.isConstructor()) && (paramMemberName.getReferenceKind() == 8)) : paramMemberName;
/* 120 */     MethodType localMethodType = paramMemberName.getMethodType().changeReturnType(localClass);
/* 121 */     LambdaForm localLambdaForm = preparedLambdaForm(paramMemberName);
/* 122 */     MemberName localMemberName = paramMemberName.asSpecial();
/* 123 */     assert (localMemberName.getMethodType().returnType() == Void.TYPE);
/* 124 */     return new Constructor(localMethodType, localLambdaForm, paramMemberName, localMemberName, localClass, null);
/*     */   }
/*     */   
/*     */   MethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm)
/*     */   {
/* 129 */     return new DirectMethodHandle(paramMethodType, paramLambdaForm, this.member);
/*     */   }
/*     */   
/*     */   String internalProperties()
/*     */   {
/* 134 */     return "/DMH=" + this.member.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   MethodHandle viewAsType(MethodType paramMethodType)
/*     */   {
/* 140 */     return new DirectMethodHandle(paramMethodType, this.form, this.member);
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   MemberName internalMemberName() {
/* 145 */     return this.member;
/*     */   }
/*     */   
/*     */ 
/*     */   MethodHandle bindArgument(int paramInt, char paramChar, Object paramObject)
/*     */   {
/* 151 */     if ((paramInt == 0) && (paramChar == 'L')) {
/* 152 */       DirectMethodHandle localDirectMethodHandle = maybeRebind(paramObject);
/* 153 */       if (localDirectMethodHandle != null)
/* 154 */         return localDirectMethodHandle.bindReceiver(paramObject);
/*     */     }
/* 156 */     return super.bindArgument(paramInt, paramChar, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */   MethodHandle bindReceiver(Object paramObject)
/*     */   {
/* 162 */     DirectMethodHandle localDirectMethodHandle = maybeRebind(paramObject);
/* 163 */     if (localDirectMethodHandle != null)
/* 164 */       return localDirectMethodHandle.bindReceiver(paramObject);
/* 165 */     return super.bindReceiver(paramObject);
/*     */   }
/*     */   
/* 168 */   private static final MemberName.Factory IMPL_NAMES = MemberName.getFactory();
/*     */   
/*     */   private DirectMethodHandle maybeRebind(Object paramObject) {
/* 171 */     if (paramObject != null) {
/* 172 */       switch (this.member.getReferenceKind())
/*     */       {
/*     */       case 5: 
/*     */       case 9: 
/* 176 */         Class localClass = paramObject.getClass();
/* 177 */         MemberName localMemberName = new MemberName(localClass, this.member.getName(), this.member.getMethodType(), (byte)7);
/* 178 */         localMemberName = IMPL_NAMES.resolveOrNull((byte)7, localMemberName, localClass);
/* 179 */         if (localMemberName != null)
/* 180 */           return new DirectMethodHandle(type(), preparedLambdaForm(localMemberName), localMemberName);
/*     */         break;
/*     */       }
/*     */     }
/* 184 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static LambdaForm preparedLambdaForm(MemberName paramMemberName)
/*     */   {
/* 193 */     assert (paramMemberName.isInvocable()) : paramMemberName;
/* 194 */     MethodType localMethodType = paramMemberName.getInvocationType().basicType();
/* 195 */     assert ((!paramMemberName.isMethodHandleInvoke()) || ("invokeBasic".equals(paramMemberName.getName()))) : paramMemberName;
/*     */     int i;
/* 197 */     switch (paramMemberName.getReferenceKind()) {
/* 198 */     case 5:  i = 0; break;
/* 199 */     case 6:  i = 1; break;
/* 200 */     case 7:  i = 2; break;
/* 201 */     case 9:  i = 4; break;
/* 202 */     case 8:  i = 3; break;
/* 203 */     default:  throw new InternalError(paramMemberName.toString());
/*     */     }
/* 205 */     if ((i == 1) && (shouldBeInitialized(paramMemberName)))
/*     */     {
/* 207 */       preparedLambdaForm(localMethodType, i);
/* 208 */       i = 5;
/*     */     }
/* 210 */     LambdaForm localLambdaForm = preparedLambdaForm(localMethodType, i);
/* 211 */     maybeCompile(localLambdaForm, paramMemberName);
/* 212 */     if ((!$assertionsDisabled) && 
/* 213 */       (!localLambdaForm.methodType().dropParameterTypes(0, 1).equals(paramMemberName.getInvocationType().basicType())))
/* 214 */       throw new AssertionError(Arrays.asList(new Object[] { paramMemberName, paramMemberName.getInvocationType().basicType(), localLambdaForm, localLambdaForm.methodType() }));
/* 215 */     return localLambdaForm;
/*     */   }
/*     */   
/*     */   private static LambdaForm preparedLambdaForm(MethodType paramMethodType, int paramInt) {
/* 219 */     LambdaForm localLambdaForm = paramMethodType.form().cachedLambdaForm(paramInt);
/* 220 */     if (localLambdaForm != null) return localLambdaForm;
/* 221 */     localLambdaForm = makePreparedLambdaForm(paramMethodType, paramInt);
/* 222 */     return paramMethodType.form().setCachedLambdaForm(paramInt, localLambdaForm);
/*     */   }
/*     */   
/*     */   private static LambdaForm makePreparedLambdaForm(MethodType paramMethodType, int paramInt) {
/* 226 */     int i = paramInt == 5 ? 1 : 0;
/* 227 */     int j = paramInt == 3 ? 1 : 0;
/*     */     String str1;
/* 229 */     switch (paramInt) {
/* 230 */     case 0:  str1 = "linkToVirtual";str2 = "DMH.invokeVirtual"; break;
/* 231 */     case 1:  str1 = "linkToStatic";str2 = "DMH.invokeStatic"; break;
/* 232 */     case 5:  str1 = "linkToStatic";str2 = "DMH.invokeStaticInit"; break;
/* 233 */     case 2:  str1 = "linkToSpecial";str2 = "DMH.invokeSpecial"; break;
/* 234 */     case 4:  str1 = "linkToInterface";str2 = "DMH.invokeInterface"; break;
/* 235 */     case 3:  str1 = "linkToSpecial";str2 = "DMH.newInvokeSpecial"; break;
/* 236 */     default:  throw new InternalError("which=" + paramInt);
/*     */     }
/* 238 */     MethodType localMethodType = paramMethodType.appendParameterTypes(new Class[] { MemberName.class });
/* 239 */     if (j != 0)
/*     */     {
/*     */ 
/* 242 */       localMethodType = localMethodType.insertParameterTypes(0, new Class[] { Object.class }).changeReturnType(Void.TYPE); }
/* 243 */     MemberName localMemberName = new MemberName(MethodHandle.class, str1, localMethodType, (byte)6);
/*     */     try {
/* 245 */       localMemberName = IMPL_NAMES.resolveOrFail((byte)6, localMemberName, null, NoSuchMethodException.class);
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 247 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */     }
/*     */     
/*     */ 
/* 251 */     int k = 1 + paramMethodType.parameterCount();
/* 252 */     int m = k;
/* 253 */     int n = j != 0 ? m++ : -1;
/* 254 */     int i1 = m++;
/* 255 */     int i2 = m++;
/* 256 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(m - k, paramMethodType.invokerType());
/* 257 */     assert (arrayOfName.length == m);
/* 258 */     if (j != 0)
/*     */     {
/* 260 */       arrayOfName[n] = new LambdaForm.Name(Lazy.NF_allocateInstance, new Object[] { arrayOfName[0] });
/* 261 */       arrayOfName[i1] = new LambdaForm.Name(Lazy.NF_constructorMethod, new Object[] { arrayOfName[0] });
/* 262 */     } else if (i != 0) {
/* 263 */       arrayOfName[i1] = new LambdaForm.Name(Lazy.NF_internalMemberNameEnsureInit, new Object[] { arrayOfName[0] });
/*     */     } else {
/* 265 */       arrayOfName[i1] = new LambdaForm.Name(Lazy.NF_internalMemberName, new Object[] { arrayOfName[0] });
/*     */     }
/* 267 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 1, i1 + 1, Object[].class);
/* 268 */     assert (arrayOfObject[(arrayOfObject.length - 1)] == arrayOfName[i1]);
/* 269 */     int i3 = -2;
/* 270 */     if (j != 0) {
/* 271 */       assert (arrayOfObject[(arrayOfObject.length - 2)] == arrayOfName[n]);
/* 272 */       System.arraycopy(arrayOfObject, 0, arrayOfObject, 1, arrayOfObject.length - 2);
/* 273 */       arrayOfObject[0] = arrayOfName[n];
/* 274 */       i3 = n;
/*     */     }
/* 276 */     arrayOfName[i2] = new LambdaForm.Name(localMemberName, arrayOfObject);
/* 277 */     String str2 = str2 + "_" + LambdaForm.basicTypeSignature(paramMethodType);
/* 278 */     LambdaForm localLambdaForm = new LambdaForm(str2, k, arrayOfName, i3);
/*     */     
/* 280 */     localLambdaForm.compileToBytecode();
/* 281 */     return localLambdaForm;
/*     */   }
/*     */   
/*     */   private static void maybeCompile(LambdaForm paramLambdaForm, MemberName paramMemberName) {
/* 285 */     if (VerifyAccess.isSamePackage(paramMemberName.getDeclaringClass(), MethodHandle.class))
/*     */     {
/* 287 */       paramLambdaForm.compileToBytecode();
/*     */     }
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object internalMemberName(Object paramObject) {
/* 293 */     return ((DirectMethodHandle)paramObject).member;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Object internalMemberNameEnsureInit(Object paramObject)
/*     */   {
/* 300 */     DirectMethodHandle localDirectMethodHandle = (DirectMethodHandle)paramObject;
/* 301 */     localDirectMethodHandle.ensureInitialized();
/* 302 */     return localDirectMethodHandle.member;
/*     */   }
/*     */   
/*     */   static boolean shouldBeInitialized(MemberName paramMemberName)
/*     */   {
/* 307 */     switch (paramMemberName.getReferenceKind()) {
/*     */     case 2: case 4: 
/*     */     case 6: 
/*     */     case 8: 
/*     */       break;
/*     */     case 3: case 5: 
/*     */     case 7: 
/*     */     default: 
/* 315 */       return false;
/*     */     }
/* 317 */     Class localClass = paramMemberName.getDeclaringClass();
/* 318 */     if ((localClass == ValueConversions.class) || (localClass == MethodHandleImpl.class) || (localClass == Invokers.class))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 323 */       return false;
/*     */     }
/* 325 */     if ((VerifyAccess.isSamePackage(MethodHandle.class, localClass)) || 
/* 326 */       (VerifyAccess.isSamePackage(ValueConversions.class, localClass)))
/*     */     {
/*     */ 
/* 329 */       if (MethodHandleStatics.UNSAFE.shouldBeInitialized(localClass)) {
/* 330 */         MethodHandleStatics.UNSAFE.ensureClassInitialized(localClass);
/*     */       }
/* 332 */       return false;
/*     */     }
/* 334 */     return MethodHandleStatics.UNSAFE.shouldBeInitialized(localClass);
/*     */   }
/*     */   
/*     */   private static class EnsureInitialized extends ClassValue<WeakReference<Thread>>
/*     */   {
/*     */     protected WeakReference<Thread> computeValue(Class<?> paramClass) {
/* 340 */       MethodHandleStatics.UNSAFE.ensureClassInitialized(paramClass);
/* 341 */       if (MethodHandleStatics.UNSAFE.shouldBeInitialized(paramClass))
/*     */       {
/*     */ 
/* 344 */         return new WeakReference(Thread.currentThread()); }
/* 345 */       return null; }
/*     */     
/* 347 */     static final EnsureInitialized INSTANCE = new EnsureInitialized();
/*     */   }
/*     */   
/*     */   private void ensureInitialized() {
/* 351 */     if (checkInitialized(this.member))
/*     */     {
/* 353 */       if (this.member.isField()) {
/* 354 */         updateForm(preparedFieldLambdaForm(this.member));
/*     */       } else
/* 356 */         updateForm(preparedLambdaForm(this.member)); }
/*     */   }
/*     */   
/*     */   private static boolean checkInitialized(MemberName paramMemberName) {
/* 360 */     Class localClass = paramMemberName.getDeclaringClass();
/* 361 */     WeakReference localWeakReference = (WeakReference)EnsureInitialized.INSTANCE.get(localClass);
/* 362 */     if (localWeakReference == null) {
/* 363 */       return true;
/*     */     }
/* 365 */     Thread localThread = (Thread)localWeakReference.get();
/*     */     
/* 367 */     if (localThread == Thread.currentThread())
/*     */     {
/* 369 */       if (MethodHandleStatics.UNSAFE.shouldBeInitialized(localClass))
/*     */       {
/* 371 */         return false;
/*     */       }
/*     */     } else {
/* 374 */       MethodHandleStatics.UNSAFE.ensureClassInitialized(localClass);
/*     */     }
/* 376 */     assert (!MethodHandleStatics.UNSAFE.shouldBeInitialized(localClass));
/*     */     
/* 378 */     EnsureInitialized.INSTANCE.remove(localClass);
/* 379 */     return true;
/*     */   }
/*     */   
/*     */   static void ensureInitialized(Object paramObject) {
/* 383 */     ((DirectMethodHandle)paramObject).ensureInitialized();
/*     */   }
/*     */   
/*     */   static class Special extends DirectMethodHandle
/*     */   {
/*     */     private Special(MethodType paramMethodType, LambdaForm paramLambdaForm, MemberName paramMemberName) {
/* 389 */       super(paramLambdaForm, paramMemberName, null);
/*     */     }
/*     */     
/*     */     boolean isInvokeSpecial() {
/* 393 */       return true;
/*     */     }
/*     */     
/*     */     MethodHandle viewAsType(MethodType paramMethodType) {
/* 397 */       return new Special(paramMethodType, this.form, this.member);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Constructor extends DirectMethodHandle
/*     */   {
/*     */     final MemberName initMethod;
/*     */     final Class<?> instanceClass;
/*     */     
/*     */     private Constructor(MethodType paramMethodType, LambdaForm paramLambdaForm, MemberName paramMemberName1, MemberName paramMemberName2, Class<?> paramClass)
/*     */     {
/* 408 */       super(paramLambdaForm, paramMemberName1, null);
/* 409 */       this.initMethod = paramMemberName2;
/* 410 */       this.instanceClass = paramClass;
/* 411 */       assert (paramMemberName2.isResolved());
/*     */     }
/*     */     
/*     */     MethodHandle viewAsType(MethodType paramMethodType) {
/* 415 */       return new Constructor(paramMethodType, this.form, this.member, this.initMethod, this.instanceClass);
/*     */     }
/*     */   }
/*     */   
/*     */   static Object constructorMethod(Object paramObject) {
/* 420 */     Constructor localConstructor = (Constructor)paramObject;
/* 421 */     return localConstructor.initMethod;
/*     */   }
/*     */   
/*     */   static Object allocateInstance(Object paramObject) throws InstantiationException {
/* 425 */     Constructor localConstructor = (Constructor)paramObject;
/* 426 */     return MethodHandleStatics.UNSAFE.allocateInstance(localConstructor.instanceClass);
/*     */   }
/*     */   
/*     */   static class Accessor extends DirectMethodHandle
/*     */   {
/*     */     final Class<?> fieldType;
/*     */     final int fieldOffset;
/*     */     
/*     */     private Accessor(MethodType paramMethodType, LambdaForm paramLambdaForm, MemberName paramMemberName, int paramInt) {
/* 435 */       super(paramLambdaForm, paramMemberName, null);
/* 436 */       this.fieldType = paramMemberName.getFieldType();
/* 437 */       this.fieldOffset = paramInt;
/*     */     }
/*     */     
/*     */     Object checkCast(Object paramObject) {
/* 441 */       return this.fieldType.cast(paramObject);
/*     */     }
/*     */     
/*     */     MethodHandle viewAsType(MethodType paramMethodType) {
/* 445 */       return new Accessor(paramMethodType, this.form, this.member, this.fieldOffset);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @ForceInline
/*     */   static long fieldOffset(Object paramObject)
/*     */   {
/* 453 */     return ((Accessor)paramObject).fieldOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @ForceInline
/*     */   static Object checkBase(Object paramObject)
/*     */   {
/* 465 */     paramObject.getClass();
/* 466 */     return paramObject;
/*     */   }
/*     */   
/*     */   static class StaticAccessor extends DirectMethodHandle
/*     */   {
/*     */     private final Class<?> fieldType;
/*     */     private final Object staticBase;
/*     */     private final long staticOffset;
/*     */     
/*     */     private StaticAccessor(MethodType paramMethodType, LambdaForm paramLambdaForm, MemberName paramMemberName, Object paramObject, long paramLong)
/*     */     {
/* 477 */       super(paramLambdaForm, paramMemberName, null);
/* 478 */       this.fieldType = paramMemberName.getFieldType();
/* 479 */       this.staticBase = paramObject;
/* 480 */       this.staticOffset = paramLong;
/*     */     }
/*     */     
/*     */     Object checkCast(Object paramObject) {
/* 484 */       return this.fieldType.cast(paramObject);
/*     */     }
/*     */     
/*     */     MethodHandle viewAsType(MethodType paramMethodType) {
/* 488 */       return new StaticAccessor(paramMethodType, this.form, this.member, this.staticBase, this.staticOffset);
/*     */     }
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object nullCheck(Object paramObject) {
/* 494 */     paramObject.getClass();
/* 495 */     return paramObject;
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object staticBase(Object paramObject) {
/* 500 */     return ((StaticAccessor)paramObject).staticBase;
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static long staticOffset(Object paramObject) {
/* 505 */     return ((StaticAccessor)paramObject).staticOffset;
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object checkCast(Object paramObject1, Object paramObject2) {
/* 510 */     return ((DirectMethodHandle)paramObject1).checkCast(paramObject2);
/*     */   }
/*     */   
/*     */   Object checkCast(Object paramObject) {
/* 514 */     return this.member.getReturnType().cast(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 519 */   private static byte AF_GETFIELD = 0;
/* 520 */   private static byte AF_PUTFIELD = 1;
/* 521 */   private static byte AF_GETSTATIC = 2;
/* 522 */   private static byte AF_PUTSTATIC = 3;
/* 523 */   private static byte AF_GETSTATIC_INIT = 4;
/* 524 */   private static byte AF_PUTSTATIC_INIT = 5;
/* 525 */   private static byte AF_LIMIT = 6;
/*     */   
/*     */ 
/*     */ 
/* 529 */   private static int FT_LAST_WRAPPER = Wrapper.values().length - 1;
/* 530 */   private static int FT_UNCHECKED_REF = Wrapper.OBJECT.ordinal();
/* 531 */   private static int FT_CHECKED_REF = FT_LAST_WRAPPER + 1;
/* 532 */   private static int FT_LIMIT = FT_LAST_WRAPPER + 2;
/*     */   
/* 534 */   private static int afIndex(byte paramByte, boolean paramBoolean, int paramInt) { return paramByte * FT_LIMIT * 2 + (paramBoolean ? FT_LIMIT : 0) + paramInt; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 539 */   private static final LambdaForm[] ACCESSOR_FORMS = new LambdaForm[afIndex(AF_LIMIT, false, 0)];
/*     */   
/* 541 */   private static int ftypeKind(Class<?> paramClass) { if (paramClass.isPrimitive())
/* 542 */       return Wrapper.forPrimitiveType(paramClass).ordinal();
/* 543 */     if (VerifyType.isNullReferenceConversion(Object.class, paramClass)) {
/* 544 */       return FT_UNCHECKED_REF;
/*     */     }
/* 546 */     return FT_CHECKED_REF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static LambdaForm preparedFieldLambdaForm(MemberName paramMemberName)
/*     */   {
/* 555 */     Class localClass = paramMemberName.getFieldType();
/* 556 */     boolean bool = paramMemberName.isVolatile();
/*     */     int i;
/* 558 */     switch (paramMemberName.getReferenceKind()) {
/* 559 */     case 1:  i = AF_GETFIELD; break;
/* 560 */     case 3:  i = AF_PUTFIELD; break;
/* 561 */     case 2:  i = AF_GETSTATIC; break;
/* 562 */     case 4:  i = AF_PUTSTATIC; break;
/* 563 */     default:  throw new InternalError(paramMemberName.toString()); }
/*     */     byte b;
/* 565 */     if (shouldBeInitialized(paramMemberName))
/*     */     {
/* 567 */       preparedFieldLambdaForm(i, bool, localClass);
/* 568 */       assert (AF_GETSTATIC_INIT - AF_GETSTATIC == AF_PUTSTATIC_INIT - AF_PUTSTATIC);
/*     */       
/* 570 */       b = (byte)(i + (AF_GETSTATIC_INIT - AF_GETSTATIC));
/*     */     }
/* 572 */     LambdaForm localLambdaForm = preparedFieldLambdaForm(b, bool, localClass);
/* 573 */     maybeCompile(localLambdaForm, paramMemberName);
/* 574 */     if ((!$assertionsDisabled) && 
/* 575 */       (!localLambdaForm.methodType().dropParameterTypes(0, 1).equals(paramMemberName.getInvocationType().basicType())))
/* 576 */       throw new AssertionError(Arrays.asList(new Object[] { paramMemberName, paramMemberName.getInvocationType().basicType(), localLambdaForm, localLambdaForm.methodType() }));
/* 577 */     return localLambdaForm;
/*     */   }
/*     */   
/* 580 */   private static LambdaForm preparedFieldLambdaForm(byte paramByte, boolean paramBoolean, Class<?> paramClass) { int i = afIndex(paramByte, paramBoolean, ftypeKind(paramClass));
/* 581 */     LambdaForm localLambdaForm = ACCESSOR_FORMS[i];
/* 582 */     if (localLambdaForm != null) return localLambdaForm;
/* 583 */     localLambdaForm = makePreparedFieldLambdaForm(paramByte, paramBoolean, ftypeKind(paramClass));
/* 584 */     ACCESSOR_FORMS[i] = localLambdaForm;
/* 585 */     return localLambdaForm;
/*     */   }
/*     */   
/*     */   private static LambdaForm makePreparedFieldLambdaForm(byte paramByte, boolean paramBoolean, int paramInt) {
/* 589 */     int i = (paramByte & 0x1) == (AF_GETFIELD & 0x1) ? 1 : 0;
/* 590 */     int j = paramByte >= AF_GETSTATIC ? 1 : 0;
/* 591 */     int k = paramByte >= AF_GETSTATIC_INIT ? 1 : 0;
/* 592 */     int m = paramInt == FT_CHECKED_REF ? 1 : 0;
/* 593 */     Wrapper localWrapper = m != 0 ? Wrapper.OBJECT : Wrapper.values()[paramInt];
/* 594 */     Class localClass = localWrapper.primitiveType();
/* 595 */     if (!$assertionsDisabled) if (ftypeKind(m != 0 ? String.class : localClass) != paramInt) throw new AssertionError();
/* 596 */     String str1 = localWrapper.primitiveSimpleName();
/* 597 */     String str2 = Character.toUpperCase(str1.charAt(0)) + str1.substring(1);
/* 598 */     if (paramBoolean) str2 = str2 + "Volatile";
/* 599 */     String str3 = i != 0 ? "get" : "put";
/* 600 */     String str4 = str3 + str2;
/*     */     MethodType localMethodType1;
/* 602 */     if (i != 0) {
/* 603 */       localMethodType1 = MethodType.methodType(localClass, Object.class, new Class[] { Long.TYPE });
/*     */     } else
/* 605 */       localMethodType1 = MethodType.methodType(Void.TYPE, Object.class, new Class[] { Long.TYPE, localClass });
/* 606 */     MemberName localMemberName = new MemberName(Unsafe.class, str4, localMethodType1, (byte)5);
/*     */     try {
/* 608 */       localMemberName = IMPL_NAMES.resolveOrFail((byte)5, localMemberName, null, NoSuchMethodException.class);
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 610 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 615 */     if (i != 0) {
/* 616 */       localMethodType2 = MethodType.methodType(localClass);
/*     */     } else
/* 618 */       localMethodType2 = MethodType.methodType(Void.TYPE, localClass);
/* 619 */     MethodType localMethodType2 = localMethodType2.basicType();
/* 620 */     if (j == 0) {
/* 621 */       localMethodType2 = localMethodType2.insertParameterTypes(0, new Class[] { Object.class });
/*     */     }
/*     */     
/* 624 */     int n = 1 + localMethodType2.parameterCount();
/*     */     
/* 626 */     int i1 = j != 0 ? -1 : 1;
/*     */     
/* 628 */     int i2 = i != 0 ? -1 : n - 1;
/* 629 */     int i3 = n;
/* 630 */     int i4 = j != 0 ? i3++ : -1;
/* 631 */     int i5 = i3++;
/* 632 */     int i6 = i1 >= 0 ? i3++ : -1;
/* 633 */     int i7 = k != 0 ? i3++ : -1;
/* 634 */     int i8 = (m != 0) && (i == 0) ? i3++ : -1;
/* 635 */     int i9 = i3++;
/* 636 */     int i10 = (m != 0) && (i != 0) ? i3++ : -1;
/* 637 */     int i11 = i3 - 1;
/* 638 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(i3 - n, localMethodType2.invokerType());
/* 639 */     if (k != 0)
/* 640 */       arrayOfName[i7] = new LambdaForm.Name(Lazy.NF_ensureInitialized, new Object[] { arrayOfName[0] });
/* 641 */     if ((m != 0) && (i == 0))
/* 642 */       arrayOfName[i8] = new LambdaForm.Name(Lazy.NF_checkCast, new Object[] { arrayOfName[0], arrayOfName[i2] });
/* 643 */     Object[] arrayOfObject = new Object[1 + localMethodType1.parameterCount()];
/* 644 */     if (!$assertionsDisabled) if (arrayOfObject.length != (i != 0 ? 3 : 4)) throw new AssertionError();
/* 645 */     arrayOfObject[0] = MethodHandleStatics.UNSAFE;
/* 646 */     if (j != 0) {
/* 647 */       arrayOfObject[1] = (arrayOfName[i4] = new LambdaForm.Name(Lazy.NF_staticBase, new Object[] { arrayOfName[0] }));
/* 648 */       arrayOfObject[2] = (arrayOfName[i5] = new LambdaForm.Name(Lazy.NF_staticOffset, new Object[] { arrayOfName[0] }));
/*     */     } else {
/* 650 */       arrayOfObject[1] = (arrayOfName[i6] = new LambdaForm.Name(Lazy.NF_checkBase, new Object[] { arrayOfName[i1] }));
/* 651 */       arrayOfObject[2] = (arrayOfName[i5] = new LambdaForm.Name(Lazy.NF_fieldOffset, new Object[] { arrayOfName[0] }));
/*     */     }
/* 653 */     if (i == 0)
/* 654 */       arrayOfObject[3] = (m != 0 ? arrayOfName[i8] : arrayOfName[i2]);
/*     */     Object localObject2;
/* 656 */     for (localObject2 : arrayOfObject) assert (localObject2 != null);
/* 657 */     arrayOfName[i9] = new LambdaForm.Name(localMemberName, arrayOfObject);
/* 658 */     if ((m != 0) && (i != 0))
/* 659 */       arrayOfName[i10] = new LambdaForm.Name(Lazy.NF_checkCast, new Object[] { arrayOfName[0], arrayOfName[i9] });
/* 660 */     for (localObject2 : arrayOfName) assert (localObject2 != null);
/* 661 */     ??? = j != 0 ? "Static" : "Field";
/* 662 */     String str5 = str4 + (String)???;
/* 663 */     if (m != 0) str5 = str5 + "Cast";
/* 664 */     if (k != 0) str5 = str5 + "Init";
/* 665 */     return new LambdaForm(str5, n, arrayOfName, i11);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class Lazy
/*     */   {
/*     */     static final LambdaForm.NamedFunction NF_internalMemberName;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_internalMemberNameEnsureInit;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_ensureInitialized;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_fieldOffset;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_checkBase;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_staticBase;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_staticOffset;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_checkCast;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_allocateInstance;
/*     */     
/*     */ 
/*     */     static final LambdaForm.NamedFunction NF_constructorMethod;
/*     */     
/*     */ 
/*     */     static
/*     */     {
/*     */       try
/*     */       {
/* 706 */         LambdaForm.NamedFunction[] arrayOfNamedFunction1 = { NF_internalMemberName = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("internalMemberName", new Class[] { Object.class })), NF_internalMemberNameEnsureInit = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("internalMemberNameEnsureInit", new Class[] { Object.class })), NF_ensureInitialized = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("ensureInitialized", new Class[] { Object.class })), NF_fieldOffset = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("fieldOffset", new Class[] { Object.class })), NF_checkBase = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("checkBase", new Class[] { Object.class })), NF_staticBase = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("staticBase", new Class[] { Object.class })), NF_staticOffset = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("staticOffset", new Class[] { Object.class })), NF_checkCast = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("checkCast", new Class[] { Object.class, Object.class })), NF_allocateInstance = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("allocateInstance", new Class[] { Object.class })), NF_constructorMethod = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("constructorMethod", new Class[] { Object.class })) };
/*     */         
/* 708 */         for (LambdaForm.NamedFunction localNamedFunction : arrayOfNamedFunction1)
/*     */         {
/* 710 */           assert (InvokerBytecodeGenerator.isStaticallyInvocable(localNamedFunction.member)) : localNamedFunction;
/* 711 */           localNamedFunction.resolve();
/*     */         }
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 714 */         throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/DirectMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */