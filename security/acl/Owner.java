package java.security.acl;

import java.security.Principal;

public abstract interface Owner
{
  public abstract boolean addOwner(Principal paramPrincipal1, Principal paramPrincipal2)
    throws NotOwnerException;
  
  public abstract boolean deleteOwner(Principal paramPrincipal1, Principal paramPrincipal2)
    throws NotOwnerException, LastOwnerException;
  
  public abstract boolean isOwner(Principal paramPrincipal);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/acl/Owner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */