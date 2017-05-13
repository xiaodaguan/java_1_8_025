package java.awt.image.renderable;

import java.awt.geom.Rectangle2D;
import java.awt.image.RenderedImage;

public abstract interface ContextualRenderedImageFactory
  extends RenderedImageFactory
{
  public abstract RenderContext mapRenderContext(int paramInt, RenderContext paramRenderContext, ParameterBlock paramParameterBlock, RenderableImage paramRenderableImage);
  
  public abstract RenderedImage create(RenderContext paramRenderContext, ParameterBlock paramParameterBlock);
  
  public abstract Rectangle2D getBounds2D(ParameterBlock paramParameterBlock);
  
  public abstract Object getProperty(ParameterBlock paramParameterBlock, String paramString);
  
  public abstract String[] getPropertyNames();
  
  public abstract boolean isDynamic();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/image/renderable/ContextualRenderedImageFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */