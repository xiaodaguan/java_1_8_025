package java.awt.peer;

public abstract interface ChoicePeer
  extends ComponentPeer
{
  public abstract void add(String paramString, int paramInt);
  
  public abstract void remove(int paramInt);
  
  public abstract void removeAll();
  
  public abstract void select(int paramInt);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/ChoicePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */