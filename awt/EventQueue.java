/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.PaintEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.EmptyStackException;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.EventQueueAccessor;
/*      */ import sun.awt.AWTAccessor.InvocationEventAccessor;
/*      */ import sun.awt.AWTAutoShutdown;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.EventQueueItem;
/*      */ import sun.awt.FwDispatcher;
/*      */ import sun.awt.PeerEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.dnd.SunDropTargetEvent;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ import sun.util.logging.PlatformLogger.Level;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EventQueue
/*      */ {
/*   98 */   private static final AtomicInteger threadInitNumber = new AtomicInteger(0);
/*      */   
/*      */ 
/*      */   private static final int LOW_PRIORITY = 0;
/*      */   
/*      */ 
/*      */   private static final int NORM_PRIORITY = 1;
/*      */   
/*      */ 
/*      */   private static final int HIGH_PRIORITY = 2;
/*      */   
/*      */ 
/*      */   private static final int ULTIMATE_PRIORITY = 3;
/*      */   
/*      */ 
/*      */   private static final int NUM_PRIORITIES = 4;
/*      */   
/*  115 */   private Queue[] queues = new Queue[4];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private EventQueue nextQueue;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private EventQueue previousQueue;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final Lock pushPopLock;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final Condition pushPopCond;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  142 */   private static final Runnable dummyRunnable = new Runnable()
/*      */   {
/*      */     public void run() {}
/*      */   };
/*      */   
/*      */ 
/*      */   private EventDispatchThread dispatchThread;
/*      */   
/*  150 */   private final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/*      */   
/*  152 */   private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  157 */   private long mostRecentEventTime = System.currentTimeMillis();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  162 */   private long mostRecentKeyEventTime = System.currentTimeMillis();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private WeakReference<AWTEvent> currentEvent;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile int waitForID;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final AppContext appContext;
/*      */   
/*      */ 
/*      */ 
/*  181 */   private final String name = "AWT-EventQueue-" + threadInitNumber.getAndIncrement();
/*      */   
/*      */   private FwDispatcher fwDispatcher;
/*      */   
/*  185 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.EventQueue");
/*      */   private static final int PAINT = 0;
/*      */   
/*  188 */   static { AWTAccessor.setEventQueueAccessor(new AWTAccessor.EventQueueAccessor()
/*      */     {
/*      */       public Thread getDispatchThread(EventQueue paramAnonymousEventQueue) {
/*  191 */         return paramAnonymousEventQueue.getDispatchThread();
/*      */       }
/*      */       
/*  194 */       public boolean isDispatchThreadImpl(EventQueue paramAnonymousEventQueue) { return paramAnonymousEventQueue.isDispatchThreadImpl(); }
/*      */       
/*      */ 
/*      */ 
/*      */       public void removeSourceEvents(EventQueue paramAnonymousEventQueue, Object paramAnonymousObject, boolean paramAnonymousBoolean)
/*      */       {
/*  200 */         paramAnonymousEventQueue.removeSourceEvents(paramAnonymousObject, paramAnonymousBoolean);
/*      */       }
/*      */       
/*  203 */       public boolean noEvents(EventQueue paramAnonymousEventQueue) { return paramAnonymousEventQueue.noEvents(); }
/*      */       
/*      */       public void wakeup(EventQueue paramAnonymousEventQueue, boolean paramAnonymousBoolean) {
/*  206 */         paramAnonymousEventQueue.wakeup(paramAnonymousBoolean);
/*      */       }
/*      */       
/*      */       public void invokeAndWait(Object paramAnonymousObject, Runnable paramAnonymousRunnable) throws InterruptedException, InvocationTargetException
/*      */       {
/*  211 */         EventQueue.invokeAndWait(paramAnonymousObject, paramAnonymousRunnable);
/*      */       }
/*      */       
/*      */       public void setFwDispatcher(EventQueue paramAnonymousEventQueue, FwDispatcher paramAnonymousFwDispatcher) {
/*  215 */         paramAnonymousEventQueue.setFwDispatcher(paramAnonymousFwDispatcher);
/*      */       }
/*      */     }); }
/*      */   
/*      */   public EventQueue()
/*      */   {
/*  221 */     for (int i = 0; i < 4; i++) {
/*  222 */       this.queues[i] = new Queue();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  233 */     this.appContext = AppContext.getAppContext();
/*  234 */     this.pushPopLock = ((Lock)this.appContext.get(AppContext.EVENT_QUEUE_LOCK_KEY));
/*  235 */     this.pushPopCond = ((Condition)this.appContext.get(AppContext.EVENT_QUEUE_COND_KEY));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int UPDATE = 1;
/*      */   
/*      */ 
/*      */   private static final int MOVE = 2;
/*      */   
/*      */ 
/*      */   public void postEvent(AWTEvent paramAWTEvent)
/*      */   {
/*  249 */     SunToolkit.flushPendingEvents(this.appContext);
/*  250 */     postEventPrivate(paramAWTEvent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void postEventPrivate(AWTEvent paramAWTEvent)
/*      */   {
/*  263 */     paramAWTEvent.isPosted = true;
/*  264 */     this.pushPopLock.lock();
/*      */     try {
/*  266 */       if (this.nextQueue != null)
/*      */       {
/*  268 */         this.nextQueue.postEventPrivate(paramAWTEvent);
/*  269 */         return;
/*      */       }
/*  271 */       if (this.dispatchThread == null) {
/*  272 */         if (paramAWTEvent.getSource() == AWTAutoShutdown.getInstance()) {
/*  273 */           return;
/*      */         }
/*  275 */         initDispatchThread();
/*      */       }
/*      */       
/*  278 */       postEvent(paramAWTEvent, getPriority(paramAWTEvent));
/*      */     } finally {
/*  280 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   private static int getPriority(AWTEvent paramAWTEvent) {
/*  285 */     if ((paramAWTEvent instanceof PeerEvent)) {
/*  286 */       PeerEvent localPeerEvent = (PeerEvent)paramAWTEvent;
/*  287 */       if ((localPeerEvent.getFlags() & 0x2) != 0L) {
/*  288 */         return 3;
/*      */       }
/*  290 */       if ((localPeerEvent.getFlags() & 1L) != 0L) {
/*  291 */         return 2;
/*      */       }
/*  293 */       if ((localPeerEvent.getFlags() & 0x4) != 0L) {
/*  294 */         return 0;
/*      */       }
/*      */     }
/*  297 */     int i = paramAWTEvent.getID();
/*  298 */     if ((i >= 800) && (i <= 801)) {
/*  299 */       return 0;
/*      */     }
/*  301 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void postEvent(AWTEvent paramAWTEvent, int paramInt)
/*      */   {
/*  313 */     if (coalesceEvent(paramAWTEvent, paramInt)) {
/*  314 */       return;
/*      */     }
/*      */     
/*  317 */     EventQueueItem localEventQueueItem = new EventQueueItem(paramAWTEvent);
/*      */     
/*  319 */     cacheEQItem(localEventQueueItem);
/*      */     
/*  321 */     int i = paramAWTEvent.getID() == this.waitForID ? 1 : 0;
/*      */     
/*  323 */     if (this.queues[paramInt].head == null) {
/*  324 */       boolean bool = noEvents();
/*  325 */       this.queues[paramInt].head = (this.queues[paramInt].tail = localEventQueueItem);
/*      */       
/*  327 */       if (bool) {
/*  328 */         if (paramAWTEvent.getSource() != AWTAutoShutdown.getInstance()) {
/*  329 */           AWTAutoShutdown.getInstance().notifyThreadBusy(this.dispatchThread);
/*      */         }
/*  331 */         this.pushPopCond.signalAll();
/*  332 */       } else if (i != 0) {
/*  333 */         this.pushPopCond.signalAll();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  338 */       this.queues[paramInt].tail.next = localEventQueueItem;
/*  339 */       this.queues[paramInt].tail = localEventQueueItem;
/*  340 */       if (i != 0) {
/*  341 */         this.pushPopCond.signalAll();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean coalescePaintEvent(PaintEvent paramPaintEvent) {
/*  347 */     ComponentPeer localComponentPeer = ((Component)paramPaintEvent.getSource()).peer;
/*  348 */     if (localComponentPeer != null) {
/*  349 */       localComponentPeer.coalescePaintEvent(paramPaintEvent);
/*      */     }
/*  351 */     EventQueueItem[] arrayOfEventQueueItem = ((Component)paramPaintEvent.getSource()).eventCache;
/*  352 */     if (arrayOfEventQueueItem == null) {
/*  353 */       return false;
/*      */     }
/*  355 */     int i = eventToCacheIndex(paramPaintEvent);
/*      */     
/*  357 */     if ((i != -1) && (arrayOfEventQueueItem[i] != null)) {
/*  358 */       PaintEvent localPaintEvent = mergePaintEvents(paramPaintEvent, (PaintEvent)arrayOfEventQueueItem[i].event);
/*  359 */       if (localPaintEvent != null) {
/*  360 */         arrayOfEventQueueItem[i].event = localPaintEvent;
/*  361 */         return true;
/*      */       }
/*      */     }
/*  364 */     return false;
/*      */   }
/*      */   
/*      */   private PaintEvent mergePaintEvents(PaintEvent paramPaintEvent1, PaintEvent paramPaintEvent2) {
/*  368 */     Rectangle localRectangle1 = paramPaintEvent1.getUpdateRect();
/*  369 */     Rectangle localRectangle2 = paramPaintEvent2.getUpdateRect();
/*  370 */     if (localRectangle2.contains(localRectangle1)) {
/*  371 */       return paramPaintEvent2;
/*      */     }
/*  373 */     if (localRectangle1.contains(localRectangle2)) {
/*  374 */       return paramPaintEvent1;
/*      */     }
/*  376 */     return null;
/*      */   }
/*      */   
/*      */   private boolean coalesceMouseEvent(MouseEvent paramMouseEvent) {
/*  380 */     EventQueueItem[] arrayOfEventQueueItem = ((Component)paramMouseEvent.getSource()).eventCache;
/*  381 */     if (arrayOfEventQueueItem == null) {
/*  382 */       return false;
/*      */     }
/*  384 */     int i = eventToCacheIndex(paramMouseEvent);
/*  385 */     if ((i != -1) && (arrayOfEventQueueItem[i] != null)) {
/*  386 */       arrayOfEventQueueItem[i].event = paramMouseEvent;
/*  387 */       return true;
/*      */     }
/*  389 */     return false;
/*      */   }
/*      */   
/*      */   private boolean coalescePeerEvent(PeerEvent paramPeerEvent) {
/*  393 */     EventQueueItem[] arrayOfEventQueueItem = ((Component)paramPeerEvent.getSource()).eventCache;
/*  394 */     if (arrayOfEventQueueItem == null) {
/*  395 */       return false;
/*      */     }
/*  397 */     int i = eventToCacheIndex(paramPeerEvent);
/*  398 */     if ((i != -1) && (arrayOfEventQueueItem[i] != null)) {
/*  399 */       paramPeerEvent = paramPeerEvent.coalesceEvents((PeerEvent)arrayOfEventQueueItem[i].event);
/*  400 */       if (paramPeerEvent != null) {
/*  401 */         arrayOfEventQueueItem[i].event = paramPeerEvent;
/*  402 */         return true;
/*      */       }
/*  404 */       arrayOfEventQueueItem[i] = null;
/*      */     }
/*      */     
/*  407 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean coalesceOtherEvent(AWTEvent paramAWTEvent, int paramInt)
/*      */   {
/*  418 */     int i = paramAWTEvent.getID();
/*  419 */     Component localComponent = (Component)paramAWTEvent.getSource();
/*  420 */     for (EventQueueItem localEventQueueItem = this.queues[paramInt].head; 
/*  421 */         localEventQueueItem != null; localEventQueueItem = localEventQueueItem.next)
/*      */     {
/*      */ 
/*  424 */       if ((localEventQueueItem.event.getSource() == localComponent) && (localEventQueueItem.event.getID() == i)) {
/*  425 */         AWTEvent localAWTEvent = localComponent.coalesceEvents(localEventQueueItem.event, paramAWTEvent);
/*      */         
/*  427 */         if (localAWTEvent != null) {
/*  428 */           localEventQueueItem.event = localAWTEvent;
/*  429 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*  433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean coalesceEvent(AWTEvent paramAWTEvent, int paramInt) {
/*  437 */     if (!(paramAWTEvent.getSource() instanceof Component)) {
/*  438 */       return false;
/*      */     }
/*  440 */     if ((paramAWTEvent instanceof PeerEvent)) {
/*  441 */       return coalescePeerEvent((PeerEvent)paramAWTEvent);
/*      */     }
/*      */     
/*  444 */     if ((((Component)paramAWTEvent.getSource()).isCoalescingEnabled()) && 
/*  445 */       (coalesceOtherEvent(paramAWTEvent, paramInt)))
/*      */     {
/*  447 */       return true;
/*      */     }
/*  449 */     if ((paramAWTEvent instanceof PaintEvent)) {
/*  450 */       return coalescePaintEvent((PaintEvent)paramAWTEvent);
/*      */     }
/*  452 */     if ((paramAWTEvent instanceof MouseEvent)) {
/*  453 */       return coalesceMouseEvent((MouseEvent)paramAWTEvent);
/*      */     }
/*  455 */     return false;
/*      */   }
/*      */   
/*      */   private void cacheEQItem(EventQueueItem paramEventQueueItem) {
/*  459 */     int i = eventToCacheIndex(paramEventQueueItem.event);
/*  460 */     if ((i != -1) && ((paramEventQueueItem.event.getSource() instanceof Component))) {
/*  461 */       Component localComponent = (Component)paramEventQueueItem.event.getSource();
/*  462 */       if (localComponent.eventCache == null) {
/*  463 */         localComponent.eventCache = new EventQueueItem[5];
/*      */       }
/*  465 */       localComponent.eventCache[i] = paramEventQueueItem;
/*      */     }
/*      */   }
/*      */   
/*      */   private void uncacheEQItem(EventQueueItem paramEventQueueItem) {
/*  470 */     int i = eventToCacheIndex(paramEventQueueItem.event);
/*  471 */     if ((i != -1) && ((paramEventQueueItem.event.getSource() instanceof Component))) {
/*  472 */       Component localComponent = (Component)paramEventQueueItem.event.getSource();
/*  473 */       if (localComponent.eventCache == null) {
/*  474 */         return;
/*      */       }
/*  476 */       localComponent.eventCache[i] = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int eventToCacheIndex(AWTEvent paramAWTEvent)
/*      */   {
/*  488 */     switch (paramAWTEvent.getID()) {
/*      */     case 800: 
/*  490 */       return 0;
/*      */     case 801: 
/*  492 */       return 1;
/*      */     case 503: 
/*  494 */       return 2;
/*      */     
/*      */ 
/*      */     case 506: 
/*  498 */       return (paramAWTEvent instanceof SunDropTargetEvent) ? -1 : 3;
/*      */     }
/*  500 */     return (paramAWTEvent instanceof PeerEvent) ? 4 : -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean noEvents()
/*      */   {
/*  510 */     for (int i = 0; i < 4; i++) {
/*  511 */       if (this.queues[i].head != null) {
/*  512 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  516 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final int DRAG = 3;
/*      */   
/*      */ 
/*      */   private static final int PEER = 4;
/*      */   
/*      */ 
/*      */   private static final int CACHE_LENGTH = 5;
/*      */   
/*      */   public AWTEvent getNextEvent()
/*      */     throws InterruptedException
/*      */   {
/*      */     for (;;)
/*      */     {
/*  534 */       SunToolkit.flushPendingEvents(this.appContext);
/*  535 */       this.pushPopLock.lock();
/*      */       try {
/*  537 */         AWTEvent localAWTEvent1 = getNextEventPrivate();
/*  538 */         if (localAWTEvent1 != null) {
/*  539 */           return localAWTEvent1;
/*      */         }
/*  541 */         AWTAutoShutdown.getInstance().notifyThreadFree(this.dispatchThread);
/*  542 */         this.pushPopCond.await();
/*      */       } finally {
/*  544 */         this.pushPopLock.unlock();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   AWTEvent getNextEventPrivate()
/*      */     throws InterruptedException
/*      */   {
/*  553 */     for (int i = 3; i >= 0; i--) {
/*  554 */       if (this.queues[i].head != null) {
/*  555 */         EventQueueItem localEventQueueItem = this.queues[i].head;
/*  556 */         this.queues[i].head = localEventQueueItem.next;
/*  557 */         if (localEventQueueItem.next == null) {
/*  558 */           this.queues[i].tail = null;
/*      */         }
/*  560 */         uncacheEQItem(localEventQueueItem);
/*  561 */         return localEventQueueItem.event;
/*      */       }
/*      */     }
/*  564 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   AWTEvent getNextEvent(int paramInt)
/*      */     throws InterruptedException
/*      */   {
/*      */     for (;;)
/*      */     {
/*  574 */       SunToolkit.flushPendingEvents(this.appContext);
/*  575 */       this.pushPopLock.lock();
/*      */       try {
/*  577 */         for (int i = 0; i < 4; i++) {
/*  578 */           EventQueueItem localEventQueueItem1 = this.queues[i].head;EventQueueItem localEventQueueItem2 = null;
/*  579 */           for (; localEventQueueItem1 != null; localEventQueueItem1 = localEventQueueItem1.next)
/*      */           {
/*  581 */             if (localEventQueueItem1.event.getID() == paramInt) {
/*  582 */               if (localEventQueueItem2 == null) {
/*  583 */                 this.queues[i].head = localEventQueueItem1.next;
/*      */               } else {
/*  585 */                 localEventQueueItem2.next = localEventQueueItem1.next;
/*      */               }
/*  587 */               if (this.queues[i].tail == localEventQueueItem1) {
/*  588 */                 this.queues[i].tail = localEventQueueItem2;
/*      */               }
/*  590 */               uncacheEQItem(localEventQueueItem1);
/*  591 */               return localEventQueueItem1.event;
/*      */             }
/*  579 */             localEventQueueItem2 = localEventQueueItem1;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  595 */         this.waitForID = paramInt;
/*  596 */         this.pushPopCond.await();
/*  597 */         this.waitForID = 0;
/*      */       } finally {
/*  599 */         this.pushPopLock.unlock();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AWTEvent peekEvent()
/*      */   {
/*  610 */     this.pushPopLock.lock();
/*      */     try {
/*  612 */       for (int i = 3; i >= 0; i--) {
/*  613 */         if (this.queues[i].head != null) {
/*  614 */           return this.queues[i].head.event;
/*      */         }
/*      */       }
/*      */     } finally {
/*  618 */       this.pushPopLock.unlock();
/*      */     }
/*      */     
/*  621 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AWTEvent peekEvent(int paramInt)
/*      */   {
/*  631 */     this.pushPopLock.lock();
/*      */     try {
/*  633 */       for (int i = 3; i >= 0; i--) {
/*  634 */         for (EventQueueItem localEventQueueItem = this.queues[i].head; 
/*  635 */             localEventQueueItem != null; localEventQueueItem = localEventQueueItem.next) {
/*  636 */           if (localEventQueueItem.event.getID() == paramInt) {
/*  637 */             return localEventQueueItem.event;
/*      */           }
/*      */         }
/*      */       }
/*      */     } finally {
/*  642 */       this.pushPopLock.unlock();
/*      */     }
/*      */     
/*  645 */     return null;
/*      */   }
/*      */   
/*      */ 
/*  649 */   private static final JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void dispatchEvent(final AWTEvent paramAWTEvent)
/*      */   {
/*  690 */     final Object localObject = paramAWTEvent.getSource();
/*  691 */     final PrivilegedAction local3 = new PrivilegedAction()
/*      */     {
/*      */ 
/*      */       public Void run()
/*      */       {
/*  696 */         if ((EventQueue.this.fwDispatcher == null) || (EventQueue.this.isDispatchThreadImpl())) {
/*  697 */           EventQueue.this.dispatchEventImpl(paramAWTEvent, localObject);
/*      */         } else {
/*  699 */           EventQueue.this.fwDispatcher.scheduleDispatch(new Runnable()
/*      */           {
/*      */             public void run() {
/*  702 */               EventQueue.this.dispatchEventImpl(EventQueue.3.this.val$event, EventQueue.3.this.val$src);
/*      */             }
/*      */           });
/*      */         }
/*  706 */         return null;
/*      */       }
/*      */       
/*  709 */     };
/*  710 */     AccessControlContext localAccessControlContext1 = AccessController.getContext();
/*  711 */     AccessControlContext localAccessControlContext2 = getAccessControlContextFrom(localObject);
/*  712 */     final AccessControlContext localAccessControlContext3 = paramAWTEvent.getAccessControlContext();
/*  713 */     if (localAccessControlContext2 == null) {
/*  714 */       javaSecurityAccess.doIntersectionPrivilege(local3, localAccessControlContext1, localAccessControlContext3);
/*      */     } else {
/*  716 */       javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*  719 */           EventQueue.javaSecurityAccess.doIntersectionPrivilege(local3, localAccessControlContext3);
/*  720 */           return null; } }, localAccessControlContext1, localAccessControlContext2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static AccessControlContext getAccessControlContextFrom(Object paramObject)
/*      */   {
/*  732 */     return (paramObject instanceof TrayIcon) ? ((TrayIcon)paramObject).getAccessControlContext() : (paramObject instanceof MenuComponent) ? ((MenuComponent)paramObject).getAccessControlContext() : (paramObject instanceof Component) ? ((Component)paramObject).getAccessControlContext() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void dispatchEventImpl(AWTEvent paramAWTEvent, Object paramObject)
/*      */   {
/*  740 */     paramAWTEvent.isPosted = true;
/*  741 */     if ((paramAWTEvent instanceof ActiveEvent))
/*      */     {
/*  743 */       setCurrentEventAndMostRecentTimeImpl(paramAWTEvent);
/*  744 */       ((ActiveEvent)paramAWTEvent).dispatch();
/*  745 */     } else if ((paramObject instanceof Component)) {
/*  746 */       ((Component)paramObject).dispatchEvent(paramAWTEvent);
/*  747 */       paramAWTEvent.dispatched();
/*  748 */     } else if ((paramObject instanceof MenuComponent)) {
/*  749 */       ((MenuComponent)paramObject).dispatchEvent(paramAWTEvent);
/*  750 */     } else if ((paramObject instanceof TrayIcon)) {
/*  751 */       ((TrayIcon)paramObject).dispatchEvent(paramAWTEvent);
/*  752 */     } else if ((paramObject instanceof AWTAutoShutdown)) {
/*  753 */       if (noEvents()) {
/*  754 */         this.dispatchThread.stopDispatching();
/*      */       }
/*      */     }
/*  757 */     else if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  758 */       eventLog.fine("Unable to dispatch event: " + paramAWTEvent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  792 */   public static long getMostRecentEventTime() { return Toolkit.getEventQueue().getMostRecentEventTimeImpl(); }
/*      */   
/*      */   private long getMostRecentEventTimeImpl() {
/*  795 */     this.pushPopLock.lock();
/*      */     
/*      */     try
/*      */     {
/*  799 */       return Thread.currentThread() == this.dispatchThread ? this.mostRecentEventTime : System.currentTimeMillis();
/*      */     } finally {
/*  801 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   long getMostRecentEventTimeEx()
/*      */   {
/*  809 */     this.pushPopLock.lock();
/*      */     try {
/*  811 */       return this.mostRecentEventTime;
/*      */     } finally {
/*  813 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  830 */   public static AWTEvent getCurrentEvent() { return Toolkit.getEventQueue().getCurrentEventImpl(); }
/*      */   
/*      */   private AWTEvent getCurrentEventImpl() {
/*  833 */     this.pushPopLock.lock();
/*      */     try
/*      */     {
/*  836 */       return Thread.currentThread() == this.dispatchThread ? (AWTEvent)this.currentEvent.get() : null;
/*      */     }
/*      */     finally {
/*  839 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void push(EventQueue paramEventQueue)
/*      */   {
/*  855 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  856 */       eventLog.fine("EventQueue.push(" + paramEventQueue + ")");
/*      */     }
/*      */     
/*  859 */     this.pushPopLock.lock();
/*      */     try {
/*  861 */       EventQueue localEventQueue = this;
/*  862 */       while (localEventQueue.nextQueue != null) {
/*  863 */         localEventQueue = localEventQueue.nextQueue;
/*      */       }
/*  865 */       if (localEventQueue.fwDispatcher != null) {
/*  866 */         throw new RuntimeException("push() to queue with fwDispatcher");
/*      */       }
/*  868 */       if ((localEventQueue.dispatchThread != null) && 
/*  869 */         (localEventQueue.dispatchThread.getEventQueue() == this))
/*      */       {
/*  871 */         paramEventQueue.dispatchThread = localEventQueue.dispatchThread;
/*  872 */         localEventQueue.dispatchThread.setEventQueue(paramEventQueue);
/*      */       }
/*      */       
/*      */ 
/*  876 */       while (localEventQueue.peekEvent() != null) {
/*      */         try
/*      */         {
/*  879 */           paramEventQueue.postEventPrivate(localEventQueue.getNextEventPrivate());
/*      */         } catch (InterruptedException localInterruptedException) {
/*  881 */           if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  882 */             eventLog.fine("Interrupted push", localInterruptedException);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  891 */       localEventQueue.postEventPrivate(new InvocationEvent(localEventQueue, dummyRunnable));
/*      */       
/*  893 */       paramEventQueue.previousQueue = localEventQueue;
/*  894 */       localEventQueue.nextQueue = paramEventQueue;
/*      */       
/*  896 */       if (this.appContext.get(AppContext.EVENT_QUEUE_KEY) == localEventQueue) {
/*  897 */         this.appContext.put(AppContext.EVENT_QUEUE_KEY, paramEventQueue);
/*      */       }
/*      */       
/*  900 */       this.pushPopCond.signalAll();
/*      */     } finally {
/*  902 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void pop()
/*      */     throws EmptyStackException
/*      */   {
/*  920 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  921 */       eventLog.fine("EventQueue.pop(" + this + ")");
/*      */     }
/*      */     
/*  924 */     this.pushPopLock.lock();
/*      */     try {
/*  926 */       EventQueue localEventQueue1 = this;
/*  927 */       while (localEventQueue1.nextQueue != null) {
/*  928 */         localEventQueue1 = localEventQueue1.nextQueue;
/*      */       }
/*  930 */       EventQueue localEventQueue2 = localEventQueue1.previousQueue;
/*  931 */       if (localEventQueue2 == null) {
/*  932 */         throw new EmptyStackException();
/*      */       }
/*      */       
/*  935 */       localEventQueue1.previousQueue = null;
/*  936 */       localEventQueue2.nextQueue = null;
/*      */       
/*      */ 
/*  939 */       while (localEventQueue1.peekEvent() != null) {
/*      */         try {
/*  941 */           localEventQueue2.postEventPrivate(localEventQueue1.getNextEventPrivate());
/*      */         } catch (InterruptedException localInterruptedException) {
/*  943 */           if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  944 */             eventLog.fine("Interrupted pop", localInterruptedException);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  949 */       if ((localEventQueue1.dispatchThread != null) && 
/*  950 */         (localEventQueue1.dispatchThread.getEventQueue() == this))
/*      */       {
/*  952 */         localEventQueue2.dispatchThread = localEventQueue1.dispatchThread;
/*  953 */         localEventQueue1.dispatchThread.setEventQueue(localEventQueue2);
/*      */       }
/*      */       
/*  956 */       if (this.appContext.get(AppContext.EVENT_QUEUE_KEY) == this) {
/*  957 */         this.appContext.put(AppContext.EVENT_QUEUE_KEY, localEventQueue2);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  962 */       localEventQueue1.postEventPrivate(new InvocationEvent(localEventQueue1, dummyRunnable));
/*      */       
/*  964 */       this.pushPopCond.signalAll();
/*      */     } finally {
/*  966 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SecondaryLoop createSecondaryLoop()
/*      */   {
/*  986 */     return createSecondaryLoop(null, null, 0L);
/*      */   }
/*      */   
/*      */   SecondaryLoop createSecondaryLoop(Conditional paramConditional, EventFilter paramEventFilter, long paramLong) {
/*  990 */     this.pushPopLock.lock();
/*      */     try { Object localObject1;
/*  992 */       if (this.nextQueue != null)
/*      */       {
/*  994 */         return this.nextQueue.createSecondaryLoop(paramConditional, paramEventFilter, paramLong);
/*      */       }
/*  996 */       if (this.fwDispatcher != null) {
/*  997 */         return this.fwDispatcher.createSecondaryLoop();
/*      */       }
/*  999 */       if (this.dispatchThread == null) {
/* 1000 */         initDispatchThread();
/*      */       }
/* 1002 */       return new WaitDispatchSupport(this.dispatchThread, paramConditional, paramEventFilter, paramLong);
/*      */     } finally {
/* 1004 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isDispatchThread()
/*      */   {
/* 1029 */     EventQueue localEventQueue = Toolkit.getEventQueue();
/* 1030 */     return localEventQueue.isDispatchThreadImpl();
/*      */   }
/*      */   
/*      */   final boolean isDispatchThreadImpl() {
/* 1034 */     Object localObject1 = this;
/* 1035 */     this.pushPopLock.lock();
/*      */     try {
/* 1037 */       EventQueue localEventQueue = ((EventQueue)localObject1).nextQueue;
/* 1038 */       while (localEventQueue != null) {
/* 1039 */         localObject1 = localEventQueue;
/* 1040 */         localEventQueue = ((EventQueue)localObject1).nextQueue; }
/*      */       boolean bool;
/* 1042 */       if (((EventQueue)localObject1).fwDispatcher != null) {
/* 1043 */         return ((EventQueue)localObject1).fwDispatcher.isDispatchThread();
/*      */       }
/* 1045 */       return Thread.currentThread() == ((EventQueue)localObject1).dispatchThread;
/*      */     } finally {
/* 1047 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final EventDispatchThread getDispatchThread()
/*      */   {
/* 1118 */     this.pushPopLock.lock();
/*      */     try {
/* 1120 */       return this.dispatchThread;
/*      */     } finally {
/* 1122 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void removeSourceEvents(Object paramObject, boolean paramBoolean)
/*      */   {
/* 1139 */     SunToolkit.flushPendingEvents(this.appContext);
/* 1140 */     this.pushPopLock.lock();
/*      */     try {
/* 1142 */       for (int i = 0; i < 4; i++) {
/* 1143 */         EventQueueItem localEventQueueItem1 = this.queues[i].head;
/* 1144 */         EventQueueItem localEventQueueItem2 = null;
/* 1145 */         while (localEventQueueItem1 != null) {
/* 1146 */           if ((localEventQueueItem1.event.getSource() == paramObject) && ((paramBoolean) || ((!(localEventQueueItem1.event instanceof SequencedEvent)) && (!(localEventQueueItem1.event instanceof SentEvent)) && (!(localEventQueueItem1.event instanceof FocusEvent)) && (!(localEventQueueItem1.event instanceof WindowEvent)) && (!(localEventQueueItem1.event instanceof KeyEvent)) && (!(localEventQueueItem1.event instanceof InputMethodEvent)))))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1155 */             if ((localEventQueueItem1.event instanceof SequencedEvent)) {
/* 1156 */               ((SequencedEvent)localEventQueueItem1.event).dispose();
/*      */             }
/* 1158 */             if ((localEventQueueItem1.event instanceof SentEvent)) {
/* 1159 */               ((SentEvent)localEventQueueItem1.event).dispose();
/*      */             }
/* 1161 */             if ((localEventQueueItem1.event instanceof InvocationEvent))
/*      */             {
/* 1163 */               AWTAccessor.getInvocationEventAccessor().dispose((InvocationEvent)localEventQueueItem1.event);
/*      */             }
/* 1165 */             if (localEventQueueItem2 == null) {
/* 1166 */               this.queues[i].head = localEventQueueItem1.next;
/*      */             } else {
/* 1168 */               localEventQueueItem2.next = localEventQueueItem1.next;
/*      */             }
/* 1170 */             uncacheEQItem(localEventQueueItem1);
/*      */           } else {
/* 1172 */             localEventQueueItem2 = localEventQueueItem1;
/*      */           }
/* 1174 */           localEventQueueItem1 = localEventQueueItem1.next;
/*      */         }
/* 1176 */         this.queues[i].tail = localEventQueueItem2;
/*      */       }
/*      */     } finally {
/* 1179 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */   synchronized long getMostRecentKeyEventTime() {
/* 1184 */     this.pushPopLock.lock();
/*      */     try {
/* 1186 */       return this.mostRecentKeyEventTime;
/*      */     } finally {
/* 1188 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/* 1193 */   static void setCurrentEventAndMostRecentTime(AWTEvent paramAWTEvent) { Toolkit.getEventQueue().setCurrentEventAndMostRecentTimeImpl(paramAWTEvent); }
/*      */   
/*      */   private void setCurrentEventAndMostRecentTimeImpl(AWTEvent paramAWTEvent) {
/* 1196 */     this.pushPopLock.lock();
/*      */     try {
/* 1198 */       if (Thread.currentThread() != this.dispatchThread) {
/* 1199 */         return;
/*      */       }
/*      */       
/* 1202 */       this.currentEvent = new WeakReference(paramAWTEvent);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1212 */       long l = Long.MIN_VALUE;
/* 1213 */       Object localObject1; if ((paramAWTEvent instanceof InputEvent)) {
/* 1214 */         localObject1 = (InputEvent)paramAWTEvent;
/* 1215 */         l = ((InputEvent)localObject1).getWhen();
/* 1216 */         if ((paramAWTEvent instanceof KeyEvent)) {
/* 1217 */           this.mostRecentKeyEventTime = ((InputEvent)localObject1).getWhen();
/*      */         }
/* 1219 */       } else if ((paramAWTEvent instanceof InputMethodEvent)) {
/* 1220 */         localObject1 = (InputMethodEvent)paramAWTEvent;
/* 1221 */         l = ((InputMethodEvent)localObject1).getWhen();
/* 1222 */       } else if ((paramAWTEvent instanceof ActionEvent)) {
/* 1223 */         localObject1 = (ActionEvent)paramAWTEvent;
/* 1224 */         l = ((ActionEvent)localObject1).getWhen();
/* 1225 */       } else if ((paramAWTEvent instanceof InvocationEvent)) {
/* 1226 */         localObject1 = (InvocationEvent)paramAWTEvent;
/* 1227 */         l = ((InvocationEvent)localObject1).getWhen();
/*      */       }
/* 1229 */       this.mostRecentEventTime = Math.max(this.mostRecentEventTime, l);
/*      */     } finally {
/* 1231 */       this.pushPopLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void invokeLater(Runnable paramRunnable)
/*      */   {
/* 1252 */     Toolkit.getEventQueue().postEvent(new InvocationEvent(
/* 1253 */       Toolkit.getDefaultToolkit(), paramRunnable));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void invokeAndWait(Runnable paramRunnable)
/*      */     throws InterruptedException, InvocationTargetException
/*      */   {
/* 1282 */     invokeAndWait(Toolkit.getDefaultToolkit(), paramRunnable);
/*      */   }
/*      */   
/*      */   static void invokeAndWait(Object paramObject, Runnable paramRunnable)
/*      */     throws InterruptedException, InvocationTargetException
/*      */   {
/* 1288 */     if (isDispatchThread()) {
/* 1289 */       throw new Error("Cannot call invokeAndWait from the event dispatcher thread");
/*      */     }
/*      */     
/*      */ 
/* 1293 */     Object local1AWTInvocationLock = new Object() {};
/* 1295 */     InvocationEvent localInvocationEvent = new InvocationEvent(paramObject, paramRunnable, local1AWTInvocationLock, true);
/*      */     
/*      */ 
/* 1298 */     synchronized (local1AWTInvocationLock) {
/* 1299 */       Toolkit.getEventQueue().postEvent(localInvocationEvent);
/* 1300 */       while (!localInvocationEvent.isDispatched()) {
/* 1301 */         local1AWTInvocationLock.wait();
/*      */       }
/*      */     }
/*      */     
/* 1305 */     ??? = localInvocationEvent.getThrowable();
/* 1306 */     if (??? != null) {
/* 1307 */       throw new InvocationTargetException((Throwable)???);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setFwDispatcher(FwDispatcher paramFwDispatcher)
/*      */   {
/* 1335 */     if (this.nextQueue != null) {
/* 1336 */       this.nextQueue.setFwDispatcher(paramFwDispatcher);
/*      */     } else {
/* 1338 */       this.fwDispatcher = paramFwDispatcher;
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   final void initDispatchThread()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   4: invokeinterface 41 1 0
/*      */     //   9: aload_0
/*      */     //   10: getfield 44	java/awt/EventQueue:dispatchThread	Ljava/awt/EventDispatchThread;
/*      */     //   13: ifnonnull +48 -> 61
/*      */     //   16: aload_0
/*      */     //   17: getfield 3	java/awt/EventQueue:threadGroup	Ljava/lang/ThreadGroup;
/*      */     //   20: invokevirtual 154	java/lang/ThreadGroup:isDestroyed	()Z
/*      */     //   23: ifne +38 -> 61
/*      */     //   26: aload_0
/*      */     //   27: getfield 30	java/awt/EventQueue:appContext	Lsun/awt/AppContext;
/*      */     //   30: invokevirtual 155	sun/awt/AppContext:isDisposed	()Z
/*      */     //   33: ifne +28 -> 61
/*      */     //   36: aload_0
/*      */     //   37: new 156	java/awt/EventQueue$5
/*      */     //   40: dup
/*      */     //   41: aload_0
/*      */     //   42: invokespecial 157	java/awt/EventQueue$5:<init>	(Ljava/awt/EventQueue;)V
/*      */     //   45: invokestatic 158	java/security/AccessController:doPrivileged	(Ljava/security/PrivilegedAction;)Ljava/lang/Object;
/*      */     //   48: checkcast 159	java/awt/EventDispatchThread
/*      */     //   51: putfield 44	java/awt/EventQueue:dispatchThread	Ljava/awt/EventDispatchThread;
/*      */     //   54: aload_0
/*      */     //   55: getfield 44	java/awt/EventQueue:dispatchThread	Ljava/awt/EventDispatchThread;
/*      */     //   58: invokevirtual 160	java/awt/EventDispatchThread:start	()V
/*      */     //   61: aload_0
/*      */     //   62: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   65: invokeinterface 43 1 0
/*      */     //   70: goto +15 -> 85
/*      */     //   73: astore_1
/*      */     //   74: aload_0
/*      */     //   75: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   78: invokeinterface 43 1 0
/*      */     //   83: aload_1
/*      */     //   84: athrow
/*      */     //   85: return
/*      */     // Line number table:
/*      */     //   Java source line #1052	-> byte code offset #0
/*      */     //   Java source line #1054	-> byte code offset #9
/*      */     //   Java source line #1055	-> byte code offset #36
/*      */     //   Java source line #1070	-> byte code offset #54
/*      */     //   Java source line #1073	-> byte code offset #61
/*      */     //   Java source line #1074	-> byte code offset #70
/*      */     //   Java source line #1073	-> byte code offset #73
/*      */     //   Java source line #1075	-> byte code offset #85
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	86	0	this	EventQueue
/*      */     //   73	11	1	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	61	73	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   final void detachDispatchThread(EventDispatchThread paramEventDispatchThread)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 30	java/awt/EventQueue:appContext	Lsun/awt/AppContext;
/*      */     //   4: invokestatic 38	sun/awt/SunToolkit:flushPendingEvents	(Lsun/awt/AppContext;)V
/*      */     //   7: aload_0
/*      */     //   8: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   11: invokeinterface 41 1 0
/*      */     //   16: aload_1
/*      */     //   17: aload_0
/*      */     //   18: getfield 44	java/awt/EventQueue:dispatchThread	Ljava/awt/EventDispatchThread;
/*      */     //   21: if_acmpne +8 -> 29
/*      */     //   24: aload_0
/*      */     //   25: aconst_null
/*      */     //   26: putfield 44	java/awt/EventQueue:dispatchThread	Ljava/awt/EventDispatchThread;
/*      */     //   29: invokestatic 46	sun/awt/AWTAutoShutdown:getInstance	()Lsun/awt/AWTAutoShutdown;
/*      */     //   32: aload_1
/*      */     //   33: invokevirtual 90	sun/awt/AWTAutoShutdown:notifyThreadFree	(Ljava/lang/Thread;)V
/*      */     //   36: aload_0
/*      */     //   37: invokevirtual 134	java/awt/EventQueue:peekEvent	()Ljava/awt/AWTEvent;
/*      */     //   40: ifnull +7 -> 47
/*      */     //   43: aload_0
/*      */     //   44: invokevirtual 47	java/awt/EventQueue:initDispatchThread	()V
/*      */     //   47: aload_0
/*      */     //   48: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   51: invokeinterface 43 1 0
/*      */     //   56: goto +15 -> 71
/*      */     //   59: astore_2
/*      */     //   60: aload_0
/*      */     //   61: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   64: invokeinterface 43 1 0
/*      */     //   69: aload_2
/*      */     //   70: athrow
/*      */     //   71: return
/*      */     // Line number table:
/*      */     //   Java source line #1081	-> byte code offset #0
/*      */     //   Java source line #1090	-> byte code offset #7
/*      */     //   Java source line #1092	-> byte code offset #16
/*      */     //   Java source line #1093	-> byte code offset #24
/*      */     //   Java source line #1095	-> byte code offset #29
/*      */     //   Java source line #1100	-> byte code offset #36
/*      */     //   Java source line #1101	-> byte code offset #43
/*      */     //   Java source line #1104	-> byte code offset #47
/*      */     //   Java source line #1105	-> byte code offset #56
/*      */     //   Java source line #1104	-> byte code offset #59
/*      */     //   Java source line #1106	-> byte code offset #71
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	72	0	this	EventQueue
/*      */     //   0	72	1	paramEventDispatchThread	EventDispatchThread
/*      */     //   59	11	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   16	47	59	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void wakeup(boolean paramBoolean)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   4: invokeinterface 41 1 0
/*      */     //   9: aload_0
/*      */     //   10: getfield 42	java/awt/EventQueue:nextQueue	Ljava/awt/EventQueue;
/*      */     //   13: ifnull +14 -> 27
/*      */     //   16: aload_0
/*      */     //   17: getfield 42	java/awt/EventQueue:nextQueue	Ljava/awt/EventQueue;
/*      */     //   20: iload_1
/*      */     //   21: invokespecial 8	java/awt/EventQueue:wakeup	(Z)V
/*      */     //   24: goto +30 -> 54
/*      */     //   27: aload_0
/*      */     //   28: getfield 44	java/awt/EventQueue:dispatchThread	Ljava/awt/EventDispatchThread;
/*      */     //   31: ifnull +15 -> 46
/*      */     //   34: aload_0
/*      */     //   35: getfield 37	java/awt/EventQueue:pushPopCond	Ljava/util/concurrent/locks/Condition;
/*      */     //   38: invokeinterface 66 1 0
/*      */     //   43: goto +11 -> 54
/*      */     //   46: iload_1
/*      */     //   47: ifne +7 -> 54
/*      */     //   50: aload_0
/*      */     //   51: invokevirtual 47	java/awt/EventQueue:initDispatchThread	()V
/*      */     //   54: aload_0
/*      */     //   55: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   58: invokeinterface 43 1 0
/*      */     //   63: goto +15 -> 78
/*      */     //   66: astore_2
/*      */     //   67: aload_0
/*      */     //   68: getfield 34	java/awt/EventQueue:pushPopLock	Ljava/util/concurrent/locks/Lock;
/*      */     //   71: invokeinterface 43 1 0
/*      */     //   76: aload_2
/*      */     //   77: athrow
/*      */     //   78: return
/*      */     // Line number table:
/*      */     //   Java source line #1318	-> byte code offset #0
/*      */     //   Java source line #1320	-> byte code offset #9
/*      */     //   Java source line #1322	-> byte code offset #16
/*      */     //   Java source line #1323	-> byte code offset #27
/*      */     //   Java source line #1324	-> byte code offset #34
/*      */     //   Java source line #1325	-> byte code offset #46
/*      */     //   Java source line #1326	-> byte code offset #50
/*      */     //   Java source line #1329	-> byte code offset #54
/*      */     //   Java source line #1330	-> byte code offset #63
/*      */     //   Java source line #1329	-> byte code offset #66
/*      */     //   Java source line #1331	-> byte code offset #78
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	79	0	this	EventQueue
/*      */     //   0	79	1	paramBoolean	boolean
/*      */     //   66	11	2	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   9	54	66	finally
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/EventQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */