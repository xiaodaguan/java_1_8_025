/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.annotation.Retention;
/*      */ import java.lang.annotation.RetentionPolicy;
/*      */ import java.lang.annotation.Target;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import sun.invoke.util.ValueConversions;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MethodHandle
/*      */ {
/*      */   private final MethodType type;
/*      */   final LambdaForm form;
/*      */   MethodHandle asTypeCache;
/*      */   private static final LambdaForm.NamedFunction NF_reinvokerTarget;
/*      */   private static final long FORM_OFFSET;
/*      */   
/*      */   public MethodType type()
/*      */   {
/*  448 */     return this.type;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm)
/*      */   {
/*  458 */     paramMethodType.getClass();
/*  459 */     paramLambdaForm.getClass();
/*  460 */     this.type = paramMethodType;
/*  461 */     this.form = paramLambdaForm;
/*      */     
/*  463 */     paramLambdaForm.prepare();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   public final native Object invokeExact(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   public final native Object invoke(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   final native Object invokeBasic(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   static native Object linkToVirtual(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   static native Object linkToStatic(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   static native Object linkToSpecial(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @PolymorphicSignature
/*      */   static native Object linkToInterface(Object... paramVarArgs)
/*      */     throws Throwable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object invokeWithArguments(Object... paramVarArgs)
/*      */     throws Throwable
/*      */   {
/*  628 */     int i = paramVarArgs == null ? 0 : paramVarArgs.length;
/*      */     
/*  630 */     MethodType localMethodType = type();
/*  631 */     if ((localMethodType.parameterCount() != i) || (isVarargsCollector()))
/*      */     {
/*  633 */       return asType(MethodType.genericMethodType(i)).invokeWithArguments(paramVarArgs);
/*      */     }
/*  635 */     MethodHandle localMethodHandle = localMethodType.invokers().varargsInvoker();
/*  636 */     return localMethodHandle.invokeExact(this, paramVarArgs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object invokeWithArguments(List<?> paramList)
/*      */     throws Throwable
/*      */   {
/*  658 */     return invokeWithArguments(paramList.toArray());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodHandle asType(MethodType paramMethodType)
/*      */   {
/*  762 */     if (paramMethodType == this.type) {
/*  763 */       return this;
/*      */     }
/*      */     
/*  766 */     MethodHandle localMethodHandle = this.asTypeCache;
/*  767 */     if ((localMethodHandle != null) && (paramMethodType == localMethodHandle.type)) {
/*  768 */       return localMethodHandle;
/*      */     }
/*  770 */     return asTypeUncached(paramMethodType);
/*      */   }
/*      */   
/*      */   MethodHandle asTypeUncached(MethodType paramMethodType)
/*      */   {
/*  775 */     if (!this.type.isConvertibleTo(paramMethodType))
/*  776 */       throw new WrongMethodTypeException("cannot convert " + this + " to " + paramMethodType);
/*  777 */     return this.asTypeCache = convertArguments(paramMethodType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodHandle asSpreader(Class<?> paramClass, int paramInt)
/*      */   {
/*  870 */     asSpreaderChecks(paramClass, paramInt);
/*  871 */     int i = this.type.parameterCount() - paramInt;
/*  872 */     return MethodHandleImpl.makeSpreadArguments(this, paramClass, i, paramInt);
/*      */   }
/*      */   
/*      */   private void asSpreaderChecks(Class<?> paramClass, int paramInt) {
/*  876 */     spreadArrayChecks(paramClass, paramInt);
/*  877 */     int i = type().parameterCount();
/*  878 */     if ((i < paramInt) || (paramInt < 0))
/*  879 */       throw MethodHandleStatics.newIllegalArgumentException("bad spread array length");
/*  880 */     if ((paramClass != Object[].class) && (paramInt != 0)) {
/*  881 */       int j = 0;
/*  882 */       Class localClass = paramClass.getComponentType();
/*  883 */       for (int k = i - paramInt; k < i; k++) {
/*  884 */         if (!MethodType.canConvert(localClass, type().parameterType(k))) {
/*  885 */           j = 1;
/*  886 */           break;
/*      */         }
/*      */       }
/*  889 */       if (j != 0) {
/*  890 */         ArrayList localArrayList = new ArrayList(type().parameterList());
/*  891 */         for (int m = i - paramInt; m < i; m++) {
/*  892 */           localArrayList.set(m, localClass);
/*      */         }
/*      */         
/*  895 */         asType(MethodType.methodType(type().returnType(), localArrayList));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void spreadArrayChecks(Class<?> paramClass, int paramInt) {
/*  901 */     Class localClass = paramClass.getComponentType();
/*  902 */     if (localClass == null)
/*  903 */       throw MethodHandleStatics.newIllegalArgumentException("not an array type", paramClass);
/*  904 */     if ((paramInt & 0x7F) != paramInt) {
/*  905 */       if ((paramInt & 0xFF) != paramInt)
/*  906 */         throw MethodHandleStatics.newIllegalArgumentException("array length is not legal", Integer.valueOf(paramInt));
/*  907 */       assert (paramInt >= 128);
/*  908 */       if ((localClass == Long.TYPE) || (localClass == Double.TYPE))
/*      */       {
/*  910 */         throw MethodHandleStatics.newIllegalArgumentException("array length is not legal for long[] or double[]", Integer.valueOf(paramInt));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodHandle asCollector(Class<?> paramClass, int paramInt)
/*      */   {
/*  986 */     asCollectorChecks(paramClass, paramInt);
/*  987 */     int i = type().parameterCount() - 1;
/*  988 */     MethodHandle localMethodHandle1 = this;
/*  989 */     if (paramClass != type().parameterType(i))
/*  990 */       localMethodHandle1 = convertArguments(type().changeParameterType(i, paramClass));
/*  991 */     MethodHandle localMethodHandle2 = ValueConversions.varargsArray(paramClass, paramInt);
/*  992 */     return MethodHandles.collectArguments(localMethodHandle1, i, localMethodHandle2);
/*      */   }
/*      */   
/*      */   private boolean asCollectorChecks(Class<?> paramClass, int paramInt)
/*      */   {
/*  997 */     spreadArrayChecks(paramClass, paramInt);
/*  998 */     int i = type().parameterCount();
/*  999 */     if (i != 0) {
/* 1000 */       Class localClass = type().parameterType(i - 1);
/* 1001 */       if (localClass == paramClass) return true;
/* 1002 */       if (localClass.isAssignableFrom(paramClass)) return false;
/*      */     }
/* 1004 */     throw MethodHandleStatics.newIllegalArgumentException("array type not assignable to trailing argument", this, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodHandle asVarargsCollector(Class<?> paramClass)
/*      */   {
/* 1158 */     Class localClass = paramClass.getComponentType();
/* 1159 */     boolean bool = asCollectorChecks(paramClass, 0);
/* 1160 */     if ((isVarargsCollector()) && (bool))
/* 1161 */       return this;
/* 1162 */     return MethodHandleImpl.makeVarargsCollector(this, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isVarargsCollector()
/*      */   {
/* 1181 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodHandle asFixedArity()
/*      */   {
/* 1228 */     assert (!isVarargsCollector());
/* 1229 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodHandle bindTo(Object paramObject)
/*      */   {
/* 1262 */     MethodType localMethodType = type();
/* 1263 */     Class localClass; if ((localMethodType.parameterCount() == 0) || 
/* 1264 */       ((localClass = localMethodType.parameterType(0)).isPrimitive()))
/* 1265 */       throw MethodHandleStatics.newIllegalArgumentException("no leading reference parameter", paramObject);
/* 1266 */     paramObject = localClass.cast(paramObject);
/* 1267 */     return bindReceiver(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1287 */     if (MethodHandleStatics.DEBUG_METHOD_HANDLE_NAMES) return debugString();
/* 1288 */     return standardString();
/*      */   }
/*      */   
/* 1291 */   String standardString() { return "MethodHandle" + this.type; }
/*      */   
/*      */   String debugString() {
/* 1294 */     return standardString() + "/LF=" + internalForm() + internalProperties();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MethodHandle setVarargs(MemberName paramMemberName)
/*      */     throws IllegalAccessException
/*      */   {
/* 1305 */     if (!paramMemberName.isVarargs()) return this;
/* 1306 */     int i = type().parameterCount();
/* 1307 */     if (i != 0) {
/* 1308 */       Class localClass = type().parameterType(i - 1);
/* 1309 */       if (localClass.isArray()) {
/* 1310 */         return MethodHandleImpl.makeVarargsCollector(this, localClass);
/*      */       }
/*      */     }
/* 1313 */     throw paramMemberName.makeAccessException("cannot make variable arity", null);
/*      */   }
/*      */   
/*      */   MethodHandle viewAsType(MethodType paramMethodType)
/*      */   {
/* 1318 */     return MethodHandleImpl.makePairwiseConvert(this, paramMethodType, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   LambdaForm internalForm()
/*      */   {
/* 1325 */     return this.form;
/*      */   }
/*      */   
/*      */   MemberName internalMemberName()
/*      */   {
/* 1330 */     return null;
/*      */   }
/*      */   
/*      */   Class<?> internalCallerClass()
/*      */   {
/* 1335 */     return null;
/*      */   }
/*      */   
/*      */   MethodHandle withInternalMemberName(MemberName paramMemberName)
/*      */   {
/* 1340 */     if (paramMemberName != null)
/* 1341 */       return MethodHandleImpl.makeWrappedMember(this, paramMemberName);
/* 1342 */     if (internalMemberName() == null)
/*      */     {
/* 1344 */       return this;
/*      */     }
/*      */     
/* 1347 */     MethodHandle localMethodHandle = rebind();
/* 1348 */     assert (localMethodHandle.internalMemberName() == null);
/* 1349 */     return localMethodHandle;
/*      */   }
/*      */   
/*      */ 
/*      */   boolean isInvokeSpecial()
/*      */   {
/* 1355 */     return false;
/*      */   }
/*      */   
/*      */   Object internalValues()
/*      */   {
/* 1360 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   Object internalProperties()
/*      */   {
/* 1366 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MethodHandle convertArguments(MethodType paramMethodType)
/*      */   {
/* 1375 */     return MethodHandleImpl.makePairwiseConvert(this, paramMethodType, 1);
/*      */   }
/*      */   
/*      */ 
/*      */   MethodHandle bindArgument(int paramInt, char paramChar, Object paramObject)
/*      */   {
/* 1381 */     return rebind().bindArgument(paramInt, paramChar, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */   MethodHandle bindReceiver(Object paramObject)
/*      */   {
/* 1387 */     return bindArgument(0, 'L', paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MethodHandle bindImmediate(int paramInt, char paramChar, Object paramObject)
/*      */   {
/* 1398 */     assert ((paramInt == 0) && (paramChar == 'L') && ((paramObject instanceof Unsafe)));
/* 1399 */     MethodType localMethodType = this.type.dropParameterTypes(paramInt, paramInt + 1);
/* 1400 */     LambdaForm localLambdaForm = this.form.bindImmediate(paramInt + 1, paramChar, paramObject);
/* 1401 */     return copyWith(localMethodType, localLambdaForm);
/*      */   }
/*      */   
/*      */   MethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm)
/*      */   {
/* 1406 */     throw new InternalError("copyWith: " + getClass());
/*      */   }
/*      */   
/*      */ 
/*      */   MethodHandle dropArguments(MethodType paramMethodType, int paramInt1, int paramInt2)
/*      */   {
/* 1412 */     return rebind().dropArguments(paramMethodType, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */   MethodHandle permuteArguments(MethodType paramMethodType, int[] paramArrayOfInt)
/*      */   {
/* 1418 */     return rebind().permuteArguments(paramMethodType, paramArrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */   MethodHandle rebind()
/*      */   {
/* 1424 */     MethodType localMethodType = type();
/* 1425 */     LambdaForm localLambdaForm = reinvokerForm(this);
/*      */     
/* 1427 */     return BoundMethodHandle.bindSingle(localMethodType, localLambdaForm, this);
/*      */   }
/*      */   
/*      */   MethodHandle reinvokerTarget()
/*      */   {
/* 1432 */     throw new InternalError("not a reinvoker MH: " + getClass().getName() + ": " + this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static LambdaForm reinvokerForm(MethodHandle paramMethodHandle)
/*      */   {
/* 1439 */     MethodType localMethodType = paramMethodHandle.type().basicType();
/* 1440 */     LambdaForm localLambdaForm = localMethodType.form().cachedLambdaForm(8);
/* 1441 */     if (localLambdaForm != null) return localLambdaForm;
/* 1442 */     if (localMethodType.parameterSlotCount() >= 254)
/* 1443 */       return makeReinvokerForm(paramMethodHandle.type(), paramMethodHandle);
/* 1444 */     localLambdaForm = makeReinvokerForm(localMethodType, null);
/* 1445 */     return localMethodType.form().setCachedLambdaForm(8, localLambdaForm);
/*      */   }
/*      */   
/* 1448 */   private static LambdaForm makeReinvokerForm(MethodType paramMethodType, MethodHandle paramMethodHandle) { int i = paramMethodHandle != null ? 1 : 0;
/* 1449 */     MethodHandle localMethodHandle1 = i != 0 ? null : MethodHandles.basicInvoker(paramMethodType);
/*      */     
/*      */ 
/* 1452 */     int j = 1 + paramMethodType.parameterCount();
/* 1453 */     int k = j;
/* 1454 */     int m = i != 0 ? -1 : k++;
/* 1455 */     int n = k++;
/* 1456 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(k - j, paramMethodType.invokerType());
/*      */     Object[] arrayOfObject;
/*      */     MethodHandle localMethodHandle2;
/* 1459 */     if (i != 0) {
/* 1460 */       arrayOfObject = Arrays.copyOfRange(arrayOfName, 1, j, Object[].class);
/* 1461 */       localMethodHandle2 = paramMethodHandle;
/*      */     } else {
/* 1463 */       arrayOfName[m] = new LambdaForm.Name(NF_reinvokerTarget, new Object[] { arrayOfName[0] });
/* 1464 */       arrayOfObject = Arrays.copyOfRange(arrayOfName, 0, j, Object[].class);
/* 1465 */       arrayOfObject[0] = arrayOfName[m];
/* 1466 */       localMethodHandle2 = MethodHandles.basicInvoker(paramMethodType);
/*      */     }
/* 1468 */     arrayOfName[n] = new LambdaForm.Name(localMethodHandle2, arrayOfObject);
/* 1469 */     return new LambdaForm("BMH.reinvoke", j, arrayOfName);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void updateForm(LambdaForm paramLambdaForm)
/*      */   {
/* 1491 */     if (this.form == paramLambdaForm) { return;
/*      */     }
/* 1493 */     MethodHandleStatics.UNSAFE.putObject(this, FORM_OFFSET, paramLambdaForm);
/* 1494 */     this.form.prepare();
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  426 */     MethodHandleImpl.initStatics();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1476 */       NF_reinvokerTarget = new LambdaForm.NamedFunction(MethodHandle.class.getDeclaredMethod("reinvokerTarget", new Class[0]));
/*      */     } catch (ReflectiveOperationException localReflectiveOperationException1) {
/* 1478 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1500 */       FORM_OFFSET = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodHandle.class.getDeclaredField("form"));
/*      */     } catch (ReflectiveOperationException localReflectiveOperationException2) {
/* 1502 */       throw MethodHandleStatics.newInternalError(localReflectiveOperationException2);
/*      */     }
/*      */   }
/*      */   
/*      */   @Target({java.lang.annotation.ElementType.METHOD})
/*      */   @Retention(RetentionPolicy.RUNTIME)
/*      */   static @interface PolymorphicSignature {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */