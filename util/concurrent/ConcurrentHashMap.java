/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleBinaryOperator;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.IntBinaryOperator;
/*      */ import java.util.function.LongBinaryOperator;
/*      */ import java.util.function.ToDoubleBiFunction;
/*      */ import java.util.function.ToDoubleFunction;
/*      */ import java.util.function.ToIntBiFunction;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.function.ToLongBiFunction;
/*      */ import java.util.function.ToLongFunction;
/*      */ import sun.misc.Contended;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConcurrentHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements ConcurrentMap<K, V>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7249069246763182397L;
/*      */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   private static final int DEFAULT_CAPACITY = 16;
/*      */   static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
/*      */   private static final float LOAD_FACTOR = 0.75F;
/*      */   static final int TREEIFY_THRESHOLD = 8;
/*      */   static final int UNTREEIFY_THRESHOLD = 6;
/*      */   static final int MIN_TREEIFY_CAPACITY = 64;
/*      */   private static final int MIN_TRANSFER_STRIDE = 16;
/*  578 */   private static int RESIZE_STAMP_BITS = 16;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  584 */   private static final int MAX_RESIZERS = (1 << 32 - RESIZE_STAMP_BITS) - 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  589 */   private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
/*      */   
/*      */ 
/*      */   static final int MOVED = -1;
/*      */   
/*      */   static final int TREEBIN = -2;
/*      */   
/*      */   static final int RESERVED = -3;
/*      */   
/*      */   static final int HASH_BITS = Integer.MAX_VALUE;
/*      */   
/*  600 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */   
/*      */ 
/*  603 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE) };
/*      */   volatile transient Node<K, V>[] table;
/*      */   private volatile transient Node<K, V>[] nextTable;
/*      */   private volatile transient long baseCount;
/*      */   private volatile transient int sizeCtl;
/*      */   private volatile transient int transferIndex;
/*      */   private volatile transient int cellsBusy;
/*      */   private volatile transient CounterCell[] counterCells;
/*      */   private transient KeySetView<K, V> keySet;
/*      */   private transient ValuesView<K, V> values;
/*      */   private transient EntrySetView<K, V> entrySet;
/*      */   private static final Unsafe U;
/*      */   private static final long SIZECTL;
/*      */   private static final long TRANSFERINDEX;
/*      */   private static final long BASECOUNT;
/*      */   private static final long CELLSBUSY;
/*      */   private static final long CELLVALUE;
/*      */   private static final long ABASE;
/*      */   private static final int ASHIFT;
/*      */   
/*      */   static class Node<K, V> implements Map.Entry<K, V> { final int hash;
/*      */     final K key;
/*      */     
/*  626 */     Node(int paramInt, K paramK, V paramV, Node<K, V> paramNode) { this.hash = paramInt;
/*  627 */       this.key = paramK;
/*  628 */       this.val = paramV;
/*  629 */       this.next = paramNode;
/*      */     }
/*      */     
/*  632 */     public final K getKey() { return (K)this.key; }
/*  633 */     public final V getValue() { return (V)this.val; }
/*  634 */     public final int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); }
/*  635 */     public final String toString() { return this.key + "=" + this.val; }
/*      */     
/*  637 */     public final V setValue(V paramV) { throw new UnsupportedOperationException(); }
/*      */     
/*      */ 
/*      */     public final boolean equals(Object paramObject)
/*      */     {
/*      */       Map.Entry localEntry;
/*      */       Object localObject1;
/*      */       Object localObject2;
/*      */       Object localObject3;
/*  646 */       return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = localEntry.getValue()) != null) && ((localObject1 == this.key) || (localObject1.equals(this.key))) && ((localObject2 == (localObject3 = this.val)) || (localObject2.equals(localObject3)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     Node<K, V> find(int paramInt, Object paramObject)
/*      */     {
/*  653 */       Node localNode = this;
/*  654 */       if (paramObject != null) {
/*      */         do {
/*      */           Object localObject;
/*  657 */           if ((localNode.hash == paramInt) && (((localObject = localNode.key) == paramObject) || ((localObject != null) && 
/*  658 */             (paramObject.equals(localObject)))))
/*  659 */             return localNode;
/*  660 */         } while ((localNode = localNode.next) != null);
/*      */       }
/*  662 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile V val;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     volatile Node<K, V> next;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int spread(int paramInt)
/*      */   {
/*  685 */     return (paramInt ^ paramInt >>> 16) & 0x7FFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int tableSizeFor(int paramInt)
/*      */   {
/*  693 */     int i = paramInt - 1;
/*  694 */     i |= i >>> 1;
/*  695 */     i |= i >>> 2;
/*  696 */     i |= i >>> 4;
/*  697 */     i |= i >>> 8;
/*  698 */     i |= i >>> 16;
/*  699 */     return i >= 1073741824 ? 1073741824 : i < 0 ? 1 : i + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static Class<?> comparableClassFor(Object paramObject)
/*      */   {
/*  707 */     if ((paramObject instanceof Comparable)) {
/*      */       Class localClass;
/*  709 */       if ((localClass = paramObject.getClass()) == String.class)
/*  710 */         return localClass;
/*  711 */       Type[] arrayOfType1; if ((arrayOfType1 = localClass.getGenericInterfaces()) != null)
/*  712 */         for (int i = 0; i < arrayOfType1.length; i++) { Type localType;
/*  713 */           ParameterizedType localParameterizedType; if ((((localType = arrayOfType1[i]) instanceof ParameterizedType)) && 
/*  714 */             ((localParameterizedType = (ParameterizedType)localType).getRawType() == Comparable.class)) {
/*      */             Type[] arrayOfType2;
/*  716 */             if (((arrayOfType2 = localParameterizedType.getActualTypeArguments()) != null) && (arrayOfType2.length == 1) && (arrayOfType2[0] == localClass))
/*      */             {
/*  718 */               return localClass; }
/*      */           }
/*      */         }
/*      */     }
/*  722 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static int compareComparables(Class<?> paramClass, Object paramObject1, Object paramObject2)
/*      */   {
/*  732 */     return (paramObject2 == null) || (paramObject2.getClass() != paramClass) ? 0 : ((Comparable)paramObject1).compareTo(paramObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final <K, V> Node<K, V> tabAt(Node<K, V>[] paramArrayOfNode, int paramInt)
/*      */   {
/*  755 */     return (Node)U.getObjectVolatile(paramArrayOfNode, (paramInt << ASHIFT) + ABASE);
/*      */   }
/*      */   
/*      */   static final <K, V> boolean casTabAt(Node<K, V>[] paramArrayOfNode, int paramInt, Node<K, V> paramNode1, Node<K, V> paramNode2)
/*      */   {
/*  760 */     return U.compareAndSwapObject(paramArrayOfNode, (paramInt << ASHIFT) + ABASE, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   static final <K, V> void setTabAt(Node<K, V>[] paramArrayOfNode, int paramInt, Node<K, V> paramNode) {
/*  764 */     U.putObjectVolatile(paramArrayOfNode, (paramInt << ASHIFT) + ABASE, paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMap() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMap(int paramInt)
/*      */   {
/*  837 */     if (paramInt < 0) {
/*  838 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  841 */     int i = paramInt >= 536870912 ? 1073741824 : tableSizeFor(paramInt + (paramInt >>> 1) + 1);
/*  842 */     this.sizeCtl = i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMap(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  851 */     this.sizeCtl = 16;
/*  852 */     putAll(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMap(int paramInt, float paramFloat)
/*      */   {
/*  871 */     this(paramInt, paramFloat, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMap(int paramInt1, float paramFloat, int paramInt2)
/*      */   {
/*  894 */     if ((paramFloat <= 0.0F) || (paramInt1 < 0) || (paramInt2 <= 0))
/*  895 */       throw new IllegalArgumentException();
/*  896 */     if (paramInt1 < paramInt2)
/*  897 */       paramInt1 = paramInt2;
/*  898 */     long l = (1.0D + (float)paramInt1 / paramFloat);
/*      */     
/*  900 */     int i = l >= 1073741824L ? 1073741824 : tableSizeFor((int)l);
/*  901 */     this.sizeCtl = i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  910 */     long l = sumCount();
/*  911 */     return l > 2147483647L ? Integer.MAX_VALUE : l < 0L ? 0 : (int)l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  920 */     return sumCount() <= 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V get(Object paramObject)
/*      */   {
/*  936 */     int k = spread(paramObject.hashCode());
/*  937 */     Node[] arrayOfNode; int i; Node localNode1; if (((arrayOfNode = this.table) != null) && ((i = arrayOfNode.length) > 0) && 
/*  938 */       ((localNode1 = tabAt(arrayOfNode, i - 1 & k)) != null)) { int j;
/*  939 */       Object localObject; if ((j = localNode1.hash) == k) {
/*  940 */         if (((localObject = localNode1.key) == paramObject) || ((localObject != null) && (paramObject.equals(localObject)))) {
/*  941 */           return (V)localNode1.val;
/*      */         }
/*  943 */       } else if (j < 0) { Node localNode2;
/*  944 */         return (V)((localNode2 = localNode1.find(k, paramObject)) != null ? localNode2.val : null); }
/*  945 */       while ((localNode1 = localNode1.next) != null) {
/*  946 */         if ((localNode1.hash == k) && (((localObject = localNode1.key) == paramObject) || ((localObject != null) && 
/*  947 */           (paramObject.equals(localObject)))))
/*  948 */           return (V)localNode1.val;
/*      */       }
/*      */     }
/*  951 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsKey(Object paramObject)
/*      */   {
/*  964 */     return get(paramObject) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsValue(Object paramObject)
/*      */   {
/*  978 */     if (paramObject == null)
/*  979 */       throw new NullPointerException();
/*      */     Node[] arrayOfNode;
/*  981 */     if ((arrayOfNode = this.table) != null) {
/*  982 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/*  983 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/*      */         Object localObject;
/*  985 */         if (((localObject = localNode.val) == paramObject) || ((localObject != null) && (paramObject.equals(localObject))))
/*  986 */           return true;
/*      */       }
/*      */     }
/*  989 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V put(K paramK, V paramV)
/*      */   {
/* 1006 */     return (V)putVal(paramK, paramV, false);
/*      */   }
/*      */   
/*      */   final V putVal(K paramK, V paramV, boolean paramBoolean)
/*      */   {
/* 1011 */     if ((paramK == null) || (paramV == null)) throw new NullPointerException();
/* 1012 */     int i = spread(paramK.hashCode());
/* 1013 */     int j = 0;
/* 1014 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int k;
/* 1016 */       if ((arrayOfNode == null) || ((k = arrayOfNode.length) == 0)) {
/* 1017 */         arrayOfNode = initTable(); } else { int m;
/* 1018 */         Node localNode; if ((localNode = tabAt(arrayOfNode, m = k - 1 & i)) == null) {
/* 1019 */           if (casTabAt(arrayOfNode, m, null, new Node(i, paramK, paramV, null)))
/*      */             break;
/*      */         } else {
/*      */           int n;
/* 1023 */           if ((n = localNode.hash) == -1) {
/* 1024 */             arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */           } else {
/* 1026 */             Object localObject1 = null;
/* 1027 */             synchronized (localNode) {
/* 1028 */               if (tabAt(arrayOfNode, m) == localNode) { Object localObject2;
/* 1029 */                 if (n >= 0) {
/* 1030 */                   j = 1;
/* 1031 */                   for (localObject2 = localNode;; j++)
/*      */                   {
/* 1033 */                     if (((Node)localObject2).hash == i) { Object localObject3; if ((localObject3 = ((Node)localObject2).key) != paramK) { if (localObject3 != null)
/*      */                         {
/* 1035 */                           if (!paramK.equals(localObject3)) {} }
/* 1036 */                       } else { localObject1 = ((Node)localObject2).val;
/* 1037 */                         if (paramBoolean) break;
/* 1038 */                         ((Node)localObject2).val = paramV; break;
/*      */                       }
/*      */                     }
/* 1041 */                     Object localObject4 = localObject2;
/* 1042 */                     if ((localObject2 = ((Node)localObject2).next) == null) {
/* 1043 */                       ((Node)localObject4).next = new Node(i, paramK, paramV, null);
/*      */                       
/* 1045 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/* 1049 */                 else if ((localNode instanceof TreeBin))
/*      */                 {
/* 1051 */                   j = 2;
/* 1052 */                   if ((localObject2 = ((TreeBin)localNode).putTreeVal(i, paramK, paramV)) != null)
/*      */                   {
/* 1054 */                     localObject1 = ((Node)localObject2).val;
/* 1055 */                     if (!paramBoolean)
/* 1056 */                       ((Node)localObject2).val = paramV;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1061 */             if (j != 0) {
/* 1062 */               if (j >= 8)
/* 1063 */                 treeifyBin(arrayOfNode, m);
/* 1064 */               if (localObject1 == null) break;
/* 1065 */               return (V)localObject1;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1070 */     addCount(1L, j);
/* 1071 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*      */   {
/* 1082 */     tryPresize(paramMap.size());
/* 1083 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 1084 */       putVal(localEntry.getKey(), localEntry.getValue(), false);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V remove(Object paramObject)
/*      */   {
/* 1097 */     return (V)replaceNode(paramObject, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final V replaceNode(Object paramObject1, V paramV, Object paramObject2)
/*      */   {
/* 1106 */     int i = spread(paramObject1.hashCode());
/* 1107 */     Node[] arrayOfNode = this.table;
/*      */     int j;
/* 1109 */     int k; Node localNode; while ((arrayOfNode != null) && ((j = arrayOfNode.length) != 0) && 
/* 1110 */       ((localNode = tabAt(arrayOfNode, k = j - 1 & i)) != null)) {
/*      */       int m;
/* 1112 */       if ((m = localNode.hash) == -1) {
/* 1113 */         arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */       } else {
/* 1115 */         Object localObject1 = null;
/* 1116 */         int n = 0;
/* 1117 */         synchronized (localNode) {
/* 1118 */           if (tabAt(arrayOfNode, k) == localNode) { Object localObject2;
/* 1119 */             Object localObject3; Object localObject4; Object localObject5; if (m >= 0) {
/* 1120 */               n = 1;
/* 1121 */               localObject2 = localNode;localObject3 = null;
/*      */               for (;;) {
/* 1123 */                 if (((Node)localObject2).hash == i) { if ((localObject4 = ((Node)localObject2).key) != paramObject1) { if (localObject4 != null)
/*      */                     {
/* 1125 */                       if (!paramObject1.equals(localObject4)) {} }
/* 1126 */                   } else { localObject5 = ((Node)localObject2).val;
/* 1127 */                     if ((paramObject2 != null) && (paramObject2 != localObject5) && ((localObject5 == null) || 
/* 1128 */                       (!paramObject2.equals(localObject5)))) break;
/* 1129 */                     localObject1 = localObject5;
/* 1130 */                     if (paramV != null) {
/* 1131 */                       ((Node)localObject2).val = paramV; break; }
/* 1132 */                     if (localObject3 != null) {
/* 1133 */                       ((Node)localObject3).next = ((Node)localObject2).next; break;
/*      */                     }
/* 1135 */                     setTabAt(arrayOfNode, k, ((Node)localObject2).next); break;
/*      */                   }
/*      */                 }
/*      */                 
/* 1139 */                 localObject3 = localObject2;
/* 1140 */                 if ((localObject2 = ((Node)localObject2).next) == null) {
/*      */                   break;
/*      */                 }
/*      */               }
/* 1144 */             } else if ((localNode instanceof TreeBin)) {
/* 1145 */               n = 1;
/* 1146 */               localObject2 = (TreeBin)localNode;
/*      */               
/* 1148 */               if (((localObject3 = ((TreeBin)localObject2).root) != null) && 
/* 1149 */                 ((localObject4 = ((TreeNode)localObject3).findTreeNode(i, paramObject1, null)) != null)) {
/* 1150 */                 localObject5 = ((TreeNode)localObject4).val;
/* 1151 */                 if ((paramObject2 == null) || (paramObject2 == localObject5) || ((localObject5 != null) && 
/* 1152 */                   (paramObject2.equals(localObject5)))) {
/* 1153 */                   localObject1 = localObject5;
/* 1154 */                   if (paramV != null) {
/* 1155 */                     ((TreeNode)localObject4).val = paramV;
/* 1156 */                   } else if (((TreeBin)localObject2).removeTreeNode((TreeNode)localObject4))
/* 1157 */                     setTabAt(arrayOfNode, k, untreeify(((TreeBin)localObject2).first));
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1163 */         if (n != 0) {
/* 1164 */           if (localObject1 == null) break;
/* 1165 */           if (paramV == null)
/* 1166 */             addCount(-1L, -1);
/* 1167 */           return (V)localObject1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1173 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/* 1180 */     long l = 0L;
/* 1181 */     int i = 0;
/* 1182 */     Node[] arrayOfNode = this.table;
/* 1183 */     while ((arrayOfNode != null) && (i < arrayOfNode.length))
/*      */     {
/* 1185 */       Node localNode1 = tabAt(arrayOfNode, i);
/* 1186 */       if (localNode1 == null) {
/* 1187 */         i++; } else { int j;
/* 1188 */         if ((j = localNode1.hash) == -1) {
/* 1189 */           arrayOfNode = helpTransfer(arrayOfNode, localNode1);
/* 1190 */           i = 0;
/*      */         }
/*      */         else {
/* 1193 */           synchronized (localNode1) {
/* 1194 */             if (tabAt(arrayOfNode, i) == localNode1) {
/* 1195 */               Node localNode2 = (localNode1 instanceof TreeBin) ? ((TreeBin)localNode1).first : j >= 0 ? localNode1 : null;
/*      */               
/*      */ 
/* 1198 */               while (localNode2 != null) {
/* 1199 */                 l -= 1L;
/* 1200 */                 localNode2 = localNode2.next;
/*      */               }
/* 1202 */               setTabAt(arrayOfNode, i++, null);
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1207 */     if (l != 0L) {
/* 1208 */       addCount(l, -1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public KeySetView<K, V> keySet()
/*      */   {
/*      */     KeySetView localKeySetView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1231 */     return (localKeySetView = this.keySet) != null ? localKeySetView : (this.keySet = new KeySetView(this, null));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Collection<V> values()
/*      */   {
/*      */     ValuesView localValuesView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1254 */     return (localValuesView = this.values) != null ? localValuesView : (this.values = new ValuesView(this));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/*      */     EntrySetView localEntrySetView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1276 */     return (localEntrySetView = this.entrySet) != null ? localEntrySetView : (this.entrySet = new EntrySetView(this));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1287 */     int i = 0;
/*      */     Node[] arrayOfNode;
/* 1289 */     if ((arrayOfNode = this.table) != null) {
/* 1290 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1291 */       Node localNode; while ((localNode = localTraverser.advance()) != null)
/* 1292 */         i += (localNode.key.hashCode() ^ localNode.val.hashCode());
/*      */     }
/* 1294 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1310 */     int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 1311 */     Traverser localTraverser = new Traverser(arrayOfNode, i, 0, i);
/* 1312 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1313 */     localStringBuilder.append('{');
/*      */     Node localNode;
/* 1315 */     if ((localNode = localTraverser.advance()) != null) {
/*      */       for (;;) {
/* 1317 */         Object localObject1 = localNode.key;
/* 1318 */         Object localObject2 = localNode.val;
/* 1319 */         localStringBuilder.append(localObject1 == this ? "(this Map)" : localObject1);
/* 1320 */         localStringBuilder.append('=');
/* 1321 */         localStringBuilder.append(localObject2 == this ? "(this Map)" : localObject2);
/* 1322 */         if ((localNode = localTraverser.advance()) == null)
/*      */           break;
/* 1324 */         localStringBuilder.append(',').append(' ');
/*      */       }
/*      */     }
/* 1327 */     return '}';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */     Object localObject2;
/*      */     
/*      */     Object localObject3;
/*      */     
/* 1341 */     if (paramObject != this) {
/* 1342 */       if (!(paramObject instanceof Map))
/* 1343 */         return false;
/* 1344 */       Map localMap = (Map)paramObject;
/*      */       Node[] arrayOfNode;
/* 1346 */       int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 1347 */       Traverser localTraverser = new Traverser(arrayOfNode, i, 0, i);
/* 1348 */       while ((localObject1 = localTraverser.advance()) != null) {
/* 1349 */         localObject2 = ((Node)localObject1).val;
/* 1350 */         localObject3 = localMap.get(((Node)localObject1).key);
/* 1351 */         if ((localObject3 == null) || ((localObject3 != localObject2) && (!localObject3.equals(localObject2))))
/* 1352 */           return false;
/*      */       }
/* 1354 */       for (localObject1 = localMap.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/*      */         Object localObject4;
/* 1356 */         Object localObject5; if (((localObject3 = ((Map.Entry)localObject2).getKey()) == null) || 
/* 1357 */           ((localObject4 = ((Map.Entry)localObject2).getValue()) == null) || 
/* 1358 */           ((localObject5 = get(localObject3)) == null) || ((localObject4 != localObject5) && 
/* 1359 */           (!localObject4.equals(localObject5))))
/* 1360 */           return false;
/*      */       }
/*      */     }
/* 1363 */     return true;
/*      */   }
/*      */   
/*      */   static class Segment<K, V> extends ReentrantLock implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     final float loadFactor;
/*      */     
/*      */     Segment(float paramFloat)
/*      */     {
/* 1373 */       this.loadFactor = paramFloat;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1390 */     int i = 0;
/* 1391 */     int j = 1;
/* 1392 */     while (j < 16) {
/* 1393 */       i++;
/* 1394 */       j <<= 1;
/*      */     }
/* 1396 */     int k = 32 - i;
/* 1397 */     int m = j - 1;
/*      */     
/* 1399 */     Segment[] arrayOfSegment = (Segment[])new Segment[16];
/*      */     
/* 1401 */     for (int n = 0; n < arrayOfSegment.length; n++)
/* 1402 */       arrayOfSegment[n] = new Segment(0.75F);
/* 1403 */     paramObjectOutputStream.putFields().put("segments", arrayOfSegment);
/* 1404 */     paramObjectOutputStream.putFields().put("segmentShift", k);
/* 1405 */     paramObjectOutputStream.putFields().put("segmentMask", m);
/* 1406 */     paramObjectOutputStream.writeFields();
/*      */     
/*      */     Node[] arrayOfNode;
/* 1409 */     if ((arrayOfNode = this.table) != null) {
/* 1410 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1411 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 1412 */         paramObjectOutputStream.writeObject(localNode.key);
/* 1413 */         paramObjectOutputStream.writeObject(localNode.val);
/*      */       }
/*      */     }
/* 1416 */     paramObjectOutputStream.writeObject(null);
/* 1417 */     paramObjectOutputStream.writeObject(null);
/* 1418 */     arrayOfSegment = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1437 */     this.sizeCtl = -1;
/* 1438 */     paramObjectInputStream.defaultReadObject();
/* 1439 */     long l1 = 0L;
/* 1440 */     Object localObject1 = null;
/*      */     for (;;)
/*      */     {
/* 1443 */       Object localObject2 = paramObjectInputStream.readObject();
/*      */       
/* 1445 */       Object localObject3 = paramObjectInputStream.readObject();
/* 1446 */       if ((localObject2 == null) || (localObject3 == null)) break;
/* 1447 */       localObject1 = new Node(spread(localObject2.hashCode()), localObject2, localObject3, (Node)localObject1);
/* 1448 */       l1 += 1L;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1453 */     if (l1 == 0L) {
/* 1454 */       this.sizeCtl = 0;
/*      */     } else {
/*      */       int i;
/* 1457 */       if (l1 >= 536870912L) {
/* 1458 */         i = 1073741824;
/*      */       } else {
/* 1460 */         int j = (int)l1;
/* 1461 */         i = tableSizeFor(j + (j >>> 1) + 1);
/*      */       }
/*      */       
/* 1464 */       Node[] arrayOfNode = (Node[])new Node[i];
/* 1465 */       int k = i - 1;
/* 1466 */       long l2 = 0L;
/* 1467 */       while (localObject1 != null)
/*      */       {
/* 1469 */         Node localNode1 = ((Node)localObject1).next;
/* 1470 */         int n = ((Node)localObject1).hash;int i1 = n & k;
/* 1471 */         Node localNode2; int m; if ((localNode2 = tabAt(arrayOfNode, i1)) == null) {
/* 1472 */           m = 1;
/*      */         } else {
/* 1474 */           Object localObject4 = ((Node)localObject1).key;
/* 1475 */           if (localNode2.hash < 0) {
/* 1476 */             TreeBin localTreeBin = (TreeBin)localNode2;
/* 1477 */             if (localTreeBin.putTreeVal(n, localObject4, ((Node)localObject1).val) == null)
/* 1478 */               l2 += 1L;
/* 1479 */             m = 0;
/*      */           }
/*      */           else {
/* 1482 */             int i2 = 0;
/* 1483 */             m = 1;
/*      */             
/* 1485 */             for (Object localObject5 = localNode2; localObject5 != null; localObject5 = ((Node)localObject5).next) {
/* 1486 */               if (((Node)localObject5).hash == n) { Object localObject6; if ((localObject6 = ((Node)localObject5).key) != localObject4) { if (localObject6 != null)
/*      */                   {
/* 1488 */                     if (!localObject4.equals(localObject6)) {} }
/* 1489 */                 } else { m = 0;
/* 1490 */                   break;
/*      */                 } }
/* 1492 */               i2++;
/*      */             }
/* 1494 */             if ((m != 0) && (i2 >= 8)) {
/* 1495 */               m = 0;
/* 1496 */               l2 += 1L;
/* 1497 */               ((Node)localObject1).next = localNode2;
/* 1498 */               Object localObject7 = null;Object localObject8 = null;
/* 1499 */               for (localObject5 = localObject1; localObject5 != null; localObject5 = ((Node)localObject5).next) {
/* 1500 */                 TreeNode localTreeNode = new TreeNode(((Node)localObject5).hash, ((Node)localObject5).key, ((Node)localObject5).val, null, null);
/*      */                 
/* 1502 */                 if ((localTreeNode.prev = localObject8) == null) {
/* 1503 */                   localObject7 = localTreeNode;
/*      */                 } else
/* 1505 */                   ((TreeNode)localObject8).next = localTreeNode;
/* 1506 */                 localObject8 = localTreeNode;
/*      */               }
/* 1508 */               setTabAt(arrayOfNode, i1, new TreeBin((TreeNode)localObject7));
/*      */             }
/*      */           }
/*      */         }
/* 1512 */         if (m != 0) {
/* 1513 */           l2 += 1L;
/* 1514 */           ((Node)localObject1).next = localNode2;
/* 1515 */           setTabAt(arrayOfNode, i1, (Node)localObject1);
/*      */         }
/* 1517 */         localObject1 = localNode1;
/*      */       }
/* 1519 */       this.table = arrayOfNode;
/* 1520 */       this.sizeCtl = (i - (i >>> 2));
/* 1521 */       this.baseCount = l2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V putIfAbsent(K paramK, V paramV)
/*      */   {
/* 1535 */     return (V)putVal(paramK, paramV, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean remove(Object paramObject1, Object paramObject2)
/*      */   {
/* 1544 */     if (paramObject1 == null)
/* 1545 */       throw new NullPointerException();
/* 1546 */     return (paramObject2 != null) && (replaceNode(paramObject1, null, paramObject2) != null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replace(K paramK, V paramV1, V paramV2)
/*      */   {
/* 1555 */     if ((paramK == null) || (paramV1 == null) || (paramV2 == null))
/* 1556 */       throw new NullPointerException();
/* 1557 */     return replaceNode(paramK, paramV2, paramV1) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V replace(K paramK, V paramV)
/*      */   {
/* 1568 */     if ((paramK == null) || (paramV == null))
/* 1569 */       throw new NullPointerException();
/* 1570 */     return (V)replaceNode(paramK, paramV, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V getOrDefault(Object paramObject, V paramV)
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1588 */     return (localObject = get(paramObject)) == null ? paramV : localObject;
/*      */   }
/*      */   
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 1592 */     if (paramBiConsumer == null) throw new NullPointerException();
/*      */     Node[] arrayOfNode;
/* 1594 */     if ((arrayOfNode = this.table) != null) {
/* 1595 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1596 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 1597 */         paramBiConsumer.accept(localNode.key, localNode.val);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 1603 */     if (paramBiFunction == null) throw new NullPointerException();
/*      */     Node[] arrayOfNode;
/* 1605 */     if ((arrayOfNode = this.table) != null) {
/* 1606 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1607 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 1608 */         Object localObject1 = localNode.val;
/* 1609 */         Object localObject2 = localNode.key;
/* 1610 */         for (;;) { Object localObject3 = paramBiFunction.apply(localObject2, localObject1);
/* 1611 */           if (localObject3 == null)
/* 1612 */             throw new NullPointerException();
/* 1613 */           if ((replaceNode(localObject2, localObject3, localObject1) != null) || 
/* 1614 */             ((localObject1 = get(localObject2)) == null)) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction)
/*      */   {
/* 1644 */     if ((paramK == null) || (paramFunction == null))
/* 1645 */       throw new NullPointerException();
/* 1646 */     int i = spread(paramK.hashCode());
/* 1647 */     Object localObject1 = null;
/* 1648 */     int j = 0;
/* 1649 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int k;
/* 1651 */       if ((arrayOfNode == null) || ((k = arrayOfNode.length) == 0)) {
/* 1652 */         arrayOfNode = initTable(); } else { int m;
/* 1653 */         Node localNode; Object localObject2; if ((localNode = tabAt(arrayOfNode, m = k - 1 & i)) == null) {
/* 1654 */           ReservationNode localReservationNode = new ReservationNode();
/* 1655 */           synchronized (localReservationNode) {
/* 1656 */             if (casTabAt(arrayOfNode, m, null, localReservationNode)) {
/* 1657 */               j = 1;
/* 1658 */               localObject2 = null;
/*      */               try {
/* 1660 */                 if ((localObject1 = paramFunction.apply(paramK)) != null)
/* 1661 */                   localObject2 = new Node(i, paramK, localObject1, null);
/*      */               } finally {
/* 1663 */                 setTabAt(arrayOfNode, m, (Node)localObject2);
/*      */               }
/*      */             }
/*      */           }
/* 1667 */           if (j != 0)
/*      */             break;
/*      */         } else { int n;
/* 1670 */           if ((n = localNode.hash) == -1) {
/* 1671 */             arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */           } else {
/* 1673 */             int i1 = 0;
/* 1674 */             synchronized (localNode) {
/* 1675 */               if (tabAt(arrayOfNode, m) == localNode) { Object localObject4;
/* 1676 */                 if (n >= 0) {
/* 1677 */                   j = 1;
/* 1678 */                   for (localObject2 = localNode;; j++)
/*      */                   {
/* 1680 */                     if (((Node)localObject2).hash == i) if ((localObject4 = ((Node)localObject2).key) != paramK) { if (localObject4 != null)
/*      */                         {
/* 1682 */                           if (!paramK.equals(localObject4)) {} }
/* 1683 */                       } else { localObject1 = ((Node)localObject2).val;
/* 1684 */                         break;
/*      */                       }
/* 1686 */                     Object localObject6 = localObject2;
/* 1687 */                     if ((localObject2 = ((Node)localObject2).next) == null) {
/* 1688 */                       if ((localObject1 = paramFunction.apply(paramK)) == null) break;
/* 1689 */                       i1 = 1;
/* 1690 */                       ((Node)localObject6).next = new Node(i, paramK, localObject1, null); break;
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   
/*      */                 }
/* 1696 */                 else if ((localNode instanceof TreeBin)) {
/* 1697 */                   j = 2;
/* 1698 */                   localObject2 = (TreeBin)localNode;
/*      */                   TreeNode localTreeNode;
/* 1700 */                   if (((localObject4 = ((TreeBin)localObject2).root) != null) && 
/* 1701 */                     ((localTreeNode = ((TreeNode)localObject4).findTreeNode(i, paramK, null)) != null)) {
/* 1702 */                     localObject1 = localTreeNode.val;
/* 1703 */                   } else if ((localObject1 = paramFunction.apply(paramK)) != null) {
/* 1704 */                     i1 = 1;
/* 1705 */                     ((TreeBin)localObject2).putTreeVal(i, paramK, localObject1);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1710 */             if (j != 0) {
/* 1711 */               if (j >= 8)
/* 1712 */                 treeifyBin(arrayOfNode, m);
/* 1713 */               if (i1 != 0) break;
/* 1714 */               return (V)localObject1;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1719 */     if (localObject1 != null)
/* 1720 */       addCount(1L, j);
/* 1721 */     return (V)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1745 */     if ((paramK == null) || (paramBiFunction == null))
/* 1746 */       throw new NullPointerException();
/* 1747 */     int i = spread(paramK.hashCode());
/* 1748 */     Object localObject1 = null;
/* 1749 */     int j = 0;
/* 1750 */     int k = 0;
/* 1751 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int m;
/* 1753 */       if ((arrayOfNode == null) || ((m = arrayOfNode.length) == 0)) {
/* 1754 */         arrayOfNode = initTable(); } else { int n;
/* 1755 */         Node localNode1; if ((localNode1 = tabAt(arrayOfNode, n = m - 1 & i)) == null) break;
/*      */         int i1;
/* 1757 */         if ((i1 = localNode1.hash) == -1) {
/* 1758 */           arrayOfNode = helpTransfer(arrayOfNode, localNode1);
/*      */         } else {
/* 1760 */           synchronized (localNode1) {
/* 1761 */             if (tabAt(arrayOfNode, n) == localNode1) { Object localObject2;
/* 1762 */               Object localObject3; Object localObject4; if (i1 >= 0) {
/* 1763 */                 k = 1;
/* 1764 */                 localObject2 = localNode1; for (localObject3 = null;; k++)
/*      */                 {
/* 1766 */                   if (((Node)localObject2).hash == i) if ((localObject4 = ((Node)localObject2).key) != paramK) { if (localObject4 != null)
/*      */                       {
/* 1768 */                         if (!paramK.equals(localObject4)) {} }
/* 1769 */                     } else { localObject1 = paramBiFunction.apply(paramK, ((Node)localObject2).val);
/* 1770 */                       if (localObject1 != null) {
/* 1771 */                         ((Node)localObject2).val = localObject1; break;
/*      */                       }
/* 1773 */                       j = -1;
/* 1774 */                       Node localNode2 = ((Node)localObject2).next;
/* 1775 */                       if (localObject3 != null) {
/* 1776 */                         ((Node)localObject3).next = localNode2;
/*      */                       } else {
/* 1778 */                         setTabAt(arrayOfNode, n, localNode2);
/*      */                       }
/* 1780 */                       break;
/*      */                     }
/* 1782 */                   localObject3 = localObject2;
/* 1783 */                   if ((localObject2 = ((Node)localObject2).next) == null) {
/*      */                     break;
/*      */                   }
/*      */                 }
/* 1787 */               } else if ((localNode1 instanceof TreeBin)) {
/* 1788 */                 k = 2;
/* 1789 */                 localObject2 = (TreeBin)localNode1;
/*      */                 
/* 1791 */                 if (((localObject3 = ((TreeBin)localObject2).root) != null) && 
/* 1792 */                   ((localObject4 = ((TreeNode)localObject3).findTreeNode(i, paramK, null)) != null)) {
/* 1793 */                   localObject1 = paramBiFunction.apply(paramK, ((TreeNode)localObject4).val);
/* 1794 */                   if (localObject1 != null) {
/* 1795 */                     ((TreeNode)localObject4).val = localObject1;
/*      */                   } else {
/* 1797 */                     j = -1;
/* 1798 */                     if (((TreeBin)localObject2).removeTreeNode((TreeNode)localObject4))
/* 1799 */                       setTabAt(arrayOfNode, n, untreeify(((TreeBin)localObject2).first));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 1805 */           if (k != 0) break;
/*      */         }
/*      */       }
/*      */     }
/* 1809 */     if (j != 0)
/* 1810 */       addCount(j, k);
/* 1811 */     return (V)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1836 */     if ((paramK == null) || (paramBiFunction == null))
/* 1837 */       throw new NullPointerException();
/* 1838 */     int i = spread(paramK.hashCode());
/* 1839 */     Object localObject1 = null;
/* 1840 */     int j = 0;
/* 1841 */     int k = 0;
/* 1842 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int m;
/* 1844 */       if ((arrayOfNode == null) || ((m = arrayOfNode.length) == 0)) {
/* 1845 */         arrayOfNode = initTable(); } else { int n;
/* 1846 */         Node localNode; Object localObject2; if ((localNode = tabAt(arrayOfNode, n = m - 1 & i)) == null) {
/* 1847 */           ReservationNode localReservationNode = new ReservationNode();
/* 1848 */           synchronized (localReservationNode) {
/* 1849 */             if (casTabAt(arrayOfNode, n, null, localReservationNode)) {
/* 1850 */               k = 1;
/* 1851 */               localObject2 = null;
/*      */               try {
/* 1853 */                 if ((localObject1 = paramBiFunction.apply(paramK, null)) != null) {
/* 1854 */                   j = 1;
/* 1855 */                   localObject2 = new Node(i, paramK, localObject1, null);
/*      */                 }
/*      */               } finally {
/* 1858 */                 setTabAt(arrayOfNode, n, (Node)localObject2);
/*      */               }
/*      */             }
/*      */           }
/* 1862 */           if (k != 0)
/*      */             break;
/*      */         } else { int i1;
/* 1865 */           if ((i1 = localNode.hash) == -1) {
/* 1866 */             arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */           } else {
/* 1868 */             synchronized (localNode) {
/* 1869 */               if (tabAt(arrayOfNode, n) == localNode) { Object localObject4;
/* 1870 */                 Object localObject6; if (i1 >= 0) {
/* 1871 */                   k = 1;
/* 1872 */                   ??? = localNode; for (localObject2 = null;; k++)
/*      */                   {
/* 1874 */                     if (((Node)???).hash == i) if ((localObject4 = ((Node)???).key) != paramK) { if (localObject4 != null)
/*      */                         {
/* 1876 */                           if (!paramK.equals(localObject4)) {} }
/* 1877 */                       } else { localObject1 = paramBiFunction.apply(paramK, ((Node)???).val);
/* 1878 */                         if (localObject1 != null) {
/* 1879 */                           ((Node)???).val = localObject1; break;
/*      */                         }
/* 1881 */                         j = -1;
/* 1882 */                         localObject6 = ((Node)???).next;
/* 1883 */                         if (localObject2 != null) {
/* 1884 */                           ((Node)localObject2).next = ((Node)localObject6);
/*      */                         } else {
/* 1886 */                           setTabAt(arrayOfNode, n, (Node)localObject6);
/*      */                         }
/* 1888 */                         break;
/*      */                       }
/* 1890 */                     localObject2 = ???;
/* 1891 */                     if ((??? = ((Node)???).next) == null) {
/* 1892 */                       localObject1 = paramBiFunction.apply(paramK, null);
/* 1893 */                       if (localObject1 == null) break;
/* 1894 */                       j = 1;
/* 1895 */                       ((Node)localObject2).next = new Node(i, paramK, localObject1, null); break;
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/* 1902 */                 else if ((localNode instanceof TreeBin)) {
/* 1903 */                   k = 1;
/* 1904 */                   ??? = (TreeBin)localNode;
/*      */                   
/* 1906 */                   if ((localObject2 = ((TreeBin)???).root) != null) {
/* 1907 */                     localObject4 = ((TreeNode)localObject2).findTreeNode(i, paramK, null);
/*      */                   } else
/* 1909 */                     localObject4 = null;
/* 1910 */                   localObject6 = localObject4 == null ? null : ((TreeNode)localObject4).val;
/* 1911 */                   localObject1 = paramBiFunction.apply(paramK, localObject6);
/* 1912 */                   if (localObject1 != null) {
/* 1913 */                     if (localObject4 != null) {
/* 1914 */                       ((TreeNode)localObject4).val = localObject1;
/*      */                     } else {
/* 1916 */                       j = 1;
/* 1917 */                       ((TreeBin)???).putTreeVal(i, paramK, localObject1);
/*      */                     }
/*      */                   }
/* 1920 */                   else if (localObject4 != null) {
/* 1921 */                     j = -1;
/* 1922 */                     if (((TreeBin)???).removeTreeNode((TreeNode)localObject4))
/* 1923 */                       setTabAt(arrayOfNode, n, untreeify(((TreeBin)???).first));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1928 */             if (k != 0) {
/* 1929 */               if (k < 8) break;
/* 1930 */               treeifyBin(arrayOfNode, n); break;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1935 */     if (j != 0)
/* 1936 */       addCount(j, k);
/* 1937 */     return (V)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1961 */     if ((paramK == null) || (paramV == null) || (paramBiFunction == null))
/* 1962 */       throw new NullPointerException();
/* 1963 */     int i = spread(paramK.hashCode());
/* 1964 */     Object localObject1 = null;
/* 1965 */     int j = 0;
/* 1966 */     int k = 0;
/* 1967 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int m;
/* 1969 */       if ((arrayOfNode == null) || ((m = arrayOfNode.length) == 0)) {
/* 1970 */         arrayOfNode = initTable(); } else { int n;
/* 1971 */         Node localNode1; if ((localNode1 = tabAt(arrayOfNode, n = m - 1 & i)) == null) {
/* 1972 */           if (casTabAt(arrayOfNode, n, null, new Node(i, paramK, paramV, null))) {
/* 1973 */             j = 1;
/* 1974 */             localObject1 = paramV;
/* 1975 */             break;
/*      */           }
/*      */         } else { int i1;
/* 1978 */           if ((i1 = localNode1.hash) == -1) {
/* 1979 */             arrayOfNode = helpTransfer(arrayOfNode, localNode1);
/*      */           } else {
/* 1981 */             synchronized (localNode1) {
/* 1982 */               if (tabAt(arrayOfNode, n) == localNode1) { Object localObject2;
/* 1983 */                 Object localObject3; Object localObject4; if (i1 >= 0) {
/* 1984 */                   k = 1;
/* 1985 */                   localObject2 = localNode1; for (localObject3 = null;; k++)
/*      */                   {
/* 1987 */                     if (((Node)localObject2).hash == i) if ((localObject4 = ((Node)localObject2).key) != paramK) { if (localObject4 != null)
/*      */                         {
/* 1989 */                           if (!paramK.equals(localObject4)) {} }
/* 1990 */                       } else { localObject1 = paramBiFunction.apply(((Node)localObject2).val, paramV);
/* 1991 */                         if (localObject1 != null) {
/* 1992 */                           ((Node)localObject2).val = localObject1; break;
/*      */                         }
/* 1994 */                         j = -1;
/* 1995 */                         Node localNode2 = ((Node)localObject2).next;
/* 1996 */                         if (localObject3 != null) {
/* 1997 */                           ((Node)localObject3).next = localNode2;
/*      */                         } else {
/* 1999 */                           setTabAt(arrayOfNode, n, localNode2);
/*      */                         }
/* 2001 */                         break;
/*      */                       }
/* 2003 */                     localObject3 = localObject2;
/* 2004 */                     if ((localObject2 = ((Node)localObject2).next) == null) {
/* 2005 */                       j = 1;
/* 2006 */                       localObject1 = paramV;
/* 2007 */                       ((Node)localObject3).next = new Node(i, paramK, localObject1, null);
/*      */                       
/* 2009 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/* 2013 */                 else if ((localNode1 instanceof TreeBin)) {
/* 2014 */                   k = 2;
/* 2015 */                   localObject2 = (TreeBin)localNode1;
/* 2016 */                   localObject3 = ((TreeBin)localObject2).root;
/*      */                   
/* 2018 */                   localObject4 = localObject3 == null ? null : ((TreeNode)localObject3).findTreeNode(i, paramK, null);
/*      */                   
/* 2020 */                   localObject1 = localObject4 == null ? paramV : paramBiFunction.apply(((TreeNode)localObject4).val, paramV);
/* 2021 */                   if (localObject1 != null) {
/* 2022 */                     if (localObject4 != null) {
/* 2023 */                       ((TreeNode)localObject4).val = localObject1;
/*      */                     } else {
/* 2025 */                       j = 1;
/* 2026 */                       ((TreeBin)localObject2).putTreeVal(i, paramK, localObject1);
/*      */                     }
/*      */                   }
/* 2029 */                   else if (localObject4 != null) {
/* 2030 */                     j = -1;
/* 2031 */                     if (((TreeBin)localObject2).removeTreeNode((TreeNode)localObject4))
/* 2032 */                       setTabAt(arrayOfNode, n, untreeify(((TreeBin)localObject2).first));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 2037 */             if (k != 0) {
/* 2038 */               if (k < 8) break;
/* 2039 */               treeifyBin(arrayOfNode, n); break;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 2044 */     if (j != 0)
/* 2045 */       addCount(j, k);
/* 2046 */     return (V)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(Object paramObject)
/*      */   {
/* 2067 */     return containsValue(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<K> keys()
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/* 2078 */     int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 2079 */     return new KeyIterator(arrayOfNode, i, 0, i, this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<V> elements()
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/* 2090 */     int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 2091 */     return new ValueIterator(arrayOfNode, i, 0, i, this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long mappingCount()
/*      */   {
/* 2107 */     long l = sumCount();
/* 2108 */     return l < 0L ? 0L : l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> KeySetView<K, Boolean> newKeySet()
/*      */   {
/* 2120 */     return new KeySetView(new ConcurrentHashMap(), Boolean.TRUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> KeySetView<K, Boolean> newKeySet(int paramInt)
/*      */   {
/* 2137 */     return new KeySetView(new ConcurrentHashMap(paramInt), Boolean.TRUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public KeySetView<K, V> keySet(V paramV)
/*      */   {
/* 2153 */     if (paramV == null)
/* 2154 */       throw new NullPointerException();
/* 2155 */     return new KeySetView(this, paramV);
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ForwardingNode<K, V>
/*      */     extends ConcurrentHashMap.Node<K, V>
/*      */   {
/*      */     final ConcurrentHashMap.Node<K, V>[] nextTable;
/*      */     
/*      */     ForwardingNode(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode)
/*      */     {
/* 2166 */       super(null, null, null);
/* 2167 */       this.nextTable = paramArrayOfNode;
/*      */     }
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> find(int paramInt, Object paramObject)
/*      */     {
/* 2172 */       ConcurrentHashMap.Node[] arrayOfNode = this.nextTable;
/*      */       int i;
/* 2174 */       ConcurrentHashMap.Node localNode; if ((paramObject == null) || (arrayOfNode == null) || ((i = arrayOfNode.length) == 0) || 
/* 2175 */         ((localNode = ConcurrentHashMap.tabAt(arrayOfNode, i - 1 & paramInt)) == null))
/* 2176 */         return null;
/*      */       for (;;) { int j;
/*      */         Object localObject;
/* 2179 */         if (((j = localNode.hash) == paramInt) && (((localObject = localNode.key) == paramObject) || ((localObject != null) && 
/* 2180 */           (paramObject.equals(localObject)))))
/* 2181 */           return localNode;
/* 2182 */         if (j < 0) {
/* 2183 */           if ((localNode instanceof ForwardingNode)) {
/* 2184 */             arrayOfNode = ((ForwardingNode)localNode).nextTable;
/* 2185 */             break;
/*      */           }
/*      */           
/* 2188 */           return localNode.find(paramInt, paramObject);
/*      */         }
/* 2190 */         if ((localNode = localNode.next) == null) {
/* 2191 */           return null;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReservationNode<K, V>
/*      */     extends ConcurrentHashMap.Node<K, V>
/*      */   {
/*      */     ReservationNode()
/*      */     {
/* 2202 */       super(null, null, null);
/*      */     }
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> find(int paramInt, Object paramObject) {
/* 2206 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int resizeStamp(int paramInt)
/*      */   {
/* 2217 */     return Integer.numberOfLeadingZeros(paramInt) | 1 << RESIZE_STAMP_BITS - 1;
/*      */   }
/*      */   
/*      */ 
/*      */   private final Node<K, V>[] initTable()
/*      */   {
/*      */     Object localObject1;
/*      */     
/* 2225 */     while (((localObject1 = this.table) == null) || (localObject1.length == 0)) { int i;
/* 2226 */       if ((i = this.sizeCtl) < 0) {
/* 2227 */         Thread.yield();
/* 2228 */       } else if (U.compareAndSwapInt(this, SIZECTL, i, -1)) {
/*      */         try {
/* 2230 */           if (((localObject1 = this.table) == null) || (localObject1.length == 0)) {
/* 2231 */             int j = i > 0 ? i : 16;
/*      */             
/* 2233 */             Node[] arrayOfNode = (Node[])new Node[j];
/* 2234 */             this.table = (localObject1 = arrayOfNode);
/* 2235 */             i = j - (j >>> 2);
/*      */           }
/*      */         } finally {
/* 2238 */           this.sizeCtl = i;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2243 */     return (Node<K, V>[])localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   private final void addCount(long paramLong, int paramInt)
/*      */   {
/*      */     CounterCell[] arrayOfCounterCell;
/*      */     
/*      */     long l1;
/*      */     
/*      */     long l2;
/*      */     
/*      */     int j;
/*      */     
/*      */     Object localObject;
/* 2258 */     if (((arrayOfCounterCell = this.counterCells) != null) || 
/* 2259 */       (!U.compareAndSwapLong(this, BASECOUNT, l1 = this.baseCount, l2 = l1 + paramLong)))
/*      */     {
/* 2261 */       boolean bool = true;
/* 2262 */       long l3; if ((arrayOfCounterCell == null) || ((j = arrayOfCounterCell.length - 1) < 0) || 
/* 2263 */         ((localObject = arrayOfCounterCell[(ThreadLocalRandom.getProbe() & j)]) == null) || 
/*      */         
/* 2265 */         (!(bool = U.compareAndSwapLong(localObject, CELLVALUE, l3 = ((CounterCell)localObject).value, l3 + paramLong)))) {
/* 2266 */         fullAddCount(paramLong, bool);
/* 2267 */         return;
/*      */       }
/* 2269 */       if (paramInt <= 1)
/* 2270 */         return;
/* 2271 */       l2 = sumCount();
/*      */     }
/* 2273 */     if (paramInt >= 0) {
/*      */       int i;
/* 2275 */       while ((l2 >= (j = this.sizeCtl)) && ((localObject = this.table) != null) && ((i = localObject.length) < 1073741824))
/*      */       {
/* 2277 */         int k = resizeStamp(i);
/* 2278 */         if (j < 0) { Node[] arrayOfNode;
/* 2279 */           if ((j >>> RESIZE_STAMP_SHIFT != k) || (j == k + 1) || (j == k + MAX_RESIZERS) || ((arrayOfNode = this.nextTable) == null) || (this.transferIndex <= 0)) {
/*      */             break;
/*      */           }
/*      */           
/* 2283 */           if (U.compareAndSwapInt(this, SIZECTL, j, j + 1)) {
/* 2284 */             transfer((Node[])localObject, arrayOfNode);
/*      */           }
/* 2286 */         } else if (U.compareAndSwapInt(this, SIZECTL, j, (k << RESIZE_STAMP_SHIFT) + 2))
/*      */         {
/* 2288 */           transfer((Node[])localObject, null); }
/* 2289 */         l2 = sumCount();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   final Node<K, V>[] helpTransfer(Node<K, V>[] paramArrayOfNode, Node<K, V> paramNode)
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/* 2299 */     if ((paramArrayOfNode != null) && ((paramNode instanceof ForwardingNode)) && ((arrayOfNode = ((ForwardingNode)paramNode).nextTable) != null))
/*      */     {
/* 2301 */       int j = resizeStamp(paramArrayOfNode.length);
/* 2302 */       int i; while ((arrayOfNode == this.nextTable) && (this.table == paramArrayOfNode) && ((i = this.sizeCtl) < 0))
/*      */       {
/* 2304 */         if ((i >>> RESIZE_STAMP_SHIFT == j) && (i != j + 1) && (i != j + MAX_RESIZERS) && (this.transferIndex > 0))
/*      */         {
/*      */ 
/* 2307 */           if (U.compareAndSwapInt(this, SIZECTL, i, i + 1)) {
/* 2308 */             transfer(paramArrayOfNode, arrayOfNode);
/*      */           }
/*      */         }
/*      */       }
/* 2312 */       return arrayOfNode;
/*      */     }
/* 2314 */     return this.table;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void tryPresize(int paramInt)
/*      */   {
/* 2324 */     int i = paramInt >= 536870912 ? 1073741824 : tableSizeFor(paramInt + (paramInt >>> 1) + 1);
/*      */     int j;
/* 2326 */     while ((j = this.sizeCtl) >= 0) {
/* 2327 */       Node[] arrayOfNode1 = this.table;
/* 2328 */       int k; if ((arrayOfNode1 == null) || ((k = arrayOfNode1.length) == 0)) {
/* 2329 */         k = j > i ? j : i;
/* 2330 */         if (U.compareAndSwapInt(this, SIZECTL, j, -1)) {
/*      */           try {
/* 2332 */             if (this.table == arrayOfNode1)
/*      */             {
/* 2334 */               Node[] arrayOfNode2 = (Node[])new Node[k];
/* 2335 */               this.table = arrayOfNode2;
/* 2336 */               j = k - (k >>> 2);
/*      */             }
/*      */           } finally {
/* 2339 */             this.sizeCtl = j;
/*      */           }
/*      */         }
/*      */       } else {
/* 2343 */         if ((i <= j) || (k >= 1073741824))
/*      */           break;
/* 2345 */         if (arrayOfNode1 == this.table) {
/* 2346 */           int m = resizeStamp(k);
/* 2347 */           if (j < 0) {
/*      */             Node[] arrayOfNode3;
/* 2349 */             if ((j >>> RESIZE_STAMP_SHIFT != m) || (j == m + 1) || (j == m + MAX_RESIZERS) || ((arrayOfNode3 = this.nextTable) == null) || (this.transferIndex <= 0)) {
/*      */               break;
/*      */             }
/*      */             
/* 2353 */             if (U.compareAndSwapInt(this, SIZECTL, j, j + 1)) {
/* 2354 */               transfer(arrayOfNode1, arrayOfNode3);
/*      */             }
/* 2356 */           } else if (U.compareAndSwapInt(this, SIZECTL, j, (m << RESIZE_STAMP_SHIFT) + 2))
/*      */           {
/* 2358 */             transfer(arrayOfNode1, null);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private final void transfer(Node<K, V>[] paramArrayOfNode1, Node<K, V>[] paramArrayOfNode2)
/*      */   {
/* 2368 */     int i = paramArrayOfNode1.length;
/* 2369 */     int j; if ((j = NCPU > 1 ? (i >>> 3) / NCPU : i) < 16)
/* 2370 */       j = 16;
/* 2371 */     if (paramArrayOfNode2 == null)
/*      */     {
/*      */       try {
/* 2374 */         Node[] arrayOfNode = (Node[])new Node[i << 1];
/* 2375 */         paramArrayOfNode2 = arrayOfNode;
/*      */       } catch (Throwable localThrowable) {
/* 2377 */         this.sizeCtl = Integer.MAX_VALUE;
/* 2378 */         return;
/*      */       }
/* 2380 */       this.nextTable = paramArrayOfNode2;
/* 2381 */       this.transferIndex = i;
/*      */     }
/* 2383 */     int k = paramArrayOfNode2.length;
/* 2384 */     ForwardingNode localForwardingNode = new ForwardingNode(paramArrayOfNode2);
/* 2385 */     boolean bool = true;
/* 2386 */     int m = 0;
/* 2387 */     int n = 0;int i1 = 0;
/*      */     for (;;) { int i3;
/* 2389 */       if (bool)
/*      */       {
/* 2391 */         n--; if ((n >= i1) || (m != 0)) {
/* 2392 */           bool = false;
/* 2393 */         } else if ((i3 = this.transferIndex) <= 0) {
/* 2394 */           n = -1;
/* 2395 */           bool = false;
/*      */         } else {
/*      */           int i4;
/* 2398 */           if (U.compareAndSwapInt(this, TRANSFERINDEX, i3, i4 = i3 > j ? i3 - j : 0))
/*      */           {
/*      */ 
/* 2401 */             i1 = i4;
/* 2402 */             n = i3 - 1;
/* 2403 */             bool = false;
/*      */           }
/*      */         }
/* 2406 */       } else if ((n < 0) || (n >= i) || (n + i >= k))
/*      */       {
/* 2408 */         if (m != 0) {
/* 2409 */           this.nextTable = null;
/* 2410 */           this.table = paramArrayOfNode2;
/* 2411 */           this.sizeCtl = ((i << 1) - (i >>> 1));
/* 2412 */           return;
/*      */         }
/* 2414 */         if (U.compareAndSwapInt(this, SIZECTL, i3 = this.sizeCtl, i3 - 1)) {
/* 2415 */           if (i3 - 2 != resizeStamp(i) << RESIZE_STAMP_SHIFT)
/* 2416 */             return;
/* 2417 */           m = bool = 1;
/* 2418 */           n = i;
/*      */         }
/*      */       } else { Node localNode;
/* 2421 */         if ((localNode = tabAt(paramArrayOfNode1, n)) == null) {
/* 2422 */           bool = casTabAt(paramArrayOfNode1, n, null, localForwardingNode); } else { int i2;
/* 2423 */           if ((i2 = localNode.hash) == -1) {
/* 2424 */             bool = true;
/*      */           } else {
/* 2426 */             synchronized (localNode) {
/* 2427 */               if (tabAt(paramArrayOfNode1, n) == localNode) { Object localObject3;
/*      */                 Object localObject4;
/* 2429 */                 Object localObject1; Object localObject2; Object localObject6; if (i2 >= 0) {
/* 2430 */                   int i5 = i2 & i;
/* 2431 */                   localObject3 = localNode;
/* 2432 */                   int i6; for (localObject4 = localNode.next; localObject4 != null; localObject4 = ((Node)localObject4).next) {
/* 2433 */                     i6 = ((Node)localObject4).hash & i;
/* 2434 */                     if (i6 != i5) {
/* 2435 */                       i5 = i6;
/* 2436 */                       localObject3 = localObject4;
/*      */                     }
/*      */                   }
/* 2439 */                   if (i5 == 0) {
/* 2440 */                     localObject1 = localObject3;
/* 2441 */                     localObject2 = null;
/*      */                   }
/*      */                   else {
/* 2444 */                     localObject2 = localObject3;
/* 2445 */                     localObject1 = null;
/*      */                   }
/* 2447 */                   for (localObject4 = localNode; localObject4 != localObject3; localObject4 = ((Node)localObject4).next) {
/* 2448 */                     i6 = ((Node)localObject4).hash;localObject6 = ((Node)localObject4).key;Object localObject7 = ((Node)localObject4).val;
/* 2449 */                     if ((i6 & i) == 0) {
/* 2450 */                       localObject1 = new Node(i6, localObject6, localObject7, (Node)localObject1);
/*      */                     } else
/* 2452 */                       localObject2 = new Node(i6, localObject6, localObject7, (Node)localObject2);
/*      */                   }
/* 2454 */                   setTabAt(paramArrayOfNode2, n, (Node)localObject1);
/* 2455 */                   setTabAt(paramArrayOfNode2, n + i, (Node)localObject2);
/* 2456 */                   setTabAt(paramArrayOfNode1, n, localForwardingNode);
/* 2457 */                   bool = true;
/*      */                 }
/* 2459 */                 else if ((localNode instanceof TreeBin)) {
/* 2460 */                   TreeBin localTreeBin = (TreeBin)localNode;
/* 2461 */                   localObject3 = null;localObject4 = null;
/* 2462 */                   Object localObject5 = null;localObject6 = null;
/* 2463 */                   int i7 = 0;int i8 = 0;
/* 2464 */                   for (Object localObject8 = localTreeBin.first; localObject8 != null; localObject8 = ((Node)localObject8).next) {
/* 2465 */                     int i9 = ((Node)localObject8).hash;
/* 2466 */                     TreeNode localTreeNode = new TreeNode(i9, ((Node)localObject8).key, ((Node)localObject8).val, null, null);
/*      */                     
/* 2468 */                     if ((i9 & i) == 0) {
/* 2469 */                       if ((localTreeNode.prev = localObject4) == null) {
/* 2470 */                         localObject3 = localTreeNode;
/*      */                       } else
/* 2472 */                         ((TreeNode)localObject4).next = localTreeNode;
/* 2473 */                       localObject4 = localTreeNode;
/* 2474 */                       i7++;
/*      */                     }
/*      */                     else {
/* 2477 */                       if ((localTreeNode.prev = localObject6) == null) {
/* 2478 */                         localObject5 = localTreeNode;
/*      */                       } else
/* 2480 */                         ((TreeNode)localObject6).next = localTreeNode;
/* 2481 */                       localObject6 = localTreeNode;
/* 2482 */                       i8++;
/*      */                     }
/*      */                   }
/* 2485 */                   localObject1 = i8 != 0 ? new TreeBin((TreeNode)localObject3) : i7 <= 6 ? untreeify((Node)localObject3) : localTreeBin;
/*      */                   
/* 2487 */                   localObject2 = i7 != 0 ? new TreeBin((TreeNode)localObject5) : i8 <= 6 ? untreeify((Node)localObject5) : localTreeBin;
/*      */                   
/* 2489 */                   setTabAt(paramArrayOfNode2, n, (Node)localObject1);
/* 2490 */                   setTabAt(paramArrayOfNode2, n + i, (Node)localObject2);
/* 2491 */                   setTabAt(paramArrayOfNode1, n, localForwardingNode);
/* 2492 */                   bool = true;
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   @Contended
/*      */   static final class CounterCell
/*      */   {
/*      */     volatile long value;
/*      */     
/* 2508 */     CounterCell(long paramLong) { this.value = paramLong; }
/*      */   }
/*      */   
/*      */   final long sumCount() {
/* 2512 */     CounterCell[] arrayOfCounterCell = this.counterCells;
/* 2513 */     long l = this.baseCount;
/* 2514 */     if (arrayOfCounterCell != null) {
/* 2515 */       for (int i = 0; i < arrayOfCounterCell.length; i++) { CounterCell localCounterCell;
/* 2516 */         if ((localCounterCell = arrayOfCounterCell[i]) != null)
/* 2517 */           l += localCounterCell.value;
/*      */       }
/*      */     }
/* 2520 */     return l;
/*      */   }
/*      */   
/*      */   private final void fullAddCount(long paramLong, boolean paramBoolean)
/*      */   {
/*      */     int i;
/* 2526 */     if ((i = ThreadLocalRandom.getProbe()) == 0) {
/* 2527 */       ThreadLocalRandom.localInit();
/* 2528 */       i = ThreadLocalRandom.getProbe();
/* 2529 */       paramBoolean = true;
/*      */     }
/* 2531 */     int j = 0;
/*      */     for (;;) { CounterCell[] arrayOfCounterCell1;
/*      */       int k;
/* 2534 */       long l; if (((arrayOfCounterCell1 = this.counterCells) != null) && ((k = arrayOfCounterCell1.length) > 0)) { CounterCell localCounterCell;
/* 2535 */         Object localObject1; int n; if ((localCounterCell = arrayOfCounterCell1[(k - 1 & i)]) == null) {
/* 2536 */           if (this.cellsBusy == 0) {
/* 2537 */             localObject1 = new CounterCell(paramLong);
/* 2538 */             if ((this.cellsBusy == 0) && 
/* 2539 */               (U.compareAndSwapInt(this, CELLSBUSY, 0, 1))) {
/* 2540 */               n = 0;
/*      */               try { CounterCell[] arrayOfCounterCell3;
/*      */                 int i1;
/* 2543 */                 int i2; if (((arrayOfCounterCell3 = this.counterCells) != null) && ((i1 = arrayOfCounterCell3.length) > 0) && (arrayOfCounterCell3[(i2 = i1 - 1 & i)] == null))
/*      */                 {
/*      */ 
/* 2546 */                   arrayOfCounterCell3[i2] = localObject1;
/* 2547 */                   n = 1;
/*      */                 }
/*      */               } finally {
/* 2550 */                 this.cellsBusy = 0;
/*      */               }
/* 2552 */               if (n == 0) continue;
/* 2553 */               break;
/*      */             }
/*      */           }
/*      */           
/* 2557 */           j = 0;
/*      */         }
/* 2559 */         else if (!paramBoolean) {
/* 2560 */           paramBoolean = true;
/* 2561 */         } else { if (U.compareAndSwapLong(localCounterCell, CELLVALUE, l = localCounterCell.value, l + paramLong))
/*      */             break;
/* 2563 */           if ((this.counterCells != arrayOfCounterCell1) || (k >= NCPU)) {
/* 2564 */             j = 0;
/* 2565 */           } else if (j == 0) {
/* 2566 */             j = 1;
/* 2567 */           } else if ((this.cellsBusy == 0) && 
/* 2568 */             (U.compareAndSwapInt(this, CELLSBUSY, 0, 1))) {
/*      */             try {
/* 2570 */               if (this.counterCells == arrayOfCounterCell1) {
/* 2571 */                 localObject1 = new CounterCell[k << 1];
/* 2572 */                 for (n = 0; n < k; n++)
/* 2573 */                   localObject1[n] = arrayOfCounterCell1[n];
/* 2574 */                 this.counterCells = ((CounterCell[])localObject1);
/*      */               }
/*      */             } finally {
/* 2577 */               this.cellsBusy = 0;
/*      */             }
/* 2579 */             j = 0;
/* 2580 */             continue;
/*      */           } }
/* 2582 */         i = ThreadLocalRandom.advanceProbe(i);
/*      */       }
/* 2584 */       else if ((this.cellsBusy == 0) && (this.counterCells == arrayOfCounterCell1) && 
/* 2585 */         (U.compareAndSwapInt(this, CELLSBUSY, 0, 1))) {
/* 2586 */         int m = 0;
/*      */         try {
/* 2588 */           if (this.counterCells == arrayOfCounterCell1) {
/* 2589 */             CounterCell[] arrayOfCounterCell2 = new CounterCell[2];
/* 2590 */             arrayOfCounterCell2[(i & 0x1)] = new CounterCell(paramLong);
/* 2591 */             this.counterCells = arrayOfCounterCell2;
/* 2592 */             m = 1;
/*      */           }
/*      */         } finally {
/* 2595 */           this.cellsBusy = 0;
/*      */         }
/* 2597 */         if (m != 0)
/*      */           break;
/*      */       } else {
/* 2600 */         if (U.compareAndSwapLong(this, BASECOUNT, l = this.baseCount, l + paramLong)) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void treeifyBin(Node<K, V>[] paramArrayOfNode, int paramInt)
/*      */   {
/* 2613 */     if (paramArrayOfNode != null) { int i;
/* 2614 */       if ((i = paramArrayOfNode.length) < 64) {
/* 2615 */         tryPresize(i << 1); } else { Node localNode1;
/* 2616 */         if (((localNode1 = tabAt(paramArrayOfNode, paramInt)) != null) && (localNode1.hash >= 0)) {
/* 2617 */           synchronized (localNode1) {
/* 2618 */             if (tabAt(paramArrayOfNode, paramInt) == localNode1) {
/* 2619 */               Object localObject1 = null;Object localObject2 = null;
/* 2620 */               for (Node localNode2 = localNode1; localNode2 != null; localNode2 = localNode2.next) {
/* 2621 */                 TreeNode localTreeNode = new TreeNode(localNode2.hash, localNode2.key, localNode2.val, null, null);
/*      */                 
/*      */ 
/* 2624 */                 if ((localTreeNode.prev = localObject2) == null) {
/* 2625 */                   localObject1 = localTreeNode;
/*      */                 } else
/* 2627 */                   ((TreeNode)localObject2).next = localTreeNode;
/* 2628 */                 localObject2 = localTreeNode;
/*      */               }
/* 2630 */               setTabAt(paramArrayOfNode, paramInt, new TreeBin((TreeNode)localObject1));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static <K, V> Node<K, V> untreeify(Node<K, V> paramNode)
/*      */   {
/* 2641 */     Object localObject1 = null;Object localObject2 = null;
/* 2642 */     for (Object localObject3 = paramNode; localObject3 != null; localObject3 = ((Node)localObject3).next) {
/* 2643 */       Node localNode = new Node(((Node)localObject3).hash, ((Node)localObject3).key, ((Node)localObject3).val, null);
/* 2644 */       if (localObject2 == null) {
/* 2645 */         localObject1 = localNode;
/*      */       } else
/* 2647 */         ((Node)localObject2).next = localNode;
/* 2648 */       localObject2 = localNode;
/*      */     }
/* 2650 */     return (Node<K, V>)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   static final class TreeNode<K, V>
/*      */     extends ConcurrentHashMap.Node<K, V>
/*      */   {
/*      */     TreeNode<K, V> parent;
/*      */     
/*      */     TreeNode<K, V> left;
/*      */     
/*      */     TreeNode<K, V> right;
/*      */     TreeNode<K, V> prev;
/*      */     boolean red;
/*      */     
/*      */     TreeNode(int paramInt, K paramK, V paramV, ConcurrentHashMap.Node<K, V> paramNode, TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2667 */       super(paramK, paramV, paramNode);
/* 2668 */       this.parent = paramTreeNode;
/*      */     }
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> find(int paramInt, Object paramObject) {
/* 2672 */       return findTreeNode(paramInt, paramObject, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final TreeNode<K, V> findTreeNode(int paramInt, Object paramObject, Class<?> paramClass)
/*      */     {
/* 2680 */       if (paramObject != null) {
/* 2681 */         Object localObject1 = this;
/*      */         do
/*      */         {
/* 2684 */           TreeNode localTreeNode2 = ((TreeNode)localObject1).left;TreeNode localTreeNode3 = ((TreeNode)localObject1).right;
/* 2685 */           int i; if ((i = ((TreeNode)localObject1).hash) > paramInt) {
/* 2686 */             localObject1 = localTreeNode2;
/* 2687 */           } else if (i < paramInt) {
/* 2688 */             localObject1 = localTreeNode3; } else { Object localObject2;
/* 2689 */             if (((localObject2 = ((TreeNode)localObject1).key) == paramObject) || ((localObject2 != null) && (paramObject.equals(localObject2))))
/* 2690 */               return (TreeNode<K, V>)localObject1;
/* 2691 */             if (localTreeNode2 == null) {
/* 2692 */               localObject1 = localTreeNode3;
/* 2693 */             } else if (localTreeNode3 == null) {
/* 2694 */               localObject1 = localTreeNode2; } else { int j;
/* 2695 */               if (((paramClass != null) || 
/* 2696 */                 ((paramClass = ConcurrentHashMap.comparableClassFor(paramObject)) != null)) && 
/* 2697 */                 ((j = ConcurrentHashMap.compareComparables(paramClass, paramObject, localObject2)) != 0)) {
/* 2698 */                 localObject1 = j < 0 ? localTreeNode2 : localTreeNode3; } else { TreeNode localTreeNode1;
/* 2699 */                 if ((localTreeNode1 = localTreeNode3.findTreeNode(paramInt, paramObject, paramClass)) != null) {
/* 2700 */                   return localTreeNode1;
/*      */                 }
/* 2702 */                 localObject1 = localTreeNode2;
/* 2703 */               } } } } while (localObject1 != null);
/*      */       }
/* 2705 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class TreeBin<K, V>
/*      */     extends ConcurrentHashMap.Node<K, V>
/*      */   {
/*      */     ConcurrentHashMap.TreeNode<K, V> root;
/*      */     
/*      */     volatile ConcurrentHashMap.TreeNode<K, V> first;
/*      */     
/*      */     volatile Thread waiter;
/*      */     
/*      */     volatile int lockState;
/*      */     
/*      */     static final int WRITER = 1;
/*      */     
/*      */     static final int WAITER = 2;
/*      */     
/*      */     static final int READER = 4;
/*      */     
/*      */     private static final Unsafe U;
/*      */     
/*      */     private static final long LOCKSTATE;
/*      */     
/*      */ 
/*      */     static int tieBreakOrder(Object paramObject1, Object paramObject2)
/*      */     {
/*      */       int i;
/*      */       
/* 2737 */       if ((paramObject1 == null) || (paramObject2 == null) || 
/*      */       
/* 2739 */         ((i = paramObject1.getClass().getName().compareTo(paramObject2.getClass().getName())) == 0)) {
/* 2740 */         i = System.identityHashCode(paramObject1) <= System.identityHashCode(paramObject2) ? -1 : 1;
/*      */       }
/* 2742 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     TreeBin(ConcurrentHashMap.TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2749 */       super(null, null, null);
/* 2750 */       this.first = paramTreeNode;
/* 2751 */       Object localObject1 = null;
/* 2752 */       ConcurrentHashMap.TreeNode localTreeNode; for (Object localObject2 = paramTreeNode; localObject2 != null; localObject2 = localTreeNode) {
/* 2753 */         localTreeNode = (ConcurrentHashMap.TreeNode)((ConcurrentHashMap.TreeNode)localObject2).next;
/* 2754 */         ((ConcurrentHashMap.TreeNode)localObject2).left = (((ConcurrentHashMap.TreeNode)localObject2).right = null);
/* 2755 */         if (localObject1 == null) {
/* 2756 */           ((ConcurrentHashMap.TreeNode)localObject2).parent = null;
/* 2757 */           ((ConcurrentHashMap.TreeNode)localObject2).red = false;
/* 2758 */           localObject1 = localObject2;
/*      */         }
/*      */         else {
/* 2761 */           Object localObject3 = ((ConcurrentHashMap.TreeNode)localObject2).key;
/* 2762 */           int i = ((ConcurrentHashMap.TreeNode)localObject2).hash;
/* 2763 */           Class localClass = null;
/* 2764 */           Object localObject4 = localObject1;
/*      */           for (;;) {
/* 2766 */             Object localObject5 = ((ConcurrentHashMap.TreeNode)localObject4).key;
/* 2767 */             int k; int j; if ((k = ((ConcurrentHashMap.TreeNode)localObject4).hash) > i) {
/* 2768 */               j = -1;
/* 2769 */             } else if (k < i) {
/* 2770 */               j = 1;
/* 2771 */             } else if (((localClass == null) && 
/* 2772 */               ((localClass = ConcurrentHashMap.comparableClassFor(localObject3)) == null)) || 
/* 2773 */               ((j = ConcurrentHashMap.compareComparables(localClass, localObject3, localObject5)) == 0))
/* 2774 */               j = tieBreakOrder(localObject3, localObject5);
/* 2775 */             Object localObject6 = localObject4;
/* 2776 */             if ((localObject4 = j <= 0 ? ((ConcurrentHashMap.TreeNode)localObject4).left : ((ConcurrentHashMap.TreeNode)localObject4).right) == null) {
/* 2777 */               ((ConcurrentHashMap.TreeNode)localObject2).parent = ((ConcurrentHashMap.TreeNode)localObject6);
/* 2778 */               if (j <= 0) {
/* 2779 */                 ((ConcurrentHashMap.TreeNode)localObject6).left = ((ConcurrentHashMap.TreeNode)localObject2);
/*      */               } else
/* 2781 */                 ((ConcurrentHashMap.TreeNode)localObject6).right = ((ConcurrentHashMap.TreeNode)localObject2);
/* 2782 */               localObject1 = balanceInsertion((ConcurrentHashMap.TreeNode)localObject1, (ConcurrentHashMap.TreeNode)localObject2);
/* 2783 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2788 */       this.root = ((ConcurrentHashMap.TreeNode)localObject1);
/* 2789 */       assert (checkInvariants(this.root));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private final void lockRoot()
/*      */     {
/* 2796 */       if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
/* 2797 */         contendedLock();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private final void unlockRoot()
/*      */     {
/* 2804 */       this.lockState = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private final void contendedLock()
/*      */     {
/* 2811 */       int i = 0;
/*      */       for (;;) { int j;
/* 2813 */         if (((j = this.lockState) & 0xFFFFFFFD) == 0) {
/* 2814 */           if (U.compareAndSwapInt(this, LOCKSTATE, j, 1)) {
/* 2815 */             if (i != 0) {
/* 2816 */               this.waiter = null;
/*      */             }
/*      */           }
/*      */         }
/* 2820 */         else if ((j & 0x2) == 0) {
/* 2821 */           if (U.compareAndSwapInt(this, LOCKSTATE, j, j | 0x2)) {
/* 2822 */             i = 1;
/* 2823 */             this.waiter = Thread.currentThread();
/*      */           }
/*      */         }
/* 2826 */         else if (i != 0) {
/* 2827 */           LockSupport.park(this);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final ConcurrentHashMap.Node<K, V> find(int paramInt, Object paramObject)
/*      */     {
/*      */       Object localObject1;
/*      */       
/* 2837 */       if (paramObject != null) {
/* 2838 */         for (localObject1 = this.first; localObject1 != null;) {
/*      */           int i;
/* 2840 */           if (((i = this.lockState) & 0x3) != 0) { Object localObject2;
/* 2841 */             if ((((ConcurrentHashMap.Node)localObject1).hash == paramInt) && (((localObject2 = ((ConcurrentHashMap.Node)localObject1).key) == paramObject) || ((localObject2 != null) && 
/* 2842 */               (paramObject.equals(localObject2)))))
/* 2843 */               return (ConcurrentHashMap.Node<K, V>)localObject1;
/* 2844 */             localObject1 = ((ConcurrentHashMap.Node)localObject1).next;
/*      */           }
/* 2846 */           else if (U.compareAndSwapInt(this, LOCKSTATE, i, i + 4))
/*      */           {
/*      */             ConcurrentHashMap.TreeNode localTreeNode2;
/*      */             try {
/*      */               ConcurrentHashMap.TreeNode localTreeNode1;
/* 2851 */               localTreeNode2 = (localTreeNode1 = this.root) == null ? null : localTreeNode1.findTreeNode(paramInt, paramObject, null);
/*      */             } finally { Thread localThread1;
/*      */               Thread localThread2;
/* 2854 */               if ((U.getAndAddInt(this, LOCKSTATE, -4) == 6) && ((localThread2 = this.waiter) != null))
/*      */               {
/* 2856 */                 LockSupport.unpark(localThread2); }
/*      */             }
/* 2858 */             return localTreeNode2;
/*      */           }
/*      */         }
/*      */       }
/* 2862 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final ConcurrentHashMap.TreeNode<K, V> putTreeVal(int paramInt, K paramK, V paramV)
/*      */     {
/* 2870 */       Class localClass = null;
/* 2871 */       int i = 0;
/* 2872 */       ConcurrentHashMap.TreeNode localTreeNode1 = this.root;
/*      */       for (;;) {
/* 2874 */         if (localTreeNode1 == null) {
/* 2875 */           this.first = (this.root = new ConcurrentHashMap.TreeNode(paramInt, paramK, paramV, null, null));
/* 2876 */           break; }
/*      */         int k;
/* 2878 */         int j; ConcurrentHashMap.TreeNode localTreeNode3; if ((k = localTreeNode1.hash) > paramInt) {
/* 2879 */           j = -1;
/* 2880 */         } else if (k < paramInt) {
/* 2881 */           j = 1; } else { Object localObject1;
/* 2882 */           if (((localObject1 = localTreeNode1.key) == paramK) || ((localObject1 != null) && (paramK.equals(localObject1))))
/* 2883 */             return localTreeNode1;
/* 2884 */           if (((localClass == null) && 
/* 2885 */             ((localClass = ConcurrentHashMap.comparableClassFor(paramK)) == null)) || 
/* 2886 */             ((j = ConcurrentHashMap.compareComparables(localClass, paramK, localObject1)) == 0)) {
/* 2887 */             if (i == 0)
/*      */             {
/* 2889 */               i = 1;
/* 2890 */               if (((localTreeNode3 = localTreeNode1.left) == null) || 
/* 2891 */                 ((localTreeNode2 = localTreeNode3.findTreeNode(paramInt, paramK, localClass)) == null)) { if ((localTreeNode3 = localTreeNode1.right) != null)
/*      */                 {
/* 2893 */                   if ((localTreeNode2 = localTreeNode3.findTreeNode(paramInt, paramK, localClass)) == null) {} }
/* 2894 */               } else return localTreeNode2;
/*      */             }
/* 2896 */             j = tieBreakOrder(paramK, localObject1);
/*      */           }
/*      */         }
/* 2899 */         ConcurrentHashMap.TreeNode localTreeNode2 = localTreeNode1;
/* 2900 */         if ((localTreeNode1 = j <= 0 ? localTreeNode1.left : localTreeNode1.right) == null) {
/* 2901 */           ConcurrentHashMap.TreeNode localTreeNode4 = this.first;
/* 2902 */           this.first = (localTreeNode3 = new ConcurrentHashMap.TreeNode(paramInt, paramK, paramV, localTreeNode4, localTreeNode2));
/* 2903 */           if (localTreeNode4 != null)
/* 2904 */             localTreeNode4.prev = localTreeNode3;
/* 2905 */           if (j <= 0) {
/* 2906 */             localTreeNode2.left = localTreeNode3;
/*      */           } else
/* 2908 */             localTreeNode2.right = localTreeNode3;
/* 2909 */           if (!localTreeNode2.red) {
/* 2910 */             localTreeNode3.red = true; break;
/*      */           }
/* 2912 */           lockRoot();
/*      */           try {
/* 2914 */             this.root = balanceInsertion(this.root, localTreeNode3);
/*      */           } finally {
/* 2916 */             unlockRoot();
/*      */           }
/*      */           
/* 2919 */           break;
/*      */         }
/*      */       }
/* 2922 */       assert (checkInvariants(this.root));
/* 2923 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final boolean removeTreeNode(ConcurrentHashMap.TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2937 */       ConcurrentHashMap.TreeNode localTreeNode1 = (ConcurrentHashMap.TreeNode)paramTreeNode.next;
/* 2938 */       ConcurrentHashMap.TreeNode localTreeNode2 = paramTreeNode.prev;
/*      */       
/* 2940 */       if (localTreeNode2 == null) {
/* 2941 */         this.first = localTreeNode1;
/*      */       } else
/* 2943 */         localTreeNode2.next = localTreeNode1;
/* 2944 */       if (localTreeNode1 != null)
/* 2945 */         localTreeNode1.prev = localTreeNode2;
/* 2946 */       if (this.first == null) {
/* 2947 */         this.root = null;
/* 2948 */         return true; }
/*      */       Object localObject1;
/* 2950 */       ConcurrentHashMap.TreeNode localTreeNode3; if (((localObject1 = this.root) == null) || (((ConcurrentHashMap.TreeNode)localObject1).right == null) || ((localTreeNode3 = ((ConcurrentHashMap.TreeNode)localObject1).left) == null) || (localTreeNode3.left == null))
/*      */       {
/* 2952 */         return true; }
/* 2953 */       lockRoot();
/*      */       try
/*      */       {
/* 2956 */         ConcurrentHashMap.TreeNode localTreeNode4 = paramTreeNode.left;
/* 2957 */         ConcurrentHashMap.TreeNode localTreeNode5 = paramTreeNode.right;
/* 2958 */         Object localObject3; Object localObject2; if ((localTreeNode4 != null) && (localTreeNode5 != null)) {
/* 2959 */           localObject3 = localTreeNode5;
/* 2960 */           ConcurrentHashMap.TreeNode localTreeNode6; while ((localTreeNode6 = ((ConcurrentHashMap.TreeNode)localObject3).left) != null)
/* 2961 */             localObject3 = localTreeNode6;
/* 2962 */           boolean bool = ((ConcurrentHashMap.TreeNode)localObject3).red;((ConcurrentHashMap.TreeNode)localObject3).red = paramTreeNode.red;paramTreeNode.red = bool;
/* 2963 */           ConcurrentHashMap.TreeNode localTreeNode7 = ((ConcurrentHashMap.TreeNode)localObject3).right;
/* 2964 */           ConcurrentHashMap.TreeNode localTreeNode8 = paramTreeNode.parent;
/* 2965 */           if (localObject3 == localTreeNode5) {
/* 2966 */             paramTreeNode.parent = ((ConcurrentHashMap.TreeNode)localObject3);
/* 2967 */             ((ConcurrentHashMap.TreeNode)localObject3).right = paramTreeNode;
/*      */           }
/*      */           else {
/* 2970 */             ConcurrentHashMap.TreeNode localTreeNode9 = ((ConcurrentHashMap.TreeNode)localObject3).parent;
/* 2971 */             if ((paramTreeNode.parent = localTreeNode9) != null) {
/* 2972 */               if (localObject3 == localTreeNode9.left) {
/* 2973 */                 localTreeNode9.left = paramTreeNode;
/*      */               } else
/* 2975 */                 localTreeNode9.right = paramTreeNode;
/*      */             }
/* 2977 */             if ((((ConcurrentHashMap.TreeNode)localObject3).right = localTreeNode5) != null)
/* 2978 */               localTreeNode5.parent = ((ConcurrentHashMap.TreeNode)localObject3);
/*      */           }
/* 2980 */           paramTreeNode.left = null;
/* 2981 */           if ((paramTreeNode.right = localTreeNode7) != null)
/* 2982 */             localTreeNode7.parent = paramTreeNode;
/* 2983 */           if ((((ConcurrentHashMap.TreeNode)localObject3).left = localTreeNode4) != null)
/* 2984 */             localTreeNode4.parent = ((ConcurrentHashMap.TreeNode)localObject3);
/* 2985 */           if ((((ConcurrentHashMap.TreeNode)localObject3).parent = localTreeNode8) == null) {
/* 2986 */             localObject1 = localObject3;
/* 2987 */           } else if (paramTreeNode == localTreeNode8.left) {
/* 2988 */             localTreeNode8.left = ((ConcurrentHashMap.TreeNode)localObject3);
/*      */           } else
/* 2990 */             localTreeNode8.right = ((ConcurrentHashMap.TreeNode)localObject3);
/* 2991 */           if (localTreeNode7 != null) {
/* 2992 */             localObject2 = localTreeNode7;
/*      */           } else {
/* 2994 */             localObject2 = paramTreeNode;
/*      */           }
/* 2996 */         } else if (localTreeNode4 != null) {
/* 2997 */           localObject2 = localTreeNode4;
/* 2998 */         } else if (localTreeNode5 != null) {
/* 2999 */           localObject2 = localTreeNode5;
/*      */         } else {
/* 3001 */           localObject2 = paramTreeNode; }
/* 3002 */         if (localObject2 != paramTreeNode) {
/* 3003 */           localObject3 = ((ConcurrentHashMap.TreeNode)localObject2).parent = paramTreeNode.parent;
/* 3004 */           if (localObject3 == null) {
/* 3005 */             localObject1 = localObject2;
/* 3006 */           } else if (paramTreeNode == ((ConcurrentHashMap.TreeNode)localObject3).left) {
/* 3007 */             ((ConcurrentHashMap.TreeNode)localObject3).left = ((ConcurrentHashMap.TreeNode)localObject2);
/*      */           } else
/* 3009 */             ((ConcurrentHashMap.TreeNode)localObject3).right = ((ConcurrentHashMap.TreeNode)localObject2);
/* 3010 */           paramTreeNode.left = (paramTreeNode.right = paramTreeNode.parent = null);
/*      */         }
/*      */         
/* 3013 */         this.root = (paramTreeNode.red ? localObject1 : balanceDeletion((ConcurrentHashMap.TreeNode)localObject1, (ConcurrentHashMap.TreeNode)localObject2));
/*      */         
/* 3015 */         if (paramTreeNode == localObject2)
/*      */         {
/* 3017 */           if ((localObject3 = paramTreeNode.parent) != null) {
/* 3018 */             if (paramTreeNode == ((ConcurrentHashMap.TreeNode)localObject3).left) {
/* 3019 */               ((ConcurrentHashMap.TreeNode)localObject3).left = null;
/* 3020 */             } else if (paramTreeNode == ((ConcurrentHashMap.TreeNode)localObject3).right)
/* 3021 */               ((ConcurrentHashMap.TreeNode)localObject3).right = null;
/* 3022 */             paramTreeNode.parent = null;
/*      */           }
/*      */         }
/*      */       } finally {
/* 3026 */         unlockRoot();
/*      */       }
/* 3028 */       assert (checkInvariants(this.root));
/* 3029 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> rotateLeft(ConcurrentHashMap.TreeNode<K, V> paramTreeNode1, ConcurrentHashMap.TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       ConcurrentHashMap.TreeNode localTreeNode1;
/*      */       
/* 3038 */       if ((paramTreeNode2 != null) && ((localTreeNode1 = paramTreeNode2.right) != null)) { ConcurrentHashMap.TreeNode localTreeNode3;
/* 3039 */         if ((localTreeNode3 = paramTreeNode2.right = localTreeNode1.left) != null)
/* 3040 */           localTreeNode3.parent = paramTreeNode2;
/* 3041 */         ConcurrentHashMap.TreeNode localTreeNode2; if ((localTreeNode2 = localTreeNode1.parent = paramTreeNode2.parent) == null) {
/* 3042 */           (paramTreeNode1 = localTreeNode1).red = false;
/* 3043 */         } else if (localTreeNode2.left == paramTreeNode2) {
/* 3044 */           localTreeNode2.left = localTreeNode1;
/*      */         } else
/* 3046 */           localTreeNode2.right = localTreeNode1;
/* 3047 */         localTreeNode1.left = paramTreeNode2;
/* 3048 */         paramTreeNode2.parent = localTreeNode1;
/*      */       }
/* 3050 */       return paramTreeNode1;
/*      */     }
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> rotateRight(ConcurrentHashMap.TreeNode<K, V> paramTreeNode1, ConcurrentHashMap.TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       ConcurrentHashMap.TreeNode localTreeNode1;
/* 3056 */       if ((paramTreeNode2 != null) && ((localTreeNode1 = paramTreeNode2.left) != null)) { ConcurrentHashMap.TreeNode localTreeNode3;
/* 3057 */         if ((localTreeNode3 = paramTreeNode2.left = localTreeNode1.right) != null)
/* 3058 */           localTreeNode3.parent = paramTreeNode2;
/* 3059 */         ConcurrentHashMap.TreeNode localTreeNode2; if ((localTreeNode2 = localTreeNode1.parent = paramTreeNode2.parent) == null) {
/* 3060 */           (paramTreeNode1 = localTreeNode1).red = false;
/* 3061 */         } else if (localTreeNode2.right == paramTreeNode2) {
/* 3062 */           localTreeNode2.right = localTreeNode1;
/*      */         } else
/* 3064 */           localTreeNode2.left = localTreeNode1;
/* 3065 */         localTreeNode1.right = paramTreeNode2;
/* 3066 */         paramTreeNode2.parent = localTreeNode1;
/*      */       }
/* 3068 */       return paramTreeNode1;
/*      */     }
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> balanceInsertion(ConcurrentHashMap.TreeNode<K, V> paramTreeNode1, ConcurrentHashMap.TreeNode<K, V> paramTreeNode2)
/*      */     {
/* 3073 */       paramTreeNode2.red = true;
/*      */       for (;;) { ConcurrentHashMap.TreeNode localTreeNode1;
/* 3075 */         if ((localTreeNode1 = paramTreeNode2.parent) == null) {
/* 3076 */           paramTreeNode2.red = false;
/* 3077 */           return paramTreeNode2; }
/*      */         ConcurrentHashMap.TreeNode localTreeNode2;
/* 3079 */         if ((!localTreeNode1.red) || ((localTreeNode2 = localTreeNode1.parent) == null))
/* 3080 */           return paramTreeNode1;
/* 3081 */         ConcurrentHashMap.TreeNode localTreeNode3; if (localTreeNode1 == (localTreeNode3 = localTreeNode2.left)) { ConcurrentHashMap.TreeNode localTreeNode4;
/* 3082 */           if (((localTreeNode4 = localTreeNode2.right) != null) && (localTreeNode4.red)) {
/* 3083 */             localTreeNode4.red = false;
/* 3084 */             localTreeNode1.red = false;
/* 3085 */             localTreeNode2.red = true;
/* 3086 */             paramTreeNode2 = localTreeNode2;
/*      */           }
/*      */           else {
/* 3089 */             if (paramTreeNode2 == localTreeNode1.right) {
/* 3090 */               paramTreeNode1 = rotateLeft(paramTreeNode1, paramTreeNode2 = localTreeNode1);
/* 3091 */               localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.parent;
/*      */             }
/* 3093 */             if (localTreeNode1 != null) {
/* 3094 */               localTreeNode1.red = false;
/* 3095 */               if (localTreeNode2 != null) {
/* 3096 */                 localTreeNode2.red = true;
/* 3097 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode2);
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */         }
/* 3103 */         else if ((localTreeNode3 != null) && (localTreeNode3.red)) {
/* 3104 */           localTreeNode3.red = false;
/* 3105 */           localTreeNode1.red = false;
/* 3106 */           localTreeNode2.red = true;
/* 3107 */           paramTreeNode2 = localTreeNode2;
/*      */         }
/*      */         else {
/* 3110 */           if (paramTreeNode2 == localTreeNode1.left) {
/* 3111 */             paramTreeNode1 = rotateRight(paramTreeNode1, paramTreeNode2 = localTreeNode1);
/* 3112 */             localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.parent;
/*      */           }
/* 3114 */           if (localTreeNode1 != null) {
/* 3115 */             localTreeNode1.red = false;
/* 3116 */             if (localTreeNode2 != null) {
/* 3117 */               localTreeNode2.red = true;
/* 3118 */               paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     static <K, V> ConcurrentHashMap.TreeNode<K, V> balanceDeletion(ConcurrentHashMap.TreeNode<K, V> paramTreeNode1, ConcurrentHashMap.TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       for (;;)
/*      */       {
/* 3129 */         if ((paramTreeNode2 == null) || (paramTreeNode2 == paramTreeNode1))
/* 3130 */           return paramTreeNode1;
/* 3131 */         ConcurrentHashMap.TreeNode localTreeNode1; if ((localTreeNode1 = paramTreeNode2.parent) == null) {
/* 3132 */           paramTreeNode2.red = false;
/* 3133 */           return paramTreeNode2;
/*      */         }
/* 3135 */         if (paramTreeNode2.red) {
/* 3136 */           paramTreeNode2.red = false;
/* 3137 */           return paramTreeNode1; }
/*      */         ConcurrentHashMap.TreeNode localTreeNode2;
/* 3139 */         ConcurrentHashMap.TreeNode localTreeNode4; ConcurrentHashMap.TreeNode localTreeNode5; if ((localTreeNode2 = localTreeNode1.left) == paramTreeNode2) { ConcurrentHashMap.TreeNode localTreeNode3;
/* 3140 */           if (((localTreeNode3 = localTreeNode1.right) != null) && (localTreeNode3.red)) {
/* 3141 */             localTreeNode3.red = false;
/* 3142 */             localTreeNode1.red = true;
/* 3143 */             paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode1);
/* 3144 */             localTreeNode3 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.right;
/*      */           }
/* 3146 */           if (localTreeNode3 == null) {
/* 3147 */             paramTreeNode2 = localTreeNode1;
/*      */           } else {
/* 3149 */             localTreeNode4 = localTreeNode3.left;localTreeNode5 = localTreeNode3.right;
/* 3150 */             if (((localTreeNode5 == null) || (!localTreeNode5.red)) && ((localTreeNode4 == null) || (!localTreeNode4.red)))
/*      */             {
/* 3152 */               localTreeNode3.red = true;
/* 3153 */               paramTreeNode2 = localTreeNode1;
/*      */             }
/*      */             else {
/* 3156 */               if ((localTreeNode5 == null) || (!localTreeNode5.red)) {
/* 3157 */                 if (localTreeNode4 != null)
/* 3158 */                   localTreeNode4.red = false;
/* 3159 */                 localTreeNode3.red = true;
/* 3160 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode3);
/* 3161 */                 localTreeNode3 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.right;
/*      */               }
/*      */               
/* 3164 */               if (localTreeNode3 != null) {
/* 3165 */                 localTreeNode3.red = (localTreeNode1 == null ? false : localTreeNode1.red);
/* 3166 */                 if ((localTreeNode5 = localTreeNode3.right) != null)
/* 3167 */                   localTreeNode5.red = false;
/*      */               }
/* 3169 */               if (localTreeNode1 != null) {
/* 3170 */                 localTreeNode1.red = false;
/* 3171 */                 paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode1);
/*      */               }
/* 3173 */               paramTreeNode2 = paramTreeNode1;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 3178 */           if ((localTreeNode2 != null) && (localTreeNode2.red)) {
/* 3179 */             localTreeNode2.red = false;
/* 3180 */             localTreeNode1.red = true;
/* 3181 */             paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode1);
/* 3182 */             localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.left;
/*      */           }
/* 3184 */           if (localTreeNode2 == null) {
/* 3185 */             paramTreeNode2 = localTreeNode1;
/*      */           } else {
/* 3187 */             localTreeNode4 = localTreeNode2.left;localTreeNode5 = localTreeNode2.right;
/* 3188 */             if (((localTreeNode4 == null) || (!localTreeNode4.red)) && ((localTreeNode5 == null) || (!localTreeNode5.red)))
/*      */             {
/* 3190 */               localTreeNode2.red = true;
/* 3191 */               paramTreeNode2 = localTreeNode1;
/*      */             }
/*      */             else {
/* 3194 */               if ((localTreeNode4 == null) || (!localTreeNode4.red)) {
/* 3195 */                 if (localTreeNode5 != null)
/* 3196 */                   localTreeNode5.red = false;
/* 3197 */                 localTreeNode2.red = true;
/* 3198 */                 paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode2);
/* 3199 */                 localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.left;
/*      */               }
/*      */               
/* 3202 */               if (localTreeNode2 != null) {
/* 3203 */                 localTreeNode2.red = (localTreeNode1 == null ? false : localTreeNode1.red);
/* 3204 */                 if ((localTreeNode4 = localTreeNode2.left) != null)
/* 3205 */                   localTreeNode4.red = false;
/*      */               }
/* 3207 */               if (localTreeNode1 != null) {
/* 3208 */                 localTreeNode1.red = false;
/* 3209 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode1);
/*      */               }
/* 3211 */               paramTreeNode2 = paramTreeNode1;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static <K, V> boolean checkInvariants(ConcurrentHashMap.TreeNode<K, V> paramTreeNode)
/*      */     {
/* 3222 */       ConcurrentHashMap.TreeNode localTreeNode1 = paramTreeNode.parent;ConcurrentHashMap.TreeNode localTreeNode2 = paramTreeNode.left;ConcurrentHashMap.TreeNode localTreeNode3 = paramTreeNode.right;
/* 3223 */       ConcurrentHashMap.TreeNode localTreeNode4 = paramTreeNode.prev;ConcurrentHashMap.TreeNode localTreeNode5 = (ConcurrentHashMap.TreeNode)paramTreeNode.next;
/* 3224 */       if ((localTreeNode4 != null) && (localTreeNode4.next != paramTreeNode))
/* 3225 */         return false;
/* 3226 */       if ((localTreeNode5 != null) && (localTreeNode5.prev != paramTreeNode))
/* 3227 */         return false;
/* 3228 */       if ((localTreeNode1 != null) && (paramTreeNode != localTreeNode1.left) && (paramTreeNode != localTreeNode1.right))
/* 3229 */         return false;
/* 3230 */       if ((localTreeNode2 != null) && ((localTreeNode2.parent != paramTreeNode) || (localTreeNode2.hash > paramTreeNode.hash)))
/* 3231 */         return false;
/* 3232 */       if ((localTreeNode3 != null) && ((localTreeNode3.parent != paramTreeNode) || (localTreeNode3.hash < paramTreeNode.hash)))
/* 3233 */         return false;
/* 3234 */       if ((paramTreeNode.red) && (localTreeNode2 != null) && (localTreeNode2.red) && (localTreeNode3 != null) && (localTreeNode3.red))
/* 3235 */         return false;
/* 3236 */       if ((localTreeNode2 != null) && (!checkInvariants(localTreeNode2)))
/* 3237 */         return false;
/* 3238 */       if ((localTreeNode3 != null) && (!checkInvariants(localTreeNode3)))
/* 3239 */         return false;
/* 3240 */       return true;
/*      */     }
/*      */     
/*      */     static
/*      */     {
/*      */       try
/*      */       {
/* 3247 */         U = Unsafe.getUnsafe();
/* 3248 */         Class localClass = TreeBin.class;
/*      */         
/* 3250 */         LOCKSTATE = U.objectFieldOffset(localClass.getDeclaredField("lockState"));
/*      */       } catch (Exception localException) {
/* 3252 */         throw new Error(localException);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class TableStack<K, V>
/*      */   {
/*      */     int length;
/*      */     
/*      */ 
/*      */     int index;
/*      */     
/*      */ 
/*      */     ConcurrentHashMap.Node<K, V>[] tab;
/*      */     
/*      */ 
/*      */     TableStack<K, V> next;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static class Traverser<K, V>
/*      */   {
/*      */     ConcurrentHashMap.Node<K, V>[] tab;
/*      */     
/*      */ 
/*      */     ConcurrentHashMap.Node<K, V> next;
/*      */     
/*      */ 
/*      */     ConcurrentHashMap.TableStack<K, V> stack;
/*      */     
/*      */ 
/*      */     ConcurrentHashMap.TableStack<K, V> spare;
/*      */     
/*      */ 
/*      */     int index;
/*      */     
/*      */ 
/*      */     int baseIndex;
/*      */     
/*      */ 
/*      */     int baseLimit;
/*      */     
/*      */     final int baseSize;
/*      */     
/*      */ 
/*      */     Traverser(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 3302 */       this.tab = paramArrayOfNode;
/* 3303 */       this.baseSize = paramInt1;
/* 3304 */       this.baseIndex = (this.index = paramInt2);
/* 3305 */       this.baseLimit = paramInt3;
/* 3306 */       this.next = null;
/*      */     }
/*      */     
/*      */ 
/*      */     final ConcurrentHashMap.Node<K, V> advance()
/*      */     {
/*      */       Object localObject;
/*      */       
/* 3314 */       if ((localObject = this.next) != null) {
/* 3315 */         localObject = ((ConcurrentHashMap.Node)localObject).next;
/*      */       }
/*      */       for (;;) {
/* 3318 */         if (localObject != null)
/* 3319 */           return (ConcurrentHashMap.Node<K, V>)(this.next = localObject);
/* 3320 */         ConcurrentHashMap.Node[] arrayOfNode; int j; int i; if ((this.baseIndex >= this.baseLimit) || ((arrayOfNode = this.tab) == null) || ((j = arrayOfNode.length) <= (i = this.index)) || (i < 0))
/*      */         {
/* 3322 */           return this.next = null; }
/* 3323 */         if (((localObject = ConcurrentHashMap.tabAt(arrayOfNode, i)) != null) && (((ConcurrentHashMap.Node)localObject).hash < 0)) {
/* 3324 */           if ((localObject instanceof ConcurrentHashMap.ForwardingNode)) {
/* 3325 */             this.tab = ((ConcurrentHashMap.ForwardingNode)localObject).nextTable;
/* 3326 */             localObject = null;
/* 3327 */             pushState(arrayOfNode, i, j);
/* 3328 */             continue;
/*      */           }
/* 3330 */           if ((localObject instanceof ConcurrentHashMap.TreeBin)) {
/* 3331 */             localObject = ((ConcurrentHashMap.TreeBin)localObject).first;
/*      */           } else
/* 3333 */             localObject = null;
/*      */         }
/* 3335 */         if (this.stack != null) {
/* 3336 */           recoverState(j);
/* 3337 */         } else if ((this.index = i + this.baseSize) >= j) {
/* 3338 */           this.index = (++this.baseIndex);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private void pushState(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2)
/*      */     {
/* 3346 */       ConcurrentHashMap.TableStack localTableStack = this.spare;
/* 3347 */       if (localTableStack != null) {
/* 3348 */         this.spare = localTableStack.next;
/*      */       } else
/* 3350 */         localTableStack = new ConcurrentHashMap.TableStack();
/* 3351 */       localTableStack.tab = paramArrayOfNode;
/* 3352 */       localTableStack.length = paramInt2;
/* 3353 */       localTableStack.index = paramInt1;
/* 3354 */       localTableStack.next = this.stack;
/* 3355 */       this.stack = localTableStack;
/*      */     }
/*      */     
/*      */ 
/*      */     private void recoverState(int paramInt)
/*      */     {
/*      */       ConcurrentHashMap.TableStack localTableStack1;
/*      */       
/*      */       int i;
/*      */       
/* 3365 */       while (((localTableStack1 = this.stack) != null) && (this.index += (i = localTableStack1.length) >= paramInt)) {
/* 3366 */         paramInt = i;
/* 3367 */         this.index = localTableStack1.index;
/* 3368 */         this.tab = localTableStack1.tab;
/* 3369 */         localTableStack1.tab = null;
/* 3370 */         ConcurrentHashMap.TableStack localTableStack2 = localTableStack1.next;
/* 3371 */         localTableStack1.next = this.spare;
/* 3372 */         this.stack = localTableStack2;
/* 3373 */         this.spare = localTableStack1;
/*      */       }
/* 3375 */       if ((localTableStack1 == null) && (this.index += this.baseSize >= paramInt)) {
/* 3376 */         this.index = (++this.baseIndex);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static class BaseIterator<K, V>
/*      */     extends ConcurrentHashMap.Traverser<K, V>
/*      */   {
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     ConcurrentHashMap.Node<K, V> lastReturned;
/*      */     
/*      */     BaseIterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 3389 */       super(paramInt1, paramInt2, paramInt3);
/* 3390 */       this.map = paramConcurrentHashMap;
/* 3391 */       advance();
/*      */     }
/*      */     
/* 3394 */     public final boolean hasNext() { return this.next != null; }
/* 3395 */     public final boolean hasMoreElements() { return this.next != null; }
/*      */     
/*      */     public final void remove() {
/*      */       ConcurrentHashMap.Node localNode;
/* 3399 */       if ((localNode = this.lastReturned) == null)
/* 3400 */         throw new IllegalStateException();
/* 3401 */       this.lastReturned = null;
/* 3402 */       this.map.replaceNode(localNode.key, null, null);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeyIterator<K, V> extends ConcurrentHashMap.BaseIterator<K, V> implements Iterator<K>, Enumeration<K>
/*      */   {
/*      */     KeyIterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 3410 */       super(paramInt1, paramInt2, paramInt3, paramConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final K next() {
/*      */       ConcurrentHashMap.Node localNode;
/* 3415 */       if ((localNode = this.next) == null)
/* 3416 */         throw new NoSuchElementException();
/* 3417 */       Object localObject = localNode.key;
/* 3418 */       this.lastReturned = localNode;
/* 3419 */       advance();
/* 3420 */       return (K)localObject;
/*      */     }
/*      */     
/* 3423 */     public final K nextElement() { return (K)next(); }
/*      */   }
/*      */   
/*      */   static final class ValueIterator<K, V> extends ConcurrentHashMap.BaseIterator<K, V> implements Iterator<V>, Enumeration<V>
/*      */   {
/*      */     ValueIterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 3430 */       super(paramInt1, paramInt2, paramInt3, paramConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final V next() {
/*      */       ConcurrentHashMap.Node localNode;
/* 3435 */       if ((localNode = this.next) == null)
/* 3436 */         throw new NoSuchElementException();
/* 3437 */       Object localObject = localNode.val;
/* 3438 */       this.lastReturned = localNode;
/* 3439 */       advance();
/* 3440 */       return (V)localObject;
/*      */     }
/*      */     
/* 3443 */     public final V nextElement() { return (V)next(); }
/*      */   }
/*      */   
/*      */   static final class EntryIterator<K, V> extends ConcurrentHashMap.BaseIterator<K, V> implements Iterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntryIterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 3450 */       super(paramInt1, paramInt2, paramInt3, paramConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> next() {
/*      */       ConcurrentHashMap.Node localNode;
/* 3455 */       if ((localNode = this.next) == null)
/* 3456 */         throw new NoSuchElementException();
/* 3457 */       Object localObject1 = localNode.key;
/* 3458 */       Object localObject2 = localNode.val;
/* 3459 */       this.lastReturned = localNode;
/* 3460 */       advance();
/* 3461 */       return new ConcurrentHashMap.MapEntry(localObject1, localObject2, this.map);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class MapEntry<K, V> implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     V val;
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     
/*      */     MapEntry(K paramK, V paramV, ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 3473 */       this.key = paramK;
/* 3474 */       this.val = paramV;
/* 3475 */       this.map = paramConcurrentHashMap; }
/*      */     
/* 3477 */     public K getKey() { return (K)this.key; }
/* 3478 */     public V getValue() { return (V)this.val; }
/* 3479 */     public int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); }
/* 3480 */     public String toString() { return this.key + "=" + this.val; }
/*      */     
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*      */       Map.Entry localEntry;
/*      */       Object localObject1;
/*      */       Object localObject2;
/* 3488 */       return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = localEntry.getValue()) != null) && ((localObject1 == this.key) || (localObject1.equals(this.key))) && ((localObject2 == this.val) || (localObject2.equals(this.val)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public V setValue(V paramV)
/*      */     {
/* 3500 */       if (paramV == null) throw new NullPointerException();
/* 3501 */       Object localObject = this.val;
/* 3502 */       this.val = paramV;
/* 3503 */       this.map.put(this.key, paramV);
/* 3504 */       return (V)localObject;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V> extends ConcurrentHashMap.Traverser<K, V> implements Spliterator<K>
/*      */   {
/*      */     long est;
/*      */     
/*      */     KeySpliterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, long paramLong) {
/* 3513 */       super(paramInt1, paramInt2, paramInt3);
/* 3514 */       this.est = paramLong; }
/*      */     
/*      */     public Spliterator<K> trySplit() { int i;
/*      */       int j;
/*      */       int k;
/* 3519 */       return (k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i ? null : new KeySpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super K> paramConsumer)
/*      */     {
/* 3525 */       if (paramConsumer == null) throw new NullPointerException();
/* 3526 */       ConcurrentHashMap.Node localNode; while ((localNode = advance()) != null)
/* 3527 */         paramConsumer.accept(localNode.key);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> paramConsumer) {
/* 3531 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       ConcurrentHashMap.Node localNode;
/* 3533 */       if ((localNode = advance()) == null)
/* 3534 */         return false;
/* 3535 */       paramConsumer.accept(localNode.key);
/* 3536 */       return true;
/*      */     }
/*      */     
/* 3539 */     public long estimateSize() { return this.est; }
/*      */     
/*      */     public int characteristics() {
/* 3542 */       return 4353;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V> extends ConcurrentHashMap.Traverser<K, V> implements Spliterator<V>
/*      */   {
/*      */     long est;
/*      */     
/*      */     ValueSpliterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, long paramLong)
/*      */     {
/* 3552 */       super(paramInt1, paramInt2, paramInt3);
/* 3553 */       this.est = paramLong; }
/*      */     
/*      */     public Spliterator<V> trySplit() { int i;
/*      */       int j;
/*      */       int k;
/* 3558 */       return (k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i ? null : new ValueSpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super V> paramConsumer)
/*      */     {
/* 3564 */       if (paramConsumer == null) throw new NullPointerException();
/* 3565 */       ConcurrentHashMap.Node localNode; while ((localNode = advance()) != null)
/* 3566 */         paramConsumer.accept(localNode.val);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> paramConsumer) {
/* 3570 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       ConcurrentHashMap.Node localNode;
/* 3572 */       if ((localNode = advance()) == null)
/* 3573 */         return false;
/* 3574 */       paramConsumer.accept(localNode.val);
/* 3575 */       return true;
/*      */     }
/*      */     
/* 3578 */     public long estimateSize() { return this.est; }
/*      */     
/*      */     public int characteristics() {
/* 3581 */       return 4352;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V> extends ConcurrentHashMap.Traverser<K, V> implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     long est;
/*      */     
/*      */     EntrySpliterator(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, long paramLong, ConcurrentHashMap<K, V> paramConcurrentHashMap) {
/* 3591 */       super(paramInt1, paramInt2, paramInt3);
/* 3592 */       this.map = paramConcurrentHashMap;
/* 3593 */       this.est = paramLong; }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> trySplit() { int i;
/*      */       int j;
/*      */       int k;
/* 3598 */       return (k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i ? null : new EntrySpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1, this.map);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */     {
/* 3604 */       if (paramConsumer == null) throw new NullPointerException();
/* 3605 */       ConcurrentHashMap.Node localNode; while ((localNode = advance()) != null)
/* 3606 */         paramConsumer.accept(new ConcurrentHashMap.MapEntry(localNode.key, localNode.val, this.map));
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> paramConsumer) {
/* 3610 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       ConcurrentHashMap.Node localNode;
/* 3612 */       if ((localNode = advance()) == null)
/* 3613 */         return false;
/* 3614 */       paramConsumer.accept(new ConcurrentHashMap.MapEntry(localNode.key, localNode.val, this.map));
/* 3615 */       return true;
/*      */     }
/*      */     
/* 3618 */     public long estimateSize() { return this.est; }
/*      */     
/*      */     public int characteristics() {
/* 3621 */       return 4353;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int batchFor(long paramLong)
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3638 */     if ((paramLong == Long.MAX_VALUE) || ((l = sumCount()) <= 1L) || (l < paramLong))
/* 3639 */       return 0;
/* 3640 */     int i = ForkJoinPool.getCommonPoolParallelism() << 2;
/* 3641 */     return (paramLong <= 0L) || (l /= paramLong >= i) ? i : (int)l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void forEach(long paramLong, BiConsumer<? super K, ? super V> paramBiConsumer)
/*      */   {
/* 3654 */     if (paramBiConsumer == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 3657 */     new ForEachMappingTask(null, batchFor(paramLong), 0, 0, this.table, paramBiConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> void forEach(long paramLong, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, Consumer<? super U> paramConsumer)
/*      */   {
/* 3676 */     if ((paramBiFunction == null) || (paramConsumer == null)) {
/* 3677 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3680 */     new ForEachTransformedMappingTask(null, batchFor(paramLong), 0, 0, this.table, paramBiFunction, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U search(long paramLong, BiFunction<? super K, ? super V, ? extends U> paramBiFunction)
/*      */   {
/* 3701 */     if (paramBiFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 3704 */     return (U)new SearchMappingsTask(null, batchFor(paramLong), 0, 0, this.table, paramBiFunction, new AtomicReference()).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduce(long paramLong, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction1)
/*      */   {
/* 3726 */     if ((paramBiFunction == null) || (paramBiFunction1 == null)) {
/* 3727 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3730 */     return (U)new MapReduceMappingsTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFunction, paramBiFunction1).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceToDouble(long paramLong, ToDoubleBiFunction<? super K, ? super V> paramToDoubleBiFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */   {
/* 3752 */     if ((paramToDoubleBiFunction == null) || (paramDoubleBinaryOperator == null)) {
/* 3753 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3756 */     return ((Double)new MapReduceMappingsToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToDoubleBiFunction, paramDouble, paramDoubleBinaryOperator).invoke()).doubleValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceToLong(long paramLong1, ToLongBiFunction<? super K, ? super V> paramToLongBiFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator)
/*      */   {
/* 3778 */     if ((paramToLongBiFunction == null) || (paramLongBinaryOperator == null)) {
/* 3779 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3782 */     return ((Long)new MapReduceMappingsToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramToLongBiFunction, paramLong2, paramLongBinaryOperator).invoke()).longValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceToInt(long paramLong, ToIntBiFunction<? super K, ? super V> paramToIntBiFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*      */   {
/* 3804 */     if ((paramToIntBiFunction == null) || (paramIntBinaryOperator == null)) {
/* 3805 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3808 */     return ((Integer)new MapReduceMappingsToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToIntBiFunction, paramInt, paramIntBinaryOperator).invoke()).intValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void forEachKey(long paramLong, Consumer<? super K> paramConsumer)
/*      */   {
/* 3821 */     if (paramConsumer == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 3824 */     new ForEachKeyTask(null, batchFor(paramLong), 0, 0, this.table, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> void forEachKey(long paramLong, Function<? super K, ? extends U> paramFunction, Consumer<? super U> paramConsumer)
/*      */   {
/* 3843 */     if ((paramFunction == null) || (paramConsumer == null)) {
/* 3844 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3847 */     new ForEachTransformedKeyTask(null, batchFor(paramLong), 0, 0, this.table, paramFunction, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U searchKeys(long paramLong, Function<? super K, ? extends U> paramFunction)
/*      */   {
/* 3868 */     if (paramFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 3871 */     return (U)new SearchKeysTask(null, batchFor(paramLong), 0, 0, this.table, paramFunction, new AtomicReference()).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public K reduceKeys(long paramLong, BiFunction<? super K, ? super K, ? extends K> paramBiFunction)
/*      */   {
/* 3887 */     if (paramBiFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 3890 */     return (K)new ReduceKeysTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFunction).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduceKeys(long paramLong, Function<? super K, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction)
/*      */   {
/* 3912 */     if ((paramFunction == null) || (paramBiFunction == null)) {
/* 3913 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3916 */     return (U)new MapReduceKeysTask(null, batchFor(paramLong), 0, 0, this.table, null, paramFunction, paramBiFunction).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceKeysToDouble(long paramLong, ToDoubleFunction<? super K> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */   {
/* 3938 */     if ((paramToDoubleFunction == null) || (paramDoubleBinaryOperator == null)) {
/* 3939 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3942 */     return ((Double)new MapReduceKeysToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToDoubleFunction, paramDouble, paramDoubleBinaryOperator).invoke()).doubleValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceKeysToLong(long paramLong1, ToLongFunction<? super K> paramToLongFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator)
/*      */   {
/* 3964 */     if ((paramToLongFunction == null) || (paramLongBinaryOperator == null)) {
/* 3965 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3968 */     return ((Long)new MapReduceKeysToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramToLongFunction, paramLong2, paramLongBinaryOperator).invoke()).longValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceKeysToInt(long paramLong, ToIntFunction<? super K> paramToIntFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*      */   {
/* 3990 */     if ((paramToIntFunction == null) || (paramIntBinaryOperator == null)) {
/* 3991 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3994 */     return ((Integer)new MapReduceKeysToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToIntFunction, paramInt, paramIntBinaryOperator).invoke()).intValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void forEachValue(long paramLong, Consumer<? super V> paramConsumer)
/*      */   {
/* 4007 */     if (paramConsumer == null) {
/* 4008 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4011 */     new ForEachValueTask(null, batchFor(paramLong), 0, 0, this.table, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> void forEachValue(long paramLong, Function<? super V, ? extends U> paramFunction, Consumer<? super U> paramConsumer)
/*      */   {
/* 4030 */     if ((paramFunction == null) || (paramConsumer == null)) {
/* 4031 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4034 */     new ForEachTransformedValueTask(null, batchFor(paramLong), 0, 0, this.table, paramFunction, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U searchValues(long paramLong, Function<? super V, ? extends U> paramFunction)
/*      */   {
/* 4055 */     if (paramFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 4058 */     return (U)new SearchValuesTask(null, batchFor(paramLong), 0, 0, this.table, paramFunction, new AtomicReference()).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V reduceValues(long paramLong, BiFunction<? super V, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 4073 */     if (paramBiFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 4076 */     return (V)new ReduceValuesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFunction).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduceValues(long paramLong, Function<? super V, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction)
/*      */   {
/* 4098 */     if ((paramFunction == null) || (paramBiFunction == null)) {
/* 4099 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4102 */     return (U)new MapReduceValuesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramFunction, paramBiFunction).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceValuesToDouble(long paramLong, ToDoubleFunction<? super V> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */   {
/* 4124 */     if ((paramToDoubleFunction == null) || (paramDoubleBinaryOperator == null)) {
/* 4125 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4128 */     return ((Double)new MapReduceValuesToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToDoubleFunction, paramDouble, paramDoubleBinaryOperator).invoke()).doubleValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceValuesToLong(long paramLong1, ToLongFunction<? super V> paramToLongFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator)
/*      */   {
/* 4150 */     if ((paramToLongFunction == null) || (paramLongBinaryOperator == null)) {
/* 4151 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4154 */     return ((Long)new MapReduceValuesToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramToLongFunction, paramLong2, paramLongBinaryOperator).invoke()).longValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceValuesToInt(long paramLong, ToIntFunction<? super V> paramToIntFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*      */   {
/* 4176 */     if ((paramToIntFunction == null) || (paramIntBinaryOperator == null)) {
/* 4177 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4180 */     return ((Integer)new MapReduceValuesToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToIntFunction, paramInt, paramIntBinaryOperator).invoke()).intValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void forEachEntry(long paramLong, Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */   {
/* 4193 */     if (paramConsumer == null) { throw new NullPointerException();
/*      */     }
/* 4195 */     new ForEachEntryTask(null, batchFor(paramLong), 0, 0, this.table, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> void forEachEntry(long paramLong, Function<Map.Entry<K, V>, ? extends U> paramFunction, Consumer<? super U> paramConsumer)
/*      */   {
/* 4214 */     if ((paramFunction == null) || (paramConsumer == null)) {
/* 4215 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4218 */     new ForEachTransformedEntryTask(null, batchFor(paramLong), 0, 0, this.table, paramFunction, paramConsumer).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U searchEntries(long paramLong, Function<Map.Entry<K, V>, ? extends U> paramFunction)
/*      */   {
/* 4239 */     if (paramFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 4242 */     return (U)new SearchEntriesTask(null, batchFor(paramLong), 0, 0, this.table, paramFunction, new AtomicReference()).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Map.Entry<K, V> reduceEntries(long paramLong, BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> paramBiFunction)
/*      */   {
/* 4257 */     if (paramBiFunction == null) { throw new NullPointerException();
/*      */     }
/*      */     
/* 4260 */     return (Map.Entry)new ReduceEntriesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFunction).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduceEntries(long paramLong, Function<Map.Entry<K, V>, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction)
/*      */   {
/* 4282 */     if ((paramFunction == null) || (paramBiFunction == null)) {
/* 4283 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4286 */     return (U)new MapReduceEntriesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramFunction, paramBiFunction).invoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceEntriesToDouble(long paramLong, ToDoubleFunction<Map.Entry<K, V>> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */   {
/* 4308 */     if ((paramToDoubleFunction == null) || (paramDoubleBinaryOperator == null)) {
/* 4309 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4312 */     return ((Double)new MapReduceEntriesToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToDoubleFunction, paramDouble, paramDoubleBinaryOperator).invoke()).doubleValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceEntriesToLong(long paramLong1, ToLongFunction<Map.Entry<K, V>> paramToLongFunction, long paramLong2, LongBinaryOperator paramLongBinaryOperator)
/*      */   {
/* 4334 */     if ((paramToLongFunction == null) || (paramLongBinaryOperator == null)) {
/* 4335 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4338 */     return ((Long)new MapReduceEntriesToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramToLongFunction, paramLong2, paramLongBinaryOperator).invoke()).longValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceEntriesToInt(long paramLong, ToIntFunction<Map.Entry<K, V>> paramToIntFunction, int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*      */   {
/* 4360 */     if ((paramToIntFunction == null) || (paramIntBinaryOperator == null)) {
/* 4361 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 4364 */     return ((Integer)new MapReduceEntriesToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramToIntFunction, paramInt, paramIntBinaryOperator).invoke()).intValue();
/*      */   }
/*      */   
/*      */ 
/*      */   static abstract class CollectionView<K, V, E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */     final ConcurrentHashMap<K, V> map;
/*      */     private static final String oomeMsg = "Required array size too large";
/*      */     
/*      */     CollectionView(ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 4377 */       this.map = paramConcurrentHashMap;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public ConcurrentHashMap<K, V> getMap()
/*      */     {
/* 4384 */       return this.map;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4390 */     public final void clear() { this.map.clear(); }
/* 4391 */     public final int size() { return this.map.size(); }
/* 4392 */     public final boolean isEmpty() { return this.map.isEmpty(); }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract Iterator<E> iterator();
/*      */     
/*      */ 
/*      */ 
/*      */     public abstract boolean contains(Object paramObject);
/*      */     
/*      */ 
/*      */ 
/*      */     public abstract boolean remove(Object paramObject);
/*      */     
/*      */ 
/*      */ 
/*      */     public final Object[] toArray()
/*      */     {
/* 4411 */       long l = this.map.mappingCount();
/* 4412 */       if (l > 2147483639L)
/* 4413 */         throw new OutOfMemoryError("Required array size too large");
/* 4414 */       int i = (int)l;
/* 4415 */       Object[] arrayOfObject = new Object[i];
/* 4416 */       int j = 0;
/* 4417 */       for (Object localObject : this) {
/* 4418 */         if (j == i) {
/* 4419 */           if (i >= 2147483639)
/* 4420 */             throw new OutOfMemoryError("Required array size too large");
/* 4421 */           if (i >= 1073741819) {
/* 4422 */             i = 2147483639;
/*      */           } else
/* 4424 */             i += (i >>> 1) + 1;
/* 4425 */           arrayOfObject = Arrays.copyOf(arrayOfObject, i);
/*      */         }
/* 4427 */         arrayOfObject[(j++)] = localObject;
/*      */       }
/* 4429 */       return j == i ? arrayOfObject : Arrays.copyOf(arrayOfObject, j);
/*      */     }
/*      */     
/*      */     public final <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/* 4434 */       long l = this.map.mappingCount();
/* 4435 */       if (l > 2147483639L)
/* 4436 */         throw new OutOfMemoryError("Required array size too large");
/* 4437 */       int i = (int)l;
/*      */       
/*      */ 
/* 4440 */       Object[] arrayOfObject = paramArrayOfT.length >= i ? paramArrayOfT : (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/* 4441 */       int j = arrayOfObject.length;
/* 4442 */       int k = 0;
/* 4443 */       for (Object localObject : this) {
/* 4444 */         if (k == j) {
/* 4445 */           if (j >= 2147483639)
/* 4446 */             throw new OutOfMemoryError("Required array size too large");
/* 4447 */           if (j >= 1073741819) {
/* 4448 */             j = 2147483639;
/*      */           } else
/* 4450 */             j += (j >>> 1) + 1;
/* 4451 */           arrayOfObject = Arrays.copyOf(arrayOfObject, j);
/*      */         }
/* 4453 */         arrayOfObject[(k++)] = localObject;
/*      */       }
/* 4455 */       if ((paramArrayOfT == arrayOfObject) && (k < j)) {
/* 4456 */         arrayOfObject[k] = null;
/* 4457 */         return arrayOfObject;
/*      */       }
/* 4459 */       return k == j ? arrayOfObject : Arrays.copyOf(arrayOfObject, k);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final String toString()
/*      */     {
/* 4474 */       StringBuilder localStringBuilder = new StringBuilder();
/* 4475 */       localStringBuilder.append('[');
/* 4476 */       Iterator localIterator = iterator();
/* 4477 */       if (localIterator.hasNext()) {
/*      */         for (;;) {
/* 4479 */           Object localObject = localIterator.next();
/* 4480 */           localStringBuilder.append(localObject == this ? "(this Collection)" : localObject);
/* 4481 */           if (!localIterator.hasNext())
/*      */             break;
/* 4483 */           localStringBuilder.append(',').append(' ');
/*      */         }
/*      */       }
/* 4486 */       return ']';
/*      */     }
/*      */     
/*      */     public final boolean containsAll(Collection<?> paramCollection) {
/* 4490 */       if (paramCollection != this) {
/* 4491 */         for (Object localObject : paramCollection) {
/* 4492 */           if ((localObject == null) || (!contains(localObject)))
/* 4493 */             return false;
/*      */         }
/*      */       }
/* 4496 */       return true;
/*      */     }
/*      */     
/*      */     public final boolean removeAll(Collection<?> paramCollection) {
/* 4500 */       if (paramCollection == null) throw new NullPointerException();
/* 4501 */       boolean bool = false;
/* 4502 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 4503 */         if (paramCollection.contains(localIterator.next())) {
/* 4504 */           localIterator.remove();
/* 4505 */           bool = true;
/*      */         }
/*      */       }
/* 4508 */       return bool;
/*      */     }
/*      */     
/*      */     public final boolean retainAll(Collection<?> paramCollection) {
/* 4512 */       if (paramCollection == null) throw new NullPointerException();
/* 4513 */       boolean bool = false;
/* 4514 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 4515 */         if (!paramCollection.contains(localIterator.next())) {
/* 4516 */           localIterator.remove();
/* 4517 */           bool = true;
/*      */         }
/*      */       }
/* 4520 */       return bool;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class KeySetView<K, V>
/*      */     extends ConcurrentHashMap.CollectionView<K, V, K>
/*      */     implements Set<K>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */     
/*      */ 
/*      */ 
/*      */     private final V value;
/*      */     
/*      */ 
/*      */ 
/*      */     KeySetView(ConcurrentHashMap<K, V> paramConcurrentHashMap, V paramV)
/*      */     {
/* 4541 */       super();
/* 4542 */       this.value = paramV;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public V getMappedValue()
/*      */     {
/* 4552 */       return (V)this.value;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean contains(Object paramObject)
/*      */     {
/* 4558 */       return this.map.containsKey(paramObject);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean remove(Object paramObject)
/*      */     {
/* 4569 */       return this.map.remove(paramObject) != null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public Iterator<K> iterator()
/*      */     {
/* 4576 */       ConcurrentHashMap localConcurrentHashMap = this.map;
/* 4577 */       ConcurrentHashMap.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMap.table) == null ? 0 : arrayOfNode.length;
/* 4578 */       return new ConcurrentHashMap.KeyIterator(arrayOfNode, i, 0, i, localConcurrentHashMap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean add(K paramK)
/*      */     {
/*      */       Object localObject;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4593 */       if ((localObject = this.value) == null)
/* 4594 */         throw new UnsupportedOperationException();
/* 4595 */       return this.map.putVal(paramK, localObject, true) == null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean addAll(Collection<? extends K> paramCollection)
/*      */     {
/* 4610 */       boolean bool = false;
/*      */       Object localObject1;
/* 4612 */       if ((localObject1 = this.value) == null)
/* 4613 */         throw new UnsupportedOperationException();
/* 4614 */       for (Object localObject2 : paramCollection) {
/* 4615 */         if (this.map.putVal(localObject2, localObject1, true) == null)
/* 4616 */           bool = true;
/*      */       }
/* 4618 */       return bool;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 4622 */       int i = 0;
/* 4623 */       for (Object localObject : this)
/* 4624 */         i += localObject.hashCode();
/* 4625 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*      */       Set localSet;
/* 4632 */       return ((paramObject instanceof Set)) && (((localSet = (Set)paramObject) == this) || ((containsAll(localSet)) && (localSet.containsAll(this))));
/*      */     }
/*      */     
/*      */     public Spliterator<K> spliterator()
/*      */     {
/* 4637 */       ConcurrentHashMap localConcurrentHashMap = this.map;
/* 4638 */       long l = localConcurrentHashMap.sumCount();
/* 4639 */       ConcurrentHashMap.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMap.table) == null ? 0 : arrayOfNode.length;
/* 4640 */       return new ConcurrentHashMap.KeySpliterator(arrayOfNode, i, 0, i, l < 0L ? 0L : l);
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super K> paramConsumer) {
/* 4644 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       ConcurrentHashMap.Node[] arrayOfNode;
/* 4646 */       if ((arrayOfNode = this.map.table) != null) {
/* 4647 */         ConcurrentHashMap.Traverser localTraverser = new ConcurrentHashMap.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4648 */         ConcurrentHashMap.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4649 */           paramConsumer.accept(localNode.key);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ValuesView<K, V>
/*      */     extends ConcurrentHashMap.CollectionView<K, V, V>
/*      */     implements Collection<V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/* 4662 */     ValuesView(ConcurrentHashMap<K, V> paramConcurrentHashMap) { super(); }
/*      */     
/* 4664 */     public final boolean contains(Object paramObject) { return this.map.containsValue(paramObject); }
/*      */     
/*      */     public final boolean remove(Object paramObject) {
/*      */       Iterator localIterator;
/* 4668 */       if (paramObject != null) {
/* 4669 */         for (localIterator = iterator(); localIterator.hasNext();) {
/* 4670 */           if (paramObject.equals(localIterator.next())) {
/* 4671 */             localIterator.remove();
/* 4672 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 4676 */       return false;
/*      */     }
/*      */     
/*      */     public final Iterator<V> iterator() {
/* 4680 */       ConcurrentHashMap localConcurrentHashMap = this.map;
/*      */       ConcurrentHashMap.Node[] arrayOfNode;
/* 4682 */       int i = (arrayOfNode = localConcurrentHashMap.table) == null ? 0 : arrayOfNode.length;
/* 4683 */       return new ConcurrentHashMap.ValueIterator(arrayOfNode, i, 0, i, localConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public final boolean add(V paramV) {
/* 4687 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/* 4690 */     public final boolean addAll(Collection<? extends V> paramCollection) { throw new UnsupportedOperationException(); }
/*      */     
/*      */ 
/*      */     public Spliterator<V> spliterator()
/*      */     {
/* 4695 */       ConcurrentHashMap localConcurrentHashMap = this.map;
/* 4696 */       long l = localConcurrentHashMap.sumCount();
/* 4697 */       ConcurrentHashMap.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMap.table) == null ? 0 : arrayOfNode.length;
/* 4698 */       return new ConcurrentHashMap.ValueSpliterator(arrayOfNode, i, 0, i, l < 0L ? 0L : l);
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super V> paramConsumer) {
/* 4702 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       ConcurrentHashMap.Node[] arrayOfNode;
/* 4704 */       if ((arrayOfNode = this.map.table) != null) {
/* 4705 */         ConcurrentHashMap.Traverser localTraverser = new ConcurrentHashMap.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4706 */         ConcurrentHashMap.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4707 */           paramConsumer.accept(localNode.val);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySetView<K, V>
/*      */     extends ConcurrentHashMap.CollectionView<K, V, Map.Entry<K, V>> implements Set<Map.Entry<K, V>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/*      */     EntrySetView(ConcurrentHashMap<K, V> paramConcurrentHashMap)
/*      */     {
/* 4720 */       super();
/*      */     }
/*      */     
/*      */     public boolean contains(Object paramObject) {
/*      */       Map.Entry localEntry;
/*      */       Object localObject1;
/*      */       Object localObject3;
/*      */       Object localObject2;
/* 4728 */       return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject3 = this.map.get(localObject1)) != null) && ((localObject2 = localEntry.getValue()) != null) && ((localObject2 == localObject3) || (localObject2.equals(localObject3)));
/*      */     }
/*      */     
/*      */     public boolean remove(Object paramObject)
/*      */     {
/*      */       Map.Entry localEntry;
/*      */       Object localObject1;
/*      */       Object localObject2;
/* 4736 */       return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = localEntry.getValue()) != null) && (this.map.remove(localObject1, localObject2));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public Iterator<Map.Entry<K, V>> iterator()
/*      */     {
/* 4743 */       ConcurrentHashMap localConcurrentHashMap = this.map;
/*      */       ConcurrentHashMap.Node[] arrayOfNode;
/* 4745 */       int i = (arrayOfNode = localConcurrentHashMap.table) == null ? 0 : arrayOfNode.length;
/* 4746 */       return new ConcurrentHashMap.EntryIterator(arrayOfNode, i, 0, i, localConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public boolean add(Map.Entry<K, V> paramEntry) {
/* 4750 */       return this.map.putVal(paramEntry.getKey(), paramEntry.getValue(), false) == null;
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Map.Entry<K, V>> paramCollection) {
/* 4754 */       boolean bool = false;
/* 4755 */       for (Map.Entry localEntry : paramCollection) {
/* 4756 */         if (add(localEntry))
/* 4757 */           bool = true;
/*      */       }
/* 4759 */       return bool;
/*      */     }
/*      */     
/*      */     public final int hashCode() {
/* 4763 */       int i = 0;
/*      */       ConcurrentHashMap.Node[] arrayOfNode;
/* 4765 */       if ((arrayOfNode = this.map.table) != null) {
/* 4766 */         ConcurrentHashMap.Traverser localTraverser = new ConcurrentHashMap.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4767 */         ConcurrentHashMap.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4768 */           i += localNode.hashCode();
/*      */         }
/*      */       }
/* 4771 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */     public final boolean equals(Object paramObject)
/*      */     {
/*      */       Set localSet;
/* 4778 */       return ((paramObject instanceof Set)) && (((localSet = (Set)paramObject) == this) || ((containsAll(localSet)) && (localSet.containsAll(this))));
/*      */     }
/*      */     
/*      */     public Spliterator<Map.Entry<K, V>> spliterator()
/*      */     {
/* 4783 */       ConcurrentHashMap localConcurrentHashMap = this.map;
/* 4784 */       long l = localConcurrentHashMap.sumCount();
/* 4785 */       ConcurrentHashMap.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMap.table) == null ? 0 : arrayOfNode.length;
/* 4786 */       return new ConcurrentHashMap.EntrySpliterator(arrayOfNode, i, 0, i, l < 0L ? 0L : l, localConcurrentHashMap);
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super Map.Entry<K, V>> paramConsumer) {
/* 4790 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       ConcurrentHashMap.Node[] arrayOfNode;
/* 4792 */       if ((arrayOfNode = this.map.table) != null) {
/* 4793 */         ConcurrentHashMap.Traverser localTraverser = new ConcurrentHashMap.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4794 */         ConcurrentHashMap.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4795 */           paramConsumer.accept(new ConcurrentHashMap.MapEntry(localNode.key, localNode.val, this.map));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static abstract class BulkTask<K, V, R>
/*      */     extends CountedCompleter<R>
/*      */   {
/*      */     ConcurrentHashMap.Node<K, V>[] tab;
/*      */     
/*      */     ConcurrentHashMap.Node<K, V> next;
/*      */     
/*      */     ConcurrentHashMap.TableStack<K, V> stack;
/*      */     ConcurrentHashMap.TableStack<K, V> spare;
/*      */     int index;
/*      */     int baseIndex;
/*      */     int baseLimit;
/*      */     final int baseSize;
/*      */     int batch;
/*      */     
/*      */     BulkTask(BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode)
/*      */     {
/* 4819 */       super();
/* 4820 */       this.batch = paramInt1;
/* 4821 */       this.index = (this.baseIndex = paramInt2);
/* 4822 */       if ((this.tab = paramArrayOfNode) == null) {
/* 4823 */         this.baseSize = (this.baseLimit = 0);
/* 4824 */       } else if (paramBulkTask == null) {
/* 4825 */         this.baseSize = (this.baseLimit = paramArrayOfNode.length);
/*      */       } else {
/* 4827 */         this.baseLimit = paramInt3;
/* 4828 */         this.baseSize = paramBulkTask.baseSize;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final ConcurrentHashMap.Node<K, V> advance()
/*      */     {
/*      */       Object localObject;
/*      */       
/* 4837 */       if ((localObject = this.next) != null) {
/* 4838 */         localObject = ((ConcurrentHashMap.Node)localObject).next;
/*      */       }
/*      */       for (;;) {
/* 4841 */         if (localObject != null)
/* 4842 */           return (ConcurrentHashMap.Node<K, V>)(this.next = localObject);
/* 4843 */         ConcurrentHashMap.Node[] arrayOfNode; int j; int i; if ((this.baseIndex >= this.baseLimit) || ((arrayOfNode = this.tab) == null) || ((j = arrayOfNode.length) <= (i = this.index)) || (i < 0))
/*      */         {
/* 4845 */           return this.next = null; }
/* 4846 */         if (((localObject = ConcurrentHashMap.tabAt(arrayOfNode, i)) != null) && (((ConcurrentHashMap.Node)localObject).hash < 0)) {
/* 4847 */           if ((localObject instanceof ConcurrentHashMap.ForwardingNode)) {
/* 4848 */             this.tab = ((ConcurrentHashMap.ForwardingNode)localObject).nextTable;
/* 4849 */             localObject = null;
/* 4850 */             pushState(arrayOfNode, i, j);
/* 4851 */             continue;
/*      */           }
/* 4853 */           if ((localObject instanceof ConcurrentHashMap.TreeBin)) {
/* 4854 */             localObject = ((ConcurrentHashMap.TreeBin)localObject).first;
/*      */           } else
/* 4856 */             localObject = null;
/*      */         }
/* 4858 */         if (this.stack != null) {
/* 4859 */           recoverState(j);
/* 4860 */         } else if ((this.index = i + this.baseSize) >= j)
/* 4861 */           this.index = (++this.baseIndex);
/*      */       }
/*      */     }
/*      */     
/*      */     private void pushState(ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2) {
/* 4866 */       ConcurrentHashMap.TableStack localTableStack = this.spare;
/* 4867 */       if (localTableStack != null) {
/* 4868 */         this.spare = localTableStack.next;
/*      */       } else
/* 4870 */         localTableStack = new ConcurrentHashMap.TableStack();
/* 4871 */       localTableStack.tab = paramArrayOfNode;
/* 4872 */       localTableStack.length = paramInt2;
/* 4873 */       localTableStack.index = paramInt1;
/* 4874 */       localTableStack.next = this.stack;
/* 4875 */       this.stack = localTableStack;
/*      */     }
/*      */     
/*      */     private void recoverState(int paramInt) { ConcurrentHashMap.TableStack localTableStack1;
/*      */       int i;
/* 4880 */       while (((localTableStack1 = this.stack) != null) && (this.index += (i = localTableStack1.length) >= paramInt)) {
/* 4881 */         paramInt = i;
/* 4882 */         this.index = localTableStack1.index;
/* 4883 */         this.tab = localTableStack1.tab;
/* 4884 */         localTableStack1.tab = null;
/* 4885 */         ConcurrentHashMap.TableStack localTableStack2 = localTableStack1.next;
/* 4886 */         localTableStack1.next = this.spare;
/* 4887 */         this.stack = localTableStack2;
/* 4888 */         this.spare = localTableStack1;
/*      */       }
/* 4890 */       if ((localTableStack1 == null) && (this.index += this.baseSize >= paramInt)) {
/* 4891 */         this.index = (++this.baseIndex);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class ForEachKeyTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final Consumer<? super K> action;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     ForEachKeyTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Consumer<? super K> paramConsumer)
/*      */     {
/* 4909 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4910 */       this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { Consumer localConsumer;
/* 4914 */       if ((localConsumer = this.action) != null) { int j;
/* 4915 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4917 */           addToPendingCount(1);
/* 4918 */           new ForEachKeyTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localConsumer)
/*      */           
/* 4920 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 4922 */         while ((localNode = advance()) != null)
/* 4923 */           localConsumer.accept(localNode.key);
/* 4924 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachValueTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final Consumer<? super V> action;
/*      */     
/*      */     ForEachValueTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Consumer<? super V> paramConsumer)
/*      */     {
/* 4936 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4937 */       this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { Consumer localConsumer;
/* 4941 */       if ((localConsumer = this.action) != null) { int j;
/* 4942 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4944 */           addToPendingCount(1);
/* 4945 */           new ForEachValueTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localConsumer)
/*      */           
/* 4947 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 4949 */         while ((localNode = advance()) != null)
/* 4950 */           localConsumer.accept(localNode.val);
/* 4951 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachEntryTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final Consumer<? super Map.Entry<K, V>> action;
/*      */     
/*      */     ForEachEntryTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */     {
/* 4963 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4964 */       this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { Consumer localConsumer;
/* 4968 */       if ((localConsumer = this.action) != null) { int j;
/* 4969 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4971 */           addToPendingCount(1);
/* 4972 */           new ForEachEntryTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localConsumer)
/*      */           
/* 4974 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 4976 */         while ((localNode = advance()) != null)
/* 4977 */           localConsumer.accept(localNode);
/* 4978 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachMappingTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final BiConsumer<? super K, ? super V> action;
/*      */     
/*      */     ForEachMappingTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, BiConsumer<? super K, ? super V> paramBiConsumer)
/*      */     {
/* 4990 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4991 */       this.action = paramBiConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { BiConsumer localBiConsumer;
/* 4995 */       if ((localBiConsumer = this.action) != null) { int j;
/* 4996 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4998 */           addToPendingCount(1);
/* 4999 */           new ForEachMappingTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localBiConsumer)
/*      */           
/* 5001 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 5003 */         while ((localNode = advance()) != null)
/* 5004 */           localBiConsumer.accept(localNode.key, localNode.val);
/* 5005 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachTransformedKeyTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final Function<? super K, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedKeyTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Function<? super K, ? extends U> paramFunction, Consumer<? super U> paramConsumer)
/*      */     {
/* 5018 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5019 */       this.transformer = paramFunction;this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       Consumer localConsumer;
/* 5024 */       if (((localFunction = this.transformer) != null) && ((localConsumer = this.action) != null)) { int j;
/*      */         int k;
/* 5026 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5028 */           addToPendingCount(1);
/* 5029 */           new ForEachTransformedKeyTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFunction, localConsumer)
/*      */           
/* 5031 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 5033 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 5035 */           if ((localObject = localFunction.apply(localNode.key)) != null)
/* 5036 */             localConsumer.accept(localObject);
/*      */         }
/* 5038 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachTransformedValueTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final Function<? super V, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedValueTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Function<? super V, ? extends U> paramFunction, Consumer<? super U> paramConsumer)
/*      */     {
/* 5051 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5052 */       this.transformer = paramFunction;this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       Consumer localConsumer;
/* 5057 */       if (((localFunction = this.transformer) != null) && ((localConsumer = this.action) != null)) { int j;
/*      */         int k;
/* 5059 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5061 */           addToPendingCount(1);
/* 5062 */           new ForEachTransformedValueTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFunction, localConsumer)
/*      */           
/* 5064 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 5066 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 5068 */           if ((localObject = localFunction.apply(localNode.val)) != null)
/* 5069 */             localConsumer.accept(localObject);
/*      */         }
/* 5071 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachTransformedEntryTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final Function<Map.Entry<K, V>, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedEntryTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Function<Map.Entry<K, V>, ? extends U> paramFunction, Consumer<? super U> paramConsumer)
/*      */     {
/* 5084 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5085 */       this.transformer = paramFunction;this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       Consumer localConsumer;
/* 5090 */       if (((localFunction = this.transformer) != null) && ((localConsumer = this.action) != null)) { int j;
/*      */         int k;
/* 5092 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5094 */           addToPendingCount(1);
/* 5095 */           new ForEachTransformedEntryTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFunction, localConsumer)
/*      */           
/* 5097 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 5099 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 5101 */           if ((localObject = localFunction.apply(localNode)) != null)
/* 5102 */             localConsumer.accept(localObject);
/*      */         }
/* 5104 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ForEachTransformedMappingTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Void>
/*      */   {
/*      */     final BiFunction<? super K, ? super V, ? extends U> transformer;
/*      */     final Consumer<? super U> action;
/*      */     
/*      */     ForEachTransformedMappingTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, Consumer<? super U> paramConsumer)
/*      */     {
/* 5118 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5119 */       this.transformer = paramBiFunction;this.action = paramConsumer;
/*      */     }
/*      */     
/*      */     public final void compute() { BiFunction localBiFunction;
/*      */       Consumer localConsumer;
/* 5124 */       if (((localBiFunction = this.transformer) != null) && ((localConsumer = this.action) != null)) { int j;
/*      */         int k;
/* 5126 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5128 */           addToPendingCount(1);
/* 5129 */           new ForEachTransformedMappingTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localBiFunction, localConsumer)
/*      */           
/* 5131 */             .fork(); }
/*      */         ConcurrentHashMap.Node localNode;
/* 5133 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 5135 */           if ((localObject = localBiFunction.apply(localNode.key, localNode.val)) != null)
/* 5136 */             localConsumer.accept(localObject);
/*      */         }
/* 5138 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchKeysTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super K, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchKeysTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Function<? super K, ? extends U> paramFunction, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 5152 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5153 */       this.searchFunction = paramFunction;this.result = paramAtomicReference; }
/*      */     
/* 5155 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       AtomicReference localAtomicReference;
/* 5159 */       if (((localFunction = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 5161 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5163 */           if (localAtomicReference.get() != null)
/* 5164 */             return;
/* 5165 */           addToPendingCount(1);
/* 5166 */           new SearchKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFunction, localAtomicReference)
/*      */           
/* 5168 */             .fork();
/*      */         }
/* 5170 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMap.Node localNode;
/* 5173 */           if ((localNode = advance()) == null) {
/* 5174 */             propagateCompletion();
/* 5175 */             break; }
/*      */           Object localObject;
/* 5177 */           if ((localObject = localFunction.apply(localNode.key)) != null) {
/* 5178 */             if (!localAtomicReference.compareAndSet(null, localObject)) break;
/* 5179 */             quietlyCompleteRoot(); break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchValuesTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super V, ? extends U> searchFunction;
/*      */     
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchValuesTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Function<? super V, ? extends U> paramFunction, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 5196 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5197 */       this.searchFunction = paramFunction;this.result = paramAtomicReference; }
/*      */     
/* 5199 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       AtomicReference localAtomicReference;
/* 5203 */       if (((localFunction = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 5205 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5207 */           if (localAtomicReference.get() != null)
/* 5208 */             return;
/* 5209 */           addToPendingCount(1);
/* 5210 */           new SearchValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFunction, localAtomicReference)
/*      */           
/* 5212 */             .fork();
/*      */         }
/* 5214 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMap.Node localNode;
/* 5217 */           if ((localNode = advance()) == null) {
/* 5218 */             propagateCompletion();
/* 5219 */             break; }
/*      */           Object localObject;
/* 5221 */           if ((localObject = localFunction.apply(localNode.val)) != null) {
/* 5222 */             if (!localAtomicReference.compareAndSet(null, localObject)) break;
/* 5223 */             quietlyCompleteRoot(); break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchEntriesTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final Function<Map.Entry<K, V>, ? extends U> searchFunction;
/*      */     
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchEntriesTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, Function<Map.Entry<K, V>, ? extends U> paramFunction, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 5240 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5241 */       this.searchFunction = paramFunction;this.result = paramAtomicReference; }
/*      */     
/* 5243 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       AtomicReference localAtomicReference;
/* 5247 */       if (((localFunction = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 5249 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5251 */           if (localAtomicReference.get() != null)
/* 5252 */             return;
/* 5253 */           addToPendingCount(1);
/* 5254 */           new SearchEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFunction, localAtomicReference)
/*      */           
/* 5256 */             .fork();
/*      */         }
/* 5258 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMap.Node localNode;
/* 5261 */           if ((localNode = advance()) == null) {
/* 5262 */             propagateCompletion();
/* 5263 */             break; }
/*      */           Object localObject;
/* 5265 */           if ((localObject = localFunction.apply(localNode)) != null) {
/* 5266 */             if (localAtomicReference.compareAndSet(null, localObject))
/* 5267 */               quietlyCompleteRoot();
/* 5268 */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchMappingsTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final BiFunction<? super K, ? super V, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchMappingsTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 5284 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5285 */       this.searchFunction = paramBiFunction;this.result = paramAtomicReference; }
/*      */     
/* 5287 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { BiFunction localBiFunction;
/*      */       AtomicReference localAtomicReference;
/* 5291 */       if (((localBiFunction = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 5293 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5295 */           if (localAtomicReference.get() != null)
/* 5296 */             return;
/* 5297 */           addToPendingCount(1);
/* 5298 */           new SearchMappingsTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localBiFunction, localAtomicReference)
/*      */           
/* 5300 */             .fork();
/*      */         }
/* 5302 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMap.Node localNode;
/* 5305 */           if ((localNode = advance()) == null) {
/* 5306 */             propagateCompletion();
/* 5307 */             break; }
/*      */           Object localObject;
/* 5309 */           if ((localObject = localBiFunction.apply(localNode.key, localNode.val)) != null) {
/* 5310 */             if (!localAtomicReference.compareAndSet(null, localObject)) break;
/* 5311 */             quietlyCompleteRoot(); break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ReduceKeysTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, K>
/*      */   {
/*      */     final BiFunction<? super K, ? super K, ? extends K> reducer;
/*      */     K result;
/*      */     ReduceKeysTask<K, V> rights;
/*      */     ReduceKeysTask<K, V> nextRight;
/*      */     
/*      */     ReduceKeysTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, ReduceKeysTask<K, V> paramReduceKeysTask, BiFunction<? super K, ? super K, ? extends K> paramBiFunction)
/*      */     {
/* 5329 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramReduceKeysTask;
/* 5330 */       this.reducer = paramBiFunction; }
/*      */     
/* 5332 */     public final K getRawResult() { return (K)this.result; }
/*      */     
/*      */     public final void compute() { BiFunction localBiFunction;
/* 5335 */       if ((localBiFunction = this.reducer) != null) { int j;
/* 5336 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5338 */           addToPendingCount(1);
/* 5339 */           (this.rights = new ReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFunction))
/*      */           
/* 5341 */             .fork();
/*      */         }
/* 5343 */         Object localObject1 = null;
/* 5344 */         Object localObject3; while ((localObject2 = advance()) != null) {
/* 5345 */           localObject3 = ((ConcurrentHashMap.Node)localObject2).key;
/* 5346 */           localObject1 = localObject3 == null ? localObject1 : localObject1 == null ? localObject3 : localBiFunction.apply(localObject1, localObject3);
/*      */         }
/* 5348 */         this.result = localObject1;
/*      */         
/* 5350 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5353 */           localObject3 = (ReduceKeysTask)localObject2;
/* 5354 */           ReduceKeysTask localReduceKeysTask = ((ReduceKeysTask)localObject3).rights;
/* 5355 */           while (localReduceKeysTask != null) {
/*      */             Object localObject5;
/* 5357 */             if ((localObject5 = localReduceKeysTask.result) != null) {
/*      */               Object localObject4;
/* 5359 */               ((ReduceKeysTask)localObject3).result = ((localObject4 = ((ReduceKeysTask)localObject3).result) == null ? localObject5 : localBiFunction.apply(localObject4, localObject5)); }
/* 5360 */             localReduceKeysTask = ((ReduceKeysTask)localObject3).rights = localReduceKeysTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReduceValuesTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, V>
/*      */   {
/*      */     final BiFunction<? super V, ? super V, ? extends V> reducer;
/*      */     V result;
/*      */     ReduceValuesTask<K, V> rights;
/*      */     ReduceValuesTask<K, V> nextRight;
/*      */     
/*      */     ReduceValuesTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, ReduceValuesTask<K, V> paramReduceValuesTask, BiFunction<? super V, ? super V, ? extends V> paramBiFunction)
/*      */     {
/* 5377 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramReduceValuesTask;
/* 5378 */       this.reducer = paramBiFunction; }
/*      */     
/* 5380 */     public final V getRawResult() { return (V)this.result; }
/*      */     
/*      */     public final void compute() { BiFunction localBiFunction;
/* 5383 */       if ((localBiFunction = this.reducer) != null) { int j;
/* 5384 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5386 */           addToPendingCount(1);
/* 5387 */           (this.rights = new ReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFunction))
/*      */           
/* 5389 */             .fork();
/*      */         }
/* 5391 */         Object localObject1 = null;
/* 5392 */         Object localObject3; while ((localObject2 = advance()) != null) {
/* 5393 */           localObject3 = ((ConcurrentHashMap.Node)localObject2).val;
/* 5394 */           localObject1 = localObject1 == null ? localObject3 : localBiFunction.apply(localObject1, localObject3);
/*      */         }
/* 5396 */         this.result = localObject1;
/*      */         
/* 5398 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5401 */           localObject3 = (ReduceValuesTask)localObject2;
/* 5402 */           ReduceValuesTask localReduceValuesTask = ((ReduceValuesTask)localObject3).rights;
/* 5403 */           while (localReduceValuesTask != null) {
/*      */             Object localObject5;
/* 5405 */             if ((localObject5 = localReduceValuesTask.result) != null) {
/*      */               Object localObject4;
/* 5407 */               ((ReduceValuesTask)localObject3).result = ((localObject4 = ((ReduceValuesTask)localObject3).result) == null ? localObject5 : localBiFunction.apply(localObject4, localObject5)); }
/* 5408 */             localReduceValuesTask = ((ReduceValuesTask)localObject3).rights = localReduceValuesTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReduceEntriesTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Map.Entry<K, V>>
/*      */   {
/*      */     final BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
/*      */     Map.Entry<K, V> result;
/*      */     ReduceEntriesTask<K, V> rights;
/*      */     ReduceEntriesTask<K, V> nextRight;
/*      */     
/*      */     ReduceEntriesTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, ReduceEntriesTask<K, V> paramReduceEntriesTask, BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> paramBiFunction)
/*      */     {
/* 5425 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramReduceEntriesTask;
/* 5426 */       this.reducer = paramBiFunction; }
/*      */     
/* 5428 */     public final Map.Entry<K, V> getRawResult() { return this.result; }
/*      */     
/*      */     public final void compute() { BiFunction localBiFunction;
/* 5431 */       if ((localBiFunction = this.reducer) != null) { int j;
/* 5432 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5434 */           addToPendingCount(1);
/* 5435 */           (this.rights = new ReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFunction))
/*      */           
/* 5437 */             .fork();
/*      */         }
/* 5439 */         Object localObject1 = null;
/* 5440 */         while ((localObject2 = advance()) != null)
/* 5441 */           localObject1 = localObject1 == null ? localObject2 : (Map.Entry)localBiFunction.apply(localObject1, localObject2);
/* 5442 */         this.result = ((Map.Entry)localObject1);
/*      */         
/* 5444 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5447 */           ReduceEntriesTask localReduceEntriesTask1 = (ReduceEntriesTask)localObject2;
/* 5448 */           ReduceEntriesTask localReduceEntriesTask2 = localReduceEntriesTask1.rights;
/* 5449 */           while (localReduceEntriesTask2 != null) {
/*      */             Map.Entry localEntry2;
/* 5451 */             if ((localEntry2 = localReduceEntriesTask2.result) != null) {
/*      */               Map.Entry localEntry1;
/* 5453 */               localReduceEntriesTask1.result = ((localEntry1 = localReduceEntriesTask1.result) == null ? localEntry2 : (Map.Entry)localBiFunction.apply(localEntry1, localEntry2)); }
/* 5454 */             localReduceEntriesTask2 = localReduceEntriesTask1.rights = localReduceEntriesTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super K, ? extends U> transformer;
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceKeysTask<K, V, U> rights;
/*      */     MapReduceKeysTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceKeysTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceKeysTask<K, V, U> paramMapReduceKeysTask, Function<? super K, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction)
/*      */     {
/* 5473 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysTask;
/* 5474 */       this.transformer = paramFunction;
/* 5475 */       this.reducer = paramBiFunction; }
/*      */     
/* 5477 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       BiFunction localBiFunction;
/* 5481 */       if (((localFunction = this.transformer) != null) && ((localBiFunction = this.reducer) != null)) { int j;
/*      */         int k;
/* 5483 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5485 */           addToPendingCount(1);
/* 5486 */           (this.rights = new MapReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localFunction, localBiFunction))
/*      */           
/* 5488 */             .fork();
/*      */         }
/* 5490 */         Object localObject1 = null;
/* 5491 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5493 */           if ((localObject3 = localFunction.apply(((ConcurrentHashMap.Node)localObject2).key)) != null)
/* 5494 */             localObject1 = localObject1 == null ? localObject3 : localBiFunction.apply(localObject1, localObject3);
/*      */         }
/* 5496 */         this.result = localObject1;
/*      */         
/* 5498 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5501 */           localObject3 = (MapReduceKeysTask)localObject2;
/* 5502 */           MapReduceKeysTask localMapReduceKeysTask = ((MapReduceKeysTask)localObject3).rights;
/* 5503 */           while (localMapReduceKeysTask != null) {
/*      */             Object localObject5;
/* 5505 */             if ((localObject5 = localMapReduceKeysTask.result) != null) {
/*      */               Object localObject4;
/* 5507 */               ((MapReduceKeysTask)localObject3).result = ((localObject4 = ((MapReduceKeysTask)localObject3).result) == null ? localObject5 : localBiFunction.apply(localObject4, localObject5)); }
/* 5508 */             localMapReduceKeysTask = ((MapReduceKeysTask)localObject3).rights = localMapReduceKeysTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final Function<? super V, ? extends U> transformer;
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceValuesTask<K, V, U> rights;
/*      */     MapReduceValuesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceValuesTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceValuesTask<K, V, U> paramMapReduceValuesTask, Function<? super V, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction)
/*      */     {
/* 5527 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesTask;
/* 5528 */       this.transformer = paramFunction;
/* 5529 */       this.reducer = paramBiFunction; }
/*      */     
/* 5531 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       BiFunction localBiFunction;
/* 5535 */       if (((localFunction = this.transformer) != null) && ((localBiFunction = this.reducer) != null)) { int j;
/*      */         int k;
/* 5537 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5539 */           addToPendingCount(1);
/* 5540 */           (this.rights = new MapReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localFunction, localBiFunction))
/*      */           
/* 5542 */             .fork();
/*      */         }
/* 5544 */         Object localObject1 = null;
/* 5545 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5547 */           if ((localObject3 = localFunction.apply(((ConcurrentHashMap.Node)localObject2).val)) != null)
/* 5548 */             localObject1 = localObject1 == null ? localObject3 : localBiFunction.apply(localObject1, localObject3);
/*      */         }
/* 5550 */         this.result = localObject1;
/*      */         
/* 5552 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5555 */           localObject3 = (MapReduceValuesTask)localObject2;
/* 5556 */           MapReduceValuesTask localMapReduceValuesTask = ((MapReduceValuesTask)localObject3).rights;
/* 5557 */           while (localMapReduceValuesTask != null) {
/*      */             Object localObject5;
/* 5559 */             if ((localObject5 = localMapReduceValuesTask.result) != null) {
/*      */               Object localObject4;
/* 5561 */               ((MapReduceValuesTask)localObject3).result = ((localObject4 = ((MapReduceValuesTask)localObject3).result) == null ? localObject5 : localBiFunction.apply(localObject4, localObject5)); }
/* 5562 */             localMapReduceValuesTask = ((MapReduceValuesTask)localObject3).rights = localMapReduceValuesTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final Function<Map.Entry<K, V>, ? extends U> transformer;
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceEntriesTask<K, V, U> rights;
/*      */     MapReduceEntriesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceEntriesTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceEntriesTask<K, V, U> paramMapReduceEntriesTask, Function<Map.Entry<K, V>, ? extends U> paramFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction)
/*      */     {
/* 5581 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesTask;
/* 5582 */       this.transformer = paramFunction;
/* 5583 */       this.reducer = paramBiFunction; }
/*      */     
/* 5585 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { Function localFunction;
/*      */       BiFunction localBiFunction;
/* 5589 */       if (((localFunction = this.transformer) != null) && ((localBiFunction = this.reducer) != null)) { int j;
/*      */         int k;
/* 5591 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5593 */           addToPendingCount(1);
/* 5594 */           (this.rights = new MapReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localFunction, localBiFunction))
/*      */           
/* 5596 */             .fork();
/*      */         }
/* 5598 */         Object localObject1 = null;
/* 5599 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5601 */           if ((localObject3 = localFunction.apply(localObject2)) != null)
/* 5602 */             localObject1 = localObject1 == null ? localObject3 : localBiFunction.apply(localObject1, localObject3);
/*      */         }
/* 5604 */         this.result = localObject1;
/*      */         
/* 5606 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5609 */           localObject3 = (MapReduceEntriesTask)localObject2;
/* 5610 */           MapReduceEntriesTask localMapReduceEntriesTask = ((MapReduceEntriesTask)localObject3).rights;
/* 5611 */           while (localMapReduceEntriesTask != null) {
/*      */             Object localObject5;
/* 5613 */             if ((localObject5 = localMapReduceEntriesTask.result) != null) {
/*      */               Object localObject4;
/* 5615 */               ((MapReduceEntriesTask)localObject3).result = ((localObject4 = ((MapReduceEntriesTask)localObject3).result) == null ? localObject5 : localBiFunction.apply(localObject4, localObject5)); }
/* 5616 */             localMapReduceEntriesTask = ((MapReduceEntriesTask)localObject3).rights = localMapReduceEntriesTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsTask<K, V, U>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, U>
/*      */   {
/*      */     final BiFunction<? super K, ? super V, ? extends U> transformer;
/*      */     final BiFunction<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceMappingsTask<K, V, U> rights;
/*      */     MapReduceMappingsTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceMappingsTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceMappingsTask<K, V, U> paramMapReduceMappingsTask, BiFunction<? super K, ? super V, ? extends U> paramBiFunction, BiFunction<? super U, ? super U, ? extends U> paramBiFunction1)
/*      */     {
/* 5635 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsTask;
/* 5636 */       this.transformer = paramBiFunction;
/* 5637 */       this.reducer = paramBiFunction1; }
/*      */     
/* 5639 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { BiFunction localBiFunction1;
/*      */       BiFunction localBiFunction2;
/* 5643 */       if (((localBiFunction1 = this.transformer) != null) && ((localBiFunction2 = this.reducer) != null)) { int j;
/*      */         int k;
/* 5645 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5647 */           addToPendingCount(1);
/* 5648 */           (this.rights = new MapReduceMappingsTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFunction1, localBiFunction2))
/*      */           
/* 5650 */             .fork();
/*      */         }
/* 5652 */         Object localObject1 = null;
/* 5653 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5655 */           if ((localObject3 = localBiFunction1.apply(((ConcurrentHashMap.Node)localObject2).key, ((ConcurrentHashMap.Node)localObject2).val)) != null)
/* 5656 */             localObject1 = localObject1 == null ? localObject3 : localBiFunction2.apply(localObject1, localObject3);
/*      */         }
/* 5658 */         this.result = localObject1;
/*      */         
/* 5660 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/*      */ 
/* 5663 */           localObject3 = (MapReduceMappingsTask)localObject2;
/* 5664 */           MapReduceMappingsTask localMapReduceMappingsTask = ((MapReduceMappingsTask)localObject3).rights;
/* 5665 */           while (localMapReduceMappingsTask != null) {
/*      */             Object localObject5;
/* 5667 */             if ((localObject5 = localMapReduceMappingsTask.result) != null) {
/*      */               Object localObject4;
/* 5669 */               ((MapReduceMappingsTask)localObject3).result = ((localObject4 = ((MapReduceMappingsTask)localObject3).result) == null ? localObject5 : localBiFunction2.apply(localObject4, localObject5)); }
/* 5670 */             localMapReduceMappingsTask = ((MapReduceMappingsTask)localObject3).rights = localMapReduceMappingsTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysToDoubleTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleFunction<? super K> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceKeysToDoubleTask<K, V> rights;
/*      */     MapReduceKeysToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceKeysToDoubleTask<K, V> paramMapReduceKeysToDoubleTask, ToDoubleFunction<? super K> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */     {
/* 5691 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysToDoubleTask;
/* 5692 */       this.transformer = paramToDoubleFunction;
/* 5693 */       this.basis = paramDouble;this.reducer = paramDoubleBinaryOperator; }
/*      */     
/* 5695 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToDoubleFunction localToDoubleFunction;
/*      */       DoubleBinaryOperator localDoubleBinaryOperator;
/* 5699 */       if (((localToDoubleFunction = this.transformer) != null) && ((localDoubleBinaryOperator = this.reducer) != null))
/*      */       {
/* 5701 */         double d = this.basis;
/* 5702 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5704 */           addToPendingCount(1);
/* 5705 */           (this.rights = new MapReduceKeysToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToDoubleFunction, d, localDoubleBinaryOperator))
/*      */           
/* 5707 */             .fork();
/*      */         }
/* 5709 */         while ((localObject = advance()) != null)
/* 5710 */           d = localDoubleBinaryOperator.applyAsDouble(d, localToDoubleFunction.applyAsDouble(((ConcurrentHashMap.Node)localObject).key));
/* 5711 */         this.result = d;
/*      */         
/* 5713 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 5716 */           MapReduceKeysToDoubleTask localMapReduceKeysToDoubleTask1 = (MapReduceKeysToDoubleTask)localObject;
/* 5717 */           MapReduceKeysToDoubleTask localMapReduceKeysToDoubleTask2 = localMapReduceKeysToDoubleTask1.rights;
/* 5718 */           while (localMapReduceKeysToDoubleTask2 != null) {
/* 5719 */             localMapReduceKeysToDoubleTask1.result = localDoubleBinaryOperator.applyAsDouble(localMapReduceKeysToDoubleTask1.result, localMapReduceKeysToDoubleTask2.result);
/* 5720 */             localMapReduceKeysToDoubleTask2 = localMapReduceKeysToDoubleTask1.rights = localMapReduceKeysToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesToDoubleTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleFunction<? super V> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceValuesToDoubleTask<K, V> rights;
/*      */     MapReduceValuesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceValuesToDoubleTask<K, V> paramMapReduceValuesToDoubleTask, ToDoubleFunction<? super V> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */     {
/* 5741 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesToDoubleTask;
/* 5742 */       this.transformer = paramToDoubleFunction;
/* 5743 */       this.basis = paramDouble;this.reducer = paramDoubleBinaryOperator; }
/*      */     
/* 5745 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToDoubleFunction localToDoubleFunction;
/*      */       DoubleBinaryOperator localDoubleBinaryOperator;
/* 5749 */       if (((localToDoubleFunction = this.transformer) != null) && ((localDoubleBinaryOperator = this.reducer) != null))
/*      */       {
/* 5751 */         double d = this.basis;
/* 5752 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5754 */           addToPendingCount(1);
/* 5755 */           (this.rights = new MapReduceValuesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToDoubleFunction, d, localDoubleBinaryOperator))
/*      */           
/* 5757 */             .fork();
/*      */         }
/* 5759 */         while ((localObject = advance()) != null)
/* 5760 */           d = localDoubleBinaryOperator.applyAsDouble(d, localToDoubleFunction.applyAsDouble(((ConcurrentHashMap.Node)localObject).val));
/* 5761 */         this.result = d;
/*      */         
/* 5763 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 5766 */           MapReduceValuesToDoubleTask localMapReduceValuesToDoubleTask1 = (MapReduceValuesToDoubleTask)localObject;
/* 5767 */           MapReduceValuesToDoubleTask localMapReduceValuesToDoubleTask2 = localMapReduceValuesToDoubleTask1.rights;
/* 5768 */           while (localMapReduceValuesToDoubleTask2 != null) {
/* 5769 */             localMapReduceValuesToDoubleTask1.result = localDoubleBinaryOperator.applyAsDouble(localMapReduceValuesToDoubleTask1.result, localMapReduceValuesToDoubleTask2.result);
/* 5770 */             localMapReduceValuesToDoubleTask2 = localMapReduceValuesToDoubleTask1.rights = localMapReduceValuesToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesToDoubleTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleFunction<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceEntriesToDoubleTask<K, V> rights;
/*      */     MapReduceEntriesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceEntriesToDoubleTask<K, V> paramMapReduceEntriesToDoubleTask, ToDoubleFunction<Map.Entry<K, V>> paramToDoubleFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */     {
/* 5791 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesToDoubleTask;
/* 5792 */       this.transformer = paramToDoubleFunction;
/* 5793 */       this.basis = paramDouble;this.reducer = paramDoubleBinaryOperator; }
/*      */     
/* 5795 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToDoubleFunction localToDoubleFunction;
/*      */       DoubleBinaryOperator localDoubleBinaryOperator;
/* 5799 */       if (((localToDoubleFunction = this.transformer) != null) && ((localDoubleBinaryOperator = this.reducer) != null))
/*      */       {
/* 5801 */         double d = this.basis;
/* 5802 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5804 */           addToPendingCount(1);
/* 5805 */           (this.rights = new MapReduceEntriesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToDoubleFunction, d, localDoubleBinaryOperator))
/*      */           
/* 5807 */             .fork();
/*      */         }
/* 5809 */         while ((localObject = advance()) != null)
/* 5810 */           d = localDoubleBinaryOperator.applyAsDouble(d, localToDoubleFunction.applyAsDouble(localObject));
/* 5811 */         this.result = d;
/*      */         
/* 5813 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 5816 */           MapReduceEntriesToDoubleTask localMapReduceEntriesToDoubleTask1 = (MapReduceEntriesToDoubleTask)localObject;
/* 5817 */           MapReduceEntriesToDoubleTask localMapReduceEntriesToDoubleTask2 = localMapReduceEntriesToDoubleTask1.rights;
/* 5818 */           while (localMapReduceEntriesToDoubleTask2 != null) {
/* 5819 */             localMapReduceEntriesToDoubleTask1.result = localDoubleBinaryOperator.applyAsDouble(localMapReduceEntriesToDoubleTask1.result, localMapReduceEntriesToDoubleTask2.result);
/* 5820 */             localMapReduceEntriesToDoubleTask2 = localMapReduceEntriesToDoubleTask1.rights = localMapReduceEntriesToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsToDoubleTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Double>
/*      */   {
/*      */     final ToDoubleBiFunction<? super K, ? super V> transformer;
/*      */     
/*      */     final DoubleBinaryOperator reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceMappingsToDoubleTask<K, V> rights;
/*      */     MapReduceMappingsToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToDoubleTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceMappingsToDoubleTask<K, V> paramMapReduceMappingsToDoubleTask, ToDoubleBiFunction<? super K, ? super V> paramToDoubleBiFunction, double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator)
/*      */     {
/* 5841 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsToDoubleTask;
/* 5842 */       this.transformer = paramToDoubleBiFunction;
/* 5843 */       this.basis = paramDouble;this.reducer = paramDoubleBinaryOperator; }
/*      */     
/* 5845 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToDoubleBiFunction localToDoubleBiFunction;
/*      */       DoubleBinaryOperator localDoubleBinaryOperator;
/* 5849 */       if (((localToDoubleBiFunction = this.transformer) != null) && ((localDoubleBinaryOperator = this.reducer) != null))
/*      */       {
/* 5851 */         double d = this.basis;
/* 5852 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5854 */           addToPendingCount(1);
/* 5855 */           (this.rights = new MapReduceMappingsToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToDoubleBiFunction, d, localDoubleBinaryOperator))
/*      */           
/* 5857 */             .fork();
/*      */         }
/* 5859 */         while ((localObject = advance()) != null)
/* 5860 */           d = localDoubleBinaryOperator.applyAsDouble(d, localToDoubleBiFunction.applyAsDouble(((ConcurrentHashMap.Node)localObject).key, ((ConcurrentHashMap.Node)localObject).val));
/* 5861 */         this.result = d;
/*      */         
/* 5863 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 5866 */           MapReduceMappingsToDoubleTask localMapReduceMappingsToDoubleTask1 = (MapReduceMappingsToDoubleTask)localObject;
/* 5867 */           MapReduceMappingsToDoubleTask localMapReduceMappingsToDoubleTask2 = localMapReduceMappingsToDoubleTask1.rights;
/* 5868 */           while (localMapReduceMappingsToDoubleTask2 != null) {
/* 5869 */             localMapReduceMappingsToDoubleTask1.result = localDoubleBinaryOperator.applyAsDouble(localMapReduceMappingsToDoubleTask1.result, localMapReduceMappingsToDoubleTask2.result);
/* 5870 */             localMapReduceMappingsToDoubleTask2 = localMapReduceMappingsToDoubleTask1.rights = localMapReduceMappingsToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysToLongTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongFunction<? super K> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceKeysToLongTask<K, V> rights;
/*      */     MapReduceKeysToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceKeysToLongTask<K, V> paramMapReduceKeysToLongTask, ToLongFunction<? super K> paramToLongFunction, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*      */     {
/* 5891 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysToLongTask;
/* 5892 */       this.transformer = paramToLongFunction;
/* 5893 */       this.basis = paramLong;this.reducer = paramLongBinaryOperator; }
/*      */     
/* 5895 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToLongFunction localToLongFunction;
/*      */       LongBinaryOperator localLongBinaryOperator;
/* 5899 */       if (((localToLongFunction = this.transformer) != null) && ((localLongBinaryOperator = this.reducer) != null))
/*      */       {
/* 5901 */         long l = this.basis;
/* 5902 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5904 */           addToPendingCount(1);
/* 5905 */           (this.rights = new MapReduceKeysToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToLongFunction, l, localLongBinaryOperator))
/*      */           
/* 5907 */             .fork();
/*      */         }
/* 5909 */         while ((localObject = advance()) != null)
/* 5910 */           l = localLongBinaryOperator.applyAsLong(l, localToLongFunction.applyAsLong(((ConcurrentHashMap.Node)localObject).key));
/* 5911 */         this.result = l;
/*      */         
/* 5913 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 5916 */           MapReduceKeysToLongTask localMapReduceKeysToLongTask1 = (MapReduceKeysToLongTask)localObject;
/* 5917 */           MapReduceKeysToLongTask localMapReduceKeysToLongTask2 = localMapReduceKeysToLongTask1.rights;
/* 5918 */           while (localMapReduceKeysToLongTask2 != null) {
/* 5919 */             localMapReduceKeysToLongTask1.result = localLongBinaryOperator.applyAsLong(localMapReduceKeysToLongTask1.result, localMapReduceKeysToLongTask2.result);
/* 5920 */             localMapReduceKeysToLongTask2 = localMapReduceKeysToLongTask1.rights = localMapReduceKeysToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesToLongTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongFunction<? super V> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceValuesToLongTask<K, V> rights;
/*      */     MapReduceValuesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceValuesToLongTask<K, V> paramMapReduceValuesToLongTask, ToLongFunction<? super V> paramToLongFunction, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*      */     {
/* 5941 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesToLongTask;
/* 5942 */       this.transformer = paramToLongFunction;
/* 5943 */       this.basis = paramLong;this.reducer = paramLongBinaryOperator; }
/*      */     
/* 5945 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToLongFunction localToLongFunction;
/*      */       LongBinaryOperator localLongBinaryOperator;
/* 5949 */       if (((localToLongFunction = this.transformer) != null) && ((localLongBinaryOperator = this.reducer) != null))
/*      */       {
/* 5951 */         long l = this.basis;
/* 5952 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5954 */           addToPendingCount(1);
/* 5955 */           (this.rights = new MapReduceValuesToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToLongFunction, l, localLongBinaryOperator))
/*      */           
/* 5957 */             .fork();
/*      */         }
/* 5959 */         while ((localObject = advance()) != null)
/* 5960 */           l = localLongBinaryOperator.applyAsLong(l, localToLongFunction.applyAsLong(((ConcurrentHashMap.Node)localObject).val));
/* 5961 */         this.result = l;
/*      */         
/* 5963 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 5966 */           MapReduceValuesToLongTask localMapReduceValuesToLongTask1 = (MapReduceValuesToLongTask)localObject;
/* 5967 */           MapReduceValuesToLongTask localMapReduceValuesToLongTask2 = localMapReduceValuesToLongTask1.rights;
/* 5968 */           while (localMapReduceValuesToLongTask2 != null) {
/* 5969 */             localMapReduceValuesToLongTask1.result = localLongBinaryOperator.applyAsLong(localMapReduceValuesToLongTask1.result, localMapReduceValuesToLongTask2.result);
/* 5970 */             localMapReduceValuesToLongTask2 = localMapReduceValuesToLongTask1.rights = localMapReduceValuesToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesToLongTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongFunction<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceEntriesToLongTask<K, V> rights;
/*      */     MapReduceEntriesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceEntriesToLongTask<K, V> paramMapReduceEntriesToLongTask, ToLongFunction<Map.Entry<K, V>> paramToLongFunction, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*      */     {
/* 5991 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesToLongTask;
/* 5992 */       this.transformer = paramToLongFunction;
/* 5993 */       this.basis = paramLong;this.reducer = paramLongBinaryOperator; }
/*      */     
/* 5995 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToLongFunction localToLongFunction;
/*      */       LongBinaryOperator localLongBinaryOperator;
/* 5999 */       if (((localToLongFunction = this.transformer) != null) && ((localLongBinaryOperator = this.reducer) != null))
/*      */       {
/* 6001 */         long l = this.basis;
/* 6002 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 6004 */           addToPendingCount(1);
/* 6005 */           (this.rights = new MapReduceEntriesToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToLongFunction, l, localLongBinaryOperator))
/*      */           
/* 6007 */             .fork();
/*      */         }
/* 6009 */         while ((localObject = advance()) != null)
/* 6010 */           l = localLongBinaryOperator.applyAsLong(l, localToLongFunction.applyAsLong(localObject));
/* 6011 */         this.result = l;
/*      */         
/* 6013 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 6016 */           MapReduceEntriesToLongTask localMapReduceEntriesToLongTask1 = (MapReduceEntriesToLongTask)localObject;
/* 6017 */           MapReduceEntriesToLongTask localMapReduceEntriesToLongTask2 = localMapReduceEntriesToLongTask1.rights;
/* 6018 */           while (localMapReduceEntriesToLongTask2 != null) {
/* 6019 */             localMapReduceEntriesToLongTask1.result = localLongBinaryOperator.applyAsLong(localMapReduceEntriesToLongTask1.result, localMapReduceEntriesToLongTask2.result);
/* 6020 */             localMapReduceEntriesToLongTask2 = localMapReduceEntriesToLongTask1.rights = localMapReduceEntriesToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsToLongTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Long>
/*      */   {
/*      */     final ToLongBiFunction<? super K, ? super V> transformer;
/*      */     
/*      */     final LongBinaryOperator reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceMappingsToLongTask<K, V> rights;
/*      */     MapReduceMappingsToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToLongTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceMappingsToLongTask<K, V> paramMapReduceMappingsToLongTask, ToLongBiFunction<? super K, ? super V> paramToLongBiFunction, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*      */     {
/* 6041 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsToLongTask;
/* 6042 */       this.transformer = paramToLongBiFunction;
/* 6043 */       this.basis = paramLong;this.reducer = paramLongBinaryOperator; }
/*      */     
/* 6045 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToLongBiFunction localToLongBiFunction;
/*      */       LongBinaryOperator localLongBinaryOperator;
/* 6049 */       if (((localToLongBiFunction = this.transformer) != null) && ((localLongBinaryOperator = this.reducer) != null))
/*      */       {
/* 6051 */         long l = this.basis;
/* 6052 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 6054 */           addToPendingCount(1);
/* 6055 */           (this.rights = new MapReduceMappingsToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localToLongBiFunction, l, localLongBinaryOperator))
/*      */           
/* 6057 */             .fork();
/*      */         }
/* 6059 */         while ((localObject = advance()) != null)
/* 6060 */           l = localLongBinaryOperator.applyAsLong(l, localToLongBiFunction.applyAsLong(((ConcurrentHashMap.Node)localObject).key, ((ConcurrentHashMap.Node)localObject).val));
/* 6061 */         this.result = l;
/*      */         
/* 6063 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 6066 */           MapReduceMappingsToLongTask localMapReduceMappingsToLongTask1 = (MapReduceMappingsToLongTask)localObject;
/* 6067 */           MapReduceMappingsToLongTask localMapReduceMappingsToLongTask2 = localMapReduceMappingsToLongTask1.rights;
/* 6068 */           while (localMapReduceMappingsToLongTask2 != null) {
/* 6069 */             localMapReduceMappingsToLongTask1.result = localLongBinaryOperator.applyAsLong(localMapReduceMappingsToLongTask1.result, localMapReduceMappingsToLongTask2.result);
/* 6070 */             localMapReduceMappingsToLongTask2 = localMapReduceMappingsToLongTask1.rights = localMapReduceMappingsToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysToIntTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntFunction<? super K> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceKeysToIntTask<K, V> rights;
/*      */     MapReduceKeysToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceKeysToIntTask<K, V> paramMapReduceKeysToIntTask, ToIntFunction<? super K> paramToIntFunction, int paramInt4, IntBinaryOperator paramIntBinaryOperator)
/*      */     {
/* 6091 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysToIntTask;
/* 6092 */       this.transformer = paramToIntFunction;
/* 6093 */       this.basis = paramInt4;this.reducer = paramIntBinaryOperator; }
/*      */     
/* 6095 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToIntFunction localToIntFunction;
/*      */       IntBinaryOperator localIntBinaryOperator;
/* 6099 */       if (((localToIntFunction = this.transformer) != null) && ((localIntBinaryOperator = this.reducer) != null))
/*      */       {
/* 6101 */         int i = this.basis;
/* 6102 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 6104 */           addToPendingCount(1);
/* 6105 */           (this.rights = new MapReduceKeysToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localToIntFunction, i, localIntBinaryOperator))
/*      */           
/* 6107 */             .fork();
/*      */         }
/* 6109 */         while ((localObject = advance()) != null)
/* 6110 */           i = localIntBinaryOperator.applyAsInt(i, localToIntFunction.applyAsInt(((ConcurrentHashMap.Node)localObject).key));
/* 6111 */         this.result = i;
/*      */         
/* 6113 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 6116 */           MapReduceKeysToIntTask localMapReduceKeysToIntTask1 = (MapReduceKeysToIntTask)localObject;
/* 6117 */           MapReduceKeysToIntTask localMapReduceKeysToIntTask2 = localMapReduceKeysToIntTask1.rights;
/* 6118 */           while (localMapReduceKeysToIntTask2 != null) {
/* 6119 */             localMapReduceKeysToIntTask1.result = localIntBinaryOperator.applyAsInt(localMapReduceKeysToIntTask1.result, localMapReduceKeysToIntTask2.result);
/* 6120 */             localMapReduceKeysToIntTask2 = localMapReduceKeysToIntTask1.rights = localMapReduceKeysToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesToIntTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntFunction<? super V> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceValuesToIntTask<K, V> rights;
/*      */     MapReduceValuesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceValuesToIntTask<K, V> paramMapReduceValuesToIntTask, ToIntFunction<? super V> paramToIntFunction, int paramInt4, IntBinaryOperator paramIntBinaryOperator)
/*      */     {
/* 6141 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesToIntTask;
/* 6142 */       this.transformer = paramToIntFunction;
/* 6143 */       this.basis = paramInt4;this.reducer = paramIntBinaryOperator; }
/*      */     
/* 6145 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToIntFunction localToIntFunction;
/*      */       IntBinaryOperator localIntBinaryOperator;
/* 6149 */       if (((localToIntFunction = this.transformer) != null) && ((localIntBinaryOperator = this.reducer) != null))
/*      */       {
/* 6151 */         int i = this.basis;
/* 6152 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 6154 */           addToPendingCount(1);
/* 6155 */           (this.rights = new MapReduceValuesToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localToIntFunction, i, localIntBinaryOperator))
/*      */           
/* 6157 */             .fork();
/*      */         }
/* 6159 */         while ((localObject = advance()) != null)
/* 6160 */           i = localIntBinaryOperator.applyAsInt(i, localToIntFunction.applyAsInt(((ConcurrentHashMap.Node)localObject).val));
/* 6161 */         this.result = i;
/*      */         
/* 6163 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 6166 */           MapReduceValuesToIntTask localMapReduceValuesToIntTask1 = (MapReduceValuesToIntTask)localObject;
/* 6167 */           MapReduceValuesToIntTask localMapReduceValuesToIntTask2 = localMapReduceValuesToIntTask1.rights;
/* 6168 */           while (localMapReduceValuesToIntTask2 != null) {
/* 6169 */             localMapReduceValuesToIntTask1.result = localIntBinaryOperator.applyAsInt(localMapReduceValuesToIntTask1.result, localMapReduceValuesToIntTask2.result);
/* 6170 */             localMapReduceValuesToIntTask2 = localMapReduceValuesToIntTask1.rights = localMapReduceValuesToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesToIntTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntFunction<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceEntriesToIntTask<K, V> rights;
/*      */     MapReduceEntriesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceEntriesToIntTask<K, V> paramMapReduceEntriesToIntTask, ToIntFunction<Map.Entry<K, V>> paramToIntFunction, int paramInt4, IntBinaryOperator paramIntBinaryOperator)
/*      */     {
/* 6191 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesToIntTask;
/* 6192 */       this.transformer = paramToIntFunction;
/* 6193 */       this.basis = paramInt4;this.reducer = paramIntBinaryOperator; }
/*      */     
/* 6195 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToIntFunction localToIntFunction;
/*      */       IntBinaryOperator localIntBinaryOperator;
/* 6199 */       if (((localToIntFunction = this.transformer) != null) && ((localIntBinaryOperator = this.reducer) != null))
/*      */       {
/* 6201 */         int i = this.basis;
/* 6202 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 6204 */           addToPendingCount(1);
/* 6205 */           (this.rights = new MapReduceEntriesToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localToIntFunction, i, localIntBinaryOperator))
/*      */           
/* 6207 */             .fork();
/*      */         }
/* 6209 */         while ((localObject = advance()) != null)
/* 6210 */           i = localIntBinaryOperator.applyAsInt(i, localToIntFunction.applyAsInt(localObject));
/* 6211 */         this.result = i;
/*      */         
/* 6213 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 6216 */           MapReduceEntriesToIntTask localMapReduceEntriesToIntTask1 = (MapReduceEntriesToIntTask)localObject;
/* 6217 */           MapReduceEntriesToIntTask localMapReduceEntriesToIntTask2 = localMapReduceEntriesToIntTask1.rights;
/* 6218 */           while (localMapReduceEntriesToIntTask2 != null) {
/* 6219 */             localMapReduceEntriesToIntTask1.result = localIntBinaryOperator.applyAsInt(localMapReduceEntriesToIntTask1.result, localMapReduceEntriesToIntTask2.result);
/* 6220 */             localMapReduceEntriesToIntTask2 = localMapReduceEntriesToIntTask1.rights = localMapReduceEntriesToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsToIntTask<K, V>
/*      */     extends ConcurrentHashMap.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ToIntBiFunction<? super K, ? super V> transformer;
/*      */     
/*      */     final IntBinaryOperator reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceMappingsToIntTask<K, V> rights;
/*      */     MapReduceMappingsToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToIntTask(ConcurrentHashMap.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMap.Node<K, V>[] paramArrayOfNode, MapReduceMappingsToIntTask<K, V> paramMapReduceMappingsToIntTask, ToIntBiFunction<? super K, ? super V> paramToIntBiFunction, int paramInt4, IntBinaryOperator paramIntBinaryOperator)
/*      */     {
/* 6241 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsToIntTask;
/* 6242 */       this.transformer = paramToIntBiFunction;
/* 6243 */       this.basis = paramInt4;this.reducer = paramIntBinaryOperator; }
/*      */     
/* 6245 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ToIntBiFunction localToIntBiFunction;
/*      */       IntBinaryOperator localIntBinaryOperator;
/* 6249 */       if (((localToIntBiFunction = this.transformer) != null) && ((localIntBinaryOperator = this.reducer) != null))
/*      */       {
/* 6251 */         int i = this.basis;
/* 6252 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 6254 */           addToPendingCount(1);
/* 6255 */           (this.rights = new MapReduceMappingsToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localToIntBiFunction, i, localIntBinaryOperator))
/*      */           
/* 6257 */             .fork();
/*      */         }
/* 6259 */         while ((localObject = advance()) != null)
/* 6260 */           i = localIntBinaryOperator.applyAsInt(i, localToIntBiFunction.applyAsInt(((ConcurrentHashMap.Node)localObject).key, ((ConcurrentHashMap.Node)localObject).val));
/* 6261 */         this.result = i;
/*      */         
/* 6263 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/*      */ 
/* 6266 */           MapReduceMappingsToIntTask localMapReduceMappingsToIntTask1 = (MapReduceMappingsToIntTask)localObject;
/* 6267 */           MapReduceMappingsToIntTask localMapReduceMappingsToIntTask2 = localMapReduceMappingsToIntTask1.rights;
/* 6268 */           while (localMapReduceMappingsToIntTask2 != null) {
/* 6269 */             localMapReduceMappingsToIntTask1.result = localIntBinaryOperator.applyAsInt(localMapReduceMappingsToIntTask1.result, localMapReduceMappingsToIntTask2.result);
/* 6270 */             localMapReduceMappingsToIntTask2 = localMapReduceMappingsToIntTask1.rights = localMapReduceMappingsToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 6289 */       U = Unsafe.getUnsafe();
/* 6290 */       Class localClass1 = ConcurrentHashMap.class;
/*      */       
/* 6292 */       SIZECTL = U.objectFieldOffset(localClass1.getDeclaredField("sizeCtl"));
/*      */       
/* 6294 */       TRANSFERINDEX = U.objectFieldOffset(localClass1.getDeclaredField("transferIndex"));
/*      */       
/* 6296 */       BASECOUNT = U.objectFieldOffset(localClass1.getDeclaredField("baseCount"));
/*      */       
/* 6298 */       CELLSBUSY = U.objectFieldOffset(localClass1.getDeclaredField("cellsBusy"));
/* 6299 */       Class localClass2 = CounterCell.class;
/*      */       
/* 6301 */       CELLVALUE = U.objectFieldOffset(localClass2.getDeclaredField("value"));
/* 6302 */       Class localClass3 = Node[].class;
/* 6303 */       ABASE = U.arrayBaseOffset(localClass3);
/* 6304 */       int i = U.arrayIndexScale(localClass3);
/* 6305 */       if ((i & i - 1) != 0)
/* 6306 */         throw new Error("data type scale not a power of two");
/* 6307 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(i);
/*      */     } catch (Exception localException) {
/* 6309 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ConcurrentHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */