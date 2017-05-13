/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractShortCircuitTask<P_IN, P_OUT, R, K extends AbstractShortCircuitTask<P_IN, P_OUT, R, K>>
/*     */   extends AbstractTask<P_IN, P_OUT, R, K>
/*     */ {
/*     */   protected final AtomicReference<R> sharedResult;
/*     */   protected volatile boolean canceled;
/*     */   
/*     */   protected AbstractShortCircuitTask(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator)
/*     */   {
/*  70 */     super(paramPipelineHelper, paramSpliterator);
/*  71 */     this.sharedResult = new AtomicReference(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractShortCircuitTask(K paramK, Spliterator<P_IN> paramSpliterator)
/*     */   {
/*  83 */     super(paramK, paramSpliterator);
/*  84 */     this.sharedResult = paramK.sharedResult;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract R getEmptyResult();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void compute()
/*     */   {
/* 102 */     Object localObject1 = this.spliterator;
/* 103 */     long l1 = ((Spliterator)localObject1).estimateSize();
/* 104 */     long l2 = getTargetSize(l1);
/* 105 */     int i = 0;
/* 106 */     Object localObject2 = this;
/* 107 */     AtomicReference localAtomicReference = this.sharedResult;
/*     */     Object localObject3;
/* 109 */     while ((localObject3 = localAtomicReference.get()) == null) {
/* 110 */       if (((AbstractShortCircuitTask)localObject2).taskCanceled()) {
/* 111 */         localObject3 = ((AbstractShortCircuitTask)localObject2).getEmptyResult();
/* 112 */         break; }
/*     */       Spliterator localSpliterator;
/* 114 */       if ((l1 <= l2) || ((localSpliterator = ((Spliterator)localObject1).trySplit()) == null)) {
/* 115 */         localObject3 = ((AbstractShortCircuitTask)localObject2).doLeaf();
/* 116 */         break;
/*     */       }
/*     */       AbstractShortCircuitTask localAbstractShortCircuitTask1;
/* 119 */       ((AbstractShortCircuitTask)localObject2).leftChild = (localAbstractShortCircuitTask1 = (AbstractShortCircuitTask)((AbstractShortCircuitTask)localObject2).makeChild(localSpliterator));
/* 120 */       AbstractShortCircuitTask localAbstractShortCircuitTask2; ((AbstractShortCircuitTask)localObject2).rightChild = (localAbstractShortCircuitTask2 = (AbstractShortCircuitTask)((AbstractShortCircuitTask)localObject2).makeChild((Spliterator)localObject1));
/* 121 */       ((AbstractShortCircuitTask)localObject2).setPendingCount(1);
/* 122 */       AbstractShortCircuitTask localAbstractShortCircuitTask3; if (i != 0) {
/* 123 */         i = 0;
/* 124 */         localObject1 = localSpliterator;
/* 125 */         localObject2 = localAbstractShortCircuitTask1;
/* 126 */         localAbstractShortCircuitTask3 = localAbstractShortCircuitTask2;
/*     */       }
/*     */       else {
/* 129 */         i = 1;
/* 130 */         localObject2 = localAbstractShortCircuitTask2;
/* 131 */         localAbstractShortCircuitTask3 = localAbstractShortCircuitTask1;
/*     */       }
/* 133 */       localAbstractShortCircuitTask3.fork();
/* 134 */       l1 = ((Spliterator)localObject1).estimateSize();
/*     */     }
/* 136 */     ((AbstractShortCircuitTask)localObject2).setLocalResult(localObject3);
/* 137 */     ((AbstractShortCircuitTask)localObject2).tryComplete();
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
/*     */   protected void shortCircuit(R paramR)
/*     */   {
/* 151 */     if (paramR != null) {
/* 152 */       this.sharedResult.compareAndSet(null, paramR);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setLocalResult(R paramR)
/*     */   {
/* 163 */     if (isRoot()) {
/* 164 */       if (paramR != null) {
/* 165 */         this.sharedResult.compareAndSet(null, paramR);
/*     */       }
/*     */     } else {
/* 168 */       super.setLocalResult(paramR);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public R getRawResult()
/*     */   {
/* 176 */     return (R)getLocalResult();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public R getLocalResult()
/*     */   {
/* 185 */     if (isRoot()) {
/* 186 */       Object localObject = this.sharedResult.get();
/* 187 */       return (R)(localObject == null ? getEmptyResult() : localObject);
/*     */     }
/*     */     
/* 190 */     return (R)super.getLocalResult();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void cancel()
/*     */   {
/* 197 */     this.canceled = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean taskCanceled()
/*     */   {
/* 207 */     boolean bool = this.canceled;
/* 208 */     if (!bool) {
/* 209 */       for (AbstractShortCircuitTask localAbstractShortCircuitTask = (AbstractShortCircuitTask)getParent(); (!bool) && (localAbstractShortCircuitTask != null); localAbstractShortCircuitTask = (AbstractShortCircuitTask)localAbstractShortCircuitTask.getParent()) {
/* 210 */         bool = localAbstractShortCircuitTask.canceled;
/*     */       }
/*     */     }
/* 213 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void cancelLaterNodes()
/*     */   {
/* 223 */     AbstractShortCircuitTask localAbstractShortCircuitTask1 = (AbstractShortCircuitTask)getParent();AbstractShortCircuitTask localAbstractShortCircuitTask2 = this;
/* 224 */     for (; localAbstractShortCircuitTask1 != null; 
/* 225 */         localAbstractShortCircuitTask1 = (AbstractShortCircuitTask)localAbstractShortCircuitTask1.getParent())
/*     */     {
/* 227 */       if (localAbstractShortCircuitTask1.leftChild == localAbstractShortCircuitTask2) {
/* 228 */         AbstractShortCircuitTask localAbstractShortCircuitTask3 = (AbstractShortCircuitTask)localAbstractShortCircuitTask1.rightChild;
/* 229 */         if (!localAbstractShortCircuitTask3.canceled) {
/* 230 */           localAbstractShortCircuitTask3.cancel();
/*     */         }
/*     */       }
/* 225 */       localAbstractShortCircuitTask2 = localAbstractShortCircuitTask1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/AbstractShortCircuitTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */