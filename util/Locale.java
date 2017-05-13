/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.spi.LocaleNameProvider;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.util.locale.BaseLocale;
/*      */ import sun.util.locale.InternalLocaleBuilder;
/*      */ import sun.util.locale.LanguageTag;
/*      */ import sun.util.locale.LocaleExtensions;
/*      */ import sun.util.locale.LocaleMatcher;
/*      */ import sun.util.locale.LocaleObjectCache;
/*      */ import sun.util.locale.LocaleSyntaxException;
/*      */ import sun.util.locale.LocaleUtils;
/*      */ import sun.util.locale.ParseStatus;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*      */ import sun.util.locale.provider.LocaleServiceProviderPool.LocalizedObjectGetter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Locale
/*      */   implements Cloneable, Serializable
/*      */ {
/*  486 */   private static final Cache LOCALECACHE = new Cache(null);
/*      */   
/*      */ 
/*      */ 
/*  490 */   public static final Locale ENGLISH = createConstant("en", "");
/*      */   
/*      */ 
/*      */ 
/*  494 */   public static final Locale FRENCH = createConstant("fr", "");
/*      */   
/*      */ 
/*      */ 
/*  498 */   public static final Locale GERMAN = createConstant("de", "");
/*      */   
/*      */ 
/*      */ 
/*  502 */   public static final Locale ITALIAN = createConstant("it", "");
/*      */   
/*      */ 
/*      */ 
/*  506 */   public static final Locale JAPANESE = createConstant("ja", "");
/*      */   
/*      */ 
/*      */ 
/*  510 */   public static final Locale KOREAN = createConstant("ko", "");
/*      */   
/*      */ 
/*      */ 
/*  514 */   public static final Locale CHINESE = createConstant("zh", "");
/*      */   
/*      */ 
/*      */ 
/*  518 */   public static final Locale SIMPLIFIED_CHINESE = createConstant("zh", "CN");
/*      */   
/*      */ 
/*      */ 
/*  522 */   public static final Locale TRADITIONAL_CHINESE = createConstant("zh", "TW");
/*      */   
/*      */ 
/*      */ 
/*  526 */   public static final Locale FRANCE = createConstant("fr", "FR");
/*      */   
/*      */ 
/*      */ 
/*  530 */   public static final Locale GERMANY = createConstant("de", "DE");
/*      */   
/*      */ 
/*      */ 
/*  534 */   public static final Locale ITALY = createConstant("it", "IT");
/*      */   
/*      */ 
/*      */ 
/*  538 */   public static final Locale JAPAN = createConstant("ja", "JP");
/*      */   
/*      */ 
/*      */ 
/*  542 */   public static final Locale KOREA = createConstant("ko", "KR");
/*      */   
/*      */ 
/*      */ 
/*  546 */   public static final Locale CHINA = SIMPLIFIED_CHINESE;
/*      */   
/*      */ 
/*      */ 
/*  550 */   public static final Locale PRC = SIMPLIFIED_CHINESE;
/*      */   
/*      */ 
/*      */ 
/*  554 */   public static final Locale TAIWAN = TRADITIONAL_CHINESE;
/*      */   
/*      */ 
/*      */ 
/*  558 */   public static final Locale UK = createConstant("en", "GB");
/*      */   
/*      */ 
/*      */ 
/*  562 */   public static final Locale US = createConstant("en", "US");
/*      */   
/*      */ 
/*      */ 
/*  566 */   public static final Locale CANADA = createConstant("en", "CA");
/*      */   
/*      */ 
/*      */ 
/*  570 */   public static final Locale CANADA_FRENCH = createConstant("fr", "CA");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  580 */   public static final Locale ROOT = createConstant("", "");
/*      */   
/*      */ 
/*      */ 
/*      */   public static final char PRIVATE_USE_EXTENSION = 'x';
/*      */   
/*      */ 
/*      */ 
/*      */   public static final char UNICODE_LOCALE_EXTENSION = 'u';
/*      */   
/*      */ 
/*      */ 
/*      */   static final long serialVersionUID = 9149081749638150636L;
/*      */   
/*      */ 
/*      */   private static final int DISPLAY_LANGUAGE = 0;
/*      */   
/*      */ 
/*      */   private static final int DISPLAY_COUNTRY = 1;
/*      */   
/*      */ 
/*      */   private static final int DISPLAY_VARIANT = 2;
/*      */   
/*      */ 
/*      */   private static final int DISPLAY_SCRIPT = 3;
/*      */   
/*      */ 
/*      */   private transient BaseLocale baseLocale;
/*      */   
/*      */ 
/*      */   private transient LocaleExtensions localeExtensions;
/*      */   
/*      */ 
/*      */ 
/*      */   private Locale(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions)
/*      */   {
/*  616 */     this.baseLocale = paramBaseLocale;
/*  617 */     this.localeExtensions = paramLocaleExtensions;
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
/*      */   public Locale(String paramString1, String paramString2, String paramString3)
/*      */   {
/*  647 */     if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null)) {
/*  648 */       throw new NullPointerException();
/*      */     }
/*  650 */     this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(paramString1), "", paramString2, paramString3);
/*  651 */     this.localeExtensions = getCompatibilityExtensions(paramString1, "", paramString2, paramString3);
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
/*      */   public Locale(String paramString1, String paramString2)
/*      */   {
/*  677 */     this(paramString1, paramString2, "");
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
/*      */   public Locale(String paramString)
/*      */   {
/*  701 */     this(paramString, "", "");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Locale createConstant(String paramString1, String paramString2)
/*      */   {
/*  709 */     BaseLocale localBaseLocale = BaseLocale.createInstance(paramString1, paramString2);
/*  710 */     return getInstance(localBaseLocale, null);
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
/*      */   static Locale getInstance(String paramString1, String paramString2, String paramString3)
/*      */   {
/*  728 */     return getInstance(paramString1, "", paramString2, paramString3, null);
/*      */   }
/*      */   
/*      */   static Locale getInstance(String paramString1, String paramString2, String paramString3, String paramString4, LocaleExtensions paramLocaleExtensions)
/*      */   {
/*  733 */     if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null) || (paramString4 == null)) {
/*  734 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  737 */     if (paramLocaleExtensions == null) {
/*  738 */       paramLocaleExtensions = getCompatibilityExtensions(paramString1, paramString2, paramString3, paramString4);
/*      */     }
/*      */     
/*  741 */     BaseLocale localBaseLocale = BaseLocale.getInstance(paramString1, paramString2, paramString3, paramString4);
/*  742 */     return getInstance(localBaseLocale, paramLocaleExtensions);
/*      */   }
/*      */   
/*      */   static Locale getInstance(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions) {
/*  746 */     LocaleKey localLocaleKey = new LocaleKey(paramBaseLocale, paramLocaleExtensions, null);
/*  747 */     return (Locale)LOCALECACHE.get(localLocaleKey);
/*      */   }
/*      */   
/*      */ 
/*      */   private static class Cache
/*      */     extends LocaleObjectCache<Locale.LocaleKey, Locale>
/*      */   {
/*      */     protected Locale createObject(Locale.LocaleKey paramLocaleKey)
/*      */     {
/*  756 */       return new Locale(paramLocaleKey.base, paramLocaleKey.exts, null);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class LocaleKey {
/*      */     private final BaseLocale base;
/*      */     private final LocaleExtensions exts;
/*      */     private final int hash;
/*      */     
/*      */     private LocaleKey(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions) {
/*  766 */       this.base = paramBaseLocale;
/*  767 */       this.exts = paramLocaleExtensions;
/*      */       
/*      */ 
/*  770 */       int i = this.base.hashCode();
/*  771 */       if (this.exts != null) {
/*  772 */         i ^= this.exts.hashCode();
/*      */       }
/*  774 */       this.hash = i;
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*  779 */       if (this == paramObject) {
/*  780 */         return true;
/*      */       }
/*  782 */       if (!(paramObject instanceof LocaleKey)) {
/*  783 */         return false;
/*      */       }
/*  785 */       LocaleKey localLocaleKey = (LocaleKey)paramObject;
/*  786 */       if ((this.hash != localLocaleKey.hash) || (!this.base.equals(localLocaleKey.base))) {
/*  787 */         return false;
/*      */       }
/*  789 */       if (this.exts == null) {
/*  790 */         return localLocaleKey.exts == null;
/*      */       }
/*  792 */       return this.exts.equals(localLocaleKey.exts);
/*      */     }
/*      */     
/*      */     public int hashCode()
/*      */     {
/*  797 */       return this.hash;
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
/*      */   public static Locale getDefault()
/*      */   {
/*  815 */     return defaultLocale;
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
/*      */   public static Locale getDefault(Category paramCategory)
/*      */   {
/*  836 */     switch (paramCategory) {
/*      */     case DISPLAY: 
/*  838 */       if (defaultDisplayLocale == null) {
/*  839 */         synchronized (Locale.class) {
/*  840 */           if (defaultDisplayLocale == null) {
/*  841 */             defaultDisplayLocale = initDefault(paramCategory);
/*      */           }
/*      */         }
/*      */       }
/*  845 */       return defaultDisplayLocale;
/*      */     case FORMAT: 
/*  847 */       if (defaultFormatLocale == null) {
/*  848 */         synchronized (Locale.class) {
/*  849 */           if (defaultFormatLocale == null) {
/*  850 */             defaultFormatLocale = initDefault(paramCategory);
/*      */           }
/*      */         }
/*      */       }
/*  854 */       return defaultFormatLocale;
/*      */     }
/*  856 */     if (!$assertionsDisabled) { throw new AssertionError("Unknown Category");
/*      */     }
/*  858 */     return getDefault();
/*      */   }
/*      */   
/*      */   private static Locale initDefault()
/*      */   {
/*  863 */     String str1 = (String)AccessController.doPrivileged(new GetPropertyAction("user.language", "en"));
/*      */     
/*      */ 
/*  866 */     String str2 = (String)AccessController.doPrivileged(new GetPropertyAction("user.region"));
/*      */     String str4;
/*  868 */     String str5; String str3; if (str2 != null)
/*      */     {
/*  870 */       int i = str2.indexOf('_');
/*  871 */       if (i >= 0) {
/*  872 */         str4 = str2.substring(0, i);
/*  873 */         str5 = str2.substring(i + 1);
/*      */       } else {
/*  875 */         str4 = str2;
/*  876 */         str5 = "";
/*      */       }
/*  878 */       str3 = "";
/*      */     } else {
/*  880 */       str3 = (String)AccessController.doPrivileged(new GetPropertyAction("user.script", ""));
/*      */       
/*  882 */       str4 = (String)AccessController.doPrivileged(new GetPropertyAction("user.country", ""));
/*      */       
/*  884 */       str5 = (String)AccessController.doPrivileged(new GetPropertyAction("user.variant", ""));
/*      */     }
/*      */     
/*      */ 
/*  888 */     return getInstance(str1, str3, str4, str5, null);
/*      */   }
/*      */   
/*      */   private static Locale initDefault(Category paramCategory) {
/*  892 */     return getInstance(
/*  893 */       (String)AccessController.doPrivileged(new GetPropertyAction(paramCategory.languageKey, defaultLocale
/*  894 */       .getLanguage())), 
/*  895 */       (String)AccessController.doPrivileged(new GetPropertyAction(paramCategory.scriptKey, defaultLocale
/*  896 */       .getScript())), 
/*  897 */       (String)AccessController.doPrivileged(new GetPropertyAction(paramCategory.countryKey, defaultLocale
/*  898 */       .getCountry())), 
/*  899 */       (String)AccessController.doPrivileged(new GetPropertyAction(paramCategory.variantKey, defaultLocale
/*  900 */       .getVariant())), null);
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
/*      */   public static synchronized void setDefault(Locale paramLocale)
/*      */   {
/*  933 */     setDefault(Category.DISPLAY, paramLocale);
/*  934 */     setDefault(Category.FORMAT, paramLocale);
/*  935 */     defaultLocale = paramLocale;
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
/*      */   public static synchronized void setDefault(Category paramCategory, Locale paramLocale)
/*      */   {
/*  968 */     if (paramCategory == null)
/*  969 */       throw new NullPointerException("Category cannot be NULL");
/*  970 */     if (paramLocale == null) {
/*  971 */       throw new NullPointerException("Can't set default locale to NULL");
/*      */     }
/*  973 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  974 */     if (localSecurityManager != null) { localSecurityManager.checkPermission(new PropertyPermission("user.language", "write"));
/*      */     }
/*  976 */     switch (paramCategory) {
/*      */     case DISPLAY: 
/*  978 */       defaultDisplayLocale = paramLocale;
/*  979 */       break;
/*      */     case FORMAT: 
/*  981 */       defaultFormatLocale = paramLocale;
/*  982 */       break;
/*      */     default: 
/*  984 */       if (!$assertionsDisabled) { throw new AssertionError("Unknown Category");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Locale[] getAvailableLocales()
/*      */   {
/*  999 */     return LocaleServiceProviderPool.getAllAvailableLocales();
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
/*      */   public static String[] getISOCountries()
/*      */   {
/* 1014 */     if (isoCountries == null) {
/* 1015 */       isoCountries = getISO2Table("ADANDAEAREAFAFGAGATGAIAIAALALBAMARMANANTAOAGOAQATAARARGASASMATAUTAUAUSAWABWAXALAAZAZEBABIHBBBRBBDBGDBEBELBFBFABGBGRBHBHRBIBDIBJBENBLBLMBMBMUBNBRNBOBOLBQBESBRBRABSBHSBTBTNBVBVTBWBWABYBLRBZBLZCACANCCCCKCDCODCFCAFCGCOGCHCHECICIVCKCOKCLCHLCMCMRCNCHNCOCOLCRCRICUCUBCVCPVCWCUWCXCXRCYCYPCZCZEDEDEUDJDJIDKDNKDMDMADODOMDZDZAECECUEEESTEGEGYEHESHERERIESESPETETHFIFINFJFJIFKFLKFMFSMFOFROFRFRAGAGABGBGBRGDGRDGEGEOGFGUFGGGGYGHGHAGIGIBGLGRLGMGMBGNGINGPGLPGQGNQGRGRCGSSGSGTGTMGUGUMGWGNBGYGUYHKHKGHMHMDHNHNDHRHRVHTHTIHUHUNIDIDNIEIRLILISRIMIMNININDIOIOTIQIRQIRIRNISISLITITAJEJEYJMJAMJOJORJPJPNKEKENKGKGZKHKHMKIKIRKMCOMKNKNAKPPRKKRKORKWKWTKYCYMKZKAZLALAOLBLBNLCLCALILIELKLKALRLBRLSLSOLTLTULULUXLVLVALYLBYMAMARMCMCOMDMDAMEMNEMFMAFMGMDGMHMHLMKMKDMLMLIMMMMRMNMNGMOMACMPMNPMQMTQMRMRTMSMSRMTMLTMUMUSMVMDVMWMWIMXMEXMYMYSMZMOZNANAMNCNCLNENERNFNFKNGNGANINICNLNLDNONORNPNPLNRNRUNUNIUNZNZLOMOMNPAPANPEPERPFPYFPGPNGPHPHLPKPAKPLPOLPMSPMPNPCNPRPRIPSPSEPTPRTPWPLWPYPRYQAQATREREUROROURSSRBRURUSRWRWASASAUSBSLBSCSYCSDSDNSESWESGSGPSHSHNSISVNSJSJMSKSVKSLSLESMSMRSNSENSOSOMSRSURSSSSDSTSTPSVSLVSXSXMSYSYRSZSWZTCTCATDTCDTFATFTGTGOTHTHATJTJKTKTKLTLTLSTMTKMTNTUNTOTONTRTURTTTTOTVTUVTWTWNTZTZAUAUKRUGUGAUMUMIUSUSAUYURYUZUZBVAVATVCVCTVEVENVGVGBVIVIRVNVNMVUVUTWFWLFWSWSMYEYEMYTMYTZAZAFZMZMBZWZWE");
/*      */     }
/* 1017 */     String[] arrayOfString = new String[isoCountries.length];
/* 1018 */     System.arraycopy(isoCountries, 0, arrayOfString, 0, isoCountries.length);
/* 1019 */     return arrayOfString;
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
/*      */   public static String[] getISOLanguages()
/*      */   {
/* 1039 */     if (isoLanguages == null) {
/* 1040 */       isoLanguages = getISO2Table("aaaarababkaeaveafafrakakaamamhanargararaasasmavavaayaymazazebabakbebelbgbulbhbihbibisbmbambnbenbobodbrbrebsboscacatcechechchacocoscrcrecscescuchucvchvcycymdadandedeudvdivdzdzoeeeweelellenengeoepoesspaetesteueusfafasfffulfifinfjfijfofaofrfrafyfrygaglegdglaglglggngrngugujgvglvhahauhehebhihinhohmohrhrvhthathuhunhyhyehzheriainaidindieileigiboiiiiiikipkinindioidoisislititaiuikuiwhebjajpnjiyidjvjavkakatkgkonkikikkjkuakkkazklkalkmkhmknkankokorkrkaukskaskukurkvkomkwcorkykirlalatlbltzlgluglilimlnlinlolaoltlitlulublvlavmgmlgmhmahmimrimkmkdmlmalmnmonmomolmrmarmsmsamtmltmymyananaunbnobndndenenepngndonlnldnnnnononornrnblnvnavnynyaocociojojiomormororiososspapanpipliplpolpspusptporququermrohrnrunroronrurusrwkinsasanscsrdsdsndsesmesgsagsisinskslkslslvsmsmosnsnasosomsqsqisrsrpsssswstsotsusunsvsweswswatatamteteltgtgkththatitirtktuktltgltntsntotontrturtstsotttattwtwitytahuguigukukrururduzuzbvevenvivievovolwawlnwowolxhxhoyiyidyoyorzazhazhzhozuzul");
/*      */     }
/* 1042 */     String[] arrayOfString = new String[isoLanguages.length];
/* 1043 */     System.arraycopy(isoLanguages, 0, arrayOfString, 0, isoLanguages.length);
/* 1044 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   private static String[] getISO2Table(String paramString) {
/* 1048 */     int i = paramString.length() / 5;
/* 1049 */     String[] arrayOfString = new String[i];
/* 1050 */     int j = 0; for (int k = 0; j < i; k += 5) {
/* 1051 */       arrayOfString[j] = paramString.substring(k, k + 2);j++;
/*      */     }
/* 1053 */     return arrayOfString;
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
/*      */   public String getLanguage()
/*      */   {
/* 1076 */     return this.baseLocale.getLanguage();
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
/*      */   public String getScript()
/*      */   {
/* 1090 */     return this.baseLocale.getScript();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getCountry()
/*      */   {
/* 1102 */     return this.baseLocale.getRegion();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getVariant()
/*      */   {
/* 1112 */     return this.baseLocale.getVariant();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasExtensions()
/*      */   {
/* 1123 */     return this.localeExtensions != null;
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
/*      */   public Locale stripExtensions()
/*      */   {
/* 1136 */     return hasExtensions() ? getInstance(this.baseLocale, null) : this;
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
/*      */   public String getExtension(char paramChar)
/*      */   {
/* 1155 */     if (!LocaleExtensions.isValidKey(paramChar)) {
/* 1156 */       throw new IllegalArgumentException("Ill-formed extension key: " + paramChar);
/*      */     }
/* 1158 */     return hasExtensions() ? this.localeExtensions.getExtensionValue(Character.valueOf(paramChar)) : null;
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
/*      */   public Set<Character> getExtensionKeys()
/*      */   {
/* 1171 */     if (!hasExtensions()) {
/* 1172 */       return Collections.emptySet();
/*      */     }
/* 1174 */     return this.localeExtensions.getKeys();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<String> getUnicodeLocaleAttributes()
/*      */   {
/* 1186 */     if (!hasExtensions()) {
/* 1187 */       return Collections.emptySet();
/*      */     }
/* 1189 */     return this.localeExtensions.getUnicodeLocaleAttributes();
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
/*      */   public String getUnicodeLocaleType(String paramString)
/*      */   {
/* 1207 */     if (!isUnicodeExtensionKey(paramString)) {
/* 1208 */       throw new IllegalArgumentException("Ill-formed Unicode locale key: " + paramString);
/*      */     }
/* 1210 */     return hasExtensions() ? this.localeExtensions.getUnicodeLocaleType(paramString) : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<String> getUnicodeLocaleKeys()
/*      */   {
/* 1222 */     if (this.localeExtensions == null) {
/* 1223 */       return Collections.emptySet();
/*      */     }
/* 1225 */     return this.localeExtensions.getUnicodeLocaleKeys();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   BaseLocale getBaseLocale()
/*      */   {
/* 1234 */     return this.baseLocale;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   LocaleExtensions getLocaleExtensions()
/*      */   {
/* 1244 */     return this.localeExtensions;
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
/*      */   public final String toString()
/*      */   {
/* 1291 */     int i = this.baseLocale.getLanguage().length() != 0 ? 1 : 0;
/* 1292 */     int j = this.baseLocale.getScript().length() != 0 ? 1 : 0;
/* 1293 */     int k = this.baseLocale.getRegion().length() != 0 ? 1 : 0;
/* 1294 */     int m = this.baseLocale.getVariant().length() != 0 ? 1 : 0;
/* 1295 */     int n = (this.localeExtensions != null) && (this.localeExtensions.getID().length() != 0) ? 1 : 0;
/*      */     
/* 1297 */     StringBuilder localStringBuilder = new StringBuilder(this.baseLocale.getLanguage());
/* 1298 */     if ((k != 0) || ((i != 0) && ((m != 0) || (j != 0) || (n != 0))))
/*      */     {
/* 1300 */       localStringBuilder.append('_').append(this.baseLocale.getRegion());
/*      */     }
/* 1302 */     if ((m != 0) && ((i != 0) || (k != 0)))
/*      */     {
/* 1304 */       localStringBuilder.append('_').append(this.baseLocale.getVariant());
/*      */     }
/*      */     
/* 1307 */     if ((j != 0) && ((i != 0) || (k != 0)))
/*      */     {
/* 1309 */       localStringBuilder.append("_#").append(this.baseLocale.getScript());
/*      */     }
/*      */     
/* 1312 */     if ((n != 0) && ((i != 0) || (k != 0))) {
/* 1313 */       localStringBuilder.append('_');
/* 1314 */       if (j == 0) {
/* 1315 */         localStringBuilder.append('#');
/*      */       }
/* 1317 */       localStringBuilder.append(this.localeExtensions.getID());
/*      */     }
/*      */     
/* 1320 */     return localStringBuilder.toString();
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
/*      */   public String toLanguageTag()
/*      */   {
/* 1391 */     if (this.languageTag != null) {
/* 1392 */       return this.languageTag;
/*      */     }
/*      */     
/* 1395 */     LanguageTag localLanguageTag = LanguageTag.parseLocale(this.baseLocale, this.localeExtensions);
/* 1396 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/* 1398 */     String str1 = localLanguageTag.getLanguage();
/* 1399 */     if (str1.length() > 0) {
/* 1400 */       localStringBuilder.append(LanguageTag.canonicalizeLanguage(str1));
/*      */     }
/*      */     
/* 1403 */     str1 = localLanguageTag.getScript();
/* 1404 */     if (str1.length() > 0) {
/* 1405 */       localStringBuilder.append("-");
/* 1406 */       localStringBuilder.append(LanguageTag.canonicalizeScript(str1));
/*      */     }
/*      */     
/* 1409 */     str1 = localLanguageTag.getRegion();
/* 1410 */     if (str1.length() > 0) {
/* 1411 */       localStringBuilder.append("-");
/* 1412 */       localStringBuilder.append(LanguageTag.canonicalizeRegion(str1));
/*      */     }
/*      */     
/* 1415 */     List localList = localLanguageTag.getVariants();
/* 1416 */     for (Object localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();) { str2 = (String)((Iterator)localObject1).next();
/* 1417 */       localStringBuilder.append("-");
/*      */       
/* 1419 */       localStringBuilder.append(str2);
/*      */     }
/*      */     String str2;
/* 1422 */     localList = localLanguageTag.getExtensions();
/* 1423 */     for (localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();) { str2 = (String)((Iterator)localObject1).next();
/* 1424 */       localStringBuilder.append("-");
/* 1425 */       localStringBuilder.append(LanguageTag.canonicalizeExtension(str2));
/*      */     }
/*      */     
/* 1428 */     str1 = localLanguageTag.getPrivateuse();
/* 1429 */     if (str1.length() > 0) {
/* 1430 */       if (localStringBuilder.length() > 0) {
/* 1431 */         localStringBuilder.append("-");
/*      */       }
/* 1433 */       localStringBuilder.append("x").append("-");
/*      */       
/* 1435 */       localStringBuilder.append(str1);
/*      */     }
/*      */     
/* 1438 */     localObject1 = localStringBuilder.toString();
/* 1439 */     synchronized (this) {
/* 1440 */       if (this.languageTag == null) {
/* 1441 */         this.languageTag = ((String)localObject1);
/*      */       }
/*      */     }
/* 1444 */     return this.languageTag;
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
/*      */   public static Locale forLanguageTag(String paramString)
/*      */   {
/* 1568 */     LanguageTag localLanguageTag = LanguageTag.parse(paramString, null);
/* 1569 */     InternalLocaleBuilder localInternalLocaleBuilder = new InternalLocaleBuilder();
/* 1570 */     localInternalLocaleBuilder.setLanguageTag(localLanguageTag);
/* 1571 */     BaseLocale localBaseLocale = localInternalLocaleBuilder.getBaseLocale();
/* 1572 */     LocaleExtensions localLocaleExtensions = localInternalLocaleBuilder.getLocaleExtensions();
/* 1573 */     if ((localLocaleExtensions == null) && (localBaseLocale.getVariant().length() > 0)) {
/* 1574 */       localLocaleExtensions = getCompatibilityExtensions(localBaseLocale.getLanguage(), localBaseLocale.getScript(), localBaseLocale
/* 1575 */         .getRegion(), localBaseLocale.getVariant());
/*      */     }
/* 1577 */     return getInstance(localBaseLocale, localLocaleExtensions);
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
/*      */   public String getISO3Language()
/*      */     throws MissingResourceException
/*      */   {
/* 1595 */     String str1 = this.baseLocale.getLanguage();
/* 1596 */     if (str1.length() == 3) {
/* 1597 */       return str1;
/*      */     }
/*      */     
/* 1600 */     String str2 = getISO3Code(str1, "aaaarababkaeaveafafrakakaamamhanargararaasasmavavaayaymazazebabakbebelbgbulbhbihbibisbmbambnbenbobodbrbrebsboscacatcechechchacocoscrcrecscescuchucvchvcycymdadandedeudvdivdzdzoeeeweelellenengeoepoesspaetesteueusfafasfffulfifinfjfijfofaofrfrafyfrygaglegdglaglglggngrngugujgvglvhahauhehebhihinhohmohrhrvhthathuhunhyhyehzheriainaidindieileigiboiiiiiikipkinindioidoisislititaiuikuiwhebjajpnjiyidjvjavkakatkgkonkikikkjkuakkkazklkalkmkhmknkankokorkrkaukskaskukurkvkomkwcorkykirlalatlbltzlgluglilimlnlinlolaoltlitlulublvlavmgmlgmhmahmimrimkmkdmlmalmnmonmomolmrmarmsmsamtmltmymyananaunbnobndndenenepngndonlnldnnnnononornrnblnvnavnynyaocociojojiomormororiososspapanpipliplpolpspusptporququermrohrnrunroronrurusrwkinsasanscsrdsdsndsesmesgsagsisinskslkslslvsmsmosnsnasosomsqsqisrsrpsssswstsotsusunsvsweswswatatamteteltgtgkththatitirtktuktltgltntsntotontrturtstsotttattwtwitytahuguigukukrururduzuzbvevenvivievovolwawlnwowolxhxhoyiyidyoyorzazhazhzhozuzul");
/* 1601 */     if (str2 == null)
/*      */     {
/* 1603 */       throw new MissingResourceException("Couldn't find 3-letter language code for " + str1, "FormatData_" + toString(), "ShortLanguage");
/*      */     }
/* 1605 */     return str2;
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
/*      */   public String getISO3Country()
/*      */     throws MissingResourceException
/*      */   {
/* 1622 */     String str = getISO3Code(this.baseLocale.getRegion(), "ADANDAEAREAFAFGAGATGAIAIAALALBAMARMANANTAOAGOAQATAARARGASASMATAUTAUAUSAWABWAXALAAZAZEBABIHBBBRBBDBGDBEBELBFBFABGBGRBHBHRBIBDIBJBENBLBLMBMBMUBNBRNBOBOLBQBESBRBRABSBHSBTBTNBVBVTBWBWABYBLRBZBLZCACANCCCCKCDCODCFCAFCGCOGCHCHECICIVCKCOKCLCHLCMCMRCNCHNCOCOLCRCRICUCUBCVCPVCWCUWCXCXRCYCYPCZCZEDEDEUDJDJIDKDNKDMDMADODOMDZDZAECECUEEESTEGEGYEHESHERERIESESPETETHFIFINFJFJIFKFLKFMFSMFOFROFRFRAGAGABGBGBRGDGRDGEGEOGFGUFGGGGYGHGHAGIGIBGLGRLGMGMBGNGINGPGLPGQGNQGRGRCGSSGSGTGTMGUGUMGWGNBGYGUYHKHKGHMHMDHNHNDHRHRVHTHTIHUHUNIDIDNIEIRLILISRIMIMNININDIOIOTIQIRQIRIRNISISLITITAJEJEYJMJAMJOJORJPJPNKEKENKGKGZKHKHMKIKIRKMCOMKNKNAKPPRKKRKORKWKWTKYCYMKZKAZLALAOLBLBNLCLCALILIELKLKALRLBRLSLSOLTLTULULUXLVLVALYLBYMAMARMCMCOMDMDAMEMNEMFMAFMGMDGMHMHLMKMKDMLMLIMMMMRMNMNGMOMACMPMNPMQMTQMRMRTMSMSRMTMLTMUMUSMVMDVMWMWIMXMEXMYMYSMZMOZNANAMNCNCLNENERNFNFKNGNGANINICNLNLDNONORNPNPLNRNRUNUNIUNZNZLOMOMNPAPANPEPERPFPYFPGPNGPHPHLPKPAKPLPOLPMSPMPNPCNPRPRIPSPSEPTPRTPWPLWPYPRYQAQATREREUROROURSSRBRURUSRWRWASASAUSBSLBSCSYCSDSDNSESWESGSGPSHSHNSISVNSJSJMSKSVKSLSLESMSMRSNSENSOSOMSRSURSSSSDSTSTPSVSLVSXSXMSYSYRSZSWZTCTCATDTCDTFATFTGTGOTHTHATJTJKTKTKLTLTLSTMTKMTNTUNTOTONTRTURTTTTOTVTUVTWTWNTZTZAUAUKRUGUGAUMUMIUSUSAUYURYUZUZBVAVATVCVCTVEVENVGVGBVIVIRVNVNMVUVUTWFWLFWSWSMYEYEMYTMYTZAZAFZMZMBZWZWE");
/* 1623 */     if (str == null)
/*      */     {
/* 1625 */       throw new MissingResourceException("Couldn't find 3-letter country code for " + this.baseLocale.getRegion(), "FormatData_" + toString(), "ShortCountry");
/*      */     }
/* 1627 */     return str;
/*      */   }
/*      */   
/*      */   private static String getISO3Code(String paramString1, String paramString2) {
/* 1631 */     int i = paramString1.length();
/* 1632 */     if (i == 0) {
/* 1633 */       return "";
/*      */     }
/*      */     
/* 1636 */     int j = paramString2.length();
/* 1637 */     int k = j;
/* 1638 */     if (i == 2) {
/* 1639 */       int m = paramString1.charAt(0);
/* 1640 */       int n = paramString1.charAt(1);
/* 1641 */       for (k = 0; k < j; k += 5) {
/* 1642 */         if ((paramString2.charAt(k) == m) && 
/* 1643 */           (paramString2.charAt(k + 1) == n)) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1648 */     return k < j ? paramString2.substring(k + 2, k + 5) : null;
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
/*      */   public final String getDisplayLanguage()
/*      */   {
/* 1670 */     return getDisplayLanguage(getDefault(Category.DISPLAY));
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
/*      */   public String getDisplayLanguage(Locale paramLocale)
/*      */   {
/* 1691 */     return getDisplayString(this.baseLocale.getLanguage(), paramLocale, 0);
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
/*      */   public String getDisplayScript()
/*      */   {
/* 1705 */     return getDisplayScript(getDefault(Category.DISPLAY));
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
/*      */   public String getDisplayScript(Locale paramLocale)
/*      */   {
/* 1721 */     return getDisplayString(this.baseLocale.getScript(), paramLocale, 3);
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
/*      */   public final String getDisplayCountry()
/*      */   {
/* 1743 */     return getDisplayCountry(getDefault(Category.DISPLAY));
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
/*      */   public String getDisplayCountry(Locale paramLocale)
/*      */   {
/* 1764 */     return getDisplayString(this.baseLocale.getRegion(), paramLocale, 1);
/*      */   }
/*      */   
/*      */   private String getDisplayString(String paramString, Locale paramLocale, int paramInt) {
/* 1768 */     if (paramString.length() == 0) {
/* 1769 */       return "";
/*      */     }
/*      */     
/* 1772 */     if (paramLocale == null) {
/* 1773 */       throw new NullPointerException();
/*      */     }
/*      */     
/*      */ 
/* 1777 */     LocaleServiceProviderPool localLocaleServiceProviderPool = LocaleServiceProviderPool.getPool(LocaleNameProvider.class);
/* 1778 */     String str1 = paramInt == 2 ? "%%" + paramString : paramString;
/* 1779 */     String str2 = (String)localLocaleServiceProviderPool.getLocalizedObject(
/* 1780 */       LocaleNameGetter.INSTANCE, paramLocale, str1, new Object[] {
/* 1781 */       Integer.valueOf(paramInt), paramString });
/* 1782 */     if (str2 != null) {
/* 1783 */       return str2;
/*      */     }
/*      */     
/* 1786 */     return paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final String getDisplayVariant()
/*      */   {
/* 1798 */     return getDisplayVariant(getDefault(Category.DISPLAY));
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
/*      */   public String getDisplayVariant(Locale paramLocale)
/*      */   {
/* 1811 */     if (this.baseLocale.getVariant().length() == 0) {
/* 1812 */       return "";
/*      */     }
/* 1814 */     LocaleResources localLocaleResources = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale);
/*      */     
/* 1816 */     String[] arrayOfString = getDisplayVariantArray(paramLocale);
/*      */     
/*      */ 
/*      */ 
/* 1820 */     return formatList(arrayOfString, localLocaleResources
/* 1821 */       .getLocaleName("ListPattern"), localLocaleResources
/* 1822 */       .getLocaleName("ListCompositionPattern"));
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
/*      */   public final String getDisplayName()
/*      */   {
/* 1845 */     return getDisplayName(getDefault(Category.DISPLAY));
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
/*      */   public String getDisplayName(Locale paramLocale)
/*      */   {
/* 1871 */     LocaleResources localLocaleResources = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale);
/*      */     
/* 1873 */     String str1 = getDisplayLanguage(paramLocale);
/* 1874 */     String str2 = getDisplayScript(paramLocale);
/* 1875 */     String str3 = getDisplayCountry(paramLocale);
/* 1876 */     String[] arrayOfString1 = getDisplayVariantArray(paramLocale);
/*      */     
/*      */ 
/* 1879 */     String str4 = localLocaleResources.getLocaleName("DisplayNamePattern");
/* 1880 */     String str5 = localLocaleResources.getLocaleName("ListPattern");
/* 1881 */     String str6 = localLocaleResources.getLocaleName("ListCompositionPattern");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1886 */     String str7 = null;
/* 1887 */     String[] arrayOfString2 = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1893 */     if ((str1.length() == 0) && (str2.length() == 0) && (str3.length() == 0)) {
/* 1894 */       if (arrayOfString1.length == 0) {
/* 1895 */         return "";
/*      */       }
/* 1897 */       return formatList(arrayOfString1, str5, str6);
/*      */     }
/*      */     
/* 1900 */     ArrayList localArrayList = new ArrayList(4);
/* 1901 */     if (str1.length() != 0) {
/* 1902 */       localArrayList.add(str1);
/*      */     }
/* 1904 */     if (str2.length() != 0) {
/* 1905 */       localArrayList.add(str2);
/*      */     }
/* 1907 */     if (str3.length() != 0) {
/* 1908 */       localArrayList.add(str3);
/*      */     }
/* 1910 */     if (arrayOfString1.length != 0) {
/* 1911 */       localArrayList.addAll(Arrays.asList(arrayOfString1));
/*      */     }
/*      */     
/*      */ 
/* 1915 */     str7 = (String)localArrayList.get(0);
/*      */     
/*      */ 
/* 1918 */     int i = localArrayList.size();
/*      */     
/* 1920 */     arrayOfString2 = i > 1 ? (String[])localArrayList.subList(1, i).toArray(new String[i - 1]) : new String[0];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1934 */     Object[] arrayOfObject = { new Integer(arrayOfString2.length != 0 ? 2 : 1), str7, arrayOfString2.length != 0 ? formatList(arrayOfString2, str5, str6) : null };
/*      */     
/*      */ 
/* 1937 */     if (str4 != null) {
/* 1938 */       return new MessageFormat(str4).format(arrayOfObject);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1944 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1945 */     localStringBuilder.append((String)arrayOfObject[1]);
/* 1946 */     if (arrayOfObject.length > 2) {
/* 1947 */       localStringBuilder.append(" (");
/* 1948 */       localStringBuilder.append((String)arrayOfObject[2]);
/* 1949 */       localStringBuilder.append(')');
/*      */     }
/* 1951 */     return localStringBuilder.toString();
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
/* 1962 */       return (Locale)super.clone();
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 1965 */       throw new InternalError(localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1976 */     int i = this.hashCodeValue;
/* 1977 */     if (i == 0) {
/* 1978 */       i = this.baseLocale.hashCode();
/* 1979 */       if (this.localeExtensions != null) {
/* 1980 */         i ^= this.localeExtensions.hashCode();
/*      */       }
/* 1982 */       this.hashCodeValue = i;
/*      */     }
/* 1984 */     return i;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1998 */     if (this == paramObject)
/* 1999 */       return true;
/* 2000 */     if (!(paramObject instanceof Locale))
/* 2001 */       return false;
/* 2002 */     BaseLocale localBaseLocale = ((Locale)paramObject).baseLocale;
/* 2003 */     if (!this.baseLocale.equals(localBaseLocale)) {
/* 2004 */       return false;
/*      */     }
/* 2006 */     if (this.localeExtensions == null) {
/* 2007 */       return ((Locale)paramObject).localeExtensions == null;
/*      */     }
/* 2009 */     return this.localeExtensions.equals(((Locale)paramObject).localeExtensions);
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
/* 2020 */   private volatile transient int hashCodeValue = 0;
/*      */   
/* 2022 */   private static volatile Locale defaultLocale = initDefault();
/* 2023 */   private static volatile Locale defaultDisplayLocale = null;
/* 2024 */   private static volatile Locale defaultFormatLocale = null;
/*      */   
/*      */ 
/*      */ 
/*      */   private volatile transient String languageTag;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String[] getDisplayVariantArray(Locale paramLocale)
/*      */   {
/* 2035 */     StringTokenizer localStringTokenizer = new StringTokenizer(this.baseLocale.getVariant(), "_");
/* 2036 */     String[] arrayOfString = new String[localStringTokenizer.countTokens()];
/*      */     
/*      */ 
/*      */ 
/* 2040 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 2041 */       arrayOfString[i] = getDisplayString(localStringTokenizer.nextToken(), paramLocale, 2);
/*      */     }
/*      */     
/*      */ 
/* 2045 */     return arrayOfString;
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
/*      */   private static String formatList(String[] paramArrayOfString, String paramString1, String paramString2)
/*      */   {
/* 2062 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 2063 */       localObject = new StringBuilder();
/* 2064 */       for (int i = 0; i < paramArrayOfString.length; i++) {
/* 2065 */         if (i > 0) {
/* 2066 */           ((StringBuilder)localObject).append(',');
/*      */         }
/* 2068 */         ((StringBuilder)localObject).append(paramArrayOfString[i]);
/*      */       }
/* 2070 */       return ((StringBuilder)localObject).toString();
/*      */     }
/*      */     
/*      */ 
/* 2074 */     if (paramArrayOfString.length > 3) {
/* 2075 */       localObject = new MessageFormat(paramString2);
/* 2076 */       paramArrayOfString = composeList((MessageFormat)localObject, paramArrayOfString);
/*      */     }
/*      */     
/*      */ 
/* 2080 */     Object localObject = new Object[paramArrayOfString.length + 1];
/* 2081 */     System.arraycopy(paramArrayOfString, 0, localObject, 1, paramArrayOfString.length);
/* 2082 */     localObject[0] = new Integer(paramArrayOfString.length);
/*      */     
/*      */ 
/* 2085 */     MessageFormat localMessageFormat = new MessageFormat(paramString1);
/* 2086 */     return localMessageFormat.format(localObject);
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
/*      */   private static String[] composeList(MessageFormat paramMessageFormat, String[] paramArrayOfString)
/*      */   {
/* 2099 */     if (paramArrayOfString.length <= 3) { return paramArrayOfString;
/*      */     }
/*      */     
/* 2102 */     String[] arrayOfString1 = { paramArrayOfString[0], paramArrayOfString[1] };
/* 2103 */     String str = paramMessageFormat.format(arrayOfString1);
/*      */     
/*      */ 
/* 2106 */     String[] arrayOfString2 = new String[paramArrayOfString.length - 1];
/* 2107 */     System.arraycopy(paramArrayOfString, 2, arrayOfString2, 1, arrayOfString2.length - 1);
/* 2108 */     arrayOfString2[0] = str;
/*      */     
/*      */ 
/* 2111 */     return composeList(paramMessageFormat, arrayOfString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static boolean isUnicodeExtensionKey(String paramString)
/*      */   {
/* 2118 */     return (paramString.length() == 2) && (LocaleUtils.isAlphaNumericString(paramString));
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
/* 2140 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("language", String.class), new ObjectStreamField("country", String.class), new ObjectStreamField("variant", String.class), new ObjectStreamField("hashcode", Integer.TYPE), new ObjectStreamField("script", String.class), new ObjectStreamField("extensions", String.class) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 2156 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 2157 */     localPutField.put("language", this.baseLocale.getLanguage());
/* 2158 */     localPutField.put("script", this.baseLocale.getScript());
/* 2159 */     localPutField.put("country", this.baseLocale.getRegion());
/* 2160 */     localPutField.put("variant", this.baseLocale.getVariant());
/* 2161 */     localPutField.put("extensions", this.localeExtensions == null ? "" : this.localeExtensions.getID());
/* 2162 */     localPutField.put("hashcode", -1);
/* 2163 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 2175 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 2176 */     String str1 = (String)localGetField.get("language", "");
/* 2177 */     String str2 = (String)localGetField.get("script", "");
/* 2178 */     String str3 = (String)localGetField.get("country", "");
/* 2179 */     String str4 = (String)localGetField.get("variant", "");
/* 2180 */     String str5 = (String)localGetField.get("extensions", "");
/* 2181 */     this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(str1), str2, str3, str4);
/* 2182 */     if (str5.length() > 0) {
/*      */       try {
/* 2184 */         InternalLocaleBuilder localInternalLocaleBuilder = new InternalLocaleBuilder();
/* 2185 */         localInternalLocaleBuilder.setExtensions(str5);
/* 2186 */         this.localeExtensions = localInternalLocaleBuilder.getLocaleExtensions();
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2188 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage());
/*      */       }
/*      */     } else {
/* 2191 */       this.localeExtensions = null;
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
/*      */   private Object readResolve()
/*      */     throws ObjectStreamException
/*      */   {
/* 2210 */     return getInstance(this.baseLocale.getLanguage(), this.baseLocale.getScript(), this.baseLocale
/* 2211 */       .getRegion(), this.baseLocale.getVariant(), this.localeExtensions);
/*      */   }
/*      */   
/* 2214 */   private static volatile String[] isoLanguages = null;
/*      */   
/* 2216 */   private static volatile String[] isoCountries = null;
/*      */   
/*      */ 
/*      */   private static String convertOldISOCodes(String paramString)
/*      */   {
/* 2221 */     paramString = LocaleUtils.toLowerString(paramString).intern();
/* 2222 */     if (paramString == "he")
/* 2223 */       return "iw";
/* 2224 */     if (paramString == "yi")
/* 2225 */       return "ji";
/* 2226 */     if (paramString == "id") {
/* 2227 */       return "in";
/*      */     }
/* 2229 */     return paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static LocaleExtensions getCompatibilityExtensions(String paramString1, String paramString2, String paramString3, String paramString4)
/*      */   {
/* 2237 */     LocaleExtensions localLocaleExtensions = null;
/*      */     
/* 2239 */     if ((LocaleUtils.caseIgnoreMatch(paramString1, "ja")) && 
/* 2240 */       (paramString2.length() == 0) && 
/* 2241 */       (LocaleUtils.caseIgnoreMatch(paramString3, "jp")) && 
/* 2242 */       ("JP".equals(paramString4)))
/*      */     {
/* 2244 */       localLocaleExtensions = LocaleExtensions.CALENDAR_JAPANESE;
/* 2245 */     } else if ((LocaleUtils.caseIgnoreMatch(paramString1, "th")) && 
/* 2246 */       (paramString2.length() == 0) && 
/* 2247 */       (LocaleUtils.caseIgnoreMatch(paramString3, "th")) && 
/* 2248 */       ("TH".equals(paramString4)))
/*      */     {
/* 2250 */       localLocaleExtensions = LocaleExtensions.NUMBER_THAI;
/*      */     }
/* 2252 */     return localLocaleExtensions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class LocaleNameGetter
/*      */     implements LocaleServiceProviderPool.LocalizedObjectGetter<LocaleNameProvider, String>
/*      */   {
/* 2261 */     private static final LocaleNameGetter INSTANCE = new LocaleNameGetter();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getObject(LocaleNameProvider paramLocaleNameProvider, Locale paramLocale, String paramString, Object... paramVarArgs)
/*      */     {
/* 2268 */       assert (paramVarArgs.length == 2);
/* 2269 */       int i = ((Integer)paramVarArgs[0]).intValue();
/* 2270 */       String str = (String)paramVarArgs[1];
/*      */       
/* 2272 */       switch (i) {
/*      */       case 0: 
/* 2274 */         return paramLocaleNameProvider.getDisplayLanguage(str, paramLocale);
/*      */       case 1: 
/* 2276 */         return paramLocaleNameProvider.getDisplayCountry(str, paramLocale);
/*      */       case 2: 
/* 2278 */         return paramLocaleNameProvider.getDisplayVariant(str, paramLocale);
/*      */       case 3: 
/* 2280 */         return paramLocaleNameProvider.getDisplayScript(str, paramLocale);
/*      */       }
/* 2282 */       if (!$assertionsDisabled) { throw new AssertionError();
/*      */       }
/*      */       
/* 2285 */       return null;
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
/*      */   public static enum Category
/*      */   {
/* 2304 */     DISPLAY("user.language.display", "user.script.display", "user.country.display", "user.variant.display"), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2313 */     FORMAT("user.language.format", "user.script.format", "user.country.format", "user.variant.format");
/*      */     
/*      */     final String languageKey;
/*      */     final String scriptKey;
/*      */     
/*      */     private Category(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 2319 */       this.languageKey = paramString1;
/* 2320 */       this.scriptKey = paramString2;
/* 2321 */       this.countryKey = paramString3;
/* 2322 */       this.variantKey = paramString4;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final String countryKey;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final String variantKey;
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
/*      */   public static final class Builder
/*      */   {
/*      */     private final InternalLocaleBuilder localeBuilder;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder()
/*      */     {
/* 2373 */       this.localeBuilder = new InternalLocaleBuilder();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setLocale(Locale paramLocale)
/*      */     {
/*      */       try
/*      */       {
/* 2398 */         this.localeBuilder.setLocale(paramLocale.baseLocale, paramLocale.localeExtensions);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2400 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2402 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setLanguageTag(String paramString)
/*      */     {
/* 2423 */       ParseStatus localParseStatus = new ParseStatus();
/* 2424 */       LanguageTag localLanguageTag = LanguageTag.parse(paramString, localParseStatus);
/* 2425 */       if (localParseStatus.isError()) {
/* 2426 */         throw new IllformedLocaleException(localParseStatus.getErrorMessage(), localParseStatus.getErrorIndex());
/*      */       }
/* 2428 */       this.localeBuilder.setLanguageTag(localLanguageTag);
/* 2429 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setLanguage(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2447 */         this.localeBuilder.setLanguage(paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2449 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2451 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setScript(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2468 */         this.localeBuilder.setScript(paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2470 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2472 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setRegion(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2493 */         this.localeBuilder.setRegion(paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2495 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2497 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setVariant(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2520 */         this.localeBuilder.setVariant(paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2522 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2524 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setExtension(char paramChar, String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2552 */         this.localeBuilder.setExtension(paramChar, paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2554 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2556 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder setUnicodeLocaleKeyword(String paramString1, String paramString2)
/*      */     {
/*      */       try
/*      */       {
/* 2582 */         this.localeBuilder.setUnicodeLocaleKeyword(paramString1, paramString2);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2584 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2586 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder addUnicodeLocaleAttribute(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2603 */         this.localeBuilder.addUnicodeLocaleAttribute(paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2605 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2607 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder removeUnicodeLocaleAttribute(String paramString)
/*      */     {
/*      */       try
/*      */       {
/* 2626 */         this.localeBuilder.removeUnicodeLocaleAttribute(paramString);
/*      */       } catch (LocaleSyntaxException localLocaleSyntaxException) {
/* 2628 */         throw new IllformedLocaleException(localLocaleSyntaxException.getMessage(), localLocaleSyntaxException.getErrorIndex());
/*      */       }
/* 2630 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder clear()
/*      */     {
/* 2639 */       this.localeBuilder.clear();
/* 2640 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Builder clearExtensions()
/*      */     {
/* 2651 */       this.localeBuilder.clearExtensions();
/* 2652 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Locale build()
/*      */     {
/* 2666 */       BaseLocale localBaseLocale = this.localeBuilder.getBaseLocale();
/* 2667 */       LocaleExtensions localLocaleExtensions = this.localeBuilder.getLocaleExtensions();
/* 2668 */       if ((localLocaleExtensions == null) && (localBaseLocale.getVariant().length() > 0)) {
/* 2669 */         localLocaleExtensions = Locale.getCompatibilityExtensions(localBaseLocale.getLanguage(), localBaseLocale.getScript(), localBaseLocale
/* 2670 */           .getRegion(), localBaseLocale.getVariant());
/*      */       }
/* 2672 */       return Locale.getInstance(localBaseLocale, localLocaleExtensions);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static enum FilteringMode
/*      */   {
/* 2775 */     AUTOSELECT_FILTERING, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2780 */     EXTENDED_FILTERING, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2786 */     IGNORE_EXTENDED_RANGES, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2797 */     MAP_EXTENDED_RANGES, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2804 */     REJECT_EXTENDED_RANGES;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private FilteringMode() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final class LanguageRange
/*      */   {
/*      */     public static final double MAX_WEIGHT = 1.0D;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static final double MIN_WEIGHT = 0.0D;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final String range;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final double weight;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2858 */     private volatile int hash = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public LanguageRange(String paramString)
/*      */     {
/* 2872 */       this(paramString, 1.0D);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public LanguageRange(String paramString, double paramDouble)
/*      */     {
/* 2889 */       if (paramString == null) {
/* 2890 */         throw new NullPointerException();
/*      */       }
/* 2892 */       if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
/* 2893 */         throw new IllegalArgumentException("weight=" + paramDouble);
/*      */       }
/*      */       
/* 2896 */       paramString = paramString.toLowerCase();
/*      */       
/*      */ 
/* 2899 */       int i = 0;
/* 2900 */       String[] arrayOfString = paramString.split("-");
/* 2901 */       if ((isSubtagIllFormed(arrayOfString[0], true)) || 
/* 2902 */         (paramString.endsWith("-"))) {
/* 2903 */         i = 1;
/*      */       } else {
/* 2905 */         for (int j = 1; j < arrayOfString.length; j++) {
/* 2906 */           if (isSubtagIllFormed(arrayOfString[j], false)) {
/* 2907 */             i = 1;
/* 2908 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 2912 */       if (i != 0) {
/* 2913 */         throw new IllegalArgumentException("range=" + paramString);
/*      */       }
/*      */       
/* 2916 */       this.range = paramString;
/* 2917 */       this.weight = paramDouble;
/*      */     }
/*      */     
/*      */     private static boolean isSubtagIllFormed(String paramString, boolean paramBoolean)
/*      */     {
/* 2922 */       if ((paramString.equals("")) || (paramString.length() > 8))
/* 2923 */         return true;
/* 2924 */       if (paramString.equals("*")) {
/* 2925 */         return false;
/*      */       }
/* 2927 */       char[] arrayOfChar1 = paramString.toCharArray();
/* 2928 */       int k; if (paramBoolean) {
/* 2929 */         for (k : arrayOfChar1) {
/* 2930 */           if ((k < 97) || (k > 122)) {
/* 2931 */             return true;
/*      */           }
/*      */         }
/*      */       } else {
/* 2935 */         for (k : arrayOfChar1) {
/* 2936 */           if ((k < 48) || ((k > 57) && (k < 97)) || (k > 122)) {
/* 2937 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 2941 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getRange()
/*      */     {
/* 2950 */       return this.range;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public double getWeight()
/*      */     {
/* 2959 */       return this.weight;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static List<LanguageRange> parse(String paramString)
/*      */     {
/* 3028 */       return LocaleMatcher.parse(paramString);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static List<LanguageRange> parse(String paramString, Map<String, List<String>> paramMap)
/*      */     {
/* 3052 */       return mapEquivalents(parse(paramString), paramMap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static List<LanguageRange> mapEquivalents(List<LanguageRange> paramList, Map<String, List<String>> paramMap)
/*      */     {
/* 3105 */       return LocaleMatcher.mapEquivalents(paramList, paramMap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 3115 */       if (this.hash == 0) {
/* 3116 */         int i = 17;
/* 3117 */         i = 37 * i + this.range.hashCode();
/* 3118 */         long l = Double.doubleToLongBits(this.weight);
/* 3119 */         i = 37 * i + (int)(l ^ l >>> 32);
/* 3120 */         this.hash = i;
/*      */       }
/* 3122 */       return this.hash;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/* 3138 */       if (this == paramObject) {
/* 3139 */         return true;
/*      */       }
/* 3141 */       if (!(paramObject instanceof LanguageRange)) {
/* 3142 */         return false;
/*      */       }
/* 3144 */       LanguageRange localLanguageRange = (LanguageRange)paramObject;
/*      */       
/* 3146 */       return (this.hash == localLanguageRange.hash) && (this.range.equals(localLanguageRange.range)) && (this.weight == localLanguageRange.weight);
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
/*      */   public static List<Locale> filter(List<LanguageRange> paramList, Collection<Locale> paramCollection, FilteringMode paramFilteringMode)
/*      */   {
/* 3173 */     return LocaleMatcher.filter(paramList, paramCollection, paramFilteringMode);
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
/*      */   public static List<Locale> filter(List<LanguageRange> paramList, Collection<Locale> paramCollection)
/*      */   {
/* 3195 */     return filter(paramList, paramCollection, FilteringMode.AUTOSELECT_FILTERING);
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
/*      */   public static List<String> filterTags(List<LanguageRange> paramList, Collection<String> paramCollection, FilteringMode paramFilteringMode)
/*      */   {
/* 3220 */     return LocaleMatcher.filterTags(paramList, paramCollection, paramFilteringMode);
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
/*      */   public static List<String> filterTags(List<LanguageRange> paramList, Collection<String> paramCollection)
/*      */   {
/* 3242 */     return filterTags(paramList, paramCollection, FilteringMode.AUTOSELECT_FILTERING);
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
/*      */   public static Locale lookup(List<LanguageRange> paramList, Collection<Locale> paramCollection)
/*      */   {
/* 3261 */     return LocaleMatcher.lookup(paramList, paramCollection);
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
/*      */   public static String lookupTag(List<LanguageRange> paramList, Collection<String> paramCollection)
/*      */   {
/* 3280 */     return LocaleMatcher.lookupTag(paramList, paramCollection);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/Locale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */