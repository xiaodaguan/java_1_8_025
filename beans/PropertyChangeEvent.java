/*     */ package java.beans;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyChangeEvent
/*     */   extends EventObject
/*     */ {
/*     */   private static final long serialVersionUID = 7042693688939648123L;
/*     */   private String propertyName;
/*     */   private Object newValue;
/*     */   private Object oldValue;
/*     */   private Object propagationId;
/*     */   
/*     */   public PropertyChangeEvent(Object paramObject1, String paramString, Object paramObject2, Object paramObject3)
/*     */   {
/*  62 */     super(paramObject1);
/*  63 */     this.propertyName = paramString;
/*  64 */     this.newValue = paramObject3;
/*  65 */     this.oldValue = paramObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPropertyName()
/*     */   {
/*  75 */     return this.propertyName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getNewValue()
/*     */   {
/*  85 */     return this.newValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getOldValue()
/*     */   {
/*  95 */     return this.oldValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPropagationId(Object paramObject)
/*     */   {
/* 104 */     this.propagationId = paramObject;
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
/*     */   public Object getPropagationId()
/*     */   {
/* 118 */     return this.propagationId;
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
/*     */   public String toString()
/*     */   {
/* 154 */     StringBuilder localStringBuilder = new StringBuilder(getClass().getName());
/* 155 */     localStringBuilder.append("[propertyName=").append(getPropertyName());
/* 156 */     appendTo(localStringBuilder);
/* 157 */     localStringBuilder.append("; oldValue=").append(getOldValue());
/* 158 */     localStringBuilder.append("; newValue=").append(getNewValue());
/* 159 */     localStringBuilder.append("; propagationId=").append(getPropagationId());
/* 160 */     localStringBuilder.append("; source=").append(getSource());
/* 161 */     return "]";
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/PropertyChangeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */