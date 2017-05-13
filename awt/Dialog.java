/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.DialogPeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.util.IdentityArrayList;
/*      */ import sun.awt.util.IdentityLinkedList;
/*      */ import sun.security.util.SecurityConstants.AWT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Dialog
/*      */   extends Window
/*      */ {
/*      */   static
/*      */   {
/*      */     
/*  102 */     if (!GraphicsEnvironment.isHeadless()) {
/*  103 */       initIDs();
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
/*  115 */   boolean resizable = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  130 */   boolean undecorated = false;
/*      */   
/*  132 */   private transient boolean initialized = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static enum ModalityType
/*      */   {
/*  151 */     MODELESS, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  160 */     DOCUMENT_MODAL, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  168 */     APPLICATION_MODAL, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  184 */     TOOLKIT_MODAL;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private ModalityType() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  197 */   public static final ModalityType DEFAULT_MODALITY_TYPE = ModalityType.APPLICATION_MODAL;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean modal;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   ModalityType modalityType;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static enum ModalExclusionType
/*      */   {
/*  247 */     NO_EXCLUDE, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  253 */     APPLICATION_EXCLUDE, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  265 */     TOOLKIT_EXCLUDE;
/*      */     
/*      */     private ModalExclusionType() {} }
/*      */   
/*  269 */   static transient IdentityArrayList<Dialog> modalDialogs = new IdentityArrayList();
/*      */   
/*  271 */   transient IdentityArrayList<Window> blockedWindows = new IdentityArrayList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   String title;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient ModalEventFilter modalFilter;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient SecondaryLoop secondaryLoop;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  295 */   volatile transient boolean isInHide = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  306 */   volatile transient boolean isInDispose = false;
/*      */   
/*      */   private static final String base = "dialog";
/*  309 */   private static int nameCounter = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 5920926903803293709L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Dialog(Frame paramFrame)
/*      */   {
/*  332 */     this(paramFrame, "", false);
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
/*      */   public Dialog(Frame paramFrame, boolean paramBoolean)
/*      */   {
/*  358 */     this(paramFrame, "", paramBoolean);
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
/*      */   public Dialog(Frame paramFrame, String paramString)
/*      */   {
/*  379 */     this(paramFrame, paramString, false);
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
/*      */   public Dialog(Frame paramFrame, String paramString, boolean paramBoolean)
/*      */   {
/*  409 */     this(paramFrame, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
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
/*      */   public Dialog(Frame paramFrame, String paramString, boolean paramBoolean, GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  443 */     this(paramFrame, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, paramGraphicsConfiguration);
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
/*      */   public Dialog(Dialog paramDialog)
/*      */   {
/*  460 */     this(paramDialog, "", false);
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
/*      */   public Dialog(Dialog paramDialog, String paramString)
/*      */   {
/*  480 */     this(paramDialog, paramString, false);
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
/*      */   public Dialog(Dialog paramDialog, String paramString, boolean paramBoolean)
/*      */   {
/*  510 */     this(paramDialog, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
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
/*      */   public Dialog(Dialog paramDialog, String paramString, boolean paramBoolean, GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  547 */     this(paramDialog, paramString, paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, paramGraphicsConfiguration);
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
/*      */   public Dialog(Window paramWindow)
/*      */   {
/*  571 */     this(paramWindow, "", ModalityType.MODELESS);
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
/*      */   public Dialog(Window paramWindow, String paramString)
/*      */   {
/*  597 */     this(paramWindow, paramString, ModalityType.MODELESS);
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
/*      */   public Dialog(Window paramWindow, ModalityType paramModalityType)
/*      */   {
/*  630 */     this(paramWindow, "", paramModalityType);
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
/*      */   public Dialog(Window paramWindow, String paramString, ModalityType paramModalityType)
/*      */   {
/*  665 */     super(paramWindow);
/*      */     
/*  667 */     if ((paramWindow != null) && (!(paramWindow instanceof Frame)) && (!(paramWindow instanceof Dialog)))
/*      */     {
/*      */ 
/*      */ 
/*  671 */       throw new IllegalArgumentException("Wrong parent window");
/*      */     }
/*      */     
/*  674 */     this.title = paramString;
/*  675 */     setModalityType(paramModalityType);
/*  676 */     SunToolkit.checkAndSetPolicy(this);
/*  677 */     this.initialized = true;
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
/*      */   public Dialog(Window paramWindow, String paramString, ModalityType paramModalityType, GraphicsConfiguration paramGraphicsConfiguration)
/*      */   {
/*  717 */     super(paramWindow, paramGraphicsConfiguration);
/*      */     
/*  719 */     if ((paramWindow != null) && (!(paramWindow instanceof Frame)) && (!(paramWindow instanceof Dialog)))
/*      */     {
/*      */ 
/*      */ 
/*  723 */       throw new IllegalArgumentException("wrong owner window");
/*      */     }
/*      */     
/*  726 */     this.title = paramString;
/*  727 */     setModalityType(paramModalityType);
/*  728 */     SunToolkit.checkAndSetPolicy(this);
/*  729 */     this.initialized = true;
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
/*  752 */     synchronized (getTreeLock()) {
/*  753 */       if ((this.parent != null) && (this.parent.getPeer() == null)) {
/*  754 */         this.parent.addNotify();
/*      */       }
/*      */       
/*  757 */       if (this.peer == null) {
/*  758 */         this.peer = getToolkit().createDialog(this);
/*      */       }
/*  760 */       super.addNotify();
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
/*      */   public boolean isModal()
/*      */   {
/*  780 */     return isModal_NoClientCode();
/*      */   }
/*      */   
/*  783 */   final boolean isModal_NoClientCode() { return this.modalityType != ModalityType.MODELESS; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setModal(boolean paramBoolean)
/*      */   {
/*  810 */     this.modal = paramBoolean;
/*  811 */     setModalityType(paramBoolean ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
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
/*      */   public ModalityType getModalityType()
/*      */   {
/*  824 */     return this.modalityType;
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
/*      */   public void setModalityType(ModalityType paramModalityType)
/*      */   {
/*  850 */     if (paramModalityType == null) {
/*  851 */       paramModalityType = ModalityType.MODELESS;
/*      */     }
/*  853 */     if (!Toolkit.getDefaultToolkit().isModalityTypeSupported(paramModalityType)) {
/*  854 */       paramModalityType = ModalityType.MODELESS;
/*      */     }
/*  856 */     if (this.modalityType == paramModalityType) {
/*  857 */       return;
/*      */     }
/*      */     
/*  860 */     checkModalityPermission(paramModalityType);
/*      */     
/*  862 */     this.modalityType = paramModalityType;
/*  863 */     this.modal = (this.modalityType != ModalityType.MODELESS);
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
/*  874 */     return this.title;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTitle(String paramString)
/*      */   {
/*  884 */     String str = this.title;
/*      */     
/*  886 */     synchronized (this) {
/*  887 */       this.title = paramString;
/*  888 */       DialogPeer localDialogPeer = (DialogPeer)this.peer;
/*  889 */       if (localDialogPeer != null) {
/*  890 */         localDialogPeer.setTitle(paramString);
/*      */       }
/*      */     }
/*  893 */     firePropertyChange("title", str, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionalShow(Component paramComponent, AtomicLong paramAtomicLong)
/*      */   {
/*  902 */     closeSplashScreen();
/*      */     boolean bool;
/*  904 */     synchronized (getTreeLock()) {
/*  905 */       if (this.peer == null) {
/*  906 */         addNotify();
/*      */       }
/*  908 */       validateUnconditionally();
/*  909 */       if (this.visible) {
/*  910 */         toFront();
/*  911 */         bool = false;
/*      */       } else {
/*  913 */         this.visible = (bool = 1);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  918 */         if (!isModal()) {
/*  919 */           checkShouldBeBlocked(this);
/*      */         } else {
/*  921 */           modalDialogs.add(this);
/*  922 */           modalShow();
/*      */         }
/*      */         
/*  925 */         if ((paramComponent != null) && (paramAtomicLong != null) && (isFocusable()) && 
/*  926 */           (isEnabled()) && (!isModalBlocked()))
/*      */         {
/*      */ 
/*  929 */           paramAtomicLong.set(Toolkit.getEventQueue().getMostRecentKeyEventTime());
/*  930 */           KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  931 */             .enqueueKeyEvents(paramAtomicLong.get(), paramComponent);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  936 */         mixOnShowing();
/*      */         
/*  938 */         this.peer.setVisible(true);
/*  939 */         if (isModalBlocked()) {
/*  940 */           this.modalBlocker.toFront();
/*      */         }
/*      */         
/*  943 */         setLocationByPlatform(false);
/*  944 */         for (int i = 0; i < this.ownedWindowList.size(); i++) {
/*  945 */           Window localWindow = (Window)((WeakReference)this.ownedWindowList.elementAt(i)).get();
/*  946 */           if ((localWindow != null) && (localWindow.showWithParent)) {
/*  947 */             localWindow.show();
/*  948 */             localWindow.showWithParent = false;
/*      */           }
/*      */         }
/*  951 */         Window.updateChildFocusableWindowState(this);
/*      */         
/*  953 */         createHierarchyEvents(1400, this, this.parent, 4L, 
/*      */         
/*      */ 
/*  956 */           Toolkit.enabledOnToolkit(32768L));
/*  957 */         if ((this.componentListener != null) || ((this.eventMask & 1L) != 0L) || 
/*      */         
/*  959 */           (Toolkit.enabledOnToolkit(1L))) {
/*  960 */           ComponentEvent localComponentEvent = new ComponentEvent(this, 102);
/*      */           
/*  962 */           Toolkit.getEventQueue().postEvent(localComponentEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  967 */     if ((bool) && ((this.state & 0x1) == 0)) {
/*  968 */       postWindowEvent(200);
/*  969 */       this.state |= 0x1;
/*      */     }
/*      */     
/*  972 */     return bool;
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
/*      */   public void setVisible(boolean paramBoolean)
/*      */   {
/* 1005 */     super.setVisible(paramBoolean);
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
/*      */   @Deprecated
/*      */   public void show()
/*      */   {
/* 1030 */     if (!this.initialized) {
/* 1031 */       throw new IllegalStateException("The dialog component has not been initialized properly");
/*      */     }
/*      */     
/*      */ 
/* 1035 */     this.beforeFirstShow = false;
/* 1036 */     if (!isModal()) {
/* 1037 */       conditionalShow(null, null);
/*      */     } else {
/* 1039 */       AppContext localAppContext1 = AppContext.getAppContext();
/*      */       
/* 1041 */       AtomicLong localAtomicLong = new AtomicLong();
/* 1042 */       Component localComponent = null;
/*      */       try {
/* 1044 */         localComponent = getMostRecentFocusOwner();
/* 1045 */         if (conditionalShow(localComponent, localAtomicLong)) {
/* 1046 */           this.modalFilter = ModalEventFilter.createFilterForDialog(this);
/* 1047 */           Conditional local1 = new Conditional()
/*      */           {
/*      */ 
/* 1050 */             public boolean evaluate() { return Dialog.this.windowClosingException == null; }
/*      */           };
/*      */           Object localObject1;
/*      */           AppContext localAppContext2;
/*      */           EventQueue localEventQueue;
/*      */           Object localObject2;
/* 1056 */           if (this.modalityType == ModalityType.TOOLKIT_MODAL) {
/* 1057 */             localObject1 = AppContext.getAppContexts().iterator();
/* 1058 */             while (((Iterator)localObject1).hasNext()) {
/* 1059 */               localAppContext2 = (AppContext)((Iterator)localObject1).next();
/* 1060 */               if (localAppContext2 != localAppContext1)
/*      */               {
/*      */ 
/* 1063 */                 localEventQueue = (EventQueue)localAppContext2.get(AppContext.EVENT_QUEUE_KEY);
/*      */                 
/*      */ 
/* 1066 */                 localObject2 = new Runnable() {
/*      */                   public void run() {}
/* 1068 */                 };
/* 1069 */                 localEventQueue.postEvent(new InvocationEvent(this, (Runnable)localObject2));
/* 1070 */                 EventDispatchThread localEventDispatchThread = localEventQueue.getDispatchThread();
/* 1071 */                 localEventDispatchThread.addEventFilter(this.modalFilter);
/*      */               }
/*      */             }
/*      */           }
/* 1075 */           modalityPushed();
/*      */           try {
/* 1077 */             localObject1 = (EventQueue)AccessController.doPrivileged(new PrivilegedAction()
/*      */             {
/*      */               public EventQueue run() {
/* 1080 */                 return Toolkit.getDefaultToolkit().getSystemEventQueue();
/*      */               }
/* 1082 */             });
/* 1083 */             this.secondaryLoop = ((EventQueue)localObject1).createSecondaryLoop(local1, this.modalFilter, 0L);
/* 1084 */             if (!this.secondaryLoop.enter()) {
/* 1085 */               this.secondaryLoop = null;
/*      */             }
/*      */           } finally {
/* 1088 */             modalityPopped();
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1093 */           if (this.modalityType == ModalityType.TOOLKIT_MODAL) {
/* 1094 */             localObject1 = AppContext.getAppContexts().iterator();
/* 1095 */             while (((Iterator)localObject1).hasNext()) {
/* 1096 */               localAppContext2 = (AppContext)((Iterator)localObject1).next();
/* 1097 */               if (localAppContext2 != localAppContext1)
/*      */               {
/*      */ 
/* 1100 */                 localEventQueue = (EventQueue)localAppContext2.get(AppContext.EVENT_QUEUE_KEY);
/* 1101 */                 localObject2 = localEventQueue.getDispatchThread();
/* 1102 */                 ((EventDispatchThread)localObject2).removeEventFilter(this.modalFilter);
/*      */               }
/*      */             }
/*      */           }
/* 1106 */           if (this.windowClosingException != null) {
/* 1107 */             this.windowClosingException.fillInStackTrace();
/* 1108 */             throw this.windowClosingException;
/*      */           }
/*      */         }
/*      */       } finally {
/* 1112 */         if (localComponent != null)
/*      */         {
/*      */ 
/* 1115 */           KeyboardFocusManager.getCurrentKeyboardFocusManager().dequeueKeyEvents(localAtomicLong.get(), localComponent);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   final void modalityPushed() {
/* 1122 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 1123 */     if ((localToolkit instanceof SunToolkit)) {
/* 1124 */       SunToolkit localSunToolkit = (SunToolkit)localToolkit;
/* 1125 */       localSunToolkit.notifyModalityPushed(this);
/*      */     }
/*      */   }
/*      */   
/*      */   final void modalityPopped() {
/* 1130 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/* 1131 */     if ((localToolkit instanceof SunToolkit)) {
/* 1132 */       SunToolkit localSunToolkit = (SunToolkit)localToolkit;
/* 1133 */       localSunToolkit.notifyModalityPopped(this);
/*      */     }
/*      */   }
/*      */   
/*      */   void interruptBlocking() {
/* 1138 */     if (isModal()) {
/* 1139 */       disposeImpl();
/* 1140 */     } else if (this.windowClosingException != null) {
/* 1141 */       this.windowClosingException.fillInStackTrace();
/* 1142 */       this.windowClosingException.printStackTrace();
/* 1143 */       this.windowClosingException = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private void hideAndDisposePreHandler() {
/* 1148 */     this.isInHide = true;
/* 1149 */     synchronized (getTreeLock()) {
/* 1150 */       if (this.secondaryLoop != null) {
/* 1151 */         modalHide();
/*      */         
/*      */ 
/* 1154 */         if (this.modalFilter != null) {
/* 1155 */           this.modalFilter.disable();
/*      */         }
/* 1157 */         modalDialogs.remove(this);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1162 */   private void hideAndDisposeHandler() { if (this.secondaryLoop != null) {
/* 1163 */       this.secondaryLoop.exit();
/* 1164 */       this.secondaryLoop = null;
/*      */     }
/* 1166 */     this.isInHide = false;
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
/* 1180 */     hideAndDisposePreHandler();
/* 1181 */     super.hide();
/*      */     
/*      */ 
/*      */ 
/* 1185 */     if (!this.isInDispose) {
/* 1186 */       hideAndDisposeHandler();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void doDispose()
/*      */   {
/* 1197 */     this.isInDispose = true;
/* 1198 */     super.doDispose();
/* 1199 */     hideAndDisposeHandler();
/* 1200 */     this.isInDispose = false;
/*      */   }
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
/* 1212 */     super.toBack();
/* 1213 */     if (this.visible) {
/* 1214 */       synchronized (getTreeLock()) {
/* 1215 */         for (Window localWindow : this.blockedWindows) {
/* 1216 */           localWindow.toBack_NoClientCode();
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
/* 1230 */     return this.resizable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setResizable(boolean paramBoolean)
/*      */   {
/* 1240 */     int i = 0;
/*      */     
/* 1242 */     synchronized (this) {
/* 1243 */       this.resizable = paramBoolean;
/* 1244 */       DialogPeer localDialogPeer = (DialogPeer)this.peer;
/* 1245 */       if (localDialogPeer != null) {
/* 1246 */         localDialogPeer.setResizable(paramBoolean);
/* 1247 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1255 */     if (i != 0) {
/* 1256 */       invalidateIfValid();
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
/*      */   public void setUndecorated(boolean paramBoolean)
/*      */   {
/* 1292 */     synchronized (getTreeLock()) {
/* 1293 */       if (isDisplayable()) {
/* 1294 */         throw new IllegalComponentStateException("The dialog is displayable.");
/*      */       }
/* 1296 */       if (!paramBoolean) {
/* 1297 */         if (getOpacity() < 1.0F) {
/* 1298 */           throw new IllegalComponentStateException("The dialog is not opaque");
/*      */         }
/* 1300 */         if (getShape() != null) {
/* 1301 */           throw new IllegalComponentStateException("The dialog does not have a default shape");
/*      */         }
/* 1303 */         Color localColor = getBackground();
/* 1304 */         if ((localColor != null) && (localColor.getAlpha() < 255)) {
/* 1305 */           throw new IllegalComponentStateException("The dialog background color is not opaque");
/*      */         }
/*      */       }
/* 1308 */       this.undecorated = paramBoolean;
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
/* 1321 */     return this.undecorated;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setOpacity(float paramFloat)
/*      */   {
/* 1329 */     synchronized (getTreeLock()) {
/* 1330 */       if ((paramFloat < 1.0F) && (!isUndecorated())) {
/* 1331 */         throw new IllegalComponentStateException("The dialog is decorated");
/*      */       }
/* 1333 */       super.setOpacity(paramFloat);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setShape(Shape paramShape)
/*      */   {
/* 1342 */     synchronized (getTreeLock()) {
/* 1343 */       if ((paramShape != null) && (!isUndecorated())) {
/* 1344 */         throw new IllegalComponentStateException("The dialog is decorated");
/*      */       }
/* 1346 */       super.setShape(paramShape);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBackground(Color paramColor)
/*      */   {
/* 1355 */     synchronized (getTreeLock()) {
/* 1356 */       if ((paramColor != null) && (paramColor.getAlpha() < 255) && (!isUndecorated())) {
/* 1357 */         throw new IllegalComponentStateException("The dialog is decorated");
/*      */       }
/* 1359 */       super.setBackground(paramColor);
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
/*      */   protected String paramString()
/*      */   {
/* 1373 */     String str = super.paramString() + "," + this.modalityType;
/* 1374 */     if (this.title != null) {
/* 1375 */       str = str + ",title=" + this.title;
/*      */     }
/* 1377 */     return str;
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
/*      */   void modalShow()
/*      */   {
/* 1401 */     IdentityArrayList localIdentityArrayList1 = new IdentityArrayList();
/* 1402 */     for (Iterator localIterator = modalDialogs.iterator(); localIterator.hasNext();) { localDialog1 = (Dialog)localIterator.next();
/* 1403 */       if (localDialog1.shouldBlock(this)) {
/* 1404 */         localObject1 = localDialog1;
/* 1405 */         while ((localObject1 != null) && (localObject1 != this)) {
/* 1406 */           localObject1 = ((Window)localObject1).getOwner_NoClientCode();
/*      */         }
/* 1408 */         if ((localObject1 == this) || (!shouldBlock(localDialog1)) || (this.modalityType.compareTo(localDialog1.getModalityType()) < 0)) {
/* 1409 */           localIdentityArrayList1.add(localDialog1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     Dialog localDialog1;
/* 1415 */     for (int i = 0; i < localIdentityArrayList1.size(); i++) {
/* 1416 */       localDialog1 = (Dialog)localIdentityArrayList1.get(i);
/* 1417 */       if (localDialog1.isModalBlocked()) {
/* 1418 */         localObject1 = localDialog1.getModalBlocker();
/* 1419 */         if (!localIdentityArrayList1.contains(localObject1)) {
/* 1420 */           localIdentityArrayList1.add(i + 1, localObject1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1425 */     if (localIdentityArrayList1.size() > 0) {
/* 1426 */       ((Dialog)localIdentityArrayList1.get(0)).blockWindow(this);
/*      */     }
/*      */     
/*      */ 
/* 1430 */     IdentityArrayList localIdentityArrayList2 = new IdentityArrayList(localIdentityArrayList1);
/* 1431 */     int j = 0;
/* 1432 */     while (j < localIdentityArrayList2.size()) {
/* 1433 */       localObject1 = (Window)localIdentityArrayList2.get(j);
/* 1434 */       localObject2 = ((Window)localObject1).getOwnedWindows_NoClientCode();
/* 1435 */       for (Object localObject4 : localObject2) {
/* 1436 */         localIdentityArrayList2.add(localObject4);
/*      */       }
/* 1438 */       j++;
/*      */     }
/*      */     
/* 1441 */     Object localObject1 = new IdentityLinkedList();
/*      */     
/* 1443 */     Object localObject2 = Window.getAllUnblockedWindows();
/* 1444 */     for (??? = ((IdentityArrayList)localObject2).iterator(); ((Iterator)???).hasNext();) { Window localWindow = (Window)((Iterator)???).next();
/* 1445 */       if ((shouldBlock(localWindow)) && (!localIdentityArrayList2.contains(localWindow)))
/* 1446 */         if (((localWindow instanceof Dialog)) && (((Dialog)localWindow).isModal_NoClientCode())) {
/* 1447 */           Dialog localDialog2 = (Dialog)localWindow;
/* 1448 */           if ((localDialog2.shouldBlock(this)) && (modalDialogs.indexOf(localDialog2) > modalDialogs.indexOf(this))) {}
/*      */         }
/*      */         else
/*      */         {
/* 1452 */           ((List)localObject1).add(localWindow);
/*      */         }
/*      */     }
/* 1455 */     blockWindows((List)localObject1);
/*      */     
/* 1457 */     if (!isModalBlocked()) {
/* 1458 */       updateChildrenBlocking();
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
/*      */   void modalHide()
/*      */   {
/* 1471 */     IdentityArrayList localIdentityArrayList = new IdentityArrayList();
/* 1472 */     int i = this.blockedWindows.size();
/* 1473 */     Window localWindow; for (int j = 0; j < i; j++) {
/* 1474 */       localWindow = (Window)this.blockedWindows.get(0);
/* 1475 */       localIdentityArrayList.add(localWindow);
/* 1476 */       unblockWindow(localWindow);
/*      */     }
/*      */     
/*      */ 
/* 1480 */     for (j = 0; j < i; j++) {
/* 1481 */       localWindow = (Window)localIdentityArrayList.get(j);
/* 1482 */       if (((localWindow instanceof Dialog)) && (((Dialog)localWindow).isModal_NoClientCode())) {
/* 1483 */         Dialog localDialog = (Dialog)localWindow;
/* 1484 */         localDialog.modalShow();
/*      */       } else {
/* 1486 */         checkShouldBeBlocked(localWindow);
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
/*      */   boolean shouldBlock(Window paramWindow)
/*      */   {
/* 1501 */     if ((!isVisible_NoClientCode()) || 
/* 1502 */       ((!paramWindow.isVisible_NoClientCode()) && (!paramWindow.isInShow)) || (this.isInHide) || (paramWindow == this) || 
/*      */       
/*      */ 
/* 1505 */       (!isModal_NoClientCode()))
/*      */     {
/* 1507 */       return false;
/*      */     }
/* 1509 */     if (((paramWindow instanceof Dialog)) && (((Dialog)paramWindow).isInHide)) {
/* 1510 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1515 */     Dialog localDialog = this;
/* 1516 */     Object localObject; while (localDialog != null) {
/* 1517 */       localObject = paramWindow;
/* 1518 */       while ((localObject != null) && (localObject != localDialog)) {
/* 1519 */         localObject = ((Component)localObject).getParent_NoClientCode();
/*      */       }
/* 1521 */       if (localObject == localDialog) {
/* 1522 */         return false;
/*      */       }
/* 1524 */       localDialog = localDialog.getModalBlocker();
/*      */     }
/* 1526 */     switch (this.modalityType) {
/*      */     case MODELESS: 
/* 1528 */       return false;
/*      */     case DOCUMENT_MODAL: 
/* 1530 */       if (paramWindow.isModalExcluded(ModalExclusionType.APPLICATION_EXCLUDE))
/*      */       {
/*      */ 
/* 1533 */         localObject = this;
/* 1534 */         while ((localObject != null) && (localObject != paramWindow)) {
/* 1535 */           localObject = ((Component)localObject).getParent_NoClientCode();
/*      */         }
/* 1537 */         return localObject == paramWindow;
/*      */       }
/* 1539 */       return getDocumentRoot() == paramWindow.getDocumentRoot();
/*      */     
/*      */     case APPLICATION_MODAL: 
/* 1542 */       return (!paramWindow.isModalExcluded(ModalExclusionType.APPLICATION_EXCLUDE)) && (this.appContext == paramWindow.appContext);
/*      */     
/*      */     case TOOLKIT_MODAL: 
/* 1545 */       return !paramWindow.isModalExcluded(ModalExclusionType.TOOLKIT_EXCLUDE);
/*      */     }
/*      */     
/* 1548 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void blockWindow(Window paramWindow)
/*      */   {
/* 1558 */     if (!paramWindow.isModalBlocked()) {
/* 1559 */       paramWindow.setModalBlocked(this, true, true);
/* 1560 */       this.blockedWindows.add(paramWindow);
/*      */     }
/*      */   }
/*      */   
/*      */   void blockWindows(List<Window> paramList) {
/* 1565 */     DialogPeer localDialogPeer = (DialogPeer)this.peer;
/* 1566 */     if (localDialogPeer == null) {
/* 1567 */       return;
/*      */     }
/* 1569 */     Iterator localIterator = paramList.iterator();
/* 1570 */     while (localIterator.hasNext()) {
/* 1571 */       Window localWindow = (Window)localIterator.next();
/* 1572 */       if (!localWindow.isModalBlocked()) {
/* 1573 */         localWindow.setModalBlocked(this, true, false);
/*      */       } else {
/* 1575 */         localIterator.remove();
/*      */       }
/*      */     }
/* 1578 */     localDialogPeer.blockWindows(paramList);
/* 1579 */     this.blockedWindows.addAll(paramList);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void unblockWindow(Window paramWindow)
/*      */   {
/* 1588 */     if ((paramWindow.isModalBlocked()) && (this.blockedWindows.contains(paramWindow))) {
/* 1589 */       this.blockedWindows.remove(paramWindow);
/* 1590 */       paramWindow.setModalBlocked(this, false, true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void checkShouldBeBlocked(Window paramWindow)
/*      */   {
/* 1599 */     synchronized (paramWindow.getTreeLock()) {
/* 1600 */       for (int i = 0; i < modalDialogs.size(); i++) {
/* 1601 */         Dialog localDialog = (Dialog)modalDialogs.get(i);
/* 1602 */         if (localDialog.shouldBlock(paramWindow)) {
/* 1603 */           localDialog.blockWindow(paramWindow);
/* 1604 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkModalityPermission(ModalityType paramModalityType) {
/* 1611 */     if (paramModalityType == ModalityType.TOOLKIT_MODAL) {
/* 1612 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 1613 */       if (localSecurityManager != null) {
/* 1614 */         localSecurityManager.checkPermission(SecurityConstants.AWT.TOOLKIT_MODALITY_PERMISSION);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException, HeadlessException
/*      */   {
/* 1624 */     GraphicsEnvironment.checkHeadless();
/*      */     
/*      */ 
/* 1627 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*      */     
/* 1629 */     ModalityType localModalityType = (ModalityType)localGetField.get("modalityType", null);
/*      */     try
/*      */     {
/* 1632 */       checkModalityPermission(localModalityType);
/*      */     } catch (AccessControlException localAccessControlException) {
/* 1634 */       localModalityType = DEFAULT_MODALITY_TYPE;
/*      */     }
/*      */     
/*      */ 
/* 1638 */     if (localModalityType == null) {
/* 1639 */       this.modal = localGetField.get("modal", false);
/* 1640 */       setModal(this.modal);
/*      */     } else {
/* 1642 */       this.modalityType = localModalityType;
/*      */     }
/*      */     
/* 1645 */     this.resizable = localGetField.get("resizable", true);
/* 1646 */     this.undecorated = localGetField.get("undecorated", false);
/* 1647 */     this.title = ((String)localGetField.get("title", ""));
/*      */     
/* 1649 */     this.blockedWindows = new IdentityArrayList();
/*      */     
/* 1651 */     SunToolkit.checkAndSetPolicy(this);
/*      */     
/* 1653 */     this.initialized = true;
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
/*      */   public AccessibleContext getAccessibleContext()
/*      */   {
/* 1673 */     if (this.accessibleContext == null) {
/* 1674 */       this.accessibleContext = new AccessibleAWTDialog();
/*      */     }
/* 1676 */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   String constructComponentName()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 18
/*      */     //   2: dup
/*      */     //   3: astore_1
/*      */     //   4: monitorenter
/*      */     //   5: new 27	java/lang/StringBuilder
/*      */     //   8: dup
/*      */     //   9: invokespecial 28	java/lang/StringBuilder:<init>	()V
/*      */     //   12: ldc 29
/*      */     //   14: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   17: getstatic 31	java/awt/Dialog:nameCounter	I
/*      */     //   20: dup
/*      */     //   21: iconst_1
/*      */     //   22: iadd
/*      */     //   23: putstatic 31	java/awt/Dialog:nameCounter	I
/*      */     //   26: invokevirtual 32	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   29: invokevirtual 33	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   32: aload_1
/*      */     //   33: monitorexit
/*      */     //   34: areturn
/*      */     //   35: astore_2
/*      */     //   36: aload_1
/*      */     //   37: monitorexit
/*      */     //   38: aload_2
/*      */     //   39: athrow
/*      */     // Line number table:
/*      */     //   Java source line #737	-> byte code offset #0
/*      */     //   Java source line #738	-> byte code offset #5
/*      */     //   Java source line #739	-> byte code offset #35
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	40	0	this	Dialog
/*      */     //   3	34	1	Ljava/lang/Object;	Object
/*      */     //   35	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   5	34	35	finally
/*      */     //   35	38	35	finally
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   protected class AccessibleAWTDialog
/*      */     extends Window.AccessibleAWTWindow
/*      */   {
/*      */     private static final long serialVersionUID = 4837230331833941201L;
/*      */     
/*      */     protected AccessibleAWTDialog()
/*      */     {
/* 1685 */       super();
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
/* 1700 */       return AccessibleRole.DIALOG;
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
/* 1711 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/* 1712 */       if (Dialog.this.getFocusOwner() != null) {
/* 1713 */         localAccessibleStateSet.add(AccessibleState.ACTIVE);
/*      */       }
/* 1715 */       if (Dialog.this.isModal()) {
/* 1716 */         localAccessibleStateSet.add(AccessibleState.MODAL);
/*      */       }
/* 1718 */       if (Dialog.this.isResizable()) {
/* 1719 */         localAccessibleStateSet.add(AccessibleState.RESIZABLE);
/*      */       }
/* 1721 */       return localAccessibleStateSet;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Dialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */