/*      */ package java.security;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
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
/*      */ public abstract class Provider
/*      */   extends Properties
/*      */ {
/*      */   static final long serialVersionUID = -4298000515446427739L;
/*   95 */   private static final Debug debug = Debug.getInstance("provider", "Provider");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String name;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String info;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private double version;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  119 */   private transient Set<Map.Entry<Object, Object>> entrySet = null;
/*  120 */   private transient int entrySetCallCount = 0;
/*      */   
/*      */   private transient boolean initialized;
/*      */   
/*      */   private transient boolean legacyChanged;
/*      */   private transient boolean servicesChanged;
/*      */   private transient Map<String, String> legacyStrings;
/*      */   private transient Map<ServiceKey, Service> serviceMap;
/*      */   private transient Map<ServiceKey, Service> legacyMap;
/*      */   private transient Set<Service> serviceSet;
/*      */   private static final String ALIAS_PREFIX = "Alg.Alias.";
/*      */   private static final String ALIAS_PREFIX_LOWER = "alg.alias.";
/*      */   
/*      */   protected Provider(String paramString1, double paramDouble, String paramString2)
/*      */   {
/*  135 */     this.name = paramString1;
/*  136 */     this.version = paramDouble;
/*  137 */     this.info = paramString2;
/*  138 */     putId();
/*  139 */     this.initialized = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  148 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getVersion()
/*      */   {
/*  157 */     return this.version;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getInfo()
/*      */   {
/*  167 */     return this.info;
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
/*  178 */     return this.name + " version " + this.version;
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
/*      */   public synchronized void clear()
/*      */   {
/*  205 */     check("clearProviderProperties." + this.name);
/*  206 */     if (debug != null) {
/*  207 */       debug.println("Remove " + this.name + " provider properties");
/*      */     }
/*  209 */     implClear();
/*      */   }
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
/*  222 */     check("putProviderProperty." + this.name);
/*  223 */     if (debug != null) {
/*  224 */       debug.println("Load " + this.name + " provider properties");
/*      */     }
/*  226 */     Properties localProperties = new Properties();
/*  227 */     localProperties.load(paramInputStream);
/*  228 */     implPutAll(localProperties);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void putAll(Map<?, ?> paramMap)
/*      */   {
/*  240 */     check("putProviderProperty." + this.name);
/*  241 */     if (debug != null) {
/*  242 */       debug.println("Put all " + this.name + " provider properties");
/*      */     }
/*  244 */     implPutAll(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Set<Map.Entry<Object, Object>> entrySet()
/*      */   {
/*  256 */     checkInitialized();
/*  257 */     if (this.entrySet == null) {
/*  258 */       if (this.entrySetCallCount++ == 0) {
/*  259 */         this.entrySet = Collections.unmodifiableMap(this).entrySet();
/*      */       } else {
/*  261 */         return super.entrySet();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  269 */     if (this.entrySetCallCount != 2) {
/*  270 */       throw new RuntimeException("Internal error.");
/*      */     }
/*  272 */     return this.entrySet;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<Object> keySet()
/*      */   {
/*  283 */     checkInitialized();
/*  284 */     return Collections.unmodifiableSet(super.keySet());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Collection<Object> values()
/*      */   {
/*  295 */     checkInitialized();
/*  296 */     return Collections.unmodifiableCollection(super.values());
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
/*      */   public synchronized Object put(Object paramObject1, Object paramObject2)
/*      */   {
/*  317 */     check("putProviderProperty." + this.name);
/*  318 */     if (debug != null) {
/*  319 */       debug.println("Set " + this.name + " provider property [" + paramObject1 + "/" + paramObject2 + "]");
/*      */     }
/*      */     
/*  322 */     return implPut(paramObject1, paramObject2);
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
/*      */   public synchronized Object putIfAbsent(Object paramObject1, Object paramObject2)
/*      */   {
/*  344 */     check("putProviderProperty." + this.name);
/*  345 */     if (debug != null) {
/*  346 */       debug.println("Set " + this.name + " provider property [" + paramObject1 + "/" + paramObject2 + "]");
/*      */     }
/*      */     
/*  349 */     return implPutIfAbsent(paramObject1, paramObject2);
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
/*      */   public synchronized Object remove(Object paramObject)
/*      */   {
/*  370 */     check("removeProviderProperty." + this.name);
/*  371 */     if (debug != null) {
/*  372 */       debug.println("Remove " + this.name + " provider property " + paramObject);
/*      */     }
/*  374 */     return implRemove(paramObject);
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
/*      */   public synchronized boolean remove(Object paramObject1, Object paramObject2)
/*      */   {
/*  395 */     check("removeProviderProperty." + this.name);
/*  396 */     if (debug != null) {
/*  397 */       debug.println("Remove " + this.name + " provider property " + paramObject1);
/*      */     }
/*  399 */     return implRemove(paramObject1, paramObject2);
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
/*      */   public synchronized boolean replace(Object paramObject1, Object paramObject2, Object paramObject3)
/*      */   {
/*  421 */     check("putProviderProperty." + this.name);
/*      */     
/*  423 */     if (debug != null) {
/*  424 */       debug.println("Replace " + this.name + " provider property " + paramObject1);
/*      */     }
/*  426 */     return implReplace(paramObject1, paramObject2, paramObject3);
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
/*      */   public synchronized Object replace(Object paramObject1, Object paramObject2)
/*      */   {
/*  447 */     check("putProviderProperty." + this.name);
/*      */     
/*  449 */     if (debug != null) {
/*  450 */       debug.println("Replace " + this.name + " provider property " + paramObject1);
/*      */     }
/*  452 */     return implReplace(paramObject1, paramObject2);
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
/*      */   public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction)
/*      */   {
/*  475 */     check("putProviderProperty." + this.name);
/*      */     
/*  477 */     if (debug != null) {
/*  478 */       debug.println("ReplaceAll " + this.name + " provider property ");
/*      */     }
/*  480 */     implReplaceAll(paramBiFunction);
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
/*      */   public synchronized Object compute(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction)
/*      */   {
/*  504 */     check("putProviderProperty." + this.name);
/*  505 */     check("removeProviderProperty" + this.name);
/*      */     
/*  507 */     if (debug != null) {
/*  508 */       debug.println("Compute " + this.name + " provider property " + paramObject);
/*      */     }
/*  510 */     return implCompute(paramObject, paramBiFunction);
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
/*      */   public synchronized Object computeIfAbsent(Object paramObject, Function<? super Object, ? extends Object> paramFunction)
/*      */   {
/*  534 */     check("putProviderProperty." + this.name);
/*  535 */     check("removeProviderProperty" + this.name);
/*      */     
/*  537 */     if (debug != null) {
/*  538 */       debug.println("ComputeIfAbsent " + this.name + " provider property " + paramObject);
/*      */     }
/*      */     
/*  541 */     return implComputeIfAbsent(paramObject, paramFunction);
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
/*      */   public synchronized Object computeIfPresent(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction)
/*      */   {
/*  563 */     check("putProviderProperty." + this.name);
/*  564 */     check("removeProviderProperty" + this.name);
/*      */     
/*  566 */     if (debug != null) {
/*  567 */       debug.println("ComputeIfPresent " + this.name + " provider property " + paramObject);
/*      */     }
/*      */     
/*  570 */     return implComputeIfPresent(paramObject, paramBiFunction);
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
/*      */   public synchronized Object merge(Object paramObject1, Object paramObject2, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction)
/*      */   {
/*  595 */     check("putProviderProperty." + this.name);
/*  596 */     check("removeProviderProperty" + this.name);
/*      */     
/*  598 */     if (debug != null) {
/*  599 */       debug.println("Merge " + this.name + " provider property " + paramObject1);
/*      */     }
/*  601 */     return implMerge(paramObject1, paramObject2, paramBiFunction);
/*      */   }
/*      */   
/*      */ 
/*      */   public Object get(Object paramObject)
/*      */   {
/*  607 */     checkInitialized();
/*  608 */     return super.get(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized Object getOrDefault(Object paramObject1, Object paramObject2)
/*      */   {
/*  615 */     checkInitialized();
/*  616 */     return super.getOrDefault(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void forEach(BiConsumer<? super Object, ? super Object> paramBiConsumer)
/*      */   {
/*  624 */     checkInitialized();
/*  625 */     super.forEach(paramBiConsumer);
/*      */   }
/*      */   
/*      */ 
/*      */   public Enumeration<Object> keys()
/*      */   {
/*  631 */     checkInitialized();
/*  632 */     return super.keys();
/*      */   }
/*      */   
/*      */ 
/*      */   public Enumeration<Object> elements()
/*      */   {
/*  638 */     checkInitialized();
/*  639 */     return super.elements();
/*      */   }
/*      */   
/*      */   public String getProperty(String paramString)
/*      */   {
/*  644 */     checkInitialized();
/*  645 */     return super.getProperty(paramString);
/*      */   }
/*      */   
/*      */   private void checkInitialized() {
/*  649 */     if (!this.initialized) {
/*  650 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */   private void check(String paramString) {
/*  655 */     checkInitialized();
/*  656 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  657 */     if (localSecurityManager != null) {
/*  658 */       localSecurityManager.checkSecurityAccess(paramString);
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
/*      */   private void putId()
/*      */   {
/*  687 */     super.put("Provider.id name", String.valueOf(this.name));
/*  688 */     super.put("Provider.id version", String.valueOf(this.version));
/*  689 */     super.put("Provider.id info", String.valueOf(this.info));
/*  690 */     super.put("Provider.id className", getClass().getName());
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException
/*      */   {
/*  695 */     HashMap localHashMap = new HashMap();
/*  696 */     for (Map.Entry localEntry : super.entrySet()) {
/*  697 */       localHashMap.put(localEntry.getKey(), localEntry.getValue());
/*      */     }
/*  699 */     this.defaults = null;
/*  700 */     paramObjectInputStream.defaultReadObject();
/*  701 */     implClear();
/*  702 */     this.initialized = true;
/*  703 */     putAll(localHashMap);
/*      */   }
/*      */   
/*      */   private boolean checkLegacy(Object paramObject) {
/*  707 */     String str = (String)paramObject;
/*  708 */     if (str.startsWith("Provider.")) {
/*  709 */       return false;
/*      */     }
/*      */     
/*  712 */     this.legacyChanged = true;
/*  713 */     if (this.legacyStrings == null) {
/*  714 */       this.legacyStrings = new LinkedHashMap();
/*      */     }
/*  716 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void implPutAll(Map<?, ?> paramMap)
/*      */   {
/*  725 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  726 */       implPut(localEntry.getKey(), localEntry.getValue());
/*      */     }
/*      */   }
/*      */   
/*      */   private Object implRemove(Object paramObject) {
/*  731 */     if ((paramObject instanceof String)) {
/*  732 */       if (!checkLegacy(paramObject)) {
/*  733 */         return null;
/*      */       }
/*  735 */       this.legacyStrings.remove((String)paramObject);
/*      */     }
/*  737 */     return super.remove(paramObject);
/*      */   }
/*      */   
/*      */   private boolean implRemove(Object paramObject1, Object paramObject2) {
/*  741 */     if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
/*  742 */       if (!checkLegacy(paramObject1)) {
/*  743 */         return false;
/*      */       }
/*  745 */       this.legacyStrings.remove((String)paramObject1, paramObject2);
/*      */     }
/*  747 */     return super.remove(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private boolean implReplace(Object paramObject1, Object paramObject2, Object paramObject3) {
/*  751 */     if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String)) && ((paramObject3 instanceof String)))
/*      */     {
/*  753 */       if (!checkLegacy(paramObject1)) {
/*  754 */         return false;
/*      */       }
/*  756 */       this.legacyStrings.replace((String)paramObject1, (String)paramObject2, (String)paramObject3);
/*      */     }
/*      */     
/*  759 */     return super.replace(paramObject1, paramObject2, paramObject3);
/*      */   }
/*      */   
/*      */   private Object implReplace(Object paramObject1, Object paramObject2) {
/*  763 */     if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
/*  764 */       if (!checkLegacy(paramObject1)) {
/*  765 */         return null;
/*      */       }
/*  767 */       this.legacyStrings.replace((String)paramObject1, (String)paramObject2);
/*      */     }
/*  769 */     return super.replace(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private void implReplaceAll(BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  773 */     this.legacyChanged = true;
/*  774 */     if (this.legacyStrings == null) {
/*  775 */       this.legacyStrings = new LinkedHashMap();
/*      */     } else {
/*  777 */       this.legacyStrings.replaceAll(paramBiFunction);
/*      */     }
/*  779 */     super.replaceAll(paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implMerge(Object paramObject1, Object paramObject2, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction)
/*      */   {
/*  784 */     if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
/*  785 */       if (!checkLegacy(paramObject1)) {
/*  786 */         return null;
/*      */       }
/*  788 */       this.legacyStrings.merge((String)paramObject1, (String)paramObject2, paramBiFunction);
/*      */     }
/*      */     
/*  791 */     return super.merge(paramObject1, paramObject2, paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implCompute(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  795 */     if ((paramObject instanceof String)) {
/*  796 */       if (!checkLegacy(paramObject)) {
/*  797 */         return null;
/*      */       }
/*  799 */       this.legacyStrings.computeIfAbsent((String)paramObject, (Function)paramBiFunction);
/*      */     }
/*      */     
/*  802 */     return super.compute(paramObject, paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implComputeIfAbsent(Object paramObject, Function<? super Object, ? extends Object> paramFunction) {
/*  806 */     if ((paramObject instanceof String)) {
/*  807 */       if (!checkLegacy(paramObject)) {
/*  808 */         return null;
/*      */       }
/*  810 */       this.legacyStrings.computeIfAbsent((String)paramObject, paramFunction);
/*      */     }
/*      */     
/*  813 */     return super.computeIfAbsent(paramObject, paramFunction);
/*      */   }
/*      */   
/*      */   private Object implComputeIfPresent(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  817 */     if ((paramObject instanceof String)) {
/*  818 */       if (!checkLegacy(paramObject)) {
/*  819 */         return null;
/*      */       }
/*  821 */       this.legacyStrings.computeIfPresent((String)paramObject, paramBiFunction);
/*      */     }
/*      */     
/*  824 */     return super.computeIfPresent(paramObject, paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implPut(Object paramObject1, Object paramObject2) {
/*  828 */     if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
/*  829 */       if (!checkLegacy(paramObject1)) {
/*  830 */         return null;
/*      */       }
/*  832 */       this.legacyStrings.put((String)paramObject1, (String)paramObject2);
/*      */     }
/*  834 */     return super.put(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private Object implPutIfAbsent(Object paramObject1, Object paramObject2) {
/*  838 */     if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
/*  839 */       if (!checkLegacy(paramObject1)) {
/*  840 */         return null;
/*      */       }
/*  842 */       this.legacyStrings.putIfAbsent((String)paramObject1, (String)paramObject2);
/*      */     }
/*  844 */     return super.putIfAbsent(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private void implClear() {
/*  848 */     if (this.legacyStrings != null) {
/*  849 */       this.legacyStrings.clear();
/*      */     }
/*  851 */     if (this.legacyMap != null) {
/*  852 */       this.legacyMap.clear();
/*      */     }
/*  854 */     if (this.serviceMap != null) {
/*  855 */       this.serviceMap.clear();
/*      */     }
/*  857 */     this.legacyChanged = false;
/*  858 */     this.servicesChanged = false;
/*  859 */     this.serviceSet = null;
/*  860 */     super.clear();
/*  861 */     putId();
/*      */   }
/*      */   
/*      */   private static class ServiceKey {
/*      */     private final String type;
/*      */     private final String algorithm;
/*      */     private final String originalAlgorithm;
/*      */     
/*      */     private ServiceKey(String paramString1, String paramString2, boolean paramBoolean) {
/*  870 */       this.type = paramString1;
/*  871 */       this.originalAlgorithm = paramString2;
/*  872 */       paramString2 = paramString2.toUpperCase(Locale.ENGLISH);
/*  873 */       this.algorithm = (paramBoolean ? paramString2.intern() : paramString2);
/*      */     }
/*      */     
/*  876 */     public int hashCode() { return this.type.hashCode() + this.algorithm.hashCode(); }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/*  879 */       if (this == paramObject) {
/*  880 */         return true;
/*      */       }
/*  882 */       if (!(paramObject instanceof ServiceKey)) {
/*  883 */         return false;
/*      */       }
/*  885 */       ServiceKey localServiceKey = (ServiceKey)paramObject;
/*      */       
/*  887 */       return (this.type.equals(localServiceKey.type)) && (this.algorithm.equals(localServiceKey.algorithm));
/*      */     }
/*      */     
/*  890 */     boolean matches(String paramString1, String paramString2) { return (this.type == paramString1) && (this.originalAlgorithm == paramString2); }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void ensureLegacyParsed()
/*      */   {
/*  899 */     if ((!this.legacyChanged) || (this.legacyStrings == null)) {
/*  900 */       return;
/*      */     }
/*  902 */     this.serviceSet = null;
/*  903 */     if (this.legacyMap == null) {
/*  904 */       this.legacyMap = new LinkedHashMap();
/*      */     } else {
/*  906 */       this.legacyMap.clear();
/*      */     }
/*  908 */     for (Map.Entry localEntry : this.legacyStrings.entrySet()) {
/*  909 */       parseLegacyPut((String)localEntry.getKey(), (String)localEntry.getValue());
/*      */     }
/*  911 */     removeInvalidServices(this.legacyMap);
/*  912 */     this.legacyChanged = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void removeInvalidServices(Map<ServiceKey, Service> paramMap)
/*      */   {
/*  921 */     for (Iterator localIterator = paramMap.entrySet().iterator(); localIterator.hasNext();) {
/*  922 */       Service localService = (Service)((Map.Entry)localIterator.next()).getValue();
/*  923 */       if (!localService.isValid()) {
/*  924 */         localIterator.remove();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private String[] getTypeAndAlgorithm(String paramString) {
/*  930 */     int i = paramString.indexOf(".");
/*  931 */     if (i < 1) {
/*  932 */       if (debug != null) {
/*  933 */         debug.println("Ignoring invalid entry in provider " + this.name + ":" + paramString);
/*      */       }
/*      */       
/*  936 */       return null;
/*      */     }
/*  938 */     String str1 = paramString.substring(0, i);
/*  939 */     String str2 = paramString.substring(i + 1);
/*  940 */     return new String[] { str1, str2 };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  945 */   private static final int ALIAS_LENGTH = "Alg.Alias.".length();
/*      */   
/*      */   private void parseLegacyPut(String paramString1, String paramString2) { Object localObject1;
/*  948 */     Object localObject2; String str2; String str3; Object localObject3; Object localObject4; if (paramString1.toLowerCase(Locale.ENGLISH).startsWith("alg.alias."))
/*      */     {
/*      */ 
/*  951 */       localObject1 = paramString2;
/*  952 */       String str1 = paramString1.substring(ALIAS_LENGTH);
/*  953 */       localObject2 = getTypeAndAlgorithm(str1);
/*  954 */       if (localObject2 == null) {
/*  955 */         return;
/*      */       }
/*  957 */       str2 = getEngineName(localObject2[0]);
/*  958 */       str3 = localObject2[1].intern();
/*  959 */       localObject3 = new ServiceKey(str2, (String)localObject1, true, null);
/*  960 */       localObject4 = (Service)this.legacyMap.get(localObject3);
/*  961 */       if (localObject4 == null) {
/*  962 */         localObject4 = new Service(this, null);
/*  963 */         ((Service)localObject4).type = str2;
/*  964 */         ((Service)localObject4).algorithm = ((String)localObject1);
/*  965 */         this.legacyMap.put(localObject3, localObject4);
/*      */       }
/*  967 */       this.legacyMap.put(new ServiceKey(str2, str3, true, null), localObject4);
/*  968 */       ((Service)localObject4).addAlias(str3);
/*      */     } else {
/*  970 */       localObject1 = getTypeAndAlgorithm(paramString1);
/*  971 */       if (localObject1 == null) {
/*  972 */         return;
/*      */       }
/*  974 */       int i = localObject1[1].indexOf(' ');
/*  975 */       if (i == -1)
/*      */       {
/*  977 */         localObject2 = getEngineName(localObject1[0]);
/*  978 */         str2 = localObject1[1].intern();
/*  979 */         str3 = paramString2;
/*  980 */         localObject3 = new ServiceKey((String)localObject2, str2, true, null);
/*  981 */         localObject4 = (Service)this.legacyMap.get(localObject3);
/*  982 */         if (localObject4 == null) {
/*  983 */           localObject4 = new Service(this, null);
/*  984 */           ((Service)localObject4).type = ((String)localObject2);
/*  985 */           ((Service)localObject4).algorithm = str2;
/*  986 */           this.legacyMap.put(localObject3, localObject4);
/*      */         }
/*  988 */         ((Service)localObject4).className = str3;
/*      */       }
/*      */       else {
/*  991 */         localObject2 = paramString2;
/*  992 */         str2 = getEngineName(localObject1[0]);
/*  993 */         str3 = localObject1[1];
/*  994 */         localObject3 = str3.substring(0, i).intern();
/*  995 */         localObject4 = str3.substring(i + 1);
/*      */         
/*  997 */         while (((String)localObject4).startsWith(" ")) {
/*  998 */           localObject4 = ((String)localObject4).substring(1);
/*      */         }
/* 1000 */         localObject4 = ((String)localObject4).intern();
/* 1001 */         ServiceKey localServiceKey = new ServiceKey(str2, (String)localObject3, true, null);
/* 1002 */         Service localService = (Service)this.legacyMap.get(localServiceKey);
/* 1003 */         if (localService == null) {
/* 1004 */           localService = new Service(this, null);
/* 1005 */           localService.type = str2;
/* 1006 */           localService.algorithm = ((String)localObject3);
/* 1007 */           this.legacyMap.put(localServiceKey, localService);
/*      */         }
/* 1009 */         localService.addAttribute((String)localObject4, (String)localObject2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Service getService(String paramString1, String paramString2)
/*      */   {
/* 1035 */     checkInitialized();
/*      */     
/* 1037 */     ServiceKey localServiceKey = previousKey;
/* 1038 */     if (!localServiceKey.matches(paramString1, paramString2)) {
/* 1039 */       localServiceKey = new ServiceKey(paramString1, paramString2, false, null);
/* 1040 */       previousKey = localServiceKey;
/*      */     }
/* 1042 */     if (this.serviceMap != null) {
/* 1043 */       Service localService = (Service)this.serviceMap.get(localServiceKey);
/* 1044 */       if (localService != null) {
/* 1045 */         return localService;
/*      */       }
/*      */     }
/* 1048 */     ensureLegacyParsed();
/* 1049 */     return this.legacyMap != null ? (Service)this.legacyMap.get(localServiceKey) : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1058 */   private static volatile ServiceKey previousKey = new ServiceKey("", "", false, null);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Set<Service> getServices()
/*      */   {
/* 1071 */     checkInitialized();
/* 1072 */     if ((this.legacyChanged) || (this.servicesChanged)) {
/* 1073 */       this.serviceSet = null;
/*      */     }
/* 1075 */     if (this.serviceSet == null) {
/* 1076 */       ensureLegacyParsed();
/* 1077 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/* 1078 */       if (this.serviceMap != null) {
/* 1079 */         localLinkedHashSet.addAll(this.serviceMap.values());
/*      */       }
/* 1081 */       if (this.legacyMap != null) {
/* 1082 */         localLinkedHashSet.addAll(this.legacyMap.values());
/*      */       }
/* 1084 */       this.serviceSet = Collections.unmodifiableSet(localLinkedHashSet);
/* 1085 */       this.servicesChanged = false;
/*      */     }
/* 1087 */     return this.serviceSet;
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
/*      */   protected synchronized void putService(Service paramService)
/*      */   {
/* 1120 */     check("putProviderProperty." + this.name);
/* 1121 */     if (debug != null) {
/* 1122 */       debug.println(this.name + ".putService(): " + paramService);
/*      */     }
/* 1124 */     if (paramService == null) {
/* 1125 */       throw new NullPointerException();
/*      */     }
/* 1127 */     if (paramService.getProvider() != this) {
/* 1128 */       throw new IllegalArgumentException("service.getProvider() must match this Provider object");
/*      */     }
/*      */     
/* 1131 */     if (this.serviceMap == null) {
/* 1132 */       this.serviceMap = new LinkedHashMap();
/*      */     }
/* 1134 */     this.servicesChanged = true;
/* 1135 */     String str1 = paramService.getType();
/* 1136 */     String str2 = paramService.getAlgorithm();
/* 1137 */     ServiceKey localServiceKey = new ServiceKey(str1, str2, true, null);
/*      */     
/* 1139 */     implRemoveService((Service)this.serviceMap.get(localServiceKey));
/* 1140 */     this.serviceMap.put(localServiceKey, paramService);
/* 1141 */     for (String str3 : paramService.getAliases()) {
/* 1142 */       this.serviceMap.put(new ServiceKey(str1, str3, true, null), paramService);
/*      */     }
/* 1144 */     putPropertyStrings(paramService);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void putPropertyStrings(Service paramService)
/*      */   {
/* 1152 */     String str1 = paramService.getType();
/* 1153 */     String str2 = paramService.getAlgorithm();
/*      */     
/* 1155 */     super.put(str1 + "." + str2, paramService.getClassName());
/* 1156 */     for (Iterator localIterator = paramService.getAliases().iterator(); localIterator.hasNext();) { localObject = (String)localIterator.next();
/* 1157 */       super.put("Alg.Alias." + str1 + "." + (String)localObject, str2); }
/*      */     Object localObject;
/* 1159 */     for (localIterator = paramService.attributes.entrySet().iterator(); localIterator.hasNext();) { localObject = (Map.Entry)localIterator.next();
/* 1160 */       String str3 = str1 + "." + str2 + " " + ((Map.Entry)localObject).getKey();
/* 1161 */       super.put(str3, ((Map.Entry)localObject).getValue());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void removePropertyStrings(Service paramService)
/*      */   {
/* 1170 */     String str1 = paramService.getType();
/* 1171 */     String str2 = paramService.getAlgorithm();
/*      */     
/* 1173 */     super.remove(str1 + "." + str2);
/* 1174 */     for (Iterator localIterator = paramService.getAliases().iterator(); localIterator.hasNext();) { localObject = (String)localIterator.next();
/* 1175 */       super.remove("Alg.Alias." + str1 + "." + (String)localObject); }
/*      */     Object localObject;
/* 1177 */     for (localIterator = paramService.attributes.entrySet().iterator(); localIterator.hasNext();) { localObject = (Map.Entry)localIterator.next();
/* 1178 */       String str3 = str1 + "." + str2 + " " + ((Map.Entry)localObject).getKey();
/* 1179 */       super.remove(str3);
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
/*      */   protected synchronized void removeService(Service paramService)
/*      */   {
/* 1212 */     check("removeProviderProperty." + this.name);
/* 1213 */     if (debug != null) {
/* 1214 */       debug.println(this.name + ".removeService(): " + paramService);
/*      */     }
/* 1216 */     if (paramService == null) {
/* 1217 */       throw new NullPointerException();
/*      */     }
/* 1219 */     implRemoveService(paramService);
/*      */   }
/*      */   
/*      */   private void implRemoveService(Service paramService) {
/* 1223 */     if ((paramService == null) || (this.serviceMap == null)) {
/* 1224 */       return;
/*      */     }
/* 1226 */     String str1 = paramService.getType();
/* 1227 */     String str2 = paramService.getAlgorithm();
/* 1228 */     ServiceKey localServiceKey = new ServiceKey(str1, str2, false, null);
/* 1229 */     Service localService = (Service)this.serviceMap.get(localServiceKey);
/* 1230 */     if (paramService != localService) {
/* 1231 */       return;
/*      */     }
/* 1233 */     this.servicesChanged = true;
/* 1234 */     this.serviceMap.remove(localServiceKey);
/* 1235 */     for (String str3 : paramService.getAliases()) {
/* 1236 */       this.serviceMap.remove(new ServiceKey(str1, str3, false, null));
/*      */     }
/* 1238 */     removePropertyStrings(paramService);
/*      */   }
/*      */   
/*      */   private static class UString
/*      */   {
/*      */     final String string;
/*      */     final String lowerString;
/*      */     
/*      */     UString(String paramString) {
/* 1247 */       this.string = paramString;
/* 1248 */       this.lowerString = paramString.toLowerCase(Locale.ENGLISH);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1252 */       return this.lowerString.hashCode();
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/* 1256 */       if (this == paramObject) {
/* 1257 */         return true;
/*      */       }
/* 1259 */       if (!(paramObject instanceof UString)) {
/* 1260 */         return false;
/*      */       }
/* 1262 */       UString localUString = (UString)paramObject;
/* 1263 */       return this.lowerString.equals(localUString.lowerString);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1267 */       return this.string;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EngineDescription
/*      */   {
/*      */     final String name;
/*      */     final boolean supportsParameter;
/*      */     final String constructorParameterClassName;
/*      */     private volatile Class<?> constructorParameterClass;
/*      */     
/*      */     EngineDescription(String paramString1, boolean paramBoolean, String paramString2) {
/* 1279 */       this.name = paramString1;
/* 1280 */       this.supportsParameter = paramBoolean;
/* 1281 */       this.constructorParameterClassName = paramString2;
/*      */     }
/*      */     
/* 1284 */     Class<?> getConstructorParameterClass() throws ClassNotFoundException { Class localClass = this.constructorParameterClass;
/* 1285 */       if (localClass == null) {
/* 1286 */         localClass = Class.forName(this.constructorParameterClassName);
/* 1287 */         this.constructorParameterClass = localClass;
/*      */       }
/* 1289 */       return localClass;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void addEngine(String paramString1, boolean paramBoolean, String paramString2)
/*      */   {
/* 1297 */     EngineDescription localEngineDescription = new EngineDescription(paramString1, paramBoolean, paramString2);
/*      */     
/* 1299 */     knownEngines.put(paramString1.toLowerCase(Locale.ENGLISH), localEngineDescription);
/* 1300 */     knownEngines.put(paramString1, localEngineDescription);
/*      */   }
/*      */   
/*      */ 
/* 1304 */   private static final Map<String, EngineDescription> knownEngines = new HashMap();
/*      */   
/* 1306 */   static { addEngine("AlgorithmParameterGenerator", false, null);
/* 1307 */     addEngine("AlgorithmParameters", false, null);
/* 1308 */     addEngine("KeyFactory", false, null);
/* 1309 */     addEngine("KeyPairGenerator", false, null);
/* 1310 */     addEngine("KeyStore", false, null);
/* 1311 */     addEngine("MessageDigest", false, null);
/* 1312 */     addEngine("SecureRandom", false, null);
/* 1313 */     addEngine("Signature", true, null);
/* 1314 */     addEngine("CertificateFactory", false, null);
/* 1315 */     addEngine("CertPathBuilder", false, null);
/* 1316 */     addEngine("CertPathValidator", false, null);
/* 1317 */     addEngine("CertStore", false, "java.security.cert.CertStoreParameters");
/*      */     
/*      */ 
/* 1320 */     addEngine("Cipher", true, null);
/* 1321 */     addEngine("ExemptionMechanism", false, null);
/* 1322 */     addEngine("Mac", true, null);
/* 1323 */     addEngine("KeyAgreement", true, null);
/* 1324 */     addEngine("KeyGenerator", false, null);
/* 1325 */     addEngine("SecretKeyFactory", false, null);
/*      */     
/* 1327 */     addEngine("KeyManagerFactory", false, null);
/* 1328 */     addEngine("SSLContext", false, null);
/* 1329 */     addEngine("TrustManagerFactory", false, null);
/*      */     
/* 1331 */     addEngine("GssApiMechanism", false, null);
/*      */     
/* 1333 */     addEngine("SaslClientFactory", false, null);
/* 1334 */     addEngine("SaslServerFactory", false, null);
/*      */     
/* 1336 */     addEngine("Policy", false, "java.security.Policy$Parameters");
/*      */     
/*      */ 
/* 1339 */     addEngine("Configuration", false, "javax.security.auth.login.Configuration$Parameters");
/*      */     
/*      */ 
/* 1342 */     addEngine("XMLSignatureFactory", false, null);
/* 1343 */     addEngine("KeyInfoFactory", false, null);
/* 1344 */     addEngine("TransformService", false, null);
/*      */     
/* 1346 */     addEngine("TerminalFactory", false, "java.lang.Object");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getEngineName(String paramString)
/*      */   {
/* 1354 */     EngineDescription localEngineDescription = (EngineDescription)knownEngines.get(paramString);
/* 1355 */     if (localEngineDescription == null) {
/* 1356 */       localEngineDescription = (EngineDescription)knownEngines.get(paramString.toLowerCase(Locale.ENGLISH));
/*      */     }
/* 1358 */     return localEngineDescription == null ? paramString : localEngineDescription.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class Service
/*      */   {
/*      */     private String type;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private String algorithm;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private String className;
/*      */     
/*      */ 
/*      */ 
/*      */     private final Provider provider;
/*      */     
/*      */ 
/*      */ 
/*      */     private List<String> aliases;
/*      */     
/*      */ 
/*      */ 
/*      */     private Map<Provider.UString, String> attributes;
/*      */     
/*      */ 
/*      */ 
/*      */     private volatile Reference<Class<?>> classRef;
/*      */     
/*      */ 
/*      */ 
/*      */     private volatile Boolean hasKeyAttributes;
/*      */     
/*      */ 
/*      */ 
/*      */     private String[] supportedFormats;
/*      */     
/*      */ 
/*      */ 
/*      */     private Class[] supportedClasses;
/*      */     
/*      */ 
/*      */ 
/*      */     private boolean registered;
/*      */     
/*      */ 
/*      */ 
/* 1413 */     private static final Class<?>[] CLASS0 = new Class[0];
/*      */     
/*      */ 
/*      */ 
/*      */     private Service(Provider paramProvider)
/*      */     {
/* 1419 */       this.provider = paramProvider;
/* 1420 */       this.aliases = Collections.emptyList();
/* 1421 */       this.attributes = Collections.emptyMap();
/*      */     }
/*      */     
/*      */     private boolean isValid() {
/* 1425 */       return (this.type != null) && (this.algorithm != null) && (this.className != null);
/*      */     }
/*      */     
/*      */     private void addAlias(String paramString) {
/* 1429 */       if (this.aliases.isEmpty()) {
/* 1430 */         this.aliases = new ArrayList(2);
/*      */       }
/* 1432 */       this.aliases.add(paramString);
/*      */     }
/*      */     
/*      */     void addAttribute(String paramString1, String paramString2) {
/* 1436 */       if (this.attributes.isEmpty()) {
/* 1437 */         this.attributes = new HashMap(8);
/*      */       }
/* 1439 */       this.attributes.put(new Provider.UString(paramString1), paramString2);
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
/*      */     public Service(Provider paramProvider, String paramString1, String paramString2, String paramString3, List<String> paramList, Map<String, String> paramMap)
/*      */     {
/* 1459 */       if ((paramProvider == null) || (paramString1 == null) || (paramString2 == null) || (paramString3 == null))
/*      */       {
/* 1461 */         throw new NullPointerException();
/*      */       }
/* 1463 */       this.provider = paramProvider;
/* 1464 */       this.type = Provider.getEngineName(paramString1);
/* 1465 */       this.algorithm = paramString2;
/* 1466 */       this.className = paramString3;
/* 1467 */       if (paramList == null) {
/* 1468 */         this.aliases = Collections.emptyList();
/*      */       } else {
/* 1470 */         this.aliases = new ArrayList(paramList);
/*      */       }
/* 1472 */       if (paramMap == null) {
/* 1473 */         this.attributes = Collections.emptyMap();
/*      */       } else {
/* 1475 */         this.attributes = new HashMap();
/* 1476 */         for (Map.Entry localEntry : paramMap.entrySet()) {
/* 1477 */           this.attributes.put(new Provider.UString((String)localEntry.getKey()), localEntry.getValue());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final String getType()
/*      */     {
/* 1488 */       return this.type;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final String getAlgorithm()
/*      */     {
/* 1498 */       return this.algorithm;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final Provider getProvider()
/*      */     {
/* 1507 */       return this.provider;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final String getClassName()
/*      */     {
/* 1516 */       return this.className;
/*      */     }
/*      */     
/*      */     private final List<String> getAliases()
/*      */     {
/* 1521 */       return this.aliases;
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
/*      */     public final String getAttribute(String paramString)
/*      */     {
/* 1536 */       if (paramString == null) {
/* 1537 */         throw new NullPointerException();
/*      */       }
/* 1539 */       return (String)this.attributes.get(new Provider.UString(paramString));
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
/*      */     public Object newInstance(Object paramObject)
/*      */       throws NoSuchAlgorithmException
/*      */     {
/* 1570 */       if (!this.registered) {
/* 1571 */         if (this.provider.getService(this.type, this.algorithm) != this)
/*      */         {
/*      */ 
/* 1574 */           throw new NoSuchAlgorithmException("Service not registered with Provider " + this.provider.getName() + ": " + this);
/*      */         }
/* 1576 */         this.registered = true;
/*      */       }
/*      */       try {
/* 1579 */         Provider.EngineDescription localEngineDescription = (Provider.EngineDescription)Provider.knownEngines.get(this.type);
/* 1580 */         if (localEngineDescription == null)
/*      */         {
/*      */ 
/*      */ 
/* 1584 */           return newInstanceGeneric(paramObject);
/*      */         }
/* 1586 */         if (localEngineDescription.constructorParameterClassName == null) {
/* 1587 */           if (paramObject != null) {
/* 1588 */             throw new InvalidParameterException("constructorParameter not used with " + this.type + " engines");
/*      */           }
/*      */           
/*      */ 
/* 1592 */           localClass = getImplClass();
/* 1593 */           localObject = new Class[0];
/* 1594 */           localConstructor = localClass.getConstructor((Class[])localObject);
/* 1595 */           return localConstructor.newInstance(new Object[0]);
/*      */         }
/* 1597 */         Class localClass = localEngineDescription.getConstructorParameterClass();
/* 1598 */         if (paramObject != null) {
/* 1599 */           localObject = paramObject.getClass();
/* 1600 */           if (!localClass.isAssignableFrom((Class)localObject))
/*      */           {
/*      */ 
/* 1603 */             throw new InvalidParameterException("constructorParameter must be instanceof " + localEngineDescription.constructorParameterClassName.replace('$', '.') + " for engine type " + this.type);
/*      */           }
/*      */         }
/*      */         
/* 1607 */         Object localObject = getImplClass();
/* 1608 */         Constructor localConstructor = ((Class)localObject).getConstructor(new Class[] { localClass });
/* 1609 */         return localConstructor.newInstance(new Object[] { paramObject });
/*      */       }
/*      */       catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 1612 */         throw localNoSuchAlgorithmException;
/*      */ 
/*      */       }
/*      */       catch (InvocationTargetException localInvocationTargetException)
/*      */       {
/* 1617 */         throw new NoSuchAlgorithmException("Error constructing implementation (algorithm: " + this.algorithm + ", provider: " + this.provider.getName() + ", class: " + this.className + ")", localInvocationTargetException.getCause());
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1621 */         throw new NoSuchAlgorithmException("Error constructing implementation (algorithm: " + this.algorithm + ", provider: " + this.provider.getName() + ", class: " + this.className + ")", localException);
/*      */       }
/*      */     }
/*      */     
/*      */     private Class<?> getImplClass() throws NoSuchAlgorithmException
/*      */     {
/*      */       try
/*      */       {
/* 1629 */         Reference localReference = this.classRef;
/* 1630 */         Class localClass = localReference == null ? null : (Class)localReference.get();
/* 1631 */         if (localClass == null) {
/* 1632 */           ClassLoader localClassLoader = this.provider.getClass().getClassLoader();
/* 1633 */           if (localClassLoader == null) {
/* 1634 */             localClass = Class.forName(this.className);
/*      */           } else {
/* 1636 */             localClass = localClassLoader.loadClass(this.className);
/*      */           }
/* 1638 */           if (!Modifier.isPublic(localClass.getModifiers()))
/*      */           {
/*      */ 
/* 1641 */             throw new NoSuchAlgorithmException("class configured for " + this.type + " (provider: " + this.provider.getName() + ") is not public.");
/*      */           }
/* 1643 */           this.classRef = new WeakReference(localClass);
/*      */         }
/* 1645 */         return localClass;
/*      */       }
/*      */       catch (ClassNotFoundException localClassNotFoundException)
/*      */       {
/* 1649 */         throw new NoSuchAlgorithmException("class configured for " + this.type + " (provider: " + this.provider.getName() + ") cannot be found.", localClassNotFoundException);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Object newInstanceGeneric(Object paramObject)
/*      */       throws Exception
/*      */     {
/* 1660 */       Class localClass1 = getImplClass();
/* 1661 */       if (paramObject == null) {
/*      */         try
/*      */         {
/* 1664 */           Class[] arrayOfClass1 = new Class[0];
/* 1665 */           localObject1 = localClass1.getConstructor(arrayOfClass1);
/* 1666 */           return ((Constructor)localObject1).newInstance(new Object[0]);
/*      */         } catch (NoSuchMethodException localNoSuchMethodException) {
/* 1668 */           throw new NoSuchAlgorithmException("No public no-arg constructor found in class " + this.className);
/*      */         }
/*      */       }
/*      */       
/* 1672 */       Class localClass2 = paramObject.getClass();
/* 1673 */       Object localObject1 = localClass1.getConstructors();
/*      */       
/*      */ 
/* 1676 */       for (Object localObject3 : localObject1) {
/* 1677 */         Class[] arrayOfClass2 = ((Constructor)localObject3).getParameterTypes();
/* 1678 */         if (arrayOfClass2.length == 1)
/*      */         {
/*      */ 
/* 1681 */           if (arrayOfClass2[0].isAssignableFrom(localClass2))
/*      */           {
/*      */ 
/* 1684 */             return ((Constructor)localObject3).newInstance(new Object[] { paramObject }); }
/*      */         }
/*      */       }
/* 1687 */       throw new NoSuchAlgorithmException("No public constructor matching " + localClass2.getName() + " found in class " + this.className);
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
/*      */     public boolean supportsParameter(Object paramObject)
/*      */     {
/* 1718 */       Provider.EngineDescription localEngineDescription = (Provider.EngineDescription)Provider.knownEngines.get(this.type);
/* 1719 */       if (localEngineDescription == null)
/*      */       {
/* 1721 */         return true;
/*      */       }
/* 1723 */       if (!localEngineDescription.supportsParameter) {
/* 1724 */         throw new InvalidParameterException("supportsParameter() not used with " + this.type + " engines");
/*      */       }
/*      */       
/*      */ 
/* 1728 */       if ((paramObject != null) && (!(paramObject instanceof Key))) {
/* 1729 */         throw new InvalidParameterException("Parameter must be instanceof Key for engine " + this.type);
/*      */       }
/*      */       
/* 1732 */       if (!hasKeyAttributes()) {
/* 1733 */         return true;
/*      */       }
/* 1735 */       if (paramObject == null) {
/* 1736 */         return false;
/*      */       }
/* 1738 */       Key localKey = (Key)paramObject;
/* 1739 */       if (supportsKeyFormat(localKey)) {
/* 1740 */         return true;
/*      */       }
/* 1742 */       if (supportsKeyClass(localKey)) {
/* 1743 */         return true;
/*      */       }
/* 1745 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private boolean hasKeyAttributes()
/*      */     {
/* 1753 */       Boolean localBoolean = this.hasKeyAttributes;
/* 1754 */       if (localBoolean == null) {
/* 1755 */         synchronized (this)
/*      */         {
/* 1757 */           String str1 = getAttribute("SupportedKeyFormats");
/* 1758 */           if (str1 != null) {
/* 1759 */             this.supportedFormats = str1.split("\\|");
/*      */           }
/* 1761 */           str1 = getAttribute("SupportedKeyClasses");
/* 1762 */           if (str1 != null) {
/* 1763 */             String[] arrayOfString1 = str1.split("\\|");
/* 1764 */             ArrayList localArrayList = new ArrayList(arrayOfString1.length);
/*      */             
/* 1766 */             for (String str2 : arrayOfString1) {
/* 1767 */               Class localClass = getKeyClass(str2);
/* 1768 */               if (localClass != null) {
/* 1769 */                 localArrayList.add(localClass);
/*      */               }
/*      */             }
/* 1772 */             this.supportedClasses = ((Class[])localArrayList.toArray(CLASS0));
/*      */           }
/* 1774 */           boolean bool = (this.supportedFormats != null) || (this.supportedClasses != null);
/*      */           
/* 1776 */           localBoolean = Boolean.valueOf(bool);
/* 1777 */           this.hasKeyAttributes = localBoolean;
/*      */         }
/*      */       }
/* 1780 */       return localBoolean.booleanValue();
/*      */     }
/*      */     
/*      */     private Class<?> getKeyClass(String paramString)
/*      */     {
/*      */       try {
/* 1786 */         return Class.forName(paramString);
/*      */       }
/*      */       catch (ClassNotFoundException localClassNotFoundException1)
/*      */       {
/*      */         try {
/* 1791 */           ClassLoader localClassLoader = this.provider.getClass().getClassLoader();
/* 1792 */           if (localClassLoader != null) {
/* 1793 */             return localClassLoader.loadClass(paramString);
/*      */           }
/*      */         }
/*      */         catch (ClassNotFoundException localClassNotFoundException2) {}
/*      */       }
/* 1798 */       return null;
/*      */     }
/*      */     
/*      */     private boolean supportsKeyFormat(Key paramKey) {
/* 1802 */       if (this.supportedFormats == null) {
/* 1803 */         return false;
/*      */       }
/* 1805 */       String str1 = paramKey.getFormat();
/* 1806 */       if (str1 == null) {
/* 1807 */         return false;
/*      */       }
/* 1809 */       for (String str2 : this.supportedFormats) {
/* 1810 */         if (str2.equals(str1)) {
/* 1811 */           return true;
/*      */         }
/*      */       }
/* 1814 */       return false;
/*      */     }
/*      */     
/*      */     private boolean supportsKeyClass(Key paramKey) {
/* 1818 */       if (this.supportedClasses == null) {
/* 1819 */         return false;
/*      */       }
/* 1821 */       Class localClass1 = paramKey.getClass();
/* 1822 */       for (Class localClass2 : this.supportedClasses) {
/* 1823 */         if (localClass2.isAssignableFrom(localClass1)) {
/* 1824 */           return true;
/*      */         }
/*      */       }
/* 1827 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1837 */       String str1 = "\r\n  aliases: " + this.aliases.toString();
/*      */       
/* 1839 */       String str2 = "\r\n  attributes: " + this.attributes.toString();
/* 1840 */       return this.provider.getName() + ": " + this.type + "." + this.algorithm + " -> " + this.className + str1 + str2 + "\r\n";
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Provider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */