/*     */ package java.awt;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import sun.awt.EventQueueDelegate;
/*     */ import sun.awt.EventQueueDelegate.Delegate;
/*     */ import sun.awt.ModalExclude;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.dnd.SunDragSourceContextPeer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EventDispatchThread
/*     */   extends Thread
/*     */ {
/*  59 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.EventDispatchThread");
/*     */   
/*     */   private EventQueue theQueue;
/*  62 */   private volatile boolean doDispatch = true;
/*     */   
/*     */   private static final int ANY_EVENT = -1;
/*     */   
/*  66 */   private ArrayList<EventFilter> eventFilters = new ArrayList();
/*     */   
/*     */   EventDispatchThread(ThreadGroup paramThreadGroup, String paramString, EventQueue paramEventQueue) {
/*  69 */     super(paramThreadGroup, paramString);
/*  70 */     setEventQueue(paramEventQueue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void stopDispatching()
/*     */   {
/*  77 */     this.doDispatch = false;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: new 7	java/awt/EventDispatchThread$1
/*     */     //   4: dup
/*     */     //   5: aload_0
/*     */     //   6: invokespecial 8	java/awt/EventDispatchThread$1:<init>	(Ljava/awt/EventDispatchThread;)V
/*     */     //   9: invokevirtual 9	java/awt/EventDispatchThread:pumpEvents	(Ljava/awt/Conditional;)V
/*     */     //   12: aload_0
/*     */     //   13: invokevirtual 10	java/awt/EventDispatchThread:getEventQueue	()Ljava/awt/EventQueue;
/*     */     //   16: aload_0
/*     */     //   17: invokevirtual 11	java/awt/EventQueue:detachDispatchThread	(Ljava/awt/EventDispatchThread;)V
/*     */     //   20: goto +14 -> 34
/*     */     //   23: astore_1
/*     */     //   24: aload_0
/*     */     //   25: invokevirtual 10	java/awt/EventDispatchThread:getEventQueue	()Ljava/awt/EventQueue;
/*     */     //   28: aload_0
/*     */     //   29: invokevirtual 11	java/awt/EventQueue:detachDispatchThread	(Ljava/awt/EventDispatchThread;)V
/*     */     //   32: aload_1
/*     */     //   33: athrow
/*     */     //   34: return
/*     */     // Line number table:
/*     */     //   Java source line #82	-> byte code offset #0
/*     */     //   Java source line #88	-> byte code offset #12
/*     */     //   Java source line #89	-> byte code offset #20
/*     */     //   Java source line #88	-> byte code offset #23
/*     */     //   Java source line #90	-> byte code offset #34
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	35	0	this	EventDispatchThread
/*     */     //   23	10	1	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	12	23	finally
/*     */   }
/*     */   
/*     */   void pumpEvents(Conditional paramConditional)
/*     */   {
/*  93 */     pumpEvents(-1, paramConditional);
/*     */   }
/*     */   
/*     */   void pumpEventsForHierarchy(Conditional paramConditional, Component paramComponent) {
/*  97 */     pumpEventsForHierarchy(-1, paramConditional, paramComponent);
/*     */   }
/*     */   
/*     */   void pumpEvents(int paramInt, Conditional paramConditional) {
/* 101 */     pumpEventsForHierarchy(paramInt, paramConditional, null);
/*     */   }
/*     */   
/*     */   void pumpEventsForHierarchy(int paramInt, Conditional paramConditional, Component paramComponent) {
/* 105 */     pumpEventsForFilter(paramInt, paramConditional, new HierarchyEventFilter(paramComponent));
/*     */   }
/*     */   
/*     */   void pumpEventsForFilter(Conditional paramConditional, EventFilter paramEventFilter) {
/* 109 */     pumpEventsForFilter(-1, paramConditional, paramEventFilter);
/*     */   }
/*     */   
/*     */   void pumpEventsForFilter(int paramInt, Conditional paramConditional, EventFilter paramEventFilter) {
/* 113 */     addEventFilter(paramEventFilter);
/* 114 */     this.doDispatch = true;
/* 115 */     while ((this.doDispatch) && (!isInterrupted()) && (paramConditional.evaluate())) {
/* 116 */       pumpOneEventForFilters(paramInt);
/*     */     }
/* 118 */     removeEventFilter(paramEventFilter);
/*     */   }
/*     */   
/*     */   void addEventFilter(EventFilter paramEventFilter) {
/* 122 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 123 */       eventLog.finest("adding the event filter: " + paramEventFilter);
/*     */     }
/* 125 */     synchronized (this.eventFilters) {
/* 126 */       if (!this.eventFilters.contains(paramEventFilter)) {
/* 127 */         if ((paramEventFilter instanceof ModalEventFilter)) {
/* 128 */           ModalEventFilter localModalEventFilter1 = (ModalEventFilter)paramEventFilter;
/* 129 */           int i = 0;
/* 130 */           for (i = 0; i < this.eventFilters.size(); i++) {
/* 131 */             EventFilter localEventFilter = (EventFilter)this.eventFilters.get(i);
/* 132 */             if ((localEventFilter instanceof ModalEventFilter)) {
/* 133 */               ModalEventFilter localModalEventFilter2 = (ModalEventFilter)localEventFilter;
/* 134 */               if (localModalEventFilter2.compareTo(localModalEventFilter1) > 0) {
/*     */                 break;
/*     */               }
/*     */             }
/*     */           }
/* 139 */           this.eventFilters.add(i, paramEventFilter);
/*     */         } else {
/* 141 */           this.eventFilters.add(paramEventFilter);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void removeEventFilter(EventFilter paramEventFilter) {
/* 148 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 149 */       eventLog.finest("removing the event filter: " + paramEventFilter);
/*     */     }
/* 151 */     synchronized (this.eventFilters) {
/* 152 */       this.eventFilters.remove(paramEventFilter);
/*     */     }
/*     */   }
/*     */   
/*     */   void pumpOneEventForFilters(int paramInt) {
/* 157 */     AWTEvent localAWTEvent = null;
/* 158 */     int i = 0;
/*     */     try {
/* 160 */       EventQueue localEventQueue = null;
/* 161 */       EventQueueDelegate.Delegate localDelegate = null;
/*     */       do
/*     */       {
/* 164 */         localEventQueue = getEventQueue();
/* 165 */         localDelegate = EventQueueDelegate.getDelegate();
/*     */         
/* 167 */         if ((localDelegate != null) && (paramInt == -1)) {
/* 168 */           localAWTEvent = localDelegate.getNextEvent(localEventQueue);
/*     */         } else {
/* 170 */           localAWTEvent = paramInt == -1 ? localEventQueue.getNextEvent() : localEventQueue.getNextEvent(paramInt);
/*     */         }
/*     */         
/* 173 */         i = 1;
/* 174 */         synchronized (this.eventFilters) {
/* 175 */           for (int j = this.eventFilters.size() - 1; j >= 0; j--) {
/* 176 */             EventFilter localEventFilter = (EventFilter)this.eventFilters.get(j);
/* 177 */             EventFilter.FilterAction localFilterAction = localEventFilter.acceptEvent(localAWTEvent);
/* 178 */             if (localFilterAction == EventFilter.FilterAction.REJECT) {
/* 179 */               i = 0;
/*     */             } else {
/* 181 */               if (localFilterAction == EventFilter.FilterAction.ACCEPT_IMMEDIATELY)
/*     */                 break;
/*     */             }
/*     */           }
/*     */         }
/* 186 */         i = (i != 0) && (SunDragSourceContextPeer.checkEvent(localAWTEvent)) ? 1 : 0;
/* 187 */         if (i == 0) {
/* 188 */           localAWTEvent.consume();
/*     */         }
/*     */         
/* 191 */       } while (i == 0);
/*     */       
/* 193 */       if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 194 */         eventLog.finest("Dispatching: " + localAWTEvent);
/*     */       }
/*     */       
/* 197 */       ??? = null;
/* 198 */       if (localDelegate != null) {
/* 199 */         ??? = localDelegate.beforeDispatch(localAWTEvent);
/*     */       }
/* 201 */       localEventQueue.dispatchEvent(localAWTEvent);
/* 202 */       if (localDelegate != null) {
/* 203 */         localDelegate.afterDispatch(localAWTEvent, ???);
/*     */       }
/*     */     }
/*     */     catch (ThreadDeath localThreadDeath) {
/* 207 */       this.doDispatch = false;
/* 208 */       throw localThreadDeath;
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 211 */       this.doDispatch = false;
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/* 215 */       processException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   private void processException(Throwable paramThrowable) {
/* 220 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 221 */       eventLog.fine("Processing exception: " + paramThrowable);
/*     */     }
/* 223 */     getUncaughtExceptionHandler().uncaughtException(this, paramThrowable);
/*     */   }
/*     */   
/*     */   public synchronized EventQueue getEventQueue() {
/* 227 */     return this.theQueue;
/*     */   }
/*     */   
/* 230 */   public synchronized void setEventQueue(EventQueue paramEventQueue) { this.theQueue = paramEventQueue; }
/*     */   
/*     */   private static class HierarchyEventFilter
/*     */     implements EventFilter {
/*     */     private Component modalComponent;
/*     */     
/* 236 */     public HierarchyEventFilter(Component paramComponent) { this.modalComponent = paramComponent; }
/*     */     
/*     */     public EventFilter.FilterAction acceptEvent(AWTEvent paramAWTEvent) {
/* 239 */       if (this.modalComponent != null) {
/* 240 */         int i = paramAWTEvent.getID();
/* 241 */         int j = (i >= 500) && (i <= 507) ? 1 : 0;
/*     */         
/* 243 */         int k = (i >= 1001) && (i <= 1001) ? 1 : 0;
/*     */         
/* 245 */         int m = i == 201 ? 1 : 0;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 252 */         if (Component.isInstanceOf(this.modalComponent, "javax.swing.JInternalFrame"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 259 */           return m != 0 ? EventFilter.FilterAction.REJECT : EventFilter.FilterAction.ACCEPT;
/*     */         }
/* 261 */         if ((j != 0) || (k != 0) || (m != 0)) {
/* 262 */           Object localObject1 = paramAWTEvent.getSource();
/* 263 */           if ((localObject1 instanceof ModalExclude))
/*     */           {
/*     */ 
/* 266 */             return EventFilter.FilterAction.ACCEPT; }
/* 267 */           if ((localObject1 instanceof Component)) {
/* 268 */             Object localObject2 = (Component)localObject1;
/*     */             
/* 270 */             int n = 0;
/* 271 */             if ((this.modalComponent instanceof Container)) {
/* 272 */               while ((localObject2 != this.modalComponent) && (localObject2 != null)) {
/* 273 */                 if (((localObject2 instanceof Window)) && 
/* 274 */                   (SunToolkit.isModalExcluded((Window)localObject2)))
/*     */                 {
/*     */ 
/* 277 */                   n = 1;
/* 278 */                   break;
/*     */                 }
/* 280 */                 localObject2 = ((Component)localObject2).getParent();
/*     */               }
/*     */             }
/* 283 */             if ((n == 0) && (localObject2 != this.modalComponent)) {
/* 284 */               return EventFilter.FilterAction.REJECT;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 289 */       return EventFilter.FilterAction.ACCEPT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/EventDispatchThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */