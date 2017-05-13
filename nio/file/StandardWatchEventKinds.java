/*    */ package java.nio.file;
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
/*    */ public final class StandardWatchEventKinds
/*    */ {
/* 47 */   public static final WatchEvent.Kind<Object> OVERFLOW = new StdWatchEventKind("OVERFLOW", Object.class);
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
/* 58 */   public static final WatchEvent.Kind<Path> ENTRY_CREATE = new StdWatchEventKind("ENTRY_CREATE", Path.class);
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
/* 69 */   public static final WatchEvent.Kind<Path> ENTRY_DELETE = new StdWatchEventKind("ENTRY_DELETE", Path.class);
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
/* 80 */   public static final WatchEvent.Kind<Path> ENTRY_MODIFY = new StdWatchEventKind("ENTRY_MODIFY", Path.class);
/*    */   
/*    */   private static class StdWatchEventKind<T> implements WatchEvent.Kind<T> {
/*    */     private final String name;
/*    */     private final Class<T> type;
/*    */     
/*    */     StdWatchEventKind(String paramString, Class<T> paramClass) {
/* 87 */       this.name = paramString;
/* 88 */       this.type = paramClass; }
/*    */     
/* 90 */     public String name() { return this.name; }
/* 91 */     public Class<T> type() { return this.type; }
/* 92 */     public String toString() { return this.name; }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/StandardWatchEventKinds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */