/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractTask<P_IN, P_OUT, R, K extends AbstractTask<P_IN, P_OUT, R, K>>
/*     */   extends CountedCompleter<R>
/*     */ {
/*  97 */   static final int LEAF_TARGET = ForkJoinPool.getCommonPoolParallelism() << 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final PipelineHelper<P_OUT> helper;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Spliterator<P_IN> spliterator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected long targetSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected K leftChild;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected K rightChild;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private R localResult;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractTask(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator)
/*     */   {
/* 138 */     super(null);
/* 139 */     this.helper = paramPipelineHelper;
/* 140 */     this.spliterator = paramSpliterator;
/* 141 */     this.targetSize = 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractTask(K paramK, Spliterator<P_IN> paramSpliterator)
/*     */   {
/* 153 */     super(paramK);
/* 154 */     this.spliterator = paramSpliterator;
/* 155 */     this.helper = paramK.helper;
/* 156 */     this.targetSize = paramK.targetSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract K makeChild(Spliterator<P_IN> paramSpliterator);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract R doLeaf();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long suggestTargetSize(long paramLong)
/*     */   {
/* 184 */     long l = paramLong / LEAF_TARGET;
/* 185 */     return l > 0L ? l : 1L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final long getTargetSize(long paramLong)
/*     */   {
/*     */     long l;
/*     */     
/*     */ 
/* 195 */     return (l = this.targetSize) != 0L ? l : (this.targetSize = suggestTargetSize(paramLong));
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
/*     */   public R getRawResult()
/*     */   {
/* 209 */     return (R)this.localResult;
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
/*     */   protected void setRawResult(R paramR)
/*     */   {
/* 222 */     if (paramR != null) {
/* 223 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected R getLocalResult()
/*     */   {
/* 233 */     return (R)this.localResult;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setLocalResult(R paramR)
/*     */   {
/* 243 */     this.localResult = paramR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isLeaf()
/*     */   {
/* 255 */     return this.leftChild == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isRoot()
/*     */   {
/* 264 */     return getParent() == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected K getParent()
/*     */   {
/* 274 */     return (AbstractTask)getCompleter();
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
/*     */   public void compute()
/*     */   {
/* 292 */     Object localObject1 = this.spliterator;
/* 293 */     long l1 = ((Spliterator)localObject1).estimateSize();
/* 294 */     long l2 = getTargetSize(l1);
/* 295 */     int i = 0;
/* 296 */     Object localObject2 = this;
/* 297 */     Spliterator localSpliterator; while ((l1 > l2) && ((localSpliterator = ((Spliterator)localObject1).trySplit()) != null)) {
/*     */       AbstractTask localAbstractTask1;
/* 299 */       ((AbstractTask)localObject2).leftChild = (localAbstractTask1 = ((AbstractTask)localObject2).makeChild(localSpliterator));
/* 300 */       AbstractTask localAbstractTask2; ((AbstractTask)localObject2).rightChild = (localAbstractTask2 = ((AbstractTask)localObject2).makeChild((Spliterator)localObject1));
/* 301 */       ((AbstractTask)localObject2).setPendingCount(1);
/* 302 */       AbstractTask localAbstractTask3; if (i != 0) {
/* 303 */         i = 0;
/* 304 */         localObject1 = localSpliterator;
/* 305 */         localObject2 = localAbstractTask1;
/* 306 */         localAbstractTask3 = localAbstractTask2;
/*     */       }
/*     */       else {
/* 309 */         i = 1;
/* 310 */         localObject2 = localAbstractTask2;
/* 311 */         localAbstractTask3 = localAbstractTask1;
/*     */       }
/* 313 */       localAbstractTask3.fork();
/* 314 */       l1 = ((Spliterator)localObject1).estimateSize();
/*     */     }
/* 316 */     ((AbstractTask)localObject2).setLocalResult(((AbstractTask)localObject2).doLeaf());
/* 317 */     ((AbstractTask)localObject2).tryComplete();
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
/*     */   public void onCompletion(CountedCompleter<?> paramCountedCompleter)
/*     */   {
/* 330 */     this.spliterator = null;
/* 331 */     this.leftChild = (this.rightChild = null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isLeftmostNode()
/*     */   {
/* 343 */     Object localObject = this;
/* 344 */     while (localObject != null) {
/* 345 */       AbstractTask localAbstractTask = ((AbstractTask)localObject).getParent();
/* 346 */       if ((localAbstractTask != null) && (localAbstractTask.leftChild != localObject))
/* 347 */         return false;
/* 348 */       localObject = localAbstractTask;
/*     */     }
/* 350 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/AbstractTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */