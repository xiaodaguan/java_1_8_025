package java.security.cert;

public abstract interface CRLSelector
  extends Cloneable
{
  public abstract boolean match(CRL paramCRL);
  
  public abstract Object clone();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CRLSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */