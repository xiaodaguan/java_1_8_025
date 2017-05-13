package java.awt;

public abstract interface LayoutManager
{
  public abstract void addLayoutComponent(String paramString, Component paramComponent);
  
  public abstract void removeLayoutComponent(Component paramComponent);
  
  public abstract Dimension preferredLayoutSize(Container paramContainer);
  
  public abstract Dimension minimumLayoutSize(Container paramContainer);
  
  public abstract void layoutContainer(Container paramContainer);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/LayoutManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */