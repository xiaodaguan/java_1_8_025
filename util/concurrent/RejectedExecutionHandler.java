package java.util.concurrent;

public abstract interface RejectedExecutionHandler
{
  public abstract void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/RejectedExecutionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */