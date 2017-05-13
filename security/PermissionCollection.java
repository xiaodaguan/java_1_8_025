/*     */ package java.security;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public abstract class PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6727011328946861783L;
/*     */   private volatile boolean readOnly;
/*     */   
/*     */   public abstract void add(Permission paramPermission);
/*     */   
/*     */   public abstract boolean implies(Permission paramPermission);
/*     */   
/*     */   public abstract Enumeration<Permission> elements();
/*     */   
/*     */   public void setReadOnly()
/*     */   {
/* 139 */     this.readOnly = true;
/*     */   }
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
/*     */   public boolean isReadOnly()
/*     */   {
/* 154 */     return this.readOnly;
/*     */   }
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
/*     */   public String toString()
/*     */   {
/* 180 */     Enumeration localEnumeration = elements();
/* 181 */     StringBuilder localStringBuilder = new StringBuilder();
/* 182 */     localStringBuilder.append(super.toString() + " (\n");
/* 183 */     while (localEnumeration.hasMoreElements()) {
/*     */       try {
/* 185 */         localStringBuilder.append(" ");
/* 186 */         localStringBuilder.append(((Permission)localEnumeration.nextElement()).toString());
/* 187 */         localStringBuilder.append("\n");
/*     */       }
/*     */       catch (NoSuchElementException localNoSuchElementException) {}
/*     */     }
/*     */     
/* 192 */     localStringBuilder.append(")\n");
/* 193 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/PermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */