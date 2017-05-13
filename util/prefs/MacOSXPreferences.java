/*     */ package java.util.prefs;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ class MacOSXPreferences
/*     */   extends AbstractPreferences
/*     */ {
/*     */   private static final String defaultAppName = "com.apple.java.util.prefs";
/*     */   private final boolean isUser;
/*     */   private final boolean isRoot;
/*     */   private final MacOSXPreferencesFile file;
/*     */   private final String path;
/*  50 */   private static MacOSXPreferences userRoot = null;
/*  51 */   private static MacOSXPreferences systemRoot = null;
/*     */   
/*     */ 
/*     */ 
/*     */   static synchronized Preferences getUserRoot()
/*     */   {
/*  57 */     if (userRoot == null) {
/*  58 */       userRoot = new MacOSXPreferences(true);
/*     */     }
/*  60 */     return userRoot;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static synchronized Preferences getSystemRoot()
/*     */   {
/*  67 */     if (systemRoot == null) {
/*  68 */       systemRoot = new MacOSXPreferences(false);
/*     */     }
/*  70 */     return systemRoot;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private MacOSXPreferences(boolean paramBoolean)
/*     */   {
/*  77 */     this(null, "", false, true, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private MacOSXPreferences(MacOSXPreferences paramMacOSXPreferences, String paramString)
/*     */   {
/*  84 */     this(paramMacOSXPreferences, paramString, false, false, false);
/*     */   }
/*     */   
/*     */ 
/*     */   private MacOSXPreferences(MacOSXPreferences paramMacOSXPreferences, String paramString, boolean paramBoolean)
/*     */   {
/*  90 */     this(paramMacOSXPreferences, paramString, paramBoolean, false, false);
/*     */   }
/*     */   
/*     */ 
/*     */   private MacOSXPreferences(MacOSXPreferences paramMacOSXPreferences, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/*  96 */     super(paramMacOSXPreferences, paramString);
/*  97 */     this.isRoot = paramBoolean2;
/*  98 */     if (paramBoolean2) {
/*  99 */       this.isUser = paramBoolean3;
/*     */     } else
/* 101 */       this.isUser = isUserNode();
/* 102 */     this.path = (absolutePath() + "/");
/* 103 */     this.file = cfFileForNode(this.isUser);
/* 104 */     if (paramBoolean1) {
/* 105 */       this.newNode = paramBoolean1;
/*     */     } else {
/* 107 */       this.newNode = this.file.addNode(this.path);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private MacOSXPreferencesFile cfFileForNode(boolean paramBoolean)
/*     */   {
/* 114 */     String str = this.path;
/*     */     
/*     */ 
/*     */ 
/* 118 */     int i = 0;
/* 119 */     int j = -1;
/* 120 */     for (int k = 0; k < 4; k++) {
/* 121 */       j = str.indexOf('/', j + 1);
/* 122 */       if (j == -1)
/*     */         break;
/*     */     }
/* 125 */     if (j == -1)
/*     */     {
/* 127 */       str = "com.apple.java.util.prefs";
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 132 */       str = str.substring(1, j);
/* 133 */       str = str.replace('/', '.');
/* 134 */       str = str.toLowerCase();
/*     */     }
/*     */     
/* 137 */     return MacOSXPreferencesFile.getFile(str, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void putSpi(String paramString1, String paramString2)
/*     */   {
/* 145 */     this.file.addKeyToNode(this.path, paramString1, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getSpi(String paramString)
/*     */   {
/* 152 */     return this.file.getKeyFromNode(this.path, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void removeSpi(String paramString)
/*     */   {
/* 159 */     Objects.requireNonNull(paramString, "Specified key cannot be null");
/* 160 */     this.file.removeKeyFromNode(this.path, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void removeNodeSpi()
/*     */     throws BackingStoreException
/*     */   {
/* 171 */     synchronized (MacOSXPreferencesFile.class) {
/* 172 */       ((MacOSXPreferences)parent()).removeChild(name());
/* 173 */       this.file.removeNode(this.path);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void removeChild(String paramString)
/*     */   {
/* 180 */     this.file.removeChildFromNode(this.path, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String[] childrenNamesSpi()
/*     */     throws BackingStoreException
/*     */   {
/* 189 */     String[] arrayOfString = this.file.getChildrenForNode(this.path);
/* 190 */     if (arrayOfString == null) throw new BackingStoreException("Couldn't get list of children for node '" + this.path + "'");
/* 191 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String[] keysSpi()
/*     */     throws BackingStoreException
/*     */   {
/* 199 */     String[] arrayOfString = this.file.getKeysForNode(this.path);
/* 200 */     if (arrayOfString == null) throw new BackingStoreException("Couldn't get list of keys for node '" + this.path + "'");
/* 201 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractPreferences childSpi(String paramString)
/*     */   {
/* 210 */     synchronized (MacOSXPreferencesFile.class) {
/* 211 */       boolean bool = this.file.addChildToNode(this.path, paramString);
/* 212 */       return new MacOSXPreferences(this, paramString, bool);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws BackingStoreException
/*     */   {
/* 223 */     synchronized (this.lock) {
/* 224 */       if (this.isUser) {
/* 225 */         if (!MacOSXPreferencesFile.flushUser()) {
/* 226 */           throw new BackingStoreException("Synchronization failed for node '" + this.path + "'");
/*     */         }
/*     */       }
/* 229 */       else if (!MacOSXPreferencesFile.flushWorld()) {
/* 230 */         throw new BackingStoreException("Synchronization failed for node '" + this.path + "'");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void flushSpi()
/*     */     throws BackingStoreException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sync()
/*     */     throws BackingStoreException
/*     */   {
/* 249 */     synchronized (this.lock) {
/* 250 */       if (isRemoved()) {
/* 251 */         throw new IllegalStateException("Node has been removed");
/*     */       }
/* 253 */       if (this.isUser) {
/* 254 */         if (!MacOSXPreferencesFile.syncUser()) {
/* 255 */           throw new BackingStoreException("Synchronization failed for node '" + this.path + "'");
/*     */         }
/*     */       }
/* 258 */       else if (!MacOSXPreferencesFile.syncWorld()) {
/* 259 */         throw new BackingStoreException("Synchronization failed for node '" + this.path + "'");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void syncSpi()
/*     */     throws BackingStoreException
/*     */   {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/MacOSXPreferences.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */