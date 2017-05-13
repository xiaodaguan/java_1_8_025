/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.stream.DoubleStream;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Random
/*      */   implements Serializable
/*      */ {
/*      */   static final long serialVersionUID = 3905348978240129619L;
/*      */   private final AtomicLong seed;
/*      */   private static final long multiplier = 25214903917L;
/*      */   private static final long addend = 11L;
/*      */   private static final long mask = 281474976710655L;
/*      */   private static final double DOUBLE_UNIT = 1.1102230246251565E-16D;
/*      */   static final String BadBound = "bound must be positive";
/*      */   static final String BadRange = "bound must be greater than origin";
/*      */   static final String BadSize = "size must be non-negative";
/*      */   
/*      */   public Random()
/*      */   {
/*  105 */     this(seedUniquifier() ^ System.nanoTime());
/*      */   }
/*      */   
/*      */   private static long seedUniquifier()
/*      */   {
/*      */     for (;;)
/*      */     {
/*  112 */       long l1 = seedUniquifier.get();
/*  113 */       long l2 = l1 * 181783497276652981L;
/*  114 */       if (seedUniquifier.compareAndSet(l1, l2))
/*  115 */         return l2;
/*      */     }
/*      */   }
/*      */   
/*  119 */   private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private double nextNextGaussian;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Random(long paramLong)
/*      */   {
/*  136 */     if (getClass() == Random.class) {
/*  137 */       this.seed = new AtomicLong(initialScramble(paramLong));
/*      */     }
/*      */     else {
/*  140 */       this.seed = new AtomicLong();
/*  141 */       setSeed(paramLong);
/*      */     }
/*      */   }
/*      */   
/*      */   private static long initialScramble(long paramLong) {
/*  146 */     return (paramLong ^ 0x5DEECE66D) & 0xFFFFFFFFFFFF;
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
/*      */   public synchronized void setSeed(long paramLong)
/*      */   {
/*  169 */     this.seed.set(initialScramble(paramLong));
/*  170 */     this.haveNextNextGaussian = false;
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
/*      */   protected int next(int paramInt)
/*      */   {
/*  200 */     AtomicLong localAtomicLong = this.seed;
/*      */     long l1;
/*  202 */     long l2; do { l1 = localAtomicLong.get();
/*  203 */       l2 = l1 * 25214903917L + 11L & 0xFFFFFFFFFFFF;
/*  204 */     } while (!localAtomicLong.compareAndSet(l1, l2));
/*  205 */     return (int)(l2 >>> 48 - paramInt);
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
/*      */   public void nextBytes(byte[] paramArrayOfByte)
/*      */   {
/*  228 */     int i = 0; for (int j = paramArrayOfByte.length; i < j;) {
/*  229 */       int k = nextInt();
/*  230 */       int m = Math.min(j - i, 4);
/*  231 */       for (; m-- > 0; k >>= 8) {
/*  232 */         paramArrayOfByte[(i++)] = ((byte)k);
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
/*      */   final long internalNextLong(long paramLong1, long paramLong2)
/*      */   {
/*  245 */     long l1 = nextLong();
/*  246 */     if (paramLong1 < paramLong2) {
/*  247 */       long l2 = paramLong2 - paramLong1;long l3 = l2 - 1L;
/*  248 */       if ((l2 & l3) == 0L) {
/*  249 */         l1 = (l1 & l3) + paramLong1;
/*  250 */       } else if (l2 > 0L) {
/*  251 */         long l4 = l1 >>> 1;
/*  252 */         while (l4 + l3 - (l1 = l4 % l2) < 0L) {
/*  253 */           l4 = nextLong() >>> 1;
/*      */         }
/*  255 */         l1 += paramLong1;
/*      */       }
/*      */       else {
/*  258 */         while ((l1 < paramLong1) || (l1 >= paramLong2))
/*  259 */           l1 = nextLong();
/*      */       }
/*      */     }
/*  262 */     return l1;
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
/*      */   final int internalNextInt(int paramInt1, int paramInt2)
/*      */   {
/*  276 */     if (paramInt1 < paramInt2) {
/*  277 */       int i = paramInt2 - paramInt1;
/*  278 */       if (i > 0) {
/*  279 */         return nextInt(i) + paramInt1;
/*      */       }
/*      */       int j;
/*      */       do
/*      */       {
/*  284 */         j = nextInt();
/*  285 */       } while ((j < paramInt1) || (j >= paramInt2));
/*  286 */       return j;
/*      */     }
/*      */     
/*      */ 
/*  290 */     return nextInt();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final double internalNextDouble(double paramDouble1, double paramDouble2)
/*      */   {
/*  302 */     double d = nextDouble();
/*  303 */     if (paramDouble1 < paramDouble2) {
/*  304 */       d = d * (paramDouble2 - paramDouble1) + paramDouble1;
/*  305 */       if (d >= paramDouble2)
/*  306 */         d = Double.longBitsToDouble(Double.doubleToLongBits(paramDouble2) - 1L);
/*      */     }
/*  308 */     return d;
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
/*      */   public int nextInt()
/*      */   {
/*  329 */     return next(32);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  387 */     if (paramInt <= 0) {
/*  388 */       throw new IllegalArgumentException("bound must be positive");
/*      */     }
/*  390 */     int i = next(31);
/*  391 */     int j = paramInt - 1;
/*  392 */     if ((paramInt & j) == 0) {
/*  393 */       i = (int)(paramInt * i >> 31);
/*      */     } else {
/*  395 */       int k = i;
/*  396 */       while (k - (i = k % paramInt) + j < 0) {
/*  397 */         k = next(31);
/*      */       }
/*      */     }
/*  400 */     return i;
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
/*      */   public long nextLong()
/*      */   {
/*  424 */     return (next(32) << 32) + next(32);
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
/*      */   public boolean nextBoolean()
/*      */   {
/*  448 */     return next(1) != 0;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public float nextFloat()
/*      */   {
/*  489 */     return next(24) / 1.6777216E7F;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double nextDouble()
/*      */   {
/*  532 */     return ((next(26) << 27) + next(27)) * 1.1102230246251565E-16D;
/*      */   }
/*      */   
/*      */ 
/*  536 */   private boolean haveNextNextGaussian = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized double nextGaussian()
/*      */   {
/*  585 */     if (this.haveNextNextGaussian) {
/*  586 */       this.haveNextNextGaussian = false;
/*  587 */       return this.nextNextGaussian; }
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*  591 */     do { d1 = 2.0D * nextDouble() - 1.0D;
/*  592 */       d2 = 2.0D * nextDouble() - 1.0D;
/*  593 */       d3 = d1 * d1 + d2 * d2;
/*  594 */     } while ((d3 >= 1.0D) || (d3 == 0.0D));
/*  595 */     double d4 = StrictMath.sqrt(-2.0D * StrictMath.log(d3) / d3);
/*  596 */     this.nextNextGaussian = (d2 * d4);
/*  597 */     this.haveNextNextGaussian = true;
/*  598 */     return d1 * d4;
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
/*      */   public IntStream ints(long paramLong)
/*      */   {
/*  619 */     if (paramLong < 0L) {
/*  620 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  622 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, Integer.MAX_VALUE, 0), false);
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
/*      */   public IntStream ints()
/*      */   {
/*  642 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
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
/*      */ 
/*      */   public IntStream ints(long paramLong, int paramInt1, int paramInt2)
/*      */   {
/*  681 */     if (paramLong < 0L)
/*  682 */       throw new IllegalArgumentException("size must be non-negative");
/*  683 */     if (paramInt1 >= paramInt2) {
/*  684 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  686 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, paramLong, paramInt1, paramInt2), false);
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
/*      */ 
/*      */   public IntStream ints(int paramInt1, int paramInt2)
/*      */   {
/*  725 */     if (paramInt1 >= paramInt2) {
/*  726 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  728 */     return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, paramInt1, paramInt2), false);
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
/*      */   public LongStream longs(long paramLong)
/*      */   {
/*  747 */     if (paramLong < 0L) {
/*  748 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  750 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong, Long.MAX_VALUE, 0L), false);
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
/*      */   public LongStream longs()
/*      */   {
/*  770 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LongStream longs(long paramLong1, long paramLong2, long paramLong3)
/*      */   {
/*  814 */     if (paramLong1 < 0L)
/*  815 */       throw new IllegalArgumentException("size must be non-negative");
/*  816 */     if (paramLong2 >= paramLong3) {
/*  817 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  819 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, paramLong1, paramLong2, paramLong3), false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LongStream longs(long paramLong1, long paramLong2)
/*      */   {
/*  863 */     if (paramLong1 >= paramLong2) {
/*  864 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  866 */     return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, paramLong1, paramLong2), false);
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
/*      */   public DoubleStream doubles(long paramLong)
/*      */   {
/*  886 */     if (paramLong < 0L) {
/*  887 */       throw new IllegalArgumentException("size must be non-negative");
/*      */     }
/*  889 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, Double.MAX_VALUE, 0.0D), false);
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
/*      */   public DoubleStream doubles()
/*      */   {
/*  910 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0D), false);
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
/*      */   public DoubleStream doubles(long paramLong, double paramDouble1, double paramDouble2)
/*      */   {
/*  944 */     if (paramLong < 0L)
/*  945 */       throw new IllegalArgumentException("size must be non-negative");
/*  946 */     if (paramDouble1 >= paramDouble2) {
/*  947 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  949 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, paramLong, paramDouble1, paramDouble2), false);
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
/*      */   public DoubleStream doubles(double paramDouble1, double paramDouble2)
/*      */   {
/*  982 */     if (paramDouble1 >= paramDouble2) {
/*  983 */       throw new IllegalArgumentException("bound must be greater than origin");
/*      */     }
/*  985 */     return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, paramDouble1, paramDouble2), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class RandomIntsSpliterator
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     final Random rng;
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
/*      */     RandomIntsSpliterator(Random paramRandom, long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/*      */     {
/* 1006 */       this.rng = paramRandom;this.index = paramLong1;this.fence = paramLong2;
/* 1007 */       this.origin = paramInt1;this.bound = paramInt2;
/*      */     }
/*      */     
/*      */     public RandomIntsSpliterator trySplit() {
/* 1011 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/* 1012 */       return l2 <= l1 ? null : new RandomIntsSpliterator(this.rng, l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 1017 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1021 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(IntConsumer paramIntConsumer)
/*      */     {
/* 1026 */       if (paramIntConsumer == null) throw new NullPointerException();
/* 1027 */       long l1 = this.index;long l2 = this.fence;
/* 1028 */       if (l1 < l2) {
/* 1029 */         paramIntConsumer.accept(this.rng.internalNextInt(this.origin, this.bound));
/* 1030 */         this.index = (l1 + 1L);
/* 1031 */         return true;
/*      */       }
/* 1033 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(IntConsumer paramIntConsumer) {
/* 1037 */       if (paramIntConsumer == null) throw new NullPointerException();
/* 1038 */       long l1 = this.index;long l2 = this.fence;
/* 1039 */       if (l1 < l2) {
/* 1040 */         this.index = l2;
/* 1041 */         Random localRandom = this.rng;
/* 1042 */         int i = this.origin;int j = this.bound;
/*      */         do {
/* 1044 */           paramIntConsumer.accept(localRandom.internalNextInt(i, j));
/* 1045 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class RandomLongsSpliterator
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     final Random rng;
/*      */     long index;
/*      */     final long fence;
/*      */     final long origin;
/*      */     final long bound;
/*      */     
/*      */     RandomLongsSpliterator(Random paramRandom, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*      */     {
/* 1061 */       this.rng = paramRandom;this.index = paramLong1;this.fence = paramLong2;
/* 1062 */       this.origin = paramLong3;this.bound = paramLong4;
/*      */     }
/*      */     
/*      */     public RandomLongsSpliterator trySplit() {
/* 1066 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/* 1067 */       return l2 <= l1 ? null : new RandomLongsSpliterator(this.rng, l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 1072 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1076 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(LongConsumer paramLongConsumer)
/*      */     {
/* 1081 */       if (paramLongConsumer == null) throw new NullPointerException();
/* 1082 */       long l1 = this.index;long l2 = this.fence;
/* 1083 */       if (l1 < l2) {
/* 1084 */         paramLongConsumer.accept(this.rng.internalNextLong(this.origin, this.bound));
/* 1085 */         this.index = (l1 + 1L);
/* 1086 */         return true;
/*      */       }
/* 1088 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(LongConsumer paramLongConsumer) {
/* 1092 */       if (paramLongConsumer == null) throw new NullPointerException();
/* 1093 */       long l1 = this.index;long l2 = this.fence;
/* 1094 */       if (l1 < l2) {
/* 1095 */         this.index = l2;
/* 1096 */         Random localRandom = this.rng;
/* 1097 */         long l3 = this.origin;long l4 = this.bound;
/*      */         do {
/* 1099 */           paramLongConsumer.accept(localRandom.internalNextLong(l3, l4));
/* 1100 */         } while (++l1 < l2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class RandomDoublesSpliterator
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     final Random rng;
/*      */     long index;
/*      */     final long fence;
/*      */     final double origin;
/*      */     final double bound;
/*      */     
/*      */     RandomDoublesSpliterator(Random paramRandom, long paramLong1, long paramLong2, double paramDouble1, double paramDouble2)
/*      */     {
/* 1117 */       this.rng = paramRandom;this.index = paramLong1;this.fence = paramLong2;
/* 1118 */       this.origin = paramDouble1;this.bound = paramDouble2;
/*      */     }
/*      */     
/*      */     public RandomDoublesSpliterator trySplit() {
/* 1122 */       long l1 = this.index;long l2 = l1 + this.fence >>> 1;
/* 1123 */       return l2 <= l1 ? null : new RandomDoublesSpliterator(this.rng, l1, this.index = l2, this.origin, this.bound);
/*      */     }
/*      */     
/*      */     public long estimateSize()
/*      */     {
/* 1128 */       return this.fence - this.index;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1132 */       return 17728;
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer paramDoubleConsumer)
/*      */     {
/* 1137 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/* 1138 */       long l1 = this.index;long l2 = this.fence;
/* 1139 */       if (l1 < l2) {
/* 1140 */         paramDoubleConsumer.accept(this.rng.internalNextDouble(this.origin, this.bound));
/* 1141 */         this.index = (l1 + 1L);
/* 1142 */         return true;
/*      */       }
/* 1144 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer paramDoubleConsumer) {
/* 1148 */       if (paramDoubleConsumer == null) throw new NullPointerException();
/* 1149 */       long l1 = this.index;long l2 = this.fence;
/* 1150 */       if (l1 < l2) {
/* 1151 */         this.index = l2;
/* 1152 */         Random localRandom = this.rng;
/* 1153 */         double d1 = this.origin;double d2 = this.bound;
/*      */         do {
/* 1155 */           paramDoubleConsumer.accept(localRandom.internalNextDouble(d1, d2));
/* 1156 */         } while (++l1 < l2);
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
/* 1171 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("seed", Long.TYPE), new ObjectStreamField("nextNextGaussian", Double.TYPE), new ObjectStreamField("haveNextNextGaussian", Boolean.TYPE) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1184 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*      */     
/*      */ 
/*      */ 
/* 1188 */     long l = localGetField.get("seed", -1L);
/* 1189 */     if (l < 0L) {
/* 1190 */       throw new StreamCorruptedException("Random: invalid seed");
/*      */     }
/* 1192 */     resetSeed(l);
/* 1193 */     this.nextNextGaussian = localGetField.get("nextNextGaussian", 0.0D);
/* 1194 */     this.haveNextNextGaussian = localGetField.get("haveNextNextGaussian", false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1204 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/*      */     
/*      */ 
/* 1207 */     localPutField.put("seed", this.seed.get());
/* 1208 */     localPutField.put("nextNextGaussian", this.nextNextGaussian);
/* 1209 */     localPutField.put("haveNextNextGaussian", this.haveNextNextGaussian);
/*      */     
/*      */ 
/* 1212 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */   
/*      */ 
/* 1216 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */   private static final long seedOffset;
/*      */   
/*      */   static {
/*      */     try {
/* 1221 */       seedOffset = unsafe.objectFieldOffset(Random.class.getDeclaredField("seed"));
/* 1222 */     } catch (Exception localException) { throw new Error(localException);
/*      */     } }
/*      */   
/* 1225 */   private void resetSeed(long paramLong) { unsafe.putObjectVolatile(this, seedOffset, new AtomicLong(paramLong)); }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */