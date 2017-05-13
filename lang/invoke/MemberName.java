/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import sun.invoke.util.BytecodeDescriptor;
/*      */ import sun.invoke.util.VerifyAccess;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class MemberName
/*      */   implements Member, Cloneable
/*      */ {
/*      */   private Class<?> clazz;
/*      */   private String name;
/*      */   private Object type;
/*      */   private int flags;
/*      */   private Object resolution;
/*      */   private static final int MH_INVOKE_MODS = 273;
/*      */   static final int BRIDGE = 64;
/*      */   static final int VARARGS = 128;
/*      */   static final int SYNTHETIC = 4096;
/*      */   static final int ANNOTATION = 8192;
/*      */   static final int ENUM = 16384;
/*      */   static final String CONSTRUCTOR_NAME = "<init>";
/*      */   static final int RECOGNIZED_MODIFIERS = 65535;
/*      */   static final int IS_METHOD = 65536;
/*      */   static final int IS_CONSTRUCTOR = 131072;
/*      */   static final int IS_FIELD = 262144;
/*      */   static final int IS_TYPE = 524288;
/*      */   static final int CALLER_SENSITIVE = 1048576;
/*      */   static final int ALL_ACCESS = 7;
/*      */   static final int ALL_KINDS = 983040;
/*      */   static final int IS_INVOCABLE = 196608;
/*      */   static final int IS_FIELD_OR_METHOD = 327680;
/*      */   static final int SEARCH_ALL_SUPERS = 3145728;
/*      */   
/*      */   public Class<?> getDeclaringClass()
/*      */   {
/*   85 */     return this.clazz;
/*      */   }
/*      */   
/*      */   public ClassLoader getClassLoader()
/*      */   {
/*   90 */     return this.clazz.getClassLoader();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*   99 */     if (this.name == null) {
/*  100 */       expandFromVM();
/*  101 */       if (this.name == null) {
/*  102 */         return null;
/*      */       }
/*      */     }
/*  105 */     return this.name;
/*      */   }
/*      */   
/*      */   public MethodType getMethodOrFieldType() {
/*  109 */     if (isInvocable())
/*  110 */       return getMethodType();
/*  111 */     if (isGetter())
/*  112 */       return MethodType.methodType(getFieldType());
/*  113 */     if (isSetter())
/*  114 */       return MethodType.methodType(Void.TYPE, getFieldType());
/*  115 */     throw new InternalError("not a method or field: " + this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public MethodType getMethodType()
/*      */   {
/*  122 */     if (this.type == null) {
/*  123 */       expandFromVM();
/*  124 */       if (this.type == null) {
/*  125 */         return null;
/*      */       }
/*      */     }
/*  128 */     if (!isInvocable()) {
/*  129 */       throw MethodHandleStatics.newIllegalArgumentException("not invocable, no method type");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  134 */     Object localObject1 = this.type;
/*  135 */     if ((localObject1 instanceof MethodType)) {
/*  136 */       return (MethodType)localObject1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  141 */     synchronized (this) { Object localObject2;
/*  142 */       Object localObject3; if ((this.type instanceof String)) {
/*  143 */         localObject2 = (String)this.type;
/*  144 */         localObject3 = MethodType.fromMethodDescriptorString((String)localObject2, getClassLoader());
/*  145 */         this.type = localObject3;
/*  146 */       } else if ((this.type instanceof Object[])) {
/*  147 */         localObject2 = (Object[])this.type;
/*  148 */         localObject3 = (Class[])localObject2[1];
/*  149 */         Class localClass = (Class)localObject2[0];
/*  150 */         MethodType localMethodType = MethodType.methodType(localClass, (Class[])localObject3);
/*  151 */         this.type = localMethodType;
/*      */       }
/*      */       
/*  154 */       if ((!$assertionsDisabled) && (!(this.type instanceof MethodType))) throw new AssertionError("bad method type " + this.type);
/*      */     }
/*  156 */     return (MethodType)this.type;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public MethodType getInvocationType()
/*      */   {
/*  164 */     MethodType localMethodType = getMethodOrFieldType();
/*  165 */     if ((isConstructor()) && (getReferenceKind() == 8))
/*  166 */       return localMethodType.changeReturnType(this.clazz);
/*  167 */     if (!isStatic())
/*  168 */       return localMethodType.insertParameterTypes(0, new Class[] { this.clazz });
/*  169 */     return localMethodType;
/*      */   }
/*      */   
/*      */   public Class<?>[] getParameterTypes()
/*      */   {
/*  174 */     return getMethodType().parameterArray();
/*      */   }
/*      */   
/*      */   public Class<?> getReturnType()
/*      */   {
/*  179 */     return getMethodType().returnType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> getFieldType()
/*      */   {
/*  187 */     if (this.type == null) {
/*  188 */       expandFromVM();
/*  189 */       if (this.type == null) {
/*  190 */         return null;
/*      */       }
/*      */     }
/*  193 */     if (isInvocable()) {
/*  194 */       throw MethodHandleStatics.newIllegalArgumentException("not a field or nested class, no simple type");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  199 */     Object localObject1 = this.type;
/*  200 */     if ((localObject1 instanceof Class)) {
/*  201 */       return (Class)localObject1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  206 */     synchronized (this) {
/*  207 */       if ((this.type instanceof String)) {
/*  208 */         String str = (String)this.type;
/*  209 */         MethodType localMethodType = MethodType.fromMethodDescriptorString("()" + str, getClassLoader());
/*  210 */         Class localClass = localMethodType.returnType();
/*  211 */         this.type = localClass;
/*      */       }
/*      */       
/*  214 */       if ((!$assertionsDisabled) && (!(this.type instanceof Class))) throw new AssertionError("bad field type " + this.type);
/*      */     }
/*  216 */     return (Class)this.type;
/*      */   }
/*      */   
/*      */   public Object getType()
/*      */   {
/*  221 */     return isInvocable() ? getMethodType() : getFieldType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSignature()
/*      */   {
/*  228 */     if (this.type == null) {
/*  229 */       expandFromVM();
/*  230 */       if (this.type == null) {
/*  231 */         return null;
/*      */       }
/*      */     }
/*  234 */     if (isInvocable()) {
/*  235 */       return BytecodeDescriptor.unparse(getMethodType());
/*      */     }
/*  237 */     return BytecodeDescriptor.unparse(getFieldType());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getModifiers()
/*      */   {
/*  244 */     return this.flags & 0xFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  250 */   public byte getReferenceKind() { return (byte)(this.flags >>> 24 & 0xF); }
/*      */   
/*      */   private boolean referenceKindIsConsistent() {
/*  253 */     byte b = getReferenceKind();
/*  254 */     if (b == 0) return isType();
/*  255 */     if (isField()) {
/*  256 */       assert (staticIsConsistent());
/*  257 */       if ((!$assertionsDisabled) && (!MethodHandleNatives.refKindIsField(b))) throw new AssertionError();
/*  258 */     } else if (isConstructor()) {
/*  259 */       if ((!$assertionsDisabled) && (b != 8) && (b != 7)) throw new AssertionError();
/*  260 */     } else if (isMethod()) {
/*  261 */       assert (staticIsConsistent());
/*  262 */       assert (MethodHandleNatives.refKindIsMethod(b));
/*  263 */       if ((this.clazz.isInterface()) && 
/*  264 */         (!$assertionsDisabled) && (b != 9) && (b != 6) && (b != 7) && ((b != 5) || 
/*      */         
/*      */ 
/*  267 */         (!isObjectPublicMethod()))) {
/*  264 */         throw new AssertionError();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  269 */     else if (!$assertionsDisabled) { throw new AssertionError();
/*      */     }
/*  271 */     return true;
/*      */   }
/*      */   
/*  274 */   private boolean isObjectPublicMethod() { if (this.clazz == Object.class) return true;
/*  275 */     MethodType localMethodType = getMethodType();
/*  276 */     if ((this.name.equals("toString")) && (localMethodType.returnType() == String.class) && (localMethodType.parameterCount() == 0))
/*  277 */       return true;
/*  278 */     if ((this.name.equals("hashCode")) && (localMethodType.returnType() == Integer.TYPE) && (localMethodType.parameterCount() == 0))
/*  279 */       return true;
/*  280 */     if ((this.name.equals("equals")) && (localMethodType.returnType() == Boolean.TYPE) && (localMethodType.parameterCount() == 1) && (localMethodType.parameterType(0) == Object.class))
/*  281 */       return true;
/*  282 */     return false;
/*      */   }
/*      */   
/*  285 */   boolean referenceKindIsConsistentWith(int paramInt) { int i = getReferenceKind();
/*  286 */     if (i == paramInt) return true;
/*  287 */     switch (paramInt)
/*      */     {
/*      */     case 9: 
/*  290 */       assert ((i == 5) || (i == 7)) : this;
/*      */       
/*  292 */       return true;
/*      */     
/*      */     case 5: 
/*      */     case 8: 
/*  296 */       assert (i == 7) : this;
/*  297 */       return true;
/*      */     }
/*  299 */     if (!$assertionsDisabled) throw new AssertionError(this + " != " + MethodHandleNatives.refKindName((byte)paramInt));
/*  300 */     return true;
/*      */   }
/*      */   
/*  303 */   private boolean staticIsConsistent() { byte b = getReferenceKind();
/*  304 */     return (MethodHandleNatives.refKindIsStatic(b) == isStatic()) || (getModifiers() == 0);
/*      */   }
/*      */   
/*  307 */   private boolean vminfoIsConsistent() { byte b = getReferenceKind();
/*  308 */     assert (isResolved());
/*  309 */     Object localObject1 = MethodHandleNatives.getMemberVMInfo(this);
/*  310 */     assert ((localObject1 instanceof Object[]));
/*  311 */     long l = ((Long)((Object[])(Object[])localObject1)[0]).longValue();
/*  312 */     Object localObject2 = ((Object[])(Object[])localObject1)[1];
/*  313 */     if (MethodHandleNatives.refKindIsField(b)) {
/*  314 */       assert (l >= 0L) : (l + ":" + this);
/*  315 */       if ((!$assertionsDisabled) && (!(localObject2 instanceof Class))) throw new AssertionError();
/*      */     } else {
/*  317 */       if (MethodHandleNatives.refKindDoesDispatch(b)) {
/*  318 */         if ((!$assertionsDisabled) && (l < 0L)) throw new AssertionError(l + ":" + this);
/*      */       } else
/*  320 */         assert (l < 0L) : l;
/*  321 */       assert ((localObject2 instanceof MemberName)) : (localObject2 + " in " + this);
/*      */     }
/*  323 */     return true;
/*      */   }
/*      */   
/*      */   private MemberName changeReferenceKind(byte paramByte1, byte paramByte2) {
/*  327 */     assert (getReferenceKind() == paramByte2);
/*  328 */     assert (MethodHandleNatives.refKindIsValid(paramByte1));
/*  329 */     this.flags += (paramByte1 - paramByte2 << 24);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  334 */     return this;
/*      */   }
/*      */   
/*      */   private boolean testFlags(int paramInt1, int paramInt2) {
/*  338 */     return (this.flags & paramInt1) == paramInt2;
/*      */   }
/*      */   
/*  341 */   private boolean testAllFlags(int paramInt) { return testFlags(paramInt, paramInt); }
/*      */   
/*      */   private boolean testAnyFlags(int paramInt) {
/*  344 */     return !testFlags(paramInt, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isMethodHandleInvoke()
/*      */   {
/*  351 */     if ((testFlags(281, 273)) && (this.clazz == MethodHandle.class))
/*      */     {
/*  353 */       return isMethodHandleInvokeName(this.name);
/*      */     }
/*  355 */     return false;
/*      */   }
/*      */   
/*  358 */   public static boolean isMethodHandleInvokeName(String paramString) { return (paramString.equals("invoke")) || (paramString.equals("invokeExact")); }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isStatic()
/*      */   {
/*  364 */     return Modifier.isStatic(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isPublic() {
/*  368 */     return Modifier.isPublic(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isPrivate() {
/*  372 */     return Modifier.isPrivate(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isProtected() {
/*  376 */     return Modifier.isProtected(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isFinal() {
/*  380 */     return Modifier.isFinal(this.flags);
/*      */   }
/*      */   
/*      */   public boolean canBeStaticallyBound() {
/*  384 */     return Modifier.isFinal(this.flags | this.clazz.getModifiers());
/*      */   }
/*      */   
/*      */   public boolean isVolatile() {
/*  388 */     return Modifier.isVolatile(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isAbstract() {
/*  392 */     return Modifier.isAbstract(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isNative() {
/*  396 */     return Modifier.isNative(this.flags);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isBridge()
/*      */   {
/*  408 */     return testAllFlags(65600);
/*      */   }
/*      */   
/*      */   public boolean isVarargs() {
/*  412 */     return (testAllFlags(128)) && (isInvocable());
/*      */   }
/*      */   
/*      */   public boolean isSynthetic() {
/*  416 */     return testAllFlags(4096);
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
/*      */   public boolean isInvocable()
/*      */   {
/*  440 */     return testAnyFlags(196608);
/*      */   }
/*      */   
/*      */   public boolean isFieldOrMethod() {
/*  444 */     return testAnyFlags(327680);
/*      */   }
/*      */   
/*      */   public boolean isMethod() {
/*  448 */     return testAllFlags(65536);
/*      */   }
/*      */   
/*      */   public boolean isConstructor() {
/*  452 */     return testAllFlags(131072);
/*      */   }
/*      */   
/*      */   public boolean isField() {
/*  456 */     return testAllFlags(262144);
/*      */   }
/*      */   
/*      */   public boolean isType() {
/*  460 */     return testAllFlags(524288);
/*      */   }
/*      */   
/*      */   public boolean isPackage() {
/*  464 */     return !testAnyFlags(7);
/*      */   }
/*      */   
/*      */   public boolean isCallerSensitive() {
/*  468 */     return testAllFlags(1048576);
/*      */   }
/*      */   
/*      */   public boolean isAccessibleFrom(Class<?> paramClass)
/*      */   {
/*  473 */     return VerifyAccess.isMemberAccessible(getDeclaringClass(), getDeclaringClass(), this.flags, paramClass, 15);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void init(Class<?> paramClass, String paramString, Object paramObject, int paramInt)
/*      */   {
/*  483 */     this.clazz = paramClass;
/*  484 */     this.name = paramString;
/*  485 */     this.type = paramObject;
/*  486 */     this.flags = paramInt;
/*  487 */     assert (testAnyFlags(983040));
/*  488 */     assert (this.resolution == null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void expandFromVM()
/*      */   {
/*  497 */     if (this.type != null) {
/*  498 */       return;
/*      */     }
/*  500 */     if (!isResolved()) {
/*  501 */       return;
/*      */     }
/*  503 */     MethodHandleNatives.expand(this);
/*      */   }
/*      */   
/*      */   private static int flagsMods(int paramInt1, int paramInt2, byte paramByte)
/*      */   {
/*  508 */     assert ((paramInt1 & 0xFFFF) == 0);
/*  509 */     assert ((paramInt2 & 0xFFFF0000) == 0);
/*  510 */     assert ((paramByte & 0xFFFFFFF0) == 0);
/*  511 */     return paramInt1 | paramInt2 | paramByte << 24;
/*      */   }
/*      */   
/*      */   public MemberName(Method paramMethod) {
/*  515 */     this(paramMethod, false);
/*      */   }
/*      */   
/*      */   public MemberName(Method paramMethod, boolean paramBoolean) {
/*  519 */     paramMethod.getClass();
/*      */     
/*  521 */     MethodHandleNatives.init(this, paramMethod);
/*  522 */     if (this.clazz == null) {
/*  523 */       if ((paramMethod.getDeclaringClass() == MethodHandle.class) && 
/*  524 */         (isMethodHandleInvokeName(paramMethod.getName())))
/*      */       {
/*      */ 
/*      */ 
/*  528 */         MethodType localMethodType = MethodType.methodType(paramMethod.getReturnType(), paramMethod.getParameterTypes());
/*  529 */         int i = flagsMods(65536, paramMethod.getModifiers(), (byte)5);
/*  530 */         init(MethodHandle.class, paramMethod.getName(), localMethodType, i);
/*  531 */         if (isMethodHandleInvoke())
/*  532 */           return;
/*      */       }
/*  534 */       throw new LinkageError(paramMethod.toString());
/*      */     }
/*  536 */     assert ((isResolved()) && (this.clazz != null));
/*  537 */     this.name = paramMethod.getName();
/*  538 */     if (this.type == null)
/*  539 */       this.type = new Object[] { paramMethod.getReturnType(), paramMethod.getParameterTypes() };
/*  540 */     if (paramBoolean) {
/*  541 */       if (isAbstract())
/*  542 */         throw new AbstractMethodError(toString());
/*  543 */       if (getReferenceKind() == 5) {
/*  544 */         changeReferenceKind((byte)7, (byte)5);
/*  545 */       } else if (getReferenceKind() == 9)
/*      */       {
/*  547 */         changeReferenceKind((byte)7, (byte)9); }
/*      */     }
/*      */   }
/*      */   
/*  551 */   public MemberName asSpecial() { switch (getReferenceKind()) {
/*  552 */     case 7:  return this;
/*  553 */     case 5:  return clone().changeReferenceKind((byte)7, (byte)5);
/*  554 */     case 9:  return clone().changeReferenceKind((byte)7, (byte)9);
/*  555 */     case 8:  return clone().changeReferenceKind((byte)7, (byte)8);
/*      */     }
/*  557 */     throw new IllegalArgumentException(toString());
/*      */   }
/*      */   
/*      */ 
/*      */   public MemberName asConstructor()
/*      */   {
/*  563 */     switch (getReferenceKind()) {
/*  564 */     case 7:  return clone().changeReferenceKind((byte)8, (byte)7);
/*  565 */     case 8:  return this;
/*      */     }
/*  567 */     throw new IllegalArgumentException(toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MemberName asNormalOriginal()
/*      */   {
/*  578 */     byte b1 = this.clazz.isInterface() ? 9 : 5;
/*  579 */     byte b2 = getReferenceKind();
/*  580 */     byte b3 = b2;
/*  581 */     MemberName localMemberName = this;
/*  582 */     switch (b2) {
/*      */     case 5: 
/*      */     case 7: 
/*      */     case 9: 
/*  586 */       b3 = b1;
/*      */     }
/*      */     
/*  589 */     if (b3 == b2)
/*  590 */       return this;
/*  591 */     localMemberName = clone().changeReferenceKind(b3, b2);
/*  592 */     assert (referenceKindIsConsistentWith(localMemberName.getReferenceKind()));
/*  593 */     return localMemberName;
/*      */   }
/*      */   
/*      */   public MemberName(Constructor<?> paramConstructor)
/*      */   {
/*  598 */     paramConstructor.getClass();
/*      */     
/*  600 */     MethodHandleNatives.init(this, paramConstructor);
/*  601 */     assert ((isResolved()) && (this.clazz != null));
/*  602 */     this.name = "<init>";
/*  603 */     if (this.type == null) {
/*  604 */       this.type = new Object[] { Void.TYPE, paramConstructor.getParameterTypes() };
/*      */     }
/*      */   }
/*      */   
/*      */   public MemberName(Field paramField) {
/*  609 */     this(paramField, false);
/*      */   }
/*      */   
/*      */   public MemberName(Field paramField, boolean paramBoolean) {
/*  613 */     paramField.getClass();
/*      */     
/*  615 */     MethodHandleNatives.init(this, paramField);
/*  616 */     assert ((isResolved()) && (this.clazz != null));
/*  617 */     this.name = paramField.getName();
/*  618 */     this.type = paramField.getType();
/*      */     
/*  620 */     byte b = getReferenceKind();
/*  621 */     if (!$assertionsDisabled) if (b != (isStatic() ? 2 : 1)) throw new AssertionError();
/*  622 */     if (paramBoolean)
/*  623 */       changeReferenceKind((byte)(b + 2), b);
/*      */   }
/*      */   
/*      */   public boolean isGetter() {
/*  627 */     return MethodHandleNatives.refKindIsGetter(getReferenceKind());
/*      */   }
/*      */   
/*  630 */   public boolean isSetter() { return MethodHandleNatives.refKindIsSetter(getReferenceKind()); }
/*      */   
/*      */   public MemberName asSetter() {
/*  633 */     byte b1 = getReferenceKind();
/*  634 */     assert (MethodHandleNatives.refKindIsGetter(b1));
/*      */     
/*  636 */     byte b2 = (byte)(b1 + 2);
/*  637 */     return clone().changeReferenceKind(b2, b1);
/*      */   }
/*      */   
/*      */   public MemberName(Class<?> paramClass) {
/*  641 */     init(paramClass.getDeclaringClass(), paramClass.getSimpleName(), paramClass, 
/*  642 */       flagsMods(524288, paramClass.getModifiers(), (byte)0));
/*  643 */     initResolved(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  653 */   static MemberName makeMethodHandleInvoke(String paramString, MethodType paramMethodType) { return makeMethodHandleInvoke(paramString, paramMethodType, 4369); }
/*      */   
/*      */   static MemberName makeMethodHandleInvoke(String paramString, MethodType paramMethodType, int paramInt) {
/*  656 */     MemberName localMemberName = new MemberName(MethodHandle.class, paramString, paramMethodType, (byte)5);
/*  657 */     localMemberName.flags |= paramInt;
/*  658 */     assert (localMemberName.isMethodHandleInvoke()) : localMemberName;
/*  659 */     return localMemberName;
/*      */   }
/*      */   
/*      */   MemberName() {}
/*      */   
/*      */   protected MemberName clone()
/*      */   {
/*      */     try
/*      */     {
/*  668 */       return (MemberName)super.clone();
/*      */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  670 */       throw MethodHandleStatics.newInternalError(localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public MemberName getDefinition()
/*      */   {
/*  678 */     if (!isResolved()) throw new IllegalStateException("must be resolved: " + this);
/*  679 */     if (isType()) return this;
/*  680 */     MemberName localMemberName = clone();
/*  681 */     localMemberName.clazz = null;
/*  682 */     localMemberName.type = null;
/*  683 */     localMemberName.name = null;
/*  684 */     localMemberName.resolution = localMemberName;
/*  685 */     localMemberName.expandFromVM();
/*  686 */     assert (localMemberName.getName().equals(getName()));
/*  687 */     return localMemberName;
/*      */   }
/*      */   
/*      */   public int hashCode()
/*      */   {
/*  692 */     return Objects.hash(new Object[] { this.clazz, Byte.valueOf(getReferenceKind()), this.name, getType() });
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  696 */     return ((paramObject instanceof MemberName)) && (equals((MemberName)paramObject));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(MemberName paramMemberName)
/*      */   {
/*  705 */     if (this == paramMemberName) return true;
/*  706 */     if (paramMemberName == null) { return false;
/*      */     }
/*      */     
/*      */ 
/*  710 */     return (this.clazz == paramMemberName.clazz) && (getReferenceKind() == paramMemberName.getReferenceKind()) && (Objects.equals(this.name, paramMemberName.name)) && (Objects.equals(getType(), paramMemberName.getType()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MemberName(Class<?> paramClass1, String paramString, Class<?> paramClass2, byte paramByte)
/*      */   {
/*  720 */     init(paramClass1, paramString, paramClass2, flagsMods(262144, 0, paramByte));
/*  721 */     initResolved(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public MemberName(Class<?> paramClass1, String paramString, Class<?> paramClass2, Void paramVoid)
/*      */   {
/*  729 */     this(paramClass1, paramString, paramClass2, (byte)0);
/*  730 */     initResolved(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MemberName(Class<?> paramClass, String paramString, MethodType paramMethodType, byte paramByte)
/*      */   {
/*  739 */     int i = (paramString != null) && (paramString.equals("<init>")) ? 131072 : 65536;
/*  740 */     init(paramClass, paramString, paramMethodType, flagsMods(i, 0, paramByte));
/*  741 */     initResolved(false);
/*      */   }
/*      */   
/*      */ 
/*      */   public MemberName(byte paramByte, Class<?> paramClass, String paramString, Object paramObject)
/*      */   {
/*      */     int i;
/*  748 */     if (MethodHandleNatives.refKindIsField(paramByte)) {
/*  749 */       i = 262144;
/*  750 */       if (!(paramObject instanceof Class))
/*  751 */         throw MethodHandleStatics.newIllegalArgumentException("not a field type");
/*  752 */     } else if (MethodHandleNatives.refKindIsMethod(paramByte)) {
/*  753 */       i = 65536;
/*  754 */       if (!(paramObject instanceof MethodType))
/*  755 */         throw MethodHandleStatics.newIllegalArgumentException("not a method type");
/*  756 */     } else if (paramByte == 8) {
/*  757 */       i = 131072;
/*  758 */       if ((!(paramObject instanceof MethodType)) || 
/*  759 */         (!"<init>".equals(paramString)))
/*  760 */         throw MethodHandleStatics.newIllegalArgumentException("not a constructor type or name");
/*      */     } else {
/*  762 */       throw MethodHandleStatics.newIllegalArgumentException("bad reference kind " + paramByte);
/*      */     }
/*  764 */     init(paramClass, paramString, paramObject, flagsMods(i, 0, paramByte));
/*  765 */     initResolved(false);
/*      */   }
/*      */   
/*      */   public boolean hasReceiverTypeDispatch()
/*      */   {
/*  770 */     return MethodHandleNatives.refKindDoesDispatch(getReferenceKind());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isResolved()
/*      */   {
/*  779 */     return this.resolution == null;
/*      */   }
/*      */   
/*      */   private void initResolved(boolean paramBoolean) {
/*  783 */     assert (this.resolution == null);
/*  784 */     if (!paramBoolean)
/*  785 */       this.resolution = this;
/*  786 */     assert (isResolved() == paramBoolean);
/*      */   }
/*      */   
/*      */   void checkForTypeAlias() { Object localObject;
/*  790 */     if (isInvocable())
/*      */     {
/*  792 */       if ((this.type instanceof MethodType)) {
/*  793 */         localObject = (MethodType)this.type;
/*      */       } else
/*  795 */         this.type = (localObject = getMethodType());
/*  796 */       if (((MethodType)localObject).erase() == localObject) return;
/*  797 */       if (VerifyAccess.isTypeVisible((MethodType)localObject, this.clazz)) return;
/*  798 */       throw new LinkageError("bad method type alias: " + localObject + " not visible from " + this.clazz);
/*      */     }
/*      */     
/*  801 */     if ((this.type instanceof Class)) {
/*  802 */       localObject = (Class)this.type;
/*      */     } else
/*  804 */       this.type = (localObject = getFieldType());
/*  805 */     if (VerifyAccess.isTypeVisible((Class)localObject, this.clazz)) return;
/*  806 */     throw new LinkageError("bad field type alias: " + localObject + " not visible from " + this.clazz);
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
/*  821 */     if (isType()) {
/*  822 */       return this.type.toString();
/*      */     }
/*  824 */     StringBuilder localStringBuilder = new StringBuilder();
/*  825 */     if (getDeclaringClass() != null) {
/*  826 */       localStringBuilder.append(getName(this.clazz));
/*  827 */       localStringBuilder.append('.');
/*      */     }
/*  829 */     String str = getName();
/*  830 */     localStringBuilder.append(str == null ? "*" : str);
/*  831 */     Object localObject = getType();
/*  832 */     if (!isInvocable()) {
/*  833 */       localStringBuilder.append('/');
/*  834 */       localStringBuilder.append(localObject == null ? "*" : getName(localObject));
/*      */     } else {
/*  836 */       localStringBuilder.append(localObject == null ? "(*)*" : getName(localObject));
/*      */     }
/*  838 */     byte b = getReferenceKind();
/*  839 */     if (b != 0) {
/*  840 */       localStringBuilder.append('/');
/*  841 */       localStringBuilder.append(MethodHandleNatives.refKindName(b));
/*      */     }
/*      */     
/*  844 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*  847 */   private static String getName(Object paramObject) { if ((paramObject instanceof Class))
/*  848 */       return ((Class)paramObject).getName();
/*  849 */     return String.valueOf(paramObject);
/*      */   }
/*      */   
/*      */   public IllegalAccessException makeAccessException(String paramString, Object paramObject) {
/*  853 */     paramString = paramString + ": " + toString();
/*  854 */     if (paramObject != null) paramString = paramString + ", from " + paramObject;
/*  855 */     return new IllegalAccessException(paramString);
/*      */   }
/*      */   
/*  858 */   private String message() { if (isResolved())
/*  859 */       return "no access";
/*  860 */     if (isConstructor())
/*  861 */       return "no such constructor";
/*  862 */     if (isMethod()) {
/*  863 */       return "no such method";
/*      */     }
/*  865 */     return "no such field";
/*      */   }
/*      */   
/*  868 */   public ReflectiveOperationException makeAccessException() { String str = message() + ": " + toString();
/*      */     Object localObject;
/*  870 */     if ((isResolved()) || ((!(this.resolution instanceof NoSuchMethodError)) && (!(this.resolution instanceof NoSuchFieldError))))
/*      */     {
/*  872 */       localObject = new IllegalAccessException(str);
/*  873 */     } else if (isConstructor()) {
/*  874 */       localObject = new NoSuchMethodException(str);
/*  875 */     } else if (isMethod()) {
/*  876 */       localObject = new NoSuchMethodException(str);
/*      */     } else
/*  878 */       localObject = new NoSuchFieldException(str);
/*  879 */     if ((this.resolution instanceof Throwable))
/*  880 */       ((ReflectiveOperationException)localObject).initCause((Throwable)this.resolution);
/*  881 */     return (ReflectiveOperationException)localObject;
/*      */   }
/*      */   
/*      */   static Factory getFactory()
/*      */   {
/*  886 */     return Factory.INSTANCE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static class Factory
/*      */   {
/*  893 */     static Factory INSTANCE = new Factory();
/*      */     
/*  895 */     private static int ALLOWED_FLAGS = 983040;
/*      */     
/*      */ 
/*      */ 
/*      */     List<MemberName> getMembers(Class<?> paramClass1, String paramString, Object paramObject, int paramInt, Class<?> paramClass2)
/*      */     {
/*  901 */       paramInt &= ALLOWED_FLAGS;
/*  902 */       String str = null;
/*  903 */       if (paramObject != null) {
/*  904 */         str = BytecodeDescriptor.unparse(paramObject);
/*  905 */         if (str.startsWith("(")) {
/*  906 */           paramInt &= 0xFFF3FFFF;
/*      */         } else {
/*  908 */           paramInt &= 0xFFF4FFFF;
/*      */         }
/*      */       }
/*  911 */       int i = paramObject == null ? 4 : paramString == null ? 10 : 1;
/*  912 */       MemberName[] arrayOfMemberName = newMemberBuffer(i);
/*  913 */       int j = 0;
/*  914 */       ArrayList localArrayList1 = null;
/*  915 */       int k = 0;
/*      */       for (;;) {
/*  917 */         k = MethodHandleNatives.getMembers(paramClass1, paramString, str, paramInt, paramClass2, j, arrayOfMemberName);
/*      */         
/*      */ 
/*      */ 
/*  921 */         if (k <= arrayOfMemberName.length) {
/*  922 */           if (k < 0) k = 0;
/*  923 */           j += k;
/*  924 */           break;
/*      */         }
/*      */         
/*  927 */         j += arrayOfMemberName.length;
/*  928 */         int m = k - arrayOfMemberName.length;
/*  929 */         if (localArrayList1 == null) localArrayList1 = new ArrayList(1);
/*  930 */         localArrayList1.add(arrayOfMemberName);
/*  931 */         int n = arrayOfMemberName.length;
/*  932 */         n = Math.max(n, m);
/*  933 */         n = Math.max(n, j / 4);
/*  934 */         arrayOfMemberName = newMemberBuffer(Math.min(8192, n));
/*      */       }
/*  936 */       ArrayList localArrayList2 = new ArrayList(j);
/*  937 */       Iterator localIterator; if (localArrayList1 != null)
/*  938 */         for (localIterator = localArrayList1.iterator(); localIterator.hasNext();) { localObject = (MemberName[])localIterator.next();
/*  939 */           Collections.addAll(localArrayList2, (Object[])localObject);
/*      */         }
/*      */       Object localObject;
/*  942 */       localArrayList2.addAll(Arrays.asList(arrayOfMemberName).subList(0, k));
/*      */       
/*      */ 
/*      */ 
/*  946 */       if ((paramObject != null) && (paramObject != str)) {
/*  947 */         for (localIterator = localArrayList2.iterator(); localIterator.hasNext();) {
/*  948 */           localObject = (MemberName)localIterator.next();
/*  949 */           if (!paramObject.equals(((MemberName)localObject).getType()))
/*  950 */             localIterator.remove();
/*      */         }
/*      */       }
/*  953 */       return localArrayList2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private MemberName resolve(byte paramByte, MemberName paramMemberName, Class<?> paramClass)
/*      */     {
/*  962 */       MemberName localMemberName = paramMemberName.clone();
/*  963 */       assert (paramByte == localMemberName.getReferenceKind());
/*      */       try {
/*  965 */         localMemberName = MethodHandleNatives.resolve(localMemberName, paramClass);
/*  966 */         localMemberName.checkForTypeAlias();
/*  967 */         localMemberName.resolution = null;
/*      */       }
/*      */       catch (LinkageError localLinkageError) {
/*  970 */         assert (!localMemberName.isResolved());
/*  971 */         localMemberName.resolution = localLinkageError;
/*  972 */         return localMemberName;
/*      */       }
/*  974 */       assert (localMemberName.referenceKindIsConsistent());
/*  975 */       localMemberName.initResolved(true);
/*  976 */       assert (localMemberName.vminfoIsConsistent());
/*  977 */       return localMemberName;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public <NoSuchMemberException extends ReflectiveOperationException> MemberName resolveOrFail(byte paramByte, MemberName paramMemberName, Class<?> paramClass, Class<NoSuchMemberException> paramClass1)
/*      */       throws IllegalAccessException, ReflectiveOperationException
/*      */     {
/*  990 */       MemberName localMemberName = resolve(paramByte, paramMemberName, paramClass);
/*  991 */       if (localMemberName.isResolved())
/*  992 */         return localMemberName;
/*  993 */       ReflectiveOperationException localReflectiveOperationException = localMemberName.makeAccessException();
/*  994 */       if ((localReflectiveOperationException instanceof IllegalAccessException)) throw ((IllegalAccessException)localReflectiveOperationException);
/*  995 */       throw ((ReflectiveOperationException)paramClass1.cast(localReflectiveOperationException));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public MemberName resolveOrNull(byte paramByte, MemberName paramMemberName, Class<?> paramClass)
/*      */     {
/* 1005 */       MemberName localMemberName = resolve(paramByte, paramMemberName, paramClass);
/* 1006 */       if (localMemberName.isResolved())
/* 1007 */         return localMemberName;
/* 1008 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<MemberName> getMethods(Class<?> paramClass1, boolean paramBoolean, Class<?> paramClass2)
/*      */     {
/* 1017 */       return getMethods(paramClass1, paramBoolean, null, null, paramClass2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<MemberName> getMethods(Class<?> paramClass1, boolean paramBoolean, String paramString, MethodType paramMethodType, Class<?> paramClass2)
/*      */     {
/* 1027 */       int i = 0x10000 | (paramBoolean ? 3145728 : 0);
/* 1028 */       return getMembers(paramClass1, paramString, paramMethodType, i, paramClass2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public List<MemberName> getConstructors(Class<?> paramClass1, Class<?> paramClass2)
/*      */     {
/* 1035 */       return getMembers(paramClass1, null, null, 131072, paramClass2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<MemberName> getFields(Class<?> paramClass1, boolean paramBoolean, Class<?> paramClass2)
/*      */     {
/* 1044 */       return getFields(paramClass1, paramBoolean, null, null, paramClass2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<MemberName> getFields(Class<?> paramClass1, boolean paramBoolean, String paramString, Class<?> paramClass2, Class<?> paramClass3)
/*      */     {
/* 1054 */       int i = 0x40000 | (paramBoolean ? 3145728 : 0);
/* 1055 */       return getMembers(paramClass1, paramString, paramClass2, i, paramClass3);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<MemberName> getNestedTypes(Class<?> paramClass1, boolean paramBoolean, Class<?> paramClass2)
/*      */     {
/* 1064 */       int i = 0x80000 | (paramBoolean ? 3145728 : 0);
/* 1065 */       return getMembers(paramClass1, null, null, i, paramClass2);
/*      */     }
/*      */     
/* 1068 */     private static MemberName[] newMemberBuffer(int paramInt) { MemberName[] arrayOfMemberName = new MemberName[paramInt];
/*      */       
/* 1070 */       for (int i = 0; i < paramInt; i++)
/* 1071 */         arrayOfMemberName[i] = new MemberName();
/* 1072 */       return arrayOfMemberName;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MemberName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */