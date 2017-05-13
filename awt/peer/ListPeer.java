package java.awt.peer;

import java.awt.Dimension;

public abstract interface ListPeer
  extends ComponentPeer
{
  public abstract int[] getSelectedIndexes();
  
  public abstract void add(String paramString, int paramInt);
  
  public abstract void delItems(int paramInt1, int paramInt2);
  
  public abstract void removeAll();
  
  public abstract void select(int paramInt);
  
  public abstract void deselect(int paramInt);
  
  public abstract void makeVisible(int paramInt);
  
  public abstract void setMultipleMode(boolean paramBoolean);
  
  public abstract Dimension getPreferredSize(int paramInt);
  
  public abstract Dimension getMinimumSize(int paramInt);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/ListPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */