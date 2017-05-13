/*    */ package java.awt;
/*    */ 
/*    */ import sun.util.logging.PlatformLogger;
/*    */ import sun.util.logging.PlatformLogger.Level;
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
/*    */ abstract class AttributeValue
/*    */ {
/* 31 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.AttributeValue");
/*    */   private final int value;
/*    */   private final String[] names;
/*    */   
/*    */   protected AttributeValue(int paramInt, String[] paramArrayOfString) {
/* 36 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 37 */       log.finest("value = " + paramInt + ", names = " + paramArrayOfString);
/*    */     }
/*    */     
/* 40 */     if ((log.isLoggable(PlatformLogger.Level.FINER)) && (
/* 41 */       (paramInt < 0) || (paramArrayOfString == null) || (paramInt >= paramArrayOfString.length))) {
/* 42 */       log.finer("Assertion failed");
/*    */     }
/*    */     
/* 45 */     this.value = paramInt;
/* 46 */     this.names = paramArrayOfString;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 51 */     return this.value;
/*    */   }
/*    */   
/* 54 */   public String toString() { return this.names[this.value]; }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/AttributeValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */