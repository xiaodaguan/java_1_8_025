/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.peer.KeyboardFocusManagerPeer;
/*      */ import java.awt.peer.LightweightPeer;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyChangeSupport;
/*      */ import java.beans.PropertyVetoException;
/*      */ import java.beans.VetoableChangeListener;
/*      */ import java.beans.VetoableChangeSupport;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Field;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.KeyboardFocusManagerAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.CausedFocusEvent.Cause;
/*      */ import sun.awt.KeyboardFocusManagerPeerProvider;
/*      */ import sun.awt.SunToolkit;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class KeyboardFocusManager
/*      */   implements KeyEventDispatcher, KeyEventPostProcessor
/*      */ {
/*      */   private static final PlatformLogger focusLog;
/*      */   transient KeyboardFocusManagerPeer peer;
/*      */   
/*      */   static
/*      */   {
/*  112 */     focusLog = PlatformLogger.getLogger("java.awt.focus.KeyboardFocusManager");
/*      */     
/*      */ 
/*      */ 
/*  116 */     Toolkit.loadLibraries();
/*  117 */     if (!GraphicsEnvironment.isHeadless()) {
/*  118 */       initIDs();
/*      */     }
/*  120 */     AWTAccessor.setKeyboardFocusManagerAccessor(new AWTAccessor.KeyboardFocusManagerAccessor()
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       public int shouldNativelyFocusHeavyweight(Component paramAnonymousComponent1, Component paramAnonymousComponent2, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, long paramAnonymousLong, CausedFocusEvent.Cause paramAnonymousCause)
/*      */       {
/*      */ 
/*      */ 
/*  129 */         return KeyboardFocusManager.shouldNativelyFocusHeavyweight(paramAnonymousComponent1, paramAnonymousComponent2, paramAnonymousBoolean1, paramAnonymousBoolean2, paramAnonymousLong, paramAnonymousCause);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       public boolean processSynchronousLightweightTransfer(Component paramAnonymousComponent1, Component paramAnonymousComponent2, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, long paramAnonymousLong)
/*      */       {
/*  138 */         return KeyboardFocusManager.processSynchronousLightweightTransfer(paramAnonymousComponent1, paramAnonymousComponent2, paramAnonymousBoolean1, paramAnonymousBoolean2, paramAnonymousLong);
/*      */       }
/*      */       
/*      */       public void removeLastFocusRequest(Component paramAnonymousComponent) {
/*  142 */         KeyboardFocusManager.removeLastFocusRequest(paramAnonymousComponent);
/*      */       }
/*      */       
/*  145 */       public void setMostRecentFocusOwner(Window paramAnonymousWindow, Component paramAnonymousComponent) { KeyboardFocusManager.setMostRecentFocusOwner(paramAnonymousWindow, paramAnonymousComponent); }
/*      */       
/*      */       public KeyboardFocusManager getCurrentKeyboardFocusManager(AppContext paramAnonymousAppContext) {
/*  148 */         return KeyboardFocusManager.getCurrentKeyboardFocusManager(paramAnonymousAppContext);
/*      */       }
/*      */       
/*  151 */       public Container getCurrentFocusCycleRoot() { return KeyboardFocusManager.currentFocusCycleRoot; }
/*      */     });
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
/*  164 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.KeyboardFocusManager");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int FORWARD_TRAVERSAL_KEYS = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int BACKWARD_TRAVERSAL_KEYS = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int UP_CYCLE_TRAVERSAL_KEYS = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int DOWN_CYCLE_TRAVERSAL_KEYS = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int TRAVERSAL_KEY_LENGTH = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Component focusOwner;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Component permanentFocusOwner;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Window focusedWindow;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Window activeWindow;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static KeyboardFocusManager getCurrentKeyboardFocusManager()
/*      */   {
/*  216 */     return getCurrentKeyboardFocusManager(AppContext.getAppContext());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static synchronized KeyboardFocusManager getCurrentKeyboardFocusManager(AppContext paramAppContext)
/*      */   {
/*  223 */     Object localObject = (KeyboardFocusManager)paramAppContext.get(KeyboardFocusManager.class);
/*  224 */     if (localObject == null) {
/*  225 */       localObject = new DefaultKeyboardFocusManager();
/*  226 */       paramAppContext.put(KeyboardFocusManager.class, localObject);
/*      */     }
/*  228 */     return (KeyboardFocusManager)localObject;
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
/*      */   public static void setCurrentKeyboardFocusManager(KeyboardFocusManager paramKeyboardFocusManager)
/*      */     throws SecurityException
/*      */   {
/*  251 */     checkReplaceKFMPermission();
/*      */     
/*  253 */     KeyboardFocusManager localKeyboardFocusManager = null;
/*      */     
/*  255 */     synchronized (KeyboardFocusManager.class) {
/*  256 */       AppContext localAppContext = AppContext.getAppContext();
/*      */       
/*  258 */       if (paramKeyboardFocusManager != null) {
/*  259 */         localKeyboardFocusManager = getCurrentKeyboardFocusManager(localAppContext);
/*      */         
/*  261 */         localAppContext.put(KeyboardFocusManager.class, paramKeyboardFocusManager);
/*      */       } else {
/*  263 */         localKeyboardFocusManager = getCurrentKeyboardFocusManager(localAppContext);
/*  264 */         localAppContext.remove(KeyboardFocusManager.class);
/*      */       }
/*      */     }
/*      */     
/*  268 */     if (localKeyboardFocusManager != null) {
/*  269 */       localKeyboardFocusManager.firePropertyChange("managingFocus", Boolean.TRUE, Boolean.FALSE);
/*      */     }
/*      */     
/*      */ 
/*  273 */     if (paramKeyboardFocusManager != null) {
/*  274 */       paramKeyboardFocusManager.firePropertyChange("managingFocus", Boolean.FALSE, Boolean.TRUE);
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
/*  314 */   private FocusTraversalPolicy defaultPolicy = new DefaultFocusTraversalPolicy();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  320 */   private static final String[] defaultFocusTraversalKeyPropertyNames = { "forwardDefaultFocusTraversalKeys", "backwardDefaultFocusTraversalKeys", "upCycleDefaultFocusTraversalKeys", "downCycleDefaultFocusTraversalKeys" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  330 */   private static final AWTKeyStroke[][] defaultFocusTraversalKeyStrokes = { {
/*      */   
/*  332 */     AWTKeyStroke.getAWTKeyStroke(9, 0, false), 
/*  333 */     AWTKeyStroke.getAWTKeyStroke(9, 130, false) }, {
/*      */     
/*      */ 
/*  336 */     AWTKeyStroke.getAWTKeyStroke(9, 65, false), 
/*  337 */     AWTKeyStroke.getAWTKeyStroke(9, 195, false) }, new AWTKeyStroke[0], new AWTKeyStroke[0] };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  351 */   private Set<AWTKeyStroke>[] defaultFocusTraversalKeys = new Set[4];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Container currentFocusCycleRoot;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private VetoableChangeSupport vetoableSupport;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private PropertyChangeSupport changeSupport;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private LinkedList<KeyEventDispatcher> keyEventDispatchers;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private LinkedList<KeyEventPostProcessor> keyEventPostProcessors;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  393 */   private static Map<Window, WeakReference<Component>> mostRecentFocusOwners = new WeakHashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static AWTPermission replaceKeyboardFocusManagerPermission;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  404 */   transient SequencedEvent currentSequencedEvent = null;
/*      */   
/*      */   final void setCurrentSequencedEvent(SequencedEvent paramSequencedEvent) {
/*  407 */     synchronized (SequencedEvent.class) {
/*  408 */       assert ((paramSequencedEvent == null) || (this.currentSequencedEvent == null));
/*  409 */       this.currentSequencedEvent = paramSequencedEvent;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static Set<AWTKeyStroke> initFocusTraversalKeysSet(String paramString, Set<AWTKeyStroke> paramSet)
/*      */   {
/*  420 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
/*  421 */     while (localStringTokenizer.hasMoreTokens()) {
/*  422 */       paramSet.add(AWTKeyStroke.getAWTKeyStroke(localStringTokenizer.nextToken()));
/*      */     }
/*      */     
/*      */ 
/*  426 */     return paramSet.isEmpty() ? Collections.EMPTY_SET : Collections.unmodifiableSet(paramSet);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public KeyboardFocusManager()
/*      */   {
/*  433 */     for (int i = 0; i < 4; i++) {
/*  434 */       HashSet localHashSet = new HashSet();
/*  435 */       for (int j = 0; j < defaultFocusTraversalKeyStrokes[i].length; j++) {
/*  436 */         localHashSet.add(defaultFocusTraversalKeyStrokes[i][j]);
/*      */       }
/*      */       
/*      */ 
/*  440 */       this.defaultFocusTraversalKeys[i] = (localHashSet.isEmpty() ? Collections.EMPTY_SET : Collections.unmodifiableSet(localHashSet));
/*      */     }
/*  442 */     initPeer();
/*      */   }
/*      */   
/*      */   private void initPeer() {
/*  446 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  447 */     KeyboardFocusManagerPeerProvider localKeyboardFocusManagerPeerProvider = (KeyboardFocusManagerPeerProvider)localToolkit;
/*  448 */     this.peer = localKeyboardFocusManagerPeerProvider.getKeyboardFocusManagerPeer();
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
/*      */   public Component getFocusOwner()
/*      */   {
/*  466 */     synchronized (KeyboardFocusManager.class) {
/*  467 */       if (focusOwner == null) {
/*  468 */         return null;
/*      */       }
/*      */       
/*  471 */       return focusOwner.appContext == AppContext.getAppContext() ? focusOwner : null;
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
/*      */   protected Component getGlobalFocusOwner()
/*      */     throws SecurityException
/*      */   {
/*  499 */     synchronized (KeyboardFocusManager.class) {
/*  500 */       checkKFMSecurity();
/*  501 */       return focusOwner;
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
/*      */   protected void setGlobalFocusOwner(Component paramComponent)
/*      */     throws SecurityException
/*      */   {
/*  536 */     Component localComponent = null;
/*  537 */     int i = 0;
/*      */     
/*  539 */     if ((paramComponent == null) || (paramComponent.isFocusable())) {
/*  540 */       synchronized (KeyboardFocusManager.class) {
/*  541 */         checkKFMSecurity();
/*      */         
/*  543 */         localComponent = getFocusOwner();
/*      */         try
/*      */         {
/*  546 */           fireVetoableChange("focusOwner", localComponent, paramComponent);
/*      */         }
/*      */         catch (PropertyVetoException localPropertyVetoException)
/*      */         {
/*  550 */           return;
/*      */         }
/*      */         
/*  553 */         focusOwner = paramComponent;
/*      */         
/*  555 */         if ((paramComponent != null) && (
/*  556 */           (getCurrentFocusCycleRoot() == null) || 
/*  557 */           (!paramComponent.isFocusCycleRoot(getCurrentFocusCycleRoot()))))
/*      */         {
/*      */ 
/*  560 */           Container localContainer = paramComponent.getFocusCycleRootAncestor();
/*  561 */           if ((localContainer == null) && ((paramComponent instanceof Window)))
/*      */           {
/*  563 */             localContainer = (Container)paramComponent;
/*      */           }
/*  565 */           if (localContainer != null) {
/*  566 */             setGlobalCurrentFocusCycleRootPriv(localContainer);
/*      */           }
/*      */         }
/*      */         
/*  570 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/*  574 */     if (i != 0) {
/*  575 */       firePropertyChange("focusOwner", localComponent, paramComponent);
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
/*      */   public void clearFocusOwner()
/*      */   {
/*  596 */     if (getFocusOwner() != null) {
/*  597 */       clearGlobalFocusOwner();
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
/*      */   public void clearGlobalFocusOwner()
/*      */     throws SecurityException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  629 */     if (!GraphicsEnvironment.isHeadless())
/*      */     {
/*      */ 
/*  632 */       Toolkit.getDefaultToolkit();
/*      */       
/*  634 */       _clearGlobalFocusOwner();
/*      */     }
/*      */   }
/*      */   
/*  638 */   private void _clearGlobalFocusOwner() { Window localWindow = markClearGlobalFocusOwner();
/*  639 */     this.peer.clearGlobalFocusOwner(localWindow);
/*      */   }
/*      */   
/*      */   void clearGlobalFocusOwnerPriv() {
/*  643 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Void run() {
/*  645 */         KeyboardFocusManager.this.clearGlobalFocusOwner();
/*  646 */         return null;
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   Component getNativeFocusOwner() {
/*  652 */     return this.peer.getCurrentFocusOwner();
/*      */   }
/*      */   
/*      */   void setNativeFocusOwner(Component paramComponent) {
/*  656 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  657 */       focusLog.finest("Calling peer {0} setCurrentFocusOwner for {1}", new Object[] {
/*  658 */         String.valueOf(this.peer), String.valueOf(paramComponent) });
/*      */     }
/*  660 */     this.peer.setCurrentFocusOwner(paramComponent);
/*      */   }
/*      */   
/*      */   Window getNativeFocusedWindow() {
/*  664 */     return this.peer.getCurrentFocusedWindow();
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
/*      */   public Component getPermanentFocusOwner()
/*      */   {
/*  682 */     synchronized (KeyboardFocusManager.class) {
/*  683 */       if (permanentFocusOwner == null) {
/*  684 */         return null;
/*      */       }
/*      */       
/*      */ 
/*  688 */       return permanentFocusOwner.appContext == AppContext.getAppContext() ? permanentFocusOwner : null;
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
/*      */   protected Component getGlobalPermanentFocusOwner()
/*      */     throws SecurityException
/*      */   {
/*  714 */     synchronized (KeyboardFocusManager.class) {
/*  715 */       checkKFMSecurity();
/*  716 */       return permanentFocusOwner;
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
/*      */   protected void setGlobalPermanentFocusOwner(Component paramComponent)
/*      */     throws SecurityException
/*      */   {
/*  752 */     Component localComponent = null;
/*  753 */     int i = 0;
/*      */     
/*  755 */     if ((paramComponent == null) || (paramComponent.isFocusable())) {
/*  756 */       synchronized (KeyboardFocusManager.class) {
/*  757 */         checkKFMSecurity();
/*      */         
/*  759 */         localComponent = getPermanentFocusOwner();
/*      */         try
/*      */         {
/*  762 */           fireVetoableChange("permanentFocusOwner", localComponent, paramComponent);
/*      */ 
/*      */         }
/*      */         catch (PropertyVetoException localPropertyVetoException)
/*      */         {
/*  767 */           return;
/*      */         }
/*      */         
/*  770 */         permanentFocusOwner = paramComponent;
/*      */         
/*      */ 
/*  773 */         setMostRecentFocusOwner(paramComponent);
/*      */         
/*  775 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/*  779 */     if (i != 0) {
/*  780 */       firePropertyChange("permanentFocusOwner", localComponent, paramComponent);
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
/*      */   public Window getFocusedWindow()
/*      */   {
/*  796 */     synchronized (KeyboardFocusManager.class) {
/*  797 */       if (focusedWindow == null) {
/*  798 */         return null;
/*      */       }
/*      */       
/*  801 */       return focusedWindow.appContext == AppContext.getAppContext() ? focusedWindow : null;
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
/*      */   protected Window getGlobalFocusedWindow()
/*      */     throws SecurityException
/*      */   {
/*  821 */     synchronized (KeyboardFocusManager.class) {
/*  822 */       checkKFMSecurity();
/*  823 */       return focusedWindow;
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
/*      */   protected void setGlobalFocusedWindow(Window paramWindow)
/*      */     throws SecurityException
/*      */   {
/*  855 */     Window localWindow = null;
/*  856 */     int i = 0;
/*      */     
/*  858 */     if ((paramWindow == null) || (paramWindow.isFocusableWindow())) {
/*  859 */       synchronized (KeyboardFocusManager.class) {
/*  860 */         checkKFMSecurity();
/*      */         
/*  862 */         localWindow = getFocusedWindow();
/*      */         try
/*      */         {
/*  865 */           fireVetoableChange("focusedWindow", localWindow, paramWindow);
/*      */         }
/*      */         catch (PropertyVetoException localPropertyVetoException)
/*      */         {
/*  869 */           return;
/*      */         }
/*      */         
/*  872 */         focusedWindow = paramWindow;
/*  873 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/*  877 */     if (i != 0) {
/*  878 */       firePropertyChange("focusedWindow", localWindow, paramWindow);
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
/*      */   public Window getActiveWindow()
/*      */   {
/*  897 */     synchronized (KeyboardFocusManager.class) {
/*  898 */       if (activeWindow == null) {
/*  899 */         return null;
/*      */       }
/*      */       
/*  902 */       return activeWindow.appContext == AppContext.getAppContext() ? activeWindow : null;
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
/*      */   protected Window getGlobalActiveWindow()
/*      */     throws SecurityException
/*      */   {
/*  925 */     synchronized (KeyboardFocusManager.class) {
/*  926 */       checkKFMSecurity();
/*  927 */       return activeWindow;
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
/*      */   protected void setGlobalActiveWindow(Window paramWindow)
/*      */     throws SecurityException
/*      */   {
/*      */     Window localWindow;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  961 */     synchronized (KeyboardFocusManager.class) {
/*  962 */       checkKFMSecurity();
/*      */       
/*  964 */       localWindow = getActiveWindow();
/*  965 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  966 */         focusLog.finer("Setting global active window to " + paramWindow + ", old active " + localWindow);
/*      */       }
/*      */       try
/*      */       {
/*  970 */         fireVetoableChange("activeWindow", localWindow, paramWindow);
/*      */       }
/*      */       catch (PropertyVetoException localPropertyVetoException)
/*      */       {
/*  974 */         return;
/*      */       }
/*      */       
/*  977 */       activeWindow = paramWindow;
/*      */     }
/*      */     
/*  980 */     firePropertyChange("activeWindow", localWindow, paramWindow);
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
/*      */   public synchronized FocusTraversalPolicy getDefaultFocusTraversalPolicy()
/*      */   {
/*  994 */     return this.defaultPolicy;
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
/*      */   public void setDefaultFocusTraversalPolicy(FocusTraversalPolicy paramFocusTraversalPolicy)
/*      */   {
/* 1015 */     if (paramFocusTraversalPolicy == null) {
/* 1016 */       throw new IllegalArgumentException("default focus traversal policy cannot be null");
/*      */     }
/*      */     
/*      */     FocusTraversalPolicy localFocusTraversalPolicy;
/*      */     
/* 1021 */     synchronized (this) {
/* 1022 */       localFocusTraversalPolicy = this.defaultPolicy;
/* 1023 */       this.defaultPolicy = paramFocusTraversalPolicy;
/*      */     }
/*      */     
/* 1026 */     firePropertyChange("defaultFocusTraversalPolicy", localFocusTraversalPolicy, paramFocusTraversalPolicy);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDefaultFocusTraversalKeys(int paramInt, Set<? extends AWTKeyStroke> paramSet)
/*      */   {
/* 1122 */     if ((paramInt < 0) || (paramInt >= 4)) {
/* 1123 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/* 1125 */     if (paramSet == null) {
/* 1126 */       throw new IllegalArgumentException("cannot set null Set of default focus traversal keys");
/*      */     }
/*      */     
/*      */     Set localSet;
/*      */     
/* 1131 */     synchronized (this) {
/* 1132 */       for (AWTKeyStroke localAWTKeyStroke : paramSet)
/*      */       {
/* 1134 */         if (localAWTKeyStroke == null) {
/* 1135 */           throw new IllegalArgumentException("cannot set null focus traversal key");
/*      */         }
/*      */         
/* 1138 */         if (localAWTKeyStroke.getKeyChar() != 65535) {
/* 1139 */           throw new IllegalArgumentException("focus traversal keys cannot map to KEY_TYPED events");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1144 */         for (int i = 0; i < 4; i++) {
/* 1145 */           if (i != paramInt)
/*      */           {
/*      */ 
/*      */ 
/* 1149 */             if (this.defaultFocusTraversalKeys[i].contains(localAWTKeyStroke)) {
/* 1150 */               throw new IllegalArgumentException("focus traversal keys must be unique for a Component");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1155 */       localSet = this.defaultFocusTraversalKeys[paramInt];
/* 1156 */       this.defaultFocusTraversalKeys[paramInt] = 
/* 1157 */         Collections.unmodifiableSet(new HashSet(paramSet));
/*      */     }
/*      */     
/* 1160 */     firePropertyChange(defaultFocusTraversalKeyPropertyNames[paramInt], localSet, paramSet);
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
/*      */   public Set<AWTKeyStroke> getDefaultFocusTraversalKeys(int paramInt)
/*      */   {
/* 1191 */     if ((paramInt < 0) || (paramInt >= 4)) {
/* 1192 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */     
/*      */ 
/* 1196 */     return this.defaultFocusTraversalKeys[paramInt];
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
/*      */   public Container getCurrentFocusCycleRoot()
/*      */   {
/* 1216 */     synchronized (KeyboardFocusManager.class) {
/* 1217 */       if (currentFocusCycleRoot == null) {
/* 1218 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1222 */       return currentFocusCycleRoot.appContext == AppContext.getAppContext() ? currentFocusCycleRoot : null;
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
/*      */   protected Container getGlobalCurrentFocusCycleRoot()
/*      */     throws SecurityException
/*      */   {
/* 1248 */     synchronized (KeyboardFocusManager.class) {
/* 1249 */       checkKFMSecurity();
/* 1250 */       return currentFocusCycleRoot;
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
/*      */   public void setGlobalCurrentFocusCycleRoot(Container paramContainer)
/*      */     throws SecurityException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Container localContainer;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1284 */     synchronized (KeyboardFocusManager.class) {
/* 1285 */       localContainer = getCurrentFocusCycleRoot();
/* 1286 */       currentFocusCycleRoot = paramContainer;
/*      */     }
/*      */     
/* 1289 */     firePropertyChange("currentFocusCycleRoot", localContainer, paramContainer);
/*      */   }
/*      */   
/*      */   void setGlobalCurrentFocusCycleRootPriv(final Container paramContainer)
/*      */   {
/* 1294 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Void run() {
/* 1296 */         KeyboardFocusManager.this.setGlobalCurrentFocusCycleRoot(paramContainer);
/* 1297 */         return null;
/*      */       }
/*      */     });
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
/*      */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 1334 */     if (paramPropertyChangeListener != null) {
/* 1335 */       synchronized (this) {
/* 1336 */         if (this.changeSupport == null) {
/* 1337 */           this.changeSupport = new PropertyChangeSupport(this);
/*      */         }
/* 1339 */         this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 1357 */     if (paramPropertyChangeListener != null) {
/* 1358 */       synchronized (this) {
/* 1359 */         if (this.changeSupport != null) {
/* 1360 */           this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public synchronized PropertyChangeListener[] getPropertyChangeListeners()
/*      */   {
/* 1381 */     if (this.changeSupport == null) {
/* 1382 */       this.changeSupport = new PropertyChangeSupport(this);
/*      */     }
/* 1384 */     return this.changeSupport.getPropertyChangeListeners();
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
/*      */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 1421 */     if (paramPropertyChangeListener != null) {
/* 1422 */       synchronized (this) {
/* 1423 */         if (this.changeSupport == null) {
/* 1424 */           this.changeSupport = new PropertyChangeSupport(this);
/*      */         }
/* 1426 */         this.changeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*      */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 1447 */     if (paramPropertyChangeListener != null) {
/* 1448 */       synchronized (this) {
/* 1449 */         if (this.changeSupport != null) {
/* 1450 */           this.changeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*      */   public synchronized PropertyChangeListener[] getPropertyChangeListeners(String paramString)
/*      */   {
/* 1470 */     if (this.changeSupport == null) {
/* 1471 */       this.changeSupport = new PropertyChangeSupport(this);
/*      */     }
/* 1473 */     return this.changeSupport.getPropertyChangeListeners(paramString);
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
/*      */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2)
/*      */   {
/* 1488 */     if (paramObject1 == paramObject2) {
/* 1489 */       return;
/*      */     }
/* 1491 */     PropertyChangeSupport localPropertyChangeSupport = this.changeSupport;
/* 1492 */     if (localPropertyChangeSupport != null) {
/* 1493 */       localPropertyChangeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
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
/*      */   public void addVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener)
/*      */   {
/* 1515 */     if (paramVetoableChangeListener != null) {
/* 1516 */       synchronized (this) {
/* 1517 */         if (this.vetoableSupport == null) {
/* 1518 */           this.vetoableSupport = new VetoableChangeSupport(this);
/*      */         }
/*      */         
/* 1521 */         this.vetoableSupport.addVetoableChangeListener(paramVetoableChangeListener);
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
/*      */   public void removeVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener)
/*      */   {
/* 1539 */     if (paramVetoableChangeListener != null) {
/* 1540 */       synchronized (this) {
/* 1541 */         if (this.vetoableSupport != null) {
/* 1542 */           this.vetoableSupport.removeVetoableChangeListener(paramVetoableChangeListener);
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
/*      */   public synchronized VetoableChangeListener[] getVetoableChangeListeners()
/*      */   {
/* 1563 */     if (this.vetoableSupport == null) {
/* 1564 */       this.vetoableSupport = new VetoableChangeSupport(this);
/*      */     }
/* 1566 */     return this.vetoableSupport.getVetoableChangeListeners();
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
/*      */   public void addVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener)
/*      */   {
/* 1589 */     if (paramVetoableChangeListener != null) {
/* 1590 */       synchronized (this) {
/* 1591 */         if (this.vetoableSupport == null) {
/* 1592 */           this.vetoableSupport = new VetoableChangeSupport(this);
/*      */         }
/*      */         
/* 1595 */         this.vetoableSupport.addVetoableChangeListener(paramString, paramVetoableChangeListener);
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
/*      */   public void removeVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener)
/*      */   {
/* 1616 */     if (paramVetoableChangeListener != null) {
/* 1617 */       synchronized (this) {
/* 1618 */         if (this.vetoableSupport != null) {
/* 1619 */           this.vetoableSupport.removeVetoableChangeListener(paramString, paramVetoableChangeListener);
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
/*      */   public synchronized VetoableChangeListener[] getVetoableChangeListeners(String paramString)
/*      */   {
/* 1640 */     if (this.vetoableSupport == null) {
/* 1641 */       this.vetoableSupport = new VetoableChangeSupport(this);
/*      */     }
/* 1643 */     return this.vetoableSupport.getVetoableChangeListeners(paramString);
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
/*      */   protected void fireVetoableChange(String paramString, Object paramObject1, Object paramObject2)
/*      */     throws PropertyVetoException
/*      */   {
/* 1666 */     if (paramObject1 == paramObject2) {
/* 1667 */       return;
/*      */     }
/* 1669 */     VetoableChangeSupport localVetoableChangeSupport = this.vetoableSupport;
/*      */     
/* 1671 */     if (localVetoableChangeSupport != null) {
/* 1672 */       localVetoableChangeSupport.fireVetoableChange(paramString, paramObject1, paramObject2);
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
/*      */   public void addKeyEventDispatcher(KeyEventDispatcher paramKeyEventDispatcher)
/*      */   {
/* 1700 */     if (paramKeyEventDispatcher != null) {
/* 1701 */       synchronized (this) {
/* 1702 */         if (this.keyEventDispatchers == null) {
/* 1703 */           this.keyEventDispatchers = new LinkedList();
/*      */         }
/* 1705 */         this.keyEventDispatchers.add(paramKeyEventDispatcher);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeKeyEventDispatcher(KeyEventDispatcher paramKeyEventDispatcher)
/*      */   {
/* 1731 */     if (paramKeyEventDispatcher != null) {
/* 1732 */       synchronized (this) {
/* 1733 */         if (this.keyEventDispatchers != null) {
/* 1734 */           this.keyEventDispatchers.remove(paramKeyEventDispatcher);
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
/*      */   protected synchronized List<KeyEventDispatcher> getKeyEventDispatchers()
/*      */   {
/* 1757 */     return this.keyEventDispatchers != null ? (List)this.keyEventDispatchers.clone() : null;
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
/*      */   public void addKeyEventPostProcessor(KeyEventPostProcessor paramKeyEventPostProcessor)
/*      */   {
/* 1788 */     if (paramKeyEventPostProcessor != null) {
/* 1789 */       synchronized (this) {
/* 1790 */         if (this.keyEventPostProcessors == null) {
/* 1791 */           this.keyEventPostProcessors = new LinkedList();
/*      */         }
/* 1793 */         this.keyEventPostProcessors.add(paramKeyEventPostProcessor);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeKeyEventPostProcessor(KeyEventPostProcessor paramKeyEventPostProcessor)
/*      */   {
/* 1821 */     if (paramKeyEventPostProcessor != null) {
/* 1822 */       synchronized (this) {
/* 1823 */         if (this.keyEventPostProcessors != null) {
/* 1824 */           this.keyEventPostProcessors.remove(paramKeyEventPostProcessor);
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
/*      */ 
/*      */   protected List<KeyEventPostProcessor> getKeyEventPostProcessors()
/*      */   {
/* 1848 */     return this.keyEventPostProcessors != null ? (List)this.keyEventPostProcessors.clone() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void setMostRecentFocusOwner(Component paramComponent)
/*      */   {
/* 1855 */     Object localObject = paramComponent;
/* 1856 */     while ((localObject != null) && (!(localObject instanceof Window))) {
/* 1857 */       localObject = ((Component)localObject).parent;
/*      */     }
/* 1859 */     if (localObject != null) {
/* 1860 */       setMostRecentFocusOwner((Window)localObject, paramComponent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static synchronized void setMostRecentFocusOwner(Window paramWindow, Component paramComponent)
/*      */   {
/* 1869 */     WeakReference localWeakReference = null;
/* 1870 */     if (paramComponent != null) {
/* 1871 */       localWeakReference = new WeakReference(paramComponent);
/*      */     }
/* 1873 */     mostRecentFocusOwners.put(paramWindow, localWeakReference);
/*      */   }
/*      */   
/*      */   static void clearMostRecentFocusOwner(Component paramComponent)
/*      */   {
/* 1878 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     Container localContainer;
/* 1882 */     synchronized (paramComponent.getTreeLock()) {
/* 1883 */       localContainer = paramComponent.getParent();
/* 1884 */       while ((localContainer != null) && (!(localContainer instanceof Window))) {
/* 1885 */         localContainer = localContainer.getParent();
/*      */       }
/*      */     }
/*      */     
/* 1889 */     synchronized (KeyboardFocusManager.class) {
/* 1890 */       if ((localContainer != null) && 
/* 1891 */         (getMostRecentFocusOwner((Window)localContainer) == paramComponent))
/*      */       {
/* 1893 */         setMostRecentFocusOwner((Window)localContainer, null);
/*      */       }
/*      */       
/* 1896 */       if (localContainer != null) {
/* 1897 */         Window localWindow = (Window)localContainer;
/* 1898 */         if (localWindow.getTemporaryLostComponent() == paramComponent) {
/* 1899 */           localWindow.setTemporaryLostComponent(null);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static synchronized Component getMostRecentFocusOwner(Window paramWindow)
/*      */   {
/* 1911 */     WeakReference localWeakReference = (WeakReference)mostRecentFocusOwners.get(paramWindow);
/* 1912 */     return localWeakReference == null ? null : (Component)localWeakReference.get();
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
/*      */   public final void redispatchEvent(Component paramComponent, AWTEvent paramAWTEvent)
/*      */   {
/* 1953 */     paramAWTEvent.focusManagerIsDispatching = true;
/* 1954 */     paramComponent.dispatchEvent(paramAWTEvent);
/* 1955 */     paramAWTEvent.focusManagerIsDispatching = false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void focusNextComponent()
/*      */   {
/* 2109 */     Component localComponent = getFocusOwner();
/* 2110 */     if (localComponent != null) {
/* 2111 */       focusNextComponent(localComponent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final void focusPreviousComponent()
/*      */   {
/* 2119 */     Component localComponent = getFocusOwner();
/* 2120 */     if (localComponent != null) {
/* 2121 */       focusPreviousComponent(localComponent);
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
/*      */   public final void upFocusCycle()
/*      */   {
/* 2135 */     Component localComponent = getFocusOwner();
/* 2136 */     if (localComponent != null) {
/* 2137 */       upFocusCycle(localComponent);
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
/*      */   public final void downFocusCycle()
/*      */   {
/* 2151 */     Component localComponent = getFocusOwner();
/* 2152 */     if ((localComponent instanceof Container)) {
/* 2153 */       downFocusCycle((Container)localComponent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void dumpRequests()
/*      */   {
/* 2161 */     System.err.println(">>> Requests dump, time: " + System.currentTimeMillis());
/* 2162 */     synchronized (heavyweightRequests) {
/* 2163 */       for (HeavyweightFocusRequest localHeavyweightFocusRequest : heavyweightRequests) {
/* 2164 */         System.err.println(">>> Req: " + localHeavyweightFocusRequest);
/*      */       }
/*      */     }
/* 2167 */     System.err.println("");
/*      */   }
/*      */   
/*      */   private static final class LightweightFocusRequest {
/*      */     final Component component;
/*      */     final boolean temporary;
/*      */     final CausedFocusEvent.Cause cause;
/*      */     
/*      */     LightweightFocusRequest(Component paramComponent, boolean paramBoolean, CausedFocusEvent.Cause paramCause) {
/* 2176 */       this.component = paramComponent;
/* 2177 */       this.temporary = paramBoolean;
/* 2178 */       this.cause = paramCause;
/*      */     }
/*      */     
/* 2181 */     public String toString() { return "LightweightFocusRequest[component=" + this.component + ",temporary=" + this.temporary + ", cause=" + this.cause + "]"; }
/*      */   }
/*      */   
/*      */ 
/*      */   private static final class HeavyweightFocusRequest
/*      */   {
/*      */     final Component heavyweight;
/*      */     
/*      */     final LinkedList<KeyboardFocusManager.LightweightFocusRequest> lightweightRequests;
/* 2190 */     static final HeavyweightFocusRequest CLEAR_GLOBAL_FOCUS_OWNER = new HeavyweightFocusRequest();
/*      */     
/*      */     private HeavyweightFocusRequest()
/*      */     {
/* 2194 */       this.heavyweight = null;
/* 2195 */       this.lightweightRequests = null;
/*      */     }
/*      */     
/*      */     HeavyweightFocusRequest(Component paramComponent1, Component paramComponent2, boolean paramBoolean, CausedFocusEvent.Cause paramCause)
/*      */     {
/* 2200 */       if ((KeyboardFocusManager.log.isLoggable(PlatformLogger.Level.FINE)) && 
/* 2201 */         (paramComponent1 == null)) {
/* 2202 */         KeyboardFocusManager.log.fine("Assertion (heavyweight != null) failed");
/*      */       }
/*      */       
/*      */ 
/* 2206 */       this.heavyweight = paramComponent1;
/* 2207 */       this.lightweightRequests = new LinkedList();
/* 2208 */       addLightweightRequest(paramComponent2, paramBoolean, paramCause);
/*      */     }
/*      */     
/*      */     boolean addLightweightRequest(Component paramComponent, boolean paramBoolean, CausedFocusEvent.Cause paramCause) {
/* 2212 */       if (KeyboardFocusManager.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 2213 */         if (this == CLEAR_GLOBAL_FOCUS_OWNER) {
/* 2214 */           KeyboardFocusManager.log.fine("Assertion (this != HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER) failed");
/*      */         }
/* 2216 */         if (paramComponent == null) {
/* 2217 */           KeyboardFocusManager.log.fine("Assertion (descendant != null) failed");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2222 */       Object localObject = this.lightweightRequests.size() > 0 ? ((KeyboardFocusManager.LightweightFocusRequest)this.lightweightRequests.getLast()).component : null;
/*      */       
/*      */ 
/* 2225 */       if (paramComponent != localObject)
/*      */       {
/*      */ 
/* 2228 */         this.lightweightRequests.add(new KeyboardFocusManager.LightweightFocusRequest(paramComponent, paramBoolean, paramCause));
/* 2229 */         return true;
/*      */       }
/* 2231 */       return false;
/*      */     }
/*      */     
/*      */     KeyboardFocusManager.LightweightFocusRequest getFirstLightweightRequest()
/*      */     {
/* 2236 */       if (this == CLEAR_GLOBAL_FOCUS_OWNER) {
/* 2237 */         return null;
/*      */       }
/* 2239 */       return (KeyboardFocusManager.LightweightFocusRequest)this.lightweightRequests.getFirst();
/*      */     }
/*      */     
/* 2242 */     public String toString() { int i = 1;
/* 2243 */       String str = "HeavyweightFocusRequest[heavweight=" + this.heavyweight + ",lightweightRequests=";
/*      */       
/* 2245 */       if (this.lightweightRequests == null) {
/* 2246 */         str = str + null;
/*      */       } else {
/* 2248 */         str = str + "[";
/*      */         
/* 2250 */         for (KeyboardFocusManager.LightweightFocusRequest localLightweightFocusRequest : this.lightweightRequests) {
/* 2251 */           if (i != 0) {
/* 2252 */             i = 0;
/*      */           } else {
/* 2254 */             str = str + ",";
/*      */           }
/* 2256 */           str = str + localLightweightFocusRequest;
/*      */         }
/* 2258 */         str = str + "]";
/*      */       }
/* 2260 */       str = str + "]";
/* 2261 */       return str;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2270 */   private static LinkedList<HeavyweightFocusRequest> heavyweightRequests = new LinkedList();
/*      */   
/*      */   private static LinkedList<LightweightFocusRequest> currentLightweightRequests;
/*      */   private static boolean clearingCurrentLightweightRequests;
/* 2274 */   private static boolean allowSyncFocusRequests = true;
/* 2275 */   private static Component newFocusOwner = null;
/*      */   
/*      */   private static volatile boolean disableRestoreFocus;
/*      */   
/*      */   static final int SNFH_FAILURE = 0;
/*      */   static final int SNFH_SUCCESS_HANDLED = 1;
/*      */   static final int SNFH_SUCCESS_PROCEED = 2;
/*      */   static Field proxyActive;
/*      */   
/*      */   static boolean processSynchronousLightweightTransfer(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong)
/*      */   {
/* 2286 */     Window localWindow = SunToolkit.getContainingWindow(paramComponent1);
/* 2287 */     if ((localWindow == null) || (!localWindow.syncLWRequests)) {
/* 2288 */       return false;
/*      */     }
/* 2290 */     if (paramComponent2 == null)
/*      */     {
/*      */ 
/*      */ 
/* 2294 */       paramComponent2 = paramComponent1;
/*      */     }
/*      */     
/* 2297 */     KeyboardFocusManager localKeyboardFocusManager = getCurrentKeyboardFocusManager(SunToolkit.targetToAppContext(paramComponent2));
/*      */     
/* 2299 */     FocusEvent localFocusEvent1 = null;
/* 2300 */     FocusEvent localFocusEvent2 = null;
/* 2301 */     Component localComponent = localKeyboardFocusManager.getGlobalFocusOwner();
/*      */     
/* 2303 */     synchronized (heavyweightRequests) {
/* 2304 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getLastHWRequest();
/* 2305 */       if ((localHeavyweightFocusRequest == null) && 
/* 2306 */         (paramComponent1 == localKeyboardFocusManager.getNativeFocusOwner()) && (allowSyncFocusRequests))
/*      */       {
/*      */ 
/*      */ 
/* 2310 */         if (paramComponent2 == localComponent)
/*      */         {
/* 2312 */           return true;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2319 */         localKeyboardFocusManager.enqueueKeyEvents(paramLong, paramComponent2);
/*      */         
/* 2321 */         localHeavyweightFocusRequest = new HeavyweightFocusRequest(paramComponent1, paramComponent2, paramBoolean1, CausedFocusEvent.Cause.UNKNOWN);
/*      */         
/*      */ 
/* 2324 */         heavyweightRequests.add(localHeavyweightFocusRequest);
/*      */         
/* 2326 */         if (localComponent != null) {
/* 2327 */           localFocusEvent1 = new FocusEvent(localComponent, 1005, paramBoolean1, paramComponent2);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2332 */         localFocusEvent2 = new FocusEvent(paramComponent2, 1004, paramBoolean1, localComponent);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2337 */     boolean bool1 = false;
/* 2338 */     boolean bool2 = clearingCurrentLightweightRequests;
/*      */     
/* 2340 */     Throwable localThrowable = null;
/*      */     try {
/* 2342 */       clearingCurrentLightweightRequests = false;
/* 2343 */       synchronized (Component.LOCK)
/*      */       {
/* 2345 */         if ((localFocusEvent1 != null) && (localComponent != null)) {
/* 2346 */           localFocusEvent1.isPosted = true;
/* 2347 */           localThrowable = dispatchAndCatchException(localThrowable, localComponent, localFocusEvent1);
/* 2348 */           bool1 = true;
/*      */         }
/*      */         
/* 2351 */         if ((localFocusEvent2 != null) && (paramComponent2 != null)) {
/* 2352 */           localFocusEvent2.isPosted = true;
/* 2353 */           localThrowable = dispatchAndCatchException(localThrowable, paramComponent2, localFocusEvent2);
/* 2354 */           bool1 = true;
/*      */         }
/*      */       }
/*      */     } finally {
/* 2358 */       clearingCurrentLightweightRequests = bool2;
/*      */     }
/* 2360 */     if ((localThrowable instanceof RuntimeException))
/* 2361 */       throw ((RuntimeException)localThrowable);
/* 2362 */     if ((localThrowable instanceof Error)) {
/* 2363 */       throw ((Error)localThrowable);
/*      */     }
/* 2365 */     return bool1;
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
/*      */   static int shouldNativelyFocusHeavyweight(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause)
/*      */   {
/* 2390 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 2391 */       if (paramComponent1 == null) {
/* 2392 */         log.fine("Assertion (heavyweight != null) failed");
/*      */       }
/* 2394 */       if (paramLong == 0L) {
/* 2395 */         log.fine("Assertion (time != 0) failed");
/*      */       }
/*      */     }
/*      */     
/* 2399 */     if (paramComponent2 == null)
/*      */     {
/*      */ 
/*      */ 
/* 2403 */       paramComponent2 = paramComponent1;
/*      */     }
/*      */     
/*      */ 
/* 2407 */     KeyboardFocusManager localKeyboardFocusManager1 = getCurrentKeyboardFocusManager(SunToolkit.targetToAppContext(paramComponent2));
/* 2408 */     KeyboardFocusManager localKeyboardFocusManager2 = getCurrentKeyboardFocusManager();
/* 2409 */     Component localComponent1 = localKeyboardFocusManager2.getGlobalFocusOwner();
/* 2410 */     Component localComponent2 = localKeyboardFocusManager2.getNativeFocusOwner();
/* 2411 */     Window localWindow = localKeyboardFocusManager2.getNativeFocusedWindow();
/* 2412 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 2413 */       focusLog.finer("SNFH for {0} in {1}", new Object[] {
/* 2414 */         String.valueOf(paramComponent2), String.valueOf(paramComponent1) });
/*      */     }
/* 2416 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2417 */       focusLog.finest("0. Current focus owner {0}", new Object[] {
/* 2418 */         String.valueOf(localComponent1) });
/* 2419 */       focusLog.finest("0. Native focus owner {0}", new Object[] {
/* 2420 */         String.valueOf(localComponent2) });
/* 2421 */       focusLog.finest("0. Native focused window {0}", new Object[] {
/* 2422 */         String.valueOf(localWindow) });
/*      */     }
/* 2424 */     synchronized (heavyweightRequests) {
/* 2425 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getLastHWRequest();
/* 2426 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2427 */         focusLog.finest("Request {0}", new Object[] { String.valueOf(localHeavyweightFocusRequest) });
/*      */       }
/* 2429 */       if ((localHeavyweightFocusRequest == null) && (paramComponent1 == localComponent2))
/*      */       {
/* 2431 */         if (paramComponent1.getContainingWindow() == localWindow)
/*      */         {
/* 2433 */           if (paramComponent2 == localComponent1)
/*      */           {
/* 2435 */             if (focusLog.isLoggable(PlatformLogger.Level.FINEST))
/* 2436 */               focusLog.finest("1. SNFH_FAILURE for {0}", new Object[] {
/* 2437 */                 String.valueOf(paramComponent2) });
/* 2438 */             return 0;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2445 */           localKeyboardFocusManager1.enqueueKeyEvents(paramLong, paramComponent2);
/*      */           
/* 2447 */           localHeavyweightFocusRequest = new HeavyweightFocusRequest(paramComponent1, paramComponent2, paramBoolean1, paramCause);
/*      */           
/*      */ 
/* 2450 */           heavyweightRequests.add(localHeavyweightFocusRequest);
/*      */           
/* 2452 */           if (localComponent1 != null) {
/* 2453 */             localCausedFocusEvent = new CausedFocusEvent(localComponent1, 1005, paramBoolean1, paramComponent2, paramCause);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2459 */             SunToolkit.postEvent(localComponent1.appContext, localCausedFocusEvent);
/*      */           }
/*      */           
/* 2462 */           CausedFocusEvent localCausedFocusEvent = new CausedFocusEvent(paramComponent2, 1004, paramBoolean1, localComponent1, paramCause);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2467 */           SunToolkit.postEvent(paramComponent2.appContext, localCausedFocusEvent);
/*      */           
/* 2469 */           if (focusLog.isLoggable(PlatformLogger.Level.FINEST))
/* 2470 */             focusLog.finest("2. SNFH_HANDLED for {0}", new Object[] { String.valueOf(paramComponent2) });
/* 2471 */           return 1; } }
/* 2472 */       if ((localHeavyweightFocusRequest != null) && (localHeavyweightFocusRequest.heavyweight == paramComponent1))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2478 */         if (localHeavyweightFocusRequest.addLightweightRequest(paramComponent2, paramBoolean1, paramCause))
/*      */         {
/* 2480 */           localKeyboardFocusManager1.enqueueKeyEvents(paramLong, paramComponent2);
/*      */         }
/*      */         
/* 2483 */         if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2484 */           focusLog.finest("3. SNFH_HANDLED for lightweight" + paramComponent2 + " in " + paramComponent1);
/*      */         }
/*      */         
/* 2487 */         return 1;
/*      */       }
/* 2489 */       if (!paramBoolean2)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2495 */         if (localHeavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER)
/*      */         {
/*      */ 
/* 2498 */           int i = heavyweightRequests.size();
/*      */           
/* 2500 */           localHeavyweightFocusRequest = i >= 2 ? (HeavyweightFocusRequest)heavyweightRequests.get(i - 2) : null;
/*      */         }
/*      */         
/* 2503 */         if (focusedWindowChanged(paramComponent1, localHeavyweightFocusRequest != null ? localHeavyweightFocusRequest.heavyweight : localWindow))
/*      */         {
/*      */ 
/*      */ 
/* 2507 */           if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2508 */             focusLog.finest("4. SNFH_FAILURE for " + paramComponent2);
/*      */           }
/* 2510 */           return 0;
/*      */         }
/*      */       }
/*      */       
/* 2514 */       localKeyboardFocusManager1.enqueueKeyEvents(paramLong, paramComponent2);
/* 2515 */       heavyweightRequests
/* 2516 */         .add(new HeavyweightFocusRequest(paramComponent1, paramComponent2, paramBoolean1, paramCause));
/*      */       
/* 2518 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2519 */         focusLog.finest("5. SNFH_PROCEED for " + paramComponent2);
/*      */       }
/* 2521 */       return 2;
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
/*      */   static Window markClearGlobalFocusOwner()
/*      */   {
/* 2537 */     Window localWindow = getCurrentKeyboardFocusManager().getNativeFocusedWindow();
/*      */     
/* 2539 */     synchronized (heavyweightRequests) {
/* 2540 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getLastHWRequest();
/* 2541 */       if (localHeavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER)
/*      */       {
/*      */ 
/*      */ 
/* 2545 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 2549 */       heavyweightRequests.add(HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER);
/*      */       
/*      */ 
/* 2552 */       Object localObject1 = localHeavyweightFocusRequest != null ? SunToolkit.getContainingWindow(localHeavyweightFocusRequest.heavyweight) : localWindow;
/*      */       
/* 2554 */       while ((localObject1 != null) && (!(localObject1 instanceof Frame)) && (!(localObject1 instanceof Dialog)))
/*      */       {
/*      */ 
/*      */ 
/* 2558 */         localObject1 = ((Component)localObject1).getParent_NoClientCode();
/*      */       }
/*      */       
/* 2561 */       return (Window)localObject1;
/*      */     }
/*      */   }
/*      */   
/* 2565 */   Component getCurrentWaitingRequest(Component paramComponent) { synchronized (heavyweightRequests) {
/* 2566 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getFirstHWRequest();
/* 2567 */       if ((localHeavyweightFocusRequest != null) && 
/* 2568 */         (localHeavyweightFocusRequest.heavyweight == paramComponent))
/*      */       {
/* 2570 */         LightweightFocusRequest localLightweightFocusRequest = (LightweightFocusRequest)localHeavyweightFocusRequest.lightweightRequests.getFirst();
/* 2571 */         if (localLightweightFocusRequest != null) {
/* 2572 */           return localLightweightFocusRequest.component;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2577 */     return null;
/*      */   }
/*      */   
/*      */   static boolean isAutoFocusTransferEnabled() {
/* 2581 */     synchronized (heavyweightRequests) {
/* 2582 */       return (heavyweightRequests.size() == 0) && (!disableRestoreFocus) && (null == currentLightweightRequests);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static boolean isAutoFocusTransferEnabledFor(Component paramComponent)
/*      */   {
/* 2589 */     return (isAutoFocusTransferEnabled()) && (paramComponent.isAutoFocusTransferOnDisposal());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Throwable dispatchAndCatchException(Throwable paramThrowable, Component paramComponent, FocusEvent paramFocusEvent)
/*      */   {
/* 2599 */     Object localObject = null;
/*      */     try {
/* 2601 */       paramComponent.dispatchEvent(paramFocusEvent);
/*      */     } catch (RuntimeException localRuntimeException) {
/* 2603 */       localObject = localRuntimeException;
/*      */     } catch (Error localError) {
/* 2605 */       localObject = localError;
/*      */     }
/* 2607 */     if (localObject != null) {
/* 2608 */       if (paramThrowable != null) {
/* 2609 */         handleException(paramThrowable);
/*      */       }
/* 2611 */       return (Throwable)localObject;
/*      */     }
/* 2613 */     return paramThrowable;
/*      */   }
/*      */   
/*      */   private static void handleException(Throwable paramThrowable) {
/* 2617 */     paramThrowable.printStackTrace();
/*      */   }
/*      */   
/*      */   static void processCurrentLightweightRequests() {
/* 2621 */     KeyboardFocusManager localKeyboardFocusManager = getCurrentKeyboardFocusManager();
/* 2622 */     LinkedList localLinkedList = null;
/*      */     
/* 2624 */     Component localComponent1 = localKeyboardFocusManager.getGlobalFocusOwner();
/* 2625 */     if ((localComponent1 != null) && 
/* 2626 */       (localComponent1.appContext != AppContext.getAppContext()))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2631 */       return;
/*      */     }
/*      */     
/* 2634 */     synchronized (heavyweightRequests) {
/* 2635 */       if (currentLightweightRequests != null) {
/* 2636 */         clearingCurrentLightweightRequests = true;
/* 2637 */         disableRestoreFocus = true;
/* 2638 */         localLinkedList = currentLightweightRequests;
/* 2639 */         allowSyncFocusRequests = localLinkedList.size() < 2;
/* 2640 */         currentLightweightRequests = null;
/*      */       }
/*      */       else {
/* 2643 */         return;
/*      */       }
/*      */     }
/*      */     
/* 2647 */     ??? = null;
/*      */     try {
/* 2649 */       if (localLinkedList != null) {
/* 2650 */         localComponent2 = null;
/* 2651 */         localComponent3 = null;
/*      */         
/* 2653 */         for (localIterator = localLinkedList.iterator(); localIterator.hasNext();)
/*      */         {
/* 2655 */           localComponent3 = localKeyboardFocusManager.getGlobalFocusOwner();
/*      */           
/* 2657 */           LightweightFocusRequest localLightweightFocusRequest = (LightweightFocusRequest)localIterator.next();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2668 */           if (!localIterator.hasNext()) {
/* 2669 */             disableRestoreFocus = false;
/*      */           }
/*      */           
/* 2672 */           CausedFocusEvent localCausedFocusEvent1 = null;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2678 */           if (localComponent3 != null) {
/* 2679 */             localCausedFocusEvent1 = new CausedFocusEvent(localComponent3, 1005, localLightweightFocusRequest.temporary, localLightweightFocusRequest.component, localLightweightFocusRequest.cause);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2684 */           CausedFocusEvent localCausedFocusEvent2 = new CausedFocusEvent(localLightweightFocusRequest.component, 1004, localLightweightFocusRequest.temporary, localComponent3 == null ? localComponent2 : localComponent3, localLightweightFocusRequest.cause);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2691 */           if (localComponent3 != null) {
/* 2692 */             localCausedFocusEvent1.isPosted = true;
/* 2693 */             ??? = dispatchAndCatchException((Throwable)???, localComponent3, localCausedFocusEvent1);
/*      */           }
/*      */           
/* 2696 */           localCausedFocusEvent2.isPosted = true;
/* 2697 */           ??? = dispatchAndCatchException((Throwable)???, localLightweightFocusRequest.component, localCausedFocusEvent2);
/*      */           
/* 2699 */           if (localKeyboardFocusManager.getGlobalFocusOwner() == localLightweightFocusRequest.component)
/* 2700 */             localComponent2 = localLightweightFocusRequest.component;
/*      */         }
/*      */       } } finally { Component localComponent2;
/*      */       Component localComponent3;
/*      */       Iterator localIterator;
/* 2705 */       clearingCurrentLightweightRequests = false;
/* 2706 */       disableRestoreFocus = false;
/* 2707 */       localLinkedList = null;
/* 2708 */       allowSyncFocusRequests = true;
/*      */     }
/* 2710 */     if ((??? instanceof RuntimeException))
/* 2711 */       throw ((RuntimeException)???);
/* 2712 */     if ((??? instanceof Error)) {
/* 2713 */       throw ((Error)???);
/*      */     }
/*      */   }
/*      */   
/*      */   static FocusEvent retargetUnexpectedFocusEvent(FocusEvent paramFocusEvent) {
/* 2718 */     synchronized (heavyweightRequests)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2723 */       if (removeFirstRequest()) {
/* 2724 */         return (FocusEvent)retargetFocusEvent(paramFocusEvent);
/*      */       }
/*      */       
/* 2727 */       Component localComponent1 = paramFocusEvent.getComponent();
/* 2728 */       Component localComponent2 = paramFocusEvent.getOppositeComponent();
/* 2729 */       boolean bool = false;
/* 2730 */       if ((paramFocusEvent.getID() == 1005) && ((localComponent2 == null) || 
/* 2731 */         (isTemporary(localComponent2, localComponent1))))
/*      */       {
/* 2733 */         bool = true;
/*      */       }
/* 2735 */       return new CausedFocusEvent(localComponent1, paramFocusEvent.getID(), bool, localComponent2, CausedFocusEvent.Cause.NATIVE_SYSTEM);
/*      */     }
/*      */   }
/*      */   
/*      */   static FocusEvent retargetFocusGained(FocusEvent paramFocusEvent)
/*      */   {
/* 2741 */     assert (paramFocusEvent.getID() == 1004);
/*      */     
/*      */ 
/* 2744 */     Component localComponent1 = getCurrentKeyboardFocusManager().getGlobalFocusOwner();
/* 2745 */     Component localComponent2 = paramFocusEvent.getComponent();
/* 2746 */     Component localComponent3 = paramFocusEvent.getOppositeComponent();
/* 2747 */     Component localComponent4 = getHeavyweight(localComponent2);
/*      */     
/* 2749 */     synchronized (heavyweightRequests) {
/* 2750 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getFirstHWRequest();
/*      */       
/* 2752 */       if (localHeavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER)
/*      */       {
/* 2754 */         return retargetUnexpectedFocusEvent(paramFocusEvent);
/*      */       }
/*      */       
/* 2757 */       if ((localComponent2 != null) && (localComponent4 == null) && (localHeavyweightFocusRequest != null))
/*      */       {
/*      */ 
/*      */ 
/* 2761 */         if (localComponent2 == localHeavyweightFocusRequest.getFirstLightweightRequest().component)
/*      */         {
/* 2763 */           localComponent2 = localHeavyweightFocusRequest.heavyweight;
/* 2764 */           localComponent4 = localComponent2;
/*      */         }
/*      */       }
/* 2767 */       if ((localHeavyweightFocusRequest != null) && (localComponent4 == localHeavyweightFocusRequest.heavyweight))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2773 */         heavyweightRequests.removeFirst();
/*      */         
/*      */ 
/* 2776 */         LightweightFocusRequest localLightweightFocusRequest = (LightweightFocusRequest)localHeavyweightFocusRequest.lightweightRequests.removeFirst();
/*      */         
/* 2778 */         Component localComponent5 = localLightweightFocusRequest.component;
/* 2779 */         if (localComponent1 != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2791 */           newFocusOwner = localComponent5;
/*      */         }
/*      */         
/*      */ 
/* 2795 */         boolean bool = (localComponent3 == null) || (isTemporary(localComponent5, localComponent3)) ? false : localLightweightFocusRequest.temporary;
/*      */         
/*      */ 
/*      */ 
/* 2799 */         if (localHeavyweightFocusRequest.lightweightRequests.size() > 0) {
/* 2800 */           currentLightweightRequests = localHeavyweightFocusRequest.lightweightRequests;
/*      */           
/* 2802 */           EventQueue.invokeLater(new Runnable()
/*      */           {
/*      */             public void run() {}
/*      */           });
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2811 */         return new CausedFocusEvent(localComponent5, 1004, bool, localComponent3, localLightweightFocusRequest.cause);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2816 */       if ((localComponent1 != null) && 
/* 2817 */         (localComponent1.getContainingWindow() == localComponent2) && ((localHeavyweightFocusRequest == null) || (localComponent2 != localHeavyweightFocusRequest.heavyweight)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2824 */         return new CausedFocusEvent(localComponent1, 1004, false, null, CausedFocusEvent.Cause.ACTIVATION);
/*      */       }
/*      */       
/*      */ 
/* 2828 */       return retargetUnexpectedFocusEvent(paramFocusEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   static FocusEvent retargetFocusLost(FocusEvent paramFocusEvent) {
/* 2833 */     assert (paramFocusEvent.getID() == 1005);
/*      */     
/*      */ 
/* 2836 */     Component localComponent1 = getCurrentKeyboardFocusManager().getGlobalFocusOwner();
/* 2837 */     Component localComponent2 = paramFocusEvent.getOppositeComponent();
/* 2838 */     Component localComponent3 = getHeavyweight(localComponent2);
/*      */     
/* 2840 */     synchronized (heavyweightRequests) {
/* 2841 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getFirstHWRequest();
/*      */       
/* 2843 */       if (localHeavyweightFocusRequest == HeavyweightFocusRequest.CLEAR_GLOBAL_FOCUS_OWNER)
/*      */       {
/* 2845 */         if (localComponent1 != null)
/*      */         {
/* 2847 */           heavyweightRequests.removeFirst();
/* 2848 */           return new CausedFocusEvent(localComponent1, 1005, false, null, CausedFocusEvent.Cause.CLEAR_GLOBAL_FOCUS_OWNER);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2855 */         if (localComponent2 == null)
/*      */         {
/*      */ 
/* 2858 */           if (localComponent1 != null) {
/* 2859 */             return new CausedFocusEvent(localComponent1, 1005, true, null, CausedFocusEvent.Cause.ACTIVATION);
/*      */           }
/*      */           
/*      */ 
/* 2863 */           return paramFocusEvent;
/*      */         }
/* 2865 */         if (localHeavyweightFocusRequest != null) if (localComponent3 != localHeavyweightFocusRequest.heavyweight) { if (localComponent3 == null)
/*      */             {
/*      */ 
/* 2868 */               if (localComponent2 != localHeavyweightFocusRequest.getFirstLightweightRequest().component) {} }
/*      */           } else {
/* 2870 */             if (localComponent1 == null) {
/* 2871 */               return paramFocusEvent;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2881 */             LightweightFocusRequest localLightweightFocusRequest = (LightweightFocusRequest)localHeavyweightFocusRequest.lightweightRequests.getFirst();
/*      */             
/* 2883 */             boolean bool = isTemporary(localComponent2, localComponent1) ? true : localLightweightFocusRequest.temporary;
/*      */             
/*      */ 
/*      */ 
/* 2887 */             return new CausedFocusEvent(localComponent1, 1005, bool, localLightweightFocusRequest.component, localLightweightFocusRequest.cause);
/*      */           }
/* 2889 */         if (focusedWindowChanged(localComponent2, localComponent1))
/*      */         {
/*      */ 
/* 2892 */           if ((!paramFocusEvent.isTemporary()) && (localComponent1 != null))
/*      */           {
/* 2894 */             paramFocusEvent = new CausedFocusEvent(localComponent1, 1005, true, localComponent2, CausedFocusEvent.Cause.ACTIVATION);
/*      */           }
/*      */           
/* 2897 */           return paramFocusEvent;
/*      */         }
/*      */       }
/* 2900 */       return retargetUnexpectedFocusEvent(paramFocusEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   static AWTEvent retargetFocusEvent(AWTEvent paramAWTEvent) {
/* 2905 */     if (clearingCurrentLightweightRequests) {
/* 2906 */       return paramAWTEvent;
/*      */     }
/*      */     
/* 2909 */     KeyboardFocusManager localKeyboardFocusManager = getCurrentKeyboardFocusManager();
/* 2910 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 2911 */       if (((paramAWTEvent instanceof FocusEvent)) || ((paramAWTEvent instanceof WindowEvent))) {
/* 2912 */         focusLog.finer(">>> {0}", new Object[] { String.valueOf(paramAWTEvent) });
/*      */       }
/* 2914 */       if ((focusLog.isLoggable(PlatformLogger.Level.FINER)) && ((paramAWTEvent instanceof KeyEvent))) {
/* 2915 */         focusLog.finer("    focus owner is {0}", new Object[] {
/* 2916 */           String.valueOf(localKeyboardFocusManager.getGlobalFocusOwner()) });
/* 2917 */         focusLog.finer(">>> {0}", new Object[] { String.valueOf(paramAWTEvent) });
/*      */       }
/*      */     }
/*      */     
/* 2921 */     synchronized (heavyweightRequests)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2932 */       if ((newFocusOwner != null) && 
/* 2933 */         (paramAWTEvent.getID() == 1005))
/*      */       {
/* 2935 */         FocusEvent localFocusEvent = (FocusEvent)paramAWTEvent;
/*      */         
/* 2937 */         if ((localKeyboardFocusManager.getGlobalFocusOwner() == localFocusEvent.getComponent()) && 
/* 2938 */           (localFocusEvent.getOppositeComponent() == newFocusOwner))
/*      */         {
/* 2940 */           newFocusOwner = null;
/* 2941 */           return paramAWTEvent;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2946 */     processCurrentLightweightRequests();
/*      */     
/* 2948 */     switch (paramAWTEvent.getID()) {
/*      */     case 1004: 
/* 2950 */       paramAWTEvent = retargetFocusGained((FocusEvent)paramAWTEvent);
/* 2951 */       break;
/*      */     
/*      */     case 1005: 
/* 2954 */       paramAWTEvent = retargetFocusLost((FocusEvent)paramAWTEvent);
/* 2955 */       break;
/*      */     }
/*      */     
/*      */     
/*      */ 
/* 2960 */     return paramAWTEvent;
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
/*      */   static boolean removeFirstRequest()
/*      */   {
/* 2974 */     KeyboardFocusManager localKeyboardFocusManager = getCurrentKeyboardFocusManager();
/*      */     
/* 2976 */     synchronized (heavyweightRequests) {
/* 2977 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getFirstHWRequest();
/*      */       
/* 2979 */       if (localHeavyweightFocusRequest != null) {
/* 2980 */         heavyweightRequests.removeFirst();
/* 2981 */         if (localHeavyweightFocusRequest.lightweightRequests != null)
/*      */         {
/* 2983 */           Iterator localIterator = localHeavyweightFocusRequest.lightweightRequests.iterator();
/* 2984 */           while (localIterator.hasNext())
/*      */           {
/*      */ 
/* 2987 */             localKeyboardFocusManager.dequeueKeyEvents(-1L, ((LightweightFocusRequest)localIterator.next()).component);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2994 */       if (heavyweightRequests.size() == 0) {
/* 2995 */         localKeyboardFocusManager.clearMarkers();
/*      */       }
/* 2997 */       return heavyweightRequests.size() > 0;
/*      */     }
/*      */   }
/*      */   
/* 3001 */   static void removeLastFocusRequest(Component paramComponent) { if ((log.isLoggable(PlatformLogger.Level.FINE)) && 
/* 3002 */       (paramComponent == null)) {
/* 3003 */       log.fine("Assertion (heavyweight != null) failed");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3008 */     KeyboardFocusManager localKeyboardFocusManager = getCurrentKeyboardFocusManager();
/* 3009 */     synchronized (heavyweightRequests) {
/* 3010 */       HeavyweightFocusRequest localHeavyweightFocusRequest = getLastHWRequest();
/* 3011 */       if ((localHeavyweightFocusRequest != null) && (localHeavyweightFocusRequest.heavyweight == paramComponent))
/*      */       {
/* 3013 */         heavyweightRequests.removeLast();
/*      */       }
/*      */       
/*      */ 
/* 3017 */       if (heavyweightRequests.size() == 0) {
/* 3018 */         localKeyboardFocusManager.clearMarkers();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean focusedWindowChanged(Component paramComponent1, Component paramComponent2) {
/* 3024 */     Window localWindow1 = SunToolkit.getContainingWindow(paramComponent1);
/* 3025 */     Window localWindow2 = SunToolkit.getContainingWindow(paramComponent2);
/* 3026 */     if ((localWindow1 == null) && (localWindow2 == null)) {
/* 3027 */       return true;
/*      */     }
/* 3029 */     if (localWindow1 == null) {
/* 3030 */       return true;
/*      */     }
/* 3032 */     if (localWindow2 == null) {
/* 3033 */       return true;
/*      */     }
/* 3035 */     return localWindow1 != localWindow2;
/*      */   }
/*      */   
/*      */   private static boolean isTemporary(Component paramComponent1, Component paramComponent2) {
/* 3039 */     Window localWindow1 = SunToolkit.getContainingWindow(paramComponent1);
/* 3040 */     Window localWindow2 = SunToolkit.getContainingWindow(paramComponent2);
/* 3041 */     if ((localWindow1 == null) && (localWindow2 == null)) {
/* 3042 */       return false;
/*      */     }
/* 3044 */     if (localWindow1 == null) {
/* 3045 */       return true;
/*      */     }
/* 3047 */     if (localWindow2 == null) {
/* 3048 */       return false;
/*      */     }
/* 3050 */     return localWindow1 != localWindow2;
/*      */   }
/*      */   
/*      */   static Component getHeavyweight(Component paramComponent) {
/* 3054 */     if ((paramComponent == null) || (paramComponent.getPeer() == null))
/* 3055 */       return null;
/* 3056 */     if ((paramComponent.getPeer() instanceof LightweightPeer)) {
/* 3057 */       return paramComponent.getNativeContainer();
/*      */     }
/* 3059 */     return paramComponent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static boolean isProxyActiveImpl(KeyEvent paramKeyEvent)
/*      */   {
/* 3066 */     if (proxyActive == null) {
/* 3067 */       proxyActive = (Field)AccessController.doPrivileged(new PrivilegedAction() {
/*      */         public Field run() {
/* 3069 */           Field localField = null;
/*      */           try {
/* 3071 */             localField = KeyEvent.class.getDeclaredField("isProxyActive");
/* 3072 */             if (localField != null) {
/* 3073 */               localField.setAccessible(true);
/*      */             }
/*      */           } catch (NoSuchFieldException localNoSuchFieldException) {
/* 3076 */             if (!$assertionsDisabled) throw new AssertionError();
/*      */           }
/* 3078 */           return localField;
/*      */         }
/*      */       });
/*      */     }
/*      */     try
/*      */     {
/* 3084 */       return proxyActive.getBoolean(paramKeyEvent);
/*      */     } catch (IllegalAccessException localIllegalAccessException) {
/* 3086 */       if (!$assertionsDisabled) throw new AssertionError();
/*      */     }
/* 3088 */     return false;
/*      */   }
/*      */   
/*      */   static boolean isProxyActive(KeyEvent paramKeyEvent)
/*      */   {
/* 3093 */     if (!GraphicsEnvironment.isHeadless()) {
/* 3094 */       return isProxyActiveImpl(paramKeyEvent);
/*      */     }
/* 3096 */     return false;
/*      */   }
/*      */   
/*      */   private static HeavyweightFocusRequest getLastHWRequest()
/*      */   {
/* 3101 */     synchronized (heavyweightRequests)
/*      */     {
/* 3103 */       return heavyweightRequests.size() > 0 ? (HeavyweightFocusRequest)heavyweightRequests.getLast() : null;
/*      */     }
/*      */   }
/*      */   
/*      */   private static HeavyweightFocusRequest getFirstHWRequest()
/*      */   {
/* 3109 */     synchronized (heavyweightRequests)
/*      */     {
/* 3111 */       return heavyweightRequests.size() > 0 ? (HeavyweightFocusRequest)heavyweightRequests.getFirst() : null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static void checkReplaceKFMPermission()
/*      */     throws SecurityException
/*      */   {
/* 3119 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 3120 */     if (localSecurityManager != null) {
/* 3121 */       if (replaceKeyboardFocusManagerPermission == null) {
/* 3122 */         replaceKeyboardFocusManagerPermission = new AWTPermission("replaceKeyboardFocusManager");
/*      */       }
/*      */       
/*      */ 
/* 3126 */       localSecurityManager.checkPermission(replaceKeyboardFocusManagerPermission);
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
/*      */   private void checkKFMSecurity()
/*      */     throws SecurityException
/*      */   {
/* 3146 */     if (this != getCurrentKeyboardFocusManager()) {
/* 3147 */       checkReplaceKFMPermission();
/*      */     }
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   /* Error */
/*      */   final SequencedEvent getCurrentSequencedEvent()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 16
/*      */     //   2: dup
/*      */     //   3: astore_1
/*      */     //   4: monitorenter
/*      */     //   5: aload_0
/*      */     //   6: getfield 18	java/awt/KeyboardFocusManager:currentSequencedEvent	Ljava/awt/SequencedEvent;
/*      */     //   9: aload_1
/*      */     //   10: monitorexit
/*      */     //   11: areturn
/*      */     //   12: astore_2
/*      */     //   13: aload_1
/*      */     //   14: monitorexit
/*      */     //   15: aload_2
/*      */     //   16: athrow
/*      */     // Line number table:
/*      */     //   Java source line #414	-> byte code offset #0
/*      */     //   Java source line #415	-> byte code offset #5
/*      */     //   Java source line #416	-> byte code offset #12
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	17	0	this	KeyboardFocusManager
/*      */     //   3	11	1	Ljava/lang/Object;	Object
/*      */     //   12	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   5	11	12	finally
/*      */     //   12	15	12	finally
/*      */   }
/*      */   
/*      */   public abstract boolean dispatchEvent(AWTEvent paramAWTEvent);
/*      */   
/*      */   public abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);
/*      */   
/*      */   public abstract boolean postProcessKeyEvent(KeyEvent paramKeyEvent);
/*      */   
/*      */   public abstract void processKeyEvent(Component paramComponent, KeyEvent paramKeyEvent);
/*      */   
/*      */   protected abstract void enqueueKeyEvents(long paramLong, Component paramComponent);
/*      */   
/*      */   protected abstract void dequeueKeyEvents(long paramLong, Component paramComponent);
/*      */   
/*      */   protected abstract void discardKeyEvents(Component paramComponent);
/*      */   
/*      */   public abstract void focusNextComponent(Component paramComponent);
/*      */   
/*      */   public abstract void focusPreviousComponent(Component paramComponent);
/*      */   
/*      */   public abstract void upFocusCycle(Component paramComponent);
/*      */   
/*      */   public abstract void downFocusCycle(Container paramContainer);
/*      */   
/*      */   void clearMarkers() {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/KeyboardFocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */