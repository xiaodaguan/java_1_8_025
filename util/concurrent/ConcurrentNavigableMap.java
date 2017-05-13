package java.util.concurrent;

import java.util.NavigableMap;
import java.util.NavigableSet;

public abstract interface ConcurrentNavigableMap<K, V>
  extends ConcurrentMap<K, V>, NavigableMap<K, V>
{
  public abstract ConcurrentNavigableMap<K, V> subMap(K paramK1, boolean paramBoolean1, K paramK2, boolean paramBoolean2);
  
  public abstract ConcurrentNavigableMap<K, V> headMap(K paramK, boolean paramBoolean);
  
  public abstract ConcurrentNavigableMap<K, V> tailMap(K paramK, boolean paramBoolean);
  
  public abstract ConcurrentNavigableMap<K, V> subMap(K paramK1, K paramK2);
  
  public abstract ConcurrentNavigableMap<K, V> headMap(K paramK);
  
  public abstract ConcurrentNavigableMap<K, V> tailMap(K paramK);
  
  public abstract ConcurrentNavigableMap<K, V> descendingMap();
  
  public abstract NavigableSet<K> navigableKeySet();
  
  public abstract NavigableSet<K> keySet();
  
  public abstract NavigableSet<K> descendingKeySet();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ConcurrentNavigableMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */