package java.text;

public abstract interface CharacterIterator
  extends Cloneable
{
  public static final char DONE = '￿';
  
  public abstract char first();
  
  public abstract char last();
  
  public abstract char current();
  
  public abstract char next();
  
  public abstract char previous();
  
  public abstract char setIndex(int paramInt);
  
  public abstract int getBeginIndex();
  
  public abstract int getEndIndex();
  
  public abstract int getIndex();
  
  public abstract Object clone();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/CharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */