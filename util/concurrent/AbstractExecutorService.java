/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractExecutorService
/*     */   implements ExecutorService
/*     */ {
/*     */   protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
/*     */   {
/*  87 */     return new FutureTask(paramRunnable, paramT);
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
/*     */   protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
/*     */   {
/* 102 */     return new FutureTask(paramCallable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Future<?> submit(Runnable paramRunnable)
/*     */   {
/* 110 */     if (paramRunnable == null) throw new NullPointerException();
/* 111 */     RunnableFuture localRunnableFuture = newTaskFor(paramRunnable, null);
/* 112 */     execute(localRunnableFuture);
/* 113 */     return localRunnableFuture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> Future<T> submit(Runnable paramRunnable, T paramT)
/*     */   {
/* 121 */     if (paramRunnable == null) throw new NullPointerException();
/* 122 */     RunnableFuture localRunnableFuture = newTaskFor(paramRunnable, paramT);
/* 123 */     execute(localRunnableFuture);
/* 124 */     return localRunnableFuture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> Future<T> submit(Callable<T> paramCallable)
/*     */   {
/* 132 */     if (paramCallable == null) throw new NullPointerException();
/* 133 */     RunnableFuture localRunnableFuture = newTaskFor(paramCallable);
/* 134 */     execute(localRunnableFuture);
/* 135 */     return localRunnableFuture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private <T> T doInvokeAny(Collection<? extends Callable<T>> paramCollection, boolean paramBoolean, long paramLong)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 144 */     if (paramCollection == null)
/* 145 */       throw new NullPointerException();
/* 146 */     int i = paramCollection.size();
/* 147 */     if (i == 0)
/* 148 */       throw new IllegalArgumentException();
/* 149 */     ArrayList localArrayList = new ArrayList(i);
/* 150 */     ExecutorCompletionService localExecutorCompletionService = new ExecutorCompletionService(this);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 162 */       Object localObject1 = null;
/* 163 */       long l = paramBoolean ? System.nanoTime() + paramLong : 0L;
/* 164 */       Iterator localIterator = paramCollection.iterator();
/*     */       
/*     */ 
/* 167 */       localArrayList.add(localExecutorCompletionService.submit((Callable)localIterator.next()));
/* 168 */       i--;
/* 169 */       int j = 1;
/*     */       for (;;)
/*     */       {
/* 172 */         Future localFuture = localExecutorCompletionService.poll();
/* 173 */         if (localFuture == null)
/* 174 */           if (i > 0) {
/* 175 */             i--;
/* 176 */             localArrayList.add(localExecutorCompletionService.submit((Callable)localIterator.next()));
/* 177 */             j++;
/*     */           } else {
/* 179 */             if (j == 0)
/*     */               break;
/* 181 */             if (paramBoolean) {
/* 182 */               localFuture = localExecutorCompletionService.poll(paramLong, TimeUnit.NANOSECONDS);
/* 183 */               if (localFuture == null)
/* 184 */                 throw new TimeoutException();
/* 185 */               paramLong = l - System.nanoTime();
/*     */             }
/*     */             else {
/* 188 */               localFuture = localExecutorCompletionService.take();
/*     */             } }
/* 190 */         if (localFuture != null) {
/* 191 */           j--;
/*     */           try { int k;
/* 193 */             int m; return (T)localFuture.get();
/*     */           } catch (ExecutionException localExecutionException) {
/* 195 */             localObject1 = localExecutionException;
/*     */           } catch (RuntimeException localRuntimeException) {
/* 197 */             localObject1 = new ExecutionException(localRuntimeException);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 202 */       if (localObject1 == null)
/* 203 */         localObject1 = new ExecutionException();
/* 204 */       throw ((Throwable)localObject1);
/*     */     }
/*     */     finally {
/* 207 */       int n = 0; for (int i1 = localArrayList.size(); n < i1; n++) {
/* 208 */         ((Future)localArrayList.get(n)).cancel(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection) throws InterruptedException, ExecutionException {
/*     */     try {
/* 215 */       return (T)doInvokeAny(paramCollection, false, 0L);
/*     */     } catch (TimeoutException localTimeoutException) {
/* 217 */       if (!$assertionsDisabled) throw new AssertionError(); }
/* 218 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 225 */     return (T)doInvokeAny(paramCollection, true, paramTimeUnit.toNanos(paramLong));
/*     */   }
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection) throws InterruptedException
/*     */   {
/* 230 */     if (paramCollection == null)
/* 231 */       throw new NullPointerException();
/* 232 */     ArrayList localArrayList1 = new ArrayList(paramCollection.size());
/* 233 */     int i = 0;
/*     */     try {
/* 235 */       for (Callable localCallable : paramCollection) {
/* 236 */         localObject1 = newTaskFor(localCallable);
/* 237 */         localArrayList1.add(localObject1);
/* 238 */         execute((Runnable)localObject1); }
/*     */       Object localObject1;
/* 240 */       int j = 0; for (int k = localArrayList1.size(); j < k; j++) {
/* 241 */         localObject1 = (Future)localArrayList1.get(j);
/* 242 */         if (!((Future)localObject1).isDone()) {
/*     */           try {
/* 244 */             ((Future)localObject1).get();
/*     */           }
/*     */           catch (CancellationException localCancellationException) {}catch (ExecutionException localExecutionException) {}
/*     */         }
/*     */       }
/*     */       
/* 250 */       i = 1;
/* 251 */       int m; return localArrayList1;
/*     */     } finally {
/* 253 */       if (i == 0) {
/* 254 */         int n = 0; for (int i1 = localArrayList1.size(); n < i1; n++) {
/* 255 */           ((Future)localArrayList1.get(n)).cancel(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 262 */     if (paramCollection == null)
/* 263 */       throw new NullPointerException();
/* 264 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 265 */     ArrayList localArrayList1 = new ArrayList(paramCollection.size());
/* 266 */     int i = 0;
/*     */     try {
/* 268 */       for (Callable localCallable : paramCollection) {
/* 269 */         localArrayList1.add(newTaskFor(localCallable));
/*     */       }
/* 271 */       long l2 = System.nanoTime() + l1;
/* 272 */       int j = localArrayList1.size();
/*     */       
/*     */       Object localObject1;
/*     */       int i2;
/* 276 */       for (int k = 0; k < j; k++) {
/* 277 */         execute((Runnable)localArrayList1.get(k));
/* 278 */         l1 = l2 - System.nanoTime();
/* 279 */         if (l1 <= 0L) { int n;
/* 280 */           return localArrayList1;
/*     */         }
/*     */       }
/* 283 */       for (k = 0; k < j; k++) {
/* 284 */         localObject1 = (Future)localArrayList1.get(k);
/* 285 */         if (!((Future)localObject1).isDone()) { int i3;
/* 286 */           if (l1 <= 0L)
/* 287 */             return localArrayList1;
/*     */           try {
/* 289 */             ((Future)localObject1).get(l1, TimeUnit.NANOSECONDS);
/*     */           }
/*     */           catch (CancellationException localCancellationException) {}catch (ExecutionException localExecutionException) {}catch (TimeoutException localTimeoutException) {
/*     */             int i4;
/* 293 */             return localArrayList1;
/*     */           }
/* 295 */           l1 = l2 - System.nanoTime();
/*     */         }
/*     */       }
/* 298 */       i = 1;
/* 299 */       int m; int i1; return localArrayList1;
/*     */     } finally {
/* 301 */       if (i == 0) {
/* 302 */         int i5 = 0; for (int i6 = localArrayList1.size(); i5 < i6; i5++) {
/* 303 */           ((Future)localArrayList1.get(i5)).cancel(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/AbstractExecutorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */