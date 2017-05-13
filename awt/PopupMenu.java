/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.PopupMenuPeer;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AWTAccessor.PopupMenuAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PopupMenu
/*     */   extends Menu
/*     */ {
/*     */   private static final String base = "popup";
/*  49 */   static int nameCounter = 0;
/*     */   
/*  51 */   transient boolean isTrayIconPopup = false;
/*     */   private static final long serialVersionUID = -4620452533522760060L;
/*     */   
/*  54 */   static { AWTAccessor.setPopupMenuAccessor(new AWTAccessor.PopupMenuAccessor()
/*     */     {
/*     */       public boolean isTrayIconPopup(PopupMenu paramAnonymousPopupMenu) {
/*  57 */         return paramAnonymousPopupMenu.isTrayIconPopup;
/*     */       }
/*     */     }); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PopupMenu()
/*     */     throws HeadlessException
/*     */   {
/*  74 */     this("");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PopupMenu(String paramString)
/*     */     throws HeadlessException
/*     */   {
/*  87 */     super(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MenuContainer getParent()
/*     */   {
/*  94 */     if (this.isTrayIconPopup) {
/*  95 */       return null;
/*     */     }
/*  97 */     return super.getParent();
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
/* 116 */     synchronized (getTreeLock())
/*     */     {
/*     */ 
/* 119 */       if ((this.parent != null) && (!(this.parent instanceof Component))) {
/* 120 */         super.addNotify();
/*     */       }
/*     */       else {
/* 123 */         if (this.peer == null)
/* 124 */           this.peer = Toolkit.getDefaultToolkit().createPopupMenu(this);
/* 125 */         int i = getItemCount();
/* 126 */         for (int j = 0; j < i; j++) {
/* 127 */           MenuItem localMenuItem = getItem(j);
/* 128 */           localMenuItem.parent = this;
/* 129 */           localMenuItem.addNotify();
/*     */         }
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
/*     */ 
/*     */ 
/*     */   public void show(Component paramComponent, int paramInt1, int paramInt2)
/*     */   {
/* 158 */     MenuContainer localMenuContainer = this.parent;
/* 159 */     if (localMenuContainer == null) {
/* 160 */       throw new NullPointerException("parent is null");
/*     */     }
/* 162 */     if (!(localMenuContainer instanceof Component)) {
/* 163 */       throw new IllegalArgumentException("PopupMenus with non-Component parents cannot be shown");
/*     */     }
/*     */     
/* 166 */     Component localComponent = (Component)localMenuContainer;
/*     */     
/*     */ 
/*     */ 
/* 170 */     if (localComponent != paramComponent) {
/* 171 */       if ((localComponent instanceof Container)) {
/* 172 */         if (!((Container)localComponent).isAncestorOf(paramComponent)) {
/* 173 */           throw new IllegalArgumentException("origin not in parent's hierarchy");
/*     */         }
/*     */       } else {
/* 176 */         throw new IllegalArgumentException("origin not in parent's hierarchy");
/*     */       }
/*     */     }
/* 179 */     if ((localComponent.getPeer() == null) || (!localComponent.isShowing())) {
/* 180 */       throw new RuntimeException("parent not showing on screen");
/*     */     }
/* 182 */     if (this.peer == null) {
/* 183 */       addNotify();
/*     */     }
/* 185 */     synchronized (getTreeLock()) {
/* 186 */       if (this.peer != null) {
/* 187 */         ((PopupMenuPeer)this.peer).show(new Event(paramComponent, 0L, 501, paramInt1, paramInt2, 0, 0));
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
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 207 */     if (this.accessibleContext == null) {
/* 208 */       this.accessibleContext = new AccessibleAWTPopupMenu();
/*     */     }
/* 210 */     return this.accessibleContext;
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
/*     */     //   17: getstatic 11	java/awt/PopupMenu:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 11	java/awt/PopupMenu:nameCounter	I
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
/*     */     //   Java source line #105	-> byte code offset #0
/*     */     //   Java source line #106	-> byte code offset #5
/*     */     //   Java source line #107	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	PopupMenu
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   protected class AccessibleAWTPopupMenu
/*     */     extends Menu.AccessibleAWTMenu
/*     */   {
/*     */     private static final long serialVersionUID = -4282044795947239955L;
/*     */     
/*     */     protected AccessibleAWTPopupMenu()
/*     */     {
/* 222 */       super();
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
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 236 */       return AccessibleRole.POPUP_MENU;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/PopupMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */