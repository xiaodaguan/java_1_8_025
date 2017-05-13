package java.awt.peer;

import java.awt.GraphicsConfiguration;

public abstract interface CanvasPeer
  extends ComponentPeer
{
  public abstract GraphicsConfiguration getAppropriateGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/CanvasPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */