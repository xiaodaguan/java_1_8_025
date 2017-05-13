/*     */ package java.util.regex;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PatternSyntaxException
/*     */   extends IllegalArgumentException
/*     */ {
/*     */   private static final long serialVersionUID = -3864639126226059218L;
/*     */   private final String desc;
/*     */   private final String pattern;
/*     */   private final int index;
/*     */   
/*     */   public PatternSyntaxException(String paramString1, String paramString2, int paramInt)
/*     */   {
/*  63 */     this.desc = paramString1;
/*  64 */     this.pattern = paramString2;
/*  65 */     this.index = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getIndex()
/*     */   {
/*  75 */     return this.index;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  84 */     return this.desc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPattern()
/*     */   {
/*  93 */     return this.pattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  98 */   private static final String nl = (String)AccessController.doPrivileged(new GetPropertyAction("line.separator"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 108 */     StringBuffer localStringBuffer = new StringBuffer();
/* 109 */     localStringBuffer.append(this.desc);
/* 110 */     if (this.index >= 0) {
/* 111 */       localStringBuffer.append(" near index ");
/* 112 */       localStringBuffer.append(this.index);
/*     */     }
/* 114 */     localStringBuffer.append(nl);
/* 115 */     localStringBuffer.append(this.pattern);
/* 116 */     if (this.index >= 0) {
/* 117 */       localStringBuffer.append(nl);
/* 118 */       for (int i = 0; i < this.index; i++) localStringBuffer.append(' ');
/* 119 */       localStringBuffer.append('^');
/*     */     }
/* 121 */     return localStringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/regex/PatternSyntaxException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */