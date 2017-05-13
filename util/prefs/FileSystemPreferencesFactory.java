/*    */ package java.util.prefs;
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
/*    */ class FileSystemPreferencesFactory
/*    */   implements PreferencesFactory
/*    */ {
/*    */   public Preferences userRoot()
/*    */   {
/* 41 */     return FileSystemPreferences.getUserRoot();
/*    */   }
/*    */   
/*    */   public Preferences systemRoot() {
/* 45 */     return FileSystemPreferences.getSystemRoot();
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/FileSystemPreferencesFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */