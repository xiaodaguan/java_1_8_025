package java.util.zip;

public abstract interface Checksum
{
  public abstract void update(int paramInt);
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract long getValue();
  
  public abstract void reset();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/Checksum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */