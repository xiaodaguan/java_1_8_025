/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.util.Debug;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarFile
/*     */   extends ZipFile
/*     */ {
/*     */   private SoftReference<Manifest> manRef;
/*     */   private JarEntry manEntry;
/*     */   private JarVerifier jv;
/*     */   private boolean jvInitialized;
/*     */   private boolean verify;
/*     */   private boolean hasClassPathAttribute;
/*     */   private volatile boolean hasCheckedSpecialAttributes;
/*     */   public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
/*     */   private static final char[] CLASSPATH_CHARS;
/*     */   private static final int[] CLASSPATH_LASTOCC;
/*     */   private static final int[] CLASSPATH_OPTOSFT;
/*     */   private static String javaHome;
/*     */   private static volatile String[] jarNames;
/*     */   
/*     */   public JarFile(String paramString)
/*     */     throws IOException
/*     */   {
/* 103 */     this(new File(paramString), true, 1);
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
/*     */   public JarFile(String paramString, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 117 */     this(new File(paramString), paramBoolean, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JarFile(File paramFile)
/*     */     throws IOException
/*     */   {
/* 130 */     this(paramFile, true, 1);
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
/*     */   public JarFile(File paramFile, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 145 */     this(paramFile, paramBoolean, 1);
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
/*     */   public JarFile(File paramFile, boolean paramBoolean, int paramInt)
/*     */     throws IOException
/*     */   {
/* 166 */     super(paramFile, paramInt);
/* 167 */     this.verify = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Manifest getManifest()
/*     */     throws IOException
/*     */   {
/* 180 */     return getManifestFromReference();
/*     */   }
/*     */   
/*     */   private Manifest getManifestFromReference() throws IOException {
/* 184 */     Manifest localManifest = this.manRef != null ? (Manifest)this.manRef.get() : null;
/*     */     
/* 186 */     if (localManifest == null)
/*     */     {
/* 188 */       JarEntry localJarEntry = getManEntry();
/*     */       
/*     */ 
/* 191 */       if (localJarEntry != null) {
/* 192 */         if (this.verify) {
/* 193 */           byte[] arrayOfByte = getBytes(localJarEntry);
/* 194 */           localManifest = new Manifest(new ByteArrayInputStream(arrayOfByte));
/* 195 */           if (!this.jvInitialized) {
/* 196 */             this.jv = new JarVerifier(arrayOfByte);
/*     */           }
/*     */         } else {
/* 199 */           localManifest = new Manifest(super.getInputStream(localJarEntry));
/*     */         }
/* 201 */         this.manRef = new SoftReference(localManifest);
/*     */       }
/*     */     }
/* 204 */     return localManifest;
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
/*     */   public JarEntry getJarEntry(String paramString)
/*     */   {
/* 223 */     return (JarEntry)getEntry(paramString);
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
/*     */   public ZipEntry getEntry(String paramString)
/*     */   {
/* 240 */     ZipEntry localZipEntry = super.getEntry(paramString);
/* 241 */     if (localZipEntry != null) {
/* 242 */       return new JarFileEntry(localZipEntry);
/*     */     }
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   private class JarEntryIterator implements Enumeration<JarEntry>, Iterator<JarEntry> {
/*     */     private JarEntryIterator() {}
/*     */     
/* 250 */     final Enumeration<? extends ZipEntry> e = JarFile.this.entries();
/*     */     
/*     */     public boolean hasNext() {
/* 253 */       return this.e.hasMoreElements();
/*     */     }
/*     */     
/*     */     public JarEntry next() {
/* 257 */       ZipEntry localZipEntry = (ZipEntry)this.e.nextElement();
/* 258 */       return new JarFile.JarFileEntry(JarFile.this, localZipEntry);
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements() {
/* 262 */       return hasNext();
/*     */     }
/*     */     
/*     */     public JarEntry nextElement() {
/* 266 */       return next();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Enumeration<JarEntry> entries()
/*     */   {
/* 274 */     return new JarEntryIterator(null);
/*     */   }
/*     */   
/*     */   public Stream<JarEntry> stream()
/*     */   {
/* 279 */     return StreamSupport.stream(Spliterators.spliterator(new JarEntryIterator(null), 
/* 280 */       size(), 1297), false);
/*     */   }
/*     */   
/*     */ 
/*     */   private class JarFileEntry
/*     */     extends JarEntry
/*     */   {
/* 287 */     JarFileEntry(ZipEntry paramZipEntry) { super(); }
/*     */     
/*     */     public Attributes getAttributes() throws IOException {
/* 290 */       Manifest localManifest = JarFile.this.getManifest();
/* 291 */       if (localManifest != null) {
/* 292 */         return localManifest.getAttributes(getName());
/*     */       }
/* 294 */       return null;
/*     */     }
/*     */     
/*     */     public Certificate[] getCertificates() {
/*     */       try {
/* 299 */         JarFile.this.maybeInstantiateVerifier();
/*     */       } catch (IOException localIOException) {
/* 301 */         throw new RuntimeException(localIOException);
/*     */       }
/* 303 */       if ((this.certs == null) && (JarFile.this.jv != null)) {
/* 304 */         this.certs = JarFile.this.jv.getCerts(JarFile.this, this);
/*     */       }
/* 306 */       return this.certs == null ? null : (Certificate[])this.certs.clone();
/*     */     }
/*     */     
/*     */     public CodeSigner[] getCodeSigners() {
/* 310 */       try { JarFile.this.maybeInstantiateVerifier();
/*     */       } catch (IOException localIOException) {
/* 312 */         throw new RuntimeException(localIOException);
/*     */       }
/* 314 */       if ((this.signers == null) && (JarFile.this.jv != null)) {
/* 315 */         this.signers = JarFile.this.jv.getCodeSigners(JarFile.this, this);
/*     */       }
/* 317 */       return this.signers == null ? null : (CodeSigner[])this.signers.clone();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void maybeInstantiateVerifier()
/*     */     throws IOException
/*     */   {
/* 328 */     if (this.jv != null) {
/* 329 */       return;
/*     */     }
/*     */     
/* 332 */     if (this.verify) {
/* 333 */       String[] arrayOfString = getMetaInfEntryNames();
/* 334 */       if (arrayOfString != null) {
/* 335 */         for (int i = 0; i < arrayOfString.length; i++) {
/* 336 */           String str = arrayOfString[i].toUpperCase(Locale.ENGLISH);
/* 337 */           if ((str.endsWith(".DSA")) || 
/* 338 */             (str.endsWith(".RSA")) || 
/* 339 */             (str.endsWith(".EC")) || 
/* 340 */             (str.endsWith(".SF")))
/*     */           {
/*     */ 
/*     */ 
/* 344 */             getManifest();
/* 345 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 351 */       this.verify = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initializeVerifier()
/*     */   {
/* 361 */     ManifestEntryVerifier localManifestEntryVerifier = null;
/*     */     
/*     */     try
/*     */     {
/* 365 */       String[] arrayOfString = getMetaInfEntryNames();
/* 366 */       if (arrayOfString != null) {
/* 367 */         for (int i = 0; i < arrayOfString.length; i++) {
/* 368 */           String str = arrayOfString[i].toUpperCase(Locale.ENGLISH);
/* 369 */           if (("META-INF/MANIFEST.MF".equals(str)) || 
/* 370 */             (SignatureFileVerifier.isBlockOrSF(str))) {
/* 371 */             JarEntry localJarEntry = getJarEntry(arrayOfString[i]);
/* 372 */             if (localJarEntry == null) {
/* 373 */               throw new JarException("corrupted jar file");
/*     */             }
/* 375 */             if (localManifestEntryVerifier == null)
/*     */             {
/* 377 */               localManifestEntryVerifier = new ManifestEntryVerifier(getManifestFromReference());
/*     */             }
/* 379 */             byte[] arrayOfByte = getBytes(localJarEntry);
/* 380 */             if ((arrayOfByte != null) && (arrayOfByte.length > 0)) {
/* 381 */               this.jv.beginEntry(localJarEntry, localManifestEntryVerifier);
/* 382 */               this.jv.update(arrayOfByte.length, arrayOfByte, 0, arrayOfByte.length, localManifestEntryVerifier);
/* 383 */               this.jv.update(-1, null, 0, 0, localManifestEntryVerifier);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 391 */       this.jv = null;
/* 392 */       this.verify = false;
/* 393 */       if (JarVerifier.debug != null) {
/* 394 */         JarVerifier.debug.println("jarfile parsing error!");
/* 395 */         localIOException.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 402 */     if (this.jv != null)
/*     */     {
/* 404 */       this.jv.doneWithMeta();
/* 405 */       if (JarVerifier.debug != null) {
/* 406 */         JarVerifier.debug.println("done with meta!");
/*     */       }
/*     */       
/* 409 */       if (this.jv.nothingToVerify()) {
/* 410 */         if (JarVerifier.debug != null) {
/* 411 */           JarVerifier.debug.println("nothing to verify!");
/*     */         }
/* 413 */         this.jv = null;
/* 414 */         this.verify = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private byte[] getBytes(ZipEntry paramZipEntry)
/*     */     throws IOException
/*     */   {
/* 424 */     InputStream localInputStream = super.getInputStream(paramZipEntry);Object localObject1 = null;
/* 425 */     try { return IOUtils.readFully(localInputStream, (int)paramZipEntry.getSize(), true);
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 424 */       localObject1 = localThrowable1;throw localThrowable1;
/*     */     } finally {
/* 426 */       if (localInputStream != null) { if (localObject1 != null) try { localInputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject1).addSuppressed(localThrowable3); } else { localInputStream.close();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized InputStream getInputStream(ZipEntry paramZipEntry)
/*     */     throws IOException
/*     */   {
/* 445 */     maybeInstantiateVerifier();
/* 446 */     if (this.jv == null) {
/* 447 */       return super.getInputStream(paramZipEntry);
/*     */     }
/* 449 */     if (!this.jvInitialized) {
/* 450 */       initializeVerifier();
/* 451 */       this.jvInitialized = true;
/*     */       
/*     */ 
/*     */ 
/* 455 */       if (this.jv == null) {
/* 456 */         return super.getInputStream(paramZipEntry);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 464 */     return new JarVerifier.VerifierStream(getManifestFromReference(), (paramZipEntry instanceof JarFileEntry) ? (JarEntry)paramZipEntry : getJarEntry(paramZipEntry.getName()), super.getInputStream(paramZipEntry), this.jv);
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*  85 */     SharedSecrets.setJavaUtilJarAccess(new JavaUtilJarAccessImpl());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 469 */     CLASSPATH_CHARS = new char[] { 'c', 'l', 'a', 's', 's', '-', 'p', 'a', 't', 'h' };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 476 */     CLASSPATH_LASTOCC = new int[''];
/* 477 */     CLASSPATH_OPTOSFT = new int[10];
/* 478 */     CLASSPATH_LASTOCC[99] = 1;
/* 479 */     CLASSPATH_LASTOCC[108] = 2;
/* 480 */     CLASSPATH_LASTOCC[115] = 5;
/* 481 */     CLASSPATH_LASTOCC[45] = 6;
/* 482 */     CLASSPATH_LASTOCC[112] = 7;
/* 483 */     CLASSPATH_LASTOCC[97] = 8;
/* 484 */     CLASSPATH_LASTOCC[116] = 9;
/* 485 */     CLASSPATH_LASTOCC[104] = 10;
/* 486 */     for (int i = 0; i < 9; i++)
/* 487 */       CLASSPATH_OPTOSFT[i] = 10;
/* 488 */     CLASSPATH_OPTOSFT[9] = 1;
/*     */   }
/*     */   
/*     */   private JarEntry getManEntry() {
/* 492 */     if (this.manEntry == null)
/*     */     {
/* 494 */       this.manEntry = getJarEntry("META-INF/MANIFEST.MF");
/* 495 */       if (this.manEntry == null)
/*     */       {
/*     */ 
/* 498 */         String[] arrayOfString = getMetaInfEntryNames();
/* 499 */         if (arrayOfString != null) {
/* 500 */           for (int i = 0; i < arrayOfString.length; i++) {
/* 501 */             if ("META-INF/MANIFEST.MF".equals(arrayOfString[i]
/* 502 */               .toUpperCase(Locale.ENGLISH))) {
/* 503 */               this.manEntry = getJarEntry(arrayOfString[i]);
/* 504 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 510 */     return this.manEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean hasClassPathAttribute()
/*     */     throws IOException
/*     */   {
/* 518 */     checkForSpecialAttributes();
/* 519 */     return this.hasClassPathAttribute;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean match(char[] paramArrayOfChar, byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 528 */     int i = paramArrayOfChar.length;
/* 529 */     int j = paramArrayOfByte.length - i;
/* 530 */     int k = 0;
/*     */     
/* 532 */     if (k <= j) {
/* 533 */       for (int m = i - 1;; m--) { if (m < 0) break label112;
/* 534 */         int n = (char)paramArrayOfByte[(k + m)];
/* 535 */         n = (n - 65 | 90 - n) >= 0 ? (char)(n + 32) : n;
/* 536 */         if (n != paramArrayOfChar[m]) {
/* 537 */           k += Math.max(m + 1 - paramArrayOfInt1[(n & 0x7F)], paramArrayOfInt2[m]);
/* 538 */           break;
/*     */         } }
/*     */       label112:
/* 541 */       return true;
/*     */     }
/* 543 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void checkForSpecialAttributes()
/*     */     throws IOException
/*     */   {
/* 551 */     if (this.hasCheckedSpecialAttributes) return;
/* 552 */     if (!isKnownNotToHaveSpecialAttributes()) {
/* 553 */       JarEntry localJarEntry = getManEntry();
/* 554 */       if (localJarEntry != null) {
/* 555 */         byte[] arrayOfByte = getBytes(localJarEntry);
/* 556 */         if (match(CLASSPATH_CHARS, arrayOfByte, CLASSPATH_LASTOCC, CLASSPATH_OPTOSFT))
/* 557 */           this.hasClassPathAttribute = true;
/*     */       }
/*     */     }
/* 560 */     this.hasCheckedSpecialAttributes = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isKnownNotToHaveSpecialAttributes()
/*     */   {
/* 570 */     if (javaHome == null) {
/* 571 */       javaHome = (String)AccessController.doPrivileged(new GetPropertyAction("java.home"));
/*     */     }
/*     */     
/* 574 */     if (jarNames == null) {
/* 575 */       localObject = new String[11];
/* 576 */       str = File.separator;
/* 577 */       int i = 0;
/* 578 */       localObject[(i++)] = (str + "rt.jar");
/* 579 */       localObject[(i++)] = (str + "jsse.jar");
/* 580 */       localObject[(i++)] = (str + "jce.jar");
/* 581 */       localObject[(i++)] = (str + "charsets.jar");
/* 582 */       localObject[(i++)] = (str + "dnsns.jar");
/* 583 */       localObject[(i++)] = (str + "zipfs.jar");
/* 584 */       localObject[(i++)] = (str + "localedata.jar");
/* 585 */       localObject[(i++)] = (str = "cldrdata.jar");
/* 586 */       localObject[(i++)] = (str + "sunjce_provider.jar");
/* 587 */       localObject[(i++)] = (str + "sunpkcs11.jar");
/* 588 */       localObject[(i++)] = (str + "sunec.jar");
/* 589 */       jarNames = (String[])localObject;
/*     */     }
/*     */     
/* 592 */     Object localObject = getName();
/* 593 */     String str = javaHome;
/* 594 */     if (((String)localObject).startsWith(str)) {
/* 595 */       String[] arrayOfString = jarNames;
/* 596 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 597 */         if (((String)localObject).endsWith(arrayOfString[j])) {
/* 598 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 602 */     return false;
/*     */   }
/*     */   
/*     */   private synchronized void ensureInitialization() {
/*     */     try {
/* 607 */       maybeInstantiateVerifier();
/*     */     } catch (IOException localIOException) {
/* 609 */       throw new RuntimeException(localIOException);
/*     */     }
/* 611 */     if ((this.jv != null) && (!this.jvInitialized)) {
/* 612 */       initializeVerifier();
/* 613 */       this.jvInitialized = true;
/*     */     }
/*     */   }
/*     */   
/*     */   JarEntry newEntry(ZipEntry paramZipEntry) {
/* 618 */     return new JarFileEntry(paramZipEntry);
/*     */   }
/*     */   
/*     */   Enumeration<String> entryNames(CodeSource[] paramArrayOfCodeSource) {
/* 622 */     ensureInitialization();
/* 623 */     if (this.jv != null) {
/* 624 */       return this.jv.entryNames(this, paramArrayOfCodeSource);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 631 */     int i = 0;
/* 632 */     for (int j = 0; j < paramArrayOfCodeSource.length; j++) {
/* 633 */       if (paramArrayOfCodeSource[j].getCodeSigners() == null) {
/* 634 */         i = 1;
/* 635 */         break;
/*     */       }
/*     */     }
/* 638 */     if (i != 0) {
/* 639 */       return unsignedEntryNames();
/*     */     }
/* 641 */     new Enumeration()
/*     */     {
/*     */       public boolean hasMoreElements() {
/* 644 */         return false;
/*     */       }
/*     */       
/*     */       public String nextElement() {
/* 648 */         throw new NoSuchElementException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Enumeration<JarEntry> entries2()
/*     */   {
/* 660 */     ensureInitialization();
/* 661 */     if (this.jv != null) {
/* 662 */       return this.jv.entries2(this, super.entries());
/*     */     }
/*     */     
/*     */ 
/* 666 */     final Enumeration localEnumeration = super.entries();
/* 667 */     new Enumeration()
/*     */     {
/*     */       ZipEntry entry;
/*     */       
/*     */       public boolean hasMoreElements() {
/* 672 */         if (this.entry != null) {
/* 673 */           return true;
/*     */         }
/* 675 */         while (localEnumeration.hasMoreElements()) {
/* 676 */           ZipEntry localZipEntry = (ZipEntry)localEnumeration.nextElement();
/* 677 */           if (!JarVerifier.isSigningRelated(localZipEntry.getName()))
/*     */           {
/*     */ 
/* 680 */             this.entry = localZipEntry;
/* 681 */             return true;
/*     */           } }
/* 683 */         return false;
/*     */       }
/*     */       
/*     */       public JarFile.JarFileEntry nextElement() {
/* 687 */         if (hasMoreElements()) {
/* 688 */           ZipEntry localZipEntry = this.entry;
/* 689 */           this.entry = null;
/* 690 */           return new JarFile.JarFileEntry(JarFile.this, localZipEntry);
/*     */         }
/* 692 */         throw new NoSuchElementException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   CodeSource[] getCodeSources(URL paramURL) {
/* 698 */     ensureInitialization();
/* 699 */     if (this.jv != null) {
/* 700 */       return this.jv.getCodeSources(this, paramURL);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 707 */     Enumeration localEnumeration = unsignedEntryNames();
/* 708 */     if (localEnumeration.hasMoreElements()) {
/* 709 */       return new CodeSource[] { JarVerifier.getUnsignedCS(paramURL) };
/*     */     }
/* 711 */     return null;
/*     */   }
/*     */   
/*     */   private Enumeration<String> unsignedEntryNames()
/*     */   {
/* 716 */     final Enumeration localEnumeration = entries();
/* 717 */     new Enumeration()
/*     */     {
/*     */       String name;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       public boolean hasMoreElements()
/*     */       {
/* 726 */         if (this.name != null) {
/* 727 */           return true;
/*     */         }
/* 729 */         while (localEnumeration.hasMoreElements())
/*     */         {
/* 731 */           ZipEntry localZipEntry = (ZipEntry)localEnumeration.nextElement();
/* 732 */           String str = localZipEntry.getName();
/* 733 */           if ((!localZipEntry.isDirectory()) && (!JarVerifier.isSigningRelated(str)))
/*     */           {
/*     */ 
/* 736 */             this.name = str;
/* 737 */             return true;
/*     */           } }
/* 739 */         return false;
/*     */       }
/*     */       
/*     */       public String nextElement() {
/* 743 */         if (hasMoreElements()) {
/* 744 */           String str = this.name;
/* 745 */           this.name = null;
/* 746 */           return str;
/*     */         }
/* 748 */         throw new NoSuchElementException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   CodeSource getCodeSource(URL paramURL, String paramString) {
/* 754 */     ensureInitialization();
/* 755 */     if (this.jv != null) {
/* 756 */       if (this.jv.eagerValidation) {
/* 757 */         CodeSource localCodeSource = null;
/* 758 */         JarEntry localJarEntry = getJarEntry(paramString);
/* 759 */         if (localJarEntry != null) {
/* 760 */           localCodeSource = this.jv.getCodeSource(paramURL, this, localJarEntry);
/*     */         } else {
/* 762 */           localCodeSource = this.jv.getCodeSource(paramURL, paramString);
/*     */         }
/* 764 */         return localCodeSource;
/*     */       }
/* 766 */       return this.jv.getCodeSource(paramURL, paramString);
/*     */     }
/*     */     
/*     */ 
/* 770 */     return JarVerifier.getUnsignedCS(paramURL);
/*     */   }
/*     */   
/*     */   void setEagerValidation(boolean paramBoolean) {
/*     */     try {
/* 775 */       maybeInstantiateVerifier();
/*     */     } catch (IOException localIOException) {
/* 777 */       throw new RuntimeException(localIOException);
/*     */     }
/* 779 */     if (this.jv != null) {
/* 780 */       this.jv.setEagerValidation(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   List<Object> getManifestDigests() {
/* 785 */     ensureInitialization();
/* 786 */     if (this.jv != null) {
/* 787 */       return this.jv.getManifestDigests();
/*     */     }
/* 789 */     return new ArrayList();
/*     */   }
/*     */   
/*     */   private native String[] getMetaInfEntryNames();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/jar/JarFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */