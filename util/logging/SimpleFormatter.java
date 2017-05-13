/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.Date;
/*     */ import sun.util.logging.LoggingSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleFormatter
/*     */   extends Formatter
/*     */ {
/*  62 */   private static final String format = ;
/*  63 */   private final Date dat = new Date();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized String format(LogRecord paramLogRecord)
/*     */   {
/* 141 */     this.dat.setTime(paramLogRecord.getMillis());
/*     */     String str1;
/* 143 */     if (paramLogRecord.getSourceClassName() != null) {
/* 144 */       str1 = paramLogRecord.getSourceClassName();
/* 145 */       if (paramLogRecord.getSourceMethodName() != null) {
/* 146 */         str1 = str1 + " " + paramLogRecord.getSourceMethodName();
/*     */       }
/*     */     } else {
/* 149 */       str1 = paramLogRecord.getLoggerName();
/*     */     }
/* 151 */     String str2 = formatMessage(paramLogRecord);
/* 152 */     String str3 = "";
/* 153 */     if (paramLogRecord.getThrown() != null) {
/* 154 */       StringWriter localStringWriter = new StringWriter();
/* 155 */       PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/* 156 */       localPrintWriter.println();
/* 157 */       paramLogRecord.getThrown().printStackTrace(localPrintWriter);
/* 158 */       localPrintWriter.close();
/* 159 */       str3 = localStringWriter.toString();
/*     */     }
/* 161 */     return String.format(format, new Object[] { this.dat, str1, paramLogRecord
/*     */     
/*     */ 
/* 164 */       .getLoggerName(), paramLogRecord
/* 165 */       .getLevel().getLocalizedLevelName(), str2, str3 });
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/SimpleFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */