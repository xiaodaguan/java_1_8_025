package java.util.concurrent;

public abstract interface CompletionService<V>
{
  public abstract Future<V> submit(Callable<V> paramCallable);
  
  public abstract Future<V> submit(Runnable paramRunnable, V paramV);
  
  public abstract Future<V> take()
    throws InterruptedException;
  
  public abstract Future<V> poll();
  
  public abstract Future<V> poll(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/CompletionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */