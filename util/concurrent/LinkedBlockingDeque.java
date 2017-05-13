/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinkedBlockingDeque<E>
/*      */   extends AbstractQueue<E>
/*      */   implements BlockingDeque<E>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -387911632671998426L;
/*      */   transient Node<E> first;
/*      */   transient Node<E> last;
/*      */   private transient int count;
/*      */   private final int capacity;
/*      */   
/*      */   static final class Node<E>
/*      */   {
/*      */     E item;
/*      */     Node<E> prev;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  158 */   final ReentrantLock lock = new ReentrantLock();
/*      */   
/*      */ 
/*  161 */   private final Condition notEmpty = this.lock.newCondition();
/*      */   
/*      */ 
/*  164 */   private final Condition notFull = this.lock.newCondition();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedBlockingDeque()
/*      */   {
/*  171 */     this(Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedBlockingDeque(int paramInt)
/*      */   {
/*  181 */     if (paramInt <= 0) throw new IllegalArgumentException();
/*  182 */     this.capacity = paramInt;
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
/*      */   public LinkedBlockingDeque(Collection<? extends E> paramCollection)
/*      */   {
/*  196 */     this(Integer.MAX_VALUE);
/*  197 */     ReentrantLock localReentrantLock = this.lock;
/*  198 */     localReentrantLock.lock();
/*      */     try {
/*  200 */       for (Object localObject1 : paramCollection) {
/*  201 */         if (localObject1 == null)
/*  202 */           throw new NullPointerException();
/*  203 */         if (!linkLast(new Node(localObject1)))
/*  204 */           throw new IllegalStateException("Deque full");
/*      */       }
/*      */     } finally {
/*  207 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean linkFirst(Node<E> paramNode)
/*      */   {
/*  219 */     if (this.count >= this.capacity)
/*  220 */       return false;
/*  221 */     Node localNode = this.first;
/*  222 */     paramNode.next = localNode;
/*  223 */     this.first = paramNode;
/*  224 */     if (this.last == null) {
/*  225 */       this.last = paramNode;
/*      */     } else
/*  227 */       localNode.prev = paramNode;
/*  228 */     this.count += 1;
/*  229 */     this.notEmpty.signal();
/*  230 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean linkLast(Node<E> paramNode)
/*      */   {
/*  238 */     if (this.count >= this.capacity)
/*  239 */       return false;
/*  240 */     Node localNode = this.last;
/*  241 */     paramNode.prev = localNode;
/*  242 */     this.last = paramNode;
/*  243 */     if (this.first == null) {
/*  244 */       this.first = paramNode;
/*      */     } else
/*  246 */       localNode.next = paramNode;
/*  247 */     this.count += 1;
/*  248 */     this.notEmpty.signal();
/*  249 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private E unlinkFirst()
/*      */   {
/*  257 */     Node localNode1 = this.first;
/*  258 */     if (localNode1 == null)
/*  259 */       return null;
/*  260 */     Node localNode2 = localNode1.next;
/*  261 */     Object localObject = localNode1.item;
/*  262 */     localNode1.item = null;
/*  263 */     localNode1.next = localNode1;
/*  264 */     this.first = localNode2;
/*  265 */     if (localNode2 == null) {
/*  266 */       this.last = null;
/*      */     } else
/*  268 */       localNode2.prev = null;
/*  269 */     this.count -= 1;
/*  270 */     this.notFull.signal();
/*  271 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private E unlinkLast()
/*      */   {
/*  279 */     Node localNode1 = this.last;
/*  280 */     if (localNode1 == null)
/*  281 */       return null;
/*  282 */     Node localNode2 = localNode1.prev;
/*  283 */     Object localObject = localNode1.item;
/*  284 */     localNode1.item = null;
/*  285 */     localNode1.prev = localNode1;
/*  286 */     this.last = localNode2;
/*  287 */     if (localNode2 == null) {
/*  288 */       this.first = null;
/*      */     } else
/*  290 */       localNode2.next = null;
/*  291 */     this.count -= 1;
/*  292 */     this.notFull.signal();
/*  293 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void unlink(Node<E> paramNode)
/*      */   {
/*  301 */     Node localNode1 = paramNode.prev;
/*  302 */     Node localNode2 = paramNode.next;
/*  303 */     if (localNode1 == null) {
/*  304 */       unlinkFirst();
/*  305 */     } else if (localNode2 == null) {
/*  306 */       unlinkLast();
/*      */     } else {
/*  308 */       localNode1.next = localNode2;
/*  309 */       localNode2.prev = localNode1;
/*  310 */       paramNode.item = null;
/*      */       
/*      */ 
/*  313 */       this.count -= 1;
/*  314 */       this.notFull.signal();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addFirst(E paramE)
/*      */   {
/*  325 */     if (!offerFirst(paramE)) {
/*  326 */       throw new IllegalStateException("Deque full");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void addLast(E paramE)
/*      */   {
/*  334 */     if (!offerLast(paramE)) {
/*  335 */       throw new IllegalStateException("Deque full");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean offerFirst(E paramE)
/*      */   {
/*  342 */     if (paramE == null) throw new NullPointerException();
/*  343 */     Node localNode = new Node(paramE);
/*  344 */     ReentrantLock localReentrantLock = this.lock;
/*  345 */     localReentrantLock.lock();
/*      */     try {
/*  347 */       return linkFirst(localNode);
/*      */     } finally {
/*  349 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean offerLast(E paramE)
/*      */   {
/*  357 */     if (paramE == null) throw new NullPointerException();
/*  358 */     Node localNode = new Node(paramE);
/*  359 */     ReentrantLock localReentrantLock = this.lock;
/*  360 */     localReentrantLock.lock();
/*      */     try {
/*  362 */       return linkLast(localNode);
/*      */     } finally {
/*  364 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void putFirst(E paramE)
/*      */     throws InterruptedException
/*      */   {
/*  373 */     if (paramE == null) throw new NullPointerException();
/*  374 */     Node localNode = new Node(paramE);
/*  375 */     ReentrantLock localReentrantLock = this.lock;
/*  376 */     localReentrantLock.lock();
/*      */     try {
/*  378 */       while (!linkFirst(localNode))
/*  379 */         this.notFull.await();
/*      */     } finally {
/*  381 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void putLast(E paramE)
/*      */     throws InterruptedException
/*      */   {
/*  390 */     if (paramE == null) throw new NullPointerException();
/*  391 */     Node localNode = new Node(paramE);
/*  392 */     ReentrantLock localReentrantLock = this.lock;
/*  393 */     localReentrantLock.lock();
/*      */     try {
/*  395 */       while (!linkLast(localNode))
/*  396 */         this.notFull.await();
/*      */     } finally {
/*  398 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offerFirst(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  408 */     if (paramE == null) throw new NullPointerException();
/*  409 */     Node localNode = new Node(paramE);
/*  410 */     long l = paramTimeUnit.toNanos(paramLong);
/*  411 */     ReentrantLock localReentrantLock = this.lock;
/*  412 */     localReentrantLock.lockInterruptibly();
/*      */     try { boolean bool;
/*  414 */       while (!linkFirst(localNode)) {
/*  415 */         if (l <= 0L)
/*  416 */           return false;
/*  417 */         l = this.notFull.awaitNanos(l);
/*      */       }
/*  419 */       return true;
/*      */     } finally {
/*  421 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offerLast(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  431 */     if (paramE == null) throw new NullPointerException();
/*  432 */     Node localNode = new Node(paramE);
/*  433 */     long l = paramTimeUnit.toNanos(paramLong);
/*  434 */     ReentrantLock localReentrantLock = this.lock;
/*  435 */     localReentrantLock.lockInterruptibly();
/*      */     try { boolean bool;
/*  437 */       while (!linkLast(localNode)) {
/*  438 */         if (l <= 0L)
/*  439 */           return false;
/*  440 */         l = this.notFull.awaitNanos(l);
/*      */       }
/*  442 */       return true;
/*      */     } finally {
/*  444 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E removeFirst()
/*      */   {
/*  452 */     Object localObject = pollFirst();
/*  453 */     if (localObject == null) throw new NoSuchElementException();
/*  454 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E removeLast()
/*      */   {
/*  461 */     Object localObject = pollLast();
/*  462 */     if (localObject == null) throw new NoSuchElementException();
/*  463 */     return (E)localObject;
/*      */   }
/*      */   
/*      */   public E pollFirst() {
/*  467 */     ReentrantLock localReentrantLock = this.lock;
/*  468 */     localReentrantLock.lock();
/*      */     try {
/*  470 */       return (E)unlinkFirst();
/*      */     } finally {
/*  472 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E pollLast() {
/*  477 */     ReentrantLock localReentrantLock = this.lock;
/*  478 */     localReentrantLock.lock();
/*      */     try {
/*  480 */       return (E)unlinkLast();
/*      */     } finally {
/*  482 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E takeFirst() throws InterruptedException {
/*  487 */     ReentrantLock localReentrantLock = this.lock;
/*  488 */     localReentrantLock.lock();
/*      */     try {
/*      */       Object localObject1;
/*  491 */       while ((localObject1 = unlinkFirst()) == null)
/*  492 */         this.notEmpty.await();
/*  493 */       return (E)localObject1;
/*      */     } finally {
/*  495 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E takeLast() throws InterruptedException {
/*  500 */     ReentrantLock localReentrantLock = this.lock;
/*  501 */     localReentrantLock.lock();
/*      */     try {
/*      */       Object localObject1;
/*  504 */       while ((localObject1 = unlinkLast()) == null)
/*  505 */         this.notEmpty.await();
/*  506 */       return (E)localObject1;
/*      */     } finally {
/*  508 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E pollFirst(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
/*      */   {
/*  514 */     long l = paramTimeUnit.toNanos(paramLong);
/*  515 */     ReentrantLock localReentrantLock = this.lock;
/*  516 */     localReentrantLock.lockInterruptibly();
/*      */     try { Object localObject1;
/*      */       Object localObject2;
/*  519 */       while ((localObject1 = unlinkFirst()) == null) {
/*  520 */         if (l <= 0L)
/*  521 */           return null;
/*  522 */         l = this.notEmpty.awaitNanos(l);
/*      */       }
/*  524 */       return (E)localObject1;
/*      */     } finally {
/*  526 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E pollLast(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
/*      */   {
/*  532 */     long l = paramTimeUnit.toNanos(paramLong);
/*  533 */     ReentrantLock localReentrantLock = this.lock;
/*  534 */     localReentrantLock.lockInterruptibly();
/*      */     try { Object localObject1;
/*      */       Object localObject2;
/*  537 */       while ((localObject1 = unlinkLast()) == null) {
/*  538 */         if (l <= 0L)
/*  539 */           return null;
/*  540 */         l = this.notEmpty.awaitNanos(l);
/*      */       }
/*  542 */       return (E)localObject1;
/*      */     } finally {
/*  544 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E getFirst()
/*      */   {
/*  552 */     Object localObject = peekFirst();
/*  553 */     if (localObject == null) throw new NoSuchElementException();
/*  554 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E getLast()
/*      */   {
/*  561 */     Object localObject = peekLast();
/*  562 */     if (localObject == null) throw new NoSuchElementException();
/*  563 */     return (E)localObject;
/*      */   }
/*      */   
/*      */   public E peekFirst() {
/*  567 */     ReentrantLock localReentrantLock = this.lock;
/*  568 */     localReentrantLock.lock();
/*      */     try {
/*  570 */       return this.first == null ? null : this.first.item;
/*      */     } finally {
/*  572 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public E peekLast() {
/*  577 */     ReentrantLock localReentrantLock = this.lock;
/*  578 */     localReentrantLock.lock();
/*      */     try {
/*  580 */       return this.last == null ? null : this.last.item;
/*      */     } finally {
/*  582 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean removeFirstOccurrence(Object paramObject) {
/*  587 */     if (paramObject == null) return false;
/*  588 */     ReentrantLock localReentrantLock = this.lock;
/*  589 */     localReentrantLock.lock();
/*      */     try {
/*  591 */       for (Node localNode = this.first; localNode != null; localNode = localNode.next) {
/*  592 */         if (paramObject.equals(localNode.item)) {
/*  593 */           unlink(localNode);
/*  594 */           return true;
/*      */         }
/*      */       }
/*  597 */       return false;
/*      */     } finally {
/*  599 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean removeLastOccurrence(Object paramObject) {
/*  604 */     if (paramObject == null) return false;
/*  605 */     ReentrantLock localReentrantLock = this.lock;
/*  606 */     localReentrantLock.lock();
/*      */     try {
/*  608 */       for (Node localNode = this.last; localNode != null; localNode = localNode.prev) {
/*  609 */         if (paramObject.equals(localNode.item)) {
/*  610 */           unlink(localNode);
/*  611 */           return true;
/*      */         }
/*      */       }
/*  614 */       return false;
/*      */     } finally {
/*  616 */       localReentrantLock.unlock();
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
/*      */   public boolean add(E paramE)
/*      */   {
/*  633 */     addLast(paramE);
/*  634 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean offer(E paramE)
/*      */   {
/*  641 */     return offerLast(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void put(E paramE)
/*      */     throws InterruptedException
/*      */   {
/*  649 */     putLast(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/*  658 */     return offerLast(paramE, paramLong, paramTimeUnit);
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
/*      */   public E remove()
/*      */   {
/*  672 */     return (E)removeFirst();
/*      */   }
/*      */   
/*      */   public E poll() {
/*  676 */     return (E)pollFirst();
/*      */   }
/*      */   
/*      */   public E take() throws InterruptedException {
/*  680 */     return (E)takeFirst();
/*      */   }
/*      */   
/*      */   public E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*  684 */     return (E)pollFirst(paramLong, paramTimeUnit);
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
/*      */   public E element()
/*      */   {
/*  698 */     return (E)getFirst();
/*      */   }
/*      */   
/*      */   public E peek() {
/*  702 */     return (E)peekFirst();
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
/*      */   public int remainingCapacity()
/*      */   {
/*  717 */     ReentrantLock localReentrantLock = this.lock;
/*  718 */     localReentrantLock.lock();
/*      */     try {
/*  720 */       return this.capacity - this.count;
/*      */     } finally {
/*  722 */       localReentrantLock.unlock();
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
/*  733 */     return drainTo(paramCollection, Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*      */   {
/*  743 */     if (paramCollection == null)
/*  744 */       throw new NullPointerException();
/*  745 */     if (paramCollection == this)
/*  746 */       throw new IllegalArgumentException();
/*  747 */     if (paramInt <= 0)
/*  748 */       return 0;
/*  749 */     ReentrantLock localReentrantLock = this.lock;
/*  750 */     localReentrantLock.lock();
/*      */     try {
/*  752 */       int i = Math.min(paramInt, this.count);
/*  753 */       for (int j = 0; j < i; j++) {
/*  754 */         paramCollection.add(this.first.item);
/*  755 */         unlinkFirst();
/*      */       }
/*  757 */       return i;
/*      */     } finally {
/*  759 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void push(E paramE)
/*      */   {
/*  770 */     addFirst(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public E pop()
/*      */   {
/*  777 */     return (E)removeFirst();
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
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*  797 */     return removeFirstOccurrence(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  806 */     ReentrantLock localReentrantLock = this.lock;
/*  807 */     localReentrantLock.lock();
/*      */     try {
/*  809 */       return this.count;
/*      */     } finally {
/*  811 */       localReentrantLock.unlock();
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
/*  824 */     if (paramObject == null) return false;
/*  825 */     ReentrantLock localReentrantLock = this.lock;
/*  826 */     localReentrantLock.lock();
/*      */     try {
/*  828 */       for (Node localNode = this.first; localNode != null; localNode = localNode.next)
/*  829 */         if (paramObject.equals(localNode.item))
/*  830 */           return true;
/*  831 */       return false;
/*      */     } finally {
/*  833 */       localReentrantLock.unlock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  893 */     ReentrantLock localReentrantLock = this.lock;
/*  894 */     localReentrantLock.lock();
/*      */     try {
/*  896 */       Object[] arrayOfObject = new Object[this.count];
/*  897 */       int i = 0;
/*  898 */       for (Object localObject1 = this.first; localObject1 != null; localObject1 = ((Node)localObject1).next)
/*  899 */         arrayOfObject[(i++)] = ((Node)localObject1).item;
/*  900 */       return arrayOfObject;
/*      */     } finally {
/*  902 */       localReentrantLock.unlock();
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
/*  943 */     ReentrantLock localReentrantLock = this.lock;
/*  944 */     localReentrantLock.lock();
/*      */     try {
/*  946 */       if (paramArrayOfT.length < this.count)
/*      */       {
/*  948 */         paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.count);
/*      */       }
/*  950 */       int i = 0;
/*  951 */       for (Object localObject1 = this.first; localObject1 != null; localObject1 = ((Node)localObject1).next)
/*  952 */         paramArrayOfT[(i++)] = ((Node)localObject1).item;
/*  953 */       if (paramArrayOfT.length > i)
/*  954 */         paramArrayOfT[i] = null;
/*  955 */       return paramArrayOfT;
/*      */     } finally {
/*  957 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public String toString()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 7	java/util/concurrent/LinkedBlockingDeque:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */     //   4: astore_1
/*      */     //   5: aload_1
/*      */     //   6: invokevirtual 14	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */     //   9: aload_0
/*      */     //   10: getfield 28	java/util/concurrent/LinkedBlockingDeque:first	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */     //   13: astore_2
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull +12 -> 27
/*      */     //   18: ldc 68
/*      */     //   20: astore_3
/*      */     //   21: aload_1
/*      */     //   22: invokevirtual 26	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   25: aload_3
/*      */     //   26: areturn
/*      */     //   27: new 69	java/lang/StringBuilder
/*      */     //   30: dup
/*      */     //   31: invokespecial 70	java/lang/StringBuilder:<init>	()V
/*      */     //   34: astore_3
/*      */     //   35: aload_3
/*      */     //   36: bipush 91
/*      */     //   38: invokevirtual 71	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   41: pop
/*      */     //   42: aload_2
/*      */     //   43: getfield 33	java/util/concurrent/LinkedBlockingDeque$Node:item	Ljava/lang/Object;
/*      */     //   46: astore 4
/*      */     //   48: aload_3
/*      */     //   49: aload 4
/*      */     //   51: aload_0
/*      */     //   52: if_acmpne +8 -> 60
/*      */     //   55: ldc 72
/*      */     //   57: goto +5 -> 62
/*      */     //   60: aload 4
/*      */     //   62: invokevirtual 73	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   65: pop
/*      */     //   66: aload_2
/*      */     //   67: getfield 29	java/util/concurrent/LinkedBlockingDeque$Node:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */     //   70: astore_2
/*      */     //   71: aload_2
/*      */     //   72: ifnonnull +21 -> 93
/*      */     //   75: aload_3
/*      */     //   76: bipush 93
/*      */     //   78: invokevirtual 71	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   81: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   84: astore 5
/*      */     //   86: aload_1
/*      */     //   87: invokevirtual 26	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   90: aload 5
/*      */     //   92: areturn
/*      */     //   93: aload_3
/*      */     //   94: bipush 44
/*      */     //   96: invokevirtual 71	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   99: bipush 32
/*      */     //   101: invokevirtual 71	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*      */     //   104: pop
/*      */     //   105: goto -63 -> 42
/*      */     //   108: astore 6
/*      */     //   110: aload_1
/*      */     //   111: invokevirtual 26	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */     //   114: aload 6
/*      */     //   116: athrow
/*      */     // Line number table:
/*      */     //   Java source line #962	-> byte code offset #0
/*      */     //   Java source line #963	-> byte code offset #5
/*      */     //   Java source line #965	-> byte code offset #9
/*      */     //   Java source line #966	-> byte code offset #14
/*      */     //   Java source line #967	-> byte code offset #18
/*      */     //   Java source line #980	-> byte code offset #21
/*      */     //   Java source line #969	-> byte code offset #27
/*      */     //   Java source line #970	-> byte code offset #35
/*      */     //   Java source line #972	-> byte code offset #42
/*      */     //   Java source line #973	-> byte code offset #48
/*      */     //   Java source line #974	-> byte code offset #66
/*      */     //   Java source line #975	-> byte code offset #71
/*      */     //   Java source line #976	-> byte code offset #75
/*      */     //   Java source line #980	-> byte code offset #86
/*      */     //   Java source line #977	-> byte code offset #93
/*      */     //   Java source line #978	-> byte code offset #105
/*      */     //   Java source line #980	-> byte code offset #108
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	117	0	this	LinkedBlockingDeque
/*      */     //   4	107	1	localReentrantLock	ReentrantLock
/*      */     //   13	59	2	localNode	Node
/*      */     //   20	74	3	localObject1	Object
/*      */     //   46	15	4	localObject2	Object
/*      */     //   84	7	5	str	String
/*      */     //   108	7	6	localObject3	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	21	108	finally
/*      */     //   27	86	108	finally
/*      */     //   93	110	108	finally
/*      */   }
/*      */   
/*      */   public void clear()
/*      */   {
/*  989 */     ReentrantLock localReentrantLock = this.lock;
/*  990 */     localReentrantLock.lock();
/*      */     try {
/*  992 */       for (Object localObject1 = this.first; localObject1 != null;) {
/*  993 */         ((Node)localObject1).item = null;
/*  994 */         Node localNode = ((Node)localObject1).next;
/*  995 */         ((Node)localObject1).prev = null;
/*  996 */         ((Node)localObject1).next = null;
/*  997 */         localObject1 = localNode;
/*      */       }
/*  999 */       this.first = (this.last = null);
/* 1000 */       this.count = 0;
/* 1001 */       this.notFull.signalAll();
/*      */     } finally {
/* 1003 */       localReentrantLock.unlock();
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
/* 1017 */     return new Itr(null);
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
/*      */   public Iterator<E> descendingIterator()
/*      */   {
/* 1031 */     return new DescendingItr(null);
/*      */   }
/*      */   
/*      */   private abstract class AbstractItr
/*      */     implements Iterator<E>
/*      */   {
/*      */     LinkedBlockingDeque.Node<E> next;
/*      */     E nextItem;
/*      */     private LinkedBlockingDeque.Node<E> lastRet;
/*      */     
/*      */     abstract LinkedBlockingDeque.Node<E> firstNode();
/*      */     
/*      */     abstract LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode);
/*      */     
/*      */     /* Error */
/*      */     AbstractItr()
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: putfield 1	java/util/concurrent/LinkedBlockingDeque$AbstractItr:this$0	Ljava/util/concurrent/LinkedBlockingDeque;
/*      */       //   5: aload_0
/*      */       //   6: invokespecial 2	java/lang/Object:<init>	()V
/*      */       //   9: aload_1
/*      */       //   10: getfield 3	java/util/concurrent/LinkedBlockingDeque:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   13: astore_2
/*      */       //   14: aload_2
/*      */       //   15: invokevirtual 4	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */       //   18: aload_0
/*      */       //   19: aload_0
/*      */       //   20: invokevirtual 5	java/util/concurrent/LinkedBlockingDeque$AbstractItr:firstNode	()Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   23: putfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   26: aload_0
/*      */       //   27: aload_0
/*      */       //   28: getfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   31: ifnonnull +7 -> 38
/*      */       //   34: aconst_null
/*      */       //   35: goto +10 -> 45
/*      */       //   38: aload_0
/*      */       //   39: getfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   42: getfield 7	java/util/concurrent/LinkedBlockingDeque$Node:item	Ljava/lang/Object;
/*      */       //   45: putfield 8	java/util/concurrent/LinkedBlockingDeque$AbstractItr:nextItem	Ljava/lang/Object;
/*      */       //   48: aload_2
/*      */       //   49: invokevirtual 9	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   52: goto +10 -> 62
/*      */       //   55: astore_3
/*      */       //   56: aload_2
/*      */       //   57: invokevirtual 9	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   60: aload_3
/*      */       //   61: athrow
/*      */       //   62: return
/*      */       // Line number table:
/*      */       //   Java source line #1060	-> byte code offset #0
/*      */       //   Java source line #1062	-> byte code offset #9
/*      */       //   Java source line #1063	-> byte code offset #14
/*      */       //   Java source line #1065	-> byte code offset #18
/*      */       //   Java source line #1066	-> byte code offset #26
/*      */       //   Java source line #1068	-> byte code offset #48
/*      */       //   Java source line #1069	-> byte code offset #52
/*      */       //   Java source line #1068	-> byte code offset #55
/*      */       //   Java source line #1070	-> byte code offset #62
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	63	0	this	AbstractItr
/*      */       //   0	63	1	this$1	LinkedBlockingDeque
/*      */       //   13	44	2	localReentrantLock	ReentrantLock
/*      */       //   55	6	3	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   18	48	55	finally
/*      */     }
/*      */     
/*      */     private LinkedBlockingDeque.Node<E> succ(LinkedBlockingDeque.Node<E> paramNode)
/*      */     {
/*      */       for (;;)
/*      */       {
/* 1080 */         LinkedBlockingDeque.Node localNode = nextNode(paramNode);
/* 1081 */         if (localNode == null)
/* 1082 */           return null;
/* 1083 */         if (localNode.item != null)
/* 1084 */           return localNode;
/* 1085 */         if (localNode == paramNode) {
/* 1086 */           return firstNode();
/*      */         }
/* 1088 */         paramNode = localNode;
/*      */       }
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     void advance()
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield 1	java/util/concurrent/LinkedBlockingDeque$AbstractItr:this$0	Ljava/util/concurrent/LinkedBlockingDeque;
/*      */       //   4: getfield 3	java/util/concurrent/LinkedBlockingDeque:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   7: astore_1
/*      */       //   8: aload_1
/*      */       //   9: invokevirtual 4	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */       //   12: aload_0
/*      */       //   13: aload_0
/*      */       //   14: aload_0
/*      */       //   15: getfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   18: invokespecial 11	java/util/concurrent/LinkedBlockingDeque$AbstractItr:succ	(Ljava/util/concurrent/LinkedBlockingDeque$Node;)Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   21: putfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   24: aload_0
/*      */       //   25: aload_0
/*      */       //   26: getfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   29: ifnonnull +7 -> 36
/*      */       //   32: aconst_null
/*      */       //   33: goto +10 -> 43
/*      */       //   36: aload_0
/*      */       //   37: getfield 6	java/util/concurrent/LinkedBlockingDeque$AbstractItr:next	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   40: getfield 7	java/util/concurrent/LinkedBlockingDeque$Node:item	Ljava/lang/Object;
/*      */       //   43: putfield 8	java/util/concurrent/LinkedBlockingDeque$AbstractItr:nextItem	Ljava/lang/Object;
/*      */       //   46: aload_1
/*      */       //   47: invokevirtual 9	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   50: goto +10 -> 60
/*      */       //   53: astore_2
/*      */       //   54: aload_1
/*      */       //   55: invokevirtual 9	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   58: aload_2
/*      */       //   59: athrow
/*      */       //   60: return
/*      */       // Line number table:
/*      */       //   Java source line #1096	-> byte code offset #0
/*      */       //   Java source line #1097	-> byte code offset #8
/*      */       //   Java source line #1100	-> byte code offset #12
/*      */       //   Java source line #1101	-> byte code offset #24
/*      */       //   Java source line #1103	-> byte code offset #46
/*      */       //   Java source line #1104	-> byte code offset #50
/*      */       //   Java source line #1103	-> byte code offset #53
/*      */       //   Java source line #1105	-> byte code offset #60
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	61	0	this	AbstractItr
/*      */       //   7	48	1	localReentrantLock	ReentrantLock
/*      */       //   53	6	2	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   12	46	53	finally
/*      */     }
/*      */     
/*      */     public boolean hasNext()
/*      */     {
/* 1108 */       return this.next != null;
/*      */     }
/*      */     
/*      */     public E next() {
/* 1112 */       if (this.next == null)
/* 1113 */         throw new NoSuchElementException();
/* 1114 */       this.lastRet = this.next;
/* 1115 */       Object localObject = this.nextItem;
/* 1116 */       advance();
/* 1117 */       return (E)localObject;
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     public void remove()
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield 14	java/util/concurrent/LinkedBlockingDeque$AbstractItr:lastRet	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   4: astore_1
/*      */       //   5: aload_1
/*      */       //   6: ifnonnull +11 -> 17
/*      */       //   9: new 16	java/lang/IllegalStateException
/*      */       //   12: dup
/*      */       //   13: invokespecial 17	java/lang/IllegalStateException:<init>	()V
/*      */       //   16: athrow
/*      */       //   17: aload_0
/*      */       //   18: aconst_null
/*      */       //   19: putfield 14	java/util/concurrent/LinkedBlockingDeque$AbstractItr:lastRet	Ljava/util/concurrent/LinkedBlockingDeque$Node;
/*      */       //   22: aload_0
/*      */       //   23: getfield 1	java/util/concurrent/LinkedBlockingDeque$AbstractItr:this$0	Ljava/util/concurrent/LinkedBlockingDeque;
/*      */       //   26: getfield 3	java/util/concurrent/LinkedBlockingDeque:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*      */       //   29: astore_2
/*      */       //   30: aload_2
/*      */       //   31: invokevirtual 4	java/util/concurrent/locks/ReentrantLock:lock	()V
/*      */       //   34: aload_1
/*      */       //   35: getfield 7	java/util/concurrent/LinkedBlockingDeque$Node:item	Ljava/lang/Object;
/*      */       //   38: ifnull +11 -> 49
/*      */       //   41: aload_0
/*      */       //   42: getfield 1	java/util/concurrent/LinkedBlockingDeque$AbstractItr:this$0	Ljava/util/concurrent/LinkedBlockingDeque;
/*      */       //   45: aload_1
/*      */       //   46: invokevirtual 18	java/util/concurrent/LinkedBlockingDeque:unlink	(Ljava/util/concurrent/LinkedBlockingDeque$Node;)V
/*      */       //   49: aload_2
/*      */       //   50: invokevirtual 9	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   53: goto +10 -> 63
/*      */       //   56: astore_3
/*      */       //   57: aload_2
/*      */       //   58: invokevirtual 9	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*      */       //   61: aload_3
/*      */       //   62: athrow
/*      */       //   63: return
/*      */       // Line number table:
/*      */       //   Java source line #1121	-> byte code offset #0
/*      */       //   Java source line #1122	-> byte code offset #5
/*      */       //   Java source line #1123	-> byte code offset #9
/*      */       //   Java source line #1124	-> byte code offset #17
/*      */       //   Java source line #1125	-> byte code offset #22
/*      */       //   Java source line #1126	-> byte code offset #30
/*      */       //   Java source line #1128	-> byte code offset #34
/*      */       //   Java source line #1129	-> byte code offset #41
/*      */       //   Java source line #1131	-> byte code offset #49
/*      */       //   Java source line #1132	-> byte code offset #53
/*      */       //   Java source line #1131	-> byte code offset #56
/*      */       //   Java source line #1133	-> byte code offset #63
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	64	0	this	AbstractItr
/*      */       //   4	42	1	localNode	LinkedBlockingDeque.Node
/*      */       //   29	29	2	localReentrantLock	ReentrantLock
/*      */       //   56	6	3	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   34	49	56	finally
/*      */     }
/*      */   }
/*      */   
/*      */   private class Itr
/*      */     extends LinkedBlockingDeque<E>.AbstractItr
/*      */   {
/* 1137 */     private Itr() { super(); }
/* 1138 */     LinkedBlockingDeque.Node<E> firstNode() { return LinkedBlockingDeque.this.first; }
/* 1139 */     LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode) { return paramNode.next; }
/*      */   }
/*      */   
/*      */   private class DescendingItr extends LinkedBlockingDeque<E>.AbstractItr {
/* 1143 */     private DescendingItr() { super(); }
/* 1144 */     LinkedBlockingDeque.Node<E> firstNode() { return LinkedBlockingDeque.this.last; }
/* 1145 */     LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode) { return paramNode.prev; }
/*      */   }
/*      */   
/*      */   static final class LBDSpliterator<E> implements Spliterator<E> {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedBlockingDeque<E> queue;
/*      */     LinkedBlockingDeque.Node<E> current;
/*      */     int batch;
/*      */     boolean exhausted;
/*      */     long est;
/*      */     
/*      */     LBDSpliterator(LinkedBlockingDeque<E> paramLinkedBlockingDeque) {
/* 1157 */       this.queue = paramLinkedBlockingDeque;
/* 1158 */       this.est = paramLinkedBlockingDeque.size();
/*      */     }
/*      */     
/* 1161 */     public long estimateSize() { return this.est; }
/*      */     
/*      */     public Spliterator<E> trySplit()
/*      */     {
/* 1165 */       LinkedBlockingDeque localLinkedBlockingDeque = this.queue;
/* 1166 */       int i = this.batch;
/* 1167 */       int j = i >= 33554432 ? 33554432 : i <= 0 ? 1 : i + 1;
/* 1168 */       LinkedBlockingDeque.Node localNode1; if ((!this.exhausted) && (((localNode1 = this.current) != null) || ((localNode1 = localLinkedBlockingDeque.first) != null)) && (localNode1.next != null))
/*      */       {
/*      */ 
/* 1171 */         Object[] arrayOfObject = new Object[j];
/* 1172 */         ReentrantLock localReentrantLock = localLinkedBlockingDeque.lock;
/* 1173 */         int k = 0;
/* 1174 */         LinkedBlockingDeque.Node localNode2 = this.current;
/* 1175 */         localReentrantLock.lock();
/*      */         try {
/* 1177 */           if ((localNode2 != null) || ((localNode2 = localLinkedBlockingDeque.first) != null)) {
/*      */             do {
/* 1179 */               if ((arrayOfObject[k] = localNode2.item) != null)
/* 1180 */                 k++;
/* 1181 */               if ((localNode2 = localNode2.next) == null) break; } while (k < j);
/*      */           }
/*      */         } finally {
/* 1184 */           localReentrantLock.unlock();
/*      */         }
/* 1186 */         if ((this.current = localNode2) == null) {
/* 1187 */           this.est = 0L;
/* 1188 */           this.exhausted = true;
/*      */         }
/* 1190 */         else if (this.est -= k < 0L) {
/* 1191 */           this.est = 0L; }
/* 1192 */         if (k > 0) {
/* 1193 */           this.batch = k;
/*      */           
/* 1195 */           return Spliterators.spliterator(arrayOfObject, 0, k, 4368);
/*      */         }
/*      */       }
/*      */       
/* 1199 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer) {
/* 1203 */       if (paramConsumer == null) throw new NullPointerException();
/* 1204 */       LinkedBlockingDeque localLinkedBlockingDeque = this.queue;
/* 1205 */       ReentrantLock localReentrantLock = localLinkedBlockingDeque.lock;
/* 1206 */       if (!this.exhausted) {
/* 1207 */         this.exhausted = true;
/* 1208 */         LinkedBlockingDeque.Node localNode = this.current;
/*      */         do {
/* 1210 */           Object localObject1 = null;
/* 1211 */           localReentrantLock.lock();
/*      */           try {
/* 1213 */             if (localNode == null)
/* 1214 */               localNode = localLinkedBlockingDeque.first;
/* 1215 */             while (localNode != null) {
/* 1216 */               localObject1 = localNode.item;
/* 1217 */               localNode = localNode.next;
/* 1218 */               if (localObject1 != null)
/*      */                 break;
/*      */             }
/*      */           } finally {
/* 1222 */             localReentrantLock.unlock();
/*      */           }
/* 1224 */           if (localObject1 != null)
/* 1225 */             paramConsumer.accept(localObject1);
/* 1226 */         } while (localNode != null);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer) {
/* 1231 */       if (paramConsumer == null) throw new NullPointerException();
/* 1232 */       LinkedBlockingDeque localLinkedBlockingDeque = this.queue;
/* 1233 */       ReentrantLock localReentrantLock = localLinkedBlockingDeque.lock;
/* 1234 */       if (!this.exhausted) {
/* 1235 */         Object localObject1 = null;
/* 1236 */         localReentrantLock.lock();
/*      */         try {
/* 1238 */           if (this.current == null)
/* 1239 */             this.current = localLinkedBlockingDeque.first;
/* 1240 */           while (this.current != null) {
/* 1241 */             localObject1 = this.current.item;
/* 1242 */             this.current = this.current.next;
/* 1243 */             if (localObject1 != null)
/*      */               break;
/*      */           }
/*      */         } finally {
/* 1247 */           localReentrantLock.unlock();
/*      */         }
/* 1249 */         if (this.current == null)
/* 1250 */           this.exhausted = true;
/* 1251 */         if (localObject1 != null) {
/* 1252 */           paramConsumer.accept(localObject1);
/* 1253 */           return true;
/*      */         }
/*      */       }
/* 1256 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1260 */       return 4368;
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
/* 1282 */     return new LBDSpliterator(this);
/*      */   }
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
/* 1295 */     ReentrantLock localReentrantLock = this.lock;
/* 1296 */     localReentrantLock.lock();
/*      */     try
/*      */     {
/* 1299 */       paramObjectOutputStream.defaultWriteObject();
/*      */       
/* 1301 */       for (Node localNode = this.first; localNode != null; localNode = localNode.next) {
/* 1302 */         paramObjectOutputStream.writeObject(localNode.item);
/*      */       }
/* 1304 */       paramObjectOutputStream.writeObject(null);
/*      */     } finally {
/* 1306 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
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
/* 1319 */     paramObjectInputStream.defaultReadObject();
/* 1320 */     this.count = 0;
/* 1321 */     this.first = null;
/* 1322 */     this.last = null;
/*      */     
/*      */     for (;;)
/*      */     {
/* 1326 */       Object localObject = paramObjectInputStream.readObject();
/* 1327 */       if (localObject == null)
/*      */         break;
/* 1329 */       add(localObject);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/LinkedBlockingDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */