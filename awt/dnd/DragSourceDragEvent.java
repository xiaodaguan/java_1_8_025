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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragSourceDragEvent
/*     */   extends DragSourceEvent
/*     */ {
/*     */   private static final long serialVersionUID = 481346297933902471L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int JDK_1_3_MODIFIERS = 63;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int JDK_1_4_MODIFIERS = 16320;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DragSourceDragEvent(DragSourceContext paramDragSourceContext, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 114 */     super(paramDragSourceContext);
/*     */     
/* 116 */     this.targetActions = paramInt2;
/* 117 */     this.gestureModifiers = paramInt3;
/* 118 */     this.dropAction = paramInt1;
/* 119 */     if ((paramInt3 & 0xC000) != 0) {
/* 120 */       this.invalidModifiers = true;
/* 121 */     } else if ((getGestureModifiers() != 0) && (getGestureModifiersEx() == 0)) {
/* 122 */       setNewModifiers();
/* 123 */     } else if ((getGestureModifiers() == 0) && (getGestureModifiersEx() != 0)) {
/* 124 */       setOldModifiers();
/*     */     } else {
/* 126 */       this.invalidModifiers = true;
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
/*     */   public DragSourceDragEvent(DragSourceContext paramDragSourceContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 164 */     super(paramDragSourceContext, paramInt4, paramInt5);
/*     */     
/* 166 */     this.targetActions = paramInt2;
/* 167 */     this.gestureModifiers = paramInt3;
/* 168 */     this.dropAction = paramInt1;
/* 169 */     if ((paramInt3 & 0xC000) != 0) {
/* 170 */       this.invalidModifiers = true;
/* 171 */     } else if ((getGestureModifiers() != 0) && (getGestureModifiersEx() == 0)) {
/* 172 */       setNewModifiers();
/* 173 */     } else if ((getGestureModifiers() == 0) && (getGestureModifiersEx() != 0)) {
/* 174 */       setOldModifiers();
/*     */     } else {
/* 176 */       this.invalidModifiers = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTargetActions()
/*     */   {
/* 186 */     return this.targetActions;
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
/*     */   public int getGestureModifiers()
/*     */   {
/* 207 */     return this.invalidModifiers ? this.gestureModifiers : this.gestureModifiers & 0x3F;
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
/*     */   public int getGestureModifiersEx()
/*     */   {
/* 224 */     return this.invalidModifiers ? this.gestureModifiers : this.gestureModifiers & 0x3FC0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getUserAction()
/*     */   {
/* 232 */     return this.dropAction;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDropAction()
/*     */   {
/* 243 */     return this.targetActions & getDragSourceContext().getSourceActions();
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
/* 255 */   private int targetActions = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 262 */   private int dropAction = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 270 */   private int gestureModifiers = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean invalidModifiers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setNewModifiers()
/*     */   {
/* 285 */     if ((this.gestureModifiers & 0x10) != 0) {
/* 286 */       this.gestureModifiers |= 0x400;
/*     */     }
/* 288 */     if ((this.gestureModifiers & 0x8) != 0) {
/* 289 */       this.gestureModifiers |= 0x800;
/*     */     }
/* 291 */     if ((this.gestureModifiers & 0x4) != 0) {
/* 292 */       this.gestureModifiers |= 0x1000;
/*     */     }
/* 294 */     if ((this.gestureModifiers & 0x1) != 0) {
/* 295 */       this.gestureModifiers |= 0x40;
/*     */     }
/* 297 */     if ((this.gestureModifiers & 0x2) != 0) {
/* 298 */       this.gestureModifiers |= 0x80;
/*     */     }
/* 300 */     if ((this.gestureModifiers & 0x20) != 0) {
/* 301 */       this.gestureModifiers |= 0x2000;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setOldModifiers()
/*     */   {
/* 309 */     if ((this.gestureModifiers & 0x400) != 0) {
/* 310 */       this.gestureModifiers |= 0x10;
/*     */     }
/* 312 */     if ((this.gestureModifiers & 0x800) != 0) {
/* 313 */       this.gestureModifiers |= 0x8;
/*     */     }
/* 315 */     if ((this.gestureModifiers & 0x1000) != 0) {
/* 316 */       this.gestureModifiers |= 0x4;
/*     */     }
/* 318 */     if ((this.gestureModifiers & 0x40) != 0) {
/* 319 */       this.gestureModifiers |= 0x1;
/*     */     }
/* 321 */     if ((this.gestureModifiers & 0x80) != 0) {
/* 322 */       this.gestureModifiers |= 0x2;
/*     */     }
/* 324 */     if ((this.gestureModifiers & 0x2000) != 0) {
/* 325 */       this.gestureModifiers |= 0x20;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DragSourceDragEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */