package java.util.concurrent;

public abstract interface Delayed
  extends Comparable<Delayed>
{
  public abstract long getDelay(TimeUnit paramTimeUnit);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/Delayed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */