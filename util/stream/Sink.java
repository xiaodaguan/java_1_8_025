/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract interface Sink<T>
/*     */   extends Consumer<T>
/*     */ {
/*     */   public void begin(long paramLong) {}
/*     */   
/*     */   public void end() {}
/*     */   
/*     */   public boolean cancellationRequested()
/*     */   {
/* 148 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void accept(int paramInt)
/*     */   {
/* 159 */     throw new IllegalStateException("called wrong accept method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void accept(long paramLong)
/*     */   {
/* 170 */     throw new IllegalStateException("called wrong accept method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void accept(double paramDouble)
/*     */   {
/* 181 */     throw new IllegalStateException("called wrong accept method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static abstract interface OfInt
/*     */     extends Sink<Integer>, IntConsumer
/*     */   {
/*     */     public abstract void accept(int paramInt);
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(Integer paramInteger)
/*     */     {
/* 195 */       if (Tripwire.ENABLED)
/* 196 */         Tripwire.trip(getClass(), "{0} calling Sink.OfInt.accept(Integer)");
/* 197 */       accept(paramInteger.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static abstract interface OfLong
/*     */     extends Sink<Long>, LongConsumer
/*     */   {
/*     */     public abstract void accept(long paramLong);
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(Long paramLong)
/*     */     {
/* 212 */       if (Tripwire.ENABLED)
/* 213 */         Tripwire.trip(getClass(), "{0} calling Sink.OfLong.accept(Long)");
/* 214 */       accept(paramLong.longValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static abstract interface OfDouble
/*     */     extends Sink<Double>, DoubleConsumer
/*     */   {
/*     */     public abstract void accept(double paramDouble);
/*     */     
/*     */ 
/*     */ 
/*     */     public void accept(Double paramDouble)
/*     */     {
/* 229 */       if (Tripwire.ENABLED)
/* 230 */         Tripwire.trip(getClass(), "{0} calling Sink.OfDouble.accept(Double)");
/* 231 */       accept(paramDouble.doubleValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class ChainedReference<T, E_OUT>
/*     */     implements Sink<T>
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ChainedReference(Sink<? super E_OUT> paramSink)
/*     */     {
/* 248 */       this.downstream = ((Sink)Objects.requireNonNull(paramSink));
/*     */     }
/*     */     
/*     */     public void begin(long paramLong)
/*     */     {
/* 253 */       this.downstream.begin(paramLong);
/*     */     }
/*     */     
/*     */     public void end()
/*     */     {
/* 258 */       this.downstream.end();
/*     */     }
/*     */     
/*     */     public boolean cancellationRequested()
/*     */     {
/* 263 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class ChainedInt<E_OUT>
/*     */     implements Sink.OfInt
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ChainedInt(Sink<? super E_OUT> paramSink)
/*     */     {
/* 280 */       this.downstream = ((Sink)Objects.requireNonNull(paramSink));
/*     */     }
/*     */     
/*     */     public void begin(long paramLong)
/*     */     {
/* 285 */       this.downstream.begin(paramLong);
/*     */     }
/*     */     
/*     */     public void end()
/*     */     {
/* 290 */       this.downstream.end();
/*     */     }
/*     */     
/*     */     public boolean cancellationRequested()
/*     */     {
/* 295 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class ChainedLong<E_OUT>
/*     */     implements Sink.OfLong
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ChainedLong(Sink<? super E_OUT> paramSink)
/*     */     {
/* 312 */       this.downstream = ((Sink)Objects.requireNonNull(paramSink));
/*     */     }
/*     */     
/*     */     public void begin(long paramLong)
/*     */     {
/* 317 */       this.downstream.begin(paramLong);
/*     */     }
/*     */     
/*     */     public void end()
/*     */     {
/* 322 */       this.downstream.end();
/*     */     }
/*     */     
/*     */     public boolean cancellationRequested()
/*     */     {
/* 327 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class ChainedDouble<E_OUT>
/*     */     implements Sink.OfDouble
/*     */   {
/*     */     protected final Sink<? super E_OUT> downstream;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ChainedDouble(Sink<? super E_OUT> paramSink)
/*     */     {
/* 344 */       this.downstream = ((Sink)Objects.requireNonNull(paramSink));
/*     */     }
/*     */     
/*     */     public void begin(long paramLong)
/*     */     {
/* 349 */       this.downstream.begin(paramLong);
/*     */     }
/*     */     
/*     */     public void end()
/*     */     {
/* 354 */       this.downstream.end();
/*     */     }
/*     */     
/*     */     public boolean cancellationRequested()
/*     */     {
/* 359 */       return this.downstream.cancellationRequested();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/Sink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */