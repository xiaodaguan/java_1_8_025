package java.io;

public abstract interface DataInput
{
  public abstract void readFully(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract int skipBytes(int paramInt)
    throws IOException;
  
  public abstract boolean readBoolean()
    throws IOException;
  
  public abstract byte readByte()
    throws IOException;
  
  public abstract int readUnsignedByte()
    throws IOException;
  
  public abstract short readShort()
    throws IOException;
  
  public abstract int readUnsignedShort()
    throws IOException;
  
  public abstract char readChar()
    throws IOException;
  
  public abstract int readInt()
    throws IOException;
  
  public abstract long readLong()
    throws IOException;
  
  public abstract float readFloat()
    throws IOException;
  
  public abstract double readDouble()
    throws IOException;
  
  public abstract String readLine()
    throws IOException;
  
  public abstract String readUTF()
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/DataInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */