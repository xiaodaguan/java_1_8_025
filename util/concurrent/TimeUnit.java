/*     */ package java.util.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TimeUnit
/*     */ {
/*  75 */   NANOSECONDS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  90 */   MICROSECONDS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 105 */   MILLISECONDS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 120 */   SECONDS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 135 */   MINUTES, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 150 */   HOURS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 165 */   DAYS;
/*     */   
/*     */ 
/*     */ 
/*     */   static final long C0 = 1L;
/*     */   
/*     */ 
/*     */   static final long C1 = 1000L;
/*     */   
/*     */ 
/*     */   static final long C2 = 1000000L;
/*     */   
/*     */   static final long C3 = 1000000000L;
/*     */   
/*     */   static final long C4 = 60000000000L;
/*     */   
/*     */   static final long C5 = 3600000000000L;
/*     */   
/*     */   static final long C6 = 86400000000000L;
/*     */   
/*     */   static final long MAX = Long.MAX_VALUE;
/*     */   
/*     */ 
/*     */   private TimeUnit() {}
/*     */   
/*     */ 
/*     */   static long x(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 193 */     if (paramLong1 > paramLong3) return Long.MAX_VALUE;
/* 194 */     if (paramLong1 < -paramLong3) return Long.MIN_VALUE;
/* 195 */     return paramLong1 * paramLong2;
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
/*     */   public long convert(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 222 */     throw new AbstractMethodError();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long toNanos(long paramLong)
/*     */   {
/* 234 */     throw new AbstractMethodError();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long toMicros(long paramLong)
/*     */   {
/* 246 */     throw new AbstractMethodError();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long toMillis(long paramLong)
/*     */   {
/* 258 */     throw new AbstractMethodError();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long toSeconds(long paramLong)
/*     */   {
/* 270 */     throw new AbstractMethodError();
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
/*     */   public long toMinutes(long paramLong)
/*     */   {
/* 283 */     throw new AbstractMethodError();
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
/*     */   public long toHours(long paramLong)
/*     */   {
/* 296 */     throw new AbstractMethodError();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long toDays(long paramLong)
/*     */   {
/* 307 */     throw new AbstractMethodError();
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
/*     */   abstract int excessNanos(long paramLong1, long paramLong2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void timedWait(Object paramObject, long paramLong)
/*     */     throws InterruptedException
/*     */   {
/* 345 */     if (paramLong > 0L) {
/* 346 */       long l = toMillis(paramLong);
/* 347 */       int i = excessNanos(paramLong, l);
/* 348 */       paramObject.wait(l, i);
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
/*     */   public void timedJoin(Thread paramThread, long paramLong)
/*     */     throws InterruptedException
/*     */   {
/* 365 */     if (paramLong > 0L) {
/* 366 */       long l = toMillis(paramLong);
/* 367 */       int i = excessNanos(paramLong, l);
/* 368 */       paramThread.join(l, i);
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
/*     */   public void sleep(long paramLong)
/*     */     throws InterruptedException
/*     */   {
/* 383 */     if (paramLong > 0L) {
/* 384 */       long l = toMillis(paramLong);
/* 385 */       int i = excessNanos(paramLong, l);
/* 386 */       Thread.sleep(l, i);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/TimeUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */