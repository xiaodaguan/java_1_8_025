package java.awt.image;

import java.awt.BufferCapabilities;
import java.awt.Graphics;

public abstract class BufferStrategy
{
  public abstract BufferCapabilities getCapabilities();
  
  public abstract Graphics getDrawGraphics();
  
  public abstract boolean contentsLost();
  
  public abstract boolean contentsRestored();
  
  public abstract void show();
  
  public void dispose() {}
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/BufferStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */