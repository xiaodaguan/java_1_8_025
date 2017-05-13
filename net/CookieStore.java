package java.net;

import java.util.List;

public abstract interface CookieStore
{
  public abstract void add(URI paramURI, HttpCookie paramHttpCookie);
  
  public abstract List<HttpCookie> get(URI paramURI);
  
  public abstract List<HttpCookie> getCookies();
  
  public abstract List<URI> getURIs();
  
  public abstract boolean remove(URI paramURI, HttpCookie paramHttpCookie);
  
  public abstract boolean removeAll();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/CookieStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */