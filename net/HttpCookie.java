/*      */ package java.net;
/*      */ 
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import sun.misc.JavaNetHttpCookieAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class HttpCookie
/*      */   implements Cloneable
/*      */ {
/*      */   private final String name;
/*      */   private String value;
/*      */   private String comment;
/*      */   private String commentURL;
/*      */   private boolean toDiscard;
/*      */   private String domain;
/*   70 */   private long maxAge = -1L;
/*      */   private String path;
/*      */   private String portlist;
/*      */   private boolean secure;
/*      */   private boolean httpOnly;
/*   75 */   private int version = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   private final String header;
/*      */   
/*      */ 
/*      */ 
/*      */   private final long whenCreated;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final long MAX_AGE_UNSPECIFIED = -1L;
/*      */   
/*      */ 
/*      */ 
/*   91 */   private static final String[] COOKIE_DATE_FORMATS = { "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'", "EEE',' dd MMM yyyy HH:mm:ss 'GMT'", "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", "EEE',' dd-MMM-yy HH:mm:ss 'GMT'", "EEE',' dd MMM yy HH:mm:ss 'GMT'", "EEE MMM dd yy HH:mm:ss 'GMT'Z" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String SET_COOKIE = "set-cookie:";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String SET_COOKIE2 = "set-cookie2:";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String tspecials = ",; ";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpCookie(String paramString1, String paramString2)
/*      */   {
/*  139 */     this(paramString1, paramString2, null);
/*      */   }
/*      */   
/*      */   private HttpCookie(String paramString1, String paramString2, String paramString3) {
/*  143 */     paramString1 = paramString1.trim();
/*  144 */     if ((paramString1.length() == 0) || (!isToken(paramString1)) || (paramString1.charAt(0) == '$')) {
/*  145 */       throw new IllegalArgumentException("Illegal cookie name");
/*      */     }
/*      */     
/*  148 */     this.name = paramString1;
/*  149 */     this.value = paramString2;
/*  150 */     this.toDiscard = false;
/*  151 */     this.secure = false;
/*      */     
/*  153 */     this.whenCreated = System.currentTimeMillis();
/*  154 */     this.portlist = null;
/*  155 */     this.header = paramString3;
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
/*      */   public static List<HttpCookie> parse(String paramString)
/*      */   {
/*  178 */     return parse(paramString, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static List<HttpCookie> parse(String paramString, boolean paramBoolean)
/*      */   {
/*  187 */     int i = guessCookieVersion(paramString);
/*      */     
/*      */ 
/*  190 */     if (startsWithIgnoreCase(paramString, "set-cookie2:")) {
/*  191 */       paramString = paramString.substring("set-cookie2:".length());
/*  192 */     } else if (startsWithIgnoreCase(paramString, "set-cookie:")) {
/*  193 */       paramString = paramString.substring("set-cookie:".length());
/*      */     }
/*      */     
/*  196 */     ArrayList localArrayList = new ArrayList();
/*      */     
/*      */     Object localObject;
/*      */     
/*  200 */     if (i == 0)
/*      */     {
/*  202 */       localObject = parseInternal(paramString, paramBoolean);
/*  203 */       ((HttpCookie)localObject).setVersion(0);
/*  204 */       localArrayList.add(localObject);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  209 */       localObject = splitMultiCookies(paramString);
/*  210 */       for (String str : (List)localObject) {
/*  211 */         HttpCookie localHttpCookie = parseInternal(str, paramBoolean);
/*  212 */         localHttpCookie.setVersion(1);
/*  213 */         localArrayList.add(localHttpCookie);
/*      */       }
/*      */     }
/*      */     
/*  217 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasExpired()
/*      */   {
/*  229 */     if (this.maxAge == 0L) { return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  234 */     if (this.maxAge == -1L) { return false;
/*      */     }
/*  236 */     long l = (System.currentTimeMillis() - this.whenCreated) / 1000L;
/*  237 */     if (l > this.maxAge) {
/*  238 */       return true;
/*      */     }
/*  240 */     return false;
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
/*      */   public void setComment(String paramString)
/*      */   {
/*  254 */     this.comment = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getComment()
/*      */   {
/*  266 */     return this.comment;
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
/*      */   public void setCommentURL(String paramString)
/*      */   {
/*  280 */     this.commentURL = paramString;
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
/*      */   public String getCommentURL()
/*      */   {
/*  293 */     return this.commentURL;
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
/*      */   public void setDiscard(boolean paramBoolean)
/*      */   {
/*  306 */     this.toDiscard = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getDiscard()
/*      */   {
/*  317 */     return this.toDiscard;
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
/*      */   public void setPortlist(String paramString)
/*      */   {
/*  331 */     this.portlist = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPortlist()
/*      */   {
/*  342 */     return this.portlist;
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
/*      */   public void setDomain(String paramString)
/*      */   {
/*  362 */     if (paramString != null) {
/*  363 */       this.domain = paramString.toLowerCase();
/*      */     } else {
/*  365 */       this.domain = paramString;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDomain()
/*      */   {
/*  377 */     return this.domain;
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
/*      */   public void setMaxAge(long paramLong)
/*      */   {
/*  400 */     this.maxAge = paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getMaxAge()
/*      */   {
/*  412 */     return this.maxAge;
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
/*      */   public void setPath(String paramString)
/*      */   {
/*  434 */     this.path = paramString;
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
/*      */   public String getPath()
/*      */   {
/*  447 */     return this.path;
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
/*      */   public void setSecure(boolean paramBoolean)
/*      */   {
/*  464 */     this.secure = paramBoolean;
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
/*      */   public boolean getSecure()
/*      */   {
/*  478 */     return this.secure;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  488 */     return this.name;
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
/*      */   public void setValue(String paramString)
/*      */   {
/*  506 */     this.value = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getValue()
/*      */   {
/*  517 */     return this.value;
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
/*      */   public int getVersion()
/*      */   {
/*  532 */     return this.version;
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
/*      */   public void setVersion(int paramInt)
/*      */   {
/*  550 */     if ((paramInt != 0) && (paramInt != 1)) {
/*  551 */       throw new IllegalArgumentException("cookie version should be 0 or 1");
/*      */     }
/*      */     
/*  554 */     this.version = paramInt;
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
/*      */   public boolean isHttpOnly()
/*      */   {
/*  567 */     return this.httpOnly;
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
/*      */   public void setHttpOnly(boolean paramBoolean)
/*      */   {
/*  582 */     this.httpOnly = paramBoolean;
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
/*      */   public static boolean domainMatches(String paramString1, String paramString2)
/*      */   {
/*  636 */     if ((paramString1 == null) || (paramString2 == null)) {
/*  637 */       return false;
/*      */     }
/*      */     
/*  640 */     boolean bool = ".local".equalsIgnoreCase(paramString1);
/*  641 */     int i = paramString1.indexOf('.');
/*  642 */     if (i == 0)
/*  643 */       i = paramString1.indexOf('.', 1);
/*  644 */     if ((!bool) && ((i == -1) || 
/*      */     
/*  646 */       (i == paramString1.length() - 1))) {
/*  647 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  651 */     int j = paramString2.indexOf('.');
/*  652 */     if ((j == -1) && ((bool) || 
/*      */     
/*  654 */       (paramString1.equalsIgnoreCase(paramString2 + ".local")))) {
/*  655 */       return true;
/*      */     }
/*      */     
/*  658 */     int k = paramString1.length();
/*  659 */     int m = paramString2.length() - k;
/*  660 */     if (m == 0)
/*      */     {
/*  662 */       return paramString2.equalsIgnoreCase(paramString1);
/*      */     }
/*  664 */     if (m > 0)
/*      */     {
/*  666 */       String str1 = paramString2.substring(0, m);
/*  667 */       String str2 = paramString2.substring(m);
/*      */       
/*  669 */       return (str1.indexOf('.') == -1) && (str2.equalsIgnoreCase(paramString1));
/*      */     }
/*  671 */     if (m == -1)
/*      */     {
/*      */ 
/*  674 */       return (paramString1.charAt(0) == '.') && (paramString2.equalsIgnoreCase(paramString1.substring(1)));
/*      */     }
/*      */     
/*  677 */     return false;
/*      */   }
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
/*  689 */     if (getVersion() > 0) {
/*  690 */       return toRFC2965HeaderString();
/*      */     }
/*  692 */     return toNetscapeHeaderString();
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  708 */     if (paramObject == this)
/*  709 */       return true;
/*  710 */     if (!(paramObject instanceof HttpCookie))
/*  711 */       return false;
/*  712 */     HttpCookie localHttpCookie = (HttpCookie)paramObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  720 */     return (equalsIgnoreCase(getName(), localHttpCookie.getName())) && (equalsIgnoreCase(getDomain(), localHttpCookie.getDomain())) && (Objects.equals(getPath(), localHttpCookie.getPath()));
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
/*      */   public int hashCode()
/*      */   {
/*  737 */     int i = this.name.toLowerCase().hashCode();
/*  738 */     int j = this.domain != null ? this.domain.toLowerCase().hashCode() : 0;
/*  739 */     int k = this.path != null ? this.path.hashCode() : 0;
/*      */     
/*  741 */     return i + j + k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  752 */       return super.clone();
/*      */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  754 */       throw new RuntimeException(localCloneNotSupportedException.getMessage());
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
/*      */   private static boolean isToken(String paramString)
/*      */   {
/*  776 */     int i = paramString.length();
/*      */     
/*  778 */     for (int j = 0; j < i; j++) {
/*  779 */       int k = paramString.charAt(j);
/*      */       
/*  781 */       if ((k < 32) || (k >= 127) || (",; ".indexOf(k) != -1))
/*  782 */         return false;
/*      */     }
/*  784 */     return true;
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
/*      */   private static HttpCookie parseInternal(String paramString, boolean paramBoolean)
/*      */   {
/*  801 */     HttpCookie localHttpCookie = null;
/*  802 */     String str1 = null;
/*      */     
/*  804 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
/*      */     String str2;
/*      */     String str3;
/*      */     try
/*      */     {
/*  809 */       str1 = localStringTokenizer.nextToken();
/*  810 */       int i = str1.indexOf('=');
/*  811 */       if (i != -1) {
/*  812 */         str2 = str1.substring(0, i).trim();
/*  813 */         str3 = str1.substring(i + 1).trim();
/*  814 */         if (paramBoolean)
/*      */         {
/*  816 */           localHttpCookie = new HttpCookie(str2, stripOffSurroundingQuote(str3), paramString);
/*      */         }
/*      */         else
/*      */         {
/*  820 */           localHttpCookie = new HttpCookie(str2, stripOffSurroundingQuote(str3));
/*      */         }
/*      */       } else {
/*  823 */         throw new IllegalArgumentException("Invalid cookie name-value pair");
/*      */       }
/*      */     } catch (NoSuchElementException localNoSuchElementException) {
/*  826 */       throw new IllegalArgumentException("Empty cookie header string");
/*      */     }
/*      */     
/*      */ 
/*  830 */     while (localStringTokenizer.hasMoreTokens()) {
/*  831 */       str1 = localStringTokenizer.nextToken();
/*  832 */       int j = str1.indexOf('=');
/*      */       
/*  834 */       if (j != -1) {
/*  835 */         str2 = str1.substring(0, j).trim();
/*  836 */         str3 = str1.substring(j + 1).trim();
/*      */       } else {
/*  838 */         str2 = str1.trim();
/*  839 */         str3 = null;
/*      */       }
/*      */       
/*      */ 
/*  843 */       assignAttribute(localHttpCookie, str2, str3);
/*      */     }
/*      */     
/*  846 */     return localHttpCookie;
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
/*  858 */   static final Map<String, CookieAttributeAssignor> assignors = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void assignAttribute(HttpCookie paramHttpCookie, String paramString1, String paramString2)
/*      */   {
/*  963 */     paramString2 = stripOffSurroundingQuote(paramString2);
/*      */     
/*  965 */     CookieAttributeAssignor localCookieAttributeAssignor = (CookieAttributeAssignor)assignors.get(paramString1.toLowerCase());
/*  966 */     if (localCookieAttributeAssignor != null) {
/*  967 */       localCookieAttributeAssignor.assign(paramHttpCookie, paramString1, paramString2);
/*      */     }
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  861 */     assignors.put("comment", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  865 */         if (paramAnonymousHttpCookie.getComment() == null)
/*  866 */           paramAnonymousHttpCookie.setComment(paramAnonymousString2);
/*      */       }
/*  868 */     });
/*  869 */     assignors.put("commenturl", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  873 */         if (paramAnonymousHttpCookie.getCommentURL() == null)
/*  874 */           paramAnonymousHttpCookie.setCommentURL(paramAnonymousString2);
/*      */       }
/*  876 */     });
/*  877 */     assignors.put("discard", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  881 */         paramAnonymousHttpCookie.setDiscard(true);
/*      */       }
/*  883 */     });
/*  884 */     assignors.put("domain", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  888 */         if (paramAnonymousHttpCookie.getDomain() == null)
/*  889 */           paramAnonymousHttpCookie.setDomain(paramAnonymousString2);
/*      */       }
/*  891 */     });
/*  892 */     assignors.put("max-age", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*      */         try {
/*  897 */           long l = Long.parseLong(paramAnonymousString2);
/*  898 */           if (paramAnonymousHttpCookie.getMaxAge() == -1L)
/*  899 */             paramAnonymousHttpCookie.setMaxAge(l);
/*      */         } catch (NumberFormatException localNumberFormatException) {
/*  901 */           throw new IllegalArgumentException("Illegal cookie max-age attribute");
/*      */         }
/*      */         
/*      */       }
/*  905 */     });
/*  906 */     assignors.put("path", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  910 */         if (paramAnonymousHttpCookie.getPath() == null)
/*  911 */           paramAnonymousHttpCookie.setPath(paramAnonymousString2);
/*      */       }
/*  913 */     });
/*  914 */     assignors.put("port", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  918 */         if (paramAnonymousHttpCookie.getPortlist() == null)
/*  919 */           paramAnonymousHttpCookie.setPortlist(paramAnonymousString2 == null ? "" : paramAnonymousString2);
/*      */       }
/*  921 */     });
/*  922 */     assignors.put("secure", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  926 */         paramAnonymousHttpCookie.setSecure(true);
/*      */       }
/*  928 */     });
/*  929 */     assignors.put("httponly", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  933 */         paramAnonymousHttpCookie.setHttpOnly(true);
/*      */       }
/*  935 */     });
/*  936 */     assignors.put("version", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*      */         try {
/*  941 */           int i = Integer.parseInt(paramAnonymousString2);
/*  942 */           paramAnonymousHttpCookie.setVersion(i);
/*      */ 
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException) {}
/*      */       }
/*  947 */     });
/*  948 */     assignors.put("expires", new CookieAttributeAssignor()
/*      */     {
/*      */       public void assign(HttpCookie paramAnonymousHttpCookie, String paramAnonymousString1, String paramAnonymousString2)
/*      */       {
/*  952 */         if (paramAnonymousHttpCookie.getMaxAge() == -1L) {
/*  953 */           paramAnonymousHttpCookie.setMaxAge(paramAnonymousHttpCookie.expiryDate2DeltaSeconds(paramAnonymousString2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  973 */     });
/*  974 */     SharedSecrets.setJavaNetHttpCookieAccess(new JavaNetHttpCookieAccess()
/*      */     {
/*      */       public List<HttpCookie> parse(String paramAnonymousString) {
/*  977 */         return HttpCookie.parse(paramAnonymousString, true);
/*      */       }
/*      */       
/*      */       public String header(HttpCookie paramAnonymousHttpCookie) {
/*  981 */         return paramAnonymousHttpCookie.header;
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String header()
/*      */   {
/*  992 */     return this.header;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String toNetscapeHeaderString()
/*      */   {
/* 1000 */     return getName() + "=" + getValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String toRFC2965HeaderString()
/*      */   {
/* 1008 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/* 1010 */     localStringBuilder.append(getName()).append("=\"").append(getValue()).append('"');
/* 1011 */     if (getPath() != null)
/* 1012 */       localStringBuilder.append(";$Path=\"").append(getPath()).append('"');
/* 1013 */     if (getDomain() != null)
/* 1014 */       localStringBuilder.append(";$Domain=\"").append(getDomain()).append('"');
/* 1015 */     if (getPortlist() != null) {
/* 1016 */       localStringBuilder.append(";$Port=\"").append(getPortlist()).append('"');
/*      */     }
/* 1018 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/* 1021 */   static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long expiryDate2DeltaSeconds(String paramString)
/*      */   {
/* 1031 */     GregorianCalendar localGregorianCalendar = new GregorianCalendar(GMT);
/* 1032 */     for (int i = 0; i < COOKIE_DATE_FORMATS.length; i++) {
/* 1033 */       SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(COOKIE_DATE_FORMATS[i], Locale.US);
/*      */       
/* 1035 */       localGregorianCalendar.set(1970, 0, 1, 0, 0, 0);
/* 1036 */       localSimpleDateFormat.setTimeZone(GMT);
/* 1037 */       localSimpleDateFormat.setLenient(false);
/* 1038 */       localSimpleDateFormat.set2DigitYearStart(localGregorianCalendar.getTime());
/*      */       try {
/* 1040 */         localGregorianCalendar.setTime(localSimpleDateFormat.parse(paramString));
/* 1041 */         if (!COOKIE_DATE_FORMATS[i].contains("yyyy"))
/*      */         {
/*      */ 
/* 1044 */           int j = localGregorianCalendar.get(1);
/* 1045 */           j %= 100;
/* 1046 */           if (j < 70) {
/* 1047 */             j += 2000;
/*      */           } else {
/* 1049 */             j += 1900;
/*      */           }
/* 1051 */           localGregorianCalendar.set(1, j);
/*      */         }
/* 1053 */         return (localGregorianCalendar.getTimeInMillis() - this.whenCreated) / 1000L;
/*      */       }
/*      */       catch (Exception localException) {}
/*      */     }
/*      */     
/* 1058 */     return 0L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int guessCookieVersion(String paramString)
/*      */   {
/* 1065 */     int i = 0;
/*      */     
/* 1067 */     paramString = paramString.toLowerCase();
/* 1068 */     if (paramString.indexOf("expires=") != -1)
/*      */     {
/* 1070 */       i = 0;
/* 1071 */     } else if (paramString.indexOf("version=") != -1)
/*      */     {
/* 1073 */       i = 1;
/* 1074 */     } else if (paramString.indexOf("max-age") != -1)
/*      */     {
/* 1076 */       i = 1;
/* 1077 */     } else if (startsWithIgnoreCase(paramString, "set-cookie2:"))
/*      */     {
/* 1079 */       i = 1;
/*      */     }
/*      */     
/* 1082 */     return i;
/*      */   }
/*      */   
/*      */   private static String stripOffSurroundingQuote(String paramString) {
/* 1086 */     if ((paramString != null) && (paramString.length() > 2) && 
/* 1087 */       (paramString.charAt(0) == '"') && (paramString.charAt(paramString.length() - 1) == '"')) {
/* 1088 */       return paramString.substring(1, paramString.length() - 1);
/*      */     }
/* 1090 */     if ((paramString != null) && (paramString.length() > 2) && 
/* 1091 */       (paramString.charAt(0) == '\'') && (paramString.charAt(paramString.length() - 1) == '\'')) {
/* 1092 */       return paramString.substring(1, paramString.length() - 1);
/*      */     }
/* 1094 */     return paramString;
/*      */   }
/*      */   
/*      */   private static boolean equalsIgnoreCase(String paramString1, String paramString2) {
/* 1098 */     if (paramString1 == paramString2) return true;
/* 1099 */     if ((paramString1 != null) && (paramString2 != null)) {
/* 1100 */       return paramString1.equalsIgnoreCase(paramString2);
/*      */     }
/* 1102 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean startsWithIgnoreCase(String paramString1, String paramString2) {
/* 1106 */     if ((paramString1 == null) || (paramString2 == null)) { return false;
/*      */     }
/* 1108 */     if ((paramString1.length() >= paramString2.length()) && 
/* 1109 */       (paramString2.equalsIgnoreCase(paramString1.substring(0, paramString2.length())))) {
/* 1110 */       return true;
/*      */     }
/*      */     
/* 1113 */     return false;
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
/*      */   private static List<String> splitMultiCookies(String paramString)
/*      */   {
/* 1128 */     ArrayList localArrayList = new ArrayList();
/* 1129 */     int i = 0;
/*      */     
/*      */ 
/* 1132 */     int j = 0; for (int k = 0; j < paramString.length(); j++) {
/* 1133 */       int m = paramString.charAt(j);
/* 1134 */       if (m == 34) i++;
/* 1135 */       if ((m == 44) && (i % 2 == 0))
/*      */       {
/* 1137 */         localArrayList.add(paramString.substring(k, j));
/* 1138 */         k = j + 1;
/*      */       }
/*      */     }
/*      */     
/* 1142 */     localArrayList.add(paramString.substring(k));
/*      */     
/* 1144 */     return localArrayList;
/*      */   }
/*      */   
/*      */   static abstract interface CookieAttributeAssignor
/*      */   {
/*      */     public abstract void assign(HttpCookie paramHttpCookie, String paramString1, String paramString2);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/HttpCookie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */