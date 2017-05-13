/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Enumeration;
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
/*    */ class DefaultInterface
/*    */ {
/* 46 */   private static final NetworkInterface defaultInterface = ;
/*    */   
/*    */   static NetworkInterface getDefault() {
/* 49 */     return defaultInterface;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static NetworkInterface chooseDefaultInterface()
/*    */   {
/*    */     Enumeration localEnumeration;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 67 */       localEnumeration = NetworkInterface.getNetworkInterfaces();
/*    */     }
/*    */     catch (IOException localIOException1) {
/* 70 */       return null;
/*    */     }
/*    */     
/* 73 */     Object localObject1 = null;
/* 74 */     Object localObject2 = null;
/*    */     
/* 76 */     while (localEnumeration.hasMoreElements()) {
/* 77 */       NetworkInterface localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
/*    */       try {
/* 79 */         if ((localNetworkInterface.isUp()) && (localNetworkInterface.supportsMulticast())) {
/* 80 */           boolean bool1 = localNetworkInterface.isLoopback();
/* 81 */           boolean bool2 = localNetworkInterface.isPointToPoint();
/* 82 */           if ((!bool1) && (!bool2))
/*    */           {
/*    */ 
/* 85 */             return localNetworkInterface;
/*    */           }
/* 87 */           if ((localObject1 == null) && (bool2))
/* 88 */             localObject1 = localNetworkInterface;
/* 89 */           if ((localObject2 == null) && (bool1)) {
/* 90 */             localObject2 = localNetworkInterface;
/*    */           }
/*    */         }
/*    */       } catch (IOException localIOException2) {}
/*    */     }
/* 95 */     return (NetworkInterface)(localObject1 != null ? localObject1 : localObject2);
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/DefaultInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */