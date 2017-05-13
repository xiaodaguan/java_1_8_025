/*    */ package java.security;
/*    */ 
/*    */ import java.util.Set;
/*    */ import javax.security.auth.Subject;
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
/*    */ public abstract interface Principal
/*    */ {
/*    */   public abstract boolean equals(Object paramObject);
/*    */   
/*    */   public abstract String toString();
/*    */   
/*    */   public abstract int hashCode();
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public boolean implies(Subject paramSubject)
/*    */   {
/* 90 */     if (paramSubject == null)
/* 91 */       return false;
/* 92 */     return paramSubject.getPrincipals().contains(this);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Principal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */