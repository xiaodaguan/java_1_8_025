package java.awt.dnd;

import java.awt.Insets;
import java.awt.Point;

public abstract interface Autoscroll
{
  public abstract Insets getAutoscrollInsets();
  
  public abstract void autoscroll(Point paramPoint);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/Autoscroll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */