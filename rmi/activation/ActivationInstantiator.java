package java.rmi.activation;

import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ActivationInstantiator
  extends Remote
{
  public abstract MarshalledObject<? extends Remote> newInstance(ActivationID paramActivationID, ActivationDesc paramActivationDesc)
    throws ActivationException, RemoteException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/activation/ActivationInstantiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */