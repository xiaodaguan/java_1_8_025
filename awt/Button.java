/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.peer.ButtonPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventListener;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Button
/*     */   extends Component
/*     */   implements Accessible
/*     */ {
/*     */   String label;
/*     */   String actionCommand;
/*     */   transient ActionListener actionListener;
/*     */   private static final String base = "button";
/* 109 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -8774683716313001058L;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 119 */     Toolkit.loadLibraries();
/* 120 */     if (!GraphicsEnvironment.isHeadless()) {
/* 121 */       initIDs();
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
/*     */   public Button()
/*     */     throws HeadlessException
/*     */   {
/* 139 */     this("");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Button(String paramString)
/*     */     throws HeadlessException
/*     */   {
/* 152 */     GraphicsEnvironment.checkHeadless();
/* 153 */     this.label = paramString;
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
/* 175 */     synchronized (getTreeLock()) {
/* 176 */       if (this.peer == null)
/* 177 */         this.peer = getToolkit().createButton(this);
/* 178 */       super.addNotify();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLabel()
/*     */   {
/* 190 */     return this.label;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLabel(String paramString)
/*     */   {
/* 201 */     int i = 0;
/*     */     
/* 203 */     synchronized (this) {
/* 204 */       if ((paramString != this.label) && ((this.label == null) || 
/* 205 */         (!this.label.equals(paramString)))) {
/* 206 */         this.label = paramString;
/* 207 */         ButtonPeer localButtonPeer = (ButtonPeer)this.peer;
/* 208 */         if (localButtonPeer != null) {
/* 209 */           localButtonPeer.setLabel(paramString);
/*     */         }
/* 211 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 216 */     if (i != 0) {
/* 217 */       invalidateIfValid();
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
/*     */   public void setActionCommand(String paramString)
/*     */   {
/* 234 */     this.actionCommand = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getActionCommand()
/*     */   {
/* 243 */     return this.actionCommand == null ? this.label : this.actionCommand;
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
/*     */   public synchronized void addActionListener(ActionListener paramActionListener)
/*     */   {
/* 261 */     if (paramActionListener == null) {
/* 262 */       return;
/*     */     }
/* 264 */     this.actionListener = AWTEventMulticaster.add(this.actionListener, paramActionListener);
/* 265 */     this.newEventsOnly = true;
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
/*     */   public synchronized void removeActionListener(ActionListener paramActionListener)
/*     */   {
/* 283 */     if (paramActionListener == null) {
/* 284 */       return;
/*     */     }
/* 286 */     this.actionListener = AWTEventMulticaster.remove(this.actionListener, paramActionListener);
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
/*     */   public synchronized ActionListener[] getActionListeners()
/*     */   {
/* 303 */     return (ActionListener[])getListeners(ActionListener.class);
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
/* 340 */     ActionListener localActionListener = null;
/* 341 */     if (paramClass == ActionListener.class) {
/* 342 */       localActionListener = this.actionListener;
/*     */     } else {
/* 344 */       return super.getListeners(paramClass);
/*     */     }
/* 346 */     return AWTEventMulticaster.getListeners(localActionListener, paramClass);
/*     */   }
/*     */   
/*     */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*     */   {
/* 351 */     if (paramAWTEvent.id == 1001) {
/* 352 */       if (((this.eventMask & 0x80) != 0L) || (this.actionListener != null))
/*     */       {
/* 354 */         return true;
/*     */       }
/* 356 */       return false;
/*     */     }
/* 358 */     return super.eventEnabled(paramAWTEvent);
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
/* 376 */     if ((paramAWTEvent instanceof ActionEvent)) {
/* 377 */       processActionEvent((ActionEvent)paramAWTEvent);
/* 378 */       return;
/*     */     }
/* 380 */     super.processEvent(paramAWTEvent);
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
/*     */   protected void processActionEvent(ActionEvent paramActionEvent)
/*     */   {
/* 407 */     ActionListener localActionListener = this.actionListener;
/* 408 */     if (localActionListener != null) {
/* 409 */       localActionListener.actionPerformed(paramActionEvent);
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
/* 423 */     return super.paramString() + ",label=" + this.label;
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
/* 434 */   private int buttonSerializedDataVersion = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 458 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 460 */     AWTEventMulticaster.save(paramObjectOutputStream, "actionL", this.actionListener);
/* 461 */     paramObjectOutputStream.writeObject(null);
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
/* 483 */     GraphicsEnvironment.checkHeadless();
/* 484 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object localObject;
/* 487 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 488 */       String str = ((String)localObject).intern();
/*     */       
/* 490 */       if ("actionL" == str) {
/* 491 */         addActionListener((ActionListener)paramObjectInputStream.readObject());
/*     */       }
/*     */       else {
/* 494 */         paramObjectInputStream.readObject();
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
/* 519 */     if (this.accessibleContext == null) {
/* 520 */       this.accessibleContext = new AccessibleAWTButton();
/*     */     }
/* 522 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 7
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 8	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 9	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 10
/*     */     //   14: invokevirtual 11	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 12	java/awt/Button:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 12	java/awt/Button:nameCounter	I
/*     */     //   26: invokevirtual 13	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   29: invokevirtual 14	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   32: aload_1
/*     */     //   33: monitorexit
/*     */     //   34: areturn
/*     */     //   35: astore_2
/*     */     //   36: aload_1
/*     */     //   37: monitorexit
/*     */     //   38: aload_2
/*     */     //   39: athrow
/*     */     // Line number table:
/*     */     //   Java source line #161	-> byte code offset #0
/*     */     //   Java source line #162	-> byte code offset #5
/*     */     //   Java source line #163	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	Button
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   protected class AccessibleAWTButton
/*     */     extends Component.AccessibleAWTComponent
/*     */     implements AccessibleAction, AccessibleValue
/*     */   {
/*     */     private static final long serialVersionUID = -5932203980244017102L;
/*     */     
/*     */     protected AccessibleAWTButton()
/*     */     {
/* 531 */       super();
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
/*     */     public String getAccessibleName()
/*     */     {
/* 546 */       if (this.accessibleName != null) {
/* 547 */         return this.accessibleName;
/*     */       }
/* 549 */       if (Button.this.getLabel() == null) {
/* 550 */         return super.getAccessibleName();
/*     */       }
/* 552 */       return Button.this.getLabel();
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
/*     */     public AccessibleAction getAccessibleAction()
/*     */     {
/* 566 */       return this;
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
/* 578 */       return this;
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
/* 589 */       return 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getAccessibleActionDescription(int paramInt)
/*     */     {
/* 598 */       if (paramInt == 0)
/*     */       {
/* 600 */         return "click";
/*     */       }
/* 602 */       return null;
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
/* 613 */       if (paramInt == 0)
/*     */       {
/* 615 */         Toolkit.getEventQueue().postEvent(new ActionEvent(Button.this, 1001, Button.this
/*     */         
/*     */ 
/* 618 */           .getActionCommand()));
/* 619 */         return true;
/*     */       }
/* 621 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Number getCurrentAccessibleValue()
/*     */     {
/* 633 */       return Integer.valueOf(0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean setCurrentAccessibleValue(Number paramNumber)
/*     */     {
/* 642 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Number getMinimumAccessibleValue()
/*     */     {
/* 651 */       return Integer.valueOf(0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Number getMaximumAccessibleValue()
/*     */     {
/* 660 */       return Integer.valueOf(0);
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
/* 671 */       return AccessibleRole.PUSH_BUTTON;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */