/*       */ package java.awt;
/*       */ 
/*       */ import java.applet.Applet;
/*       */ import java.awt.dnd.DropTarget;
/*       */ import java.awt.event.ComponentEvent;
/*       */ import java.awt.event.ComponentListener;
/*       */ import java.awt.event.FocusEvent;
/*       */ import java.awt.event.FocusListener;
/*       */ import java.awt.event.HierarchyBoundsListener;
/*       */ import java.awt.event.HierarchyEvent;
/*       */ import java.awt.event.HierarchyListener;
/*       */ import java.awt.event.InputEvent;
/*       */ import java.awt.event.InputMethodEvent;
/*       */ import java.awt.event.InputMethodListener;
/*       */ import java.awt.event.KeyEvent;
/*       */ import java.awt.event.KeyListener;
/*       */ import java.awt.event.MouseEvent;
/*       */ import java.awt.event.MouseListener;
/*       */ import java.awt.event.MouseMotionListener;
/*       */ import java.awt.event.MouseWheelEvent;
/*       */ import java.awt.event.MouseWheelListener;
/*       */ import java.awt.event.PaintEvent;
/*       */ import java.awt.event.WindowEvent;
/*       */ import java.awt.im.InputMethodRequests;
/*       */ import java.awt.image.BufferStrategy;
/*       */ import java.awt.image.ColorModel;
/*       */ import java.awt.image.ImageObserver;
/*       */ import java.awt.image.ImageProducer;
/*       */ import java.awt.image.VolatileImage;
/*       */ import java.awt.peer.ComponentPeer;
/*       */ import java.awt.peer.ContainerPeer;
/*       */ import java.awt.peer.LightweightPeer;
/*       */ import java.awt.peer.MouseInfoPeer;
/*       */ import java.beans.PropertyChangeListener;
/*       */ import java.beans.PropertyChangeSupport;
/*       */ import java.beans.Transient;
/*       */ import java.io.IOException;
/*       */ import java.io.ObjectInputStream;
/*       */ import java.io.ObjectOutputStream;
/*       */ import java.io.OptionalDataException;
/*       */ import java.io.PrintStream;
/*       */ import java.io.PrintWriter;
/*       */ import java.io.Serializable;
/*       */ import java.lang.reflect.InvocationTargetException;
/*       */ import java.lang.reflect.Method;
/*       */ import java.security.AccessControlContext;
/*       */ import java.security.AccessController;
/*       */ import java.security.PrivilegedAction;
/*       */ import java.util.Collections;
/*       */ import java.util.EventListener;
/*       */ import java.util.HashSet;
/*       */ import java.util.Locale;
/*       */ import java.util.Map;
/*       */ import java.util.Objects;
/*       */ import java.util.Set;
/*       */ import java.util.Vector;
/*       */ import java.util.WeakHashMap;
/*       */ import javax.accessibility.Accessible;
/*       */ import javax.accessibility.AccessibleComponent;
/*       */ import javax.accessibility.AccessibleContext;
/*       */ import javax.accessibility.AccessibleRole;
/*       */ import javax.accessibility.AccessibleSelection;
/*       */ import javax.accessibility.AccessibleState;
/*       */ import javax.accessibility.AccessibleStateSet;
/*       */ import javax.swing.JComponent;
/*       */ import sun.awt.AWTAccessor;
/*       */ import sun.awt.AWTAccessor.ComponentAccessor;
/*       */ import sun.awt.AppContext;
/*       */ import sun.awt.CausedFocusEvent.Cause;
/*       */ import sun.awt.ConstrainableGraphics;
/*       */ import sun.awt.EmbeddedFrame;
/*       */ import sun.awt.EventQueueItem;
/*       */ import sun.awt.RequestFocusController;
/*       */ import sun.awt.SubRegionShowable;
/*       */ import sun.awt.SunToolkit;
/*       */ import sun.awt.WindowClosingListener;
/*       */ import sun.awt.dnd.SunDropTargetEvent;
/*       */ import sun.awt.im.CompositionArea;
/*       */ import sun.awt.image.VSyncedBSManager;
/*       */ import sun.font.FontDesignMetrics;
/*       */ import sun.font.FontManager;
/*       */ import sun.font.FontManagerFactory;
/*       */ import sun.font.SunFontManager;
/*       */ import sun.java2d.SunGraphics2D;
/*       */ import sun.java2d.SunGraphicsEnvironment;
/*       */ import sun.java2d.pipe.Region;
/*       */ import sun.java2d.pipe.hw.ExtendedBufferCapabilities;
/*       */ import sun.java2d.pipe.hw.ExtendedBufferCapabilities.VSyncType;
/*       */ import sun.security.action.GetPropertyAction;
/*       */ import sun.util.logging.PlatformLogger;
/*       */ import sun.util.logging.PlatformLogger.Level;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public abstract class Component
/*       */   implements ImageObserver, MenuContainer, Serializable
/*       */ {
/*       */   private static final PlatformLogger log;
/*       */   private static final PlatformLogger eventLog;
/*       */   private static final PlatformLogger focusLog;
/*       */   private static final PlatformLogger mixingLog;
/*       */   transient ComponentPeer peer;
/*       */   transient Container parent;
/*       */   transient AppContext appContext;
/*       */   int x;
/*       */   int y;
/*       */   int width;
/*       */   int height;
/*       */   Color foreground;
/*       */   Color background;
/*       */   volatile Font font;
/*       */   Font peerFont;
/*       */   Cursor cursor;
/*       */   Locale locale;
/*   315 */   private transient GraphicsConfiguration graphicsConfig = null;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   325 */   transient BufferStrategy bufferStrategy = null;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   335 */   boolean ignoreRepaint = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   345 */   boolean visible = true;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   355 */   boolean enabled = true;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   367 */   private volatile boolean valid = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   DropTarget dropTarget;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Vector<PopupMenu> popups;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private String name;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   405 */   private boolean nameExplicitlySet = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   415 */   private boolean focusable = true;
/*       */   
/*       */ 
/*       */   private static final int FOCUS_TRAVERSABLE_UNKNOWN = 0;
/*       */   
/*       */ 
/*       */   private static final int FOCUS_TRAVERSABLE_DEFAULT = 1;
/*       */   
/*       */ 
/*       */   private static final int FOCUS_TRAVERSABLE_SET = 2;
/*       */   
/*       */ 
/*   427 */   private int isFocusTraversableOverridden = 0;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Set<AWTKeyStroke>[] focusTraversalKeys;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static final String[] focusTraversalKeyPropertyNames;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   463 */   private boolean focusTraversalKeysEnabled = true;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static final Object LOCK;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   477 */   private volatile transient AccessControlContext acc = AccessController.getContext();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Dimension minSize;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   boolean minSizeSet;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Dimension prefSize;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   boolean prefSizeSet;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Dimension maxSize;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   boolean maxSizeSet;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   522 */   transient ComponentOrientation componentOrientation = ComponentOrientation.UNKNOWN;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   536 */   boolean newEventsOnly = false;
/*       */   
/*       */   transient ComponentListener componentListener;
/*       */   transient FocusListener focusListener;
/*       */   transient HierarchyListener hierarchyListener;
/*       */   transient HierarchyBoundsListener hierarchyBoundsListener;
/*       */   transient KeyListener keyListener;
/*       */   transient MouseListener mouseListener;
/*       */   transient MouseMotionListener mouseMotionListener;
/*       */   transient MouseWheelListener mouseWheelListener;
/*       */   transient InputMethodListener inputMethodListener;
/*   547 */   transient RuntimeException windowClosingException = null;
/*       */   
/*       */   static final String actionListenerK = "actionL";
/*       */   
/*       */   static final String adjustmentListenerK = "adjustmentL";
/*       */   
/*       */   static final String componentListenerK = "componentL";
/*       */   
/*       */   static final String containerListenerK = "containerL";
/*       */   
/*       */   static final String focusListenerK = "focusL";
/*       */   
/*       */   static final String itemListenerK = "itemL";
/*       */   
/*       */   static final String keyListenerK = "keyL";
/*       */   
/*       */   static final String mouseListenerK = "mouseL";
/*       */   
/*       */   static final String mouseMotionListenerK = "mouseMotionL";
/*       */   
/*       */   static final String mouseWheelListenerK = "mouseWheelL";
/*       */   
/*       */   static final String textListenerK = "textL";
/*       */   
/*       */   static final String ownedWindowK = "ownedL";
/*       */   
/*       */   static final String windowListenerK = "windowL";
/*       */   
/*       */   static final String inputMethodListenerK = "inputMethodL";
/*       */   
/*       */   static final String hierarchyListenerK = "hierarchyL";
/*       */   
/*       */   static final String hierarchyBoundsListenerK = "hierarchyBoundsL";
/*       */   
/*       */   static final String windowStateListenerK = "windowStateL";
/*       */   static final String windowFocusListenerK = "windowFocusL";
/*   583 */   long eventMask = 4096L;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static boolean isInc;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static int incRate;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float TOP_ALIGNMENT = 0.0F;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float CENTER_ALIGNMENT = 0.5F;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float BOTTOM_ALIGNMENT = 1.0F;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float LEFT_ALIGNMENT = 0.0F;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float RIGHT_ALIGNMENT = 1.0F;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static final long serialVersionUID = -7644114512714619750L;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private PropertyChangeSupport changeSupport;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   675 */   private transient Object objectLock = new Object();
/*       */   
/*   677 */   Object getObjectLock() { return this.objectLock; }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final AccessControlContext getAccessControlContext()
/*       */   {
/*   684 */     if (this.acc == null) {
/*   685 */       throw new SecurityException("Component is missing AccessControlContext");
/*       */     }
/*   687 */     return this.acc;
/*       */   }
/*       */   
/*   690 */   boolean isPacked = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   698 */   private int boundsOp = 3;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static class AWTTreeLock {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static enum BaselineResizeBehavior
/*       */   {
/*   727 */     CONSTANT_ASCENT, 
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   738 */     CONSTANT_DESCENT, 
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   777 */     CENTER_OFFSET, 
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   785 */     OTHER;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     private BaselineResizeBehavior() {}
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*   795 */   private transient Region compoundShape = null;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   804 */   private transient Region mixingCutoutRegion = null;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   810 */   private transient boolean isAddNotifyComplete = false;
/*       */   
/*       */   transient boolean backgroundEraseDisabled;
/*       */   transient EventQueueItem[] eventCache;
/*       */   
/*       */   int getBoundsOp()
/*       */   {
/*   817 */     assert (Thread.holdsLock(getTreeLock()));
/*   818 */     return this.boundsOp;
/*       */   }
/*       */   
/*       */   void setBoundsOp(int paramInt) {
/*   822 */     assert (Thread.holdsLock(getTreeLock()));
/*   823 */     if (paramInt == 5) {
/*   824 */       this.boundsOp = 3;
/*       */     }
/*   826 */     else if (this.boundsOp == 3) {
/*   827 */       this.boundsOp = paramInt;
/*       */     }
/*       */   }
/*       */   
/*       */   static
/*       */   {
/*   190 */     log = PlatformLogger.getLogger("java.awt.Component");
/*   191 */     eventLog = PlatformLogger.getLogger("java.awt.event.Component");
/*   192 */     focusLog = PlatformLogger.getLogger("java.awt.focus.Component");
/*   193 */     mixingLog = PlatformLogger.getLogger("java.awt.mixing.Component");
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   444 */     focusTraversalKeyPropertyNames = new String[] { "forwardFocusTraversalKeys", "backwardFocusTraversalKeys", "upCycleFocusTraversalKeys", "downCycleFocusTraversalKeys" };
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   470 */     LOCK = new AWTTreeLock();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   593 */     Toolkit.loadLibraries();
/*       */     
/*   595 */     if (!GraphicsEnvironment.isHeadless()) {
/*   596 */       initIDs();
/*       */     }
/*       */     
/*   599 */     String str = (String)AccessController.doPrivileged(new GetPropertyAction("awt.image.incrementaldraw"));
/*       */     
/*   601 */     isInc = (str == null) || (str.equals("true"));
/*       */     
/*   603 */     str = (String)AccessController.doPrivileged(new GetPropertyAction("awt.image.redrawrate"));
/*       */     
/*   605 */     incRate = str != null ? Integer.parseInt(str) : 100;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   839 */     AWTAccessor.setComponentAccessor(new AWTAccessor.ComponentAccessor() {
/*       */       public void setBackgroundEraseDisabled(Component paramAnonymousComponent, boolean paramAnonymousBoolean) {
/*   841 */         paramAnonymousComponent.backgroundEraseDisabled = paramAnonymousBoolean;
/*       */       }
/*       */       
/*   844 */       public boolean getBackgroundEraseDisabled(Component paramAnonymousComponent) { return paramAnonymousComponent.backgroundEraseDisabled; }
/*       */       
/*       */       public Rectangle getBounds(Component paramAnonymousComponent) {
/*   847 */         return new Rectangle(paramAnonymousComponent.x, paramAnonymousComponent.y, paramAnonymousComponent.width, paramAnonymousComponent.height);
/*       */       }
/*       */       
/*       */       public void setMixingCutoutShape(Component paramAnonymousComponent, Shape paramAnonymousShape) {
/*   851 */         Region localRegion = paramAnonymousShape == null ? null : Region.getInstance(paramAnonymousShape, null);
/*       */         
/*   853 */         synchronized (paramAnonymousComponent.getTreeLock()) {
/*   854 */           int i = 0;
/*   855 */           int j = 0;
/*       */           
/*   857 */           if (!paramAnonymousComponent.isNonOpaqueForMixing()) {
/*   858 */             j = 1;
/*       */           }
/*       */           
/*   861 */           paramAnonymousComponent.mixingCutoutRegion = localRegion;
/*       */           
/*   863 */           if (!paramAnonymousComponent.isNonOpaqueForMixing()) {
/*   864 */             i = 1;
/*       */           }
/*       */           
/*   867 */           if (paramAnonymousComponent.isMixingNeeded()) {
/*   868 */             if (j != 0) {
/*   869 */               paramAnonymousComponent.mixOnHiding(paramAnonymousComponent.isLightweight());
/*       */             }
/*   871 */             if (i != 0) {
/*   872 */               paramAnonymousComponent.mixOnShowing();
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */       public void setGraphicsConfiguration(Component paramAnonymousComponent, GraphicsConfiguration paramAnonymousGraphicsConfiguration)
/*       */       {
/*   881 */         paramAnonymousComponent.setGraphicsConfiguration(paramAnonymousGraphicsConfiguration);
/*       */       }
/*       */       
/*   884 */       public boolean requestFocus(Component paramAnonymousComponent, CausedFocusEvent.Cause paramAnonymousCause) { return paramAnonymousComponent.requestFocus(paramAnonymousCause); }
/*       */       
/*       */       public boolean canBeFocusOwner(Component paramAnonymousComponent) {
/*   887 */         return paramAnonymousComponent.canBeFocusOwner();
/*       */       }
/*       */       
/*       */       public boolean isVisible(Component paramAnonymousComponent) {
/*   891 */         return paramAnonymousComponent.isVisible_NoClientCode();
/*       */       }
/*       */       
/*       */       public void setRequestFocusController(RequestFocusController paramAnonymousRequestFocusController)
/*       */       {
/*   896 */         Component.setRequestFocusController(paramAnonymousRequestFocusController);
/*       */       }
/*       */       
/*   899 */       public AppContext getAppContext(Component paramAnonymousComponent) { return paramAnonymousComponent.appContext; }
/*       */       
/*       */       public void setAppContext(Component paramAnonymousComponent, AppContext paramAnonymousAppContext) {
/*   902 */         paramAnonymousComponent.appContext = paramAnonymousAppContext;
/*       */       }
/*       */       
/*   905 */       public Container getParent(Component paramAnonymousComponent) { return paramAnonymousComponent.getParent_NoClientCode(); }
/*       */       
/*       */ 
/*   908 */       public void setParent(Component paramAnonymousComponent, Container paramAnonymousContainer) { paramAnonymousComponent.parent = paramAnonymousContainer; }
/*       */       
/*       */       public void setSize(Component paramAnonymousComponent, int paramAnonymousInt1, int paramAnonymousInt2) {
/*   911 */         paramAnonymousComponent.width = paramAnonymousInt1;
/*   912 */         paramAnonymousComponent.height = paramAnonymousInt2;
/*       */       }
/*       */       
/*   915 */       public Point getLocation(Component paramAnonymousComponent) { return paramAnonymousComponent.location_NoClientCode(); }
/*       */       
/*       */       public void setLocation(Component paramAnonymousComponent, int paramAnonymousInt1, int paramAnonymousInt2) {
/*   918 */         paramAnonymousComponent.x = paramAnonymousInt1;
/*   919 */         paramAnonymousComponent.y = paramAnonymousInt2;
/*       */       }
/*       */       
/*   922 */       public boolean isEnabled(Component paramAnonymousComponent) { return paramAnonymousComponent.isEnabledImpl(); }
/*       */       
/*       */       public boolean isDisplayable(Component paramAnonymousComponent) {
/*   925 */         return paramAnonymousComponent.peer != null;
/*       */       }
/*       */       
/*   928 */       public Cursor getCursor(Component paramAnonymousComponent) { return paramAnonymousComponent.getCursor_NoClientCode(); }
/*       */       
/*       */       public ComponentPeer getPeer(Component paramAnonymousComponent) {
/*   931 */         return paramAnonymousComponent.peer;
/*       */       }
/*       */       
/*   934 */       public void setPeer(Component paramAnonymousComponent, ComponentPeer paramAnonymousComponentPeer) { paramAnonymousComponent.peer = paramAnonymousComponentPeer; }
/*       */       
/*       */       public boolean isLightweight(Component paramAnonymousComponent) {
/*   937 */         return paramAnonymousComponent.peer instanceof LightweightPeer;
/*       */       }
/*       */       
/*   940 */       public boolean getIgnoreRepaint(Component paramAnonymousComponent) { return paramAnonymousComponent.ignoreRepaint; }
/*       */       
/*       */       public int getWidth(Component paramAnonymousComponent) {
/*   943 */         return paramAnonymousComponent.width;
/*       */       }
/*       */       
/*   946 */       public int getHeight(Component paramAnonymousComponent) { return paramAnonymousComponent.height; }
/*       */       
/*       */       public int getX(Component paramAnonymousComponent) {
/*   949 */         return paramAnonymousComponent.x;
/*       */       }
/*       */       
/*   952 */       public int getY(Component paramAnonymousComponent) { return paramAnonymousComponent.y; }
/*       */       
/*       */       public Color getForeground(Component paramAnonymousComponent) {
/*   955 */         return paramAnonymousComponent.foreground;
/*       */       }
/*       */       
/*   958 */       public Color getBackground(Component paramAnonymousComponent) { return paramAnonymousComponent.background; }
/*       */       
/*       */       public void setBackground(Component paramAnonymousComponent, Color paramAnonymousColor) {
/*   961 */         paramAnonymousComponent.background = paramAnonymousColor;
/*       */       }
/*       */       
/*   964 */       public Font getFont(Component paramAnonymousComponent) { return paramAnonymousComponent.getFont_NoClientCode(); }
/*       */       
/*       */       public void processEvent(Component paramAnonymousComponent, AWTEvent paramAnonymousAWTEvent) {
/*   967 */         paramAnonymousComponent.processEvent(paramAnonymousAWTEvent);
/*       */       }
/*       */       
/*       */       public AccessControlContext getAccessControlContext(Component paramAnonymousComponent) {
/*   971 */         return paramAnonymousComponent.getAccessControlContext();
/*       */       }
/*       */       
/*       */       public void revalidateSynchronously(Component paramAnonymousComponent) {
/*   975 */         paramAnonymousComponent.revalidateSynchronously();
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected Component()
/*       */   {
/*   988 */     this.appContext = AppContext.getAppContext();
/*       */   }
/*       */   
/*       */   void initializeFocusTraversalKeys()
/*       */   {
/*   993 */     this.focusTraversalKeys = new Set[3];
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   String constructComponentName()
/*       */   {
/*  1001 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String getName()
/*       */   {
/*  1013 */     if ((this.name == null) && (!this.nameExplicitlySet)) {
/*  1014 */       synchronized (getObjectLock()) {
/*  1015 */         if ((this.name == null) && (!this.nameExplicitlySet))
/*  1016 */           this.name = constructComponentName();
/*       */       }
/*       */     }
/*  1019 */     return this.name;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setName(String paramString)
/*       */   {
/*       */     String str;
/*       */     
/*       */ 
/*       */ 
/*  1031 */     synchronized (getObjectLock()) {
/*  1032 */       str = this.name;
/*  1033 */       this.name = paramString;
/*  1034 */       this.nameExplicitlySet = true;
/*       */     }
/*  1036 */     firePropertyChange("name", str, paramString);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Container getParent()
/*       */   {
/*  1045 */     return getParent_NoClientCode();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final Container getParent_NoClientCode()
/*       */   {
/*  1053 */     return this.parent;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   Container getContainer()
/*       */   {
/*  1060 */     return getParent_NoClientCode();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public ComponentPeer getPeer()
/*       */   {
/*  1070 */     return this.peer;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void setDropTarget(DropTarget paramDropTarget)
/*       */   {
/*  1083 */     if ((paramDropTarget == this.dropTarget) || ((this.dropTarget != null) && (this.dropTarget.equals(paramDropTarget)))) {
/*       */       return;
/*       */     }
/*       */     
/*       */     DropTarget localDropTarget1;
/*  1088 */     if ((localDropTarget1 = this.dropTarget) != null) {
/*  1089 */       if (this.peer != null) { this.dropTarget.removeNotify(this.peer);
/*       */       }
/*  1091 */       DropTarget localDropTarget2 = this.dropTarget;
/*       */       
/*  1093 */       this.dropTarget = null;
/*       */       try
/*       */       {
/*  1096 */         localDropTarget2.setComponent(null);
/*       */       }
/*       */       catch (IllegalArgumentException localIllegalArgumentException2) {}
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  1104 */     if ((this.dropTarget = paramDropTarget) != null) {
/*       */       try {
/*  1106 */         this.dropTarget.setComponent(this);
/*  1107 */         if (this.peer != null) this.dropTarget.addNotify(this.peer);
/*       */       } catch (IllegalArgumentException localIllegalArgumentException1) {
/*  1109 */         if (localDropTarget1 != null) {
/*       */           try {
/*  1111 */             localDropTarget1.setComponent(this);
/*  1112 */             if (this.peer != null) { this.dropTarget.addNotify(this.peer);
/*       */             }
/*       */           }
/*       */           catch (IllegalArgumentException localIllegalArgumentException3) {}
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized DropTarget getDropTarget()
/*       */   {
/*  1126 */     return this.dropTarget;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   final GraphicsConfiguration getGraphicsConfiguration_NoClientCode()
/*       */   {
/*  1150 */     return this.graphicsConfig;
/*       */   }
/*       */   
/*       */   void setGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration) {
/*  1154 */     synchronized (getTreeLock()) {
/*  1155 */       if (updateGraphicsData(paramGraphicsConfiguration)) {
/*  1156 */         removeNotify();
/*  1157 */         addNotify();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/*  1163 */     checkTreeLock();
/*       */     
/*  1165 */     if (this.graphicsConfig == paramGraphicsConfiguration) {
/*  1166 */       return false;
/*       */     }
/*       */     
/*  1169 */     this.graphicsConfig = paramGraphicsConfiguration;
/*       */     
/*  1171 */     ComponentPeer localComponentPeer = getPeer();
/*  1172 */     if (localComponentPeer != null) {
/*  1173 */       return localComponentPeer.updateGraphicsData(paramGraphicsConfiguration);
/*       */     }
/*  1175 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   void checkGD(String paramString)
/*       */   {
/*  1183 */     if ((this.graphicsConfig != null) && 
/*  1184 */       (!this.graphicsConfig.getDevice().getIDstring().equals(paramString))) {
/*  1185 */       throw new IllegalArgumentException("adding a container to a container on a different GraphicsDevice");
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final Object getTreeLock()
/*       */   {
/*  1198 */     return LOCK;
/*       */   }
/*       */   
/*       */   final void checkTreeLock() {
/*  1202 */     if (!Thread.holdsLock(getTreeLock())) {
/*  1203 */       throw new IllegalStateException("This function should be called while holding treeLock");
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Toolkit getToolkit()
/*       */   {
/*  1216 */     return getToolkitImpl();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final Toolkit getToolkitImpl()
/*       */   {
/*  1224 */     Container localContainer = this.parent;
/*  1225 */     if (localContainer != null) {
/*  1226 */       return localContainer.getToolkitImpl();
/*       */     }
/*  1228 */     return Toolkit.getDefaultToolkit();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isValid()
/*       */   {
/*  1245 */     return (this.peer != null) && (this.valid);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isDisplayable()
/*       */   {
/*  1273 */     return getPeer() != null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Transient
/*       */   public boolean isVisible()
/*       */   {
/*  1288 */     return isVisible_NoClientCode();
/*       */   }
/*       */   
/*  1291 */   final boolean isVisible_NoClientCode() { return this.visible; }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   boolean isRecursivelyVisible()
/*       */   {
/*  1301 */     return (this.visible) && ((this.parent == null) || (this.parent.isRecursivelyVisible()));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   Point pointRelativeToComponent(Point paramPoint)
/*       */   {
/*  1309 */     Point localPoint = getLocationOnScreen();
/*  1310 */     return new Point(paramPoint.x - localPoint.x, paramPoint.y - localPoint.y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Component findUnderMouseInWindow(PointerInfo paramPointerInfo)
/*       */   {
/*  1324 */     if (!isShowing()) {
/*  1325 */       return null;
/*       */     }
/*  1327 */     Window localWindow = getContainingWindow();
/*  1328 */     if (!Toolkit.getDefaultToolkit().getMouseInfoPeer().isWindowUnderMouse(localWindow)) {
/*  1329 */       return null;
/*       */     }
/*       */     
/*  1332 */     Point localPoint = localWindow.pointRelativeToComponent(paramPointerInfo.getLocation());
/*  1333 */     Component localComponent = localWindow.findComponentAt(localPoint.x, localPoint.y, true);
/*       */     
/*       */ 
/*  1336 */     return localComponent;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Point getMousePosition()
/*       */     throws HeadlessException
/*       */   {
/*  1367 */     if (GraphicsEnvironment.isHeadless()) {
/*  1368 */       throw new HeadlessException();
/*       */     }
/*       */     
/*  1371 */     PointerInfo localPointerInfo = (PointerInfo)AccessController.doPrivileged(new PrivilegedAction()
/*       */     {
/*       */       public PointerInfo run() {
/*  1374 */         return MouseInfo.getPointerInfo();
/*       */       }
/*       */     });
/*       */     
/*       */ 
/*  1379 */     synchronized (getTreeLock()) {
/*  1380 */       Component localComponent = findUnderMouseInWindow(localPointerInfo);
/*  1381 */       if (!isSameOrAncestorOf(localComponent, true)) {
/*  1382 */         return null;
/*       */       }
/*  1384 */       return pointRelativeToComponent(localPointerInfo.getLocation());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   boolean isSameOrAncestorOf(Component paramComponent, boolean paramBoolean)
/*       */   {
/*  1392 */     return paramComponent == this;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isShowing()
/*       */   {
/*  1414 */     if ((this.visible) && (this.peer != null)) {
/*  1415 */       Container localContainer = this.parent;
/*  1416 */       return (localContainer == null) || (localContainer.isShowing());
/*       */     }
/*  1418 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isEnabled()
/*       */   {
/*  1432 */     return isEnabledImpl();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final boolean isEnabledImpl()
/*       */   {
/*  1440 */     return this.enabled;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setEnabled(boolean paramBoolean)
/*       */   {
/*  1461 */     enable(paramBoolean);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void enable()
/*       */   {
/*  1470 */     if (!this.enabled) {
/*  1471 */       synchronized (getTreeLock()) {
/*  1472 */         this.enabled = true;
/*  1473 */         ComponentPeer localComponentPeer = this.peer;
/*  1474 */         if (localComponentPeer != null) {
/*  1475 */           localComponentPeer.setEnabled(true);
/*  1476 */           if (this.visible) {
/*  1477 */             updateCursorImmediately();
/*       */           }
/*       */         }
/*       */       }
/*  1481 */       if (this.accessibleContext != null) {
/*  1482 */         this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void enable(boolean paramBoolean)
/*       */   {
/*  1495 */     if (paramBoolean) {
/*  1496 */       enable();
/*       */     } else {
/*  1498 */       disable();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void disable()
/*       */   {
/*  1508 */     if (this.enabled) {
/*  1509 */       KeyboardFocusManager.clearMostRecentFocusOwner(this);
/*  1510 */       synchronized (getTreeLock()) {
/*  1511 */         this.enabled = false;
/*       */         
/*  1513 */         if (((isFocusOwner()) || ((containsFocus()) && (!isLightweight()))) && 
/*  1514 */           (KeyboardFocusManager.isAutoFocusTransferEnabled()))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1520 */           transferFocus(false);
/*       */         }
/*  1522 */         ComponentPeer localComponentPeer = this.peer;
/*  1523 */         if (localComponentPeer != null) {
/*  1524 */           localComponentPeer.setEnabled(false);
/*  1525 */           if (this.visible) {
/*  1526 */             updateCursorImmediately();
/*       */           }
/*       */         }
/*       */       }
/*  1530 */       if (this.accessibleContext != null) {
/*  1531 */         this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isDoubleBuffered()
/*       */   {
/*  1547 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void enableInputMethods(boolean paramBoolean)
/*       */   {
/*       */     java.awt.im.InputContext localInputContext;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1563 */     if (paramBoolean) {
/*  1564 */       if ((this.eventMask & 0x1000) != 0L) {
/*  1565 */         return;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1570 */       if (isFocusOwner()) {
/*  1571 */         localInputContext = getInputContext();
/*  1572 */         if (localInputContext != null) {
/*  1573 */           FocusEvent localFocusEvent = new FocusEvent(this, 1004);
/*       */           
/*  1575 */           localInputContext.dispatchEvent(localFocusEvent);
/*       */         }
/*       */       }
/*       */       
/*  1579 */       this.eventMask |= 0x1000;
/*       */     } else {
/*  1581 */       if ((this.eventMask & 0x1000) != 0L) {
/*  1582 */         localInputContext = getInputContext();
/*  1583 */         if (localInputContext != null) {
/*  1584 */           localInputContext.endComposition();
/*  1585 */           localInputContext.removeNotify(this);
/*       */         }
/*       */       }
/*  1588 */       this.eventMask &= 0xFFFFFFFFFFFFEFFF;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setVisible(boolean paramBoolean)
/*       */   {
/*  1606 */     show(paramBoolean);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void show()
/*       */   {
/*  1615 */     if (!this.visible) {
/*  1616 */       synchronized (getTreeLock()) {
/*  1617 */         this.visible = true;
/*  1618 */         mixOnShowing();
/*  1619 */         ComponentPeer localComponentPeer = this.peer;
/*  1620 */         if (localComponentPeer != null) {
/*  1621 */           localComponentPeer.setVisible(true);
/*  1622 */           createHierarchyEvents(1400, this, this.parent, 4L, 
/*       */           
/*       */ 
/*  1625 */             Toolkit.enabledOnToolkit(32768L));
/*  1626 */           if ((localComponentPeer instanceof LightweightPeer)) {
/*  1627 */             repaint();
/*       */           }
/*  1629 */           updateCursorImmediately();
/*       */         }
/*       */         
/*  1632 */         if ((this.componentListener != null) || ((this.eventMask & 1L) != 0L) || 
/*       */         
/*  1634 */           (Toolkit.enabledOnToolkit(1L))) {
/*  1635 */           ComponentEvent localComponentEvent = new ComponentEvent(this, 102);
/*       */           
/*  1637 */           Toolkit.getEventQueue().postEvent(localComponentEvent);
/*       */         }
/*       */       }
/*  1640 */       ??? = this.parent;
/*  1641 */       if (??? != null) {
/*  1642 */         ((Container)???).invalidate();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void show(boolean paramBoolean)
/*       */   {
/*  1653 */     if (paramBoolean) {
/*  1654 */       show();
/*       */     } else {
/*  1656 */       hide();
/*       */     }
/*       */   }
/*       */   
/*       */   boolean containsFocus() {
/*  1661 */     return isFocusOwner();
/*       */   }
/*       */   
/*       */   void clearMostRecentFocusOwnerOnHide() {
/*  1665 */     KeyboardFocusManager.clearMostRecentFocusOwner(this);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void clearLightweightDispatcherOnRemove(Component paramComponent)
/*       */   {
/*  1676 */     if (this.parent != null) {
/*  1677 */       this.parent.clearLightweightDispatcherOnRemove(paramComponent);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void hide()
/*       */   {
/*  1687 */     this.isPacked = false;
/*       */     
/*  1689 */     if (this.visible) {
/*  1690 */       clearCurrentFocusCycleRootOnHide();
/*  1691 */       clearMostRecentFocusOwnerOnHide();
/*  1692 */       synchronized (getTreeLock()) {
/*  1693 */         this.visible = false;
/*  1694 */         mixOnHiding(isLightweight());
/*  1695 */         if ((containsFocus()) && (KeyboardFocusManager.isAutoFocusTransferEnabled())) {
/*  1696 */           transferFocus(true);
/*       */         }
/*  1698 */         ComponentPeer localComponentPeer = this.peer;
/*  1699 */         if (localComponentPeer != null) {
/*  1700 */           localComponentPeer.setVisible(false);
/*  1701 */           createHierarchyEvents(1400, this, this.parent, 4L, 
/*       */           
/*       */ 
/*  1704 */             Toolkit.enabledOnToolkit(32768L));
/*  1705 */           if ((localComponentPeer instanceof LightweightPeer)) {
/*  1706 */             repaint();
/*       */           }
/*  1708 */           updateCursorImmediately();
/*       */         }
/*  1710 */         if ((this.componentListener != null) || ((this.eventMask & 1L) != 0L) || 
/*       */         
/*  1712 */           (Toolkit.enabledOnToolkit(1L))) {
/*  1713 */           ComponentEvent localComponentEvent = new ComponentEvent(this, 103);
/*       */           
/*  1715 */           Toolkit.getEventQueue().postEvent(localComponentEvent);
/*       */         }
/*       */       }
/*  1718 */       ??? = this.parent;
/*  1719 */       if (??? != null) {
/*  1720 */         ((Container)???).invalidate();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Transient
/*       */   public Color getForeground()
/*       */   {
/*  1737 */     Color localColor = this.foreground;
/*  1738 */     if (localColor != null) {
/*  1739 */       return localColor;
/*       */     }
/*  1741 */     Container localContainer = this.parent;
/*  1742 */     return localContainer != null ? localContainer.getForeground() : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setForeground(Color paramColor)
/*       */   {
/*  1755 */     Color localColor = this.foreground;
/*  1756 */     ComponentPeer localComponentPeer = this.peer;
/*  1757 */     this.foreground = paramColor;
/*  1758 */     if (localComponentPeer != null) {
/*  1759 */       paramColor = getForeground();
/*  1760 */       if (paramColor != null) {
/*  1761 */         localComponentPeer.setForeground(paramColor);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1766 */     firePropertyChange("foreground", localColor, paramColor);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isForegroundSet()
/*       */   {
/*  1779 */     return this.foreground != null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Transient
/*       */   public Color getBackground()
/*       */   {
/*  1792 */     Color localColor = this.background;
/*  1793 */     if (localColor != null) {
/*  1794 */       return localColor;
/*       */     }
/*  1796 */     Container localContainer = this.parent;
/*  1797 */     return localContainer != null ? localContainer.getBackground() : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setBackground(Color paramColor)
/*       */   {
/*  1816 */     Color localColor = this.background;
/*  1817 */     ComponentPeer localComponentPeer = this.peer;
/*  1818 */     this.background = paramColor;
/*  1819 */     if (localComponentPeer != null) {
/*  1820 */       paramColor = getBackground();
/*  1821 */       if (paramColor != null) {
/*  1822 */         localComponentPeer.setBackground(paramColor);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1827 */     firePropertyChange("background", localColor, paramColor);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isBackgroundSet()
/*       */   {
/*  1840 */     return this.background != null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Transient
/*       */   public Font getFont()
/*       */   {
/*  1852 */     return getFont_NoClientCode();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final Font getFont_NoClientCode()
/*       */   {
/*  1860 */     Font localFont = this.font;
/*  1861 */     if (localFont != null) {
/*  1862 */       return localFont;
/*       */     }
/*  1864 */     Container localContainer = this.parent;
/*  1865 */     return localContainer != null ? localContainer.getFont_NoClientCode() : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setFont(Font paramFont)
/*       */   {
/*       */     Font localFont1;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     Font localFont2;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  1885 */     synchronized (getTreeLock()) {
/*  1886 */       localFont1 = this.font;
/*  1887 */       localFont2 = this.font = paramFont;
/*  1888 */       ComponentPeer localComponentPeer = this.peer;
/*  1889 */       if (localComponentPeer != null) {
/*  1890 */         paramFont = getFont();
/*  1891 */         if (paramFont != null) {
/*  1892 */           localComponentPeer.setFont(paramFont);
/*  1893 */           this.peerFont = paramFont;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1899 */     firePropertyChange("font", localFont1, localFont2);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  1904 */     if ((paramFont != localFont1) && ((localFont1 == null) || 
/*  1905 */       (!localFont1.equals(paramFont)))) {
/*  1906 */       invalidateIfValid();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isFontSet()
/*       */   {
/*  1920 */     return this.font != null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Locale getLocale()
/*       */   {
/*  1935 */     Locale localLocale = this.locale;
/*  1936 */     if (localLocale != null) {
/*  1937 */       return localLocale;
/*       */     }
/*  1939 */     Container localContainer = this.parent;
/*       */     
/*  1941 */     if (localContainer == null) {
/*  1942 */       throw new IllegalComponentStateException("This component must have a parent in order to determine its locale");
/*       */     }
/*  1944 */     return localContainer.getLocale();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setLocale(Locale paramLocale)
/*       */   {
/*  1960 */     Locale localLocale = this.locale;
/*  1961 */     this.locale = paramLocale;
/*       */     
/*       */ 
/*       */ 
/*  1965 */     firePropertyChange("locale", localLocale, paramLocale);
/*       */     
/*       */ 
/*  1968 */     invalidateIfValid();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ColorModel getColorModel()
/*       */   {
/*  1981 */     ComponentPeer localComponentPeer = this.peer;
/*  1982 */     if ((localComponentPeer != null) && (!(localComponentPeer instanceof LightweightPeer)))
/*  1983 */       return localComponentPeer.getColorModel();
/*  1984 */     if (GraphicsEnvironment.isHeadless()) {
/*  1985 */       return ColorModel.getRGBdefault();
/*       */     }
/*  1987 */     return getToolkit().getColorModel();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Point getLocation()
/*       */   {
/*  2011 */     return location();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   final Point getLocationOnScreen_NoTreeLock()
/*       */   {
/*  2038 */     if ((this.peer != null) && (isShowing())) {
/*  2039 */       if ((this.peer instanceof LightweightPeer))
/*       */       {
/*       */ 
/*  2042 */         localObject1 = getNativeContainer();
/*  2043 */         Point localPoint = ((Container)localObject1).peer.getLocationOnScreen();
/*  2044 */         for (Object localObject2 = this; localObject2 != localObject1; localObject2 = ((Component)localObject2).getParent()) {
/*  2045 */           localPoint.x += ((Component)localObject2).x;
/*  2046 */           localPoint.y += ((Component)localObject2).y;
/*       */         }
/*  2048 */         return localPoint;
/*       */       }
/*  2050 */       Object localObject1 = this.peer.getLocationOnScreen();
/*  2051 */       return (Point)localObject1;
/*       */     }
/*       */     
/*  2054 */     throw new IllegalComponentStateException("component must be showing on the screen to determine its location");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public Point location()
/*       */   {
/*  2065 */     return location_NoClientCode();
/*       */   }
/*       */   
/*       */   private Point location_NoClientCode() {
/*  2069 */     return new Point(this.x, this.y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setLocation(int paramInt1, int paramInt2)
/*       */   {
/*  2090 */     move(paramInt1, paramInt2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void move(int paramInt1, int paramInt2)
/*       */   {
/*  2099 */     synchronized (getTreeLock()) {
/*  2100 */       setBoundsOp(1);
/*  2101 */       setBounds(paramInt1, paramInt2, this.width, this.height);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setLocation(Point paramPoint)
/*       */   {
/*  2122 */     setLocation(paramPoint.x, paramPoint.y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Dimension getSize()
/*       */   {
/*  2138 */     return size();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public Dimension size()
/*       */   {
/*  2147 */     return new Dimension(this.width, this.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setSize(int paramInt1, int paramInt2)
/*       */   {
/*  2165 */     resize(paramInt1, paramInt2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void resize(int paramInt1, int paramInt2)
/*       */   {
/*  2174 */     synchronized (getTreeLock()) {
/*  2175 */       setBoundsOp(2);
/*  2176 */       setBounds(this.x, this.y, paramInt1, paramInt2);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setSize(Dimension paramDimension)
/*       */   {
/*  2196 */     resize(paramDimension);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void resize(Dimension paramDimension)
/*       */   {
/*  2205 */     setSize(paramDimension.width, paramDimension.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Rectangle getBounds()
/*       */   {
/*  2219 */     return bounds();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public Rectangle bounds()
/*       */   {
/*  2228 */     return new Rectangle(this.x, this.y, this.width, this.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */   {
/*  2253 */     reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */   {
/*  2262 */     synchronized (getTreeLock()) {
/*       */       try {
/*  2264 */         setBoundsOp(3);
/*  2265 */         boolean bool1 = (this.width != paramInt3) || (this.height != paramInt4);
/*  2266 */         boolean bool2 = (this.x != paramInt1) || (this.y != paramInt2);
/*  2267 */         if ((!bool1) && (!bool2))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2312 */           setBoundsOp(5);
/*       */         }
/*       */         else
/*       */         {
/*  2270 */           int i = this.x;
/*  2271 */           int j = this.y;
/*  2272 */           int k = this.width;
/*  2273 */           int m = this.height;
/*  2274 */           this.x = paramInt1;
/*  2275 */           this.y = paramInt2;
/*  2276 */           this.width = paramInt3;
/*  2277 */           this.height = paramInt4;
/*       */           
/*  2279 */           if (bool1) {
/*  2280 */             this.isPacked = false;
/*       */           }
/*       */           
/*  2283 */           int n = 1;
/*  2284 */           mixOnReshaping();
/*  2285 */           if (this.peer != null)
/*       */           {
/*  2287 */             if (!(this.peer instanceof LightweightPeer)) {
/*  2288 */               reshapeNativePeer(paramInt1, paramInt2, paramInt3, paramInt4, getBoundsOp());
/*       */               
/*  2290 */               bool1 = (k != this.width) || (m != this.height);
/*  2291 */               bool2 = (i != this.x) || (j != this.y);
/*       */               
/*       */ 
/*       */ 
/*       */ 
/*  2296 */               if ((this instanceof Window)) {
/*  2297 */                 n = 0;
/*       */               }
/*       */             }
/*  2300 */             if (bool1) {
/*  2301 */               invalidate();
/*       */             }
/*  2303 */             if (this.parent != null) {
/*  2304 */               this.parent.invalidateIfValid();
/*       */             }
/*       */           }
/*  2307 */           if (n != 0) {
/*  2308 */             notifyNewBounds(bool1, bool2);
/*       */           }
/*  2310 */           repaintParentIfNeeded(i, j, k, m);
/*       */         }
/*  2312 */       } finally { setBoundsOp(5);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private void repaintParentIfNeeded(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */   {
/*  2320 */     if ((this.parent != null) && ((this.peer instanceof LightweightPeer)) && (isShowing()))
/*       */     {
/*  2322 */       this.parent.repaint(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */       
/*  2324 */       repaint();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private void reshapeNativePeer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*       */   {
/*  2331 */     int i = paramInt1;
/*  2332 */     int j = paramInt2;
/*  2333 */     for (Container localContainer = this.parent; 
/*  2334 */         (localContainer != null) && ((localContainer.peer instanceof LightweightPeer)); 
/*  2335 */         localContainer = localContainer.parent)
/*       */     {
/*  2337 */       i += localContainer.x;
/*  2338 */       j += localContainer.y;
/*       */     }
/*  2340 */     this.peer.setBounds(i, j, paramInt3, paramInt4, paramInt5);
/*       */   }
/*       */   
/*       */   private void notifyNewBounds(boolean paramBoolean1, boolean paramBoolean2)
/*       */   {
/*  2345 */     if ((this.componentListener != null) || ((this.eventMask & 1L) != 0L) || 
/*       */     
/*  2347 */       (Toolkit.enabledOnToolkit(1L))) {
/*       */       ComponentEvent localComponentEvent;
/*  2349 */       if (paramBoolean1) {
/*  2350 */         localComponentEvent = new ComponentEvent(this, 101);
/*       */         
/*  2352 */         Toolkit.getEventQueue().postEvent(localComponentEvent);
/*       */       }
/*  2354 */       if (paramBoolean2) {
/*  2355 */         localComponentEvent = new ComponentEvent(this, 100);
/*       */         
/*  2357 */         Toolkit.getEventQueue().postEvent(localComponentEvent);
/*       */       }
/*       */     }
/*  2360 */     else if (((this instanceof Container)) && (((Container)this).countComponents() > 0))
/*       */     {
/*  2362 */       boolean bool = Toolkit.enabledOnToolkit(65536L);
/*  2363 */       if (paramBoolean1)
/*       */       {
/*  2365 */         ((Container)this).createChildHierarchyEvents(1402, 0L, bool);
/*       */       }
/*       */       
/*  2368 */       if (paramBoolean2) {
/*  2369 */         ((Container)this).createChildHierarchyEvents(1401, 0L, bool);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setBounds(Rectangle paramRectangle)
/*       */   {
/*  2397 */     setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int getX()
/*       */   {
/*  2412 */     return this.x;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int getY()
/*       */   {
/*  2427 */     return this.y;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int getWidth()
/*       */   {
/*  2442 */     return this.width;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int getHeight()
/*       */   {
/*  2457 */     return this.height;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Rectangle getBounds(Rectangle paramRectangle)
/*       */   {
/*  2472 */     if (paramRectangle == null) {
/*  2473 */       return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*       */     }
/*       */     
/*  2476 */     paramRectangle.setBounds(getX(), getY(), getWidth(), getHeight());
/*  2477 */     return paramRectangle;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Dimension getSize(Dimension paramDimension)
/*       */   {
/*  2492 */     if (paramDimension == null) {
/*  2493 */       return new Dimension(getWidth(), getHeight());
/*       */     }
/*       */     
/*  2496 */     paramDimension.setSize(getWidth(), getHeight());
/*  2497 */     return paramDimension;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Point getLocation(Point paramPoint)
/*       */   {
/*  2513 */     if (paramPoint == null) {
/*  2514 */       return new Point(getX(), getY());
/*       */     }
/*       */     
/*  2517 */     paramPoint.setLocation(getX(), getY());
/*  2518 */     return paramPoint;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isOpaque()
/*       */   {
/*  2540 */     if (getPeer() == null) {
/*  2541 */       return false;
/*       */     }
/*       */     
/*  2544 */     return !isLightweight();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isLightweight()
/*       */   {
/*  2566 */     return getPeer() instanceof LightweightPeer;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setPreferredSize(Dimension paramDimension)
/*       */   {
/*       */     Dimension localDimension;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2586 */     if (this.prefSizeSet) {
/*  2587 */       localDimension = this.prefSize;
/*       */     }
/*       */     else {
/*  2590 */       localDimension = null;
/*       */     }
/*  2592 */     this.prefSize = paramDimension;
/*  2593 */     this.prefSizeSet = (paramDimension != null);
/*  2594 */     firePropertyChange("preferredSize", localDimension, paramDimension);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isPreferredSizeSet()
/*       */   {
/*  2607 */     return this.prefSizeSet;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Dimension getPreferredSize()
/*       */   {
/*  2618 */     return preferredSize();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public Dimension preferredSize()
/*       */   {
/*  2631 */     Dimension localDimension = this.prefSize;
/*  2632 */     if ((localDimension == null) || ((!isPreferredSizeSet()) && (!isValid()))) {
/*  2633 */       synchronized (getTreeLock())
/*       */       {
/*       */ 
/*  2636 */         this.prefSize = (this.peer != null ? this.peer.getPreferredSize() : getMinimumSize());
/*  2637 */         localDimension = this.prefSize;
/*       */       }
/*       */     }
/*  2640 */     return new Dimension(localDimension);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMinimumSize(Dimension paramDimension)
/*       */   {
/*       */     Dimension localDimension;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2659 */     if (this.minSizeSet) {
/*  2660 */       localDimension = this.minSize;
/*       */     }
/*       */     else {
/*  2663 */       localDimension = null;
/*       */     }
/*  2665 */     this.minSize = paramDimension;
/*  2666 */     this.minSizeSet = (paramDimension != null);
/*  2667 */     firePropertyChange("minimumSize", localDimension, paramDimension);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isMinimumSizeSet()
/*       */   {
/*  2679 */     return this.minSizeSet;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Dimension getMinimumSize()
/*       */   {
/*  2689 */     return minimumSize();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public Dimension minimumSize()
/*       */   {
/*  2701 */     Dimension localDimension = this.minSize;
/*  2702 */     if ((localDimension == null) || ((!isMinimumSizeSet()) && (!isValid()))) {
/*  2703 */       synchronized (getTreeLock())
/*       */       {
/*       */ 
/*  2706 */         this.minSize = (this.peer != null ? this.peer.getMinimumSize() : size());
/*  2707 */         localDimension = this.minSize;
/*       */       }
/*       */     }
/*  2710 */     return new Dimension(localDimension);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMaximumSize(Dimension paramDimension)
/*       */   {
/*       */     Dimension localDimension;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2730 */     if (this.maxSizeSet) {
/*  2731 */       localDimension = this.maxSize;
/*       */     }
/*       */     else {
/*  2734 */       localDimension = null;
/*       */     }
/*  2736 */     this.maxSize = paramDimension;
/*  2737 */     this.maxSizeSet = (paramDimension != null);
/*  2738 */     firePropertyChange("maximumSize", localDimension, paramDimension);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isMaximumSizeSet()
/*       */   {
/*  2750 */     return this.maxSizeSet;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Dimension getMaximumSize()
/*       */   {
/*  2761 */     if (isMaximumSizeSet()) {
/*  2762 */       return new Dimension(this.maxSize);
/*       */     }
/*  2764 */     return new Dimension(32767, 32767);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float getAlignmentX()
/*       */   {
/*  2775 */     return 0.5F;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float getAlignmentY()
/*       */   {
/*  2786 */     return 0.5F;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int getBaseline(int paramInt1, int paramInt2)
/*       */   {
/*  2814 */     if ((paramInt1 < 0) || (paramInt2 < 0)) {
/*  2815 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*       */     }
/*       */     
/*  2818 */     return -1;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public BaselineResizeBehavior getBaselineResizeBehavior()
/*       */   {
/*  2843 */     return BaselineResizeBehavior.OTHER;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void doLayout()
/*       */   {
/*  2854 */     layout();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void validate()
/*       */   {
/*  2878 */     synchronized (getTreeLock()) {
/*  2879 */       ComponentPeer localComponentPeer = this.peer;
/*  2880 */       boolean bool = isValid();
/*  2881 */       if ((!bool) && (localComponentPeer != null)) {
/*  2882 */         Font localFont1 = getFont();
/*  2883 */         Font localFont2 = this.peerFont;
/*  2884 */         if ((localFont1 != localFont2) && ((localFont2 == null) || 
/*  2885 */           (!localFont2.equals(localFont1)))) {
/*  2886 */           localComponentPeer.setFont(localFont1);
/*  2887 */           this.peerFont = localFont1;
/*       */         }
/*  2889 */         localComponentPeer.layout();
/*       */       }
/*  2891 */       this.valid = true;
/*  2892 */       if (!bool) {
/*  2893 */         mixOnValidating();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void invalidate()
/*       */   {
/*  2921 */     synchronized (getTreeLock())
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  2926 */       this.valid = false;
/*  2927 */       if (!isPreferredSizeSet()) {
/*  2928 */         this.prefSize = null;
/*       */       }
/*  2930 */       if (!isMinimumSizeSet()) {
/*  2931 */         this.minSize = null;
/*       */       }
/*  2933 */       if (!isMaximumSizeSet()) {
/*  2934 */         this.maxSize = null;
/*       */       }
/*  2936 */       invalidateParent();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void invalidateParent()
/*       */   {
/*  2946 */     if (this.parent != null) {
/*  2947 */       this.parent.invalidateIfValid();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   final void invalidateIfValid()
/*       */   {
/*  2954 */     if (isValid()) {
/*  2955 */       invalidate();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void revalidate()
/*       */   {
/*  2976 */     revalidateSynchronously();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   final void revalidateSynchronously()
/*       */   {
/*  2983 */     synchronized (getTreeLock()) {
/*  2984 */       invalidate();
/*       */       
/*  2986 */       Container localContainer = getContainer();
/*  2987 */       if (localContainer == null)
/*       */       {
/*  2989 */         validate();
/*       */       } else {
/*  2991 */         while ((!localContainer.isValidateRoot()) && 
/*  2992 */           (localContainer.getContainer() != null))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2998 */           localContainer = localContainer.getContainer();
/*       */         }
/*       */         
/*  3001 */         localContainer.validate();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Graphics getGraphics()
/*       */   {
/*  3016 */     if ((this.peer instanceof LightweightPeer))
/*       */     {
/*       */ 
/*       */ 
/*  3020 */       if (this.parent == null) return null;
/*  3021 */       localObject = this.parent.getGraphics();
/*  3022 */       if (localObject == null) return null;
/*  3023 */       if ((localObject instanceof ConstrainableGraphics)) {
/*  3024 */         ((ConstrainableGraphics)localObject).constrain(this.x, this.y, this.width, this.height);
/*       */       } else {
/*  3026 */         ((Graphics)localObject).translate(this.x, this.y);
/*  3027 */         ((Graphics)localObject).setClip(0, 0, this.width, this.height);
/*       */       }
/*  3029 */       ((Graphics)localObject).setFont(getFont());
/*  3030 */       return (Graphics)localObject;
/*       */     }
/*  3032 */     Object localObject = this.peer;
/*  3033 */     return localObject != null ? ((ComponentPeer)localObject).getGraphics() : null;
/*       */   }
/*       */   
/*       */   final Graphics getGraphics_NoClientCode()
/*       */   {
/*  3038 */     ComponentPeer localComponentPeer = this.peer;
/*  3039 */     if ((localComponentPeer instanceof LightweightPeer))
/*       */     {
/*       */ 
/*       */ 
/*  3043 */       Container localContainer = this.parent;
/*  3044 */       if (localContainer == null) return null;
/*  3045 */       Graphics localGraphics = localContainer.getGraphics_NoClientCode();
/*  3046 */       if (localGraphics == null) return null;
/*  3047 */       if ((localGraphics instanceof ConstrainableGraphics)) {
/*  3048 */         ((ConstrainableGraphics)localGraphics).constrain(this.x, this.y, this.width, this.height);
/*       */       } else {
/*  3050 */         localGraphics.translate(this.x, this.y);
/*  3051 */         localGraphics.setClip(0, 0, this.width, this.height);
/*       */       }
/*  3053 */       localGraphics.setFont(getFont_NoClientCode());
/*  3054 */       return localGraphics;
/*       */     }
/*  3056 */     return localComponentPeer != null ? localComponentPeer.getGraphics() : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public FontMetrics getFontMetrics(Font paramFont)
/*       */   {
/*  3082 */     FontManager localFontManager = FontManagerFactory.getInstance();
/*  3083 */     if (((localFontManager instanceof SunFontManager)) && 
/*  3084 */       (((SunFontManager)localFontManager).usePlatformFontMetrics()))
/*       */     {
/*  3086 */       if ((this.peer != null) && (!(this.peer instanceof LightweightPeer)))
/*       */       {
/*  3088 */         return this.peer.getFontMetrics(paramFont);
/*       */       }
/*       */     }
/*  3091 */     return FontDesignMetrics.getMetrics(paramFont);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setCursor(Cursor paramCursor)
/*       */   {
/*  3120 */     this.cursor = paramCursor;
/*  3121 */     updateCursorImmediately();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final void updateCursorImmediately()
/*       */   {
/*  3129 */     if ((this.peer instanceof LightweightPeer)) {
/*  3130 */       Container localContainer = getNativeContainer();
/*       */       
/*  3132 */       if (localContainer == null) { return;
/*       */       }
/*  3134 */       ComponentPeer localComponentPeer = localContainer.getPeer();
/*       */       
/*  3136 */       if (localComponentPeer != null) {
/*  3137 */         localComponentPeer.updateCursorImmediately();
/*       */       }
/*  3139 */     } else if (this.peer != null) {
/*  3140 */       this.peer.updateCursorImmediately();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Cursor getCursor()
/*       */   {
/*  3153 */     return getCursor_NoClientCode();
/*       */   }
/*       */   
/*       */   final Cursor getCursor_NoClientCode() {
/*  3157 */     Cursor localCursor = this.cursor;
/*  3158 */     if (localCursor != null) {
/*  3159 */       return localCursor;
/*       */     }
/*  3161 */     Container localContainer = this.parent;
/*  3162 */     if (localContainer != null) {
/*  3163 */       return localContainer.getCursor_NoClientCode();
/*       */     }
/*  3165 */     return Cursor.getPredefinedCursor(0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isCursorSet()
/*       */   {
/*  3179 */     return this.cursor != null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void update(Graphics paramGraphics)
/*       */   {
/*  3243 */     paint(paramGraphics);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void paintAll(Graphics paramGraphics)
/*       */   {
/*  3259 */     if (isShowing())
/*       */     {
/*  3261 */       GraphicsCallback.PeerPaintCallback.getInstance().runOneComponent(this, new Rectangle(0, 0, this.width, this.height), paramGraphics, paramGraphics
/*  3262 */         .getClip(), 3);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void lightweightPaint(Graphics paramGraphics)
/*       */   {
/*  3275 */     paint(paramGraphics);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void repaint()
/*       */   {
/*  3303 */     repaint(0L, 0, 0, this.width, this.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void repaint(long paramLong)
/*       */   {
/*  3322 */     repaint(paramLong, 0, 0, this.width, this.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void repaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */   {
/*  3346 */     repaint(0L, paramInt1, paramInt2, paramInt3, paramInt4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */   {
/*  3372 */     if ((this.peer instanceof LightweightPeer))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  3377 */       if (this.parent != null) {
/*  3378 */         if (paramInt1 < 0) {
/*  3379 */           paramInt3 += paramInt1;
/*  3380 */           paramInt1 = 0;
/*       */         }
/*  3382 */         if (paramInt2 < 0) {
/*  3383 */           paramInt4 += paramInt2;
/*  3384 */           paramInt2 = 0;
/*       */         }
/*       */         
/*  3387 */         int i = paramInt3 > this.width ? this.width : paramInt3;
/*  3388 */         int j = paramInt4 > this.height ? this.height : paramInt4;
/*       */         
/*  3390 */         if ((i <= 0) || (j <= 0)) {
/*  3391 */           return;
/*       */         }
/*       */         
/*  3394 */         int k = this.x + paramInt1;
/*  3395 */         int m = this.y + paramInt2;
/*  3396 */         this.parent.repaint(paramLong, k, m, i, j);
/*       */       }
/*       */     }
/*  3399 */     else if ((isVisible()) && (this.peer != null) && (paramInt3 > 0) && (paramInt4 > 0))
/*       */     {
/*  3401 */       PaintEvent localPaintEvent = new PaintEvent(this, 801, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*       */       
/*  3403 */       Toolkit.getEventQueue().postEvent(localPaintEvent);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void print(Graphics paramGraphics)
/*       */   {
/*  3425 */     paint(paramGraphics);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void printAll(Graphics paramGraphics)
/*       */   {
/*  3440 */     if (isShowing())
/*       */     {
/*  3442 */       GraphicsCallback.PeerPrintCallback.getInstance().runOneComponent(this, new Rectangle(0, 0, this.width, this.height), paramGraphics, paramGraphics
/*  3443 */         .getClip(), 3);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void lightweightPrint(Graphics paramGraphics)
/*       */   {
/*  3456 */     print(paramGraphics);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private Insets getInsets_NoClientCode()
/*       */   {
/*  3466 */     ComponentPeer localComponentPeer = this.peer;
/*  3467 */     if ((localComponentPeer instanceof ContainerPeer)) {
/*  3468 */       return (Insets)((ContainerPeer)localComponentPeer).getInsets().clone();
/*       */     }
/*  3470 */     return new Insets(0, 0, 0, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*       */   {
/*  3521 */     int i = -1;
/*  3522 */     if ((paramInt1 & 0x30) != 0) {
/*  3523 */       i = 0;
/*  3524 */     } else if (((paramInt1 & 0x8) != 0) && 
/*  3525 */       (isInc)) {
/*  3526 */       i = incRate;
/*  3527 */       if (i < 0) {
/*  3528 */         i = 0;
/*       */       }
/*       */     }
/*       */     
/*  3532 */     if (i >= 0) {
/*  3533 */       repaint(i, 0, 0, this.width, this.height);
/*       */     }
/*  3535 */     return (paramInt1 & 0xA0) == 0;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Image createImage(ImageProducer paramImageProducer)
/*       */   {
/*  3545 */     ComponentPeer localComponentPeer = this.peer;
/*  3546 */     if ((localComponentPeer != null) && (!(localComponentPeer instanceof LightweightPeer))) {
/*  3547 */       return localComponentPeer.createImage(paramImageProducer);
/*       */     }
/*  3549 */     return getToolkit().createImage(paramImageProducer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Image createImage(int paramInt1, int paramInt2)
/*       */   {
/*  3567 */     ComponentPeer localComponentPeer = this.peer;
/*  3568 */     if ((localComponentPeer instanceof LightweightPeer)) {
/*  3569 */       if (this.parent != null) return this.parent.createImage(paramInt1, paramInt2);
/*  3570 */       return null;
/*       */     }
/*  3572 */     return localComponentPeer != null ? localComponentPeer.createImage(paramInt1, paramInt2) : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2)
/*       */   {
/*  3592 */     ComponentPeer localComponentPeer = this.peer;
/*  3593 */     if ((localComponentPeer instanceof LightweightPeer)) {
/*  3594 */       if (this.parent != null) {
/*  3595 */         return this.parent.createVolatileImage(paramInt1, paramInt2);
/*       */       }
/*  3597 */       return null;
/*       */     }
/*       */     
/*  3600 */     return localComponentPeer != null ? localComponentPeer.createVolatileImage(paramInt1, paramInt2) : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2, ImageCapabilities paramImageCapabilities)
/*       */     throws AWTException
/*       */   {
/*  3622 */     return createVolatileImage(paramInt1, paramInt2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean prepareImage(Image paramImage, ImageObserver paramImageObserver)
/*       */   {
/*  3638 */     return prepareImage(paramImage, -1, -1, paramImageObserver);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver)
/*       */   {
/*  3661 */     ComponentPeer localComponentPeer = this.peer;
/*  3662 */     if ((localComponentPeer instanceof LightweightPeer))
/*       */     {
/*       */ 
/*  3665 */       return this.parent != null ? this.parent.prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver) : getToolkit().prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */     }
/*       */     
/*       */ 
/*  3669 */     return localComponentPeer != null ? localComponentPeer.prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver) : getToolkit().prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int checkImage(Image paramImage, ImageObserver paramImageObserver)
/*       */   {
/*  3696 */     return checkImage(paramImage, -1, -1, paramImageObserver);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver)
/*       */   {
/*  3733 */     ComponentPeer localComponentPeer = this.peer;
/*  3734 */     if ((localComponentPeer instanceof LightweightPeer))
/*       */     {
/*       */ 
/*  3737 */       return this.parent != null ? this.parent.checkImage(paramImage, paramInt1, paramInt2, paramImageObserver) : getToolkit().checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */     }
/*       */     
/*       */ 
/*  3741 */     return localComponentPeer != null ? localComponentPeer.checkImage(paramImage, paramInt1, paramInt2, paramImageObserver) : getToolkit().checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void createBufferStrategy(int paramInt)
/*       */   {
/*  3767 */     if (paramInt > 1)
/*       */     {
/*  3769 */       localBufferCapabilities = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), BufferCapabilities.FlipContents.UNDEFINED);
/*       */       
/*       */       try
/*       */       {
/*  3773 */         createBufferStrategy(paramInt, localBufferCapabilities);
/*  3774 */         return;
/*       */       }
/*       */       catch (AWTException localAWTException1) {}
/*       */     }
/*       */     
/*       */ 
/*  3780 */     BufferCapabilities localBufferCapabilities = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), null);
/*       */     
/*       */     try
/*       */     {
/*  3784 */       createBufferStrategy(paramInt, localBufferCapabilities);
/*  3785 */       return;
/*       */ 
/*       */     }
/*       */     catch (AWTException localAWTException2)
/*       */     {
/*  3790 */       localBufferCapabilities = new BufferCapabilities(new ImageCapabilities(false), new ImageCapabilities(false), null);
/*       */       
/*       */       try
/*       */       {
/*  3794 */         createBufferStrategy(paramInt, localBufferCapabilities);
/*  3795 */         return;
/*       */       }
/*       */       catch (AWTException localAWTException3)
/*       */       {
/*  3799 */         throw new InternalError("Could not create a buffer strategy", localAWTException3);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void createBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*       */     throws AWTException
/*       */   {
/*  3828 */     if (paramInt < 1) {
/*  3829 */       throw new IllegalArgumentException("Number of buffers must be at least 1");
/*       */     }
/*       */     
/*  3832 */     if (paramBufferCapabilities == null) {
/*  3833 */       throw new IllegalArgumentException("No capabilities specified");
/*       */     }
/*       */     
/*  3836 */     if (this.bufferStrategy != null) {
/*  3837 */       this.bufferStrategy.dispose();
/*       */     }
/*  3839 */     if (paramInt == 1) {
/*  3840 */       this.bufferStrategy = new SingleBufferStrategy(paramBufferCapabilities);
/*       */     }
/*       */     else {
/*  3843 */       SunGraphicsEnvironment localSunGraphicsEnvironment = (SunGraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  3844 */       if ((!paramBufferCapabilities.isPageFlipping()) && (localSunGraphicsEnvironment.isFlipStrategyPreferred(this.peer))) {
/*  3845 */         paramBufferCapabilities = new ProxyCapabilities(paramBufferCapabilities, null);
/*       */       }
/*       */       
/*  3848 */       if (paramBufferCapabilities.isPageFlipping()) {
/*  3849 */         this.bufferStrategy = new FlipSubRegionBufferStrategy(paramInt, paramBufferCapabilities);
/*       */       } else {
/*  3851 */         this.bufferStrategy = new BltSubRegionBufferStrategy(paramInt, paramBufferCapabilities);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private class ProxyCapabilities
/*       */     extends ExtendedBufferCapabilities
/*       */   {
/*       */     private BufferCapabilities orig;
/*       */     
/*       */ 
/*       */     private ProxyCapabilities(BufferCapabilities paramBufferCapabilities)
/*       */     {
/*  3865 */       super(paramBufferCapabilities
/*  3866 */         .getBackBufferCapabilities(), paramBufferCapabilities
/*  3867 */         .getFlipContents() == BufferCapabilities.FlipContents.BACKGROUND ? BufferCapabilities.FlipContents.BACKGROUND : BufferCapabilities.FlipContents.COPIED);
/*       */       
/*       */ 
/*       */ 
/*  3871 */       this.orig = paramBufferCapabilities;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   BufferStrategy getBufferStrategy()
/*       */   {
/*  3882 */     return this.bufferStrategy;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Image getBackBuffer()
/*       */   {
/*  3891 */     if (this.bufferStrategy != null) { Object localObject;
/*  3892 */       if ((this.bufferStrategy instanceof BltBufferStrategy)) {
/*  3893 */         localObject = (BltBufferStrategy)this.bufferStrategy;
/*  3894 */         return ((BltBufferStrategy)localObject).getBackBuffer(); }
/*  3895 */       if ((this.bufferStrategy instanceof FlipBufferStrategy)) {
/*  3896 */         localObject = (FlipBufferStrategy)this.bufferStrategy;
/*  3897 */         return ((FlipBufferStrategy)localObject).getBackBuffer();
/*       */       }
/*       */     }
/*  3900 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected class FlipBufferStrategy
/*       */     extends BufferStrategy
/*       */   {
/*       */     protected int numBuffers;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected BufferCapabilities caps;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected Image drawBuffer;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected VolatileImage drawVBuffer;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected boolean validatedContents;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     int width;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     int height;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected FlipBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*       */       throws AWTException
/*       */     {
/*  3964 */       if ((!(Component.this instanceof Window)) && (!(Component.this instanceof Canvas)))
/*       */       {
/*       */ 
/*  3967 */         throw new ClassCastException("Component must be a Canvas or Window");
/*       */       }
/*       */       
/*  3970 */       this.numBuffers = paramInt;
/*  3971 */       this.caps = paramBufferCapabilities;
/*  3972 */       createBuffers(paramInt, paramBufferCapabilities);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected void createBuffers(int paramInt, BufferCapabilities paramBufferCapabilities)
/*       */       throws AWTException
/*       */     {
/*  3994 */       if (paramInt < 2) {
/*  3995 */         throw new IllegalArgumentException("Number of buffers cannot be less than two");
/*       */       }
/*  3997 */       if (Component.this.peer == null) {
/*  3998 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       }
/*  4000 */       if ((paramBufferCapabilities == null) || (!paramBufferCapabilities.isPageFlipping())) {
/*  4001 */         throw new IllegalArgumentException("Page flipping capabilities must be specified");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  4006 */       this.width = Component.this.getWidth();
/*  4007 */       this.height = Component.this.getHeight();
/*       */       
/*  4009 */       if (this.drawBuffer != null)
/*       */       {
/*  4011 */         this.drawBuffer = null;
/*  4012 */         this.drawVBuffer = null;
/*  4013 */         destroyBuffers();
/*       */       }
/*       */       
/*       */ 
/*  4017 */       if ((paramBufferCapabilities instanceof ExtendedBufferCapabilities)) {
/*  4018 */         ExtendedBufferCapabilities localExtendedBufferCapabilities = (ExtendedBufferCapabilities)paramBufferCapabilities;
/*       */         
/*  4020 */         if (localExtendedBufferCapabilities.getVSync() == ExtendedBufferCapabilities.VSyncType.VSYNC_ON)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4026 */           if (!VSyncedBSManager.vsyncAllowed(this)) {
/*  4027 */             paramBufferCapabilities = localExtendedBufferCapabilities.derive(ExtendedBufferCapabilities.VSyncType.VSYNC_DEFAULT);
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  4032 */       Component.this.peer.createBuffers(paramInt, paramBufferCapabilities);
/*  4033 */       updateInternalBuffers();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     private void updateInternalBuffers()
/*       */     {
/*  4042 */       this.drawBuffer = getBackBuffer();
/*  4043 */       if ((this.drawBuffer instanceof VolatileImage)) {
/*  4044 */         this.drawVBuffer = ((VolatileImage)this.drawBuffer);
/*       */       } else {
/*  4046 */         this.drawVBuffer = null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected Image getBackBuffer()
/*       */     {
/*  4056 */       if (Component.this.peer != null) {
/*  4057 */         return Component.this.peer.getBackBuffer();
/*       */       }
/*  4059 */       throw new IllegalStateException("Component must have a valid peer");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected void flip(BufferCapabilities.FlipContents paramFlipContents)
/*       */     {
/*  4076 */       if (Component.this.peer != null) {
/*  4077 */         Image localImage = getBackBuffer();
/*  4078 */         if (localImage != null) {
/*  4079 */           Component.this.peer.flip(0, 0, localImage
/*  4080 */             .getWidth(null), localImage
/*  4081 */             .getHeight(null), paramFlipContents);
/*       */         }
/*       */       } else {
/*  4084 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     void flipSubRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents)
/*       */     {
/*  4092 */       if (Component.this.peer != null) {
/*  4093 */         Component.this.peer.flip(paramInt1, paramInt2, paramInt3, paramInt4, paramFlipContents);
/*       */       } else {
/*  4095 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     protected void destroyBuffers()
/*       */     {
/*  4104 */       VSyncedBSManager.releaseVsync(this);
/*  4105 */       if (Component.this.peer != null) {
/*  4106 */         Component.this.peer.destroyBuffers();
/*       */       } else {
/*  4108 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public BufferCapabilities getCapabilities()
/*       */     {
/*  4117 */       if ((this.caps instanceof Component.ProxyCapabilities)) {
/*  4118 */         return Component.ProxyCapabilities.access$300((Component.ProxyCapabilities)this.caps);
/*       */       }
/*  4120 */       return this.caps;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Graphics getDrawGraphics()
/*       */     {
/*  4131 */       revalidate();
/*  4132 */       return this.drawBuffer.getGraphics();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     protected void revalidate()
/*       */     {
/*  4139 */       revalidate(true);
/*       */     }
/*       */     
/*       */     void revalidate(boolean paramBoolean) {
/*  4143 */       this.validatedContents = false;
/*       */       
/*  4145 */       if ((paramBoolean) && ((Component.this.getWidth() != this.width) || (Component.this.getHeight() != this.height)))
/*       */       {
/*       */         try {
/*  4148 */           createBuffers(this.numBuffers, this.caps);
/*       */         }
/*       */         catch (AWTException localAWTException1) {}
/*       */         
/*  4152 */         this.validatedContents = true;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  4157 */       updateInternalBuffers();
/*       */       
/*       */ 
/*  4160 */       if (this.drawVBuffer != null)
/*       */       {
/*  4162 */         GraphicsConfiguration localGraphicsConfiguration = Component.this.getGraphicsConfiguration_NoClientCode();
/*  4163 */         int i = this.drawVBuffer.validate(localGraphicsConfiguration);
/*  4164 */         if (i == 2) {
/*       */           try {
/*  4166 */             createBuffers(this.numBuffers, this.caps);
/*       */           }
/*       */           catch (AWTException localAWTException2) {}
/*       */           
/*  4170 */           if (this.drawVBuffer != null)
/*       */           {
/*  4172 */             this.drawVBuffer.validate(localGraphicsConfiguration);
/*       */           }
/*  4174 */           this.validatedContents = true;
/*  4175 */         } else if (i == 1) {
/*  4176 */           this.validatedContents = true;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean contentsLost()
/*       */     {
/*  4186 */       if (this.drawVBuffer == null) {
/*  4187 */         return false;
/*       */       }
/*  4189 */       return this.drawVBuffer.contentsLost();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean contentsRestored()
/*       */     {
/*  4197 */       return this.validatedContents;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public void show()
/*       */     {
/*  4205 */       flip(this.caps.getFlipContents());
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     void showSubRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */     {
/*  4213 */       flipSubRegion(paramInt1, paramInt2, paramInt3, paramInt4, this.caps.getFlipContents());
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public void dispose()
/*       */     {
/*  4221 */       if (Component.this.bufferStrategy == this) {
/*  4222 */         Component.this.bufferStrategy = null;
/*  4223 */         if (Component.this.peer != null) {
/*  4224 */           destroyBuffers();
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected class BltBufferStrategy
/*       */     extends BufferStrategy
/*       */   {
/*       */     protected BufferCapabilities caps;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     protected VolatileImage[] backBuffers;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     protected boolean validatedContents;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     protected int width;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     protected int height;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     private Insets insets;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     protected BltBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*       */     {
/*  4271 */       this.caps = paramBufferCapabilities;
/*  4272 */       createBackBuffers(paramInt - 1);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public void dispose()
/*       */     {
/*  4280 */       if (this.backBuffers != null) {
/*  4281 */         for (int i = this.backBuffers.length - 1; i >= 0; 
/*  4282 */             i--) {
/*  4283 */           if (this.backBuffers[i] != null) {
/*  4284 */             this.backBuffers[i].flush();
/*  4285 */             this.backBuffers[i] = null;
/*       */           }
/*       */         }
/*       */       }
/*  4289 */       if (Component.this.bufferStrategy == this) {
/*  4290 */         Component.this.bufferStrategy = null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     protected void createBackBuffers(int paramInt)
/*       */     {
/*  4298 */       if (paramInt == 0) {
/*  4299 */         this.backBuffers = null;
/*       */       }
/*       */       else {
/*  4302 */         this.width = Component.this.getWidth();
/*  4303 */         this.height = Component.this.getHeight();
/*  4304 */         this.insets = Component.this.getInsets_NoClientCode();
/*  4305 */         int i = this.width - this.insets.left - this.insets.right;
/*  4306 */         int j = this.height - this.insets.top - this.insets.bottom;
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*  4311 */         i = Math.max(1, i);
/*  4312 */         j = Math.max(1, j);
/*  4313 */         if (this.backBuffers == null) {
/*  4314 */           this.backBuffers = new VolatileImage[paramInt];
/*       */         }
/*       */         else {
/*  4317 */           for (k = 0; k < paramInt; k++) {
/*  4318 */             if (this.backBuffers[k] != null) {
/*  4319 */               this.backBuffers[k].flush();
/*  4320 */               this.backBuffers[k] = null;
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  4326 */         for (int k = 0; k < paramInt; k++) {
/*  4327 */           this.backBuffers[k] = Component.this.createVolatileImage(i, j);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     public BufferCapabilities getCapabilities()
/*       */     {
/*  4336 */       return this.caps;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     public Graphics getDrawGraphics()
/*       */     {
/*  4343 */       revalidate();
/*  4344 */       Image localImage = getBackBuffer();
/*  4345 */       if (localImage == null) {
/*  4346 */         return Component.this.getGraphics();
/*       */       }
/*  4348 */       SunGraphics2D localSunGraphics2D = (SunGraphics2D)localImage.getGraphics();
/*  4349 */       localSunGraphics2D.constrain(-this.insets.left, -this.insets.top, localImage
/*  4350 */         .getWidth(null) + this.insets.left, localImage
/*  4351 */         .getHeight(null) + this.insets.top);
/*  4352 */       return localSunGraphics2D;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     Image getBackBuffer()
/*       */     {
/*  4360 */       if (this.backBuffers != null) {
/*  4361 */         return this.backBuffers[(this.backBuffers.length - 1)];
/*       */       }
/*  4363 */       return null;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public void show()
/*       */     {
/*  4371 */       showSubRegion(this.insets.left, this.insets.top, this.width - this.insets.right, this.height - this.insets.bottom);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     void showSubRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */     {
/*  4386 */       if (this.backBuffers == null) {
/*  4387 */         return;
/*       */       }
/*       */       
/*  4390 */       paramInt1 -= this.insets.left;
/*  4391 */       paramInt3 -= this.insets.left;
/*  4392 */       paramInt2 -= this.insets.top;
/*  4393 */       paramInt4 -= this.insets.top;
/*  4394 */       Graphics localGraphics = Component.this.getGraphics_NoClientCode();
/*  4395 */       if (localGraphics == null)
/*       */       {
/*  4397 */         return;
/*       */       }
/*       */       
/*       */       try
/*       */       {
/*  4402 */         localGraphics.translate(this.insets.left, this.insets.top);
/*  4403 */         for (int i = 0; i < this.backBuffers.length; i++) {
/*  4404 */           localGraphics.drawImage(this.backBuffers[i], paramInt1, paramInt2, paramInt3, paramInt4, paramInt1, paramInt2, paramInt3, paramInt4, null);
/*       */           
/*       */ 
/*       */ 
/*  4408 */           localGraphics.dispose();
/*  4409 */           localGraphics = null;
/*  4410 */           localGraphics = this.backBuffers[i].getGraphics();
/*       */         }
/*       */       } finally {
/*  4413 */         if (localGraphics != null) {
/*  4414 */           localGraphics.dispose();
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     protected void revalidate()
/*       */     {
/*  4423 */       revalidate(true);
/*       */     }
/*       */     
/*       */     void revalidate(boolean paramBoolean) {
/*  4427 */       this.validatedContents = false;
/*       */       
/*  4429 */       if (this.backBuffers == null) {
/*  4430 */         return;
/*       */       }
/*       */       
/*  4433 */       if (paramBoolean) {
/*  4434 */         localObject = Component.this.getInsets_NoClientCode();
/*  4435 */         if ((Component.this.getWidth() != this.width) || (Component.this.getHeight() != this.height) || 
/*  4436 */           (!((Insets)localObject).equals(this.insets)))
/*       */         {
/*  4438 */           createBackBuffers(this.backBuffers.length);
/*  4439 */           this.validatedContents = true;
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  4444 */       Object localObject = Component.this.getGraphicsConfiguration_NoClientCode();
/*       */       
/*  4446 */       int i = this.backBuffers[(this.backBuffers.length - 1)].validate((GraphicsConfiguration)localObject);
/*  4447 */       if (i == 2) {
/*  4448 */         if (paramBoolean) {
/*  4449 */           createBackBuffers(this.backBuffers.length);
/*       */           
/*  4451 */           this.backBuffers[(this.backBuffers.length - 1)].validate((GraphicsConfiguration)localObject);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*  4457 */         this.validatedContents = true;
/*  4458 */       } else if (i == 1) {
/*  4459 */         this.validatedContents = true;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean contentsLost()
/*       */     {
/*  4468 */       if (this.backBuffers == null) {
/*  4469 */         return false;
/*       */       }
/*  4471 */       return this.backBuffers[(this.backBuffers.length - 1)].contentsLost();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean contentsRestored()
/*       */     {
/*  4480 */       return this.validatedContents;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private class FlipSubRegionBufferStrategy
/*       */     extends Component.FlipBufferStrategy
/*       */     implements SubRegionShowable
/*       */   {
/*       */     protected FlipSubRegionBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*       */       throws AWTException
/*       */     {
/*  4495 */       super(paramInt, paramBufferCapabilities);
/*       */     }
/*       */     
/*       */     public void show(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  4499 */       showSubRegion(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */     }
/*       */     
/*       */     public boolean showIfNotLost(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */     {
/*  4504 */       if (!contentsLost()) {
/*  4505 */         showSubRegion(paramInt1, paramInt2, paramInt3, paramInt4);
/*  4506 */         return !contentsLost();
/*       */       }
/*  4508 */       return false;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private class BltSubRegionBufferStrategy
/*       */     extends Component.BltBufferStrategy
/*       */     implements SubRegionShowable
/*       */   {
/*       */     protected BltSubRegionBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*       */     {
/*  4525 */       super(paramInt, paramBufferCapabilities);
/*       */     }
/*       */     
/*       */     public void show(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  4529 */       showSubRegion(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */     }
/*       */     
/*       */     public boolean showIfNotLost(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */     {
/*  4534 */       if (!contentsLost()) {
/*  4535 */         showSubRegion(paramInt1, paramInt2, paramInt3, paramInt4);
/*  4536 */         return !contentsLost();
/*       */       }
/*  4538 */       return false;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private class SingleBufferStrategy
/*       */     extends BufferStrategy
/*       */   {
/*       */     private BufferCapabilities caps;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public SingleBufferStrategy(BufferCapabilities paramBufferCapabilities)
/*       */     {
/*  4556 */       this.caps = paramBufferCapabilities;
/*       */     }
/*       */     
/*  4559 */     public BufferCapabilities getCapabilities() { return this.caps; }
/*       */     
/*       */     public Graphics getDrawGraphics() {
/*  4562 */       return Component.this.getGraphics();
/*       */     }
/*       */     
/*  4565 */     public boolean contentsLost() { return false; }
/*       */     
/*       */     public boolean contentsRestored() {
/*  4568 */       return false;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void show() {}
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setIgnoreRepaint(boolean paramBoolean)
/*       */   {
/*  4593 */     this.ignoreRepaint = paramBoolean;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean getIgnoreRepaint()
/*       */   {
/*  4604 */     return this.ignoreRepaint;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean contains(int paramInt1, int paramInt2)
/*       */   {
/*  4617 */     return inside(paramInt1, paramInt2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean inside(int paramInt1, int paramInt2)
/*       */   {
/*  4626 */     return (paramInt1 >= 0) && (paramInt1 < this.width) && (paramInt2 >= 0) && (paramInt2 < this.height);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean contains(Point paramPoint)
/*       */   {
/*  4639 */     return contains(paramPoint.x, paramPoint.y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Component getComponentAt(int paramInt1, int paramInt2)
/*       */   {
/*  4664 */     return locate(paramInt1, paramInt2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public Component locate(int paramInt1, int paramInt2)
/*       */   {
/*  4673 */     return contains(paramInt1, paramInt2) ? this : null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Component getComponentAt(Point paramPoint)
/*       */   {
/*  4684 */     return getComponentAt(paramPoint.x, paramPoint.y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void deliverEvent(Event paramEvent)
/*       */   {
/*  4693 */     postEvent(paramEvent);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final void dispatchEvent(AWTEvent paramAWTEvent)
/*       */   {
/*  4703 */     dispatchEventImpl(paramAWTEvent);
/*       */   }
/*       */   
/*       */   void dispatchEventImpl(AWTEvent paramAWTEvent)
/*       */   {
/*  4708 */     int i = paramAWTEvent.getID();
/*       */     
/*       */ 
/*  4711 */     AppContext localAppContext = this.appContext;
/*  4712 */     if ((localAppContext != null) && (!localAppContext.equals(AppContext.getAppContext())) && 
/*  4713 */       (eventLog.isLoggable(PlatformLogger.Level.FINE))) {
/*  4714 */       eventLog.fine("Event " + paramAWTEvent + " is being dispatched on the wrong AppContext");
/*       */     }
/*       */     
/*       */ 
/*  4718 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4719 */       eventLog.finest("{0}", new Object[] { paramAWTEvent });
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4725 */     if (!(paramAWTEvent instanceof KeyEvent))
/*       */     {
/*  4727 */       EventQueue.setCurrentEventAndMostRecentTime(paramAWTEvent);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4735 */     if ((paramAWTEvent instanceof SunDropTargetEvent)) {
/*  4736 */       ((SunDropTargetEvent)paramAWTEvent).dispatch();
/*  4737 */       return;
/*       */     }
/*       */     
/*  4740 */     if (!paramAWTEvent.focusManagerIsDispatching)
/*       */     {
/*       */ 
/*  4743 */       if (paramAWTEvent.isPosted) {
/*  4744 */         paramAWTEvent = KeyboardFocusManager.retargetFocusEvent(paramAWTEvent);
/*  4745 */         paramAWTEvent.isPosted = true;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4752 */       if (KeyboardFocusManager.getCurrentKeyboardFocusManager().dispatchEvent(paramAWTEvent))
/*       */       {
/*  4754 */         return;
/*       */       }
/*       */     }
/*  4757 */     if (((paramAWTEvent instanceof FocusEvent)) && (focusLog.isLoggable(PlatformLogger.Level.FINEST))) {
/*  4758 */       focusLog.finest("" + paramAWTEvent);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4765 */     if ((i == 507) && 
/*  4766 */       (!eventTypeEnabled(i)) && (this.peer != null) && 
/*  4767 */       (!this.peer.handlesWheelScrolling()) && 
/*  4768 */       (dispatchMouseWheelToAncestor((MouseWheelEvent)paramAWTEvent)))
/*       */     {
/*  4770 */       return;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4776 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  4777 */     localToolkit.notifyAWTEventListeners(paramAWTEvent);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4784 */     if ((!paramAWTEvent.isConsumed()) && 
/*  4785 */       ((paramAWTEvent instanceof KeyEvent)))
/*       */     {
/*  4787 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().processKeyEvent(this, (KeyEvent)paramAWTEvent);
/*  4788 */       if (paramAWTEvent.isConsumed()) {
/*       */         return;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */     Object localObject;
/*       */     
/*       */ 
/*  4797 */     if (areInputMethodsEnabled())
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  4802 */       if ((((paramAWTEvent instanceof InputMethodEvent)) && (!(this instanceof CompositionArea))) || ((paramAWTEvent instanceof InputEvent)) || ((paramAWTEvent instanceof FocusEvent)))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4809 */         localObject = getInputContext();
/*       */         
/*       */ 
/*  4812 */         if (localObject != null) {
/*  4813 */           ((java.awt.im.InputContext)localObject).dispatchEvent(paramAWTEvent);
/*  4814 */           if (paramAWTEvent.isConsumed()) {
/*  4815 */             if (((paramAWTEvent instanceof FocusEvent)) && (focusLog.isLoggable(PlatformLogger.Level.FINEST))) {
/*  4816 */               focusLog.finest("3579: Skipping " + paramAWTEvent);
/*       */             }
/*  4818 */             return;
/*       */           }
/*       */           
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */     }
/*  4826 */     else if (i == 1004) {
/*  4827 */       localObject = getInputContext();
/*  4828 */       if ((localObject != null) && ((localObject instanceof sun.awt.im.InputContext))) {
/*  4829 */         ((sun.awt.im.InputContext)localObject).disableNativeIM();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4838 */     switch (i)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     case 401: 
/*       */     case 402: 
/*  4846 */       localObject = (Container)((this instanceof Container) ? this : this.parent);
/*  4847 */       if (localObject != null) {
/*  4848 */         ((Container)localObject).preProcessKeyEvent((KeyEvent)paramAWTEvent);
/*  4849 */         if (paramAWTEvent.isConsumed()) {
/*  4850 */           if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4851 */             focusLog.finest("Pre-process consumed event");
/*       */           }
/*       */           
/*       */           return;
/*       */         }
/*       */       }
/*       */       break;
/*       */     case 201: 
/*  4859 */       if ((localToolkit instanceof WindowClosingListener))
/*       */       {
/*  4861 */         this.windowClosingException = ((WindowClosingListener)localToolkit).windowClosingNotify((WindowEvent)paramAWTEvent);
/*  4862 */         if (checkWindowClosingException()) {
/*       */           return;
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */       break;
/*       */     }
/*       */     
/*       */     
/*       */ 
/*       */ 
/*  4875 */     if (this.newEventsOnly)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  4880 */       if (eventEnabled(paramAWTEvent)) {
/*  4881 */         processEvent(paramAWTEvent);
/*       */       }
/*  4883 */     } else if (i == 507)
/*       */     {
/*       */ 
/*       */ 
/*  4887 */       autoProcessMouseWheel((MouseWheelEvent)paramAWTEvent);
/*  4888 */     } else if ((!(paramAWTEvent instanceof MouseEvent)) || (postsOldMouseEvents()))
/*       */     {
/*       */ 
/*       */ 
/*  4892 */       localObject = paramAWTEvent.convertToOld();
/*  4893 */       if (localObject != null) {
/*  4894 */         int j = ((Event)localObject).key;
/*  4895 */         int k = ((Event)localObject).modifiers;
/*       */         
/*  4897 */         postEvent((Event)localObject);
/*  4898 */         if (((Event)localObject).isConsumed()) {
/*  4899 */           paramAWTEvent.consume();
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  4904 */         switch (((Event)localObject).id) {
/*       */         case 401: 
/*       */         case 402: 
/*       */         case 403: 
/*       */         case 404: 
/*  4909 */           if (((Event)localObject).key != j) {
/*  4910 */             ((KeyEvent)paramAWTEvent).setKeyChar(((Event)localObject).getKeyEventChar());
/*       */           }
/*  4912 */           if (((Event)localObject).modifiers != k) {
/*  4913 */             ((KeyEvent)paramAWTEvent).setModifiers(((Event)localObject).modifiers);
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*       */           break;
/*       */         }
/*       */         
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  4926 */     if ((i == 201) && (!paramAWTEvent.isConsumed()) && 
/*  4927 */       ((localToolkit instanceof WindowClosingListener)))
/*       */     {
/*       */ 
/*  4930 */       this.windowClosingException = ((WindowClosingListener)localToolkit).windowClosingDelivered((WindowEvent)paramAWTEvent);
/*  4931 */       if (checkWindowClosingException()) {
/*  4932 */         return;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4943 */     if (!(paramAWTEvent instanceof KeyEvent)) {
/*  4944 */       localObject = this.peer;
/*  4945 */       if (((paramAWTEvent instanceof FocusEvent)) && ((localObject == null) || ((localObject instanceof LightweightPeer))))
/*       */       {
/*       */ 
/*  4948 */         Component localComponent = (Component)paramAWTEvent.getSource();
/*  4949 */         if (localComponent != null) {
/*  4950 */           Container localContainer = localComponent.getNativeContainer();
/*  4951 */           if (localContainer != null) {
/*  4952 */             localObject = localContainer.getPeer();
/*       */           }
/*       */         }
/*       */       }
/*  4956 */       if (localObject != null) {
/*  4957 */         ((ComponentPeer)localObject).handleEvent(paramAWTEvent);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   boolean dispatchMouseWheelToAncestor(MouseWheelEvent paramMouseWheelEvent)
/*       */   {
/*  4977 */     int i = paramMouseWheelEvent.getX() + getX();
/*  4978 */     int j = paramMouseWheelEvent.getY() + getY();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4983 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4984 */       eventLog.finest("dispatchMouseWheelToAncestor");
/*  4985 */       eventLog.finest("orig event src is of " + paramMouseWheelEvent.getSource().getClass());
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4991 */     synchronized (getTreeLock()) {
/*  4992 */       Container localContainer = getParent();
/*  4993 */       while ((localContainer != null) && (!localContainer.eventEnabled(paramMouseWheelEvent)))
/*       */       {
/*  4995 */         i += localContainer.getX();
/*  4996 */         j += localContainer.getY();
/*       */         
/*  4998 */         if ((localContainer instanceof Window)) break;
/*  4999 */         localContainer = localContainer.getParent();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5006 */       if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  5007 */         eventLog.finest("new event src is " + localContainer.getClass());
/*       */       }
/*       */       
/*  5010 */       if ((localContainer != null) && (localContainer.eventEnabled(paramMouseWheelEvent)))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5027 */         MouseWheelEvent localMouseWheelEvent = new MouseWheelEvent(localContainer, paramMouseWheelEvent.getID(), paramMouseWheelEvent.getWhen(), paramMouseWheelEvent.getModifiers(), i, j, paramMouseWheelEvent.getXOnScreen(), paramMouseWheelEvent.getYOnScreen(), paramMouseWheelEvent.getClickCount(), paramMouseWheelEvent.isPopupTrigger(), paramMouseWheelEvent.getScrollType(), paramMouseWheelEvent.getScrollAmount(), paramMouseWheelEvent.getWheelRotation(), paramMouseWheelEvent.getPreciseWheelRotation());
/*  5028 */         paramMouseWheelEvent.copyPrivateDataInto(localMouseWheelEvent);
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5034 */         localContainer.dispatchEventToSelf(localMouseWheelEvent);
/*  5035 */         if (localMouseWheelEvent.isConsumed()) {
/*  5036 */           paramMouseWheelEvent.consume();
/*       */         }
/*  5038 */         return true;
/*       */       }
/*       */     }
/*  5041 */     return false;
/*       */   }
/*       */   
/*       */   boolean checkWindowClosingException() {
/*  5045 */     if (this.windowClosingException != null) {
/*  5046 */       if ((this instanceof Dialog)) {
/*  5047 */         ((Dialog)this).interruptBlocking();
/*       */       } else {
/*  5049 */         this.windowClosingException.fillInStackTrace();
/*  5050 */         this.windowClosingException.printStackTrace();
/*  5051 */         this.windowClosingException = null;
/*       */       }
/*  5053 */       return true;
/*       */     }
/*  5055 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   boolean areInputMethodsEnabled()
/*       */   {
/*  5062 */     return ((this.eventMask & 0x1000) != 0L) && (((this.eventMask & 0x8) != 0L) || (this.keyListener != null));
/*       */   }
/*       */   
/*       */ 
/*       */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*       */   {
/*  5068 */     return eventTypeEnabled(paramAWTEvent.id);
/*       */   }
/*       */   
/*       */   boolean eventTypeEnabled(int paramInt) {
/*  5072 */     switch (paramInt) {
/*       */     case 100: 
/*       */     case 101: 
/*       */     case 102: 
/*       */     case 103: 
/*  5077 */       if (((this.eventMask & 1L) != 0L) || (this.componentListener != null))
/*       */       {
/*  5079 */         return true;
/*       */       }
/*       */       break;
/*       */     case 1004: 
/*       */     case 1005: 
/*  5084 */       if (((this.eventMask & 0x4) != 0L) || (this.focusListener != null))
/*       */       {
/*  5086 */         return true;
/*       */       }
/*       */       break;
/*       */     case 400: 
/*       */     case 401: 
/*       */     case 402: 
/*  5092 */       if (((this.eventMask & 0x8) != 0L) || (this.keyListener != null))
/*       */       {
/*  5094 */         return true;
/*       */       }
/*       */       break;
/*       */     case 500: 
/*       */     case 501: 
/*       */     case 502: 
/*       */     case 504: 
/*       */     case 505: 
/*  5102 */       if (((this.eventMask & 0x10) != 0L) || (this.mouseListener != null))
/*       */       {
/*  5104 */         return true;
/*       */       }
/*       */       break;
/*       */     case 503: 
/*       */     case 506: 
/*  5109 */       if (((this.eventMask & 0x20) != 0L) || (this.mouseMotionListener != null))
/*       */       {
/*  5111 */         return true;
/*       */       }
/*       */       break;
/*       */     case 507: 
/*  5115 */       if (((this.eventMask & 0x20000) != 0L) || (this.mouseWheelListener != null))
/*       */       {
/*  5117 */         return true;
/*       */       }
/*       */       break;
/*       */     case 1100: 
/*       */     case 1101: 
/*  5122 */       if (((this.eventMask & 0x800) != 0L) || (this.inputMethodListener != null))
/*       */       {
/*  5124 */         return true;
/*       */       }
/*       */       break;
/*       */     case 1400: 
/*  5128 */       if (((this.eventMask & 0x8000) != 0L) || (this.hierarchyListener != null))
/*       */       {
/*  5130 */         return true;
/*       */       }
/*       */       break;
/*       */     case 1401: 
/*       */     case 1402: 
/*  5135 */       if (((this.eventMask & 0x10000) != 0L) || (this.hierarchyBoundsListener != null))
/*       */       {
/*  5137 */         return true;
/*       */       }
/*       */       break;
/*       */     case 1001: 
/*  5141 */       if ((this.eventMask & 0x80) != 0L) {
/*  5142 */         return true;
/*       */       }
/*       */       break;
/*       */     case 900: 
/*  5146 */       if ((this.eventMask & 0x400) != 0L) {
/*  5147 */         return true;
/*       */       }
/*       */       break;
/*       */     case 701: 
/*  5151 */       if ((this.eventMask & 0x200) != 0L) {
/*  5152 */         return true;
/*       */       }
/*       */       break;
/*       */     case 601: 
/*  5156 */       if ((this.eventMask & 0x100) != 0L) {
/*  5157 */         return true;
/*       */       }
/*       */       
/*       */ 
/*       */       break;
/*       */     }
/*       */     
/*       */     
/*       */ 
/*  5166 */     if (paramInt > 1999) {
/*  5167 */       return true;
/*       */     }
/*  5169 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean postEvent(Event paramEvent)
/*       */   {
/*  5178 */     ComponentPeer localComponentPeer = this.peer;
/*       */     
/*  5180 */     if (handleEvent(paramEvent)) {
/*  5181 */       paramEvent.consume();
/*  5182 */       return true;
/*       */     }
/*       */     
/*  5185 */     Container localContainer = this.parent;
/*  5186 */     int i = paramEvent.x;
/*  5187 */     int j = paramEvent.y;
/*  5188 */     if (localContainer != null) {
/*  5189 */       paramEvent.translate(this.x, this.y);
/*  5190 */       if (localContainer.postEvent(paramEvent)) {
/*  5191 */         paramEvent.consume();
/*  5192 */         return true;
/*       */       }
/*       */       
/*  5195 */       paramEvent.x = i;
/*  5196 */       paramEvent.y = j;
/*       */     }
/*  5198 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addComponentListener(ComponentListener paramComponentListener)
/*       */   {
/*  5219 */     if (paramComponentListener == null) {
/*  5220 */       return;
/*       */     }
/*  5222 */     this.componentListener = AWTEventMulticaster.add(this.componentListener, paramComponentListener);
/*  5223 */     this.newEventsOnly = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeComponentListener(ComponentListener paramComponentListener)
/*       */   {
/*  5243 */     if (paramComponentListener == null) {
/*  5244 */       return;
/*       */     }
/*  5246 */     this.componentListener = AWTEventMulticaster.remove(this.componentListener, paramComponentListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized ComponentListener[] getComponentListeners()
/*       */   {
/*  5262 */     return (ComponentListener[])getListeners(ComponentListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addFocusListener(FocusListener paramFocusListener)
/*       */   {
/*  5281 */     if (paramFocusListener == null) {
/*  5282 */       return;
/*       */     }
/*  5284 */     this.focusListener = AWTEventMulticaster.add(this.focusListener, paramFocusListener);
/*  5285 */     this.newEventsOnly = true;
/*       */     
/*       */ 
/*       */ 
/*  5289 */     if ((this.peer instanceof LightweightPeer)) {
/*  5290 */       this.parent.proxyEnableEvents(4L);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeFocusListener(FocusListener paramFocusListener)
/*       */   {
/*  5312 */     if (paramFocusListener == null) {
/*  5313 */       return;
/*       */     }
/*  5315 */     this.focusListener = AWTEventMulticaster.remove(this.focusListener, paramFocusListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized FocusListener[] getFocusListeners()
/*       */   {
/*  5331 */     return (FocusListener[])getListeners(FocusListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addHierarchyListener(HierarchyListener paramHierarchyListener)
/*       */   {
/*  5351 */     if (paramHierarchyListener == null) {
/*       */       return;
/*       */     }
/*       */     int i;
/*  5355 */     synchronized (this) {
/*  5356 */       i = (this.hierarchyListener == null) && ((this.eventMask & 0x8000) == 0L) ? 1 : 0;
/*       */       
/*       */ 
/*  5359 */       this.hierarchyListener = AWTEventMulticaster.add(this.hierarchyListener, paramHierarchyListener);
/*  5360 */       i = (i != 0) && (this.hierarchyListener != null) ? 1 : 0;
/*  5361 */       this.newEventsOnly = true;
/*       */     }
/*  5363 */     if (i != 0) {
/*  5364 */       synchronized (getTreeLock()) {
/*  5365 */         adjustListeningChildrenOnParent(32768L, 1);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removeHierarchyListener(HierarchyListener paramHierarchyListener)
/*       */   {
/*  5389 */     if (paramHierarchyListener == null) {
/*       */       return;
/*       */     }
/*       */     int i;
/*  5393 */     synchronized (this) {
/*  5394 */       i = (this.hierarchyListener != null) && ((this.eventMask & 0x8000) == 0L) ? 1 : 0;
/*       */       
/*       */ 
/*       */ 
/*  5398 */       this.hierarchyListener = AWTEventMulticaster.remove(this.hierarchyListener, paramHierarchyListener);
/*  5399 */       i = (i != 0) && (this.hierarchyListener == null) ? 1 : 0;
/*       */     }
/*  5401 */     if (i != 0) {
/*  5402 */       synchronized (getTreeLock()) {
/*  5403 */         adjustListeningChildrenOnParent(32768L, -1);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized HierarchyListener[] getHierarchyListeners()
/*       */   {
/*  5422 */     return (HierarchyListener[])getListeners(HierarchyListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addHierarchyBoundsListener(HierarchyBoundsListener paramHierarchyBoundsListener)
/*       */   {
/*  5442 */     if (paramHierarchyBoundsListener == null) {
/*       */       return;
/*       */     }
/*       */     int i;
/*  5446 */     synchronized (this) {
/*  5447 */       i = (this.hierarchyBoundsListener == null) && ((this.eventMask & 0x10000) == 0L) ? 1 : 0;
/*       */       
/*       */ 
/*       */ 
/*  5451 */       this.hierarchyBoundsListener = AWTEventMulticaster.add(this.hierarchyBoundsListener, paramHierarchyBoundsListener);
/*  5452 */       i = (i != 0) && (this.hierarchyBoundsListener != null) ? 1 : 0;
/*       */       
/*  5454 */       this.newEventsOnly = true;
/*       */     }
/*  5456 */     if (i != 0) {
/*  5457 */       synchronized (getTreeLock()) {
/*  5458 */         adjustListeningChildrenOnParent(65536L, 1);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removeHierarchyBoundsListener(HierarchyBoundsListener paramHierarchyBoundsListener)
/*       */   {
/*  5482 */     if (paramHierarchyBoundsListener == null) {
/*       */       return;
/*       */     }
/*       */     int i;
/*  5486 */     synchronized (this) {
/*  5487 */       i = (this.hierarchyBoundsListener != null) && ((this.eventMask & 0x10000) == 0L) ? 1 : 0;
/*       */       
/*       */ 
/*       */ 
/*  5491 */       this.hierarchyBoundsListener = AWTEventMulticaster.remove(this.hierarchyBoundsListener, paramHierarchyBoundsListener);
/*  5492 */       i = (i != 0) && (this.hierarchyBoundsListener == null) ? 1 : 0;
/*       */     }
/*       */     
/*  5495 */     if (i != 0) {
/*  5496 */       synchronized (getTreeLock()) {
/*  5497 */         adjustListeningChildrenOnParent(65536L, -1);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   int numListening(long paramLong)
/*       */   {
/*  5506 */     if ((eventLog.isLoggable(PlatformLogger.Level.FINE)) && 
/*  5507 */       (paramLong != 32768L) && (paramLong != 65536L))
/*       */     {
/*       */ 
/*  5510 */       eventLog.fine("Assertion failed");
/*       */     }
/*       */     
/*  5513 */     if (((paramLong == 32768L) && ((this.hierarchyListener != null) || ((this.eventMask & 0x8000) != 0L))) || ((paramLong == 65536L) && ((this.hierarchyBoundsListener != null) || ((this.eventMask & 0x10000) != 0L))))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5519 */       return 1;
/*       */     }
/*  5521 */     return 0;
/*       */   }
/*       */   
/*       */ 
/*       */   int countHierarchyMembers()
/*       */   {
/*  5527 */     return 1;
/*       */   }
/*       */   
/*       */   int createHierarchyEvents(int paramInt, Component paramComponent, Container paramContainer, long paramLong, boolean paramBoolean)
/*       */   {
/*       */     HierarchyEvent localHierarchyEvent;
/*  5533 */     switch (paramInt) {
/*       */     case 1400: 
/*  5535 */       if ((this.hierarchyListener != null) || ((this.eventMask & 0x8000) != 0L) || (paramBoolean))
/*       */       {
/*       */ 
/*  5538 */         localHierarchyEvent = new HierarchyEvent(this, paramInt, paramComponent, paramContainer, paramLong);
/*       */         
/*       */ 
/*  5541 */         dispatchEvent(localHierarchyEvent);
/*  5542 */         return 1;
/*       */       }
/*       */       break;
/*       */     case 1401: 
/*       */     case 1402: 
/*  5547 */       if ((eventLog.isLoggable(PlatformLogger.Level.FINE)) && 
/*  5548 */         (paramLong != 0L)) {
/*  5549 */         eventLog.fine("Assertion (changeFlags == 0) failed");
/*       */       }
/*       */       
/*  5552 */       if ((this.hierarchyBoundsListener != null) || ((this.eventMask & 0x10000) != 0L) || (paramBoolean))
/*       */       {
/*       */ 
/*  5555 */         localHierarchyEvent = new HierarchyEvent(this, paramInt, paramComponent, paramContainer);
/*       */         
/*  5557 */         dispatchEvent(localHierarchyEvent);
/*  5558 */         return 1;
/*       */       }
/*       */       
/*       */       break;
/*       */     default: 
/*  5563 */       if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  5564 */         eventLog.fine("This code must never be reached");
/*       */       }
/*       */       break;
/*       */     }
/*  5568 */     return 0;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized HierarchyBoundsListener[] getHierarchyBoundsListeners()
/*       */   {
/*  5584 */     return (HierarchyBoundsListener[])getListeners(HierarchyBoundsListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   void adjustListeningChildrenOnParent(long paramLong, int paramInt)
/*       */   {
/*  5593 */     if (this.parent != null) {
/*  5594 */       this.parent.adjustListeningChildren(paramLong, paramInt);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addKeyListener(KeyListener paramKeyListener)
/*       */   {
/*  5613 */     if (paramKeyListener == null) {
/*  5614 */       return;
/*       */     }
/*  5616 */     this.keyListener = AWTEventMulticaster.add(this.keyListener, paramKeyListener);
/*  5617 */     this.newEventsOnly = true;
/*       */     
/*       */ 
/*       */ 
/*  5621 */     if ((this.peer instanceof LightweightPeer)) {
/*  5622 */       this.parent.proxyEnableEvents(8L);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeKeyListener(KeyListener paramKeyListener)
/*       */   {
/*  5644 */     if (paramKeyListener == null) {
/*  5645 */       return;
/*       */     }
/*  5647 */     this.keyListener = AWTEventMulticaster.remove(this.keyListener, paramKeyListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized KeyListener[] getKeyListeners()
/*       */   {
/*  5663 */     return (KeyListener[])getListeners(KeyListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addMouseListener(MouseListener paramMouseListener)
/*       */   {
/*  5682 */     if (paramMouseListener == null) {
/*  5683 */       return;
/*       */     }
/*  5685 */     this.mouseListener = AWTEventMulticaster.add(this.mouseListener, paramMouseListener);
/*  5686 */     this.newEventsOnly = true;
/*       */     
/*       */ 
/*       */ 
/*  5690 */     if ((this.peer instanceof LightweightPeer)) {
/*  5691 */       this.parent.proxyEnableEvents(16L);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeMouseListener(MouseListener paramMouseListener)
/*       */   {
/*  5713 */     if (paramMouseListener == null) {
/*  5714 */       return;
/*       */     }
/*  5716 */     this.mouseListener = AWTEventMulticaster.remove(this.mouseListener, paramMouseListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized MouseListener[] getMouseListeners()
/*       */   {
/*  5732 */     return (MouseListener[])getListeners(MouseListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addMouseMotionListener(MouseMotionListener paramMouseMotionListener)
/*       */   {
/*  5751 */     if (paramMouseMotionListener == null) {
/*  5752 */       return;
/*       */     }
/*  5754 */     this.mouseMotionListener = AWTEventMulticaster.add(this.mouseMotionListener, paramMouseMotionListener);
/*  5755 */     this.newEventsOnly = true;
/*       */     
/*       */ 
/*       */ 
/*  5759 */     if ((this.peer instanceof LightweightPeer)) {
/*  5760 */       this.parent.proxyEnableEvents(32L);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeMouseMotionListener(MouseMotionListener paramMouseMotionListener)
/*       */   {
/*  5782 */     if (paramMouseMotionListener == null) {
/*  5783 */       return;
/*       */     }
/*  5785 */     this.mouseMotionListener = AWTEventMulticaster.remove(this.mouseMotionListener, paramMouseMotionListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized MouseMotionListener[] getMouseMotionListeners()
/*       */   {
/*  5801 */     return (MouseMotionListener[])getListeners(MouseMotionListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addMouseWheelListener(MouseWheelListener paramMouseWheelListener)
/*       */   {
/*  5825 */     if (paramMouseWheelListener == null) {
/*  5826 */       return;
/*       */     }
/*  5828 */     this.mouseWheelListener = AWTEventMulticaster.add(this.mouseWheelListener, paramMouseWheelListener);
/*  5829 */     this.newEventsOnly = true;
/*       */     
/*       */ 
/*       */ 
/*  5833 */     if ((this.peer instanceof LightweightPeer)) {
/*  5834 */       this.parent.proxyEnableEvents(131072L);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeMouseWheelListener(MouseWheelListener paramMouseWheelListener)
/*       */   {
/*  5855 */     if (paramMouseWheelListener == null) {
/*  5856 */       return;
/*       */     }
/*  5858 */     this.mouseWheelListener = AWTEventMulticaster.remove(this.mouseWheelListener, paramMouseWheelListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized MouseWheelListener[] getMouseWheelListeners()
/*       */   {
/*  5874 */     return (MouseWheelListener[])getListeners(MouseWheelListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void addInputMethodListener(InputMethodListener paramInputMethodListener)
/*       */   {
/*  5897 */     if (paramInputMethodListener == null) {
/*  5898 */       return;
/*       */     }
/*  5900 */     this.inputMethodListener = AWTEventMulticaster.add(this.inputMethodListener, paramInputMethodListener);
/*  5901 */     this.newEventsOnly = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void removeInputMethodListener(InputMethodListener paramInputMethodListener)
/*       */   {
/*  5922 */     if (paramInputMethodListener == null) {
/*  5923 */       return;
/*       */     }
/*  5925 */     this.inputMethodListener = AWTEventMulticaster.remove(this.inputMethodListener, paramInputMethodListener);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized InputMethodListener[] getInputMethodListeners()
/*       */   {
/*  5941 */     return (InputMethodListener[])getListeners(InputMethodListener.class);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*       */   {
/*  5988 */     Object localObject = null;
/*  5989 */     if (paramClass == ComponentListener.class) {
/*  5990 */       localObject = this.componentListener;
/*  5991 */     } else if (paramClass == FocusListener.class) {
/*  5992 */       localObject = this.focusListener;
/*  5993 */     } else if (paramClass == HierarchyListener.class) {
/*  5994 */       localObject = this.hierarchyListener;
/*  5995 */     } else if (paramClass == HierarchyBoundsListener.class) {
/*  5996 */       localObject = this.hierarchyBoundsListener;
/*  5997 */     } else if (paramClass == KeyListener.class) {
/*  5998 */       localObject = this.keyListener;
/*  5999 */     } else if (paramClass == MouseListener.class) {
/*  6000 */       localObject = this.mouseListener;
/*  6001 */     } else if (paramClass == MouseMotionListener.class) {
/*  6002 */       localObject = this.mouseMotionListener;
/*  6003 */     } else if (paramClass == MouseWheelListener.class) {
/*  6004 */       localObject = this.mouseWheelListener;
/*  6005 */     } else if (paramClass == InputMethodListener.class) {
/*  6006 */       localObject = this.inputMethodListener;
/*  6007 */     } else if (paramClass == PropertyChangeListener.class) {
/*  6008 */       return (EventListener[])getPropertyChangeListeners();
/*       */     }
/*  6010 */     return AWTEventMulticaster.getListeners((EventListener)localObject, paramClass);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public InputMethodRequests getInputMethodRequests()
/*       */   {
/*  6026 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public java.awt.im.InputContext getInputContext()
/*       */   {
/*  6041 */     Container localContainer = this.parent;
/*  6042 */     if (localContainer == null) {
/*  6043 */       return null;
/*       */     }
/*  6045 */     return localContainer.getInputContext();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected final void enableEvents(long paramLong)
/*       */   {
/*  6067 */     long l = 0L;
/*  6068 */     synchronized (this) {
/*  6069 */       if (((paramLong & 0x8000) != 0L) && (this.hierarchyListener == null) && ((this.eventMask & 0x8000) == 0L))
/*       */       {
/*       */ 
/*  6072 */         l |= 0x8000;
/*       */       }
/*  6074 */       if (((paramLong & 0x10000) != 0L) && (this.hierarchyBoundsListener == null) && ((this.eventMask & 0x10000) == 0L))
/*       */       {
/*       */ 
/*  6077 */         l |= 0x10000;
/*       */       }
/*  6079 */       this.eventMask |= paramLong;
/*  6080 */       this.newEventsOnly = true;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  6085 */     if ((this.peer instanceof LightweightPeer)) {
/*  6086 */       this.parent.proxyEnableEvents(this.eventMask);
/*       */     }
/*  6088 */     if (l != 0L) {
/*  6089 */       synchronized (getTreeLock()) {
/*  6090 */         adjustListeningChildrenOnParent(l, 1);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected final void disableEvents(long paramLong)
/*       */   {
/*  6103 */     long l = 0L;
/*  6104 */     synchronized (this) {
/*  6105 */       if (((paramLong & 0x8000) != 0L) && (this.hierarchyListener == null) && ((this.eventMask & 0x8000) != 0L))
/*       */       {
/*       */ 
/*  6108 */         l |= 0x8000;
/*       */       }
/*  6110 */       if (((paramLong & 0x10000) != 0L) && (this.hierarchyBoundsListener == null) && ((this.eventMask & 0x10000) != 0L))
/*       */       {
/*       */ 
/*  6113 */         l |= 0x10000;
/*       */       }
/*  6115 */       this.eventMask &= (paramLong ^ 0xFFFFFFFFFFFFFFFF);
/*       */     }
/*  6117 */     if (l != 0L) {
/*  6118 */       synchronized (getTreeLock()) {
/*  6119 */         adjustListeningChildrenOnParent(l, -1);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6130 */   private transient boolean coalescingEnabled = checkCoalescing();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6137 */   private static final Map<Class<?>, Boolean> coalesceMap = new WeakHashMap();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private boolean checkCoalescing()
/*       */   {
/*  6149 */     if (getClass().getClassLoader() == null) {
/*  6150 */       return false;
/*       */     }
/*  6152 */     final Class localClass = getClass();
/*  6153 */     synchronized (coalesceMap)
/*       */     {
/*  6155 */       Boolean localBoolean1 = (Boolean)coalesceMap.get(localClass);
/*  6156 */       if (localBoolean1 != null) {
/*  6157 */         return localBoolean1.booleanValue();
/*       */       }
/*       */       
/*       */ 
/*  6161 */       Boolean localBoolean2 = (Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*       */       {
/*       */         public Boolean run() {
/*  6164 */           return Boolean.valueOf(Component.isCoalesceEventsOverriden(localClass));
/*       */         }
/*       */         
/*  6167 */       });
/*  6168 */       coalesceMap.put(localClass, localBoolean2);
/*  6169 */       return localBoolean2.booleanValue();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*  6176 */   private static final Class[] coalesceEventsParams = { AWTEvent.class, AWTEvent.class };
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static boolean isCoalesceEventsOverriden(Class<?> paramClass)
/*       */   {
/*  6186 */     assert (Thread.holdsLock(coalesceMap));
/*       */     
/*       */ 
/*  6189 */     Class localClass = paramClass.getSuperclass();
/*  6190 */     if (localClass == null)
/*       */     {
/*       */ 
/*  6193 */       return false;
/*       */     }
/*  6195 */     if (localClass.getClassLoader() != null) {
/*  6196 */       Boolean localBoolean = (Boolean)coalesceMap.get(localClass);
/*  6197 */       if (localBoolean == null)
/*       */       {
/*  6199 */         if (isCoalesceEventsOverriden(localClass)) {
/*  6200 */           coalesceMap.put(localClass, Boolean.valueOf(true));
/*  6201 */           return true;
/*       */         }
/*  6203 */       } else if (localBoolean.booleanValue()) {
/*  6204 */         return true;
/*       */       }
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*  6210 */       paramClass.getDeclaredMethod("coalesceEvents", coalesceEventsParams);
/*       */       
/*       */ 
/*  6213 */       return true;
/*       */     }
/*       */     catch (NoSuchMethodException localNoSuchMethodException) {}
/*  6216 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final boolean isCoalescingEnabled()
/*       */   {
/*  6224 */     return this.coalescingEnabled;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected AWTEvent coalesceEvents(AWTEvent paramAWTEvent1, AWTEvent paramAWTEvent2)
/*       */   {
/*  6256 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processEvent(AWTEvent paramAWTEvent)
/*       */   {
/*  6280 */     if ((paramAWTEvent instanceof FocusEvent)) {
/*  6281 */       processFocusEvent((FocusEvent)paramAWTEvent);
/*       */     }
/*  6283 */     else if ((paramAWTEvent instanceof MouseEvent)) {
/*  6284 */       switch (paramAWTEvent.getID()) {
/*       */       case 500: 
/*       */       case 501: 
/*       */       case 502: 
/*       */       case 504: 
/*       */       case 505: 
/*  6290 */         processMouseEvent((MouseEvent)paramAWTEvent);
/*  6291 */         break;
/*       */       case 503: 
/*       */       case 506: 
/*  6294 */         processMouseMotionEvent((MouseEvent)paramAWTEvent);
/*  6295 */         break;
/*       */       case 507: 
/*  6297 */         processMouseWheelEvent((MouseWheelEvent)paramAWTEvent);
/*       */       
/*       */       }
/*       */       
/*  6301 */     } else if ((paramAWTEvent instanceof KeyEvent)) {
/*  6302 */       processKeyEvent((KeyEvent)paramAWTEvent);
/*       */     }
/*  6304 */     else if ((paramAWTEvent instanceof ComponentEvent)) {
/*  6305 */       processComponentEvent((ComponentEvent)paramAWTEvent);
/*  6306 */     } else if ((paramAWTEvent instanceof InputMethodEvent)) {
/*  6307 */       processInputMethodEvent((InputMethodEvent)paramAWTEvent);
/*  6308 */     } else if ((paramAWTEvent instanceof HierarchyEvent)) {
/*  6309 */       switch (paramAWTEvent.getID()) {
/*       */       case 1400: 
/*  6311 */         processHierarchyEvent((HierarchyEvent)paramAWTEvent);
/*  6312 */         break;
/*       */       case 1401: 
/*       */       case 1402: 
/*  6315 */         processHierarchyBoundsEvent((HierarchyEvent)paramAWTEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processComponentEvent(ComponentEvent paramComponentEvent)
/*       */   {
/*  6346 */     ComponentListener localComponentListener = this.componentListener;
/*  6347 */     if (localComponentListener != null) {
/*  6348 */       int i = paramComponentEvent.getID();
/*  6349 */       switch (i) {
/*       */       case 101: 
/*  6351 */         localComponentListener.componentResized(paramComponentEvent);
/*  6352 */         break;
/*       */       case 100: 
/*  6354 */         localComponentListener.componentMoved(paramComponentEvent);
/*  6355 */         break;
/*       */       case 102: 
/*  6357 */         localComponentListener.componentShown(paramComponentEvent);
/*  6358 */         break;
/*       */       case 103: 
/*  6360 */         localComponentListener.componentHidden(paramComponentEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processFocusEvent(FocusEvent paramFocusEvent)
/*       */   {
/*  6409 */     FocusListener localFocusListener = this.focusListener;
/*  6410 */     if (localFocusListener != null) {
/*  6411 */       int i = paramFocusEvent.getID();
/*  6412 */       switch (i) {
/*       */       case 1004: 
/*  6414 */         localFocusListener.focusGained(paramFocusEvent);
/*  6415 */         break;
/*       */       case 1005: 
/*  6417 */         localFocusListener.focusLost(paramFocusEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processKeyEvent(KeyEvent paramKeyEvent)
/*       */   {
/*  6475 */     KeyListener localKeyListener = this.keyListener;
/*  6476 */     if (localKeyListener != null) {
/*  6477 */       int i = paramKeyEvent.getID();
/*  6478 */       switch (i) {
/*       */       case 400: 
/*  6480 */         localKeyListener.keyTyped(paramKeyEvent);
/*  6481 */         break;
/*       */       case 401: 
/*  6483 */         localKeyListener.keyPressed(paramKeyEvent);
/*  6484 */         break;
/*       */       case 402: 
/*  6486 */         localKeyListener.keyReleased(paramKeyEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processMouseEvent(MouseEvent paramMouseEvent)
/*       */   {
/*  6517 */     MouseListener localMouseListener = this.mouseListener;
/*  6518 */     if (localMouseListener != null) {
/*  6519 */       int i = paramMouseEvent.getID();
/*  6520 */       switch (i) {
/*       */       case 501: 
/*  6522 */         localMouseListener.mousePressed(paramMouseEvent);
/*  6523 */         break;
/*       */       case 502: 
/*  6525 */         localMouseListener.mouseReleased(paramMouseEvent);
/*  6526 */         break;
/*       */       case 500: 
/*  6528 */         localMouseListener.mouseClicked(paramMouseEvent);
/*  6529 */         break;
/*       */       case 505: 
/*  6531 */         localMouseListener.mouseExited(paramMouseEvent);
/*  6532 */         break;
/*       */       case 504: 
/*  6534 */         localMouseListener.mouseEntered(paramMouseEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processMouseMotionEvent(MouseEvent paramMouseEvent)
/*       */   {
/*  6565 */     MouseMotionListener localMouseMotionListener = this.mouseMotionListener;
/*  6566 */     if (localMouseMotionListener != null) {
/*  6567 */       int i = paramMouseEvent.getID();
/*  6568 */       switch (i) {
/*       */       case 503: 
/*  6570 */         localMouseMotionListener.mouseMoved(paramMouseEvent);
/*  6571 */         break;
/*       */       case 506: 
/*  6573 */         localMouseMotionListener.mouseDragged(paramMouseEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent)
/*       */   {
/*  6608 */     MouseWheelListener localMouseWheelListener = this.mouseWheelListener;
/*  6609 */     if (localMouseWheelListener != null) {
/*  6610 */       int i = paramMouseWheelEvent.getID();
/*  6611 */       switch (i) {
/*       */       case 507: 
/*  6613 */         localMouseWheelListener.mouseWheelMoved(paramMouseWheelEvent);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   boolean postsOldMouseEvents()
/*       */   {
/*  6620 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processInputMethodEvent(InputMethodEvent paramInputMethodEvent)
/*       */   {
/*  6648 */     InputMethodListener localInputMethodListener = this.inputMethodListener;
/*  6649 */     if (localInputMethodListener != null) {
/*  6650 */       int i = paramInputMethodEvent.getID();
/*  6651 */       switch (i) {
/*       */       case 1100: 
/*  6653 */         localInputMethodListener.inputMethodTextChanged(paramInputMethodEvent);
/*  6654 */         break;
/*       */       case 1101: 
/*  6656 */         localInputMethodListener.caretPositionChanged(paramInputMethodEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processHierarchyEvent(HierarchyEvent paramHierarchyEvent)
/*       */   {
/*  6687 */     HierarchyListener localHierarchyListener = this.hierarchyListener;
/*  6688 */     if (localHierarchyListener != null) {
/*  6689 */       int i = paramHierarchyEvent.getID();
/*  6690 */       switch (i) {
/*       */       case 1400: 
/*  6692 */         localHierarchyListener.hierarchyChanged(paramHierarchyEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void processHierarchyBoundsEvent(HierarchyEvent paramHierarchyEvent)
/*       */   {
/*  6723 */     HierarchyBoundsListener localHierarchyBoundsListener = this.hierarchyBoundsListener;
/*  6724 */     if (localHierarchyBoundsListener != null) {
/*  6725 */       int i = paramHierarchyEvent.getID();
/*  6726 */       switch (i) {
/*       */       case 1401: 
/*  6728 */         localHierarchyBoundsListener.ancestorMoved(paramHierarchyEvent);
/*  6729 */         break;
/*       */       case 1402: 
/*  6731 */         localHierarchyBoundsListener.ancestorResized(paramHierarchyEvent);
/*       */       }
/*       */       
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean handleEvent(Event paramEvent)
/*       */   {
/*  6743 */     switch (paramEvent.id) {
/*       */     case 504: 
/*  6745 */       return mouseEnter(paramEvent, paramEvent.x, paramEvent.y);
/*       */     
/*       */     case 505: 
/*  6748 */       return mouseExit(paramEvent, paramEvent.x, paramEvent.y);
/*       */     
/*       */     case 503: 
/*  6751 */       return mouseMove(paramEvent, paramEvent.x, paramEvent.y);
/*       */     
/*       */     case 501: 
/*  6754 */       return mouseDown(paramEvent, paramEvent.x, paramEvent.y);
/*       */     
/*       */     case 506: 
/*  6757 */       return mouseDrag(paramEvent, paramEvent.x, paramEvent.y);
/*       */     
/*       */     case 502: 
/*  6760 */       return mouseUp(paramEvent, paramEvent.x, paramEvent.y);
/*       */     
/*       */     case 401: 
/*       */     case 403: 
/*  6764 */       return keyDown(paramEvent, paramEvent.key);
/*       */     
/*       */     case 402: 
/*       */     case 404: 
/*  6768 */       return keyUp(paramEvent, paramEvent.key);
/*       */     
/*       */     case 1001: 
/*  6771 */       return action(paramEvent, paramEvent.arg);
/*       */     case 1004: 
/*  6773 */       return gotFocus(paramEvent, paramEvent.arg);
/*       */     case 1005: 
/*  6775 */       return lostFocus(paramEvent, paramEvent.arg);
/*       */     }
/*  6777 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean mouseDown(Event paramEvent, int paramInt1, int paramInt2)
/*       */   {
/*  6786 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean mouseDrag(Event paramEvent, int paramInt1, int paramInt2)
/*       */   {
/*  6795 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean mouseUp(Event paramEvent, int paramInt1, int paramInt2)
/*       */   {
/*  6804 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean mouseMove(Event paramEvent, int paramInt1, int paramInt2)
/*       */   {
/*  6813 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean mouseEnter(Event paramEvent, int paramInt1, int paramInt2)
/*       */   {
/*  6822 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean mouseExit(Event paramEvent, int paramInt1, int paramInt2)
/*       */   {
/*  6831 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean keyDown(Event paramEvent, int paramInt)
/*       */   {
/*  6840 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean keyUp(Event paramEvent, int paramInt)
/*       */   {
/*  6849 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean action(Event paramEvent, Object paramObject)
/*       */   {
/*  6859 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addNotify()
/*       */   {
/*  6877 */     synchronized (getTreeLock()) {
/*  6878 */       Object localObject1 = this.peer;
/*  6879 */       if ((localObject1 == null) || ((localObject1 instanceof LightweightPeer))) {
/*  6880 */         if (localObject1 == null)
/*       */         {
/*       */ 
/*  6883 */           this.peer = (localObject1 = getToolkit().createComponent(this));
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6890 */         if (this.parent != null) {
/*  6891 */           long l = 0L;
/*  6892 */           if ((this.mouseListener != null) || ((this.eventMask & 0x10) != 0L)) {
/*  6893 */             l |= 0x10;
/*       */           }
/*  6895 */           if ((this.mouseMotionListener != null) || ((this.eventMask & 0x20) != 0L))
/*       */           {
/*  6897 */             l |= 0x20;
/*       */           }
/*  6899 */           if ((this.mouseWheelListener != null) || ((this.eventMask & 0x20000) != 0L))
/*       */           {
/*  6901 */             l |= 0x20000;
/*       */           }
/*  6903 */           if ((this.focusListener != null) || ((this.eventMask & 0x4) != 0L)) {
/*  6904 */             l |= 0x4;
/*       */           }
/*  6906 */           if ((this.keyListener != null) || ((this.eventMask & 0x8) != 0L)) {
/*  6907 */             l |= 0x8;
/*       */           }
/*  6909 */           if (l != 0L) {
/*  6910 */             this.parent.proxyEnableEvents(l);
/*       */           }
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*  6916 */         Container localContainer = getContainer();
/*  6917 */         if ((localContainer != null) && (localContainer.isLightweight())) {
/*  6918 */           relocateComponent();
/*  6919 */           if (!localContainer.isRecursivelyVisibleUpToHeavyweightContainer())
/*       */           {
/*  6921 */             ((ComponentPeer)localObject1).setVisible(false);
/*       */           }
/*       */         }
/*       */       }
/*  6925 */       invalidate();
/*       */       
/*  6927 */       int i = this.popups != null ? this.popups.size() : 0;
/*  6928 */       for (int j = 0; j < i; j++) {
/*  6929 */         PopupMenu localPopupMenu = (PopupMenu)this.popups.elementAt(j);
/*  6930 */         localPopupMenu.addNotify();
/*       */       }
/*       */       
/*  6933 */       if (this.dropTarget != null) { this.dropTarget.addNotify((ComponentPeer)localObject1);
/*       */       }
/*  6935 */       this.peerFont = getFont();
/*       */       
/*  6937 */       if ((getContainer() != null) && (!this.isAddNotifyComplete)) {
/*  6938 */         getContainer().increaseComponentCount(this);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  6943 */       updateZOrder();
/*       */       
/*  6945 */       if (!this.isAddNotifyComplete) {
/*  6946 */         mixOnShowing();
/*       */       }
/*       */       
/*  6949 */       this.isAddNotifyComplete = true;
/*       */       
/*  6951 */       if ((this.hierarchyListener != null) || ((this.eventMask & 0x8000) != 0L) || 
/*       */       
/*  6953 */         (Toolkit.enabledOnToolkit(32768L)))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*  6958 */         HierarchyEvent localHierarchyEvent = new HierarchyEvent(this, 1400, this, this.parent, 0x2 | (isRecursivelyVisible() ? 4 : 0));
/*       */         
/*       */ 
/*  6961 */         dispatchEvent(localHierarchyEvent);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removeNotify()
/*       */   {
/*  6980 */     KeyboardFocusManager.clearMostRecentFocusOwner(this);
/*       */     
/*  6982 */     if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner() == this)
/*       */     {
/*       */ 
/*  6985 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalPermanentFocusOwner(null);
/*       */     }
/*       */     
/*  6988 */     synchronized (getTreeLock()) {
/*  6989 */       clearLightweightDispatcherOnRemove(this);
/*       */       
/*  6991 */       if ((isFocusOwner()) && (KeyboardFocusManager.isAutoFocusTransferEnabledFor(this))) {
/*  6992 */         transferFocus(true);
/*       */       }
/*       */       
/*  6995 */       if ((getContainer() != null) && (this.isAddNotifyComplete)) {
/*  6996 */         getContainer().decreaseComponentCount(this);
/*       */       }
/*       */       
/*  6999 */       int i = this.popups != null ? this.popups.size() : 0;
/*  7000 */       for (int j = 0; j < i; j++) {
/*  7001 */         PopupMenu localPopupMenu = (PopupMenu)this.popups.elementAt(j);
/*  7002 */         localPopupMenu.removeNotify();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  7007 */       if ((this.eventMask & 0x1000) != 0L) {
/*  7008 */         localObject1 = getInputContext();
/*  7009 */         if (localObject1 != null) {
/*  7010 */           ((java.awt.im.InputContext)localObject1).removeNotify(this);
/*       */         }
/*       */       }
/*       */       
/*  7014 */       Object localObject1 = this.peer;
/*  7015 */       if (localObject1 != null) {
/*  7016 */         boolean bool = isLightweight();
/*       */         
/*  7018 */         if ((this.bufferStrategy instanceof FlipBufferStrategy)) {
/*  7019 */           ((FlipBufferStrategy)this.bufferStrategy).destroyBuffers();
/*       */         }
/*       */         
/*  7022 */         if (this.dropTarget != null) { this.dropTarget.removeNotify(this.peer);
/*       */         }
/*       */         
/*  7025 */         if (this.visible) {
/*  7026 */           ((ComponentPeer)localObject1).setVisible(false);
/*       */         }
/*       */         
/*  7029 */         this.peer = null;
/*  7030 */         this.peerFont = null;
/*       */         
/*  7032 */         Toolkit.getEventQueue().removeSourceEvents(this, false);
/*  7033 */         KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  7034 */           .discardKeyEvents(this);
/*       */         
/*  7036 */         ((ComponentPeer)localObject1).dispose();
/*       */         
/*  7038 */         mixOnHiding(bool);
/*       */         
/*  7040 */         this.isAddNotifyComplete = false;
/*       */         
/*       */ 
/*  7043 */         this.compoundShape = null;
/*       */       }
/*       */       
/*  7046 */       if ((this.hierarchyListener != null) || ((this.eventMask & 0x8000) != 0L) || 
/*       */       
/*  7048 */         (Toolkit.enabledOnToolkit(32768L)))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*  7053 */         HierarchyEvent localHierarchyEvent = new HierarchyEvent(this, 1400, this, this.parent, 0x2 | (isRecursivelyVisible() ? 4 : 0));
/*       */         
/*       */ 
/*  7056 */         dispatchEvent(localHierarchyEvent);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean gotFocus(Event paramEvent, Object paramObject)
/*       */   {
/*  7067 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean lostFocus(Event paramEvent, Object paramObject)
/*       */   {
/*  7076 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public boolean isFocusTraversable()
/*       */   {
/*  7091 */     if (this.isFocusTraversableOverridden == 0) {
/*  7092 */       this.isFocusTraversableOverridden = 1;
/*       */     }
/*  7094 */     return this.focusable;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isFocusable()
/*       */   {
/*  7106 */     return isFocusTraversable();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setFocusable(boolean paramBoolean)
/*       */   {
/*       */     boolean bool;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7121 */     synchronized (this) {
/*  7122 */       bool = this.focusable;
/*  7123 */       this.focusable = paramBoolean;
/*       */     }
/*  7125 */     this.isFocusTraversableOverridden = 2;
/*       */     
/*  7127 */     firePropertyChange("focusable", bool, paramBoolean);
/*  7128 */     if ((bool) && (!paramBoolean)) {
/*  7129 */       if ((isFocusOwner()) && (KeyboardFocusManager.isAutoFocusTransferEnabled())) {
/*  7130 */         transferFocus(true);
/*       */       }
/*  7132 */       KeyboardFocusManager.clearMostRecentFocusOwner(this);
/*       */     }
/*       */   }
/*       */   
/*       */   final boolean isFocusTraversableOverridden() {
/*  7137 */     return this.isFocusTraversableOverridden != 1;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setFocusTraversalKeys(int paramInt, Set<? extends AWTKeyStroke> paramSet)
/*       */   {
/*  7215 */     if ((paramInt < 0) || (paramInt >= 3)) {
/*  7216 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*       */     }
/*       */     
/*  7219 */     setFocusTraversalKeys_NoIDCheck(paramInt, paramSet);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Set<AWTKeyStroke> getFocusTraversalKeys(int paramInt)
/*       */   {
/*  7249 */     if ((paramInt < 0) || (paramInt >= 3)) {
/*  7250 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*       */     }
/*       */     
/*  7253 */     return getFocusTraversalKeys_NoIDCheck(paramInt);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final void setFocusTraversalKeys_NoIDCheck(int paramInt, Set<? extends AWTKeyStroke> paramSet)
/*       */   {
/*       */     Set localSet;
/*       */     
/*       */ 
/*  7264 */     synchronized (this) {
/*  7265 */       if (this.focusTraversalKeys == null) {
/*  7266 */         initializeFocusTraversalKeys();
/*       */       }
/*       */       
/*  7269 */       if (paramSet != null) {
/*  7270 */         for (AWTKeyStroke localAWTKeyStroke : paramSet)
/*       */         {
/*  7272 */           if (localAWTKeyStroke == null) {
/*  7273 */             throw new IllegalArgumentException("cannot set null focus traversal key");
/*       */           }
/*       */           
/*  7276 */           if (localAWTKeyStroke.getKeyChar() != 65535) {
/*  7277 */             throw new IllegalArgumentException("focus traversal keys cannot map to KEY_TYPED events");
/*       */           }
/*       */           
/*  7280 */           for (int i = 0; i < this.focusTraversalKeys.length; i++) {
/*  7281 */             if (i != paramInt)
/*       */             {
/*       */ 
/*       */ 
/*  7285 */               if (getFocusTraversalKeys_NoIDCheck(i).contains(localAWTKeyStroke))
/*       */               {
/*  7287 */                 throw new IllegalArgumentException("focus traversal keys must be unique for a Component");
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*  7293 */       localSet = this.focusTraversalKeys[paramInt];
/*  7294 */       this.focusTraversalKeys[paramInt] = (paramSet != null ? 
/*  7295 */         Collections.unmodifiableSet(new HashSet(paramSet)) : null);
/*       */     }
/*       */     
/*       */ 
/*  7299 */     firePropertyChange(focusTraversalKeyPropertyNames[paramInt], localSet, paramSet);
/*       */   }
/*       */   
/*       */ 
/*       */   final Set<AWTKeyStroke> getFocusTraversalKeys_NoIDCheck(int paramInt)
/*       */   {
/*  7305 */     Set<AWTKeyStroke> localSet = this.focusTraversalKeys != null ? this.focusTraversalKeys[paramInt] : null;
/*       */     
/*       */ 
/*       */ 
/*  7309 */     if (localSet != null) {
/*  7310 */       return localSet;
/*       */     }
/*  7312 */     Container localContainer = this.parent;
/*  7313 */     if (localContainer != null) {
/*  7314 */       return localContainer.getFocusTraversalKeys(paramInt);
/*       */     }
/*       */     
/*  7317 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(paramInt);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean areFocusTraversalKeysSet(int paramInt)
/*       */   {
/*  7341 */     if ((paramInt < 0) || (paramInt >= 3)) {
/*  7342 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*       */     }
/*       */     
/*  7345 */     return (this.focusTraversalKeys != null) && (this.focusTraversalKeys[paramInt] != null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setFocusTraversalKeysEnabled(boolean paramBoolean)
/*       */   {
/*       */     boolean bool;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7367 */     synchronized (this) {
/*  7368 */       bool = this.focusTraversalKeysEnabled;
/*  7369 */       this.focusTraversalKeysEnabled = paramBoolean;
/*       */     }
/*  7371 */     firePropertyChange("focusTraversalKeysEnabled", bool, paramBoolean);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean getFocusTraversalKeysEnabled()
/*       */   {
/*  7390 */     return this.focusTraversalKeysEnabled;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void requestFocus()
/*       */   {
/*  7428 */     requestFocusHelper(false, true);
/*       */   }
/*       */   
/*       */   boolean requestFocus(CausedFocusEvent.Cause paramCause) {
/*  7432 */     return requestFocusHelper(false, true, paramCause);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected boolean requestFocus(boolean paramBoolean)
/*       */   {
/*  7495 */     return requestFocusHelper(paramBoolean, true);
/*       */   }
/*       */   
/*       */   boolean requestFocus(boolean paramBoolean, CausedFocusEvent.Cause paramCause) {
/*  7499 */     return requestFocusHelper(paramBoolean, true, paramCause);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean requestFocusInWindow()
/*       */   {
/*  7546 */     return requestFocusHelper(false, false);
/*       */   }
/*       */   
/*       */   boolean requestFocusInWindow(CausedFocusEvent.Cause paramCause) {
/*  7550 */     return requestFocusHelper(false, false, paramCause);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected boolean requestFocusInWindow(boolean paramBoolean)
/*       */   {
/*  7611 */     return requestFocusHelper(paramBoolean, false);
/*       */   }
/*       */   
/*       */   boolean requestFocusInWindow(boolean paramBoolean, CausedFocusEvent.Cause paramCause) {
/*  7615 */     return requestFocusHelper(paramBoolean, false, paramCause);
/*       */   }
/*       */   
/*       */   final boolean requestFocusHelper(boolean paramBoolean1, boolean paramBoolean2)
/*       */   {
/*  7620 */     return requestFocusHelper(paramBoolean1, paramBoolean2, CausedFocusEvent.Cause.UNKNOWN);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final boolean requestFocusHelper(boolean paramBoolean1, boolean paramBoolean2, CausedFocusEvent.Cause paramCause)
/*       */   {
/*  7628 */     AWTEvent localAWTEvent = EventQueue.getCurrentEvent();
/*  7629 */     if (((localAWTEvent instanceof MouseEvent)) && 
/*  7630 */       (SunToolkit.isSystemGenerated(localAWTEvent)))
/*       */     {
/*       */ 
/*  7633 */       localObject = ((MouseEvent)localAWTEvent).getComponent();
/*  7634 */       if ((localObject == null) || (((Component)localObject).getContainingWindow() == getContainingWindow())) {
/*  7635 */         focusLog.finest("requesting focus by mouse event \"in window\"");
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7645 */         paramBoolean2 = false;
/*       */       }
/*       */     }
/*  7648 */     if (!isRequestFocusAccepted(paramBoolean1, paramBoolean2, paramCause)) {
/*  7649 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7650 */         focusLog.finest("requestFocus is not accepted");
/*       */       }
/*  7652 */       return false;
/*       */     }
/*       */     
/*  7655 */     KeyboardFocusManager.setMostRecentFocusOwner(this);
/*       */     
/*  7657 */     Object localObject = this;
/*  7658 */     while ((localObject != null) && (!(localObject instanceof Window))) {
/*  7659 */       if (!((Component)localObject).isVisible()) {
/*  7660 */         if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7661 */           focusLog.finest("component is recurively invisible");
/*       */         }
/*  7663 */         return false;
/*       */       }
/*  7665 */       localObject = ((Component)localObject).parent;
/*       */     }
/*       */     
/*  7668 */     ComponentPeer localComponentPeer = this.peer;
/*       */     
/*  7670 */     Component localComponent = (localComponentPeer instanceof LightweightPeer) ? getNativeContainer() : this;
/*  7671 */     if ((localComponent == null) || (!localComponent.isVisible())) {
/*  7672 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7673 */         focusLog.finest("Component is not a part of visible hierarchy");
/*       */       }
/*  7675 */       return false;
/*       */     }
/*  7677 */     localComponentPeer = localComponent.peer;
/*  7678 */     if (localComponentPeer == null) {
/*  7679 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7680 */         focusLog.finest("Peer is null");
/*       */       }
/*  7682 */       return false;
/*       */     }
/*       */     
/*       */ 
/*  7686 */     long l = 0L;
/*  7687 */     if (EventQueue.isDispatchThread()) {
/*  7688 */       l = Toolkit.getEventQueue().getMostRecentKeyEventTime();
/*       */     }
/*       */     else
/*       */     {
/*  7692 */       l = System.currentTimeMillis();
/*       */     }
/*       */     
/*       */ 
/*  7696 */     boolean bool = localComponentPeer.requestFocus(this, paramBoolean1, paramBoolean2, l, paramCause);
/*  7697 */     if (!bool)
/*       */     {
/*  7699 */       KeyboardFocusManager.getCurrentKeyboardFocusManager(this.appContext).dequeueKeyEvents(l, this);
/*  7700 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7701 */         focusLog.finest("Peer request failed");
/*       */       }
/*       */     }
/*  7704 */     else if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7705 */       focusLog.finest("Pass for " + this);
/*       */     }
/*       */     
/*  7708 */     return bool;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private boolean isRequestFocusAccepted(boolean paramBoolean1, boolean paramBoolean2, CausedFocusEvent.Cause paramCause)
/*       */   {
/*  7715 */     if ((!isFocusable()) || (!isVisible())) {
/*  7716 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7717 */         focusLog.finest("Not focusable or not visible");
/*       */       }
/*  7719 */       return false;
/*       */     }
/*       */     
/*  7722 */     ComponentPeer localComponentPeer = this.peer;
/*  7723 */     if (localComponentPeer == null) {
/*  7724 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7725 */         focusLog.finest("peer is null");
/*       */       }
/*  7727 */       return false;
/*       */     }
/*       */     
/*  7730 */     Window localWindow = getContainingWindow();
/*  7731 */     if ((localWindow == null) || (!localWindow.isFocusableWindow())) {
/*  7732 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7733 */         focusLog.finest("Component doesn't have toplevel");
/*       */       }
/*  7735 */       return false;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  7740 */     Component localComponent = KeyboardFocusManager.getMostRecentFocusOwner(localWindow);
/*  7741 */     if (localComponent == null)
/*       */     {
/*       */ 
/*  7744 */       localComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*  7745 */       if ((localComponent != null) && (localComponent.getContainingWindow() != localWindow)) {
/*  7746 */         localComponent = null;
/*       */       }
/*       */     }
/*       */     
/*  7750 */     if ((localComponent == this) || (localComponent == null))
/*       */     {
/*       */ 
/*       */ 
/*  7754 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7755 */         focusLog.finest("focus owner is null or this");
/*       */       }
/*  7757 */       return true;
/*       */     }
/*       */     
/*  7760 */     if (CausedFocusEvent.Cause.ACTIVATION == paramCause)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7767 */       if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7768 */         focusLog.finest("cause is activation");
/*       */       }
/*  7770 */       return true;
/*       */     }
/*       */     
/*  7773 */     boolean bool = requestFocusController.acceptRequestFocus(localComponent, this, paramBoolean1, paramBoolean2, paramCause);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7778 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  7779 */       focusLog.finest("RequestFocusController returns {0}", new Object[] { Boolean.valueOf(bool) });
/*       */     }
/*       */     
/*  7782 */     return bool;
/*       */   }
/*       */   
/*  7785 */   private static RequestFocusController requestFocusController = new DummyRequestFocusController(null);
/*       */   
/*       */ 
/*       */ 
/*       */   private static class DummyRequestFocusController
/*       */     implements RequestFocusController
/*       */   {
/*       */     public boolean acceptRequestFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, CausedFocusEvent.Cause paramCause)
/*       */     {
/*  7794 */       return true;
/*       */     }
/*       */   }
/*       */   
/*       */   static synchronized void setRequestFocusController(RequestFocusController paramRequestFocusController)
/*       */   {
/*  7800 */     if (paramRequestFocusController == null) {
/*  7801 */       requestFocusController = new DummyRequestFocusController(null);
/*       */     } else {
/*  7803 */       requestFocusController = paramRequestFocusController;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Container getFocusCycleRootAncestor()
/*       */   {
/*  7822 */     Container localContainer = this.parent;
/*  7823 */     while ((localContainer != null) && (!localContainer.isFocusCycleRoot())) {
/*  7824 */       localContainer = localContainer.parent;
/*       */     }
/*  7826 */     return localContainer;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isFocusCycleRoot(Container paramContainer)
/*       */   {
/*  7842 */     Container localContainer = getFocusCycleRootAncestor();
/*  7843 */     return localContainer == paramContainer;
/*       */   }
/*       */   
/*       */   Container getTraversalRoot() {
/*  7847 */     return getFocusCycleRootAncestor();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void transferFocus()
/*       */   {
/*  7857 */     nextFocus();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   @Deprecated
/*       */   public void nextFocus()
/*       */   {
/*  7866 */     transferFocus(false);
/*       */   }
/*       */   
/*       */   boolean transferFocus(boolean paramBoolean) {
/*  7870 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7871 */       focusLog.finer("clearOnFailure = " + paramBoolean);
/*       */     }
/*  7873 */     Component localComponent = getNextFocusCandidate();
/*  7874 */     boolean bool = false;
/*  7875 */     if ((localComponent != null) && (!localComponent.isFocusOwner()) && (localComponent != this)) {
/*  7876 */       bool = localComponent.requestFocusInWindow(CausedFocusEvent.Cause.TRAVERSAL_FORWARD);
/*       */     }
/*  7878 */     if ((paramBoolean) && (!bool)) {
/*  7879 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7880 */         focusLog.finer("clear global focus owner");
/*       */       }
/*  7882 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwnerPriv();
/*       */     }
/*  7884 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7885 */       focusLog.finer("returning result: " + bool);
/*       */     }
/*  7887 */     return bool;
/*       */   }
/*       */   
/*       */   final Component getNextFocusCandidate() {
/*  7891 */     Container localContainer = getTraversalRoot();
/*  7892 */     Object localObject1 = this;
/*  7893 */     while ((localContainer != null) && (
/*  7894 */       (!localContainer.isShowing()) || (!localContainer.canBeFocusOwner())))
/*       */     {
/*  7896 */       localObject1 = localContainer;
/*  7897 */       localContainer = ((Component)localObject1).getFocusCycleRootAncestor();
/*       */     }
/*  7899 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7900 */       focusLog.finer("comp = " + localObject1 + ", root = " + localContainer);
/*       */     }
/*  7902 */     Object localObject2 = null;
/*  7903 */     if (localContainer != null) {
/*  7904 */       FocusTraversalPolicy localFocusTraversalPolicy = localContainer.getFocusTraversalPolicy();
/*  7905 */       Object localObject3 = localFocusTraversalPolicy.getComponentAfter(localContainer, (Component)localObject1);
/*  7906 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7907 */         focusLog.finer("component after is " + localObject3);
/*       */       }
/*  7909 */       if (localObject3 == null) {
/*  7910 */         localObject3 = localFocusTraversalPolicy.getDefaultComponent(localContainer);
/*  7911 */         if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7912 */           focusLog.finer("default component is " + localObject3);
/*       */         }
/*       */       }
/*  7915 */       if (localObject3 == null) {
/*  7916 */         Applet localApplet = EmbeddedFrame.getAppletIfAncestorOf(this);
/*  7917 */         if (localApplet != null) {
/*  7918 */           localObject3 = localApplet;
/*       */         }
/*       */       }
/*  7921 */       localObject2 = localObject3;
/*       */     }
/*  7923 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7924 */       focusLog.finer("Focus transfer candidate: " + localObject2);
/*       */     }
/*  7926 */     return (Component)localObject2;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void transferFocusBackward()
/*       */   {
/*  7936 */     transferFocusBackward(false);
/*       */   }
/*       */   
/*       */   boolean transferFocusBackward(boolean paramBoolean) {
/*  7940 */     Container localContainer = getTraversalRoot();
/*  7941 */     Object localObject = this;
/*  7942 */     while ((localContainer != null) && (
/*  7943 */       (!localContainer.isShowing()) || (!localContainer.canBeFocusOwner())))
/*       */     {
/*  7945 */       localObject = localContainer;
/*  7946 */       localContainer = ((Component)localObject).getFocusCycleRootAncestor();
/*       */     }
/*  7948 */     boolean bool = false;
/*  7949 */     if (localContainer != null) {
/*  7950 */       FocusTraversalPolicy localFocusTraversalPolicy = localContainer.getFocusTraversalPolicy();
/*  7951 */       Component localComponent = localFocusTraversalPolicy.getComponentBefore(localContainer, (Component)localObject);
/*  7952 */       if (localComponent == null) {
/*  7953 */         localComponent = localFocusTraversalPolicy.getDefaultComponent(localContainer);
/*       */       }
/*  7955 */       if (localComponent != null) {
/*  7956 */         bool = localComponent.requestFocusInWindow(CausedFocusEvent.Cause.TRAVERSAL_BACKWARD);
/*       */       }
/*       */     }
/*  7959 */     if ((paramBoolean) && (!bool)) {
/*  7960 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7961 */         focusLog.finer("clear global focus owner");
/*       */       }
/*  7963 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwnerPriv();
/*       */     }
/*  7965 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  7966 */       focusLog.finer("returning result: " + bool);
/*       */     }
/*  7968 */     return bool;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void transferFocusUpCycle()
/*       */   {
/*  7986 */     Container localContainer = getFocusCycleRootAncestor();
/*  7987 */     while ((localContainer != null) && ((!localContainer.isShowing()) || 
/*  7988 */       (!localContainer.isFocusable()) || 
/*  7989 */       (!localContainer.isEnabled())))
/*  7990 */       localContainer = localContainer.getFocusCycleRootAncestor();
/*       */     Object localObject1;
/*       */     Object localObject2;
/*  7993 */     if (localContainer != null)
/*       */     {
/*  7995 */       localObject1 = localContainer.getFocusCycleRootAncestor();
/*  7996 */       localObject2 = localObject1 != null ? localObject1 : localContainer;
/*       */       
/*       */ 
/*  7999 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  8000 */         .setGlobalCurrentFocusCycleRootPriv((Container)localObject2);
/*  8001 */       localContainer.requestFocus(CausedFocusEvent.Cause.TRAVERSAL_UP);
/*       */     } else {
/*  8003 */       localObject1 = getContainingWindow();
/*       */       
/*  8005 */       if (localObject1 != null)
/*       */       {
/*  8007 */         localObject2 = ((Window)localObject1).getFocusTraversalPolicy().getDefaultComponent((Container)localObject1);
/*  8008 */         if (localObject2 != null)
/*       */         {
/*  8010 */           KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalCurrentFocusCycleRootPriv((Container)localObject1);
/*  8011 */           ((Component)localObject2).requestFocus(CausedFocusEvent.Cause.TRAVERSAL_UP);
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean hasFocus()
/*       */   {
/*  8028 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean isFocusOwner()
/*       */   {
/*  8040 */     return hasFocus();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8047 */   private boolean autoFocusTransferOnDisposal = true;
/*       */   
/*       */   void setAutoFocusTransferOnDisposal(boolean paramBoolean) {
/*  8050 */     this.autoFocusTransferOnDisposal = paramBoolean;
/*       */   }
/*       */   
/*       */   boolean isAutoFocusTransferOnDisposal() {
/*  8054 */     return this.autoFocusTransferOnDisposal;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void add(PopupMenu paramPopupMenu)
/*       */   {
/*  8065 */     synchronized (getTreeLock()) {
/*  8066 */       if (paramPopupMenu.parent != null) {
/*  8067 */         paramPopupMenu.parent.remove(paramPopupMenu);
/*       */       }
/*  8069 */       if (this.popups == null) {
/*  8070 */         this.popups = new Vector();
/*       */       }
/*  8072 */       this.popups.addElement(paramPopupMenu);
/*  8073 */       paramPopupMenu.parent = this;
/*       */       
/*  8075 */       if ((this.peer != null) && 
/*  8076 */         (paramPopupMenu.peer == null)) {
/*  8077 */         paramPopupMenu.addNotify();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void remove(MenuComponent paramMenuComponent)
/*       */   {
/*  8091 */     synchronized (getTreeLock()) {
/*  8092 */       if (this.popups == null) {
/*  8093 */         return;
/*       */       }
/*  8095 */       int i = this.popups.indexOf(paramMenuComponent);
/*  8096 */       if (i >= 0) {
/*  8097 */         PopupMenu localPopupMenu = (PopupMenu)paramMenuComponent;
/*  8098 */         if (localPopupMenu.peer != null) {
/*  8099 */           localPopupMenu.removeNotify();
/*       */         }
/*  8101 */         localPopupMenu.parent = null;
/*  8102 */         this.popups.removeElementAt(i);
/*  8103 */         if (this.popups.size() == 0) {
/*  8104 */           this.popups = null;
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected String paramString()
/*       */   {
/*  8121 */     String str1 = Objects.toString(getName(), "");
/*  8122 */     String str2 = isValid() ? "" : ",invalid";
/*  8123 */     String str3 = this.visible ? "" : ",hidden";
/*  8124 */     String str4 = this.enabled ? "" : ",disabled";
/*  8125 */     return str1 + ',' + this.x + ',' + this.y + ',' + this.width + 'x' + this.height + str2 + str3 + str4;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String toString()
/*       */   {
/*  8135 */     return getClass().getName() + '[' + paramString() + ']';
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void list()
/*       */   {
/*  8145 */     list(System.out, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void list(PrintStream paramPrintStream)
/*       */   {
/*  8156 */     list(paramPrintStream, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void list(PrintStream paramPrintStream, int paramInt)
/*       */   {
/*  8169 */     for (int i = 0; i < paramInt; i++) {
/*  8170 */       paramPrintStream.print(" ");
/*       */     }
/*  8172 */     paramPrintStream.println(this);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void list(PrintWriter paramPrintWriter)
/*       */   {
/*  8182 */     list(paramPrintWriter, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void list(PrintWriter paramPrintWriter, int paramInt)
/*       */   {
/*  8195 */     for (int i = 0; i < paramInt; i++) {
/*  8196 */       paramPrintWriter.print(" ");
/*       */     }
/*  8198 */     paramPrintWriter.println(this);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   final Container getNativeContainer()
/*       */   {
/*  8206 */     Container localContainer = getContainer();
/*  8207 */     while ((localContainer != null) && ((localContainer.peer instanceof LightweightPeer))) {
/*  8208 */       localContainer = localContainer.getContainer();
/*       */     }
/*  8210 */     return localContainer;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*       */   {
/*  8249 */     synchronized (getObjectLock()) {
/*  8250 */       if (paramPropertyChangeListener == null) {
/*  8251 */         return;
/*       */       }
/*  8253 */       if (this.changeSupport == null) {
/*  8254 */         this.changeSupport = new PropertyChangeSupport(this);
/*       */       }
/*  8256 */       this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*       */   {
/*  8275 */     synchronized (getObjectLock()) {
/*  8276 */       if ((paramPropertyChangeListener == null) || (this.changeSupport == null)) {
/*  8277 */         return;
/*       */       }
/*  8279 */       this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PropertyChangeListener[] getPropertyChangeListeners()
/*       */   {
/*  8298 */     synchronized (getObjectLock()) {
/*  8299 */       if (this.changeSupport == null) {
/*  8300 */         return new PropertyChangeListener[0];
/*       */       }
/*  8302 */       return this.changeSupport.getPropertyChangeListeners();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*       */   {
/*  8340 */     synchronized (getObjectLock()) {
/*  8341 */       if (paramPropertyChangeListener == null) {
/*  8342 */         return;
/*       */       }
/*  8344 */       if (this.changeSupport == null) {
/*  8345 */         this.changeSupport = new PropertyChangeSupport(this);
/*       */       }
/*  8347 */       this.changeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*       */   {
/*  8370 */     synchronized (getObjectLock()) {
/*  8371 */       if ((paramPropertyChangeListener == null) || (this.changeSupport == null)) {
/*  8372 */         return;
/*       */       }
/*  8374 */       this.changeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PropertyChangeListener[] getPropertyChangeListeners(String paramString)
/*       */   {
/*  8394 */     synchronized (getObjectLock()) {
/*  8395 */       if (this.changeSupport == null) {
/*  8396 */         return new PropertyChangeListener[0];
/*       */       }
/*  8398 */       return this.changeSupport.getPropertyChangeListeners(paramString);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2)
/*       */   {
/*       */     PropertyChangeSupport localPropertyChangeSupport;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8415 */     synchronized (getObjectLock()) {
/*  8416 */       localPropertyChangeSupport = this.changeSupport;
/*       */     }
/*  8418 */     if ((localPropertyChangeSupport == null) || ((paramObject1 != null) && (paramObject2 != null) && 
/*  8419 */       (paramObject1.equals(paramObject2)))) {
/*  8420 */       return;
/*       */     }
/*  8422 */     localPropertyChangeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*       */   {
/*  8438 */     PropertyChangeSupport localPropertyChangeSupport = this.changeSupport;
/*  8439 */     if ((localPropertyChangeSupport == null) || (paramBoolean1 == paramBoolean2)) {
/*  8440 */       return;
/*       */     }
/*  8442 */     localPropertyChangeSupport.firePropertyChange(paramString, paramBoolean1, paramBoolean2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void firePropertyChange(String paramString, int paramInt1, int paramInt2)
/*       */   {
/*  8458 */     PropertyChangeSupport localPropertyChangeSupport = this.changeSupport;
/*  8459 */     if ((localPropertyChangeSupport == null) || (paramInt1 == paramInt2)) {
/*  8460 */       return;
/*       */     }
/*  8462 */     localPropertyChangeSupport.firePropertyChange(paramString, paramInt1, paramInt2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void firePropertyChange(String paramString, byte paramByte1, byte paramByte2)
/*       */   {
/*  8477 */     if ((this.changeSupport == null) || (paramByte1 == paramByte2)) {
/*  8478 */       return;
/*       */     }
/*  8480 */     firePropertyChange(paramString, Byte.valueOf(paramByte1), Byte.valueOf(paramByte2));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void firePropertyChange(String paramString, char paramChar1, char paramChar2)
/*       */   {
/*  8495 */     if ((this.changeSupport == null) || (paramChar1 == paramChar2)) {
/*  8496 */       return;
/*       */     }
/*  8498 */     firePropertyChange(paramString, new Character(paramChar1), new Character(paramChar2));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void firePropertyChange(String paramString, short paramShort1, short paramShort2)
/*       */   {
/*  8513 */     if ((this.changeSupport == null) || (paramShort1 == paramShort2)) {
/*  8514 */       return;
/*       */     }
/*  8516 */     firePropertyChange(paramString, Short.valueOf(paramShort1), Short.valueOf(paramShort2));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void firePropertyChange(String paramString, long paramLong1, long paramLong2)
/*       */   {
/*  8532 */     if ((this.changeSupport == null) || (paramLong1 == paramLong2)) {
/*  8533 */       return;
/*       */     }
/*  8535 */     firePropertyChange(paramString, Long.valueOf(paramLong1), Long.valueOf(paramLong2));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void firePropertyChange(String paramString, float paramFloat1, float paramFloat2)
/*       */   {
/*  8550 */     if ((this.changeSupport == null) || (paramFloat1 == paramFloat2)) {
/*  8551 */       return;
/*       */     }
/*  8553 */     firePropertyChange(paramString, Float.valueOf(paramFloat1), Float.valueOf(paramFloat2));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void firePropertyChange(String paramString, double paramDouble1, double paramDouble2)
/*       */   {
/*  8568 */     if ((this.changeSupport == null) || (paramDouble1 == paramDouble2)) {
/*  8569 */       return;
/*       */     }
/*  8571 */     firePropertyChange(paramString, Double.valueOf(paramDouble1), Double.valueOf(paramDouble2));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8582 */   private int componentSerializedDataVersion = 4;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private void doSwingSerialization()
/*       */   {
/*  8589 */     Package localPackage = Package.getPackage("javax.swing");
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8596 */     for (Class localClass1 = getClass(); localClass1 != null; 
/*  8597 */         localClass1 = localClass1.getSuperclass()) {
/*  8598 */       if ((localClass1.getPackage() == localPackage) && 
/*  8599 */         (localClass1.getClassLoader() == null)) {
/*  8600 */         final Class localClass2 = localClass1;
/*       */         
/*  8602 */         Method[] arrayOfMethod = (Method[])AccessController.doPrivileged(new PrivilegedAction()
/*       */         {
/*       */           public Method[] run() {
/*  8605 */             return localClass2.getDeclaredMethods();
/*       */           }
/*       */         });
/*  8608 */         for (int i = arrayOfMethod.length - 1; i >= 0; 
/*  8609 */             i--) {
/*  8610 */           final Method localMethod = arrayOfMethod[i];
/*  8611 */           if (localMethod.getName().equals("compWriteObjectNotify"))
/*       */           {
/*       */ 
/*  8614 */             AccessController.doPrivileged(new PrivilegedAction() {
/*       */               public Void run() {
/*  8616 */                 localMethod.setAccessible(true);
/*  8617 */                 return null;
/*       */               }
/*       */             });
/*       */             try
/*       */             {
/*  8622 */               localMethod.invoke(this, (Object[])null);
/*       */             }
/*       */             catch (IllegalAccessException localIllegalAccessException) {}catch (InvocationTargetException localInvocationTargetException) {}
/*       */             
/*       */ 
/*  8627 */             return;
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*       */     throws IOException
/*       */   {
/*  8681 */     doSwingSerialization();
/*       */     
/*  8683 */     paramObjectOutputStream.defaultWriteObject();
/*       */     
/*  8685 */     AWTEventMulticaster.save(paramObjectOutputStream, "componentL", this.componentListener);
/*  8686 */     AWTEventMulticaster.save(paramObjectOutputStream, "focusL", this.focusListener);
/*  8687 */     AWTEventMulticaster.save(paramObjectOutputStream, "keyL", this.keyListener);
/*  8688 */     AWTEventMulticaster.save(paramObjectOutputStream, "mouseL", this.mouseListener);
/*  8689 */     AWTEventMulticaster.save(paramObjectOutputStream, "mouseMotionL", this.mouseMotionListener);
/*  8690 */     AWTEventMulticaster.save(paramObjectOutputStream, "inputMethodL", this.inputMethodListener);
/*       */     
/*  8692 */     paramObjectOutputStream.writeObject(null);
/*  8693 */     paramObjectOutputStream.writeObject(this.componentOrientation);
/*       */     
/*  8695 */     AWTEventMulticaster.save(paramObjectOutputStream, "hierarchyL", this.hierarchyListener);
/*  8696 */     AWTEventMulticaster.save(paramObjectOutputStream, "hierarchyBoundsL", this.hierarchyBoundsListener);
/*       */     
/*  8698 */     paramObjectOutputStream.writeObject(null);
/*       */     
/*  8700 */     AWTEventMulticaster.save(paramObjectOutputStream, "mouseWheelL", this.mouseWheelListener);
/*  8701 */     paramObjectOutputStream.writeObject(null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private void readObject(ObjectInputStream paramObjectInputStream)
/*       */     throws ClassNotFoundException, IOException
/*       */   {
/*  8717 */     this.objectLock = new Object();
/*       */     
/*  8719 */     this.acc = AccessController.getContext();
/*       */     
/*  8721 */     paramObjectInputStream.defaultReadObject();
/*       */     
/*  8723 */     this.appContext = AppContext.getAppContext();
/*  8724 */     this.coalescingEnabled = checkCoalescing();
/*  8725 */     if (this.componentSerializedDataVersion < 4)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  8730 */       this.focusable = true;
/*  8731 */       this.isFocusTraversableOverridden = 0;
/*  8732 */       initializeFocusTraversalKeys();
/*  8733 */       this.focusTraversalKeysEnabled = true;
/*       */     }
/*       */     
/*       */     Object localObject1;
/*  8737 */     while (null != (localObject1 = paramObjectInputStream.readObject())) {
/*  8738 */       localObject2 = ((String)localObject1).intern();
/*       */       
/*  8740 */       if ("componentL" == localObject2) {
/*  8741 */         addComponentListener((ComponentListener)paramObjectInputStream.readObject());
/*       */       }
/*  8743 */       else if ("focusL" == localObject2) {
/*  8744 */         addFocusListener((FocusListener)paramObjectInputStream.readObject());
/*       */       }
/*  8746 */       else if ("keyL" == localObject2) {
/*  8747 */         addKeyListener((KeyListener)paramObjectInputStream.readObject());
/*       */       }
/*  8749 */       else if ("mouseL" == localObject2) {
/*  8750 */         addMouseListener((MouseListener)paramObjectInputStream.readObject());
/*       */       }
/*  8752 */       else if ("mouseMotionL" == localObject2) {
/*  8753 */         addMouseMotionListener((MouseMotionListener)paramObjectInputStream.readObject());
/*       */       }
/*  8755 */       else if ("inputMethodL" == localObject2) {
/*  8756 */         addInputMethodListener((InputMethodListener)paramObjectInputStream.readObject());
/*       */       }
/*       */       else {
/*  8759 */         paramObjectInputStream.readObject();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  8764 */     Object localObject2 = null;
/*       */     try
/*       */     {
/*  8767 */       localObject2 = paramObjectInputStream.readObject();
/*       */ 
/*       */ 
/*       */ 
/*       */     }
/*       */     catch (OptionalDataException localOptionalDataException1)
/*       */     {
/*       */ 
/*       */ 
/*  8776 */       if (!localOptionalDataException1.eof) {
/*  8777 */         throw localOptionalDataException1;
/*       */       }
/*       */     }
/*       */     
/*  8781 */     if (localObject2 != null) {
/*  8782 */       this.componentOrientation = ((ComponentOrientation)localObject2);
/*       */     } else {
/*  8784 */       this.componentOrientation = ComponentOrientation.UNKNOWN;
/*       */     }
/*       */     try
/*       */     {
/*  8788 */       while (null != (localObject1 = paramObjectInputStream.readObject())) {
/*  8789 */         String str1 = ((String)localObject1).intern();
/*       */         
/*  8791 */         if ("hierarchyL" == str1) {
/*  8792 */           addHierarchyListener((HierarchyListener)paramObjectInputStream.readObject());
/*       */         }
/*  8794 */         else if ("hierarchyBoundsL" == str1) {
/*  8795 */           addHierarchyBoundsListener(
/*  8796 */             (HierarchyBoundsListener)paramObjectInputStream.readObject());
/*       */         }
/*       */         else
/*       */         {
/*  8800 */           paramObjectInputStream.readObject();
/*       */ 
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (OptionalDataException localOptionalDataException2)
/*       */     {
/*       */ 
/*  8811 */       if (!localOptionalDataException2.eof) {
/*  8812 */         throw localOptionalDataException2;
/*       */       }
/*       */     }
/*       */     try
/*       */     {
/*  8817 */       while (null != (localObject1 = paramObjectInputStream.readObject())) {
/*  8818 */         String str2 = ((String)localObject1).intern();
/*       */         
/*  8820 */         if ("mouseWheelL" == str2) {
/*  8821 */           addMouseWheelListener((MouseWheelListener)paramObjectInputStream.readObject());
/*       */         }
/*       */         else
/*       */         {
/*  8825 */           paramObjectInputStream.readObject();
/*       */ 
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (OptionalDataException localOptionalDataException3)
/*       */     {
/*       */ 
/*  8836 */       if (!localOptionalDataException3.eof) {
/*  8837 */         throw localOptionalDataException3;
/*       */       }
/*       */     }
/*       */     
/*  8841 */     if (this.popups != null) {
/*  8842 */       int i = this.popups.size();
/*  8843 */       for (int j = 0; j < i; j++) {
/*  8844 */         PopupMenu localPopupMenu = (PopupMenu)this.popups.elementAt(j);
/*  8845 */         localPopupMenu.parent = this;
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setComponentOrientation(ComponentOrientation paramComponentOrientation)
/*       */   {
/*  8880 */     ComponentOrientation localComponentOrientation = this.componentOrientation;
/*  8881 */     this.componentOrientation = paramComponentOrientation;
/*       */     
/*       */ 
/*       */ 
/*  8885 */     firePropertyChange("componentOrientation", localComponentOrientation, paramComponentOrientation);
/*       */     
/*       */ 
/*  8888 */     invalidateIfValid();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ComponentOrientation getComponentOrientation()
/*       */   {
/*  8903 */     return this.componentOrientation;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void applyComponentOrientation(ComponentOrientation paramComponentOrientation)
/*       */   {
/*  8923 */     if (paramComponentOrientation == null) {
/*  8924 */       throw new NullPointerException();
/*       */     }
/*  8926 */     setComponentOrientation(paramComponentOrientation);
/*       */   }
/*       */   
/*       */   final boolean canBeFocusOwner()
/*       */   {
/*  8931 */     if ((isEnabled()) && (isDisplayable()) && (isVisible()) && (isFocusable())) {
/*  8932 */       return true;
/*       */     }
/*  8934 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   final boolean canBeFocusOwnerRecursively()
/*       */   {
/*  8948 */     if (!canBeFocusOwner()) {
/*  8949 */       return false;
/*       */     }
/*       */     
/*       */ 
/*  8953 */     synchronized (getTreeLock()) {
/*  8954 */       if (this.parent != null) {
/*  8955 */         return this.parent.canContainFocusOwner(this);
/*       */       }
/*       */     }
/*  8958 */     return true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   final void relocateComponent()
/*       */   {
/*  8965 */     synchronized (getTreeLock()) {
/*  8966 */       if (this.peer == null) {
/*  8967 */         return;
/*       */       }
/*  8969 */       int i = this.x;
/*  8970 */       int j = this.y;
/*  8971 */       for (Container localContainer = getContainer(); 
/*  8972 */           (localContainer != null) && (localContainer.isLightweight()); 
/*  8973 */           localContainer = localContainer.getContainer())
/*       */       {
/*  8975 */         i += localContainer.x;
/*  8976 */         j += localContainer.y;
/*       */       }
/*  8978 */       this.peer.setBounds(i, j, this.width, this.height, 1);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Window getContainingWindow()
/*       */   {
/*  8989 */     return SunToolkit.getContainingWindow(this);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9008 */   protected AccessibleContext accessibleContext = null;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public AccessibleContext getAccessibleContext()
/*       */   {
/*  9024 */     return this.accessibleContext;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected abstract class AccessibleAWTComponent
/*       */     extends AccessibleContext
/*       */     implements Serializable, AccessibleComponent
/*       */   {
/*       */     private static final long serialVersionUID = 642321655757800191L;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9053 */     private volatile transient int propertyListenersCount = 0;
/*       */     
/*  9055 */     protected ComponentListener accessibleAWTComponentHandler = null;
/*  9056 */     protected FocusListener accessibleAWTFocusHandler = null;
/*       */     protected AccessibleAWTComponent() {}
/*       */     
/*       */     protected class AccessibleAWTComponentHandler implements ComponentListener
/*       */     {
/*       */       protected AccessibleAWTComponentHandler() {}
/*       */       
/*       */       public void componentHidden(ComponentEvent paramComponentEvent)
/*       */       {
/*  9065 */         if (Component.this.accessibleContext != null) {
/*  9066 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.VISIBLE, null);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */       public void componentShown(ComponentEvent paramComponentEvent)
/*       */       {
/*  9073 */         if (Component.this.accessibleContext != null) {
/*  9074 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.VISIBLE);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */       public void componentMoved(ComponentEvent paramComponentEvent) {}
/*       */       
/*       */ 
/*       */       public void componentResized(ComponentEvent paramComponentEvent) {}
/*       */     }
/*       */     
/*       */ 
/*       */     protected class AccessibleAWTFocusHandler
/*       */       implements FocusListener
/*       */     {
/*       */       protected AccessibleAWTFocusHandler() {}
/*       */       
/*       */ 
/*       */       public void focusGained(FocusEvent paramFocusEvent)
/*       */       {
/*  9095 */         if (Component.this.accessibleContext != null) {
/*  9096 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.FOCUSED);
/*       */         }
/*       */       }
/*       */       
/*       */       public void focusLost(FocusEvent paramFocusEvent)
/*       */       {
/*  9102 */         if (Component.this.accessibleContext != null) {
/*  9103 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.FOCUSED, null);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*       */     {
/*  9117 */       if (this.accessibleAWTComponentHandler == null) {
/*  9118 */         this.accessibleAWTComponentHandler = new AccessibleAWTComponentHandler();
/*       */       }
/*  9120 */       if (this.accessibleAWTFocusHandler == null) {
/*  9121 */         this.accessibleAWTFocusHandler = new AccessibleAWTFocusHandler();
/*       */       }
/*  9123 */       if (this.propertyListenersCount++ == 0) {
/*  9124 */         Component.this.addComponentListener(this.accessibleAWTComponentHandler);
/*  9125 */         Component.this.addFocusListener(this.accessibleAWTFocusHandler);
/*       */       }
/*  9127 */       super.addPropertyChangeListener(paramPropertyChangeListener);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*       */     {
/*  9138 */       if (--this.propertyListenersCount == 0) {
/*  9139 */         Component.this.removeComponentListener(this.accessibleAWTComponentHandler);
/*  9140 */         Component.this.removeFocusListener(this.accessibleAWTFocusHandler);
/*       */       }
/*  9142 */       super.removePropertyChangeListener(paramPropertyChangeListener);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public String getAccessibleName()
/*       */     {
/*  9163 */       return this.accessibleName;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public String getAccessibleDescription()
/*       */     {
/*  9182 */       return this.accessibleDescription;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public AccessibleRole getAccessibleRole()
/*       */     {
/*  9193 */       return AccessibleRole.AWT_COMPONENT;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public AccessibleStateSet getAccessibleStateSet()
/*       */     {
/*  9204 */       return Component.this.getAccessibleStateSet();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Accessible getAccessibleParent()
/*       */     {
/*  9217 */       if (this.accessibleParent != null) {
/*  9218 */         return this.accessibleParent;
/*       */       }
/*  9220 */       Container localContainer = Component.this.getParent();
/*  9221 */       if ((localContainer instanceof Accessible)) {
/*  9222 */         return (Accessible)localContainer;
/*       */       }
/*       */       
/*  9225 */       return null;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public int getAccessibleIndexInParent()
/*       */     {
/*  9236 */       return Component.this.getAccessibleIndexInParent();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public int getAccessibleChildrenCount()
/*       */     {
/*  9247 */       return 0;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Accessible getAccessibleChild(int paramInt)
/*       */     {
/*  9257 */       return null;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Locale getLocale()
/*       */     {
/*  9266 */       return Component.this.getLocale();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public AccessibleComponent getAccessibleComponent()
/*       */     {
/*  9277 */       return this;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Color getBackground()
/*       */     {
/*  9290 */       return Component.this.getBackground();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setBackground(Color paramColor)
/*       */     {
/*  9301 */       Component.this.setBackground(paramColor);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Color getForeground()
/*       */     {
/*  9311 */       return Component.this.getForeground();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setForeground(Color paramColor)
/*       */     {
/*  9320 */       Component.this.setForeground(paramColor);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Cursor getCursor()
/*       */     {
/*  9330 */       return Component.this.getCursor();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setCursor(Cursor paramCursor)
/*       */     {
/*  9342 */       Component.this.setCursor(paramCursor);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Font getFont()
/*       */     {
/*  9352 */       return Component.this.getFont();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setFont(Font paramFont)
/*       */     {
/*  9361 */       Component.this.setFont(paramFont);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public FontMetrics getFontMetrics(Font paramFont)
/*       */     {
/*  9373 */       if (paramFont == null) {
/*  9374 */         return null;
/*       */       }
/*  9376 */       return Component.this.getFontMetrics(paramFont);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean isEnabled()
/*       */     {
/*  9386 */       return Component.this.isEnabled();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setEnabled(boolean paramBoolean)
/*       */     {
/*  9395 */       boolean bool = Component.this.isEnabled();
/*  9396 */       Component.this.setEnabled(paramBoolean);
/*  9397 */       if ((paramBoolean != bool) && 
/*  9398 */         (Component.this.accessibleContext != null)) {
/*  9399 */         if (paramBoolean) {
/*  9400 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
/*       */         }
/*       */         else
/*       */         {
/*  9404 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.ENABLED, null);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean isVisible()
/*       */     {
/*  9422 */       return Component.this.isVisible();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setVisible(boolean paramBoolean)
/*       */     {
/*  9431 */       boolean bool = Component.this.isVisible();
/*  9432 */       Component.this.setVisible(paramBoolean);
/*  9433 */       if ((paramBoolean != bool) && 
/*  9434 */         (Component.this.accessibleContext != null)) {
/*  9435 */         if (paramBoolean) {
/*  9436 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.VISIBLE);
/*       */         }
/*       */         else
/*       */         {
/*  9440 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.VISIBLE, null);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean isShowing()
/*       */     {
/*  9458 */       return Component.this.isShowing();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean contains(Point paramPoint)
/*       */     {
/*  9471 */       return Component.this.contains(paramPoint);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Point getLocationOnScreen()
/*       */     {
/*  9481 */       synchronized (Component.this.getTreeLock()) {
/*  9482 */         if (Component.this.isShowing()) {
/*  9483 */           return Component.this.getLocationOnScreen();
/*       */         }
/*  9485 */         return null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Point getLocation()
/*       */     {
/*  9500 */       return Component.this.getLocation();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setLocation(Point paramPoint)
/*       */     {
/*  9508 */       Component.this.setLocation(paramPoint);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Rectangle getBounds()
/*       */     {
/*  9520 */       return Component.this.getBounds();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setBounds(Rectangle paramRectangle)
/*       */     {
/*  9532 */       Component.this.setBounds(paramRectangle);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Dimension getSize()
/*       */     {
/*  9547 */       return Component.this.getSize();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void setSize(Dimension paramDimension)
/*       */     {
/*  9556 */       Component.this.setSize(paramDimension);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public Accessible getAccessibleAt(Point paramPoint)
/*       */     {
/*  9572 */       return null;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public boolean isFocusTraversable()
/*       */     {
/*  9581 */       return Component.this.isFocusTraversable();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     public void requestFocus()
/*       */     {
/*  9588 */       Component.this.requestFocus();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void addFocusListener(FocusListener paramFocusListener)
/*       */     {
/*  9598 */       Component.this.addFocusListener(paramFocusListener);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void removeFocusListener(FocusListener paramFocusListener)
/*       */     {
/*  9608 */       Component.this.removeFocusListener(paramFocusListener);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   int getAccessibleIndexInParent()
/*       */   {
/*  9622 */     synchronized (getTreeLock()) {
/*  9623 */       int i = -1;
/*  9624 */       Container localContainer = getParent();
/*  9625 */       if ((localContainer != null) && ((localContainer instanceof Accessible))) {
/*  9626 */         Component[] arrayOfComponent = localContainer.getComponents();
/*  9627 */         for (int j = 0; j < arrayOfComponent.length; j++) {
/*  9628 */           if ((arrayOfComponent[j] instanceof Accessible)) {
/*  9629 */             i++;
/*       */           }
/*  9631 */           if (equals(arrayOfComponent[j])) {
/*  9632 */             return i;
/*       */           }
/*       */         }
/*       */       }
/*  9636 */       return -1;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   AccessibleStateSet getAccessibleStateSet()
/*       */   {
/*  9648 */     synchronized (getTreeLock()) {
/*  9649 */       AccessibleStateSet localAccessibleStateSet = new AccessibleStateSet();
/*  9650 */       if (isEnabled()) {
/*  9651 */         localAccessibleStateSet.add(AccessibleState.ENABLED);
/*       */       }
/*  9653 */       if (isFocusTraversable()) {
/*  9654 */         localAccessibleStateSet.add(AccessibleState.FOCUSABLE);
/*       */       }
/*  9656 */       if (isVisible()) {
/*  9657 */         localAccessibleStateSet.add(AccessibleState.VISIBLE);
/*       */       }
/*  9659 */       if (isShowing()) {
/*  9660 */         localAccessibleStateSet.add(AccessibleState.SHOWING);
/*       */       }
/*  9662 */       if (isFocusOwner()) {
/*  9663 */         localAccessibleStateSet.add(AccessibleState.FOCUSED);
/*       */       }
/*  9665 */       if ((this instanceof Accessible)) {
/*  9666 */         AccessibleContext localAccessibleContext1 = ((Accessible)this).getAccessibleContext();
/*  9667 */         if (localAccessibleContext1 != null) {
/*  9668 */           Accessible localAccessible = localAccessibleContext1.getAccessibleParent();
/*  9669 */           if (localAccessible != null) {
/*  9670 */             AccessibleContext localAccessibleContext2 = localAccessible.getAccessibleContext();
/*  9671 */             if (localAccessibleContext2 != null) {
/*  9672 */               AccessibleSelection localAccessibleSelection = localAccessibleContext2.getAccessibleSelection();
/*  9673 */               if (localAccessibleSelection != null) {
/*  9674 */                 localAccessibleStateSet.add(AccessibleState.SELECTABLE);
/*  9675 */                 int i = localAccessibleContext1.getAccessibleIndexInParent();
/*  9676 */                 if ((i >= 0) && 
/*  9677 */                   (localAccessibleSelection.isAccessibleChildSelected(i))) {
/*  9678 */                   localAccessibleStateSet.add(AccessibleState.SELECTED);
/*       */                 }
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  9686 */       if ((isInstanceOf(this, "javax.swing.JComponent")) && 
/*  9687 */         (((JComponent)this).isOpaque())) {
/*  9688 */         localAccessibleStateSet.add(AccessibleState.OPAQUE);
/*       */       }
/*       */       
/*  9691 */       return localAccessibleStateSet;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static boolean isInstanceOf(Object paramObject, String paramString)
/*       */   {
/*  9703 */     if (paramObject == null) return false;
/*  9704 */     if (paramString == null) { return false;
/*       */     }
/*  9706 */     Class localClass = paramObject.getClass();
/*  9707 */     while (localClass != null) {
/*  9708 */       if (localClass.getName().equals(paramString)) {
/*  9709 */         return true;
/*       */       }
/*  9711 */       localClass = localClass.getSuperclass();
/*       */     }
/*  9713 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   final boolean areBoundsValid()
/*       */   {
/*  9728 */     Container localContainer = getContainer();
/*  9729 */     return (localContainer == null) || (localContainer.isValid()) || (localContainer.getLayout() == null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   void applyCompoundShape(Region paramRegion)
/*       */   {
/*  9737 */     checkTreeLock();
/*       */     
/*  9739 */     if (!areBoundsValid()) {
/*  9740 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9741 */         mixingLog.fine("this = " + this + "; areBoundsValid = " + areBoundsValid());
/*       */       }
/*  9743 */       return;
/*       */     }
/*       */     
/*  9746 */     if (!isLightweight()) {
/*  9747 */       ComponentPeer localComponentPeer = getPeer();
/*  9748 */       if (localComponentPeer != null)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9754 */         if (paramRegion.isEmpty()) {
/*  9755 */           paramRegion = Region.EMPTY_REGION;
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9764 */         if (paramRegion.equals(getNormalShape())) {
/*  9765 */           if (this.compoundShape == null) {
/*  9766 */             return;
/*       */           }
/*  9768 */           this.compoundShape = null;
/*  9769 */           localComponentPeer.applyShape(null);
/*       */         } else {
/*  9771 */           if (paramRegion.equals(getAppliedShape())) {
/*  9772 */             return;
/*       */           }
/*  9774 */           this.compoundShape = paramRegion;
/*  9775 */           Point localPoint = getLocationOnWindow();
/*  9776 */           if (mixingLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  9777 */             mixingLog.fine("this = " + this + "; compAbsolute=" + localPoint + "; shape=" + paramRegion);
/*       */           }
/*       */           
/*  9780 */           localComponentPeer.applyShape(paramRegion.getTranslatedRegion(-localPoint.x, -localPoint.y));
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private Region getAppliedShape()
/*       */   {
/*  9792 */     checkTreeLock();
/*       */     
/*  9794 */     return (this.compoundShape == null) || (isLightweight()) ? getNormalShape() : this.compoundShape;
/*       */   }
/*       */   
/*       */   Point getLocationOnWindow() {
/*  9798 */     checkTreeLock();
/*  9799 */     Point localPoint = getLocation();
/*       */     
/*  9801 */     for (Container localContainer = getContainer(); 
/*  9802 */         (localContainer != null) && (!(localContainer instanceof Window)); 
/*  9803 */         localContainer = localContainer.getContainer())
/*       */     {
/*  9805 */       localPoint.x += localContainer.getX();
/*  9806 */       localPoint.y += localContainer.getY();
/*       */     }
/*       */     
/*  9809 */     return localPoint;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   final Region getNormalShape()
/*       */   {
/*  9816 */     checkTreeLock();
/*       */     
/*  9818 */     Point localPoint = getLocationOnWindow();
/*       */     
/*  9820 */     return Region.getInstanceXYWH(localPoint.x, localPoint.y, 
/*       */     
/*       */ 
/*  9823 */       getWidth(), 
/*  9824 */       getHeight());
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   Region getOpaqueShape()
/*       */   {
/*  9841 */     checkTreeLock();
/*  9842 */     if (this.mixingCutoutRegion != null) {
/*  9843 */       return this.mixingCutoutRegion;
/*       */     }
/*  9845 */     return getNormalShape();
/*       */   }
/*       */   
/*       */   final int getSiblingIndexAbove()
/*       */   {
/*  9850 */     checkTreeLock();
/*  9851 */     Container localContainer = getContainer();
/*  9852 */     if (localContainer == null) {
/*  9853 */       return -1;
/*       */     }
/*       */     
/*  9856 */     int i = localContainer.getComponentZOrder(this) - 1;
/*       */     
/*  9858 */     return i < 0 ? -1 : i;
/*       */   }
/*       */   
/*       */   final ComponentPeer getHWPeerAboveMe() {
/*  9862 */     checkTreeLock();
/*       */     
/*  9864 */     Container localContainer = getContainer();
/*  9865 */     int i = getSiblingIndexAbove();
/*       */     
/*  9867 */     while (localContainer != null) {
/*  9868 */       for (int j = i; j > -1; j--) {
/*  9869 */         Component localComponent = localContainer.getComponent(j);
/*  9870 */         if ((localComponent != null) && (localComponent.isDisplayable()) && (!localComponent.isLightweight())) {
/*  9871 */           return localComponent.getPeer();
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  9878 */       if (!localContainer.isLightweight()) {
/*       */         break;
/*       */       }
/*       */       
/*  9882 */       i = localContainer.getSiblingIndexAbove();
/*  9883 */       localContainer = localContainer.getContainer();
/*       */     }
/*       */     
/*  9886 */     return null;
/*       */   }
/*       */   
/*       */   final int getSiblingIndexBelow() {
/*  9890 */     checkTreeLock();
/*  9891 */     Container localContainer = getContainer();
/*  9892 */     if (localContainer == null) {
/*  9893 */       return -1;
/*       */     }
/*       */     
/*  9896 */     int i = localContainer.getComponentZOrder(this) + 1;
/*       */     
/*  9898 */     return i >= localContainer.getComponentCount() ? -1 : i;
/*       */   }
/*       */   
/*       */   final boolean isNonOpaqueForMixing()
/*       */   {
/*  9903 */     return (this.mixingCutoutRegion != null) && (this.mixingCutoutRegion.isEmpty());
/*       */   }
/*       */   
/*       */   private Region calculateCurrentShape() {
/*  9907 */     checkTreeLock();
/*  9908 */     Region localRegion = getNormalShape();
/*       */     
/*  9910 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9911 */       mixingLog.fine("this = " + this + "; normalShape=" + localRegion);
/*       */     }
/*       */     
/*  9914 */     if (getContainer() != null) {
/*  9915 */       Object localObject = this;
/*  9916 */       Container localContainer = ((Component)localObject).getContainer();
/*       */       
/*  9918 */       while (localContainer != null) {
/*  9919 */         for (int i = ((Component)localObject).getSiblingIndexAbove(); i != -1; i--)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9927 */           Component localComponent = localContainer.getComponent(i);
/*  9928 */           if ((localComponent.isLightweight()) && (localComponent.isShowing())) {
/*  9929 */             localRegion = localRegion.getDifference(localComponent.getOpaqueShape());
/*       */           }
/*       */         }
/*       */         
/*  9933 */         if (!localContainer.isLightweight()) break;
/*  9934 */         localRegion = localRegion.getIntersection(localContainer.getNormalShape());
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*  9939 */         localObject = localContainer;
/*  9940 */         localContainer = localContainer.getContainer();
/*       */       }
/*       */     }
/*       */     
/*  9944 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9945 */       mixingLog.fine("currentShape=" + localRegion);
/*       */     }
/*       */     
/*  9948 */     return localRegion;
/*       */   }
/*       */   
/*       */   void applyCurrentShape() {
/*  9952 */     checkTreeLock();
/*  9953 */     if (!areBoundsValid()) {
/*  9954 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9955 */         mixingLog.fine("this = " + this + "; areBoundsValid = " + areBoundsValid());
/*       */       }
/*  9957 */       return;
/*       */     }
/*  9959 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9960 */       mixingLog.fine("this = " + this);
/*       */     }
/*  9962 */     applyCompoundShape(calculateCurrentShape());
/*       */   }
/*       */   
/*       */   final void subtractAndApplyShape(Region paramRegion) {
/*  9966 */     checkTreeLock();
/*       */     
/*  9968 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9969 */       mixingLog.fine("this = " + this + "; s=" + paramRegion);
/*       */     }
/*       */     
/*  9972 */     applyCompoundShape(getAppliedShape().getDifference(paramRegion));
/*       */   }
/*       */   
/*       */   private final void applyCurrentShapeBelowMe() {
/*  9976 */     checkTreeLock();
/*  9977 */     Object localObject = getContainer();
/*  9978 */     if ((localObject != null) && (((Container)localObject).isShowing()))
/*       */     {
/*  9980 */       ((Container)localObject).recursiveApplyCurrentShape(getSiblingIndexBelow());
/*       */       
/*       */ 
/*  9983 */       Container localContainer = ((Container)localObject).getContainer();
/*  9984 */       while ((!((Container)localObject).isOpaque()) && (localContainer != null)) {
/*  9985 */         localContainer.recursiveApplyCurrentShape(((Container)localObject).getSiblingIndexBelow());
/*       */         
/*  9987 */         localObject = localContainer;
/*  9988 */         localContainer = ((Container)localObject).getContainer();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   final void subtractAndApplyShapeBelowMe() {
/*  9994 */     checkTreeLock();
/*  9995 */     Object localObject = getContainer();
/*  9996 */     if ((localObject != null) && (isShowing())) {
/*  9997 */       Region localRegion = getOpaqueShape();
/*       */       
/*       */ 
/* 10000 */       ((Container)localObject).recursiveSubtractAndApplyShape(localRegion, getSiblingIndexBelow());
/*       */       
/*       */ 
/* 10003 */       Container localContainer = ((Container)localObject).getContainer();
/* 10004 */       while ((!((Container)localObject).isOpaque()) && (localContainer != null)) {
/* 10005 */         localContainer.recursiveSubtractAndApplyShape(localRegion, ((Container)localObject).getSiblingIndexBelow());
/*       */         
/* 10007 */         localObject = localContainer;
/* 10008 */         localContainer = ((Container)localObject).getContainer();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   void mixOnShowing() {
/* 10014 */     synchronized (getTreeLock()) {
/* 10015 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10016 */         mixingLog.fine("this = " + this);
/*       */       }
/* 10018 */       if (!isMixingNeeded()) {
/* 10019 */         return;
/*       */       }
/* 10021 */       if (isLightweight()) {
/* 10022 */         subtractAndApplyShapeBelowMe();
/*       */       } else {
/* 10024 */         applyCurrentShape();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   void mixOnHiding(boolean paramBoolean)
/*       */   {
/* 10032 */     synchronized (getTreeLock()) {
/* 10033 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10034 */         mixingLog.fine("this = " + this + "; isLightweight = " + paramBoolean);
/*       */       }
/* 10036 */       if (!isMixingNeeded()) {
/* 10037 */         return;
/*       */       }
/* 10039 */       if (paramBoolean) {
/* 10040 */         applyCurrentShapeBelowMe();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   void mixOnReshaping() {
/* 10046 */     synchronized (getTreeLock()) {
/* 10047 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10048 */         mixingLog.fine("this = " + this);
/*       */       }
/* 10050 */       if (!isMixingNeeded()) {
/* 10051 */         return;
/*       */       }
/* 10053 */       if (isLightweight()) {
/* 10054 */         applyCurrentShapeBelowMe();
/*       */       } else {
/* 10056 */         applyCurrentShape();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   void mixOnZOrderChanging(int paramInt1, int paramInt2) {
/* 10062 */     synchronized (getTreeLock()) {
/* 10063 */       int i = paramInt2 < paramInt1 ? 1 : 0;
/* 10064 */       Container localContainer = getContainer();
/*       */       
/* 10066 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10067 */         mixingLog.fine("this = " + this + "; oldZorder=" + paramInt1 + "; newZorder=" + paramInt2 + "; parent=" + localContainer);
/*       */       }
/*       */       
/* 10070 */       if (!isMixingNeeded()) {
/* 10071 */         return;
/*       */       }
/* 10073 */       if (isLightweight()) {
/* 10074 */         if (i != 0) {
/* 10075 */           if ((localContainer != null) && (isShowing())) {
/* 10076 */             localContainer.recursiveSubtractAndApplyShape(getOpaqueShape(), getSiblingIndexBelow(), paramInt1);
/*       */           }
/*       */         }
/* 10079 */         else if (localContainer != null) {
/* 10080 */           localContainer.recursiveApplyCurrentShape(paramInt1, paramInt2);
/*       */         }
/*       */         
/*       */       }
/* 10084 */       else if (i != 0) {
/* 10085 */         applyCurrentShape();
/*       */       }
/* 10087 */       else if (localContainer != null) {
/* 10088 */         Region localRegion = getAppliedShape();
/*       */         
/* 10090 */         for (int j = paramInt1; j < paramInt2; j++) {
/* 10091 */           Component localComponent = localContainer.getComponent(j);
/* 10092 */           if ((localComponent.isLightweight()) && (localComponent.isShowing())) {
/* 10093 */             localRegion = localRegion.getDifference(localComponent.getOpaqueShape());
/*       */           }
/*       */         }
/* 10096 */         applyCompoundShape(localRegion);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   final boolean isMixingNeeded()
/*       */   {
/* 10109 */     if (SunToolkit.getSunAwtDisableMixing()) {
/* 10110 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 10111 */         mixingLog.finest("this = " + this + "; Mixing disabled via sun.awt.disableMixing");
/*       */       }
/* 10113 */       return false;
/*       */     }
/* 10115 */     if (!areBoundsValid()) {
/* 10116 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10117 */         mixingLog.fine("this = " + this + "; areBoundsValid = " + areBoundsValid());
/*       */       }
/* 10119 */       return false;
/*       */     }
/* 10121 */     Window localWindow = getContainingWindow();
/* 10122 */     if (localWindow != null) {
/* 10123 */       if ((!localWindow.hasHeavyweightDescendants()) || (!localWindow.hasLightweightDescendants()) || (localWindow.isDisposing())) {
/* 10124 */         if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10125 */           mixingLog.fine("containing window = " + localWindow + "; has h/w descendants = " + localWindow
/* 10126 */             .hasHeavyweightDescendants() + "; has l/w descendants = " + localWindow
/* 10127 */             .hasLightweightDescendants() + "; disposing = " + localWindow
/* 10128 */             .isDisposing());
/*       */         }
/* 10130 */         return false;
/*       */       }
/*       */     } else {
/* 10133 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10134 */         mixingLog.fine("this = " + this + "; containing window is null");
/*       */       }
/* 10136 */       return false;
/*       */     }
/* 10138 */     return true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   void updateZOrder()
/*       */   {
/* 10146 */     this.peer.setZOrder(getHWPeerAboveMe());
/*       */   }
/*       */   
/*       */   /* Error */
/*       */   public GraphicsConfiguration getGraphicsConfiguration()
/*       */   {
/*       */     // Byte code:
/*       */     //   0: aload_0
/*       */     //   1: invokevirtual 11	java/awt/Component:getTreeLock	()Ljava/lang/Object;
/*       */     //   4: dup
/*       */     //   5: astore_1
/*       */     //   6: monitorenter
/*       */     //   7: aload_0
/*       */     //   8: invokevirtual 65	java/awt/Component:getGraphicsConfiguration_NoClientCode	()Ljava/awt/GraphicsConfiguration;
/*       */     //   11: aload_1
/*       */     //   12: monitorexit
/*       */     //   13: areturn
/*       */     //   14: astore_2
/*       */     //   15: aload_1
/*       */     //   16: monitorexit
/*       */     //   17: aload_2
/*       */     //   18: athrow
/*       */     // Line number table:
/*       */     //   Java source line #1144	-> byte code offset #0
/*       */     //   Java source line #1145	-> byte code offset #7
/*       */     //   Java source line #1146	-> byte code offset #14
/*       */     // Local variable table:
/*       */     //   start	length	slot	name	signature
/*       */     //   0	19	0	this	Component
/*       */     //   5	11	1	Ljava/lang/Object;	Object
/*       */     //   14	4	2	localObject1	Object
/*       */     // Exception table:
/*       */     //   from	to	target	type
/*       */     //   7	13	14	finally
/*       */     //   14	17	14	finally
/*       */   }
/*       */   
/*       */   void clearCurrentFocusCycleRootOnHide() {}
/*       */   
/*       */   /* Error */
/*       */   public Point getLocationOnScreen()
/*       */   {
/*       */     // Byte code:
/*       */     //   0: aload_0
/*       */     //   1: invokevirtual 11	java/awt/Component:getTreeLock	()Ljava/lang/Object;
/*       */     //   4: dup
/*       */     //   5: astore_1
/*       */     //   6: monitorenter
/*       */     //   7: aload_0
/*       */     //   8: invokevirtual 187	java/awt/Component:getLocationOnScreen_NoTreeLock	()Ljava/awt/Point;
/*       */     //   11: aload_1
/*       */     //   12: monitorexit
/*       */     //   13: areturn
/*       */     //   14: astore_2
/*       */     //   15: aload_1
/*       */     //   16: monitorexit
/*       */     //   17: aload_2
/*       */     //   18: athrow
/*       */     // Line number table:
/*       */     //   Java source line #2027	-> byte code offset #0
/*       */     //   Java source line #2028	-> byte code offset #7
/*       */     //   Java source line #2029	-> byte code offset #14
/*       */     // Local variable table:
/*       */     //   start	length	slot	name	signature
/*       */     //   0	19	0	this	Component
/*       */     //   5	11	1	Ljava/lang/Object;	Object
/*       */     //   14	4	2	localObject1	Object
/*       */     // Exception table:
/*       */     //   from	to	target	type
/*       */     //   7	13	14	finally
/*       */     //   14	17	14	finally
/*       */   }
/*       */   
/*       */   @Deprecated
/*       */   public void layout() {}
/*       */   
/*       */   public void paint(Graphics paramGraphics) {}
/*       */   
/*       */   void paintHeavyweightComponents(Graphics paramGraphics) {}
/*       */   
/*       */   void printHeavyweightComponents(Graphics paramGraphics) {}
/*       */   
/*       */   void autoProcessMouseWheel(MouseWheelEvent paramMouseWheelEvent) {}
/*       */   
/*       */   private static native void initIDs();
/*       */   
/*       */   void mixOnValidating() {}
/*       */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Component.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */