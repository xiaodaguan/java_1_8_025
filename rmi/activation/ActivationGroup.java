/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.rmi.Naming;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import java.security.AccessController;
/*     */ import sun.rmi.server.ActivationGroupImpl;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ActivationGroup
/*     */   extends UnicastRemoteObject
/*     */   implements ActivationInstantiator
/*     */ {
/*     */   private ActivationGroupID groupID;
/*     */   private ActivationMonitor monitor;
/*     */   private long incarnation;
/*     */   private static ActivationGroup currGroup;
/*     */   private static ActivationGroupID currGroupID;
/*     */   private static ActivationSystem currSystem;
/* 124 */   private static boolean canCreate = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -7696947875314805420L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ActivationGroup(ActivationGroupID paramActivationGroupID)
/*     */     throws RemoteException
/*     */   {
/* 145 */     this.groupID = paramActivationGroupID;
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
/*     */   public boolean inactiveObject(ActivationID paramActivationID)
/*     */     throws ActivationException, UnknownObjectException, RemoteException
/*     */   {
/* 189 */     getMonitor().inactiveObject(paramActivationID);
/* 190 */     return true;
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
/*     */   public abstract void activeObject(ActivationID paramActivationID, Remote paramRemote)
/*     */     throws ActivationException, UnknownObjectException, RemoteException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized ActivationGroup createGroup(ActivationGroupID paramActivationGroupID, ActivationGroupDesc paramActivationGroupDesc, long paramLong)
/*     */     throws ActivationException
/*     */   {
/* 283 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 284 */     if (localSecurityManager != null) {
/* 285 */       localSecurityManager.checkSetFactory();
/*     */     }
/* 287 */     if (currGroup != null) {
/* 288 */       throw new ActivationException("group already exists");
/*     */     }
/* 290 */     if (!canCreate) {
/* 291 */       throw new ActivationException("group deactivated and cannot be recreated");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 296 */       String str = paramActivationGroupDesc.getClassName();
/*     */       
/* 298 */       Class localClass2 = ActivationGroupImpl.class;
/*     */       Class localClass1;
/* 300 */       if ((str == null) || 
/* 301 */         (str.equals(localClass2.getName())))
/*     */       {
/* 303 */         localClass1 = localClass2;
/*     */       }
/*     */       else {
/*     */         try {
/* 307 */           localObject = RMIClassLoader.loadClass(paramActivationGroupDesc.getLocation(), str);
/*     */         }
/*     */         catch (Exception localException2) {
/* 310 */           throw new ActivationException("Could not load group implementation class", localException2);
/*     */         }
/*     */         
/* 313 */         if (ActivationGroup.class.isAssignableFrom((Class)localObject)) {
/* 314 */           localClass1 = ((Class)localObject).asSubclass(ActivationGroup.class);
/*     */         }
/*     */         else {
/* 317 */           throw new ActivationException("group not correct class: " + ((Class)localObject).getName());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 323 */       Object localObject = localClass1.getConstructor(new Class[] { ActivationGroupID.class, MarshalledObject.class });
/*     */       
/*     */ 
/* 326 */       ActivationGroup localActivationGroup = (ActivationGroup)((Constructor)localObject).newInstance(new Object[] { paramActivationGroupID, paramActivationGroupDesc.getData() });
/* 327 */       currSystem = paramActivationGroupID.getSystem();
/* 328 */       localActivationGroup.incarnation = paramLong;
/*     */       
/* 330 */       localActivationGroup.monitor = currSystem.activeGroup(paramActivationGroupID, localActivationGroup, paramLong);
/* 331 */       currGroup = localActivationGroup;
/* 332 */       currGroupID = paramActivationGroupID;
/* 333 */       canCreate = false;
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 335 */       localInvocationTargetException.getTargetException().printStackTrace();
/*     */       
/* 337 */       throw new ActivationException("exception in group constructor", localInvocationTargetException.getTargetException());
/*     */     }
/*     */     catch (ActivationException localActivationException) {
/* 340 */       throw localActivationException;
/*     */     }
/*     */     catch (Exception localException1) {
/* 343 */       throw new ActivationException("exception creating group", localException1);
/*     */     }
/*     */     
/* 346 */     return currGroup;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized ActivationGroupID currentGroupID()
/*     */   {
/* 358 */     return currGroupID;
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
/*     */   static synchronized ActivationGroupID internalCurrentGroupID()
/*     */     throws ActivationException
/*     */   {
/* 375 */     if (currGroupID == null) {
/* 376 */       throw new ActivationException("nonexistent group");
/*     */     }
/* 378 */     return currGroupID;
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
/*     */   public static synchronized void setSystem(ActivationSystem paramActivationSystem)
/*     */     throws ActivationException
/*     */   {
/* 412 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 413 */     if (localSecurityManager != null) {
/* 414 */       localSecurityManager.checkSetFactory();
/*     */     }
/* 416 */     if (currSystem != null) {
/* 417 */       throw new ActivationException("activation system already set");
/*     */     }
/* 419 */     currSystem = paramActivationSystem;
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
/*     */   public static synchronized ActivationSystem getSystem()
/*     */     throws ActivationException
/*     */   {
/* 447 */     if (currSystem == null) {
/*     */       try {
/* 449 */         int i = ((Integer)AccessController.doPrivileged(new GetIntegerAction("java.rmi.activation.port", 1098))).intValue();
/*     */         
/*     */ 
/*     */ 
/* 453 */         currSystem = (ActivationSystem)Naming.lookup("//:" + i + "/java.rmi.activation.ActivationSystem");
/*     */       }
/*     */       catch (Exception localException) {
/* 456 */         throw new ActivationException("unable to obtain ActivationSystem", localException);
/*     */       }
/*     */     }
/*     */     
/* 460 */     return currSystem;
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
/*     */   protected void activeObject(ActivationID paramActivationID, MarshalledObject<? extends Remote> paramMarshalledObject)
/*     */     throws ActivationException, UnknownObjectException, RemoteException
/*     */   {
/* 480 */     getMonitor().activeObject(paramActivationID, paramMarshalledObject);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   protected void inactiveGroup()
/*     */     throws UnknownGroupException, RemoteException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokespecial 3	java/rmi/activation/ActivationGroup:getMonitor	()Ljava/rmi/activation/ActivationMonitor;
/*     */     //   4: aload_0
/*     */     //   5: getfield 2	java/rmi/activation/ActivationGroup:groupID	Ljava/rmi/activation/ActivationGroupID;
/*     */     //   8: aload_0
/*     */     //   9: getfield 39	java/rmi/activation/ActivationGroup:incarnation	J
/*     */     //   12: invokeinterface 63 4 0
/*     */     //   17: invokestatic 64	java/rmi/activation/ActivationGroup:destroyGroup	()V
/*     */     //   20: goto +9 -> 29
/*     */     //   23: astore_1
/*     */     //   24: invokestatic 64	java/rmi/activation/ActivationGroup:destroyGroup	()V
/*     */     //   27: aload_1
/*     */     //   28: athrow
/*     */     //   29: return
/*     */     // Line number table:
/*     */     //   Java source line #498	-> byte code offset #0
/*     */     //   Java source line #500	-> byte code offset #17
/*     */     //   Java source line #501	-> byte code offset #20
/*     */     //   Java source line #500	-> byte code offset #23
/*     */     //   Java source line #502	-> byte code offset #29
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	30	0	this	ActivationGroup
/*     */     //   23	5	1	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	17	23	finally
/*     */   }
/*     */   
/*     */   private ActivationMonitor getMonitor()
/*     */     throws RemoteException
/*     */   {
/* 508 */     synchronized (ActivationGroup.class) {
/* 509 */       if (this.monitor != null) {
/* 510 */         return this.monitor;
/*     */       }
/*     */     }
/* 513 */     throw new RemoteException("monitor not received");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static synchronized void destroyGroup()
/*     */   {
/* 520 */     currGroup = null;
/* 521 */     currGroupID = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized ActivationGroup currentGroup()
/*     */     throws ActivationException
/*     */   {
/* 532 */     if (currGroup == null) {
/* 533 */       throw new ActivationException("group is not active");
/*     */     }
/* 535 */     return currGroup;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/activation/ActivationGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */