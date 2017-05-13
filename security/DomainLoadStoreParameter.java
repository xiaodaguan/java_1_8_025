/*     */ package java.security;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DomainLoadStoreParameter
/*     */   implements KeyStore.LoadStoreParameter
/*     */ {
/*     */   private final URI configuration;
/*     */   private final Map<String, KeyStore.ProtectionParameter> protectionParams;
/*     */   
/*     */   public DomainLoadStoreParameter(URI paramURI, Map<String, KeyStore.ProtectionParameter> paramMap)
/*     */   {
/* 133 */     if ((paramURI == null) || (paramMap == null)) {
/* 134 */       throw new NullPointerException("invalid null input");
/*     */     }
/* 136 */     this.configuration = paramURI;
/*     */     
/* 138 */     this.protectionParams = Collections.unmodifiableMap(new HashMap(paramMap));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public URI getConfiguration()
/*     */   {
/* 147 */     return this.configuration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, KeyStore.ProtectionParameter> getProtectionParams()
/*     */   {
/* 158 */     return this.protectionParams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public KeyStore.ProtectionParameter getProtectionParameter()
/*     */   {
/* 169 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/DomainLoadStoreParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */