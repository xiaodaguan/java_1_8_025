/*    */ package java.text;
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
/*    */ final class EntryPair
/*    */ {
/*    */   public String entryName;
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
/*    */   public int value;
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
/*    */   public boolean fwd;
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
/* 53 */   public EntryPair(String paramString, int paramInt) { this(paramString, paramInt, true); }
/*    */   
/*    */   public EntryPair(String paramString, int paramInt, boolean paramBoolean) {
/* 56 */     this.entryName = paramString;
/* 57 */     this.value = paramInt;
/* 58 */     this.fwd = paramBoolean;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/EntryPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */