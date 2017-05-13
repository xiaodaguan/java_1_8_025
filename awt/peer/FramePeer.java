package java.awt.peer;

import java.awt.MenuBar;
import java.awt.Rectangle;

public abstract interface FramePeer
  extends WindowPeer
{
  public abstract void setTitle(String paramString);
  
  public abstract void setMenuBar(MenuBar paramMenuBar);
  
  public abstract void setResizable(boolean paramBoolean);
  
  public abstract void setState(int paramInt);
  
  public abstract int getState();
  
  public abstract void setMaximizedBounds(Rectangle paramRectangle);
  
  public abstract void setBoundsPrivate(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract Rectangle getBoundsPrivate();
  
  public abstract void emulateActivation(boolean paramBoolean);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/FramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */