/*     */ package java.security;
/*     */ 
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AccessController
/*     */ {
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction);
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> paramPrivilegedAction)
/*     */   {
/* 327 */     AccessControlContext localAccessControlContext = getStackAccessControlContext();
/* 328 */     if (localAccessControlContext == null) {
/* 329 */       return (T)doPrivileged(paramPrivilegedAction);
/*     */     }
/* 331 */     DomainCombiner localDomainCombiner = localAccessControlContext.getAssignedCombiner();
/* 332 */     return (T)doPrivileged(paramPrivilegedAction, 
/* 333 */       preserveCombiner(localDomainCombiner, Reflection.getCallerClass()));
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs)
/*     */   {
/* 423 */     AccessControlContext localAccessControlContext = getContext();
/* 424 */     if (paramVarArgs == null) {
/* 425 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 427 */     Class localClass = Reflection.getCallerClass();
/* 428 */     return (T)doPrivileged(paramPrivilegedAction, createWrapper(null, localClass, localAccessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs)
/*     */   {
/* 485 */     AccessControlContext localAccessControlContext = getContext();
/* 486 */     DomainCombiner localDomainCombiner = localAccessControlContext.getCombiner();
/* 487 */     if ((localDomainCombiner == null) && (paramAccessControlContext != null)) {
/* 488 */       localDomainCombiner = paramAccessControlContext.getCombiner();
/*     */     }
/* 490 */     if (paramVarArgs == null) {
/* 491 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 493 */     Class localClass = Reflection.getCallerClass();
/* 494 */     return (T)doPrivileged(paramPrivilegedAction, createWrapper(localDomainCombiner, localClass, localAccessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction)
/*     */     throws PrivilegedActionException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction)
/*     */     throws PrivilegedActionException
/*     */   {
/* 563 */     AccessControlContext localAccessControlContext = getStackAccessControlContext();
/* 564 */     if (localAccessControlContext == null) {
/* 565 */       return (T)doPrivileged(paramPrivilegedExceptionAction);
/*     */     }
/* 567 */     DomainCombiner localDomainCombiner = localAccessControlContext.getAssignedCombiner();
/* 568 */     return (T)doPrivileged(paramPrivilegedExceptionAction, 
/* 569 */       preserveCombiner(localDomainCombiner, Reflection.getCallerClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static AccessControlContext preserveCombiner(DomainCombiner paramDomainCombiner, Class<?> paramClass)
/*     */   {
/* 578 */     return createWrapper(paramDomainCombiner, paramClass, null, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static AccessControlContext createWrapper(DomainCombiner paramDomainCombiner, Class<?> paramClass, AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission)
/*     */   {
/* 589 */     ProtectionDomain localProtectionDomain1 = getCallerPD(paramClass);
/*     */     
/* 591 */     if ((paramAccessControlContext2 != null) && (!paramAccessControlContext2.isAuthorized()) && 
/* 592 */       (System.getSecurityManager() != null) && 
/* 593 */       (!localProtectionDomain1.impliesCreateAccessControlContext()))
/*     */     {
/* 595 */       ProtectionDomain localProtectionDomain2 = new ProtectionDomain(null, null);
/* 596 */       return new AccessControlContext(new ProtectionDomain[] { localProtectionDomain2 });
/*     */     }
/* 598 */     return new AccessControlContext(localProtectionDomain1, paramDomainCombiner, paramAccessControlContext1, paramAccessControlContext2, paramArrayOfPermission);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static ProtectionDomain getCallerPD(Class<?> paramClass)
/*     */   {
/* 605 */     ProtectionDomain localProtectionDomain = (ProtectionDomain)doPrivileged(new PrivilegedAction() {
/*     */       public ProtectionDomain run() {
/* 607 */         return this.val$caller.getProtectionDomain();
/*     */       }
/*     */       
/* 610 */     });
/* 611 */     return localProtectionDomain;
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext)
/*     */     throws PrivilegedActionException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs)
/*     */     throws PrivilegedActionException
/*     */   {
/* 708 */     AccessControlContext localAccessControlContext = getContext();
/* 709 */     if (paramVarArgs == null) {
/* 710 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 712 */     Class localClass = Reflection.getCallerClass();
/* 713 */     return (T)doPrivileged(paramPrivilegedExceptionAction, createWrapper(null, localClass, localAccessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs)
/*     */     throws PrivilegedActionException
/*     */   {
/* 773 */     AccessControlContext localAccessControlContext = getContext();
/* 774 */     DomainCombiner localDomainCombiner = localAccessControlContext.getCombiner();
/* 775 */     if ((localDomainCombiner == null) && (paramAccessControlContext != null)) {
/* 776 */       localDomainCombiner = paramAccessControlContext.getCombiner();
/*     */     }
/* 778 */     if (paramVarArgs == null) {
/* 779 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 781 */     Class localClass = Reflection.getCallerClass();
/* 782 */     return (T)doPrivileged(paramPrivilegedExceptionAction, createWrapper(localDomainCombiner, localClass, localAccessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   private static native AccessControlContext getStackAccessControlContext();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static native AccessControlContext getInheritedAccessControlContext();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AccessControlContext getContext()
/*     */   {
/* 820 */     AccessControlContext localAccessControlContext = getStackAccessControlContext();
/* 821 */     if (localAccessControlContext == null)
/*     */     {
/*     */ 
/* 824 */       return new AccessControlContext(null, true);
/*     */     }
/* 826 */     return localAccessControlContext.optimize();
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
/*     */   public static void checkPermission(Permission paramPermission)
/*     */     throws AccessControlException
/*     */   {
/* 854 */     if (paramPermission == null) {
/* 855 */       throw new NullPointerException("permission can't be null");
/*     */     }
/*     */     
/* 858 */     AccessControlContext localAccessControlContext = getStackAccessControlContext();
/*     */     
/* 860 */     if (localAccessControlContext == null) {
/* 861 */       localObject = AccessControlContext.getDebug();
/* 862 */       int i = 0;
/* 863 */       if (localObject != null) {
/* 864 */         i = !Debug.isOn("codebase=") ? 1 : 0;
/*     */         
/* 866 */         i = i & ((!Debug.isOn("permission=")) || (Debug.isOn("permission=" + paramPermission.getClass().getCanonicalName())) ? 1 : 0);
/*     */       }
/*     */       
/* 869 */       if ((i != 0) && (Debug.isOn("stack"))) {
/* 870 */         Thread.dumpStack();
/*     */       }
/*     */       
/* 873 */       if ((i != 0) && (Debug.isOn("domain"))) {
/* 874 */         ((Debug)localObject).println("domain (context is null)");
/*     */       }
/*     */       
/* 877 */       if (i != 0) {
/* 878 */         ((Debug)localObject).println("access allowed " + paramPermission);
/*     */       }
/* 880 */       return;
/*     */     }
/*     */     
/* 883 */     Object localObject = localAccessControlContext.optimize();
/* 884 */     ((AccessControlContext)localObject).checkPermission(paramPermission);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/AccessController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */