/*     */ package java.io;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileDescriptor
/*     */ {
/*     */   private int fd;
/*     */   private Closeable parent;
/*     */   private List<Closeable> otherParents;
/*     */   private boolean closed;
/*     */   
/*     */   public FileDescriptor()
/*     */   {
/*  59 */     this.fd = -1;
/*     */   }
/*     */   
/*     */   private FileDescriptor(int paramInt) {
/*  63 */     this.fd = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  73 */   public static final FileDescriptor in = new FileDescriptor(0);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  81 */   public static final FileDescriptor out = new FileDescriptor(1);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  90 */   public static final FileDescriptor err = new FileDescriptor(2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean valid()
/*     */   {
/* 100 */     return this.fd != -1;
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
/*     */   static
/*     */   {
/* 137 */     initIDs();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 142 */     SharedSecrets.setJavaIOFileDescriptorAccess(new JavaIOFileDescriptorAccess()
/*     */     {
/*     */       public void set(FileDescriptor paramAnonymousFileDescriptor, int paramAnonymousInt) {
/* 145 */         paramAnonymousFileDescriptor.fd = paramAnonymousInt;
/*     */       }
/*     */       
/*     */       public int get(FileDescriptor paramAnonymousFileDescriptor) {
/* 149 */         return paramAnonymousFileDescriptor.fd;
/*     */       }
/*     */       
/*     */       public void setHandle(FileDescriptor paramAnonymousFileDescriptor, long paramAnonymousLong) {
/* 153 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public long getHandle(FileDescriptor paramAnonymousFileDescriptor) {
/* 157 */         throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   synchronized void attach(Closeable paramCloseable)
/*     */   {
/* 175 */     if (this.parent == null)
/*     */     {
/* 177 */       this.parent = paramCloseable;
/* 178 */     } else if (this.otherParents == null) {
/* 179 */       this.otherParents = new ArrayList();
/* 180 */       this.otherParents.add(this.parent);
/* 181 */       this.otherParents.add(paramCloseable);
/*     */     } else {
/* 183 */       this.otherParents.add(paramCloseable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   synchronized void closeAll(Closeable paramCloseable)
/*     */     throws IOException
/*     */   {
/* 195 */     if (!this.closed) {
/* 196 */       this.closed = true;
/* 197 */       Object localObject1 = null;
/* 198 */       try { Closeable localCloseable1 = paramCloseable;Object localObject2 = null;
/* 199 */         try { if (this.otherParents != null) {
/* 200 */             for (Closeable localCloseable2 : this.otherParents) {
/*     */               try {
/* 202 */                 localCloseable2.close();
/*     */               } catch (IOException localIOException2) {
/* 204 */                 if (localObject1 == null) {
/* 205 */                   localObject1 = localIOException2;
/*     */                 } else {
/* 207 */                   ((IOException)localObject1).addSuppressed(localIOException2);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Throwable localThrowable2)
/*     */         {
/* 198 */           localObject2 = localThrowable2;throw localThrowable2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 212 */           if (localCloseable1 != null) if (localObject2 != null) try { localCloseable1.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject2).addSuppressed(localThrowable3); } else localCloseable1.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/* 217 */         if (localObject1 != null)
/* 218 */           localIOException1.addSuppressed((Throwable)localObject1);
/* 219 */         localObject1 = localIOException1;
/*     */       } finally {
/* 221 */         if (localObject1 != null) {
/* 222 */           throw ((Throwable)localObject1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public native void sync()
/*     */     throws SyncFailedException;
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/FileDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */