/*     */ package java.lang.management;
/*     */ 
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import sun.management.ManagementFactoryHelper;
/*     */ import sun.management.ThreadInfoCompositeData;
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
/*     */ public class ThreadInfo
/*     */ {
/*     */   private String threadName;
/*     */   private long threadId;
/*     */   private long blockedTime;
/*     */   private long blockedCount;
/*     */   private long waitedTime;
/*     */   private long waitedCount;
/*     */   private LockInfo lock;
/*     */   private String lockName;
/*     */   private long lockOwnerId;
/*     */   private String lockOwnerName;
/*     */   private boolean inNative;
/*     */   private boolean suspended;
/*     */   private Thread.State threadState;
/*     */   private StackTraceElement[] stackTrace;
/*     */   private MonitorInfo[] lockedMonitors;
/*     */   private LockInfo[] lockedSynchronizers;
/* 110 */   private static MonitorInfo[] EMPTY_MONITORS = new MonitorInfo[0];
/* 111 */   private static LockInfo[] EMPTY_SYNCS = new LockInfo[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int MAX_FRAMES = 8;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ThreadInfo(Thread paramThread1, int paramInt, Object paramObject, Thread paramThread2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, StackTraceElement[] paramArrayOfStackTraceElement)
/*     */   {
/* 130 */     initialize(paramThread1, paramInt, paramObject, paramThread2, paramLong1, paramLong2, paramLong3, paramLong4, paramArrayOfStackTraceElement, EMPTY_MONITORS, EMPTY_SYNCS);
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
/*     */   private ThreadInfo(Thread paramThread1, int paramInt, Object paramObject, Thread paramThread2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, StackTraceElement[] paramArrayOfStackTraceElement, Object[] paramArrayOfObject1, int[] paramArrayOfInt, Object[] paramArrayOfObject2)
/*     */   {
/* 161 */     int i = paramArrayOfObject1 == null ? 0 : paramArrayOfObject1.length;
/*     */     MonitorInfo[] arrayOfMonitorInfo;
/* 163 */     Object localObject1; if (i == 0) {
/* 164 */       arrayOfMonitorInfo = EMPTY_MONITORS;
/*     */     } else {
/* 166 */       arrayOfMonitorInfo = new MonitorInfo[i];
/* 167 */       for (j = 0; j < i; j++) {
/* 168 */         localObject1 = paramArrayOfObject1[j];
/* 169 */         String str1 = localObject1.getClass().getName();
/* 170 */         int m = System.identityHashCode(localObject1);
/* 171 */         int n = paramArrayOfInt[j];
/* 172 */         StackTraceElement localStackTraceElement = n >= 0 ? paramArrayOfStackTraceElement[n] : null;
/*     */         
/* 174 */         arrayOfMonitorInfo[j] = new MonitorInfo(str1, m, n, localStackTraceElement);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 181 */     int j = paramArrayOfObject2 == null ? 0 : paramArrayOfObject2.length;
/*     */     
/* 183 */     if (j == 0) {
/* 184 */       localObject1 = EMPTY_SYNCS;
/*     */     } else {
/* 186 */       localObject1 = new LockInfo[j];
/* 187 */       for (int k = 0; k < j; k++) {
/* 188 */         Object localObject2 = paramArrayOfObject2[k];
/* 189 */         String str2 = localObject2.getClass().getName();
/* 190 */         int i1 = System.identityHashCode(localObject2);
/* 191 */         localObject1[k] = new LockInfo(str2, i1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 196 */     initialize(paramThread1, paramInt, paramObject, paramThread2, paramLong1, paramLong2, paramLong3, paramLong4, paramArrayOfStackTraceElement, arrayOfMonitorInfo, (LockInfo[])localObject1);
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
/*     */   private void initialize(Thread paramThread1, int paramInt, Object paramObject, Thread paramThread2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, StackTraceElement[] paramArrayOfStackTraceElement, MonitorInfo[] paramArrayOfMonitorInfo, LockInfo[] paramArrayOfLockInfo)
/*     */   {
/* 223 */     this.threadId = paramThread1.getId();
/* 224 */     this.threadName = paramThread1.getName();
/* 225 */     this.threadState = ManagementFactoryHelper.toThreadState(paramInt);
/* 226 */     this.suspended = ManagementFactoryHelper.isThreadSuspended(paramInt);
/* 227 */     this.inNative = ManagementFactoryHelper.isThreadRunningNative(paramInt);
/* 228 */     this.blockedCount = paramLong1;
/* 229 */     this.blockedTime = paramLong2;
/* 230 */     this.waitedCount = paramLong3;
/* 231 */     this.waitedTime = paramLong4;
/*     */     
/* 233 */     if (paramObject == null) {
/* 234 */       this.lock = null;
/* 235 */       this.lockName = null;
/*     */     } else {
/* 237 */       this.lock = new LockInfo(paramObject);
/*     */       
/*     */ 
/* 240 */       this.lockName = (this.lock.getClassName() + '@' + Integer.toHexString(this.lock.getIdentityHashCode()));
/*     */     }
/* 242 */     if (paramThread2 == null) {
/* 243 */       this.lockOwnerId = -1L;
/* 244 */       this.lockOwnerName = null;
/*     */     } else {
/* 246 */       this.lockOwnerId = paramThread2.getId();
/* 247 */       this.lockOwnerName = paramThread2.getName();
/*     */     }
/* 249 */     if (paramArrayOfStackTraceElement == null) {
/* 250 */       this.stackTrace = NO_STACK_TRACE;
/*     */     } else {
/* 252 */       this.stackTrace = paramArrayOfStackTraceElement;
/*     */     }
/* 254 */     this.lockedMonitors = paramArrayOfMonitorInfo;
/* 255 */     this.lockedSynchronizers = paramArrayOfLockInfo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ThreadInfo(CompositeData paramCompositeData)
/*     */   {
/* 263 */     ThreadInfoCompositeData localThreadInfoCompositeData = ThreadInfoCompositeData.getInstance(paramCompositeData);
/*     */     
/* 265 */     this.threadId = localThreadInfoCompositeData.threadId();
/* 266 */     this.threadName = localThreadInfoCompositeData.threadName();
/* 267 */     this.blockedTime = localThreadInfoCompositeData.blockedTime();
/* 268 */     this.blockedCount = localThreadInfoCompositeData.blockedCount();
/* 269 */     this.waitedTime = localThreadInfoCompositeData.waitedTime();
/* 270 */     this.waitedCount = localThreadInfoCompositeData.waitedCount();
/* 271 */     this.lockName = localThreadInfoCompositeData.lockName();
/* 272 */     this.lockOwnerId = localThreadInfoCompositeData.lockOwnerId();
/* 273 */     this.lockOwnerName = localThreadInfoCompositeData.lockOwnerName();
/* 274 */     this.threadState = localThreadInfoCompositeData.threadState();
/* 275 */     this.suspended = localThreadInfoCompositeData.suspended();
/* 276 */     this.inNative = localThreadInfoCompositeData.inNative();
/* 277 */     this.stackTrace = localThreadInfoCompositeData.stackTrace();
/*     */     
/*     */ 
/* 280 */     if (localThreadInfoCompositeData.isCurrentVersion()) {
/* 281 */       this.lock = localThreadInfoCompositeData.lockInfo();
/* 282 */       this.lockedMonitors = localThreadInfoCompositeData.lockedMonitors();
/* 283 */       this.lockedSynchronizers = localThreadInfoCompositeData.lockedSynchronizers();
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 288 */       if (this.lockName != null) {
/* 289 */         String[] arrayOfString = this.lockName.split("@");
/* 290 */         if (arrayOfString.length == 2) {
/* 291 */           int i = Integer.parseInt(arrayOfString[1], 16);
/* 292 */           this.lock = new LockInfo(arrayOfString[0], i);
/*     */         } else {
/* 294 */           assert (arrayOfString.length == 2);
/* 295 */           this.lock = null;
/*     */         }
/*     */       } else {
/* 298 */         this.lock = null;
/*     */       }
/* 300 */       this.lockedMonitors = EMPTY_MONITORS;
/* 301 */       this.lockedSynchronizers = EMPTY_SYNCS;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getThreadId()
/*     */   {
/* 311 */     return this.threadId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getThreadName()
/*     */   {
/* 320 */     return this.threadName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Thread.State getThreadState()
/*     */   {
/* 329 */     return this.threadState;
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
/*     */   public long getBlockedTime()
/*     */   {
/* 358 */     return this.blockedTime;
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
/*     */   public long getBlockedCount()
/*     */   {
/* 372 */     return this.blockedCount;
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
/*     */   public long getWaitedTime()
/*     */   {
/* 403 */     return this.waitedTime;
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
/*     */   public long getWaitedCount()
/*     */   {
/* 418 */     return this.waitedCount;
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
/*     */   public LockInfo getLockInfo()
/*     */   {
/* 459 */     return this.lock;
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
/*     */   public String getLockName()
/*     */   {
/* 482 */     return this.lockName;
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
/*     */   public long getLockOwnerId()
/*     */   {
/* 500 */     return this.lockOwnerId;
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
/*     */   public String getLockOwnerName()
/*     */   {
/* 518 */     return this.lockOwnerName;
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
/*     */   public StackTraceElement[] getStackTrace()
/*     */   {
/* 541 */     return this.stackTrace;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSuspended()
/*     */   {
/* 553 */     return this.suspended;
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
/*     */   public boolean isInNative()
/*     */   {
/* 567 */     return this.inNative;
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
/*     */   public String toString()
/*     */   {
/* 584 */     StringBuilder localStringBuilder = new StringBuilder("\"" + getThreadName() + "\"" + " Id=" + getThreadId() + " " + getThreadState());
/* 585 */     if (getLockName() != null) {
/* 586 */       localStringBuilder.append(" on " + getLockName());
/*     */     }
/* 588 */     if (getLockOwnerName() != null) {
/* 589 */       localStringBuilder.append(" owned by \"" + getLockOwnerName() + "\" Id=" + 
/* 590 */         getLockOwnerId());
/*     */     }
/* 592 */     if (isSuspended()) {
/* 593 */       localStringBuilder.append(" (suspended)");
/*     */     }
/* 595 */     if (isInNative()) {
/* 596 */       localStringBuilder.append(" (in native)");
/*     */     }
/* 598 */     localStringBuilder.append('\n');
/* 599 */     Object localObject2; Object localObject3; for (int i = 0; 
/* 600 */         (i < this.stackTrace.length) && (i < 8); i++) {
/* 601 */       localObject1 = this.stackTrace[i];
/* 602 */       localStringBuilder.append("\tat " + ((StackTraceElement)localObject1).toString());
/* 603 */       localStringBuilder.append('\n');
/* 604 */       if ((i == 0) && (getLockInfo() != null)) {
/* 605 */         localObject2 = getThreadState();
/* 606 */         switch (localObject2) {
/*     */         case BLOCKED: 
/* 608 */           localStringBuilder.append("\t-  blocked on " + getLockInfo());
/* 609 */           localStringBuilder.append('\n');
/* 610 */           break;
/*     */         case WAITING: 
/* 612 */           localStringBuilder.append("\t-  waiting on " + getLockInfo());
/* 613 */           localStringBuilder.append('\n');
/* 614 */           break;
/*     */         case TIMED_WAITING: 
/* 616 */           localStringBuilder.append("\t-  waiting on " + getLockInfo());
/* 617 */           localStringBuilder.append('\n');
/* 618 */           break;
/*     */         }
/*     */         
/*     */       }
/*     */       
/* 623 */       for (localObject3 : this.lockedMonitors) {
/* 624 */         if (((MonitorInfo)localObject3).getLockedStackDepth() == i) {
/* 625 */           localStringBuilder.append("\t-  locked " + localObject3);
/* 626 */           localStringBuilder.append('\n');
/*     */         }
/*     */       }
/*     */     }
/* 630 */     if (i < this.stackTrace.length) {
/* 631 */       localStringBuilder.append("\t...");
/* 632 */       localStringBuilder.append('\n');
/*     */     }
/*     */     
/* 635 */     Object localObject1 = getLockedSynchronizers();
/* 636 */     if (localObject1.length > 0) {
/* 637 */       localStringBuilder.append("\n\tNumber of locked synchronizers = " + localObject1.length);
/* 638 */       localStringBuilder.append('\n');
/* 639 */       for (localObject3 : localObject1) {
/* 640 */         localStringBuilder.append("\t- " + localObject3);
/* 641 */         localStringBuilder.append('\n');
/*     */       }
/*     */     }
/* 644 */     localStringBuilder.append('\n');
/* 645 */     return localStringBuilder.toString();
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
/*     */   public static ThreadInfo from(CompositeData paramCompositeData)
/*     */   {
/* 787 */     if (paramCompositeData == null) {
/* 788 */       return null;
/*     */     }
/*     */     
/* 791 */     if ((paramCompositeData instanceof ThreadInfoCompositeData)) {
/* 792 */       return ((ThreadInfoCompositeData)paramCompositeData).getThreadInfo();
/*     */     }
/* 794 */     return new ThreadInfo(paramCompositeData);
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
/*     */   public MonitorInfo[] getLockedMonitors()
/*     */   {
/* 812 */     return this.lockedMonitors;
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
/*     */   public LockInfo[] getLockedSynchronizers()
/*     */   {
/* 829 */     return this.lockedSynchronizers;
/*     */   }
/*     */   
/* 832 */   private static final StackTraceElement[] NO_STACK_TRACE = new StackTraceElement[0];
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/ThreadInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */