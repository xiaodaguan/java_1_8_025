package java.nio.file.attribute;

public abstract interface BasicFileAttributes
{
  public abstract FileTime lastModifiedTime();
  
  public abstract FileTime lastAccessTime();
  
  public abstract FileTime creationTime();
  
  public abstract boolean isRegularFile();
  
  public abstract boolean isDirectory();
  
  public abstract boolean isSymbolicLink();
  
  public abstract boolean isOther();
  
  public abstract long size();
  
  public abstract Object fileKey();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/BasicFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */