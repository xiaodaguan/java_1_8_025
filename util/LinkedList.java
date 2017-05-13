/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
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
/*      */ public class LinkedList<E>
/*      */   extends AbstractSequentialList<E>
/*      */   implements List<E>, Deque<E>, Cloneable, Serializable
/*      */ {
/*   87 */   transient int size = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   transient Node<E> first;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   transient Node<E> last;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 876323262645176354L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedList() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedList(Collection<? extends E> paramCollection)
/*      */   {
/*  118 */     this();
/*  119 */     addAll(paramCollection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void linkFirst(E paramE)
/*      */   {
/*  126 */     Node localNode1 = this.first;
/*  127 */     Node localNode2 = new Node(null, paramE, localNode1);
/*  128 */     this.first = localNode2;
/*  129 */     if (localNode1 == null) {
/*  130 */       this.last = localNode2;
/*      */     } else
/*  132 */       localNode1.prev = localNode2;
/*  133 */     this.size += 1;
/*  134 */     this.modCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void linkLast(E paramE)
/*      */   {
/*  141 */     Node localNode1 = this.last;
/*  142 */     Node localNode2 = new Node(localNode1, paramE, null);
/*  143 */     this.last = localNode2;
/*  144 */     if (localNode1 == null) {
/*  145 */       this.first = localNode2;
/*      */     } else
/*  147 */       localNode1.next = localNode2;
/*  148 */     this.size += 1;
/*  149 */     this.modCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void linkBefore(E paramE, Node<E> paramNode)
/*      */   {
/*  157 */     Node localNode1 = paramNode.prev;
/*  158 */     Node localNode2 = new Node(localNode1, paramE, paramNode);
/*  159 */     paramNode.prev = localNode2;
/*  160 */     if (localNode1 == null) {
/*  161 */       this.first = localNode2;
/*      */     } else
/*  163 */       localNode1.next = localNode2;
/*  164 */     this.size += 1;
/*  165 */     this.modCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private E unlinkFirst(Node<E> paramNode)
/*      */   {
/*  173 */     Object localObject = paramNode.item;
/*  174 */     Node localNode = paramNode.next;
/*  175 */     paramNode.item = null;
/*  176 */     paramNode.next = null;
/*  177 */     this.first = localNode;
/*  178 */     if (localNode == null) {
/*  179 */       this.last = null;
/*      */     } else
/*  181 */       localNode.prev = null;
/*  182 */     this.size -= 1;
/*  183 */     this.modCount += 1;
/*  184 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private E unlinkLast(Node<E> paramNode)
/*      */   {
/*  192 */     Object localObject = paramNode.item;
/*  193 */     Node localNode = paramNode.prev;
/*  194 */     paramNode.item = null;
/*  195 */     paramNode.prev = null;
/*  196 */     this.last = localNode;
/*  197 */     if (localNode == null) {
/*  198 */       this.first = null;
/*      */     } else
/*  200 */       localNode.next = null;
/*  201 */     this.size -= 1;
/*  202 */     this.modCount += 1;
/*  203 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   E unlink(Node<E> paramNode)
/*      */   {
/*  211 */     Object localObject = paramNode.item;
/*  212 */     Node localNode1 = paramNode.next;
/*  213 */     Node localNode2 = paramNode.prev;
/*      */     
/*  215 */     if (localNode2 == null) {
/*  216 */       this.first = localNode1;
/*      */     } else {
/*  218 */       localNode2.next = localNode1;
/*  219 */       paramNode.prev = null;
/*      */     }
/*      */     
/*  222 */     if (localNode1 == null) {
/*  223 */       this.last = localNode2;
/*      */     } else {
/*  225 */       localNode1.prev = localNode2;
/*  226 */       paramNode.next = null;
/*      */     }
/*      */     
/*  229 */     paramNode.item = null;
/*  230 */     this.size -= 1;
/*  231 */     this.modCount += 1;
/*  232 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E getFirst()
/*      */   {
/*  242 */     Node localNode = this.first;
/*  243 */     if (localNode == null)
/*  244 */       throw new NoSuchElementException();
/*  245 */     return (E)localNode.item;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E getLast()
/*      */   {
/*  255 */     Node localNode = this.last;
/*  256 */     if (localNode == null)
/*  257 */       throw new NoSuchElementException();
/*  258 */     return (E)localNode.item;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E removeFirst()
/*      */   {
/*  268 */     Node localNode = this.first;
/*  269 */     if (localNode == null)
/*  270 */       throw new NoSuchElementException();
/*  271 */     return (E)unlinkFirst(localNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E removeLast()
/*      */   {
/*  281 */     Node localNode = this.last;
/*  282 */     if (localNode == null)
/*  283 */       throw new NoSuchElementException();
/*  284 */     return (E)unlinkLast(localNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addFirst(E paramE)
/*      */   {
/*  293 */     linkFirst(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addLast(E paramE)
/*      */   {
/*  304 */     linkLast(paramE);
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
/*      */   public boolean contains(Object paramObject)
/*      */   {
/*  317 */     return indexOf(paramObject) != -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  326 */     return this.size;
/*      */   }
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
/*  338 */     linkLast(paramE);
/*  339 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean remove(Object paramObject)
/*      */   {
/*      */     Node localNode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  356 */     if (paramObject == null) {
/*  357 */       for (localNode = this.first; localNode != null; localNode = localNode.next) {
/*  358 */         if (localNode.item == null) {
/*  359 */           unlink(localNode);
/*  360 */           return true;
/*      */         }
/*      */       }
/*      */     } else {
/*  364 */       for (localNode = this.first; localNode != null; localNode = localNode.next) {
/*  365 */         if (paramObject.equals(localNode.item)) {
/*  366 */           unlink(localNode);
/*  367 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*  371 */     return false;
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection)
/*      */   {
/*  387 */     return addAll(this.size, paramCollection);
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
/*      */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*      */   {
/*  406 */     checkPositionIndex(paramInt);
/*      */     
/*  408 */     Object[] arrayOfObject1 = paramCollection.toArray();
/*  409 */     int i = arrayOfObject1.length;
/*  410 */     if (i == 0)
/*  411 */       return false;
/*      */     Node localNode1;
/*      */     Object localObject1;
/*  414 */     if (paramInt == this.size) {
/*  415 */       localNode1 = null;
/*  416 */       localObject1 = this.last;
/*      */     } else {
/*  418 */       localNode1 = node(paramInt);
/*  419 */       localObject1 = localNode1.prev;
/*      */     }
/*      */     
/*  422 */     for (Object localObject2 : arrayOfObject1) {
/*  423 */       Object localObject3 = localObject2;
/*  424 */       Node localNode2 = new Node((Node)localObject1, localObject3, null);
/*  425 */       if (localObject1 == null) {
/*  426 */         this.first = localNode2;
/*      */       } else
/*  428 */         ((Node)localObject1).next = localNode2;
/*  429 */       localObject1 = localNode2;
/*      */     }
/*      */     
/*  432 */     if (localNode1 == null) {
/*  433 */       this.last = ((Node)localObject1);
/*      */     } else {
/*  435 */       ((Node)localObject1).next = localNode1;
/*  436 */       localNode1.prev = ((Node)localObject1);
/*      */     }
/*      */     
/*  439 */     this.size += i;
/*  440 */     this.modCount += 1;
/*  441 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  453 */     for (Object localObject = this.first; localObject != null;) {
/*  454 */       Node localNode = ((Node)localObject).next;
/*  455 */       ((Node)localObject).item = null;
/*  456 */       ((Node)localObject).next = null;
/*  457 */       ((Node)localObject).prev = null;
/*  458 */       localObject = localNode;
/*      */     }
/*  460 */     this.first = (this.last = null);
/*  461 */     this.size = 0;
/*  462 */     this.modCount += 1;
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
/*      */   public E get(int paramInt)
/*      */   {
/*  476 */     checkElementIndex(paramInt);
/*  477 */     return (E)node(paramInt).item;
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
/*      */   public E set(int paramInt, E paramE)
/*      */   {
/*  490 */     checkElementIndex(paramInt);
/*  491 */     Node localNode = node(paramInt);
/*  492 */     Object localObject = localNode.item;
/*  493 */     localNode.item = paramE;
/*  494 */     return (E)localObject;
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
/*      */   public void add(int paramInt, E paramE)
/*      */   {
/*  507 */     checkPositionIndex(paramInt);
/*      */     
/*  509 */     if (paramInt == this.size) {
/*  510 */       linkLast(paramE);
/*      */     } else {
/*  512 */       linkBefore(paramE, node(paramInt));
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
/*      */   public E remove(int paramInt)
/*      */   {
/*  525 */     checkElementIndex(paramInt);
/*  526 */     return (E)unlink(node(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isElementIndex(int paramInt)
/*      */   {
/*  533 */     return (paramInt >= 0) && (paramInt < this.size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isPositionIndex(int paramInt)
/*      */   {
/*  541 */     return (paramInt >= 0) && (paramInt <= this.size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String outOfBoundsMsg(int paramInt)
/*      */   {
/*  550 */     return "Index: " + paramInt + ", Size: " + this.size;
/*      */   }
/*      */   
/*      */   private void checkElementIndex(int paramInt) {
/*  554 */     if (!isElementIndex(paramInt))
/*  555 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */   }
/*      */   
/*      */   private void checkPositionIndex(int paramInt) {
/*  559 */     if (!isPositionIndex(paramInt)) {
/*  560 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Node<E> node(int paramInt)
/*      */   {
/*  569 */     if (paramInt < this.size >> 1) {
/*  570 */       localNode = this.first;
/*  571 */       for (i = 0; i < paramInt; i++)
/*  572 */         localNode = localNode.next;
/*  573 */       return localNode;
/*      */     }
/*  575 */     Node localNode = this.last;
/*  576 */     for (int i = this.size - 1; i > paramInt; i--)
/*  577 */       localNode = localNode.prev;
/*  578 */     return localNode;
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
/*      */   public int indexOf(Object paramObject)
/*      */   {
/*  596 */     int i = 0;
/*  597 */     Node localNode; if (paramObject == null) {
/*  598 */       for (localNode = this.first; localNode != null; localNode = localNode.next) {
/*  599 */         if (localNode.item == null)
/*  600 */           return i;
/*  601 */         i++;
/*      */       }
/*      */     } else {
/*  604 */       for (localNode = this.first; localNode != null; localNode = localNode.next) {
/*  605 */         if (paramObject.equals(localNode.item))
/*  606 */           return i;
/*  607 */         i++;
/*      */       }
/*      */     }
/*  610 */     return -1;
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
/*      */   public int lastIndexOf(Object paramObject)
/*      */   {
/*  625 */     int i = this.size;
/*  626 */     Node localNode; if (paramObject == null) {
/*  627 */       for (localNode = this.last; localNode != null; localNode = localNode.prev) {
/*  628 */         i--;
/*  629 */         if (localNode.item == null)
/*  630 */           return i;
/*      */       }
/*      */     } else {
/*  633 */       for (localNode = this.last; localNode != null; localNode = localNode.prev) {
/*  634 */         i--;
/*  635 */         if (paramObject.equals(localNode.item))
/*  636 */           return i;
/*      */       }
/*      */     }
/*  639 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E peek()
/*      */   {
/*  651 */     Node localNode = this.first;
/*  652 */     return localNode == null ? null : localNode.item;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E element()
/*      */   {
/*  663 */     return (E)getFirst();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E poll()
/*      */   {
/*  673 */     Node localNode = this.first;
/*  674 */     return localNode == null ? null : unlinkFirst(localNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E remove()
/*      */   {
/*  685 */     return (E)removeFirst();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offer(E paramE)
/*      */   {
/*  696 */     return add(paramE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offerFirst(E paramE)
/*      */   {
/*  708 */     addFirst(paramE);
/*  709 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean offerLast(E paramE)
/*      */   {
/*  720 */     addLast(paramE);
/*  721 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E peekFirst()
/*      */   {
/*  733 */     Node localNode = this.first;
/*  734 */     return localNode == null ? null : localNode.item;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E peekLast()
/*      */   {
/*  746 */     Node localNode = this.last;
/*  747 */     return localNode == null ? null : localNode.item;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E pollFirst()
/*      */   {
/*  759 */     Node localNode = this.first;
/*  760 */     return localNode == null ? null : unlinkFirst(localNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E pollLast()
/*      */   {
/*  772 */     Node localNode = this.last;
/*  773 */     return localNode == null ? null : unlinkLast(localNode);
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
/*      */   public void push(E paramE)
/*      */   {
/*  786 */     addFirst(paramE);
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
/*      */   public E pop()
/*      */   {
/*  801 */     return (E)removeFirst();
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
/*      */   public boolean removeFirstOccurrence(Object paramObject)
/*      */   {
/*  814 */     return remove(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean removeLastOccurrence(Object paramObject)
/*      */   {
/*      */     Node localNode;
/*      */     
/*      */ 
/*      */ 
/*  827 */     if (paramObject == null) {
/*  828 */       for (localNode = this.last; localNode != null; localNode = localNode.prev) {
/*  829 */         if (localNode.item == null) {
/*  830 */           unlink(localNode);
/*  831 */           return true;
/*      */         }
/*      */       }
/*      */     } else {
/*  835 */       for (localNode = this.last; localNode != null; localNode = localNode.prev) {
/*  836 */         if (paramObject.equals(localNode.item)) {
/*  837 */           unlink(localNode);
/*  838 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*  842 */     return false;
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
/*      */   public ListIterator<E> listIterator(int paramInt)
/*      */   {
/*  867 */     checkPositionIndex(paramInt);
/*  868 */     return new ListItr(paramInt);
/*      */   }
/*      */   
/*      */   private class ListItr implements ListIterator<E> {
/*      */     private LinkedList.Node<E> lastReturned;
/*      */     private LinkedList.Node<E> next;
/*      */     private int nextIndex;
/*  875 */     private int expectedModCount = LinkedList.this.modCount;
/*      */     
/*      */     ListItr(int paramInt)
/*      */     {
/*  879 */       this.next = (paramInt == LinkedList.this.size ? null : LinkedList.this.node(paramInt));
/*  880 */       this.nextIndex = paramInt;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  884 */       return this.nextIndex < LinkedList.this.size;
/*      */     }
/*      */     
/*      */     public E next() {
/*  888 */       checkForComodification();
/*  889 */       if (!hasNext()) {
/*  890 */         throw new NoSuchElementException();
/*      */       }
/*  892 */       this.lastReturned = this.next;
/*  893 */       this.next = this.next.next;
/*  894 */       this.nextIndex += 1;
/*  895 */       return (E)this.lastReturned.item;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  899 */       return this.nextIndex > 0;
/*      */     }
/*      */     
/*      */     public E previous() {
/*  903 */       checkForComodification();
/*  904 */       if (!hasPrevious()) {
/*  905 */         throw new NoSuchElementException();
/*      */       }
/*  907 */       this.lastReturned = (this.next = this.next == null ? LinkedList.this.last : this.next.prev);
/*  908 */       this.nextIndex -= 1;
/*  909 */       return (E)this.lastReturned.item;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  913 */       return this.nextIndex;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  917 */       return this.nextIndex - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  921 */       checkForComodification();
/*  922 */       if (this.lastReturned == null) {
/*  923 */         throw new IllegalStateException();
/*      */       }
/*  925 */       LinkedList.Node localNode = this.lastReturned.next;
/*  926 */       LinkedList.this.unlink(this.lastReturned);
/*  927 */       if (this.next == this.lastReturned) {
/*  928 */         this.next = localNode;
/*      */       } else
/*  930 */         this.nextIndex -= 1;
/*  931 */       this.lastReturned = null;
/*  932 */       this.expectedModCount += 1;
/*      */     }
/*      */     
/*      */     public void set(E paramE) {
/*  936 */       if (this.lastReturned == null)
/*  937 */         throw new IllegalStateException();
/*  938 */       checkForComodification();
/*  939 */       this.lastReturned.item = paramE;
/*      */     }
/*      */     
/*      */     public void add(E paramE) {
/*  943 */       checkForComodification();
/*  944 */       this.lastReturned = null;
/*  945 */       if (this.next == null) {
/*  946 */         LinkedList.this.linkLast(paramE);
/*      */       } else
/*  948 */         LinkedList.this.linkBefore(paramE, this.next);
/*  949 */       this.nextIndex += 1;
/*  950 */       this.expectedModCount += 1;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer) {
/*  954 */       Objects.requireNonNull(paramConsumer);
/*  955 */       while ((LinkedList.this.modCount == this.expectedModCount) && (this.nextIndex < LinkedList.this.size)) {
/*  956 */         paramConsumer.accept(this.next.item);
/*  957 */         this.lastReturned = this.next;
/*  958 */         this.next = this.next.next;
/*  959 */         this.nextIndex += 1;
/*      */       }
/*  961 */       checkForComodification();
/*      */     }
/*      */     
/*      */     final void checkForComodification() {
/*  965 */       if (LinkedList.this.modCount != this.expectedModCount)
/*  966 */         throw new ConcurrentModificationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class Node<E> {
/*      */     E item;
/*      */     Node<E> next;
/*      */     Node<E> prev;
/*      */     
/*      */     Node(Node<E> paramNode1, E paramE, Node<E> paramNode2) {
/*  976 */       this.item = paramE;
/*  977 */       this.next = paramNode2;
/*  978 */       this.prev = paramNode1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Iterator<E> descendingIterator()
/*      */   {
/*  986 */     return new DescendingIterator(null);
/*      */   }
/*      */   
/*      */   private class DescendingIterator implements Iterator<E>
/*      */   {
/*      */     private DescendingIterator() {}
/*      */     
/*  993 */     private final LinkedList<E>.ListItr itr = new LinkedList.ListItr(LinkedList.this, LinkedList.this.size());
/*      */     
/*  995 */     public boolean hasNext() { return this.itr.hasPrevious(); }
/*      */     
/*      */     public E next() {
/*  998 */       return (E)this.itr.previous();
/*      */     }
/*      */     
/* 1001 */     public void remove() { this.itr.remove(); }
/*      */   }
/*      */   
/*      */   private LinkedList<E> superClone()
/*      */   {
/*      */     try
/*      */     {
/* 1008 */       return (LinkedList)super.clone();
/*      */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 1010 */       throw new InternalError(localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/* 1021 */     LinkedList localLinkedList = superClone();
/*      */     
/*      */ 
/* 1024 */     localLinkedList.first = (localLinkedList.last = null);
/* 1025 */     localLinkedList.size = 0;
/* 1026 */     localLinkedList.modCount = 0;
/*      */     
/*      */ 
/* 1029 */     for (Node localNode = this.first; localNode != null; localNode = localNode.next) {
/* 1030 */       localLinkedList.add(localNode.item);
/*      */     }
/* 1032 */     return localLinkedList;
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
/* 1050 */     Object[] arrayOfObject = new Object[this.size];
/* 1051 */     int i = 0;
/* 1052 */     for (Node localNode = this.first; localNode != null; localNode = localNode.next)
/* 1053 */       arrayOfObject[(i++)] = localNode.item;
/* 1054 */     return arrayOfObject;
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/* 1097 */     if (paramArrayOfT.length < this.size)
/* 1098 */       paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT
/* 1099 */         .getClass().getComponentType(), this.size);
/* 1100 */     int i = 0;
/* 1101 */     T[] arrayOfT = paramArrayOfT;
/* 1102 */     for (Node localNode = this.first; localNode != null; localNode = localNode.next) {
/* 1103 */       arrayOfT[(i++)] = localNode.item;
/*      */     }
/* 1105 */     if (paramArrayOfT.length > this.size) {
/* 1106 */       paramArrayOfT[this.size] = null;
/*      */     }
/* 1108 */     return paramArrayOfT;
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
/* 1124 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*      */ 
/* 1127 */     paramObjectOutputStream.writeInt(this.size);
/*      */     
/*      */ 
/* 1130 */     for (Node localNode = this.first; localNode != null; localNode = localNode.next) {
/* 1131 */       paramObjectOutputStream.writeObject(localNode.item);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1142 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/* 1145 */     int i = paramObjectInputStream.readInt();
/*      */     
/*      */ 
/* 1148 */     for (int j = 0; j < i; j++) {
/* 1149 */       linkLast(paramObjectInputStream.readObject());
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1170 */     return new LLSpliterator(this, -1, 0);
/*      */   }
/*      */   
/*      */   static final class LLSpliterator<E> implements Spliterator<E>
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedList<E> list;
/*      */     LinkedList.Node<E> current;
/*      */     int est;
/*      */     int expectedModCount;
/*      */     int batch;
/*      */     
/*      */     LLSpliterator(LinkedList<E> paramLinkedList, int paramInt1, int paramInt2) {
/* 1184 */       this.list = paramLinkedList;
/* 1185 */       this.est = paramInt1;
/* 1186 */       this.expectedModCount = paramInt2;
/*      */     }
/*      */     
/*      */     final int getEst()
/*      */     {
/*      */       int i;
/* 1192 */       if ((i = this.est) < 0) { LinkedList localLinkedList;
/* 1193 */         if ((localLinkedList = this.list) == null) {
/* 1194 */           i = this.est = 0;
/*      */         } else {
/* 1196 */           this.expectedModCount = localLinkedList.modCount;
/* 1197 */           this.current = localLinkedList.first;
/* 1198 */           i = this.est = localLinkedList.size;
/*      */         }
/*      */       }
/* 1201 */       return i;
/*      */     }
/*      */     
/* 1204 */     public long estimateSize() { return getEst(); }
/*      */     
/*      */     public Spliterator<E> trySplit()
/*      */     {
/* 1208 */       int i = getEst();
/* 1209 */       LinkedList.Node localNode; if ((i > 1) && ((localNode = this.current) != null)) {
/* 1210 */         int j = this.batch + 1024;
/* 1211 */         if (j > i)
/* 1212 */           j = i;
/* 1213 */         if (j > 33554432)
/* 1214 */           j = 33554432;
/* 1215 */         Object[] arrayOfObject = new Object[j];
/* 1216 */         int k = 0;
/* 1217 */         do { arrayOfObject[(k++)] = localNode.item; } while (((localNode = localNode.next) != null) && (k < j));
/* 1218 */         this.current = localNode;
/* 1219 */         this.batch = k;
/* 1220 */         this.est = (i - k);
/* 1221 */         return Spliterators.spliterator(arrayOfObject, 0, k, 16);
/*      */       }
/* 1223 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1228 */       if (paramConsumer == null) throw new NullPointerException();
/* 1229 */       int i; LinkedList.Node localNode; if (((i = getEst()) > 0) && ((localNode = this.current) != null)) {
/* 1230 */         this.current = null;
/* 1231 */         this.est = 0;
/*      */         do {
/* 1233 */           Object localObject = localNode.item;
/* 1234 */           localNode = localNode.next;
/* 1235 */           paramConsumer.accept(localObject);
/* 1236 */           if (localNode == null) break; i--; } while (i > 0);
/*      */       }
/* 1238 */       if (this.list.modCount != this.expectedModCount) {
/* 1239 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer) {
/* 1244 */       if (paramConsumer == null) throw new NullPointerException();
/* 1245 */       LinkedList.Node localNode; if ((getEst() > 0) && ((localNode = this.current) != null)) {
/* 1246 */         this.est -= 1;
/* 1247 */         Object localObject = localNode.item;
/* 1248 */         this.current = localNode.next;
/* 1249 */         paramConsumer.accept(localObject);
/* 1250 */         if (this.list.modCount != this.expectedModCount)
/* 1251 */           throw new ConcurrentModificationException();
/* 1252 */         return true;
/*      */       }
/* 1254 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1258 */       return 16464;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/LinkedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */