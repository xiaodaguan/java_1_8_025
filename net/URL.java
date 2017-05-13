/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.util.Hashtable;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.net.ApplicationProxy;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class URL
/*      */   implements Serializable
/*      */ {
/*      */   static final long serialVersionUID = -7627629688361524110L;
/*      */   private static final String protocolPathProp = "java.protocol.handler.pkgs";
/*      */   private String protocol;
/*      */   private String host;
/*  170 */   private int port = -1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String file;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient String query;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String authority;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient String path;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient String userInfo;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String ref;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   transient InetAddress hostAddress;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   transient URLStreamHandler handler;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  220 */   private int hashCode = -1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static URLStreamHandlerFactory factory;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public URL(String paramString1, String paramString2, int paramInt, String paramString3)
/*      */     throws MalformedURLException
/*      */   {
/*  303 */     this(paramString1, paramString2, paramInt, paramString3, null);
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
/*      */   public URL(String paramString1, String paramString2, String paramString3)
/*      */     throws MalformedURLException
/*      */   {
/*  326 */     this(paramString1, paramString2, -1, paramString3);
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
/*      */   public URL(String paramString1, String paramString2, int paramInt, String paramString3, URLStreamHandler paramURLStreamHandler)
/*      */     throws MalformedURLException
/*      */   {
/*  370 */     if (paramURLStreamHandler != null) {
/*  371 */       localObject = System.getSecurityManager();
/*  372 */       if (localObject != null)
/*      */       {
/*  374 */         checkSpecifyHandler((SecurityManager)localObject);
/*      */       }
/*      */     }
/*      */     
/*  378 */     paramString1 = paramString1.toLowerCase();
/*  379 */     this.protocol = paramString1;
/*  380 */     if (paramString2 != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  386 */       if ((paramString2.indexOf(':') >= 0) && (!paramString2.startsWith("["))) {
/*  387 */         paramString2 = "[" + paramString2 + "]";
/*      */       }
/*  389 */       this.host = paramString2;
/*      */       
/*  391 */       if (paramInt < -1) {
/*  392 */         throw new MalformedURLException("Invalid port number :" + paramInt);
/*      */       }
/*      */       
/*  395 */       this.port = paramInt;
/*  396 */       this.authority = (paramString2 + ":" + paramInt);
/*      */     }
/*      */     
/*  399 */     Object localObject = new Parts(paramString3);
/*  400 */     this.path = ((Parts)localObject).getPath();
/*  401 */     this.query = ((Parts)localObject).getQuery();
/*      */     
/*  403 */     if (this.query != null) {
/*  404 */       this.file = (this.path + "?" + this.query);
/*      */     } else {
/*  406 */       this.file = this.path;
/*      */     }
/*  408 */     this.ref = ((Parts)localObject).getRef();
/*      */     
/*      */ 
/*      */ 
/*  412 */     if ((paramURLStreamHandler == null) && 
/*  413 */       ((paramURLStreamHandler = getURLStreamHandler(paramString1)) == null)) {
/*  414 */       throw new MalformedURLException("unknown protocol: " + paramString1);
/*      */     }
/*  416 */     this.handler = paramURLStreamHandler;
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
/*      */   public URL(String paramString)
/*      */     throws MalformedURLException
/*      */   {
/*  432 */     this(null, paramString);
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
/*      */   public URL(URL paramURL, String paramString)
/*      */     throws MalformedURLException
/*      */   {
/*  483 */     this(paramURL, paramString, null);
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
/*      */   public URL(URL paramURL, String paramString, URLStreamHandler paramURLStreamHandler)
/*      */     throws MalformedURLException
/*      */   {
/*  509 */     String str = paramString;
/*      */     
/*  511 */     int m = 0;
/*  512 */     Object localObject1 = null;
/*  513 */     int n = 0;
/*  514 */     int i1 = 0;
/*      */     
/*      */     Object localObject2;
/*  517 */     if (paramURLStreamHandler != null) {
/*  518 */       localObject2 = System.getSecurityManager();
/*  519 */       if (localObject2 != null) {
/*  520 */         checkSpecifyHandler((SecurityManager)localObject2);
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  525 */       int j = paramString.length();
/*  526 */       while ((j > 0) && (paramString.charAt(j - 1) <= ' ')) {
/*  527 */         j--;
/*      */       }
/*  529 */       while ((m < j) && (paramString.charAt(m) <= ' ')) {
/*  530 */         m++;
/*      */       }
/*      */       
/*  533 */       if (paramString.regionMatches(true, m, "url:", 0, 4)) {
/*  534 */         m += 4;
/*      */       }
/*  536 */       if ((m < paramString.length()) && (paramString.charAt(m) == '#'))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  541 */         n = 1; }
/*      */       int k;
/*  543 */       for (int i = m; (n == 0) && (i < j) && 
/*  544 */             ((k = paramString.charAt(i)) != '/'); i++) {
/*  545 */         if (k == 58)
/*      */         {
/*  547 */           localObject2 = paramString.substring(m, i).toLowerCase();
/*  548 */           if (!isValidProtocol((String)localObject2)) break;
/*  549 */           localObject1 = localObject2;
/*  550 */           m = i + 1; break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  557 */       this.protocol = ((String)localObject1);
/*  558 */       if ((paramURL != null) && ((localObject1 == null) || 
/*  559 */         (((String)localObject1).equalsIgnoreCase(paramURL.protocol))))
/*      */       {
/*      */ 
/*  562 */         if (paramURLStreamHandler == null) {
/*  563 */           paramURLStreamHandler = paramURL.handler;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  570 */         if ((paramURL.path != null) && (paramURL.path.startsWith("/"))) {
/*  571 */           localObject1 = null;
/*      */         }
/*  573 */         if (localObject1 == null) {
/*  574 */           this.protocol = paramURL.protocol;
/*  575 */           this.authority = paramURL.authority;
/*  576 */           this.userInfo = paramURL.userInfo;
/*  577 */           this.host = paramURL.host;
/*  578 */           this.port = paramURL.port;
/*  579 */           this.file = paramURL.file;
/*  580 */           this.path = paramURL.path;
/*  581 */           i1 = 1;
/*      */         }
/*      */       }
/*      */       
/*  585 */       if (this.protocol == null) {
/*  586 */         throw new MalformedURLException("no protocol: " + str);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  591 */       if ((paramURLStreamHandler == null) && 
/*  592 */         ((paramURLStreamHandler = getURLStreamHandler(this.protocol)) == null)) {
/*  593 */         throw new MalformedURLException("unknown protocol: " + this.protocol);
/*      */       }
/*      */       
/*  596 */       this.handler = paramURLStreamHandler;
/*      */       
/*  598 */       i = paramString.indexOf('#', m);
/*  599 */       if (i >= 0) {
/*  600 */         this.ref = paramString.substring(i + 1, j);
/*  601 */         j = i;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  608 */       if ((i1 != 0) && (m == j)) {
/*  609 */         this.query = paramURL.query;
/*  610 */         if (this.ref == null) {
/*  611 */           this.ref = paramURL.ref;
/*      */         }
/*      */       }
/*      */       
/*  615 */       paramURLStreamHandler.parseURL(this, paramString, m, j);
/*      */     }
/*      */     catch (MalformedURLException localMalformedURLException1) {
/*  618 */       throw localMalformedURLException1;
/*      */     } catch (Exception localException) {
/*  620 */       MalformedURLException localMalformedURLException2 = new MalformedURLException(localException.getMessage());
/*  621 */       localMalformedURLException2.initCause(localException);
/*  622 */       throw localMalformedURLException2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isValidProtocol(String paramString)
/*      */   {
/*  630 */     int i = paramString.length();
/*  631 */     if (i < 1)
/*  632 */       return false;
/*  633 */     char c = paramString.charAt(0);
/*  634 */     if (!Character.isLetter(c))
/*  635 */       return false;
/*  636 */     for (int j = 1; j < i; j++) {
/*  637 */       c = paramString.charAt(j);
/*  638 */       if ((!Character.isLetterOrDigit(c)) && (c != '.') && (c != '+') && (c != '-'))
/*      */       {
/*  640 */         return false;
/*      */       }
/*      */     }
/*  643 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void checkSpecifyHandler(SecurityManager paramSecurityManager)
/*      */   {
/*  650 */     paramSecurityManager.checkPermission(SecurityConstants.SPECIFY_HANDLER_PERMISSION);
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
/*      */   void set(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
/*      */   {
/*  666 */     synchronized (this) {
/*  667 */       this.protocol = paramString1;
/*  668 */       this.host = paramString2;
/*  669 */       this.authority = (paramString2 + ":" + paramInt);
/*  670 */       this.port = paramInt;
/*  671 */       this.file = paramString3;
/*  672 */       this.ref = paramString4;
/*      */       
/*      */ 
/*  675 */       this.hashCode = -1;
/*  676 */       this.hostAddress = null;
/*  677 */       int i = paramString3.lastIndexOf('?');
/*  678 */       if (i != -1) {
/*  679 */         this.query = paramString3.substring(i + 1);
/*  680 */         this.path = paramString3.substring(0, i);
/*      */       } else {
/*  682 */         this.path = paramString3;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void set(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
/*      */   {
/*  704 */     synchronized (this) {
/*  705 */       this.protocol = paramString1;
/*  706 */       this.host = paramString2;
/*  707 */       this.port = paramInt;
/*  708 */       this.file = (paramString5 + "?" + paramString6);
/*  709 */       this.userInfo = paramString4;
/*  710 */       this.path = paramString5;
/*  711 */       this.ref = paramString7;
/*      */       
/*      */ 
/*  714 */       this.hashCode = -1;
/*  715 */       this.hostAddress = null;
/*  716 */       this.query = paramString6;
/*  717 */       this.authority = paramString3;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getQuery()
/*      */   {
/*  729 */     return this.query;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPath()
/*      */   {
/*  740 */     return this.path;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getUserInfo()
/*      */   {
/*  751 */     return this.userInfo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAuthority()
/*      */   {
/*  761 */     return this.authority;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getPort()
/*      */   {
/*  770 */     return this.port;
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
/*      */   public int getDefaultPort()
/*      */   {
/*  783 */     return this.handler.getDefaultPort();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getProtocol()
/*      */   {
/*  792 */     return this.protocol;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHost()
/*      */   {
/*  804 */     return this.host;
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
/*      */   public String getFile()
/*      */   {
/*  819 */     return this.file;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getRef()
/*      */   {
/*  830 */     return this.ref;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  859 */     if (!(paramObject instanceof URL))
/*  860 */       return false;
/*  861 */     URL localURL = (URL)paramObject;
/*      */     
/*  863 */     return this.handler.equals(this, localURL);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int hashCode()
/*      */   {
/*  875 */     if (this.hashCode != -1) {
/*  876 */       return this.hashCode;
/*      */     }
/*  878 */     this.hashCode = this.handler.hashCode(this);
/*  879 */     return this.hashCode;
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
/*      */   public boolean sameFile(URL paramURL)
/*      */   {
/*  894 */     return this.handler.sameFile(this, paramURL);
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
/*      */   public String toString()
/*      */   {
/*  908 */     return toExternalForm();
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
/*      */   public String toExternalForm()
/*      */   {
/*  922 */     return this.handler.toExternalForm(this);
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
/*      */   public URI toURI()
/*      */     throws URISyntaxException
/*      */   {
/*  939 */     return new URI(toString());
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
/*      */   public URLConnection openConnection()
/*      */     throws IOException
/*      */   {
/*  972 */     return this.handler.openConnection(this);
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
/*      */   public URLConnection openConnection(Proxy paramProxy)
/*      */     throws IOException
/*      */   {
/* 1006 */     if (paramProxy == null) {
/* 1007 */       throw new IllegalArgumentException("proxy can not be null");
/*      */     }
/*      */     
/*      */ 
/* 1011 */     ApplicationProxy localApplicationProxy = paramProxy == Proxy.NO_PROXY ? Proxy.NO_PROXY : ApplicationProxy.create(paramProxy);
/* 1012 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1013 */     if ((localApplicationProxy.type() != Proxy.Type.DIRECT) && (localSecurityManager != null)) {
/* 1014 */       InetSocketAddress localInetSocketAddress = (InetSocketAddress)localApplicationProxy.address();
/* 1015 */       if (localInetSocketAddress.isUnresolved()) {
/* 1016 */         localSecurityManager.checkConnect(localInetSocketAddress.getHostName(), localInetSocketAddress.getPort());
/*      */       } else
/* 1018 */         localSecurityManager.checkConnect(localInetSocketAddress.getAddress().getHostAddress(), localInetSocketAddress
/* 1019 */           .getPort());
/*      */     }
/* 1021 */     return this.handler.openConnection(this, localApplicationProxy);
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
/*      */   public final InputStream openStream()
/*      */     throws IOException
/*      */   {
/* 1038 */     return openConnection().getInputStream();
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
/*      */   public final Object getContent()
/*      */     throws IOException
/*      */   {
/* 1052 */     return openConnection().getContent();
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
/*      */   public final Object getContent(Class[] paramArrayOfClass)
/*      */     throws IOException
/*      */   {
/* 1071 */     return openConnection().getContent(paramArrayOfClass);
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
/*      */   public static void setURLStreamHandlerFactory(URLStreamHandlerFactory paramURLStreamHandlerFactory)
/*      */   {
/* 1103 */     synchronized (streamHandlerLock) {
/* 1104 */       if (factory != null) {
/* 1105 */         throw new Error("factory already defined");
/*      */       }
/* 1107 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 1108 */       if (localSecurityManager != null) {
/* 1109 */         localSecurityManager.checkSetFactory();
/*      */       }
/* 1111 */       handlers.clear();
/* 1112 */       factory = paramURLStreamHandlerFactory;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1119 */   static Hashtable<String, URLStreamHandler> handlers = new Hashtable();
/* 1120 */   private static Object streamHandlerLock = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static URLStreamHandler getURLStreamHandler(String paramString)
/*      */   {
/* 1128 */     Object localObject1 = (URLStreamHandler)handlers.get(paramString);
/* 1129 */     if (localObject1 == null)
/*      */     {
/* 1131 */       int i = 0;
/*      */       
/*      */ 
/* 1134 */       if (factory != null) {
/* 1135 */         localObject1 = factory.createURLStreamHandler(paramString);
/* 1136 */         i = 1;
/*      */       }
/*      */       
/*      */       Object localObject2;
/* 1140 */       if (localObject1 == null) {
/* 1141 */         String str1 = null;
/*      */         
/*      */ 
/* 1144 */         str1 = (String)AccessController.doPrivileged(new GetPropertyAction("java.protocol.handler.pkgs", ""));
/*      */         
/*      */ 
/* 1147 */         if (str1 != "") {
/* 1148 */           str1 = str1 + "|";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1153 */         str1 = str1 + "sun.net.www.protocol";
/*      */         
/* 1155 */         localObject2 = new StringTokenizer(str1, "|");
/*      */         
/*      */ 
/* 1158 */         while ((localObject1 == null) && 
/* 1159 */           (((StringTokenizer)localObject2).hasMoreTokens()))
/*      */         {
/*      */ 
/* 1162 */           String str2 = ((StringTokenizer)localObject2).nextToken().trim();
/*      */           try {
/* 1164 */             String str3 = str2 + "." + paramString + ".Handler";
/*      */             
/* 1166 */             Class localClass = null;
/*      */             try {
/* 1168 */               localClass = Class.forName(str3);
/*      */             } catch (ClassNotFoundException localClassNotFoundException) {
/* 1170 */               ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
/* 1171 */               if (localClassLoader != null) {
/* 1172 */                 localClass = localClassLoader.loadClass(str3);
/*      */               }
/*      */             }
/* 1175 */             if (localClass != null)
/*      */             {
/* 1177 */               localObject1 = (URLStreamHandler)localClass.newInstance();
/*      */             }
/*      */           }
/*      */           catch (Exception localException) {}
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1185 */       synchronized (streamHandlerLock)
/*      */       {
/* 1187 */         localObject2 = null;
/*      */         
/*      */ 
/*      */ 
/* 1191 */         localObject2 = (URLStreamHandler)handlers.get(paramString);
/*      */         
/* 1193 */         if (localObject2 != null) {
/* 1194 */           return (URLStreamHandler)localObject2;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1199 */         if ((i == 0) && (factory != null)) {
/* 1200 */           localObject2 = factory.createURLStreamHandler(paramString);
/*      */         }
/*      */         
/* 1203 */         if (localObject2 != null)
/*      */         {
/*      */ 
/*      */ 
/* 1207 */           localObject1 = localObject2;
/*      */         }
/*      */         
/*      */ 
/* 1211 */         if (localObject1 != null) {
/* 1212 */           handlers.put(paramString, localObject1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1218 */     return (URLStreamHandler)localObject1;
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
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1235 */     paramObjectOutputStream.defaultWriteObject();
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
/* 1246 */     paramObjectInputStream.defaultReadObject();
/* 1247 */     if ((this.handler = getURLStreamHandler(this.protocol)) == null) {
/* 1248 */       throw new IOException("unknown protocol: " + this.protocol);
/*      */     }
/*      */     
/*      */     int i;
/* 1252 */     if ((this.authority == null) && (((this.host != null) && 
/* 1253 */       (this.host.length() > 0)) || (this.port != -1))) {
/* 1254 */       if (this.host == null)
/* 1255 */         this.host = "";
/* 1256 */       this.authority = (this.host + ":" + this.port);
/*      */       
/*      */ 
/* 1259 */       i = this.host.lastIndexOf('@');
/* 1260 */       if (i != -1) {
/* 1261 */         this.userInfo = this.host.substring(0, i);
/* 1262 */         this.host = this.host.substring(i + 1);
/*      */       }
/* 1264 */     } else if (this.authority != null)
/*      */     {
/* 1266 */       i = this.authority.indexOf('@');
/* 1267 */       if (i != -1) {
/* 1268 */         this.userInfo = this.authority.substring(0, i);
/*      */       }
/*      */     }
/*      */     
/* 1272 */     this.path = null;
/* 1273 */     this.query = null;
/* 1274 */     if (this.file != null)
/*      */     {
/* 1276 */       i = this.file.lastIndexOf('?');
/* 1277 */       if (i != -1) {
/* 1278 */         this.query = this.file.substring(i + 1);
/* 1279 */         this.path = this.file.substring(0, i);
/*      */       } else {
/* 1281 */         this.path = this.file;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/URL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */