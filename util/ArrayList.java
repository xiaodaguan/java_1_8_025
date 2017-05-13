/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayList<E>
/*      */   extends AbstractList<E>
/*      */   implements List<E>, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8683452581122892189L;
/*      */   private static final int DEFAULT_CAPACITY = 10;
/*  119 */   private static final Object[] EMPTY_ELEMENTDATA = new Object[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  126 */   private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   transient Object[] elementData;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int size;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList(int paramInt)
/*      */   {
/*  151 */     if (paramInt > 0) {
/*  152 */       this.elementData = new Object[paramInt];
/*  153 */     } else if (paramInt == 0) {
/*  154 */       this.elementData = EMPTY_ELEMENTDATA;
/*      */     } else {
/*  156 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList()
/*      */   {
/*  165 */     this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList(Collection<? extends E> paramCollection)
/*      */   {
/*  177 */     this.elementData = paramCollection.toArray();
/*  178 */     if ((this.size = this.elementData.length) != 0)
/*      */     {
/*  180 */       if (this.elementData.getClass() != Object[].class) {
/*  181 */         this.elementData = Arrays.copyOf(this.elementData, this.size, Object[].class);
/*      */       }
/*      */     } else {
/*  184 */       this.elementData = EMPTY_ELEMENTDATA;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trimToSize()
/*      */   {
/*  194 */     this.modCount += 1;
/*  195 */     if (this.size < this.elementData.length)
/*      */     {
/*      */ 
/*  198 */       this.elementData = (this.size == 0 ? EMPTY_ELEMENTDATA : Arrays.copyOf(this.elementData, this.size));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ensureCapacity(int paramInt)
/*      */   {
/*  210 */     int i = this.elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA ? 0 : 10;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  217 */     if (paramInt > i) {
/*  218 */       ensureExplicitCapacity(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */   private void ensureCapacityInternal(int paramInt) {
/*  223 */     if (this.elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
/*  224 */       paramInt = Math.max(10, paramInt);
/*      */     }
/*      */     
/*  227 */     ensureExplicitCapacity(paramInt);
/*      */   }
/*      */   
/*      */   private void ensureExplicitCapacity(int paramInt) {
/*  231 */     this.modCount += 1;
/*      */     
/*      */ 
/*  234 */     if (paramInt - this.elementData.length > 0) {
/*  235 */       grow(paramInt);
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
/*      */   private void grow(int paramInt)
/*      */   {
/*  254 */     int i = this.elementData.length;
/*  255 */     int j = i + (i >> 1);
/*  256 */     if (j - paramInt < 0)
/*  257 */       j = paramInt;
/*  258 */     if (j - 2147483639 > 0) {
/*  259 */       j = hugeCapacity(paramInt);
/*      */     }
/*  261 */     this.elementData = Arrays.copyOf(this.elementData, j);
/*      */   }
/*      */   
/*      */   private static int hugeCapacity(int paramInt) {
/*  265 */     if (paramInt < 0)
/*  266 */       throw new OutOfMemoryError();
/*  267 */     return paramInt > 2147483639 ? Integer.MAX_VALUE : 2147483639;
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
/*  278 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  287 */     return this.size == 0;
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
/*  300 */     return indexOf(paramObject) >= 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int indexOf(Object paramObject)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*  311 */     if (paramObject == null) {
/*  312 */       for (i = 0; i < this.size; i++)
/*  313 */         if (this.elementData[i] == null)
/*  314 */           return i;
/*      */     } else {
/*  316 */       for (i = 0; i < this.size; i++)
/*  317 */         if (paramObject.equals(this.elementData[i]))
/*  318 */           return i;
/*      */     }
/*  320 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int lastIndexOf(Object paramObject)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*  331 */     if (paramObject == null) {
/*  332 */       for (i = this.size - 1; i >= 0; i--)
/*  333 */         if (this.elementData[i] == null)
/*  334 */           return i;
/*      */     } else {
/*  336 */       for (i = this.size - 1; i >= 0; i--)
/*  337 */         if (paramObject.equals(this.elementData[i]))
/*  338 */           return i;
/*      */     }
/*  340 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  351 */       ArrayList localArrayList = (ArrayList)super.clone();
/*  352 */       localArrayList.elementData = Arrays.copyOf(this.elementData, this.size);
/*  353 */       localArrayList.modCount = 0;
/*  354 */       return localArrayList;
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  357 */       throw new InternalError(localCloneNotSupportedException);
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
/*  376 */     return Arrays.copyOf(this.elementData, this.size);
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT)
/*      */   {
/*  405 */     if (paramArrayOfT.length < this.size)
/*      */     {
/*  407 */       return (Object[])Arrays.copyOf(this.elementData, this.size, paramArrayOfT.getClass()); }
/*  408 */     System.arraycopy(this.elementData, 0, paramArrayOfT, 0, this.size);
/*  409 */     if (paramArrayOfT.length > this.size)
/*  410 */       paramArrayOfT[this.size] = null;
/*  411 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   E elementData(int paramInt)
/*      */   {
/*  418 */     return (E)this.elementData[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public E get(int paramInt)
/*      */   {
/*  429 */     rangeCheck(paramInt);
/*      */     
/*  431 */     return (E)elementData(paramInt);
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
/*  444 */     rangeCheck(paramInt);
/*      */     
/*  446 */     Object localObject = elementData(paramInt);
/*  447 */     this.elementData[paramInt] = paramE;
/*  448 */     return (E)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean add(E paramE)
/*      */   {
/*  458 */     ensureCapacityInternal(this.size + 1);
/*  459 */     this.elementData[(this.size++)] = paramE;
/*  460 */     return true;
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
/*  473 */     rangeCheckForAdd(paramInt);
/*      */     
/*  475 */     ensureCapacityInternal(this.size + 1);
/*  476 */     System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + 1, this.size - paramInt);
/*      */     
/*  478 */     this.elementData[paramInt] = paramE;
/*  479 */     this.size += 1;
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
/*      */   public E remove(int paramInt)
/*      */   {
/*  492 */     rangeCheck(paramInt);
/*      */     
/*  494 */     this.modCount += 1;
/*  495 */     Object localObject = elementData(paramInt);
/*      */     
/*  497 */     int i = this.size - paramInt - 1;
/*  498 */     if (i > 0) {
/*  499 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  501 */     this.elementData[(--this.size)] = null;
/*      */     
/*  503 */     return (E)localObject;
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
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  520 */     if (paramObject == null) {
/*  521 */       for (i = 0; i < this.size; i++)
/*  522 */         if (this.elementData[i] == null) {
/*  523 */           fastRemove(i);
/*  524 */           return true;
/*      */         }
/*      */     } else {
/*  527 */       for (i = 0; i < this.size; i++)
/*  528 */         if (paramObject.equals(this.elementData[i])) {
/*  529 */           fastRemove(i);
/*  530 */           return true;
/*      */         }
/*      */     }
/*  533 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void fastRemove(int paramInt)
/*      */   {
/*  541 */     this.modCount += 1;
/*  542 */     int i = this.size - paramInt - 1;
/*  543 */     if (i > 0) {
/*  544 */       System.arraycopy(this.elementData, paramInt + 1, this.elementData, paramInt, i);
/*      */     }
/*  546 */     this.elementData[(--this.size)] = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  554 */     this.modCount += 1;
/*      */     
/*      */ 
/*  557 */     for (int i = 0; i < this.size; i++) {
/*  558 */       this.elementData[i] = null;
/*      */     }
/*  560 */     this.size = 0;
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection)
/*      */   {
/*  577 */     Object[] arrayOfObject = paramCollection.toArray();
/*  578 */     int i = arrayOfObject.length;
/*  579 */     ensureCapacityInternal(this.size + i);
/*  580 */     System.arraycopy(arrayOfObject, 0, this.elementData, this.size, i);
/*  581 */     this.size += i;
/*  582 */     return i != 0;
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
/*  601 */     rangeCheckForAdd(paramInt);
/*      */     
/*  603 */     Object[] arrayOfObject = paramCollection.toArray();
/*  604 */     int i = arrayOfObject.length;
/*  605 */     ensureCapacityInternal(this.size + i);
/*      */     
/*  607 */     int j = this.size - paramInt;
/*  608 */     if (j > 0) {
/*  609 */       System.arraycopy(this.elementData, paramInt, this.elementData, paramInt + i, j);
/*      */     }
/*      */     
/*  612 */     System.arraycopy(arrayOfObject, 0, this.elementData, paramInt, i);
/*  613 */     this.size += i;
/*  614 */     return i != 0;
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
/*      */   protected void removeRange(int paramInt1, int paramInt2)
/*      */   {
/*  632 */     this.modCount += 1;
/*  633 */     int i = this.size - paramInt2;
/*  634 */     System.arraycopy(this.elementData, paramInt2, this.elementData, paramInt1, i);
/*      */     
/*      */ 
/*      */ 
/*  638 */     int j = this.size - (paramInt2 - paramInt1);
/*  639 */     for (int k = j; k < this.size; k++) {
/*  640 */       this.elementData[k] = null;
/*      */     }
/*  642 */     this.size = j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void rangeCheck(int paramInt)
/*      */   {
/*  652 */     if (paramInt >= this.size) {
/*  653 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void rangeCheckForAdd(int paramInt)
/*      */   {
/*  660 */     if ((paramInt > this.size) || (paramInt < 0)) {
/*  661 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String outOfBoundsMsg(int paramInt)
/*      */   {
/*  670 */     return "Index: " + paramInt + ", Size: " + this.size;
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
/*      */   public boolean removeAll(Collection<?> paramCollection)
/*      */   {
/*  689 */     Objects.requireNonNull(paramCollection);
/*  690 */     return batchRemove(paramCollection, false);
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
/*      */   public boolean retainAll(Collection<?> paramCollection)
/*      */   {
/*  710 */     Objects.requireNonNull(paramCollection);
/*  711 */     return batchRemove(paramCollection, true);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private boolean batchRemove(Collection<?> paramCollection, boolean paramBoolean)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 4	java/util/ArrayList:elementData	[Ljava/lang/Object;
/*      */     //   4: astore_3
/*      */     //   5: iconst_0
/*      */     //   6: istore 4
/*      */     //   8: iconst_0
/*      */     //   9: istore 5
/*      */     //   11: iconst_0
/*      */     //   12: istore 6
/*      */     //   14: iload 4
/*      */     //   16: aload_0
/*      */     //   17: getfield 1	java/util/ArrayList:size	I
/*      */     //   20: if_icmpge +34 -> 54
/*      */     //   23: aload_1
/*      */     //   24: aload_3
/*      */     //   25: iload 4
/*      */     //   27: aaload
/*      */     //   28: invokeinterface 50 2 0
/*      */     //   33: iload_2
/*      */     //   34: if_icmpne +14 -> 48
/*      */     //   37: aload_3
/*      */     //   38: iload 5
/*      */     //   40: iinc 5 1
/*      */     //   43: aload_3
/*      */     //   44: iload 4
/*      */     //   46: aaload
/*      */     //   47: aastore
/*      */     //   48: iinc 4 1
/*      */     //   51: goto -37 -> 14
/*      */     //   54: iload 4
/*      */     //   56: aload_0
/*      */     //   57: getfield 1	java/util/ArrayList:size	I
/*      */     //   60: if_icmpeq +31 -> 91
/*      */     //   63: aload_3
/*      */     //   64: iload 4
/*      */     //   66: aload_3
/*      */     //   67: iload 5
/*      */     //   69: aload_0
/*      */     //   70: getfield 1	java/util/ArrayList:size	I
/*      */     //   73: iload 4
/*      */     //   75: isub
/*      */     //   76: invokestatic 37	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   79: iload 5
/*      */     //   81: aload_0
/*      */     //   82: getfield 1	java/util/ArrayList:size	I
/*      */     //   85: iload 4
/*      */     //   87: isub
/*      */     //   88: iadd
/*      */     //   89: istore 5
/*      */     //   91: iload 5
/*      */     //   93: aload_0
/*      */     //   94: getfield 1	java/util/ArrayList:size	I
/*      */     //   97: if_icmpeq +155 -> 252
/*      */     //   100: iload 5
/*      */     //   102: istore 7
/*      */     //   104: iload 7
/*      */     //   106: aload_0
/*      */     //   107: getfield 1	java/util/ArrayList:size	I
/*      */     //   110: if_icmpge +14 -> 124
/*      */     //   113: aload_3
/*      */     //   114: iload 7
/*      */     //   116: aconst_null
/*      */     //   117: aastore
/*      */     //   118: iinc 7 1
/*      */     //   121: goto -17 -> 104
/*      */     //   124: aload_0
/*      */     //   125: dup
/*      */     //   126: getfield 19	java/util/ArrayList:modCount	I
/*      */     //   129: aload_0
/*      */     //   130: getfield 1	java/util/ArrayList:size	I
/*      */     //   133: iload 5
/*      */     //   135: isub
/*      */     //   136: iadd
/*      */     //   137: putfield 19	java/util/ArrayList:modCount	I
/*      */     //   140: aload_0
/*      */     //   141: iload 5
/*      */     //   143: putfield 1	java/util/ArrayList:size	I
/*      */     //   146: iconst_1
/*      */     //   147: istore 6
/*      */     //   149: goto +103 -> 252
/*      */     //   152: astore 8
/*      */     //   154: iload 4
/*      */     //   156: aload_0
/*      */     //   157: getfield 1	java/util/ArrayList:size	I
/*      */     //   160: if_icmpeq +31 -> 191
/*      */     //   163: aload_3
/*      */     //   164: iload 4
/*      */     //   166: aload_3
/*      */     //   167: iload 5
/*      */     //   169: aload_0
/*      */     //   170: getfield 1	java/util/ArrayList:size	I
/*      */     //   173: iload 4
/*      */     //   175: isub
/*      */     //   176: invokestatic 37	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   179: iload 5
/*      */     //   181: aload_0
/*      */     //   182: getfield 1	java/util/ArrayList:size	I
/*      */     //   185: iload 4
/*      */     //   187: isub
/*      */     //   188: iadd
/*      */     //   189: istore 5
/*      */     //   191: iload 5
/*      */     //   193: aload_0
/*      */     //   194: getfield 1	java/util/ArrayList:size	I
/*      */     //   197: if_icmpeq +52 -> 249
/*      */     //   200: iload 5
/*      */     //   202: istore 9
/*      */     //   204: iload 9
/*      */     //   206: aload_0
/*      */     //   207: getfield 1	java/util/ArrayList:size	I
/*      */     //   210: if_icmpge +14 -> 224
/*      */     //   213: aload_3
/*      */     //   214: iload 9
/*      */     //   216: aconst_null
/*      */     //   217: aastore
/*      */     //   218: iinc 9 1
/*      */     //   221: goto -17 -> 204
/*      */     //   224: aload_0
/*      */     //   225: dup
/*      */     //   226: getfield 19	java/util/ArrayList:modCount	I
/*      */     //   229: aload_0
/*      */     //   230: getfield 1	java/util/ArrayList:size	I
/*      */     //   233: iload 5
/*      */     //   235: isub
/*      */     //   236: iadd
/*      */     //   237: putfield 19	java/util/ArrayList:modCount	I
/*      */     //   240: aload_0
/*      */     //   241: iload 5
/*      */     //   243: putfield 1	java/util/ArrayList:size	I
/*      */     //   246: iconst_1
/*      */     //   247: istore 6
/*      */     //   249: aload 8
/*      */     //   251: athrow
/*      */     //   252: iload 6
/*      */     //   254: ireturn
/*      */     // Line number table:
/*      */     //   Java source line #715	-> byte code offset #0
/*      */     //   Java source line #716	-> byte code offset #5
/*      */     //   Java source line #717	-> byte code offset #11
/*      */     //   Java source line #719	-> byte code offset #14
/*      */     //   Java source line #720	-> byte code offset #23
/*      */     //   Java source line #721	-> byte code offset #37
/*      */     //   Java source line #719	-> byte code offset #48
/*      */     //   Java source line #725	-> byte code offset #54
/*      */     //   Java source line #726	-> byte code offset #63
/*      */     //   Java source line #729	-> byte code offset #79
/*      */     //   Java source line #731	-> byte code offset #91
/*      */     //   Java source line #733	-> byte code offset #100
/*      */     //   Java source line #734	-> byte code offset #113
/*      */     //   Java source line #733	-> byte code offset #118
/*      */     //   Java source line #735	-> byte code offset #124
/*      */     //   Java source line #736	-> byte code offset #140
/*      */     //   Java source line #737	-> byte code offset #146
/*      */     //   Java source line #725	-> byte code offset #152
/*      */     //   Java source line #726	-> byte code offset #163
/*      */     //   Java source line #729	-> byte code offset #179
/*      */     //   Java source line #731	-> byte code offset #191
/*      */     //   Java source line #733	-> byte code offset #200
/*      */     //   Java source line #734	-> byte code offset #213
/*      */     //   Java source line #733	-> byte code offset #218
/*      */     //   Java source line #735	-> byte code offset #224
/*      */     //   Java source line #736	-> byte code offset #240
/*      */     //   Java source line #737	-> byte code offset #246
/*      */     //   Java source line #740	-> byte code offset #252
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	255	0	this	ArrayList
/*      */     //   0	255	1	paramCollection	Collection<?>
/*      */     //   0	255	2	paramBoolean	boolean
/*      */     //   4	210	3	arrayOfObject	Object[]
/*      */     //   6	182	4	i	int
/*      */     //   9	233	5	j	int
/*      */     //   12	241	6	bool	boolean
/*      */     //   102	17	7	k	int
/*      */     //   152	98	8	localObject	Object
/*      */     //   202	17	9	m	int
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   14	54	152	finally
/*      */     //   152	154	152	finally
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*  754 */     int i = this.modCount;
/*  755 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*      */ 
/*  758 */     paramObjectOutputStream.writeInt(this.size);
/*      */     
/*      */ 
/*  761 */     for (int j = 0; j < this.size; j++) {
/*  762 */       paramObjectOutputStream.writeObject(this.elementData[j]);
/*      */     }
/*      */     
/*  765 */     if (this.modCount != i) {
/*  766 */       throw new ConcurrentModificationException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  776 */     this.elementData = EMPTY_ELEMENTDATA;
/*      */     
/*      */ 
/*  779 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/*  782 */     paramObjectInputStream.readInt();
/*      */     
/*  784 */     if (this.size > 0)
/*      */     {
/*  786 */       ensureCapacityInternal(this.size);
/*      */       
/*  788 */       Object[] arrayOfObject = this.elementData;
/*      */       
/*  790 */       for (int i = 0; i < this.size; i++) {
/*  791 */         arrayOfObject[i] = paramObjectInputStream.readObject();
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
/*      */   public ListIterator<E> listIterator(int paramInt)
/*      */   {
/*  809 */     if ((paramInt < 0) || (paramInt > this.size))
/*  810 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
/*  811 */     return new ListItr(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ListIterator<E> listIterator()
/*      */   {
/*  823 */     return new ListItr(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Iterator<E> iterator()
/*      */   {
/*  834 */     return new Itr(null);
/*      */   }
/*      */   
/*      */   private class Itr implements Iterator<E> {
/*      */     int cursor;
/*      */     
/*      */     private Itr() {}
/*      */     
/*  842 */     int lastRet = -1;
/*  843 */     int expectedModCount = ArrayList.this.modCount;
/*      */     
/*      */     public boolean hasNext() {
/*  846 */       return this.cursor != ArrayList.this.size;
/*      */     }
/*      */     
/*      */     public E next()
/*      */     {
/*  851 */       checkForComodification();
/*  852 */       int i = this.cursor;
/*  853 */       if (i >= ArrayList.this.size)
/*  854 */         throw new NoSuchElementException();
/*  855 */       Object[] arrayOfObject = ArrayList.this.elementData;
/*  856 */       if (i >= arrayOfObject.length)
/*  857 */         throw new ConcurrentModificationException();
/*  858 */       this.cursor = (i + 1);
/*  859 */       return (E)arrayOfObject[(this.lastRet = i)];
/*      */     }
/*      */     
/*      */     public void remove() {
/*  863 */       if (this.lastRet < 0)
/*  864 */         throw new IllegalStateException();
/*  865 */       checkForComodification();
/*      */       try
/*      */       {
/*  868 */         ArrayList.this.remove(this.lastRet);
/*  869 */         this.cursor = this.lastRet;
/*  870 */         this.lastRet = -1;
/*  871 */         this.expectedModCount = ArrayList.this.modCount;
/*      */       } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/*  873 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/*  880 */       Objects.requireNonNull(paramConsumer);
/*  881 */       int i = ArrayList.this.size;
/*  882 */       int j = this.cursor;
/*  883 */       if (j >= i) {
/*  884 */         return;
/*      */       }
/*  886 */       Object[] arrayOfObject = ArrayList.this.elementData;
/*  887 */       if (j >= arrayOfObject.length) {
/*  888 */         throw new ConcurrentModificationException();
/*      */       }
/*  890 */       while ((j != i) && (ArrayList.this.modCount == this.expectedModCount)) {
/*  891 */         paramConsumer.accept(arrayOfObject[(j++)]);
/*      */       }
/*      */       
/*  894 */       this.cursor = j;
/*  895 */       this.lastRet = (j - 1);
/*  896 */       checkForComodification();
/*      */     }
/*      */     
/*      */     final void checkForComodification() {
/*  900 */       if (ArrayList.this.modCount != this.expectedModCount) {
/*  901 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ListItr extends ArrayList<E>.Itr implements ListIterator<E>
/*      */   {
/*      */     ListItr(int paramInt)
/*      */     {
/*  910 */       super(null);
/*  911 */       this.cursor = paramInt;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  915 */       return this.cursor != 0;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  919 */       return this.cursor;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  923 */       return this.cursor - 1;
/*      */     }
/*      */     
/*      */     public E previous()
/*      */     {
/*  928 */       checkForComodification();
/*  929 */       int i = this.cursor - 1;
/*  930 */       if (i < 0)
/*  931 */         throw new NoSuchElementException();
/*  932 */       Object[] arrayOfObject = ArrayList.this.elementData;
/*  933 */       if (i >= arrayOfObject.length)
/*  934 */         throw new ConcurrentModificationException();
/*  935 */       this.cursor = i;
/*  936 */       return (E)arrayOfObject[(this.lastRet = i)];
/*      */     }
/*      */     
/*      */     public void set(E paramE) {
/*  940 */       if (this.lastRet < 0)
/*  941 */         throw new IllegalStateException();
/*  942 */       checkForComodification();
/*      */       try
/*      */       {
/*  945 */         ArrayList.this.set(this.lastRet, paramE);
/*      */       } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/*  947 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public void add(E paramE) {
/*  952 */       checkForComodification();
/*      */       try
/*      */       {
/*  955 */         int i = this.cursor;
/*  956 */         ArrayList.this.add(i, paramE);
/*  957 */         this.cursor = (i + 1);
/*  958 */         this.lastRet = -1;
/*  959 */         this.expectedModCount = ArrayList.this.modCount;
/*      */       } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/*  961 */         throw new ConcurrentModificationException();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<E> subList(int paramInt1, int paramInt2)
/*      */   {
/*  996 */     subListRangeCheck(paramInt1, paramInt2, this.size);
/*  997 */     return new SubList(this, 0, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   static void subListRangeCheck(int paramInt1, int paramInt2, int paramInt3) {
/* 1001 */     if (paramInt1 < 0)
/* 1002 */       throw new IndexOutOfBoundsException("fromIndex = " + paramInt1);
/* 1003 */     if (paramInt2 > paramInt3)
/* 1004 */       throw new IndexOutOfBoundsException("toIndex = " + paramInt2);
/* 1005 */     if (paramInt1 > paramInt2) {
/* 1006 */       throw new IllegalArgumentException("fromIndex(" + paramInt1 + ") > toIndex(" + paramInt2 + ")");
/*      */     }
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractList<E> implements RandomAccess
/*      */   {
/*      */     private final AbstractList<E> parent;
/*      */     private final int parentOffset;
/*      */     private final int offset;
/*      */     int size;
/*      */     
/*      */     SubList(int paramInt1, int paramInt2, int paramInt3) {
/* 1018 */       this.parent = paramInt1;
/* 1019 */       this.parentOffset = paramInt3;
/* 1020 */       this.offset = (paramInt2 + paramInt3);
/* 1021 */       int i; this.size = (i - paramInt3);
/* 1022 */       this.modCount = ArrayList.this.modCount;
/*      */     }
/*      */     
/*      */     public E set(int paramInt, E paramE) {
/* 1026 */       rangeCheck(paramInt);
/* 1027 */       checkForComodification();
/* 1028 */       Object localObject = ArrayList.this.elementData(this.offset + paramInt);
/* 1029 */       ArrayList.this.elementData[(this.offset + paramInt)] = paramE;
/* 1030 */       return (E)localObject;
/*      */     }
/*      */     
/*      */     public E get(int paramInt) {
/* 1034 */       rangeCheck(paramInt);
/* 1035 */       checkForComodification();
/* 1036 */       return (E)ArrayList.this.elementData(this.offset + paramInt);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1040 */       checkForComodification();
/* 1041 */       return this.size;
/*      */     }
/*      */     
/*      */     public void add(int paramInt, E paramE) {
/* 1045 */       rangeCheckForAdd(paramInt);
/* 1046 */       checkForComodification();
/* 1047 */       this.parent.add(this.parentOffset + paramInt, paramE);
/* 1048 */       this.modCount = this.parent.modCount;
/* 1049 */       this.size += 1;
/*      */     }
/*      */     
/*      */     public E remove(int paramInt) {
/* 1053 */       rangeCheck(paramInt);
/* 1054 */       checkForComodification();
/* 1055 */       Object localObject = this.parent.remove(this.parentOffset + paramInt);
/* 1056 */       this.modCount = this.parent.modCount;
/* 1057 */       this.size -= 1;
/* 1058 */       return (E)localObject;
/*      */     }
/*      */     
/*      */     protected void removeRange(int paramInt1, int paramInt2) {
/* 1062 */       checkForComodification();
/* 1063 */       this.parent.removeRange(this.parentOffset + paramInt1, this.parentOffset + paramInt2);
/*      */       
/* 1065 */       this.modCount = this.parent.modCount;
/* 1066 */       this.size -= paramInt2 - paramInt1;
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends E> paramCollection) {
/* 1070 */       return addAll(this.size, paramCollection);
/*      */     }
/*      */     
/*      */     public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/* 1074 */       rangeCheckForAdd(paramInt);
/* 1075 */       int i = paramCollection.size();
/* 1076 */       if (i == 0) {
/* 1077 */         return false;
/*      */       }
/* 1079 */       checkForComodification();
/* 1080 */       this.parent.addAll(this.parentOffset + paramInt, paramCollection);
/* 1081 */       this.modCount = this.parent.modCount;
/* 1082 */       this.size += i;
/* 1083 */       return true;
/*      */     }
/*      */     
/*      */     public Iterator<E> iterator() {
/* 1087 */       return listIterator();
/*      */     }
/*      */     
/*      */     public ListIterator<E> listIterator(final int paramInt) {
/* 1091 */       checkForComodification();
/* 1092 */       rangeCheckForAdd(paramInt);
/* 1093 */       final int i = this.offset;
/*      */       
/* 1095 */       new ListIterator() {
/* 1096 */         int cursor = paramInt;
/* 1097 */         int lastRet = -1;
/* 1098 */         int expectedModCount = ArrayList.this.modCount;
/*      */         
/*      */         public boolean hasNext() {
/* 1101 */           return this.cursor != ArrayList.SubList.this.size;
/*      */         }
/*      */         
/*      */         public E next()
/*      */         {
/* 1106 */           checkForComodification();
/* 1107 */           int i = this.cursor;
/* 1108 */           if (i >= ArrayList.SubList.this.size)
/* 1109 */             throw new NoSuchElementException();
/* 1110 */           Object[] arrayOfObject = ArrayList.this.elementData;
/* 1111 */           if (i + i >= arrayOfObject.length)
/* 1112 */             throw new ConcurrentModificationException();
/* 1113 */           this.cursor = (i + 1);
/* 1114 */           return (E)arrayOfObject[(i + (this.lastRet = i))];
/*      */         }
/*      */         
/*      */         public boolean hasPrevious() {
/* 1118 */           return this.cursor != 0;
/*      */         }
/*      */         
/*      */         public E previous()
/*      */         {
/* 1123 */           checkForComodification();
/* 1124 */           int i = this.cursor - 1;
/* 1125 */           if (i < 0)
/* 1126 */             throw new NoSuchElementException();
/* 1127 */           Object[] arrayOfObject = ArrayList.this.elementData;
/* 1128 */           if (i + i >= arrayOfObject.length)
/* 1129 */             throw new ConcurrentModificationException();
/* 1130 */           this.cursor = i;
/* 1131 */           return (E)arrayOfObject[(i + (this.lastRet = i))];
/*      */         }
/*      */         
/*      */         public void forEachRemaining(Consumer<? super E> paramAnonymousConsumer)
/*      */         {
/* 1136 */           Objects.requireNonNull(paramAnonymousConsumer);
/* 1137 */           int i = ArrayList.SubList.this.size;
/* 1138 */           int j = this.cursor;
/* 1139 */           if (j >= i) {
/* 1140 */             return;
/*      */           }
/* 1142 */           Object[] arrayOfObject = ArrayList.this.elementData;
/* 1143 */           if (i + j >= arrayOfObject.length) {
/* 1144 */             throw new ConcurrentModificationException();
/*      */           }
/* 1146 */           while ((j != i) && (ArrayList.SubList.this.modCount == this.expectedModCount)) {
/* 1147 */             paramAnonymousConsumer.accept(arrayOfObject[(i + j++)]);
/*      */           }
/*      */           
/* 1150 */           this.lastRet = (this.cursor = j);
/* 1151 */           checkForComodification();
/*      */         }
/*      */         
/*      */         public int nextIndex() {
/* 1155 */           return this.cursor;
/*      */         }
/*      */         
/*      */         public int previousIndex() {
/* 1159 */           return this.cursor - 1;
/*      */         }
/*      */         
/*      */         public void remove() {
/* 1163 */           if (this.lastRet < 0)
/* 1164 */             throw new IllegalStateException();
/* 1165 */           checkForComodification();
/*      */           try
/*      */           {
/* 1168 */             ArrayList.SubList.this.remove(this.lastRet);
/* 1169 */             this.cursor = this.lastRet;
/* 1170 */             this.lastRet = -1;
/* 1171 */             this.expectedModCount = ArrayList.this.modCount;
/*      */           } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1173 */             throw new ConcurrentModificationException();
/*      */           }
/*      */         }
/*      */         
/*      */         public void set(E paramAnonymousE) {
/* 1178 */           if (this.lastRet < 0)
/* 1179 */             throw new IllegalStateException();
/* 1180 */           checkForComodification();
/*      */           try
/*      */           {
/* 1183 */             ArrayList.this.set(i + this.lastRet, paramAnonymousE);
/*      */           } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1185 */             throw new ConcurrentModificationException();
/*      */           }
/*      */         }
/*      */         
/*      */         public void add(E paramAnonymousE) {
/* 1190 */           checkForComodification();
/*      */           try
/*      */           {
/* 1193 */             int i = this.cursor;
/* 1194 */             ArrayList.SubList.this.add(i, paramAnonymousE);
/* 1195 */             this.cursor = (i + 1);
/* 1196 */             this.lastRet = -1;
/* 1197 */             this.expectedModCount = ArrayList.this.modCount;
/*      */           } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1199 */             throw new ConcurrentModificationException();
/*      */           }
/*      */         }
/*      */         
/*      */         final void checkForComodification() {
/* 1204 */           if (this.expectedModCount != ArrayList.this.modCount)
/* 1205 */             throw new ConcurrentModificationException();
/*      */         }
/*      */       };
/*      */     }
/*      */     
/*      */     public List<E> subList(int paramInt1, int paramInt2) {
/* 1211 */       ArrayList.subListRangeCheck(paramInt1, paramInt2, this.size);
/* 1212 */       return new SubList(ArrayList.this, this, this.offset, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */     private void rangeCheck(int paramInt) {
/* 1216 */       if ((paramInt < 0) || (paramInt >= this.size))
/* 1217 */         throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */     
/*      */     private void rangeCheckForAdd(int paramInt) {
/* 1221 */       if ((paramInt < 0) || (paramInt > this.size))
/* 1222 */         throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */     
/*      */     private String outOfBoundsMsg(int paramInt) {
/* 1226 */       return "Index: " + paramInt + ", Size: " + this.size;
/*      */     }
/*      */     
/*      */     private void checkForComodification() {
/* 1230 */       if (ArrayList.this.modCount != this.modCount)
/* 1231 */         throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1235 */       checkForComodification();
/* 1236 */       return new ArrayList.ArrayListSpliterator(ArrayList.this, this.offset, this.offset + this.size, this.modCount);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void forEach(Consumer<? super E> paramConsumer)
/*      */   {
/* 1243 */     Objects.requireNonNull(paramConsumer);
/* 1244 */     int i = this.modCount;
/*      */     
/* 1246 */     Object[] arrayOfObject = (Object[])this.elementData;
/* 1247 */     int j = this.size;
/* 1248 */     for (int k = 0; (this.modCount == i) && (k < j); k++) {
/* 1249 */       paramConsumer.accept(arrayOfObject[k]);
/*      */     }
/* 1251 */     if (this.modCount != i) {
/* 1252 */       throw new ConcurrentModificationException();
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
/*      */   public Spliterator<E> spliterator()
/*      */   {
/* 1271 */     return new ArrayListSpliterator(this, 0, -1, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class ArrayListSpliterator<E>
/*      */     implements Spliterator<E>
/*      */   {
/*      */     private final ArrayList<E> list;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int index;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int fence;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int expectedModCount;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ArrayListSpliterator(ArrayList<E> paramArrayList, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1317 */       this.list = paramArrayList;
/* 1318 */       this.index = paramInt1;
/* 1319 */       this.fence = paramInt2;
/* 1320 */       this.expectedModCount = paramInt3;
/*      */     }
/*      */     
/*      */     private int getFence()
/*      */     {
/*      */       int i;
/* 1326 */       if ((i = this.fence) < 0) { ArrayList localArrayList;
/* 1327 */         if ((localArrayList = this.list) == null) {
/* 1328 */           i = this.fence = 0;
/*      */         } else {
/* 1330 */           this.expectedModCount = localArrayList.modCount;
/* 1331 */           i = this.fence = localArrayList.size;
/*      */         }
/*      */       }
/* 1334 */       return i;
/*      */     }
/*      */     
/*      */     public ArrayListSpliterator<E> trySplit() {
/* 1338 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1339 */       return j >= k ? null : new ArrayListSpliterator(this.list, j, this.index = k, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean tryAdvance(Consumer<? super E> paramConsumer)
/*      */     {
/* 1345 */       if (paramConsumer == null)
/* 1346 */         throw new NullPointerException();
/* 1347 */       int i = getFence();int j = this.index;
/* 1348 */       if (j < i) {
/* 1349 */         this.index = (j + 1);
/* 1350 */         Object localObject = this.list.elementData[j];
/* 1351 */         paramConsumer.accept(localObject);
/* 1352 */         if (this.list.modCount != this.expectedModCount)
/* 1353 */           throw new ConcurrentModificationException();
/* 1354 */         return true;
/*      */       }
/* 1356 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super E> paramConsumer)
/*      */     {
/* 1362 */       if (paramConsumer == null)
/* 1363 */         throw new NullPointerException();
/* 1364 */       ArrayList localArrayList; Object[] arrayOfObject; if (((localArrayList = this.list) != null) && ((arrayOfObject = localArrayList.elementData) != null)) { int j;
/* 1365 */         int k; if ((j = this.fence) < 0) {
/* 1366 */           k = localArrayList.modCount;
/* 1367 */           j = localArrayList.size;
/*      */         }
/*      */         else {
/* 1370 */           k = this.expectedModCount; }
/* 1371 */         int i; if (((i = this.index) >= 0) && ((this.index = j) <= arrayOfObject.length)) {
/* 1372 */           for (; i < j; i++) {
/* 1373 */             Object localObject = arrayOfObject[i];
/* 1374 */             paramConsumer.accept(localObject);
/*      */           }
/* 1376 */           if (localArrayList.modCount == k)
/* 1377 */             return;
/*      */         }
/*      */       }
/* 1380 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1384 */       return getFence() - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1388 */       return 16464;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean removeIf(Predicate<? super E> paramPredicate)
/*      */   {
/* 1394 */     Objects.requireNonNull(paramPredicate);
/*      */     
/*      */ 
/*      */ 
/* 1398 */     int i = 0;
/* 1399 */     BitSet localBitSet = new BitSet(this.size);
/* 1400 */     int j = this.modCount;
/* 1401 */     int k = this.size;
/* 1402 */     for (int m = 0; (this.modCount == j) && (m < k); m++)
/*      */     {
/* 1404 */       Object localObject = this.elementData[m];
/* 1405 */       if (paramPredicate.test(localObject)) {
/* 1406 */         localBitSet.set(m);
/* 1407 */         i++;
/*      */       }
/*      */     }
/* 1410 */     if (this.modCount != j) {
/* 1411 */       throw new ConcurrentModificationException();
/*      */     }
/*      */     
/*      */ 
/* 1415 */     m = i > 0 ? 1 : 0;
/* 1416 */     if (m != 0) {
/* 1417 */       int n = k - i;
/* 1418 */       int i1 = 0; for (int i2 = 0; (i1 < k) && (i2 < n); i2++) {
/* 1419 */         i1 = localBitSet.nextClearBit(i1);
/* 1420 */         this.elementData[i2] = this.elementData[i1];i1++;
/*      */       }
/*      */       
/* 1422 */       for (i1 = n; i1 < k; i1++) {
/* 1423 */         this.elementData[i1] = null;
/*      */       }
/* 1425 */       this.size = n;
/* 1426 */       if (this.modCount != j) {
/* 1427 */         throw new ConcurrentModificationException();
/*      */       }
/* 1429 */       this.modCount += 1;
/*      */     }
/*      */     
/* 1432 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */   public void replaceAll(UnaryOperator<E> paramUnaryOperator)
/*      */   {
/* 1438 */     Objects.requireNonNull(paramUnaryOperator);
/* 1439 */     int i = this.modCount;
/* 1440 */     int j = this.size;
/* 1441 */     for (int k = 0; (this.modCount == i) && (k < j); k++) {
/* 1442 */       this.elementData[k] = paramUnaryOperator.apply(this.elementData[k]);
/*      */     }
/* 1444 */     if (this.modCount != i) {
/* 1445 */       throw new ConcurrentModificationException();
/*      */     }
/* 1447 */     this.modCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */   public void sort(Comparator<? super E> paramComparator)
/*      */   {
/* 1453 */     int i = this.modCount;
/* 1454 */     Arrays.sort((Object[])this.elementData, 0, this.size, paramComparator);
/* 1455 */     if (this.modCount != i) {
/* 1456 */       throw new ConcurrentModificationException();
/*      */     }
/* 1458 */     this.modCount += 1;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */