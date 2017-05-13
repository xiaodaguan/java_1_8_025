/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.util.List;
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
/*     */ public abstract interface ChronoPeriod
/*     */   extends TemporalAmount
/*     */ {
/*     */   public static ChronoPeriod between(ChronoLocalDate paramChronoLocalDate1, ChronoLocalDate paramChronoLocalDate2)
/*     */   {
/* 117 */     Objects.requireNonNull(paramChronoLocalDate1, "startDateInclusive");
/* 118 */     Objects.requireNonNull(paramChronoLocalDate2, "endDateExclusive");
/* 119 */     return paramChronoLocalDate1.until(paramChronoLocalDate2);
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
/*     */   public abstract long get(TemporalUnit paramTemporalUnit);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<TemporalUnit> getUnits();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Chronology getChronology();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isZero()
/*     */   {
/* 173 */     for (TemporalUnit localTemporalUnit : getUnits()) {
/* 174 */       if (get(localTemporalUnit) != 0L) {
/* 175 */         return false;
/*     */       }
/*     */     }
/* 178 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isNegative()
/*     */   {
/* 187 */     for (TemporalUnit localTemporalUnit : getUnits()) {
/* 188 */       if (get(localTemporalUnit) < 0L) {
/* 189 */         return true;
/*     */       }
/*     */     }
/* 192 */     return false;
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
/*     */   public abstract ChronoPeriod plus(TemporalAmount paramTemporalAmount);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoPeriod minus(TemporalAmount paramTemporalAmount);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract ChronoPeriod multipliedBy(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChronoPeriod negated()
/*     */   {
/* 256 */     return multipliedBy(-1);
/*     */   }
/*     */   
/*     */   public abstract ChronoPeriod normalized();
/*     */   
/*     */   public abstract Temporal addTo(Temporal paramTemporal);
/*     */   
/*     */   public abstract Temporal subtractFrom(Temporal paramTemporal);
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ChronoPeriod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */