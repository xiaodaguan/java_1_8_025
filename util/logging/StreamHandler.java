/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamHandler
/*     */   extends Handler
/*     */ {
/*     */   private OutputStream output;
/*     */   private boolean doneHeader;
/*     */   private volatile Writer writer;
/*     */   
/*     */   private void configure()
/*     */   {
/*  84 */     LogManager localLogManager = LogManager.getLogManager();
/*  85 */     String str = getClass().getName();
/*     */     
/*  87 */     setLevel(localLogManager.getLevelProperty(str + ".level", Level.INFO));
/*  88 */     setFilter(localLogManager.getFilterProperty(str + ".filter", null));
/*  89 */     setFormatter(localLogManager.getFormatterProperty(str + ".formatter", new SimpleFormatter()));
/*     */     try {
/*  91 */       setEncoding(localLogManager.getStringProperty(str + ".encoding", null));
/*     */     } catch (Exception localException1) {
/*     */       try {
/*  94 */         setEncoding(null);
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StreamHandler()
/*     */   {
/* 106 */     this.sealed = false;
/* 107 */     configure();
/* 108 */     this.sealed = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StreamHandler(OutputStream paramOutputStream, Formatter paramFormatter)
/*     */   {
/* 119 */     this.sealed = false;
/* 120 */     configure();
/* 121 */     setFormatter(paramFormatter);
/* 122 */     setOutputStream(paramOutputStream);
/* 123 */     this.sealed = true;
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
/*     */   protected synchronized void setOutputStream(OutputStream paramOutputStream)
/*     */     throws SecurityException
/*     */   {
/* 138 */     if (paramOutputStream == null) {
/* 139 */       throw new NullPointerException();
/*     */     }
/* 141 */     flushAndClose();
/* 142 */     this.output = paramOutputStream;
/* 143 */     this.doneHeader = false;
/* 144 */     String str = getEncoding();
/* 145 */     if (str == null) {
/* 146 */       this.writer = new OutputStreamWriter(this.output);
/*     */     } else {
/*     */       try {
/* 149 */         this.writer = new OutputStreamWriter(this.output, str);
/*     */       }
/*     */       catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*     */       {
/* 153 */         throw new Error("Unexpected exception " + localUnsupportedEncodingException);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setEncoding(String paramString)
/*     */     throws SecurityException, UnsupportedEncodingException
/*     */   {
/* 174 */     super.setEncoding(paramString);
/* 175 */     if (this.output == null) {
/* 176 */       return;
/*     */     }
/*     */     
/* 179 */     flush();
/* 180 */     if (paramString == null) {
/* 181 */       this.writer = new OutputStreamWriter(this.output);
/*     */     } else {
/* 183 */       this.writer = new OutputStreamWriter(this.output, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void publish(LogRecord paramLogRecord)
/*     */   {
/* 206 */     if (!isLoggable(paramLogRecord)) {
/*     */       return;
/*     */     }
/*     */     String str;
/*     */     try {
/* 211 */       str = getFormatter().format(paramLogRecord);
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/* 215 */       reportError(null, localException1, 5);
/* 216 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 220 */       if (!this.doneHeader) {
/* 221 */         this.writer.write(getFormatter().getHead(this));
/* 222 */         this.doneHeader = true;
/*     */       }
/* 224 */       this.writer.write(str);
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/* 228 */       reportError(null, localException2, 1);
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
/*     */ 
/*     */ 
/*     */   public boolean isLoggable(LogRecord paramLogRecord)
/*     */   {
/* 246 */     if ((this.writer == null) || (paramLogRecord == null)) {
/* 247 */       return false;
/*     */     }
/* 249 */     return super.isLoggable(paramLogRecord);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void flush()
/*     */   {
/* 257 */     if (this.writer != null) {
/*     */       try {
/* 259 */         this.writer.flush();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 263 */         reportError(null, localException, 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void flushAndClose() throws SecurityException {
/* 269 */     checkPermission();
/* 270 */     if (this.writer != null) {
/*     */       try {
/* 272 */         if (!this.doneHeader) {
/* 273 */           this.writer.write(getFormatter().getHead(this));
/* 274 */           this.doneHeader = true;
/*     */         }
/* 276 */         this.writer.write(getFormatter().getTail(this));
/* 277 */         this.writer.flush();
/* 278 */         this.writer.close();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 282 */         reportError(null, localException, 3);
/*     */       }
/* 284 */       this.writer = null;
/* 285 */       this.output = null;
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
/*     */   public synchronized void close()
/*     */     throws SecurityException
/*     */   {
/* 302 */     flushAndClose();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/StreamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */