/*     */ package java.util.logging;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.GregorianCalendar;
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
/*     */ public class XMLFormatter
/*     */   extends Formatter
/*     */ {
/*  47 */   private LogManager manager = LogManager.getLogManager();
/*     */   
/*     */   private void a2(StringBuilder paramStringBuilder, int paramInt)
/*     */   {
/*  51 */     if (paramInt < 10) {
/*  52 */       paramStringBuilder.append('0');
/*     */     }
/*  54 */     paramStringBuilder.append(paramInt);
/*     */   }
/*     */   
/*     */   private void appendISO8601(StringBuilder paramStringBuilder, long paramLong)
/*     */   {
/*  59 */     GregorianCalendar localGregorianCalendar = new GregorianCalendar();
/*  60 */     localGregorianCalendar.setTimeInMillis(paramLong);
/*  61 */     paramStringBuilder.append(localGregorianCalendar.get(1));
/*  62 */     paramStringBuilder.append('-');
/*  63 */     a2(paramStringBuilder, localGregorianCalendar.get(2) + 1);
/*  64 */     paramStringBuilder.append('-');
/*  65 */     a2(paramStringBuilder, localGregorianCalendar.get(5));
/*  66 */     paramStringBuilder.append('T');
/*  67 */     a2(paramStringBuilder, localGregorianCalendar.get(11));
/*  68 */     paramStringBuilder.append(':');
/*  69 */     a2(paramStringBuilder, localGregorianCalendar.get(12));
/*  70 */     paramStringBuilder.append(':');
/*  71 */     a2(paramStringBuilder, localGregorianCalendar.get(13));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void escape(StringBuilder paramStringBuilder, String paramString)
/*     */   {
/*  78 */     if (paramString == null) {
/*  79 */       paramString = "<null>";
/*     */     }
/*  81 */     for (int i = 0; i < paramString.length(); i++) {
/*  82 */       char c = paramString.charAt(i);
/*  83 */       if (c == '<') {
/*  84 */         paramStringBuilder.append("&lt;");
/*  85 */       } else if (c == '>') {
/*  86 */         paramStringBuilder.append("&gt;");
/*  87 */       } else if (c == '&') {
/*  88 */         paramStringBuilder.append("&amp;");
/*     */       } else {
/*  90 */         paramStringBuilder.append(c);
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
/*     */   public String format(LogRecord paramLogRecord)
/*     */   {
/* 106 */     StringBuilder localStringBuilder = new StringBuilder(500);
/* 107 */     localStringBuilder.append("<record>\n");
/*     */     
/* 109 */     localStringBuilder.append("  <date>");
/* 110 */     appendISO8601(localStringBuilder, paramLogRecord.getMillis());
/* 111 */     localStringBuilder.append("</date>\n");
/*     */     
/* 113 */     localStringBuilder.append("  <millis>");
/* 114 */     localStringBuilder.append(paramLogRecord.getMillis());
/* 115 */     localStringBuilder.append("</millis>\n");
/*     */     
/* 117 */     localStringBuilder.append("  <sequence>");
/* 118 */     localStringBuilder.append(paramLogRecord.getSequenceNumber());
/* 119 */     localStringBuilder.append("</sequence>\n");
/*     */     
/* 121 */     String str = paramLogRecord.getLoggerName();
/* 122 */     if (str != null) {
/* 123 */       localStringBuilder.append("  <logger>");
/* 124 */       escape(localStringBuilder, str);
/* 125 */       localStringBuilder.append("</logger>\n");
/*     */     }
/*     */     
/* 128 */     localStringBuilder.append("  <level>");
/* 129 */     escape(localStringBuilder, paramLogRecord.getLevel().toString());
/* 130 */     localStringBuilder.append("</level>\n");
/*     */     
/* 132 */     if (paramLogRecord.getSourceClassName() != null) {
/* 133 */       localStringBuilder.append("  <class>");
/* 134 */       escape(localStringBuilder, paramLogRecord.getSourceClassName());
/* 135 */       localStringBuilder.append("</class>\n");
/*     */     }
/*     */     
/* 138 */     if (paramLogRecord.getSourceMethodName() != null) {
/* 139 */       localStringBuilder.append("  <method>");
/* 140 */       escape(localStringBuilder, paramLogRecord.getSourceMethodName());
/* 141 */       localStringBuilder.append("</method>\n");
/*     */     }
/*     */     
/* 144 */     localStringBuilder.append("  <thread>");
/* 145 */     localStringBuilder.append(paramLogRecord.getThreadID());
/* 146 */     localStringBuilder.append("</thread>\n");
/*     */     
/* 148 */     if (paramLogRecord.getMessage() != null)
/*     */     {
/* 150 */       localObject = formatMessage(paramLogRecord);
/* 151 */       localStringBuilder.append("  <message>");
/* 152 */       escape(localStringBuilder, (String)localObject);
/* 153 */       localStringBuilder.append("</message>");
/* 154 */       localStringBuilder.append("\n");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 159 */     Object localObject = paramLogRecord.getResourceBundle();
/*     */     try {
/* 161 */       if ((localObject != null) && (((ResourceBundle)localObject).getString(paramLogRecord.getMessage()) != null)) {
/* 162 */         localStringBuilder.append("  <key>");
/* 163 */         escape(localStringBuilder, paramLogRecord.getMessage());
/* 164 */         localStringBuilder.append("</key>\n");
/* 165 */         localStringBuilder.append("  <catalog>");
/* 166 */         escape(localStringBuilder, paramLogRecord.getResourceBundleName());
/* 167 */         localStringBuilder.append("</catalog>\n");
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */     
/*     */ 
/* 173 */     Object[] arrayOfObject = paramLogRecord.getParameters();
/*     */     
/*     */ 
/* 176 */     if ((arrayOfObject != null) && (arrayOfObject.length != 0) && 
/* 177 */       (paramLogRecord.getMessage().indexOf("{") == -1)) {
/* 178 */       for (int i = 0; i < arrayOfObject.length; i++) {
/* 179 */         localStringBuilder.append("  <param>");
/*     */         try {
/* 181 */           escape(localStringBuilder, arrayOfObject[i].toString());
/*     */         } catch (Exception localException2) {
/* 183 */           localStringBuilder.append("???");
/*     */         }
/* 185 */         localStringBuilder.append("</param>\n");
/*     */       }
/*     */     }
/*     */     
/* 189 */     if (paramLogRecord.getThrown() != null)
/*     */     {
/* 191 */       Throwable localThrowable = paramLogRecord.getThrown();
/* 192 */       localStringBuilder.append("  <exception>\n");
/* 193 */       localStringBuilder.append("    <message>");
/* 194 */       escape(localStringBuilder, localThrowable.toString());
/* 195 */       localStringBuilder.append("</message>\n");
/* 196 */       StackTraceElement[] arrayOfStackTraceElement = localThrowable.getStackTrace();
/* 197 */       for (int j = 0; j < arrayOfStackTraceElement.length; j++) {
/* 198 */         StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
/* 199 */         localStringBuilder.append("    <frame>\n");
/* 200 */         localStringBuilder.append("      <class>");
/* 201 */         escape(localStringBuilder, localStackTraceElement.getClassName());
/* 202 */         localStringBuilder.append("</class>\n");
/* 203 */         localStringBuilder.append("      <method>");
/* 204 */         escape(localStringBuilder, localStackTraceElement.getMethodName());
/* 205 */         localStringBuilder.append("</method>\n");
/*     */         
/* 207 */         if (localStackTraceElement.getLineNumber() >= 0) {
/* 208 */           localStringBuilder.append("      <line>");
/* 209 */           localStringBuilder.append(localStackTraceElement.getLineNumber());
/* 210 */           localStringBuilder.append("</line>\n");
/*     */         }
/* 212 */         localStringBuilder.append("    </frame>\n");
/*     */       }
/* 214 */       localStringBuilder.append("  </exception>\n");
/*     */     }
/*     */     
/* 217 */     localStringBuilder.append("</record>\n");
/* 218 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getHead(Handler paramHandler)
/*     */   {
/* 228 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 230 */     localStringBuilder.append("<?xml version=\"1.0\"");
/*     */     String str;
/* 232 */     if (paramHandler != null) {
/* 233 */       str = paramHandler.getEncoding();
/*     */     } else {
/* 235 */       str = null;
/*     */     }
/*     */     
/* 238 */     if (str == null)
/*     */     {
/* 240 */       str = Charset.defaultCharset().name();
/*     */     }
/*     */     try
/*     */     {
/* 244 */       Charset localCharset = Charset.forName(str);
/* 245 */       str = localCharset.name();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*     */ 
/*     */ 
/* 251 */     localStringBuilder.append(" encoding=\"");
/* 252 */     localStringBuilder.append(str);
/* 253 */     localStringBuilder.append("\"");
/* 254 */     localStringBuilder.append(" standalone=\"no\"?>\n");
/* 255 */     localStringBuilder.append("<!DOCTYPE log SYSTEM \"logger.dtd\">\n");
/* 256 */     localStringBuilder.append("<log>\n");
/* 257 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTail(Handler paramHandler)
/*     */   {
/* 267 */     return "</log>\n";
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/logging/XMLFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */