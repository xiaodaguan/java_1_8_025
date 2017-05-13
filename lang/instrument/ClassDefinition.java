/*    */ package java.lang.instrument;
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
/*    */ public final class ClassDefinition
/*    */ {
/*    */   private final Class<?> mClass;
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
/*    */   private final byte[] mClassFile;
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
/*    */   public ClassDefinition(Class<?> paramClass, byte[] paramArrayOfByte)
/*    */   {
/* 62 */     if ((paramClass == null) || (paramArrayOfByte == null)) {
/* 63 */       throw new NullPointerException();
/*    */     }
/* 65 */     this.mClass = paramClass;
/* 66 */     this.mClassFile = paramArrayOfByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class<?> getDefinitionClass()
/*    */   {
/* 76 */     return this.mClass;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getDefinitionClassFile()
/*    */   {
/* 86 */     return this.mClassFile;
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/instrument/ClassDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */