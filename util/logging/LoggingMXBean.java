package java.util.logging;

import java.util.List;

public abstract interface LoggingMXBean
{
  public abstract List<String> getLoggerNames();
  
  public abstract String getLoggerLevel(String paramString);
  
  public abstract void setLoggerLevel(String paramString1, String paramString2);
  
  public abstract String getParentLoggerName(String paramString);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/LoggingMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */