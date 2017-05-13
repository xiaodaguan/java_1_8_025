package java.security.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public abstract interface DSAPublicKey
  extends DSAKey, PublicKey
{
  public static final long serialVersionUID = 1234526332779022332L;
  
  public abstract BigInteger getY();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/interfaces/DSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */