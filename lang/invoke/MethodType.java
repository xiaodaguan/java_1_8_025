/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.invoke.util.BytecodeDescriptor;
/*      */ import sun.invoke.util.VerifyType;
/*      */ import sun.invoke.util.Wrapper;
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
/*      */ public final class MethodType
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 292L;
/*      */   private final Class<?> rtype;
/*      */   private final Class<?>[] ptypes;
/*      */   @Stable
/*      */   private MethodTypeForm form;
/*      */   @Stable
/*      */   private MethodType wrapAlt;
/*      */   @Stable
/*      */   private Invokers invokers;
/*      */   @Stable
/*      */   private String methodDescriptor;
/*      */   static final int MAX_JVM_ARITY = 255;
/*      */   static final int MAX_MH_ARITY = 254;
/*      */   static final int MAX_MH_INVOKER_ARITY = 253;
/*      */   static final ConcurrentWeakInternSet<MethodType> internTable;
/*      */   static final Class<?>[] NO_PTYPES;
/*      */   private static final MethodType[] objectOnlyTypes;
/*      */   private static final ObjectStreamField[] serialPersistentFields;
/*      */   private static final long rtypeOffset;
/*      */   private static final long ptypesOffset;
/*      */   
/*      */   private MethodType(Class<?> paramClass, Class<?>[] paramArrayOfClass, boolean paramBoolean)
/*      */   {
/*  108 */     checkRtype(paramClass);
/*  109 */     checkPtypes(paramArrayOfClass);
/*  110 */     this.rtype = paramClass;
/*      */     
/*  112 */     this.ptypes = (paramBoolean ? paramArrayOfClass : (Class[])Arrays.copyOf(paramArrayOfClass, paramArrayOfClass.length));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private MethodType(Class<?>[] paramArrayOfClass, Class<?> paramClass)
/*      */   {
/*  121 */     this.rtype = paramClass;
/*  122 */     this.ptypes = paramArrayOfClass;
/*      */   }
/*      */   
/*  125 */   MethodTypeForm form() { return this.form; }
/*  126 */   Class<?> rtype() { return this.rtype; }
/*  127 */   Class<?>[] ptypes() { return this.ptypes; }
/*      */   
/*  129 */   void setForm(MethodTypeForm paramMethodTypeForm) { this.form = paramMethodTypeForm; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  162 */   private static void checkRtype(Class<?> paramClass) { Objects.requireNonNull(paramClass); }
/*      */   
/*      */   private static void checkPtype(Class<?> paramClass) {
/*  165 */     Objects.requireNonNull(paramClass);
/*  166 */     if (paramClass == Void.TYPE)
/*  167 */       throw MethodHandleStatics.newIllegalArgumentException("parameter type cannot be void");
/*      */   }
/*      */   
/*      */   private static int checkPtypes(Class<?>[] paramArrayOfClass) {
/*  171 */     int i = 0;
/*  172 */     for (Class<?> localClass : paramArrayOfClass) {
/*  173 */       checkPtype(localClass);
/*  174 */       if ((localClass == Double.TYPE) || (localClass == Long.TYPE)) {
/*  175 */         i++;
/*      */       }
/*      */     }
/*  178 */     checkSlotCount(paramArrayOfClass.length + i);
/*  179 */     return i;
/*      */   }
/*      */   
/*      */   static void checkSlotCount(int paramInt)
/*      */   {
/*  184 */     if ((paramInt & 0xFF) != paramInt)
/*  185 */       throw MethodHandleStatics.newIllegalArgumentException("bad parameter count " + paramInt);
/*      */   }
/*      */   
/*  188 */   private static IndexOutOfBoundsException newIndexOutOfBoundsException(Object paramObject) { if ((paramObject instanceof Integer)) paramObject = "bad index: " + paramObject;
/*  189 */     return new IndexOutOfBoundsException(paramObject.toString());
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
/*      */   public static MethodType methodType(Class<?> paramClass, Class<?>[] paramArrayOfClass)
/*      */   {
/*  206 */     return makeImpl(paramClass, paramArrayOfClass, false);
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
/*      */   public static MethodType methodType(Class<?> paramClass, List<Class<?>> paramList)
/*      */   {
/*  220 */     boolean bool = false;
/*  221 */     return makeImpl(paramClass, listToArray(paramList), bool);
/*      */   }
/*      */   
/*      */   private static Class<?>[] listToArray(List<Class<?>> paramList)
/*      */   {
/*  226 */     checkSlotCount(paramList.size());
/*  227 */     return (Class[])paramList.toArray(NO_PTYPES);
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
/*      */   public static MethodType methodType(Class<?> paramClass1, Class<?> paramClass2, Class<?>... paramVarArgs)
/*      */   {
/*  243 */     Class[] arrayOfClass = new Class[1 + paramVarArgs.length];
/*  244 */     arrayOfClass[0] = paramClass2;
/*  245 */     System.arraycopy(paramVarArgs, 0, arrayOfClass, 1, paramVarArgs.length);
/*  246 */     return makeImpl(paramClass1, arrayOfClass, true);
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
/*      */   public static MethodType methodType(Class<?> paramClass)
/*      */   {
/*  259 */     return makeImpl(paramClass, NO_PTYPES, true);
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
/*      */   public static MethodType methodType(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  274 */     return makeImpl(paramClass1, new Class[] { paramClass2 }, true);
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
/*      */   public static MethodType methodType(Class<?> paramClass, MethodType paramMethodType)
/*      */   {
/*  289 */     return makeImpl(paramClass, paramMethodType.ptypes, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static MethodType makeImpl(Class<?> paramClass, Class<?>[] paramArrayOfClass, boolean paramBoolean)
/*      */   {
/*  301 */     MethodType localMethodType = (MethodType)internTable.get(new MethodType(paramArrayOfClass, paramClass));
/*  302 */     if (localMethodType != null)
/*  303 */       return localMethodType;
/*  304 */     if (paramArrayOfClass.length == 0) {
/*  305 */       paramArrayOfClass = NO_PTYPES;paramBoolean = true;
/*      */     }
/*  307 */     localMethodType = new MethodType(paramClass, paramArrayOfClass, paramBoolean);
/*      */     
/*  309 */     localMethodType.form = MethodTypeForm.findForm(localMethodType);
/*  310 */     return (MethodType)internTable.add(localMethodType);
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
/*      */   public static MethodType genericMethodType(int paramInt, boolean paramBoolean)
/*      */   {
/*  328 */     checkSlotCount(paramInt);
/*  329 */     int i = !paramBoolean ? 0 : 1;
/*  330 */     int j = paramInt * 2 + i;
/*  331 */     if (j < objectOnlyTypes.length) {
/*  332 */       localMethodType = objectOnlyTypes[j];
/*  333 */       if (localMethodType != null) return localMethodType;
/*      */     }
/*  335 */     Class[] arrayOfClass = new Class[paramInt + i];
/*  336 */     Arrays.fill(arrayOfClass, Object.class);
/*  337 */     if (i != 0) arrayOfClass[paramInt] = Object[].class;
/*  338 */     MethodType localMethodType = makeImpl(Object.class, arrayOfClass, true);
/*  339 */     if (j < objectOnlyTypes.length) {
/*  340 */       objectOnlyTypes[j] = localMethodType;
/*      */     }
/*  342 */     return localMethodType;
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
/*      */   public static MethodType genericMethodType(int paramInt)
/*      */   {
/*  356 */     return genericMethodType(paramInt, false);
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
/*      */   public MethodType changeParameterType(int paramInt, Class<?> paramClass)
/*      */   {
/*  370 */     if (parameterType(paramInt) == paramClass) return this;
/*  371 */     checkPtype(paramClass);
/*  372 */     Class[] arrayOfClass = (Class[])this.ptypes.clone();
/*  373 */     arrayOfClass[paramInt] = paramClass;
/*  374 */     return makeImpl(this.rtype, arrayOfClass, true);
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
/*      */   public MethodType insertParameterTypes(int paramInt, Class<?>... paramVarArgs)
/*      */   {
/*  389 */     int i = this.ptypes.length;
/*  390 */     if ((paramInt < 0) || (paramInt > i))
/*  391 */       throw newIndexOutOfBoundsException(Integer.valueOf(paramInt));
/*  392 */     int j = checkPtypes(paramVarArgs);
/*  393 */     checkSlotCount(parameterSlotCount() + paramVarArgs.length + j);
/*  394 */     int k = paramVarArgs.length;
/*  395 */     if (k == 0) return this;
/*  396 */     Class[] arrayOfClass = (Class[])Arrays.copyOfRange(this.ptypes, 0, i + k);
/*  397 */     System.arraycopy(arrayOfClass, paramInt, arrayOfClass, paramInt + k, i - paramInt);
/*  398 */     System.arraycopy(paramVarArgs, 0, arrayOfClass, paramInt, k);
/*  399 */     return makeImpl(this.rtype, arrayOfClass, true);
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
/*      */   public MethodType appendParameterTypes(Class<?>... paramVarArgs)
/*      */   {
/*  412 */     return insertParameterTypes(parameterCount(), paramVarArgs);
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
/*      */   public MethodType insertParameterTypes(int paramInt, List<Class<?>> paramList)
/*      */   {
/*  427 */     return insertParameterTypes(paramInt, listToArray(paramList));
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
/*      */   public MethodType appendParameterTypes(List<Class<?>> paramList)
/*      */   {
/*  440 */     return insertParameterTypes(parameterCount(), paramList);
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
/*      */   MethodType replaceParameterTypes(int paramInt1, int paramInt2, Class<?>... paramVarArgs)
/*      */   {
/*  458 */     if (paramInt1 == paramInt2)
/*  459 */       return insertParameterTypes(paramInt1, paramVarArgs);
/*  460 */     int i = this.ptypes.length;
/*  461 */     if ((0 > paramInt1) || (paramInt1 > paramInt2) || (paramInt2 > i))
/*  462 */       throw newIndexOutOfBoundsException("start=" + paramInt1 + " end=" + paramInt2);
/*  463 */     int j = paramVarArgs.length;
/*  464 */     if (j == 0)
/*  465 */       return dropParameterTypes(paramInt1, paramInt2);
/*  466 */     return dropParameterTypes(paramInt1, paramInt2).insertParameterTypes(paramInt1, paramVarArgs);
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
/*      */   public MethodType dropParameterTypes(int paramInt1, int paramInt2)
/*      */   {
/*  480 */     int i = this.ptypes.length;
/*  481 */     if ((0 > paramInt1) || (paramInt1 > paramInt2) || (paramInt2 > i))
/*  482 */       throw newIndexOutOfBoundsException("start=" + paramInt1 + " end=" + paramInt2);
/*  483 */     if (paramInt1 == paramInt2) return this;
/*      */     Class[] arrayOfClass;
/*  485 */     if (paramInt1 == 0) {
/*  486 */       if (paramInt2 == i)
/*      */       {
/*  488 */         arrayOfClass = NO_PTYPES;
/*      */       }
/*      */       else {
/*  491 */         arrayOfClass = (Class[])Arrays.copyOfRange(this.ptypes, paramInt2, i);
/*      */       }
/*      */     }
/*  494 */     else if (paramInt2 == i)
/*      */     {
/*  496 */       arrayOfClass = (Class[])Arrays.copyOfRange(this.ptypes, 0, paramInt1);
/*      */     } else {
/*  498 */       int j = i - paramInt2;
/*  499 */       arrayOfClass = (Class[])Arrays.copyOfRange(this.ptypes, 0, paramInt1 + j);
/*  500 */       System.arraycopy(this.ptypes, paramInt2, arrayOfClass, paramInt1, j);
/*      */     }
/*      */     
/*  503 */     return makeImpl(this.rtype, arrayOfClass, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodType changeReturnType(Class<?> paramClass)
/*      */   {
/*  514 */     if (returnType() == paramClass) return this;
/*  515 */     return makeImpl(paramClass, this.ptypes, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasPrimitives()
/*      */   {
/*  524 */     return this.form.hasPrimitives();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasWrappers()
/*      */   {
/*  535 */     return unwrap() != this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodType erase()
/*      */   {
/*  545 */     return this.form.erasedType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MethodType basicType()
/*      */   {
/*  555 */     return this.form.basicType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   MethodType invokerType()
/*      */   {
/*  562 */     return insertParameterTypes(0, new Class[] { MethodHandle.class });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodType generic()
/*      */   {
/*  573 */     return genericMethodType(parameterCount());
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
/*      */   public MethodType wrap()
/*      */   {
/*  586 */     return hasPrimitives() ? wrapWithPrims(this) : this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodType unwrap()
/*      */   {
/*  597 */     MethodType localMethodType = !hasPrimitives() ? this : wrapWithPrims(this);
/*  598 */     return unwrapWithNoPrims(localMethodType);
/*      */   }
/*      */   
/*      */   private static MethodType wrapWithPrims(MethodType paramMethodType) {
/*  602 */     assert (paramMethodType.hasPrimitives());
/*  603 */     MethodType localMethodType = paramMethodType.wrapAlt;
/*  604 */     if (localMethodType == null)
/*      */     {
/*  606 */       localMethodType = MethodTypeForm.canonicalize(paramMethodType, 2, 2);
/*  607 */       assert (localMethodType != null);
/*  608 */       paramMethodType.wrapAlt = localMethodType;
/*      */     }
/*  610 */     return localMethodType;
/*      */   }
/*      */   
/*      */   private static MethodType unwrapWithNoPrims(MethodType paramMethodType) {
/*  614 */     assert (!paramMethodType.hasPrimitives());
/*  615 */     MethodType localMethodType = paramMethodType.wrapAlt;
/*  616 */     if (localMethodType == null)
/*      */     {
/*  618 */       localMethodType = MethodTypeForm.canonicalize(paramMethodType, 3, 3);
/*  619 */       if (localMethodType == null)
/*  620 */         localMethodType = paramMethodType;
/*  621 */       paramMethodType.wrapAlt = localMethodType;
/*      */     }
/*  623 */     return localMethodType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> parameterType(int paramInt)
/*      */   {
/*  633 */     return this.ptypes[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int parameterCount()
/*      */   {
/*  640 */     return this.ptypes.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Class<?> returnType()
/*      */   {
/*  647 */     return this.rtype;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<Class<?>> parameterList()
/*      */   {
/*  656 */     return Collections.unmodifiableList(Arrays.asList((Object[])this.ptypes.clone()));
/*      */   }
/*      */   
/*      */   Class<?> lastParameterType() {
/*  660 */     int i = this.ptypes.length;
/*  661 */     return i == 0 ? Void.TYPE : this.ptypes[(i - 1)];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?>[] parameterArray()
/*      */   {
/*  670 */     return (Class[])this.ptypes.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  682 */     return (this == paramObject) || (((paramObject instanceof MethodType)) && (equals((MethodType)paramObject)));
/*      */   }
/*      */   
/*      */   private boolean equals(MethodType paramMethodType)
/*      */   {
/*  687 */     return (this.rtype == paramMethodType.rtype) && (Arrays.equals(this.ptypes, paramMethodType.ptypes));
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
/*      */   public int hashCode()
/*      */   {
/*  702 */     int i = 31 + this.rtype.hashCode();
/*  703 */     for (Class localClass : this.ptypes)
/*  704 */       i = 31 * i + localClass.hashCode();
/*  705 */     return i;
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
/*      */   public String toString()
/*      */   {
/*  720 */     StringBuilder localStringBuilder = new StringBuilder();
/*  721 */     localStringBuilder.append("(");
/*  722 */     for (int i = 0; i < this.ptypes.length; i++) {
/*  723 */       if (i > 0) localStringBuilder.append(",");
/*  724 */       localStringBuilder.append(this.ptypes[i].getSimpleName());
/*      */     }
/*  726 */     localStringBuilder.append(")");
/*  727 */     localStringBuilder.append(this.rtype.getSimpleName());
/*  728 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   boolean isViewableAs(MethodType paramMethodType)
/*      */   {
/*  734 */     if (!VerifyType.isNullConversion(returnType(), paramMethodType.returnType()))
/*  735 */       return false;
/*  736 */     int i = parameterCount();
/*  737 */     if (i != paramMethodType.parameterCount())
/*  738 */       return false;
/*  739 */     for (int j = 0; j < i; j++) {
/*  740 */       if (!VerifyType.isNullConversion(paramMethodType.parameterType(j), parameterType(j)))
/*  741 */         return false;
/*      */     }
/*  743 */     return true;
/*      */   }
/*      */   
/*      */   boolean isCastableTo(MethodType paramMethodType) {
/*  747 */     int i = parameterCount();
/*  748 */     if (i != paramMethodType.parameterCount())
/*  749 */       return false;
/*  750 */     return true;
/*      */   }
/*      */   
/*      */   boolean isConvertibleTo(MethodType paramMethodType) {
/*  754 */     if (!canConvert(returnType(), paramMethodType.returnType()))
/*  755 */       return false;
/*  756 */     int i = parameterCount();
/*  757 */     if (i != paramMethodType.parameterCount())
/*  758 */       return false;
/*  759 */     for (int j = 0; j < i; j++) {
/*  760 */       if (!canConvert(paramMethodType.parameterType(j), parameterType(j)))
/*  761 */         return false;
/*      */     }
/*  763 */     return true;
/*      */   }
/*      */   
/*      */   static boolean canConvert(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  768 */     if ((paramClass1 == paramClass2) || (paramClass2 == Object.class)) return true;
/*      */     Wrapper localWrapper;
/*  770 */     if (paramClass1.isPrimitive())
/*      */     {
/*      */ 
/*  773 */       if (paramClass1 == Void.TYPE) return true;
/*  774 */       localWrapper = Wrapper.forPrimitiveType(paramClass1);
/*  775 */       if (paramClass2.isPrimitive())
/*      */       {
/*  777 */         return Wrapper.forPrimitiveType(paramClass2).isConvertibleFrom(localWrapper);
/*      */       }
/*      */       
/*  780 */       return paramClass2.isAssignableFrom(localWrapper.wrapperType());
/*      */     }
/*  782 */     if (paramClass2.isPrimitive())
/*      */     {
/*  784 */       if (paramClass2 == Void.TYPE) return true;
/*  785 */       localWrapper = Wrapper.forPrimitiveType(paramClass2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  792 */       if (paramClass1.isAssignableFrom(localWrapper.wrapperType())) {
/*  793 */         return true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  799 */       if ((Wrapper.isWrapperType(paramClass1)) && 
/*  800 */         (localWrapper.isConvertibleFrom(Wrapper.forWrapperType(paramClass1))))
/*      */       {
/*  802 */         return true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  811 */       return false;
/*      */     }
/*      */     
/*  814 */     return true;
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
/*      */   int parameterSlotCount()
/*      */   {
/*  831 */     return this.form.parameterSlotCount();
/*      */   }
/*      */   
/*      */   Invokers invokers() {
/*  835 */     Invokers localInvokers = this.invokers;
/*  836 */     if (localInvokers != null) return localInvokers;
/*  837 */     this.invokers = (localInvokers = new Invokers(this));
/*  838 */     return localInvokers;
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
/*      */   int parameterSlotDepth(int paramInt)
/*      */   {
/*  865 */     if ((paramInt < 0) || (paramInt > this.ptypes.length))
/*  866 */       parameterType(paramInt);
/*  867 */     return this.form.parameterToArgSlot(paramInt - 1);
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
/*      */   int returnSlotCount()
/*      */   {
/*  881 */     return this.form.returnSlotCount();
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
/*      */   public static MethodType fromMethodDescriptorString(String paramString, ClassLoader paramClassLoader)
/*      */     throws IllegalArgumentException, TypeNotPresentException
/*      */   {
/*  907 */     if ((!paramString.startsWith("(")) || 
/*  908 */       (paramString.indexOf(')') < 0) || 
/*  909 */       (paramString.indexOf('.') >= 0))
/*  910 */       throw new IllegalArgumentException("not a method descriptor: " + paramString);
/*  911 */     List localList = BytecodeDescriptor.parseMethod(paramString, paramClassLoader);
/*  912 */     Class localClass = (Class)localList.remove(localList.size() - 1);
/*  913 */     checkSlotCount(localList.size());
/*  914 */     Class[] arrayOfClass = listToArray(localList);
/*  915 */     return makeImpl(localClass, arrayOfClass, true);
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
/*      */   public String toMethodDescriptorString()
/*      */   {
/*  932 */     String str = this.methodDescriptor;
/*  933 */     if (str == null) {
/*  934 */       str = BytecodeDescriptor.unparse(this);
/*  935 */       this.methodDescriptor = str;
/*      */     }
/*  937 */     return str;
/*      */   }
/*      */   
/*      */   static String toFieldDescriptorString(Class<?> paramClass) {
/*  941 */     return BytecodeDescriptor.unparse(paramClass);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*  972 */     paramObjectOutputStream.defaultWriteObject();
/*  973 */     paramObjectOutputStream.writeObject(returnType());
/*  974 */     paramObjectOutputStream.writeObject(parameterArray());
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  992 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*  994 */     Class localClass = (Class)paramObjectInputStream.readObject();
/*  995 */     Class[] arrayOfClass = (Class[])paramObjectInputStream.readObject();
/*      */     
/*      */ 
/*      */ 
/*  999 */     checkRtype(localClass);
/* 1000 */     checkPtypes(arrayOfClass);
/*      */     
/* 1002 */     arrayOfClass = (Class[])arrayOfClass.clone();
/* 1003 */     MethodType_init(localClass, arrayOfClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private MethodType()
/*      */   {
/* 1011 */     this.rtype = null;
/* 1012 */     this.ptypes = null;
/*      */   }
/*      */   
/*      */   private void MethodType_init(Class<?> paramClass, Class<?>[] paramArrayOfClass)
/*      */   {
/* 1017 */     checkRtype(paramClass);
/* 1018 */     checkPtypes(paramArrayOfClass);
/* 1019 */     MethodHandleStatics.UNSAFE.putObject(this, rtypeOffset, paramClass);
/* 1020 */     MethodHandleStatics.UNSAFE.putObject(this, ptypesOffset, paramArrayOfClass);
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  192 */     internTable = new ConcurrentWeakInternSet();
/*      */     
/*  194 */     NO_PTYPES = new Class[0];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  312 */     objectOnlyTypes = new MethodType[20];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  949 */     serialPersistentFields = new ObjectStreamField[0];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1028 */       rtypeOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("rtype"));
/*      */       
/* 1030 */       ptypesOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("ptypes"));
/*      */     } catch (Exception localException) {
/* 1032 */       throw new Error(localException);
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
/*      */   private Object readResolve()
/*      */   {
/* 1045 */     return methodType(this.rtype, this.ptypes);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class ConcurrentWeakInternSet<T>
/*      */   {
/*      */     private final ConcurrentMap<WeakEntry<T>, WeakEntry<T>> map;
/*      */     
/*      */     private final ReferenceQueue<T> stale;
/*      */     
/*      */ 
/*      */     public ConcurrentWeakInternSet()
/*      */     {
/* 1059 */       this.map = new ConcurrentHashMap();
/* 1060 */       this.stale = new ReferenceQueue();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public T get(T paramT)
/*      */     {
/* 1071 */       if (paramT == null) throw new NullPointerException();
/* 1072 */       expungeStaleElements();
/*      */       
/* 1074 */       WeakEntry localWeakEntry = (WeakEntry)this.map.get(new WeakEntry(paramT));
/* 1075 */       if (localWeakEntry != null) {
/* 1076 */         Object localObject = localWeakEntry.get();
/* 1077 */         if (localObject != null) {
/* 1078 */           return (T)localObject;
/*      */         }
/*      */       }
/* 1081 */       return null;
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
/*      */     public T add(T paramT)
/*      */     {
/* 1094 */       if (paramT == null) { throw new NullPointerException();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1101 */       WeakEntry localWeakEntry1 = new WeakEntry(paramT, this.stale);
/*      */       Object localObject;
/* 1103 */       do { expungeStaleElements();
/* 1104 */         WeakEntry localWeakEntry2 = (WeakEntry)this.map.putIfAbsent(localWeakEntry1, localWeakEntry1);
/* 1105 */         localObject = localWeakEntry2 == null ? paramT : localWeakEntry2.get();
/* 1106 */       } while (localObject == null);
/* 1107 */       return (T)localObject;
/*      */     }
/*      */     
/*      */     private void expungeStaleElements() {
/*      */       Reference localReference;
/* 1112 */       while ((localReference = this.stale.poll()) != null) {
/* 1113 */         this.map.remove(localReference);
/*      */       }
/*      */     }
/*      */     
/*      */     private static class WeakEntry<T> extends WeakReference<T>
/*      */     {
/*      */       public final int hashcode;
/*      */       
/*      */       public WeakEntry(T paramT, ReferenceQueue<T> paramReferenceQueue) {
/* 1122 */         super(paramReferenceQueue);
/* 1123 */         this.hashcode = paramT.hashCode();
/*      */       }
/*      */       
/*      */       public WeakEntry(T paramT) {
/* 1127 */         super();
/* 1128 */         this.hashcode = paramT.hashCode();
/*      */       }
/*      */       
/*      */       public boolean equals(Object paramObject)
/*      */       {
/* 1133 */         if ((paramObject instanceof WeakEntry)) {
/* 1134 */           Object localObject1 = ((WeakEntry)paramObject).get();
/* 1135 */           Object localObject2 = get();
/* 1136 */           return (localObject1 == null) || (localObject2 == null) ? false : this == paramObject ? true : localObject2.equals(localObject1);
/*      */         }
/* 1138 */         return false;
/*      */       }
/*      */       
/*      */       public int hashCode()
/*      */       {
/* 1143 */         return this.hashcode;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */