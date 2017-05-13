package java.lang.reflect;

public abstract interface TypeVariable<D extends GenericDeclaration>
  extends Type, AnnotatedElement
{
  public abstract Type[] getBounds();
  
  public abstract D getGenericDeclaration();
  
  public abstract String getName();
  
  public abstract AnnotatedType[] getAnnotatedBounds();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/TypeVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */