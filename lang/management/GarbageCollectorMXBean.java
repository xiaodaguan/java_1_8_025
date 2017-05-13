package java.lang.management;

public abstract interface GarbageCollectorMXBean
  extends MemoryManagerMXBean
{
  public abstract long getCollectionCount();
  
  public abstract long getCollectionTime();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/GarbageCollectorMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */