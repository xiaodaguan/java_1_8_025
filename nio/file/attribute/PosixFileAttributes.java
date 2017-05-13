package java.nio.file.attribute;

import java.util.Set;

public abstract interface PosixFileAttributes
  extends BasicFileAttributes
{
  public abstract UserPrincipal owner();
  
  public abstract GroupPrincipal group();
  
  public abstract Set<PosixFilePermission> permissions();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/PosixFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */