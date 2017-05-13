package java.security.cert;

public abstract interface CertPathBuilderResult
  extends Cloneable
{
  public abstract CertPath getCertPath();
  
  public abstract Object clone();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CertPathBuilderResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */