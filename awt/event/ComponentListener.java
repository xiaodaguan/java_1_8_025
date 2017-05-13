package java.awt.event;

import java.util.EventListener;

public abstract interface ComponentListener
  extends EventListener
{
  public abstract void componentResized(ComponentEvent paramComponentEvent);
  
  public abstract void componentMoved(ComponentEvent paramComponentEvent);
  
  public abstract void componentShown(ComponentEvent paramComponentEvent);
  
  public abstract void componentHidden(ComponentEvent paramComponentEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/ComponentListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */