package java.io;

public abstract interface Externalizable
  extends Serializable
{
  public abstract void writeExternal(ObjectOutput paramObjectOutput)
    throws IOException;
  
  public abstract void readExternal(ObjectInput paramObjectInput)
    throws IOException, ClassNotFoundException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/Externalizable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */