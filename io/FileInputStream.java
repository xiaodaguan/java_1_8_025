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
/*     */ public class FileInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private final FileDescriptor fd;
/*     */   private final String path;
/*  60 */   private FileChannel channel = null;
/*     */   
/*  62 */   private final Object closeLock = new Object();
/*  63 */   private volatile boolean closed = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileInputStream(String paramString)
/*     */     throws FileNotFoundException
/*     */   {
/*  93 */     this(paramString != null ? new File(paramString) : null);
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
/*     */   public FileInputStream(File paramFile)
/*     */     throws FileNotFoundException
/*     */   {
/* 124 */     String str = paramFile != null ? paramFile.getPath() : null;
/* 125 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 126 */     if (localSecurityManager != null) {
/* 127 */       localSecurityManager.checkRead(str);
/*     */     }
/* 129 */     if (str == null) {
/* 130 */       throw new NullPointerException();
/*     */     }
/* 132 */     if (paramFile.isInvalid()) {
/* 133 */       throw new FileNotFoundException("Invalid file path");
/*     */     }
/* 135 */     this.fd = new FileDescriptor();
/* 136 */     this.fd.attach(this);
/* 137 */     this.path = str;
/* 138 */     open(str);
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
/*     */   public FileInputStream(FileDescriptor paramFileDescriptor)
/*     */   {
/* 166 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 167 */     if (paramFileDescriptor == null) {
/* 168 */       throw new NullPointerException();
/*     */     }
/* 170 */     if (localSecurityManager != null) {
/* 171 */       localSecurityManager.checkRead(paramFileDescriptor);
/*     */     }
/* 173 */     this.fd = paramFileDescriptor;
/* 174 */     this.path = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 180 */     this.fd.attach(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native void open(String paramString)
/*     */     throws FileNotFoundException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 198 */     return read0();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native int read0()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private native int readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 224 */     return readBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 246 */     return readBytes(paramArrayOfByte, paramInt1, paramInt2);
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
/*     */   public native long skip(long paramLong)
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public native int available()
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 307 */     synchronized (this.closeLock) {
/* 308 */       if (this.closed) {
/* 309 */         return;
/*     */       }
/* 311 */       this.closed = true;
/*     */     }
/* 313 */     if (this.channel != null) {
/* 314 */       this.channel.close();
/*     */     }
/*     */     
/* 317 */     this.fd.closeAll(new Closeable() {
/*     */       public void close() throws IOException {
/* 319 */         FileInputStream.this.close0();
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
/* 335 */     if (this.fd != null) {
/* 336 */       return this.fd;
/*     */     }
/* 338 */     throw new IOException();
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
/*     */   public FileChannel getChannel()
/*     */   {
/* 358 */     synchronized (this) {
/* 359 */       if (this.channel == null) {
/* 360 */         this.channel = FileChannelImpl.open(this.fd, this.path, true, false, this);
/*     */       }
/* 362 */       return this.channel;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static native void initIDs();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private native void close0()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws IOException
/*     */   {
/* 382 */     if ((this.fd != null) && (this.fd != FileDescriptor.in))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 388 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */   static {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/FileInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */