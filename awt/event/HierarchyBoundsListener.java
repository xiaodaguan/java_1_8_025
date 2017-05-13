package java.awt.event;

import java.util.EventListener;

public abstract interface HierarchyBoundsListener
  extends EventListener
{
  public abstract void ancestorMoved(HierarchyEvent paramHierarchyEvent);
  
  public abstract void ancestorResized(HierarchyEvent paramHierarchyEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/HierarchyBoundsListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */