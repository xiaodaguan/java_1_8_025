package java.awt;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public abstract interface CompositeContext
{
  public abstract void dispose();
  
  public abstract void compose(Raster paramRaster1, Raster paramRaster2, WritableRaster paramWritableRaster);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/CompositeContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */