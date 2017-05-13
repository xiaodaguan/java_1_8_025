package java.awt.peer;

import java.awt.Component;
import java.awt.Window;

public abstract interface KeyboardFocusManagerPeer
{
  public abstract void setCurrentFocusedWindow(Window paramWindow);
  
  public abstract Window getCurrentFocusedWindow();
  
  public abstract void setCurrentFocusOwner(Component paramComponent);
  
  public abstract Component getCurrentFocusOwner();
  
  public abstract void clearGlobalFocusOwner(Window paramWindow);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/KeyboardFocusManagerPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */