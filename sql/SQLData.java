package java.sql;

public abstract interface SQLData
{
  public abstract String getSQLTypeName()
    throws SQLException;
  
  public abstract void readSQL(SQLInput paramSQLInput, String paramString)
    throws SQLException;
  
  public abstract void writeSQL(SQLOutput paramSQLOutput)
    throws SQLException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/SQLData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */