package java.lang.management;

public abstract interface BufferPoolMXBean
  extends PlatformManagedObject
{
  public abstract String getName();
  
  public abstract long getCount();
  
  public abstract long getTotalCapacity();
  
  public abstract long getMemoryUsed();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/BufferPoolMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */