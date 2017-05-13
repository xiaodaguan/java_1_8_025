package java.awt.peer;

import java.awt.Insets;

public abstract interface ContainerPeer
  extends ComponentPeer
{
  public abstract Insets getInsets();
  
  public abstract void beginValidate();
  
  public abstract void endValidate();
  
  public abstract void beginLayout();
  
  public abstract void endLayout();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/ContainerPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */