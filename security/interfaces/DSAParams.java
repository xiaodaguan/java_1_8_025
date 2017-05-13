package java.security.interfaces;

import java.math.BigInteger;

public abstract interface DSAParams
{
  public abstract BigInteger getP();
  
  public abstract BigInteger getQ();
  
  public abstract BigInteger getG();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/interfaces/DSAParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */