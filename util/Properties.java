/*      */ package java.util;
/*      */ 
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import jdk.internal.util.xml.BasicXmlPropertiesProvider;
/*      */ import sun.util.spi.XmlPropertiesProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Properties
/*      */   extends Hashtable<Object, Object>
/*      */ {
/*      */   private static final long serialVersionUID = 4112578634029874840L;
/*      */   protected Properties defaults;
/*      */   
/*      */   public Properties()
/*      */   {
/*  140 */     this(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties(Properties paramProperties)
/*      */   {
/*  149 */     this.defaults = paramProperties;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Object setProperty(String paramString1, String paramString2)
/*      */   {
/*  166 */     return put(paramString1, paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void load(Reader paramReader)
/*      */     throws IOException
/*      */   {
/*  317 */     load0(new LineReader(paramReader));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void load(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  341 */     load0(new LineReader(paramInputStream));
/*      */   }
/*      */   
/*      */   private void load0(LineReader paramLineReader) throws IOException {
/*  345 */     char[] arrayOfChar = new char['Ѐ'];
/*      */     
/*      */ 
/*      */ 
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*  353 */     while ((i = paramLineReader.readLine()) >= 0) {
/*  354 */       int m = 0;
/*  355 */       int j = 0;
/*  356 */       int k = i;
/*  357 */       int n = 0;
/*      */       
/*      */ 
/*  360 */       int i1 = 0;
/*  361 */       while (j < i) {
/*  362 */         m = paramLineReader.lineBuf[j];
/*      */         
/*  364 */         if (((m == 61) || (m == 58)) && (i1 == 0)) {
/*  365 */           k = j + 1;
/*  366 */           n = 1;
/*  367 */           break; }
/*  368 */         if (((m == 32) || (m == 9) || (m == 12)) && (i1 == 0)) {
/*  369 */           k = j + 1;
/*  370 */           break;
/*      */         }
/*  372 */         if (m == 92) {
/*  373 */           i1 = i1 == 0 ? 1 : 0;
/*      */         } else {
/*  375 */           i1 = 0;
/*      */         }
/*  377 */         j++;
/*      */       }
/*  379 */       while (k < i) {
/*  380 */         m = paramLineReader.lineBuf[k];
/*  381 */         if ((m != 32) && (m != 9) && (m != 12)) {
/*  382 */           if ((n != 0) || ((m != 61) && (m != 58))) break;
/*  383 */           n = 1;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  388 */         k++;
/*      */       }
/*  390 */       String str1 = loadConvert(paramLineReader.lineBuf, 0, j, arrayOfChar);
/*  391 */       String str2 = loadConvert(paramLineReader.lineBuf, k, i - k, arrayOfChar);
/*  392 */       put(str1, str2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   class LineReader
/*      */   {
/*      */     byte[] inByteBuf;
/*      */     char[] inCharBuf;
/*      */     
/*      */     public LineReader(InputStream paramInputStream)
/*      */     {
/*  404 */       this.inStream = paramInputStream;
/*  405 */       this.inByteBuf = new byte[' '];
/*      */     }
/*      */     
/*      */     public LineReader(Reader paramReader) {
/*  409 */       this.reader = paramReader;
/*  410 */       this.inCharBuf = new char[' '];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  415 */     char[] lineBuf = new char['Ѐ'];
/*  416 */     int inLimit = 0;
/*  417 */     int inOff = 0;
/*      */     InputStream inStream;
/*      */     Reader reader;
/*      */     
/*      */     int readLine() throws IOException {
/*  422 */       int i = 0;
/*  423 */       int j = 0;
/*      */       
/*  425 */       int k = 1;
/*  426 */       int m = 0;
/*  427 */       int n = 1;
/*  428 */       int i1 = 0;
/*  429 */       int i2 = 0;
/*  430 */       int i3 = 0;
/*      */       for (;;)
/*      */       {
/*  433 */         if (this.inOff >= this.inLimit)
/*      */         {
/*  435 */           this.inLimit = (this.inStream == null ? this.reader.read(this.inCharBuf) : this.inStream.read(this.inByteBuf));
/*  436 */           this.inOff = 0;
/*  437 */           if (this.inLimit <= 0) {
/*  438 */             if ((i == 0) || (m != 0)) {
/*  439 */               return -1;
/*      */             }
/*  441 */             if (i2 != 0) {
/*  442 */               i--;
/*      */             }
/*  444 */             return i;
/*      */           }
/*      */         }
/*  447 */         if (this.inStream != null)
/*      */         {
/*      */ 
/*  450 */           j = (char)(0xFF & this.inByteBuf[(this.inOff++)]);
/*      */         } else {
/*  452 */           j = this.inCharBuf[(this.inOff++)];
/*      */         }
/*  454 */         if (i3 != 0) {
/*  455 */           i3 = 0;
/*  456 */           if (j == 10) {}
/*      */ 
/*      */ 
/*      */         }
/*  460 */         else if (k != 0) {
/*  461 */           if ((j != 32) && (j != 9) && (j != 12) && (
/*      */           
/*      */ 
/*  464 */             (i1 != 0) || ((j != 13) && (j != 10))))
/*      */           {
/*      */ 
/*  467 */             k = 0;
/*  468 */             i1 = 0;
/*      */           }
/*  470 */         } else { if (n != 0) {
/*  471 */             n = 0;
/*  472 */             if ((j == 35) || (j == 33)) {
/*  473 */               m = 1;
/*  474 */               continue;
/*      */             }
/*      */           }
/*      */           
/*  478 */           if ((j != 10) && (j != 13)) {
/*  479 */             this.lineBuf[(i++)] = j;
/*  480 */             if (i == this.lineBuf.length) {
/*  481 */               int i4 = this.lineBuf.length * 2;
/*  482 */               if (i4 < 0) {
/*  483 */                 i4 = Integer.MAX_VALUE;
/*      */               }
/*  485 */               char[] arrayOfChar = new char[i4];
/*  486 */               System.arraycopy(this.lineBuf, 0, arrayOfChar, 0, this.lineBuf.length);
/*  487 */               this.lineBuf = arrayOfChar;
/*      */             }
/*      */             
/*  490 */             if (j == 92) {
/*  491 */               i2 = i2 == 0 ? 1 : 0;
/*      */             } else {
/*  493 */               i2 = 0;
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*  498 */           else if ((m != 0) || (i == 0)) {
/*  499 */             m = 0;
/*  500 */             n = 1;
/*  501 */             k = 1;
/*  502 */             i = 0;
/*      */           }
/*      */           else {
/*  505 */             if (this.inOff >= this.inLimit)
/*      */             {
/*      */ 
/*  508 */               this.inLimit = (this.inStream == null ? this.reader.read(this.inCharBuf) : this.inStream.read(this.inByteBuf));
/*  509 */               this.inOff = 0;
/*  510 */               if (this.inLimit <= 0) {
/*  511 */                 if (i2 != 0) {
/*  512 */                   i--;
/*      */                 }
/*  514 */                 return i;
/*      */               }
/*      */             }
/*  517 */             if (i2 == 0) break;
/*  518 */             i--;
/*      */             
/*  520 */             k = 1;
/*  521 */             i1 = 1;
/*  522 */             i2 = 0;
/*  523 */             if (j == 13)
/*  524 */               i3 = 1;
/*      */           }
/*      */         } }
/*  527 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String loadConvert(char[] paramArrayOfChar1, int paramInt1, int paramInt2, char[] paramArrayOfChar2)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*  539 */     if (paramArrayOfChar2.length < paramInt2) {
/*  540 */       i = paramInt2 * 2;
/*  541 */       if (i < 0) {
/*  542 */         i = Integer.MAX_VALUE;
/*      */       }
/*  544 */       paramArrayOfChar2 = new char[i];
/*      */     }
/*      */     
/*  547 */     char[] arrayOfChar = paramArrayOfChar2;
/*  548 */     int j = 0;
/*  549 */     int k = paramInt1 + paramInt2;
/*      */     
/*  551 */     while (paramInt1 < k) {
/*  552 */       i = paramArrayOfChar1[(paramInt1++)];
/*  553 */       if (i == 92) {
/*  554 */         i = paramArrayOfChar1[(paramInt1++)];
/*  555 */         if (i == 117)
/*      */         {
/*  557 */           int m = 0;
/*  558 */           for (int n = 0; n < 4; n++) {
/*  559 */             i = paramArrayOfChar1[(paramInt1++)];
/*  560 */             switch (i) {
/*      */             case 48: case 49: case 50: case 51: case 52: 
/*      */             case 53: case 54: case 55: case 56: case 57: 
/*  563 */               m = (m << 4) + i - 48;
/*  564 */               break;
/*      */             case 97: case 98: case 99: 
/*      */             case 100: case 101: case 102: 
/*  567 */               m = (m << 4) + 10 + i - 97;
/*  568 */               break;
/*      */             case 65: case 66: case 67: 
/*      */             case 68: case 69: case 70: 
/*  571 */               m = (m << 4) + 10 + i - 65;
/*  572 */               break;
/*      */             case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 80: case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89: case 90: case 91: case 92: case 93: case 94: case 95: case 96: default: 
/*  574 */               throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
/*      */             }
/*      */             
/*      */           }
/*  578 */           arrayOfChar[(j++)] = ((char)m);
/*      */         } else {
/*  580 */           if (i == 116) { i = 9;
/*  581 */           } else if (i == 114) { i = 13;
/*  582 */           } else if (i == 110) { i = 10;
/*  583 */           } else if (i == 102) i = 12;
/*  584 */           arrayOfChar[(j++)] = i;
/*      */         }
/*      */       } else {
/*  587 */         arrayOfChar[(j++)] = i;
/*      */       }
/*      */     }
/*  590 */     return new String(arrayOfChar, 0, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String saveConvert(String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  600 */     int i = paramString.length();
/*  601 */     int j = i * 2;
/*  602 */     if (j < 0) {
/*  603 */       j = Integer.MAX_VALUE;
/*      */     }
/*  605 */     StringBuffer localStringBuffer = new StringBuffer(j);
/*      */     
/*  607 */     for (int k = 0; k < i; k++) {
/*  608 */       char c = paramString.charAt(k);
/*      */       
/*      */ 
/*  611 */       if ((c > '=') && (c < '')) {
/*  612 */         if (c == '\\') {
/*  613 */           localStringBuffer.append('\\');localStringBuffer.append('\\');
/*      */         }
/*      */         else {
/*  616 */           localStringBuffer.append(c);
/*      */         }
/*      */       } else
/*  619 */         switch (c) {
/*      */         case ' ': 
/*  621 */           if ((k == 0) || (paramBoolean1))
/*  622 */             localStringBuffer.append('\\');
/*  623 */           localStringBuffer.append(' ');
/*  624 */           break;
/*  625 */         case '\t':  localStringBuffer.append('\\');localStringBuffer.append('t');
/*  626 */           break;
/*  627 */         case '\n':  localStringBuffer.append('\\');localStringBuffer.append('n');
/*  628 */           break;
/*  629 */         case '\r':  localStringBuffer.append('\\');localStringBuffer.append('r');
/*  630 */           break;
/*  631 */         case '\f':  localStringBuffer.append('\\');localStringBuffer.append('f');
/*  632 */           break;
/*      */         case '!': 
/*      */         case '#': 
/*      */         case ':': 
/*      */         case '=': 
/*  637 */           localStringBuffer.append('\\');localStringBuffer.append(c);
/*  638 */           break;
/*      */         default: 
/*  640 */           if ((((c < ' ') || (c > '~')) & paramBoolean2)) {
/*  641 */             localStringBuffer.append('\\');
/*  642 */             localStringBuffer.append('u');
/*  643 */             localStringBuffer.append(toHex(c >> '\f' & 0xF));
/*  644 */             localStringBuffer.append(toHex(c >> '\b' & 0xF));
/*  645 */             localStringBuffer.append(toHex(c >> '\004' & 0xF));
/*  646 */             localStringBuffer.append(toHex(c & 0xF));
/*      */           } else {
/*  648 */             localStringBuffer.append(c);
/*      */           }
/*      */           break; }
/*      */     }
/*  652 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   private static void writeComments(BufferedWriter paramBufferedWriter, String paramString) throws IOException
/*      */   {
/*  657 */     paramBufferedWriter.write("#");
/*  658 */     int i = paramString.length();
/*  659 */     int j = 0;
/*  660 */     int k = 0;
/*  661 */     char[] arrayOfChar = new char[6];
/*  662 */     arrayOfChar[0] = '\\';
/*  663 */     arrayOfChar[1] = 'u';
/*  664 */     while (j < i) {
/*  665 */       int m = paramString.charAt(j);
/*  666 */       if ((m > 255) || (m == 10) || (m == 13)) {
/*  667 */         if (k != j)
/*  668 */           paramBufferedWriter.write(paramString.substring(k, j));
/*  669 */         if (m > 255) {
/*  670 */           arrayOfChar[2] = toHex(m >> 12 & 0xF);
/*  671 */           arrayOfChar[3] = toHex(m >> 8 & 0xF);
/*  672 */           arrayOfChar[4] = toHex(m >> 4 & 0xF);
/*  673 */           arrayOfChar[5] = toHex(m & 0xF);
/*  674 */           paramBufferedWriter.write(new String(arrayOfChar));
/*      */         } else {
/*  676 */           paramBufferedWriter.newLine();
/*  677 */           if ((m == 13) && (j != i - 1))
/*      */           {
/*  679 */             if (paramString.charAt(j + 1) == '\n')
/*  680 */               j++;
/*      */           }
/*  682 */           if ((j == i - 1) || (
/*  683 */             (paramString.charAt(j + 1) != '#') && 
/*  684 */             (paramString.charAt(j + 1) != '!')))
/*  685 */             paramBufferedWriter.write("#");
/*      */         }
/*  687 */         k = j + 1;
/*      */       }
/*  689 */       j++;
/*      */     }
/*  691 */     if (k != j)
/*  692 */       paramBufferedWriter.write(paramString.substring(k, j));
/*  693 */     paramBufferedWriter.newLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public void save(OutputStream paramOutputStream, String paramString)
/*      */   {
/*      */     try
/*      */     {
/*  715 */       store(paramOutputStream, paramString);
/*      */     }
/*      */     catch (IOException localIOException) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void store(Writer paramWriter, String paramString)
/*      */     throws IOException
/*      */   {
/*  771 */     store0((paramWriter instanceof BufferedWriter) ? (BufferedWriter)paramWriter : new BufferedWriter(paramWriter), paramString, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void store(OutputStream paramOutputStream, String paramString)
/*      */     throws IOException
/*      */   {
/*  818 */     store0(new BufferedWriter(new OutputStreamWriter(paramOutputStream, "8859_1")), paramString, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void store0(BufferedWriter paramBufferedWriter, String paramString, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  826 */     if (paramString != null) {
/*  827 */       writeComments(paramBufferedWriter, paramString);
/*      */     }
/*  829 */     paramBufferedWriter.write("#" + new Date().toString());
/*  830 */     paramBufferedWriter.newLine();
/*  831 */     Enumeration localEnumeration; synchronized (this) {
/*  832 */       for (localEnumeration = keys(); localEnumeration.hasMoreElements();) {
/*  833 */         String str1 = (String)localEnumeration.nextElement();
/*  834 */         String str2 = (String)get(str1);
/*  835 */         str1 = saveConvert(str1, true, paramBoolean);
/*      */         
/*      */ 
/*      */ 
/*  839 */         str2 = saveConvert(str2, false, paramBoolean);
/*  840 */         paramBufferedWriter.write(str1 + "=" + str2);
/*  841 */         paramBufferedWriter.newLine();
/*      */       }
/*      */     }
/*  844 */     paramBufferedWriter.flush();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void loadFromXML(InputStream paramInputStream)
/*      */     throws IOException, InvalidPropertiesFormatException
/*      */   {
/*  881 */     XmlSupport.load(this, (InputStream)Objects.requireNonNull(paramInputStream));
/*  882 */     paramInputStream.close();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void storeToXML(OutputStream paramOutputStream, String paramString)
/*      */     throws IOException
/*      */   {
/*  908 */     storeToXML(paramOutputStream, paramString, "UTF-8");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void storeToXML(OutputStream paramOutputStream, String paramString1, String paramString2)
/*      */     throws IOException
/*      */   {
/*  953 */     XmlSupport.save(this, (OutputStream)Objects.requireNonNull(paramOutputStream), paramString1, 
/*  954 */       (String)Objects.requireNonNull(paramString2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getProperty(String paramString)
/*      */   {
/*  969 */     Object localObject = super.get(paramString);
/*  970 */     String str = (localObject instanceof String) ? (String)localObject : null;
/*  971 */     return (str == null) && (this.defaults != null) ? this.defaults.getProperty(paramString) : str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getProperty(String paramString1, String paramString2)
/*      */   {
/*  988 */     String str = getProperty(paramString1);
/*  989 */     return str == null ? paramString2 : str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<?> propertyNames()
/*      */   {
/* 1007 */     Hashtable localHashtable = new Hashtable();
/* 1008 */     enumerate(localHashtable);
/* 1009 */     return localHashtable.keys();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<String> stringPropertyNames()
/*      */   {
/* 1031 */     Hashtable localHashtable = new Hashtable();
/* 1032 */     enumerateStringProperties(localHashtable);
/* 1033 */     return localHashtable.keySet();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void list(PrintStream paramPrintStream)
/*      */   {
/* 1045 */     paramPrintStream.println("-- listing properties --");
/* 1046 */     Hashtable localHashtable = new Hashtable();
/* 1047 */     enumerate(localHashtable);
/* 1048 */     for (Enumeration localEnumeration = localHashtable.keys(); localEnumeration.hasMoreElements();) {
/* 1049 */       String str1 = (String)localEnumeration.nextElement();
/* 1050 */       String str2 = (String)localHashtable.get(str1);
/* 1051 */       if (str2.length() > 40) {
/* 1052 */         str2 = str2.substring(0, 37) + "...";
/*      */       }
/* 1054 */       paramPrintStream.println(str1 + "=" + str2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void list(PrintWriter paramPrintWriter)
/*      */   {
/* 1073 */     paramPrintWriter.println("-- listing properties --");
/* 1074 */     Hashtable localHashtable = new Hashtable();
/* 1075 */     enumerate(localHashtable);
/* 1076 */     for (Enumeration localEnumeration = localHashtable.keys(); localEnumeration.hasMoreElements();) {
/* 1077 */       String str1 = (String)localEnumeration.nextElement();
/* 1078 */       String str2 = (String)localHashtable.get(str1);
/* 1079 */       if (str2.length() > 40) {
/* 1080 */         str2 = str2.substring(0, 37) + "...";
/*      */       }
/* 1082 */       paramPrintWriter.println(str1 + "=" + str2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void enumerate(Hashtable<String, Object> paramHashtable)
/*      */   {
/* 1093 */     if (this.defaults != null) {
/* 1094 */       this.defaults.enumerate(paramHashtable);
/*      */     }
/* 1096 */     for (Enumeration localEnumeration = keys(); localEnumeration.hasMoreElements();) {
/* 1097 */       String str = (String)localEnumeration.nextElement();
/* 1098 */       paramHashtable.put(str, get(str));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void enumerateStringProperties(Hashtable<String, String> paramHashtable)
/*      */   {
/* 1108 */     if (this.defaults != null) {
/* 1109 */       this.defaults.enumerateStringProperties(paramHashtable);
/*      */     }
/* 1111 */     for (Enumeration localEnumeration = keys(); localEnumeration.hasMoreElements();) {
/* 1112 */       Object localObject1 = localEnumeration.nextElement();
/* 1113 */       Object localObject2 = get(localObject1);
/* 1114 */       if (((localObject1 instanceof String)) && ((localObject2 instanceof String))) {
/* 1115 */         paramHashtable.put((String)localObject1, (String)localObject2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static char toHex(int paramInt)
/*      */   {
/* 1125 */     return hexDigit[(paramInt & 0xF)];
/*      */   }
/*      */   
/*      */ 
/* 1129 */   private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class XmlSupport
/*      */   {
/*      */     private static XmlPropertiesProvider loadProviderFromProperty(ClassLoader paramClassLoader)
/*      */     {
/* 1162 */       String str = System.getProperty("sun.util.spi.XmlPropertiesProvider");
/* 1163 */       if (str == null)
/* 1164 */         return null;
/*      */       try {
/* 1166 */         Class localClass = Class.forName(str, true, paramClassLoader);
/* 1167 */         return (XmlPropertiesProvider)localClass.newInstance();
/*      */       }
/*      */       catch (ClassNotFoundException|IllegalAccessException|InstantiationException localClassNotFoundException)
/*      */       {
/* 1171 */         throw new ServiceConfigurationError(null, localClassNotFoundException);
/*      */       }
/*      */     }
/*      */     
/*      */     private static XmlPropertiesProvider loadProviderAsService(ClassLoader paramClassLoader)
/*      */     {
/* 1177 */       Iterator localIterator = ServiceLoader.load(XmlPropertiesProvider.class, paramClassLoader).iterator();
/* 1178 */       return localIterator.hasNext() ? (XmlPropertiesProvider)localIterator.next() : null;
/*      */     }
/*      */     
/*      */ 
/* 1182 */     private static XmlPropertiesProvider loadProvider() { (XmlPropertiesProvider)AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public XmlPropertiesProvider run() {
/* 1185 */           ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
/* 1186 */           XmlPropertiesProvider localXmlPropertiesProvider = Properties.XmlSupport.loadProviderFromProperty(localClassLoader);
/* 1187 */           if (localXmlPropertiesProvider != null)
/* 1188 */             return localXmlPropertiesProvider;
/* 1189 */           localXmlPropertiesProvider = Properties.XmlSupport.loadProviderAsService(localClassLoader);
/* 1190 */           if (localXmlPropertiesProvider != null)
/* 1191 */             return localXmlPropertiesProvider;
/* 1192 */           return new BasicXmlPropertiesProvider();
/*      */         }
/*      */       }); }
/*      */     
/* 1196 */     private static final XmlPropertiesProvider PROVIDER = ;
/*      */     
/*      */     static void load(Properties paramProperties, InputStream paramInputStream)
/*      */       throws IOException, InvalidPropertiesFormatException
/*      */     {
/* 1201 */       PROVIDER.load(paramProperties, paramInputStream);
/*      */     }
/*      */     
/*      */ 
/*      */     static void save(Properties paramProperties, OutputStream paramOutputStream, String paramString1, String paramString2)
/*      */       throws IOException
/*      */     {
/* 1208 */       PROVIDER.store(paramProperties, paramOutputStream, paramString1, paramString2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Properties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */