/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayBlockingQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -817911632652898426L;
/*      */   final Object[] items;
/*      */   int takeIndex;
/*      */   int putIndex;
/*      */   int count;
/*      */   final ReentrantLock lock;
/*      */   private final Condition notEmpty;
/*      */   private final Condition notFull;
/*  124 */   transient ArrayBlockingQueue<E>.Itrs itrs = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int dec(int paramInt)
/*      */   {
/*  132 */     return (paramInt == 0 ? this.items.length : paramInt) - 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final E itemAt(int paramInt)
/*      */   {
/*  140 */     return (E)this.items[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void checkNotNull(Object paramObject)
/*      */   {
/*  149 */     if (paramObject == null) {
/*  150 */       throw new NullPointerException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void enqueue(E paramE)
/*      */   {
/*  160 */     Object[] arrayOfObject = this.items;
/*  161 */     arrayOfObject[this.putIndex] = paramE;
/*  162 */     if (++this.putIndex == arrayOfObject.length)
/*  163 */       this.putIndex = 0;
/*  164 */     this.count += 1;
/*  165 */     this.notEmpty.signal();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private E dequeue()
/*      */   {
/*  175 */     Object[] arrayOfObject = this.items;
/*      */     
/*  177 */     Object localObject = arrayOfObject[this.takeIndex];
/*  178 */     arrayOfObject[this.takeIndex] = null;
/*  179 */     if (++this.takeIndex == arrayOfObject.length)
/*  180 */       this.takeIndex = 0;
/*  181 */     this.count -= 1;
/*  182 */     if (this.itrs != null)
/*  183 */       this.itrs.elementDequeued();
/*  184 */     this.notFull.signal();
/*  185 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeAt(int paramInt)
/*      */   {
/*  197 */     Object[] arrayOfObject = this.items;
/*  198 */     if (paramInt == this.takeIndex)
/*      */     {
/*  200 */       arrayOfObject[this.takeIndex] = null;
/*  201 */       if (++this.takeIndex == arrayOfObject.length)
/*  202 */         this.takeIndex = 0;
/*  203 */       this.count -= 1;
/*  204 */       if (this.itrs != null) {
/*  205 */         this.itrs.elementDequeued();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  210 */       int i = this.putIndex;
/*  211 */       int j = paramInt;
/*  212 */       for (;;) { int k = j + 1;
/*  213 */         if (k == arrayOfObject.length)
/*  214 */           k = 0;
/*  215 */         if (k != i) {
/*  216 */           arrayOfObject[j] = arrayOfObject[k];
/*  217 */           j = k;
/*      */         } else {
/*  219 */           arrayOfObject[j] = null;
/*  220 */           this.putIndex = j;
/*  221 */           break;
/*      */         }
/*      */       }
/*  224 */       this.count -= 1;
/*  225 */       if (this.itrs != null)
/*  226 */         this.itrs.removedAt(paramInt);
/*      */     }
/*  228 */     this.notFull.signal();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayBlockingQueue(int paramInt)
/*      */   {
/*  239 */     this(paramInt, false);
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
/*      */   public ArrayBlockingQueue(int paramInt, boolean paramBoolean)
/*      */   {
/*  253 */     if (paramInt <= 0)
/*  254 */       throw new IllegalArgumentException();
/*  255 */     this.items = new Object[paramInt];
/*  256 */     this.lock = new ReentrantLock(paramBoolean);
/*  257 */     this.notEmpty = this.lock.newCondition();
/*  258 */     this.notFull = this.lock.newCondition();
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
/*      */   public ArrayBlockingQueue(int paramInt, boolean paramBoolean, Collection<? extends E> paramCollection)
/*      */   {
/*  279 */     this(paramInt, paramBoolean);
/*      */     
/*  281 */     ReentrantLock localReentrantLock = this.lock;
/*  282 */     localReentrantLock.lock();
/*      */     try {
/*  284 */       int i = 0;
/*      */       try {
/*  286 */         for (Object localObject1 : paramCollection) {
/*  287 */           checkNotNull(localObject1);
/*  288 */           this.items[(i++)] = localObject1;
/*      */         }
/*      */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/*  291 */         throw new IllegalArgumentException();
/*      */       }
/*  293 */       this.count = i;
/*  294 */       this.putIndex = (i == paramInt ? 0 : i);
/*      */     } finally {
/*  296 */       localReentrantLock.unlock();
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
/*      */   public boolean add(E paramE)
/*      */   {
/*  312 */     return super.add(paramE);
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
/*      */   public boolean offer(E paramE)
/*      */   {
/*  325 */     checkNotNull(paramE);
/*  326 */     ReentrantLock localReentrantLock = this.lock;
/*  327 */     localReentrantLock.lock();
/*      */     try { boolean bool;
/*  329 */       if (this.count == this.items.length) {
/*  330 */         return false;
/*      */       }
/*  332 */       enqueue(paramE);
/*  333 */       return true;
/*      */     }
/*      */     finally {
/*  336 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void put(E paramE)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: invokestatic 26	java/util/concurrent/ArrayBlockingQueue:checkNotNull	(Ljava/lang/Object;)V
/*      */     //   4: aload_0
/*      */     //   5: getfield 20	java/util/concurrent/ArrayBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   8: astore_2
/*      */     //   9: aload_2
/*      */     //   10: invokevirtual 31	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*      */     //   13: aload_0
/*      */     //   14: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   17: aload_0
/*      */     //   18: getfield 1	java/util/concurrent/ArrayBlockingQueue:items	[Ljava/lang/Object;
/*      */     //   21: arraylength
/*      */     //   22: if_icmpne +15 -> 37
/*      */     //   25: aload_0
/*      */     //   26: getfield 11	java/util/concurrent/ArrayBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   29: invokeinterface 32 1 0
/*      */     //   34: goto -21 -> 13
/*      */     //   37: aload_0
/*      */     //   38: aload_1
/*      */     //   39: invokespecial 30	java/util/concurrent/ArrayBlockingQueue:enqueue	(Ljava/lang/Object;)V
/*      */     //   42: aload_2
/*      */     //   43: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   46: goto +10 -> 56
/*      */     //   49: astore_3
/*      */     //   50: aload_2
/*      */     //   51: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   54: aload_3
/*      */     //   55: athrow
/*      */     //   56: return
/*      */     // Line number table:
/*      */     //   Java source line #348	-> byte code offset #0
/*      */     //   Java source line #349	-> byte code offset #4
/*      */     //   Java source line #350	-> byte code offset #9
/*      */     //   Java source line #352	-> byte code offset #13
/*      */     //   Java source line #353	-> byte code offset #25
/*      */     //   Java source line #354	-> byte code offset #37
/*      */     //   Java source line #356	-> byte code offset #42
/*      */     //   Java source line #357	-> byte code offset #46
/*      */     //   Java source line #356	-> byte code offset #49
/*      */     //   Java source line #358	-> byte code offset #56
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	57	0	this	ArrayBlockingQueue
/*      */     //   0	57	1	paramE	E
/*      */     //   8	43	2	localReentrantLock	ReentrantLock
/*      */     //   49	6	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   13	42	49	finally
/*      */   }
/*      */   
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  371 */     checkNotNull(paramE);
/*  372 */     long l = paramTimeUnit.toNanos(paramLong);
/*  373 */     ReentrantLock localReentrantLock = this.lock;
/*  374 */     localReentrantLock.lockInterruptibly();
/*      */     try { boolean bool;
/*  376 */       while (this.count == this.items.length) {
/*  377 */         if (l <= 0L)
/*  378 */           return false;
/*  379 */         l = this.notFull.awaitNanos(l);
/*      */       }
/*  381 */       enqueue(paramE);
/*  382 */       return true;
/*      */     } finally {
/*  384 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E poll() {
/*  389 */     ReentrantLock localReentrantLock = this.lock;
/*  390 */     localReentrantLock.lock();
/*      */     try {
/*  392 */       return this.count == 0 ? null : dequeue();
/*      */     } finally {
/*  394 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/*  399 */     ReentrantLock localReentrantLock = this.lock;
/*  400 */     localReentrantLock.lockInterruptibly();
/*      */     try {
/*  402 */       while (this.count == 0)
/*  403 */         this.notEmpty.await();
/*  404 */       return (E)dequeue();
/*      */     } finally {
/*  406 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  411 */     long l = paramTimeUnit.toNanos(paramLong);
/*  412 */     ReentrantLock localReentrantLock = this.lock;
/*  413 */     localReentrantLock.lockInterruptibly();
/*      */     try { Object localObject1;
/*  415 */       while (this.count == 0) {
/*  416 */         if (l <= 0L)
/*  417 */           return null;
/*  418 */         l = this.notEmpty.awaitNanos(l);
/*      */       }
/*  420 */       return (E)dequeue();
/*      */     } finally {
/*  422 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E peek() {
/*  427 */     ReentrantLock localReentrantLock = this.lock;
/*  428 */     localReentrantLock.lock();
/*      */     try {
/*  430 */       return (E)itemAt(this.takeIndex);
/*      */     } finally {
/*  432 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  444 */     ReentrantLock localReentrantLock = this.lock;
/*  445 */     localReentrantLock.lock();
/*      */     try {
/*  447 */       return this.count;
/*      */     } finally {
/*  449 */       localReentrantLock.unlock();
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
/*      */   public int remainingCapacity()
/*      */   {
/*  467 */     ReentrantLock localReentrantLock = this.lock;
/*  468 */     localReentrantLock.lock();
/*      */     try {
/*  470 */       return this.items.length - this.count;
/*      */     } finally {
/*  472 */       localReentrantLock.unlock();
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*  494 */     if (paramObject == null) return false;
/*  495 */     Object[] arrayOfObject = this.items;
/*  496 */     ReentrantLock localReentrantLock = this.lock;
/*  497 */     localReentrantLock.lock();
/*      */     try { int i;
/*  499 */       if (this.count > 0) {
/*  500 */         i = this.putIndex;
/*  501 */         int j = this.takeIndex;
/*      */         do {
/*  503 */           if (paramObject.equals(arrayOfObject[j])) {
/*  504 */             removeAt(j);
/*  505 */             return true;
/*      */           }
/*  507 */           j++; if (j == arrayOfObject.length)
/*  508 */             j = 0;
/*  509 */         } while (j != i);
/*      */       }
/*  511 */       return 0;
/*      */     } finally {
/*  513 */       localReentrantLock.unlock();
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
/*      */   public boolean contains(Object paramObject)
/*      */   {
/*  526 */     if (paramObject == null) return false;
/*  527 */     Object[] arrayOfObject = this.items;
/*  528 */     ReentrantLock localReentrantLock = this.lock;
/*  529 */     localReentrantLock.lock();
/*      */     try { int i;
/*  531 */       if (this.count > 0) {
/*  532 */         i = this.putIndex;
/*  533 */         int j = this.takeIndex;
/*      */         do {
/*  535 */           if (paramObject.equals(arrayOfObject[j]))
/*  536 */             return true;
/*  537 */           j++; if (j == arrayOfObject.length)
/*  538 */             j = 0;
/*  539 */         } while (j != i);
/*      */       }
/*  541 */       return 0;
/*      */     } finally {
/*  543 */       localReentrantLock.unlock();
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
/*      */   public Object[] toArray()
/*      */   {
/*  562 */     ReentrantLock localReentrantLock = this.lock;
/*  563 */     localReentrantLock.lock();
/*      */     Object[] arrayOfObject;
/*  565 */     try { int i = this.count;
/*  566 */       arrayOfObject = new Object[i];
/*  567 */       int j = this.items.length - this.takeIndex;
/*  568 */       if (i <= j) {
/*  569 */         System.arraycopy(this.items, this.takeIndex, arrayOfObject, 0, i);
/*      */       } else {
/*  571 */         System.arraycopy(this.items, this.takeIndex, arrayOfObject, 0, j);
/*  572 */         System.arraycopy(this.items, 0, arrayOfObject, j, i - j);
/*      */       }
/*      */     } finally {
/*  575 */       localReentrantLock.unlock();
/*      */     }
/*  577 */     return arrayOfObject;
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
/*      */ 
/*      */ 
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/*  617 */     Object[] arrayOfObject = this.items;
/*  618 */     ReentrantLock localReentrantLock = this.lock;
/*  619 */     localReentrantLock.lock();
/*      */     try {
/*  621 */       int i = this.count;
/*  622 */       int j = paramArrayOfT.length;
/*  623 */       if (j < i)
/*  624 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT
/*  625 */           .getClass().getComponentType(), i);
/*  626 */       int k = arrayOfObject.length - this.takeIndex;
/*  627 */       if (i <= k) {
/*  628 */         System.arraycopy(arrayOfObject, this.takeIndex, paramArrayOfT, 0, i);
/*      */       } else {
/*  630 */         System.arraycopy(arrayOfObject, this.takeIndex, paramArrayOfT, 0, k);
/*  631 */         System.arraycopy(arrayOfObject, 0, paramArrayOfT, k, i - k);
/*      */       }
/*  633 */       if (j > i)
/*  634 */         paramArrayOfT[i] = null;
/*      */     } finally {
/*  636 */       localReentrantLock.unlock();
/*      */     }
/*  638 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public String toString()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 20	java/util/concurrent/ArrayBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   13: istore_2
/*      */     //   14: iload_2
/*      */     //   15: ifne +12 -> 27
/*      */     //   18: ldc 44
/*      */     //   20: astore_3
/*      */     //   21: aload_1
/*      */     //   22: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   25: aload_3
/*      */     //   26: areturn
/*      */     //   27: aload_0
/*      */     //   28: getfield 1	java/util/concurrent/ArrayBlockingQueue:items	[Ljava/lang/Object;
/*      */     //   31: astore_3
/*      */     //   32: new 45	java/lang/StringBuilder
/*      */     //   35: dup
/*      */     //   36: invokespecial 46	java/lang/StringBuilder:<init>	()V
/*      */     //   39: astore 4
/*      */     //   41: aload 4
/*      */     //   43: bipush 91
/*      */     //   45: invokevirtual 47	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   48: pop
/*      */     //   49: aload_0
/*      */     //   50: getfield 8	java/util/concurrent/ArrayBlockingQueue:takeIndex	I
/*      */     //   53: istore 5
/*      */     //   55: aload_3
/*      */     //   56: iload 5
/*      */     //   58: aaload
/*      */     //   59: astore 6
/*      */     //   61: aload 4
/*      */     //   63: aload 6
/*      */     //   65: aload_0
/*      */     //   66: if_acmpne +8 -> 74
/*      */     //   69: ldc 48
/*      */     //   71: goto +5 -> 76
/*      */     //   74: aload 6
/*      */     //   76: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   79: pop
/*      */     //   80: iinc 2 -1
/*      */     //   83: iload_2
/*      */     //   84: ifne +22 -> 106
/*      */     //   87: aload 4
/*      */     //   89: bipush 93
/*      */     //   91: invokevirtual 47	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   94: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   97: astore 7
/*      */     //   99: aload_1
/*      */     //   100: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   103: aload 7
/*      */     //   105: areturn
/*      */     //   106: aload 4
/*      */     //   108: bipush 44
/*      */     //   110: invokevirtual 47	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   113: bipush 32
/*      */     //   115: invokevirtual 47	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   118: pop
/*      */     //   119: iinc 5 1
/*      */     //   122: iload 5
/*      */     //   124: aload_3
/*      */     //   125: arraylength
/*      */     //   126: if_icmpne +6 -> 132
/*      */     //   129: iconst_0
/*      */     //   130: istore 5
/*      */     //   132: goto -77 -> 55
/*      */     //   135: astore 8
/*      */     //   137: aload_1
/*      */     //   138: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   141: aload 8
/*      */     //   143: athrow
/*      */     // Line number table:
/*      */     //   Java source line #642	-> byte code offset #0
/*      */     //   Java source line #643	-> byte code offset #5
/*      */     //   Java source line #645	-> byte code offset #9
/*      */     //   Java source line #646	-> byte code offset #14
/*      */     //   Java source line #647	-> byte code offset #18
/*      */     //   Java source line #662	-> byte code offset #21
/*      */     //   Java source line #649	-> byte code offset #27
/*      */     //   Java source line #650	-> byte code offset #32
/*      */     //   Java source line #651	-> byte code offset #41
/*      */     //   Java source line #652	-> byte code offset #49
/*      */     //   Java source line #653	-> byte code offset #55
/*      */     //   Java source line #654	-> byte code offset #61
/*      */     //   Java source line #655	-> byte code offset #80
/*      */     //   Java source line #656	-> byte code offset #87
/*      */     //   Java source line #662	-> byte code offset #99
/*      */     //   Java source line #657	-> byte code offset #106
/*      */     //   Java source line #658	-> byte code offset #119
/*      */     //   Java source line #659	-> byte code offset #129
/*      */     //   Java source line #660	-> byte code offset #132
/*      */     //   Java source line #662	-> byte code offset #135
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	144	0	this	ArrayBlockingQueue
/*      */     //   4	134	1	localReentrantLock	ReentrantLock
/*      */     //   13	71	2	i	int
/*      */     //   20	105	3	localObject1	Object
/*      */     //   39	68	4	localStringBuilder	StringBuilder
/*      */     //   53	78	5	j	int
/*      */     //   59	16	6	localObject2	Object
/*      */     //   97	7	7	str	String
/*      */     //   135	7	8	localObject3	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	21	135	finally
/*      */     //   27	99	135	finally
/*      */     //   106	137	135	finally
/*      */   }
/*      */   
/*      */   public void clear()
/*      */   {
/*  671 */     Object[] arrayOfObject = this.items;
/*  672 */     ReentrantLock localReentrantLock = this.lock;
/*  673 */     localReentrantLock.lock();
/*      */     try {
/*  675 */       int i = this.count;
/*  676 */       if (i > 0) {
/*  677 */         int j = this.putIndex;
/*  678 */         int k = this.takeIndex;
/*      */         do {
/*  680 */           arrayOfObject[k] = null;
/*  681 */           k++; if (k == arrayOfObject.length)
/*  682 */             k = 0;
/*  683 */         } while (k != j);
/*  684 */         this.takeIndex = j;
/*  685 */         this.count = 0;
/*  686 */         if (this.itrs != null)
/*  687 */           this.itrs.queueIsEmpty();
/*  688 */         for (; (i > 0) && (localReentrantLock.hasWaiters(this.notFull)); i--)
/*  689 */           this.notFull.signal();
/*      */       }
/*      */     } finally {
/*  692 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection)
/*      */   {
/*  703 */     return drainTo(paramCollection, Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: invokestatic 26	java/util/concurrent/ArrayBlockingQueue:checkNotNull	(Ljava/lang/Object;)V
/*      */     //   4: aload_1
/*      */     //   5: aload_0
/*      */     //   6: if_acmpne +11 -> 17
/*      */     //   9: new 15	java/lang/IllegalArgumentException
/*      */     //   12: dup
/*      */     //   13: invokespecial 16	java/lang/IllegalArgumentException:<init>	()V
/*      */     //   16: athrow
/*      */     //   17: iload_2
/*      */     //   18: ifgt +5 -> 23
/*      */     //   21: iconst_0
/*      */     //   22: ireturn
/*      */     //   23: aload_0
/*      */     //   24: getfield 1	java/util/concurrent/ArrayBlockingQueue:items	[Ljava/lang/Object;
/*      */     //   27: astore_3
/*      */     //   28: aload_0
/*      */     //   29: getfield 20	java/util/concurrent/ArrayBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   32: astore 4
/*      */     //   34: aload 4
/*      */     //   36: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   39: iload_2
/*      */     //   40: aload_0
/*      */     //   41: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   44: invokestatic 56	java/lang/Math:min	(II)I
/*      */     //   47: istore 5
/*      */     //   49: aload_0
/*      */     //   50: getfield 8	java/util/concurrent/ArrayBlockingQueue:takeIndex	I
/*      */     //   53: istore 6
/*      */     //   55: iconst_0
/*      */     //   56: istore 7
/*      */     //   58: iload 7
/*      */     //   60: iload 5
/*      */     //   62: if_icmpge +42 -> 104
/*      */     //   65: aload_3
/*      */     //   66: iload 6
/*      */     //   68: aaload
/*      */     //   69: astore 8
/*      */     //   71: aload_1
/*      */     //   72: aload 8
/*      */     //   74: invokeinterface 57 2 0
/*      */     //   79: pop
/*      */     //   80: aload_3
/*      */     //   81: iload 6
/*      */     //   83: aconst_null
/*      */     //   84: aastore
/*      */     //   85: iinc 6 1
/*      */     //   88: iload 6
/*      */     //   90: aload_3
/*      */     //   91: arraylength
/*      */     //   92: if_icmpne +6 -> 98
/*      */     //   95: iconst_0
/*      */     //   96: istore 6
/*      */     //   98: iinc 7 1
/*      */     //   101: goto -43 -> 58
/*      */     //   104: iload 5
/*      */     //   106: istore 8
/*      */     //   108: iload 7
/*      */     //   110: ifle +90 -> 200
/*      */     //   113: aload_0
/*      */     //   114: dup
/*      */     //   115: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   118: iload 7
/*      */     //   120: isub
/*      */     //   121: putfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   124: aload_0
/*      */     //   125: iload 6
/*      */     //   127: putfield 8	java/util/concurrent/ArrayBlockingQueue:takeIndex	I
/*      */     //   130: aload_0
/*      */     //   131: getfield 9	java/util/concurrent/ArrayBlockingQueue:itrs	Ljava/util/concurrent/ArrayBlockingQueue$Itrs;
/*      */     //   134: ifnull +34 -> 168
/*      */     //   137: aload_0
/*      */     //   138: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   141: ifne +13 -> 154
/*      */     //   144: aload_0
/*      */     //   145: getfield 9	java/util/concurrent/ArrayBlockingQueue:itrs	Ljava/util/concurrent/ArrayBlockingQueue$Itrs;
/*      */     //   148: invokevirtual 51	java/util/concurrent/ArrayBlockingQueue$Itrs:queueIsEmpty	()V
/*      */     //   151: goto +17 -> 168
/*      */     //   154: iload 7
/*      */     //   156: iload 6
/*      */     //   158: if_icmple +10 -> 168
/*      */     //   161: aload_0
/*      */     //   162: getfield 9	java/util/concurrent/ArrayBlockingQueue:itrs	Ljava/util/concurrent/ArrayBlockingQueue$Itrs;
/*      */     //   165: invokevirtual 58	java/util/concurrent/ArrayBlockingQueue$Itrs:takeIndexWrapped	()V
/*      */     //   168: iload 7
/*      */     //   170: ifle +30 -> 200
/*      */     //   173: aload 4
/*      */     //   175: aload_0
/*      */     //   176: getfield 11	java/util/concurrent/ArrayBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   179: invokevirtual 52	java/util/concurrent/locks/ReentrantLock:hasWaiters	(Ljava/util/concurrent/locks/Condition;)Z
/*      */     //   182: ifeq +18 -> 200
/*      */     //   185: aload_0
/*      */     //   186: getfield 11	java/util/concurrent/ArrayBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   189: invokeinterface 7 1 0
/*      */     //   194: iinc 7 -1
/*      */     //   197: goto -29 -> 168
/*      */     //   200: aload 4
/*      */     //   202: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   205: iload 8
/*      */     //   207: ireturn
/*      */     //   208: astore 9
/*      */     //   210: iload 7
/*      */     //   212: ifle +90 -> 302
/*      */     //   215: aload_0
/*      */     //   216: dup
/*      */     //   217: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   220: iload 7
/*      */     //   222: isub
/*      */     //   223: putfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   226: aload_0
/*      */     //   227: iload 6
/*      */     //   229: putfield 8	java/util/concurrent/ArrayBlockingQueue:takeIndex	I
/*      */     //   232: aload_0
/*      */     //   233: getfield 9	java/util/concurrent/ArrayBlockingQueue:itrs	Ljava/util/concurrent/ArrayBlockingQueue$Itrs;
/*      */     //   236: ifnull +34 -> 270
/*      */     //   239: aload_0
/*      */     //   240: getfield 5	java/util/concurrent/ArrayBlockingQueue:count	I
/*      */     //   243: ifne +13 -> 256
/*      */     //   246: aload_0
/*      */     //   247: getfield 9	java/util/concurrent/ArrayBlockingQueue:itrs	Ljava/util/concurrent/ArrayBlockingQueue$Itrs;
/*      */     //   250: invokevirtual 51	java/util/concurrent/ArrayBlockingQueue$Itrs:queueIsEmpty	()V
/*      */     //   253: goto +17 -> 270
/*      */     //   256: iload 7
/*      */     //   258: iload 6
/*      */     //   260: if_icmple +10 -> 270
/*      */     //   263: aload_0
/*      */     //   264: getfield 9	java/util/concurrent/ArrayBlockingQueue:itrs	Ljava/util/concurrent/ArrayBlockingQueue$Itrs;
/*      */     //   267: invokevirtual 58	java/util/concurrent/ArrayBlockingQueue$Itrs:takeIndexWrapped	()V
/*      */     //   270: iload 7
/*      */     //   272: ifle +30 -> 302
/*      */     //   275: aload 4
/*      */     //   277: aload_0
/*      */     //   278: getfield 11	java/util/concurrent/ArrayBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   281: invokevirtual 52	java/util/concurrent/locks/ReentrantLock:hasWaiters	(Ljava/util/concurrent/locks/Condition;)Z
/*      */     //   284: ifeq +18 -> 302
/*      */     //   287: aload_0
/*      */     //   288: getfield 11	java/util/concurrent/ArrayBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   291: invokeinterface 7 1 0
/*      */     //   296: iinc 7 -1
/*      */     //   299: goto -29 -> 270
/*      */     //   302: aload 9
/*      */     //   304: athrow
/*      */     //   305: astore 10
/*      */     //   307: aload 4
/*      */     //   309: invokevirtual 28	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   312: aload 10
/*      */     //   314: athrow
/*      */     // Line number table:
/*      */     //   Java source line #713	-> byte code offset #0
/*      */     //   Java source line #714	-> byte code offset #4
/*      */     //   Java source line #715	-> byte code offset #9
/*      */     //   Java source line #716	-> byte code offset #17
/*      */     //   Java source line #717	-> byte code offset #21
/*      */     //   Java source line #718	-> byte code offset #23
/*      */     //   Java source line #719	-> byte code offset #28
/*      */     //   Java source line #720	-> byte code offset #34
/*      */     //   Java source line #722	-> byte code offset #39
/*      */     //   Java source line #723	-> byte code offset #49
/*      */     //   Java source line #724	-> byte code offset #55
/*      */     //   Java source line #726	-> byte code offset #58
/*      */     //   Java source line #728	-> byte code offset #65
/*      */     //   Java source line #729	-> byte code offset #71
/*      */     //   Java source line #730	-> byte code offset #80
/*      */     //   Java source line #731	-> byte code offset #85
/*      */     //   Java source line #732	-> byte code offset #95
/*      */     //   Java source line #733	-> byte code offset #98
/*      */     //   Java source line #734	-> byte code offset #101
/*      */     //   Java source line #735	-> byte code offset #104
/*      */     //   Java source line #738	-> byte code offset #108
/*      */     //   Java source line #739	-> byte code offset #113
/*      */     //   Java source line #740	-> byte code offset #124
/*      */     //   Java source line #741	-> byte code offset #130
/*      */     //   Java source line #742	-> byte code offset #137
/*      */     //   Java source line #743	-> byte code offset #144
/*      */     //   Java source line #744	-> byte code offset #154
/*      */     //   Java source line #745	-> byte code offset #161
/*      */     //   Java source line #747	-> byte code offset #168
/*      */     //   Java source line #748	-> byte code offset #185
/*      */     //   Java source line #747	-> byte code offset #194
/*      */     //   Java source line #752	-> byte code offset #200
/*      */     //   Java source line #738	-> byte code offset #208
/*      */     //   Java source line #739	-> byte code offset #215
/*      */     //   Java source line #740	-> byte code offset #226
/*      */     //   Java source line #741	-> byte code offset #232
/*      */     //   Java source line #742	-> byte code offset #239
/*      */     //   Java source line #743	-> byte code offset #246
/*      */     //   Java source line #744	-> byte code offset #256
/*      */     //   Java source line #745	-> byte code offset #263
/*      */     //   Java source line #747	-> byte code offset #270
/*      */     //   Java source line #748	-> byte code offset #287
/*      */     //   Java source line #747	-> byte code offset #296
/*      */     //   Java source line #752	-> byte code offset #305
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	315	0	this	ArrayBlockingQueue
/*      */     //   0	315	1	paramCollection	Collection<? super E>
/*      */     //   0	315	2	paramInt	int
/*      */     //   27	64	3	arrayOfObject	Object[]
/*      */     //   32	276	4	localReentrantLock	ReentrantLock
/*      */     //   47	58	5	localObject1	Object
/*      */     //   53	208	6	i	int
/*      */     //   56	241	7	j	int
/*      */     //   69	137	8	localObject2	Object
/*      */     //   208	95	9	localObject3	Object
/*      */     //   305	8	10	localObject4	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   58	108	208	finally
/*      */     //   208	210	208	finally
/*      */     //   39	200	305	finally
/*      */     //   208	307	305	finally
/*      */   }
/*      */   
/*      */   public Iterator<E> iterator()
/*      */   {
/*  766 */     return new Itr();
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
/*      */   class Itrs
/*      */   {
/*      */     private class Node
/*      */       extends WeakReference<ArrayBlockingQueue<E>.Itr>
/*      */     {
/*      */       ArrayBlockingQueue<E>.Itrs.Node next;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       Node(ArrayBlockingQueue<E>.Itrs.Node paramArrayBlockingQueue)
/*      */       {
/*  829 */         super();
/*  830 */         Node localNode; this.next = localNode;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  835 */     int cycles = 0;
/*      */     
/*      */ 
/*      */     private ArrayBlockingQueue<E>.Itrs.Node head;
/*      */     
/*      */ 
/*  841 */     private ArrayBlockingQueue<E>.Itrs.Node sweeper = null;
/*      */     private static final int SHORT_SWEEP_PROBES = 4;
/*      */     private static final int LONG_SWEEP_PROBES = 16;
/*      */     
/*      */     Itrs() {
/*      */       ArrayBlockingQueue.Itr localItr;
/*  847 */       register(localItr);
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
/*      */     void doSomeSweeping(boolean paramBoolean)
/*      */     {
/*  861 */       int i = paramBoolean ? 16 : 4;
/*      */       
/*  863 */       Node localNode1 = this.sweeper;
/*      */       Object localObject1;
/*      */       Object localObject2;
/*  866 */       int j; if (localNode1 == null) {
/*  867 */         localObject1 = null;
/*  868 */         localObject2 = this.head;
/*  869 */         j = 1;
/*      */       } else {
/*  871 */         localObject1 = localNode1;
/*  872 */         localObject2 = ((Node)localObject1).next;
/*  873 */         j = 0;
/*      */       }
/*  876 */       for (; 
/*  876 */           i > 0; i--) {
/*  877 */         if (localObject2 == null) {
/*  878 */           if (j != 0)
/*      */             break;
/*  880 */           localObject1 = null;
/*  881 */           localObject2 = this.head;
/*  882 */           j = 1;
/*      */         }
/*  884 */         ArrayBlockingQueue.Itr localItr = (ArrayBlockingQueue.Itr)((Node)localObject2).get();
/*  885 */         Node localNode2 = ((Node)localObject2).next;
/*  886 */         if ((localItr == null) || (localItr.isDetached()))
/*      */         {
/*  888 */           i = 16;
/*      */           
/*  890 */           ((Node)localObject2).clear();
/*  891 */           ((Node)localObject2).next = null;
/*  892 */           if (localObject1 == null) {
/*  893 */             this.head = localNode2;
/*  894 */             if (localNode2 == null)
/*      */             {
/*  896 */               ArrayBlockingQueue.this.itrs = null;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  901 */             ((Node)localObject1).next = localNode2;
/*      */           }
/*  903 */         } else { localObject1 = localObject2;
/*      */         }
/*  905 */         localObject2 = localNode2;
/*      */       }
/*      */       
/*  908 */       this.sweeper = (localObject2 == null ? null : (Node)localObject1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     void register(ArrayBlockingQueue<E>.Itr paramArrayBlockingQueue)
/*      */     {
/*  916 */       this.head = new Node(paramArrayBlockingQueue, this.head);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void takeIndexWrapped()
/*      */     {
/*  926 */       this.cycles += 1;
/*  927 */       Object localObject1 = null; for (Object localObject2 = this.head; localObject2 != null;) {
/*  928 */         ArrayBlockingQueue.Itr localItr = (ArrayBlockingQueue.Itr)((Node)localObject2).get();
/*  929 */         Node localNode = ((Node)localObject2).next;
/*  930 */         if ((localItr == null) || (localItr.takeIndexWrapped()))
/*      */         {
/*      */ 
/*  933 */           ((Node)localObject2).clear();
/*  934 */           ((Node)localObject2).next = null;
/*  935 */           if (localObject1 == null) {
/*  936 */             this.head = localNode;
/*      */           } else
/*  938 */             ((Node)localObject1).next = localNode;
/*      */         } else {
/*  940 */           localObject1 = localObject2;
/*      */         }
/*  942 */         localObject2 = localNode;
/*      */       }
/*  944 */       if (this.head == null) {
/*  945 */         ArrayBlockingQueue.this.itrs = null;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     void removedAt(int paramInt)
/*      */     {
/*  954 */       Object localObject1 = null; for (Object localObject2 = this.head; localObject2 != null;) {
/*  955 */         ArrayBlockingQueue.Itr localItr = (ArrayBlockingQueue.Itr)((Node)localObject2).get();
/*  956 */         Node localNode = ((Node)localObject2).next;
/*  957 */         if ((localItr == null) || (localItr.removedAt(paramInt)))
/*      */         {
/*      */ 
/*  960 */           ((Node)localObject2).clear();
/*  961 */           ((Node)localObject2).next = null;
/*  962 */           if (localObject1 == null) {
/*  963 */             this.head = localNode;
/*      */           } else
/*  965 */             ((Node)localObject1).next = localNode;
/*      */         } else {
/*  967 */           localObject1 = localObject2;
/*      */         }
/*  969 */         localObject2 = localNode;
/*      */       }
/*  971 */       if (this.head == null) {
/*  972 */         ArrayBlockingQueue.this.itrs = null;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void queueIsEmpty()
/*      */     {
/*  983 */       for (Node localNode = this.head; localNode != null; localNode = localNode.next) {
/*  984 */         ArrayBlockingQueue.Itr localItr = (ArrayBlockingQueue.Itr)localNode.get();
/*  985 */         if (localItr != null) {
/*  986 */           localNode.clear();
/*  987 */           localItr.shutdown();
/*      */         }
/*      */       }
/*  990 */       this.head = null;
/*  991 */       ArrayBlockingQueue.this.itrs = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     void elementDequeued()
/*      */     {
/*  999 */       if (ArrayBlockingQueue.this.count == 0) {
/* 1000 */         queueIsEmpty();
/* 1001 */       } else if (ArrayBlockingQueue.this.takeIndex == 0) {
/* 1002 */         takeIndexWrapped();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private int cursor;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private E nextItem;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private int nextIndex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private E lastItem;
/*      */     
/*      */ 
/*      */ 
/*      */     private int lastRet;
/*      */     
/*      */ 
/*      */ 
/*      */     private int prevTakeIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     private int prevCycles;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int NONE = -1;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int REMOVED = -2;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int DETACHED = -3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Itr()
/*      */     {
/* 1060 */       this.lastRet = -1;
/* 1061 */       ReentrantLock localReentrantLock = ArrayBlockingQueue.this.lock;
/* 1062 */       localReentrantLock.lock();
/*      */       try {
/* 1064 */         if (ArrayBlockingQueue.this.count == 0)
/*      */         {
/* 1066 */           this.cursor = -1;
/* 1067 */           this.nextIndex = -1;
/* 1068 */           this.prevTakeIndex = -3;
/*      */         } else {
/* 1070 */           int i = ArrayBlockingQueue.this.takeIndex;
/* 1071 */           this.prevTakeIndex = i;
/* 1072 */           this.nextItem = ArrayBlockingQueue.this.itemAt(this.nextIndex = i);
/* 1073 */           this.cursor = incCursor(i);
/* 1074 */           if (ArrayBlockingQueue.this.itrs == null) {
/* 1075 */             ArrayBlockingQueue.this.itrs = new ArrayBlockingQueue.Itrs(ArrayBlockingQueue.this, this);
/*      */           } else {
/* 1077 */             ArrayBlockingQueue.this.itrs.register(this);
/* 1078 */             ArrayBlockingQueue.this.itrs.doSomeSweeping(false);
/*      */           }
/* 1080 */           this.prevCycles = ArrayBlockingQueue.this.itrs.cycles;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 1087 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */     
/*      */     boolean isDetached()
/*      */     {
/* 1093 */       return this.prevTakeIndex < 0;
/*      */     }
/*      */     
/*      */     private int incCursor(int paramInt) {
/*      */       
/* 1098 */       if (paramInt == ArrayBlockingQueue.this.items.length)
/* 1099 */         paramInt = 0;
/* 1100 */       if (paramInt == ArrayBlockingQueue.this.putIndex)
/* 1101 */         paramInt = -1;
/* 1102 */       return paramInt;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private boolean invalidated(int paramInt1, int paramInt2, long paramLong, int paramInt3)
/*      */     {
/* 1111 */       if (paramInt1 < 0)
/* 1112 */         return false;
/* 1113 */       int i = paramInt1 - paramInt2;
/* 1114 */       if (i < 0)
/* 1115 */         i += paramInt3;
/* 1116 */       return paramLong > i;
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
/*      */     private void incorporateDequeues()
/*      */     {
/* 1129 */       int i = ArrayBlockingQueue.this.itrs.cycles;
/* 1130 */       int j = ArrayBlockingQueue.this.takeIndex;
/* 1131 */       int k = this.prevCycles;
/* 1132 */       int m = this.prevTakeIndex;
/*      */       
/* 1134 */       if ((i != k) || (j != m)) {
/* 1135 */         int n = ArrayBlockingQueue.this.items.length;
/*      */         
/*      */ 
/* 1138 */         long l = (i - k) * n + (j - m);
/*      */         
/*      */ 
/*      */ 
/* 1142 */         if (invalidated(this.lastRet, m, l, n))
/* 1143 */           this.lastRet = -2;
/* 1144 */         if (invalidated(this.nextIndex, m, l, n))
/* 1145 */           this.nextIndex = -2;
/* 1146 */         if (invalidated(this.cursor, m, l, n)) {
/* 1147 */           this.cursor = j;
/*      */         }
/* 1149 */         if ((this.cursor < 0) && (this.nextIndex < 0) && (this.lastRet < 0)) {
/* 1150 */           detach();
/*      */         } else {
/* 1152 */           this.prevCycles = i;
/* 1153 */           this.prevTakeIndex = j;
/*      */         }
/*      */       }
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
/*      */ 
/*      */ 
/*      */     private void detach()
/*      */     {
/* 1172 */       if (this.prevTakeIndex >= 0)
/*      */       {
/* 1174 */         this.prevTakeIndex = -3;
/*      */         
/* 1176 */         ArrayBlockingQueue.this.itrs.doSomeSweeping(true);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean hasNext()
/*      */     {
/* 1188 */       if (this.nextItem != null)
/* 1189 */         return true;
/* 1190 */       noNext();
/* 1191 */       return false;
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     private void noNext()
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield 1	java/util/concurrent/ArrayBlockingQueue$Itr:this$0	Ljava/util/concurrent/ArrayBlockingQueue;
/*      */       //   4: getfield 5	java/util/concurrent/ArrayBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   7: astore_1
/*      */       //   8: aload_1
/*      */       //   9: invokevirtual 6	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */       //   12: aload_0
/*      */       //   13: invokevirtual 28	java/util/concurrent/ArrayBlockingQueue$Itr:isDetached	()Z
/*      */       //   16: ifne +33 -> 49
/*      */       //   19: aload_0
/*      */       //   20: invokespecial 29	java/util/concurrent/ArrayBlockingQueue$Itr:incorporateDequeues	()V
/*      */       //   23: aload_0
/*      */       //   24: getfield 4	java/util/concurrent/ArrayBlockingQueue$Itr:lastRet	I
/*      */       //   27: iflt +22 -> 49
/*      */       //   30: aload_0
/*      */       //   31: aload_0
/*      */       //   32: getfield 1	java/util/concurrent/ArrayBlockingQueue$Itr:this$0	Ljava/util/concurrent/ArrayBlockingQueue;
/*      */       //   35: aload_0
/*      */       //   36: getfield 4	java/util/concurrent/ArrayBlockingQueue$Itr:lastRet	I
/*      */       //   39: invokevirtual 12	java/util/concurrent/ArrayBlockingQueue:itemAt	(I)Ljava/lang/Object;
/*      */       //   42: putfield 30	java/util/concurrent/ArrayBlockingQueue$Itr:lastItem	Ljava/lang/Object;
/*      */       //   45: aload_0
/*      */       //   46: invokespecial 26	java/util/concurrent/ArrayBlockingQueue$Itr:detach	()V
/*      */       //   49: aload_1
/*      */       //   50: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   53: goto +10 -> 63
/*      */       //   56: astore_2
/*      */       //   57: aload_1
/*      */       //   58: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   61: aload_2
/*      */       //   62: athrow
/*      */       //   63: return
/*      */       // Line number table:
/*      */       //   Java source line #1195	-> byte code offset #0
/*      */       //   Java source line #1196	-> byte code offset #8
/*      */       //   Java source line #1200	-> byte code offset #12
/*      */       //   Java source line #1202	-> byte code offset #19
/*      */       //   Java source line #1203	-> byte code offset #23
/*      */       //   Java source line #1204	-> byte code offset #30
/*      */       //   Java source line #1206	-> byte code offset #45
/*      */       //   Java source line #1212	-> byte code offset #49
/*      */       //   Java source line #1213	-> byte code offset #53
/*      */       //   Java source line #1212	-> byte code offset #56
/*      */       //   Java source line #1214	-> byte code offset #63
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	64	0	this	Itr
/*      */       //   7	51	1	localReentrantLock	ReentrantLock
/*      */       //   56	6	2	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   12	49	56	finally
/*      */     }
/*      */     
/*      */     public E next()
/*      */     {
/* 1218 */       Object localObject1 = this.nextItem;
/* 1219 */       if (localObject1 == null)
/* 1220 */         throw new NoSuchElementException();
/* 1221 */       ReentrantLock localReentrantLock = ArrayBlockingQueue.this.lock;
/* 1222 */       localReentrantLock.lock();
/*      */       try {
/* 1224 */         if (!isDetached()) {
/* 1225 */           incorporateDequeues();
/*      */         }
/*      */         
/* 1228 */         this.lastRet = this.nextIndex;
/* 1229 */         int i = this.cursor;
/* 1230 */         if (i >= 0) {
/* 1231 */           this.nextItem = ArrayBlockingQueue.this.itemAt(this.nextIndex = i);
/*      */           
/* 1233 */           this.cursor = incCursor(i);
/*      */         } else {
/* 1235 */           this.nextIndex = -1;
/* 1236 */           this.nextItem = null;
/*      */         }
/*      */       } finally {
/* 1239 */         localReentrantLock.unlock();
/*      */       }
/* 1241 */       return (E)localObject1;
/*      */     }
/*      */     
/*      */     public void remove()
/*      */     {
/* 1246 */       ReentrantLock localReentrantLock = ArrayBlockingQueue.this.lock;
/* 1247 */       localReentrantLock.lock();
/*      */       try {
/* 1249 */         if (!isDetached())
/* 1250 */           incorporateDequeues();
/* 1251 */         int i = this.lastRet;
/* 1252 */         this.lastRet = -1;
/* 1253 */         if (i >= 0) {
/* 1254 */           if (!isDetached()) {
/* 1255 */             ArrayBlockingQueue.this.removeAt(i);
/*      */           } else {
/* 1257 */             Object localObject1 = this.lastItem;
/*      */             
/* 1259 */             this.lastItem = null;
/* 1260 */             if (ArrayBlockingQueue.this.itemAt(i) == localObject1)
/* 1261 */               ArrayBlockingQueue.this.removeAt(i);
/*      */           }
/* 1263 */         } else if (i == -1) {
/* 1264 */           throw new IllegalStateException();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1269 */         if ((this.cursor < 0) && (this.nextIndex < 0))
/* 1270 */           detach();
/*      */       } finally {
/* 1272 */         localReentrantLock.unlock();
/*      */       }
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
/*      */     void shutdown()
/*      */     {
/* 1286 */       this.cursor = -1;
/* 1287 */       if (this.nextIndex >= 0)
/* 1288 */         this.nextIndex = -2;
/* 1289 */       if (this.lastRet >= 0) {
/* 1290 */         this.lastRet = -2;
/* 1291 */         this.lastItem = null;
/*      */       }
/* 1293 */       this.prevTakeIndex = -3;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private int distance(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1301 */       int i = paramInt1 - paramInt2;
/* 1302 */       if (i < 0)
/* 1303 */         i += paramInt3;
/* 1304 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean removedAt(int paramInt)
/*      */     {
/* 1314 */       if (isDetached()) {
/* 1315 */         return true;
/*      */       }
/* 1317 */       int i = ArrayBlockingQueue.this.itrs.cycles;
/* 1318 */       int j = ArrayBlockingQueue.this.takeIndex;
/* 1319 */       int k = this.prevCycles;
/* 1320 */       int m = this.prevTakeIndex;
/* 1321 */       int n = ArrayBlockingQueue.this.items.length;
/* 1322 */       int i1 = i - k;
/* 1323 */       if (paramInt < j)
/* 1324 */         i1++;
/* 1325 */       int i2 = i1 * n + (paramInt - m);
/*      */       
/*      */ 
/* 1328 */       int i3 = this.cursor;
/* 1329 */       if (i3 >= 0) {
/* 1330 */         i4 = distance(i3, m, n);
/* 1331 */         if (i4 == i2) {
/* 1332 */           if (i3 == ArrayBlockingQueue.this.putIndex) {
/* 1333 */             this.cursor = (i3 = -1);
/*      */           }
/* 1335 */         } else if (i4 > i2)
/*      */         {
/* 1337 */           this.cursor = (i3 = ArrayBlockingQueue.this.dec(i3));
/*      */         }
/*      */       }
/* 1340 */       int i4 = this.lastRet;
/* 1341 */       if (i4 >= 0) {
/* 1342 */         i5 = distance(i4, m, n);
/* 1343 */         if (i5 == i2) {
/* 1344 */           this.lastRet = (i4 = -2);
/* 1345 */         } else if (i5 > i2)
/* 1346 */           this.lastRet = (i4 = ArrayBlockingQueue.this.dec(i4));
/*      */       }
/* 1348 */       int i5 = this.nextIndex;
/* 1349 */       if (i5 >= 0) {
/* 1350 */         int i6 = distance(i5, m, n);
/* 1351 */         if (i6 == i2) {
/* 1352 */           this.nextIndex = (i5 = -2);
/* 1353 */         } else if (i6 > i2) {
/* 1354 */           this.nextIndex = (i5 = ArrayBlockingQueue.this.dec(i5));
/*      */         }
/* 1356 */       } else if ((i3 < 0) && (i5 < 0) && (i4 < 0)) {
/* 1357 */         this.prevTakeIndex = -3;
/* 1358 */         return true;
/*      */       }
/* 1360 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean takeIndexWrapped()
/*      */     {
/* 1370 */       if (isDetached())
/* 1371 */         return true;
/* 1372 */       if (ArrayBlockingQueue.this.itrs.cycles - this.prevCycles > 1)
/*      */       {
/*      */ 
/* 1375 */         shutdown();
/* 1376 */         return true;
/*      */       }
/* 1378 */       return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1413 */     return Spliterators.spliterator(this, 4368);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ArrayBlockingQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */