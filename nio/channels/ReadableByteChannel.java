package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract interface ReadableByteChannel
  extends Channel
{
  public abstract int read(ByteBuffer paramByteBuffer)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/ReadableByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */