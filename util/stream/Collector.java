/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.Function;
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
/*     */ public abstract interface Collector<T, A, R>
/*     */ {
/*     */   public abstract Supplier<A> supplier();
/*     */   
/*     */   public abstract BiConsumer<A, T> accumulator();
/*     */   
/*     */   public abstract BinaryOperator<A> combiner();
/*     */   
/*     */   public abstract Function<A, R> finisher();
/*     */   
/*     */   public abstract Set<Characteristics> characteristics();
/*     */   
/*     */   public static <T, R> Collector<T, R, R> of(Supplier<R> paramSupplier, BiConsumer<R, T> paramBiConsumer, BinaryOperator<R> paramBinaryOperator, Characteristics... paramVarArgs)
/*     */   {
/* 264 */     Objects.requireNonNull(paramSupplier);
/* 265 */     Objects.requireNonNull(paramBiConsumer);
/* 266 */     Objects.requireNonNull(paramBinaryOperator);
/* 267 */     Objects.requireNonNull(paramVarArgs);
/*     */     
/*     */ 
/* 270 */     Set localSet = paramVarArgs.length == 0 ? Collectors.CH_ID : Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, paramVarArgs));
/*     */     
/* 272 */     return new Collectors.CollectorImpl(paramSupplier, paramBiConsumer, paramBinaryOperator, localSet);
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
/*     */   public static <T, A, R> Collector<T, A, R> of(Supplier<A> paramSupplier, BiConsumer<A, T> paramBiConsumer, BinaryOperator<A> paramBinaryOperator, Function<A, R> paramFunction, Characteristics... paramVarArgs)
/*     */   {
/* 296 */     Objects.requireNonNull(paramSupplier);
/* 297 */     Objects.requireNonNull(paramBiConsumer);
/* 298 */     Objects.requireNonNull(paramBinaryOperator);
/* 299 */     Objects.requireNonNull(paramFunction);
/* 300 */     Objects.requireNonNull(paramVarArgs);
/* 301 */     Object localObject = Collectors.CH_NOID;
/* 302 */     if (paramVarArgs.length > 0) {
/* 303 */       localObject = EnumSet.noneOf(Characteristics.class);
/* 304 */       Collections.addAll((Collection)localObject, paramVarArgs);
/* 305 */       localObject = Collections.unmodifiableSet((Set)localObject);
/*     */     }
/* 307 */     return new Collectors.CollectorImpl(paramSupplier, paramBiConsumer, paramBinaryOperator, paramFunction, (Set)localObject);
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
/*     */   public static enum Characteristics
/*     */   {
/* 325 */     CONCURRENT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 332 */     UNORDERED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 339 */     IDENTITY_FINISH;
/*     */     
/*     */     private Characteristics() {}
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/Collector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */