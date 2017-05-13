/*     */ package java.awt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ import sun.util.logging.PlatformLogger.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerOrderFocusTraversalPolicy
/*     */   extends FocusTraversalPolicy
/*     */   implements Serializable
/*     */ {
/*  63 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.ContainerOrderFocusTraversalPolicy");
/*     */   
/*  65 */   private final int FORWARD_TRAVERSAL = 0;
/*  66 */   private final int BACKWARD_TRAVERSAL = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 486933713763926351L;
/*     */   
/*     */ 
/*  73 */   private boolean implicitDownCycleTraversal = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient Container cachedRoot;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient List<Component> cachedCycle;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<Component> getFocusTraversalCycle(Container paramContainer)
/*     */   {
/* 106 */     ArrayList localArrayList = new ArrayList();
/* 107 */     enumerateCycle(paramContainer, localArrayList);
/* 108 */     return localArrayList;
/*     */   }
/*     */   
/* 111 */   private int getComponentIndex(List<Component> paramList, Component paramComponent) { return paramList.indexOf(paramComponent); }
/*     */   
/*     */   private void enumerateCycle(Container paramContainer, List<Component> paramList)
/*     */   {
/* 115 */     if ((!paramContainer.isVisible()) || (!paramContainer.isDisplayable())) {
/* 116 */       return;
/*     */     }
/*     */     
/* 119 */     paramList.add(paramContainer);
/*     */     
/* 121 */     Component[] arrayOfComponent = paramContainer.getComponents();
/* 122 */     for (int i = 0; i < arrayOfComponent.length; i++) {
/* 123 */       Component localComponent = arrayOfComponent[i];
/* 124 */       if ((localComponent instanceof Container)) {
/* 125 */         Container localContainer = (Container)localComponent;
/*     */         
/* 127 */         if ((!localContainer.isFocusCycleRoot()) && (!localContainer.isFocusTraversalPolicyProvider())) {
/* 128 */           enumerateCycle(localContainer, paramList);
/* 129 */           continue;
/*     */         }
/*     */       }
/* 132 */       paramList.add(localComponent);
/*     */     }
/*     */   }
/*     */   
/*     */   private Container getTopmostProvider(Container paramContainer, Component paramComponent) {
/* 137 */     Container localContainer1 = paramComponent.getParent();
/* 138 */     Container localContainer2 = null;
/* 139 */     while ((localContainer1 != paramContainer) && (localContainer1 != null)) {
/* 140 */       if (localContainer1.isFocusTraversalPolicyProvider()) {
/* 141 */         localContainer2 = localContainer1;
/*     */       }
/* 143 */       localContainer1 = localContainer1.getParent();
/*     */     }
/* 145 */     if (localContainer1 == null) {
/* 146 */       return null;
/*     */     }
/* 148 */     return localContainer2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Component getComponentDownCycle(Component paramComponent, int paramInt)
/*     */   {
/* 159 */     Component localComponent = null;
/*     */     
/* 161 */     if ((paramComponent instanceof Container)) {
/* 162 */       Container localContainer = (Container)paramComponent;
/*     */       
/* 164 */       if (localContainer.isFocusCycleRoot()) {
/* 165 */         if (getImplicitDownCycleTraversal()) {
/* 166 */           localComponent = localContainer.getFocusTraversalPolicy().getDefaultComponent(localContainer);
/*     */           
/* 168 */           if ((localComponent != null) && (log.isLoggable(PlatformLogger.Level.FINE))) {
/* 169 */             log.fine("### Transfered focus down-cycle to " + localComponent + " in the focus cycle root " + localContainer);
/*     */           }
/*     */         }
/*     */         else {
/* 173 */           return null;
/*     */         }
/* 175 */       } else if (localContainer.isFocusTraversalPolicyProvider())
/*     */       {
/*     */ 
/* 178 */         localComponent = paramInt == 0 ? localContainer.getFocusTraversalPolicy().getDefaultComponent(localContainer) : localContainer.getFocusTraversalPolicy().getLastComponent(localContainer);
/*     */         
/* 180 */         if ((localComponent != null) && (log.isLoggable(PlatformLogger.Level.FINE))) {
/* 181 */           log.fine("### Transfered focus to " + localComponent + " in the FTP provider " + localContainer);
/*     */         }
/*     */       }
/*     */     }
/* 185 */     return localComponent;
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
/*     */   public Component getComponentAfter(Container paramContainer, Component paramComponent)
/*     */   {
/* 211 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 212 */       log.fine("### Searching in " + paramContainer + " for component after " + paramComponent);
/*     */     }
/*     */     
/* 215 */     if ((paramContainer == null) || (paramComponent == null)) {
/* 216 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 218 */     if ((!paramContainer.isFocusTraversalPolicyProvider()) && (!paramContainer.isFocusCycleRoot())) {
/* 219 */       throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");
/*     */     }
/* 221 */     if ((paramContainer.isFocusCycleRoot()) && (!paramComponent.isFocusCycleRoot(paramContainer))) {
/* 222 */       throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
/*     */     }
/*     */     
/* 225 */     synchronized (paramContainer.getTreeLock())
/*     */     {
/* 227 */       if ((!paramContainer.isVisible()) || (!paramContainer.isDisplayable())) {
/* 228 */         return null;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 233 */       Component localComponent1 = getComponentDownCycle(paramComponent, 0);
/* 234 */       if (localComponent1 != null) {
/* 235 */         return localComponent1;
/*     */       }
/*     */       
/*     */ 
/* 239 */       Container localContainer = getTopmostProvider(paramContainer, paramComponent);
/* 240 */       if (localContainer != null) {
/* 241 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 242 */           log.fine("### Asking FTP " + localContainer + " for component after " + paramComponent);
/*     */         }
/*     */         
/*     */ 
/* 246 */         localObject1 = localContainer.getFocusTraversalPolicy();
/* 247 */         Component localComponent2 = ((FocusTraversalPolicy)localObject1).getComponentAfter(localContainer, paramComponent);
/*     */         
/*     */ 
/*     */ 
/* 251 */         if (localComponent2 != null) {
/* 252 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 253 */             log.fine("### FTP returned " + localComponent2);
/*     */           }
/* 255 */           return localComponent2;
/*     */         }
/* 257 */         paramComponent = localContainer;
/*     */       }
/*     */       
/* 260 */       Object localObject1 = getFocusTraversalCycle(paramContainer);
/*     */       
/* 262 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 263 */         log.fine("### Cycle is " + localObject1 + ", component is " + paramComponent);
/*     */       }
/*     */       
/* 266 */       int i = getComponentIndex((List)localObject1, paramComponent);
/*     */       
/* 268 */       if (i < 0) {
/* 269 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 270 */           log.fine("### Didn't find component " + paramComponent + " in a cycle " + paramContainer);
/*     */         }
/* 272 */         return getFirstComponent(paramContainer);
/*     */       }
/*     */       
/* 275 */       for (i++; i < ((List)localObject1).size(); i++) {
/* 276 */         localComponent1 = (Component)((List)localObject1).get(i);
/* 277 */         if (accept(localComponent1))
/* 278 */           return localComponent1;
/* 279 */         if ((localComponent1 = getComponentDownCycle(localComponent1, 0)) != null) {
/* 280 */           return localComponent1;
/*     */         }
/*     */       }
/*     */       
/* 284 */       if (paramContainer.isFocusCycleRoot()) {
/* 285 */         this.cachedRoot = paramContainer;
/* 286 */         this.cachedCycle = ((List)localObject1);
/*     */         
/* 288 */         localComponent1 = getFirstComponent(paramContainer);
/*     */         
/* 290 */         this.cachedRoot = null;
/* 291 */         this.cachedCycle = null;
/*     */         
/* 293 */         return localComponent1;
/*     */       }
/*     */     }
/* 296 */     return null;
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
/*     */   public Component getComponentBefore(Container paramContainer, Component paramComponent)
/*     */   {
/* 315 */     if ((paramContainer == null) || (paramComponent == null)) {
/* 316 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 318 */     if ((!paramContainer.isFocusTraversalPolicyProvider()) && (!paramContainer.isFocusCycleRoot())) {
/* 319 */       throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");
/*     */     }
/* 321 */     if ((paramContainer.isFocusCycleRoot()) && (!paramComponent.isFocusCycleRoot(paramContainer))) {
/* 322 */       throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
/*     */     }
/*     */     
/* 325 */     synchronized (paramContainer.getTreeLock())
/*     */     {
/* 327 */       if ((!paramContainer.isVisible()) || (!paramContainer.isDisplayable())) {
/* 328 */         return null;
/*     */       }
/*     */       
/*     */ 
/* 332 */       Container localContainer = getTopmostProvider(paramContainer, paramComponent);
/* 333 */       if (localContainer != null) {
/* 334 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 335 */           log.fine("### Asking FTP " + localContainer + " for component after " + paramComponent);
/*     */         }
/*     */         
/*     */ 
/* 339 */         localObject1 = localContainer.getFocusTraversalPolicy();
/* 340 */         Component localComponent1 = ((FocusTraversalPolicy)localObject1).getComponentBefore(localContainer, paramComponent);
/*     */         
/*     */ 
/*     */ 
/* 344 */         if (localComponent1 != null) {
/* 345 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 346 */             log.fine("### FTP returned " + localComponent1);
/*     */           }
/* 348 */           return localComponent1;
/*     */         }
/* 350 */         paramComponent = localContainer;
/*     */         
/*     */ 
/* 353 */         if (accept(paramComponent)) {
/* 354 */           return paramComponent;
/*     */         }
/*     */       }
/*     */       
/* 358 */       Object localObject1 = getFocusTraversalCycle(paramContainer);
/*     */       
/* 360 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 361 */         log.fine("### Cycle is " + localObject1 + ", component is " + paramComponent);
/*     */       }
/*     */       
/* 364 */       int i = getComponentIndex((List)localObject1, paramComponent);
/*     */       
/* 366 */       if (i < 0) {
/* 367 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 368 */           log.fine("### Didn't find component " + paramComponent + " in a cycle " + paramContainer);
/*     */         }
/* 370 */         return getLastComponent(paramContainer);
/*     */       }
/*     */       
/* 373 */       Component localComponent2 = null;
/* 374 */       Component localComponent3 = null;
/*     */       
/* 376 */       for (i--; i >= 0; i--) {
/* 377 */         localComponent2 = (Component)((List)localObject1).get(i);
/* 378 */         if ((localComponent2 != paramContainer) && ((localComponent3 = getComponentDownCycle(localComponent2, 1)) != null))
/* 379 */           return localComponent3;
/* 380 */         if (accept(localComponent2)) {
/* 381 */           return localComponent2;
/*     */         }
/*     */       }
/*     */       
/* 385 */       if (paramContainer.isFocusCycleRoot()) {
/* 386 */         this.cachedRoot = paramContainer;
/* 387 */         this.cachedCycle = ((List)localObject1);
/*     */         
/* 389 */         localComponent2 = getLastComponent(paramContainer);
/*     */         
/* 391 */         this.cachedRoot = null;
/* 392 */         this.cachedCycle = null;
/*     */         
/* 394 */         return localComponent2;
/*     */       }
/*     */     }
/* 397 */     return null;
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
/*     */   public Component getFirstComponent(Container paramContainer)
/*     */   {
/* 414 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 415 */       log.fine("### Getting first component in " + paramContainer);
/*     */     }
/* 417 */     if (paramContainer == null) {
/* 418 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/*     */     
/*     */ 
/* 422 */     synchronized (paramContainer.getTreeLock())
/*     */     {
/* 424 */       if ((!paramContainer.isVisible()) || (!paramContainer.isDisplayable())) {
/* 425 */         return null;
/*     */       }
/*     */       List localList;
/* 428 */       if (this.cachedRoot == paramContainer) {
/* 429 */         localList = this.cachedCycle;
/*     */       } else {
/* 431 */         localList = getFocusTraversalCycle(paramContainer);
/*     */       }
/*     */       
/* 434 */       if (localList.size() == 0) {
/* 435 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 436 */           log.fine("### Cycle is empty");
/*     */         }
/* 438 */         return null;
/*     */       }
/* 440 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 441 */         log.fine("### Cycle is " + localList);
/*     */       }
/*     */       
/* 444 */       for (Component localComponent : localList) {
/* 445 */         if (accept(localComponent))
/* 446 */           return localComponent;
/* 447 */         if ((localComponent != paramContainer) && 
/* 448 */           ((localComponent = getComponentDownCycle(localComponent, 0)) != null))
/*     */         {
/* 450 */           return localComponent;
/*     */         }
/*     */       }
/*     */     }
/* 454 */     return null;
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
/*     */   public Component getLastComponent(Container paramContainer)
/*     */   {
/* 470 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 471 */       log.fine("### Getting last component in " + paramContainer);
/*     */     }
/*     */     
/* 474 */     if (paramContainer == null) {
/* 475 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/*     */     
/* 478 */     synchronized (paramContainer.getTreeLock())
/*     */     {
/* 480 */       if ((!paramContainer.isVisible()) || (!paramContainer.isDisplayable())) {
/* 481 */         return null;
/*     */       }
/*     */       List localList;
/* 484 */       if (this.cachedRoot == paramContainer) {
/* 485 */         localList = this.cachedCycle;
/*     */       } else {
/* 487 */         localList = getFocusTraversalCycle(paramContainer);
/*     */       }
/*     */       
/* 490 */       if (localList.size() == 0) {
/* 491 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 492 */           log.fine("### Cycle is empty");
/*     */         }
/* 494 */         return null;
/*     */       }
/* 496 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 497 */         log.fine("### Cycle is " + localList);
/*     */       }
/*     */       
/* 500 */       for (int i = localList.size() - 1; i >= 0; i--) {
/* 501 */         Component localComponent = (Component)localList.get(i);
/* 502 */         if (accept(localComponent))
/* 503 */           return localComponent;
/* 504 */         if (((localComponent instanceof Container)) && (localComponent != paramContainer)) {
/* 505 */           Container localContainer = (Container)localComponent;
/* 506 */           if (localContainer.isFocusTraversalPolicyProvider()) {
/* 507 */             return localContainer.getFocusTraversalPolicy().getLastComponent(localContainer);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 512 */     return null;
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
/*     */   public Component getDefaultComponent(Container paramContainer)
/*     */   {
/* 529 */     return getFirstComponent(paramContainer);
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
/*     */   public void setImplicitDownCycleTraversal(boolean paramBoolean)
/*     */   {
/* 548 */     this.implicitDownCycleTraversal = paramBoolean;
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
/*     */   public boolean getImplicitDownCycleTraversal()
/*     */   {
/* 565 */     return this.implicitDownCycleTraversal;
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
/*     */   protected boolean accept(Component paramComponent)
/*     */   {
/* 579 */     if (!paramComponent.canBeFocusOwner()) {
/* 580 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 586 */     if (!(paramComponent instanceof Window)) {
/* 587 */       for (Container localContainer = paramComponent.getParent(); 
/* 588 */           localContainer != null; 
/* 589 */           localContainer = localContainer.getParent())
/*     */       {
/* 591 */         if ((!localContainer.isEnabled()) && (!localContainer.isLightweight())) {
/* 592 */           return false;
/*     */         }
/* 594 */         if ((localContainer instanceof Window)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 600 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/ContainerOrderFocusTraversalPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */