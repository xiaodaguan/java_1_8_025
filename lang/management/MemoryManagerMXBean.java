package java.lang.management;

public abstract interface MemoryManagerMXBean
  extends PlatformManagedObject
{
  public abstract String getName();
  
  public abstract boolean isValid();
  
  public abstract String[] getMemoryPoolNames();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/MemoryManagerMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */