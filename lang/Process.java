/*     */ package java.lang;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Process
/*     */ {
/*     */   public abstract OutputStream getOutputStream();
/*     */   
/*     */   public abstract InputStream getInputStream();
/*     */   
/*     */   public abstract InputStream getErrorStream();
/*     */   
/*     */   public abstract int waitFor()
/*     */     throws InterruptedException;
/*     */   
/*     */   public boolean waitFor(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 189 */     long l1 = System.nanoTime();
/* 190 */     long l2 = paramTimeUnit.toNanos(paramLong);
/*     */     do
/*     */     {
/*     */       try {
/* 194 */         exitValue();
/* 195 */         return true;
/*     */       } catch (IllegalThreadStateException localIllegalThreadStateException) {
/* 197 */         if (l2 > 0L) {
/* 198 */           Thread.sleep(
/* 199 */             Math.min(TimeUnit.NANOSECONDS.toMillis(l2) + 1L, 100L));
/*     */         }
/* 201 */         l2 = paramTimeUnit.toNanos(paramLong) - (System.nanoTime() - l1);
/* 202 */       } } while (l2 > 0L);
/* 203 */     return false;
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
/*     */   public abstract int exitValue();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void destroy();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Process destroyForcibly()
/*     */   {
/* 245 */     destroy();
/* 246 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAlive()
/*     */   {
/*     */     try
/*     */     {
/* 259 */       exitValue();
/* 260 */       return false;
/*     */     } catch (IllegalThreadStateException localIllegalThreadStateException) {}
/* 262 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Process.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */