/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterator.OfDouble;
/*     */ import java.util.Spliterator.OfInt;
/*     */ import java.util.Spliterator.OfLong;
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
/*     */ public final class StreamSupport
/*     */ {
/*     */   public static <T> Stream<T> stream(Spliterator<T> paramSpliterator, boolean paramBoolean)
/*     */   {
/*  68 */     Objects.requireNonNull(paramSpliterator);
/*     */     
/*  70 */     return new ReferencePipeline.Head(paramSpliterator, StreamOpFlag.fromCharacteristics(paramSpliterator), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> Stream<T> stream(Supplier<? extends Spliterator<T>> paramSupplier, int paramInt, boolean paramBoolean)
/*     */   {
/* 110 */     Objects.requireNonNull(paramSupplier);
/*     */     
/* 112 */     return new ReferencePipeline.Head(paramSupplier, StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static IntStream intStream(Spliterator.OfInt paramOfInt, boolean paramBoolean)
/*     */   {
/* 139 */     return new IntPipeline.Head(paramOfInt, StreamOpFlag.fromCharacteristics(paramOfInt), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static IntStream intStream(Supplier<? extends Spliterator.OfInt> paramSupplier, int paramInt, boolean paramBoolean)
/*     */   {
/* 179 */     return new IntPipeline.Head(paramSupplier, StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream longStream(Spliterator.OfLong paramOfLong, boolean paramBoolean)
/*     */   {
/* 207 */     return new LongPipeline.Head(paramOfLong, StreamOpFlag.fromCharacteristics(paramOfLong), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LongStream longStream(Supplier<? extends Spliterator.OfLong> paramSupplier, int paramInt, boolean paramBoolean)
/*     */   {
/* 247 */     return new LongPipeline.Head(paramSupplier, StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DoubleStream doubleStream(Spliterator.OfDouble paramOfDouble, boolean paramBoolean)
/*     */   {
/* 275 */     return new DoublePipeline.Head(paramOfDouble, StreamOpFlag.fromCharacteristics(paramOfDouble), paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DoubleStream doubleStream(Supplier<? extends Spliterator.OfDouble> paramSupplier, int paramInt, boolean paramBoolean)
/*     */   {
/* 315 */     return new DoublePipeline.Head(paramSupplier, StreamOpFlag.fromCharacteristics(paramInt), paramBoolean);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/StreamSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */