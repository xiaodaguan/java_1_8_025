package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract interface WritableByteChannel
  extends Channel
{
  public abstract int write(ByteBuffer paramByteBuffer)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/WritableByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */