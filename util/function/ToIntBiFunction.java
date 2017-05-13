package java.util.function;

@FunctionalInterface
public abstract interface ToIntBiFunction<T, U>
{
  public abstract int applyAsInt(T paramT, U paramU);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/function/ToIntBiFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */