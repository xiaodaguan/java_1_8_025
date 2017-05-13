/*     */ package java.lang.management;
/*     */ 
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import sun.management.MemoryUsageCompositeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemoryUsage
/*     */ {
/*     */   private final long init;
/*     */   private final long used;
/*     */   private final long committed;
/*     */   private final long max;
/*     */   
/*     */   public MemoryUsage(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*     */   {
/* 139 */     if (paramLong1 < -1L) {
/* 140 */       throw new IllegalArgumentException("init parameter = " + paramLong1 + " is negative but not -1.");
/*     */     }
/*     */     
/* 143 */     if (paramLong4 < -1L) {
/* 144 */       throw new IllegalArgumentException("max parameter = " + paramLong4 + " is negative but not -1.");
/*     */     }
/*     */     
/* 147 */     if (paramLong2 < 0L) {
/* 148 */       throw new IllegalArgumentException("used parameter = " + paramLong2 + " is negative.");
/*     */     }
/*     */     
/* 151 */     if (paramLong3 < 0L) {
/* 152 */       throw new IllegalArgumentException("committed parameter = " + paramLong3 + " is negative.");
/*     */     }
/*     */     
/* 155 */     if (paramLong2 > paramLong3) {
/* 156 */       throw new IllegalArgumentException("used = " + paramLong2 + " should be <= committed = " + paramLong3);
/*     */     }
/*     */     
/* 159 */     if ((paramLong4 >= 0L) && (paramLong3 > paramLong4)) {
/* 160 */       throw new IllegalArgumentException("committed = " + paramLong3 + " should be < max = " + paramLong4);
/*     */     }
/*     */     
/*     */ 
/* 164 */     this.init = paramLong1;
/* 165 */     this.used = paramLong2;
/* 166 */     this.committed = paramLong3;
/* 167 */     this.max = paramLong4;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private MemoryUsage(CompositeData paramCompositeData)
/*     */   {
/* 176 */     MemoryUsageCompositeData.validateCompositeData(paramCompositeData);
/*     */     
/* 178 */     this.init = MemoryUsageCompositeData.getInit(paramCompositeData);
/* 179 */     this.used = MemoryUsageCompositeData.getUsed(paramCompositeData);
/* 180 */     this.committed = MemoryUsageCompositeData.getCommitted(paramCompositeData);
/* 181 */     this.max = MemoryUsageCompositeData.getMax(paramCompositeData);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getInit()
/*     */   {
/* 193 */     return this.init;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getUsed()
/*     */   {
/* 203 */     return this.used;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getCommitted()
/*     */   {
/* 215 */     return this.committed;
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
/*     */   public long getMax()
/*     */   {
/* 233 */     return this.max;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 240 */     StringBuffer localStringBuffer = new StringBuffer();
/* 241 */     localStringBuffer.append("init = " + this.init + "(" + (this.init >> 10) + "K) ");
/* 242 */     localStringBuffer.append("used = " + this.used + "(" + (this.used >> 10) + "K) ");
/* 243 */     localStringBuffer.append("committed = " + this.committed + "(" + (this.committed >> 10) + "K) ");
/*     */     
/* 245 */     localStringBuffer.append("max = " + this.max + "(" + (this.max >> 10) + "K)");
/* 246 */     return localStringBuffer.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MemoryUsage from(CompositeData paramCompositeData)
/*     */   {
/* 290 */     if (paramCompositeData == null) {
/* 291 */       return null;
/*     */     }
/*     */     
/* 294 */     if ((paramCompositeData instanceof MemoryUsageCompositeData)) {
/* 295 */       return ((MemoryUsageCompositeData)paramCompositeData).getMemoryUsage();
/*     */     }
/* 297 */     return new MemoryUsage(paramCompositeData);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/MemoryUsage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */