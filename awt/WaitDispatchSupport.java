/*     */ package java.awt;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import sun.awt.PeerEvent;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ import sun.util.logging.PlatformLogger.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WaitDispatchSupport
/*     */   implements SecondaryLoop
/*     */ {
/*  51 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.event.WaitDispatchSupport");
/*     */   
/*     */   private EventDispatchThread dispatchThread;
/*     */   
/*     */   private EventFilter filter;
/*     */   
/*     */   private volatile Conditional extCondition;
/*     */   
/*     */   private volatile Conditional condition;
/*     */   
/*     */   private long interval;
/*     */   
/*     */   private static Timer timer;
/*     */   
/*     */   private TimerTask timerTask;
/*  66 */   private AtomicBoolean keepBlockingEDT = new AtomicBoolean(false);
/*  67 */   private AtomicBoolean keepBlockingCT = new AtomicBoolean(false);
/*     */   
/*     */   private static synchronized void initializeTimer() {
/*  70 */     if (timer == null) {
/*  71 */       timer = new Timer("AWT-WaitDispatchSupport-Timer", true);
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
/*     */   public WaitDispatchSupport(EventDispatchThread paramEventDispatchThread)
/*     */   {
/*  85 */     this(paramEventDispatchThread, null);
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
/*     */   public WaitDispatchSupport(EventDispatchThread paramEventDispatchThread, Conditional paramConditional)
/*     */   {
/* 102 */     if (paramEventDispatchThread == null) {
/* 103 */       throw new IllegalArgumentException("The dispatchThread can not be null");
/*     */     }
/*     */     
/* 106 */     this.dispatchThread = paramEventDispatchThread;
/* 107 */     this.extCondition = paramConditional;
/* 108 */     this.condition = new Conditional()
/*     */     {
/*     */       public boolean evaluate() {
/* 111 */         if (WaitDispatchSupport.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 112 */           WaitDispatchSupport.log.finest("evaluate(): blockingEDT=" + WaitDispatchSupport.this.keepBlockingEDT.get() + ", blockingCT=" + 
/* 113 */             WaitDispatchSupport.this.keepBlockingCT.get());
/*     */         }
/*     */         
/* 116 */         int i = WaitDispatchSupport.this.extCondition != null ? WaitDispatchSupport.this.extCondition.evaluate() : 1;
/* 117 */         if ((!WaitDispatchSupport.this.keepBlockingEDT.get()) || (i == 0)) {
/* 118 */           if (WaitDispatchSupport.this.timerTask != null) {
/* 119 */             WaitDispatchSupport.this.timerTask.cancel();
/* 120 */             WaitDispatchSupport.this.timerTask = null;
/*     */           }
/* 122 */           return false;
/*     */         }
/* 124 */         return true;
/*     */       }
/*     */     };
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
/*     */   public WaitDispatchSupport(EventDispatchThread paramEventDispatchThread, Conditional paramConditional, EventFilter paramEventFilter, long paramLong)
/*     */   {
/* 152 */     this(paramEventDispatchThread, paramConditional);
/* 153 */     this.filter = paramEventFilter;
/* 154 */     if (paramLong < 0L) {
/* 155 */       throw new IllegalArgumentException("The interval value must be >= 0");
/*     */     }
/* 157 */     this.interval = paramLong;
/* 158 */     if (paramLong != 0L) {
/* 159 */       initializeTimer();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean enter()
/*     */   {
/* 168 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 169 */       log.fine("enter(): blockingEDT=" + this.keepBlockingEDT.get() + ", blockingCT=" + this.keepBlockingCT
/* 170 */         .get());
/*     */     }
/*     */     
/* 173 */     if (!this.keepBlockingEDT.compareAndSet(false, true)) {
/* 174 */       log.fine("The secondary loop is already running, aborting");
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     final Runnable local2 = new Runnable() {
/*     */       public void run() {
/* 180 */         WaitDispatchSupport.log.fine("Starting a new event pump");
/* 181 */         if (WaitDispatchSupport.this.filter == null) {
/* 182 */           WaitDispatchSupport.this.dispatchThread.pumpEvents(WaitDispatchSupport.this.condition);
/*     */         } else {
/* 184 */           WaitDispatchSupport.this.dispatchThread.pumpEventsForFilter(WaitDispatchSupport.this.condition, WaitDispatchSupport.this.filter);
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       
/*     */ 
/* 192 */     };
/* 193 */     Thread localThread = Thread.currentThread();
/* 194 */     if (localThread == this.dispatchThread) {
/* 195 */       if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 196 */         log.finest("On dispatch thread: " + this.dispatchThread);
/*     */       }
/* 198 */       if (this.interval != 0L) {
/* 199 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 200 */           log.finest("scheduling the timer for " + this.interval + " ms");
/*     */         }
/* 202 */         timer.schedule(this. = new TimerTask()
/*     */         {
/*     */           public void run() {
/* 205 */             if (WaitDispatchSupport.this.keepBlockingEDT.compareAndSet(true, false))
/* 206 */               WaitDispatchSupport.this.wakeupEDT(); } }, this.interval);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 214 */       SequencedEvent localSequencedEvent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getCurrentSequencedEvent();
/* 215 */       if (localSequencedEvent != null) {
/* 216 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 217 */           log.fine("Dispose current SequencedEvent: " + localSequencedEvent);
/*     */         }
/* 219 */         localSequencedEvent.dispose();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Void run() {
/* 229 */           local2.run();
/* 230 */           return null;
/*     */         }
/*     */       });
/*     */     } else {
/* 234 */       if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 235 */         log.finest("On non-dispatch thread: " + localThread);
/*     */       }
/* 237 */       synchronized (getTreeLock()) {
/* 238 */         if (this.filter != null) {
/* 239 */           this.dispatchThread.addEventFilter(this.filter);
/*     */         }
/*     */         try {
/* 242 */           EventQueue localEventQueue = this.dispatchThread.getEventQueue();
/* 243 */           localEventQueue.postEvent(new PeerEvent(this, local2, 1L));
/* 244 */           this.keepBlockingCT.set(true);
/* 245 */           if (this.interval > 0L) {
/* 246 */             long l = System.currentTimeMillis();
/* 247 */             while ((this.keepBlockingCT.get()) && ((this.extCondition == null) || 
/* 248 */               (this.extCondition.evaluate())) && 
/* 249 */               (l + this.interval > System.currentTimeMillis()))
/*     */             {
/* 251 */               getTreeLock().wait(this.interval);
/*     */             }
/*     */           } else {
/* 254 */             while ((this.keepBlockingCT.get()) && ((this.extCondition == null) || 
/* 255 */               (this.extCondition.evaluate())))
/*     */             {
/* 257 */               getTreeLock().wait();
/*     */             }
/*     */           }
/* 260 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 261 */             log.fine("waitDone " + this.keepBlockingEDT.get() + " " + this.keepBlockingCT.get());
/*     */           }
/*     */         } catch (InterruptedException localInterruptedException) {
/* 264 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 265 */             log.fine("Exception caught while waiting: " + localInterruptedException);
/*     */           }
/*     */         } finally {
/* 268 */           if (this.filter != null) {
/* 269 */             this.dispatchThread.removeEventFilter(this.filter);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 275 */         this.keepBlockingEDT.set(false);
/* 276 */         this.keepBlockingCT.set(false);
/*     */       }
/*     */     }
/*     */     
/* 280 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean exit()
/*     */   {
/* 287 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 288 */       log.fine("exit(): blockingEDT=" + this.keepBlockingEDT.get() + ", blockingCT=" + this.keepBlockingCT
/* 289 */         .get());
/*     */     }
/* 291 */     if (this.keepBlockingEDT.compareAndSet(true, false)) {
/* 292 */       wakeupEDT();
/* 293 */       return true;
/*     */     }
/* 295 */     return false;
/*     */   }
/*     */   
/*     */   private static final Object getTreeLock() {
/* 299 */     return Component.LOCK;
/*     */   }
/*     */   
/* 302 */   private final Runnable wakingRunnable = new Runnable() {
/*     */     public void run() {
/* 304 */       WaitDispatchSupport.log.fine("Wake up EDT");
/* 305 */       synchronized (WaitDispatchSupport.access$900()) {
/* 306 */         WaitDispatchSupport.this.keepBlockingCT.set(false);
/* 307 */         WaitDispatchSupport.access$900().notifyAll();
/*     */       }
/* 309 */       WaitDispatchSupport.log.fine("Wake up EDT done");
/*     */     }
/*     */   };
/*     */   
/*     */   private void wakeupEDT() {
/* 314 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 315 */       log.finest("wakeupEDT(): EDT == " + this.dispatchThread);
/*     */     }
/* 317 */     EventQueue localEventQueue = this.dispatchThread.getEventQueue();
/* 318 */     localEventQueue.postEvent(new PeerEvent(this, this.wakingRunnable, 1L));
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/WaitDispatchSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */