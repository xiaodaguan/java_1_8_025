package java.util.stream;

import java.util.Iterator;
import java.util.Spliterator;

public abstract interface BaseStream<T, S extends BaseStream<T, S>>
  extends AutoCloseable
{
  public abstract Iterator<T> iterator();
  
  public abstract Spliterator<T> spliterator();
  
  public abstract boolean isParallel();
  
  public abstract S sequential();
  
  public abstract S parallel();
  
  public abstract S unordered();
  
  public abstract S onClose(Runnable paramRunnable);
  
  public abstract void close();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/BaseStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */