package java.util.concurrent;

public abstract interface RunnableFuture<V>
  extends Runnable, Future<V>
{
  public abstract void run();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/RunnableFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */