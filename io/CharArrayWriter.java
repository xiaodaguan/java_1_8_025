/*     */ package java.io;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharArrayWriter
/*     */   extends Writer
/*     */ {
/*     */   protected char[] buf;
/*     */   protected int count;
/*     */   
/*     */   public CharArrayWriter()
/*     */   {
/*  58 */     this(32);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayWriter(int paramInt)
/*     */   {
/*  68 */     if (paramInt < 0) {
/*  69 */       throw new IllegalArgumentException("Negative initial size: " + paramInt);
/*     */     }
/*     */     
/*  72 */     this.buf = new char[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */   {
/*  79 */     synchronized (this.lock) {
/*  80 */       int i = this.count + 1;
/*  81 */       if (i > this.buf.length) {
/*  82 */         this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, i));
/*     */       }
/*  84 */       this.buf[this.count] = ((char)paramInt);
/*  85 */       this.count = i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/*  96 */     if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0))
/*     */     {
/*  98 */       throw new IndexOutOfBoundsException(); }
/*  99 */     if (paramInt2 == 0) {
/* 100 */       return;
/*     */     }
/* 102 */     synchronized (this.lock) {
/* 103 */       int i = this.count + paramInt2;
/* 104 */       if (i > this.buf.length) {
/* 105 */         this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, i));
/*     */       }
/* 107 */       System.arraycopy(paramArrayOfChar, paramInt1, this.buf, this.count, paramInt2);
/* 108 */       this.count = i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 119 */     synchronized (this.lock) {
/* 120 */       int i = this.count + paramInt2;
/* 121 */       if (i > this.buf.length) {
/* 122 */         this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, i));
/*     */       }
/* 124 */       paramString.getChars(paramInt1, paramInt1 + paramInt2, this.buf, this.count);
/* 125 */       this.count = i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTo(Writer paramWriter)
/*     */     throws IOException
/*     */   {
/* 136 */     synchronized (this.lock) {
/* 137 */       paramWriter.write(this.buf, 0, this.count);
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
/*     */   public CharArrayWriter append(CharSequence paramCharSequence)
/*     */   {
/* 166 */     String str = paramCharSequence == null ? "null" : paramCharSequence.toString();
/* 167 */     write(str, 0, str.length());
/* 168 */     return this;
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
/*     */   public CharArrayWriter append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/* 204 */     String str = (paramCharSequence == null ? "null" : paramCharSequence).subSequence(paramInt1, paramInt2).toString();
/* 205 */     write(str, 0, str.length());
/* 206 */     return this;
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
/*     */   public CharArrayWriter append(char paramChar)
/*     */   {
/* 226 */     write(paramChar);
/* 227 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 235 */     this.count = 0;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public char[] toCharArray()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 12	java/io/CharArrayWriter:lock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 11	java/io/CharArrayWriter:buf	[C
/*     */     //   11: aload_0
/*     */     //   12: getfield 13	java/io/CharArrayWriter:count	I
/*     */     //   15: invokestatic 15	java/util/Arrays:copyOf	([CI)[C
/*     */     //   18: aload_1
/*     */     //   19: monitorexit
/*     */     //   20: areturn
/*     */     //   21: astore_2
/*     */     //   22: aload_1
/*     */     //   23: monitorexit
/*     */     //   24: aload_2
/*     */     //   25: athrow
/*     */     // Line number table:
/*     */     //   Java source line #244	-> byte code offset #0
/*     */     //   Java source line #245	-> byte code offset #7
/*     */     //   Java source line #246	-> byte code offset #21
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	26	0	this	CharArrayWriter
/*     */     //   5	18	1	Ljava/lang/Object;	Object
/*     */     //   21	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	20	21	finally
/*     */     //   21	24	21	finally
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/* 255 */     return this.count;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public String toString()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 12	java/io/CharArrayWriter:lock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: new 27	java/lang/String
/*     */     //   10: dup
/*     */     //   11: aload_0
/*     */     //   12: getfield 11	java/io/CharArrayWriter:buf	[C
/*     */     //   15: iconst_0
/*     */     //   16: aload_0
/*     */     //   17: getfield 13	java/io/CharArrayWriter:count	I
/*     */     //   20: invokespecial 28	java/lang/String:<init>	([CII)V
/*     */     //   23: aload_1
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_2
/*     */     //   27: aload_1
/*     */     //   28: monitorexit
/*     */     //   29: aload_2
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #263	-> byte code offset #0
/*     */     //   Java source line #264	-> byte code offset #7
/*     */     //   Java source line #265	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	CharArrayWriter
/*     */     //   5	23	1	Ljava/lang/Object;	Object
/*     */     //   26	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   public void flush() {}
/*     */   
/*     */   public void close() {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/CharArrayWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */