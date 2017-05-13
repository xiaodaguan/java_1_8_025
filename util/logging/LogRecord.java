/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import sun.misc.JavaLangAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogRecord
/*     */   implements Serializable
/*     */ {
/*  72 */   private static final AtomicLong globalSequenceNumber = new AtomicLong(0L);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int MIN_SEQUENTIAL_THREAD_ID = 1073741823;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */   private static final AtomicInteger nextThreadId = new AtomicInteger(1073741823);
/*     */   
/*     */ 
/*  88 */   private static final ThreadLocal<Integer> threadIds = new ThreadLocal();
/*     */   
/*     */ 
/*     */ 
/*     */   private Level level;
/*     */   
/*     */ 
/*     */ 
/*     */   private long sequenceNumber;
/*     */   
/*     */ 
/*     */ 
/*     */   private String sourceClassName;
/*     */   
/*     */ 
/*     */ 
/*     */   private String sourceMethodName;
/*     */   
/*     */ 
/*     */ 
/*     */   private String message;
/*     */   
/*     */ 
/*     */ 
/*     */   private int threadID;
/*     */   
/*     */ 
/*     */ 
/*     */   private long millis;
/*     */   
/*     */ 
/*     */ 
/*     */   private Throwable thrown;
/*     */   
/*     */ 
/*     */ 
/*     */   private String loggerName;
/*     */   
/*     */ 
/*     */ 
/*     */   private String resourceBundleName;
/*     */   
/*     */ 
/*     */ 
/*     */   private transient boolean needToInferCaller;
/*     */   
/*     */ 
/*     */ 
/*     */   private transient Object[] parameters;
/*     */   
/*     */ 
/*     */   private transient ResourceBundle resourceBundle;
/*     */   
/*     */ 
/*     */   private static final long serialVersionUID = 5372048053134512534L;
/*     */   
/*     */ 
/*     */ 
/*     */   private int defaultThreadID()
/*     */   {
/* 148 */     long l = Thread.currentThread().getId();
/* 149 */     if (l < 1073741823L) {
/* 150 */       return (int)l;
/*     */     }
/* 152 */     Integer localInteger = (Integer)threadIds.get();
/* 153 */     if (localInteger == null) {
/* 154 */       localInteger = Integer.valueOf(nextThreadId.getAndIncrement());
/* 155 */       threadIds.set(localInteger);
/*     */     }
/* 157 */     return localInteger.intValue();
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
/*     */   public LogRecord(Level paramLevel, String paramString)
/*     */   {
/* 179 */     paramLevel.getClass();
/* 180 */     this.level = paramLevel;
/* 181 */     this.message = paramString;
/*     */     
/* 183 */     this.sequenceNumber = globalSequenceNumber.getAndIncrement();
/* 184 */     this.threadID = defaultThreadID();
/* 185 */     this.millis = System.currentTimeMillis();
/* 186 */     this.needToInferCaller = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLoggerName()
/*     */   {
/* 195 */     return this.loggerName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLoggerName(String paramString)
/*     */   {
/* 204 */     this.loggerName = paramString;
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
/*     */   public ResourceBundle getResourceBundle()
/*     */   {
/* 217 */     return this.resourceBundle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setResourceBundle(ResourceBundle paramResourceBundle)
/*     */   {
/* 226 */     this.resourceBundle = paramResourceBundle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getResourceBundleName()
/*     */   {
/* 238 */     return this.resourceBundleName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setResourceBundleName(String paramString)
/*     */   {
/* 247 */     this.resourceBundleName = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Level getLevel()
/*     */   {
/* 255 */     return this.level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevel(Level paramLevel)
/*     */   {
/* 263 */     if (paramLevel == null) {
/* 264 */       throw new NullPointerException();
/*     */     }
/* 266 */     this.level = paramLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getSequenceNumber()
/*     */   {
/* 278 */     return this.sequenceNumber;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSequenceNumber(long paramLong)
/*     */   {
/* 289 */     this.sequenceNumber = paramLong;
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
/*     */   public String getSourceClassName()
/*     */   {
/* 307 */     if (this.needToInferCaller) {
/* 308 */       inferCaller();
/*     */     }
/* 310 */     return this.sourceClassName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSourceClassName(String paramString)
/*     */   {
/* 319 */     this.sourceClassName = paramString;
/* 320 */     this.needToInferCaller = false;
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
/*     */   public String getSourceMethodName()
/*     */   {
/* 338 */     if (this.needToInferCaller) {
/* 339 */       inferCaller();
/*     */     }
/* 341 */     return this.sourceMethodName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSourceMethodName(String paramString)
/*     */   {
/* 350 */     this.sourceMethodName = paramString;
/* 351 */     this.needToInferCaller = false;
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
/*     */   public String getMessage()
/*     */   {
/* 369 */     return this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMessage(String paramString)
/*     */   {
/* 378 */     this.message = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/* 388 */     return this.parameters;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParameters(Object[] paramArrayOfObject)
/*     */   {
/* 397 */     this.parameters = paramArrayOfObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getThreadID()
/*     */   {
/* 409 */     return this.threadID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThreadID(int paramInt)
/*     */   {
/* 417 */     this.threadID = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getMillis()
/*     */   {
/* 426 */     return this.millis;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMillis(long paramLong)
/*     */   {
/* 435 */     this.millis = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrown()
/*     */   {
/* 447 */     return this.thrown;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThrown(Throwable paramThrowable)
/*     */   {
/* 456 */     this.thrown = paramThrowable;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 473 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/*     */ 
/* 476 */     paramObjectOutputStream.writeByte(1);
/* 477 */     paramObjectOutputStream.writeByte(0);
/* 478 */     if (this.parameters == null) {
/* 479 */       paramObjectOutputStream.writeInt(-1);
/* 480 */       return;
/*     */     }
/* 482 */     paramObjectOutputStream.writeInt(this.parameters.length);
/*     */     
/* 484 */     for (int i = 0; i < this.parameters.length; i++) {
/* 485 */       if (this.parameters[i] == null) {
/* 486 */         paramObjectOutputStream.writeObject(null);
/*     */       } else {
/* 488 */         paramObjectOutputStream.writeObject(this.parameters[i].toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 496 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 499 */     int i = paramObjectInputStream.readByte();
/* 500 */     int j = paramObjectInputStream.readByte();
/* 501 */     if (i != 1) {
/* 502 */       throw new IOException("LogRecord: bad version: " + i + "." + j);
/*     */     }
/* 504 */     int k = paramObjectInputStream.readInt();
/* 505 */     if (k == -1) {
/* 506 */       this.parameters = null;
/*     */     } else {
/* 508 */       this.parameters = new Object[k];
/* 509 */       for (int m = 0; m < this.parameters.length; m++) {
/* 510 */         this.parameters[m] = paramObjectInputStream.readObject();
/*     */       }
/*     */     }
/*     */     
/* 514 */     if (this.resourceBundleName != null)
/*     */     {
/*     */       try
/*     */       {
/*     */ 
/* 519 */         ResourceBundle localResourceBundle = ResourceBundle.getBundle(this.resourceBundleName, 
/* 520 */           Locale.getDefault(), 
/* 521 */           ClassLoader.getSystemClassLoader());
/* 522 */         this.resourceBundle = localResourceBundle;
/*     */       }
/*     */       catch (MissingResourceException localMissingResourceException)
/*     */       {
/* 526 */         this.resourceBundle = null;
/*     */       }
/*     */     }
/*     */     
/* 530 */     this.needToInferCaller = false;
/*     */   }
/*     */   
/*     */   private void inferCaller()
/*     */   {
/* 535 */     this.needToInferCaller = false;
/* 536 */     JavaLangAccess localJavaLangAccess = SharedSecrets.getJavaLangAccess();
/* 537 */     Throwable localThrowable = new Throwable();
/* 538 */     int i = localJavaLangAccess.getStackTraceDepth(localThrowable);
/*     */     
/* 540 */     int j = 1;
/* 541 */     for (int k = 0; k < i; k++)
/*     */     {
/*     */ 
/*     */ 
/* 545 */       StackTraceElement localStackTraceElement = localJavaLangAccess.getStackTraceElement(localThrowable, k);
/* 546 */       String str = localStackTraceElement.getClassName();
/* 547 */       boolean bool = isLoggerImplFrame(str);
/* 548 */       if (j != 0)
/*     */       {
/* 550 */         if (bool) {
/* 551 */           j = 0;
/*     */         }
/*     */       }
/* 554 */       else if (!bool)
/*     */       {
/* 556 */         if ((!str.startsWith("java.lang.reflect.")) && (!str.startsWith("sun.reflect.")))
/*     */         {
/* 558 */           setSourceClassName(str);
/* 559 */           setSourceMethodName(localStackTraceElement.getMethodName());
/* 560 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isLoggerImplFrame(String paramString)
/*     */   {
/* 573 */     return (paramString.equals("java.util.logging.Logger")) || (paramString.startsWith("java.util.logging.LoggingProxyImpl")) || (paramString.startsWith("sun.util.logging."));
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/LogRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */