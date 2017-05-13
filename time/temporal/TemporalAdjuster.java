package java.time.temporal;

@FunctionalInterface
public abstract interface TemporalAdjuster
{
  public abstract Temporal adjustInto(Temporal paramTemporal);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/temporal/TemporalAdjuster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */