/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.LightweightPeer;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Set;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.DefaultKeyboardFocusManagerAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.CausedFocusEvent.Cause;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.TimedWindowEvent;
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
/*      */ public class DefaultKeyboardFocusManager
/*      */   extends KeyboardFocusManager
/*      */ {
/*   66 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("java.awt.focus.DefaultKeyboardFocusManager");
/*      */   
/*      */ 
/*   69 */   private static final WeakReference<Window> NULL_WINDOW_WR = new WeakReference(null);
/*      */   
/*   71 */   private static final WeakReference<Component> NULL_COMPONENT_WR = new WeakReference(null);
/*      */   
/*   73 */   public DefaultKeyboardFocusManager() { this.realOppositeWindowWR = NULL_WINDOW_WR;
/*   74 */     this.realOppositeComponentWR = NULL_COMPONENT_WR;
/*      */     
/*   76 */     this.enqueuedKeyEvents = new LinkedList();
/*   77 */     this.typeAheadMarkers = new LinkedList();
/*      */   }
/*      */   
/*      */   static {
/*   81 */     AWTAccessor.setDefaultKeyboardFocusManagerAccessor(new AWTAccessor.DefaultKeyboardFocusManagerAccessor()
/*      */     {
/*      */       public void consumeNextKeyTyped(DefaultKeyboardFocusManager paramAnonymousDefaultKeyboardFocusManager, KeyEvent paramAnonymousKeyEvent) {
/*   84 */         paramAnonymousDefaultKeyboardFocusManager.consumeNextKeyTyped(paramAnonymousKeyEvent);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private static class TypeAheadMarker {
/*      */     long after;
/*      */     Component untilFocused;
/*      */     
/*      */     TypeAheadMarker(long paramLong, Component paramComponent) {
/*   94 */       this.after = paramLong;
/*   95 */       this.untilFocused = paramComponent;
/*      */     }
/*      */     
/*      */ 
/*      */     public String toString()
/*      */     {
/*  101 */       return ">>> Marker after " + this.after + " on " + this.untilFocused;
/*      */     }
/*      */   }
/*      */   
/*      */   private Window getOwningFrameDialog(Window paramWindow) {
/*  106 */     while ((paramWindow != null) && (!(paramWindow instanceof Frame)) && (!(paramWindow instanceof Dialog)))
/*      */     {
/*  108 */       paramWindow = (Window)paramWindow.getParent();
/*      */     }
/*  110 */     return paramWindow;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void restoreFocus(FocusEvent paramFocusEvent, Window paramWindow)
/*      */   {
/*  119 */     Component localComponent1 = (Component)this.realOppositeComponentWR.get();
/*  120 */     Component localComponent2 = paramFocusEvent.getComponent();
/*      */     
/*  122 */     if ((paramWindow == null) || (!restoreFocus(paramWindow, localComponent2, false)))
/*      */     {
/*      */ 
/*  125 */       if (((localComponent1 == null) || 
/*  126 */         (!doRestoreFocus(localComponent1, localComponent2, false))) && (
/*  127 */         (paramFocusEvent.getOppositeComponent() == null) || 
/*  128 */         (!doRestoreFocus(paramFocusEvent.getOppositeComponent(), localComponent2, false))))
/*      */       {
/*  130 */         clearGlobalFocusOwnerPriv(); } }
/*      */   }
/*      */   
/*      */   private void restoreFocus(WindowEvent paramWindowEvent) {
/*  134 */     Window localWindow = (Window)this.realOppositeWindowWR.get();
/*  135 */     if ((localWindow == null) || 
/*  136 */       (!restoreFocus(localWindow, null, false)))
/*      */     {
/*      */ 
/*  139 */       if ((paramWindowEvent.getOppositeWindow() == null) || 
/*  140 */         (!restoreFocus(paramWindowEvent.getOppositeWindow(), null, false)))
/*      */       {
/*      */ 
/*      */ 
/*  144 */         clearGlobalFocusOwnerPriv();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean restoreFocus(Window paramWindow, Component paramComponent, boolean paramBoolean) {
/*  150 */     Component localComponent = KeyboardFocusManager.getMostRecentFocusOwner(paramWindow);
/*      */     
/*  152 */     if ((localComponent != null) && (localComponent != paramComponent) && (doRestoreFocus(localComponent, paramComponent, false)))
/*  153 */       return true;
/*  154 */     if (paramBoolean) {
/*  155 */       clearGlobalFocusOwnerPriv();
/*  156 */       return true;
/*      */     }
/*  158 */     return false;
/*      */   }
/*      */   
/*      */   private boolean restoreFocus(Component paramComponent, boolean paramBoolean) {
/*  162 */     return doRestoreFocus(paramComponent, null, paramBoolean);
/*      */   }
/*      */   
/*      */   private boolean doRestoreFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean)
/*      */   {
/*  167 */     if ((paramComponent1 != paramComponent2) && (paramComponent1.isShowing()) && (paramComponent1.canBeFocusOwner()) && 
/*  168 */       (paramComponent1.requestFocus(false, CausedFocusEvent.Cause.ROLLBACK)))
/*      */     {
/*  170 */       return true;
/*      */     }
/*  172 */     Component localComponent = paramComponent1.getNextFocusCandidate();
/*  173 */     if ((localComponent != null) && (localComponent != paramComponent2) && 
/*  174 */       (localComponent.requestFocusInWindow(CausedFocusEvent.Cause.ROLLBACK)))
/*      */     {
/*  176 */       return true; }
/*  177 */     if (paramBoolean) {
/*  178 */       clearGlobalFocusOwnerPriv();
/*  179 */       return true;
/*      */     }
/*  181 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class DefaultKeyboardFocusManagerSentEvent
/*      */     extends SentEvent
/*      */   {
/*      */     private static final long serialVersionUID = -2924743257508701758L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public DefaultKeyboardFocusManagerSentEvent(AWTEvent paramAWTEvent, AppContext paramAppContext)
/*      */     {
/*  201 */       super(paramAppContext);
/*      */     }
/*      */     
/*      */     public final void dispatch() {
/*  205 */       KeyboardFocusManager localKeyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/*  206 */       DefaultKeyboardFocusManager localDefaultKeyboardFocusManager = (localKeyboardFocusManager instanceof DefaultKeyboardFocusManager) ? (DefaultKeyboardFocusManager)localKeyboardFocusManager : null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  211 */       if (localDefaultKeyboardFocusManager != null) {
/*  212 */         synchronized (localDefaultKeyboardFocusManager) {
/*  213 */           DefaultKeyboardFocusManager.access$108(localDefaultKeyboardFocusManager);
/*      */         }
/*      */       }
/*      */       
/*  217 */       super.dispatch();
/*      */       
/*  219 */       if (localDefaultKeyboardFocusManager != null) {
/*  220 */         synchronized (localDefaultKeyboardFocusManager) {
/*  221 */           DefaultKeyboardFocusManager.access$110(localDefaultKeyboardFocusManager);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private WeakReference<Window> realOppositeWindowWR;
/*      */   
/*      */ 
/*      */ 
/*      */   static boolean sendMessage(Component paramComponent, AWTEvent paramAWTEvent)
/*      */   {
/*  237 */     paramAWTEvent.isPosted = true;
/*  238 */     AppContext localAppContext1 = AppContext.getAppContext();
/*  239 */     final AppContext localAppContext2 = paramComponent.appContext;
/*  240 */     DefaultKeyboardFocusManagerSentEvent localDefaultKeyboardFocusManagerSentEvent = new DefaultKeyboardFocusManagerSentEvent(paramAWTEvent, localAppContext1);
/*      */     
/*      */ 
/*  243 */     if (localAppContext1 == localAppContext2) {
/*  244 */       localDefaultKeyboardFocusManagerSentEvent.dispatch();
/*      */     } else {
/*  246 */       if (localAppContext2.isDisposed()) {
/*  247 */         return false;
/*      */       }
/*  249 */       SunToolkit.postEvent(localAppContext2, localDefaultKeyboardFocusManagerSentEvent);
/*  250 */       if (EventQueue.isDispatchThread())
/*      */       {
/*  252 */         EventDispatchThread localEventDispatchThread = (EventDispatchThread)Thread.currentThread();
/*  253 */         localEventDispatchThread.pumpEvents(1007, new Conditional() {
/*      */           public boolean evaluate() {
/*  255 */             return (!this.val$se.dispatched) && (!localAppContext2.isDisposed());
/*      */           }
/*      */         });
/*      */       } else {
/*  259 */         synchronized (localDefaultKeyboardFocusManagerSentEvent) {
/*  260 */           for (;;) { if ((!localDefaultKeyboardFocusManagerSentEvent.dispatched) && (!localAppContext2.isDisposed())) {
/*      */               try {
/*  262 */                 localDefaultKeyboardFocusManagerSentEvent.wait(1000L);
/*      */               }
/*      */               catch (InterruptedException localInterruptedException) {}
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  270 */     return localDefaultKeyboardFocusManagerSentEvent.dispatched;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean repostIfFollowsKeyEvents(WindowEvent paramWindowEvent)
/*      */   {
/*  282 */     if (!(paramWindowEvent instanceof TimedWindowEvent)) {
/*  283 */       return false;
/*      */     }
/*  285 */     TimedWindowEvent localTimedWindowEvent = (TimedWindowEvent)paramWindowEvent;
/*  286 */     long l = localTimedWindowEvent.getWhen();
/*  287 */     synchronized (this) {
/*  288 */       KeyEvent localKeyEvent = this.enqueuedKeyEvents.isEmpty() ? null : (KeyEvent)this.enqueuedKeyEvents.getFirst();
/*  289 */       if ((localKeyEvent != null) && (l >= localKeyEvent.getWhen())) {
/*  290 */         TypeAheadMarker localTypeAheadMarker = this.typeAheadMarkers.isEmpty() ? null : (TypeAheadMarker)this.typeAheadMarkers.getFirst();
/*  291 */         if (localTypeAheadMarker != null) {
/*  292 */           Window localWindow = localTypeAheadMarker.untilFocused.getContainingWindow();
/*      */           
/*      */ 
/*  295 */           if ((localWindow != null) && (localWindow.isFocused())) {
/*  296 */             SunToolkit.postEvent(AppContext.getAppContext(), new SequencedEvent(paramWindowEvent));
/*  297 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  302 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private WeakReference<Component> realOppositeComponentWR;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int inSendMessage;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean dispatchEvent(AWTEvent paramAWTEvent)
/*      */   {
/*  320 */     if ((focusLog.isLoggable(PlatformLogger.Level.FINE)) && (((paramAWTEvent instanceof WindowEvent)) || ((paramAWTEvent instanceof FocusEvent))))
/*  321 */       focusLog.fine("" + paramAWTEvent);
/*      */     Object localObject1;
/*  323 */     Object localObject2; Object localObject3; Object localObject5; Object localObject6; Object localObject4; Window localWindow3; switch (paramAWTEvent.getID()) {
/*      */     case 207: 
/*  325 */       if (!repostIfFollowsKeyEvents((WindowEvent)paramAWTEvent))
/*      */       {
/*      */ 
/*      */ 
/*  329 */         localObject1 = (WindowEvent)paramAWTEvent;
/*  330 */         localObject2 = getGlobalFocusedWindow();
/*  331 */         localObject3 = ((WindowEvent)localObject1).getWindow();
/*  332 */         if (localObject3 != localObject2)
/*      */         {
/*      */ 
/*      */ 
/*  336 */           if ((!((Window)localObject3).isFocusableWindow()) || 
/*  337 */             (!((Window)localObject3).isVisible()) || 
/*  338 */             (!((Window)localObject3).isDisplayable()))
/*      */           {
/*      */ 
/*  341 */             restoreFocus((WindowEvent)localObject1);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  346 */             if (localObject2 != null)
/*      */             {
/*  348 */               boolean bool1 = sendMessage((Component)localObject2, new WindowEvent((Window)localObject2, 208, (Window)localObject3));
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*  353 */               if (!bool1) {
/*  354 */                 setGlobalFocusOwner(null);
/*  355 */                 setGlobalFocusedWindow(null);
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  363 */             Window localWindow1 = getOwningFrameDialog((Window)localObject3);
/*  364 */             Window localWindow2 = getGlobalActiveWindow();
/*  365 */             if (localWindow1 != localWindow2) {
/*  366 */               sendMessage(localWindow1, new WindowEvent(localWindow1, 205, localWindow2));
/*      */               
/*      */ 
/*      */ 
/*  370 */               if (localWindow1 != getGlobalActiveWindow())
/*      */               {
/*      */ 
/*  373 */                 restoreFocus((WindowEvent)localObject1);
/*  374 */                 break;
/*      */               }
/*      */             }
/*      */             
/*  378 */             setGlobalFocusedWindow((Window)localObject3);
/*      */             
/*  380 */             if (localObject3 != getGlobalFocusedWindow())
/*      */             {
/*      */ 
/*  383 */               restoreFocus((WindowEvent)localObject1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  394 */               if (this.inSendMessage == 0)
/*      */               {
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
/*  415 */                 localObject5 = KeyboardFocusManager.getMostRecentFocusOwner((Window)localObject3);
/*  416 */                 if ((localObject5 == null) && 
/*  417 */                   (((Window)localObject3).isFocusableWindow()))
/*      */                 {
/*      */ 
/*  420 */                   localObject5 = ((Window)localObject3).getFocusTraversalPolicy().getInitialComponent((Window)localObject3);
/*      */                 }
/*  422 */                 localObject6 = null;
/*  423 */                 synchronized (KeyboardFocusManager.class) {
/*  424 */                   localObject6 = ((Window)localObject3).setTemporaryLostComponent(null);
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  429 */                 if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  430 */                   focusLog.finer("tempLost {0}, toFocus {1}", new Object[] { localObject6, localObject5 });
/*      */                 }
/*      */                 
/*  433 */                 if (localObject6 != null) {
/*  434 */                   ((Component)localObject6).requestFocusInWindow(CausedFocusEvent.Cause.ACTIVATION);
/*      */                 }
/*      */                 
/*  437 */                 if ((localObject5 != null) && (localObject5 != localObject6))
/*      */                 {
/*      */ 
/*  440 */                   ((Component)localObject5).requestFocusInWindow(CausedFocusEvent.Cause.ACTIVATION);
/*      */                 }
/*      */               }
/*      */               
/*  444 */               localObject5 = (Window)this.realOppositeWindowWR.get();
/*  445 */               if (localObject5 != ((WindowEvent)localObject1).getOppositeWindow()) {
/*  446 */                 localObject1 = new WindowEvent((Window)localObject3, 207, (Window)localObject5);
/*      */               }
/*      */               
/*      */ 
/*  450 */               return typeAheadAssertions((Component)localObject3, (AWTEvent)localObject1);
/*      */             }
/*      */           } }
/*      */       }
/*      */       break; case 205:  localObject1 = (WindowEvent)paramAWTEvent;
/*  455 */       localObject2 = getGlobalActiveWindow();
/*  456 */       localObject3 = ((WindowEvent)localObject1).getWindow();
/*  457 */       if (localObject2 != localObject3)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  463 */         if (localObject2 != null)
/*      */         {
/*  465 */           boolean bool2 = sendMessage((Component)localObject2, new WindowEvent((Window)localObject2, 206, (Window)localObject3));
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  470 */           if (!bool2) {
/*  471 */             setGlobalActiveWindow(null);
/*      */           }
/*  473 */           if (getGlobalActiveWindow() != null) {
/*      */             break;
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/*  480 */           setGlobalActiveWindow((Window)localObject3);
/*      */           
/*  482 */           if (localObject3 == getGlobalActiveWindow())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  488 */             return typeAheadAssertions((Component)localObject3, (AWTEvent)localObject1); }
/*      */         } }
/*      */       break;
/*      */     case 1004: 
/*  492 */       localObject1 = (FocusEvent)paramAWTEvent;
/*      */       
/*  494 */       localObject2 = (localObject1 instanceof CausedFocusEvent) ? ((CausedFocusEvent)localObject1).getCause() : CausedFocusEvent.Cause.UNKNOWN;
/*  495 */       localObject3 = getGlobalFocusOwner();
/*  496 */       localObject4 = ((FocusEvent)localObject1).getComponent();
/*  497 */       if (localObject3 == localObject4) {
/*  498 */         if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  499 */           focusLog.fine("Skipping {0} because focus owner is the same", new Object[] { paramAWTEvent });
/*      */         }
/*      */         
/*      */ 
/*  503 */         dequeueKeyEvents(-1L, (Component)localObject4);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  509 */         if (localObject3 != null)
/*      */         {
/*  511 */           boolean bool3 = sendMessage((Component)localObject3, new CausedFocusEvent((Component)localObject3, 1005, ((FocusEvent)localObject1)
/*      */           
/*      */ 
/*  514 */             .isTemporary(), (Component)localObject4, (CausedFocusEvent.Cause)localObject2));
/*      */           
/*      */ 
/*  517 */           if (!bool3) {
/*  518 */             setGlobalFocusOwner(null);
/*  519 */             if (!((FocusEvent)localObject1).isTemporary()) {
/*  520 */               setGlobalPermanentFocusOwner(null);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  530 */         localWindow3 = SunToolkit.getContainingWindow((Component)localObject4);
/*  531 */         localObject5 = getGlobalFocusedWindow();
/*  532 */         if ((localWindow3 != null) && (localWindow3 != localObject5))
/*      */         {
/*      */ 
/*  535 */           sendMessage(localWindow3, new WindowEvent(localWindow3, 207, (Window)localObject5));
/*      */           
/*      */ 
/*      */ 
/*  539 */           if (localWindow3 != getGlobalFocusedWindow())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  546 */             dequeueKeyEvents(-1L, (Component)localObject4);
/*  547 */             break;
/*      */           }
/*      */         }
/*      */         
/*  551 */         if ((!((Component)localObject4).isFocusable()) || (!((Component)localObject4).isShowing()) || (
/*      */         
/*      */ 
/*      */ 
/*  555 */           (!((Component)localObject4).isEnabled()) && (!((CausedFocusEvent.Cause)localObject2).equals(CausedFocusEvent.Cause.UNKNOWN))))
/*      */         {
/*      */ 
/*  558 */           dequeueKeyEvents(-1L, (Component)localObject4);
/*  559 */           if (KeyboardFocusManager.isAutoFocusTransferEnabled())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  564 */             if (localWindow3 == null) {
/*  565 */               restoreFocus((FocusEvent)localObject1, (Window)localObject5);
/*      */             } else {
/*  567 */               restoreFocus((FocusEvent)localObject1, localWindow3);
/*      */             }
/*  569 */             setMostRecentFocusOwner(localWindow3, null);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  574 */           setGlobalFocusOwner((Component)localObject4);
/*      */           
/*  576 */           if (localObject4 != getGlobalFocusOwner())
/*      */           {
/*      */ 
/*  579 */             dequeueKeyEvents(-1L, (Component)localObject4);
/*  580 */             if (KeyboardFocusManager.isAutoFocusTransferEnabled()) {
/*  581 */               restoreFocus((FocusEvent)localObject1, localWindow3);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  586 */             if (!((FocusEvent)localObject1).isTemporary()) {
/*  587 */               setGlobalPermanentFocusOwner((Component)localObject4);
/*      */               
/*  589 */               if (localObject4 != getGlobalPermanentFocusOwner())
/*      */               {
/*  591 */                 dequeueKeyEvents(-1L, (Component)localObject4);
/*  592 */                 if (!KeyboardFocusManager.isAutoFocusTransferEnabled()) break;
/*  593 */                 restoreFocus((FocusEvent)localObject1, localWindow3); break;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  599 */             setNativeFocusOwner(getHeavyweight((Component)localObject4));
/*      */             
/*  601 */             localObject6 = (Component)this.realOppositeComponentWR.get();
/*  602 */             if ((localObject6 != null) && 
/*  603 */               (localObject6 != ((FocusEvent)localObject1).getOppositeComponent()))
/*      */             {
/*      */ 
/*  606 */               localObject1 = new CausedFocusEvent((Component)localObject4, 1004, ((FocusEvent)localObject1).isTemporary(), (Component)localObject6, (CausedFocusEvent.Cause)localObject2);
/*      */               
/*  608 */               ((AWTEvent)localObject1).isPosted = true;
/*      */             }
/*  610 */             return typeAheadAssertions((Component)localObject4, (AWTEvent)localObject1);
/*      */           }
/*      */         }
/*      */       }
/*      */       break; case 1005:  localObject1 = (FocusEvent)paramAWTEvent;
/*  615 */       localObject2 = getGlobalFocusOwner();
/*  616 */       if (localObject2 == null) {
/*  617 */         if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  618 */           focusLog.fine("Skipping {0} because focus owner is null", new Object[] { paramAWTEvent });
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*  624 */       else if (localObject2 == ((FocusEvent)localObject1).getOppositeComponent()) {
/*  625 */         if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  626 */           focusLog.fine("Skipping {0} because current focus owner is equal to opposite", new Object[] { paramAWTEvent });
/*      */         }
/*      */       }
/*      */       else {
/*  630 */         setGlobalFocusOwner(null);
/*      */         
/*  632 */         if (getGlobalFocusOwner() != null)
/*      */         {
/*  634 */           restoreFocus((Component)localObject2, true);
/*      */         }
/*      */         else
/*      */         {
/*  638 */           if (!((FocusEvent)localObject1).isTemporary()) {
/*  639 */             setGlobalPermanentFocusOwner(null);
/*      */             
/*  641 */             if (getGlobalPermanentFocusOwner() != null)
/*      */             {
/*  643 */               restoreFocus((Component)localObject2, true);
/*  644 */               break;
/*      */             }
/*      */           } else {
/*  647 */             localObject3 = ((Component)localObject2).getContainingWindow();
/*  648 */             if (localObject3 != null) {
/*  649 */               ((Window)localObject3).setTemporaryLostComponent((Component)localObject2);
/*      */             }
/*      */           }
/*      */           
/*  653 */           setNativeFocusOwner(null);
/*      */           
/*  655 */           ((FocusEvent)localObject1).setSource(localObject2);
/*      */           
/*  657 */           this.realOppositeComponentWR = (((FocusEvent)localObject1).getOppositeComponent() != null ? new WeakReference(localObject2) : NULL_COMPONENT_WR);
/*      */           
/*      */ 
/*      */ 
/*  661 */           return typeAheadAssertions((Component)localObject2, (AWTEvent)localObject1);
/*      */         }
/*      */       }
/*      */       break;
/*  665 */     case 206:  localObject1 = (WindowEvent)paramAWTEvent;
/*  666 */       localObject2 = getGlobalActiveWindow();
/*  667 */       if (localObject2 != null)
/*      */       {
/*      */ 
/*      */ 
/*  671 */         if (localObject2 == paramAWTEvent.getSource())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  678 */           setGlobalActiveWindow(null);
/*  679 */           if (getGlobalActiveWindow() == null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  684 */             ((WindowEvent)localObject1).setSource(localObject2);
/*  685 */             return typeAheadAssertions((Component)localObject2, (AWTEvent)localObject1);
/*      */           }
/*      */         } }
/*      */       break;
/*  689 */     case 208:  if (!repostIfFollowsKeyEvents((WindowEvent)paramAWTEvent))
/*      */       {
/*      */ 
/*      */ 
/*  693 */         localObject1 = (WindowEvent)paramAWTEvent;
/*  694 */         localObject2 = getGlobalFocusedWindow();
/*  695 */         localObject3 = ((WindowEvent)localObject1).getWindow();
/*  696 */         localObject4 = getGlobalActiveWindow();
/*  697 */         localWindow3 = ((WindowEvent)localObject1).getOppositeWindow();
/*  698 */         if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  699 */           focusLog.fine("Active {0}, Current focused {1}, losing focus {2} opposite {3}", new Object[] { localObject4, localObject2, localObject3, localWindow3 });
/*      */         }
/*      */         
/*  702 */         if (localObject2 != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  711 */           if ((this.inSendMessage != 0) || (localObject3 != localObject4) || (localWindow3 != localObject2))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  717 */             localObject5 = getGlobalFocusOwner();
/*  718 */             if (localObject5 != null)
/*      */             {
/*      */ 
/*  721 */               localObject6 = null;
/*  722 */               if (localWindow3 != null) {
/*  723 */                 localObject6 = localWindow3.getTemporaryLostComponent();
/*  724 */                 if (localObject6 == null) {
/*  725 */                   localObject6 = localWindow3.getMostRecentFocusOwner();
/*      */                 }
/*      */               }
/*  728 */               if (localObject6 == null) {
/*  729 */                 localObject6 = localWindow3;
/*      */               }
/*  731 */               sendMessage((Component)localObject5, new CausedFocusEvent((Component)localObject5, 1005, true, (Component)localObject6, CausedFocusEvent.Cause.ACTIVATION));
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  738 */             setGlobalFocusedWindow(null);
/*  739 */             if (getGlobalFocusedWindow() != null)
/*      */             {
/*  741 */               restoreFocus((Window)localObject2, null, true);
/*      */             }
/*      */             else
/*      */             {
/*  745 */               ((WindowEvent)localObject1).setSource(localObject2);
/*  746 */               this.realOppositeWindowWR = (localWindow3 != null ? new WeakReference(localObject2) : NULL_WINDOW_WR);
/*      */               
/*      */ 
/*  749 */               typeAheadAssertions((Component)localObject2, (AWTEvent)localObject1);
/*      */               
/*  751 */               if (localWindow3 == null)
/*      */               {
/*      */ 
/*      */ 
/*  755 */                 sendMessage((Component)localObject4, new WindowEvent((Window)localObject4, 206, null));
/*      */                 
/*      */ 
/*      */ 
/*  759 */                 if (getGlobalActiveWindow() != null)
/*      */                 {
/*      */ 
/*  762 */                   restoreFocus((Window)localObject2, null, true);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       break;
/*      */     case 400: case 401: case 402: 
/*  771 */       return typeAheadAssertions(null, paramAWTEvent);
/*      */     
/*      */     default: 
/*  774 */       return false;
/*      */     }
/*      */     
/*  777 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private LinkedList<KeyEvent> enqueuedKeyEvents;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private LinkedList<TypeAheadMarker> typeAheadMarkers;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean consumeNextKeyTyped;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
/*      */   {
/*  800 */     Component localComponent1 = paramKeyEvent.isPosted ? getFocusOwner() : paramKeyEvent.getComponent();
/*      */     
/*  802 */     if ((localComponent1 != null) && (localComponent1.isShowing()) && (localComponent1.canBeFocusOwner()) && 
/*  803 */       (!paramKeyEvent.isConsumed())) {
/*  804 */       Component localComponent2 = paramKeyEvent.getComponent();
/*  805 */       if ((localComponent2 != null) && (localComponent2.isEnabled())) {
/*  806 */         redispatchEvent(localComponent2, paramKeyEvent);
/*      */       }
/*      */     }
/*      */     
/*  810 */     boolean bool = false;
/*  811 */     List localList = getKeyEventPostProcessors();
/*  812 */     if (localList != null) {
/*  813 */       localObject = localList.iterator();
/*  814 */       while ((!bool) && (((Iterator)localObject).hasNext()))
/*      */       {
/*      */ 
/*  817 */         bool = ((KeyEventPostProcessor)((Iterator)localObject).next()).postProcessKeyEvent(paramKeyEvent);
/*      */       }
/*      */     }
/*  820 */     if (!bool) {
/*  821 */       postProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */     
/*      */ 
/*  825 */     Object localObject = paramKeyEvent.getComponent();
/*  826 */     ComponentPeer localComponentPeer = ((Component)localObject).getPeer();
/*      */     
/*  828 */     if ((localComponentPeer == null) || ((localComponentPeer instanceof LightweightPeer)))
/*      */     {
/*      */ 
/*  831 */       Container localContainer = ((Component)localObject).getNativeContainer();
/*  832 */       if (localContainer != null) {
/*  833 */         localComponentPeer = localContainer.getPeer();
/*      */       }
/*      */     }
/*  836 */     if (localComponentPeer != null) {
/*  837 */       localComponentPeer.handleEvent(paramKeyEvent);
/*      */     }
/*      */     
/*  840 */     return true;
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
/*      */   public boolean postProcessKeyEvent(KeyEvent paramKeyEvent)
/*      */   {
/*  855 */     if (!paramKeyEvent.isConsumed()) {
/*  856 */       Component localComponent = paramKeyEvent.getComponent();
/*      */       
/*  858 */       Container localContainer = (Container)((localComponent instanceof Container) ? localComponent : localComponent.getParent());
/*  859 */       if (localContainer != null) {
/*  860 */         localContainer.postProcessKeyEvent(paramKeyEvent);
/*      */       }
/*      */     }
/*  863 */     return true;
/*      */   }
/*      */   
/*      */   private void pumpApprovedKeyEvents() {
/*      */     KeyEvent localKeyEvent;
/*      */     do {
/*  869 */       localKeyEvent = null;
/*  870 */       synchronized (this) {
/*  871 */         if (this.enqueuedKeyEvents.size() != 0) {
/*  872 */           localKeyEvent = (KeyEvent)this.enqueuedKeyEvents.getFirst();
/*  873 */           if (this.typeAheadMarkers.size() != 0) {
/*  874 */             TypeAheadMarker localTypeAheadMarker = (TypeAheadMarker)this.typeAheadMarkers.getFirst();
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  879 */             if (localKeyEvent.getWhen() > localTypeAheadMarker.after) {
/*  880 */               localKeyEvent = null;
/*      */             }
/*      */           }
/*  883 */           if (localKeyEvent != null) {
/*  884 */             if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  885 */               focusLog.finer("Pumping approved event {0}", new Object[] { localKeyEvent });
/*      */             }
/*  887 */             this.enqueuedKeyEvents.removeFirst();
/*      */           }
/*      */         }
/*      */       }
/*  891 */       if (localKeyEvent != null) {
/*  892 */         preDispatchKeyEvent(localKeyEvent);
/*      */       }
/*  894 */     } while (localKeyEvent != null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void dumpMarkers()
/*      */   {
/*  901 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  902 */       focusLog.finest(">>> Markers dump, time: {0}", new Object[] { Long.valueOf(System.currentTimeMillis()) });
/*  903 */       synchronized (this) {
/*  904 */         if (this.typeAheadMarkers.size() != 0) {
/*  905 */           Iterator localIterator = this.typeAheadMarkers.iterator();
/*  906 */           while (localIterator.hasNext()) {
/*  907 */             TypeAheadMarker localTypeAheadMarker = (TypeAheadMarker)localIterator.next();
/*  908 */             focusLog.finest("    {0}", new Object[] { localTypeAheadMarker });
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean typeAheadAssertions(Component paramComponent, AWTEvent paramAWTEvent)
/*      */   {
/*  920 */     pumpApprovedKeyEvents();
/*      */     Object localObject1;
/*  922 */     switch (paramAWTEvent.getID()) {
/*      */     case 400: 
/*      */     case 401: 
/*      */     case 402: 
/*  926 */       KeyEvent localKeyEvent = (KeyEvent)paramAWTEvent;
/*  927 */       synchronized (this) {
/*  928 */         if ((paramAWTEvent.isPosted) && (this.typeAheadMarkers.size() != 0)) {
/*  929 */           localObject1 = (TypeAheadMarker)this.typeAheadMarkers.getFirst();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  934 */           if (localKeyEvent.getWhen() > ((TypeAheadMarker)localObject1).after) {
/*  935 */             if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  936 */               focusLog.finer("Storing event {0} because of marker {1}", new Object[] { localKeyEvent, localObject1 });
/*      */             }
/*  938 */             this.enqueuedKeyEvents.addLast(localKeyEvent);
/*  939 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  945 */       return preDispatchKeyEvent(localKeyEvent);
/*      */     
/*      */ 
/*      */     case 1004: 
/*  949 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  950 */         focusLog.finest("Markers before FOCUS_GAINED on {0}", new Object[] { paramComponent });
/*      */       }
/*  952 */       dumpMarkers();
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
/*  963 */       synchronized (this) {
/*  964 */         int i = 0;
/*  965 */         if (hasMarker(paramComponent)) {
/*  966 */           localObject1 = this.typeAheadMarkers.iterator();
/*  967 */           while (((Iterator)localObject1).hasNext())
/*      */           {
/*  969 */             if (((TypeAheadMarker)((Iterator)localObject1).next()).untilFocused == paramComponent)
/*  970 */               i = 1; else {
/*  971 */               if (i != 0)
/*      */                 break;
/*      */             }
/*  974 */             ((Iterator)localObject1).remove();
/*      */           }
/*      */           
/*      */         }
/*  978 */         else if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  979 */           focusLog.finer("Event without marker {0}", new Object[] { paramAWTEvent });
/*      */         }
/*      */       }
/*      */       
/*  983 */       focusLog.finest("Markers after FOCUS_GAINED");
/*  984 */       dumpMarkers();
/*      */       
/*  986 */       redispatchEvent(paramComponent, paramAWTEvent);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  991 */       pumpApprovedKeyEvents();
/*  992 */       return true;
/*      */     }
/*      */     
/*  995 */     redispatchEvent(paramComponent, paramAWTEvent);
/*  996 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean hasMarker(Component paramComponent)
/*      */   {
/* 1006 */     for (Iterator localIterator = this.typeAheadMarkers.iterator(); localIterator.hasNext();) {
/* 1007 */       if (((TypeAheadMarker)localIterator.next()).untilFocused == paramComponent) {
/* 1008 */         return true;
/*      */       }
/*      */     }
/* 1011 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void clearMarkers()
/*      */   {
/* 1019 */     synchronized (this) {
/* 1020 */       this.typeAheadMarkers.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean preDispatchKeyEvent(KeyEvent paramKeyEvent) {
/* 1025 */     if (paramKeyEvent.isPosted) {
/* 1026 */       localObject1 = getFocusOwner();
/* 1027 */       paramKeyEvent.setSource(localObject1 != null ? localObject1 : getFocusedWindow());
/*      */     }
/* 1029 */     if (paramKeyEvent.getSource() == null) {
/* 1030 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */     EventQueue.setCurrentEventAndMostRecentTime(paramKeyEvent);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Object localObject2;
/*      */     
/*      */ 
/*      */ 
/* 1046 */     if (KeyboardFocusManager.isProxyActive(paramKeyEvent)) {
/* 1047 */       localObject1 = (Component)paramKeyEvent.getSource();
/* 1048 */       localObject2 = ((Component)localObject1).getNativeContainer();
/* 1049 */       if (localObject2 != null) {
/* 1050 */         ComponentPeer localComponentPeer = ((Container)localObject2).getPeer();
/* 1051 */         if (localComponentPeer != null) {
/* 1052 */           localComponentPeer.handleEvent(paramKeyEvent);
/*      */           
/*      */ 
/*      */ 
/* 1056 */           paramKeyEvent.consume();
/*      */         }
/*      */       }
/* 1059 */       return true;
/*      */     }
/*      */     
/* 1062 */     Object localObject1 = getKeyEventDispatchers();
/* 1063 */     if (localObject1 != null) {
/* 1064 */       localObject2 = ((List)localObject1).iterator();
/* 1065 */       while (((Iterator)localObject2).hasNext())
/*      */       {
/*      */ 
/* 1068 */         if (((KeyEventDispatcher)((Iterator)localObject2).next()).dispatchKeyEvent(paramKeyEvent))
/*      */         {
/* 1070 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 1074 */     return dispatchKeyEvent(paramKeyEvent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void consumeNextKeyTyped(KeyEvent paramKeyEvent)
/*      */   {
/* 1082 */     this.consumeNextKeyTyped = true;
/*      */   }
/*      */   
/*      */   private void consumeTraversalKey(KeyEvent paramKeyEvent) {
/* 1086 */     paramKeyEvent.consume();
/*      */     
/* 1088 */     this.consumeNextKeyTyped = ((paramKeyEvent.getID() == 401) && (!paramKeyEvent.isActionKey()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean consumeProcessedKeyEvent(KeyEvent paramKeyEvent)
/*      */   {
/* 1095 */     if ((paramKeyEvent.getID() == 400) && (this.consumeNextKeyTyped)) {
/* 1096 */       paramKeyEvent.consume();
/* 1097 */       this.consumeNextKeyTyped = false;
/* 1098 */       return true;
/*      */     }
/* 1100 */     return false;
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
/*      */   public void processKeyEvent(Component paramComponent, KeyEvent paramKeyEvent)
/*      */   {
/* 1118 */     if (consumeProcessedKeyEvent(paramKeyEvent)) {
/* 1119 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1123 */     if (paramKeyEvent.getID() == 400) {
/* 1124 */       return;
/*      */     }
/*      */     
/* 1127 */     if ((paramComponent.getFocusTraversalKeysEnabled()) && 
/* 1128 */       (!paramKeyEvent.isConsumed()))
/*      */     {
/* 1130 */       AWTKeyStroke localAWTKeyStroke1 = AWTKeyStroke.getAWTKeyStrokeForEvent(paramKeyEvent);
/* 1131 */       AWTKeyStroke localAWTKeyStroke2 = AWTKeyStroke.getAWTKeyStroke(localAWTKeyStroke1.getKeyCode(), localAWTKeyStroke1
/* 1132 */         .getModifiers(), 
/* 1133 */         !localAWTKeyStroke1.isOnKeyRelease());
/*      */       
/*      */ 
/*      */ 
/* 1137 */       Set localSet = paramComponent.getFocusTraversalKeys(0);
/*      */       
/* 1139 */       boolean bool1 = localSet.contains(localAWTKeyStroke1);
/* 1140 */       boolean bool2 = localSet.contains(localAWTKeyStroke2);
/*      */       
/* 1142 */       if ((bool1) || (bool2)) {
/* 1143 */         consumeTraversalKey(paramKeyEvent);
/* 1144 */         if (bool1) {
/* 1145 */           focusNextComponent(paramComponent);
/*      */         }
/* 1147 */         return; }
/* 1148 */       if (paramKeyEvent.getID() == 401)
/*      */       {
/* 1150 */         this.consumeNextKeyTyped = false;
/*      */       }
/*      */       
/* 1153 */       localSet = paramComponent.getFocusTraversalKeys(1);
/*      */       
/* 1155 */       bool1 = localSet.contains(localAWTKeyStroke1);
/* 1156 */       bool2 = localSet.contains(localAWTKeyStroke2);
/*      */       
/* 1158 */       if ((bool1) || (bool2)) {
/* 1159 */         consumeTraversalKey(paramKeyEvent);
/* 1160 */         if (bool1) {
/* 1161 */           focusPreviousComponent(paramComponent);
/*      */         }
/* 1163 */         return;
/*      */       }
/*      */       
/* 1166 */       localSet = paramComponent.getFocusTraversalKeys(2);
/*      */       
/* 1168 */       bool1 = localSet.contains(localAWTKeyStroke1);
/* 1169 */       bool2 = localSet.contains(localAWTKeyStroke2);
/*      */       
/* 1171 */       if ((bool1) || (bool2)) {
/* 1172 */         consumeTraversalKey(paramKeyEvent);
/* 1173 */         if (bool1) {
/* 1174 */           upFocusCycle(paramComponent);
/*      */         }
/* 1176 */         return;
/*      */       }
/*      */       
/* 1179 */       if ((!(paramComponent instanceof Container)) || 
/* 1180 */         (!((Container)paramComponent).isFocusCycleRoot())) {
/* 1181 */         return;
/*      */       }
/*      */       
/* 1184 */       localSet = paramComponent.getFocusTraversalKeys(3);
/*      */       
/* 1186 */       bool1 = localSet.contains(localAWTKeyStroke1);
/* 1187 */       bool2 = localSet.contains(localAWTKeyStroke2);
/*      */       
/* 1189 */       if ((bool1) || (bool2)) {
/* 1190 */         consumeTraversalKey(paramKeyEvent);
/* 1191 */         if (bool1) {
/* 1192 */           downFocusCycle((Container)paramComponent);
/*      */         }
/*      */       }
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
/*      */   protected synchronized void enqueueKeyEvents(long paramLong, Component paramComponent)
/*      */   {
/* 1215 */     if (paramComponent == null) {
/* 1216 */       return;
/*      */     }
/*      */     
/* 1219 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1220 */       focusLog.finer("Enqueue at {0} for {1}", new Object[] {
/* 1221 */         Long.valueOf(paramLong), paramComponent });
/*      */     }
/*      */     
/* 1224 */     int i = 0;
/* 1225 */     int j = this.typeAheadMarkers.size();
/* 1226 */     ListIterator localListIterator = this.typeAheadMarkers.listIterator(j);
/* 1228 */     for (; 
/* 1228 */         j > 0; j--) {
/* 1229 */       TypeAheadMarker localTypeAheadMarker = (TypeAheadMarker)localListIterator.previous();
/* 1230 */       if (localTypeAheadMarker.after <= paramLong) {
/* 1231 */         i = j;
/* 1232 */         break;
/*      */       }
/*      */     }
/*      */     
/* 1236 */     this.typeAheadMarkers.add(i, new TypeAheadMarker(paramLong, paramComponent));
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
/*      */   protected synchronized void dequeueKeyEvents(long paramLong, Component paramComponent)
/*      */   {
/* 1257 */     if (paramComponent == null) {
/* 1258 */       return;
/*      */     }
/*      */     
/* 1261 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1262 */       focusLog.finer("Dequeue at {0} for {1}", new Object[] {
/* 1263 */         Long.valueOf(paramLong), paramComponent });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1268 */     ListIterator localListIterator = this.typeAheadMarkers.listIterator(paramLong >= 0L ? this.typeAheadMarkers.size() : 0);
/*      */     TypeAheadMarker localTypeAheadMarker;
/* 1270 */     if (paramLong < 0L) {
/* 1271 */       do { if (!localListIterator.hasNext()) break;
/* 1272 */         localTypeAheadMarker = (TypeAheadMarker)localListIterator.next();
/* 1273 */       } while (localTypeAheadMarker.untilFocused != paramComponent);
/*      */       
/* 1275 */       localListIterator.remove();
/* 1276 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1280 */     while (localListIterator.hasPrevious()) {
/* 1281 */       localTypeAheadMarker = (TypeAheadMarker)localListIterator.previous();
/* 1282 */       if ((localTypeAheadMarker.untilFocused == paramComponent) && (localTypeAheadMarker.after == paramLong))
/*      */       {
/*      */ 
/* 1285 */         localListIterator.remove();
/* 1286 */         return;
/*      */       }
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
/*      */   protected synchronized void discardKeyEvents(Component paramComponent)
/*      */   {
/* 1303 */     if (paramComponent == null) {
/* 1304 */       return;
/*      */     }
/*      */     
/* 1307 */     long l = -1L;
/*      */     
/* 1309 */     for (Iterator localIterator = this.typeAheadMarkers.iterator(); localIterator.hasNext();) {
/* 1310 */       TypeAheadMarker localTypeAheadMarker = (TypeAheadMarker)localIterator.next();
/* 1311 */       Object localObject = localTypeAheadMarker.untilFocused;
/* 1312 */       int i = localObject == paramComponent ? 1 : 0;
/* 1313 */       while ((i == 0) && (localObject != null) && (!(localObject instanceof Window))) {
/* 1314 */         localObject = ((Component)localObject).getParent();
/* 1315 */         i = localObject == paramComponent ? 1 : 0;
/*      */       }
/* 1317 */       if (i != 0) {
/* 1318 */         if (l < 0L) {
/* 1319 */           l = localTypeAheadMarker.after;
/*      */         }
/* 1321 */         localIterator.remove();
/* 1322 */       } else if (l >= 0L) {
/* 1323 */         purgeStampedEvents(l, localTypeAheadMarker.after);
/* 1324 */         l = -1L;
/*      */       }
/*      */     }
/*      */     
/* 1328 */     purgeStampedEvents(l, -1L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void purgeStampedEvents(long paramLong1, long paramLong2)
/*      */   {
/* 1337 */     if (paramLong1 < 0L) {
/* 1338 */       return;
/*      */     }
/*      */     
/* 1341 */     for (Iterator localIterator = this.enqueuedKeyEvents.iterator(); localIterator.hasNext();) {
/* 1342 */       KeyEvent localKeyEvent = (KeyEvent)localIterator.next();
/* 1343 */       long l = localKeyEvent.getWhen();
/*      */       
/* 1345 */       if ((paramLong1 < l) && ((paramLong2 < 0L) || (l <= paramLong2))) {
/* 1346 */         localIterator.remove();
/*      */       }
/*      */       
/* 1349 */       if ((paramLong2 >= 0L) && (l > paramLong2)) {
/*      */         break;
/*      */       }
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
/*      */   public void focusPreviousComponent(Component paramComponent)
/*      */   {
/* 1365 */     if (paramComponent != null) {
/* 1366 */       paramComponent.transferFocusBackward();
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
/*      */   public void focusNextComponent(Component paramComponent)
/*      */   {
/* 1380 */     if (paramComponent != null) {
/* 1381 */       paramComponent.transferFocus();
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
/*      */   public void upFocusCycle(Component paramComponent)
/*      */   {
/* 1398 */     if (paramComponent != null) {
/* 1399 */       paramComponent.transferFocusUpCycle();
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
/*      */   public void downFocusCycle(Container paramContainer)
/*      */   {
/* 1415 */     if ((paramContainer != null) && (paramContainer.isFocusCycleRoot())) {
/* 1416 */       paramContainer.transferFocusDownCycle();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/DefaultKeyboardFocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */