/*      */ package java.security;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.crypto.BadPaddingException;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.IllegalBlockSizeException;
/*      */ import javax.crypto.NoSuchPaddingException;
/*      */ import sun.security.jca.GetInstance;
/*      */ import sun.security.jca.GetInstance.Instance;
/*      */ import sun.security.jca.ServiceId;
/*      */ import sun.security.util.Debug;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Signature
/*      */   extends SignatureSpi
/*      */ {
/*  122 */   private static final Debug debug = Debug.getInstance("jca", "Signature");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String algorithm;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Provider provider;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final int UNINITIALIZED = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final int SIGN = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final int VERIFY = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  155 */   protected int state = 0;
/*      */   
/*      */ 
/*      */   private static final String RSA_SIGNATURE = "NONEwithRSA";
/*      */   
/*      */ 
/*      */   private static final String RSA_CIPHER = "RSA/ECB/PKCS1Padding";
/*      */   
/*      */ 
/*      */ 
/*      */   protected Signature(String paramString)
/*      */   {
/*  167 */     this.algorithm = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  177 */   private static final List<ServiceId> rsaIds = Arrays.asList(new ServiceId[] { new ServiceId("Signature", "NONEwithRSA"), new ServiceId("Cipher", "RSA/ECB/PKCS1Padding"), new ServiceId("Cipher", "RSA/ECB"), new ServiceId("Cipher", "RSA//PKCS1Padding"), new ServiceId("Cipher", "RSA") });
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Signature getInstance(String paramString)
/*      */     throws NoSuchAlgorithmException
/*      */   {
/*      */     List localList;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  217 */     if (paramString.equalsIgnoreCase("NONEwithRSA")) {
/*  218 */       localList = GetInstance.getServices(rsaIds);
/*      */     } else {
/*  220 */       localList = GetInstance.getServices("Signature", paramString);
/*      */     }
/*  222 */     Iterator localIterator = localList.iterator();
/*  223 */     if (!localIterator.hasNext()) {
/*  224 */       throw new NoSuchAlgorithmException(paramString + " Signature not available");
/*      */     }
/*      */     
/*      */     NoSuchAlgorithmException localNoSuchAlgorithmException1;
/*      */     do
/*      */     {
/*  230 */       Provider.Service localService = (Provider.Service)localIterator.next();
/*  231 */       if (isSpi(localService)) {
/*  232 */         return new Delegate(localService, localIterator, paramString);
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  237 */         GetInstance.Instance localInstance = GetInstance.getInstance(localService, SignatureSpi.class);
/*  238 */         return getInstance(localInstance, paramString);
/*      */       } catch (NoSuchAlgorithmException localNoSuchAlgorithmException2) {
/*  240 */         localNoSuchAlgorithmException1 = localNoSuchAlgorithmException2;
/*      */       }
/*      */       
/*  243 */     } while (localIterator.hasNext());
/*  244 */     throw localNoSuchAlgorithmException1;
/*      */   }
/*      */   
/*      */   private static Signature getInstance(GetInstance.Instance paramInstance, String paramString) {
/*      */     Object localObject;
/*  249 */     if ((paramInstance.impl instanceof Signature)) {
/*  250 */       localObject = (Signature)paramInstance.impl;
/*  251 */       ((Signature)localObject).algorithm = paramString;
/*      */     } else {
/*  253 */       SignatureSpi localSignatureSpi = (SignatureSpi)paramInstance.impl;
/*  254 */       localObject = new Delegate(localSignatureSpi, paramString);
/*      */     }
/*  256 */     ((Signature)localObject).provider = paramInstance.provider;
/*  257 */     return (Signature)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  263 */   private static final Map<String, Boolean> signatureInfo = new ConcurrentHashMap();
/*  264 */   static { Boolean localBoolean = Boolean.TRUE;
/*      */     
/*  266 */     signatureInfo.put("sun.security.provider.DSA$RawDSA", localBoolean);
/*  267 */     signatureInfo.put("sun.security.provider.DSA$SHA1withDSA", localBoolean);
/*  268 */     signatureInfo.put("sun.security.rsa.RSASignature$MD2withRSA", localBoolean);
/*  269 */     signatureInfo.put("sun.security.rsa.RSASignature$MD5withRSA", localBoolean);
/*  270 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA1withRSA", localBoolean);
/*  271 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA256withRSA", localBoolean);
/*  272 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA384withRSA", localBoolean);
/*  273 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA512withRSA", localBoolean);
/*  274 */     signatureInfo.put("com.sun.net.ssl.internal.ssl.RSASignature", localBoolean);
/*  275 */     signatureInfo.put("sun.security.pkcs11.P11Signature", localBoolean);
/*      */   }
/*      */   
/*      */   private static boolean isSpi(Provider.Service paramService) {
/*  279 */     if (paramService.getType().equals("Cipher"))
/*      */     {
/*  281 */       return true;
/*      */     }
/*  283 */     String str = paramService.getClassName();
/*  284 */     Boolean localBoolean = (Boolean)signatureInfo.get(str);
/*  285 */     if (localBoolean == null) {
/*      */       try {
/*  287 */         Object localObject = paramService.newInstance(null);
/*      */         
/*      */ 
/*      */ 
/*  291 */         boolean bool = ((localObject instanceof SignatureSpi)) && (!(localObject instanceof Signature));
/*      */         
/*  293 */         if ((debug != null) && (!bool)) {
/*  294 */           debug.println("Not a SignatureSpi " + str);
/*  295 */           debug.println("Delayed provider selection may not be available for algorithm " + paramService
/*  296 */             .getAlgorithm());
/*      */         }
/*  298 */         localBoolean = Boolean.valueOf(bool);
/*  299 */         signatureInfo.put(str, localBoolean);
/*      */       }
/*      */       catch (Exception localException) {
/*  302 */         return false;
/*      */       }
/*      */     }
/*  305 */     return localBoolean.booleanValue();
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
/*      */   public static Signature getInstance(String paramString1, String paramString2)
/*      */     throws NoSuchAlgorithmException, NoSuchProviderException
/*      */   {
/*  344 */     if (paramString1.equalsIgnoreCase("NONEwithRSA"))
/*      */     {
/*  346 */       if ((paramString2 == null) || (paramString2.length() == 0)) {
/*  347 */         throw new IllegalArgumentException("missing provider");
/*      */       }
/*  349 */       localObject = Security.getProvider(paramString2);
/*  350 */       if (localObject == null) {
/*  351 */         throw new NoSuchProviderException("no such provider: " + paramString2);
/*      */       }
/*      */       
/*  354 */       return getInstanceRSA((Provider)localObject);
/*      */     }
/*      */     
/*  357 */     Object localObject = GetInstance.getInstance("Signature", SignatureSpi.class, paramString1, paramString2);
/*  358 */     return getInstance((GetInstance.Instance)localObject, paramString1);
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
/*      */   public static Signature getInstance(String paramString, Provider paramProvider)
/*      */     throws NoSuchAlgorithmException
/*      */   {
/*  392 */     if (paramString.equalsIgnoreCase("NONEwithRSA"))
/*      */     {
/*  394 */       if (paramProvider == null) {
/*  395 */         throw new IllegalArgumentException("missing provider");
/*      */       }
/*  397 */       return getInstanceRSA(paramProvider);
/*      */     }
/*      */     
/*  400 */     GetInstance.Instance localInstance = GetInstance.getInstance("Signature", SignatureSpi.class, paramString, paramProvider);
/*  401 */     return getInstance(localInstance, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static Signature getInstanceRSA(Provider paramProvider)
/*      */     throws NoSuchAlgorithmException
/*      */   {
/*  409 */     Provider.Service localService = paramProvider.getService("Signature", "NONEwithRSA");
/*  410 */     Object localObject; if (localService != null) {
/*  411 */       localObject = GetInstance.getInstance(localService, SignatureSpi.class);
/*  412 */       return getInstance((GetInstance.Instance)localObject, "NONEwithRSA");
/*      */     }
/*      */     try
/*      */     {
/*  416 */       localObject = Cipher.getInstance("RSA/ECB/PKCS1Padding", paramProvider);
/*  417 */       return new Delegate(new CipherAdapter((Cipher)localObject), "NONEwithRSA");
/*      */ 
/*      */     }
/*      */     catch (GeneralSecurityException localGeneralSecurityException)
/*      */     {
/*  422 */       throw new NoSuchAlgorithmException("no such algorithm: NONEwithRSA for provider " + paramProvider.getName(), localGeneralSecurityException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Provider getProvider()
/*      */   {
/*  432 */     chooseFirstProvider();
/*  433 */     return this.provider;
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
/*      */   public final void initVerify(PublicKey paramPublicKey)
/*      */     throws InvalidKeyException
/*      */   {
/*  452 */     engineInitVerify(paramPublicKey);
/*  453 */     this.state = 3;
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
/*      */   public final void initVerify(Certificate paramCertificate)
/*      */     throws InvalidKeyException
/*      */   {
/*  479 */     if ((paramCertificate instanceof X509Certificate))
/*      */     {
/*      */ 
/*      */ 
/*  483 */       localObject = (X509Certificate)paramCertificate;
/*  484 */       Set localSet = ((X509Certificate)localObject).getCriticalExtensionOIDs();
/*      */       
/*  486 */       if ((localSet != null) && (!localSet.isEmpty()) && 
/*  487 */         (localSet.contains("2.5.29.15"))) {
/*  488 */         boolean[] arrayOfBoolean = ((X509Certificate)localObject).getKeyUsage();
/*      */         
/*  490 */         if ((arrayOfBoolean != null) && (arrayOfBoolean[0] == 0)) {
/*  491 */           throw new InvalidKeyException("Wrong key usage");
/*      */         }
/*      */       }
/*      */     }
/*  495 */     Object localObject = paramCertificate.getPublicKey();
/*  496 */     engineInitVerify((PublicKey)localObject);
/*  497 */     this.state = 3;
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
/*      */   public final void initSign(PrivateKey paramPrivateKey)
/*      */     throws InvalidKeyException
/*      */   {
/*  512 */     engineInitSign(paramPrivateKey);
/*  513 */     this.state = 2;
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
/*      */   public final void initSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom)
/*      */     throws InvalidKeyException
/*      */   {
/*  530 */     engineInitSign(paramPrivateKey, paramSecureRandom);
/*  531 */     this.state = 2;
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
/*      */   public final byte[] sign()
/*      */     throws SignatureException
/*      */   {
/*  553 */     if (this.state == 2) {
/*  554 */       return engineSign();
/*      */     }
/*  556 */     throw new SignatureException("object not initialized for signing");
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
/*      */   public final int sign(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SignatureException
/*      */   {
/*  590 */     if (paramArrayOfByte == null) {
/*  591 */       throw new IllegalArgumentException("No output buffer given");
/*      */     }
/*  593 */     if ((paramInt1 < 0) || (paramInt2 < 0)) {
/*  594 */       throw new IllegalArgumentException("offset or len is less than 0");
/*      */     }
/*  596 */     if (paramArrayOfByte.length - paramInt1 < paramInt2) {
/*  597 */       throw new IllegalArgumentException("Output buffer too small for specified offset and length");
/*      */     }
/*      */     
/*  600 */     if (this.state != 2) {
/*  601 */       throw new SignatureException("object not initialized for signing");
/*      */     }
/*      */     
/*  604 */     return engineSign(paramArrayOfByte, paramInt1, paramInt2);
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
/*      */   public final boolean verify(byte[] paramArrayOfByte)
/*      */     throws SignatureException
/*      */   {
/*  626 */     if (this.state == 3) {
/*  627 */       return engineVerify(paramArrayOfByte);
/*      */     }
/*  629 */     throw new SignatureException("object not initialized for verification");
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
/*      */   public final boolean verify(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SignatureException
/*      */   {
/*  663 */     if (this.state == 3) {
/*  664 */       if (paramArrayOfByte == null) {
/*  665 */         throw new IllegalArgumentException("signature is null");
/*      */       }
/*  667 */       if ((paramInt1 < 0) || (paramInt2 < 0)) {
/*  668 */         throw new IllegalArgumentException("offset or length is less than 0");
/*      */       }
/*      */       
/*  671 */       if (paramArrayOfByte.length - paramInt1 < paramInt2) {
/*  672 */         throw new IllegalArgumentException("signature too small for specified offset and length");
/*      */       }
/*      */       
/*      */ 
/*  676 */       return engineVerify(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*  678 */     throw new SignatureException("object not initialized for verification");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void update(byte paramByte)
/*      */     throws SignatureException
/*      */   {
/*  691 */     if ((this.state == 3) || (this.state == 2)) {
/*  692 */       engineUpdate(paramByte);
/*      */     } else {
/*  694 */       throw new SignatureException("object not initialized for signature or verification");
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
/*      */   public final void update(byte[] paramArrayOfByte)
/*      */     throws SignatureException
/*      */   {
/*  709 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public final void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SignatureException
/*      */   {
/*  725 */     if ((this.state == 2) || (this.state == 3)) {
/*  726 */       if (paramArrayOfByte == null) {
/*  727 */         throw new IllegalArgumentException("data is null");
/*      */       }
/*  729 */       if ((paramInt1 < 0) || (paramInt2 < 0)) {
/*  730 */         throw new IllegalArgumentException("off or len is less than 0");
/*      */       }
/*  732 */       if (paramArrayOfByte.length - paramInt1 < paramInt2) {
/*  733 */         throw new IllegalArgumentException("data too small for specified offset and length");
/*      */       }
/*      */       
/*  736 */       engineUpdate(paramArrayOfByte, paramInt1, paramInt2);
/*      */     } else {
/*  738 */       throw new SignatureException("object not initialized for signature or verification");
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
/*      */   public final void update(ByteBuffer paramByteBuffer)
/*      */     throws SignatureException
/*      */   {
/*  757 */     if ((this.state != 2) && (this.state != 3)) {
/*  758 */       throw new SignatureException("object not initialized for signature or verification");
/*      */     }
/*      */     
/*  761 */     if (paramByteBuffer == null) {
/*  762 */       throw new NullPointerException();
/*      */     }
/*  764 */     engineUpdate(paramByteBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getAlgorithm()
/*      */   {
/*  773 */     return this.algorithm;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  784 */     String str = "";
/*  785 */     switch (this.state) {
/*      */     case 0: 
/*  787 */       str = "<not initialized>";
/*  788 */       break;
/*      */     case 3: 
/*  790 */       str = "<initialized for verifying>";
/*  791 */       break;
/*      */     case 2: 
/*  793 */       str = "<initialized for signing>";
/*      */     }
/*      */     
/*  796 */     return "Signature object: " + getAlgorithm() + str;
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
/*      */   @Deprecated
/*      */   public final void setParameter(String paramString, Object paramObject)
/*      */     throws InvalidParameterException
/*      */   {
/*  827 */     engineSetParameter(paramString, paramObject);
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
/*      */   public final void setParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec)
/*      */     throws InvalidAlgorithmParameterException
/*      */   {
/*  842 */     engineSetParameter(paramAlgorithmParameterSpec);
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
/*      */   public final AlgorithmParameters getParameters()
/*      */   {
/*  861 */     return engineGetParameters();
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
/*      */   @Deprecated
/*      */   public final Object getParameter(String paramString)
/*      */     throws InvalidParameterException
/*      */   {
/*  890 */     return engineGetParameter(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */     throws CloneNotSupportedException
/*      */   {
/*  902 */     if ((this instanceof Cloneable)) {
/*  903 */       return super.clone();
/*      */     }
/*  905 */     throw new CloneNotSupportedException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void chooseFirstProvider() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class Delegate
/*      */     extends Signature
/*      */   {
/*      */     private SignatureSpi sigSpi;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Object lock;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private Provider.Service firstService;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private Iterator<Provider.Service> serviceIterator;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Delegate(SignatureSpi paramSignatureSpi, String paramString)
/*      */     {
/*  943 */       super();
/*  944 */       this.sigSpi = paramSignatureSpi;
/*  945 */       this.lock = null;
/*      */     }
/*      */     
/*      */ 
/*      */     Delegate(Provider.Service paramService, Iterator<Provider.Service> paramIterator, String paramString)
/*      */     {
/*  951 */       super();
/*  952 */       this.firstService = paramService;
/*  953 */       this.serviceIterator = paramIterator;
/*  954 */       this.lock = new Object();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Object clone()
/*      */       throws CloneNotSupportedException
/*      */     {
/*  966 */       chooseFirstProvider();
/*  967 */       if ((this.sigSpi instanceof Cloneable)) {
/*  968 */         SignatureSpi localSignatureSpi = (SignatureSpi)this.sigSpi.clone();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  973 */         Delegate localDelegate = new Delegate(localSignatureSpi, this.algorithm);
/*  974 */         localDelegate.provider = this.provider;
/*  975 */         return localDelegate;
/*      */       }
/*  977 */       throw new CloneNotSupportedException();
/*      */     }
/*      */     
/*      */     private static SignatureSpi newInstance(Provider.Service paramService)
/*      */       throws NoSuchAlgorithmException
/*      */     {
/*  983 */       if (paramService.getType().equals("Cipher")) {
/*      */         try
/*      */         {
/*  986 */           Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", paramService.getProvider());
/*  987 */           return new Signature.CipherAdapter(localCipher);
/*      */         } catch (NoSuchPaddingException localNoSuchPaddingException) {
/*  989 */           throw new NoSuchAlgorithmException(localNoSuchPaddingException);
/*      */         }
/*      */       }
/*  992 */       Object localObject = paramService.newInstance(null);
/*  993 */       if (!(localObject instanceof SignatureSpi))
/*      */       {
/*  995 */         throw new NoSuchAlgorithmException("Not a SignatureSpi: " + localObject.getClass().getName());
/*      */       }
/*  997 */       return (SignatureSpi)localObject;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1002 */     private static int warnCount = 10;
/*      */     
/*      */     private static final int I_PUB = 1;
/*      */     private static final int I_PRIV = 2;
/*      */     private static final int I_PRIV_SR = 3;
/*      */     
/*      */     void chooseFirstProvider()
/*      */     {
/* 1010 */       if (this.sigSpi != null) {
/* 1011 */         return;
/*      */       }
/* 1013 */       synchronized (this.lock) {
/* 1014 */         if (this.sigSpi != null) {
/* 1015 */           return;
/*      */         }
/* 1017 */         if (Signature.debug != null) {
/* 1018 */           int i = --warnCount;
/* 1019 */           if (i >= 0) {
/* 1020 */             Signature.debug.println("Signature.init() not first method called, disabling delayed provider selection");
/*      */             
/* 1022 */             if (i == 0) {
/* 1023 */               Signature.debug.println("Further warnings of this type will be suppressed");
/*      */             }
/*      */             
/* 1026 */             new Exception("Call trace").printStackTrace();
/*      */           }
/*      */         }
/* 1029 */         Object localObject1 = null;
/* 1030 */         while ((this.firstService != null) || (this.serviceIterator.hasNext()))
/*      */         {
/* 1032 */           if (this.firstService != null) {
/* 1033 */             localObject2 = this.firstService;
/* 1034 */             this.firstService = null;
/*      */           } else {
/* 1036 */             localObject2 = (Provider.Service)this.serviceIterator.next();
/*      */           }
/* 1038 */           if (Signature.isSpi((Provider.Service)localObject2))
/*      */           {
/*      */             try
/*      */             {
/* 1042 */               this.sigSpi = newInstance((Provider.Service)localObject2);
/* 1043 */               this.provider = ((Provider.Service)localObject2).getProvider();
/*      */               
/* 1045 */               this.firstService = null;
/* 1046 */               this.serviceIterator = null;
/* 1047 */               return;
/*      */             } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 1049 */               localObject1 = localNoSuchAlgorithmException;
/*      */             } }
/*      */         }
/* 1052 */         Object localObject2 = new ProviderException("Could not construct SignatureSpi instance");
/*      */         
/* 1054 */         if (localObject1 != null) {
/* 1055 */           ((ProviderException)localObject2).initCause((Throwable)localObject1);
/*      */         }
/* 1057 */         throw ((Throwable)localObject2);
/*      */       }
/*      */     }
/*      */     
/*      */     private void chooseProvider(int paramInt, Key paramKey, SecureRandom paramSecureRandom) throws InvalidKeyException
/*      */     {
/* 1063 */       synchronized (this.lock) {
/* 1064 */         if (this.sigSpi != null) {
/* 1065 */           init(this.sigSpi, paramInt, paramKey, paramSecureRandom);
/* 1066 */           return;
/*      */         }
/* 1068 */         Object localObject1 = null;
/* 1069 */         while ((this.firstService != null) || (this.serviceIterator.hasNext()))
/*      */         {
/* 1071 */           if (this.firstService != null) {
/* 1072 */             localObject2 = this.firstService;
/* 1073 */             this.firstService = null;
/*      */           } else {
/* 1075 */             localObject2 = (Provider.Service)this.serviceIterator.next();
/*      */           }
/*      */           
/* 1078 */           if ((((Provider.Service)localObject2).supportsParameter(paramKey)) && 
/*      */           
/*      */ 
/*      */ 
/* 1082 */             (Signature.isSpi((Provider.Service)localObject2)))
/*      */           {
/*      */             try
/*      */             {
/* 1086 */               SignatureSpi localSignatureSpi = newInstance((Provider.Service)localObject2);
/* 1087 */               init(localSignatureSpi, paramInt, paramKey, paramSecureRandom);
/* 1088 */               this.provider = ((Provider.Service)localObject2).getProvider();
/* 1089 */               this.sigSpi = localSignatureSpi;
/* 1090 */               this.firstService = null;
/* 1091 */               this.serviceIterator = null;
/* 1092 */               return;
/*      */ 
/*      */             }
/*      */             catch (Exception localException)
/*      */             {
/* 1097 */               if (localObject1 == null) {
/* 1098 */                 localObject1 = localException;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1103 */         if ((localObject1 instanceof InvalidKeyException)) {
/* 1104 */           throw ((InvalidKeyException)localObject1);
/*      */         }
/* 1106 */         if ((localObject1 instanceof RuntimeException)) {
/* 1107 */           throw ((RuntimeException)localObject1);
/*      */         }
/* 1109 */         Object localObject2 = paramKey != null ? paramKey.getClass().getName() : "(null)";
/* 1110 */         throw new InvalidKeyException("No installed provider supports this key: " + (String)localObject2, (Throwable)localObject1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void init(SignatureSpi paramSignatureSpi, int paramInt, Key paramKey, SecureRandom paramSecureRandom)
/*      */       throws InvalidKeyException
/*      */     {
/* 1122 */       switch (paramInt) {
/*      */       case 1: 
/* 1124 */         paramSignatureSpi.engineInitVerify((PublicKey)paramKey);
/* 1125 */         break;
/*      */       case 2: 
/* 1127 */         paramSignatureSpi.engineInitSign((PrivateKey)paramKey);
/* 1128 */         break;
/*      */       case 3: 
/* 1130 */         paramSignatureSpi.engineInitSign((PrivateKey)paramKey, paramSecureRandom);
/* 1131 */         break;
/*      */       default: 
/* 1133 */         throw new AssertionError("Internal error: " + paramInt);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException
/*      */     {
/* 1139 */       if (this.sigSpi != null) {
/* 1140 */         this.sigSpi.engineInitVerify(paramPublicKey);
/*      */       } else {
/* 1142 */         chooseProvider(1, paramPublicKey, null);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException
/*      */     {
/* 1148 */       if (this.sigSpi != null) {
/* 1149 */         this.sigSpi.engineInitSign(paramPrivateKey);
/*      */       } else {
/* 1151 */         chooseProvider(2, paramPrivateKey, null);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException
/*      */     {
/* 1157 */       if (this.sigSpi != null) {
/* 1158 */         this.sigSpi.engineInitSign(paramPrivateKey, paramSecureRandom);
/*      */       } else {
/* 1160 */         chooseProvider(3, paramPrivateKey, paramSecureRandom);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void engineUpdate(byte paramByte) throws SignatureException {
/* 1165 */       chooseFirstProvider();
/* 1166 */       this.sigSpi.engineUpdate(paramByte);
/*      */     }
/*      */     
/*      */     protected void engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws SignatureException
/*      */     {
/* 1171 */       chooseFirstProvider();
/* 1172 */       this.sigSpi.engineUpdate(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */     protected void engineUpdate(ByteBuffer paramByteBuffer) {
/* 1176 */       chooseFirstProvider();
/* 1177 */       this.sigSpi.engineUpdate(paramByteBuffer);
/*      */     }
/*      */     
/*      */     protected byte[] engineSign() throws SignatureException {
/* 1181 */       chooseFirstProvider();
/* 1182 */       return this.sigSpi.engineSign();
/*      */     }
/*      */     
/*      */     protected int engineSign(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws SignatureException
/*      */     {
/* 1187 */       chooseFirstProvider();
/* 1188 */       return this.sigSpi.engineSign(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */     protected boolean engineVerify(byte[] paramArrayOfByte) throws SignatureException
/*      */     {
/* 1193 */       chooseFirstProvider();
/* 1194 */       return this.sigSpi.engineVerify(paramArrayOfByte);
/*      */     }
/*      */     
/*      */     protected boolean engineVerify(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws SignatureException
/*      */     {
/* 1199 */       chooseFirstProvider();
/* 1200 */       return this.sigSpi.engineVerify(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */     protected void engineSetParameter(String paramString, Object paramObject) throws InvalidParameterException
/*      */     {
/* 1205 */       chooseFirstProvider();
/* 1206 */       this.sigSpi.engineSetParameter(paramString, paramObject);
/*      */     }
/*      */     
/*      */     protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException
/*      */     {
/* 1211 */       chooseFirstProvider();
/* 1212 */       this.sigSpi.engineSetParameter(paramAlgorithmParameterSpec);
/*      */     }
/*      */     
/*      */     protected Object engineGetParameter(String paramString) throws InvalidParameterException
/*      */     {
/* 1217 */       chooseFirstProvider();
/* 1218 */       return this.sigSpi.engineGetParameter(paramString);
/*      */     }
/*      */     
/*      */     protected AlgorithmParameters engineGetParameters() {
/* 1222 */       chooseFirstProvider();
/* 1223 */       return this.sigSpi.engineGetParameters();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class CipherAdapter
/*      */     extends SignatureSpi
/*      */   {
/*      */     private final Cipher cipher;
/*      */     private ByteArrayOutputStream data;
/*      */     
/*      */     CipherAdapter(Cipher paramCipher)
/*      */     {
/* 1236 */       this.cipher = paramCipher;
/*      */     }
/*      */     
/*      */     protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException
/*      */     {
/* 1241 */       this.cipher.init(2, paramPublicKey);
/* 1242 */       if (this.data == null) {
/* 1243 */         this.data = new ByteArrayOutputStream(128);
/*      */       } else {
/* 1245 */         this.data.reset();
/*      */       }
/*      */     }
/*      */     
/*      */     protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException
/*      */     {
/* 1251 */       this.cipher.init(1, paramPrivateKey);
/* 1252 */       this.data = null;
/*      */     }
/*      */     
/*      */     protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException
/*      */     {
/* 1257 */       this.cipher.init(1, paramPrivateKey, paramSecureRandom);
/* 1258 */       this.data = null;
/*      */     }
/*      */     
/*      */     protected void engineUpdate(byte paramByte) throws SignatureException {
/* 1262 */       engineUpdate(new byte[] { paramByte }, 0, 1);
/*      */     }
/*      */     
/*      */     protected void engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws SignatureException
/*      */     {
/* 1267 */       if (this.data != null) {
/* 1268 */         this.data.write(paramArrayOfByte, paramInt1, paramInt2);
/* 1269 */         return;
/*      */       }
/* 1271 */       byte[] arrayOfByte = this.cipher.update(paramArrayOfByte, paramInt1, paramInt2);
/* 1272 */       if ((arrayOfByte != null) && (arrayOfByte.length != 0)) {
/* 1273 */         throw new SignatureException("Cipher unexpectedly returned data");
/*      */       }
/*      */     }
/*      */     
/*      */     protected byte[] engineSign() throws SignatureException
/*      */     {
/*      */       try {
/* 1280 */         return this.cipher.doFinal();
/*      */       } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
/* 1282 */         throw new SignatureException("doFinal() failed", localIllegalBlockSizeException);
/*      */       } catch (BadPaddingException localBadPaddingException) {
/* 1284 */         throw new SignatureException("doFinal() failed", localBadPaddingException);
/*      */       }
/*      */     }
/*      */     
/*      */     protected boolean engineVerify(byte[] paramArrayOfByte) throws SignatureException
/*      */     {
/*      */       try {
/* 1291 */         byte[] arrayOfByte1 = this.cipher.doFinal(paramArrayOfByte);
/* 1292 */         byte[] arrayOfByte2 = this.data.toByteArray();
/* 1293 */         this.data.reset();
/* 1294 */         return Arrays.equals(arrayOfByte1, arrayOfByte2);
/*      */       }
/*      */       catch (BadPaddingException localBadPaddingException)
/*      */       {
/* 1298 */         return false;
/*      */       } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
/* 1300 */         throw new SignatureException("doFinal() failed", localIllegalBlockSizeException);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void engineSetParameter(String paramString, Object paramObject) throws InvalidParameterException
/*      */     {
/* 1306 */       throw new InvalidParameterException("Parameters not supported");
/*      */     }
/*      */     
/*      */     protected Object engineGetParameter(String paramString) throws InvalidParameterException
/*      */     {
/* 1311 */       throw new InvalidParameterException("Parameters not supported");
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Signature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */