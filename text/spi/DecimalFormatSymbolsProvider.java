package java.text.spi;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

public abstract class DecimalFormatSymbolsProvider
  extends LocaleServiceProvider
{
  public abstract DecimalFormatSymbols getInstance(Locale paramLocale);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/spi/DecimalFormatSymbolsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */