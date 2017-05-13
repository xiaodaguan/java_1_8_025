/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.LongSummaryStatistics;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.PrimitiveIterator.OfLong;
/*     */ import java.util.Spliterator.OfLong;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.LongFunction;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.LongSupplier;
/*     */ import java.util.function.LongToDoubleFunction;
/*     */ import java.util.function.LongToIntFunction;
/*     */ import java.util.function.LongUnaryOperator;
/*     */ import java.util.function.ObjLongConsumer;
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
/*     */ public abstract interface LongStream
/*     */   extends BaseStream<Long, LongStream>
/*     */ {
/*     */   public abstract LongStream filter(LongPredicate paramLongPredicate);
/*     */   
/*     */   public abstract LongStream map(LongUnaryOperator paramLongUnaryOperator);
/*     */   
/*     */   public abstract <U> Stream<U> mapToObj(LongFunction<? extends U> paramLongFunction);
/*     */   
/*     */   public abstract IntStream mapToInt(LongToIntFunction paramLongToIntFunction);
/*     */   
/*     */   public abstract DoubleStream mapToDouble(LongToDoubleFunction paramLongToDoubleFunction);
/*     */   
/*     */   public abstract LongStream flatMap(LongFunction<? extends LongStream> paramLongFunction);
/*     */   
/*     */   public abstract LongStream distinct();
/*     */   
/*     */   public abstract LongStream sorted();
/*     */   
/*     */   public abstract LongStream peek(LongConsumer paramLongConsumer);
/*     */   
/*     */   public abstract LongStream limit(long paramLong);
/*     */   
/*     */   public abstract LongStream skip(long paramLong);
/*     */   
/*     */   public abstract void forEach(LongConsumer paramLongConsumer);
/*     */   
/*     */   public abstract void forEachOrdered(LongConsumer paramLongConsumer);
/*     */   
/*     */   public abstract long[] toArray();
/*     */   
/*     */   public abstract long reduce(long paramLong, LongBinaryOperator paramLongBinaryOperator);
/*     */   
/*     */   public abstract OptionalLong reduce(LongBinaryOperator paramLongBinaryOperator);
/*     */   
/*     */   public abstract <R> R collect(Supplier<R> paramSupplier, ObjLongConsumer<R> paramObjLongConsumer, BiConsumer<R, R> paramBiConsumer);
/*     */   
/*     */   public abstract long sum();
/*     */   
/*     */   public abstract OptionalLong min();
/*     */   
/*     */   public abstract OptionalLong max();
/*     */   
/*     */   public abstract long count();
/*     */   
/*     */   public abstract OptionalDouble average();
/*     */   
/*     */   public abstract LongSummaryStatistics summaryStatistics();
/*     */   
/*     */   public abstract boolean anyMatch(LongPredicate paramLongPredicate);
/*     */   
/*     */   public abstract boolean allMatch(LongPredicate paramLongPredicate);
/*     */   
/*     */   public abstract boolean noneMatch(LongPredicate paramLongPredicate);
/*     */   
/*     */   public abstract OptionalLong findFirst();
/*     */   
/*     */   public abstract OptionalLong findAny();
/*     */   
/*     */   public abstract DoubleStream asDoubleStream();
/*     */   
/*     */   public abstract Stream<Long> boxed();
/*     */   
/*     */   public abstract LongStream sequential();
/*     */   
/*     */   public abstract LongStream parallel();
/*     */   
/*     */   public abstract PrimitiveIterator.OfLong iterator();
/*     */   
/*     */   public abstract Spliterator.OfLong spliterator();
/*     */   
/*     */   public static Builder builder()
/*     */   {
/* 686 */     return new Streams.LongStreamBuilderImpl();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream empty()
/*     */   {
/* 695 */     return StreamSupport.longStream(Spliterators.emptyLongSpliterator(), false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream of(long paramLong)
/*     */   {
/* 705 */     return StreamSupport.longStream(new Streams.LongStreamBuilderImpl(paramLong), false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream of(long... paramVarArgs)
/*     */   {
/* 715 */     return Arrays.stream(paramVarArgs);
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
/*     */ 
/*     */   public static LongStream iterate(long paramLong, LongUnaryOperator paramLongUnaryOperator)
/*     */   {
/* 735 */     Objects.requireNonNull(paramLongUnaryOperator);
/* 736 */     PrimitiveIterator.OfLong local1 = new PrimitiveIterator.OfLong() {
/* 737 */       long t = this.val$seed;
/*     */       
/*     */       public boolean hasNext()
/*     */       {
/* 741 */         return true;
/*     */       }
/*     */       
/*     */       public long nextLong()
/*     */       {
/* 746 */         long l = this.t;
/* 747 */         this.t = this.val$f.applyAsLong(this.t);
/* 748 */         return l;
/*     */       }
/* 750 */     };
/* 751 */     return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(local1, 1296), false);
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
/*     */   public static LongStream generate(LongSupplier paramLongSupplier)
/*     */   {
/* 765 */     Objects.requireNonNull(paramLongSupplier);
/* 766 */     return StreamSupport.longStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfLong(Long.MAX_VALUE, paramLongSupplier), false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream range(long paramLong1, long paramLong2)
/*     */   {
/* 788 */     if (paramLong1 >= paramLong2)
/* 789 */       return empty();
/* 790 */     if (paramLong2 - paramLong1 < 0L)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 795 */       long l = paramLong1 + Long.divideUnsigned(paramLong2 - paramLong1, 2L) + 1L;
/* 796 */       return concat(range(paramLong1, l), range(l, paramLong2));
/*     */     }
/* 798 */     return StreamSupport.longStream(new Streams.RangeLongSpliterator(paramLong1, paramLong2, false), false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream rangeClosed(long paramLong1, long paramLong2)
/*     */   {
/* 821 */     if (paramLong1 > paramLong2)
/* 822 */       return empty();
/* 823 */     if (paramLong2 - paramLong1 + 1L <= 0L)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 829 */       long l = paramLong1 + Long.divideUnsigned(paramLong2 - paramLong1, 2L) + 1L;
/* 830 */       return concat(range(paramLong1, l), rangeClosed(l, paramLong2));
/*     */     }
/* 832 */     return StreamSupport.longStream(new Streams.RangeLongSpliterator(paramLong1, paramLong2, true), false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream concat(LongStream paramLongStream1, LongStream paramLongStream2)
/*     */   {
/* 855 */     Objects.requireNonNull(paramLongStream1);
/* 856 */     Objects.requireNonNull(paramLongStream2);
/*     */     
/*     */ 
/* 859 */     Streams.ConcatSpliterator.OfLong localOfLong = new Streams.ConcatSpliterator.OfLong(paramLongStream1.spliterator(), paramLongStream2.spliterator());
/* 860 */     LongStream localLongStream = StreamSupport.longStream(localOfLong, (paramLongStream1.isParallel()) || (paramLongStream2.isParallel()));
/* 861 */     return (LongStream)localLongStream.onClose(Streams.composedClose(paramLongStream1, paramLongStream2));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract interface Builder
/*     */     extends LongConsumer
/*     */   {
/*     */     public abstract void accept(long paramLong);
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
/*     */     public Builder add(long paramLong)
/*     */     {
/* 904 */       accept(paramLong);
/* 905 */       return this;
/*     */     }
/*     */     
/*     */     public abstract LongStream build();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/LongStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */