package java.util.spi;

import java.util.Locale;

public abstract class CalendarDataProvider
  extends LocaleServiceProvider
{
  public abstract int getFirstDayOfWeek(Locale paramLocale);
  
  public abstract int getMinimalDaysInFirstWeek(Locale paramLocale);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/spi/CalendarDataProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */