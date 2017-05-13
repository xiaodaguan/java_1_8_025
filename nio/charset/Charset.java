/*     */ package java.nio.charset;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.spi.CharsetProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import sun.misc.ASCIICaseInsensitiveComparator;
/*     */ import sun.misc.VM;
/*     */ import sun.nio.cs.StandardCharsets;
/*     */ import sun.nio.cs.ThreadLocalCoders;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public abstract class Charset
/*     */   implements Comparable<Charset>
/*     */ {
/* 277 */   private static volatile String bugLevel = null;
/*     */   
/*     */   static boolean atBugLevel(String paramString) {
/* 280 */     String str = bugLevel;
/* 281 */     if (str == null) {
/* 282 */       if (!VM.isBooted())
/* 283 */         return false;
/* 284 */       bugLevel = str = (String)AccessController.doPrivileged(new GetPropertyAction("sun.nio.cs.bugLevel", ""));
/*     */     }
/*     */     
/* 287 */     return str.equals(paramString);
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
/*     */   private static void checkName(String paramString)
/*     */   {
/* 300 */     int i = paramString.length();
/* 301 */     if ((!atBugLevel("1.4")) && 
/* 302 */       (i == 0)) {
/* 303 */       throw new IllegalCharsetNameException(paramString);
/*     */     }
/* 305 */     for (int j = 0; j < i; j++) {
/* 306 */       int k = paramString.charAt(j);
/* 307 */       if (((k < 65) || (k > 90)) && 
/* 308 */         ((k < 97) || (k > 122)) && 
/* 309 */         ((k < 48) || (k > 57)) && 
/* 310 */         ((k != 45) || (j == 0)) && 
/* 311 */         ((k != 43) || (j == 0)) && 
/* 312 */         ((k != 58) || (j == 0)) && 
/* 313 */         ((k != 95) || (j == 0)) && (
/* 314 */         (k != 46) || (j == 0))) {
/* 315 */         throw new IllegalCharsetNameException(paramString);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 320 */   private static CharsetProvider standardProvider = new StandardCharsets();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 325 */   private static volatile Object[] cache1 = null;
/* 326 */   private static volatile Object[] cache2 = null;
/*     */   
/*     */   private static void cache(String paramString, Charset paramCharset) {
/* 329 */     cache2 = cache1;
/* 330 */     cache1 = new Object[] { paramString, paramCharset };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Iterator<CharsetProvider> providers()
/*     */   {
/* 338 */     new Iterator()
/*     */     {
/* 340 */       ClassLoader cl = ClassLoader.getSystemClassLoader();
/*     */       
/* 342 */       ServiceLoader<CharsetProvider> sl = ServiceLoader.load(CharsetProvider.class, this.cl);
/* 343 */       Iterator<CharsetProvider> i = this.sl.iterator();
/*     */       
/* 345 */       CharsetProvider next = null;
/*     */       
/*     */       private boolean getNext() {
/* 348 */         while (this.next == null) {
/*     */           try {
/* 350 */             if (!this.i.hasNext())
/* 351 */               return false;
/* 352 */             this.next = ((CharsetProvider)this.i.next());
/*     */           } catch (ServiceConfigurationError localServiceConfigurationError) {}
/* 354 */           if (!(localServiceConfigurationError.getCause() instanceof SecurityException))
/*     */           {
/*     */ 
/*     */ 
/* 358 */             throw localServiceConfigurationError;
/*     */           }
/*     */         }
/* 361 */         return true;
/*     */       }
/*     */       
/*     */       public boolean hasNext() {
/* 365 */         return getNext();
/*     */       }
/*     */       
/*     */       public CharsetProvider next() {
/* 369 */         if (!getNext())
/* 370 */           throw new NoSuchElementException();
/* 371 */         CharsetProvider localCharsetProvider = this.next;
/* 372 */         this.next = null;
/* 373 */         return localCharsetProvider;
/*     */       }
/*     */       
/*     */       public void remove() {
/* 377 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 384 */   private static ThreadLocal<ThreadLocal<?>> gate = new ThreadLocal();
/*     */   
/*     */ 
/*     */   private static volatile Charset defaultCharset;
/*     */   
/*     */ 
/*     */   private final String name;
/*     */   
/*     */   private final String[] aliases;
/*     */   
/*     */ 
/*     */   private static Charset lookupViaProviders(String paramString)
/*     */   {
/* 397 */     if (!VM.isBooted()) {
/* 398 */       return null;
/*     */     }
/* 400 */     if (gate.get() != null)
/*     */     {
/* 402 */       return null; }
/*     */     try {
/* 404 */       gate.set(gate);
/*     */       
/* 406 */       (Charset)AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Charset run() {
/* 409 */           Iterator localIterator = Charset.access$000();
/* 410 */           while (localIterator.hasNext()) {
/* 411 */             CharsetProvider localCharsetProvider = (CharsetProvider)localIterator.next();
/* 412 */             Charset localCharset = localCharsetProvider.charsetForName(this.val$charsetName);
/* 413 */             if (localCharset != null)
/* 414 */               return localCharset;
/*     */           }
/* 416 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */     finally {
/* 421 */       gate.set(null);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ExtendedProviderHolder
/*     */   {
/* 427 */     static final CharsetProvider extendedProvider = ;
/*     */     
/*     */     private static CharsetProvider extendedProvider() {
/* 430 */       (CharsetProvider)AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public CharsetProvider run()
/*     */         {
/*     */           try {
/* 435 */             Class localClass = Class.forName("sun.nio.cs.ext.ExtendedCharsets");
/* 436 */             return (CharsetProvider)localClass.newInstance();
/*     */ 
/*     */           }
/*     */           catch (ClassNotFoundException localClassNotFoundException) {}catch (InstantiationException|IllegalAccessException localInstantiationException)
/*     */           {
/*     */ 
/* 442 */             throw new Error(localInstantiationException);
/*     */           }
/* 444 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private static Charset lookupExtendedCharset(String paramString) {
/* 451 */     CharsetProvider localCharsetProvider = ExtendedProviderHolder.extendedProvider;
/* 452 */     return localCharsetProvider != null ? localCharsetProvider.charsetForName(paramString) : null;
/*     */   }
/*     */   
/*     */   private static Charset lookup(String paramString) {
/* 456 */     if (paramString == null)
/* 457 */       throw new IllegalArgumentException("Null charset name");
/*     */     Object[] arrayOfObject;
/* 459 */     if (((arrayOfObject = cache1) != null) && (paramString.equals(arrayOfObject[0]))) {
/* 460 */       return (Charset)arrayOfObject[1];
/*     */     }
/*     */     
/*     */ 
/* 464 */     return lookup2(paramString);
/*     */   }
/*     */   
/*     */   private static Charset lookup2(String paramString) {
/*     */     Object[] arrayOfObject;
/* 469 */     if (((arrayOfObject = cache2) != null) && (paramString.equals(arrayOfObject[0]))) {
/* 470 */       cache2 = cache1;
/* 471 */       cache1 = arrayOfObject;
/* 472 */       return (Charset)arrayOfObject[1];
/*     */     }
/*     */     Charset localCharset;
/* 475 */     if (((localCharset = standardProvider.charsetForName(paramString)) != null) || 
/* 476 */       ((localCharset = lookupExtendedCharset(paramString)) != null) || 
/* 477 */       ((localCharset = lookupViaProviders(paramString)) != null))
/*     */     {
/* 479 */       cache(paramString, localCharset);
/* 480 */       return localCharset;
/*     */     }
/*     */     
/*     */ 
/* 484 */     checkName(paramString);
/* 485 */     return null;
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
/*     */   public static boolean isSupported(String paramString)
/*     */   {
/* 505 */     return lookup(paramString) != null;
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
/*     */   public static Charset forName(String paramString)
/*     */   {
/* 528 */     Charset localCharset = lookup(paramString);
/* 529 */     if (localCharset != null)
/* 530 */       return localCharset;
/* 531 */     throw new UnsupportedCharsetException(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void put(Iterator<Charset> paramIterator, Map<String, Charset> paramMap)
/*     */   {
/* 538 */     while (paramIterator.hasNext()) {
/* 539 */       Charset localCharset = (Charset)paramIterator.next();
/* 540 */       if (!paramMap.containsKey(localCharset.name())) {
/* 541 */         paramMap.put(localCharset.name(), localCharset);
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
/*     */   public static SortedMap<String, Charset> availableCharsets()
/*     */   {
/* 572 */     (SortedMap)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public SortedMap<String, Charset> run() {
/* 575 */         TreeMap localTreeMap = new TreeMap(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
/*     */         
/*     */ 
/* 578 */         Charset.put(Charset.standardProvider.charsets(), localTreeMap);
/* 579 */         CharsetProvider localCharsetProvider1 = Charset.ExtendedProviderHolder.extendedProvider;
/* 580 */         if (localCharsetProvider1 != null)
/* 581 */           Charset.put(localCharsetProvider1.charsets(), localTreeMap);
/* 582 */         for (Iterator localIterator = Charset.access$000(); localIterator.hasNext();) {
/* 583 */           CharsetProvider localCharsetProvider2 = (CharsetProvider)localIterator.next();
/* 584 */           Charset.put(localCharsetProvider2.charsets(), localTreeMap);
/*     */         }
/* 586 */         return Collections.unmodifiableSortedMap(localTreeMap);
/*     */       }
/*     */     });
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
/*     */   public static Charset defaultCharset()
/*     */   {
/* 605 */     if (defaultCharset == null) {
/* 606 */       synchronized (Charset.class) {
/* 607 */         String str = (String)AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
/*     */         
/* 609 */         Charset localCharset = lookup(str);
/* 610 */         if (localCharset != null) {
/* 611 */           defaultCharset = localCharset;
/*     */         } else
/* 613 */           defaultCharset = forName("UTF-8");
/*     */       }
/*     */     }
/* 616 */     return defaultCharset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 624 */   private Set<String> aliasSet = null;
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
/*     */   protected Charset(String paramString, String[] paramArrayOfString)
/*     */   {
/* 640 */     checkName(paramString);
/* 641 */     String[] arrayOfString = paramArrayOfString == null ? new String[0] : paramArrayOfString;
/* 642 */     for (int i = 0; i < arrayOfString.length; i++)
/* 643 */       checkName(arrayOfString[i]);
/* 644 */     this.name = paramString;
/* 645 */     this.aliases = arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String name()
/*     */   {
/* 654 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Set<String> aliases()
/*     */   {
/* 663 */     if (this.aliasSet != null)
/* 664 */       return this.aliasSet;
/* 665 */     int i = this.aliases.length;
/* 666 */     HashSet localHashSet = new HashSet(i);
/* 667 */     for (int j = 0; j < i; j++)
/* 668 */       localHashSet.add(this.aliases[j]);
/* 669 */     this.aliasSet = Collections.unmodifiableSet(localHashSet);
/* 670 */     return this.aliasSet;
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
/*     */   public String displayName()
/*     */   {
/* 683 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isRegistered()
/*     */   {
/* 695 */     return (!this.name.startsWith("X-")) && (!this.name.startsWith("x-"));
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
/*     */   public String displayName(Locale paramLocale)
/*     */   {
/* 711 */     return this.name;
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
/*     */   public abstract boolean contains(Charset paramCharset);
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
/*     */   public abstract CharsetDecoder newDecoder();
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
/*     */   public abstract CharsetEncoder newEncoder();
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
/*     */   public boolean canEncode()
/*     */   {
/* 774 */     return true;
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
/*     */   public final CharBuffer decode(ByteBuffer paramByteBuffer)
/*     */   {
/*     */     try
/*     */     {
/* 807 */       return ThreadLocalCoders.decoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).decode(paramByteBuffer);
/*     */     } catch (CharacterCodingException localCharacterCodingException) {
/* 809 */       throw new Error(localCharacterCodingException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ByteBuffer encode(CharBuffer paramCharBuffer)
/*     */   {
/*     */     try
/*     */     {
/* 843 */       return ThreadLocalCoders.encoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).encode(paramCharBuffer);
/*     */     } catch (CharacterCodingException localCharacterCodingException) {
/* 845 */       throw new Error(localCharacterCodingException);
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
/*     */   public final ByteBuffer encode(String paramString)
/*     */   {
/* 863 */     return encode(CharBuffer.wrap(paramString));
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
/*     */   public final int compareTo(Charset paramCharset)
/*     */   {
/* 879 */     return name().compareToIgnoreCase(paramCharset.name());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 888 */     return name().hashCode();
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
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/* 901 */     if (!(paramObject instanceof Charset))
/* 902 */       return false;
/* 903 */     if (this == paramObject)
/* 904 */       return true;
/* 905 */     return this.name.equals(((Charset)paramObject).name());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String toString()
/*     */   {
/* 914 */     return name();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/charset/Charset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */