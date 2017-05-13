/*     */ package java.security;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.misc.JavaSecurityAccess;
/*     */ import sun.misc.JavaSecurityProtectionDomainAccess;
/*     */ import sun.misc.JavaSecurityProtectionDomainAccess.ProtectionDomainCache;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtectionDomain
/*     */ {
/*     */   private CodeSource codesource;
/*     */   private ClassLoader classloader;
/*     */   private Principal[] principals;
/*     */   private PermissionCollection permissions;
/* 106 */   private boolean hasAllPerm = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean staticPermissions;
/*     */   
/*     */ 
/*     */ 
/* 115 */   final Key key = new Key();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final Debug debug;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProtectionDomain(CodeSource paramCodeSource, PermissionCollection paramPermissionCollection)
/*     */   {
/* 131 */     this.codesource = paramCodeSource;
/* 132 */     if (paramPermissionCollection != null) {
/* 133 */       this.permissions = paramPermissionCollection;
/* 134 */       this.permissions.setReadOnly();
/* 135 */       if (((paramPermissionCollection instanceof Permissions)) && (((Permissions)paramPermissionCollection).allPermission != null))
/*     */       {
/* 137 */         this.hasAllPerm = true;
/*     */       }
/*     */     }
/* 140 */     this.classloader = null;
/* 141 */     this.principals = new Principal[0];
/* 142 */     this.staticPermissions = true;
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
/*     */   public ProtectionDomain(CodeSource paramCodeSource, PermissionCollection paramPermissionCollection, ClassLoader paramClassLoader, Principal[] paramArrayOfPrincipal)
/*     */   {
/* 178 */     this.codesource = paramCodeSource;
/* 179 */     if (paramPermissionCollection != null) {
/* 180 */       this.permissions = paramPermissionCollection;
/* 181 */       this.permissions.setReadOnly();
/* 182 */       if (((paramPermissionCollection instanceof Permissions)) && (((Permissions)paramPermissionCollection).allPermission != null))
/*     */       {
/* 184 */         this.hasAllPerm = true;
/*     */       }
/*     */     }
/* 187 */     this.classloader = paramClassLoader;
/* 188 */     this.principals = (paramArrayOfPrincipal != null ? (Principal[])paramArrayOfPrincipal.clone() : new Principal[0]);
/*     */     
/* 190 */     this.staticPermissions = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CodeSource getCodeSource()
/*     */   {
/* 199 */     return this.codesource;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ClassLoader getClassLoader()
/*     */   {
/* 210 */     return this.classloader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Principal[] getPrincipals()
/*     */   {
/* 222 */     return (Principal[])this.principals.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final PermissionCollection getPermissions()
/*     */   {
/* 233 */     return this.permissions;
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
/*     */   public boolean implies(Permission paramPermission)
/*     */   {
/* 265 */     if (this.hasAllPerm)
/*     */     {
/*     */ 
/* 268 */       return true;
/*     */     }
/*     */     
/* 271 */     if ((!this.staticPermissions) && 
/* 272 */       (Policy.getPolicyNoCheck().implies(this, paramPermission)))
/* 273 */       return true;
/* 274 */     if (this.permissions != null) {
/* 275 */       return this.permissions.implies(paramPermission);
/*     */     }
/* 277 */     return false;
/*     */   }
/*     */   
/*     */   boolean impliesCreateAccessControlContext()
/*     */   {
/* 282 */     return implies(SecurityConstants.CREATE_ACC_PERMISSION);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 289 */     String str = "<no principals>";
/* 290 */     if ((this.principals != null) && (this.principals.length > 0)) {
/* 291 */       localObject = new StringBuilder("(principals ");
/*     */       
/* 293 */       for (int i = 0; i < this.principals.length; i++) {
/* 294 */         ((StringBuilder)localObject).append(this.principals[i].getClass().getName() + " \"" + this.principals[i]
/* 295 */           .getName() + "\"");
/*     */         
/* 297 */         if (i < this.principals.length - 1) {
/* 298 */           ((StringBuilder)localObject).append(",\n");
/*     */         } else
/* 300 */           ((StringBuilder)localObject).append(")\n");
/*     */       }
/* 302 */       str = ((StringBuilder)localObject).toString();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 309 */     Object localObject = (Policy.isSet()) && (seeAllp()) ? mergePermissions() : getPermissions();
/*     */     
/* 311 */     return "ProtectionDomain  " + this.codesource + "\n" + " " + this.classloader + "\n" + " " + str + "\n" + " " + localObject + "\n";
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
/*     */   private static boolean seeAllp()
/*     */   {
/* 334 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*     */     
/* 336 */     if (localSecurityManager == null) {
/* 337 */       return true;
/*     */     }
/* 339 */     if (debug != null) {
/* 340 */       if ((localSecurityManager.getClass().getClassLoader() == null) && 
/* 341 */         (Policy.getPolicyNoCheck().getClass().getClassLoader() == null))
/*     */       {
/* 343 */         return true;
/*     */       }
/*     */     } else {
/*     */       try {
/* 347 */         localSecurityManager.checkPermission(SecurityConstants.GET_POLICY_PERMISSION);
/* 348 */         return true;
/*     */       }
/*     */       catch (SecurityException localSecurityException) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 355 */     return false;
/*     */   }
/*     */   
/*     */   private PermissionCollection mergePermissions() {
/* 359 */     if (this.staticPermissions) {
/* 360 */       return this.permissions;
/*     */     }
/*     */     
/*     */ 
/* 364 */     PermissionCollection localPermissionCollection = (PermissionCollection)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public PermissionCollection run() {
/* 366 */         Policy localPolicy = Policy.getPolicyNoCheck();
/* 367 */         return localPolicy.getPermissions(ProtectionDomain.this);
/*     */       }
/*     */       
/* 370 */     });
/* 371 */     Permissions localPermissions = new Permissions();
/* 372 */     int i = 32;
/* 373 */     int j = 8;
/*     */     
/* 375 */     ArrayList localArrayList1 = new ArrayList(j);
/* 376 */     ArrayList localArrayList2 = new ArrayList(i);
/*     */     
/*     */     Enumeration localEnumeration;
/*     */     
/* 380 */     if (this.permissions != null) {
/* 381 */       synchronized (this.permissions) {
/* 382 */         localEnumeration = this.permissions.elements();
/* 383 */         while (localEnumeration.hasMoreElements()) {
/* 384 */           localArrayList1.add(localEnumeration.nextElement());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 391 */     if (localPermissionCollection != null) {
/* 392 */       synchronized (localPermissionCollection) {
/* 393 */         localEnumeration = localPermissionCollection.elements();
/* 394 */         while (localEnumeration.hasMoreElements()) {
/* 395 */           localArrayList2.add(localEnumeration.nextElement());
/* 396 */           j++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 401 */     if ((localPermissionCollection != null) && (this.permissions != null))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 406 */       synchronized (this.permissions) {
/* 407 */         localEnumeration = this.permissions.elements();
/* 408 */         while (localEnumeration.hasMoreElements()) {
/* 409 */           Permission localPermission1 = (Permission)localEnumeration.nextElement();
/* 410 */           Class localClass = localPermission1.getClass();
/* 411 */           String str1 = localPermission1.getActions();
/* 412 */           String str2 = localPermission1.getName();
/* 413 */           for (int m = 0; m < localArrayList2.size(); m++) {
/* 414 */             Permission localPermission2 = (Permission)localArrayList2.get(m);
/* 415 */             if (localClass.isInstance(localPermission2))
/*     */             {
/*     */ 
/*     */ 
/* 419 */               if ((str2.equals(localPermission2.getName())) && 
/* 420 */                 (str1.equals(localPermission2.getActions()))) {
/* 421 */                 localArrayList2.remove(m);
/* 422 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     int k;
/* 430 */     if (localPermissionCollection != null)
/*     */     {
/*     */ 
/*     */ 
/* 434 */       for (k = localArrayList2.size() - 1; k >= 0; k--) {
/* 435 */         localPermissions.add((Permission)localArrayList2.get(k));
/*     */       }
/*     */     }
/* 438 */     if (this.permissions != null) {
/* 439 */       for (k = localArrayList1.size() - 1; k >= 0; k--) {
/* 440 */         localPermissions.add((Permission)localArrayList1.get(k));
/*     */       }
/*     */     }
/*     */     
/* 444 */     return localPermissions;
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*  65 */     SharedSecrets.setJavaSecurityAccess(new JavaSecurityAccess()
/*     */     {
/*     */ 
/*     */ 
/*     */       public <T> T doIntersectionPrivilege(PrivilegedAction<T> paramAnonymousPrivilegedAction, AccessControlContext paramAnonymousAccessControlContext1, AccessControlContext paramAnonymousAccessControlContext2)
/*     */       {
/*     */ 
/*  72 */         if (paramAnonymousPrivilegedAction == null) {
/*  73 */           throw new NullPointerException();
/*     */         }
/*  75 */         return (T)AccessController.doPrivileged(paramAnonymousPrivilegedAction, new AccessControlContext(paramAnonymousAccessControlContext1
/*     */         
/*     */ 
/*  78 */           .getContext(), paramAnonymousAccessControlContext2).optimize());
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       public <T> T doIntersectionPrivilege(PrivilegedAction<T> paramAnonymousPrivilegedAction, AccessControlContext paramAnonymousAccessControlContext)
/*     */       {
/*  86 */         return (T)doIntersectionPrivilege(paramAnonymousPrivilegedAction, 
/*  87 */           AccessController.getContext(), paramAnonymousAccessControlContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 116 */     });
/* 117 */     debug = Debug.getInstance("domain");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 453 */     SharedSecrets.setJavaSecurityProtectionDomainAccess(new JavaSecurityProtectionDomainAccess()
/*     */     {
/*     */       public JavaSecurityProtectionDomainAccess.ProtectionDomainCache getProtectionDomainCache() {
/* 456 */         new JavaSecurityProtectionDomainAccess.ProtectionDomainCache()
/*     */         {
/*     */ 
/* 459 */           private final Map<ProtectionDomain.Key, PermissionCollection> map = Collections.synchronizedMap(new WeakHashMap());
/*     */           
/*     */           public void put(ProtectionDomain paramAnonymous2ProtectionDomain, PermissionCollection paramAnonymous2PermissionCollection) {
/* 462 */             this.map.put(paramAnonymous2ProtectionDomain == null ? null : paramAnonymous2ProtectionDomain.key, paramAnonymous2PermissionCollection);
/*     */           }
/*     */           
/* 465 */           public PermissionCollection get(ProtectionDomain paramAnonymous2ProtectionDomain) { return paramAnonymous2ProtectionDomain == null ? (PermissionCollection)this.map.get(null) : (PermissionCollection)this.map.get(paramAnonymous2ProtectionDomain.key); }
/*     */         };
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   final class Key
/*     */   {
/*     */     Key() {}
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/ProtectionDomain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */