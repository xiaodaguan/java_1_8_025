/*      */ package java.util;
/*      */ 
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Spliterators
/*      */ {
/*      */   public static <T> Spliterator<T> emptySpliterator()
/*      */   {
/*   60 */     return EMPTY_SPLITERATOR;
/*      */   }
/*      */   
/*   63 */   private static final Spliterator<Object> EMPTY_SPLITERATOR = new Spliterators.EmptySpliterator.OfRef();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Spliterator.OfInt emptyIntSpliterator()
/*      */   {
/*   76 */     return EMPTY_INT_SPLITERATOR;
/*      */   }
/*      */   
/*   79 */   private static final Spliterator.OfInt EMPTY_INT_SPLITERATOR = new Spliterators.EmptySpliterator.OfInt();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Spliterator.OfLong emptyLongSpliterator()
/*      */   {
/*   92 */     return EMPTY_LONG_SPLITERATOR;
/*      */   }
/*      */   
/*   95 */   private static final Spliterator.OfLong EMPTY_LONG_SPLITERATOR = new Spliterators.EmptySpliterator.OfLong();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Spliterator.OfDouble emptyDoubleSpliterator()
/*      */   {
/*  108 */     return EMPTY_DOUBLE_SPLITERATOR;
/*      */   }
/*      */   
/*  111 */   private static final Spliterator.OfDouble EMPTY_DOUBLE_SPLITERATOR = new Spliterators.EmptySpliterator.OfDouble();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> Spliterator<T> spliterator(Object[] paramArrayOfObject, int paramInt)
/*      */   {
/*  142 */     return new ArraySpliterator((Object[])Objects.requireNonNull(paramArrayOfObject), paramInt);
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
/*      */   public static <T> Spliterator<T> spliterator(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  177 */     checkFromToBounds(((Object[])Objects.requireNonNull(paramArrayOfObject)).length, paramInt1, paramInt2);
/*  178 */     return new ArraySpliterator(paramArrayOfObject, paramInt1, paramInt2, paramInt3);
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
/*      */   public static Spliterator.OfInt spliterator(int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  206 */     return new IntArraySpliterator((int[])Objects.requireNonNull(paramArrayOfInt), paramInt);
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
/*      */   public static Spliterator.OfInt spliterator(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  239 */     checkFromToBounds(((int[])Objects.requireNonNull(paramArrayOfInt)).length, paramInt1, paramInt2);
/*  240 */     return new IntArraySpliterator(paramArrayOfInt, paramInt1, paramInt2, paramInt3);
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
/*      */   public static Spliterator.OfLong spliterator(long[] paramArrayOfLong, int paramInt)
/*      */   {
/*  268 */     return new LongArraySpliterator((long[])Objects.requireNonNull(paramArrayOfLong), paramInt);
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
/*      */   public static Spliterator.OfLong spliterator(long[] paramArrayOfLong, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  305 */     checkFromToBounds(((long[])Objects.requireNonNull(paramArrayOfLong)).length, paramInt1, paramInt2);
/*  306 */     return new LongArraySpliterator(paramArrayOfLong, paramInt1, paramInt2, paramInt3);
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
/*      */   public static Spliterator.OfDouble spliterator(double[] paramArrayOfDouble, int paramInt)
/*      */   {
/*  334 */     return new DoubleArraySpliterator((double[])Objects.requireNonNull(paramArrayOfDouble), paramInt);
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
/*      */   public static Spliterator.OfDouble spliterator(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  371 */     checkFromToBounds(((double[])Objects.requireNonNull(paramArrayOfDouble)).length, paramInt1, paramInt2);
/*  372 */     return new DoubleArraySpliterator(paramArrayOfDouble, paramInt1, paramInt2, paramInt3);
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
/*      */   private static void checkFromToBounds(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  386 */     if (paramInt2 > paramInt3) {
/*  387 */       throw new ArrayIndexOutOfBoundsException("origin(" + paramInt2 + ") > fence(" + paramInt3 + ")");
/*      */     }
/*      */     
/*  390 */     if (paramInt2 < 0) {
/*  391 */       throw new ArrayIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  393 */     if (paramInt3 > paramInt1) {
/*  394 */       throw new ArrayIndexOutOfBoundsException(paramInt3);
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
/*      */   public static <T> Spliterator<T> spliterator(Collection<? extends T> paramCollection, int paramInt)
/*      */   {
/*  420 */     return new IteratorSpliterator((Collection)Objects.requireNonNull(paramCollection), paramInt);
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
/*      */   public static <T> Spliterator<T> spliterator(Iterator<? extends T> paramIterator, long paramLong, int paramInt)
/*      */   {
/*  451 */     return new IteratorSpliterator((Iterator)Objects.requireNonNull(paramIterator), paramLong, paramInt);
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
/*      */   public static <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> paramIterator, int paramInt)
/*      */   {
/*  478 */     return new IteratorSpliterator((Iterator)Objects.requireNonNull(paramIterator), paramInt);
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
/*      */   public static Spliterator.OfInt spliterator(PrimitiveIterator.OfInt paramOfInt, long paramLong, int paramInt)
/*      */   {
/*  508 */     return new IntIteratorSpliterator((PrimitiveIterator.OfInt)Objects.requireNonNull(paramOfInt), paramLong, paramInt);
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
/*      */   public static Spliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt paramOfInt, int paramInt)
/*      */   {
/*  535 */     return new IntIteratorSpliterator((PrimitiveIterator.OfInt)Objects.requireNonNull(paramOfInt), paramInt);
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
/*      */   public static Spliterator.OfLong spliterator(PrimitiveIterator.OfLong paramOfLong, long paramLong, int paramInt)
/*      */   {
/*  565 */     return new LongIteratorSpliterator((PrimitiveIterator.OfLong)Objects.requireNonNull(paramOfLong), paramLong, paramInt);
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
/*      */   public static Spliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong paramOfLong, int paramInt)
/*      */   {
/*  592 */     return new LongIteratorSpliterator((PrimitiveIterator.OfLong)Objects.requireNonNull(paramOfLong), paramInt);
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
/*      */   public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble paramOfDouble, long paramLong, int paramInt)
/*      */   {
/*  622 */     return new DoubleIteratorSpliterator((PrimitiveIterator.OfDouble)Objects.requireNonNull(paramOfDouble), paramLong, paramInt);
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
/*      */   public static Spliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble paramOfDouble, int paramInt)
/*      */   {
/*  649 */     return new DoubleIteratorSpliterator((PrimitiveIterator.OfDouble)Objects.requireNonNull(paramOfDouble), paramInt);
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
/*      */   public static <T> Iterator<T> iterator(Spliterator<? extends T> paramSpliterator)
/*      */   {
/*  667 */     Objects.requireNonNull(paramSpliterator);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  696 */     new Iterator()
/*      */     {
/*  669 */       boolean valueReady = false;
/*      */       T nextElement;
/*      */       
/*      */       public void accept(T paramAnonymousT)
/*      */       {
/*  674 */         this.valueReady = true;
/*  675 */         this.nextElement = paramAnonymousT;
/*      */       }
/*      */       
/*      */       public boolean hasNext()
/*      */       {
/*  680 */         if (!this.valueReady)
/*  681 */           this.val$spliterator.tryAdvance(this);
/*  682 */         return this.valueReady;
/*      */       }
/*      */       
/*      */       public T next()
/*      */       {
/*  687 */         if ((!this.valueReady) && (!hasNext())) {
/*  688 */           throw new NoSuchElementException();
/*      */         }
/*  690 */         this.valueReady = false;
/*  691 */         return (T)this.nextElement;
/*      */       }
/*      */     };
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
/*      */   public static PrimitiveIterator.OfInt iterator(Spliterator.OfInt paramOfInt)
/*      */   {
/*  712 */     Objects.requireNonNull(paramOfInt);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  741 */     new PrimitiveIterator.OfInt()
/*      */     {
/*  714 */       boolean valueReady = false;
/*      */       int nextElement;
/*      */       
/*      */       public void accept(int paramAnonymousInt)
/*      */       {
/*  719 */         this.valueReady = true;
/*  720 */         this.nextElement = paramAnonymousInt;
/*      */       }
/*      */       
/*      */       public boolean hasNext()
/*      */       {
/*  725 */         if (!this.valueReady)
/*  726 */           this.val$spliterator.tryAdvance(this);
/*  727 */         return this.valueReady;
/*      */       }
/*      */       
/*      */       public int nextInt()
/*      */       {
/*  732 */         if ((!this.valueReady) && (!hasNext())) {
/*  733 */           throw new NoSuchElementException();
/*      */         }
/*  735 */         this.valueReady = false;
/*  736 */         return this.nextElement;
/*      */       }
/*      */     };
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
/*      */   public static PrimitiveIterator.OfLong iterator(Spliterator.OfLong paramOfLong)
/*      */   {
/*  757 */     Objects.requireNonNull(paramOfLong);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  786 */     new PrimitiveIterator.OfLong()
/*      */     {
/*  759 */       boolean valueReady = false;
/*      */       long nextElement;
/*      */       
/*      */       public void accept(long paramAnonymousLong)
/*      */       {
/*  764 */         this.valueReady = true;
/*  765 */         this.nextElement = paramAnonymousLong;
/*      */       }
/*      */       
/*      */       public boolean hasNext()
/*      */       {
/*  770 */         if (!this.valueReady)
/*  771 */           this.val$spliterator.tryAdvance(this);
/*  772 */         return this.valueReady;
/*      */       }
/*      */       
/*      */       public long nextLong()
/*      */       {
/*  777 */         if ((!this.valueReady) && (!hasNext())) {
/*  778 */           throw new NoSuchElementException();
/*      */         }
/*  780 */         this.valueReady = false;
/*  781 */         return this.nextElement;
/*      */       }
/*      */     };
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
/*      */   public static PrimitiveIterator.OfDouble iterator(Spliterator.OfDouble paramOfDouble)
/*      */   {
/*  802 */     Objects.requireNonNull(paramOfDouble);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  831 */     new PrimitiveIterator.OfDouble()
/*      */     {
/*  804 */       boolean valueReady = false;
/*      */       double nextElement;
/*      */       
/*      */       public void accept(double paramAnonymousDouble)
/*      */       {
/*  809 */         this.valueReady = true;
/*  810 */         this.nextElement = paramAnonymousDouble;
/*      */       }
/*      */       
/*      */       public boolean hasNext()
/*      */       {
/*  815 */         if (!this.valueReady)
/*  816 */           this.val$spliterator.tryAdvance(this);
/*  817 */         return this.valueReady;
/*      */       }
/*      */       
/*      */       public double nextDouble()
/*      */       {
/*  822 */         if ((!this.valueReady) && (!hasNext())) {
/*  823 */           throw new NoSuchElementException();
/*      */         }
/*  825 */         this.valueReady = false;
/*  826 */         return this.nextElement;
/*      */       }
/*      */     };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static abstract class EmptySpliterator<T, S extends Spliterator<T>, C>
/*      */   {
/*      */     public S trySplit()
/*      */     {
/*  841 */       return null;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(C paramC) {
/*  845 */       Objects.requireNonNull(paramC);
/*  846 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(C paramC) {
/*  850 */       Objects.requireNonNull(paramC);
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  854 */       return 0L;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  858 */       return 16448;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final class OfDouble
/*      */       extends Spliterators.EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
/*      */       implements Spliterator.OfDouble
/*      */     {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final class OfLong
/*      */       extends Spliterators.EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
/*      */       implements Spliterator.OfLong
/*      */     {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final class OfInt
/*      */       extends Spliterators.EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
/*      */       implements Spliterator.OfInt
/*      */     {}
/*      */     
/*      */ 
/*      */ 
/*      */     private static final class OfRef<T>
/*      */       extends Spliterators.EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
/*      */       implements Spliterator<T>
/*      */     {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class ArraySpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     private final Object[] array;
/*      */     
/*      */ 
/*      */     private int index;
/*      */     
/*      */ 
/*      */     private final int fence;
/*      */     
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */ 
/*      */     public ArraySpliterator(Object[] paramArrayOfObject, int paramInt)
/*      */     {
/*  913 */       this(paramArrayOfObject, 0, paramArrayOfObject.length, paramInt);
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
/*      */     public ArraySpliterator(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/*  926 */       this.array = paramArrayOfObject;
/*  927 */       this.index = paramInt1;
/*  928 */       this.fence = paramInt2;
/*  929 */       this.characteristics = (paramInt3 | 0x40 | 0x4000);
/*      */     }
/*      */     
/*      */     public Spliterator<T> trySplit()
/*      */     {
/*  934 */       int i = this.index;int j = i + this.fence >>> 1;
/*  935 */       return i >= j ? null : new ArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(Consumer<? super T> paramConsumer)
/*      */     {
/*  944 */       if (paramConsumer == null)
/*  945 */         throw new NullPointerException();
/*  946 */       Object[] arrayOfObject; int j; int i; if (((arrayOfObject = this.array).length >= (j = this.fence)) && ((i = this.index) >= 0) && (i < (this.index = j))) {
/*      */         do {
/*  948 */           paramConsumer.accept(arrayOfObject[i]);i++; } while (i < j);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> paramConsumer)
/*      */     {
/*  954 */       if (paramConsumer == null)
/*  955 */         throw new NullPointerException();
/*  956 */       if ((this.index >= 0) && (this.index < this.fence)) {
/*  957 */         Object localObject = this.array[(this.index++)];
/*  958 */         paramConsumer.accept(localObject);
/*  959 */         return true;
/*      */       }
/*  961 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  965 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  969 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super T> getComparator()
/*      */     {
/*  974 */       if (hasCharacteristics(4))
/*  975 */         return null;
/*  976 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class IntArraySpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     private final int[] array;
/*      */     
/*      */ 
/*      */     private int index;
/*      */     
/*      */ 
/*      */     private final int fence;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */     public IntArraySpliterator(int[] paramArrayOfInt, int paramInt)
/*      */     {
/*  998 */       this(paramArrayOfInt, 0, paramArrayOfInt.length, paramInt);
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
/*      */     public IntArraySpliterator(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1011 */       this.array = paramArrayOfInt;
/* 1012 */       this.index = paramInt1;
/* 1013 */       this.fence = paramInt2;
/* 1014 */       this.characteristics = (paramInt3 | 0x40 | 0x4000);
/*      */     }
/*      */     
/*      */     public Spliterator.OfInt trySplit()
/*      */     {
/* 1019 */       int i = this.index;int j = i + this.fence >>> 1;
/* 1020 */       return i >= j ? null : new IntArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(IntConsumer paramIntConsumer)
/*      */     {
/* 1028 */       if (paramIntConsumer == null)
/* 1029 */         throw new NullPointerException();
/* 1030 */       int[] arrayOfInt; int j; int i; if (((arrayOfInt = this.array).length >= (j = this.fence)) && ((i = this.index) >= 0) && (i < (this.index = j))) {
/*      */         do {
/* 1032 */           paramIntConsumer.accept(arrayOfInt[i]);i++; } while (i < j);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*      */     {
/* 1038 */       if (paramIntConsumer == null)
/* 1039 */         throw new NullPointerException();
/* 1040 */       if ((this.index >= 0) && (this.index < this.fence)) {
/* 1041 */         paramIntConsumer.accept(this.array[(this.index++)]);
/* 1042 */         return true;
/*      */       }
/* 1044 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1048 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1052 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Integer> getComparator()
/*      */     {
/* 1057 */       if (hasCharacteristics(4))
/* 1058 */         return null;
/* 1059 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class LongArraySpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     private final long[] array;
/*      */     
/*      */ 
/*      */     private int index;
/*      */     
/*      */ 
/*      */     private final int fence;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */     public LongArraySpliterator(long[] paramArrayOfLong, int paramInt)
/*      */     {
/* 1081 */       this(paramArrayOfLong, 0, paramArrayOfLong.length, paramInt);
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
/*      */     public LongArraySpliterator(long[] paramArrayOfLong, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1094 */       this.array = paramArrayOfLong;
/* 1095 */       this.index = paramInt1;
/* 1096 */       this.fence = paramInt2;
/* 1097 */       this.characteristics = (paramInt3 | 0x40 | 0x4000);
/*      */     }
/*      */     
/*      */     public Spliterator.OfLong trySplit()
/*      */     {
/* 1102 */       int i = this.index;int j = i + this.fence >>> 1;
/* 1103 */       return i >= j ? null : new LongArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(LongConsumer paramLongConsumer)
/*      */     {
/* 1111 */       if (paramLongConsumer == null)
/* 1112 */         throw new NullPointerException();
/* 1113 */       long[] arrayOfLong; int j; int i; if (((arrayOfLong = this.array).length >= (j = this.fence)) && ((i = this.index) >= 0) && (i < (this.index = j))) {
/*      */         do {
/* 1115 */           paramLongConsumer.accept(arrayOfLong[i]);i++; } while (i < j);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*      */     {
/* 1121 */       if (paramLongConsumer == null)
/* 1122 */         throw new NullPointerException();
/* 1123 */       if ((this.index >= 0) && (this.index < this.fence)) {
/* 1124 */         paramLongConsumer.accept(this.array[(this.index++)]);
/* 1125 */         return true;
/*      */       }
/* 1127 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1131 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1135 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Long> getComparator()
/*      */     {
/* 1140 */       if (hasCharacteristics(4))
/* 1141 */         return null;
/* 1142 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class DoubleArraySpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     private final double[] array;
/*      */     
/*      */ 
/*      */     private int index;
/*      */     
/*      */ 
/*      */     private final int fence;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */     public DoubleArraySpliterator(double[] paramArrayOfDouble, int paramInt)
/*      */     {
/* 1164 */       this(paramArrayOfDouble, 0, paramArrayOfDouble.length, paramInt);
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
/*      */     public DoubleArraySpliterator(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1177 */       this.array = paramArrayOfDouble;
/* 1178 */       this.index = paramInt1;
/* 1179 */       this.fence = paramInt2;
/* 1180 */       this.characteristics = (paramInt3 | 0x40 | 0x4000);
/*      */     }
/*      */     
/*      */     public Spliterator.OfDouble trySplit()
/*      */     {
/* 1185 */       int i = this.index;int j = i + this.fence >>> 1;
/* 1186 */       return i >= j ? null : new DoubleArraySpliterator(this.array, i, this.index = j, this.characteristics);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void forEachRemaining(DoubleConsumer paramDoubleConsumer)
/*      */     {
/* 1194 */       if (paramDoubleConsumer == null)
/* 1195 */         throw new NullPointerException();
/* 1196 */       double[] arrayOfDouble; int j; int i; if (((arrayOfDouble = this.array).length >= (j = this.fence)) && ((i = this.index) >= 0) && (i < (this.index = j))) {
/*      */         do {
/* 1198 */           paramDoubleConsumer.accept(arrayOfDouble[i]);i++; } while (i < j);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer paramDoubleConsumer)
/*      */     {
/* 1204 */       if (paramDoubleConsumer == null)
/* 1205 */         throw new NullPointerException();
/* 1206 */       if ((this.index >= 0) && (this.index < this.fence)) {
/* 1207 */         paramDoubleConsumer.accept(this.array[(this.index++)]);
/* 1208 */         return true;
/*      */       }
/* 1210 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/* 1214 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1218 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Double> getComparator()
/*      */     {
/* 1223 */       if (hasCharacteristics(4))
/* 1224 */         return null;
/* 1225 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract class AbstractSpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private long est;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int batch;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected AbstractSpliterator(long paramLong, int paramInt)
/*      */     {
/* 1274 */       this.est = paramLong;
/* 1275 */       this.characteristics = ((paramInt & 0x40) != 0 ? paramInt | 0x4000 : paramInt);
/*      */     }
/*      */     
/*      */     static final class HoldingConsumer<T>
/*      */       implements Consumer<T>
/*      */     {
/*      */       Object value;
/*      */       
/*      */       public void accept(T paramT)
/*      */       {
/* 1285 */         this.value = paramT;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Spliterator<T> trySplit()
/*      */     {
/* 1309 */       HoldingConsumer localHoldingConsumer = new HoldingConsumer();
/* 1310 */       long l = this.est;
/* 1311 */       if ((l > 1L) && (tryAdvance(localHoldingConsumer))) {
/* 1312 */         int i = this.batch + 1024;
/* 1313 */         if (i > l)
/* 1314 */           i = (int)l;
/* 1315 */         if (i > 33554432)
/* 1316 */           i = 33554432;
/* 1317 */         Object[] arrayOfObject = new Object[i];
/* 1318 */         int j = 0;
/* 1319 */         do { arrayOfObject[j] = localHoldingConsumer.value;j++; } while ((j < i) && (tryAdvance(localHoldingConsumer)));
/* 1320 */         this.batch = j;
/* 1321 */         if (this.est != Long.MAX_VALUE)
/* 1322 */           this.est -= j;
/* 1323 */         return new Spliterators.ArraySpliterator(arrayOfObject, 0, j, characteristics());
/*      */       }
/* 1325 */       return null;
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
/*      */     public long estimateSize()
/*      */     {
/* 1338 */       return this.est;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int characteristics()
/*      */     {
/* 1350 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract class AbstractIntSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private long est;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int batch;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected AbstractIntSpliterator(long paramLong, int paramInt)
/*      */     {
/* 1397 */       this.est = paramLong;
/* 1398 */       this.characteristics = ((paramInt & 0x40) != 0 ? paramInt | 0x4000 : paramInt);
/*      */     }
/*      */     
/*      */     static final class HoldingIntConsumer
/*      */       implements IntConsumer
/*      */     {
/*      */       int value;
/*      */       
/*      */       public void accept(int paramInt)
/*      */       {
/* 1408 */         this.value = paramInt;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Spliterator.OfInt trySplit()
/*      */     {
/* 1419 */       HoldingIntConsumer localHoldingIntConsumer = new HoldingIntConsumer();
/* 1420 */       long l = this.est;
/* 1421 */       if ((l > 1L) && (tryAdvance(localHoldingIntConsumer))) {
/* 1422 */         int i = this.batch + 1024;
/* 1423 */         if (i > l)
/* 1424 */           i = (int)l;
/* 1425 */         if (i > 33554432)
/* 1426 */           i = 33554432;
/* 1427 */         int[] arrayOfInt = new int[i];
/* 1428 */         int j = 0;
/* 1429 */         do { arrayOfInt[j] = localHoldingIntConsumer.value;j++; } while ((j < i) && (tryAdvance(localHoldingIntConsumer)));
/* 1430 */         this.batch = j;
/* 1431 */         if (this.est != Long.MAX_VALUE)
/* 1432 */           this.est -= j;
/* 1433 */         return new Spliterators.IntArraySpliterator(arrayOfInt, 0, j, characteristics());
/*      */       }
/* 1435 */       return null;
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
/*      */     public long estimateSize()
/*      */     {
/* 1448 */       return this.est;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int characteristics()
/*      */     {
/* 1460 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract class AbstractLongSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private long est;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int batch;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected AbstractLongSpliterator(long paramLong, int paramInt)
/*      */     {
/* 1507 */       this.est = paramLong;
/* 1508 */       this.characteristics = ((paramInt & 0x40) != 0 ? paramInt | 0x4000 : paramInt);
/*      */     }
/*      */     
/*      */     static final class HoldingLongConsumer
/*      */       implements LongConsumer
/*      */     {
/*      */       long value;
/*      */       
/*      */       public void accept(long paramLong)
/*      */       {
/* 1518 */         this.value = paramLong;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Spliterator.OfLong trySplit()
/*      */     {
/* 1529 */       HoldingLongConsumer localHoldingLongConsumer = new HoldingLongConsumer();
/* 1530 */       long l = this.est;
/* 1531 */       if ((l > 1L) && (tryAdvance(localHoldingLongConsumer))) {
/* 1532 */         int i = this.batch + 1024;
/* 1533 */         if (i > l)
/* 1534 */           i = (int)l;
/* 1535 */         if (i > 33554432)
/* 1536 */           i = 33554432;
/* 1537 */         long[] arrayOfLong = new long[i];
/* 1538 */         int j = 0;
/* 1539 */         do { arrayOfLong[j] = localHoldingLongConsumer.value;j++; } while ((j < i) && (tryAdvance(localHoldingLongConsumer)));
/* 1540 */         this.batch = j;
/* 1541 */         if (this.est != Long.MAX_VALUE)
/* 1542 */           this.est -= j;
/* 1543 */         return new Spliterators.LongArraySpliterator(arrayOfLong, 0, j, characteristics());
/*      */       }
/* 1545 */       return null;
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
/*      */     public long estimateSize()
/*      */     {
/* 1558 */       return this.est;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int characteristics()
/*      */     {
/* 1570 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract class AbstractDoubleSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private long est;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int batch;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected AbstractDoubleSpliterator(long paramLong, int paramInt)
/*      */     {
/* 1617 */       this.est = paramLong;
/* 1618 */       this.characteristics = ((paramInt & 0x40) != 0 ? paramInt | 0x4000 : paramInt);
/*      */     }
/*      */     
/*      */     static final class HoldingDoubleConsumer
/*      */       implements DoubleConsumer
/*      */     {
/*      */       double value;
/*      */       
/*      */       public void accept(double paramDouble)
/*      */       {
/* 1628 */         this.value = paramDouble;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Spliterator.OfDouble trySplit()
/*      */     {
/* 1639 */       HoldingDoubleConsumer localHoldingDoubleConsumer = new HoldingDoubleConsumer();
/* 1640 */       long l = this.est;
/* 1641 */       if ((l > 1L) && (tryAdvance(localHoldingDoubleConsumer))) {
/* 1642 */         int i = this.batch + 1024;
/* 1643 */         if (i > l)
/* 1644 */           i = (int)l;
/* 1645 */         if (i > 33554432)
/* 1646 */           i = 33554432;
/* 1647 */         double[] arrayOfDouble = new double[i];
/* 1648 */         int j = 0;
/* 1649 */         do { arrayOfDouble[j] = localHoldingDoubleConsumer.value;j++; } while ((j < i) && (tryAdvance(localHoldingDoubleConsumer)));
/* 1650 */         this.batch = j;
/* 1651 */         if (this.est != Long.MAX_VALUE)
/* 1652 */           this.est -= j;
/* 1653 */         return new Spliterators.DoubleArraySpliterator(arrayOfDouble, 0, j, characteristics());
/*      */       }
/* 1655 */       return null;
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
/*      */     public long estimateSize()
/*      */     {
/* 1668 */       return this.est;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int characteristics()
/*      */     {
/* 1680 */       return this.characteristics;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static class IteratorSpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */ 
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */ 
/*      */     private final Collection<? extends T> collection;
/*      */     
/*      */ 
/*      */     private Iterator<? extends T> it;
/*      */     
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */     private long est;
/*      */     
/*      */     private int batch;
/*      */     
/*      */ 
/*      */     public IteratorSpliterator(Collection<? extends T> paramCollection, int paramInt)
/*      */     {
/* 1711 */       this.collection = paramCollection;
/* 1712 */       this.it = null;
/* 1713 */       this.characteristics = ((paramInt & 0x1000) == 0 ? paramInt | 0x40 | 0x4000 : paramInt);
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
/*      */     public IteratorSpliterator(Iterator<? extends T> paramIterator, long paramLong, int paramInt)
/*      */     {
/* 1729 */       this.collection = null;
/* 1730 */       this.it = paramIterator;
/* 1731 */       this.est = paramLong;
/* 1732 */       this.characteristics = ((paramInt & 0x1000) == 0 ? paramInt | 0x40 | 0x4000 : paramInt);
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
/*      */     public IteratorSpliterator(Iterator<? extends T> paramIterator, int paramInt)
/*      */     {
/* 1747 */       this.collection = null;
/* 1748 */       this.it = paramIterator;
/* 1749 */       this.est = Long.MAX_VALUE;
/* 1750 */       this.characteristics = (paramInt & 0xBFBF);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Spliterator<T> trySplit()
/*      */     {
/*      */       Iterator localIterator;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       long l;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1770 */       if ((localIterator = this.it) == null) {
/* 1771 */         localIterator = this.it = this.collection.iterator();
/* 1772 */         l = this.est = this.collection.size();
/*      */       }
/*      */       else {
/* 1775 */         l = this.est; }
/* 1776 */       if ((l > 1L) && (localIterator.hasNext())) {
/* 1777 */         int i = this.batch + 1024;
/* 1778 */         if (i > l)
/* 1779 */           i = (int)l;
/* 1780 */         if (i > 33554432)
/* 1781 */           i = 33554432;
/* 1782 */         Object[] arrayOfObject = new Object[i];
/* 1783 */         int j = 0;
/* 1784 */         do { arrayOfObject[j] = localIterator.next();j++; } while ((j < i) && (localIterator.hasNext()));
/* 1785 */         this.batch = j;
/* 1786 */         if (this.est != Long.MAX_VALUE)
/* 1787 */           this.est -= j;
/* 1788 */         return new Spliterators.ArraySpliterator(arrayOfObject, 0, j, this.characteristics);
/*      */       }
/* 1790 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> paramConsumer)
/*      */     {
/* 1795 */       if (paramConsumer == null) throw new NullPointerException();
/*      */       Iterator localIterator;
/* 1797 */       if ((localIterator = this.it) == null) {
/* 1798 */         localIterator = this.it = this.collection.iterator();
/* 1799 */         this.est = this.collection.size();
/*      */       }
/* 1801 */       localIterator.forEachRemaining(paramConsumer);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> paramConsumer)
/*      */     {
/* 1806 */       if (paramConsumer == null) throw new NullPointerException();
/* 1807 */       if (this.it == null) {
/* 1808 */         this.it = this.collection.iterator();
/* 1809 */         this.est = this.collection.size();
/*      */       }
/* 1811 */       if (this.it.hasNext()) {
/* 1812 */         paramConsumer.accept(this.it.next());
/* 1813 */         return true;
/*      */       }
/* 1815 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 1820 */       if (this.it == null) {
/* 1821 */         this.it = this.collection.iterator();
/* 1822 */         return this.est = this.collection.size();
/*      */       }
/* 1824 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1828 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/* 1832 */       if (hasCharacteristics(4))
/* 1833 */         return null;
/* 1834 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class IntIteratorSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */ 
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */ 
/*      */     private PrimitiveIterator.OfInt it;
/*      */     
/*      */ 
/*      */     private final int characteristics;
/*      */     
/*      */ 
/*      */     private long est;
/*      */     
/*      */     private int batch;
/*      */     
/*      */ 
/*      */     public IntIteratorSpliterator(PrimitiveIterator.OfInt paramOfInt, long paramLong, int paramInt)
/*      */     {
/* 1862 */       this.it = paramOfInt;
/* 1863 */       this.est = paramLong;
/* 1864 */       this.characteristics = ((paramInt & 0x1000) == 0 ? paramInt | 0x40 | 0x4000 : paramInt);
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
/*      */     public IntIteratorSpliterator(PrimitiveIterator.OfInt paramOfInt, int paramInt)
/*      */     {
/* 1879 */       this.it = paramOfInt;
/* 1880 */       this.est = Long.MAX_VALUE;
/* 1881 */       this.characteristics = (paramInt & 0xBFBF);
/*      */     }
/*      */     
/*      */     public Spliterator.OfInt trySplit()
/*      */     {
/* 1886 */       PrimitiveIterator.OfInt localOfInt = this.it;
/* 1887 */       long l = this.est;
/* 1888 */       if ((l > 1L) && (localOfInt.hasNext())) {
/* 1889 */         int i = this.batch + 1024;
/* 1890 */         if (i > l)
/* 1891 */           i = (int)l;
/* 1892 */         if (i > 33554432)
/* 1893 */           i = 33554432;
/* 1894 */         int[] arrayOfInt = new int[i];
/* 1895 */         int j = 0;
/* 1896 */         do { arrayOfInt[j] = localOfInt.nextInt();j++; } while ((j < i) && (localOfInt.hasNext()));
/* 1897 */         this.batch = j;
/* 1898 */         if (this.est != Long.MAX_VALUE)
/* 1899 */           this.est -= j;
/* 1900 */         return new Spliterators.IntArraySpliterator(arrayOfInt, 0, j, this.characteristics);
/*      */       }
/* 1902 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(IntConsumer paramIntConsumer)
/*      */     {
/* 1907 */       if (paramIntConsumer == null) throw new NullPointerException();
/* 1908 */       this.it.forEachRemaining(paramIntConsumer);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*      */     {
/* 1913 */       if (paramIntConsumer == null) throw new NullPointerException();
/* 1914 */       if (this.it.hasNext()) {
/* 1915 */         paramIntConsumer.accept(this.it.nextInt());
/* 1916 */         return true;
/*      */       }
/* 1918 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 1923 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1927 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Integer> getComparator() {
/* 1931 */       if (hasCharacteristics(4))
/* 1932 */         return null;
/* 1933 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class LongIteratorSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */     private PrimitiveIterator.OfLong it;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */     private long est;
/*      */     
/*      */     private int batch;
/*      */     
/*      */ 
/*      */     public LongIteratorSpliterator(PrimitiveIterator.OfLong paramOfLong, long paramLong, int paramInt)
/*      */     {
/* 1956 */       this.it = paramOfLong;
/* 1957 */       this.est = paramLong;
/* 1958 */       this.characteristics = ((paramInt & 0x1000) == 0 ? paramInt | 0x40 | 0x4000 : paramInt);
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
/*      */     public LongIteratorSpliterator(PrimitiveIterator.OfLong paramOfLong, int paramInt)
/*      */     {
/* 1973 */       this.it = paramOfLong;
/* 1974 */       this.est = Long.MAX_VALUE;
/* 1975 */       this.characteristics = (paramInt & 0xBFBF);
/*      */     }
/*      */     
/*      */     public Spliterator.OfLong trySplit()
/*      */     {
/* 1980 */       PrimitiveIterator.OfLong localOfLong = this.it;
/* 1981 */       long l = this.est;
/* 1982 */       if ((l > 1L) && (localOfLong.hasNext())) {
/* 1983 */         int i = this.batch + 1024;
/* 1984 */         if (i > l)
/* 1985 */           i = (int)l;
/* 1986 */         if (i > 33554432)
/* 1987 */           i = 33554432;
/* 1988 */         long[] arrayOfLong = new long[i];
/* 1989 */         int j = 0;
/* 1990 */         do { arrayOfLong[j] = localOfLong.nextLong();j++; } while ((j < i) && (localOfLong.hasNext()));
/* 1991 */         this.batch = j;
/* 1992 */         if (this.est != Long.MAX_VALUE)
/* 1993 */           this.est -= j;
/* 1994 */         return new Spliterators.LongArraySpliterator(arrayOfLong, 0, j, this.characteristics);
/*      */       }
/* 1996 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(LongConsumer paramLongConsumer)
/*      */     {
/* 2001 */       if (paramLongConsumer == null) throw new NullPointerException();
/* 2002 */       this.it.forEachRemaining(paramLongConsumer);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*      */     {
/* 2007 */       if (paramLongConsumer == null) throw new NullPointerException();
/* 2008 */       if (this.it.hasNext()) {
/* 2009 */         paramLongConsumer.accept(this.it.nextLong());
/* 2010 */         return true;
/*      */       }
/* 2012 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 2017 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2021 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Long> getComparator() {
/* 2025 */       if (hasCharacteristics(4))
/* 2026 */         return null;
/* 2027 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class DoubleIteratorSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     
/*      */     static final int MAX_BATCH = 33554432;
/*      */     
/*      */     private PrimitiveIterator.OfDouble it;
/*      */     
/*      */     private final int characteristics;
/*      */     
/*      */     private long est;
/*      */     
/*      */     private int batch;
/*      */     
/*      */ 
/*      */     public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble paramOfDouble, long paramLong, int paramInt)
/*      */     {
/* 2050 */       this.it = paramOfDouble;
/* 2051 */       this.est = paramLong;
/* 2052 */       this.characteristics = ((paramInt & 0x1000) == 0 ? paramInt | 0x40 | 0x4000 : paramInt);
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
/*      */     public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble paramOfDouble, int paramInt)
/*      */     {
/* 2067 */       this.it = paramOfDouble;
/* 2068 */       this.est = Long.MAX_VALUE;
/* 2069 */       this.characteristics = (paramInt & 0xBFBF);
/*      */     }
/*      */     
/*      */     public Spliterator.OfDouble trySplit()
/*      */     {
/* 2074 */       PrimitiveIterator.OfDouble localOfDouble = this.it;
/* 2075 */       long l = this.est;
/* 2076 */       if ((l > 1L) && (localOfDouble.hasNext())) {
/* 2077 */         int i = this.batch + 1024;
/* 2078 */         if (i > l)
/* 2079 */           i = (int)l;
/* 2080 */         if (i > 33554432)
/* 2081 */           i = 33554432;
/* 2082 */         double[] arrayOfDouble = new double[i];
/* 2083 */         int j = 0;
/* 2084 */         do { arrayOfDouble[j] = localOfDouble.nextDouble();j++; } while ((j < i) && (localOfDouble.hasNext()));
/* 2085 */         this.batch = j;
/* 2086 */         if (this.est != Long.MAX_VALUE)
/* 2087 */           this.est -= j;
/* 2088 */         return new Spliterators.DoubleArraySpliterator(arrayOfDouble, 0, j, this.characteristics);
/*      */       }
/* 2090 */       return null;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer paramDoubleConsumer)
/*      */     {
/* 2095 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/* 2096 */       this.it.forEachRemaining(paramDoubleConsumer);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer paramDoubleConsumer)
/*      */     {
/* 2101 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/* 2102 */       if (this.it.hasNext()) {
/* 2103 */         paramDoubleConsumer.accept(this.it.nextDouble());
/* 2104 */         return true;
/*      */       }
/* 2106 */       return false;
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 2111 */       return this.est;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 2115 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     public Comparator<? super Double> getComparator() {
/* 2119 */       if (hasCharacteristics(4))
/* 2120 */         return null;
/* 2121 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Spliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */