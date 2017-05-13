/*     */ package java.awt.dnd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragSourceDropEvent
/*     */   extends DragSourceEvent
/*     */ {
/*     */   private static final long serialVersionUID = -5571321229470821891L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean dropSuccess;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DragSourceDropEvent(DragSourceContext paramDragSourceContext, int paramInt, boolean paramBoolean)
/*     */   {
/*  71 */     super(paramDragSourceContext);
/*     */     
/*  73 */     this.dropSuccess = paramBoolean;
/*  74 */     this.dropAction = paramInt;
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
/*     */   public DragSourceDropEvent(DragSourceContext paramDragSourceContext, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3)
/*     */   {
/*  99 */     super(paramDragSourceContext, paramInt2, paramInt3);
/*     */     
/* 101 */     this.dropSuccess = paramBoolean;
/* 102 */     this.dropAction = paramInt1;
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
/*     */   public DragSourceDropEvent(DragSourceContext paramDragSourceContext)
/*     */   {
/* 120 */     super(paramDragSourceContext);
/*     */     
/* 122 */     this.dropSuccess = false;
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
/*     */   public boolean getDropSuccess()
/*     */   {
/* 136 */     return this.dropSuccess;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDropAction()
/*     */   {
/* 148 */     return this.dropAction;
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
/* 166 */   private int dropAction = 0;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DragSourceDropEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */