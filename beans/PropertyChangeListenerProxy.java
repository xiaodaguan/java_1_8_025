/*    */ package java.beans;
/*    */ 
/*    */ import java.util.EventListenerProxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertyChangeListenerProxy
/*    */   extends EventListenerProxy<PropertyChangeListener>
/*    */   implements PropertyChangeListener
/*    */ {
/*    */   private final String propertyName;
/*    */   
/*    */   public PropertyChangeListenerProxy(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*    */   {
/* 60 */     super(paramPropertyChangeListener);
/* 61 */     this.propertyName = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
/*    */   {
/* 70 */     ((PropertyChangeListener)getListener()).propertyChange(paramPropertyChangeEvent);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getPropertyName()
/*    */   {
/* 79 */     return this.propertyName;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/PropertyChangeListenerProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */