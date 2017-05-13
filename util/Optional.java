/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
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
/*     */ public final class Optional<T>
/*     */ {
/*  54 */   private static final Optional<?> EMPTY = new Optional();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final T value;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Optional()
/*     */   {
/*  68 */     this.value = null;
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
/*     */   public static <T> Optional<T> empty()
/*     */   {
/*  85 */     Optional localOptional = EMPTY;
/*  86 */     return localOptional;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Optional(T paramT)
/*     */   {
/*  96 */     this.value = Objects.requireNonNull(paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> Optional<T> of(T paramT)
/*     */   {
/* 108 */     return new Optional(paramT);
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
/*     */   public static <T> Optional<T> ofNullable(T paramT)
/*     */   {
/* 121 */     return paramT == null ? empty() : of(paramT);
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
/*     */   public T get()
/*     */   {
/* 134 */     if (this.value == null) {
/* 135 */       throw new NoSuchElementException("No value present");
/*     */     }
/* 137 */     return (T)this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPresent()
/*     */   {
/* 146 */     return this.value != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void ifPresent(Consumer<? super T> paramConsumer)
/*     */   {
/* 158 */     if (this.value != null) {
/* 159 */       paramConsumer.accept(this.value);
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
/*     */ 
/*     */ 
/*     */   public Optional<T> filter(Predicate<? super T> paramPredicate)
/*     */   {
/* 174 */     Objects.requireNonNull(paramPredicate);
/* 175 */     if (!isPresent()) {
/* 176 */       return this;
/*     */     }
/* 178 */     return paramPredicate.test(this.value) ? this : empty();
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
/*     */   public <U> Optional<U> map(Function<? super T, ? extends U> paramFunction)
/*     */   {
/* 211 */     Objects.requireNonNull(paramFunction);
/* 212 */     if (!isPresent()) {
/* 213 */       return empty();
/*     */     }
/* 215 */     return ofNullable(paramFunction.apply(this.value));
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
/*     */   public <U> Optional<U> flatMap(Function<? super T, Optional<U>> paramFunction)
/*     */   {
/* 237 */     Objects.requireNonNull(paramFunction);
/* 238 */     if (!isPresent()) {
/* 239 */       return empty();
/*     */     }
/* 241 */     return (Optional)Objects.requireNonNull(paramFunction.apply(this.value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T orElse(T paramT)
/*     */   {
/* 253 */     return (T)(this.value != null ? this.value : paramT);
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
/*     */   public T orElseGet(Supplier<? extends T> paramSupplier)
/*     */   {
/* 267 */     return (T)(this.value != null ? this.value : paramSupplier.get());
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
/*     */   public <X extends Throwable> T orElseThrow(Supplier<? extends X> paramSupplier)
/*     */     throws Throwable
/*     */   {
/* 287 */     if (this.value != null) {
/* 288 */       return (T)this.value;
/*     */     }
/* 290 */     throw ((Throwable)paramSupplier.get());
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 309 */     if (this == paramObject) {
/* 310 */       return true;
/*     */     }
/*     */     
/* 313 */     if (!(paramObject instanceof Optional)) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     Optional localOptional = (Optional)paramObject;
/* 318 */     return Objects.equals(this.value, localOptional.value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 329 */     return Objects.hashCode(this.value);
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
/*     */   public String toString()
/*     */   {
/* 346 */     return this.value != null ? String.format("Optional[%s]", new Object[] { this.value }) : "Optional.empty";
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Optional.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */