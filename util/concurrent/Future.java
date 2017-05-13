package java.util.concurrent;

public abstract interface Future<V>
{
  public abstract boolean cancel(boolean paramBoolean);
  
  public abstract boolean isCancelled();
  
  public abstract boolean isDone();
  
  public abstract V get()
    throws InterruptedException, ExecutionException;
  
  public abstract V get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/Future.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */