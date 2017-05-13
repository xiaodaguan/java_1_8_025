/*      */ package java.beans.beancontext;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.TooManyListenersException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BeanContextServicesSupport
/*      */   extends BeanContextSupport
/*      */   implements BeanContextServices
/*      */ {
/*      */   private static final long serialVersionUID = -8494482757288719206L;
/*      */   protected transient HashMap services;
/*      */   
/*      */   public BeanContextServicesSupport(BeanContextServices paramBeanContextServices, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*   78 */     super(paramBeanContextServices, paramLocale, paramBoolean1, paramBoolean2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextServicesSupport(BeanContextServices paramBeanContextServices, Locale paramLocale, boolean paramBoolean)
/*      */   {
/*   90 */     this(paramBeanContextServices, paramLocale, paramBoolean, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextServicesSupport(BeanContextServices paramBeanContextServices, Locale paramLocale)
/*      */   {
/*  101 */     this(paramBeanContextServices, paramLocale, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextServicesSupport(BeanContextServices paramBeanContextServices)
/*      */   {
/*  111 */     this(paramBeanContextServices, null, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextServicesSupport()
/*      */   {
/*  119 */     this(null, null, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void initialize()
/*      */   {
/*  131 */     super.initialize();
/*      */     
/*  133 */     this.services = new HashMap(this.serializable + 1);
/*  134 */     this.bcsListeners = new ArrayList(1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextServices getBeanContextServicesPeer()
/*      */   {
/*  145 */     return (BeanContextServices)getBeanContextChildPeer();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected class BCSSChild
/*      */     extends BeanContextSupport.BCSChild
/*      */   {
/*      */     private static final long serialVersionUID = -3263851306889194873L;
/*      */     
/*      */     private transient HashMap serviceClasses;
/*      */     
/*      */     private transient HashMap serviceRequestors;
/*      */     
/*      */ 
/*      */     class BCSSCServiceClassRef
/*      */     {
/*      */       Class serviceClass;
/*      */       
/*      */       BeanContextServiceProvider serviceProvider;
/*      */       
/*      */       int serviceRefs;
/*      */       
/*      */       BeanContextServiceProvider delegateProvider;
/*      */       
/*      */       int delegateRefs;
/*      */       
/*      */ 
/*      */       BCSSCServiceClassRef(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider, boolean paramBoolean)
/*      */       {
/*  175 */         this.serviceClass = paramClass;
/*      */         
/*  177 */         if (paramBoolean) {
/*  178 */           this.delegateProvider = paramBeanContextServiceProvider;
/*      */         } else {
/*  180 */           this.serviceProvider = paramBeanContextServiceProvider;
/*      */         }
/*      */       }
/*      */       
/*      */       void addRequestor(Object paramObject, BeanContextServiceRevokedListener paramBeanContextServiceRevokedListener) throws TooManyListenersException
/*      */       {
/*  186 */         BeanContextServiceRevokedListener localBeanContextServiceRevokedListener = (BeanContextServiceRevokedListener)this.requestors.get(paramObject);
/*      */         
/*  188 */         if ((localBeanContextServiceRevokedListener != null) && (!localBeanContextServiceRevokedListener.equals(paramBeanContextServiceRevokedListener))) {
/*  189 */           throw new TooManyListenersException();
/*      */         }
/*  191 */         this.requestors.put(paramObject, paramBeanContextServiceRevokedListener);
/*      */       }
/*      */       
/*      */ 
/*      */       void removeRequestor(Object paramObject)
/*      */       {
/*  197 */         this.requestors.remove(paramObject);
/*      */       }
/*      */       
/*      */       void verifyRequestor(Object paramObject, BeanContextServiceRevokedListener paramBeanContextServiceRevokedListener)
/*      */         throws TooManyListenersException
/*      */       {
/*  203 */         BeanContextServiceRevokedListener localBeanContextServiceRevokedListener = (BeanContextServiceRevokedListener)this.requestors.get(paramObject);
/*      */         
/*  205 */         if ((localBeanContextServiceRevokedListener != null) && (!localBeanContextServiceRevokedListener.equals(paramBeanContextServiceRevokedListener))) {
/*  206 */           throw new TooManyListenersException();
/*      */         }
/*      */       }
/*      */       
/*      */       void verifyAndMaybeSetProvider(BeanContextServiceProvider paramBeanContextServiceProvider, boolean paramBoolean) {
/*      */         BeanContextServiceProvider localBeanContextServiceProvider;
/*  212 */         if (paramBoolean) {
/*  213 */           localBeanContextServiceProvider = this.delegateProvider;
/*      */           
/*  215 */           if ((localBeanContextServiceProvider == null) || (paramBeanContextServiceProvider == null)) {
/*  216 */             this.delegateProvider = paramBeanContextServiceProvider;
/*      */           }
/*      */         }
/*      */         else {
/*  220 */           localBeanContextServiceProvider = this.serviceProvider;
/*      */           
/*  222 */           if ((localBeanContextServiceProvider == null) || (paramBeanContextServiceProvider == null)) {
/*  223 */             this.serviceProvider = paramBeanContextServiceProvider;
/*  224 */             return;
/*      */           }
/*      */         }
/*      */         
/*  228 */         if (!localBeanContextServiceProvider.equals(paramBeanContextServiceProvider)) {
/*  229 */           throw new UnsupportedOperationException("existing service reference obtained from different BeanContextServiceProvider not supported");
/*      */         }
/*      */       }
/*      */       
/*      */       Iterator cloneOfEntries() {
/*  234 */         return ((HashMap)this.requestors.clone()).entrySet().iterator();
/*      */       }
/*      */       
/*  237 */       Iterator entries() { return this.requestors.entrySet().iterator(); }
/*      */       
/*  239 */       boolean isEmpty() { return this.requestors.isEmpty(); }
/*      */       
/*  241 */       Class getServiceClass() { return this.serviceClass; }
/*      */       
/*      */       BeanContextServiceProvider getServiceProvider() {
/*  244 */         return this.serviceProvider;
/*      */       }
/*      */       
/*      */       BeanContextServiceProvider getDelegateProvider() {
/*  248 */         return this.delegateProvider;
/*      */       }
/*      */       
/*  251 */       boolean isDelegated() { return this.delegateProvider != null; }
/*      */       
/*      */       void addRef(boolean paramBoolean) {
/*  254 */         if (paramBoolean) {
/*  255 */           this.delegateRefs += 1;
/*      */         } else {
/*  257 */           this.serviceRefs += 1;
/*      */         }
/*      */       }
/*      */       
/*      */       void releaseRef(boolean paramBoolean)
/*      */       {
/*  263 */         if (paramBoolean) {
/*  264 */           if (--this.delegateRefs == 0) {
/*  265 */             this.delegateProvider = null;
/*      */           }
/*      */         }
/*  268 */         else if (--this.serviceRefs <= 0) {
/*  269 */           this.serviceProvider = null;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  274 */       int getRefs() { return this.serviceRefs + this.delegateRefs; }
/*      */       
/*  276 */       int getDelegateRefs() { return this.delegateRefs; }
/*      */       
/*  278 */       int getServiceRefs() { return this.serviceRefs; }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  292 */       HashMap requestors = new HashMap(1);
/*      */     }
/*      */     
/*      */     class BCSSCServiceRef
/*      */     {
/*      */       BeanContextServicesSupport.BCSSChild.BCSSCServiceClassRef serviceClassRef;
/*      */       
/*      */       BCSSCServiceRef(BeanContextServicesSupport.BCSSChild.BCSSCServiceClassRef paramBCSSCServiceClassRef, boolean paramBoolean)
/*      */       {
/*  301 */         this.serviceClassRef = paramBCSSCServiceClassRef;
/*  302 */         this.delegated = paramBoolean;
/*      */       }
/*      */       
/*  305 */       void addRef() { this.refCnt += 1; }
/*  306 */       int release() { return --this.refCnt; }
/*      */       
/*  308 */       BeanContextServicesSupport.BCSSChild.BCSSCServiceClassRef getServiceClassRef() { return this.serviceClassRef; }
/*      */       
/*  310 */       boolean isDelegated() { return this.delegated; }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  317 */       int refCnt = 1;
/*  318 */       boolean delegated = false;
/*      */     }
/*      */     
/*  321 */     BCSSChild(Object paramObject1, Object paramObject2) { super(paramObject1, paramObject2); }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     synchronized void usingService(Object paramObject1, Object paramObject2, Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider, boolean paramBoolean, BeanContextServiceRevokedListener paramBeanContextServiceRevokedListener)
/*      */       throws TooManyListenersException, UnsupportedOperationException
/*      */     {
/*  329 */       BCSSCServiceClassRef localBCSSCServiceClassRef = null;
/*      */       
/*  331 */       if (this.serviceClasses == null) {
/*  332 */         this.serviceClasses = new HashMap(1);
/*      */       } else {
/*  334 */         localBCSSCServiceClassRef = (BCSSCServiceClassRef)this.serviceClasses.get(paramClass);
/*      */       }
/*  336 */       if (localBCSSCServiceClassRef == null) {
/*  337 */         localBCSSCServiceClassRef = new BCSSCServiceClassRef(paramClass, paramBeanContextServiceProvider, paramBoolean);
/*  338 */         this.serviceClasses.put(paramClass, localBCSSCServiceClassRef);
/*      */       }
/*      */       else {
/*  341 */         localBCSSCServiceClassRef.verifyAndMaybeSetProvider(paramBeanContextServiceProvider, paramBoolean);
/*  342 */         localBCSSCServiceClassRef.verifyRequestor(paramObject1, paramBeanContextServiceRevokedListener);
/*      */       }
/*      */       
/*  345 */       localBCSSCServiceClassRef.addRequestor(paramObject1, paramBeanContextServiceRevokedListener);
/*  346 */       localBCSSCServiceClassRef.addRef(paramBoolean);
/*      */       
/*      */ 
/*      */ 
/*  350 */       BCSSCServiceRef localBCSSCServiceRef = null;
/*  351 */       Object localObject = null;
/*      */       
/*  353 */       if (this.serviceRequestors == null) {
/*  354 */         this.serviceRequestors = new HashMap(1);
/*      */       } else {
/*  356 */         localObject = (Map)this.serviceRequestors.get(paramObject1);
/*      */       }
/*      */       
/*  359 */       if (localObject == null) {
/*  360 */         localObject = new HashMap(1);
/*      */         
/*  362 */         this.serviceRequestors.put(paramObject1, localObject);
/*      */       } else {
/*  364 */         localBCSSCServiceRef = (BCSSCServiceRef)((Map)localObject).get(paramObject2);
/*      */       }
/*  366 */       if (localBCSSCServiceRef == null) {
/*  367 */         localBCSSCServiceRef = new BCSSCServiceRef(localBCSSCServiceClassRef, paramBoolean);
/*      */         
/*  369 */         ((Map)localObject).put(paramObject2, localBCSSCServiceRef);
/*      */       } else {
/*  371 */         localBCSSCServiceRef.addRef();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     synchronized void releaseService(Object paramObject1, Object paramObject2)
/*      */     {
/*  378 */       if (this.serviceRequestors == null) { return;
/*      */       }
/*  380 */       Map localMap = (Map)this.serviceRequestors.get(paramObject1);
/*      */       
/*  382 */       if (localMap == null) { return;
/*      */       }
/*  384 */       BCSSCServiceRef localBCSSCServiceRef = (BCSSCServiceRef)localMap.get(paramObject2);
/*      */       
/*  386 */       if (localBCSSCServiceRef == null) { return;
/*      */       }
/*  388 */       BCSSCServiceClassRef localBCSSCServiceClassRef = localBCSSCServiceRef.getServiceClassRef();
/*  389 */       boolean bool = localBCSSCServiceRef.isDelegated();
/*  390 */       BeanContextServiceProvider localBeanContextServiceProvider = bool ? localBCSSCServiceClassRef.getDelegateProvider() : localBCSSCServiceClassRef.getServiceProvider();
/*      */       
/*  392 */       localBeanContextServiceProvider.releaseService(BeanContextServicesSupport.this.getBeanContextServicesPeer(), paramObject1, paramObject2);
/*      */       
/*  394 */       localBCSSCServiceClassRef.releaseRef(bool);
/*  395 */       localBCSSCServiceClassRef.removeRequestor(paramObject1);
/*      */       
/*  397 */       if (localBCSSCServiceRef.release() == 0)
/*      */       {
/*  399 */         localMap.remove(paramObject2);
/*      */         
/*  401 */         if (localMap.isEmpty()) {
/*  402 */           this.serviceRequestors.remove(paramObject1);
/*  403 */           localBCSSCServiceClassRef.removeRequestor(paramObject1);
/*      */         }
/*      */         
/*  406 */         if (this.serviceRequestors.isEmpty()) {
/*  407 */           this.serviceRequestors = null;
/*      */         }
/*      */         
/*  410 */         if (localBCSSCServiceClassRef.isEmpty()) {
/*  411 */           this.serviceClasses.remove(localBCSSCServiceClassRef.getServiceClass());
/*      */         }
/*      */         
/*  414 */         if (this.serviceClasses.isEmpty()) {
/*  415 */           this.serviceClasses = null;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     synchronized void revokeService(Class paramClass, boolean paramBoolean1, boolean paramBoolean2)
/*      */     {
/*  422 */       if (this.serviceClasses == null) { return;
/*      */       }
/*  424 */       BCSSCServiceClassRef localBCSSCServiceClassRef = (BCSSCServiceClassRef)this.serviceClasses.get(paramClass);
/*      */       
/*  426 */       if (localBCSSCServiceClassRef == null) { return;
/*      */       }
/*  428 */       Iterator localIterator1 = localBCSSCServiceClassRef.cloneOfEntries();
/*      */       
/*  430 */       BeanContextServiceRevokedEvent localBeanContextServiceRevokedEvent = new BeanContextServiceRevokedEvent(BeanContextServicesSupport.this.getBeanContextServicesPeer(), paramClass, paramBoolean2);
/*  431 */       boolean bool = false;
/*      */       
/*  433 */       while ((localIterator1.hasNext()) && (this.serviceRequestors != null)) {
/*  434 */         Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
/*  435 */         BeanContextServiceRevokedListener localBeanContextServiceRevokedListener = (BeanContextServiceRevokedListener)localEntry1.getValue();
/*      */         
/*  437 */         if (paramBoolean2) {
/*  438 */           Object localObject = localEntry1.getKey();
/*  439 */           Map localMap = (Map)this.serviceRequestors.get(localObject);
/*      */           
/*  441 */           if (localMap != null) {
/*  442 */             Iterator localIterator2 = localMap.entrySet().iterator();
/*      */             
/*  444 */             while (localIterator2.hasNext()) {
/*  445 */               Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
/*      */               
/*  447 */               BCSSCServiceRef localBCSSCServiceRef = (BCSSCServiceRef)localEntry2.getValue();
/*  448 */               if ((localBCSSCServiceRef.getServiceClassRef().equals(localBCSSCServiceClassRef)) && (paramBoolean1 == localBCSSCServiceRef.isDelegated())) {
/*  449 */                 localIterator2.remove();
/*      */               }
/*      */             }
/*      */             
/*  453 */             if ((bool = localMap.isEmpty())) {
/*  454 */               this.serviceRequestors.remove(localObject);
/*      */             }
/*      */           }
/*      */           
/*  458 */           if (bool) { localBCSSCServiceClassRef.removeRequestor(localObject);
/*      */           }
/*      */         }
/*  461 */         localBeanContextServiceRevokedListener.serviceRevoked(localBeanContextServiceRevokedEvent);
/*      */       }
/*      */       
/*  464 */       if ((paramBoolean2) && (this.serviceClasses != null)) {
/*  465 */         if (localBCSSCServiceClassRef.isEmpty()) {
/*  466 */           this.serviceClasses.remove(paramClass);
/*      */         }
/*  468 */         if (this.serviceClasses.isEmpty()) {
/*  469 */           this.serviceClasses = null;
/*      */         }
/*      */       }
/*  472 */       if ((this.serviceRequestors != null) && (this.serviceRequestors.isEmpty())) {
/*  473 */         this.serviceRequestors = null;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     void cleanupReferences()
/*      */     {
/*  480 */       if (this.serviceRequestors == null) { return;
/*      */       }
/*  482 */       Iterator localIterator1 = this.serviceRequestors.entrySet().iterator();
/*      */       
/*  484 */       while (localIterator1.hasNext()) {
/*  485 */         Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
/*  486 */         Object localObject1 = localEntry1.getKey();
/*  487 */         Iterator localIterator2 = ((Map)localEntry1.getValue()).entrySet().iterator();
/*      */         
/*  489 */         localIterator1.remove();
/*      */         
/*  491 */         while (localIterator2.hasNext()) {
/*  492 */           Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
/*  493 */           Object localObject2 = localEntry2.getKey();
/*  494 */           BCSSCServiceRef localBCSSCServiceRef = (BCSSCServiceRef)localEntry2.getValue();
/*      */           
/*  496 */           BCSSCServiceClassRef localBCSSCServiceClassRef = localBCSSCServiceRef.getServiceClassRef();
/*      */           
/*  498 */           BeanContextServiceProvider localBeanContextServiceProvider = localBCSSCServiceRef.isDelegated() ? localBCSSCServiceClassRef.getDelegateProvider() : localBCSSCServiceClassRef.getServiceProvider();
/*      */           
/*  500 */           localBCSSCServiceClassRef.removeRequestor(localObject1);
/*  501 */           localIterator2.remove();
/*      */           
/*  503 */           while (localBCSSCServiceRef.release() >= 0) {
/*  504 */             localBeanContextServiceProvider.releaseService(BeanContextServicesSupport.this.getBeanContextServicesPeer(), localObject1, localObject2);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  509 */       this.serviceRequestors = null;
/*  510 */       this.serviceClasses = null;
/*      */     }
/*      */     
/*      */     void revokeAllDelegatedServicesNow() {
/*  514 */       if (this.serviceClasses == null) { return;
/*      */       }
/*      */       
/*  517 */       Iterator localIterator1 = new HashSet(this.serviceClasses.values()).iterator();
/*      */       
/*  519 */       while (localIterator1.hasNext()) {
/*  520 */         BCSSCServiceClassRef localBCSSCServiceClassRef = (BCSSCServiceClassRef)localIterator1.next();
/*      */         
/*  522 */         if (localBCSSCServiceClassRef.isDelegated())
/*      */         {
/*  524 */           Iterator localIterator2 = localBCSSCServiceClassRef.cloneOfEntries();
/*  525 */           BeanContextServiceRevokedEvent localBeanContextServiceRevokedEvent = new BeanContextServiceRevokedEvent(BeanContextServicesSupport.this.getBeanContextServicesPeer(), localBCSSCServiceClassRef.getServiceClass(), true);
/*  526 */           boolean bool = false;
/*      */           
/*  528 */           while (localIterator2.hasNext()) {
/*  529 */             Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
/*  530 */             BeanContextServiceRevokedListener localBeanContextServiceRevokedListener = (BeanContextServiceRevokedListener)localEntry1.getValue();
/*      */             
/*  532 */             Object localObject = localEntry1.getKey();
/*  533 */             Map localMap = (Map)this.serviceRequestors.get(localObject);
/*      */             
/*  535 */             if (localMap != null) {
/*  536 */               Iterator localIterator3 = localMap.entrySet().iterator();
/*      */               
/*  538 */               while (localIterator3.hasNext()) {
/*  539 */                 Map.Entry localEntry2 = (Map.Entry)localIterator3.next();
/*      */                 
/*  541 */                 BCSSCServiceRef localBCSSCServiceRef = (BCSSCServiceRef)localEntry2.getValue();
/*  542 */                 if ((localBCSSCServiceRef.getServiceClassRef().equals(localBCSSCServiceClassRef)) && (localBCSSCServiceRef.isDelegated())) {
/*  543 */                   localIterator3.remove();
/*      */                 }
/*      */               }
/*      */               
/*  547 */               if ((bool = localMap.isEmpty())) {
/*  548 */                 this.serviceRequestors.remove(localObject);
/*      */               }
/*      */             }
/*      */             
/*  552 */             if (bool) { localBCSSCServiceClassRef.removeRequestor(localObject);
/*      */             }
/*  554 */             localBeanContextServiceRevokedListener.serviceRevoked(localBeanContextServiceRevokedEvent);
/*      */             
/*  556 */             if (localBCSSCServiceClassRef.isEmpty())
/*  557 */               this.serviceClasses.remove(localBCSSCServiceClassRef.getServiceClass());
/*      */           }
/*      */         }
/*      */       }
/*  561 */       if (this.serviceClasses.isEmpty()) { this.serviceClasses = null;
/*      */       }
/*  563 */       if ((this.serviceRequestors != null) && (this.serviceRequestors.isEmpty())) {
/*  564 */         this.serviceRequestors = null;
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BeanContextSupport.BCSChild createBCSChild(Object paramObject1, Object paramObject2)
/*      */   {
/*  587 */     return new BCSSChild(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static class BCSSServiceProvider
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 861278251667444782L;
/*      */     
/*      */ 
/*      */     protected BeanContextServiceProvider serviceProvider;
/*      */     
/*      */ 
/*      */     BCSSServiceProvider(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider)
/*      */     {
/*  603 */       this.serviceProvider = paramBeanContextServiceProvider;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected BeanContextServiceProvider getServiceProvider()
/*      */     {
/*  611 */       return this.serviceProvider;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BCSSServiceProvider createBCSSServiceProvider(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider)
/*      */   {
/*  631 */     return new BCSSServiceProvider(paramClass, paramBeanContextServiceProvider);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addBeanContextServicesListener(BeanContextServicesListener paramBeanContextServicesListener)
/*      */   {
/*  643 */     if (paramBeanContextServicesListener == null) { throw new NullPointerException("bcsl");
/*      */     }
/*  645 */     synchronized (this.bcsListeners) {
/*  646 */       if (this.bcsListeners.contains(paramBeanContextServicesListener)) {
/*  647 */         return;
/*      */       }
/*  649 */       this.bcsListeners.add(paramBeanContextServicesListener);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeBeanContextServicesListener(BeanContextServicesListener paramBeanContextServicesListener)
/*      */   {
/*  658 */     if (paramBeanContextServicesListener == null) { throw new NullPointerException("bcsl");
/*      */     }
/*  660 */     synchronized (this.bcsListeners) {
/*  661 */       if (!this.bcsListeners.contains(paramBeanContextServicesListener)) {
/*  662 */         return;
/*      */       }
/*  664 */       this.bcsListeners.remove(paramBeanContextServicesListener);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean addService(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider)
/*      */   {
/*  675 */     return addService(paramClass, paramBeanContextServiceProvider, true);
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
/*      */   protected boolean addService(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider, boolean paramBoolean)
/*      */   {
/*  688 */     if (paramClass == null) throw new NullPointerException("serviceClass");
/*  689 */     if (paramBeanContextServiceProvider == null) { throw new NullPointerException("bcsp");
/*      */     }
/*  691 */     synchronized (BeanContext.globalHierarchyLock) {
/*  692 */       if (this.services.containsKey(paramClass)) {
/*  693 */         return false;
/*      */       }
/*  695 */       this.services.put(paramClass, createBCSSServiceProvider(paramClass, paramBeanContextServiceProvider));
/*      */       
/*  697 */       if ((paramBeanContextServiceProvider instanceof Serializable)) { this.serializable += 1;
/*      */       }
/*  699 */       if (!paramBoolean) { return true;
/*      */       }
/*      */       
/*  702 */       BeanContextServiceAvailableEvent localBeanContextServiceAvailableEvent = new BeanContextServiceAvailableEvent(getBeanContextServicesPeer(), paramClass);
/*      */       
/*  704 */       fireServiceAdded(localBeanContextServiceAvailableEvent);
/*      */       
/*  706 */       synchronized (this.children) {
/*  707 */         Iterator localIterator = this.children.keySet().iterator();
/*      */         
/*  709 */         while (localIterator.hasNext()) {
/*  710 */           Object localObject1 = localIterator.next();
/*      */           
/*  712 */           if ((localObject1 instanceof BeanContextServices)) {
/*  713 */             ((BeanContextServicesListener)localObject1).serviceAvailable(localBeanContextServiceAvailableEvent);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  718 */       return true;
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
/*      */   public void revokeService(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider, boolean paramBoolean)
/*      */   {
/*  732 */     if (paramClass == null) throw new NullPointerException("serviceClass");
/*  733 */     if (paramBeanContextServiceProvider == null) { throw new NullPointerException("bcsp");
/*      */     }
/*  735 */     synchronized (BeanContext.globalHierarchyLock) {
/*  736 */       if (!this.services.containsKey(paramClass)) { return;
/*      */       }
/*  738 */       BCSSServiceProvider localBCSSServiceProvider = (BCSSServiceProvider)this.services.get(paramClass);
/*      */       
/*  740 */       if (!localBCSSServiceProvider.getServiceProvider().equals(paramBeanContextServiceProvider)) {
/*  741 */         throw new IllegalArgumentException("service provider mismatch");
/*      */       }
/*  743 */       this.services.remove(paramClass);
/*      */       
/*  745 */       if ((paramBeanContextServiceProvider instanceof Serializable)) { this.serializable -= 1;
/*      */       }
/*  747 */       Iterator localIterator = bcsChildren();
/*      */       
/*  749 */       while (localIterator.hasNext()) {
/*  750 */         ((BCSSChild)localIterator.next()).revokeService(paramClass, false, paramBoolean);
/*      */       }
/*      */       
/*  753 */       fireServiceRevoked(paramClass, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean hasService(Class paramClass)
/*      */   {
/*  762 */     if (paramClass == null) { throw new NullPointerException("serviceClass");
/*      */     }
/*  764 */     synchronized (BeanContext.globalHierarchyLock) {
/*  765 */       if (this.services.containsKey(paramClass)) { return true;
/*      */       }
/*  767 */       BeanContextServices localBeanContextServices = null;
/*      */       try
/*      */       {
/*  770 */         localBeanContextServices = (BeanContextServices)getBeanContext();
/*      */       } catch (ClassCastException localClassCastException) {
/*  772 */         return false;
/*      */       }
/*      */       
/*  775 */       return localBeanContextServices == null ? false : localBeanContextServices.hasService(paramClass);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected class BCSSProxyServiceProvider
/*      */     implements BeanContextServiceProvider, BeanContextServiceRevokedListener
/*      */   {
/*      */     private BeanContextServices nestingCtxt;
/*      */     
/*      */ 
/*      */ 
/*      */     BCSSProxyServiceProvider(BeanContextServices paramBeanContextServices)
/*      */     {
/*  791 */       this.nestingCtxt = paramBeanContextServices;
/*      */     }
/*      */     
/*      */     public Object getService(BeanContextServices paramBeanContextServices, Object paramObject1, Class paramClass, Object paramObject2) {
/*  795 */       Object localObject = null;
/*      */       try
/*      */       {
/*  798 */         localObject = this.nestingCtxt.getService(paramBeanContextServices, paramObject1, paramClass, paramObject2, this);
/*      */       } catch (TooManyListenersException localTooManyListenersException) {
/*  800 */         return null;
/*      */       }
/*      */       
/*  803 */       return localObject;
/*      */     }
/*      */     
/*      */     public void releaseService(BeanContextServices paramBeanContextServices, Object paramObject1, Object paramObject2) {
/*  807 */       this.nestingCtxt.releaseService(paramBeanContextServices, paramObject1, paramObject2);
/*      */     }
/*      */     
/*      */     public Iterator getCurrentServiceSelectors(BeanContextServices paramBeanContextServices, Class paramClass) {
/*  811 */       return this.nestingCtxt.getCurrentServiceSelectors(paramClass);
/*      */     }
/*      */     
/*      */     public void serviceRevoked(BeanContextServiceRevokedEvent paramBeanContextServiceRevokedEvent) {
/*  815 */       Iterator localIterator = BeanContextServicesSupport.this.bcsChildren();
/*      */       
/*  817 */       while (localIterator.hasNext()) {
/*  818 */         ((BeanContextServicesSupport.BCSSChild)localIterator.next()).revokeService(paramBeanContextServiceRevokedEvent.getServiceClass(), true, paramBeanContextServiceRevokedEvent.isCurrentServiceInvalidNow());
/*      */       }
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
/*      */ 
/*      */ 
/*      */   public Object getService(BeanContextChild paramBeanContextChild, Object paramObject1, Class paramClass, Object paramObject2, BeanContextServiceRevokedListener paramBeanContextServiceRevokedListener)
/*      */     throws TooManyListenersException
/*      */   {
/*  836 */     if (paramBeanContextChild == null) throw new NullPointerException("child");
/*  837 */     if (paramClass == null) throw new NullPointerException("serviceClass");
/*  838 */     if (paramObject1 == null) throw new NullPointerException("requestor");
/*  839 */     if (paramBeanContextServiceRevokedListener == null) { throw new NullPointerException("bcsrl");
/*      */     }
/*  841 */     Object localObject1 = null;
/*      */     
/*  843 */     BeanContextServices localBeanContextServices = getBeanContextServicesPeer();
/*      */     
/*  845 */     synchronized (BeanContext.globalHierarchyLock) { BCSSChild localBCSSChild;
/*  846 */       synchronized (this.children) { localBCSSChild = (BCSSChild)this.children.get(paramBeanContextChild);
/*      */       }
/*  848 */       if (localBCSSChild == null) { throw new IllegalArgumentException("not a child of this context");
/*      */       }
/*  850 */       ??? = (BCSSServiceProvider)this.services.get(paramClass);
/*      */       
/*  852 */       if (??? != null) {
/*  853 */         BeanContextServiceProvider localBeanContextServiceProvider = ((BCSSServiceProvider)???).getServiceProvider();
/*  854 */         localObject1 = localBeanContextServiceProvider.getService(localBeanContextServices, paramObject1, paramClass, paramObject2);
/*  855 */         if (localObject1 != null) {
/*      */           try {
/*  857 */             localBCSSChild.usingService(paramObject1, localObject1, paramClass, localBeanContextServiceProvider, false, paramBeanContextServiceRevokedListener);
/*      */           } catch (TooManyListenersException localTooManyListenersException2) {
/*  859 */             localBeanContextServiceProvider.releaseService(localBeanContextServices, paramObject1, localObject1);
/*  860 */             throw localTooManyListenersException2;
/*      */           } catch (UnsupportedOperationException localUnsupportedOperationException2) {
/*  862 */             localBeanContextServiceProvider.releaseService(localBeanContextServices, paramObject1, localObject1);
/*  863 */             throw localUnsupportedOperationException2;
/*      */           }
/*      */           
/*  866 */           return localObject1;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  871 */       if (this.proxy != null)
/*      */       {
/*      */ 
/*      */ 
/*  875 */         localObject1 = this.proxy.getService(localBeanContextServices, paramObject1, paramClass, paramObject2);
/*      */         
/*  877 */         if (localObject1 != null) {
/*      */           try {
/*  879 */             localBCSSChild.usingService(paramObject1, localObject1, paramClass, this.proxy, true, paramBeanContextServiceRevokedListener);
/*      */           } catch (TooManyListenersException localTooManyListenersException1) {
/*  881 */             this.proxy.releaseService(localBeanContextServices, paramObject1, localObject1);
/*  882 */             throw localTooManyListenersException1;
/*      */           } catch (UnsupportedOperationException localUnsupportedOperationException1) {
/*  884 */             this.proxy.releaseService(localBeanContextServices, paramObject1, localObject1);
/*  885 */             throw localUnsupportedOperationException1;
/*      */           }
/*      */           
/*  888 */           return localObject1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  893 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void releaseService(BeanContextChild paramBeanContextChild, Object paramObject1, Object paramObject2)
/*      */   {
/*  901 */     if (paramBeanContextChild == null) throw new NullPointerException("child");
/*  902 */     if (paramObject1 == null) throw new NullPointerException("requestor");
/*  903 */     if (paramObject2 == null) { throw new NullPointerException("service");
/*      */     }
/*      */     
/*      */ 
/*  907 */     synchronized (BeanContext.globalHierarchyLock) { BCSSChild localBCSSChild;
/*  908 */       synchronized (this.children) { localBCSSChild = (BCSSChild)this.children.get(paramBeanContextChild);
/*      */       }
/*  910 */       if (localBCSSChild != null) {
/*  911 */         localBCSSChild.releaseService(paramObject1, paramObject2);
/*      */       } else {
/*  913 */         throw new IllegalArgumentException("child actual is not a child of this BeanContext");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Iterator getCurrentServiceClasses()
/*      */   {
/*  922 */     return new BeanContextSupport.BCSIterator(this.services.keySet().iterator());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Iterator getCurrentServiceSelectors(Class paramClass)
/*      */   {
/*  932 */     BCSSServiceProvider localBCSSServiceProvider = (BCSSServiceProvider)this.services.get(paramClass);
/*      */     
/*  934 */     return localBCSSServiceProvider != null ? new BeanContextSupport.BCSIterator(localBCSSServiceProvider.getServiceProvider().getCurrentServiceSelectors(getBeanContextServicesPeer(), paramClass)) : null;
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
/*      */   public void serviceAvailable(BeanContextServiceAvailableEvent paramBeanContextServiceAvailableEvent)
/*      */   {
/*  948 */     synchronized (BeanContext.globalHierarchyLock) {
/*  949 */       if (this.services.containsKey(paramBeanContextServiceAvailableEvent.getServiceClass())) { return;
/*      */       }
/*  951 */       fireServiceAdded(paramBeanContextServiceAvailableEvent);
/*      */       
/*      */       Iterator localIterator;
/*      */       
/*  955 */       synchronized (this.children) {
/*  956 */         localIterator = this.children.keySet().iterator();
/*      */       }
/*      */       
/*  959 */       while (localIterator.hasNext()) {
/*  960 */         ??? = localIterator.next();
/*      */         
/*  962 */         if ((??? instanceof BeanContextServices)) {
/*  963 */           ((BeanContextServicesListener)???).serviceAvailable(paramBeanContextServiceAvailableEvent);
/*      */         }
/*      */       }
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
/*      */ 
/*      */   public void serviceRevoked(BeanContextServiceRevokedEvent paramBeanContextServiceRevokedEvent)
/*      */   {
/*  980 */     synchronized (BeanContext.globalHierarchyLock) {
/*  981 */       if (this.services.containsKey(paramBeanContextServiceRevokedEvent.getServiceClass())) { return;
/*      */       }
/*  983 */       fireServiceRevoked(paramBeanContextServiceRevokedEvent);
/*      */       
/*      */       Iterator localIterator;
/*      */       
/*  987 */       synchronized (this.children) {
/*  988 */         localIterator = this.children.keySet().iterator();
/*      */       }
/*      */       
/*  991 */       while (localIterator.hasNext()) {
/*  992 */         ??? = localIterator.next();
/*      */         
/*  994 */         if ((??? instanceof BeanContextServices)) {
/*  995 */           ((BeanContextServicesListener)???).serviceRevoked(paramBeanContextServiceRevokedEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final BeanContextServicesListener getChildBeanContextServicesListener(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1010 */       return (BeanContextServicesListener)paramObject;
/*      */     } catch (ClassCastException localClassCastException) {}
/* 1012 */     return null;
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
/*      */   protected void childJustRemovedHook(Object paramObject, BeanContextSupport.BCSChild paramBCSChild)
/*      */   {
/* 1028 */     BCSSChild localBCSSChild = (BCSSChild)paramBCSChild;
/*      */     
/* 1030 */     localBCSSChild.cleanupReferences();
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
/*      */   protected synchronized void releaseBeanContextResources()
/*      */   {
/* 1045 */     super.releaseBeanContextResources();
/*      */     Object[] arrayOfObject;
/* 1047 */     synchronized (this.children) {
/* 1048 */       if (this.children.isEmpty()) { return;
/*      */       }
/* 1050 */       arrayOfObject = this.children.values().toArray();
/*      */     }
/*      */     
/*      */ 
/* 1054 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 1055 */       ((BCSSChild)arrayOfObject[i]).revokeAllDelegatedServicesNow();
/*      */     }
/*      */     
/* 1058 */     this.proxy = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected synchronized void initializeBeanContextResources()
/*      */   {
/* 1069 */     super.initializeBeanContextResources();
/*      */     
/* 1071 */     BeanContext localBeanContext = getBeanContext();
/*      */     
/* 1073 */     if (localBeanContext == null) return;
/*      */     try
/*      */     {
/* 1076 */       BeanContextServices localBeanContextServices = (BeanContextServices)localBeanContext;
/*      */       
/* 1078 */       this.proxy = new BCSSProxyServiceProvider(localBeanContextServices);
/*      */     }
/*      */     catch (ClassCastException localClassCastException) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void fireServiceAdded(Class paramClass)
/*      */   {
/* 1089 */     BeanContextServiceAvailableEvent localBeanContextServiceAvailableEvent = new BeanContextServiceAvailableEvent(getBeanContextServicesPeer(), paramClass);
/*      */     
/* 1091 */     fireServiceAdded(localBeanContextServiceAvailableEvent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void fireServiceAdded(BeanContextServiceAvailableEvent paramBeanContextServiceAvailableEvent)
/*      */   {
/*      */     Object[] arrayOfObject;
/*      */     
/*      */ 
/*      */ 
/* 1103 */     synchronized (this.bcsListeners) { arrayOfObject = this.bcsListeners.toArray();
/*      */     }
/* 1105 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 1106 */       ((BeanContextServicesListener)arrayOfObject[i]).serviceAvailable(paramBeanContextServiceAvailableEvent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void fireServiceRevoked(BeanContextServiceRevokedEvent paramBeanContextServiceRevokedEvent)
/*      */   {
/*      */     Object[] arrayOfObject;
/*      */     
/*      */ 
/* 1118 */     synchronized (this.bcsListeners) { arrayOfObject = this.bcsListeners.toArray();
/*      */     }
/* 1120 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 1121 */       ((BeanContextServiceRevokedListener)arrayOfObject[i]).serviceRevoked(paramBeanContextServiceRevokedEvent);
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
/*      */   protected final void fireServiceRevoked(Class paramClass, boolean paramBoolean)
/*      */   {
/* 1134 */     BeanContextServiceRevokedEvent localBeanContextServiceRevokedEvent = new BeanContextServiceRevokedEvent(getBeanContextServicesPeer(), paramClass, paramBoolean);
/*      */     Object[] arrayOfObject;
/* 1136 */     synchronized (this.bcsListeners) { arrayOfObject = this.bcsListeners.toArray();
/*      */     }
/* 1138 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 1139 */       ((BeanContextServicesListener)arrayOfObject[i]).serviceRevoked(localBeanContextServiceRevokedEvent);
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
/*      */ 
/*      */ 
/*      */   protected synchronized void bcsPreSerializationHook(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1156 */     paramObjectOutputStream.writeInt(this.serializable);
/*      */     
/* 1158 */     if (this.serializable <= 0) { return;
/*      */     }
/* 1160 */     int i = 0;
/*      */     
/* 1162 */     Iterator localIterator = this.services.entrySet().iterator();
/*      */     
/* 1164 */     while ((localIterator.hasNext()) && (i < this.serializable)) {
/* 1165 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 1166 */       BCSSServiceProvider localBCSSServiceProvider = null;
/*      */       try
/*      */       {
/* 1169 */         localBCSSServiceProvider = (BCSSServiceProvider)localEntry.getValue();
/*      */       } catch (ClassCastException localClassCastException) {}
/* 1171 */       continue;
/*      */       
/*      */ 
/* 1174 */       if ((localBCSSServiceProvider.getServiceProvider() instanceof Serializable)) {
/* 1175 */         paramObjectOutputStream.writeObject(localEntry.getKey());
/* 1176 */         paramObjectOutputStream.writeObject(localBCSSServiceProvider);
/* 1177 */         i++;
/*      */       }
/*      */     }
/*      */     
/* 1181 */     if (i != this.serializable) {
/* 1182 */       throw new IOException("wrote different number of service providers than expected");
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
/*      */ 
/*      */ 
/*      */   protected synchronized void bcsPreDeserializationHook(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1199 */     this.serializable = paramObjectInputStream.readInt();
/*      */     
/* 1201 */     int i = this.serializable;
/*      */     
/* 1203 */     while (i > 0) {
/* 1204 */       this.services.put(paramObjectInputStream.readObject(), paramObjectInputStream.readObject());
/* 1205 */       i--;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1214 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1216 */     serialize(paramObjectOutputStream, this.bcsListeners);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1225 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1227 */     deserialize(paramObjectInputStream, this.bcsListeners);
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
/* 1244 */   protected transient int serializable = 0;
/*      */   protected transient BCSSProxyServiceProvider proxy;
/*      */   protected transient ArrayList bcsListeners;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/beancontext/BeanContextServicesSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */