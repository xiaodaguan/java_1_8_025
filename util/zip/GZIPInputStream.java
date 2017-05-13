/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GZIPInputStream
/*     */   extends InflaterInputStream
/*     */ {
/*  48 */   protected CRC32 crc = new CRC32();
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean eos;
/*     */   
/*     */ 
/*  55 */   private boolean closed = false;
/*     */   public static final int GZIP_MAGIC = 35615;
/*     */   private static final int FTEXT = 1;
/*     */   private static final int FHCRC = 2;
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  61 */     if (this.closed) {
/*  62 */       throw new IOException("Stream closed");
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
/*     */   public GZIPInputStream(InputStream paramInputStream, int paramInt)
/*     */     throws IOException
/*     */   {
/*  77 */     super(paramInputStream, new Inflater(true), paramInt);
/*  78 */     this.usesDefaultInflater = true;
/*  79 */     readHeader(paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GZIPInputStream(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/*  91 */     this(paramInputStream, 512);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int FEXTRA = 4;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int FNAME = 8;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int FCOMMENT = 16;
/*     */   
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 113 */     ensureOpen();
/* 114 */     if (this.eos) {
/* 115 */       return -1;
/*     */     }
/* 117 */     int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
/* 118 */     if (i == -1) {
/* 119 */       if (readTrailer()) {
/* 120 */         this.eos = true;
/*     */       } else
/* 122 */         return read(paramArrayOfByte, paramInt1, paramInt2);
/*     */     } else {
/* 124 */       this.crc.update(paramArrayOfByte, paramInt1, i);
/*     */     }
/* 126 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 135 */     if (!this.closed) {
/* 136 */       super.close();
/* 137 */       this.eos = true;
/* 138 */       this.closed = true;
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
/*     */   private int readHeader(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 161 */     CheckedInputStream localCheckedInputStream = new CheckedInputStream(paramInputStream, this.crc);
/* 162 */     this.crc.reset();
/*     */     
/* 164 */     if (readUShort(localCheckedInputStream) != 35615) {
/* 165 */       throw new ZipException("Not in GZIP format");
/*     */     }
/*     */     
/* 168 */     if (readUByte(localCheckedInputStream) != 8) {
/* 169 */       throw new ZipException("Unsupported compression method");
/*     */     }
/*     */     
/* 172 */     int i = readUByte(localCheckedInputStream);
/*     */     
/* 174 */     skipBytes(localCheckedInputStream, 6);
/* 175 */     int j = 10;
/*     */     int k;
/* 177 */     if ((i & 0x4) == 4) {
/* 178 */       k = readUShort(localCheckedInputStream);
/* 179 */       skipBytes(localCheckedInputStream, k);
/* 180 */       j += k + 2;
/*     */     }
/*     */     
/* 183 */     if ((i & 0x8) == 8) {
/*     */       do {
/* 185 */         j++;
/* 186 */       } while (readUByte(localCheckedInputStream) != 0);
/*     */     }
/*     */     
/* 189 */     if ((i & 0x10) == 16) {
/*     */       do {
/* 191 */         j++;
/* 192 */       } while (readUByte(localCheckedInputStream) != 0);
/*     */     }
/*     */     
/* 195 */     if ((i & 0x2) == 2) {
/* 196 */       k = (int)this.crc.getValue() & 0xFFFF;
/* 197 */       if (readUShort(localCheckedInputStream) != k) {
/* 198 */         throw new ZipException("Corrupt GZIP header");
/*     */       }
/* 200 */       j += 2;
/*     */     }
/* 202 */     this.crc.reset();
/* 203 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean readTrailer()
/*     */     throws IOException
/*     */   {
/* 212 */     Object localObject = this.in;
/* 213 */     int i = this.inf.getRemaining();
/* 214 */     if (i > 0) {
/* 215 */       localObject = new SequenceInputStream(new ByteArrayInputStream(this.buf, this.len - i, i), new FilterInputStream((InputStream)localObject)
/*     */       {
/*     */         public void close()
/*     */           throws IOException
/*     */         {}
/*     */       });
/*     */     }
/* 222 */     if ((readUInt((InputStream)localObject) != this.crc.getValue()) || 
/*     */     
/* 224 */       (readUInt((InputStream)localObject) != (this.inf.getBytesWritten() & 0xFFFFFFFF))) {
/* 225 */       throw new ZipException("Corrupt GZIP trailer");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 231 */     if ((this.in.available() > 0) || (i > 26)) {
/* 232 */       int j = 8;
/*     */       try {
/* 234 */         j += readHeader((InputStream)localObject);
/*     */       } catch (IOException localIOException) {
/* 236 */         return true;
/*     */       }
/* 238 */       this.inf.reset();
/* 239 */       if (i > j)
/* 240 */         this.inf.setInput(this.buf, this.len - i + j, i - j);
/* 241 */       return false;
/*     */     }
/* 243 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private long readUInt(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 250 */     long l = readUShort(paramInputStream);
/* 251 */     return readUShort(paramInputStream) << 16 | l;
/*     */   }
/*     */   
/*     */ 
/*     */   private int readUShort(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 258 */     int i = readUByte(paramInputStream);
/* 259 */     return readUByte(paramInputStream) << 8 | i;
/*     */   }
/*     */   
/*     */ 
/*     */   private int readUByte(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 266 */     int i = paramInputStream.read();
/* 267 */     if (i == -1) {
/* 268 */       throw new EOFException();
/*     */     }
/* 270 */     if ((i < -1) || (i > 255))
/*     */     {
/* 272 */       throw new IOException(this.in.getClass().getName() + ".read() returned value out of range -1..255: " + i);
/*     */     }
/*     */     
/* 275 */     return i;
/*     */   }
/*     */   
/* 278 */   private byte[] tmpbuf = new byte[''];
/*     */   
/*     */ 
/*     */ 
/*     */   private void skipBytes(InputStream paramInputStream, int paramInt)
/*     */     throws IOException
/*     */   {
/* 285 */     while (paramInt > 0) {
/* 286 */       int i = paramInputStream.read(this.tmpbuf, 0, paramInt < this.tmpbuf.length ? paramInt : this.tmpbuf.length);
/* 287 */       if (i == -1) {
/* 288 */         throw new EOFException();
/*     */       }
/* 290 */       paramInt -= i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/GZIPInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */