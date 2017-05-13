/*      */ package java.util;
/*      */ 
/*      */ import java.net.NetworkInterface;
/*      */ import java.security.AccessController;
/*      */ import java.security.SecureRandom;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.stream.DoubleStream;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SplittableRandom
/*      */ {
/*      */   private static final long GOLDEN_GAMMA = -7046029254386353131L;
/*      */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*      */   private long seed;
/*      */   private final long gamma;
/*      */   
/*      */   private SplittableRandom(long paramLong1, long paramLong2)
/*      */   {
/*  185 */     this.seed = paramLong1;
/*  186 */     this.gamma = paramLong2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static long mix64(long paramLong)
/*      */   {
/*  193 */     paramLong = (paramLong ^ paramLong >>> 30) * -4658895280553007687L;
/*  194 */     paramLong = (paramLong ^ paramLong >>> 27) * -7723592293110705685L;
/*  195 */     return paramLong ^ paramLong >>> 31;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int mix32(long paramLong)
/*      */   {
/*  202 */     paramLong = (paramLong ^ paramLong >>> 33) * 7109453100751455733L;
/*  203 */     return (int)((paramLong ^ paramLong >>> 28) * -3808689974395783757L >>> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static long mixGamma(long paramLong)
/*      */   {
/*  210 */     paramLong = (paramLong ^ paramLong >>> 33) * -49064778989728563L;
/*  211 */     paramLong = (paramLong ^ paramLong >>> 33) * -4265267296055464877L;
/*  212 */     paramLong = paramLong ^ paramLong >>> 33 | 1L;
/*  213 */     int i = Long.bitCount(paramLong ^ paramLong >>> 1);
/*  214 */     return i < 24 ? paramLong ^ 0xAAAAAAAAAAAAAAAA : paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private long nextSeed()
/*      */   {
/*  221 */     return this.seed += this.gamma;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  227 */   private static final AtomicLong defaultGen = new AtomicLong(initialSeed());
/*      */   static final String BadBound = "bound must be positive";
/*      */   
/*  230 */   private static long initialSeed() { String str = (String)AccessController.doPrivileged(new GetPropertyAction("java.util.secureRandomSeed"));
/*      */     
/*      */     int i;
/*  233 */     if ((str != null) && (str.equalsIgnoreCase("true"))) {
/*  234 */       byte[] arrayOfByte1 = SecureRandom.getSeed(8);
/*  235 */       long l2 = arrayOfByte1[0] & 0xFF;
/*  236 */       for (i = 1; i < 8; i++)
/*  237 */         l2 = l2 << 8 | arrayOfByte1[i] & 0xFF;
/*  238 */       return l2;
/*      */     }
/*  240 */     long l1 = 0L;
/*      */     try
/*      */     {
/*  243 */       Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
/*  244 */       i = 0;
/*  245 */       while (localEnumeration.hasMoreElements()) {
/*  246 */         NetworkInterface localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
/*  247 */         if (!localNetworkInterface.isVirtual()) {
/*  248 */           byte[] arrayOfByte2 = localNetworkInterface.getHardwareAddress();
/*  249 */           if (arrayOfByte2 != null) {
/*  250 */             int j = arrayOfByte2.length;
/*  251 */             int k = Math.min(j >>> 1, 4);
/*  252 */             for (int m = 0; m < k; m++)
/*  253 */               l1 = l1 << 16 ^ arrayOfByte2[m] << 8 ^ arrayOfByte2[(j - 1 - m)];
/*  254 */             if (k < 4)
/*  255 */               l1 = l1 << 8 ^ arrayOfByte2[(j - 1 - k)];
/*  256 */             l1 = mix64(l1);
/*  257 */             break;
/*      */           }
/*  259 */           if (i != 0) break;
/*  260 */           i = 1;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/*      */ 
/*  268 */     return l1 ^ mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
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
/*      */   static final String BadRange = "bound must be greater than origin";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final String BadSize = "size must be non-negative";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final long internalNextLong(long paramLong1, long paramLong2)
/*      */   {
/*  319 */     long l1 = mix64(nextSeed());
/*  320 */     if (paramLong1 < paramLong2) {
/*  321 */       long l2 = paramLong2 - paramLong1;long l3 = l2 - 1L;
/*  322 */       if ((l2 & l3) == 0L) {
/*  323 */         l1 = (l1 & l3) + paramLong1;
/*  324 */       } else if (l2 > 0L) {
/*  325 */         long l4 = l1 >>> 1;
/*  326 */         while (l4 + l3 - (l1 = l4 % l2) < 0L) {
/*  327 */           l4 = mix64(nextSeed()) >>> 1;
/*      */         }
/*  329 */         l1 += paramLong1;
/*      */       }
/*      */       else {
/*  332 */         while ((l1 < paramLong1) || (l1 >= paramLong2))
/*  333 */           l1 = mix64(nextSeed());
/*      */       }
/*      */     }
/*  336 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int internalNextInt(int paramInt1, int paramInt2)
/*      */   {
/*  348 */     int i = mix32(nextSeed());
/*  349 */     if (paramInt1 < paramInt2) {
/*  350 */       int j = paramInt2 - paramInt1;int k = j - 1;
/*  351 */       if ((j & k) == 0) {
/*  352 */         i = (i & k) + paramInt1;
/*  353 */       } else if (j > 0) {
/*  354 */         int m = i >>> 1;
/*  355 */         while (m + k - (i = m % j) < 0) {
/*  356 */           m = mix32(nextSeed()) >>> 1;
/*      */         }
/*  358 */         i += paramInt1;
/*      */       }
/*      */       else {
/*  361 */         while ((i < paramInt1) || (i >= paramInt2))
/*  362 */           i = mix32(nextSeed());
/*      */       }
/*      */     }
/*  365 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final double internalNextDouble(double paramDouble1, double paramDouble2)
/*      */   {
/*  376 */     double d = (nextLong() >>> 11) * 1.1102230246251565E-16D;
/*  377 */     if (paramDouble1 < paramDouble2) {
/*  378 */       d = d * (paramDouble2 - paramDouble1) + paramDouble1;
/*  379 */       if (d >= paramDouble2)
/*  380 */         d = Double.longBitsToDouble(Double.doubleToLongBits(paramDouble2) - 1L);
/*      */     }
/*  382 */     return d;
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
/*      */   public SplittableRandom(long paramLong)
/*      */   {
/*  395 */     this(paramLong, -7046029254386353131L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SplittableRandom()
/*      */   {
/*  405 */     long l = defaultGen.getAndAdd(4354685564936845354L);
/*  406 */     this.seed = mix64(l);
/*  407 */     this.gamma = mixGamma(l + -7046029254386353131L);
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
/*      */   public SplittableRandom split()
/*      */   {
/*  425 */     return new SplittableRandom(nextLong(), mixGamma(nextSeed()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int nextInt()
/*      */   {
/*  434 */     return mix32(nextSeed());
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
/*      */   public int nextInt(int paramInt)
/*      */   {
/*  447 */     if (paramInt <= 0) {
/*  448 */       throw new IllegalArgumentException("bound must be positive");
/*      */     }
/*  450 */     int i = mix32(nextSeed());
/*  451 */     int j = paramInt - 1;
/*  452 */     if ((paramInt & j) == 0) {
/*  453 */       i &= j;
/*      */     } else {
/*  455 */       int k = i >>> 1;
/*  456 */       while (k + j - (i = k % paramInt) < 0) {
/*  457 */         k = mix32(nextSeed()) >>> 1;
/*      */       }
/*      */     }
/*  460 */     return i;
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
/*      */   public int nextInt(int paramInt1, int paramInt2)
/*      */   {
/*  475 */     if (paramInt1 >= paramInt2)
/*  476 */       throw new IllegalArgumentException("bound must be greater than origin");
/*  477 */     return internalNextInt(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long nextLong()
/*      */   {
/*  486 */     return mix64(nextSeed());
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
/*      */   public long nextLong(long paramLong)
/*      */   {
/*  499 */     if (paramLong <= 0L) {
/*  500 */       throw new IllegalArgumentException("bound must be positive");
/*      */     }
/*  502 */     long l1 = mix64(nextSeed());
/*  503 */     long l2 = paramLong - 1L;
/*  504 */     if ((paramLong & l2) == 0L) {
/*  505 */       l1 &= l2;
/*      */     } else {
/*  507 */       long l3 = l1 >>> 1;
/*  508 */       while (l3 + l2 - (l1 = l3 % paramLong) < 0L) {
/*  509 */         l3 = mix64(nextSeed()) >>> 1;
/*      */       }
/*      */     }
/*  512 */     return l1;
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
/*      */   public long nextLong(long paramLong1, long paramLong2)
/*      */   {
/*  527 */     if (paramLong1 >= paramLong2)
/*  528 */       throw new IllegalArgumentException("bound must be greater than origin");
/*  529 */     return internalNextLong(paramLong1, paramLong2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double nextDouble()
/*      */   {
/*  540 */     return (mix64(nextSeed()) >>> 11) * 1.1102230246251565E-16D;
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
/*      */   public double nextDouble(double paramDouble)
/*      */   {
/*  553 */     if (paramDouble <= 0.0D)
/*  554 */       throw new IllegalArgumentException("bound must be positive");
/*  555 */     double d = (mix64(nextSeed()) >>> 11) * 1.1102230246251565E-16D * paramDouble;
/*      */     
/*  557 */     return d < paramDouble ? d : Double.longBitsToDouble(Double.doubleToLongBits(paramDouble) - 1L);
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
/*      */   public double nextDouble(double paramDouble1, double paramDouble2)
/*      */   {
/*  572 */     if (paramDouble1 >= paramDouble2)
/*  573 */       throw new IllegalArgumentException("bound must be greater than origin");
/*  574 */     return internalNextDouble(paramDouble1, paramDouble2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean nextBoolean()
/*      */   {
/*  583 */     return mix32(nextSeed()) < 0;
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
/*      */   public IntStream ints(long paramLong)
/*      */   {
/*  600 */     if (paramLong < 0L) {
/*  601 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  603 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, Integer.MAX_VALUE, 0), false);
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
/*      */   public IntStream ints()
/*      */   {
/*  619 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
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
/*      */   public IntStream ints(long paramLong, int paramInt1, int paramInt2)
/*      */   {
/*  641 */     if (paramLong < 0L)
/*  642 */       throw new IllegalArgumentException("size must be non-negative");
/*  643 */     if (paramInt1 >= paramInt2) {
/*  644 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  646 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, paramInt1, paramInt2), false);
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
/*      */   public IntStream ints(int paramInt1, int paramInt2)
/*      */   {
/*  667 */     if (paramInt1 >= paramInt2) {
/*  668 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  670 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, paramInt1, paramInt2), false);
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
/*      */   public LongStream longs(long paramLong)
/*      */   {
/*  686 */     if (paramLong < 0L) {
/*  687 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  689 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong, Long.MAX_VALUE, 0L), false);
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
/*      */   public LongStream longs()
/*      */   {
/*  705 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
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
/*      */   public LongStream longs(long paramLong1, long paramLong2, long paramLong3)
/*      */   {
/*  727 */     if (paramLong1 < 0L)
/*  728 */       throw new IllegalArgumentException("size must be non-negative");
/*  729 */     if (paramLong2 >= paramLong3) {
/*  730 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  732 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong1, paramLong2, paramLong3), false);
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
/*      */   public LongStream longs(long paramLong1, long paramLong2)
/*      */   {
/*  753 */     if (paramLong1 >= paramLong2) {
/*  754 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  756 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, paramLong1, paramLong2), false);
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
/*      */   public DoubleStream doubles(long paramLong)
/*      */   {
/*  772 */     if (paramLong < 0L) {
/*  773 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  775 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, Double.MAX_VALUE, 0.0D), false);
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
/*      */   public DoubleStream doubles()
/*      */   {
/*  792 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0D), false);
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
/*      */   public DoubleStream doubles(long paramLong, double paramDouble1, double paramDouble2)
/*      */   {
/*  815 */     if (paramLong < 0L)
/*  816 */       throw new IllegalArgumentException("size must be non-negative");
/*  817 */     if (paramDouble1 >= paramDouble2) {
/*  818 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  820 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, paramDouble1, paramDouble2), false);
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
/*      */   public DoubleStream doubles(double paramDouble1, double paramDouble2)
/*      */   {
/*  841 */     if (paramDouble1 >= paramDouble2) {
/*  842 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  844 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, paramDouble1, paramDouble2), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class RandomIntsSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     final SplittableRandom rng;
/*      */     
/*      */     long index;
/*      */     
/*      */     final long fence;
/*      */     
/*      */     final int origin;
/*      */     
/*      */     final int bound;
/*      */     
/*      */ 
/*      */     RandomIntsSpliterator(SplittableRandom paramSplittableRandom, long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/*      */     {
/*  865 */       this.rng = paramSplittableRandom;this.index = paramLong1;this.fence = paramLong2;
/*  866 */       this.origin = paramInt1;this.bound = paramInt2;
/*      */     }
/*      */     
/*      */     public RandomIntsSpliterator trySplit() {
/*  870 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/*      */       
/*  872 */       return l2 <= l1 ? null : new RandomIntsSpliterator(this.rng.split(), l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  876 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  880 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*      */     {
/*  885 */       if (paramIntConsumer == null) throw new NullPointerException();
/*  886 */       long l1 = this.index;long l2 = this.fence;
/*  887 */       if (l1 < l2) {
/*  888 */         paramIntConsumer.accept(this.rng.internalNextInt(this.origin, this.bound));
/*  889 */         this.index = (l1 + 1L);
/*  890 */         return true;
/*      */       }
/*  892 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(IntConsumer paramIntConsumer) {
/*  896 */       if (paramIntConsumer == null) throw new NullPointerException();
/*  897 */       long l1 = this.index;long l2 = this.fence;
/*  898 */       if (l1 < l2) {
/*  899 */         this.index = l2;
/*  900 */         SplittableRandom localSplittableRandom = this.rng;
/*  901 */         int i = this.origin;int j = this.bound;
/*      */         do {
/*  903 */           paramIntConsumer.accept(localSplittableRandom.internalNextInt(i, j));
/*  904 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class RandomLongsSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     final SplittableRandom rng;
/*      */     long index;
/*      */     final long fence;
/*      */     final long origin;
/*      */     final long bound;
/*      */     
/*      */     RandomLongsSpliterator(SplittableRandom paramSplittableRandom, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*      */     {
/*  920 */       this.rng = paramSplittableRandom;this.index = paramLong1;this.fence = paramLong2;
/*  921 */       this.origin = paramLong3;this.bound = paramLong4;
/*      */     }
/*      */     
/*      */     public RandomLongsSpliterator trySplit() {
/*  925 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/*      */       
/*  927 */       return l2 <= l1 ? null : new RandomLongsSpliterator(this.rng.split(), l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  931 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  935 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*      */     {
/*  940 */       if (paramLongConsumer == null) throw new NullPointerException();
/*  941 */       long l1 = this.index;long l2 = this.fence;
/*  942 */       if (l1 < l2) {
/*  943 */         paramLongConsumer.accept(this.rng.internalNextLong(this.origin, this.bound));
/*  944 */         this.index = (l1 + 1L);
/*  945 */         return true;
/*      */       }
/*  947 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(LongConsumer paramLongConsumer) {
/*  951 */       if (paramLongConsumer == null) throw new NullPointerException();
/*  952 */       long l1 = this.index;long l2 = this.fence;
/*  953 */       if (l1 < l2) {
/*  954 */         this.index = l2;
/*  955 */         SplittableRandom localSplittableRandom = this.rng;
/*  956 */         long l3 = this.origin;long l4 = this.bound;
/*      */         do {
/*  958 */           paramLongConsumer.accept(localSplittableRandom.internalNextLong(l3, l4));
/*  959 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class RandomDoublesSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     final SplittableRandom rng;
/*      */     long index;
/*      */     final long fence;
/*      */     final double origin;
/*      */     final double bound;
/*      */     
/*      */     RandomDoublesSpliterator(SplittableRandom paramSplittableRandom, long paramLong1, long paramLong2, double paramDouble1, double paramDouble2)
/*      */     {
/*  976 */       this.rng = paramSplittableRandom;this.index = paramLong1;this.fence = paramLong2;
/*  977 */       this.origin = paramDouble1;this.bound = paramDouble2;
/*      */     }
/*      */     
/*      */     public RandomDoublesSpliterator trySplit() {
/*  981 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/*      */       
/*  983 */       return l2 <= l1 ? null : new RandomDoublesSpliterator(this.rng.split(), l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  987 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  991 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer paramDoubleConsumer)
/*      */     {
/*  996 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/*  997 */       long l1 = this.index;long l2 = this.fence;
/*  998 */       if (l1 < l2) {
/*  999 */         paramDoubleConsumer.accept(this.rng.internalNextDouble(this.origin, this.bound));
/* 1000 */         this.index = (l1 + 1L);
/* 1001 */         return true;
/*      */       }
/* 1003 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer paramDoubleConsumer) {
/* 1007 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/* 1008 */       long l1 = this.index;long l2 = this.fence;
/* 1009 */       if (l1 < l2) {
/* 1010 */         this.index = l2;
/* 1011 */         SplittableRandom localSplittableRandom = this.rng;
/* 1012 */         double d1 = this.origin;double d2 = this.bound;
/*      */         do {
/* 1014 */           paramDoubleConsumer.accept(localSplittableRandom.internalNextDouble(d1, d2));
/* 1015 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/SplittableRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */