package java.awt.peer;

import java.awt.Point;
import java.awt.Window;

public abstract interface MouseInfoPeer
{
  public abstract int fillPointWithCoords(Point paramPoint);
  
  public abstract boolean isWindowUnderMouse(Window paramWindow);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/MouseInfoPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */