package java.awt.peer;

import java.io.FilenameFilter;

public abstract interface FileDialogPeer
  extends DialogPeer
{
  public abstract void setFile(String paramString);
  
  public abstract void setDirectory(String paramString);
  
  public abstract void setFilenameFilter(FilenameFilter paramFilenameFilter);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/peer/FileDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */