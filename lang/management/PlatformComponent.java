/*     */ package java.lang.management;
/*     */ 
/*     */ import com.sun.management.HotSpotDiagnosticMXBean;
/*     */ import com.sun.management.UnixOperatingSystemMXBean;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.ObjectName;
/*     */ import sun.management.ManagementFactoryHelper;
/*     */ import sun.management.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */  enum PlatformComponent
/*     */ {
/*  66 */   CLASS_LOADING("java.lang.management.ClassLoadingMXBean", "java.lang", "ClassLoading", 
/*     */   
/*  68 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */   COMPILATION("java.lang.management.CompilationMXBean", "java.lang", "Compilation", 
/*     */   
/*  81 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */   MEMORY("java.lang.management.MemoryMXBean", "java.lang", "Memory", 
/*     */   
/*  99 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */   GARBAGE_COLLECTOR("java.lang.management.GarbageCollectorMXBean", "java.lang", "GarbageCollector", 
/*     */   
/* 112 */     keyProperties(new String[] { "name" }), false, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 124 */   MEMORY_MANAGER("java.lang.management.MemoryManagerMXBean", "java.lang", "MemoryManager", 
/*     */   
/* 126 */     keyProperties(new String[] { "name" }), false, new MXBeanFetcher(), new PlatformComponent[] { GARBAGE_COLLECTOR }), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 138 */   MEMORY_POOL("java.lang.management.MemoryPoolMXBean", "java.lang", "MemoryPool", 
/*     */   
/* 140 */     keyProperties(new String[] { "name" }), false, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 151 */   OPERATING_SYSTEM("java.lang.management.OperatingSystemMXBean", "java.lang", "OperatingSystem", 
/*     */   
/* 153 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 164 */   RUNTIME("java.lang.management.RuntimeMXBean", "java.lang", "Runtime", 
/*     */   
/* 166 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */   THREADING("java.lang.management.ThreadMXBean", "java.lang", "Threading", 
/*     */   
/* 179 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 191 */   LOGGING("java.lang.management.PlatformLoggingMXBean", "java.util.logging", "Logging", 
/*     */   
/* 193 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */   BUFFER_POOL("java.lang.management.BufferPoolMXBean", "java.nio", "BufferPool", 
/*     */   
/* 211 */     keyProperties(new String[] { "name" }), false, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 225 */   SUN_GARBAGE_COLLECTOR("com.sun.management.GarbageCollectorMXBean", "java.lang", "GarbageCollector", 
/*     */   
/* 227 */     keyProperties(new String[] { "name" }), false, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 239 */   SUN_OPERATING_SYSTEM("com.sun.management.OperatingSystemMXBean", "java.lang", "OperatingSystem", 
/*     */   
/* 241 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 252 */   SUN_UNIX_OPERATING_SYSTEM("com.sun.management.UnixOperatingSystemMXBean", "java.lang", "OperatingSystem", 
/*     */   
/* 254 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 265 */   HOTSPOT_DIAGNOSTIC("com.sun.management.HotSpotDiagnosticMXBean", "com.sun.management", "HotSpotDiagnostic", 
/*     */   
/* 267 */     defaultKeyProperties(), true, new MXBeanFetcher(), new PlatformComponent[0]);
/*     */   
/*     */ 
/*     */   private final String mxbeanInterfaceName;
/*     */   
/*     */   private final String domain;
/*     */   
/*     */   private final String type;
/*     */   
/*     */   private final Set<String> keyProperties;
/*     */   
/*     */   private final MXBeanFetcher<?> fetcher;
/*     */   
/*     */   private final PlatformComponent[] subComponents;
/*     */   
/*     */   private final boolean singleton;
/*     */   private static Set<String> defaultKeyProps;
/*     */   private static Map<String, PlatformComponent> enumMap;
/*     */   private static final long serialVersionUID = 6992337162326171013L;
/*     */   
/*     */   private static <T extends GarbageCollectorMXBean> List<T> getGcMXBeanList(Class<T> paramClass)
/*     */   {
/* 289 */     List localList = ManagementFactoryHelper.getGarbageCollectorMXBeans();
/* 290 */     ArrayList localArrayList = new ArrayList(localList.size());
/* 291 */     for (GarbageCollectorMXBean localGarbageCollectorMXBean : localList) {
/* 292 */       if (paramClass.isInstance(localGarbageCollectorMXBean)) {
/* 293 */         localArrayList.add(paramClass.cast(localGarbageCollectorMXBean));
/*     */       }
/*     */     }
/* 296 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <T extends OperatingSystemMXBean> List<T> getOSMXBeanList(Class<T> paramClass)
/*     */   {
/* 305 */     OperatingSystemMXBean localOperatingSystemMXBean = ManagementFactoryHelper.getOperatingSystemMXBean();
/* 306 */     if (paramClass.isInstance(localOperatingSystemMXBean)) {
/* 307 */       return Collections.singletonList(paramClass.cast(localOperatingSystemMXBean));
/*     */     }
/* 309 */     return Collections.emptyList();
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
/*     */   private PlatformComponent(String paramString1, String paramString2, String paramString3, Set<String> paramSet, boolean paramBoolean, MXBeanFetcher<?> paramMXBeanFetcher, PlatformComponent... paramVarArgs)
/*     */   {
/* 327 */     this.mxbeanInterfaceName = paramString1;
/* 328 */     this.domain = paramString2;
/* 329 */     this.type = paramString3;
/* 330 */     this.keyProperties = paramSet;
/* 331 */     this.singleton = paramBoolean;
/* 332 */     this.fetcher = paramMXBeanFetcher;
/* 333 */     this.subComponents = paramVarArgs;
/*     */   }
/*     */   
/*     */   private static Set<String> defaultKeyProperties()
/*     */   {
/* 338 */     if (defaultKeyProps == null) {
/* 339 */       defaultKeyProps = Collections.singleton("type");
/*     */     }
/* 341 */     return defaultKeyProps;
/*     */   }
/*     */   
/*     */   private static Set<String> keyProperties(String... paramVarArgs) {
/* 345 */     HashSet localHashSet = new HashSet();
/* 346 */     localHashSet.add("type");
/* 347 */     for (String str : paramVarArgs) {
/* 348 */       localHashSet.add(str);
/*     */     }
/* 350 */     return localHashSet;
/*     */   }
/*     */   
/*     */   boolean isSingleton() {
/* 354 */     return this.singleton;
/*     */   }
/*     */   
/*     */   String getMXBeanInterfaceName() {
/* 358 */     return this.mxbeanInterfaceName;
/*     */   }
/*     */   
/*     */ 
/*     */   Class<? extends PlatformManagedObject> getMXBeanInterface()
/*     */   {
/*     */     try
/*     */     {
/* 366 */       return Class.forName(this.mxbeanInterfaceName, false, PlatformManagedObject.class
/* 367 */         .getClassLoader());
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 369 */       throw new AssertionError(localClassNotFoundException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   <T extends PlatformManagedObject> List<T> getMXBeans(Class<T> paramClass)
/*     */   {
/* 377 */     return this.fetcher.getMXBeans();
/*     */   }
/*     */   
/*     */   <T extends PlatformManagedObject> T getSingletonMXBean(Class<T> paramClass)
/*     */   {
/* 382 */     if (!this.singleton) {
/* 383 */       throw new IllegalArgumentException(this.mxbeanInterfaceName + " can have zero or more than one instances");
/*     */     }
/*     */     
/* 386 */     List localList = getMXBeans(paramClass);
/* 387 */     assert (localList.size() == 1);
/* 388 */     return localList.isEmpty() ? null : (PlatformManagedObject)localList.get(0);
/*     */   }
/*     */   
/*     */ 
/*     */   <T extends PlatformManagedObject> T getSingletonMXBean(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass)
/*     */     throws IOException
/*     */   {
/* 395 */     if (!this.singleton) {
/* 396 */       throw new IllegalArgumentException(this.mxbeanInterfaceName + " can have zero or more than one instances");
/*     */     }
/*     */     
/*     */ 
/* 400 */     assert (this.keyProperties.size() == 1);
/* 401 */     String str = this.domain + ":type=" + this.type;
/* 402 */     return (PlatformManagedObject)ManagementFactory.newPlatformMXBeanProxy(paramMBeanServerConnection, str, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   <T extends PlatformManagedObject> List<T> getMXBeans(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass)
/*     */     throws IOException
/*     */   {
/* 411 */     ArrayList localArrayList = new ArrayList();
/* 412 */     for (ObjectName localObjectName : getObjectNames(paramMBeanServerConnection)) {
/* 413 */       localArrayList.add(
/* 414 */         ManagementFactory.newPlatformMXBeanProxy(paramMBeanServerConnection, localObjectName
/* 415 */         .getCanonicalName(), paramClass));
/*     */     }
/*     */     
/*     */ 
/* 419 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private Set<ObjectName> getObjectNames(MBeanServerConnection paramMBeanServerConnection)
/*     */     throws IOException
/*     */   {
/* 425 */     String str = this.domain + ":type=" + this.type;
/* 426 */     if (this.keyProperties.size() > 1)
/*     */     {
/* 428 */       str = str + ",*";
/*     */     }
/* 430 */     ObjectName localObjectName = Util.newObjectName(str);
/* 431 */     Set localSet = paramMBeanServerConnection.queryNames(localObjectName, null);
/* 432 */     for (PlatformComponent localPlatformComponent : this.subComponents) {
/* 433 */       localSet.addAll(localPlatformComponent.getObjectNames(paramMBeanServerConnection));
/*     */     }
/* 435 */     return localSet;
/*     */   }
/*     */   
/*     */ 
/*     */   private static synchronized void ensureInitialized()
/*     */   {
/* 441 */     if (enumMap == null) {
/* 442 */       enumMap = new HashMap();
/* 443 */       for (PlatformComponent localPlatformComponent : values())
/*     */       {
/*     */ 
/* 446 */         enumMap.put(localPlatformComponent.getMXBeanInterfaceName(), localPlatformComponent);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean isPlatformMXBean(String paramString) {
/* 452 */     ensureInitialized();
/* 453 */     return enumMap.containsKey(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   static <T extends PlatformManagedObject> PlatformComponent getPlatformComponent(Class<T> paramClass)
/*     */   {
/* 459 */     ensureInitialized();
/* 460 */     String str = paramClass.getName();
/* 461 */     PlatformComponent localPlatformComponent = (PlatformComponent)enumMap.get(str);
/* 462 */     if ((localPlatformComponent != null) && (localPlatformComponent.getMXBeanInterface() == paramClass))
/* 463 */       return localPlatformComponent;
/* 464 */     return null;
/*     */   }
/*     */   
/*     */   static abstract interface MXBeanFetcher<T extends PlatformManagedObject>
/*     */   {
/*     */     public abstract List<T> getMXBeans();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/PlatformComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */