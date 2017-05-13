/*     */ package java.security;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.jca.GetInstance.Instance;
/*     */ import sun.security.jca.JCAUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyPairGenerator
/*     */   extends KeyPairGeneratorSpi
/*     */ {
/*     */   private final String algorithm;
/*     */   Provider provider;
/*     */   
/*     */   protected KeyPairGenerator(String paramString)
/*     */   {
/* 144 */     this.algorithm = paramString;
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
/*     */   public String getAlgorithm()
/*     */   {
/* 157 */     return this.algorithm;
/*     */   }
/*     */   
/*     */   private static KeyPairGenerator getInstance(GetInstance.Instance paramInstance, String paramString)
/*     */   {
/*     */     Object localObject;
/* 163 */     if ((paramInstance.impl instanceof KeyPairGenerator)) {
/* 164 */       localObject = (KeyPairGenerator)paramInstance.impl;
/*     */     } else {
/* 166 */       KeyPairGeneratorSpi localKeyPairGeneratorSpi = (KeyPairGeneratorSpi)paramInstance.impl;
/* 167 */       localObject = new Delegate(localKeyPairGeneratorSpi, paramString);
/*     */     }
/* 169 */     ((KeyPairGenerator)localObject).provider = paramInstance.provider;
/* 170 */     return (KeyPairGenerator)localObject;
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
/*     */   public static KeyPairGenerator getInstance(String paramString)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 203 */     List localList = GetInstance.getServices("KeyPairGenerator", paramString);
/* 204 */     Iterator localIterator = localList.iterator();
/* 205 */     if (!localIterator.hasNext()) {
/* 206 */       throw new NoSuchAlgorithmException(paramString + " KeyPairGenerator not available");
/*     */     }
/*     */     
/*     */ 
/* 210 */     Object localObject = null;
/*     */     do {
/* 212 */       Provider.Service localService = (Provider.Service)localIterator.next();
/*     */       try
/*     */       {
/* 215 */         GetInstance.Instance localInstance = GetInstance.getInstance(localService, KeyPairGeneratorSpi.class);
/* 216 */         if ((localInstance.impl instanceof KeyPairGenerator)) {
/* 217 */           return getInstance(localInstance, paramString);
/*     */         }
/* 219 */         return new Delegate(localInstance, localIterator, paramString);
/*     */       }
/*     */       catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 222 */         if (localObject == null) {
/* 223 */           localObject = localNoSuchAlgorithmException;
/*     */         }
/*     */       }
/* 226 */     } while (localIterator.hasNext());
/* 227 */     throw ((Throwable)localObject);
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
/*     */   public static KeyPairGenerator getInstance(String paramString1, String paramString2)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 267 */     GetInstance.Instance localInstance = GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, paramString1, paramString2);
/*     */     
/* 269 */     return getInstance(localInstance, paramString1);
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
/*     */   public static KeyPairGenerator getInstance(String paramString, Provider paramProvider)
/*     */     throws NoSuchAlgorithmException
/*     */   {
/* 303 */     GetInstance.Instance localInstance = GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, paramString, paramProvider);
/*     */     
/* 305 */     return getInstance(localInstance, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Provider getProvider()
/*     */   {
/* 314 */     disableFailover();
/* 315 */     return this.provider;
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
/*     */   void disableFailover() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize(int paramInt)
/*     */   {
/* 339 */     initialize(paramInt, JCAUtil.getSecureRandom());
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
/*     */   public void initialize(int paramInt, SecureRandom paramSecureRandom) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec)
/*     */     throws InvalidAlgorithmParameterException
/*     */   {
/* 399 */     initialize(paramAlgorithmParameterSpec, JCAUtil.getSecureRandom());
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
/*     */   public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
/*     */     throws InvalidAlgorithmParameterException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final KeyPair genKeyPair()
/*     */   {
/* 458 */     return generateKeyPair();
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
/*     */   public KeyPair generateKeyPair()
/*     */   {
/* 489 */     return null;
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
/*     */   private static final class Delegate
/*     */     extends KeyPairGenerator
/*     */   {
/*     */     private volatile KeyPairGeneratorSpi spi;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 534 */     private final Object lock = new Object();
/*     */     
/*     */     private Iterator<Provider.Service> serviceIterator;
/*     */     
/*     */     private static final int I_NONE = 1;
/*     */     
/*     */     private static final int I_SIZE = 2;
/*     */     private static final int I_PARAMS = 3;
/*     */     private int initType;
/*     */     private int initKeySize;
/*     */     private AlgorithmParameterSpec initParams;
/*     */     private SecureRandom initRandom;
/*     */     
/*     */     Delegate(KeyPairGeneratorSpi paramKeyPairGeneratorSpi, String paramString)
/*     */     {
/* 549 */       super();
/* 550 */       this.spi = paramKeyPairGeneratorSpi;
/*     */     }
/*     */     
/*     */     Delegate(GetInstance.Instance paramInstance, Iterator<Provider.Service> paramIterator, String paramString)
/*     */     {
/* 555 */       super();
/* 556 */       this.spi = ((KeyPairGeneratorSpi)paramInstance.impl);
/* 557 */       this.provider = paramInstance.provider;
/* 558 */       this.serviceIterator = paramIterator;
/* 559 */       this.initType = 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private KeyPairGeneratorSpi nextSpi(KeyPairGeneratorSpi paramKeyPairGeneratorSpi, boolean paramBoolean)
/*     */     {
/* 570 */       synchronized (this.lock)
/*     */       {
/*     */ 
/* 573 */         if ((paramKeyPairGeneratorSpi != null) && (paramKeyPairGeneratorSpi != this.spi)) {
/* 574 */           return this.spi;
/*     */         }
/* 576 */         if (this.serviceIterator == null) {
/* 577 */           return null;
/*     */         }
/* 579 */         while (this.serviceIterator.hasNext()) {
/* 580 */           Provider.Service localService = (Provider.Service)this.serviceIterator.next();
/*     */           try {
/* 582 */             Object localObject1 = localService.newInstance(null);
/*     */             
/* 584 */             if (((localObject1 instanceof KeyPairGeneratorSpi)) && 
/*     */             
/*     */ 
/* 587 */               (!(localObject1 instanceof KeyPairGenerator)))
/*     */             {
/*     */ 
/* 590 */               KeyPairGeneratorSpi localKeyPairGeneratorSpi = (KeyPairGeneratorSpi)localObject1;
/* 591 */               if (paramBoolean) {
/* 592 */                 if (this.initType == 2) {
/* 593 */                   localKeyPairGeneratorSpi.initialize(this.initKeySize, this.initRandom);
/* 594 */                 } else if (this.initType == 3) {
/* 595 */                   localKeyPairGeneratorSpi.initialize(this.initParams, this.initRandom);
/* 596 */                 } else if (this.initType != 1) {
/* 597 */                   throw new AssertionError("KeyPairGenerator initType: " + this.initType);
/*     */                 }
/*     */               }
/*     */               
/* 601 */               this.provider = localService.getProvider();
/* 602 */               this.spi = localKeyPairGeneratorSpi;
/* 603 */               return localKeyPairGeneratorSpi;
/*     */             }
/*     */           }
/*     */           catch (Exception localException) {}
/*     */         }
/* 608 */         disableFailover();
/* 609 */         return null;
/*     */       }
/*     */     }
/*     */     
/*     */     void disableFailover() {
/* 614 */       this.serviceIterator = null;
/* 615 */       this.initType = 0;
/* 616 */       this.initParams = null;
/* 617 */       this.initRandom = null;
/*     */     }
/*     */     
/*     */     public void initialize(int paramInt, SecureRandom paramSecureRandom)
/*     */     {
/* 622 */       if (this.serviceIterator == null) {
/* 623 */         this.spi.initialize(paramInt, paramSecureRandom);
/* 624 */         return;
/*     */       }
/* 626 */       Object localObject = null;
/* 627 */       KeyPairGeneratorSpi localKeyPairGeneratorSpi = this.spi;
/*     */       do {
/*     */         try {
/* 630 */           localKeyPairGeneratorSpi.initialize(paramInt, paramSecureRandom);
/* 631 */           this.initType = 2;
/* 632 */           this.initKeySize = paramInt;
/* 633 */           this.initParams = null;
/* 634 */           this.initRandom = paramSecureRandom;
/* 635 */           return;
/*     */         } catch (RuntimeException localRuntimeException) {
/* 637 */           if (localObject == null) {
/* 638 */             localObject = localRuntimeException;
/*     */           }
/* 640 */           localKeyPairGeneratorSpi = nextSpi(localKeyPairGeneratorSpi, false);
/*     */         }
/* 642 */       } while (localKeyPairGeneratorSpi != null);
/* 643 */       throw ((Throwable)localObject);
/*     */     }
/*     */     
/*     */     public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
/*     */       throws InvalidAlgorithmParameterException
/*     */     {
/* 649 */       if (this.serviceIterator == null) {
/* 650 */         this.spi.initialize(paramAlgorithmParameterSpec, paramSecureRandom);
/* 651 */         return;
/*     */       }
/* 653 */       Object localObject = null;
/* 654 */       KeyPairGeneratorSpi localKeyPairGeneratorSpi = this.spi;
/*     */       do {
/*     */         try {
/* 657 */           localKeyPairGeneratorSpi.initialize(paramAlgorithmParameterSpec, paramSecureRandom);
/* 658 */           this.initType = 3;
/* 659 */           this.initKeySize = 0;
/* 660 */           this.initParams = paramAlgorithmParameterSpec;
/* 661 */           this.initRandom = paramSecureRandom;
/* 662 */           return;
/*     */         } catch (Exception localException) {
/* 664 */           if (localObject == null) {
/* 665 */             localObject = localException;
/*     */           }
/* 667 */           localKeyPairGeneratorSpi = nextSpi(localKeyPairGeneratorSpi, false);
/*     */         }
/* 669 */       } while (localKeyPairGeneratorSpi != null);
/* 670 */       if ((localObject instanceof RuntimeException)) {
/* 671 */         throw ((RuntimeException)localObject);
/*     */       }
/*     */       
/* 674 */       throw ((InvalidAlgorithmParameterException)localObject);
/*     */     }
/*     */     
/*     */     public KeyPair generateKeyPair()
/*     */     {
/* 679 */       if (this.serviceIterator == null) {
/* 680 */         return this.spi.generateKeyPair();
/*     */       }
/* 682 */       Object localObject = null;
/* 683 */       KeyPairGeneratorSpi localKeyPairGeneratorSpi = this.spi;
/*     */       do {
/*     */         try {
/* 686 */           return localKeyPairGeneratorSpi.generateKeyPair();
/*     */         } catch (RuntimeException localRuntimeException) {
/* 688 */           if (localObject == null) {
/* 689 */             localObject = localRuntimeException;
/*     */           }
/* 691 */           localKeyPairGeneratorSpi = nextSpi(localKeyPairGeneratorSpi, true);
/*     */         }
/* 693 */       } while (localKeyPairGeneratorSpi != null);
/* 694 */       throw ((Throwable)localObject);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/KeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */