/*    */ package java.lang.annotation;
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
/*    */ public class IncompleteAnnotationException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 8445097402741811912L;
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
/*    */   private Class<? extends Annotation> annotationType;
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
/*    */   private String elementName;
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
/*    */   public IncompleteAnnotationException(Class<? extends Annotation> paramClass, String paramString)
/*    */   {
/* 58 */     super(paramClass.getName() + " missing element " + paramString
/* 59 */       .toString());
/*    */     
/* 61 */     this.annotationType = paramClass;
/* 62 */     this.elementName = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class<? extends Annotation> annotationType()
/*    */   {
/* 73 */     return this.annotationType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String elementName()
/*    */   {
/* 82 */     return this.elementName;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/annotation/IncompleteAnnotationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */