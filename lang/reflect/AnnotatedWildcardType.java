package java.lang.reflect;

public abstract interface AnnotatedWildcardType
  extends AnnotatedType
{
  public abstract AnnotatedType[] getAnnotatedLowerBounds();
  
  public abstract AnnotatedType[] getAnnotatedUpperBounds();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/AnnotatedWildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */