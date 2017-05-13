/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.TextEvent;
/*      */ import java.awt.event.TextListener;
/*      */ import java.awt.im.InputMethodRequests;
/*      */ import java.awt.peer.TextComponentPeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.text.BreakIterator;
/*      */ import java.util.EventListener;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import sun.awt.InputMethodSupport;
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
/*      */ public class TextComponent
/*      */   extends Component
/*      */   implements Accessible
/*      */ {
/*      */   String text;
/*   81 */   boolean editable = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int selectionStart;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int selectionEnd;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  108 */   boolean backgroundSetByClientCode = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected transient TextListener textListener;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = -2214773872412987419L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   TextComponent(String paramString)
/*      */     throws HeadlessException
/*      */   {
/*  131 */     GraphicsEnvironment.checkHeadless();
/*  132 */     this.text = (paramString != null ? paramString : "");
/*  133 */     setCursor(Cursor.getPredefinedCursor(2));
/*      */   }
/*      */   
/*      */   private void enableInputMethodsIfNecessary() {
/*  137 */     if (this.checkForEnableIM) {
/*  138 */       this.checkForEnableIM = false;
/*      */       try {
/*  140 */         Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  141 */         boolean bool = false;
/*  142 */         if ((localToolkit instanceof InputMethodSupport))
/*      */         {
/*  144 */           bool = ((InputMethodSupport)localToolkit).enableInputMethodsForTextComponent();
/*      */         }
/*  146 */         enableInputMethods(bool);
/*      */       }
/*      */       catch (Exception localException) {}
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
/*      */   public void enableInputMethods(boolean paramBoolean)
/*      */   {
/*  166 */     this.checkForEnableIM = false;
/*  167 */     super.enableInputMethods(paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */   boolean areInputMethodsEnabled()
/*      */   {
/*  173 */     if (this.checkForEnableIM) {
/*  174 */       enableInputMethodsIfNecessary();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  179 */     return (this.eventMask & 0x1000) != 0L;
/*      */   }
/*      */   
/*      */   public InputMethodRequests getInputMethodRequests() {
/*  183 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  184 */     if (localTextComponentPeer != null) return localTextComponentPeer.getInputMethodRequests();
/*  185 */     return null;
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
/*  198 */     super.addNotify();
/*  199 */     enableInputMethodsIfNecessary();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeNotify()
/*      */   {
/*  209 */     synchronized (getTreeLock()) {
/*  210 */       TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  211 */       if (localTextComponentPeer != null) {
/*  212 */         this.text = localTextComponentPeer.getText();
/*  213 */         this.selectionStart = localTextComponentPeer.getSelectionStart();
/*  214 */         this.selectionEnd = localTextComponentPeer.getSelectionEnd();
/*      */       }
/*  216 */       super.removeNotify();
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
/*      */   public synchronized void setText(String paramString)
/*      */   {
/*  230 */     int i = ((this.text == null) || (this.text.isEmpty())) && ((paramString == null) || (paramString.isEmpty())) ? 1 : 0;
/*  231 */     this.text = (paramString != null ? paramString : "");
/*  232 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*      */     
/*      */ 
/*      */ 
/*  236 */     if ((localTextComponentPeer != null) && (i == 0)) {
/*  237 */       localTextComponentPeer.setText(this.text);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized String getText()
/*      */   {
/*  249 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  250 */     if (localTextComponentPeer != null) {
/*  251 */       this.text = localTextComponentPeer.getText();
/*      */     }
/*  253 */     return this.text;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized String getSelectedText()
/*      */   {
/*  263 */     return getText().substring(getSelectionStart(), getSelectionEnd());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEditable()
/*      */   {
/*  274 */     return this.editable;
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
/*      */   public synchronized void setEditable(boolean paramBoolean)
/*      */   {
/*  294 */     if (this.editable == paramBoolean) {
/*  295 */       return;
/*      */     }
/*      */     
/*  298 */     this.editable = paramBoolean;
/*  299 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  300 */     if (localTextComponentPeer != null) {
/*  301 */       localTextComponentPeer.setEditable(paramBoolean);
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
/*      */   public Color getBackground()
/*      */   {
/*  319 */     if ((!this.editable) && (!this.backgroundSetByClientCode)) {
/*  320 */       return SystemColor.control;
/*      */     }
/*      */     
/*  323 */     return super.getBackground();
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
/*      */   public void setBackground(Color paramColor)
/*      */   {
/*  336 */     this.backgroundSetByClientCode = true;
/*  337 */     super.setBackground(paramColor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int getSelectionStart()
/*      */   {
/*  348 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  349 */     if (localTextComponentPeer != null) {
/*  350 */       this.selectionStart = localTextComponentPeer.getSelectionStart();
/*      */     }
/*  352 */     return this.selectionStart;
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
/*      */   public synchronized void setSelectionStart(int paramInt)
/*      */   {
/*  374 */     select(paramInt, getSelectionEnd());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int getSelectionEnd()
/*      */   {
/*  385 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  386 */     if (localTextComponentPeer != null) {
/*  387 */       this.selectionEnd = localTextComponentPeer.getSelectionEnd();
/*      */     }
/*  389 */     return this.selectionEnd;
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
/*      */   public synchronized void setSelectionEnd(int paramInt)
/*      */   {
/*  410 */     select(getSelectionStart(), paramInt);
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
/*      */   public synchronized void select(int paramInt1, int paramInt2)
/*      */   {
/*  446 */     String str = getText();
/*  447 */     if (paramInt1 < 0) {
/*  448 */       paramInt1 = 0;
/*      */     }
/*  450 */     if (paramInt1 > str.length()) {
/*  451 */       paramInt1 = str.length();
/*      */     }
/*  453 */     if (paramInt2 > str.length()) {
/*  454 */       paramInt2 = str.length();
/*      */     }
/*  456 */     if (paramInt2 < paramInt1) {
/*  457 */       paramInt2 = paramInt1;
/*      */     }
/*      */     
/*  460 */     this.selectionStart = paramInt1;
/*  461 */     this.selectionEnd = paramInt2;
/*      */     
/*  463 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  464 */     if (localTextComponentPeer != null) {
/*  465 */       localTextComponentPeer.select(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void selectAll()
/*      */   {
/*  474 */     this.selectionStart = 0;
/*  475 */     this.selectionEnd = getText().length();
/*      */     
/*  477 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  478 */     if (localTextComponentPeer != null) {
/*  479 */       localTextComponentPeer.select(this.selectionStart, this.selectionEnd);
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
/*      */   public synchronized void setCaretPosition(int paramInt)
/*      */   {
/*  500 */     if (paramInt < 0) {
/*  501 */       throw new IllegalArgumentException("position less than zero.");
/*      */     }
/*      */     
/*  504 */     int i = getText().length();
/*  505 */     if (paramInt > i) {
/*  506 */       paramInt = i;
/*      */     }
/*      */     
/*  509 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  510 */     if (localTextComponentPeer != null) {
/*  511 */       localTextComponentPeer.setCaretPosition(paramInt);
/*      */     } else {
/*  513 */       select(paramInt, paramInt);
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
/*      */   public synchronized int getCaretPosition()
/*      */   {
/*  529 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  530 */     int i = 0;
/*      */     
/*  532 */     if (localTextComponentPeer != null) {
/*  533 */       i = localTextComponentPeer.getCaretPosition();
/*      */     } else {
/*  535 */       i = this.selectionStart;
/*      */     }
/*  537 */     int j = getText().length();
/*  538 */     if (i > j) {
/*  539 */       i = j;
/*      */     }
/*  541 */     return i;
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
/*      */   public synchronized void addTextListener(TextListener paramTextListener)
/*      */   {
/*  558 */     if (paramTextListener == null) {
/*  559 */       return;
/*      */     }
/*  561 */     this.textListener = AWTEventMulticaster.add(this.textListener, paramTextListener);
/*  562 */     this.newEventsOnly = true;
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
/*      */   public synchronized void removeTextListener(TextListener paramTextListener)
/*      */   {
/*  580 */     if (paramTextListener == null) {
/*  581 */       return;
/*      */     }
/*  583 */     this.textListener = AWTEventMulticaster.remove(this.textListener, paramTextListener);
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
/*      */   public synchronized TextListener[] getTextListeners()
/*      */   {
/*  600 */     return (TextListener[])getListeners(TextListener.class);
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
/*      */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*      */   {
/*  637 */     TextListener localTextListener = null;
/*  638 */     if (paramClass == TextListener.class) {
/*  639 */       localTextListener = this.textListener;
/*      */     } else {
/*  641 */       return super.getListeners(paramClass);
/*      */     }
/*  643 */     return AWTEventMulticaster.getListeners(localTextListener, paramClass);
/*      */   }
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent)
/*      */   {
/*  648 */     if (paramAWTEvent.id == 900) {
/*  649 */       if (((this.eventMask & 0x400) != 0L) || (this.textListener != null))
/*      */       {
/*  651 */         return true;
/*      */       }
/*  653 */       return false;
/*      */     }
/*  655 */     return super.eventEnabled(paramAWTEvent);
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
/*      */   protected void processEvent(AWTEvent paramAWTEvent)
/*      */   {
/*  669 */     if ((paramAWTEvent instanceof TextEvent)) {
/*  670 */       processTextEvent((TextEvent)paramAWTEvent);
/*  671 */       return;
/*      */     }
/*  673 */     super.processEvent(paramAWTEvent);
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
/*      */   protected void processTextEvent(TextEvent paramTextEvent)
/*      */   {
/*  696 */     TextListener localTextListener = this.textListener;
/*  697 */     if (localTextListener != null) {
/*  698 */       int i = paramTextEvent.getID();
/*  699 */       switch (i) {
/*      */       case 900: 
/*  701 */         localTextListener.textValueChanged(paramTextEvent);
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
/*      */ 
/*      */ 
/*      */   protected String paramString()
/*      */   {
/*  718 */     String str = super.paramString() + ",text=" + getText();
/*  719 */     if (this.editable) {
/*  720 */       str = str + ",editable";
/*      */     }
/*  722 */     return str + ",selection=" + getSelectionStart() + "-" + getSelectionEnd();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean canAccessClipboard()
/*      */   {
/*  729 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  730 */     if (localSecurityManager == null) return true;
/*      */     try {
/*  732 */       localSecurityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/*  733 */       return true;
/*      */     } catch (SecurityException localSecurityException) {}
/*  735 */     return false;
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
/*  746 */   private int textComponentSerializedDataVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  769 */     TextComponentPeer localTextComponentPeer = (TextComponentPeer)this.peer;
/*  770 */     if (localTextComponentPeer != null) {
/*  771 */       this.text = localTextComponentPeer.getText();
/*  772 */       this.selectionStart = localTextComponentPeer.getSelectionStart();
/*  773 */       this.selectionEnd = localTextComponentPeer.getSelectionEnd();
/*      */     }
/*      */     
/*  776 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/*  778 */     AWTEventMulticaster.save(paramObjectOutputStream, "textL", this.textListener);
/*  779 */     paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException, HeadlessException
/*      */   {
/*  798 */     GraphicsEnvironment.checkHeadless();
/*  799 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */ 
/*      */ 
/*  803 */     this.text = (this.text != null ? this.text : "");
/*  804 */     select(this.selectionStart, this.selectionEnd);
/*      */     
/*      */     Object localObject;
/*  807 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/*  808 */       String str = ((String)localObject).intern();
/*      */       
/*  810 */       if ("textL" == str) {
/*  811 */         addTextListener((TextListener)paramObjectInputStream.readObject());
/*      */       }
/*      */       else {
/*  814 */         paramObjectInputStream.readObject();
/*      */       }
/*      */     }
/*  817 */     enableInputMethodsIfNecessary();
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
/*  836 */     if (this.accessibleContext == null) {
/*  837 */       this.accessibleContext = new AccessibleAWTTextComponent();
/*      */     }
/*  839 */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected class AccessibleAWTTextComponent
/*      */     extends Component.AccessibleAWTComponent
/*      */     implements AccessibleText, TextListener
/*      */   {
/*      */     private static final long serialVersionUID = 3631432373506317811L;
/*      */     
/*      */ 
/*      */     private static final boolean NEXT = true;
/*      */     
/*      */ 
/*      */     private static final boolean PREVIOUS = false;
/*      */     
/*      */ 
/*      */ 
/*      */     public AccessibleAWTTextComponent()
/*      */     {
/*  861 */       super();
/*  862 */       TextComponent.this.addTextListener(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void textValueChanged(TextEvent paramTextEvent)
/*      */     {
/*  869 */       Integer localInteger = Integer.valueOf(TextComponent.this.getCaretPosition());
/*  870 */       firePropertyChange("AccessibleText", null, localInteger);
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
/*      */     public AccessibleStateSet getAccessibleStateSet()
/*      */     {
/*  887 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/*  888 */       if (TextComponent.this.isEditable()) {
/*  889 */         localAccessibleStateSet.add(AccessibleState.EDITABLE);
/*      */       }
/*  891 */       return localAccessibleStateSet;
/*      */     }
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
/*  903 */       return AccessibleRole.TEXT;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public AccessibleText getAccessibleText()
/*      */     {
/*  915 */       return this;
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
/*      */     public int getIndexAtPoint(Point paramPoint)
/*      */     {
/*  935 */       return -1;
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
/*      */     public Rectangle getCharacterBounds(int paramInt)
/*      */     {
/*  948 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int getCharCount()
/*      */     {
/*  957 */       return TextComponent.this.getText().length();
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
/*      */     public int getCaretPosition()
/*      */     {
/*  970 */       return TextComponent.this.getCaretPosition();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public AttributeSet getCharacterAttribute(int paramInt)
/*      */     {
/*  980 */       return null;
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
/*      */     public int getSelectionStart()
/*      */     {
/*  993 */       return TextComponent.this.getSelectionStart();
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
/*      */     public int getSelectionEnd()
/*      */     {
/* 1006 */       return TextComponent.this.getSelectionEnd();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getSelectedText()
/*      */     {
/* 1015 */       String str = TextComponent.this.getSelectedText();
/*      */       
/* 1017 */       if ((str == null) || (str.equals(""))) {
/* 1018 */         return null;
/*      */       }
/* 1020 */       return str;
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
/*      */     public String getAtIndex(int paramInt1, int paramInt2)
/*      */     {
/* 1033 */       if ((paramInt2 < 0) || (paramInt2 >= TextComponent.this.getText().length()))
/* 1034 */         return null;
/*      */       String str;
/* 1036 */       BreakIterator localBreakIterator; int i; switch (paramInt1) {
/*      */       case 1: 
/* 1038 */         return TextComponent.this.getText().substring(paramInt2, paramInt2 + 1);
/*      */       case 2: 
/* 1040 */         str = TextComponent.this.getText();
/* 1041 */         localBreakIterator = BreakIterator.getWordInstance();
/* 1042 */         localBreakIterator.setText(str);
/* 1043 */         i = localBreakIterator.following(paramInt2);
/* 1044 */         return str.substring(localBreakIterator.previous(), i);
/*      */       
/*      */       case 3: 
/* 1047 */         str = TextComponent.this.getText();
/* 1048 */         localBreakIterator = BreakIterator.getSentenceInstance();
/* 1049 */         localBreakIterator.setText(str);
/* 1050 */         i = localBreakIterator.following(paramInt2);
/* 1051 */         return str.substring(localBreakIterator.previous(), i);
/*      */       }
/*      */       
/* 1054 */       return null;
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
/*      */     private int findWordLimit(int paramInt, BreakIterator paramBreakIterator, boolean paramBoolean, String paramString)
/*      */     {
/* 1073 */       int i = paramBoolean == true ? paramBreakIterator.following(paramInt) : paramBreakIterator.preceding(paramInt);
/*      */       
/* 1075 */       int j = paramBoolean == true ? paramBreakIterator.next() : paramBreakIterator.previous();
/* 1076 */       while (j != -1) {
/* 1077 */         for (int k = Math.min(i, j); k < Math.max(i, j); k++) {
/* 1078 */           if (Character.isLetter(paramString.charAt(k))) {
/* 1079 */             return i;
/*      */           }
/*      */         }
/* 1082 */         i = j;
/*      */         
/* 1084 */         j = paramBoolean == true ? paramBreakIterator.next() : paramBreakIterator.previous();
/*      */       }
/* 1086 */       return -1;
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
/*      */     public String getAfterIndex(int paramInt1, int paramInt2)
/*      */     {
/* 1099 */       if ((paramInt2 < 0) || (paramInt2 >= TextComponent.this.getText().length()))
/* 1100 */         return null;
/*      */       String str;
/* 1102 */       BreakIterator localBreakIterator; int i; int j; switch (paramInt1) {
/*      */       case 1: 
/* 1104 */         if (paramInt2 + 1 >= TextComponent.this.getText().length()) {
/* 1105 */           return null;
/*      */         }
/* 1107 */         return TextComponent.this.getText().substring(paramInt2 + 1, paramInt2 + 2);
/*      */       case 2: 
/* 1109 */         str = TextComponent.this.getText();
/* 1110 */         localBreakIterator = BreakIterator.getWordInstance();
/* 1111 */         localBreakIterator.setText(str);
/* 1112 */         i = findWordLimit(paramInt2, localBreakIterator, true, str);
/* 1113 */         if ((i == -1) || (i >= str.length())) {
/* 1114 */           return null;
/*      */         }
/* 1116 */         j = localBreakIterator.following(i);
/* 1117 */         if ((j == -1) || (j >= str.length())) {
/* 1118 */           return null;
/*      */         }
/* 1120 */         return str.substring(i, j);
/*      */       
/*      */       case 3: 
/* 1123 */         str = TextComponent.this.getText();
/* 1124 */         localBreakIterator = BreakIterator.getSentenceInstance();
/* 1125 */         localBreakIterator.setText(str);
/* 1126 */         i = localBreakIterator.following(paramInt2);
/* 1127 */         if ((i == -1) || (i >= str.length())) {
/* 1128 */           return null;
/*      */         }
/* 1130 */         j = localBreakIterator.following(i);
/* 1131 */         if ((j == -1) || (j >= str.length())) {
/* 1132 */           return null;
/*      */         }
/* 1134 */         return str.substring(i, j);
/*      */       }
/*      */       
/* 1137 */       return null;
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
/*      */     public String getBeforeIndex(int paramInt1, int paramInt2)
/*      */     {
/* 1152 */       if ((paramInt2 < 0) || (paramInt2 > TextComponent.this.getText().length() - 1))
/* 1153 */         return null;
/*      */       String str;
/* 1155 */       BreakIterator localBreakIterator; int i; int j; switch (paramInt1) {
/*      */       case 1: 
/* 1157 */         if (paramInt2 == 0) {
/* 1158 */           return null;
/*      */         }
/* 1160 */         return TextComponent.this.getText().substring(paramInt2 - 1, paramInt2);
/*      */       case 2: 
/* 1162 */         str = TextComponent.this.getText();
/* 1163 */         localBreakIterator = BreakIterator.getWordInstance();
/* 1164 */         localBreakIterator.setText(str);
/* 1165 */         i = findWordLimit(paramInt2, localBreakIterator, false, str);
/* 1166 */         if (i == -1) {
/* 1167 */           return null;
/*      */         }
/* 1169 */         j = localBreakIterator.preceding(i);
/* 1170 */         if (j == -1) {
/* 1171 */           return null;
/*      */         }
/* 1173 */         return str.substring(j, i);
/*      */       
/*      */       case 3: 
/* 1176 */         str = TextComponent.this.getText();
/* 1177 */         localBreakIterator = BreakIterator.getSentenceInstance();
/* 1178 */         localBreakIterator.setText(str);
/* 1179 */         i = localBreakIterator.following(paramInt2);
/* 1180 */         i = localBreakIterator.previous();
/* 1181 */         j = localBreakIterator.previous();
/* 1182 */         if (j == -1) {
/* 1183 */           return null;
/*      */         }
/* 1185 */         return str.substring(j, i);
/*      */       }
/*      */       
/* 1188 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/* 1193 */   private boolean checkForEnableIM = true;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/TextComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */