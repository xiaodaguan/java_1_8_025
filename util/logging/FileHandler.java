/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileHandler
/*     */   extends StreamHandler
/*     */ {
/*     */   private MeteredStream meter;
/*     */   private boolean append;
/*     */   private int limit;
/*     */   private int count;
/*     */   private String pattern;
/*     */   private String lockFileName;
/*     */   private FileChannel lockFileChannel;
/*     */   private File[] files;
/*     */   private static final int MAX_LOCKS = 100;
/* 152 */   private static final HashMap<String, String> locks = new HashMap();
/*     */   
/*     */ 
/*     */   private class MeteredStream
/*     */     extends OutputStream
/*     */   {
/*     */     final OutputStream out;
/*     */     
/*     */     int written;
/*     */     
/*     */     MeteredStream(OutputStream paramOutputStream, int paramInt)
/*     */     {
/* 164 */       this.out = paramOutputStream;
/* 165 */       this.written = paramInt;
/*     */     }
/*     */     
/*     */     public void write(int paramInt) throws IOException
/*     */     {
/* 170 */       this.out.write(paramInt);
/* 171 */       this.written += 1;
/*     */     }
/*     */     
/*     */     public void write(byte[] paramArrayOfByte) throws IOException
/*     */     {
/* 176 */       this.out.write(paramArrayOfByte);
/* 177 */       this.written += paramArrayOfByte.length;
/*     */     }
/*     */     
/*     */     public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */     {
/* 182 */       this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/* 183 */       this.written += paramInt2;
/*     */     }
/*     */     
/*     */     public void flush() throws IOException
/*     */     {
/* 188 */       this.out.flush();
/*     */     }
/*     */     
/*     */     public void close() throws IOException
/*     */     {
/* 193 */       this.out.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void open(File paramFile, boolean paramBoolean) throws IOException {
/* 198 */     int i = 0;
/* 199 */     if (paramBoolean) {
/* 200 */       i = (int)paramFile.length();
/*     */     }
/* 202 */     FileOutputStream localFileOutputStream = new FileOutputStream(paramFile.toString(), paramBoolean);
/* 203 */     BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream);
/* 204 */     this.meter = new MeteredStream(localBufferedOutputStream, i);
/* 205 */     setOutputStream(this.meter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void configure()
/*     */   {
/* 213 */     LogManager localLogManager = LogManager.getLogManager();
/*     */     
/* 215 */     String str = getClass().getName();
/*     */     
/* 217 */     this.pattern = localLogManager.getStringProperty(str + ".pattern", "%h/java%u.log");
/* 218 */     this.limit = localLogManager.getIntProperty(str + ".limit", 0);
/* 219 */     if (this.limit < 0) {
/* 220 */       this.limit = 0;
/*     */     }
/* 222 */     this.count = localLogManager.getIntProperty(str + ".count", 1);
/* 223 */     if (this.count <= 0) {
/* 224 */       this.count = 1;
/*     */     }
/* 226 */     this.append = localLogManager.getBooleanProperty(str + ".append", false);
/* 227 */     setLevel(localLogManager.getLevelProperty(str + ".level", Level.ALL));
/* 228 */     setFilter(localLogManager.getFilterProperty(str + ".filter", null));
/* 229 */     setFormatter(localLogManager.getFormatterProperty(str + ".formatter", new XMLFormatter()));
/*     */     try {
/* 231 */       setEncoding(localLogManager.getStringProperty(str + ".encoding", null));
/*     */     } catch (Exception localException1) {
/*     */       try {
/* 234 */         setEncoding(null);
/*     */       }
/*     */       catch (Exception localException2) {}
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
/*     */   public FileHandler()
/*     */     throws IOException, SecurityException
/*     */   {
/* 253 */     checkPermission();
/* 254 */     configure();
/* 255 */     openFiles();
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
/*     */   public FileHandler(String paramString)
/*     */     throws IOException, SecurityException
/*     */   {
/* 276 */     if (paramString.length() < 1) {
/* 277 */       throw new IllegalArgumentException();
/*     */     }
/* 279 */     checkPermission();
/* 280 */     configure();
/* 281 */     this.pattern = paramString;
/* 282 */     this.limit = 0;
/* 283 */     this.count = 1;
/* 284 */     openFiles();
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
/*     */   public FileHandler(String paramString, boolean paramBoolean)
/*     */     throws IOException, SecurityException
/*     */   {
/* 309 */     if (paramString.length() < 1) {
/* 310 */       throw new IllegalArgumentException();
/*     */     }
/* 312 */     checkPermission();
/* 313 */     configure();
/* 314 */     this.pattern = paramString;
/* 315 */     this.limit = 0;
/* 316 */     this.count = 1;
/* 317 */     this.append = paramBoolean;
/* 318 */     openFiles();
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
/*     */   public FileHandler(String paramString, int paramInt1, int paramInt2)
/*     */     throws IOException, SecurityException
/*     */   {
/* 346 */     if ((paramInt1 < 0) || (paramInt2 < 1) || (paramString.length() < 1)) {
/* 347 */       throw new IllegalArgumentException();
/*     */     }
/* 349 */     checkPermission();
/* 350 */     configure();
/* 351 */     this.pattern = paramString;
/* 352 */     this.limit = paramInt1;
/* 353 */     this.count = paramInt2;
/* 354 */     openFiles();
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
/*     */   public FileHandler(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */     throws IOException, SecurityException
/*     */   {
/* 385 */     if ((paramInt1 < 0) || (paramInt2 < 1) || (paramString.length() < 1)) {
/* 386 */       throw new IllegalArgumentException();
/*     */     }
/* 388 */     checkPermission();
/* 389 */     configure();
/* 390 */     this.pattern = paramString;
/* 391 */     this.limit = paramInt1;
/* 392 */     this.count = paramInt2;
/* 393 */     this.append = paramBoolean;
/* 394 */     openFiles();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void openFiles()
/*     */     throws IOException
/*     */   {
/* 402 */     LogManager localLogManager = LogManager.getLogManager();
/* 403 */     localLogManager.checkPermission();
/* 404 */     if (this.count < 1) {
/* 405 */       throw new IllegalArgumentException("file count = " + this.count);
/*     */     }
/* 407 */     if (this.limit < 0) {
/* 408 */       this.limit = 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 413 */     InitializationErrorManager localInitializationErrorManager = new InitializationErrorManager(null);
/* 414 */     setErrorManager(localInitializationErrorManager);
/*     */     
/*     */ 
/*     */ 
/* 418 */     int i = -1;
/*     */     for (;;) {
/* 420 */       i++;
/* 421 */       if (i > 100) {
/* 422 */         throw new IOException("Couldn't get lock for " + this.pattern);
/*     */       }
/*     */       
/* 425 */       this.lockFileName = (generate(this.pattern, 0, i).toString() + ".lck");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 430 */       synchronized (locks) {
/* 431 */         if (locks.get(this.lockFileName) == null)
/*     */         {
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/*     */ 
/* 438 */             this.lockFileChannel = FileChannel.open(Paths.get(this.lockFileName, new String[0]), new OpenOption[] { StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE });
/*     */           }
/*     */           catch (FileAlreadyExistsException localFileAlreadyExistsException) {}
/*     */           
/* 442 */           continue;
/*     */           
/*     */           int k;
/*     */           try
/*     */           {
/* 447 */             k = this.lockFileChannel.tryLock() != null ? 1 : 0;
/*     */ 
/*     */ 
/*     */           }
/*     */           catch (IOException localIOException)
/*     */           {
/*     */ 
/* 454 */             k = 1;
/*     */           }
/* 456 */           if (k != 0)
/*     */           {
/* 458 */             locks.put(this.lockFileName, this.lockFileName);
/* 459 */             break;
/*     */           }
/*     */           
/*     */ 
/* 463 */           this.lockFileChannel.close();
/*     */         }
/*     */       }
/*     */     }
/* 467 */     this.files = new File[this.count];
/* 468 */     for (int j = 0; j < this.count; j++) {
/* 469 */       this.files[j] = generate(this.pattern, j, i);
/*     */     }
/*     */     
/*     */ 
/* 473 */     if (this.append) {
/* 474 */       open(this.files[0], true);
/*     */     } else {
/* 476 */       rotate();
/*     */     }
/*     */     
/*     */ 
/* 480 */     Exception localException = localInitializationErrorManager.lastException;
/* 481 */     if (localException != null) {
/* 482 */       if ((localException instanceof IOException))
/* 483 */         throw ((IOException)localException);
/* 484 */       if ((localException instanceof SecurityException)) {
/* 485 */         throw ((SecurityException)localException);
/*     */       }
/* 487 */       throw new IOException("Exception: " + localException);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 492 */     setErrorManager(new ErrorManager());
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
/*     */   private File generate(String paramString, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 506 */     File localFile = null;
/* 507 */     String str1 = "";
/* 508 */     int i = 0;
/* 509 */     int j = 0;
/* 510 */     int k = 0;
/* 511 */     while (i < paramString.length()) {
/* 512 */       char c = paramString.charAt(i);
/* 513 */       i++;
/* 514 */       int m = 0;
/* 515 */       if (i < paramString.length()) {
/* 516 */         m = Character.toLowerCase(paramString.charAt(i));
/*     */       }
/* 518 */       if (c == '/') {
/* 519 */         if (localFile == null) {
/* 520 */           localFile = new File(str1);
/*     */         } else {
/* 522 */           localFile = new File(localFile, str1);
/*     */         }
/* 524 */         str1 = "";
/*     */       } else {
/* 526 */         if (c == '%') {
/* 527 */           if (m == 116) {
/* 528 */             String str2 = System.getProperty("java.io.tmpdir");
/* 529 */             if (str2 == null) {
/* 530 */               str2 = System.getProperty("user.home");
/*     */             }
/* 532 */             localFile = new File(str2);
/* 533 */             i++;
/* 534 */             str1 = "";
/* 535 */             continue; }
/* 536 */           if (m == 104) {
/* 537 */             localFile = new File(System.getProperty("user.home"));
/* 538 */             if (isSetUID())
/*     */             {
/*     */ 
/* 541 */               throw new IOException("can't use %h in set UID program");
/*     */             }
/* 543 */             i++;
/* 544 */             str1 = "";
/* 545 */             continue; }
/* 546 */           if (m == 103) {
/* 547 */             str1 = str1 + paramInt1;
/* 548 */             j = 1;
/* 549 */             i++;
/* 550 */             continue; }
/* 551 */           if (m == 117) {
/* 552 */             str1 = str1 + paramInt2;
/* 553 */             k = 1;
/* 554 */             i++;
/* 555 */             continue; }
/* 556 */           if (m == 37) {
/* 557 */             str1 = str1 + "%";
/* 558 */             i++;
/* 559 */             continue;
/*     */           }
/*     */         }
/* 562 */         str1 = str1 + c;
/*     */       } }
/* 564 */     if ((this.count > 1) && (j == 0)) {
/* 565 */       str1 = str1 + "." + paramInt1;
/*     */     }
/* 567 */     if ((paramInt2 > 0) && (k == 0)) {
/* 568 */       str1 = str1 + "." + paramInt2;
/*     */     }
/* 570 */     if (str1.length() > 0) {
/* 571 */       if (localFile == null) {
/* 572 */         localFile = new File(str1);
/*     */       } else {
/* 574 */         localFile = new File(localFile, str1);
/*     */       }
/*     */     }
/* 577 */     return localFile;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private synchronized void rotate()
/*     */   {
/* 584 */     Level localLevel = getLevel();
/* 585 */     setLevel(Level.OFF);
/*     */     
/* 587 */     super.close();
/* 588 */     for (int i = this.count - 2; i >= 0; i--) {
/* 589 */       File localFile1 = this.files[i];
/* 590 */       File localFile2 = this.files[(i + 1)];
/* 591 */       if (localFile1.exists()) {
/* 592 */         if (localFile2.exists()) {
/* 593 */           localFile2.delete();
/*     */         }
/* 595 */         localFile1.renameTo(localFile2);
/*     */       }
/*     */     }
/*     */     try {
/* 599 */       open(this.files[0], false);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 603 */       reportError(null, localIOException, 4);
/*     */     }
/*     */     
/* 606 */     setLevel(localLevel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void publish(LogRecord paramLogRecord)
/*     */   {
/* 617 */     if (!isLoggable(paramLogRecord)) {
/* 618 */       return;
/*     */     }
/* 620 */     super.publish(paramLogRecord);
/* 621 */     flush();
/* 622 */     if ((this.limit > 0) && (this.meter.written >= this.limit))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 628 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Object run() {
/* 631 */           FileHandler.this.rotate();
/* 632 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void close()
/*     */     throws SecurityException
/*     */   {
/* 646 */     super.close();
/*     */     
/* 648 */     if (this.lockFileName == null) {
/* 649 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 653 */       this.lockFileChannel.close();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 657 */     synchronized (locks) {
/* 658 */       locks.remove(this.lockFileName);
/*     */     }
/* 660 */     new File(this.lockFileName).delete();
/* 661 */     this.lockFileName = null;
/* 662 */     this.lockFileChannel = null;
/*     */   }
/*     */   
/*     */   private static native boolean isSetUID();
/*     */   
/*     */   private static class InitializationErrorManager extends ErrorManager { Exception lastException;
/*     */     
/* 669 */     public void error(String paramString, Exception paramException, int paramInt) { this.lastException = paramException; }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/FileHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */