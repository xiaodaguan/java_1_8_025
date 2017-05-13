/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.PriorityQueue;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
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
/*      */ public class PriorityBlockingQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 5595510919245408276L;
/*      */   private static final int DEFAULT_INITIAL_CAPACITY = 11;
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   private transient Object[] queue;
/*      */   private transient int size;
/*      */   private transient Comparator<? super E> comparator;
/*      */   private final ReentrantLock lock;
/*      */   private final Condition notEmpty;
/*      */   private volatile transient int allocationSpinLock;
/*      */   private PriorityQueue<E> q;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long allocationSpinLockOffset;
/*      */   
/*      */   public PriorityBlockingQueue()
/*      */   {
/*  191 */     this(11, null);
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
/*      */   public PriorityBlockingQueue(int paramInt)
/*      */   {
/*  204 */     this(paramInt, null);
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
/*      */   public PriorityBlockingQueue(int paramInt, Comparator<? super E> paramComparator)
/*      */   {
/*  221 */     if (paramInt < 1)
/*  222 */       throw new IllegalArgumentException();
/*  223 */     this.lock = new ReentrantLock();
/*  224 */     this.notEmpty = this.lock.newCondition();
/*  225 */     this.comparator = paramComparator;
/*  226 */     this.queue = new Object[paramInt];
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
/*      */   public PriorityBlockingQueue(Collection<? extends E> paramCollection)
/*      */   {
/*  246 */     this.lock = new ReentrantLock();
/*  247 */     this.notEmpty = this.lock.newCondition();
/*  248 */     int i = 1;
/*  249 */     int j = 1;
/*  250 */     if ((paramCollection instanceof SortedSet)) {
/*  251 */       localObject = (SortedSet)paramCollection;
/*  252 */       this.comparator = ((SortedSet)localObject).comparator();
/*  253 */       i = 0;
/*      */     }
/*  255 */     else if ((paramCollection instanceof PriorityBlockingQueue)) {
/*  256 */       localObject = (PriorityBlockingQueue)paramCollection;
/*      */       
/*  258 */       this.comparator = ((PriorityBlockingQueue)localObject).comparator();
/*  259 */       j = 0;
/*  260 */       if (localObject.getClass() == PriorityBlockingQueue.class)
/*  261 */         i = 0;
/*      */     }
/*  263 */     Object localObject = paramCollection.toArray();
/*  264 */     int k = localObject.length;
/*      */     
/*  266 */     if (localObject.getClass() != Object[].class)
/*  267 */       localObject = Arrays.copyOf((Object[])localObject, k, Object[].class);
/*  268 */     if ((j != 0) && ((k == 1) || (this.comparator != null))) {
/*  269 */       for (int m = 0; m < k; m++)
/*  270 */         if (localObject[m] == null)
/*  271 */           throw new NullPointerException();
/*      */     }
/*  273 */     this.queue = ((Object[])localObject);
/*  274 */     this.size = k;
/*  275 */     if (i != 0) {
/*  276 */       heapify();
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
/*      */   private void tryGrow(Object[] paramArrayOfObject, int paramInt)
/*      */   {
/*  289 */     this.lock.unlock();
/*  290 */     Object[] arrayOfObject = null;
/*  291 */     if ((this.allocationSpinLock == 0) && 
/*  292 */       (UNSAFE.compareAndSwapInt(this, allocationSpinLockOffset, 0, 1))) {
/*      */       try
/*      */       {
/*  295 */         int i = paramInt + (paramInt < 64 ? paramInt + 2 : paramInt >> 1);
/*      */         
/*      */ 
/*  298 */         if (i - 2147483639 > 0) {
/*  299 */           int j = paramInt + 1;
/*  300 */           if ((j < 0) || (j > 2147483639))
/*  301 */             throw new OutOfMemoryError();
/*  302 */           i = 2147483639;
/*      */         }
/*  304 */         if ((i > paramInt) && (this.queue == paramArrayOfObject))
/*  305 */           arrayOfObject = new Object[i];
/*      */       } finally {
/*  307 */         this.allocationSpinLock = 0;
/*      */       }
/*      */     }
/*  310 */     if (arrayOfObject == null)
/*  311 */       Thread.yield();
/*  312 */     this.lock.lock();
/*  313 */     if ((arrayOfObject != null) && (this.queue == paramArrayOfObject)) {
/*  314 */       this.queue = arrayOfObject;
/*  315 */       System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private E dequeue()
/*      */   {
/*  323 */     int i = this.size - 1;
/*  324 */     if (i < 0) {
/*  325 */       return null;
/*      */     }
/*  327 */     Object[] arrayOfObject = this.queue;
/*  328 */     Object localObject1 = arrayOfObject[0];
/*  329 */     Object localObject2 = arrayOfObject[i];
/*  330 */     arrayOfObject[i] = null;
/*  331 */     Comparator localComparator = this.comparator;
/*  332 */     if (localComparator == null) {
/*  333 */       siftDownComparable(0, localObject2, arrayOfObject, i);
/*      */     } else
/*  335 */       siftDownUsingComparator(0, localObject2, arrayOfObject, i, localComparator);
/*  336 */     this.size = i;
/*  337 */     return (E)localObject1;
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
/*      */   private static <T> void siftUpComparable(int paramInt, T paramT, Object[] paramArrayOfObject)
/*      */   {
/*  357 */     Comparable localComparable = (Comparable)paramT;
/*  358 */     while (paramInt > 0) {
/*  359 */       int i = paramInt - 1 >>> 1;
/*  360 */       Object localObject = paramArrayOfObject[i];
/*  361 */       if (localComparable.compareTo(localObject) >= 0)
/*      */         break;
/*  363 */       paramArrayOfObject[paramInt] = localObject;
/*  364 */       paramInt = i;
/*      */     }
/*  366 */     paramArrayOfObject[paramInt] = localComparable;
/*      */   }
/*      */   
/*      */   private static <T> void siftUpUsingComparator(int paramInt, T paramT, Object[] paramArrayOfObject, Comparator<? super T> paramComparator)
/*      */   {
/*  371 */     while (paramInt > 0) {
/*  372 */       int i = paramInt - 1 >>> 1;
/*  373 */       Object localObject = paramArrayOfObject[i];
/*  374 */       if (paramComparator.compare(paramT, localObject) >= 0)
/*      */         break;
/*  376 */       paramArrayOfObject[paramInt] = localObject;
/*  377 */       paramInt = i;
/*      */     }
/*  379 */     paramArrayOfObject[paramInt] = paramT;
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
/*      */   private static <T> void siftDownComparable(int paramInt1, T paramT, Object[] paramArrayOfObject, int paramInt2)
/*      */   {
/*  394 */     if (paramInt2 > 0) {
/*  395 */       Comparable localComparable = (Comparable)paramT;
/*  396 */       int i = paramInt2 >>> 1;
/*  397 */       while (paramInt1 < i) {
/*  398 */         int j = (paramInt1 << 1) + 1;
/*  399 */         Object localObject = paramArrayOfObject[j];
/*  400 */         int k = j + 1;
/*  401 */         if ((k < paramInt2) && 
/*  402 */           (((Comparable)localObject).compareTo(paramArrayOfObject[k]) > 0))
/*  403 */           localObject = paramArrayOfObject[(j = k)];
/*  404 */         if (localComparable.compareTo(localObject) <= 0)
/*      */           break;
/*  406 */         paramArrayOfObject[paramInt1] = localObject;
/*  407 */         paramInt1 = j;
/*      */       }
/*  409 */       paramArrayOfObject[paramInt1] = localComparable;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static <T> void siftDownUsingComparator(int paramInt1, T paramT, Object[] paramArrayOfObject, int paramInt2, Comparator<? super T> paramComparator)
/*      */   {
/*  416 */     if (paramInt2 > 0) {
/*  417 */       int i = paramInt2 >>> 1;
/*  418 */       while (paramInt1 < i) {
/*  419 */         int j = (paramInt1 << 1) + 1;
/*  420 */         Object localObject = paramArrayOfObject[j];
/*  421 */         int k = j + 1;
/*  422 */         if ((k < paramInt2) && (paramComparator.compare(localObject, paramArrayOfObject[k]) > 0))
/*  423 */           localObject = paramArrayOfObject[(j = k)];
/*  424 */         if (paramComparator.compare(paramT, localObject) <= 0)
/*      */           break;
/*  426 */         paramArrayOfObject[paramInt1] = localObject;
/*  427 */         paramInt1 = j;
/*      */       }
/*  429 */       paramArrayOfObject[paramInt1] = paramT;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void heapify()
/*      */   {
/*  438 */     Object[] arrayOfObject = this.queue;
/*  439 */     int i = this.size;
/*  440 */     int j = (i >>> 1) - 1;
/*  441 */     Comparator localComparator = this.comparator;
/*  442 */     int k; if (localComparator == null) {
/*  443 */       for (k = j; k >= 0; k--) {
/*  444 */         siftDownComparable(k, arrayOfObject[k], arrayOfObject, i);
/*      */       }
/*      */     } else {
/*  447 */       for (k = j; k >= 0; k--) {
/*  448 */         siftDownUsingComparator(k, arrayOfObject[k], arrayOfObject, i, localComparator);
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
/*      */   public boolean add(E paramE)
/*      */   {
/*  463 */     return offer(paramE);
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
/*      */   public boolean offer(E paramE)
/*      */   {
/*  478 */     if (paramE == null)
/*  479 */       throw new NullPointerException();
/*  480 */     ReentrantLock localReentrantLock = this.lock;
/*  481 */     localReentrantLock.lock();
/*      */     int i;
/*      */     Object[] arrayOfObject;
/*  484 */     int j; while ((i = this.size) >= (j = (arrayOfObject = this.queue).length))
/*  485 */       tryGrow(arrayOfObject, j);
/*      */     try {
/*  487 */       Comparator localComparator = this.comparator;
/*  488 */       if (localComparator == null) {
/*  489 */         siftUpComparable(i, paramE, arrayOfObject);
/*      */       } else
/*  491 */         siftUpUsingComparator(i, paramE, arrayOfObject, localComparator);
/*  492 */       this.size = (i + 1);
/*  493 */       this.notEmpty.signal();
/*      */     } finally {
/*  495 */       localReentrantLock.unlock();
/*      */     }
/*  497 */     return true;
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
/*      */   public void put(E paramE)
/*      */   {
/*  511 */     offer(paramE);
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/*  530 */     return offer(paramE);
/*      */   }
/*      */   
/*      */   public E poll() {
/*  534 */     ReentrantLock localReentrantLock = this.lock;
/*  535 */     localReentrantLock.lock();
/*      */     try {
/*  537 */       return (E)dequeue();
/*      */     } finally {
/*  539 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public E take()
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 8	java/util/concurrent/PriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 47	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*      */     //   9: aload_0
/*      */     //   10: invokespecial 46	java/util/concurrent/PriorityBlockingQueue:dequeue	()Ljava/lang/Object;
/*      */     //   13: dup
/*      */     //   14: astore_2
/*      */     //   15: ifnonnull +15 -> 30
/*      */     //   18: aload_0
/*      */     //   19: getfield 10	java/util/concurrent/PriorityBlockingQueue:notEmpty	Ljava/util/concurrent/locks/Condition;
/*      */     //   22: invokeinterface 48 1 0
/*      */     //   27: goto -18 -> 9
/*      */     //   30: aload_1
/*      */     //   31: invokevirtual 25	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   34: goto +10 -> 44
/*      */     //   37: astore_3
/*      */     //   38: aload_1
/*      */     //   39: invokevirtual 25	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   42: aload_3
/*      */     //   43: athrow
/*      */     //   44: aload_2
/*      */     //   45: areturn
/*      */     // Line number table:
/*      */     //   Java source line #544	-> byte code offset #0
/*      */     //   Java source line #545	-> byte code offset #5
/*      */     //   Java source line #548	-> byte code offset #9
/*      */     //   Java source line #549	-> byte code offset #18
/*      */     //   Java source line #551	-> byte code offset #30
/*      */     //   Java source line #552	-> byte code offset #34
/*      */     //   Java source line #551	-> byte code offset #37
/*      */     //   Java source line #553	-> byte code offset #44
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	46	0	this	PriorityBlockingQueue
/*      */     //   4	35	1	localReentrantLock	ReentrantLock
/*      */     //   14	31	2	localObject1	Object
/*      */     //   37	6	3	localObject2	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	30	37	finally
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  557 */     long l = paramTimeUnit.toNanos(paramLong);
/*  558 */     ReentrantLock localReentrantLock = this.lock;
/*  559 */     localReentrantLock.lockInterruptibly();
/*      */     Object localObject1;
/*      */     try {
/*  562 */       while (((localObject1 = dequeue()) == null) && (l > 0L))
/*  563 */         l = this.notEmpty.awaitNanos(l);
/*      */     } finally {
/*  565 */       localReentrantLock.unlock();
/*      */     }
/*  567 */     return (E)localObject1;
/*      */   }
/*      */   
/*      */   public E peek() {
/*  571 */     ReentrantLock localReentrantLock = this.lock;
/*  572 */     localReentrantLock.lock();
/*      */     try {
/*  574 */       return this.size == 0 ? null : this.queue[0];
/*      */     } finally {
/*  576 */       localReentrantLock.unlock();
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
/*      */   public Comparator<? super E> comparator()
/*      */   {
/*  590 */     return this.comparator;
/*      */   }
/*      */   
/*      */   public int size() {
/*  594 */     ReentrantLock localReentrantLock = this.lock;
/*  595 */     localReentrantLock.lock();
/*      */     try {
/*  597 */       return this.size;
/*      */     } finally {
/*  599 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int remainingCapacity()
/*      */   {
/*  609 */     return Integer.MAX_VALUE;
/*      */   }
/*      */   
/*      */   private int indexOf(Object paramObject) {
/*  613 */     if (paramObject != null) {
/*  614 */       Object[] arrayOfObject = this.queue;
/*  615 */       int i = this.size;
/*  616 */       for (int j = 0; j < i; j++)
/*  617 */         if (paramObject.equals(arrayOfObject[j]))
/*  618 */           return j;
/*      */     }
/*  620 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void removeAt(int paramInt)
/*      */   {
/*  627 */     Object[] arrayOfObject = this.queue;
/*  628 */     int i = this.size - 1;
/*  629 */     if (i == paramInt) {
/*  630 */       arrayOfObject[paramInt] = null;
/*      */     } else {
/*  632 */       Object localObject = arrayOfObject[i];
/*  633 */       arrayOfObject[i] = null;
/*  634 */       Comparator localComparator = this.comparator;
/*  635 */       if (localComparator == null) {
/*  636 */         siftDownComparable(paramInt, localObject, arrayOfObject, i);
/*      */       } else
/*  638 */         siftDownUsingComparator(paramInt, localObject, arrayOfObject, i, localComparator);
/*  639 */       if (arrayOfObject[paramInt] == localObject) {
/*  640 */         if (localComparator == null) {
/*  641 */           siftUpComparable(paramInt, localObject, arrayOfObject);
/*      */         } else
/*  643 */           siftUpUsingComparator(paramInt, localObject, arrayOfObject, localComparator);
/*      */       }
/*      */     }
/*  646 */     this.size = i;
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*  661 */     ReentrantLock localReentrantLock = this.lock;
/*  662 */     localReentrantLock.lock();
/*      */     try {
/*  664 */       int i = indexOf(paramObject);
/*  665 */       boolean bool; if (i == -1)
/*  666 */         return false;
/*  667 */       removeAt(i);
/*  668 */       return true;
/*      */     } finally {
/*  670 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void removeEQ(Object paramObject)
/*      */   {
/*  678 */     ReentrantLock localReentrantLock = this.lock;
/*  679 */     localReentrantLock.lock();
/*      */     try {
/*  681 */       Object[] arrayOfObject = this.queue;
/*  682 */       int i = 0; for (int j = this.size; i < j; i++) {
/*  683 */         if (paramObject == arrayOfObject[i]) {
/*  684 */           removeAt(i);
/*  685 */           break;
/*      */         }
/*      */       }
/*      */     } finally {
/*  689 */       localReentrantLock.unlock();
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
/*  702 */     ReentrantLock localReentrantLock = this.lock;
/*  703 */     localReentrantLock.lock();
/*      */     try {
/*  705 */       return indexOf(paramObject) != -1;
/*      */     } finally {
/*  707 */       localReentrantLock.unlock();
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
/*      */   public Object[] toArray()
/*      */   {
/*  725 */     ReentrantLock localReentrantLock = this.lock;
/*  726 */     localReentrantLock.lock();
/*      */     try {
/*  728 */       return Arrays.copyOf(this.queue, this.size);
/*      */     } finally {
/*  730 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public String toString() {
/*  735 */     ReentrantLock localReentrantLock = this.lock;
/*  736 */     localReentrantLock.lock();
/*      */     try {
/*  738 */       int i = this.size;
/*  739 */       if (i == 0)
/*  740 */         return "[]";
/*  741 */       Object localObject1 = new StringBuilder();
/*  742 */       ((StringBuilder)localObject1).append('[');
/*  743 */       for (int j = 0; j < i; j++) {
/*  744 */         Object localObject2 = this.queue[j];
/*  745 */         ((StringBuilder)localObject1).append(localObject2 == this ? "(this Collection)" : localObject2);
/*  746 */         if (j != i - 1)
/*  747 */           ((StringBuilder)localObject1).append(',').append(' ');
/*      */       }
/*  749 */       return ']';
/*      */     } finally {
/*  751 */       localReentrantLock.unlock();
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
/*  762 */     return drainTo(paramCollection, Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*      */   {
/*  772 */     if (paramCollection == null)
/*  773 */       throw new NullPointerException();
/*  774 */     if (paramCollection == this)
/*  775 */       throw new IllegalArgumentException();
/*  776 */     if (paramInt <= 0)
/*  777 */       return 0;
/*  778 */     ReentrantLock localReentrantLock = this.lock;
/*  779 */     localReentrantLock.lock();
/*      */     try {
/*  781 */       int i = Math.min(this.size, paramInt);
/*  782 */       for (int j = 0; j < i; j++) {
/*  783 */         paramCollection.add(this.queue[0]);
/*  784 */         dequeue();
/*      */       }
/*  786 */       return i;
/*      */     } finally {
/*  788 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  797 */     ReentrantLock localReentrantLock = this.lock;
/*  798 */     localReentrantLock.lock();
/*      */     try {
/*  800 */       Object[] arrayOfObject = this.queue;
/*  801 */       int i = this.size;
/*  802 */       this.size = 0;
/*  803 */       for (int j = 0; j < i; j++)
/*  804 */         arrayOfObject[j] = null;
/*      */     } finally {
/*  806 */       localReentrantLock.unlock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/*  847 */     ReentrantLock localReentrantLock = this.lock;
/*  848 */     localReentrantLock.lock();
/*      */     try {
/*  850 */       int i = this.size;
/*  851 */       Object localObject1; if (paramArrayOfT.length < i)
/*      */       {
/*  853 */         return (Object[])Arrays.copyOf(this.queue, this.size, paramArrayOfT.getClass()); }
/*  854 */       System.arraycopy(this.queue, 0, paramArrayOfT, 0, i);
/*  855 */       if (paramArrayOfT.length > i)
/*  856 */         paramArrayOfT[i] = null;
/*  857 */       return paramArrayOfT;
/*      */     } finally {
/*  859 */       localReentrantLock.unlock();
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
/*      */   public Iterator<E> iterator()
/*      */   {
/*  873 */     return new Itr(toArray());
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void writeObject(java.io.ObjectOutputStream paramObjectOutputStream)
/*      */     throws java.io.IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 8	java/util/concurrent/PriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: invokevirtual 34	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   7: aload_0
/*      */     //   8: new 70	java/util/PriorityQueue
/*      */     //   11: dup
/*      */     //   12: aload_0
/*      */     //   13: getfield 23	java/util/concurrent/PriorityBlockingQueue:size	I
/*      */     //   16: iconst_1
/*      */     //   17: invokestatic 71	java/lang/Math:max	(II)I
/*      */     //   20: aload_0
/*      */     //   21: getfield 11	java/util/concurrent/PriorityBlockingQueue:comparator	Ljava/util/Comparator;
/*      */     //   24: invokespecial 72	java/util/PriorityQueue:<init>	(ILjava/util/Comparator;)V
/*      */     //   27: putfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   30: aload_0
/*      */     //   31: getfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   34: aload_0
/*      */     //   35: invokevirtual 74	java/util/PriorityQueue:addAll	(Ljava/util/Collection;)Z
/*      */     //   38: pop
/*      */     //   39: aload_1
/*      */     //   40: invokevirtual 75	java/io/ObjectOutputStream:defaultWriteObject	()V
/*      */     //   43: aload_0
/*      */     //   44: aconst_null
/*      */     //   45: putfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   48: aload_0
/*      */     //   49: getfield 8	java/util/concurrent/PriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   52: invokevirtual 25	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   55: goto +18 -> 73
/*      */     //   58: astore_2
/*      */     //   59: aload_0
/*      */     //   60: aconst_null
/*      */     //   61: putfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   64: aload_0
/*      */     //   65: getfield 8	java/util/concurrent/PriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   68: invokevirtual 25	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   71: aload_2
/*      */     //   72: athrow
/*      */     //   73: return
/*      */     // Line number table:
/*      */     //   Java source line #920	-> byte code offset #0
/*      */     //   Java source line #923	-> byte code offset #7
/*      */     //   Java source line #924	-> byte code offset #30
/*      */     //   Java source line #925	-> byte code offset #39
/*      */     //   Java source line #927	-> byte code offset #43
/*      */     //   Java source line #928	-> byte code offset #48
/*      */     //   Java source line #929	-> byte code offset #55
/*      */     //   Java source line #927	-> byte code offset #58
/*      */     //   Java source line #928	-> byte code offset #64
/*      */     //   Java source line #930	-> byte code offset #73
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	74	0	this	PriorityBlockingQueue
/*      */     //   0	74	1	paramObjectOutputStream	java.io.ObjectOutputStream
/*      */     //   58	14	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	43	58	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void readObject(java.io.ObjectInputStream paramObjectInputStream)
/*      */     throws java.io.IOException, java.lang.ClassNotFoundException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: invokevirtual 76	java/io/ObjectInputStream:defaultReadObject	()V
/*      */     //   4: aload_0
/*      */     //   5: aload_0
/*      */     //   6: getfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   9: invokevirtual 77	java/util/PriorityQueue:size	()I
/*      */     //   12: anewarray 12	java/lang/Object
/*      */     //   15: putfield 13	java/util/concurrent/PriorityBlockingQueue:queue	[Ljava/lang/Object;
/*      */     //   18: aload_0
/*      */     //   19: aload_0
/*      */     //   20: getfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   23: invokevirtual 78	java/util/PriorityQueue:comparator	()Ljava/util/Comparator;
/*      */     //   26: putfield 11	java/util/concurrent/PriorityBlockingQueue:comparator	Ljava/util/Comparator;
/*      */     //   29: aload_0
/*      */     //   30: aload_0
/*      */     //   31: getfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   34: invokevirtual 79	java/util/concurrent/PriorityBlockingQueue:addAll	(Ljava/util/Collection;)Z
/*      */     //   37: pop
/*      */     //   38: aload_0
/*      */     //   39: aconst_null
/*      */     //   40: putfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   43: goto +11 -> 54
/*      */     //   46: astore_2
/*      */     //   47: aload_0
/*      */     //   48: aconst_null
/*      */     //   49: putfield 73	java/util/concurrent/PriorityBlockingQueue:q	Ljava/util/PriorityQueue;
/*      */     //   52: aload_2
/*      */     //   53: athrow
/*      */     //   54: return
/*      */     // Line number table:
/*      */     //   Java source line #942	-> byte code offset #0
/*      */     //   Java source line #943	-> byte code offset #4
/*      */     //   Java source line #944	-> byte code offset #18
/*      */     //   Java source line #945	-> byte code offset #29
/*      */     //   Java source line #947	-> byte code offset #38
/*      */     //   Java source line #948	-> byte code offset #43
/*      */     //   Java source line #947	-> byte code offset #46
/*      */     //   Java source line #949	-> byte code offset #54
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	55	0	this	PriorityBlockingQueue
/*      */     //   0	55	1	paramObjectInputStream	java.io.ObjectInputStream
/*      */     //   46	7	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	38	46	finally
/*      */   }
/*      */   
/*      */   final class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     final Object[] array;
/*      */     int cursor;
/*      */     int lastRet;
/*      */     
/*      */     Itr(Object[] paramArrayOfObject)
/*      */     {
/*  885 */       this.lastRet = -1;
/*  886 */       this.array = paramArrayOfObject;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  890 */       return this.cursor < this.array.length;
/*      */     }
/*      */     
/*      */     public E next() {
/*  894 */       if (this.cursor >= this.array.length)
/*  895 */         throw new NoSuchElementException();
/*  896 */       this.lastRet = this.cursor;
/*  897 */       return (E)this.array[(this.cursor++)];
/*      */     }
/*      */     
/*      */     public void remove() {
/*  901 */       if (this.lastRet < 0)
/*  902 */         throw new IllegalStateException();
/*  903 */       PriorityBlockingQueue.this.removeEQ(this.array[this.lastRet]);
/*  904 */       this.lastRet = -1;
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
/*      */   static final class PBQSpliterator<E>
/*      */     implements Spliterator<E>
/*      */   {
/*      */     final PriorityBlockingQueue<E> queue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Object[] array;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int index;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int fence;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     PBQSpliterator(PriorityBlockingQueue<E> paramPriorityBlockingQueue, Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*      */     {
/*  961 */       this.queue = paramPriorityBlockingQueue;
/*  962 */       this.array = paramArrayOfObject;
/*  963 */       this.index = paramInt1;
/*  964 */       this.fence = paramInt2;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/*  969 */       if ((i = this.fence) < 0)
/*  970 */         i = this.fence = (this.array = this.queue.toArray()).length;
/*  971 */       return i;
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/*  975 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/*  976 */       return j >= k ? null : new PBQSpliterator(this.queue, this.array, j, this.index = k);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/*  983 */       if (paramConsumer == null)
/*  984 */         throw new NullPointerException();
/*  985 */       Object[] arrayOfObject; if ((arrayOfObject = this.array) == null)
/*  986 */         this.fence = (arrayOfObject = this.queue.toArray()).length;
/*  987 */       int j; int i; if (((j = this.fence) <= arrayOfObject.length) && ((i = this.index) >= 0) && (i < (this.index = j))) {
/*      */         do {
/*  989 */           paramConsumer.accept(arrayOfObject[i]);i++; } while (i < j);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer) {
/*  994 */       if (paramConsumer == null)
/*  995 */         throw new NullPointerException();
/*  996 */       if ((getFence() > this.index) && (this.index >= 0)) {
/*  997 */         Object localObject = this.array[(this.index++)];
/*  998 */         paramConsumer.accept(localObject);
/*  999 */         return true;
/*      */       }
/* 1001 */       return false;
/*      */     }
/*      */     
/* 1004 */     public long estimateSize() { return getFence() - this.index; }
/*      */     
/*      */     public int characteristics() {
/* 1007 */       return 16704;
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1027 */     return new PBQSpliterator(this, null, 0, -1);
/*      */   }
/*      */   
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 1035 */       UNSAFE = Unsafe.getUnsafe();
/* 1036 */       Class localClass = PriorityBlockingQueue.class;
/*      */       
/* 1038 */       allocationSpinLockOffset = UNSAFE.objectFieldOffset(localClass.getDeclaredField("allocationSpinLock"));
/*      */     } catch (Exception localException) {
/* 1040 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/PriorityBlockingQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */