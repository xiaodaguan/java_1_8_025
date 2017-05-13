/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongUnaryOperator;
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
/*     */ public abstract class AtomicLongFieldUpdater<T>
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <U> AtomicLongFieldUpdater<U> newUpdater(Class<U> paramClass, String paramString)
/*     */   {
/*  86 */     Class localClass = Reflection.getCallerClass();
/*  87 */     if (AtomicLong.VM_SUPPORTS_LONG_CAS) {
/*  88 */       return new CASUpdater(paramClass, paramString, localClass);
/*     */     }
/*  90 */     return new LockedUpdater(paramClass, paramString, localClass);
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
/*     */   public abstract boolean compareAndSet(T paramT, long paramLong1, long paramLong2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean weakCompareAndSet(T paramT, long paramLong1, long paramLong2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void set(T paramT, long paramLong);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void lazySet(T paramT, long paramLong);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract long get(T paramT);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getAndSet(T paramT, long paramLong)
/*     */   {
/*     */     long l;
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
/* 175 */       l = get(paramT);
/* 176 */     } while (!compareAndSet(paramT, l, paramLong));
/* 177 */     return l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getAndIncrement(T paramT)
/*     */   {
/*     */     long l1;
/*     */     
/*     */     long l2;
/*     */     
/*     */     do
/*     */     {
/* 190 */       l1 = get(paramT);
/* 191 */       l2 = l1 + 1L;
/* 192 */     } while (!compareAndSet(paramT, l1, l2));
/* 193 */     return l1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getAndDecrement(T paramT)
/*     */   {
/*     */     long l1;
/*     */     
/*     */     long l2;
/*     */     
/*     */     do
/*     */     {
/* 206 */       l1 = get(paramT);
/* 207 */       l2 = l1 - 1L;
/* 208 */     } while (!compareAndSet(paramT, l1, l2));
/* 209 */     return l1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getAndAdd(T paramT, long paramLong)
/*     */   {
/*     */     long l1;
/*     */     
/*     */ 
/*     */     long l2;
/*     */     
/*     */     do
/*     */     {
/* 223 */       l1 = get(paramT);
/* 224 */       l2 = l1 + paramLong;
/* 225 */     } while (!compareAndSet(paramT, l1, l2));
/* 226 */     return l1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long incrementAndGet(T paramT)
/*     */   {
/*     */     long l1;
/*     */     
/*     */     long l2;
/*     */     
/*     */     do
/*     */     {
/* 239 */       l1 = get(paramT);
/* 240 */       l2 = l1 + 1L;
/* 241 */     } while (!compareAndSet(paramT, l1, l2));
/* 242 */     return l2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long decrementAndGet(T paramT)
/*     */   {
/*     */     long l1;
/*     */     
/*     */     long l2;
/*     */     
/*     */     do
/*     */     {
/* 255 */       l1 = get(paramT);
/* 256 */       l2 = l1 - 1L;
/* 257 */     } while (!compareAndSet(paramT, l1, l2));
/* 258 */     return l2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long addAndGet(T paramT, long paramLong)
/*     */   {
/*     */     long l1;
/*     */     
/*     */ 
/*     */     long l2;
/*     */     
/*     */     do
/*     */     {
/* 272 */       l1 = get(paramT);
/* 273 */       l2 = l1 + paramLong;
/* 274 */     } while (!compareAndSet(paramT, l1, l2));
/* 275 */     return l2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getAndUpdate(T paramT, LongUnaryOperator paramLongUnaryOperator)
/*     */   {
/*     */     long l1;
/*     */     
/*     */ 
/*     */ 
/*     */     long l2;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 292 */       l1 = get(paramT);
/* 293 */       l2 = paramLongUnaryOperator.applyAsLong(l1);
/* 294 */     } while (!compareAndSet(paramT, l1, l2));
/* 295 */     return l1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long updateAndGet(T paramT, LongUnaryOperator paramLongUnaryOperator)
/*     */   {
/*     */     long l1;
/*     */     
/*     */ 
/*     */ 
/*     */     long l2;
/*     */     
/*     */ 
/*     */     do
/*     */     {
/* 312 */       l1 = get(paramT);
/* 313 */       l2 = paramLongUnaryOperator.applyAsLong(l1);
/* 314 */     } while (!compareAndSet(paramT, l1, l2));
/* 315 */     return l2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getAndAccumulate(T paramT, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*     */   {
/*     */     long l1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     long l2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 337 */       l1 = get(paramT);
/* 338 */       l2 = paramLongBinaryOperator.applyAsLong(l1, paramLong);
/* 339 */     } while (!compareAndSet(paramT, l1, l2));
/* 340 */     return l1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long accumulateAndGet(T paramT, long paramLong, LongBinaryOperator paramLongBinaryOperator)
/*     */   {
/*     */     long l1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     long l2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 362 */       l1 = get(paramT);
/* 363 */       l2 = paramLongBinaryOperator.applyAsLong(l1, paramLong);
/* 364 */     } while (!compareAndSet(paramT, l1, l2));
/* 365 */     return l2;
/*     */   }
/*     */   
/*     */   private static class CASUpdater<T> extends AtomicLongFieldUpdater<T> {
/* 369 */     private static final Unsafe unsafe = ;
/*     */     private final long offset;
/*     */     private final Class<T> tclass;
/*     */     private final Class<?> cclass;
/*     */     
/*     */     CASUpdater(final Class<T> paramClass, final String paramString, Class<?> paramClass1)
/*     */     {
/*     */       Field localField;
/*     */       int i;
/*     */       try {
/* 379 */         localField = (Field)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Field run() throws NoSuchFieldException {
/* 382 */             return paramClass.getDeclaredField(paramString);
/*     */           }
/* 384 */         });
/* 385 */         i = localField.getModifiers();
/* 386 */         ReflectUtil.ensureMemberAccess(paramClass1, paramClass, null, i);
/*     */         
/* 388 */         ClassLoader localClassLoader1 = paramClass.getClassLoader();
/* 389 */         ClassLoader localClassLoader2 = paramClass1.getClassLoader();
/* 390 */         if ((localClassLoader2 != null) && (localClassLoader2 != localClassLoader1) && ((localClassLoader1 == null) || 
/* 391 */           (!AtomicLongFieldUpdater.isAncestor(localClassLoader1, localClassLoader2)))) {
/* 392 */           ReflectUtil.checkPackageAccess(paramClass);
/*     */         }
/*     */       } catch (PrivilegedActionException localPrivilegedActionException) {
/* 395 */         throw new RuntimeException(localPrivilegedActionException.getException());
/*     */       } catch (Exception localException) {
/* 397 */         throw new RuntimeException(localException);
/*     */       }
/*     */       
/* 400 */       Class localClass = localField.getType();
/* 401 */       if (localClass != Long.TYPE) {
/* 402 */         throw new IllegalArgumentException("Must be long type");
/*     */       }
/* 404 */       if (!Modifier.isVolatile(i)) {
/* 405 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/* 407 */       this.cclass = ((Modifier.isProtected(i)) && (paramClass1 != paramClass) ? paramClass1 : null);
/*     */       
/* 409 */       this.tclass = paramClass;
/* 410 */       this.offset = unsafe.objectFieldOffset(localField);
/*     */     }
/*     */     
/*     */     private void fullCheck(T paramT) {
/* 414 */       if (!this.tclass.isInstance(paramT))
/* 415 */         throw new ClassCastException();
/* 416 */       if (this.cclass != null)
/* 417 */         ensureProtectedAccess(paramT);
/*     */     }
/*     */     
/*     */     public boolean compareAndSet(T paramT, long paramLong1, long paramLong2) {
/* 421 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 422 */       return unsafe.compareAndSwapLong(paramT, this.offset, paramLong1, paramLong2);
/*     */     }
/*     */     
/*     */     public boolean weakCompareAndSet(T paramT, long paramLong1, long paramLong2) {
/* 426 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 427 */       return unsafe.compareAndSwapLong(paramT, this.offset, paramLong1, paramLong2);
/*     */     }
/*     */     
/*     */     public void set(T paramT, long paramLong) {
/* 431 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 432 */       unsafe.putLongVolatile(paramT, this.offset, paramLong);
/*     */     }
/*     */     
/*     */     public void lazySet(T paramT, long paramLong) {
/* 436 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 437 */       unsafe.putOrderedLong(paramT, this.offset, paramLong);
/*     */     }
/*     */     
/*     */     public long get(T paramT) {
/* 441 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 442 */       return unsafe.getLongVolatile(paramT, this.offset);
/*     */     }
/*     */     
/*     */     public long getAndSet(T paramT, long paramLong) {
/* 446 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 447 */       return unsafe.getAndSetLong(paramT, this.offset, paramLong);
/*     */     }
/*     */     
/*     */     public long getAndIncrement(T paramT) {
/* 451 */       return getAndAdd(paramT, 1L);
/*     */     }
/*     */     
/*     */     public long getAndDecrement(T paramT) {
/* 455 */       return getAndAdd(paramT, -1L);
/*     */     }
/*     */     
/*     */     public long getAndAdd(T paramT, long paramLong) {
/* 459 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 460 */       return unsafe.getAndAddLong(paramT, this.offset, paramLong);
/*     */     }
/*     */     
/*     */     public long incrementAndGet(T paramT) {
/* 464 */       return getAndAdd(paramT, 1L) + 1L;
/*     */     }
/*     */     
/*     */     public long decrementAndGet(T paramT) {
/* 468 */       return getAndAdd(paramT, -1L) - 1L;
/*     */     }
/*     */     
/*     */     public long addAndGet(T paramT, long paramLong) {
/* 472 */       return getAndAdd(paramT, paramLong) + paramLong;
/*     */     }
/*     */     
/*     */     private void ensureProtectedAccess(T paramT) {
/* 476 */       if (this.cclass.isInstance(paramT)) {
/* 477 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 485 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass.getName() + " can not access a protected member of class " + this.tclass.getName() + " using an instance of " + paramT.getClass().getName()));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class LockedUpdater<T>
/*     */     extends AtomicLongFieldUpdater<T>
/*     */   {
/* 493 */     private static final Unsafe unsafe = ;
/*     */     private final long offset;
/*     */     private final Class<T> tclass;
/*     */     private final Class<?> cclass;
/*     */     
/*     */     LockedUpdater(final Class<T> paramClass, final String paramString, Class<?> paramClass1)
/*     */     {
/* 500 */       Field localField = null;
/* 501 */       int i = 0;
/*     */       try {
/* 503 */         localField = (Field)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Field run() throws NoSuchFieldException {
/* 506 */             return paramClass.getDeclaredField(paramString);
/*     */           }
/* 508 */         });
/* 509 */         i = localField.getModifiers();
/* 510 */         ReflectUtil.ensureMemberAccess(paramClass1, paramClass, null, i);
/*     */         
/* 512 */         ClassLoader localClassLoader1 = paramClass.getClassLoader();
/* 513 */         ClassLoader localClassLoader2 = paramClass1.getClassLoader();
/* 514 */         if ((localClassLoader2 != null) && (localClassLoader2 != localClassLoader1) && ((localClassLoader1 == null) || 
/* 515 */           (!AtomicLongFieldUpdater.isAncestor(localClassLoader1, localClassLoader2)))) {
/* 516 */           ReflectUtil.checkPackageAccess(paramClass);
/*     */         }
/*     */       } catch (PrivilegedActionException localPrivilegedActionException) {
/* 519 */         throw new RuntimeException(localPrivilegedActionException.getException());
/*     */       } catch (Exception localException) {
/* 521 */         throw new RuntimeException(localException);
/*     */       }
/*     */       
/* 524 */       Class localClass = localField.getType();
/* 525 */       if (localClass != Long.TYPE) {
/* 526 */         throw new IllegalArgumentException("Must be long type");
/*     */       }
/* 528 */       if (!Modifier.isVolatile(i)) {
/* 529 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/* 531 */       this.cclass = ((Modifier.isProtected(i)) && (paramClass1 != paramClass) ? paramClass1 : null);
/*     */       
/* 533 */       this.tclass = paramClass;
/* 534 */       this.offset = unsafe.objectFieldOffset(localField);
/*     */     }
/*     */     
/*     */     private void fullCheck(T paramT) {
/* 538 */       if (!this.tclass.isInstance(paramT))
/* 539 */         throw new ClassCastException();
/* 540 */       if (this.cclass != null)
/* 541 */         ensureProtectedAccess(paramT);
/*     */     }
/*     */     
/*     */     public boolean compareAndSet(T paramT, long paramLong1, long paramLong2) {
/* 545 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 546 */       synchronized (this) {
/* 547 */         long l = unsafe.getLong(paramT, this.offset);
/* 548 */         if (l != paramLong1)
/* 549 */           return false;
/* 550 */         unsafe.putLong(paramT, this.offset, paramLong2);
/* 551 */         return true;
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean weakCompareAndSet(T paramT, long paramLong1, long paramLong2) {
/* 556 */       return compareAndSet(paramT, paramLong1, paramLong2);
/*     */     }
/*     */     
/*     */     public void set(T paramT, long paramLong) {
/* 560 */       if ((paramT == null) || (paramT.getClass() != this.tclass) || (this.cclass != null)) fullCheck(paramT);
/* 561 */       synchronized (this) {
/* 562 */         unsafe.putLong(paramT, this.offset, paramLong);
/*     */       }
/*     */     }
/*     */     
/*     */     public void lazySet(T paramT, long paramLong) {
/* 567 */       set(paramT, paramLong);
/*     */     }
/*     */     
/*     */     /* Error */
/*     */     public long get(T paramT)
/*     */     {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: ifnull +21 -> 22
/*     */       //   4: aload_1
/*     */       //   5: invokevirtual 33	java/lang/Object:getClass	()Ljava/lang/Class;
/*     */       //   8: aload_0
/*     */       //   9: getfield 25	java/util/concurrent/atomic/AtomicLongFieldUpdater$LockedUpdater:tclass	Ljava/lang/Class;
/*     */       //   12: if_acmpne +10 -> 22
/*     */       //   15: aload_0
/*     */       //   16: getfield 24	java/util/concurrent/atomic/AtomicLongFieldUpdater$LockedUpdater:cclass	Ljava/lang/Class;
/*     */       //   19: ifnull +8 -> 27
/*     */       //   22: aload_0
/*     */       //   23: aload_1
/*     */       //   24: invokespecial 34	java/util/concurrent/atomic/AtomicLongFieldUpdater$LockedUpdater:fullCheck	(Ljava/lang/Object;)V
/*     */       //   27: aload_0
/*     */       //   28: dup
/*     */       //   29: astore_2
/*     */       //   30: monitorenter
/*     */       //   31: getstatic 26	java/util/concurrent/atomic/AtomicLongFieldUpdater$LockedUpdater:unsafe	Lsun/misc/Unsafe;
/*     */       //   34: aload_1
/*     */       //   35: aload_0
/*     */       //   36: getfield 28	java/util/concurrent/atomic/AtomicLongFieldUpdater$LockedUpdater:offset	J
/*     */       //   39: invokevirtual 35	sun/misc/Unsafe:getLong	(Ljava/lang/Object;J)J
/*     */       //   42: aload_2
/*     */       //   43: monitorexit
/*     */       //   44: lreturn
/*     */       //   45: astore_3
/*     */       //   46: aload_2
/*     */       //   47: monitorexit
/*     */       //   48: aload_3
/*     */       //   49: athrow
/*     */       // Line number table:
/*     */       //   Java source line #571	-> byte code offset #0
/*     */       //   Java source line #572	-> byte code offset #27
/*     */       //   Java source line #573	-> byte code offset #31
/*     */       //   Java source line #574	-> byte code offset #45
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	50	0	this	LockedUpdater
/*     */       //   0	50	1	paramT	T
/*     */       //   29	18	2	Ljava/lang/Object;	Object
/*     */       //   45	4	3	localObject1	Object
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   31	44	45	finally
/*     */       //   45	48	45	finally
/*     */     }
/*     */     
/*     */     private void ensureProtectedAccess(T paramT)
/*     */     {
/* 578 */       if (this.cclass.isInstance(paramT)) {
/* 579 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 587 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass.getName() + " can not access a protected member of class " + this.tclass.getName() + " using an instance of " + paramT.getClass().getName()));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isAncestor(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2)
/*     */   {
/* 599 */     ClassLoader localClassLoader = paramClassLoader1;
/*     */     do {
/* 601 */       localClassLoader = localClassLoader.getParent();
/* 602 */       if (paramClassLoader2 == localClassLoader) {
/* 603 */         return true;
/*     */       }
/* 605 */     } while (localClassLoader != null);
/* 606 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/atomic/AtomicLongFieldUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */