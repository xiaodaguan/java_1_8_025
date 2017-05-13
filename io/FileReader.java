/*    */ package java.io;
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
/*    */ public class FileReader
/*    */   extends InputStreamReader
/*    */ {
/*    */   public FileReader(String paramString)
/*    */     throws FileNotFoundException
/*    */   {
/* 58 */     super(new FileInputStream(paramString));
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
/*    */   public FileReader(File paramFile)
/*    */     throws FileNotFoundException
/*    */   {
/* 72 */     super(new FileInputStream(paramFile));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FileReader(FileDescriptor paramFileDescriptor)
/*    */   {
/* 82 */     super(new FileInputStream(paramFileDescriptor));
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/FileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */