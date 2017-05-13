/*    */ package java.util.zip;
/*    */ 
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class CheckedOutputStream
/*    */   extends FilterOutputStream
/*    */ {
/*    */   private Checksum cksum;
/*    */   
/*    */   public CheckedOutputStream(OutputStream paramOutputStream, Checksum paramChecksum)
/*    */   {
/* 50 */     super(paramOutputStream);
/* 51 */     this.cksum = paramChecksum;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void write(int paramInt)
/*    */     throws IOException
/*    */   {
/* 60 */     this.out.write(paramInt);
/* 61 */     this.cksum.update(paramInt);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*    */     throws IOException
/*    */   {
/* 73 */     this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/* 74 */     this.cksum.update(paramArrayOfByte, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Checksum getChecksum()
/*    */   {
/* 82 */     return this.cksum;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/CheckedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */