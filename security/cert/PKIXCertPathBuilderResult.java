/*     */ package java.security.cert;
/*     */ 
/*     */ import java.security.PublicKey;
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
/*     */ public class PKIXCertPathBuilderResult
/*     */   extends PKIXCertPathValidatorResult
/*     */   implements CertPathBuilderResult
/*     */ {
/*     */   private CertPath certPath;
/*     */   
/*     */   public PKIXCertPathBuilderResult(CertPath paramCertPath, TrustAnchor paramTrustAnchor, PolicyNode paramPolicyNode, PublicKey paramPublicKey)
/*     */   {
/*  82 */     super(paramTrustAnchor, paramPolicyNode, paramPublicKey);
/*  83 */     if (paramCertPath == null)
/*  84 */       throw new NullPointerException("certPath must be non-null");
/*  85 */     this.certPath = paramCertPath;
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
/*     */   public CertPath getCertPath()
/*     */   {
/*  99 */     return this.certPath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 110 */     StringBuffer localStringBuffer = new StringBuffer();
/* 111 */     localStringBuffer.append("PKIXCertPathBuilderResult: [\n");
/* 112 */     localStringBuffer.append("  Certification Path: " + this.certPath + "\n");
/* 113 */     localStringBuffer.append("  Trust Anchor: " + getTrustAnchor().toString() + "\n");
/* 114 */     localStringBuffer.append("  Policy Tree: " + String.valueOf(getPolicyTree()) + "\n");
/* 115 */     localStringBuffer.append("  Subject Public Key: " + getPublicKey() + "\n");
/* 116 */     localStringBuffer.append("]");
/* 117 */     return localStringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/PKIXCertPathBuilderResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */