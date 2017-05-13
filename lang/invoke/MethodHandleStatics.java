/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ class MethodHandleStatics
/*     */ {
/*  42 */   static final Unsafe UNSAFE = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  50 */     Object[] arrayOfObject = { Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), null };
/*  51 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/*  53 */         this.val$values[0] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.DEBUG_NAMES"));
/*  54 */         this.val$values[1] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.DUMP_CLASS_FILES"));
/*  55 */         this.val$values[2] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.TRACE_INTERPRETER"));
/*  56 */         this.val$values[3] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.TRACE_METHOD_LINKAGE"));
/*  57 */         this.val$values[4] = Integer.getInteger("java.lang.invoke.MethodHandle.COMPILE_THRESHOLD");
/*  58 */         return null;
/*     */       } }); }
/*     */   
/*  61 */   static final boolean DEBUG_METHOD_HANDLE_NAMES = ((Boolean)arrayOfObject[0]).booleanValue();
/*  62 */   static final boolean DUMP_CLASS_FILES = ((Boolean)arrayOfObject[1]).booleanValue();
/*  63 */   static final boolean TRACE_INTERPRETER = ((Boolean)arrayOfObject[2]).booleanValue();
/*  64 */   static final boolean TRACE_METHOD_LINKAGE = ((Boolean)arrayOfObject[3]).booleanValue();
/*  65 */   static final Integer COMPILE_THRESHOLD = (Integer)arrayOfObject[4];
/*     */   
/*     */   static String getNameString(MethodHandle paramMethodHandle, MethodType paramMethodType)
/*     */   {
/*  69 */     if (paramMethodType == null)
/*  70 */       paramMethodType = paramMethodHandle.type();
/*  71 */     MemberName localMemberName = null;
/*  72 */     if (paramMethodHandle != null)
/*  73 */       localMemberName = paramMethodHandle.internalMemberName();
/*  74 */     if (localMemberName == null)
/*  75 */       return "invoke" + paramMethodType;
/*  76 */     return localMemberName.getName() + paramMethodType;
/*     */   }
/*     */   
/*     */   static String getNameString(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) {
/*  80 */     return getNameString(paramMethodHandle1, paramMethodHandle2 == null ? (MethodType)null : paramMethodHandle2.type());
/*     */   }
/*     */   
/*     */   static String getNameString(MethodHandle paramMethodHandle) {
/*  84 */     return getNameString(paramMethodHandle, (MethodType)null);
/*     */   }
/*     */   
/*     */   static String addTypeString(Object paramObject, MethodHandle paramMethodHandle) {
/*  88 */     String str = String.valueOf(paramObject);
/*  89 */     if (paramMethodHandle == null) return str;
/*  90 */     int i = str.indexOf('(');
/*  91 */     if (i >= 0) str = str.substring(0, i);
/*  92 */     return str + paramMethodHandle.type();
/*     */   }
/*     */   
/*     */   static InternalError newInternalError(String paramString, Throwable paramThrowable)
/*     */   {
/*  97 */     return new InternalError(paramString, paramThrowable);
/*     */   }
/*     */   
/* 100 */   static InternalError newInternalError(Throwable paramThrowable) { return new InternalError(paramThrowable); }
/*     */   
/*     */   static RuntimeException newIllegalStateException(String paramString) {
/* 103 */     return new IllegalStateException(paramString);
/*     */   }
/*     */   
/* 106 */   static RuntimeException newIllegalStateException(String paramString, Object paramObject) { return new IllegalStateException(message(paramString, paramObject)); }
/*     */   
/*     */   static RuntimeException newIllegalArgumentException(String paramString) {
/* 109 */     return new IllegalArgumentException(paramString);
/*     */   }
/*     */   
/* 112 */   static RuntimeException newIllegalArgumentException(String paramString, Object paramObject) { return new IllegalArgumentException(message(paramString, paramObject)); }
/*     */   
/*     */   static RuntimeException newIllegalArgumentException(String paramString, Object paramObject1, Object paramObject2) {
/* 115 */     return new IllegalArgumentException(message(paramString, paramObject1, paramObject2));
/*     */   }
/*     */   
/* 118 */   static Error uncaughtException(Throwable paramThrowable) { throw newInternalError("uncaught exception", paramThrowable); }
/*     */   
/*     */ 
/* 121 */   static Error NYI() { throw new AssertionError("NYI"); }
/*     */   
/*     */   private static String message(String paramString, Object paramObject) {
/* 124 */     if (paramObject != null) paramString = paramString + ": " + paramObject;
/* 125 */     return paramString;
/*     */   }
/*     */   
/* 128 */   private static String message(String paramString, Object paramObject1, Object paramObject2) { if ((paramObject1 != null) || (paramObject2 != null)) paramString = paramString + ": " + paramObject1 + ", " + paramObject2;
/* 129 */     return paramString;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandleStatics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */