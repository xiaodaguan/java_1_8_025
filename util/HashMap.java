/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements Map<K, V>, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 362498820763181265L;
/*      */   static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*      */   static final int TREEIFY_THRESHOLD = 8;
/*      */   static final int UNTREEIFY_THRESHOLD = 6;
/*      */   static final int MIN_TREEIFY_CAPACITY = 64;
/*      */   transient Node<K, V>[] table;
/*      */   transient Set<Map.Entry<K, V>> entrySet;
/*      */   transient int size;
/*      */   transient int modCount;
/*      */   int threshold;
/*      */   final float loadFactor;
/*      */   
/*      */   static class Node<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final K key;
/*      */     V value;
/*      */     Node<K, V> next;
/*      */     
/*      */     Node(int paramInt, K paramK, V paramV, Node<K, V> paramNode)
/*      */     {
/*  285 */       this.hash = paramInt;
/*  286 */       this.key = paramK;
/*  287 */       this.value = paramV;
/*  288 */       this.next = paramNode;
/*      */     }
/*      */     
/*  291 */     public final K getKey() { return (K)this.key; }
/*  292 */     public final V getValue() { return (V)this.value; }
/*  293 */     public final String toString() { return this.key + "=" + this.value; }
/*      */     
/*      */     public final int hashCode() {
/*  296 */       return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
/*      */     }
/*      */     
/*      */     public final V setValue(V paramV) {
/*  300 */       Object localObject = this.value;
/*  301 */       this.value = paramV;
/*  302 */       return (V)localObject;
/*      */     }
/*      */     
/*      */     public final boolean equals(Object paramObject) {
/*  306 */       if (paramObject == this)
/*  307 */         return true;
/*  308 */       if ((paramObject instanceof Map.Entry)) {
/*  309 */         Map.Entry localEntry = (Map.Entry)paramObject;
/*  310 */         if ((Objects.equals(this.key, localEntry.getKey())) && 
/*  311 */           (Objects.equals(this.value, localEntry.getValue())))
/*  312 */           return true;
/*      */       }
/*  314 */       return false;
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
/*      */   static final int hash(Object paramObject)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  338 */     return paramObject == null ? 0 : (i = paramObject.hashCode()) ^ i >>> 16;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static Class<?> comparableClassFor(Object paramObject)
/*      */   {
/*  346 */     if ((paramObject instanceof Comparable)) {
/*      */       Class localClass;
/*  348 */       if ((localClass = paramObject.getClass()) == String.class)
/*  349 */         return localClass;
/*  350 */       Type[] arrayOfType1; if ((arrayOfType1 = localClass.getGenericInterfaces()) != null)
/*  351 */         for (int i = 0; i < arrayOfType1.length; i++) { Type localType;
/*  352 */           ParameterizedType localParameterizedType; if ((((localType = arrayOfType1[i]) instanceof ParameterizedType)) && 
/*  353 */             ((localParameterizedType = (ParameterizedType)localType).getRawType() == Comparable.class)) {
/*      */             Type[] arrayOfType2;
/*  355 */             if (((arrayOfType2 = localParameterizedType.getActualTypeArguments()) != null) && (arrayOfType2.length == 1) && (arrayOfType2[0] == localClass))
/*      */             {
/*  357 */               return localClass; }
/*      */           }
/*      */         }
/*      */     }
/*  361 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static int compareComparables(Class<?> paramClass, Object paramObject1, Object paramObject2)
/*      */   {
/*  371 */     return (paramObject2 == null) || (paramObject2.getClass() != paramClass) ? 0 : ((Comparable)paramObject1).compareTo(paramObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final int tableSizeFor(int paramInt)
/*      */   {
/*  378 */     int i = paramInt - 1;
/*  379 */     i |= i >>> 1;
/*  380 */     i |= i >>> 2;
/*  381 */     i |= i >>> 4;
/*  382 */     i |= i >>> 8;
/*  383 */     i |= i >>> 16;
/*  384 */     return i >= 1073741824 ? 1073741824 : i < 0 ? 1 : i + 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap(int paramInt, float paramFloat)
/*      */   {
/*  447 */     if (paramInt < 0) {
/*  448 */       throw new IllegalArgumentException("Illegal initial capacity: " + paramInt);
/*      */     }
/*  450 */     if (paramInt > 1073741824)
/*  451 */       paramInt = 1073741824;
/*  452 */     if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat))) {
/*  453 */       throw new IllegalArgumentException("Illegal load factor: " + paramFloat);
/*      */     }
/*  455 */     this.loadFactor = paramFloat;
/*  456 */     this.threshold = tableSizeFor(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap(int paramInt)
/*      */   {
/*  467 */     this(paramInt, 0.75F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap()
/*      */   {
/*  475 */     this.loadFactor = 0.75F;
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
/*      */   public HashMap(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  488 */     this.loadFactor = 0.75F;
/*  489 */     putMapEntries(paramMap, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void putMapEntries(Map<? extends K, ? extends V> paramMap, boolean paramBoolean)
/*      */   {
/*  500 */     int i = paramMap.size();
/*  501 */     if (i > 0) {
/*  502 */       if (this.table == null) {
/*  503 */         float f = i / this.loadFactor + 1.0F;
/*  504 */         int j = f < 1.07374182E9F ? (int)f : 1073741824;
/*      */         
/*  506 */         if (j > this.threshold) {
/*  507 */           this.threshold = tableSizeFor(j);
/*      */         }
/*  509 */       } else if (i > this.threshold) {
/*  510 */         resize(); }
/*  511 */       for (Map.Entry localEntry : paramMap.entrySet()) {
/*  512 */         Object localObject1 = localEntry.getKey();
/*  513 */         Object localObject2 = localEntry.getValue();
/*  514 */         putVal(hash(localObject1), localObject1, localObject2, false, paramBoolean);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  525 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  534 */     return this.size == 0;
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
/*      */   public V get(Object paramObject)
/*      */   {
/*      */     Node localNode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  556 */     return (localNode = getNode(hash(paramObject), paramObject)) == null ? null : localNode.value;
/*      */   }
/*      */   
/*      */ 
/*      */   final Node<K, V> getNode(int paramInt, Object paramObject)
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */     int i;
/*      */     
/*      */     Node localNode1;
/*      */     
/*  568 */     if (((arrayOfNode = this.table) != null) && ((i = arrayOfNode.length) > 0) && ((localNode1 = arrayOfNode[(i - 1 & paramInt)]) != null)) {
/*      */       Object localObject;
/*  570 */       if ((localNode1.hash == paramInt) && (((localObject = localNode1.key) == paramObject) || ((paramObject != null) && 
/*  571 */         (paramObject.equals(localObject)))))
/*  572 */         return localNode1;
/*  573 */       Node localNode2; if ((localNode2 = localNode1.next) != null) {
/*  574 */         if ((localNode1 instanceof TreeNode))
/*  575 */           return ((TreeNode)localNode1).getTreeNode(paramInt, paramObject);
/*      */         do {
/*  577 */           if ((localNode2.hash == paramInt) && (((localObject = localNode2.key) == paramObject) || ((paramObject != null) && 
/*  578 */             (paramObject.equals(localObject)))))
/*  579 */             return localNode2;
/*  580 */         } while ((localNode2 = localNode2.next) != null);
/*      */       }
/*      */     }
/*  583 */     return null;
/*      */   }
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
/*  595 */     return getNode(hash(paramObject), paramObject) != null;
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
/*      */   public V put(K paramK, V paramV)
/*      */   {
/*  611 */     return (V)putVal(hash(paramK), paramK, paramV, false, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final V putVal(int paramInt, K paramK, V paramV, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/*      */ 
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*  627 */     if (((arrayOfNode = this.table) == null) || ((i = arrayOfNode.length) == 0))
/*  628 */       i = (arrayOfNode = resize()).length;
/*  629 */     int j; Object localObject1; if ((localObject1 = arrayOfNode[(j = i - 1 & paramInt)]) == null) {
/*  630 */       arrayOfNode[j] = newNode(paramInt, paramK, paramV, null);
/*      */     } else { Object localObject3;
/*      */       Object localObject2;
/*  633 */       if ((((Node)localObject1).hash == paramInt) && (((localObject3 = ((Node)localObject1).key) == paramK) || ((paramK != null) && 
/*  634 */         (paramK.equals(localObject3))))) {
/*  635 */         localObject2 = localObject1;
/*  636 */       } else if ((localObject1 instanceof TreeNode)) {
/*  637 */         localObject2 = ((TreeNode)localObject1).putTreeVal(this, arrayOfNode, paramInt, paramK, paramV);
/*      */       } else {
/*  639 */         for (int k = 0;; k++) {
/*  640 */           if ((localObject2 = ((Node)localObject1).next) == null) {
/*  641 */             ((Node)localObject1).next = newNode(paramInt, paramK, paramV, null);
/*  642 */             if (k < 7) break;
/*  643 */             treeifyBin(arrayOfNode, paramInt); break;
/*      */           }
/*      */           
/*  646 */           if ((((Node)localObject2).hash == paramInt) && (((localObject3 = ((Node)localObject2).key) == paramK) || ((paramK != null) && 
/*  647 */             (paramK.equals(localObject3)))))
/*      */             break;
/*  649 */           localObject1 = localObject2;
/*      */         }
/*      */       }
/*  652 */       if (localObject2 != null) {
/*  653 */         Object localObject4 = ((Node)localObject2).value;
/*  654 */         if ((!paramBoolean1) || (localObject4 == null))
/*  655 */           ((Node)localObject2).value = paramV;
/*  656 */         afterNodeAccess((Node)localObject2);
/*  657 */         return (V)localObject4;
/*      */       }
/*      */     }
/*  660 */     this.modCount += 1;
/*  661 */     if (++this.size > this.threshold)
/*  662 */       resize();
/*  663 */     afterNodeInsertion(paramBoolean2);
/*  664 */     return null;
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
/*      */   final Node<K, V>[] resize()
/*      */   {
/*  677 */     Node[] arrayOfNode1 = this.table;
/*  678 */     int i = arrayOfNode1 == null ? 0 : arrayOfNode1.length;
/*  679 */     int j = this.threshold;
/*  680 */     int m = 0;
/*  681 */     int k; if (i > 0) {
/*  682 */       if (i >= 1073741824) {
/*  683 */         this.threshold = Integer.MAX_VALUE;
/*  684 */         return arrayOfNode1;
/*      */       }
/*  686 */       if (((k = i << 1) < 1073741824) && (i >= 16))
/*      */       {
/*  688 */         m = j << 1;
/*      */       }
/*  690 */     } else if (j > 0) {
/*  691 */       k = j;
/*      */     } else {
/*  693 */       k = 16;
/*  694 */       m = 12;
/*      */     }
/*  696 */     if (m == 0) {
/*  697 */       float f = k * this.loadFactor;
/*  698 */       m = (k < 1073741824) && (f < 1.07374182E9F) ? (int)f : Integer.MAX_VALUE;
/*      */     }
/*      */     
/*  701 */     this.threshold = m;
/*      */     
/*  703 */     Node[] arrayOfNode2 = (Node[])new Node[k];
/*  704 */     this.table = arrayOfNode2;
/*  705 */     if (arrayOfNode1 != null) {
/*  706 */       for (int n = 0; n < i; n++) {
/*      */         Object localObject1;
/*  708 */         if ((localObject1 = arrayOfNode1[n]) != null) {
/*  709 */           arrayOfNode1[n] = null;
/*  710 */           if (((Node)localObject1).next == null) {
/*  711 */             arrayOfNode2[(localObject1.hash & k - 1)] = localObject1;
/*  712 */           } else if ((localObject1 instanceof TreeNode)) {
/*  713 */             ((TreeNode)localObject1).split(this, arrayOfNode2, n, i);
/*      */           } else {
/*  715 */             Object localObject2 = null;Object localObject3 = null;
/*  716 */             Object localObject4 = null;Object localObject5 = null;
/*      */             Node localNode;
/*      */             do {
/*  719 */               localNode = ((Node)localObject1).next;
/*  720 */               if ((((Node)localObject1).hash & i) == 0) {
/*  721 */                 if (localObject3 == null) {
/*  722 */                   localObject2 = localObject1;
/*      */                 } else
/*  724 */                   ((Node)localObject3).next = ((Node)localObject1);
/*  725 */                 localObject3 = localObject1;
/*      */               }
/*      */               else {
/*  728 */                 if (localObject5 == null) {
/*  729 */                   localObject4 = localObject1;
/*      */                 } else
/*  731 */                   ((Node)localObject5).next = ((Node)localObject1);
/*  732 */                 localObject5 = localObject1;
/*      */               }
/*  734 */             } while ((localObject1 = localNode) != null);
/*  735 */             if (localObject3 != null) {
/*  736 */               ((Node)localObject3).next = null;
/*  737 */               arrayOfNode2[n] = localObject2;
/*      */             }
/*  739 */             if (localObject5 != null) {
/*  740 */               ((Node)localObject5).next = null;
/*  741 */               arrayOfNode2[(n + i)] = localObject4;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  747 */     return arrayOfNode2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void treeifyBin(Node<K, V>[] paramArrayOfNode, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*  756 */     if ((paramArrayOfNode == null) || ((i = paramArrayOfNode.length) < 64)) {
/*  757 */       resize(); } else { int j;
/*  758 */       Object localObject1; if ((localObject1 = paramArrayOfNode[(j = i - 1 & paramInt)]) != null) {
/*  759 */         Object localObject2 = null;Object localObject3 = null;
/*      */         do {
/*  761 */           TreeNode localTreeNode = replacementTreeNode((Node)localObject1, null);
/*  762 */           if (localObject3 == null) {
/*  763 */             localObject2 = localTreeNode;
/*      */           } else {
/*  765 */             localTreeNode.prev = ((TreeNode)localObject3);
/*  766 */             ((TreeNode)localObject3).next = localTreeNode;
/*      */           }
/*  768 */           localObject3 = localTreeNode;
/*  769 */         } while ((localObject1 = ((Node)localObject1).next) != null);
/*  770 */         if ((paramArrayOfNode[j] = localObject2) != null) {
/*  771 */           ((TreeNode)localObject2).treeify(paramArrayOfNode);
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
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  784 */     putMapEntries(paramMap, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V remove(Object paramObject)
/*      */   {
/*      */     Node localNode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  798 */     return (localNode = removeNode(hash(paramObject), paramObject, null, false, true)) == null ? null : localNode.value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final Node<K, V> removeNode(int paramInt, Object paramObject1, Object paramObject2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/*      */     int i;
/*      */     
/*      */ 
/*      */     int j;
/*      */     
/*      */     Object localObject1;
/*      */     
/*  815 */     if (((arrayOfNode = this.table) != null) && ((i = arrayOfNode.length) > 0) && ((localObject1 = arrayOfNode[(j = i - 1 & paramInt)]) != null))
/*      */     {
/*  817 */       Object localObject2 = null;
/*  818 */       Object localObject3; if ((((Node)localObject1).hash == paramInt) && (((localObject3 = ((Node)localObject1).key) == paramObject1) || ((paramObject1 != null) && 
/*  819 */         (paramObject1.equals(localObject3))))) {
/*  820 */         localObject2 = localObject1; } else { Node localNode;
/*  821 */         if ((localNode = ((Node)localObject1).next) != null)
/*  822 */           if ((localObject1 instanceof TreeNode)) {
/*  823 */             localObject2 = ((TreeNode)localObject1).getTreeNode(paramInt, paramObject1);
/*      */           } else
/*      */             do {
/*  826 */               if (localNode.hash == paramInt) if ((localObject3 = localNode.key) != paramObject1) { if (paramObject1 != null)
/*      */                   {
/*  828 */                     if (!paramObject1.equals(localObject3)) {} }
/*  829 */                 } else { localObject2 = localNode;
/*  830 */                   break;
/*      */                 }
/*  832 */               localObject1 = localNode;
/*  833 */             } while ((localNode = localNode.next) != null);
/*      */       }
/*      */       Object localObject4;
/*  836 */       if ((localObject2 != null) && ((!paramBoolean1) || ((localObject4 = ((Node)localObject2).value) == paramObject2) || ((paramObject2 != null) && 
/*  837 */         (paramObject2.equals(localObject4))))) {
/*  838 */         if ((localObject2 instanceof TreeNode)) {
/*  839 */           ((TreeNode)localObject2).removeTreeNode(this, arrayOfNode, paramBoolean2);
/*  840 */         } else if (localObject2 == localObject1) {
/*  841 */           arrayOfNode[j] = ((Node)localObject2).next;
/*      */         } else
/*  843 */           ((Node)localObject1).next = ((Node)localObject2).next;
/*  844 */         this.modCount += 1;
/*  845 */         this.size -= 1;
/*  846 */         afterNodeRemoval((Node)localObject2);
/*  847 */         return (Node<K, V>)localObject2;
/*      */       }
/*      */     }
/*  850 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/*  859 */     this.modCount += 1;
/*  860 */     Node[] arrayOfNode; if (((arrayOfNode = this.table) != null) && (this.size > 0)) {
/*  861 */       this.size = 0;
/*  862 */       for (int i = 0; i < arrayOfNode.length; i++) {
/*  863 */         arrayOfNode[i] = null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsValue(Object paramObject)
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/*      */ 
/*  877 */     if (((arrayOfNode = this.table) != null) && (this.size > 0)) {
/*  878 */       for (int i = 0; i < arrayOfNode.length; i++) {
/*  879 */         for (Node localNode = arrayOfNode[i]; localNode != null; localNode = localNode.next) { Object localObject;
/*  880 */           if (((localObject = localNode.value) == paramObject) || ((paramObject != null) && 
/*  881 */             (paramObject.equals(localObject))))
/*  882 */             return true;
/*      */         }
/*      */       }
/*      */     }
/*  886 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<K> keySet()
/*      */   {
/*      */     Set localSet;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  906 */     return (localSet = this.keySet) == null ? (this.keySet = new KeySet()) : localSet; }
/*      */   
/*      */   final class KeySet extends AbstractSet<K> { KeySet() {}
/*      */     
/*  910 */     public final int size() { return HashMap.this.size; }
/*  911 */     public final void clear() { HashMap.this.clear(); }
/*  912 */     public final Iterator<K> iterator() { return new HashMap.KeyIterator(HashMap.this); }
/*  913 */     public final boolean contains(Object paramObject) { return HashMap.this.containsKey(paramObject); }
/*      */     
/*  915 */     public final boolean remove(Object paramObject) { return HashMap.this.removeNode(HashMap.hash(paramObject), paramObject, null, false, true) != null; }
/*      */     
/*      */     public final Spliterator<K> spliterator() {
/*  918 */       return new HashMap.KeySpliterator(HashMap.this, 0, -1, 0, 0);
/*      */     }
/*      */     
/*      */     public final void forEach(Consumer<? super K> paramConsumer) {
/*  922 */       if (paramConsumer == null)
/*  923 */         throw new NullPointerException();
/*  924 */       HashMap.Node[] arrayOfNode; if ((HashMap.this.size > 0) && ((arrayOfNode = HashMap.this.table) != null)) {
/*  925 */         int i = HashMap.this.modCount;
/*  926 */         for (int j = 0; j < arrayOfNode.length; j++) {
/*  927 */           for (HashMap.Node localNode = arrayOfNode[j]; localNode != null; localNode = localNode.next)
/*  928 */             paramConsumer.accept(localNode.key);
/*      */         }
/*  930 */         if (HashMap.this.modCount != i) {
/*  931 */           throw new ConcurrentModificationException();
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
/*      */   public Collection<V> values()
/*      */   {
/*      */     Collection localCollection;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  953 */     return (localCollection = this.values) == null ? (this.values = new Values()) : localCollection; }
/*      */   
/*      */   final class Values extends AbstractCollection<V> { Values() {}
/*      */     
/*  957 */     public final int size() { return HashMap.this.size; }
/*  958 */     public final void clear() { HashMap.this.clear(); }
/*  959 */     public final Iterator<V> iterator() { return new HashMap.ValueIterator(HashMap.this); }
/*  960 */     public final boolean contains(Object paramObject) { return HashMap.this.containsValue(paramObject); }
/*      */     
/*  962 */     public final Spliterator<V> spliterator() { return new HashMap.ValueSpliterator(HashMap.this, 0, -1, 0, 0); }
/*      */     
/*      */     public final void forEach(Consumer<? super V> paramConsumer)
/*      */     {
/*  966 */       if (paramConsumer == null)
/*  967 */         throw new NullPointerException();
/*  968 */       HashMap.Node[] arrayOfNode; if ((HashMap.this.size > 0) && ((arrayOfNode = HashMap.this.table) != null)) {
/*  969 */         int i = HashMap.this.modCount;
/*  970 */         for (int j = 0; j < arrayOfNode.length; j++) {
/*  971 */           for (HashMap.Node localNode = arrayOfNode[j]; localNode != null; localNode = localNode.next)
/*  972 */             paramConsumer.accept(localNode.value);
/*      */         }
/*  974 */         if (HashMap.this.modCount != i) {
/*  975 */           throw new ConcurrentModificationException();
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
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/*      */     Set localSet;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  998 */     return (localSet = this.entrySet) == null ? (this.entrySet = new EntrySet()) : localSet; }
/*      */   
/*      */   final class EntrySet extends AbstractSet<Map.Entry<K, V>> { EntrySet() {}
/*      */     
/* 1002 */     public final int size() { return HashMap.this.size; }
/* 1003 */     public final void clear() { HashMap.this.clear(); }
/*      */     
/* 1005 */     public final Iterator<Map.Entry<K, V>> iterator() { return new HashMap.EntryIterator(HashMap.this); }
/*      */     
/*      */     public final boolean contains(Object paramObject) {
/* 1008 */       if (!(paramObject instanceof Map.Entry))
/* 1009 */         return false;
/* 1010 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 1011 */       Object localObject = localEntry.getKey();
/* 1012 */       HashMap.Node localNode = HashMap.this.getNode(HashMap.hash(localObject), localObject);
/* 1013 */       return (localNode != null) && (localNode.equals(localEntry));
/*      */     }
/*      */     
/* 1016 */     public final boolean remove(Object paramObject) { if ((paramObject instanceof Map.Entry)) {
/* 1017 */         Map.Entry localEntry = (Map.Entry)paramObject;
/* 1018 */         Object localObject1 = localEntry.getKey();
/* 1019 */         Object localObject2 = localEntry.getValue();
/* 1020 */         return HashMap.this.removeNode(HashMap.hash(localObject1), localObject1, localObject2, true, true) != null;
/*      */       }
/* 1022 */       return false;
/*      */     }
/*      */     
/* 1025 */     public final Spliterator<Map.Entry<K, V>> spliterator() { return new HashMap.EntrySpliterator(HashMap.this, 0, -1, 0, 0); }
/*      */     
/*      */     public final void forEach(Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */     {
/* 1029 */       if (paramConsumer == null)
/* 1030 */         throw new NullPointerException();
/* 1031 */       HashMap.Node[] arrayOfNode; if ((HashMap.this.size > 0) && ((arrayOfNode = HashMap.this.table) != null)) {
/* 1032 */         int i = HashMap.this.modCount;
/* 1033 */         for (int j = 0; j < arrayOfNode.length; j++) {
/* 1034 */           for (HashMap.Node localNode = arrayOfNode[j]; localNode != null; localNode = localNode.next)
/* 1035 */             paramConsumer.accept(localNode);
/*      */         }
/* 1037 */         if (HashMap.this.modCount != i) {
/* 1038 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public V getOrDefault(Object paramObject, V paramV)
/*      */   {
/*      */     Node localNode;
/* 1048 */     return (localNode = getNode(hash(paramObject), paramObject)) == null ? paramV : localNode.value;
/*      */   }
/*      */   
/*      */   public V putIfAbsent(K paramK, V paramV)
/*      */   {
/* 1053 */     return (V)putVal(hash(paramK), paramK, paramV, true, true);
/*      */   }
/*      */   
/*      */   public boolean remove(Object paramObject1, Object paramObject2)
/*      */   {
/* 1058 */     return removeNode(hash(paramObject1), paramObject1, paramObject2, true, true) != null;
/*      */   }
/*      */   
/*      */   public boolean replace(K paramK, V paramV1, V paramV2) {
/*      */     Node localNode;
/*      */     Object localObject;
/* 1064 */     if (((localNode = getNode(hash(paramK), paramK)) != null) && (((localObject = localNode.value) == paramV1) || ((localObject != null) && 
/* 1065 */       (localObject.equals(paramV1))))) {
/* 1066 */       localNode.value = paramV2;
/* 1067 */       afterNodeAccess(localNode);
/* 1068 */       return true;
/*      */     }
/* 1070 */     return false;
/*      */   }
/*      */   
/*      */   public V replace(K paramK, V paramV)
/*      */   {
/*      */     Node localNode;
/* 1076 */     if ((localNode = getNode(hash(paramK), paramK)) != null) {
/* 1077 */       Object localObject = localNode.value;
/* 1078 */       localNode.value = paramV;
/* 1079 */       afterNodeAccess(localNode);
/* 1080 */       return (V)localObject;
/*      */     }
/* 1082 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction)
/*      */   {
/* 1088 */     if (paramFunction == null)
/* 1089 */       throw new NullPointerException();
/* 1090 */     int i = hash(paramK);
/*      */     
/* 1092 */     int m = 0;
/* 1093 */     TreeNode localTreeNode = null;
/* 1094 */     Object localObject1 = null;
/* 1095 */     Node[] arrayOfNode; int j; if ((this.size > this.threshold) || ((arrayOfNode = this.table) == null) || ((j = arrayOfNode.length) == 0))
/*      */     {
/* 1097 */       j = (arrayOfNode = resize()).length; }
/* 1098 */     int k; Node localNode; if ((localNode = arrayOfNode[(k = j - 1 & i)]) != null) {
/* 1099 */       if ((localNode instanceof TreeNode)) {
/* 1100 */         localObject1 = (localTreeNode = (TreeNode)localNode).getTreeNode(i, paramK);
/*      */       } else {
/* 1102 */         localObject2 = localNode;
/*      */         do { Object localObject3;
/* 1104 */           if ((((Node)localObject2).hash == i) && (((localObject3 = ((Node)localObject2).key) == paramK) || ((paramK != null) && 
/* 1105 */             (paramK.equals(localObject3))))) {
/* 1106 */             localObject1 = localObject2;
/* 1107 */             break;
/*      */           }
/* 1109 */           m++;
/* 1110 */         } while ((localObject2 = ((Node)localObject2).next) != null);
/*      */       }
/*      */       
/* 1113 */       if ((localObject1 != null) && ((localObject2 = ((Node)localObject1).value) != null)) {
/* 1114 */         afterNodeAccess((Node)localObject1);
/* 1115 */         return (V)localObject2;
/*      */       }
/*      */     }
/* 1118 */     Object localObject2 = paramFunction.apply(paramK);
/* 1119 */     if (localObject2 == null)
/* 1120 */       return null;
/* 1121 */     if (localObject1 != null) {
/* 1122 */       ((Node)localObject1).value = localObject2;
/* 1123 */       afterNodeAccess((Node)localObject1);
/* 1124 */       return (V)localObject2;
/*      */     }
/* 1126 */     if (localTreeNode != null) {
/* 1127 */       localTreeNode.putTreeVal(this, arrayOfNode, i, paramK, localObject2);
/*      */     } else {
/* 1129 */       arrayOfNode[k] = newNode(i, paramK, localObject2, localNode);
/* 1130 */       if (m >= 7)
/* 1131 */         treeifyBin(arrayOfNode, i);
/*      */     }
/* 1133 */     this.modCount += 1;
/* 1134 */     this.size += 1;
/* 1135 */     afterNodeInsertion(true);
/* 1136 */     return (V)localObject2;
/*      */   }
/*      */   
/*      */   public V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1141 */     if (paramBiFunction == null) {
/* 1142 */       throw new NullPointerException();
/*      */     }
/* 1144 */     int i = hash(paramK);
/* 1145 */     Node localNode; Object localObject1; if (((localNode = getNode(i, paramK)) != null) && ((localObject1 = localNode.value) != null))
/*      */     {
/* 1147 */       Object localObject2 = paramBiFunction.apply(paramK, localObject1);
/* 1148 */       if (localObject2 != null) {
/* 1149 */         localNode.value = localObject2;
/* 1150 */         afterNodeAccess(localNode);
/* 1151 */         return (V)localObject2;
/*      */       }
/*      */       
/* 1154 */       removeNode(i, paramK, null, false, true);
/*      */     }
/* 1156 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1162 */     if (paramBiFunction == null)
/* 1163 */       throw new NullPointerException();
/* 1164 */     int i = hash(paramK);
/*      */     
/* 1166 */     int m = 0;
/* 1167 */     TreeNode localTreeNode = null;
/* 1168 */     Object localObject1 = null;
/* 1169 */     Node[] arrayOfNode; int j; if ((this.size > this.threshold) || ((arrayOfNode = this.table) == null) || ((j = arrayOfNode.length) == 0))
/*      */     {
/* 1171 */       j = (arrayOfNode = resize()).length; }
/* 1172 */     int k; Node localNode; if ((localNode = arrayOfNode[(k = j - 1 & i)]) != null) {
/* 1173 */       if ((localNode instanceof TreeNode)) {
/* 1174 */         localObject1 = (localTreeNode = (TreeNode)localNode).getTreeNode(i, paramK);
/*      */       } else {
/* 1176 */         localObject2 = localNode;
/*      */         do {
/* 1178 */           if ((((Node)localObject2).hash == i) && (((localObject3 = ((Node)localObject2).key) == paramK) || ((paramK != null) && 
/* 1179 */             (paramK.equals(localObject3))))) {
/* 1180 */             localObject1 = localObject2;
/* 1181 */             break;
/*      */           }
/* 1183 */           m++;
/* 1184 */         } while ((localObject2 = ((Node)localObject2).next) != null);
/*      */       }
/*      */     }
/* 1187 */     Object localObject2 = localObject1 == null ? null : ((Node)localObject1).value;
/* 1188 */     Object localObject3 = paramBiFunction.apply(paramK, localObject2);
/* 1189 */     if (localObject1 != null) {
/* 1190 */       if (localObject3 != null) {
/* 1191 */         ((Node)localObject1).value = localObject3;
/* 1192 */         afterNodeAccess((Node)localObject1);
/*      */       }
/*      */       else {
/* 1195 */         removeNode(i, paramK, null, false, true);
/*      */       }
/* 1197 */     } else if (localObject3 != null) {
/* 1198 */       if (localTreeNode != null) {
/* 1199 */         localTreeNode.putTreeVal(this, arrayOfNode, i, paramK, localObject3);
/*      */       } else {
/* 1201 */         arrayOfNode[k] = newNode(i, paramK, localObject3, localNode);
/* 1202 */         if (m >= 7)
/* 1203 */           treeifyBin(arrayOfNode, i);
/*      */       }
/* 1205 */       this.modCount += 1;
/* 1206 */       this.size += 1;
/* 1207 */       afterNodeInsertion(true);
/*      */     }
/* 1209 */     return (V)localObject3;
/*      */   }
/*      */   
/*      */ 
/*      */   public V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1215 */     if (paramV == null)
/* 1216 */       throw new NullPointerException();
/* 1217 */     if (paramBiFunction == null)
/* 1218 */       throw new NullPointerException();
/* 1219 */     int i = hash(paramK);
/*      */     
/* 1221 */     int m = 0;
/* 1222 */     TreeNode localTreeNode = null;
/* 1223 */     Object localObject1 = null;
/* 1224 */     Node[] arrayOfNode; int j; if ((this.size > this.threshold) || ((arrayOfNode = this.table) == null) || ((j = arrayOfNode.length) == 0))
/*      */     {
/* 1226 */       j = (arrayOfNode = resize()).length; }
/* 1227 */     int k; Node localNode; Object localObject2; if ((localNode = arrayOfNode[(k = j - 1 & i)]) != null) {
/* 1228 */       if ((localNode instanceof TreeNode)) {
/* 1229 */         localObject1 = (localTreeNode = (TreeNode)localNode).getTreeNode(i, paramK);
/*      */       } else {
/* 1231 */         localObject2 = localNode;
/*      */         do { Object localObject3;
/* 1233 */           if ((((Node)localObject2).hash == i) && (((localObject3 = ((Node)localObject2).key) == paramK) || ((paramK != null) && 
/* 1234 */             (paramK.equals(localObject3))))) {
/* 1235 */             localObject1 = localObject2;
/* 1236 */             break;
/*      */           }
/* 1238 */           m++;
/* 1239 */         } while ((localObject2 = ((Node)localObject2).next) != null);
/*      */       }
/*      */     }
/* 1242 */     if (localObject1 != null)
/*      */     {
/* 1244 */       if (((Node)localObject1).value != null) {
/* 1245 */         localObject2 = paramBiFunction.apply(((Node)localObject1).value, paramV);
/*      */       } else
/* 1247 */         localObject2 = paramV;
/* 1248 */       if (localObject2 != null) {
/* 1249 */         ((Node)localObject1).value = localObject2;
/* 1250 */         afterNodeAccess((Node)localObject1);
/*      */       }
/*      */       else {
/* 1253 */         removeNode(i, paramK, null, false, true); }
/* 1254 */       return (V)localObject2;
/*      */     }
/* 1256 */     if (paramV != null) {
/* 1257 */       if (localTreeNode != null) {
/* 1258 */         localTreeNode.putTreeVal(this, arrayOfNode, i, paramK, paramV);
/*      */       } else {
/* 1260 */         arrayOfNode[k] = newNode(i, paramK, paramV, localNode);
/* 1261 */         if (m >= 7)
/* 1262 */           treeifyBin(arrayOfNode, i);
/*      */       }
/* 1264 */       this.modCount += 1;
/* 1265 */       this.size += 1;
/* 1266 */       afterNodeInsertion(true);
/*      */     }
/* 1268 */     return paramV;
/*      */   }
/*      */   
/*      */ 
/*      */   public void forEach(BiConsumer<? super K, ? super V> paramBiConsumer)
/*      */   {
/* 1274 */     if (paramBiConsumer == null)
/* 1275 */       throw new NullPointerException();
/* 1276 */     Node[] arrayOfNode; if ((this.size > 0) && ((arrayOfNode = this.table) != null)) {
/* 1277 */       int i = this.modCount;
/* 1278 */       for (int j = 0; j < arrayOfNode.length; j++) {
/* 1279 */         for (Node localNode = arrayOfNode[j]; localNode != null; localNode = localNode.next)
/* 1280 */           paramBiConsumer.accept(localNode.key, localNode.value);
/*      */       }
/* 1282 */       if (this.modCount != i) {
/* 1283 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction)
/*      */   {
/* 1290 */     if (paramBiFunction == null)
/* 1291 */       throw new NullPointerException();
/* 1292 */     Node[] arrayOfNode; if ((this.size > 0) && ((arrayOfNode = this.table) != null)) {
/* 1293 */       int i = this.modCount;
/* 1294 */       for (int j = 0; j < arrayOfNode.length; j++) {
/* 1295 */         for (Node localNode = arrayOfNode[j]; localNode != null; localNode = localNode.next) {
/* 1296 */           localNode.value = paramBiFunction.apply(localNode.key, localNode.value);
/*      */         }
/*      */       }
/* 1299 */       if (this.modCount != i) {
/* 1300 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     HashMap localHashMap;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1318 */       localHashMap = (HashMap)super.clone();
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 1321 */       throw new InternalError(localCloneNotSupportedException);
/*      */     }
/* 1323 */     localHashMap.reinitialize();
/* 1324 */     localHashMap.putMapEntries(this, false);
/* 1325 */     return localHashMap;
/*      */   }
/*      */   
/*      */ 
/* 1329 */   final float loadFactor() { return this.loadFactor; }
/*      */   
/* 1331 */   final int capacity() { return this.threshold > 0 ? this.threshold : this.table != null ? this.table.length : 16; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1349 */     int i = capacity();
/*      */     
/* 1351 */     paramObjectOutputStream.defaultWriteObject();
/* 1352 */     paramObjectOutputStream.writeInt(i);
/* 1353 */     paramObjectOutputStream.writeInt(this.size);
/* 1354 */     internalWriteEntries(paramObjectOutputStream);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1364 */     paramObjectInputStream.defaultReadObject();
/* 1365 */     reinitialize();
/* 1366 */     if ((this.loadFactor <= 0.0F) || (Float.isNaN(this.loadFactor))) {
/* 1367 */       throw new InvalidObjectException("Illegal load factor: " + this.loadFactor);
/*      */     }
/* 1369 */     paramObjectInputStream.readInt();
/* 1370 */     int i = paramObjectInputStream.readInt();
/* 1371 */     if (i < 0) {
/* 1372 */       throw new InvalidObjectException("Illegal mappings count: " + i);
/*      */     }
/* 1374 */     if (i > 0)
/*      */     {
/*      */ 
/* 1377 */       float f1 = Math.min(Math.max(0.25F, this.loadFactor), 4.0F);
/* 1378 */       float f2 = i / f1 + 1.0F;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1383 */       int j = f2 >= 1.07374182E9F ? 1073741824 : f2 < 16.0F ? 16 : tableSizeFor((int)f2);
/* 1384 */       float f3 = j * f1;
/* 1385 */       this.threshold = ((j < 1073741824) && (f3 < 1.07374182E9F) ? (int)f3 : Integer.MAX_VALUE);
/*      */       
/*      */ 
/* 1388 */       Node[] arrayOfNode = (Node[])new Node[j];
/* 1389 */       this.table = arrayOfNode;
/*      */       
/*      */ 
/* 1392 */       for (int k = 0; k < i; k++)
/*      */       {
/* 1394 */         Object localObject1 = paramObjectInputStream.readObject();
/*      */         
/* 1396 */         Object localObject2 = paramObjectInputStream.readObject();
/* 1397 */         putVal(hash(localObject1), localObject1, localObject2, false, false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   abstract class HashIterator
/*      */   {
/*      */     HashMap.Node<K, V> next;
/*      */     HashMap.Node<K, V> current;
/*      */     int expectedModCount;
/*      */     int index;
/*      */     
/*      */     HashIterator()
/*      */     {
/* 1412 */       this.expectedModCount = HashMap.this.modCount;
/* 1413 */       HashMap.Node[] arrayOfNode = HashMap.this.table;
/* 1414 */       this.current = (this.next = null);
/* 1415 */       this.index = 0;
/* 1416 */       while ((arrayOfNode != null) && (HashMap.this.size > 0) && 
/* 1417 */         (this.index < arrayOfNode.length) && ((this.next = arrayOfNode[(this.index++)]) == null)) {}
/*      */     }
/*      */     
/*      */     public final boolean hasNext()
/*      */     {
/* 1422 */       return this.next != null;
/*      */     }
/*      */     
/*      */     final HashMap.Node<K, V> nextNode()
/*      */     {
/* 1427 */       HashMap.Node localNode = this.next;
/* 1428 */       if (HashMap.this.modCount != this.expectedModCount)
/* 1429 */         throw new ConcurrentModificationException();
/* 1430 */       if (localNode == null)
/* 1431 */         throw new NoSuchElementException();
/* 1432 */       HashMap.Node[] arrayOfNode; while (((this.next = (this.current = localNode).next) == null) && ((arrayOfNode = HashMap.this.table) != null) && 
/* 1433 */         (this.index < arrayOfNode.length) && ((this.next = arrayOfNode[(this.index++)]) == null)) {}
/*      */       
/* 1435 */       return localNode;
/*      */     }
/*      */     
/*      */     public final void remove() {
/* 1439 */       HashMap.Node localNode = this.current;
/* 1440 */       if (localNode == null)
/* 1441 */         throw new IllegalStateException();
/* 1442 */       if (HashMap.this.modCount != this.expectedModCount)
/* 1443 */         throw new ConcurrentModificationException();
/* 1444 */       this.current = null;
/* 1445 */       Object localObject = localNode.key;
/* 1446 */       HashMap.this.removeNode(HashMap.hash(localObject), localObject, null, false, false);
/* 1447 */       this.expectedModCount = HashMap.this.modCount;
/*      */     }
/*      */   }
/*      */   
/* 1451 */   final class KeyIterator extends HashMap<K, V>.HashIterator implements Iterator<K> { KeyIterator() { super(); }
/*      */     
/* 1453 */     public final K next() { return (K)nextNode().key; }
/*      */   }
/*      */   
/* 1456 */   final class ValueIterator extends HashMap<K, V>.HashIterator implements Iterator<V> { ValueIterator() { super(); }
/*      */     
/* 1458 */     public final V next() { return (V)nextNode().value; }
/*      */   }
/*      */   
/* 1461 */   final class EntryIterator extends HashMap<K, V>.HashIterator implements Iterator<Map.Entry<K, V>> { EntryIterator() { super(); }
/*      */     
/* 1463 */     public final Map.Entry<K, V> next() { return nextNode(); }
/*      */   }
/*      */   
/*      */ 
/*      */   static class HashMapSpliterator<K, V>
/*      */   {
/*      */     final HashMap<K, V> map;
/*      */     
/*      */     HashMap.Node<K, V> current;
/*      */     
/*      */     int index;
/*      */     int fence;
/*      */     int est;
/*      */     int expectedModCount;
/*      */     
/*      */     HashMapSpliterator(HashMap<K, V> paramHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1480 */       this.map = paramHashMap;
/* 1481 */       this.index = paramInt1;
/* 1482 */       this.fence = paramInt2;
/* 1483 */       this.est = paramInt3;
/* 1484 */       this.expectedModCount = paramInt4;
/*      */     }
/*      */     
/*      */     final int getFence() {
/*      */       int i;
/* 1489 */       if ((i = this.fence) < 0) {
/* 1490 */         HashMap localHashMap = this.map;
/* 1491 */         this.est = localHashMap.size;
/* 1492 */         this.expectedModCount = localHashMap.modCount;
/* 1493 */         HashMap.Node[] arrayOfNode = localHashMap.table;
/* 1494 */         i = this.fence = arrayOfNode == null ? 0 : arrayOfNode.length;
/*      */       }
/* 1496 */       return i;
/*      */     }
/*      */     
/*      */     public final long estimateSize() {
/* 1500 */       getFence();
/* 1501 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends HashMap.HashMapSpliterator<K, V> implements Spliterator<K>
/*      */   {
/*      */     KeySpliterator(HashMap<K, V> paramHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1510 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public KeySpliterator<K, V> trySplit() {
/* 1514 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1515 */       return (j >= k) || (this.current != null) ? null : new KeySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super K> paramConsumer)
/*      */     {
/* 1522 */       if (paramConsumer == null)
/* 1523 */         throw new NullPointerException();
/* 1524 */       HashMap localHashMap = this.map;
/* 1525 */       HashMap.Node[] arrayOfNode = localHashMap.table;
/* 1526 */       int j; int k; if ((j = this.fence) < 0) {
/* 1527 */         k = this.expectedModCount = localHashMap.modCount;
/* 1528 */         j = this.fence = arrayOfNode == null ? 0 : arrayOfNode.length;
/*      */       }
/*      */       else {
/* 1531 */         k = this.expectedModCount; }
/* 1532 */       int i; if ((arrayOfNode != null) && (arrayOfNode.length >= j) && ((i = this.index) >= 0) && ((i < (this.index = j)) || (this.current != null)))
/*      */       {
/* 1534 */         HashMap.Node localNode = this.current;
/* 1535 */         this.current = null;
/*      */         do {
/* 1537 */           if (localNode == null) {
/* 1538 */             localNode = arrayOfNode[(i++)];
/*      */           } else {
/* 1540 */             paramConsumer.accept(localNode.key);
/* 1541 */             localNode = localNode.next;
/*      */           }
/* 1543 */         } while ((localNode != null) || (i < j));
/* 1544 */         if (localHashMap.modCount != k) {
/* 1545 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> paramConsumer) {
/* 1551 */       if (paramConsumer == null)
/* 1552 */         throw new NullPointerException();
/* 1553 */       HashMap.Node[] arrayOfNode = this.map.table;
/* 1554 */       int i; if ((arrayOfNode != null) && (arrayOfNode.length >= (i = getFence())) && (this.index >= 0)) {
/* 1555 */         while ((this.current != null) || (this.index < i)) {
/* 1556 */           if (this.current == null) {
/* 1557 */             this.current = arrayOfNode[(this.index++)];
/*      */           } else {
/* 1559 */             Object localObject = this.current.key;
/* 1560 */             this.current = this.current.next;
/* 1561 */             paramConsumer.accept(localObject);
/* 1562 */             if (this.map.modCount != this.expectedModCount)
/* 1563 */               throw new ConcurrentModificationException();
/* 1564 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 1568 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1572 */       return ((this.fence < 0) || (this.est == this.map.size) ? 64 : 0) | 0x1;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends HashMap.HashMapSpliterator<K, V>
/*      */     implements Spliterator<V>
/*      */   {
/*      */     ValueSpliterator(HashMap<K, V> paramHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1582 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public ValueSpliterator<K, V> trySplit() {
/* 1586 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1587 */       return (j >= k) || (this.current != null) ? null : new ValueSpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super V> paramConsumer)
/*      */     {
/* 1594 */       if (paramConsumer == null)
/* 1595 */         throw new NullPointerException();
/* 1596 */       HashMap localHashMap = this.map;
/* 1597 */       HashMap.Node[] arrayOfNode = localHashMap.table;
/* 1598 */       int j; int k; if ((j = this.fence) < 0) {
/* 1599 */         k = this.expectedModCount = localHashMap.modCount;
/* 1600 */         j = this.fence = arrayOfNode == null ? 0 : arrayOfNode.length;
/*      */       }
/*      */       else {
/* 1603 */         k = this.expectedModCount; }
/* 1604 */       int i; if ((arrayOfNode != null) && (arrayOfNode.length >= j) && ((i = this.index) >= 0) && ((i < (this.index = j)) || (this.current != null)))
/*      */       {
/* 1606 */         HashMap.Node localNode = this.current;
/* 1607 */         this.current = null;
/*      */         do {
/* 1609 */           if (localNode == null) {
/* 1610 */             localNode = arrayOfNode[(i++)];
/*      */           } else {
/* 1612 */             paramConsumer.accept(localNode.value);
/* 1613 */             localNode = localNode.next;
/*      */           }
/* 1615 */         } while ((localNode != null) || (i < j));
/* 1616 */         if (localHashMap.modCount != k) {
/* 1617 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super V> paramConsumer) {
/* 1623 */       if (paramConsumer == null)
/* 1624 */         throw new NullPointerException();
/* 1625 */       HashMap.Node[] arrayOfNode = this.map.table;
/* 1626 */       int i; if ((arrayOfNode != null) && (arrayOfNode.length >= (i = getFence())) && (this.index >= 0)) {
/* 1627 */         while ((this.current != null) || (this.index < i)) {
/* 1628 */           if (this.current == null) {
/* 1629 */             this.current = arrayOfNode[(this.index++)];
/*      */           } else {
/* 1631 */             Object localObject = this.current.value;
/* 1632 */             this.current = this.current.next;
/* 1633 */             paramConsumer.accept(localObject);
/* 1634 */             if (this.map.modCount != this.expectedModCount)
/* 1635 */               throw new ConcurrentModificationException();
/* 1636 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 1640 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1644 */       return (this.fence < 0) || (this.est == this.map.size) ? 64 : 0;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends HashMap.HashMapSpliterator<K, V> implements Spliterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySpliterator(HashMap<K, V> paramHashMap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 1653 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */     
/*      */     public EntrySpliterator<K, V> trySplit() {
/* 1657 */       int i = getFence();int j = this.index;int k = j + i >>> 1;
/* 1658 */       return (j >= k) || (this.current != null) ? null : new EntrySpliterator(this.map, j, this.index = k, this.est >>>= 1, this.expectedModCount);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super Map.Entry<K, V>> paramConsumer)
/*      */     {
/* 1665 */       if (paramConsumer == null)
/* 1666 */         throw new NullPointerException();
/* 1667 */       HashMap localHashMap = this.map;
/* 1668 */       HashMap.Node[] arrayOfNode = localHashMap.table;
/* 1669 */       int j; int k; if ((j = this.fence) < 0) {
/* 1670 */         k = this.expectedModCount = localHashMap.modCount;
/* 1671 */         j = this.fence = arrayOfNode == null ? 0 : arrayOfNode.length;
/*      */       }
/*      */       else {
/* 1674 */         k = this.expectedModCount; }
/* 1675 */       int i; if ((arrayOfNode != null) && (arrayOfNode.length >= j) && ((i = this.index) >= 0) && ((i < (this.index = j)) || (this.current != null)))
/*      */       {
/* 1677 */         HashMap.Node localNode = this.current;
/* 1678 */         this.current = null;
/*      */         do {
/* 1680 */           if (localNode == null) {
/* 1681 */             localNode = arrayOfNode[(i++)];
/*      */           } else {
/* 1683 */             paramConsumer.accept(localNode);
/* 1684 */             localNode = localNode.next;
/*      */           }
/* 1686 */         } while ((localNode != null) || (i < j));
/* 1687 */         if (localHashMap.modCount != k) {
/* 1688 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> paramConsumer) {
/* 1694 */       if (paramConsumer == null)
/* 1695 */         throw new NullPointerException();
/* 1696 */       HashMap.Node[] arrayOfNode = this.map.table;
/* 1697 */       int i; if ((arrayOfNode != null) && (arrayOfNode.length >= (i = getFence())) && (this.index >= 0)) {
/* 1698 */         while ((this.current != null) || (this.index < i)) {
/* 1699 */           if (this.current == null) {
/* 1700 */             this.current = arrayOfNode[(this.index++)];
/*      */           } else {
/* 1702 */             HashMap.Node localNode = this.current;
/* 1703 */             this.current = this.current.next;
/* 1704 */             paramConsumer.accept(localNode);
/* 1705 */             if (this.map.modCount != this.expectedModCount)
/* 1706 */               throw new ConcurrentModificationException();
/* 1707 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 1711 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1715 */       return ((this.fence < 0) || (this.est == this.map.size) ? 64 : 0) | 0x1;
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
/*      */   Node<K, V> newNode(int paramInt, K paramK, V paramV, Node<K, V> paramNode)
/*      */   {
/* 1734 */     return new Node(paramInt, paramK, paramV, paramNode);
/*      */   }
/*      */   
/*      */   Node<K, V> replacementNode(Node<K, V> paramNode1, Node<K, V> paramNode2)
/*      */   {
/* 1739 */     return new Node(paramNode1.hash, paramNode1.key, paramNode1.value, paramNode2);
/*      */   }
/*      */   
/*      */   TreeNode<K, V> newTreeNode(int paramInt, K paramK, V paramV, Node<K, V> paramNode)
/*      */   {
/* 1744 */     return new TreeNode(paramInt, paramK, paramV, paramNode);
/*      */   }
/*      */   
/*      */   TreeNode<K, V> replacementTreeNode(Node<K, V> paramNode1, Node<K, V> paramNode2)
/*      */   {
/* 1749 */     return new TreeNode(paramNode1.hash, paramNode1.key, paramNode1.value, paramNode2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void reinitialize()
/*      */   {
/* 1756 */     this.table = null;
/* 1757 */     this.entrySet = null;
/* 1758 */     this.keySet = null;
/* 1759 */     this.values = null;
/* 1760 */     this.modCount = 0;
/* 1761 */     this.threshold = 0;
/* 1762 */     this.size = 0;
/*      */   }
/*      */   
/*      */   void afterNodeAccess(Node<K, V> paramNode) {}
/*      */   
/*      */   void afterNodeInsertion(boolean paramBoolean) {}
/*      */   
/*      */   void afterNodeRemoval(Node<K, V> paramNode) {}
/*      */   
/*      */   void internalWriteEntries(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     Node[] arrayOfNode;
/* 1773 */     if ((this.size > 0) && ((arrayOfNode = this.table) != null)) {
/* 1774 */       for (int i = 0; i < arrayOfNode.length; i++) {
/* 1775 */         for (Node localNode = arrayOfNode[i]; localNode != null; localNode = localNode.next) {
/* 1776 */           paramObjectOutputStream.writeObject(localNode.key);
/* 1777 */           paramObjectOutputStream.writeObject(localNode.value);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class TreeNode<K, V>
/*      */     extends LinkedHashMap.Entry<K, V>
/*      */   {
/*      */     TreeNode<K, V> parent;
/*      */     
/*      */     TreeNode<K, V> left;
/*      */     
/*      */     TreeNode<K, V> right;
/*      */     
/*      */     TreeNode<K, V> prev;
/*      */     boolean red;
/*      */     
/*      */     TreeNode(int paramInt, K paramK, V paramV, HashMap.Node<K, V> paramNode)
/*      */     {
/* 1798 */       super(paramK, paramV, paramNode);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final TreeNode<K, V> root()
/*      */     {
/* 1805 */       Object localObject = this;
/* 1806 */       for (;;) { TreeNode localTreeNode; if ((localTreeNode = ((TreeNode)localObject).parent) == null)
/* 1807 */           return (TreeNode<K, V>)localObject;
/* 1808 */         localObject = localTreeNode;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     static <K, V> void moveRootToFront(HashMap.Node<K, V>[] paramArrayOfNode, TreeNode<K, V> paramTreeNode)
/*      */     {
/*      */       int i;
/*      */       
/* 1817 */       if ((paramTreeNode != null) && (paramArrayOfNode != null) && ((i = paramArrayOfNode.length) > 0)) {
/* 1818 */         int j = i - 1 & paramTreeNode.hash;
/* 1819 */         TreeNode localTreeNode1 = (TreeNode)paramArrayOfNode[j];
/* 1820 */         if (paramTreeNode != localTreeNode1)
/*      */         {
/* 1822 */           paramArrayOfNode[j] = paramTreeNode;
/* 1823 */           TreeNode localTreeNode2 = paramTreeNode.prev;
/* 1824 */           HashMap.Node localNode; if ((localNode = paramTreeNode.next) != null)
/* 1825 */             ((TreeNode)localNode).prev = localTreeNode2;
/* 1826 */           if (localTreeNode2 != null)
/* 1827 */             localTreeNode2.next = localNode;
/* 1828 */           if (localTreeNode1 != null)
/* 1829 */             localTreeNode1.prev = paramTreeNode;
/* 1830 */           paramTreeNode.next = localTreeNode1;
/* 1831 */           paramTreeNode.prev = null;
/*      */         }
/* 1833 */         assert (checkInvariants(paramTreeNode));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final TreeNode<K, V> find(int paramInt, Object paramObject, Class<?> paramClass)
/*      */     {
/* 1843 */       Object localObject1 = this;
/*      */       do
/*      */       {
/* 1846 */         TreeNode localTreeNode1 = ((TreeNode)localObject1).left;TreeNode localTreeNode2 = ((TreeNode)localObject1).right;
/* 1847 */         int i; if ((i = ((TreeNode)localObject1).hash) > paramInt) {
/* 1848 */           localObject1 = localTreeNode1;
/* 1849 */         } else if (i < paramInt) {
/* 1850 */           localObject1 = localTreeNode2; } else { Object localObject2;
/* 1851 */           if (((localObject2 = ((TreeNode)localObject1).key) == paramObject) || ((paramObject != null) && (paramObject.equals(localObject2))))
/* 1852 */             return (TreeNode<K, V>)localObject1;
/* 1853 */           if (localTreeNode1 == null) {
/* 1854 */             localObject1 = localTreeNode2;
/* 1855 */           } else if (localTreeNode2 == null) {
/* 1856 */             localObject1 = localTreeNode1; } else { int j;
/* 1857 */             if (((paramClass != null) || 
/* 1858 */               ((paramClass = HashMap.comparableClassFor(paramObject)) != null)) && 
/* 1859 */               ((j = HashMap.compareComparables(paramClass, paramObject, localObject2)) != 0)) {
/* 1860 */               localObject1 = j < 0 ? localTreeNode1 : localTreeNode2; } else { TreeNode localTreeNode3;
/* 1861 */               if ((localTreeNode3 = localTreeNode2.find(paramInt, paramObject, paramClass)) != null) {
/* 1862 */                 return localTreeNode3;
/*      */               }
/* 1864 */               localObject1 = localTreeNode1;
/* 1865 */             } } } } while (localObject1 != null);
/* 1866 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final TreeNode<K, V> getTreeNode(int paramInt, Object paramObject)
/*      */     {
/* 1873 */       return (this.parent != null ? root() : this).find(paramInt, paramObject, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     static int tieBreakOrder(Object paramObject1, Object paramObject2)
/*      */     {
/*      */       int i;
/*      */       
/*      */ 
/*      */ 
/* 1885 */       if ((paramObject1 == null) || (paramObject2 == null) || 
/*      */       
/* 1887 */         ((i = paramObject1.getClass().getName().compareTo(paramObject2.getClass().getName())) == 0)) {
/* 1888 */         i = System.identityHashCode(paramObject1) <= System.identityHashCode(paramObject2) ? -1 : 1;
/*      */       }
/* 1890 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final void treeify(HashMap.Node<K, V>[] paramArrayOfNode)
/*      */     {
/* 1898 */       Object localObject1 = null;
/* 1899 */       TreeNode localTreeNode; for (Object localObject2 = this; localObject2 != null; localObject2 = localTreeNode) {
/* 1900 */         localTreeNode = (TreeNode)((TreeNode)localObject2).next;
/* 1901 */         ((TreeNode)localObject2).left = (((TreeNode)localObject2).right = null);
/* 1902 */         if (localObject1 == null) {
/* 1903 */           ((TreeNode)localObject2).parent = null;
/* 1904 */           ((TreeNode)localObject2).red = false;
/* 1905 */           localObject1 = localObject2;
/*      */         }
/*      */         else {
/* 1908 */           Object localObject3 = ((TreeNode)localObject2).key;
/* 1909 */           int i = ((TreeNode)localObject2).hash;
/* 1910 */           Class localClass = null;
/* 1911 */           Object localObject4 = localObject1;
/*      */           for (;;) {
/* 1913 */             Object localObject5 = ((TreeNode)localObject4).key;
/* 1914 */             int k; int j; if ((k = ((TreeNode)localObject4).hash) > i) {
/* 1915 */               j = -1;
/* 1916 */             } else if (k < i) {
/* 1917 */               j = 1;
/* 1918 */             } else if (((localClass == null) && 
/* 1919 */               ((localClass = HashMap.comparableClassFor(localObject3)) == null)) || 
/* 1920 */               ((j = HashMap.compareComparables(localClass, localObject3, localObject5)) == 0)) {
/* 1921 */               j = tieBreakOrder(localObject3, localObject5);
/*      */             }
/* 1923 */             Object localObject6 = localObject4;
/* 1924 */             if ((localObject4 = j <= 0 ? ((TreeNode)localObject4).left : ((TreeNode)localObject4).right) == null) {
/* 1925 */               ((TreeNode)localObject2).parent = ((TreeNode)localObject6);
/* 1926 */               if (j <= 0) {
/* 1927 */                 ((TreeNode)localObject6).left = ((TreeNode)localObject2);
/*      */               } else
/* 1929 */                 ((TreeNode)localObject6).right = ((TreeNode)localObject2);
/* 1930 */               localObject1 = balanceInsertion((TreeNode)localObject1, (TreeNode)localObject2);
/* 1931 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1936 */       moveRootToFront(paramArrayOfNode, (TreeNode)localObject1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final HashMap.Node<K, V> untreeify(HashMap<K, V> paramHashMap)
/*      */     {
/* 1944 */       Object localObject1 = null;Object localObject2 = null;
/* 1945 */       for (Object localObject3 = this; localObject3 != null; localObject3 = ((HashMap.Node)localObject3).next) {
/* 1946 */         HashMap.Node localNode = paramHashMap.replacementNode((HashMap.Node)localObject3, null);
/* 1947 */         if (localObject2 == null) {
/* 1948 */           localObject1 = localNode;
/*      */         } else
/* 1950 */           ((HashMap.Node)localObject2).next = localNode;
/* 1951 */         localObject2 = localNode;
/*      */       }
/* 1953 */       return (HashMap.Node<K, V>)localObject1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final TreeNode<K, V> putTreeVal(HashMap<K, V> paramHashMap, HashMap.Node<K, V>[] paramArrayOfNode, int paramInt, K paramK, V paramV)
/*      */     {
/* 1961 */       Class localClass = null;
/* 1962 */       int i = 0;
/* 1963 */       TreeNode localTreeNode1 = this.parent != null ? root() : this;
/* 1964 */       TreeNode localTreeNode2 = localTreeNode1;
/*      */       for (;;) { int k;
/* 1966 */         int j; Object localObject2; if ((k = localTreeNode2.hash) > paramInt) {
/* 1967 */           j = -1;
/* 1968 */         } else if (k < paramInt) {
/* 1969 */           j = 1; } else { Object localObject1;
/* 1970 */           if (((localObject1 = localTreeNode2.key) == paramK) || ((paramK != null) && (paramK.equals(localObject1))))
/* 1971 */             return localTreeNode2;
/* 1972 */           if (((localClass == null) && 
/* 1973 */             ((localClass = HashMap.comparableClassFor(paramK)) == null)) || 
/* 1974 */             ((j = HashMap.compareComparables(localClass, paramK, localObject1)) == 0)) {
/* 1975 */             if (i == 0)
/*      */             {
/* 1977 */               i = 1;
/* 1978 */               if (((localObject2 = localTreeNode2.left) == null) || 
/* 1979 */                 ((localTreeNode3 = ((TreeNode)localObject2).find(paramInt, paramK, localClass)) == null)) { if ((localObject2 = localTreeNode2.right) != null)
/*      */                 {
/* 1981 */                   if ((localTreeNode3 = ((TreeNode)localObject2).find(paramInt, paramK, localClass)) == null) {} }
/* 1982 */               } else return localTreeNode3;
/*      */             }
/* 1984 */             j = tieBreakOrder(paramK, localObject1);
/*      */           }
/*      */         }
/* 1987 */         TreeNode localTreeNode3 = localTreeNode2;
/* 1988 */         if ((localTreeNode2 = j <= 0 ? localTreeNode2.left : localTreeNode2.right) == null) {
/* 1989 */           localObject2 = localTreeNode3.next;
/* 1990 */           TreeNode localTreeNode4 = paramHashMap.newTreeNode(paramInt, paramK, paramV, (HashMap.Node)localObject2);
/* 1991 */           if (j <= 0) {
/* 1992 */             localTreeNode3.left = localTreeNode4;
/*      */           } else
/* 1994 */             localTreeNode3.right = localTreeNode4;
/* 1995 */           localTreeNode3.next = localTreeNode4;
/* 1996 */           localTreeNode4.parent = (localTreeNode4.prev = localTreeNode3);
/* 1997 */           if (localObject2 != null)
/* 1998 */             ((TreeNode)localObject2).prev = localTreeNode4;
/* 1999 */           moveRootToFront(paramArrayOfNode, balanceInsertion(localTreeNode1, localTreeNode4));
/* 2000 */           return null;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final void removeTreeNode(HashMap<K, V> paramHashMap, HashMap.Node<K, V>[] paramArrayOfNode, boolean paramBoolean)
/*      */     {
/*      */       int i;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2018 */       if ((paramArrayOfNode == null) || ((i = paramArrayOfNode.length) == 0))
/* 2019 */         return;
/* 2020 */       int j = i - 1 & this.hash;
/* 2021 */       Object localObject1 = (TreeNode)paramArrayOfNode[j];Object localObject2 = localObject1;
/* 2022 */       TreeNode localTreeNode2 = (TreeNode)this.next;TreeNode localTreeNode3 = this.prev;
/* 2023 */       if (localTreeNode3 == null) {
/* 2024 */         paramArrayOfNode[j] = (localObject1 = localTreeNode2);
/*      */       } else
/* 2026 */         localTreeNode3.next = localTreeNode2;
/* 2027 */       if (localTreeNode2 != null)
/* 2028 */         localTreeNode2.prev = localTreeNode3;
/* 2029 */       if (localObject1 == null)
/* 2030 */         return;
/* 2031 */       if (((TreeNode)localObject2).parent != null)
/* 2032 */         localObject2 = ((TreeNode)localObject2).root();
/* 2033 */       TreeNode localTreeNode1; if ((localObject2 == null) || (((TreeNode)localObject2).right == null) || ((localTreeNode1 = ((TreeNode)localObject2).left) == null) || (localTreeNode1.left == null))
/*      */       {
/* 2035 */         paramArrayOfNode[j] = ((TreeNode)localObject1).untreeify(paramHashMap);
/* 2036 */         return;
/*      */       }
/* 2038 */       TreeNode localTreeNode4 = this;TreeNode localTreeNode5 = this.left;TreeNode localTreeNode6 = this.right;
/* 2039 */       TreeNode localTreeNode8; TreeNode localTreeNode7; if ((localTreeNode5 != null) && (localTreeNode6 != null)) {
/* 2040 */         localObject3 = localTreeNode6;
/* 2041 */         while ((localTreeNode8 = ((TreeNode)localObject3).left) != null)
/* 2042 */           localObject3 = localTreeNode8;
/* 2043 */         boolean bool = ((TreeNode)localObject3).red;((TreeNode)localObject3).red = localTreeNode4.red;localTreeNode4.red = bool;
/* 2044 */         TreeNode localTreeNode9 = ((TreeNode)localObject3).right;
/* 2045 */         TreeNode localTreeNode10 = localTreeNode4.parent;
/* 2046 */         if (localObject3 == localTreeNode6) {
/* 2047 */           localTreeNode4.parent = ((TreeNode)localObject3);
/* 2048 */           ((TreeNode)localObject3).right = localTreeNode4;
/*      */         }
/*      */         else {
/* 2051 */           TreeNode localTreeNode11 = ((TreeNode)localObject3).parent;
/* 2052 */           if ((localTreeNode4.parent = localTreeNode11) != null) {
/* 2053 */             if (localObject3 == localTreeNode11.left) {
/* 2054 */               localTreeNode11.left = localTreeNode4;
/*      */             } else
/* 2056 */               localTreeNode11.right = localTreeNode4;
/*      */           }
/* 2058 */           if ((((TreeNode)localObject3).right = localTreeNode6) != null)
/* 2059 */             localTreeNode6.parent = ((TreeNode)localObject3);
/*      */         }
/* 2061 */         localTreeNode4.left = null;
/* 2062 */         if ((localTreeNode4.right = localTreeNode9) != null)
/* 2063 */           localTreeNode9.parent = localTreeNode4;
/* 2064 */         if ((((TreeNode)localObject3).left = localTreeNode5) != null)
/* 2065 */           localTreeNode5.parent = ((TreeNode)localObject3);
/* 2066 */         if ((((TreeNode)localObject3).parent = localTreeNode10) == null) {
/* 2067 */           localObject2 = localObject3;
/* 2068 */         } else if (localTreeNode4 == localTreeNode10.left) {
/* 2069 */           localTreeNode10.left = ((TreeNode)localObject3);
/*      */         } else
/* 2071 */           localTreeNode10.right = ((TreeNode)localObject3);
/* 2072 */         if (localTreeNode9 != null) {
/* 2073 */           localTreeNode7 = localTreeNode9;
/*      */         } else {
/* 2075 */           localTreeNode7 = localTreeNode4;
/*      */         }
/* 2077 */       } else if (localTreeNode5 != null) {
/* 2078 */         localTreeNode7 = localTreeNode5;
/* 2079 */       } else if (localTreeNode6 != null) {
/* 2080 */         localTreeNode7 = localTreeNode6;
/*      */       } else {
/* 2082 */         localTreeNode7 = localTreeNode4; }
/* 2083 */       if (localTreeNode7 != localTreeNode4) {
/* 2084 */         localObject3 = localTreeNode7.parent = localTreeNode4.parent;
/* 2085 */         if (localObject3 == null) {
/* 2086 */           localObject2 = localTreeNode7;
/* 2087 */         } else if (localTreeNode4 == ((TreeNode)localObject3).left) {
/* 2088 */           ((TreeNode)localObject3).left = localTreeNode7;
/*      */         } else
/* 2090 */           ((TreeNode)localObject3).right = localTreeNode7;
/* 2091 */         localTreeNode4.left = (localTreeNode4.right = localTreeNode4.parent = null);
/*      */       }
/*      */       
/* 2094 */       Object localObject3 = localTreeNode4.red ? localObject2 : balanceDeletion((TreeNode)localObject2, localTreeNode7);
/*      */       
/* 2096 */       if (localTreeNode7 == localTreeNode4) {
/* 2097 */         localTreeNode8 = localTreeNode4.parent;
/* 2098 */         localTreeNode4.parent = null;
/* 2099 */         if (localTreeNode8 != null) {
/* 2100 */           if (localTreeNode4 == localTreeNode8.left) {
/* 2101 */             localTreeNode8.left = null;
/* 2102 */           } else if (localTreeNode4 == localTreeNode8.right)
/* 2103 */             localTreeNode8.right = null;
/*      */         }
/*      */       }
/* 2106 */       if (paramBoolean) {
/* 2107 */         moveRootToFront(paramArrayOfNode, (TreeNode)localObject3);
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
/*      */     final void split(HashMap<K, V> paramHashMap, HashMap.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2)
/*      */     {
/* 2121 */       TreeNode localTreeNode1 = this;
/*      */       
/* 2123 */       Object localObject1 = null;Object localObject2 = null;
/* 2124 */       Object localObject3 = null;Object localObject4 = null;
/* 2125 */       int i = 0;int j = 0;
/* 2126 */       TreeNode localTreeNode2; for (Object localObject5 = localTreeNode1; localObject5 != null; localObject5 = localTreeNode2) {
/* 2127 */         localTreeNode2 = (TreeNode)((TreeNode)localObject5).next;
/* 2128 */         ((TreeNode)localObject5).next = null;
/* 2129 */         if ((((TreeNode)localObject5).hash & paramInt2) == 0) {
/* 2130 */           if ((((TreeNode)localObject5).prev = localObject2) == null) {
/* 2131 */             localObject1 = localObject5;
/*      */           } else
/* 2133 */             ((TreeNode)localObject2).next = ((HashMap.Node)localObject5);
/* 2134 */           localObject2 = localObject5;
/* 2135 */           i++;
/*      */         }
/*      */         else {
/* 2138 */           if ((((TreeNode)localObject5).prev = localObject4) == null) {
/* 2139 */             localObject3 = localObject5;
/*      */           } else
/* 2141 */             ((TreeNode)localObject4).next = ((HashMap.Node)localObject5);
/* 2142 */           localObject4 = localObject5;
/* 2143 */           j++;
/*      */         }
/*      */       }
/*      */       
/* 2147 */       if (localObject1 != null) {
/* 2148 */         if (i <= 6) {
/* 2149 */           paramArrayOfNode[paramInt1] = ((TreeNode)localObject1).untreeify(paramHashMap);
/*      */         } else {
/* 2151 */           paramArrayOfNode[paramInt1] = localObject1;
/* 2152 */           if (localObject3 != null)
/* 2153 */             ((TreeNode)localObject1).treeify(paramArrayOfNode);
/*      */         }
/*      */       }
/* 2156 */       if (localObject3 != null) {
/* 2157 */         if (j <= 6) {
/* 2158 */           paramArrayOfNode[(paramInt1 + paramInt2)] = ((TreeNode)localObject3).untreeify(paramHashMap);
/*      */         } else {
/* 2160 */           paramArrayOfNode[(paramInt1 + paramInt2)] = localObject3;
/* 2161 */           if (localObject1 != null) {
/* 2162 */             ((TreeNode)localObject3).treeify(paramArrayOfNode);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> paramTreeNode1, TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       TreeNode localTreeNode1;
/*      */       
/* 2173 */       if ((paramTreeNode2 != null) && ((localTreeNode1 = paramTreeNode2.right) != null)) { TreeNode localTreeNode3;
/* 2174 */         if ((localTreeNode3 = paramTreeNode2.right = localTreeNode1.left) != null)
/* 2175 */           localTreeNode3.parent = paramTreeNode2;
/* 2176 */         TreeNode localTreeNode2; if ((localTreeNode2 = localTreeNode1.parent = paramTreeNode2.parent) == null) {
/* 2177 */           (paramTreeNode1 = localTreeNode1).red = false;
/* 2178 */         } else if (localTreeNode2.left == paramTreeNode2) {
/* 2179 */           localTreeNode2.left = localTreeNode1;
/*      */         } else
/* 2181 */           localTreeNode2.right = localTreeNode1;
/* 2182 */         localTreeNode1.left = paramTreeNode2;
/* 2183 */         paramTreeNode2.parent = localTreeNode1;
/*      */       }
/* 2185 */       return paramTreeNode1;
/*      */     }
/*      */     
/*      */     static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> paramTreeNode1, TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       TreeNode localTreeNode1;
/* 2191 */       if ((paramTreeNode2 != null) && ((localTreeNode1 = paramTreeNode2.left) != null)) { TreeNode localTreeNode3;
/* 2192 */         if ((localTreeNode3 = paramTreeNode2.left = localTreeNode1.right) != null)
/* 2193 */           localTreeNode3.parent = paramTreeNode2;
/* 2194 */         TreeNode localTreeNode2; if ((localTreeNode2 = localTreeNode1.parent = paramTreeNode2.parent) == null) {
/* 2195 */           (paramTreeNode1 = localTreeNode1).red = false;
/* 2196 */         } else if (localTreeNode2.right == paramTreeNode2) {
/* 2197 */           localTreeNode2.right = localTreeNode1;
/*      */         } else
/* 2199 */           localTreeNode2.left = localTreeNode1;
/* 2200 */         localTreeNode1.right = paramTreeNode2;
/* 2201 */         paramTreeNode2.parent = localTreeNode1;
/*      */       }
/* 2203 */       return paramTreeNode1;
/*      */     }
/*      */     
/*      */     static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> paramTreeNode1, TreeNode<K, V> paramTreeNode2)
/*      */     {
/* 2208 */       paramTreeNode2.red = true;
/*      */       for (;;) { TreeNode localTreeNode1;
/* 2210 */         if ((localTreeNode1 = paramTreeNode2.parent) == null) {
/* 2211 */           paramTreeNode2.red = false;
/* 2212 */           return paramTreeNode2; }
/*      */         TreeNode localTreeNode2;
/* 2214 */         if ((!localTreeNode1.red) || ((localTreeNode2 = localTreeNode1.parent) == null))
/* 2215 */           return paramTreeNode1;
/* 2216 */         TreeNode localTreeNode3; if (localTreeNode1 == (localTreeNode3 = localTreeNode2.left)) { TreeNode localTreeNode4;
/* 2217 */           if (((localTreeNode4 = localTreeNode2.right) != null) && (localTreeNode4.red)) {
/* 2218 */             localTreeNode4.red = false;
/* 2219 */             localTreeNode1.red = false;
/* 2220 */             localTreeNode2.red = true;
/* 2221 */             paramTreeNode2 = localTreeNode2;
/*      */           }
/*      */           else {
/* 2224 */             if (paramTreeNode2 == localTreeNode1.right) {
/* 2225 */               paramTreeNode1 = rotateLeft(paramTreeNode1, paramTreeNode2 = localTreeNode1);
/* 2226 */               localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.parent;
/*      */             }
/* 2228 */             if (localTreeNode1 != null) {
/* 2229 */               localTreeNode1.red = false;
/* 2230 */               if (localTreeNode2 != null) {
/* 2231 */                 localTreeNode2.red = true;
/* 2232 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode2);
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */         }
/* 2238 */         else if ((localTreeNode3 != null) && (localTreeNode3.red)) {
/* 2239 */           localTreeNode3.red = false;
/* 2240 */           localTreeNode1.red = false;
/* 2241 */           localTreeNode2.red = true;
/* 2242 */           paramTreeNode2 = localTreeNode2;
/*      */         }
/*      */         else {
/* 2245 */           if (paramTreeNode2 == localTreeNode1.left) {
/* 2246 */             paramTreeNode1 = rotateRight(paramTreeNode1, paramTreeNode2 = localTreeNode1);
/* 2247 */             localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.parent;
/*      */           }
/* 2249 */           if (localTreeNode1 != null) {
/* 2250 */             localTreeNode1.red = false;
/* 2251 */             if (localTreeNode2 != null) {
/* 2252 */               localTreeNode2.red = true;
/* 2253 */               paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> paramTreeNode1, TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       for (;;)
/*      */       {
/* 2264 */         if ((paramTreeNode2 == null) || (paramTreeNode2 == paramTreeNode1))
/* 2265 */           return paramTreeNode1;
/* 2266 */         TreeNode localTreeNode1; if ((localTreeNode1 = paramTreeNode2.parent) == null) {
/* 2267 */           paramTreeNode2.red = false;
/* 2268 */           return paramTreeNode2;
/*      */         }
/* 2270 */         if (paramTreeNode2.red) {
/* 2271 */           paramTreeNode2.red = false;
/* 2272 */           return paramTreeNode1; }
/*      */         TreeNode localTreeNode2;
/* 2274 */         TreeNode localTreeNode4; TreeNode localTreeNode5; if ((localTreeNode2 = localTreeNode1.left) == paramTreeNode2) { TreeNode localTreeNode3;
/* 2275 */           if (((localTreeNode3 = localTreeNode1.right) != null) && (localTreeNode3.red)) {
/* 2276 */             localTreeNode3.red = false;
/* 2277 */             localTreeNode1.red = true;
/* 2278 */             paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode1);
/* 2279 */             localTreeNode3 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.right;
/*      */           }
/* 2281 */           if (localTreeNode3 == null) {
/* 2282 */             paramTreeNode2 = localTreeNode1;
/*      */           } else {
/* 2284 */             localTreeNode4 = localTreeNode3.left;localTreeNode5 = localTreeNode3.right;
/* 2285 */             if (((localTreeNode5 == null) || (!localTreeNode5.red)) && ((localTreeNode4 == null) || (!localTreeNode4.red)))
/*      */             {
/* 2287 */               localTreeNode3.red = true;
/* 2288 */               paramTreeNode2 = localTreeNode1;
/*      */             }
/*      */             else {
/* 2291 */               if ((localTreeNode5 == null) || (!localTreeNode5.red)) {
/* 2292 */                 if (localTreeNode4 != null)
/* 2293 */                   localTreeNode4.red = false;
/* 2294 */                 localTreeNode3.red = true;
/* 2295 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode3);
/* 2296 */                 localTreeNode3 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.right;
/*      */               }
/*      */               
/* 2299 */               if (localTreeNode3 != null) {
/* 2300 */                 localTreeNode3.red = (localTreeNode1 == null ? false : localTreeNode1.red);
/* 2301 */                 if ((localTreeNode5 = localTreeNode3.right) != null)
/* 2302 */                   localTreeNode5.red = false;
/*      */               }
/* 2304 */               if (localTreeNode1 != null) {
/* 2305 */                 localTreeNode1.red = false;
/* 2306 */                 paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode1);
/*      */               }
/* 2308 */               paramTreeNode2 = paramTreeNode1;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 2313 */           if ((localTreeNode2 != null) && (localTreeNode2.red)) {
/* 2314 */             localTreeNode2.red = false;
/* 2315 */             localTreeNode1.red = true;
/* 2316 */             paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode1);
/* 2317 */             localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.left;
/*      */           }
/* 2319 */           if (localTreeNode2 == null) {
/* 2320 */             paramTreeNode2 = localTreeNode1;
/*      */           } else {
/* 2322 */             localTreeNode4 = localTreeNode2.left;localTreeNode5 = localTreeNode2.right;
/* 2323 */             if (((localTreeNode4 == null) || (!localTreeNode4.red)) && ((localTreeNode5 == null) || (!localTreeNode5.red)))
/*      */             {
/* 2325 */               localTreeNode2.red = true;
/* 2326 */               paramTreeNode2 = localTreeNode1;
/*      */             }
/*      */             else {
/* 2329 */               if ((localTreeNode4 == null) || (!localTreeNode4.red)) {
/* 2330 */                 if (localTreeNode5 != null)
/* 2331 */                   localTreeNode5.red = false;
/* 2332 */                 localTreeNode2.red = true;
/* 2333 */                 paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode2);
/* 2334 */                 localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.left;
/*      */               }
/*      */               
/* 2337 */               if (localTreeNode2 != null) {
/* 2338 */                 localTreeNode2.red = (localTreeNode1 == null ? false : localTreeNode1.red);
/* 2339 */                 if ((localTreeNode4 = localTreeNode2.left) != null)
/* 2340 */                   localTreeNode4.red = false;
/*      */               }
/* 2342 */               if (localTreeNode1 != null) {
/* 2343 */                 localTreeNode1.red = false;
/* 2344 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode1);
/*      */               }
/* 2346 */               paramTreeNode2 = paramTreeNode1;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static <K, V> boolean checkInvariants(TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2357 */       TreeNode localTreeNode1 = paramTreeNode.parent;TreeNode localTreeNode2 = paramTreeNode.left;TreeNode localTreeNode3 = paramTreeNode.right;
/* 2358 */       TreeNode localTreeNode4 = paramTreeNode.prev;TreeNode localTreeNode5 = (TreeNode)paramTreeNode.next;
/* 2359 */       if ((localTreeNode4 != null) && (localTreeNode4.next != paramTreeNode))
/* 2360 */         return false;
/* 2361 */       if ((localTreeNode5 != null) && (localTreeNode5.prev != paramTreeNode))
/* 2362 */         return false;
/* 2363 */       if ((localTreeNode1 != null) && (paramTreeNode != localTreeNode1.left) && (paramTreeNode != localTreeNode1.right))
/* 2364 */         return false;
/* 2365 */       if ((localTreeNode2 != null) && ((localTreeNode2.parent != paramTreeNode) || (localTreeNode2.hash > paramTreeNode.hash)))
/* 2366 */         return false;
/* 2367 */       if ((localTreeNode3 != null) && ((localTreeNode3.parent != paramTreeNode) || (localTreeNode3.hash < paramTreeNode.hash)))
/* 2368 */         return false;
/* 2369 */       if ((paramTreeNode.red) && (localTreeNode2 != null) && (localTreeNode2.red) && (localTreeNode3 != null) && (localTreeNode3.red))
/* 2370 */         return false;
/* 2371 */       if ((localTreeNode2 != null) && (!checkInvariants(localTreeNode2)))
/* 2372 */         return false;
/* 2373 */       if ((localTreeNode3 != null) && (!checkInvariants(localTreeNode3)))
/* 2374 */         return false;
/* 2375 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/HashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */