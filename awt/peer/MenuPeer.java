package java.awt.peer;

import java.awt.MenuItem;

public abstract interface MenuPeer
  extends MenuItemPeer
{
  public abstract void addSeparator();
  
  public abstract void addItem(MenuItem paramMenuItem);
  
  public abstract void delItem(int paramInt);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/MenuPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */