package java.net;

public abstract interface SocketOption<T>
{
  public abstract String name();
  
  public abstract Class<T> type();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/SocketOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */