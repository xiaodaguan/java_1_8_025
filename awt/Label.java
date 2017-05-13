/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.LabelPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import javax.accessibility.Accessible;
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
/*     */ public class Label
/*     */   extends Component
/*     */   implements Accessible
/*     */ {
/*     */   public static final int LEFT = 0;
/*     */   public static final int CENTER = 1;
/*     */   public static final int RIGHT = 2;
/*     */   String text;
/*     */   
/*     */   static
/*     */   {
/*     */     
/*  59 */     if (!GraphicsEnvironment.isHeadless()) {
/*  60 */       initIDs();
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
/*  99 */   int alignment = 0;
/*     */   
/*     */   private static final String base = "label";
/* 102 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 3094126758329070636L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Label()
/*     */     throws HeadlessException
/*     */   {
/* 117 */     this("", 0);
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
/*     */   public Label(String paramString)
/*     */     throws HeadlessException
/*     */   {
/* 132 */     this(paramString, 0);
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
/*     */   public Label(String paramString, int paramInt)
/*     */     throws HeadlessException
/*     */   {
/* 150 */     GraphicsEnvironment.checkHeadless();
/* 151 */     this.text = paramString;
/* 152 */     setAlignment(paramInt);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException, HeadlessException
/*     */   {
/* 166 */     GraphicsEnvironment.checkHeadless();
/* 167 */     paramObjectInputStream.defaultReadObject();
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
/*     */   public void addNotify()
/*     */   {
/* 186 */     synchronized (getTreeLock()) {
/* 187 */       if (this.peer == null)
/* 188 */         this.peer = getToolkit().createLabel(this);
/* 189 */       super.addNotify();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAlignment()
/*     */   {
/* 200 */     return this.alignment;
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
/*     */   public synchronized void setAlignment(int paramInt)
/*     */   {
/* 213 */     switch (paramInt) {
/*     */     case 0: 
/*     */     case 1: 
/*     */     case 2: 
/* 217 */       this.alignment = paramInt;
/* 218 */       LabelPeer localLabelPeer = (LabelPeer)this.peer;
/* 219 */       if (localLabelPeer != null) {
/* 220 */         localLabelPeer.setAlignment(paramInt);
/*     */       }
/* 222 */       return;
/*     */     }
/* 224 */     throw new IllegalArgumentException("improper alignment: " + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getText()
/*     */   {
/* 234 */     return this.text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String paramString)
/*     */   {
/* 246 */     int i = 0;
/* 247 */     synchronized (this) {
/* 248 */       if ((paramString != this.text) && ((this.text == null) || 
/* 249 */         (!this.text.equals(paramString)))) {
/* 250 */         this.text = paramString;
/* 251 */         LabelPeer localLabelPeer = (LabelPeer)this.peer;
/* 252 */         if (localLabelPeer != null) {
/* 253 */           localLabelPeer.setText(paramString);
/*     */         }
/* 255 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 260 */     if (i != 0) {
/* 261 */       invalidateIfValid();
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
/* 275 */     String str = "";
/* 276 */     switch (this.alignment) {
/* 277 */     case 0:  str = "left"; break;
/* 278 */     case 1:  str = "center"; break;
/* 279 */     case 2:  str = "right";
/*     */     }
/* 281 */     return super.paramString() + ",align=" + str + ",text=" + this.text;
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
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 306 */     if (this.accessibleContext == null) {
/* 307 */       this.accessibleContext = new AccessibleAWTLabel();
/*     */     }
/* 309 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 2
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 10	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 11	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 12
/*     */     //   14: invokevirtual 13	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 14	java/awt/Label:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 14	java/awt/Label:nameCounter	I
/*     */     //   26: invokevirtual 15	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   29: invokevirtual 16	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   32: aload_1
/*     */     //   33: monitorexit
/*     */     //   34: areturn
/*     */     //   35: astore_2
/*     */     //   36: aload_1
/*     */     //   37: monitorexit
/*     */     //   38: aload_2
/*     */     //   39: athrow
/*     */     // Line number table:
/*     */     //   Java source line #175	-> byte code offset #0
/*     */     //   Java source line #176	-> byte code offset #5
/*     */     //   Java source line #177	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	Label
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
/*     */   protected class AccessibleAWTLabel
/*     */     extends Component.AccessibleAWTComponent
/*     */   {
/*     */     private static final long serialVersionUID = -3568967560160480438L;
/*     */     
/*     */     public AccessibleAWTLabel()
/*     */     {
/* 326 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getAccessibleName()
/*     */     {
/* 337 */       if (this.accessibleName != null) {
/* 338 */         return this.accessibleName;
/*     */       }
/* 340 */       if (Label.this.getText() == null) {
/* 341 */         return super.getAccessibleName();
/*     */       }
/* 343 */       return Label.this.getText();
/*     */     }
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
/* 355 */       return AccessibleRole.LABEL;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Label.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */