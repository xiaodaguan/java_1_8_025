/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   public static final int COMPONENT_FIRST = 100;
/*     */   public static final int COMPONENT_LAST = 103;
/*     */   public static final int COMPONENT_MOVED = 100;
/*     */   public static final int COMPONENT_RESIZED = 101;
/*     */   public static final int COMPONENT_SHOWN = 102;
/*     */   public static final int COMPONENT_HIDDEN = 103;
/*     */   private static final long serialVersionUID = 8101406823902992965L;
/*     */   
/*     */   public ComponentEvent(Component paramComponent, int paramInt)
/*     */   {
/* 120 */     super(paramComponent, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Component getComponent()
/*     */   {
/* 131 */     return (this.source instanceof Component) ? (Component)this.source : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String paramString()
/*     */   {
/* 143 */     Object localObject = this.source != null ? ((Component)this.source).getBounds() : null;
/*     */     
/*     */     String str;
/* 146 */     switch (this.id) {
/*     */     case 102: 
/* 148 */       str = "COMPONENT_SHOWN";
/* 149 */       break;
/*     */     case 103: 
/* 151 */       str = "COMPONENT_HIDDEN";
/* 152 */       break;
/*     */     case 100: 
/* 154 */       str = "COMPONENT_MOVED (" + ((Rectangle)localObject).x + "," + ((Rectangle)localObject).y + " " + ((Rectangle)localObject).width + "x" + ((Rectangle)localObject).height + ")";
/*     */       
/* 156 */       break;
/*     */     case 101: 
/* 158 */       str = "COMPONENT_RESIZED (" + ((Rectangle)localObject).x + "," + ((Rectangle)localObject).y + " " + ((Rectangle)localObject).width + "x" + ((Rectangle)localObject).height + ")";
/*     */       
/* 160 */       break;
/*     */     default: 
/* 162 */       str = "unknown type";
/*     */     }
/* 164 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/ComponentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */