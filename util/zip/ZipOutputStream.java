/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashSet;
/*     */ import java.util.Vector;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZipOutputStream
/*     */   extends DeflaterOutputStream
/*     */   implements ZipConstants
/*     */ {
/*  56 */   private static final boolean inhibitZip64 = Boolean.parseBoolean(
/*  57 */     (String)AccessController.doPrivileged(new GetPropertyAction("jdk.util.zip.inhibitZip64", "false")));
/*     */   private XEntry current;
/*     */   
/*     */   private static class XEntry {
/*     */     final ZipEntry entry;
/*     */     final long offset;
/*     */     long dostime;
/*     */     
/*     */     public XEntry(ZipEntry paramZipEntry, long paramLong) {
/*  66 */       this.entry = paramZipEntry;
/*  67 */       this.offset = paramLong;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  72 */   private Vector<XEntry> xentries = new Vector();
/*  73 */   private HashSet<String> names = new HashSet();
/*  74 */   private CRC32 crc = new CRC32();
/*  75 */   private long written = 0L;
/*  76 */   private long locoff = 0L;
/*     */   private byte[] comment;
/*  78 */   private int method = 8;
/*     */   
/*     */   private boolean finished;
/*  81 */   private boolean closed = false;
/*     */   private final ZipCoder zc;
/*     */   public static final int STORED = 0;
/*     */   public static final int DEFLATED = 8;
/*     */   
/*  86 */   private static int version(ZipEntry paramZipEntry) throws ZipException { switch (paramZipEntry.method) {
/*  87 */     case 8:  return 20;
/*  88 */     case 0:  return 10; }
/*  89 */     throw new ZipException("unsupported compression method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void ensureOpen()
/*     */     throws IOException
/*     */   {
/*  97 */     if (this.closed) {
/*  98 */       throw new IOException("Stream closed");
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
/*     */   public ZipOutputStream(OutputStream paramOutputStream)
/*     */   {
/* 120 */     this(paramOutputStream, StandardCharsets.UTF_8);
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
/*     */   public ZipOutputStream(OutputStream paramOutputStream, Charset paramCharset)
/*     */   {
/* 134 */     super(paramOutputStream, new Deflater(-1, true));
/* 135 */     if (paramCharset == null)
/* 136 */       throw new NullPointerException("charset is null");
/* 137 */     this.zc = ZipCoder.get(paramCharset);
/* 138 */     this.usesDefaultDeflater = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComment(String paramString)
/*     */   {
/* 148 */     if (paramString != null) {
/* 149 */       this.comment = this.zc.getBytes(paramString);
/* 150 */       if (this.comment.length > 65535) {
/* 151 */         throw new IllegalArgumentException("ZIP file comment too long.");
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
/*     */   public void setMethod(int paramInt)
/*     */   {
/* 164 */     if ((paramInt != 8) && (paramInt != 0)) {
/* 165 */       throw new IllegalArgumentException("invalid compression method");
/*     */     }
/* 167 */     this.method = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevel(int paramInt)
/*     */   {
/* 177 */     this.def.setLevel(paramInt);
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
/*     */   public void putNextEntry(ZipEntry paramZipEntry)
/*     */     throws IOException
/*     */   {
/* 191 */     ensureOpen();
/* 192 */     if (this.current != null) {
/* 193 */       closeEntry();
/*     */     }
/* 195 */     if (paramZipEntry.time == -1L)
/*     */     {
/*     */ 
/* 198 */       paramZipEntry.setTime(System.currentTimeMillis());
/*     */     }
/* 200 */     if (paramZipEntry.method == -1) {
/* 201 */       paramZipEntry.method = this.method;
/*     */     }
/*     */     
/* 204 */     paramZipEntry.flag = 0;
/* 205 */     switch (paramZipEntry.method)
/*     */     {
/*     */ 
/*     */     case 8: 
/* 209 */       if ((paramZipEntry.size == -1L) || (paramZipEntry.csize == -1L) || (paramZipEntry.crc == -1L)) {
/* 210 */         paramZipEntry.flag = 8;
/*     */       }
/*     */       
/*     */ 
/*     */       break;
/*     */     case 0: 
/* 216 */       if (paramZipEntry.size == -1L) {
/* 217 */         paramZipEntry.size = paramZipEntry.csize;
/* 218 */       } else if (paramZipEntry.csize == -1L) {
/* 219 */         paramZipEntry.csize = paramZipEntry.size;
/* 220 */       } else if (paramZipEntry.size != paramZipEntry.csize) {
/* 221 */         throw new ZipException("STORED entry where compressed != uncompressed size");
/*     */       }
/*     */       
/* 224 */       if ((paramZipEntry.size == -1L) || (paramZipEntry.crc == -1L)) {
/* 225 */         throw new ZipException("STORED entry missing size, compressed size, or crc-32");
/*     */       }
/*     */       
/*     */       break;
/*     */     default: 
/* 230 */       throw new ZipException("unsupported compression method"); }
/*     */     
/* 232 */     if (!this.names.add(paramZipEntry.name)) {
/* 233 */       throw new ZipException("duplicate entry: " + paramZipEntry.name);
/*     */     }
/* 235 */     if (this.zc.isUTF8())
/* 236 */       paramZipEntry.flag |= 0x800;
/* 237 */     this.current = new XEntry(paramZipEntry, this.written);
/* 238 */     this.xentries.add(this.current);
/* 239 */     writeLOC(this.current);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeEntry()
/*     */     throws IOException
/*     */   {
/* 249 */     ensureOpen();
/* 250 */     if (this.current != null) {
/* 251 */       ZipEntry localZipEntry = this.current.entry;
/* 252 */       switch (localZipEntry.method) {
/*     */       case 8: 
/* 254 */         this.def.finish();
/* 255 */         while (!this.def.finished()) {
/* 256 */           deflate();
/*     */         }
/* 258 */         if ((localZipEntry.flag & 0x8) == 0)
/*     */         {
/* 260 */           if (localZipEntry.size != this.def.getBytesRead())
/*     */           {
/*     */ 
/* 263 */             throw new ZipException("invalid entry size (expected " + localZipEntry.size + " but got " + this.def.getBytesRead() + " bytes)");
/*     */           }
/* 265 */           if (localZipEntry.csize != this.def.getBytesWritten())
/*     */           {
/*     */ 
/* 268 */             throw new ZipException("invalid entry compressed size (expected " + localZipEntry.csize + " but got " + this.def.getBytesWritten() + " bytes)");
/*     */           }
/* 270 */           if (localZipEntry.crc != this.crc.getValue())
/*     */           {
/*     */ 
/*     */ 
/* 274 */             throw new ZipException("invalid entry CRC-32 (expected 0x" + Long.toHexString(localZipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
/*     */           }
/*     */         } else {
/* 277 */           localZipEntry.size = this.def.getBytesRead();
/* 278 */           localZipEntry.csize = this.def.getBytesWritten();
/* 279 */           localZipEntry.crc = this.crc.getValue();
/* 280 */           writeEXT(localZipEntry);
/*     */         }
/* 282 */         this.def.reset();
/* 283 */         this.written += localZipEntry.csize;
/* 284 */         break;
/*     */       
/*     */       case 0: 
/* 287 */         if (localZipEntry.size != this.written - this.locoff) {
/* 288 */           throw new ZipException("invalid entry size (expected " + localZipEntry.size + " but got " + (this.written - this.locoff) + " bytes)");
/*     */         }
/*     */         
/*     */ 
/* 292 */         if (localZipEntry.crc != this.crc.getValue())
/*     */         {
/*     */ 
/*     */ 
/* 296 */           throw new ZipException("invalid entry crc-32 (expected 0x" + Long.toHexString(localZipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
/*     */         }
/*     */         break;
/*     */       default: 
/* 300 */         throw new ZipException("invalid compression method");
/*     */       }
/* 302 */       this.crc.reset();
/* 303 */       this.current = null;
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
/*     */   public synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 319 */     ensureOpen();
/* 320 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2))
/* 321 */       throw new IndexOutOfBoundsException();
/* 322 */     if (paramInt2 == 0) {
/* 323 */       return;
/*     */     }
/*     */     
/* 326 */     if (this.current == null) {
/* 327 */       throw new ZipException("no current ZIP entry");
/*     */     }
/* 329 */     ZipEntry localZipEntry = this.current.entry;
/* 330 */     switch (localZipEntry.method) {
/*     */     case 8: 
/* 332 */       super.write(paramArrayOfByte, paramInt1, paramInt2);
/* 333 */       break;
/*     */     case 0: 
/* 335 */       this.written += paramInt2;
/* 336 */       if (this.written - this.locoff > localZipEntry.size) {
/* 337 */         throw new ZipException("attempt to write past end of STORED entry");
/*     */       }
/*     */       
/* 340 */       this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/* 341 */       break;
/*     */     default: 
/* 343 */       throw new ZipException("invalid compression method");
/*     */     }
/* 345 */     this.crc.update(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void finish()
/*     */     throws IOException
/*     */   {
/* 356 */     ensureOpen();
/* 357 */     if (this.finished) {
/* 358 */       return;
/*     */     }
/* 360 */     if (this.current != null) {
/* 361 */       closeEntry();
/*     */     }
/*     */     
/* 364 */     long l = this.written;
/* 365 */     for (XEntry localXEntry : this.xentries)
/* 366 */       writeCEN(localXEntry);
/* 367 */     writeEND(l, this.written - l);
/* 368 */     this.finished = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 377 */     if (!this.closed) {
/* 378 */       super.close();
/* 379 */       this.closed = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeLOC(XEntry paramXEntry)
/*     */     throws IOException
/*     */   {
/* 387 */     ZipEntry localZipEntry = paramXEntry.entry;
/* 388 */     int i = localZipEntry.flag;
/* 389 */     int j = 0;
/* 390 */     int k = getExtraLen(localZipEntry.extra);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 396 */     paramXEntry.dostime = ZipUtils.javaToDosTime(localZipEntry.time);
/*     */     
/* 398 */     writeInt(67324752L);
/* 399 */     if ((i & 0x8) == 8) {
/* 400 */       writeShort(version(localZipEntry));
/* 401 */       writeShort(i);
/* 402 */       writeShort(localZipEntry.method);
/* 403 */       writeInt(paramXEntry.dostime);
/*     */       
/*     */ 
/* 406 */       writeInt(0L);
/* 407 */       writeInt(0L);
/* 408 */       writeInt(0L);
/*     */     } else {
/* 410 */       if ((localZipEntry.csize >= 4294967295L) || (localZipEntry.size >= 4294967295L)) {
/* 411 */         j = 1;
/* 412 */         writeShort(45);
/*     */       } else {
/* 414 */         writeShort(version(localZipEntry));
/*     */       }
/* 416 */       writeShort(i);
/* 417 */       writeShort(localZipEntry.method);
/* 418 */       writeInt(paramXEntry.dostime);
/* 419 */       writeInt(localZipEntry.crc);
/* 420 */       if (j != 0) {
/* 421 */         writeInt(4294967295L);
/* 422 */         writeInt(4294967295L);
/* 423 */         k += 20;
/*     */       } else {
/* 425 */         writeInt(localZipEntry.csize);
/* 426 */         writeInt(localZipEntry.size);
/*     */       }
/*     */     }
/* 429 */     byte[] arrayOfByte = this.zc.getBytes(localZipEntry.name);
/* 430 */     writeShort(arrayOfByte.length);
/*     */     
/* 432 */     int m = 0;
/* 433 */     int n = 0;
/* 434 */     if (localZipEntry.mtime != null) {
/* 435 */       m += 4;
/* 436 */       n |= 0x1;
/*     */     }
/* 438 */     if (localZipEntry.atime != null) {
/* 439 */       m += 4;
/* 440 */       n |= 0x2;
/*     */     }
/* 442 */     if (localZipEntry.ctime != null) {
/* 443 */       m += 4;
/* 444 */       n |= 0x4;
/*     */     }
/* 446 */     if (n != 0)
/* 447 */       k += m + 5;
/* 448 */     writeShort(k);
/* 449 */     writeBytes(arrayOfByte, 0, arrayOfByte.length);
/* 450 */     if (j != 0) {
/* 451 */       writeShort(1);
/* 452 */       writeShort(16);
/* 453 */       writeLong(localZipEntry.size);
/* 454 */       writeLong(localZipEntry.csize);
/*     */     }
/* 456 */     if (n != 0) {
/* 457 */       writeShort(21589);
/* 458 */       writeShort(m + 1);
/* 459 */       writeByte(n);
/* 460 */       if (localZipEntry.mtime != null)
/* 461 */         writeInt(ZipUtils.fileTimeToUnixTime(localZipEntry.mtime));
/* 462 */       if (localZipEntry.atime != null)
/* 463 */         writeInt(ZipUtils.fileTimeToUnixTime(localZipEntry.atime));
/* 464 */       if (localZipEntry.ctime != null)
/* 465 */         writeInt(ZipUtils.fileTimeToUnixTime(localZipEntry.ctime));
/*     */     }
/* 467 */     writeExtra(localZipEntry.extra);
/* 468 */     this.locoff = this.written;
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeEXT(ZipEntry paramZipEntry)
/*     */     throws IOException
/*     */   {
/* 475 */     writeInt(134695760L);
/* 476 */     writeInt(paramZipEntry.crc);
/* 477 */     if ((paramZipEntry.csize >= 4294967295L) || (paramZipEntry.size >= 4294967295L)) {
/* 478 */       writeLong(paramZipEntry.csize);
/* 479 */       writeLong(paramZipEntry.size);
/*     */     } else {
/* 481 */       writeInt(paramZipEntry.csize);
/* 482 */       writeInt(paramZipEntry.size);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void writeCEN(XEntry paramXEntry)
/*     */     throws IOException
/*     */   {
/* 491 */     ZipEntry localZipEntry = paramXEntry.entry;
/* 492 */     int i = localZipEntry.flag;
/* 493 */     int j = version(localZipEntry);
/* 494 */     long l1 = localZipEntry.csize;
/* 495 */     long l2 = localZipEntry.size;
/* 496 */     long l3 = paramXEntry.offset;
/* 497 */     int k = 0;
/* 498 */     int m = 0;
/*     */     
/* 500 */     if (localZipEntry.csize >= 4294967295L) {
/* 501 */       l1 = 4294967295L;
/* 502 */       k += 8;
/* 503 */       m = 1;
/*     */     }
/* 505 */     if (localZipEntry.size >= 4294967295L) {
/* 506 */       l2 = 4294967295L;
/* 507 */       k += 8;
/* 508 */       m = 1;
/*     */     }
/* 510 */     if (paramXEntry.offset >= 4294967295L) {
/* 511 */       l3 = 4294967295L;
/* 512 */       k += 8;
/* 513 */       m = 1;
/*     */     }
/* 515 */     writeInt(33639248L);
/* 516 */     if (m != 0) {
/* 517 */       writeShort(45);
/* 518 */       writeShort(45);
/*     */     } else {
/* 520 */       writeShort(j);
/* 521 */       writeShort(j);
/*     */     }
/* 523 */     writeShort(i);
/* 524 */     writeShort(localZipEntry.method);
/*     */     
/*     */ 
/* 527 */     writeInt(paramXEntry.dostime);
/* 528 */     writeInt(localZipEntry.crc);
/* 529 */     writeInt(l1);
/* 530 */     writeInt(l2);
/* 531 */     byte[] arrayOfByte1 = this.zc.getBytes(localZipEntry.name);
/* 532 */     writeShort(arrayOfByte1.length);
/*     */     
/* 534 */     int n = getExtraLen(localZipEntry.extra);
/* 535 */     if (m != 0) {
/* 536 */       n += k + 4;
/*     */     }
/*     */     
/*     */ 
/* 540 */     int i1 = 0;
/* 541 */     if (localZipEntry.mtime != null) {
/* 542 */       n += 4;
/* 543 */       i1 |= 0x1;
/*     */     }
/* 545 */     if (localZipEntry.atime != null) {
/* 546 */       i1 |= 0x2;
/*     */     }
/* 548 */     if (localZipEntry.ctime != null) {
/* 549 */       i1 |= 0x4;
/*     */     }
/* 551 */     if (i1 != 0) {
/* 552 */       n += 5;
/*     */     }
/* 554 */     writeShort(n);
/*     */     byte[] arrayOfByte2;
/* 556 */     if (localZipEntry.comment != null) {
/* 557 */       arrayOfByte2 = this.zc.getBytes(localZipEntry.comment);
/* 558 */       writeShort(Math.min(arrayOfByte2.length, 65535));
/*     */     } else {
/* 560 */       arrayOfByte2 = null;
/* 561 */       writeShort(0);
/*     */     }
/* 563 */     writeShort(0);
/* 564 */     writeShort(0);
/* 565 */     writeInt(0L);
/* 566 */     writeInt(l3);
/* 567 */     writeBytes(arrayOfByte1, 0, arrayOfByte1.length);
/*     */     
/*     */ 
/* 570 */     if (m != 0) {
/* 571 */       writeShort(1);
/* 572 */       writeShort(k);
/* 573 */       if (l2 == 4294967295L)
/* 574 */         writeLong(localZipEntry.size);
/* 575 */       if (l1 == 4294967295L)
/* 576 */         writeLong(localZipEntry.csize);
/* 577 */       if (l3 == 4294967295L)
/* 578 */         writeLong(paramXEntry.offset);
/*     */     }
/* 580 */     if (i1 != 0) {
/* 581 */       writeShort(21589);
/* 582 */       if (localZipEntry.mtime != null) {
/* 583 */         writeShort(5);
/* 584 */         writeByte(i1);
/* 585 */         writeInt(ZipUtils.fileTimeToUnixTime(localZipEntry.mtime));
/*     */       } else {
/* 587 */         writeShort(1);
/* 588 */         writeByte(i1);
/*     */       }
/*     */     }
/* 591 */     writeExtra(localZipEntry.extra);
/* 592 */     if (arrayOfByte2 != null) {
/* 593 */       writeBytes(arrayOfByte2, 0, Math.min(arrayOfByte2.length, 65535));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeEND(long paramLong1, long paramLong2)
/*     */     throws IOException
/*     */   {
/* 601 */     int i = 0;
/* 602 */     long l1 = paramLong2;
/* 603 */     long l2 = paramLong1;
/* 604 */     if (l1 >= 4294967295L) {
/* 605 */       l1 = 4294967295L;
/* 606 */       i = 1;
/*     */     }
/* 608 */     if (l2 >= 4294967295L) {
/* 609 */       l2 = 4294967295L;
/* 610 */       i = 1;
/*     */     }
/* 612 */     int j = this.xentries.size();
/* 613 */     if (j >= 65535) {
/* 614 */       i |= (!inhibitZip64 ? 1 : 0);
/* 615 */       if (i != 0) {
/* 616 */         j = 65535;
/*     */       }
/*     */     }
/* 619 */     if (i != 0) {
/* 620 */       long l3 = this.written;
/*     */       
/* 622 */       writeInt(101075792L);
/* 623 */       writeLong(44L);
/* 624 */       writeShort(45);
/* 625 */       writeShort(45);
/* 626 */       writeInt(0L);
/* 627 */       writeInt(0L);
/* 628 */       writeLong(this.xentries.size());
/* 629 */       writeLong(this.xentries.size());
/* 630 */       writeLong(paramLong2);
/* 631 */       writeLong(paramLong1);
/*     */       
/*     */ 
/* 634 */       writeInt(117853008L);
/* 635 */       writeInt(0L);
/* 636 */       writeLong(l3);
/* 637 */       writeInt(1L);
/*     */     }
/* 639 */     writeInt(101010256L);
/* 640 */     writeShort(0);
/* 641 */     writeShort(0);
/* 642 */     writeShort(j);
/* 643 */     writeShort(j);
/* 644 */     writeInt(l1);
/* 645 */     writeInt(l2);
/* 646 */     if (this.comment != null) {
/* 647 */       writeShort(this.comment.length);
/* 648 */       writeBytes(this.comment, 0, this.comment.length);
/*     */     } else {
/* 650 */       writeShort(0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int getExtraLen(byte[] paramArrayOfByte)
/*     */   {
/* 658 */     if (paramArrayOfByte == null)
/* 659 */       return 0;
/* 660 */     int i = 0;
/* 661 */     int j = paramArrayOfByte.length;
/* 662 */     int k = 0;
/* 663 */     while (k + 4 <= j) {
/* 664 */       int m = ZipUtils.get16(paramArrayOfByte, k);
/* 665 */       int n = ZipUtils.get16(paramArrayOfByte, k + 2);
/* 666 */       if ((n < 0) || (k + 4 + n > j)) {
/*     */         break;
/*     */       }
/* 669 */       if ((m == 21589) || (m == 1)) {
/* 670 */         i += n + 4;
/*     */       }
/* 672 */       k += n + 4;
/*     */     }
/* 674 */     return j - i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeExtra(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 684 */     if (paramArrayOfByte != null) {
/* 685 */       int i = paramArrayOfByte.length;
/* 686 */       int j = 0;
/* 687 */       while (j + 4 <= i) {
/* 688 */         int k = ZipUtils.get16(paramArrayOfByte, j);
/* 689 */         int m = ZipUtils.get16(paramArrayOfByte, j + 2);
/* 690 */         if ((m < 0) || (j + 4 + m > i)) {
/* 691 */           writeBytes(paramArrayOfByte, j, i - j);
/* 692 */           return;
/*     */         }
/* 694 */         if ((k != 21589) && (k != 1)) {
/* 695 */           writeBytes(paramArrayOfByte, j, m + 4);
/*     */         }
/* 697 */         j += m + 4;
/*     */       }
/* 699 */       if (j < i) {
/* 700 */         writeBytes(paramArrayOfByte, j, i - j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeByte(int paramInt)
/*     */     throws IOException
/*     */   {
/* 709 */     OutputStream localOutputStream = this.out;
/* 710 */     localOutputStream.write(paramInt & 0xFF);
/* 711 */     this.written += 1L;
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeShort(int paramInt)
/*     */     throws IOException
/*     */   {
/* 718 */     OutputStream localOutputStream = this.out;
/* 719 */     localOutputStream.write(paramInt >>> 0 & 0xFF);
/* 720 */     localOutputStream.write(paramInt >>> 8 & 0xFF);
/* 721 */     this.written += 2L;
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeInt(long paramLong)
/*     */     throws IOException
/*     */   {
/* 728 */     OutputStream localOutputStream = this.out;
/* 729 */     localOutputStream.write((int)(paramLong >>> 0 & 0xFF));
/* 730 */     localOutputStream.write((int)(paramLong >>> 8 & 0xFF));
/* 731 */     localOutputStream.write((int)(paramLong >>> 16 & 0xFF));
/* 732 */     localOutputStream.write((int)(paramLong >>> 24 & 0xFF));
/* 733 */     this.written += 4L;
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeLong(long paramLong)
/*     */     throws IOException
/*     */   {
/* 740 */     OutputStream localOutputStream = this.out;
/* 741 */     localOutputStream.write((int)(paramLong >>> 0 & 0xFF));
/* 742 */     localOutputStream.write((int)(paramLong >>> 8 & 0xFF));
/* 743 */     localOutputStream.write((int)(paramLong >>> 16 & 0xFF));
/* 744 */     localOutputStream.write((int)(paramLong >>> 24 & 0xFF));
/* 745 */     localOutputStream.write((int)(paramLong >>> 32 & 0xFF));
/* 746 */     localOutputStream.write((int)(paramLong >>> 40 & 0xFF));
/* 747 */     localOutputStream.write((int)(paramLong >>> 48 & 0xFF));
/* 748 */     localOutputStream.write((int)(paramLong >>> 56 & 0xFF));
/* 749 */     this.written += 8L;
/*     */   }
/*     */   
/*     */ 
/*     */   private void writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 756 */     this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/* 757 */     this.written += paramInt2;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/ZipOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */