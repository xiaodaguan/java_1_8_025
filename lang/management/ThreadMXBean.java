package java.lang.management;

public abstract interface ThreadMXBean
  extends PlatformManagedObject
{
  public abstract int getThreadCount();
  
  public abstract int getPeakThreadCount();
  
  public abstract long getTotalStartedThreadCount();
  
  public abstract int getDaemonThreadCount();
  
  public abstract long[] getAllThreadIds();
  
  public abstract ThreadInfo getThreadInfo(long paramLong);
  
  public abstract ThreadInfo[] getThreadInfo(long[] paramArrayOfLong);
  
  public abstract ThreadInfo getThreadInfo(long paramLong, int paramInt);
  
  public abstract ThreadInfo[] getThreadInfo(long[] paramArrayOfLong, int paramInt);
  
  public abstract boolean isThreadContentionMonitoringSupported();
  
  public abstract boolean isThreadContentionMonitoringEnabled();
  
  public abstract void setThreadContentionMonitoringEnabled(boolean paramBoolean);
  
  public abstract long getCurrentThreadCpuTime();
  
  public abstract long getCurrentThreadUserTime();
  
  public abstract long getThreadCpuTime(long paramLong);
  
  public abstract long getThreadUserTime(long paramLong);
  
  public abstract boolean isThreadCpuTimeSupported();
  
  public abstract boolean isCurrentThreadCpuTimeSupported();
  
  public abstract boolean isThreadCpuTimeEnabled();
  
  public abstract void setThreadCpuTimeEnabled(boolean paramBoolean);
  
  public abstract long[] findMonitorDeadlockedThreads();
  
  public abstract void resetPeakThreadCount();
  
  public abstract long[] findDeadlockedThreads();
  
  public abstract boolean isObjectMonitorUsageSupported();
  
  public abstract boolean isSynchronizerUsageSupported();
  
  public abstract ThreadInfo[] getThreadInfo(long[] paramArrayOfLong, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract ThreadInfo[] dumpAllThreads(boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/ThreadMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */