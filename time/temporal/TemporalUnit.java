/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.Duration;
/*     */ import java.time.LocalTime;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.ChronoLocalDateTime;
/*     */ import java.time.chrono.ChronoZonedDateTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface TemporalUnit
/*     */ {
/*     */   public abstract Duration getDuration();
/*     */   
/*     */   public abstract boolean isDurationEstimated();
/*     */   
/*     */   public abstract boolean isDateBased();
/*     */   
/*     */   public abstract boolean isTimeBased();
/*     */   
/*     */   public boolean isSupportedBy(Temporal paramTemporal)
/*     */   {
/* 169 */     if ((paramTemporal instanceof LocalTime)) {
/* 170 */       return isTimeBased();
/*     */     }
/* 172 */     if ((paramTemporal instanceof ChronoLocalDate)) {
/* 173 */       return isDateBased();
/*     */     }
/* 175 */     if (((paramTemporal instanceof ChronoLocalDateTime)) || ((paramTemporal instanceof ChronoZonedDateTime))) {
/* 176 */       return true;
/*     */     }
/*     */     try {
/* 179 */       paramTemporal.plus(1L, this);
/* 180 */       return true;
/*     */     } catch (UnsupportedTemporalTypeException localUnsupportedTemporalTypeException) {
/* 182 */       return false;
/*     */     } catch (RuntimeException localRuntimeException1) {
/*     */       try {
/* 185 */         paramTemporal.plus(-1L, this);
/* 186 */         return true;
/*     */       } catch (RuntimeException localRuntimeException2) {} }
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   public abstract <R extends Temporal> R addTo(R paramR, long paramLong);
/*     */   
/*     */   public abstract long between(Temporal paramTemporal1, Temporal paramTemporal2);
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/temporal/TemporalUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */