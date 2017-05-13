/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.ContainerPeer;
/*      */ import java.awt.peer.LightweightPeer;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventListener;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.swing.JInternalFrame;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.ContainerAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent.Cause;
/*      */ import sun.awt.PeerEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.java2d.pipe.Region;
/*      */ import sun.security.action.GetBooleanAction;
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
/*      */ public class Container
/*      */   extends Component
/*      */ {
/*   92 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.Container");
/*   93 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.Container");
/*      */   
/*   95 */   private static final Component[] EMPTY_ARRAY = new Component[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  102 */   private List<Component> component = new ArrayList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   LayoutManager layoutMgr;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private LightweightDispatcher dispatcher;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient FocusTraversalPolicy focusTraversalPolicy;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  152 */   private boolean focusCycleRoot = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean focusTraversalPolicyProvider;
/*      */   
/*      */ 
/*      */ 
/*      */   private transient Set<Thread> printingThreads;
/*      */   
/*      */ 
/*      */ 
/*  165 */   private transient boolean printing = false;
/*      */   
/*      */   transient ContainerListener containerListener;
/*      */   
/*      */   transient int listeningChildren;
/*      */   
/*      */   transient int listeningBoundsChildren;
/*      */   
/*      */   transient int descendantsCount;
/*      */   
/*  175 */   transient Color preserveBackgroundColor = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 4613797578919906343L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final boolean INCLUDE_SELF = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final boolean SEARCH_HEAVYWEIGHTS = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  205 */   private transient int numOfHWComponents = 0;
/*  206 */   private transient int numOfLWComponents = 0;
/*      */   
/*  208 */   private static final PlatformLogger mixingLog = PlatformLogger.getLogger("java.awt.mixing.Container");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  237 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("ncomponents", Integer.TYPE), new ObjectStreamField("component", Component[].class), new ObjectStreamField("layoutMgr", LayoutManager.class), new ObjectStreamField("dispatcher", LightweightDispatcher.class), new ObjectStreamField("maxSize", Dimension.class), new ObjectStreamField("focusCycleRoot", Boolean.TYPE), new ObjectStreamField("containerSerializedDataVersion", Integer.TYPE), new ObjectStreamField("focusTraversalPolicyProvider", Boolean.TYPE) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*  250 */     Toolkit.loadLibraries();
/*  251 */     if (!GraphicsEnvironment.isHeadless()) {
/*  252 */       initIDs();
/*      */     }
/*      */     
/*  255 */     AWTAccessor.setContainerAccessor(new AWTAccessor.ContainerAccessor()
/*      */     {
/*      */       public void validateUnconditionally(Container paramAnonymousContainer) {
/*  258 */         paramAnonymousContainer.validateUnconditionally();
/*      */       }
/*      */       
/*      */ 
/*      */       public Component findComponentAt(Container paramAnonymousContainer, int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean)
/*      */       {
/*  264 */         return paramAnonymousContainer.findComponentAt(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousBoolean);
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
/*      */   void initializeFocusTraversalKeys()
/*      */   {
/*  285 */     this.focusTraversalKeys = new Set[4];
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
/*      */   public int getComponentCount()
/*      */   {
/*  299 */     return countComponents();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int countComponents()
/*      */   {
/*  311 */     return this.component.size();
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
/*      */   public Component getComponent(int paramInt)
/*      */   {
/*      */     try
/*      */     {
/*  330 */       return (Component)this.component.get(paramInt);
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/*  332 */       throw new ArrayIndexOutOfBoundsException("No such child: " + paramInt);
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
/*      */   public Component[] getComponents()
/*      */   {
/*  348 */     return getComponents_NoClientCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final Component[] getComponents_NoClientCode()
/*      */   {
/*  356 */     return (Component[])this.component.toArray(EMPTY_ARRAY);
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
/*      */   public Insets getInsets()
/*      */   {
/*  380 */     return insets();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public Insets insets()
/*      */   {
/*  389 */     ComponentPeer localComponentPeer = this.peer;
/*  390 */     if ((localComponentPeer instanceof ContainerPeer)) {
/*  391 */       ContainerPeer localContainerPeer = (ContainerPeer)localComponentPeer;
/*  392 */       return (Insets)localContainerPeer.getInsets().clone();
/*      */     }
/*  394 */     return new Insets(0, 0, 0, 0);
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
/*      */   public Component add(Component paramComponent)
/*      */   {
/*  415 */     addImpl(paramComponent, null, -1);
/*  416 */     return paramComponent;
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
/*      */   public Component add(String paramString, Component paramComponent)
/*      */   {
/*  436 */     addImpl(paramComponent, paramString, -1);
/*  437 */     return paramComponent;
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
/*      */   public Component add(Component paramComponent, int paramInt)
/*      */   {
/*  465 */     addImpl(paramComponent, null, paramInt);
/*  466 */     return paramComponent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkAddToSelf(Component paramComponent)
/*      */   {
/*  474 */     if ((paramComponent instanceof Container)) {
/*  475 */       for (Container localContainer = this; localContainer != null; localContainer = localContainer.parent) {
/*  476 */         if (localContainer == paramComponent) {
/*  477 */           throw new IllegalArgumentException("adding container's parent to itself");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void checkNotAWindow(Component paramComponent)
/*      */   {
/*  487 */     if ((paramComponent instanceof Window)) {
/*  488 */       throw new IllegalArgumentException("adding a window to a container");
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
/*      */   private void checkAdding(Component paramComponent, int paramInt)
/*      */   {
/*  504 */     checkTreeLock();
/*      */     
/*  506 */     GraphicsConfiguration localGraphicsConfiguration = getGraphicsConfiguration();
/*      */     
/*  508 */     if ((paramInt > this.component.size()) || (paramInt < 0)) {
/*  509 */       throw new IllegalArgumentException("illegal component position");
/*      */     }
/*  511 */     if ((paramComponent.parent == this) && 
/*  512 */       (paramInt == this.component.size()))
/*      */     {
/*  514 */       throw new IllegalArgumentException("illegal component position " + paramInt + " should be less then " + this.component.size());
/*      */     }
/*      */     
/*  517 */     checkAddToSelf(paramComponent);
/*  518 */     checkNotAWindow(paramComponent);
/*      */     
/*  520 */     Window localWindow1 = getContainingWindow();
/*  521 */     Window localWindow2 = paramComponent.getContainingWindow();
/*  522 */     if (localWindow1 != localWindow2) {
/*  523 */       throw new IllegalArgumentException("component and container should be in the same top-level window");
/*      */     }
/*  525 */     if (localGraphicsConfiguration != null) {
/*  526 */       paramComponent.checkGD(localGraphicsConfiguration.getDevice().getIDstring());
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
/*      */   private boolean removeDelicately(Component paramComponent, Container paramContainer, int paramInt)
/*      */   {
/*  540 */     checkTreeLock();
/*      */     
/*  542 */     int i = getComponentZOrder(paramComponent);
/*  543 */     boolean bool = isRemoveNotifyNeeded(paramComponent, this, paramContainer);
/*  544 */     if (bool) {
/*  545 */       paramComponent.removeNotify();
/*      */     }
/*  547 */     if (paramContainer != this) {
/*  548 */       if (this.layoutMgr != null) {
/*  549 */         this.layoutMgr.removeLayoutComponent(paramComponent);
/*      */       }
/*  551 */       adjustListeningChildren(32768L, 
/*  552 */         -paramComponent.numListening(32768L));
/*  553 */       adjustListeningChildren(65536L, 
/*  554 */         -paramComponent.numListening(65536L));
/*  555 */       adjustDescendants(-paramComponent.countHierarchyMembers());
/*      */       
/*  557 */       paramComponent.parent = null;
/*  558 */       if (bool) {
/*  559 */         paramComponent.setGraphicsConfiguration(null);
/*      */       }
/*  561 */       this.component.remove(i);
/*      */       
/*  563 */       invalidateIfValid();
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  570 */       this.component.remove(i);
/*  571 */       this.component.add(paramInt, paramComponent);
/*      */     }
/*  573 */     if (paramComponent.parent == null) {
/*  574 */       if ((this.containerListener != null) || ((this.eventMask & 0x2) != 0L) || 
/*      */       
/*  576 */         (Toolkit.enabledOnToolkit(2L))) {
/*  577 */         ContainerEvent localContainerEvent = new ContainerEvent(this, 301, paramComponent);
/*      */         
/*      */ 
/*  580 */         dispatchEvent(localContainerEvent);
/*      */       }
/*      */       
/*  583 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1L, 
/*      */       
/*  585 */         Toolkit.enabledOnToolkit(32768L));
/*  586 */       if ((this.peer != null) && (this.layoutMgr == null) && (isVisible())) {
/*  587 */         updateCursorImmediately();
/*      */       }
/*      */     }
/*  590 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean canContainFocusOwner(Component paramComponent)
/*      */   {
/*  600 */     if ((!isEnabled()) || (!isDisplayable()) || 
/*  601 */       (!isVisible()) || (!isFocusable()))
/*      */     {
/*  603 */       return false;
/*      */     }
/*  605 */     if (isFocusCycleRoot()) {
/*  606 */       FocusTraversalPolicy localFocusTraversalPolicy = getFocusTraversalPolicy();
/*  607 */       if (((localFocusTraversalPolicy instanceof DefaultFocusTraversalPolicy)) && 
/*  608 */         (!((DefaultFocusTraversalPolicy)localFocusTraversalPolicy).accept(paramComponent))) {
/*  609 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  613 */     synchronized (getTreeLock()) {
/*  614 */       if (this.parent != null) {
/*  615 */         return this.parent.canContainFocusOwner(paramComponent);
/*      */       }
/*      */     }
/*  618 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean hasHeavyweightDescendants()
/*      */   {
/*  628 */     checkTreeLock();
/*  629 */     return this.numOfHWComponents > 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean hasLightweightDescendants()
/*      */   {
/*  639 */     checkTreeLock();
/*  640 */     return this.numOfLWComponents > 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Container getHeavyweightContainer()
/*      */   {
/*  649 */     checkTreeLock();
/*  650 */     if ((this.peer != null) && (!(this.peer instanceof LightweightPeer))) {
/*  651 */       return this;
/*      */     }
/*  653 */     return getNativeContainer();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isRemoveNotifyNeeded(Component paramComponent, Container paramContainer1, Container paramContainer2)
/*      */   {
/*  665 */     if (paramContainer1 == null) {
/*  666 */       return false;
/*      */     }
/*  668 */     if (paramComponent.peer == null) {
/*  669 */       return false;
/*      */     }
/*  671 */     if (paramContainer2.peer == null)
/*      */     {
/*  673 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  678 */     if (paramComponent.isLightweight()) {
/*  679 */       boolean bool = paramComponent instanceof Container;
/*      */       
/*  681 */       if ((!bool) || ((bool) && (!((Container)paramComponent).hasHeavyweightDescendants()))) {
/*  682 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  689 */     Container localContainer1 = paramContainer1.getHeavyweightContainer();
/*  690 */     Container localContainer2 = paramContainer2.getHeavyweightContainer();
/*  691 */     if (localContainer1 != localContainer2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  699 */       return !paramComponent.peer.isReparentSupported();
/*      */     }
/*  701 */     return false;
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
/*      */   public void setComponentZOrder(Component paramComponent, int paramInt)
/*      */   {
/*  756 */     synchronized (getTreeLock())
/*      */     {
/*  758 */       Container localContainer = paramComponent.parent;
/*  759 */       int i = getComponentZOrder(paramComponent);
/*      */       
/*  761 */       if ((localContainer == this) && (paramInt == i)) {
/*  762 */         return;
/*      */       }
/*  764 */       checkAdding(paramComponent, paramInt);
/*      */       
/*      */ 
/*  767 */       int j = localContainer != null ? localContainer.removeDelicately(paramComponent, this, paramInt) : 0;
/*      */       
/*  769 */       addDelicately(paramComponent, localContainer, paramInt);
/*      */       
/*      */ 
/*      */ 
/*  773 */       if ((j == 0) && (i != -1))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  779 */         paramComponent.mixOnZOrderChanging(i, paramInt);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void reparentTraverse(ContainerPeer paramContainerPeer, Container paramContainer)
/*      */   {
/*  790 */     checkTreeLock();
/*      */     
/*  792 */     for (int i = 0; i < paramContainer.getComponentCount(); i++) {
/*  793 */       Component localComponent = paramContainer.getComponent(i);
/*  794 */       if (localComponent.isLightweight())
/*      */       {
/*      */ 
/*  797 */         if ((localComponent instanceof Container)) {
/*  798 */           reparentTraverse(paramContainerPeer, (Container)localComponent);
/*      */         }
/*      */       }
/*      */       else {
/*  802 */         localComponent.getPeer().reparent(paramContainerPeer);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void reparentChild(Component paramComponent)
/*      */   {
/*  813 */     checkTreeLock();
/*  814 */     if (paramComponent == null) {
/*  815 */       return;
/*      */     }
/*  817 */     if (paramComponent.isLightweight())
/*      */     {
/*  819 */       if ((paramComponent instanceof Container))
/*      */       {
/*  821 */         reparentTraverse((ContainerPeer)getPeer(), (Container)paramComponent);
/*      */       }
/*      */     } else {
/*  824 */       paramComponent.getPeer().reparent((ContainerPeer)getPeer());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addDelicately(Component paramComponent, Container paramContainer, int paramInt)
/*      */   {
/*  834 */     checkTreeLock();
/*      */     
/*      */ 
/*  837 */     if (paramContainer != this)
/*      */     {
/*  839 */       if (paramInt == -1) {
/*  840 */         this.component.add(paramComponent);
/*      */       } else {
/*  842 */         this.component.add(paramInt, paramComponent);
/*      */       }
/*  844 */       paramComponent.parent = this;
/*  845 */       paramComponent.setGraphicsConfiguration(getGraphicsConfiguration());
/*      */       
/*  847 */       adjustListeningChildren(32768L, paramComponent
/*  848 */         .numListening(32768L));
/*  849 */       adjustListeningChildren(65536L, paramComponent
/*  850 */         .numListening(65536L));
/*  851 */       adjustDescendants(paramComponent.countHierarchyMembers());
/*      */     }
/*  853 */     else if (paramInt < this.component.size()) {
/*  854 */       this.component.set(paramInt, paramComponent);
/*      */     }
/*      */     
/*      */ 
/*  858 */     invalidateIfValid();
/*  859 */     Object localObject; if (this.peer != null) {
/*  860 */       if (paramComponent.peer == null) {
/*  861 */         paramComponent.addNotify();
/*      */       }
/*      */       else {
/*  864 */         localObject = getHeavyweightContainer();
/*  865 */         Container localContainer = paramContainer.getHeavyweightContainer();
/*  866 */         if (localContainer != localObject)
/*      */         {
/*  868 */           ((Container)localObject).reparentChild(paramComponent);
/*      */         }
/*  870 */         paramComponent.updateZOrder();
/*      */         
/*  872 */         if ((!paramComponent.isLightweight()) && (isLightweight()))
/*      */         {
/*      */ 
/*  875 */           paramComponent.relocateComponent();
/*      */         }
/*      */       }
/*      */     }
/*  879 */     if (paramContainer != this)
/*      */     {
/*  881 */       if (this.layoutMgr != null) {
/*  882 */         if ((this.layoutMgr instanceof LayoutManager2)) {
/*  883 */           ((LayoutManager2)this.layoutMgr).addLayoutComponent(paramComponent, null);
/*      */         } else {
/*  885 */           this.layoutMgr.addLayoutComponent(null, paramComponent);
/*      */         }
/*      */       }
/*  888 */       if ((this.containerListener != null) || ((this.eventMask & 0x2) != 0L) || 
/*      */       
/*  890 */         (Toolkit.enabledOnToolkit(2L))) {
/*  891 */         localObject = new ContainerEvent(this, 300, paramComponent);
/*      */         
/*      */ 
/*  894 */         dispatchEvent((AWTEvent)localObject);
/*      */       }
/*  896 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1L, 
/*      */       
/*  898 */         Toolkit.enabledOnToolkit(32768L));
/*      */       
/*      */ 
/*      */ 
/*  902 */       if ((paramComponent.isFocusOwner()) && (!paramComponent.canBeFocusOwnerRecursively())) {
/*  903 */         paramComponent.transferFocus();
/*  904 */       } else if ((paramComponent instanceof Container)) {
/*  905 */         localObject = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*  906 */         if ((localObject != null) && (isParentOf((Component)localObject)) && (!((Component)localObject).canBeFocusOwnerRecursively())) {
/*  907 */           ((Component)localObject).transferFocus();
/*      */         }
/*      */       }
/*      */     } else {
/*  911 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1400L, 
/*      */       
/*  913 */         Toolkit.enabledOnToolkit(32768L));
/*      */     }
/*      */     
/*  916 */     if ((this.peer != null) && (this.layoutMgr == null) && (isVisible())) {
/*  917 */       updateCursorImmediately();
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
/*      */   public int getComponentZOrder(Component paramComponent)
/*      */   {
/*  935 */     if (paramComponent == null) {
/*  936 */       return -1;
/*      */     }
/*  938 */     synchronized (getTreeLock())
/*      */     {
/*  940 */       if (paramComponent.parent != this) {
/*  941 */         return -1;
/*      */       }
/*  943 */       return this.component.indexOf(paramComponent);
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
/*      */   public void add(Component paramComponent, Object paramObject)
/*      */   {
/*  971 */     addImpl(paramComponent, paramObject, -1);
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
/*      */   public void add(Component paramComponent, Object paramObject, int paramInt)
/*      */   {
/* 1003 */     addImpl(paramComponent, paramObject, paramInt);
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
/*      */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt)
/*      */   {
/* 1074 */     synchronized (getTreeLock())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1082 */       GraphicsConfiguration localGraphicsConfiguration = getGraphicsConfiguration();
/*      */       
/* 1084 */       if ((paramInt > this.component.size()) || ((paramInt < 0) && (paramInt != -1))) {
/* 1085 */         throw new IllegalArgumentException("illegal component position");
/*      */       }
/*      */       
/* 1088 */       checkAddToSelf(paramComponent);
/* 1089 */       checkNotAWindow(paramComponent);
/* 1090 */       if (localGraphicsConfiguration != null) {
/* 1091 */         paramComponent.checkGD(localGraphicsConfiguration.getDevice().getIDstring());
/*      */       }
/*      */       
/*      */ 
/* 1095 */       if (paramComponent.parent != null) {
/* 1096 */         paramComponent.parent.remove(paramComponent);
/* 1097 */         if (paramInt > this.component.size()) {
/* 1098 */           throw new IllegalArgumentException("illegal component position");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1103 */       if (paramInt == -1) {
/* 1104 */         this.component.add(paramComponent);
/*      */       } else {
/* 1106 */         this.component.add(paramInt, paramComponent);
/*      */       }
/* 1108 */       paramComponent.parent = this;
/* 1109 */       paramComponent.setGraphicsConfiguration(localGraphicsConfiguration);
/*      */       
/* 1111 */       adjustListeningChildren(32768L, paramComponent
/* 1112 */         .numListening(32768L));
/* 1113 */       adjustListeningChildren(65536L, paramComponent
/* 1114 */         .numListening(65536L));
/* 1115 */       adjustDescendants(paramComponent.countHierarchyMembers());
/*      */       
/* 1117 */       invalidateIfValid();
/* 1118 */       if (this.peer != null) {
/* 1119 */         paramComponent.addNotify();
/*      */       }
/*      */       
/*      */ 
/* 1123 */       if (this.layoutMgr != null) {
/* 1124 */         if ((this.layoutMgr instanceof LayoutManager2)) {
/* 1125 */           ((LayoutManager2)this.layoutMgr).addLayoutComponent(paramComponent, paramObject);
/* 1126 */         } else if ((paramObject instanceof String)) {
/* 1127 */           this.layoutMgr.addLayoutComponent((String)paramObject, paramComponent);
/*      */         }
/*      */       }
/* 1130 */       if ((this.containerListener != null) || ((this.eventMask & 0x2) != 0L) || 
/*      */       
/* 1132 */         (Toolkit.enabledOnToolkit(2L))) {
/* 1133 */         ContainerEvent localContainerEvent = new ContainerEvent(this, 300, paramComponent);
/*      */         
/*      */ 
/* 1136 */         dispatchEvent(localContainerEvent);
/*      */       }
/*      */       
/* 1139 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1L, 
/*      */       
/* 1141 */         Toolkit.enabledOnToolkit(32768L));
/* 1142 */       if ((this.peer != null) && (this.layoutMgr == null) && (isVisible())) {
/* 1143 */         updateCursorImmediately();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/* 1150 */     checkTreeLock();
/*      */     
/* 1152 */     boolean bool = super.updateGraphicsData(paramGraphicsConfiguration);
/*      */     
/* 1154 */     for (Component localComponent : this.component) {
/* 1155 */       if (localComponent != null) {
/* 1156 */         bool |= localComponent.updateGraphicsData(paramGraphicsConfiguration);
/*      */       }
/*      */     }
/* 1159 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void checkGD(String paramString)
/*      */   {
/* 1168 */     for (Component localComponent : this.component) {
/* 1169 */       if (localComponent != null) {
/* 1170 */         localComponent.checkGD(paramString);
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
/*      */   public void remove(int paramInt)
/*      */   {
/* 1198 */     synchronized (getTreeLock()) {
/* 1199 */       if ((paramInt < 0) || (paramInt >= this.component.size())) {
/* 1200 */         throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */       }
/* 1202 */       Component localComponent = (Component)this.component.get(paramInt);
/* 1203 */       if (this.peer != null) {
/* 1204 */         localComponent.removeNotify();
/*      */       }
/* 1206 */       if (this.layoutMgr != null) {
/* 1207 */         this.layoutMgr.removeLayoutComponent(localComponent);
/*      */       }
/*      */       
/* 1210 */       adjustListeningChildren(32768L, 
/* 1211 */         -localComponent.numListening(32768L));
/* 1212 */       adjustListeningChildren(65536L, 
/* 1213 */         -localComponent.numListening(65536L));
/* 1214 */       adjustDescendants(-localComponent.countHierarchyMembers());
/*      */       
/* 1216 */       localComponent.parent = null;
/* 1217 */       this.component.remove(paramInt);
/* 1218 */       localComponent.setGraphicsConfiguration(null);
/*      */       
/* 1220 */       invalidateIfValid();
/* 1221 */       if ((this.containerListener != null) || ((this.eventMask & 0x2) != 0L) || 
/*      */       
/* 1223 */         (Toolkit.enabledOnToolkit(2L))) {
/* 1224 */         ContainerEvent localContainerEvent = new ContainerEvent(this, 301, localComponent);
/*      */         
/*      */ 
/* 1227 */         dispatchEvent(localContainerEvent);
/*      */       }
/*      */       
/* 1230 */       localComponent.createHierarchyEvents(1400, localComponent, this, 1L, 
/*      */       
/* 1232 */         Toolkit.enabledOnToolkit(32768L));
/* 1233 */       if ((this.peer != null) && (this.layoutMgr == null) && (isVisible())) {
/* 1234 */         updateCursorImmediately();
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
/*      */   public void remove(Component paramComponent)
/*      */   {
/* 1258 */     synchronized (getTreeLock()) {
/* 1259 */       if (paramComponent.parent == this) {
/* 1260 */         int i = this.component.indexOf(paramComponent);
/* 1261 */         if (i >= 0) {
/* 1262 */           remove(i);
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
/*      */   public void removeAll()
/*      */   {
/* 1284 */     synchronized (getTreeLock()) {
/* 1285 */       adjustListeningChildren(32768L, -this.listeningChildren);
/*      */       
/* 1287 */       adjustListeningChildren(65536L, -this.listeningBoundsChildren);
/*      */       
/* 1289 */       adjustDescendants(-this.descendantsCount);
/*      */       
/* 1291 */       while (!this.component.isEmpty()) {
/* 1292 */         Component localComponent = (Component)this.component.remove(this.component.size() - 1);
/*      */         
/* 1294 */         if (this.peer != null) {
/* 1295 */           localComponent.removeNotify();
/*      */         }
/* 1297 */         if (this.layoutMgr != null) {
/* 1298 */           this.layoutMgr.removeLayoutComponent(localComponent);
/*      */         }
/* 1300 */         localComponent.parent = null;
/* 1301 */         localComponent.setGraphicsConfiguration(null);
/* 1302 */         if ((this.containerListener != null) || ((this.eventMask & 0x2) != 0L) || 
/*      */         
/* 1304 */           (Toolkit.enabledOnToolkit(2L))) {
/* 1305 */           ContainerEvent localContainerEvent = new ContainerEvent(this, 301, localComponent);
/*      */           
/*      */ 
/* 1308 */           dispatchEvent(localContainerEvent);
/*      */         }
/*      */         
/* 1311 */         localComponent.createHierarchyEvents(1400, localComponent, this, 1L, 
/*      */         
/*      */ 
/* 1314 */           Toolkit.enabledOnToolkit(32768L));
/*      */       }
/* 1316 */       if ((this.peer != null) && (this.layoutMgr == null) && (isVisible())) {
/* 1317 */         updateCursorImmediately();
/*      */       }
/* 1319 */       invalidateIfValid();
/*      */     }
/*      */   }
/*      */   
/*      */   int numListening(long paramLong)
/*      */   {
/* 1325 */     int i = super.numListening(paramLong);
/*      */     int j;
/* 1327 */     Iterator localIterator; Component localComponent; if (paramLong == 32768L) {
/* 1328 */       if (eventLog.isLoggable(PlatformLogger.Level.FINE))
/*      */       {
/* 1330 */         j = 0;
/* 1331 */         for (localIterator = this.component.iterator(); localIterator.hasNext();) { localComponent = (Component)localIterator.next();
/* 1332 */           j += localComponent.numListening(paramLong);
/*      */         }
/* 1334 */         if (this.listeningChildren != j) {
/* 1335 */           eventLog.fine("Assertion (listeningChildren == sum) failed");
/*      */         }
/*      */       }
/* 1338 */       return this.listeningChildren + i; }
/* 1339 */     if (paramLong == 65536L) {
/* 1340 */       if (eventLog.isLoggable(PlatformLogger.Level.FINE))
/*      */       {
/* 1342 */         j = 0;
/* 1343 */         for (localIterator = this.component.iterator(); localIterator.hasNext();) { localComponent = (Component)localIterator.next();
/* 1344 */           j += localComponent.numListening(paramLong);
/*      */         }
/* 1346 */         if (this.listeningBoundsChildren != j) {
/* 1347 */           eventLog.fine("Assertion (listeningBoundsChildren == sum) failed");
/*      */         }
/*      */       }
/* 1350 */       return this.listeningBoundsChildren + i;
/*      */     }
/*      */     
/* 1353 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1354 */       eventLog.fine("This code must never be reached");
/*      */     }
/* 1356 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   void adjustListeningChildren(long paramLong, int paramInt)
/*      */   {
/* 1362 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1363 */       int i = (paramLong == 32768L) || (paramLong == 65536L) || (paramLong == 98304L) ? 1 : 0;
/*      */       
/*      */ 
/*      */ 
/* 1367 */       if (i == 0) {
/* 1368 */         eventLog.fine("Assertion failed");
/*      */       }
/*      */     }
/*      */     
/* 1372 */     if (paramInt == 0) {
/* 1373 */       return;
/*      */     }
/* 1375 */     if ((paramLong & 0x8000) != 0L) {
/* 1376 */       this.listeningChildren += paramInt;
/*      */     }
/* 1378 */     if ((paramLong & 0x10000) != 0L) {
/* 1379 */       this.listeningBoundsChildren += paramInt;
/*      */     }
/*      */     
/* 1382 */     adjustListeningChildrenOnParent(paramLong, paramInt);
/*      */   }
/*      */   
/*      */   void adjustDescendants(int paramInt)
/*      */   {
/* 1387 */     if (paramInt == 0) {
/* 1388 */       return;
/*      */     }
/* 1390 */     this.descendantsCount += paramInt;
/* 1391 */     adjustDecendantsOnParent(paramInt);
/*      */   }
/*      */   
/*      */   void adjustDecendantsOnParent(int paramInt)
/*      */   {
/* 1396 */     if (this.parent != null) {
/* 1397 */       this.parent.adjustDescendants(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */   int countHierarchyMembers()
/*      */   {
/* 1403 */     if (log.isLoggable(PlatformLogger.Level.FINE))
/*      */     {
/* 1405 */       int i = 0;
/* 1406 */       for (Component localComponent : this.component) {
/* 1407 */         i += localComponent.countHierarchyMembers();
/*      */       }
/* 1409 */       if (this.descendantsCount != i) {
/* 1410 */         log.fine("Assertion (descendantsCount == sum) failed");
/*      */       }
/*      */     }
/* 1413 */     return this.descendantsCount + 1;
/*      */   }
/*      */   
/*      */   private int getListenersCount(int paramInt, boolean paramBoolean) {
/* 1417 */     checkTreeLock();
/* 1418 */     if (paramBoolean) {
/* 1419 */       return this.descendantsCount;
/*      */     }
/* 1421 */     switch (paramInt) {
/*      */     case 1400: 
/* 1423 */       return this.listeningChildren;
/*      */     case 1401: 
/*      */     case 1402: 
/* 1426 */       return this.listeningBoundsChildren;
/*      */     }
/* 1428 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final int createHierarchyEvents(int paramInt, Component paramComponent, Container paramContainer, long paramLong, boolean paramBoolean)
/*      */   {
/* 1435 */     checkTreeLock();
/* 1436 */     int i = getListenersCount(paramInt, paramBoolean);
/*      */     
/* 1438 */     int j = i; for (int k = 0; j > 0; k++) {
/* 1439 */       j -= ((Component)this.component.get(k)).createHierarchyEvents(paramInt, paramComponent, paramContainer, paramLong, paramBoolean);
/*      */     }
/*      */     
/*      */ 
/* 1443 */     return i + super.createHierarchyEvents(paramInt, paramComponent, paramContainer, paramLong, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void createChildHierarchyEvents(int paramInt, long paramLong, boolean paramBoolean)
/*      */   {
/* 1450 */     checkTreeLock();
/* 1451 */     if (this.component.isEmpty()) {
/* 1452 */       return;
/*      */     }
/* 1454 */     int i = getListenersCount(paramInt, paramBoolean);
/*      */     
/* 1456 */     int j = i; for (int k = 0; j > 0; k++) {
/* 1457 */       j -= ((Component)this.component.get(k)).createHierarchyEvents(paramInt, this, this.parent, paramLong, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LayoutManager getLayout()
/*      */   {
/* 1468 */     return this.layoutMgr;
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
/*      */   public void setLayout(LayoutManager paramLayoutManager)
/*      */   {
/* 1483 */     this.layoutMgr = paramLayoutManager;
/* 1484 */     invalidateIfValid();
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
/*      */   public void doLayout()
/*      */   {
/* 1497 */     layout();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void layout()
/*      */   {
/* 1506 */     LayoutManager localLayoutManager = this.layoutMgr;
/* 1507 */     if (localLayoutManager != null) {
/* 1508 */       localLayoutManager.layoutContainer(this);
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
/*      */   public boolean isValidateRoot()
/*      */   {
/* 1542 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1548 */   private static final boolean isJavaAwtSmartInvalidate = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("java.awt.smartInvalidate"))).booleanValue();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void invalidateParent()
/*      */   {
/* 1558 */     if ((!isJavaAwtSmartInvalidate) || (!isValidateRoot())) {
/* 1559 */       super.invalidateParent();
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
/*      */   public void invalidate()
/*      */   {
/* 1580 */     LayoutManager localLayoutManager = this.layoutMgr;
/* 1581 */     if ((localLayoutManager instanceof LayoutManager2)) {
/* 1582 */       LayoutManager2 localLayoutManager2 = (LayoutManager2)localLayoutManager;
/* 1583 */       localLayoutManager2.invalidateLayout(this);
/*      */     }
/* 1585 */     super.invalidate();
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
/*      */   public void validate()
/*      */   {
/* 1616 */     boolean bool = false;
/* 1617 */     synchronized (getTreeLock()) {
/* 1618 */       if (((!isValid()) || (descendUnconditionallyWhenValidating)) && (this.peer != null))
/*      */       {
/*      */ 
/* 1621 */         ContainerPeer localContainerPeer = null;
/* 1622 */         if ((this.peer instanceof ContainerPeer)) {
/* 1623 */           localContainerPeer = (ContainerPeer)this.peer;
/*      */         }
/* 1625 */         if (localContainerPeer != null) {
/* 1626 */           localContainerPeer.beginValidate();
/*      */         }
/* 1628 */         validateTree();
/* 1629 */         if (localContainerPeer != null) {
/* 1630 */           localContainerPeer.endValidate();
/*      */           
/*      */ 
/* 1633 */           if (!descendUnconditionallyWhenValidating) {
/* 1634 */             bool = isVisible();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1639 */     if (bool) {
/* 1640 */       updateCursorImmediately();
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
/* 1655 */   private static boolean descendUnconditionallyWhenValidating = false;
/*      */   transient Component modalComp;
/*      */   transient AppContext modalAppContext;
/*      */   
/*      */   final void validateUnconditionally()
/*      */   {
/* 1661 */     boolean bool = false;
/* 1662 */     synchronized (getTreeLock()) {
/* 1663 */       descendUnconditionallyWhenValidating = true;
/*      */       
/* 1665 */       validate();
/* 1666 */       if ((this.peer instanceof ContainerPeer)) {
/* 1667 */         bool = isVisible();
/*      */       }
/*      */       
/* 1670 */       descendUnconditionallyWhenValidating = false;
/*      */     }
/* 1672 */     if (bool) {
/* 1673 */       updateCursorImmediately();
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
/*      */   protected void validateTree()
/*      */   {
/* 1687 */     checkTreeLock();
/* 1688 */     if ((!isValid()) || (descendUnconditionallyWhenValidating)) {
/* 1689 */       if ((this.peer instanceof ContainerPeer)) {
/* 1690 */         ((ContainerPeer)this.peer).beginLayout();
/*      */       }
/* 1692 */       if (!isValid()) {
/* 1693 */         doLayout();
/*      */       }
/* 1695 */       for (int i = 0; i < this.component.size(); i++) {
/* 1696 */         Component localComponent = (Component)this.component.get(i);
/* 1697 */         if (((localComponent instanceof Container)) && (!(localComponent instanceof Window)))
/*      */         {
/* 1699 */           if ((!localComponent.isValid()) || (descendUnconditionallyWhenValidating))
/*      */           {
/*      */ 
/* 1702 */             ((Container)localComponent).validateTree(); continue;
/*      */           } }
/* 1704 */         localComponent.validate();
/*      */       }
/*      */       
/* 1707 */       if ((this.peer instanceof ContainerPeer)) {
/* 1708 */         ((ContainerPeer)this.peer).endLayout();
/*      */       }
/*      */     }
/* 1711 */     super.validate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void invalidateTree()
/*      */   {
/* 1719 */     synchronized (getTreeLock()) {
/* 1720 */       for (int i = 0; i < this.component.size(); i++) {
/* 1721 */         Component localComponent = (Component)this.component.get(i);
/* 1722 */         if ((localComponent instanceof Container)) {
/* 1723 */           ((Container)localComponent).invalidateTree();
/*      */         }
/*      */         else {
/* 1726 */           localComponent.invalidateIfValid();
/*      */         }
/*      */       }
/* 1729 */       invalidateIfValid();
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
/*      */   public void setFont(Font paramFont)
/*      */   {
/* 1745 */     int i = 0;
/*      */     
/* 1747 */     Font localFont1 = getFont();
/* 1748 */     super.setFont(paramFont);
/* 1749 */     Font localFont2 = getFont();
/* 1750 */     if ((localFont2 != localFont1) && ((localFont1 == null) || 
/* 1751 */       (!localFont1.equals(localFont2)))) {
/* 1752 */       invalidateTree();
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
/*      */   public Dimension getPreferredSize()
/*      */   {
/* 1778 */     return preferredSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public Dimension preferredSize()
/*      */   {
/* 1790 */     Dimension localDimension = this.prefSize;
/* 1791 */     if ((localDimension == null) || ((!isPreferredSizeSet()) && (!isValid()))) {
/* 1792 */       synchronized (getTreeLock())
/*      */       {
/*      */ 
/* 1795 */         this.prefSize = (this.layoutMgr != null ? this.layoutMgr.preferredLayoutSize(this) : super.preferredSize());
/* 1796 */         localDimension = this.prefSize;
/*      */       }
/*      */     }
/* 1799 */     if (localDimension != null) {
/* 1800 */       return new Dimension(localDimension);
/*      */     }
/*      */     
/* 1803 */     return localDimension;
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
/*      */   public Dimension getMinimumSize()
/*      */   {
/* 1830 */     return minimumSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public Dimension minimumSize()
/*      */   {
/* 1842 */     Dimension localDimension = this.minSize;
/* 1843 */     if ((localDimension == null) || ((!isMinimumSizeSet()) && (!isValid()))) {
/* 1844 */       synchronized (getTreeLock())
/*      */       {
/*      */ 
/* 1847 */         this.minSize = (this.layoutMgr != null ? this.layoutMgr.minimumLayoutSize(this) : super.minimumSize());
/* 1848 */         localDimension = this.minSize;
/*      */       }
/*      */     }
/* 1851 */     if (localDimension != null) {
/* 1852 */       return new Dimension(localDimension);
/*      */     }
/*      */     
/* 1855 */     return localDimension;
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
/*      */   public Dimension getMaximumSize()
/*      */   {
/* 1885 */     Dimension localDimension = this.maxSize;
/* 1886 */     if ((localDimension == null) || ((!isMaximumSizeSet()) && (!isValid()))) {
/* 1887 */       synchronized (getTreeLock()) {
/* 1888 */         if ((this.layoutMgr instanceof LayoutManager2)) {
/* 1889 */           LayoutManager2 localLayoutManager2 = (LayoutManager2)this.layoutMgr;
/* 1890 */           this.maxSize = localLayoutManager2.maximumLayoutSize(this);
/*      */         } else {
/* 1892 */           this.maxSize = super.getMaximumSize();
/*      */         }
/* 1894 */         localDimension = this.maxSize;
/*      */       }
/*      */     }
/* 1897 */     if (localDimension != null) {
/* 1898 */       return new Dimension(localDimension);
/*      */     }
/*      */     
/* 1901 */     return localDimension;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getAlignmentX()
/*      */   {
/*      */     float f;
/*      */     
/*      */ 
/*      */ 
/* 1914 */     if ((this.layoutMgr instanceof LayoutManager2)) {
/* 1915 */       synchronized (getTreeLock()) {
/* 1916 */         LayoutManager2 localLayoutManager2 = (LayoutManager2)this.layoutMgr;
/* 1917 */         f = localLayoutManager2.getLayoutAlignmentX(this);
/*      */       }
/*      */     } else {
/* 1920 */       f = super.getAlignmentX();
/*      */     }
/* 1922 */     return f;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getAlignmentY()
/*      */   {
/*      */     float f;
/*      */     
/*      */ 
/*      */ 
/* 1934 */     if ((this.layoutMgr instanceof LayoutManager2)) {
/* 1935 */       synchronized (getTreeLock()) {
/* 1936 */         LayoutManager2 localLayoutManager2 = (LayoutManager2)this.layoutMgr;
/* 1937 */         f = localLayoutManager2.getLayoutAlignmentY(this);
/*      */       }
/*      */     } else {
/* 1940 */       f = super.getAlignmentY();
/*      */     }
/* 1942 */     return f;
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
/*      */   public void paint(Graphics paramGraphics)
/*      */   {
/* 1957 */     if (isShowing()) {
/* 1958 */       synchronized (getObjectLock()) {
/* 1959 */         if ((this.printing) && 
/* 1960 */           (this.printingThreads.contains(Thread.currentThread()))) {
/* 1961 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1973 */       GraphicsCallback.PaintCallback.getInstance().runComponents(getComponentsSync(), paramGraphics, 2);
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
/*      */   public void update(Graphics paramGraphics)
/*      */   {
/* 1989 */     if (isShowing()) {
/* 1990 */       if (!(this.peer instanceof LightweightPeer)) {
/* 1991 */         paramGraphics.clearRect(0, 0, this.width, this.height);
/*      */       }
/* 1993 */       paint(paramGraphics);
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
/*      */   public void print(Graphics paramGraphics)
/*      */   {
/* 2009 */     if (isShowing()) {
/* 2010 */       Thread localThread = Thread.currentThread();
/*      */       try {
/* 2012 */         synchronized (getObjectLock()) {
/* 2013 */           if (this.printingThreads == null) {
/* 2014 */             this.printingThreads = new HashSet();
/*      */           }
/* 2016 */           this.printingThreads.add(localThread);
/* 2017 */           this.printing = true;
/*      */         }
/* 2019 */         super.print(paramGraphics);
/*      */       } finally {
/* 2021 */         synchronized (getObjectLock()) {
/* 2022 */           this.printingThreads.remove(localThread);
/* 2023 */           this.printing = (!this.printingThreads.isEmpty());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2028 */       GraphicsCallback.PrintCallback.getInstance().runComponents(getComponentsSync(), paramGraphics, 2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void paintComponents(Graphics paramGraphics)
/*      */   {
/* 2039 */     if (isShowing())
/*      */     {
/* 2041 */       GraphicsCallback.PaintAllCallback.getInstance().runComponents(getComponentsSync(), paramGraphics, 4);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void lightweightPaint(Graphics paramGraphics)
/*      */   {
/* 2053 */     super.lightweightPaint(paramGraphics);
/* 2054 */     paintHeavyweightComponents(paramGraphics);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void paintHeavyweightComponents(Graphics paramGraphics)
/*      */   {
/* 2061 */     if (isShowing())
/*      */     {
/* 2063 */       GraphicsCallback.PaintHeavyweightComponentsCallback.getInstance().runComponents(getComponentsSync(), paramGraphics, 3);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printComponents(Graphics paramGraphics)
/*      */   {
/* 2075 */     if (isShowing())
/*      */     {
/* 2077 */       GraphicsCallback.PrintAllCallback.getInstance().runComponents(getComponentsSync(), paramGraphics, 4);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void lightweightPrint(Graphics paramGraphics)
/*      */   {
/* 2089 */     super.lightweightPrint(paramGraphics);
/* 2090 */     printHeavyweightComponents(paramGraphics);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void printHeavyweightComponents(Graphics paramGraphics)
/*      */   {
/* 2097 */     if (isShowing())
/*      */     {
/* 2099 */       GraphicsCallback.PrintHeavyweightComponentsCallback.getInstance().runComponents(getComponentsSync(), paramGraphics, 3);
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
/*      */   public synchronized void addContainerListener(ContainerListener paramContainerListener)
/*      */   {
/* 2117 */     if (paramContainerListener == null) {
/* 2118 */       return;
/*      */     }
/* 2120 */     this.containerListener = AWTEventMulticaster.add(this.containerListener, paramContainerListener);
/* 2121 */     this.newEventsOnly = true;
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
/*      */   public synchronized void removeContainerListener(ContainerListener paramContainerListener)
/*      */   {
/* 2137 */     if (paramContainerListener == null) {
/* 2138 */       return;
/*      */     }
/* 2140 */     this.containerListener = AWTEventMulticaster.remove(this.containerListener, paramContainerListener);
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
/*      */   public synchronized ContainerListener[] getContainerListeners()
/*      */   {
/* 2156 */     return (ContainerListener[])getListeners(ContainerListener.class);
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
/*      */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*      */   {
/* 2194 */     ContainerListener localContainerListener = null;
/* 2195 */     if (paramClass == ContainerListener.class) {
/* 2196 */       localContainerListener = this.containerListener;
/*      */     } else {
/* 2198 */       return super.getListeners(paramClass);
/*      */     }
/* 2200 */     return AWTEventMulticaster.getListeners(localContainerListener, paramClass);
/*      */   }
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*      */   {
/* 2205 */     int i = paramAWTEvent.getID();
/*      */     
/* 2207 */     if ((i == 300) || (i == 301))
/*      */     {
/* 2209 */       if (((this.eventMask & 0x2) != 0L) || (this.containerListener != null))
/*      */       {
/* 2211 */         return true;
/*      */       }
/* 2213 */       return false;
/*      */     }
/* 2215 */     return super.eventEnabled(paramAWTEvent);
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
/* 2230 */     if ((paramAWTEvent instanceof ContainerEvent)) {
/* 2231 */       processContainerEvent((ContainerEvent)paramAWTEvent);
/* 2232 */       return;
/*      */     }
/* 2234 */     super.processEvent(paramAWTEvent);
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
/*      */   protected void processContainerEvent(ContainerEvent paramContainerEvent)
/*      */   {
/* 2256 */     ContainerListener localContainerListener = this.containerListener;
/* 2257 */     if (localContainerListener != null) {
/* 2258 */       switch (paramContainerEvent.getID()) {
/*      */       case 300: 
/* 2260 */         localContainerListener.componentAdded(paramContainerEvent);
/* 2261 */         break;
/*      */       case 301: 
/* 2263 */         localContainerListener.componentRemoved(paramContainerEvent);
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
/*      */   void dispatchEventImpl(AWTEvent paramAWTEvent)
/*      */   {
/* 2278 */     if ((this.dispatcher != null) && (this.dispatcher.dispatchEvent(paramAWTEvent)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2285 */       paramAWTEvent.consume();
/* 2286 */       if (this.peer != null) {
/* 2287 */         this.peer.handleEvent(paramAWTEvent);
/*      */       }
/* 2289 */       return;
/*      */     }
/*      */     
/* 2292 */     super.dispatchEventImpl(paramAWTEvent);
/*      */     
/* 2294 */     synchronized (getTreeLock()) {
/* 2295 */       switch (paramAWTEvent.getID()) {
/*      */       case 101: 
/* 2297 */         createChildHierarchyEvents(1402, 0L, 
/* 2298 */           Toolkit.enabledOnToolkit(65536L));
/* 2299 */         break;
/*      */       case 100: 
/* 2301 */         createChildHierarchyEvents(1401, 0L, 
/* 2302 */           Toolkit.enabledOnToolkit(65536L));
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
/*      */   void dispatchEventToSelf(AWTEvent paramAWTEvent)
/*      */   {
/* 2316 */     super.dispatchEventImpl(paramAWTEvent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Component getMouseEventTarget(int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 2324 */     return getMouseEventTarget(paramInt1, paramInt2, paramBoolean, MouseEventTargetFilter.FILTER, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Component getDropTargetEventTarget(int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 2333 */     return getMouseEventTarget(paramInt1, paramInt2, paramBoolean, DropTargetEventTargetFilter.FILTER, true);
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
/*      */   private Component getMouseEventTarget(int paramInt1, int paramInt2, boolean paramBoolean1, EventTargetFilter paramEventTargetFilter, boolean paramBoolean2)
/*      */   {
/* 2353 */     Component localComponent = null;
/* 2354 */     if (paramBoolean2) {
/* 2355 */       localComponent = getMouseEventTargetImpl(paramInt1, paramInt2, paramBoolean1, paramEventTargetFilter, true, paramBoolean2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2360 */     if ((localComponent == null) || (localComponent == this)) {
/* 2361 */       localComponent = getMouseEventTargetImpl(paramInt1, paramInt2, paramBoolean1, paramEventTargetFilter, false, paramBoolean2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2366 */     return localComponent;
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
/*      */   private Component getMouseEventTargetImpl(int paramInt1, int paramInt2, boolean paramBoolean1, EventTargetFilter paramEventTargetFilter, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/* 2395 */     synchronized (getTreeLock())
/*      */     {
/* 2397 */       for (int i = 0; i < this.component.size(); i++) {
/* 2398 */         Component localComponent1 = (Component)this.component.get(i);
/* 2399 */         if ((localComponent1 != null) && (localComponent1.visible) && (((!paramBoolean2) && ((localComponent1.peer instanceof LightweightPeer))) || ((paramBoolean2) && (!(localComponent1.peer instanceof LightweightPeer)))))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 2404 */           if (localComponent1.contains(paramInt1 - localComponent1.x, paramInt2 - localComponent1.y))
/*      */           {
/*      */ 
/*      */ 
/* 2408 */             if ((localComponent1 instanceof Container)) {
/* 2409 */               Container localContainer = (Container)localComponent1;
/* 2410 */               Component localComponent2 = localContainer.getMouseEventTarget(paramInt1 - localContainer.x, paramInt2 - localContainer.y, paramBoolean1, paramEventTargetFilter, paramBoolean3);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2416 */               if (localComponent2 != null) {
/* 2417 */                 return localComponent2;
/*      */               }
/*      */             }
/* 2420 */             else if (paramEventTargetFilter.accept(localComponent1))
/*      */             {
/*      */ 
/* 2423 */               return localComponent1;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2432 */       i = ((this.peer instanceof LightweightPeer)) || (paramBoolean1) ? 1 : 0;
/* 2433 */       boolean bool = contains(paramInt1, paramInt2);
/*      */       
/*      */ 
/*      */ 
/* 2437 */       if ((bool) && (i != 0) && (paramEventTargetFilter.accept(this))) {
/* 2438 */         return this;
/*      */       }
/*      */       
/* 2441 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract interface EventTargetFilter {
/*      */     public abstract boolean accept(Component paramComponent);
/*      */   }
/*      */   
/*      */   static class MouseEventTargetFilter implements Container.EventTargetFilter {
/* 2450 */     static final Container.EventTargetFilter FILTER = new MouseEventTargetFilter();
/*      */     
/*      */ 
/*      */     public boolean accept(Component paramComponent)
/*      */     {
/* 2455 */       return ((paramComponent.eventMask & 0x20) != 0L) || ((paramComponent.eventMask & 0x10) != 0L) || ((paramComponent.eventMask & 0x20000) != 0L) || (paramComponent.mouseListener != null) || (paramComponent.mouseMotionListener != null) || (paramComponent.mouseWheelListener != null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static class DropTargetEventTargetFilter
/*      */     implements Container.EventTargetFilter
/*      */   {
/* 2465 */     static final Container.EventTargetFilter FILTER = new DropTargetEventTargetFilter();
/*      */     
/*      */ 
/*      */     public boolean accept(Component paramComponent)
/*      */     {
/* 2470 */       DropTarget localDropTarget = paramComponent.getDropTarget();
/* 2471 */       return (localDropTarget != null) && (localDropTarget.isActive());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void proxyEnableEvents(long paramLong)
/*      */   {
/* 2483 */     if ((this.peer instanceof LightweightPeer))
/*      */     {
/*      */ 
/* 2486 */       if (this.parent != null) {
/* 2487 */         this.parent.proxyEnableEvents(paramLong);
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/* 2495 */     else if (this.dispatcher != null) {
/* 2496 */       this.dispatcher.enableEvents(paramLong);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void deliverEvent(Event paramEvent)
/*      */   {
/* 2507 */     Component localComponent = getComponentAt(paramEvent.x, paramEvent.y);
/* 2508 */     if ((localComponent != null) && (localComponent != this)) {
/* 2509 */       paramEvent.translate(-localComponent.x, -localComponent.y);
/* 2510 */       localComponent.deliverEvent(paramEvent);
/*      */     } else {
/* 2512 */       postEvent(paramEvent);
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
/*      */   public Component getComponentAt(int paramInt1, int paramInt2)
/*      */   {
/* 2535 */     return locate(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public Component locate(int paramInt1, int paramInt2)
/*      */   {
/* 2544 */     if (!contains(paramInt1, paramInt2)) {
/* 2545 */       return null;
/*      */     }
/* 2547 */     synchronized (getTreeLock()) {
/*      */       Component localComponent;
/* 2549 */       for (int i = 0; i < this.component.size(); i++) {
/* 2550 */         localComponent = (Component)this.component.get(i);
/* 2551 */         if ((localComponent != null) && (!(localComponent.peer instanceof LightweightPeer)))
/*      */         {
/* 2553 */           if (localComponent.contains(paramInt1 - localComponent.x, paramInt2 - localComponent.y)) {
/* 2554 */             return localComponent;
/*      */           }
/*      */         }
/*      */       }
/* 2558 */       for (i = 0; i < this.component.size(); i++) {
/* 2559 */         localComponent = (Component)this.component.get(i);
/* 2560 */         if ((localComponent != null) && ((localComponent.peer instanceof LightweightPeer)))
/*      */         {
/* 2562 */           if (localComponent.contains(paramInt1 - localComponent.x, paramInt2 - localComponent.y)) {
/* 2563 */             return localComponent;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2568 */     return this;
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
/*      */   public Component getComponentAt(Point paramPoint)
/*      */   {
/* 2581 */     return getComponentAt(paramPoint.x, paramPoint.y);
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
/*      */   public Point getMousePosition(boolean paramBoolean)
/*      */     throws HeadlessException
/*      */   {
/* 2604 */     if (GraphicsEnvironment.isHeadless()) {
/* 2605 */       throw new HeadlessException();
/*      */     }
/* 2607 */     PointerInfo localPointerInfo = (PointerInfo)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public PointerInfo run() {
/* 2610 */         return MouseInfo.getPointerInfo();
/*      */       }
/*      */     });
/*      */     
/* 2614 */     synchronized (getTreeLock()) {
/* 2615 */       Component localComponent = findUnderMouseInWindow(localPointerInfo);
/* 2616 */       if (isSameOrAncestorOf(localComponent, paramBoolean)) {
/* 2617 */         return pointRelativeToComponent(localPointerInfo.getLocation());
/*      */       }
/* 2619 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   boolean isSameOrAncestorOf(Component paramComponent, boolean paramBoolean) {
/* 2624 */     return (this == paramComponent) || ((paramBoolean) && (isParentOf(paramComponent)));
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
/*      */   public Component findComponentAt(int paramInt1, int paramInt2)
/*      */   {
/* 2651 */     return findComponentAt(paramInt1, paramInt2, true);
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
/*      */   final Component findComponentAt(int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 2664 */     synchronized (getTreeLock()) {
/* 2665 */       if (isRecursivelyVisible()) {
/* 2666 */         return findComponentAtImpl(paramInt1, paramInt2, paramBoolean);
/*      */       }
/*      */     }
/* 2669 */     return null;
/*      */   }
/*      */   
/*      */   final Component findComponentAtImpl(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2673 */     checkTreeLock();
/*      */     
/* 2675 */     if ((!contains(paramInt1, paramInt2)) || (!this.visible) || ((!paramBoolean) && (!this.enabled))) {
/* 2676 */       return null;
/*      */     }
/*      */     
/*      */     Component localComponent;
/* 2680 */     for (int i = 0; i < this.component.size(); i++) {
/* 2681 */       localComponent = (Component)this.component.get(i);
/* 2682 */       if ((localComponent != null) && (!(localComponent.peer instanceof LightweightPeer)))
/*      */       {
/* 2684 */         if ((localComponent instanceof Container)) {
/* 2685 */           localComponent = ((Container)localComponent).findComponentAtImpl(paramInt1 - localComponent.x, paramInt2 - localComponent.y, paramBoolean);
/*      */         }
/*      */         else
/*      */         {
/* 2689 */           localComponent = localComponent.getComponentAt(paramInt1 - localComponent.x, paramInt2 - localComponent.y);
/*      */         }
/* 2691 */         if ((localComponent != null) && (localComponent.visible) && ((paramBoolean) || (localComponent.enabled)))
/*      */         {
/*      */ 
/* 2694 */           return localComponent;
/*      */         }
/*      */       }
/*      */     }
/* 2698 */     for (i = 0; i < this.component.size(); i++) {
/* 2699 */       localComponent = (Component)this.component.get(i);
/* 2700 */       if ((localComponent != null) && ((localComponent.peer instanceof LightweightPeer)))
/*      */       {
/* 2702 */         if ((localComponent instanceof Container)) {
/* 2703 */           localComponent = ((Container)localComponent).findComponentAtImpl(paramInt1 - localComponent.x, paramInt2 - localComponent.y, paramBoolean);
/*      */         }
/*      */         else
/*      */         {
/* 2707 */           localComponent = localComponent.getComponentAt(paramInt1 - localComponent.x, paramInt2 - localComponent.y);
/*      */         }
/* 2709 */         if ((localComponent != null) && (localComponent.visible) && ((paramBoolean) || (localComponent.enabled)))
/*      */         {
/*      */ 
/* 2712 */           return localComponent;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2717 */     return this;
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
/*      */   public Component findComponentAt(Point paramPoint)
/*      */   {
/* 2744 */     return findComponentAt(paramPoint.x, paramPoint.y);
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
/* 2757 */     synchronized (getTreeLock())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2762 */       super.addNotify();
/* 2763 */       if (!(this.peer instanceof LightweightPeer)) {
/* 2764 */         this.dispatcher = new LightweightDispatcher(this);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */       for (int i = 0; i < this.component.size(); i++) {
/* 2773 */         ((Component)this.component.get(i)).addNotify();
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
/* 2788 */     synchronized (getTreeLock())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2794 */       for (int i = this.component.size() - 1; i >= 0; i--) {
/* 2795 */         Component localComponent = (Component)this.component.get(i);
/* 2796 */         if (localComponent != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2803 */           localComponent.setAutoFocusTransferOnDisposal(false);
/* 2804 */           localComponent.removeNotify();
/* 2805 */           localComponent.setAutoFocusTransferOnDisposal(true);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2811 */       if ((containsFocus()) && (KeyboardFocusManager.isAutoFocusTransferEnabledFor(this)) && 
/* 2812 */         (!transferFocus(false))) {
/* 2813 */         transferFocusBackward(true);
/*      */       }
/*      */       
/* 2816 */       if (this.dispatcher != null) {
/* 2817 */         this.dispatcher.dispose();
/* 2818 */         this.dispatcher = null;
/*      */       }
/* 2820 */       super.removeNotify();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAncestorOf(Component paramComponent)
/*      */   {
/*      */     Container localContainer;
/*      */     
/*      */ 
/*      */ 
/* 2834 */     if ((paramComponent == null) || ((localContainer = paramComponent.getParent()) == null)) {
/* 2835 */       return false;
/*      */     }
/* 2837 */     while (localContainer != null) {
/* 2838 */       if (localContainer == this) {
/* 2839 */         return true;
/*      */       }
/* 2841 */       localContainer = localContainer.getParent();
/*      */     }
/* 2843 */     return false;
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
/*      */   private void startLWModal()
/*      */   {
/* 2866 */     this.modalAppContext = AppContext.getAppContext();
/*      */     
/*      */ 
/*      */ 
/* 2870 */     long l = Toolkit.getEventQueue().getMostRecentKeyEventTime();
/* 2871 */     Component localComponent = Component.isInstanceOf(this, "javax.swing.JInternalFrame") ? ((JInternalFrame)this).getMostRecentFocusOwner() : null;
/* 2872 */     if (localComponent != null)
/*      */     {
/* 2874 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().enqueueKeyEvents(l, localComponent);
/*      */     }
/*      */     
/*      */ 
/*      */     final Container localContainer;
/*      */     
/* 2880 */     synchronized (getTreeLock()) {
/* 2881 */       localContainer = getHeavyweightContainer();
/* 2882 */       if (localContainer.modalComp != null) {
/* 2883 */         this.modalComp = localContainer.modalComp;
/* 2884 */         localContainer.modalComp = this;
/* 2885 */         return;
/*      */       }
/*      */       
/* 2888 */       localContainer.modalComp = this;
/*      */     }
/*      */     
/*      */ 
/* 2892 */     ??? = new Runnable()
/*      */     {
/*      */       public void run() {
/* 2895 */         EventDispatchThread localEventDispatchThread = (EventDispatchThread)Thread.currentThread();
/* 2896 */         localEventDispatchThread.pumpEventsForHierarchy(new Conditional()
/*      */         {
/*      */ 
/* 2899 */           public boolean evaluate() { return (Container.this.windowClosingException == null) && (Container.3.this.val$nativeContainer.modalComp != null); } }, Container.this);
/*      */       }
/*      */     };
/*      */     
/*      */ 
/*      */ 
/* 2905 */     if (EventQueue.isDispatchThread())
/*      */     {
/*      */ 
/* 2908 */       SequencedEvent localSequencedEvent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getCurrentSequencedEvent();
/* 2909 */       if (localSequencedEvent != null) {
/* 2910 */         localSequencedEvent.dispose();
/*      */       }
/*      */       
/* 2913 */       ((Runnable)???).run();
/*      */     } else {
/* 2915 */       synchronized (getTreeLock())
/*      */       {
/* 2917 */         Toolkit.getEventQueue().postEvent(new PeerEvent(this, (Runnable)???, 1L));
/*      */         for (;;)
/*      */         {
/* 2920 */           if ((this.windowClosingException == null) && (localContainer.modalComp != null))
/*      */           {
/*      */             try
/*      */             {
/* 2924 */               getTreeLock().wait();
/*      */             }
/*      */             catch (InterruptedException localInterruptedException) {}
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2931 */     if (this.windowClosingException != null) {
/* 2932 */       this.windowClosingException.fillInStackTrace();
/* 2933 */       throw this.windowClosingException;
/*      */     }
/* 2935 */     if (localComponent != null)
/*      */     {
/* 2937 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().dequeueKeyEvents(l, localComponent);
/*      */     }
/*      */   }
/*      */   
/*      */   private void stopLWModal() {
/* 2942 */     synchronized (getTreeLock()) {
/* 2943 */       if (this.modalAppContext != null) {
/* 2944 */         Container localContainer = getHeavyweightContainer();
/* 2945 */         if (localContainer != null) {
/* 2946 */           if (this.modalComp != null) {
/* 2947 */             localContainer.modalComp = this.modalComp;
/* 2948 */             this.modalComp = null;
/* 2949 */             return;
/*      */           }
/*      */           
/* 2952 */           localContainer.modalComp = null;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2957 */         SunToolkit.postEvent(this.modalAppContext, new PeerEvent(this, new WakingRunnable(), 1L));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2962 */       EventQueue.invokeLater(new WakingRunnable());
/* 2963 */       getTreeLock().notifyAll();
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
/*      */   protected String paramString()
/*      */   {
/* 2984 */     String str = super.paramString();
/* 2985 */     LayoutManager localLayoutManager = this.layoutMgr;
/* 2986 */     if (localLayoutManager != null) {
/* 2987 */       str = str + ",layout=" + localLayoutManager.getClass().getName();
/*      */     }
/* 2989 */     return str;
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
/*      */   public void list(PrintStream paramPrintStream, int paramInt)
/*      */   {
/* 3008 */     super.list(paramPrintStream, paramInt);
/* 3009 */     synchronized (getTreeLock()) {
/* 3010 */       for (int i = 0; i < this.component.size(); i++) {
/* 3011 */         Component localComponent = (Component)this.component.get(i);
/* 3012 */         if (localComponent != null) {
/* 3013 */           localComponent.list(paramPrintStream, paramInt + 1);
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
/*      */   public void list(PrintWriter paramPrintWriter, int paramInt)
/*      */   {
/* 3035 */     super.list(paramPrintWriter, paramInt);
/* 3036 */     synchronized (getTreeLock()) {
/* 3037 */       for (int i = 0; i < this.component.size(); i++) {
/* 3038 */         Component localComponent = (Component)this.component.get(i);
/* 3039 */         if (localComponent != null) {
/* 3040 */           localComponent.list(paramPrintWriter, paramInt + 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFocusTraversalKeys(int paramInt, Set<? extends AWTKeyStroke> paramSet)
/*      */   {
/* 3129 */     if ((paramInt < 0) || (paramInt >= 4)) {
/* 3130 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3135 */     setFocusTraversalKeys_NoIDCheck(paramInt, paramSet);
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
/*      */   public Set<AWTKeyStroke> getFocusTraversalKeys(int paramInt)
/*      */   {
/* 3168 */     if ((paramInt < 0) || (paramInt >= 4)) {
/* 3169 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3174 */     return getFocusTraversalKeys_NoIDCheck(paramInt);
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
/*      */   public boolean areFocusTraversalKeysSet(int paramInt)
/*      */   {
/* 3198 */     if ((paramInt < 0) || (paramInt >= 4)) {
/* 3199 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */     
/* 3202 */     return (this.focusTraversalKeys != null) && (this.focusTraversalKeys[paramInt] != null);
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
/*      */   public boolean isFocusCycleRoot(Container paramContainer)
/*      */   {
/* 3222 */     if ((isFocusCycleRoot()) && (paramContainer == this)) {
/* 3223 */       return true;
/*      */     }
/* 3225 */     return super.isFocusCycleRoot(paramContainer);
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
/*      */   private Container findTraversalRoot()
/*      */   {
/* 3238 */     Container localContainer1 = KeyboardFocusManager.getCurrentKeyboardFocusManager().getCurrentFocusCycleRoot();
/*      */     
/*      */     Container localContainer2;
/* 3241 */     if (localContainer1 == this) {
/* 3242 */       localContainer2 = this;
/*      */     } else {
/* 3244 */       localContainer2 = getFocusCycleRootAncestor();
/* 3245 */       if (localContainer2 == null) {
/* 3246 */         localContainer2 = this;
/*      */       }
/*      */     }
/*      */     
/* 3250 */     if (localContainer2 != localContainer1)
/*      */     {
/* 3252 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalCurrentFocusCycleRootPriv(localContainer2);
/*      */     }
/* 3254 */     return localContainer2;
/*      */   }
/*      */   
/*      */   final boolean containsFocus()
/*      */   {
/* 3259 */     Component localComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 3260 */     return isParentOf(localComponent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isParentOf(Component paramComponent)
/*      */   {
/* 3270 */     synchronized (getTreeLock()) {
/* 3271 */       while ((paramComponent != null) && (paramComponent != this) && (!(paramComponent instanceof Window))) {
/* 3272 */         paramComponent = paramComponent.getParent();
/*      */       }
/* 3274 */       return paramComponent == this;
/*      */     }
/*      */   }
/*      */   
/*      */   void clearMostRecentFocusOwnerOnHide() {
/* 3279 */     int i = 0;
/* 3280 */     Window localWindow = null;
/*      */     
/* 3282 */     synchronized (getTreeLock()) {
/* 3283 */       localWindow = getContainingWindow();
/* 3284 */       if (localWindow != null) {
/* 3285 */         Component localComponent1 = KeyboardFocusManager.getMostRecentFocusOwner(localWindow);
/* 3286 */         i = (localComponent1 == this) || (isParentOf(localComponent1)) ? 1 : 0;
/*      */         
/*      */ 
/* 3289 */         synchronized (KeyboardFocusManager.class) {
/* 3290 */           Component localComponent2 = localWindow.getTemporaryLostComponent();
/* 3291 */           if ((isParentOf(localComponent2)) || (localComponent2 == this)) {
/* 3292 */             localWindow.setTemporaryLostComponent(null);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3298 */     if (i != 0) {
/* 3299 */       KeyboardFocusManager.setMostRecentFocusOwner(localWindow, null);
/*      */     }
/*      */   }
/*      */   
/*      */   void clearCurrentFocusCycleRootOnHide()
/*      */   {
/* 3305 */     KeyboardFocusManager localKeyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 3306 */     Container localContainer = localKeyboardFocusManager.getCurrentFocusCycleRoot();
/*      */     
/* 3308 */     if ((localContainer == this) || (isParentOf(localContainer))) {
/* 3309 */       localKeyboardFocusManager.setGlobalCurrentFocusCycleRootPriv(null);
/*      */     }
/*      */   }
/*      */   
/*      */   void clearLightweightDispatcherOnRemove(Component paramComponent)
/*      */   {
/* 3315 */     if (this.dispatcher != null) {
/* 3316 */       this.dispatcher.removeReferences(paramComponent);
/*      */     }
/*      */     else {
/* 3319 */       super.clearLightweightDispatcherOnRemove(paramComponent);
/*      */     }
/*      */   }
/*      */   
/*      */   final Container getTraversalRoot() {
/* 3324 */     if (isFocusCycleRoot()) {
/* 3325 */       return findTraversalRoot();
/*      */     }
/*      */     
/* 3328 */     return super.getTraversalRoot();
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
/*      */   public void setFocusTraversalPolicy(FocusTraversalPolicy paramFocusTraversalPolicy)
/*      */   {
/*      */     FocusTraversalPolicy localFocusTraversalPolicy;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3354 */     synchronized (this) {
/* 3355 */       localFocusTraversalPolicy = this.focusTraversalPolicy;
/* 3356 */       this.focusTraversalPolicy = paramFocusTraversalPolicy;
/*      */     }
/* 3358 */     firePropertyChange("focusTraversalPolicy", localFocusTraversalPolicy, paramFocusTraversalPolicy);
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
/*      */   public FocusTraversalPolicy getFocusTraversalPolicy()
/*      */   {
/* 3376 */     if ((!isFocusTraversalPolicyProvider()) && (!isFocusCycleRoot())) {
/* 3377 */       return null;
/*      */     }
/*      */     
/* 3380 */     FocusTraversalPolicy localFocusTraversalPolicy = this.focusTraversalPolicy;
/* 3381 */     if (localFocusTraversalPolicy != null) {
/* 3382 */       return localFocusTraversalPolicy;
/*      */     }
/*      */     
/* 3385 */     Container localContainer = getFocusCycleRootAncestor();
/* 3386 */     if (localContainer != null) {
/* 3387 */       return localContainer.getFocusTraversalPolicy();
/*      */     }
/*      */     
/* 3390 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalPolicy();
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
/*      */   public boolean isFocusTraversalPolicySet()
/*      */   {
/* 3404 */     return this.focusTraversalPolicy != null;
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
/*      */   public void setFocusCycleRoot(boolean paramBoolean)
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
/* 3434 */     synchronized (this) {
/* 3435 */       bool = this.focusCycleRoot;
/* 3436 */       this.focusCycleRoot = paramBoolean;
/*      */     }
/* 3438 */     firePropertyChange("focusCycleRoot", bool, paramBoolean);
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
/*      */   public boolean isFocusCycleRoot()
/*      */   {
/* 3460 */     return this.focusCycleRoot;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setFocusTraversalPolicyProvider(boolean paramBoolean)
/*      */   {
/*      */     boolean bool;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3479 */     synchronized (this) {
/* 3480 */       bool = this.focusTraversalPolicyProvider;
/* 3481 */       this.focusTraversalPolicyProvider = paramBoolean;
/*      */     }
/* 3483 */     firePropertyChange("focusTraversalPolicyProvider", bool, paramBoolean);
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
/*      */   public final boolean isFocusTraversalPolicyProvider()
/*      */   {
/* 3505 */     return this.focusTraversalPolicyProvider;
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
/*      */   public void transferFocusDownCycle()
/*      */   {
/* 3521 */     if (isFocusCycleRoot())
/*      */     {
/* 3523 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalCurrentFocusCycleRootPriv(this);
/*      */       
/* 3525 */       Component localComponent = getFocusTraversalPolicy().getDefaultComponent(this);
/* 3526 */       if (localComponent != null) {
/* 3527 */         localComponent.requestFocus(CausedFocusEvent.Cause.TRAVERSAL_DOWN);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void preProcessKeyEvent(KeyEvent paramKeyEvent) {
/* 3533 */     Container localContainer = this.parent;
/* 3534 */     if (localContainer != null) {
/* 3535 */       localContainer.preProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   void postProcessKeyEvent(KeyEvent paramKeyEvent) {
/* 3540 */     Container localContainer = this.parent;
/* 3541 */     if (localContainer != null) {
/* 3542 */       localContainer.postProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   boolean postsOldMouseEvents() {
/* 3547 */     return true;
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
/*      */   public void applyComponentOrientation(ComponentOrientation paramComponentOrientation)
/*      */   {
/* 3566 */     super.applyComponentOrientation(paramComponentOrientation);
/* 3567 */     synchronized (getTreeLock()) {
/* 3568 */       for (int i = 0; i < this.component.size(); i++) {
/* 3569 */         Component localComponent = (Component)this.component.get(i);
/* 3570 */         localComponent.applyComponentOrientation(paramComponentOrientation);
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
/*      */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 3609 */     super.addPropertyChangeListener(paramPropertyChangeListener);
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
/* 3650 */     super.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3659 */   private int containerSerializedDataVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3688 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 3689 */     localPutField.put("ncomponents", this.component.size());
/* 3690 */     localPutField.put("component", getComponentsSync());
/* 3691 */     localPutField.put("layoutMgr", this.layoutMgr);
/* 3692 */     localPutField.put("dispatcher", this.dispatcher);
/* 3693 */     localPutField.put("maxSize", this.maxSize);
/* 3694 */     localPutField.put("focusCycleRoot", this.focusCycleRoot);
/* 3695 */     localPutField.put("containerSerializedDataVersion", this.containerSerializedDataVersion);
/* 3696 */     localPutField.put("focusTraversalPolicyProvider", this.focusTraversalPolicyProvider);
/* 3697 */     paramObjectOutputStream.writeFields();
/*      */     
/* 3699 */     AWTEventMulticaster.save(paramObjectOutputStream, "containerL", this.containerListener);
/* 3700 */     paramObjectOutputStream.writeObject(null);
/*      */     
/* 3702 */     if ((this.focusTraversalPolicy instanceof Serializable)) {
/* 3703 */       paramObjectOutputStream.writeObject(this.focusTraversalPolicy);
/*      */     } else {
/* 3705 */       paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException
/*      */   {
/* 3728 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 3729 */     Component[] arrayOfComponent = (Component[])localGetField.get("component", EMPTY_ARRAY);
/* 3730 */     int i = Integer.valueOf(localGetField.get("ncomponents", 0)).intValue();
/* 3731 */     this.component = new ArrayList(i);
/* 3732 */     for (int j = 0; j < i; j++) {
/* 3733 */       this.component.add(arrayOfComponent[j]);
/*      */     }
/* 3735 */     this.layoutMgr = ((LayoutManager)localGetField.get("layoutMgr", null));
/* 3736 */     this.dispatcher = ((LightweightDispatcher)localGetField.get("dispatcher", null));
/*      */     
/* 3738 */     if (this.maxSize == null) {
/* 3739 */       this.maxSize = ((Dimension)localGetField.get("maxSize", null));
/*      */     }
/* 3741 */     this.focusCycleRoot = localGetField.get("focusCycleRoot", false);
/* 3742 */     this.containerSerializedDataVersion = localGetField.get("containerSerializedDataVersion", 1);
/* 3743 */     this.focusTraversalPolicyProvider = localGetField.get("focusTraversalPolicyProvider", false);
/* 3744 */     List localList = this.component;
/* 3745 */     for (Object localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Component)((Iterator)localObject1).next();
/* 3746 */       ((Component)localObject2).parent = this;
/* 3747 */       adjustListeningChildren(32768L, ((Component)localObject2)
/* 3748 */         .numListening(32768L));
/* 3749 */       adjustListeningChildren(65536L, ((Component)localObject2)
/* 3750 */         .numListening(65536L));
/* 3751 */       adjustDescendants(((Component)localObject2).countHierarchyMembers());
/*      */     }
/*      */     
/*      */     Object localObject2;
/* 3755 */     while (null != (localObject1 = paramObjectInputStream.readObject())) {
/* 3756 */       localObject2 = ((String)localObject1).intern();
/*      */       
/* 3758 */       if ("containerL" == localObject2) {
/* 3759 */         addContainerListener((ContainerListener)paramObjectInputStream.readObject());
/*      */       }
/*      */       else {
/* 3762 */         paramObjectInputStream.readObject();
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 3767 */       localObject2 = paramObjectInputStream.readObject();
/* 3768 */       if ((localObject2 instanceof FocusTraversalPolicy)) {
/* 3769 */         this.focusTraversalPolicy = ((FocusTraversalPolicy)localObject2);
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (OptionalDataException localOptionalDataException)
/*      */     {
/*      */ 
/* 3778 */       if (!localOptionalDataException.eof) {
/* 3779 */         throw localOptionalDataException;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class WakingRunnable
/*      */     implements Runnable
/*      */   {
/*      */     public void run() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected class AccessibleAWTContainer
/*      */     extends Component.AccessibleAWTComponent
/*      */   {
/*      */     private static final long serialVersionUID = 5081320404842566097L;
/*      */     
/*      */ 
/*      */ 
/*      */     private volatile transient int propertyListenersCount;
/*      */     
/*      */ 
/*      */ 
/*      */     protected ContainerListener accessibleContainerHandler;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int getAccessibleChildrenCount()
/*      */     {
/* 3814 */       return Container.this.getAccessibleChildrenCount();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Accessible getAccessibleChild(int paramInt)
/*      */     {
/* 3824 */       return Container.this.getAccessibleChild(paramInt);
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
/*      */     public Accessible getAccessibleAt(Point paramPoint)
/*      */     {
/* 3838 */       return Container.this.getAccessibleAt(paramPoint);
/*      */     }
/*      */     
/*      */     protected AccessibleAWTContainer()
/*      */     {
/* 3799 */       super();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3845 */       this.propertyListenersCount = 0;
/*      */       
/* 3847 */       this.accessibleContainerHandler = null;
/*      */     }
/*      */     
/*      */     protected class AccessibleContainerHandler
/*      */       implements ContainerListener
/*      */     {
/*      */       protected AccessibleContainerHandler() {}
/*      */       
/*      */       public void componentAdded(ContainerEvent paramContainerEvent)
/*      */       {
/* 3857 */         Component localComponent = paramContainerEvent.getChild();
/* 3858 */         if ((localComponent != null) && ((localComponent instanceof Accessible)))
/* 3859 */           Container.AccessibleAWTContainer.this.firePropertyChange("AccessibleChild", null, ((Accessible)localComponent)
/*      */           
/* 3861 */             .getAccessibleContext());
/*      */       }
/*      */       
/*      */       public void componentRemoved(ContainerEvent paramContainerEvent) {
/* 3865 */         Component localComponent = paramContainerEvent.getChild();
/* 3866 */         if ((localComponent != null) && ((localComponent instanceof Accessible))) {
/* 3867 */           Container.AccessibleAWTContainer.this.firePropertyChange("AccessibleChild", ((Accessible)localComponent)
/*      */           
/* 3869 */             .getAccessibleContext(), null);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */     {
/* 3880 */       if (this.accessibleContainerHandler == null) {
/* 3881 */         this.accessibleContainerHandler = new AccessibleContainerHandler();
/*      */       }
/* 3883 */       if (this.propertyListenersCount++ == 0) {
/* 3884 */         Container.this.addContainerListener(this.accessibleContainerHandler);
/*      */       }
/* 3886 */       super.addPropertyChangeListener(paramPropertyChangeListener);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */     {
/* 3897 */       if (--this.propertyListenersCount == 0) {
/* 3898 */         Container.this.removeContainerListener(this.accessibleContainerHandler);
/*      */       }
/* 3900 */       super.removePropertyChangeListener(paramPropertyChangeListener);
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
/*      */   Accessible getAccessibleAt(Point paramPoint)
/*      */   {
/* 3917 */     synchronized (getTreeLock()) { Object localObject2;
/* 3918 */       if ((this instanceof Accessible)) {
/* 3919 */         localObject1 = (Accessible)this;
/* 3920 */         AccessibleContext localAccessibleContext = ((Accessible)localObject1).getAccessibleContext();
/* 3921 */         if (localAccessibleContext != null)
/*      */         {
/*      */ 
/* 3924 */           int k = localAccessibleContext.getAccessibleChildrenCount();
/* 3925 */           for (int m = 0; m < k; m++) {
/* 3926 */             localObject1 = localAccessibleContext.getAccessibleChild(m);
/* 3927 */             if (localObject1 != null) {
/* 3928 */               localAccessibleContext = ((Accessible)localObject1).getAccessibleContext();
/* 3929 */               if (localAccessibleContext != null) {
/* 3930 */                 AccessibleComponent localAccessibleComponent = localAccessibleContext.getAccessibleComponent();
/* 3931 */                 if ((localAccessibleComponent != null) && (localAccessibleComponent.isShowing())) {
/* 3932 */                   localObject2 = localAccessibleComponent.getLocation();
/* 3933 */                   Point localPoint2 = new Point(paramPoint.x - ((Point)localObject2).x, paramPoint.y - ((Point)localObject2).y);
/*      */                   
/* 3935 */                   if (localAccessibleComponent.contains(localPoint2)) {
/* 3936 */                     return (Accessible)localObject1;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 3943 */         return (Accessible)this;
/*      */       }
/* 3945 */       Object localObject1 = this;
/* 3946 */       if (!contains(paramPoint.x, paramPoint.y)) {
/* 3947 */         localObject1 = null;
/*      */       } else {
/* 3949 */         int i = getComponentCount();
/* 3950 */         for (int j = 0; j < i; j++) {
/* 3951 */           localObject2 = getComponent(j);
/* 3952 */           if ((localObject2 != null) && (((Component)localObject2).isShowing())) {
/* 3953 */             Point localPoint1 = ((Component)localObject2).getLocation();
/* 3954 */             if (((Component)localObject2).contains(paramPoint.x - localPoint1.x, paramPoint.y - localPoint1.y)) {
/* 3955 */               localObject1 = localObject2;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 3960 */       if ((localObject1 instanceof Accessible)) {
/* 3961 */         return (Accessible)localObject1;
/*      */       }
/*      */       
/* 3964 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getAccessibleChildrenCount()
/*      */   {
/* 3976 */     synchronized (getTreeLock()) {
/* 3977 */       int i = 0;
/* 3978 */       Component[] arrayOfComponent = getComponents();
/* 3979 */       for (int j = 0; j < arrayOfComponent.length; j++) {
/* 3980 */         if ((arrayOfComponent[j] instanceof Accessible)) {
/* 3981 */           i++;
/*      */         }
/*      */       }
/* 3984 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Accessible getAccessibleChild(int paramInt)
/*      */   {
/* 3995 */     synchronized (getTreeLock()) {
/* 3996 */       Component[] arrayOfComponent = getComponents();
/* 3997 */       int i = 0;
/* 3998 */       for (int j = 0; j < arrayOfComponent.length; j++) {
/* 3999 */         if ((arrayOfComponent[j] instanceof Accessible)) {
/* 4000 */           if (i == paramInt) {
/* 4001 */             return (Accessible)arrayOfComponent[j];
/*      */           }
/* 4003 */           i++;
/*      */         }
/*      */       }
/*      */       
/* 4007 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   final void increaseComponentCount(Component paramComponent)
/*      */   {
/* 4014 */     synchronized (getTreeLock()) {
/* 4015 */       if (!paramComponent.isDisplayable()) {
/* 4016 */         throw new IllegalStateException("Peer does not exist while invoking the increaseComponentCount() method");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4021 */       int i = 0;
/* 4022 */       int j = 0;
/*      */       
/* 4024 */       if ((paramComponent instanceof Container)) {
/* 4025 */         j = ((Container)paramComponent).numOfLWComponents;
/* 4026 */         i = ((Container)paramComponent).numOfHWComponents;
/*      */       }
/* 4028 */       if (paramComponent.isLightweight()) {
/* 4029 */         j++;
/*      */       } else {
/* 4031 */         i++;
/*      */       }
/*      */       
/* 4034 */       for (Container localContainer = this; localContainer != null; localContainer = localContainer.getContainer()) {
/* 4035 */         localContainer.numOfLWComponents += j;
/* 4036 */         localContainer.numOfHWComponents += i;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   final void decreaseComponentCount(Component paramComponent) {
/* 4042 */     synchronized (getTreeLock()) {
/* 4043 */       if (!paramComponent.isDisplayable()) {
/* 4044 */         throw new IllegalStateException("Peer does not exist while invoking the decreaseComponentCount() method");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4049 */       int i = 0;
/* 4050 */       int j = 0;
/*      */       
/* 4052 */       if ((paramComponent instanceof Container)) {
/* 4053 */         j = ((Container)paramComponent).numOfLWComponents;
/* 4054 */         i = ((Container)paramComponent).numOfHWComponents;
/*      */       }
/* 4056 */       if (paramComponent.isLightweight()) {
/* 4057 */         j++;
/*      */       } else {
/* 4059 */         i++;
/*      */       }
/*      */       
/* 4062 */       for (Container localContainer = this; localContainer != null; localContainer = localContainer.getContainer()) {
/* 4063 */         localContainer.numOfLWComponents -= j;
/* 4064 */         localContainer.numOfHWComponents -= i;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private int getTopmostComponentIndex() {
/* 4070 */     checkTreeLock();
/* 4071 */     if (getComponentCount() > 0) {
/* 4072 */       return 0;
/*      */     }
/* 4074 */     return -1;
/*      */   }
/*      */   
/*      */   private int getBottommostComponentIndex() {
/* 4078 */     checkTreeLock();
/* 4079 */     if (getComponentCount() > 0) {
/* 4080 */       return getComponentCount() - 1;
/*      */     }
/* 4082 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final Region getOpaqueShape()
/*      */   {
/* 4091 */     checkTreeLock();
/* 4092 */     if ((isLightweight()) && (isNonOpaqueForMixing()) && 
/* 4093 */       (hasLightweightDescendants()))
/*      */     {
/* 4095 */       Region localRegion = Region.EMPTY_REGION;
/* 4096 */       for (int i = 0; i < getComponentCount(); i++) {
/* 4097 */         Component localComponent = getComponent(i);
/* 4098 */         if ((localComponent.isLightweight()) && (localComponent.isShowing())) {
/* 4099 */           localRegion = localRegion.getUnion(localComponent.getOpaqueShape());
/*      */         }
/*      */       }
/* 4102 */       return localRegion.getIntersection(getNormalShape());
/*      */     }
/* 4104 */     return super.getOpaqueShape();
/*      */   }
/*      */   
/*      */   final void recursiveSubtractAndApplyShape(Region paramRegion) {
/* 4108 */     recursiveSubtractAndApplyShape(paramRegion, getTopmostComponentIndex(), getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveSubtractAndApplyShape(Region paramRegion, int paramInt) {
/* 4112 */     recursiveSubtractAndApplyShape(paramRegion, paramInt, getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveSubtractAndApplyShape(Region paramRegion, int paramInt1, int paramInt2) {
/* 4116 */     checkTreeLock();
/* 4117 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4118 */       mixingLog.fine("this = " + this + "; shape=" + paramRegion + "; fromZ=" + paramInt1 + "; toZ=" + paramInt2);
/*      */     }
/*      */     
/* 4121 */     if (paramInt1 == -1) {
/* 4122 */       return;
/*      */     }
/* 4124 */     if (paramRegion.isEmpty()) {
/* 4125 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4130 */     if ((getLayout() != null) && (!isValid())) {
/* 4131 */       return;
/*      */     }
/* 4133 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 4134 */       Component localComponent = getComponent(i);
/* 4135 */       if (!localComponent.isLightweight()) {
/* 4136 */         localComponent.subtractAndApplyShape(paramRegion);
/* 4137 */       } else if (((localComponent instanceof Container)) && 
/* 4138 */         (((Container)localComponent).hasHeavyweightDescendants()) && (localComponent.isShowing())) {
/* 4139 */         ((Container)localComponent).recursiveSubtractAndApplyShape(paramRegion);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   final void recursiveApplyCurrentShape() {
/* 4145 */     recursiveApplyCurrentShape(getTopmostComponentIndex(), getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveApplyCurrentShape(int paramInt) {
/* 4149 */     recursiveApplyCurrentShape(paramInt, getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveApplyCurrentShape(int paramInt1, int paramInt2) {
/* 4153 */     checkTreeLock();
/* 4154 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4155 */       mixingLog.fine("this = " + this + "; fromZ=" + paramInt1 + "; toZ=" + paramInt2);
/*      */     }
/*      */     
/* 4158 */     if (paramInt1 == -1) {
/* 4159 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4164 */     if ((getLayout() != null) && (!isValid())) {
/* 4165 */       return;
/*      */     }
/* 4167 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 4168 */       Component localComponent = getComponent(i);
/* 4169 */       if (!localComponent.isLightweight()) {
/* 4170 */         localComponent.applyCurrentShape();
/*      */       }
/* 4172 */       if (((localComponent instanceof Container)) && 
/* 4173 */         (((Container)localComponent).hasHeavyweightDescendants())) {
/* 4174 */         ((Container)localComponent).recursiveApplyCurrentShape();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void recursiveShowHeavyweightChildren() {
/* 4180 */     if ((!hasHeavyweightDescendants()) || (!isVisible())) {
/* 4181 */       return;
/*      */     }
/* 4183 */     for (int i = 0; i < getComponentCount(); i++) {
/* 4184 */       Component localComponent = getComponent(i);
/* 4185 */       if (localComponent.isLightweight()) {
/* 4186 */         if ((localComponent instanceof Container)) {
/* 4187 */           ((Container)localComponent).recursiveShowHeavyweightChildren();
/*      */         }
/*      */       }
/* 4190 */       else if (localComponent.isVisible()) {
/* 4191 */         ComponentPeer localComponentPeer = localComponent.getPeer();
/* 4192 */         if (localComponentPeer != null) {
/* 4193 */           localComponentPeer.setVisible(true);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void recursiveHideHeavyweightChildren()
/*      */   {
/* 4201 */     if (!hasHeavyweightDescendants()) {
/* 4202 */       return;
/*      */     }
/* 4204 */     for (int i = 0; i < getComponentCount(); i++) {
/* 4205 */       Component localComponent = getComponent(i);
/* 4206 */       if (localComponent.isLightweight()) {
/* 4207 */         if ((localComponent instanceof Container)) {
/* 4208 */           ((Container)localComponent).recursiveHideHeavyweightChildren();
/*      */         }
/*      */       }
/* 4211 */       else if (localComponent.isVisible()) {
/* 4212 */         ComponentPeer localComponentPeer = localComponent.getPeer();
/* 4213 */         if (localComponentPeer != null) {
/* 4214 */           localComponentPeer.setVisible(false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void recursiveRelocateHeavyweightChildren(Point paramPoint)
/*      */   {
/* 4222 */     for (int i = 0; i < getComponentCount(); i++) {
/* 4223 */       Component localComponent = getComponent(i);
/* 4224 */       Object localObject; if (localComponent.isLightweight()) {
/* 4225 */         if (((localComponent instanceof Container)) && 
/* 4226 */           (((Container)localComponent).hasHeavyweightDescendants()))
/*      */         {
/* 4228 */           localObject = new Point(paramPoint);
/* 4229 */           ((Point)localObject).translate(localComponent.getX(), localComponent.getY());
/* 4230 */           ((Container)localComponent).recursiveRelocateHeavyweightChildren((Point)localObject);
/*      */         }
/*      */       } else {
/* 4233 */         localObject = localComponent.getPeer();
/* 4234 */         if (localObject != null) {
/* 4235 */           ((ComponentPeer)localObject).setBounds(paramPoint.x + localComponent.getX(), paramPoint.y + localComponent.getY(), localComponent
/* 4236 */             .getWidth(), localComponent.getHeight(), 1);
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
/*      */   final boolean isRecursivelyVisibleUpToHeavyweightContainer()
/*      */   {
/* 4253 */     if (!isLightweight()) {
/* 4254 */       return true;
/*      */     }
/*      */     
/* 4257 */     for (Container localContainer = this; 
/* 4258 */         (localContainer != null) && (localContainer.isLightweight()); 
/* 4259 */         localContainer = localContainer.getContainer())
/*      */     {
/* 4261 */       if (!localContainer.isVisible()) {
/* 4262 */         return false;
/*      */       }
/*      */     }
/* 4265 */     return true;
/*      */   }
/*      */   
/*      */   void mixOnShowing()
/*      */   {
/* 4270 */     synchronized (getTreeLock()) {
/* 4271 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4272 */         mixingLog.fine("this = " + this);
/*      */       }
/*      */       
/* 4275 */       boolean bool = isLightweight();
/*      */       
/* 4277 */       if ((bool) && (isRecursivelyVisibleUpToHeavyweightContainer())) {
/* 4278 */         recursiveShowHeavyweightChildren();
/*      */       }
/*      */       
/* 4281 */       if (!isMixingNeeded()) {
/* 4282 */         return;
/*      */       }
/*      */       
/* 4285 */       if ((!bool) || ((bool) && (hasHeavyweightDescendants()))) {
/* 4286 */         recursiveApplyCurrentShape();
/*      */       }
/*      */       
/* 4289 */       super.mixOnShowing();
/*      */     }
/*      */   }
/*      */   
/*      */   void mixOnHiding(boolean paramBoolean)
/*      */   {
/* 4295 */     synchronized (getTreeLock()) {
/* 4296 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4297 */         mixingLog.fine("this = " + this + "; isLightweight=" + paramBoolean);
/*      */       }
/*      */       
/* 4300 */       if (paramBoolean) {
/* 4301 */         recursiveHideHeavyweightChildren();
/*      */       }
/* 4303 */       super.mixOnHiding(paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */   void mixOnReshaping()
/*      */   {
/* 4309 */     synchronized (getTreeLock()) {
/* 4310 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4311 */         mixingLog.fine("this = " + this);
/*      */       }
/*      */       
/* 4314 */       boolean bool = isMixingNeeded();
/*      */       
/* 4316 */       if ((isLightweight()) && (hasHeavyweightDescendants())) {
/* 4317 */         Point localPoint = new Point(getX(), getY());
/* 4318 */         for (Container localContainer = getContainer(); 
/* 4319 */             (localContainer != null) && (localContainer.isLightweight()); 
/* 4320 */             localContainer = localContainer.getContainer())
/*      */         {
/* 4322 */           localPoint.translate(localContainer.getX(), localContainer.getY());
/*      */         }
/*      */         
/* 4325 */         recursiveRelocateHeavyweightChildren(localPoint);
/*      */         
/* 4327 */         if (!bool) {
/* 4328 */           return;
/*      */         }
/*      */         
/* 4331 */         recursiveApplyCurrentShape();
/*      */       }
/*      */       
/* 4334 */       if (!bool) {
/* 4335 */         return;
/*      */       }
/*      */       
/* 4338 */       super.mixOnReshaping();
/*      */     }
/*      */   }
/*      */   
/*      */   void mixOnZOrderChanging(int paramInt1, int paramInt2)
/*      */   {
/* 4344 */     synchronized (getTreeLock()) {
/* 4345 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4346 */         mixingLog.fine("this = " + this + "; oldZ=" + paramInt1 + "; newZ=" + paramInt2);
/*      */       }
/*      */       
/*      */ 
/* 4350 */       if (!isMixingNeeded()) {
/* 4351 */         return;
/*      */       }
/*      */       
/* 4354 */       int i = paramInt2 < paramInt1 ? 1 : 0;
/*      */       
/* 4356 */       if ((i != 0) && (isLightweight()) && (hasHeavyweightDescendants())) {
/* 4357 */         recursiveApplyCurrentShape();
/*      */       }
/* 4359 */       super.mixOnZOrderChanging(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */   void mixOnValidating()
/*      */   {
/* 4365 */     synchronized (getTreeLock()) {
/* 4366 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4367 */         mixingLog.fine("this = " + this);
/*      */       }
/*      */       
/* 4370 */       if (!isMixingNeeded()) {
/* 4371 */         return;
/*      */       }
/*      */       
/* 4374 */       if (hasHeavyweightDescendants()) {
/* 4375 */         recursiveApplyCurrentShape();
/*      */       }
/*      */       
/* 4378 */       if ((isLightweight()) && (isNonOpaqueForMixing())) {
/* 4379 */         subtractAndApplyShapeBelowMe();
/*      */       }
/*      */       
/* 4382 */       super.mixOnValidating();
/*      */     }
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   /* Error */
/*      */   Component[] getComponentsSync()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 30	java/awt/Container:getTreeLock	()Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: invokevirtual 31	java/awt/Container:getComponents	()[Ljava/awt/Component;
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: areturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #363	-> byte code offset #0
/*      */     //   Java source line #364	-> byte code offset #7
/*      */     //   Java source line #365	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	Container
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */