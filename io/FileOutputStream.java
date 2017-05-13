/*     */ package java.io;
/*     */ 
/*     */ import java.nio.channels.FileChannel;
/*     */ import sun.nio.ch.FileChannelImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final FileDescriptor fd;
/*     */   private final boolean append;
/*     */   private FileChannel channel;
/*     */   private final String path;
/*  76 */   private final Object closeLock = new Object();
/*  77 */   private volatile boolean closed = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileOutputStream(String paramString)
/*     */     throws FileNotFoundException
/*     */   {
/* 101 */     this(paramString != null ? new File(paramString) : null, false);
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
/*     */   public FileOutputStream(String paramString, boolean paramBoolean)
/*     */     throws FileNotFoundException
/*     */   {
/* 133 */     this(paramString != null ? new File(paramString) : null, paramBoolean);
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
/*     */   public FileOutputStream(File paramFile)
/*     */     throws FileNotFoundException
/*     */   {
/* 162 */     this(paramFile, false);
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
/*     */   public FileOutputStream(File paramFile, boolean paramBoolean)
/*     */     throws FileNotFoundException
/*     */   {
/* 197 */     String str = paramFile != null ? paramFile.getPath() : null;
/* 198 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 199 */     if (localSecurityManager != null) {
/* 200 */       localSecurityManager.checkWrite(str);
/*     */     }
/* 202 */     if (str == null) {
/* 203 */       throw new NullPointerException();
/*     */     }
/* 205 */     if (paramFile.isInvalid()) {
/* 206 */       throw new FileNotFoundException("Invalid file path");
/*     */     }
/* 208 */     this.fd = new FileDescriptor();
/* 209 */     this.fd.attach(this);
/* 210 */     this.append = paramBoolean;
/* 211 */     this.path = str;
/*     */     
/* 213 */     open(str, paramBoolean);
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
/*     */   public FileOutputStream(FileDescriptor paramFileDescriptor)
/*     */   {
/* 240 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 241 */     if (paramFileDescriptor == null) {
/* 242 */       throw new NullPointerException();
/*     */     }
/* 244 */     if (localSecurityManager != null) {
/* 245 */       localSecurityManager.checkWrite(paramFileDescriptor);
/*     */     }
/* 247 */     this.fd = paramFileDescriptor;
/* 248 */     this.append = false;
/* 249 */     this.path = null;
/*     */     
/* 251 */     this.fd.attach(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native void open(String paramString, boolean paramBoolean)
/*     */     throws FileNotFoundException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native void write(int paramInt, boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 279 */     write(paramInt, this.append);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native void writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 302 */     writeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, this.append);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 315 */     writeBytes(paramArrayOfByte, paramInt1, paramInt2, this.append);
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 332 */     synchronized (this.closeLock) {
/* 333 */       if (this.closed) {
/* 334 */         return;
/*     */       }
/* 336 */       this.closed = true;
/*     */     }
/*     */     
/* 339 */     if (this.channel != null) {
/* 340 */       this.channel.close();
/*     */     }
/*     */     
/* 343 */     this.fd.closeAll(new Closeable() {
/*     */       public void close() throws IOException {
/* 345 */         FileOutputStream.this.close0();
/*     */       }
/*     */     });
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
/*     */   public final FileDescriptor getFD()
/*     */     throws IOException
/*     */   {
/* 361 */     if (this.fd != null) {
/* 362 */       return this.fd;
/*     */     }
/* 364 */     throw new IOException();
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
/*     */   public FileChannel getChannel()
/*     */   {
/* 385 */     synchronized (this) {
/* 386 */       if (this.channel == null) {
/* 387 */         this.channel = FileChannelImpl.open(this.fd, this.path, false, true, this.append, this);
/*     */       }
/* 389 */       return this.channel;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws IOException
/*     */   {
/* 402 */     if (this.fd != null) {
/* 403 */       if ((this.fd == FileDescriptor.out) || (this.fd == FileDescriptor.err)) {
/* 404 */         flush();
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 411 */         close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private native void close0()
/*     */     throws IOException;
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   static {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/FileOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */