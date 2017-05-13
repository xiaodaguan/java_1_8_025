package java.lang;

import java.io.IOException;

public abstract interface Appendable
{
  public abstract Appendable append(CharSequence paramCharSequence)
    throws IOException;
  
  public abstract Appendable append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract Appendable append(char paramChar)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Appendable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */