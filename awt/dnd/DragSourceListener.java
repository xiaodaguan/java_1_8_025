package java.awt.dnd;

import java.util.EventListener;

public abstract interface DragSourceListener
  extends EventListener
{
  public abstract void dragEnter(DragSourceDragEvent paramDragSourceDragEvent);
  
  public abstract void dragOver(DragSourceDragEvent paramDragSourceDragEvent);
  
  public abstract void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent);
  
  public abstract void dragExit(DragSourceEvent paramDragSourceEvent);
  
  public abstract void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DragSourceListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */