package java.nio.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;

public abstract interface MulticastChannel
  extends NetworkChannel
{
  public abstract void close()
    throws IOException;
  
  public abstract MembershipKey join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface)
    throws IOException;
  
  public abstract MembershipKey join(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/MulticastChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */