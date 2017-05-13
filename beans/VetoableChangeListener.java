package java.beans;

import java.util.EventListener;

public abstract interface VetoableChangeListener
  extends EventListener
{
  public abstract void vetoableChange(PropertyChangeEvent paramPropertyChangeEvent)
    throws PropertyVetoException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/VetoableChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */