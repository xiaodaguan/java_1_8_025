/*      */ package java.util.prefs;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collection;
/*      */ import java.util.EventObject;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractPreferences
/*      */   extends Preferences
/*      */ {
/*      */   private final String name;
/*      */   private final String absolutePath;
/*      */   final AbstractPreferences parent;
/*      */   private final AbstractPreferences root;
/*  152 */   protected boolean newNode = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  158 */   private Map<String, AbstractPreferences> kidCache = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  164 */   private boolean removed = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  169 */   private PreferenceChangeListener[] prefListeners = new PreferenceChangeListener[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  175 */   private NodeChangeListener[] nodeListeners = new NodeChangeListener[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  184 */   protected final Object lock = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected AbstractPreferences(AbstractPreferences paramAbstractPreferences, String paramString)
/*      */   {
/*  199 */     if (paramAbstractPreferences == null) {
/*  200 */       if (!paramString.equals("")) {
/*  201 */         throw new IllegalArgumentException("Root name '" + paramString + "' must be \"\"");
/*      */       }
/*  203 */       this.absolutePath = "/";
/*  204 */       this.root = this;
/*      */     } else {
/*  206 */       if (paramString.indexOf('/') != -1) {
/*  207 */         throw new IllegalArgumentException("Name '" + paramString + "' contains '/'");
/*      */       }
/*  209 */       if (paramString.equals("")) {
/*  210 */         throw new IllegalArgumentException("Illegal name: empty string");
/*      */       }
/*  212 */       this.root = paramAbstractPreferences.root;
/*      */       
/*  214 */       this.absolutePath = (paramAbstractPreferences.absolutePath() + "/" + paramString);
/*      */     }
/*  216 */     this.name = paramString;
/*  217 */     this.parent = paramAbstractPreferences;
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
/*      */   public void put(String paramString1, String paramString2)
/*      */   {
/*  240 */     if ((paramString1 == null) || (paramString2 == null))
/*  241 */       throw new NullPointerException();
/*  242 */     if (paramString1.length() > 80)
/*  243 */       throw new IllegalArgumentException("Key too long: " + paramString1);
/*  244 */     if (paramString2.length() > 8192) {
/*  245 */       throw new IllegalArgumentException("Value too long: " + paramString2);
/*      */     }
/*  247 */     synchronized (this.lock) {
/*  248 */       if (this.removed) {
/*  249 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  251 */       putSpi(paramString1, paramString2);
/*  252 */       enqueuePreferenceChangeEvent(paramString1, paramString2);
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
/*      */   public String get(String paramString1, String paramString2)
/*      */   {
/*  279 */     if (paramString1 == null)
/*  280 */       throw new NullPointerException("Null key");
/*  281 */     synchronized (this.lock) {
/*  282 */       if (this.removed) {
/*  283 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  285 */       String str = null;
/*      */       try {
/*  287 */         str = getSpi(paramString1);
/*      */       }
/*      */       catch (Exception localException) {}
/*      */       
/*  291 */       return str == null ? paramString2 : str;
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
/*      */   public void remove(String paramString)
/*      */   {
/*  311 */     Objects.requireNonNull(paramString, "Specified key cannot be null");
/*  312 */     synchronized (this.lock) {
/*  313 */       if (this.removed) {
/*  314 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  316 */       removeSpi(paramString);
/*  317 */       enqueuePreferenceChangeEvent(paramString, null);
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
/*      */   public void clear()
/*      */     throws BackingStoreException
/*      */   {
/*  336 */     synchronized (this.lock) {
/*  337 */       String[] arrayOfString = keys();
/*  338 */       for (int i = 0; i < arrayOfString.length; i++) {
/*  339 */         remove(arrayOfString[i]);
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
/*      */   public void putInt(String paramString, int paramInt)
/*      */   {
/*  360 */     put(paramString, Integer.toString(paramInt));
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
/*      */   public int getInt(String paramString, int paramInt)
/*      */   {
/*  386 */     int i = paramInt;
/*      */     try {
/*  388 */       String str = get(paramString, null);
/*  389 */       if (str != null) {
/*  390 */         i = Integer.parseInt(str);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {}
/*      */     
/*  395 */     return i;
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
/*      */   public void putLong(String paramString, long paramLong)
/*      */   {
/*  415 */     put(paramString, Long.toString(paramLong));
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
/*      */   public long getLong(String paramString, long paramLong)
/*      */   {
/*  441 */     long l = paramLong;
/*      */     try {
/*  443 */       String str = get(paramString, null);
/*  444 */       if (str != null) {
/*  445 */         l = Long.parseLong(str);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {}
/*      */     
/*  450 */     return l;
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
/*      */   public void putBoolean(String paramString, boolean paramBoolean)
/*      */   {
/*  470 */     put(paramString, String.valueOf(paramBoolean));
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
/*      */   public boolean getBoolean(String paramString, boolean paramBoolean)
/*      */   {
/*  499 */     boolean bool = paramBoolean;
/*  500 */     String str = get(paramString, null);
/*  501 */     if (str != null) {
/*  502 */       if (str.equalsIgnoreCase("true")) {
/*  503 */         bool = true;
/*  504 */       } else if (str.equalsIgnoreCase("false")) {
/*  505 */         bool = false;
/*      */       }
/*      */     }
/*  508 */     return bool;
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
/*      */   public void putFloat(String paramString, float paramFloat)
/*      */   {
/*  528 */     put(paramString, Float.toString(paramFloat));
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
/*      */   public float getFloat(String paramString, float paramFloat)
/*      */   {
/*  554 */     float f = paramFloat;
/*      */     try {
/*  556 */       String str = get(paramString, null);
/*  557 */       if (str != null) {
/*  558 */         f = Float.parseFloat(str);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {}
/*      */     
/*  563 */     return f;
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
/*      */   public void putDouble(String paramString, double paramDouble)
/*      */   {
/*  583 */     put(paramString, Double.toString(paramDouble));
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
/*      */   public double getDouble(String paramString, double paramDouble)
/*      */   {
/*  609 */     double d = paramDouble;
/*      */     try {
/*  611 */       String str = get(paramString, null);
/*  612 */       if (str != null) {
/*  613 */         d = Double.parseDouble(str);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {}
/*      */     
/*  618 */     return d;
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
/*      */   public void putByteArray(String paramString, byte[] paramArrayOfByte)
/*      */   {
/*  634 */     put(paramString, Base64.byteArrayToBase64(paramArrayOfByte));
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
/*      */   public byte[] getByteArray(String paramString, byte[] paramArrayOfByte)
/*      */   {
/*  655 */     byte[] arrayOfByte = paramArrayOfByte;
/*  656 */     String str = get(paramString, null);
/*      */     try {
/*  658 */       if (str != null) {
/*  659 */         arrayOfByte = Base64.base64ToByteArray(str);
/*      */       }
/*      */     }
/*      */     catch (RuntimeException localRuntimeException) {}
/*      */     
/*      */ 
/*  665 */     return arrayOfByte;
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
/*      */   public String[] keys()
/*      */     throws BackingStoreException
/*      */   {
/*  684 */     synchronized (this.lock) {
/*  685 */       if (this.removed) {
/*  686 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  688 */       return keysSpi();
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
/*      */   public String[] childrenNames()
/*      */     throws BackingStoreException
/*      */   {
/*  713 */     synchronized (this.lock) {
/*  714 */       if (this.removed) {
/*  715 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  717 */       TreeSet localTreeSet = new TreeSet(this.kidCache.keySet());
/*  718 */       for (String str : childrenNamesSpi())
/*  719 */         localTreeSet.add(str);
/*  720 */       return (String[])localTreeSet.toArray(EMPTY_STRING_ARRAY);
/*      */     }
/*      */   }
/*      */   
/*  724 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final AbstractPreferences[] cachedChildren()
/*      */   {
/*  732 */     return (AbstractPreferences[])this.kidCache.values().toArray(EMPTY_ABSTRACT_PREFS_ARRAY);
/*      */   }
/*      */   
/*  735 */   private static final AbstractPreferences[] EMPTY_ABSTRACT_PREFS_ARRAY = new AbstractPreferences[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Preferences parent()
/*      */   {
/*  751 */     synchronized (this.lock) {
/*  752 */       if (this.removed) {
/*  753 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*  755 */       return this.parent;
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
/*      */   public Preferences node(String paramString)
/*      */   {
/*  805 */     synchronized (this.lock) {
/*  806 */       if (this.removed)
/*  807 */         throw new IllegalStateException("Node has been removed.");
/*  808 */       if (paramString.equals(""))
/*  809 */         return this;
/*  810 */       if (paramString.equals("/"))
/*  811 */         return this.root;
/*  812 */       if (paramString.charAt(0) != '/') {
/*  813 */         return node(new StringTokenizer(paramString, "/", true));
/*      */       }
/*      */     }
/*      */     
/*  817 */     return this.root.node(new StringTokenizer(paramString.substring(1), "/", true));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Preferences node(StringTokenizer paramStringTokenizer)
/*      */   {
/*  824 */     String str = paramStringTokenizer.nextToken();
/*  825 */     if (str.equals("/"))
/*  826 */       throw new IllegalArgumentException("Consecutive slashes in path");
/*  827 */     synchronized (this.lock) {
/*  828 */       AbstractPreferences localAbstractPreferences = (AbstractPreferences)this.kidCache.get(str);
/*  829 */       if (localAbstractPreferences == null) {
/*  830 */         if (str.length() > 80) {
/*  831 */           throw new IllegalArgumentException("Node name " + str + " too long");
/*      */         }
/*  833 */         localAbstractPreferences = childSpi(str);
/*  834 */         if (localAbstractPreferences.newNode)
/*  835 */           enqueueNodeAddedEvent(localAbstractPreferences);
/*  836 */         this.kidCache.put(str, localAbstractPreferences);
/*      */       }
/*  838 */       if (!paramStringTokenizer.hasMoreTokens())
/*  839 */         return localAbstractPreferences;
/*  840 */       paramStringTokenizer.nextToken();
/*  841 */       if (!paramStringTokenizer.hasMoreTokens())
/*  842 */         throw new IllegalArgumentException("Path ends with slash");
/*  843 */       return localAbstractPreferences.node(paramStringTokenizer);
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
/*      */   public boolean nodeExists(String paramString)
/*      */     throws BackingStoreException
/*      */   {
/*  870 */     synchronized (this.lock) {
/*  871 */       if (paramString.equals(""))
/*  872 */         return !this.removed;
/*  873 */       if (this.removed)
/*  874 */         throw new IllegalStateException("Node has been removed.");
/*  875 */       if (paramString.equals("/"))
/*  876 */         return true;
/*  877 */       if (paramString.charAt(0) != '/') {
/*  878 */         return nodeExists(new StringTokenizer(paramString, "/", true));
/*      */       }
/*      */     }
/*      */     
/*  882 */     return this.root.nodeExists(new StringTokenizer(paramString.substring(1), "/", true));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean nodeExists(StringTokenizer paramStringTokenizer)
/*      */     throws BackingStoreException
/*      */   {
/*  892 */     String str = paramStringTokenizer.nextToken();
/*  893 */     if (str.equals("/"))
/*  894 */       throw new IllegalArgumentException("Consecutive slashes in path");
/*  895 */     synchronized (this.lock) {
/*  896 */       AbstractPreferences localAbstractPreferences = (AbstractPreferences)this.kidCache.get(str);
/*  897 */       if (localAbstractPreferences == null)
/*  898 */         localAbstractPreferences = getChild(str);
/*  899 */       if (localAbstractPreferences == null)
/*  900 */         return false;
/*  901 */       if (!paramStringTokenizer.hasMoreTokens())
/*  902 */         return true;
/*  903 */       paramStringTokenizer.nextToken();
/*  904 */       if (!paramStringTokenizer.hasMoreTokens())
/*  905 */         throw new IllegalArgumentException("Path ends with slash");
/*  906 */       return localAbstractPreferences.nodeExists(paramStringTokenizer);
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
/*      */   public void removeNode()
/*      */     throws BackingStoreException
/*      */   {
/*  943 */     if (this == this.root)
/*  944 */       throw new UnsupportedOperationException("Can't remove the root!");
/*  945 */     synchronized (this.parent.lock) {
/*  946 */       removeNode2();
/*  947 */       this.parent.kidCache.remove(this.name);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void removeNode2()
/*      */     throws BackingStoreException
/*      */   {
/*  956 */     synchronized (this.lock) {
/*  957 */       if (this.removed) {
/*  958 */         throw new IllegalStateException("Node already removed.");
/*      */       }
/*      */       
/*  961 */       String[] arrayOfString = childrenNamesSpi();
/*  962 */       for (int i = 0; i < arrayOfString.length; i++) {
/*  963 */         if (!this.kidCache.containsKey(arrayOfString[i])) {
/*  964 */           this.kidCache.put(arrayOfString[i], childSpi(arrayOfString[i]));
/*      */         }
/*      */       }
/*  967 */       Iterator localIterator = this.kidCache.values().iterator();
/*  968 */       while (localIterator.hasNext()) {
/*      */         try {
/*  970 */           ((AbstractPreferences)localIterator.next()).removeNode2();
/*  971 */           localIterator.remove();
/*      */         }
/*      */         catch (BackingStoreException localBackingStoreException) {}
/*      */       }
/*      */       
/*  976 */       removeNodeSpi();
/*  977 */       this.removed = true;
/*  978 */       this.parent.enqueueNodeRemovedEvent(this);
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
/*      */   public String name()
/*      */   {
/*  992 */     return this.name;
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
/*      */   public String absolutePath()
/*      */   {
/* 1007 */     return this.absolutePath;
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
/*      */   public boolean isUserNode()
/*      */   {
/* 1029 */     ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Boolean run()
/*      */       {
/* 1027 */         return Boolean.valueOf(AbstractPreferences.this.root == Preferences.userRoot());
/*      */       }
/*      */     })).booleanValue();
/*      */   }
/*      */   
/*      */   public void addPreferenceChangeListener(PreferenceChangeListener paramPreferenceChangeListener) {
/* 1033 */     if (paramPreferenceChangeListener == null)
/* 1034 */       throw new NullPointerException("Change listener is null.");
/* 1035 */     synchronized (this.lock) {
/* 1036 */       if (this.removed) {
/* 1037 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*      */       
/* 1040 */       PreferenceChangeListener[] arrayOfPreferenceChangeListener = this.prefListeners;
/* 1041 */       this.prefListeners = new PreferenceChangeListener[arrayOfPreferenceChangeListener.length + 1];
/* 1042 */       System.arraycopy(arrayOfPreferenceChangeListener, 0, this.prefListeners, 0, arrayOfPreferenceChangeListener.length);
/* 1043 */       this.prefListeners[arrayOfPreferenceChangeListener.length] = paramPreferenceChangeListener;
/*      */     }
/* 1045 */     startEventDispatchThreadIfNecessary();
/*      */   }
/*      */   
/*      */   public void removePreferenceChangeListener(PreferenceChangeListener paramPreferenceChangeListener) {
/* 1049 */     synchronized (this.lock) {
/* 1050 */       if (this.removed)
/* 1051 */         throw new IllegalStateException("Node has been removed.");
/* 1052 */       if ((this.prefListeners == null) || (this.prefListeners.length == 0)) {
/* 1053 */         throw new IllegalArgumentException("Listener not registered.");
/*      */       }
/*      */       
/* 1056 */       PreferenceChangeListener[] arrayOfPreferenceChangeListener = new PreferenceChangeListener[this.prefListeners.length - 1];
/*      */       
/* 1058 */       int i = 0;
/* 1059 */       while ((i < arrayOfPreferenceChangeListener.length) && (this.prefListeners[i] != paramPreferenceChangeListener)) {
/* 1060 */         arrayOfPreferenceChangeListener[i] = this.prefListeners[(i++)];
/*      */       }
/* 1062 */       if ((i == arrayOfPreferenceChangeListener.length) && (this.prefListeners[i] != paramPreferenceChangeListener))
/* 1063 */         throw new IllegalArgumentException("Listener not registered.");
/* 1064 */       while (i < arrayOfPreferenceChangeListener.length)
/* 1065 */         arrayOfPreferenceChangeListener[i] = this.prefListeners[(++i)];
/* 1066 */       this.prefListeners = arrayOfPreferenceChangeListener;
/*      */     }
/*      */   }
/*      */   
/*      */   public void addNodeChangeListener(NodeChangeListener paramNodeChangeListener) {
/* 1071 */     if (paramNodeChangeListener == null)
/* 1072 */       throw new NullPointerException("Change listener is null.");
/* 1073 */     synchronized (this.lock) {
/* 1074 */       if (this.removed) {
/* 1075 */         throw new IllegalStateException("Node has been removed.");
/*      */       }
/*      */       
/* 1078 */       if (this.nodeListeners == null) {
/* 1079 */         this.nodeListeners = new NodeChangeListener[1];
/* 1080 */         this.nodeListeners[0] = paramNodeChangeListener;
/*      */       } else {
/* 1082 */         NodeChangeListener[] arrayOfNodeChangeListener = this.nodeListeners;
/* 1083 */         this.nodeListeners = new NodeChangeListener[arrayOfNodeChangeListener.length + 1];
/* 1084 */         System.arraycopy(arrayOfNodeChangeListener, 0, this.nodeListeners, 0, arrayOfNodeChangeListener.length);
/* 1085 */         this.nodeListeners[arrayOfNodeChangeListener.length] = paramNodeChangeListener;
/*      */       }
/*      */     }
/* 1088 */     startEventDispatchThreadIfNecessary();
/*      */   }
/*      */   
/*      */   public void removeNodeChangeListener(NodeChangeListener paramNodeChangeListener) {
/* 1092 */     synchronized (this.lock) {
/* 1093 */       if (this.removed)
/* 1094 */         throw new IllegalStateException("Node has been removed.");
/* 1095 */       if ((this.nodeListeners == null) || (this.nodeListeners.length == 0)) {
/* 1096 */         throw new IllegalArgumentException("Listener not registered.");
/*      */       }
/*      */       
/* 1099 */       int i = 0;
/* 1100 */       while ((i < this.nodeListeners.length) && (this.nodeListeners[i] != paramNodeChangeListener))
/* 1101 */         i++;
/* 1102 */       if (i == this.nodeListeners.length)
/* 1103 */         throw new IllegalArgumentException("Listener not registered.");
/* 1104 */       NodeChangeListener[] arrayOfNodeChangeListener = new NodeChangeListener[this.nodeListeners.length - 1];
/*      */       
/* 1106 */       if (i != 0)
/* 1107 */         System.arraycopy(this.nodeListeners, 0, arrayOfNodeChangeListener, 0, i);
/* 1108 */       if (i != arrayOfNodeChangeListener.length) {
/* 1109 */         System.arraycopy(this.nodeListeners, i + 1, arrayOfNodeChangeListener, i, arrayOfNodeChangeListener.length - i);
/*      */       }
/* 1111 */       this.nodeListeners = arrayOfNodeChangeListener;
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
/*      */   protected abstract void putSpi(String paramString1, String paramString2);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract String getSpi(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void removeSpi(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void removeNodeSpi()
/*      */     throws BackingStoreException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract String[] keysSpi()
/*      */     throws BackingStoreException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract String[] childrenNamesSpi()
/*      */     throws BackingStoreException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected AbstractPreferences getChild(String paramString)
/*      */     throws BackingStoreException
/*      */   {
/* 1257 */     synchronized (this.lock)
/*      */     {
/* 1259 */       String[] arrayOfString = childrenNames();
/* 1260 */       for (int i = 0; i < arrayOfString.length; i++)
/* 1261 */         if (arrayOfString[i].equals(paramString))
/* 1262 */           return childSpi(arrayOfString[i]);
/*      */     }
/* 1264 */     return null;
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
/*      */   protected abstract AbstractPreferences childSpi(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1305 */     return (isUserNode() ? "User" : "System") + " Preference Node: " + absolutePath();
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
/*      */   public void sync()
/*      */     throws BackingStoreException
/*      */   {
/* 1329 */     sync2();
/*      */   }
/*      */   
/*      */   private void sync2() throws BackingStoreException
/*      */   {
/*      */     AbstractPreferences[] arrayOfAbstractPreferences;
/* 1335 */     synchronized (this.lock) {
/* 1336 */       if (this.removed)
/* 1337 */         throw new IllegalStateException("Node has been removed");
/* 1338 */       syncSpi();
/* 1339 */       arrayOfAbstractPreferences = cachedChildren();
/*      */     }
/*      */     
/* 1342 */     for (int i = 0; i < arrayOfAbstractPreferences.length; i++) {
/* 1343 */       arrayOfAbstractPreferences[i].sync2();
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
/*      */   protected abstract void syncSpi()
/*      */     throws BackingStoreException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     throws BackingStoreException
/*      */   {
/* 1389 */     flush2();
/*      */   }
/*      */   
/*      */   private void flush2() throws BackingStoreException
/*      */   {
/*      */     AbstractPreferences[] arrayOfAbstractPreferences;
/* 1395 */     synchronized (this.lock) {
/* 1396 */       flushSpi();
/* 1397 */       if (this.removed)
/* 1398 */         return;
/* 1399 */       arrayOfAbstractPreferences = cachedChildren();
/*      */     }
/*      */     
/* 1402 */     for (int i = 0; i < arrayOfAbstractPreferences.length; i++) {
/* 1403 */       arrayOfAbstractPreferences[i].flush2();
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
/* 1450 */   private static final List<EventObject> eventQueue = new LinkedList();
/*      */   protected abstract void flushSpi()
/*      */     throws BackingStoreException;
/*      */   
/*      */   /* Error */
/*      */   protected boolean isRemoved()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/util/prefs/AbstractPreferences:lock	Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 8	java/util/prefs/AbstractPreferences:removed	Z
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: ireturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1437	-> byte code offset #0
/*      */     //   Java source line #1438	-> byte code offset #7
/*      */     //   Java source line #1439	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	AbstractPreferences
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   private class NodeAddedEvent
/*      */     extends NodeChangeEvent
/*      */   {
/*      */     private static final long serialVersionUID = -6743557530157328528L;
/*      */     
/* 1460 */     NodeAddedEvent(Preferences paramPreferences1, Preferences paramPreferences2) { super(paramPreferences2); }
/*      */   }
/*      */   
/*      */   private class NodeRemovedEvent extends NodeChangeEvent {
/*      */     private static final long serialVersionUID = 8735497392918824837L;
/*      */     
/* 1466 */     NodeRemovedEvent(Preferences paramPreferences1, Preferences paramPreferences2) { super(paramPreferences2); }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class EventDispatchThread
/*      */     extends Thread
/*      */   {
/*      */     public void run()
/*      */     {
/*      */       for (;;)
/*      */       {
/* 1478 */         EventObject localEventObject = null;
/* 1479 */         synchronized (AbstractPreferences.eventQueue) {
/*      */           try {
/* 1481 */             while (AbstractPreferences.eventQueue.isEmpty())
/* 1482 */               AbstractPreferences.eventQueue.wait();
/* 1483 */             localEventObject = (EventObject)AbstractPreferences.eventQueue.remove(0);
/*      */           }
/*      */           catch (InterruptedException localInterruptedException) {
/* 1486 */             return;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1491 */         ??? = (AbstractPreferences)localEventObject.getSource();
/* 1492 */         Object localObject1; Object localObject3; int i; if ((localEventObject instanceof PreferenceChangeEvent)) {
/* 1493 */           localObject1 = (PreferenceChangeEvent)localEventObject;
/* 1494 */           localObject3 = ((AbstractPreferences)???).prefListeners();
/* 1495 */           for (i = 0; i < localObject3.length; i++)
/* 1496 */             localObject3[i].preferenceChange((PreferenceChangeEvent)localObject1);
/*      */         } else {
/* 1498 */           localObject1 = (NodeChangeEvent)localEventObject;
/* 1499 */           localObject3 = ((AbstractPreferences)???).nodeListeners();
/* 1500 */           if ((localObject1 instanceof AbstractPreferences.NodeAddedEvent)) {
/* 1501 */             for (i = 0; i < localObject3.length; i++) {
/* 1502 */               localObject3[i].childAdded((NodeChangeEvent)localObject1);
/*      */             }
/*      */           } else {
/* 1505 */             for (i = 0; i < localObject3.length; i++)
/* 1506 */               localObject3[i].childRemoved((NodeChangeEvent)localObject1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1513 */   private static Thread eventDispatchThread = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static synchronized void startEventDispatchThreadIfNecessary()
/*      */   {
/* 1521 */     if (eventDispatchThread == null)
/*      */     {
/* 1523 */       eventDispatchThread = new EventDispatchThread(null);
/* 1524 */       eventDispatchThread.setDaemon(true);
/* 1525 */       eventDispatchThread.start();
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   PreferenceChangeListener[] prefListeners()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/util/prefs/AbstractPreferences:lock	Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 10	java/util/prefs/AbstractPreferences:prefListeners	[Ljava/util/prefs/PreferenceChangeListener;
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: areturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1536	-> byte code offset #0
/*      */     //   Java source line #1537	-> byte code offset #7
/*      */     //   Java source line #1538	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	AbstractPreferences
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   NodeChangeListener[] nodeListeners()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 15	java/util/prefs/AbstractPreferences:lock	Ljava/lang/Object;
/*      */     //   4: dup
/*      */     //   5: astore_1
/*      */     //   6: monitorenter
/*      */     //   7: aload_0
/*      */     //   8: getfield 12	java/util/prefs/AbstractPreferences:nodeListeners	[Ljava/util/prefs/NodeChangeListener;
/*      */     //   11: aload_1
/*      */     //   12: monitorexit
/*      */     //   13: areturn
/*      */     //   14: astore_2
/*      */     //   15: aload_1
/*      */     //   16: monitorexit
/*      */     //   17: aload_2
/*      */     //   18: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1541	-> byte code offset #0
/*      */     //   Java source line #1542	-> byte code offset #7
/*      */     //   Java source line #1543	-> byte code offset #14
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	19	0	this	AbstractPreferences
/*      */     //   5	11	1	Ljava/lang/Object;	Object
/*      */     //   14	4	2	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   7	13	14	finally
/*      */     //   14	17	14	finally
/*      */   }
/*      */   
/*      */   private void enqueuePreferenceChangeEvent(String paramString1, String paramString2)
/*      */   {
/* 1552 */     if (this.prefListeners.length != 0) {
/* 1553 */       synchronized (eventQueue) {
/* 1554 */         eventQueue.add(new PreferenceChangeEvent(this, paramString1, paramString2));
/* 1555 */         eventQueue.notify();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void enqueueNodeAddedEvent(Preferences paramPreferences)
/*      */   {
/* 1566 */     if (this.nodeListeners.length != 0) {
/* 1567 */       synchronized (eventQueue) {
/* 1568 */         eventQueue.add(new NodeAddedEvent(this, paramPreferences));
/* 1569 */         eventQueue.notify();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void enqueueNodeRemovedEvent(Preferences paramPreferences)
/*      */   {
/* 1580 */     if (this.nodeListeners.length != 0) {
/* 1581 */       synchronized (eventQueue) {
/* 1582 */         eventQueue.add(new NodeRemovedEvent(this, paramPreferences));
/* 1583 */         eventQueue.notify();
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
/*      */   public void exportNode(OutputStream paramOutputStream)
/*      */     throws IOException, BackingStoreException
/*      */   {
/* 1601 */     XmlSupport.export(paramOutputStream, this, false);
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
/*      */   public void exportSubtree(OutputStream paramOutputStream)
/*      */     throws IOException, BackingStoreException
/*      */   {
/* 1617 */     XmlSupport.export(paramOutputStream, this, true);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/prefs/AbstractPreferences.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */