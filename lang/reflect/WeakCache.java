/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WeakCache<K, P, V>
/*     */ {
/*  59 */   private final ReferenceQueue<K> refQueue = new ReferenceQueue();
/*     */   
/*     */ 
/*  62 */   private final ConcurrentMap<Object, ConcurrentMap<Object, Supplier<V>>> map = new ConcurrentHashMap();
/*     */   
/*  64 */   private final ConcurrentMap<Supplier<V>, Boolean> reverseMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final BiFunction<K, P, ?> subKeyFactory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final BiFunction<K, P, V> valueFactory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WeakCache(BiFunction<K, P, ?> paramBiFunction, BiFunction<K, P, V> paramBiFunction1)
/*     */   {
/*  81 */     this.subKeyFactory = ((BiFunction)Objects.requireNonNull(paramBiFunction));
/*  82 */     this.valueFactory = ((BiFunction)Objects.requireNonNull(paramBiFunction1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V get(K paramK, P paramP)
/*     */   {
/* 101 */     Objects.requireNonNull(paramP);
/*     */     
/* 103 */     expungeStaleEntries();
/*     */     
/* 105 */     Object localObject1 = CacheKey.valueOf(paramK, this.refQueue);
/*     */     
/*     */ 
/* 108 */     Object localObject2 = (ConcurrentMap)this.map.get(localObject1);
/* 109 */     if (localObject2 == null)
/*     */     {
/* 111 */       localObject3 = (ConcurrentMap)this.map.putIfAbsent(localObject1, localObject2 = new ConcurrentHashMap());
/*     */       
/* 113 */       if (localObject3 != null) {
/* 114 */         localObject2 = localObject3;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 120 */     Object localObject3 = Objects.requireNonNull(this.subKeyFactory.apply(paramK, paramP));
/* 121 */     Object localObject4 = (Supplier)((ConcurrentMap)localObject2).get(localObject3);
/* 122 */     Factory localFactory = null;
/*     */     for (;;)
/*     */     {
/* 125 */       if (localObject4 != null)
/*     */       {
/* 127 */         Object localObject5 = ((Supplier)localObject4).get();
/* 128 */         if (localObject5 != null) {
/* 129 */           return (V)localObject5;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 137 */       if (localFactory == null) {
/* 138 */         localFactory = new Factory(paramK, paramP, localObject3, (ConcurrentMap)localObject2);
/*     */       }
/*     */       
/* 141 */       if (localObject4 == null) {
/* 142 */         localObject4 = (Supplier)((ConcurrentMap)localObject2).putIfAbsent(localObject3, localFactory);
/* 143 */         if (localObject4 == null)
/*     */         {
/* 145 */           localObject4 = localFactory;
/*     */         }
/*     */         
/*     */       }
/* 149 */       else if (((ConcurrentMap)localObject2).replace(localObject3, localObject4, localFactory))
/*     */       {
/*     */ 
/*     */ 
/* 153 */         localObject4 = localFactory;
/*     */       }
/*     */       else {
/* 156 */         localObject4 = (Supplier)((ConcurrentMap)localObject2).get(localObject3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsValue(V paramV)
/*     */   {
/* 172 */     Objects.requireNonNull(paramV);
/*     */     
/* 174 */     expungeStaleEntries();
/* 175 */     return this.reverseMap.containsKey(new LookupValue(paramV));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 183 */     expungeStaleEntries();
/* 184 */     return this.reverseMap.size();
/*     */   }
/*     */   
/*     */   private void expungeStaleEntries() {
/*     */     CacheKey localCacheKey;
/* 189 */     while ((localCacheKey = (CacheKey)this.refQueue.poll()) != null) {
/* 190 */       localCacheKey.expungeFrom(this.map, this.reverseMap);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final class Factory
/*     */     implements Supplier<V>
/*     */   {
/*     */     private final K key;
/*     */     
/*     */     private final P parameter;
/*     */     
/*     */     private final Object subKey;
/*     */     private final ConcurrentMap<Object, Supplier<V>> valuesMap;
/*     */     
/*     */     Factory(P paramP, Object paramObject, ConcurrentMap<Object, Supplier<V>> paramConcurrentMap)
/*     */     {
/* 207 */       this.key = paramP;
/* 208 */       this.parameter = paramObject;
/* 209 */       this.subKey = paramConcurrentMap;
/* 210 */       ConcurrentMap localConcurrentMap; this.valuesMap = localConcurrentMap;
/*     */     }
/*     */     
/*     */     /* Error */
/*     */     public synchronized V get()
/*     */     {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield 6	java/lang/reflect/WeakCache$Factory:valuesMap	Ljava/util/concurrent/ConcurrentMap;
/*     */       //   4: aload_0
/*     */       //   5: getfield 5	java/lang/reflect/WeakCache$Factory:subKey	Ljava/lang/Object;
/*     */       //   8: invokeinterface 7 2 0
/*     */       //   13: checkcast 8	java/util/function/Supplier
/*     */       //   16: astore_1
/*     */       //   17: aload_1
/*     */       //   18: aload_0
/*     */       //   19: if_acmpeq +5 -> 24
/*     */       //   22: aconst_null
/*     */       //   23: areturn
/*     */       //   24: aconst_null
/*     */       //   25: astore_2
/*     */       //   26: aload_0
/*     */       //   27: getfield 1	java/lang/reflect/WeakCache$Factory:this$0	Ljava/lang/reflect/WeakCache;
/*     */       //   30: invokestatic 9	java/lang/reflect/WeakCache:access$000	(Ljava/lang/reflect/WeakCache;)Ljava/util/function/BiFunction;
/*     */       //   33: aload_0
/*     */       //   34: getfield 3	java/lang/reflect/WeakCache$Factory:key	Ljava/lang/Object;
/*     */       //   37: aload_0
/*     */       //   38: getfield 4	java/lang/reflect/WeakCache$Factory:parameter	Ljava/lang/Object;
/*     */       //   41: invokeinterface 10 3 0
/*     */       //   46: invokestatic 11	java/util/Objects:requireNonNull	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */       //   49: astore_2
/*     */       //   50: aload_2
/*     */       //   51: ifnonnull +43 -> 94
/*     */       //   54: aload_0
/*     */       //   55: getfield 6	java/lang/reflect/WeakCache$Factory:valuesMap	Ljava/util/concurrent/ConcurrentMap;
/*     */       //   58: aload_0
/*     */       //   59: getfield 5	java/lang/reflect/WeakCache$Factory:subKey	Ljava/lang/Object;
/*     */       //   62: aload_0
/*     */       //   63: invokeinterface 12 3 0
/*     */       //   68: pop
/*     */       //   69: goto +25 -> 94
/*     */       //   72: astore_3
/*     */       //   73: aload_2
/*     */       //   74: ifnonnull +18 -> 92
/*     */       //   77: aload_0
/*     */       //   78: getfield 6	java/lang/reflect/WeakCache$Factory:valuesMap	Ljava/util/concurrent/ConcurrentMap;
/*     */       //   81: aload_0
/*     */       //   82: getfield 5	java/lang/reflect/WeakCache$Factory:subKey	Ljava/lang/Object;
/*     */       //   85: aload_0
/*     */       //   86: invokeinterface 12 3 0
/*     */       //   91: pop
/*     */       //   92: aload_3
/*     */       //   93: athrow
/*     */       //   94: getstatic 13	java/lang/reflect/WeakCache$Factory:$assertionsDisabled	Z
/*     */       //   97: ifne +15 -> 112
/*     */       //   100: aload_2
/*     */       //   101: ifnonnull +11 -> 112
/*     */       //   104: new 14	java/lang/AssertionError
/*     */       //   107: dup
/*     */       //   108: invokespecial 15	java/lang/AssertionError:<init>	()V
/*     */       //   111: athrow
/*     */       //   112: new 16	java/lang/reflect/WeakCache$CacheValue
/*     */       //   115: dup
/*     */       //   116: aload_2
/*     */       //   117: invokespecial 17	java/lang/reflect/WeakCache$CacheValue:<init>	(Ljava/lang/Object;)V
/*     */       //   120: astore_3
/*     */       //   121: aload_0
/*     */       //   122: getfield 6	java/lang/reflect/WeakCache$Factory:valuesMap	Ljava/util/concurrent/ConcurrentMap;
/*     */       //   125: aload_0
/*     */       //   126: getfield 5	java/lang/reflect/WeakCache$Factory:subKey	Ljava/lang/Object;
/*     */       //   129: aload_0
/*     */       //   130: aload_3
/*     */       //   131: invokeinterface 18 4 0
/*     */       //   136: ifeq +23 -> 159
/*     */       //   139: aload_0
/*     */       //   140: getfield 1	java/lang/reflect/WeakCache$Factory:this$0	Ljava/lang/reflect/WeakCache;
/*     */       //   143: invokestatic 19	java/lang/reflect/WeakCache:access$100	(Ljava/lang/reflect/WeakCache;)Ljava/util/concurrent/ConcurrentMap;
/*     */       //   146: aload_3
/*     */       //   147: getstatic 20	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
/*     */       //   150: invokeinterface 21 3 0
/*     */       //   155: pop
/*     */       //   156: goto +13 -> 169
/*     */       //   159: new 14	java/lang/AssertionError
/*     */       //   162: dup
/*     */       //   163: ldc 22
/*     */       //   165: invokespecial 23	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
/*     */       //   168: athrow
/*     */       //   169: aload_2
/*     */       //   170: areturn
/*     */       // Line number table:
/*     */       //   Java source line #216	-> byte code offset #0
/*     */       //   Java source line #217	-> byte code offset #17
/*     */       //   Java source line #223	-> byte code offset #22
/*     */       //   Java source line #228	-> byte code offset #24
/*     */       //   Java source line #230	-> byte code offset #26
/*     */       //   Java source line #232	-> byte code offset #50
/*     */       //   Java source line #233	-> byte code offset #54
/*     */       //   Java source line #232	-> byte code offset #72
/*     */       //   Java source line #233	-> byte code offset #77
/*     */       //   Java source line #237	-> byte code offset #94
/*     */       //   Java source line #240	-> byte code offset #112
/*     */       //   Java source line #243	-> byte code offset #121
/*     */       //   Java source line #245	-> byte code offset #139
/*     */       //   Java source line #247	-> byte code offset #159
/*     */       //   Java source line #252	-> byte code offset #169
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	171	0	this	Factory
/*     */       //   16	2	1	localSupplier	Supplier
/*     */       //   25	145	2	localObject1	Object
/*     */       //   72	21	3	localObject2	Object
/*     */       //   120	27	3	localCacheValue	WeakCache.CacheValue
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   26	50	72	finally
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract interface Value<V>
/*     */     extends Supplier<V>
/*     */   {}
/*     */   
/*     */   private static final class LookupValue<V>
/*     */     implements WeakCache.Value<V>
/*     */   {
/*     */     private final V value;
/*     */     
/*     */     LookupValue(V paramV)
/*     */     {
/* 272 */       this.value = paramV;
/*     */     }
/*     */     
/*     */     public V get()
/*     */     {
/* 277 */       return (V)this.value;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 282 */       return System.identityHashCode(this.value);
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 287 */       if (paramObject != this) if (!(paramObject instanceof WeakCache.Value)) break label32;
/*     */       label32:
/* 289 */       return this.value == ((WeakCache.Value)paramObject).get();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class CacheValue<V>
/*     */     extends WeakReference<V>
/*     */     implements WeakCache.Value<V>
/*     */   {
/*     */     private final int hash;
/*     */     
/*     */     CacheValue(V paramV)
/*     */     {
/* 302 */       super();
/* 303 */       this.hash = System.identityHashCode(paramV);
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 308 */       return this.hash;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 314 */       if (paramObject != this) if (!(paramObject instanceof WeakCache.Value))
/*     */           break label38;
/*     */       Object localObject;
/*     */       label38:
/* 318 */       return ((localObject = get()) != null) && (localObject == ((WeakCache.Value)paramObject).get());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class CacheKey<K>
/*     */     extends WeakReference<K>
/*     */   {
/* 330 */     private static final Object NULL_KEY = new Object();
/*     */     private final int hash;
/*     */     
/* 333 */     static <K> Object valueOf(K paramK, ReferenceQueue<K> paramReferenceQueue) { return paramK == null ? NULL_KEY : new CacheKey(paramK, paramReferenceQueue); }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private CacheKey(K paramK, ReferenceQueue<K> paramReferenceQueue)
/*     */     {
/* 344 */       super(paramReferenceQueue);
/* 345 */       this.hash = System.identityHashCode(paramK);
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 350 */       return this.hash;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/*     */       Object localObject;
/* 356 */       if (paramObject != this) { if (paramObject == null)
/*     */           break label44;
/* 358 */         if (paramObject.getClass() != getClass())
/*     */           break label44;
/* 360 */         if ((localObject = get()) == null) break label44; }
/*     */       label44:
/* 362 */       return localObject == ((CacheKey)paramObject).get();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     void expungeFrom(ConcurrentMap<?, ? extends ConcurrentMap<?, ?>> paramConcurrentMap, ConcurrentMap<?, Boolean> paramConcurrentMap1)
/*     */     {
/* 370 */       ConcurrentMap localConcurrentMap = (ConcurrentMap)paramConcurrentMap.remove(this);
/*     */       
/* 372 */       if (localConcurrentMap != null) {
/* 373 */         for (Object localObject : localConcurrentMap.values()) {
/* 374 */           paramConcurrentMap1.remove(localObject);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/WeakCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */