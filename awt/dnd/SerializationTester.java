/*    */ package java.awt.dnd;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
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
/*    */ final class SerializationTester
/*    */ {
/*    */   private static ObjectOutputStream stream;
/*    */   
/*    */   static
/*    */   {
/*    */     try
/*    */     {
/* 43 */       stream = new ObjectOutputStream(new OutputStream() {
/*    */         public void write(int paramAnonymousInt) {}
/*    */       });
/*    */     }
/*    */     catch (IOException localIOException) {}
/*    */   }
/*    */   
/*    */   static boolean test(Object paramObject) {
/* 51 */     if (!(paramObject instanceof Serializable)) {
/* 52 */       return false;
/*    */     }
/*    */     try
/*    */     {
/* 56 */       stream.writeObject(paramObject);
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
/* 69 */       return true;
/*    */     }
/*    */     catch (IOException localIOException2)
/*    */     {
/* 58 */       return false;
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 64 */         stream.reset();
/*    */       }
/*    */       catch (IOException localIOException4) {}
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/SerializationTester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */