/*    */ package java.awt.event;
/*    */ 
/*    */ import java.awt.AWTEvent;
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
/*    */ public class AWTEventListenerProxy
/*    */   extends EventListenerProxy<AWTEventListener>
/*    */   implements AWTEventListener
/*    */ {
/*    */   private final long eventMask;
/*    */   
/*    */   public AWTEventListenerProxy(long paramLong, AWTEventListener paramAWTEventListener)
/*    */   {
/* 60 */     super(paramAWTEventListener);
/* 61 */     this.eventMask = paramLong;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void eventDispatched(AWTEvent paramAWTEvent)
/*    */   {
/* 70 */     ((AWTEventListener)getListener()).eventDispatched(paramAWTEvent);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getEventMask()
/*    */   {
/* 79 */     return this.eventMask;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/AWTEventListenerProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */