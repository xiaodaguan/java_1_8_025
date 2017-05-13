/*    */ package java.io;
/*    */ 
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UncheckedIOException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -8134305061645241065L;
/*    */   
/*    */   public UncheckedIOException(String paramString, IOException paramIOException)
/*    */   {
/* 49 */     super(paramString, (Throwable)Objects.requireNonNull(paramIOException));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UncheckedIOException(IOException paramIOException)
/*    */   {
/* 62 */     super((Throwable)Objects.requireNonNull(paramIOException));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IOException getCause()
/*    */   {
/* 72 */     return (IOException)super.getCause();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private void readObject(ObjectInputStream paramObjectInputStream)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 85 */     paramObjectInputStream.defaultReadObject();
/* 86 */     Throwable localThrowable = super.getCause();
/* 87 */     if (!(localThrowable instanceof IOException)) {
/* 88 */       throw new InvalidObjectException("Cause must be an IOException");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/UncheckedIOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */