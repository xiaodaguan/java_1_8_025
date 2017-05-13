/*     */ package java.security;
/*     */ 
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.jca.GetInstance.Instance;
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
/*     */ public class KeyFactory
/*     */ {
/*  99 */   private static final Debug debug = Debug.getInstance("jca", "KeyFactory");
/*     */   
/*     */ 
/*     */   private final String algorithm;
/*     */   
/*     */ 
/*     */   private Provider provider;
/*     */   
/*     */ 
/*     */   private volatile KeyFactorySpi spi;
/*     */   
/*     */ 
/* 111 */   private final Object lock = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Iterator<Provider.Service> serviceIterator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected KeyFactory(KeyFactorySpi paramKeyFactorySpi, Provider paramProvider, String paramString)
/*     */   {
/* 127 */     this.spi = paramKeyFactorySpi;
/* 128 */     this.provider = paramProvider;
/* 129 */     this.algorithm = paramString;
/*     */   }
/*     */   
/*     */   private KeyFactory(String paramString) throws NoSuchAlgorithmException {
/* 133 */     this.algorithm = paramString;
/* 134 */     List localList = GetInstance.getServices("KeyFactory", paramString);
/* 135 */     this.serviceIterator = localList.iterator();
/*     */     
/* 137 */     if (nextSpi(null) == null) {
/* 138 */       throw new NoSuchAlgorithmException(paramString + " KeyFactory not available");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static KeyFactory getInstance(String paramString)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 172 */     return new KeyFactory(paramString);
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
/*     */   public static KeyFactory getInstance(String paramString1, String paramString2)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 211 */     GetInstance.Instance localInstance = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, paramString1, paramString2);
/*     */     
/* 213 */     return new KeyFactory((KeyFactorySpi)localInstance.impl, localInstance.provider, paramString1);
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
/*     */   public static KeyFactory getInstance(String paramString, Provider paramProvider)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 248 */     GetInstance.Instance localInstance = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, paramString, paramProvider);
/*     */     
/* 250 */     return new KeyFactory((KeyFactorySpi)localInstance.impl, localInstance.provider, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Provider getProvider()
/*     */   {
/* 260 */     synchronized (this.lock)
/*     */     {
/* 262 */       this.serviceIterator = null;
/* 263 */       return this.provider;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getAlgorithm()
/*     */   {
/* 275 */     return this.algorithm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private KeyFactorySpi nextSpi(KeyFactorySpi paramKeyFactorySpi)
/*     */   {
/* 285 */     synchronized (this.lock)
/*     */     {
/*     */ 
/* 288 */       if ((paramKeyFactorySpi != null) && (paramKeyFactorySpi != this.spi)) {
/* 289 */         return this.spi;
/*     */       }
/* 291 */       if (this.serviceIterator == null) {
/* 292 */         return null;
/*     */       }
/* 294 */       while (this.serviceIterator.hasNext()) {
/* 295 */         Provider.Service localService = (Provider.Service)this.serviceIterator.next();
/*     */         try {
/* 297 */           Object localObject1 = localService.newInstance(null);
/* 298 */           if ((localObject1 instanceof KeyFactorySpi))
/*     */           {
/*     */ 
/* 301 */             KeyFactorySpi localKeyFactorySpi = (KeyFactorySpi)localObject1;
/* 302 */             this.provider = localService.getProvider();
/* 303 */             this.spi = localKeyFactorySpi;
/* 304 */             return localKeyFactorySpi;
/*     */           }
/*     */         }
/*     */         catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
/*     */       }
/* 309 */       this.serviceIterator = null;
/* 310 */       return null;
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
/*     */   public final PublicKey generatePublic(KeySpec paramKeySpec)
/*     */     throws InvalidKeySpecException
/*     */   {
/* 327 */     if (this.serviceIterator == null) {
/* 328 */       return this.spi.engineGeneratePublic(paramKeySpec);
/*     */     }
/* 330 */     Object localObject = null;
/* 331 */     KeyFactorySpi localKeyFactorySpi = this.spi;
/*     */     do {
/*     */       try {
/* 334 */         return localKeyFactorySpi.engineGeneratePublic(paramKeySpec);
/*     */       } catch (Exception localException) {
/* 336 */         if (localObject == null) {
/* 337 */           localObject = localException;
/*     */         }
/* 339 */         localKeyFactorySpi = nextSpi(localKeyFactorySpi);
/*     */       }
/* 341 */     } while (localKeyFactorySpi != null);
/* 342 */     if ((localObject instanceof RuntimeException)) {
/* 343 */       throw ((RuntimeException)localObject);
/*     */     }
/* 345 */     if ((localObject instanceof InvalidKeySpecException)) {
/* 346 */       throw ((InvalidKeySpecException)localObject);
/*     */     }
/* 348 */     throw new InvalidKeySpecException("Could not generate public key", (Throwable)localObject);
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
/*     */   public final PrivateKey generatePrivate(KeySpec paramKeySpec)
/*     */     throws InvalidKeySpecException
/*     */   {
/* 365 */     if (this.serviceIterator == null) {
/* 366 */       return this.spi.engineGeneratePrivate(paramKeySpec);
/*     */     }
/* 368 */     Object localObject = null;
/* 369 */     KeyFactorySpi localKeyFactorySpi = this.spi;
/*     */     do {
/*     */       try {
/* 372 */         return localKeyFactorySpi.engineGeneratePrivate(paramKeySpec);
/*     */       } catch (Exception localException) {
/* 374 */         if (localObject == null) {
/* 375 */           localObject = localException;
/*     */         }
/* 377 */         localKeyFactorySpi = nextSpi(localKeyFactorySpi);
/*     */       }
/* 379 */     } while (localKeyFactorySpi != null);
/* 380 */     if ((localObject instanceof RuntimeException)) {
/* 381 */       throw ((RuntimeException)localObject);
/*     */     }
/* 383 */     if ((localObject instanceof InvalidKeySpecException)) {
/* 384 */       throw ((InvalidKeySpecException)localObject);
/*     */     }
/* 386 */     throw new InvalidKeySpecException("Could not generate private key", (Throwable)localObject);
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
/*     */   public final <T extends KeySpec> T getKeySpec(Key paramKey, Class<T> paramClass)
/*     */     throws InvalidKeySpecException
/*     */   {
/* 414 */     if (this.serviceIterator == null) {
/* 415 */       return this.spi.engineGetKeySpec(paramKey, paramClass);
/*     */     }
/* 417 */     Object localObject = null;
/* 418 */     KeyFactorySpi localKeyFactorySpi = this.spi;
/*     */     do {
/*     */       try {
/* 421 */         return localKeyFactorySpi.engineGetKeySpec(paramKey, paramClass);
/*     */       } catch (Exception localException) {
/* 423 */         if (localObject == null) {
/* 424 */           localObject = localException;
/*     */         }
/* 426 */         localKeyFactorySpi = nextSpi(localKeyFactorySpi);
/*     */       }
/* 428 */     } while (localKeyFactorySpi != null);
/* 429 */     if ((localObject instanceof RuntimeException)) {
/* 430 */       throw ((RuntimeException)localObject);
/*     */     }
/* 432 */     if ((localObject instanceof InvalidKeySpecException)) {
/* 433 */       throw ((InvalidKeySpecException)localObject);
/*     */     }
/* 435 */     throw new InvalidKeySpecException("Could not get key spec", (Throwable)localObject);
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
/*     */   public final Key translateKey(Key paramKey)
/*     */     throws InvalidKeyException
/*     */   {
/* 451 */     if (this.serviceIterator == null) {
/* 452 */       return this.spi.engineTranslateKey(paramKey);
/*     */     }
/* 454 */     Object localObject = null;
/* 455 */     KeyFactorySpi localKeyFactorySpi = this.spi;
/*     */     do {
/*     */       try {
/* 458 */         return localKeyFactorySpi.engineTranslateKey(paramKey);
/*     */       } catch (Exception localException) {
/* 460 */         if (localObject == null) {
/* 461 */           localObject = localException;
/*     */         }
/* 463 */         localKeyFactorySpi = nextSpi(localKeyFactorySpi);
/*     */       }
/* 465 */     } while (localKeyFactorySpi != null);
/* 466 */     if ((localObject instanceof RuntimeException)) {
/* 467 */       throw ((RuntimeException)localObject);
/*     */     }
/* 469 */     if ((localObject instanceof InvalidKeyException)) {
/* 470 */       throw ((InvalidKeyException)localObject);
/*     */     }
/* 472 */     throw new InvalidKeyException("Could not translate key", (Throwable)localObject);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/KeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */