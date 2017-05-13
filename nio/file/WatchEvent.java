package java.nio.file;

public abstract interface WatchEvent<T>
{
  public abstract Kind<T> kind();
  
  public abstract int count();
  
  public abstract T context();
  
  public static abstract interface Modifier
  {
    public abstract String name();
  }
  
  public static abstract interface Kind<T>
  {
    public abstract String name();
    
    public abstract Class<T> type();
  }
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/WatchEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */