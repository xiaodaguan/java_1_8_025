/*     */ package java.security;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.jca.GetInstance.Instance;
/*     */ import sun.security.provider.PolicyFile;
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
/*     */ public abstract class Policy
/*     */ {
/*  92 */   public static final PermissionCollection UNSUPPORTED_EMPTY_COLLECTION = new UnsupportedEmptyCollection();
/*     */   
/*     */ 
/*     */   private static class PolicyInfo
/*     */   {
/*     */     final Policy policy;
/*     */     
/*     */     final boolean initialized;
/*     */     
/*     */     PolicyInfo(Policy paramPolicy, boolean paramBoolean)
/*     */     {
/* 103 */       this.policy = paramPolicy;
/* 104 */       this.initialized = paramBoolean;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 109 */   private static AtomicReference<PolicyInfo> policy = new AtomicReference(new PolicyInfo(null, false));
/*     */   
/*     */ 
/* 112 */   private static final Debug debug = Debug.getInstance("policy");
/*     */   
/*     */ 
/*     */   private WeakHashMap<ProtectionDomain.Key, PermissionCollection> pdMapping;
/*     */   
/*     */ 
/*     */   static boolean isSet()
/*     */   {
/* 120 */     PolicyInfo localPolicyInfo = (PolicyInfo)policy.get();
/* 121 */     return (localPolicyInfo.policy != null) && (localPolicyInfo.initialized == true);
/*     */   }
/*     */   
/*     */   private static void checkPermission(String paramString) {
/* 125 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 126 */     if (localSecurityManager != null) {
/* 127 */       localSecurityManager.checkPermission(new SecurityPermission("createPolicy." + paramString));
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
/*     */   public static Policy getPolicy()
/*     */   {
/* 151 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 152 */     if (localSecurityManager != null)
/* 153 */       localSecurityManager.checkPermission(SecurityConstants.GET_POLICY_PERMISSION);
/* 154 */     return getPolicyNoCheck();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Policy getPolicyNoCheck()
/*     */   {
/* 165 */     PolicyInfo localPolicyInfo1 = (PolicyInfo)policy.get();
/*     */     
/*     */ 
/* 168 */     if ((!localPolicyInfo1.initialized) || (localPolicyInfo1.policy == null)) {
/* 169 */       synchronized (Policy.class) {
/* 170 */         PolicyInfo localPolicyInfo2 = (PolicyInfo)policy.get();
/* 171 */         if (localPolicyInfo2.policy == null) {
/* 172 */           String str1 = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public String run() {
/* 175 */               return Security.getProperty("policy.provider");
/*     */             }
/*     */           });
/* 178 */           if (str1 == null) {
/* 179 */             str1 = "sun.security.provider.PolicyFile";
/*     */           }
/*     */           
/*     */           try
/*     */           {
/* 184 */             localPolicyInfo2 = new PolicyInfo((Policy)Class.forName(str1).newInstance(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           catch (Exception localException)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 196 */             PolicyFile localPolicyFile = new PolicyFile();
/* 197 */             localPolicyInfo2 = new PolicyInfo(localPolicyFile, false);
/* 198 */             policy.set(localPolicyInfo2);
/*     */             
/* 200 */             String str2 = str1;
/* 201 */             Policy localPolicy = (Policy)AccessController.doPrivileged(new PrivilegedAction()
/*     */             {
/*     */               public Policy run()
/*     */               {
/*     */                 try {
/* 206 */                   ClassLoader localClassLoader1 = ClassLoader.getSystemClassLoader();
/*     */                   
/* 208 */                   ClassLoader localClassLoader2 = null;
/* 209 */                   while (localClassLoader1 != null) {
/* 210 */                     localClassLoader2 = localClassLoader1;
/* 211 */                     localClassLoader1 = localClassLoader1.getParent();
/*     */                   }
/*     */                   
/* 214 */                   return localClassLoader2 != null ? (Policy)Class.forName(this.val$pc, true, localClassLoader2).newInstance() : null;
/*     */                 } catch (Exception localException) {
/* 216 */                   if (Policy.debug != null) {
/* 217 */                     Policy.debug.println("policy provider " + this.val$pc + " not available");
/*     */                     
/*     */ 
/* 220 */                     localException.printStackTrace();
/*     */                   } }
/* 222 */                 return null;
/*     */               }
/*     */             });
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 230 */             if (localPolicy != null) {
/* 231 */               localPolicyInfo2 = new PolicyInfo(localPolicy, true);
/*     */             } else {
/* 233 */               if (debug != null) {
/* 234 */                 debug.println("using sun.security.provider.PolicyFile");
/*     */               }
/* 236 */               localPolicyInfo2 = new PolicyInfo(localPolicyFile, true);
/*     */             }
/*     */           }
/* 239 */           policy.set(localPolicyInfo2);
/*     */         }
/* 241 */         return localPolicyInfo2.policy;
/*     */       }
/*     */     }
/* 244 */     return localPolicyInfo1.policy;
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
/*     */   public static void setPolicy(Policy paramPolicy)
/*     */   {
/* 266 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 267 */     if (localSecurityManager != null) { localSecurityManager.checkPermission(new SecurityPermission("setPolicy"));
/*     */     }
/* 269 */     if (paramPolicy != null) {
/* 270 */       initPolicy(paramPolicy);
/*     */     }
/* 272 */     synchronized (Policy.class) {
/* 273 */       policy.set(new PolicyInfo(paramPolicy, paramPolicy != null));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void initPolicy(Policy paramPolicy)
/*     */   {
/* 306 */     ProtectionDomain localProtectionDomain = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public ProtectionDomain run() {
/* 308 */         return this.val$p.getClass().getProtectionDomain();
/*     */ 
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 316 */     });
/* 317 */     Object localObject1 = null;
/* 318 */     synchronized (paramPolicy) {
/* 319 */       if (paramPolicy.pdMapping == null) {
/* 320 */         paramPolicy.pdMapping = new WeakHashMap();
/*     */       }
/*     */     }
/*     */     
/* 324 */     if (localProtectionDomain.getCodeSource() != null) {
/* 325 */       ??? = ((PolicyInfo)policy.get()).policy;
/* 326 */       if (??? != null) {
/* 327 */         localObject1 = ((Policy)???).getPermissions(localProtectionDomain);
/*     */       }
/*     */       
/* 330 */       if (localObject1 == null) {
/* 331 */         localObject1 = new Permissions();
/* 332 */         ((PermissionCollection)localObject1).add(SecurityConstants.ALL_PERMISSION);
/*     */       }
/*     */       
/* 335 */       synchronized (paramPolicy.pdMapping)
/*     */       {
/* 337 */         paramPolicy.pdMapping.put(localProtectionDomain.key, localObject1);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Policy getInstance(String paramString, Parameters paramParameters)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 384 */     checkPermission(paramString);
/*     */     try {
/* 386 */       GetInstance.Instance localInstance = GetInstance.getInstance("Policy", PolicySpi.class, paramString, paramParameters);
/*     */       
/*     */ 
/*     */ 
/* 390 */       return new PolicyDelegate((PolicySpi)localInstance.impl, localInstance.provider, paramString, paramParameters, null);
/*     */ 
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/*     */     {
/* 395 */       return handleException(localNoSuchAlgorithmException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Policy getInstance(String paramString1, Parameters paramParameters, String paramString2)
/*     */     throws NoSuchProviderException, NoSuchAlgorithmException
/*     */   {
/* 446 */     if ((paramString2 == null) || (paramString2.length() == 0)) {
/* 447 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*     */     
/* 450 */     checkPermission(paramString1);
/*     */     try {
/* 452 */       GetInstance.Instance localInstance = GetInstance.getInstance("Policy", PolicySpi.class, paramString1, paramParameters, paramString2);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 457 */       return new PolicyDelegate((PolicySpi)localInstance.impl, localInstance.provider, paramString1, paramParameters, null);
/*     */ 
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/*     */     {
/* 462 */       return handleException(localNoSuchAlgorithmException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Policy getInstance(String paramString, Parameters paramParameters, Provider paramProvider)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 506 */     if (paramProvider == null) {
/* 507 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*     */     
/* 510 */     checkPermission(paramString);
/*     */     try {
/* 512 */       GetInstance.Instance localInstance = GetInstance.getInstance("Policy", PolicySpi.class, paramString, paramParameters, paramProvider);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 517 */       return new PolicyDelegate((PolicySpi)localInstance.impl, localInstance.provider, paramString, paramParameters, null);
/*     */ 
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/*     */     {
/* 522 */       return handleException(localNoSuchAlgorithmException);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Policy handleException(NoSuchAlgorithmException paramNoSuchAlgorithmException) throws NoSuchAlgorithmException
/*     */   {
/* 528 */     Throwable localThrowable = paramNoSuchAlgorithmException.getCause();
/* 529 */     if ((localThrowable instanceof IllegalArgumentException)) {
/* 530 */       throw ((IllegalArgumentException)localThrowable);
/*     */     }
/* 532 */     throw paramNoSuchAlgorithmException;
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
/*     */   public Provider getProvider()
/*     */   {
/* 547 */     return null;
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
/*     */   public String getType()
/*     */   {
/* 562 */     return null;
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
/*     */   public Parameters getParameters()
/*     */   {
/* 577 */     return null;
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
/*     */   public PermissionCollection getPermissions(CodeSource paramCodeSource)
/*     */   {
/* 607 */     return UNSUPPORTED_EMPTY_COLLECTION;
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
/*     */   public PermissionCollection getPermissions(ProtectionDomain paramProtectionDomain)
/*     */   {
/* 645 */     Object localObject1 = null;
/*     */     
/* 647 */     if (paramProtectionDomain == null) {
/* 648 */       return new Permissions();
/*     */     }
/* 650 */     if (this.pdMapping == null) {
/* 651 */       initPolicy(this);
/*     */     }
/*     */     
/* 654 */     synchronized (this.pdMapping) {
/* 655 */       localObject1 = (PermissionCollection)this.pdMapping.get(paramProtectionDomain.key);
/*     */     }
/*     */     
/* 658 */     if (localObject1 != null) {
/* 659 */       ??? = new Permissions();
/* 660 */       Enumeration localEnumeration; synchronized (localObject1) {
/* 661 */         for (localEnumeration = ((PermissionCollection)localObject1).elements(); localEnumeration.hasMoreElements();) {
/* 662 */           ((Permissions)???).add((Permission)localEnumeration.nextElement());
/*     */         }
/*     */       }
/* 665 */       return (PermissionCollection)???;
/*     */     }
/*     */     
/* 668 */     localObject1 = getPermissions(paramProtectionDomain.getCodeSource());
/* 669 */     if ((localObject1 == null) || (localObject1 == UNSUPPORTED_EMPTY_COLLECTION)) {
/* 670 */       localObject1 = new Permissions();
/*     */     }
/*     */     
/* 673 */     addStaticPerms((PermissionCollection)localObject1, paramProtectionDomain.getPermissions());
/* 674 */     return (PermissionCollection)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addStaticPerms(PermissionCollection paramPermissionCollection1, PermissionCollection paramPermissionCollection2)
/*     */   {
/* 682 */     if (paramPermissionCollection2 != null) {
/* 683 */       synchronized (paramPermissionCollection2) {
/* 684 */         Enumeration localEnumeration = paramPermissionCollection2.elements();
/* 685 */         while (localEnumeration.hasMoreElements()) {
/* 686 */           paramPermissionCollection1.add((Permission)localEnumeration.nextElement());
/*     */         }
/*     */       }
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
/*     */   public boolean implies(ProtectionDomain paramProtectionDomain, Permission paramPermission)
/*     */   {
/* 709 */     if (this.pdMapping == null) {
/* 710 */       initPolicy(this);
/*     */     }
/*     */     
/* 713 */     synchronized (this.pdMapping) {
/* 714 */       localPermissionCollection = (PermissionCollection)this.pdMapping.get(paramProtectionDomain.key);
/*     */     }
/*     */     
/* 717 */     if (localPermissionCollection != null) {
/* 718 */       return localPermissionCollection.implies(paramPermission);
/*     */     }
/*     */     
/* 721 */     PermissionCollection localPermissionCollection = getPermissions(paramProtectionDomain);
/* 722 */     if (localPermissionCollection == null) {
/* 723 */       return false;
/*     */     }
/*     */     
/* 726 */     synchronized (this.pdMapping)
/*     */     {
/* 728 */       this.pdMapping.put(paramProtectionDomain.key, localPermissionCollection);
/*     */     }
/*     */     
/* 731 */     return localPermissionCollection.implies(paramPermission);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void refresh() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private static class PolicyDelegate
/*     */     extends Policy
/*     */   {
/*     */     private PolicySpi spi;
/*     */     
/*     */ 
/*     */     private Provider p;
/*     */     
/*     */ 
/*     */     private String type;
/*     */     
/*     */ 
/*     */     private Policy.Parameters params;
/*     */     
/*     */ 
/*     */ 
/*     */     private PolicyDelegate(PolicySpi paramPolicySpi, Provider paramProvider, String paramString, Policy.Parameters paramParameters)
/*     */     {
/* 758 */       this.spi = paramPolicySpi;
/* 759 */       this.p = paramProvider;
/* 760 */       this.type = paramString;
/* 761 */       this.params = paramParameters;
/*     */     }
/*     */     
/* 764 */     public String getType() { return this.type; }
/*     */     
/* 766 */     public Policy.Parameters getParameters() { return this.params; }
/*     */     
/* 768 */     public Provider getProvider() { return this.p; }
/*     */     
/*     */     public PermissionCollection getPermissions(CodeSource paramCodeSource)
/*     */     {
/* 772 */       return this.spi.engineGetPermissions(paramCodeSource);
/*     */     }
/*     */     
/*     */     public PermissionCollection getPermissions(ProtectionDomain paramProtectionDomain) {
/* 776 */       return this.spi.engineGetPermissions(paramProtectionDomain);
/*     */     }
/*     */     
/*     */     public boolean implies(ProtectionDomain paramProtectionDomain, Permission paramPermission) {
/* 780 */       return this.spi.engineImplies(paramProtectionDomain, paramPermission);
/*     */     }
/*     */     
/*     */     public void refresh() {
/* 784 */       this.spi.engineRefresh();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract interface Parameters {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class UnsupportedEmptyCollection
/*     */     extends PermissionCollection
/*     */   {
/*     */     private static final long serialVersionUID = -8492269157353014774L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private Permissions perms;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public UnsupportedEmptyCollection()
/*     */     {
/* 813 */       this.perms = new Permissions();
/* 814 */       this.perms.setReadOnly();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void add(Permission paramPermission)
/*     */     {
/* 827 */       this.perms.add(paramPermission);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean implies(Permission paramPermission)
/*     */     {
/* 840 */       return this.perms.implies(paramPermission);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Enumeration<Permission> elements()
/*     */     {
/* 850 */       return this.perms.elements();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Policy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */