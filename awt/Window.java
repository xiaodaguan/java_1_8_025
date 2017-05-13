/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowFocusListener;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.awt.event.WindowStateListener;
/*      */ import java.awt.geom.Path2D.Float;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Double;
/*      */ import java.awt.im.InputContext;
/*      */ import java.awt.image.BufferStrategy;
/*      */ import java.awt.peer.WindowPeer;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.security.AccessController;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.EventListener;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JLayeredPane;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.WindowAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent.Cause;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.util.IdentityArrayList;
/*      */ import sun.java2d.Disposer;
/*      */ import sun.java2d.DisposerRecord;
/*      */ import sun.java2d.pipe.Region;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.util.SecurityConstants.AWT;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Window
/*      */   extends Container
/*      */   implements Accessible
/*      */ {
/*      */   String warningString;
/*      */   transient List<Image> icons;
/*      */   private transient Component temporaryLostComponent;
/*      */   private static native void initIDs();
/*      */   
/*      */   public static enum Type
/*      */   {
/*  172 */     NORMAL, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  182 */     UTILITY, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  192 */     POPUP;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Type() {}
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
/*  225 */   static boolean systemSyncLWRequests = false;
/*  226 */   boolean syncLWRequests = false;
/*  227 */   transient boolean beforeFirstShow = true;
/*  228 */   private transient boolean disposing = false;
/*  229 */   transient WindowDisposerRecord disposerRecord = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int OPENED = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int state;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean alwaysOnTop;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  258 */   private static final IdentityArrayList<Window> allWindows = new IdentityArrayList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  266 */   transient Vector<WeakReference<Window>> ownedWindowList = new Vector();
/*      */   
/*      */ 
/*      */ 
/*      */   private transient WeakReference<Window> weakThis;
/*      */   
/*      */ 
/*      */ 
/*      */   transient boolean showWithParent;
/*      */   
/*      */ 
/*      */ 
/*      */   transient Dialog modalBlocker;
/*      */   
/*      */ 
/*      */ 
/*      */   Dialog.ModalExclusionType modalExclusionType;
/*      */   
/*      */ 
/*      */ 
/*      */   transient WindowListener windowListener;
/*      */   
/*      */ 
/*      */ 
/*      */   transient WindowStateListener windowStateListener;
/*      */   
/*      */ 
/*      */ 
/*      */   transient WindowFocusListener windowFocusListener;
/*      */   
/*      */ 
/*      */ 
/*      */   transient InputContext inputContext;
/*      */   
/*      */ 
/*      */ 
/*  302 */   private transient Object inputContextLock = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private FocusManager focusMgr;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  320 */   private boolean focusableWindowState = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  332 */   private volatile boolean autoRequestFocus = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  341 */   transient boolean isInShow = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  351 */   private float opacity = 1.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  362 */   private Shape shape = null;
/*      */   
/*      */   private static final String base = "win";
/*  365 */   private static int nameCounter = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 4497834738069338734L;
/*      */   
/*      */ 
/*  372 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.Window");
/*      */   
/*      */   private static final boolean locationByPlatformProp;
/*      */   
/*  376 */   transient boolean isTrayIconWindow = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  382 */   private volatile transient int securityWarningWidth = 0;
/*  383 */   private volatile transient int securityWarningHeight = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  390 */   private transient double securityWarningPointX = 2.0D;
/*  391 */   private transient double securityWarningPointY = 0.0D;
/*  392 */   private transient float securityWarningAlignmentX = 1.0F;
/*  393 */   private transient float securityWarningAlignmentY = 0.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Window(GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  436 */     init(paramGraphicsConfiguration);
/*      */   }
/*      */   
/*  439 */   transient Object anchor = new Object();
/*      */   private static final AtomicBoolean beforeFirstWindowShown;
/*      */   
/*      */   static class WindowDisposerRecord implements DisposerRecord { WeakReference<Window> owner;
/*      */     final WeakReference<Window> weakThis;
/*      */     final WeakReference<AppContext> context;
/*      */     
/*  446 */     WindowDisposerRecord(AppContext paramAppContext, Window paramWindow) { this.weakThis = paramWindow.weakThis;
/*  447 */       this.context = new WeakReference(paramAppContext);
/*      */     }
/*      */     
/*      */     public void updateOwner() {
/*  451 */       Window localWindow = (Window)this.weakThis.get();
/*      */       
/*      */ 
/*  454 */       this.owner = (localWindow == null ? null : new WeakReference(localWindow.getOwner()));
/*      */     }
/*      */     
/*      */     public void dispose() {
/*  458 */       if (this.owner != null) {
/*  459 */         localObject = (Window)this.owner.get();
/*  460 */         if (localObject != null) {
/*  461 */           ((Window)localObject).removeOwnedWindow(this.weakThis);
/*      */         }
/*      */       }
/*  464 */       Object localObject = (AppContext)this.context.get();
/*  465 */       if (null != localObject) {
/*  466 */         Window.removeFromWindowList((AppContext)localObject, this.weakThis);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private GraphicsConfiguration initGC(GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*      */     
/*  474 */     if (paramGraphicsConfiguration == null)
/*      */     {
/*  476 */       paramGraphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
/*      */     }
/*  478 */     setGraphicsConfiguration(paramGraphicsConfiguration);
/*      */     
/*  480 */     return paramGraphicsConfiguration;
/*      */   }
/*      */   
/*      */   private void init(GraphicsConfiguration paramGraphicsConfiguration) {
/*  484 */     GraphicsEnvironment.checkHeadless();
/*      */     
/*  486 */     this.syncLWRequests = systemSyncLWRequests;
/*      */     
/*  488 */     this.weakThis = new WeakReference(this);
/*  489 */     addToWindowList();
/*      */     
/*  491 */     setWarningString();
/*  492 */     this.cursor = Cursor.getPredefinedCursor(0);
/*  493 */     this.visible = false;
/*      */     
/*  495 */     paramGraphicsConfiguration = initGC(paramGraphicsConfiguration);
/*      */     
/*  497 */     if (paramGraphicsConfiguration.getDevice().getType() != 0)
/*      */     {
/*  499 */       throw new IllegalArgumentException("not a screen device");
/*      */     }
/*  501 */     setLayout(new BorderLayout());
/*      */     
/*      */ 
/*      */ 
/*  505 */     Rectangle localRectangle = paramGraphicsConfiguration.getBounds();
/*  506 */     Insets localInsets = getToolkit().getScreenInsets(paramGraphicsConfiguration);
/*  507 */     int i = getX() + localRectangle.x + localInsets.left;
/*  508 */     int j = getY() + localRectangle.y + localInsets.top;
/*  509 */     if ((i != this.x) || (j != this.y)) {
/*  510 */       setLocation(i, j);
/*      */       
/*  512 */       setLocationByPlatform(locationByPlatformProp);
/*      */     }
/*      */     
/*  515 */     this.modalExclusionType = Dialog.ModalExclusionType.NO_EXCLUDE;
/*  516 */     this.disposerRecord = new WindowDisposerRecord(this.appContext, this);
/*  517 */     Disposer.addRecord(this.anchor, this.disposerRecord);
/*      */     
/*  519 */     SunToolkit.checkAndSetPolicy(this);
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
/*      */   Window()
/*      */     throws HeadlessException
/*      */   {
/*  536 */     GraphicsEnvironment.checkHeadless();
/*  537 */     init((GraphicsConfiguration)null);
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
/*      */   public Window(Frame paramFrame)
/*      */   {
/*  561 */     this(paramFrame == null ? (GraphicsConfiguration)null : paramFrame
/*  562 */       .getGraphicsConfiguration());
/*  563 */     ownedInit(paramFrame);
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
/*      */   public Window(Window paramWindow)
/*      */   {
/*  591 */     this(paramWindow == null ? (GraphicsConfiguration)null : paramWindow
/*  592 */       .getGraphicsConfiguration());
/*  593 */     ownedInit(paramWindow);
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
/*      */   public Window(Window paramWindow, GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  625 */     this(paramGraphicsConfiguration);
/*  626 */     ownedInit(paramWindow);
/*      */   }
/*      */   
/*      */   private void ownedInit(Window paramWindow) {
/*  630 */     this.parent = paramWindow;
/*  631 */     if (paramWindow != null) {
/*  632 */       paramWindow.addOwnedWindow(this.weakThis);
/*  633 */       if (paramWindow.isAlwaysOnTop()) {
/*      */         try {
/*  635 */           setAlwaysOnTop(true);
/*      */         }
/*      */         catch (SecurityException localSecurityException) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  642 */     this.disposerRecord.updateOwner();
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   String constructComponentName()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 92
/*      */     //   2: dup
/*      */     //   3: astore_1
/*      */     //   4: monitorenter
/*      */     //   5: new 93	java/lang/StringBuilder
/*      */     //   8: dup
/*      */     //   9: invokespecial 94	java/lang/StringBuilder:<init>	()V
/*      */     //   12: ldc 95
/*      */     //   14: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   17: getstatic 97	java/awt/Window:nameCounter	I
/*      */     //   20: dup
/*      */     //   21: iconst_1
/*      */     //   22: iadd
/*      */     //   23: putstatic 97	java/awt/Window:nameCounter	I
/*      */     //   26: invokevirtual 98	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   29: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   32: aload_1
/*      */     //   33: monitorexit
/*      */     //   34: areturn
/*      */     //   35: astore_2
/*      */     //   36: aload_1
/*      */     //   37: monitorexit
/*      */     //   38: aload_2
/*      */     //   39: athrow
/*      */     // Line number table:
/*      */     //   Java source line #650	-> byte code offset #0
/*      */     //   Java source line #651	-> byte code offset #5
/*      */     //   Java source line #652	-> byte code offset #35
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	40	0	this	Window
/*      */     //   3	34	1	Ljava/lang/Object;	Object
/*      */     //   35	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   5	34	35	finally
/*      */     //   35	38	35	finally
/*      */   }
/*      */   
/*      */   public List<Image> getIconImages()
/*      */   {
/*  668 */     List localList = this.icons;
/*  669 */     if ((localList == null) || (localList.size() == 0)) {
/*  670 */       return new ArrayList();
/*      */     }
/*  672 */     return new ArrayList(localList);
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
/*      */   public synchronized void setIconImages(List<? extends Image> paramList)
/*      */   {
/*  704 */     this.icons = (paramList == null ? new ArrayList() : new ArrayList(paramList));
/*      */     
/*  706 */     WindowPeer localWindowPeer = (WindowPeer)this.peer;
/*  707 */     if (localWindowPeer != null) {
/*  708 */       localWindowPeer.updateIconImages();
/*      */     }
/*      */     
/*  711 */     firePropertyChange("iconImage", null, null);
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
/*      */   public void setIconImage(Image paramImage)
/*      */   {
/*  742 */     ArrayList localArrayList = new ArrayList();
/*  743 */     if (paramImage != null) {
/*  744 */       localArrayList.add(paramImage);
/*      */     }
/*  746 */     setIconImages(localArrayList);
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
/*      */   public void addNotify()
/*      */   {
/*  759 */     synchronized (getTreeLock()) {
/*  760 */       Container localContainer = this.parent;
/*  761 */       if ((localContainer != null) && (localContainer.getPeer() == null)) {
/*  762 */         localContainer.addNotify();
/*      */       }
/*  764 */       if (this.peer == null) {
/*  765 */         this.peer = getToolkit().createWindow(this);
/*      */       }
/*  767 */       synchronized (allWindows) {
/*  768 */         allWindows.add(this);
/*      */       }
/*  770 */       super.addNotify();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void removeNotify()
/*      */   {
/*  778 */     synchronized (getTreeLock()) {
/*  779 */       synchronized (allWindows) {
/*  780 */         allWindows.remove(this);
/*      */       }
/*  782 */       super.removeNotify();
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
/*      */   public void pack()
/*      */   {
/*  802 */     Container localContainer = this.parent;
/*  803 */     if ((localContainer != null) && (localContainer.getPeer() == null)) {
/*  804 */       localContainer.addNotify();
/*      */     }
/*  806 */     if (this.peer == null) {
/*  807 */       addNotify();
/*      */     }
/*  809 */     Dimension localDimension = getPreferredSize();
/*  810 */     if (this.peer != null) {
/*  811 */       setClientSize(localDimension.width, localDimension.height);
/*      */     }
/*      */     
/*  814 */     if (this.beforeFirstShow) {
/*  815 */       this.isPacked = true;
/*      */     }
/*      */     
/*  818 */     validateUnconditionally();
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
/*      */   public void setMinimumSize(Dimension paramDimension)
/*      */   {
/*  851 */     synchronized (getTreeLock()) {
/*  852 */       super.setMinimumSize(paramDimension);
/*  853 */       Dimension localDimension = getSize();
/*  854 */       if ((isMinimumSizeSet()) && (
/*  855 */         (localDimension.width < paramDimension.width) || (localDimension.height < paramDimension.height))) {
/*  856 */         int i = Math.max(this.width, paramDimension.width);
/*  857 */         int j = Math.max(this.height, paramDimension.height);
/*  858 */         setSize(i, j);
/*      */       }
/*      */       
/*  861 */       if (this.peer != null) {
/*  862 */         ((WindowPeer)this.peer).updateMinimumSize();
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
/*      */   public void setSize(Dimension paramDimension)
/*      */   {
/*  886 */     super.setSize(paramDimension);
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
/*      */   public void setSize(int paramInt1, int paramInt2)
/*      */   {
/*  908 */     super.setSize(paramInt1, paramInt2);
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
/*      */   public void setLocation(int paramInt1, int paramInt2)
/*      */   {
/*  921 */     super.setLocation(paramInt1, paramInt2);
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
/*      */   public void setLocation(Point paramPoint)
/*      */   {
/*  934 */     super.setLocation(paramPoint);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  943 */     if (isMinimumSizeSet()) {
/*  944 */       Dimension localDimension = getMinimumSize();
/*  945 */       if (paramInt3 < localDimension.width) {
/*  946 */         paramInt3 = localDimension.width;
/*      */       }
/*  948 */       if (paramInt4 < localDimension.height) {
/*  949 */         paramInt4 = localDimension.height;
/*      */       }
/*      */     }
/*  952 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */   
/*      */   void setClientSize(int paramInt1, int paramInt2) {
/*  956 */     synchronized (getTreeLock()) {
/*  957 */       setBoundsOp(4);
/*  958 */       setBounds(this.x, this.y, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void closeSplashScreen()
/*      */   {
/*  966 */     if (this.isTrayIconWindow) {
/*  967 */       return;
/*      */     }
/*  969 */     if (beforeFirstWindowShown.getAndSet(false))
/*      */     {
/*      */ 
/*  972 */       SunToolkit.closeSplashScreen();
/*  973 */       SplashScreen.markClosed();
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
/*      */   public void setVisible(boolean paramBoolean)
/*      */   {
/* 1014 */     super.setVisible(paramBoolean);
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
/*      */   @Deprecated
/*      */   public void show()
/*      */   {
/* 1030 */     if (this.peer == null) {
/* 1031 */       addNotify();
/*      */     }
/* 1033 */     validateUnconditionally();
/*      */     
/* 1035 */     this.isInShow = true;
/* 1036 */     if (this.visible) {
/* 1037 */       toFront();
/*      */     } else {
/* 1039 */       this.beforeFirstShow = false;
/* 1040 */       closeSplashScreen();
/* 1041 */       Dialog.checkShouldBeBlocked(this);
/* 1042 */       super.show();
/* 1043 */       synchronized (getTreeLock()) {
/* 1044 */         this.locationByPlatform = false;
/*      */       }
/* 1046 */       for (int i = 0; i < this.ownedWindowList.size(); i++) {
/* 1047 */         Window localWindow = (Window)((WeakReference)this.ownedWindowList.elementAt(i)).get();
/* 1048 */         if ((localWindow != null) && (localWindow.showWithParent)) {
/* 1049 */           localWindow.show();
/* 1050 */           localWindow.showWithParent = false;
/*      */         }
/*      */       }
/* 1053 */       if (!isModalBlocked()) {
/* 1054 */         updateChildrenBlocking();
/*      */       }
/*      */       else
/*      */       {
/* 1058 */         this.modalBlocker.toFront_NoClientCode();
/*      */       }
/* 1060 */       if (((this instanceof Frame)) || ((this instanceof Dialog))) {
/* 1061 */         updateChildFocusableWindowState(this);
/*      */       }
/*      */     }
/* 1064 */     this.isInShow = false;
/*      */     
/*      */ 
/* 1067 */     if ((this.state & 0x1) == 0) {
/* 1068 */       postWindowEvent(200);
/* 1069 */       this.state |= 0x1;
/*      */     }
/*      */   }
/*      */   
/*      */   static void updateChildFocusableWindowState(Window paramWindow) {
/* 1074 */     if ((paramWindow.getPeer() != null) && (paramWindow.isShowing())) {
/* 1075 */       ((WindowPeer)paramWindow.getPeer()).updateFocusableWindowState();
/*      */     }
/* 1077 */     for (int i = 0; i < paramWindow.ownedWindowList.size(); i++) {
/* 1078 */       Window localWindow = (Window)((WeakReference)paramWindow.ownedWindowList.elementAt(i)).get();
/* 1079 */       if (localWindow != null) {
/* 1080 */         updateChildFocusableWindowState(localWindow);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   synchronized void postWindowEvent(int paramInt) {
/* 1086 */     if ((this.windowListener != null) || ((this.eventMask & 0x40) != 0L) || 
/*      */     
/* 1088 */       (Toolkit.enabledOnToolkit(64L))) {
/* 1089 */       WindowEvent localWindowEvent = new WindowEvent(this, paramInt);
/* 1090 */       Toolkit.getEventQueue().postEvent(localWindowEvent);
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
/*      */   @Deprecated
/*      */   public void hide()
/*      */   {
/* 1105 */     synchronized (this.ownedWindowList) {
/* 1106 */       for (int i = 0; i < this.ownedWindowList.size(); i++) {
/* 1107 */         Window localWindow = (Window)((WeakReference)this.ownedWindowList.elementAt(i)).get();
/* 1108 */         if ((localWindow != null) && (localWindow.visible)) {
/* 1109 */           localWindow.hide();
/* 1110 */           localWindow.showWithParent = true;
/*      */         }
/*      */       }
/*      */     }
/* 1114 */     if (isModalBlocked()) {
/* 1115 */       this.modalBlocker.unblockWindow(this);
/*      */     }
/* 1117 */     super.hide();
/* 1118 */     synchronized (getTreeLock()) {
/* 1119 */       this.locationByPlatform = false;
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
/*      */   final void clearMostRecentFocusOwnerOnHide() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void dispose()
/*      */   {
/* 1151 */     doDispose();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void disposeImpl()
/*      */   {
/* 1161 */     dispose();
/* 1162 */     if (getPeer() != null) {
/* 1163 */       doDispose();
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
/*      */   void doDispose()
/*      */   {
/* 1207 */     boolean bool = isDisplayable();
/* 1208 */     Runnable local1DisposeAction = new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/* 1170 */         Window.this.disposing = true;
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1175 */           GraphicsDevice localGraphicsDevice = Window.this.getGraphicsConfiguration().getDevice();
/* 1176 */           if (localGraphicsDevice.getFullScreenWindow() == Window.this) {
/* 1177 */             localGraphicsDevice.setFullScreenWindow(null);
/*      */           }
/*      */           
/*      */           Object[] arrayOfObject;
/* 1181 */           synchronized (Window.this.ownedWindowList) {
/* 1182 */             arrayOfObject = new Object[Window.this.ownedWindowList.size()];
/* 1183 */             Window.this.ownedWindowList.copyInto(arrayOfObject);
/*      */           }
/* 1185 */           for (??? = 0; ??? < arrayOfObject.length; ???++)
/*      */           {
/* 1187 */             Window localWindow = (Window)((WeakReference)arrayOfObject[???]).get();
/* 1188 */             if (localWindow != null) {
/* 1189 */               localWindow.disposeImpl();
/*      */             }
/*      */           }
/* 1192 */           Window.this.hide();
/* 1193 */           Window.this.beforeFirstShow = true;
/* 1194 */           Window.this.removeNotify();
/* 1195 */           synchronized (Window.this.inputContextLock) {
/* 1196 */             if (Window.this.inputContext != null) {
/* 1197 */               Window.this.inputContext.dispose();
/* 1198 */               Window.this.inputContext = null;
/*      */             }
/*      */           }
/* 1201 */           Window.this.clearCurrentFocusCycleRootOnHide();
/*      */         } finally {
/* 1203 */           Window.this.disposing = false;
/*      */         }
/*      */       }
/*      */     };
/*      */     
/*      */ 
/* 1209 */     if (EventQueue.isDispatchThread()) {
/* 1210 */       local1DisposeAction.run();
/*      */     } else {
/*      */       try
/*      */       {
/* 1214 */         EventQueue.invokeAndWait(this, local1DisposeAction);
/*      */       }
/*      */       catch (InterruptedException localInterruptedException) {
/* 1217 */         System.err.println("Disposal was interrupted:");
/* 1218 */         localInterruptedException.printStackTrace();
/*      */       }
/*      */       catch (InvocationTargetException localInvocationTargetException) {
/* 1221 */         System.err.println("Exception during disposal:");
/* 1222 */         localInvocationTargetException.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1228 */     if (bool) {
/* 1229 */       postWindowEvent(202);
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
/*      */   void adjustListeningChildrenOnParent(long paramLong, int paramInt) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void adjustDecendantsOnParent(int paramInt) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void toFront()
/*      */   {
/* 1291 */     toFront_NoClientCode();
/*      */   }
/*      */   
/*      */ 
/*      */   final void toFront_NoClientCode()
/*      */   {
/* 1297 */     if (this.visible) {
/* 1298 */       WindowPeer localWindowPeer = (WindowPeer)this.peer;
/* 1299 */       if (localWindowPeer != null) {
/* 1300 */         localWindowPeer.toFront();
/*      */       }
/* 1302 */       if (isModalBlocked()) {
/* 1303 */         this.modalBlocker.toFront_NoClientCode();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public void toBack()
/*      */   {
/* 1334 */     toBack_NoClientCode();
/*      */   }
/*      */   
/*      */ 
/*      */   final void toBack_NoClientCode()
/*      */   {
/* 1340 */     if (isAlwaysOnTop()) {
/*      */       try {
/* 1342 */         setAlwaysOnTop(false);
/*      */       }
/*      */       catch (SecurityException localSecurityException) {}
/*      */     }
/* 1346 */     if (this.visible) {
/* 1347 */       WindowPeer localWindowPeer = (WindowPeer)this.peer;
/* 1348 */       if (localWindowPeer != null) {
/* 1349 */         localWindowPeer.toBack();
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
/*      */   public Toolkit getToolkit()
/*      */   {
/* 1362 */     return Toolkit.getDefaultToolkit();
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
/*      */   public final String getWarningString()
/*      */   {
/* 1381 */     return this.warningString;
/*      */   }
/*      */   
/*      */   private void setWarningString() {
/* 1385 */     this.warningString = null;
/* 1386 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1387 */     if (localSecurityManager != null) {
/*      */       try {
/* 1389 */         localSecurityManager.checkPermission(SecurityConstants.AWT.TOPLEVEL_WINDOW_PERMISSION);
/*      */ 
/*      */       }
/*      */       catch (SecurityException localSecurityException)
/*      */       {
/* 1394 */         this.warningString = ((String)AccessController.doPrivileged(new GetPropertyAction("awt.appletWarning", "Java Applet Window")));
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
/*      */   public Locale getLocale()
/*      */   {
/* 1411 */     if (this.locale == null) {
/* 1412 */       return Locale.getDefault();
/*      */     }
/* 1414 */     return this.locale;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public InputContext getInputContext()
/*      */   {
/* 1424 */     synchronized (this.inputContextLock) {
/* 1425 */       if (this.inputContext == null) {
/* 1426 */         this.inputContext = InputContext.getInstance();
/*      */       }
/*      */     }
/* 1429 */     return this.inputContext;
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
/*      */   public void setCursor(Cursor paramCursor)
/*      */   {
/* 1447 */     if (paramCursor == null) {
/* 1448 */       paramCursor = Cursor.getPredefinedCursor(0);
/*      */     }
/* 1450 */     super.setCursor(paramCursor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Window getOwner()
/*      */   {
/* 1458 */     return getOwner_NoClientCode();
/*      */   }
/*      */   
/* 1461 */   final Window getOwner_NoClientCode() { return (Window)this.parent; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Window[] getOwnedWindows()
/*      */   {
/* 1470 */     return getOwnedWindows_NoClientCode();
/*      */   }
/*      */   
/*      */   final Window[] getOwnedWindows_NoClientCode() {
/*      */     Window[] arrayOfWindow1;
/* 1475 */     synchronized (this.ownedWindowList)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1481 */       int i = this.ownedWindowList.size();
/* 1482 */       int j = 0;
/* 1483 */       Window[] arrayOfWindow2 = new Window[i];
/*      */       
/* 1485 */       for (int k = 0; k < i; k++) {
/* 1486 */         arrayOfWindow2[j] = ((Window)((WeakReference)this.ownedWindowList.elementAt(k)).get());
/*      */         
/* 1488 */         if (arrayOfWindow2[j] != null) {
/* 1489 */           j++;
/*      */         }
/*      */       }
/*      */       
/* 1493 */       if (i != j) {
/* 1494 */         arrayOfWindow1 = (Window[])Arrays.copyOf(arrayOfWindow2, j);
/*      */       } else {
/* 1496 */         arrayOfWindow1 = arrayOfWindow2;
/*      */       }
/*      */     }
/*      */     
/* 1500 */     return arrayOfWindow1;
/*      */   }
/*      */   
/*      */   boolean isModalBlocked() {
/* 1504 */     return this.modalBlocker != null;
/*      */   }
/*      */   
/*      */   void setModalBlocked(Dialog paramDialog, boolean paramBoolean1, boolean paramBoolean2) {
/* 1508 */     this.modalBlocker = (paramBoolean1 ? paramDialog : null);
/* 1509 */     if (paramBoolean2) {
/* 1510 */       WindowPeer localWindowPeer = (WindowPeer)this.peer;
/* 1511 */       if (localWindowPeer != null) {
/* 1512 */         localWindowPeer.setModalBlocked(paramDialog, paramBoolean1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   Dialog getModalBlocker() {
/* 1518 */     return this.modalBlocker;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static IdentityArrayList<Window> getAllWindows()
/*      */   {
/* 1529 */     synchronized (allWindows) {
/* 1530 */       IdentityArrayList localIdentityArrayList = new IdentityArrayList();
/* 1531 */       localIdentityArrayList.addAll(allWindows);
/* 1532 */       return localIdentityArrayList;
/*      */     }
/*      */   }
/*      */   
/*      */   static IdentityArrayList<Window> getAllUnblockedWindows() {
/* 1537 */     synchronized (allWindows) {
/* 1538 */       IdentityArrayList localIdentityArrayList = new IdentityArrayList();
/* 1539 */       for (int i = 0; i < allWindows.size(); i++) {
/* 1540 */         Window localWindow = (Window)allWindows.get(i);
/* 1541 */         if (!localWindow.isModalBlocked()) {
/* 1542 */           localIdentityArrayList.add(localWindow);
/*      */         }
/*      */       }
/* 1545 */       return localIdentityArrayList;
/*      */     }
/*      */   }
/*      */   
/*      */   private static Window[] getWindows(AppContext paramAppContext) {
/* 1550 */     synchronized (Window.class)
/*      */     {
/*      */ 
/*      */ 
/* 1554 */       Vector localVector = (Vector)paramAppContext.get(Window.class);
/* 1555 */       Window[] arrayOfWindow1; if (localVector != null) {
/* 1556 */         int i = localVector.size();
/* 1557 */         int j = 0;
/* 1558 */         Window[] arrayOfWindow2 = new Window[i];
/* 1559 */         for (int k = 0; k < i; k++) {
/* 1560 */           Window localWindow = (Window)((WeakReference)localVector.get(k)).get();
/* 1561 */           if (localWindow != null) {
/* 1562 */             arrayOfWindow2[(j++)] = localWindow;
/*      */           }
/*      */         }
/* 1565 */         if (i != j) {
/* 1566 */           arrayOfWindow1 = (Window[])Arrays.copyOf(arrayOfWindow2, j);
/*      */         } else {
/* 1568 */           arrayOfWindow1 = arrayOfWindow2;
/*      */         }
/*      */       } else {
/* 1571 */         arrayOfWindow1 = new Window[0];
/*      */       }
/* 1573 */       return arrayOfWindow1;
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
/*      */   public static Window[] getWindows()
/*      */   {
/* 1595 */     return getWindows(AppContext.getAppContext());
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
/*      */   public static Window[] getOwnerlessWindows()
/*      */   {
/* 1617 */     Window[] arrayOfWindow1 = getWindows();
/*      */     
/* 1619 */     int i = 0;
/* 1620 */     for (Window localWindow1 : arrayOfWindow1) {
/* 1621 */       if (localWindow1.getOwner() == null) {
/* 1622 */         i++;
/*      */       }
/*      */     }
/*      */     
/* 1626 */     ??? = new Window[i];
/* 1627 */     ??? = 0;
/* 1628 */     for (Window localWindow2 : arrayOfWindow1) {
/* 1629 */       if (localWindow2.getOwner() == null) {
/* 1630 */         ???[(???++)] = localWindow2;
/*      */       }
/*      */     }
/*      */     
/* 1634 */     return (Window[])???;
/*      */   }
/*      */   
/*      */   Window getDocumentRoot() {
/* 1638 */     synchronized (getTreeLock()) {
/* 1639 */       Window localWindow = this;
/* 1640 */       while (localWindow.getOwner() != null) {
/* 1641 */         localWindow = localWindow.getOwner();
/*      */       }
/* 1643 */       return localWindow;
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
/*      */   public void setModalExclusionType(Dialog.ModalExclusionType paramModalExclusionType)
/*      */   {
/* 1671 */     if (paramModalExclusionType == null) {
/* 1672 */       paramModalExclusionType = Dialog.ModalExclusionType.NO_EXCLUDE;
/*      */     }
/* 1674 */     if (!Toolkit.getDefaultToolkit().isModalExclusionTypeSupported(paramModalExclusionType)) {
/* 1675 */       paramModalExclusionType = Dialog.ModalExclusionType.NO_EXCLUDE;
/*      */     }
/* 1677 */     if (this.modalExclusionType == paramModalExclusionType) {
/* 1678 */       return;
/*      */     }
/* 1680 */     if (paramModalExclusionType == Dialog.ModalExclusionType.TOOLKIT_EXCLUDE) {
/* 1681 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 1682 */       if (localSecurityManager != null) {
/* 1683 */         localSecurityManager.checkPermission(SecurityConstants.AWT.TOOLKIT_MODALITY_PERMISSION);
/*      */       }
/*      */     }
/* 1686 */     this.modalExclusionType = paramModalExclusionType;
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
/*      */   public Dialog.ModalExclusionType getModalExclusionType()
/*      */   {
/* 1711 */     return this.modalExclusionType;
/*      */   }
/*      */   
/*      */   boolean isModalExcluded(Dialog.ModalExclusionType paramModalExclusionType) {
/* 1715 */     if ((this.modalExclusionType != null) && 
/* 1716 */       (this.modalExclusionType.compareTo(paramModalExclusionType) >= 0))
/*      */     {
/* 1718 */       return true;
/*      */     }
/* 1720 */     Window localWindow = getOwner_NoClientCode();
/* 1721 */     return (localWindow != null) && (localWindow.isModalExcluded(paramModalExclusionType));
/*      */   }
/*      */   
/*      */   void updateChildrenBlocking() {
/* 1725 */     Vector localVector = new Vector();
/* 1726 */     Window[] arrayOfWindow = getOwnedWindows();
/* 1727 */     for (int i = 0; i < arrayOfWindow.length; i++) {
/* 1728 */       localVector.add(arrayOfWindow[i]);
/*      */     }
/* 1730 */     i = 0;
/* 1731 */     while (i < localVector.size()) {
/* 1732 */       Window localWindow = (Window)localVector.get(i);
/* 1733 */       if (localWindow.isVisible()) {
/* 1734 */         if (localWindow.isModalBlocked()) {
/* 1735 */           localObject = localWindow.getModalBlocker();
/* 1736 */           ((Dialog)localObject).unblockWindow(localWindow);
/*      */         }
/* 1738 */         Dialog.checkShouldBeBlocked(localWindow);
/* 1739 */         Object localObject = localWindow.getOwnedWindows();
/* 1740 */         for (int j = 0; j < localObject.length; j++) {
/* 1741 */           localVector.add(localObject[j]);
/*      */         }
/*      */       }
/* 1744 */       i++;
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
/*      */   public synchronized void addWindowListener(WindowListener paramWindowListener)
/*      */   {
/* 1760 */     if (paramWindowListener == null) {
/* 1761 */       return;
/*      */     }
/* 1763 */     this.newEventsOnly = true;
/* 1764 */     this.windowListener = AWTEventMulticaster.add(this.windowListener, paramWindowListener);
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
/*      */   public synchronized void addWindowStateListener(WindowStateListener paramWindowStateListener)
/*      */   {
/* 1780 */     if (paramWindowStateListener == null) {
/* 1781 */       return;
/*      */     }
/* 1783 */     this.windowStateListener = AWTEventMulticaster.add(this.windowStateListener, paramWindowStateListener);
/* 1784 */     this.newEventsOnly = true;
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
/*      */   public synchronized void addWindowFocusListener(WindowFocusListener paramWindowFocusListener)
/*      */   {
/* 1800 */     if (paramWindowFocusListener == null) {
/* 1801 */       return;
/*      */     }
/* 1803 */     this.windowFocusListener = AWTEventMulticaster.add(this.windowFocusListener, paramWindowFocusListener);
/* 1804 */     this.newEventsOnly = true;
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
/*      */   public synchronized void removeWindowListener(WindowListener paramWindowListener)
/*      */   {
/* 1819 */     if (paramWindowListener == null) {
/* 1820 */       return;
/*      */     }
/* 1822 */     this.windowListener = AWTEventMulticaster.remove(this.windowListener, paramWindowListener);
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
/*      */   public synchronized void removeWindowStateListener(WindowStateListener paramWindowStateListener)
/*      */   {
/* 1839 */     if (paramWindowStateListener == null) {
/* 1840 */       return;
/*      */     }
/* 1842 */     this.windowStateListener = AWTEventMulticaster.remove(this.windowStateListener, paramWindowStateListener);
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
/*      */   public synchronized void removeWindowFocusListener(WindowFocusListener paramWindowFocusListener)
/*      */   {
/* 1858 */     if (paramWindowFocusListener == null) {
/* 1859 */       return;
/*      */     }
/* 1861 */     this.windowFocusListener = AWTEventMulticaster.remove(this.windowFocusListener, paramWindowFocusListener);
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
/*      */   public synchronized WindowListener[] getWindowListeners()
/*      */   {
/* 1877 */     return (WindowListener[])getListeners(WindowListener.class);
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
/*      */   public synchronized WindowFocusListener[] getWindowFocusListeners()
/*      */   {
/* 1893 */     return (WindowFocusListener[])getListeners(WindowFocusListener.class);
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
/*      */   public synchronized WindowStateListener[] getWindowStateListeners()
/*      */   {
/* 1909 */     return (WindowStateListener[])getListeners(WindowStateListener.class);
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
/*      */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*      */   {
/* 1949 */     Object localObject = null;
/* 1950 */     if (paramClass == WindowFocusListener.class) {
/* 1951 */       localObject = this.windowFocusListener;
/* 1952 */     } else if (paramClass == WindowStateListener.class) {
/* 1953 */       localObject = this.windowStateListener;
/* 1954 */     } else if (paramClass == WindowListener.class) {
/* 1955 */       localObject = this.windowListener;
/*      */     } else {
/* 1957 */       return super.getListeners(paramClass);
/*      */     }
/* 1959 */     return AWTEventMulticaster.getListeners((EventListener)localObject, paramClass);
/*      */   }
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*      */   {
/* 1964 */     switch (paramAWTEvent.id) {
/*      */     case 200: 
/*      */     case 201: 
/*      */     case 202: 
/*      */     case 203: 
/*      */     case 204: 
/*      */     case 205: 
/*      */     case 206: 
/* 1972 */       if (((this.eventMask & 0x40) != 0L) || (this.windowListener != null))
/*      */       {
/* 1974 */         return true;
/*      */       }
/* 1976 */       return false;
/*      */     case 207: 
/*      */     case 208: 
/* 1979 */       if (((this.eventMask & 0x80000) != 0L) || (this.windowFocusListener != null))
/*      */       {
/* 1981 */         return true;
/*      */       }
/* 1983 */       return false;
/*      */     case 209: 
/* 1985 */       if (((this.eventMask & 0x40000) != 0L) || (this.windowStateListener != null))
/*      */       {
/* 1987 */         return true;
/*      */       }
/* 1989 */       return false;
/*      */     }
/*      */     
/*      */     
/* 1993 */     return super.eventEnabled(paramAWTEvent);
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
/*      */   protected void processEvent(AWTEvent paramAWTEvent)
/*      */   {
/* 2008 */     if ((paramAWTEvent instanceof WindowEvent)) {
/* 2009 */       switch (paramAWTEvent.getID()) {
/*      */       case 200: 
/*      */       case 201: 
/*      */       case 202: 
/*      */       case 203: 
/*      */       case 204: 
/*      */       case 205: 
/*      */       case 206: 
/* 2017 */         processWindowEvent((WindowEvent)paramAWTEvent);
/* 2018 */         break;
/*      */       case 207: 
/*      */       case 208: 
/* 2021 */         processWindowFocusEvent((WindowEvent)paramAWTEvent);
/* 2022 */         break;
/*      */       case 209: 
/* 2024 */         processWindowStateEvent((WindowEvent)paramAWTEvent);
/*      */       }
/*      */       
/* 2027 */       return;
/*      */     }
/* 2029 */     super.processEvent(paramAWTEvent);
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
/*      */   protected void processWindowEvent(WindowEvent paramWindowEvent)
/*      */   {
/* 2051 */     WindowListener localWindowListener = this.windowListener;
/* 2052 */     if (localWindowListener != null) {
/* 2053 */       switch (paramWindowEvent.getID()) {
/*      */       case 200: 
/* 2055 */         localWindowListener.windowOpened(paramWindowEvent);
/* 2056 */         break;
/*      */       case 201: 
/* 2058 */         localWindowListener.windowClosing(paramWindowEvent);
/* 2059 */         break;
/*      */       case 202: 
/* 2061 */         localWindowListener.windowClosed(paramWindowEvent);
/* 2062 */         break;
/*      */       case 203: 
/* 2064 */         localWindowListener.windowIconified(paramWindowEvent);
/* 2065 */         break;
/*      */       case 204: 
/* 2067 */         localWindowListener.windowDeiconified(paramWindowEvent);
/* 2068 */         break;
/*      */       case 205: 
/* 2070 */         localWindowListener.windowActivated(paramWindowEvent);
/* 2071 */         break;
/*      */       case 206: 
/* 2073 */         localWindowListener.windowDeactivated(paramWindowEvent);
/* 2074 */         break;
/*      */       }
/*      */       
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
/*      */   protected void processWindowFocusEvent(WindowEvent paramWindowEvent)
/*      */   {
/* 2101 */     WindowFocusListener localWindowFocusListener = this.windowFocusListener;
/* 2102 */     if (localWindowFocusListener != null) {
/* 2103 */       switch (paramWindowEvent.getID()) {
/*      */       case 207: 
/* 2105 */         localWindowFocusListener.windowGainedFocus(paramWindowEvent);
/* 2106 */         break;
/*      */       case 208: 
/* 2108 */         localWindowFocusListener.windowLostFocus(paramWindowEvent);
/* 2109 */         break;
/*      */       }
/*      */       
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
/*      */   protected void processWindowStateEvent(WindowEvent paramWindowEvent)
/*      */   {
/* 2137 */     WindowStateListener localWindowStateListener = this.windowStateListener;
/* 2138 */     if (localWindowStateListener != null) {
/* 2139 */       switch (paramWindowEvent.getID()) {
/*      */       case 209: 
/* 2141 */         localWindowStateListener.windowStateChanged(paramWindowEvent);
/* 2142 */         break;
/*      */       }
/*      */       
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
/*      */   void preProcessKeyEvent(KeyEvent paramKeyEvent)
/*      */   {
/* 2157 */     if ((paramKeyEvent.isActionKey()) && (paramKeyEvent.getKeyCode() == 112) && 
/* 2158 */       (paramKeyEvent.isControlDown()) && (paramKeyEvent.isShiftDown()) && 
/* 2159 */       (paramKeyEvent.getID() == 401)) {
/* 2160 */       list(System.out, 0);
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
/*      */   void postProcessKeyEvent(KeyEvent paramKeyEvent) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setAlwaysOnTop(boolean paramBoolean)
/*      */     throws SecurityException
/*      */   {
/* 2229 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2230 */     if (localSecurityManager != null) {
/* 2231 */       localSecurityManager.checkPermission(SecurityConstants.AWT.SET_WINDOW_ALWAYS_ON_TOP_PERMISSION);
/*      */     }
/*      */     
/*      */     boolean bool;
/* 2235 */     synchronized (this) {
/* 2236 */       bool = this.alwaysOnTop;
/* 2237 */       this.alwaysOnTop = paramBoolean;
/*      */     }
/* 2239 */     if (bool != paramBoolean) {
/* 2240 */       if (isAlwaysOnTopSupported()) {
/* 2241 */         ??? = (WindowPeer)this.peer;
/* 2242 */         synchronized (getTreeLock()) {
/* 2243 */           if (??? != null) {
/* 2244 */             ((WindowPeer)???).updateAlwaysOnTopState();
/*      */           }
/*      */         }
/*      */       }
/* 2248 */       firePropertyChange("alwaysOnTop", bool, paramBoolean);
/*      */     }
/* 2250 */     for (??? = this.ownedWindowList.iterator(); ((Iterator)???).hasNext();) { ??? = (WeakReference)((Iterator)???).next();
/* 2251 */       Window localWindow = (Window)((WeakReference)???).get();
/* 2252 */       if (localWindow != null) {
/*      */         try {
/* 2254 */           localWindow.setAlwaysOnTop(paramBoolean);
/*      */         }
/*      */         catch (SecurityException localSecurityException) {}
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
/*      */   public boolean isAlwaysOnTopSupported()
/*      */   {
/* 2277 */     return Toolkit.getDefaultToolkit().isAlwaysOnTopSupported();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isAlwaysOnTop()
/*      */   {
/* 2289 */     return this.alwaysOnTop;
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
/*      */   public Component getFocusOwner()
/*      */   {
/* 2305 */     return isFocused() ? KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() : null;
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
/*      */   public Component getMostRecentFocusOwner()
/*      */   {
/* 2327 */     if (isFocused()) {
/* 2328 */       return getFocusOwner();
/*      */     }
/*      */     
/* 2331 */     Component localComponent = KeyboardFocusManager.getMostRecentFocusOwner(this);
/* 2332 */     if (localComponent != null) {
/* 2333 */       return localComponent;
/*      */     }
/*      */     
/* 2336 */     return isFocusableWindow() ? getFocusTraversalPolicy().getInitialComponent(this) : null;
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
/*      */   public boolean isActive()
/*      */   {
/* 2355 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow() == this;
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
/*      */   public boolean isFocused()
/*      */   {
/* 2373 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getGlobalFocusedWindow() == this;
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
/*      */   public Set<AWTKeyStroke> getFocusTraversalKeys(int paramInt)
/*      */   {
/* 2405 */     if ((paramInt < 0) || (paramInt >= 4)) {
/* 2406 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2411 */     Set<AWTKeyStroke> localSet = this.focusTraversalKeys != null ? this.focusTraversalKeys[paramInt] : null;
/*      */     
/*      */ 
/*      */ 
/* 2415 */     if (localSet != null) {
/* 2416 */       return localSet;
/*      */     }
/*      */     
/* 2419 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(paramInt);
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
/*      */   public final void setFocusCycleRoot(boolean paramBoolean) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isFocusCycleRoot()
/*      */   {
/* 2447 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Container getFocusCycleRootAncestor()
/*      */   {
/* 2459 */     return null;
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
/*      */   public final boolean isFocusableWindow()
/*      */   {
/* 2485 */     if (!getFocusableWindowState()) {
/* 2486 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 2490 */     if (((this instanceof Frame)) || ((this instanceof Dialog))) {
/* 2491 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2496 */     if (getFocusTraversalPolicy().getDefaultComponent(this) == null) {
/* 2497 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2502 */     for (Window localWindow = getOwner(); localWindow != null; 
/* 2503 */         localWindow = localWindow.getOwner())
/*      */     {
/* 2505 */       if (((localWindow instanceof Frame)) || ((localWindow instanceof Dialog))) {
/* 2506 */         return localWindow.isShowing();
/*      */       }
/*      */     }
/*      */     
/* 2510 */     return false;
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
/*      */   public boolean getFocusableWindowState()
/*      */   {
/* 2534 */     return this.focusableWindowState;
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
/*      */   public void setFocusableWindowState(boolean paramBoolean)
/*      */   {
/*      */     boolean bool;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2569 */     synchronized (this) {
/* 2570 */       bool = this.focusableWindowState;
/* 2571 */       this.focusableWindowState = paramBoolean;
/*      */     }
/* 2573 */     ??? = (WindowPeer)this.peer;
/* 2574 */     if (??? != null) {
/* 2575 */       ((WindowPeer)???).updateFocusableWindowState();
/*      */     }
/* 2577 */     firePropertyChange("focusableWindowState", bool, paramBoolean);
/*      */     
/* 2579 */     if ((bool) && (!paramBoolean) && (isFocused())) {
/* 2580 */       for (Window localWindow = getOwner(); 
/* 2581 */           localWindow != null; 
/* 2582 */           localWindow = localWindow.getOwner())
/*      */       {
/*      */ 
/* 2585 */         Component localComponent = KeyboardFocusManager.getMostRecentFocusOwner(localWindow);
/* 2586 */         if ((localComponent != null) && (localComponent.requestFocus(false, CausedFocusEvent.Cause.ACTIVATION))) {
/* 2587 */           return;
/*      */         }
/*      */       }
/*      */       
/* 2591 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwnerPriv();
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
/*      */   public void setAutoRequestFocus(boolean paramBoolean)
/*      */   {
/* 2617 */     this.autoRequestFocus = paramBoolean;
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
/*      */   public boolean isAutoRequestFocus()
/*      */   {
/* 2632 */     return this.autoRequestFocus;
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
/*      */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 2671 */     super.addPropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 2712 */     super.addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*      */   public boolean isValidateRoot()
/*      */   {
/* 2727 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void dispatchEventImpl(AWTEvent paramAWTEvent)
/*      */   {
/* 2735 */     if (paramAWTEvent.getID() == 101) {
/* 2736 */       invalidate();
/* 2737 */       validate();
/*      */     }
/* 2739 */     super.dispatchEventImpl(paramAWTEvent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public boolean postEvent(Event paramEvent)
/*      */   {
/* 2748 */     if (handleEvent(paramEvent)) {
/* 2749 */       paramEvent.consume();
/* 2750 */       return true;
/*      */     }
/* 2752 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isShowing()
/*      */   {
/* 2760 */     return this.visible;
/*      */   }
/*      */   
/*      */   boolean isDisposing() {
/* 2764 */     return this.disposing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void applyResourceBundle(ResourceBundle paramResourceBundle)
/*      */   {
/* 2773 */     applyComponentOrientation(ComponentOrientation.getOrientation(paramResourceBundle));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void applyResourceBundle(String paramString)
/*      */   {
/* 2782 */     applyResourceBundle(ResourceBundle.getBundle(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void addOwnedWindow(WeakReference<Window> paramWeakReference)
/*      */   {
/* 2789 */     if (paramWeakReference != null) {
/* 2790 */       synchronized (this.ownedWindowList)
/*      */       {
/*      */ 
/* 2793 */         if (!this.ownedWindowList.contains(paramWeakReference)) {
/* 2794 */           this.ownedWindowList.addElement(paramWeakReference);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void removeOwnedWindow(WeakReference<Window> paramWeakReference) {
/* 2801 */     if (paramWeakReference != null)
/*      */     {
/*      */ 
/* 2804 */       this.ownedWindowList.removeElement(paramWeakReference);
/*      */     }
/*      */   }
/*      */   
/*      */   void connectOwnedWindow(Window paramWindow) {
/* 2809 */     paramWindow.parent = this;
/* 2810 */     addOwnedWindow(paramWindow.weakThis);
/* 2811 */     paramWindow.disposerRecord.updateOwner();
/*      */   }
/*      */   
/*      */   private void addToWindowList() {
/* 2815 */     synchronized (Window.class)
/*      */     {
/* 2817 */       Vector localVector = (Vector)this.appContext.get(Window.class);
/* 2818 */       if (localVector == null) {
/* 2819 */         localVector = new Vector();
/* 2820 */         this.appContext.put(Window.class, localVector);
/*      */       }
/* 2822 */       localVector.add(this.weakThis);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void removeFromWindowList(AppContext paramAppContext, WeakReference<Window> paramWeakReference) {
/* 2827 */     synchronized (Window.class)
/*      */     {
/* 2829 */       Vector localVector = (Vector)paramAppContext.get(Window.class);
/* 2830 */       if (localVector != null) {
/* 2831 */         localVector.remove(paramWeakReference);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void removeFromWindowList() {
/* 2837 */     removeFromWindowList(this.appContext, this.weakThis);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2845 */   private Type type = Type.NORMAL;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setType(Type paramType)
/*      */   {
/* 2860 */     if (paramType == null) {
/* 2861 */       throw new IllegalArgumentException("type should not be null.");
/*      */     }
/* 2863 */     synchronized (getTreeLock()) {
/* 2864 */       if (isDisplayable()) {
/* 2865 */         throw new IllegalComponentStateException("The window is displayable.");
/*      */       }
/*      */       
/* 2868 */       synchronized (getObjectLock()) {
/* 2869 */         this.type = paramType;
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
/* 2891 */   private int windowSerializedDataVersion = 2;
/*      */   
/*      */   /* Error */
/*      */   public Type getType()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 343	java/awt/Window:getObjectLock	()Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 33	java/awt/Window:type	Ljava/awt/Window$Type;
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: areturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #2881	-> byte code offset #0
/*      */     //   Java source line #2882	-> byte code offset #7
/*      */     //   Java source line #2883	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Window
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 2919 */     synchronized (this)
/*      */     {
/*      */ 
/* 2922 */       this.focusMgr = new FocusManager();
/* 2923 */       this.focusMgr.focusRoot = this;
/* 2924 */       this.focusMgr.focusOwner = getMostRecentFocusOwner();
/*      */       
/* 2926 */       paramObjectOutputStream.defaultWriteObject();
/*      */       
/*      */ 
/* 2929 */       this.focusMgr = null;
/*      */       
/* 2931 */       AWTEventMulticaster.save(paramObjectOutputStream, "windowL", this.windowListener);
/* 2932 */       AWTEventMulticaster.save(paramObjectOutputStream, "windowFocusL", this.windowFocusListener);
/* 2933 */       AWTEventMulticaster.save(paramObjectOutputStream, "windowStateL", this.windowStateListener);
/*      */     }
/*      */     
/* 2936 */     paramObjectOutputStream.writeObject(null);
/*      */     
/* 2938 */     synchronized (this.ownedWindowList) {
/* 2939 */       for (int i = 0; i < this.ownedWindowList.size(); i++) {
/* 2940 */         Window localWindow = (Window)((WeakReference)this.ownedWindowList.elementAt(i)).get();
/* 2941 */         if (localWindow != null) {
/* 2942 */           paramObjectOutputStream.writeObject("ownedL");
/* 2943 */           paramObjectOutputStream.writeObject(localWindow);
/*      */         }
/*      */       }
/*      */     }
/* 2947 */     paramObjectOutputStream.writeObject(null);
/*      */     
/*      */ 
/* 2950 */     if (this.icons != null) {
/* 2951 */       for (??? = this.icons.iterator(); ((Iterator)???).hasNext();) { Image localImage = (Image)((Iterator)???).next();
/* 2952 */         if ((localImage instanceof Serializable)) {
/* 2953 */           paramObjectOutputStream.writeObject(localImage);
/*      */         }
/*      */       }
/*      */     }
/* 2957 */     paramObjectOutputStream.writeObject(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initDeserializedWindow()
/*      */   {
/* 2965 */     setWarningString();
/* 2966 */     this.inputContextLock = new Object();
/*      */     
/*      */ 
/* 2969 */     this.visible = false;
/*      */     
/* 2971 */     this.weakThis = new WeakReference(this);
/*      */     
/* 2973 */     this.anchor = new Object();
/* 2974 */     this.disposerRecord = new WindowDisposerRecord(this.appContext, this);
/* 2975 */     Disposer.addRecord(this.anchor, this.disposerRecord);
/*      */     
/* 2977 */     addToWindowList();
/* 2978 */     initGC(null);
/* 2979 */     this.ownedWindowList = new Vector();
/*      */   }
/*      */   
/*      */   private void deserializeResources(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException, HeadlessException
/*      */   {
/* 2985 */     if (this.windowSerializedDataVersion < 2)
/*      */     {
/*      */ 
/*      */ 
/* 2989 */       if ((this.focusMgr != null) && 
/* 2990 */         (this.focusMgr.focusOwner != null))
/*      */       {
/* 2992 */         KeyboardFocusManager.setMostRecentFocusOwner(this, this.focusMgr.focusOwner);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2999 */       this.focusableWindowState = true;
/*      */     }
/*      */     
/*      */     Object localObject1;
/*      */     
/*      */     Object localObject2;
/* 3005 */     while (null != (localObject1 = paramObjectInputStream.readObject())) {
/* 3006 */       localObject2 = ((String)localObject1).intern();
/*      */       
/* 3008 */       if ("windowL" == localObject2) {
/* 3009 */         addWindowListener((WindowListener)paramObjectInputStream.readObject());
/* 3010 */       } else if ("windowFocusL" == localObject2) {
/* 3011 */         addWindowFocusListener((WindowFocusListener)paramObjectInputStream.readObject());
/* 3012 */       } else if ("windowStateL" == localObject2) {
/* 3013 */         addWindowStateListener((WindowStateListener)paramObjectInputStream.readObject());
/*      */       } else {
/* 3015 */         paramObjectInputStream.readObject();
/*      */       }
/*      */     }
/*      */     try {
/* 3019 */       while (null != (localObject1 = paramObjectInputStream.readObject())) {
/* 3020 */         localObject2 = ((String)localObject1).intern();
/*      */         
/* 3022 */         if ("ownedL" == localObject2) {
/* 3023 */           connectOwnedWindow((Window)paramObjectInputStream.readObject());
/*      */         }
/*      */         else {
/* 3026 */           paramObjectInputStream.readObject();
/*      */         }
/*      */       }
/*      */       
/* 3030 */       localObject2 = paramObjectInputStream.readObject();
/*      */       
/* 3032 */       this.icons = new ArrayList();
/*      */       
/* 3034 */       while (localObject2 != null) {
/* 3035 */         if ((localObject2 instanceof Image)) {
/* 3036 */           this.icons.add((Image)localObject2);
/*      */         }
/* 3038 */         localObject2 = paramObjectInputStream.readObject();
/*      */       }
/*      */     }
/*      */     catch (OptionalDataException localOptionalDataException) {}
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException, HeadlessException
/*      */   {
/* 3065 */     GraphicsEnvironment.checkHeadless();
/* 3066 */     initDeserializedWindow();
/* 3067 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*      */     
/* 3069 */     this.syncLWRequests = localGetField.get("syncLWRequests", systemSyncLWRequests);
/* 3070 */     this.state = localGetField.get("state", 0);
/* 3071 */     this.focusableWindowState = localGetField.get("focusableWindowState", true);
/* 3072 */     this.windowSerializedDataVersion = localGetField.get("windowSerializedDataVersion", 1);
/* 3073 */     this.locationByPlatform = localGetField.get("locationByPlatform", locationByPlatformProp);
/*      */     
/* 3075 */     this.focusMgr = ((FocusManager)localGetField.get("focusMgr", null));
/*      */     
/* 3077 */     Dialog.ModalExclusionType localModalExclusionType = (Dialog.ModalExclusionType)localGetField.get("modalExclusionType", Dialog.ModalExclusionType.NO_EXCLUDE);
/* 3078 */     setModalExclusionType(localModalExclusionType);
/* 3079 */     boolean bool = localGetField.get("alwaysOnTop", false);
/* 3080 */     if (bool) {
/* 3081 */       setAlwaysOnTop(bool);
/*      */     }
/* 3083 */     this.shape = ((Shape)localGetField.get("shape", null));
/* 3084 */     this.opacity = Float.valueOf(localGetField.get("opacity", 1.0F)).floatValue();
/*      */     
/* 3086 */     this.securityWarningWidth = 0;
/* 3087 */     this.securityWarningHeight = 0;
/* 3088 */     this.securityWarningPointX = 2.0D;
/* 3089 */     this.securityWarningPointY = 0.0D;
/* 3090 */     this.securityWarningAlignmentX = 1.0F;
/* 3091 */     this.securityWarningAlignmentY = 0.0F;
/*      */     
/* 3093 */     deserializeResources(paramObjectInputStream);
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
/*      */   public AccessibleContext getAccessibleContext()
/*      */   {
/* 3112 */     if (this.accessibleContext == null) {
/* 3113 */       this.accessibleContext = new AccessibleAWTWindow();
/*      */     }
/* 3115 */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */   protected class AccessibleAWTWindow extends Container.AccessibleAWTContainer
/*      */   {
/*      */     private static final long serialVersionUID = 4215068635060671780L;
/*      */     
/*      */     protected AccessibleAWTWindow()
/*      */     {
/* 3124 */       super();
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
/*      */ 
/*      */ 
/*      */     public AccessibleRole getAccessibleRole()
/*      */     {
/* 3139 */       return AccessibleRole.WINDOW;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public AccessibleStateSet getAccessibleStateSet()
/*      */     {
/* 3150 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/* 3151 */       if (Window.this.getFocusOwner() != null) {
/* 3152 */         localAccessibleStateSet.add(AccessibleState.ACTIVE);
/*      */       }
/* 3154 */       return localAccessibleStateSet;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   void setGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/* 3161 */     if (paramGraphicsConfiguration == null)
/*      */     {
/*      */ 
/*      */ 
/* 3165 */       paramGraphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
/*      */     }
/* 3167 */     synchronized (getTreeLock()) {
/* 3168 */       super.setGraphicsConfiguration(paramGraphicsConfiguration);
/* 3169 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 3170 */         log.finer("+ Window.setGraphicsConfiguration(): new GC is \n+ " + getGraphicsConfiguration_NoClientCode() + "\n+ this is " + this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLocationRelativeTo(Component paramComponent)
/*      */   {
/* 3233 */     int i = 0;int j = 0;
/*      */     
/* 3235 */     GraphicsConfiguration localGraphicsConfiguration = getGraphicsConfiguration_NoClientCode();
/* 3236 */     Rectangle localRectangle = localGraphicsConfiguration.getBounds();
/*      */     
/* 3238 */     Dimension localDimension = getSize();
/*      */     
/*      */ 
/* 3241 */     Window localWindow = SunToolkit.getContainingWindow(paramComponent);
/* 3242 */     Object localObject; Point localPoint; if ((paramComponent == null) || (localWindow == null)) {
/* 3243 */       localObject = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 3244 */       localGraphicsConfiguration = ((GraphicsEnvironment)localObject).getDefaultScreenDevice().getDefaultConfiguration();
/* 3245 */       localRectangle = localGraphicsConfiguration.getBounds();
/* 3246 */       localPoint = ((GraphicsEnvironment)localObject).getCenterPoint();
/* 3247 */       i = localPoint.x - localDimension.width / 2;
/* 3248 */       j = localPoint.y - localDimension.height / 2;
/* 3249 */     } else if (!paramComponent.isShowing()) {
/* 3250 */       localGraphicsConfiguration = localWindow.getGraphicsConfiguration();
/* 3251 */       localRectangle = localGraphicsConfiguration.getBounds();
/* 3252 */       i = localRectangle.x + (localRectangle.width - localDimension.width) / 2;
/* 3253 */       j = localRectangle.y + (localRectangle.height - localDimension.height) / 2;
/*      */     } else {
/* 3255 */       localGraphicsConfiguration = localWindow.getGraphicsConfiguration();
/* 3256 */       localRectangle = localGraphicsConfiguration.getBounds();
/* 3257 */       localObject = paramComponent.getSize();
/* 3258 */       localPoint = paramComponent.getLocationOnScreen();
/* 3259 */       i = localPoint.x + (((Dimension)localObject).width - localDimension.width) / 2;
/* 3260 */       j = localPoint.y + (((Dimension)localObject).height - localDimension.height) / 2;
/*      */       
/*      */ 
/* 3263 */       if (j + localDimension.height > localRectangle.y + localRectangle.height) {
/* 3264 */         j = localRectangle.y + localRectangle.height - localDimension.height;
/* 3265 */         if (localPoint.x - localRectangle.x + ((Dimension)localObject).width / 2 < localRectangle.width / 2) {
/* 3266 */           i = localPoint.x + ((Dimension)localObject).width;
/*      */         } else {
/* 3268 */           i = localPoint.x - localDimension.width;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3275 */     if (j + localDimension.height > localRectangle.y + localRectangle.height) {
/* 3276 */       j = localRectangle.y + localRectangle.height - localDimension.height;
/*      */     }
/*      */     
/* 3279 */     if (j < localRectangle.y) {
/* 3280 */       j = localRectangle.y;
/*      */     }
/*      */     
/* 3283 */     if (i + localDimension.width > localRectangle.x + localRectangle.width) {
/* 3284 */       i = localRectangle.x + localRectangle.width - localDimension.width;
/*      */     }
/*      */     
/* 3287 */     if (i < localRectangle.x) {
/* 3288 */       i = localRectangle.x;
/*      */     }
/*      */     
/* 3291 */     setLocation(i, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void deliverMouseWheelToAncestor(MouseWheelEvent paramMouseWheelEvent) {}
/*      */   
/*      */ 
/*      */ 
/*      */   boolean dispatchMouseWheelToAncestor(MouseWheelEvent paramMouseWheelEvent)
/*      */   {
/* 3303 */     return false;
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
/*      */   public void createBufferStrategy(int paramInt)
/*      */   {
/* 3325 */     super.createBufferStrategy(paramInt);
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
/*      */   public void createBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*      */     throws AWTException
/*      */   {
/* 3350 */     super.createBufferStrategy(paramInt, paramBufferCapabilities);
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
/*      */   public BufferStrategy getBufferStrategy()
/*      */   {
/* 3363 */     return super.getBufferStrategy();
/*      */   }
/*      */   
/*      */ 
/* 3367 */   Component getTemporaryLostComponent() { return this.temporaryLostComponent; }
/*      */   
/*      */   Component setTemporaryLostComponent(Component paramComponent) {
/* 3370 */     Component localComponent = this.temporaryLostComponent;
/*      */     
/*      */ 
/* 3373 */     if ((paramComponent == null) || (paramComponent.canBeFocusOwner())) {
/* 3374 */       this.temporaryLostComponent = paramComponent;
/*      */     } else {
/* 3376 */       this.temporaryLostComponent = null;
/*      */     }
/* 3378 */     return localComponent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean canContainFocusOwner(Component paramComponent)
/*      */   {
/* 3387 */     return (super.canContainFocusOwner(paramComponent)) && (isFocusableWindow());
/*      */   }
/*      */   
/* 3390 */   private boolean locationByPlatform = locationByPlatformProp;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLocationByPlatform(boolean paramBoolean)
/*      */   {
/* 3441 */     synchronized (getTreeLock()) {
/* 3442 */       if ((paramBoolean) && (isShowing())) {
/* 3443 */         throw new IllegalComponentStateException("The window is showing on screen.");
/*      */       }
/* 3445 */       this.locationByPlatform = paramBoolean;
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public boolean isLocationByPlatform()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 112	java/awt/Window:getTreeLock	()Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 36	java/awt/Window:locationByPlatform	Z
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: ireturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #3461	-> byte code offset #0
/*      */     //   Java source line #3462	-> byte code offset #7
/*      */     //   Java source line #3463	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Window
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 3490 */     synchronized (getTreeLock()) {
/* 3491 */       if ((getBoundsOp() == 1) || 
/* 3492 */         (getBoundsOp() == 3))
/*      */       {
/* 3494 */         this.locationByPlatform = false;
/*      */       }
/* 3496 */       super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public void setBounds(Rectangle paramRectangle)
/*      */   {
/* 3524 */     setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isRecursivelyVisible()
/*      */   {
/* 3535 */     return this.visible;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public float getOpacity()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 112	java/awt/Window:getTreeLock	()Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 10	java/awt/Window:opacity	F
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: freturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #3552	-> byte code offset #0
/*      */     //   Java source line #3553	-> byte code offset #7
/*      */     //   Java source line #3554	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Window
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   public void setOpacity(float paramFloat)
/*      */   {
/* 3607 */     synchronized (getTreeLock()) {
/* 3608 */       if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 3609 */         throw new IllegalArgumentException("The value of opacity should be in the range [0.0f .. 1.0f].");
/*      */       }
/*      */       
/* 3612 */       if (paramFloat < 1.0F) {
/* 3613 */         localObject1 = getGraphicsConfiguration();
/* 3614 */         GraphicsDevice localGraphicsDevice = ((GraphicsConfiguration)localObject1).getDevice();
/* 3615 */         if (((GraphicsConfiguration)localObject1).getDevice().getFullScreenWindow() == this) {
/* 3616 */           throw new IllegalComponentStateException("Setting opacity for full-screen window is not supported.");
/*      */         }
/*      */         
/* 3619 */         if (!localGraphicsDevice.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT))
/*      */         {
/*      */ 
/* 3622 */           throw new UnsupportedOperationException("TRANSLUCENT translucency is not supported.");
/*      */         }
/*      */       }
/*      */       
/* 3626 */       this.opacity = paramFloat;
/* 3627 */       Object localObject1 = (WindowPeer)getPeer();
/* 3628 */       if (localObject1 != null) {
/* 3629 */         ((WindowPeer)localObject1).setOpacity(paramFloat);
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
/*      */   public Shape getShape()
/*      */   {
/* 3650 */     synchronized (getTreeLock()) {
/* 3651 */       return this.shape == null ? null : new Path2D.Float(this.shape);
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
/*      */   public void setShape(Shape paramShape)
/*      */   {
/* 3705 */     synchronized (getTreeLock()) {
/* 3706 */       if (paramShape != null) {
/* 3707 */         localObject1 = getGraphicsConfiguration();
/* 3708 */         GraphicsDevice localGraphicsDevice = ((GraphicsConfiguration)localObject1).getDevice();
/* 3709 */         if (((GraphicsConfiguration)localObject1).getDevice().getFullScreenWindow() == this) {
/* 3710 */           throw new IllegalComponentStateException("Setting shape for full-screen window is not supported.");
/*      */         }
/*      */         
/* 3713 */         if (!localGraphicsDevice.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT))
/*      */         {
/*      */ 
/* 3716 */           throw new UnsupportedOperationException("PERPIXEL_TRANSPARENT translucency is not supported.");
/*      */         }
/*      */       }
/*      */       
/* 3720 */       this.shape = (paramShape == null ? null : new Path2D.Float(paramShape));
/* 3721 */       Object localObject1 = (WindowPeer)getPeer();
/* 3722 */       if (localObject1 != null) {
/* 3723 */         ((WindowPeer)localObject1).applyShape(paramShape == null ? null : Region.getInstance(paramShape, null));
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
/*      */   public Color getBackground()
/*      */   {
/* 3742 */     return super.getBackground();
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
/*      */   public void setBackground(Color paramColor)
/*      */   {
/* 3822 */     Color localColor = getBackground();
/* 3823 */     super.setBackground(paramColor);
/* 3824 */     if ((localColor != null) && (localColor.equals(paramColor))) {
/* 3825 */       return;
/*      */     }
/* 3827 */     int i = localColor != null ? localColor.getAlpha() : 255;
/* 3828 */     int j = paramColor != null ? paramColor.getAlpha() : 255;
/* 3829 */     if ((i == 255) && (j < 255)) {
/* 3830 */       localObject = getGraphicsConfiguration();
/* 3831 */       GraphicsDevice localGraphicsDevice = ((GraphicsConfiguration)localObject).getDevice();
/* 3832 */       if (((GraphicsConfiguration)localObject).getDevice().getFullScreenWindow() == this) {
/* 3833 */         throw new IllegalComponentStateException("Making full-screen window non opaque is not supported.");
/*      */       }
/*      */       
/* 3836 */       if (!((GraphicsConfiguration)localObject).isTranslucencyCapable()) {
/* 3837 */         GraphicsConfiguration localGraphicsConfiguration = localGraphicsDevice.getTranslucencyCapableGC();
/* 3838 */         if (localGraphicsConfiguration == null) {
/* 3839 */           throw new UnsupportedOperationException("PERPIXEL_TRANSLUCENT translucency is not supported");
/*      */         }
/*      */         
/* 3842 */         setGraphicsConfiguration(localGraphicsConfiguration);
/*      */       }
/* 3844 */       setLayersOpaque(this, false);
/* 3845 */     } else if ((i < 255) && (j == 255)) {
/* 3846 */       setLayersOpaque(this, true);
/*      */     }
/* 3848 */     Object localObject = (WindowPeer)getPeer();
/* 3849 */     if (localObject != null) {
/* 3850 */       ((WindowPeer)localObject).setOpaque(j == 255);
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
/*      */   public boolean isOpaque()
/*      */   {
/* 3869 */     Color localColor = getBackground();
/* 3870 */     return localColor.getAlpha() == 255;
/*      */   }
/*      */   
/*      */   private void updateWindow() {
/* 3874 */     synchronized (getTreeLock()) {
/* 3875 */       WindowPeer localWindowPeer = (WindowPeer)getPeer();
/* 3876 */       if (localWindowPeer != null) {
/* 3877 */         localWindowPeer.updateWindow();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void paint(Graphics paramGraphics)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 447	java/awt/Window:isOpaque	()Z
/*      */     //   4: ifne +62 -> 66
/*      */     //   7: aload_1
/*      */     //   8: invokevirtual 448	java/awt/Graphics:create	()Ljava/awt/Graphics;
/*      */     //   11: astore_2
/*      */     //   12: aload_2
/*      */     //   13: instanceof 449
/*      */     //   16: ifeq +36 -> 52
/*      */     //   19: aload_2
/*      */     //   20: aload_0
/*      */     //   21: invokevirtual 436	java/awt/Window:getBackground	()Ljava/awt/Color;
/*      */     //   24: invokevirtual 450	java/awt/Graphics:setColor	(Ljava/awt/Color;)V
/*      */     //   27: aload_2
/*      */     //   28: checkcast 449	java/awt/Graphics2D
/*      */     //   31: iconst_2
/*      */     //   32: invokestatic 452	java/awt/AlphaComposite:getInstance	(I)Ljava/awt/AlphaComposite;
/*      */     //   35: invokevirtual 453	java/awt/Graphics2D:setComposite	(Ljava/awt/Composite;)V
/*      */     //   38: aload_2
/*      */     //   39: iconst_0
/*      */     //   40: iconst_0
/*      */     //   41: aload_0
/*      */     //   42: invokevirtual 454	java/awt/Window:getWidth	()I
/*      */     //   45: aload_0
/*      */     //   46: invokevirtual 455	java/awt/Window:getHeight	()I
/*      */     //   49: invokevirtual 456	java/awt/Graphics:fillRect	(IIII)V
/*      */     //   52: aload_2
/*      */     //   53: invokevirtual 457	java/awt/Graphics:dispose	()V
/*      */     //   56: goto +10 -> 66
/*      */     //   59: astore_3
/*      */     //   60: aload_2
/*      */     //   61: invokevirtual 457	java/awt/Graphics:dispose	()V
/*      */     //   64: aload_3
/*      */     //   65: athrow
/*      */     //   66: aload_0
/*      */     //   67: aload_1
/*      */     //   68: invokespecial 458	java/awt/Container:paint	(Ljava/awt/Graphics;)V
/*      */     //   71: return
/*      */     // Line number table:
/*      */     //   Java source line #3889	-> byte code offset #0
/*      */     //   Java source line #3890	-> byte code offset #7
/*      */     //   Java source line #3892	-> byte code offset #12
/*      */     //   Java source line #3893	-> byte code offset #19
/*      */     //   Java source line #3894	-> byte code offset #27
/*      */     //   Java source line #3895	-> byte code offset #38
/*      */     //   Java source line #3898	-> byte code offset #52
/*      */     //   Java source line #3899	-> byte code offset #56
/*      */     //   Java source line #3898	-> byte code offset #59
/*      */     //   Java source line #3901	-> byte code offset #66
/*      */     //   Java source line #3902	-> byte code offset #71
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	72	0	this	Window
/*      */     //   0	72	1	paramGraphics	Graphics
/*      */     //   11	50	2	localGraphics	Graphics
/*      */     //   59	6	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   12	52	59	finally
/*      */   }
/*      */   
/*      */   private static void setLayersOpaque(Component paramComponent, boolean paramBoolean)
/*      */   {
/* 3907 */     if (SunToolkit.isInstanceOf(paramComponent, "javax.swing.RootPaneContainer")) {
/* 3908 */       RootPaneContainer localRootPaneContainer = (RootPaneContainer)paramComponent;
/* 3909 */       JRootPane localJRootPane = localRootPaneContainer.getRootPane();
/* 3910 */       JLayeredPane localJLayeredPane = localJRootPane.getLayeredPane();
/* 3911 */       Container localContainer = localJRootPane.getContentPane();
/* 3912 */       Object localObject = (localContainer instanceof JComponent) ? (JComponent)localContainer : null;
/*      */       
/* 3914 */       localJLayeredPane.setOpaque(paramBoolean);
/* 3915 */       localJRootPane.setOpaque(paramBoolean);
/* 3916 */       if (localObject != null) {
/* 3917 */         ((JComponent)localObject).setOpaque(paramBoolean);
/*      */         
/*      */ 
/*      */ 
/* 3921 */         int i = ((JComponent)localObject).getComponentCount();
/* 3922 */         if (i > 0) {
/* 3923 */           Component localComponent = ((JComponent)localObject).getComponent(0);
/*      */           
/*      */ 
/* 3926 */           if ((localComponent instanceof RootPaneContainer)) {
/* 3927 */             setLayersOpaque(localComponent, paramBoolean);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final Container getContainer()
/*      */   {
/* 3940 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void applyCompoundShape(Region paramRegion) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void applyCurrentShape() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void mixOnReshaping() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final Point getLocationOnWindow()
/*      */   {
/* 3967 */     return new Point(0, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static double limit(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/* 3976 */     paramDouble1 = Math.max(paramDouble1, paramDouble2);
/* 3977 */     paramDouble1 = Math.min(paramDouble1, paramDouble3);
/* 3978 */     return paramDouble1;
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
/*      */   private Point2D calculateSecurityWarningPosition(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 3996 */     double d1 = paramDouble1 + paramDouble3 * this.securityWarningAlignmentX + this.securityWarningPointX;
/* 3997 */     double d2 = paramDouble2 + paramDouble4 * this.securityWarningAlignmentY + this.securityWarningPointY;
/*      */     
/*      */ 
/* 4000 */     d1 = limit(d1, paramDouble1 - this.securityWarningWidth - 2.0D, paramDouble1 + paramDouble3 + 2.0D);
/*      */     
/*      */ 
/* 4003 */     d2 = limit(d2, paramDouble2 - this.securityWarningHeight - 2.0D, paramDouble2 + paramDouble4 + 2.0D);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4009 */     GraphicsConfiguration localGraphicsConfiguration = getGraphicsConfiguration_NoClientCode();
/* 4010 */     Rectangle localRectangle = localGraphicsConfiguration.getBounds();
/*      */     
/* 4012 */     Insets localInsets = Toolkit.getDefaultToolkit().getScreenInsets(localGraphicsConfiguration);
/*      */     
/* 4014 */     d1 = limit(d1, localRectangle.x + localInsets.left, localRectangle.x + localRectangle.width - localInsets.right - this.securityWarningWidth);
/*      */     
/*      */ 
/*      */ 
/* 4018 */     d2 = limit(d2, localRectangle.y + localInsets.top, localRectangle.y + localRectangle.height - localInsets.bottom - this.securityWarningHeight);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4023 */     return new Point2D.Double(d1, d2);
/*      */   }
/*      */   
/*      */   void updateZOrder() {}
/*      */   
/*      */   static
/*      */   {
/*  397 */     Toolkit.loadLibraries();
/*  398 */     if (!GraphicsEnvironment.isHeadless()) {
/*  399 */       initIDs();
/*      */     }
/*      */     
/*  402 */     String str = (String)AccessController.doPrivileged(new GetPropertyAction("java.awt.syncLWRequests"));
/*      */     
/*  404 */     systemSyncLWRequests = (str != null) && (str.equals("true"));
/*  405 */     str = (String)AccessController.doPrivileged(new GetPropertyAction("java.awt.Window.locationByPlatform"));
/*      */     
/*  407 */     locationByPlatformProp = (str != null) && (str.equals("true"));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  963 */     beforeFirstWindowShown = new AtomicBoolean(true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4027 */     AWTAccessor.setWindowAccessor(new AWTAccessor.WindowAccessor() {
/*      */       public float getOpacity(Window paramAnonymousWindow) {
/* 4029 */         return paramAnonymousWindow.opacity;
/*      */       }
/*      */       
/* 4032 */       public void setOpacity(Window paramAnonymousWindow, float paramAnonymousFloat) { paramAnonymousWindow.setOpacity(paramAnonymousFloat); }
/*      */       
/*      */       public Shape getShape(Window paramAnonymousWindow) {
/* 4035 */         return paramAnonymousWindow.getShape();
/*      */       }
/*      */       
/* 4038 */       public void setShape(Window paramAnonymousWindow, Shape paramAnonymousShape) { paramAnonymousWindow.setShape(paramAnonymousShape); }
/*      */       
/*      */       public void setOpaque(Window paramAnonymousWindow, boolean paramAnonymousBoolean) {
/* 4041 */         Color localColor = paramAnonymousWindow.getBackground();
/* 4042 */         if (localColor == null) {
/* 4043 */           localColor = new Color(0, 0, 0, 0);
/*      */         }
/* 4045 */         paramAnonymousWindow.setBackground(new Color(localColor.getRed(), localColor.getGreen(), localColor.getBlue(), paramAnonymousBoolean ? 255 : 0));
/*      */       }
/*      */       
/*      */       public void updateWindow(Window paramAnonymousWindow) {
/* 4049 */         paramAnonymousWindow.updateWindow();
/*      */       }
/*      */       
/*      */       public Dimension getSecurityWarningSize(Window paramAnonymousWindow)
/*      */       {
/* 4054 */         return new Dimension(paramAnonymousWindow.securityWarningWidth, paramAnonymousWindow.securityWarningHeight);
/*      */       }
/*      */       
/*      */       public void setSecurityWarningSize(Window paramAnonymousWindow, int paramAnonymousInt1, int paramAnonymousInt2)
/*      */       {
/* 4059 */         paramAnonymousWindow.securityWarningWidth = paramAnonymousInt1;
/* 4060 */         paramAnonymousWindow.securityWarningHeight = paramAnonymousInt2;
/*      */       }
/*      */       
/*      */ 
/*      */       public void setSecurityWarningPosition(Window paramAnonymousWindow, Point2D paramAnonymousPoint2D, float paramAnonymousFloat1, float paramAnonymousFloat2)
/*      */       {
/* 4066 */         paramAnonymousWindow.securityWarningPointX = paramAnonymousPoint2D.getX();
/* 4067 */         paramAnonymousWindow.securityWarningPointY = paramAnonymousPoint2D.getY();
/* 4068 */         paramAnonymousWindow.securityWarningAlignmentX = paramAnonymousFloat1;
/* 4069 */         paramAnonymousWindow.securityWarningAlignmentY = paramAnonymousFloat2;
/*      */         
/* 4071 */         synchronized (paramAnonymousWindow.getTreeLock()) {
/* 4072 */           WindowPeer localWindowPeer = (WindowPeer)paramAnonymousWindow.getPeer();
/* 4073 */           if (localWindowPeer != null) {
/* 4074 */             localWindowPeer.repositionSecurityWarning();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       public Point2D calculateSecurityWarningPosition(Window paramAnonymousWindow, double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4)
/*      */       {
/* 4082 */         return paramAnonymousWindow.calculateSecurityWarningPosition(paramAnonymousDouble1, paramAnonymousDouble2, paramAnonymousDouble3, paramAnonymousDouble4);
/*      */       }
/*      */       
/*      */       public void setLWRequestStatus(Window paramAnonymousWindow, boolean paramAnonymousBoolean) {
/* 4086 */         paramAnonymousWindow.syncLWRequests = paramAnonymousBoolean;
/*      */       }
/*      */       
/*      */       public boolean isAutoRequestFocus(Window paramAnonymousWindow) {
/* 4090 */         return paramAnonymousWindow.autoRequestFocus;
/*      */       }
/*      */       
/*      */       public boolean isTrayIconWindow(Window paramAnonymousWindow) {
/* 4094 */         return paramAnonymousWindow.isTrayIconWindow;
/*      */       }
/*      */       
/*      */       public void setTrayIconWindow(Window paramAnonymousWindow, boolean paramAnonymousBoolean) {
/* 4098 */         paramAnonymousWindow.isTrayIconWindow = paramAnonymousBoolean;
/*      */       }
/*      */     });
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */