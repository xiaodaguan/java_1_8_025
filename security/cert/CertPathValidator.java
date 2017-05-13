/*     */ package java.security.cert;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.jca.GetInstance.Instance;
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
/*     */ public class CertPathValidator
/*     */ {
/*     */   private static final String CPV_TYPE = "certpathvalidator.type";
/*     */   private final CertPathValidatorSpi validatorSpi;
/*     */   private final Provider provider;
/*     */   private final String algorithm;
/*     */   
/*     */   protected CertPathValidator(CertPathValidatorSpi paramCertPathValidatorSpi, Provider paramProvider, String paramString)
/*     */   {
/* 128 */     this.validatorSpi = paramCertPathValidatorSpi;
/* 129 */     this.provider = paramProvider;
/* 130 */     this.algorithm = paramString;
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
/*     */   public static CertPathValidator getInstance(String paramString)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 163 */     GetInstance.Instance localInstance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, paramString);
/*     */     
/* 165 */     return new CertPathValidator((CertPathValidatorSpi)localInstance.impl, localInstance.provider, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CertPathValidator getInstance(String paramString1, String paramString2)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 207 */     GetInstance.Instance localInstance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, paramString1, paramString2);
/*     */     
/* 209 */     return new CertPathValidator((CertPathValidatorSpi)localInstance.impl, localInstance.provider, paramString1);
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
/*     */   public static CertPathValidator getInstance(String paramString, Provider paramProvider)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 244 */     GetInstance.Instance localInstance = GetInstance.getInstance("CertPathValidator", CertPathValidatorSpi.class, paramString, paramProvider);
/*     */     
/* 246 */     return new CertPathValidator((CertPathValidatorSpi)localInstance.impl, localInstance.provider, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Provider getProvider()
/*     */   {
/* 257 */     return this.provider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getAlgorithm()
/*     */   {
/* 266 */     return this.algorithm;
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
/*     */   public final CertPathValidatorResult validate(CertPath paramCertPath, CertPathParameters paramCertPathParameters)
/*     */     throws CertPathValidatorException, InvalidAlgorithmParameterException
/*     */   {
/* 292 */     return this.validatorSpi.engineValidate(paramCertPath, paramCertPathParameters);
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
/*     */   public static final String getDefaultType()
/*     */   {
/* 316 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public String run() {
/* 318 */         return Security.getProperty("certpathvalidator.type");
/*     */       }
/* 320 */     });
/* 321 */     return str == null ? "PKIX" : str;
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
/*     */   public final CertPathChecker getRevocationChecker()
/*     */   {
/* 341 */     return this.validatorSpi.engineGetRevocationChecker();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CertPathValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */