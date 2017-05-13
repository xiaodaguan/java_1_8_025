package java.awt.datatransfer;

import java.util.Map;

public abstract interface FlavorMap
{
  public abstract Map<DataFlavor, String> getNativesForFlavors(DataFlavor[] paramArrayOfDataFlavor);
  
  public abstract Map<String, DataFlavor> getFlavorsForNatives(String[] paramArrayOfString);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/FlavorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */