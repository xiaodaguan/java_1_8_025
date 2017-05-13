package java.awt;

import java.awt.event.KeyEvent;

@FunctionalInterface
public abstract interface KeyEventDispatcher
{
  public abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/KeyEventDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */