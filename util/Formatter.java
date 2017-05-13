/*      */ package java.util;
/*      */ 
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.Flushable;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.MathContext;
/*      */ import java.math.RoundingMode;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.nio.charset.UnsupportedCharsetException;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.text.NumberFormat;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.Instant;
/*      */ import java.time.ZoneId;
/*      */ import java.time.ZoneOffset;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalQueries;
/*      */ import java.time.zone.ZoneRules;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import sun.misc.FormattedFloatingDecimal;
/*      */ import sun.misc.FormattedFloatingDecimal.Form;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Formatter
/*      */   implements Closeable, Flushable
/*      */ {
/*      */   private Appendable a;
/*      */   private final Locale l;
/*      */   private IOException lastException;
/*      */   private final char zero;
/*      */   private static double scaleUp;
/*      */   private static final int MAX_FD_CHARS = 30;
/*      */   private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
/*      */   
/*      */   private static Charset toCharset(String paramString)
/*      */     throws UnsupportedEncodingException
/*      */   {
/* 1872 */     Objects.requireNonNull(paramString, "charsetName");
/*      */     try {
/* 1874 */       return Charset.forName(paramString);
/*      */     }
/*      */     catch (IllegalCharsetNameException|UnsupportedCharsetException localIllegalCharsetNameException) {
/* 1877 */       throw new UnsupportedEncodingException(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final Appendable nonNullAppendable(Appendable paramAppendable) {
/* 1882 */     if (paramAppendable == null) {
/* 1883 */       return new StringBuilder();
/*      */     }
/* 1885 */     return paramAppendable;
/*      */   }
/*      */   
/*      */   private Formatter(Locale paramLocale, Appendable paramAppendable)
/*      */   {
/* 1890 */     this.a = paramAppendable;
/* 1891 */     this.l = paramLocale;
/* 1892 */     this.zero = getZero(paramLocale);
/*      */   }
/*      */   
/*      */   private Formatter(Charset paramCharset, Locale paramLocale, File paramFile)
/*      */     throws FileNotFoundException
/*      */   {
/* 1898 */     this(paramLocale, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramFile), paramCharset)));
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
/*      */   public Formatter()
/*      */   {
/* 1914 */     this(Locale.getDefault(Locale.Category.FORMAT), new StringBuilder());
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
/*      */   public Formatter(Appendable paramAppendable)
/*      */   {
/* 1930 */     this(Locale.getDefault(Locale.Category.FORMAT), nonNullAppendable(paramAppendable));
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
/*      */   public Formatter(Locale paramLocale)
/*      */   {
/* 1947 */     this(paramLocale, new StringBuilder());
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
/*      */   public Formatter(Appendable paramAppendable, Locale paramLocale)
/*      */   {
/* 1963 */     this(paramLocale, nonNullAppendable(paramAppendable));
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
/*      */   public Formatter(String paramString)
/*      */     throws FileNotFoundException
/*      */   {
/* 1996 */     this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramString))));
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
/*      */   public Formatter(String paramString1, String paramString2)
/*      */     throws FileNotFoundException, UnsupportedEncodingException
/*      */   {
/* 2035 */     this(paramString1, paramString2, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public Formatter(String paramString1, String paramString2, Locale paramLocale)
/*      */     throws FileNotFoundException, UnsupportedEncodingException
/*      */   {
/* 2074 */     this(toCharset(paramString2), paramLocale, new File(paramString1));
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
/*      */   public Formatter(File paramFile)
/*      */     throws FileNotFoundException
/*      */   {
/* 2107 */     this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramFile))));
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
/*      */   public Formatter(File paramFile, String paramString)
/*      */     throws FileNotFoundException, UnsupportedEncodingException
/*      */   {
/* 2146 */     this(paramFile, paramString, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public Formatter(File paramFile, String paramString, Locale paramLocale)
/*      */     throws FileNotFoundException, UnsupportedEncodingException
/*      */   {
/* 2185 */     this(toCharset(paramString), paramLocale, paramFile);
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
/*      */   public Formatter(PrintStream paramPrintStream)
/*      */   {
/* 2204 */     this(Locale.getDefault(Locale.Category.FORMAT), 
/* 2205 */       (Appendable)Objects.requireNonNull(paramPrintStream));
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
/*      */   public Formatter(OutputStream paramOutputStream)
/*      */   {
/* 2225 */     this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(paramOutputStream)));
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
/*      */   public Formatter(OutputStream paramOutputStream, String paramString)
/*      */     throws UnsupportedEncodingException
/*      */   {
/* 2252 */     this(paramOutputStream, paramString, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public Formatter(OutputStream paramOutputStream, String paramString, Locale paramLocale)
/*      */     throws UnsupportedEncodingException
/*      */   {
/* 2278 */     this(paramLocale, new BufferedWriter(new OutputStreamWriter(paramOutputStream, paramString)));
/*      */   }
/*      */   
/*      */   private static char getZero(Locale paramLocale) {
/* 2282 */     if ((paramLocale != null) && (!paramLocale.equals(Locale.US))) {
/* 2283 */       DecimalFormatSymbols localDecimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 2284 */       return localDecimalFormatSymbols.getZeroDigit();
/*      */     }
/* 2286 */     return '0';
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
/*      */   public Locale locale()
/*      */   {
/* 2304 */     ensureOpen();
/* 2305 */     return this.l;
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
/*      */   public Appendable out()
/*      */   {
/* 2318 */     ensureOpen();
/* 2319 */     return this.a;
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
/*      */   public String toString()
/*      */   {
/* 2354 */     ensureOpen();
/* 2355 */     return this.a.toString();
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
/*      */   public void flush()
/*      */   {
/* 2370 */     ensureOpen();
/* 2371 */     if ((this.a instanceof Flushable)) {
/*      */       try {
/* 2373 */         ((Flushable)this.a).flush();
/*      */       } catch (IOException localIOException) {
/* 2375 */         this.lastException = localIOException;
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
/*      */ 
/*      */   public void close()
/*      */   {
/* 2393 */     if (this.a == null)
/* 2394 */       return;
/*      */     try {
/* 2396 */       if ((this.a instanceof Closeable))
/* 2397 */         ((Closeable)this.a).close();
/*      */     } catch (IOException localIOException) {
/* 2399 */       this.lastException = localIOException;
/*      */     } finally {
/* 2401 */       this.a = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private void ensureOpen() {
/* 2406 */     if (this.a == null) {
/* 2407 */       throw new FormatterClosedException();
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
/*      */   public IOException ioException()
/*      */   {
/* 2421 */     return this.lastException;
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
/*      */   public Formatter format(String paramString, Object... paramVarArgs)
/*      */   {
/* 2455 */     return format(this.l, paramString, paramVarArgs);
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
/*      */   public Formatter format(Locale paramLocale, String paramString, Object... paramVarArgs)
/*      */   {
/* 2494 */     ensureOpen();
/*      */     
/*      */ 
/* 2497 */     int i = -1;
/*      */     
/* 2499 */     int j = -1;
/*      */     
/* 2501 */     FormatString[] arrayOfFormatString = parse(paramString);
/* 2502 */     for (int k = 0; k < arrayOfFormatString.length; k++) {
/* 2503 */       FormatString localFormatString = arrayOfFormatString[k];
/* 2504 */       int m = localFormatString.index();
/*      */       try {
/* 2506 */         switch (m) {
/*      */         case -2: 
/* 2508 */           localFormatString.print(null, paramLocale);
/* 2509 */           break;
/*      */         case -1: 
/* 2511 */           if ((i < 0) || ((paramVarArgs != null) && (i > paramVarArgs.length - 1)))
/* 2512 */             throw new MissingFormatArgumentException(localFormatString.toString());
/* 2513 */           localFormatString.print(paramVarArgs == null ? null : paramVarArgs[i], paramLocale);
/* 2514 */           break;
/*      */         case 0: 
/* 2516 */           j++;
/* 2517 */           i = j;
/* 2518 */           if ((paramVarArgs != null) && (j > paramVarArgs.length - 1))
/* 2519 */             throw new MissingFormatArgumentException(localFormatString.toString());
/* 2520 */           localFormatString.print(paramVarArgs == null ? null : paramVarArgs[j], paramLocale);
/* 2521 */           break;
/*      */         default: 
/* 2523 */           i = m - 1;
/* 2524 */           if ((paramVarArgs != null) && (i > paramVarArgs.length - 1))
/* 2525 */             throw new MissingFormatArgumentException(localFormatString.toString());
/* 2526 */           localFormatString.print(paramVarArgs == null ? null : paramVarArgs[i], paramLocale);
/*      */         }
/*      */       }
/*      */       catch (IOException localIOException) {
/* 2530 */         this.lastException = localIOException;
/*      */       }
/*      */     }
/* 2533 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2540 */   private static Pattern fsPattern = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
/*      */   
/*      */ 
/*      */ 
/*      */   private FormatString[] parse(String paramString)
/*      */   {
/* 2546 */     ArrayList localArrayList = new ArrayList();
/* 2547 */     Matcher localMatcher = fsPattern.matcher(paramString);
/* 2548 */     int i = 0; for (int j = paramString.length(); i < j;) {
/* 2549 */       if (localMatcher.find(i))
/*      */       {
/*      */ 
/*      */ 
/* 2553 */         if (localMatcher.start() != i)
/*      */         {
/* 2555 */           checkText(paramString, i, localMatcher.start());
/*      */           
/* 2557 */           localArrayList.add(new FixedString(paramString.substring(i, localMatcher.start())));
/*      */         }
/*      */         
/* 2560 */         localArrayList.add(new FormatSpecifier(localMatcher));
/* 2561 */         i = localMatcher.end();
/*      */       }
/*      */       else
/*      */       {
/* 2565 */         checkText(paramString, i, j);
/*      */         
/* 2567 */         localArrayList.add(new FixedString(paramString.substring(i)));
/*      */       }
/*      */     }
/*      */     
/* 2571 */     return (FormatString[])localArrayList.toArray(new FormatString[localArrayList.size()]);
/*      */   }
/*      */   
/*      */   private static void checkText(String paramString, int paramInt1, int paramInt2) {
/* 2575 */     for (int i = paramInt1; i < paramInt2; i++)
/*      */     {
/* 2577 */       if (paramString.charAt(i) == '%') {
/* 2578 */         char c = i == paramInt2 - 1 ? '%' : paramString.charAt(i + 1);
/* 2579 */         throw new UnknownFormatConversionException(String.valueOf(c));
/*      */       } }
/*      */   }
/*      */   
/*      */   private static abstract interface FormatString { public abstract int index();
/*      */     
/*      */     public abstract void print(Object paramObject, Locale paramLocale) throws IOException;
/*      */     
/*      */     public abstract String toString();
/*      */   }
/*      */   
/*      */   private class FixedString implements Formatter.FormatString { private String s;
/*      */     
/* 2592 */     FixedString(String paramString) { this.s = paramString; }
/* 2593 */     public int index() { return -2; }
/*      */     
/* 2595 */     public void print(Object paramObject, Locale paramLocale) throws IOException { Formatter.this.a.append(this.s); }
/* 2596 */     public String toString() { return this.s; }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static enum BigDecimalLayoutForm
/*      */   {
/* 2606 */     SCIENTIFIC, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2611 */     DECIMAL_FLOAT;
/*      */     
/*      */     private BigDecimalLayoutForm() {} }
/*      */   
/* 2615 */   private class FormatSpecifier implements Formatter.FormatString { private int index = -1;
/* 2616 */     private Formatter.Flags f = Formatter.Flags.NONE;
/*      */     private int width;
/*      */     private int precision;
/* 2619 */     private boolean dt = false;
/*      */     private char c;
/*      */     
/*      */     private int index(String paramString) {
/* 2623 */       if (paramString != null) {
/*      */         try {
/* 2625 */           this.index = Integer.parseInt(paramString.substring(0, paramString.length() - 1));
/*      */         } catch (NumberFormatException localNumberFormatException) {
/* 2627 */           if (!$assertionsDisabled) throw new AssertionError();
/*      */         }
/*      */       } else {
/* 2630 */         this.index = 0;
/*      */       }
/* 2632 */       return this.index;
/*      */     }
/*      */     
/*      */     public int index() {
/* 2636 */       return this.index;
/*      */     }
/*      */     
/*      */     private Formatter.Flags flags(String paramString) {
/* 2640 */       this.f = Formatter.Flags.parse(paramString);
/* 2641 */       if (this.f.contains(Formatter.Flags.PREVIOUS))
/* 2642 */         this.index = -1;
/* 2643 */       return this.f;
/*      */     }
/*      */     
/*      */     Formatter.Flags flags() {
/* 2647 */       return this.f;
/*      */     }
/*      */     
/*      */     private int width(String paramString) {
/* 2651 */       this.width = -1;
/* 2652 */       if (paramString != null) {
/*      */         try {
/* 2654 */           this.width = Integer.parseInt(paramString);
/* 2655 */           if (this.width < 0)
/* 2656 */             throw new IllegalFormatWidthException(this.width);
/*      */         } catch (NumberFormatException localNumberFormatException) {
/* 2658 */           if (!$assertionsDisabled) throw new AssertionError();
/*      */         }
/*      */       }
/* 2661 */       return this.width;
/*      */     }
/*      */     
/*      */     int width() {
/* 2665 */       return this.width;
/*      */     }
/*      */     
/*      */     private int precision(String paramString) {
/* 2669 */       this.precision = -1;
/* 2670 */       if (paramString != null) {
/*      */         try
/*      */         {
/* 2673 */           this.precision = Integer.parseInt(paramString.substring(1));
/* 2674 */           if (this.precision < 0)
/* 2675 */             throw new IllegalFormatPrecisionException(this.precision);
/*      */         } catch (NumberFormatException localNumberFormatException) {
/* 2677 */           if (!$assertionsDisabled) throw new AssertionError();
/*      */         }
/*      */       }
/* 2680 */       return this.precision;
/*      */     }
/*      */     
/*      */     int precision() {
/* 2684 */       return this.precision;
/*      */     }
/*      */     
/*      */     private char conversion(String paramString) {
/* 2688 */       this.c = paramString.charAt(0);
/* 2689 */       if (!this.dt) {
/* 2690 */         if (!Formatter.Conversion.isValid(this.c))
/* 2691 */           throw new UnknownFormatConversionException(String.valueOf(this.c));
/* 2692 */         if (Character.isUpperCase(this.c))
/* 2693 */           this.f.add(Formatter.Flags.UPPERCASE);
/* 2694 */         this.c = Character.toLowerCase(this.c);
/* 2695 */         if (Formatter.Conversion.isText(this.c))
/* 2696 */           this.index = -2;
/*      */       }
/* 2698 */       return this.c;
/*      */     }
/*      */     
/*      */     private char conversion() {
/* 2702 */       return this.c;
/*      */     }
/*      */     
/*      */     FormatSpecifier(Matcher paramMatcher) {
/* 2706 */       int i = 1;
/*      */       
/* 2708 */       index(paramMatcher.group(i++));
/* 2709 */       flags(paramMatcher.group(i++));
/* 2710 */       width(paramMatcher.group(i++));
/* 2711 */       precision(paramMatcher.group(i++));
/*      */       
/* 2713 */       String str = paramMatcher.group(i++);
/* 2714 */       if (str != null) {
/* 2715 */         this.dt = true;
/* 2716 */         if (str.equals("T")) {
/* 2717 */           this.f.add(Formatter.Flags.UPPERCASE);
/*      */         }
/*      */       }
/* 2720 */       conversion(paramMatcher.group(i));
/*      */       
/* 2722 */       if (this.dt) {
/* 2723 */         checkDateTime();
/* 2724 */       } else if (Formatter.Conversion.isGeneral(this.c)) {
/* 2725 */         checkGeneral();
/* 2726 */       } else if (Formatter.Conversion.isCharacter(this.c)) {
/* 2727 */         checkCharacter();
/* 2728 */       } else if (Formatter.Conversion.isInteger(this.c)) {
/* 2729 */         checkInteger();
/* 2730 */       } else if (Formatter.Conversion.isFloat(this.c)) {
/* 2731 */         checkFloat();
/* 2732 */       } else if (Formatter.Conversion.isText(this.c)) {
/* 2733 */         checkText();
/*      */       } else
/* 2735 */         throw new UnknownFormatConversionException(String.valueOf(this.c));
/*      */     }
/*      */     
/*      */     public void print(Object paramObject, Locale paramLocale) throws IOException {
/* 2739 */       if (this.dt) {
/* 2740 */         printDateTime(paramObject, paramLocale);
/* 2741 */         return;
/*      */       }
/* 2743 */       switch (this.c) {
/*      */       case 'd': 
/*      */       case 'o': 
/*      */       case 'x': 
/* 2747 */         printInteger(paramObject, paramLocale);
/* 2748 */         break;
/*      */       case 'a': 
/*      */       case 'e': 
/*      */       case 'f': 
/*      */       case 'g': 
/* 2753 */         printFloat(paramObject, paramLocale);
/* 2754 */         break;
/*      */       case 'C': 
/*      */       case 'c': 
/* 2757 */         printCharacter(paramObject);
/* 2758 */         break;
/*      */       case 'b': 
/* 2760 */         printBoolean(paramObject);
/* 2761 */         break;
/*      */       case 's': 
/* 2763 */         printString(paramObject, paramLocale);
/* 2764 */         break;
/*      */       case 'h': 
/* 2766 */         printHashCode(paramObject);
/* 2767 */         break;
/*      */       case 'n': 
/* 2769 */         Formatter.this.a.append(System.lineSeparator());
/* 2770 */         break;
/*      */       case '%': 
/* 2772 */         Formatter.this.a.append('%');
/* 2773 */         break;
/*      */       default: 
/* 2775 */         if (!$assertionsDisabled) throw new AssertionError();
/*      */         break; }
/*      */     }
/*      */     
/*      */     private void printInteger(Object paramObject, Locale paramLocale) throws IOException {
/* 2780 */       if (paramObject == null) {
/* 2781 */         print("null");
/* 2782 */       } else if ((paramObject instanceof Byte)) {
/* 2783 */         print(((Byte)paramObject).byteValue(), paramLocale);
/* 2784 */       } else if ((paramObject instanceof Short)) {
/* 2785 */         print(((Short)paramObject).shortValue(), paramLocale);
/* 2786 */       } else if ((paramObject instanceof Integer)) {
/* 2787 */         print(((Integer)paramObject).intValue(), paramLocale);
/* 2788 */       } else if ((paramObject instanceof Long)) {
/* 2789 */         print(((Long)paramObject).longValue(), paramLocale);
/* 2790 */       } else if ((paramObject instanceof BigInteger)) {
/* 2791 */         print((BigInteger)paramObject, paramLocale);
/*      */       } else
/* 2793 */         failConversion(this.c, paramObject);
/*      */     }
/*      */     
/*      */     private void printFloat(Object paramObject, Locale paramLocale) throws IOException {
/* 2797 */       if (paramObject == null) {
/* 2798 */         print("null");
/* 2799 */       } else if ((paramObject instanceof Float)) {
/* 2800 */         print(((Float)paramObject).floatValue(), paramLocale);
/* 2801 */       } else if ((paramObject instanceof Double)) {
/* 2802 */         print(((Double)paramObject).doubleValue(), paramLocale);
/* 2803 */       } else if ((paramObject instanceof BigDecimal)) {
/* 2804 */         print((BigDecimal)paramObject, paramLocale);
/*      */       } else
/* 2806 */         failConversion(this.c, paramObject);
/*      */     }
/*      */     
/*      */     private void printDateTime(Object paramObject, Locale paramLocale) throws IOException {
/* 2810 */       if (paramObject == null) {
/* 2811 */         print("null");
/* 2812 */         return;
/*      */       }
/* 2814 */       Calendar localCalendar = null;
/*      */       
/*      */ 
/*      */ 
/* 2818 */       if ((paramObject instanceof Long))
/*      */       {
/*      */ 
/* 2821 */         localCalendar = Calendar.getInstance(paramLocale == null ? Locale.US : paramLocale);
/* 2822 */         localCalendar.setTimeInMillis(((Long)paramObject).longValue());
/* 2823 */       } else if ((paramObject instanceof Date))
/*      */       {
/*      */ 
/* 2826 */         localCalendar = Calendar.getInstance(paramLocale == null ? Locale.US : paramLocale);
/* 2827 */         localCalendar.setTime((Date)paramObject);
/* 2828 */       } else if ((paramObject instanceof Calendar)) {
/* 2829 */         localCalendar = (Calendar)((Calendar)paramObject).clone();
/* 2830 */         localCalendar.setLenient(true);
/* 2831 */       } else { if ((paramObject instanceof TemporalAccessor)) {
/* 2832 */           print((TemporalAccessor)paramObject, this.c, paramLocale);
/* 2833 */           return;
/*      */         }
/* 2835 */         failConversion(this.c, paramObject);
/*      */       }
/*      */       
/*      */ 
/* 2839 */       print(localCalendar, this.c, paramLocale);
/*      */     }
/*      */     
/*      */     private void printCharacter(Object paramObject) throws IOException {
/* 2843 */       if (paramObject == null) {
/* 2844 */         print("null");
/* 2845 */         return;
/*      */       }
/* 2847 */       String str = null;
/* 2848 */       if ((paramObject instanceof Character)) {
/* 2849 */         str = ((Character)paramObject).toString(); } else { int i;
/* 2850 */         if ((paramObject instanceof Byte)) {
/* 2851 */           i = ((Byte)paramObject).byteValue();
/* 2852 */           if (Character.isValidCodePoint(i)) {
/* 2853 */             str = new String(Character.toChars(i));
/*      */           } else
/* 2855 */             throw new IllegalFormatCodePointException(i);
/* 2856 */         } else if ((paramObject instanceof Short)) {
/* 2857 */           i = ((Short)paramObject).shortValue();
/* 2858 */           if (Character.isValidCodePoint(i)) {
/* 2859 */             str = new String(Character.toChars(i));
/*      */           } else
/* 2861 */             throw new IllegalFormatCodePointException(i);
/* 2862 */         } else if ((paramObject instanceof Integer)) {
/* 2863 */           i = ((Integer)paramObject).intValue();
/* 2864 */           if (Character.isValidCodePoint(i)) {
/* 2865 */             str = new String(Character.toChars(i));
/*      */           } else
/* 2867 */             throw new IllegalFormatCodePointException(i);
/*      */         } else {
/* 2869 */           failConversion(this.c, paramObject);
/*      */         } }
/* 2871 */       print(str);
/*      */     }
/*      */     
/*      */     private void printString(Object paramObject, Locale paramLocale) throws IOException {
/* 2875 */       if ((paramObject instanceof Formattable)) {
/* 2876 */         Formatter localFormatter = Formatter.this;
/* 2877 */         if (localFormatter.locale() != paramLocale)
/* 2878 */           localFormatter = new Formatter(localFormatter.out(), paramLocale);
/* 2879 */         ((Formattable)paramObject).formatTo(localFormatter, this.f.valueOf(), this.width, this.precision);
/*      */       } else {
/* 2881 */         if (this.f.contains(Formatter.Flags.ALTERNATE))
/* 2882 */           failMismatch(Formatter.Flags.ALTERNATE, 's');
/* 2883 */         if (paramObject == null) {
/* 2884 */           print("null");
/*      */         } else
/* 2886 */           print(paramObject.toString());
/*      */       }
/*      */     }
/*      */     
/*      */     private void printBoolean(Object paramObject) throws IOException {
/*      */       String str;
/* 2892 */       if (paramObject != null)
/*      */       {
/*      */ 
/* 2895 */         str = (paramObject instanceof Boolean) ? ((Boolean)paramObject).toString() : Boolean.toString(true);
/*      */       } else
/* 2897 */         str = Boolean.toString(false);
/* 2898 */       print(str);
/*      */     }
/*      */     
/*      */     private void printHashCode(Object paramObject)
/*      */       throws IOException
/*      */     {
/* 2904 */       String str = paramObject == null ? "null" : Integer.toHexString(paramObject.hashCode());
/* 2905 */       print(str);
/*      */     }
/*      */     
/*      */     private void print(String paramString) throws IOException {
/* 2909 */       if ((this.precision != -1) && (this.precision < paramString.length()))
/* 2910 */         paramString = paramString.substring(0, this.precision);
/* 2911 */       if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 2912 */         paramString = paramString.toUpperCase();
/* 2913 */       Formatter.this.a.append(justify(paramString));
/*      */     }
/*      */     
/*      */     private String justify(String paramString) {
/* 2917 */       if (this.width == -1)
/* 2918 */         return paramString;
/* 2919 */       StringBuilder localStringBuilder = new StringBuilder();
/* 2920 */       boolean bool = this.f.contains(Formatter.Flags.LEFT_JUSTIFY);
/* 2921 */       int i = this.width - paramString.length();
/* 2922 */       int j; if (!bool)
/* 2923 */         for (j = 0; j < i; j++) localStringBuilder.append(' ');
/* 2924 */       localStringBuilder.append(paramString);
/* 2925 */       if (bool)
/* 2926 */         for (j = 0; j < i; j++) localStringBuilder.append(' ');
/* 2927 */       return localStringBuilder.toString();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2931 */       StringBuilder localStringBuilder = new StringBuilder("%");
/*      */       
/* 2933 */       Formatter.Flags localFlags = this.f.dup().remove(Formatter.Flags.UPPERCASE);
/* 2934 */       localStringBuilder.append(localFlags.toString());
/* 2935 */       if (this.index > 0)
/* 2936 */         localStringBuilder.append(this.index).append('$');
/* 2937 */       if (this.width != -1)
/* 2938 */         localStringBuilder.append(this.width);
/* 2939 */       if (this.precision != -1)
/* 2940 */         localStringBuilder.append('.').append(this.precision);
/* 2941 */       if (this.dt)
/* 2942 */         localStringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? 'T' : 't');
/* 2943 */       localStringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? 
/* 2944 */         Character.toUpperCase(this.c) : this.c);
/* 2945 */       return localStringBuilder.toString();
/*      */     }
/*      */     
/*      */     private void checkGeneral() {
/* 2949 */       if (((this.c == 'b') || (this.c == 'h')) && 
/* 2950 */         (this.f.contains(Formatter.Flags.ALTERNATE))) {
/* 2951 */         failMismatch(Formatter.Flags.ALTERNATE, this.c);
/*      */       }
/* 2953 */       if ((this.width == -1) && (this.f.contains(Formatter.Flags.LEFT_JUSTIFY)))
/* 2954 */         throw new MissingFormatWidthException(toString());
/* 2955 */       checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PLUS, Formatter.Flags.LEADING_SPACE, Formatter.Flags.ZERO_PAD, Formatter.Flags.GROUP, Formatter.Flags.PARENTHESES });
/*      */     }
/*      */     
/*      */     private void checkDateTime()
/*      */     {
/* 2960 */       if (this.precision != -1)
/* 2961 */         throw new IllegalFormatPrecisionException(this.precision);
/* 2962 */       if (!Formatter.DateTime.isValid(this.c))
/* 2963 */         throw new UnknownFormatConversionException("t" + this.c);
/* 2964 */       checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE, Formatter.Flags.PLUS, Formatter.Flags.LEADING_SPACE, Formatter.Flags.ZERO_PAD, Formatter.Flags.GROUP, Formatter.Flags.PARENTHESES });
/*      */       
/*      */ 
/* 2967 */       if ((this.width == -1) && (this.f.contains(Formatter.Flags.LEFT_JUSTIFY)))
/* 2968 */         throw new MissingFormatWidthException(toString());
/*      */     }
/*      */     
/*      */     private void checkCharacter() {
/* 2972 */       if (this.precision != -1)
/* 2973 */         throw new IllegalFormatPrecisionException(this.precision);
/* 2974 */       checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE, Formatter.Flags.PLUS, Formatter.Flags.LEADING_SPACE, Formatter.Flags.ZERO_PAD, Formatter.Flags.GROUP, Formatter.Flags.PARENTHESES });
/*      */       
/*      */ 
/* 2977 */       if ((this.width == -1) && (this.f.contains(Formatter.Flags.LEFT_JUSTIFY)))
/* 2978 */         throw new MissingFormatWidthException(toString());
/*      */     }
/*      */     
/*      */     private void checkInteger() {
/* 2982 */       checkNumeric();
/* 2983 */       if (this.precision != -1) {
/* 2984 */         throw new IllegalFormatPrecisionException(this.precision);
/*      */       }
/* 2986 */       if (this.c == 'd') {
/* 2987 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE });
/* 2988 */       } else if (this.c == 'o') {
/* 2989 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.GROUP });
/*      */       } else
/* 2991 */         checkBadFlags(new Formatter.Flags[] { Formatter.Flags.GROUP });
/*      */     }
/*      */     
/*      */     private void checkBadFlags(Formatter.Flags... paramVarArgs) {
/* 2995 */       for (int i = 0; i < paramVarArgs.length; i++)
/* 2996 */         if (this.f.contains(paramVarArgs[i]))
/* 2997 */           failMismatch(paramVarArgs[i], this.c);
/*      */     }
/*      */     
/*      */     private void checkFloat() {
/* 3001 */       checkNumeric();
/* 3002 */       if (this.c != 'f') {
/* 3003 */         if (this.c == 'a') {
/* 3004 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PARENTHESES, Formatter.Flags.GROUP });
/* 3005 */         } else if (this.c == 'e') {
/* 3006 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.GROUP });
/* 3007 */         } else if (this.c == 'g')
/* 3008 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.ALTERNATE });
/*      */       }
/*      */     }
/*      */     
/*      */     private void checkNumeric() {
/* 3013 */       if ((this.width != -1) && (this.width < 0)) {
/* 3014 */         throw new IllegalFormatWidthException(this.width);
/*      */       }
/* 3016 */       if ((this.precision != -1) && (this.precision < 0)) {
/* 3017 */         throw new IllegalFormatPrecisionException(this.precision);
/*      */       }
/*      */       
/* 3020 */       if ((this.width == -1) && (
/* 3021 */         (this.f.contains(Formatter.Flags.LEFT_JUSTIFY)) || (this.f.contains(Formatter.Flags.ZERO_PAD)))) {
/* 3022 */         throw new MissingFormatWidthException(toString());
/*      */       }
/*      */       
/* 3025 */       if (((this.f.contains(Formatter.Flags.PLUS)) && (this.f.contains(Formatter.Flags.LEADING_SPACE))) || (
/* 3026 */         (this.f.contains(Formatter.Flags.LEFT_JUSTIFY)) && (this.f.contains(Formatter.Flags.ZERO_PAD))))
/* 3027 */         throw new IllegalFormatFlagsException(this.f.toString());
/*      */     }
/*      */     
/*      */     private void checkText() {
/* 3031 */       if (this.precision != -1)
/* 3032 */         throw new IllegalFormatPrecisionException(this.precision);
/* 3033 */       switch (this.c) {
/*      */       case '%': 
/* 3035 */         if ((this.f.valueOf() != Formatter.Flags.LEFT_JUSTIFY.valueOf()) && 
/* 3036 */           (this.f.valueOf() != Formatter.Flags.NONE.valueOf())) {
/* 3037 */           throw new IllegalFormatFlagsException(this.f.toString());
/*      */         }
/* 3039 */         if ((this.width == -1) && (this.f.contains(Formatter.Flags.LEFT_JUSTIFY)))
/* 3040 */           throw new MissingFormatWidthException(toString());
/*      */         break;
/*      */       case 'n': 
/* 3043 */         if (this.width != -1)
/* 3044 */           throw new IllegalFormatWidthException(this.width);
/* 3045 */         if (this.f.valueOf() != Formatter.Flags.NONE.valueOf())
/* 3046 */           throw new IllegalFormatFlagsException(this.f.toString());
/*      */         break;
/*      */       default: 
/* 3049 */         if (!$assertionsDisabled) throw new AssertionError();
/*      */         break; }
/*      */     }
/*      */     
/*      */     private void print(byte paramByte, Locale paramLocale) throws IOException {
/* 3054 */       long l = paramByte;
/* 3055 */       if ((paramByte < 0) && ((this.c == 'o') || (this.c == 'x')))
/*      */       {
/*      */ 
/* 3058 */         l += 256L;
/* 3059 */         assert (l >= 0L) : l;
/*      */       }
/* 3061 */       print(l, paramLocale);
/*      */     }
/*      */     
/*      */     private void print(short paramShort, Locale paramLocale) throws IOException {
/* 3065 */       long l = paramShort;
/* 3066 */       if ((paramShort < 0) && ((this.c == 'o') || (this.c == 'x')))
/*      */       {
/*      */ 
/* 3069 */         l += 65536L;
/* 3070 */         assert (l >= 0L) : l;
/*      */       }
/* 3072 */       print(l, paramLocale);
/*      */     }
/*      */     
/*      */     private void print(int paramInt, Locale paramLocale) throws IOException {
/* 3076 */       long l = paramInt;
/* 3077 */       if ((paramInt < 0) && ((this.c == 'o') || (this.c == 'x')))
/*      */       {
/*      */ 
/* 3080 */         l += 4294967296L;
/* 3081 */         assert (l >= 0L) : l;
/*      */       }
/* 3083 */       print(l, paramLocale);
/*      */     }
/*      */     
/*      */     private void print(long paramLong, Locale paramLocale) throws IOException
/*      */     {
/* 3088 */       StringBuilder localStringBuilder = new StringBuilder();
/*      */       
/* 3090 */       if (this.c == 'd') {
/* 3091 */         boolean bool = paramLong < 0L;
/*      */         char[] arrayOfChar;
/* 3093 */         if (paramLong < 0L) {
/* 3094 */           arrayOfChar = Long.toString(paramLong, 10).substring(1).toCharArray();
/*      */         } else {
/* 3096 */           arrayOfChar = Long.toString(paramLong, 10).toCharArray();
/*      */         }
/*      */         
/* 3099 */         leadingSign(localStringBuilder, bool);
/*      */         
/*      */ 
/* 3102 */         localizedMagnitude(localStringBuilder, arrayOfChar, this.f, adjustWidth(this.width, this.f, bool), paramLocale);
/*      */         
/*      */ 
/* 3105 */         trailingSign(localStringBuilder, bool); } else { String str;
/* 3106 */         int i; int j; if (this.c == 'o') {
/* 3107 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PARENTHESES, Formatter.Flags.LEADING_SPACE, Formatter.Flags.PLUS });
/*      */           
/* 3109 */           str = Long.toOctalString(paramLong);
/*      */           
/*      */ 
/* 3112 */           i = this.f.contains(Formatter.Flags.ALTERNATE) ? str.length() + 1 : str.length();
/*      */           
/*      */ 
/* 3115 */           if (this.f.contains(Formatter.Flags.ALTERNATE))
/* 3116 */             localStringBuilder.append('0');
/* 3117 */           if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3118 */             for (j = 0; j < this.width - i; j++) localStringBuilder.append('0');
/* 3119 */           localStringBuilder.append(str);
/* 3120 */         } else if (this.c == 'x') {
/* 3121 */           checkBadFlags(new Formatter.Flags[] { Formatter.Flags.PARENTHESES, Formatter.Flags.LEADING_SPACE, Formatter.Flags.PLUS });
/*      */           
/* 3123 */           str = Long.toHexString(paramLong);
/*      */           
/*      */ 
/* 3126 */           i = this.f.contains(Formatter.Flags.ALTERNATE) ? str.length() + 2 : str.length();
/*      */           
/*      */ 
/* 3129 */           if (this.f.contains(Formatter.Flags.ALTERNATE))
/* 3130 */             localStringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "0X" : "0x");
/* 3131 */           if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3132 */             for (j = 0; j < this.width - i; j++) localStringBuilder.append('0');
/* 3133 */           if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 3134 */             str = str.toUpperCase();
/* 3135 */           localStringBuilder.append(str);
/*      */         }
/*      */       }
/*      */       
/* 3139 */       Formatter.this.a.append(justify(localStringBuilder.toString()));
/*      */     }
/*      */     
/*      */     private StringBuilder leadingSign(StringBuilder paramStringBuilder, boolean paramBoolean)
/*      */     {
/* 3144 */       if (!paramBoolean) {
/* 3145 */         if (this.f.contains(Formatter.Flags.PLUS)) {
/* 3146 */           paramStringBuilder.append('+');
/* 3147 */         } else if (this.f.contains(Formatter.Flags.LEADING_SPACE)) {
/* 3148 */           paramStringBuilder.append(' ');
/*      */         }
/*      */       }
/* 3151 */       else if (this.f.contains(Formatter.Flags.PARENTHESES)) {
/* 3152 */         paramStringBuilder.append('(');
/*      */       } else {
/* 3154 */         paramStringBuilder.append('-');
/*      */       }
/* 3156 */       return paramStringBuilder;
/*      */     }
/*      */     
/*      */     private StringBuilder trailingSign(StringBuilder paramStringBuilder, boolean paramBoolean)
/*      */     {
/* 3161 */       if ((paramBoolean) && (this.f.contains(Formatter.Flags.PARENTHESES)))
/* 3162 */         paramStringBuilder.append(')');
/* 3163 */       return paramStringBuilder;
/*      */     }
/*      */     
/*      */     private void print(BigInteger paramBigInteger, Locale paramLocale) throws IOException {
/* 3167 */       StringBuilder localStringBuilder = new StringBuilder();
/* 3168 */       boolean bool = paramBigInteger.signum() == -1;
/* 3169 */       BigInteger localBigInteger = paramBigInteger.abs();
/*      */       
/*      */ 
/* 3172 */       leadingSign(localStringBuilder, bool);
/*      */       
/*      */       Object localObject;
/* 3175 */       if (this.c == 'd') {
/* 3176 */         localObject = localBigInteger.toString().toCharArray();
/* 3177 */         localizedMagnitude(localStringBuilder, (char[])localObject, this.f, adjustWidth(this.width, this.f, bool), paramLocale); } else { int i;
/* 3178 */         int j; if (this.c == 'o') {
/* 3179 */           localObject = localBigInteger.toString(8);
/*      */           
/* 3181 */           i = ((String)localObject).length() + localStringBuilder.length();
/* 3182 */           if ((bool) && (this.f.contains(Formatter.Flags.PARENTHESES))) {
/* 3183 */             i++;
/*      */           }
/*      */           
/* 3186 */           if (this.f.contains(Formatter.Flags.ALTERNATE)) {
/* 3187 */             i++;
/* 3188 */             localStringBuilder.append('0');
/*      */           }
/* 3190 */           if (this.f.contains(Formatter.Flags.ZERO_PAD)) {
/* 3191 */             for (j = 0; j < this.width - i; j++)
/* 3192 */               localStringBuilder.append('0');
/*      */           }
/* 3194 */           localStringBuilder.append((String)localObject);
/* 3195 */         } else if (this.c == 'x') {
/* 3196 */           localObject = localBigInteger.toString(16);
/*      */           
/* 3198 */           i = ((String)localObject).length() + localStringBuilder.length();
/* 3199 */           if ((bool) && (this.f.contains(Formatter.Flags.PARENTHESES))) {
/* 3200 */             i++;
/*      */           }
/*      */           
/* 3203 */           if (this.f.contains(Formatter.Flags.ALTERNATE)) {
/* 3204 */             i += 2;
/* 3205 */             localStringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "0X" : "0x");
/*      */           }
/* 3207 */           if (this.f.contains(Formatter.Flags.ZERO_PAD))
/* 3208 */             for (j = 0; j < this.width - i; j++)
/* 3209 */               localStringBuilder.append('0');
/* 3210 */           if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 3211 */             localObject = ((String)localObject).toUpperCase();
/* 3212 */           localStringBuilder.append((String)localObject);
/*      */         }
/*      */       }
/*      */       
/* 3216 */       trailingSign(localStringBuilder, paramBigInteger.signum() == -1);
/*      */       
/*      */ 
/* 3219 */       Formatter.this.a.append(justify(localStringBuilder.toString()));
/*      */     }
/*      */     
/*      */     private void print(float paramFloat, Locale paramLocale) throws IOException {
/* 3223 */       print(paramFloat, paramLocale);
/*      */     }
/*      */     
/*      */     private void print(double paramDouble, Locale paramLocale) throws IOException {
/* 3227 */       StringBuilder localStringBuilder = new StringBuilder();
/* 3228 */       boolean bool = Double.compare(paramDouble, 0.0D) == -1;
/*      */       
/* 3230 */       if (!Double.isNaN(paramDouble)) {
/* 3231 */         double d = Math.abs(paramDouble);
/*      */         
/*      */ 
/* 3234 */         leadingSign(localStringBuilder, bool);
/*      */         
/*      */ 
/* 3237 */         if (!Double.isInfinite(d)) {
/* 3238 */           print(localStringBuilder, d, paramLocale, this.f, this.c, this.precision, bool);
/*      */         } else {
/* 3240 */           localStringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "INFINITY" : "Infinity");
/*      */         }
/*      */         
/*      */ 
/* 3244 */         trailingSign(localStringBuilder, bool);
/*      */       } else {
/* 3246 */         localStringBuilder.append(this.f.contains(Formatter.Flags.UPPERCASE) ? "NAN" : "NaN");
/*      */       }
/*      */       
/*      */ 
/* 3250 */       Formatter.this.a.append(justify(localStringBuilder.toString())); }
/*      */     
/*      */     private void print(StringBuilder paramStringBuilder, double paramDouble, Locale paramLocale, Formatter.Flags paramFlags, char paramChar, int paramInt, boolean paramBoolean) throws IOException { int i;
/*      */       Object localObject1;
/*      */       char[] arrayOfChar1;
/*      */       Object localObject2;
/*      */       char c1;
/*      */       char[] arrayOfChar3;
/* 3258 */       if (paramChar == 'e')
/*      */       {
/*      */ 
/* 3261 */         i = paramInt == -1 ? 6 : paramInt;
/*      */         
/*      */ 
/* 3264 */         localObject1 = FormattedFloatingDecimal.valueOf(paramDouble, i, FormattedFloatingDecimal.Form.SCIENTIFIC);
/*      */         
/*      */ 
/* 3267 */         arrayOfChar1 = addZeros(((FormattedFloatingDecimal)localObject1).getMantissa(), i);
/*      */         
/*      */ 
/*      */ 
/* 3271 */         if ((paramFlags.contains(Formatter.Flags.ALTERNATE)) && (i == 0)) {
/* 3272 */           arrayOfChar1 = addDot(arrayOfChar1);
/*      */         }
/*      */         
/* 3275 */         char[] arrayOfChar2 = paramDouble == 0.0D ? new char[] { '+', '0', '0' } : ((FormattedFloatingDecimal)localObject1).getExponent();
/*      */         
/* 3277 */         int k = this.width;
/* 3278 */         if (this.width != -1)
/* 3279 */           k = adjustWidth(this.width - arrayOfChar2.length - 1, paramFlags, paramBoolean);
/* 3280 */         localizedMagnitude(paramStringBuilder, arrayOfChar1, paramFlags, k, paramLocale);
/*      */         
/* 3282 */         paramStringBuilder.append(paramFlags.contains(Formatter.Flags.UPPERCASE) ? 'E' : 'e');
/*      */         
/* 3284 */         localObject2 = paramFlags.dup().remove(Formatter.Flags.GROUP);
/* 3285 */         c1 = arrayOfChar2[0];
/* 3286 */         assert ((c1 == '+') || (c1 == '-'));
/* 3287 */         paramStringBuilder.append(c1);
/*      */         
/* 3289 */         arrayOfChar3 = new char[arrayOfChar2.length - 1];
/* 3290 */         System.arraycopy(arrayOfChar2, 1, arrayOfChar3, 0, arrayOfChar2.length - 1);
/* 3291 */         paramStringBuilder.append(localizedMagnitude(null, arrayOfChar3, (Formatter.Flags)localObject2, -1, paramLocale)); } else { int j;
/* 3292 */         if (paramChar == 'f')
/*      */         {
/*      */ 
/* 3295 */           i = paramInt == -1 ? 6 : paramInt;
/*      */           
/*      */ 
/* 3298 */           localObject1 = FormattedFloatingDecimal.valueOf(paramDouble, i, FormattedFloatingDecimal.Form.DECIMAL_FLOAT);
/*      */           
/*      */ 
/* 3301 */           arrayOfChar1 = addZeros(((FormattedFloatingDecimal)localObject1).getMantissa(), i);
/*      */           
/*      */ 
/*      */ 
/* 3305 */           if ((paramFlags.contains(Formatter.Flags.ALTERNATE)) && (i == 0)) {
/* 3306 */             arrayOfChar1 = addDot(arrayOfChar1);
/*      */           }
/* 3308 */           j = this.width;
/* 3309 */           if (this.width != -1)
/* 3310 */             j = adjustWidth(this.width, paramFlags, paramBoolean);
/* 3311 */           localizedMagnitude(paramStringBuilder, arrayOfChar1, paramFlags, j, paramLocale); } else { int m;
/* 3312 */           if (paramChar == 'g') {
/* 3313 */             i = paramInt;
/* 3314 */             if (paramInt == -1) {
/* 3315 */               i = 6;
/* 3316 */             } else if (paramInt == 0) {
/* 3317 */               i = 1;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3322 */             if (paramDouble == 0.0D) {
/* 3323 */               localObject1 = null;
/* 3324 */               arrayOfChar1 = new char[] { '0' };
/* 3325 */               j = 0;
/*      */             }
/*      */             else {
/* 3328 */               FormattedFloatingDecimal localFormattedFloatingDecimal = FormattedFloatingDecimal.valueOf(paramDouble, i, FormattedFloatingDecimal.Form.GENERAL);
/*      */               
/* 3330 */               localObject1 = localFormattedFloatingDecimal.getExponent();
/* 3331 */               arrayOfChar1 = localFormattedFloatingDecimal.getMantissa();
/* 3332 */               j = localFormattedFloatingDecimal.getExponentRounded();
/*      */             }
/*      */             
/* 3335 */             if (localObject1 != null) {
/* 3336 */               i--;
/*      */             } else {
/* 3338 */               i -= j + 1;
/*      */             }
/*      */             
/* 3341 */             arrayOfChar1 = addZeros(arrayOfChar1, i);
/*      */             
/*      */ 
/* 3344 */             if ((paramFlags.contains(Formatter.Flags.ALTERNATE)) && (i == 0)) {
/* 3345 */               arrayOfChar1 = addDot(arrayOfChar1);
/*      */             }
/* 3347 */             m = this.width;
/* 3348 */             if (this.width != -1) {
/* 3349 */               if (localObject1 != null) {
/* 3350 */                 m = adjustWidth(this.width - localObject1.length - 1, paramFlags, paramBoolean);
/*      */               } else
/* 3352 */                 m = adjustWidth(this.width, paramFlags, paramBoolean);
/*      */             }
/* 3354 */             localizedMagnitude(paramStringBuilder, arrayOfChar1, paramFlags, m, paramLocale);
/*      */             
/* 3356 */             if (localObject1 != null) {
/* 3357 */               paramStringBuilder.append(paramFlags.contains(Formatter.Flags.UPPERCASE) ? 'E' : 'e');
/*      */               
/* 3359 */               localObject2 = paramFlags.dup().remove(Formatter.Flags.GROUP);
/* 3360 */               c1 = localObject1[0];
/* 3361 */               assert ((c1 == '+') || (c1 == '-'));
/* 3362 */               paramStringBuilder.append(c1);
/*      */               
/* 3364 */               arrayOfChar3 = new char[localObject1.length - 1];
/* 3365 */               System.arraycopy(localObject1, 1, arrayOfChar3, 0, localObject1.length - 1);
/* 3366 */               paramStringBuilder.append(localizedMagnitude(null, arrayOfChar3, (Formatter.Flags)localObject2, -1, paramLocale));
/*      */             }
/* 3368 */           } else if (paramChar == 'a') {
/* 3369 */             i = paramInt;
/* 3370 */             if (paramInt == -1)
/*      */             {
/* 3372 */               i = 0;
/* 3373 */             } else if (paramInt == 0) {
/* 3374 */               i = 1;
/*      */             }
/* 3376 */             localObject1 = hexDouble(paramDouble, i);
/*      */             
/*      */ 
/* 3379 */             boolean bool = paramFlags.contains(Formatter.Flags.UPPERCASE);
/* 3380 */             paramStringBuilder.append(bool ? "0X" : "0x");
/*      */             
/* 3382 */             if (paramFlags.contains(Formatter.Flags.ZERO_PAD)) {
/* 3383 */               for (m = 0; m < this.width - ((String)localObject1).length() - 2; m++)
/* 3384 */                 paramStringBuilder.append('0');
/*      */             }
/* 3386 */             m = ((String)localObject1).indexOf('p');
/* 3387 */             arrayOfChar1 = ((String)localObject1).substring(0, m).toCharArray();
/* 3388 */             if (bool) {
/* 3389 */               localObject2 = new String(arrayOfChar1);
/*      */               
/* 3391 */               localObject2 = ((String)localObject2).toUpperCase(Locale.US);
/* 3392 */               arrayOfChar1 = ((String)localObject2).toCharArray();
/*      */             }
/* 3394 */             paramStringBuilder.append(i != 0 ? addZeros(arrayOfChar1, i) : arrayOfChar1);
/* 3395 */             paramStringBuilder.append(bool ? 'P' : 'p');
/* 3396 */             paramStringBuilder.append(((String)localObject1).substring(m + 1));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private char[] addZeros(char[] paramArrayOfChar, int paramInt)
/*      */     {
/* 3405 */       for (int i = 0; i < paramArrayOfChar.length; i++) {
/* 3406 */         if (paramArrayOfChar[i] == '.')
/*      */           break;
/*      */       }
/* 3409 */       int j = 0;
/* 3410 */       if (i == paramArrayOfChar.length) {
/* 3411 */         j = 1;
/*      */       }
/*      */       
/*      */ 
/* 3415 */       int k = paramArrayOfChar.length - i - (j != 0 ? 0 : 1);
/* 3416 */       assert (k <= paramInt);
/* 3417 */       if (k == paramInt) {
/* 3418 */         return paramArrayOfChar;
/*      */       }
/*      */       
/* 3421 */       char[] arrayOfChar = new char[paramArrayOfChar.length + paramInt - k + (j != 0 ? 1 : 0)];
/*      */       
/* 3423 */       System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, paramArrayOfChar.length);
/*      */       
/*      */ 
/* 3426 */       int m = paramArrayOfChar.length;
/* 3427 */       if (j != 0) {
/* 3428 */         arrayOfChar[paramArrayOfChar.length] = '.';
/* 3429 */         m++;
/*      */       }
/*      */       
/*      */ 
/* 3433 */       for (int n = m; n < arrayOfChar.length; n++) {
/* 3434 */         arrayOfChar[n] = '0';
/*      */       }
/* 3436 */       return arrayOfChar;
/*      */     }
/*      */     
/*      */ 
/*      */     private String hexDouble(double paramDouble, int paramInt)
/*      */     {
/* 3442 */       if ((!Double.isFinite(paramDouble)) || (paramDouble == 0.0D) || (paramInt == 0) || (paramInt >= 13))
/*      */       {
/* 3444 */         return Double.toHexString(paramDouble).substring(2);
/*      */       }
/* 3446 */       assert ((paramInt >= 1) && (paramInt <= 12));
/*      */       
/* 3448 */       int i = Math.getExponent(paramDouble);
/* 3449 */       int j = i == 64513 ? 1 : 0;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3454 */       if (j != 0) {
/* 3455 */         Formatter.access$202(Math.scalb(1.0D, 54));
/* 3456 */         paramDouble *= Formatter.scaleUp;
/*      */         
/*      */ 
/* 3459 */         i = Math.getExponent(paramDouble);
/* 3460 */         assert ((i >= 64514) && (i <= 1023)) : i;
/*      */       }
/*      */       
/*      */ 
/* 3464 */       int k = 1 + paramInt * 4;
/* 3465 */       int m = 53 - k;
/*      */       
/* 3467 */       assert ((m >= 1) && (m < 53));
/*      */       
/* 3469 */       long l1 = Double.doubleToLongBits(paramDouble);
/*      */       
/* 3471 */       long l2 = (l1 & 0x7FFFFFFFFFFFFFFF) >> m;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3476 */       long l3 = l1 & (-1L << m ^ 0xFFFFFFFFFFFFFFFF);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3483 */       int n = (l2 & 1L) == 0L ? 1 : 0;
/* 3484 */       int i1 = (1L << m - 1 & l3) != 0L ? 1 : 0;
/*      */       
/* 3486 */       int i2 = (m > 1) && (((1L << m - 1 ^ 0xFFFFFFFFFFFFFFFF) & l3) != 0L) ? 1 : 0;
/*      */       
/* 3488 */       if (((n != 0) && (i1 != 0) && (i2 != 0)) || ((n == 0) && (i1 != 0))) {
/* 3489 */         l2 += 1L;
/*      */       }
/*      */       
/* 3492 */       long l4 = l1 & 0x8000000000000000;
/* 3493 */       l2 = l4 | l2 << m;
/* 3494 */       double d = Double.longBitsToDouble(l2);
/*      */       
/* 3496 */       if (Double.isInfinite(d))
/*      */       {
/* 3498 */         return "1.0p1024";
/*      */       }
/* 3500 */       String str1 = Double.toHexString(d).substring(2);
/* 3501 */       if (j == 0) {
/* 3502 */         return str1;
/*      */       }
/*      */       
/* 3505 */       int i3 = str1.indexOf('p');
/* 3506 */       if (i3 == -1)
/*      */       {
/* 3508 */         if (!$assertionsDisabled) throw new AssertionError();
/* 3509 */         return null;
/*      */       }
/*      */       
/* 3512 */       String str2 = str1.substring(i3 + 1);
/* 3513 */       int i4 = Integer.parseInt(str2) - 54;
/*      */       
/* 3515 */       return str1.substring(0, i3) + "p" + Integer.toString(i4);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void print(BigDecimal paramBigDecimal, Locale paramLocale)
/*      */       throws IOException
/*      */     {
/* 3523 */       if (this.c == 'a')
/* 3524 */         failConversion(this.c, paramBigDecimal);
/* 3525 */       StringBuilder localStringBuilder = new StringBuilder();
/* 3526 */       boolean bool = paramBigDecimal.signum() == -1;
/* 3527 */       BigDecimal localBigDecimal = paramBigDecimal.abs();
/*      */       
/* 3529 */       leadingSign(localStringBuilder, bool);
/*      */       
/*      */ 
/* 3532 */       print(localStringBuilder, localBigDecimal, paramLocale, this.f, this.c, this.precision, bool);
/*      */       
/*      */ 
/* 3535 */       trailingSign(localStringBuilder, bool);
/*      */       
/*      */ 
/* 3538 */       Formatter.this.a.append(justify(localStringBuilder.toString()));
/*      */     }
/*      */     
/*      */     private void print(StringBuilder paramStringBuilder, BigDecimal paramBigDecimal, Locale paramLocale, Formatter.Flags paramFlags, char paramChar, int paramInt, boolean paramBoolean) throws IOException {
/*      */       int i;
/*      */       int j;
/*      */       int k;
/*      */       int i1;
/* 3546 */       if (paramChar == 'e')
/*      */       {
/* 3548 */         i = paramInt == -1 ? 6 : paramInt;
/* 3549 */         j = paramBigDecimal.scale();
/* 3550 */         k = paramBigDecimal.precision();
/* 3551 */         int m = 0;
/*      */         
/*      */ 
/* 3554 */         if (i > k - 1) {
/* 3555 */           i1 = k;
/* 3556 */           m = i - (k - 1);
/*      */         } else {
/* 3558 */           i1 = i + 1;
/*      */         }
/*      */         
/* 3561 */         MathContext localMathContext = new MathContext(i1);
/*      */         
/* 3563 */         BigDecimal localBigDecimal2 = new BigDecimal(paramBigDecimal.unscaledValue(), j, localMathContext);
/*      */         
/*      */ 
/* 3566 */         BigDecimalLayout localBigDecimalLayout = new BigDecimalLayout(localBigDecimal2.unscaledValue(), localBigDecimal2.scale(), Formatter.BigDecimalLayoutForm.SCIENTIFIC);
/*      */         
/*      */ 
/* 3569 */         char[] arrayOfChar2 = localBigDecimalLayout.mantissa();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3576 */         if (((k == 1) || (!localBigDecimalLayout.hasDot())) && ((m > 0) || 
/* 3577 */           (paramFlags.contains(Formatter.Flags.ALTERNATE)))) {
/* 3578 */           arrayOfChar2 = addDot(arrayOfChar2);
/*      */         }
/*      */         
/*      */ 
/* 3582 */         arrayOfChar2 = trailingZeros(arrayOfChar2, m);
/*      */         
/* 3584 */         char[] arrayOfChar3 = localBigDecimalLayout.exponent();
/* 3585 */         int i2 = this.width;
/* 3586 */         if (this.width != -1)
/* 3587 */           i2 = adjustWidth(this.width - arrayOfChar3.length - 1, paramFlags, paramBoolean);
/* 3588 */         localizedMagnitude(paramStringBuilder, arrayOfChar2, paramFlags, i2, paramLocale);
/*      */         
/* 3590 */         paramStringBuilder.append(paramFlags.contains(Formatter.Flags.UPPERCASE) ? 'E' : 'e');
/*      */         
/* 3592 */         Formatter.Flags localFlags = paramFlags.dup().remove(Formatter.Flags.GROUP);
/* 3593 */         int i3 = arrayOfChar3[0];
/* 3594 */         assert ((i3 == 43) || (i3 == 45));
/* 3595 */         paramStringBuilder.append(arrayOfChar3[0]);
/*      */         
/* 3597 */         char[] arrayOfChar4 = new char[arrayOfChar3.length - 1];
/* 3598 */         System.arraycopy(arrayOfChar3, 1, arrayOfChar4, 0, arrayOfChar3.length - 1);
/* 3599 */         paramStringBuilder.append(localizedMagnitude(null, arrayOfChar4, localFlags, -1, paramLocale)); } else { Object localObject;
/* 3600 */         if (paramChar == 'f')
/*      */         {
/* 3602 */           i = paramInt == -1 ? 6 : paramInt;
/* 3603 */           j = paramBigDecimal.scale();
/*      */           
/* 3605 */           if (j > i)
/*      */           {
/* 3607 */             k = paramBigDecimal.precision();
/* 3608 */             if (k <= j)
/*      */             {
/* 3610 */               paramBigDecimal = paramBigDecimal.setScale(i, RoundingMode.HALF_UP);
/*      */             } else {
/* 3612 */               k -= j - i;
/* 3613 */               paramBigDecimal = new BigDecimal(paramBigDecimal.unscaledValue(), j, new MathContext(k));
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 3620 */           localObject = new BigDecimalLayout(paramBigDecimal.unscaledValue(), paramBigDecimal.scale(), Formatter.BigDecimalLayoutForm.DECIMAL_FLOAT);
/*      */           
/*      */ 
/* 3623 */           char[] arrayOfChar1 = ((BigDecimalLayout)localObject).mantissa();
/* 3624 */           i1 = ((BigDecimalLayout)localObject).scale() < i ? i - ((BigDecimalLayout)localObject).scale() : 0;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3631 */           if ((((BigDecimalLayout)localObject).scale() == 0) && ((paramFlags.contains(Formatter.Flags.ALTERNATE)) || (i1 > 0))) {
/* 3632 */             arrayOfChar1 = addDot(((BigDecimalLayout)localObject).mantissa());
/*      */           }
/*      */           
/*      */ 
/* 3636 */           arrayOfChar1 = trailingZeros(arrayOfChar1, i1);
/*      */           
/* 3638 */           localizedMagnitude(paramStringBuilder, arrayOfChar1, paramFlags, adjustWidth(this.width, paramFlags, paramBoolean), paramLocale);
/* 3639 */         } else if (paramChar == 'g') {
/* 3640 */           i = paramInt;
/* 3641 */           if (paramInt == -1) {
/* 3642 */             i = 6;
/* 3643 */           } else if (paramInt == 0) {
/* 3644 */             i = 1;
/*      */           }
/* 3646 */           BigDecimal localBigDecimal1 = BigDecimal.valueOf(1L, 4);
/* 3647 */           localObject = BigDecimal.valueOf(1L, -i);
/* 3648 */           if ((paramBigDecimal.equals(BigDecimal.ZERO)) || (
/* 3649 */             (paramBigDecimal.compareTo(localBigDecimal1) != -1) && 
/* 3650 */             (paramBigDecimal.compareTo((BigDecimal)localObject) == -1)))
/*      */           {
/*      */ 
/* 3653 */             int n = -paramBigDecimal.scale() + (paramBigDecimal.unscaledValue().toString().length() - 1);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3665 */             i = i - n - 1;
/*      */             
/* 3667 */             print(paramStringBuilder, paramBigDecimal, paramLocale, paramFlags, 'f', i, paramBoolean);
/*      */           }
/*      */           else {
/* 3670 */             print(paramStringBuilder, paramBigDecimal, paramLocale, paramFlags, 'e', i - 1, paramBoolean);
/*      */           }
/* 3672 */         } else if (paramChar == 'a')
/*      */         {
/*      */ 
/* 3675 */           if (!$assertionsDisabled) throw new AssertionError();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private class BigDecimalLayout { private StringBuilder mant;
/*      */       private StringBuilder exp;
/* 3682 */       private boolean dot = false;
/*      */       private int scale;
/*      */       
/*      */       public BigDecimalLayout(BigInteger paramBigInteger, int paramInt, Formatter.BigDecimalLayoutForm paramBigDecimalLayoutForm) {
/* 3686 */         layout(paramBigInteger, paramInt, paramBigDecimalLayoutForm);
/*      */       }
/*      */       
/*      */       public boolean hasDot() {
/* 3690 */         return this.dot;
/*      */       }
/*      */       
/*      */       public int scale() {
/* 3694 */         return this.scale;
/*      */       }
/*      */       
/*      */       public char[] layoutChars()
/*      */       {
/* 3699 */         StringBuilder localStringBuilder = new StringBuilder(this.mant);
/* 3700 */         if (this.exp != null) {
/* 3701 */           localStringBuilder.append('E');
/* 3702 */           localStringBuilder.append(this.exp);
/*      */         }
/* 3704 */         return toCharArray(localStringBuilder);
/*      */       }
/*      */       
/*      */       public char[] mantissa() {
/* 3708 */         return toCharArray(this.mant);
/*      */       }
/*      */       
/*      */ 
/*      */       public char[] exponent()
/*      */       {
/* 3714 */         return toCharArray(this.exp);
/*      */       }
/*      */       
/*      */       private char[] toCharArray(StringBuilder paramStringBuilder) {
/* 3718 */         if (paramStringBuilder == null)
/* 3719 */           return null;
/* 3720 */         char[] arrayOfChar = new char[paramStringBuilder.length()];
/* 3721 */         paramStringBuilder.getChars(0, arrayOfChar.length, arrayOfChar, 0);
/* 3722 */         return arrayOfChar;
/*      */       }
/*      */       
/*      */       private void layout(BigInteger paramBigInteger, int paramInt, Formatter.BigDecimalLayoutForm paramBigDecimalLayoutForm) {
/* 3726 */         char[] arrayOfChar = paramBigInteger.toString().toCharArray();
/* 3727 */         this.scale = paramInt;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3734 */         this.mant = new StringBuilder(arrayOfChar.length + 14);
/*      */         
/* 3736 */         if (paramInt == 0) {
/* 3737 */           int i = arrayOfChar.length;
/* 3738 */           if (i > 1) {
/* 3739 */             this.mant.append(arrayOfChar[0]);
/* 3740 */             if (paramBigDecimalLayoutForm == Formatter.BigDecimalLayoutForm.SCIENTIFIC) {
/* 3741 */               this.mant.append('.');
/* 3742 */               this.dot = true;
/* 3743 */               this.mant.append(arrayOfChar, 1, i - 1);
/* 3744 */               this.exp = new StringBuilder("+");
/* 3745 */               if (i < 10) {
/* 3746 */                 this.exp.append("0").append(i - 1);
/*      */               } else
/* 3748 */                 this.exp.append(i - 1);
/*      */             } else {
/* 3750 */               this.mant.append(arrayOfChar, 1, i - 1);
/*      */             }
/*      */           } else {
/* 3753 */             this.mant.append(arrayOfChar);
/* 3754 */             if (paramBigDecimalLayoutForm == Formatter.BigDecimalLayoutForm.SCIENTIFIC)
/* 3755 */               this.exp = new StringBuilder("+00");
/*      */           }
/* 3757 */           return;
/*      */         }
/* 3759 */         long l1 = -paramInt + (arrayOfChar.length - 1);
/* 3760 */         if (paramBigDecimalLayoutForm == Formatter.BigDecimalLayoutForm.DECIMAL_FLOAT)
/*      */         {
/* 3762 */           int j = paramInt - arrayOfChar.length;
/* 3763 */           if (j >= 0)
/*      */           {
/* 3765 */             this.mant.append("0.");
/* 3766 */             this.dot = true;
/* 3767 */             for (; j > 0; j--) this.mant.append('0');
/* 3768 */             this.mant.append(arrayOfChar);
/*      */           }
/* 3770 */           else if (-j < arrayOfChar.length)
/*      */           {
/* 3772 */             this.mant.append(arrayOfChar, 0, -j);
/* 3773 */             this.mant.append('.');
/* 3774 */             this.dot = true;
/* 3775 */             this.mant.append(arrayOfChar, -j, paramInt);
/*      */           }
/*      */           else {
/* 3778 */             this.mant.append(arrayOfChar, 0, arrayOfChar.length);
/* 3779 */             for (int k = 0; k < -paramInt; k++)
/* 3780 */               this.mant.append('0');
/* 3781 */             this.scale = 0;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3786 */           this.mant.append(arrayOfChar[0]);
/* 3787 */           if (arrayOfChar.length > 1) {
/* 3788 */             this.mant.append('.');
/* 3789 */             this.dot = true;
/* 3790 */             this.mant.append(arrayOfChar, 1, arrayOfChar.length - 1);
/*      */           }
/* 3792 */           this.exp = new StringBuilder();
/* 3793 */           if (l1 != 0L) {
/* 3794 */             long l2 = Math.abs(l1);
/*      */             
/* 3796 */             this.exp.append(l1 < 0L ? '-' : '+');
/* 3797 */             if (l2 < 10L)
/* 3798 */               this.exp.append('0');
/* 3799 */             this.exp.append(l2);
/*      */           } else {
/* 3801 */             this.exp.append("+00");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private int adjustWidth(int paramInt, Formatter.Flags paramFlags, boolean paramBoolean) {
/* 3808 */       int i = paramInt;
/* 3809 */       if ((i != -1) && (paramBoolean) && (paramFlags.contains(Formatter.Flags.PARENTHESES)))
/* 3810 */         i--;
/* 3811 */       return i;
/*      */     }
/*      */     
/*      */     private char[] addDot(char[] paramArrayOfChar)
/*      */     {
/* 3816 */       char[] arrayOfChar = paramArrayOfChar;
/* 3817 */       arrayOfChar = new char[paramArrayOfChar.length + 1];
/* 3818 */       System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, paramArrayOfChar.length);
/* 3819 */       arrayOfChar[(arrayOfChar.length - 1)] = '.';
/* 3820 */       return arrayOfChar;
/*      */     }
/*      */     
/*      */ 
/*      */     private char[] trailingZeros(char[] paramArrayOfChar, int paramInt)
/*      */     {
/* 3826 */       char[] arrayOfChar = paramArrayOfChar;
/* 3827 */       if (paramInt > 0) {
/* 3828 */         arrayOfChar = new char[paramArrayOfChar.length + paramInt];
/* 3829 */         System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, paramArrayOfChar.length);
/* 3830 */         for (int i = paramArrayOfChar.length; i < arrayOfChar.length; i++)
/* 3831 */           arrayOfChar[i] = '0';
/*      */       }
/* 3833 */       return arrayOfChar;
/*      */     }
/*      */     
/*      */     private void print(Calendar paramCalendar, char paramChar, Locale paramLocale) throws IOException
/*      */     {
/* 3838 */       StringBuilder localStringBuilder = new StringBuilder();
/* 3839 */       print(localStringBuilder, paramCalendar, paramChar, paramLocale);
/*      */       
/*      */ 
/* 3842 */       String str = justify(localStringBuilder.toString());
/* 3843 */       if (this.f.contains(Formatter.Flags.UPPERCASE)) {
/* 3844 */         str = str.toUpperCase();
/*      */       }
/* 3846 */       Formatter.this.a.append(str);
/*      */     }
/*      */     
/*      */ 
/*      */     private Appendable print(StringBuilder paramStringBuilder, Calendar paramCalendar, char paramChar, Locale paramLocale)
/*      */       throws IOException
/*      */     {
/* 3853 */       if (paramStringBuilder == null)
/* 3854 */         paramStringBuilder = new StringBuilder();
/* 3855 */       int i; Object localObject1; Formatter.Flags localFlags1; int j; int k; Locale localLocale; Object localObject3; Object localObject2; char c1; switch (paramChar) {
/*      */       case 'H': 
/*      */       case 'I': 
/*      */       case 'k': 
/*      */       case 'l': 
/* 3860 */         i = paramCalendar.get(11);
/* 3861 */         if ((paramChar == 'I') || (paramChar == 'l'))
/* 3862 */           i = (i == 0) || (i == 12) ? 12 : i % 12;
/* 3863 */         localObject1 = (paramChar == 'H') || (paramChar == 'I') ? Formatter.Flags.ZERO_PAD : Formatter.Flags.NONE;
/*      */         
/*      */ 
/*      */ 
/* 3867 */         paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 2, paramLocale));
/* 3868 */         break;
/*      */       
/*      */       case 'M': 
/* 3871 */         i = paramCalendar.get(12);
/* 3872 */         localObject1 = Formatter.Flags.ZERO_PAD;
/* 3873 */         paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 2, paramLocale));
/* 3874 */         break;
/*      */       
/*      */       case 'N': 
/* 3877 */         i = paramCalendar.get(14) * 1000000;
/* 3878 */         localObject1 = Formatter.Flags.ZERO_PAD;
/* 3879 */         paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 9, paramLocale));
/* 3880 */         break;
/*      */       
/*      */       case 'L': 
/* 3883 */         i = paramCalendar.get(14);
/* 3884 */         localObject1 = Formatter.Flags.ZERO_PAD;
/* 3885 */         paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 3, paramLocale));
/* 3886 */         break;
/*      */       
/*      */       case 'Q': 
/* 3889 */         long l1 = paramCalendar.getTimeInMillis();
/* 3890 */         localFlags1 = Formatter.Flags.NONE;
/* 3891 */         paramStringBuilder.append(localizedMagnitude(null, l1, localFlags1, this.width, paramLocale));
/* 3892 */         break;
/*      */       
/*      */ 
/*      */       case 'p': 
/* 3896 */         String[] arrayOfString = { "AM", "PM" };
/* 3897 */         if ((paramLocale != null) && (paramLocale != Locale.US)) {
/* 3898 */           localObject1 = DateFormatSymbols.getInstance(paramLocale);
/* 3899 */           arrayOfString = ((DateFormatSymbols)localObject1).getAmPmStrings();
/*      */         }
/* 3901 */         localObject1 = arrayOfString[paramCalendar.get(9)];
/* 3902 */         paramStringBuilder.append(((String)localObject1).toLowerCase(paramLocale != null ? paramLocale : Locale.US));
/* 3903 */         break;
/*      */       
/*      */       case 's': 
/* 3906 */         long l2 = paramCalendar.getTimeInMillis() / 1000L;
/* 3907 */         localFlags1 = Formatter.Flags.NONE;
/* 3908 */         paramStringBuilder.append(localizedMagnitude(null, l2, localFlags1, this.width, paramLocale));
/* 3909 */         break;
/*      */       
/*      */       case 'S': 
/* 3912 */         j = paramCalendar.get(13);
/* 3913 */         localObject1 = Formatter.Flags.ZERO_PAD;
/* 3914 */         paramStringBuilder.append(localizedMagnitude(null, j, (Formatter.Flags)localObject1, 2, paramLocale));
/* 3915 */         break;
/*      */       
/*      */       case 'z': 
/* 3918 */         j = paramCalendar.get(15) + paramCalendar.get(16);
/* 3919 */         int m = j < 0 ? 1 : 0;
/* 3920 */         paramStringBuilder.append(m != 0 ? '-' : '+');
/* 3921 */         if (m != 0)
/* 3922 */           j = -j;
/* 3923 */         int i1 = j / 60000;
/*      */         
/* 3925 */         int i2 = i1 / 60 * 100 + i1 % 60;
/* 3926 */         Formatter.Flags localFlags2 = Formatter.Flags.ZERO_PAD;
/*      */         
/* 3928 */         paramStringBuilder.append(localizedMagnitude(null, i2, localFlags2, 4, paramLocale));
/* 3929 */         break;
/*      */       
/*      */       case 'Z': 
/* 3932 */         TimeZone localTimeZone = paramCalendar.getTimeZone();
/* 3933 */         paramStringBuilder.append(localTimeZone.getDisplayName(paramCalendar.get(16) != 0, 0, paramLocale == null ? Locale.US : paramLocale));
/*      */         
/*      */ 
/* 3936 */         break;
/*      */       
/*      */ 
/*      */ 
/*      */       case 'A': 
/*      */       case 'a': 
/* 3942 */         k = paramCalendar.get(7);
/* 3943 */         localLocale = paramLocale == null ? Locale.US : paramLocale;
/* 3944 */         localObject3 = DateFormatSymbols.getInstance(localLocale);
/* 3945 */         if (paramChar == 'A') {
/* 3946 */           paramStringBuilder.append(localObject3.getWeekdays()[k]);
/*      */         } else
/* 3948 */           paramStringBuilder.append(localObject3.getShortWeekdays()[k]);
/* 3949 */         break;
/*      */       
/*      */       case 'B': 
/*      */       case 'b': 
/*      */       case 'h': 
/* 3954 */         k = paramCalendar.get(2);
/* 3955 */         localLocale = paramLocale == null ? Locale.US : paramLocale;
/* 3956 */         localObject3 = DateFormatSymbols.getInstance(localLocale);
/* 3957 */         if (paramChar == 'B') {
/* 3958 */           paramStringBuilder.append(localObject3.getMonths()[k]);
/*      */         } else
/* 3960 */           paramStringBuilder.append(localObject3.getShortMonths()[k]);
/* 3961 */         break;
/*      */       
/*      */       case 'C': 
/*      */       case 'Y': 
/*      */       case 'y': 
/* 3966 */         k = paramCalendar.get(1);
/* 3967 */         int n = 2;
/* 3968 */         switch (paramChar) {
/*      */         case 'C': 
/* 3970 */           k /= 100;
/* 3971 */           break;
/*      */         case 'y': 
/* 3973 */           k %= 100;
/* 3974 */           break;
/*      */         case 'Y': 
/* 3976 */           n = 4;
/*      */         }
/*      */         
/* 3979 */         localObject3 = Formatter.Flags.ZERO_PAD;
/* 3980 */         paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject3, n, paramLocale));
/* 3981 */         break;
/*      */       
/*      */       case 'd': 
/*      */       case 'e': 
/* 3985 */         k = paramCalendar.get(5);
/* 3986 */         localObject2 = paramChar == 'd' ? Formatter.Flags.ZERO_PAD : Formatter.Flags.NONE;
/*      */         
/*      */ 
/* 3989 */         paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject2, 2, paramLocale));
/* 3990 */         break;
/*      */       
/*      */       case 'j': 
/* 3993 */         k = paramCalendar.get(6);
/* 3994 */         localObject2 = Formatter.Flags.ZERO_PAD;
/* 3995 */         paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject2, 3, paramLocale));
/* 3996 */         break;
/*      */       
/*      */       case 'm': 
/* 3999 */         k = paramCalendar.get(2) + 1;
/* 4000 */         localObject2 = Formatter.Flags.ZERO_PAD;
/* 4001 */         paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject2, 2, paramLocale));
/* 4002 */         break;
/*      */       
/*      */ 
/*      */ 
/*      */       case 'R': 
/*      */       case 'T': 
/* 4008 */         k = 58;
/* 4009 */         print(paramStringBuilder, paramCalendar, 'H', paramLocale).append(k);
/* 4010 */         print(paramStringBuilder, paramCalendar, 'M', paramLocale);
/* 4011 */         if (paramChar == 'T') {
/* 4012 */           paramStringBuilder.append(k);
/* 4013 */           print(paramStringBuilder, paramCalendar, 'S', paramLocale);
/*      */         }
/*      */         
/*      */         break;
/*      */       case 'r': 
/* 4018 */         c1 = ':';
/* 4019 */         print(paramStringBuilder, paramCalendar, 'I', paramLocale).append(c1);
/* 4020 */         print(paramStringBuilder, paramCalendar, 'M', paramLocale).append(c1);
/* 4021 */         print(paramStringBuilder, paramCalendar, 'S', paramLocale).append(' ');
/*      */         
/* 4023 */         localObject2 = new StringBuilder();
/* 4024 */         print((StringBuilder)localObject2, paramCalendar, 'p', paramLocale);
/* 4025 */         paramStringBuilder.append(((StringBuilder)localObject2).toString().toUpperCase(paramLocale != null ? paramLocale : Locale.US));
/* 4026 */         break;
/*      */       
/*      */       case 'c': 
/* 4029 */         c1 = ' ';
/* 4030 */         print(paramStringBuilder, paramCalendar, 'a', paramLocale).append(c1);
/* 4031 */         print(paramStringBuilder, paramCalendar, 'b', paramLocale).append(c1);
/* 4032 */         print(paramStringBuilder, paramCalendar, 'd', paramLocale).append(c1);
/* 4033 */         print(paramStringBuilder, paramCalendar, 'T', paramLocale).append(c1);
/* 4034 */         print(paramStringBuilder, paramCalendar, 'Z', paramLocale).append(c1);
/* 4035 */         print(paramStringBuilder, paramCalendar, 'Y', paramLocale);
/* 4036 */         break;
/*      */       
/*      */       case 'D': 
/* 4039 */         c1 = '/';
/* 4040 */         print(paramStringBuilder, paramCalendar, 'm', paramLocale).append(c1);
/* 4041 */         print(paramStringBuilder, paramCalendar, 'd', paramLocale).append(c1);
/* 4042 */         print(paramStringBuilder, paramCalendar, 'y', paramLocale);
/* 4043 */         break;
/*      */       
/*      */       case 'F': 
/* 4046 */         c1 = '-';
/* 4047 */         print(paramStringBuilder, paramCalendar, 'Y', paramLocale).append(c1);
/* 4048 */         print(paramStringBuilder, paramCalendar, 'm', paramLocale).append(c1);
/* 4049 */         print(paramStringBuilder, paramCalendar, 'd', paramLocale);
/* 4050 */         break;
/*      */       case 'E': case 'G': case 'J': case 'K': case 'O': case 'P': case 'U': case 'V': case 'W': case 'X': case '[': case '\\': case ']': case '^': 
/*      */       case '_': case '`': case 'f': case 'g': case 'i': case 'n': case 'o': case 'q': case 't': case 'u': case 'v': case 'w': case 'x': default: 
/* 4053 */         if (!$assertionsDisabled) throw new AssertionError();
/*      */         break; }
/* 4055 */       return paramStringBuilder;
/*      */     }
/*      */     
/*      */     private void print(TemporalAccessor paramTemporalAccessor, char paramChar, Locale paramLocale) throws IOException {
/* 4059 */       StringBuilder localStringBuilder = new StringBuilder();
/* 4060 */       print(localStringBuilder, paramTemporalAccessor, paramChar, paramLocale);
/*      */       
/* 4062 */       String str = justify(localStringBuilder.toString());
/* 4063 */       if (this.f.contains(Formatter.Flags.UPPERCASE))
/* 4064 */         str = str.toUpperCase();
/* 4065 */       Formatter.this.a.append(str);
/*      */     }
/*      */     
/*      */     private Appendable print(StringBuilder paramStringBuilder, TemporalAccessor paramTemporalAccessor, char paramChar, Locale paramLocale) throws IOException
/*      */     {
/* 4070 */       if (paramStringBuilder == null)
/* 4071 */         paramStringBuilder = new StringBuilder();
/*      */       try { int i;
/* 4073 */         Object localObject1; Formatter.Flags localFlags1; int j; Object localObject2; int k; Object localObject4; Object localObject3; char c1; switch (paramChar) {
/*      */         case 'H': 
/* 4075 */           i = paramTemporalAccessor.get(ChronoField.HOUR_OF_DAY);
/* 4076 */           paramStringBuilder.append(localizedMagnitude(null, i, Formatter.Flags.ZERO_PAD, 2, paramLocale));
/* 4077 */           break;
/*      */         
/*      */         case 'k': 
/* 4080 */           i = paramTemporalAccessor.get(ChronoField.HOUR_OF_DAY);
/* 4081 */           paramStringBuilder.append(localizedMagnitude(null, i, Formatter.Flags.NONE, 2, paramLocale));
/* 4082 */           break;
/*      */         
/*      */         case 'I': 
/* 4085 */           i = paramTemporalAccessor.get(ChronoField.CLOCK_HOUR_OF_AMPM);
/* 4086 */           paramStringBuilder.append(localizedMagnitude(null, i, Formatter.Flags.ZERO_PAD, 2, paramLocale));
/* 4087 */           break;
/*      */         
/*      */         case 'l': 
/* 4090 */           i = paramTemporalAccessor.get(ChronoField.CLOCK_HOUR_OF_AMPM);
/* 4091 */           paramStringBuilder.append(localizedMagnitude(null, i, Formatter.Flags.NONE, 2, paramLocale));
/* 4092 */           break;
/*      */         
/*      */         case 'M': 
/* 4095 */           i = paramTemporalAccessor.get(ChronoField.MINUTE_OF_HOUR);
/* 4096 */           localObject1 = Formatter.Flags.ZERO_PAD;
/* 4097 */           paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 2, paramLocale));
/* 4098 */           break;
/*      */         
/*      */         case 'N': 
/* 4101 */           i = paramTemporalAccessor.get(ChronoField.MILLI_OF_SECOND) * 1000000;
/* 4102 */           localObject1 = Formatter.Flags.ZERO_PAD;
/* 4103 */           paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 9, paramLocale));
/* 4104 */           break;
/*      */         
/*      */         case 'L': 
/* 4107 */           i = paramTemporalAccessor.get(ChronoField.MILLI_OF_SECOND);
/* 4108 */           localObject1 = Formatter.Flags.ZERO_PAD;
/* 4109 */           paramStringBuilder.append(localizedMagnitude(null, i, (Formatter.Flags)localObject1, 3, paramLocale));
/* 4110 */           break;
/*      */         
/*      */ 
/*      */         case 'Q': 
/* 4114 */           long l1 = paramTemporalAccessor.getLong(ChronoField.INSTANT_SECONDS) * 1000L + paramTemporalAccessor.getLong(ChronoField.MILLI_OF_SECOND);
/* 4115 */           localFlags1 = Formatter.Flags.NONE;
/* 4116 */           paramStringBuilder.append(localizedMagnitude(null, l1, localFlags1, this.width, paramLocale));
/* 4117 */           break;
/*      */         
/*      */ 
/*      */         case 'p': 
/* 4121 */           String[] arrayOfString = { "AM", "PM" };
/* 4122 */           if ((paramLocale != null) && (paramLocale != Locale.US)) {
/* 4123 */             localObject1 = DateFormatSymbols.getInstance(paramLocale);
/* 4124 */             arrayOfString = ((DateFormatSymbols)localObject1).getAmPmStrings();
/*      */           }
/* 4126 */           localObject1 = arrayOfString[paramTemporalAccessor.get(ChronoField.AMPM_OF_DAY)];
/* 4127 */           paramStringBuilder.append(((String)localObject1).toLowerCase(paramLocale != null ? paramLocale : Locale.US));
/* 4128 */           break;
/*      */         
/*      */         case 's': 
/* 4131 */           long l2 = paramTemporalAccessor.getLong(ChronoField.INSTANT_SECONDS);
/* 4132 */           localFlags1 = Formatter.Flags.NONE;
/* 4133 */           paramStringBuilder.append(localizedMagnitude(null, l2, localFlags1, this.width, paramLocale));
/* 4134 */           break;
/*      */         
/*      */         case 'S': 
/* 4137 */           j = paramTemporalAccessor.get(ChronoField.SECOND_OF_MINUTE);
/* 4138 */           localObject1 = Formatter.Flags.ZERO_PAD;
/* 4139 */           paramStringBuilder.append(localizedMagnitude(null, j, (Formatter.Flags)localObject1, 2, paramLocale));
/* 4140 */           break;
/*      */         
/*      */         case 'z': 
/* 4143 */           j = paramTemporalAccessor.get(ChronoField.OFFSET_SECONDS);
/* 4144 */           int m = j < 0 ? 1 : 0;
/* 4145 */           paramStringBuilder.append(m != 0 ? '-' : '+');
/* 4146 */           if (m != 0)
/* 4147 */             j = -j;
/* 4148 */           int i1 = j / 60;
/*      */           
/* 4150 */           int i2 = i1 / 60 * 100 + i1 % 60;
/* 4151 */           Formatter.Flags localFlags2 = Formatter.Flags.ZERO_PAD;
/* 4152 */           paramStringBuilder.append(localizedMagnitude(null, i2, localFlags2, 4, paramLocale));
/* 4153 */           break;
/*      */         
/*      */         case 'Z': 
/* 4156 */           ZoneId localZoneId = (ZoneId)paramTemporalAccessor.query(TemporalQueries.zone());
/* 4157 */           if (localZoneId == null) {
/* 4158 */             throw new IllegalFormatConversionException(paramChar, paramTemporalAccessor.getClass());
/*      */           }
/* 4160 */           if ((!(localZoneId instanceof ZoneOffset)) && 
/* 4161 */             (paramTemporalAccessor.isSupported(ChronoField.INSTANT_SECONDS))) {
/* 4162 */             localObject2 = Instant.from(paramTemporalAccessor);
/* 4163 */             paramStringBuilder.append(TimeZone.getTimeZone(localZoneId.getId())
/* 4164 */               .getDisplayName(localZoneId.getRules().isDaylightSavings((Instant)localObject2), 0, paramLocale == null ? Locale.US : paramLocale));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 4169 */             paramStringBuilder.append(localZoneId.getId()); }
/* 4170 */           break;
/*      */         
/*      */ 
/*      */         case 'A': 
/*      */         case 'a': 
/* 4175 */           k = paramTemporalAccessor.get(ChronoField.DAY_OF_WEEK) % 7 + 1;
/* 4176 */           localObject2 = paramLocale == null ? Locale.US : paramLocale;
/* 4177 */           localObject4 = DateFormatSymbols.getInstance((Locale)localObject2);
/* 4178 */           if (paramChar == 'A') {
/* 4179 */             paramStringBuilder.append(localObject4.getWeekdays()[k]);
/*      */           } else
/* 4181 */             paramStringBuilder.append(localObject4.getShortWeekdays()[k]);
/* 4182 */           break;
/*      */         
/*      */         case 'B': 
/*      */         case 'b': 
/*      */         case 'h': 
/* 4187 */           k = paramTemporalAccessor.get(ChronoField.MONTH_OF_YEAR) - 1;
/* 4188 */           localObject2 = paramLocale == null ? Locale.US : paramLocale;
/* 4189 */           localObject4 = DateFormatSymbols.getInstance((Locale)localObject2);
/* 4190 */           if (paramChar == 'B') {
/* 4191 */             paramStringBuilder.append(localObject4.getMonths()[k]);
/*      */           } else
/* 4193 */             paramStringBuilder.append(localObject4.getShortMonths()[k]);
/* 4194 */           break;
/*      */         
/*      */         case 'C': 
/*      */         case 'Y': 
/*      */         case 'y': 
/* 4199 */           k = paramTemporalAccessor.get(ChronoField.YEAR_OF_ERA);
/* 4200 */           int n = 2;
/* 4201 */           switch (paramChar) {
/*      */           case 'C': 
/* 4203 */             k /= 100;
/* 4204 */             break;
/*      */           case 'y': 
/* 4206 */             k %= 100;
/* 4207 */             break;
/*      */           case 'Y': 
/* 4209 */             n = 4;
/*      */           }
/*      */           
/* 4212 */           localObject4 = Formatter.Flags.ZERO_PAD;
/* 4213 */           paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject4, n, paramLocale));
/* 4214 */           break;
/*      */         
/*      */         case 'd': 
/*      */         case 'e': 
/* 4218 */           k = paramTemporalAccessor.get(ChronoField.DAY_OF_MONTH);
/* 4219 */           localObject3 = paramChar == 'd' ? Formatter.Flags.ZERO_PAD : Formatter.Flags.NONE;
/*      */           
/*      */ 
/* 4222 */           paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject3, 2, paramLocale));
/* 4223 */           break;
/*      */         
/*      */         case 'j': 
/* 4226 */           k = paramTemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/* 4227 */           localObject3 = Formatter.Flags.ZERO_PAD;
/* 4228 */           paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject3, 3, paramLocale));
/* 4229 */           break;
/*      */         
/*      */         case 'm': 
/* 4232 */           k = paramTemporalAccessor.get(ChronoField.MONTH_OF_YEAR);
/* 4233 */           localObject3 = Formatter.Flags.ZERO_PAD;
/* 4234 */           paramStringBuilder.append(localizedMagnitude(null, k, (Formatter.Flags)localObject3, 2, paramLocale));
/* 4235 */           break;
/*      */         
/*      */ 
/*      */ 
/*      */         case 'R': 
/*      */         case 'T': 
/* 4241 */           k = 58;
/* 4242 */           print(paramStringBuilder, paramTemporalAccessor, 'H', paramLocale).append(k);
/* 4243 */           print(paramStringBuilder, paramTemporalAccessor, 'M', paramLocale);
/* 4244 */           if (paramChar == 'T') {
/* 4245 */             paramStringBuilder.append(k);
/* 4246 */             print(paramStringBuilder, paramTemporalAccessor, 'S', paramLocale);
/*      */           }
/*      */           
/*      */           break;
/*      */         case 'r': 
/* 4251 */           c1 = ':';
/* 4252 */           print(paramStringBuilder, paramTemporalAccessor, 'I', paramLocale).append(c1);
/* 4253 */           print(paramStringBuilder, paramTemporalAccessor, 'M', paramLocale).append(c1);
/* 4254 */           print(paramStringBuilder, paramTemporalAccessor, 'S', paramLocale).append(' ');
/*      */           
/* 4256 */           localObject3 = new StringBuilder();
/* 4257 */           print((StringBuilder)localObject3, paramTemporalAccessor, 'p', paramLocale);
/* 4258 */           paramStringBuilder.append(((StringBuilder)localObject3).toString().toUpperCase(paramLocale != null ? paramLocale : Locale.US));
/* 4259 */           break;
/*      */         
/*      */         case 'c': 
/* 4262 */           c1 = ' ';
/* 4263 */           print(paramStringBuilder, paramTemporalAccessor, 'a', paramLocale).append(c1);
/* 4264 */           print(paramStringBuilder, paramTemporalAccessor, 'b', paramLocale).append(c1);
/* 4265 */           print(paramStringBuilder, paramTemporalAccessor, 'd', paramLocale).append(c1);
/* 4266 */           print(paramStringBuilder, paramTemporalAccessor, 'T', paramLocale).append(c1);
/* 4267 */           print(paramStringBuilder, paramTemporalAccessor, 'Z', paramLocale).append(c1);
/* 4268 */           print(paramStringBuilder, paramTemporalAccessor, 'Y', paramLocale);
/* 4269 */           break;
/*      */         
/*      */         case 'D': 
/* 4272 */           c1 = '/';
/* 4273 */           print(paramStringBuilder, paramTemporalAccessor, 'm', paramLocale).append(c1);
/* 4274 */           print(paramStringBuilder, paramTemporalAccessor, 'd', paramLocale).append(c1);
/* 4275 */           print(paramStringBuilder, paramTemporalAccessor, 'y', paramLocale);
/* 4276 */           break;
/*      */         
/*      */         case 'F': 
/* 4279 */           c1 = '-';
/* 4280 */           print(paramStringBuilder, paramTemporalAccessor, 'Y', paramLocale).append(c1);
/* 4281 */           print(paramStringBuilder, paramTemporalAccessor, 'm', paramLocale).append(c1);
/* 4282 */           print(paramStringBuilder, paramTemporalAccessor, 'd', paramLocale);
/* 4283 */           break;
/*      */         case 'E': case 'G': case 'J': case 'K': case 'O': case 'P': case 'U': case 'V': case 'W': case 'X': case '[': case '\\': case ']': case '^': 
/*      */         case '_': case '`': case 'f': case 'g': case 'i': case 'n': case 'o': case 'q': case 't': case 'u': case 'v': case 'w': case 'x': default: 
/* 4286 */           if (!$assertionsDisabled) throw new AssertionError();
/*      */           break; }
/*      */       } catch (DateTimeException localDateTimeException) {
/* 4289 */         throw new IllegalFormatConversionException(paramChar, paramTemporalAccessor.getClass());
/*      */       }
/* 4291 */       return paramStringBuilder;
/*      */     }
/*      */     
/*      */ 
/*      */     private void failMismatch(Formatter.Flags paramFlags, char paramChar)
/*      */     {
/* 4297 */       String str = paramFlags.toString();
/* 4298 */       throw new FormatFlagsConversionMismatchException(str, paramChar);
/*      */     }
/*      */     
/*      */     private void failConversion(char paramChar, Object paramObject) {
/* 4302 */       throw new IllegalFormatConversionException(paramChar, paramObject.getClass());
/*      */     }
/*      */     
/*      */     private char getZero(Locale paramLocale) {
/* 4306 */       if ((paramLocale != null) && (!paramLocale.equals(Formatter.this.locale()))) {
/* 4307 */         DecimalFormatSymbols localDecimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 4308 */         return localDecimalFormatSymbols.getZeroDigit();
/*      */       }
/* 4310 */       return Formatter.this.zero;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private StringBuilder localizedMagnitude(StringBuilder paramStringBuilder, long paramLong, Formatter.Flags paramFlags, int paramInt, Locale paramLocale)
/*      */     {
/* 4317 */       char[] arrayOfChar = Long.toString(paramLong, 10).toCharArray();
/* 4318 */       return localizedMagnitude(paramStringBuilder, arrayOfChar, paramFlags, paramInt, paramLocale);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private StringBuilder localizedMagnitude(StringBuilder paramStringBuilder, char[] paramArrayOfChar, Formatter.Flags paramFlags, int paramInt, Locale paramLocale)
/*      */     {
/* 4325 */       if (paramStringBuilder == null)
/* 4326 */         paramStringBuilder = new StringBuilder();
/* 4327 */       int i = paramStringBuilder.length();
/*      */       
/* 4329 */       int j = getZero(paramLocale);
/*      */       
/*      */ 
/* 4332 */       char c1 = '\000';
/* 4333 */       int k = -1;
/* 4334 */       char c2 = '\000';
/*      */       
/* 4336 */       int m = paramArrayOfChar.length;
/* 4337 */       int n = m;
/* 4338 */       for (int i1 = 0; i1 < m; i1++) {
/* 4339 */         if (paramArrayOfChar[i1] == '.') {
/* 4340 */           n = i1;
/* 4341 */           break;
/*      */         }
/*      */       }
/*      */       DecimalFormatSymbols localDecimalFormatSymbols;
/* 4345 */       if (n < m) {
/* 4346 */         if ((paramLocale == null) || (paramLocale.equals(Locale.US))) {
/* 4347 */           c2 = '.';
/*      */         } else {
/* 4349 */           localDecimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 4350 */           c2 = localDecimalFormatSymbols.getDecimalSeparator();
/*      */         }
/*      */       }
/*      */       
/* 4354 */       if (paramFlags.contains(Formatter.Flags.GROUP)) {
/* 4355 */         if ((paramLocale == null) || (paramLocale.equals(Locale.US))) {
/* 4356 */           c1 = ',';
/* 4357 */           k = 3;
/*      */         } else {
/* 4359 */           localDecimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 4360 */           c1 = localDecimalFormatSymbols.getGroupingSeparator();
/* 4361 */           DecimalFormat localDecimalFormat = (DecimalFormat)NumberFormat.getIntegerInstance(paramLocale);
/* 4362 */           k = localDecimalFormat.getGroupingSize();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4367 */       for (int i2 = 0; i2 < m; i2++) {
/* 4368 */         if (i2 == n) {
/* 4369 */           paramStringBuilder.append(c2);
/*      */           
/* 4371 */           c1 = '\000';
/*      */         }
/*      */         else
/*      */         {
/* 4375 */           int i3 = paramArrayOfChar[i2];
/* 4376 */           paramStringBuilder.append((char)(i3 - 48 + j));
/* 4377 */           if ((c1 != 0) && (i2 != n - 1) && ((n - i2) % k == 1)) {
/* 4378 */             paramStringBuilder.append(c1);
/*      */           }
/*      */         }
/*      */       }
/* 4382 */       m = paramStringBuilder.length();
/* 4383 */       if ((paramInt != -1) && (paramFlags.contains(Formatter.Flags.ZERO_PAD))) {
/* 4384 */         for (i2 = 0; i2 < paramInt - m; i2++)
/* 4385 */           paramStringBuilder.insert(i, j);
/*      */       }
/* 4387 */       return paramStringBuilder;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class Flags
/*      */   {
/*      */     private int flags;
/* 4394 */     static final Flags NONE = new Flags(0);
/*      */     
/*      */ 
/* 4397 */     static final Flags LEFT_JUSTIFY = new Flags(1);
/* 4398 */     static final Flags UPPERCASE = new Flags(2);
/* 4399 */     static final Flags ALTERNATE = new Flags(4);
/*      */     
/*      */ 
/* 4402 */     static final Flags PLUS = new Flags(8);
/* 4403 */     static final Flags LEADING_SPACE = new Flags(16);
/* 4404 */     static final Flags ZERO_PAD = new Flags(32);
/* 4405 */     static final Flags GROUP = new Flags(64);
/* 4406 */     static final Flags PARENTHESES = new Flags(128);
/*      */     
/*      */ 
/* 4409 */     static final Flags PREVIOUS = new Flags(256);
/*      */     
/*      */     private Flags(int paramInt) {
/* 4412 */       this.flags = paramInt;
/*      */     }
/*      */     
/*      */     public int valueOf() {
/* 4416 */       return this.flags;
/*      */     }
/*      */     
/*      */     public boolean contains(Flags paramFlags) {
/* 4420 */       return (this.flags & paramFlags.valueOf()) == paramFlags.valueOf();
/*      */     }
/*      */     
/*      */     public Flags dup() {
/* 4424 */       return new Flags(this.flags);
/*      */     }
/*      */     
/*      */     private Flags add(Flags paramFlags) {
/* 4428 */       this.flags |= paramFlags.valueOf();
/* 4429 */       return this;
/*      */     }
/*      */     
/*      */     public Flags remove(Flags paramFlags) {
/* 4433 */       this.flags &= (paramFlags.valueOf() ^ 0xFFFFFFFF);
/* 4434 */       return this;
/*      */     }
/*      */     
/*      */     public static Flags parse(String paramString) {
/* 4438 */       char[] arrayOfChar = paramString.toCharArray();
/* 4439 */       Flags localFlags1 = new Flags(0);
/* 4440 */       for (int i = 0; i < arrayOfChar.length; i++) {
/* 4441 */         Flags localFlags2 = parse(arrayOfChar[i]);
/* 4442 */         if (localFlags1.contains(localFlags2))
/* 4443 */           throw new DuplicateFormatFlagsException(localFlags2.toString());
/* 4444 */         localFlags1.add(localFlags2);
/*      */       }
/* 4446 */       return localFlags1;
/*      */     }
/*      */     
/*      */     private static Flags parse(char paramChar)
/*      */     {
/* 4451 */       switch (paramChar) {
/* 4452 */       case '-':  return LEFT_JUSTIFY;
/* 4453 */       case '#':  return ALTERNATE;
/* 4454 */       case '+':  return PLUS;
/* 4455 */       case ' ':  return LEADING_SPACE;
/* 4456 */       case '0':  return ZERO_PAD;
/* 4457 */       case ',':  return GROUP;
/* 4458 */       case '(':  return PARENTHESES;
/* 4459 */       case '<':  return PREVIOUS;
/*      */       }
/* 4461 */       throw new UnknownFormatFlagsException(String.valueOf(paramChar));
/*      */     }
/*      */     
/*      */ 
/*      */     public static String toString(Flags paramFlags)
/*      */     {
/* 4467 */       return paramFlags.toString();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 4471 */       StringBuilder localStringBuilder = new StringBuilder();
/* 4472 */       if (contains(LEFT_JUSTIFY)) localStringBuilder.append('-');
/* 4473 */       if (contains(UPPERCASE)) localStringBuilder.append('^');
/* 4474 */       if (contains(ALTERNATE)) localStringBuilder.append('#');
/* 4475 */       if (contains(PLUS)) localStringBuilder.append('+');
/* 4476 */       if (contains(LEADING_SPACE)) localStringBuilder.append(' ');
/* 4477 */       if (contains(ZERO_PAD)) localStringBuilder.append('0');
/* 4478 */       if (contains(GROUP)) localStringBuilder.append(',');
/* 4479 */       if (contains(PARENTHESES)) localStringBuilder.append('(');
/* 4480 */       if (contains(PREVIOUS)) localStringBuilder.append('<');
/* 4481 */       return localStringBuilder.toString();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class Conversion
/*      */   {
/*      */     static final char DECIMAL_INTEGER = 'd';
/*      */     
/*      */     static final char OCTAL_INTEGER = 'o';
/*      */     
/*      */     static final char HEXADECIMAL_INTEGER = 'x';
/*      */     
/*      */     static final char HEXADECIMAL_INTEGER_UPPER = 'X';
/*      */     
/*      */     static final char SCIENTIFIC = 'e';
/*      */     
/*      */     static final char SCIENTIFIC_UPPER = 'E';
/*      */     
/*      */     static final char GENERAL = 'g';
/*      */     
/*      */     static final char GENERAL_UPPER = 'G';
/*      */     
/*      */     static final char DECIMAL_FLOAT = 'f';
/*      */     
/*      */     static final char HEXADECIMAL_FLOAT = 'a';
/*      */     
/*      */     static final char HEXADECIMAL_FLOAT_UPPER = 'A';
/*      */     
/*      */     static final char CHARACTER = 'c';
/*      */     
/*      */     static final char CHARACTER_UPPER = 'C';
/*      */     
/*      */     static final char DATE_TIME = 't';
/*      */     
/*      */     static final char DATE_TIME_UPPER = 'T';
/*      */     
/*      */     static final char BOOLEAN = 'b';
/*      */     static final char BOOLEAN_UPPER = 'B';
/*      */     static final char STRING = 's';
/*      */     static final char STRING_UPPER = 'S';
/*      */     static final char HASHCODE = 'h';
/*      */     static final char HASHCODE_UPPER = 'H';
/*      */     static final char LINE_SEPARATOR = 'n';
/*      */     static final char PERCENT_SIGN = '%';
/*      */     
/*      */     static boolean isValid(char paramChar)
/*      */     {
/* 4529 */       return (isGeneral(paramChar)) || (isInteger(paramChar)) || (isFloat(paramChar)) || (isText(paramChar)) || (paramChar == 't') || (isCharacter(paramChar));
/*      */     }
/*      */     
/*      */     static boolean isGeneral(char paramChar)
/*      */     {
/* 4534 */       switch (paramChar) {
/*      */       case 'B': 
/*      */       case 'H': 
/*      */       case 'S': 
/*      */       case 'b': 
/*      */       case 'h': 
/*      */       case 's': 
/* 4541 */         return true;
/*      */       }
/* 4543 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     static boolean isCharacter(char paramChar)
/*      */     {
/* 4549 */       switch (paramChar) {
/*      */       case 'C': 
/*      */       case 'c': 
/* 4552 */         return true;
/*      */       }
/* 4554 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     static boolean isInteger(char paramChar)
/*      */     {
/* 4560 */       switch (paramChar) {
/*      */       case 'X': 
/*      */       case 'd': 
/*      */       case 'o': 
/*      */       case 'x': 
/* 4565 */         return true;
/*      */       }
/* 4567 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     static boolean isFloat(char paramChar)
/*      */     {
/* 4573 */       switch (paramChar) {
/*      */       case 'A': 
/*      */       case 'E': 
/*      */       case 'G': 
/*      */       case 'a': 
/*      */       case 'e': 
/*      */       case 'f': 
/*      */       case 'g': 
/* 4581 */         return true;
/*      */       }
/* 4583 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     static boolean isText(char paramChar)
/*      */     {
/* 4589 */       switch (paramChar) {
/*      */       case '%': 
/*      */       case 'n': 
/* 4592 */         return true;
/*      */       }
/* 4594 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class DateTime
/*      */   {
/*      */     static final char HOUR_OF_DAY_0 = 'H';
/*      */     
/*      */     static final char HOUR_0 = 'I';
/*      */     
/*      */     static final char HOUR_OF_DAY = 'k';
/*      */     
/*      */     static final char HOUR = 'l';
/*      */     
/*      */     static final char MINUTE = 'M';
/*      */     
/*      */     static final char NANOSECOND = 'N';
/*      */     
/*      */     static final char MILLISECOND = 'L';
/*      */     
/*      */     static final char MILLISECOND_SINCE_EPOCH = 'Q';
/*      */     
/*      */     static final char AM_PM = 'p';
/*      */     
/*      */     static final char SECONDS_SINCE_EPOCH = 's';
/*      */     
/*      */     static final char SECOND = 'S';
/*      */     
/*      */     static final char TIME = 'T';
/*      */     
/*      */     static final char ZONE_NUMERIC = 'z';
/*      */     static final char ZONE = 'Z';
/*      */     static final char NAME_OF_DAY_ABBREV = 'a';
/*      */     static final char NAME_OF_DAY = 'A';
/*      */     static final char NAME_OF_MONTH_ABBREV = 'b';
/*      */     static final char NAME_OF_MONTH = 'B';
/*      */     static final char CENTURY = 'C';
/*      */     static final char DAY_OF_MONTH_0 = 'd';
/*      */     static final char DAY_OF_MONTH = 'e';
/*      */     static final char NAME_OF_MONTH_ABBREV_X = 'h';
/*      */     static final char DAY_OF_YEAR = 'j';
/*      */     static final char MONTH = 'm';
/*      */     static final char YEAR_2 = 'y';
/*      */     static final char YEAR_4 = 'Y';
/*      */     static final char TIME_12_HOUR = 'r';
/*      */     static final char TIME_24_HOUR = 'R';
/*      */     static final char DATE_TIME = 'c';
/*      */     static final char DATE = 'D';
/*      */     static final char ISO_STANDARD_DATE = 'F';
/*      */     
/*      */     static boolean isValid(char paramChar)
/*      */     {
/* 4647 */       switch (paramChar)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       case 'A': 
/*      */       case 'B': 
/*      */       case 'C': 
/*      */       case 'D': 
/*      */       case 'F': 
/*      */       case 'H': 
/*      */       case 'I': 
/*      */       case 'L': 
/*      */       case 'M': 
/*      */       case 'N': 
/*      */       case 'Q': 
/*      */       case 'R': 
/*      */       case 'S': 
/*      */       case 'T': 
/*      */       case 'Y': 
/*      */       case 'Z': 
/*      */       case 'a': 
/*      */       case 'b': 
/*      */       case 'c': 
/*      */       case 'd': 
/*      */       case 'e': 
/*      */       case 'h': 
/*      */       case 'j': 
/*      */       case 'k': 
/*      */       case 'l': 
/*      */       case 'm': 
/*      */       case 'p': 
/*      */       case 'r': 
/*      */       case 's': 
/*      */       case 'y': 
/*      */       case 'z': 
/* 4692 */         return true; }
/*      */       
/* 4694 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Formatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */