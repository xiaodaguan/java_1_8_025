/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterator.OfDouble;
/*     */ import java.util.Spliterator.OfInt;
/*     */ import java.util.Spliterator.OfLong;
/*     */ import java.util.Spliterator.OfPrimitive;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.LongConsumer;
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
/*     */ final class Streams
/*     */ {
/*     */   private Streams()
/*     */   {
/*  47 */     throw new Error("no instances");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */   static final Object NONE = new Object();
/*     */   
/*     */ 
/*     */   static final class RangeIntSpliterator
/*     */     implements Spliterator.OfInt
/*     */   {
/*     */     private int from;
/*     */     
/*     */     private final int upTo;
/*     */     
/*     */     private int last;
/*     */     
/*     */     private static final int BALANCED_SPLIT_THRESHOLD = 16777216;
/*     */     private static final int RIGHT_BALANCED_SPLIT_RATIO = 8;
/*     */     
/*     */     RangeIntSpliterator(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */     {
/*  73 */       this(paramInt1, paramInt2, paramBoolean ? 1 : 0);
/*     */     }
/*     */     
/*     */     private RangeIntSpliterator(int paramInt1, int paramInt2, int paramInt3) {
/*  77 */       this.from = paramInt1;
/*  78 */       this.upTo = paramInt2;
/*  79 */       this.last = paramInt3;
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*     */     {
/*  84 */       Objects.requireNonNull(paramIntConsumer);
/*     */       
/*  86 */       int i = this.from;
/*  87 */       if (i < this.upTo) {
/*  88 */         this.from += 1;
/*  89 */         paramIntConsumer.accept(i);
/*  90 */         return true;
/*     */       }
/*  92 */       if (this.last > 0) {
/*  93 */         this.last = 0;
/*  94 */         paramIntConsumer.accept(i);
/*  95 */         return true;
/*     */       }
/*  97 */       return false;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(IntConsumer paramIntConsumer)
/*     */     {
/* 102 */       Objects.requireNonNull(paramIntConsumer);
/*     */       
/* 104 */       int i = this.from;
/* 105 */       int j = this.upTo;
/* 106 */       int k = this.last;
/* 107 */       this.from = this.upTo;
/* 108 */       this.last = 0;
/* 109 */       while (i < j) {
/* 110 */         paramIntConsumer.accept(i++);
/*     */       }
/* 112 */       if (k > 0)
/*     */       {
/* 114 */         paramIntConsumer.accept(i);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public long estimateSize()
/*     */     {
/* 121 */       return this.upTo - this.from + this.last;
/*     */     }
/*     */     
/*     */     public int characteristics()
/*     */     {
/* 126 */       return 17749;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Comparator<? super Integer> getComparator()
/*     */     {
/* 133 */       return null;
/*     */     }
/*     */     
/*     */     public Spliterator.OfInt trySplit()
/*     */     {
/* 138 */       long l = estimateSize();
/*     */       
/*     */ 
/*     */ 
/* 142 */       return l <= 1L ? null : new RangeIntSpliterator(this.from, this.from = this.from + splitPoint(l), 0);
/*     */     }
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
/*     */     private int splitPoint(long paramLong)
/*     */     {
/* 171 */       int i = paramLong < 16777216L ? 2 : 8;
/*     */       
/*     */ 
/*     */ 
/* 175 */       return (int)(paramLong / i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class RangeLongSpliterator
/*     */     implements Spliterator.OfLong
/*     */   {
/*     */     private long from;
/*     */     
/*     */     private final long upTo;
/*     */     
/*     */     private int last;
/*     */     
/*     */     private static final long BALANCED_SPLIT_THRESHOLD = 16777216L;
/*     */     
/*     */     private static final long RIGHT_BALANCED_SPLIT_RATIO = 8L;
/*     */     
/*     */ 
/*     */     RangeLongSpliterator(long paramLong1, long paramLong2, boolean paramBoolean)
/*     */     {
/* 197 */       this(paramLong1, paramLong2, paramBoolean ? 1 : 0);
/*     */     }
/*     */     
/*     */     private RangeLongSpliterator(long paramLong1, long paramLong2, int paramInt) {
/* 201 */       assert (paramLong2 - paramLong1 + paramInt > 0L);
/* 202 */       this.from = paramLong1;
/* 203 */       this.upTo = paramLong2;
/* 204 */       this.last = paramInt;
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*     */     {
/* 209 */       Objects.requireNonNull(paramLongConsumer);
/*     */       
/* 211 */       long l = this.from;
/* 212 */       if (l < this.upTo) {
/* 213 */         this.from += 1L;
/* 214 */         paramLongConsumer.accept(l);
/* 215 */         return true;
/*     */       }
/* 217 */       if (this.last > 0) {
/* 218 */         this.last = 0;
/* 219 */         paramLongConsumer.accept(l);
/* 220 */         return true;
/*     */       }
/* 222 */       return false;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(LongConsumer paramLongConsumer)
/*     */     {
/* 227 */       Objects.requireNonNull(paramLongConsumer);
/*     */       
/* 229 */       long l1 = this.from;
/* 230 */       long l2 = this.upTo;
/* 231 */       int i = this.last;
/* 232 */       this.from = this.upTo;
/* 233 */       this.last = 0;
/* 234 */       while (l1 < l2) {
/* 235 */         paramLongConsumer.accept(l1++);
/*     */       }
/* 237 */       if (i > 0)
/*     */       {
/* 239 */         paramLongConsumer.accept(l1);
/*     */       }
/*     */     }
/*     */     
/*     */     public long estimateSize()
/*     */     {
/* 245 */       return this.upTo - this.from + this.last;
/*     */     }
/*     */     
/*     */     public int characteristics()
/*     */     {
/* 250 */       return 17749;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Comparator<? super Long> getComparator()
/*     */     {
/* 257 */       return null;
/*     */     }
/*     */     
/*     */     public Spliterator.OfLong trySplit()
/*     */     {
/* 262 */       long l = estimateSize();
/*     */       
/*     */ 
/*     */ 
/* 266 */       return l <= 1L ? null : new RangeLongSpliterator(this.from, this.from = this.from + splitPoint(l), 0);
/*     */     }
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
/*     */     private long splitPoint(long paramLong)
/*     */     {
/* 295 */       long l = paramLong < 16777216L ? 2L : 8L;
/*     */       
/* 297 */       return paramLong / l;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static abstract class AbstractStreamBuilderImpl<T, S extends Spliterator<T>>
/*     */     implements Spliterator<T>
/*     */   {
/*     */     int count;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public S trySplit()
/*     */     {
/* 314 */       return null;
/*     */     }
/*     */     
/*     */     public long estimateSize()
/*     */     {
/* 319 */       return -this.count - 1;
/*     */     }
/*     */     
/*     */     public int characteristics()
/*     */     {
/* 324 */       return 17488;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class StreamBuilderImpl<T>
/*     */     extends Streams.AbstractStreamBuilderImpl<T, Spliterator<T>>
/*     */     implements Stream.Builder<T>
/*     */   {
/*     */     T first;
/*     */     
/*     */ 
/*     */     SpinedBuffer<T> buffer;
/*     */     
/*     */ 
/*     */ 
/*     */     StreamBuilderImpl()
/*     */     {
/* 343 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     StreamBuilderImpl(T paramT)
/*     */     {
/* 350 */       super();
/* 351 */       this.first = paramT;
/* 352 */       this.count = -2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(T paramT)
/*     */     {
/* 359 */       if (this.count == 0) {
/* 360 */         this.first = paramT;
/* 361 */         this.count += 1;
/*     */       }
/* 363 */       else if (this.count > 0) {
/* 364 */         if (this.buffer == null) {
/* 365 */           this.buffer = new SpinedBuffer();
/* 366 */           this.buffer.accept(this.first);
/* 367 */           this.count += 1;
/*     */         }
/*     */         
/* 370 */         this.buffer.accept(paramT);
/*     */       }
/*     */       else {
/* 373 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/*     */     
/*     */     public Stream.Builder<T> add(T paramT) {
/* 378 */       accept(paramT);
/* 379 */       return this;
/*     */     }
/*     */     
/*     */     public Stream<T> build()
/*     */     {
/* 384 */       int i = this.count;
/* 385 */       if (i >= 0)
/*     */       {
/* 387 */         this.count = (-this.count - 1);
/*     */         
/*     */ 
/* 390 */         return i < 2 ? StreamSupport.stream(this, false) : StreamSupport.stream(this.buffer.spliterator(), false);
/*     */       }
/*     */       
/* 393 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean tryAdvance(Consumer<? super T> paramConsumer)
/*     */     {
/* 402 */       Objects.requireNonNull(paramConsumer);
/*     */       
/* 404 */       if (this.count == -2) {
/* 405 */         paramConsumer.accept(this.first);
/* 406 */         this.count = -1;
/* 407 */         return true;
/*     */       }
/*     */       
/* 410 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public void forEachRemaining(Consumer<? super T> paramConsumer)
/*     */     {
/* 416 */       Objects.requireNonNull(paramConsumer);
/*     */       
/* 418 */       if (this.count == -2) {
/* 419 */         paramConsumer.accept(this.first);
/* 420 */         this.count = -1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class IntStreamBuilderImpl
/*     */     extends Streams.AbstractStreamBuilderImpl<Integer, Spliterator.OfInt>
/*     */     implements IntStream.Builder, Spliterator.OfInt
/*     */   {
/*     */     int first;
/*     */     
/*     */ 
/*     */     SpinedBuffer.OfInt buffer;
/*     */     
/*     */ 
/*     */     IntStreamBuilderImpl()
/*     */     {
/* 439 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     IntStreamBuilderImpl(int paramInt)
/*     */     {
/* 446 */       super();
/* 447 */       this.first = paramInt;
/* 448 */       this.count = -2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(int paramInt)
/*     */     {
/* 455 */       if (this.count == 0) {
/* 456 */         this.first = paramInt;
/* 457 */         this.count += 1;
/*     */       }
/* 459 */       else if (this.count > 0) {
/* 460 */         if (this.buffer == null) {
/* 461 */           this.buffer = new SpinedBuffer.OfInt();
/* 462 */           this.buffer.accept(this.first);
/* 463 */           this.count += 1;
/*     */         }
/*     */         
/* 466 */         this.buffer.accept(paramInt);
/*     */       }
/*     */       else {
/* 469 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/*     */     
/*     */     public IntStream build()
/*     */     {
/* 475 */       int i = this.count;
/* 476 */       if (i >= 0)
/*     */       {
/* 478 */         this.count = (-this.count - 1);
/*     */         
/*     */ 
/* 481 */         return i < 2 ? StreamSupport.intStream(this, false) : StreamSupport.intStream(this.buffer.spliterator(), false);
/*     */       }
/*     */       
/* 484 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*     */     {
/* 493 */       Objects.requireNonNull(paramIntConsumer);
/*     */       
/* 495 */       if (this.count == -2) {
/* 496 */         paramIntConsumer.accept(this.first);
/* 497 */         this.count = -1;
/* 498 */         return true;
/*     */       }
/*     */       
/* 501 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public void forEachRemaining(IntConsumer paramIntConsumer)
/*     */     {
/* 507 */       Objects.requireNonNull(paramIntConsumer);
/*     */       
/* 509 */       if (this.count == -2) {
/* 510 */         paramIntConsumer.accept(this.first);
/* 511 */         this.count = -1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class LongStreamBuilderImpl
/*     */     extends Streams.AbstractStreamBuilderImpl<Long, Spliterator.OfLong>
/*     */     implements LongStream.Builder, Spliterator.OfLong
/*     */   {
/*     */     long first;
/*     */     
/*     */ 
/*     */     SpinedBuffer.OfLong buffer;
/*     */     
/*     */ 
/*     */     LongStreamBuilderImpl()
/*     */     {
/* 530 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     LongStreamBuilderImpl(long paramLong)
/*     */     {
/* 537 */       super();
/* 538 */       this.first = paramLong;
/* 539 */       this.count = -2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(long paramLong)
/*     */     {
/* 546 */       if (this.count == 0) {
/* 547 */         this.first = paramLong;
/* 548 */         this.count += 1;
/*     */       }
/* 550 */       else if (this.count > 0) {
/* 551 */         if (this.buffer == null) {
/* 552 */           this.buffer = new SpinedBuffer.OfLong();
/* 553 */           this.buffer.accept(this.first);
/* 554 */           this.count += 1;
/*     */         }
/*     */         
/* 557 */         this.buffer.accept(paramLong);
/*     */       }
/*     */       else {
/* 560 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/*     */     
/*     */     public LongStream build()
/*     */     {
/* 566 */       int i = this.count;
/* 567 */       if (i >= 0)
/*     */       {
/* 569 */         this.count = (-this.count - 1);
/*     */         
/*     */ 
/* 572 */         return i < 2 ? StreamSupport.longStream(this, false) : StreamSupport.longStream(this.buffer.spliterator(), false);
/*     */       }
/*     */       
/* 575 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*     */     {
/* 584 */       Objects.requireNonNull(paramLongConsumer);
/*     */       
/* 586 */       if (this.count == -2) {
/* 587 */         paramLongConsumer.accept(this.first);
/* 588 */         this.count = -1;
/* 589 */         return true;
/*     */       }
/*     */       
/* 592 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public void forEachRemaining(LongConsumer paramLongConsumer)
/*     */     {
/* 598 */       Objects.requireNonNull(paramLongConsumer);
/*     */       
/* 600 */       if (this.count == -2) {
/* 601 */         paramLongConsumer.accept(this.first);
/* 602 */         this.count = -1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class DoubleStreamBuilderImpl
/*     */     extends Streams.AbstractStreamBuilderImpl<Double, Spliterator.OfDouble>
/*     */     implements DoubleStream.Builder, Spliterator.OfDouble
/*     */   {
/*     */     double first;
/*     */     
/*     */ 
/*     */     SpinedBuffer.OfDouble buffer;
/*     */     
/*     */ 
/*     */     DoubleStreamBuilderImpl()
/*     */     {
/* 621 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     DoubleStreamBuilderImpl(double paramDouble)
/*     */     {
/* 628 */       super();
/* 629 */       this.first = paramDouble;
/* 630 */       this.count = -2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(double paramDouble)
/*     */     {
/* 637 */       if (this.count == 0) {
/* 638 */         this.first = paramDouble;
/* 639 */         this.count += 1;
/*     */       }
/* 641 */       else if (this.count > 0) {
/* 642 */         if (this.buffer == null) {
/* 643 */           this.buffer = new SpinedBuffer.OfDouble();
/* 644 */           this.buffer.accept(this.first);
/* 645 */           this.count += 1;
/*     */         }
/*     */         
/* 648 */         this.buffer.accept(paramDouble);
/*     */       }
/*     */       else {
/* 651 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/*     */     
/*     */     public DoubleStream build()
/*     */     {
/* 657 */       int i = this.count;
/* 658 */       if (i >= 0)
/*     */       {
/* 660 */         this.count = (-this.count - 1);
/*     */         
/*     */ 
/* 663 */         return i < 2 ? StreamSupport.doubleStream(this, false) : StreamSupport.doubleStream(this.buffer.spliterator(), false);
/*     */       }
/*     */       
/* 666 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean tryAdvance(DoubleConsumer paramDoubleConsumer)
/*     */     {
/* 675 */       Objects.requireNonNull(paramDoubleConsumer);
/*     */       
/* 677 */       if (this.count == -2) {
/* 678 */         paramDoubleConsumer.accept(this.first);
/* 679 */         this.count = -1;
/* 680 */         return true;
/*     */       }
/*     */       
/* 683 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public void forEachRemaining(DoubleConsumer paramDoubleConsumer)
/*     */     {
/* 689 */       Objects.requireNonNull(paramDoubleConsumer);
/*     */       
/* 691 */       if (this.count == -2) {
/* 692 */         paramDoubleConsumer.accept(this.first);
/* 693 */         this.count = -1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class ConcatSpliterator<T, T_SPLITR extends Spliterator<T>>
/*     */     implements Spliterator<T>
/*     */   {
/*     */     protected final T_SPLITR aSpliterator;
/*     */     protected final T_SPLITR bSpliterator;
/*     */     boolean beforeSplit;
/*     */     final boolean unsized;
/*     */     
/*     */     public ConcatSpliterator(T_SPLITR paramT_SPLITR1, T_SPLITR paramT_SPLITR2)
/*     */     {
/* 708 */       this.aSpliterator = paramT_SPLITR1;
/* 709 */       this.bSpliterator = paramT_SPLITR2;
/* 710 */       this.beforeSplit = true;
/*     */       
/*     */ 
/* 713 */       this.unsized = (paramT_SPLITR1.estimateSize() + paramT_SPLITR2.estimateSize() < 0L);
/*     */     }
/*     */     
/*     */     public T_SPLITR trySplit()
/*     */     {
/* 718 */       Spliterator localSpliterator = this.beforeSplit ? this.aSpliterator : this.bSpliterator.trySplit();
/* 719 */       this.beforeSplit = false;
/* 720 */       return localSpliterator;
/*     */     }
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super T> paramConsumer)
/*     */     {
/*     */       boolean bool;
/* 726 */       if (this.beforeSplit) {
/* 727 */         bool = this.aSpliterator.tryAdvance(paramConsumer);
/* 728 */         if (!bool) {
/* 729 */           this.beforeSplit = false;
/* 730 */           bool = this.bSpliterator.tryAdvance(paramConsumer);
/*     */         }
/*     */       }
/*     */       else {
/* 734 */         bool = this.bSpliterator.tryAdvance(paramConsumer); }
/* 735 */       return bool;
/*     */     }
/*     */     
/*     */     public void forEachRemaining(Consumer<? super T> paramConsumer)
/*     */     {
/* 740 */       if (this.beforeSplit)
/* 741 */         this.aSpliterator.forEachRemaining(paramConsumer);
/* 742 */       this.bSpliterator.forEachRemaining(paramConsumer);
/*     */     }
/*     */     
/*     */     public long estimateSize()
/*     */     {
/* 747 */       if (this.beforeSplit)
/*     */       {
/*     */ 
/* 750 */         long l = this.aSpliterator.estimateSize() + this.bSpliterator.estimateSize();
/* 751 */         return l >= 0L ? l : Long.MAX_VALUE;
/*     */       }
/*     */       
/* 754 */       return this.bSpliterator.estimateSize();
/*     */     }
/*     */     
/*     */ 
/*     */     public int characteristics()
/*     */     {
/* 760 */       if (this.beforeSplit)
/*     */       {
/* 762 */         return this.aSpliterator.characteristics() & this.bSpliterator.characteristics() & ((0x5 | (this.unsized ? 16448 : 0)) ^ 0xFFFFFFFF);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 767 */       return this.bSpliterator.characteristics();
/*     */     }
/*     */     
/*     */ 
/*     */     public Comparator<? super T> getComparator()
/*     */     {
/* 773 */       if (this.beforeSplit)
/* 774 */         throw new IllegalStateException();
/* 775 */       return this.bSpliterator.getComparator();
/*     */     }
/*     */     
/*     */     static class OfRef<T> extends Streams.ConcatSpliterator<T, Spliterator<T>> {
/*     */       OfRef(Spliterator<T> paramSpliterator1, Spliterator<T> paramSpliterator2) {
/* 780 */         super(paramSpliterator2);
/*     */       }
/*     */     }
/*     */     
/*     */     private static abstract class OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> extends Streams.ConcatSpliterator<T, T_SPLITR> implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>
/*     */     {
/*     */       private OfPrimitive(T_SPLITR paramT_SPLITR1, T_SPLITR paramT_SPLITR2)
/*     */       {
/* 788 */         super(paramT_SPLITR2);
/*     */       }
/*     */       
/*     */       public boolean tryAdvance(T_CONS paramT_CONS)
/*     */       {
/*     */         boolean bool;
/* 794 */         if (this.beforeSplit) {
/* 795 */           bool = ((Spliterator.OfPrimitive)this.aSpliterator).tryAdvance(paramT_CONS);
/* 796 */           if (!bool) {
/* 797 */             this.beforeSplit = false;
/* 798 */             bool = ((Spliterator.OfPrimitive)this.bSpliterator).tryAdvance(paramT_CONS);
/*     */           }
/*     */         }
/*     */         else {
/* 802 */           bool = ((Spliterator.OfPrimitive)this.bSpliterator).tryAdvance(paramT_CONS); }
/* 803 */         return bool;
/*     */       }
/*     */       
/*     */       public void forEachRemaining(T_CONS paramT_CONS)
/*     */       {
/* 808 */         if (this.beforeSplit)
/* 809 */           ((Spliterator.OfPrimitive)this.aSpliterator).forEachRemaining(paramT_CONS);
/* 810 */         ((Spliterator.OfPrimitive)this.bSpliterator).forEachRemaining(paramT_CONS);
/*     */       }
/*     */     }
/*     */     
/*     */     static class OfInt extends Streams.ConcatSpliterator.OfPrimitive<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt
/*     */     {
/*     */       OfInt(Spliterator.OfInt paramOfInt1, Spliterator.OfInt paramOfInt2)
/*     */       {
/* 818 */         super(paramOfInt2, null);
/*     */       }
/*     */     }
/*     */     
/*     */     static class OfLong extends Streams.ConcatSpliterator.OfPrimitive<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong
/*     */     {
/*     */       OfLong(Spliterator.OfLong paramOfLong1, Spliterator.OfLong paramOfLong2)
/*     */       {
/* 826 */         super(paramOfLong2, null);
/*     */       }
/*     */     }
/*     */     
/*     */     static class OfDouble extends Streams.ConcatSpliterator.OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble
/*     */     {
/*     */       OfDouble(Spliterator.OfDouble paramOfDouble1, Spliterator.OfDouble paramOfDouble2)
/*     */       {
/* 834 */         super(paramOfDouble2, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Runnable composeWithExceptions(Runnable paramRunnable1, final Runnable paramRunnable2)
/*     */   {
/* 845 */     new Runnable()
/*     */     {
/*     */       public void run() {
/*     */         try {
/* 849 */           this.val$a.run();
/*     */         }
/*     */         catch (Throwable localThrowable1) {
/*     */           try {
/* 853 */             paramRunnable2.run();
/*     */           }
/*     */           catch (Throwable localThrowable2) {
/*     */             try {
/* 857 */               localThrowable1.addSuppressed(localThrowable2);
/*     */             } catch (Throwable localThrowable3) {}
/*     */           }
/* 860 */           throw localThrowable1;
/*     */         }
/* 862 */         paramRunnable2.run();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Runnable composedClose(BaseStream<?, ?> paramBaseStream1, final BaseStream<?, ?> paramBaseStream2)
/*     */   {
/* 874 */     new Runnable()
/*     */     {
/*     */       public void run() {
/*     */         try {
/* 878 */           this.val$a.close();
/*     */         }
/*     */         catch (Throwable localThrowable1) {
/*     */           try {
/* 882 */             paramBaseStream2.close();
/*     */           }
/*     */           catch (Throwable localThrowable2) {
/*     */             try {
/* 886 */               localThrowable1.addSuppressed(localThrowable2);
/*     */             } catch (Throwable localThrowable3) {}
/*     */           }
/* 889 */           throw localThrowable1;
/*     */         }
/* 891 */         paramBaseStream2.close();
/*     */       }
/*     */     };
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/Streams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */