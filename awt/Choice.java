/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.peer.ChoicePeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventListener;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Choice
/*     */   extends Component
/*     */   implements ItemSelectable, Accessible
/*     */ {
/*     */   Vector<String> pItems;
/*  94 */   int selectedIndex = -1;
/*     */   
/*     */   transient ItemListener itemListener;
/*     */   
/*     */   private static final String base = "choice";
/*  99 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */   private static final long serialVersionUID = -4075310674757313071L;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 108 */     Toolkit.loadLibraries();
/*     */     
/* 110 */     if (!GraphicsEnvironment.isHeadless()) {
/* 111 */       initIDs();
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
/*     */   public Choice()
/*     */     throws HeadlessException
/*     */   {
/* 128 */     GraphicsEnvironment.checkHeadless();
/* 129 */     this.pItems = new Vector();
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
/*     */   public void addNotify()
/*     */   {
/* 150 */     synchronized (getTreeLock()) {
/* 151 */       if (this.peer == null)
/* 152 */         this.peer = getToolkit().createChoice(this);
/* 153 */       super.addNotify();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getItemCount()
/*     */   {
/* 164 */     return countItems();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int countItems()
/*     */   {
/* 173 */     return this.pItems.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getItem(int paramInt)
/*     */   {
/* 183 */     return getItemImpl(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final String getItemImpl(int paramInt)
/*     */   {
/* 191 */     return (String)this.pItems.elementAt(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(String paramString)
/*     */   {
/* 202 */     addItem(paramString);
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
/*     */   public void addItem(String paramString)
/*     */   {
/* 215 */     synchronized (this) {
/* 216 */       insertNoInvalidate(paramString, this.pItems.size());
/*     */     }
/*     */     
/*     */ 
/* 220 */     invalidateIfValid();
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
/*     */   private void insertNoInvalidate(String paramString, int paramInt)
/*     */   {
/* 234 */     if (paramString == null) {
/* 235 */       throw new NullPointerException("cannot add null item to Choice");
/*     */     }
/*     */     
/* 238 */     this.pItems.insertElementAt(paramString, paramInt);
/* 239 */     ChoicePeer localChoicePeer = (ChoicePeer)this.peer;
/* 240 */     if (localChoicePeer != null) {
/* 241 */       localChoicePeer.add(paramString, paramInt);
/*     */     }
/*     */     
/* 244 */     if ((this.selectedIndex < 0) || (this.selectedIndex >= paramInt)) {
/* 245 */       select(0);
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
/*     */   public void insert(String paramString, int paramInt)
/*     */   {
/* 269 */     synchronized (this) {
/* 270 */       if (paramInt < 0) {
/* 271 */         throw new IllegalArgumentException("index less than zero.");
/*     */       }
/*     */       
/* 274 */       paramInt = Math.min(paramInt, this.pItems.size());
/*     */       
/* 276 */       insertNoInvalidate(paramString, paramInt);
/*     */     }
/*     */     
/*     */ 
/* 280 */     invalidateIfValid();
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
/*     */   public void remove(String paramString)
/*     */   {
/* 297 */     synchronized (this) {
/* 298 */       int i = this.pItems.indexOf(paramString);
/* 299 */       if (i < 0) {
/* 300 */         throw new IllegalArgumentException("item " + paramString + " not found in choice");
/*     */       }
/*     */       
/* 303 */       removeNoInvalidate(i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 308 */     invalidateIfValid();
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
/*     */   public void remove(int paramInt)
/*     */   {
/* 325 */     synchronized (this) {
/* 326 */       removeNoInvalidate(paramInt);
/*     */     }
/*     */     
/*     */ 
/* 330 */     invalidateIfValid();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void removeNoInvalidate(int paramInt)
/*     */   {
/* 341 */     this.pItems.removeElementAt(paramInt);
/* 342 */     ChoicePeer localChoicePeer = (ChoicePeer)this.peer;
/* 343 */     if (localChoicePeer != null) {
/* 344 */       localChoicePeer.remove(paramInt);
/*     */     }
/*     */     
/* 347 */     if (this.pItems.size() == 0) {
/* 348 */       this.selectedIndex = -1;
/* 349 */     } else if (this.selectedIndex == paramInt) {
/* 350 */       select(0);
/* 351 */     } else if (this.selectedIndex > paramInt) {
/* 352 */       select(this.selectedIndex - 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAll()
/*     */   {
/* 363 */     synchronized (this) {
/* 364 */       if (this.peer != null) {
/* 365 */         ((ChoicePeer)this.peer).removeAll();
/*     */       }
/* 367 */       this.pItems.removeAllElements();
/* 368 */       this.selectedIndex = -1;
/*     */     }
/*     */     
/*     */ 
/* 372 */     invalidateIfValid();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized String getSelectedItem()
/*     */   {
/* 382 */     return this.selectedIndex >= 0 ? getItem(this.selectedIndex) : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Object[] getSelectedObjects()
/*     */   {
/* 391 */     if (this.selectedIndex >= 0) {
/* 392 */       Object[] arrayOfObject = new Object[1];
/* 393 */       arrayOfObject[0] = getItem(this.selectedIndex);
/* 394 */       return arrayOfObject;
/*     */     }
/* 396 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSelectedIndex()
/*     */   {
/* 408 */     return this.selectedIndex;
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
/*     */   public synchronized void select(int paramInt)
/*     */   {
/* 429 */     if ((paramInt >= this.pItems.size()) || (paramInt < 0)) {
/* 430 */       throw new IllegalArgumentException("illegal Choice item position: " + paramInt);
/*     */     }
/* 432 */     if (this.pItems.size() > 0) {
/* 433 */       this.selectedIndex = paramInt;
/* 434 */       ChoicePeer localChoicePeer = (ChoicePeer)this.peer;
/* 435 */       if (localChoicePeer != null) {
/* 436 */         localChoicePeer.select(paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void select(String paramString)
/*     */   {
/* 458 */     int i = this.pItems.indexOf(paramString);
/* 459 */     if (i >= 0) {
/* 460 */       select(i);
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
/*     */   public synchronized void addItemListener(ItemListener paramItemListener)
/*     */   {
/* 481 */     if (paramItemListener == null) {
/* 482 */       return;
/*     */     }
/* 484 */     this.itemListener = AWTEventMulticaster.add(this.itemListener, paramItemListener);
/* 485 */     this.newEventsOnly = true;
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
/*     */   public synchronized void removeItemListener(ItemListener paramItemListener)
/*     */   {
/* 503 */     if (paramItemListener == null) {
/* 504 */       return;
/*     */     }
/* 506 */     this.itemListener = AWTEventMulticaster.remove(this.itemListener, paramItemListener);
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
/*     */   public synchronized ItemListener[] getItemListeners()
/*     */   {
/* 524 */     return (ItemListener[])getListeners(ItemListener.class);
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
/*     */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*     */   {
/* 561 */     ItemListener localItemListener = null;
/* 562 */     if (paramClass == ItemListener.class) {
/* 563 */       localItemListener = this.itemListener;
/*     */     } else {
/* 565 */       return super.getListeners(paramClass);
/*     */     }
/* 567 */     return AWTEventMulticaster.getListeners(localItemListener, paramClass);
/*     */   }
/*     */   
/*     */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*     */   {
/* 572 */     if (paramAWTEvent.id == 701) {
/* 573 */       if (((this.eventMask & 0x200) != 0L) || (this.itemListener != null))
/*     */       {
/* 575 */         return true;
/*     */       }
/* 577 */       return false;
/*     */     }
/* 579 */     return super.eventEnabled(paramAWTEvent);
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
/*     */   protected void processEvent(AWTEvent paramAWTEvent)
/*     */   {
/* 597 */     if ((paramAWTEvent instanceof ItemEvent)) {
/* 598 */       processItemEvent((ItemEvent)paramAWTEvent);
/* 599 */       return;
/*     */     }
/* 601 */     super.processEvent(paramAWTEvent);
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
/*     */   protected void processItemEvent(ItemEvent paramItemEvent)
/*     */   {
/* 629 */     ItemListener localItemListener = this.itemListener;
/* 630 */     if (localItemListener != null) {
/* 631 */       localItemListener.itemStateChanged(paramItemEvent);
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
/*     */   protected String paramString()
/*     */   {
/* 645 */     return super.paramString() + ",current=" + getSelectedItem();
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
/* 656 */   private int choiceSerializedDataVersion = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 680 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 682 */     AWTEventMulticaster.save(paramObjectOutputStream, "itemL", this.itemListener);
/* 683 */     paramObjectOutputStream.writeObject(null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException, HeadlessException
/*     */   {
/* 705 */     GraphicsEnvironment.checkHeadless();
/* 706 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object localObject;
/* 709 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 710 */       String str = ((String)localObject).intern();
/*     */       
/* 712 */       if ("itemL" == str) {
/* 713 */         addItemListener((ItemListener)paramObjectInputStream.readObject());
/*     */       }
/*     */       else {
/* 716 */         paramObjectInputStream.readObject();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 742 */     if (this.accessibleContext == null) {
/* 743 */       this.accessibleContext = new AccessibleAWTChoice();
/*     */     }
/* 745 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 8
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 9	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 10	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 11
/*     */     //   14: invokevirtual 12	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 13	java/awt/Choice:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 13	java/awt/Choice:nameCounter	I
/*     */     //   26: invokevirtual 14	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   29: invokevirtual 15	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   32: aload_1
/*     */     //   33: monitorexit
/*     */     //   34: areturn
/*     */     //   35: astore_2
/*     */     //   36: aload_1
/*     */     //   37: monitorexit
/*     */     //   38: aload_2
/*     */     //   39: athrow
/*     */     // Line number table:
/*     */     //   Java source line #137	-> byte code offset #0
/*     */     //   Java source line #138	-> byte code offset #5
/*     */     //   Java source line #139	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	Choice
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   protected class AccessibleAWTChoice
/*     */     extends Component.AccessibleAWTComponent
/*     */     implements AccessibleAction
/*     */   {
/*     */     private static final long serialVersionUID = 7175603582428509322L;
/*     */     
/*     */     public AccessibleAWTChoice()
/*     */     {
/* 763 */       super();
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
/*     */     public AccessibleAction getAccessibleAction()
/*     */     {
/* 776 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 787 */       return AccessibleRole.COMBO_BOX;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getAccessibleActionCount()
/*     */     {
/* 798 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getAccessibleActionDescription(int paramInt)
/*     */     {
/* 809 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean doAccessibleAction(int paramInt)
/*     */     {
/* 820 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Choice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */