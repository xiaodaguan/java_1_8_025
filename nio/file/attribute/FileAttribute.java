package java.nio.file.attribute;

public abstract interface FileAttribute<T>
{
  public abstract String name();
  
  public abstract T value();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/FileAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */