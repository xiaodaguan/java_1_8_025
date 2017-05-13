/*     */ package java.util.prefs;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
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
/*     */ class MacOSXPreferencesFile
/*     */ {
/*     */   private static HashMap<String, WeakReference<MacOSXPreferencesFile>> cachedFiles;
/*     */   private static HashSet<MacOSXPreferencesFile> changedFiles;
/*     */   
/*  82 */   static { AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  85 */         System.loadLibrary("osx");
/*  86 */         return null;
/*     */       }
/*     */     }); }
/*     */   
/*     */   private class FlushTask extends TimerTask {
/*     */     private FlushTask() {}
/*     */     
/*  93 */     public void run() { MacOSXPreferencesFile.flushWorld(); }
/*     */   }
/*     */   
/*     */   private class SyncTask extends TimerTask {
/*     */     private SyncTask() {}
/*     */     
/*  99 */     public void run() { MacOSXPreferencesFile.syncWorld(); }
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
/* 112 */   private static Timer timer = null;
/* 113 */   private static FlushTask flushTimerTask = null;
/* 114 */   private static long flushDelay = -1L;
/* 115 */   private static long syncInterval = -1L;
/*     */   
/*     */   private String appName;
/*     */   private long user;
/*     */   private long host;
/*     */   
/* 121 */   String name() { return this.appName; }
/* 122 */   long user() { return this.user; }
/* 123 */   long host() { return this.host; }
/*     */   
/*     */ 
/*     */   private MacOSXPreferencesFile(String paramString, long paramLong1, long paramLong2)
/*     */   {
/* 128 */     this.appName = paramString;
/* 129 */     this.user = paramLong1;
/* 130 */     this.host = paramLong2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized MacOSXPreferencesFile getFile(String paramString, boolean paramBoolean)
/*     */   {
/* 138 */     MacOSXPreferencesFile localMacOSXPreferencesFile = null;
/*     */     
/* 140 */     if (cachedFiles == null) {
/* 141 */       cachedFiles = new HashMap();
/*     */     }
/*     */     
/* 144 */     String str = paramString + String.valueOf(paramBoolean);
/* 145 */     WeakReference localWeakReference = (WeakReference)cachedFiles.get(str);
/* 146 */     if (localWeakReference != null) {
/* 147 */       localMacOSXPreferencesFile = (MacOSXPreferencesFile)localWeakReference.get();
/*     */     }
/* 149 */     if (localMacOSXPreferencesFile == null)
/*     */     {
/*     */ 
/* 152 */       localMacOSXPreferencesFile = new MacOSXPreferencesFile(paramString, paramBoolean ? cfCurrentUser : cfAnyUser, paramBoolean ? cfAnyHost : cfCurrentHost);
/*     */       
/*     */ 
/* 155 */       cachedFiles.put(str, new WeakReference(localMacOSXPreferencesFile));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 163 */     initSyncTimerIfNeeded();
/*     */     
/* 165 */     return localMacOSXPreferencesFile;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized boolean syncWorld()
/*     */   {
/* 173 */     boolean bool = true;
/*     */     
/* 175 */     if ((cachedFiles != null) && (!cachedFiles.isEmpty()))
/*     */     {
/* 177 */       Iterator localIterator = cachedFiles.values().iterator();
/* 178 */       while (localIterator.hasNext()) {
/* 179 */         WeakReference localWeakReference = (WeakReference)localIterator.next();
/* 180 */         MacOSXPreferencesFile localMacOSXPreferencesFile = (MacOSXPreferencesFile)localWeakReference.get();
/* 181 */         if (localMacOSXPreferencesFile != null) {
/* 182 */           if (!localMacOSXPreferencesFile.synchronize()) bool = false;
/*     */         } else {
/* 184 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 190 */     if (flushTimerTask != null) {
/* 191 */       flushTimerTask.cancel();
/* 192 */       flushTimerTask = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 198 */     if (changedFiles != null) { changedFiles.clear();
/*     */     }
/* 200 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */   static synchronized boolean syncUser()
/*     */   {
/* 206 */     boolean bool = true;
/* 207 */     Iterator localIterator; Object localObject; if ((cachedFiles != null) && (!cachedFiles.isEmpty()))
/*     */     {
/* 209 */       localIterator = cachedFiles.values().iterator();
/* 210 */       while (localIterator.hasNext()) {
/* 211 */         localObject = (WeakReference)localIterator.next();
/* 212 */         MacOSXPreferencesFile localMacOSXPreferencesFile = (MacOSXPreferencesFile)((WeakReference)localObject).get();
/* 213 */         if ((localMacOSXPreferencesFile != null) && (localMacOSXPreferencesFile.user == cfCurrentUser)) {
/* 214 */           if (!localMacOSXPreferencesFile.synchronize()) {
/* 215 */             bool = false;
/*     */           }
/*     */         } else {
/* 218 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 225 */     if (changedFiles != null) {
/* 226 */       localIterator = changedFiles.iterator();
/* 227 */       while (localIterator.hasNext()) {
/* 228 */         localObject = (MacOSXPreferencesFile)localIterator.next();
/* 229 */         if ((localObject != null) && (((MacOSXPreferencesFile)localObject).user == cfCurrentUser))
/* 230 */           localIterator.remove();
/*     */       }
/*     */     }
/* 233 */     return bool;
/*     */   }
/*     */   
/*     */   static synchronized boolean flushUser()
/*     */   {
/* 238 */     boolean bool = true;
/* 239 */     if ((changedFiles != null) && (!changedFiles.isEmpty())) {
/* 240 */       Iterator localIterator = changedFiles.iterator();
/* 241 */       while (localIterator.hasNext()) {
/* 242 */         MacOSXPreferencesFile localMacOSXPreferencesFile = (MacOSXPreferencesFile)localIterator.next();
/* 243 */         if (localMacOSXPreferencesFile.user == cfCurrentUser) {
/* 244 */           if (!localMacOSXPreferencesFile.synchronize()) {
/* 245 */             bool = false;
/*     */           } else
/* 247 */             localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/* 251 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized boolean flushWorld()
/*     */   {
/* 260 */     boolean bool = true;
/*     */     
/* 262 */     if ((changedFiles != null) && (!changedFiles.isEmpty())) {
/* 263 */       for (MacOSXPreferencesFile localMacOSXPreferencesFile : changedFiles) {
/* 264 */         if (!localMacOSXPreferencesFile.synchronize())
/* 265 */           bool = false;
/*     */       }
/* 267 */       changedFiles.clear();
/*     */     }
/*     */     
/* 270 */     if (flushTimerTask != null) {
/* 271 */       flushTimerTask.cancel();
/* 272 */       flushTimerTask = null;
/*     */     }
/*     */     
/* 275 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void markChanged()
/*     */   {
/* 284 */     if (changedFiles == null)
/* 285 */       changedFiles = new HashSet();
/* 286 */     changedFiles.add(this);
/*     */     
/*     */ 
/* 289 */     if (flushTimerTask == null) {
/* 290 */       flushTimerTask = new FlushTask(null);
/* 291 */       timer().schedule(flushTimerTask, flushDelay() * 1000L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static synchronized long flushDelay()
/*     */   {
/* 298 */     if (flushDelay == -1L) {
/*     */       try
/*     */       {
/* 301 */         flushDelay = Math.max(5, Integer.parseInt(System.getProperty("java.util.prefs.flushDelay", "60")));
/*     */       } catch (NumberFormatException localNumberFormatException) {
/* 303 */         flushDelay = 60L;
/*     */       }
/*     */     }
/* 306 */     return flushDelay;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static synchronized void initSyncTimerIfNeeded()
/*     */   {
/* 316 */     if (syncInterval == -1L) {
/*     */       try {
/* 318 */         syncInterval = Integer.parseInt(System.getProperty("java.util.prefs.syncInterval", "-2"));
/* 319 */         if (syncInterval >= 0L)
/*     */         {
/* 321 */           syncInterval = Math.max(5L, syncInterval);
/*     */         } else {
/* 323 */           syncInterval = -2L;
/*     */         }
/*     */       } catch (NumberFormatException localNumberFormatException) {
/* 326 */         syncInterval = -2L;
/*     */       }
/*     */       
/* 329 */       if (syncInterval > 0L) {
/* 330 */         timer().schedule(new TimerTask()
/*     */         {
/*     */ 
/* 333 */           public void run() { MacOSXPreferencesFile.syncWorld(); } }, syncInterval * 1000L, syncInterval * 1000L);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static synchronized Timer timer()
/*     */   {
/* 344 */     if (timer == null) {
/* 345 */       timer = new Timer(true);
/* 346 */       Thread local3 = new Thread()
/*     */       {
/*     */         public void run() {
/* 349 */           MacOSXPreferencesFile.flushWorld();
/*     */ 
/*     */         }
/*     */         
/*     */ 
/* 354 */       };
/* 355 */       local3.setContextClassLoader(null);
/* 356 */       Runtime.getRuntime().addShutdownHook(local3);
/*     */     }
/* 358 */     return timer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean addNode(String paramString)
/*     */   {
/* 365 */     synchronized (MacOSXPreferencesFile.class) {
/* 366 */       markChanged();
/* 367 */       return addNode(paramString, this.appName, this.user, this.host);
/*     */     }
/*     */   }
/*     */   
/*     */   void removeNode(String paramString)
/*     */   {
/* 373 */     synchronized (MacOSXPreferencesFile.class) {
/* 374 */       markChanged();
/* 375 */       removeNode(paramString, this.appName, this.user, this.host);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean addChildToNode(String paramString1, String paramString2)
/*     */   {
/* 381 */     synchronized (MacOSXPreferencesFile.class) {
/* 382 */       markChanged();
/* 383 */       return addChildToNode(paramString1, paramString2 + "/", this.appName, this.user, this.host);
/*     */     }
/*     */   }
/*     */   
/*     */   void removeChildFromNode(String paramString1, String paramString2)
/*     */   {
/* 389 */     synchronized (MacOSXPreferencesFile.class) {
/* 390 */       markChanged();
/* 391 */       removeChildFromNode(paramString1, paramString2 + "/", this.appName, this.user, this.host);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void addKeyToNode(String paramString1, String paramString2, String paramString3)
/*     */   {
/* 399 */     synchronized (MacOSXPreferencesFile.class) {
/* 400 */       markChanged();
/* 401 */       addKeyToNode(paramString1, paramString2, paramString3, this.appName, this.user, this.host);
/*     */     }
/*     */   }
/*     */   
/*     */   void removeKeyFromNode(String paramString1, String paramString2)
/*     */   {
/* 407 */     synchronized (MacOSXPreferencesFile.class) {
/* 408 */       markChanged();
/* 409 */       removeKeyFromNode(paramString1, paramString2, this.appName, this.user, this.host);
/*     */     }
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
/* 475 */   private static long cfCurrentUser = currentUser();
/* 476 */   private static long cfAnyUser = anyUser();
/* 477 */   private static long cfCurrentHost = currentHost();
/* 478 */   private static long cfAnyHost = anyHost();
/*     */   
/*     */   /* Error */
/*     */   String getKeyFromNode(String paramString1, String paramString2)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 16
/*     */     //   2: dup
/*     */     //   3: astore_3
/*     */     //   4: monitorenter
/*     */     //   5: aload_1
/*     */     //   6: aload_2
/*     */     //   7: aload_0
/*     */     //   8: getfield 1	java/util/prefs/MacOSXPreferencesFile:appName	Ljava/lang/String;
/*     */     //   11: aload_0
/*     */     //   12: getfield 2	java/util/prefs/MacOSXPreferencesFile:user	J
/*     */     //   15: aload_0
/*     */     //   16: getfield 3	java/util/prefs/MacOSXPreferencesFile:host	J
/*     */     //   19: invokestatic 86	java/util/prefs/MacOSXPreferencesFile:getKeyFromNode	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)Ljava/lang/String;
/*     */     //   22: aload_3
/*     */     //   23: monitorexit
/*     */     //   24: areturn
/*     */     //   25: astore 4
/*     */     //   27: aload_3
/*     */     //   28: monitorexit
/*     */     //   29: aload 4
/*     */     //   31: athrow
/*     */     // Line number table:
/*     */     //   Java source line #415	-> byte code offset #0
/*     */     //   Java source line #416	-> byte code offset #5
/*     */     //   Java source line #417	-> byte code offset #25
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	32	0	this	MacOSXPreferencesFile
/*     */     //   0	32	1	paramString1	String
/*     */     //   0	32	2	paramString2	String
/*     */     //   3	25	3	Ljava/lang/Object;	Object
/*     */     //   25	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	24	25	finally
/*     */     //   25	29	25	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   String[] getChildrenForNode(String paramString)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 16
/*     */     //   2: dup
/*     */     //   3: astore_2
/*     */     //   4: monitorenter
/*     */     //   5: aload_1
/*     */     //   6: aload_0
/*     */     //   7: getfield 1	java/util/prefs/MacOSXPreferencesFile:appName	Ljava/lang/String;
/*     */     //   10: aload_0
/*     */     //   11: getfield 2	java/util/prefs/MacOSXPreferencesFile:user	J
/*     */     //   14: aload_0
/*     */     //   15: getfield 3	java/util/prefs/MacOSXPreferencesFile:host	J
/*     */     //   18: invokestatic 87	java/util/prefs/MacOSXPreferencesFile:getChildrenForNode	(Ljava/lang/String;Ljava/lang/String;JJ)[Ljava/lang/String;
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: areturn
/*     */     //   24: astore_3
/*     */     //   25: aload_2
/*     */     //   26: monitorexit
/*     */     //   27: aload_3
/*     */     //   28: athrow
/*     */     // Line number table:
/*     */     //   Java source line #424	-> byte code offset #0
/*     */     //   Java source line #425	-> byte code offset #5
/*     */     //   Java source line #426	-> byte code offset #24
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	29	0	this	MacOSXPreferencesFile
/*     */     //   0	29	1	paramString	String
/*     */     //   3	23	2	Ljava/lang/Object;	Object
/*     */     //   24	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	23	24	finally
/*     */     //   24	27	24	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   String[] getKeysForNode(String paramString)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 16
/*     */     //   2: dup
/*     */     //   3: astore_2
/*     */     //   4: monitorenter
/*     */     //   5: aload_1
/*     */     //   6: aload_0
/*     */     //   7: getfield 1	java/util/prefs/MacOSXPreferencesFile:appName	Ljava/lang/String;
/*     */     //   10: aload_0
/*     */     //   11: getfield 2	java/util/prefs/MacOSXPreferencesFile:user	J
/*     */     //   14: aload_0
/*     */     //   15: getfield 3	java/util/prefs/MacOSXPreferencesFile:host	J
/*     */     //   18: invokestatic 88	java/util/prefs/MacOSXPreferencesFile:getKeysForNode	(Ljava/lang/String;Ljava/lang/String;JJ)[Ljava/lang/String;
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: areturn
/*     */     //   24: astore_3
/*     */     //   25: aload_2
/*     */     //   26: monitorexit
/*     */     //   27: aload_3
/*     */     //   28: athrow
/*     */     // Line number table:
/*     */     //   Java source line #431	-> byte code offset #0
/*     */     //   Java source line #432	-> byte code offset #5
/*     */     //   Java source line #433	-> byte code offset #24
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	29	0	this	MacOSXPreferencesFile
/*     */     //   0	29	1	paramString	String
/*     */     //   3	23	2	Ljava/lang/Object;	Object
/*     */     //   24	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	23	24	finally
/*     */     //   24	27	24	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   boolean synchronize()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 16
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: aload_0
/*     */     //   6: getfield 1	java/util/prefs/MacOSXPreferencesFile:appName	Ljava/lang/String;
/*     */     //   9: aload_0
/*     */     //   10: getfield 2	java/util/prefs/MacOSXPreferencesFile:user	J
/*     */     //   13: aload_0
/*     */     //   14: getfield 3	java/util/prefs/MacOSXPreferencesFile:host	J
/*     */     //   17: invokestatic 89	java/util/prefs/MacOSXPreferencesFile:synchronize	(Ljava/lang/String;JJ)Z
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: ireturn
/*     */     //   23: astore_2
/*     */     //   24: aload_1
/*     */     //   25: monitorexit
/*     */     //   26: aload_2
/*     */     //   27: athrow
/*     */     // Line number table:
/*     */     //   Java source line #440	-> byte code offset #0
/*     */     //   Java source line #441	-> byte code offset #5
/*     */     //   Java source line #442	-> byte code offset #23
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	28	0	this	MacOSXPreferencesFile
/*     */     //   3	22	1	Ljava/lang/Object;	Object
/*     */     //   23	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	22	23	finally
/*     */     //   23	26	23	finally
/*     */   }
/*     */   
/*     */   private static final native boolean addNode(String paramString1, String paramString2, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native void removeNode(String paramString1, String paramString2, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native boolean addChildToNode(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native void removeChildFromNode(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native void addKeyToNode(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native void removeKeyFromNode(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native String getKeyFromNode(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native String[] getChildrenForNode(String paramString1, String paramString2, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native String[] getKeysForNode(String paramString1, String paramString2, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native boolean synchronize(String paramString, long paramLong1, long paramLong2);
/*     */   
/*     */   private static final native long currentUser();
/*     */   
/*     */   private static final native long anyUser();
/*     */   
/*     */   private static final native long currentHost();
/*     */   
/*     */   private static final native long anyHost();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/MacOSXPreferencesFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */