package java.awt;

public abstract interface Transparency
{
  public static final int OPAQUE = 1;
  public static final int BITMASK = 2;
  public static final int TRANSLUCENT = 3;
  
  public abstract int getTransparency();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Transparency.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */