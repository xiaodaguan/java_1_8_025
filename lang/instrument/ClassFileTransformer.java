package java.lang.instrument;

import java.security.ProtectionDomain;

public abstract interface ClassFileTransformer
{
  public abstract byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfByte)
    throws IllegalClassFormatException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/instrument/ClassFileTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */