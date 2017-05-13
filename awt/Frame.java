/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.peer.FramePeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.FrameAccessor;
/*      */ import sun.awt.SunToolkit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Frame
/*      */   extends Window
/*      */   implements MenuContainer
/*      */ {
/*      */   @Deprecated
/*      */   public static final int DEFAULT_CURSOR = 0;
/*      */   @Deprecated
/*      */   public static final int CROSSHAIR_CURSOR = 1;
/*      */   @Deprecated
/*      */   public static final int TEXT_CURSOR = 2;
/*      */   @Deprecated
/*      */   public static final int WAIT_CURSOR = 3;
/*      */   @Deprecated
/*      */   public static final int SW_RESIZE_CURSOR = 4;
/*      */   @Deprecated
/*      */   public static final int SE_RESIZE_CURSOR = 5;
/*      */   @Deprecated
/*      */   public static final int NW_RESIZE_CURSOR = 6;
/*      */   @Deprecated
/*      */   public static final int NE_RESIZE_CURSOR = 7;
/*      */   @Deprecated
/*      */   public static final int N_RESIZE_CURSOR = 8;
/*      */   @Deprecated
/*      */   public static final int S_RESIZE_CURSOR = 9;
/*      */   @Deprecated
/*      */   public static final int W_RESIZE_CURSOR = 10;
/*      */   @Deprecated
/*      */   public static final int E_RESIZE_CURSOR = 11;
/*      */   @Deprecated
/*      */   public static final int HAND_CURSOR = 12;
/*      */   @Deprecated
/*      */   public static final int MOVE_CURSOR = 13;
/*      */   public static final int NORMAL = 0;
/*      */   public static final int ICONIFIED = 1;
/*      */   public static final int MAXIMIZED_HORIZ = 2;
/*      */   public static final int MAXIMIZED_VERT = 4;
/*      */   public static final int MAXIMIZED_BOTH = 6;
/*      */   Rectangle maximizedBounds;
/*  301 */   String title = "Untitled";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MenuBar menuBar;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  322 */   boolean resizable = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  336 */   boolean undecorated = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  343 */   boolean mbManagement = false;
/*      */   
/*      */ 
/*      */ 
/*  347 */   private int state = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   Vector<Window> ownedWindows;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String base = "frame";
/*      */   
/*      */ 
/*      */ 
/*  359 */   private static int nameCounter = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 2673458971256075116L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Frame()
/*      */     throws HeadlessException
/*      */   {
/*  385 */     this("");
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
/*      */   public Frame(GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  404 */     this("", paramGraphicsConfiguration);
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
/*      */   public Frame(String paramString)
/*      */     throws HeadlessException
/*      */   {
/*  421 */     init(paramString, null);
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
/*      */   public Frame(String paramString, GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  446 */     super(paramGraphicsConfiguration);
/*  447 */     init(paramString, paramGraphicsConfiguration);
/*      */   }
/*      */   
/*      */   private void init(String paramString, GraphicsConfiguration paramGraphicsConfiguration) {
/*  451 */     this.title = paramString;
/*  452 */     SunToolkit.checkAndSetPolicy(this);
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
/*      */   public void addNotify()
/*      */   {
/*  475 */     synchronized (getTreeLock()) {
/*  476 */       if (this.peer == null) {
/*  477 */         this.peer = getToolkit().createFrame(this);
/*      */       }
/*  479 */       FramePeer localFramePeer = (FramePeer)this.peer;
/*  480 */       MenuBar localMenuBar = this.menuBar;
/*  481 */       if (localMenuBar != null) {
/*  482 */         this.mbManagement = true;
/*  483 */         localMenuBar.addNotify();
/*  484 */         localFramePeer.setMenuBar(localMenuBar);
/*      */       }
/*  486 */       localFramePeer.setMaximizedBounds(this.maximizedBounds);
/*  487 */       super.addNotify();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTitle()
/*      */   {
/*  499 */     return this.title;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTitle(String paramString)
/*      */   {
/*  510 */     String str = this.title;
/*  511 */     if (paramString == null) {
/*  512 */       paramString = "";
/*      */     }
/*      */     
/*      */ 
/*  516 */     synchronized (this) {
/*  517 */       this.title = paramString;
/*  518 */       FramePeer localFramePeer = (FramePeer)this.peer;
/*  519 */       if (localFramePeer != null) {
/*  520 */         localFramePeer.setTitle(paramString);
/*      */       }
/*      */     }
/*  523 */     firePropertyChange("title", str, paramString);
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
/*      */   public Image getIconImage()
/*      */   {
/*  542 */     List localList = this.icons;
/*  543 */     if ((localList != null) && 
/*  544 */       (localList.size() > 0)) {
/*  545 */       return (Image)localList.get(0);
/*      */     }
/*      */     
/*  548 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setIconImage(Image paramImage)
/*      */   {
/*  555 */     super.setIconImage(paramImage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MenuBar getMenuBar()
/*      */   {
/*  565 */     return this.menuBar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMenuBar(MenuBar paramMenuBar)
/*      */   {
/*  576 */     synchronized (getTreeLock()) {
/*  577 */       if (this.menuBar == paramMenuBar) {
/*  578 */         return;
/*      */       }
/*  580 */       if ((paramMenuBar != null) && (paramMenuBar.parent != null)) {
/*  581 */         paramMenuBar.parent.remove(paramMenuBar);
/*      */       }
/*  583 */       if (this.menuBar != null) {
/*  584 */         remove(this.menuBar);
/*      */       }
/*  586 */       this.menuBar = paramMenuBar;
/*  587 */       if (this.menuBar != null) {
/*  588 */         this.menuBar.parent = this;
/*      */         
/*  590 */         FramePeer localFramePeer = (FramePeer)this.peer;
/*  591 */         if (localFramePeer != null) {
/*  592 */           this.mbManagement = true;
/*  593 */           this.menuBar.addNotify();
/*  594 */           invalidateIfValid();
/*  595 */           localFramePeer.setMenuBar(this.menuBar);
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
/*      */   public boolean isResizable()
/*      */   {
/*  609 */     return this.resizable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setResizable(boolean paramBoolean)
/*      */   {
/*  619 */     boolean bool = this.resizable;
/*  620 */     int i = 0;
/*      */     
/*  622 */     synchronized (this) {
/*  623 */       this.resizable = paramBoolean;
/*  624 */       FramePeer localFramePeer = (FramePeer)this.peer;
/*  625 */       if (localFramePeer != null) {
/*  626 */         localFramePeer.setResizable(paramBoolean);
/*  627 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  635 */     if (i != 0) {
/*  636 */       invalidateIfValid();
/*      */     }
/*  638 */     firePropertyChange("resizable", bool, paramBoolean);
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
/*      */   public synchronized void setState(int paramInt)
/*      */   {
/*  690 */     int i = getExtendedState();
/*  691 */     if ((paramInt == 1) && ((i & 0x1) == 0)) {
/*  692 */       setExtendedState(i | 0x1);
/*      */     }
/*  694 */     else if ((paramInt == 0) && ((i & 0x1) != 0)) {
/*  695 */       setExtendedState(i & 0xFFFFFFFE);
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
/*      */   public void setExtendedState(int paramInt)
/*      */   {
/*  743 */     if (!isFrameStateSupported(paramInt)) {
/*  744 */       return;
/*      */     }
/*  746 */     synchronized (getObjectLock()) {
/*  747 */       this.state = paramInt;
/*      */     }
/*      */     
/*      */ 
/*  751 */     ??? = (FramePeer)this.peer;
/*  752 */     if (??? != null)
/*  753 */       ((FramePeer)???).setState(paramInt);
/*      */   }
/*      */   
/*      */   private boolean isFrameStateSupported(int paramInt) {
/*  757 */     if (!getToolkit().isFrameStateSupported(paramInt))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  762 */       if (((paramInt & 0x1) != 0) && 
/*  763 */         (!getToolkit().isFrameStateSupported(1))) {
/*  764 */         return false;
/*      */       }
/*  766 */       paramInt &= 0xFFFFFFFE;
/*      */       
/*  768 */       return getToolkit().isFrameStateSupported(paramInt);
/*      */     }
/*  770 */     return true;
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
/*      */   public synchronized int getState()
/*      */   {
/*  790 */     return (getExtendedState() & 0x1) != 0 ? 1 : 0;
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  368 */     Toolkit.loadLibraries();
/*  369 */     if (!GraphicsEnvironment.isHeadless()) {
/*  370 */       initIDs();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  819 */     AWTAccessor.setFrameAccessor(new AWTAccessor.FrameAccessor()
/*      */     {
/*      */       public void setExtendedState(Frame paramAnonymousFrame, int paramAnonymousInt) {
/*  822 */         synchronized (paramAnonymousFrame.getObjectLock()) {
/*  823 */           paramAnonymousFrame.state = paramAnonymousInt;
/*      */         }
/*      */       }
/*      */       
/*      */       /* Error */
/*      */       public int getExtendedState(Frame paramAnonymousFrame)
/*      */       {
/*      */         // Byte code:
/*      */         //   0: aload_1
/*      */         //   1: invokevirtual 2	java/awt/Frame:getObjectLock	()Ljava/lang/Object;
/*      */         //   4: dup
/*      */         //   5: astore_2
/*      */         //   6: monitorenter
/*      */         //   7: aload_1
/*      */         //   8: invokestatic 4	java/awt/Frame:access$000	(Ljava/awt/Frame;)I
/*      */         //   11: aload_2
/*      */         //   12: monitorexit
/*      */         //   13: ireturn
/*      */         //   14: astore_3
/*      */         //   15: aload_2
/*      */         //   16: monitorexit
/*      */         //   17: aload_3
/*      */         //   18: athrow
/*      */         // Line number table:
/*      */         //   Java source line #827	-> byte code offset #0
/*      */         //   Java source line #828	-> byte code offset #7
/*      */         //   Java source line #829	-> byte code offset #14
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	signature
/*      */         //   0	19	0	this	1
/*      */         //   0	19	1	paramAnonymousFrame	Frame
/*      */         //   5	11	2	Ljava/lang/Object;	Object
/*      */         //   14	4	3	localObject1	Object
/*      */         // Exception table:
/*      */         //   from	to	target	type
/*      */         //   7	13	14	finally
/*      */         //   14	17	14	finally
/*      */       }
/*      */       
/*      */       /* Error */
/*      */       public Rectangle getMaximizedBounds(Frame paramAnonymousFrame)
/*      */       {
/*      */         // Byte code:
/*      */         //   0: aload_1
/*      */         //   1: invokevirtual 2	java/awt/Frame:getObjectLock	()Ljava/lang/Object;
/*      */         //   4: dup
/*      */         //   5: astore_2
/*      */         //   6: monitorenter
/*      */         //   7: aload_1
/*      */         //   8: getfield 5	java/awt/Frame:maximizedBounds	Ljava/awt/Rectangle;
/*      */         //   11: aload_2
/*      */         //   12: monitorexit
/*      */         //   13: areturn
/*      */         //   14: astore_3
/*      */         //   15: aload_2
/*      */         //   16: monitorexit
/*      */         //   17: aload_3
/*      */         //   18: athrow
/*      */         // Line number table:
/*      */         //   Java source line #832	-> byte code offset #0
/*      */         //   Java source line #833	-> byte code offset #7
/*      */         //   Java source line #834	-> byte code offset #14
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	signature
/*      */         //   0	19	0	this	1
/*      */         //   0	19	1	paramAnonymousFrame	Frame
/*      */         //   5	11	2	Ljava/lang/Object;	Object
/*      */         //   14	4	3	localObject1	Object
/*      */         // Exception table:
/*      */         //   from	to	target	type
/*      */         //   7	13	14	finally
/*      */         //   14	17	14	finally
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public void setMaximizedBounds(Rectangle paramRectangle)
/*      */   {
/*  864 */     synchronized (getObjectLock()) {
/*  865 */       this.maximizedBounds = paramRectangle;
/*      */     }
/*  867 */     ??? = (FramePeer)this.peer;
/*  868 */     if (??? != null) {
/*  869 */       ((FramePeer)???).setMaximizedBounds(paramRectangle);
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
/*      */   public void setUndecorated(boolean paramBoolean)
/*      */   {
/*  921 */     synchronized (getTreeLock()) {
/*  922 */       if (isDisplayable()) {
/*  923 */         throw new IllegalComponentStateException("The frame is displayable.");
/*      */       }
/*  925 */       if (!paramBoolean) {
/*  926 */         if (getOpacity() < 1.0F) {
/*  927 */           throw new IllegalComponentStateException("The frame is not opaque");
/*      */         }
/*  929 */         if (getShape() != null) {
/*  930 */           throw new IllegalComponentStateException("The frame does not have a default shape");
/*      */         }
/*  932 */         Color localColor = getBackground();
/*  933 */         if ((localColor != null) && (localColor.getAlpha() < 255)) {
/*  934 */           throw new IllegalComponentStateException("The frame background color is not opaque");
/*      */         }
/*      */       }
/*  937 */       this.undecorated = paramBoolean;
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
/*      */   public boolean isUndecorated()
/*      */   {
/*  950 */     return this.undecorated;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setOpacity(float paramFloat)
/*      */   {
/*  958 */     synchronized (getTreeLock()) {
/*  959 */       if ((paramFloat < 1.0F) && (!isUndecorated())) {
/*  960 */         throw new IllegalComponentStateException("The frame is decorated");
/*      */       }
/*  962 */       super.setOpacity(paramFloat);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setShape(Shape paramShape)
/*      */   {
/*  971 */     synchronized (getTreeLock()) {
/*  972 */       if ((paramShape != null) && (!isUndecorated())) {
/*  973 */         throw new IllegalComponentStateException("The frame is decorated");
/*      */       }
/*  975 */       super.setShape(paramShape);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBackground(Color paramColor)
/*      */   {
/*  984 */     synchronized (getTreeLock()) {
/*  985 */       if ((paramColor != null) && (paramColor.getAlpha() < 255) && (!isUndecorated())) {
/*  986 */         throw new IllegalComponentStateException("The frame is decorated");
/*      */       }
/*  988 */       super.setBackground(paramColor);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void remove(MenuComponent paramMenuComponent)
/*      */   {
/*  999 */     if (paramMenuComponent == null) {
/* 1000 */       return;
/*      */     }
/* 1002 */     synchronized (getTreeLock()) {
/* 1003 */       if (paramMenuComponent == this.menuBar) {
/* 1004 */         this.menuBar = null;
/* 1005 */         FramePeer localFramePeer = (FramePeer)this.peer;
/* 1006 */         if (localFramePeer != null) {
/* 1007 */           this.mbManagement = true;
/* 1008 */           invalidateIfValid();
/* 1009 */           localFramePeer.setMenuBar(null);
/* 1010 */           paramMenuComponent.removeNotify();
/*      */         }
/* 1012 */         paramMenuComponent.parent = null;
/*      */       } else {
/* 1014 */         super.remove(paramMenuComponent);
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
/*      */   public void removeNotify()
/*      */   {
/* 1029 */     synchronized (getTreeLock()) {
/* 1030 */       FramePeer localFramePeer = (FramePeer)this.peer;
/* 1031 */       if (localFramePeer != null)
/*      */       {
/* 1033 */         getState();
/*      */         
/* 1035 */         if (this.menuBar != null) {
/* 1036 */           this.mbManagement = true;
/* 1037 */           localFramePeer.setMenuBar(null);
/* 1038 */           this.menuBar.removeNotify();
/*      */         }
/*      */       }
/* 1041 */       super.removeNotify();
/*      */     }
/*      */   }
/*      */   
/*      */   void postProcessKeyEvent(KeyEvent paramKeyEvent) {
/* 1046 */     if ((this.menuBar != null) && (this.menuBar.handleShortcut(paramKeyEvent))) {
/* 1047 */       paramKeyEvent.consume();
/* 1048 */       return;
/*      */     }
/* 1050 */     super.postProcessKeyEvent(paramKeyEvent);
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
/*      */   protected String paramString()
/*      */   {
/* 1063 */     String str = super.paramString();
/* 1064 */     if (this.title != null) {
/* 1065 */       str = str + ",title=" + this.title;
/*      */     }
/* 1067 */     if (this.resizable) {
/* 1068 */       str = str + ",resizable";
/*      */     }
/* 1070 */     int i = getExtendedState();
/* 1071 */     if (i == 0) {
/* 1072 */       str = str + ",normal";
/*      */     }
/*      */     else {
/* 1075 */       if ((i & 0x1) != 0) {
/* 1076 */         str = str + ",iconified";
/*      */       }
/* 1078 */       if ((i & 0x6) == 6) {
/* 1079 */         str = str + ",maximized";
/*      */       }
/* 1081 */       else if ((i & 0x2) != 0) {
/* 1082 */         str = str + ",maximized_horiz";
/*      */       }
/* 1084 */       else if ((i & 0x4) != 0) {
/* 1085 */         str = str + ",maximized_vert";
/*      */       }
/*      */     }
/* 1088 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void setCursor(int paramInt)
/*      */   {
/* 1097 */     if ((paramInt < 0) || (paramInt > 13)) {
/* 1098 */       throw new IllegalArgumentException("illegal cursor type");
/*      */     }
/* 1100 */     setCursor(Cursor.getPredefinedCursor(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int getCursorType()
/*      */   {
/* 1109 */     return getCursor().getType();
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
/*      */   public static Frame[] getFrames()
/*      */   {
/* 1133 */     Window[] arrayOfWindow1 = Window.getWindows();
/*      */     
/* 1135 */     int i = 0;
/* 1136 */     for (Object localObject2 : arrayOfWindow1) {
/* 1137 */       if ((localObject2 instanceof Frame)) {
/* 1138 */         i++;
/*      */       }
/*      */     }
/*      */     
/* 1142 */     ??? = new Frame[i];
/* 1143 */     ??? = 0;
/* 1144 */     for (Window localWindow : arrayOfWindow1) {
/* 1145 */       if ((localWindow instanceof Frame)) {
/* 1146 */         ???[(???++)] = ((Frame)localWindow);
/*      */       }
/*      */     }
/*      */     
/* 1150 */     return (Frame[])???;
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
/* 1163 */   private int frameSerializedDataVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1180 */     paramObjectOutputStream.defaultWriteObject();
/* 1181 */     if ((this.icons != null) && (this.icons.size() > 0)) {
/* 1182 */       Image localImage = (Image)this.icons.get(0);
/* 1183 */       if ((localImage instanceof Serializable)) {
/* 1184 */         paramObjectOutputStream.writeObject(localImage);
/* 1185 */         return;
/*      */       }
/*      */     }
/* 1188 */     paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException, HeadlessException
/*      */   {
/* 1217 */     paramObjectInputStream.defaultReadObject();
/*      */     try {
/* 1219 */       Image localImage = (Image)paramObjectInputStream.readObject();
/* 1220 */       if (this.icons == null) {
/* 1221 */         this.icons = new ArrayList();
/* 1222 */         this.icons.add(localImage);
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (OptionalDataException localOptionalDataException)
/*      */     {
/*      */ 
/* 1232 */       if (!localOptionalDataException.eof) {
/* 1233 */         throw localOptionalDataException;
/*      */       }
/*      */     }
/*      */     
/* 1237 */     if (this.menuBar != null) {
/* 1238 */       this.menuBar.parent = this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1243 */     if (this.ownedWindows != null) {
/* 1244 */       for (int i = 0; i < this.ownedWindows.size(); i++) {
/* 1245 */         connectOwnedWindow((Window)this.ownedWindows.elementAt(i));
/*      */       }
/* 1247 */       this.ownedWindows = null;
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
/*      */   public AccessibleContext getAccessibleContext()
/*      */   {
/* 1272 */     if (this.accessibleContext == null) {
/* 1273 */       this.accessibleContext = new AccessibleAWTFrame();
/*      */     }
/* 1275 */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   String constructComponentName()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 11
/*      */     //   2: dup
/*      */     //   3: astore_1
/*      */     //   4: monitorenter
/*      */     //   5: new 16	java/lang/StringBuilder
/*      */     //   8: dup
/*      */     //   9: invokespecial 17	java/lang/StringBuilder:<init>	()V
/*      */     //   12: ldc 18
/*      */     //   14: invokevirtual 19	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   17: getstatic 20	java/awt/Frame:nameCounter	I
/*      */     //   20: dup
/*      */     //   21: iconst_1
/*      */     //   22: iadd
/*      */     //   23: putstatic 20	java/awt/Frame:nameCounter	I
/*      */     //   26: invokevirtual 21	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   29: invokevirtual 22	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   32: aload_1
/*      */     //   33: monitorexit
/*      */     //   34: areturn
/*      */     //   35: astore_2
/*      */     //   36: aload_1
/*      */     //   37: monitorexit
/*      */     //   38: aload_2
/*      */     //   39: athrow
/*      */     // Line number table:
/*      */     //   Java source line #460	-> byte code offset #0
/*      */     //   Java source line #461	-> byte code offset #5
/*      */     //   Java source line #462	-> byte code offset #35
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	40	0	this	Frame
/*      */     //   3	34	1	Ljava/lang/Object;	Object
/*      */     //   35	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   5	34	35	finally
/*      */     //   35	38	35	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public int getExtendedState()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 52	java/awt/Frame:getObjectLock	()Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 1	java/awt/Frame:state	I
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: ireturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #813	-> byte code offset #0
/*      */     //   Java source line #814	-> byte code offset #7
/*      */     //   Java source line #815	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Frame
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public Rectangle getMaximizedBounds()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 52	java/awt/Frame:getObjectLock	()Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 31	java/awt/Frame:maximizedBounds	Ljava/awt/Rectangle;
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: areturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #883	-> byte code offset #0
/*      */     //   Java source line #884	-> byte code offset #7
/*      */     //   Java source line #885	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Frame
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   protected class AccessibleAWTFrame
/*      */     extends Window.AccessibleAWTWindow
/*      */   {
/*      */     private static final long serialVersionUID = -6172960752956030250L;
/*      */     
/*      */     protected AccessibleAWTFrame()
/*      */     {
/* 1284 */       super();
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
/* 1299 */       return AccessibleRole.FRAME;
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
/* 1310 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/* 1311 */       if (Frame.this.getFocusOwner() != null) {
/* 1312 */         localAccessibleStateSet.add(AccessibleState.ACTIVE);
/*      */       }
/* 1314 */       if (Frame.this.isResizable()) {
/* 1315 */         localAccessibleStateSet.add(AccessibleState.RESIZABLE);
/*      */       }
/* 1317 */       return localAccessibleStateSet;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Frame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */