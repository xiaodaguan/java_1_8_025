/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.URI;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.nio.fs.DefaultFileSystemProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileSystems
/*     */ {
/*     */   private static class DefaultFileSystemHolder
/*     */   {
/*  90 */     static final FileSystem defaultFileSystem = ;
/*     */     
/*     */ 
/*     */ 
/*     */     private static FileSystem defaultFileSystem()
/*     */     {
/*  96 */       FileSystemProvider localFileSystemProvider = (FileSystemProvider)AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public FileSystemProvider run() {
/*  98 */           return FileSystems.DefaultFileSystemHolder.access$000();
/*     */         }
/*     */         
/*     */ 
/* 102 */       });
/* 103 */       return localFileSystemProvider.getFileSystem(URI.create("file:///"));
/*     */     }
/*     */     
/*     */     private static FileSystemProvider getDefaultProvider()
/*     */     {
/* 108 */       FileSystemProvider localFileSystemProvider = DefaultFileSystemProvider.create();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 113 */       String str1 = System.getProperty("java.nio.file.spi.DefaultFileSystemProvider");
/* 114 */       if (str1 != null) {
/* 115 */         for (String str2 : str1.split(",")) {
/*     */           try
/*     */           {
/* 118 */             Class localClass = Class.forName(str2, true, ClassLoader.getSystemClassLoader());
/*     */             
/* 120 */             Constructor localConstructor = localClass.getDeclaredConstructor(new Class[] { FileSystemProvider.class });
/* 121 */             localFileSystemProvider = (FileSystemProvider)localConstructor.newInstance(new Object[] { localFileSystemProvider });
/*     */             
/*     */ 
/* 124 */             if (!localFileSystemProvider.getScheme().equals("file")) {
/* 125 */               throw new Error("Default provider must use scheme 'file'");
/*     */             }
/*     */           } catch (Exception localException) {
/* 128 */             throw new Error(localException);
/*     */           }
/*     */         }
/*     */       }
/* 132 */       return localFileSystemProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FileSystem getDefault()
/*     */   {
/* 176 */     return DefaultFileSystemHolder.defaultFileSystem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FileSystem getFileSystem(URI paramURI)
/*     */   {
/* 218 */     String str = paramURI.getScheme();
/* 219 */     for (FileSystemProvider localFileSystemProvider : FileSystemProvider.installedProviders()) {
/* 220 */       if (str.equalsIgnoreCase(localFileSystemProvider.getScheme())) {
/* 221 */         return localFileSystemProvider.getFileSystem(paramURI);
/*     */       }
/*     */     }
/* 224 */     throw new ProviderNotFoundException("Provider \"" + str + "\" not found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FileSystem newFileSystem(URI paramURI, Map<String, ?> paramMap)
/*     */     throws IOException
/*     */   {
/* 276 */     return newFileSystem(paramURI, paramMap, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FileSystem newFileSystem(URI paramURI, Map<String, ?> paramMap, ClassLoader paramClassLoader)
/*     */     throws IOException
/*     */   {
/* 321 */     String str = paramURI.getScheme();
/*     */     
/*     */ 
/* 324 */     for (Object localObject1 = FileSystemProvider.installedProviders().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (FileSystemProvider)((Iterator)localObject1).next();
/* 325 */       if (str.equalsIgnoreCase(((FileSystemProvider)localObject2).getScheme())) {
/* 326 */         return ((FileSystemProvider)localObject2).newFileSystem(paramURI, paramMap);
/*     */       }
/*     */     }
/*     */     
/*     */     Object localObject2;
/* 331 */     if (paramClassLoader != null)
/*     */     {
/* 333 */       localObject1 = ServiceLoader.load(FileSystemProvider.class, paramClassLoader);
/* 334 */       for (localObject2 = ((ServiceLoader)localObject1).iterator(); ((Iterator)localObject2).hasNext();) { FileSystemProvider localFileSystemProvider = (FileSystemProvider)((Iterator)localObject2).next();
/* 335 */         if (str.equalsIgnoreCase(localFileSystemProvider.getScheme())) {
/* 336 */           return localFileSystemProvider.newFileSystem(paramURI, paramMap);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 341 */     throw new ProviderNotFoundException("Provider \"" + str + "\" not found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FileSystem newFileSystem(Path paramPath, ClassLoader paramClassLoader)
/*     */     throws IOException
/*     */   {
/* 383 */     if (paramPath == null)
/* 384 */       throw new NullPointerException();
/* 385 */     Map localMap = Collections.emptyMap();
/*     */     
/*     */ 
/* 388 */     for (Object localObject1 = FileSystemProvider.installedProviders().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (FileSystemProvider)((Iterator)localObject1).next();
/*     */       try {
/* 390 */         return ((FileSystemProvider)localObject2).newFileSystem(paramPath, localMap);
/*     */       }
/*     */       catch (UnsupportedOperationException localUnsupportedOperationException1) {}
/*     */     }
/*     */     
/*     */     Object localObject2;
/* 396 */     if (paramClassLoader != null)
/*     */     {
/* 398 */       localObject1 = ServiceLoader.load(FileSystemProvider.class, paramClassLoader);
/* 399 */       for (localObject2 = ((ServiceLoader)localObject1).iterator(); ((Iterator)localObject2).hasNext();) { FileSystemProvider localFileSystemProvider = (FileSystemProvider)((Iterator)localObject2).next();
/*     */         try {
/* 401 */           return localFileSystemProvider.newFileSystem(paramPath, localMap);
/*     */         }
/*     */         catch (UnsupportedOperationException localUnsupportedOperationException2) {}
/*     */       }
/*     */     }
/*     */     
/* 407 */     throw new ProviderNotFoundException("Provider not found");
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/FileSystems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */