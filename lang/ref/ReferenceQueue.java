/*     */ package java.lang.ref;
/*     */ 
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceQueue<T>
/*     */ {
/*     */   private static class Lock {}
/*     */   
/*     */   private static class Null<S>
/*     */     extends ReferenceQueue<S>
/*     */   {
/*     */     boolean enqueue(Reference<? extends S> paramReference)
/*     */     {
/*  45 */       return false;
/*     */     }
/*     */   }
/*     */   
/*  49 */   static ReferenceQueue<Object> NULL = new Null(null);
/*  50 */   static ReferenceQueue<Object> ENQUEUED = new Null(null);
/*     */   
/*     */ 
/*  53 */   private Lock lock = new Lock(null);
/*  54 */   private volatile Reference<? extends T> head = null;
/*  55 */   private long queueLength = 0L;
/*     */   
/*     */   boolean enqueue(Reference<? extends T> paramReference) {
/*  58 */     synchronized (this.lock)
/*     */     {
/*     */ 
/*  61 */       ReferenceQueue localReferenceQueue = paramReference.queue;
/*  62 */       if ((localReferenceQueue == NULL) || (localReferenceQueue == ENQUEUED)) {
/*  63 */         return false;
/*     */       }
/*  65 */       assert (localReferenceQueue == this);
/*  66 */       paramReference.queue = ENQUEUED;
/*  67 */       paramReference.next = (this.head == null ? paramReference : this.head);
/*  68 */       this.head = paramReference;
/*  69 */       this.queueLength += 1L;
/*  70 */       if ((paramReference instanceof FinalReference)) {
/*  71 */         VM.addFinalRefCount(1);
/*     */       }
/*  73 */       this.lock.notifyAll();
/*  74 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private Reference<? extends T> reallyPoll()
/*     */   {
/*  80 */     Reference localReference = this.head;
/*  81 */     if (localReference != null) {
/*  82 */       this.head = (localReference.next == localReference ? null : localReference.next);
/*     */       
/*     */ 
/*  85 */       localReference.queue = NULL;
/*  86 */       localReference.next = localReference;
/*  87 */       this.queueLength -= 1L;
/*  88 */       if ((localReference instanceof FinalReference)) {
/*  89 */         VM.addFinalRefCount(-1);
/*     */       }
/*  91 */       return localReference;
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Reference<? extends T> poll()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 5	java/lang/ref/ReferenceQueue:head	Ljava/lang/ref/Reference;
/*     */     //   4: ifnonnull +5 -> 9
/*     */     //   7: aconst_null
/*     */     //   8: areturn
/*     */     //   9: aload_0
/*     */     //   10: getfield 4	java/lang/ref/ReferenceQueue:lock	Ljava/lang/ref/ReferenceQueue$Lock;
/*     */     //   13: dup
/*     */     //   14: astore_1
/*     */     //   15: monitorenter
/*     */     //   16: aload_0
/*     */     //   17: invokespecial 17	java/lang/ref/ReferenceQueue:reallyPoll	()Ljava/lang/ref/Reference;
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: areturn
/*     */     //   23: astore_2
/*     */     //   24: aload_1
/*     */     //   25: monitorexit
/*     */     //   26: aload_2
/*     */     //   27: athrow
/*     */     // Line number table:
/*     */     //   Java source line #105	-> byte code offset #0
/*     */     //   Java source line #106	-> byte code offset #7
/*     */     //   Java source line #107	-> byte code offset #9
/*     */     //   Java source line #108	-> byte code offset #16
/*     */     //   Java source line #109	-> byte code offset #23
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	28	0	this	ReferenceQueue
/*     */     //   14	11	1	Ljava/lang/Object;	Object
/*     */     //   23	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   16	22	23	finally
/*     */     //   23	26	23	finally
/*     */   }
/*     */   
/*     */   public Reference<? extends T> remove(long paramLong)
/*     */     throws IllegalArgumentException, InterruptedException
/*     */   {
/* 135 */     if (paramLong < 0L) {
/* 136 */       throw new IllegalArgumentException("Negative timeout value");
/*     */     }
/* 138 */     synchronized (this.lock) {
/* 139 */       Reference localReference = reallyPoll();
/* 140 */       if (localReference != null) return localReference;
/*     */       do {
/* 142 */         this.lock.wait(paramLong);
/* 143 */         localReference = reallyPoll();
/* 144 */         if (localReference != null) return localReference;
/* 145 */       } while (paramLong == 0L); return null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Reference<? extends T> remove()
/*     */     throws InterruptedException
/*     */   {
/* 158 */     return remove(0L);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ref/ReferenceQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */