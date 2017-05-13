package java.nio.channels;

public abstract interface CompletionHandler<V, A>
{
  public abstract void completed(V paramV, A paramA);
  
  public abstract void failed(Throwable paramThrowable, A paramA);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/CompletionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */