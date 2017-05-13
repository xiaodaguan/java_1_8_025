/*     */ package java.security;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class AccessControlContext
/*     */ {
/*     */   private ProtectionDomain[] context;
/*     */   private boolean isPrivileged;
/*  83 */   private boolean isAuthorized = false;
/*     */   
/*     */ 
/*     */   private AccessControlContext privilegedContext;
/*     */   
/*     */ 
/*  89 */   private DomainCombiner combiner = null;
/*     */   
/*     */   private Permission[] permissions;
/*     */   
/*     */   private AccessControlContext parent;
/*     */   
/*     */   private boolean isWrapped;
/*     */   
/*     */   private boolean isLimited;
/*     */   
/*     */   private ProtectionDomain[] limitedContext;
/* 100 */   private static boolean debugInit = false;
/* 101 */   private static Debug debug = null;
/*     */   
/*     */   static Debug getDebug()
/*     */   {
/* 105 */     if (debugInit) {
/* 106 */       return debug;
/*     */     }
/* 108 */     if (Policy.isSet()) {
/* 109 */       debug = Debug.getInstance("access");
/* 110 */       debugInit = true;
/*     */     }
/* 112 */     return debug;
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
/*     */   public AccessControlContext(ProtectionDomain[] paramArrayOfProtectionDomain)
/*     */   {
/* 128 */     if (paramArrayOfProtectionDomain.length == 0) {
/* 129 */       this.context = null;
/* 130 */     } else if (paramArrayOfProtectionDomain.length == 1) {
/* 131 */       if (paramArrayOfProtectionDomain[0] != null) {
/* 132 */         this.context = ((ProtectionDomain[])paramArrayOfProtectionDomain.clone());
/*     */       } else {
/* 134 */         this.context = null;
/*     */       }
/*     */     } else {
/* 137 */       ArrayList localArrayList = new ArrayList(paramArrayOfProtectionDomain.length);
/* 138 */       for (int i = 0; i < paramArrayOfProtectionDomain.length; i++) {
/* 139 */         if ((paramArrayOfProtectionDomain[i] != null) && (!localArrayList.contains(paramArrayOfProtectionDomain[i])))
/* 140 */           localArrayList.add(paramArrayOfProtectionDomain[i]);
/*     */       }
/* 142 */       if (!localArrayList.isEmpty()) {
/* 143 */         this.context = new ProtectionDomain[localArrayList.size()];
/* 144 */         this.context = ((ProtectionDomain[])localArrayList.toArray(this.context));
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
/*     */   public AccessControlContext(AccessControlContext paramAccessControlContext, DomainCombiner paramDomainCombiner)
/*     */   {
/* 175 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 176 */     if (localSecurityManager != null) {
/* 177 */       localSecurityManager.checkPermission(SecurityConstants.CREATE_ACC_PERMISSION);
/* 178 */       this.isAuthorized = true;
/*     */     }
/*     */     
/* 181 */     this.context = paramAccessControlContext.context;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 189 */     this.combiner = paramDomainCombiner;
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
/*     */   AccessControlContext(ProtectionDomain paramProtectionDomain, DomainCombiner paramDomainCombiner, AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission)
/*     */   {
/* 206 */     ProtectionDomain[] arrayOfProtectionDomain = null;
/* 207 */     if (paramProtectionDomain != null) {
/* 208 */       arrayOfProtectionDomain = new ProtectionDomain[] { paramProtectionDomain };
/*     */     }
/* 210 */     if (paramAccessControlContext2 != null) {
/* 211 */       if (paramDomainCombiner != null) {
/* 212 */         this.context = paramDomainCombiner.combine(arrayOfProtectionDomain, paramAccessControlContext2.context);
/*     */       } else {
/* 214 */         this.context = combine(arrayOfProtectionDomain, paramAccessControlContext2.context);
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 220 */     else if (paramDomainCombiner != null) {
/* 221 */       this.context = paramDomainCombiner.combine(arrayOfProtectionDomain, null);
/*     */     } else {
/* 223 */       this.context = combine(arrayOfProtectionDomain, null);
/*     */     }
/*     */     
/* 226 */     this.combiner = paramDomainCombiner;
/*     */     
/* 228 */     Permission[] arrayOfPermission = null;
/* 229 */     if (paramArrayOfPermission != null) {
/* 230 */       arrayOfPermission = new Permission[paramArrayOfPermission.length];
/* 231 */       for (int i = 0; i < paramArrayOfPermission.length; i++) {
/* 232 */         if (paramArrayOfPermission[i] == null) {
/* 233 */           throw new NullPointerException("permission can't be null");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */         if (paramArrayOfPermission[i].getClass() == AllPermission.class) {
/* 241 */           paramAccessControlContext1 = null;
/*     */         }
/* 243 */         arrayOfPermission[i] = paramArrayOfPermission[i];
/*     */       }
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
/*     */ 
/* 256 */     if (paramAccessControlContext1 != null) {
/* 257 */       this.limitedContext = combine(paramAccessControlContext1.context, paramAccessControlContext1.limitedContext);
/* 258 */       this.isLimited = true;
/* 259 */       this.isWrapped = true;
/* 260 */       this.permissions = arrayOfPermission;
/* 261 */       this.parent = paramAccessControlContext1;
/* 262 */       this.privilegedContext = paramAccessControlContext2;
/*     */     }
/* 264 */     this.isAuthorized = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   AccessControlContext(ProtectionDomain[] paramArrayOfProtectionDomain, boolean paramBoolean)
/*     */   {
/* 275 */     this.context = paramArrayOfProtectionDomain;
/* 276 */     this.isPrivileged = paramBoolean;
/* 277 */     this.isAuthorized = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   AccessControlContext(ProtectionDomain[] paramArrayOfProtectionDomain, AccessControlContext paramAccessControlContext)
/*     */   {
/* 286 */     this.context = paramArrayOfProtectionDomain;
/* 287 */     this.privilegedContext = paramAccessControlContext;
/* 288 */     this.isPrivileged = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   ProtectionDomain[] getContext()
/*     */   {
/* 295 */     return this.context;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isPrivileged()
/*     */   {
/* 303 */     return this.isPrivileged;
/*     */   }
/*     */   
/*     */ 
/*     */   DomainCombiner getAssignedCombiner()
/*     */   {
/*     */     AccessControlContext localAccessControlContext;
/*     */     
/* 311 */     if (this.isPrivileged) {
/* 312 */       localAccessControlContext = this.privilegedContext;
/*     */     } else {
/* 314 */       localAccessControlContext = AccessController.getInheritedAccessControlContext();
/*     */     }
/* 316 */     if (localAccessControlContext != null) {
/* 317 */       return localAccessControlContext.combiner;
/*     */     }
/* 319 */     return null;
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
/*     */   public DomainCombiner getDomainCombiner()
/*     */   {
/* 339 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 340 */     if (localSecurityManager != null) {
/* 341 */       localSecurityManager.checkPermission(SecurityConstants.GET_COMBINER_PERMISSION);
/*     */     }
/* 343 */     return getCombiner();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   DomainCombiner getCombiner()
/*     */   {
/* 350 */     return this.combiner;
/*     */   }
/*     */   
/*     */   boolean isAuthorized() {
/* 354 */     return this.isAuthorized;
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
/*     */   public void checkPermission(Permission paramPermission)
/*     */     throws AccessControlException
/*     */   {
/* 379 */     int i = 0;
/*     */     
/* 381 */     if (paramPermission == null) {
/* 382 */       throw new NullPointerException("permission can't be null");
/*     */     }
/* 384 */     if (getDebug() != null)
/*     */     {
/* 386 */       i = !Debug.isOn("codebase=") ? 1 : 0;
/* 387 */       if (i == 0)
/*     */       {
/*     */ 
/* 390 */         for (j = 0; (this.context != null) && (j < this.context.length); j++) {
/* 391 */           if ((this.context[j].getCodeSource() != null) && 
/* 392 */             (this.context[j].getCodeSource().getLocation() != null) && 
/* 393 */             (Debug.isOn("codebase=" + this.context[j].getCodeSource().getLocation().toString()))) {
/* 394 */             i = 1;
/* 395 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 401 */       i = i & ((!Debug.isOn("permission=")) || (Debug.isOn("permission=" + paramPermission.getClass().getCanonicalName())) ? 1 : 0);
/*     */       
/* 403 */       if ((i != 0) && (Debug.isOn("stack"))) {
/* 404 */         Thread.dumpStack();
/*     */       }
/*     */       
/* 407 */       if ((i != 0) && (Debug.isOn("domain"))) {
/* 408 */         if (this.context == null) {
/* 409 */           debug.println("domain (context is null)");
/*     */         } else {
/* 411 */           for (j = 0; j < this.context.length; j++) {
/* 412 */             debug.println("domain " + j + " " + this.context[j]);
/*     */           }
/*     */         }
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/* 429 */     if (this.context == null) {
/* 430 */       checkPermission2(paramPermission);
/* 431 */       return;
/*     */     }
/*     */     
/* 434 */     for (int j = 0; j < this.context.length; j++) {
/* 435 */       if ((this.context[j] != null) && (!this.context[j].implies(paramPermission))) {
/* 436 */         if (i != 0) {
/* 437 */           debug.println("access denied " + paramPermission);
/*     */         }
/*     */         
/* 440 */         if ((Debug.isOn("failure")) && (debug != null))
/*     */         {
/*     */ 
/*     */ 
/* 444 */           if (i == 0) {
/* 445 */             debug.println("access denied " + paramPermission);
/*     */           }
/* 447 */           Thread.dumpStack();
/* 448 */           final ProtectionDomain localProtectionDomain = this.context[j];
/* 449 */           final Debug localDebug = debug;
/* 450 */           AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Void run() {
/* 452 */               localDebug.println("domain that failed " + localProtectionDomain);
/* 453 */               return null;
/*     */             }
/*     */           });
/*     */         }
/* 457 */         throw new AccessControlException("access denied " + paramPermission, paramPermission);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 462 */     if (i != 0) {
/* 463 */       debug.println("access allowed " + paramPermission);
/*     */     }
/*     */     
/* 466 */     checkPermission2(paramPermission);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void checkPermission2(Permission paramPermission)
/*     */   {
/* 473 */     if (!this.isLimited) {
/* 474 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 480 */     if (this.privilegedContext != null) {
/* 481 */       this.privilegedContext.checkPermission2(paramPermission);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 489 */     if (this.isWrapped) {
/* 490 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 496 */     if (this.permissions != null) {
/* 497 */       Class localClass = paramPermission.getClass();
/* 498 */       for (int i = 0; i < this.permissions.length; i++) {
/* 499 */         Permission localPermission = this.permissions[i];
/* 500 */         if ((localPermission.getClass().equals(localClass)) && (localPermission.implies(paramPermission))) {
/* 501 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 510 */     if (this.parent != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 520 */       if (this.permissions == null) {
/* 521 */         this.parent.checkPermission2(paramPermission);
/*     */       } else {
/* 523 */         this.parent.checkPermission(paramPermission);
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
/*     */   AccessControlContext optimize()
/*     */   {
/* 539 */     DomainCombiner localDomainCombiner = null;
/* 540 */     Object localObject = null;
/* 541 */     Permission[] arrayOfPermission = null;
/*     */     AccessControlContext localAccessControlContext;
/* 543 */     if (this.isPrivileged) {
/* 544 */       localAccessControlContext = this.privilegedContext;
/* 545 */       if (localAccessControlContext != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 551 */         if (localAccessControlContext.isWrapped) {
/* 552 */           arrayOfPermission = localAccessControlContext.permissions;
/* 553 */           localObject = localAccessControlContext.parent;
/*     */         }
/*     */       }
/*     */     } else {
/* 557 */       localAccessControlContext = AccessController.getInheritedAccessControlContext();
/* 558 */       if (localAccessControlContext != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 564 */         if (localAccessControlContext.isLimited) {
/* 565 */           localObject = localAccessControlContext;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 572 */     int i = this.context == null ? 1 : 0;
/*     */     
/*     */ 
/*     */ 
/* 576 */     int j = (localAccessControlContext == null) || (localAccessControlContext.context == null) ? 1 : 0;
/* 577 */     ProtectionDomain[] arrayOfProtectionDomain1 = j != 0 ? null : localAccessControlContext.context;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 582 */     int k = ((localAccessControlContext == null) || (!localAccessControlContext.isWrapped)) && (localObject == null) ? 1 : 0;
/*     */     ProtectionDomain[] arrayOfProtectionDomain2;
/* 584 */     if ((localAccessControlContext != null) && (localAccessControlContext.combiner != null))
/*     */     {
/* 586 */       if (getDebug() != null) {
/* 587 */         debug.println("AccessControlContext invoking the Combiner");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 592 */       localDomainCombiner = localAccessControlContext.combiner;
/* 593 */       arrayOfProtectionDomain2 = localDomainCombiner.combine(this.context, arrayOfProtectionDomain1);
/*     */     } else {
/* 595 */       if (i != 0) {
/* 596 */         if (j != 0) {
/* 597 */           calculateFields(localAccessControlContext, (AccessControlContext)localObject, arrayOfPermission);
/* 598 */           return this; }
/* 599 */         if (k != 0) {
/* 600 */           return localAccessControlContext;
/*     */         }
/* 602 */       } else if ((arrayOfProtectionDomain1 != null) && 
/* 603 */         (k != 0))
/*     */       {
/*     */ 
/*     */ 
/* 607 */         if ((this.context.length == 1) && (this.context[0] == arrayOfProtectionDomain1[0])) {
/* 608 */           return localAccessControlContext;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 613 */       arrayOfProtectionDomain2 = combine(this.context, arrayOfProtectionDomain1);
/* 614 */       if ((k != 0) && (j == 0) && (arrayOfProtectionDomain2 == arrayOfProtectionDomain1))
/* 615 */         return localAccessControlContext;
/* 616 */       if ((j != 0) && (arrayOfProtectionDomain2 == this.context)) {
/* 617 */         calculateFields(localAccessControlContext, (AccessControlContext)localObject, arrayOfPermission);
/* 618 */         return this;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 623 */     this.context = arrayOfProtectionDomain2;
/* 624 */     this.combiner = localDomainCombiner;
/* 625 */     this.isPrivileged = false;
/*     */     
/* 627 */     calculateFields(localAccessControlContext, (AccessControlContext)localObject, arrayOfPermission);
/* 628 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static ProtectionDomain[] combine(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2)
/*     */   {
/* 640 */     int i = paramArrayOfProtectionDomain1 == null ? 1 : 0;
/*     */     
/*     */ 
/*     */ 
/* 644 */     int j = paramArrayOfProtectionDomain2 == null ? 1 : 0;
/*     */     
/* 646 */     int k = i != 0 ? 0 : paramArrayOfProtectionDomain1.length;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 651 */     if ((j != 0) && (k <= 2)) {
/* 652 */       return paramArrayOfProtectionDomain1;
/*     */     }
/*     */     
/* 655 */     int m = j != 0 ? 0 : paramArrayOfProtectionDomain2.length;
/*     */     
/*     */ 
/* 658 */     Object localObject = new ProtectionDomain[k + m];
/*     */     
/*     */ 
/* 661 */     if (j == 0) {
/* 662 */       System.arraycopy(paramArrayOfProtectionDomain2, 0, localObject, 0, m);
/*     */     }
/*     */     
/*     */     label140:
/*     */     
/* 667 */     for (int n = 0; n < k; n++) {
/* 668 */       ProtectionDomain localProtectionDomain = paramArrayOfProtectionDomain1[n];
/* 669 */       if (localProtectionDomain != null) {
/* 670 */         for (int i1 = 0; i1 < m; i1++) {
/* 671 */           if (localProtectionDomain == localObject[i1]) {
/*     */             break label140;
/*     */           }
/*     */         }
/* 675 */         localObject[(m++)] = localProtectionDomain;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 680 */     if (m != localObject.length)
/*     */     {
/* 682 */       if ((j == 0) && (m == paramArrayOfProtectionDomain2.length))
/* 683 */         return paramArrayOfProtectionDomain2;
/* 684 */       if ((j != 0) && (m == k)) {
/* 685 */         return paramArrayOfProtectionDomain1;
/*     */       }
/* 687 */       ProtectionDomain[] arrayOfProtectionDomain = new ProtectionDomain[m];
/* 688 */       System.arraycopy(localObject, 0, arrayOfProtectionDomain, 0, m);
/* 689 */       localObject = arrayOfProtectionDomain;
/*     */     }
/*     */     
/* 692 */     return (ProtectionDomain[])localObject;
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
/*     */   private void calculateFields(AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission)
/*     */   {
/* 706 */     ProtectionDomain[] arrayOfProtectionDomain1 = null;
/* 707 */     ProtectionDomain[] arrayOfProtectionDomain2 = null;
/*     */     
/*     */ 
/* 710 */     arrayOfProtectionDomain1 = paramAccessControlContext2 != null ? paramAccessControlContext2.limitedContext : null;
/* 711 */     arrayOfProtectionDomain2 = paramAccessControlContext1 != null ? paramAccessControlContext1.limitedContext : null;
/* 712 */     ProtectionDomain[] arrayOfProtectionDomain3 = combine(arrayOfProtectionDomain1, arrayOfProtectionDomain2);
/* 713 */     if ((arrayOfProtectionDomain3 != null) && (
/* 714 */       (this.context == null) || (!containsAllPDs(arrayOfProtectionDomain3, this.context)))) {
/* 715 */       this.limitedContext = arrayOfProtectionDomain3;
/* 716 */       this.permissions = paramArrayOfPermission;
/* 717 */       this.parent = paramAccessControlContext2;
/* 718 */       this.isLimited = true;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 735 */     if (paramObject == this) {
/* 736 */       return true;
/*     */     }
/* 738 */     if (!(paramObject instanceof AccessControlContext)) {
/* 739 */       return false;
/*     */     }
/* 741 */     AccessControlContext localAccessControlContext = (AccessControlContext)paramObject;
/*     */     
/* 743 */     if (!equalContext(localAccessControlContext)) {
/* 744 */       return false;
/*     */     }
/* 746 */     if (!equalLimitedContext(localAccessControlContext)) {
/* 747 */       return false;
/*     */     }
/* 749 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean equalContext(AccessControlContext paramAccessControlContext)
/*     */   {
/* 757 */     if (!equalPDs(this.context, paramAccessControlContext.context)) {
/* 758 */       return false;
/*     */     }
/* 760 */     if ((this.combiner == null) && (paramAccessControlContext.combiner != null)) {
/* 761 */       return false;
/*     */     }
/* 763 */     if ((this.combiner != null) && (!this.combiner.equals(paramAccessControlContext.combiner))) {
/* 764 */       return false;
/*     */     }
/* 766 */     return true;
/*     */   }
/*     */   
/*     */   private boolean equalPDs(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 770 */     if (paramArrayOfProtectionDomain1 == null) {
/* 771 */       return paramArrayOfProtectionDomain2 == null;
/*     */     }
/*     */     
/* 774 */     if (paramArrayOfProtectionDomain2 == null) {
/* 775 */       return false;
/*     */     }
/* 777 */     if ((!containsAllPDs(paramArrayOfProtectionDomain1, paramArrayOfProtectionDomain2)) || (!containsAllPDs(paramArrayOfProtectionDomain2, paramArrayOfProtectionDomain1))) {
/* 778 */       return false;
/*     */     }
/* 780 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean equalLimitedContext(AccessControlContext paramAccessControlContext)
/*     */   {
/* 789 */     if (paramAccessControlContext == null) {
/* 790 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 795 */     if ((!this.isLimited) && (!paramAccessControlContext.isLimited)) {
/* 796 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 801 */     if ((!this.isLimited) || (!paramAccessControlContext.isLimited)) {
/* 802 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 810 */     if (((this.isWrapped) && (!paramAccessControlContext.isWrapped)) || ((!this.isWrapped) && (paramAccessControlContext.isWrapped)))
/*     */     {
/* 812 */       return false;
/*     */     }
/*     */     
/* 815 */     if ((this.permissions == null) && (paramAccessControlContext.permissions != null)) {
/* 816 */       return false;
/*     */     }
/* 818 */     if ((this.permissions != null) && (paramAccessControlContext.permissions == null)) {
/* 819 */       return false;
/*     */     }
/* 821 */     if ((!containsAllLimits(paramAccessControlContext)) || (!paramAccessControlContext.containsAllLimits(this))) {
/* 822 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 827 */     AccessControlContext localAccessControlContext1 = getNextPC(this);
/* 828 */     AccessControlContext localAccessControlContext2 = getNextPC(paramAccessControlContext);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 836 */     if ((localAccessControlContext1 == null) && (localAccessControlContext2 != null) && (localAccessControlContext2.isLimited)) {
/* 837 */       return false;
/*     */     }
/* 839 */     if ((localAccessControlContext1 != null) && (!localAccessControlContext1.equalLimitedContext(localAccessControlContext2))) {
/* 840 */       return false;
/*     */     }
/* 842 */     if ((this.parent == null) && (paramAccessControlContext.parent != null)) {
/* 843 */       return false;
/*     */     }
/* 845 */     if ((this.parent != null) && (!this.parent.equals(paramAccessControlContext.parent))) {
/* 846 */       return false;
/*     */     }
/* 848 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static AccessControlContext getNextPC(AccessControlContext paramAccessControlContext)
/*     */   {
/* 856 */     while ((paramAccessControlContext != null) && (paramAccessControlContext.privilegedContext != null)) {
/* 857 */       paramAccessControlContext = paramAccessControlContext.privilegedContext;
/* 858 */       if (!paramAccessControlContext.isWrapped)
/* 859 */         return paramAccessControlContext;
/*     */     }
/* 861 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean containsAllPDs(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2)
/*     */   {
/* 866 */     boolean bool = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 875 */     for (int i = 0; i < paramArrayOfProtectionDomain1.length; i++) {
/* 876 */       bool = false;
/* 877 */       ProtectionDomain localProtectionDomain1; if ((localProtectionDomain1 = paramArrayOfProtectionDomain1[i]) == null) {
/* 878 */         for (int j = 0; (j < paramArrayOfProtectionDomain2.length) && (!bool); j++) {
/* 879 */           bool = paramArrayOfProtectionDomain2[j] == null;
/*     */         }
/*     */       } else {
/* 882 */         Class localClass = localProtectionDomain1.getClass();
/*     */         
/* 884 */         for (int k = 0; (k < paramArrayOfProtectionDomain2.length) && (!bool); k++) {
/* 885 */           ProtectionDomain localProtectionDomain2 = paramArrayOfProtectionDomain2[k];
/*     */           
/*     */ 
/*     */ 
/* 889 */           bool = (localProtectionDomain2 != null) && (localClass == localProtectionDomain2.getClass()) && (localProtectionDomain1.equals(localProtectionDomain2));
/*     */         }
/*     */       }
/* 892 */       if (!bool) return false;
/*     */     }
/* 894 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean containsAllLimits(AccessControlContext paramAccessControlContext) {
/* 898 */     boolean bool = false;
/*     */     
/*     */ 
/* 901 */     if ((this.permissions == null) && (paramAccessControlContext.permissions == null)) {
/* 902 */       return true;
/*     */     }
/* 904 */     for (int i = 0; i < this.permissions.length; i++) {
/* 905 */       Permission localPermission1 = this.permissions[i];
/* 906 */       Class localClass = localPermission1.getClass();
/* 907 */       bool = false;
/* 908 */       for (int j = 0; (j < paramAccessControlContext.permissions.length) && (!bool); j++) {
/* 909 */         Permission localPermission2 = paramAccessControlContext.permissions[j];
/*     */         
/* 911 */         bool = (localClass.equals(localPermission2.getClass())) && (localPermission1.equals(localPermission2));
/*     */       }
/* 913 */       if (!bool) return false;
/*     */     }
/* 915 */     return bool;
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
/*     */   public int hashCode()
/*     */   {
/* 928 */     int i = 0;
/*     */     
/* 930 */     if (this.context == null) {
/* 931 */       return i;
/*     */     }
/* 933 */     for (int j = 0; j < this.context.length; j++) {
/* 934 */       if (this.context[j] != null) {
/* 935 */         i ^= this.context[j].hashCode();
/*     */       }
/*     */     }
/* 938 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/AccessControlContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */