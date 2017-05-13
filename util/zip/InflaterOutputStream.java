/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InflaterOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   protected final Inflater inf;
/*     */   protected final byte[] buf;
/*  52 */   private final byte[] wbuf = new byte[1];
/*     */   
/*     */ 
/*  55 */   private boolean usesDefaultInflater = false;
/*     */   
/*     */ 
/*  58 */   private boolean closed = false;
/*     */   
/*     */ 
/*     */   private void ensureOpen()
/*     */     throws IOException
/*     */   {
/*  64 */     if (this.closed) {
/*  65 */       throw new IOException("Stream closed");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InflaterOutputStream(OutputStream paramOutputStream)
/*     */   {
/*  77 */     this(paramOutputStream, new Inflater());
/*  78 */     this.usesDefaultInflater = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InflaterOutputStream(OutputStream paramOutputStream, Inflater paramInflater)
/*     */   {
/*  90 */     this(paramOutputStream, paramInflater, 512);
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
/*     */   public InflaterOutputStream(OutputStream paramOutputStream, Inflater paramInflater, int paramInt)
/*     */   {
/* 104 */     super(paramOutputStream);
/*     */     
/*     */ 
/* 107 */     if (paramOutputStream == null)
/* 108 */       throw new NullPointerException("Null output");
/* 109 */     if (paramInflater == null)
/* 110 */       throw new NullPointerException("Null inflater");
/* 111 */     if (paramInt <= 0) {
/* 112 */       throw new IllegalArgumentException("Buffer size < 1");
/*     */     }
/*     */     
/* 115 */     this.inf = paramInflater;
/* 116 */     this.buf = new byte[paramInt];
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 1	java/util/zip/InflaterOutputStream:closed	Z
/*     */     //   4: ifne +37 -> 41
/*     */     //   7: aload_0
/*     */     //   8: invokevirtual 21	java/util/zip/InflaterOutputStream:finish	()V
/*     */     //   11: aload_0
/*     */     //   12: getfield 22	java/util/zip/InflaterOutputStream:out	Ljava/io/OutputStream;
/*     */     //   15: invokevirtual 23	java/io/OutputStream:close	()V
/*     */     //   18: aload_0
/*     */     //   19: iconst_1
/*     */     //   20: putfield 1	java/util/zip/InflaterOutputStream:closed	Z
/*     */     //   23: goto +18 -> 41
/*     */     //   26: astore_1
/*     */     //   27: aload_0
/*     */     //   28: getfield 22	java/util/zip/InflaterOutputStream:out	Ljava/io/OutputStream;
/*     */     //   31: invokevirtual 23	java/io/OutputStream:close	()V
/*     */     //   34: aload_0
/*     */     //   35: iconst_1
/*     */     //   36: putfield 1	java/util/zip/InflaterOutputStream:closed	Z
/*     */     //   39: aload_1
/*     */     //   40: athrow
/*     */     //   41: return
/*     */     // Line number table:
/*     */     //   Java source line #126	-> byte code offset #0
/*     */     //   Java source line #129	-> byte code offset #7
/*     */     //   Java source line #131	-> byte code offset #11
/*     */     //   Java source line #132	-> byte code offset #18
/*     */     //   Java source line #133	-> byte code offset #23
/*     */     //   Java source line #131	-> byte code offset #26
/*     */     //   Java source line #132	-> byte code offset #34
/*     */     //   Java source line #135	-> byte code offset #41
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	42	0	this	InflaterOutputStream
/*     */     //   26	14	1	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	11	26	finally
/*     */   }
/*     */   
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 145 */     ensureOpen();
/*     */     
/*     */ 
/* 148 */     if (!this.inf.finished()) {
/*     */       try {
/* 150 */         while ((!this.inf.finished()) && (!this.inf.needsInput()))
/*     */         {
/*     */ 
/*     */ 
/* 154 */           int i = this.inf.inflate(this.buf, 0, this.buf.length);
/* 155 */           if (i < 1) {
/*     */             break;
/*     */           }
/*     */           
/*     */ 
/* 160 */           this.out.write(this.buf, 0, i);
/*     */         }
/* 162 */         super.flush();
/*     */       }
/*     */       catch (DataFormatException localDataFormatException) {
/* 165 */         String str = localDataFormatException.getMessage();
/* 166 */         if (str == null) {
/* 167 */           str = "Invalid ZLIB data format";
/*     */         }
/* 169 */         throw new ZipException(str);
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
/*     */   public void finish()
/*     */     throws IOException
/*     */   {
/* 183 */     ensureOpen();
/*     */     
/*     */ 
/* 186 */     flush();
/* 187 */     if (this.usesDefaultInflater) {
/* 188 */       this.inf.end();
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
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 203 */     this.wbuf[0] = ((byte)paramInt);
/* 204 */     write(this.wbuf, 0, 1);
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
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 223 */     ensureOpen();
/* 224 */     if (paramArrayOfByte == null)
/* 225 */       throw new NullPointerException("Null buffer for read");
/* 226 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1))
/* 227 */       throw new IndexOutOfBoundsException();
/* 228 */     if (paramInt2 == 0) {
/* 229 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/*     */       for (;;)
/*     */       {
/* 238 */         if (this.inf.needsInput())
/*     */         {
/*     */ 
/* 241 */           if (paramInt2 < 1) {
/*     */             break;
/*     */           }
/*     */           
/* 245 */           int j = paramInt2 < 512 ? paramInt2 : 512;
/* 246 */           this.inf.setInput(paramArrayOfByte, paramInt1, j);
/* 247 */           paramInt1 += j;
/* 248 */           paramInt2 -= j;
/*     */         }
/*     */         int i;
/*     */         do
/*     */         {
/* 253 */           i = this.inf.inflate(this.buf, 0, this.buf.length);
/* 254 */           if (i > 0) {
/* 255 */             this.out.write(this.buf, 0, i);
/*     */           }
/* 257 */         } while (i > 0);
/*     */         
/*     */ 
/* 260 */         if (this.inf.finished()) {
/*     */           break;
/*     */         }
/* 263 */         if (this.inf.needsDictionary()) {
/* 264 */           throw new ZipException("ZLIB dictionary missing");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (DataFormatException localDataFormatException) {
/* 269 */       String str = localDataFormatException.getMessage();
/* 270 */       if (str == null) {
/* 271 */         str = "Invalid ZLIB data format";
/*     */       }
/* 273 */       throw new ZipException(str);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/zip/InflaterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */