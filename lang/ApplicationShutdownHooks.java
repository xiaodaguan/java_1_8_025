/*     */ package java.lang;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ApplicationShutdownHooks
/*     */ {
/*     */   private static IdentityHashMap<Thread, Thread> hooks;
/*     */   
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  42 */       Shutdown.add(1, false, new Runnable()
/*     */       {
/*     */ 
/*     */ 
/*     */         public void run() {}
/*     */ 
/*     */ 
/*  49 */       });
/*  50 */       hooks = new IdentityHashMap();
/*     */     }
/*     */     catch (IllegalStateException localIllegalStateException)
/*     */     {
/*  54 */       hooks = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized void add(Thread paramThread)
/*     */   {
/*  65 */     if (hooks == null) {
/*  66 */       throw new IllegalStateException("Shutdown in progress");
/*     */     }
/*  68 */     if (paramThread.isAlive()) {
/*  69 */       throw new IllegalArgumentException("Hook already running");
/*     */     }
/*  71 */     if (hooks.containsKey(paramThread)) {
/*  72 */       throw new IllegalArgumentException("Hook previously registered");
/*     */     }
/*  74 */     hooks.put(paramThread, paramThread);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static synchronized boolean remove(Thread paramThread)
/*     */   {
/*  81 */     if (hooks == null) {
/*  82 */       throw new IllegalStateException("Shutdown in progress");
/*     */     }
/*  84 */     if (paramThread == null) {
/*  85 */       throw new NullPointerException();
/*     */     }
/*  87 */     return hooks.remove(paramThread) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static void runHooks()
/*     */   {
/*     */     Set localSet;
/*     */     
/*  96 */     synchronized (ApplicationShutdownHooks.class) {
/*  97 */       localSet = hooks.keySet();
/*  98 */       hooks = null;
/*     */     }
/*     */     
/* 101 */     for (??? = localSet.iterator(); ((Iterator)???).hasNext();) { localThread = (Thread)((Iterator)???).next();
/* 102 */       localThread.start(); }
/*     */     Thread localThread;
/* 104 */     for (??? = localSet.iterator(); ((Iterator)???).hasNext();) { localThread = (Thread)((Iterator)???).next();
/*     */       try {
/* 106 */         localThread.join();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ApplicationShutdownHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */