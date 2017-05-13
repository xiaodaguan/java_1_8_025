/*     */ package java.lang;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadLocal<T>
/*     */ {
/*  85 */   private final int threadLocalHashCode = nextHashCode();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  91 */   private static AtomicInteger nextHashCode = new AtomicInteger();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int HASH_INCREMENT = 1640531527;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int nextHashCode()
/*     */   {
/* 105 */     return nextHashCode.getAndAdd(1640531527);
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
/*     */   protected T initialValue()
/*     */   {
/* 127 */     return null;
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
/*     */   public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> paramSupplier)
/*     */   {
/* 141 */     return new SuppliedThreadLocal(paramSupplier);
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
/*     */   public T get()
/*     */   {
/* 160 */     Thread localThread = Thread.currentThread();
/* 161 */     ThreadLocalMap localThreadLocalMap = getMap(localThread);
/* 162 */     if (localThreadLocalMap != null) {
/* 163 */       ThreadLocal.ThreadLocalMap.Entry localEntry = localThreadLocalMap.getEntry(this);
/* 164 */       if (localEntry != null)
/*     */       {
/* 166 */         Object localObject = localEntry.value;
/* 167 */         return (T)localObject;
/*     */       }
/*     */     }
/* 170 */     return (T)setInitialValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private T setInitialValue()
/*     */   {
/* 180 */     Object localObject = initialValue();
/* 181 */     Thread localThread = Thread.currentThread();
/* 182 */     ThreadLocalMap localThreadLocalMap = getMap(localThread);
/* 183 */     if (localThreadLocalMap != null) {
/* 184 */       localThreadLocalMap.set(this, localObject);
/*     */     } else
/* 186 */       createMap(localThread, localObject);
/* 187 */     return (T)localObject;
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
/*     */   public void set(T paramT)
/*     */   {
/* 200 */     Thread localThread = Thread.currentThread();
/* 201 */     ThreadLocalMap localThreadLocalMap = getMap(localThread);
/* 202 */     if (localThreadLocalMap != null) {
/* 203 */       localThreadLocalMap.set(this, paramT);
/*     */     } else {
/* 205 */       createMap(localThread, paramT);
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
/*     */   public void remove()
/*     */   {
/* 220 */     ThreadLocalMap localThreadLocalMap = getMap(Thread.currentThread());
/* 221 */     if (localThreadLocalMap != null) {
/* 222 */       localThreadLocalMap.remove(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   ThreadLocalMap getMap(Thread paramThread)
/*     */   {
/* 233 */     return paramThread.threadLocals;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void createMap(Thread paramThread, T paramT)
/*     */   {
/* 244 */     paramThread.threadLocals = new ThreadLocalMap(this, paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static ThreadLocalMap createInheritedMap(ThreadLocalMap paramThreadLocalMap)
/*     */   {
/* 255 */     return new ThreadLocalMap(paramThreadLocalMap, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   T childValue(T paramT)
/*     */   {
/* 267 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */   static final class SuppliedThreadLocal<T>
/*     */     extends ThreadLocal<T>
/*     */   {
/*     */     private final Supplier<? extends T> supplier;
/*     */     
/*     */ 
/*     */     SuppliedThreadLocal(Supplier<? extends T> paramSupplier)
/*     */     {
/* 279 */       this.supplier = ((Supplier)Objects.requireNonNull(paramSupplier));
/*     */     }
/*     */     
/*     */     protected T initialValue()
/*     */     {
/* 284 */       return (T)this.supplier.get();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static class ThreadLocalMap
/*     */   {
/*     */     private static final int INITIAL_CAPACITY = 16;
/*     */     
/*     */ 
/*     */ 
/*     */     private Entry[] table;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     static class Entry
/*     */       extends WeakReference<ThreadLocal<?>>
/*     */     {
/*     */       Object value;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       Entry(ThreadLocal<?> paramThreadLocal, Object paramObject)
/*     */       {
/* 313 */         super();
/* 314 */         this.value = paramObject;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 332 */     private int size = 0;
/*     */     
/*     */ 
/*     */ 
/*     */     private int threshold;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private void setThreshold(int paramInt)
/*     */     {
/* 343 */       this.threshold = (paramInt * 2 / 3);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private static int nextIndex(int paramInt1, int paramInt2)
/*     */     {
/* 350 */       return paramInt1 + 1 < paramInt2 ? paramInt1 + 1 : 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private static int prevIndex(int paramInt1, int paramInt2)
/*     */     {
/* 357 */       return paramInt1 - 1 >= 0 ? paramInt1 - 1 : paramInt2 - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     ThreadLocalMap(ThreadLocal<?> paramThreadLocal, Object paramObject)
/*     */     {
/* 366 */       this.table = new Entry[16];
/* 367 */       int i = paramThreadLocal.threadLocalHashCode & 0xF;
/* 368 */       this.table[i] = new Entry(paramThreadLocal, paramObject);
/* 369 */       this.size = 1;
/* 370 */       setThreshold(16);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private ThreadLocalMap(ThreadLocalMap paramThreadLocalMap)
/*     */     {
/* 380 */       Entry[] arrayOfEntry = paramThreadLocalMap.table;
/* 381 */       int i = arrayOfEntry.length;
/* 382 */       setThreshold(i);
/* 383 */       this.table = new Entry[i];
/*     */       
/* 385 */       for (int j = 0; j < i; j++) {
/* 386 */         Entry localEntry1 = arrayOfEntry[j];
/* 387 */         if (localEntry1 != null)
/*     */         {
/* 389 */           ThreadLocal localThreadLocal = (ThreadLocal)localEntry1.get();
/* 390 */           if (localThreadLocal != null) {
/* 391 */             Object localObject = localThreadLocal.childValue(localEntry1.value);
/* 392 */             Entry localEntry2 = new Entry(localThreadLocal, localObject);
/* 393 */             int k = localThreadLocal.threadLocalHashCode & i - 1;
/* 394 */             while (this.table[k] != null)
/* 395 */               k = nextIndex(k, i);
/* 396 */             this.table[k] = localEntry2;
/* 397 */             this.size += 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Entry getEntry(ThreadLocal<?> paramThreadLocal)
/*     */     {
/* 414 */       int i = paramThreadLocal.threadLocalHashCode & this.table.length - 1;
/* 415 */       Entry localEntry = this.table[i];
/* 416 */       if ((localEntry != null) && (localEntry.get() == paramThreadLocal)) {
/* 417 */         return localEntry;
/*     */       }
/* 419 */       return getEntryAfterMiss(paramThreadLocal, i, localEntry);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Entry getEntryAfterMiss(ThreadLocal<?> paramThreadLocal, int paramInt, Entry paramEntry)
/*     */     {
/* 432 */       Entry[] arrayOfEntry = this.table;
/* 433 */       int i = arrayOfEntry.length;
/*     */       
/* 435 */       while (paramEntry != null) {
/* 436 */         ThreadLocal localThreadLocal = (ThreadLocal)paramEntry.get();
/* 437 */         if (localThreadLocal == paramThreadLocal)
/* 438 */           return paramEntry;
/* 439 */         if (localThreadLocal == null) {
/* 440 */           expungeStaleEntry(paramInt);
/*     */         } else
/* 442 */           paramInt = nextIndex(paramInt, i);
/* 443 */         paramEntry = arrayOfEntry[paramInt];
/*     */       }
/* 445 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private void set(ThreadLocal<?> paramThreadLocal, Object paramObject)
/*     */     {
/* 461 */       Entry[] arrayOfEntry = this.table;
/* 462 */       int i = arrayOfEntry.length;
/* 463 */       int j = paramThreadLocal.threadLocalHashCode & i - 1;
/*     */       
/* 465 */       for (Entry localEntry = arrayOfEntry[j]; 
/* 466 */           localEntry != null; 
/* 467 */           localEntry = arrayOfEntry[(j = nextIndex(j, i))]) {
/* 468 */         ThreadLocal localThreadLocal = (ThreadLocal)localEntry.get();
/*     */         
/* 470 */         if (localThreadLocal == paramThreadLocal) {
/* 471 */           localEntry.value = paramObject;
/* 472 */           return;
/*     */         }
/*     */         
/* 475 */         if (localThreadLocal == null) {
/* 476 */           replaceStaleEntry(paramThreadLocal, paramObject, j);
/* 477 */           return;
/*     */         }
/*     */       }
/*     */       
/* 481 */       arrayOfEntry[j] = new Entry(paramThreadLocal, paramObject);
/* 482 */       int k = ++this.size;
/* 483 */       if ((!cleanSomeSlots(j, k)) && (k >= this.threshold)) {
/* 484 */         rehash();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private void remove(ThreadLocal<?> paramThreadLocal)
/*     */     {
/* 491 */       Entry[] arrayOfEntry = this.table;
/* 492 */       int i = arrayOfEntry.length;
/* 493 */       int j = paramThreadLocal.threadLocalHashCode & i - 1;
/* 494 */       for (Entry localEntry = arrayOfEntry[j]; 
/* 495 */           localEntry != null; 
/* 496 */           localEntry = arrayOfEntry[(j = nextIndex(j, i))]) {
/* 497 */         if (localEntry.get() == paramThreadLocal) {
/* 498 */           localEntry.clear();
/* 499 */           expungeStaleEntry(j);
/* 500 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private void replaceStaleEntry(ThreadLocal<?> paramThreadLocal, Object paramObject, int paramInt)
/*     */     {
/* 522 */       Entry[] arrayOfEntry = this.table;
/* 523 */       int i = arrayOfEntry.length;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 530 */       int j = paramInt;
/* 531 */       Entry localEntry; for (int k = prevIndex(paramInt, i); 
/* 532 */           (localEntry = arrayOfEntry[k]) != null; 
/* 533 */           k = prevIndex(k, i)) {
/* 534 */         if (localEntry.get() == null) {
/* 535 */           j = k;
/*     */         }
/*     */       }
/*     */       
/* 539 */       for (k = nextIndex(paramInt, i); 
/* 540 */           (localEntry = arrayOfEntry[k]) != null; 
/* 541 */           k = nextIndex(k, i)) {
/* 542 */         ThreadLocal localThreadLocal = (ThreadLocal)localEntry.get();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 549 */         if (localThreadLocal == paramThreadLocal) {
/* 550 */           localEntry.value = paramObject;
/*     */           
/* 552 */           arrayOfEntry[k] = arrayOfEntry[paramInt];
/* 553 */           arrayOfEntry[paramInt] = localEntry;
/*     */           
/*     */ 
/* 556 */           if (j == paramInt)
/* 557 */             j = k;
/* 558 */           cleanSomeSlots(expungeStaleEntry(j), i);
/* 559 */           return;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 565 */         if ((localThreadLocal == null) && (j == paramInt)) {
/* 566 */           j = k;
/*     */         }
/*     */       }
/*     */       
/* 570 */       arrayOfEntry[paramInt].value = null;
/* 571 */       arrayOfEntry[paramInt] = new Entry(paramThreadLocal, paramObject);
/*     */       
/*     */ 
/* 574 */       if (j != paramInt) {
/* 575 */         cleanSomeSlots(expungeStaleEntry(j), i);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private int expungeStaleEntry(int paramInt)
/*     */     {
/* 590 */       Entry[] arrayOfEntry = this.table;
/* 591 */       int i = arrayOfEntry.length;
/*     */       
/*     */ 
/* 594 */       arrayOfEntry[paramInt].value = null;
/* 595 */       arrayOfEntry[paramInt] = null;
/* 596 */       this.size -= 1;
/*     */       
/*     */ 
/*     */       Entry localEntry;
/*     */       
/* 601 */       for (int j = nextIndex(paramInt, i); 
/* 602 */           (localEntry = arrayOfEntry[j]) != null; 
/* 603 */           j = nextIndex(j, i)) {
/* 604 */         ThreadLocal localThreadLocal = (ThreadLocal)localEntry.get();
/* 605 */         if (localThreadLocal == null) {
/* 606 */           localEntry.value = null;
/* 607 */           arrayOfEntry[j] = null;
/* 608 */           this.size -= 1;
/*     */         } else {
/* 610 */           int k = localThreadLocal.threadLocalHashCode & i - 1;
/* 611 */           if (k != j) {
/* 612 */             arrayOfEntry[j] = null;
/*     */             
/*     */ 
/*     */ 
/* 616 */             while (arrayOfEntry[k] != null)
/* 617 */               k = nextIndex(k, i);
/* 618 */             arrayOfEntry[k] = localEntry;
/*     */           }
/*     */         }
/*     */       }
/* 622 */       return j;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private boolean cleanSomeSlots(int paramInt1, int paramInt2)
/*     */     {
/* 650 */       boolean bool = false;
/* 651 */       Entry[] arrayOfEntry = this.table;
/* 652 */       int i = arrayOfEntry.length;
/*     */       do {
/* 654 */         paramInt1 = nextIndex(paramInt1, i);
/* 655 */         Entry localEntry = arrayOfEntry[paramInt1];
/* 656 */         if ((localEntry != null) && (localEntry.get() == null)) {
/* 657 */           paramInt2 = i;
/* 658 */           bool = true;
/* 659 */           paramInt1 = expungeStaleEntry(paramInt1);
/*     */         }
/* 661 */       } while (paramInt2 >>>= 1 != 0);
/* 662 */       return bool;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private void rehash()
/*     */     {
/* 671 */       expungeStaleEntries();
/*     */       
/*     */ 
/* 674 */       if (this.size >= this.threshold - this.threshold / 4) {
/* 675 */         resize();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private void resize()
/*     */     {
/* 682 */       Entry[] arrayOfEntry1 = this.table;
/* 683 */       int i = arrayOfEntry1.length;
/* 684 */       int j = i * 2;
/* 685 */       Entry[] arrayOfEntry2 = new Entry[j];
/* 686 */       int k = 0;
/*     */       
/* 688 */       for (int m = 0; m < i; m++) {
/* 689 */         Entry localEntry = arrayOfEntry1[m];
/* 690 */         if (localEntry != null) {
/* 691 */           ThreadLocal localThreadLocal = (ThreadLocal)localEntry.get();
/* 692 */           if (localThreadLocal == null) {
/* 693 */             localEntry.value = null;
/*     */           } else {
/* 695 */             int n = localThreadLocal.threadLocalHashCode & j - 1;
/* 696 */             while (arrayOfEntry2[n] != null)
/* 697 */               n = nextIndex(n, j);
/* 698 */             arrayOfEntry2[n] = localEntry;
/* 699 */             k++;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 704 */       setThreshold(j);
/* 705 */       this.size = k;
/* 706 */       this.table = arrayOfEntry2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private void expungeStaleEntries()
/*     */     {
/* 713 */       Entry[] arrayOfEntry = this.table;
/* 714 */       int i = arrayOfEntry.length;
/* 715 */       for (int j = 0; j < i; j++) {
/* 716 */         Entry localEntry = arrayOfEntry[j];
/* 717 */         if ((localEntry != null) && (localEntry.get() == null)) {
/* 718 */           expungeStaleEntry(j);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ThreadLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */