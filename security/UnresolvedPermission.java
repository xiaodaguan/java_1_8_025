/*     */ package java.security;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnresolvedPermission
/*     */   extends Permission
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4821973115467008846L;
/* 109 */   private static final Debug debug = Debug.getInstance("policy,access", "UnresolvedPermission");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String type;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String actions;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient Certificate[] certs;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UnresolvedPermission(String paramString1, String paramString2, String paramString3, Certificate[] paramArrayOfCertificate)
/*     */   {
/* 157 */     super(paramString1);
/*     */     
/* 159 */     if (paramString1 == null) {
/* 160 */       throw new NullPointerException("type can't be null");
/*     */     }
/* 162 */     this.type = paramString1;
/* 163 */     this.name = paramString2;
/* 164 */     this.actions = paramString3;
/* 165 */     if (paramArrayOfCertificate != null)
/*     */     {
/* 167 */       for (int i = 0; i < paramArrayOfCertificate.length; i++) {
/* 168 */         if (!(paramArrayOfCertificate[i] instanceof X509Certificate))
/*     */         {
/*     */ 
/* 171 */           this.certs = ((Certificate[])paramArrayOfCertificate.clone());
/* 172 */           break;
/*     */         }
/*     */       }
/*     */       
/* 176 */       if (this.certs == null)
/*     */       {
/*     */ 
/* 179 */         i = 0;
/* 180 */         int j = 0;
/* 181 */         while (i < paramArrayOfCertificate.length) {
/* 182 */           j++;
/* 183 */           while ((i + 1 < paramArrayOfCertificate.length) && 
/* 184 */             (((X509Certificate)paramArrayOfCertificate[i]).getIssuerDN().equals(((X509Certificate)paramArrayOfCertificate[(i + 1)])
/* 185 */             .getSubjectDN()))) {
/* 186 */             i++;
/*     */           }
/* 188 */           i++;
/*     */         }
/* 190 */         if (j == paramArrayOfCertificate.length)
/*     */         {
/*     */ 
/* 193 */           this.certs = ((Certificate[])paramArrayOfCertificate.clone());
/*     */         }
/*     */         
/* 196 */         if (this.certs == null)
/*     */         {
/* 198 */           ArrayList localArrayList = new ArrayList();
/*     */           
/* 200 */           i = 0;
/* 201 */           while (i < paramArrayOfCertificate.length) {
/* 202 */             localArrayList.add(paramArrayOfCertificate[i]);
/* 203 */             while ((i + 1 < paramArrayOfCertificate.length) && 
/* 204 */               (((X509Certificate)paramArrayOfCertificate[i]).getIssuerDN().equals(((X509Certificate)paramArrayOfCertificate[(i + 1)])
/* 205 */               .getSubjectDN()))) {
/* 206 */               i++;
/*     */             }
/* 208 */             i++;
/*     */           }
/*     */           
/* 211 */           this.certs = new Certificate[localArrayList.size()];
/* 212 */           localArrayList.toArray(this.certs);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 219 */   private static final Class[] PARAMS0 = new Class[0];
/* 220 */   private static final Class[] PARAMS1 = { String.class };
/* 221 */   private static final Class[] PARAMS2 = { String.class, String.class };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   Permission resolve(Permission paramPermission, Certificate[] paramArrayOfCertificate)
/*     */   {
/* 228 */     if (this.certs != null)
/*     */     {
/* 230 */       if (paramArrayOfCertificate == null) {
/* 231 */         return null;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 236 */       for (int j = 0; j < this.certs.length; j++) {
/* 237 */         int i = 0;
/* 238 */         for (int k = 0; k < paramArrayOfCertificate.length; k++) {
/* 239 */           if (this.certs[j].equals(paramArrayOfCertificate[k])) {
/* 240 */             i = 1;
/* 241 */             break;
/*     */           }
/*     */         }
/* 244 */         if (i == 0) return null;
/*     */       }
/*     */     }
/*     */     try {
/* 248 */       Class localClass = paramPermission.getClass();
/*     */       
/* 250 */       if ((this.name == null) && (this.actions == null)) {
/*     */         try {
/* 252 */           Constructor localConstructor1 = localClass.getConstructor(PARAMS0);
/* 253 */           return (Permission)localConstructor1.newInstance(new Object[0]);
/*     */         } catch (NoSuchMethodException localNoSuchMethodException2) {
/*     */           try {
/* 256 */             Constructor localConstructor4 = localClass.getConstructor(PARAMS1);
/* 257 */             return (Permission)localConstructor4.newInstance(new Object[] { this.name });
/*     */           }
/*     */           catch (NoSuchMethodException localNoSuchMethodException4) {
/* 260 */             Constructor localConstructor6 = localClass.getConstructor(PARAMS2);
/* 261 */             return (Permission)localConstructor6.newInstance(new Object[] { this.name, this.actions });
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 266 */       if ((this.name != null) && (this.actions == null)) {
/*     */         try {
/* 268 */           Constructor localConstructor2 = localClass.getConstructor(PARAMS1);
/* 269 */           return (Permission)localConstructor2.newInstance(new Object[] { this.name });
/*     */         }
/*     */         catch (NoSuchMethodException localNoSuchMethodException3) {
/* 272 */           Constructor localConstructor5 = localClass.getConstructor(PARAMS2);
/* 273 */           return (Permission)localConstructor5.newInstance(new Object[] { this.name, this.actions });
/*     */         }
/*     */       }
/*     */       
/* 277 */       Constructor localConstructor3 = localClass.getConstructor(PARAMS2);
/* 278 */       return (Permission)localConstructor3.newInstance(new Object[] { this.name, this.actions });
/*     */ 
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException1)
/*     */     {
/* 283 */       if (debug != null) {
/* 284 */         debug.println("NoSuchMethodException:\n  could not find proper constructor for " + this.type);
/*     */         
/* 286 */         localNoSuchMethodException1.printStackTrace();
/*     */       }
/* 288 */       return null;
/*     */     } catch (Exception localException) {
/* 290 */       if (debug != null) {
/* 291 */         debug.println("unable to instantiate " + this.name);
/* 292 */         localException.printStackTrace();
/*     */       } }
/* 294 */     return null;
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
/*     */   public boolean implies(Permission paramPermission)
/*     */   {
/* 308 */     return false;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 328 */     if (paramObject == this) {
/* 329 */       return true;
/*     */     }
/* 331 */     if (!(paramObject instanceof UnresolvedPermission))
/* 332 */       return false;
/* 333 */     UnresolvedPermission localUnresolvedPermission = (UnresolvedPermission)paramObject;
/*     */     
/*     */ 
/* 336 */     if (!this.type.equals(localUnresolvedPermission.type)) {
/* 337 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 341 */     if (this.name == null) {
/* 342 */       if (localUnresolvedPermission.name != null) {
/* 343 */         return false;
/*     */       }
/* 345 */     } else if (!this.name.equals(localUnresolvedPermission.name)) {
/* 346 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 350 */     if (this.actions == null) {
/* 351 */       if (localUnresolvedPermission.actions != null) {
/* 352 */         return false;
/*     */       }
/*     */     }
/* 355 */     else if (!this.actions.equals(localUnresolvedPermission.actions)) {
/* 356 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 361 */     if (((this.certs == null) && (localUnresolvedPermission.certs != null)) || ((this.certs != null) && (localUnresolvedPermission.certs == null)) || ((this.certs != null) && (localUnresolvedPermission.certs != null) && (this.certs.length != localUnresolvedPermission.certs.length)))
/*     */     {
/*     */ 
/*     */ 
/* 365 */       return false;
/*     */     }
/*     */     
/*     */     int k;
/*     */     
/*     */     int j;
/* 371 */     for (int i = 0; (this.certs != null) && (i < this.certs.length); i++) {
/* 372 */       k = 0;
/* 373 */       for (j = 0; j < localUnresolvedPermission.certs.length; j++) {
/* 374 */         if (this.certs[i].equals(localUnresolvedPermission.certs[j])) {
/* 375 */           k = 1;
/* 376 */           break;
/*     */         }
/*     */       }
/* 379 */       if (k == 0) { return false;
/*     */       }
/*     */     }
/* 382 */     for (i = 0; (localUnresolvedPermission.certs != null) && (i < localUnresolvedPermission.certs.length); i++) {
/* 383 */       k = 0;
/* 384 */       for (j = 0; j < this.certs.length; j++) {
/* 385 */         if (localUnresolvedPermission.certs[i].equals(this.certs[j])) {
/* 386 */           k = 1;
/* 387 */           break;
/*     */         }
/*     */       }
/* 390 */       if (k == 0) return false;
/*     */     }
/* 392 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 402 */     int i = this.type.hashCode();
/* 403 */     if (this.name != null)
/* 404 */       i ^= this.name.hashCode();
/* 405 */     if (this.actions != null)
/* 406 */       i ^= this.actions.hashCode();
/* 407 */     return i;
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
/*     */   public String getActions()
/*     */   {
/* 422 */     return "";
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
/*     */   public String getUnresolvedType()
/*     */   {
/* 435 */     return this.type;
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
/*     */   public String getUnresolvedName()
/*     */   {
/* 449 */     return this.name;
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
/*     */   public String getUnresolvedActions()
/*     */   {
/* 463 */     return this.actions;
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
/*     */   public Certificate[] getUnresolvedCerts()
/*     */   {
/* 477 */     return this.certs == null ? null : (Certificate[])this.certs.clone();
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
/* 488 */     return "(unresolved " + this.type + " " + this.name + " " + this.actions + ")";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PermissionCollection newPermissionCollection()
/*     */   {
/* 500 */     return new UnresolvedPermissionCollection();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 522 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 524 */     if ((this.certs == null) || (this.certs.length == 0)) {
/* 525 */       paramObjectOutputStream.writeInt(0);
/*     */     }
/*     */     else {
/* 528 */       paramObjectOutputStream.writeInt(this.certs.length);
/*     */       
/* 530 */       for (int i = 0; i < this.certs.length; i++) {
/* 531 */         Certificate localCertificate = this.certs[i];
/*     */         try {
/* 533 */           paramObjectOutputStream.writeUTF(localCertificate.getType());
/* 534 */           byte[] arrayOfByte = localCertificate.getEncoded();
/* 535 */           paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 536 */           paramObjectOutputStream.write(arrayOfByte);
/*     */         } catch (CertificateEncodingException localCertificateEncodingException) {
/* 538 */           throw new IOException(localCertificateEncodingException.getMessage());
/*     */         }
/*     */       }
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
/* 551 */     Hashtable localHashtable = null;
/*     */     
/* 553 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 555 */     if (this.type == null) {
/* 556 */       throw new NullPointerException("type can't be null");
/*     */     }
/*     */     
/* 559 */     int i = paramObjectInputStream.readInt();
/* 560 */     if (i > 0)
/*     */     {
/*     */ 
/* 563 */       localHashtable = new Hashtable(3);
/* 564 */       this.certs = new Certificate[i];
/*     */     }
/*     */     
/* 567 */     for (int j = 0; j < i; j++)
/*     */     {
/*     */ 
/* 570 */       String str = paramObjectInputStream.readUTF();
/* 571 */       CertificateFactory localCertificateFactory; if (localHashtable.containsKey(str))
/*     */       {
/* 573 */         localCertificateFactory = (CertificateFactory)localHashtable.get(str);
/*     */       }
/*     */       else {
/*     */         try {
/* 577 */           localCertificateFactory = CertificateFactory.getInstance(str);
/*     */         } catch (CertificateException localCertificateException1) {
/* 579 */           throw new ClassNotFoundException("Certificate factory for " + str + " not found");
/*     */         }
/*     */         
/*     */ 
/* 583 */         localHashtable.put(str, localCertificateFactory);
/*     */       }
/*     */       
/* 586 */       byte[] arrayOfByte = null;
/*     */       try {
/* 588 */         arrayOfByte = new byte[paramObjectInputStream.readInt()];
/*     */       } catch (OutOfMemoryError localOutOfMemoryError) {
/* 590 */         throw new IOException("Certificate too big");
/*     */       }
/* 592 */       paramObjectInputStream.readFully(arrayOfByte);
/* 593 */       ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       try {
/* 595 */         this.certs[j] = localCertificateFactory.generateCertificate(localByteArrayInputStream);
/*     */       } catch (CertificateException localCertificateException2) {
/* 597 */         throw new IOException(localCertificateException2.getMessage());
/*     */       }
/* 599 */       localByteArrayInputStream.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/UnresolvedPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */