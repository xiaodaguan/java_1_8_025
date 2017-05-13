/*     */ package java.lang;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UNIXProcess
/*     */   extends Process
/*     */ {
/*  57 */   private static final JavaIOFileDescriptorAccess fdAccess = ;
/*     */   
/*     */   private final int pid;
/*     */   private int exitcode;
/*     */   private boolean hasExited;
/*     */   private OutputStream stdin;
/*     */   private InputStream stdout;
/*     */   private InputStream stderr;
/*     */   
/*     */   private static enum LaunchMechanism
/*     */   {
/*  68 */     FORK(1), 
/*  69 */     POSIX_SPAWN(2);
/*     */     
/*     */     private LaunchMechanism(int paramInt) {
/*  72 */       this.value = paramInt;
/*     */     }
/*     */     
/*     */     private int value;
/*     */   }
/*     */   
/*     */   private static byte[] toCString(String paramString)
/*     */   {
/*  80 */     if (paramString == null)
/*  81 */       return null;
/*  82 */     byte[] arrayOfByte1 = paramString.getBytes();
/*  83 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
/*  84 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/*     */     
/*     */ 
/*  87 */     arrayOfByte2[(arrayOfByte2.length - 1)] = 0;
/*  88 */     return arrayOfByte2;
/*     */   }
/*     */   
/*     */ 
/*  92 */   private static final LaunchMechanism launchMechanism = (LaunchMechanism)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public UNIXProcess.LaunchMechanism run()
/*     */     {
/*  96 */       String str1 = System.getProperty("java.home");
/*     */       
/*  98 */       UNIXProcess.access$002(UNIXProcess.toCString(str1 + "/lib/jspawnhelper"));
/*  99 */       String str2 = System.getProperty("jdk.lang.Process.launchMechanism", "posix_spawn");
/*     */       
/*     */       try
/*     */       {
/* 103 */         return UNIXProcess.LaunchMechanism.valueOf(str2.toUpperCase());
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 105 */         throw new Error(str2 + " is not a supported " + "process launch mechanism on this platform.");
/*     */       }
/*     */     }
/*  92 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static byte[] helperpath;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native int waitForProcessExit(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native int forkAndExec(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt2, byte[] paramArrayOfByte4, int paramInt3, byte[] paramArrayOfByte5, int[] paramArrayOfInt, boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class ProcessReaperThreadFactory
/*     */     implements ThreadFactory
/*     */   {
/* 145 */     private static final ThreadGroup group = ;
/*     */     
/*     */     private static ThreadGroup getRootThreadGroup() {
/* 148 */       (ThreadGroup)AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public ThreadGroup run() {
/* 150 */           ThreadGroup localThreadGroup = Thread.currentThread().getThreadGroup();
/* 151 */           while (localThreadGroup.getParent() != null)
/* 152 */             localThreadGroup = localThreadGroup.getParent();
/* 153 */           return localThreadGroup;
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*     */     public Thread newThread(Runnable paramRunnable) {
/* 159 */       Thread localThread = new Thread(group, paramRunnable, "process reaper", 32768L);
/* 160 */       localThread.setDaemon(true);
/*     */       
/* 162 */       localThread.setPriority(10);
/* 163 */       return localThread;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */   private static final Executor processReaperExecutor = (Executor)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public Executor run()
/*     */     {
/* 174 */       return Executors.newCachedThreadPool(new UNIXProcess.ProcessReaperThreadFactory(null));
/*     */     }
/* 171 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   UNIXProcess(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, byte[] paramArrayOfByte3, int paramInt2, byte[] paramArrayOfByte4, final int[] paramArrayOfInt, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 185 */     this.pid = forkAndExec(launchMechanism.value, helperpath, paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramArrayOfByte3, paramInt2, paramArrayOfByte4, paramArrayOfInt, paramBoolean);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 195 */       AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */         public Void run() throws IOException {
/* 197 */           UNIXProcess.this.initStreams(paramArrayOfInt);
/* 198 */           return null;
/*     */         }
/*     */       });
/* 201 */     } catch (PrivilegedActionException localPrivilegedActionException) { throw ((IOException)localPrivilegedActionException.getException());
/*     */     }
/*     */   }
/*     */   
/*     */   static FileDescriptor newFileDescriptor(int paramInt) {
/* 206 */     FileDescriptor localFileDescriptor = new FileDescriptor();
/* 207 */     fdAccess.set(localFileDescriptor, paramInt);
/* 208 */     return localFileDescriptor;
/*     */   }
/*     */   
/*     */   void initStreams(int[] paramArrayOfInt) throws IOException {
/* 212 */     this.stdin = (paramArrayOfInt[0] == -1 ? ProcessBuilder.NullOutputStream.INSTANCE : new ProcessPipeOutputStream(paramArrayOfInt[0]));
/*     */     
/*     */ 
/*     */ 
/* 216 */     this.stdout = (paramArrayOfInt[1] == -1 ? ProcessBuilder.NullInputStream.INSTANCE : new ProcessPipeInputStream(paramArrayOfInt[1]));
/*     */     
/*     */ 
/*     */ 
/* 220 */     this.stderr = (paramArrayOfInt[2] == -1 ? ProcessBuilder.NullInputStream.INSTANCE : new ProcessPipeInputStream(paramArrayOfInt[2]));
/*     */     
/*     */ 
/*     */ 
/* 224 */     processReaperExecutor.execute(new Runnable() {
/*     */       public void run() {
/* 226 */         int i = UNIXProcess.this.waitForProcessExit(UNIXProcess.this.pid);
/* 227 */         UNIXProcess.this.processExited(i);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/* 232 */   void processExited(int paramInt) { synchronized (this) {
/* 233 */       this.exitcode = paramInt;
/* 234 */       this.hasExited = true;
/* 235 */       notifyAll();
/*     */     }
/*     */     
/* 238 */     if ((this.stdout instanceof ProcessPipeInputStream)) {
/* 239 */       ((ProcessPipeInputStream)this.stdout).processExited();
/*     */     }
/* 241 */     if ((this.stderr instanceof ProcessPipeInputStream)) {
/* 242 */       ((ProcessPipeInputStream)this.stderr).processExited();
/*     */     }
/* 244 */     if ((this.stdin instanceof ProcessPipeOutputStream))
/* 245 */       ((ProcessPipeOutputStream)this.stdin).processExited();
/*     */   }
/*     */   
/*     */   public OutputStream getOutputStream() {
/* 249 */     return this.stdin;
/*     */   }
/*     */   
/*     */   public InputStream getInputStream() {
/* 253 */     return this.stdout;
/*     */   }
/*     */   
/*     */   public InputStream getErrorStream() {
/* 257 */     return this.stderr;
/*     */   }
/*     */   
/*     */   public synchronized int waitFor() throws InterruptedException {
/* 261 */     while (!this.hasExited) {
/* 262 */       wait();
/*     */     }
/* 264 */     return this.exitcode;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized boolean waitFor(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 271 */     if (this.hasExited) return true;
/* 272 */     if (paramLong <= 0L) { return false;
/*     */     }
/* 274 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 275 */     long l2 = System.nanoTime();
/* 276 */     long l3 = l1;
/*     */     
/* 278 */     while ((!this.hasExited) && (l3 > 0L)) {
/* 279 */       wait(Math.max(TimeUnit.NANOSECONDS.toMillis(l3), 1L));
/* 280 */       l3 = l1 - (System.nanoTime() - l2);
/*     */     }
/* 282 */     return this.hasExited;
/*     */   }
/*     */   
/*     */   public synchronized int exitValue() {
/* 286 */     if (!this.hasExited) {
/* 287 */       throw new IllegalThreadStateException("process hasn't exited");
/*     */     }
/* 289 */     return this.exitcode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static native void destroyProcess(int paramInt, boolean paramBoolean);
/*     */   
/*     */ 
/*     */ 
/*     */   private void destroy(boolean paramBoolean)
/*     */   {
/* 300 */     synchronized (this) {
/* 301 */       if (!this.hasExited)
/* 302 */         destroyProcess(this.pid, paramBoolean);
/*     */     }
/* 304 */     try { this.stdin.close(); } catch (IOException localIOException1) {}
/* 305 */     try { this.stdout.close(); } catch (IOException localIOException2) {}
/* 306 */     try { this.stderr.close();
/*     */     } catch (IOException localIOException3) {}
/*     */   }
/*     */   
/* 310 */   public void destroy() { destroy(false); }
/*     */   
/*     */ 
/*     */   public Process destroyForcibly()
/*     */   {
/* 315 */     destroy(true);
/* 316 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized boolean isAlive()
/*     */   {
/* 321 */     return !this.hasExited;
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   static {
/* 327 */     init();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static class ProcessPipeInputStream
/*     */     extends BufferedInputStream
/*     */   {
/* 340 */     private final Object closeLock = new Object();
/*     */     
/*     */     ProcessPipeInputStream(int paramInt) {
/* 343 */       super();
/*     */     }
/*     */     
/*     */     private static byte[] drainInputStream(InputStream paramInputStream) throws IOException {
/* 347 */       int i = 0;
/*     */       
/* 349 */       byte[] arrayOfByte = null;
/* 350 */       int j; while ((j = paramInputStream.available()) > 0) {
/* 351 */         arrayOfByte = arrayOfByte == null ? new byte[j] : Arrays.copyOf(arrayOfByte, i + j);
/* 352 */         i += paramInputStream.read(arrayOfByte, i, j);
/*     */       }
/* 354 */       return (arrayOfByte == null) || (i == arrayOfByte.length) ? arrayOfByte : Arrays.copyOf(arrayOfByte, i);
/*     */     }
/*     */     
/*     */     synchronized void processExited()
/*     */     {
/* 359 */       synchronized (this.closeLock) {
/*     */         try {
/* 361 */           InputStream localInputStream = this.in;
/*     */           
/* 363 */           if (localInputStream != null) {
/* 364 */             byte[] arrayOfByte = drainInputStream(localInputStream);
/* 365 */             localInputStream.close();
/* 366 */             this.in = (arrayOfByte == null ? ProcessBuilder.NullInputStream.INSTANCE : new ByteArrayInputStream(arrayOfByte));
/*     */           }
/*     */         }
/*     */         catch (IOException localIOException) {}
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void close()
/*     */       throws IOException
/*     */     {
/* 378 */       synchronized (this.closeLock) {
/* 379 */         super.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static class ProcessPipeOutputStream
/*     */     extends BufferedOutputStream
/*     */   {
/*     */     ProcessPipeOutputStream(int paramInt)
/*     */     {
/* 391 */       super();
/*     */     }
/*     */     
/*     */     synchronized void processExited()
/*     */     {
/* 396 */       OutputStream localOutputStream = this.out;
/* 397 */       if (localOutputStream != null) {
/*     */         try {
/* 399 */           localOutputStream.close();
/*     */         }
/*     */         catch (IOException localIOException) {}
/*     */         
/*     */ 
/* 404 */         this.out = ProcessBuilder.NullOutputStream.INSTANCE;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/UNIXProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */