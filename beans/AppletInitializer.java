package java.beans;

import java.applet.Applet;
import java.beans.beancontext.BeanContext;

public abstract interface AppletInitializer
{
  public abstract void initialize(Applet paramApplet, BeanContext paramBeanContext);
  
  public abstract void activate(Applet paramApplet);
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/AppletInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */