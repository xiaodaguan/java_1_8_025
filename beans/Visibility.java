package java.beans;

public abstract interface Visibility
{
  public abstract boolean needsGui();
  
  public abstract void dontUseGui();
  
  public abstract void okToUseGui();
  
  public abstract boolean avoidingGui();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/Visibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */