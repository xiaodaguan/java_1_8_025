package java.awt.peer;

import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public abstract interface DesktopPeer
{
  public abstract boolean isSupported(Desktop.Action paramAction);
  
  public abstract void open(File paramFile)
    throws IOException;
  
  public abstract void edit(File paramFile)
    throws IOException;
  
  public abstract void print(File paramFile)
    throws IOException;
  
  public abstract void mail(URI paramURI)
    throws IOException;
  
  public abstract void browse(URI paramURI)
    throws IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/DesktopPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */