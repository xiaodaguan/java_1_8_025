package java.nio.file.attribute;

public abstract interface DosFileAttributes
  extends BasicFileAttributes
{
  public abstract boolean isReadOnly();
  
  public abstract boolean isHidden();
  
  public abstract boolean isArchive();
  
  public abstract boolean isSystem();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/DosFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */