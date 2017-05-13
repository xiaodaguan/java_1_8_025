/*     */ package java.util.logging;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Formatter
/*     */ {
/*     */   public abstract String format(LogRecord paramLogRecord);
/*     */   
/*     */   public String getHead(Handler paramHandler)
/*     */   {
/*  75 */     return "";
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
/*     */   public String getTail(Handler paramHandler)
/*     */   {
/*  88 */     return "";
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
/*     */   public synchronized String formatMessage(LogRecord paramLogRecord)
/*     */   {
/* 114 */     String str = paramLogRecord.getMessage();
/* 115 */     ResourceBundle localResourceBundle = paramLogRecord.getResourceBundle();
/* 116 */     if (localResourceBundle != null) {
/*     */       try {
/* 118 */         str = localResourceBundle.getString(paramLogRecord.getMessage());
/*     */       }
/*     */       catch (MissingResourceException localMissingResourceException) {
/* 121 */         str = paramLogRecord.getMessage();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 126 */       Object[] arrayOfObject = paramLogRecord.getParameters();
/* 127 */       if ((arrayOfObject == null) || (arrayOfObject.length == 0))
/*     */       {
/* 129 */         return str;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */       if ((str.indexOf("{0") >= 0) || (str.indexOf("{1") >= 0) || 
/* 137 */         (str.indexOf("{2") >= 0) || (str.indexOf("{3") >= 0)) {
/* 138 */         return MessageFormat.format(str, arrayOfObject);
/*     */       }
/* 140 */       return str;
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 144 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/Formatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */