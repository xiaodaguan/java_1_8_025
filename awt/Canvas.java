/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.BufferStrategy;
/*     */ import java.awt.peer.CanvasPeer;
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
/*     */ public class Canvas
/*     */   extends Component
/*     */   implements Accessible
/*     */ {
/*     */   private static final String base = "canvas";
/*  47 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -2284879212465893870L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Canvas() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Canvas(GraphicsConfiguration paramGraphicsConfiguration)
/*     */   {
/*  68 */     this();
/*  69 */     setGraphicsConfiguration(paramGraphicsConfiguration);
/*     */   }
/*     */   
/*     */   void setGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration)
/*     */   {
/*  74 */     synchronized (getTreeLock()) {
/*  75 */       CanvasPeer localCanvasPeer = (CanvasPeer)getPeer();
/*  76 */       if (localCanvasPeer != null) {
/*  77 */         paramGraphicsConfiguration = localCanvasPeer.getAppropriateGraphicsConfiguration(paramGraphicsConfiguration);
/*     */       }
/*  79 */       super.setGraphicsConfiguration(paramGraphicsConfiguration);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 9
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 10	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 11	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 12
/*     */     //   14: invokevirtual 13	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 14	java/awt/Canvas:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 14	java/awt/Canvas:nameCounter	I
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
/*     */     //   Java source line #88	-> byte code offset #0
/*     */     //   Java source line #89	-> byte code offset #5
/*     */     //   Java source line #90	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	Canvas
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
/* 100 */     synchronized (getTreeLock()) {
/* 101 */       if (this.peer == null)
/* 102 */         this.peer = getToolkit().createCanvas(this);
/* 103 */       super.addNotify();
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
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 122 */     paramGraphics.clearRect(0, 0, this.width, this.height);
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
/*     */   public void update(Graphics paramGraphics)
/*     */   {
/* 141 */     paramGraphics.clearRect(0, 0, this.width, this.height);
/* 142 */     paint(paramGraphics);
/*     */   }
/*     */   
/*     */   boolean postsOldMouseEvents() {
/* 146 */     return true;
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
/*     */   public void createBufferStrategy(int paramInt)
/*     */   {
/* 169 */     super.createBufferStrategy(paramInt);
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
/*     */   public void createBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities)
/*     */     throws AWTException
/*     */   {
/* 194 */     super.createBufferStrategy(paramInt, paramBufferCapabilities);
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
/*     */   public BufferStrategy getBufferStrategy()
/*     */   {
/* 207 */     return super.getBufferStrategy();
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
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 226 */     if (this.accessibleContext == null) {
/* 227 */       this.accessibleContext = new AccessibleAWTCanvas();
/*     */     }
/* 229 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   protected class AccessibleAWTCanvas extends Component.AccessibleAWTComponent
/*     */   {
/*     */     private static final long serialVersionUID = -6325592262103146699L;
/*     */     
/*     */     protected AccessibleAWTCanvas()
/*     */     {
/* 238 */       super();
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
/* 250 */       return AccessibleRole.CANVAS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Canvas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */