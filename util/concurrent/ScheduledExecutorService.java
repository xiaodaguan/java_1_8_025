package java.util.concurrent;

public abstract interface ScheduledExecutorService
  extends ExecutorService
{
  public abstract ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
  
  public abstract <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit);
  
  public abstract ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  public abstract ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ScheduledExecutorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */