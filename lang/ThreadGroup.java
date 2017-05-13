/*      */ package java.lang;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.Arrays;
/*      */ import sun.misc.VM;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThreadGroup
/*      */   implements Thread.UncaughtExceptionHandler
/*      */ {
/*      */   private final ThreadGroup parent;
/*      */   String name;
/*      */   int maxPriority;
/*      */   boolean destroyed;
/*      */   boolean daemon;
/*      */   boolean vmAllowSuspension;
/*   65 */   int nUnstartedThreads = 0;
/*      */   
/*      */   int nthreads;
/*      */   
/*      */   Thread[] threads;
/*      */   
/*      */   int ngroups;
/*      */   
/*      */   ThreadGroup[] groups;
/*      */   
/*      */   private ThreadGroup()
/*      */   {
/*   77 */     this.name = "system";
/*   78 */     this.maxPriority = 10;
/*   79 */     this.parent = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ThreadGroup(String paramString)
/*      */   {
/*   96 */     this(Thread.currentThread().getThreadGroup(), paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ThreadGroup(ThreadGroup paramThreadGroup, String paramString)
/*      */   {
/*  117 */     this(checkParentAccess(paramThreadGroup), paramThreadGroup, paramString);
/*      */   }
/*      */   
/*      */   private ThreadGroup(Void paramVoid, ThreadGroup paramThreadGroup, String paramString) {
/*  121 */     this.name = paramString;
/*  122 */     this.maxPriority = paramThreadGroup.maxPriority;
/*  123 */     this.daemon = paramThreadGroup.daemon;
/*  124 */     this.vmAllowSuspension = paramThreadGroup.vmAllowSuspension;
/*  125 */     this.parent = paramThreadGroup;
/*  126 */     paramThreadGroup.add(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Void checkParentAccess(ThreadGroup paramThreadGroup)
/*      */   {
/*  135 */     paramThreadGroup.checkAccess();
/*  136 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getName()
/*      */   {
/*  146 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ThreadGroup getParent()
/*      */   {
/*  166 */     if (this.parent != null)
/*  167 */       this.parent.checkAccess();
/*  168 */     return this.parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int getMaxPriority()
/*      */   {
/*  182 */     return this.maxPriority;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isDaemon()
/*      */   {
/*  195 */     return this.daemon;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isDestroyed()
/*      */   {
/*  205 */     return this.destroyed;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setDaemon(boolean paramBoolean)
/*      */   {
/*  227 */     checkAccess();
/*  228 */     this.daemon = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setMaxPriority(int paramInt)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  262 */     synchronized (this) {
/*  263 */       checkAccess();
/*  264 */       if ((paramInt < 1) || (paramInt > 10)) {
/*  265 */         return;
/*      */       }
/*  267 */       this.maxPriority = (this.parent != null ? Math.min(paramInt, this.parent.maxPriority) : paramInt);
/*  268 */       localObject1 = this.ngroups;
/*  269 */       if (this.groups != null) {
/*  270 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/*  272 */         arrayOfThreadGroup = null;
/*      */       }
/*      */     }
/*  275 */     for (??? = 0; ??? < localObject1; ???++) {
/*  276 */       arrayOfThreadGroup[???].setMaxPriority(paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean parentOf(ThreadGroup paramThreadGroup)
/*      */   {
/*  291 */     for (; 
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  291 */         paramThreadGroup != null; paramThreadGroup = paramThreadGroup.parent) {
/*  292 */       if (paramThreadGroup == this) {
/*  293 */         return true;
/*      */       }
/*      */     }
/*  296 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void checkAccess()
/*      */   {
/*  313 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  314 */     if (localSecurityManager != null) {
/*  315 */       localSecurityManager.checkAccess(this);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int activeCount()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  342 */     synchronized (this) {
/*  343 */       if (this.destroyed) {
/*  344 */         return 0;
/*      */       }
/*  346 */       i = this.nthreads;
/*  347 */       localObject1 = this.ngroups;
/*  348 */       if (this.groups != null) {
/*  349 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/*  351 */         arrayOfThreadGroup = null;
/*      */       }
/*      */     }
/*  354 */     for (??? = 0; ??? < localObject1; ???++) {
/*  355 */       i += arrayOfThreadGroup[???].activeCount();
/*      */     }
/*  357 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int enumerate(Thread[] paramArrayOfThread)
/*      */   {
/*  383 */     checkAccess();
/*  384 */     return enumerate(paramArrayOfThread, 0, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int enumerate(Thread[] paramArrayOfThread, boolean paramBoolean)
/*      */   {
/*  421 */     checkAccess();
/*  422 */     return enumerate(paramArrayOfThread, 0, paramBoolean);
/*      */   }
/*      */   
/*      */   private int enumerate(Thread[] paramArrayOfThread, int paramInt, boolean paramBoolean) {
/*  426 */     Object localObject1 = 0;
/*  427 */     ThreadGroup[] arrayOfThreadGroup = null;
/*  428 */     synchronized (this) {
/*  429 */       if (this.destroyed) {
/*  430 */         return 0;
/*      */       }
/*  432 */       int i = this.nthreads;
/*  433 */       if (i > paramArrayOfThread.length - paramInt) {
/*  434 */         i = paramArrayOfThread.length - paramInt;
/*      */       }
/*  436 */       for (int j = 0; j < i; j++) {
/*  437 */         if (this.threads[j].isAlive()) {
/*  438 */           paramArrayOfThread[(paramInt++)] = this.threads[j];
/*      */         }
/*      */       }
/*  441 */       if (paramBoolean) {
/*  442 */         localObject1 = this.ngroups;
/*  443 */         if (this.groups != null) {
/*  444 */           arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */         } else {
/*  446 */           arrayOfThreadGroup = null;
/*      */         }
/*      */       }
/*      */     }
/*  450 */     if (paramBoolean) {
/*  451 */       for (??? = 0; ??? < localObject1; ???++) {
/*  452 */         paramInt = arrayOfThreadGroup[???].enumerate(paramArrayOfThread, paramInt, true);
/*      */       }
/*      */     }
/*  455 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int activeGroupCount()
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  476 */     synchronized (this) {
/*  477 */       if (this.destroyed) {
/*  478 */         return 0;
/*      */       }
/*  480 */       localObject1 = this.ngroups;
/*  481 */       if (this.groups != null) {
/*  482 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/*  484 */         arrayOfThreadGroup = null;
/*      */       }
/*      */     }
/*  487 */     ??? = localObject1;
/*  488 */     int i; for (int j = 0; j < localObject1; j++) {
/*  489 */       ??? += arrayOfThreadGroup[j].activeGroupCount();
/*      */     }
/*  491 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int enumerate(ThreadGroup[] paramArrayOfThreadGroup)
/*      */   {
/*  517 */     checkAccess();
/*  518 */     return enumerate(paramArrayOfThreadGroup, 0, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int enumerate(ThreadGroup[] paramArrayOfThreadGroup, boolean paramBoolean)
/*      */   {
/*  555 */     checkAccess();
/*  556 */     return enumerate(paramArrayOfThreadGroup, 0, paramBoolean);
/*      */   }
/*      */   
/*      */   private int enumerate(ThreadGroup[] paramArrayOfThreadGroup, int paramInt, boolean paramBoolean) {
/*  560 */     Object localObject1 = 0;
/*  561 */     ThreadGroup[] arrayOfThreadGroup = null;
/*  562 */     synchronized (this) {
/*  563 */       if (this.destroyed) {
/*  564 */         return 0;
/*      */       }
/*  566 */       int i = this.ngroups;
/*  567 */       if (i > paramArrayOfThreadGroup.length - paramInt) {
/*  568 */         i = paramArrayOfThreadGroup.length - paramInt;
/*      */       }
/*  570 */       if (i > 0) {
/*  571 */         System.arraycopy(this.groups, 0, paramArrayOfThreadGroup, paramInt, i);
/*  572 */         paramInt += i;
/*      */       }
/*  574 */       if (paramBoolean) {
/*  575 */         localObject1 = this.ngroups;
/*  576 */         if (this.groups != null) {
/*  577 */           arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */         } else {
/*  579 */           arrayOfThreadGroup = null;
/*      */         }
/*      */       }
/*      */     }
/*  583 */     if (paramBoolean) {
/*  584 */       for (??? = 0; ??? < localObject1; ???++) {
/*  585 */         paramInt = arrayOfThreadGroup[???].enumerate(paramArrayOfThreadGroup, paramInt, true);
/*      */       }
/*      */     }
/*  588 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public final void stop()
/*      */   {
/*  612 */     if (stopOrSuspend(false)) {
/*  613 */       Thread.currentThread().stop();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void interrupt()
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  636 */     synchronized (this) {
/*  637 */       checkAccess();
/*  638 */       for (int i = 0; i < this.nthreads; i++) {
/*  639 */         this.threads[i].interrupt();
/*      */       }
/*  641 */       localObject1 = this.ngroups;
/*  642 */       if (this.groups != null) {
/*  643 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/*  645 */         arrayOfThreadGroup = null;
/*      */       }
/*      */     }
/*  648 */     for (??? = 0; ??? < localObject1; ???++) {
/*  649 */       arrayOfThreadGroup[???].interrupt();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public final void suspend()
/*      */   {
/*  675 */     if (stopOrSuspend(true)) {
/*  676 */       Thread.currentThread().suspend();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean stopOrSuspend(boolean paramBoolean)
/*      */   {
/*  688 */     boolean bool = false;
/*  689 */     Thread localThread = Thread.currentThread();
/*      */     
/*  691 */     ThreadGroup[] arrayOfThreadGroup = null;
/*  692 */     Object localObject1; synchronized (this) {
/*  693 */       checkAccess();
/*  694 */       for (int i = 0; i < this.nthreads; i++) {
/*  695 */         if (this.threads[i] == localThread) {
/*  696 */           bool = true;
/*  697 */         } else if (paramBoolean) {
/*  698 */           this.threads[i].suspend();
/*      */         } else {
/*  700 */           this.threads[i].stop();
/*      */         }
/*      */       }
/*  703 */       localObject1 = this.ngroups;
/*  704 */       if (this.groups != null) {
/*  705 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       }
/*      */     }
/*  708 */     for (??? = 0; ??? < localObject1; ???++) {
/*  709 */       bool = (arrayOfThreadGroup[???].stopOrSuspend(paramBoolean)) || (bool);
/*      */     }
/*  711 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public final void resume()
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  740 */     synchronized (this) {
/*  741 */       checkAccess();
/*  742 */       for (int i = 0; i < this.nthreads; i++) {
/*  743 */         this.threads[i].resume();
/*      */       }
/*  745 */       localObject1 = this.ngroups;
/*  746 */       if (this.groups != null) {
/*  747 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/*  749 */         arrayOfThreadGroup = null;
/*      */       }
/*      */     }
/*  752 */     for (??? = 0; ??? < localObject1; ???++) {
/*  753 */       arrayOfThreadGroup[???].resume();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void destroy()
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  775 */     synchronized (this) {
/*  776 */       checkAccess();
/*  777 */       if ((this.destroyed) || (this.nthreads > 0)) {
/*  778 */         throw new IllegalThreadStateException();
/*      */       }
/*  780 */       localObject1 = this.ngroups;
/*  781 */       if (this.groups != null) {
/*  782 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/*  784 */         arrayOfThreadGroup = null;
/*      */       }
/*  786 */       if (this.parent != null) {
/*  787 */         this.destroyed = true;
/*  788 */         this.ngroups = 0;
/*  789 */         this.groups = null;
/*  790 */         this.nthreads = 0;
/*  791 */         this.threads = null;
/*      */       }
/*      */     }
/*  794 */     for (??? = 0; ??? < localObject1; ???++) {
/*  795 */       arrayOfThreadGroup[???].destroy();
/*      */     }
/*  797 */     if (this.parent != null) {
/*  798 */       this.parent.remove(this);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void add(ThreadGroup paramThreadGroup)
/*      */   {
/*  808 */     synchronized (this) {
/*  809 */       if (this.destroyed) {
/*  810 */         throw new IllegalThreadStateException();
/*      */       }
/*  812 */       if (this.groups == null) {
/*  813 */         this.groups = new ThreadGroup[4];
/*  814 */       } else if (this.ngroups == this.groups.length) {
/*  815 */         this.groups = ((ThreadGroup[])Arrays.copyOf(this.groups, this.ngroups * 2));
/*      */       }
/*  817 */       this.groups[this.ngroups] = paramThreadGroup;
/*      */       
/*      */ 
/*      */ 
/*  821 */       this.ngroups += 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void remove(ThreadGroup paramThreadGroup)
/*      */   {
/*  831 */     synchronized (this) {
/*  832 */       if (this.destroyed) {
/*  833 */         return;
/*      */       }
/*  835 */       for (int i = 0; i < this.ngroups; i++) {
/*  836 */         if (this.groups[i] == paramThreadGroup) {
/*  837 */           this.ngroups -= 1;
/*  838 */           System.arraycopy(this.groups, i + 1, this.groups, i, this.ngroups - i);
/*      */           
/*      */ 
/*  841 */           this.groups[this.ngroups] = null;
/*  842 */           break;
/*      */         }
/*      */       }
/*  845 */       if (this.nthreads == 0) {
/*  846 */         notifyAll();
/*      */       }
/*  848 */       if ((this.daemon) && (this.nthreads == 0) && (this.nUnstartedThreads == 0) && (this.ngroups == 0))
/*      */       {
/*      */ 
/*  851 */         destroy();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addUnstarted()
/*      */   {
/*  865 */     synchronized (this) {
/*  866 */       if (this.destroyed) {
/*  867 */         throw new IllegalThreadStateException();
/*      */       }
/*  869 */       this.nUnstartedThreads += 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void add(Thread paramThread)
/*      */   {
/*  887 */     synchronized (this) {
/*  888 */       if (this.destroyed) {
/*  889 */         throw new IllegalThreadStateException();
/*      */       }
/*  891 */       if (this.threads == null) {
/*  892 */         this.threads = new Thread[4];
/*  893 */       } else if (this.nthreads == this.threads.length) {
/*  894 */         this.threads = ((Thread[])Arrays.copyOf(this.threads, this.nthreads * 2));
/*      */       }
/*  896 */       this.threads[this.nthreads] = paramThread;
/*      */       
/*      */ 
/*      */ 
/*  900 */       this.nthreads += 1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  906 */       this.nUnstartedThreads -= 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void threadStartFailed(Thread paramThread)
/*      */   {
/*  923 */     synchronized (this) {
/*  924 */       remove(paramThread);
/*  925 */       this.nUnstartedThreads += 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void threadTerminated(Thread paramThread)
/*      */   {
/*  941 */     synchronized (this) {
/*  942 */       remove(paramThread);
/*      */       
/*  944 */       if (this.nthreads == 0) {
/*  945 */         notifyAll();
/*      */       }
/*  947 */       if ((this.daemon) && (this.nthreads == 0) && (this.nUnstartedThreads == 0) && (this.ngroups == 0))
/*      */       {
/*      */ 
/*  950 */         destroy();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void remove(Thread paramThread)
/*      */   {
/*  963 */     synchronized (this) {
/*  964 */       if (this.destroyed) {
/*  965 */         return;
/*      */       }
/*  967 */       for (int i = 0; i < this.nthreads; i++) {
/*  968 */         if (this.threads[i] == paramThread) {
/*  969 */           System.arraycopy(this.threads, i + 1, this.threads, i, --this.nthreads - i);
/*      */           
/*      */ 
/*  972 */           this.threads[this.nthreads] = null;
/*  973 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  986 */   public void list() { list(System.out, 0); }
/*      */   
/*      */   void list(PrintStream paramPrintStream, int paramInt) {
/*      */     Object localObject1;
/*      */     ThreadGroup[] arrayOfThreadGroup;
/*  991 */     synchronized (this) {
/*  992 */       for (int i = 0; i < paramInt; i++) {
/*  993 */         paramPrintStream.print(" ");
/*      */       }
/*  995 */       paramPrintStream.println(this);
/*  996 */       paramInt += 4;
/*  997 */       for (i = 0; i < this.nthreads; i++) {
/*  998 */         for (int j = 0; j < paramInt; j++) {
/*  999 */           paramPrintStream.print(" ");
/*      */         }
/* 1001 */         paramPrintStream.println(this.threads[i]);
/*      */       }
/* 1003 */       localObject1 = this.ngroups;
/* 1004 */       if (this.groups != null) {
/* 1005 */         arrayOfThreadGroup = (ThreadGroup[])Arrays.copyOf(this.groups, localObject1);
/*      */       } else {
/* 1007 */         arrayOfThreadGroup = null;
/*      */       }
/*      */     }
/* 1010 */     for (??? = 0; ??? < localObject1; ???++) {
/* 1011 */       arrayOfThreadGroup[???].list(paramPrintStream, paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void uncaughtException(Thread paramThread, Throwable paramThrowable)
/*      */   {
/* 1051 */     if (this.parent != null) {
/* 1052 */       this.parent.uncaughtException(paramThread, paramThrowable);
/*      */     }
/*      */     else {
/* 1055 */       Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
/* 1056 */       if (localUncaughtExceptionHandler != null) {
/* 1057 */         localUncaughtExceptionHandler.uncaughtException(paramThread, paramThrowable);
/* 1058 */       } else if (!(paramThrowable instanceof ThreadDeath)) {
/* 1059 */         System.err.print("Exception in thread \"" + paramThread
/* 1060 */           .getName() + "\" ");
/* 1061 */         paramThrowable.printStackTrace(System.err);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public boolean allowThreadSuspension(boolean paramBoolean)
/*      */   {
/* 1078 */     this.vmAllowSuspension = paramBoolean;
/* 1079 */     if (!paramBoolean) {
/* 1080 */       VM.unsuspendSomeThreads();
/*      */     }
/* 1082 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1092 */     return getClass().getName() + "[name=" + getName() + ",maxpri=" + this.maxPriority + "]";
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ThreadGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */