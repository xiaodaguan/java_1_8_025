package java.awt.event;

import java.awt.AWTEvent;
import java.util.EventListener;

public abstract interface AWTEventListener
  extends EventListener
{
  public abstract void eventDispatched(AWTEvent paramAWTEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/AWTEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */