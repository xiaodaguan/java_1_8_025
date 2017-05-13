package java.lang.management;

import java.util.List;

public abstract interface PlatformLoggingMXBean
  extends PlatformManagedObject
{
  public abstract List<String> getLoggerNames();
  
  public abstract String getLoggerLevel(String paramString);
  
  public abstract void setLoggerLevel(String paramString1, String paramString2);
  
  public abstract String getParentLoggerName(String paramString);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/PlatformLoggingMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */