package java.awt.print;

import java.awt.Graphics;

public abstract interface Printable
{
  public static final int PAGE_EXISTS = 0;
  public static final int NO_SUCH_PAGE = 1;
  
  public abstract int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt)
    throws PrinterException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/print/Printable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */