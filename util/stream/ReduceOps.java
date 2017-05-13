/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.ObjDoubleConsumer;
/*     */ import java.util.function.ObjIntConsumer;
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
/*     */ final class ReduceOps
/*     */ {
/*     */   public static <T, U> TerminalOp<T, U> makeRef(final U paramU, final BiFunction<U, ? super T, U> paramBiFunction, final BinaryOperator<U> paramBinaryOperator)
/*     */   {
/*  70 */     Objects.requireNonNull(paramBiFunction);
/*  71 */     Objects.requireNonNull(paramBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  88 */     new ReduceOp(StreamShape.REFERENCE)
/*     */     {
/*     */       public ReduceOps.1ReducingSink makeSink() {
/*  91 */         return new ReduceOps.1ReducingSink(paramU, paramBiFunction, paramBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static <T> TerminalOp<T, Optional<T>> makeRef(final BinaryOperator<T> paramBinaryOperator)
/*     */   {
/* 106 */     Objects.requireNonNull(paramBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 138 */     new ReduceOp(StreamShape.REFERENCE)
/*     */     {
/*     */       public ReduceOps.2ReducingSink makeSink() {
/* 141 */         return new ReduceOps.2ReducingSink(paramBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static <T, I> TerminalOp<T, I> makeRef(final Collector<? super T, I, ?> paramCollector)
/*     */   {
/* 157 */     final Supplier localSupplier = ((Collector)Objects.requireNonNull(paramCollector)).supplier();
/* 158 */     final BiConsumer localBiConsumer = paramCollector.accumulator();
/* 159 */     final BinaryOperator localBinaryOperator = paramCollector.combiner();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */     new ReduceOp(StreamShape.REFERENCE)
/*     */     {
/*     */       public ReduceOps.3ReducingSink makeSink() {
/* 180 */         return new ReduceOps.3ReducingSink(localSupplier, localBiConsumer, localBinaryOperator);
/*     */       }
/*     */       
/*     */       public int getOpFlags()
/*     */       {
/* 185 */         return paramCollector.characteristics().contains(Collector.Characteristics.UNORDERED) ? StreamOpFlag.NOT_ORDERED : 0;
/*     */       }
/*     */     };
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
/*     */   public static <T, R> TerminalOp<T, R> makeRef(final Supplier<R> paramSupplier, final BiConsumer<R, ? super T> paramBiConsumer, final BiConsumer<R, R> paramBiConsumer1)
/*     */   {
/* 208 */     Objects.requireNonNull(paramSupplier);
/* 209 */     Objects.requireNonNull(paramBiConsumer);
/* 210 */     Objects.requireNonNull(paramBiConsumer1);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 228 */     new ReduceOp(StreamShape.REFERENCE)
/*     */     {
/*     */       public ReduceOps.4ReducingSink makeSink() {
/* 231 */         return new ReduceOps.4ReducingSink(paramSupplier, paramBiConsumer, paramBiConsumer1);
/*     */       }
/*     */     };
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
/*     */   public static TerminalOp<Integer, Integer> makeInt(final int paramInt, final IntBinaryOperator paramIntBinaryOperator)
/*     */   {
/* 246 */     Objects.requireNonNull(paramIntBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 271 */     new ReduceOp(StreamShape.INT_VALUE)
/*     */     {
/*     */       public ReduceOps.5ReducingSink makeSink() {
/* 274 */         return new ReduceOps.5ReducingSink(paramInt, paramIntBinaryOperator);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static TerminalOp<Integer, OptionalInt> makeInt(final IntBinaryOperator paramIntBinaryOperator)
/*     */   {
/* 288 */     Objects.requireNonNull(paramIntBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 321 */     new ReduceOp(StreamShape.INT_VALUE)
/*     */     {
/*     */       public ReduceOps.6ReducingSink makeSink() {
/* 324 */         return new ReduceOps.6ReducingSink(paramIntBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static <R> TerminalOp<Integer, R> makeInt(final Supplier<R> paramSupplier, final ObjIntConsumer<R> paramObjIntConsumer, final BinaryOperator<R> paramBinaryOperator)
/*     */   {
/* 344 */     Objects.requireNonNull(paramSupplier);
/* 345 */     Objects.requireNonNull(paramObjIntConsumer);
/* 346 */     Objects.requireNonNull(paramBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 364 */     new ReduceOp(StreamShape.INT_VALUE)
/*     */     {
/*     */       public ReduceOps.7ReducingSink makeSink() {
/* 367 */         return new ReduceOps.7ReducingSink(paramSupplier, paramObjIntConsumer, paramBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static TerminalOp<Long, Long> makeLong(final long paramLong, final LongBinaryOperator paramLongBinaryOperator)
/*     */   {
/* 382 */     Objects.requireNonNull(paramLongBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 407 */     new ReduceOp(StreamShape.LONG_VALUE)
/*     */     {
/*     */       public ReduceOps.8ReducingSink makeSink() {
/* 410 */         return new ReduceOps.8ReducingSink(paramLong, paramLongBinaryOperator);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static TerminalOp<Long, OptionalLong> makeLong(final LongBinaryOperator paramLongBinaryOperator)
/*     */   {
/* 424 */     Objects.requireNonNull(paramLongBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 457 */     new ReduceOp(StreamShape.LONG_VALUE)
/*     */     {
/*     */       public ReduceOps.9ReducingSink makeSink() {
/* 460 */         return new ReduceOps.9ReducingSink(paramLongBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static <R> TerminalOp<Long, R> makeLong(final Supplier<R> paramSupplier, final ObjLongConsumer<R> paramObjLongConsumer, final BinaryOperator<R> paramBinaryOperator)
/*     */   {
/* 480 */     Objects.requireNonNull(paramSupplier);
/* 481 */     Objects.requireNonNull(paramObjLongConsumer);
/* 482 */     Objects.requireNonNull(paramBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 500 */     new ReduceOp(StreamShape.LONG_VALUE)
/*     */     {
/*     */       public ReduceOps.10ReducingSink makeSink() {
/* 503 */         return new ReduceOps.10ReducingSink(paramSupplier, paramObjLongConsumer, paramBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static TerminalOp<Double, Double> makeDouble(final double paramDouble, final DoubleBinaryOperator paramDoubleBinaryOperator)
/*     */   {
/* 518 */     Objects.requireNonNull(paramDoubleBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 543 */     new ReduceOp(StreamShape.DOUBLE_VALUE)
/*     */     {
/*     */       public ReduceOps.11ReducingSink makeSink() {
/* 546 */         return new ReduceOps.11ReducingSink(paramDouble, paramDoubleBinaryOperator);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static TerminalOp<Double, OptionalDouble> makeDouble(final DoubleBinaryOperator paramDoubleBinaryOperator)
/*     */   {
/* 560 */     Objects.requireNonNull(paramDoubleBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 593 */     new ReduceOp(StreamShape.DOUBLE_VALUE)
/*     */     {
/*     */       public ReduceOps.12ReducingSink makeSink() {
/* 596 */         return new ReduceOps.12ReducingSink(paramDoubleBinaryOperator);
/*     */       }
/*     */     };
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
/*     */   public static <R> TerminalOp<Double, R> makeDouble(final Supplier<R> paramSupplier, final ObjDoubleConsumer<R> paramObjDoubleConsumer, final BinaryOperator<R> paramBinaryOperator)
/*     */   {
/* 616 */     Objects.requireNonNull(paramSupplier);
/* 617 */     Objects.requireNonNull(paramObjDoubleConsumer);
/* 618 */     Objects.requireNonNull(paramBinaryOperator);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 636 */     new ReduceOp(StreamShape.DOUBLE_VALUE)
/*     */     {
/*     */       public ReduceOps.13ReducingSink makeSink() {
/* 639 */         return new ReduceOps.13ReducingSink(paramSupplier, paramObjDoubleConsumer, paramBinaryOperator);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static abstract interface AccumulatingSink<T, R, K extends AccumulatingSink<T, R, K>>
/*     */     extends TerminalSink<T, R>
/*     */   {
/*     */     public abstract void combine(K paramK);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static abstract class Box<U>
/*     */   {
/*     */     U state;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public U get()
/*     */     {
/* 670 */       return (U)this.state;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static abstract class ReduceOp<T, R, S extends ReduceOps.AccumulatingSink<T, R, S>>
/*     */     implements TerminalOp<T, R>
/*     */   {
/*     */     private final StreamShape inputShape;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     ReduceOp(StreamShape paramStreamShape)
/*     */     {
/* 695 */       this.inputShape = paramStreamShape;
/*     */     }
/*     */     
/*     */     public abstract S makeSink();
/*     */     
/*     */     public StreamShape inputShape()
/*     */     {
/* 702 */       return this.inputShape;
/*     */     }
/*     */     
/*     */ 
/*     */     public <P_IN> R evaluateSequential(PipelineHelper<T> paramPipelineHelper, Spliterator<P_IN> paramSpliterator)
/*     */     {
/* 708 */       return (R)((ReduceOps.AccumulatingSink)paramPipelineHelper.wrapAndCopyInto(makeSink(), paramSpliterator)).get();
/*     */     }
/*     */     
/*     */ 
/*     */     public <P_IN> R evaluateParallel(PipelineHelper<T> paramPipelineHelper, Spliterator<P_IN> paramSpliterator)
/*     */     {
/* 714 */       return (R)((ReduceOps.AccumulatingSink)new ReduceOps.ReduceTask(this, paramPipelineHelper, paramSpliterator).invoke()).get();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ReduceTask<P_IN, P_OUT, R, S extends ReduceOps.AccumulatingSink<P_OUT, R, S>>
/*     */     extends AbstractTask<P_IN, P_OUT, S, ReduceTask<P_IN, P_OUT, R, S>>
/*     */   {
/*     */     private final ReduceOps.ReduceOp<P_OUT, R, S> op;
/*     */     
/*     */ 
/*     */ 
/*     */     ReduceTask(ReduceOps.ReduceOp<P_OUT, R, S> paramReduceOp, PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator)
/*     */     {
/* 730 */       super(paramSpliterator);
/* 731 */       this.op = paramReduceOp;
/*     */     }
/*     */     
/*     */     ReduceTask(ReduceTask<P_IN, P_OUT, R, S> paramReduceTask, Spliterator<P_IN> paramSpliterator)
/*     */     {
/* 736 */       super(paramSpliterator);
/* 737 */       this.op = paramReduceTask.op;
/*     */     }
/*     */     
/*     */     protected ReduceTask<P_IN, P_OUT, R, S> makeChild(Spliterator<P_IN> paramSpliterator)
/*     */     {
/* 742 */       return new ReduceTask(this, paramSpliterator);
/*     */     }
/*     */     
/*     */     protected S doLeaf()
/*     */     {
/* 747 */       return (ReduceOps.AccumulatingSink)this.helper.wrapAndCopyInto(this.op.makeSink(), this.spliterator);
/*     */     }
/*     */     
/*     */     public void onCompletion(CountedCompleter<?> paramCountedCompleter)
/*     */     {
/* 752 */       if (!isLeaf()) {
/* 753 */         ReduceOps.AccumulatingSink localAccumulatingSink = (ReduceOps.AccumulatingSink)((ReduceTask)this.leftChild).getLocalResult();
/* 754 */         localAccumulatingSink.combine((ReduceOps.AccumulatingSink)((ReduceTask)this.rightChild).getLocalResult());
/* 755 */         setLocalResult(localAccumulatingSink);
/*     */       }
/*     */       
/* 758 */       super.onCompletion(paramCountedCompleter);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/ReduceOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */