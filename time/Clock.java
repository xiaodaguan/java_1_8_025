/*     */ package java.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Clock
/*     */ {
/*     */   public static Clock systemUTC()
/*     */   {
/* 155 */     return new SystemClock(ZoneOffset.UTC);
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
/*     */   public static Clock systemDefaultZone()
/*     */   {
/* 178 */     return new SystemClock(ZoneId.systemDefault());
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
/*     */   public static Clock system(ZoneId paramZoneId)
/*     */   {
/* 197 */     Objects.requireNonNull(paramZoneId, "zone");
/* 198 */     return new SystemClock(paramZoneId);
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
/*     */   public static Clock tickSeconds(ZoneId paramZoneId)
/*     */   {
/* 222 */     return new TickClock(system(paramZoneId), 1000000000L);
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
/*     */   public static Clock tickMinutes(ZoneId paramZoneId)
/*     */   {
/* 245 */     return new TickClock(system(paramZoneId), 60000000000L);
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
/*     */   public static Clock tick(Clock paramClock, Duration paramDuration)
/*     */   {
/* 280 */     Objects.requireNonNull(paramClock, "baseClock");
/* 281 */     Objects.requireNonNull(paramDuration, "tickDuration");
/* 282 */     if (paramDuration.isNegative()) {
/* 283 */       throw new IllegalArgumentException("Tick duration must not be negative");
/*     */     }
/* 285 */     long l = paramDuration.toNanos();
/* 286 */     if (l % 1000000L != 0L)
/*     */     {
/* 288 */       if (1000000000L % l != 0L)
/*     */       {
/*     */ 
/* 291 */         throw new IllegalArgumentException("Invalid tick duration"); }
/*     */     }
/* 293 */     if (l <= 1L) {
/* 294 */       return paramClock;
/*     */     }
/* 296 */     return new TickClock(paramClock, l);
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
/*     */   public static Clock fixed(Instant paramInstant, ZoneId paramZoneId)
/*     */   {
/* 315 */     Objects.requireNonNull(paramInstant, "fixedInstant");
/* 316 */     Objects.requireNonNull(paramZoneId, "zone");
/* 317 */     return new FixedClock(paramInstant, paramZoneId);
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
/*     */   public static Clock offset(Clock paramClock, Duration paramDuration)
/*     */   {
/* 341 */     Objects.requireNonNull(paramClock, "baseClock");
/* 342 */     Objects.requireNonNull(paramDuration, "offsetDuration");
/* 343 */     if (paramDuration.equals(Duration.ZERO)) {
/* 344 */       return paramClock;
/*     */     }
/* 346 */     return new OffsetClock(paramClock, paramDuration);
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
/*     */   public abstract ZoneId getZone();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Clock withZone(ZoneId paramZoneId);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long millis()
/*     */   {
/* 398 */     return instant().toEpochMilli();
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
/*     */   public abstract Instant instant();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 425 */     return super.equals(paramObject);
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
/*     */   public int hashCode()
/*     */   {
/* 439 */     return super.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */   static final class SystemClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 6740630888130243051L;
/*     */     private final ZoneId zone;
/*     */     
/*     */     SystemClock(ZoneId paramZoneId)
/*     */     {
/* 452 */       this.zone = paramZoneId;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 456 */       return this.zone;
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId paramZoneId) {
/* 460 */       if (paramZoneId.equals(this.zone)) {
/* 461 */         return this;
/*     */       }
/* 463 */       return new SystemClock(paramZoneId);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 467 */       return System.currentTimeMillis();
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 471 */       return Instant.ofEpochMilli(millis());
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 475 */       if ((paramObject instanceof SystemClock)) {
/* 476 */         return this.zone.equals(((SystemClock)paramObject).zone);
/*     */       }
/* 478 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 482 */       return this.zone.hashCode() + 1;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 486 */       return "SystemClock[" + this.zone + "]";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static final class FixedClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7430389292664866958L;
/*     */     private final Instant instant;
/*     */     private final ZoneId zone;
/*     */     
/*     */     FixedClock(Instant paramInstant, ZoneId paramZoneId)
/*     */     {
/* 501 */       this.instant = paramInstant;
/* 502 */       this.zone = paramZoneId;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 506 */       return this.zone;
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId paramZoneId) {
/* 510 */       if (paramZoneId.equals(this.zone)) {
/* 511 */         return this;
/*     */       }
/* 513 */       return new FixedClock(this.instant, paramZoneId);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 517 */       return this.instant.toEpochMilli();
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 521 */       return this.instant;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 525 */       if ((paramObject instanceof FixedClock)) {
/* 526 */         FixedClock localFixedClock = (FixedClock)paramObject;
/* 527 */         return (this.instant.equals(localFixedClock.instant)) && (this.zone.equals(localFixedClock.zone));
/*     */       }
/* 529 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 533 */       return this.instant.hashCode() ^ this.zone.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 537 */       return "FixedClock[" + this.instant + "," + this.zone + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class OffsetClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2007484719125426256L;
/*     */     private final Clock baseClock;
/*     */     private final Duration offset;
/*     */     
/*     */     OffsetClock(Clock paramClock, Duration paramDuration)
/*     */     {
/* 551 */       this.baseClock = paramClock;
/* 552 */       this.offset = paramDuration;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 556 */       return this.baseClock.getZone();
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId paramZoneId) {
/* 560 */       if (paramZoneId.equals(this.baseClock.getZone())) {
/* 561 */         return this;
/*     */       }
/* 563 */       return new OffsetClock(this.baseClock.withZone(paramZoneId), this.offset);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 567 */       return Math.addExact(this.baseClock.millis(), this.offset.toMillis());
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 571 */       return this.baseClock.instant().plus(this.offset);
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 575 */       if ((paramObject instanceof OffsetClock)) {
/* 576 */         OffsetClock localOffsetClock = (OffsetClock)paramObject;
/* 577 */         return (this.baseClock.equals(localOffsetClock.baseClock)) && (this.offset.equals(localOffsetClock.offset));
/*     */       }
/* 579 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 583 */       return this.baseClock.hashCode() ^ this.offset.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 587 */       return "OffsetClock[" + this.baseClock + "," + this.offset + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class TickClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 6504659149906368850L;
/*     */     private final Clock baseClock;
/*     */     private final long tickNanos;
/*     */     
/*     */     TickClock(Clock paramClock, long paramLong)
/*     */     {
/* 601 */       this.baseClock = paramClock;
/* 602 */       this.tickNanos = paramLong;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 606 */       return this.baseClock.getZone();
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId paramZoneId) {
/* 610 */       if (paramZoneId.equals(this.baseClock.getZone())) {
/* 611 */         return this;
/*     */       }
/* 613 */       return new TickClock(this.baseClock.withZone(paramZoneId), this.tickNanos);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 617 */       long l = this.baseClock.millis();
/* 618 */       return l - Math.floorMod(l, this.tickNanos / 1000000L);
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 622 */       if (this.tickNanos % 1000000L == 0L) {
/* 623 */         long l1 = this.baseClock.millis();
/* 624 */         return Instant.ofEpochMilli(l1 - Math.floorMod(l1, this.tickNanos / 1000000L));
/*     */       }
/* 626 */       Instant localInstant = this.baseClock.instant();
/* 627 */       long l2 = localInstant.getNano();
/* 628 */       long l3 = Math.floorMod(l2, this.tickNanos);
/* 629 */       return localInstant.minusNanos(l3);
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 633 */       if ((paramObject instanceof TickClock)) {
/* 634 */         TickClock localTickClock = (TickClock)paramObject;
/* 635 */         return (this.baseClock.equals(localTickClock.baseClock)) && (this.tickNanos == localTickClock.tickNanos);
/*     */       }
/* 637 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 641 */       return this.baseClock.hashCode() ^ (int)(this.tickNanos ^ this.tickNanos >>> 32);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 645 */       return "TickClock[" + this.baseClock + "," + Duration.ofNanos(this.tickNanos) + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/Clock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */