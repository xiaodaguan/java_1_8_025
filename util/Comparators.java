/*    */ package java.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Comparators
/*    */ {
/*    */   private Comparators()
/*    */   {
/* 39 */     throw new AssertionError("no instances");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   static enum NaturalOrderComparator
/*    */     implements Comparator<Comparable<Object>>
/*    */   {
/* 48 */     INSTANCE;
/*    */     
/*    */     private NaturalOrderComparator() {}
/*    */     
/* 52 */     public int compare(Comparable<Object> paramComparable1, Comparable<Object> paramComparable2) { return paramComparable1.compareTo(paramComparable2); }
/*    */     
/*    */ 
/*    */     public Comparator<Comparable<Object>> reversed()
/*    */     {
/* 57 */       return Comparator.reverseOrder();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   static final class NullComparator<T>
/*    */     implements Comparator<T>, Serializable
/*    */   {
/*    */     private static final long serialVersionUID = -7569533591570686392L;
/*    */     
/*    */     private final boolean nullFirst;
/*    */     private final Comparator<T> real;
/*    */     
/*    */     NullComparator(boolean paramBoolean, Comparator<? super T> paramComparator)
/*    */     {
/* 72 */       this.nullFirst = paramBoolean;
/* 73 */       this.real = paramComparator;
/*    */     }
/*    */     
/*    */     public int compare(T paramT1, T paramT2)
/*    */     {
/* 78 */       if (paramT1 == null)
/* 79 */         return this.nullFirst ? -1 : paramT2 == null ? 0 : 1;
/* 80 */       if (paramT2 == null) {
/* 81 */         return this.nullFirst ? 1 : -1;
/*    */       }
/* 83 */       return this.real == null ? 0 : this.real.compare(paramT1, paramT2);
/*    */     }
/*    */     
/*    */ 
/*    */     public Comparator<T> thenComparing(Comparator<? super T> paramComparator)
/*    */     {
/* 89 */       Objects.requireNonNull(paramComparator);
/* 90 */       return new NullComparator(this.nullFirst, this.real == null ? paramComparator : this.real.thenComparing(paramComparator));
/*    */     }
/*    */     
/*    */     public Comparator<T> reversed()
/*    */     {
/* 95 */       return new NullComparator(!this.nullFirst, this.real == null ? null : this.real.reversed());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Comparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */