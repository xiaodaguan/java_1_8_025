/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import sun.misc.Contended;
/*      */ import sun.misc.Unsafe;
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
/*      */ @Contended
/*      */ public class ForkJoinPool
/*      */   extends AbstractExecutorService
/*      */ {
/*      */   public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
/*      */   private static final RuntimePermission modifyThreadPermission;
/*      */   static final ForkJoinPool common;
/*      */   static final int commonParallelism;
/*      */   private static int poolNumberSequence;
/*      */   private static final long IDLE_TIMEOUT = 2000000000L;
/*      */   private static final long FAST_IDLE_TIMEOUT = 200000000L;
/*      */   private static final long TIMEOUT_SLOP = 2000000L;
/*      */   private static final int MAX_HELP = 64;
/*      */   private static final int SEED_INCREMENT = -1640531527;
/*      */   private static final int AC_SHIFT = 48;
/*      */   private static final int TC_SHIFT = 32;
/*      */   private static final int ST_SHIFT = 31;
/*      */   private static final int EC_SHIFT = 16;
/*      */   private static final int SMASK = 65535;
/*      */   private static final int MAX_CAP = 32767;
/*      */   private static final int EVENMASK = 65534;
/*      */   private static final int SQMASK = 126;
/*      */   private static final int SHORT_SIGN = 32768;
/*      */   private static final int INT_SIGN = Integer.MIN_VALUE;
/*      */   private static final long STOP_BIT = 2147483648L;
/*      */   private static final long AC_MASK = -281474976710656L;
/*      */   private static final long TC_MASK = 281470681743360L;
/*      */   private static final long TC_UNIT = 4294967296L;
/*      */   private static final long AC_UNIT = 281474976710656L;
/*      */   private static final int UAC_SHIFT = 16;
/*      */   private static final int UTC_SHIFT = 0;
/*      */   private static final int UAC_MASK = -65536;
/*      */   private static final int UTC_MASK = 65535;
/*      */   private static final int UAC_UNIT = 65536;
/*      */   private static final int UTC_UNIT = 1;
/*      */   private static final int E_MASK = Integer.MAX_VALUE;
/*      */   private static final int E_SEQ = 65536;
/*      */   private static final int SHUTDOWN = Integer.MIN_VALUE;
/*      */   private static final int PL_LOCK = 2;
/*      */   private static final int PL_SIGNAL = 1;
/*      */   private static final int PL_SPINS = 256;
/*      */   static final int LIFO_QUEUE = 0;
/*      */   static final int FIFO_QUEUE = 1;
/*      */   static final int SHARED_QUEUE = -1;
/*      */   volatile long stealCount;
/*      */   volatile long ctl;
/*      */   volatile int plock;
/*      */   volatile int indexSeed;
/*      */   final short parallelism;
/*      */   final short mode;
/*      */   WorkQueue[] workQueues;
/*      */   final ForkJoinWorkerThreadFactory factory;
/*      */   final Thread.UncaughtExceptionHandler ueh;
/*      */   final String workerNamePrefix;
/*      */   private static final Unsafe U;
/*      */   private static final long CTL;
/*      */   private static final long PARKBLOCKER;
/*      */   private static final int ABASE;
/*      */   private static final int ASHIFT;
/*      */   private static final long STEALCOUNT;
/*      */   private static final long PLOCK;
/*      */   private static final long INDEXSEED;
/*      */   private static final long QBASE;
/*      */   private static final long QLOCK;
/*      */   
/*      */   private static void checkPermission()
/*      */   {
/*  563 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  564 */     if (localSecurityManager != null) {
/*  565 */       localSecurityManager.checkPermission(modifyThreadPermission);
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
/*      */   public static abstract interface ForkJoinWorkerThreadFactory
/*      */   {
/*      */     public abstract ForkJoinWorkerThread newThread(ForkJoinPool paramForkJoinPool);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class DefaultForkJoinWorkerThreadFactory
/*      */     implements ForkJoinPool.ForkJoinWorkerThreadFactory
/*      */   {
/*      */     public final ForkJoinWorkerThread newThread(ForkJoinPool paramForkJoinPool)
/*      */     {
/*  594 */       return new ForkJoinWorkerThread(paramForkJoinPool);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class EmptyTask
/*      */     extends ForkJoinTask<Void>
/*      */   {
/*      */     private static final long serialVersionUID = -7721805057305804111L;
/*      */     
/*      */ 
/*  606 */     EmptyTask() { this.status = -268435456; }
/*  607 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/*  609 */     public final boolean exec() { return true; }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Contended
/*      */   static final class WorkQueue
/*      */   {
/*      */     static final int INITIAL_QUEUE_CAPACITY = 8192;
/*      */     
/*      */ 
/*      */ 
/*      */     static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
/*      */     
/*      */ 
/*      */ 
/*      */     volatile int eventCount;
/*      */     
/*      */ 
/*      */ 
/*      */     int nextWait;
/*      */     
/*      */ 
/*      */ 
/*      */     int nsteals;
/*      */     
/*      */ 
/*      */ 
/*      */     int hint;
/*      */     
/*      */ 
/*      */ 
/*      */     short poolIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     final short mode;
/*      */     
/*      */ 
/*      */ 
/*      */     volatile int qlock;
/*      */     
/*      */ 
/*      */ 
/*      */     volatile int base;
/*      */     
/*      */ 
/*      */ 
/*      */     int top;
/*      */     
/*      */ 
/*      */ 
/*      */     ForkJoinTask<?>[] array;
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinPool pool;
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinWorkerThread owner;
/*      */     
/*      */ 
/*      */     volatile Thread parker;
/*      */     
/*      */ 
/*      */     volatile ForkJoinTask<?> currentJoin;
/*      */     
/*      */ 
/*      */     ForkJoinTask<?> currentSteal;
/*      */     
/*      */ 
/*      */     private static final Unsafe U;
/*      */     
/*      */ 
/*      */     private static final long QBASE;
/*      */     
/*      */ 
/*      */     private static final long QLOCK;
/*      */     
/*      */ 
/*      */     private static final int ABASE;
/*      */     
/*      */ 
/*      */     private static final int ASHIFT;
/*      */     
/*      */ 
/*      */ 
/*      */     WorkQueue(ForkJoinPool paramForkJoinPool, ForkJoinWorkerThread paramForkJoinWorkerThread, int paramInt1, int paramInt2)
/*      */     {
/*  700 */       this.pool = paramForkJoinPool;
/*  701 */       this.owner = paramForkJoinWorkerThread;
/*  702 */       this.mode = ((short)paramInt1);
/*  703 */       this.hint = paramInt2;
/*      */       
/*  705 */       this.base = (this.top = 'က');
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final int queueSize()
/*      */     {
/*  712 */       int i = this.base - this.top;
/*  713 */       return i >= 0 ? 0 : -i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final boolean isEmpty()
/*      */     {
/*      */       int j;
/*      */       
/*      */ 
/*  723 */       int k = this.base - (j = this.top);
/*      */       
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */       int i;
/*      */       
/*  729 */       return (k >= 0) || ((k == -1) && (((arrayOfForkJoinTask = this.array) == null) || ((i = arrayOfForkJoinTask.length - 1) < 0) || (U.getObject(arrayOfForkJoinTask, ((i & j - 1) << ASHIFT) + ABASE) == null)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final void push(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*  741 */       int i = this.top;
/*  742 */       ForkJoinTask[] arrayOfForkJoinTask; if ((arrayOfForkJoinTask = this.array) != null) {
/*  743 */         int k = arrayOfForkJoinTask.length - 1;
/*  744 */         U.putOrderedObject(arrayOfForkJoinTask, ((k & i) << ASHIFT) + ABASE, paramForkJoinTask);
/*  745 */         int j; if ((j = (this.top = i + 1) - this.base) <= 2) { ForkJoinPool localForkJoinPool;
/*  746 */           (localForkJoinPool = this.pool).signalWork(localForkJoinPool.workQueues, this);
/*  747 */         } else if (j >= k) {
/*  748 */           growArray();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?>[] growArray()
/*      */     {
/*  758 */       ForkJoinTask[] arrayOfForkJoinTask1 = this.array;
/*  759 */       int i = arrayOfForkJoinTask1 != null ? arrayOfForkJoinTask1.length << 1 : 8192;
/*  760 */       if (i > 67108864) {
/*  761 */         throw new RejectedExecutionException("Queue capacity exceeded");
/*      */       }
/*  763 */       ForkJoinTask[] arrayOfForkJoinTask2 = this.array = new ForkJoinTask[i];
/*  764 */       int j; int k; int m; if ((arrayOfForkJoinTask1 != null) && ((j = arrayOfForkJoinTask1.length - 1) >= 0) && ((k = this.top) - (m = this.base) > 0))
/*      */       {
/*  766 */         int n = i - 1;
/*      */         do
/*      */         {
/*  769 */           int i1 = ((m & j) << ASHIFT) + ABASE;
/*  770 */           int i2 = ((m & n) << ASHIFT) + ABASE;
/*  771 */           ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask1, i1);
/*  772 */           if ((localForkJoinTask != null) && 
/*  773 */             (U.compareAndSwapObject(arrayOfForkJoinTask1, i1, localForkJoinTask, null)))
/*  774 */             U.putObjectVolatile(arrayOfForkJoinTask2, i2, localForkJoinTask);
/*  775 */           m++; } while (m != k);
/*      */       }
/*  777 */       return arrayOfForkJoinTask2;
/*      */     }
/*      */     
/*      */ 
/*      */     final ForkJoinTask<?> pop()
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */       int i;
/*  786 */       if (((arrayOfForkJoinTask = this.array) != null) && ((i = arrayOfForkJoinTask.length - 1) >= 0)) { int j;
/*  787 */         while ((j = this.top - 1) - this.base >= 0) {
/*  788 */           long l = ((i & j) << ASHIFT) + ABASE;
/*  789 */           ForkJoinTask localForkJoinTask; if ((localForkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l)) == null)
/*      */             break;
/*  791 */           if (U.compareAndSwapObject(arrayOfForkJoinTask, l, localForkJoinTask, null)) {
/*  792 */             this.top = j;
/*  793 */             return localForkJoinTask;
/*      */           }
/*      */         }
/*      */       }
/*  797 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?> pollAt(int paramInt)
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */ 
/*  807 */       if ((arrayOfForkJoinTask = this.array) != null) {
/*  808 */         int i = ((arrayOfForkJoinTask.length - 1 & paramInt) << ASHIFT) + ABASE;
/*  809 */         ForkJoinTask localForkJoinTask; if (((localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, i)) != null) && (this.base == paramInt) && 
/*  810 */           (U.compareAndSwapObject(arrayOfForkJoinTask, i, localForkJoinTask, null))) {
/*  811 */           U.putOrderedInt(this, QBASE, paramInt + 1);
/*  812 */           return localForkJoinTask;
/*      */         }
/*      */       }
/*  815 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */     final ForkJoinTask<?> poll()
/*      */     {
/*      */       int i;
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*  823 */       while (((i = this.base) - this.top < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/*  824 */         int j = ((arrayOfForkJoinTask.length - 1 & i) << ASHIFT) + ABASE;
/*  825 */         ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, j);
/*  826 */         if (localForkJoinTask != null) {
/*  827 */           if (U.compareAndSwapObject(arrayOfForkJoinTask, j, localForkJoinTask, null)) {
/*  828 */             U.putOrderedInt(this, QBASE, i + 1);
/*  829 */             return localForkJoinTask;
/*      */           }
/*      */         }
/*  832 */         else if (this.base == i) {
/*  833 */           if (i + 1 == this.top)
/*      */             break;
/*  835 */           Thread.yield();
/*      */         }
/*      */       }
/*  838 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?> nextLocalTask()
/*      */     {
/*  845 */       return this.mode == 0 ? pop() : poll();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?> peek()
/*      */     {
/*  852 */       ForkJoinTask[] arrayOfForkJoinTask = this.array;
/*  853 */       int i; if ((arrayOfForkJoinTask == null) || ((i = arrayOfForkJoinTask.length - 1) < 0))
/*  854 */         return null;
/*  855 */       int j = this.mode == 0 ? this.top - 1 : this.base;
/*  856 */       int k = ((j & i) << ASHIFT) + ABASE;
/*  857 */       return (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, k);
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean tryUnpush(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */       int i;
/*  866 */       if (((arrayOfForkJoinTask = this.array) != null) && ((i = this.top) != this.base))
/*      */       {
/*  868 */         if (U.compareAndSwapObject(arrayOfForkJoinTask, ((arrayOfForkJoinTask.length - 1 & --i) << ASHIFT) + ABASE, paramForkJoinTask, null)) {
/*  869 */           this.top = i;
/*  870 */           return true;
/*      */         } }
/*  872 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final void cancelAll()
/*      */     {
/*  879 */       ForkJoinTask.cancelIgnoringExceptions(this.currentJoin);
/*  880 */       ForkJoinTask.cancelIgnoringExceptions(this.currentSteal);
/*  881 */       ForkJoinTask localForkJoinTask; while ((localForkJoinTask = poll()) != null) {
/*  882 */         ForkJoinTask.cancelIgnoringExceptions(localForkJoinTask);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final void pollAndExecAll()
/*      */     {
/*      */       ForkJoinTask localForkJoinTask;
/*      */       
/*  891 */       while ((localForkJoinTask = poll()) != null) {
/*  892 */         localForkJoinTask.doExec();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final void runTask(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*  900 */       if ((this.currentSteal = paramForkJoinTask) != null)
/*      */       {
/*  902 */         paramForkJoinTask.doExec();
/*  903 */         ForkJoinTask[] arrayOfForkJoinTask = this.array;
/*  904 */         int i = this.mode;
/*  905 */         this.nsteals += 1;
/*  906 */         this.currentSteal = null;
/*  907 */         if (i != 0) {
/*  908 */           pollAndExecAll();
/*  909 */         } else if (arrayOfForkJoinTask != null) {
/*  910 */           int k = arrayOfForkJoinTask.length - 1;
/*      */           int j;
/*  912 */           while ((j = this.top - 1) - this.base >= 0) {
/*      */             ForkJoinTask localForkJoinTask;
/*  914 */             if ((localForkJoinTask = (ForkJoinTask)U.getAndSetObject(arrayOfForkJoinTask, ((k & j) << ASHIFT) + ABASE, null)) == null) break;
/*  915 */             this.top = j;
/*  916 */             localForkJoinTask.doExec();
/*      */           } }
/*      */         ForkJoinWorkerThread localForkJoinWorkerThread;
/*  919 */         if ((localForkJoinWorkerThread = this.owner) != null) {
/*  920 */           localForkJoinWorkerThread.afterTopLevelExec();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean tryRemoveAndExec(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       int i;
/*      */       int j;
/*      */       int k;
/*      */       int m;
/*      */       boolean bool1;
/*  934 */       if ((paramForkJoinTask != null) && ((arrayOfForkJoinTask = this.array) != null) && ((i = arrayOfForkJoinTask.length - 1) >= 0) && ((m = (j = this.top) - (k = this.base)) > 0))
/*      */       {
/*  936 */         boolean bool2 = false;int n = 1;
/*  937 */         bool1 = true;
/*      */         for (;;) {
/*  939 */           j--;long l = ((j & i) << ASHIFT) + ABASE;
/*  940 */           ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l);
/*  941 */           if (localForkJoinTask == null)
/*      */             break;
/*  943 */           if (localForkJoinTask == paramForkJoinTask) {
/*  944 */             if (j + 1 == this.top) {
/*  945 */               if (!U.compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, null))
/*      */                 break;
/*  947 */               this.top = j;
/*  948 */               bool2 = true; break;
/*      */             }
/*  950 */             if (this.base != k) break;
/*  951 */             bool2 = U.compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, new ForkJoinPool.EmptyTask()); break;
/*      */           }
/*      */           
/*      */ 
/*  955 */           if (localForkJoinTask.status >= 0) {
/*  956 */             n = 0;
/*  957 */           } else if (j + 1 == this.top) {
/*  958 */             if (!U.compareAndSwapObject(arrayOfForkJoinTask, l, localForkJoinTask, null)) break;
/*  959 */             this.top = j; break;
/*      */           }
/*      */           
/*  962 */           m--; if (m == 0) {
/*  963 */             if ((n != 0) || (this.base != k)) break;
/*  964 */             bool1 = false; break;
/*      */           }
/*      */         }
/*      */         
/*  968 */         if (bool2) {
/*  969 */           paramForkJoinTask.doExec();
/*      */         }
/*      */       } else {
/*  972 */         bool1 = false; }
/*  973 */       return bool1;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean pollAndExecCC(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*      */       int i;
/*      */       
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*  982 */       if (((i = this.base) - this.top < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/*  983 */         long l = ((arrayOfForkJoinTask.length - 1 & i) << ASHIFT) + ABASE;
/*  984 */         Object localObject; if ((localObject = U.getObjectVolatile(arrayOfForkJoinTask, l)) == null)
/*  985 */           return true;
/*  986 */         if ((localObject instanceof CountedCompleter)) {
/*  987 */           CountedCompleter localCountedCompleter1 = (CountedCompleter)localObject;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*  988 */           for (;;) { if (localCountedCompleter2 == paramCountedCompleter) {
/*  989 */               if ((this.base == i) && 
/*  990 */                 (U.compareAndSwapObject(arrayOfForkJoinTask, l, localCountedCompleter1, null))) {
/*  991 */                 U.putOrderedInt(this, QBASE, i + 1);
/*  992 */                 localCountedCompleter1.doExec();
/*      */               }
/*  994 */               return true;
/*      */             }
/*  996 */             if ((localCountedCompleter2 = localCountedCompleter2.completer) == null)
/*      */               break;
/*      */           }
/*      */         }
/*      */       }
/* 1001 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean externalPopAndExecCC(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*      */       int i;
/*      */       
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/* 1010 */       if ((this.base - (i = this.top) < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/* 1011 */         long l = ((arrayOfForkJoinTask.length - 1 & i - 1) << ASHIFT) + ABASE;
/* 1012 */         Object localObject; if (((localObject = U.getObject(arrayOfForkJoinTask, l)) instanceof CountedCompleter)) {
/* 1013 */           CountedCompleter localCountedCompleter1 = (CountedCompleter)localObject;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/* 1014 */           for (;;) { if (localCountedCompleter2 == paramCountedCompleter) {
/* 1015 */               if (U.compareAndSwapInt(this, QLOCK, 0, 1))
/* 1016 */                 if ((this.top == i) && (this.array == arrayOfForkJoinTask) && 
/* 1017 */                   (U.compareAndSwapObject(arrayOfForkJoinTask, l, localCountedCompleter1, null))) {
/* 1018 */                   this.top = (i - 1);
/* 1019 */                   this.qlock = 0;
/* 1020 */                   localCountedCompleter1.doExec();
/*      */                 }
/*      */                 else {
/* 1023 */                   this.qlock = 0;
/*      */                 }
/* 1025 */               return true;
/*      */             }
/* 1027 */             if ((localCountedCompleter2 = localCountedCompleter2.completer) == null)
/*      */               break;
/*      */           }
/*      */         }
/*      */       }
/* 1032 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean internalPopAndExecCC(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*      */       int i;
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/* 1040 */       if ((this.base - (i = this.top) < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/* 1041 */         long l = ((arrayOfForkJoinTask.length - 1 & i - 1) << ASHIFT) + ABASE;
/* 1042 */         Object localObject; if (((localObject = U.getObject(arrayOfForkJoinTask, l)) instanceof CountedCompleter)) {
/* 1043 */           CountedCompleter localCountedCompleter1 = (CountedCompleter)localObject;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/* 1044 */           for (;;) { if (localCountedCompleter2 == paramCountedCompleter) {
/* 1045 */               if (U.compareAndSwapObject(arrayOfForkJoinTask, l, localCountedCompleter1, null)) {
/* 1046 */                 this.top = (i - 1);
/* 1047 */                 localCountedCompleter1.doExec();
/*      */               }
/* 1049 */               return true;
/*      */             }
/* 1051 */             if ((localCountedCompleter2 = localCountedCompleter2.completer) == null)
/*      */               break;
/*      */           }
/*      */         }
/*      */       }
/* 1056 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean isApparentlyUnblocked()
/*      */     {
/*      */       ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */       
/* 1064 */       if ((this.eventCount >= 0) && ((localForkJoinWorkerThread = this.owner) != null)) {}
/*      */       Thread.State localState;
/* 1066 */       return ((localState = localForkJoinWorkerThread.getState()) != Thread.State.BLOCKED) && (localState != Thread.State.WAITING) && (localState != Thread.State.TIMED_WAITING);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     static
/*      */     {
/*      */       try
/*      */       {
/* 1079 */         U = Unsafe.getUnsafe();
/* 1080 */         Class localClass1 = WorkQueue.class;
/* 1081 */         Class localClass2 = ForkJoinTask[].class;
/*      */         
/* 1083 */         QBASE = U.objectFieldOffset(localClass1.getDeclaredField("base"));
/*      */         
/* 1085 */         QLOCK = U.objectFieldOffset(localClass1.getDeclaredField("qlock"));
/* 1086 */         ABASE = U.arrayBaseOffset(localClass2);
/* 1087 */         int i = U.arrayIndexScale(localClass2);
/* 1088 */         if ((i & i - 1) != 0)
/* 1089 */           throw new Error("data type scale not a power of two");
/* 1090 */         ASHIFT = 31 - Integer.numberOfLeadingZeros(i);
/*      */       } catch (Exception localException) {
/* 1092 */         throw new Error(localException);
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
/*      */   private static final synchronized int nextPoolId()
/*      */   {
/* 1138 */     return ++poolNumberSequence;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int acquirePlock()
/*      */   {
/* 1284 */     int i = 256;
/*      */     for (;;) { int j;
/* 1286 */       int k; if ((((j = this.plock) & 0x2) == 0) && 
/* 1287 */         (U.compareAndSwapInt(this, PLOCK, j, k = j + 2)))
/* 1288 */         return k;
/* 1289 */       if (i >= 0) {
/* 1290 */         if (ThreadLocalRandom.nextSecondarySeed() >= 0) {
/* 1291 */           i--;
/*      */         }
/* 1293 */       } else if (U.compareAndSwapInt(this, PLOCK, j, j | 0x1)) {
/* 1294 */         synchronized (this) {
/* 1295 */           if ((this.plock & 0x1) != 0) {
/*      */             try {
/* 1297 */               wait();
/*      */             } catch (InterruptedException localInterruptedException) {
/*      */               try {
/* 1300 */                 Thread.currentThread().interrupt();
/*      */ 
/*      */               }
/*      */               catch (SecurityException localSecurityException) {}
/*      */             }
/*      */           } else {
/* 1306 */             notifyAll();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void releasePlock(int paramInt)
/*      */   {
/* 1317 */     this.plock = paramInt;
/* 1318 */     synchronized (this) { notifyAll();
/*      */     }
/*      */   }
/*      */   
/*      */   private void tryAddWorker()
/*      */   {
/*      */     long l1;
/*      */     int i;
/*      */     int j;
/* 1327 */     while (((i = (int)((l1 = this.ctl) >>> 32)) < 0) && ((i & 0x8000) != 0) && ((j = (int)l1) >= 0))
/*      */     {
/* 1329 */       long l2 = (i + 1 & 0xFFFF | i + 65536 & 0xFFFF0000) << 32 | j;
/*      */       
/* 1331 */       if (U.compareAndSwapLong(this, CTL, l1, l2))
/*      */       {
/* 1333 */         Object localObject = null;
/* 1334 */         ForkJoinWorkerThread localForkJoinWorkerThread = null;
/*      */         try { ForkJoinWorkerThreadFactory localForkJoinWorkerThreadFactory;
/* 1336 */           if (((localForkJoinWorkerThreadFactory = this.factory) != null) && 
/* 1337 */             ((localForkJoinWorkerThread = localForkJoinWorkerThreadFactory.newThread(this)) != null)) {
/* 1338 */             localForkJoinWorkerThread.start();
/* 1339 */             break;
/*      */           }
/*      */         } catch (Throwable localThrowable) {
/* 1342 */           localObject = localThrowable;
/*      */         }
/* 1344 */         deregisterWorker(localForkJoinWorkerThread, (Throwable)localObject);
/* 1345 */         break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   final WorkQueue registerWorker(ForkJoinWorkerThread paramForkJoinWorkerThread)
/*      */   {
/* 1364 */     paramForkJoinWorkerThread.setDaemon(true);
/* 1365 */     Thread.UncaughtExceptionHandler localUncaughtExceptionHandler; if ((localUncaughtExceptionHandler = this.ueh) != null)
/* 1366 */       paramForkJoinWorkerThread.setUncaughtExceptionHandler(localUncaughtExceptionHandler);
/* 1367 */     int i; do { i -= 1640531527; } while ((!U.compareAndSwapInt(this, INDEXSEED, i = this.indexSeed, i)) || (i == 0));
/*      */     
/*      */ 
/* 1370 */     WorkQueue localWorkQueue = new WorkQueue(this, paramForkJoinWorkerThread, this.mode, i);
/* 1371 */     int j; if ((((j = this.plock) & 0x2) != 0) || 
/* 1372 */       (!U.compareAndSwapInt(this, PLOCK, , j)))
/* 1373 */       j = acquirePlock();
/* 1374 */     int k = j & 0x80000000 | j + 2 & 0x7FFFFFFF;
/*      */     try { WorkQueue[] arrayOfWorkQueue;
/* 1376 */       if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 1377 */         int m = arrayOfWorkQueue.length;int n = m - 1;
/* 1378 */         int i1 = i << 1 | 0x1;
/* 1379 */         if (arrayOfWorkQueue[(i1 &= n)] != null) {
/* 1380 */           int i2 = 0;
/* 1381 */           int i3 = m <= 4 ? 2 : (m >>> 1 & 0xFFFE) + 2;
/* 1382 */           while (arrayOfWorkQueue[(i1 = i1 + i3 & n)] != null) {
/* 1383 */             i2++; if (i2 >= m) {
/* 1384 */               this.workQueues = (arrayOfWorkQueue = (WorkQueue[])Arrays.copyOf(arrayOfWorkQueue, m <<= 1));
/* 1385 */               n = m - 1;
/* 1386 */               i2 = 0;
/*      */             }
/*      */           }
/*      */         }
/* 1390 */         localWorkQueue.poolIndex = ((short)i1);
/* 1391 */         localWorkQueue.eventCount = i1;
/* 1392 */         arrayOfWorkQueue[i1] = localWorkQueue;
/*      */       }
/*      */     } finally {
/* 1395 */       if (!U.compareAndSwapInt(this, PLOCK, j, k))
/* 1396 */         releasePlock(k);
/*      */     }
/* 1398 */     paramForkJoinWorkerThread.setName(this.workerNamePrefix.concat(Integer.toString(localWorkQueue.poolIndex >>> 1)));
/* 1399 */     return localWorkQueue;
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
/*      */   final void deregisterWorker(ForkJoinWorkerThread paramForkJoinWorkerThread, Throwable paramThrowable)
/*      */   {
/* 1412 */     WorkQueue localWorkQueue = null;
/* 1413 */     Object localObject1; if ((paramForkJoinWorkerThread != null) && ((localWorkQueue = paramForkJoinWorkerThread.workQueue) != null))
/*      */     {
/* 1415 */       localWorkQueue.qlock = -1;
/* 1416 */       U.getAndAddLong(this, STEALCOUNT, localWorkQueue.nsteals);
/* 1417 */       int i; if ((((i = this.plock) & 0x2) != 0) || 
/* 1418 */         (!U.compareAndSwapInt(this, PLOCK, , i)))
/* 1419 */         i = acquirePlock();
/* 1420 */       int j = i & 0x80000000 | i + 2 & 0x7FFFFFFF;
/*      */       try {
/* 1422 */         int k = localWorkQueue.poolIndex;
/* 1423 */         localObject1 = this.workQueues;
/* 1424 */         if ((localObject1 != null) && (k >= 0) && (k < localObject1.length) && (localObject1[k] == localWorkQueue))
/* 1425 */           localObject1[k] = null;
/*      */       } finally {
/* 1427 */         if (!U.compareAndSwapInt(this, PLOCK, i, j)) {
/* 1428 */           releasePlock(j);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     long l1;
/* 1434 */     while (!U.compareAndSwapLong(this, CTL, l1 = this.ctl, l1 - 281474976710656L & 0xFFFF000000000000 | l1 - 4294967296L & 0xFFFF00000000 | l1 & 0xFFFFFFFF)) {}
/*      */     
/*      */ 
/*      */ 
/* 1438 */     if ((!tryTerminate(false, false)) && (localWorkQueue != null) && (localWorkQueue.array != null)) {
/* 1439 */       localWorkQueue.cancelAll();
/*      */       int m;
/* 1441 */       int i1; while (((m = (int)((l1 = this.ctl) >>> 32)) < 0) && ((i1 = (int)l1) >= 0)) {
/* 1442 */         if (i1 > 0) { WorkQueue[] arrayOfWorkQueue;
/* 1443 */           int n; if (((arrayOfWorkQueue = this.workQueues) != null) && ((n = i1 & 0xFFFF) < arrayOfWorkQueue.length) && ((localObject1 = arrayOfWorkQueue[n]) != null))
/*      */           {
/*      */ 
/*      */ 
/* 1447 */             long l2 = ((WorkQueue)localObject1).nextWait & 0x7FFFFFFF | m + 65536 << 32;
/*      */             
/* 1449 */             if (((WorkQueue)localObject1).eventCount == (i1 | 0x80000000))
/*      */             {
/* 1451 */               if (U.compareAndSwapLong(this, CTL, l1, l2)) {
/* 1452 */                 ((WorkQueue)localObject1).eventCount = (i1 + 65536 & 0x7FFFFFFF);
/* 1453 */                 Thread localThread; if ((localThread = ((WorkQueue)localObject1).parker) == null) break;
/* 1454 */                 U.unpark(localThread);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1459 */         else if ((short)m < 0) {
/* 1460 */           tryAddWorker();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1465 */     if (paramThrowable == null) {
/* 1466 */       ForkJoinTask.helpExpungeStaleExceptions();
/*      */     } else {
/* 1468 */       ForkJoinTask.rethrow(paramThrowable);
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
/*      */   final void externalPush(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 1483 */     int n = ThreadLocalRandom.getProbe();
/* 1484 */     int i1 = this.plock;
/* 1485 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 1486 */     int i; WorkQueue localWorkQueue; if ((i1 > 0) && (arrayOfWorkQueue != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && ((localWorkQueue = arrayOfWorkQueue[(i & n & 0x7E)]) != null) && (n != 0))
/*      */     {
/* 1488 */       if (U.compareAndSwapInt(localWorkQueue, QLOCK, 0, 1)) { ForkJoinTask[] arrayOfForkJoinTask;
/* 1489 */         int m; int j; int k; if (((arrayOfForkJoinTask = localWorkQueue.array) != null) && ((m = arrayOfForkJoinTask.length - 1) > (k = (j = localWorkQueue.top) - localWorkQueue.base)))
/*      */         {
/* 1491 */           int i2 = ((m & j) << ASHIFT) + ABASE;
/* 1492 */           U.putOrderedObject(arrayOfForkJoinTask, i2, paramForkJoinTask);
/* 1493 */           localWorkQueue.top = (j + 1);
/* 1494 */           localWorkQueue.qlock = 0;
/* 1495 */           if (k <= 1)
/* 1496 */             signalWork(arrayOfWorkQueue, localWorkQueue);
/* 1497 */           return;
/*      */         }
/* 1499 */         localWorkQueue.qlock = 0;
/*      */       } }
/* 1501 */     fullExternalPush(paramForkJoinTask);
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
/*      */   private void fullExternalPush(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1523 */     if ((i = ThreadLocalRandom.getProbe()) == 0) {
/* 1524 */       ThreadLocalRandom.localInit();
/* 1525 */       i = ThreadLocalRandom.getProbe();
/*      */     }
/*      */     for (;;)
/*      */     {
/* 1529 */       int n = 0;
/* 1530 */       int j; if ((j = this.plock) < 0)
/* 1531 */         throw new RejectedExecutionException();
/* 1532 */       WorkQueue[] arrayOfWorkQueue1; int k; int i3; int i5; if ((j == 0) || ((arrayOfWorkQueue1 = this.workQueues) == null) || ((k = arrayOfWorkQueue1.length - 1) < 0))
/*      */       {
/* 1534 */         int i1 = this.parallelism;
/* 1535 */         i3 = i1 > 1 ? i1 - 1 : 1;
/* 1536 */         i3 |= i3 >>> 1;i3 |= i3 >>> 2;i3 |= i3 >>> 4;
/* 1537 */         i3 |= i3 >>> 8;i3 |= i3 >>> 16;i3 = i3 + 1 << 1;
/* 1538 */         WorkQueue[] arrayOfWorkQueue2 = ((arrayOfWorkQueue1 = this.workQueues) == null) || (arrayOfWorkQueue1.length == 0) ? new WorkQueue[i3] : null;
/*      */         
/* 1540 */         if ((((j = this.plock) & 0x2) != 0) || 
/* 1541 */           (!U.compareAndSwapInt(this, PLOCK, , j)))
/* 1542 */           j = acquirePlock();
/* 1543 */         if ((((arrayOfWorkQueue1 = this.workQueues) == null) || (arrayOfWorkQueue1.length == 0)) && (arrayOfWorkQueue2 != null))
/* 1544 */           this.workQueues = arrayOfWorkQueue2;
/* 1545 */         i5 = j & 0x80000000 | j + 2 & 0x7FFFFFFF;
/* 1546 */         if (!U.compareAndSwapInt(this, PLOCK, j, i5))
/* 1547 */           releasePlock(i5); } else { int m;
/*      */         WorkQueue localWorkQueue;
/* 1549 */         if ((localWorkQueue = arrayOfWorkQueue1[(m = i & k & 0x7E)]) != null) {
/* 1550 */           if ((localWorkQueue.qlock == 0) && (U.compareAndSwapInt(localWorkQueue, QLOCK, 0, 1))) {
/* 1551 */             ForkJoinTask[] arrayOfForkJoinTask = localWorkQueue.array;
/* 1552 */             i3 = localWorkQueue.top;
/* 1553 */             int i4 = 0;
/*      */             try {
/* 1555 */               if (((arrayOfForkJoinTask != null) && (arrayOfForkJoinTask.length > i3 + 1 - localWorkQueue.base)) || 
/* 1556 */                 ((arrayOfForkJoinTask = localWorkQueue.growArray()) != null)) {
/* 1557 */                 i5 = ((arrayOfForkJoinTask.length - 1 & i3) << ASHIFT) + ABASE;
/* 1558 */                 U.putOrderedObject(arrayOfForkJoinTask, i5, paramForkJoinTask);
/* 1559 */                 localWorkQueue.top = (i3 + 1);
/* 1560 */                 i4 = 1;
/*      */               }
/*      */             } finally {
/* 1563 */               localWorkQueue.qlock = 0;
/*      */             }
/* 1565 */             if (i4 != 0) {
/* 1566 */               signalWork(arrayOfWorkQueue1, localWorkQueue);
/* 1567 */               return;
/*      */             }
/*      */           }
/* 1570 */           n = 1;
/*      */         }
/* 1572 */         else if (((j = this.plock) & 0x2) == 0) {
/* 1573 */           localWorkQueue = new WorkQueue(this, null, -1, i);
/* 1574 */           localWorkQueue.poolIndex = ((short)m);
/* 1575 */           if ((((j = this.plock) & 0x2) != 0) || 
/* 1576 */             (!U.compareAndSwapInt(this, PLOCK, , j)))
/* 1577 */             j = acquirePlock();
/* 1578 */           if (((arrayOfWorkQueue1 = this.workQueues) != null) && (m < arrayOfWorkQueue1.length) && (arrayOfWorkQueue1[m] == null))
/* 1579 */             arrayOfWorkQueue1[m] = localWorkQueue;
/* 1580 */           int i2 = j & 0x80000000 | j + 2 & 0x7FFFFFFF;
/* 1581 */           if (!U.compareAndSwapInt(this, PLOCK, j, i2)) {
/* 1582 */             releasePlock(i2);
/*      */           }
/*      */         } else {
/* 1585 */           n = 1; } }
/* 1586 */       if (n != 0) {
/* 1587 */         i = ThreadLocalRandom.advanceProbe(i);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void incrementActiveCount()
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/* 1599 */     while (!U.compareAndSwapLong(this, CTL, l = this.ctl, l & 0xFFFFFFFFFFFF | (l & 0xFFFF000000000000) + 281474976710656L)) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final void signalWork(WorkQueue[] paramArrayOfWorkQueue, WorkQueue paramWorkQueue)
/*      */   {
/*      */     long l1;
/*      */     
/*      */ 
/*      */     int j;
/*      */     
/*      */ 
/* 1612 */     while ((j = (int)((l1 = this.ctl) >>> 32)) < 0) {
/*      */       int i;
/* 1614 */       if ((i = (int)l1) <= 0) {
/* 1615 */         if ((short)j < 0)
/* 1616 */           tryAddWorker();
/*      */       } else { int k;
/*      */         WorkQueue localWorkQueue;
/* 1619 */         if ((paramArrayOfWorkQueue != null) && (paramArrayOfWorkQueue.length > (k = i & 0xFFFF)) && ((localWorkQueue = paramArrayOfWorkQueue[k]) != null))
/*      */         {
/*      */ 
/* 1622 */           long l2 = localWorkQueue.nextWait & 0x7FFFFFFF | j + 65536 << 32;
/*      */           
/* 1624 */           int m = i + 65536 & 0x7FFFFFFF;
/* 1625 */           if ((localWorkQueue.eventCount == (i | 0x80000000)) && 
/* 1626 */             (U.compareAndSwapLong(this, CTL, l1, l2))) {
/* 1627 */             localWorkQueue.eventCount = m;
/* 1628 */             Thread localThread; if ((localThread = localWorkQueue.parker) != null) {
/* 1629 */               U.unpark(localThread);
/*      */             }
/*      */           } else {
/* 1632 */             if ((paramWorkQueue != null) && (paramWorkQueue.base >= paramWorkQueue.top)) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   final void runWorker(WorkQueue paramWorkQueue)
/*      */   {
/* 1643 */     paramWorkQueue.growArray();
/* 1644 */     for (int i = paramWorkQueue.hint; scan(paramWorkQueue, i) == 0; 
/* 1645 */         i ^= i << 5) { i ^= i << 13;i ^= i >>> 17;
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
/*      */   private final int scan(WorkQueue paramWorkQueue, int paramInt)
/*      */   {
/* 1673 */     long l1 = this.ctl;
/* 1674 */     WorkQueue[] arrayOfWorkQueue; int i; if (((arrayOfWorkQueue = this.workQueues) != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && (paramWorkQueue != null)) {
/* 1675 */       int j = i + i + 1;int k = paramWorkQueue.eventCount;
/*      */       for (;;) { WorkQueue localWorkQueue;
/* 1677 */         int m; ForkJoinTask[] arrayOfForkJoinTask; long l2; if (((localWorkQueue = arrayOfWorkQueue[(paramInt - j & i)]) != null) && ((m = localWorkQueue.base) - localWorkQueue.top < 0) && ((arrayOfForkJoinTask = localWorkQueue.array) != null))
/*      */         {
/* 1679 */           l2 = ((arrayOfForkJoinTask.length - 1 & m) << ASHIFT) + ABASE;
/*      */           ForkJoinTask localForkJoinTask;
/* 1681 */           if ((localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, l2)) == null) break;
/* 1682 */           if (k < 0) {
/* 1683 */             helpRelease(l1, arrayOfWorkQueue, paramWorkQueue, localWorkQueue, m); break; }
/* 1684 */           if ((localWorkQueue.base != m) || 
/* 1685 */             (!U.compareAndSwapObject(arrayOfForkJoinTask, l2, localForkJoinTask, null))) break;
/* 1686 */           U.putOrderedInt(localWorkQueue, QBASE, m + 1);
/* 1687 */           if (m + 1 - localWorkQueue.top < 0)
/* 1688 */             signalWork(arrayOfWorkQueue, localWorkQueue);
/* 1689 */           paramWorkQueue.runTask(localForkJoinTask); break;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1694 */         j--; if (j < 0) { int n;
/* 1695 */           if ((k | (n = (int)l1)) < 0)
/* 1696 */             return awaitWork(paramWorkQueue, l1, k);
/* 1697 */           if (this.ctl != l1) break;
/* 1698 */           l2 = k | l1 - 281474976710656L & 0xFFFFFFFF00000000;
/* 1699 */           paramWorkQueue.nextWait = n;
/* 1700 */           paramWorkQueue.eventCount = (k | 0x80000000);
/* 1701 */           if (!U.compareAndSwapLong(this, CTL, l1, l2))
/* 1702 */             paramWorkQueue.eventCount = k;
/* 1703 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1708 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int awaitWork(WorkQueue paramWorkQueue, long paramLong, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1727 */     if (((i = paramWorkQueue.qlock) >= 0) && (paramWorkQueue.eventCount == paramInt) && (this.ctl == paramLong) && 
/* 1728 */       (!Thread.interrupted())) {
/* 1729 */       int k = (int)paramLong;
/* 1730 */       int m = (int)(paramLong >>> 32);
/* 1731 */       int n = (m >> 16) + this.parallelism;
/*      */       
/* 1733 */       if ((k < 0) || ((n <= 0) && (tryTerminate(false, false)))) {
/* 1734 */         i = paramWorkQueue.qlock = -1; } else { int j;
/* 1735 */         if ((j = paramWorkQueue.nsteals) != 0) {
/* 1736 */           paramWorkQueue.nsteals = 0;
/* 1737 */           U.getAndAddLong(this, STEALCOUNT, j);
/*      */         }
/*      */         else {
/* 1740 */           long l3 = (n > 0) || (paramInt != (k | 0x80000000)) ? 0L : paramWorkQueue.nextWait & 0x7FFFFFFF | m + 65536 << 32;
/*      */           long l1;
/*      */           long l2;
/* 1743 */           if (l3 != 0L) {
/* 1744 */             int i1 = -(short)(int)(paramLong >>> 32);
/* 1745 */             l1 = i1 < 0 ? 200000000L : (i1 + 1) * 2000000000L;
/*      */             
/* 1747 */             l2 = System.nanoTime() + l1 - 2000000L;
/*      */           }
/*      */           else {
/* 1750 */             l1 = l2 = 0L; }
/* 1751 */           if ((paramWorkQueue.eventCount == paramInt) && (this.ctl == paramLong)) {
/* 1752 */             Thread localThread = Thread.currentThread();
/* 1753 */             U.putObject(localThread, PARKBLOCKER, this);
/* 1754 */             paramWorkQueue.parker = localThread;
/* 1755 */             if ((paramWorkQueue.eventCount == paramInt) && (this.ctl == paramLong))
/* 1756 */               U.park(false, l1);
/* 1757 */             paramWorkQueue.parker = null;
/* 1758 */             U.putObject(localThread, PARKBLOCKER, null);
/* 1759 */             if ((l1 != 0L) && (this.ctl == paramLong) && 
/* 1760 */               (l2 - System.nanoTime() <= 0L) && 
/* 1761 */               (U.compareAndSwapLong(this, CTL, paramLong, l3)))
/* 1762 */               i = paramWorkQueue.qlock = -1;
/*      */           }
/*      */         }
/*      */       } }
/* 1766 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   private final void helpRelease(long paramLong, WorkQueue[] paramArrayOfWorkQueue, WorkQueue paramWorkQueue1, WorkQueue paramWorkQueue2, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */     int j;
/*      */     
/*      */     WorkQueue localWorkQueue;
/*      */     
/* 1778 */     if ((paramWorkQueue1 != null) && (paramWorkQueue1.eventCount < 0) && ((i = (int)paramLong) > 0) && (paramArrayOfWorkQueue != null) && (paramArrayOfWorkQueue.length > (j = i & 0xFFFF)) && ((localWorkQueue = paramArrayOfWorkQueue[j]) != null) && (this.ctl == paramLong))
/*      */     {
/*      */ 
/* 1781 */       long l = localWorkQueue.nextWait & 0x7FFFFFFF | (int)(paramLong >>> 32) + 65536 << 32;
/*      */       
/* 1783 */       int k = i + 65536 & 0x7FFFFFFF;
/* 1784 */       if ((paramWorkQueue2 != null) && (paramWorkQueue2.base == paramInt) && (paramWorkQueue1.eventCount < 0) && (localWorkQueue.eventCount == (i | 0x80000000)))
/*      */       {
/* 1786 */         if (U.compareAndSwapLong(this, CTL, paramLong, l)) {
/* 1787 */           localWorkQueue.eventCount = k;
/* 1788 */           Thread localThread; if ((localThread = localWorkQueue.parker) != null) {
/* 1789 */             U.unpark(localThread);
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int tryHelpStealer(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 1813 */     int i = 0;int j = 0;
/* 1814 */     if ((paramForkJoinTask != null) && (paramWorkQueue != null) && (paramWorkQueue.base - paramWorkQueue.top >= 0)) {
/*      */       break label107;
/*      */       label25:
/* 1817 */       Object localObject1 = paramForkJoinTask;
/* 1818 */       Object localObject2 = paramWorkQueue;
/*      */       label107:
/* 1820 */       label471: label472: label474: for (;;) { int m; if ((m = paramForkJoinTask.status) < 0) {
/* 1821 */           i = m;
/* 1822 */           return i; }
/*      */         WorkQueue[] arrayOfWorkQueue;
/* 1824 */         int k; if (((arrayOfWorkQueue = this.workQueues) == null) || ((k = arrayOfWorkQueue.length - 1) <= 0)) return i;
/*      */         int n;
/* 1826 */         WorkQueue localWorkQueue; if (((localWorkQueue = arrayOfWorkQueue[(n = (((WorkQueue)localObject2).hint | 0x1) & k)]) == null) || (localWorkQueue.currentSteal != localObject1))
/*      */         {
/* 1828 */           int i1 = n;
/* 1829 */           if ((((n = n + 2 & k) & 0xF) == 1) && ((((ForkJoinTask)localObject1).status < 0) || (((WorkQueue)localObject2).currentJoin != localObject1))) {
/*      */             break label25;
/*      */           }
/* 1832 */           if (((localWorkQueue = arrayOfWorkQueue[n]) != null) && (localWorkQueue.currentSteal == localObject1))
/*      */           {
/* 1834 */             ((WorkQueue)localObject2).hint = n;
/*      */           }
/*      */           else {
/* 1837 */             if (n != i1) break;
/* 1838 */             return i;
/*      */           }
/*      */         }
/*      */         for (;;)
/*      */         {
/* 1843 */           if (((ForkJoinTask)localObject1).status < 0) break label472;
/*      */           int i2;
/* 1845 */           ForkJoinTask[] arrayOfForkJoinTask; if (((i2 = localWorkQueue.base) - localWorkQueue.top < 0) && ((arrayOfForkJoinTask = localWorkQueue.array) != null)) {
/* 1846 */             int i3 = ((arrayOfForkJoinTask.length - 1 & i2) << ASHIFT) + ABASE;
/*      */             
/* 1848 */             ForkJoinTask localForkJoinTask2 = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, i3);
/* 1849 */             if ((((ForkJoinTask)localObject1).status < 0) || (((WorkQueue)localObject2).currentJoin != localObject1) || (localWorkQueue.currentSteal != localObject1)) {
/*      */               break;
/*      */             }
/* 1852 */             i = 1;
/* 1853 */             if (localWorkQueue.base == i2) {
/* 1854 */               if (localForkJoinTask2 == null)
/*      */                 return i;
/* 1856 */               if (U.compareAndSwapObject(arrayOfForkJoinTask, i3, localForkJoinTask2, null)) {
/* 1857 */                 U.putOrderedInt(localWorkQueue, QBASE, i2 + 1);
/* 1858 */                 ForkJoinTask localForkJoinTask3 = paramWorkQueue.currentSteal;
/* 1859 */                 int i4 = paramWorkQueue.top;
/*      */                 do {
/* 1861 */                   paramWorkQueue.currentSteal = localForkJoinTask2;
/* 1862 */                   localForkJoinTask2.doExec();
/* 1863 */                 } while ((paramForkJoinTask.status >= 0) && (paramWorkQueue.top != i4) && 
/*      */                 
/* 1865 */                   ((localForkJoinTask2 = paramWorkQueue.pop()) != null));
/* 1866 */                 paramWorkQueue.currentSteal = localForkJoinTask3;
/* 1867 */                 return i;
/*      */               }
/*      */             }
/*      */             break label471;
/*      */           }
/* 1872 */           ForkJoinTask localForkJoinTask1 = localWorkQueue.currentJoin;
/* 1873 */           if ((((ForkJoinTask)localObject1).status < 0) || (((WorkQueue)localObject2).currentJoin != localObject1) || (localWorkQueue.currentSteal != localObject1)) {
/*      */             break;
/*      */           }
/* 1876 */           if (localForkJoinTask1 == null) return i; j++; if (j == 64) {
/*      */             return i;
/*      */           }
/* 1879 */           localObject1 = localForkJoinTask1;
/* 1880 */           localObject2 = localWorkQueue;
/*      */           
/*      */           break label474;
/*      */         }
/*      */         
/*      */         break label25;
/*      */       }
/*      */     }
/* 1888 */     return i;
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
/*      */   final int helpComplete(WorkQueue paramWorkQueue, CountedCompleter<?> paramCountedCompleter, int paramInt)
/*      */   {
/* 1901 */     int j = 0;
/* 1902 */     WorkQueue[] arrayOfWorkQueue; int i; if (((arrayOfWorkQueue = this.workQueues) != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && (paramWorkQueue != null) && (paramCountedCompleter != null))
/*      */     {
/* 1904 */       int k = paramWorkQueue.poolIndex;
/* 1905 */       int m = i + i + 1;
/* 1906 */       long l = 0L;
/* 1907 */       int n = m;
/* 1909 */       for (; 
/* 1909 */           (j = paramCountedCompleter.status) >= 0; k += 2)
/*      */       {
/*      */ 
/*      */ 
/* 1911 */         if (paramWorkQueue.internalPopAndExecCC(paramCountedCompleter)) {
/* 1912 */           paramInt--; if (paramInt <= 0) {
/* 1913 */             j = paramCountedCompleter.status;
/* 1914 */             break;
/*      */           }
/* 1916 */           n = m;
/*      */         } else {
/* 1918 */           if ((j = paramCountedCompleter.status) < 0) break;
/*      */           WorkQueue localWorkQueue;
/* 1920 */           if (((localWorkQueue = arrayOfWorkQueue[(k & i)]) != null) && (localWorkQueue.pollAndExecCC(paramCountedCompleter))) {
/* 1921 */             paramInt--; if (paramInt <= 0) {
/* 1922 */               j = paramCountedCompleter.status;
/* 1923 */               break;
/*      */             }
/* 1925 */             n = m;
/*      */           } else {
/* 1927 */             n--; if (n < 0) {
/* 1928 */               if (l == (l = this.ctl))
/*      */                 break;
/* 1930 */               n = m;
/*      */             }
/*      */           }
/*      */         } } }
/* 1934 */     return j;
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
/*      */   final boolean tryCompensate(long paramLong)
/*      */   {
/* 1947 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 1948 */     int i = this.parallelism;int j = (int)paramLong;
/* 1949 */     int k; if ((arrayOfWorkQueue != null) && ((k = arrayOfWorkQueue.length - 1) >= 0) && (j >= 0) && (this.ctl == paramLong)) {
/* 1950 */       WorkQueue localWorkQueue = arrayOfWorkQueue[(j & k)];
/* 1951 */       if ((j != 0) && (localWorkQueue != null))
/*      */       {
/* 1953 */         long l2 = localWorkQueue.nextWait & 0x7FFFFFFF | paramLong & 0xFFFFFFFF00000000;
/*      */         
/* 1955 */         int n = j + 65536 & 0x7FFFFFFF;
/* 1956 */         if ((localWorkQueue.eventCount == (j | 0x80000000)) && 
/* 1957 */           (U.compareAndSwapLong(this, CTL, paramLong, l2))) {
/* 1958 */           localWorkQueue.eventCount = n;
/* 1959 */           Thread localThread; if ((localThread = localWorkQueue.parker) != null)
/* 1960 */             U.unpark(localThread);
/* 1961 */           return true;
/*      */         } } else { int m;
/*      */         long l1;
/* 1964 */         if (((m = (short)(int)(paramLong >>> 32)) >= 0) && ((int)(paramLong >> 48) + i > 1))
/*      */         {
/* 1966 */           l1 = paramLong - 281474976710656L & 0xFFFF000000000000 | paramLong & 0xFFFFFFFFFFFF;
/* 1967 */           if (U.compareAndSwapLong(this, CTL, paramLong, l1)) {
/* 1968 */             return true;
/*      */           }
/* 1970 */         } else if (m + i < 32767) {
/* 1971 */           l1 = paramLong + 4294967296L & 0xFFFF00000000 | paramLong & 0xFFFF0000FFFFFFFF;
/* 1972 */           if (U.compareAndSwapLong(this, CTL, paramLong, l1))
/*      */           {
/* 1974 */             Object localObject = null;
/* 1975 */             ForkJoinWorkerThread localForkJoinWorkerThread = null;
/*      */             try { ForkJoinWorkerThreadFactory localForkJoinWorkerThreadFactory;
/* 1977 */               if (((localForkJoinWorkerThreadFactory = this.factory) != null) && 
/* 1978 */                 ((localForkJoinWorkerThread = localForkJoinWorkerThreadFactory.newThread(this)) != null)) {
/* 1979 */                 localForkJoinWorkerThread.start();
/* 1980 */                 return true;
/*      */               }
/*      */             } catch (Throwable localThrowable) {
/* 1983 */               localObject = localThrowable;
/*      */             }
/* 1985 */             deregisterWorker(localForkJoinWorkerThread, (Throwable)localObject);
/*      */           }
/*      */         }
/*      */       } }
/* 1989 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int awaitJoin(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 2000 */     int i = 0;
/* 2001 */     if ((paramForkJoinTask != null) && ((i = paramForkJoinTask.status) >= 0) && (paramWorkQueue != null)) {
/* 2002 */       ForkJoinTask localForkJoinTask = paramWorkQueue.currentJoin;
/* 2003 */       paramWorkQueue.currentJoin = paramForkJoinTask;
/* 2004 */       while ((paramWorkQueue.tryRemoveAndExec(paramForkJoinTask)) && ((i = paramForkJoinTask.status) >= 0)) {}
/*      */       
/* 2006 */       if ((i >= 0) && ((paramForkJoinTask instanceof CountedCompleter)))
/* 2007 */         i = helpComplete(paramWorkQueue, (CountedCompleter)paramForkJoinTask, Integer.MAX_VALUE);
/* 2008 */       long l1 = 0L;
/* 2009 */       while ((i >= 0) && ((i = paramForkJoinTask.status) >= 0)) {
/* 2010 */         if (((i = tryHelpStealer(paramWorkQueue, paramForkJoinTask)) == 0) && ((i = paramForkJoinTask.status) >= 0))
/*      */         {
/* 2012 */           if (!tryCompensate(l1)) {
/* 2013 */             l1 = this.ctl;
/*      */           } else {
/* 2015 */             if ((paramForkJoinTask.trySetSignal()) && ((i = paramForkJoinTask.status) >= 0)) {
/* 2016 */               synchronized (paramForkJoinTask) {
/* 2017 */                 if (paramForkJoinTask.status >= 0) {
/*      */                   try {
/* 2019 */                     paramForkJoinTask.wait();
/*      */ 
/*      */                   }
/*      */                   catch (InterruptedException localInterruptedException) {}
/*      */                 } else {
/* 2024 */                   paramForkJoinTask.notifyAll();
/*      */                 }
/*      */               }
/*      */             }
/*      */             long l2;
/* 2029 */             while (!U.compareAndSwapLong(this, CTL, l2 = this.ctl, l2 & 0xFFFFFFFFFFFF | (l2 & 0xFFFF000000000000) + 281474976710656L)) {}
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2035 */       paramWorkQueue.currentJoin = localForkJoinTask;
/*      */     }
/* 2037 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void helpJoinOnce(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/* 2050 */     if ((paramWorkQueue != null) && (paramForkJoinTask != null) && ((i = paramForkJoinTask.status) >= 0)) {
/* 2051 */       ForkJoinTask localForkJoinTask = paramWorkQueue.currentJoin;
/* 2052 */       paramWorkQueue.currentJoin = paramForkJoinTask;
/* 2053 */       while ((paramWorkQueue.tryRemoveAndExec(paramForkJoinTask)) && ((i = paramForkJoinTask.status) >= 0)) {}
/*      */       
/* 2055 */       if (i >= 0) {
/* 2056 */         if ((paramForkJoinTask instanceof CountedCompleter))
/* 2057 */           helpComplete(paramWorkQueue, (CountedCompleter)paramForkJoinTask, Integer.MAX_VALUE);
/* 2058 */         while ((paramForkJoinTask.status >= 0) && 
/* 2059 */           (tryHelpStealer(paramWorkQueue, paramForkJoinTask) > 0)) {}
/*      */       }
/* 2061 */       paramWorkQueue.currentJoin = localForkJoinTask;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private WorkQueue findNonEmptyStealQueue()
/*      */   {
/* 2071 */     int i = ThreadLocalRandom.nextSecondarySeed();
/*      */     for (;;) {
/* 2073 */       int j = this.plock;
/* 2074 */       WorkQueue[] arrayOfWorkQueue; int k; if (((arrayOfWorkQueue = this.workQueues) != null) && ((k = arrayOfWorkQueue.length - 1) >= 0)) {
/* 2075 */         for (int m = k + 1 << 2; m >= 0; m--) { WorkQueue localWorkQueue;
/* 2076 */           if (((localWorkQueue = arrayOfWorkQueue[((i - m << 1 | 0x1) & k)]) != null) && (localWorkQueue.base - localWorkQueue.top < 0))
/*      */           {
/* 2078 */             return localWorkQueue; }
/*      */         }
/*      */       }
/* 2081 */       if (this.plock == j) {
/* 2082 */         return null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void helpQuiescePool(WorkQueue paramWorkQueue)
/*      */   {
/* 2093 */     ForkJoinTask localForkJoinTask1 = paramWorkQueue.currentSteal;
/* 2094 */     int i = 1;
/*      */     for (;;) { ForkJoinTask localForkJoinTask2;
/* 2096 */       if ((localForkJoinTask2 = paramWorkQueue.nextLocalTask()) != null) {
/* 2097 */         localForkJoinTask2.doExec(); } else { WorkQueue localWorkQueue;
/* 2098 */         long l1; if ((localWorkQueue = findNonEmptyStealQueue()) != null) {
/* 2099 */           if (i == 0) {
/* 2100 */             i = 1;
/*      */             
/* 2102 */             while (!U.compareAndSwapLong(this, CTL, l1 = this.ctl, l1 & 0xFFFFFFFFFFFF | (l1 & 0xFFFF000000000000) + 281474976710656L)) {}
/*      */           }
/*      */           
/*      */           int j;
/* 2106 */           if (((j = localWorkQueue.base) - localWorkQueue.top < 0) && ((localForkJoinTask2 = localWorkQueue.pollAt(j)) != null)) {
/* 2107 */             paramWorkQueue.runTask(localForkJoinTask2);
/*      */           }
/* 2109 */         } else if (i != 0) {
/* 2110 */           long l2 = (l1 = this.ctl) & 0xFFFFFFFFFFFF | (l1 & 0xFFFF000000000000) - 281474976710656L;
/* 2111 */           if ((int)(l2 >> 48) + this.parallelism == 0)
/*      */             break;
/* 2113 */           if (U.compareAndSwapLong(this, CTL, l1, l2))
/* 2114 */             i = 0;
/*      */         } else {
/* 2116 */           if (((int)((l1 = this.ctl) >> 48) + this.parallelism <= 0) && 
/*      */           
/* 2118 */             (U.compareAndSwapLong(this, CTL, l1, l1 & 0xFFFFFFFFFFFF | (l1 & 0xFFFF000000000000) + 281474976710656L))) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   final ForkJoinTask<?> nextTaskFor(WorkQueue paramWorkQueue)
/*      */   {
/*      */     for (;;)
/*      */     {
/*      */       ForkJoinTask localForkJoinTask;
/* 2132 */       if ((localForkJoinTask = paramWorkQueue.nextLocalTask()) != null)
/* 2133 */         return localForkJoinTask;
/* 2134 */       WorkQueue localWorkQueue; if ((localWorkQueue = findNonEmptyStealQueue()) == null)
/* 2135 */         return null;
/* 2136 */       int i; if (((i = localWorkQueue.base) - localWorkQueue.top < 0) && ((localForkJoinTask = localWorkQueue.pollAt(i)) != null)) {
/* 2137 */         return localForkJoinTask;
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
/*      */   static int getSurplusQueuedTaskCount()
/*      */   {
/*      */     Thread localThread;
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
/* 2189 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) { ForkJoinWorkerThread localForkJoinWorkerThread;
/* 2190 */       ForkJoinPool localForkJoinPool; int i = (localForkJoinPool = (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool).parallelism;
/* 2191 */       WorkQueue localWorkQueue; int j = (localWorkQueue = localForkJoinWorkerThread.workQueue).top - localWorkQueue.base;
/* 2192 */       int k = (int)(localForkJoinPool.ctl >> 48) + i;
/* 2193 */       return j - (k > i >>>= 1 ? 4 : k > i >>>= 1 ? 2 : k > i >>>= 1 ? 1 : k > i >>>= 1 ? 0 : 8);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2199 */     return 0;
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
/*      */   private boolean tryTerminate(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 2220 */     if (this == common)
/* 2221 */       return false;
/* 2222 */     int i; if ((i = this.plock) >= 0) {
/* 2223 */       if (!paramBoolean2)
/* 2224 */         return false;
/* 2225 */       if (((i & 0x2) != 0) || 
/* 2226 */         (!U.compareAndSwapInt(this, PLOCK, , i)))
/* 2227 */         i = acquirePlock();
/* 2228 */       int j = i + 2 & 0x7FFFFFFF | 0x80000000;
/* 2229 */       if (!U.compareAndSwapInt(this, PLOCK, i, j))
/* 2230 */         releasePlock(j);
/*      */     }
/*      */     for (;;) { long l1;
/* 2233 */       if (((l1 = this.ctl) & 0x80000000) != 0L) {
/* 2234 */         if ((short)(int)(l1 >>> 32) + this.parallelism <= 0) {
/* 2235 */           synchronized (this) {
/* 2236 */             notifyAll();
/*      */           }
/*      */         }
/* 2239 */         return true; }
/*      */       Object localObject2;
/* 2241 */       if (!paramBoolean1)
/*      */       {
/* 2243 */         if ((int)(l1 >> 48) + this.parallelism > 0)
/* 2244 */           return false;
/* 2245 */         if ((??? = this.workQueues) != null) {
/* 2246 */           for (int m = 0; m < ???.length; m++) {
/* 2247 */             if (((localObject2 = ???[m]) != null) && (
/* 2248 */               (!((WorkQueue)localObject2).isEmpty()) || (((m & 0x1) != 0) && (((WorkQueue)localObject2).eventCount >= 0))))
/*      */             {
/* 2250 */               signalWork((WorkQueue[])???, (WorkQueue)localObject2);
/* 2251 */               return false;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2256 */       if (U.compareAndSwapLong(this, CTL, l1, l1 | 0x80000000)) {
/* 2257 */         for (int k = 0; k < 3; k++)
/*      */         {
/* 2259 */           if ((localObject2 = this.workQueues) != null) {
/* 2260 */             int n = localObject2.length;
/* 2261 */             Object localObject3; for (int i1 = 0; i1 < n; i1++) {
/* 2262 */               if ((localObject3 = localObject2[i1]) != null) {
/* 2263 */                 ((WorkQueue)localObject3).qlock = -1;
/* 2264 */                 if (k > 0) {
/* 2265 */                   ((WorkQueue)localObject3).cancelAll();
/* 2266 */                   ForkJoinWorkerThread localForkJoinWorkerThread; if ((k > 1) && ((localForkJoinWorkerThread = ((WorkQueue)localObject3).owner) != null)) {
/* 2267 */                     if (!localForkJoinWorkerThread.isInterrupted()) {
/*      */                       try {
/* 2269 */                         localForkJoinWorkerThread.interrupt();
/*      */                       }
/*      */                       catch (Throwable localThrowable) {}
/*      */                     }
/* 2273 */                     U.unpark(localForkJoinWorkerThread);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             long l2;
/*      */             int i2;
/* 2280 */             while (((i2 = (int)(l2 = this.ctl) & 0x7FFFFFFF) != 0) && ((i1 = i2 & 0xFFFF) < n) && (i1 >= 0) && ((localObject3 = localObject2[i1]) != null))
/*      */             {
/*      */ 
/* 2283 */               long l3 = ((WorkQueue)localObject3).nextWait & 0x7FFFFFFF | l2 + 281474976710656L & 0xFFFF000000000000 | l2 & 0xFFFF80000000;
/*      */               
/*      */ 
/* 2286 */               if ((((WorkQueue)localObject3).eventCount == (i2 | 0x80000000)) && 
/* 2287 */                 (U.compareAndSwapLong(this, CTL, l2, l3))) {
/* 2288 */                 ((WorkQueue)localObject3).eventCount = (i2 + 65536 & 0x7FFFFFFF);
/* 2289 */                 ((WorkQueue)localObject3).qlock = -1;
/* 2290 */                 Thread localThread; if ((localThread = ((WorkQueue)localObject3).parker) != null) {
/* 2291 */                   U.unpark(localThread);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static WorkQueue commonSubmitterQueue()
/*      */   {
/*      */     int j;
/*      */     ForkJoinPool localForkJoinPool;
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     int i;
/* 2308 */     return ((j = ThreadLocalRandom.getProbe()) != 0) && ((localForkJoinPool = common) != null) && ((arrayOfWorkQueue = localForkJoinPool.workQueues) != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) ? arrayOfWorkQueue[(i & j & 0x7E)] : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean tryExternalUnpush(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 2320 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 2321 */     int k = ThreadLocalRandom.getProbe();
/* 2322 */     boolean bool = false;
/* 2323 */     int i; WorkQueue localWorkQueue; int j; ForkJoinTask[] arrayOfForkJoinTask; if ((arrayOfWorkQueue != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && ((localWorkQueue = arrayOfWorkQueue[(k & i & 0x7E)]) != null) && (localWorkQueue.base != (j = localWorkQueue.top)) && ((arrayOfForkJoinTask = localWorkQueue.array) != null))
/*      */     {
/*      */ 
/*      */ 
/* 2327 */       long l = ((arrayOfForkJoinTask.length - 1 & j - 1) << ASHIFT) + ABASE;
/* 2328 */       if ((U.getObject(arrayOfForkJoinTask, l) == paramForkJoinTask) && 
/* 2329 */         (U.compareAndSwapInt(localWorkQueue, QLOCK, 0, 1))) {
/* 2330 */         if ((localWorkQueue.top == j) && (localWorkQueue.array == arrayOfForkJoinTask) && 
/* 2331 */           (U.compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, null))) {
/* 2332 */           localWorkQueue.top = (j - 1);
/* 2333 */           bool = true;
/*      */         }
/* 2335 */         localWorkQueue.qlock = 0;
/*      */       }
/*      */     }
/* 2338 */     return bool;
/*      */   }
/*      */   
/*      */   final int externalHelpComplete(CountedCompleter<?> paramCountedCompleter, int paramInt)
/*      */   {
/* 2343 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 2344 */     int j = ThreadLocalRandom.getProbe();
/* 2345 */     int k = 0;
/* 2346 */     int i; WorkQueue localWorkQueue1; if ((arrayOfWorkQueue != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && ((localWorkQueue1 = arrayOfWorkQueue[(j & i & 0x7E)]) != null) && (paramCountedCompleter != null))
/*      */     {
/* 2348 */       int m = i + i + 1;
/* 2349 */       long l = 0L;
/* 2350 */       j |= 0x1;
/* 2351 */       int n = m;
/* 2353 */       for (; 
/* 2353 */           (k = paramCountedCompleter.status) >= 0; j += 2)
/*      */       {
/*      */ 
/*      */ 
/* 2355 */         if (localWorkQueue1.externalPopAndExecCC(paramCountedCompleter)) {
/* 2356 */           paramInt--; if (paramInt <= 0) {
/* 2357 */             k = paramCountedCompleter.status;
/* 2358 */             break;
/*      */           }
/* 2360 */           n = m;
/*      */         } else {
/* 2362 */           if ((k = paramCountedCompleter.status) < 0) break;
/*      */           WorkQueue localWorkQueue2;
/* 2364 */           if (((localWorkQueue2 = arrayOfWorkQueue[(j & i)]) != null) && (localWorkQueue2.pollAndExecCC(paramCountedCompleter))) {
/* 2365 */             paramInt--; if (paramInt <= 0) {
/* 2366 */               k = paramCountedCompleter.status;
/* 2367 */               break;
/*      */             }
/* 2369 */             n = m;
/*      */           } else {
/* 2371 */             n--; if (n < 0) {
/* 2372 */               if (l == (l = this.ctl))
/*      */                 break;
/* 2374 */               n = m;
/*      */             }
/*      */           }
/*      */         } } }
/* 2378 */     return k;
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
/*      */   public ForkJoinPool()
/*      */   {
/* 2397 */     this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false);
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
/*      */   public ForkJoinPool(int paramInt)
/*      */   {
/* 2416 */     this(paramInt, defaultForkJoinWorkerThreadFactory, null, false);
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
/*      */   public ForkJoinPool(int paramInt, ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, boolean paramBoolean)
/*      */   {
/* 2447 */     this(checkParallelism(paramInt), 
/* 2448 */       checkFactory(paramForkJoinWorkerThreadFactory), paramUncaughtExceptionHandler, paramBoolean ? 1 : 0, "ForkJoinPool-" + 
/*      */       
/*      */ 
/* 2451 */       nextPoolId() + "-worker-");
/* 2452 */     checkPermission();
/*      */   }
/*      */   
/*      */   private static int checkParallelism(int paramInt) {
/* 2456 */     if ((paramInt <= 0) || (paramInt > 32767))
/* 2457 */       throw new IllegalArgumentException();
/* 2458 */     return paramInt;
/*      */   }
/*      */   
/*      */   private static ForkJoinWorkerThreadFactory checkFactory(ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory)
/*      */   {
/* 2463 */     if (paramForkJoinWorkerThreadFactory == null)
/* 2464 */       throw new NullPointerException();
/* 2465 */     return paramForkJoinWorkerThreadFactory;
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
/*      */   private ForkJoinPool(int paramInt1, ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, int paramInt2, String paramString)
/*      */   {
/* 2478 */     this.workerNamePrefix = paramString;
/* 2479 */     this.factory = paramForkJoinWorkerThreadFactory;
/* 2480 */     this.ueh = paramUncaughtExceptionHandler;
/* 2481 */     this.mode = ((short)paramInt2);
/* 2482 */     this.parallelism = ((short)paramInt1);
/* 2483 */     long l = -paramInt1;
/* 2484 */     this.ctl = (l << 48 & 0xFFFF000000000000 | l << 32 & 0xFFFF00000000);
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
/*      */   public static ForkJoinPool commonPool()
/*      */   {
/* 2502 */     return common;
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
/*      */   public <T> T invoke(ForkJoinTask<T> paramForkJoinTask)
/*      */   {
/* 2525 */     if (paramForkJoinTask == null)
/* 2526 */       throw new NullPointerException();
/* 2527 */     externalPush(paramForkJoinTask);
/* 2528 */     return (T)paramForkJoinTask.join();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void execute(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 2540 */     if (paramForkJoinTask == null)
/* 2541 */       throw new NullPointerException();
/* 2542 */     externalPush(paramForkJoinTask);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void execute(Runnable paramRunnable)
/*      */   {
/* 2553 */     if (paramRunnable == null)
/* 2554 */       throw new NullPointerException();
/*      */     Object localObject;
/* 2556 */     if ((paramRunnable instanceof ForkJoinTask)) {
/* 2557 */       localObject = (ForkJoinTask)paramRunnable;
/*      */     } else
/* 2559 */       localObject = new ForkJoinTask.RunnableExecuteAction(paramRunnable);
/* 2560 */     externalPush((ForkJoinTask)localObject);
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
/*      */   public <T> ForkJoinTask<T> submit(ForkJoinTask<T> paramForkJoinTask)
/*      */   {
/* 2574 */     if (paramForkJoinTask == null)
/* 2575 */       throw new NullPointerException();
/* 2576 */     externalPush(paramForkJoinTask);
/* 2577 */     return paramForkJoinTask;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> ForkJoinTask<T> submit(Callable<T> paramCallable)
/*      */   {
/* 2586 */     ForkJoinTask.AdaptedCallable localAdaptedCallable = new ForkJoinTask.AdaptedCallable(paramCallable);
/* 2587 */     externalPush(localAdaptedCallable);
/* 2588 */     return localAdaptedCallable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> ForkJoinTask<T> submit(Runnable paramRunnable, T paramT)
/*      */   {
/* 2597 */     ForkJoinTask.AdaptedRunnable localAdaptedRunnable = new ForkJoinTask.AdaptedRunnable(paramRunnable, paramT);
/* 2598 */     externalPush(localAdaptedRunnable);
/* 2599 */     return localAdaptedRunnable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ForkJoinTask<?> submit(Runnable paramRunnable)
/*      */   {
/* 2608 */     if (paramRunnable == null)
/* 2609 */       throw new NullPointerException();
/*      */     Object localObject;
/* 2611 */     if ((paramRunnable instanceof ForkJoinTask)) {
/* 2612 */       localObject = (ForkJoinTask)paramRunnable;
/*      */     } else
/* 2614 */       localObject = new ForkJoinTask.AdaptedRunnableAction(paramRunnable);
/* 2615 */     externalPush((ForkJoinTask)localObject);
/* 2616 */     return (ForkJoinTask<?>)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection)
/*      */   {
/* 2627 */     ArrayList localArrayList1 = new ArrayList(paramCollection.size());
/*      */     
/* 2629 */     int i = 0;
/*      */     try {
/* 2631 */       for (Callable localCallable : paramCollection) {
/* 2632 */         ForkJoinTask.AdaptedCallable localAdaptedCallable = new ForkJoinTask.AdaptedCallable(localCallable);
/* 2633 */         localArrayList1.add(localAdaptedCallable);
/* 2634 */         externalPush(localAdaptedCallable);
/*      */       }
/* 2636 */       int j = 0; for (int k = localArrayList1.size(); j < k; j++)
/* 2637 */         ((ForkJoinTask)localArrayList1.get(j)).quietlyJoin();
/* 2638 */       i = 1;
/* 2639 */       int m; return localArrayList1;
/*      */     } finally {
/* 2641 */       if (i == 0) {
/* 2642 */         int n = 0; for (int i1 = localArrayList1.size(); n < i1; n++) {
/* 2643 */           ((Future)localArrayList1.get(n)).cancel(false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ForkJoinWorkerThreadFactory getFactory()
/*      */   {
/* 2653 */     return this.factory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler()
/*      */   {
/* 2663 */     return this.ueh;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getParallelism()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/* 2673 */     return (i = this.parallelism) > 0 ? i : 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getCommonPoolParallelism()
/*      */   {
/* 2683 */     return commonParallelism;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getPoolSize()
/*      */   {
/* 2695 */     return this.parallelism + (short)(int)(this.ctl >>> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getAsyncMode()
/*      */   {
/* 2705 */     return this.mode == 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getRunningThreadCount()
/*      */   {
/* 2717 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2719 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2720 */       for (int j = 1; j < arrayOfWorkQueue.length; j += 2) { WorkQueue localWorkQueue;
/* 2721 */         if (((localWorkQueue = arrayOfWorkQueue[j]) != null) && (localWorkQueue.isApparentlyUnblocked()))
/* 2722 */           i++;
/*      */       }
/*      */     }
/* 2725 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getActiveThreadCount()
/*      */   {
/* 2736 */     int i = this.parallelism + (int)(this.ctl >> 48);
/* 2737 */     return i <= 0 ? 0 : i;
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
/*      */   public boolean isQuiescent()
/*      */   {
/* 2752 */     return this.parallelism + (int)(this.ctl >> 48) <= 0;
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
/*      */   public long getStealCount()
/*      */   {
/* 2767 */     long l = this.stealCount;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2769 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2770 */       for (int i = 1; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2771 */         if ((localWorkQueue = arrayOfWorkQueue[i]) != null)
/* 2772 */           l += localWorkQueue.nsteals;
/*      */       }
/*      */     }
/* 2775 */     return l;
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
/*      */   public long getQueuedTaskCount()
/*      */   {
/* 2789 */     long l = 0L;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2791 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2792 */       for (int i = 1; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2793 */         if ((localWorkQueue = arrayOfWorkQueue[i]) != null)
/* 2794 */           l += localWorkQueue.queueSize();
/*      */       }
/*      */     }
/* 2797 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getQueuedSubmissionCount()
/*      */   {
/* 2808 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2810 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2811 */       for (int j = 0; j < arrayOfWorkQueue.length; j += 2) { WorkQueue localWorkQueue;
/* 2812 */         if ((localWorkQueue = arrayOfWorkQueue[j]) != null)
/* 2813 */           i += localWorkQueue.queueSize();
/*      */       }
/*      */     }
/* 2816 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasQueuedSubmissions()
/*      */   {
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     
/*      */ 
/* 2827 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2828 */       for (int i = 0; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2829 */         if (((localWorkQueue = arrayOfWorkQueue[i]) != null) && (!localWorkQueue.isEmpty()))
/* 2830 */           return true;
/*      */       }
/*      */     }
/* 2833 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ForkJoinTask<?> pollSubmission()
/*      */   {
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     
/*      */ 
/*      */ 
/* 2845 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2846 */       for (int i = 0; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2847 */         ForkJoinTask localForkJoinTask; if (((localWorkQueue = arrayOfWorkQueue[i]) != null) && ((localForkJoinTask = localWorkQueue.poll()) != null))
/* 2848 */           return localForkJoinTask;
/*      */       }
/*      */     }
/* 2851 */     return null;
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
/*      */   protected int drainTasksTo(Collection<? super ForkJoinTask<?>> paramCollection)
/*      */   {
/* 2872 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2874 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2875 */       for (int j = 0; j < arrayOfWorkQueue.length; j++) { WorkQueue localWorkQueue;
/* 2876 */         if ((localWorkQueue = arrayOfWorkQueue[j]) != null) { ForkJoinTask localForkJoinTask;
/* 2877 */           while ((localForkJoinTask = localWorkQueue.poll()) != null) {
/* 2878 */             paramCollection.add(localForkJoinTask);
/* 2879 */             i++;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2884 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2896 */     long l1 = 0L;long l2 = 0L;int i = 0;
/* 2897 */     long l3 = this.stealCount;
/* 2898 */     long l4 = this.ctl;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2900 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2901 */       for (j = 0; j < arrayOfWorkQueue.length; j++) { WorkQueue localWorkQueue;
/* 2902 */         if ((localWorkQueue = arrayOfWorkQueue[j]) != null) {
/* 2903 */           k = localWorkQueue.queueSize();
/* 2904 */           if ((j & 0x1) == 0) {
/* 2905 */             l2 += k;
/*      */           } else {
/* 2907 */             l1 += k;
/* 2908 */             l3 += localWorkQueue.nsteals;
/* 2909 */             if (localWorkQueue.isApparentlyUnblocked())
/* 2910 */               i++;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2915 */     int j = this.parallelism;
/* 2916 */     int k = j + (short)(int)(l4 >>> 32);
/* 2917 */     int m = j + (int)(l4 >> 48);
/* 2918 */     if (m < 0)
/* 2919 */       m = 0;
/*      */     String str;
/* 2921 */     if ((l4 & 0x80000000) != 0L) {
/* 2922 */       str = k == 0 ? "Terminated" : "Terminating";
/*      */     } else
/* 2924 */       str = this.plock < 0 ? "Shutting down" : "Running";
/* 2925 */     return super.toString() + "[" + str + ", parallelism = " + j + ", size = " + k + ", active = " + m + ", running = " + i + ", steals = " + l3 + ", tasks = " + l1 + ", submissions = " + l2 + "]";
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
/*      */   public void shutdown()
/*      */   {
/* 2952 */     checkPermission();
/* 2953 */     tryTerminate(false, true);
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
/*      */   public List<Runnable> shutdownNow()
/*      */   {
/* 2975 */     checkPermission();
/* 2976 */     tryTerminate(true, true);
/* 2977 */     return Collections.emptyList();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isTerminated()
/*      */   {
/* 2986 */     long l = this.ctl;
/* 2987 */     return ((l & 0x80000000) != 0L) && ((short)(int)(l >>> 32) + this.parallelism <= 0);
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
/*      */   public boolean isTerminating()
/*      */   {
/* 3005 */     long l = this.ctl;
/* 3006 */     return ((l & 0x80000000) != 0L) && ((short)(int)(l >>> 32) + this.parallelism > 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isShutdown()
/*      */   {
/* 3016 */     return this.plock < 0;
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
/*      */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/* 3035 */     if (Thread.interrupted())
/* 3036 */       throw new InterruptedException();
/* 3037 */     if (this == common) {
/* 3038 */       awaitQuiescence(paramLong, paramTimeUnit);
/* 3039 */       return false;
/*      */     }
/* 3041 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 3042 */     if (isTerminated())
/* 3043 */       return true;
/* 3044 */     if (l1 <= 0L)
/* 3045 */       return false;
/* 3046 */     long l2 = System.nanoTime() + l1;
/* 3047 */     synchronized (this)
/*      */     {
/* 3049 */       if (isTerminated())
/* 3050 */         return true;
/* 3051 */       if (l1 <= 0L)
/* 3052 */         return false;
/* 3053 */       long l3 = TimeUnit.NANOSECONDS.toMillis(l1);
/* 3054 */       wait(l3 > 0L ? l3 : 1L);
/* 3055 */       l1 = l2 - System.nanoTime();
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
/*      */   public boolean awaitQuiescence(long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/* 3072 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*      */     
/* 3074 */     Thread localThread = Thread.currentThread();
/* 3075 */     ForkJoinWorkerThread localForkJoinWorkerThread; if (((localThread instanceof ForkJoinWorkerThread)) && ((localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool == this))
/*      */     {
/* 3077 */       helpQuiescePool(localForkJoinWorkerThread.workQueue);
/* 3078 */       return true;
/*      */     }
/* 3080 */     long l2 = System.nanoTime();
/*      */     
/* 3082 */     int i = 0;
/* 3083 */     int k = 1;
/* 3084 */     WorkQueue[] arrayOfWorkQueue; int j; while ((!isQuiescent()) && ((arrayOfWorkQueue = this.workQueues) != null) && ((j = arrayOfWorkQueue.length - 1) >= 0))
/*      */     {
/* 3086 */       if (k == 0) {
/* 3087 */         if (System.nanoTime() - l2 > l1)
/* 3088 */           return false;
/* 3089 */         Thread.yield();
/*      */       }
/* 3091 */       k = 0;
/* 3092 */       for (int m = j + 1 << 2; m >= 0; m--) { WorkQueue localWorkQueue;
/*      */         int n;
/* 3094 */         if (((localWorkQueue = arrayOfWorkQueue[(i++ & j)]) != null) && ((n = localWorkQueue.base) - localWorkQueue.top < 0)) {
/* 3095 */           k = 1;
/* 3096 */           ForkJoinTask localForkJoinTask; if ((localForkJoinTask = localWorkQueue.pollAt(n)) == null) break;
/* 3097 */           localForkJoinTask.doExec(); break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3102 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void quiesceCommonPool()
/*      */   {
/* 3110 */     common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
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
/*      */   protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
/*      */   {
/* 3235 */     return new ForkJoinTask.AdaptedRunnable(paramRunnable, paramT);
/*      */   }
/*      */   
/*      */   protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable) {
/* 3239 */     return new ForkJoinTask.AdaptedCallable(paramCallable);
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
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 3257 */       U = Unsafe.getUnsafe();
/* 3258 */       Class localClass1 = ForkJoinPool.class;
/*      */       
/* 3260 */       CTL = U.objectFieldOffset(localClass1.getDeclaredField("ctl"));
/*      */       
/* 3262 */       STEALCOUNT = U.objectFieldOffset(localClass1.getDeclaredField("stealCount"));
/*      */       
/* 3264 */       PLOCK = U.objectFieldOffset(localClass1.getDeclaredField("plock"));
/*      */       
/* 3266 */       INDEXSEED = U.objectFieldOffset(localClass1.getDeclaredField("indexSeed"));
/* 3267 */       Class localClass2 = Thread.class;
/*      */       
/* 3269 */       PARKBLOCKER = U.objectFieldOffset(localClass2.getDeclaredField("parkBlocker"));
/* 3270 */       Class localClass3 = WorkQueue.class;
/*      */       
/* 3272 */       QBASE = U.objectFieldOffset(localClass3.getDeclaredField("base"));
/*      */       
/* 3274 */       QLOCK = U.objectFieldOffset(localClass3.getDeclaredField("qlock"));
/* 3275 */       Class localClass4 = ForkJoinTask[].class;
/* 3276 */       ABASE = U.arrayBaseOffset(localClass4);
/* 3277 */       int j = U.arrayIndexScale(localClass4);
/* 3278 */       if ((j & j - 1) != 0)
/* 3279 */         throw new Error("data type scale not a power of two");
/* 3280 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(j);
/*      */     } catch (Exception localException) {
/* 3282 */       throw new Error(localException);
/*      */     }
/*      */     
/* 3285 */     defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
/*      */     
/* 3287 */     modifyThreadPermission = new RuntimePermission("modifyThread");
/*      */     
/*      */ 
/* 3290 */     common = (ForkJoinPool)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/* 3291 */       public ForkJoinPool run() { return ForkJoinPool.access$000(); } });
/* 3292 */     int i = common.parallelism;
/* 3293 */     commonParallelism = i > 0 ? i : 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ForkJoinPool makeCommonPool()
/*      */   {
/* 3301 */     int i = -1;
/* 3302 */     Object localObject = null;
/* 3303 */     Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = null;
/*      */     try
/*      */     {
/* 3306 */       String str1 = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
/*      */       
/* 3308 */       String str2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
/*      */       
/* 3310 */       String str3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
/* 3311 */       if (str1 != null)
/* 3312 */         i = Integer.parseInt(str1);
/* 3313 */       if (str2 != null)
/*      */       {
/* 3315 */         localObject = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(str2).newInstance(); }
/* 3316 */       if (str3 != null)
/*      */       {
/* 3318 */         localUncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(str3).newInstance();
/*      */       }
/*      */     } catch (Exception localException) {}
/* 3321 */     if (localObject == null) {
/* 3322 */       if (System.getSecurityManager() == null) {
/* 3323 */         localObject = defaultForkJoinWorkerThreadFactory;
/*      */       } else
/* 3325 */         localObject = new InnocuousForkJoinWorkerThreadFactory();
/*      */     }
/* 3327 */     if ((i < 0) && 
/* 3328 */       ((i = Runtime.getRuntime().availableProcessors() - 1) <= 0))
/* 3329 */       i = 1;
/* 3330 */     if (i > 32767)
/* 3331 */       i = 32767;
/* 3332 */     return new ForkJoinPool(i, (ForkJoinWorkerThreadFactory)localObject, localUncaughtExceptionHandler, 0, "ForkJoinPool.commonPool-worker-");
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public static void managedBlock(ManagedBlocker paramManagedBlocker)
/*      */     throws InterruptedException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: invokestatic 14	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*      */     //   3: astore_1
/*      */     //   4: aload_1
/*      */     //   5: instanceof 137
/*      */     //   8: ifeq +69 -> 77
/*      */     //   11: aload_1
/*      */     //   12: checkcast 137	java/util/concurrent/ForkJoinWorkerThread
/*      */     //   15: getfield 138	java/util/concurrent/ForkJoinWorkerThread:pool	Ljava/util/concurrent/ForkJoinPool;
/*      */     //   18: astore_2
/*      */     //   19: aload_0
/*      */     //   20: invokeinterface 229 1 0
/*      */     //   25: ifne +49 -> 74
/*      */     //   28: aload_2
/*      */     //   29: aload_2
/*      */     //   30: getfield 18	java/util/concurrent/ForkJoinPool:ctl	J
/*      */     //   33: invokevirtual 132	java/util/concurrent/ForkJoinPool:tryCompensate	(J)Z
/*      */     //   36: ifeq -17 -> 19
/*      */     //   39: aload_0
/*      */     //   40: invokeinterface 229 1 0
/*      */     //   45: ifne +12 -> 57
/*      */     //   48: aload_0
/*      */     //   49: invokeinterface 230 1 0
/*      */     //   54: ifeq -15 -> 39
/*      */     //   57: aload_2
/*      */     //   58: invokevirtual 231	java/util/concurrent/ForkJoinPool:incrementActiveCount	()V
/*      */     //   61: goto +10 -> 71
/*      */     //   64: astore_3
/*      */     //   65: aload_2
/*      */     //   66: invokevirtual 231	java/util/concurrent/ForkJoinPool:incrementActiveCount	()V
/*      */     //   69: aload_3
/*      */     //   70: athrow
/*      */     //   71: goto +3 -> 74
/*      */     //   74: goto +21 -> 95
/*      */     //   77: aload_0
/*      */     //   78: invokeinterface 229 1 0
/*      */     //   83: ifne +12 -> 95
/*      */     //   86: aload_0
/*      */     //   87: invokeinterface 230 1 0
/*      */     //   92: ifeq -15 -> 77
/*      */     //   95: return
/*      */     // Line number table:
/*      */     //   Java source line #3209	-> byte code offset #0
/*      */     //   Java source line #3210	-> byte code offset #4
/*      */     //   Java source line #3211	-> byte code offset #11
/*      */     //   Java source line #3212	-> byte code offset #19
/*      */     //   Java source line #3213	-> byte code offset #28
/*      */     //   Java source line #3215	-> byte code offset #39
/*      */     //   Java source line #3216	-> byte code offset #49
/*      */     //   Java source line #3218	-> byte code offset #57
/*      */     //   Java source line #3219	-> byte code offset #61
/*      */     //   Java source line #3218	-> byte code offset #64
/*      */     //   Java source line #3220	-> byte code offset #71
/*      */     //   Java source line #3223	-> byte code offset #74
/*      */     //   Java source line #3225	-> byte code offset #77
/*      */     //   Java source line #3226	-> byte code offset #87
/*      */     //   Java source line #3228	-> byte code offset #95
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	96	0	paramManagedBlocker	ManagedBlocker
/*      */     //   3	9	1	localThread	Thread
/*      */     //   18	48	2	localForkJoinPool	ForkJoinPool
/*      */     //   64	6	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   39	57	64	finally
/*      */   }
/*      */   
/*      */   public static abstract interface ManagedBlocker
/*      */   {
/*      */     public abstract boolean block()
/*      */       throws InterruptedException;
/*      */     
/*      */     public abstract boolean isReleasable();
/*      */   }
/*      */   
/*      */   static final class InnocuousForkJoinWorkerThreadFactory
/*      */     implements ForkJoinPool.ForkJoinWorkerThreadFactory
/*      */   {
/*      */     private static final AccessControlContext innocuousAcc;
/*      */     
/*      */     static
/*      */     {
/* 3348 */       Permissions localPermissions = new Permissions();
/* 3349 */       localPermissions.add(ForkJoinPool.modifyThreadPermission);
/* 3350 */       localPermissions.add(new RuntimePermission("enableContextClassLoaderOverride"));
/*      */       
/* 3352 */       localPermissions.add(new RuntimePermission("modifyThreadGroup"));
/*      */       
/* 3354 */       innocuousAcc = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, localPermissions) });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public final ForkJoinWorkerThread newThread(final ForkJoinPool paramForkJoinPool)
/*      */     {
/* 3361 */       (ForkJoinWorkerThread.InnocuousForkJoinWorkerThread)AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */ 
/* 3364 */         public ForkJoinWorkerThread run() { return new ForkJoinWorkerThread.InnocuousForkJoinWorkerThread(paramForkJoinPool); } }, innocuousAcc);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/ForkJoinPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */