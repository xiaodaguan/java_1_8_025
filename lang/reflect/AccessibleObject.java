/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.ReflectionFactory;
/*     */ import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessibleObject
/*     */   implements AnnotatedElement
/*     */ {
/*  64 */   private static final Permission ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean override;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setAccessible(AccessibleObject[] paramArrayOfAccessibleObject, boolean paramBoolean)
/*     */     throws SecurityException
/*     */   {
/*  94 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  95 */     if (localSecurityManager != null) localSecurityManager.checkPermission(ACCESS_PERMISSION);
/*  96 */     for (int i = 0; i < paramArrayOfAccessibleObject.length; i++) {
/*  97 */       setAccessible0(paramArrayOfAccessibleObject[i], paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAccessible(boolean paramBoolean)
/*     */     throws SecurityException
/*     */   {
/* 127 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 128 */     if (localSecurityManager != null) localSecurityManager.checkPermission(ACCESS_PERMISSION);
/* 129 */     setAccessible0(this, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   private static void setAccessible0(AccessibleObject paramAccessibleObject, boolean paramBoolean)
/*     */     throws SecurityException
/*     */   {
/* 136 */     if (((paramAccessibleObject instanceof Constructor)) && (paramBoolean == true)) {
/* 137 */       Constructor localConstructor = (Constructor)paramAccessibleObject;
/* 138 */       if (localConstructor.getDeclaringClass() == Class.class) {
/* 139 */         throw new SecurityException("Can not make a java.lang.Class constructor accessible");
/*     */       }
/*     */     }
/*     */     
/* 143 */     paramAccessibleObject.override = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAccessible()
/*     */   {
/* 152 */     return this.override;
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
/* 172 */   static final ReflectionFactory reflectionFactory = (ReflectionFactory)AccessController.doPrivileged(new ReflectionFactory.GetReflectionFactoryAction());
/*     */   
/*     */ 
/*     */   volatile Object securityCheckCache;
/*     */   
/*     */ 
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass)
/*     */   {
/* 180 */     throw new AssertionError("All subclasses should override this method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAnnotationPresent(Class<? extends Annotation> paramClass)
/*     */   {
/* 190 */     return super.isAnnotationPresent(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass)
/*     */   {
/* 199 */     throw new AssertionError("All subclasses should override this method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getAnnotations()
/*     */   {
/* 206 */     return getDeclaredAnnotations();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T getDeclaredAnnotation(Class<T> paramClass)
/*     */   {
/* 218 */     return getAnnotation(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> paramClass)
/*     */   {
/* 230 */     return getAnnotationsByType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Annotation[] getDeclaredAnnotations()
/*     */   {
/* 237 */     throw new AssertionError("All subclasses should override this method");
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
/*     */   void checkAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt)
/*     */     throws IllegalAccessException
/*     */   {
/* 263 */     if (paramClass1 == paramClass2) {
/* 264 */       return;
/*     */     }
/* 266 */     Object localObject1 = this.securityCheckCache;
/* 267 */     Object localObject2 = paramClass2;
/* 268 */     if ((paramObject != null) && 
/* 269 */       (Modifier.isProtected(paramInt)) && 
/* 270 */       ((localObject2 = paramObject.getClass()) != paramClass2))
/*     */     {
/* 272 */       if ((localObject1 instanceof Class[])) {
/* 273 */         Class[] arrayOfClass = (Class[])localObject1;
/* 274 */         if ((arrayOfClass[1] == localObject2) && (arrayOfClass[0] == paramClass1))
/*     */         {
/* 276 */           return;
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 281 */     else if (localObject1 == paramClass1)
/*     */     {
/* 283 */       return;
/*     */     }
/*     */     
/*     */ 
/* 287 */     slowCheckMemberAccess(paramClass1, paramClass2, paramObject, paramInt, (Class)localObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void slowCheckMemberAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt, Class<?> paramClass3)
/*     */     throws IllegalAccessException
/*     */   {
/* 295 */     Reflection.ensureMemberAccess(paramClass1, paramClass2, paramObject, paramInt);
/*     */     
/*     */ 
/* 298 */     Class[] arrayOfClass = { paramClass1, paramClass3 == paramClass2 ? paramClass1 : paramClass3 };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 306 */     this.securityCheckCache = arrayOfClass;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/AccessibleObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */