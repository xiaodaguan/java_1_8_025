/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   protected OutputStream out;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FilterOutputStream(OutputStream paramOutputStream)
/*     */   {
/*  61 */     this.out = paramOutputStream;
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
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/*  77 */     this.out.write(paramInt);
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
/*     */   public void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*  97 */     write(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 121 */     if ((paramInt1 | paramInt2 | paramArrayOfByte.length - (paramInt2 + paramInt1) | paramInt1 + paramInt2) < 0) {
/* 122 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 124 */     for (int i = 0; i < paramInt2; i++) {
/* 125 */       write(paramArrayOfByte[(paramInt1 + i)]);
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
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 140 */     this.out.flush();
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 157 */     OutputStream localOutputStream = this.out;Object localObject1 = null;
/* 158 */     try { flush();
/*     */     }
/*     */     catch (Throwable localThrowable2)
/*     */     {
/* 157 */       localObject1 = localThrowable2;throw localThrowable2;
/*     */     } finally {
/* 159 */       if (localOutputStream != null) if (localObject1 != null) try { localOutputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject1).addSuppressed(localThrowable3); } else localOutputStream.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/FilterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */