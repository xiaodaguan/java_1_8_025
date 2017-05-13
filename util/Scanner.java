/*      */ package java.util;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.StringReader;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.channels.Channels;
/*      */ import java.nio.channels.ReadableByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetDecoder;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.nio.charset.UnsupportedCharsetException;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.OpenOption;
/*      */ import java.nio.file.Path;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.regex.MatchResult;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import sun.misc.LRUCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Scanner
/*      */   implements Iterator<String>, Closeable
/*      */ {
/*      */   private CharBuffer buf;
/*      */   private static final int BUFFER_SIZE = 1024;
/*      */   private int position;
/*      */   private Matcher matcher;
/*      */   private Pattern delimPattern;
/*      */   private Pattern hasNextPattern;
/*      */   private int hasNextPosition;
/*      */   private String hasNextResult;
/*      */   private Readable source;
/*  334 */   private boolean sourceClosed = false;
/*      */   
/*      */ 
/*  337 */   private boolean needInput = false;
/*      */   
/*      */ 
/*  340 */   private boolean skipped = false;
/*      */   
/*      */ 
/*  343 */   private int savedScannerPosition = -1;
/*      */   
/*      */ 
/*  346 */   private Object typeCache = null;
/*      */   
/*      */ 
/*  349 */   private boolean matchValid = false;
/*      */   
/*      */ 
/*  352 */   private boolean closed = false;
/*      */   
/*      */ 
/*  355 */   private int radix = 10;
/*      */   
/*      */ 
/*  358 */   private int defaultRadix = 10;
/*      */   
/*      */ 
/*  361 */   private Locale locale = null;
/*      */   
/*      */ 
/*  364 */   private LRUCache<String, Pattern> patternCache = new LRUCache(7)
/*      */   {
/*      */     protected Pattern create(String paramAnonymousString) {
/*  367 */       return Pattern.compile(paramAnonymousString);
/*      */     }
/*      */     
/*  370 */     protected boolean hasName(Pattern paramAnonymousPattern, String paramAnonymousString) { return paramAnonymousPattern.pattern().equals(paramAnonymousString); }
/*      */   };
/*      */   
/*      */ 
/*      */ 
/*      */   private IOException lastException;
/*      */   
/*      */ 
/*  378 */   private static Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
/*      */   
/*      */ 
/*      */ 
/*  382 */   private static Pattern FIND_ANY_PATTERN = Pattern.compile("(?s).*");
/*      */   
/*      */ 
/*  385 */   private static Pattern NON_ASCII_DIGIT = Pattern.compile("[\\p{javaDigit}&&[^0-9]]");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  393 */   private String groupSeparator = "\\,";
/*  394 */   private String decimalSeparator = "\\.";
/*  395 */   private String nanString = "NaN";
/*  396 */   private String infinityString = "Infinity";
/*  397 */   private String positivePrefix = "";
/*  398 */   private String negativePrefix = "\\-";
/*  399 */   private String positiveSuffix = "";
/*  400 */   private String negativeSuffix = "";
/*      */   
/*      */   private static volatile Pattern boolPattern;
/*      */   private static final String BOOLEAN_PATTERN = "true|false";
/*      */   private Pattern integerPattern;
/*      */   
/*      */   private static Pattern boolPattern()
/*      */   {
/*  408 */     Pattern localPattern = boolPattern;
/*  409 */     if (localPattern == null) {
/*  410 */       boolPattern = localPattern = Pattern.compile("true|false", 2);
/*      */     }
/*  412 */     return localPattern;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  419 */   private String digits = "0123456789abcdefghijklmnopqrstuvwxyz";
/*  420 */   private String non0Digit = "[\\p{javaDigit}&&[^0]]";
/*  421 */   private int SIMPLE_GROUP_INDEX = 5;
/*      */   
/*  423 */   private String buildIntegerPatternString() { String str1 = this.digits.substring(0, this.radix);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  428 */     String str2 = "((?i)[" + str1 + "]|\\p{javaDigit})";
/*  429 */     String str3 = "(" + this.non0Digit + str2 + "?" + str2 + "?(" + this.groupSeparator + str2 + str2 + str2 + ")+)";
/*      */     
/*      */ 
/*      */ 
/*  433 */     String str4 = "((" + str2 + "++)|" + str3 + ")";
/*  434 */     String str5 = "([-+]?(" + str4 + "))";
/*  435 */     String str6 = this.negativePrefix + str4 + this.negativeSuffix;
/*  436 */     String str7 = this.positivePrefix + str4 + this.positiveSuffix;
/*  437 */     return "(" + str5 + ")|(" + str7 + ")|(" + str6 + ")";
/*      */   }
/*      */   
/*      */   private Pattern integerPattern()
/*      */   {
/*  442 */     if (this.integerPattern == null) {
/*  443 */       this.integerPattern = ((Pattern)this.patternCache.forName(buildIntegerPatternString()));
/*      */     }
/*  445 */     return this.integerPattern;
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
/*      */   private static Pattern separatorPattern()
/*      */   {
/*  458 */     Pattern localPattern = separatorPattern;
/*  459 */     if (localPattern == null)
/*  460 */       separatorPattern = localPattern = Pattern.compile("\r\n|[\n\r  ]");
/*  461 */     return localPattern;
/*      */   }
/*      */   
/*      */   private static Pattern linePattern() {
/*  465 */     Pattern localPattern = linePattern;
/*  466 */     if (localPattern == null)
/*  467 */       linePattern = localPattern = Pattern.compile(".*(\r\n|[\n\r  ])|.+$");
/*  468 */     return localPattern;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void buildFloatAndDecimalPattern()
/*      */   {
/*  478 */     String str1 = "([0-9]|(\\p{javaDigit}))";
/*  479 */     String str2 = "([eE][+-]?" + str1 + "+)?";
/*  480 */     String str3 = "(" + this.non0Digit + str1 + "?" + str1 + "?(" + this.groupSeparator + str1 + str1 + str1 + ")+)";
/*      */     
/*      */ 
/*  483 */     String str4 = "((" + str1 + "++)|" + str3 + ")";
/*  484 */     String str5 = "(" + str4 + "|" + str4 + this.decimalSeparator + str1 + "*+|" + this.decimalSeparator + str1 + "++)";
/*      */     
/*      */ 
/*  487 */     String str6 = "(NaN|" + this.nanString + "|Infinity|" + this.infinityString + ")";
/*      */     
/*  489 */     String str7 = "(" + this.positivePrefix + str5 + this.positiveSuffix + str2 + ")";
/*      */     
/*  491 */     String str8 = "(" + this.negativePrefix + str5 + this.negativeSuffix + str2 + ")";
/*      */     
/*  493 */     String str9 = "(([-+]?" + str5 + str2 + ")|" + str7 + "|" + str8 + ")";
/*      */     
/*  495 */     String str10 = "[-+]?0[xX][0-9a-fA-F]*\\.[0-9a-fA-F]+([pP][-+]?[0-9]+)?";
/*      */     
/*  497 */     String str11 = "(" + this.positivePrefix + str6 + this.positiveSuffix + ")";
/*      */     
/*  499 */     String str12 = "(" + this.negativePrefix + str6 + this.negativeSuffix + ")";
/*      */     
/*  501 */     String str13 = "(([-+]?" + str6 + ")|" + str11 + "|" + str12 + ")";
/*      */     
/*      */ 
/*  504 */     this.floatPattern = Pattern.compile(str9 + "|" + str10 + "|" + str13);
/*      */     
/*  506 */     this.decimalPattern = Pattern.compile(str9);
/*      */   }
/*      */   
/*  509 */   private Pattern floatPattern() { if (this.floatPattern == null) {
/*  510 */       buildFloatAndDecimalPattern();
/*      */     }
/*  512 */     return this.floatPattern;
/*      */   }
/*      */   
/*  515 */   private Pattern decimalPattern() { if (this.decimalPattern == null) {
/*  516 */       buildFloatAndDecimalPattern();
/*      */     }
/*  518 */     return this.decimalPattern;
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
/*      */   private Scanner(Readable paramReadable, Pattern paramPattern)
/*      */   {
/*  531 */     assert (paramReadable != null) : "source should not be null";
/*  532 */     assert (paramPattern != null) : "pattern should not be null";
/*  533 */     this.source = paramReadable;
/*  534 */     this.delimPattern = paramPattern;
/*  535 */     this.buf = CharBuffer.allocate(1024);
/*  536 */     this.buf.limit(0);
/*  537 */     this.matcher = this.delimPattern.matcher(this.buf);
/*  538 */     this.matcher.useTransparentBounds(true);
/*  539 */     this.matcher.useAnchoringBounds(false);
/*  540 */     useLocale(Locale.getDefault(Locale.Category.FORMAT));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner(Readable paramReadable)
/*      */   {
/*  551 */     this((Readable)Objects.requireNonNull(paramReadable, "source"), WHITESPACE_PATTERN);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner(InputStream paramInputStream)
/*      */   {
/*  563 */     this(new InputStreamReader(paramInputStream), WHITESPACE_PATTERN);
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
/*      */   public Scanner(InputStream paramInputStream, String paramString)
/*      */   {
/*  578 */     this(makeReadable((InputStream)Objects.requireNonNull(paramInputStream, "source"), toCharset(paramString)), WHITESPACE_PATTERN);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Charset toCharset(String paramString)
/*      */   {
/*  588 */     Objects.requireNonNull(paramString, "charsetName");
/*      */     try {
/*  590 */       return Charset.forName(paramString);
/*      */     }
/*      */     catch (IllegalCharsetNameException|UnsupportedCharsetException localIllegalCharsetNameException) {
/*  593 */       throw new IllegalArgumentException(localIllegalCharsetNameException);
/*      */     }
/*      */   }
/*      */   
/*      */   private static Readable makeReadable(InputStream paramInputStream, Charset paramCharset) {
/*  598 */     return new InputStreamReader(paramInputStream, paramCharset);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner(File paramFile)
/*      */     throws FileNotFoundException
/*      */   {
/*  611 */     this(new FileInputStream(paramFile).getChannel());
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
/*      */   public Scanner(File paramFile, String paramString)
/*      */     throws FileNotFoundException
/*      */   {
/*  629 */     this((File)Objects.requireNonNull(paramFile), toDecoder(paramString));
/*      */   }
/*      */   
/*      */   private Scanner(File paramFile, CharsetDecoder paramCharsetDecoder)
/*      */     throws FileNotFoundException
/*      */   {
/*  635 */     this(makeReadable(new FileInputStream(paramFile).getChannel(), paramCharsetDecoder));
/*      */   }
/*      */   
/*      */   private static CharsetDecoder toDecoder(String paramString) {
/*  639 */     Objects.requireNonNull(paramString, "charsetName");
/*      */     try {
/*  641 */       return Charset.forName(paramString).newDecoder();
/*      */     } catch (IllegalCharsetNameException|UnsupportedCharsetException localIllegalCharsetNameException) {
/*  643 */       throw new IllegalArgumentException(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private static Readable makeReadable(ReadableByteChannel paramReadableByteChannel, CharsetDecoder paramCharsetDecoder)
/*      */   {
/*  649 */     return Channels.newReader(paramReadableByteChannel, paramCharsetDecoder, -1);
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
/*      */   public Scanner(Path paramPath)
/*      */     throws IOException
/*      */   {
/*  668 */     this(Files.newInputStream(paramPath, new OpenOption[0]));
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
/*      */   public Scanner(Path paramPath, String paramString)
/*      */     throws IOException
/*      */   {
/*  688 */     this((Path)Objects.requireNonNull(paramPath), toCharset(paramString));
/*      */   }
/*      */   
/*      */   private Scanner(Path paramPath, Charset paramCharset) throws IOException {
/*  692 */     this(makeReadable(Files.newInputStream(paramPath, new OpenOption[0]), paramCharset));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner(String paramString)
/*      */   {
/*  702 */     this(new StringReader(paramString), WHITESPACE_PATTERN);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner(ReadableByteChannel paramReadableByteChannel)
/*      */   {
/*  714 */     this(makeReadable((ReadableByteChannel)Objects.requireNonNull(paramReadableByteChannel, "source")), WHITESPACE_PATTERN);
/*      */   }
/*      */   
/*      */   private static Readable makeReadable(ReadableByteChannel paramReadableByteChannel)
/*      */   {
/*  719 */     return makeReadable(paramReadableByteChannel, Charset.defaultCharset().newDecoder());
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
/*      */   public Scanner(ReadableByteChannel paramReadableByteChannel, String paramString)
/*      */   {
/*  734 */     this(makeReadable((ReadableByteChannel)Objects.requireNonNull(paramReadableByteChannel, "source"), toDecoder(paramString)), WHITESPACE_PATTERN);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void saveState()
/*      */   {
/*  741 */     this.savedScannerPosition = this.position;
/*      */   }
/*      */   
/*      */   private void revertState() {
/*  745 */     this.position = this.savedScannerPosition;
/*  746 */     this.savedScannerPosition = -1;
/*  747 */     this.skipped = false;
/*      */   }
/*      */   
/*      */   private boolean revertState(boolean paramBoolean) {
/*  751 */     this.position = this.savedScannerPosition;
/*  752 */     this.savedScannerPosition = -1;
/*  753 */     this.skipped = false;
/*  754 */     return paramBoolean;
/*      */   }
/*      */   
/*      */   private void cacheResult() {
/*  758 */     this.hasNextResult = this.matcher.group();
/*  759 */     this.hasNextPosition = this.matcher.end();
/*  760 */     this.hasNextPattern = this.matcher.pattern();
/*      */   }
/*      */   
/*      */   private void cacheResult(String paramString) {
/*  764 */     this.hasNextResult = paramString;
/*  765 */     this.hasNextPosition = this.matcher.end();
/*  766 */     this.hasNextPattern = this.matcher.pattern();
/*      */   }
/*      */   
/*      */   private void clearCaches()
/*      */   {
/*  771 */     this.hasNextPattern = null;
/*  772 */     this.typeCache = null;
/*      */   }
/*      */   
/*      */   private String getCachedResult()
/*      */   {
/*  777 */     this.position = this.hasNextPosition;
/*  778 */     this.hasNextPattern = null;
/*  779 */     this.typeCache = null;
/*  780 */     return this.hasNextResult;
/*      */   }
/*      */   
/*      */   private void useTypeCache()
/*      */   {
/*  785 */     if (this.closed)
/*  786 */       throw new IllegalStateException("Scanner closed");
/*  787 */     this.position = this.hasNextPosition;
/*  788 */     this.hasNextPattern = null;
/*  789 */     this.typeCache = null;
/*      */   }
/*      */   
/*      */   private void readInput()
/*      */   {
/*  794 */     if (this.buf.limit() == this.buf.capacity()) {
/*  795 */       makeSpace();
/*      */     }
/*      */     
/*  798 */     int i = this.buf.position();
/*  799 */     this.buf.position(this.buf.limit());
/*  800 */     this.buf.limit(this.buf.capacity());
/*      */     
/*  802 */     int j = 0;
/*      */     try {
/*  804 */       j = this.source.read(this.buf);
/*      */     } catch (IOException localIOException) {
/*  806 */       this.lastException = localIOException;
/*  807 */       j = -1;
/*      */     }
/*      */     
/*  810 */     if (j == -1) {
/*  811 */       this.sourceClosed = true;
/*  812 */       this.needInput = false;
/*      */     }
/*      */     
/*  815 */     if (j > 0) {
/*  816 */       this.needInput = false;
/*      */     }
/*      */     
/*  819 */     this.buf.limit(this.buf.position());
/*  820 */     this.buf.position(i);
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean makeSpace()
/*      */   {
/*  826 */     clearCaches();
/*  827 */     int i = this.savedScannerPosition == -1 ? this.position : this.savedScannerPosition;
/*      */     
/*  829 */     this.buf.position(i);
/*      */     
/*  831 */     if (i > 0) {
/*  832 */       this.buf.compact();
/*  833 */       translateSavedIndexes(i);
/*  834 */       this.position -= i;
/*  835 */       this.buf.flip();
/*  836 */       return true;
/*      */     }
/*      */     
/*  839 */     int j = this.buf.capacity() * 2;
/*  840 */     CharBuffer localCharBuffer = CharBuffer.allocate(j);
/*  841 */     localCharBuffer.put(this.buf);
/*  842 */     localCharBuffer.flip();
/*  843 */     translateSavedIndexes(i);
/*  844 */     this.position -= i;
/*  845 */     this.buf = localCharBuffer;
/*  846 */     this.matcher.reset(this.buf);
/*  847 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   private void translateSavedIndexes(int paramInt)
/*      */   {
/*  853 */     if (this.savedScannerPosition != -1) {
/*  854 */       this.savedScannerPosition -= paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */   private void throwFor()
/*      */   {
/*  860 */     this.skipped = false;
/*  861 */     if ((this.sourceClosed) && (this.position == this.buf.limit())) {
/*  862 */       throw new NoSuchElementException();
/*      */     }
/*  864 */     throw new InputMismatchException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean hasTokenInBuffer()
/*      */   {
/*  871 */     this.matchValid = false;
/*  872 */     this.matcher.usePattern(this.delimPattern);
/*  873 */     this.matcher.region(this.position, this.buf.limit());
/*      */     
/*      */ 
/*  876 */     if (this.matcher.lookingAt()) {
/*  877 */       this.position = this.matcher.end();
/*      */     }
/*      */     
/*  880 */     if (this.position == this.buf.limit()) {
/*  881 */       return false;
/*      */     }
/*  883 */     return true;
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
/*      */   private String getCompleteTokenInBuffer(Pattern paramPattern)
/*      */   {
/*  902 */     this.matchValid = false;
/*      */     
/*      */ 
/*  905 */     this.matcher.usePattern(this.delimPattern);
/*  906 */     if (!this.skipped) {
/*  907 */       this.matcher.region(this.position, this.buf.limit());
/*  908 */       if (this.matcher.lookingAt())
/*      */       {
/*      */ 
/*  911 */         if ((this.matcher.hitEnd()) && (!this.sourceClosed)) {
/*  912 */           this.needInput = true;
/*  913 */           return null;
/*      */         }
/*      */         
/*  916 */         this.skipped = true;
/*  917 */         this.position = this.matcher.end();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  922 */     if (this.position == this.buf.limit()) {
/*  923 */       if (this.sourceClosed)
/*  924 */         return null;
/*  925 */       this.needInput = true;
/*  926 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  935 */     this.matcher.region(this.position, this.buf.limit());
/*  936 */     boolean bool = this.matcher.find();
/*  937 */     if ((bool) && (this.matcher.end() == this.position))
/*      */     {
/*      */ 
/*      */ 
/*  941 */       bool = this.matcher.find();
/*      */     }
/*  943 */     if (bool)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  950 */       if ((this.matcher.requireEnd()) && (!this.sourceClosed)) {
/*  951 */         this.needInput = true;
/*  952 */         return null;
/*      */       }
/*  954 */       int i = this.matcher.start();
/*      */       
/*  956 */       if (paramPattern == null)
/*      */       {
/*  958 */         paramPattern = FIND_ANY_PATTERN;
/*      */       }
/*      */       
/*  961 */       this.matcher.usePattern(paramPattern);
/*  962 */       this.matcher.region(this.position, i);
/*  963 */       if (this.matcher.matches()) {
/*  964 */         String str2 = this.matcher.group();
/*  965 */         this.position = this.matcher.end();
/*  966 */         return str2;
/*      */       }
/*  968 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  974 */     if (this.sourceClosed) {
/*  975 */       if (paramPattern == null)
/*      */       {
/*  977 */         paramPattern = FIND_ANY_PATTERN;
/*      */       }
/*      */       
/*  980 */       this.matcher.usePattern(paramPattern);
/*  981 */       this.matcher.region(this.position, this.buf.limit());
/*  982 */       if (this.matcher.matches()) {
/*  983 */         String str1 = this.matcher.group();
/*  984 */         this.position = this.matcher.end();
/*  985 */         return str1;
/*      */       }
/*      */       
/*  988 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  993 */     this.needInput = true;
/*  994 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private String findPatternInBuffer(Pattern paramPattern, int paramInt)
/*      */   {
/* 1000 */     this.matchValid = false;
/* 1001 */     this.matcher.usePattern(paramPattern);
/* 1002 */     int i = this.buf.limit();
/* 1003 */     int j = -1;
/* 1004 */     int k = i;
/* 1005 */     if (paramInt > 0) {
/* 1006 */       j = this.position + paramInt;
/* 1007 */       if (j < i)
/* 1008 */         k = j;
/*      */     }
/* 1010 */     this.matcher.region(this.position, k);
/* 1011 */     if (this.matcher.find()) {
/* 1012 */       if ((this.matcher.hitEnd()) && (!this.sourceClosed))
/*      */       {
/* 1014 */         if (k != j)
/*      */         {
/* 1016 */           this.needInput = true;
/* 1017 */           return null;
/*      */         }
/*      */         
/* 1020 */         if ((k == j) && (this.matcher.requireEnd()))
/*      */         {
/*      */ 
/*      */ 
/* 1024 */           this.needInput = true;
/* 1025 */           return null;
/*      */         }
/*      */       }
/*      */       
/* 1029 */       this.position = this.matcher.end();
/* 1030 */       return this.matcher.group();
/*      */     }
/*      */     
/* 1033 */     if (this.sourceClosed) {
/* 1034 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1038 */     if ((paramInt == 0) || (k != j))
/* 1039 */       this.needInput = true;
/* 1040 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private String matchPatternInBuffer(Pattern paramPattern)
/*      */   {
/* 1046 */     this.matchValid = false;
/* 1047 */     this.matcher.usePattern(paramPattern);
/* 1048 */     this.matcher.region(this.position, this.buf.limit());
/* 1049 */     if (this.matcher.lookingAt()) {
/* 1050 */       if ((this.matcher.hitEnd()) && (!this.sourceClosed))
/*      */       {
/* 1052 */         this.needInput = true;
/* 1053 */         return null;
/*      */       }
/* 1055 */       this.position = this.matcher.end();
/* 1056 */       return this.matcher.group();
/*      */     }
/*      */     
/* 1059 */     if (this.sourceClosed) {
/* 1060 */       return null;
/*      */     }
/*      */     
/* 1063 */     this.needInput = true;
/* 1064 */     return null;
/*      */   }
/*      */   
/*      */   private void ensureOpen()
/*      */   {
/* 1069 */     if (this.closed) {
/* 1070 */       throw new IllegalStateException("Scanner closed");
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
/*      */   public void close()
/*      */   {
/* 1089 */     if (this.closed)
/* 1090 */       return;
/* 1091 */     if ((this.source instanceof Closeable)) {
/*      */       try {
/* 1093 */         ((Closeable)this.source).close();
/*      */       } catch (IOException localIOException) {
/* 1095 */         this.lastException = localIOException;
/*      */       }
/*      */     }
/* 1098 */     this.sourceClosed = true;
/* 1099 */     this.source = null;
/* 1100 */     this.closed = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IOException ioException()
/*      */   {
/* 1111 */     return this.lastException;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Pattern delimiter()
/*      */   {
/* 1121 */     return this.delimPattern;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner useDelimiter(Pattern paramPattern)
/*      */   {
/* 1131 */     this.delimPattern = paramPattern;
/* 1132 */     return this;
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
/*      */   public Scanner useDelimiter(String paramString)
/*      */   {
/* 1150 */     this.delimPattern = ((Pattern)this.patternCache.forName(paramString));
/* 1151 */     return this;
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
/*      */   public Locale locale()
/*      */   {
/* 1164 */     return this.locale;
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
/*      */   public Scanner useLocale(Locale paramLocale)
/*      */   {
/* 1181 */     if (paramLocale.equals(this.locale)) {
/* 1182 */       return this;
/*      */     }
/* 1184 */     this.locale = paramLocale;
/*      */     
/* 1186 */     DecimalFormat localDecimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(paramLocale);
/* 1187 */     DecimalFormatSymbols localDecimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/*      */     
/*      */ 
/*      */ 
/* 1191 */     this.groupSeparator = ("\\" + localDecimalFormatSymbols.getGroupingSeparator());
/* 1192 */     this.decimalSeparator = ("\\" + localDecimalFormatSymbols.getDecimalSeparator());
/*      */     
/*      */ 
/*      */ 
/* 1196 */     this.nanString = ("\\Q" + localDecimalFormatSymbols.getNaN() + "\\E");
/* 1197 */     this.infinityString = ("\\Q" + localDecimalFormatSymbols.getInfinity() + "\\E");
/* 1198 */     this.positivePrefix = localDecimalFormat.getPositivePrefix();
/* 1199 */     if (this.positivePrefix.length() > 0)
/* 1200 */       this.positivePrefix = ("\\Q" + this.positivePrefix + "\\E");
/* 1201 */     this.negativePrefix = localDecimalFormat.getNegativePrefix();
/* 1202 */     if (this.negativePrefix.length() > 0)
/* 1203 */       this.negativePrefix = ("\\Q" + this.negativePrefix + "\\E");
/* 1204 */     this.positiveSuffix = localDecimalFormat.getPositiveSuffix();
/* 1205 */     if (this.positiveSuffix.length() > 0)
/* 1206 */       this.positiveSuffix = ("\\Q" + this.positiveSuffix + "\\E");
/* 1207 */     this.negativeSuffix = localDecimalFormat.getNegativeSuffix();
/* 1208 */     if (this.negativeSuffix.length() > 0) {
/* 1209 */       this.negativeSuffix = ("\\Q" + this.negativeSuffix + "\\E");
/*      */     }
/*      */     
/*      */ 
/* 1213 */     this.integerPattern = null;
/* 1214 */     this.floatPattern = null;
/*      */     
/* 1216 */     return this;
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
/*      */   public int radix()
/*      */   {
/* 1229 */     return this.defaultRadix;
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
/*      */   private static volatile Pattern separatorPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner useRadix(int paramInt)
/*      */   {
/* 1251 */     if ((paramInt < 2) || (paramInt > 36)) {
/* 1252 */       throw new IllegalArgumentException("radix:" + paramInt);
/*      */     }
/* 1254 */     if (this.defaultRadix == paramInt)
/* 1255 */       return this;
/* 1256 */     this.defaultRadix = paramInt;
/*      */     
/* 1258 */     this.integerPattern = null;
/* 1259 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */   private void setRadix(int paramInt)
/*      */   {
/* 1265 */     if (this.radix != paramInt)
/*      */     {
/* 1267 */       this.integerPattern = null;
/* 1268 */       this.radix = paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static volatile Pattern linePattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r  ]";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MatchResult match()
/*      */   {
/* 1292 */     if (!this.matchValid)
/* 1293 */       throw new IllegalStateException("No match result available");
/* 1294 */     return this.matcher.toMatchResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1305 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1306 */     localStringBuilder.append("java.util.Scanner");
/* 1307 */     localStringBuilder.append("[delimiters=" + this.delimPattern + "]");
/* 1308 */     localStringBuilder.append("[position=" + this.position + "]");
/* 1309 */     localStringBuilder.append("[match valid=" + this.matchValid + "]");
/* 1310 */     localStringBuilder.append("[need input=" + this.needInput + "]");
/* 1311 */     localStringBuilder.append("[source closed=" + this.sourceClosed + "]");
/* 1312 */     localStringBuilder.append("[skipped=" + this.skipped + "]");
/* 1313 */     localStringBuilder.append("[group separator=" + this.groupSeparator + "]");
/* 1314 */     localStringBuilder.append("[decimal separator=" + this.decimalSeparator + "]");
/* 1315 */     localStringBuilder.append("[positive prefix=" + this.positivePrefix + "]");
/* 1316 */     localStringBuilder.append("[negative prefix=" + this.negativePrefix + "]");
/* 1317 */     localStringBuilder.append("[positive suffix=" + this.positiveSuffix + "]");
/* 1318 */     localStringBuilder.append("[negative suffix=" + this.negativeSuffix + "]");
/* 1319 */     localStringBuilder.append("[NaN string=" + this.nanString + "]");
/* 1320 */     localStringBuilder.append("[infinity string=" + this.infinityString + "]");
/* 1321 */     return localStringBuilder.toString();
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
/*      */   public boolean hasNext()
/*      */   {
/* 1334 */     ensureOpen();
/* 1335 */     saveState();
/* 1336 */     while (!this.sourceClosed) {
/* 1337 */       if (hasTokenInBuffer())
/* 1338 */         return revertState(true);
/* 1339 */       readInput();
/*      */     }
/* 1341 */     boolean bool = hasTokenInBuffer();
/* 1342 */     return revertState(bool);
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
/*      */   public String next()
/*      */   {
/* 1358 */     ensureOpen();
/* 1359 */     clearCaches();
/*      */     for (;;)
/*      */     {
/* 1362 */       String str = getCompleteTokenInBuffer(null);
/* 1363 */       if (str != null) {
/* 1364 */         this.matchValid = true;
/* 1365 */         this.skipped = false;
/* 1366 */         return str;
/*      */       }
/* 1368 */       if (this.needInput) {
/* 1369 */         readInput();
/*      */       } else {
/* 1371 */         throwFor();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void remove()
/*      */   {
/* 1383 */     throw new UnsupportedOperationException();
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
/*      */   public boolean hasNext(String paramString)
/*      */   {
/* 1400 */     return hasNext((Pattern)this.patternCache.forName(paramString));
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
/*      */   public String next(String paramString)
/*      */   {
/* 1418 */     return next((Pattern)this.patternCache.forName(paramString));
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
/*      */   public boolean hasNext(Pattern paramPattern)
/*      */   {
/* 1433 */     ensureOpen();
/* 1434 */     if (paramPattern == null)
/* 1435 */       throw new NullPointerException();
/* 1436 */     this.hasNextPattern = null;
/* 1437 */     saveState();
/*      */     for (;;)
/*      */     {
/* 1440 */       if (getCompleteTokenInBuffer(paramPattern) != null) {
/* 1441 */         this.matchValid = true;
/* 1442 */         cacheResult();
/* 1443 */         return revertState(true);
/*      */       }
/* 1445 */       if (!this.needInput) break;
/* 1446 */       readInput();
/*      */     }
/* 1448 */     return revertState(false);
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
/*      */   public String next(Pattern paramPattern)
/*      */   {
/* 1465 */     ensureOpen();
/* 1466 */     if (paramPattern == null) {
/* 1467 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 1470 */     if (this.hasNextPattern == paramPattern)
/* 1471 */       return getCachedResult();
/* 1472 */     clearCaches();
/*      */     
/*      */     for (;;)
/*      */     {
/* 1476 */       String str = getCompleteTokenInBuffer(paramPattern);
/* 1477 */       if (str != null) {
/* 1478 */         this.matchValid = true;
/* 1479 */         this.skipped = false;
/* 1480 */         return str;
/*      */       }
/* 1482 */       if (this.needInput) {
/* 1483 */         readInput();
/*      */       } else {
/* 1485 */         throwFor();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasNextLine()
/*      */   {
/* 1498 */     saveState();
/*      */     
/* 1500 */     String str1 = findWithinHorizon(linePattern(), 0);
/* 1501 */     if (str1 != null) {
/* 1502 */       MatchResult localMatchResult = match();
/* 1503 */       String str2 = localMatchResult.group(1);
/* 1504 */       if (str2 != null) {
/* 1505 */         str1 = str1.substring(0, str1.length() - str2
/* 1506 */           .length());
/* 1507 */         cacheResult(str1);
/*      */       }
/*      */       else {
/* 1510 */         cacheResult();
/*      */       }
/*      */     }
/* 1513 */     revertState();
/* 1514 */     return str1 != null;
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
/*      */   public String nextLine()
/*      */   {
/* 1534 */     if (this.hasNextPattern == linePattern())
/* 1535 */       return getCachedResult();
/* 1536 */     clearCaches();
/*      */     
/* 1538 */     String str1 = findWithinHorizon(linePattern, 0);
/* 1539 */     if (str1 == null)
/* 1540 */       throw new NoSuchElementException("No line found");
/* 1541 */     MatchResult localMatchResult = match();
/* 1542 */     String str2 = localMatchResult.group(1);
/* 1543 */     if (str2 != null)
/* 1544 */       str1 = str1.substring(0, str1.length() - str2.length());
/* 1545 */     if (str1 == null) {
/* 1546 */       throw new NoSuchElementException();
/*      */     }
/* 1548 */     return str1;
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
/*      */   public String findInLine(String paramString)
/*      */   {
/* 1566 */     return findInLine((Pattern)this.patternCache.forName(paramString));
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
/*      */   public String findInLine(Pattern paramPattern)
/*      */   {
/* 1588 */     ensureOpen();
/* 1589 */     if (paramPattern == null)
/* 1590 */       throw new NullPointerException();
/* 1591 */     clearCaches();
/*      */     
/* 1593 */     int i = 0;
/* 1594 */     saveState();
/*      */     for (;;) {
/* 1596 */       String str = findPatternInBuffer(separatorPattern(), 0);
/* 1597 */       if (str != null) {
/* 1598 */         i = this.matcher.start();
/* 1599 */         break;
/*      */       }
/* 1601 */       if (this.needInput) {
/* 1602 */         readInput();
/*      */       } else {
/* 1604 */         i = this.buf.limit();
/* 1605 */         break;
/*      */       }
/*      */     }
/* 1608 */     revertState();
/* 1609 */     int j = i - this.position;
/*      */     
/*      */ 
/*      */ 
/* 1613 */     if (j == 0) {
/* 1614 */       return null;
/*      */     }
/* 1616 */     return findWithinHorizon(paramPattern, j);
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
/*      */   public String findWithinHorizon(String paramString, int paramInt)
/*      */   {
/* 1635 */     return findWithinHorizon((Pattern)this.patternCache.forName(paramString), paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String LINE_PATTERN = ".*(\r\n|[\n\r  ])|.+$";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Pattern floatPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Pattern decimalPattern;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String findWithinHorizon(Pattern paramPattern, int paramInt)
/*      */   {
/* 1670 */     ensureOpen();
/* 1671 */     if (paramPattern == null)
/* 1672 */       throw new NullPointerException();
/* 1673 */     if (paramInt < 0)
/* 1674 */       throw new IllegalArgumentException("horizon < 0");
/* 1675 */     clearCaches();
/*      */     
/*      */     for (;;)
/*      */     {
/* 1679 */       String str = findPatternInBuffer(paramPattern, paramInt);
/* 1680 */       if (str != null) {
/* 1681 */         this.matchValid = true;
/* 1682 */         return str;
/*      */       }
/* 1684 */       if (!this.needInput) break;
/* 1685 */       readInput();
/*      */     }
/*      */     
/*      */ 
/* 1689 */     return null;
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
/*      */   public Scanner skip(Pattern paramPattern)
/*      */   {
/* 1716 */     ensureOpen();
/* 1717 */     if (paramPattern == null)
/* 1718 */       throw new NullPointerException();
/* 1719 */     clearCaches();
/*      */     
/*      */     for (;;)
/*      */     {
/* 1723 */       String str = matchPatternInBuffer(paramPattern);
/* 1724 */       if (str != null) {
/* 1725 */         this.matchValid = true;
/* 1726 */         this.position = this.matcher.end();
/* 1727 */         return this;
/*      */       }
/* 1729 */       if (this.needInput) {
/* 1730 */         readInput();
/*      */       } else {
/* 1732 */         throw new NoSuchElementException();
/*      */       }
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
/*      */   public Scanner skip(String paramString)
/*      */   {
/* 1749 */     return skip((Pattern)this.patternCache.forName(paramString));
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
/*      */   public boolean hasNextBoolean()
/*      */   {
/* 1765 */     return hasNext(boolPattern());
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
/*      */   public boolean nextBoolean()
/*      */   {
/* 1781 */     clearCaches();
/* 1782 */     return Boolean.parseBoolean(next(boolPattern()));
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
/*      */   public boolean hasNextByte()
/*      */   {
/* 1795 */     return hasNextByte(this.defaultRadix);
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
/*      */   public boolean hasNextByte(int paramInt)
/*      */   {
/* 1809 */     setRadix(paramInt);
/* 1810 */     boolean bool = hasNext(integerPattern());
/* 1811 */     if (bool) {
/*      */       try
/*      */       {
/* 1814 */         String str = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? processIntegerToken(this.hasNextResult) : this.hasNextResult;
/*      */         
/* 1816 */         this.typeCache = Byte.valueOf(Byte.parseByte(str, paramInt));
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 1818 */         bool = false;
/*      */       }
/*      */     }
/* 1821 */     return bool;
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
/*      */   public byte nextByte()
/*      */   {
/* 1840 */     return nextByte(this.defaultRadix);
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
/*      */   public byte nextByte(int paramInt)
/*      */   {
/* 1871 */     if ((this.typeCache != null) && ((this.typeCache instanceof Byte)) && (this.radix == paramInt))
/*      */     {
/* 1873 */       byte b = ((Byte)this.typeCache).byteValue();
/* 1874 */       useTypeCache();
/* 1875 */       return b;
/*      */     }
/* 1877 */     setRadix(paramInt);
/* 1878 */     clearCaches();
/*      */     try
/*      */     {
/* 1881 */       String str = next(integerPattern());
/* 1882 */       if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null)
/* 1883 */         str = processIntegerToken(str);
/* 1884 */       return Byte.parseByte(str, paramInt);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 1886 */       this.position = this.matcher.start();
/* 1887 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   public boolean hasNextShort()
/*      */   {
/* 1901 */     return hasNextShort(this.defaultRadix);
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
/*      */   public boolean hasNextShort(int paramInt)
/*      */   {
/* 1915 */     setRadix(paramInt);
/* 1916 */     boolean bool = hasNext(integerPattern());
/* 1917 */     if (bool) {
/*      */       try
/*      */       {
/* 1920 */         String str = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? processIntegerToken(this.hasNextResult) : this.hasNextResult;
/*      */         
/* 1922 */         this.typeCache = Short.valueOf(Short.parseShort(str, paramInt));
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 1924 */         bool = false;
/*      */       }
/*      */     }
/* 1927 */     return bool;
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
/*      */   public short nextShort()
/*      */   {
/* 1946 */     return nextShort(this.defaultRadix);
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
/*      */   public short nextShort(int paramInt)
/*      */   {
/* 1977 */     if ((this.typeCache != null) && ((this.typeCache instanceof Short)) && (this.radix == paramInt))
/*      */     {
/* 1979 */       short s = ((Short)this.typeCache).shortValue();
/* 1980 */       useTypeCache();
/* 1981 */       return s;
/*      */     }
/* 1983 */     setRadix(paramInt);
/* 1984 */     clearCaches();
/*      */     try
/*      */     {
/* 1987 */       String str = next(integerPattern());
/* 1988 */       if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null)
/* 1989 */         str = processIntegerToken(str);
/* 1990 */       return Short.parseShort(str, paramInt);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 1992 */       this.position = this.matcher.start();
/* 1993 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   public boolean hasNextInt()
/*      */   {
/* 2007 */     return hasNextInt(this.defaultRadix);
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
/*      */   public boolean hasNextInt(int paramInt)
/*      */   {
/* 2021 */     setRadix(paramInt);
/* 2022 */     boolean bool = hasNext(integerPattern());
/* 2023 */     if (bool) {
/*      */       try
/*      */       {
/* 2026 */         String str = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? processIntegerToken(this.hasNextResult) : this.hasNextResult;
/*      */         
/* 2028 */         this.typeCache = Integer.valueOf(Integer.parseInt(str, paramInt));
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 2030 */         bool = false;
/*      */       }
/*      */     }
/* 2033 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String processIntegerToken(String paramString)
/*      */   {
/* 2042 */     String str = paramString.replaceAll("" + this.groupSeparator, "");
/* 2043 */     int i = 0;
/* 2044 */     int j = this.negativePrefix.length();
/* 2045 */     if ((j > 0) && (str.startsWith(this.negativePrefix))) {
/* 2046 */       i = 1;
/* 2047 */       str = str.substring(j);
/*      */     }
/* 2049 */     int k = this.negativeSuffix.length();
/* 2050 */     if ((k > 0) && (str.endsWith(this.negativeSuffix))) {
/* 2051 */       i = 1;
/* 2052 */       str = str.substring(str.length() - k, str
/* 2053 */         .length());
/*      */     }
/* 2055 */     if (i != 0)
/* 2056 */       str = "-" + str;
/* 2057 */     return str;
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
/*      */   public int nextInt()
/*      */   {
/* 2076 */     return nextInt(this.defaultRadix);
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
/*      */   public int nextInt(int paramInt)
/*      */   {
/* 2107 */     if ((this.typeCache != null) && ((this.typeCache instanceof Integer)) && (this.radix == paramInt))
/*      */     {
/* 2109 */       int i = ((Integer)this.typeCache).intValue();
/* 2110 */       useTypeCache();
/* 2111 */       return i;
/*      */     }
/* 2113 */     setRadix(paramInt);
/* 2114 */     clearCaches();
/*      */     try
/*      */     {
/* 2117 */       String str = next(integerPattern());
/* 2118 */       if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null)
/* 2119 */         str = processIntegerToken(str);
/* 2120 */       return Integer.parseInt(str, paramInt);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 2122 */       this.position = this.matcher.start();
/* 2123 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   public boolean hasNextLong()
/*      */   {
/* 2137 */     return hasNextLong(this.defaultRadix);
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
/*      */   public boolean hasNextLong(int paramInt)
/*      */   {
/* 2151 */     setRadix(paramInt);
/* 2152 */     boolean bool = hasNext(integerPattern());
/* 2153 */     if (bool) {
/*      */       try
/*      */       {
/* 2156 */         String str = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? processIntegerToken(this.hasNextResult) : this.hasNextResult;
/*      */         
/* 2158 */         this.typeCache = Long.valueOf(Long.parseLong(str, paramInt));
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 2160 */         bool = false;
/*      */       }
/*      */     }
/* 2163 */     return bool;
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
/*      */   public long nextLong()
/*      */   {
/* 2182 */     return nextLong(this.defaultRadix);
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
/*      */   public long nextLong(int paramInt)
/*      */   {
/* 2213 */     if ((this.typeCache != null) && ((this.typeCache instanceof Long)) && (this.radix == paramInt))
/*      */     {
/* 2215 */       long l = ((Long)this.typeCache).longValue();
/* 2216 */       useTypeCache();
/* 2217 */       return l;
/*      */     }
/* 2219 */     setRadix(paramInt);
/* 2220 */     clearCaches();
/*      */     try {
/* 2222 */       String str = next(integerPattern());
/* 2223 */       if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null)
/* 2224 */         str = processIntegerToken(str);
/* 2225 */       return Long.parseLong(str, paramInt);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 2227 */       this.position = this.matcher.start();
/* 2228 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   private String processFloatToken(String paramString)
/*      */   {
/* 2241 */     String str = paramString.replaceAll(this.groupSeparator, "");
/* 2242 */     if (!this.decimalSeparator.equals("\\."))
/* 2243 */       str = str.replaceAll(this.decimalSeparator, ".");
/* 2244 */     int i = 0;
/* 2245 */     int j = this.negativePrefix.length();
/* 2246 */     if ((j > 0) && (str.startsWith(this.negativePrefix))) {
/* 2247 */       i = 1;
/* 2248 */       str = str.substring(j);
/*      */     }
/* 2250 */     int k = this.negativeSuffix.length();
/* 2251 */     if ((k > 0) && (str.endsWith(this.negativeSuffix))) {
/* 2252 */       i = 1;
/* 2253 */       str = str.substring(str.length() - k, str
/* 2254 */         .length());
/*      */     }
/* 2256 */     if (str.equals(this.nanString))
/* 2257 */       str = "NaN";
/* 2258 */     if (str.equals(this.infinityString))
/* 2259 */       str = "Infinity";
/* 2260 */     if (i != 0) {
/* 2261 */       str = "-" + str;
/*      */     }
/*      */     
/* 2264 */     Matcher localMatcher = NON_ASCII_DIGIT.matcher(str);
/* 2265 */     if (localMatcher.find()) {
/* 2266 */       StringBuilder localStringBuilder = new StringBuilder();
/* 2267 */       for (int m = 0; m < str.length(); m++) {
/* 2268 */         char c = str.charAt(m);
/* 2269 */         if (Character.isDigit(c)) {
/* 2270 */           int n = Character.digit(c, 10);
/* 2271 */           if (n != -1) {
/* 2272 */             localStringBuilder.append(n);
/*      */           } else
/* 2274 */             localStringBuilder.append(c);
/*      */         } else {
/* 2276 */           localStringBuilder.append(c);
/*      */         }
/*      */       }
/* 2279 */       str = localStringBuilder.toString();
/*      */     }
/*      */     
/* 2282 */     return str;
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
/*      */   public boolean hasNextFloat()
/*      */   {
/* 2295 */     setRadix(10);
/* 2296 */     boolean bool = hasNext(floatPattern());
/* 2297 */     if (bool) {
/*      */       try {
/* 2299 */         String str = processFloatToken(this.hasNextResult);
/* 2300 */         this.typeCache = Float.valueOf(Float.parseFloat(str));
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 2302 */         bool = false;
/*      */       }
/*      */     }
/* 2305 */     return bool;
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
/*      */   public float nextFloat()
/*      */   {
/* 2337 */     if ((this.typeCache != null) && ((this.typeCache instanceof Float))) {
/* 2338 */       float f = ((Float)this.typeCache).floatValue();
/* 2339 */       useTypeCache();
/* 2340 */       return f;
/*      */     }
/* 2342 */     setRadix(10);
/* 2343 */     clearCaches();
/*      */     try {
/* 2345 */       return Float.parseFloat(processFloatToken(next(floatPattern())));
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 2347 */       this.position = this.matcher.start();
/* 2348 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   public boolean hasNextDouble()
/*      */   {
/* 2362 */     setRadix(10);
/* 2363 */     boolean bool = hasNext(floatPattern());
/* 2364 */     if (bool) {
/*      */       try {
/* 2366 */         String str = processFloatToken(this.hasNextResult);
/* 2367 */         this.typeCache = Double.valueOf(Double.parseDouble(str));
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 2369 */         bool = false;
/*      */       }
/*      */     }
/* 2372 */     return bool;
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
/*      */   public double nextDouble()
/*      */   {
/* 2404 */     if ((this.typeCache != null) && ((this.typeCache instanceof Double))) {
/* 2405 */       double d = ((Double)this.typeCache).doubleValue();
/* 2406 */       useTypeCache();
/* 2407 */       return d;
/*      */     }
/* 2409 */     setRadix(10);
/* 2410 */     clearCaches();
/*      */     try
/*      */     {
/* 2413 */       return Double.parseDouble(processFloatToken(next(floatPattern())));
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 2415 */       this.position = this.matcher.start();
/* 2416 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   public boolean hasNextBigInteger()
/*      */   {
/* 2433 */     return hasNextBigInteger(this.defaultRadix);
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
/*      */   public boolean hasNextBigInteger(int paramInt)
/*      */   {
/* 2448 */     setRadix(paramInt);
/* 2449 */     boolean bool = hasNext(integerPattern());
/* 2450 */     if (bool) {
/*      */       try
/*      */       {
/* 2453 */         String str = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? processIntegerToken(this.hasNextResult) : this.hasNextResult;
/*      */         
/* 2455 */         this.typeCache = new BigInteger(str, paramInt);
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 2457 */         bool = false;
/*      */       }
/*      */     }
/* 2460 */     return bool;
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
/*      */   public BigInteger nextBigInteger()
/*      */   {
/* 2480 */     return nextBigInteger(this.defaultRadix);
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
/*      */   public BigInteger nextBigInteger(int paramInt)
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2506 */     if ((this.typeCache != null) && ((this.typeCache instanceof BigInteger)) && (this.radix == paramInt))
/*      */     {
/* 2508 */       localObject = (BigInteger)this.typeCache;
/* 2509 */       useTypeCache();
/* 2510 */       return (BigInteger)localObject;
/*      */     }
/* 2512 */     setRadix(paramInt);
/* 2513 */     clearCaches();
/*      */     try
/*      */     {
/* 2516 */       localObject = next(integerPattern());
/* 2517 */       if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null)
/* 2518 */         localObject = processIntegerToken((String)localObject);
/* 2519 */       return new BigInteger((String)localObject, paramInt);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 2521 */       this.position = this.matcher.start();
/* 2522 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */   public boolean hasNextBigDecimal()
/*      */   {
/* 2537 */     setRadix(10);
/* 2538 */     boolean bool = hasNext(decimalPattern());
/* 2539 */     if (bool) {
/*      */       try {
/* 2541 */         String str = processFloatToken(this.hasNextResult);
/* 2542 */         this.typeCache = new BigDecimal(str);
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 2544 */         bool = false;
/*      */       }
/*      */     }
/* 2547 */     return bool;
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
/*      */   public BigDecimal nextBigDecimal()
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2572 */     if ((this.typeCache != null) && ((this.typeCache instanceof BigDecimal))) {
/* 2573 */       localObject = (BigDecimal)this.typeCache;
/* 2574 */       useTypeCache();
/* 2575 */       return (BigDecimal)localObject;
/*      */     }
/* 2577 */     setRadix(10);
/* 2578 */     clearCaches();
/*      */     try
/*      */     {
/* 2581 */       localObject = processFloatToken(next(decimalPattern()));
/* 2582 */       return new BigDecimal((String)localObject);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/* 2584 */       this.position = this.matcher.start();
/* 2585 */       throw new InputMismatchException(localNumberFormatException.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Scanner reset()
/*      */   {
/* 2611 */     this.delimPattern = WHITESPACE_PATTERN;
/* 2612 */     useLocale(Locale.getDefault(Locale.Category.FORMAT));
/* 2613 */     useRadix(10);
/* 2614 */     clearCaches();
/* 2615 */     return this;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Scanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */