/*     */ package java.beans;
/*     */ 
/*     */ import com.sun.beans.finder.BeanInfoFinder;
/*     */ import com.sun.beans.finder.PropertyEditorFinder;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ThreadGroupContext
/*     */ {
/*  44 */   private static final WeakIdentityMap<ThreadGroupContext> contexts = new WeakIdentityMap() {
/*     */     protected ThreadGroupContext create(Object paramAnonymousObject) {
/*  46 */       return new ThreadGroupContext(null);
/*     */     }
/*     */   };
/*     */   private volatile boolean isDesignTime;
/*     */   private volatile Boolean isGuiAvailable;
/*     */   private Map<Class<?>, BeanInfo> beanInfoCache;
/*     */   private BeanInfoFinder beanInfoFinder;
/*     */   private PropertyEditorFinder propertyEditorFinder;
/*     */   
/*     */   static ThreadGroupContext getContext()
/*     */   {
/*  57 */     return (ThreadGroupContext)contexts.get(Thread.currentThread().getThreadGroup());
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
/*     */   boolean isDesignTime()
/*     */   {
/*  71 */     return this.isDesignTime;
/*     */   }
/*     */   
/*     */   void setDesignTime(boolean paramBoolean) {
/*  75 */     this.isDesignTime = paramBoolean;
/*     */   }
/*     */   
/*     */   boolean isGuiAvailable()
/*     */   {
/*  80 */     Boolean localBoolean = this.isGuiAvailable;
/*     */     
/*     */ 
/*  83 */     return !GraphicsEnvironment.isHeadless() ? true : localBoolean != null ? localBoolean.booleanValue() : false;
/*     */   }
/*     */   
/*     */   void setGuiAvailable(boolean paramBoolean) {
/*  87 */     this.isGuiAvailable = Boolean.valueOf(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   BeanInfo getBeanInfo(Class<?> paramClass)
/*     */   {
/*  93 */     return this.beanInfoCache != null ? (BeanInfo)this.beanInfoCache.get(paramClass) : null;
/*     */   }
/*     */   
/*     */   BeanInfo putBeanInfo(Class<?> paramClass, BeanInfo paramBeanInfo)
/*     */   {
/*  98 */     if (this.beanInfoCache == null) {
/*  99 */       this.beanInfoCache = new WeakHashMap();
/*     */     }
/* 101 */     return (BeanInfo)this.beanInfoCache.put(paramClass, paramBeanInfo);
/*     */   }
/*     */   
/*     */   void removeBeanInfo(Class<?> paramClass) {
/* 105 */     if (this.beanInfoCache != null) {
/* 106 */       this.beanInfoCache.remove(paramClass);
/*     */     }
/*     */   }
/*     */   
/*     */   void clearBeanInfoCache() {
/* 111 */     if (this.beanInfoCache != null) {
/* 112 */       this.beanInfoCache.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized BeanInfoFinder getBeanInfoFinder()
/*     */   {
/* 118 */     if (this.beanInfoFinder == null) {
/* 119 */       this.beanInfoFinder = new BeanInfoFinder();
/*     */     }
/* 121 */     return this.beanInfoFinder;
/*     */   }
/*     */   
/*     */   synchronized PropertyEditorFinder getPropertyEditorFinder() {
/* 125 */     if (this.propertyEditorFinder == null) {
/* 126 */       this.propertyEditorFinder = new PropertyEditorFinder();
/*     */     }
/* 128 */     return this.propertyEditorFinder;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/ThreadGroupContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */