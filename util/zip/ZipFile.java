/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import sun.misc.JavaUtilZipFileAccess;
/*     */ import sun.misc.PerfCounter;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZipFile
/*     */   implements ZipConstants, Closeable
/*     */ {
/*     */   private long jzfile;
/*     */   private final String name;
/*     */   private final int total;
/*     */   private final boolean locsig;
/*  66 */   private volatile boolean closeRequested = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int STORED = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int DEFLATED = 8;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int OPEN_READ = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int OPEN_DELETE = 4;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final boolean usemmap;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ZipCoder zc;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ZipFile(String paramString)
/*     */     throws IOException
/*     */   {
/* 121 */     this(new File(paramString), 1);
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
/*     */   public ZipFile(File paramFile, int paramInt)
/*     */     throws IOException
/*     */   {
/* 150 */     this(paramFile, paramInt, StandardCharsets.UTF_8);
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
/*     */   public ZipFile(File paramFile)
/*     */     throws ZipException, IOException
/*     */   {
/* 164 */     this(paramFile, 1);
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
/*     */   public ZipFile(File paramFile, int paramInt, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/* 203 */     if (((paramInt & 0x1) == 0) || ((paramInt & 0xFFFFFFFA) != 0))
/*     */     {
/*     */ 
/* 206 */       throw new IllegalArgumentException("Illegal mode: 0x" + Integer.toHexString(paramInt));
/*     */     }
/* 208 */     String str = paramFile.getPath();
/* 209 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 210 */     if (localSecurityManager != null) {
/* 211 */       localSecurityManager.checkRead(str);
/* 212 */       if ((paramInt & 0x4) != 0) {
/* 213 */         localSecurityManager.checkDelete(str);
/*     */       }
/*     */     }
/* 216 */     if (paramCharset == null)
/* 217 */       throw new NullPointerException("charset is null");
/* 218 */     this.zc = ZipCoder.get(paramCharset);
/* 219 */     long l = System.nanoTime();
/* 220 */     this.jzfile = open(str, paramInt, paramFile.lastModified(), usemmap);
/* 221 */     PerfCounter.getZipFileOpenTime().addElapsedTimeFrom(l);
/* 222 */     PerfCounter.getZipFileCount().increment();
/* 223 */     this.name = str;
/* 224 */     this.total = getTotal(this.jzfile);
/* 225 */     this.locsig = startsWithLOC(this.jzfile);
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
/*     */   public ZipFile(String paramString, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/* 254 */     this(new File(paramString), 1, paramCharset);
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
/*     */   public ZipFile(File paramFile, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/* 274 */     this(paramFile, 1, paramCharset);
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
/*     */   public String getComment()
/*     */   {
/* 287 */     synchronized (this) {
/* 288 */       ensureOpen();
/* 289 */       byte[] arrayOfByte = getCommentBytes(this.jzfile);
/* 290 */       if (arrayOfByte == null)
/* 291 */         return null;
/* 292 */       return this.zc.toString(arrayOfByte, arrayOfByte.length);
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
/*     */   public ZipEntry getEntry(String paramString)
/*     */   {
/* 305 */     if (paramString == null) {
/* 306 */       throw new NullPointerException("name");
/*     */     }
/* 308 */     long l = 0L;
/* 309 */     synchronized (this) {
/* 310 */       ensureOpen();
/* 311 */       l = getEntry(this.jzfile, this.zc.getBytes(paramString), true);
/* 312 */       if (l != 0L) {
/* 313 */         ZipEntry localZipEntry = getZipEntry(paramString, l);
/* 314 */         freeEntry(this.jzfile, l);
/* 315 */         return localZipEntry;
/*     */       }
/*     */     }
/* 318 */     return null;
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
/* 329 */   private final Map<InputStream, Inflater> streams = new WeakHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputStream getInputStream(ZipEntry paramZipEntry)
/*     */     throws IOException
/*     */   {
/* 346 */     if (paramZipEntry == null) {
/* 347 */       throw new NullPointerException("entry");
/*     */     }
/* 349 */     long l1 = 0L;
/* 350 */     ZipFileInputStream localZipFileInputStream = null;
/* 351 */     synchronized (this) {
/* 352 */       ensureOpen();
/* 353 */       if ((!this.zc.isUTF8()) && ((paramZipEntry.flag & 0x800) != 0)) {
/* 354 */         l1 = getEntry(this.jzfile, this.zc.getBytesUTF8(paramZipEntry.name), false);
/*     */       } else {
/* 356 */         l1 = getEntry(this.jzfile, this.zc.getBytes(paramZipEntry.name), false);
/*     */       }
/* 358 */       if (l1 == 0L) {
/* 359 */         return null;
/*     */       }
/* 361 */       localZipFileInputStream = new ZipFileInputStream(l1);
/*     */       
/* 363 */       switch (getEntryMethod(l1)) {
/*     */       case 0: 
/* 365 */         synchronized (this.streams) {
/* 366 */           this.streams.put(localZipFileInputStream, null);
/*     */         }
/* 368 */         return localZipFileInputStream;
/*     */       
/*     */       case 8: 
/* 371 */         long l2 = getEntrySize(l1) + 2L;
/* 372 */         if (l2 > 65536L) l2 = 8192L;
/* 373 */         if (l2 <= 0L) l2 = 4096L;
/* 374 */         Inflater localInflater = getInflater();
/* 375 */         ZipFileInflaterInputStream localZipFileInflaterInputStream = new ZipFileInflaterInputStream(localZipFileInputStream, localInflater, (int)l2);
/*     */         
/* 377 */         synchronized (this.streams) {
/* 378 */           this.streams.put(localZipFileInflaterInputStream, localInflater);
/*     */         }
/* 380 */         return localZipFileInflaterInputStream;
/*     */       }
/* 382 */       throw new ZipException("invalid compression method");
/*     */     }
/*     */   }
/*     */   
/*     */   private class ZipFileInflaterInputStream extends InflaterInputStream
/*     */   {
/* 388 */     private volatile boolean closeRequested = false;
/* 389 */     private boolean eof = false;
/*     */     private final ZipFile.ZipFileInputStream zfin;
/*     */     
/*     */     ZipFileInflaterInputStream(ZipFile.ZipFileInputStream paramZipFileInputStream, Inflater paramInflater, int paramInt)
/*     */     {
/* 394 */       super(paramInflater, paramInt);
/* 395 */       this.zfin = paramZipFileInputStream;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 399 */       if (this.closeRequested)
/* 400 */         return;
/* 401 */       this.closeRequested = true;
/*     */       
/* 403 */       super.close();
/*     */       Inflater localInflater;
/* 405 */       synchronized (ZipFile.this.streams) {
/* 406 */         localInflater = (Inflater)ZipFile.this.streams.remove(this);
/*     */       }
/* 408 */       if (localInflater != null) {
/* 409 */         ZipFile.this.releaseInflater(localInflater);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     protected void fill()
/*     */       throws IOException
/*     */     {
/* 417 */       if (this.eof) {
/* 418 */         throw new EOFException("Unexpected end of ZLIB input stream");
/*     */       }
/* 420 */       this.len = this.in.read(this.buf, 0, this.buf.length);
/* 421 */       if (this.len == -1) {
/* 422 */         this.buf[0] = 0;
/* 423 */         this.len = 1;
/* 424 */         this.eof = true;
/*     */       }
/* 426 */       this.inf.setInput(this.buf, 0, this.len);
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 430 */       if (this.closeRequested)
/* 431 */         return 0;
/* 432 */       long l = this.zfin.size() - this.inf.getBytesWritten();
/* 433 */       return l > 2147483647L ? Integer.MAX_VALUE : (int)l;
/*     */     }
/*     */     
/*     */     protected void finalize() throws Throwable
/*     */     {
/* 438 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Inflater getInflater()
/*     */   {
/* 448 */     synchronized (this.inflaterCache) { Inflater localInflater;
/* 449 */       while (null != (localInflater = (Inflater)this.inflaterCache.poll())) {
/* 450 */         if (false == localInflater.ended()) {
/* 451 */           return localInflater;
/*     */         }
/*     */       }
/*     */     }
/* 455 */     return new Inflater(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void releaseInflater(Inflater paramInflater)
/*     */   {
/* 462 */     if (false == paramInflater.ended()) {
/* 463 */       paramInflater.reset();
/* 464 */       synchronized (this.inflaterCache) {
/* 465 */         this.inflaterCache.add(paramInflater);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 471 */   private Deque<Inflater> inflaterCache = new ArrayDeque();
/*     */   private static final int JZENTRY_NAME = 0;
/*     */   private static final int JZENTRY_EXTRA = 1;
/*     */   private static final int JZENTRY_COMMENT = 2;
/*     */   
/*     */   public String getName()
/*     */   {
/* 478 */     return this.name;
/*     */   }
/*     */   
/*     */   private class ZipEntryIterator implements Enumeration<ZipEntry>, Iterator<ZipEntry> {
/* 482 */     private int i = 0;
/*     */     
/*     */     public ZipEntryIterator() {
/* 485 */       ZipFile.this.ensureOpen();
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements() {
/* 489 */       return hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 493 */       synchronized (ZipFile.this) {
/* 494 */         ZipFile.this.ensureOpen();
/* 495 */         return this.i < ZipFile.this.total;
/*     */       }
/*     */     }
/*     */     
/*     */     public ZipEntry nextElement() {
/* 500 */       return next();
/*     */     }
/*     */     
/*     */     public ZipEntry next() {
/* 504 */       synchronized (ZipFile.this) {
/* 505 */         ZipFile.this.ensureOpen();
/* 506 */         if (this.i >= ZipFile.this.total) {
/* 507 */           throw new NoSuchElementException();
/*     */         }
/* 509 */         long l = ZipFile.getNextEntry(ZipFile.this.jzfile, this.i++);
/* 510 */         if (l == 0L)
/*     */         {
/* 512 */           if (ZipFile.this.closeRequested) {
/* 513 */             localObject1 = "ZipFile concurrently closed";
/*     */           } else {
/* 515 */             localObject1 = ZipFile.getZipMessage(ZipFile.this.jzfile);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 520 */           throw new ZipError("jzentry == 0,\n jzfile = " + ZipFile.this.jzfile + ",\n total = " + ZipFile.this.total + ",\n name = " + ZipFile.this.name + ",\n i = " + this.i + ",\n message = " + (String)localObject1);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 525 */         Object localObject1 = ZipFile.this.getZipEntry(null, l);
/* 526 */         ZipFile.freeEntry(ZipFile.this.jzfile, l);
/* 527 */         return (ZipEntry)localObject1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Enumeration<? extends ZipEntry> entries()
/*     */   {
/* 538 */     return new ZipEntryIterator();
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
/*     */   public Stream<? extends ZipEntry> stream()
/*     */   {
/* 551 */     return StreamSupport.stream(Spliterators.spliterator(new ZipEntryIterator(), 
/* 552 */       size(), 1297), false);
/*     */   }
/*     */   
/*     */ 
/*     */   private ZipEntry getZipEntry(String paramString, long paramLong)
/*     */   {
/* 558 */     ZipEntry localZipEntry = new ZipEntry();
/* 559 */     localZipEntry.flag = getEntryFlag(paramLong);
/* 560 */     if (paramString != null) {
/* 561 */       localZipEntry.name = paramString;
/*     */     } else {
/* 563 */       arrayOfByte = getEntryBytes(paramLong, 0);
/* 564 */       if ((!this.zc.isUTF8()) && ((localZipEntry.flag & 0x800) != 0)) {
/* 565 */         localZipEntry.name = this.zc.toStringUTF8(arrayOfByte, arrayOfByte.length);
/*     */       } else {
/* 567 */         localZipEntry.name = this.zc.toString(arrayOfByte, arrayOfByte.length);
/*     */       }
/*     */     }
/* 570 */     localZipEntry.time = ZipUtils.dosToJavaTime(getEntryTime(paramLong));
/* 571 */     localZipEntry.crc = getEntryCrc(paramLong);
/* 572 */     localZipEntry.size = getEntrySize(paramLong);
/* 573 */     localZipEntry.csize = getEntryCSize(paramLong);
/* 574 */     localZipEntry.method = getEntryMethod(paramLong);
/* 575 */     localZipEntry.setExtra0(getEntryBytes(paramLong, 1), false);
/* 576 */     byte[] arrayOfByte = getEntryBytes(paramLong, 2);
/* 577 */     if (arrayOfByte == null) {
/* 578 */       localZipEntry.comment = null;
/*     */     }
/* 580 */     else if ((!this.zc.isUTF8()) && ((localZipEntry.flag & 0x800) != 0)) {
/* 581 */       localZipEntry.comment = this.zc.toStringUTF8(arrayOfByte, arrayOfByte.length);
/*     */     } else {
/* 583 */       localZipEntry.comment = this.zc.toString(arrayOfByte, arrayOfByte.length);
/*     */     }
/*     */     
/* 586 */     return localZipEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 597 */     ensureOpen();
/* 598 */     return this.total;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 610 */     if (this.closeRequested)
/* 611 */       return;
/* 612 */     this.closeRequested = true;
/*     */     
/* 614 */     synchronized (this)
/*     */     {
/* 616 */       synchronized (this.streams) {
/* 617 */         if (false == this.streams.isEmpty()) {
/* 618 */           HashMap localHashMap = new HashMap(this.streams);
/* 619 */           this.streams.clear();
/* 620 */           for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 621 */             ((InputStream)localEntry.getKey()).close();
/* 622 */             Inflater localInflater = (Inflater)localEntry.getValue();
/* 623 */             if (localInflater != null) {
/* 624 */               localInflater.end();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 632 */       synchronized (this.inflaterCache) {
/* 633 */         while (null != (??? = (Inflater)this.inflaterCache.poll())) {
/* 634 */           ((Inflater)???).end();
/*     */         }
/*     */       }
/*     */       
/* 638 */       if (this.jzfile != 0L)
/*     */       {
/* 640 */         long l = this.jzfile;
/* 641 */         this.jzfile = 0L;
/*     */         
/* 643 */         close(l);
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
/*     */   protected void finalize()
/*     */     throws IOException
/*     */   {
/* 663 */     close();
/*     */   }
/*     */   
/*     */ 
/*     */   private void ensureOpen()
/*     */   {
/* 669 */     if (this.closeRequested) {
/* 670 */       throw new IllegalStateException("zip file closed");
/*     */     }
/*     */     
/* 673 */     if (this.jzfile == 0L) {
/* 674 */       throw new IllegalStateException("The object is not initialized.");
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureOpenOrZipException() throws IOException {
/* 679 */     if (this.closeRequested) {
/* 680 */       throw new ZipException("ZipFile closed");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class ZipFileInputStream
/*     */     extends InputStream
/*     */   {
/* 689 */     private volatile boolean closeRequested = false;
/*     */     protected long jzentry;
/*     */     private long pos;
/*     */     protected long rem;
/*     */     protected long size;
/*     */     
/*     */     ZipFileInputStream(long paramLong) {
/* 696 */       this.pos = 0L;
/* 697 */       this.rem = ZipFile.getEntryCSize(paramLong);
/* 698 */       this.size = ZipFile.getEntrySize(paramLong);
/* 699 */       this.jzentry = paramLong;
/*     */     }
/*     */     
/*     */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 703 */       synchronized (ZipFile.this) {
/* 704 */         long l1 = this.rem;
/* 705 */         long l2 = this.pos;
/* 706 */         if (l1 == 0L) {
/* 707 */           return -1;
/*     */         }
/* 709 */         if (paramInt2 <= 0) {
/* 710 */           return 0;
/*     */         }
/* 712 */         if (paramInt2 > l1) {
/* 713 */           paramInt2 = (int)l1;
/*     */         }
/*     */         
/* 716 */         ZipFile.this.ensureOpenOrZipException();
/* 717 */         paramInt2 = ZipFile.read(ZipFile.this.jzfile, this.jzentry, l2, paramArrayOfByte, paramInt1, paramInt2);
/*     */         
/* 719 */         if (paramInt2 > 0) {
/* 720 */           this.pos = (l2 + paramInt2);
/* 721 */           this.rem = (l1 - paramInt2);
/*     */         }
/*     */       }
/* 724 */       if (this.rem == 0L) {
/* 725 */         close();
/*     */       }
/* 727 */       return paramInt2;
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 731 */       byte[] arrayOfByte = new byte[1];
/* 732 */       if (read(arrayOfByte, 0, 1) == 1) {
/* 733 */         return arrayOfByte[0] & 0xFF;
/*     */       }
/* 735 */       return -1;
/*     */     }
/*     */     
/*     */     public long skip(long paramLong)
/*     */     {
/* 740 */       if (paramLong > this.rem)
/* 741 */         paramLong = this.rem;
/* 742 */       this.pos += paramLong;
/* 743 */       this.rem -= paramLong;
/* 744 */       if (this.rem == 0L) {
/* 745 */         close();
/*     */       }
/* 747 */       return paramLong;
/*     */     }
/*     */     
/*     */     public int available() {
/* 751 */       return this.rem > 2147483647L ? Integer.MAX_VALUE : (int)this.rem;
/*     */     }
/*     */     
/*     */     public long size() {
/* 755 */       return this.size;
/*     */     }
/*     */     
/*     */     public void close() {
/* 759 */       if (this.closeRequested)
/* 760 */         return;
/* 761 */       this.closeRequested = true;
/*     */       
/* 763 */       this.rem = 0L;
/* 764 */       synchronized (ZipFile.this) {
/* 765 */         if ((this.jzentry != 0L) && (ZipFile.this.jzfile != 0L)) {
/* 766 */           ZipFile.freeEntry(ZipFile.this.jzfile, this.jzentry);
/* 767 */           this.jzentry = 0L;
/*     */         }
/*     */       }
/* 770 */       synchronized (ZipFile.this.streams) {
/* 771 */         ZipFile.this.streams.remove(this);
/*     */       }
/*     */     }
/*     */     
/*     */     protected void finalize() {
/* 776 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*  87 */     initIDs();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */     String str = VM.getSavedProperty("sun.zip.disableMemoryMapping");
/*     */     
/*  99 */     usemmap = (str == null) || ((str.length() != 0) && (!str.equalsIgnoreCase("true")));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 781 */     SharedSecrets.setJavaUtilZipFileAccess(new JavaUtilZipFileAccess()
/*     */     {
/*     */       public boolean startsWithLocHeader(ZipFile paramAnonymousZipFile) {
/* 784 */         return paramAnonymousZipFile.startsWithLocHeader();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean startsWithLocHeader()
/*     */   {
/* 795 */     return this.locsig;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native long getEntry(long paramLong, byte[] paramArrayOfByte, boolean paramBoolean);
/*     */   
/*     */   private static native void freeEntry(long paramLong1, long paramLong2);
/*     */   
/*     */   private static native long getNextEntry(long paramLong, int paramInt);
/*     */   
/*     */   private static native void close(long paramLong);
/*     */   
/*     */   private static native long open(String paramString, int paramInt, long paramLong, boolean paramBoolean)
/*     */     throws IOException;
/*     */   
/*     */   private static native int getTotal(long paramLong);
/*     */   
/*     */   private static native boolean startsWithLOC(long paramLong);
/*     */   
/*     */   private static native int read(long paramLong1, long paramLong2, long paramLong3, byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*     */   
/*     */   private static native long getEntryTime(long paramLong);
/*     */   
/*     */   private static native long getEntryCrc(long paramLong);
/*     */   
/*     */   private static native long getEntryCSize(long paramLong);
/*     */   
/*     */   private static native long getEntrySize(long paramLong);
/*     */   
/*     */   private static native int getEntryMethod(long paramLong);
/*     */   
/*     */   private static native int getEntryFlag(long paramLong);
/*     */   
/*     */   private static native byte[] getCommentBytes(long paramLong);
/*     */   
/*     */   private static native byte[] getEntryBytes(long paramLong, int paramInt);
/*     */   
/*     */   private static native String getZipMessage(long paramLong);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/ZipFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */