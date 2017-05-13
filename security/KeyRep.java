/*     */ package java.security;
/*     */ 
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Locale;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyRep
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4757683898830641853L;
/*     */   private static final String PKCS8 = "PKCS#8";
/*     */   private static final String X509 = "X.509";
/*     */   private static final String RAW = "RAW";
/*     */   private Type type;
/*     */   private String algorithm;
/*     */   private String format;
/*     */   private byte[] encoded;
/*     */   
/*     */   public static enum Type
/*     */   {
/*  70 */     SECRET, 
/*     */     
/*     */ 
/*  73 */     PUBLIC, 
/*     */     
/*     */ 
/*  76 */     PRIVATE;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Type() {}
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
/*     */   public KeyRep(Type paramType, String paramString1, String paramString2, byte[] paramArrayOfByte)
/*     */   {
/* 134 */     if ((paramType == null) || (paramString1 == null) || (paramString2 == null) || (paramArrayOfByte == null))
/*     */     {
/* 136 */       throw new NullPointerException("invalid null input(s)");
/*     */     }
/*     */     
/* 139 */     this.type = paramType;
/* 140 */     this.algorithm = paramString1;
/* 141 */     this.format = paramString2.toUpperCase(Locale.ENGLISH);
/* 142 */     this.encoded = ((byte[])paramArrayOfByte.clone());
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
/*     */   protected Object readResolve()
/*     */     throws ObjectStreamException
/*     */   {
/*     */     try
/*     */     {
/* 171 */       if ((this.type == Type.SECRET) && ("RAW".equals(this.format)))
/* 172 */         return new SecretKeySpec(this.encoded, this.algorithm);
/* 173 */       KeyFactory localKeyFactory; if ((this.type == Type.PUBLIC) && ("X.509".equals(this.format))) {
/* 174 */         localKeyFactory = KeyFactory.getInstance(this.algorithm);
/* 175 */         return localKeyFactory.generatePublic(new X509EncodedKeySpec(this.encoded)); }
/* 176 */       if ((this.type == Type.PRIVATE) && ("PKCS#8".equals(this.format))) {
/* 177 */         localKeyFactory = KeyFactory.getInstance(this.algorithm);
/* 178 */         return localKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(this.encoded));
/*     */       }
/* 180 */       throw new NotSerializableException("unrecognized type/format combination: " + this.type + "/" + this.format);
/*     */ 
/*     */     }
/*     */     catch (NotSerializableException localNotSerializableException1)
/*     */     {
/* 185 */       throw localNotSerializableException1;
/*     */     } catch (Exception localException) {
/* 187 */       NotSerializableException localNotSerializableException2 = new NotSerializableException("java.security.Key: [" + this.type + "] " + "[" + this.algorithm + "] " + "[" + this.format + "]");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 192 */       localNotSerializableException2.initCause(localException);
/* 193 */       throw localNotSerializableException2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/KeyRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */