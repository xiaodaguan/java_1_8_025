/*     */ package java.security.cert;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PKIXRevocationChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*     */   private URI ocspResponder;
/*     */   private X509Certificate ocspResponderCert;
/* 102 */   private List<Extension> ocspExtensions = Collections.emptyList();
/* 103 */   private Map<X509Certificate, byte[]> ocspResponses = Collections.emptyMap();
/* 104 */   private Set<Option> options = Collections.emptySet();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOcspResponder(URI paramURI)
/*     */   {
/* 120 */     this.ocspResponder = paramURI;
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
/*     */   public URI getOcspResponder()
/*     */   {
/* 133 */     return this.ocspResponder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOcspResponderCert(X509Certificate paramX509Certificate)
/*     */   {
/* 145 */     this.ocspResponderCert = paramX509Certificate;
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
/*     */   public X509Certificate getOcspResponderCert()
/*     */   {
/* 159 */     return this.ocspResponderCert;
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
/*     */   public void setOcspExtensions(List<Extension> paramList)
/*     */   {
/* 172 */     this.ocspExtensions = (paramList == null ? Collections.emptyList() : new ArrayList(paramList));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Extension> getOcspExtensions()
/*     */   {
/* 183 */     return Collections.unmodifiableList(this.ocspExtensions);
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
/*     */   public void setOcspResponses(Map<X509Certificate, byte[]> paramMap)
/*     */   {
/* 197 */     if (paramMap == null) {
/* 198 */       this.ocspResponses = Collections.emptyMap();
/*     */     } else {
/* 200 */       HashMap localHashMap = new HashMap(paramMap.size());
/* 201 */       for (Map.Entry localEntry : paramMap.entrySet()) {
/* 202 */         localHashMap.put(localEntry.getKey(), ((byte[])localEntry.getValue()).clone());
/*     */       }
/* 204 */       this.ocspResponses = localHashMap;
/*     */     }
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
/*     */   public Map<X509Certificate, byte[]> getOcspResponses()
/*     */   {
/* 219 */     HashMap localHashMap = new HashMap(this.ocspResponses.size());
/* 220 */     for (Map.Entry localEntry : this.ocspResponses.entrySet()) {
/* 221 */       localHashMap.put(localEntry.getKey(), ((byte[])localEntry.getValue()).clone());
/*     */     }
/* 223 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOptions(Set<Option> paramSet)
/*     */   {
/* 234 */     this.options = (paramSet == null ? Collections.emptySet() : new HashSet(paramSet));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<Option> getOptions()
/*     */   {
/* 245 */     return Collections.unmodifiableSet(this.options);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<CertPathValidatorException> getSoftFailExceptions();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PKIXRevocationChecker clone()
/*     */   {
/* 266 */     PKIXRevocationChecker localPKIXRevocationChecker = (PKIXRevocationChecker)super.clone();
/* 267 */     localPKIXRevocationChecker.ocspExtensions = new ArrayList(this.ocspExtensions);
/* 268 */     localPKIXRevocationChecker.ocspResponses = new HashMap(this.ocspResponses);
/*     */     
/*     */ 
/* 271 */     for (Map.Entry localEntry : localPKIXRevocationChecker.ocspResponses.entrySet())
/*     */     {
/* 273 */       byte[] arrayOfByte = (byte[])localEntry.getValue();
/* 274 */       localEntry.setValue(arrayOfByte.clone());
/*     */     }
/* 276 */     localPKIXRevocationChecker.options = new HashSet(this.options);
/* 277 */     return localPKIXRevocationChecker;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Option
/*     */   {
/* 288 */     ONLY_END_ENTITY, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 294 */     PREFER_CRLS, 
/*     */     
/*     */ 
/*     */ 
/* 298 */     NO_FALLBACK, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 316 */     SOFT_FAIL;
/*     */     
/*     */     private Option() {}
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/PKIXRevocationChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */