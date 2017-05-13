/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.annotation.Retention;
/*      */ import java.lang.annotation.RetentionPolicy;
/*      */ import java.lang.annotation.Target;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.invoke.util.Wrapper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class LambdaForm
/*      */ {
/*      */   final int arity;
/*      */   final int result;
/*      */   @Stable
/*      */   final Name[] names;
/*      */   final String debugName;
/*      */   MemberName vmentry;
/*      */   private boolean isCompiled;
/*      */   LambdaForm[] bindCache;
/*      */   public static final int VOID_RESULT = -1;
/*      */   public static final int LAST_RESULT = -2;
/*      */   private static final ConcurrentHashMap<String, LambdaForm> PREPARED_FORMS;
/*      */   private static final boolean USE_PREDEFINED_INTERPRET_METHODS = true;
/*      */   private static final int COMPILE_THRESHOLD;
/*      */   
/*      */   LambdaForm(String paramString, int paramInt1, Name[] paramArrayOfName, int paramInt2)
/*      */   {
/*  135 */     assert (namesOK(paramInt1, paramArrayOfName));
/*  136 */     this.arity = paramInt1;
/*  137 */     this.result = fixResult(paramInt2, paramArrayOfName);
/*  138 */     this.names = ((Name[])paramArrayOfName.clone());
/*  139 */     this.debugName = paramString;
/*  140 */     normalize();
/*      */   }
/*      */   
/*      */   LambdaForm(String paramString, int paramInt, Name[] paramArrayOfName)
/*      */   {
/*  145 */     this(paramString, paramInt, paramArrayOfName, -2);
/*      */   }
/*      */   
/*      */ 
/*      */   LambdaForm(String paramString, Name[] paramArrayOfName1, Name[] paramArrayOfName2, Name paramName)
/*      */   {
/*  151 */     this(paramString, paramArrayOfName1.length, 
/*  152 */       buildNames(paramArrayOfName1, paramArrayOfName2, paramName), -2);
/*      */   }
/*      */   
/*      */   private static Name[] buildNames(Name[] paramArrayOfName1, Name[] paramArrayOfName2, Name paramName) {
/*  156 */     int i = paramArrayOfName1.length;
/*  157 */     int j = i + paramArrayOfName2.length + (paramName == null ? 0 : 1);
/*  158 */     Name[] arrayOfName = (Name[])Arrays.copyOf(paramArrayOfName1, j);
/*  159 */     System.arraycopy(paramArrayOfName2, 0, arrayOfName, i, paramArrayOfName2.length);
/*  160 */     if (paramName != null)
/*  161 */       arrayOfName[(j - 1)] = paramName;
/*  162 */     return arrayOfName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private LambdaForm(String paramString)
/*      */   {
/*  169 */     assert (isValidSignature(paramString));
/*  170 */     this.arity = signatureArity(paramString);
/*  171 */     this.result = (signatureReturn(paramString) == 'V' ? -1 : this.arity);
/*  172 */     this.names = buildEmptyNames(this.arity, paramString);
/*  173 */     this.debugName = "LF.zero";
/*  174 */     assert (nameRefsAreLegal());
/*  175 */     assert (isEmpty());
/*  176 */     assert (paramString.equals(basicTypeSignature()));
/*      */   }
/*      */   
/*      */   private static Name[] buildEmptyNames(int paramInt, String paramString) {
/*  180 */     assert (isValidSignature(paramString));
/*  181 */     int i = paramInt + 1;
/*  182 */     if ((paramInt < 0) || (paramString.length() != i + 1))
/*  183 */       throw new IllegalArgumentException("bad arity for " + paramString);
/*  184 */     int j = paramString.charAt(i) == 'V' ? 0 : 1;
/*  185 */     Name[] arrayOfName = arguments(j, paramString.substring(0, paramInt));
/*  186 */     for (int k = 0; k < j; k++) {
/*  187 */       arrayOfName[(paramInt + k)] = constantZero(paramInt + k, paramString.charAt(i + k));
/*      */     }
/*  189 */     return arrayOfName;
/*      */   }
/*      */   
/*      */   private static int fixResult(int paramInt, Name[] paramArrayOfName) {
/*  193 */     if (paramInt >= 0) {
/*  194 */       if (paramArrayOfName[paramInt].type == 'V')
/*  195 */         return -1;
/*  196 */     } else if (paramInt == -2) {
/*  197 */       return paramArrayOfName.length - 1;
/*      */     }
/*  199 */     return paramInt;
/*      */   }
/*      */   
/*      */   private static boolean namesOK(int paramInt, Name[] paramArrayOfName) {
/*  203 */     for (int i = 0; i < paramArrayOfName.length; i++) {
/*  204 */       Name localName = paramArrayOfName[i];
/*  205 */       assert (localName != null) : "n is null";
/*  206 */       if (i < paramInt) {
/*  207 */         if ((!$assertionsDisabled) && (!localName.isParam())) throw new AssertionError(localName + " is not param at " + i);
/*      */       } else
/*  209 */         assert (!localName.isParam()) : (localName + " is param at " + i);
/*      */     }
/*  211 */     return true;
/*      */   }
/*      */   
/*      */   private void normalize()
/*      */   {
/*  216 */     Name[] arrayOfName = null;
/*  217 */     int i = 0;
/*  218 */     Name localName2; for (Name localName1 = 0; localName1 < this.names.length; localName1++) {
/*  219 */       localName2 = this.names[localName1];
/*  220 */       if (!localName2.initIndex(localName1)) {
/*  221 */         if (arrayOfName == null) {
/*  222 */           arrayOfName = (Name[])this.names.clone();
/*  223 */           i = localName1;
/*      */         }
/*  225 */         this.names[localName1] = localName2.cloneWithIndex(localName1);
/*      */       }
/*      */     }
/*  228 */     if (arrayOfName != null) {
/*  229 */       localName1 = this.arity;
/*  230 */       if (localName1 <= i)
/*  231 */         localName1 = i + 1;
/*  232 */       for (localName2 = localName1; localName2 < this.names.length; localName2++) {
/*  233 */         Name localName3 = this.names[localName2].replaceNames(arrayOfName, this.names, i, localName2);
/*  234 */         this.names[localName2] = localName3.newIndex(localName2);
/*      */       }
/*      */     }
/*  237 */     assert (nameRefsAreLegal());
/*  238 */     localName1 = Math.min(this.arity, 10);
/*  239 */     int j = 0;
/*  240 */     for (int k = 0; k < localName1; k++) {
/*  241 */       Name localName4 = this.names[k];Name localName5 = internArgument(localName4);
/*  242 */       if (localName4 != localName5) {
/*  243 */         this.names[k] = localName5;
/*  244 */         j = 1;
/*      */       }
/*      */     }
/*  247 */     if (j != 0) {
/*  248 */       for (k = this.arity; k < this.names.length; k++) {
/*  249 */         this.names[k].internArguments();
/*      */       }
/*  251 */       assert (nameRefsAreLegal());
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
/*      */   private boolean nameRefsAreLegal()
/*      */   {
/*  265 */     assert ((this.arity >= 0) && (this.arity <= this.names.length));
/*  266 */     assert ((this.result >= -1) && (this.result < this.names.length));
/*      */     Name localName1;
/*  268 */     for (int i = 0; i < this.arity; i++) {
/*  269 */       localName1 = this.names[i];
/*  270 */       if ((!$assertionsDisabled) && (localName1.index() != i)) throw new AssertionError(Arrays.asList(new Integer[] { Integer.valueOf(localName1.index()), Integer.valueOf(i) }));
/*  271 */       assert (localName1.isParam());
/*      */     }
/*      */     
/*  274 */     for (i = this.arity; i < this.names.length; i++) {
/*  275 */       localName1 = this.names[i];
/*  276 */       assert (localName1.index() == i);
/*  277 */       for (Object localObject : localName1.arguments) {
/*  278 */         if ((localObject instanceof Name)) {
/*  279 */           Name localName2 = (Name)localObject;
/*  280 */           int m = localName2.index;
/*  281 */           assert ((0 <= m) && (m < this.names.length)) : (localName1.debugString() + ": 0 <= i2 && i2 < names.length: 0 <= " + m + " < " + this.names.length);
/*  282 */           if ((!$assertionsDisabled) && (this.names[m] != localName2)) throw new AssertionError(Arrays.asList(new Object[] { "-1-", Integer.valueOf(i), "-2-", localName1.debugString(), "-3-", Integer.valueOf(m), "-4-", localName2.debugString(), "-5-", this.names[m].debugString(), "-6-", this }));
/*  283 */           assert (m < i);
/*      */         }
/*      */       }
/*      */     }
/*  287 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   char returnType()
/*      */   {
/*  298 */     if (this.result < 0) return 'V';
/*  299 */     Name localName = this.names[this.result];
/*  300 */     return localName.type;
/*      */   }
/*      */   
/*      */   char parameterType(int paramInt)
/*      */   {
/*  305 */     assert (paramInt < this.arity);
/*  306 */     return this.names[paramInt].type;
/*      */   }
/*      */   
/*      */   int arity()
/*      */   {
/*  311 */     return this.arity;
/*      */   }
/*      */   
/*      */   MethodType methodType()
/*      */   {
/*  316 */     return signatureType(basicTypeSignature());
/*      */   }
/*      */   
/*      */   final String basicTypeSignature() {
/*  320 */     StringBuilder localStringBuilder = new StringBuilder(arity() + 3);
/*  321 */     int i = 0; for (int j = arity(); i < j; i++)
/*  322 */       localStringBuilder.append(parameterType(i));
/*  323 */     return '_' + returnType();
/*      */   }
/*      */   
/*  326 */   static int signatureArity(String paramString) { assert (isValidSignature(paramString));
/*  327 */     return paramString.indexOf('_');
/*      */   }
/*      */   
/*  330 */   static char signatureReturn(String paramString) { return paramString.charAt(signatureArity(paramString) + 1); }
/*      */   
/*      */   static boolean isValidSignature(String paramString) {
/*  333 */     int i = paramString.indexOf('_');
/*  334 */     if (i < 0) return false;
/*  335 */     int j = paramString.length();
/*  336 */     if (j != i + 2) return false;
/*  337 */     for (int k = 0; k < j; k++)
/*  338 */       if (k != i) {
/*  339 */         int m = paramString.charAt(k);
/*  340 */         if (m == 86)
/*  341 */           return (k == j - 1) && (i == j - 2);
/*  342 */         if ("LIJFD".indexOf(m) < 0) return false;
/*      */       }
/*  344 */     return true;
/*      */   }
/*      */   
/*  347 */   static Class<?> typeClass(char paramChar) { switch (paramChar) {
/*  348 */     case 'I':  return Integer.TYPE;
/*  349 */     case 'J':  return Long.TYPE;
/*  350 */     case 'F':  return Float.TYPE;
/*  351 */     case 'D':  return Double.TYPE;
/*  352 */     case 'L':  return Object.class;
/*  353 */     case 'V':  return Void.TYPE; }
/*  354 */     if (!$assertionsDisabled) { throw new AssertionError();
/*      */     }
/*  356 */     return null;
/*      */   }
/*      */   
/*  359 */   static MethodType signatureType(String paramString) { Class[] arrayOfClass = new Class[signatureArity(paramString)];
/*  360 */     for (int i = 0; i < arrayOfClass.length; i++)
/*  361 */       arrayOfClass[i] = typeClass(paramString.charAt(i));
/*  362 */     Class localClass = typeClass(signatureReturn(paramString));
/*  363 */     return MethodType.methodType(localClass, arrayOfClass);
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
/*      */   public void prepare()
/*      */   {
/*  434 */     if (COMPILE_THRESHOLD == 0) {
/*  435 */       compileToBytecode();
/*      */     }
/*  437 */     if (this.vmentry != null)
/*      */     {
/*  439 */       return;
/*      */     }
/*  441 */     LambdaForm localLambdaForm = getPreparedForm(basicTypeSignature());
/*  442 */     this.vmentry = localLambdaForm.vmentry;
/*      */   }
/*      */   
/*      */ 
/*      */   MemberName compileToBytecode()
/*      */   {
/*  448 */     MethodType localMethodType = methodType();
/*  449 */     assert ((this.vmentry == null) || (this.vmentry.getMethodType().basicType().equals(localMethodType)));
/*  450 */     if ((this.vmentry != null) && (this.isCompiled)) {
/*  451 */       return this.vmentry;
/*      */     }
/*      */     try {
/*  454 */       this.vmentry = InvokerBytecodeGenerator.generateCustomizedCode(this, localMethodType);
/*  455 */       if (TRACE_INTERPRETER)
/*  456 */         traceInterpreter("compileToBytecode", this);
/*  457 */       this.isCompiled = true;
/*  458 */       return this.vmentry;
/*      */     } catch (Error|Exception localError) {
/*  460 */       throw MethodHandleStatics.newInternalError("compileToBytecode", localError);
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
/*      */   private static Map<String, LambdaForm> computeInitialPreparedForms()
/*      */   {
/*  474 */     HashMap localHashMap = new HashMap();
/*  475 */     for (MemberName localMemberName : MemberName.getFactory().getMethods(LambdaForm.class, false, null, null, null)) {
/*  476 */       if ((localMemberName.isStatic()) && (localMemberName.isPackage())) {
/*  477 */         MethodType localMethodType = localMemberName.getMethodType();
/*  478 */         if ((localMethodType.parameterCount() > 0) && 
/*  479 */           (localMethodType.parameterType(0) == MethodHandle.class) && 
/*  480 */           (localMemberName.getName().startsWith("interpret_"))) {
/*  481 */           String str = basicTypeSignature(localMethodType);
/*  482 */           assert (localMemberName.getName().equals("interpret" + str.substring(str.indexOf('_'))));
/*  483 */           LambdaForm localLambdaForm = new LambdaForm(str);
/*  484 */           localLambdaForm.vmentry = localMemberName;
/*  485 */           localLambdaForm = localMethodType.form().setCachedLambdaForm(7, localLambdaForm);
/*      */           
/*  487 */           localHashMap.put(str, localLambdaForm);
/*      */         }
/*      */       }
/*      */     }
/*  491 */     return localHashMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static Object interpret_L(MethodHandle paramMethodHandle)
/*      */     throws Throwable
/*      */   {
/*  500 */     Object[] arrayOfObject = { paramMethodHandle };
/*  501 */     String str = null;
/*  502 */     assert (argumentTypesMatch(str = "L_L", arrayOfObject));
/*  503 */     Object localObject = paramMethodHandle.form.interpretWithArguments(arrayOfObject);
/*  504 */     assert (returnTypesMatch(str, arrayOfObject, localObject));
/*  505 */     return localObject;
/*      */   }
/*      */   
/*  508 */   static Object interpret_L(MethodHandle paramMethodHandle, Object paramObject) throws Throwable { Object[] arrayOfObject = { paramMethodHandle, paramObject };
/*  509 */     String str = null;
/*  510 */     assert (argumentTypesMatch(str = "LL_L", arrayOfObject));
/*  511 */     Object localObject = paramMethodHandle.form.interpretWithArguments(arrayOfObject);
/*  512 */     assert (returnTypesMatch(str, arrayOfObject, localObject));
/*  513 */     return localObject;
/*      */   }
/*      */   
/*  516 */   static Object interpret_L(MethodHandle paramMethodHandle, Object paramObject1, Object paramObject2) throws Throwable { Object[] arrayOfObject = { paramMethodHandle, paramObject1, paramObject2 };
/*  517 */     String str = null;
/*  518 */     assert (argumentTypesMatch(str = "LLL_L", arrayOfObject));
/*  519 */     Object localObject = paramMethodHandle.form.interpretWithArguments(arrayOfObject);
/*  520 */     assert (returnTypesMatch(str, arrayOfObject, localObject));
/*  521 */     return localObject;
/*      */   }
/*      */   
/*  524 */   private static LambdaForm getPreparedForm(String paramString) { MethodType localMethodType = signatureType(paramString);
/*      */     
/*  526 */     LambdaForm localLambdaForm = localMethodType.form().cachedLambdaForm(6);
/*  527 */     if (localLambdaForm != null) return localLambdaForm;
/*  528 */     assert (isValidSignature(paramString));
/*  529 */     localLambdaForm = new LambdaForm(paramString);
/*  530 */     localLambdaForm.vmentry = InvokerBytecodeGenerator.generateLambdaFormInterpreterEntryPoint(paramString);
/*      */     
/*  532 */     return localMethodType.form().setCachedLambdaForm(6, localLambdaForm);
/*      */   }
/*      */   
/*      */ 
/*      */   private static boolean argumentTypesMatch(String paramString, Object[] paramArrayOfObject)
/*      */   {
/*  538 */     int i = signatureArity(paramString);
/*  539 */     assert (paramArrayOfObject.length == i) : ("av.length == arity: av.length=" + paramArrayOfObject.length + ", arity=" + i);
/*  540 */     assert ((paramArrayOfObject[0] instanceof MethodHandle)) : ("av[0] not instace of MethodHandle: " + paramArrayOfObject[0]);
/*  541 */     MethodHandle localMethodHandle = (MethodHandle)paramArrayOfObject[0];
/*  542 */     MethodType localMethodType = localMethodHandle.type();
/*  543 */     assert (localMethodType.parameterCount() == i - 1);
/*  544 */     for (int j = 0; j < paramArrayOfObject.length; j++) {
/*  545 */       Class localClass = j == 0 ? MethodHandle.class : localMethodType.parameterType(j - 1);
/*  546 */       assert (valueMatches(paramString.charAt(j), localClass, paramArrayOfObject[j]));
/*      */     }
/*  548 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean valueMatches(char paramChar, Class<?> paramClass, Object paramObject) {
/*  552 */     if (paramClass == Void.TYPE) paramChar = 'V';
/*  553 */     assert (paramChar == basicType(paramClass)) : (paramChar + " == basicType(" + paramClass + ")=" + basicType(paramClass));
/*  554 */     switch (paramChar) {
/*  555 */     case 'I':  if ((!$assertionsDisabled) && (!checkInt(paramClass, paramObject))) throw new AssertionError("checkInt(" + paramClass + "," + paramObject + ")");
/*      */       break; case 'J':  if ((!$assertionsDisabled) && (!(paramObject instanceof Long))) throw new AssertionError("instanceof Long: " + paramObject);
/*      */       break; case 'F':  if ((!$assertionsDisabled) && (!(paramObject instanceof Float))) throw new AssertionError("instanceof Float: " + paramObject);
/*      */       break; case 'D':  if ((!$assertionsDisabled) && (!(paramObject instanceof Double))) throw new AssertionError("instanceof Double: " + paramObject);
/*      */       break; case 'L':  if ((!$assertionsDisabled) && (!checkRef(paramClass, paramObject))) throw new AssertionError("checkRef(" + paramClass + "," + paramObject + ")");
/*      */       break;
/*      */     case 'V':  break; case 'E': case 'G': case 'H': case 'K': case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U': default:  if (!$assertionsDisabled) throw new AssertionError();
/*      */       break; }
/*  563 */     return true;
/*      */   }
/*      */   
/*  566 */   private static boolean returnTypesMatch(String paramString, Object[] paramArrayOfObject, Object paramObject) { MethodHandle localMethodHandle = (MethodHandle)paramArrayOfObject[0];
/*  567 */     return valueMatches(signatureReturn(paramString), localMethodHandle.type().returnType(), paramObject);
/*      */   }
/*      */   
/*  570 */   private static boolean checkInt(Class<?> paramClass, Object paramObject) { assert ((paramObject instanceof Integer));
/*  571 */     if (paramClass == Integer.TYPE) return true;
/*  572 */     Wrapper localWrapper = Wrapper.forBasicType(paramClass);
/*  573 */     assert (localWrapper.isSubwordOrInt());
/*  574 */     Object localObject = Wrapper.INT.wrap(localWrapper.wrap(paramObject));
/*  575 */     return paramObject.equals(localObject);
/*      */   }
/*      */   
/*  578 */   private static boolean checkRef(Class<?> paramClass, Object paramObject) { assert (!paramClass.isPrimitive());
/*  579 */     if (paramObject == null) return true;
/*  580 */     if (paramClass.isInterface()) return true;
/*  581 */     return paramClass.isInstance(paramObject);
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
/*  592 */   private int invocationCounter = 0;
/*      */   static final String ALL_TYPES = "LIJFD";
/*      */   static final int INTERNED_ARGUMENT_LIMIT = 10;
/*      */   
/*      */   @Hidden
/*      */   @DontInline
/*  598 */   Object interpretWithArguments(Object... paramVarArgs) throws Throwable { if (TRACE_INTERPRETER)
/*  599 */       return interpretWithArgumentsTracing(paramVarArgs);
/*  600 */     checkInvocationCounter();
/*  601 */     assert (arityCheck(paramVarArgs));
/*  602 */     Object[] arrayOfObject = Arrays.copyOf(paramVarArgs, this.names.length);
/*  603 */     for (int i = paramVarArgs.length; i < arrayOfObject.length; i++) {
/*  604 */       arrayOfObject[i] = interpretName(this.names[i], arrayOfObject);
/*      */     }
/*  606 */     return this.result < 0 ? null : arrayOfObject[this.result];
/*      */   }
/*      */   
/*      */   @Hidden
/*      */   @DontInline
/*      */   Object interpretName(Name paramName, Object[] paramArrayOfObject) throws Throwable
/*      */   {
/*  613 */     if (TRACE_INTERPRETER)
/*  614 */       traceInterpreter("| interpretName", paramName.debugString(), (Object[])null);
/*  615 */     Object[] arrayOfObject = Arrays.copyOf(paramName.arguments, paramName.arguments.length, Object[].class);
/*  616 */     for (int i = 0; i < arrayOfObject.length; i++) {
/*  617 */       Object localObject = arrayOfObject[i];
/*  618 */       if ((localObject instanceof Name)) {
/*  619 */         int j = ((Name)localObject).index();
/*  620 */         assert (this.names[j] == localObject);
/*  621 */         localObject = paramArrayOfObject[j];
/*  622 */         arrayOfObject[i] = localObject;
/*      */       }
/*      */     }
/*  625 */     return paramName.function.invokeWithArguments(arrayOfObject);
/*      */   }
/*      */   
/*      */   private void checkInvocationCounter() {
/*  629 */     if ((COMPILE_THRESHOLD != 0) && (this.invocationCounter < COMPILE_THRESHOLD))
/*      */     {
/*  631 */       this.invocationCounter += 1;
/*  632 */       if (this.invocationCounter >= COMPILE_THRESHOLD)
/*      */       {
/*  634 */         compileToBytecode(); }
/*      */     }
/*      */   }
/*      */   
/*      */   Object interpretWithArgumentsTracing(Object... paramVarArgs) throws Throwable {
/*  639 */     traceInterpreter("[ interpretWithArguments", this, paramVarArgs);
/*  640 */     if (this.invocationCounter < COMPILE_THRESHOLD) {
/*  641 */       int i = this.invocationCounter++;
/*  642 */       traceInterpreter("| invocationCounter", Integer.valueOf(i));
/*  643 */       if (this.invocationCounter >= COMPILE_THRESHOLD) {
/*  644 */         compileToBytecode();
/*      */       }
/*      */     }
/*      */     Object localObject;
/*      */     try {
/*  649 */       assert (arityCheck(paramVarArgs));
/*  650 */       Object[] arrayOfObject = Arrays.copyOf(paramVarArgs, this.names.length);
/*  651 */       for (int j = paramVarArgs.length; j < arrayOfObject.length; j++) {
/*  652 */         arrayOfObject[j] = interpretName(this.names[j], arrayOfObject);
/*      */       }
/*  654 */       localObject = this.result < 0 ? null : arrayOfObject[this.result];
/*      */     } catch (Throwable localThrowable) {
/*  656 */       traceInterpreter("] throw =>", localThrowable);
/*  657 */       throw localThrowable;
/*      */     }
/*  659 */     traceInterpreter("] return =>", localObject);
/*  660 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Name[][] INTERNED_ARGUMENTS;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final MemberName.Factory IMPL_NAMES;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Name[] CONSTANT_ZERO;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void traceInterpreter(String paramString, Object paramObject, Object... paramVarArgs)
/*      */   {
/*  686 */     if (TRACE_INTERPRETER)
/*  687 */       System.out.println("LFI: " + paramString + " " + (paramObject != null ? paramObject : "") + ((paramVarArgs != null) && (paramVarArgs.length != 0) ? Arrays.asList(paramVarArgs) : ""));
/*      */   }
/*      */   
/*      */   static void traceInterpreter(String paramString, Object paramObject) {
/*  691 */     traceInterpreter(paramString, paramObject, (Object[])null); }
/*      */   
/*      */   private boolean arityCheck(Object[] paramArrayOfObject) {
/*  694 */     assert (paramArrayOfObject.length == this.arity) : (this.arity + "!=" + Arrays.asList(paramArrayOfObject) + ".length");
/*      */     
/*  696 */     assert ((paramArrayOfObject[0] instanceof MethodHandle)) : ("not MH: " + paramArrayOfObject[0]);
/*  697 */     assert (((MethodHandle)paramArrayOfObject[0]).internalForm() == this);
/*      */     
/*  699 */     return true;
/*      */   }
/*      */   
/*      */   private boolean isEmpty() {
/*  703 */     if (this.result < 0)
/*  704 */       return this.names.length == this.arity;
/*  705 */     if ((this.result == this.arity) && (this.names.length == this.arity + 1)) {
/*  706 */       return this.names[this.arity].isConstantZero();
/*      */     }
/*  708 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  712 */     StringBuilder localStringBuilder = new StringBuilder(this.debugName + "=Lambda(");
/*  713 */     for (int i = 0; i < this.names.length; i++) {
/*  714 */       if (i == this.arity) localStringBuilder.append(")=>{");
/*  715 */       Name localName = this.names[i];
/*  716 */       if (i >= this.arity) localStringBuilder.append("\n    ");
/*  717 */       localStringBuilder.append(localName);
/*  718 */       if (i < this.arity) {
/*  719 */         if (i + 1 < this.arity) localStringBuilder.append(",");
/*      */       }
/*      */       else {
/*  722 */         localStringBuilder.append("=").append(localName.exprString());
/*  723 */         localStringBuilder.append(";");
/*      */       } }
/*  725 */     localStringBuilder.append(this.result < 0 ? "void" : this.names[this.result]).append("}");
/*  726 */     if (TRACE_INTERPRETER)
/*      */     {
/*  728 */       localStringBuilder.append(":").append(basicTypeSignature());
/*  729 */       localStringBuilder.append("/").append(this.vmentry);
/*      */     }
/*  731 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   LambdaForm bindImmediate(int paramInt, char paramChar, Object paramObject)
/*      */   {
/*  741 */     assert ((paramInt > 0) && (paramInt < this.arity) && (this.names[paramInt].type == paramChar) && (Name.typesMatch(paramChar, paramObject)));
/*      */     
/*  743 */     int i = this.arity - 1;
/*  744 */     Name[] arrayOfName = new Name[this.names.length - 1];
/*  745 */     int j = 0; for (int k = 0; j < this.names.length; k++) {
/*  746 */       Name localName = this.names[j];
/*  747 */       if (localName.isParam()) {
/*  748 */         if (localName.index == paramInt)
/*      */         {
/*      */ 
/*  751 */           k--;
/*      */         } else {
/*  753 */           arrayOfName[k] = new Name(k, localName.type);
/*      */         }
/*      */       } else {
/*  756 */         Object[] arrayOfObject = new Object[localName.arguments.length];
/*  757 */         for (int m = 0; m < localName.arguments.length; m++) {
/*  758 */           Object localObject = localName.arguments[m];
/*  759 */           if ((localObject instanceof Name)) {
/*  760 */             int n = ((Name)localObject).index;
/*  761 */             if (n == paramInt) {
/*  762 */               arrayOfObject[m] = paramObject;
/*  763 */             } else if (n < paramInt)
/*      */             {
/*  765 */               arrayOfObject[m] = arrayOfName[n];
/*      */             }
/*      */             else {
/*  768 */               arrayOfObject[m] = arrayOfName[(n - 1)];
/*      */             }
/*      */           } else {
/*  771 */             arrayOfObject[m] = localObject;
/*      */           }
/*      */         }
/*  774 */         arrayOfName[k] = new Name(localName.function, arrayOfObject);
/*  775 */         arrayOfName[k].initIndex(k);
/*      */       }
/*  745 */       j++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  779 */     j = this.result == -1 ? -1 : this.result - 1;
/*  780 */     return new LambdaForm(this.debugName, i, arrayOfName, j);
/*      */   }
/*      */   
/*      */   LambdaForm bind(int paramInt, BoundMethodHandle.SpeciesData paramSpeciesData) {
/*  784 */     Name localName = this.names[paramInt];
/*  785 */     BoundMethodHandle.SpeciesData localSpeciesData = paramSpeciesData.extendWithType(localName.type);
/*  786 */     return bind(localName, localSpeciesData.getterName(this.names[0], paramSpeciesData.fieldCount()), paramSpeciesData, localSpeciesData);
/*      */   }
/*      */   
/*      */   LambdaForm bind(Name paramName1, Name paramName2, BoundMethodHandle.SpeciesData paramSpeciesData1, BoundMethodHandle.SpeciesData paramSpeciesData2)
/*      */   {
/*  791 */     int i = paramName1.index;
/*  792 */     assert (paramName1.isParam());
/*  793 */     assert (!paramName2.isParam());
/*  794 */     assert (paramName1.type == paramName2.type);
/*  795 */     assert ((0 <= i) && (i < this.arity) && (this.names[i] == paramName1));
/*  796 */     assert (paramName2.function.memberDeclaringClassOrNull() == paramSpeciesData2.clazz);
/*  797 */     assert (paramSpeciesData1.getters.length == paramSpeciesData2.getters.length - 1);
/*  798 */     if (this.bindCache != null) {
/*  799 */       LambdaForm localLambdaForm = this.bindCache[i];
/*  800 */       if (localLambdaForm != null) {
/*  801 */         assert (localLambdaForm.contains(paramName2)) : ("form << " + localLambdaForm + " >> does not contain binding << " + paramName2 + " >>");
/*  802 */         return localLambdaForm;
/*      */       }
/*      */     } else {
/*  805 */       this.bindCache = new LambdaForm[this.arity];
/*      */     }
/*  807 */     assert (nameRefsAreLegal());
/*  808 */     int j = this.arity - 1;
/*  809 */     Name[] arrayOfName = (Name[])this.names.clone();
/*  810 */     arrayOfName[i] = paramName2;
/*      */     
/*      */ 
/*      */ 
/*  814 */     int k = -1;
/*  815 */     Name localName1; for (int m = 0; m < arrayOfName.length; m++) {
/*  816 */       localName1 = this.names[m];
/*  817 */       if ((localName1.function != null) && 
/*  818 */         (localName1.function.memberDeclaringClassOrNull() == paramSpeciesData1.clazz)) {
/*  819 */         MethodHandle localMethodHandle1 = localName1.function.resolvedHandle;
/*  820 */         MethodHandle localMethodHandle2 = null;
/*  821 */         for (int i1 = 0; i1 < paramSpeciesData1.getters.length; i1++) {
/*  822 */           if (localMethodHandle1 == paramSpeciesData1.getters[i1])
/*  823 */             localMethodHandle2 = paramSpeciesData2.getters[i1];
/*      */         }
/*  825 */         if (localMethodHandle2 != null) {
/*  826 */           if (k < 0) k = m;
/*  827 */           Name localName2 = new Name(localMethodHandle2, localName1.arguments);
/*  828 */           arrayOfName[m] = localName2;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  837 */     assert ((k < 0) || (k > i));
/*  838 */     for (m = i + 1; m < arrayOfName.length; m++) {
/*  839 */       if (m > j) {
/*  840 */         arrayOfName[m] = arrayOfName[m].replaceNames(this.names, arrayOfName, i, m);
/*      */       }
/*      */     }
/*      */     
/*  844 */     for (m = i; 
/*  845 */         m + 1 < arrayOfName.length; m++) {
/*  846 */       localName1 = arrayOfName[(m + 1)];
/*  847 */       if (!localName1.isSiblingBindingBefore(paramName2)) break;
/*  848 */       arrayOfName[m] = localName1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  853 */     arrayOfName[m] = paramName2;
/*      */     
/*      */ 
/*  856 */     int n = this.result;
/*  857 */     if (n == i) {
/*  858 */       n = m;
/*  859 */     } else if ((n > i) && (n <= m)) {
/*  860 */       n--;
/*      */     }
/*  862 */     return this.bindCache[i] = new LambdaForm(this.debugName, j, arrayOfName, n);
/*      */   }
/*      */   
/*      */   boolean contains(Name paramName) {
/*  866 */     int i = paramName.index();
/*  867 */     if (i >= 0) {
/*  868 */       return (i < this.names.length) && (paramName.equals(this.names[i]));
/*      */     }
/*  870 */     for (int j = this.arity; j < this.names.length; j++) {
/*  871 */       if (paramName.equals(this.names[j]))
/*  872 */         return true;
/*      */     }
/*  874 */     return false;
/*      */   }
/*      */   
/*      */   LambdaForm addArguments(int paramInt, char... paramVarArgs) {
/*  878 */     assert (paramInt <= this.arity);
/*  879 */     int i = this.names.length;
/*  880 */     int j = paramVarArgs.length;
/*  881 */     Name[] arrayOfName = (Name[])Arrays.copyOf(this.names, i + j);
/*  882 */     int k = this.arity + j;
/*  883 */     int m = this.result;
/*  884 */     if (m >= this.arity) {
/*  885 */       m += j;
/*      */     }
/*  887 */     int n = paramInt + 1;
/*      */     
/*      */ 
/*  890 */     System.arraycopy(this.names, n, arrayOfName, n + j, i - n);
/*  891 */     for (int i1 = 0; i1 < j; i1++) {
/*  892 */       arrayOfName[(n + i1)] = new Name(paramVarArgs[i1]);
/*      */     }
/*  894 */     return new LambdaForm(this.debugName, k, arrayOfName, m);
/*      */   }
/*      */   
/*      */   LambdaForm addArguments(int paramInt, List<Class<?>> paramList) {
/*  898 */     char[] arrayOfChar = new char[paramList.size()];
/*  899 */     for (int i = 0; i < arrayOfChar.length; i++)
/*  900 */       arrayOfChar[i] = basicType((Class)paramList.get(i));
/*  901 */     return addArguments(paramInt, arrayOfChar);
/*      */   }
/*      */   
/*      */ 
/*      */   LambdaForm permuteArguments(int paramInt, int[] paramArrayOfInt, char[] paramArrayOfChar)
/*      */   {
/*  907 */     int i = this.names.length;
/*  908 */     int j = paramArrayOfChar.length;
/*  909 */     int k = paramArrayOfInt.length;
/*  910 */     assert (paramInt + k == this.arity);
/*  911 */     assert (permutedTypesMatch(paramArrayOfInt, paramArrayOfChar, this.names, paramInt));
/*  912 */     int m = 0;
/*      */     
/*  914 */     while ((m < k) && (paramArrayOfInt[m] == m)) m++;
/*  915 */     Name[] arrayOfName = new Name[i - k + j];
/*  916 */     System.arraycopy(this.names, 0, arrayOfName, 0, paramInt + m);
/*      */     
/*  918 */     int n = i - this.arity;
/*  919 */     System.arraycopy(this.names, paramInt + k, arrayOfName, paramInt + j, n);
/*  920 */     int i1 = arrayOfName.length - n;
/*  921 */     int i2 = this.result;
/*  922 */     if (i2 >= 0) {
/*  923 */       if (i2 < paramInt + k)
/*      */       {
/*  925 */         i2 = paramArrayOfInt[(i2 - paramInt)];
/*      */       } else
/*  927 */         i2 = i2 - k + j;
/*      */     }
/*      */     Name localName3;
/*      */     int i6;
/*  931 */     for (int i3 = m; i3 < k; i3++) {
/*  932 */       Name localName1 = this.names[(paramInt + i3)];
/*  933 */       int i5 = paramArrayOfInt[i3];
/*      */       
/*  935 */       localName3 = arrayOfName[(paramInt + i5)];
/*  936 */       if (localName3 == null) {
/*  937 */         arrayOfName[(paramInt + i5)] = (localName3 = new Name(paramArrayOfChar[i5]));
/*      */       } else
/*  939 */         assert (localName3.type == paramArrayOfChar[i5]);
/*  940 */       for (i6 = i1; i6 < arrayOfName.length; i6++) {
/*  941 */         arrayOfName[i6] = arrayOfName[i6].replaceName(localName1, localName3);
/*      */       }
/*      */     }
/*      */     
/*  945 */     for (i3 = paramInt + m; i3 < i1; i3++) {
/*  946 */       if (arrayOfName[i3] == null)
/*  947 */         arrayOfName[i3] = argument(i3, paramArrayOfChar[(i3 - paramInt)]);
/*      */     }
/*  949 */     for (i3 = this.arity; i3 < this.names.length; i3++) {
/*  950 */       int i4 = i3 - this.arity + i1;
/*      */       
/*  952 */       Name localName2 = this.names[i3];
/*  953 */       localName3 = arrayOfName[i4];
/*  954 */       if (localName2 != localName3) {
/*  955 */         for (i6 = i4 + 1; i6 < arrayOfName.length; i6++) {
/*  956 */           arrayOfName[i6] = arrayOfName[i6].replaceName(localName2, localName3);
/*      */         }
/*      */       }
/*      */     }
/*  960 */     return new LambdaForm(this.debugName, i1, arrayOfName, i2);
/*      */   }
/*      */   
/*      */   static boolean permutedTypesMatch(int[] paramArrayOfInt, char[] paramArrayOfChar, Name[] paramArrayOfName, int paramInt) {
/*  964 */     int i = paramArrayOfChar.length;
/*  965 */     int j = paramArrayOfInt.length;
/*  966 */     for (int k = 0; k < j; k++) {
/*  967 */       assert (paramArrayOfName[(paramInt + k)].isParam());
/*  968 */       assert (paramArrayOfName[(paramInt + k)].type == paramArrayOfChar[paramArrayOfInt[k]]);
/*      */     }
/*  970 */     return true;
/*      */   }
/*      */   
/*      */   static class NamedFunction { final MemberName member;
/*      */     @Stable
/*      */     MethodHandle resolvedHandle;
/*      */     @Stable
/*      */     MethodHandle invoker;
/*      */     
/*  979 */     NamedFunction(MethodHandle paramMethodHandle) { this(paramMethodHandle.internalMemberName(), paramMethodHandle); }
/*      */     
/*      */     NamedFunction(MemberName paramMemberName, MethodHandle paramMethodHandle) {
/*  982 */       this.member = paramMemberName;
/*      */       
/*  984 */       this.resolvedHandle = paramMethodHandle;
/*      */     }
/*      */     
/*  987 */     NamedFunction(MethodType paramMethodType) { assert (paramMethodType == paramMethodType.basicType()) : paramMethodType;
/*  988 */       if (paramMethodType.parameterSlotCount() < 253) {
/*  989 */         this.resolvedHandle = paramMethodType.invokers().basicInvoker();
/*  990 */         this.member = this.resolvedHandle.internalMemberName();
/*      */       }
/*      */       else {
/*  993 */         this.member = Invokers.invokeBasicMethod(paramMethodType);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     NamedFunction(Method paramMethod)
/*      */     {
/* 1002 */       this(new MemberName(paramMethod));
/*      */     }
/*      */     
/* 1005 */     NamedFunction(Field paramField) { this(new MemberName(paramField)); }
/*      */     
/*      */     NamedFunction(MemberName paramMemberName) {
/* 1008 */       this.member = paramMemberName;
/* 1009 */       this.resolvedHandle = null;
/*      */     }
/*      */     
/*      */     MethodHandle resolvedHandle() {
/* 1013 */       if (this.resolvedHandle == null) resolve();
/* 1014 */       return this.resolvedHandle;
/*      */     }
/*      */     
/*      */     void resolve() {
/* 1018 */       this.resolvedHandle = DirectMethodHandle.make(this.member);
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject)
/*      */     {
/* 1023 */       if (this == paramObject) return true;
/* 1024 */       if (paramObject == null) return false;
/* 1025 */       if (!(paramObject instanceof NamedFunction)) return false;
/* 1026 */       NamedFunction localNamedFunction = (NamedFunction)paramObject;
/* 1027 */       return (this.member != null) && (this.member.equals(localNamedFunction.member));
/*      */     }
/*      */     
/*      */     public int hashCode()
/*      */     {
/* 1032 */       if (this.member != null)
/* 1033 */         return this.member.hashCode();
/* 1034 */       return super.hashCode();
/*      */     }
/*      */     
/*      */     static void initializeInvokers()
/*      */     {
/* 1039 */       for (MemberName localMemberName : MemberName.getFactory().getMethods(NamedFunction.class, false, null, null, null)) {
/* 1040 */         if ((localMemberName.isStatic()) && (localMemberName.isPackage())) {
/* 1041 */           MethodType localMethodType1 = localMemberName.getMethodType();
/* 1042 */           if ((localMethodType1.equals(INVOKER_METHOD_TYPE)) && 
/* 1043 */             (localMemberName.getName().startsWith("invoke_"))) {
/* 1044 */             String str = localMemberName.getName().substring("invoke_".length());
/* 1045 */             int i = LambdaForm.signatureArity(str);
/* 1046 */             MethodType localMethodType2 = MethodType.genericMethodType(i);
/* 1047 */             if (LambdaForm.signatureReturn(str) == 'V')
/* 1048 */               localMethodType2 = localMethodType2.changeReturnType(Void.TYPE);
/* 1049 */             MethodTypeForm localMethodTypeForm = localMethodType2.form();
/* 1050 */             localMethodTypeForm.namedFunctionInvoker = DirectMethodHandle.make(localMemberName);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/*      */     static Object invoke__V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject)
/*      */       throws Throwable
/*      */     {
/* 1060 */       assert (paramArrayOfObject.length == 0);
/* 1061 */       paramMethodHandle.invokeBasic();
/* 1062 */       return null;
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1066 */     static Object invoke_L_V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 1);
/* 1067 */       paramMethodHandle.invokeBasic(paramArrayOfObject[0]);
/* 1068 */       return null;
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1072 */     static Object invoke_LL_V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 2);
/* 1073 */       paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1]);
/* 1074 */       return null;
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1078 */     static Object invoke_LLL_V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 3);
/* 1079 */       paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2]);
/* 1080 */       return null;
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1084 */     static Object invoke_LLLL_V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 4);
/* 1085 */       paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramArrayOfObject[3]);
/* 1086 */       return null;
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1090 */     static Object invoke_LLLLL_V(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 5);
/* 1091 */       paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramArrayOfObject[3], paramArrayOfObject[4]);
/* 1092 */       return null;
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/*      */     static Object invoke__L(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable {
/* 1097 */       assert (paramArrayOfObject.length == 0);
/* 1098 */       return paramMethodHandle.invokeBasic();
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1102 */     static Object invoke_L_L(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 1);
/* 1103 */       return paramMethodHandle.invokeBasic(paramArrayOfObject[0]);
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1107 */     static Object invoke_LL_L(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 2);
/* 1108 */       return paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1]);
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1112 */     static Object invoke_LLL_L(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 3);
/* 1113 */       return paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2]);
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1117 */     static Object invoke_LLLL_L(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 4);
/* 1118 */       return paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramArrayOfObject[3]);
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/* 1122 */     static Object invoke_LLLLL_L(MethodHandle paramMethodHandle, Object[] paramArrayOfObject) throws Throwable { assert (paramArrayOfObject.length == 5);
/* 1123 */       return paramMethodHandle.invokeBasic(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramArrayOfObject[3], paramArrayOfObject[4]);
/*      */     }
/*      */     
/*      */ 
/* 1127 */     static final MethodType INVOKER_METHOD_TYPE = MethodType.methodType(Object.class, MethodHandle.class, new Class[] { Object[].class });
/*      */     
/*      */     private static MethodHandle computeInvoker(MethodTypeForm paramMethodTypeForm) {
/* 1130 */       Object localObject = paramMethodTypeForm.namedFunctionInvoker;
/* 1131 */       if (localObject != null) return (MethodHandle)localObject;
/* 1132 */       MemberName localMemberName = InvokerBytecodeGenerator.generateNamedFunctionInvoker(paramMethodTypeForm);
/* 1133 */       localObject = DirectMethodHandle.make(localMemberName);
/* 1134 */       MethodHandle localMethodHandle = paramMethodTypeForm.namedFunctionInvoker;
/* 1135 */       if (localMethodHandle != null) return localMethodHandle;
/* 1136 */       if (!((MethodHandle)localObject).type().equals(INVOKER_METHOD_TYPE))
/* 1137 */         throw new InternalError(((MethodHandle)localObject).debugString());
/* 1138 */       return (MethodHandle)(paramMethodTypeForm.namedFunctionInvoker = localObject);
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/*      */     Object invokeWithArguments(Object... paramVarArgs)
/*      */       throws Throwable
/*      */     {
/* 1145 */       if (LambdaForm.TRACE_INTERPRETER) return invokeWithArgumentsTracing(paramVarArgs);
/* 1146 */       assert (checkArgumentTypes(paramVarArgs, methodType()));
/* 1147 */       return invoker().invokeBasic(resolvedHandle(), paramVarArgs);
/*      */     }
/*      */     
/*      */     @LambdaForm.Hidden
/*      */     Object invokeWithArgumentsTracing(Object[] paramArrayOfObject) throws Throwable {
/*      */       Object localObject;
/*      */       try {
/* 1154 */         LambdaForm.traceInterpreter("[ call", this, paramArrayOfObject);
/* 1155 */         if (this.invoker == null) {
/* 1156 */           LambdaForm.traceInterpreter("| getInvoker", this);
/* 1157 */           invoker();
/*      */         }
/* 1159 */         if (this.resolvedHandle == null) {
/* 1160 */           LambdaForm.traceInterpreter("| resolve", this);
/* 1161 */           resolvedHandle();
/*      */         }
/* 1163 */         assert (checkArgumentTypes(paramArrayOfObject, methodType()));
/* 1164 */         localObject = invoker().invokeBasic(resolvedHandle(), paramArrayOfObject);
/*      */       } catch (Throwable localThrowable) {
/* 1166 */         LambdaForm.traceInterpreter("] throw =>", localThrowable);
/* 1167 */         throw localThrowable;
/*      */       }
/* 1169 */       LambdaForm.traceInterpreter("] return =>", localObject);
/* 1170 */       return localObject;
/*      */     }
/*      */     
/*      */     private MethodHandle invoker() {
/* 1174 */       if (this.invoker != null) { return this.invoker;
/*      */       }
/* 1176 */       return this.invoker = computeInvoker(methodType().form());
/*      */     }
/*      */     
/*      */     private static boolean checkArgumentTypes(Object[] paramArrayOfObject, MethodType paramMethodType) {
/* 1180 */       return true;
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
/*      */     String basicTypeSignature()
/*      */     {
/* 1198 */       return LambdaForm.basicTypeSignature(methodType());
/*      */     }
/*      */     
/*      */     MethodType methodType() {
/* 1202 */       if (this.resolvedHandle != null) {
/* 1203 */         return this.resolvedHandle.type();
/*      */       }
/*      */       
/* 1206 */       return this.member.getInvocationType();
/*      */     }
/*      */     
/*      */     MemberName member() {
/* 1210 */       assert (assertMemberIsConsistent());
/* 1211 */       return this.member;
/*      */     }
/*      */     
/*      */     private boolean assertMemberIsConsistent()
/*      */     {
/* 1216 */       if ((this.resolvedHandle instanceof DirectMethodHandle)) {
/* 1217 */         MemberName localMemberName = this.resolvedHandle.internalMemberName();
/* 1218 */         assert (localMemberName.equals(this.member));
/*      */       }
/* 1220 */       return true;
/*      */     }
/*      */     
/*      */     Class<?> memberDeclaringClassOrNull() {
/* 1224 */       return this.member == null ? null : this.member.getDeclaringClass();
/*      */     }
/*      */     
/*      */     char returnType() {
/* 1228 */       return LambdaForm.basicType(methodType().returnType());
/*      */     }
/*      */     
/*      */     char parameterType(int paramInt) {
/* 1232 */       return LambdaForm.basicType(methodType().parameterType(paramInt));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int arity()
/*      */     {
/* 1239 */       return methodType().parameterCount();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1243 */       if (this.member == null) return String.valueOf(this.resolvedHandle);
/* 1244 */       return this.member.getDeclaringClass().getSimpleName() + "." + this.member.getName();
/*      */     }
/*      */   }
/*      */   
/*      */   void resolve() {
/* 1249 */     for (Name localName : this.names) localName.resolve();
/*      */   }
/*      */   
/*      */   public static char basicType(Class<?> paramClass) {
/* 1253 */     char c = Wrapper.basicTypeChar(paramClass);
/* 1254 */     if ("ZBSC".indexOf(c) >= 0) c = 'I';
/* 1255 */     assert ("LIJFDV".indexOf(c) >= 0);
/* 1256 */     return c;
/*      */   }
/*      */   
/* 1259 */   public static char[] basicTypes(List<Class<?>> paramList) { char[] arrayOfChar = new char[paramList.size()];
/* 1260 */     for (int i = 0; i < arrayOfChar.length; i++) {
/* 1261 */       arrayOfChar[i] = basicType((Class)paramList.get(i));
/*      */     }
/* 1263 */     return arrayOfChar;
/*      */   }
/*      */   
/* 1266 */   public static String basicTypeSignature(MethodType paramMethodType) { char[] arrayOfChar = new char[paramMethodType.parameterCount() + 2];
/* 1267 */     int i = 0;
/* 1268 */     for (Class localClass : paramMethodType.parameterList()) {
/* 1269 */       arrayOfChar[(i++)] = basicType(localClass);
/*      */     }
/* 1271 */     arrayOfChar[(i++)] = '_';
/* 1272 */     arrayOfChar[(i++)] = basicType(paramMethodType.returnType());
/* 1273 */     assert (i == arrayOfChar.length);
/* 1274 */     return String.valueOf(arrayOfChar);
/*      */   }
/*      */   
/*      */   static final class Name {
/*      */     final char type;
/*      */     private short index;
/*      */     final LambdaForm.NamedFunction function;
/*      */     @Stable
/*      */     final Object[] arguments;
/*      */     
/* 1284 */     private Name(int paramInt, char paramChar, LambdaForm.NamedFunction paramNamedFunction, Object[] paramArrayOfObject) { this.index = ((short)paramInt);
/* 1285 */       this.type = paramChar;
/* 1286 */       this.function = paramNamedFunction;
/* 1287 */       this.arguments = paramArrayOfObject;
/* 1288 */       assert (this.index == paramInt);
/*      */     }
/*      */     
/* 1291 */     Name(MethodHandle paramMethodHandle, Object... paramVarArgs) { this(new LambdaForm.NamedFunction(paramMethodHandle), paramVarArgs); }
/*      */     
/*      */     Name(MethodType paramMethodType, Object... paramVarArgs) {
/* 1294 */       this(new LambdaForm.NamedFunction(paramMethodType), paramVarArgs);
/* 1295 */       assert (((paramVarArgs[0] instanceof Name)) && (((Name)paramVarArgs[0]).type == 'L'));
/*      */     }
/*      */     
/* 1298 */     Name(MemberName paramMemberName, Object... paramVarArgs) { this(new LambdaForm.NamedFunction(paramMemberName), paramVarArgs); }
/*      */     
/*      */     Name(LambdaForm.NamedFunction paramNamedFunction, Object... paramVarArgs) {
/* 1301 */       this(-1, paramNamedFunction.returnType(), paramNamedFunction, paramVarArgs = (Object[])paramVarArgs.clone());
/* 1302 */       assert (paramVarArgs.length == paramNamedFunction.arity()) : ("arity mismatch: arguments.length=" + paramVarArgs.length + " == function.arity()=" + paramNamedFunction.arity() + " in " + debugString());
/* 1303 */       for (int i = 0; i < paramVarArgs.length; i++)
/* 1304 */         assert (typesMatch(paramNamedFunction.parameterType(i), paramVarArgs[i])) : ("types don't match: function.parameterType(" + i + ")=" + paramNamedFunction.parameterType(i) + ", arguments[" + i + "]=" + paramVarArgs[i] + " in " + debugString());
/*      */     }
/*      */     
/* 1307 */     Name(int paramInt, char paramChar) { this(paramInt, paramChar, null, null); }
/*      */     
/*      */     Name(char paramChar) {
/* 1310 */       this(-1, paramChar);
/*      */     }
/*      */     
/* 1313 */     char type() { return this.type; }
/* 1314 */     int index() { return this.index; }
/*      */     
/* 1316 */     boolean initIndex(int paramInt) { if (this.index != paramInt) {
/* 1317 */         if (this.index != -1) return false;
/* 1318 */         this.index = ((short)paramInt);
/*      */       }
/* 1320 */       return true;
/*      */     }
/*      */     
/*      */     void resolve()
/*      */     {
/* 1325 */       if (this.function != null)
/* 1326 */         this.function.resolve();
/*      */     }
/*      */     
/*      */     Name newIndex(int paramInt) {
/* 1330 */       if (initIndex(paramInt)) return this;
/* 1331 */       return cloneWithIndex(paramInt);
/*      */     }
/*      */     
/* 1334 */     Name cloneWithIndex(int paramInt) { Object[] arrayOfObject = this.arguments == null ? null : (Object[])this.arguments.clone();
/* 1335 */       return new Name(paramInt, this.type, this.function, arrayOfObject);
/*      */     }
/*      */     
/* 1338 */     Name replaceName(Name paramName1, Name paramName2) { if (paramName1 == paramName2) { return this;
/*      */       }
/* 1340 */       Object[] arrayOfObject = this.arguments;
/* 1341 */       if (arrayOfObject == null) return this;
/* 1342 */       int i = 0;
/* 1343 */       for (int j = 0; j < arrayOfObject.length; j++) {
/* 1344 */         if (arrayOfObject[j] == paramName1) {
/* 1345 */           if (i == 0) {
/* 1346 */             i = 1;
/* 1347 */             arrayOfObject = (Object[])arrayOfObject.clone();
/*      */           }
/* 1349 */           arrayOfObject[j] = paramName2;
/*      */         }
/*      */       }
/* 1352 */       if (i == 0) return this;
/* 1353 */       return new Name(this.function, arrayOfObject);
/*      */     }
/*      */     
/*      */     Name replaceNames(Name[] paramArrayOfName1, Name[] paramArrayOfName2, int paramInt1, int paramInt2) {
/* 1357 */       Object[] arrayOfObject = this.arguments;
/* 1358 */       int i = 0;
/*      */       
/* 1360 */       for (int j = 0; j < arrayOfObject.length; j++) {
/* 1361 */         if ((arrayOfObject[j] instanceof Name)) {
/* 1362 */           Name localName = (Name)arrayOfObject[j];
/* 1363 */           int k = localName.index;
/*      */           
/* 1365 */           if ((k < 0) || (k >= paramArrayOfName2.length) || (localName != paramArrayOfName2[k]))
/*      */           {
/*      */ 
/* 1368 */             for (int m = paramInt1; m < paramInt2; m++)
/* 1369 */               if (localName == paramArrayOfName1[m]) {
/* 1370 */                 if (localName == paramArrayOfName2[m])
/*      */                   break;
/* 1372 */                 if (i == 0) {
/* 1373 */                   i = 1;
/* 1374 */                   arrayOfObject = (Object[])arrayOfObject.clone();
/*      */                 }
/* 1376 */                 arrayOfObject[j] = paramArrayOfName2[m];
/* 1377 */                 break;
/*      */               }
/*      */           }
/*      */         }
/*      */       }
/* 1382 */       if (i == 0) return this;
/* 1383 */       return new Name(this.function, arrayOfObject);
/*      */     }
/*      */     
/*      */     void internArguments() {
/* 1387 */       Object[] arrayOfObject = this.arguments;
/* 1388 */       for (int i = 0; i < arrayOfObject.length; i++)
/* 1389 */         if ((arrayOfObject[i] instanceof Name)) {
/* 1390 */           Name localName = (Name)arrayOfObject[i];
/* 1391 */           if ((localName.isParam()) && (localName.index < 10))
/* 1392 */             arrayOfObject[i] = LambdaForm.internArgument(localName);
/*      */         }
/*      */     }
/*      */     
/*      */     boolean isParam() {
/* 1397 */       return this.function == null;
/*      */     }
/*      */     
/* 1400 */     boolean isConstantZero() { return (!isParam()) && (this.arguments.length == 0) && (this.function.equals(LambdaForm.constantZero(0, this.type).function)); }
/*      */     
/*      */ 
/*      */ 
/* 1404 */     public String toString() { return (isParam() ? "a" : "t") + (this.index >= 0 ? this.index : System.identityHashCode(this)) + ":" + this.type; }
/*      */     
/*      */     public String debugString() {
/* 1407 */       String str = toString();
/* 1408 */       return str + "=" + exprString();
/*      */     }
/*      */     
/* 1411 */     public String exprString() { if (this.function == null) return "null";
/* 1412 */       StringBuilder localStringBuilder = new StringBuilder(this.function.toString());
/* 1413 */       localStringBuilder.append("(");
/* 1414 */       String str = "";
/* 1415 */       for (Object localObject : this.arguments) {
/* 1416 */         localStringBuilder.append(str);str = ",";
/* 1417 */         if (((localObject instanceof Name)) || ((localObject instanceof Integer))) {
/* 1418 */           localStringBuilder.append(localObject);
/*      */         } else
/* 1420 */           localStringBuilder.append("(").append(localObject).append(")");
/*      */       }
/* 1422 */       localStringBuilder.append(")");
/* 1423 */       return localStringBuilder.toString();
/*      */     }
/*      */     
/*      */     private static boolean typesMatch(char paramChar, Object paramObject) {
/* 1427 */       if ((paramObject instanceof Name)) {
/* 1428 */         return ((Name)paramObject).type == paramChar;
/*      */       }
/* 1430 */       switch (paramChar) {
/* 1431 */       case 'I':  return paramObject instanceof Integer;
/* 1432 */       case 'J':  return paramObject instanceof Long;
/* 1433 */       case 'F':  return paramObject instanceof Float;
/* 1434 */       case 'D':  return paramObject instanceof Double;
/*      */       }
/* 1436 */       assert (paramChar == 'L');
/* 1437 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean isSiblingBindingBefore(Name paramName)
/*      */     {
/* 1446 */       assert (!paramName.isParam());
/* 1447 */       if (isParam()) return true;
/* 1448 */       if ((this.function.equals(paramName.function)) && (this.arguments.length == paramName.arguments.length))
/*      */       {
/* 1450 */         boolean bool = false;
/* 1451 */         for (int i = 0; i < this.arguments.length; i++) {
/* 1452 */           Object localObject1 = this.arguments[i];
/* 1453 */           Object localObject2 = paramName.arguments[i];
/* 1454 */           if (!localObject1.equals(localObject2)) {
/* 1455 */             if (((localObject1 instanceof Integer)) && ((localObject2 instanceof Integer))) {
/* 1456 */               if (!bool) {
/* 1457 */                 bool = true;
/* 1458 */                 if (((Integer)localObject1).intValue() < ((Integer)localObject2).intValue()) {}
/*      */               }
/* 1460 */             } else return false;
/*      */           }
/*      */         }
/* 1463 */         return bool;
/*      */       }
/* 1465 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int lastUseIndex(Name paramName)
/*      */     {
/* 1472 */       if (this.arguments == null) return -1;
/* 1473 */       int i = this.arguments.length; do { i--; if (i < 0) break;
/* 1474 */       } while (this.arguments[i] != paramName); return i;
/*      */       
/* 1476 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int useCount(Name paramName)
/*      */     {
/* 1483 */       if (this.arguments == null) return 0;
/* 1484 */       int i = 0;
/* 1485 */       int j = this.arguments.length; for (;;) { j--; if (j < 0) break;
/* 1486 */         if (this.arguments[j] == paramName) i++;
/*      */       }
/* 1488 */       return i;
/*      */     }
/*      */     
/*      */     boolean contains(Name paramName) {
/* 1492 */       return (this == paramName) || (lastUseIndex(paramName) >= 0);
/*      */     }
/*      */     
/*      */     public boolean equals(Name paramName) {
/* 1496 */       if (this == paramName) return true;
/* 1497 */       if (isParam())
/*      */       {
/* 1499 */         return false; }
/* 1500 */       if (this.type == paramName.type) {}
/*      */       
/*      */ 
/*      */ 
/* 1504 */       return (this.function.equals(paramName.function)) && (Arrays.equals(this.arguments, paramName.arguments));
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/* 1508 */       return ((paramObject instanceof Name)) && (equals((Name)paramObject));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1512 */       if (isParam())
/* 1513 */         return this.index | this.type << '\b';
/* 1514 */       return this.function.hashCode() ^ Arrays.hashCode(this.arguments);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   int lastUseIndex(Name paramName)
/*      */   {
/* 1522 */     int i = paramName.index;int j = this.names.length;
/* 1523 */     assert (this.names[i] == paramName);
/* 1524 */     if (this.result == i) return j;
/* 1525 */     int k = j; do { k--; if (k <= i) break;
/* 1526 */     } while (this.names[k].lastUseIndex(paramName) < 0);
/* 1527 */     return k;
/*      */     
/* 1529 */     return -1;
/*      */   }
/*      */   
/*      */   int useCount(Name paramName)
/*      */   {
/* 1534 */     int i = paramName.index;int j = this.names.length;
/* 1535 */     int k = lastUseIndex(paramName);
/* 1536 */     if (k < 0) return 0;
/* 1537 */     int m = 0;
/* 1538 */     if (k == j) { m++;k--; }
/* 1539 */     int n = paramName.index() + 1;
/* 1540 */     if (n < this.arity) n = this.arity;
/* 1541 */     for (int i1 = n; i1 <= k; i1++) {
/* 1542 */       m += this.names[i1].useCount(paramName);
/*      */     }
/* 1544 */     return m;
/*      */   }
/*      */   
/*      */   static Name argument(int paramInt, char paramChar) {
/* 1548 */     int i = "LIJFD".indexOf(paramChar);
/* 1549 */     if ((i < 0) || (paramInt >= 10))
/* 1550 */       return new Name(paramInt, paramChar);
/* 1551 */     return INTERNED_ARGUMENTS[i][paramInt];
/*      */   }
/*      */   
/* 1554 */   static Name internArgument(Name paramName) { assert (paramName.isParam()) : ("not param: " + paramName);
/* 1555 */     assert (paramName.index < 10);
/* 1556 */     return argument(paramName.index, paramName.type);
/*      */   }
/*      */   
/* 1559 */   static Name[] arguments(int paramInt, String paramString) { int i = paramString.length();
/* 1560 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1561 */     for (int j = 0; j < i; j++)
/* 1562 */       arrayOfName[j] = argument(j, paramString.charAt(j));
/* 1563 */     return arrayOfName;
/*      */   }
/*      */   
/* 1566 */   static Name[] arguments(int paramInt, char... paramVarArgs) { int i = paramVarArgs.length;
/* 1567 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1568 */     for (int j = 0; j < i; j++)
/* 1569 */       arrayOfName[j] = argument(j, paramVarArgs[j]);
/* 1570 */     return arrayOfName;
/*      */   }
/*      */   
/* 1573 */   static Name[] arguments(int paramInt, List<Class<?>> paramList) { int i = paramList.size();
/* 1574 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1575 */     for (int j = 0; j < i; j++)
/* 1576 */       arrayOfName[j] = argument(j, basicType((Class)paramList.get(j)));
/* 1577 */     return arrayOfName;
/*      */   }
/*      */   
/* 1580 */   static Name[] arguments(int paramInt, Class<?>... paramVarArgs) { int i = paramVarArgs.length;
/* 1581 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1582 */     for (int j = 0; j < i; j++)
/* 1583 */       arrayOfName[j] = argument(j, basicType(paramVarArgs[j]));
/* 1584 */     return arrayOfName;
/*      */   }
/*      */   
/* 1587 */   static Name[] arguments(int paramInt, MethodType paramMethodType) { int i = paramMethodType.parameterCount();
/* 1588 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1589 */     for (int j = 0; j < i; j++)
/* 1590 */       arrayOfName[j] = argument(j, basicType(paramMethodType.parameterType(j)));
/* 1591 */     return arrayOfName;
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
/*      */   static Name constantZero(int paramInt, char paramChar)
/*      */   {
/* 1609 */     return CONSTANT_ZERO["LIJFD".indexOf(paramChar)].newIndex(paramInt);
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
/* 1632 */   private static int zeroI() { return 0; }
/* 1633 */   private static long zeroJ() { return 0L; }
/* 1634 */   private static float zeroF() { return 0.0F; }
/* 1635 */   private static double zeroD() { return 0.0D; }
/* 1636 */   private static Object zeroL() { return null;
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  466 */     int i = 512;
/*  467 */     float f = 0.75F;
/*  468 */     char c = '\001';
/*  469 */     PREPARED_FORMS = new ConcurrentHashMap(i, f, c);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  587 */     if (MethodHandleStatics.COMPILE_THRESHOLD != null) {
/*  588 */       COMPILE_THRESHOLD = MethodHandleStatics.COMPILE_THRESHOLD.intValue();
/*      */     } else {
/*  590 */       COMPILE_THRESHOLD = 30;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1596 */     INTERNED_ARGUMENTS = new Name["LIJFD".length()][10];
/*      */     int j;
/* 1598 */     for (i = 0; i < "LIJFD".length(); i++) {
/* 1599 */       for (j = 0; j < INTERNED_ARGUMENTS[i].length; j++) {
/* 1600 */         c = "LIJFD".charAt(i);
/* 1601 */         INTERNED_ARGUMENTS[i][j] = new Name(j, c);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1606 */     IMPL_NAMES = MemberName.getFactory();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1612 */     CONSTANT_ZERO = new Name["LIJFD".length()];
/*      */     
/* 1614 */     for (i = 0; i < "LIJFD".length(); i++) {
/* 1615 */       j = "LIJFD".charAt(i);
/* 1616 */       Wrapper localWrapper = Wrapper.forBasicType(j);
/* 1617 */       MemberName localMemberName = new MemberName(LambdaForm.class, "zero" + j, MethodType.methodType(localWrapper.primitiveType()), (byte)6);
/*      */       try {
/* 1619 */         localMemberName = IMPL_NAMES.resolveOrFail((byte)6, localMemberName, null, NoSuchMethodException.class);
/*      */       } catch (IllegalAccessException|NoSuchMethodException localIllegalAccessException) {
/* 1621 */         throw MethodHandleStatics.newInternalError(localIllegalAccessException);
/*      */       }
/* 1623 */       NamedFunction localNamedFunction = new NamedFunction(localMemberName);
/* 1624 */       Name localName = new Name(localNamedFunction, new Object[0]).newIndex(0);
/* 1625 */       assert (localName.type == "LIJFD".charAt(i));
/* 1626 */       CONSTANT_ZERO[i] = localName;
/* 1627 */       assert (localName.isConstantZero());
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
/* 1641 */     PREPARED_FORMS.putAll(computeInitialPreparedForms());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1693 */     NamedFunction.initializeInvokers();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1701 */   private static final boolean TRACE_INTERPRETER = MethodHandleStatics.TRACE_INTERPRETER;
/*      */   
/*      */   @Target({java.lang.annotation.ElementType.METHOD})
/*      */   @Retention(RetentionPolicy.RUNTIME)
/*      */   static @interface Compiled {}
/*      */   
/*      */   @Target({java.lang.annotation.ElementType.METHOD})
/*      */   @Retention(RetentionPolicy.RUNTIME)
/*      */   static @interface Hidden {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/LambdaForm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */