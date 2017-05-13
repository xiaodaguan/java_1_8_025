package java.util.logging;

@FunctionalInterface
public abstract interface Filter
{
  public abstract boolean isLoggable(LogRecord paramLogRecord);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */