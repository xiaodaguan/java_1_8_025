package java.rmi.registry;

import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

@Deprecated
public abstract interface RegistryHandler
{
  @Deprecated
  public abstract Registry registryStub(String paramString, int paramInt)
    throws RemoteException, UnknownHostException;
  
  @Deprecated
  public abstract Registry registryImpl(int paramInt)
    throws RemoteException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/registry/RegistryHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */