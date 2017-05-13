/*    */ package java.sql;
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
/*    */ public class DriverPropertyInfo
/*    */ {
/*    */   public String name;
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
/*    */   public DriverPropertyInfo(String paramString1, String paramString2)
/*    */   {
/* 48 */     this.name = paramString1;
/* 49 */     this.value = paramString2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 60 */   public String description = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 67 */   public boolean required = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 76 */   public String value = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 83 */   public String[] choices = null;
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/DriverPropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */