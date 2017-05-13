/*      */ package java.beans.beancontext;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.beans.Beans;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyVetoException;
/*      */ import java.beans.VetoableChangeListener;
/*      */ import java.beans.Visibility;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BeanContextSupport
/*      */   extends BeanContextChildSupport
/*      */   implements BeanContext, Serializable, PropertyChangeListener, VetoableChangeListener
/*      */ {
/*      */   static final long serialVersionUID = -4879613978649577204L;
/*      */   protected transient HashMap children;
/*      */   
/*      */   public BeanContextSupport(BeanContext paramBeanContext, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  103 */     super(paramBeanContext);
/*      */     
/*  105 */     this.locale = (paramLocale != null ? paramLocale : Locale.getDefault());
/*  106 */     this.designTime = paramBoolean1;
/*  107 */     this.okToUseGui = paramBoolean2;
/*      */     
/*  109 */     initialize();
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
/*      */   public BeanContextSupport(BeanContext paramBeanContext, Locale paramLocale, boolean paramBoolean)
/*      */   {
/*  128 */     this(paramBeanContext, paramLocale, paramBoolean, true);
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
/*      */   public BeanContextSupport(BeanContext paramBeanContext, Locale paramLocale)
/*      */   {
/*  148 */     this(paramBeanContext, paramLocale, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextSupport(BeanContext paramBeanContext)
/*      */   {
/*  160 */     this(paramBeanContext, null, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContextSupport()
/*      */   {
/*  168 */     this(null, null, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public BeanContext getBeanContextPeer()
/*      */   {
/*  176 */     return (BeanContext)getBeanContextChildPeer();
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
/*      */   public Object instantiateChild(String paramString)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  197 */     BeanContext localBeanContext = getBeanContextPeer();
/*      */     
/*  199 */     return Beans.instantiate(localBeanContext.getClass().getClassLoader(), paramString, localBeanContext);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public int size()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: invokevirtual 16	java/util/HashMap:size	()I
/*      */     //   14: aload_1
/*      */     //   15: monitorexit
/*      */     //   16: ireturn
/*      */     //   17: astore_2
/*      */     //   18: aload_1
/*      */     //   19: monitorexit
/*      */     //   20: aload_2
/*      */     //   21: athrow
/*      */     // Line number table:
/*      */     //   Java source line #209	-> byte code offset #0
/*      */     //   Java source line #210	-> byte code offset #7
/*      */     //   Java source line #211	-> byte code offset #17
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	22	0	this	BeanContextSupport
/*      */     //   5	14	1	Ljava/lang/Object;	Object
/*      */     //   17	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	16	17	finally
/*      */     //   17	20	17	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean isEmpty()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: invokevirtual 17	java/util/HashMap:isEmpty	()Z
/*      */     //   14: aload_1
/*      */     //   15: monitorexit
/*      */     //   16: ireturn
/*      */     //   17: astore_2
/*      */     //   18: aload_1
/*      */     //   19: monitorexit
/*      */     //   20: aload_2
/*      */     //   21: athrow
/*      */     // Line number table:
/*      */     //   Java source line #223	-> byte code offset #0
/*      */     //   Java source line #224	-> byte code offset #7
/*      */     //   Java source line #225	-> byte code offset #17
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	22	0	this	BeanContextSupport
/*      */     //   5	14	1	Ljava/lang/Object;	Object
/*      */     //   17	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	16	17	finally
/*      */     //   17	20	17	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean contains(Object paramObject)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_2
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: aload_1
/*      */     //   12: invokevirtual 18	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
/*      */     //   15: aload_2
/*      */     //   16: monitorexit
/*      */     //   17: ireturn
/*      */     //   18: astore_3
/*      */     //   19: aload_2
/*      */     //   20: monitorexit
/*      */     //   21: aload_3
/*      */     //   22: athrow
/*      */     // Line number table:
/*      */     //   Java source line #235	-> byte code offset #0
/*      */     //   Java source line #236	-> byte code offset #7
/*      */     //   Java source line #237	-> byte code offset #18
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	23	0	this	BeanContextSupport
/*      */     //   0	23	1	paramObject	Object
/*      */     //   5	15	2	Ljava/lang/Object;	Object
/*      */     //   18	4	3	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	17	18	finally
/*      */     //   18	21	18	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean containsKey(Object paramObject)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_2
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: aload_1
/*      */     //   12: invokevirtual 18	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
/*      */     //   15: aload_2
/*      */     //   16: monitorexit
/*      */     //   17: ireturn
/*      */     //   18: astore_3
/*      */     //   19: aload_2
/*      */     //   20: monitorexit
/*      */     //   21: aload_3
/*      */     //   22: athrow
/*      */     // Line number table:
/*      */     //   Java source line #247	-> byte code offset #0
/*      */     //   Java source line #248	-> byte code offset #7
/*      */     //   Java source line #249	-> byte code offset #18
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	23	0	this	BeanContextSupport
/*      */     //   0	23	1	paramObject	Object
/*      */     //   5	15	2	Ljava/lang/Object;	Object
/*      */     //   18	4	3	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	17	18	finally
/*      */     //   18	21	18	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public Iterator iterator()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: new 19	java/beans/beancontext/BeanContextSupport$BCSIterator
/*      */     //   10: dup
/*      */     //   11: aload_0
/*      */     //   12: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   15: invokevirtual 20	java/util/HashMap:keySet	()Ljava/util/Set;
/*      */     //   18: invokeinterface 21 1 0
/*      */     //   23: invokespecial 22	java/beans/beancontext/BeanContextSupport$BCSIterator:<init>	(Ljava/util/Iterator;)V
/*      */     //   26: aload_1
/*      */     //   27: monitorexit
/*      */     //   28: areturn
/*      */     //   29: astore_2
/*      */     //   30: aload_1
/*      */     //   31: monitorexit
/*      */     //   32: aload_2
/*      */     //   33: athrow
/*      */     // Line number table:
/*      */     //   Java source line #258	-> byte code offset #0
/*      */     //   Java source line #259	-> byte code offset #7
/*      */     //   Java source line #260	-> byte code offset #29
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	34	0	this	BeanContextSupport
/*      */     //   5	26	1	Ljava/lang/Object;	Object
/*      */     //   29	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	28	29	finally
/*      */     //   29	32	29	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public Object[] toArray()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: invokevirtual 20	java/util/HashMap:keySet	()Ljava/util/Set;
/*      */     //   14: invokeinterface 23 1 0
/*      */     //   19: aload_1
/*      */     //   20: monitorexit
/*      */     //   21: areturn
/*      */     //   22: astore_2
/*      */     //   23: aload_1
/*      */     //   24: monitorexit
/*      */     //   25: aload_2
/*      */     //   26: athrow
/*      */     // Line number table:
/*      */     //   Java source line #268	-> byte code offset #0
/*      */     //   Java source line #269	-> byte code offset #7
/*      */     //   Java source line #270	-> byte code offset #22
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	27	0	this	BeanContextSupport
/*      */     //   5	19	1	Ljava/lang/Object;	Object
/*      */     //   22	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	21	22	finally
/*      */     //   22	25	22	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public Object[] toArray(Object[] paramArrayOfObject)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_2
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: invokevirtual 20	java/util/HashMap:keySet	()Ljava/util/Set;
/*      */     //   14: aload_1
/*      */     //   15: invokeinterface 24 2 0
/*      */     //   20: aload_2
/*      */     //   21: monitorexit
/*      */     //   22: areturn
/*      */     //   23: astore_3
/*      */     //   24: aload_2
/*      */     //   25: monitorexit
/*      */     //   26: aload_3
/*      */     //   27: athrow
/*      */     // Line number table:
/*      */     //   Java source line #282	-> byte code offset #0
/*      */     //   Java source line #283	-> byte code offset #7
/*      */     //   Java source line #284	-> byte code offset #23
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	28	0	this	BeanContextSupport
/*      */     //   0	28	1	paramArrayOfObject	Object[]
/*      */     //   5	20	2	Ljava/lang/Object;	Object
/*      */     //   23	4	3	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	22	23	finally
/*      */     //   23	26	23	finally
/*      */   }
/*      */   
/*      */   protected static final class BCSIterator
/*      */     implements Iterator
/*      */   {
/*      */     private Iterator src;
/*      */     
/*  296 */     BCSIterator(Iterator paramIterator) { this.src = paramIterator; }
/*      */     
/*  298 */     public boolean hasNext() { return this.src.hasNext(); }
/*  299 */     public Object next() { return this.src.next(); }
/*      */     
/*      */ 
/*      */ 
/*      */     public void remove() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected class BCSChild
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5815286101609939109L;
/*      */     
/*      */ 
/*      */     private Object child;
/*      */     
/*      */     private Object proxyPeer;
/*      */     
/*      */     private transient boolean removePending;
/*      */     
/*      */ 
/*      */     BCSChild(Object paramObject1, Object paramObject2)
/*      */     {
/*  323 */       this.child = paramObject1;
/*  324 */       this.proxyPeer = paramObject2;
/*      */     }
/*      */     
/*  327 */     Object getChild() { return this.child; }
/*      */     
/*  329 */     void setRemovePending(boolean paramBoolean) { this.removePending = paramBoolean; }
/*      */     
/*  331 */     boolean isRemovePending() { return this.removePending; }
/*      */     
/*  333 */     boolean isProxyPeer() { return this.proxyPeer != null; }
/*      */     
/*  335 */     Object getProxyPeer() { return this.proxyPeer; }
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
/*      */   protected BCSChild createBCSChild(Object paramObject1, Object paramObject2)
/*      */   {
/*  358 */     return new BCSChild(paramObject1, paramObject2);
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
/*      */   public boolean add(Object paramObject)
/*      */   {
/*  379 */     if (paramObject == null) { throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  384 */     if (this.children.containsKey(paramObject)) { return false;
/*      */     }
/*  386 */     synchronized (BeanContext.globalHierarchyLock) {
/*  387 */       if (this.children.containsKey(paramObject)) { return false;
/*      */       }
/*  389 */       if (!validatePendingAdd(paramObject)) {
/*  390 */         throw new IllegalStateException();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  397 */       BeanContextChild localBeanContextChild1 = getChildBeanContextChild(paramObject);
/*  398 */       BeanContextChild localBeanContextChild2 = null;
/*      */       
/*  400 */       synchronized (paramObject)
/*      */       {
/*  402 */         if ((paramObject instanceof BeanContextProxy)) {
/*  403 */           localBeanContextChild2 = ((BeanContextProxy)paramObject).getBeanContextProxy();
/*      */           
/*  405 */           if (localBeanContextChild2 == null) { throw new NullPointerException("BeanContextPeer.getBeanContextProxy()");
/*      */           }
/*      */         }
/*  408 */         BCSChild localBCSChild1 = createBCSChild(paramObject, localBeanContextChild2);
/*  409 */         BCSChild localBCSChild2 = null;
/*      */         
/*  411 */         synchronized (this.children) {
/*  412 */           this.children.put(paramObject, localBCSChild1);
/*      */           
/*  414 */           if (localBeanContextChild2 != null) { this.children.put(localBeanContextChild2, localBCSChild2 = createBCSChild(localBeanContextChild2, paramObject));
/*      */           }
/*      */         }
/*  417 */         if (localBeanContextChild1 != null) { synchronized (localBeanContextChild1) {
/*      */             try {
/*  419 */               localBeanContextChild1.setBeanContext(getBeanContextPeer());
/*      */             }
/*      */             catch (PropertyVetoException localPropertyVetoException) {
/*  422 */               synchronized (this.children) {
/*  423 */                 this.children.remove(paramObject);
/*      */                 
/*  425 */                 if (localBeanContextChild2 != null) { this.children.remove(localBeanContextChild2);
/*      */                 }
/*      */               }
/*  428 */               throw new IllegalStateException();
/*      */             }
/*      */             
/*  431 */             localBeanContextChild1.addPropertyChangeListener("beanContext", this.childPCL);
/*  432 */             localBeanContextChild1.addVetoableChangeListener("beanContext", this.childVCL);
/*      */           }
/*      */         }
/*  435 */         ??? = getChildVisibility(paramObject);
/*      */         
/*  437 */         if (??? != null) {
/*  438 */           if (this.okToUseGui) {
/*  439 */             ((Visibility)???).okToUseGui();
/*      */           } else {
/*  441 */             ((Visibility)???).dontUseGui();
/*      */           }
/*      */         }
/*  444 */         if (getChildSerializable(paramObject) != null) { this.serializable += 1;
/*      */         }
/*  446 */         childJustAddedHook(paramObject, localBCSChild1);
/*      */         
/*  448 */         if (localBeanContextChild2 != null) {
/*  449 */           ??? = getChildVisibility(localBeanContextChild2);
/*      */           
/*  451 */           if (??? != null) {
/*  452 */             if (this.okToUseGui) {
/*  453 */               ((Visibility)???).okToUseGui();
/*      */             } else {
/*  455 */               ((Visibility)???).dontUseGui();
/*      */             }
/*      */           }
/*  458 */           if (getChildSerializable(localBeanContextChild2) != null) { this.serializable += 1;
/*      */           }
/*  460 */           childJustAddedHook(localBeanContextChild2, localBCSChild2);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  468 */       fireChildrenAdded(new BeanContextMembershipEvent(getBeanContextPeer(), new Object[] { paramObject, localBeanContextChild2 == null ? new Object[] { paramObject } : localBeanContextChild2 }));
/*      */     }
/*      */     
/*      */ 
/*  472 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*  482 */     return remove(paramObject, true);
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
/*      */   protected boolean remove(Object paramObject, boolean paramBoolean)
/*      */   {
/*  497 */     if (paramObject == null) { throw new IllegalArgumentException();
/*      */     }
/*  499 */     synchronized (BeanContext.globalHierarchyLock) {
/*  500 */       if (!containsKey(paramObject)) { return false;
/*      */       }
/*  502 */       if (!validatePendingRemove(paramObject)) {
/*  503 */         throw new IllegalStateException();
/*      */       }
/*      */       
/*  506 */       BCSChild localBCSChild1 = (BCSChild)this.children.get(paramObject);
/*  507 */       BCSChild localBCSChild2 = null;
/*  508 */       Object localObject1 = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  513 */       synchronized (paramObject) {
/*  514 */         if (paramBoolean) {
/*  515 */           BeanContextChild localBeanContextChild = getChildBeanContextChild(paramObject);
/*  516 */           if (localBeanContextChild != null) { synchronized (localBeanContextChild) {
/*  517 */               localBeanContextChild.removePropertyChangeListener("beanContext", this.childPCL);
/*  518 */               localBeanContextChild.removeVetoableChangeListener("beanContext", this.childVCL);
/*      */               try
/*      */               {
/*  521 */                 localBeanContextChild.setBeanContext(null);
/*      */               } catch (PropertyVetoException localPropertyVetoException) {
/*  523 */                 localBeanContextChild.addPropertyChangeListener("beanContext", this.childPCL);
/*  524 */                 localBeanContextChild.addVetoableChangeListener("beanContext", this.childVCL);
/*  525 */                 throw new IllegalStateException();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  531 */         synchronized (this.children) {
/*  532 */           this.children.remove(paramObject);
/*      */           
/*  534 */           if (localBCSChild1.isProxyPeer()) {
/*  535 */             localBCSChild2 = (BCSChild)this.children.get(localObject1 = localBCSChild1.getProxyPeer());
/*  536 */             this.children.remove(localObject1);
/*      */           }
/*      */         }
/*      */         
/*  540 */         if (getChildSerializable(paramObject) != null) { this.serializable -= 1;
/*      */         }
/*  542 */         childJustRemovedHook(paramObject, localBCSChild1);
/*      */         
/*  544 */         if (localObject1 != null) {
/*  545 */           if (getChildSerializable(localObject1) != null) { this.serializable -= 1;
/*      */           }
/*  547 */           childJustRemovedHook(localObject1, localBCSChild2);
/*      */         }
/*      */       }
/*      */       
/*  551 */       fireChildrenRemoved(new BeanContextMembershipEvent(getBeanContextPeer(), new Object[] { paramObject, localObject1 == null ? new Object[] { paramObject } : localObject1 }));
/*      */     }
/*      */     
/*      */ 
/*  555 */     return true;
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
/*      */   public boolean containsAll(Collection paramCollection)
/*      */   {
/*  569 */     synchronized (this.children) {
/*  570 */       Iterator localIterator = paramCollection.iterator();
/*  571 */       while (localIterator.hasNext()) {
/*  572 */         if (!contains(localIterator.next()))
/*  573 */           return false;
/*      */       }
/*  575 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean addAll(Collection paramCollection)
/*      */   {
/*  586 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean removeAll(Collection paramCollection)
/*      */   {
/*  597 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean retainAll(Collection paramCollection)
/*      */   {
/*  608 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  617 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addBeanContextMembershipListener(BeanContextMembershipListener paramBeanContextMembershipListener)
/*      */   {
/*  628 */     if (paramBeanContextMembershipListener == null) { throw new NullPointerException("listener");
/*      */     }
/*  630 */     synchronized (this.bcmListeners) {
/*  631 */       if (this.bcmListeners.contains(paramBeanContextMembershipListener)) {
/*  632 */         return;
/*      */       }
/*  634 */       this.bcmListeners.add(paramBeanContextMembershipListener);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeBeanContextMembershipListener(BeanContextMembershipListener paramBeanContextMembershipListener)
/*      */   {
/*  646 */     if (paramBeanContextMembershipListener == null) { throw new NullPointerException("listener");
/*      */     }
/*  648 */     synchronized (this.bcmListeners) {
/*  649 */       if (!this.bcmListeners.contains(paramBeanContextMembershipListener)) {
/*  650 */         return;
/*      */       }
/*  652 */       this.bcmListeners.remove(paramBeanContextMembershipListener);
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
/*      */   public InputStream getResourceAsStream(String paramString, BeanContextChild paramBeanContextChild)
/*      */   {
/*  665 */     if (paramString == null) throw new NullPointerException("name");
/*  666 */     if (paramBeanContextChild == null) { throw new NullPointerException("bcc");
/*      */     }
/*  668 */     if (containsKey(paramBeanContextChild)) {
/*  669 */       ClassLoader localClassLoader = paramBeanContextChild.getClass().getClassLoader();
/*      */       
/*      */ 
/*  672 */       return localClassLoader != null ? localClassLoader.getResourceAsStream(paramString) : ClassLoader.getSystemResourceAsStream(paramString); }
/*  673 */     throw new IllegalArgumentException("Not a valid child");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public URL getResource(String paramString, BeanContextChild paramBeanContextChild)
/*      */   {
/*  684 */     if (paramString == null) throw new NullPointerException("name");
/*  685 */     if (paramBeanContextChild == null) { throw new NullPointerException("bcc");
/*      */     }
/*  687 */     if (containsKey(paramBeanContextChild)) {
/*  688 */       ClassLoader localClassLoader = paramBeanContextChild.getClass().getClassLoader();
/*      */       
/*      */ 
/*  691 */       return localClassLoader != null ? localClassLoader.getResource(paramString) : ClassLoader.getSystemResource(paramString); }
/*  692 */     throw new IllegalArgumentException("Not a valid child");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void setDesignTime(boolean paramBoolean)
/*      */   {
/*  700 */     if (this.designTime != paramBoolean) {
/*  701 */       this.designTime = paramBoolean;
/*      */       
/*  703 */       firePropertyChange("designMode", Boolean.valueOf(!paramBoolean), Boolean.valueOf(paramBoolean));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isDesignTime()
/*      */   {
/*  714 */     return this.designTime;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void setLocale(Locale paramLocale)
/*      */     throws PropertyVetoException
/*      */   {
/*  724 */     if ((this.locale != null) && (!this.locale.equals(paramLocale)) && (paramLocale != null)) {
/*  725 */       Locale localLocale = this.locale;
/*      */       
/*  727 */       fireVetoableChange("locale", localLocale, paramLocale);
/*      */       
/*  729 */       this.locale = paramLocale;
/*      */       
/*  731 */       firePropertyChange("locale", localLocale, paramLocale);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Locale getLocale()
/*      */   {
/*  740 */     return this.locale;
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
/*      */   public synchronized boolean needsGui()
/*      */   {
/*  755 */     BeanContext localBeanContext = getBeanContextPeer();
/*      */     
/*  757 */     if (localBeanContext != this) {
/*  758 */       if ((localBeanContext instanceof Visibility)) { return localBeanContext.needsGui();
/*      */       }
/*  760 */       if (((localBeanContext instanceof Container)) || ((localBeanContext instanceof Component)))
/*  761 */         return true;
/*      */     }
/*      */     Iterator localIterator;
/*  764 */     synchronized (this.children) {
/*  765 */       for (localIterator = this.children.keySet().iterator(); localIterator.hasNext();) {
/*  766 */         Object localObject1 = localIterator.next();
/*      */         try
/*      */         {
/*  769 */           return ((Visibility)localObject1).needsGui();
/*      */ 
/*      */         }
/*      */         catch (ClassCastException localClassCastException)
/*      */         {
/*  774 */           if (((localObject1 instanceof Container)) || ((localObject1 instanceof Component)))
/*  775 */             return true;
/*      */         }
/*      */       }
/*      */     }
/*  779 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void dontUseGui()
/*      */   {
/*  787 */     if (this.okToUseGui) {
/*  788 */       this.okToUseGui = false;
/*      */       
/*      */       Iterator localIterator;
/*  791 */       synchronized (this.children) {
/*  792 */         for (localIterator = this.children.keySet().iterator(); localIterator.hasNext();) {
/*  793 */           Visibility localVisibility = getChildVisibility(localIterator.next());
/*      */           
/*  795 */           if (localVisibility != null) { localVisibility.dontUseGui();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized void okToUseGui()
/*      */   {
/*  806 */     if (!this.okToUseGui) {
/*  807 */       this.okToUseGui = true;
/*      */       
/*      */       Iterator localIterator;
/*  810 */       synchronized (this.children) {
/*  811 */         for (localIterator = this.children.keySet().iterator(); localIterator.hasNext();) {
/*  812 */           Visibility localVisibility = getChildVisibility(localIterator.next());
/*      */           
/*  814 */           if (localVisibility != null) { localVisibility.okToUseGui();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean avoidingGui()
/*      */   {
/*  827 */     return (!this.okToUseGui) && (needsGui());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isSerializing()
/*      */   {
/*  836 */     return this.serializing;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   protected Iterator bcsChildren()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: invokevirtual 100	java/util/HashMap:values	()Ljava/util/Collection;
/*      */     //   14: invokeinterface 68 1 0
/*      */     //   19: aload_1
/*      */     //   20: monitorexit
/*      */     //   21: areturn
/*      */     //   22: astore_2
/*      */     //   23: aload_1
/*      */     //   24: monitorexit
/*      */     //   25: aload_2
/*      */     //   26: athrow
/*      */     // Line number table:
/*      */     //   Java source line #843	-> byte code offset #0
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	27	0	this	BeanContextSupport
/*      */     //   5	19	1	Ljava/lang/Object;	Object
/*      */     //   22	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	21	22	finally
/*      */     //   22	25	22	finally
/*      */   }
/*      */   
/*      */   protected void bcsPreSerializationHook(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {}
/*      */   
/*      */   protected void bcsPreDeserializationHook(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {}
/*      */   
/*      */   protected void childDeserializedHook(Object paramObject, BCSChild paramBCSChild)
/*      */   {
/*  886 */     synchronized (this.children) {
/*  887 */       this.children.put(paramObject, paramBCSChild);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void serialize(ObjectOutputStream paramObjectOutputStream, Collection paramCollection)
/*      */     throws IOException
/*      */   {
/*  899 */     int i = 0;
/*  900 */     Object[] arrayOfObject = paramCollection.toArray();
/*      */     
/*  902 */     for (int j = 0; j < arrayOfObject.length; j++) {
/*  903 */       if ((arrayOfObject[j] instanceof Serializable)) {
/*  904 */         i++;
/*      */       } else {
/*  906 */         arrayOfObject[j] = null;
/*      */       }
/*      */     }
/*  909 */     paramObjectOutputStream.writeInt(i);
/*      */     
/*  911 */     for (j = 0; i > 0; j++) {
/*  912 */       Object localObject = arrayOfObject[j];
/*      */       
/*  914 */       if (localObject != null) {
/*  915 */         paramObjectOutputStream.writeObject(localObject);
/*  916 */         i--;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void deserialize(ObjectInputStream paramObjectInputStream, Collection paramCollection)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  929 */     int i = 0;
/*      */     
/*  931 */     i = paramObjectInputStream.readInt();
/*      */     
/*  933 */     while (i-- > 0) {
/*  934 */       paramCollection.add(paramObjectInputStream.readObject());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeChildren(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*  946 */     if (this.serializable <= 0) { return;
/*      */     }
/*  948 */     boolean bool = this.serializing;
/*      */     
/*  950 */     this.serializing = true;
/*      */     
/*  952 */     int i = 0;
/*      */     
/*  954 */     synchronized (this.children) {
/*  955 */       Iterator localIterator = this.children.entrySet().iterator();
/*      */       
/*  957 */       while ((localIterator.hasNext()) && (i < this.serializable)) {
/*  958 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/*      */         
/*  960 */         if ((localEntry.getKey() instanceof Serializable)) {
/*      */           try {
/*  962 */             paramObjectOutputStream.writeObject(localEntry.getKey());
/*  963 */             paramObjectOutputStream.writeObject(localEntry.getValue());
/*      */           } catch (IOException localIOException) {
/*  965 */             this.serializing = bool;
/*  966 */             throw localIOException;
/*      */           }
/*  968 */           i++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  973 */     this.serializing = bool;
/*      */     
/*  975 */     if (i != this.serializable) {
/*  976 */       throw new IOException("wrote different number of children than expected");
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: iconst_1
/*      */     //   2: putfield 99	java/beans/beancontext/BeanContextSupport:serializing	Z
/*      */     //   5: getstatic 29	java/beans/beancontext/BeanContext:globalHierarchyLock	Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore_2
/*      */     //   10: monitorenter
/*      */     //   11: aload_1
/*      */     //   12: invokevirtual 115	java/io/ObjectOutputStream:defaultWriteObject	()V
/*      */     //   15: aload_0
/*      */     //   16: aload_1
/*      */     //   17: invokevirtual 116	java/beans/beancontext/BeanContextSupport:bcsPreSerializationHook	(Ljava/io/ObjectOutputStream;)V
/*      */     //   20: aload_0
/*      */     //   21: getfield 2	java/beans/beancontext/BeanContextSupport:serializable	I
/*      */     //   24: ifle +19 -> 43
/*      */     //   27: aload_0
/*      */     //   28: aload_0
/*      */     //   29: invokevirtual 11	java/beans/beancontext/BeanContextSupport:getBeanContextPeer	()Ljava/beans/beancontext/BeanContext;
/*      */     //   32: invokevirtual 117	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*      */     //   35: ifeq +8 -> 43
/*      */     //   38: aload_0
/*      */     //   39: aload_1
/*      */     //   40: invokevirtual 118	java/beans/beancontext/BeanContextSupport:writeChildren	(Ljava/io/ObjectOutputStream;)V
/*      */     //   43: aload_0
/*      */     //   44: aload_1
/*      */     //   45: aload_0
/*      */     //   46: getfield 75	java/beans/beancontext/BeanContextSupport:bcmListeners	Ljava/util/ArrayList;
/*      */     //   49: invokevirtual 119	java/beans/beancontext/BeanContextSupport:serialize	(Ljava/io/ObjectOutputStream;Ljava/util/Collection;)V
/*      */     //   52: aload_0
/*      */     //   53: iconst_0
/*      */     //   54: putfield 99	java/beans/beancontext/BeanContextSupport:serializing	Z
/*      */     //   57: goto +11 -> 68
/*      */     //   60: astore_3
/*      */     //   61: aload_0
/*      */     //   62: iconst_0
/*      */     //   63: putfield 99	java/beans/beancontext/BeanContextSupport:serializing	Z
/*      */     //   66: aload_3
/*      */     //   67: athrow
/*      */     //   68: aload_2
/*      */     //   69: monitorexit
/*      */     //   70: goto +10 -> 80
/*      */     //   73: astore 4
/*      */     //   75: aload_2
/*      */     //   76: monitorexit
/*      */     //   77: aload 4
/*      */     //   79: athrow
/*      */     //   80: return
/*      */     // Line number table:
/*      */     //   Java source line #996	-> byte code offset #0
/*      */     //   Java source line #998	-> byte code offset #5
/*      */     //   Java source line #1000	-> byte code offset #11
/*      */     //   Java source line #1002	-> byte code offset #15
/*      */     //   Java source line #1004	-> byte code offset #20
/*      */     //   Java source line #1005	-> byte code offset #38
/*      */     //   Java source line #1007	-> byte code offset #43
/*      */     //   Java source line #1009	-> byte code offset #52
/*      */     //   Java source line #1010	-> byte code offset #57
/*      */     //   Java source line #1009	-> byte code offset #60
/*      */     //   Java source line #1011	-> byte code offset #68
/*      */     //   Java source line #1012	-> byte code offset #80
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	81	0	this	BeanContextSupport
/*      */     //   0	81	1	paramObjectOutputStream	ObjectOutputStream
/*      */     //   9	67	2	Ljava/lang/Object;	Object
/*      */     //   60	7	3	localObject1	Object
/*      */     //   73	5	4	localObject2	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   11	52	60	finally
/*      */     //   11	70	73	finally
/*      */     //   73	77	73	finally
/*      */   }
/*      */   
/*      */   public final void readChildren(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1024 */     int i = this.serializable;
/*      */     
/* 1026 */     while (i-- > 0) {
/* 1027 */       Object localObject1 = null;
/* 1028 */       BCSChild localBCSChild = null;
/*      */       try
/*      */       {
/* 1031 */         localObject1 = paramObjectInputStream.readObject();
/* 1032 */         localBCSChild = (BCSChild)paramObjectInputStream.readObject();
/*      */       } catch (IOException localIOException) {
/*      */         continue;
/*      */       } catch (ClassNotFoundException localClassNotFoundException) {}
/* 1036 */       continue;
/*      */       
/*      */ 
/*      */ 
/* 1040 */       synchronized (localObject1) {
/* 1041 */         BeanContextChild localBeanContextChild = null;
/*      */         try
/*      */         {
/* 1044 */           localBeanContextChild = (BeanContextChild)localObject1;
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {}
/*      */         
/*      */ 
/* 1049 */         if (localBeanContextChild != null) {
/*      */           try {
/* 1051 */             localBeanContextChild.setBeanContext(getBeanContextPeer());
/*      */             
/* 1053 */             localBeanContextChild.addPropertyChangeListener("beanContext", this.childPCL);
/* 1054 */             localBeanContextChild.addVetoableChangeListener("beanContext", this.childVCL);
/*      */           }
/*      */           catch (PropertyVetoException localPropertyVetoException) {}
/* 1057 */           continue;
/*      */         }
/*      */         
/*      */ 
/* 1061 */         childDeserializedHook(localObject1, localBCSChild);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1074 */     synchronized (BeanContext.globalHierarchyLock) {
/* 1075 */       paramObjectInputStream.defaultReadObject();
/*      */       
/* 1077 */       initialize();
/*      */       
/* 1079 */       bcsPreDeserializationHook(paramObjectInputStream);
/*      */       
/* 1081 */       if ((this.serializable > 0) && (equals(getBeanContextPeer()))) {
/* 1082 */         readChildren(paramObjectInputStream);
/*      */       }
/* 1084 */       deserialize(paramObjectInputStream, this.bcmListeners = new ArrayList(1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void vetoableChange(PropertyChangeEvent paramPropertyChangeEvent)
/*      */     throws PropertyVetoException
/*      */   {
/* 1093 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 1094 */     Object localObject1 = paramPropertyChangeEvent.getSource();
/*      */     
/* 1096 */     synchronized (this.children) {
/* 1097 */       if (("beanContext".equals(str)) && 
/* 1098 */         (containsKey(localObject1)) && 
/* 1099 */         (!getBeanContextPeer().equals(paramPropertyChangeEvent.getNewValue())))
/*      */       {
/* 1101 */         if (!validatePendingRemove(localObject1))
/* 1102 */           throw new PropertyVetoException("current BeanContext vetoes setBeanContext()", paramPropertyChangeEvent);
/* 1103 */         ((BCSChild)this.children.get(localObject1)).setRemovePending(true);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
/*      */   {
/* 1113 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 1114 */     Object localObject1 = paramPropertyChangeEvent.getSource();
/*      */     
/* 1116 */     synchronized (this.children) {
/* 1117 */       if (("beanContext".equals(str)) && 
/* 1118 */         (containsKey(localObject1)) && 
/* 1119 */         (((BCSChild)this.children.get(localObject1)).isRemovePending())) {
/* 1120 */         BeanContext localBeanContext = getBeanContextPeer();
/*      */         
/* 1122 */         if ((localBeanContext.equals(paramPropertyChangeEvent.getOldValue())) && (!localBeanContext.equals(paramPropertyChangeEvent.getNewValue()))) {
/* 1123 */           remove(localObject1, false);
/*      */         } else {
/* 1125 */           ((BCSChild)this.children.get(localObject1)).setRemovePending(false);
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
/*      */ 
/*      */   protected boolean validatePendingAdd(Object paramObject)
/*      */   {
/* 1143 */     return true;
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
/*      */   protected boolean validatePendingRemove(Object paramObject)
/*      */   {
/* 1158 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void childJustAddedHook(Object paramObject, BCSChild paramBCSChild) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void childJustRemovedHook(Object paramObject, BCSChild paramBCSChild) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final Visibility getChildVisibility(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1190 */       return (Visibility)paramObject;
/*      */     } catch (ClassCastException localClassCastException) {}
/* 1192 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final Serializable getChildSerializable(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1203 */       return (Serializable)paramObject;
/*      */     } catch (ClassCastException localClassCastException) {}
/* 1205 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final PropertyChangeListener getChildPropertyChangeListener(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1217 */       return (PropertyChangeListener)paramObject;
/*      */     } catch (ClassCastException localClassCastException) {}
/* 1219 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final VetoableChangeListener getChildVetoableChangeListener(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1231 */       return (VetoableChangeListener)paramObject;
/*      */     } catch (ClassCastException localClassCastException) {}
/* 1233 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final BeanContextMembershipListener getChildBeanContextMembershipListener(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1245 */       return (BeanContextMembershipListener)paramObject;
/*      */     } catch (ClassCastException localClassCastException) {}
/* 1247 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final BeanContextChild getChildBeanContextChild(Object paramObject)
/*      */   {
/*      */     try
/*      */     {
/* 1259 */       BeanContextChild localBeanContextChild = (BeanContextChild)paramObject;
/*      */       
/* 1261 */       if (((paramObject instanceof BeanContextChild)) && ((paramObject instanceof BeanContextProxy))) {
/* 1262 */         throw new IllegalArgumentException("child cannot implement both BeanContextChild and BeanContextProxy");
/*      */       }
/* 1264 */       return localBeanContextChild;
/*      */     } catch (ClassCastException localClassCastException1) {
/*      */       try {
/* 1267 */         return ((BeanContextProxy)paramObject).getBeanContextProxy();
/*      */       } catch (ClassCastException localClassCastException2) {} }
/* 1269 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void fireChildrenAdded(BeanContextMembershipEvent paramBeanContextMembershipEvent)
/*      */   {
/*      */     Object[] arrayOfObject;
/*      */     
/*      */ 
/*      */ 
/* 1282 */     synchronized (this.bcmListeners) { arrayOfObject = this.bcmListeners.toArray();
/*      */     }
/* 1284 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 1285 */       ((BeanContextMembershipListener)arrayOfObject[i]).childrenAdded(paramBeanContextMembershipEvent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected final void fireChildrenRemoved(BeanContextMembershipEvent paramBeanContextMembershipEvent)
/*      */   {
/*      */     Object[] arrayOfObject;
/*      */     
/*      */ 
/* 1296 */     synchronized (this.bcmListeners) { arrayOfObject = this.bcmListeners.toArray();
/*      */     }
/* 1298 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 1299 */       ((BeanContextMembershipListener)arrayOfObject[i]).childrenRemoved(paramBeanContextMembershipEvent);
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
/*      */   protected synchronized void initialize()
/*      */   {
/* 1314 */     this.children = new HashMap(this.serializable + 1);
/* 1315 */     this.bcmListeners = new ArrayList(1);
/*      */     
/* 1317 */     this.childPCL = new PropertyChangeListener()
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       public void propertyChange(PropertyChangeEvent paramAnonymousPropertyChangeEvent)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1327 */         BeanContextSupport.this.propertyChange(paramAnonymousPropertyChangeEvent);
/*      */       }
/*      */       
/* 1330 */     };
/* 1331 */     this.childVCL = new VetoableChangeListener()
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       public void vetoableChange(PropertyChangeEvent paramAnonymousPropertyChangeEvent)
/*      */         throws PropertyVetoException
/*      */       {
/*      */ 
/*      */ 
/* 1341 */         BeanContextSupport.this.vetoableChange(paramAnonymousPropertyChangeEvent);
/*      */       }
/*      */     };
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   protected final Object[] copyChildren()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 15	java/beans/beancontext/BeanContextSupport:children	Ljava/util/HashMap;
/*      */     //   11: invokevirtual 20	java/util/HashMap:keySet	()Ljava/util/Set;
/*      */     //   14: invokeinterface 23 1 0
/*      */     //   19: aload_1
/*      */     //   20: monitorexit
/*      */     //   21: areturn
/*      */     //   22: astore_2
/*      */     //   23: aload_1
/*      */     //   24: monitorexit
/*      */     //   25: aload_2
/*      */     //   26: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1351	-> byte code offset #0
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	27	0	this	BeanContextSupport
/*      */     //   5	19	1	Ljava/lang/Object;	Object
/*      */     //   22	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	21	22	finally
/*      */     //   22	25	22	finally
/*      */   }
/*      */   
/*      */   protected static final boolean classEquals(Class paramClass1, Class paramClass2)
/*      */   {
/* 1362 */     return (paramClass1.equals(paramClass2)) || (paramClass1.getName().equals(paramClass2.getName()));
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
/* 1377 */   private int serializable = 0;
/*      */   protected transient ArrayList bcmListeners;
/*      */   protected Locale locale;
/*      */   protected boolean okToUseGui;
/*      */   protected boolean designTime;
/*      */   private transient PropertyChangeListener childPCL;
/*      */   private transient VetoableChangeListener childVCL;
/*      */   private transient boolean serializing;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/beancontext/BeanContextSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */