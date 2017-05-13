package java.lang.management;

public abstract interface MemoryMXBean
  extends PlatformManagedObject
{
  public abstract int getObjectPendingFinalizationCount();
  
  public abstract MemoryUsage getHeapMemoryUsage();
  
  public abstract MemoryUsage getNonHeapMemoryUsage();
  
  public abstract boolean isVerbose();
  
  public abstract void setVerbose(boolean paramBoolean);
  
  public abstract void gc();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/MemoryMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */