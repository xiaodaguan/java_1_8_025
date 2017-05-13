/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import sun.invoke.WrapperInstance;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodHandleProxies
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <T> T asInterfaceInstance(final Class<T> paramClass, MethodHandle paramMethodHandle)
/*     */   {
/* 150 */     if ((!paramClass.isInterface()) || (!Modifier.isPublic(paramClass.getModifiers())))
/* 151 */       throw new IllegalArgumentException("not a public interface: " + paramClass.getName());
/*     */     MethodHandle localMethodHandle1;
/* 153 */     if (System.getSecurityManager() != null) {
/* 154 */       localObject1 = Reflection.getCallerClass();
/* 155 */       localObject2 = localObject1 != null ? ((Class)localObject1).getClassLoader() : null;
/* 156 */       ReflectUtil.checkProxyPackageAccess((ClassLoader)localObject2, new Class[] { paramClass });
/* 157 */       localMethodHandle1 = localObject2 != null ? bindCaller(paramMethodHandle, (Class)localObject1) : paramMethodHandle;
/*     */     } else {
/* 159 */       localMethodHandle1 = paramMethodHandle;
/*     */     }
/* 161 */     Object localObject1 = paramClass.getClassLoader();
/* 162 */     if (localObject1 == null) {
/* 163 */       localObject2 = Thread.currentThread().getContextClassLoader();
/* 164 */       localObject1 = localObject2 != null ? localObject2 : ClassLoader.getSystemClassLoader();
/*     */     }
/* 166 */     final Object localObject2 = getSingleNameMethods(paramClass);
/* 167 */     if (localObject2 == null)
/* 168 */       throw new IllegalArgumentException("not a single-method interface: " + paramClass.getName());
/* 169 */     final MethodHandle[] arrayOfMethodHandle = new MethodHandle[localObject2.length];
/* 170 */     Object localObject3; Object localObject4; for (int i = 0; i < localObject2.length; i++) {
/* 171 */       localObject3 = localObject2[i];
/* 172 */       localObject4 = MethodType.methodType(((Method)localObject3).getReturnType(), ((Method)localObject3).getParameterTypes());
/* 173 */       MethodHandle localMethodHandle2 = localMethodHandle1.asType((MethodType)localObject4);
/* 174 */       localMethodHandle2 = localMethodHandle2.asType(localMethodHandle2.type().changeReturnType(Object.class));
/* 175 */       arrayOfMethodHandle[i] = localMethodHandle2.asSpreader(Object[].class, ((MethodType)localObject4).parameterCount());
/*     */     }
/* 177 */     final InvocationHandler local1 = new InvocationHandler() {
/*     */       private Object getArg(String paramAnonymousString) {
/* 179 */         if (paramAnonymousString == "getWrapperInstanceTarget") return this.val$target;
/* 180 */         if (paramAnonymousString == "getWrapperInstanceType") return paramClass;
/* 181 */         throw new AssertionError();
/*     */       }
/*     */       
/* 184 */       public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject) throws Throwable { for (int i = 0; i < localObject2.length; i++) {
/* 185 */           if (paramAnonymousMethod.equals(localObject2[i]))
/* 186 */             return arrayOfMethodHandle[i].invokeExact(paramAnonymousArrayOfObject);
/*     */         }
/* 188 */         if (paramAnonymousMethod.getDeclaringClass() == WrapperInstance.class)
/* 189 */           return getArg(paramAnonymousMethod.getName());
/* 190 */         if (MethodHandleProxies.isObjectMethod(paramAnonymousMethod))
/* 191 */           return MethodHandleProxies.callObjectMethod(paramAnonymousObject, paramAnonymousMethod, paramAnonymousArrayOfObject);
/* 192 */         throw new InternalError("bad proxy method: " + paramAnonymousMethod);
/*     */       }
/*     */     };
/*     */     
/*     */ 
/* 197 */     if (System.getSecurityManager() != null)
/*     */     {
/*     */ 
/* 200 */       localObject4 = localObject1;
/* 201 */       localObject3 = AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Object run() {
/* 203 */           return Proxy.newProxyInstance(this.val$loader, new Class[] { paramClass, WrapperInstance.class }, local1);
/*     */         }
/*     */         
/*     */       });
/*     */     }
/*     */     else
/*     */     {
/* 210 */       localObject3 = Proxy.newProxyInstance((ClassLoader)localObject1, new Class[] { paramClass, WrapperInstance.class }, local1);
/*     */     }
/*     */     
/*     */ 
/* 214 */     return (T)paramClass.cast(localObject3);
/*     */   }
/*     */   
/*     */   private static MethodHandle bindCaller(MethodHandle paramMethodHandle, Class<?> paramClass) {
/* 218 */     MethodHandle localMethodHandle = MethodHandleImpl.bindCaller(paramMethodHandle, paramClass);
/* 219 */     if (paramMethodHandle.isVarargsCollector()) {
/* 220 */       MethodType localMethodType = localMethodHandle.type();
/* 221 */       int i = localMethodType.parameterCount();
/* 222 */       return localMethodHandle.asVarargsCollector(localMethodType.parameterType(i - 1));
/*     */     }
/* 224 */     return localMethodHandle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isWrapperInstance(Object paramObject)
/*     */   {
/* 234 */     return paramObject instanceof WrapperInstance;
/*     */   }
/*     */   
/*     */   private static WrapperInstance asWrapperInstance(Object paramObject) {
/*     */     try {
/* 239 */       if (paramObject != null) {
/* 240 */         return (WrapperInstance)paramObject;
/*     */       }
/*     */     } catch (ClassCastException localClassCastException) {}
/* 243 */     throw new IllegalArgumentException("not a wrapper instance");
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
/*     */   public static MethodHandle wrapperInstanceTarget(Object paramObject)
/*     */   {
/* 257 */     return asWrapperInstance(paramObject).getWrapperInstanceTarget();
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
/*     */   public static Class<?> wrapperInstanceType(Object paramObject)
/*     */   {
/* 270 */     return asWrapperInstance(paramObject).getWrapperInstanceType();
/*     */   }
/*     */   
/*     */   private static boolean isObjectMethod(Method paramMethod)
/*     */   {
/* 275 */     switch (paramMethod.getName())
/*     */     {
/*     */     case "toString": 
/* 278 */       return (paramMethod.getReturnType() == String.class) && (paramMethod.getParameterTypes().length == 0);
/*     */     
/*     */     case "hashCode": 
/* 281 */       return (paramMethod.getReturnType() == Integer.TYPE) && (paramMethod.getParameterTypes().length == 0);
/*     */     
/*     */ 
/*     */     case "equals": 
/* 285 */       return (paramMethod.getReturnType() == Boolean.TYPE) && (paramMethod.getParameterTypes().length == 1) && (paramMethod.getParameterTypes()[0] == Object.class);
/*     */     }
/* 287 */     return false;
/*     */   }
/*     */   
/*     */   private static Object callObjectMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
/*     */   {
/* 292 */     assert (isObjectMethod(paramMethod)) : paramMethod;
/* 293 */     switch (paramMethod.getName()) {
/*     */     case "toString": 
/* 295 */       return paramObject.getClass().getName() + "@" + Integer.toHexString(paramObject.hashCode());
/*     */     case "hashCode": 
/* 297 */       return Integer.valueOf(System.identityHashCode(paramObject));
/*     */     case "equals": 
/* 299 */       return Boolean.valueOf(paramObject == paramArrayOfObject[0]);
/*     */     }
/* 301 */     return null;
/*     */   }
/*     */   
/*     */   private static Method[] getSingleNameMethods(Class<?> paramClass)
/*     */   {
/* 306 */     ArrayList localArrayList = new ArrayList();
/* 307 */     Object localObject = null;
/* 308 */     for (Method localMethod : paramClass.getMethods())
/* 309 */       if ((!isObjectMethod(localMethod)) && 
/* 310 */         (Modifier.isAbstract(localMethod.getModifiers()))) {
/* 311 */         String str = localMethod.getName();
/* 312 */         if (localObject == null) {
/* 313 */           localObject = str;
/* 314 */         } else if (!((String)localObject).equals(str))
/* 315 */           return null;
/* 316 */         localArrayList.add(localMethod);
/*     */       }
/* 318 */     if (localObject == null) return null;
/* 319 */     return (Method[])localArrayList.toArray(new Method[localArrayList.size()]);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodHandleProxies.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */