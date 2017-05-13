/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.ProtectionDomain;
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
/*     */ public class ForkJoinWorkerThread
/*     */   extends Thread
/*     */ {
/*     */   final ForkJoinPool pool;
/*     */   final ForkJoinPool.WorkQueue workQueue;
/*     */   private static final Unsafe U;
/*     */   private static final long THREADLOCALS;
/*     */   private static final long INHERITABLETHREADLOCALS;
/*     */   private static final long INHERITEDACCESSCONTROLCONTEXT;
/*     */   
/*     */   protected ForkJoinWorkerThread(ForkJoinPool paramForkJoinPool)
/*     */   {
/*  84 */     super("aForkJoinWorkerThread");
/*  85 */     this.pool = paramForkJoinPool;
/*  86 */     this.workQueue = paramForkJoinPool.registerWorker(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   ForkJoinWorkerThread(ForkJoinPool paramForkJoinPool, ThreadGroup paramThreadGroup, AccessControlContext paramAccessControlContext)
/*     */   {
/*  94 */     super(paramThreadGroup, null, "aForkJoinWorkerThread");
/*  95 */     U.putOrderedObject(this, INHERITEDACCESSCONTROLCONTEXT, paramAccessControlContext);
/*  96 */     eraseThreadLocals();
/*  97 */     this.pool = paramForkJoinPool;
/*  98 */     this.workQueue = paramForkJoinPool.registerWorker(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ForkJoinPool getPool()
/*     */   {
/* 107 */     return this.pool;
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
/*     */   public int getPoolIndex()
/*     */   {
/* 121 */     return this.workQueue.poolIndex >>> 1;
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
/*     */   protected void onStart() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onTermination(Throwable paramThrowable) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 153 */     if (this.workQueue.array == null) {
/* 154 */       Object localObject1 = null;
/*     */       try {
/* 156 */         onStart();
/* 157 */         this.pool.runWorker(this.workQueue);
/*     */       } catch (Throwable localThrowable2) {
/* 159 */         localObject1 = localThrowable2;
/*     */       } finally {
/*     */         try {
/* 162 */           onTermination((Throwable)localObject1);
/*     */         } catch (Throwable localThrowable4) {
/* 164 */           if (localObject1 == null)
/* 165 */             localObject1 = localThrowable4;
/*     */         } finally {
/* 167 */           this.pool.deregisterWorker(this, (Throwable)localObject1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   final void eraseThreadLocals()
/*     */   {
/* 177 */     U.putObject(this, THREADLOCALS, null);
/* 178 */     U.putObject(this, INHERITABLETHREADLOCALS, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void afterTopLevelExec() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 194 */       U = Unsafe.getUnsafe();
/* 195 */       Class localClass = Thread.class;
/*     */       
/* 197 */       THREADLOCALS = U.objectFieldOffset(localClass.getDeclaredField("threadLocals"));
/*     */       
/* 199 */       INHERITABLETHREADLOCALS = U.objectFieldOffset(localClass.getDeclaredField("inheritableThreadLocals"));
/*     */       
/* 201 */       INHERITEDACCESSCONTROLCONTEXT = U.objectFieldOffset(localClass.getDeclaredField("inheritedAccessControlContext"));
/*     */     }
/*     */     catch (Exception localException) {
/* 204 */       throw new Error(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class InnocuousForkJoinWorkerThread
/*     */     extends ForkJoinWorkerThread
/*     */   {
/* 216 */     private static final ThreadGroup innocuousThreadGroup = ;
/*     */     
/*     */ 
/* 219 */     private static final AccessControlContext INNOCUOUS_ACC = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     InnocuousForkJoinWorkerThread(ForkJoinPool paramForkJoinPool)
/*     */     {
/* 226 */       super(innocuousThreadGroup, INNOCUOUS_ACC);
/*     */     }
/*     */     
/*     */     void afterTopLevelExec()
/*     */     {
/* 231 */       eraseThreadLocals();
/*     */     }
/*     */     
/*     */     public ClassLoader getContextClassLoader()
/*     */     {
/* 236 */       return ClassLoader.getSystemClassLoader();
/*     */     }
/*     */     
/*     */ 
/*     */     public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler) {}
/*     */     
/*     */     public void setContextClassLoader(ClassLoader paramClassLoader)
/*     */     {
/* 244 */       throw new SecurityException("setContextClassLoader");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private static ThreadGroup createThreadGroup()
/*     */     {
/*     */       try
/*     */       {
/* 254 */         Unsafe localUnsafe = Unsafe.getUnsafe();
/* 255 */         Class localClass1 = Thread.class;
/* 256 */         Class localClass2 = ThreadGroup.class;
/* 257 */         long l1 = localUnsafe.objectFieldOffset(localClass1.getDeclaredField("group"));
/* 258 */         long l2 = localUnsafe.objectFieldOffset(localClass2.getDeclaredField("parent"));
/*     */         
/* 260 */         Object localObject = (ThreadGroup)localUnsafe.getObject(Thread.currentThread(), l1);
/* 261 */         while (localObject != null) {
/* 262 */           ThreadGroup localThreadGroup = (ThreadGroup)localUnsafe.getObject(localObject, l2);
/* 263 */           if (localThreadGroup == null) {
/* 264 */             return new ThreadGroup((ThreadGroup)localObject, "InnocuousForkJoinWorkerThreadGroup");
/*     */           }
/* 266 */           localObject = localThreadGroup;
/*     */         }
/*     */       } catch (Exception localException) {
/* 269 */         throw new Error(localException);
/*     */       }
/*     */       
/* 272 */       throw new Error("Cannot create ThreadGroup");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ForkJoinWorkerThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */