/*     */ package java.util;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EnumSet<E extends Enum<E>>
/*     */   extends AbstractSet<E>
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   final Class<E> elementType;
/*     */   final Enum<?>[] universe;
/*  93 */   private static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum[0];
/*     */   
/*     */   EnumSet(Class<E> paramClass, Enum<?>[] paramArrayOfEnum) {
/*  96 */     this.elementType = paramClass;
/*  97 */     this.universe = paramArrayOfEnum;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> paramClass)
/*     */   {
/* 110 */     Enum[] arrayOfEnum = getUniverse(paramClass);
/* 111 */     if (arrayOfEnum == null) {
/* 112 */       throw new ClassCastException(paramClass + " not an enum");
/*     */     }
/* 114 */     if (arrayOfEnum.length <= 64) {
/* 115 */       return new RegularEnumSet(paramClass, arrayOfEnum);
/*     */     }
/* 117 */     return new JumboEnumSet(paramClass, arrayOfEnum);
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
/*     */   public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> paramClass)
/*     */   {
/* 131 */     EnumSet localEnumSet = noneOf(paramClass);
/* 132 */     localEnumSet.addAll();
/* 133 */     return localEnumSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   abstract void addAll();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> paramEnumSet)
/*     */   {
/* 152 */     return paramEnumSet.clone();
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
/*     */   public static <E extends Enum<E>> EnumSet<E> copyOf(Collection<E> paramCollection)
/*     */   {
/* 170 */     if ((paramCollection instanceof EnumSet)) {
/* 171 */       return ((EnumSet)paramCollection).clone();
/*     */     }
/* 173 */     if (paramCollection.isEmpty())
/* 174 */       throw new IllegalArgumentException("Collection is empty");
/* 175 */     Iterator localIterator = paramCollection.iterator();
/* 176 */     Enum localEnum = (Enum)localIterator.next();
/* 177 */     EnumSet localEnumSet = of(localEnum);
/* 178 */     while (localIterator.hasNext())
/* 179 */       localEnumSet.add(localIterator.next());
/* 180 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> paramEnumSet)
/*     */   {
/* 195 */     EnumSet localEnumSet = copyOf(paramEnumSet);
/* 196 */     localEnumSet.complement();
/* 197 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE)
/*     */   {
/* 215 */     EnumSet localEnumSet = noneOf(paramE.getDeclaringClass());
/* 216 */     localEnumSet.add(paramE);
/* 217 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2)
/*     */   {
/* 236 */     EnumSet localEnumSet = noneOf(paramE1.getDeclaringClass());
/* 237 */     localEnumSet.add(paramE1);
/* 238 */     localEnumSet.add(paramE2);
/* 239 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2, E paramE3)
/*     */   {
/* 259 */     EnumSet localEnumSet = noneOf(paramE1.getDeclaringClass());
/* 260 */     localEnumSet.add(paramE1);
/* 261 */     localEnumSet.add(paramE2);
/* 262 */     localEnumSet.add(paramE3);
/* 263 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2, E paramE3, E paramE4)
/*     */   {
/* 284 */     EnumSet localEnumSet = noneOf(paramE1.getDeclaringClass());
/* 285 */     localEnumSet.add(paramE1);
/* 286 */     localEnumSet.add(paramE2);
/* 287 */     localEnumSet.add(paramE3);
/* 288 */     localEnumSet.add(paramE4);
/* 289 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2, E paramE3, E paramE4, E paramE5)
/*     */   {
/* 313 */     EnumSet localEnumSet = noneOf(paramE1.getDeclaringClass());
/* 314 */     localEnumSet.add(paramE1);
/* 315 */     localEnumSet.add(paramE2);
/* 316 */     localEnumSet.add(paramE3);
/* 317 */     localEnumSet.add(paramE4);
/* 318 */     localEnumSet.add(paramE5);
/* 319 */     return localEnumSet;
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
/*     */   @SafeVarargs
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE, E... paramVarArgs)
/*     */   {
/* 338 */     EnumSet localEnumSet = noneOf(paramE.getDeclaringClass());
/* 339 */     localEnumSet.add(paramE);
/* 340 */     for (E ? : paramVarArgs)
/* 341 */       localEnumSet.add(?);
/* 342 */     return localEnumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> range(E paramE1, E paramE2)
/*     */   {
/* 360 */     if (paramE1.compareTo(paramE2) > 0)
/* 361 */       throw new IllegalArgumentException(paramE1 + " > " + paramE2);
/* 362 */     EnumSet localEnumSet = noneOf(paramE1.getDeclaringClass());
/* 363 */     localEnumSet.addRange(paramE1, paramE2);
/* 364 */     return localEnumSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   abstract void addRange(E paramE1, E paramE2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumSet<E> clone()
/*     */   {
/*     */     try
/*     */     {
/* 381 */       return (EnumSet)super.clone();
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 383 */       throw new AssertionError(localCloneNotSupportedException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   abstract void complement();
/*     */   
/*     */ 
/*     */ 
/*     */   final void typeCheck(E paramE)
/*     */   {
/* 396 */     Class localClass = paramE.getClass();
/* 397 */     if ((localClass != this.elementType) && (localClass.getSuperclass() != this.elementType)) {
/* 398 */       throw new ClassCastException(localClass + " != " + this.elementType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <E extends Enum<E>> E[] getUniverse(Class<E> paramClass)
/*     */   {
/* 407 */     return SharedSecrets.getJavaLangAccess().getEnumConstantsShared(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class SerializationProxy<E extends Enum<E>>
/*     */     implements Serializable
/*     */   {
/*     */     private final Class<E> elementType;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final Enum<?>[] elements;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private static final long serialVersionUID = 362491234563181265L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     SerializationProxy(EnumSet<E> paramEnumSet)
/*     */     {
/* 437 */       this.elementType = paramEnumSet.elementType;
/* 438 */       this.elements = ((Enum[])paramEnumSet.toArray(EnumSet.ZERO_LENGTH_ENUM_ARRAY));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private Object readResolve()
/*     */     {
/* 445 */       EnumSet localEnumSet = EnumSet.noneOf(this.elementType);
/* 446 */       for (Enum localEnum : this.elements)
/* 447 */         localEnumSet.add(localEnum);
/* 448 */       return localEnumSet;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   Object writeReplace()
/*     */   {
/* 455 */     return new SerializationProxy(this);
/*     */   }
/*     */   
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 462 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/EnumSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */