/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.ReflectPermission;
/*      */ import java.security.Permission;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.invoke.util.ValueConversions;
/*      */ import sun.invoke.util.VerifyAccess;
/*      */ import sun.invoke.util.Wrapper;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MethodHandles
/*      */ {
/*      */   private static final MemberName.Factory IMPL_NAMES;
/*      */   
/*      */   static
/*      */   {
/*   63 */     IMPL_NAMES = MemberName.getFactory();
/*   64 */     MethodHandleImpl.initStatics();
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
/*      */   @CallerSensitive
/*      */   public static Lookup lookup()
/*      */   {
/*   92 */     return new Lookup(Reflection.getCallerClass());
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
/*      */   public static Lookup publicLookup()
/*      */   {
/*  116 */     return Lookup.PUBLIC_LOOKUP;
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
/*      */   public static <T extends Member> T reflectAs(Class<T> paramClass, MethodHandle paramMethodHandle)
/*      */   {
/*  143 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  144 */     if (localSecurityManager != null) localSecurityManager.checkPermission(ACCESS_PERMISSION);
/*  145 */     Lookup localLookup = Lookup.IMPL_LOOKUP;
/*  146 */     return localLookup.revealDirect(paramMethodHandle).reflectAs(paramClass, localLookup);
/*      */   }
/*      */   
/*  149 */   private static final Permission ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final class Lookup
/*      */   {
/*      */     private final Class<?> lookupClass;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int allowedModes;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final int PUBLIC = 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final int PRIVATE = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final int PROTECTED = 4;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final int PACKAGE = 8;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final int ALL_MODES = 15;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final int TRUSTED = -1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private static int fixmods(int paramInt)
/*      */     {
/*  550 */       paramInt &= 0x7;
/*  551 */       return paramInt != 0 ? paramInt : 8;
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
/*      */     public Class<?> lookupClass()
/*      */     {
/*  564 */       return this.lookupClass;
/*      */     }
/*      */     
/*      */     private Class<?> lookupClassOrNull()
/*      */     {
/*  569 */       return this.allowedModes == -1 ? null : this.lookupClass;
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
/*      */     public int lookupModes()
/*      */     {
/*  591 */       return this.allowedModes & 0xF;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Lookup(Class<?> paramClass)
/*      */     {
/*  600 */       this(paramClass, 15);
/*      */       
/*  602 */       checkUnprivilegedlookupClass(paramClass, 15);
/*      */     }
/*      */     
/*      */     private Lookup(Class<?> paramClass, int paramInt) {
/*  606 */       this.lookupClass = paramClass;
/*  607 */       this.allowedModes = paramInt;
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
/*      */     public Lookup in(Class<?> paramClass)
/*      */     {
/*  635 */       paramClass.getClass();
/*  636 */       if (this.allowedModes == -1)
/*  637 */         return new Lookup(paramClass, 15);
/*  638 */       if (paramClass == this.lookupClass)
/*  639 */         return this;
/*  640 */       int i = this.allowedModes & 0xB;
/*  641 */       if (((i & 0x8) != 0) && 
/*  642 */         (!VerifyAccess.isSamePackage(this.lookupClass, paramClass))) {
/*  643 */         i &= 0xFFFFFFF5;
/*      */       }
/*      */       
/*  646 */       if (((i & 0x2) != 0) && 
/*  647 */         (!VerifyAccess.isSamePackageMember(this.lookupClass, paramClass))) {
/*  648 */         i &= 0xFFFFFFFD;
/*      */       }
/*  650 */       if (((i & 0x1) != 0) && 
/*  651 */         (!VerifyAccess.isClassAccessible(paramClass, this.lookupClass, this.allowedModes)))
/*      */       {
/*      */ 
/*  654 */         i = 0;
/*      */       }
/*  656 */       checkUnprivilegedlookupClass(paramClass, i);
/*  657 */       return new Lookup(paramClass, i);
/*      */     }
/*      */     
/*      */     static {
/*  661 */       MethodHandles.IMPL_NAMES.getClass();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  667 */     static final Lookup PUBLIC_LOOKUP = new Lookup(Object.class, 1);
/*      */     
/*      */ 
/*  670 */     static final Lookup IMPL_LOOKUP = new Lookup(Object.class, -1);
/*      */     private static final boolean ALLOW_NESTMATE_ACCESS = false;
/*      */     
/*  673 */     private static void checkUnprivilegedlookupClass(Class<?> paramClass, int paramInt) { String str = paramClass.getName();
/*  674 */       if (str.startsWith("java.lang.invoke.")) {
/*  675 */         throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + paramClass);
/*      */       }
/*      */       
/*      */ 
/*  679 */       if ((paramInt == 15) && (paramClass.getClassLoader() == null) && (
/*  680 */         (str.startsWith("java.")) || (
/*  681 */         (str.startsWith("sun.")) && (!str.startsWith("sun.invoke."))))) {
/*  682 */         throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + paramClass);
/*      */       }
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
/*      */     public String toString()
/*      */     {
/*  717 */       String str = this.lookupClass.getName();
/*  718 */       switch (this.allowedModes) {
/*      */       case 0: 
/*  720 */         return str + "/noaccess";
/*      */       case 1: 
/*  722 */         return str + "/public";
/*      */       case 9: 
/*  724 */         return str + "/package";
/*      */       case 11: 
/*  726 */         return str + "/private";
/*      */       case 15: 
/*  728 */         return str;
/*      */       case -1: 
/*  730 */         return "/trusted";
/*      */       }
/*  732 */       str = str + "/" + Integer.toHexString(this.allowedModes);
/*  733 */       if (!$assertionsDisabled) throw new AssertionError(str);
/*  734 */       return str;
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
/*      */     public MethodHandle findStatic(Class<?> paramClass, String paramString, MethodType paramMethodType)
/*      */       throws NoSuchMethodException, IllegalAccessException
/*      */     {
/*  776 */       MemberName localMemberName = resolveOrFail((byte)6, paramClass, paramString, paramMethodType);
/*  777 */       return getDirectMethod((byte)6, paramClass, localMemberName, findBoundCallerClass(localMemberName));
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
/*      */     public MethodHandle findVirtual(Class<?> paramClass, String paramString, MethodType paramMethodType)
/*      */       throws NoSuchMethodException, IllegalAccessException
/*      */     {
/*  852 */       if (paramClass == MethodHandle.class) {
/*  853 */         MethodHandle localMethodHandle = findVirtualForMH(paramString, paramMethodType);
/*  854 */         if (localMethodHandle != null) return localMethodHandle;
/*      */       }
/*  856 */       byte b = paramClass.isInterface() ? 9 : 5;
/*  857 */       MemberName localMemberName = resolveOrFail(b, paramClass, paramString, paramMethodType);
/*  858 */       return getDirectMethod(b, paramClass, localMemberName, findBoundCallerClass(localMemberName));
/*      */     }
/*      */     
/*      */     private MethodHandle findVirtualForMH(String paramString, MethodType paramMethodType) {
/*  862 */       if ("invoke".equals(paramString))
/*  863 */         return MethodHandles.invoker(paramMethodType);
/*  864 */       if ("invokeExact".equals(paramString))
/*  865 */         return MethodHandles.exactInvoker(paramMethodType);
/*  866 */       assert (!MemberName.isMethodHandleInvokeName(paramString));
/*  867 */       return null;
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
/*      */     public MethodHandle findConstructor(Class<?> paramClass, MethodType paramMethodType)
/*      */       throws NoSuchMethodException, IllegalAccessException
/*      */     {
/*  916 */       String str = "<init>";
/*  917 */       MemberName localMemberName = resolveOrFail((byte)8, paramClass, str, paramMethodType);
/*  918 */       return getDirectConstructor(paramClass, localMemberName);
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
/*      */     public MethodHandle findSpecial(Class<?> paramClass1, String paramString, MethodType paramMethodType, Class<?> paramClass2)
/*      */       throws NoSuchMethodException, IllegalAccessException
/*      */     {
/*  995 */       checkSpecialCaller(paramClass2);
/*  996 */       Lookup localLookup = in(paramClass2);
/*  997 */       MemberName localMemberName = localLookup.resolveOrFail((byte)7, paramClass1, paramString, paramMethodType);
/*  998 */       return localLookup.getDirectMethod((byte)7, paramClass1, localMemberName, findBoundCallerClass(localMemberName));
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
/*      */     public MethodHandle findGetter(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */       throws NoSuchFieldException, IllegalAccessException
/*      */     {
/* 1019 */       MemberName localMemberName = resolveOrFail((byte)1, paramClass1, paramString, paramClass2);
/* 1020 */       return getDirectField((byte)1, paramClass1, localMemberName);
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
/*      */     public MethodHandle findSetter(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */       throws NoSuchFieldException, IllegalAccessException
/*      */     {
/* 1041 */       MemberName localMemberName = resolveOrFail((byte)3, paramClass1, paramString, paramClass2);
/* 1042 */       return getDirectField((byte)3, paramClass1, localMemberName);
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
/*      */     public MethodHandle findStaticGetter(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */       throws NoSuchFieldException, IllegalAccessException
/*      */     {
/* 1065 */       MemberName localMemberName = resolveOrFail((byte)2, paramClass1, paramString, paramClass2);
/* 1066 */       return getDirectField((byte)2, paramClass1, localMemberName);
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
/*      */     public MethodHandle findStaticSetter(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */       throws NoSuchFieldException, IllegalAccessException
/*      */     {
/* 1089 */       MemberName localMemberName = resolveOrFail((byte)4, paramClass1, paramString, paramClass2);
/* 1090 */       return getDirectField((byte)4, paramClass1, localMemberName);
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
/*      */     public MethodHandle bind(Object paramObject, String paramString, MethodType paramMethodType)
/*      */       throws NoSuchMethodException, IllegalAccessException
/*      */     {
/* 1142 */       Class localClass = paramObject.getClass();
/* 1143 */       MemberName localMemberName = resolveOrFail((byte)7, localClass, paramString, paramMethodType);
/* 1144 */       MethodHandle localMethodHandle = getDirectMethodNoRestrict((byte)7, localClass, localMemberName, findBoundCallerClass(localMemberName));
/* 1145 */       return localMethodHandle.bindReceiver(paramObject).setVarargs(localMemberName);
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
/*      */     public MethodHandle unreflect(Method paramMethod)
/*      */       throws IllegalAccessException
/*      */     {
/* 1175 */       if (paramMethod.getDeclaringClass() == MethodHandle.class) {
/* 1176 */         localObject = unreflectForMH(paramMethod);
/* 1177 */         if (localObject != null) return (MethodHandle)localObject;
/*      */       }
/* 1179 */       Object localObject = new MemberName(paramMethod);
/* 1180 */       byte b = ((MemberName)localObject).getReferenceKind();
/* 1181 */       if (b == 7)
/* 1182 */         b = 5;
/* 1183 */       assert (((MemberName)localObject).isMethod());
/* 1184 */       Lookup localLookup = paramMethod.isAccessible() ? IMPL_LOOKUP : this;
/* 1185 */       return localLookup.getDirectMethodNoSecurityManager(b, ((MemberName)localObject).getDeclaringClass(), (MemberName)localObject, findBoundCallerClass((MemberName)localObject));
/*      */     }
/*      */     
/*      */     private MethodHandle unreflectForMH(Method paramMethod) {
/* 1189 */       if (MemberName.isMethodHandleInvokeName(paramMethod.getName()))
/* 1190 */         return MethodHandleImpl.fakeMethodHandleInvoke(new MemberName(paramMethod));
/* 1191 */       return null;
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
/*      */     public MethodHandle unreflectSpecial(Method paramMethod, Class<?> paramClass)
/*      */       throws IllegalAccessException
/*      */     {
/* 1224 */       checkSpecialCaller(paramClass);
/* 1225 */       Lookup localLookup = in(paramClass);
/* 1226 */       MemberName localMemberName = new MemberName(paramMethod, true);
/* 1227 */       assert (localMemberName.isMethod());
/*      */       
/* 1229 */       return localLookup.getDirectMethodNoSecurityManager((byte)7, localMemberName.getDeclaringClass(), localMemberName, findBoundCallerClass(localMemberName));
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
/*      */     public MethodHandle unreflectConstructor(Constructor<?> paramConstructor)
/*      */       throws IllegalAccessException
/*      */     {
/* 1257 */       MemberName localMemberName = new MemberName(paramConstructor);
/* 1258 */       assert (localMemberName.isConstructor());
/* 1259 */       Lookup localLookup = paramConstructor.isAccessible() ? IMPL_LOOKUP : this;
/* 1260 */       return localLookup.getDirectConstructorNoSecurityManager(localMemberName.getDeclaringClass(), localMemberName);
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
/* 1282 */     public MethodHandle unreflectGetter(Field paramField)
/* 1282 */       throws IllegalAccessException { return unreflectField(paramField, false); }
/*      */     
/*      */     private MethodHandle unreflectField(Field paramField, boolean paramBoolean) throws IllegalAccessException {
/* 1285 */       MemberName localMemberName = new MemberName(paramField, paramBoolean);
/* 1286 */       assert (paramBoolean ? 
/* 1287 */         MethodHandleNatives.refKindIsSetter(localMemberName.getReferenceKind()) : 
/* 1288 */         MethodHandleNatives.refKindIsGetter(localMemberName.getReferenceKind()));
/* 1289 */       Lookup localLookup = paramField.isAccessible() ? IMPL_LOOKUP : this;
/* 1290 */       return localLookup.getDirectFieldNoSecurityManager(localMemberName.getReferenceKind(), paramField.getDeclaringClass(), localMemberName);
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
/*      */     public MethodHandle unreflectSetter(Field paramField)
/*      */       throws IllegalAccessException
/*      */     {
/* 1312 */       return unreflectField(paramField, true);
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
/*      */     public MethodHandleInfo revealDirect(MethodHandle paramMethodHandle)
/*      */     {
/* 1334 */       MemberName localMemberName = paramMethodHandle.internalMemberName();
/* 1335 */       if ((localMemberName == null) || ((!localMemberName.isResolved()) && (!localMemberName.isMethodHandleInvoke())))
/* 1336 */         throw MethodHandleStatics.newIllegalArgumentException("not a direct method handle");
/* 1337 */       Class localClass1 = localMemberName.getDeclaringClass();
/* 1338 */       byte b = localMemberName.getReferenceKind();
/* 1339 */       assert (MethodHandleNatives.refKindIsValid(b));
/* 1340 */       if ((b == 7) && (!paramMethodHandle.isInvokeSpecial()))
/*      */       {
/*      */ 
/*      */ 
/* 1344 */         b = 5; }
/* 1345 */       if ((b == 5) && (localClass1.isInterface()))
/*      */       {
/* 1347 */         b = 9;
/*      */       }
/*      */       try {
/* 1350 */         checkAccess(b, localClass1, localMemberName);
/* 1351 */         checkSecurityManager(localClass1, localMemberName);
/*      */       } catch (IllegalAccessException localIllegalAccessException) {
/* 1353 */         throw new IllegalArgumentException(localIllegalAccessException);
/*      */       }
/* 1355 */       if ((this.allowedModes != -1) && (localMemberName.isCallerSensitive())) {
/* 1356 */         Class localClass2 = paramMethodHandle.internalCallerClass();
/* 1357 */         if ((!hasPrivateAccess()) || (localClass2 != lookupClass())) {
/* 1358 */           throw new IllegalArgumentException("method handle is caller sensitive: " + localClass2);
/*      */         }
/*      */       }
/* 1361 */       return new InfoFromMemberName(this, localMemberName, b);
/*      */     }
/*      */     
/*      */     MemberName resolveOrFail(byte paramByte, Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */       throws NoSuchFieldException, IllegalAccessException
/*      */     {
/* 1367 */       checkSymbolicClass(paramClass1);
/* 1368 */       paramString.getClass();
/* 1369 */       paramClass2.getClass();
/* 1370 */       return MethodHandles.IMPL_NAMES.resolveOrFail(paramByte, new MemberName(paramClass1, paramString, paramClass2, paramByte), lookupClassOrNull(), NoSuchFieldException.class);
/*      */     }
/*      */     
/*      */     MemberName resolveOrFail(byte paramByte, Class<?> paramClass, String paramString, MethodType paramMethodType) throws NoSuchMethodException, IllegalAccessException
/*      */     {
/* 1375 */       checkSymbolicClass(paramClass);
/* 1376 */       paramString.getClass();
/* 1377 */       paramMethodType.getClass();
/* 1378 */       checkMethodName(paramByte, paramString);
/* 1379 */       return MethodHandles.IMPL_NAMES.resolveOrFail(paramByte, new MemberName(paramClass, paramString, paramMethodType, paramByte), lookupClassOrNull(), NoSuchMethodException.class);
/*      */     }
/*      */     
/*      */     MemberName resolveOrFail(byte paramByte, MemberName paramMemberName) throws ReflectiveOperationException
/*      */     {
/* 1384 */       checkSymbolicClass(paramMemberName.getDeclaringClass());
/* 1385 */       paramMemberName.getName().getClass();
/* 1386 */       paramMemberName.getType().getClass();
/* 1387 */       return MethodHandles.IMPL_NAMES.resolveOrFail(paramByte, paramMemberName, lookupClassOrNull(), ReflectiveOperationException.class);
/*      */     }
/*      */     
/*      */     void checkSymbolicClass(Class<?> paramClass) throws IllegalAccessException
/*      */     {
/* 1392 */       paramClass.getClass();
/* 1393 */       Class localClass = lookupClassOrNull();
/* 1394 */       if ((localClass != null) && (!VerifyAccess.isClassAccessible(paramClass, localClass, this.allowedModes))) {
/* 1395 */         throw new MemberName(paramClass).makeAccessException("symbolic reference class is not public", this);
/*      */       }
/*      */     }
/*      */     
/*      */     void checkMethodName(byte paramByte, String paramString) throws NoSuchMethodException {
/* 1400 */       if ((paramString.startsWith("<")) && (paramByte != 8)) {
/* 1401 */         throw new NoSuchMethodException("illegal method name: " + paramString);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Class<?> findBoundCallerClass(MemberName paramMemberName)
/*      */       throws IllegalAccessException
/*      */     {
/* 1411 */       Class localClass = null;
/* 1412 */       if (MethodHandleNatives.isCallerSensitive(paramMemberName))
/*      */       {
/* 1414 */         if (hasPrivateAccess()) {
/* 1415 */           localClass = this.lookupClass;
/*      */         } else {
/* 1417 */           throw new IllegalAccessException("Attempt to lookup caller-sensitive method using restricted lookup object");
/*      */         }
/*      */       }
/* 1420 */       return localClass;
/*      */     }
/*      */     
/*      */     private boolean hasPrivateAccess() {
/* 1424 */       return (this.allowedModes & 0x2) != 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void checkSecurityManager(Class<?> paramClass, MemberName paramMemberName)
/*      */     {
/* 1433 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 1434 */       if (localSecurityManager == null) return;
/* 1435 */       if (this.allowedModes == -1) { return;
/*      */       }
/*      */       
/* 1438 */       boolean bool = hasPrivateAccess();
/* 1439 */       if ((!bool) || 
/* 1440 */         (!VerifyAccess.classLoaderIsAncestor(this.lookupClass, paramClass))) {
/* 1441 */         ReflectUtil.checkPackageAccess(paramClass);
/*      */       }
/*      */       
/*      */ 
/* 1445 */       if (paramMemberName.isPublic()) return;
/* 1446 */       if (!bool) {
/* 1447 */         localSecurityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
/*      */       }
/*      */       
/*      */ 
/* 1451 */       Class localClass = paramMemberName.getDeclaringClass();
/* 1452 */       if ((!bool) && (localClass != paramClass)) {
/* 1453 */         ReflectUtil.checkPackageAccess(localClass);
/*      */       }
/*      */     }
/*      */     
/*      */     void checkMethod(byte paramByte, Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException {
/* 1458 */       int i = paramByte == 6 ? 1 : 0;
/*      */       String str;
/* 1460 */       if (paramMemberName.isConstructor()) {
/* 1461 */         str = "expected a method, not a constructor";
/* 1462 */       } else if (!paramMemberName.isMethod()) {
/* 1463 */         str = "expected a method";
/* 1464 */       } else if (i != paramMemberName.isStatic()) {
/* 1465 */         str = i != 0 ? "expected a static method" : "expected a non-static method";
/*      */       } else {
/* 1467 */         checkAccess(paramByte, paramClass, paramMemberName);return; }
/* 1468 */       throw paramMemberName.makeAccessException(str, this);
/*      */     }
/*      */     
/*      */     void checkField(byte paramByte, Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException {
/* 1472 */       int i = !MethodHandleNatives.refKindHasReceiver(paramByte) ? 1 : 0;
/*      */       String str;
/* 1474 */       if (i != paramMemberName.isStatic()) {
/* 1475 */         str = i != 0 ? "expected a static field" : "expected a non-static field";
/*      */       } else {
/* 1477 */         checkAccess(paramByte, paramClass, paramMemberName);return; }
/* 1478 */       throw paramMemberName.makeAccessException(str, this);
/*      */     }
/*      */     
/*      */     void checkAccess(byte paramByte, Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException
/*      */     {
/* 1483 */       assert ((paramMemberName.referenceKindIsConsistentWith(paramByte)) && 
/* 1484 */         (MethodHandleNatives.refKindIsValid(paramByte)) && 
/* 1485 */         (MethodHandleNatives.refKindIsField(paramByte) == paramMemberName.isField()));
/* 1486 */       int i = this.allowedModes;
/* 1487 */       if (i == -1) return;
/* 1488 */       int j = paramMemberName.getModifiers();
/* 1489 */       if ((Modifier.isProtected(j)) && (paramByte == 5))
/*      */       {
/* 1491 */         if ((paramMemberName.getDeclaringClass() == Object.class) && 
/* 1492 */           (paramMemberName.getName().equals("clone")) && 
/* 1493 */           (paramClass.isArray()))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1507 */           j ^= 0x5; }
/*      */       }
/* 1509 */       if ((Modifier.isProtected(j)) && (paramByte == 8))
/*      */       {
/* 1511 */         j ^= 0x4;
/*      */       }
/* 1513 */       if ((Modifier.isFinal(j)) && 
/* 1514 */         (MethodHandleNatives.refKindIsSetter(paramByte)))
/* 1515 */         throw paramMemberName.makeAccessException("unexpected set of a final field", this);
/* 1516 */       if ((Modifier.isPublic(j)) && (Modifier.isPublic(paramClass.getModifiers())) && (i != 0))
/* 1517 */         return;
/* 1518 */       int k = fixmods(j);
/* 1519 */       if ((k & i) != 0) {
/* 1520 */         if (!VerifyAccess.isMemberAccessible(paramClass, paramMemberName.getDeclaringClass(), j, 
/* 1521 */           lookupClass(), i)) {}
/*      */ 
/*      */ 
/*      */       }
/* 1525 */       else if (((k & 0x4) != 0) && ((i & 0x8) != 0) && 
/* 1526 */         (VerifyAccess.isSamePackage(paramMemberName.getDeclaringClass(), lookupClass()))) {
/* 1527 */         return;
/*      */       }
/* 1529 */       throw paramMemberName.makeAccessException(accessFailedMessage(paramClass, paramMemberName), this);
/*      */     }
/*      */     
/*      */     String accessFailedMessage(Class<?> paramClass, MemberName paramMemberName) {
/* 1533 */       Class localClass = paramMemberName.getDeclaringClass();
/* 1534 */       int i = paramMemberName.getModifiers();
/*      */       
/*      */ 
/*      */ 
/* 1538 */       int j = (Modifier.isPublic(localClass.getModifiers())) && ((localClass == paramClass) || (Modifier.isPublic(paramClass.getModifiers()))) ? 1 : 0;
/* 1539 */       if ((j == 0) && ((this.allowedModes & 0x8) != 0))
/*      */       {
/*      */ 
/* 1542 */         j = (VerifyAccess.isClassAccessible(localClass, lookupClass(), 15)) && ((localClass == paramClass) || (VerifyAccess.isClassAccessible(paramClass, lookupClass(), 15))) ? 1 : 0;
/*      */       }
/* 1544 */       if (j == 0)
/* 1545 */         return "class is not public";
/* 1546 */       if (Modifier.isPublic(i))
/* 1547 */         return "access to public member failed";
/* 1548 */       if (Modifier.isPrivate(i))
/* 1549 */         return "member is private";
/* 1550 */       if (Modifier.isProtected(i))
/* 1551 */         return "member is protected";
/* 1552 */       return "member is private to package";
/*      */     }
/*      */     
/*      */     private void checkSpecialCaller(Class<?> paramClass)
/*      */       throws IllegalAccessException
/*      */     {
/* 1558 */       int i = this.allowedModes;
/* 1559 */       if (i == -1) return;
/* 1560 */       if ((!hasPrivateAccess()) || 
/* 1561 */         (paramClass != lookupClass()))
/*      */       {
/*      */ 
/*      */ 
/* 1565 */         throw new MemberName(paramClass).makeAccessException("no private access for invokespecial", this);
/*      */       }
/*      */     }
/*      */     
/*      */     private boolean restrictProtectedReceiver(MemberName paramMemberName)
/*      */     {
/* 1571 */       if ((!paramMemberName.isProtected()) || (paramMemberName.isStatic()) || (this.allowedModes == -1) || 
/*      */       
/* 1573 */         (paramMemberName.getDeclaringClass() == lookupClass()) || 
/* 1574 */         (VerifyAccess.isSamePackage(paramMemberName.getDeclaringClass(), lookupClass())))
/*      */       {
/*      */ 
/* 1577 */         return false; }
/* 1578 */       return true;
/*      */     }
/*      */     
/* 1581 */     private MethodHandle restrictReceiver(MemberName paramMemberName, MethodHandle paramMethodHandle, Class<?> paramClass) throws IllegalAccessException { assert (!paramMemberName.isStatic());
/*      */       
/* 1583 */       if (!paramMemberName.getDeclaringClass().isAssignableFrom(paramClass)) {
/* 1584 */         throw paramMemberName.makeAccessException("caller class must be a subclass below the method", paramClass);
/*      */       }
/* 1586 */       MethodType localMethodType1 = paramMethodHandle.type();
/* 1587 */       if (localMethodType1.parameterType(0) == paramClass) return paramMethodHandle;
/* 1588 */       MethodType localMethodType2 = localMethodType1.changeParameterType(0, paramClass);
/* 1589 */       return paramMethodHandle.viewAsType(localMethodType2);
/*      */     }
/*      */     
/*      */ 
/*      */     private MethodHandle getDirectMethod(byte paramByte, Class<?> paramClass1, MemberName paramMemberName, Class<?> paramClass2)
/*      */       throws IllegalAccessException
/*      */     {
/* 1596 */       return getDirectMethodCommon(paramByte, paramClass1, paramMemberName, true, true, paramClass2);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectMethodNoRestrict(byte paramByte, Class<?> paramClass1, MemberName paramMemberName, Class<?> paramClass2)
/*      */       throws IllegalAccessException
/*      */     {
/* 1602 */       return getDirectMethodCommon(paramByte, paramClass1, paramMemberName, true, false, paramClass2);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectMethodNoSecurityManager(byte paramByte, Class<?> paramClass1, MemberName paramMemberName, Class<?> paramClass2)
/*      */       throws IllegalAccessException
/*      */     {
/* 1608 */       return getDirectMethodCommon(paramByte, paramClass1, paramMemberName, false, true, paramClass2);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectMethodCommon(byte paramByte, Class<?> paramClass1, MemberName paramMemberName, boolean paramBoolean1, boolean paramBoolean2, Class<?> paramClass2)
/*      */       throws IllegalAccessException
/*      */     {
/* 1614 */       checkMethod(paramByte, paramClass1, paramMemberName);
/*      */       
/* 1616 */       if (paramBoolean1)
/* 1617 */         checkSecurityManager(paramClass1, paramMemberName);
/* 1618 */       assert (!paramMemberName.isMethodHandleInvoke());
/*      */       
/* 1620 */       if ((paramByte == 7) && 
/* 1621 */         (paramClass1 != lookupClass()) && 
/* 1622 */         (!paramClass1.isInterface()) && 
/* 1623 */         (paramClass1 != lookupClass().getSuperclass()) && 
/* 1624 */         (paramClass1.isAssignableFrom(lookupClass()))) {
/* 1625 */         assert (!paramMemberName.getName().equals("<init>"));
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1633 */         localObject = lookupClass();
/*      */         MemberName localMemberName;
/*      */         do {
/* 1636 */           localObject = ((Class)localObject).getSuperclass();
/*      */           
/*      */ 
/* 1639 */           localMemberName = new MemberName((Class)localObject, paramMemberName.getName(), paramMemberName.getMethodType(), (byte)7);
/*      */           
/* 1641 */           localMemberName = MethodHandles.IMPL_NAMES.resolveOrNull(paramByte, localMemberName, lookupClassOrNull());
/* 1642 */         } while ((localMemberName == null) && (paramClass1 != localObject));
/*      */         
/* 1644 */         if (localMemberName == null) throw new InternalError(paramMemberName.toString());
/* 1645 */         paramMemberName = localMemberName;
/* 1646 */         paramClass1 = (Class<?>)localObject;
/*      */         
/* 1648 */         checkMethod(paramByte, paramClass1, paramMemberName);
/*      */       }
/*      */       
/* 1651 */       Object localObject = DirectMethodHandle.make(paramByte, paramClass1, paramMemberName);
/* 1652 */       localObject = maybeBindCaller(paramMemberName, (MethodHandle)localObject, paramClass2);
/* 1653 */       localObject = ((MethodHandle)localObject).setVarargs(paramMemberName);
/*      */       
/* 1655 */       if ((paramBoolean2) && ((paramByte == 7) || (
/*      */       
/* 1657 */         (MethodHandleNatives.refKindHasReceiver(paramByte)) && 
/* 1658 */         (restrictProtectedReceiver(paramMemberName)))))
/* 1659 */         localObject = restrictReceiver(paramMemberName, (MethodHandle)localObject, lookupClass());
/* 1660 */       return (MethodHandle)localObject;
/*      */     }
/*      */     
/*      */     private MethodHandle maybeBindCaller(MemberName paramMemberName, MethodHandle paramMethodHandle, Class<?> paramClass) throws IllegalAccessException
/*      */     {
/* 1665 */       if ((this.allowedModes == -1) || (!MethodHandleNatives.isCallerSensitive(paramMemberName)))
/* 1666 */         return paramMethodHandle;
/* 1667 */       Object localObject = this.lookupClass;
/* 1668 */       if (!hasPrivateAccess())
/* 1669 */         localObject = paramClass;
/* 1670 */       MethodHandle localMethodHandle = MethodHandleImpl.bindCaller(paramMethodHandle, (Class)localObject);
/*      */       
/* 1672 */       return localMethodHandle;
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectField(byte paramByte, Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException
/*      */     {
/* 1677 */       return getDirectFieldCommon(paramByte, paramClass, paramMemberName, true);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectFieldNoSecurityManager(byte paramByte, Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException
/*      */     {
/* 1682 */       return getDirectFieldCommon(paramByte, paramClass, paramMemberName, false);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectFieldCommon(byte paramByte, Class<?> paramClass, MemberName paramMemberName, boolean paramBoolean) throws IllegalAccessException
/*      */     {
/* 1687 */       checkField(paramByte, paramClass, paramMemberName);
/*      */       
/* 1689 */       if (paramBoolean)
/* 1690 */         checkSecurityManager(paramClass, paramMemberName);
/* 1691 */       Object localObject = DirectMethodHandle.make(paramClass, paramMemberName);
/*      */       
/* 1693 */       int i = (MethodHandleNatives.refKindHasReceiver(paramByte)) && (restrictProtectedReceiver(paramMemberName)) ? 1 : 0;
/* 1694 */       if (i != 0)
/* 1695 */         localObject = restrictReceiver(paramMemberName, (MethodHandle)localObject, lookupClass());
/* 1696 */       return (MethodHandle)localObject;
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectConstructor(Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException
/*      */     {
/* 1701 */       return getDirectConstructorCommon(paramClass, paramMemberName, true);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectConstructorNoSecurityManager(Class<?> paramClass, MemberName paramMemberName) throws IllegalAccessException
/*      */     {
/* 1706 */       return getDirectConstructorCommon(paramClass, paramMemberName, false);
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectConstructorCommon(Class<?> paramClass, MemberName paramMemberName, boolean paramBoolean) throws IllegalAccessException
/*      */     {
/* 1711 */       assert (paramMemberName.isConstructor());
/* 1712 */       checkAccess((byte)8, paramClass, paramMemberName);
/*      */       
/* 1714 */       if (paramBoolean)
/* 1715 */         checkSecurityManager(paramClass, paramMemberName);
/* 1716 */       assert (!MethodHandleNatives.isCallerSensitive(paramMemberName));
/* 1717 */       return DirectMethodHandle.make(paramMemberName).setVarargs(paramMemberName);
/*      */     }
/*      */     
/*      */ 
/*      */     MethodHandle linkMethodHandleConstant(byte paramByte, Class<?> paramClass, String paramString, Object paramObject)
/*      */       throws ReflectiveOperationException
/*      */     {
/* 1724 */       if ((!(paramObject instanceof Class)) && (!(paramObject instanceof MethodType)))
/* 1725 */         throw new InternalError("unresolved MemberName");
/* 1726 */       MemberName localMemberName1 = new MemberName(paramByte, paramClass, paramString, paramObject);
/* 1727 */       MethodHandle localMethodHandle = (MethodHandle)LOOKASIDE_TABLE.get(localMemberName1);
/* 1728 */       if (localMethodHandle != null) {
/* 1729 */         checkSymbolicClass(paramClass);
/* 1730 */         return localMethodHandle;
/*      */       }
/*      */       
/* 1733 */       if ((paramClass == MethodHandle.class) && (paramByte == 5)) {
/* 1734 */         localMethodHandle = findVirtualForMH(localMemberName1.getName(), localMemberName1.getMethodType());
/* 1735 */         if (localMethodHandle != null) {
/* 1736 */           return localMethodHandle;
/*      */         }
/*      */       }
/* 1739 */       MemberName localMemberName2 = resolveOrFail(paramByte, localMemberName1);
/* 1740 */       localMethodHandle = getDirectMethodForConstant(paramByte, paramClass, localMemberName2);
/* 1741 */       if (((localMethodHandle instanceof DirectMethodHandle)) && 
/* 1742 */         (canBeCached(paramByte, paramClass, localMemberName2))) {
/* 1743 */         MemberName localMemberName3 = localMethodHandle.internalMemberName();
/* 1744 */         if (localMemberName3 != null) {
/* 1745 */           localMemberName3 = localMemberName3.asNormalOriginal();
/*      */         }
/* 1747 */         if (localMemberName1.equals(localMemberName3)) {
/* 1748 */           LOOKASIDE_TABLE.put(localMemberName3, (DirectMethodHandle)localMethodHandle);
/*      */         }
/*      */       }
/* 1751 */       return localMethodHandle;
/*      */     }
/*      */     
/*      */     private boolean canBeCached(byte paramByte, Class<?> paramClass, MemberName paramMemberName) {
/* 1755 */       if (paramByte == 7) {
/* 1756 */         return false;
/*      */       }
/* 1758 */       if ((!Modifier.isPublic(paramClass.getModifiers())) || 
/* 1759 */         (!Modifier.isPublic(paramMemberName.getDeclaringClass().getModifiers())) || 
/* 1760 */         (!paramMemberName.isPublic()) || 
/* 1761 */         (paramMemberName.isCallerSensitive())) {
/* 1762 */         return false;
/*      */       }
/* 1764 */       ClassLoader localClassLoader = paramClass.getClassLoader();
/* 1765 */       Object localObject; if (!VM.isSystemDomainLoader(localClassLoader)) {
/* 1766 */         localObject = ClassLoader.getSystemClassLoader();
/* 1767 */         int i = 0;
/* 1768 */         while (localObject != null) {
/* 1769 */           if (localClassLoader == localObject) { i = 1; break; }
/* 1770 */           localObject = ((ClassLoader)localObject).getParent();
/*      */         }
/* 1772 */         if (i == 0) {
/* 1773 */           return false;
/*      */         }
/*      */       }
/*      */       try {
/* 1777 */         localObject = MethodHandles.publicLookup().resolveOrFail(paramByte, new MemberName(paramByte, paramClass, paramMemberName
/* 1778 */           .getName(), paramMemberName.getType()));
/* 1779 */         checkSecurityManager(paramClass, (MemberName)localObject);
/*      */       } catch (ReflectiveOperationException|SecurityException localReflectiveOperationException) {
/* 1781 */         return false;
/*      */       }
/* 1783 */       return true;
/*      */     }
/*      */     
/*      */     private MethodHandle getDirectMethodForConstant(byte paramByte, Class<?> paramClass, MemberName paramMemberName) throws ReflectiveOperationException
/*      */     {
/* 1788 */       if (MethodHandleNatives.refKindIsField(paramByte))
/* 1789 */         return getDirectFieldNoSecurityManager(paramByte, paramClass, paramMemberName);
/* 1790 */       if (MethodHandleNatives.refKindIsMethod(paramByte))
/* 1791 */         return getDirectMethodNoSecurityManager(paramByte, paramClass, paramMemberName, this.lookupClass);
/* 1792 */       if (paramByte == 8) {
/* 1793 */         return getDirectConstructorNoSecurityManager(paramClass, paramMemberName);
/*      */       }
/*      */       
/* 1796 */       throw MethodHandleStatics.newIllegalArgumentException("bad MethodHandle constant #" + paramMemberName);
/*      */     }
/*      */     
/* 1799 */     static ConcurrentHashMap<MemberName, DirectMethodHandle> LOOKASIDE_TABLE = new ConcurrentHashMap();
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
/*      */   public static MethodHandle arrayElementGetter(Class<?> paramClass)
/*      */     throws IllegalArgumentException
/*      */   {
/* 1814 */     return MethodHandleImpl.makeArrayElementAccessor(paramClass, false);
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
/*      */   public static MethodHandle arrayElementSetter(Class<?> paramClass)
/*      */     throws IllegalArgumentException
/*      */   {
/* 1829 */     return MethodHandleImpl.makeArrayElementAccessor(paramClass, true);
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
/*      */   public static MethodHandle spreadInvoker(MethodType paramMethodType, int paramInt)
/*      */   {
/* 1882 */     if ((paramInt < 0) || (paramInt > paramMethodType.parameterCount()))
/* 1883 */       throw new IllegalArgumentException("bad argument count " + paramInt);
/* 1884 */     return paramMethodType.invokers().spreadInvoker(paramInt);
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
/*      */   public static MethodHandle exactInvoker(MethodType paramMethodType)
/*      */   {
/* 1924 */     return paramMethodType.invokers().exactInvoker();
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
/*      */   public static MethodHandle invoker(MethodType paramMethodType)
/*      */   {
/* 1963 */     return paramMethodType.invokers().generalInvoker();
/*      */   }
/*      */   
/*      */   static MethodHandle basicInvoker(MethodType paramMethodType)
/*      */   {
/* 1968 */     return paramMethodType.form().basicInvoker();
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
/*      */   public static MethodHandle explicitCastArguments(MethodHandle paramMethodHandle, MethodType paramMethodType)
/*      */   {
/* 2019 */     if (!paramMethodHandle.type().isCastableTo(paramMethodType)) {
/* 2020 */       throw new WrongMethodTypeException("cannot explicitly cast " + paramMethodHandle + " to " + paramMethodType);
/*      */     }
/* 2022 */     return MethodHandleImpl.makePairwiseConvert(paramMethodHandle, paramMethodType, 2);
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
/*      */   public static MethodHandle permuteArguments(MethodHandle paramMethodHandle, MethodType paramMethodType, int... paramVarArgs)
/*      */   {
/* 2086 */     paramVarArgs = (int[])paramVarArgs.clone();
/* 2087 */     checkReorder(paramVarArgs, paramMethodType, paramMethodHandle.type());
/* 2088 */     return paramMethodHandle.permuteArguments(paramMethodType, paramVarArgs);
/*      */   }
/*      */   
/*      */   private static void checkReorder(int[] paramArrayOfInt, MethodType paramMethodType1, MethodType paramMethodType2) {
/* 2092 */     if (paramMethodType1.returnType() != paramMethodType2.returnType()) {
/* 2093 */       throw MethodHandleStatics.newIllegalArgumentException("return types do not match", paramMethodType2, paramMethodType1);
/*      */     }
/* 2095 */     if (paramArrayOfInt.length == paramMethodType2.parameterCount()) {
/* 2096 */       int i = paramMethodType1.parameterCount();
/* 2097 */       int j = 0;
/* 2098 */       for (int k = 0; k < paramArrayOfInt.length; k++) {
/* 2099 */         int m = paramArrayOfInt[k];
/* 2100 */         if ((m < 0) || (m >= i)) {
/* 2101 */           j = 1; break;
/*      */         }
/* 2103 */         Class localClass1 = paramMethodType1.parameterType(m);
/* 2104 */         Class localClass2 = paramMethodType2.parameterType(k);
/* 2105 */         if (localClass1 != localClass2) {
/* 2106 */           throw MethodHandleStatics.newIllegalArgumentException("parameter types do not match after reorder", paramMethodType2, paramMethodType1);
/*      */         }
/*      */       }
/* 2109 */       if (j == 0) return;
/*      */     }
/* 2111 */     throw MethodHandleStatics.newIllegalArgumentException("bad reorder array: " + Arrays.toString(paramArrayOfInt));
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
/*      */   public static MethodHandle constant(Class<?> paramClass, Object paramObject)
/*      */   {
/* 2131 */     if (paramClass.isPrimitive()) {
/* 2132 */       if (paramClass == Void.TYPE)
/* 2133 */         throw MethodHandleStatics.newIllegalArgumentException("void type");
/* 2134 */       Wrapper localWrapper = Wrapper.forPrimitiveType(paramClass);
/* 2135 */       return insertArguments(identity(paramClass), 0, new Object[] { localWrapper.convert(paramObject, paramClass) });
/*      */     }
/* 2137 */     return identity(paramClass).bindTo(paramClass.cast(paramObject));
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
/*      */   public static MethodHandle identity(Class<?> paramClass)
/*      */   {
/* 2150 */     if (paramClass == Void.TYPE)
/* 2151 */       throw MethodHandleStatics.newIllegalArgumentException("void type");
/* 2152 */     if (paramClass == Object.class)
/* 2153 */       return ValueConversions.identity();
/* 2154 */     if (paramClass.isPrimitive()) {
/* 2155 */       return ValueConversions.identity(Wrapper.forPrimitiveType(paramClass));
/*      */     }
/* 2157 */     return MethodHandleImpl.makeReferenceIdentity(paramClass);
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
/*      */   public static MethodHandle insertArguments(MethodHandle paramMethodHandle, int paramInt, Object... paramVarArgs)
/*      */   {
/* 2192 */     int i = paramVarArgs.length;
/* 2193 */     MethodType localMethodType = paramMethodHandle.type();
/* 2194 */     int j = localMethodType.parameterCount();
/* 2195 */     int k = j - i;
/* 2196 */     if (k < 0)
/* 2197 */       throw MethodHandleStatics.newIllegalArgumentException("too many values to insert");
/* 2198 */     if ((paramInt < 0) || (paramInt > k))
/* 2199 */       throw MethodHandleStatics.newIllegalArgumentException("no argument type to append");
/* 2200 */     MethodHandle localMethodHandle = paramMethodHandle;
/* 2201 */     for (int m = 0; m < i; m++) {
/* 2202 */       Object localObject = paramVarArgs[m];
/* 2203 */       Class localClass = localMethodType.parameterType(paramInt + m);
/* 2204 */       if (localClass.isPrimitive()) {
/* 2205 */         char c = 'I';
/* 2206 */         Wrapper localWrapper = Wrapper.forPrimitiveType(localClass);
/* 2207 */         switch (localWrapper) {
/* 2208 */         case LONG:  c = 'J'; break;
/* 2209 */         case FLOAT:  c = 'F'; break;
/* 2210 */         case DOUBLE:  c = 'D';
/*      */         }
/*      */         
/* 2213 */         localObject = localWrapper.convert(localObject, localClass);
/* 2214 */         localMethodHandle = localMethodHandle.bindArgument(paramInt, c, localObject);
/*      */       }
/*      */       else {
/* 2217 */         localObject = localClass.cast(localObject);
/* 2218 */         if (paramInt == 0) {
/* 2219 */           localMethodHandle = localMethodHandle.bindReceiver(localObject);
/*      */         } else
/* 2221 */           localMethodHandle = localMethodHandle.bindArgument(paramInt, 'L', localObject);
/*      */       }
/*      */     }
/* 2224 */     return localMethodHandle;
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
/*      */   public static MethodHandle dropArguments(MethodHandle paramMethodHandle, int paramInt, List<Class<?>> paramList)
/*      */   {
/* 2271 */     MethodType localMethodType1 = paramMethodHandle.type();
/* 2272 */     int i = paramList.size();
/* 2273 */     MethodType.checkSlotCount(i);
/* 2274 */     if (i == 0) return paramMethodHandle;
/* 2275 */     int j = localMethodType1.parameterCount();
/* 2276 */     int k = j + i;
/* 2277 */     if ((paramInt < 0) || (paramInt >= k))
/* 2278 */       throw MethodHandleStatics.newIllegalArgumentException("no argument type to remove");
/* 2279 */     ArrayList localArrayList = new ArrayList(localMethodType1.parameterList());
/* 2280 */     localArrayList.addAll(paramInt, paramList);
/* 2281 */     if (localArrayList.size() != k) throw MethodHandleStatics.newIllegalArgumentException("valueTypes");
/* 2282 */     MethodType localMethodType2 = MethodType.methodType(localMethodType1.returnType(), localArrayList);
/* 2283 */     return paramMethodHandle.dropArguments(localMethodType2, paramInt, i);
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
/*      */   public static MethodHandle dropArguments(MethodHandle paramMethodHandle, int paramInt, Class<?>... paramVarArgs)
/*      */   {
/* 2335 */     return dropArguments(paramMethodHandle, paramInt, Arrays.asList(paramVarArgs));
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
/*      */   public static MethodHandle filterArguments(MethodHandle paramMethodHandle, int paramInt, MethodHandle... paramVarArgs)
/*      */   {
/* 2405 */     MethodType localMethodType1 = paramMethodHandle.type();
/* 2406 */     MethodHandle localMethodHandle1 = paramMethodHandle;
/* 2407 */     MethodType localMethodType2 = null;
/* 2408 */     assert ((localMethodType2 = localMethodType1) != null);
/* 2409 */     int i = localMethodType1.parameterCount();
/* 2410 */     if (paramInt + paramVarArgs.length > i)
/* 2411 */       throw MethodHandleStatics.newIllegalArgumentException("too many filters");
/* 2412 */     int j = paramInt - 1;
/* 2413 */     for (MethodHandle localMethodHandle2 : paramVarArgs) {
/* 2414 */       j++;
/* 2415 */       if (localMethodHandle2 != null) {
/* 2416 */         localMethodHandle1 = filterArgument(localMethodHandle1, j, localMethodHandle2);
/* 2417 */         assert ((localMethodType2 = localMethodType2.changeParameterType(j, localMethodHandle2.type().parameterType(0))) != null);
/*      */       } }
/* 2419 */     assert (localMethodType2.equals(localMethodHandle1.type()));
/* 2420 */     return localMethodHandle1;
/*      */   }
/*      */   
/*      */   static MethodHandle filterArgument(MethodHandle paramMethodHandle1, int paramInt, MethodHandle paramMethodHandle2)
/*      */   {
/* 2425 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 2426 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 2427 */     if ((localMethodType2.parameterCount() != 1) || 
/* 2428 */       (localMethodType2.returnType() != localMethodType1.parameterType(paramInt)))
/* 2429 */       throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", localMethodType1, localMethodType2);
/* 2430 */     return MethodHandleImpl.makeCollectArguments(paramMethodHandle1, paramMethodHandle2, paramInt, false);
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
/*      */   public static MethodHandle collectArguments(MethodHandle paramMethodHandle1, int paramInt, MethodHandle paramMethodHandle2)
/*      */   {
/* 2541 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 2542 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 2543 */     if ((localMethodType2.returnType() != Void.TYPE) && 
/* 2544 */       (localMethodType2.returnType() != localMethodType1.parameterType(paramInt)))
/* 2545 */       throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", localMethodType1, localMethodType2);
/* 2546 */     return MethodHandleImpl.makeCollectArguments(paramMethodHandle1, paramMethodHandle2, paramInt, false);
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
/*      */   public static MethodHandle filterReturnValue(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2)
/*      */   {
/* 2608 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 2609 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 2610 */     Class localClass = localMethodType1.returnType();
/* 2611 */     int i = localMethodType2.parameterCount();
/* 2612 */     if (i == 0 ? localClass != Void.TYPE : localClass != localMethodType2
/*      */     
/* 2614 */       .parameterType(0)) {
/* 2615 */       throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", paramMethodHandle1, paramMethodHandle2);
/*      */     }
/*      */     
/* 2618 */     return MethodHandleImpl.makeCollectArguments(paramMethodHandle2, paramMethodHandle1, 0, false);
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
/*      */   public static MethodHandle foldArguments(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2)
/*      */   {
/* 2699 */     int i = 0;
/* 2700 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 2701 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 2702 */     int j = i;
/* 2703 */     int k = localMethodType2.parameterCount();
/* 2704 */     int m = localMethodType2.returnType() == Void.TYPE ? 0 : 1;
/* 2705 */     int n = j + m;
/* 2706 */     int i1 = localMethodType1.parameterCount() >= n + k ? 1 : 0;
/* 2707 */     if ((i1 != 0) && 
/* 2708 */       (!localMethodType2.parameterList().equals(localMethodType1.parameterList().subList(n, n + k))))
/*      */     {
/* 2710 */       i1 = 0; }
/* 2711 */     if ((i1 != 0) && (m != 0) && (!localMethodType2.returnType().equals(localMethodType1.parameterType(0))))
/* 2712 */       i1 = 0;
/* 2713 */     if (i1 == 0)
/* 2714 */       throw misMatchedTypes("target and combiner types", localMethodType1, localMethodType2);
/* 2715 */     MethodType localMethodType3 = localMethodType1.dropParameterTypes(j, n);
/* 2716 */     return MethodHandleImpl.makeCollectArguments(paramMethodHandle1, paramMethodHandle2, j, true);
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
/*      */   public static MethodHandle guardWithTest(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, MethodHandle paramMethodHandle3)
/*      */   {
/* 2755 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 2756 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 2757 */     MethodType localMethodType3 = paramMethodHandle3.type();
/* 2758 */     if (!localMethodType2.equals(localMethodType3))
/* 2759 */       throw misMatchedTypes("target and fallback types", localMethodType2, localMethodType3);
/* 2760 */     if (localMethodType1.returnType() != Boolean.TYPE)
/* 2761 */       throw MethodHandleStatics.newIllegalArgumentException("guard type is not a predicate " + localMethodType1);
/* 2762 */     List localList1 = localMethodType2.parameterList();
/* 2763 */     List localList2 = localMethodType1.parameterList();
/* 2764 */     if (!localList1.equals(localList2)) {
/* 2765 */       int i = localList2.size();int j = localList1.size();
/* 2766 */       if ((i >= j) || (!localList1.subList(0, i).equals(localList2)))
/* 2767 */         throw misMatchedTypes("target and test types", localMethodType2, localMethodType1);
/* 2768 */       paramMethodHandle1 = dropArguments(paramMethodHandle1, i, localList1.subList(i, j));
/* 2769 */       localMethodType1 = paramMethodHandle1.type();
/*      */     }
/* 2771 */     return MethodHandleImpl.makeGuardWithTest(paramMethodHandle1, paramMethodHandle2, paramMethodHandle3);
/*      */   }
/*      */   
/*      */   static RuntimeException misMatchedTypes(String paramString, MethodType paramMethodType1, MethodType paramMethodType2) {
/* 2775 */     return MethodHandleStatics.newIllegalArgumentException(paramString + " must match: " + paramMethodType1 + " != " + paramMethodType2);
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
/*      */   public static MethodHandle catchException(MethodHandle paramMethodHandle1, Class<? extends Throwable> paramClass, MethodHandle paramMethodHandle2)
/*      */   {
/* 2825 */     MethodType localMethodType1 = paramMethodHandle1.type();
/* 2826 */     MethodType localMethodType2 = paramMethodHandle2.type();
/* 2827 */     if ((localMethodType2.parameterCount() < 1) || 
/* 2828 */       (!localMethodType2.parameterType(0).isAssignableFrom(paramClass)))
/* 2829 */       throw MethodHandleStatics.newIllegalArgumentException("handler does not accept exception type " + paramClass);
/* 2830 */     if (localMethodType2.returnType() != localMethodType1.returnType())
/* 2831 */       throw misMatchedTypes("target and handler return types", localMethodType1, localMethodType2);
/* 2832 */     List localList1 = localMethodType1.parameterList();
/* 2833 */     List localList2 = localMethodType2.parameterList();
/* 2834 */     localList2 = localList2.subList(1, localList2.size());
/* 2835 */     if (!localList1.equals(localList2)) {
/* 2836 */       int i = localList2.size();int j = localList1.size();
/* 2837 */       if ((i >= j) || (!localList1.subList(0, i).equals(localList2)))
/* 2838 */         throw misMatchedTypes("target and handler types", localMethodType1, localMethodType2);
/* 2839 */       paramMethodHandle2 = dropArguments(paramMethodHandle2, 1 + i, localList1.subList(i, j));
/* 2840 */       localMethodType2 = paramMethodHandle2.type();
/*      */     }
/* 2842 */     return MethodHandleImpl.makeGuardWithCatch(paramMethodHandle1, paramClass, paramMethodHandle2);
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
/*      */   public static MethodHandle throwException(Class<?> paramClass, Class<? extends Throwable> paramClass1)
/*      */   {
/* 2859 */     if (!Throwable.class.isAssignableFrom(paramClass1))
/* 2860 */       throw new ClassCastException(paramClass1.getName());
/* 2861 */     return MethodHandleImpl.throwException(MethodType.methodType(paramClass, paramClass1));
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */