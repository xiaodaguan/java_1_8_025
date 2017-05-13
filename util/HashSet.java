/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashSet<E>
/*     */   extends AbstractSet<E>
/*     */   implements Set<E>, Cloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = -5024744406713321676L;
/*     */   private transient HashMap<E, Object> map;
/*  98 */   private static final Object PRESENT = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HashSet()
/*     */   {
/* 105 */     this.map = new HashMap();
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
/*     */   public HashSet(Collection<? extends E> paramCollection)
/*     */   {
/* 118 */     this.map = new HashMap(Math.max((int)(paramCollection.size() / 0.75F) + 1, 16));
/* 119 */     addAll(paramCollection);
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
/*     */   public HashSet(int paramInt, float paramFloat)
/*     */   {
/* 132 */     this.map = new HashMap(paramInt, paramFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HashSet(int paramInt)
/*     */   {
/* 144 */     this.map = new HashMap(paramInt);
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
/*     */   HashSet(int paramInt, float paramFloat, boolean paramBoolean)
/*     */   {
/* 161 */     this.map = new LinkedHashMap(paramInt, paramFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 172 */     return this.map.keySet().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 181 */     return this.map.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 190 */     return this.map.isEmpty();
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
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 203 */     return this.map.containsKey(paramObject);
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
/*     */   public boolean add(E paramE)
/*     */   {
/* 219 */     return this.map.put(paramE, PRESENT) == null;
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
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 235 */     return this.map.remove(paramObject) == PRESENT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 243 */     this.map.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 255 */       HashSet localHashSet = (HashSet)super.clone();
/* 256 */       localHashSet.map = ((HashMap)this.map.clone());
/* 257 */       return localHashSet;
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 259 */       throw new InternalError(localCloneNotSupportedException);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 276 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 279 */     paramObjectOutputStream.writeInt(this.map.capacity());
/* 280 */     paramObjectOutputStream.writeFloat(this.map.loadFactor());
/*     */     
/*     */ 
/* 283 */     paramObjectOutputStream.writeInt(this.map.size());
/*     */     
/*     */ 
/* 286 */     for (Object localObject : this.map.keySet()) {
/* 287 */       paramObjectOutputStream.writeObject(localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 297 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 300 */     int i = paramObjectInputStream.readInt();
/* 301 */     if (i < 0) {
/* 302 */       throw new InvalidObjectException("Illegal capacity: " + i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 307 */     float f = paramObjectInputStream.readFloat();
/* 308 */     if ((f <= 0.0F) || (Float.isNaN(f))) {
/* 309 */       throw new InvalidObjectException("Illegal load factor: " + f);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 314 */     int j = paramObjectInputStream.readInt();
/* 315 */     if (j < 0) {
/* 316 */       throw new InvalidObjectException("Illegal size: " + j);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 322 */     i = (int)Math.min(j * Math.min(1.0F / f, 4.0F), 1.07374182E9F);
/*     */     
/*     */ 
/*     */ 
/* 326 */     this.map = ((this instanceof LinkedHashSet) ? new LinkedHashMap(i, f) : new HashMap(i, f));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 331 */     for (int k = 0; k < j; k++)
/*     */     {
/* 333 */       Object localObject = paramObjectInputStream.readObject();
/* 334 */       this.map.put(localObject, PRESENT);
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
/*     */   public Spliterator<E> spliterator()
/*     */   {
/* 351 */     return new HashMap.KeySpliterator(this.map, 0, -1, 0, 0);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/HashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */