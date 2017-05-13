package java.awt.event;

import java.util.EventListener;

public abstract interface WindowFocusListener
  extends EventListener
{
  public abstract void windowGainedFocus(WindowEvent paramWindowEvent);
  
  public abstract void windowLostFocus(WindowEvent paramWindowEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/WindowFocusListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */