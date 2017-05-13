/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.Socket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketHandler
/*     */   extends StreamHandler
/*     */ {
/*     */   private Socket sock;
/*     */   private String host;
/*     */   private int port;
/*     */   
/*     */   private void configure()
/*     */   {
/*  90 */     LogManager localLogManager = LogManager.getLogManager();
/*  91 */     String str = getClass().getName();
/*     */     
/*  93 */     setLevel(localLogManager.getLevelProperty(str + ".level", Level.ALL));
/*  94 */     setFilter(localLogManager.getFilterProperty(str + ".filter", null));
/*  95 */     setFormatter(localLogManager.getFormatterProperty(str + ".formatter", new XMLFormatter()));
/*     */     try {
/*  97 */       setEncoding(localLogManager.getStringProperty(str + ".encoding", null));
/*     */     } catch (Exception localException1) {
/*     */       try {
/* 100 */         setEncoding(null);
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */     
/*     */ 
/* 106 */     this.port = localLogManager.getIntProperty(str + ".port", 0);
/* 107 */     this.host = localLogManager.getStringProperty(str + ".host", null);
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
/*     */   public SocketHandler()
/*     */     throws IOException
/*     */   {
/* 121 */     this.sealed = false;
/* 122 */     configure();
/*     */     try
/*     */     {
/* 125 */       connect();
/*     */     } catch (IOException localIOException) {
/* 127 */       System.err.println("SocketHandler: connect failed to " + this.host + ":" + this.port);
/* 128 */       throw localIOException;
/*     */     }
/* 130 */     this.sealed = true;
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
/*     */   public SocketHandler(String paramString, int paramInt)
/*     */     throws IOException
/*     */   {
/* 149 */     this.sealed = false;
/* 150 */     configure();
/* 151 */     this.sealed = true;
/* 152 */     this.port = paramInt;
/* 153 */     this.host = paramString;
/* 154 */     connect();
/*     */   }
/*     */   
/*     */   private void connect() throws IOException
/*     */   {
/* 159 */     if (this.port == 0) {
/* 160 */       throw new IllegalArgumentException("Bad port: " + this.port);
/*     */     }
/* 162 */     if (this.host == null) {
/* 163 */       throw new IllegalArgumentException("Null host name: " + this.host);
/*     */     }
/*     */     
/*     */ 
/* 167 */     this.sock = new Socket(this.host, this.port);
/* 168 */     OutputStream localOutputStream = this.sock.getOutputStream();
/* 169 */     BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localOutputStream);
/* 170 */     setOutputStream(localBufferedOutputStream);
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
/* 181 */     super.close();
/* 182 */     if (this.sock != null) {
/*     */       try {
/* 184 */         this.sock.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */     
/* 189 */     this.sock = null;
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
/* 200 */     if (!isLoggable(paramLogRecord)) {
/* 201 */       return;
/*     */     }
/* 203 */     super.publish(paramLogRecord);
/* 204 */     flush();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/SocketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */