package java.awt.peer;

import java.awt.Dimension;

public abstract interface TextFieldPeer
  extends TextComponentPeer
{
  public abstract void setEchoChar(char paramChar);
  
  public abstract Dimension getPreferredSize(int paramInt);
  
  public abstract Dimension getMinimumSize(int paramInt);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/TextFieldPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */