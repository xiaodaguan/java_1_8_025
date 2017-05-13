/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.peer.ScrollPanePeer;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.beans.Transient;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import sun.awt.ScrollPaneWheelScroller;
/*     */ import sun.awt.SunToolkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScrollPane
/*     */   extends Container
/*     */   implements Accessible
/*     */ {
/*     */   public static final int SCROLLBARS_AS_NEEDED = 0;
/*     */   public static final int SCROLLBARS_ALWAYS = 1;
/*     */   public static final int SCROLLBARS_NEVER = 2;
/*     */   private int scrollbarDisplayPolicy;
/*     */   private ScrollPaneAdjustable vAdjustable;
/*     */   private ScrollPaneAdjustable hAdjustable;
/*     */   private static final String base = "scrollpane";
/*     */   
/*     */   static
/*     */   {
/*     */     
/* 103 */     if (!GraphicsEnvironment.isHeadless()) {
/* 104 */       initIDs();
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
/* 162 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final boolean defaultWheelScroll = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 173 */   private boolean wheelScrollingEnabled = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 7956609840827222915L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScrollPane()
/*     */     throws HeadlessException
/*     */   {
/* 188 */     this(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @ConstructorProperties({"scrollbarDisplayPolicy"})
/*     */   public ScrollPane(int paramInt)
/*     */     throws HeadlessException
/*     */   {
/* 202 */     GraphicsEnvironment.checkHeadless();
/* 203 */     this.layoutMgr = null;
/* 204 */     this.width = 100;
/* 205 */     this.height = 100;
/* 206 */     switch (paramInt) {
/*     */     case 0: 
/*     */     case 1: 
/*     */     case 2: 
/* 210 */       this.scrollbarDisplayPolicy = paramInt;
/* 211 */       break;
/*     */     default: 
/* 213 */       throw new IllegalArgumentException("illegal scrollbar display policy");
/*     */     }
/*     */     
/* 216 */     this.vAdjustable = new ScrollPaneAdjustable(this, new PeerFixer(this), 1);
/*     */     
/* 218 */     this.hAdjustable = new ScrollPaneAdjustable(this, new PeerFixer(this), 0);
/*     */     
/* 220 */     setWheelScrollingEnabled(true);
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
/*     */   private void addToPanel(Component paramComponent, Object paramObject, int paramInt)
/*     */   {
/* 237 */     Panel localPanel = new Panel();
/* 238 */     localPanel.setLayout(new BorderLayout());
/* 239 */     localPanel.add(paramComponent);
/* 240 */     super.addImpl(localPanel, paramObject, paramInt);
/* 241 */     validate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void addImpl(Component paramComponent, Object paramObject, int paramInt)
/*     */   {
/* 253 */     synchronized (getTreeLock()) {
/* 254 */       if (getComponentCount() > 0) {
/* 255 */         remove(0);
/*     */       }
/* 257 */       if (paramInt > 0) {
/* 258 */         throw new IllegalArgumentException("position greater than 0");
/*     */       }
/*     */       
/* 261 */       if (!SunToolkit.isLightweightOrUnknown(paramComponent)) {
/* 262 */         super.addImpl(paramComponent, paramObject, paramInt);
/*     */       } else {
/* 264 */         addToPanel(paramComponent, paramObject, paramInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getScrollbarDisplayPolicy()
/*     */   {
/* 274 */     return this.scrollbarDisplayPolicy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getViewportSize()
/*     */   {
/* 282 */     Insets localInsets = getInsets();
/* 283 */     return new Dimension(this.width - localInsets.right - localInsets.left, this.height - localInsets.top - localInsets.bottom);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getHScrollbarHeight()
/*     */   {
/* 294 */     int i = 0;
/* 295 */     if (this.scrollbarDisplayPolicy != 2) {
/* 296 */       ScrollPanePeer localScrollPanePeer = (ScrollPanePeer)this.peer;
/* 297 */       if (localScrollPanePeer != null) {
/* 298 */         i = localScrollPanePeer.getHScrollbarHeight();
/*     */       }
/*     */     }
/* 301 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getVScrollbarWidth()
/*     */   {
/* 311 */     int i = 0;
/* 312 */     if (this.scrollbarDisplayPolicy != 2) {
/* 313 */       ScrollPanePeer localScrollPanePeer = (ScrollPanePeer)this.peer;
/* 314 */       if (localScrollPanePeer != null) {
/* 315 */         i = localScrollPanePeer.getVScrollbarWidth();
/*     */       }
/*     */     }
/* 318 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Adjustable getVAdjustable()
/*     */   {
/* 329 */     return this.vAdjustable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Adjustable getHAdjustable()
/*     */   {
/* 340 */     return this.hAdjustable;
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
/*     */   public void setScrollPosition(int paramInt1, int paramInt2)
/*     */   {
/* 359 */     synchronized (getTreeLock()) {
/* 360 */       if (getComponentCount() == 0) {
/* 361 */         throw new NullPointerException("child is null");
/*     */       }
/* 363 */       this.hAdjustable.setValue(paramInt1);
/* 364 */       this.vAdjustable.setValue(paramInt2);
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
/*     */   public void setScrollPosition(Point paramPoint)
/*     */   {
/* 383 */     setScrollPosition(paramPoint.x, paramPoint.y);
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
/*     */   @Transient
/*     */   public Point getScrollPosition()
/*     */   {
/* 397 */     synchronized (getTreeLock()) {
/* 398 */       if (getComponentCount() == 0) {
/* 399 */         throw new NullPointerException("child is null");
/*     */       }
/* 401 */       return new Point(this.hAdjustable.getValue(), this.vAdjustable.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setLayout(LayoutManager paramLayoutManager)
/*     */   {
/* 411 */     throw new AWTError("ScrollPane controls layout");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void doLayout()
/*     */   {
/* 423 */     layout();
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
/*     */   Dimension calculateChildSize()
/*     */   {
/* 439 */     Dimension localDimension1 = getSize();
/* 440 */     Insets localInsets = getInsets();
/* 441 */     int i = localDimension1.width - localInsets.left * 2;
/* 442 */     int j = localDimension1.height - localInsets.top * 2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 449 */     Component localComponent = getComponent(0);
/* 450 */     Dimension localDimension2 = new Dimension(localComponent.getPreferredSize());
/*     */     int k;
/* 452 */     int m; if (this.scrollbarDisplayPolicy == 0) {
/* 453 */       k = localDimension2.height > j ? 1 : 0;
/* 454 */       m = localDimension2.width > i ? 1 : 0;
/* 455 */     } else if (this.scrollbarDisplayPolicy == 1) {
/* 456 */       k = m = 1;
/*     */     } else {
/* 458 */       k = m = 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 464 */     int n = getVScrollbarWidth();
/* 465 */     int i1 = getHScrollbarHeight();
/* 466 */     if (k != 0) {
/* 467 */       i -= n;
/*     */     }
/* 469 */     if (m != 0) {
/* 470 */       j -= i1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 476 */     if (localDimension2.width < i) {
/* 477 */       localDimension2.width = i;
/*     */     }
/* 479 */     if (localDimension2.height < j) {
/* 480 */       localDimension2.height = j;
/*     */     }
/*     */     
/* 483 */     return localDimension2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void layout()
/*     */   {
/* 492 */     if (getComponentCount() == 0) {
/* 493 */       return;
/*     */     }
/* 495 */     Component localComponent = getComponent(0);
/* 496 */     Point localPoint = getScrollPosition();
/* 497 */     Dimension localDimension1 = calculateChildSize();
/* 498 */     Dimension localDimension2 = getViewportSize();
/* 499 */     Insets localInsets = getInsets();
/*     */     
/* 501 */     localComponent.reshape(localInsets.left - localPoint.x, localInsets.top - localPoint.y, localDimension1.width, localDimension1.height);
/* 502 */     ScrollPanePeer localScrollPanePeer = (ScrollPanePeer)this.peer;
/* 503 */     if (localScrollPanePeer != null) {
/* 504 */       localScrollPanePeer.childResized(localDimension1.width, localDimension1.height);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 510 */     localDimension2 = getViewportSize();
/* 511 */     this.hAdjustable.setSpan(0, localDimension1.width, localDimension2.width);
/* 512 */     this.vAdjustable.setSpan(0, localDimension1.height, localDimension2.height);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printComponents(Graphics paramGraphics)
/*     */   {
/* 522 */     if (getComponentCount() == 0) {
/* 523 */       return;
/*     */     }
/* 525 */     Component localComponent = getComponent(0);
/* 526 */     Point localPoint = localComponent.getLocation();
/* 527 */     Dimension localDimension = getViewportSize();
/* 528 */     Insets localInsets = getInsets();
/*     */     
/* 530 */     Graphics localGraphics = paramGraphics.create();
/*     */     try {
/* 532 */       localGraphics.clipRect(localInsets.left, localInsets.top, localDimension.width, localDimension.height);
/* 533 */       localGraphics.translate(localPoint.x, localPoint.y);
/* 534 */       localComponent.printAll(localGraphics);
/*     */     } finally {
/* 536 */       localGraphics.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addNotify()
/*     */   {
/* 544 */     synchronized (getTreeLock())
/*     */     {
/* 546 */       int i = 0;
/* 547 */       int j = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 553 */       if (getComponentCount() > 0) {
/* 554 */         i = this.vAdjustable.getValue();
/* 555 */         j = this.hAdjustable.getValue();
/* 556 */         this.vAdjustable.setValue(0);
/* 557 */         this.hAdjustable.setValue(0);
/*     */       }
/*     */       
/* 560 */       if (this.peer == null)
/* 561 */         this.peer = getToolkit().createScrollPane(this);
/* 562 */       super.addNotify();
/*     */       
/*     */ 
/* 565 */       if (getComponentCount() > 0) {
/* 566 */         this.vAdjustable.setValue(i);
/* 567 */         this.hAdjustable.setValue(j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String paramString()
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 584 */     switch (this.scrollbarDisplayPolicy) {
/*     */     case 0: 
/* 586 */       str = "as-needed";
/* 587 */       break;
/*     */     case 1: 
/* 589 */       str = "always";
/* 590 */       break;
/*     */     case 2: 
/* 592 */       str = "never";
/* 593 */       break;
/*     */     default: 
/* 595 */       str = "invalid display policy";
/*     */     }
/* 597 */     Point localPoint = getComponentCount() > 0 ? getScrollPosition() : new Point(0, 0);
/* 598 */     Insets localInsets = getInsets();
/*     */     
/*     */ 
/*     */ 
/* 602 */     return super.paramString() + ",ScrollPosition=(" + localPoint.x + "," + localPoint.y + ")" + ",Insets=(" + localInsets.top + "," + localInsets.left + "," + localInsets.bottom + "," + localInsets.right + ")" + ",ScrollbarDisplayPolicy=" + str + ",wheelScrollingEnabled=" + isWheelScrollingEnabled();
/*     */   }
/*     */   
/*     */   void autoProcessMouseWheel(MouseWheelEvent paramMouseWheelEvent) {
/* 606 */     processMouseWheelEvent(paramMouseWheelEvent);
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
/*     */   protected void processMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent)
/*     */   {
/* 620 */     if (isWheelScrollingEnabled()) {
/* 621 */       ScrollPaneWheelScroller.handleWheelScrolling(this, paramMouseWheelEvent);
/* 622 */       paramMouseWheelEvent.consume();
/*     */     }
/* 624 */     super.processMouseWheelEvent(paramMouseWheelEvent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean eventTypeEnabled(int paramInt)
/*     */   {
/* 632 */     if ((paramInt == 507) && (isWheelScrollingEnabled())) {
/* 633 */       return true;
/*     */     }
/*     */     
/* 636 */     return super.eventTypeEnabled(paramInt);
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
/*     */   public void setWheelScrollingEnabled(boolean paramBoolean)
/*     */   {
/* 653 */     this.wheelScrollingEnabled = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWheelScrollingEnabled()
/*     */   {
/* 664 */     return this.wheelScrollingEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 675 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException, HeadlessException
/*     */   {
/* 688 */     GraphicsEnvironment.checkHeadless();
/*     */     
/*     */ 
/* 691 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */ 
/* 694 */     this.scrollbarDisplayPolicy = localGetField.get("scrollbarDisplayPolicy", 0);
/*     */     
/* 696 */     this.hAdjustable = ((ScrollPaneAdjustable)localGetField.get("hAdjustable", null));
/* 697 */     this.vAdjustable = ((ScrollPaneAdjustable)localGetField.get("vAdjustable", null));
/*     */     
/*     */ 
/* 700 */     this.wheelScrollingEnabled = localGetField.get("wheelScrollingEnabled", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   class PeerFixer
/*     */     implements AdjustmentListener, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1043664721353696630L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private ScrollPane scroller;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     PeerFixer(ScrollPane paramScrollPane)
/*     */     {
/* 722 */       this.scroller = paramScrollPane;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
/*     */     {
/* 729 */       Adjustable localAdjustable = paramAdjustmentEvent.getAdjustable();
/* 730 */       int i = paramAdjustmentEvent.getValue();
/* 731 */       ScrollPanePeer localScrollPanePeer = (ScrollPanePeer)this.scroller.peer;
/* 732 */       if (localScrollPanePeer != null) {
/* 733 */         localScrollPanePeer.setValue(localAdjustable, i);
/*     */       }
/*     */       
/* 736 */       Component localComponent = this.scroller.getComponent(0);
/* 737 */       switch (localAdjustable.getOrientation()) {
/*     */       case 1: 
/* 739 */         localComponent.move(localComponent.getLocation().x, -i);
/* 740 */         break;
/*     */       case 0: 
/* 742 */         localComponent.move(-i, localComponent.getLocation().y);
/* 743 */         break;
/*     */       default: 
/* 745 */         throw new IllegalArgumentException("Illegal adjustable orientation");
/*     */       }
/*     */       
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
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 768 */     if (this.accessibleContext == null) {
/* 769 */       this.accessibleContext = new AccessibleAWTScrollPane();
/*     */     }
/* 771 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 1
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 21	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 22	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 23
/*     */     //   14: invokevirtual 24	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 25	java/awt/ScrollPane:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 25	java/awt/ScrollPane:nameCounter	I
/*     */     //   26: invokevirtual 26	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   29: invokevirtual 27	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   32: aload_1
/*     */     //   33: monitorexit
/*     */     //   34: areturn
/*     */     //   35: astore_2
/*     */     //   36: aload_1
/*     */     //   37: monitorexit
/*     */     //   38: aload_2
/*     */     //   39: athrow
/*     */     // Line number table:
/*     */     //   Java source line #228	-> byte code offset #0
/*     */     //   Java source line #229	-> byte code offset #5
/*     */     //   Java source line #230	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	ScrollPane
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   protected class AccessibleAWTScrollPane
/*     */     extends Container.AccessibleAWTContainer
/*     */   {
/*     */     private static final long serialVersionUID = 6100703663886637L;
/*     */     
/*     */     protected AccessibleAWTScrollPane()
/*     */     {
/* 781 */       super();
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
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 796 */       return AccessibleRole.SCROLL_PANE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/ScrollPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */