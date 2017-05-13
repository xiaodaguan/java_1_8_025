package java.awt.event;

import java.util.EventListener;

public abstract interface ContainerListener
  extends EventListener
{
  public abstract void componentAdded(ContainerEvent paramContainerEvent);
  
  public abstract void componentRemoved(ContainerEvent paramContainerEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/ContainerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */