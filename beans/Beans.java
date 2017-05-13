/*     */ package java.beans;
/*     */ 
/*     */ import com.sun.beans.finder.ClassFinder;
/*     */ import java.applet.Applet;
/*     */ import java.applet.AppletContext;
/*     */ import java.applet.AppletStub;
/*     */ import java.beans.beancontext.BeanContext;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Beans
/*     */ {
/*     */   public static Object instantiate(ClassLoader paramClassLoader, String paramString)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  80 */     return instantiate(paramClassLoader, paramString, null, null);
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
/*     */   public static Object instantiate(ClassLoader paramClassLoader, String paramString, BeanContext paramBeanContext)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 102 */     return instantiate(paramClassLoader, paramString, paramBeanContext, null);
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
/*     */   public static Object instantiate(ClassLoader paramClassLoader, String paramString, BeanContext paramBeanContext, AppletInitializer paramAppletInitializer)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 161 */     Object localObject1 = null;
/* 162 */     Object localObject2 = null;
/* 163 */     int i = 0;
/* 164 */     Object localObject3 = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */     if (paramClassLoader == null) {
/*     */       try {
/* 173 */         paramClassLoader = ClassLoader.getSystemClassLoader();
/*     */       }
/*     */       catch (SecurityException localSecurityException) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 181 */     final String str1 = paramString.replace('.', '/').concat(".ser");
/* 182 */     ClassLoader localClassLoader1 = paramClassLoader;
/*     */     
/* 184 */     InputStream localInputStream = (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public InputStream run() {
/* 186 */         if (this.val$loader == null) {
/* 187 */           return ClassLoader.getSystemResourceAsStream(str1);
/*     */         }
/* 189 */         return this.val$loader.getResourceAsStream(str1);
/*     */       }
/*     */     });
/* 192 */     if (localInputStream != null) {
/*     */       try {
/* 194 */         if (paramClassLoader == null) {
/* 195 */           localObject1 = new ObjectInputStream(localInputStream);
/*     */         } else {
/* 197 */           localObject1 = new ObjectInputStreamWithLoader(localInputStream, paramClassLoader);
/*     */         }
/* 199 */         localObject2 = ((ObjectInputStream)localObject1).readObject();
/* 200 */         i = 1;
/* 201 */         ((ObjectInputStream)localObject1).close();
/*     */       } catch (IOException localIOException) {
/* 203 */         localInputStream.close();
/*     */         
/*     */ 
/* 206 */         localObject3 = localIOException;
/*     */       } catch (ClassNotFoundException localClassNotFoundException1) {
/* 208 */         localInputStream.close();
/* 209 */         throw localClassNotFoundException1;
/*     */       }
/*     */     }
/*     */     Object localObject4;
/* 213 */     if (localObject2 == null)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/* 218 */         localObject4 = ClassFinder.findClass(paramString, paramClassLoader);
/*     */ 
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException2)
/*     */       {
/* 223 */         if (localObject3 != null) {
/* 224 */           throw ((Throwable)localObject3);
/*     */         }
/* 226 */         throw localClassNotFoundException2;
/*     */       }
/*     */       
/* 229 */       if (!Modifier.isPublic(((Class)localObject4).getModifiers())) {
/* 230 */         throw new ClassNotFoundException("" + localObject4 + " : no public access");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 238 */         localObject2 = ((Class)localObject4).newInstance();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 242 */         throw new ClassNotFoundException("" + localObject4 + " : " + localException, localException);
/*     */       }
/*     */     }
/*     */     
/* 246 */     if (localObject2 != null)
/*     */     {
/*     */ 
/*     */ 
/* 250 */       localObject4 = null;
/*     */       
/* 252 */       if ((localObject2 instanceof Applet)) {
/* 253 */         Applet localApplet = (Applet)localObject2;
/* 254 */         int j = paramAppletInitializer == null ? 1 : 0;
/*     */         
/* 256 */         if (j != 0)
/*     */         {
/*     */           final String str2;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 269 */           if (i != 0)
/*     */           {
/* 271 */             str2 = paramString.replace('.', '/').concat(".ser");
/*     */           }
/*     */           else {
/* 274 */             str2 = paramString.replace('.', '/').concat(".class");
/*     */           }
/*     */           
/* 277 */           URL localURL1 = null;
/* 278 */           URL localURL2 = null;
/* 279 */           URL localURL3 = null;
/*     */           
/*     */ 
/*     */ 
/* 283 */           ClassLoader localClassLoader2 = paramClassLoader;
/*     */           
/*     */ 
/* 286 */           localURL1 = (URL)AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public URL run()
/*     */             {
/* 288 */               if (this.val$cloader == null)
/*     */               {
/* 290 */                 return ClassLoader.getSystemResource(str2);
/*     */               }
/* 292 */               return this.val$cloader.getResource(str2);
/*     */             }
/*     */           });
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 304 */           if (localURL1 != null) {
/* 305 */             localObject5 = localURL1.toExternalForm();
/*     */             
/* 307 */             if (((String)localObject5).endsWith(str2)) {
/* 308 */               int k = ((String)localObject5).length() - str2.length();
/* 309 */               localURL2 = new URL(((String)localObject5).substring(0, k));
/* 310 */               localURL3 = localURL2;
/*     */               
/* 312 */               k = ((String)localObject5).lastIndexOf('/');
/*     */               
/* 314 */               if (k >= 0) {
/* 315 */                 localURL3 = new URL(((String)localObject5).substring(0, k + 1));
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 321 */           Object localObject5 = new BeansAppletContext(localApplet);
/*     */           
/* 323 */           localObject4 = new BeansAppletStub(localApplet, (AppletContext)localObject5, localURL2, localURL3);
/* 324 */           localApplet.setStub((AppletStub)localObject4);
/*     */         } else {
/* 326 */           paramAppletInitializer.initialize(localApplet, paramBeanContext);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 331 */         if (paramBeanContext != null) {
/* 332 */           unsafeBeanContextAdd(paramBeanContext, localObject2);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 338 */         if (i == 0)
/*     */         {
/*     */ 
/*     */ 
/* 342 */           localApplet.setSize(100, 100);
/* 343 */           localApplet.init();
/*     */         }
/*     */         
/* 346 */         if (j != 0)
/* 347 */           ((BeansAppletStub)localObject4).active = true; else {
/* 348 */           paramAppletInitializer.activate(localApplet);
/*     */         }
/* 350 */       } else if (paramBeanContext != null) { unsafeBeanContextAdd(paramBeanContext, localObject2);
/*     */       }
/*     */     }
/* 353 */     return localObject2;
/*     */   }
/*     */   
/*     */   private static void unsafeBeanContextAdd(BeanContext paramBeanContext, Object paramObject)
/*     */   {
/* 358 */     paramBeanContext.add(paramObject);
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
/*     */   public static Object getInstanceOf(Object paramObject, Class<?> paramClass)
/*     */   {
/* 379 */     return paramObject;
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
/*     */   public static boolean isInstanceOf(Object paramObject, Class<?> paramClass)
/*     */   {
/* 394 */     return Introspector.isSubclass(paramObject.getClass(), paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDesignTime()
/*     */   {
/* 406 */     return ThreadGroupContext.getContext().isDesignTime();
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
/*     */   public static boolean isGuiAvailable()
/*     */   {
/* 423 */     return ThreadGroupContext.getContext().isGuiAvailable();
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
/*     */   public static void setDesignTime(boolean paramBoolean)
/*     */     throws SecurityException
/*     */   {
/* 445 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 446 */     if (localSecurityManager != null) {
/* 447 */       localSecurityManager.checkPropertiesAccess();
/*     */     }
/* 449 */     ThreadGroupContext.getContext().setDesignTime(paramBoolean);
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
/*     */   public static void setGuiAvailable(boolean paramBoolean)
/*     */     throws SecurityException
/*     */   {
/* 471 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 472 */     if (localSecurityManager != null) {
/* 473 */       localSecurityManager.checkPropertiesAccess();
/*     */     }
/* 475 */     ThreadGroupContext.getContext().setGuiAvailable(paramBoolean);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/Beans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */