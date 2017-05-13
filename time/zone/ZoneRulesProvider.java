/*     */ package java.time.zone;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Objects;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ZoneRulesProvider
/*     */ {
/* 134 */   private static final CopyOnWriteArrayList<ZoneRulesProvider> PROVIDERS = new CopyOnWriteArrayList();
/*     */   
/*     */ 
/*     */ 
/* 138 */   private static final ConcurrentMap<String, ZoneRulesProvider> ZONES = new ConcurrentHashMap(512, 0.75F, 2);
/*     */   
/*     */ 
/*     */   static
/*     */   {
/* 143 */     ArrayList localArrayList = new ArrayList();
/* 144 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/* 146 */         String str = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
/* 147 */         if (str != null) {
/*     */           try {
/* 149 */             Class localClass = Class.forName(str, true, ClassLoader.getSystemClassLoader());
/* 150 */             ZoneRulesProvider localZoneRulesProvider = (ZoneRulesProvider)ZoneRulesProvider.class.cast(localClass.newInstance());
/* 151 */             ZoneRulesProvider.registerProvider(localZoneRulesProvider);
/* 152 */             this.val$loaded.add(localZoneRulesProvider);
/*     */           } catch (Exception localException) {
/* 154 */             throw new Error(localException);
/*     */           }
/*     */         } else {
/* 157 */           ZoneRulesProvider.registerProvider(new TzdbZoneRulesProvider());
/*     */         }
/* 159 */         return null;
/*     */       }
/*     */       
/* 162 */     });
/* 163 */     ServiceLoader localServiceLoader = ServiceLoader.load(ZoneRulesProvider.class, ClassLoader.getSystemClassLoader());
/* 164 */     Iterator localIterator1 = localServiceLoader.iterator();
/* 165 */     while (localIterator1.hasNext()) {
/*     */       ZoneRulesProvider localZoneRulesProvider1;
/*     */       try {
/* 168 */         localZoneRulesProvider1 = (ZoneRulesProvider)localIterator1.next();
/*     */       } catch (ServiceConfigurationError localServiceConfigurationError) {}
/* 170 */       if (!(localServiceConfigurationError.getCause() instanceof SecurityException))
/*     */       {
/*     */ 
/* 173 */         throw localServiceConfigurationError;
/*     */         
/* 175 */         int i = 0;
/* 176 */         for (ZoneRulesProvider localZoneRulesProvider2 : localArrayList) {
/* 177 */           if (localZoneRulesProvider2.getClass() == localZoneRulesProvider1.getClass()) {
/* 178 */             i = 1;
/*     */           }
/*     */         }
/* 181 */         if (i == 0) {
/* 182 */           registerProvider0(localZoneRulesProvider1);
/* 183 */           localArrayList.add(localZoneRulesProvider1);
/*     */         }
/*     */       }
/*     */     }
/* 187 */     PROVIDERS.addAll(localArrayList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Set<String> getAvailableZoneIds()
/*     */   {
/* 199 */     return new HashSet(ZONES.keySet());
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
/*     */   public static ZoneRules getRules(String paramString, boolean paramBoolean)
/*     */   {
/* 226 */     Objects.requireNonNull(paramString, "zoneId");
/* 227 */     return getProvider(paramString).provideRules(paramString, paramBoolean);
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
/*     */   public static NavigableMap<String, ZoneRules> getVersions(String paramString)
/*     */   {
/* 255 */     Objects.requireNonNull(paramString, "zoneId");
/* 256 */     return getProvider(paramString).provideVersions(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static ZoneRulesProvider getProvider(String paramString)
/*     */   {
/* 267 */     ZoneRulesProvider localZoneRulesProvider = (ZoneRulesProvider)ZONES.get(paramString);
/* 268 */     if (localZoneRulesProvider == null) {
/* 269 */       if (ZONES.isEmpty()) {
/* 270 */         throw new ZoneRulesException("No time-zone data files registered");
/*     */       }
/* 272 */       throw new ZoneRulesException("Unknown time-zone ID: " + paramString);
/*     */     }
/* 274 */     return localZoneRulesProvider;
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
/*     */   public static void registerProvider(ZoneRulesProvider paramZoneRulesProvider)
/*     */   {
/* 294 */     Objects.requireNonNull(paramZoneRulesProvider, "provider");
/* 295 */     registerProvider0(paramZoneRulesProvider);
/* 296 */     PROVIDERS.add(paramZoneRulesProvider);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void registerProvider0(ZoneRulesProvider paramZoneRulesProvider)
/*     */   {
/* 306 */     for (String str : paramZoneRulesProvider.provideZoneIds()) {
/* 307 */       Objects.requireNonNull(str, "zoneId");
/* 308 */       ZoneRulesProvider localZoneRulesProvider = (ZoneRulesProvider)ZONES.putIfAbsent(str, paramZoneRulesProvider);
/* 309 */       if (localZoneRulesProvider != null) {
/* 310 */         throw new ZoneRulesException("Unable to register zone as one already registered with that ID: " + str + ", currently loading from provider: " + paramZoneRulesProvider);
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
/*     */   public static boolean refresh()
/*     */   {
/* 341 */     boolean bool = false;
/* 342 */     for (ZoneRulesProvider localZoneRulesProvider : PROVIDERS) {
/* 343 */       bool |= localZoneRulesProvider.provideRefresh();
/*     */     }
/* 345 */     return bool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean provideRefresh()
/*     */   {
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract Set<String> provideZoneIds();
/*     */   
/*     */   protected abstract ZoneRules provideRules(String paramString, boolean paramBoolean);
/*     */   
/*     */   protected abstract NavigableMap<String, ZoneRules> provideVersions(String paramString);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/zone/ZoneRulesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */