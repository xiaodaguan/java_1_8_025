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
/*     */ public enum IsoEra
/*     */   implements Era
/*     */ {
/* 111 */   BCE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 116 */   CE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private IsoEra() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static IsoEra of(int paramInt)
/*     */   {
/* 130 */     switch (paramInt) {
/*     */     case 0: 
/* 132 */       return BCE;
/*     */     case 1: 
/* 134 */       return CE;
/*     */     }
/* 136 */     throw new DateTimeException("Invalid era: " + paramInt);
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
/* 150 */     return ordinal();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/chrono/IsoEra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */