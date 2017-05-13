/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.ManifestDigester;
/*     */ import sun.security.util.ManifestEntryVerifier;
/*     */ import sun.security.util.SignatureFileVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JarVerifier
/*     */ {
/*  48 */   static final Debug debug = Debug.getInstance("jar");
/*     */   
/*     */ 
/*     */ 
/*     */   private Hashtable<String, CodeSigner[]> verifiedSigners;
/*     */   
/*     */ 
/*     */ 
/*     */   private Hashtable<String, CodeSigner[]> sigFileSigners;
/*     */   
/*     */ 
/*     */ 
/*     */   private Hashtable<String, byte[]> sigFileData;
/*     */   
/*     */ 
/*     */   private ArrayList<SignatureFileVerifier> pendingBlocks;
/*     */   
/*     */ 
/*     */   private ArrayList<CodeSigner[]> signerCache;
/*     */   
/*     */ 
/*  69 */   private boolean parsingBlockOrSF = false;
/*     */   
/*     */ 
/*  72 */   private boolean parsingMeta = true;
/*     */   
/*     */ 
/*  75 */   private boolean anyToVerify = true;
/*     */   
/*     */ 
/*     */ 
/*     */   private ByteArrayOutputStream baos;
/*     */   
/*     */ 
/*     */   private volatile ManifestDigester manDig;
/*     */   
/*     */ 
/*  85 */   byte[] manifestRawBytes = null;
/*     */   
/*     */ 
/*     */   boolean eagerValidation;
/*     */   
/*     */ 
/*  91 */   private Object csdomain = new Object();
/*     */   
/*     */   private List<Object> manifestDigests;
/*     */   
/*     */   public JarVerifier(byte[] paramArrayOfByte)
/*     */   {
/*  97 */     this.manifestRawBytes = paramArrayOfByte;
/*  98 */     this.sigFileSigners = new Hashtable();
/*  99 */     this.verifiedSigners = new Hashtable();
/* 100 */     this.sigFileData = new Hashtable(11);
/* 101 */     this.pendingBlocks = new ArrayList();
/* 102 */     this.baos = new ByteArrayOutputStream();
/* 103 */     this.manifestDigests = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void beginEntry(JarEntry paramJarEntry, ManifestEntryVerifier paramManifestEntryVerifier)
/*     */     throws IOException
/*     */   {
/* 114 */     if (paramJarEntry == null) {
/* 115 */       return;
/*     */     }
/* 117 */     if (debug != null) {
/* 118 */       debug.println("beginEntry " + paramJarEntry.getName());
/*     */     }
/*     */     
/* 121 */     String str1 = paramJarEntry.getName();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */     if (this.parsingMeta) {
/* 134 */       String str2 = str1.toUpperCase(Locale.ENGLISH);
/* 135 */       if ((str2.startsWith("META-INF/")) || 
/* 136 */         (str2.startsWith("/META-INF/")))
/*     */       {
/* 138 */         if (paramJarEntry.isDirectory()) {
/* 139 */           paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/* 140 */           return;
/*     */         }
/*     */         
/* 143 */         if ((str2.equals("META-INF/MANIFEST.MF")) || 
/* 144 */           (str2.equals("META-INF/INDEX.LIST"))) {
/* 145 */           return;
/*     */         }
/*     */         
/* 148 */         if (SignatureFileVerifier.isBlockOrSF(str2))
/*     */         {
/* 150 */           this.parsingBlockOrSF = true;
/* 151 */           this.baos.reset();
/* 152 */           paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/* 153 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 162 */     if (this.parsingMeta) {
/* 163 */       doneWithMeta();
/*     */     }
/*     */     
/* 166 */     if (paramJarEntry.isDirectory()) {
/* 167 */       paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/* 168 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 173 */     if (str1.startsWith("./")) {
/* 174 */       str1 = str1.substring(2);
/*     */     }
/*     */     
/*     */ 
/* 178 */     if (str1.startsWith("/")) {
/* 179 */       str1 = str1.substring(1);
/*     */     }
/*     */     
/*     */ 
/* 183 */     if ((this.sigFileSigners.get(str1) != null) || 
/* 184 */       (this.verifiedSigners.get(str1) != null)) {
/* 185 */       paramManifestEntryVerifier.setEntry(str1, paramJarEntry);
/* 186 */       return;
/*     */     }
/*     */     
/*     */ 
/* 190 */     paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(int paramInt, ManifestEntryVerifier paramManifestEntryVerifier)
/*     */     throws IOException
/*     */   {
/* 202 */     if (paramInt != -1) {
/* 203 */       if (this.parsingBlockOrSF) {
/* 204 */         this.baos.write(paramInt);
/*     */       } else {
/* 206 */         paramManifestEntryVerifier.update((byte)paramInt);
/*     */       }
/*     */     } else {
/* 209 */       processEntry(paramManifestEntryVerifier);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, ManifestEntryVerifier paramManifestEntryVerifier)
/*     */     throws IOException
/*     */   {
/* 221 */     if (paramInt1 != -1) {
/* 222 */       if (this.parsingBlockOrSF) {
/* 223 */         this.baos.write(paramArrayOfByte, paramInt2, paramInt1);
/*     */       } else {
/* 225 */         paramManifestEntryVerifier.update(paramArrayOfByte, paramInt2, paramInt1);
/*     */       }
/*     */     } else {
/* 228 */       processEntry(paramManifestEntryVerifier);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void processEntry(ManifestEntryVerifier paramManifestEntryVerifier)
/*     */     throws IOException
/*     */   {
/*     */     Object localObject1;
/*     */     
/* 238 */     if (!this.parsingBlockOrSF) {
/* 239 */       localObject1 = paramManifestEntryVerifier.getEntry();
/* 240 */       if ((localObject1 != null) && (((JarEntry)localObject1).signers == null)) {
/* 241 */         ((JarEntry)localObject1).signers = paramManifestEntryVerifier.verify(this.verifiedSigners, this.sigFileSigners);
/* 242 */         ((JarEntry)localObject1).certs = mapSignersToCertArray(((JarEntry)localObject1).signers);
/*     */       }
/*     */     }
/*     */     else {
/*     */       try {
/* 247 */         this.parsingBlockOrSF = false;
/*     */         
/* 249 */         if (debug != null) {
/* 250 */           debug.println("processEntry: processing block");
/*     */         }
/*     */         
/*     */ 
/* 254 */         localObject1 = paramManifestEntryVerifier.getEntry().getName().toUpperCase(Locale.ENGLISH);
/*     */         Object localObject2;
/* 256 */         if (((String)localObject1).endsWith(".SF")) {
/* 257 */           str = ((String)localObject1).substring(0, ((String)localObject1).length() - 3);
/* 258 */           byte[] arrayOfByte = this.baos.toByteArray();
/*     */           
/* 260 */           this.sigFileData.put(str, arrayOfByte);
/*     */           
/*     */ 
/* 263 */           localObject2 = this.pendingBlocks.iterator();
/* 264 */           while (((Iterator)localObject2).hasNext()) {
/* 265 */             SignatureFileVerifier localSignatureFileVerifier = (SignatureFileVerifier)((Iterator)localObject2).next();
/* 266 */             if (localSignatureFileVerifier.needSignatureFile(str)) {
/* 267 */               if (debug != null) {
/* 268 */                 debug.println("processEntry: processing pending block");
/*     */               }
/*     */               
/*     */ 
/* 272 */               localSignatureFileVerifier.setSignatureFile(arrayOfByte);
/* 273 */               localSignatureFileVerifier.process(this.sigFileSigners, this.manifestDigests);
/*     */             }
/*     */           }
/* 276 */           return;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 281 */         String str = ((String)localObject1).substring(0, ((String)localObject1).lastIndexOf("."));
/*     */         
/* 283 */         if (this.signerCache == null) {
/* 284 */           this.signerCache = new ArrayList();
/*     */         }
/* 286 */         if (this.manDig == null) {
/* 287 */           synchronized (this.manifestRawBytes) {
/* 288 */             if (this.manDig == null) {
/* 289 */               this.manDig = new ManifestDigester(this.manifestRawBytes);
/* 290 */               this.manifestRawBytes = null;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 297 */         ??? = new SignatureFileVerifier(this.signerCache, this.manDig, (String)localObject1, this.baos.toByteArray());
/*     */         
/* 299 */         if (((SignatureFileVerifier)???).needSignatureFileBytes())
/*     */         {
/* 301 */           localObject2 = (byte[])this.sigFileData.get(str);
/*     */           
/* 303 */           if (localObject2 == null)
/*     */           {
/*     */ 
/*     */ 
/* 307 */             if (debug != null) {
/* 308 */               debug.println("adding pending block");
/*     */             }
/* 310 */             this.pendingBlocks.add(???);
/* 311 */             return;
/*     */           }
/* 313 */           ((SignatureFileVerifier)???).setSignatureFile((byte[])localObject2);
/*     */         }
/*     */         
/* 316 */         ((SignatureFileVerifier)???).process(this.sigFileSigners, this.manifestDigests);
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/* 320 */         if (debug != null) debug.println("processEntry caught: " + localIOException);
/*     */       }
/*     */       catch (SignatureException localSignatureException) {
/* 323 */         if (debug != null) debug.println("processEntry caught: " + localSignatureException);
/*     */       }
/*     */       catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 326 */         if (debug != null) debug.println("processEntry caught: " + localNoSuchAlgorithmException);
/*     */       }
/*     */       catch (CertificateException localCertificateException) {
/* 329 */         if (debug != null) { debug.println("processEntry caught: " + localCertificateException);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Certificate[] getCerts(String paramString)
/*     */   {
/* 343 */     return mapSignersToCertArray(getCodeSigners(paramString));
/*     */   }
/*     */   
/*     */   public Certificate[] getCerts(JarFile paramJarFile, JarEntry paramJarEntry)
/*     */   {
/* 348 */     return mapSignersToCertArray(getCodeSigners(paramJarFile, paramJarEntry));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CodeSigner[] getCodeSigners(String paramString)
/*     */   {
/* 358 */     return (CodeSigner[])this.verifiedSigners.get(paramString);
/*     */   }
/*     */   
/*     */   public CodeSigner[] getCodeSigners(JarFile paramJarFile, JarEntry paramJarEntry)
/*     */   {
/* 363 */     String str = paramJarEntry.getName();
/* 364 */     if ((this.eagerValidation) && (this.sigFileSigners.get(str) != null))
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/* 370 */         InputStream localInputStream = paramJarFile.getInputStream(paramJarEntry);
/* 371 */         byte[] arrayOfByte = new byte['Ѐ'];
/* 372 */         int i = arrayOfByte.length;
/* 373 */         while (i != -1) {
/* 374 */           i = localInputStream.read(arrayOfByte, 0, arrayOfByte.length);
/*     */         }
/* 376 */         localInputStream.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/* 380 */     return getCodeSigners(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Certificate[] mapSignersToCertArray(CodeSigner[] paramArrayOfCodeSigner)
/*     */   {
/* 390 */     if (paramArrayOfCodeSigner != null) {
/* 391 */       ArrayList localArrayList = new ArrayList();
/* 392 */       for (int i = 0; i < paramArrayOfCodeSigner.length; i++) {
/* 393 */         localArrayList.addAll(paramArrayOfCodeSigner[i]
/* 394 */           .getSignerCertPath().getCertificates());
/*     */       }
/*     */       
/*     */ 
/* 398 */       return (Certificate[])localArrayList.toArray(
/* 399 */         new Certificate[localArrayList.size()]);
/*     */     }
/* 401 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean nothingToVerify()
/*     */   {
/* 411 */     return !this.anyToVerify;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void doneWithMeta()
/*     */   {
/* 422 */     this.parsingMeta = false;
/* 423 */     this.anyToVerify = (!this.sigFileSigners.isEmpty());
/* 424 */     this.baos = null;
/* 425 */     this.sigFileData = null;
/* 426 */     this.pendingBlocks = null;
/* 427 */     this.signerCache = null;
/* 428 */     this.manDig = null;
/*     */     
/*     */ 
/* 431 */     if (this.sigFileSigners.containsKey("META-INF/MANIFEST.MF")) {
/* 432 */       CodeSigner[] arrayOfCodeSigner = (CodeSigner[])this.sigFileSigners.remove("META-INF/MANIFEST.MF");
/* 433 */       this.verifiedSigners.put("META-INF/MANIFEST.MF", arrayOfCodeSigner);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static class VerifierStream
/*     */     extends InputStream
/*     */   {
/*     */     private InputStream is;
/*     */     private JarVerifier jv;
/*     */     private ManifestEntryVerifier mev;
/*     */     private long numLeft;
/*     */     
/*     */     VerifierStream(Manifest paramManifest, JarEntry paramJarEntry, InputStream paramInputStream, JarVerifier paramJarVerifier)
/*     */       throws IOException
/*     */     {
/* 449 */       this.is = paramInputStream;
/* 450 */       this.jv = paramJarVerifier;
/* 451 */       this.mev = new ManifestEntryVerifier(paramManifest);
/* 452 */       this.jv.beginEntry(paramJarEntry, this.mev);
/* 453 */       this.numLeft = paramJarEntry.getSize();
/* 454 */       if (this.numLeft == 0L) {
/* 455 */         this.jv.update(-1, this.mev);
/*     */       }
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 460 */       if (this.numLeft > 0L) {
/* 461 */         int i = this.is.read();
/* 462 */         this.jv.update(i, this.mev);
/* 463 */         this.numLeft -= 1L;
/* 464 */         if (this.numLeft == 0L)
/* 465 */           this.jv.update(-1, this.mev);
/* 466 */         return i;
/*     */       }
/* 468 */       return -1;
/*     */     }
/*     */     
/*     */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */     {
/* 473 */       if ((this.numLeft > 0L) && (this.numLeft < paramInt2)) {
/* 474 */         paramInt2 = (int)this.numLeft;
/*     */       }
/*     */       
/* 477 */       if (this.numLeft > 0L) {
/* 478 */         int i = this.is.read(paramArrayOfByte, paramInt1, paramInt2);
/* 479 */         this.jv.update(i, paramArrayOfByte, paramInt1, paramInt2, this.mev);
/* 480 */         this.numLeft -= i;
/* 481 */         if (this.numLeft == 0L)
/* 482 */           this.jv.update(-1, paramArrayOfByte, paramInt1, paramInt2, this.mev);
/* 483 */         return i;
/*     */       }
/* 485 */       return -1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void close()
/*     */       throws IOException
/*     */     {
/* 492 */       if (this.is != null)
/* 493 */         this.is.close();
/* 494 */       this.is = null;
/* 495 */       this.mev = null;
/* 496 */       this.jv = null;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 500 */       return this.is.available();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 507 */   private Map<URL, Map<CodeSigner[], CodeSource>> urlToCodeSourceMap = new HashMap();
/* 508 */   private Map<CodeSigner[], CodeSource> signerToCodeSource = new HashMap();
/*     */   
/*     */   private URL lastURL;
/*     */   
/*     */   private Map<CodeSigner[], CodeSource> lastURLMap;
/*     */   
/*     */ 
/*     */   private synchronized CodeSource mapSignersToCodeSource(URL paramURL, CodeSigner[] paramArrayOfCodeSigner)
/*     */   {
/*     */     Object localObject1;
/*     */     
/* 519 */     if (paramURL == this.lastURL) {
/* 520 */       localObject1 = this.lastURLMap;
/*     */     } else {
/* 522 */       localObject1 = (Map)this.urlToCodeSourceMap.get(paramURL);
/* 523 */       if (localObject1 == null) {
/* 524 */         localObject1 = new HashMap();
/* 525 */         this.urlToCodeSourceMap.put(paramURL, localObject1);
/*     */       }
/* 527 */       this.lastURLMap = ((Map)localObject1);
/* 528 */       this.lastURL = paramURL;
/*     */     }
/* 530 */     Object localObject2 = (CodeSource)((Map)localObject1).get(paramArrayOfCodeSigner);
/* 531 */     if (localObject2 == null) {
/* 532 */       localObject2 = new VerifierCodeSource(this.csdomain, paramURL, paramArrayOfCodeSigner);
/* 533 */       this.signerToCodeSource.put(paramArrayOfCodeSigner, localObject2);
/*     */     }
/* 535 */     return (CodeSource)localObject2;
/*     */   }
/*     */   
/*     */   private CodeSource[] mapSignersToCodeSources(URL paramURL, List<CodeSigner[]> paramList, boolean paramBoolean) {
/* 539 */     ArrayList localArrayList = new ArrayList();
/*     */     
/* 541 */     for (int i = 0; i < paramList.size(); i++) {
/* 542 */       localArrayList.add(mapSignersToCodeSource(paramURL, (CodeSigner[])paramList.get(i)));
/*     */     }
/* 544 */     if (paramBoolean) {
/* 545 */       localArrayList.add(mapSignersToCodeSource(paramURL, null));
/*     */     }
/* 547 */     return (CodeSource[])localArrayList.toArray(new CodeSource[localArrayList.size()]); }
/*     */   
/* 549 */   private CodeSigner[] emptySigner = new CodeSigner[0];
/*     */   
/*     */   private Map<String, CodeSigner[]> signerMap;
/*     */   
/*     */   private CodeSigner[] findMatchingSigners(CodeSource paramCodeSource)
/*     */   {
/* 555 */     if ((paramCodeSource instanceof VerifierCodeSource)) {
/* 556 */       localObject = (VerifierCodeSource)paramCodeSource;
/* 557 */       if (((VerifierCodeSource)localObject).isSameDomain(this.csdomain)) {
/* 558 */         return ((VerifierCodeSource)paramCodeSource).getPrivateSigners();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 566 */     Object localObject = mapSignersToCodeSources(paramCodeSource.getLocation(), getJarCodeSigners(), true);
/* 567 */     ArrayList localArrayList = new ArrayList();
/* 568 */     for (int i = 0; i < localObject.length; i++) {
/* 569 */       localArrayList.add(localObject[i]);
/*     */     }
/* 571 */     i = localArrayList.indexOf(paramCodeSource);
/* 572 */     if (i != -1)
/*     */     {
/* 574 */       CodeSigner[] arrayOfCodeSigner = ((VerifierCodeSource)localArrayList.get(i)).getPrivateSigners();
/* 575 */       if (arrayOfCodeSigner == null) {
/* 576 */         arrayOfCodeSigner = this.emptySigner;
/*     */       }
/* 578 */       return arrayOfCodeSigner;
/*     */     }
/* 580 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private static class VerifierCodeSource
/*     */     extends CodeSource
/*     */   {
/*     */     private static final long serialVersionUID = -9047366145967768825L;
/*     */     
/*     */     URL vlocation;
/*     */     CodeSigner[] vsigners;
/*     */     Certificate[] vcerts;
/*     */     Object csdomain;
/*     */     
/*     */     VerifierCodeSource(Object paramObject, URL paramURL, CodeSigner[] paramArrayOfCodeSigner)
/*     */     {
/* 596 */       super(paramArrayOfCodeSigner);
/* 597 */       this.csdomain = paramObject;
/* 598 */       this.vlocation = paramURL;
/* 599 */       this.vsigners = paramArrayOfCodeSigner;
/*     */     }
/*     */     
/*     */     VerifierCodeSource(Object paramObject, URL paramURL, Certificate[] paramArrayOfCertificate) {
/* 603 */       super(paramArrayOfCertificate);
/* 604 */       this.csdomain = paramObject;
/* 605 */       this.vlocation = paramURL;
/* 606 */       this.vcerts = paramArrayOfCertificate;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 616 */       if (paramObject == this) {
/* 617 */         return true;
/*     */       }
/* 619 */       if ((paramObject instanceof VerifierCodeSource)) {
/* 620 */         VerifierCodeSource localVerifierCodeSource = (VerifierCodeSource)paramObject;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 627 */         if (isSameDomain(localVerifierCodeSource.csdomain)) {
/* 628 */           if ((localVerifierCodeSource.vsigners != this.vsigners) || (localVerifierCodeSource.vcerts != this.vcerts))
/*     */           {
/* 630 */             return false;
/*     */           }
/* 632 */           if (localVerifierCodeSource.vlocation != null)
/* 633 */             return localVerifierCodeSource.vlocation.equals(this.vlocation);
/* 634 */           if (this.vlocation != null) {
/* 635 */             return this.vlocation.equals(localVerifierCodeSource.vlocation);
/*     */           }
/* 637 */           return true;
/*     */         }
/*     */       }
/*     */       
/* 641 */       return super.equals(paramObject);
/*     */     }
/*     */     
/*     */     boolean isSameDomain(Object paramObject) {
/* 645 */       return this.csdomain == paramObject;
/*     */     }
/*     */     
/*     */     private CodeSigner[] getPrivateSigners() {
/* 649 */       return this.vsigners;
/*     */     }
/*     */     
/*     */     private Certificate[] getPrivateCertificates() {
/* 653 */       return this.vcerts;
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized Map<String, CodeSigner[]> signerMap()
/*     */   {
/* 659 */     if (this.signerMap == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 665 */       this.signerMap = new HashMap(this.verifiedSigners.size() + this.sigFileSigners.size());
/* 666 */       this.signerMap.putAll(this.verifiedSigners);
/* 667 */       this.signerMap.putAll(this.sigFileSigners);
/*     */     }
/* 669 */     return this.signerMap;
/*     */   }
/*     */   
/*     */   public synchronized Enumeration<String> entryNames(JarFile paramJarFile, CodeSource[] paramArrayOfCodeSource) {
/* 673 */     Map localMap = signerMap();
/* 674 */     final Iterator localIterator = localMap.entrySet().iterator();
/* 675 */     int i = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 681 */     ArrayList localArrayList1 = new ArrayList(paramArrayOfCodeSource.length);
/* 682 */     for (int j = 0; j < paramArrayOfCodeSource.length; j++) {
/* 683 */       localObject = findMatchingSigners(paramArrayOfCodeSource[j]);
/* 684 */       if (localObject != null) {
/* 685 */         if (localObject.length > 0) {
/* 686 */           localArrayList1.add(localObject);
/*     */         } else {
/* 688 */           i = 1;
/*     */         }
/*     */       } else {
/* 691 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/* 695 */     final ArrayList localArrayList2 = localArrayList1;
/* 696 */     final Object localObject = i != 0 ? unsignedEntryNames(paramJarFile) : this.emptyEnumeration;
/*     */     
/* 698 */     new Enumeration()
/*     */     {
/*     */       String name;
/*     */       
/*     */       public boolean hasMoreElements() {
/* 703 */         if (this.name != null) {
/* 704 */           return true;
/*     */         }
/*     */         
/* 707 */         while (localIterator.hasNext()) {
/* 708 */           Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 709 */           if (localArrayList2.contains(localEntry.getValue())) {
/* 710 */             this.name = ((String)localEntry.getKey());
/* 711 */             return true;
/*     */           }
/*     */         }
/* 714 */         if (localObject.hasMoreElements()) {
/* 715 */           this.name = ((String)localObject.nextElement());
/* 716 */           return true;
/*     */         }
/* 718 */         return false;
/*     */       }
/*     */       
/*     */       public String nextElement() {
/* 722 */         if (hasMoreElements()) {
/* 723 */           String str = this.name;
/* 724 */           this.name = null;
/* 725 */           return str;
/*     */         }
/* 727 */         throw new NoSuchElementException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Enumeration<JarEntry> entries2(final JarFile paramJarFile, Enumeration<? extends ZipEntry> paramEnumeration)
/*     */   {
/* 737 */     final HashMap localHashMap = new HashMap();
/* 738 */     localHashMap.putAll(signerMap());
/* 739 */     final Enumeration<? extends ZipEntry> localEnumeration = paramEnumeration;
/* 740 */     new Enumeration()
/*     */     {
/* 742 */       Enumeration<String> signers = null;
/*     */       JarEntry entry;
/*     */       
/*     */       public boolean hasMoreElements() {
/* 746 */         if (this.entry != null)
/* 747 */           return true;
/*     */         Object localObject;
/* 749 */         while (localEnumeration.hasMoreElements()) {
/* 750 */           localObject = (ZipEntry)localEnumeration.nextElement();
/* 751 */           if (!JarVerifier.isSigningRelated(((ZipEntry)localObject).getName()))
/*     */           {
/*     */ 
/* 754 */             this.entry = paramJarFile.newEntry((ZipEntry)localObject);
/* 755 */             return true;
/*     */           } }
/* 757 */         if (this.signers == null) {
/* 758 */           this.signers = Collections.enumeration(localHashMap.keySet());
/*     */         }
/* 760 */         if (this.signers.hasMoreElements()) {
/* 761 */           localObject = (String)this.signers.nextElement();
/* 762 */           this.entry = paramJarFile.newEntry(new ZipEntry((String)localObject));
/* 763 */           return true;
/*     */         }
/*     */         
/*     */ 
/* 767 */         return false;
/*     */       }
/*     */       
/*     */       public JarEntry nextElement() {
/* 771 */         if (hasMoreElements()) {
/* 772 */           JarEntry localJarEntry = this.entry;
/* 773 */           localHashMap.remove(localJarEntry.getName());
/* 774 */           this.entry = null;
/* 775 */           return localJarEntry;
/*     */         }
/* 777 */         throw new NoSuchElementException();
/*     */       }
/*     */     }; }
/*     */   
/* 781 */   private Enumeration<String> emptyEnumeration = new Enumeration()
/*     */   {
/*     */     public boolean hasMoreElements() {
/* 784 */       return false;
/*     */     }
/*     */     
/*     */     public String nextElement() {
/* 788 */       throw new NoSuchElementException();
/*     */     }
/*     */   };
/*     */   private List<CodeSigner[]> jarCodeSigners;
/*     */   
/*     */   static boolean isSigningRelated(String paramString) {
/* 794 */     return SignatureFileVerifier.isSigningRelated(paramString);
/*     */   }
/*     */   
/*     */   private Enumeration<String> unsignedEntryNames(JarFile paramJarFile) {
/* 798 */     final Map localMap = signerMap();
/* 799 */     final Enumeration localEnumeration = paramJarFile.entries();
/* 800 */     new Enumeration()
/*     */     {
/*     */       String name;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       public boolean hasMoreElements()
/*     */       {
/* 809 */         if (this.name != null) {
/* 810 */           return true;
/*     */         }
/* 812 */         while (localEnumeration.hasMoreElements())
/*     */         {
/* 814 */           ZipEntry localZipEntry = (ZipEntry)localEnumeration.nextElement();
/* 815 */           String str = localZipEntry.getName();
/* 816 */           if ((!localZipEntry.isDirectory()) && (!JarVerifier.isSigningRelated(str)))
/*     */           {
/*     */ 
/* 819 */             if (localMap.get(str) == null) {
/* 820 */               this.name = str;
/* 821 */               return true;
/*     */             } }
/*     */         }
/* 824 */         return false;
/*     */       }
/*     */       
/*     */       public String nextElement() {
/* 828 */         if (hasMoreElements()) {
/* 829 */           String str = this.name;
/* 830 */           this.name = null;
/* 831 */           return str;
/*     */         }
/* 833 */         throw new NoSuchElementException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized List<CodeSigner[]> getJarCodeSigners()
/*     */   {
/* 841 */     if (this.jarCodeSigners == null) {
/* 842 */       HashSet localHashSet = new HashSet();
/* 843 */       localHashSet.addAll(signerMap().values());
/* 844 */       this.jarCodeSigners = new ArrayList();
/* 845 */       this.jarCodeSigners.addAll(localHashSet);
/*     */     }
/* 847 */     return this.jarCodeSigners;
/*     */   }
/*     */   
/*     */   public synchronized CodeSource[] getCodeSources(JarFile paramJarFile, URL paramURL) {
/* 851 */     boolean bool = unsignedEntryNames(paramJarFile).hasMoreElements();
/*     */     
/* 853 */     return mapSignersToCodeSources(paramURL, getJarCodeSigners(), bool);
/*     */   }
/*     */   
/*     */ 
/*     */   public CodeSource getCodeSource(URL paramURL, String paramString)
/*     */   {
/* 859 */     CodeSigner[] arrayOfCodeSigner = (CodeSigner[])signerMap().get(paramString);
/* 860 */     return mapSignersToCodeSource(paramURL, arrayOfCodeSigner);
/*     */   }
/*     */   
/*     */ 
/*     */   public CodeSource getCodeSource(URL paramURL, JarFile paramJarFile, JarEntry paramJarEntry)
/*     */   {
/* 866 */     return mapSignersToCodeSource(paramURL, getCodeSigners(paramJarFile, paramJarEntry));
/*     */   }
/*     */   
/*     */   public void setEagerValidation(boolean paramBoolean) {
/* 870 */     this.eagerValidation = paramBoolean;
/*     */   }
/*     */   
/*     */   public synchronized List<Object> getManifestDigests() {
/* 874 */     return Collections.unmodifiableList(this.manifestDigests);
/*     */   }
/*     */   
/*     */   static CodeSource getUnsignedCS(URL paramURL) {
/* 878 */     return new VerifierCodeSource(null, paramURL, (Certificate[])null);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/jar/JarVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */