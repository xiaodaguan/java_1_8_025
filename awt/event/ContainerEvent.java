/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerEvent
/*     */   extends ComponentEvent
/*     */ {
/*     */   public static final int CONTAINER_FIRST = 300;
/*     */   public static final int CONTAINER_LAST = 301;
/*     */   public static final int COMPONENT_ADDED = 300;
/*     */   public static final int COMPONENT_REMOVED = 301;
/*     */   Component child;
/*     */   private static final long serialVersionUID = -4114942250539772041L;
/*     */   
/*     */   public ContainerEvent(Component paramComponent1, int paramInt, Component paramComponent2)
/*     */   {
/* 115 */     super(paramComponent1, paramInt);
/* 116 */     this.child = paramComponent2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Container getContainer()
/*     */   {
/* 127 */     return (this.source instanceof Container) ? (Container)this.source : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Component getChild()
/*     */   {
/* 136 */     return this.child;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String paramString()
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/* 147 */     switch (this.id) {
/*     */     case 300: 
/* 149 */       str = "COMPONENT_ADDED";
/* 150 */       break;
/*     */     case 301: 
/* 152 */       str = "COMPONENT_REMOVED";
/* 153 */       break;
/*     */     default: 
/* 155 */       str = "unknown type";
/*     */     }
/* 157 */     return str + ",child=" + this.child.getName();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/ContainerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */