package java.awt.datatransfer;

import java.io.IOException;

public abstract interface Transferable
{
  public abstract DataFlavor[] getTransferDataFlavors();
  
  public abstract boolean isDataFlavorSupported(DataFlavor paramDataFlavor);
  
  public abstract Object getTransferData(DataFlavor paramDataFlavor)
    throws UnsupportedFlavorException, IOException;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/Transferable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */