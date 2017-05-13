package java.awt.font;

import java.awt.Font;

public abstract interface MultipleMaster
{
  public abstract int getNumDesignAxes();
  
  public abstract float[] getDesignAxisRanges();
  
  public abstract float[] getDesignAxisDefaults();
  
  public abstract String[] getDesignAxisNames();
  
  public abstract Font deriveMMFont(float[] paramArrayOfFloat);
  
  public abstract Font deriveMMFont(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/font/MultipleMaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */