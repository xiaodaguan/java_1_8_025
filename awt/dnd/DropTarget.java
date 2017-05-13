/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.FlavorMap;
/*     */ import java.awt.datatransfer.SystemFlavorMap;
/*     */ import java.awt.dnd.peer.DropTargetPeer;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.LightweightPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.TooManyListenersException;
/*     */ import javax.swing.Timer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DropTarget
/*     */   implements DropTargetListener, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6283860791671019047L;
/*     */   
/*     */   public DropTarget(Component paramComponent, int paramInt, DropTargetListener paramDropTargetListener, boolean paramBoolean, FlavorMap paramFlavorMap)
/*     */     throws HeadlessException
/*     */   {
/*  94 */     if (GraphicsEnvironment.isHeadless()) {
/*  95 */       throw new HeadlessException();
/*     */     }
/*     */     
/*  98 */     this.component = paramComponent;
/*     */     
/* 100 */     setDefaultActions(paramInt);
/*     */     
/* 102 */     if (paramDropTargetListener != null) {
/* 103 */       try { addDropTargetListener(paramDropTargetListener);
/*     */       }
/*     */       catch (TooManyListenersException localTooManyListenersException) {}
/*     */     }
/*     */     
/* 108 */     if (paramComponent != null) {
/* 109 */       paramComponent.setDropTarget(this);
/* 110 */       setActive(paramBoolean);
/*     */     }
/*     */     
/* 113 */     if (paramFlavorMap != null) {
/* 114 */       this.flavorMap = paramFlavorMap;
/*     */     } else {
/* 116 */       this.flavorMap = SystemFlavorMap.getDefaultFlavorMap();
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
/*     */   public DropTarget(Component paramComponent, int paramInt, DropTargetListener paramDropTargetListener, boolean paramBoolean)
/*     */     throws HeadlessException
/*     */   {
/* 141 */     this(paramComponent, paramInt, paramDropTargetListener, paramBoolean, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DropTarget()
/*     */     throws HeadlessException
/*     */   {
/* 151 */     this(null, 3, null, true, null);
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
/*     */   public DropTarget(Component paramComponent, DropTargetListener paramDropTargetListener)
/*     */     throws HeadlessException
/*     */   {
/* 169 */     this(paramComponent, 3, paramDropTargetListener, true, null);
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
/*     */   public DropTarget(Component paramComponent, int paramInt, DropTargetListener paramDropTargetListener)
/*     */     throws HeadlessException
/*     */   {
/* 189 */     this(paramComponent, paramInt, paramDropTargetListener, true);
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
/*     */   public synchronized void setComponent(Component paramComponent)
/*     */   {
/* 204 */     if ((this.component == paramComponent) || ((this.component != null) && (this.component.equals(paramComponent)))) {
/* 205 */       return;
/*     */     }
/*     */     
/* 208 */     ComponentPeer localComponentPeer = null;
/*     */     Component localComponent;
/* 210 */     if ((localComponent = this.component) != null) {
/* 211 */       clearAutoscroll();
/*     */       
/* 213 */       this.component = null;
/*     */       
/* 215 */       if (this.componentPeer != null) {
/* 216 */         localComponentPeer = this.componentPeer;
/* 217 */         removeNotify(this.componentPeer);
/*     */       }
/*     */       
/* 220 */       localComponent.setDropTarget(null);
/*     */     }
/*     */     
/*     */ 
/* 224 */     if ((this.component = paramComponent) != null) {
/* 225 */       try { paramComponent.setDropTarget(this);
/*     */       } catch (Exception localException) {
/* 227 */         if (localComponent != null) {
/* 228 */           localComponent.setDropTarget(this);
/* 229 */           addNotify(localComponentPeer);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Component getComponent()
/*     */   {
/* 242 */     return this.component;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDefaultActions(int paramInt)
/*     */   {
/* 253 */     getDropTargetContext().setTargetActions(paramInt & 0x40000003);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void doSetDefaultActions(int paramInt)
/*     */   {
/* 261 */     this.actions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDefaultActions()
/*     */   {
/* 272 */     return this.actions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setActive(boolean paramBoolean)
/*     */   {
/* 283 */     if (paramBoolean != this.active) {
/* 284 */       this.active = paramBoolean;
/*     */     }
/*     */     
/* 287 */     if (!this.active) { clearAutoscroll();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 299 */     return this.active;
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
/*     */   public synchronized void addDropTargetListener(DropTargetListener paramDropTargetListener)
/*     */     throws TooManyListenersException
/*     */   {
/* 313 */     if (paramDropTargetListener == null) { return;
/*     */     }
/* 315 */     if (equals(paramDropTargetListener)) { throw new IllegalArgumentException("DropTarget may not be its own Listener");
/*     */     }
/* 317 */     if (this.dtListener == null) {
/* 318 */       this.dtListener = paramDropTargetListener;
/*     */     } else {
/* 320 */       throw new TooManyListenersException();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void removeDropTargetListener(DropTargetListener paramDropTargetListener)
/*     */   {
/* 330 */     if ((paramDropTargetListener != null) && (this.dtListener != null)) {
/* 331 */       if (this.dtListener.equals(paramDropTargetListener)) {
/* 332 */         this.dtListener = null;
/*     */       } else {
/* 334 */         throw new IllegalArgumentException("listener mismatch");
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void dragEnter(DropTargetDragEvent paramDropTargetDragEvent)
/*     */   {
/* 353 */     this.isDraggingInside = true;
/*     */     
/* 355 */     if (!this.active) { return;
/*     */     }
/* 357 */     if (this.dtListener != null) {
/* 358 */       this.dtListener.dragEnter(paramDropTargetDragEvent);
/*     */     } else {
/* 360 */       paramDropTargetDragEvent.getDropTargetContext().setTargetActions(0);
/*     */     }
/* 362 */     initializeAutoscrolling(paramDropTargetDragEvent.getLocation());
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
/*     */   public synchronized void dragOver(DropTargetDragEvent paramDropTargetDragEvent)
/*     */   {
/* 380 */     if (!this.active) { return;
/*     */     }
/* 382 */     if ((this.dtListener != null) && (this.active)) { this.dtListener.dragOver(paramDropTargetDragEvent);
/*     */     }
/* 384 */     updateAutoscroll(paramDropTargetDragEvent.getLocation());
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
/*     */   public synchronized void dropActionChanged(DropTargetDragEvent paramDropTargetDragEvent)
/*     */   {
/* 402 */     if (!this.active) { return;
/*     */     }
/* 404 */     if (this.dtListener != null) { this.dtListener.dropActionChanged(paramDropTargetDragEvent);
/*     */     }
/* 406 */     updateAutoscroll(paramDropTargetDragEvent.getLocation());
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
/*     */   public synchronized void dragExit(DropTargetEvent paramDropTargetEvent)
/*     */   {
/* 425 */     this.isDraggingInside = false;
/*     */     
/* 427 */     if (!this.active) { return;
/*     */     }
/* 429 */     if ((this.dtListener != null) && (this.active)) { this.dtListener.dragExit(paramDropTargetEvent);
/*     */     }
/* 431 */     clearAutoscroll();
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
/*     */   public synchronized void drop(DropTargetDropEvent paramDropTargetDropEvent)
/*     */   {
/* 450 */     this.isDraggingInside = false;
/*     */     
/* 452 */     clearAutoscroll();
/*     */     
/* 454 */     if ((this.dtListener != null) && (this.active)) {
/* 455 */       this.dtListener.drop(paramDropTargetDropEvent);
/*     */     } else {
/* 457 */       paramDropTargetDropEvent.rejectDrop();
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
/*     */   public FlavorMap getFlavorMap()
/*     */   {
/* 471 */     return this.flavorMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFlavorMap(FlavorMap paramFlavorMap)
/*     */   {
/* 482 */     this.flavorMap = (paramFlavorMap == null ? SystemFlavorMap.getDefaultFlavorMap() : paramFlavorMap);
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
/*     */   public void addNotify(ComponentPeer paramComponentPeer)
/*     */   {
/* 503 */     if (paramComponentPeer == this.componentPeer) { return;
/*     */     }
/* 505 */     this.componentPeer = paramComponentPeer;
/*     */     
/* 507 */     for (Object localObject = this.component; 
/* 508 */         (localObject != null) && ((paramComponentPeer instanceof LightweightPeer)); localObject = ((Component)localObject).getParent()) {
/* 509 */       paramComponentPeer = ((Component)localObject).getPeer();
/*     */     }
/*     */     
/* 512 */     if ((paramComponentPeer instanceof DropTargetPeer)) {
/* 513 */       this.nativePeer = paramComponentPeer;
/* 514 */       ((DropTargetPeer)paramComponentPeer).addDropTarget(this);
/*     */     } else {
/* 516 */       this.nativePeer = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeNotify(ComponentPeer paramComponentPeer)
/*     */   {
/* 537 */     if (this.nativePeer != null) {
/* 538 */       ((DropTargetPeer)this.nativePeer).removeDropTarget(this);
/*     */     }
/* 540 */     this.componentPeer = (this.nativePeer = null);
/*     */     
/* 542 */     synchronized (this) {
/* 543 */       if (this.isDraggingInside) {
/* 544 */         dragExit(new DropTargetEvent(getDropTargetContext()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DropTargetContext getDropTargetContext()
/*     */   {
/* 557 */     return this.dropTargetContext;
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
/*     */   protected DropTargetContext createDropTargetContext()
/*     */   {
/* 572 */     return new DropTargetContext(this);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 587 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 589 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.dtListener) ? this.dtListener : null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException
/*     */   {
/* 608 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */     try
/*     */     {
/* 612 */       this.dropTargetContext = ((DropTargetContext)localGetField.get("dropTargetContext", null));
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException1) {}
/*     */     
/* 616 */     if (this.dropTargetContext == null) {
/* 617 */       this.dropTargetContext = createDropTargetContext();
/*     */     }
/*     */     
/* 620 */     this.component = ((Component)localGetField.get("component", null));
/* 621 */     this.actions = localGetField.get("actions", 3);
/* 622 */     this.active = localGetField.get("active", true);
/*     */     
/*     */     try
/*     */     {
/* 626 */       this.dtListener = ((DropTargetListener)localGetField.get("dtListener", null));
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException2) {
/* 629 */       this.dtListener = ((DropTargetListener)paramObjectInputStream.readObject());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected static class DropTargetAutoScroller
/*     */     implements ActionListener
/*     */   {
/*     */     private Component component;
/*     */     
/*     */     private Autoscroll autoScroll;
/*     */     
/*     */     private Timer timer;
/*     */     
/*     */     private Point locn;
/*     */     
/*     */     private Point prev;
/*     */     
/*     */ 
/*     */     protected DropTargetAutoScroller(Component paramComponent, Point paramPoint)
/*     */     {
/* 651 */       this.component = paramComponent;
/* 652 */       this.autoScroll = ((Autoscroll)this.component);
/*     */       
/* 654 */       Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*     */       
/* 656 */       Integer localInteger1 = Integer.valueOf(100);
/* 657 */       Integer localInteger2 = Integer.valueOf(100);
/*     */       try
/*     */       {
/* 660 */         localInteger1 = (Integer)localToolkit.getDesktopProperty("DnD.Autoscroll.initialDelay");
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       
/*     */       try
/*     */       {
/* 666 */         localInteger2 = (Integer)localToolkit.getDesktopProperty("DnD.Autoscroll.interval");
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */       
/*     */ 
/* 671 */       this.timer = new Timer(localInteger2.intValue(), this);
/*     */       
/* 673 */       this.timer.setCoalesce(true);
/* 674 */       this.timer.setInitialDelay(localInteger1.intValue());
/*     */       
/* 676 */       this.locn = paramPoint;
/* 677 */       this.prev = paramPoint;
/*     */       try
/*     */       {
/* 680 */         this.hysteresis = ((Integer)localToolkit.getDesktopProperty("DnD.Autoscroll.cursorHysteresis")).intValue();
/*     */       }
/*     */       catch (Exception localException3) {}
/*     */       
/*     */ 
/* 685 */       this.timer.start();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private void updateRegion()
/*     */     {
/* 693 */       Insets localInsets = this.autoScroll.getAutoscrollInsets();
/* 694 */       Dimension localDimension = this.component.getSize();
/*     */       
/* 696 */       if ((localDimension.width != this.outer.width) || (localDimension.height != this.outer.height)) {
/* 697 */         this.outer.reshape(0, 0, localDimension.width, localDimension.height);
/*     */       }
/* 699 */       if ((this.inner.x != localInsets.left) || (this.inner.y != localInsets.top)) {
/* 700 */         this.inner.setLocation(localInsets.left, localInsets.top);
/*     */       }
/* 702 */       int i = localDimension.width - (localInsets.left + localInsets.right);
/* 703 */       int j = localDimension.height - (localInsets.top + localInsets.bottom);
/*     */       
/* 705 */       if ((i != this.inner.width) || (j != this.inner.height)) {
/* 706 */         this.inner.setSize(i, j);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected synchronized void updateLocation(Point paramPoint)
/*     */     {
/* 717 */       this.prev = this.locn;
/* 718 */       this.locn = paramPoint;
/*     */       
/* 720 */       if ((Math.abs(this.locn.x - this.prev.x) > this.hysteresis) || 
/* 721 */         (Math.abs(this.locn.y - this.prev.y) > this.hysteresis)) {
/* 722 */         if (this.timer.isRunning()) this.timer.stop();
/*     */       }
/* 724 */       else if (!this.timer.isRunning()) { this.timer.start();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void stop()
/*     */     {
/* 732 */       this.timer.stop();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public synchronized void actionPerformed(ActionEvent paramActionEvent)
/*     */     {
/* 741 */       updateRegion();
/*     */       
/* 743 */       if ((this.outer.contains(this.locn)) && (!this.inner.contains(this.locn))) {
/* 744 */         this.autoScroll.autoscroll(this.locn);
/*     */       }
/*     */     }
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
/* 759 */     private Rectangle outer = new Rectangle();
/* 760 */     private Rectangle inner = new Rectangle();
/*     */     
/* 762 */     private int hysteresis = 10;
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
/*     */   protected DropTargetAutoScroller createDropTargetAutoScroller(Component paramComponent, Point paramPoint)
/*     */   {
/* 775 */     return new DropTargetAutoScroller(paramComponent, paramPoint);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAutoscrolling(Point paramPoint)
/*     */   {
/* 785 */     if ((this.component == null) || (!(this.component instanceof Autoscroll))) { return;
/*     */     }
/* 787 */     this.autoScroller = createDropTargetAutoScroller(this.component, paramPoint);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateAutoscroll(Point paramPoint)
/*     */   {
/* 797 */     if (this.autoScroller != null) { this.autoScroller.updateLocation(paramPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void clearAutoscroll()
/*     */   {
/* 805 */     if (this.autoScroller != null) {
/* 806 */       this.autoScroller.stop();
/* 807 */       this.autoScroller = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 816 */   private DropTargetContext dropTargetContext = createDropTargetContext();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Component component;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient ComponentPeer componentPeer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient ComponentPeer nativePeer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 843 */   int actions = 3;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 850 */   boolean active = true;
/*     */   private transient DropTargetAutoScroller autoScroller;
/*     */   private transient DropTargetListener dtListener;
/*     */   private transient FlavorMap flavorMap;
/*     */   private transient boolean isDraggingInside;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DropTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */