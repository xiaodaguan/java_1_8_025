package java.beans.beancontext;

import java.util.EventListener;

public abstract interface BeanContextMembershipListener
  extends EventListener
{
  public abstract void childrenAdded(BeanContextMembershipEvent paramBeanContextMembershipEvent);
  
  public abstract void childrenRemoved(BeanContextMembershipEvent paramBeanContextMembershipEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/beancontext/BeanContextMembershipListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */