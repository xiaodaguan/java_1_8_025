/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.UnaryOperator;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AtomicReferenceFieldUpdater<T, V>
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <U, W> AtomicReferenceFieldUpdater<U, W> newUpdater(Class<U> paramClass, Class<W> paramClass1, String paramString)
/*     */   {
/* 109 */     return new AtomicReferenceFieldUpdaterImpl(paramClass, paramClass1, paramString, Reflection.getCallerClass());
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
/*     */   public abstract boolean compareAndSet(T paramT, V paramV1, V paramV2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean weakCompareAndSet(T paramT, V paramV1, V paramV2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void set(T paramT, V paramV);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void lazySet(T paramT, V paramV);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract V get(T paramT);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V getAndSet(T paramT, V paramV)
/*     */   {
/*     */     Object localObject;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 190 */       localObject = get(paramT);
/* 191 */     } while (!compareAndSet(paramT, localObject, paramV));
/* 192 */     return (V)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V getAndUpdate(T paramT, UnaryOperator<V> paramUnaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 209 */       localObject1 = get(paramT);
/* 210 */       localObject2 = paramUnaryOperator.apply(localObject1);
/* 211 */     } while (!compareAndSet(paramT, localObject1, localObject2));
/* 212 */     return (V)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V updateAndGet(T paramT, UnaryOperator<V> paramUnaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 229 */       localObject1 = get(paramT);
/* 230 */       localObject2 = paramUnaryOperator.apply(localObject1);
/* 231 */     } while (!compareAndSet(paramT, localObject1, localObject2));
/* 232 */     return (V)localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V getAndAccumulate(T paramT, V paramV, BinaryOperator<V> paramBinaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 254 */       localObject1 = get(paramT);
/* 255 */       localObject2 = paramBinaryOperator.apply(localObject1, paramV);
/* 256 */     } while (!compareAndSet(paramT, localObject1, localObject2));
/* 257 */     return (V)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V accumulateAndGet(T paramT, V paramV, BinaryOperator<V> paramBinaryOperator)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 279 */       localObject1 = get(paramT);
/* 280 */       localObject2 = paramBinaryOperator.apply(localObject1, paramV);
/* 281 */     } while (!compareAndSet(paramT, localObject1, localObject2));
/* 282 */     return (V)localObject2;
/*     */   }
/*     */   
/*     */   private static final class AtomicReferenceFieldUpdaterImpl<T, V> extends AtomicReferenceFieldUpdater<T, V>
/*     */   {
/* 287 */     private static final Unsafe unsafe = ;
/*     */     
/*     */ 
/*     */     private final long offset;
/*     */     
/*     */ 
/*     */     private final Class<T> tclass;
/*     */     
/*     */ 
/*     */     private final Class<V> vclass;
/*     */     
/*     */ 
/*     */     private final Class<?> cclass;
/*     */     
/*     */ 
/*     */ 
/*     */     AtomicReferenceFieldUpdaterImpl(final Class<T> paramClass, Class<V> paramClass1, final String paramString, Class<?> paramClass2)
/*     */     {
/*     */       Field localField;
/*     */       
/*     */       int i;
/*     */       
/*     */       Class localClass;
/*     */       
/*     */       try
/*     */       {
/* 313 */         localField = (Field)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Field run() throws NoSuchFieldException {
/* 316 */             return paramClass.getDeclaredField(paramString);
/*     */           }
/* 318 */         });
/* 319 */         i = localField.getModifiers();
/* 320 */         ReflectUtil.ensureMemberAccess(paramClass2, paramClass, null, i);
/*     */         
/* 322 */         ClassLoader localClassLoader1 = paramClass.getClassLoader();
/* 323 */         ClassLoader localClassLoader2 = paramClass2.getClassLoader();
/* 324 */         if ((localClassLoader2 != null) && (localClassLoader2 != localClassLoader1) && ((localClassLoader1 == null) || 
/* 325 */           (!isAncestor(localClassLoader1, localClassLoader2)))) {
/* 326 */           ReflectUtil.checkPackageAccess(paramClass);
/*     */         }
/* 328 */         localClass = localField.getType();
/*     */       } catch (PrivilegedActionException localPrivilegedActionException) {
/* 330 */         throw new RuntimeException(localPrivilegedActionException.getException());
/*     */       } catch (Exception localException) {
/* 332 */         throw new RuntimeException(localException);
/*     */       }
/*     */       
/* 335 */       if (paramClass1 != localClass)
/* 336 */         throw new ClassCastException();
/* 337 */       if (paramClass1.isPrimitive()) {
/* 338 */         throw new IllegalArgumentException("Must be reference type");
/*     */       }
/* 340 */       if (!Modifier.isVolatile(i)) {
/* 341 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/* 343 */       this.cclass = ((Modifier.isProtected(i)) && (paramClass2 != paramClass) ? paramClass2 : null);
/*     */       
/* 345 */       this.tclass = paramClass;
/* 346 */       if (paramClass1 == Object.class) {
/* 347 */         this.vclass = null;
/*     */       } else
/* 349 */         this.vclass = paramClass1;
/* 350 */       this.offset = unsafe.objectFieldOffset(localField);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private static boolean isAncestor(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2)
/*     */     {
/* 359 */       ClassLoader localClassLoader = paramClassLoader1;
/*     */       do {
/* 361 */         localClassLoader = localClassLoader.getParent();
/* 362 */         if (paramClassLoader2 == localClassLoader) {
/* 363 */           return true;
/*     */         }
/* 365 */       } while (localClassLoader != null);
/* 366 */       return false;
/*     */     }
/*     */     
/*     */     void targetCheck(T paramT) {
/* 370 */       if (!this.tclass.isInstance(paramT))
/* 371 */         throw new ClassCastException();
/* 372 */       if (this.cclass != null)
/* 373 */         ensureProtectedAccess(paramT);
/*     */     }
/*     */     
/*     */     void updateCheck(T paramT, V paramV) {
/* 377 */       if ((!this.tclass.isInstance(paramT)) || ((paramV != null) && (this.vclass != null) && 
/* 378 */         (!this.vclass.isInstance(paramV))))
/* 379 */         throw new ClassCastException();
/* 380 */       if (this.cclass != null)
/* 381 */         ensureProtectedAccess(paramT);
/*     */     }
/*     */     
/*     */     public boolean compareAndSet(T paramT, V paramV1, V paramV2) {
/* 385 */       if ((paramT != null) && (paramT.getClass() == this.tclass) && (this.cclass == null)) { if ((paramV2 != null) && (this.vclass != null))
/*     */         {
/* 387 */           if (this.vclass == paramV2.getClass()) {} }
/* 388 */       } else updateCheck(paramT, paramV2);
/* 389 */       return unsafe.compareAndSwapObject(paramT, this.offset, paramV1, paramV2);
/*     */     }
/*     */     
/*     */     public boolean weakCompareAndSet(T paramT, V paramV1, V paramV2)
/*     */     {
/* 394 */       if ((paramT != null) && (paramT.getClass() == this.tclass) && (this.cclass == null)) { if ((paramV2 != null) && (this.vclass != null))
/*     */         {
/* 396 */           if (this.vclass == paramV2.getClass()) {} }
/* 397 */       } else updateCheck(paramT, paramV2);
/* 398 */       return unsafe.compareAndSwapObject(paramT, this.offset, paramV1, paramV2);
/*     */     }
/*     */     
/*     */     public void set(T paramT, V paramV) {
/* 402 */       if ((paramT != null) && (paramT.getClass() == this.tclass) && (this.cclass == null)) { if ((paramV != null) && (this.vclass != null))
/*     */         {
/* 404 */           if (this.vclass == paramV.getClass()) {} }
/* 405 */       } else updateCheck(paramT, paramV);
/* 406 */       unsafe.putObjectVolatile(paramT, this.offset, paramV);
/*     */     }
/*     */     
/*     */     public void lazySet(T paramT, V paramV) {
/* 410 */       if ((paramT != null) && (paramT.getClass() == this.tclass) && (this.cclass == null)) { if ((paramV != null) && (this.vclass != null))
/*     */         {
/* 412 */           if (this.vclass == paramV.getClass()) {} }
/* 413 */       } else updateCheck(paramT, paramV);
/* 414 */       unsafe.putOrderedObject(paramT, this.offset, paramV);
/*     */     }
/*     */     
/*     */     public V get(T paramT)
/*     */     {
/* 419 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null))
/* 420 */         targetCheck(paramT);
/* 421 */       return (V)unsafe.getObjectVolatile(paramT, this.offset);
/*     */     }
/*     */     
/*     */     public V getAndSet(T paramT, V paramV)
/*     */     {
/* 426 */       if ((paramT != null) && (paramT.getClass() == this.tclass) && (this.cclass == null)) { if ((paramV != null) && (this.vclass != null))
/*     */         {
/* 428 */           if (this.vclass == paramV.getClass()) {} }
/* 429 */       } else updateCheck(paramT, paramV);
/* 430 */       return (V)unsafe.getAndSetObject(paramT, this.offset, paramV);
/*     */     }
/*     */     
/*     */     private void ensureProtectedAccess(T paramT) {
/* 434 */       if (this.cclass.isInstance(paramT)) {
/* 435 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 443 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass.getName() + " can not access a protected member of class " + this.tclass.getName() + " using an instance of " + paramT.getClass().getName()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicReferenceFieldUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */