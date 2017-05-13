package java.security;

import java.io.Serializable;

public abstract interface Key
  extends Serializable
{
  public static final long serialVersionUID = 6603384152749567654L;
  
  public abstract String getAlgorithm();
  
  public abstract String getFormat();
  
  public abstract byte[] getEncoded();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */