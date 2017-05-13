/*    */ package java.security.spec;
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
/*    */ public abstract class EncodedKeySpec
/*    */   implements KeySpec
/*    */ {
/*    */   private byte[] encodedKey;
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
/*    */   public EncodedKeySpec(byte[] paramArrayOfByte)
/*    */   {
/* 56 */     this.encodedKey = ((byte[])paramArrayOfByte.clone());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getEncoded()
/*    */   {
/* 66 */     return (byte[])this.encodedKey.clone();
/*    */   }
/*    */   
/*    */   public abstract String getFormat();
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/spec/EncodedKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */