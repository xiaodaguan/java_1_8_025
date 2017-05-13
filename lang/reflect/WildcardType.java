package java.lang.reflect;

public abstract interface WildcardType
  extends Type
{
  public abstract Type[] getUpperBounds();
  
  public abstract Type[] getLowerBounds();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/WildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */