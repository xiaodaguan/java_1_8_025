package java.io;

public abstract interface DataOutput
{
  public abstract void write(int paramInt)
    throws IOException;
  
  public abstract void write(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract void writeBoolean(boolean paramBoolean)
    throws IOException;
  
  public abstract void writeByte(int paramInt)
    throws IOException;
  
  public abstract void writeShort(int paramInt)
    throws IOException;
  
  public abstract void writeChar(int paramInt)
    throws IOException;
  
  public abstract void writeInt(int paramInt)
    throws IOException;
  
  public abstract void writeLong(long paramLong)
    throws IOException;
  
  public abstract void writeFloat(float paramFloat)
    throws IOException;
  
  public abstract void writeDouble(double paramDouble)
    throws IOException;
  
  public abstract void writeBytes(String paramString)
    throws IOException;
  
  public abstract void writeChars(String paramString)
    throws IOException;
  
  public abstract void writeUTF(String paramString)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/DataOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */