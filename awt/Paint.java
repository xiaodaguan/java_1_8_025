package java.awt;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

public abstract interface Paint
  extends Transparency
{
  public abstract PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Paint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */