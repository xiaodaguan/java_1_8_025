package java.security.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;

public abstract interface DSAPrivateKey
  extends DSAKey, PrivateKey
{
  public static final long serialVersionUID = 7776497482533790279L;
  
  public abstract BigInteger getX();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/interfaces/DSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */