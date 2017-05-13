/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ThaiBuddhistEra
/*     */   implements Era
/*     */ {
/* 113 */   BEFORE_BE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 118 */   BE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ThaiBuddhistEra() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ThaiBuddhistEra of(int paramInt)
/*     */   {
/* 132 */     switch (paramInt) {
/*     */     case 0: 
/* 134 */       return BEFORE_BE;
/*     */     case 1: 
/* 136 */       return BE;
/*     */     }
/* 138 */     throw new DateTimeException("Invalid era: " + paramInt);
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
/*     */   public int getValue()
/*     */   {
/* 152 */     return ordinal();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/ThaiBuddhistEra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */