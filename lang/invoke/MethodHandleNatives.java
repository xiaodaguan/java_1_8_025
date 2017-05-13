/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MethodHandleNatives
/*     */ {
/*     */   static final boolean COUNT_GWT;
/*     */   
/* 192 */   static boolean refKindIsValid(int paramInt) { return (paramInt > 0) && (paramInt < 10); }
/*     */   
/*     */   static boolean refKindIsField(byte paramByte) {
/* 195 */     assert (refKindIsValid(paramByte));
/* 196 */     return paramByte <= 4;
/*     */   }
/*     */   
/* 199 */   static boolean refKindIsGetter(byte paramByte) { assert (refKindIsValid(paramByte));
/* 200 */     return paramByte <= 2;
/*     */   }
/*     */   
/* 203 */   static boolean refKindIsSetter(byte paramByte) { return (refKindIsField(paramByte)) && (!refKindIsGetter(paramByte)); }
/*     */   
/*     */   static boolean refKindIsMethod(byte paramByte) {
/* 206 */     return (!refKindIsField(paramByte)) && (paramByte != 8);
/*     */   }
/*     */   
/* 209 */   static boolean refKindIsConstructor(byte paramByte) { return paramByte == 8; }
/*     */   
/*     */   static boolean refKindHasReceiver(byte paramByte) {
/* 212 */     assert (refKindIsValid(paramByte));
/* 213 */     return (paramByte & 0x1) != 0;
/*     */   }
/*     */   
/* 216 */   static boolean refKindIsStatic(byte paramByte) { return (!refKindHasReceiver(paramByte)) && (paramByte != 8); }
/*     */   
/*     */   static boolean refKindDoesDispatch(byte paramByte) {
/* 219 */     assert (refKindIsValid(paramByte));
/* 220 */     return (paramByte == 5) || (paramByte == 9);
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
/*     */   static String refKindName(byte paramByte)
/*     */   {
/* 235 */     assert (refKindIsValid(paramByte));
/* 236 */     switch (paramByte) {
/* 237 */     case 1:  return "getField";
/* 238 */     case 2:  return "getStatic";
/* 239 */     case 3:  return "putField";
/* 240 */     case 4:  return "putStatic";
/* 241 */     case 5:  return "invokeVirtual";
/* 242 */     case 6:  return "invokeStatic";
/* 243 */     case 7:  return "invokeSpecial";
/* 244 */     case 8:  return "newInvokeSpecial";
/* 245 */     case 9:  return "invokeInterface"; }
/* 246 */     return "REF_???";
/*     */   }
/*     */   
/*     */ 
/*     */   static boolean verifyConstants()
/*     */   {
/* 252 */     Object[] arrayOfObject = { null };
/* 253 */     for (int i = 0;; i++) {
/* 254 */       arrayOfObject[0] = null;
/* 255 */       int j = getNamedCon(i, arrayOfObject);
/* 256 */       if (arrayOfObject[0] == null) break;
/* 257 */       String str1 = (String)arrayOfObject[0];
/*     */       try {
/* 259 */         Field localField = Constants.class.getDeclaredField(str1);
/* 260 */         int k = localField.getInt(null);
/* 261 */         if (k != j) {
/* 262 */           String str3 = str1 + ": JVM has " + j + " while Java has " + k;
/* 263 */           if (str1.equals("CONV_OP_LIMIT")) {
/* 264 */             System.err.println("warning: " + str3);
/*     */           }
/*     */           else
/* 267 */             throw new InternalError(str3);
/*     */         }
/* 269 */       } catch (NoSuchFieldException|IllegalAccessException localNoSuchFieldException) { String str2 = str1 + ": JVM has " + j + " which Java does not define";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 275 */     return true;
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*  76 */     registerNatives();
/*  77 */     COUNT_GWT = getConstant(4) != 0;
/*     */     
/*     */ 
/*  80 */     MethodHandleImpl.initStatics();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 230 */     for (byte b = 1; b < 10; b = (byte)(b + 1)) {
/* 231 */       if (!$assertionsDisabled) { if (refKindHasReceiver(b) != ((1 << b & 0x2AA) != 0)) { throw new AssertionError(b);
/*     */         }
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 278 */     assert (verifyConstants());
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
/*     */   static MemberName linkCallSite(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object[] paramArrayOfObject)
/*     */   {
/* 292 */     MethodHandle localMethodHandle = (MethodHandle)paramObject2;
/* 293 */     Class localClass = (Class)paramObject1;
/* 294 */     String str = paramObject3.toString().intern();
/* 295 */     MethodType localMethodType = (MethodType)paramObject4;
/* 296 */     CallSite localCallSite = CallSite.makeSite(localMethodHandle, str, localMethodType, paramObject5, localClass);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 301 */     if ((localCallSite instanceof ConstantCallSite)) {
/* 302 */       paramArrayOfObject[0] = localCallSite.dynamicInvoker();
/* 303 */       return Invokers.linkToTargetMethod(localMethodType);
/*     */     }
/* 305 */     paramArrayOfObject[0] = localCallSite;
/* 306 */     return Invokers.linkToCallSiteMethod(localMethodType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static MethodType findMethodHandleType(Class<?> paramClass, Class<?>[] paramArrayOfClass)
/*     */   {
/* 314 */     return MethodType.makeImpl(paramClass, paramArrayOfClass, true);
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
/*     */   static MemberName linkMethod(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject, Object[] paramArrayOfObject)
/*     */   {
/* 387 */     if (!MethodHandleStatics.TRACE_METHOD_LINKAGE)
/* 388 */       return linkMethodImpl(paramClass1, paramInt, paramClass2, paramString, paramObject, paramArrayOfObject);
/* 389 */     return linkMethodTracing(paramClass1, paramInt, paramClass2, paramString, paramObject, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   static MemberName linkMethodImpl(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject, Object[] paramArrayOfObject)
/*     */   {
/*     */     try {
/* 395 */       if ((paramClass2 == MethodHandle.class) && (paramInt == 5)) {
/* 396 */         return Invokers.methodHandleInvokeLinkerMethod(paramString, fixMethodType(paramClass1, paramObject), paramArrayOfObject);
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 399 */       if ((localThrowable instanceof LinkageError)) {
/* 400 */         throw ((LinkageError)localThrowable);
/*     */       }
/* 402 */       throw new LinkageError(localThrowable.getMessage(), localThrowable);
/*     */     }
/* 404 */     throw new LinkageError("no such method " + paramClass2.getName() + "." + paramString + paramObject);
/*     */   }
/*     */   
/* 407 */   private static MethodType fixMethodType(Class<?> paramClass, Object paramObject) { if ((paramObject instanceof MethodType)) {
/* 408 */       return (MethodType)paramObject;
/*     */     }
/* 410 */     return MethodType.fromMethodDescriptorString((String)paramObject, paramClass.getClassLoader());
/*     */   }
/*     */   
/*     */ 
/*     */   static MemberName linkMethodTracing(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject, Object[] paramArrayOfObject)
/*     */   {
/* 416 */     System.out.println("linkMethod " + paramClass2.getName() + "." + paramString + paramObject + "/" + 
/* 417 */       Integer.toHexString(paramInt));
/*     */     try {
/* 419 */       MemberName localMemberName = linkMethodImpl(paramClass1, paramInt, paramClass2, paramString, paramObject, paramArrayOfObject);
/* 420 */       System.out.println("linkMethod => " + localMemberName + " + " + paramArrayOfObject[0]);
/* 421 */       return localMemberName;
/*     */     } catch (Throwable localThrowable) {
/* 423 */       System.out.println("linkMethod => throw " + localThrowable);
/* 424 */       throw localThrowable;
/*     */     }
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
/*     */   static MethodHandle linkMethodHandleConstant(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/* 440 */       MethodHandles.Lookup localLookup = MethodHandles.Lookup.IMPL_LOOKUP.in(paramClass1);
/* 441 */       assert (refKindIsValid(paramInt));
/* 442 */       return localLookup.linkMethodHandleConstant((byte)paramInt, paramClass2, paramString, paramObject);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 444 */       localObject = localIllegalAccessException.getCause();
/* 445 */       if ((localObject instanceof AbstractMethodError)) {
/* 446 */         throw ((AbstractMethodError)localObject);
/*     */       }
/* 448 */       IllegalAccessError localIllegalAccessError = new IllegalAccessError(localIllegalAccessException.getMessage());
/* 449 */       throw initCauseFrom(localIllegalAccessError, localIllegalAccessException);
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {
/* 452 */       localObject = new NoSuchMethodError(localNoSuchMethodException.getMessage());
/* 453 */       throw initCauseFrom((Error)localObject, localNoSuchMethodException);
/*     */     } catch (NoSuchFieldException localNoSuchFieldException) {
/* 455 */       localObject = new NoSuchFieldError(localNoSuchFieldException.getMessage());
/* 456 */       throw initCauseFrom((Error)localObject, localNoSuchFieldException);
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 458 */       Object localObject = new IncompatibleClassChangeError();
/* 459 */       throw initCauseFrom((Error)localObject, localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Error initCauseFrom(Error paramError, Exception paramException)
/*     */   {
/* 468 */     Throwable localThrowable = paramException.getCause();
/* 469 */     if (paramError.getClass().isInstance(localThrowable))
/* 470 */       return (Error)localThrowable;
/* 471 */     paramError.initCause(localThrowable == null ? paramException : localThrowable);
/* 472 */     return paramError;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isCallerSensitive(MemberName paramMemberName)
/*     */   {
/* 481 */     if (!paramMemberName.isInvocable()) { return false;
/*     */     }
/* 483 */     return (paramMemberName.isCallerSensitive()) || (canBeCalledVirtual(paramMemberName));
/*     */   }
/*     */   
/*     */   static boolean canBeCalledVirtual(MemberName paramMemberName) {
/* 487 */     assert (paramMemberName.isInvocable());
/* 488 */     Class localClass = paramMemberName.getDeclaringClass();
/* 489 */     switch (paramMemberName.getName()) {
/*     */     case "checkMemberAccess": 
/* 491 */       return canBeCalledVirtual(paramMemberName, SecurityManager.class);
/*     */     case "getContextClassLoader": 
/* 493 */       return canBeCalledVirtual(paramMemberName, Thread.class);
/*     */     }
/* 495 */     return false;
/*     */   }
/*     */   
/*     */   static boolean canBeCalledVirtual(MemberName paramMemberName, Class<?> paramClass) {
/* 499 */     Class localClass = paramMemberName.getDeclaringClass();
/* 500 */     if (localClass == paramClass) return true;
/* 501 */     if ((paramMemberName.isStatic()) || (paramMemberName.isPrivate())) { return false;
/*     */     }
/* 503 */     return (paramClass.isAssignableFrom(localClass)) || (localClass.isInterface());
/*     */   }
/*     */   
/*     */   static native void init(MemberName paramMemberName, Object paramObject);
/*     */   
/*     */   static native void expand(MemberName paramMemberName);
/*     */   
/*     */   static native MemberName resolve(MemberName paramMemberName, Class<?> paramClass)
/*     */     throws LinkageError;
/*     */   
/*     */   static native int getMembers(Class<?> paramClass1, String paramString1, String paramString2, int paramInt1, Class<?> paramClass2, int paramInt2, MemberName[] paramArrayOfMemberName);
/*     */   
/*     */   static native long objectFieldOffset(MemberName paramMemberName);
/*     */   
/*     */   static native long staticFieldOffset(MemberName paramMemberName);
/*     */   
/*     */   static native Object staticFieldBase(MemberName paramMemberName);
/*     */   
/*     */   static native Object getMemberVMInfo(MemberName paramMemberName);
/*     */   
/*     */   static native int getConstant(int paramInt);
/*     */   
/*     */   static native void setCallSiteTargetNormal(CallSite paramCallSite, MethodHandle paramMethodHandle);
/*     */   
/*     */   static native void setCallSiteTargetVolatile(CallSite paramCallSite, MethodHandle paramMethodHandle);
/*     */   
/*     */   private static native void registerNatives();
/*     */   
/*     */   private static native int getNamedCon(int paramInt, Object[] paramArrayOfObject);
/*     */   
/*     */   static class Constants
/*     */   {
/*     */     static final int GC_COUNT_GWT = 4;
/*     */     static final int GC_LAMBDA_SUPPORT = 5;
/*     */     static final int MN_IS_METHOD = 65536;
/*     */     static final int MN_IS_CONSTRUCTOR = 131072;
/*     */     static final int MN_IS_FIELD = 262144;
/*     */     static final int MN_IS_TYPE = 524288;
/*     */     static final int MN_CALLER_SENSITIVE = 1048576;
/*     */     static final int MN_REFERENCE_KIND_SHIFT = 24;
/*     */     static final int MN_REFERENCE_KIND_MASK = 15;
/*     */     static final int MN_SEARCH_SUPERCLASSES = 1048576;
/*     */     static final int MN_SEARCH_INTERFACES = 2097152;
/*     */     static final int T_BOOLEAN = 4;
/*     */     static final int T_CHAR = 5;
/*     */     static final int T_FLOAT = 6;
/*     */     static final int T_DOUBLE = 7;
/*     */     static final int T_BYTE = 8;
/*     */     static final int T_SHORT = 9;
/*     */     static final int T_INT = 10;
/*     */     static final int T_LONG = 11;
/*     */     static final int T_OBJECT = 12;
/*     */     static final int T_VOID = 14;
/*     */     static final int T_ILLEGAL = 99;
/*     */     static final byte CONSTANT_Utf8 = 1;
/*     */     static final byte CONSTANT_Integer = 3;
/*     */     static final byte CONSTANT_Float = 4;
/*     */     static final byte CONSTANT_Long = 5;
/*     */     static final byte CONSTANT_Double = 6;
/*     */     static final byte CONSTANT_Class = 7;
/*     */     static final byte CONSTANT_String = 8;
/*     */     static final byte CONSTANT_Fieldref = 9;
/*     */     static final byte CONSTANT_Methodref = 10;
/*     */     static final byte CONSTANT_InterfaceMethodref = 11;
/*     */     static final byte CONSTANT_NameAndType = 12;
/*     */     static final byte CONSTANT_MethodHandle = 15;
/*     */     static final byte CONSTANT_MethodType = 16;
/*     */     static final byte CONSTANT_InvokeDynamic = 18;
/*     */     static final byte CONSTANT_LIMIT = 19;
/*     */     static final char ACC_PUBLIC = '\001';
/*     */     static final char ACC_PRIVATE = '\002';
/*     */     static final char ACC_PROTECTED = '\004';
/*     */     static final char ACC_STATIC = '\b';
/*     */     static final char ACC_FINAL = '\020';
/*     */     static final char ACC_SYNCHRONIZED = ' ';
/*     */     static final char ACC_VOLATILE = '@';
/*     */     static final char ACC_TRANSIENT = '';
/*     */     static final char ACC_NATIVE = 'Ā';
/*     */     static final char ACC_INTERFACE = 'Ȁ';
/*     */     static final char ACC_ABSTRACT = 'Ѐ';
/*     */     static final char ACC_STRICT = 'ࠀ';
/*     */     static final char ACC_SYNTHETIC = 'က';
/*     */     static final char ACC_ANNOTATION = ' ';
/*     */     static final char ACC_ENUM = '䀀';
/*     */     static final char ACC_SUPER = ' ';
/*     */     static final char ACC_BRIDGE = '@';
/*     */     static final char ACC_VARARGS = '';
/*     */     static final byte REF_NONE = 0;
/*     */     static final byte REF_getField = 1;
/*     */     static final byte REF_getStatic = 2;
/*     */     static final byte REF_putField = 3;
/*     */     static final byte REF_putStatic = 4;
/*     */     static final byte REF_invokeVirtual = 5;
/*     */     static final byte REF_invokeStatic = 6;
/*     */     static final byte REF_invokeSpecial = 7;
/*     */     static final byte REF_newInvokeSpecial = 8;
/*     */     static final byte REF_invokeInterface = 9;
/*     */     static final byte REF_LIMIT = 10;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandleNatives.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */