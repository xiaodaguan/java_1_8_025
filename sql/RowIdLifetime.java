package java.sql;

public enum RowIdLifetime
{
  ROWID_UNSUPPORTED,  ROWID_VALID_OTHER,  ROWID_VALID_SESSION,  ROWID_VALID_TRANSACTION,  ROWID_VALID_FOREVER;
  
  private RowIdLifetime() {}
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/RowIdLifetime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */