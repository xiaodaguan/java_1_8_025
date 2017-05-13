/*    */ package java.io;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.List;
/*    */ import sun.misc.JavaLangAccess;
/*    */ import sun.misc.SharedSecrets;
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
/*    */ class DeleteOnExitHook
/*    */ {
/* 37 */   private static LinkedHashSet<String> files = new LinkedHashSet();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static
/*    */   {
/* 45 */     SharedSecrets.getJavaLangAccess().registerShutdownHook(2, true, new Runnable()
/*    */     {
/*    */       public void run() {}
/*    */     });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static synchronized void add(String paramString)
/*    */   {
/* 58 */     if (files == null)
/*    */     {
/* 60 */       throw new IllegalStateException("Shutdown in progress");
/*    */     }
/*    */     
/* 63 */     files.add(paramString);
/*    */   }
/*    */   
/*    */   static void runHooks()
/*    */   {
/*    */     LinkedHashSet localLinkedHashSet;
/* 69 */     synchronized (DeleteOnExitHook.class) {
/* 70 */       localLinkedHashSet = files;
/* 71 */       files = null;
/*    */     }
/*    */     
/* 74 */     ??? = new ArrayList(localLinkedHashSet);
/*    */     
/*    */ 
/*    */ 
/* 78 */     Collections.reverse((List)???);
/* 79 */     for (String str : (ArrayList)???) {
/* 80 */       new File(str).delete();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/DeleteOnExitHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */