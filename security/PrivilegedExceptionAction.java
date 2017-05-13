package java.security;

public abstract interface PrivilegedExceptionAction<T>
{
  public abstract T run()
    throws Exception;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/PrivilegedExceptionAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */