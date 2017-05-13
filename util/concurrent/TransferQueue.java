package java.util.concurrent;

public abstract interface TransferQueue<E>
  extends BlockingQueue<E>
{
  public abstract boolean tryTransfer(E paramE);
  
  public abstract void transfer(E paramE)
    throws InterruptedException;
  
  public abstract boolean tryTransfer(E paramE, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException;
  
  public abstract boolean hasWaitingConsumer();
  
  public abstract int getWaitingConsumerCount();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/TransferQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */