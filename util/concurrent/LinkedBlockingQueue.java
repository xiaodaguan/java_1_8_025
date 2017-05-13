/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ public class LinkedBlockingQueue<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingQueue<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6903933977591709194L;
/*      */   private final int capacity;
/*      */   /* Error */
/*      */   private void signalNotEmpty()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 1	java/util/concurrent/LinkedBlockingQueue:takeLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 2	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: getfield 3	java/util/concurrent/LinkedBlockingQueue:notEmpty	Ljava/util/concurrent/locks/Condition;
/*      */     //   13: invokeinterface 4 1 0
/*      */     //   18: aload_1
/*      */     //   19: invokevirtual 5	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   22: goto +10 -> 32
/*      */     //   25: astore_2
/*      */     //   26: aload_1
/*      */     //   27: invokevirtual 5	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   30: aload_2
/*      */     //   31: athrow
/*      */     //   32: return
/*      */     // Line number table:
/*      */     //   Java source line #171	-> byte code offset #0
/*      */     //   Java source line #172	-> byte code offset #5
/*      */     //   Java source line #174	-> byte code offset #9
/*      */     //   Java source line #176	-> byte code offset #18
/*      */     //   Java source line #177	-> byte code offset #22
/*      */     //   Java source line #176	-> byte code offset #25
/*      */     //   Java source line #178	-> byte code offset #32
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	33	0	this	LinkedBlockingQueue
/*      */     //   4	23	1	localReentrantLock	ReentrantLock
/*      */     //   25	6	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	18	25	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void signalNotFull()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 6	java/util/concurrent/LinkedBlockingQueue:putLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 2	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: getfield 7	java/util/concurrent/LinkedBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   13: invokeinterface 4 1 0
/*      */     //   18: aload_1
/*      */     //   19: invokevirtual 5	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   22: goto +10 -> 32
/*      */     //   25: astore_2
/*      */     //   26: aload_1
/*      */     //   27: invokevirtual 5	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   30: aload_2
/*      */     //   31: athrow
/*      */     //   32: return
/*      */     // Line number table:
/*      */     //   Java source line #184	-> byte code offset #0
/*      */     //   Java source line #185	-> byte code offset #5
/*      */     //   Java source line #187	-> byte code offset #9
/*      */     //   Java source line #189	-> byte code offset #18
/*      */     //   Java source line #190	-> byte code offset #22
/*      */     //   Java source line #189	-> byte code offset #25
/*      */     //   Java source line #191	-> byte code offset #32
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	33	0	this	LinkedBlockingQueue
/*      */     //   4	23	1	localReentrantLock	ReentrantLock
/*      */     //   25	6	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	18	25	finally
/*      */   }
/*      */   
/*      */   static class Node<E>
/*      */   {
/*      */     E item;
/*      */     Node<E> next;
/*      */     
/*      */     Node(E paramE)
/*      */     {
/*  133 */       this.item = paramE;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  140 */   private final AtomicInteger count = new AtomicInteger();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   transient Node<E> head;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient Node<E> last;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  155 */   private final ReentrantLock takeLock = new ReentrantLock();
/*      */   
/*      */ 
/*  158 */   private final Condition notEmpty = this.takeLock.newCondition();
/*      */   
/*      */ 
/*  161 */   private final ReentrantLock putLock = new ReentrantLock();
/*      */   
/*      */ 
/*  164 */   private final Condition notFull = this.putLock.newCondition();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void enqueue(Node<E> paramNode)
/*      */   {
/*  201 */     this.last = (this.last.next = paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private E dequeue()
/*      */   {
/*  212 */     Node localNode1 = this.head;
/*  213 */     Node localNode2 = localNode1.next;
/*  214 */     localNode1.next = localNode1;
/*  215 */     this.head = localNode2;
/*  216 */     Object localObject = localNode2.item;
/*  217 */     localNode2.item = null;
/*  218 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void fullyLock()
/*      */   {
/*  225 */     this.putLock.lock();
/*  226 */     this.takeLock.lock();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void fullyUnlock()
/*      */   {
/*  233 */     this.takeLock.unlock();
/*  234 */     this.putLock.unlock();
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
/*      */   public LinkedBlockingQueue()
/*      */   {
/*  250 */     this(Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedBlockingQueue(int paramInt)
/*      */   {
/*  261 */     if (paramInt <= 0) throw new IllegalArgumentException();
/*  262 */     this.capacity = paramInt;
/*  263 */     this.last = (this.head = new Node(null));
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
/*      */   public LinkedBlockingQueue(Collection<? extends E> paramCollection)
/*      */   {
/*  277 */     this(Integer.MAX_VALUE);
/*  278 */     ReentrantLock localReentrantLock = this.putLock;
/*  279 */     localReentrantLock.lock();
/*      */     try {
/*  281 */       int i = 0;
/*  282 */       for (Object localObject1 : paramCollection) {
/*  283 */         if (localObject1 == null)
/*  284 */           throw new NullPointerException();
/*  285 */         if (i == this.capacity)
/*  286 */           throw new IllegalStateException("Queue full");
/*  287 */         enqueue(new Node(localObject1));
/*  288 */         i++;
/*      */       }
/*  290 */       this.count.set(i);
/*      */     } finally {
/*  292 */       localReentrantLock.unlock();
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
/*  304 */     return this.count.get();
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
/*  321 */     return this.capacity - this.count.get();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(E paramE)
/*      */     throws InterruptedException
/*      */   {
/*  332 */     if (paramE == null) { throw new NullPointerException();
/*      */     }
/*      */     
/*  335 */     int i = -1;
/*  336 */     Node localNode = new Node(paramE);
/*  337 */     ReentrantLock localReentrantLock = this.putLock;
/*  338 */     AtomicInteger localAtomicInteger = this.count;
/*  339 */     localReentrantLock.lockInterruptibly();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  349 */       while (localAtomicInteger.get() == this.capacity) {
/*  350 */         this.notFull.await();
/*      */       }
/*  352 */       enqueue(localNode);
/*  353 */       i = localAtomicInteger.getAndIncrement();
/*  354 */       if (i + 1 < this.capacity)
/*  355 */         this.notFull.signal();
/*      */     } finally {
/*  357 */       localReentrantLock.unlock();
/*      */     }
/*  359 */     if (i == 0) {
/*  360 */       signalNotEmpty();
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
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  375 */     if (paramE == null) throw new NullPointerException();
/*  376 */     long l = paramTimeUnit.toNanos(paramLong);
/*  377 */     int i = -1;
/*  378 */     ReentrantLock localReentrantLock = this.putLock;
/*  379 */     AtomicInteger localAtomicInteger = this.count;
/*  380 */     localReentrantLock.lockInterruptibly();
/*      */     try {
/*  382 */       while (localAtomicInteger.get() == this.capacity) {
/*  383 */         if (l <= 0L)
/*  384 */           return false;
/*  385 */         l = this.notFull.awaitNanos(l);
/*      */       }
/*  387 */       enqueue(new Node(paramE));
/*  388 */       i = localAtomicInteger.getAndIncrement();
/*  389 */       if (i + 1 < this.capacity)
/*  390 */         this.notFull.signal();
/*      */     } finally {
/*  392 */       localReentrantLock.unlock();
/*      */     }
/*  394 */     if (i == 0)
/*  395 */       signalNotEmpty();
/*  396 */     return true;
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
/*  411 */     if (paramE == null) throw new NullPointerException();
/*  412 */     AtomicInteger localAtomicInteger = this.count;
/*  413 */     if (localAtomicInteger.get() == this.capacity)
/*  414 */       return false;
/*  415 */     int i = -1;
/*  416 */     Node localNode = new Node(paramE);
/*  417 */     ReentrantLock localReentrantLock = this.putLock;
/*  418 */     localReentrantLock.lock();
/*      */     try {
/*  420 */       if (localAtomicInteger.get() < this.capacity) {
/*  421 */         enqueue(localNode);
/*  422 */         i = localAtomicInteger.getAndIncrement();
/*  423 */         if (i + 1 < this.capacity)
/*  424 */           this.notFull.signal();
/*      */       }
/*      */     } finally {
/*  427 */       localReentrantLock.unlock();
/*      */     }
/*  429 */     if (i == 0)
/*  430 */       signalNotEmpty();
/*  431 */     return i >= 0;
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException
/*      */   {
/*  436 */     int i = -1;
/*  437 */     AtomicInteger localAtomicInteger = this.count;
/*  438 */     ReentrantLock localReentrantLock = this.takeLock;
/*  439 */     localReentrantLock.lockInterruptibly();
/*      */     Object localObject1;
/*  441 */     try { while (localAtomicInteger.get() == 0) {
/*  442 */         this.notEmpty.await();
/*      */       }
/*  444 */       localObject1 = dequeue();
/*  445 */       i = localAtomicInteger.getAndDecrement();
/*  446 */       if (i > 1)
/*  447 */         this.notEmpty.signal();
/*      */     } finally {
/*  449 */       localReentrantLock.unlock();
/*      */     }
/*  451 */     if (i == this.capacity)
/*  452 */       signalNotFull();
/*  453 */     return (E)localObject1;
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  457 */     Object localObject1 = null;
/*  458 */     int i = -1;
/*  459 */     long l = paramTimeUnit.toNanos(paramLong);
/*  460 */     AtomicInteger localAtomicInteger = this.count;
/*  461 */     ReentrantLock localReentrantLock = this.takeLock;
/*  462 */     localReentrantLock.lockInterruptibly();
/*      */     try {
/*  464 */       while (localAtomicInteger.get() == 0) {
/*  465 */         if (l <= 0L)
/*  466 */           return null;
/*  467 */         l = this.notEmpty.awaitNanos(l);
/*      */       }
/*  469 */       localObject1 = dequeue();
/*  470 */       i = localAtomicInteger.getAndDecrement();
/*  471 */       if (i > 1)
/*  472 */         this.notEmpty.signal();
/*      */     } finally {
/*  474 */       localReentrantLock.unlock();
/*      */     }
/*  476 */     if (i == this.capacity)
/*  477 */       signalNotFull();
/*  478 */     return (E)localObject1;
/*      */   }
/*      */   
/*      */   public E poll() {
/*  482 */     AtomicInteger localAtomicInteger = this.count;
/*  483 */     if (localAtomicInteger.get() == 0)
/*  484 */       return null;
/*  485 */     Object localObject1 = null;
/*  486 */     int i = -1;
/*  487 */     ReentrantLock localReentrantLock = this.takeLock;
/*  488 */     localReentrantLock.lock();
/*      */     try {
/*  490 */       if (localAtomicInteger.get() > 0) {
/*  491 */         localObject1 = dequeue();
/*  492 */         i = localAtomicInteger.getAndDecrement();
/*  493 */         if (i > 1)
/*  494 */           this.notEmpty.signal();
/*      */       }
/*      */     } finally {
/*  497 */       localReentrantLock.unlock();
/*      */     }
/*  499 */     if (i == this.capacity)
/*  500 */       signalNotFull();
/*  501 */     return (E)localObject1;
/*      */   }
/*      */   
/*      */   public E peek() {
/*  505 */     if (this.count.get() == 0)
/*  506 */       return null;
/*  507 */     ReentrantLock localReentrantLock = this.takeLock;
/*  508 */     localReentrantLock.lock();
/*      */     try {
/*  510 */       Node localNode = this.head.next;
/*  511 */       Object localObject1; if (localNode == null) {
/*  512 */         return null;
/*      */       }
/*  514 */       return (E)localNode.item;
/*      */     } finally {
/*  516 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void unlink(Node<E> paramNode1, Node<E> paramNode2)
/*      */   {
/*  527 */     paramNode1.item = null;
/*  528 */     paramNode2.next = paramNode1.next;
/*  529 */     if (this.last == paramNode1)
/*  530 */       this.last = paramNode2;
/*  531 */     if (this.count.getAndDecrement() == this.capacity) {
/*  532 */       this.notFull.signal();
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*  547 */     if (paramObject == null) return false;
/*  548 */     fullyLock();
/*      */     try {
/*  550 */       Object localObject1 = this.head; for (Node localNode = ((Node)localObject1).next; 
/*  551 */           localNode != null; 
/*  552 */           localNode = localNode.next) {
/*  553 */         if (paramObject.equals(localNode.item)) {
/*  554 */           unlink(localNode, (Node)localObject1);
/*  555 */           return true;
/*      */         }
/*  552 */         localObject1 = localNode;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  558 */       return false;
/*      */     } finally {
/*  560 */       fullyUnlock();
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
/*  573 */     if (paramObject == null) return false;
/*  574 */     fullyLock();
/*      */     try {
/*  576 */       for (Node localNode = this.head.next; localNode != null; localNode = localNode.next)
/*  577 */         if (paramObject.equals(localNode.item))
/*  578 */           return true;
/*  579 */       return false;
/*      */     } finally {
/*  581 */       fullyUnlock();
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
/*  599 */     fullyLock();
/*      */     try {
/*  601 */       int i = this.count.get();
/*  602 */       Object[] arrayOfObject = new Object[i];
/*  603 */       int j = 0;
/*  604 */       for (Object localObject1 = this.head.next; localObject1 != null; localObject1 = ((Node)localObject1).next)
/*  605 */         arrayOfObject[(j++)] = ((Node)localObject1).item;
/*  606 */       return arrayOfObject;
/*      */     } finally {
/*  608 */       fullyUnlock();
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
/*  649 */     fullyLock();
/*      */     try {
/*  651 */       int i = this.count.get();
/*  652 */       if (paramArrayOfT.length < i)
/*      */       {
/*  654 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/*      */       }
/*  656 */       int j = 0;
/*  657 */       for (Object localObject1 = this.head.next; localObject1 != null; localObject1 = ((Node)localObject1).next)
/*  658 */         paramArrayOfT[(j++)] = ((Node)localObject1).item;
/*  659 */       if (paramArrayOfT.length > j)
/*  660 */         paramArrayOfT[j] = null;
/*  661 */       return paramArrayOfT;
/*      */     } finally {
/*  663 */       fullyUnlock();
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public String toString()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 47	java/util/concurrent/LinkedBlockingQueue:fullyLock	()V
/*      */     //   4: aload_0
/*      */     //   5: getfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   8: getfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   11: astore_1
/*      */     //   12: aload_1
/*      */     //   13: ifnonnull +12 -> 25
/*      */     //   16: ldc 56
/*      */     //   18: astore_2
/*      */     //   19: aload_0
/*      */     //   20: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   23: aload_2
/*      */     //   24: areturn
/*      */     //   25: new 57	java/lang/StringBuilder
/*      */     //   28: dup
/*      */     //   29: invokespecial 58	java/lang/StringBuilder:<init>	()V
/*      */     //   32: astore_2
/*      */     //   33: aload_2
/*      */     //   34: bipush 91
/*      */     //   36: invokevirtual 59	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   39: pop
/*      */     //   40: aload_1
/*      */     //   41: getfield 11	java/util/concurrent/LinkedBlockingQueue$Node:item	Ljava/lang/Object;
/*      */     //   44: astore_3
/*      */     //   45: aload_2
/*      */     //   46: aload_3
/*      */     //   47: aload_0
/*      */     //   48: if_acmpne +8 -> 56
/*      */     //   51: ldc 60
/*      */     //   53: goto +4 -> 57
/*      */     //   56: aload_3
/*      */     //   57: invokevirtual 61	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   60: pop
/*      */     //   61: aload_1
/*      */     //   62: getfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   65: astore_1
/*      */     //   66: aload_1
/*      */     //   67: ifnonnull +21 -> 88
/*      */     //   70: aload_2
/*      */     //   71: bipush 93
/*      */     //   73: invokevirtual 59	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   76: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   79: astore 4
/*      */     //   81: aload_0
/*      */     //   82: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   85: aload 4
/*      */     //   87: areturn
/*      */     //   88: aload_2
/*      */     //   89: bipush 44
/*      */     //   91: invokevirtual 59	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   94: bipush 32
/*      */     //   96: invokevirtual 59	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   99: pop
/*      */     //   100: goto -60 -> 40
/*      */     //   103: astore 5
/*      */     //   105: aload_0
/*      */     //   106: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   109: aload 5
/*      */     //   111: athrow
/*      */     // Line number table:
/*      */     //   Java source line #668	-> byte code offset #0
/*      */     //   Java source line #670	-> byte code offset #4
/*      */     //   Java source line #671	-> byte code offset #12
/*      */     //   Java source line #672	-> byte code offset #16
/*      */     //   Java source line #685	-> byte code offset #19
/*      */     //   Java source line #674	-> byte code offset #25
/*      */     //   Java source line #675	-> byte code offset #33
/*      */     //   Java source line #677	-> byte code offset #40
/*      */     //   Java source line #678	-> byte code offset #45
/*      */     //   Java source line #679	-> byte code offset #61
/*      */     //   Java source line #680	-> byte code offset #66
/*      */     //   Java source line #681	-> byte code offset #70
/*      */     //   Java source line #685	-> byte code offset #81
/*      */     //   Java source line #682	-> byte code offset #88
/*      */     //   Java source line #683	-> byte code offset #100
/*      */     //   Java source line #685	-> byte code offset #103
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	112	0	this	LinkedBlockingQueue
/*      */     //   11	56	1	localNode	Node
/*      */     //   18	71	2	localObject1	Object
/*      */     //   44	13	3	localObject2	Object
/*      */     //   79	7	4	str	String
/*      */     //   103	7	5	localObject3	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   4	19	103	finally
/*      */     //   25	81	103	finally
/*      */     //   88	105	103	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void clear()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 47	java/util/concurrent/LinkedBlockingQueue:fullyLock	()V
/*      */     //   4: aload_0
/*      */     //   5: getfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   8: astore_2
/*      */     //   9: aload_2
/*      */     //   10: getfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   13: dup
/*      */     //   14: astore_1
/*      */     //   15: ifnull +18 -> 33
/*      */     //   18: aload_2
/*      */     //   19: aload_2
/*      */     //   20: putfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   23: aload_1
/*      */     //   24: aconst_null
/*      */     //   25: putfield 11	java/util/concurrent/LinkedBlockingQueue$Node:item	Ljava/lang/Object;
/*      */     //   28: aload_1
/*      */     //   29: astore_2
/*      */     //   30: goto -21 -> 9
/*      */     //   33: aload_0
/*      */     //   34: aload_0
/*      */     //   35: getfield 8	java/util/concurrent/LinkedBlockingQueue:last	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   38: putfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   41: aload_0
/*      */     //   42: getfield 18	java/util/concurrent/LinkedBlockingQueue:count	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   45: iconst_0
/*      */     //   46: invokevirtual 63	java/util/concurrent/atomic/AtomicInteger:getAndSet	(I)I
/*      */     //   49: aload_0
/*      */     //   50: getfield 24	java/util/concurrent/LinkedBlockingQueue:capacity	I
/*      */     //   53: if_icmpne +12 -> 65
/*      */     //   56: aload_0
/*      */     //   57: getfield 7	java/util/concurrent/LinkedBlockingQueue:notFull	Ljava/util/concurrent/locks/Condition;
/*      */     //   60: invokeinterface 4 1 0
/*      */     //   65: aload_0
/*      */     //   66: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   69: goto +10 -> 79
/*      */     //   72: astore_3
/*      */     //   73: aload_0
/*      */     //   74: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   77: aload_3
/*      */     //   78: athrow
/*      */     //   79: return
/*      */     // Line number table:
/*      */     //   Java source line #694	-> byte code offset #0
/*      */     //   Java source line #696	-> byte code offset #4
/*      */     //   Java source line #697	-> byte code offset #18
/*      */     //   Java source line #698	-> byte code offset #23
/*      */     //   Java source line #696	-> byte code offset #28
/*      */     //   Java source line #700	-> byte code offset #33
/*      */     //   Java source line #702	-> byte code offset #41
/*      */     //   Java source line #703	-> byte code offset #56
/*      */     //   Java source line #705	-> byte code offset #65
/*      */     //   Java source line #706	-> byte code offset #69
/*      */     //   Java source line #705	-> byte code offset #72
/*      */     //   Java source line #707	-> byte code offset #79
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	80	0	this	LinkedBlockingQueue
/*      */     //   14	15	1	localNode	Node
/*      */     //   8	22	2	localObject1	Object
/*      */     //   72	6	3	localObject2	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   4	65	72	finally
/*      */   }
/*      */   
/*      */   public int drainTo(Collection<? super E> paramCollection)
/*      */   {
/*  716 */     return drainTo(paramCollection, Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnonnull +11 -> 12
/*      */     //   4: new 30	java/lang/NullPointerException
/*      */     //   7: dup
/*      */     //   8: invokespecial 31	java/lang/NullPointerException:<init>	()V
/*      */     //   11: athrow
/*      */     //   12: aload_1
/*      */     //   13: aload_0
/*      */     //   14: if_acmpne +11 -> 25
/*      */     //   17: new 22	java/lang/IllegalArgumentException
/*      */     //   20: dup
/*      */     //   21: invokespecial 23	java/lang/IllegalArgumentException:<init>	()V
/*      */     //   24: athrow
/*      */     //   25: iload_2
/*      */     //   26: ifgt +5 -> 31
/*      */     //   29: iconst_0
/*      */     //   30: ireturn
/*      */     //   31: iconst_0
/*      */     //   32: istore_3
/*      */     //   33: aload_0
/*      */     //   34: getfield 1	java/util/concurrent/LinkedBlockingQueue:takeLock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   37: astore 4
/*      */     //   39: aload 4
/*      */     //   41: invokevirtual 2	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   44: iload_2
/*      */     //   45: aload_0
/*      */     //   46: getfield 18	java/util/concurrent/LinkedBlockingQueue:count	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   49: invokevirtual 37	java/util/concurrent/atomic/AtomicInteger:get	()I
/*      */     //   52: invokestatic 65	java/lang/Math:min	(II)I
/*      */     //   55: istore 5
/*      */     //   57: aload_0
/*      */     //   58: getfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   61: astore 6
/*      */     //   63: iconst_0
/*      */     //   64: istore 7
/*      */     //   66: iload 7
/*      */     //   68: iload 5
/*      */     //   70: if_icmpge +45 -> 115
/*      */     //   73: aload 6
/*      */     //   75: getfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   78: astore 8
/*      */     //   80: aload_1
/*      */     //   81: aload 8
/*      */     //   83: getfield 11	java/util/concurrent/LinkedBlockingQueue$Node:item	Ljava/lang/Object;
/*      */     //   86: invokeinterface 66 2 0
/*      */     //   91: pop
/*      */     //   92: aload 8
/*      */     //   94: aconst_null
/*      */     //   95: putfield 11	java/util/concurrent/LinkedBlockingQueue$Node:item	Ljava/lang/Object;
/*      */     //   98: aload 6
/*      */     //   100: aload 6
/*      */     //   102: putfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   105: aload 8
/*      */     //   107: astore 6
/*      */     //   109: iinc 7 1
/*      */     //   112: goto -46 -> 66
/*      */     //   115: iload 5
/*      */     //   117: istore 8
/*      */     //   119: iload 7
/*      */     //   121: ifle +32 -> 153
/*      */     //   124: aload_0
/*      */     //   125: aload 6
/*      */     //   127: putfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   130: aload_0
/*      */     //   131: getfield 18	java/util/concurrent/LinkedBlockingQueue:count	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   134: iload 7
/*      */     //   136: ineg
/*      */     //   137: invokevirtual 67	java/util/concurrent/atomic/AtomicInteger:getAndAdd	(I)I
/*      */     //   140: aload_0
/*      */     //   141: getfield 24	java/util/concurrent/LinkedBlockingQueue:capacity	I
/*      */     //   144: if_icmpne +7 -> 151
/*      */     //   147: iconst_1
/*      */     //   148: goto +4 -> 152
/*      */     //   151: iconst_0
/*      */     //   152: istore_3
/*      */     //   153: aload 4
/*      */     //   155: invokevirtual 5	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   158: iload_3
/*      */     //   159: ifeq +7 -> 166
/*      */     //   162: aload_0
/*      */     //   163: invokespecial 46	java/util/concurrent/LinkedBlockingQueue:signalNotFull	()V
/*      */     //   166: iload 8
/*      */     //   168: ireturn
/*      */     //   169: astore 9
/*      */     //   171: iload 7
/*      */     //   173: ifle +32 -> 205
/*      */     //   176: aload_0
/*      */     //   177: aload 6
/*      */     //   179: putfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   182: aload_0
/*      */     //   183: getfield 18	java/util/concurrent/LinkedBlockingQueue:count	Ljava/util/concurrent/atomic/AtomicInteger;
/*      */     //   186: iload 7
/*      */     //   188: ineg
/*      */     //   189: invokevirtual 67	java/util/concurrent/atomic/AtomicInteger:getAndAdd	(I)I
/*      */     //   192: aload_0
/*      */     //   193: getfield 24	java/util/concurrent/LinkedBlockingQueue:capacity	I
/*      */     //   196: if_icmpne +7 -> 203
/*      */     //   199: iconst_1
/*      */     //   200: goto +4 -> 204
/*      */     //   203: iconst_0
/*      */     //   204: istore_3
/*      */     //   205: aload 9
/*      */     //   207: athrow
/*      */     //   208: astore 10
/*      */     //   210: aload 4
/*      */     //   212: invokevirtual 5	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   215: iload_3
/*      */     //   216: ifeq +7 -> 223
/*      */     //   219: aload_0
/*      */     //   220: invokespecial 46	java/util/concurrent/LinkedBlockingQueue:signalNotFull	()V
/*      */     //   223: aload 10
/*      */     //   225: athrow
/*      */     // Line number table:
/*      */     //   Java source line #726	-> byte code offset #0
/*      */     //   Java source line #727	-> byte code offset #4
/*      */     //   Java source line #728	-> byte code offset #12
/*      */     //   Java source line #729	-> byte code offset #17
/*      */     //   Java source line #730	-> byte code offset #25
/*      */     //   Java source line #731	-> byte code offset #29
/*      */     //   Java source line #732	-> byte code offset #31
/*      */     //   Java source line #733	-> byte code offset #33
/*      */     //   Java source line #734	-> byte code offset #39
/*      */     //   Java source line #736	-> byte code offset #44
/*      */     //   Java source line #738	-> byte code offset #57
/*      */     //   Java source line #739	-> byte code offset #63
/*      */     //   Java source line #741	-> byte code offset #66
/*      */     //   Java source line #742	-> byte code offset #73
/*      */     //   Java source line #743	-> byte code offset #80
/*      */     //   Java source line #744	-> byte code offset #92
/*      */     //   Java source line #745	-> byte code offset #98
/*      */     //   Java source line #746	-> byte code offset #105
/*      */     //   Java source line #747	-> byte code offset #109
/*      */     //   Java source line #748	-> byte code offset #112
/*      */     //   Java source line #749	-> byte code offset #115
/*      */     //   Java source line #752	-> byte code offset #119
/*      */     //   Java source line #754	-> byte code offset #124
/*      */     //   Java source line #755	-> byte code offset #130
/*      */     //   Java source line #759	-> byte code offset #153
/*      */     //   Java source line #760	-> byte code offset #158
/*      */     //   Java source line #761	-> byte code offset #162
/*      */     //   Java source line #752	-> byte code offset #169
/*      */     //   Java source line #754	-> byte code offset #176
/*      */     //   Java source line #755	-> byte code offset #182
/*      */     //   Java source line #759	-> byte code offset #208
/*      */     //   Java source line #760	-> byte code offset #215
/*      */     //   Java source line #761	-> byte code offset #219
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	226	0	this	LinkedBlockingQueue
/*      */     //   0	226	1	paramCollection	Collection<? super E>
/*      */     //   0	226	2	paramInt	int
/*      */     //   32	184	3	i	int
/*      */     //   37	174	4	localReentrantLock	ReentrantLock
/*      */     //   55	61	5	localNode1	Node
/*      */     //   61	117	6	localObject1	Object
/*      */     //   64	123	7	j	int
/*      */     //   78	89	8	localNode2	Node
/*      */     //   169	37	9	localObject2	Object
/*      */     //   208	16	10	localObject3	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   66	119	169	finally
/*      */     //   169	171	169	finally
/*      */     //   44	153	208	finally
/*      */     //   169	210	208	finally
/*      */   }
/*      */   
/*      */   public Iterator<E> iterator()
/*      */   {
/*  775 */     return new Itr();
/*      */   }
/*      */   
/*      */   private class Itr
/*      */     implements Iterator<E>
/*      */   {
/*      */     private LinkedBlockingQueue.Node<E> current;
/*      */     private LinkedBlockingQueue.Node<E> lastRet;
/*      */     private E currentElement;
/*      */     
/*      */     /* Error */
/*      */     Itr()
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: putfield 1	java/util/concurrent/LinkedBlockingQueue$Itr:this$0	Ljava/util/concurrent/LinkedBlockingQueue;
/*      */       //   5: aload_0
/*      */       //   6: invokespecial 2	java/lang/Object:<init>	()V
/*      */       //   9: aload_1
/*      */       //   10: invokevirtual 3	java/util/concurrent/LinkedBlockingQueue:fullyLock	()V
/*      */       //   13: aload_0
/*      */       //   14: aload_1
/*      */       //   15: getfield 4	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */       //   18: getfield 5	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */       //   21: putfield 6	java/util/concurrent/LinkedBlockingQueue$Itr:current	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */       //   24: aload_0
/*      */       //   25: getfield 6	java/util/concurrent/LinkedBlockingQueue$Itr:current	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */       //   28: ifnull +14 -> 42
/*      */       //   31: aload_0
/*      */       //   32: aload_0
/*      */       //   33: getfield 6	java/util/concurrent/LinkedBlockingQueue$Itr:current	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */       //   36: getfield 7	java/util/concurrent/LinkedBlockingQueue$Node:item	Ljava/lang/Object;
/*      */       //   39: putfield 8	java/util/concurrent/LinkedBlockingQueue$Itr:currentElement	Ljava/lang/Object;
/*      */       //   42: aload_1
/*      */       //   43: invokevirtual 9	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */       //   46: goto +10 -> 56
/*      */       //   49: astore_2
/*      */       //   50: aload_1
/*      */       //   51: invokevirtual 9	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */       //   54: aload_2
/*      */       //   55: athrow
/*      */       //   56: return
/*      */       // Line number table:
/*      */       //   Java source line #789	-> byte code offset #0
/*      */       //   Java source line #790	-> byte code offset #9
/*      */       //   Java source line #792	-> byte code offset #13
/*      */       //   Java source line #793	-> byte code offset #24
/*      */       //   Java source line #794	-> byte code offset #31
/*      */       //   Java source line #796	-> byte code offset #42
/*      */       //   Java source line #797	-> byte code offset #46
/*      */       //   Java source line #796	-> byte code offset #49
/*      */       //   Java source line #798	-> byte code offset #56
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	57	0	this	Itr
/*      */       //   0	57	1	this$1	LinkedBlockingQueue
/*      */       //   49	6	2	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   13	42	49	finally
/*      */     }
/*      */     
/*      */     public boolean hasNext()
/*      */     {
/*  801 */       return this.current != null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private LinkedBlockingQueue.Node<E> nextNode(LinkedBlockingQueue.Node<E> paramNode)
/*      */     {
/*      */       for (;;)
/*      */       {
/*  813 */         LinkedBlockingQueue.Node localNode = paramNode.next;
/*  814 */         if (localNode == paramNode)
/*  815 */           return LinkedBlockingQueue.this.head.next;
/*  816 */         if ((localNode == null) || (localNode.item != null))
/*  817 */           return localNode;
/*  818 */         paramNode = localNode;
/*      */       }
/*      */     }
/*      */     
/*      */     public E next() {
/*  823 */       LinkedBlockingQueue.this.fullyLock();
/*      */       try {
/*  825 */         if (this.current == null)
/*  826 */           throw new NoSuchElementException();
/*  827 */         Object localObject1 = this.currentElement;
/*  828 */         this.lastRet = this.current;
/*  829 */         this.current = nextNode(this.current);
/*  830 */         this.currentElement = (this.current == null ? null : this.current.item);
/*  831 */         return (E)localObject1;
/*      */       } finally {
/*  833 */         LinkedBlockingQueue.this.fullyUnlock();
/*      */       }
/*      */     }
/*      */     
/*      */     public void remove() {
/*  838 */       if (this.lastRet == null)
/*  839 */         throw new IllegalStateException();
/*  840 */       LinkedBlockingQueue.this.fullyLock();
/*      */       try {
/*  842 */         LinkedBlockingQueue.Node localNode1 = this.lastRet;
/*  843 */         this.lastRet = null;
/*  844 */         Object localObject1 = LinkedBlockingQueue.this.head; for (LinkedBlockingQueue.Node localNode2 = ((LinkedBlockingQueue.Node)localObject1).next; 
/*  845 */             localNode2 != null; 
/*  846 */             localNode2 = localNode2.next) {
/*  847 */           if (localNode2 == localNode1) {
/*  848 */             LinkedBlockingQueue.this.unlink(localNode2, (LinkedBlockingQueue.Node)localObject1);
/*  849 */             break;
/*      */           }
/*  846 */           localObject1 = localNode2;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/*  853 */         LinkedBlockingQueue.this.fullyUnlock();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class LBQSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedBlockingQueue<E> queue;
/*      */     LinkedBlockingQueue.Node<E> current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     long est;
/*      */     
/*      */     LBQSpliterator(LinkedBlockingQueue<E> paramLinkedBlockingQueue) {
/*  867 */       this.queue = paramLinkedBlockingQueue;
/*  868 */       this.est = paramLinkedBlockingQueue.size();
/*      */     }
/*      */     
/*  871 */     public long estimateSize() { return this.est; }
/*      */     
/*      */     public Spliterator<E> trySplit()
/*      */     {
/*  875 */       LinkedBlockingQueue localLinkedBlockingQueue = this.queue;
/*  876 */       int i = this.batch;
/*  877 */       int j = i >= 33554432 ? 33554432 : i <= 0 ? 1 : i + 1;
/*  878 */       LinkedBlockingQueue.Node localNode1; if ((!this.exhausted) && (((localNode1 = this.current) != null) || ((localNode1 = localLinkedBlockingQueue.head.next) != null)) && (localNode1.next != null))
/*      */       {
/*      */ 
/*  881 */         Object[] arrayOfObject = new Object[j];
/*  882 */         int k = 0;
/*  883 */         LinkedBlockingQueue.Node localNode2 = this.current;
/*  884 */         localLinkedBlockingQueue.fullyLock();
/*      */         try {
/*  886 */           if ((localNode2 != null) || ((localNode2 = localLinkedBlockingQueue.head.next) != null)) {
/*      */             do {
/*  888 */               if ((arrayOfObject[k] = localNode2.item) != null)
/*  889 */                 k++;
/*  890 */               if ((localNode2 = localNode2.next) == null) break; } while (k < j);
/*      */           }
/*      */         } finally {
/*  893 */           localLinkedBlockingQueue.fullyUnlock();
/*      */         }
/*  895 */         if ((this.current = localNode2) == null) {
/*  896 */           this.est = 0L;
/*  897 */           this.exhausted = true;
/*      */         }
/*  899 */         else if (this.est -= k < 0L) {
/*  900 */           this.est = 0L; }
/*  901 */         if (k > 0) {
/*  902 */           this.batch = k;
/*      */           
/*  904 */           return Spliterators.spliterator(arrayOfObject, 0, k, 4368);
/*      */         }
/*      */       }
/*      */       
/*  908 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer) {
/*  912 */       if (paramConsumer == null) throw new NullPointerException();
/*  913 */       LinkedBlockingQueue localLinkedBlockingQueue = this.queue;
/*  914 */       if (!this.exhausted) {
/*  915 */         this.exhausted = true;
/*  916 */         LinkedBlockingQueue.Node localNode = this.current;
/*      */         do {
/*  918 */           Object localObject1 = null;
/*  919 */           localLinkedBlockingQueue.fullyLock();
/*      */           try {
/*  921 */             if (localNode == null)
/*  922 */               localNode = localLinkedBlockingQueue.head.next;
/*  923 */             while (localNode != null) {
/*  924 */               localObject1 = localNode.item;
/*  925 */               localNode = localNode.next;
/*  926 */               if (localObject1 != null)
/*      */                 break;
/*      */             }
/*      */           } finally {
/*  930 */             localLinkedBlockingQueue.fullyUnlock();
/*      */           }
/*  932 */           if (localObject1 != null)
/*  933 */             paramConsumer.accept(localObject1);
/*  934 */         } while (localNode != null);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer) {
/*  939 */       if (paramConsumer == null) throw new NullPointerException();
/*  940 */       LinkedBlockingQueue localLinkedBlockingQueue = this.queue;
/*  941 */       if (!this.exhausted) {
/*  942 */         Object localObject1 = null;
/*  943 */         localLinkedBlockingQueue.fullyLock();
/*      */         try {
/*  945 */           if (this.current == null)
/*  946 */             this.current = localLinkedBlockingQueue.head.next;
/*  947 */           while (this.current != null) {
/*  948 */             localObject1 = this.current.item;
/*  949 */             this.current = this.current.next;
/*  950 */             if (localObject1 != null)
/*      */               break;
/*      */           }
/*      */         } finally {
/*  954 */           localLinkedBlockingQueue.fullyUnlock();
/*      */         }
/*  956 */         if (this.current == null)
/*  957 */           this.exhausted = true;
/*  958 */         if (localObject1 != null) {
/*  959 */           paramConsumer.accept(localObject1);
/*  960 */           return true;
/*      */         }
/*      */       }
/*  963 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  967 */       return 4368;
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/*  989 */     return new LBQSpliterator(this);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void writeObject(java.io.ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 47	java/util/concurrent/LinkedBlockingQueue:fullyLock	()V
/*      */     //   4: aload_1
/*      */     //   5: invokevirtual 72	java/io/ObjectOutputStream:defaultWriteObject	()V
/*      */     //   8: aload_0
/*      */     //   9: getfield 10	java/util/concurrent/LinkedBlockingQueue:head	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   12: getfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   15: astore_2
/*      */     //   16: aload_2
/*      */     //   17: ifnull +19 -> 36
/*      */     //   20: aload_1
/*      */     //   21: aload_2
/*      */     //   22: getfield 11	java/util/concurrent/LinkedBlockingQueue$Node:item	Ljava/lang/Object;
/*      */     //   25: invokevirtual 73	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
/*      */     //   28: aload_2
/*      */     //   29: getfield 9	java/util/concurrent/LinkedBlockingQueue$Node:next	Ljava/util/concurrent/LinkedBlockingQueue$Node;
/*      */     //   32: astore_2
/*      */     //   33: goto -17 -> 16
/*      */     //   36: aload_1
/*      */     //   37: aconst_null
/*      */     //   38: invokevirtual 73	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
/*      */     //   41: aload_0
/*      */     //   42: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   45: goto +10 -> 55
/*      */     //   48: astore_3
/*      */     //   49: aload_0
/*      */     //   50: invokevirtual 50	java/util/concurrent/LinkedBlockingQueue:fullyUnlock	()V
/*      */     //   53: aload_3
/*      */     //   54: athrow
/*      */     //   55: return
/*      */     // Line number table:
/*      */     //   Java source line #1004	-> byte code offset #0
/*      */     //   Java source line #1007	-> byte code offset #4
/*      */     //   Java source line #1010	-> byte code offset #8
/*      */     //   Java source line #1011	-> byte code offset #20
/*      */     //   Java source line #1010	-> byte code offset #28
/*      */     //   Java source line #1014	-> byte code offset #36
/*      */     //   Java source line #1016	-> byte code offset #41
/*      */     //   Java source line #1017	-> byte code offset #45
/*      */     //   Java source line #1016	-> byte code offset #48
/*      */     //   Java source line #1018	-> byte code offset #55
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	56	0	this	LinkedBlockingQueue
/*      */     //   0	56	1	paramObjectOutputStream	java.io.ObjectOutputStream
/*      */     //   15	18	2	localNode	Node
/*      */     //   48	6	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   4	41	48	finally
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1030 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1032 */     this.count.set(0);
/* 1033 */     this.last = (this.head = new Node(null));
/*      */     
/*      */ 
/*      */     for (;;)
/*      */     {
/* 1038 */       Object localObject = paramObjectInputStream.readObject();
/* 1039 */       if (localObject == null)
/*      */         break;
/* 1041 */       add(localObject);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/LinkedBlockingQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */