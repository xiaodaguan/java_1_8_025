/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.Window;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowEvent
/*     */   extends ComponentEvent
/*     */ {
/*     */   public static final int WINDOW_FIRST = 200;
/*     */   public static final int WINDOW_OPENED = 200;
/*     */   public static final int WINDOW_CLOSING = 201;
/*     */   public static final int WINDOW_CLOSED = 202;
/*     */   public static final int WINDOW_ICONIFIED = 203;
/*     */   public static final int WINDOW_DEICONIFIED = 204;
/*     */   public static final int WINDOW_ACTIVATED = 205;
/*     */   public static final int WINDOW_DEACTIVATED = 206;
/*     */   public static final int WINDOW_GAINED_FOCUS = 207;
/*     */   public static final int WINDOW_LOST_FOCUS = 208;
/*     */   public static final int WINDOW_STATE_CHANGED = 209;
/*     */   public static final int WINDOW_LAST = 209;
/*     */   transient Window opposite;
/*     */   int oldState;
/*     */   int newState;
/*     */   private static final long serialVersionUID = -1567959133147912127L;
/*     */   
/*     */   public WindowEvent(Window paramWindow1, int paramInt1, Window paramWindow2, int paramInt2, int paramInt3)
/*     */   {
/* 206 */     super(paramWindow1, paramInt1);
/* 207 */     this.opposite = paramWindow2;
/* 208 */     this.oldState = paramInt2;
/* 209 */     this.newState = paramInt3;
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
/*     */ 
/*     */ 
/*     */   public WindowEvent(Window paramWindow1, int paramInt, Window paramWindow2)
/*     */   {
/* 251 */     this(paramWindow1, paramInt, paramWindow2, 0, 0);
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
/*     */   public WindowEvent(Window paramWindow, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 284 */     this(paramWindow, paramInt1, null, paramInt2, paramInt3);
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
/*     */   public WindowEvent(Window paramWindow, int paramInt)
/*     */   {
/* 302 */     this(paramWindow, paramInt, null, 0, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Window getWindow()
/*     */   {
/* 311 */     return (this.source instanceof Window) ? (Window)this.source : null;
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
/*     */   public Window getOppositeWindow()
/*     */   {
/* 328 */     if (this.opposite == null) {
/* 329 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 333 */     return SunToolkit.targetToAppContext(this.opposite) == AppContext.getAppContext() ? this.opposite : null;
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
/*     */   public int getOldState()
/*     */   {
/* 358 */     return this.oldState;
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
/*     */   public int getNewState()
/*     */   {
/* 381 */     return this.newState;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String paramString()
/*     */   {
/* 392 */     switch (this.id) {
/*     */     case 200: 
/* 394 */       str = "WINDOW_OPENED";
/* 395 */       break;
/*     */     case 201: 
/* 397 */       str = "WINDOW_CLOSING";
/* 398 */       break;
/*     */     case 202: 
/* 400 */       str = "WINDOW_CLOSED";
/* 401 */       break;
/*     */     case 203: 
/* 403 */       str = "WINDOW_ICONIFIED";
/* 404 */       break;
/*     */     case 204: 
/* 406 */       str = "WINDOW_DEICONIFIED";
/* 407 */       break;
/*     */     case 205: 
/* 409 */       str = "WINDOW_ACTIVATED";
/* 410 */       break;
/*     */     case 206: 
/* 412 */       str = "WINDOW_DEACTIVATED";
/* 413 */       break;
/*     */     case 207: 
/* 415 */       str = "WINDOW_GAINED_FOCUS";
/* 416 */       break;
/*     */     case 208: 
/* 418 */       str = "WINDOW_LOST_FOCUS";
/* 419 */       break;
/*     */     case 209: 
/* 421 */       str = "WINDOW_STATE_CHANGED";
/* 422 */       break;
/*     */     default: 
/* 424 */       str = "unknown type";
/*     */     }
/* 426 */     String str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState;
/*     */     
/*     */ 
/* 429 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/WindowEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */