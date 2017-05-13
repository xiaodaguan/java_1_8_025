package java.util.prefs;

public abstract interface PreferencesFactory
{
  public abstract Preferences systemRoot();
  
  public abstract Preferences userRoot();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/PreferencesFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */