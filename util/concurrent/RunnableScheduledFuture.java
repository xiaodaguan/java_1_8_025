package java.util.concurrent;

public abstract interface RunnableScheduledFuture<V>
  extends RunnableFuture<V>, ScheduledFuture<V>
{
  public abstract boolean isPeriodic();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/RunnableScheduledFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */