package java.sql;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface Blob
{
  public abstract long length()
    throws SQLException;
  
  public abstract byte[] getBytes(long paramLong, int paramInt)
    throws SQLException;
  
  public abstract InputStream getBinaryStream()
    throws SQLException;
  
  public abstract long position(byte[] paramArrayOfByte, long paramLong)
    throws SQLException;
  
  public abstract long position(Blob paramBlob, long paramLong)
    throws SQLException;
  
  public abstract int setBytes(long paramLong, byte[] paramArrayOfByte)
    throws SQLException;
  
  public abstract int setBytes(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException;
  
  public abstract OutputStream setBinaryStream(long paramLong)
    throws SQLException;
  
  public abstract void truncate(long paramLong)
    throws SQLException;
  
  public abstract void free()
    throws SQLException;
  
  public abstract InputStream getBinaryStream(long paramLong1, long paramLong2)
    throws SQLException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/Blob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */