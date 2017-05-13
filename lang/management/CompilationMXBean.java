package java.lang.management;

public abstract interface CompilationMXBean
  extends PlatformManagedObject
{
  public abstract String getName();
  
  public abstract boolean isCompilationTimeMonitoringSupported();
  
  public abstract long getTotalCompilationTime();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/CompilationMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */