/*     */ package java.security.cert;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
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
/*     */ public abstract class CertPathValidatorSpi
/*     */ {
/*     */   public abstract CertPathValidatorResult engineValidate(CertPath paramCertPath, CertPathParameters paramCertPathParameters)
/*     */     throws CertPathValidatorException, InvalidAlgorithmParameterException;
/*     */   
/*     */   public CertPathChecker engineGetRevocationChecker()
/*     */   {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CertPathValidatorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */