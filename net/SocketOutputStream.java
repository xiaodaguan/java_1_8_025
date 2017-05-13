/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import sun.net.ConnectionResetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SocketOutputStream
/*     */   extends FileOutputStream
/*     */ {
/*  47 */   private AbstractPlainSocketImpl impl = null;
/*  48 */   private byte[] temp = new byte[1];
/*  49 */   private Socket socket = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   SocketOutputStream(AbstractPlainSocketImpl paramAbstractPlainSocketImpl)
/*     */     throws IOException
/*     */   {
/*  58 */     super(paramAbstractPlainSocketImpl.getFileDescriptor());
/*  59 */     this.impl = paramAbstractPlainSocketImpl;
/*  60 */     this.socket = paramAbstractPlainSocketImpl.getSocket();
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
/*     */   public final FileChannel getChannel()
/*     */   {
/*  76 */     return null;
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
/*     */   private native void socketWrite0(FileDescriptor paramFileDescriptor, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void socketWrite(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 100 */     if ((paramInt2 <= 0) || (paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length)) {
/* 101 */       if (paramInt2 == 0) {
/* 102 */         return;
/*     */       }
/* 104 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/*     */     
/* 107 */     FileDescriptor localFileDescriptor = this.impl.acquireFD();
/*     */     try {
/* 109 */       socketWrite0(localFileDescriptor, paramArrayOfByte, paramInt1, paramInt2);
/*     */     } catch (SocketException localSocketException1) { SocketException localSocketException2;
/* 111 */       if ((localSocketException1 instanceof ConnectionResetException)) {
/* 112 */         this.impl.setConnectionResetPending();
/* 113 */         localSocketException2 = new SocketException("Connection reset");
/*     */       }
/* 115 */       if (this.impl.isClosedOrPending()) {
/* 116 */         throw new SocketException("Socket closed");
/*     */       }
/* 118 */       throw localSocketException2;
/*     */     }
/*     */     finally {
/* 121 */       this.impl.releaseFD();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 131 */     this.temp[0] = ((byte)paramInt);
/* 132 */     socketWrite(this.temp, 0, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 141 */     socketWrite(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
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
/* 153 */     socketWrite(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 159 */   private boolean closing = false;
/*     */   
/*     */   public void close() throws IOException {
/* 162 */     if (this.closing)
/* 163 */       return;
/* 164 */     this.closing = true;
/* 165 */     if (this.socket != null) {
/* 166 */       if (!this.socket.isClosed())
/* 167 */         this.socket.close();
/*     */     } else
/* 169 */       this.impl.close();
/* 170 */     this.closing = false;
/*     */   }
/*     */   
/*     */   protected void finalize() {}
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   static {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/SocketOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */