/*     */ package java.util.logging;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConsoleHandler
/*     */   extends StreamHandler
/*     */ {
/*     */   private void configure()
/*     */   {
/*  73 */     LogManager localLogManager = LogManager.getLogManager();
/*  74 */     String str = getClass().getName();
/*     */     
/*  76 */     setLevel(localLogManager.getLevelProperty(str + ".level", Level.INFO));
/*  77 */     setFilter(localLogManager.getFilterProperty(str + ".filter", null));
/*  78 */     setFormatter(localLogManager.getFormatterProperty(str + ".formatter", new SimpleFormatter()));
/*     */     try {
/*  80 */       setEncoding(localLogManager.getStringProperty(str + ".encoding", null));
/*     */     } catch (Exception localException1) {
/*     */       try {
/*  83 */         setEncoding(null);
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
/*     */   public ConsoleHandler()
/*     */   {
/*  99 */     this.sealed = false;
/* 100 */     configure();
/* 101 */     setOutputStream(System.err);
/* 102 */     this.sealed = true;
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
/*     */   public void publish(LogRecord paramLogRecord)
/*     */   {
/* 116 */     super.publish(paramLogRecord);
/* 117 */     flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */   {
/* 127 */     flush();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/ConsoleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */