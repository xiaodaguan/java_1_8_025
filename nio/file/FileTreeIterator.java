/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FileTreeIterator
/*     */   implements Iterator<FileTreeWalker.Event>, Closeable
/*     */ {
/*     */   private final FileTreeWalker walker;
/*     */   private FileTreeWalker.Event next;
/*     */   
/*     */   FileTreeIterator(Path paramPath, int paramInt, FileVisitOption... paramVarArgs)
/*     */     throws IOException
/*     */   {
/*  71 */     this.walker = new FileTreeWalker(Arrays.asList(paramVarArgs), paramInt);
/*  72 */     this.next = this.walker.walk(paramPath);
/*  73 */     assert ((this.next.type() == FileTreeWalker.EventType.ENTRY) || 
/*  74 */       (this.next.type() == FileTreeWalker.EventType.START_DIRECTORY));
/*     */     
/*     */ 
/*  77 */     IOException localIOException = this.next.ioeException();
/*  78 */     if (localIOException != null)
/*  79 */       throw localIOException;
/*     */   }
/*     */   
/*     */   private void fetchNextIfNeeded() {
/*  83 */     if (this.next == null) {
/*  84 */       FileTreeWalker.Event localEvent = this.walker.next();
/*  85 */       while (localEvent != null) {
/*  86 */         IOException localIOException = localEvent.ioeException();
/*  87 */         if (localIOException != null) {
/*  88 */           throw new UncheckedIOException(localIOException);
/*     */         }
/*     */         
/*  91 */         if (localEvent.type() != FileTreeWalker.EventType.END_DIRECTORY) {
/*  92 */           this.next = localEvent;
/*  93 */           return;
/*     */         }
/*  95 */         localEvent = this.walker.next();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasNext()
/*     */   {
/* 102 */     if (!this.walker.isOpen())
/* 103 */       throw new IllegalStateException();
/* 104 */     fetchNextIfNeeded();
/* 105 */     return this.next != null;
/*     */   }
/*     */   
/*     */   public FileTreeWalker.Event next()
/*     */   {
/* 110 */     if (!this.walker.isOpen())
/* 111 */       throw new IllegalStateException();
/* 112 */     fetchNextIfNeeded();
/* 113 */     if (this.next == null)
/* 114 */       throw new NoSuchElementException();
/* 115 */     FileTreeWalker.Event localEvent = this.next;
/* 116 */     this.next = null;
/* 117 */     return localEvent;
/*     */   }
/*     */   
/*     */   public void close()
/*     */   {
/* 122 */     this.walker.close();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/FileTreeIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */