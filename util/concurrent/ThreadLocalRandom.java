/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.net.NetworkInterface;
/*      */ import java.security.AccessController;
/*      */ import java.security.SecureRandom;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Random;
/*      */ import java.util.Spliterator.OfDouble;
/*      */ import java.util.Spliterator.OfInt;
/*      */ import java.util.Spliterator.OfLong;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.stream.DoubleStream;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.misc.Unsafe;
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
/*      */ public class ThreadLocalRandom
/*      */   extends Random
/*      */ {
/*  131 */   private static final AtomicInteger probeGenerator = new AtomicInteger();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  137 */   private static final AtomicLong seeder = new AtomicLong(initialSeed());
/*      */   private static final long GAMMA = -7046029254386353131L;
/*      */   
/*  140 */   private static long initialSeed() { String str = (String)AccessController.doPrivileged(new GetPropertyAction("java.util.secureRandomSeed"));
/*      */     
/*      */     int i;
/*  143 */     if ((str != null) && (str.equalsIgnoreCase("true"))) {
/*  144 */       byte[] arrayOfByte1 = SecureRandom.getSeed(8);
/*  145 */       long l2 = arrayOfByte1[0] & 0xFF;
/*  146 */       for (i = 1; i < 8; i++)
/*  147 */         l2 = l2 << 8 | arrayOfByte1[i] & 0xFF;
/*  148 */       return l2;
/*      */     }
/*  150 */     long l1 = 0L;
/*      */     try
/*      */     {
/*  153 */       Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
/*  154 */       i = 0;
/*  155 */       while (localEnumeration.hasMoreElements()) {
/*  156 */         NetworkInterface localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
/*  157 */         if (!localNetworkInterface.isVirtual()) {
/*  158 */           byte[] arrayOfByte2 = localNetworkInterface.getHardwareAddress();
/*  159 */           if (arrayOfByte2 != null) {
/*  160 */             int j = arrayOfByte2.length;
/*  161 */             int k = Math.min(j >>> 1, 4);
/*  162 */             for (int m = 0; m < k; m++)
/*  163 */               l1 = l1 << 16 ^ arrayOfByte2[m] << 8 ^ arrayOfByte2[(j - 1 - m)];
/*  164 */             if (k < 4)
/*  165 */               l1 = l1 << 8 ^ arrayOfByte2[(j - 1 - k)];
/*  166 */             l1 = mix64(l1);
/*  167 */             break;
/*      */           }
/*  169 */           if (i != 0) break;
/*  170 */           i = 1;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/*      */ 
/*  178 */     return l1 ^ mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int PROBE_INCREMENT = -1640531527;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final long SEEDER_INCREMENT = -4942790177534073029L;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final float FLOAT_UNIT = 5.9604645E-8F;
/*      */   
/*      */ 
/*      */ 
/*  201 */   private static final ThreadLocal<Double> nextLocalGaussian = new ThreadLocal();
/*      */   boolean initialized;
/*      */   
/*      */   private static long mix64(long paramLong) {
/*  205 */     paramLong = (paramLong ^ paramLong >>> 33) * -49064778989728563L;
/*  206 */     paramLong = (paramLong ^ paramLong >>> 33) * -4265267296055464877L;
/*  207 */     return paramLong ^ paramLong >>> 33;
/*      */   }
/*      */   
/*      */   private static int mix32(long paramLong) {
/*  211 */     paramLong = (paramLong ^ paramLong >>> 33) * -49064778989728563L;
/*  212 */     return (int)((paramLong ^ paramLong >>> 33) * -4265267296055464877L >>> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ThreadLocalRandom()
/*      */   {
/*  223 */     this.initialized = true;
/*      */   }
/*      */   
/*      */ 
/*  227 */   static final ThreadLocalRandom instance = new ThreadLocalRandom();
/*      */   
/*      */   static final String BadBound = "bound must be positive";
/*      */   
/*      */   static final String BadRange = "bound must be greater than origin";
/*      */   static final String BadSize = "size must be non-negative";
/*      */   private static final long serialVersionUID = -5851777807851030925L;
/*      */   
/*      */   static final void localInit()
/*      */   {
/*  237 */     int i = probeGenerator.addAndGet(-1640531527);
/*  238 */     int j = i == 0 ? 1 : i;
/*  239 */     long l = mix64(seeder.getAndAdd(-4942790177534073029L));
/*  240 */     Thread localThread = Thread.currentThread();
/*  241 */     UNSAFE.putLong(localThread, SEED, l);
/*  242 */     UNSAFE.putInt(localThread, PROBE, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ThreadLocalRandom current()
/*      */   {
/*  251 */     if (UNSAFE.getInt(Thread.currentThread(), PROBE) == 0)
/*  252 */       localInit();
/*  253 */     return instance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSeed(long paramLong)
/*      */   {
/*  264 */     if (this.initialized)
/*  265 */       throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   final long nextSeed() { Thread localThread;
/*      */     long l;
/*  270 */     UNSAFE.putLong(localThread = Thread.currentThread(), SEED, 
/*  271 */       l = UNSAFE.getLong(localThread, SEED) + -7046029254386353131L);
/*  272 */     return l;
/*      */   }
/*      */   
/*      */   protected int next(int paramInt)
/*      */   {
/*  277 */     return (int)(mix64(nextSeed()) >>> 64 - paramInt);
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
/*      */   final long internalNextLong(long paramLong1, long paramLong2)
/*      */   {
/*  295 */     long l1 = mix64(nextSeed());
/*  296 */     if (paramLong1 < paramLong2) {
/*  297 */       long l2 = paramLong2 - paramLong1;long l3 = l2 - 1L;
/*  298 */       if ((l2 & l3) == 0L) {
/*  299 */         l1 = (l1 & l3) + paramLong1;
/*  300 */       } else if (l2 > 0L) {
/*  301 */         long l4 = l1 >>> 1;
/*  302 */         while (l4 + l3 - (l1 = l4 % l2) < 0L) {
/*  303 */           l4 = mix64(nextSeed()) >>> 1;
/*      */         }
/*  305 */         l1 += paramLong1;
/*      */       }
/*      */       else {
/*  308 */         while ((l1 < paramLong1) || (l1 >= paramLong2))
/*  309 */           l1 = mix64(nextSeed());
/*      */       }
/*      */     }
/*  312 */     return l1;
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
/*  324 */     int i = mix32(nextSeed());
/*  325 */     if (paramInt1 < paramInt2) {
/*  326 */       int j = paramInt2 - paramInt1;int k = j - 1;
/*  327 */       if ((j & k) == 0) {
/*  328 */         i = (i & k) + paramInt1;
/*  329 */       } else if (j > 0) {
/*  330 */         int m = i >>> 1;
/*  331 */         while (m + k - (i = m % j) < 0) {
/*  332 */           m = mix32(nextSeed()) >>> 1;
/*      */         }
/*  334 */         i += paramInt1;
/*      */       }
/*      */       else {
/*  337 */         while ((i < paramInt1) || (i >= paramInt2))
/*  338 */           i = mix32(nextSeed());
/*      */       }
/*      */     }
/*  341 */     return i;
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
/*  352 */     double d = (nextLong() >>> 11) * 1.1102230246251565E-16D;
/*  353 */     if (paramDouble1 < paramDouble2) {
/*  354 */       d = d * (paramDouble2 - paramDouble1) + paramDouble1;
/*  355 */       if (d >= paramDouble2)
/*  356 */         d = Double.longBitsToDouble(Double.doubleToLongBits(paramDouble2) - 1L);
/*      */     }
/*  358 */     return d;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int nextInt()
/*      */   {
/*  367 */     return mix32(nextSeed());
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
/*  380 */     if (paramInt <= 0)
/*  381 */       throw new IllegalArgumentException("bound must be positive");
/*  382 */     int i = mix32(nextSeed());
/*  383 */     int j = paramInt - 1;
/*  384 */     if ((paramInt & j) == 0) {
/*  385 */       i &= j;
/*      */     } else {
/*  387 */       int k = i >>> 1;
/*  388 */       while (k + j - (i = k % paramInt) < 0) {
/*  389 */         k = mix32(nextSeed()) >>> 1;
/*      */       }
/*      */     }
/*  392 */     return i;
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
/*  407 */     if (paramInt1 >= paramInt2)
/*  408 */       throw new IllegalArgumentException("bound must be greater than origin");
/*  409 */     return internalNextInt(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long nextLong()
/*      */   {
/*  418 */     return mix64(nextSeed());
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
/*  431 */     if (paramLong <= 0L)
/*  432 */       throw new IllegalArgumentException("bound must be positive");
/*  433 */     long l1 = mix64(nextSeed());
/*  434 */     long l2 = paramLong - 1L;
/*  435 */     if ((paramLong & l2) == 0L) {
/*  436 */       l1 &= l2;
/*      */     } else {
/*  438 */       long l3 = l1 >>> 1;
/*  439 */       while (l3 + l2 - (l1 = l3 % paramLong) < 0L) {
/*  440 */         l3 = mix64(nextSeed()) >>> 1;
/*      */       }
/*      */     }
/*  443 */     return l1;
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
/*  458 */     if (paramLong1 >= paramLong2)
/*  459 */       throw new IllegalArgumentException("bound must be greater than origin");
/*  460 */     return internalNextLong(paramLong1, paramLong2);
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
/*  471 */     return (mix64(nextSeed()) >>> 11) * 1.1102230246251565E-16D;
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
/*  484 */     if (paramDouble <= 0.0D)
/*  485 */       throw new IllegalArgumentException("bound must be positive");
/*  486 */     double d = (mix64(nextSeed()) >>> 11) * 1.1102230246251565E-16D * paramDouble;
/*      */     
/*  488 */     return d < paramDouble ? d : Double.longBitsToDouble(Double.doubleToLongBits(paramDouble) - 1L);
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
/*  503 */     if (paramDouble1 >= paramDouble2)
/*  504 */       throw new IllegalArgumentException("bound must be greater than origin");
/*  505 */     return internalNextDouble(paramDouble1, paramDouble2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean nextBoolean()
/*      */   {
/*  514 */     return mix32(nextSeed()) < 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float nextFloat()
/*      */   {
/*  525 */     return (mix32(nextSeed()) >>> 8) * 5.9604645E-8F;
/*      */   }
/*      */   
/*      */   public double nextGaussian()
/*      */   {
/*  530 */     Double localDouble = (Double)nextLocalGaussian.get();
/*  531 */     if (localDouble != null) {
/*  532 */       nextLocalGaussian.set(null);
/*  533 */       return localDouble.doubleValue(); }
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*  537 */     do { d1 = 2.0D * nextDouble() - 1.0D;
/*  538 */       d2 = 2.0D * nextDouble() - 1.0D;
/*  539 */       d3 = d1 * d1 + d2 * d2;
/*  540 */     } while ((d3 >= 1.0D) || (d3 == 0.0D));
/*  541 */     double d4 = StrictMath.sqrt(-2.0D * StrictMath.log(d3) / d3);
/*  542 */     nextLocalGaussian.set(new Double(d2 * d4));
/*  543 */     return d1 * d4;
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
/*  560 */     if (paramLong < 0L) {
/*  561 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  563 */     return StreamSupport.intStream(new RandomIntsSpliterator(0L, paramLong, Integer.MAX_VALUE, 0), false);
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
/*      */   public IntStream ints()
/*      */   {
/*  580 */     return StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
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
/*  602 */     if (paramLong < 0L)
/*  603 */       throw new IllegalArgumentException("size must be non-negative");
/*  604 */     if (paramInt1 >= paramInt2) {
/*  605 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  607 */     return StreamSupport.intStream(new RandomIntsSpliterator(0L, paramLong, paramInt1, paramInt2), false);
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
/*      */   public IntStream ints(int paramInt1, int paramInt2)
/*      */   {
/*  629 */     if (paramInt1 >= paramInt2) {
/*  630 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  632 */     return StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, paramInt1, paramInt2), false);
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
/*  648 */     if (paramLong < 0L) {
/*  649 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  651 */     return StreamSupport.longStream(new RandomLongsSpliterator(0L, paramLong, Long.MAX_VALUE, 0L), false);
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
/*      */   public LongStream longs()
/*      */   {
/*  668 */     return StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
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
/*  690 */     if (paramLong1 < 0L)
/*  691 */       throw new IllegalArgumentException("size must be non-negative");
/*  692 */     if (paramLong2 >= paramLong3) {
/*  693 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  695 */     return StreamSupport.longStream(new RandomLongsSpliterator(0L, paramLong1, paramLong2, paramLong3), false);
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
/*      */   public LongStream longs(long paramLong1, long paramLong2)
/*      */   {
/*  717 */     if (paramLong1 >= paramLong2) {
/*  718 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  720 */     return StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, paramLong1, paramLong2), false);
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
/*      */   public DoubleStream doubles(long paramLong)
/*      */   {
/*  737 */     if (paramLong < 0L) {
/*  738 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  740 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, paramLong, Double.MAX_VALUE, 0.0D), false);
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
/*      */   public DoubleStream doubles()
/*      */   {
/*  758 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0D), false);
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
/*  781 */     if (paramLong < 0L)
/*  782 */       throw new IllegalArgumentException("size must be non-negative");
/*  783 */     if (paramDouble1 >= paramDouble2) {
/*  784 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  786 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, paramLong, paramDouble1, paramDouble2), false);
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
/*      */   public DoubleStream doubles(double paramDouble1, double paramDouble2)
/*      */   {
/*  808 */     if (paramDouble1 >= paramDouble2) {
/*  809 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  811 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, paramDouble1, paramDouble2), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class RandomIntsSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     long index;
/*      */     
/*      */ 
/*      */     final long fence;
/*      */     
/*      */     final int origin;
/*      */     
/*      */     final int bound;
/*      */     
/*      */ 
/*      */     RandomIntsSpliterator(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/*      */     {
/*  831 */       this.index = paramLong1;this.fence = paramLong2;
/*  832 */       this.origin = paramInt1;this.bound = paramInt2;
/*      */     }
/*      */     
/*      */     public RandomIntsSpliterator trySplit() {
/*  836 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/*  837 */       return l2 <= l1 ? null : new RandomIntsSpliterator(l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/*  842 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  846 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*      */     {
/*  851 */       if (paramIntConsumer == null) throw new NullPointerException();
/*  852 */       long l1 = this.index;long l2 = this.fence;
/*  853 */       if (l1 < l2) {
/*  854 */         paramIntConsumer.accept(ThreadLocalRandom.current().internalNextInt(this.origin, this.bound));
/*  855 */         this.index = (l1 + 1L);
/*  856 */         return true;
/*      */       }
/*  858 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(IntConsumer paramIntConsumer) {
/*  862 */       if (paramIntConsumer == null) throw new NullPointerException();
/*  863 */       long l1 = this.index;long l2 = this.fence;
/*  864 */       if (l1 < l2) {
/*  865 */         this.index = l2;
/*  866 */         int i = this.origin;int j = this.bound;
/*  867 */         ThreadLocalRandom localThreadLocalRandom = ThreadLocalRandom.current();
/*      */         do {
/*  869 */           paramIntConsumer.accept(localThreadLocalRandom.internalNextInt(i, j));
/*  870 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class RandomLongsSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     long index;
/*      */     final long fence;
/*      */     final long origin;
/*      */     final long bound;
/*      */     
/*      */     RandomLongsSpliterator(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*      */     {
/*  885 */       this.index = paramLong1;this.fence = paramLong2;
/*  886 */       this.origin = paramLong3;this.bound = paramLong4;
/*      */     }
/*      */     
/*      */     public RandomLongsSpliterator trySplit() {
/*  890 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/*  891 */       return l2 <= l1 ? null : new RandomLongsSpliterator(l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/*  896 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  900 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*      */     {
/*  905 */       if (paramLongConsumer == null) throw new NullPointerException();
/*  906 */       long l1 = this.index;long l2 = this.fence;
/*  907 */       if (l1 < l2) {
/*  908 */         paramLongConsumer.accept(ThreadLocalRandom.current().internalNextLong(this.origin, this.bound));
/*  909 */         this.index = (l1 + 1L);
/*  910 */         return true;
/*      */       }
/*  912 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(LongConsumer paramLongConsumer) {
/*  916 */       if (paramLongConsumer == null) throw new NullPointerException();
/*  917 */       long l1 = this.index;long l2 = this.fence;
/*  918 */       if (l1 < l2) {
/*  919 */         this.index = l2;
/*  920 */         long l3 = this.origin;long l4 = this.bound;
/*  921 */         ThreadLocalRandom localThreadLocalRandom = ThreadLocalRandom.current();
/*      */         do {
/*  923 */           paramLongConsumer.accept(localThreadLocalRandom.internalNextLong(l3, l4));
/*  924 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class RandomDoublesSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     long index;
/*      */     final long fence;
/*      */     final double origin;
/*      */     final double bound;
/*      */     
/*      */     RandomDoublesSpliterator(long paramLong1, long paramLong2, double paramDouble1, double paramDouble2)
/*      */     {
/*  940 */       this.index = paramLong1;this.fence = paramLong2;
/*  941 */       this.origin = paramDouble1;this.bound = paramDouble2;
/*      */     }
/*      */     
/*      */     public RandomDoublesSpliterator trySplit() {
/*  945 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/*  946 */       return l2 <= l1 ? null : new RandomDoublesSpliterator(l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/*  951 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  955 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer paramDoubleConsumer)
/*      */     {
/*  960 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/*  961 */       long l1 = this.index;long l2 = this.fence;
/*  962 */       if (l1 < l2) {
/*  963 */         paramDoubleConsumer.accept(ThreadLocalRandom.current().internalNextDouble(this.origin, this.bound));
/*  964 */         this.index = (l1 + 1L);
/*  965 */         return true;
/*      */       }
/*  967 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer paramDoubleConsumer) {
/*  971 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/*  972 */       long l1 = this.index;long l2 = this.fence;
/*  973 */       if (l1 < l2) {
/*  974 */         this.index = l2;
/*  975 */         double d1 = this.origin;double d2 = this.bound;
/*  976 */         ThreadLocalRandom localThreadLocalRandom = ThreadLocalRandom.current();
/*      */         do {
/*  978 */           paramDoubleConsumer.accept(localThreadLocalRandom.internalNextDouble(d1, d2));
/*  979 */         } while (++l1 < l2);
/*      */       }
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
/*      */   static final int getProbe()
/*      */   {
/* 1010 */     return UNSAFE.getInt(Thread.currentThread(), PROBE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int advanceProbe(int paramInt)
/*      */   {
/* 1018 */     paramInt ^= paramInt << 13;
/* 1019 */     paramInt ^= paramInt >>> 17;
/* 1020 */     paramInt ^= paramInt << 5;
/* 1021 */     UNSAFE.putInt(Thread.currentThread(), PROBE, paramInt);
/* 1022 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final int nextSecondarySeed()
/*      */   {
/* 1030 */     Thread localThread = Thread.currentThread();
/* 1031 */     int i; if ((i = UNSAFE.getInt(localThread, SECONDARY)) != 0) {
/* 1032 */       i ^= i << 13;
/* 1033 */       i ^= i >>> 17;
/* 1034 */       i ^= i << 5;
/*      */     }
/*      */     else {
/* 1037 */       localInit();
/* 1038 */       if ((i = (int)UNSAFE.getLong(localThread, SEED)) == 0)
/* 1039 */         i = 1;
/*      */     }
/* 1041 */     UNSAFE.putInt(localThread, SECONDARY, i);
/* 1042 */     return i;
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
/* 1055 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("rnd", Long.TYPE), new ObjectStreamField("initialized", Boolean.TYPE) };
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   private static final long SEED;
/*      */   
/*      */   private static final long PROBE;
/*      */   
/*      */   private static final long SECONDARY;
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1068 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 1069 */     localPutField.put("rnd", UNSAFE.getLong(Thread.currentThread(), SEED));
/* 1070 */     localPutField.put("initialized", true);
/* 1071 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object readResolve()
/*      */   {
/* 1079 */     return current();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 1089 */       UNSAFE = Unsafe.getUnsafe();
/* 1090 */       Class localClass = Thread.class;
/*      */       
/* 1092 */       SEED = UNSAFE.objectFieldOffset(localClass.getDeclaredField("threadLocalRandomSeed"));
/*      */       
/* 1094 */       PROBE = UNSAFE.objectFieldOffset(localClass.getDeclaredField("threadLocalRandomProbe"));
/*      */       
/* 1096 */       SECONDARY = UNSAFE.objectFieldOffset(localClass.getDeclaredField("threadLocalRandomSecondarySeed"));
/*      */     } catch (Exception localException) {
/* 1098 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ThreadLocalRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */