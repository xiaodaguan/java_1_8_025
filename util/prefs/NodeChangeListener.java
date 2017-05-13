package java.util.prefs;

import java.util.EventListener;

public abstract interface NodeChangeListener
  extends EventListener
{
  public abstract void childAdded(NodeChangeEvent paramNodeChangeEvent);
  
  public abstract void childRemoved(NodeChangeEvent paramNodeChangeEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/NodeChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */