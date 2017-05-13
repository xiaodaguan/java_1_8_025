/*     */ package java.lang.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.JMX;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.MBeanServerFactory;
/*     */ import javax.management.MBeanServerPermission;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.NotificationEmitter;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.StandardEmitterMBean;
/*     */ import javax.management.StandardMBean;
/*     */ import sun.management.ManagementFactoryHelper;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagementFactory
/*     */ {
/*     */   public static final String CLASS_LOADING_MXBEAN_NAME = "java.lang:type=ClassLoading";
/*     */   public static final String COMPILATION_MXBEAN_NAME = "java.lang:type=Compilation";
/*     */   public static final String MEMORY_MXBEAN_NAME = "java.lang:type=Memory";
/*     */   public static final String OPERATING_SYSTEM_MXBEAN_NAME = "java.lang:type=OperatingSystem";
/*     */   public static final String RUNTIME_MXBEAN_NAME = "java.lang:type=Runtime";
/*     */   public static final String THREAD_MXBEAN_NAME = "java.lang:type=Threading";
/*     */   public static final String GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE = "java.lang:type=GarbageCollector";
/*     */   public static final String MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE = "java.lang:type=MemoryManager";
/*     */   public static final String MEMORY_POOL_MXBEAN_DOMAIN_TYPE = "java.lang:type=MemoryPool";
/*     */   private static MBeanServer platformMBeanServer;
/*     */   private static final String NOTIF_EMITTER = "javax.management.NotificationEmitter";
/*     */   
/*     */   public static ClassLoadingMXBean getClassLoadingMXBean()
/*     */   {
/* 318 */     return ManagementFactoryHelper.getClassLoadingMXBean();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MemoryMXBean getMemoryMXBean()
/*     */   {
/* 328 */     return ManagementFactoryHelper.getMemoryMXBean();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ThreadMXBean getThreadMXBean()
/*     */   {
/* 338 */     return ManagementFactoryHelper.getThreadMXBean();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static RuntimeMXBean getRuntimeMXBean()
/*     */   {
/* 349 */     return ManagementFactoryHelper.getRuntimeMXBean();
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
/*     */   public static CompilationMXBean getCompilationMXBean()
/*     */   {
/* 362 */     return ManagementFactoryHelper.getCompilationMXBean();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static OperatingSystemMXBean getOperatingSystemMXBean()
/*     */   {
/* 373 */     return ManagementFactoryHelper.getOperatingSystemMXBean();
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
/*     */   public static List<MemoryPoolMXBean> getMemoryPoolMXBeans()
/*     */   {
/* 386 */     return ManagementFactoryHelper.getMemoryPoolMXBeans();
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
/*     */   public static List<MemoryManagerMXBean> getMemoryManagerMXBeans()
/*     */   {
/* 399 */     return ManagementFactoryHelper.getMemoryManagerMXBeans();
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
/*     */   public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans()
/*     */   {
/* 415 */     return ManagementFactoryHelper.getGarbageCollectorMXBeans();
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
/*     */   public static synchronized MBeanServer getPlatformMBeanServer()
/*     */   {
/* 461 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 462 */     Object localObject1; if (localSecurityManager != null) {
/* 463 */       localObject1 = new MBeanServerPermission("createMBeanServer");
/* 464 */       localSecurityManager.checkPermission((Permission)localObject1);
/*     */     }
/*     */     
/* 467 */     if (platformMBeanServer == null) {
/* 468 */       platformMBeanServer = MBeanServerFactory.createMBeanServer();
/* 469 */       for (Object localObject2 : PlatformComponent.values())
/*     */       {
/* 471 */         List localList = ((PlatformComponent)localObject2).getMXBeans(((PlatformComponent)localObject2).getMXBeanInterface());
/* 472 */         for (PlatformManagedObject localPlatformManagedObject : localList)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 482 */           if (!platformMBeanServer.isRegistered(localPlatformManagedObject.getObjectName())) {
/* 483 */             addMXBean(platformMBeanServer, localPlatformManagedObject);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 488 */       localObject1 = ManagementFactoryHelper.getPlatformDynamicMBeans();
/* 489 */       for (Map.Entry localEntry : ((HashMap)localObject1).entrySet()) {
/* 490 */         addDynamicMBean(platformMBeanServer, (DynamicMBean)localEntry.getValue(), (ObjectName)localEntry.getKey());
/*     */       }
/*     */     }
/* 493 */     return platformMBeanServer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T newPlatformMXBeanProxy(MBeanServerConnection paramMBeanServerConnection, String paramString, Class<T> paramClass)
/*     */     throws IOException
/*     */   {
/* 594 */     Class<T> localClass = paramClass;
/*     */     
/* 596 */     ClassLoader localClassLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public ClassLoader run() {
/* 598 */         return this.val$cls.getClassLoader();
/*     */       }
/*     */     });
/* 601 */     if (!VM.isSystemDomainLoader(localClassLoader)) {
/* 602 */       throw new IllegalArgumentException(paramString + " is not a platform MXBean");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 607 */       ObjectName localObjectName = new ObjectName(paramString);
/*     */       
/* 609 */       String str = paramClass.getName();
/* 610 */       if (!paramMBeanServerConnection.isInstanceOf(localObjectName, str)) {
/* 611 */         throw new IllegalArgumentException(paramString + " is not an instance of " + paramClass);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 617 */       boolean bool = paramMBeanServerConnection.isInstanceOf(localObjectName, "javax.management.NotificationEmitter");
/*     */       
/*     */ 
/* 620 */       return (T)JMX.newMXBeanProxy(paramMBeanServerConnection, localObjectName, paramClass, bool);
/*     */     }
/*     */     catch (InstanceNotFoundException|MalformedObjectNameException localInstanceNotFoundException) {
/* 623 */       throw new IllegalArgumentException(localInstanceNotFoundException);
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
/*     */ 
/*     */ 
/*     */   public static <T extends PlatformManagedObject> T getPlatformMXBean(Class<T> paramClass)
/*     */   {
/* 657 */     PlatformComponent localPlatformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 658 */     if (localPlatformComponent == null) {
/* 659 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     }
/* 661 */     if (!localPlatformComponent.isSingleton()) {
/* 662 */       throw new IllegalArgumentException(paramClass.getName() + " can have zero or more than one instances");
/*     */     }
/*     */     
/* 665 */     return localPlatformComponent.getSingletonMXBean(paramClass);
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
/*     */   public static <T extends PlatformManagedObject> List<T> getPlatformMXBeans(Class<T> paramClass)
/*     */   {
/* 692 */     PlatformComponent localPlatformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 693 */     if (localPlatformComponent == null) {
/* 694 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     }
/* 696 */     return Collections.unmodifiableList(localPlatformComponent.getMXBeans(paramClass));
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
/*     */   public static <T extends PlatformManagedObject> T getPlatformMXBean(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass)
/*     */     throws IOException
/*     */   {
/* 739 */     PlatformComponent localPlatformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 740 */     if (localPlatformComponent == null) {
/* 741 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     }
/* 743 */     if (!localPlatformComponent.isSingleton()) {
/* 744 */       throw new IllegalArgumentException(paramClass.getName() + " can have zero or more than one instances");
/*     */     }
/* 746 */     return localPlatformComponent.getSingletonMXBean(paramMBeanServerConnection, paramClass);
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
/*     */   public static <T extends PlatformManagedObject> List<T> getPlatformMXBeans(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass)
/*     */     throws IOException
/*     */   {
/* 782 */     PlatformComponent localPlatformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 783 */     if (localPlatformComponent == null) {
/* 784 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     }
/*     */     
/* 787 */     return Collections.unmodifiableList(localPlatformComponent.getMXBeans(paramMBeanServerConnection, paramClass));
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
/*     */   public static Set<Class<? extends PlatformManagedObject>> getPlatformManagementInterfaces()
/*     */   {
/* 806 */     HashSet localHashSet = new HashSet();
/*     */     
/* 808 */     for (PlatformComponent localPlatformComponent : PlatformComponent.values()) {
/* 809 */       localHashSet.add(localPlatformComponent.getMXBeanInterface());
/*     */     }
/* 811 */     return Collections.unmodifiableSet(localHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void addMXBean(final MBeanServer paramMBeanServer, PlatformManagedObject paramPlatformManagedObject)
/*     */   {
/*     */     try
/*     */     {
/* 823 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Void run() throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */         {
/*     */           Object localObject;
/* 828 */           if ((this.val$pmo instanceof DynamicMBean)) {
/* 829 */             localObject = (DynamicMBean)DynamicMBean.class.cast(this.val$pmo);
/* 830 */           } else if ((this.val$pmo instanceof NotificationEmitter)) {
/* 831 */             localObject = new StandardEmitterMBean(this.val$pmo, null, true, (NotificationEmitter)this.val$pmo);
/*     */           } else {
/* 833 */             localObject = new StandardMBean(this.val$pmo, null, true);
/*     */           }
/*     */           
/* 836 */           paramMBeanServer.registerMBean(localObject, this.val$pmo.getObjectName());
/* 837 */           return null;
/*     */         }
/*     */       });
/*     */     } catch (PrivilegedActionException localPrivilegedActionException) {
/* 841 */       throw new RuntimeException(localPrivilegedActionException.getException());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void addDynamicMBean(MBeanServer paramMBeanServer, final DynamicMBean paramDynamicMBean, final ObjectName paramObjectName)
/*     */   {
/*     */     try
/*     */     {
/* 852 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Void run()
/*     */           throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */         {
/* 857 */           this.val$mbs.registerMBean(paramDynamicMBean, paramObjectName);
/* 858 */           return null;
/*     */         }
/*     */       });
/*     */     } catch (PrivilegedActionException localPrivilegedActionException) {
/* 862 */       throw new RuntimeException(localPrivilegedActionException.getException());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/management/ManagementFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */