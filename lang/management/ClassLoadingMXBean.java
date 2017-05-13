package java.lang.management;

public abstract interface ClassLoadingMXBean
  extends PlatformManagedObject
{
  public abstract long getTotalLoadedClassCount();
  
  public abstract int getLoadedClassCount();
  
  public abstract long getUnloadedClassCount();
  
  public abstract boolean isVerbose();
  
  public abstract void setVerbose(boolean paramBoolean);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/ClassLoadingMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */