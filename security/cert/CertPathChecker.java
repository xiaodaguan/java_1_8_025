package java.security.cert;

public abstract interface CertPathChecker
{
  public abstract void init(boolean paramBoolean)
    throws CertPathValidatorException;
  
  public abstract boolean isForwardCheckingSupported();
  
  public abstract void check(Certificate paramCertificate)
    throws CertPathValidatorException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CertPathChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */