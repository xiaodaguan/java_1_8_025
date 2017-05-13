/*     */ package java.util.concurrent;
/*     */ 
/*     */ import sun.misc.Contended;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Exchanger<V>
/*     */ {
/*     */   private static final int ASHIFT = 7;
/*     */   private static final int MMASK = 255;
/*     */   private static final int SEQ = 256;
/* 278 */   private static final int NCPU = Runtime.getRuntime().availableProcessors();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 285 */   static final int FULL = NCPU >= 510 ? 255 : NCPU >>> 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int SPINS = 1024;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 299 */   private static final Object NULL_ITEM = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 306 */   private static final Object TIMED_OUT = new Object();
/*     */   
/*     */   private final Participant participant;
/*     */   
/*     */   private volatile Node[] arena;
/*     */   private volatile Node slot;
/*     */   private volatile int bound;
/*     */   private static final Unsafe U;
/*     */   private static final long BOUND;
/*     */   private static final long SLOT;
/*     */   private static final long MATCH;
/*     */   private static final long BLOCKER;
/*     */   private static final int ABASE;
/*     */   
/*     */   static final class Participant
/*     */     extends ThreadLocal<Exchanger.Node>
/*     */   {
/*     */     public Exchanger.Node initialValue()
/*     */     {
/* 325 */       return new Exchanger.Node();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Object arenaExchange(Object paramObject, boolean paramBoolean, long paramLong)
/*     */   {
/* 362 */     Node[] arrayOfNode = this.arena;
/* 363 */     Node localNode1 = (Node)this.participant.get();
/* 364 */     int i = localNode1.index;
/*     */     for (;;) { long l1;
/* 366 */       Node localNode2 = (Node)U.getObjectVolatile(arrayOfNode, l1 = (i << 7) + ABASE);
/* 367 */       if ((localNode2 != null) && (U.compareAndSwapObject(arrayOfNode, l1, localNode2, null))) {
/* 368 */         Object localObject1 = localNode2.item;
/* 369 */         localNode2.match = paramObject;
/* 370 */         Thread localThread1 = localNode2.parked;
/* 371 */         if (localThread1 != null)
/* 372 */           U.unpark(localThread1);
/* 373 */         return localObject1; }
/*     */       int j;
/* 375 */       int k; if ((i <= (k = (j = this.bound) & 0xFF)) && (localNode2 == null)) {
/* 376 */         localNode1.item = paramObject;
/* 377 */         if (U.compareAndSwapObject(arrayOfNode, l1, null, localNode1)) {
/* 378 */           long l2 = (paramBoolean) && (k == 0) ? System.nanoTime() + paramLong : 0L;
/* 379 */           Thread localThread2 = Thread.currentThread();
/* 380 */           int n = localNode1.hash;int i1 = 1024;
/* 381 */           for (;;) { Object localObject2 = localNode1.match;
/* 382 */             if (localObject2 != null) {
/* 383 */               U.putOrderedObject(localNode1, MATCH, null);
/* 384 */               localNode1.item = null;
/* 385 */               localNode1.hash = n;
/* 386 */               return localObject2;
/*     */             }
/* 388 */             if (i1 > 0) {
/* 389 */               n ^= n << 1;n ^= n >>> 3;n ^= n << 10;
/* 390 */               if (n == 0) {
/* 391 */                 n = 0x400 | (int)localThread2.getId();
/* 392 */               } else if (n < 0) { i1--; if ((i1 & 0x1FF) == 0)
/*     */                 {
/* 394 */                   Thread.yield(); }
/*     */               }
/* 396 */             } else if (U.getObjectVolatile(arrayOfNode, l1) != localNode1) {
/* 397 */               i1 = 1024;
/* 398 */             } else if ((!localThread2.isInterrupted()) && (k == 0) && ((!paramBoolean) || 
/*     */             
/* 400 */               ((paramLong = l2 - System.nanoTime()) > 0L))) {
/* 401 */               U.putObject(localThread2, BLOCKER, this);
/* 402 */               localNode1.parked = localThread2;
/* 403 */               if (U.getObjectVolatile(arrayOfNode, l1) == localNode1)
/* 404 */                 U.park(false, paramLong);
/* 405 */               localNode1.parked = null;
/* 406 */               U.putObject(localThread2, BLOCKER, null);
/*     */             }
/* 408 */             else if ((U.getObjectVolatile(arrayOfNode, l1) == localNode1) && 
/* 409 */               (U.compareAndSwapObject(arrayOfNode, l1, localNode1, null))) {
/* 410 */               if (k != 0)
/* 411 */                 U.compareAndSwapInt(this, BOUND, j, j + 256 - 1);
/* 412 */               localNode1.item = null;
/* 413 */               localNode1.hash = n;
/* 414 */               i = localNode1.index >>>= 1;
/* 415 */               if (Thread.interrupted())
/* 416 */                 return null;
/* 417 */               if ((!paramBoolean) || (k != 0) || (paramLong > 0L)) break;
/* 418 */               return TIMED_OUT;
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 424 */           localNode1.item = null;
/*     */         }
/*     */       } else {
/* 427 */         if (localNode1.bound != j) {
/* 428 */           localNode1.bound = j;
/* 429 */           localNode1.collides = 0;
/* 430 */           i = (i != k) || (k == 0) ? k : k - 1;
/*     */         } else { int m;
/* 432 */           if (((m = localNode1.collides) < k) || (k == FULL) || 
/* 433 */             (!U.compareAndSwapInt(this, BOUND, j, j + 256 + 1))) {
/* 434 */             localNode1.collides = (m + 1);
/* 435 */             i = i == 0 ? k : i - 1;
/*     */           }
/*     */           else {
/* 438 */             i = k + 1; } }
/* 439 */         localNode1.index = i;
/*     */       }
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
/*     */   private final Object slotExchange(Object paramObject, boolean paramBoolean, long paramLong)
/*     */   {
/* 455 */     Node localNode1 = (Node)this.participant.get();
/* 456 */     Thread localThread1 = Thread.currentThread();
/* 457 */     if (localThread1.isInterrupted())
/* 458 */       return null;
/*     */     for (;;) {
/*     */       Node localNode2;
/* 461 */       if ((localNode2 = this.slot) != null) {
/* 462 */         if (U.compareAndSwapObject(this, SLOT, localNode2, null)) {
/* 463 */           Object localObject1 = localNode2.item;
/* 464 */           localNode2.match = paramObject;
/* 465 */           Thread localThread2 = localNode2.parked;
/* 466 */           if (localThread2 != null)
/* 467 */             U.unpark(localThread2);
/* 468 */           return localObject1;
/*     */         }
/*     */         
/* 471 */         if ((NCPU > 1) && (this.bound == 0) && 
/* 472 */           (U.compareAndSwapInt(this, BOUND, 0, 256)))
/* 473 */           this.arena = new Node[FULL + 2 << 7];
/*     */       } else {
/* 475 */         if (this.arena != null) {
/* 476 */           return null;
/*     */         }
/* 478 */         localNode1.item = paramObject;
/* 479 */         if (U.compareAndSwapObject(this, SLOT, null, localNode1))
/*     */           break;
/* 481 */         localNode1.item = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 486 */     int i = localNode1.hash;
/* 487 */     long l = paramBoolean ? System.nanoTime() + paramLong : 0L;
/* 488 */     int j = NCPU > 1 ? 1024 : 1;
/*     */     Object localObject2;
/* 490 */     while ((localObject2 = localNode1.match) == null) {
/* 491 */       if (j > 0) {
/* 492 */         i ^= i << 1;i ^= i >>> 3;i ^= i << 10;
/* 493 */         if (i == 0) {
/* 494 */           i = 0x400 | (int)localThread1.getId();
/* 495 */         } else if (i < 0) { j--; if ((j & 0x1FF) == 0)
/* 496 */             Thread.yield();
/*     */         }
/* 498 */       } else if (this.slot != localNode1) {
/* 499 */         j = 1024;
/* 500 */       } else if ((!localThread1.isInterrupted()) && (this.arena == null) && ((!paramBoolean) || 
/* 501 */         ((paramLong = l - System.nanoTime()) > 0L))) {
/* 502 */         U.putObject(localThread1, BLOCKER, this);
/* 503 */         localNode1.parked = localThread1;
/* 504 */         if (this.slot == localNode1)
/* 505 */           U.park(false, paramLong);
/* 506 */         localNode1.parked = null;
/* 507 */         U.putObject(localThread1, BLOCKER, null);
/*     */       }
/* 509 */       else if (U.compareAndSwapObject(this, SLOT, localNode1, null)) {
/* 510 */         localObject2 = (paramBoolean) && (paramLong <= 0L) && (!localThread1.isInterrupted()) ? TIMED_OUT : null;
/*     */       }
/*     */     }
/*     */     
/* 514 */     U.putOrderedObject(localNode1, MATCH, null);
/* 515 */     localNode1.item = null;
/* 516 */     localNode1.hash = i;
/* 517 */     return localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Exchanger()
/*     */   {
/* 524 */     this.participant = new Participant();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V exchange(V paramV)
/*     */     throws InterruptedException
/*     */   {
/* 563 */     V ? = paramV == null ? NULL_ITEM : paramV;
/* 564 */     Object localObject; if (((this.arena != null) || 
/* 565 */       ((localObject = slotExchange(?, false, 0L)) == null)) && (
/* 566 */       (Thread.interrupted()) || 
/* 567 */       ((localObject = arenaExchange(?, false, 0L)) == null)))
/* 568 */       throw new InterruptedException();
/* 569 */     return localObject == NULL_ITEM ? null : localObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V exchange(V paramV, long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException, TimeoutException
/*     */   {
/* 618 */     V ? = paramV == null ? NULL_ITEM : paramV;
/* 619 */     long l = paramTimeUnit.toNanos(paramLong);
/* 620 */     Object localObject; if (((this.arena != null) || 
/* 621 */       ((localObject = slotExchange(?, true, l)) == null)) && (
/* 622 */       (Thread.interrupted()) || 
/* 623 */       ((localObject = arenaExchange(?, true, l)) == null)))
/* 624 */       throw new InterruptedException();
/* 625 */     if (localObject == TIMED_OUT)
/* 626 */       throw new TimeoutException();
/* 627 */     return localObject == NULL_ITEM ? null : localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 640 */       U = Unsafe.getUnsafe();
/* 641 */       Class localClass1 = Exchanger.class;
/* 642 */       Class localClass2 = Node.class;
/* 643 */       Class localClass3 = Node[].class;
/* 644 */       Class localClass4 = Thread.class;
/*     */       
/* 646 */       BOUND = U.objectFieldOffset(localClass1.getDeclaredField("bound"));
/*     */       
/* 648 */       SLOT = U.objectFieldOffset(localClass1.getDeclaredField("slot"));
/*     */       
/* 650 */       MATCH = U.objectFieldOffset(localClass2.getDeclaredField("match"));
/*     */       
/* 652 */       BLOCKER = U.objectFieldOffset(localClass4.getDeclaredField("parkBlocker"));
/* 653 */       i = U.arrayIndexScale(localClass3);
/*     */       
/* 655 */       ABASE = U.arrayBaseOffset(localClass3) + 128;
/*     */     }
/*     */     catch (Exception localException) {
/* 658 */       throw new Error(localException);
/*     */     }
/* 660 */     if (((i & i - 1) != 0) || (i > 128)) {
/* 661 */       throw new Error("Unsupported array scale");
/*     */     }
/*     */   }
/*     */   
/*     */   @Contended
/*     */   static final class Node
/*     */   {
/*     */     int index;
/*     */     int bound;
/*     */     int collides;
/*     */     int hash;
/*     */     Object item;
/*     */     volatile Object match;
/*     */     volatile Thread parked;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/Exchanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */