/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Objects;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProxyClassesDumper
/*     */ {
/*  47 */   private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */   
/*     */ 
/*     */ 
/*  51 */   private static final char[] BAD_CHARS = { '\\', ':', '*', '?', '"', '<', '>', '|' };
/*     */   
/*     */ 
/*  54 */   private static final String[] REPLACEMENT = { "%5C", "%3A", "%2A", "%3F", "%22", "%3C", "%3E", "%7C" };
/*     */   
/*     */   private final Path dumpDir;
/*     */   
/*     */ 
/*     */   public static ProxyClassesDumper getInstance(String paramString)
/*     */   {
/*  61 */     if (null == paramString) {
/*  62 */       return null;
/*     */     }
/*     */     try {
/*  65 */       paramString = paramString.trim();
/*  66 */       Path localPath = Paths.get(paramString.length() == 0 ? "." : paramString, new String[0]);
/*  67 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Void run() {
/*  70 */           ProxyClassesDumper.validateDumpDir(this.val$dir);
/*  71 */           return null; } }, null, new Permission[] { new FilePermission("<<ALL FILES>>", "read, write") });
/*     */       
/*     */ 
/*  74 */       return new ProxyClassesDumper(localPath);
/*     */     }
/*     */     catch (InvalidPathException localInvalidPathException) {
/*  77 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName()).warning("Path " + paramString + " is not valid - dumping disabled", localInvalidPathException);
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException) {
/*  80 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName()).warning(localIllegalArgumentException.getMessage() + " - dumping disabled");
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   private ProxyClassesDumper(Path paramPath) {
/*  86 */     this.dumpDir = ((Path)Objects.requireNonNull(paramPath));
/*     */   }
/*     */   
/*     */   private static void validateDumpDir(Path paramPath) {
/*  90 */     if (!Files.exists(paramPath, new LinkOption[0]))
/*  91 */       throw new IllegalArgumentException("Directory " + paramPath + " does not exist");
/*  92 */     if (!Files.isDirectory(paramPath, new LinkOption[0]))
/*  93 */       throw new IllegalArgumentException("Path " + paramPath + " is not a directory");
/*  94 */     if (!Files.isWritable(paramPath)) {
/*  95 */       throw new IllegalArgumentException("Directory " + paramPath + " is not writable");
/*     */     }
/*     */   }
/*     */   
/*     */   public static String encodeForFilename(String paramString) {
/* 100 */     int i = paramString.length();
/* 101 */     StringBuilder localStringBuilder = new StringBuilder(i);
/*     */     
/* 103 */     for (int j = 0; j < i; j++) {
/* 104 */       char c = paramString.charAt(j);
/*     */       
/* 106 */       if (c <= '\037') {
/* 107 */         localStringBuilder.append('%');
/* 108 */         localStringBuilder.append(HEX[(c >> '\004' & 0xF)]);
/* 109 */         localStringBuilder.append(HEX[(c & 0xF)]);
/*     */       } else {
/* 111 */         for (int k = 0; 
/* 112 */             k < BAD_CHARS.length; k++) {
/* 113 */           if (c == BAD_CHARS[k]) {
/* 114 */             localStringBuilder.append(REPLACEMENT[k]);
/* 115 */             break;
/*     */           }
/*     */         }
/* 118 */         if (k >= BAD_CHARS.length) {
/* 119 */           localStringBuilder.append(c);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 124 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void dumpClass(String paramString, byte[] paramArrayOfByte) {
/*     */     Path localPath1;
/*     */     try {
/* 130 */       localPath1 = this.dumpDir.resolve(encodeForFilename(paramString) + ".class");
/*     */     }
/*     */     catch (InvalidPathException localInvalidPathException) {
/* 133 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName()).warning("Invalid path for class " + paramString);
/* 134 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 138 */       Path localPath2 = localPath1.getParent();
/* 139 */       Files.createDirectories(localPath2, new FileAttribute[0]);
/* 140 */       Files.write(localPath1, paramArrayOfByte, new OpenOption[0]);
/*     */     }
/*     */     catch (Exception localException) {
/* 143 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName()).warning("Exception writing to path at " + localPath1.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/ProxyClassesDumper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */