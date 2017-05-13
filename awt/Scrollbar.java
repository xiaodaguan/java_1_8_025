/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.AdjustmentEvent;
/*      */ import java.awt.event.AdjustmentListener;
/*      */ import java.awt.peer.ScrollbarPeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.EventListener;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Scrollbar
/*      */   extends Component
/*      */   implements Adjustable, Accessible
/*      */ {
/*      */   public static final int HORIZONTAL = 0;
/*      */   public static final int VERTICAL = 1;
/*      */   int value;
/*      */   int maximum;
/*      */   int minimum;
/*      */   int visibleAmount;
/*      */   int orientation;
/*  247 */   int lineIncrement = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  258 */   int pageIncrement = 10;
/*      */   
/*      */ 
/*      */ 
/*      */   transient boolean isAdjusting;
/*      */   
/*      */ 
/*      */ 
/*      */   transient AdjustmentListener adjustmentListener;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String base = "scrollbar";
/*      */   
/*      */ 
/*      */ 
/*  274 */   private static int nameCounter = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = 8451667562882310543L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*  288 */     Toolkit.loadLibraries();
/*  289 */     if (!GraphicsEnvironment.isHeadless()) {
/*  290 */       initIDs();
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
/*      */   public Scrollbar()
/*      */     throws HeadlessException
/*      */   {
/*  357 */     this(1, 0, 10, 0, 100);
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
/*      */   public Scrollbar(int paramInt)
/*      */     throws HeadlessException
/*      */   {
/*  376 */     this(paramInt, 0, 10, 0, 100);
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
/*      */   public Scrollbar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */     throws HeadlessException
/*      */   {
/*  406 */     GraphicsEnvironment.checkHeadless();
/*  407 */     switch (paramInt1) {
/*      */     case 0: 
/*      */     case 1: 
/*  410 */       this.orientation = paramInt1;
/*  411 */       break;
/*      */     default: 
/*  413 */       throw new IllegalArgumentException("illegal scrollbar orientation");
/*      */     }
/*  415 */     setValues(paramInt2, paramInt3, paramInt4, paramInt5);
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
/*      */   public void addNotify()
/*      */   {
/*  434 */     synchronized (getTreeLock()) {
/*  435 */       if (this.peer == null)
/*  436 */         this.peer = getToolkit().createScrollbar(this);
/*  437 */       super.addNotify();
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
/*      */   public int getOrientation()
/*      */   {
/*  450 */     return this.orientation;
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
/*      */   public void setOrientation(int paramInt)
/*      */   {
/*  466 */     synchronized (getTreeLock()) {
/*  467 */       if (paramInt == this.orientation) {
/*  468 */         return;
/*      */       }
/*  470 */       switch (paramInt) {
/*      */       case 0: 
/*      */       case 1: 
/*  473 */         this.orientation = paramInt;
/*  474 */         break;
/*      */       default: 
/*  476 */         throw new IllegalArgumentException("illegal scrollbar orientation");
/*      */       }
/*      */       
/*  479 */       if (this.peer != null) {
/*  480 */         removeNotify();
/*  481 */         addNotify();
/*  482 */         invalidate();
/*      */       }
/*      */     }
/*  485 */     if (this.accessibleContext != null) {
/*  486 */       this.accessibleContext.firePropertyChange("AccessibleState", paramInt == 1 ? AccessibleState.HORIZONTAL : AccessibleState.VERTICAL, paramInt == 1 ? AccessibleState.VERTICAL : AccessibleState.HORIZONTAL);
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
/*      */   public int getValue()
/*      */   {
/*  503 */     return this.value;
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
/*      */   public void setValue(int paramInt)
/*      */   {
/*  533 */     setValues(paramInt, this.visibleAmount, this.minimum, this.maximum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinimum()
/*      */   {
/*  544 */     return this.minimum;
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
/*      */   public void setMinimum(int paramInt)
/*      */   {
/*  577 */     setValues(this.value, this.visibleAmount, paramInt, this.maximum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaximum()
/*      */   {
/*  588 */     return this.maximum;
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
/*      */   public void setMaximum(int paramInt)
/*      */   {
/*  619 */     if (paramInt == Integer.MIN_VALUE) {
/*  620 */       paramInt = -2147483647;
/*      */     }
/*      */     
/*  623 */     if (this.minimum >= paramInt) {
/*  624 */       this.minimum = (paramInt - 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  629 */     setValues(this.value, this.visibleAmount, this.minimum, paramInt);
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
/*      */   public int getVisibleAmount()
/*      */   {
/*  655 */     return getVisible();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int getVisible()
/*      */   {
/*  664 */     return this.visibleAmount;
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
/*      */   public void setVisibleAmount(int paramInt)
/*      */   {
/*  705 */     setValues(this.value, paramInt, this.minimum, this.maximum);
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
/*      */   public void setUnitIncrement(int paramInt)
/*      */   {
/*  728 */     setLineIncrement(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public synchronized void setLineIncrement(int paramInt)
/*      */   {
/*  737 */     int i = paramInt < 1 ? 1 : paramInt;
/*      */     
/*  739 */     if (this.lineIncrement == i) {
/*  740 */       return;
/*      */     }
/*  742 */     this.lineIncrement = i;
/*      */     
/*  744 */     ScrollbarPeer localScrollbarPeer = (ScrollbarPeer)this.peer;
/*  745 */     if (localScrollbarPeer != null) {
/*  746 */       localScrollbarPeer.setLineIncrement(this.lineIncrement);
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
/*      */   public int getUnitIncrement()
/*      */   {
/*  767 */     return getLineIncrement();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int getLineIncrement()
/*      */   {
/*  776 */     return this.lineIncrement;
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
/*      */   public void setBlockIncrement(int paramInt)
/*      */   {
/*  796 */     setPageIncrement(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public synchronized void setPageIncrement(int paramInt)
/*      */   {
/*  805 */     int i = paramInt < 1 ? 1 : paramInt;
/*      */     
/*  807 */     if (this.pageIncrement == i) {
/*  808 */       return;
/*      */     }
/*  810 */     this.pageIncrement = i;
/*      */     
/*  812 */     ScrollbarPeer localScrollbarPeer = (ScrollbarPeer)this.peer;
/*  813 */     if (localScrollbarPeer != null) {
/*  814 */       localScrollbarPeer.setPageIncrement(this.pageIncrement);
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
/*      */   public int getBlockIncrement()
/*      */   {
/*  832 */     return getPageIncrement();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int getPageIncrement()
/*      */   {
/*  841 */     return this.pageIncrement;
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
/*      */   public void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  879 */     synchronized (this) {
/*  880 */       if (paramInt3 == Integer.MAX_VALUE) {
/*  881 */         paramInt3 = 2147483646;
/*      */       }
/*  883 */       if (paramInt4 <= paramInt3) {
/*  884 */         paramInt4 = paramInt3 + 1;
/*      */       }
/*      */       
/*  887 */       long l = paramInt4 - paramInt3;
/*  888 */       if (l > 2147483647L) {
/*  889 */         l = 2147483647L;
/*  890 */         paramInt4 = paramInt3 + (int)l;
/*      */       }
/*  892 */       if (paramInt2 > (int)l) {
/*  893 */         paramInt2 = (int)l;
/*      */       }
/*  895 */       if (paramInt2 < 1) {
/*  896 */         paramInt2 = 1;
/*      */       }
/*      */       
/*  899 */       if (paramInt1 < paramInt3) {
/*  900 */         paramInt1 = paramInt3;
/*      */       }
/*  902 */       if (paramInt1 > paramInt4 - paramInt2) {
/*  903 */         paramInt1 = paramInt4 - paramInt2;
/*      */       }
/*      */       
/*  906 */       i = this.value;
/*  907 */       this.value = paramInt1;
/*  908 */       this.visibleAmount = paramInt2;
/*  909 */       this.minimum = paramInt3;
/*  910 */       this.maximum = paramInt4;
/*  911 */       ScrollbarPeer localScrollbarPeer = (ScrollbarPeer)this.peer;
/*  912 */       if (localScrollbarPeer != null) {
/*  913 */         localScrollbarPeer.setValues(paramInt1, this.visibleAmount, paramInt3, paramInt4);
/*      */       }
/*      */     }
/*      */     
/*  917 */     if ((i != paramInt1) && (this.accessibleContext != null)) {
/*  918 */       this.accessibleContext.firePropertyChange("AccessibleValue", 
/*      */       
/*  920 */         Integer.valueOf(i), 
/*  921 */         Integer.valueOf(paramInt1));
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
/*      */   public boolean getValueIsAdjusting()
/*      */   {
/*  934 */     return this.isAdjusting;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setValueIsAdjusting(boolean paramBoolean)
/*      */   {
/*      */     boolean bool;
/*      */     
/*      */ 
/*      */ 
/*  947 */     synchronized (this) {
/*  948 */       bool = this.isAdjusting;
/*  949 */       this.isAdjusting = paramBoolean;
/*      */     }
/*      */     
/*  952 */     if ((bool != paramBoolean) && (this.accessibleContext != null)) {
/*  953 */       this.accessibleContext.firePropertyChange("AccessibleState", bool ? AccessibleState.BUSY : null, paramBoolean ? AccessibleState.BUSY : null);
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
/*      */   public synchronized void addAdjustmentListener(AdjustmentListener paramAdjustmentListener)
/*      */   {
/*  978 */     if (paramAdjustmentListener == null) {
/*  979 */       return;
/*      */     }
/*  981 */     this.adjustmentListener = AWTEventMulticaster.add(this.adjustmentListener, paramAdjustmentListener);
/*  982 */     this.newEventsOnly = true;
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
/*      */   public synchronized void removeAdjustmentListener(AdjustmentListener paramAdjustmentListener)
/*      */   {
/* 1001 */     if (paramAdjustmentListener == null) {
/* 1002 */       return;
/*      */     }
/* 1004 */     this.adjustmentListener = AWTEventMulticaster.remove(this.adjustmentListener, paramAdjustmentListener);
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
/*      */   public synchronized AdjustmentListener[] getAdjustmentListeners()
/*      */   {
/* 1021 */     return (AdjustmentListener[])getListeners(AdjustmentListener.class);
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
/*      */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*      */   {
/* 1055 */     AdjustmentListener localAdjustmentListener = null;
/* 1056 */     if (paramClass == AdjustmentListener.class) {
/* 1057 */       localAdjustmentListener = this.adjustmentListener;
/*      */     } else {
/* 1059 */       return super.getListeners(paramClass);
/*      */     }
/* 1061 */     return AWTEventMulticaster.getListeners(localAdjustmentListener, paramClass);
/*      */   }
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*      */   {
/* 1066 */     if (paramAWTEvent.id == 601) {
/* 1067 */       if (((this.eventMask & 0x100) != 0L) || (this.adjustmentListener != null))
/*      */       {
/* 1069 */         return true;
/*      */       }
/* 1071 */       return false;
/*      */     }
/* 1073 */     return super.eventEnabled(paramAWTEvent);
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
/*      */   protected void processEvent(AWTEvent paramAWTEvent)
/*      */   {
/* 1092 */     if ((paramAWTEvent instanceof AdjustmentEvent)) {
/* 1093 */       processAdjustmentEvent((AdjustmentEvent)paramAWTEvent);
/* 1094 */       return;
/*      */     }
/* 1096 */     super.processEvent(paramAWTEvent);
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
/*      */   protected void processAdjustmentEvent(AdjustmentEvent paramAdjustmentEvent)
/*      */   {
/* 1124 */     AdjustmentListener localAdjustmentListener = this.adjustmentListener;
/* 1125 */     if (localAdjustmentListener != null) {
/* 1126 */       localAdjustmentListener.adjustmentValueChanged(paramAdjustmentEvent);
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
/* 1140 */     return super.paramString() + ",val=" + this.value + ",vis=" + this.visibleAmount + ",min=" + this.minimum + ",max=" + this.maximum + (this.orientation == 1 ? ",vert" : ",horz") + ",isAdjusting=" + this.isAdjusting;
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
/* 1158 */   private int scrollbarSerializedDataVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1181 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1183 */     AWTEventMulticaster.save(paramObjectOutputStream, "adjustmentL", this.adjustmentListener);
/* 1184 */     paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException, HeadlessException
/*      */   {
/* 1204 */     GraphicsEnvironment.checkHeadless();
/* 1205 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     Object localObject;
/* 1208 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 1209 */       String str = ((String)localObject).intern();
/*      */       
/* 1211 */       if ("adjustmentL" == str) {
/* 1212 */         addAdjustmentListener((AdjustmentListener)paramObjectInputStream.readObject());
/*      */       }
/*      */       else {
/* 1215 */         paramObjectInputStream.readObject();
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
/*      */   public AccessibleContext getAccessibleContext()
/*      */   {
/* 1236 */     if (this.accessibleContext == null) {
/* 1237 */       this.accessibleContext = new AccessibleAWTScrollBar();
/*      */     }
/* 1239 */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   /* Error */
/*      */   String constructComponentName()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: ldc 1
/*      */     //   2: dup
/*      */     //   3: astore_1
/*      */     //   4: monitorenter
/*      */     //   5: new 13	java/lang/StringBuilder
/*      */     //   8: dup
/*      */     //   9: invokespecial 14	java/lang/StringBuilder:<init>	()V
/*      */     //   12: ldc 15
/*      */     //   14: invokevirtual 16	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   17: getstatic 17	java/awt/Scrollbar:nameCounter	I
/*      */     //   20: dup
/*      */     //   21: iconst_1
/*      */     //   22: iadd
/*      */     //   23: putstatic 17	java/awt/Scrollbar:nameCounter	I
/*      */     //   26: invokevirtual 18	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   29: invokevirtual 19	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   32: aload_1
/*      */     //   33: monitorexit
/*      */     //   34: areturn
/*      */     //   35: astore_2
/*      */     //   36: aload_1
/*      */     //   37: monitorexit
/*      */     //   38: aload_2
/*      */     //   39: athrow
/*      */     // Line number table:
/*      */     //   Java source line #423	-> byte code offset #0
/*      */     //   Java source line #424	-> byte code offset #5
/*      */     //   Java source line #425	-> byte code offset #35
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	40	0	this	Scrollbar
/*      */     //   3	34	1	Ljava/lang/Object;	Object
/*      */     //   35	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   5	34	35	finally
/*      */     //   35	38	35	finally
/*      */   }
/*      */   
/*      */   protected class AccessibleAWTScrollBar
/*      */     extends Component.AccessibleAWTComponent
/*      */     implements AccessibleValue
/*      */   {
/*      */     private static final long serialVersionUID = -344337268523697807L;
/*      */     
/*      */     protected AccessibleAWTScrollBar()
/*      */     {
/* 1249 */       super();
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
/*      */     public AccessibleStateSet getAccessibleStateSet()
/*      */     {
/* 1265 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/* 1266 */       if (Scrollbar.this.getValueIsAdjusting()) {
/* 1267 */         localAccessibleStateSet.add(AccessibleState.BUSY);
/*      */       }
/* 1269 */       if (Scrollbar.this.getOrientation() == 1) {
/* 1270 */         localAccessibleStateSet.add(AccessibleState.VERTICAL);
/*      */       } else {
/* 1272 */         localAccessibleStateSet.add(AccessibleState.HORIZONTAL);
/*      */       }
/* 1274 */       return localAccessibleStateSet;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public AccessibleRole getAccessibleRole()
/*      */     {
/* 1284 */       return AccessibleRole.SCROLL_BAR;
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
/*      */     public AccessibleValue getAccessibleValue()
/*      */     {
/* 1297 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Number getCurrentAccessibleValue()
/*      */     {
/* 1306 */       return Integer.valueOf(Scrollbar.this.getValue());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean setCurrentAccessibleValue(Number paramNumber)
/*      */     {
/* 1315 */       if ((paramNumber instanceof Integer)) {
/* 1316 */         Scrollbar.this.setValue(paramNumber.intValue());
/* 1317 */         return true;
/*      */       }
/* 1319 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Number getMinimumAccessibleValue()
/*      */     {
/* 1329 */       return Integer.valueOf(Scrollbar.this.getMinimum());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Number getMaximumAccessibleValue()
/*      */     {
/* 1338 */       return Integer.valueOf(Scrollbar.this.getMaximum());
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Scrollbar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */