/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.net.PortConfig;
/*      */ import sun.net.RegisteredDomain;
/*      */ import sun.net.util.IPAddressUtil;
/*      */ import sun.net.www.URLConnection;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.util.Debug;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SocketPermission
/*      */   extends Permission
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7204263841984476862L;
/*      */   private static final int CONNECT = 1;
/*      */   private static final int LISTEN = 2;
/*      */   private static final int ACCEPT = 4;
/*      */   private static final int RESOLVE = 8;
/*      */   private static final int NONE = 0;
/*      */   private static final int ALL = 15;
/*      */   private static final int PORT_MIN = 0;
/*      */   private static final int PORT_MAX = 65535;
/*      */   private static final int PRIV_PORT_MAX = 1023;
/*      */   private static final int DEF_EPH_LOW = 49152;
/*      */   private transient int mask;
/*      */   private String actions;
/*      */   private transient String hostname;
/*      */   private transient String cname;
/*      */   private transient InetAddress[] addresses;
/*      */   private transient boolean wildcard;
/*      */   private transient boolean init_with_ip;
/*      */   private transient boolean invalid;
/*      */   private transient int[] portrange;
/*  225 */   private transient boolean defaultDeny = false;
/*      */   
/*      */ 
/*      */   private transient boolean untrusted;
/*      */   
/*      */ 
/*      */   private transient boolean trusted;
/*      */   
/*      */   private static boolean trustNameService;
/*      */   
/*  235 */   private static Debug debug = null;
/*  236 */   private static boolean debugInit = false;
/*      */   private transient String cdomain;
/*      */   private transient String hdomain;
/*      */   
/*  240 */   private static class EphemeralRange { static final int low = SocketPermission.initEphemeralPorts("low", 49152);
/*  241 */     static final int high = SocketPermission.initEphemeralPorts("high", 65535);
/*      */   }
/*      */   
/*      */   static {
/*  245 */     Boolean localBoolean = (Boolean)AccessController.doPrivileged(new GetBooleanAction("sun.net.trustNameService"));
/*      */     
/*  247 */     trustNameService = localBoolean.booleanValue();
/*      */   }
/*      */   
/*      */   private static synchronized Debug getDebug() {
/*  251 */     if (!debugInit) {
/*  252 */       debug = Debug.getInstance("access");
/*  253 */       debugInit = true;
/*      */     }
/*  255 */     return debug;
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
/*      */   public SocketPermission(String paramString1, String paramString2)
/*      */   {
/*  289 */     super(getHost(paramString1));
/*      */     
/*  291 */     init(getName(), getMask(paramString2));
/*      */   }
/*      */   
/*      */   SocketPermission(String paramString, int paramInt)
/*      */   {
/*  296 */     super(getHost(paramString));
/*      */     
/*  298 */     init(getName(), paramInt);
/*      */   }
/*      */   
/*      */   private void setDeny() {
/*  302 */     this.defaultDeny = true;
/*      */   }
/*      */   
/*      */   private static String getHost(String paramString) {
/*  306 */     if (paramString.equals("")) {
/*  307 */       return "localhost";
/*      */     }
/*      */     
/*      */ 
/*      */     int i;
/*      */     
/*      */ 
/*  314 */     if ((paramString.charAt(0) != '[') && 
/*  315 */       ((i = paramString.indexOf(':')) != paramString.lastIndexOf(':')))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  320 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ":");
/*  321 */       int j = localStringTokenizer.countTokens();
/*  322 */       if (j == 9)
/*      */       {
/*  324 */         i = paramString.lastIndexOf(':');
/*      */         
/*  326 */         paramString = "[" + paramString.substring(0, i) + "]" + paramString.substring(i);
/*  327 */       } else if ((j == 8) && (paramString.indexOf("::") == -1))
/*      */       {
/*  329 */         paramString = "[" + paramString + "]";
/*      */       }
/*      */       else {
/*  332 */         throw new IllegalArgumentException("Ambiguous hostport part");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  337 */     return paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int[] parsePort(String paramString)
/*      */     throws Exception
/*      */   {
/*  345 */     if ((paramString == null) || (paramString.equals("")) || (paramString.equals("*"))) {
/*  346 */       return new int[] { 0, 65535 };
/*      */     }
/*      */     
/*  349 */     int i = paramString.indexOf('-');
/*      */     
/*  351 */     if (i == -1) {
/*  352 */       int j = Integer.parseInt(paramString);
/*  353 */       return new int[] { j, j };
/*      */     }
/*  355 */     String str1 = paramString.substring(0, i);
/*  356 */     String str2 = paramString.substring(i + 1);
/*      */     
/*      */     int k;
/*  359 */     if (str1.equals("")) {
/*  360 */       k = 0;
/*      */     } else {
/*  362 */       k = Integer.parseInt(str1);
/*      */     }
/*      */     int m;
/*  365 */     if (str2.equals("")) {
/*  366 */       m = 65535;
/*      */     } else {
/*  368 */       m = Integer.parseInt(str2);
/*      */     }
/*  370 */     if ((k < 0) || (m < 0) || (m < k)) {
/*  371 */       throw new IllegalArgumentException("invalid port range");
/*      */     }
/*  373 */     return new int[] { k, m };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean includesEphemerals()
/*      */   {
/*  382 */     return this.portrange[0] == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void init(String paramString, int paramInt)
/*      */   {
/*  393 */     if ((paramInt & 0xF) != paramInt) {
/*  394 */       throw new IllegalArgumentException("invalid actions mask");
/*      */     }
/*      */     
/*  397 */     this.mask = (paramInt | 0x8);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  408 */     int i = 0;
/*  409 */     int j = 0;int k = 0;
/*  410 */     int m = -1;
/*  411 */     String str1 = paramString;
/*  412 */     if (paramString.charAt(0) == '[') {
/*  413 */       j = 1;
/*  414 */       i = paramString.indexOf(']');
/*  415 */       if (i != -1) {
/*  416 */         paramString = paramString.substring(j, i);
/*      */       } else {
/*  418 */         throw new IllegalArgumentException("invalid host/port: " + paramString);
/*      */       }
/*      */       
/*  421 */       m = str1.indexOf(':', i + 1);
/*      */     } else {
/*  423 */       j = 0;
/*  424 */       m = paramString.indexOf(':', i);
/*  425 */       k = m;
/*  426 */       if (m != -1) {
/*  427 */         paramString = paramString.substring(j, k);
/*      */       }
/*      */     }
/*      */     
/*  431 */     if (m != -1) {
/*  432 */       String str2 = str1.substring(m + 1);
/*      */       try {
/*  434 */         this.portrange = parsePort(str2);
/*      */       } catch (Exception localException) {
/*  436 */         throw new IllegalArgumentException("invalid port range: " + str2);
/*      */       }
/*      */     }
/*      */     else {
/*  440 */       this.portrange = new int[] { 0, 65535 };
/*      */     }
/*      */     
/*  443 */     this.hostname = paramString;
/*      */     
/*      */ 
/*  446 */     if (paramString.lastIndexOf('*') > 0) {
/*  447 */       throw new IllegalArgumentException("invalid host wildcard specification");
/*      */     }
/*  449 */     if (paramString.startsWith("*")) {
/*  450 */       this.wildcard = true;
/*  451 */       if (paramString.equals("*")) {
/*  452 */         this.cname = "";
/*  453 */       } else if (paramString.startsWith("*.")) {
/*  454 */         this.cname = paramString.substring(1).toLowerCase();
/*      */       } else {
/*  456 */         throw new IllegalArgumentException("invalid host wildcard specification");
/*      */       }
/*      */       
/*  459 */       return;
/*      */     }
/*  461 */     if (paramString.length() > 0)
/*      */     {
/*  463 */       char c = paramString.charAt(0);
/*  464 */       if ((c == ':') || (Character.digit(c, 16) != -1)) {
/*  465 */         byte[] arrayOfByte = IPAddressUtil.textToNumericFormatV4(paramString);
/*  466 */         if (arrayOfByte == null) {
/*  467 */           arrayOfByte = IPAddressUtil.textToNumericFormatV6(paramString);
/*      */         }
/*  469 */         if (arrayOfByte != null)
/*      */         {
/*      */           try
/*      */           {
/*  473 */             this.addresses = new InetAddress[] {InetAddress.getByAddress(arrayOfByte) };
/*  474 */             this.init_with_ip = true;
/*      */           }
/*      */           catch (UnknownHostException localUnknownHostException) {
/*  477 */             this.invalid = true;
/*      */           }
/*      */         }
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
/*      */   private static int getMask(String paramString)
/*      */   {
/*  493 */     if (paramString == null) {
/*  494 */       throw new NullPointerException("action can't be null");
/*      */     }
/*      */     
/*  497 */     if (paramString.equals("")) {
/*  498 */       throw new IllegalArgumentException("action can't be empty");
/*      */     }
/*      */     
/*  501 */     int i = 0;
/*      */     
/*      */ 
/*      */ 
/*  505 */     if (paramString == "resolve")
/*  506 */       return 8;
/*  507 */     if (paramString == "connect")
/*  508 */       return 1;
/*  509 */     if (paramString == "listen")
/*  510 */       return 2;
/*  511 */     if (paramString == "accept")
/*  512 */       return 4;
/*  513 */     if (paramString == "connect,accept") {
/*  514 */       return 5;
/*      */     }
/*      */     
/*  517 */     char[] arrayOfChar = paramString.toCharArray();
/*      */     
/*  519 */     int j = arrayOfChar.length - 1;
/*  520 */     if (j < 0) {
/*  521 */       return i;
/*      */     }
/*  523 */     while (j != -1)
/*      */     {
/*      */       int k;
/*      */       
/*  527 */       while ((j != -1) && (((k = arrayOfChar[j]) == ' ') || (k == 13) || (k == 10) || (k == 12) || (k == 9)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  532 */         j--;
/*      */       }
/*      */       
/*      */       int m;
/*      */       
/*  537 */       if ((j >= 6) && ((arrayOfChar[(j - 6)] == 'c') || (arrayOfChar[(j - 6)] == 'C')) && ((arrayOfChar[(j - 5)] == 'o') || (arrayOfChar[(j - 5)] == 'O')) && ((arrayOfChar[(j - 4)] == 'n') || (arrayOfChar[(j - 4)] == 'N')) && ((arrayOfChar[(j - 3)] == 'n') || (arrayOfChar[(j - 3)] == 'N')) && ((arrayOfChar[(j - 2)] == 'e') || (arrayOfChar[(j - 2)] == 'E')) && ((arrayOfChar[(j - 1)] == 'c') || (arrayOfChar[(j - 1)] == 'C')) && ((arrayOfChar[j] == 't') || (arrayOfChar[j] == 'T')))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  545 */         m = 7;
/*  546 */         i |= 0x1;
/*      */       }
/*  548 */       else if ((j >= 6) && ((arrayOfChar[(j - 6)] == 'r') || (arrayOfChar[(j - 6)] == 'R')) && ((arrayOfChar[(j - 5)] == 'e') || (arrayOfChar[(j - 5)] == 'E')) && ((arrayOfChar[(j - 4)] == 's') || (arrayOfChar[(j - 4)] == 'S')) && ((arrayOfChar[(j - 3)] == 'o') || (arrayOfChar[(j - 3)] == 'O')) && ((arrayOfChar[(j - 2)] == 'l') || (arrayOfChar[(j - 2)] == 'L')) && ((arrayOfChar[(j - 1)] == 'v') || (arrayOfChar[(j - 1)] == 'V')) && ((arrayOfChar[j] == 'e') || (arrayOfChar[j] == 'E')))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  556 */         m = 7;
/*  557 */         i |= 0x8;
/*      */       }
/*  559 */       else if ((j >= 5) && ((arrayOfChar[(j - 5)] == 'l') || (arrayOfChar[(j - 5)] == 'L')) && ((arrayOfChar[(j - 4)] == 'i') || (arrayOfChar[(j - 4)] == 'I')) && ((arrayOfChar[(j - 3)] == 's') || (arrayOfChar[(j - 3)] == 'S')) && ((arrayOfChar[(j - 2)] == 't') || (arrayOfChar[(j - 2)] == 'T')) && ((arrayOfChar[(j - 1)] == 'e') || (arrayOfChar[(j - 1)] == 'E')) && ((arrayOfChar[j] == 'n') || (arrayOfChar[j] == 'N')))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  566 */         m = 6;
/*  567 */         i |= 0x2;
/*      */       }
/*  569 */       else if ((j >= 5) && ((arrayOfChar[(j - 5)] == 'a') || (arrayOfChar[(j - 5)] == 'A')) && ((arrayOfChar[(j - 4)] == 'c') || (arrayOfChar[(j - 4)] == 'C')) && ((arrayOfChar[(j - 3)] == 'c') || (arrayOfChar[(j - 3)] == 'C')) && ((arrayOfChar[(j - 2)] == 'e') || (arrayOfChar[(j - 2)] == 'E')) && ((arrayOfChar[(j - 1)] == 'p') || (arrayOfChar[(j - 1)] == 'P')) && ((arrayOfChar[j] == 't') || (arrayOfChar[j] == 'T')))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  576 */         m = 6;
/*  577 */         i |= 0x4;
/*      */       }
/*      */       else
/*      */       {
/*  581 */         throw new IllegalArgumentException("invalid permission: " + paramString);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  587 */       int n = 0;
/*  588 */       while ((j >= m) && (n == 0)) {
/*  589 */         switch (arrayOfChar[(j - m)]) {
/*      */         case ',': 
/*  591 */           n = 1;
/*  592 */           break;
/*      */         case '\t': case '\n': case '\f': 
/*      */         case '\r': case ' ': 
/*      */           break;
/*      */         default: 
/*  597 */           throw new IllegalArgumentException("invalid permission: " + paramString);
/*      */         }
/*      */         
/*  600 */         j--;
/*      */       }
/*      */       
/*      */ 
/*  604 */       j -= m;
/*      */     }
/*      */     
/*  607 */     return i;
/*      */   }
/*      */   
/*      */   private boolean isUntrusted()
/*      */     throws UnknownHostException
/*      */   {
/*  613 */     if (this.trusted) return false;
/*  614 */     if ((this.invalid) || (this.untrusted)) return true;
/*      */     try {
/*  616 */       if ((!trustNameService) && ((this.defaultDeny) || 
/*  617 */         (URLConnection.isProxiedHost(this.hostname)))) {
/*  618 */         if (this.cname == null) {
/*  619 */           getCanonName();
/*      */         }
/*  621 */         if (!match(this.cname, this.hostname))
/*      */         {
/*  623 */           if (!authorized(this.hostname, this.addresses[0].getAddress())) {
/*  624 */             this.untrusted = true;
/*  625 */             Debug localDebug = getDebug();
/*  626 */             if ((localDebug != null) && (Debug.isOn("failure"))) {
/*  627 */               localDebug.println("socket access restriction: proxied host (" + this.addresses[0] + ")" + " does not match " + this.cname + " from reverse lookup");
/*      */             }
/*  629 */             return true;
/*      */           }
/*      */         }
/*  632 */         this.trusted = true;
/*      */       }
/*      */     } catch (UnknownHostException localUnknownHostException) {
/*  635 */       this.invalid = true;
/*  636 */       throw localUnknownHostException;
/*      */     }
/*  638 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void getCanonName()
/*      */     throws UnknownHostException
/*      */   {
/*  648 */     if ((this.cname != null) || (this.invalid) || (this.untrusted)) { return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  656 */       if (this.addresses == null) {
/*  657 */         getIP();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  662 */       if (this.init_with_ip) {
/*  663 */         this.cname = this.addresses[0].getHostName(false).toLowerCase();
/*      */       }
/*      */       else {
/*  666 */         this.cname = InetAddress.getByName(this.addresses[0].getHostAddress()).getHostName(false).toLowerCase();
/*      */       }
/*      */     } catch (UnknownHostException localUnknownHostException) {
/*  669 */       this.invalid = true;
/*  670 */       throw localUnknownHostException;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean match(String paramString1, String paramString2)
/*      */   {
/*  677 */     String str1 = paramString1.toLowerCase();
/*  678 */     String str2 = paramString2.toLowerCase();
/*  679 */     if ((str1.startsWith(str2)) && (
/*  680 */       (str1.length() == str2.length()) || (str1.charAt(str2.length()) == '.')))
/*  681 */       return true;
/*  682 */     if (this.cdomain == null) {
/*  683 */       this.cdomain = RegisteredDomain.getRegisteredDomain(str1);
/*      */     }
/*  685 */     if (this.hdomain == null) {
/*  686 */       this.hdomain = RegisteredDomain.getRegisteredDomain(str2);
/*      */     }
/*      */     
/*      */ 
/*  690 */     return (this.cdomain.length() != 0) && (this.hdomain.length() != 0) && (this.cdomain.equals(this.hdomain));
/*      */   }
/*      */   
/*      */   private boolean authorized(String paramString, byte[] paramArrayOfByte) {
/*  694 */     if (paramArrayOfByte.length == 4)
/*  695 */       return authorizedIPv4(paramString, paramArrayOfByte);
/*  696 */     if (paramArrayOfByte.length == 16) {
/*  697 */       return authorizedIPv6(paramString, paramArrayOfByte);
/*      */     }
/*  699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean authorizedIPv4(String paramString, byte[] paramArrayOfByte) {
/*  703 */     String str = "";
/*      */     
/*      */     try
/*      */     {
/*  707 */       str = "auth." + (paramArrayOfByte[3] & 0xFF) + "." + (paramArrayOfByte[2] & 0xFF) + "." + (paramArrayOfByte[1] & 0xFF) + "." + (paramArrayOfByte[0] & 0xFF) + ".in-addr.arpa";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  713 */       str = this.hostname + '.' + str;
/*  714 */       InetAddress localInetAddress = InetAddress.getAllByName0(str, false)[0];
/*  715 */       if (localInetAddress.equals(InetAddress.getByAddress(paramArrayOfByte))) {
/*  716 */         return true;
/*      */       }
/*  718 */       Debug localDebug1 = getDebug();
/*  719 */       if ((localDebug1 != null) && (Debug.isOn("failure"))) {
/*  720 */         localDebug1.println("socket access restriction: IP address of " + localInetAddress + " != " + InetAddress.getByAddress(paramArrayOfByte));
/*      */       }
/*      */     } catch (UnknownHostException localUnknownHostException) {
/*  723 */       Debug localDebug2 = getDebug();
/*  724 */       if ((localDebug2 != null) && (Debug.isOn("failure"))) {
/*  725 */         localDebug2.println("socket access restriction: forward lookup failed for " + str);
/*      */       }
/*      */     }
/*  728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean authorizedIPv6(String paramString, byte[] paramArrayOfByte) {
/*  732 */     String str = "";
/*      */     
/*      */     try
/*      */     {
/*  736 */       StringBuffer localStringBuffer = new StringBuffer(39);
/*      */       
/*  738 */       for (int i = 15; i >= 0; i--) {
/*  739 */         localStringBuffer.append(Integer.toHexString(paramArrayOfByte[i] & 0xF));
/*  740 */         localStringBuffer.append('.');
/*  741 */         localStringBuffer.append(Integer.toHexString(paramArrayOfByte[i] >> 4 & 0xF));
/*  742 */         localStringBuffer.append('.');
/*      */       }
/*  744 */       str = "auth." + localStringBuffer.toString() + "IP6.ARPA";
/*      */       
/*  746 */       str = this.hostname + '.' + str;
/*  747 */       InetAddress localInetAddress = InetAddress.getAllByName0(str, false)[0];
/*  748 */       if (localInetAddress.equals(InetAddress.getByAddress(paramArrayOfByte)))
/*  749 */         return true;
/*  750 */       localDebug = getDebug();
/*  751 */       if ((localDebug != null) && (Debug.isOn("failure"))) {
/*  752 */         localDebug.println("socket access restriction: IP address of " + localInetAddress + " != " + InetAddress.getByAddress(paramArrayOfByte));
/*      */       }
/*      */     } catch (UnknownHostException localUnknownHostException) {
/*  755 */       Debug localDebug = getDebug();
/*  756 */       if ((localDebug != null) && (Debug.isOn("failure"))) {
/*  757 */         localDebug.println("socket access restriction: forward lookup failed for " + str);
/*      */       }
/*      */     }
/*  760 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void getIP()
/*      */     throws UnknownHostException
/*      */   {
/*  771 */     if ((this.addresses != null) || (this.wildcard) || (this.invalid)) { return;
/*      */     }
/*      */     try
/*      */     {
/*      */       String str;
/*  776 */       if (getName().charAt(0) == '[')
/*      */       {
/*  778 */         str = getName().substring(1, getName().indexOf(']'));
/*      */       } else {
/*  780 */         int i = getName().indexOf(":");
/*  781 */         if (i == -1) {
/*  782 */           str = getName();
/*      */         } else {
/*  784 */           str = getName().substring(0, i);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  789 */       this.addresses = new InetAddress[] {InetAddress.getAllByName0(str, false)[0] };
/*      */     }
/*      */     catch (UnknownHostException localUnknownHostException) {
/*  792 */       this.invalid = true;
/*  793 */       throw localUnknownHostException;
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/*  795 */       this.invalid = true;
/*  796 */       throw new UnknownHostException(getName());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean implies(Permission paramPermission)
/*      */   {
/*  837 */     if (!(paramPermission instanceof SocketPermission)) {
/*  838 */       return false;
/*      */     }
/*  840 */     if (paramPermission == this) {
/*  841 */       return true;
/*      */     }
/*  843 */     SocketPermission localSocketPermission = (SocketPermission)paramPermission;
/*      */     
/*      */ 
/*  846 */     return ((this.mask & localSocketPermission.mask) == localSocketPermission.mask) && (impliesIgnoreMask(localSocketPermission));
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
/*      */   boolean impliesIgnoreMask(SocketPermission paramSocketPermission)
/*      */   {
/*  875 */     if ((paramSocketPermission.mask & 0x8) != paramSocketPermission.mask)
/*      */     {
/*      */ 
/*  878 */       if ((paramSocketPermission.portrange[0] < this.portrange[0]) || (paramSocketPermission.portrange[1] > this.portrange[1]))
/*      */       {
/*      */ 
/*      */ 
/*  882 */         if ((includesEphemerals()) || (paramSocketPermission.includesEphemerals())) {
/*  883 */           if (!inRange(this.portrange[0], this.portrange[1], paramSocketPermission.portrange[0], paramSocketPermission.portrange[1]))
/*      */           {
/*      */ 
/*  886 */             return false;
/*      */           }
/*      */         } else {
/*  889 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  895 */     if ((this.wildcard) && ("".equals(this.cname))) {
/*  896 */       return true;
/*      */     }
/*      */     
/*  899 */     if ((this.invalid) || (paramSocketPermission.invalid)) {
/*  900 */       return compareHostnames(paramSocketPermission);
/*      */     }
/*      */     try {
/*      */       int i;
/*  904 */       if (this.init_with_ip) {
/*  905 */         if (paramSocketPermission.wildcard) {
/*  906 */           return false;
/*      */         }
/*  908 */         if (paramSocketPermission.init_with_ip) {
/*  909 */           return this.addresses[0].equals(paramSocketPermission.addresses[0]);
/*      */         }
/*  911 */         if (paramSocketPermission.addresses == null) {
/*  912 */           paramSocketPermission.getIP();
/*      */         }
/*  914 */         for (i = 0; i < paramSocketPermission.addresses.length; i++) {
/*  915 */           if (this.addresses[0].equals(paramSocketPermission.addresses[i])) {
/*  916 */             return true;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  921 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  925 */       if ((this.wildcard) || (paramSocketPermission.wildcard))
/*      */       {
/*      */ 
/*      */ 
/*  929 */         if ((this.wildcard) && (paramSocketPermission.wildcard)) {
/*  930 */           return paramSocketPermission.cname.endsWith(this.cname);
/*      */         }
/*      */         
/*  933 */         if (paramSocketPermission.wildcard) {
/*  934 */           return false;
/*      */         }
/*      */         
/*      */ 
/*  938 */         if (paramSocketPermission.cname == null) {
/*  939 */           paramSocketPermission.getCanonName();
/*      */         }
/*  941 */         return paramSocketPermission.cname.endsWith(this.cname);
/*      */       }
/*      */       
/*      */ 
/*  945 */       if (this.addresses == null) {
/*  946 */         getIP();
/*      */       }
/*      */       
/*  949 */       if (paramSocketPermission.addresses == null) {
/*  950 */         paramSocketPermission.getIP();
/*      */       }
/*      */       
/*  953 */       if ((!paramSocketPermission.init_with_ip) || (!isUntrusted())) {
/*  954 */         for (int j = 0; j < this.addresses.length; j++) {
/*  955 */           for (i = 0; i < paramSocketPermission.addresses.length; i++) {
/*  956 */             if (this.addresses[j].equals(paramSocketPermission.addresses[i])) {
/*  957 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  963 */         if (this.cname == null) {
/*  964 */           getCanonName();
/*      */         }
/*      */         
/*  967 */         if (paramSocketPermission.cname == null) {
/*  968 */           paramSocketPermission.getCanonName();
/*      */         }
/*      */         
/*  971 */         return this.cname.equalsIgnoreCase(paramSocketPermission.cname);
/*      */       }
/*      */     }
/*      */     catch (UnknownHostException localUnknownHostException) {
/*  975 */       return compareHostnames(paramSocketPermission);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  981 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean compareHostnames(SocketPermission paramSocketPermission)
/*      */   {
/*  987 */     String str1 = this.hostname;
/*  988 */     String str2 = paramSocketPermission.hostname;
/*      */     
/*  990 */     if (str1 == null)
/*  991 */       return false;
/*  992 */     if (this.wildcard) {
/*  993 */       int i = this.cname.length();
/*  994 */       return str2.regionMatches(true, str2
/*  995 */         .length() - i, this.cname, 0, i);
/*      */     }
/*      */     
/*  998 */     return str1.equalsIgnoreCase(str2);
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1013 */     if (paramObject == this) {
/* 1014 */       return true;
/*      */     }
/* 1016 */     if (!(paramObject instanceof SocketPermission)) {
/* 1017 */       return false;
/*      */     }
/* 1019 */     SocketPermission localSocketPermission = (SocketPermission)paramObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1024 */     if (this.mask != localSocketPermission.mask) { return false;
/*      */     }
/* 1026 */     if ((localSocketPermission.mask & 0x8) != localSocketPermission.mask)
/*      */     {
/* 1028 */       if ((this.portrange[0] != localSocketPermission.portrange[0]) || (this.portrange[1] != localSocketPermission.portrange[1]))
/*      */       {
/* 1030 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1041 */     if (getName().equalsIgnoreCase(localSocketPermission.getName())) {
/* 1042 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1050 */       getCanonName();
/* 1051 */       localSocketPermission.getCanonName();
/*      */     } catch (UnknownHostException localUnknownHostException) {
/* 1053 */       return false;
/*      */     }
/*      */     
/* 1056 */     if ((this.invalid) || (localSocketPermission.invalid)) {
/* 1057 */       return false;
/*      */     }
/* 1059 */     if (this.cname != null) {
/* 1060 */       return this.cname.equalsIgnoreCase(localSocketPermission.cname);
/*      */     }
/*      */     
/* 1063 */     return false;
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
/* 1080 */     if ((this.init_with_ip) || (this.wildcard)) {
/* 1081 */       return getName().hashCode();
/*      */     }
/*      */     try
/*      */     {
/* 1085 */       getCanonName();
/*      */     }
/*      */     catch (UnknownHostException localUnknownHostException) {}
/*      */     
/*      */ 
/* 1090 */     if ((this.invalid) || (this.cname == null)) {
/* 1091 */       return getName().hashCode();
/*      */     }
/* 1093 */     return this.cname.hashCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getMask()
/*      */   {
/* 1103 */     return this.mask;
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
/*      */   private static String getActions(int paramInt)
/*      */   {
/* 1117 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1118 */     int i = 0;
/*      */     
/* 1120 */     if ((paramInt & 0x1) == 1) {
/* 1121 */       i = 1;
/* 1122 */       localStringBuilder.append("connect");
/*      */     }
/*      */     
/* 1125 */     if ((paramInt & 0x2) == 2) {
/* 1126 */       if (i != 0) localStringBuilder.append(','); else
/* 1127 */         i = 1;
/* 1128 */       localStringBuilder.append("listen");
/*      */     }
/*      */     
/* 1131 */     if ((paramInt & 0x4) == 4) {
/* 1132 */       if (i != 0) localStringBuilder.append(','); else
/* 1133 */         i = 1;
/* 1134 */       localStringBuilder.append("accept");
/*      */     }
/*      */     
/*      */ 
/* 1138 */     if ((paramInt & 0x8) == 8) {
/* 1139 */       if (i != 0) localStringBuilder.append(','); else
/* 1140 */         i = 1;
/* 1141 */       localStringBuilder.append("resolve");
/*      */     }
/*      */     
/* 1144 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getActions()
/*      */   {
/* 1156 */     if (this.actions == null) {
/* 1157 */       this.actions = getActions(this.mask);
/*      */     }
/* 1159 */     return this.actions;
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
/*      */   public PermissionCollection newPermissionCollection()
/*      */   {
/* 1175 */     return new SocketPermissionCollection();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1188 */     if (this.actions == null)
/* 1189 */       getActions();
/* 1190 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1201 */     paramObjectInputStream.defaultReadObject();
/* 1202 */     init(getName(), getMask(this.actions));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int initEphemeralPorts(String paramString, int paramInt)
/*      */   {
/* 1210 */     ((Integer)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Integer run() {
/* 1213 */         int i = Integer.getInteger("jdk.net.ephemeralPortRange." + this.val$suffix, -1).intValue();
/*      */         
/*      */ 
/* 1216 */         if (i != -1) {
/* 1217 */           return Integer.valueOf(i);
/*      */         }
/* 1219 */         return Integer.valueOf(this.val$suffix.equals("low") ? 
/* 1220 */           PortConfig.getLower() : PortConfig.getUpper());
/*      */       }
/*      */     })).intValue();
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
/*      */   private static boolean inRange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1236 */     int i = EphemeralRange.low;
/* 1237 */     int j = EphemeralRange.high;
/*      */     
/* 1239 */     if (paramInt3 == 0)
/*      */     {
/* 1241 */       if (!inRange(paramInt1, paramInt2, i, j)) {
/* 1242 */         return false;
/*      */       }
/* 1244 */       if (paramInt4 == 0)
/*      */       {
/* 1246 */         return true;
/*      */       }
/*      */       
/* 1249 */       paramInt3 = 1;
/*      */     }
/*      */     
/* 1252 */     if ((paramInt1 == 0) && (paramInt2 == 0))
/*      */     {
/* 1254 */       return (paramInt3 >= i) && (paramInt4 <= j);
/*      */     }
/*      */     
/* 1257 */     if (paramInt1 != 0)
/*      */     {
/* 1259 */       return (paramInt3 >= paramInt1) && (paramInt4 <= paramInt2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1266 */     if (paramInt2 >= i - 1) {
/* 1267 */       return paramInt4 <= j;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1274 */     return ((paramInt3 <= paramInt2) && (paramInt4 <= paramInt2)) || ((paramInt3 >= i) && (paramInt4 <= j));
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/SocketPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */