/*     */ package java.security;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.jca.GetInstance.Instance;
/*     */ import sun.security.jca.ProviderList;
/*     */ import sun.security.jca.Providers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecureRandom
/*     */   extends Random
/*     */ {
/* 101 */   private Provider provider = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 109 */   private SecureRandomSpi secureRandomSpi = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String algorithm;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 120 */   private static volatile SecureRandom seedGenerator = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final long serialVersionUID = 4940670005562187L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private byte[] state;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SecureRandom()
/*     */   {
/* 155 */     super(0L);
/* 156 */     getDefaultPRNG(false, null);
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
/*     */   public SecureRandom(byte[] paramArrayOfByte)
/*     */   {
/* 183 */     super(0L);
/* 184 */     getDefaultPRNG(true, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   private void getDefaultPRNG(boolean paramBoolean, byte[] paramArrayOfByte) {
/* 188 */     String str = getPrngAlgorithm();
/* 189 */     if (str == null)
/*     */     {
/* 191 */       str = "SHA1PRNG";
/* 192 */       this.secureRandomSpi = new sun.security.provider.SecureRandom();
/* 193 */       this.provider = Providers.getSunProvider();
/* 194 */       if (paramBoolean) {
/* 195 */         this.secureRandomSpi.engineSetSeed(paramArrayOfByte);
/*     */       }
/*     */     } else {
/*     */       try {
/* 199 */         SecureRandom localSecureRandom = getInstance(str);
/* 200 */         this.secureRandomSpi = localSecureRandom.getSecureRandomSpi();
/* 201 */         this.provider = localSecureRandom.getProvider();
/* 202 */         if (paramBoolean) {
/* 203 */           this.secureRandomSpi.engineSetSeed(paramArrayOfByte);
/*     */         }
/*     */       }
/*     */       catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 207 */         throw new RuntimeException(localNoSuchAlgorithmException);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 215 */     if (getClass() == SecureRandom.class) {
/* 216 */       this.algorithm = str;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SecureRandom(SecureRandomSpi paramSecureRandomSpi, Provider paramProvider)
/*     */   {
/* 228 */     this(paramSecureRandomSpi, paramProvider, null);
/*     */   }
/*     */   
/*     */   private SecureRandom(SecureRandomSpi paramSecureRandomSpi, Provider paramProvider, String paramString)
/*     */   {
/* 233 */     super(0L);
/* 234 */     this.secureRandomSpi = paramSecureRandomSpi;
/* 235 */     this.provider = paramProvider;
/* 236 */     this.algorithm = paramString;
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
/*     */   public static SecureRandom getInstance(String paramString)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 277 */     GetInstance.Instance localInstance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, paramString);
/*     */     
/* 279 */     return new SecureRandom((SecureRandomSpi)localInstance.impl, localInstance.provider, paramString);
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
/*     */   public static SecureRandom getInstance(String paramString1, String paramString2)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 328 */     GetInstance.Instance localInstance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, paramString1, paramString2);
/*     */     
/* 330 */     return new SecureRandom((SecureRandomSpi)localInstance.impl, localInstance.provider, paramString1);
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
/*     */   public static SecureRandom getInstance(String paramString, Provider paramProvider)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 372 */     GetInstance.Instance localInstance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, paramString, paramProvider);
/*     */     
/* 374 */     return new SecureRandom((SecureRandomSpi)localInstance.impl, localInstance.provider, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   SecureRandomSpi getSecureRandomSpi()
/*     */   {
/* 382 */     return this.secureRandomSpi;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Provider getProvider()
/*     */   {
/* 391 */     return this.provider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAlgorithm()
/*     */   {
/* 403 */     return this.algorithm != null ? this.algorithm : "unknown";
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
/*     */   public synchronized void setSeed(byte[] paramArrayOfByte)
/*     */   {
/* 416 */     this.secureRandomSpi.engineSetSeed(paramArrayOfByte);
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
/*     */   public void setSeed(long paramLong)
/*     */   {
/* 440 */     if (paramLong != 0L) {
/* 441 */       this.secureRandomSpi.engineSetSeed(longToByteArray(paramLong));
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
/*     */   public synchronized void nextBytes(byte[] paramArrayOfByte)
/*     */   {
/* 457 */     this.secureRandomSpi.engineNextBytes(paramArrayOfByte);
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
/*     */   protected final int next(int paramInt)
/*     */   {
/* 476 */     int i = (paramInt + 7) / 8;
/* 477 */     byte[] arrayOfByte = new byte[i];
/* 478 */     int j = 0;
/*     */     
/* 480 */     nextBytes(arrayOfByte);
/* 481 */     for (int k = 0; k < i; k++) {
/* 482 */       j = (j << 8) + (arrayOfByte[k] & 0xFF);
/*     */     }
/*     */     
/* 485 */     return j >>> i * 8 - paramInt;
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
/*     */   public static byte[] getSeed(int paramInt)
/*     */   {
/* 506 */     if (seedGenerator == null) {
/* 507 */       seedGenerator = new SecureRandom();
/*     */     }
/* 509 */     return seedGenerator.generateSeed(paramInt);
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
/*     */   public byte[] generateSeed(int paramInt)
/*     */   {
/* 522 */     return this.secureRandomSpi.engineGenerateSeed(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static byte[] longToByteArray(long paramLong)
/*     */   {
/* 530 */     byte[] arrayOfByte = new byte[8];
/*     */     
/* 532 */     for (int i = 0; i < 8; i++) {
/* 533 */       arrayOfByte[i] = ((byte)(int)paramLong);
/* 534 */       paramLong >>= 8;
/*     */     }
/*     */     
/* 537 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String getPrngAlgorithm()
/*     */   {
/* 547 */     for (Provider localProvider : Providers.getProviderList().providers()) {
/* 548 */       for (Provider.Service localService : localProvider.getServices()) {
/* 549 */         if (localService.getType().equals("SecureRandom")) {
/* 550 */           return localService.getAlgorithm();
/*     */         }
/*     */       }
/*     */     }
/* 554 */     return null;
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
/*     */   private static final class StrongPatternHolder
/*     */   {
/* 574 */     private static Pattern pattern = Pattern.compile("\\s*([\\S&&[^:,]]*)(\\:([\\S&&[^,]]*))?\\s*(\\,(.*))?");
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
/*     */   public static SecureRandom getInstanceStrong()
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 606 */     String str1 = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public String run()
/*     */       {
/* 610 */         return Security.getProperty("securerandom.strongAlgorithms");
/*     */       }
/*     */     });
/*     */     
/*     */ 
/* 615 */     if ((str1 == null) || (str1.length() == 0)) {
/* 616 */       throw new NoSuchAlgorithmException("Null/empty securerandom.strongAlgorithms Security Property");
/*     */     }
/*     */     
/*     */ 
/* 620 */     String str2 = str1;
/* 621 */     while (str2 != null)
/*     */     {
/*     */       Matcher localMatcher;
/* 624 */       if ((localMatcher = StrongPatternHolder.pattern.matcher(str2)).matches())
/*     */       {
/* 626 */         String str3 = localMatcher.group(1);
/* 627 */         String str4 = localMatcher.group(3);
/*     */         try
/*     */         {
/* 630 */           if (str4 == null) {
/* 631 */             return getInstance(str3);
/*     */           }
/* 633 */           return getInstance(str3, str4);
/*     */ 
/*     */         }
/*     */         catch (NoSuchAlgorithmException|NoSuchProviderException localNoSuchAlgorithmException)
/*     */         {
/* 638 */           str2 = localMatcher.group(5);
/*     */         } }
/* 640 */       str2 = null;
/*     */     }
/*     */     
/*     */ 
/* 644 */     throw new NoSuchAlgorithmException("No strong SecureRandom impls available: " + str1);
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
/* 659 */   private MessageDigest digest = null;
/*     */   private byte[] randomBytes;
/*     */   private int randomBytesUsed;
/*     */   private long counter;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/SecureRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */