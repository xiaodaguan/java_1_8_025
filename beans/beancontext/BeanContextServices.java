package java.beans.beancontext;

import java.util.Iterator;
import java.util.TooManyListenersException;

public abstract interface BeanContextServices
  extends BeanContext, BeanContextServicesListener
{
  public abstract boolean addService(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider);
  
  public abstract void revokeService(Class paramClass, BeanContextServiceProvider paramBeanContextServiceProvider, boolean paramBoolean);
  
  public abstract boolean hasService(Class paramClass);
  
  public abstract Object getService(BeanContextChild paramBeanContextChild, Object paramObject1, Class paramClass, Object paramObject2, BeanContextServiceRevokedListener paramBeanContextServiceRevokedListener)
    throws TooManyListenersException;
  
  public abstract void releaseService(BeanContextChild paramBeanContextChild, Object paramObject1, Object paramObject2);
  
  public abstract Iterator getCurrentServiceClasses();
  
  public abstract Iterator getCurrentServiceSelectors(Class paramClass);
  
  public abstract void addBeanContextServicesListener(BeanContextServicesListener paramBeanContextServicesListener);
  
  public abstract void removeBeanContextServicesListener(BeanContextServicesListener paramBeanContextServicesListener);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/beancontext/BeanContextServices.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */