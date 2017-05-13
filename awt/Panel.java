/*     */ package java.awt;
/*     */ 
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
/*     */ public class Panel
/*     */   extends Container
/*     */   implements Accessible
/*     */ {
/*     */   private static final String base = "panel";
/*  43 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -2728009084054400034L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Panel()
/*     */   {
/*  56 */     this(new FlowLayout());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Panel(LayoutManager paramLayoutManager)
/*     */   {
/*  65 */     setLayout(paramLayoutManager);
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
/*     */     //   17: getstatic 11	java/awt/Panel:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 11	java/awt/Panel:nameCounter	I
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
/*     */     //   Java source line #73	-> byte code offset #0
/*     */     //   Java source line #74	-> byte code offset #5
/*     */     //   Java source line #75	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	Panel
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   public void addNotify()
/*     */   {
/*  84 */     synchronized (getTreeLock()) {
/*  85 */       if (this.peer == null)
/*  86 */         this.peer = getToolkit().createPanel(this);
/*  87 */       super.addNotify();
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
/* 106 */     if (this.accessibleContext == null) {
/* 107 */       this.accessibleContext = new AccessibleAWTPanel();
/*     */     }
/* 109 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   protected class AccessibleAWTPanel extends Container.AccessibleAWTContainer
/*     */   {
/*     */     private static final long serialVersionUID = -6409552226660031050L;
/*     */     
/*     */     protected AccessibleAWTPanel()
/*     */     {
/* 118 */       super();
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
/* 129 */       return AccessibleRole.PANEL;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Panel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */