package java.rmi.activation;

import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface Activator
  extends Remote
{
  public abstract MarshalledObject<? extends Remote> activate(ActivationID paramActivationID, boolean paramBoolean)
    throws ActivationException, UnknownObjectException, RemoteException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/activation/Activator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */