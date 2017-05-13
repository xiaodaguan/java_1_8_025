/*     */ package java.time.format;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum SignStyle
/*     */ {
/*  84 */   NORMAL, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  92 */   ALWAYS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */   NEVER, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 106 */   NOT_NEGATIVE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 115 */   EXCEEDS_PAD;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private SignStyle() {}
/*     */   
/*     */ 
/*     */ 
/*     */   boolean parse(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/* 126 */     switch (ordinal())
/*     */     {
/*     */     case 0: 
/* 129 */       return (!paramBoolean1) || (!paramBoolean2);
/*     */     case 1: 
/*     */     case 4: 
/* 132 */       return true;
/*     */     }
/*     */     
/* 135 */     return (!paramBoolean2) && (!paramBoolean3);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/format/SignStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */