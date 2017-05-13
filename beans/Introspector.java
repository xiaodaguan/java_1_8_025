/*      */ package java.beans;
/*      */ 
/*      */ import com.sun.beans.TypeResolver;
/*      */ import com.sun.beans.WeakCache;
/*      */ import com.sun.beans.finder.BeanInfoFinder;
/*      */ import com.sun.beans.finder.ClassFinder;
/*      */ import com.sun.beans.finder.MethodFinder;
/*      */ import java.awt.Component;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.EventListener;
/*      */ import java.util.EventObject;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TooManyListenersException;
/*      */ import java.util.TreeMap;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Introspector
/*      */ {
/*      */   public static final int USE_ALL_BEANINFO = 1;
/*      */   public static final int IGNORE_IMMEDIATE_BEANINFO = 2;
/*      */   public static final int IGNORE_ALL_BEANINFO = 3;
/*  110 */   private static final WeakCache<Class<?>, Method[]> declaredMethodCache = new WeakCache();
/*      */   
/*      */   private Class<?> beanClass;
/*      */   
/*      */   private BeanInfo explicitBeanInfo;
/*      */   private BeanInfo superBeanInfo;
/*      */   private BeanInfo[] additionalBeanInfo;
/*  117 */   private boolean propertyChangeSource = false;
/*  118 */   private static Class<EventListener> eventListenerType = EventListener.class;
/*      */   
/*      */   private String defaultEventName;
/*      */   
/*      */   private String defaultPropertyName;
/*  123 */   private int defaultEventIndex = -1;
/*  124 */   private int defaultPropertyIndex = -1;
/*      */   
/*      */ 
/*      */   private Map<String, MethodDescriptor> methods;
/*      */   
/*      */ 
/*      */   private Map<String, PropertyDescriptor> properties;
/*      */   
/*      */ 
/*      */   private Map<String, EventSetDescriptor> events;
/*      */   
/*  135 */   private static final EventSetDescriptor[] EMPTY_EVENTSETDESCRIPTORS = new EventSetDescriptor[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final String ADD_PREFIX = "add";
/*      */   
/*      */ 
/*      */ 
/*      */   static final String REMOVE_PREFIX = "remove";
/*      */   
/*      */ 
/*      */ 
/*      */   static final String GET_PREFIX = "get";
/*      */   
/*      */ 
/*      */ 
/*      */   static final String SET_PREFIX = "set";
/*      */   
/*      */ 
/*      */ 
/*      */   static final String IS_PREFIX = "is";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass)
/*      */     throws IntrospectionException
/*      */   {
/*  164 */     if (!ReflectUtil.isPackageAccessible(paramClass)) {
/*  165 */       return new Introspector(paramClass, null, 1).getBeanInfo();
/*      */     }
/*  167 */     ThreadGroupContext localThreadGroupContext = ThreadGroupContext.getContext();
/*      */     BeanInfo localBeanInfo;
/*  169 */     synchronized (declaredMethodCache) {
/*  170 */       localBeanInfo = localThreadGroupContext.getBeanInfo(paramClass);
/*      */     }
/*  172 */     if (localBeanInfo == null) {
/*  173 */       localBeanInfo = new Introspector(paramClass, null, 1).getBeanInfo();
/*  174 */       synchronized (declaredMethodCache) {
/*  175 */         localThreadGroupContext.putBeanInfo(paramClass, localBeanInfo);
/*      */       }
/*      */     }
/*  178 */     return localBeanInfo;
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
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass, int paramInt)
/*      */     throws IntrospectionException
/*      */   {
/*  204 */     return getBeanInfo(paramClass, null, paramInt);
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
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass1, Class<?> paramClass2)
/*      */     throws IntrospectionException
/*      */   {
/*  224 */     return getBeanInfo(paramClass1, paramClass2, 1);
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
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass1, Class<?> paramClass2, int paramInt)
/*      */     throws IntrospectionException
/*      */   {
/*      */     BeanInfo localBeanInfo;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  258 */     if ((paramClass2 == null) && (paramInt == 1))
/*      */     {
/*  260 */       localBeanInfo = getBeanInfo(paramClass1);
/*      */     } else {
/*  262 */       localBeanInfo = new Introspector(paramClass1, paramClass2, paramInt).getBeanInfo();
/*      */     }
/*  264 */     return localBeanInfo;
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
/*      */   public static String decapitalize(String paramString)
/*      */   {
/*  285 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  286 */       return paramString;
/*      */     }
/*  288 */     if ((paramString.length() > 1) && (Character.isUpperCase(paramString.charAt(1))) && 
/*  289 */       (Character.isUpperCase(paramString.charAt(0)))) {
/*  290 */       return paramString;
/*      */     }
/*  292 */     char[] arrayOfChar = paramString.toCharArray();
/*  293 */     arrayOfChar[0] = Character.toLowerCase(arrayOfChar[0]);
/*  294 */     return new String(arrayOfChar);
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
/*      */   public static String[] getBeanInfoSearchPath()
/*      */   {
/*  308 */     return ThreadGroupContext.getContext().getBeanInfoFinder().getPackages();
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
/*      */   public static void setBeanInfoSearchPath(String[] paramArrayOfString)
/*      */   {
/*  328 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  329 */     if (localSecurityManager != null) {
/*  330 */       localSecurityManager.checkPropertiesAccess();
/*      */     }
/*  332 */     ThreadGroupContext.getContext().getBeanInfoFinder().setPackages(paramArrayOfString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void flushCaches()
/*      */   {
/*  344 */     synchronized (declaredMethodCache) {
/*  345 */       ThreadGroupContext.getContext().clearBeanInfoCache();
/*  346 */       declaredMethodCache.clear();
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
/*      */   public static void flushFromCaches(Class<?> paramClass)
/*      */   {
/*  366 */     if (paramClass == null) {
/*  367 */       throw new NullPointerException();
/*      */     }
/*  369 */     synchronized (declaredMethodCache) {
/*  370 */       ThreadGroupContext.getContext().removeBeanInfo(paramClass);
/*  371 */       declaredMethodCache.put(paramClass, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Introspector(Class<?> paramClass1, Class<?> paramClass2, int paramInt)
/*      */     throws IntrospectionException
/*      */   {
/*  381 */     this.beanClass = paramClass1;
/*      */     
/*      */ 
/*  384 */     if (paramClass2 != null) {
/*  385 */       int i = 0;
/*  386 */       for (Class localClass2 = paramClass1.getSuperclass(); localClass2 != null; localClass2 = localClass2.getSuperclass()) {
/*  387 */         if (localClass2 == paramClass2) {
/*  388 */           i = 1;
/*      */         }
/*      */       }
/*  391 */       if (i == 0)
/*      */       {
/*  393 */         throw new IntrospectionException(paramClass2.getName() + " not superclass of " + paramClass1.getName());
/*      */       }
/*      */     }
/*      */     
/*  397 */     if (paramInt == 1) {
/*  398 */       this.explicitBeanInfo = findExplicitBeanInfo(paramClass1);
/*      */     }
/*      */     
/*  401 */     Class localClass1 = paramClass1.getSuperclass();
/*  402 */     if (localClass1 != paramClass2) {
/*  403 */       int j = paramInt;
/*  404 */       if (j == 2) {
/*  405 */         j = 1;
/*      */       }
/*  407 */       this.superBeanInfo = getBeanInfo(localClass1, paramClass2, j);
/*      */     }
/*  409 */     if (this.explicitBeanInfo != null) {
/*  410 */       this.additionalBeanInfo = this.explicitBeanInfo.getAdditionalBeanInfo();
/*      */     }
/*  412 */     if (this.additionalBeanInfo == null) {
/*  413 */       this.additionalBeanInfo = new BeanInfo[0];
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BeanInfo getBeanInfo()
/*      */     throws IntrospectionException
/*      */   {
/*  425 */     BeanDescriptor localBeanDescriptor = getTargetBeanDescriptor();
/*  426 */     MethodDescriptor[] arrayOfMethodDescriptor = getTargetMethodInfo();
/*  427 */     EventSetDescriptor[] arrayOfEventSetDescriptor = getTargetEventInfo();
/*  428 */     PropertyDescriptor[] arrayOfPropertyDescriptor = getTargetPropertyInfo();
/*      */     
/*  430 */     int i = getTargetDefaultEventIndex();
/*  431 */     int j = getTargetDefaultPropertyIndex();
/*      */     
/*  433 */     return new GenericBeanInfo(localBeanDescriptor, arrayOfEventSetDescriptor, i, arrayOfPropertyDescriptor, j, arrayOfMethodDescriptor, this.explicitBeanInfo);
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
/*      */   private static BeanInfo findExplicitBeanInfo(Class<?> paramClass)
/*      */   {
/*  448 */     return (BeanInfo)ThreadGroupContext.getContext().getBeanInfoFinder().find(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private PropertyDescriptor[] getTargetPropertyInfo()
/*      */   {
/*  460 */     PropertyDescriptor[] arrayOfPropertyDescriptor = null;
/*  461 */     if (this.explicitBeanInfo != null) {
/*  462 */       arrayOfPropertyDescriptor = getPropertyDescriptors(this.explicitBeanInfo);
/*      */     }
/*      */     
/*  465 */     if ((arrayOfPropertyDescriptor == null) && (this.superBeanInfo != null))
/*      */     {
/*  467 */       addPropertyDescriptors(getPropertyDescriptors(this.superBeanInfo));
/*      */     }
/*      */     
/*  470 */     for (int i = 0; i < this.additionalBeanInfo.length; i++) {
/*  471 */       addPropertyDescriptors(this.additionalBeanInfo[i].getPropertyDescriptors());
/*      */     }
/*      */     int j;
/*  474 */     if (arrayOfPropertyDescriptor != null)
/*      */     {
/*  476 */       addPropertyDescriptors(arrayOfPropertyDescriptor);
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  483 */       localObject1 = getPublicDeclaredMethods(this.beanClass);
/*      */       
/*      */ 
/*  486 */       for (j = 0; j < localObject1.length; j++) {
/*  487 */         Method localMethod = localObject1[j];
/*  488 */         if (localMethod != null)
/*      */         {
/*      */ 
/*      */ 
/*  492 */           int k = localMethod.getModifiers();
/*  493 */           if (!Modifier.isStatic(k))
/*      */           {
/*      */ 
/*  496 */             String str = localMethod.getName();
/*  497 */             Class[] arrayOfClass = localMethod.getParameterTypes();
/*  498 */             Class localClass = localMethod.getReturnType();
/*  499 */             int m = arrayOfClass.length;
/*  500 */             Object localObject2 = null;
/*      */             
/*  502 */             if ((str.length() > 3) || (str.startsWith("is")))
/*      */             {
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/*      */ 
/*  509 */                 if (m == 0) {
/*  510 */                   if (str.startsWith("get"))
/*      */                   {
/*  512 */                     localObject2 = new PropertyDescriptor(this.beanClass, str.substring(3), localMethod, null);
/*  513 */                   } else if ((localClass == Boolean.TYPE) && (str.startsWith("is")))
/*      */                   {
/*  515 */                     localObject2 = new PropertyDescriptor(this.beanClass, str.substring(2), localMethod, null);
/*      */                   }
/*  517 */                 } else if (m == 1) {
/*  518 */                   if ((Integer.TYPE.equals(arrayOfClass[0])) && (str.startsWith("get"))) {
/*  519 */                     localObject2 = new IndexedPropertyDescriptor(this.beanClass, str.substring(3), null, null, localMethod, null);
/*  520 */                   } else if ((Void.TYPE.equals(localClass)) && (str.startsWith("set")))
/*      */                   {
/*  522 */                     localObject2 = new PropertyDescriptor(this.beanClass, str.substring(3), null, localMethod);
/*  523 */                     if (throwsException(localMethod, PropertyVetoException.class)) {
/*  524 */                       ((PropertyDescriptor)localObject2).setConstrained(true);
/*      */                     }
/*      */                   }
/*  527 */                 } else if ((m == 2) && 
/*  528 */                   (Void.TYPE.equals(localClass)) && (Integer.TYPE.equals(arrayOfClass[0])) && (str.startsWith("set"))) {
/*  529 */                   localObject2 = new IndexedPropertyDescriptor(this.beanClass, str.substring(3), null, null, null, localMethod);
/*  530 */                   if (throwsException(localMethod, PropertyVetoException.class)) {
/*  531 */                     ((PropertyDescriptor)localObject2).setConstrained(true);
/*      */                   }
/*      */                   
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               catch (IntrospectionException localIntrospectionException)
/*      */               {
/*  540 */                 localObject2 = null;
/*      */               }
/*      */               
/*  543 */               if (localObject2 != null)
/*      */               {
/*      */ 
/*  546 */                 if (this.propertyChangeSource) {
/*  547 */                   ((PropertyDescriptor)localObject2).setBound(true);
/*      */                 }
/*  549 */                 addPropertyDescriptor((PropertyDescriptor)localObject2);
/*      */               }
/*      */             }
/*      */           } } } }
/*  553 */     processPropertyDescriptors();
/*      */     
/*      */ 
/*      */ 
/*  557 */     Object localObject1 = (PropertyDescriptor[])this.properties.values().toArray(new PropertyDescriptor[this.properties.size()]);
/*      */     
/*      */ 
/*  560 */     if (this.defaultPropertyName != null) {
/*  561 */       for (j = 0; j < localObject1.length; j++) {
/*  562 */         if (this.defaultPropertyName.equals(localObject1[j].getName())) {
/*  563 */           this.defaultPropertyIndex = j;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  568 */     return (PropertyDescriptor[])localObject1;
/*      */   }
/*      */   
/*  571 */   private HashMap<String, List<PropertyDescriptor>> pdStore = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */   private void addPropertyDescriptor(PropertyDescriptor paramPropertyDescriptor)
/*      */   {
/*  577 */     String str = paramPropertyDescriptor.getName();
/*  578 */     Object localObject = (List)this.pdStore.get(str);
/*  579 */     if (localObject == null) {
/*  580 */       localObject = new ArrayList();
/*  581 */       this.pdStore.put(str, localObject);
/*      */     }
/*  583 */     if (this.beanClass != paramPropertyDescriptor.getClass0())
/*      */     {
/*      */ 
/*      */ 
/*  587 */       Method localMethod1 = paramPropertyDescriptor.getReadMethod();
/*  588 */       Method localMethod2 = paramPropertyDescriptor.getWriteMethod();
/*  589 */       int i = 1;
/*  590 */       if (localMethod1 != null) i = (i != 0) && ((localMethod1.getGenericReturnType() instanceof Class)) ? 1 : 0;
/*  591 */       if (localMethod2 != null) i = (i != 0) && ((localMethod2.getGenericParameterTypes()[0] instanceof Class)) ? 1 : 0;
/*  592 */       if ((paramPropertyDescriptor instanceof IndexedPropertyDescriptor)) {
/*  593 */         IndexedPropertyDescriptor localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)paramPropertyDescriptor;
/*  594 */         Method localMethod3 = localIndexedPropertyDescriptor.getIndexedReadMethod();
/*  595 */         Method localMethod4 = localIndexedPropertyDescriptor.getIndexedWriteMethod();
/*  596 */         if (localMethod3 != null) i = (i != 0) && ((localMethod3.getGenericReturnType() instanceof Class)) ? 1 : 0;
/*  597 */         if (localMethod4 != null) i = (i != 0) && ((localMethod4.getGenericParameterTypes()[1] instanceof Class)) ? 1 : 0;
/*  598 */         if (i == 0) {
/*  599 */           paramPropertyDescriptor = new IndexedPropertyDescriptor(localIndexedPropertyDescriptor);
/*  600 */           paramPropertyDescriptor.updateGenericsFor(this.beanClass);
/*      */         }
/*      */       }
/*  603 */       else if (i == 0) {
/*  604 */         paramPropertyDescriptor = new PropertyDescriptor(paramPropertyDescriptor);
/*  605 */         paramPropertyDescriptor.updateGenericsFor(this.beanClass);
/*      */       }
/*      */     }
/*  608 */     ((List)localObject).add(paramPropertyDescriptor);
/*      */   }
/*      */   
/*      */   private void addPropertyDescriptors(PropertyDescriptor[] paramArrayOfPropertyDescriptor) {
/*  612 */     if (paramArrayOfPropertyDescriptor != null) {
/*  613 */       for (PropertyDescriptor localPropertyDescriptor : paramArrayOfPropertyDescriptor) {
/*  614 */         addPropertyDescriptor(localPropertyDescriptor);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private PropertyDescriptor[] getPropertyDescriptors(BeanInfo paramBeanInfo) {
/*  620 */     PropertyDescriptor[] arrayOfPropertyDescriptor = paramBeanInfo.getPropertyDescriptors();
/*  621 */     int i = paramBeanInfo.getDefaultPropertyIndex();
/*  622 */     if ((0 <= i) && (i < arrayOfPropertyDescriptor.length)) {
/*  623 */       this.defaultPropertyName = arrayOfPropertyDescriptor[i].getName();
/*      */     }
/*  625 */     return arrayOfPropertyDescriptor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void processPropertyDescriptors()
/*      */   {
/*  633 */     if (this.properties == null) {
/*  634 */       this.properties = new TreeMap();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  642 */     Iterator localIterator = this.pdStore.values().iterator();
/*  643 */     while (localIterator.hasNext()) {
/*  644 */       Object localObject1 = null;Object localObject2 = null;Object localObject3 = null;
/*  645 */       IndexedPropertyDescriptor localIndexedPropertyDescriptor = null;Object localObject4 = null;Object localObject5 = null;
/*      */       
/*  647 */       List localList = (List)localIterator.next();
/*      */       
/*      */ 
/*      */ 
/*  651 */       for (int i = 0; i < localList.size(); i++) {
/*  652 */         localObject1 = (PropertyDescriptor)localList.get(i);
/*  653 */         if ((localObject1 instanceof IndexedPropertyDescriptor)) {
/*  654 */           localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)localObject1;
/*  655 */           if (localIndexedPropertyDescriptor.getIndexedReadMethod() != null) {
/*  656 */             if (localObject4 != null) {
/*  657 */               localObject4 = new IndexedPropertyDescriptor((PropertyDescriptor)localObject4, localIndexedPropertyDescriptor);
/*      */             } else {
/*  659 */               localObject4 = localIndexedPropertyDescriptor;
/*      */             }
/*      */           }
/*      */         }
/*  663 */         else if (((PropertyDescriptor)localObject1).getReadMethod() != null) {
/*  664 */           String str1 = ((PropertyDescriptor)localObject1).getReadMethod().getName();
/*  665 */           if (localObject2 != null)
/*      */           {
/*      */ 
/*  668 */             String str2 = ((PropertyDescriptor)localObject2).getReadMethod().getName();
/*  669 */             if ((str2.equals(str1)) || (!str2.startsWith("is"))) {
/*  670 */               localObject2 = new PropertyDescriptor((PropertyDescriptor)localObject2, (PropertyDescriptor)localObject1);
/*      */             }
/*      */           } else {
/*  673 */             localObject2 = localObject1;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  681 */       for (i = 0; i < localList.size(); i++) {
/*  682 */         localObject1 = (PropertyDescriptor)localList.get(i);
/*  683 */         if ((localObject1 instanceof IndexedPropertyDescriptor)) {
/*  684 */           localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)localObject1;
/*  685 */           if (localIndexedPropertyDescriptor.getIndexedWriteMethod() != null) {
/*  686 */             if (localObject4 != null) {
/*  687 */               if (isAssignable(((IndexedPropertyDescriptor)localObject4).getIndexedPropertyType(), localIndexedPropertyDescriptor.getIndexedPropertyType())) {
/*  688 */                 if (localObject5 != null) {
/*  689 */                   localObject5 = new IndexedPropertyDescriptor((PropertyDescriptor)localObject5, localIndexedPropertyDescriptor);
/*      */                 } else {
/*  691 */                   localObject5 = localIndexedPropertyDescriptor;
/*      */                 }
/*      */               }
/*      */             }
/*  695 */             else if (localObject5 != null) {
/*  696 */               localObject5 = new IndexedPropertyDescriptor((PropertyDescriptor)localObject5, localIndexedPropertyDescriptor);
/*      */             } else {
/*  698 */               localObject5 = localIndexedPropertyDescriptor;
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*  703 */         else if (((PropertyDescriptor)localObject1).getWriteMethod() != null) {
/*  704 */           if (localObject2 != null) {
/*  705 */             if (isAssignable(((PropertyDescriptor)localObject2).getPropertyType(), ((PropertyDescriptor)localObject1).getPropertyType())) {
/*  706 */               if (localObject3 != null) {
/*  707 */                 localObject3 = new PropertyDescriptor((PropertyDescriptor)localObject3, (PropertyDescriptor)localObject1);
/*      */               } else {
/*  709 */                 localObject3 = localObject1;
/*      */               }
/*      */             }
/*      */           }
/*  713 */           else if (localObject3 != null) {
/*  714 */             localObject3 = new PropertyDescriptor((PropertyDescriptor)localObject3, (PropertyDescriptor)localObject1);
/*      */           } else {
/*  716 */             localObject3 = localObject1;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  727 */       localObject1 = null;localIndexedPropertyDescriptor = null;
/*      */       
/*  729 */       if ((localObject4 != null) && (localObject5 != null))
/*      */       {
/*      */         PropertyDescriptor localPropertyDescriptor;
/*  732 */         if (localObject2 != null) {
/*  733 */           localPropertyDescriptor = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject4, (PropertyDescriptor)localObject2);
/*  734 */           if ((localPropertyDescriptor instanceof IndexedPropertyDescriptor)) {
/*  735 */             localObject4 = (IndexedPropertyDescriptor)localPropertyDescriptor;
/*      */           }
/*      */         }
/*  738 */         if (localObject3 != null) {
/*  739 */           localPropertyDescriptor = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject5, (PropertyDescriptor)localObject3);
/*  740 */           if ((localPropertyDescriptor instanceof IndexedPropertyDescriptor)) {
/*  741 */             localObject5 = (IndexedPropertyDescriptor)localPropertyDescriptor;
/*      */           }
/*      */         }
/*  744 */         if (localObject4 == localObject5) {
/*  745 */           localObject1 = localObject4;
/*      */         } else {
/*  747 */           localObject1 = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject4, (IndexedPropertyDescriptor)localObject5);
/*      */         }
/*  749 */       } else if ((localObject2 != null) && (localObject3 != null))
/*      */       {
/*  751 */         if (localObject2 == localObject3) {
/*  752 */           localObject1 = localObject2;
/*      */         } else {
/*  754 */           localObject1 = mergePropertyDescriptor((PropertyDescriptor)localObject2, (PropertyDescriptor)localObject3);
/*      */         }
/*  756 */       } else if (localObject5 != null)
/*      */       {
/*  758 */         localObject1 = localObject5;
/*      */         
/*  760 */         if (localObject3 != null) {
/*  761 */           localObject1 = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject5, (PropertyDescriptor)localObject3);
/*      */         }
/*  763 */         if (localObject2 != null) {
/*  764 */           localObject1 = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject5, (PropertyDescriptor)localObject2);
/*      */         }
/*  766 */       } else if (localObject4 != null)
/*      */       {
/*  768 */         localObject1 = localObject4;
/*      */         
/*  770 */         if (localObject2 != null) {
/*  771 */           localObject1 = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject4, (PropertyDescriptor)localObject2);
/*      */         }
/*  773 */         if (localObject3 != null) {
/*  774 */           localObject1 = mergePropertyDescriptor((IndexedPropertyDescriptor)localObject4, (PropertyDescriptor)localObject3);
/*      */         }
/*  776 */       } else if (localObject3 != null)
/*      */       {
/*  778 */         localObject1 = localObject3;
/*  779 */       } else if (localObject2 != null)
/*      */       {
/*  781 */         localObject1 = localObject2;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  788 */       if ((localObject1 instanceof IndexedPropertyDescriptor)) {
/*  789 */         localIndexedPropertyDescriptor = (IndexedPropertyDescriptor)localObject1;
/*  790 */         if ((localIndexedPropertyDescriptor.getIndexedReadMethod() == null) && (localIndexedPropertyDescriptor.getIndexedWriteMethod() == null)) {
/*  791 */           localObject1 = new PropertyDescriptor(localIndexedPropertyDescriptor);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  798 */       if ((localObject1 == null) && (localList.size() > 0)) {
/*  799 */         localObject1 = (PropertyDescriptor)localList.get(0);
/*      */       }
/*      */       
/*  802 */       if (localObject1 != null) {
/*  803 */         this.properties.put(((PropertyDescriptor)localObject1).getName(), localObject1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean isAssignable(Class<?> paramClass1, Class<?> paramClass2) {
/*  809 */     return (paramClass1 == null) || (paramClass2 == null) ? false : paramClass1 == paramClass2 ? true : paramClass1.isAssignableFrom(paramClass2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private PropertyDescriptor mergePropertyDescriptor(IndexedPropertyDescriptor paramIndexedPropertyDescriptor, PropertyDescriptor paramPropertyDescriptor)
/*      */   {
/*  820 */     Object localObject = null;
/*      */     
/*  822 */     Class localClass1 = paramPropertyDescriptor.getPropertyType();
/*  823 */     Class localClass2 = paramIndexedPropertyDescriptor.getIndexedPropertyType();
/*      */     
/*  825 */     if ((localClass1.isArray()) && (localClass1.getComponentType() == localClass2)) {
/*  826 */       if (paramPropertyDescriptor.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor.getClass0())) {
/*  827 */         localObject = new IndexedPropertyDescriptor(paramPropertyDescriptor, paramIndexedPropertyDescriptor);
/*      */       } else {
/*  829 */         localObject = new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor, paramPropertyDescriptor);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  834 */     else if (paramPropertyDescriptor.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor.getClass0())) {
/*  835 */       localObject = paramIndexedPropertyDescriptor;
/*      */     } else {
/*  837 */       localObject = paramPropertyDescriptor;
/*      */       
/*      */ 
/*  840 */       Method localMethod1 = ((PropertyDescriptor)localObject).getWriteMethod();
/*  841 */       Method localMethod2 = ((PropertyDescriptor)localObject).getReadMethod();
/*      */       
/*  843 */       if ((localMethod2 == null) && (localMethod1 != null)) {
/*  844 */         localMethod2 = findMethod(((PropertyDescriptor)localObject).getClass0(), "get" + 
/*  845 */           NameGenerator.capitalize(((PropertyDescriptor)localObject).getName()), 0);
/*  846 */         if (localMethod2 != null) {
/*      */           try {
/*  848 */             ((PropertyDescriptor)localObject).setReadMethod(localMethod2);
/*      */           }
/*      */           catch (IntrospectionException localIntrospectionException1) {}
/*      */         }
/*      */       }
/*      */       
/*  854 */       if ((localMethod1 == null) && (localMethod2 != null)) {
/*  855 */         localMethod1 = findMethod(((PropertyDescriptor)localObject).getClass0(), "set" + 
/*  856 */           NameGenerator.capitalize(((PropertyDescriptor)localObject).getName()), 1, new Class[] {
/*  857 */           FeatureDescriptor.getReturnType(((PropertyDescriptor)localObject).getClass0(), localMethod2) });
/*  858 */         if (localMethod1 != null) {
/*      */           try {
/*  860 */             ((PropertyDescriptor)localObject).setWriteMethod(localMethod1);
/*      */           }
/*      */           catch (IntrospectionException localIntrospectionException2) {}
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  868 */     return (PropertyDescriptor)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */   private PropertyDescriptor mergePropertyDescriptor(PropertyDescriptor paramPropertyDescriptor1, PropertyDescriptor paramPropertyDescriptor2)
/*      */   {
/*  874 */     if (paramPropertyDescriptor1.getClass0().isAssignableFrom(paramPropertyDescriptor2.getClass0())) {
/*  875 */       return new PropertyDescriptor(paramPropertyDescriptor1, paramPropertyDescriptor2);
/*      */     }
/*  877 */     return new PropertyDescriptor(paramPropertyDescriptor2, paramPropertyDescriptor1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private PropertyDescriptor mergePropertyDescriptor(IndexedPropertyDescriptor paramIndexedPropertyDescriptor1, IndexedPropertyDescriptor paramIndexedPropertyDescriptor2)
/*      */   {
/*  884 */     if (paramIndexedPropertyDescriptor1.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor2.getClass0())) {
/*  885 */       return new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor1, paramIndexedPropertyDescriptor2);
/*      */     }
/*  887 */     return new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor2, paramIndexedPropertyDescriptor1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private EventSetDescriptor[] getTargetEventInfo()
/*      */     throws IntrospectionException
/*      */   {
/*  896 */     if (this.events == null) {
/*  897 */       this.events = new HashMap();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  902 */     EventSetDescriptor[] arrayOfEventSetDescriptor1 = null;
/*  903 */     if (this.explicitBeanInfo != null) {
/*  904 */       arrayOfEventSetDescriptor1 = this.explicitBeanInfo.getEventSetDescriptors();
/*  905 */       int i = this.explicitBeanInfo.getDefaultEventIndex();
/*  906 */       if ((i >= 0) && (i < arrayOfEventSetDescriptor1.length)) {
/*  907 */         this.defaultEventName = arrayOfEventSetDescriptor1[i].getName();
/*      */       }
/*      */     }
/*      */     
/*  911 */     if ((arrayOfEventSetDescriptor1 == null) && (this.superBeanInfo != null))
/*      */     {
/*  913 */       EventSetDescriptor[] arrayOfEventSetDescriptor2 = this.superBeanInfo.getEventSetDescriptors();
/*  914 */       for (int k = 0; k < arrayOfEventSetDescriptor2.length; k++) {
/*  915 */         addEvent(arrayOfEventSetDescriptor2[k]);
/*      */       }
/*  917 */       k = this.superBeanInfo.getDefaultEventIndex();
/*  918 */       if ((k >= 0) && (k < arrayOfEventSetDescriptor2.length)) {
/*  919 */         this.defaultEventName = arrayOfEventSetDescriptor2[k].getName();
/*      */       }
/*      */     }
/*      */     Object localObject2;
/*  923 */     for (int j = 0; j < this.additionalBeanInfo.length; j++) {
/*  924 */       localObject2 = this.additionalBeanInfo[j].getEventSetDescriptors();
/*  925 */       if (localObject2 != null) {
/*  926 */         for (int n = 0; n < localObject2.length; n++) {
/*  927 */           addEvent(localObject2[n]);
/*      */         }
/*      */       }
/*      */     }
/*      */     Object localObject1;
/*  932 */     if (arrayOfEventSetDescriptor1 != null)
/*      */     {
/*  934 */       for (j = 0; j < arrayOfEventSetDescriptor1.length; j++) {
/*  935 */         addEvent(arrayOfEventSetDescriptor1[j]);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  943 */       localObject1 = getPublicDeclaredMethods(this.beanClass);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  948 */       localObject2 = null;
/*  949 */       HashMap localHashMap1 = null;
/*  950 */       HashMap localHashMap2 = null;
/*      */       Object localObject3;
/*  952 */       Object localObject4; Object localObject5; Object localObject6; Class localClass; Object localObject7; for (int i1 = 0; i1 < localObject1.length; i1++) {
/*  953 */         localObject3 = localObject1[i1];
/*  954 */         if (localObject3 != null)
/*      */         {
/*      */ 
/*      */ 
/*  958 */           int i2 = ((Method)localObject3).getModifiers();
/*  959 */           if (!Modifier.isStatic(i2))
/*      */           {
/*      */ 
/*  962 */             localObject4 = ((Method)localObject3).getName();
/*      */             
/*  964 */             if ((((String)localObject4).startsWith("add")) || (((String)localObject4).startsWith("remove")) || 
/*  965 */               (((String)localObject4).startsWith("get")))
/*      */             {
/*      */ 
/*      */ 
/*  969 */               if (((String)localObject4).startsWith("add")) {
/*  970 */                 localObject5 = ((Method)localObject3).getReturnType();
/*  971 */                 if (localObject5 == Void.TYPE) {
/*  972 */                   localObject6 = ((Method)localObject3).getGenericParameterTypes();
/*  973 */                   if (localObject6.length == 1) {
/*  974 */                     localClass = TypeResolver.erase(TypeResolver.resolveInClass(this.beanClass, localObject6[0]));
/*  975 */                     if (isSubclass(localClass, eventListenerType)) {
/*  976 */                       localObject7 = ((String)localObject4).substring(3);
/*  977 */                       if ((((String)localObject7).length() > 0) && 
/*  978 */                         (localClass.getName().endsWith((String)localObject7))) {
/*  979 */                         if (localObject2 == null) {
/*  980 */                           localObject2 = new HashMap();
/*      */                         }
/*  982 */                         ((Map)localObject2).put(localObject7, localObject3);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*  988 */               else if (((String)localObject4).startsWith("remove")) {
/*  989 */                 localObject5 = ((Method)localObject3).getReturnType();
/*  990 */                 if (localObject5 == Void.TYPE) {
/*  991 */                   localObject6 = ((Method)localObject3).getGenericParameterTypes();
/*  992 */                   if (localObject6.length == 1) {
/*  993 */                     localClass = TypeResolver.erase(TypeResolver.resolveInClass(this.beanClass, localObject6[0]));
/*  994 */                     if (isSubclass(localClass, eventListenerType)) {
/*  995 */                       localObject7 = ((String)localObject4).substring(6);
/*  996 */                       if ((((String)localObject7).length() > 0) && 
/*  997 */                         (localClass.getName().endsWith((String)localObject7))) {
/*  998 */                         if (localHashMap1 == null) {
/*  999 */                           localHashMap1 = new HashMap();
/*      */                         }
/* 1001 */                         localHashMap1.put(localObject7, localObject3);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/* 1007 */               else if (((String)localObject4).startsWith("get")) {
/* 1008 */                 localObject5 = ((Method)localObject3).getParameterTypes();
/* 1009 */                 if (localObject5.length == 0) {
/* 1010 */                   localObject6 = FeatureDescriptor.getReturnType(this.beanClass, (Method)localObject3);
/* 1011 */                   if (((Class)localObject6).isArray()) {
/* 1012 */                     localClass = ((Class)localObject6).getComponentType();
/* 1013 */                     if (isSubclass(localClass, eventListenerType)) {
/* 1014 */                       localObject7 = ((String)localObject4).substring(3, ((String)localObject4).length() - 1);
/* 1015 */                       if ((((String)localObject7).length() > 0) && 
/* 1016 */                         (localClass.getName().endsWith((String)localObject7))) {
/* 1017 */                         if (localHashMap2 == null) {
/* 1018 */                           localHashMap2 = new HashMap();
/*      */                         }
/* 1020 */                         localHashMap2.put(localObject7, localObject3);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               } }
/*      */           }
/*      */         } }
/* 1028 */       if ((localObject2 != null) && (localHashMap1 != null))
/*      */       {
/*      */ 
/* 1031 */         Iterator localIterator = ((Map)localObject2).keySet().iterator();
/* 1032 */         while (localIterator.hasNext()) {
/* 1033 */           localObject3 = (String)localIterator.next();
/*      */           
/*      */ 
/* 1036 */           if ((localHashMap1.get(localObject3) != null) && (((String)localObject3).endsWith("Listener")))
/*      */           {
/*      */ 
/* 1039 */             String str = decapitalize(((String)localObject3).substring(0, ((String)localObject3).length() - 8));
/* 1040 */             localObject4 = (Method)((Map)localObject2).get(localObject3);
/* 1041 */             localObject5 = (Method)localHashMap1.get(localObject3);
/* 1042 */             localObject6 = null;
/* 1043 */             if (localHashMap2 != null) {
/* 1044 */               localObject6 = (Method)localHashMap2.get(localObject3);
/*      */             }
/* 1046 */             localClass = FeatureDescriptor.getParameterTypes(this.beanClass, localObject4)[0];
/*      */             
/*      */ 
/* 1049 */             localObject7 = getPublicDeclaredMethods(localClass);
/* 1050 */             ArrayList localArrayList = new ArrayList(localObject7.length);
/* 1051 */             for (int i3 = 0; i3 < localObject7.length; i3++) {
/* 1052 */               if (localObject7[i3] != null)
/*      */               {
/*      */ 
/*      */ 
/* 1056 */                 if (isEventHandler(localObject7[i3]))
/* 1057 */                   localArrayList.add(localObject7[i3]);
/*      */               }
/*      */             }
/* 1060 */             Method[] arrayOfMethod = (Method[])localArrayList.toArray(new Method[localArrayList.size()]);
/*      */             
/* 1062 */             EventSetDescriptor localEventSetDescriptor = new EventSetDescriptor(str, localClass, arrayOfMethod, (Method)localObject4, (Method)localObject5, (Method)localObject6);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1069 */             if (throwsException((Method)localObject4, TooManyListenersException.class))
/*      */             {
/* 1071 */               localEventSetDescriptor.setUnicast(true);
/*      */             }
/* 1073 */             addEvent(localEventSetDescriptor);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1078 */     if (this.events.size() == 0) {
/* 1079 */       localObject1 = EMPTY_EVENTSETDESCRIPTORS;
/*      */     }
/*      */     else {
/* 1082 */       localObject1 = new EventSetDescriptor[this.events.size()];
/* 1083 */       localObject1 = (EventSetDescriptor[])this.events.values().toArray((Object[])localObject1);
/*      */       
/*      */ 
/* 1086 */       if (this.defaultEventName != null) {
/* 1087 */         for (int m = 0; m < localObject1.length; m++) {
/* 1088 */           if (this.defaultEventName.equals(localObject1[m].getName())) {
/* 1089 */             this.defaultEventIndex = m;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1094 */     return (EventSetDescriptor[])localObject1;
/*      */   }
/*      */   
/*      */   private void addEvent(EventSetDescriptor paramEventSetDescriptor) {
/* 1098 */     String str = paramEventSetDescriptor.getName();
/* 1099 */     if (paramEventSetDescriptor.getName().equals("propertyChange")) {
/* 1100 */       this.propertyChangeSource = true;
/*      */     }
/* 1102 */     EventSetDescriptor localEventSetDescriptor1 = (EventSetDescriptor)this.events.get(str);
/* 1103 */     if (localEventSetDescriptor1 == null) {
/* 1104 */       this.events.put(str, paramEventSetDescriptor);
/* 1105 */       return;
/*      */     }
/* 1107 */     EventSetDescriptor localEventSetDescriptor2 = new EventSetDescriptor(localEventSetDescriptor1, paramEventSetDescriptor);
/* 1108 */     this.events.put(str, localEventSetDescriptor2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private MethodDescriptor[] getTargetMethodInfo()
/*      */   {
/* 1116 */     if (this.methods == null) {
/* 1117 */       this.methods = new HashMap(100);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1122 */     MethodDescriptor[] arrayOfMethodDescriptor1 = null;
/* 1123 */     if (this.explicitBeanInfo != null) {
/* 1124 */       arrayOfMethodDescriptor1 = this.explicitBeanInfo.getMethodDescriptors();
/*      */     }
/*      */     
/* 1127 */     if ((arrayOfMethodDescriptor1 == null) && (this.superBeanInfo != null))
/*      */     {
/* 1129 */       MethodDescriptor[] arrayOfMethodDescriptor2 = this.superBeanInfo.getMethodDescriptors();
/* 1130 */       for (int j = 0; j < arrayOfMethodDescriptor2.length; j++) {
/* 1131 */         addMethod(arrayOfMethodDescriptor2[j]);
/*      */       }
/*      */     }
/*      */     
/* 1135 */     for (int i = 0; i < this.additionalBeanInfo.length; i++) {
/* 1136 */       MethodDescriptor[] arrayOfMethodDescriptor3 = this.additionalBeanInfo[i].getMethodDescriptors();
/* 1137 */       if (arrayOfMethodDescriptor3 != null) {
/* 1138 */         for (int m = 0; m < arrayOfMethodDescriptor3.length; m++) {
/* 1139 */           addMethod(arrayOfMethodDescriptor3[m]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1144 */     if (arrayOfMethodDescriptor1 != null)
/*      */     {
/* 1146 */       for (i = 0; i < arrayOfMethodDescriptor1.length; i++) {
/* 1147 */         addMethod(arrayOfMethodDescriptor1[i]);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1155 */       localObject = getPublicDeclaredMethods(this.beanClass);
/*      */       
/*      */ 
/* 1158 */       for (int k = 0; k < localObject.length; k++) {
/* 1159 */         Method localMethod = localObject[k];
/* 1160 */         if (localMethod != null)
/*      */         {
/*      */ 
/* 1163 */           MethodDescriptor localMethodDescriptor = new MethodDescriptor(localMethod);
/* 1164 */           addMethod(localMethodDescriptor);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1169 */     Object localObject = new MethodDescriptor[this.methods.size()];
/* 1170 */     localObject = (MethodDescriptor[])this.methods.values().toArray((Object[])localObject);
/*      */     
/* 1172 */     return (MethodDescriptor[])localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void addMethod(MethodDescriptor paramMethodDescriptor)
/*      */   {
/* 1179 */     String str = paramMethodDescriptor.getName();
/*      */     
/* 1181 */     MethodDescriptor localMethodDescriptor1 = (MethodDescriptor)this.methods.get(str);
/* 1182 */     if (localMethodDescriptor1 == null)
/*      */     {
/* 1184 */       this.methods.put(str, paramMethodDescriptor);
/* 1185 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1191 */     String[] arrayOfString1 = paramMethodDescriptor.getParamNames();
/* 1192 */     String[] arrayOfString2 = localMethodDescriptor1.getParamNames();
/*      */     
/* 1194 */     int i = 0;
/* 1195 */     if (arrayOfString1.length == arrayOfString2.length) {
/* 1196 */       i = 1;
/* 1197 */       for (int j = 0; j < arrayOfString1.length; j++) {
/* 1198 */         if (arrayOfString1[j] != arrayOfString2[j]) {
/* 1199 */           i = 0;
/* 1200 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1204 */     if (i != 0) {
/* 1205 */       localObject = new MethodDescriptor(localMethodDescriptor1, paramMethodDescriptor);
/* 1206 */       this.methods.put(str, localObject);
/* 1207 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1213 */     Object localObject = makeQualifiedMethodName(str, arrayOfString1);
/* 1214 */     localMethodDescriptor1 = (MethodDescriptor)this.methods.get(localObject);
/* 1215 */     if (localMethodDescriptor1 == null) {
/* 1216 */       this.methods.put(localObject, paramMethodDescriptor);
/* 1217 */       return;
/*      */     }
/* 1219 */     MethodDescriptor localMethodDescriptor2 = new MethodDescriptor(localMethodDescriptor1, paramMethodDescriptor);
/* 1220 */     this.methods.put(localObject, localMethodDescriptor2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String makeQualifiedMethodName(String paramString, String[] paramArrayOfString)
/*      */   {
/* 1227 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1228 */     localStringBuffer.append('=');
/* 1229 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 1230 */       localStringBuffer.append(':');
/* 1231 */       localStringBuffer.append(paramArrayOfString[i]);
/*      */     }
/* 1233 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   private int getTargetDefaultEventIndex() {
/* 1237 */     return this.defaultEventIndex;
/*      */   }
/*      */   
/*      */   private int getTargetDefaultPropertyIndex() {
/* 1241 */     return this.defaultPropertyIndex;
/*      */   }
/*      */   
/*      */   private BeanDescriptor getTargetBeanDescriptor()
/*      */   {
/* 1246 */     if (this.explicitBeanInfo != null) {
/* 1247 */       BeanDescriptor localBeanDescriptor = this.explicitBeanInfo.getBeanDescriptor();
/* 1248 */       if (localBeanDescriptor != null) {
/* 1249 */         return localBeanDescriptor;
/*      */       }
/*      */     }
/*      */     
/* 1253 */     return new BeanDescriptor(this.beanClass, findCustomizerClass(this.beanClass));
/*      */   }
/*      */   
/*      */   private static Class<?> findCustomizerClass(Class<?> paramClass) {
/* 1257 */     String str = paramClass.getName() + "Customizer";
/*      */     try {
/* 1259 */       paramClass = ClassFinder.findClass(str, paramClass.getClassLoader());
/*      */       
/*      */ 
/* 1262 */       if ((Component.class.isAssignableFrom(paramClass)) && (Customizer.class.isAssignableFrom(paramClass))) {
/* 1263 */         return paramClass;
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/* 1269 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean isEventHandler(Method paramMethod)
/*      */   {
/* 1275 */     Type[] arrayOfType = paramMethod.getGenericParameterTypes();
/* 1276 */     if (arrayOfType.length != 1) {
/* 1277 */       return false;
/*      */     }
/* 1279 */     return isSubclass(TypeResolver.erase(TypeResolver.resolveInClass(this.beanClass, arrayOfType[0])), EventObject.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Method[] getPublicDeclaredMethods(Class<?> paramClass)
/*      */   {
/* 1288 */     if (!ReflectUtil.isPackageAccessible(paramClass)) {
/* 1289 */       return new Method[0];
/*      */     }
/* 1291 */     synchronized (declaredMethodCache) {
/* 1292 */       Method[] arrayOfMethod = (Method[])declaredMethodCache.get(paramClass);
/* 1293 */       if (arrayOfMethod == null) {
/* 1294 */         arrayOfMethod = paramClass.getMethods();
/* 1295 */         for (int i = 0; i < arrayOfMethod.length; i++) {
/* 1296 */           Method localMethod = arrayOfMethod[i];
/* 1297 */           if (!localMethod.getDeclaringClass().equals(paramClass)) {
/* 1298 */             arrayOfMethod[i] = null;
/*      */           } else {
/*      */             try
/*      */             {
/* 1302 */               localMethod = MethodFinder.findAccessibleMethod(localMethod);
/* 1303 */               Class localClass = localMethod.getDeclaringClass();
/* 1304 */               arrayOfMethod[i] = ((localClass.equals(paramClass)) || (localClass.isInterface()) ? localMethod : null);
/*      */             }
/*      */             catch (NoSuchMethodException localNoSuchMethodException) {}
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1314 */         declaredMethodCache.put(paramClass, arrayOfMethod);
/*      */       }
/* 1316 */       return arrayOfMethod;
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
/*      */   private static Method internalFindMethod(Class<?> paramClass, String paramString, int paramInt, Class[] paramArrayOfClass)
/*      */   {
/* 1333 */     Method localMethod = null;
/*      */     
/* 1335 */     for (Object localObject = paramClass; localObject != null; localObject = ((Class)localObject).getSuperclass()) {
/* 1336 */       Method[] arrayOfMethod = getPublicDeclaredMethods((Class)localObject);
/* 1337 */       for (int j = 0; j < arrayOfMethod.length; j++) {
/* 1338 */         localMethod = arrayOfMethod[j];
/* 1339 */         if (localMethod != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1344 */           if (localMethod.getName().equals(paramString)) {
/* 1345 */             Type[] arrayOfType = localMethod.getGenericParameterTypes();
/* 1346 */             if (arrayOfType.length == paramInt) {
/* 1347 */               if (paramArrayOfClass != null) {
/* 1348 */                 int k = 0;
/* 1349 */                 if (paramInt > 0) {
/* 1350 */                   for (int m = 0; m < paramInt; m++) {
/* 1351 */                     if (TypeResolver.erase(TypeResolver.resolveInClass(paramClass, arrayOfType[m])) != paramArrayOfClass[m]) {
/* 1352 */                       k = 1;
/*      */                     }
/*      */                   }
/*      */                   
/* 1356 */                   if (k != 0) {
/*      */                     continue;
/*      */                   }
/*      */                 }
/*      */               }
/* 1361 */               return localMethod;
/*      */             }
/*      */           } }
/*      */       }
/*      */     }
/* 1366 */     localMethod = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1371 */     localObject = paramClass.getInterfaces();
/* 1372 */     for (int i = 0; i < localObject.length; i++)
/*      */     {
/*      */ 
/*      */ 
/* 1376 */       localMethod = internalFindMethod(localObject[i], paramString, paramInt, null);
/* 1377 */       if (localMethod != null) {
/*      */         break;
/*      */       }
/*      */     }
/* 1381 */     return localMethod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static Method findMethod(Class<?> paramClass, String paramString, int paramInt)
/*      */   {
/* 1388 */     return findMethod(paramClass, paramString, paramInt, null);
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
/*      */   static Method findMethod(Class<?> paramClass, String paramString, int paramInt, Class[] paramArrayOfClass)
/*      */   {
/* 1405 */     if (paramString == null) {
/* 1406 */       return null;
/*      */     }
/* 1408 */     return internalFindMethod(paramClass, paramString, paramInt, paramArrayOfClass);
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
/*      */   static boolean isSubclass(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/* 1421 */     if (paramClass1 == paramClass2) {
/* 1422 */       return true;
/*      */     }
/* 1424 */     if ((paramClass1 == null) || (paramClass2 == null)) {
/* 1425 */       return false;
/*      */     }
/* 1427 */     for (Object localObject = paramClass1; localObject != null; localObject = ((Class)localObject).getSuperclass()) {
/* 1428 */       if (localObject == paramClass2) {
/* 1429 */         return true;
/*      */       }
/* 1431 */       if (paramClass2.isInterface()) {
/* 1432 */         Class[] arrayOfClass = ((Class)localObject).getInterfaces();
/* 1433 */         for (int i = 0; i < arrayOfClass.length; i++) {
/* 1434 */           if (isSubclass(arrayOfClass[i], paramClass2)) {
/* 1435 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1440 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean throwsException(Method paramMethod, Class<?> paramClass)
/*      */   {
/* 1447 */     Class[] arrayOfClass = paramMethod.getExceptionTypes();
/* 1448 */     for (int i = 0; i < arrayOfClass.length; i++) {
/* 1449 */       if (arrayOfClass[i] == paramClass) {
/* 1450 */         return true;
/*      */       }
/*      */     }
/* 1453 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static Object instantiate(Class<?> paramClass, String paramString)
/*      */     throws InstantiationException, IllegalAccessException, ClassNotFoundException
/*      */   {
/* 1465 */     ClassLoader localClassLoader = paramClass.getClassLoader();
/* 1466 */     Class localClass = ClassFinder.findClass(paramString, localClassLoader);
/* 1467 */     return localClass.newInstance();
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/Introspector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */