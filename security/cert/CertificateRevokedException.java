/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ import sun.security.x509.InvalidityDateExtension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateRevokedException
/*     */   extends CertificateException
/*     */ {
/*     */   private static final long serialVersionUID = 7839996631571608627L;
/*     */   private Date revocationDate;
/*     */   private final CRLReason reason;
/*     */   private final X500Principal authority;
/*     */   private transient Map<String, Extension> extensions;
/*     */   
/*     */   public CertificateRevokedException(Date paramDate, CRLReason paramCRLReason, X500Principal paramX500Principal, Map<String, Extension> paramMap)
/*     */   {
/*  90 */     if ((paramDate == null) || (paramCRLReason == null) || (paramX500Principal == null) || (paramMap == null))
/*     */     {
/*  92 */       throw new NullPointerException();
/*     */     }
/*  94 */     this.revocationDate = new Date(paramDate.getTime());
/*  95 */     this.reason = paramCRLReason;
/*  96 */     this.authority = paramX500Principal;
/*     */     
/*  98 */     this.extensions = Collections.checkedMap(new HashMap(), String.class, Extension.class);
/*     */     
/* 100 */     this.extensions.putAll(paramMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date getRevocationDate()
/*     */   {
/* 111 */     return (Date)this.revocationDate.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CRLReason getRevocationReason()
/*     */   {
/* 120 */     return this.reason;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public X500Principal getAuthorityName()
/*     */   {
/* 131 */     return this.authority;
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
/*     */   public Date getInvalidityDate()
/*     */   {
/* 148 */     Extension localExtension = (Extension)getExtensions().get("2.5.29.24");
/* 149 */     if (localExtension == null) {
/* 150 */       return null;
/*     */     }
/*     */     try {
/* 153 */       Date localDate = InvalidityDateExtension.toImpl(localExtension).get("DATE");
/* 154 */       return new Date(localDate.getTime());
/*     */     } catch (IOException localIOException) {}
/* 156 */     return null;
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
/*     */   public Map<String, Extension> getExtensions()
/*     */   {
/* 171 */     return Collections.unmodifiableMap(this.extensions);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 179 */     return "Certificate has been revoked, reason: " + this.reason + ", revocation date: " + this.revocationDate + ", authority: " + this.authority + ", extension OIDs: " + this.extensions.keySet();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 194 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 197 */     paramObjectOutputStream.writeInt(this.extensions.size());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 204 */     for (Map.Entry localEntry : this.extensions.entrySet()) {
/* 205 */       Extension localExtension = (Extension)localEntry.getValue();
/* 206 */       paramObjectOutputStream.writeObject(localExtension.getId());
/* 207 */       paramObjectOutputStream.writeBoolean(localExtension.isCritical());
/* 208 */       byte[] arrayOfByte = localExtension.getValue();
/* 209 */       paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 210 */       paramObjectOutputStream.write(arrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 221 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 224 */     this.revocationDate = new Date(this.revocationDate.getTime());
/*     */     
/*     */ 
/*     */ 
/* 228 */     int i = paramObjectInputStream.readInt();
/* 229 */     if (i == 0) {
/* 230 */       this.extensions = Collections.emptyMap();
/*     */     } else {
/* 232 */       this.extensions = new HashMap(i);
/*     */     }
/*     */     
/*     */ 
/* 236 */     for (int j = 0; j < i; j++) {
/* 237 */       String str = (String)paramObjectInputStream.readObject();
/* 238 */       boolean bool = paramObjectInputStream.readBoolean();
/* 239 */       int k = paramObjectInputStream.readInt();
/* 240 */       byte[] arrayOfByte = new byte[k];
/* 241 */       paramObjectInputStream.readFully(arrayOfByte);
/*     */       
/* 243 */       sun.security.x509.Extension localExtension = sun.security.x509.Extension.newExtension(new ObjectIdentifier(str), bool, arrayOfByte);
/* 244 */       this.extensions.put(str, localExtension);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/CertificateRevokedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */