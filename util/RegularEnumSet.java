/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RegularEnumSet<E extends Enum<E>>
/*     */   extends EnumSet<E>
/*     */ {
/*     */   private static final long serialVersionUID = 3411599620347842686L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  42 */   private long elements = 0L;
/*     */   
/*     */   RegularEnumSet(Class<E> paramClass, Enum<?>[] paramArrayOfEnum) {
/*  45 */     super(paramClass, paramArrayOfEnum);
/*     */   }
/*     */   
/*     */   void addRange(E paramE1, E paramE2) {
/*  49 */     this.elements = (-1L >>> paramE1.ordinal() - paramE2.ordinal() - 1 << paramE1.ordinal());
/*     */   }
/*     */   
/*     */   void addAll() {
/*  53 */     if (this.universe.length != 0)
/*  54 */       this.elements = (-1L >>> -this.universe.length);
/*     */   }
/*     */   
/*     */   void complement() {
/*  58 */     if (this.universe.length != 0) {
/*  59 */       this.elements ^= 0xFFFFFFFFFFFFFFFF;
/*  60 */       this.elements &= -1L >>> -this.universe.length;
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
/*     */   public Iterator<E> iterator()
/*     */   {
/*  75 */     return new EnumSetIterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private class EnumSetIterator<E extends Enum<E>>
/*     */     implements Iterator<E>
/*     */   {
/*     */     long unseen;
/*     */     
/*     */ 
/*     */ 
/*  89 */     long lastReturned = 0L;
/*     */     
/*     */     EnumSetIterator() {
/*  92 */       this.unseen = RegularEnumSet.this.elements;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/*  96 */       return this.unseen != 0L;
/*     */     }
/*     */     
/*     */     public E next()
/*     */     {
/* 101 */       if (this.unseen == 0L)
/* 102 */         throw new NoSuchElementException();
/* 103 */       this.lastReturned = (this.unseen & -this.unseen);
/* 104 */       this.unseen -= this.lastReturned;
/* 105 */       return RegularEnumSet.this.universe[Long.numberOfTrailingZeros(this.lastReturned)];
/*     */     }
/*     */     
/*     */     public void remove() {
/* 109 */       if (this.lastReturned == 0L)
/* 110 */         throw new IllegalStateException();
/* 111 */       RegularEnumSet.this.elements = (RegularEnumSet.this.elements & (this.lastReturned ^ 0xFFFFFFFFFFFFFFFF));
/* 112 */       this.lastReturned = 0L;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 122 */     return Long.bitCount(this.elements);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 131 */     return this.elements == 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 141 */     if (paramObject == null)
/* 142 */       return false;
/* 143 */     Class localClass = paramObject.getClass();
/* 144 */     if ((localClass != this.elementType) && (localClass.getSuperclass() != this.elementType)) {
/* 145 */       return false;
/*     */     }
/* 147 */     return (this.elements & 1L << ((Enum)paramObject).ordinal()) != 0L;
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 161 */     typeCheck(paramE);
/*     */     
/* 163 */     long l = this.elements;
/* 164 */     this.elements |= 1L << paramE.ordinal();
/* 165 */     return this.elements != l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 175 */     if (paramObject == null)
/* 176 */       return false;
/* 177 */     Class localClass = paramObject.getClass();
/* 178 */     if ((localClass != this.elementType) && (localClass.getSuperclass() != this.elementType)) {
/* 179 */       return false;
/*     */     }
/* 181 */     long l = this.elements;
/* 182 */     this.elements &= (1L << ((Enum)paramObject).ordinal() ^ 0xFFFFFFFFFFFFFFFF);
/* 183 */     return this.elements != l;
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
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 198 */     if (!(paramCollection instanceof RegularEnumSet)) {
/* 199 */       return super.containsAll(paramCollection);
/*     */     }
/* 201 */     RegularEnumSet localRegularEnumSet = (RegularEnumSet)paramCollection;
/* 202 */     if (localRegularEnumSet.elementType != this.elementType) {
/* 203 */       return localRegularEnumSet.isEmpty();
/*     */     }
/* 205 */     return (localRegularEnumSet.elements & (this.elements ^ 0xFFFFFFFFFFFFFFFF)) == 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 217 */     if (!(paramCollection instanceof RegularEnumSet)) {
/* 218 */       return super.addAll(paramCollection);
/*     */     }
/* 220 */     RegularEnumSet localRegularEnumSet = (RegularEnumSet)paramCollection;
/* 221 */     if (localRegularEnumSet.elementType != this.elementType) {
/* 222 */       if (localRegularEnumSet.isEmpty()) {
/* 223 */         return false;
/*     */       }
/* 225 */       throw new ClassCastException(localRegularEnumSet.elementType + " != " + this.elementType);
/*     */     }
/*     */     
/*     */ 
/* 229 */     long l = this.elements;
/* 230 */     this.elements |= localRegularEnumSet.elements;
/* 231 */     return this.elements != l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 243 */     if (!(paramCollection instanceof RegularEnumSet)) {
/* 244 */       return super.removeAll(paramCollection);
/*     */     }
/* 246 */     RegularEnumSet localRegularEnumSet = (RegularEnumSet)paramCollection;
/* 247 */     if (localRegularEnumSet.elementType != this.elementType) {
/* 248 */       return false;
/*     */     }
/* 250 */     long l = this.elements;
/* 251 */     this.elements &= (localRegularEnumSet.elements ^ 0xFFFFFFFFFFFFFFFF);
/* 252 */     return this.elements != l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 264 */     if (!(paramCollection instanceof RegularEnumSet)) {
/* 265 */       return super.retainAll(paramCollection);
/*     */     }
/* 267 */     RegularEnumSet localRegularEnumSet = (RegularEnumSet)paramCollection;
/* 268 */     if (localRegularEnumSet.elementType != this.elementType) {
/* 269 */       boolean bool = this.elements != 0L;
/* 270 */       this.elements = 0L;
/* 271 */       return bool;
/*     */     }
/*     */     
/* 274 */     long l = this.elements;
/* 275 */     this.elements &= localRegularEnumSet.elements;
/* 276 */     return this.elements != l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 283 */     this.elements = 0L;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 296 */     if (!(paramObject instanceof RegularEnumSet)) {
/* 297 */       return super.equals(paramObject);
/*     */     }
/* 299 */     RegularEnumSet localRegularEnumSet = (RegularEnumSet)paramObject;
/* 300 */     if (localRegularEnumSet.elementType != this.elementType)
/* 301 */       return (this.elements == 0L) && (localRegularEnumSet.elements == 0L);
/* 302 */     return localRegularEnumSet.elements == this.elements;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/RegularEnumSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */