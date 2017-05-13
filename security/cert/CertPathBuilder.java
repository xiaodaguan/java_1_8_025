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
/*     */ public class CertPathBuilder
/*     */ {
/*     */   private static final String CPB_TYPE = "certpathbuilder.type";
/*     */   private final CertPathBuilderSpi builderSpi;
/*     */   private final Provider provider;
/*     */   private final String algorithm;
/*     */   
/*     */   protected CertPathBuilder(CertPathBuilderSpi paramCertPathBuilderSpi, Provider paramProvider, String paramString)
/*     */   {
/* 127 */     this.builderSpi = paramCertPathBuilderSpi;
/* 128 */     this.provider = paramProvider;
/* 129 */     this.algorithm = paramString;
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
/*     */   public static CertPathBuilder getInstance(String paramString)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 162 */     GetInstance.Instance localInstance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, paramString);
/*     */     
/* 164 */     return new CertPathBuilder((CertPathBuilderSpi)localInstance.impl, localInstance.provider, paramString);
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
/*     */   public static CertPathBuilder getInstance(String paramString1, String paramString2)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 205 */     GetInstance.Instance localInstance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, paramString1, paramString2);
/*     */     
/* 207 */     return new CertPathBuilder((CertPathBuilderSpi)localInstance.impl, localInstance.provider, paramString1);
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
/*     */   public static CertPathBuilder getInstance(String paramString, Provider paramProvider)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 242 */     GetInstance.Instance localInstance = GetInstance.getInstance("CertPathBuilder", CertPathBuilderSpi.class, paramString, paramProvider);
/*     */     
/* 244 */     return new CertPathBuilder((CertPathBuilderSpi)localInstance.impl, localInstance.provider, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Provider getProvider()
/*     */   {
/* 254 */     return this.provider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getAlgorithm()
/*     */   {
/* 263 */     return this.algorithm;
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
/*     */   public final CertPathBuilderResult build(CertPathParameters paramCertPathParameters)
/*     */     throws CertPathBuilderException, InvalidAlgorithmParameterException
/*     */   {
/* 280 */     return this.builderSpi.engineBuild(paramCertPathParameters);
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
/* 304 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public String run() {
/* 306 */         return Security.getProperty("certpathbuilder.type");
/*     */       }
/* 308 */     });
/* 309 */     return str == null ? "PKIX" : str;
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
/* 329 */     return this.builderSpi.engineGetRevocationChecker();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CertPathBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */