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
/*    */ class MacOSXPreferencesFactory
/*    */   implements PreferencesFactory
/*    */ {
/*    */   public Preferences userRoot()
/*    */   {
/* 31 */     return MacOSXPreferences.getUserRoot();
/*    */   }
/*    */   
/*    */   public Preferences systemRoot()
/*    */   {
/* 36 */     return MacOSXPreferences.getSystemRoot();
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/MacOSXPreferencesFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */