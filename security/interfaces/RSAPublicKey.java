package java.security.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public abstract interface RSAPublicKey
  extends PublicKey, RSAKey
{
  public static final long serialVersionUID = -8727434096241101194L;
  
  public abstract BigInteger getPublicExponent();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/interfaces/RSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */