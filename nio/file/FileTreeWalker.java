/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import sun.nio.fs.BasicFileAttributesHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FileTreeWalker
/*     */   implements Closeable
/*     */ {
/*     */   private final boolean followLinks;
/*     */   private final LinkOption[] linkOptions;
/*     */   private final int maxDepth;
/*  61 */   private final ArrayDeque<DirectoryNode> stack = new ArrayDeque();
/*     */   
/*     */   private boolean closed;
/*     */   
/*     */   private static class DirectoryNode
/*     */   {
/*     */     private final Path dir;
/*     */     private final Object key;
/*     */     private final DirectoryStream<Path> stream;
/*     */     private final Iterator<Path> iterator;
/*     */     private boolean skipped;
/*     */     
/*     */     DirectoryNode(Path paramPath, Object paramObject, DirectoryStream<Path> paramDirectoryStream)
/*     */     {
/*  75 */       this.dir = paramPath;
/*  76 */       this.key = paramObject;
/*  77 */       this.stream = paramDirectoryStream;
/*  78 */       this.iterator = paramDirectoryStream.iterator();
/*     */     }
/*     */     
/*     */     Path directory() {
/*  82 */       return this.dir;
/*     */     }
/*     */     
/*     */     Object key() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     DirectoryStream<Path> stream() {
/*  90 */       return this.stream;
/*     */     }
/*     */     
/*     */     Iterator<Path> iterator() {
/*  94 */       return this.iterator;
/*     */     }
/*     */     
/*     */     void skip() {
/*  98 */       this.skipped = true;
/*     */     }
/*     */     
/*     */     boolean skipped() {
/* 102 */       return this.skipped;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static enum EventType
/*     */   {
/* 113 */     START_DIRECTORY, 
/*     */     
/*     */ 
/*     */ 
/* 117 */     END_DIRECTORY, 
/*     */     
/*     */ 
/*     */ 
/* 121 */     ENTRY;
/*     */     
/*     */     private EventType() {}
/*     */   }
/*     */   
/*     */   static class Event
/*     */   {
/*     */     private final FileTreeWalker.EventType type;
/*     */     private final Path file;
/*     */     private final BasicFileAttributes attrs;
/*     */     private final IOException ioe;
/*     */     
/*     */     private Event(FileTreeWalker.EventType paramEventType, Path paramPath, BasicFileAttributes paramBasicFileAttributes, IOException paramIOException) {
/* 134 */       this.type = paramEventType;
/* 135 */       this.file = paramPath;
/* 136 */       this.attrs = paramBasicFileAttributes;
/* 137 */       this.ioe = paramIOException;
/*     */     }
/*     */     
/*     */     Event(FileTreeWalker.EventType paramEventType, Path paramPath, BasicFileAttributes paramBasicFileAttributes) {
/* 141 */       this(paramEventType, paramPath, paramBasicFileAttributes, null);
/*     */     }
/*     */     
/*     */     Event(FileTreeWalker.EventType paramEventType, Path paramPath, IOException paramIOException) {
/* 145 */       this(paramEventType, paramPath, null, paramIOException);
/*     */     }
/*     */     
/*     */     FileTreeWalker.EventType type() {
/* 149 */       return this.type;
/*     */     }
/*     */     
/*     */     Path file() {
/* 153 */       return this.file;
/*     */     }
/*     */     
/*     */     BasicFileAttributes attributes() {
/* 157 */       return this.attrs;
/*     */     }
/*     */     
/*     */     IOException ioeException() {
/* 161 */       return this.ioe;
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
/*     */   FileTreeWalker(Collection<FileVisitOption> paramCollection, int paramInt)
/*     */   {
/* 178 */     boolean bool = false;
/* 179 */     for (FileVisitOption localFileVisitOption : paramCollection)
/*     */     {
/* 181 */       switch (localFileVisitOption) {
/* 182 */       case FOLLOW_LINKS:  bool = true; break;
/*     */       default: 
/* 184 */         throw new AssertionError("Should not get here");
/*     */       }
/*     */     }
/* 187 */     if (paramInt < 0) {
/* 188 */       throw new IllegalArgumentException("'maxDepth' is negative");
/*     */     }
/* 190 */     this.followLinks = bool;
/* 191 */     this.linkOptions = new LinkOption[] { bool ? new LinkOption[0] : LinkOption.NOFOLLOW_LINKS };
/*     */     
/* 193 */     this.maxDepth = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private BasicFileAttributes getAttributes(Path paramPath, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*     */     BasicFileAttributes localBasicFileAttributes;
/*     */     
/*     */ 
/* 205 */     if ((paramBoolean) && ((paramPath instanceof BasicFileAttributesHolder)))
/*     */     {
/* 207 */       if (System.getSecurityManager() == null)
/*     */       {
/* 209 */         localBasicFileAttributes = ((BasicFileAttributesHolder)paramPath).get();
/* 210 */         if ((localBasicFileAttributes != null) && ((!this.followLinks) || (!localBasicFileAttributes.isSymbolicLink()))) {
/* 211 */           return localBasicFileAttributes;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 219 */       localBasicFileAttributes = Files.readAttributes(paramPath, BasicFileAttributes.class, this.linkOptions);
/*     */     } catch (IOException localIOException) {
/* 221 */       if (!this.followLinks) {
/* 222 */         throw localIOException;
/*     */       }
/*     */       
/* 225 */       localBasicFileAttributes = Files.readAttributes(paramPath, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/*     */     }
/*     */     
/*     */ 
/* 229 */     return localBasicFileAttributes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean wouldLoop(Path paramPath, Object paramObject)
/*     */   {
/* 239 */     for (DirectoryNode localDirectoryNode : this.stack) {
/* 240 */       Object localObject = localDirectoryNode.key();
/* 241 */       if ((paramObject != null) && (localObject != null)) {
/* 242 */         if (paramObject.equals(localObject))
/*     */         {
/* 244 */           return true;
/*     */         }
/*     */       } else {
/*     */         try {
/* 248 */           if (Files.isSameFile(paramPath, localDirectoryNode.directory()))
/*     */           {
/* 250 */             return true;
/*     */           }
/*     */         }
/*     */         catch (IOException|SecurityException localIOException) {}
/*     */       }
/*     */     }
/*     */     
/* 257 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Event visit(Path paramPath, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*     */     BasicFileAttributes localBasicFileAttributes;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 276 */       localBasicFileAttributes = getAttributes(paramPath, paramBoolean2);
/*     */     } catch (IOException localIOException1) {
/* 278 */       return new Event(EventType.ENTRY, paramPath, localIOException1);
/*     */     } catch (SecurityException localSecurityException1) {
/* 280 */       if (paramBoolean1)
/* 281 */         return null;
/* 282 */       throw localSecurityException1;
/*     */     }
/*     */     
/*     */ 
/* 286 */     int i = this.stack.size();
/* 287 */     if ((i >= this.maxDepth) || (!localBasicFileAttributes.isDirectory())) {
/* 288 */       return new Event(EventType.ENTRY, paramPath, localBasicFileAttributes);
/*     */     }
/*     */     
/*     */ 
/* 292 */     if ((this.followLinks) && (wouldLoop(paramPath, localBasicFileAttributes.fileKey())))
/*     */     {
/* 294 */       return new Event(EventType.ENTRY, paramPath, new FileSystemLoopException(paramPath.toString()));
/*     */     }
/*     */     
/*     */ 
/* 298 */     DirectoryStream localDirectoryStream = null;
/*     */     try {
/* 300 */       localDirectoryStream = Files.newDirectoryStream(paramPath);
/*     */     } catch (IOException localIOException2) {
/* 302 */       return new Event(EventType.ENTRY, paramPath, localIOException2);
/*     */     } catch (SecurityException localSecurityException2) {
/* 304 */       if (paramBoolean1)
/* 305 */         return null;
/* 306 */       throw localSecurityException2;
/*     */     }
/*     */     
/*     */ 
/* 310 */     this.stack.push(new DirectoryNode(paramPath, localBasicFileAttributes.fileKey(), localDirectoryStream));
/* 311 */     return new Event(EventType.START_DIRECTORY, paramPath, localBasicFileAttributes);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   Event walk(Path paramPath)
/*     */   {
/* 319 */     if (this.closed) {
/* 320 */       throw new IllegalStateException("Closed");
/*     */     }
/* 322 */     Event localEvent = visit(paramPath, false, false);
/*     */     
/*     */ 
/* 325 */     assert (localEvent != null);
/* 326 */     return localEvent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   Event next()
/*     */   {
/* 334 */     DirectoryNode localDirectoryNode = (DirectoryNode)this.stack.peek();
/* 335 */     if (localDirectoryNode == null) {
/* 336 */       return null;
/*     */     }
/*     */     Event localEvent;
/*     */     do
/*     */     {
/* 341 */       Path localPath = null;
/* 342 */       Object localObject = null;
/*     */       
/*     */ 
/* 345 */       if (!localDirectoryNode.skipped()) {
/* 346 */         Iterator localIterator = localDirectoryNode.iterator();
/*     */         try {
/* 348 */           if (localIterator.hasNext()) {
/* 349 */             localPath = (Path)localIterator.next();
/*     */           }
/*     */         } catch (DirectoryIteratorException localDirectoryIteratorException) {
/* 352 */           localObject = localDirectoryIteratorException.getCause();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 357 */       if (localPath == null) {
/*     */         try {
/* 359 */           localDirectoryNode.stream().close();
/*     */         } catch (IOException localIOException) {
/* 361 */           if (localObject != null) {
/* 362 */             localObject = localIOException;
/*     */           } else {
/* 364 */             ((IOException)localObject).addSuppressed(localIOException);
/*     */           }
/*     */         }
/* 367 */         this.stack.pop();
/* 368 */         return new Event(EventType.END_DIRECTORY, localDirectoryNode.directory(), (IOException)localObject);
/*     */       }
/*     */       
/*     */ 
/* 372 */       localEvent = visit(localPath, true, true);
/*     */ 
/*     */ 
/*     */     }
/* 376 */     while (localEvent == null);
/*     */     
/* 378 */     return localEvent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void pop()
/*     */   {
/* 388 */     if (!this.stack.isEmpty()) {
/* 389 */       DirectoryNode localDirectoryNode = (DirectoryNode)this.stack.pop();
/*     */       try {
/* 391 */         localDirectoryNode.stream().close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void skipRemainingSiblings()
/*     */   {
/* 401 */     if (!this.stack.isEmpty()) {
/* 402 */       ((DirectoryNode)this.stack.peek()).skip();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isOpen()
/*     */   {
/* 410 */     return !this.closed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */   {
/* 418 */     if (!this.closed) {
/* 419 */       while (!this.stack.isEmpty()) {
/* 420 */         pop();
/*     */       }
/* 422 */       this.closed = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/FileTreeWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */