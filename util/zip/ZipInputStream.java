/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZipInputStream
/*     */   extends InflaterInputStream
/*     */   implements ZipConstants
/*     */ {
/*     */   private ZipEntry entry;
/*     */   private int flag;
/*  48 */   private CRC32 crc = new CRC32();
/*     */   private long remaining;
/*  50 */   private byte[] tmpbuf = new byte['Ȁ'];
/*     */   
/*     */   private static final int STORED = 0;
/*     */   
/*     */   private static final int DEFLATED = 8;
/*  55 */   private boolean closed = false;
/*     */   
/*     */ 
/*  58 */   private boolean entryEOF = false;
/*     */   
/*     */   private ZipCoder zc;
/*     */   
/*     */ 
/*     */   private void ensureOpen()
/*     */     throws IOException
/*     */   {
/*  66 */     if (this.closed) {
/*  67 */       throw new IOException("Stream closed");
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
/*     */   public ZipInputStream(InputStream paramInputStream)
/*     */   {
/*  80 */     this(paramInputStream, StandardCharsets.UTF_8);
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
/*     */   public ZipInputStream(InputStream paramInputStream, Charset paramCharset)
/*     */   {
/*  98 */     super(new PushbackInputStream(paramInputStream, 512), new Inflater(true), 512);
/*  99 */     this.usesDefaultInflater = true;
/* 100 */     if (paramInputStream == null) {
/* 101 */       throw new NullPointerException("in is null");
/*     */     }
/* 103 */     if (paramCharset == null)
/* 104 */       throw new NullPointerException("charset is null");
/* 105 */     this.zc = ZipCoder.get(paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ZipEntry getNextEntry()
/*     */     throws IOException
/*     */   {
/* 116 */     ensureOpen();
/* 117 */     if (this.entry != null) {
/* 118 */       closeEntry();
/*     */     }
/* 120 */     this.crc.reset();
/* 121 */     this.inf.reset();
/* 122 */     if ((this.entry = readLOC()) == null) {
/* 123 */       return null;
/*     */     }
/* 125 */     if (this.entry.method == 0) {
/* 126 */       this.remaining = this.entry.size;
/*     */     }
/* 128 */     this.entryEOF = false;
/* 129 */     return this.entry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeEntry()
/*     */     throws IOException
/*     */   {
/* 139 */     ensureOpen();
/* 140 */     while (read(this.tmpbuf, 0, this.tmpbuf.length) != -1) {}
/* 141 */     this.entryEOF = true;
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
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/* 156 */     ensureOpen();
/* 157 */     if (this.entryEOF) {
/* 158 */       return 0;
/*     */     }
/* 160 */     return 1;
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 182 */     ensureOpen();
/* 183 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2))
/* 184 */       throw new IndexOutOfBoundsException();
/* 185 */     if (paramInt2 == 0) {
/* 186 */       return 0;
/*     */     }
/*     */     
/* 189 */     if (this.entry == null) {
/* 190 */       return -1;
/*     */     }
/* 192 */     switch (this.entry.method) {
/*     */     case 8: 
/* 194 */       paramInt2 = super.read(paramArrayOfByte, paramInt1, paramInt2);
/* 195 */       if (paramInt2 == -1) {
/* 196 */         readEnd(this.entry);
/* 197 */         this.entryEOF = true;
/* 198 */         this.entry = null;
/*     */       } else {
/* 200 */         this.crc.update(paramArrayOfByte, paramInt1, paramInt2);
/*     */       }
/* 202 */       return paramInt2;
/*     */     case 0: 
/* 204 */       if (this.remaining <= 0L) {
/* 205 */         this.entryEOF = true;
/* 206 */         this.entry = null;
/* 207 */         return -1;
/*     */       }
/* 209 */       if (paramInt2 > this.remaining) {
/* 210 */         paramInt2 = (int)this.remaining;
/*     */       }
/* 212 */       paramInt2 = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/* 213 */       if (paramInt2 == -1) {
/* 214 */         throw new ZipException("unexpected EOF");
/*     */       }
/* 216 */       this.crc.update(paramArrayOfByte, paramInt1, paramInt2);
/* 217 */       this.remaining -= paramInt2;
/* 218 */       if ((this.remaining == 0L) && (this.entry.crc != this.crc.getValue()))
/*     */       {
/*     */ 
/* 221 */         throw new ZipException("invalid entry CRC (expected 0x" + Long.toHexString(this.entry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
/*     */       }
/* 223 */       return paramInt2;
/*     */     }
/* 225 */     throw new ZipException("invalid compression method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 238 */     if (paramLong < 0L) {
/* 239 */       throw new IllegalArgumentException("negative skip length");
/*     */     }
/* 241 */     ensureOpen();
/* 242 */     int i = (int)Math.min(paramLong, 2147483647L);
/* 243 */     int j = 0;
/* 244 */     while (j < i) {
/* 245 */       int k = i - j;
/* 246 */       if (k > this.tmpbuf.length) {
/* 247 */         k = this.tmpbuf.length;
/*     */       }
/* 249 */       k = read(this.tmpbuf, 0, k);
/* 250 */       if (k == -1) {
/* 251 */         this.entryEOF = true;
/* 252 */         break;
/*     */       }
/* 254 */       j += k;
/*     */     }
/* 256 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 265 */     if (!this.closed) {
/* 266 */       super.close();
/* 267 */       this.closed = true;
/*     */     }
/*     */   }
/*     */   
/* 271 */   private byte[] b = new byte['Ā'];
/*     */   
/*     */   private ZipEntry readLOC()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 278 */       readFully(this.tmpbuf, 0, 30);
/*     */     } catch (EOFException localEOFException) {
/* 280 */       return null;
/*     */     }
/* 282 */     if (ZipUtils.get32(this.tmpbuf, 0) != 67324752L) {
/* 283 */       return null;
/*     */     }
/*     */     
/* 286 */     this.flag = ZipUtils.get16(this.tmpbuf, 6);
/*     */     
/* 288 */     int i = ZipUtils.get16(this.tmpbuf, 26);
/* 289 */     int j = this.b.length;
/* 290 */     if (i > j) {
/*     */       do {
/* 292 */         j *= 2;
/* 293 */       } while (i > j);
/* 294 */       this.b = new byte[j];
/*     */     }
/* 296 */     readFully(this.b, 0, i);
/*     */     
/* 298 */     ZipEntry localZipEntry = createZipEntry((this.flag & 0x800) != 0 ? this.zc
/* 299 */       .toStringUTF8(this.b, i) : this.zc
/* 300 */       .toString(this.b, i));
/*     */     
/* 302 */     if ((this.flag & 0x1) == 1) {
/* 303 */       throw new ZipException("encrypted ZIP entry not supported");
/*     */     }
/* 305 */     localZipEntry.method = ZipUtils.get16(this.tmpbuf, 8);
/* 306 */     localZipEntry.time = ZipUtils.dosToJavaTime(ZipUtils.get32(this.tmpbuf, 10));
/* 307 */     if ((this.flag & 0x8) == 8)
/*     */     {
/* 309 */       if (localZipEntry.method != 8) {
/* 310 */         throw new ZipException("only DEFLATED entries can have EXT descriptor");
/*     */       }
/*     */     }
/*     */     else {
/* 314 */       localZipEntry.crc = ZipUtils.get32(this.tmpbuf, 14);
/* 315 */       localZipEntry.csize = ZipUtils.get32(this.tmpbuf, 18);
/* 316 */       localZipEntry.size = ZipUtils.get32(this.tmpbuf, 22);
/*     */     }
/* 318 */     i = ZipUtils.get16(this.tmpbuf, 28);
/* 319 */     if (i > 0) {
/* 320 */       byte[] arrayOfByte = new byte[i];
/* 321 */       readFully(arrayOfByte, 0, i);
/* 322 */       localZipEntry.setExtra0(arrayOfByte, (localZipEntry.csize == 4294967295L) || (localZipEntry.size == 4294967295L));
/*     */     }
/*     */     
/* 325 */     return localZipEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ZipEntry createZipEntry(String paramString)
/*     */   {
/* 336 */     return new ZipEntry(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   private void readEnd(ZipEntry paramZipEntry)
/*     */     throws IOException
/*     */   {
/* 343 */     int i = this.inf.getRemaining();
/* 344 */     if (i > 0) {
/* 345 */       ((PushbackInputStream)this.in).unread(this.buf, this.len - i, i);
/*     */     }
/* 347 */     if ((this.flag & 0x8) == 8) {
/*     */       long l;
/* 349 */       if ((this.inf.getBytesWritten() > 4294967295L) || 
/* 350 */         (this.inf.getBytesRead() > 4294967295L))
/*     */       {
/* 352 */         readFully(this.tmpbuf, 0, 24);
/* 353 */         l = ZipUtils.get32(this.tmpbuf, 0);
/* 354 */         if (l != 134695760L) {
/* 355 */           paramZipEntry.crc = l;
/* 356 */           paramZipEntry.csize = ZipUtils.get64(this.tmpbuf, 4);
/* 357 */           paramZipEntry.size = ZipUtils.get64(this.tmpbuf, 12);
/* 358 */           ((PushbackInputStream)this.in).unread(this.tmpbuf, 19, 4);
/*     */         }
/*     */         else {
/* 361 */           paramZipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
/* 362 */           paramZipEntry.csize = ZipUtils.get64(this.tmpbuf, 8);
/* 363 */           paramZipEntry.size = ZipUtils.get64(this.tmpbuf, 16);
/*     */         }
/*     */       } else {
/* 366 */         readFully(this.tmpbuf, 0, 16);
/* 367 */         l = ZipUtils.get32(this.tmpbuf, 0);
/* 368 */         if (l != 134695760L) {
/* 369 */           paramZipEntry.crc = l;
/* 370 */           paramZipEntry.csize = ZipUtils.get32(this.tmpbuf, 4);
/* 371 */           paramZipEntry.size = ZipUtils.get32(this.tmpbuf, 8);
/* 372 */           ((PushbackInputStream)this.in).unread(this.tmpbuf, 11, 4);
/*     */         }
/*     */         else {
/* 375 */           paramZipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
/* 376 */           paramZipEntry.csize = ZipUtils.get32(this.tmpbuf, 8);
/* 377 */           paramZipEntry.size = ZipUtils.get32(this.tmpbuf, 12);
/*     */         }
/*     */       }
/*     */     }
/* 381 */     if (paramZipEntry.size != this.inf.getBytesWritten())
/*     */     {
/*     */ 
/* 384 */       throw new ZipException("invalid entry size (expected " + paramZipEntry.size + " but got " + this.inf.getBytesWritten() + " bytes)");
/*     */     }
/* 386 */     if (paramZipEntry.csize != this.inf.getBytesRead())
/*     */     {
/*     */ 
/* 389 */       throw new ZipException("invalid entry compressed size (expected " + paramZipEntry.csize + " but got " + this.inf.getBytesRead() + " bytes)");
/*     */     }
/* 391 */     if (paramZipEntry.crc != this.crc.getValue())
/*     */     {
/*     */ 
/* 394 */       throw new ZipException("invalid entry CRC (expected 0x" + Long.toHexString(paramZipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 402 */     while (paramInt2 > 0) {
/* 403 */       int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/* 404 */       if (i == -1) {
/* 405 */         throw new EOFException();
/*     */       }
/* 407 */       paramInt1 += i;
/* 408 */       paramInt2 -= i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/ZipInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */