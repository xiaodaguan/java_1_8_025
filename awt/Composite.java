package java.awt;

import java.awt.image.ColorModel;

public abstract interface Composite
{
  public abstract CompositeContext createContext(ColorModel paramColorModel1, ColorModel paramColorModel2, RenderingHints paramRenderingHints);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Composite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */