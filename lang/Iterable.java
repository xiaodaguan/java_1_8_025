/*     */ package java.lang;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface Iterable<T>
/*     */ {
/*     */   public abstract Iterator<T> iterator();
/*     */   
/*     */   public void forEach(Consumer<? super T> paramConsumer)
/*     */   {
/*  73 */     Objects.requireNonNull(paramConsumer);
/*  74 */     for (Object localObject : this) {
/*  75 */       paramConsumer.accept(localObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Spliterator<T> spliterator()
/*     */   {
/* 101 */     return Spliterators.spliteratorUnknownSize(iterator(), 0);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Iterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */