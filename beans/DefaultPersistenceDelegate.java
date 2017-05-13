/*     */ package java.beans;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.EventListener;
/*     */ import java.util.Objects;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPersistenceDelegate
/*     */   extends PersistenceDelegate
/*     */ {
/*  61 */   private static final String[] EMPTY = new String[0];
/*     */   
/*     */ 
/*     */   private final String[] constructor;
/*     */   
/*     */   private Boolean definesEquals;
/*     */   
/*     */ 
/*     */   public DefaultPersistenceDelegate()
/*     */   {
/*  71 */     this.constructor = EMPTY;
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
/*     */   public DefaultPersistenceDelegate(String[] paramArrayOfString)
/*     */   {
/*  96 */     this.constructor = (paramArrayOfString == null ? EMPTY : (String[])paramArrayOfString.clone());
/*     */   }
/*     */   
/*     */   private static boolean definesEquals(Class<?> paramClass) {
/*     */     try {
/* 101 */       return paramClass == paramClass.getMethod("equals", new Class[] { Object.class }).getDeclaringClass();
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {}
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   private boolean definesEquals(Object paramObject)
/*     */   {
/* 109 */     if (this.definesEquals != null) {
/* 110 */       return this.definesEquals == Boolean.TRUE;
/*     */     }
/*     */     
/* 113 */     boolean bool = definesEquals(paramObject.getClass());
/* 114 */     this.definesEquals = (bool ? Boolean.TRUE : Boolean.FALSE);
/* 115 */     return bool;
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
/*     */   protected boolean mutatesTo(Object paramObject1, Object paramObject2)
/*     */   {
/* 138 */     return (this.constructor.length == 0) || (!definesEquals(paramObject1)) ? super.mutatesTo(paramObject1, paramObject2) : paramObject1.equals(paramObject2);
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
/*     */   protected Expression instantiate(Object paramObject, Encoder paramEncoder)
/*     */   {
/* 157 */     int i = this.constructor.length;
/* 158 */     Class localClass = paramObject.getClass();
/* 159 */     Object[] arrayOfObject = new Object[i];
/* 160 */     for (int j = 0; j < i; j++) {
/*     */       try {
/* 162 */         Method localMethod = findMethod(localClass, this.constructor[j]);
/* 163 */         arrayOfObject[j] = MethodUtil.invoke(localMethod, paramObject, new Object[0]);
/*     */       }
/*     */       catch (Exception localException) {
/* 166 */         paramEncoder.getExceptionListener().exceptionThrown(localException);
/*     */       }
/*     */     }
/* 169 */     return new Expression(paramObject, paramObject.getClass(), "new", arrayOfObject);
/*     */   }
/*     */   
/*     */   private Method findMethod(Class<?> paramClass, String paramString) {
/* 173 */     if (paramString == null) {
/* 174 */       throw new IllegalArgumentException("Property name is null");
/*     */     }
/* 176 */     PropertyDescriptor localPropertyDescriptor = getPropertyDescriptor(paramClass, paramString);
/* 177 */     if (localPropertyDescriptor == null) {
/* 178 */       throw new IllegalStateException("Could not find property by the name " + paramString);
/*     */     }
/* 180 */     Method localMethod = localPropertyDescriptor.getReadMethod();
/* 181 */     if (localMethod == null) {
/* 182 */       throw new IllegalStateException("Could not find getter for the property " + paramString);
/*     */     }
/* 184 */     return localMethod;
/*     */   }
/*     */   
/*     */   private void doProperty(Class<?> paramClass, PropertyDescriptor paramPropertyDescriptor, Object paramObject1, Object paramObject2, Encoder paramEncoder) throws Exception {
/* 188 */     Method localMethod1 = paramPropertyDescriptor.getReadMethod();
/* 189 */     Method localMethod2 = paramPropertyDescriptor.getWriteMethod();
/*     */     
/* 191 */     if ((localMethod1 != null) && (localMethod2 != null)) {
/* 192 */       Expression localExpression1 = new Expression(paramObject1, localMethod1.getName(), new Object[0]);
/* 193 */       Expression localExpression2 = new Expression(paramObject2, localMethod1.getName(), new Object[0]);
/* 194 */       Object localObject1 = localExpression1.getValue();
/* 195 */       Object localObject2 = localExpression2.getValue();
/* 196 */       paramEncoder.writeExpression(localExpression1);
/* 197 */       if (!Objects.equals(localObject2, paramEncoder.get(localObject1)))
/*     */       {
/* 199 */         Object[] arrayOfObject1 = (Object[])paramPropertyDescriptor.getValue("enumerationValues");
/* 200 */         if (((arrayOfObject1 instanceof Object[])) && (Array.getLength(arrayOfObject1) % 3 == 0)) {
/* 201 */           Object[] arrayOfObject2 = (Object[])arrayOfObject1;
/* 202 */           for (int i = 0; i < arrayOfObject2.length; i += 3) {
/*     */             try {
/* 204 */               Field localField = paramClass.getField((String)arrayOfObject2[i]);
/* 205 */               if (localField.get(null).equals(localObject1)) {
/* 206 */                 paramEncoder.remove(localObject1);
/* 207 */                 paramEncoder.writeExpression(new Expression(localObject1, localField, "get", new Object[] { null }));
/*     */               }
/*     */             }
/*     */             catch (Exception localException) {}
/*     */           }
/*     */         }
/* 213 */         invokeStatement(paramObject1, localMethod2.getName(), new Object[] { localObject1 }, paramEncoder);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 219 */   static void invokeStatement(Object paramObject, String paramString, Object[] paramArrayOfObject, Encoder paramEncoder) { paramEncoder.writeStatement(new Statement(paramObject, paramString, paramArrayOfObject)); }
/*     */   
/*     */   private void initBean(Class<?> paramClass, Object paramObject1, Object paramObject2, Encoder paramEncoder) { Object localObject4;
/*     */     Object localObject5;
/*     */     Object localObject6;
/* 224 */     for (Object localObject3 : paramClass.getFields()) {
/* 225 */       if (ReflectUtil.isPackageAccessible(((Field)localObject3).getDeclaringClass()))
/*     */       {
/*     */ 
/* 228 */         int m = ((Field)localObject3).getModifiers();
/* 229 */         if ((!Modifier.isFinal(m)) && (!Modifier.isStatic(m)) && (!Modifier.isTransient(m)))
/*     */         {
/*     */           try
/*     */           {
/* 233 */             Expression localExpression = new Expression(localObject3, "get", new Object[] { paramObject1 });
/* 234 */             localObject4 = new Expression(localObject3, "get", new Object[] { paramObject2 });
/* 235 */             localObject5 = localExpression.getValue();
/* 236 */             localObject6 = ((Expression)localObject4).getValue();
/* 237 */             paramEncoder.writeExpression(localExpression);
/* 238 */             if (!Objects.equals(localObject6, paramEncoder.get(localObject5))) {
/* 239 */               paramEncoder.writeStatement(new Statement(localObject3, "set", new Object[] { paramObject1, localObject5 }));
/*     */             }
/*     */           }
/*     */           catch (Exception localException1) {
/* 243 */             paramEncoder.getExceptionListener().exceptionThrown(localException1);
/*     */           } }
/*     */       }
/*     */     }
/*     */     try {
/* 248 */       ??? = Introspector.getBeanInfo(paramClass);
/*     */     } catch (IntrospectionException localIntrospectionException) {
/*     */       return;
/*     */     }
/*     */     PropertyDescriptor localPropertyDescriptor;
/* 253 */     for (localPropertyDescriptor : ((BeanInfo)???).getPropertyDescriptors()) {
/* 254 */       if (!localPropertyDescriptor.isTransient())
/*     */       {
/*     */         try
/*     */         {
/* 258 */           doProperty(paramClass, localPropertyDescriptor, paramObject1, paramObject2, paramEncoder);
/*     */         }
/*     */         catch (Exception localException2) {
/* 261 */           paramEncoder.getExceptionListener().exceptionThrown(localException2);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 289 */     if (!Component.class.isAssignableFrom(paramClass)) {
/* 290 */       return;
/*     */     }
/* 292 */     for (localPropertyDescriptor : ((BeanInfo)???).getEventSetDescriptors()) {
/* 293 */       if (!localPropertyDescriptor.isTransient())
/*     */       {
/*     */ 
/* 296 */         Class localClass = localPropertyDescriptor.getListenerType();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 301 */         if (localClass != ComponentListener.class)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 312 */           if ((localClass != ChangeListener.class) || (paramClass != JMenuItem.class))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 317 */             localObject4 = new EventListener[0];
/* 318 */             localObject5 = new EventListener[0];
/*     */             try {
/* 320 */               localObject6 = localPropertyDescriptor.getGetListenerMethod();
/* 321 */               localObject4 = (EventListener[])MethodUtil.invoke((Method)localObject6, paramObject1, new Object[0]);
/* 322 */               localObject5 = (EventListener[])MethodUtil.invoke((Method)localObject6, paramObject2, new Object[0]);
/*     */             }
/*     */             catch (Exception localException3) {
/*     */               try {
/* 326 */                 Method localMethod = paramClass.getMethod("getListeners", new Class[] { Class.class });
/* 327 */                 localObject4 = (EventListener[])MethodUtil.invoke(localMethod, paramObject1, new Object[] { localClass });
/* 328 */                 localObject5 = (EventListener[])MethodUtil.invoke(localMethod, paramObject2, new Object[] { localClass });
/*     */               }
/*     */               catch (Exception localException4) {
/* 331 */                 return;
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 337 */             String str1 = localPropertyDescriptor.getAddListenerMethod().getName();
/* 338 */             for (int n = localObject5.length; n < localObject4.length; n++)
/*     */             {
/* 340 */               invokeStatement(paramObject1, str1, new Object[] { localObject4[n] }, paramEncoder);
/*     */             }
/*     */             
/* 343 */             String str2 = localPropertyDescriptor.getRemoveListenerMethod().getName();
/* 344 */             for (int i1 = localObject4.length; i1 < localObject5.length; i1++) {
/* 345 */               invokeStatement(paramObject1, str2, new Object[] { localObject5[i1] }, paramEncoder);
/*     */             }
/*     */           }
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initialize(Class<?> paramClass, Object paramObject1, Object paramObject2, Encoder paramEncoder)
/*     */   {
/* 404 */     super.initialize(paramClass, paramObject1, paramObject2, paramEncoder);
/* 405 */     if (paramObject1.getClass() == paramClass) {
/* 406 */       initBean(paramClass, paramObject1, paramObject2, paramEncoder);
/*     */     }
/*     */   }
/*     */   
/*     */   private static PropertyDescriptor getPropertyDescriptor(Class<?> paramClass, String paramString) {
/*     */     try {
/* 412 */       for (PropertyDescriptor localPropertyDescriptor : Introspector.getBeanInfo(paramClass).getPropertyDescriptors()) {
/* 413 */         if (paramString.equals(localPropertyDescriptor.getName())) {
/* 414 */           return localPropertyDescriptor;
/*     */         }
/*     */       }
/*     */     } catch (IntrospectionException localIntrospectionException) {}
/* 418 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/DefaultPersistenceDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */