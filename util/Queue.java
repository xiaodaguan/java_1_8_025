package java.util;

public abstract interface Queue<E>
  extends Collection<E>
{
  public abstract boolean add(E paramE);
  
  public abstract boolean offer(E paramE);
  
  public abstract E remove();
  
  public abstract E poll();
  
  public abstract E element();
  
  public abstract E peek();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Queue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */