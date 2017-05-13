/*    */ package java.nio.file.spi;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
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
/*    */ public abstract class FileTypeDetector
/*    */ {
/*    */   private static Void checkPermission()
/*    */   {
/* 53 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 54 */     if (localSecurityManager != null)
/* 55 */       localSecurityManager.checkPermission(new RuntimePermission("fileTypeDetector"));
/* 56 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private FileTypeDetector(Void paramVoid) {}
/*    */   
/*    */ 
/*    */ 
/*    */   protected FileTypeDetector()
/*    */   {
/* 68 */     this(checkPermission());
/*    */   }
/*    */   
/*    */   public abstract String probeContentType(Path paramPath)
/*    */     throws IOException;
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/spi/FileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */