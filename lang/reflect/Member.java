package java.lang.reflect;

public abstract interface Member
{
  public static final int PUBLIC = 0;
  public static final int DECLARED = 1;
  
  public abstract Class<?> getDeclaringClass();
  
  public abstract String getName();
  
  public abstract int getModifiers();
  
  public abstract boolean isSynthetic();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Member.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */