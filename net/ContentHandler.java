/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ContentHandler
/*     */ {
/*     */   public abstract Object getContent(URLConnection paramURLConnection)
/*     */     throws IOException;
/*     */   
/*     */   public Object getContent(URLConnection paramURLConnection, Class[] paramArrayOfClass)
/*     */     throws IOException
/*     */   {
/* 101 */     Object localObject = getContent(paramURLConnection);
/*     */     
/* 103 */     for (int i = 0; i < paramArrayOfClass.length; i++) {
/* 104 */       if (paramArrayOfClass[i].isInstance(localObject)) {
/* 105 */         return localObject;
/*     */       }
/*     */     }
/* 108 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/ContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */