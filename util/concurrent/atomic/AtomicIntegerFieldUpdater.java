/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntUnaryOperator;
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
/*     */ public abstract class AtomicIntegerFieldUpdater<T>
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <U> AtomicIntegerFieldUpdater<U> newUpdater(Class<U> paramClass, String paramString)
/*     */   {
/*  87 */     return new AtomicIntegerFieldUpdaterImpl(paramClass, paramString, Reflection.getCallerClass());
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
/*     */   public abstract boolean compareAndSet(T paramT, int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean weakCompareAndSet(T paramT, int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void set(T paramT, int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void lazySet(T paramT, int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int get(T paramT);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAndSet(T paramT, int paramInt)
/*     */   {
/*     */     int i;
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
/* 172 */       i = get(paramT);
/* 173 */     } while (!compareAndSet(paramT, i, paramInt));
/* 174 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAndIncrement(T paramT)
/*     */   {
/*     */     int i;
/*     */     
/*     */     int j;
/*     */     
/*     */     do
/*     */     {
/* 187 */       i = get(paramT);
/* 188 */       j = i + 1;
/* 189 */     } while (!compareAndSet(paramT, i, j));
/* 190 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAndDecrement(T paramT)
/*     */   {
/*     */     int i;
/*     */     
/*     */     int j;
/*     */     
/*     */     do
/*     */     {
/* 203 */       i = get(paramT);
/* 204 */       j = i - 1;
/* 205 */     } while (!compareAndSet(paramT, i, j));
/* 206 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAndAdd(T paramT, int paramInt)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     int j;
/*     */     
/*     */     do
/*     */     {
/* 220 */       i = get(paramT);
/* 221 */       j = i + paramInt;
/* 222 */     } while (!compareAndSet(paramT, i, j));
/* 223 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int incrementAndGet(T paramT)
/*     */   {
/*     */     int i;
/*     */     
/*     */     int j;
/*     */     
/*     */     do
/*     */     {
/* 236 */       i = get(paramT);
/* 237 */       j = i + 1;
/* 238 */     } while (!compareAndSet(paramT, i, j));
/* 239 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int decrementAndGet(T paramT)
/*     */   {
/*     */     int i;
/*     */     
/*     */     int j;
/*     */     
/*     */     do
/*     */     {
/* 252 */       i = get(paramT);
/* 253 */       j = i - 1;
/* 254 */     } while (!compareAndSet(paramT, i, j));
/* 255 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int addAndGet(T paramT, int paramInt)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     int j;
/*     */     
/*     */     do
/*     */     {
/* 269 */       i = get(paramT);
/* 270 */       j = i + paramInt;
/* 271 */     } while (!compareAndSet(paramT, i, j));
/* 272 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndUpdate(T paramT, IntUnaryOperator paramIntUnaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 289 */       i = get(paramT);
/* 290 */       j = paramIntUnaryOperator.applyAsInt(i);
/* 291 */     } while (!compareAndSet(paramT, i, j));
/* 292 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int updateAndGet(T paramT, IntUnaryOperator paramIntUnaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 309 */       i = get(paramT);
/* 310 */       j = paramIntUnaryOperator.applyAsInt(i);
/* 311 */     } while (!compareAndSet(paramT, i, j));
/* 312 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getAndAccumulate(T paramT, int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 334 */       i = get(paramT);
/* 335 */       j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 336 */     } while (!compareAndSet(paramT, i, j));
/* 337 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int accumulateAndGet(T paramT, int paramInt, IntBinaryOperator paramIntBinaryOperator)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 359 */       i = get(paramT);
/* 360 */       j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 361 */     } while (!compareAndSet(paramT, i, j));
/* 362 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class AtomicIntegerFieldUpdaterImpl<T>
/*     */     extends AtomicIntegerFieldUpdater<T>
/*     */   {
/* 370 */     private static final Unsafe unsafe = ;
/*     */     private final long offset;
/*     */     private final Class<T> tclass;
/*     */     private final Class<?> cclass;
/*     */     
/*     */     AtomicIntegerFieldUpdaterImpl(final Class<T> paramClass, final String paramString, Class<?> paramClass1)
/*     */     {
/*     */       Field localField;
/*     */       int i;
/*     */       try
/*     */       {
/* 381 */         localField = (Field)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Field run() throws NoSuchFieldException {
/* 384 */             return paramClass.getDeclaredField(paramString);
/*     */           }
/* 386 */         });
/* 387 */         i = localField.getModifiers();
/* 388 */         ReflectUtil.ensureMemberAccess(paramClass1, paramClass, null, i);
/*     */         
/* 390 */         ClassLoader localClassLoader1 = paramClass.getClassLoader();
/* 391 */         ClassLoader localClassLoader2 = paramClass1.getClassLoader();
/* 392 */         if ((localClassLoader2 != null) && (localClassLoader2 != localClassLoader1) && ((localClassLoader1 == null) || 
/* 393 */           (!isAncestor(localClassLoader1, localClassLoader2)))) {
/* 394 */           ReflectUtil.checkPackageAccess(paramClass);
/*     */         }
/*     */       } catch (PrivilegedActionException localPrivilegedActionException) {
/* 397 */         throw new RuntimeException(localPrivilegedActionException.getException());
/*     */       } catch (Exception localException) {
/* 399 */         throw new RuntimeException(localException);
/*     */       }
/*     */       
/* 402 */       Class localClass = localField.getType();
/* 403 */       if (localClass != Integer.TYPE) {
/* 404 */         throw new IllegalArgumentException("Must be integer type");
/*     */       }
/* 406 */       if (!Modifier.isVolatile(i)) {
/* 407 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/* 409 */       this.cclass = ((Modifier.isProtected(i)) && (paramClass1 != paramClass) ? paramClass1 : null);
/*     */       
/* 411 */       this.tclass = paramClass;
/* 412 */       this.offset = unsafe.objectFieldOffset(localField);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private static boolean isAncestor(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2)
/*     */     {
/* 421 */       ClassLoader localClassLoader = paramClassLoader1;
/*     */       do {
/* 423 */         localClassLoader = localClassLoader.getParent();
/* 424 */         if (paramClassLoader2 == localClassLoader) {
/* 425 */           return true;
/*     */         }
/* 427 */       } while (localClassLoader != null);
/* 428 */       return false;
/*     */     }
/*     */     
/*     */     private void fullCheck(T paramT) {
/* 432 */       if (!this.tclass.isInstance(paramT))
/* 433 */         throw new ClassCastException();
/* 434 */       if (this.cclass != null)
/* 435 */         ensureProtectedAccess(paramT);
/*     */     }
/*     */     
/*     */     public boolean compareAndSet(T paramT, int paramInt1, int paramInt2) {
/* 439 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 440 */       return unsafe.compareAndSwapInt(paramT, this.offset, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */     public boolean weakCompareAndSet(T paramT, int paramInt1, int paramInt2) {
/* 444 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 445 */       return unsafe.compareAndSwapInt(paramT, this.offset, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */     public void set(T paramT, int paramInt) {
/* 449 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 450 */       unsafe.putIntVolatile(paramT, this.offset, paramInt);
/*     */     }
/*     */     
/*     */     public void lazySet(T paramT, int paramInt) {
/* 454 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 455 */       unsafe.putOrderedInt(paramT, this.offset, paramInt);
/*     */     }
/*     */     
/*     */     public final int get(T paramT) {
/* 459 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 460 */       return unsafe.getIntVolatile(paramT, this.offset);
/*     */     }
/*     */     
/*     */     public int getAndSet(T paramT, int paramInt) {
/* 464 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 465 */       return unsafe.getAndSetInt(paramT, this.offset, paramInt);
/*     */     }
/*     */     
/*     */     public int getAndIncrement(T paramT) {
/* 469 */       return getAndAdd(paramT, 1);
/*     */     }
/*     */     
/*     */     public int getAndDecrement(T paramT) {
/* 473 */       return getAndAdd(paramT, -1);
/*     */     }
/*     */     
/*     */     public int getAndAdd(T paramT, int paramInt) {
/* 477 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 478 */       return unsafe.getAndAddInt(paramT, this.offset, paramInt);
/*     */     }
/*     */     
/*     */     public int incrementAndGet(T paramT) {
/* 482 */       return getAndAdd(paramT, 1) + 1;
/*     */     }
/*     */     
/*     */     public int decrementAndGet(T paramT) {
/* 486 */       return getAndAdd(paramT, -1) - 1;
/*     */     }
/*     */     
/*     */     public int addAndGet(T paramT, int paramInt) {
/* 490 */       return getAndAdd(paramT, paramInt) + paramInt;
/*     */     }
/*     */     
/*     */     private void ensureProtectedAccess(T paramT) {
/* 494 */       if (this.cclass.isInstance(paramT)) {
/* 495 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 503 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass.getName() + " can not access a protected member of class " + this.tclass.getName() + " using an instance of " + paramT.getClass().getName()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicIntegerFieldUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */