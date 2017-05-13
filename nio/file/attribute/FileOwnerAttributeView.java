package java.nio.file.attribute;

import java.io.IOException;

public abstract interface FileOwnerAttributeView
  extends FileAttributeView
{
  public abstract String name();
  
  public abstract UserPrincipal getOwner()
    throws IOException;
  
  public abstract void setOwner(UserPrincipal paramUserPrincipal)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/FileOwnerAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */