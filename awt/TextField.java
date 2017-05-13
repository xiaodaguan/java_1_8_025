/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.peer.TextFieldPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventListener;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextField
/*     */   extends TextComponent
/*     */ {
/*     */   int columns;
/*     */   char echoChar;
/*     */   transient ActionListener actionListener;
/*     */   private static final String base = "textfield";
/* 127 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -2966288784432217853L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 141 */     Toolkit.loadLibraries();
/* 142 */     if (!GraphicsEnvironment.isHeadless()) {
/* 143 */       initIDs();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextField()
/*     */     throws HeadlessException
/*     */   {
/* 154 */     this("", 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextField(String paramString)
/*     */     throws HeadlessException
/*     */   {
/* 167 */     this(paramString, paramString != null ? paramString.length() : 0);
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
/*     */   public TextField(int paramInt)
/*     */     throws HeadlessException
/*     */   {
/* 182 */     this("", paramInt);
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
/*     */   public TextField(String paramString, int paramInt)
/*     */     throws HeadlessException
/*     */   {
/* 201 */     super(paramString);
/* 202 */     this.columns = (paramInt >= 0 ? paramInt : 0);
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
/*     */   public void addNotify()
/*     */   {
/* 220 */     synchronized (getTreeLock()) {
/* 221 */       if (this.peer == null)
/* 222 */         this.peer = getToolkit().createTextField(this);
/* 223 */       super.addNotify();
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
/*     */   public char getEchoChar()
/*     */   {
/* 246 */     return this.echoChar;
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
/*     */   public void setEchoChar(char paramChar)
/*     */   {
/* 271 */     setEchoCharacter(paramChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public synchronized void setEchoCharacter(char paramChar)
/*     */   {
/* 280 */     if (this.echoChar != paramChar) {
/* 281 */       this.echoChar = paramChar;
/* 282 */       TextFieldPeer localTextFieldPeer = (TextFieldPeer)this.peer;
/* 283 */       if (localTextFieldPeer != null) {
/* 284 */         localTextFieldPeer.setEchoChar(paramChar);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String paramString)
/*     */   {
/* 296 */     super.setText(paramString);
/*     */     
/*     */ 
/* 299 */     invalidateIfValid();
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
/*     */   public boolean echoCharIsSet()
/*     */   {
/* 316 */     return this.echoChar != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getColumns()
/*     */   {
/* 327 */     return this.columns;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setColumns(int paramInt)
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 342 */     synchronized (this) {
/* 343 */       i = this.columns;
/* 344 */       if (paramInt < 0) {
/* 345 */         throw new IllegalArgumentException("columns less than zero.");
/*     */       }
/* 347 */       if (paramInt != i) {
/* 348 */         this.columns = paramInt;
/*     */       }
/*     */     }
/*     */     
/* 352 */     if (paramInt != i) {
/* 353 */       invalidate();
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
/*     */   public Dimension getPreferredSize(int paramInt)
/*     */   {
/* 367 */     return preferredSize(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Dimension preferredSize(int paramInt)
/*     */   {
/* 376 */     synchronized (getTreeLock()) {
/* 377 */       TextFieldPeer localTextFieldPeer = (TextFieldPeer)this.peer;
/*     */       
/*     */ 
/* 380 */       return localTextFieldPeer != null ? localTextFieldPeer.getPreferredSize(paramInt) : super.preferredSize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getPreferredSize()
/*     */   {
/* 391 */     return preferredSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Dimension preferredSize()
/*     */   {
/* 400 */     synchronized (getTreeLock())
/*     */     {
/*     */ 
/* 403 */       return this.columns > 0 ? preferredSize(this.columns) : super.preferredSize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getMinimumSize(int paramInt)
/*     */   {
/* 415 */     return minimumSize(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Dimension minimumSize(int paramInt)
/*     */   {
/* 424 */     synchronized (getTreeLock()) {
/* 425 */       TextFieldPeer localTextFieldPeer = (TextFieldPeer)this.peer;
/*     */       
/*     */ 
/* 428 */       return localTextFieldPeer != null ? localTextFieldPeer.getMinimumSize(paramInt) : super.minimumSize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getMinimumSize()
/*     */   {
/* 439 */     return minimumSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Dimension minimumSize()
/*     */   {
/* 448 */     synchronized (getTreeLock())
/*     */     {
/*     */ 
/* 451 */       return this.columns > 0 ? minimumSize(this.columns) : super.minimumSize();
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
/*     */   public synchronized void addActionListener(ActionListener paramActionListener)
/*     */   {
/* 469 */     if (paramActionListener == null) {
/* 470 */       return;
/*     */     }
/* 472 */     this.actionListener = AWTEventMulticaster.add(this.actionListener, paramActionListener);
/* 473 */     this.newEventsOnly = true;
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
/*     */   public synchronized void removeActionListener(ActionListener paramActionListener)
/*     */   {
/* 490 */     if (paramActionListener == null) {
/* 491 */       return;
/*     */     }
/* 493 */     this.actionListener = AWTEventMulticaster.remove(this.actionListener, paramActionListener);
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
/* 510 */     return (ActionListener[])getListeners(ActionListener.class);
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
/* 547 */     ActionListener localActionListener = null;
/* 548 */     if (paramClass == ActionListener.class) {
/* 549 */       localActionListener = this.actionListener;
/*     */     } else {
/* 551 */       return super.getListeners(paramClass);
/*     */     }
/* 553 */     return AWTEventMulticaster.getListeners(localActionListener, paramClass);
/*     */   }
/*     */   
/*     */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*     */   {
/* 558 */     if (paramAWTEvent.id == 1001) {
/* 559 */       if (((this.eventMask & 0x80) != 0L) || (this.actionListener != null))
/*     */       {
/* 561 */         return true;
/*     */       }
/* 563 */       return false;
/*     */     }
/* 565 */     return super.eventEnabled(paramAWTEvent);
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
/*     */   protected void processEvent(AWTEvent paramAWTEvent)
/*     */   {
/* 584 */     if ((paramAWTEvent instanceof ActionEvent)) {
/* 585 */       processActionEvent((ActionEvent)paramAWTEvent);
/* 586 */       return;
/*     */     }
/* 588 */     super.processEvent(paramAWTEvent);
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
/* 615 */     ActionListener localActionListener = this.actionListener;
/* 616 */     if (localActionListener != null) {
/* 617 */       localActionListener.actionPerformed(paramActionEvent);
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
/* 631 */     String str = super.paramString();
/* 632 */     if (this.echoChar != 0) {
/* 633 */       str = str + ",echo=" + this.echoChar;
/*     */     }
/* 635 */     return str;
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
/* 647 */   private int textFieldSerializedDataVersion = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 667 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 669 */     AWTEventMulticaster.save(paramObjectOutputStream, "actionL", this.actionListener);
/* 670 */     paramObjectOutputStream.writeObject(null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException, HeadlessException
/*     */   {
/* 690 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 693 */     if (this.columns < 0) {
/* 694 */       this.columns = 0;
/*     */     }
/*     */     
/*     */     Object localObject;
/*     */     
/* 699 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 700 */       String str = ((String)localObject).intern();
/*     */       
/* 702 */       if ("actionL" == str) {
/* 703 */         addActionListener((ActionListener)paramObjectInputStream.readObject());
/*     */       }
/*     */       else {
/* 706 */         paramObjectInputStream.readObject();
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
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 728 */     if (this.accessibleContext == null) {
/* 729 */       this.accessibleContext = new AccessibleAWTTextField();
/*     */     }
/* 731 */     return this.accessibleContext;
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
/*     */     //   17: getstatic 12	java/awt/TextField:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 12	java/awt/TextField:nameCounter	I
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
/*     */     //   Java source line #210	-> byte code offset #0
/*     */     //   Java source line #211	-> byte code offset #5
/*     */     //   Java source line #212	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	TextField
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   protected class AccessibleAWTTextField
/*     */     extends TextComponent.AccessibleAWTTextComponent
/*     */   {
/*     */     private static final long serialVersionUID = 6219164359235943158L;
/*     */     
/*     */     protected AccessibleAWTTextField()
/*     */     {
/* 740 */       super();
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
/*     */     public AccessibleStateSet getAccessibleStateSet()
/*     */     {
/* 755 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/* 756 */       localAccessibleStateSet.add(AccessibleState.SINGLE_LINE);
/* 757 */       return localAccessibleStateSet;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/TextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */