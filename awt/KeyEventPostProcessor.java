package java.awt;

import java.awt.event.KeyEvent;

@FunctionalInterface
public abstract interface KeyEventPostProcessor
{
  public abstract boolean postProcessKeyEvent(KeyEvent paramKeyEvent);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/KeyEventPostProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */