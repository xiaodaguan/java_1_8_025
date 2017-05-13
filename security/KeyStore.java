/*      */ package java.security;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.security.auth.DestroyFailedException;
/*      */ import javax.security.auth.Destroyable;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class KeyStore
/*      */ {
/*      */   private static final String KEYSTORE_TYPE = "keystore.type";
/*      */   private String type;
/*      */   private Provider provider;
/*      */   private KeyStoreSpi keyStoreSpi;
/*  200 */   private boolean initialized = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract interface LoadStoreParameter
/*      */   {
/*      */     public abstract KeyStore.ProtectionParameter getProtectionParameter();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract interface ProtectionParameter {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class PasswordProtection
/*      */     implements KeyStore.ProtectionParameter, Destroyable
/*      */   {
/*      */     private final char[] password;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final String protectionAlgorithm;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final AlgorithmParameterSpec protectionParameters;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  245 */     private volatile boolean destroyed = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public PasswordProtection(char[] paramArrayOfChar)
/*      */     {
/*  256 */       this.password = (paramArrayOfChar == null ? null : (char[])paramArrayOfChar.clone());
/*  257 */       this.protectionAlgorithm = null;
/*  258 */       this.protectionParameters = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public PasswordProtection(char[] paramArrayOfChar, String paramString, AlgorithmParameterSpec paramAlgorithmParameterSpec)
/*      */     {
/*  285 */       if (paramString == null) {
/*  286 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  288 */       this.password = (paramArrayOfChar == null ? null : (char[])paramArrayOfChar.clone());
/*  289 */       this.protectionAlgorithm = paramString;
/*  290 */       this.protectionParameters = paramAlgorithmParameterSpec;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getProtectionAlgorithm()
/*      */     {
/*  310 */       return this.protectionAlgorithm;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public AlgorithmParameterSpec getProtectionParameters()
/*      */     {
/*  322 */       return this.protectionParameters;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public synchronized char[] getPassword()
/*      */     {
/*  339 */       if (this.destroyed) {
/*  340 */         throw new IllegalStateException("password has been cleared");
/*      */       }
/*  342 */       return this.password;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public synchronized void destroy()
/*      */       throws DestroyFailedException
/*      */     {
/*  352 */       this.destroyed = true;
/*  353 */       if (this.password != null) {
/*  354 */         Arrays.fill(this.password, ' ');
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public synchronized boolean isDestroyed()
/*      */     {
/*  364 */       return this.destroyed;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class CallbackHandlerProtection
/*      */     implements KeyStore.ProtectionParameter
/*      */   {
/*      */     private final CallbackHandler handler;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public CallbackHandlerProtection(CallbackHandler paramCallbackHandler)
/*      */     {
/*  386 */       if (paramCallbackHandler == null) {
/*  387 */         throw new NullPointerException("handler must not be null");
/*      */       }
/*  389 */       this.handler = paramCallbackHandler;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public CallbackHandler getCallbackHandler()
/*      */     {
/*  398 */       return this.handler;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract interface Entry
/*      */   {
/*      */     public Set<Attribute> getAttributes()
/*      */     {
/*  420 */       return Collections.emptySet();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static abstract interface Attribute
/*      */     {
/*      */       public abstract String getName();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       public abstract String getValue();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final class PrivateKeyEntry
/*      */     implements KeyStore.Entry
/*      */   {
/*      */     private final PrivateKey privKey;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Certificate[] chain;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Set<KeyStore.Entry.Attribute> attributes;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public PrivateKeyEntry(PrivateKey paramPrivateKey, Certificate[] paramArrayOfCertificate)
/*      */     {
/*  484 */       this(paramPrivateKey, paramArrayOfCertificate, Collections.emptySet());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public PrivateKeyEntry(PrivateKey paramPrivateKey, Certificate[] paramArrayOfCertificate, Set<KeyStore.Entry.Attribute> paramSet)
/*      */     {
/*  516 */       if ((paramPrivateKey == null) || (paramArrayOfCertificate == null) || (paramSet == null)) {
/*  517 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  519 */       if (paramArrayOfCertificate.length == 0) {
/*  520 */         throw new IllegalArgumentException("invalid zero-length input chain");
/*      */       }
/*      */       
/*      */ 
/*  524 */       Certificate[] arrayOfCertificate = (Certificate[])paramArrayOfCertificate.clone();
/*  525 */       String str = arrayOfCertificate[0].getType();
/*  526 */       for (int i = 1; i < arrayOfCertificate.length; i++) {
/*  527 */         if (!str.equals(arrayOfCertificate[i].getType())) {
/*  528 */           throw new IllegalArgumentException("chain does not contain certificates of the same type");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  534 */       if (!paramPrivateKey.getAlgorithm().equals(arrayOfCertificate[0].getPublicKey().getAlgorithm())) {
/*  535 */         throw new IllegalArgumentException("private key algorithm does not match algorithm of public key in end entity certificate (at index 0)");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  540 */       this.privKey = paramPrivateKey;
/*      */       
/*  542 */       if (((arrayOfCertificate[0] instanceof X509Certificate)) && (!(arrayOfCertificate instanceof X509Certificate[])))
/*      */       {
/*      */ 
/*  545 */         this.chain = new X509Certificate[arrayOfCertificate.length];
/*  546 */         System.arraycopy(arrayOfCertificate, 0, this.chain, 0, arrayOfCertificate.length);
/*      */       }
/*      */       else {
/*  549 */         this.chain = arrayOfCertificate;
/*      */       }
/*      */       
/*      */ 
/*  553 */       this.attributes = Collections.unmodifiableSet(new HashSet(paramSet));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public PrivateKey getPrivateKey()
/*      */     {
/*  562 */       return this.privKey;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Certificate[] getCertificateChain()
/*      */     {
/*  577 */       return (Certificate[])this.chain.clone();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Certificate getCertificate()
/*      */     {
/*  591 */       return this.chain[0];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Set<KeyStore.Entry.Attribute> getAttributes()
/*      */     {
/*  604 */       return this.attributes;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  612 */       StringBuilder localStringBuilder = new StringBuilder();
/*  613 */       localStringBuilder.append("Private key entry and certificate chain with " + this.chain.length + " elements:\r\n");
/*      */       
/*  615 */       for (Certificate localCertificate : this.chain) {
/*  616 */         localStringBuilder.append(localCertificate);
/*  617 */         localStringBuilder.append("\r\n");
/*      */       }
/*  619 */       return localStringBuilder.toString();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final class SecretKeyEntry
/*      */     implements KeyStore.Entry
/*      */   {
/*      */     private final SecretKey sKey;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Set<KeyStore.Entry.Attribute> attributes;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public SecretKeyEntry(SecretKey paramSecretKey)
/*      */     {
/*  644 */       if (paramSecretKey == null) {
/*  645 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  647 */       this.sKey = paramSecretKey;
/*  648 */       this.attributes = Collections.emptySet();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public SecretKeyEntry(SecretKey paramSecretKey, Set<KeyStore.Entry.Attribute> paramSet)
/*      */     {
/*  668 */       if ((paramSecretKey == null) || (paramSet == null)) {
/*  669 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  671 */       this.sKey = paramSecretKey;
/*      */       
/*  673 */       this.attributes = Collections.unmodifiableSet(new HashSet(paramSet));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public SecretKey getSecretKey()
/*      */     {
/*  682 */       return this.sKey;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Set<KeyStore.Entry.Attribute> getAttributes()
/*      */     {
/*  695 */       return this.attributes;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  703 */       return "Secret key entry with algorithm " + this.sKey.getAlgorithm();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final class TrustedCertificateEntry
/*      */     implements KeyStore.Entry
/*      */   {
/*      */     private final Certificate cert;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Set<KeyStore.Entry.Attribute> attributes;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public TrustedCertificateEntry(Certificate paramCertificate)
/*      */     {
/*  728 */       if (paramCertificate == null) {
/*  729 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  731 */       this.cert = paramCertificate;
/*  732 */       this.attributes = Collections.emptySet();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public TrustedCertificateEntry(Certificate paramCertificate, Set<KeyStore.Entry.Attribute> paramSet)
/*      */     {
/*  752 */       if ((paramCertificate == null) || (paramSet == null)) {
/*  753 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  755 */       this.cert = paramCertificate;
/*      */       
/*  757 */       this.attributes = Collections.unmodifiableSet(new HashSet(paramSet));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Certificate getTrustedCertificate()
/*      */     {
/*  766 */       return this.cert;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Set<KeyStore.Entry.Attribute> getAttributes()
/*      */     {
/*  779 */       return this.attributes;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  787 */       return "Trusted certificate entry:\r\n" + this.cert.toString();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected KeyStore(KeyStoreSpi paramKeyStoreSpi, Provider paramProvider, String paramString)
/*      */   {
/*  801 */     this.keyStoreSpi = paramKeyStoreSpi;
/*  802 */     this.provider = paramProvider;
/*  803 */     this.type = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static KeyStore getInstance(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/*      */     try
/*      */     {
/*  836 */       Object[] arrayOfObject = Security.getImpl(paramString, "KeyStore", (String)null);
/*  837 */       return new KeyStore((KeyStoreSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*      */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  839 */       throw new KeyStoreException(paramString + " not found", localNoSuchAlgorithmException);
/*      */     } catch (NoSuchProviderException localNoSuchProviderException) {
/*  841 */       throw new KeyStoreException(paramString + " not found", localNoSuchProviderException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static KeyStore getInstance(String paramString1, String paramString2)
/*      */     throws KeyStoreException, NoSuchProviderException
/*      */   {
/*  881 */     if ((paramString2 == null) || (paramString2.length() == 0))
/*  882 */       throw new IllegalArgumentException("missing provider");
/*      */     try {
/*  884 */       Object[] arrayOfObject = Security.getImpl(paramString1, "KeyStore", paramString2);
/*  885 */       return new KeyStore((KeyStoreSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString1);
/*      */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  887 */       throw new KeyStoreException(paramString1 + " not found", localNoSuchAlgorithmException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static KeyStore getInstance(String paramString, Provider paramProvider)
/*      */     throws KeyStoreException
/*      */   {
/*  922 */     if (paramProvider == null)
/*  923 */       throw new IllegalArgumentException("missing provider");
/*      */     try {
/*  925 */       Object[] arrayOfObject = Security.getImpl(paramString, "KeyStore", paramProvider);
/*  926 */       return new KeyStore((KeyStoreSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*      */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  928 */       throw new KeyStoreException(paramString + " not found", localNoSuchAlgorithmException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String getDefaultType()
/*      */   {
/*  953 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public String run() {
/*  955 */         return Security.getProperty("keystore.type");
/*      */       }
/*      */     });
/*  958 */     if (str == null) {
/*  959 */       str = "jks";
/*      */     }
/*  961 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Provider getProvider()
/*      */   {
/*  971 */     return this.provider;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getType()
/*      */   {
/*  981 */     return this.type;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Key getKey(String paramString, char[] paramArrayOfChar)
/*      */     throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException
/*      */   {
/* 1008 */     if (!this.initialized) {
/* 1009 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1011 */     return this.keyStoreSpi.engineGetKey(paramString, paramArrayOfChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Certificate[] getCertificateChain(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1033 */     if (!this.initialized) {
/* 1034 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1036 */     return this.keyStoreSpi.engineGetCertificateChain(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Certificate getCertificate(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1066 */     if (!this.initialized) {
/* 1067 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1069 */     return this.keyStoreSpi.engineGetCertificate(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Date getCreationDate(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1086 */     if (!this.initialized) {
/* 1087 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1089 */     return this.keyStoreSpi.engineGetCreationDate(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setKeyEntry(String paramString, Key paramKey, char[] paramArrayOfChar, Certificate[] paramArrayOfCertificate)
/*      */     throws KeyStoreException
/*      */   {
/* 1119 */     if (!this.initialized) {
/* 1120 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1122 */     if (((paramKey instanceof PrivateKey)) && ((paramArrayOfCertificate == null) || (paramArrayOfCertificate.length == 0)))
/*      */     {
/* 1124 */       throw new IllegalArgumentException("Private key must be accompanied by certificate chain");
/*      */     }
/*      */     
/*      */ 
/* 1128 */     this.keyStoreSpi.engineSetKeyEntry(paramString, paramKey, paramArrayOfChar, paramArrayOfCertificate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setKeyEntry(String paramString, byte[] paramArrayOfByte, Certificate[] paramArrayOfCertificate)
/*      */     throws KeyStoreException
/*      */   {
/* 1159 */     if (!this.initialized) {
/* 1160 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1162 */     this.keyStoreSpi.engineSetKeyEntry(paramString, paramArrayOfByte, paramArrayOfCertificate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setCertificateEntry(String paramString, Certificate paramCertificate)
/*      */     throws KeyStoreException
/*      */   {
/* 1186 */     if (!this.initialized) {
/* 1187 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1189 */     this.keyStoreSpi.engineSetCertificateEntry(paramString, paramCertificate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void deleteEntry(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1203 */     if (!this.initialized) {
/* 1204 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1206 */     this.keyStoreSpi.engineDeleteEntry(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Enumeration<String> aliases()
/*      */     throws KeyStoreException
/*      */   {
/* 1220 */     if (!this.initialized) {
/* 1221 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1223 */     return this.keyStoreSpi.engineAliases();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean containsAlias(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1239 */     if (!this.initialized) {
/* 1240 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1242 */     return this.keyStoreSpi.engineContainsAlias(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int size()
/*      */     throws KeyStoreException
/*      */   {
/* 1256 */     if (!this.initialized) {
/* 1257 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1259 */     return this.keyStoreSpi.engineSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isKeyEntry(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1279 */     if (!this.initialized) {
/* 1280 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1282 */     return this.keyStoreSpi.engineIsKeyEntry(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isCertificateEntry(String paramString)
/*      */     throws KeyStoreException
/*      */   {
/* 1302 */     if (!this.initialized) {
/* 1303 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1305 */     return this.keyStoreSpi.engineIsCertificateEntry(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getCertificateAlias(Certificate paramCertificate)
/*      */     throws KeyStoreException
/*      */   {
/* 1337 */     if (!this.initialized) {
/* 1338 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1340 */     return this.keyStoreSpi.engineGetCertificateAlias(paramCertificate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void store(OutputStream paramOutputStream, char[] paramArrayOfChar)
/*      */     throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
/*      */   {
/* 1362 */     if (!this.initialized) {
/* 1363 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1365 */     this.keyStoreSpi.engineStore(paramOutputStream, paramArrayOfChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void store(LoadStoreParameter paramLoadStoreParameter)
/*      */     throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
/*      */   {
/* 1391 */     if (!this.initialized) {
/* 1392 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1394 */     this.keyStoreSpi.engineStore(paramLoadStoreParameter);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void load(InputStream paramInputStream, char[] paramArrayOfChar)
/*      */     throws IOException, NoSuchAlgorithmException, CertificateException
/*      */   {
/* 1433 */     this.keyStoreSpi.engineLoad(paramInputStream, paramArrayOfChar);
/* 1434 */     this.initialized = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void load(LoadStoreParameter paramLoadStoreParameter)
/*      */     throws IOException, NoSuchAlgorithmException, CertificateException
/*      */   {
/* 1467 */     this.keyStoreSpi.engineLoad(paramLoadStoreParameter);
/* 1468 */     this.initialized = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Entry getEntry(String paramString, ProtectionParameter paramProtectionParameter)
/*      */     throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException
/*      */   {
/* 1503 */     if (paramString == null) {
/* 1504 */       throw new NullPointerException("invalid null input");
/*      */     }
/* 1506 */     if (!this.initialized) {
/* 1507 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1509 */     return this.keyStoreSpi.engineGetEntry(paramString, paramProtectionParameter);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setEntry(String paramString, Entry paramEntry, ProtectionParameter paramProtectionParameter)
/*      */     throws KeyStoreException
/*      */   {
/* 1539 */     if ((paramString == null) || (paramEntry == null)) {
/* 1540 */       throw new NullPointerException("invalid null input");
/*      */     }
/* 1542 */     if (!this.initialized) {
/* 1543 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1545 */     this.keyStoreSpi.engineSetEntry(paramString, paramEntry, paramProtectionParameter);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean entryInstanceOf(String paramString, Class<? extends Entry> paramClass)
/*      */     throws KeyStoreException
/*      */   {
/* 1574 */     if ((paramString == null) || (paramClass == null)) {
/* 1575 */       throw new NullPointerException("invalid null input");
/*      */     }
/* 1577 */     if (!this.initialized) {
/* 1578 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1580 */     return this.keyStoreSpi.engineEntryInstanceOf(paramString, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract class Builder
/*      */   {
/*      */     static final int MAX_CALLBACK_TRIES = 3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract KeyStore getKeyStore()
/*      */       throws KeyStoreException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract KeyStore.ProtectionParameter getProtectionParameter(String paramString)
/*      */       throws KeyStoreException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static Builder newInstance(KeyStore paramKeyStore, final KeyStore.ProtectionParameter paramProtectionParameter)
/*      */     {
/* 1659 */       if ((paramKeyStore == null) || (paramProtectionParameter == null)) {
/* 1660 */         throw new NullPointerException();
/*      */       }
/* 1662 */       if (!paramKeyStore.initialized) {
/* 1663 */         throw new IllegalArgumentException("KeyStore not initialized");
/*      */       }
/* 1665 */       new Builder() {
/*      */         private volatile boolean getCalled;
/*      */         
/*      */         public KeyStore getKeyStore() {
/* 1669 */           this.getCalled = true;
/* 1670 */           return this.val$keyStore;
/*      */         }
/*      */         
/*      */         public KeyStore.ProtectionParameter getProtectionParameter(String paramAnonymousString)
/*      */         {
/* 1675 */           if (paramAnonymousString == null) {
/* 1676 */             throw new NullPointerException();
/*      */           }
/* 1678 */           if (!this.getCalled) {
/* 1679 */             throw new IllegalStateException("getKeyStore() must be called first");
/*      */           }
/*      */           
/* 1682 */           return paramProtectionParameter;
/*      */         }
/*      */       };
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static Builder newInstance(String paramString, Provider paramProvider, File paramFile, KeyStore.ProtectionParameter paramProtectionParameter)
/*      */     {
/* 1732 */       if ((paramString == null) || (paramFile == null) || (paramProtectionParameter == null)) {
/* 1733 */         throw new NullPointerException();
/*      */       }
/* 1735 */       if ((!(paramProtectionParameter instanceof KeyStore.PasswordProtection)) && (!(paramProtectionParameter instanceof KeyStore.CallbackHandlerProtection)))
/*      */       {
/* 1737 */         throw new IllegalArgumentException("Protection must be PasswordProtection or CallbackHandlerProtection");
/*      */       }
/*      */       
/*      */ 
/* 1741 */       if (!paramFile.isFile()) {
/* 1742 */         throw new IllegalArgumentException("File does not exist or it does not refer to a normal file: " + paramFile);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1747 */       return new FileBuilder(paramString, paramProvider, paramFile, paramProtectionParameter, AccessController.getContext());
/*      */     }
/*      */     
/*      */ 
/*      */     private static final class FileBuilder
/*      */       extends KeyStore.Builder
/*      */     {
/*      */       private final String type;
/*      */       
/*      */       private final Provider provider;
/*      */       private final File file;
/*      */       private KeyStore.ProtectionParameter protection;
/*      */       private KeyStore.ProtectionParameter keyProtection;
/*      */       private final AccessControlContext context;
/*      */       private KeyStore keyStore;
/*      */       private Throwable oldException;
/*      */       
/*      */       FileBuilder(String paramString, Provider paramProvider, File paramFile, KeyStore.ProtectionParameter paramProtectionParameter, AccessControlContext paramAccessControlContext)
/*      */       {
/* 1766 */         this.type = paramString;
/* 1767 */         this.provider = paramProvider;
/* 1768 */         this.file = paramFile;
/* 1769 */         this.protection = paramProtectionParameter;
/* 1770 */         this.context = paramAccessControlContext;
/*      */       }
/*      */       
/*      */       public synchronized KeyStore getKeyStore() throws KeyStoreException
/*      */       {
/* 1775 */         if (this.keyStore != null) {
/* 1776 */           return this.keyStore;
/*      */         }
/* 1778 */         if (this.oldException != null) {
/* 1779 */           throw new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
/*      */         }
/*      */         
/*      */ 
/* 1783 */         PrivilegedExceptionAction local1 = new PrivilegedExceptionAction()
/*      */         {
/*      */           public KeyStore run() throws Exception {
/* 1786 */             if (!(KeyStore.Builder.FileBuilder.this.protection instanceof KeyStore.CallbackHandlerProtection)) {
/* 1787 */               return run0();
/*      */             }
/*      */             
/*      */ 
/* 1791 */             int i = 0;
/*      */             do {
/* 1793 */               i++;
/*      */               try {
/* 1795 */                 return run0();
/*      */               } catch (IOException localIOException) {}
/* 1797 */             } while ((i < 3) && 
/* 1798 */               ((localIOException.getCause() instanceof UnrecoverableKeyException)));
/*      */             
/*      */ 
/* 1801 */             throw localIOException;
/*      */           }
/*      */           
/*      */           public KeyStore run0() throws Exception
/*      */           {
/*      */             KeyStore localKeyStore;
/* 1807 */             if (KeyStore.Builder.FileBuilder.this.provider == null) {
/* 1808 */               localKeyStore = KeyStore.getInstance(KeyStore.Builder.FileBuilder.this.type);
/*      */             } else {
/* 1810 */               localKeyStore = KeyStore.getInstance(KeyStore.Builder.FileBuilder.this.type, KeyStore.Builder.FileBuilder.this.provider);
/*      */             }
/* 1812 */             FileInputStream localFileInputStream = null;
/* 1813 */             char[] arrayOfChar = null;
/*      */             try {
/* 1815 */               localFileInputStream = new FileInputStream(KeyStore.Builder.FileBuilder.this.file);
/* 1816 */               Object localObject1; if ((KeyStore.Builder.FileBuilder.this.protection instanceof KeyStore.PasswordProtection))
/*      */               {
/* 1818 */                 arrayOfChar = ((KeyStore.PasswordProtection)KeyStore.Builder.FileBuilder.this.protection).getPassword();
/* 1819 */                 KeyStore.Builder.FileBuilder.this.keyProtection = KeyStore.Builder.FileBuilder.this.protection;
/*      */               }
/*      */               else
/*      */               {
/* 1823 */                 localObject1 = ((KeyStore.CallbackHandlerProtection)KeyStore.Builder.FileBuilder.this.protection).getCallbackHandler();
/*      */                 
/* 1825 */                 PasswordCallback localPasswordCallback = new PasswordCallback("Password for keystore " + KeyStore.Builder.FileBuilder.this.file.getName(), false);
/*      */                 
/* 1827 */                 ((CallbackHandler)localObject1).handle(new Callback[] { localPasswordCallback });
/* 1828 */                 arrayOfChar = localPasswordCallback.getPassword();
/* 1829 */                 if (arrayOfChar == null) {
/* 1830 */                   throw new KeyStoreException("No password provided");
/*      */                 }
/*      */                 
/* 1833 */                 localPasswordCallback.clearPassword();
/* 1834 */                 KeyStore.Builder.FileBuilder.this.keyProtection = new KeyStore.PasswordProtection(arrayOfChar);
/*      */               }
/* 1836 */               localKeyStore.load(localFileInputStream, arrayOfChar);
/* 1837 */               return localKeyStore;
/*      */             } finally {
/* 1839 */               if (localFileInputStream != null) {
/* 1840 */                 localFileInputStream.close();
/*      */               }
/*      */             }
/*      */           }
/*      */         };
/*      */         try {
/* 1846 */           this.keyStore = ((KeyStore)AccessController.doPrivileged(local1, this.context));
/* 1847 */           return this.keyStore;
/*      */         } catch (PrivilegedActionException localPrivilegedActionException) {
/* 1849 */           this.oldException = localPrivilegedActionException.getCause();
/* 1850 */           throw new KeyStoreException("KeyStore instantiation failed", this.oldException);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       public synchronized KeyStore.ProtectionParameter getProtectionParameter(String paramString)
/*      */       {
/* 1857 */         if (paramString == null) {
/* 1858 */           throw new NullPointerException();
/*      */         }
/* 1860 */         if (this.keyStore == null) {
/* 1861 */           throw new IllegalStateException("getKeyStore() must be called first");
/*      */         }
/*      */         
/* 1864 */         return this.keyProtection;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static Builder newInstance(final String paramString, Provider paramProvider, final KeyStore.ProtectionParameter paramProtectionParameter)
/*      */     {
/* 1897 */       if ((paramString == null) || (paramProtectionParameter == null)) {
/* 1898 */         throw new NullPointerException();
/*      */       }
/* 1900 */       final AccessControlContext localAccessControlContext = AccessController.getContext();
/* 1901 */       new Builder()
/*      */       {
/*      */         private volatile boolean getCalled;
/*      */         private IOException oldException;
/* 1905 */         private final PrivilegedExceptionAction<KeyStore> action = new PrivilegedExceptionAction()
/*      */         {
/*      */           public KeyStore run() throws Exception
/*      */           {
/*      */             KeyStore localKeyStore;
/* 1910 */             if (KeyStore.Builder.2.this.val$provider == null) {
/* 1911 */               localKeyStore = KeyStore.getInstance(KeyStore.Builder.2.this.val$type);
/*      */             } else {
/* 1913 */               localKeyStore = KeyStore.getInstance(KeyStore.Builder.2.this.val$type, KeyStore.Builder.2.this.val$provider);
/*      */             }
/* 1915 */             KeyStore.SimpleLoadStoreParameter localSimpleLoadStoreParameter = new KeyStore.SimpleLoadStoreParameter(KeyStore.Builder.2.this.val$protection);
/* 1916 */             if (!(KeyStore.Builder.2.this.val$protection instanceof KeyStore.CallbackHandlerProtection)) {
/* 1917 */               localKeyStore.load(localSimpleLoadStoreParameter);
/*      */             }
/*      */             else
/*      */             {
/* 1921 */               int i = 0;
/*      */               for (;;) {
/* 1923 */                 i++;
/*      */                 try {
/* 1925 */                   localKeyStore.load(localSimpleLoadStoreParameter);
/*      */                 }
/*      */                 catch (IOException localIOException) {
/* 1928 */                   if ((localIOException.getCause() instanceof UnrecoverableKeyException)) {
/* 1929 */                     if (i >= 3)
/*      */                     {
/*      */ 
/* 1932 */                       KeyStore.Builder.2.this.oldException = localIOException;
/*      */                     }
/*      */                   } else
/* 1935 */                     throw localIOException;
/*      */                 }
/*      */               }
/*      */             }
/* 1939 */             KeyStore.Builder.2.this.getCalled = true;
/* 1940 */             return localKeyStore;
/*      */           }
/*      */         };
/*      */         
/*      */         public synchronized KeyStore getKeyStore() throws KeyStoreException
/*      */         {
/* 1946 */           if (this.oldException != null) {
/* 1947 */             throw new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
/*      */           }
/*      */           
/*      */           try
/*      */           {
/* 1952 */             return (KeyStore)AccessController.doPrivileged(this.action, localAccessControlContext);
/*      */           } catch (PrivilegedActionException localPrivilegedActionException) {
/* 1954 */             Throwable localThrowable = localPrivilegedActionException.getCause();
/* 1955 */             throw new KeyStoreException("KeyStore instantiation failed", localThrowable);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */         public KeyStore.ProtectionParameter getProtectionParameter(String paramAnonymousString)
/*      */         {
/* 1962 */           if (paramAnonymousString == null) {
/* 1963 */             throw new NullPointerException();
/*      */           }
/* 1965 */           if (!this.getCalled) {
/* 1966 */             throw new IllegalStateException("getKeyStore() must be called first");
/*      */           }
/*      */           
/* 1969 */           return paramProtectionParameter;
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */   
/*      */   static class SimpleLoadStoreParameter implements KeyStore.LoadStoreParameter
/*      */   {
/*      */     private final KeyStore.ProtectionParameter protection;
/*      */     
/*      */     SimpleLoadStoreParameter(KeyStore.ProtectionParameter paramProtectionParameter)
/*      */     {
/* 1981 */       this.protection = paramProtectionParameter;
/*      */     }
/*      */     
/*      */     public KeyStore.ProtectionParameter getProtectionParameter() {
/* 1985 */       return this.protection;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/KeyStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */