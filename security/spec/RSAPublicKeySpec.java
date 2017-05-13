/*    */ package java.security.spec;
/*    */ 
/*    */ import java.math.BigInteger;
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
/*    */ public class RSAPublicKeySpec
/*    */   implements KeySpec
/*    */ {
/*    */   private BigInteger modulus;
/*    */   private BigInteger publicExponent;
/*    */   
/*    */   public RSAPublicKeySpec(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*    */   {
/* 56 */     this.modulus = paramBigInteger1;
/* 57 */     this.publicExponent = paramBigInteger2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BigInteger getModulus()
/*    */   {
/* 66 */     return this.modulus;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BigInteger getPublicExponent()
/*    */   {
/* 75 */     return this.publicExponent;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/spec/RSAPublicKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */