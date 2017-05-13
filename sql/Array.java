package java.sql;

import java.util.Map;

public abstract interface Array
{
  public abstract String getBaseTypeName()
    throws SQLException;
  
  public abstract int getBaseType()
    throws SQLException;
  
  public abstract Object getArray()
    throws SQLException;
  
  public abstract Object getArray(Map<String, Class<?>> paramMap)
    throws SQLException;
  
  public abstract Object getArray(long paramLong, int paramInt)
    throws SQLException;
  
  public abstract Object getArray(long paramLong, int paramInt, Map<String, Class<?>> paramMap)
    throws SQLException;
  
  public abstract ResultSet getResultSet()
    throws SQLException;
  
  public abstract ResultSet getResultSet(Map<String, Class<?>> paramMap)
    throws SQLException;
  
  public abstract ResultSet getResultSet(long paramLong, int paramInt)
    throws SQLException;
  
  public abstract ResultSet getResultSet(long paramLong, int paramInt, Map<String, Class<?>> paramMap)
    throws SQLException;
  
  public abstract void free()
    throws SQLException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/Array.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */