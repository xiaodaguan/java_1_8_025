/*     */ package java.rmi;
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
/*     */ public class RemoteException
/*     */   extends IOException
/*     */ {
/*     */   private static final long serialVersionUID = -5148567311918794206L;
/*     */   public Throwable detail;
/*     */   
/*     */   public RemoteException()
/*     */   {
/*  69 */     initCause(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RemoteException(String paramString)
/*     */   {
/*  79 */     super(paramString);
/*  80 */     initCause(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RemoteException(String paramString, Throwable paramThrowable)
/*     */   {
/*  92 */     super(paramString);
/*  93 */     initCause(null);
/*  94 */     this.detail = paramThrowable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 104 */     if (this.detail == null) {
/* 105 */       return super.getMessage();
/*     */     }
/*     */     
/* 108 */     return super.getMessage() + "; nested exception is: \n\t" + this.detail.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getCause()
/*     */   {
/* 120 */     return this.detail;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/RemoteException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */