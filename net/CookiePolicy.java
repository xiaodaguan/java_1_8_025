/*    */ package java.net;
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
/*    */ public abstract interface CookiePolicy
/*    */ {
/* 42 */   public static final CookiePolicy ACCEPT_ALL = new CookiePolicy() {
/*    */     public boolean shouldAccept(URI paramAnonymousURI, HttpCookie paramAnonymousHttpCookie) {
/* 44 */       return true;
/*    */     }
/*    */   };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 51 */   public static final CookiePolicy ACCEPT_NONE = new CookiePolicy() {
/*    */     public boolean shouldAccept(URI paramAnonymousURI, HttpCookie paramAnonymousHttpCookie) {
/* 53 */       return false;
/*    */     }
/*    */   };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 60 */   public static final CookiePolicy ACCEPT_ORIGINAL_SERVER = new CookiePolicy() {
/*    */     public boolean shouldAccept(URI paramAnonymousURI, HttpCookie paramAnonymousHttpCookie) {
/* 62 */       if ((paramAnonymousURI == null) || (paramAnonymousHttpCookie == null))
/* 63 */         return false;
/* 64 */       return HttpCookie.domainMatches(paramAnonymousHttpCookie.getDomain(), paramAnonymousURI.getHost());
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract boolean shouldAccept(URI paramURI, HttpCookie paramHttpCookie);
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/CookiePolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */