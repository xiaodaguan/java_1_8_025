package java.util.concurrent.locks;

public abstract interface ReadWriteLock
{
  public abstract Lock readLock();
  
  public abstract Lock writeLock();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/locks/ReadWriteLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */