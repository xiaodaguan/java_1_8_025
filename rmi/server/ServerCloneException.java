/*     */ package java.rmi.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerCloneException
/*     */   extends CloneNotSupportedException
/*     */ {
/*     */   public Exception detail;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 6617456357664815945L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerCloneException(String paramString)
/*     */   {
/*  70 */     super(paramString);
/*  71 */     initCause(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerCloneException(String paramString, Exception paramException)
/*     */   {
/*  82 */     super(paramString);
/*  83 */     initCause(null);
/*  84 */     this.detail = paramException;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  94 */     if (this.detail == null) {
/*  95 */       return super.getMessage();
/*     */     }
/*     */     
/*     */ 
/*  99 */     return super.getMessage() + "; nested exception is: \n\t" + this.detail.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getCause()
/*     */   {
/* 110 */     return this.detail;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/server/ServerCloneException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */