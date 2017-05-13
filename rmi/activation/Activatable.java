/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RemoteServer;
/*     */ import sun.rmi.server.ActivatableRef;
/*     */ import sun.rmi.server.ActivatableServerRef;
/*     */ import sun.rmi.transport.ObjectTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Activatable
/*     */   extends RemoteServer
/*     */ {
/*     */   private ActivationID id;
/*     */   private static final long serialVersionUID = -3120617863591563455L;
/*     */   
/*     */   protected Activatable(String paramString, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean, int paramInt)
/*     */     throws ActivationException, RemoteException
/*     */   {
/* 107 */     this.id = exportObject(this, paramString, paramMarshalledObject, paramBoolean, paramInt);
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
/*     */   protected Activatable(String paramString, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory)
/*     */     throws ActivationException, RemoteException
/*     */   {
/* 161 */     this.id = exportObject(this, paramString, paramMarshalledObject, paramBoolean, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
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
/*     */   protected Activatable(ActivationID paramActivationID, int paramInt)
/*     */     throws RemoteException
/*     */   {
/* 190 */     this.id = paramActivationID;
/* 191 */     exportObject(this, paramActivationID, paramInt);
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
/*     */   protected Activatable(ActivationID paramActivationID, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory)
/*     */     throws RemoteException
/*     */   {
/* 225 */     this.id = paramActivationID;
/* 226 */     exportObject(this, paramActivationID, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ActivationID getID()
/*     */   {
/* 237 */     return this.id;
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
/*     */   public static Remote register(ActivationDesc paramActivationDesc)
/*     */     throws UnknownGroupException, ActivationException, RemoteException
/*     */   {
/* 259 */     ActivationID localActivationID = ActivationGroup.getSystem().registerObject(paramActivationDesc);
/* 260 */     return ActivatableRef.getStub(paramActivationDesc, localActivationID);
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
/*     */   public static boolean inactive(ActivationID paramActivationID)
/*     */     throws UnknownObjectException, ActivationException, RemoteException
/*     */   {
/* 293 */     return ActivationGroup.currentGroup().inactiveObject(paramActivationID);
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
/*     */   public static void unregister(ActivationID paramActivationID)
/*     */     throws UnknownObjectException, ActivationException, RemoteException
/*     */   {
/* 312 */     ActivationGroup.getSystem().unregisterObject(paramActivationID);
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
/*     */   public static ActivationID exportObject(Remote paramRemote, String paramString, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean, int paramInt)
/*     */     throws ActivationException, RemoteException
/*     */   {
/* 362 */     return exportObject(paramRemote, paramString, paramMarshalledObject, paramBoolean, paramInt, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ActivationID exportObject(Remote paramRemote, String paramString, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory)
/*     */     throws ActivationException, RemoteException
/*     */   {
/* 439 */     ActivationDesc localActivationDesc = new ActivationDesc(paramRemote.getClass().getName(), paramString, paramMarshalledObject, paramBoolean);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 444 */     ActivationSystem localActivationSystem = ActivationGroup.getSystem();
/* 445 */     ActivationID localActivationID = localActivationSystem.registerObject(localActivationDesc);
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 451 */       exportObject(paramRemote, localActivationID, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/*     */ 
/*     */     }
/*     */     catch (RemoteException localRemoteException)
/*     */     {
/*     */       try
/*     */       {
/* 458 */         localActivationSystem.unregisterObject(localActivationID);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */       
/*     */ 
/*     */ 
/* 464 */       throw localRemoteException;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 472 */     ActivationGroup.currentGroup().activeObject(localActivationID, paramRemote);
/*     */     
/* 474 */     return localActivationID;
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
/*     */   public static Remote exportObject(Remote paramRemote, ActivationID paramActivationID, int paramInt)
/*     */     throws RemoteException
/*     */   {
/* 503 */     return exportObject(paramRemote, new ActivatableServerRef(paramActivationID, paramInt));
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
/*     */   public static Remote exportObject(Remote paramRemote, ActivationID paramActivationID, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory)
/*     */     throws RemoteException
/*     */   {
/* 537 */     return exportObject(paramRemote, new ActivatableServerRef(paramActivationID, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory));
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
/*     */   public static boolean unexportObject(Remote paramRemote, boolean paramBoolean)
/*     */     throws NoSuchObjectException
/*     */   {
/* 563 */     return ObjectTable.unexportObject(paramRemote, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Remote exportObject(Remote paramRemote, ActivatableServerRef paramActivatableServerRef)
/*     */     throws RemoteException
/*     */   {
/* 573 */     if ((paramRemote instanceof Activatable)) {
/* 574 */       ((Activatable)paramRemote).ref = paramActivatableServerRef;
/*     */     }
/*     */     
/* 577 */     return paramActivatableServerRef.exportObject(paramRemote, null, false);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/activation/Activatable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */