package java.nio.file;

public enum StandardOpenOption
  implements OpenOption
{
  READ,  WRITE,  APPEND,  TRUNCATE_EXISTING,  CREATE,  CREATE_NEW,  DELETE_ON_CLOSE,  SPARSE,  SYNC,  DSYNC;
  
  private StandardOpenOption() {}
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/StandardOpenOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */