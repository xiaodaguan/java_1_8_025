/*     */ package java.security;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.SocketPermission;
/*     */ import java.net.URL;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CodeSource
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4977541819976013951L;
/*     */   private URL location;
/*  62 */   private transient CodeSigner[] signers = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  67 */   private transient Certificate[] certs = null;
/*     */   
/*     */ 
/*     */   private transient SocketPermission sp;
/*     */   
/*     */ 
/*  73 */   private transient CertificateFactory factory = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CodeSource(URL paramURL, Certificate[] paramArrayOfCertificate)
/*     */   {
/*  85 */     this.location = paramURL;
/*     */     
/*     */ 
/*  88 */     if (paramArrayOfCertificate != null) {
/*  89 */       this.certs = ((Certificate[])paramArrayOfCertificate.clone());
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
/*     */   public CodeSource(URL paramURL, CodeSigner[] paramArrayOfCodeSigner)
/*     */   {
/* 104 */     this.location = paramURL;
/*     */     
/*     */ 
/* 107 */     if (paramArrayOfCodeSigner != null) {
/* 108 */       this.signers = ((CodeSigner[])paramArrayOfCodeSigner.clone());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 119 */     if (this.location != null) {
/* 120 */       return this.location.hashCode();
/*     */     }
/* 122 */     return 0;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 138 */     if (paramObject == this) {
/* 139 */       return true;
/*     */     }
/*     */     
/* 142 */     if (!(paramObject instanceof CodeSource)) {
/* 143 */       return false;
/*     */     }
/* 145 */     CodeSource localCodeSource = (CodeSource)paramObject;
/*     */     
/*     */ 
/* 148 */     if (this.location == null)
/*     */     {
/* 150 */       if (localCodeSource.location != null) { return false;
/*     */       }
/*     */     }
/* 153 */     else if (!this.location.equals(localCodeSource.location)) { return false;
/*     */     }
/*     */     
/*     */ 
/* 157 */     return matchCerts(localCodeSource, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final URL getLocation()
/*     */   {
/* 168 */     return this.location;
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
/*     */   public final Certificate[] getCertificates()
/*     */   {
/* 186 */     if (this.certs != null) {
/* 187 */       return (Certificate[])this.certs.clone();
/*     */     }
/* 189 */     if (this.signers != null)
/*     */     {
/* 191 */       ArrayList localArrayList = new ArrayList();
/*     */       
/* 193 */       for (int i = 0; i < this.signers.length; i++) {
/* 194 */         localArrayList.addAll(this.signers[i]
/* 195 */           .getSignerCertPath().getCertificates());
/*     */       }
/* 197 */       this.certs = ((Certificate[])localArrayList.toArray(
/* 198 */         new Certificate[localArrayList.size()]));
/* 199 */       return (Certificate[])this.certs.clone();
/*     */     }
/*     */     
/* 202 */     return null;
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
/*     */   public final CodeSigner[] getCodeSigners()
/*     */   {
/* 220 */     if (this.signers != null) {
/* 221 */       return (CodeSigner[])this.signers.clone();
/*     */     }
/* 223 */     if (this.certs != null)
/*     */     {
/* 225 */       this.signers = convertCertArrayToSignerArray(this.certs);
/* 226 */       return (CodeSigner[])this.signers.clone();
/*     */     }
/*     */     
/* 229 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean implies(CodeSource paramCodeSource)
/*     */   {
/* 305 */     if (paramCodeSource == null) {
/* 306 */       return false;
/*     */     }
/* 308 */     return (matchCerts(paramCodeSource, false)) && (matchLocation(paramCodeSource));
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
/*     */   private boolean matchCerts(CodeSource paramCodeSource, boolean paramBoolean)
/*     */   {
/* 324 */     if ((this.certs == null) && (this.signers == null)) {
/* 325 */       if (paramBoolean) {
/* 326 */         return (paramCodeSource.certs == null) && (paramCodeSource.signers == null);
/*     */       }
/* 328 */       return true; }
/*     */     int j;
/*     */     int i;
/* 331 */     int k; if ((this.signers != null) && (paramCodeSource.signers != null)) {
/* 332 */       if ((paramBoolean) && (this.signers.length != paramCodeSource.signers.length)) {
/* 333 */         return false;
/*     */       }
/* 335 */       for (j = 0; j < this.signers.length; j++) {
/* 336 */         i = 0;
/* 337 */         for (k = 0; k < paramCodeSource.signers.length; k++) {
/* 338 */           if (this.signers[j].equals(paramCodeSource.signers[k])) {
/* 339 */             i = 1;
/* 340 */             break;
/*     */           }
/*     */         }
/* 343 */         if (i == 0) return false;
/*     */       }
/* 345 */       return true;
/*     */     }
/*     */     
/* 348 */     if ((this.certs != null) && (paramCodeSource.certs != null)) {
/* 349 */       if ((paramBoolean) && (this.certs.length != paramCodeSource.certs.length)) {
/* 350 */         return false;
/*     */       }
/* 352 */       for (j = 0; j < this.certs.length; j++) {
/* 353 */         i = 0;
/* 354 */         for (k = 0; k < paramCodeSource.certs.length; k++) {
/* 355 */           if (this.certs[j].equals(paramCodeSource.certs[k])) {
/* 356 */             i = 1;
/* 357 */             break;
/*     */           }
/*     */         }
/* 360 */         if (i == 0) return false;
/*     */       }
/* 362 */       return true;
/*     */     }
/*     */     
/* 365 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean matchLocation(CodeSource paramCodeSource)
/*     */   {
/* 375 */     if (this.location == null) {
/* 376 */       return true;
/*     */     }
/* 378 */     if ((paramCodeSource == null) || (paramCodeSource.location == null)) {
/* 379 */       return false;
/*     */     }
/* 381 */     if (this.location.equals(paramCodeSource.location)) {
/* 382 */       return true;
/*     */     }
/* 384 */     if (!this.location.getProtocol().equalsIgnoreCase(paramCodeSource.location.getProtocol())) {
/* 385 */       return false;
/*     */     }
/* 387 */     int i = this.location.getPort();
/* 388 */     if (i != -1) {
/* 389 */       int j = paramCodeSource.location.getPort();
/*     */       
/* 391 */       int m = j != -1 ? j : paramCodeSource.location.getDefaultPort();
/* 392 */       if (i != m) {
/* 393 */         return false;
/*     */       }
/*     */     }
/* 396 */     if (this.location.getFile().endsWith("/-"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 401 */       String str1 = this.location.getFile().substring(0, this.location
/* 402 */         .getFile().length() - 1);
/* 403 */       if (!paramCodeSource.location.getFile().startsWith(str1))
/* 404 */         return false;
/* 405 */     } else if (this.location.getFile().endsWith("/*"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 410 */       int k = paramCodeSource.location.getFile().lastIndexOf('/');
/* 411 */       if (k == -1)
/* 412 */         return false;
/* 413 */       str3 = this.location.getFile().substring(0, this.location
/* 414 */         .getFile().length() - 1);
/* 415 */       String str4 = paramCodeSource.location.getFile().substring(0, k + 1);
/* 416 */       if (!str4.equals(str3)) {
/* 417 */         return false;
/*     */       }
/*     */       
/*     */     }
/* 421 */     else if ((!paramCodeSource.location.getFile().equals(this.location.getFile())) && 
/* 422 */       (!paramCodeSource.location.getFile().equals(this.location.getFile() + "/"))) {
/* 423 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 427 */     if ((this.location.getRef() != null) && 
/* 428 */       (!this.location.getRef().equals(paramCodeSource.location.getRef()))) {
/* 429 */       return false;
/*     */     }
/*     */     
/* 432 */     String str2 = this.location.getHost();
/* 433 */     String str3 = paramCodeSource.location.getHost();
/* 434 */     if ((str2 != null) && (
/* 435 */       ((!"".equals(str2)) && (!"localhost".equals(str2))) || (
/* 436 */       (!"".equals(str3)) && (!"localhost".equals(str3)))))
/*     */     {
/* 438 */       if (!str2.equals(str3)) {
/* 439 */         if (str3 == null) {
/* 440 */           return false;
/*     */         }
/* 442 */         if (this.sp == null) {
/* 443 */           this.sp = new SocketPermission(str2, "resolve");
/*     */         }
/* 445 */         if (paramCodeSource.sp == null) {
/* 446 */           paramCodeSource.sp = new SocketPermission(str3, "resolve");
/*     */         }
/* 448 */         if (!this.sp.implies(paramCodeSource.sp)) {
/* 449 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 454 */     return true;
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
/* 465 */     StringBuilder localStringBuilder = new StringBuilder();
/* 466 */     localStringBuilder.append("(");
/* 467 */     localStringBuilder.append(this.location);
/*     */     int i;
/* 469 */     if ((this.certs != null) && (this.certs.length > 0)) {
/* 470 */       for (i = 0; i < this.certs.length; i++) {
/* 471 */         localStringBuilder.append(" " + this.certs[i]);
/*     */       }
/*     */       
/* 474 */     } else if ((this.signers != null) && (this.signers.length > 0)) {
/* 475 */       for (i = 0; i < this.signers.length; i++) {
/* 476 */         localStringBuilder.append(" " + this.signers[i]);
/*     */       }
/*     */     } else {
/* 479 */       localStringBuilder.append(" <no signer certificates>");
/*     */     }
/* 481 */     localStringBuilder.append(")");
/* 482 */     return localStringBuilder.toString();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 502 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 505 */     if ((this.certs == null) || (this.certs.length == 0)) {
/* 506 */       paramObjectOutputStream.writeInt(0);
/*     */     }
/*     */     else {
/* 509 */       paramObjectOutputStream.writeInt(this.certs.length);
/*     */       
/* 511 */       for (int i = 0; i < this.certs.length; i++) {
/* 512 */         Certificate localCertificate = this.certs[i];
/*     */         try {
/* 514 */           paramObjectOutputStream.writeUTF(localCertificate.getType());
/* 515 */           byte[] arrayOfByte = localCertificate.getEncoded();
/* 516 */           paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 517 */           paramObjectOutputStream.write(arrayOfByte);
/*     */         } catch (CertificateEncodingException localCertificateEncodingException) {
/* 519 */           throw new IOException(localCertificateEncodingException.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 525 */     if ((this.signers != null) && (this.signers.length > 0)) {
/* 526 */       paramObjectOutputStream.writeObject(this.signers);
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
/* 537 */     Hashtable localHashtable = null;
/*     */     
/* 539 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 542 */     int i = paramObjectInputStream.readInt();
/* 543 */     if (i > 0)
/*     */     {
/*     */ 
/* 546 */       localHashtable = new Hashtable(3);
/* 547 */       this.certs = new Certificate[i];
/*     */     }
/*     */     
/* 550 */     for (int j = 0; j < i; j++)
/*     */     {
/*     */ 
/* 553 */       String str = paramObjectInputStream.readUTF();
/* 554 */       CertificateFactory localCertificateFactory; if (localHashtable.containsKey(str))
/*     */       {
/* 556 */         localCertificateFactory = (CertificateFactory)localHashtable.get(str);
/*     */       }
/*     */       else {
/*     */         try {
/* 560 */           localCertificateFactory = CertificateFactory.getInstance(str);
/*     */         } catch (CertificateException localCertificateException1) {
/* 562 */           throw new ClassNotFoundException("Certificate factory for " + str + " not found");
/*     */         }
/*     */         
/*     */ 
/* 566 */         localHashtable.put(str, localCertificateFactory);
/*     */       }
/*     */       
/* 569 */       byte[] arrayOfByte = null;
/*     */       try {
/* 571 */         arrayOfByte = new byte[paramObjectInputStream.readInt()];
/*     */       } catch (OutOfMemoryError localOutOfMemoryError) {
/* 573 */         throw new IOException("Certificate too big");
/*     */       }
/* 575 */       paramObjectInputStream.readFully(arrayOfByte);
/* 576 */       ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       try {
/* 578 */         this.certs[j] = localCertificateFactory.generateCertificate(localByteArrayInputStream);
/*     */       } catch (CertificateException localCertificateException2) {
/* 580 */         throw new IOException(localCertificateException2.getMessage());
/*     */       }
/* 582 */       localByteArrayInputStream.close();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 587 */       this.signers = ((CodeSigner[])((CodeSigner[])paramObjectInputStream.readObject()).clone());
/*     */     }
/*     */     catch (IOException localIOException) {}
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
/*     */   private CodeSigner[] convertCertArrayToSignerArray(Certificate[] paramArrayOfCertificate)
/*     */   {
/* 603 */     if (paramArrayOfCertificate == null) {
/* 604 */       return null;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 609 */       if (this.factory == null) {
/* 610 */         this.factory = CertificateFactory.getInstance("X.509");
/*     */       }
/*     */       
/*     */ 
/* 614 */       int i = 0;
/* 615 */       ArrayList localArrayList1 = new ArrayList();
/* 616 */       while (i < paramArrayOfCertificate.length) {
/* 617 */         ArrayList localArrayList2 = new ArrayList();
/*     */         
/* 619 */         localArrayList2.add(paramArrayOfCertificate[(i++)]);
/* 620 */         int j = i;
/*     */         
/*     */ 
/*     */ 
/* 624 */         while ((j < paramArrayOfCertificate.length) && ((paramArrayOfCertificate[j] instanceof X509Certificate)))
/*     */         {
/* 626 */           if (((X509Certificate)paramArrayOfCertificate[j]).getBasicConstraints() == -1) break;
/* 627 */           localArrayList2.add(paramArrayOfCertificate[j]);
/* 628 */           j++;
/*     */         }
/* 630 */         i = j;
/* 631 */         CertPath localCertPath = this.factory.generateCertPath(localArrayList2);
/* 632 */         localArrayList1.add(new CodeSigner(localCertPath, null));
/*     */       }
/*     */       
/* 635 */       if (localArrayList1.isEmpty()) {
/* 636 */         return null;
/*     */       }
/* 638 */       return (CodeSigner[])localArrayList1.toArray(new CodeSigner[localArrayList1.size()]);
/*     */     }
/*     */     catch (CertificateException localCertificateException) {}
/*     */     
/* 642 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/CodeSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */