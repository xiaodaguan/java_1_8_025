/*    */ package java.lang;
/*    */ 
/*    */ import java.io.File;
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
/*    */ class ClassLoaderHelper
/*    */ {
/*    */   static File mapAlternativeName(File paramFile)
/*    */   {
/* 40 */     String str = paramFile.toString();
/* 41 */     int i = str.lastIndexOf('.');
/* 42 */     if (i < 0) {
/* 43 */       return null;
/*    */     }
/* 45 */     return new File(str.substring(0, i) + ".jnilib");
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ClassLoaderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */