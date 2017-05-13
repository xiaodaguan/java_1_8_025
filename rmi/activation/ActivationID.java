/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.RemoteObjectInvocationHandler;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.UID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivationID
/*     */   implements Serializable
/*     */ {
/*     */   private transient Activator activator;
/*  79 */   private transient UID uid = new UID();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -4608673054848209235L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActivationID(Activator paramActivator)
/*     */   {
/*  98 */     this.activator = paramActivator;
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
/*     */   public Remote activate(boolean paramBoolean)
/*     */     throws ActivationException, UnknownObjectException, RemoteException
/*     */   {
/*     */     try
/*     */     {
/* 118 */       MarshalledObject localMarshalledObject = this.activator.activate(this, paramBoolean);
/* 119 */       return (Remote)localMarshalledObject.get();
/*     */     } catch (RemoteException localRemoteException) {
/* 121 */       throw localRemoteException;
/*     */     } catch (IOException localIOException) {
/* 123 */       throw new UnmarshalException("activation failed", localIOException);
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 125 */       throw new UnmarshalException("activation failed", localClassNotFoundException);
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
/*     */   public int hashCode()
/*     */   {
/* 138 */     return this.uid.hashCode();
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
/* 154 */     if ((paramObject instanceof ActivationID)) {
/* 155 */       ActivationID localActivationID = (ActivationID)paramObject;
/* 156 */       return (this.uid.equals(localActivationID.uid)) && (this.activator.equals(localActivationID.activator));
/*     */     }
/* 158 */     return false;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 210 */     paramObjectOutputStream.writeObject(this.uid);
/*     */     
/*     */     RemoteRef localRemoteRef;
/* 213 */     if ((this.activator instanceof RemoteObject)) {
/* 214 */       localRemoteRef = ((RemoteObject)this.activator).getRef();
/* 215 */     } else if (Proxy.isProxyClass(this.activator.getClass())) {
/* 216 */       InvocationHandler localInvocationHandler = Proxy.getInvocationHandler(this.activator);
/* 217 */       if (!(localInvocationHandler instanceof RemoteObjectInvocationHandler)) {
/* 218 */         throw new InvalidObjectException("unexpected invocation handler");
/*     */       }
/*     */       
/* 221 */       localRemoteRef = ((RemoteObjectInvocationHandler)localInvocationHandler).getRef();
/*     */     }
/*     */     else {
/* 224 */       throw new InvalidObjectException("unexpected activator type");
/*     */     }
/* 226 */     paramObjectOutputStream.writeUTF(localRemoteRef.getRefClass(paramObjectOutputStream));
/* 227 */     localRemoteRef.writeExternal(paramObjectOutputStream);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 269 */     this.uid = ((UID)paramObjectInputStream.readObject());
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 274 */       Class localClass = Class.forName("sun.rmi.server." + paramObjectInputStream.readUTF()).asSubclass(RemoteRef.class);
/* 275 */       RemoteRef localRemoteRef = (RemoteRef)localClass.newInstance();
/* 276 */       localRemoteRef.readExternal(paramObjectInputStream);
/*     */       
/* 278 */       this.activator = ((Activator)Proxy.newProxyInstance(null, new Class[] { Activator.class }, new RemoteObjectInvocationHandler(localRemoteRef)));
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (InstantiationException localInstantiationException)
/*     */     {
/*     */ 
/* 285 */       throw ((IOException)new InvalidObjectException("Unable to create remote reference").initCause(localInstantiationException));
/*     */     }
/*     */     catch (IllegalAccessException localIllegalAccessException)
/*     */     {
/* 289 */       throw ((IOException)new InvalidObjectException("Unable to create remote reference").initCause(localIllegalAccessException));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/activation/ActivationID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */