/*    */ package java.nio.charset.spi;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.Iterator;
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
/*    */ public abstract class CharsetProvider
/*    */ {
/*    */   protected CharsetProvider()
/*    */   {
/* 82 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 83 */     if (localSecurityManager != null) {
/* 84 */       localSecurityManager.checkPermission(new RuntimePermission("charsetProvider"));
/*    */     }
/*    */   }
/*    */   
/*    */   public abstract Iterator<Charset> charsets();
/*    */   
/*    */   public abstract Charset charsetForName(String paramString);
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/charset/spi/CharsetProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */