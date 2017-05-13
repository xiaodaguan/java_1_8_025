package java.lang.management;

public abstract interface OperatingSystemMXBean
  extends PlatformManagedObject
{
  public abstract String getName();
  
  public abstract String getArch();
  
  public abstract String getVersion();
  
  public abstract int getAvailableProcessors();
  
  public abstract double getSystemLoadAverage();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/OperatingSystemMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */