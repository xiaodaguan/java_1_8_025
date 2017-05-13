package java.awt.event;

import java.util.EventListener;

public abstract interface MouseListener
  extends EventListener
{
  public abstract void mouseClicked(MouseEvent paramMouseEvent);
  
  public abstract void mousePressed(MouseEvent paramMouseEvent);
  
  public abstract void mouseReleased(MouseEvent paramMouseEvent);
  
  public abstract void mouseEntered(MouseEvent paramMouseEvent);
  
  public abstract void mouseExited(MouseEvent paramMouseEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/MouseListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */