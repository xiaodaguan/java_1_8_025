package java.security;

import java.util.Set;

public abstract interface AlgorithmConstraints
{
  public abstract boolean permits(Set<CryptoPrimitive> paramSet, String paramString, AlgorithmParameters paramAlgorithmParameters);
  
  public abstract boolean permits(Set<CryptoPrimitive> paramSet, Key paramKey);
  
  public abstract boolean permits(Set<CryptoPrimitive> paramSet, String paramString, Key paramKey, AlgorithmParameters paramAlgorithmParameters);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/AlgorithmConstraints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */