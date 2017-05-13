/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.peer.CheckboxMenuItemPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventListener;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleValue;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AWTAccessor.CheckboxMenuItemAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckboxMenuItem
/*     */   extends MenuItem
/*     */   implements ItemSelectable, Accessible
/*     */ {
/*     */   static
/*     */   {
/*     */     
/*  69 */     if (!GraphicsEnvironment.isHeadless()) {
/*  70 */       initIDs();
/*     */     }
/*     */     
/*  73 */     AWTAccessor.setCheckboxMenuItemAccessor(new AWTAccessor.CheckboxMenuItemAccessor()
/*     */     {
/*     */       public boolean getState(CheckboxMenuItem paramAnonymousCheckboxMenuItem) {
/*  76 */         return paramAnonymousCheckboxMenuItem.state;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */   boolean state = false;
/*     */   
/*     */   transient ItemListener itemListener;
/*     */   
/*     */   private static final String base = "chkmenuitem";
/*  92 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 6190621106981774043L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CheckboxMenuItem()
/*     */     throws HeadlessException
/*     */   {
/* 108 */     this("", false);
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
/*     */   public CheckboxMenuItem(String paramString)
/*     */     throws HeadlessException
/*     */   {
/* 122 */     this(paramString, false);
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
/*     */   public CheckboxMenuItem(String paramString, boolean paramBoolean)
/*     */     throws HeadlessException
/*     */   {
/* 139 */     super(paramString);
/* 140 */     this.state = paramBoolean;
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
/*     */   public void addNotify()
/*     */   {
/* 162 */     synchronized (getTreeLock()) {
/* 163 */       if (this.peer == null)
/* 164 */         this.peer = Toolkit.getDefaultToolkit().createCheckboxMenuItem(this);
/* 165 */       super.addNotify();
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
/*     */   public boolean getState()
/*     */   {
/* 179 */     return this.state;
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
/*     */   public synchronized void setState(boolean paramBoolean)
/*     */   {
/* 199 */     this.state = paramBoolean;
/* 200 */     CheckboxMenuItemPeer localCheckboxMenuItemPeer = (CheckboxMenuItemPeer)this.peer;
/* 201 */     if (localCheckboxMenuItemPeer != null) {
/* 202 */       localCheckboxMenuItemPeer.setState(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Object[] getSelectedObjects()
/*     */   {
/* 212 */     if (this.state) {
/* 213 */       Object[] arrayOfObject = new Object[1];
/* 214 */       arrayOfObject[0] = this.label;
/* 215 */       return arrayOfObject;
/*     */     }
/* 217 */     return null;
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
/* 237 */     if (paramItemListener == null) {
/* 238 */       return;
/*     */     }
/* 240 */     this.itemListener = AWTEventMulticaster.add(this.itemListener, paramItemListener);
/* 241 */     this.newEventsOnly = true;
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
/* 259 */     if (paramItemListener == null) {
/* 260 */       return;
/*     */     }
/* 262 */     this.itemListener = AWTEventMulticaster.remove(this.itemListener, paramItemListener);
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
/* 280 */     return (ItemListener[])getListeners(ItemListener.class);
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
/* 317 */     ItemListener localItemListener = null;
/* 318 */     if (paramClass == ItemListener.class) {
/* 319 */       localItemListener = this.itemListener;
/*     */     } else {
/* 321 */       return super.getListeners(paramClass);
/*     */     }
/* 323 */     return AWTEventMulticaster.getListeners(localItemListener, paramClass);
/*     */   }
/*     */   
/*     */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*     */   {
/* 328 */     if (paramAWTEvent.id == 701) {
/* 329 */       if (((this.eventMask & 0x200) != 0L) || (this.itemListener != null))
/*     */       {
/* 331 */         return true;
/*     */       }
/* 333 */       return false;
/*     */     }
/* 335 */     return super.eventEnabled(paramAWTEvent);
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
/*     */   protected void processEvent(AWTEvent paramAWTEvent)
/*     */   {
/* 356 */     if ((paramAWTEvent instanceof ItemEvent)) {
/* 357 */       processItemEvent((ItemEvent)paramAWTEvent);
/* 358 */       return;
/*     */     }
/* 360 */     super.processEvent(paramAWTEvent);
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
/*     */   protected void processItemEvent(ItemEvent paramItemEvent)
/*     */   {
/* 387 */     ItemListener localItemListener = this.itemListener;
/* 388 */     if (localItemListener != null) {
/* 389 */       localItemListener.itemStateChanged(paramItemEvent);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void doMenuEvent(long paramLong, int paramInt)
/*     */   {
/* 397 */     setState(!this.state);
/* 398 */     Toolkit.getEventQueue().postEvent(new ItemEvent(this, 701, 
/*     */     
/* 400 */       getLabel(), this.state ? 1 : 2));
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
/*     */   public String paramString()
/*     */   {
/* 416 */     return super.paramString() + ",state=" + this.state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 426 */   private int checkboxMenuItemSerializedDataVersion = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 450 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 452 */     AWTEventMulticaster.save(paramObjectOutputStream, "itemL", this.itemListener);
/* 453 */     paramObjectOutputStream.writeObject(null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException
/*     */   {
/* 471 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object localObject;
/* 474 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 475 */       String str = ((String)localObject).intern();
/*     */       
/* 477 */       if ("itemL" == str) {
/* 478 */         addItemListener((ItemListener)paramObjectInputStream.readObject());
/*     */       }
/*     */       else {
/* 481 */         paramObjectInputStream.readObject();
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
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 506 */     if (this.accessibleContext == null) {
/* 507 */       this.accessibleContext = new AccessibleAWTCheckboxMenuItem();
/*     */     }
/* 509 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 6
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 7	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 8	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 9
/*     */     //   14: invokevirtual 10	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 11	java/awt/CheckboxMenuItem:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 11	java/awt/CheckboxMenuItem:nameCounter	I
/*     */     //   26: invokevirtual 12	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   29: invokevirtual 13	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   32: aload_1
/*     */     //   33: monitorexit
/*     */     //   34: areturn
/*     */     //   35: astore_2
/*     */     //   36: aload_1
/*     */     //   37: monitorexit
/*     */     //   38: aload_2
/*     */     //   39: athrow
/*     */     // Line number table:
/*     */     //   Java source line #148	-> byte code offset #0
/*     */     //   Java source line #149	-> byte code offset #5
/*     */     //   Java source line #150	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	CheckboxMenuItem
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
/*     */   protected class AccessibleAWTCheckboxMenuItem
/*     */     extends MenuItem.AccessibleAWTMenuItem
/*     */     implements AccessibleAction, AccessibleValue
/*     */   {
/*     */     private static final long serialVersionUID = -1122642964303476L;
/*     */     
/*     */     protected AccessibleAWTCheckboxMenuItem()
/*     */     {
/* 524 */       super();
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
/*     */ 
/*     */     public AccessibleAction getAccessibleAction()
/*     */     {
/* 541 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public AccessibleValue getAccessibleValue()
/*     */     {
/* 553 */       return this;
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
/* 564 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getAccessibleActionDescription(int paramInt)
/*     */     {
/* 573 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean doAccessibleAction(int paramInt)
/*     */     {
/* 583 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Number getCurrentAccessibleValue()
/*     */     {
/* 594 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean setCurrentAccessibleValue(Number paramNumber)
/*     */     {
/* 604 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Number getMinimumAccessibleValue()
/*     */     {
/* 615 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Number getMaximumAccessibleValue()
/*     */     {
/* 626 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 636 */       return AccessibleRole.CHECK_BOX;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/CheckboxMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */