package java.security.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;

public abstract interface RSAPrivateKey
  extends PrivateKey, RSAKey
{
  public static final long serialVersionUID = 5187144804936595022L;
  
  public abstract BigInteger getPrivateExponent();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/interfaces/RSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */