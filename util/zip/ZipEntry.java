/*     */ package java.util.zip;
/*     */ 
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZipEntry
/*     */   implements ZipConstants, Cloneable
/*     */ {
/*     */   String name;
/*  44 */   long time = -1L;
/*     */   FileTime mtime;
/*     */   FileTime atime;
/*     */   FileTime ctime;
/*  48 */   long crc = -1L;
/*  49 */   long size = -1L;
/*  50 */   long csize = -1L;
/*  51 */   int method = -1;
/*  52 */   int flag = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   byte[] extra;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String comment;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int STORED = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int DEFLATED = 8;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ZipEntry(String paramString)
/*     */   {
/*  77 */     Objects.requireNonNull(paramString, "name");
/*  78 */     if (paramString.length() > 65535) {
/*  79 */       throw new IllegalArgumentException("entry name too long");
/*     */     }
/*  81 */     this.name = paramString;
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
/*     */   public ZipEntry(ZipEntry paramZipEntry)
/*     */   {
/*  94 */     Objects.requireNonNull(paramZipEntry, "entry");
/*  95 */     this.name = paramZipEntry.name;
/*  96 */     this.time = paramZipEntry.time;
/*  97 */     this.mtime = paramZipEntry.mtime;
/*  98 */     this.atime = paramZipEntry.atime;
/*  99 */     this.ctime = paramZipEntry.ctime;
/* 100 */     this.crc = paramZipEntry.crc;
/* 101 */     this.size = paramZipEntry.size;
/* 102 */     this.csize = paramZipEntry.csize;
/* 103 */     this.method = paramZipEntry.method;
/* 104 */     this.flag = paramZipEntry.flag;
/* 105 */     this.extra = paramZipEntry.extra;
/* 106 */     this.comment = paramZipEntry.comment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   ZipEntry() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 119 */     return this.name;
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
/*     */   public void setTime(long paramLong)
/*     */   {
/* 140 */     this.time = paramLong;
/* 141 */     this.mtime = null;
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
/*     */   public long getTime()
/*     */   {
/* 161 */     return this.time;
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
/*     */   public ZipEntry setLastModifiedTime(FileTime paramFileTime)
/*     */   {
/* 183 */     Objects.requireNonNull(this.name, "time");
/* 184 */     this.mtime = paramFileTime;
/* 185 */     this.time = paramFileTime.to(TimeUnit.MILLISECONDS);
/* 186 */     return this;
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
/*     */   public FileTime getLastModifiedTime()
/*     */   {
/* 206 */     if (this.mtime != null)
/* 207 */       return this.mtime;
/* 208 */     if (this.time == -1L)
/* 209 */       return null;
/* 210 */     return FileTime.from(this.time, TimeUnit.MILLISECONDS);
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
/*     */   public ZipEntry setLastAccessTime(FileTime paramFileTime)
/*     */   {
/* 230 */     Objects.requireNonNull(this.name, "time");
/* 231 */     this.atime = paramFileTime;
/* 232 */     return this;
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
/*     */   public FileTime getLastAccessTime()
/*     */   {
/* 248 */     return this.atime;
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
/*     */   public ZipEntry setCreationTime(FileTime paramFileTime)
/*     */   {
/* 268 */     Objects.requireNonNull(this.name, "time");
/* 269 */     this.ctime = paramFileTime;
/* 270 */     return this;
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
/*     */   public FileTime getCreationTime()
/*     */   {
/* 285 */     return this.ctime;
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
/*     */   public void setSize(long paramLong)
/*     */   {
/* 300 */     if (paramLong < 0L) {
/* 301 */       throw new IllegalArgumentException("invalid entry size");
/*     */     }
/* 303 */     this.size = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getSize()
/*     */   {
/* 313 */     return this.size;
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
/*     */   public long getCompressedSize()
/*     */   {
/* 326 */     return this.csize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCompressedSize(long paramLong)
/*     */   {
/* 337 */     this.csize = paramLong;
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
/*     */   public void setCrc(long paramLong)
/*     */   {
/* 350 */     if ((paramLong < 0L) || (paramLong > 4294967295L)) {
/* 351 */       throw new IllegalArgumentException("invalid entry crc-32");
/*     */     }
/* 353 */     this.crc = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getCrc()
/*     */   {
/* 365 */     return this.crc;
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
/*     */   public void setMethod(int paramInt)
/*     */   {
/* 378 */     if ((paramInt != 0) && (paramInt != 8)) {
/* 379 */       throw new IllegalArgumentException("invalid compression method");
/*     */     }
/* 381 */     this.method = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMethod()
/*     */   {
/* 391 */     return this.method;
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
/*     */   public void setExtra(byte[] paramArrayOfByte)
/*     */   {
/* 413 */     setExtra0(paramArrayOfByte, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setExtra0(byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 425 */     if (paramArrayOfByte != null) {
/* 426 */       if (paramArrayOfByte.length > 65535) {
/* 427 */         throw new IllegalArgumentException("invalid extra field length");
/*     */       }
/*     */       
/* 430 */       int i = 0;
/* 431 */       int j = paramArrayOfByte.length;
/* 432 */       while (i + 4 < j) {
/* 433 */         int k = ZipUtils.get16(paramArrayOfByte, i);
/* 434 */         int m = ZipUtils.get16(paramArrayOfByte, i + 2);
/* 435 */         i += 4;
/* 436 */         if (i + m > j)
/*     */           break;
/* 438 */         switch (k) {
/*     */         case 1: 
/* 440 */           if (paramBoolean)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 447 */             if (m >= 16) {
/* 448 */               this.size = ZipUtils.get64(paramArrayOfByte, i);
/* 449 */               this.csize = ZipUtils.get64(paramArrayOfByte, i + 8);
/*     */             }
/*     */           }
/*     */           break;
/*     */         case 10: 
/* 454 */           int n = i + 4;
/* 455 */           if ((ZipUtils.get16(paramArrayOfByte, n) == 1) && (ZipUtils.get16(paramArrayOfByte, n + 2) == 24))
/*     */           {
/* 457 */             this.mtime = ZipUtils.winTimeToFileTime(ZipUtils.get64(paramArrayOfByte, n + 4));
/* 458 */             this.atime = ZipUtils.winTimeToFileTime(ZipUtils.get64(paramArrayOfByte, n + 12));
/* 459 */             this.ctime = ZipUtils.winTimeToFileTime(ZipUtils.get64(paramArrayOfByte, n + 20)); }
/* 460 */           break;
/*     */         case 21589: 
/* 462 */           int i1 = Byte.toUnsignedInt(paramArrayOfByte[i]);
/* 463 */           int i2 = 1;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 468 */           if (((i1 & 0x1) != 0) && (i2 + 4 <= m)) {
/* 469 */             this.mtime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(paramArrayOfByte, i + i2));
/* 470 */             i2 += 4;
/*     */           }
/* 472 */           if (((i1 & 0x2) != 0) && (i2 + 4 <= m)) {
/* 473 */             this.atime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(paramArrayOfByte, i + i2));
/* 474 */             i2 += 4;
/*     */           }
/* 476 */           if (((i1 & 0x4) != 0) && (i2 + 4 <= m)) {
/* 477 */             this.ctime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(paramArrayOfByte, i + i2));
/* 478 */             i2 += 4;
/*     */           }
/*     */           break;
/*     */         }
/*     */         
/* 483 */         i += m;
/*     */       }
/*     */     }
/* 486 */     this.extra = paramArrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getExtra()
/*     */   {
/* 497 */     return this.extra;
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
/*     */   public void setComment(String paramString)
/*     */   {
/* 512 */     this.comment = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getComment()
/*     */   {
/* 523 */     return this.comment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDirectory()
/*     */   {
/* 532 */     return this.name.endsWith("/");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 539 */     return getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 546 */     return this.name.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 554 */       ZipEntry localZipEntry = (ZipEntry)super.clone();
/* 555 */       localZipEntry.extra = (this.extra == null ? null : (byte[])this.extra.clone());
/* 556 */       return localZipEntry;
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 559 */       throw new InternalError(localCloneNotSupportedException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/ZipEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */