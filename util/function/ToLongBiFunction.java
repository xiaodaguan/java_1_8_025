package java.util.function;

@FunctionalInterface
public abstract interface ToLongBiFunction<T, U>
{
  public abstract long applyAsLong(T paramT, U paramU);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/function/ToLongBiFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */