/*     */ package java.lang.ref;
/*     */ 
/*     */ import sun.misc.Cleaner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Reference<T>
/*     */ {
/*     */   private T referent;
/*     */   volatile ReferenceQueue<? super T> queue;
/*     */   Reference next;
/*     */   private transient Reference<T> discovered;
/* 115 */   private static Lock lock = new Lock(null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 123 */   private static Reference<Object> pending = null;
/*     */   
/*     */   private static class ReferenceHandler
/*     */     extends Thread
/*     */   {
/*     */     ReferenceHandler(ThreadGroup paramThreadGroup, String paramString)
/*     */     {
/* 130 */       super(paramString);
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       for (;;) {
/*     */         Reference localReference;
/* 136 */         synchronized (Reference.lock) {
/* 137 */           if (Reference.pending != null) {
/* 138 */             localReference = Reference.pending;
/* 139 */             Reference.access$202(localReference.discovered);
/* 140 */             localReference.discovered = null;
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/*     */ 
/*     */             try
/*     */             {
/*     */ 
/*     */ 
/*     */               try
/*     */               {
/*     */ 
/*     */ 
/* 157 */                 Reference.lock.wait();
/*     */               } catch (OutOfMemoryError localOutOfMemoryError) {}
/*     */             } catch (InterruptedException localInterruptedException) {}
/* 160 */             continue;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 165 */         if ((localReference instanceof Cleaner)) {
/* 166 */           ((Cleaner)localReference).clean();
/*     */         }
/*     */         else
/*     */         {
/* 170 */           ??? = localReference.queue;
/* 171 */           if (??? != ReferenceQueue.NULL) ((ReferenceQueue)???).enqueue(localReference);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 177 */   static { Object localObject1 = Thread.currentThread().getThreadGroup();
/* 178 */     for (Object localObject2 = localObject1; 
/* 179 */         localObject2 != null; 
/* 180 */         localObject2 = ((ThreadGroup)localObject1).getParent()) localObject1 = localObject2;
/* 181 */     localObject2 = new ReferenceHandler((ThreadGroup)localObject1, "Reference Handler");
/*     */     
/*     */ 
/*     */ 
/* 185 */     ((Thread)localObject2).setPriority(10);
/* 186 */     ((Thread)localObject2).setDaemon(true);
/* 187 */     ((Thread)localObject2).start();
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
/*     */   public T get()
/*     */   {
/* 202 */     return (T)this.referent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 213 */     this.referent = null;
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
/*     */   public boolean isEnqueued()
/*     */   {
/* 229 */     return this.queue == ReferenceQueue.ENQUEUED;
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
/*     */   public boolean enqueue()
/*     */   {
/* 244 */     return this.queue.enqueue(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   Reference(T paramT)
/*     */   {
/* 251 */     this(paramT, null);
/*     */   }
/*     */   
/*     */   Reference(T paramT, ReferenceQueue<? super T> paramReferenceQueue) {
/* 255 */     this.referent = paramT;
/* 256 */     this.queue = (paramReferenceQueue == null ? ReferenceQueue.NULL : paramReferenceQueue);
/*     */   }
/*     */   
/*     */   private static class Lock {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ref/Reference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */