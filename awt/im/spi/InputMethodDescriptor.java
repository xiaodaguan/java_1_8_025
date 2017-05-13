package java.awt.im.spi;

import java.awt.AWTException;
import java.awt.Image;
import java.util.Locale;

public abstract interface InputMethodDescriptor
{
  public abstract Locale[] getAvailableLocales()
    throws AWTException;
  
  public abstract boolean hasDynamicLocaleList();
  
  public abstract String getInputMethodDisplayName(Locale paramLocale1, Locale paramLocale2);
  
  public abstract Image getInputMethodIcon(Locale paramLocale);
  
  public abstract InputMethod createInputMethod()
    throws Exception;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/im/spi/InputMethodDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */