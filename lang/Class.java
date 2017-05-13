/*      */ package java.lang;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.reflect.AnnotatedElement;
/*      */ import java.lang.reflect.AnnotatedType;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Executable;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.GenericDeclaration;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.ConstantPool;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.ReflectionFactory;
/*      */ import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
/*      */ import sun.reflect.annotation.AnnotationParser;
/*      */ import sun.reflect.annotation.AnnotationSupport;
/*      */ import sun.reflect.annotation.AnnotationType;
/*      */ import sun.reflect.annotation.TypeAnnotationParser;
/*      */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*      */ import sun.reflect.generics.factory.GenericsFactory;
/*      */ import sun.reflect.generics.repository.ClassRepository;
/*      */ import sun.reflect.generics.repository.ConstructorRepository;
/*      */ import sun.reflect.generics.repository.MethodRepository;
/*      */ import sun.reflect.generics.scope.ClassScope;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Class<T>
/*      */   implements Serializable, GenericDeclaration, Type, AnnotatedElement
/*      */ {
/*      */   private static final int ANNOTATION = 8192;
/*      */   private static final int ENUM = 16384;
/*      */   private static final int SYNTHETIC = 4096;
/*      */   private volatile transient Constructor<T> cachedConstructor;
/*      */   private volatile transient Class<?> newInstanceCallerCache;
/*      */   private transient String name;
/*      */   private static ProtectionDomain allPermDomain;
/*      */   
/*      */   private static native void registerNatives();
/*      */   
/*      */   public String toString()
/*      */   {
/*  152 */     return (isPrimitive() ? "" : isInterface() ? "interface " : "class ") + getName();
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
/*      */   public String toGenericString()
/*      */   {
/*  182 */     if (isPrimitive()) {
/*  183 */       return toString();
/*      */     }
/*  185 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/*      */ 
/*  188 */     int i = getModifiers() & Modifier.classModifiers();
/*  189 */     if (i != 0) {
/*  190 */       localStringBuilder.append(Modifier.toString(i));
/*  191 */       localStringBuilder.append(' ');
/*      */     }
/*      */     
/*  194 */     if (isAnnotation()) {
/*  195 */       localStringBuilder.append('@');
/*      */     }
/*  197 */     if (isInterface()) {
/*  198 */       localStringBuilder.append("interface");
/*      */     }
/*  200 */     else if (isEnum()) {
/*  201 */       localStringBuilder.append("enum");
/*      */     } else {
/*  203 */       localStringBuilder.append("class");
/*      */     }
/*  205 */     localStringBuilder.append(' ');
/*  206 */     localStringBuilder.append(getName());
/*      */     
/*  208 */     TypeVariable[] arrayOfTypeVariable1 = getTypeParameters();
/*  209 */     if (arrayOfTypeVariable1.length > 0) {
/*  210 */       int j = 1;
/*  211 */       localStringBuilder.append('<');
/*  212 */       for (TypeVariable localTypeVariable : arrayOfTypeVariable1) {
/*  213 */         if (j == 0)
/*  214 */           localStringBuilder.append(',');
/*  215 */         localStringBuilder.append(localTypeVariable.getTypeName());
/*  216 */         j = 0;
/*      */       }
/*  218 */       localStringBuilder.append('>');
/*      */     }
/*      */     
/*  221 */     return localStringBuilder.toString();
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
/*      */   @CallerSensitive
/*      */   public static Class<?> forName(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  259 */     Class localClass = Reflection.getCallerClass();
/*  260 */     return forName0(paramString, true, ClassLoader.getClassLoader(localClass), localClass);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public static Class<?> forName(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
/*      */     throws ClassNotFoundException
/*      */   {
/*  330 */     Class localClass = null;
/*  331 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  332 */     if (localSecurityManager != null)
/*      */     {
/*      */ 
/*  335 */       localClass = Reflection.getCallerClass();
/*  336 */       if (VM.isSystemDomainLoader(paramClassLoader)) {
/*  337 */         ClassLoader localClassLoader = ClassLoader.getClassLoader(localClass);
/*  338 */         if (!VM.isSystemDomainLoader(localClassLoader)) {
/*  339 */           localSecurityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  344 */     return forName0(paramString, paramBoolean, paramClassLoader, localClass);
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
/*      */   private static native Class<?> forName0(String paramString, boolean paramBoolean, ClassLoader paramClassLoader, Class<?> paramClass)
/*      */     throws ClassNotFoundException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public T newInstance()
/*      */     throws InstantiationException, IllegalAccessException
/*      */   {
/*  392 */     if (System.getSecurityManager() != null) {
/*  393 */       checkMemberAccess(0, Reflection.getCallerClass(), false);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  400 */     if (this.cachedConstructor == null) {
/*  401 */       if (this == Class.class) {
/*  402 */         throw new IllegalAccessException("Can not call newInstance() on the Class for java.lang.Class");
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  407 */         Class[] arrayOfClass = new Class[0];
/*  408 */         final Constructor localConstructor2 = getConstructor0(arrayOfClass, 1);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  413 */         AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Void run() {
/*  416 */             localConstructor2.setAccessible(true);
/*  417 */             return null;
/*      */           }
/*  419 */         });
/*  420 */         this.cachedConstructor = localConstructor2;
/*      */       }
/*      */       catch (NoSuchMethodException localNoSuchMethodException) {
/*  423 */         throw ((InstantiationException)new InstantiationException(getName()).initCause(localNoSuchMethodException));
/*      */       }
/*      */     }
/*  426 */     Constructor localConstructor1 = this.cachedConstructor;
/*      */     
/*  428 */     int i = localConstructor1.getModifiers();
/*  429 */     if (!Reflection.quickCheckMemberAccess(this, i)) {
/*  430 */       Class localClass = Reflection.getCallerClass();
/*  431 */       if (this.newInstanceCallerCache != localClass) {
/*  432 */         Reflection.ensureMemberAccess(localClass, this, null, i);
/*  433 */         this.newInstanceCallerCache = localClass;
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  438 */       return (T)localConstructor1.newInstance((Object[])null);
/*      */     } catch (InvocationTargetException localInvocationTargetException) {
/*  440 */       Unsafe.getUnsafe().throwException(localInvocationTargetException.getTargetException());
/*      */     }
/*  442 */     return null;
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
/*      */   public native boolean isInstance(Object paramObject);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native boolean isAssignableFrom(Class<?> paramClass);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native boolean isInterface();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native boolean isArray();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native boolean isPrimitive();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAnnotation()
/*      */   {
/*  569 */     return (getModifiers() & 0x2000) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isSynthetic()
/*      */   {
/*  581 */     return (getModifiers() & 0x1000) != 0;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  636 */     String str = this.name;
/*  637 */     if (str == null)
/*  638 */       this.name = (str = getName0());
/*  639 */     return str;
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
/*      */   private native String getName0();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public ClassLoader getClassLoader()
/*      */   {
/*  674 */     ClassLoader localClassLoader = getClassLoader0();
/*  675 */     if (localClassLoader == null)
/*  676 */       return null;
/*  677 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  678 */     if (localSecurityManager != null) {
/*  679 */       ClassLoader.checkClassLoaderPermission(localClassLoader, Reflection.getCallerClass());
/*      */     }
/*  681 */     return localClassLoader;
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
/*      */   native ClassLoader getClassLoader0();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeVariable<Class<T>>[] getTypeParameters()
/*      */   {
/*  705 */     ClassRepository localClassRepository = getGenericInfo();
/*  706 */     if (localClassRepository != null) {
/*  707 */       return (TypeVariable[])localClassRepository.getTypeParameters();
/*      */     }
/*  709 */     return (TypeVariable[])new TypeVariable[0];
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
/*      */   public native Class<? super T> getSuperclass();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Type getGenericSuperclass()
/*      */   {
/*  757 */     ClassRepository localClassRepository = getGenericInfo();
/*  758 */     if (localClassRepository == null) {
/*  759 */       return getSuperclass();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  765 */     if (isInterface()) {
/*  766 */       return null;
/*      */     }
/*      */     
/*  769 */     return localClassRepository.getSuperclass();
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
/*      */   public Package getPackage()
/*      */   {
/*  788 */     return Package.getPackage(this);
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
/*      */   public Class<?>[] getInterfaces()
/*      */   {
/*  837 */     ReflectionData localReflectionData = reflectionData();
/*  838 */     if (localReflectionData == null)
/*      */     {
/*  840 */       return getInterfaces0();
/*      */     }
/*  842 */     Class[] arrayOfClass = localReflectionData.interfaces;
/*  843 */     if (arrayOfClass == null) {
/*  844 */       arrayOfClass = getInterfaces0();
/*  845 */       localReflectionData.interfaces = arrayOfClass;
/*      */     }
/*      */     
/*  848 */     return (Class[])arrayOfClass.clone();
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
/*      */   private native Class<?>[] getInterfaces0();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Type[] getGenericInterfaces()
/*      */   {
/*  904 */     ClassRepository localClassRepository = getGenericInfo();
/*  905 */     return localClassRepository == null ? getInterfaces() : localClassRepository.getSuperInterfaces();
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
/*      */   public native Class<?> getComponentType();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native int getModifiers();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public native Object[] getSigners();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   native void setSigners(Object[] paramArrayOfObject);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public Method getEnclosingMethod()
/*      */     throws SecurityException
/*      */   {
/* 1007 */     EnclosingMethodInfo localEnclosingMethodInfo = getEnclosingMethodInfo();
/*      */     
/* 1009 */     if (localEnclosingMethodInfo == null) {
/* 1010 */       return null;
/*      */     }
/* 1012 */     if (!localEnclosingMethodInfo.isMethod()) {
/* 1013 */       return null;
/*      */     }
/* 1015 */     MethodRepository localMethodRepository = MethodRepository.make(localEnclosingMethodInfo.getDescriptor(), 
/* 1016 */       getFactory());
/* 1017 */     Class localClass1 = toClass(localMethodRepository.getReturnType());
/* 1018 */     Type[] arrayOfType = localMethodRepository.getParameterTypes();
/* 1019 */     Class[] arrayOfClass1 = new Class[arrayOfType.length];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1024 */     for (int i = 0; i < arrayOfClass1.length; i++) {
/* 1025 */       arrayOfClass1[i] = toClass(arrayOfType[i]);
/*      */     }
/*      */     
/* 1028 */     Class localClass2 = localEnclosingMethodInfo.getEnclosingClass();
/* 1029 */     localClass2.checkMemberAccess(1, 
/* 1030 */       Reflection.getCallerClass(), true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */     for (Method localMethod : localClass2.getDeclaredMethods()) {
/* 1038 */       if (localMethod.getName().equals(localEnclosingMethodInfo.getName())) {
/* 1039 */         Class[] arrayOfClass2 = localMethod.getParameterTypes();
/* 1040 */         if (arrayOfClass2.length == arrayOfClass1.length) {
/* 1041 */           int m = 1;
/* 1042 */           for (int n = 0; n < arrayOfClass2.length; n++) {
/* 1043 */             if (!arrayOfClass2[n].equals(arrayOfClass1[n])) {
/* 1044 */               m = 0;
/* 1045 */               break;
/*      */             }
/*      */           }
/*      */           
/* 1049 */           if ((m != 0) && 
/* 1050 */             (localMethod.getReturnType().equals(localClass1))) {
/* 1051 */             return localMethod;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1057 */     throw new InternalError("Enclosing method not found");
/*      */   }
/*      */   
/*      */   private native Object[] getEnclosingMethod0();
/*      */   
/*      */   private EnclosingMethodInfo getEnclosingMethodInfo()
/*      */   {
/* 1064 */     Object[] arrayOfObject = getEnclosingMethod0();
/* 1065 */     if (arrayOfObject == null) {
/* 1066 */       return null;
/*      */     }
/* 1068 */     return new EnclosingMethodInfo(arrayOfObject, null);
/*      */   }
/*      */   
/*      */   private static final class EnclosingMethodInfo
/*      */   {
/*      */     private Class<?> enclosingClass;
/*      */     private String name;
/*      */     private String descriptor;
/*      */     
/*      */     private EnclosingMethodInfo(Object[] paramArrayOfObject) {
/* 1078 */       if (paramArrayOfObject.length != 3) {
/* 1079 */         throw new InternalError("Malformed enclosing method information");
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1084 */         this.enclosingClass = ((Class)paramArrayOfObject[0]);
/* 1085 */         assert (this.enclosingClass != null);
/*      */         
/*      */ 
/*      */ 
/* 1089 */         this.name = ((String)paramArrayOfObject[1]);
/*      */         
/*      */ 
/*      */ 
/* 1093 */         this.descriptor = ((String)paramArrayOfObject[2]);
/* 1094 */         if ((!$assertionsDisabled) && ((this.name == null) || (this.descriptor == null)) && (this.name != this.descriptor)) throw new AssertionError();
/*      */       } catch (ClassCastException localClassCastException) {
/* 1096 */         throw new InternalError("Invalid type in enclosing method information", localClassCastException);
/*      */       }
/*      */     }
/*      */     
/*      */     boolean isPartial() {
/* 1101 */       return (this.enclosingClass == null) || (this.name == null) || (this.descriptor == null);
/*      */     }
/*      */     
/* 1104 */     boolean isConstructor() { return (!isPartial()) && ("<init>".equals(this.name)); }
/*      */     
/* 1106 */     boolean isMethod() { return (!isPartial()) && (!isConstructor()) && (!"<clinit>".equals(this.name)); }
/*      */     
/* 1108 */     Class<?> getEnclosingClass() { return this.enclosingClass; }
/*      */     
/* 1110 */     String getName() { return this.name; }
/*      */     
/* 1112 */     String getDescriptor() { return this.descriptor; }
/*      */   }
/*      */   
/*      */   private static Class<?> toClass(Type paramType)
/*      */   {
/* 1117 */     if ((paramType instanceof GenericArrayType))
/*      */     {
/*      */ 
/* 1120 */       return Array.newInstance(toClass(((GenericArrayType)paramType).getGenericComponentType()), 0).getClass(); }
/* 1121 */     return (Class)paramType;
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
/*      */   @CallerSensitive
/*      */   public Constructor<?> getEnclosingConstructor()
/*      */     throws SecurityException
/*      */   {
/* 1160 */     EnclosingMethodInfo localEnclosingMethodInfo = getEnclosingMethodInfo();
/*      */     
/* 1162 */     if (localEnclosingMethodInfo == null) {
/* 1163 */       return null;
/*      */     }
/* 1165 */     if (!localEnclosingMethodInfo.isConstructor()) {
/* 1166 */       return null;
/*      */     }
/* 1168 */     ConstructorRepository localConstructorRepository = ConstructorRepository.make(localEnclosingMethodInfo.getDescriptor(), 
/* 1169 */       getFactory());
/* 1170 */     Type[] arrayOfType = localConstructorRepository.getParameterTypes();
/* 1171 */     Class[] arrayOfClass1 = new Class[arrayOfType.length];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1176 */     for (int i = 0; i < arrayOfClass1.length; i++) {
/* 1177 */       arrayOfClass1[i] = toClass(arrayOfType[i]);
/*      */     }
/*      */     
/* 1180 */     Class localClass = localEnclosingMethodInfo.getEnclosingClass();
/* 1181 */     localClass.checkMemberAccess(1, 
/* 1182 */       Reflection.getCallerClass(), true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1187 */     for (Constructor localConstructor : localClass.getDeclaredConstructors()) {
/* 1188 */       Class[] arrayOfClass2 = localConstructor.getParameterTypes();
/* 1189 */       if (arrayOfClass2.length == arrayOfClass1.length) {
/* 1190 */         int m = 1;
/* 1191 */         for (int n = 0; n < arrayOfClass2.length; n++) {
/* 1192 */           if (!arrayOfClass2[n].equals(arrayOfClass1[n])) {
/* 1193 */             m = 0;
/* 1194 */             break;
/*      */           }
/*      */         }
/*      */         
/* 1198 */         if (m != 0) {
/* 1199 */           return localConstructor;
/*      */         }
/*      */       }
/*      */     }
/* 1203 */     throw new InternalError("Enclosing constructor not found");
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
/*      */   @CallerSensitive
/*      */   public Class<?> getDeclaringClass()
/*      */     throws SecurityException
/*      */   {
/* 1227 */     Class localClass = getDeclaringClass0();
/*      */     
/* 1229 */     if (localClass != null)
/* 1230 */       localClass.checkPackageAccess(
/* 1231 */         ClassLoader.getClassLoader(Reflection.getCallerClass()), true);
/* 1232 */     return localClass;
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
/*      */   private native Class<?> getDeclaringClass0();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public Class<?> getEnclosingClass()
/*      */     throws SecurityException
/*      */   {
/* 1264 */     EnclosingMethodInfo localEnclosingMethodInfo = getEnclosingMethodInfo();
/*      */     
/*      */     Object localObject;
/* 1267 */     if (localEnclosingMethodInfo == null)
/*      */     {
/* 1269 */       localObject = getDeclaringClass();
/*      */     } else {
/* 1271 */       Class localClass = localEnclosingMethodInfo.getEnclosingClass();
/*      */       
/* 1273 */       if ((localClass == this) || (localClass == null)) {
/* 1274 */         throw new InternalError("Malformed enclosing method information");
/*      */       }
/* 1276 */       localObject = localClass;
/*      */     }
/*      */     
/* 1279 */     if (localObject != null)
/* 1280 */       ((Class)localObject).checkPackageAccess(
/* 1281 */         ClassLoader.getClassLoader(Reflection.getCallerClass()), true);
/* 1282 */     return (Class<?>)localObject;
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
/*      */   public String getSimpleName()
/*      */   {
/* 1298 */     if (isArray()) {
/* 1299 */       return getComponentType().getSimpleName() + "[]";
/*      */     }
/* 1301 */     String str = getSimpleBinaryName();
/* 1302 */     if (str == null) {
/* 1303 */       str = getName();
/* 1304 */       return str.substring(str.lastIndexOf(".") + 1);
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
/* 1320 */     int i = str.length();
/* 1321 */     if ((i < 1) || (str.charAt(0) != '$'))
/* 1322 */       throw new InternalError("Malformed class name");
/* 1323 */     int j = 1;
/* 1324 */     while ((j < i) && (isAsciiDigit(str.charAt(j)))) {
/* 1325 */       j++;
/*      */     }
/* 1327 */     return str.substring(j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeName()
/*      */   {
/* 1337 */     if (isArray()) {
/*      */       try {
/* 1339 */         Class localClass = this;
/* 1340 */         int i = 0;
/* 1341 */         while (localClass.isArray()) {
/* 1342 */           i++;
/* 1343 */           localClass = localClass.getComponentType();
/*      */         }
/* 1345 */         StringBuilder localStringBuilder = new StringBuilder();
/* 1346 */         localStringBuilder.append(localClass.getName());
/* 1347 */         for (int j = 0; j < i; j++) {
/* 1348 */           localStringBuilder.append("[]");
/*      */         }
/* 1350 */         return localStringBuilder.toString();
/*      */       } catch (Throwable localThrowable) {}
/*      */     }
/* 1353 */     return getName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isAsciiDigit(char paramChar)
/*      */   {
/* 1361 */     return ('0' <= paramChar) && (paramChar <= '9');
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
/*      */   public String getCanonicalName()
/*      */   {
/* 1375 */     if (isArray()) {
/* 1376 */       localObject = getComponentType().getCanonicalName();
/* 1377 */       if (localObject != null) {
/* 1378 */         return (String)localObject + "[]";
/*      */       }
/* 1380 */       return null;
/*      */     }
/* 1382 */     if (isLocalOrAnonymousClass())
/* 1383 */       return null;
/* 1384 */     Object localObject = getEnclosingClass();
/* 1385 */     if (localObject == null) {
/* 1386 */       return getName();
/*      */     }
/* 1388 */     String str = ((Class)localObject).getCanonicalName();
/* 1389 */     if (str == null)
/* 1390 */       return null;
/* 1391 */     return str + "." + getSimpleName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAnonymousClass()
/*      */   {
/* 1403 */     return "".equals(getSimpleName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLocalClass()
/*      */   {
/* 1414 */     return (isLocalOrAnonymousClass()) && (!isAnonymousClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMemberClass()
/*      */   {
/* 1425 */     return (getSimpleBinaryName() != null) && (!isLocalOrAnonymousClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSimpleBinaryName()
/*      */   {
/* 1435 */     Class localClass = getEnclosingClass();
/* 1436 */     if (localClass == null) {
/* 1437 */       return null;
/*      */     }
/*      */     try {
/* 1440 */       return getName().substring(localClass.getName().length());
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1442 */       throw new InternalError("Malformed class name", localIndexOutOfBoundsException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isLocalOrAnonymousClass()
/*      */   {
/* 1454 */     return getEnclosingMethodInfo() != null;
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
/*      */   @CallerSensitive
/*      */   public Class<?>[] getClasses()
/*      */   {
/* 1482 */     checkMemberAccess(0, Reflection.getCallerClass(), false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1490 */     (Class[])AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Class<?>[] run() {
/* 1493 */         ArrayList localArrayList = new ArrayList();
/* 1494 */         Class localClass = Class.this;
/* 1495 */         while (localClass != null) {
/* 1496 */           Class[] arrayOfClass = localClass.getDeclaredClasses();
/* 1497 */           for (int i = 0; i < arrayOfClass.length; i++) {
/* 1498 */             if (Modifier.isPublic(arrayOfClass[i].getModifiers())) {
/* 1499 */               localArrayList.add(arrayOfClass[i]);
/*      */             }
/*      */           }
/* 1502 */           localClass = localClass.getSuperclass();
/*      */         }
/* 1504 */         return (Class[])localArrayList.toArray(new Class[0]);
/*      */       }
/*      */     });
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
/*      */   @CallerSensitive
/*      */   public Field[] getFields()
/*      */     throws SecurityException
/*      */   {
/* 1548 */     checkMemberAccess(0, Reflection.getCallerClass(), true);
/* 1549 */     return copyFields(privateGetPublicFields(null));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public Method[] getMethods()
/*      */     throws SecurityException
/*      */   {
/* 1606 */     checkMemberAccess(0, Reflection.getCallerClass(), true);
/* 1607 */     return copyMethods(privateGetPublicMethods());
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
/*      */   @CallerSensitive
/*      */   public Constructor<?>[] getConstructors()
/*      */     throws SecurityException
/*      */   {
/* 1642 */     checkMemberAccess(0, Reflection.getCallerClass(), true);
/* 1643 */     return copyConstructors(privateGetDeclaredConstructors(true));
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
/*      */   @CallerSensitive
/*      */   public Field getField(String paramString)
/*      */     throws NoSuchFieldException, SecurityException
/*      */   {
/* 1692 */     checkMemberAccess(0, Reflection.getCallerClass(), true);
/* 1693 */     Field localField = getField0(paramString);
/* 1694 */     if (localField == null) {
/* 1695 */       throw new NoSuchFieldException(paramString);
/*      */     }
/* 1697 */     return localField;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public Method getMethod(String paramString, Class<?>... paramVarArgs)
/*      */     throws NoSuchMethodException, SecurityException
/*      */   {
/* 1775 */     checkMemberAccess(0, Reflection.getCallerClass(), true);
/* 1776 */     Method localMethod = getMethod0(paramString, paramVarArgs, true);
/* 1777 */     if (localMethod == null) {
/* 1778 */       throw new NoSuchMethodException(getName() + "." + paramString + argumentTypesToString(paramVarArgs));
/*      */     }
/* 1780 */     return localMethod;
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
/*      */   @CallerSensitive
/*      */   public Constructor<T> getConstructor(Class<?>... paramVarArgs)
/*      */     throws NoSuchMethodException, SecurityException
/*      */   {
/* 1816 */     checkMemberAccess(0, Reflection.getCallerClass(), true);
/* 1817 */     return getConstructor0(paramVarArgs, 0);
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
/*      */   @CallerSensitive
/*      */   public Class<?>[] getDeclaredClasses()
/*      */     throws SecurityException
/*      */   {
/* 1858 */     checkMemberAccess(1, Reflection.getCallerClass(), false);
/* 1859 */     return getDeclaredClasses0();
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
/*      */   @CallerSensitive
/*      */   public Field[] getDeclaredFields()
/*      */     throws SecurityException
/*      */   {
/* 1907 */     checkMemberAccess(1, Reflection.getCallerClass(), true);
/* 1908 */     return copyFields(privateGetDeclaredFields(false));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public Method[] getDeclaredMethods()
/*      */     throws SecurityException
/*      */   {
/* 1966 */     checkMemberAccess(1, Reflection.getCallerClass(), true);
/* 1967 */     return copyMethods(privateGetDeclaredMethods(false));
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
/*      */   @CallerSensitive
/*      */   public Constructor<?>[] getDeclaredConstructors()
/*      */     throws SecurityException
/*      */   {
/* 2011 */     checkMemberAccess(1, Reflection.getCallerClass(), true);
/* 2012 */     return copyConstructors(privateGetDeclaredConstructors(false));
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
/*      */   @CallerSensitive
/*      */   public Field getDeclaredField(String paramString)
/*      */     throws NoSuchFieldException, SecurityException
/*      */   {
/* 2059 */     checkMemberAccess(1, Reflection.getCallerClass(), true);
/* 2060 */     Field localField = searchFields(privateGetDeclaredFields(false), paramString);
/* 2061 */     if (localField == null) {
/* 2062 */       throw new NoSuchFieldException(paramString);
/*      */     }
/* 2064 */     return localField;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @CallerSensitive
/*      */   public Method getDeclaredMethod(String paramString, Class<?>... paramVarArgs)
/*      */     throws NoSuchMethodException, SecurityException
/*      */   {
/* 2119 */     checkMemberAccess(1, Reflection.getCallerClass(), true);
/* 2120 */     Method localMethod = searchMethods(privateGetDeclaredMethods(false), paramString, paramVarArgs);
/* 2121 */     if (localMethod == null) {
/* 2122 */       throw new NoSuchMethodException(getName() + "." + paramString + argumentTypesToString(paramVarArgs));
/*      */     }
/* 2124 */     return localMethod;
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
/*      */   @CallerSensitive
/*      */   public Constructor<T> getDeclaredConstructor(Class<?>... paramVarArgs)
/*      */     throws NoSuchMethodException, SecurityException
/*      */   {
/* 2169 */     checkMemberAccess(1, Reflection.getCallerClass(), true);
/* 2170 */     return getConstructor0(paramVarArgs, 1);
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
/*      */   public InputStream getResourceAsStream(String paramString)
/*      */   {
/* 2209 */     paramString = resolveName(paramString);
/* 2210 */     ClassLoader localClassLoader = getClassLoader0();
/* 2211 */     if (localClassLoader == null)
/*      */     {
/* 2213 */       return ClassLoader.getSystemResourceAsStream(paramString);
/*      */     }
/* 2215 */     return localClassLoader.getResourceAsStream(paramString);
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
/*      */   public URL getResource(String paramString)
/*      */   {
/* 2253 */     paramString = resolveName(paramString);
/* 2254 */     ClassLoader localClassLoader = getClassLoader0();
/* 2255 */     if (localClassLoader == null)
/*      */     {
/* 2257 */       return ClassLoader.getSystemResource(paramString);
/*      */     }
/* 2259 */     return localClassLoader.getResource(paramString);
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
/*      */   public ProtectionDomain getProtectionDomain()
/*      */   {
/* 2289 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2290 */     if (localSecurityManager != null) {
/* 2291 */       localSecurityManager.checkPermission(SecurityConstants.GET_PD_PERMISSION);
/*      */     }
/* 2293 */     ProtectionDomain localProtectionDomain = getProtectionDomain0();
/* 2294 */     if (localProtectionDomain == null) {
/* 2295 */       if (allPermDomain == null) {
/* 2296 */         Permissions localPermissions = new Permissions();
/*      */         
/* 2298 */         localPermissions.add(SecurityConstants.ALL_PERMISSION);
/* 2299 */         allPermDomain = new ProtectionDomain(null, localPermissions);
/*      */       }
/*      */       
/* 2302 */       localProtectionDomain = allPermDomain;
/*      */     }
/* 2304 */     return localProtectionDomain;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native ProtectionDomain getProtectionDomain0();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static native Class<?> getPrimitiveClass(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkMemberAccess(int paramInt, Class<?> paramClass, boolean paramBoolean)
/*      */   {
/* 2329 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2330 */     if (localSecurityManager != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2336 */       ClassLoader localClassLoader1 = ClassLoader.getClassLoader(paramClass);
/* 2337 */       ClassLoader localClassLoader2 = getClassLoader0();
/* 2338 */       if ((paramInt != 0) && 
/* 2339 */         (localClassLoader1 != localClassLoader2)) {
/* 2340 */         localSecurityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
/*      */       }
/*      */       
/* 2343 */       checkPackageAccess(localClassLoader1, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkPackageAccess(ClassLoader paramClassLoader, boolean paramBoolean)
/*      */   {
/* 2353 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2354 */     if (localSecurityManager != null) {
/* 2355 */       ClassLoader localClassLoader = getClassLoader0();
/*      */       
/* 2357 */       if (ReflectUtil.needsPackageAccessCheck(paramClassLoader, localClassLoader)) {
/* 2358 */         String str1 = getName();
/* 2359 */         int i = str1.lastIndexOf('.');
/* 2360 */         if (i != -1)
/*      */         {
/* 2362 */           String str2 = str1.substring(0, i);
/* 2363 */           if ((!Proxy.isProxyClass(this)) || (ReflectUtil.isNonPublicProxyClass(this))) {
/* 2364 */             localSecurityManager.checkPackageAccess(str2);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2369 */       if ((paramBoolean) && (Proxy.isProxyClass(this))) {
/* 2370 */         ReflectUtil.checkProxyPackageAccess(paramClassLoader, getInterfaces());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String resolveName(String paramString)
/*      */   {
/* 2380 */     if (paramString == null) {
/* 2381 */       return paramString;
/*      */     }
/* 2383 */     if (!paramString.startsWith("/")) {
/* 2384 */       Class localClass = this;
/* 2385 */       while (localClass.isArray()) {
/* 2386 */         localClass = localClass.getComponentType();
/*      */       }
/* 2388 */       String str = localClass.getName();
/* 2389 */       int i = str.lastIndexOf('.');
/* 2390 */       if (i != -1) {
/* 2391 */         paramString = str.substring(0, i).replace('.', '/') + "/" + paramString;
/*      */       }
/*      */     }
/*      */     else {
/* 2395 */       paramString = paramString.substring(1);
/*      */     }
/* 2397 */     return paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class Atomic
/*      */   {
/* 2406 */     private static final Unsafe unsafe = ;
/*      */     
/*      */     private static final long reflectionDataOffset;
/*      */     
/*      */     private static final long annotationTypeOffset;
/*      */     private static final long annotationDataOffset;
/*      */     
/*      */     static
/*      */     {
/* 2415 */       Field[] arrayOfField = Class.class.getDeclaredFields0(false);
/* 2416 */       reflectionDataOffset = objectFieldOffset(arrayOfField, "reflectionData");
/* 2417 */       annotationTypeOffset = objectFieldOffset(arrayOfField, "annotationType");
/* 2418 */       annotationDataOffset = objectFieldOffset(arrayOfField, "annotationData");
/*      */     }
/*      */     
/*      */     private static long objectFieldOffset(Field[] paramArrayOfField, String paramString) {
/* 2422 */       Field localField = Class.searchFields(paramArrayOfField, paramString);
/* 2423 */       if (localField == null) {
/* 2424 */         throw new Error("No " + paramString + " field found in java.lang.Class");
/*      */       }
/* 2426 */       return unsafe.objectFieldOffset(localField);
/*      */     }
/*      */     
/*      */ 
/*      */     static <T> boolean casReflectionData(Class<?> paramClass, SoftReference<Class.ReflectionData<T>> paramSoftReference1, SoftReference<Class.ReflectionData<T>> paramSoftReference2)
/*      */     {
/* 2432 */       return unsafe.compareAndSwapObject(paramClass, reflectionDataOffset, paramSoftReference1, paramSoftReference2);
/*      */     }
/*      */     
/*      */ 
/*      */     static <T> boolean casAnnotationType(Class<?> paramClass, AnnotationType paramAnnotationType1, AnnotationType paramAnnotationType2)
/*      */     {
/* 2438 */       return unsafe.compareAndSwapObject(paramClass, annotationTypeOffset, paramAnnotationType1, paramAnnotationType2);
/*      */     }
/*      */     
/*      */ 
/*      */     static <T> boolean casAnnotationData(Class<?> paramClass, Class.AnnotationData paramAnnotationData1, Class.AnnotationData paramAnnotationData2)
/*      */     {
/* 2444 */       return unsafe.compareAndSwapObject(paramClass, annotationDataOffset, paramAnnotationData1, paramAnnotationData2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2453 */   private static boolean useCaches = true;
/*      */   
/*      */   private volatile transient SoftReference<ReflectionData<T>> reflectionData;
/*      */   
/*      */   private static class ReflectionData<T>
/*      */   {
/*      */     volatile Field[] declaredFields;
/*      */     volatile Field[] publicFields;
/*      */     volatile Method[] declaredMethods;
/*      */     volatile Method[] publicMethods;
/*      */     volatile Constructor<T>[] declaredConstructors;
/*      */     volatile Constructor<T>[] publicConstructors;
/*      */     volatile Field[] declaredPublicFields;
/*      */     volatile Method[] declaredPublicMethods;
/*      */     volatile Class<?>[] interfaces;
/*      */     final int redefinedCount;
/*      */     
/*      */     ReflectionData(int paramInt)
/*      */     {
/* 2472 */       this.redefinedCount = paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2480 */   private volatile transient int classRedefinedCount = 0;
/*      */   private volatile transient ClassRepository genericInfo;
/*      */   private static final long serialVersionUID = 3206093459760846163L;
/*      */   
/* 2484 */   private ReflectionData<T> reflectionData() { SoftReference localSoftReference = this.reflectionData;
/* 2485 */     int i = this.classRedefinedCount;
/*      */     
/* 2487 */     if ((useCaches) && (localSoftReference != null)) {
/*      */       ReflectionData localReflectionData;
/* 2489 */       if (((localReflectionData = (ReflectionData)localSoftReference.get()) != null) && (localReflectionData.redefinedCount == i))
/*      */       {
/* 2491 */         return localReflectionData;
/*      */       }
/*      */     }
/*      */     
/* 2495 */     return newReflectionData(localSoftReference, i);
/*      */   }
/*      */   
/*      */   private ReflectionData<T> newReflectionData(SoftReference<ReflectionData<T>> paramSoftReference, int paramInt)
/*      */   {
/* 2500 */     if (!useCaches) return null;
/*      */     for (;;)
/*      */     {
/* 2503 */       ReflectionData localReflectionData = new ReflectionData(paramInt);
/*      */       
/* 2505 */       if (Atomic.casReflectionData(this, paramSoftReference, new SoftReference(localReflectionData))) {
/* 2506 */         return localReflectionData;
/*      */       }
/*      */       
/* 2509 */       paramSoftReference = this.reflectionData;
/* 2510 */       paramInt = this.classRedefinedCount;
/* 2511 */       if ((paramSoftReference != null) && 
/* 2512 */         ((localReflectionData = (ReflectionData)paramSoftReference.get()) != null) && (localReflectionData.redefinedCount == paramInt))
/*      */       {
/* 2514 */         return localReflectionData;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private native String getGenericSignature0();
/*      */   
/*      */ 
/*      */ 
/*      */   private GenericsFactory getFactory()
/*      */   {
/* 2528 */     return CoreReflectionFactory.make(this, ClassScope.make(this));
/*      */   }
/*      */   
/*      */ 
/*      */   private ClassRepository getGenericInfo()
/*      */   {
/* 2534 */     ClassRepository localClassRepository = this.genericInfo;
/* 2535 */     if (localClassRepository == null) {
/* 2536 */       String str = getGenericSignature0();
/* 2537 */       if (str == null) {
/* 2538 */         localClassRepository = ClassRepository.NONE;
/*      */       } else {
/* 2540 */         localClassRepository = ClassRepository.make(str, getFactory());
/*      */       }
/* 2542 */       this.genericInfo = localClassRepository;
/*      */     }
/* 2544 */     return localClassRepository != ClassRepository.NONE ? localClassRepository : null;
/*      */   }
/*      */   
/*      */   native byte[] getRawAnnotations();
/*      */   
/*      */   native byte[] getRawTypeAnnotations();
/*      */   
/*      */   static byte[] getExecutableTypeAnnotationBytes(Executable paramExecutable) {
/* 2552 */     return getReflectionFactory().getExecutableTypeAnnotationBytes(paramExecutable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   native ConstantPool getConstantPool();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Field[] privateGetDeclaredFields(boolean paramBoolean)
/*      */   {
/* 2567 */     checkInitted();
/*      */     
/* 2569 */     ReflectionData localReflectionData = reflectionData();
/* 2570 */     if (localReflectionData != null) {
/* 2571 */       arrayOfField = paramBoolean ? localReflectionData.declaredPublicFields : localReflectionData.declaredFields;
/* 2572 */       if (arrayOfField != null) { return arrayOfField;
/*      */       }
/*      */     }
/* 2575 */     Field[] arrayOfField = Reflection.filterFields(this, getDeclaredFields0(paramBoolean));
/* 2576 */     if (localReflectionData != null) {
/* 2577 */       if (paramBoolean) {
/* 2578 */         localReflectionData.declaredPublicFields = arrayOfField;
/*      */       } else {
/* 2580 */         localReflectionData.declaredFields = arrayOfField;
/*      */       }
/*      */     }
/* 2583 */     return arrayOfField;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Field[] privateGetPublicFields(Set<Class<?>> paramSet)
/*      */   {
/* 2590 */     checkInitted();
/*      */     
/* 2592 */     ReflectionData localReflectionData = reflectionData();
/* 2593 */     if (localReflectionData != null) {
/* 2594 */       arrayOfField1 = localReflectionData.publicFields;
/* 2595 */       if (arrayOfField1 != null) { return arrayOfField1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2600 */     ArrayList localArrayList = new ArrayList();
/* 2601 */     if (paramSet == null) {
/* 2602 */       paramSet = new HashSet();
/*      */     }
/*      */     
/*      */ 
/* 2606 */     Field[] arrayOfField2 = privateGetDeclaredFields(true);
/* 2607 */     addAll(localArrayList, arrayOfField2);
/*      */     
/*      */ 
/* 2610 */     for (Object localObject2 : getInterfaces()) {
/* 2611 */       if (!paramSet.contains(localObject2)) {
/* 2612 */         paramSet.add(localObject2);
/* 2613 */         addAll(localArrayList, ((Class)localObject2).privateGetPublicFields(paramSet));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2618 */     if (!isInterface()) {
/* 2619 */       ??? = getSuperclass();
/* 2620 */       if (??? != null) {
/* 2621 */         addAll(localArrayList, ((Class)???).privateGetPublicFields(paramSet));
/*      */       }
/*      */     }
/*      */     
/* 2625 */     Field[] arrayOfField1 = new Field[localArrayList.size()];
/* 2626 */     localArrayList.toArray(arrayOfField1);
/* 2627 */     if (localReflectionData != null) {
/* 2628 */       localReflectionData.publicFields = arrayOfField1;
/*      */     }
/* 2630 */     return arrayOfField1;
/*      */   }
/*      */   
/*      */   private static void addAll(Collection<Field> paramCollection, Field[] paramArrayOfField) {
/* 2634 */     for (int i = 0; i < paramArrayOfField.length; i++) {
/* 2635 */       paramCollection.add(paramArrayOfField[i]);
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
/*      */   private Constructor<T>[] privateGetDeclaredConstructors(boolean paramBoolean)
/*      */   {
/* 2650 */     checkInitted();
/*      */     
/* 2652 */     ReflectionData localReflectionData = reflectionData();
/* 2653 */     Object localObject; if (localReflectionData != null) {
/* 2654 */       localObject = paramBoolean ? localReflectionData.publicConstructors : localReflectionData.declaredConstructors;
/* 2655 */       if (localObject != null) { return (Constructor<T>[])localObject;
/*      */       }
/*      */     }
/* 2658 */     if (isInterface())
/*      */     {
/* 2660 */       Constructor[] arrayOfConstructor = (Constructor[])new Constructor[0];
/* 2661 */       localObject = arrayOfConstructor;
/*      */     } else {
/* 2663 */       localObject = getDeclaredConstructors0(paramBoolean);
/*      */     }
/* 2665 */     if (localReflectionData != null) {
/* 2666 */       if (paramBoolean) {
/* 2667 */         localReflectionData.publicConstructors = ((Constructor[])localObject);
/*      */       } else {
/* 2669 */         localReflectionData.declaredConstructors = ((Constructor[])localObject);
/*      */       }
/*      */     }
/* 2672 */     return (Constructor<T>[])localObject;
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
/*      */   private Method[] privateGetDeclaredMethods(boolean paramBoolean)
/*      */   {
/* 2685 */     checkInitted();
/*      */     
/* 2687 */     ReflectionData localReflectionData = reflectionData();
/* 2688 */     if (localReflectionData != null) {
/* 2689 */       arrayOfMethod = paramBoolean ? localReflectionData.declaredPublicMethods : localReflectionData.declaredMethods;
/* 2690 */       if (arrayOfMethod != null) { return arrayOfMethod;
/*      */       }
/*      */     }
/* 2693 */     Method[] arrayOfMethod = Reflection.filterMethods(this, getDeclaredMethods0(paramBoolean));
/* 2694 */     if (localReflectionData != null) {
/* 2695 */       if (paramBoolean) {
/* 2696 */         localReflectionData.declaredPublicMethods = arrayOfMethod;
/*      */       } else {
/* 2698 */         localReflectionData.declaredMethods = arrayOfMethod;
/*      */       }
/*      */     }
/* 2701 */     return arrayOfMethod;
/*      */   }
/*      */   
/*      */   static class MethodArray
/*      */   {
/*      */     private Method[] methods;
/*      */     private int length;
/*      */     private int defaults;
/*      */     
/*      */     MethodArray() {
/* 2711 */       this(20);
/*      */     }
/*      */     
/*      */     MethodArray(int paramInt) {
/* 2715 */       if (paramInt < 2) {
/* 2716 */         throw new IllegalArgumentException("Size should be 2 or more");
/*      */       }
/* 2718 */       this.methods = new Method[paramInt];
/* 2719 */       this.length = 0;
/* 2720 */       this.defaults = 0;
/*      */     }
/*      */     
/*      */     boolean hasDefaults() {
/* 2724 */       return this.defaults != 0;
/*      */     }
/*      */     
/*      */     void add(Method paramMethod) {
/* 2728 */       if (this.length == this.methods.length) {
/* 2729 */         this.methods = ((Method[])Arrays.copyOf(this.methods, 2 * this.methods.length));
/*      */       }
/* 2731 */       this.methods[(this.length++)] = paramMethod;
/*      */       
/* 2733 */       if ((paramMethod != null) && (paramMethod.isDefault()))
/* 2734 */         this.defaults += 1;
/*      */     }
/*      */     
/*      */     void addAll(Method[] paramArrayOfMethod) {
/* 2738 */       for (int i = 0; i < paramArrayOfMethod.length; i++) {
/* 2739 */         add(paramArrayOfMethod[i]);
/*      */       }
/*      */     }
/*      */     
/*      */     void addAll(MethodArray paramMethodArray) {
/* 2744 */       for (int i = 0; i < paramMethodArray.length(); i++) {
/* 2745 */         add(paramMethodArray.get(i));
/*      */       }
/*      */     }
/*      */     
/*      */     void addIfNotPresent(Method paramMethod) {
/* 2750 */       for (int i = 0; i < this.length; i++) {
/* 2751 */         Method localMethod = this.methods[i];
/* 2752 */         if ((localMethod == paramMethod) || ((localMethod != null) && (localMethod.equals(paramMethod)))) {
/* 2753 */           return;
/*      */         }
/*      */       }
/* 2756 */       add(paramMethod);
/*      */     }
/*      */     
/*      */     void addAllIfNotPresent(MethodArray paramMethodArray) {
/* 2760 */       for (int i = 0; i < paramMethodArray.length(); i++) {
/* 2761 */         Method localMethod = paramMethodArray.get(i);
/* 2762 */         if (localMethod != null) {
/* 2763 */           addIfNotPresent(localMethod);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void addInterfaceMethods(Method[] paramArrayOfMethod)
/*      */     {
/* 2772 */       for (Method localMethod : paramArrayOfMethod) {
/* 2773 */         if (!Modifier.isStatic(localMethod.getModifiers())) {
/* 2774 */           add(localMethod);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     int length() {
/* 2780 */       return this.length;
/*      */     }
/*      */     
/*      */     Method get(int paramInt) {
/* 2784 */       return this.methods[paramInt];
/*      */     }
/*      */     
/*      */     Method getFirst() {
/* 2788 */       for (Method localMethod : this.methods)
/* 2789 */         if (localMethod != null)
/* 2790 */           return localMethod;
/* 2791 */       return null;
/*      */     }
/*      */     
/*      */     void removeByNameAndDescriptor(Method paramMethod) {
/* 2795 */       for (int i = 0; i < this.length; i++) {
/* 2796 */         Method localMethod = this.methods[i];
/* 2797 */         if ((localMethod != null) && (matchesNameAndDescriptor(localMethod, paramMethod))) {
/* 2798 */           remove(i);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private void remove(int paramInt) {
/* 2804 */       if ((this.methods[paramInt] != null) && (this.methods[paramInt].isDefault()))
/* 2805 */         this.defaults -= 1;
/* 2806 */       this.methods[paramInt] = null;
/*      */     }
/*      */     
/*      */ 
/*      */     private boolean matchesNameAndDescriptor(Method paramMethod1, Method paramMethod2)
/*      */     {
/* 2812 */       return (paramMethod1.getReturnType() == paramMethod2.getReturnType()) && (paramMethod1.getName() == paramMethod2.getName()) && (Class.arrayContentsEq(paramMethod1.getParameterTypes(), paramMethod2
/* 2813 */         .getParameterTypes()));
/*      */     }
/*      */     
/*      */     void compactAndTrim() {
/* 2817 */       int i = 0;
/*      */       
/* 2819 */       for (int j = 0; j < this.length; j++) {
/* 2820 */         Method localMethod = this.methods[j];
/* 2821 */         if (localMethod != null) {
/* 2822 */           if (j != i) {
/* 2823 */             this.methods[i] = localMethod;
/*      */           }
/* 2825 */           i++;
/*      */         }
/*      */       }
/* 2828 */       if (i != this.methods.length) {
/* 2829 */         this.methods = ((Method[])Arrays.copyOf(this.methods, i));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void removeLessSpecifics()
/*      */     {
/* 2840 */       if (!hasDefaults()) {
/* 2841 */         return;
/*      */       }
/* 2843 */       for (int i = 0; i < this.length; i++) {
/* 2844 */         Method localMethod1 = get(i);
/* 2845 */         if ((localMethod1 != null) && (localMethod1.isDefault()))
/*      */         {
/*      */ 
/* 2848 */           for (int j = 0; j < this.length; j++)
/* 2849 */             if (i != j)
/*      */             {
/*      */ 
/* 2852 */               Method localMethod2 = get(j);
/* 2853 */               if (localMethod2 != null)
/*      */               {
/*      */ 
/* 2856 */                 if (matchesNameAndDescriptor(localMethod1, localMethod2))
/*      */                 {
/*      */ 
/* 2859 */                   if (hasMoreSpecificClass(localMethod1, localMethod2))
/* 2860 */                     remove(j); } }
/*      */             } }
/*      */       }
/*      */     }
/*      */     
/*      */     Method[] getArray() {
/* 2866 */       return this.methods;
/*      */     }
/*      */     
/*      */     static boolean hasMoreSpecificClass(Method paramMethod1, Method paramMethod2)
/*      */     {
/* 2871 */       Class localClass1 = paramMethod1.getDeclaringClass();
/* 2872 */       Class localClass2 = paramMethod2.getDeclaringClass();
/* 2873 */       return (localClass1 != localClass2) && (localClass2.isAssignableFrom(localClass1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Method[] privateGetPublicMethods()
/*      */   {
/* 2882 */     checkInitted();
/*      */     
/* 2884 */     ReflectionData localReflectionData = reflectionData();
/* 2885 */     if (localReflectionData != null) {
/* 2886 */       arrayOfMethod = localReflectionData.publicMethods;
/* 2887 */       if (arrayOfMethod != null) { return arrayOfMethod;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2892 */     MethodArray localMethodArray = new MethodArray();
/*      */     
/* 2894 */     Object localObject1 = privateGetDeclaredMethods(true);
/* 2895 */     localMethodArray.addAll((Method[])localObject1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2901 */     localObject1 = new MethodArray();
/* 2902 */     Method localMethod; for (localMethod : getInterfaces())
/* 2903 */       ((MethodArray)localObject1).addInterfaceMethods(localMethod.privateGetPublicMethods());
/*      */     Object localObject3;
/* 2905 */     if (!isInterface()) {
/* 2906 */       ??? = getSuperclass();
/* 2907 */       if (??? != null) {
/* 2908 */         localObject3 = new MethodArray();
/* 2909 */         ((MethodArray)localObject3).addAll(((Class)???).privateGetPublicMethods());
/*      */         
/*      */ 
/* 2912 */         for (??? = 0; ??? < ((MethodArray)localObject3).length(); ???++) {
/* 2913 */           localMethod = ((MethodArray)localObject3).get(???);
/* 2914 */           if ((localMethod != null) && 
/* 2915 */             (!Modifier.isAbstract(localMethod.getModifiers())) && 
/* 2916 */             (!localMethod.isDefault())) {
/* 2917 */             ((MethodArray)localObject1).removeByNameAndDescriptor(localMethod);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2923 */         ((MethodArray)localObject3).addAll((MethodArray)localObject1);
/* 2924 */         localObject1 = localObject3;
/*      */       }
/*      */     }
/*      */     
/* 2928 */     for (int i = 0; i < localMethodArray.length(); i++) {
/* 2929 */       localObject3 = localMethodArray.get(i);
/* 2930 */       ((MethodArray)localObject1).removeByNameAndDescriptor((Method)localObject3);
/*      */     }
/* 2932 */     localMethodArray.addAllIfNotPresent((MethodArray)localObject1);
/* 2933 */     localMethodArray.removeLessSpecifics();
/* 2934 */     localMethodArray.compactAndTrim();
/* 2935 */     Method[] arrayOfMethod = localMethodArray.getArray();
/* 2936 */     if (localReflectionData != null) {
/* 2937 */       localReflectionData.publicMethods = arrayOfMethod;
/*      */     }
/* 2939 */     return arrayOfMethod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Field searchFields(Field[] paramArrayOfField, String paramString)
/*      */   {
/* 2948 */     String str = paramString.intern();
/* 2949 */     for (int i = 0; i < paramArrayOfField.length; i++) {
/* 2950 */       if (paramArrayOfField[i].getName() == str) {
/* 2951 */         return getReflectionFactory().copyField(paramArrayOfField[i]);
/*      */       }
/*      */     }
/* 2954 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Field getField0(String paramString)
/*      */     throws NoSuchFieldException
/*      */   {
/*      */     Field localField;
/*      */     
/*      */ 
/*      */ 
/* 2967 */     if ((localField = searchFields(privateGetDeclaredFields(true), paramString)) != null) {
/* 2968 */       return localField;
/*      */     }
/*      */     
/* 2971 */     Class[] arrayOfClass = getInterfaces();
/* 2972 */     for (int i = 0; i < arrayOfClass.length; i++) {
/* 2973 */       Class localClass2 = arrayOfClass[i];
/* 2974 */       if ((localField = localClass2.getField0(paramString)) != null) {
/* 2975 */         return localField;
/*      */       }
/*      */     }
/*      */     
/* 2979 */     if (!isInterface()) {
/* 2980 */       Class localClass1 = getSuperclass();
/* 2981 */       if ((localClass1 != null) && 
/* 2982 */         ((localField = localClass1.getField0(paramString)) != null)) {
/* 2983 */         return localField;
/*      */       }
/*      */     }
/*      */     
/* 2987 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static Method searchMethods(Method[] paramArrayOfMethod, String paramString, Class<?>[] paramArrayOfClass)
/*      */   {
/* 2994 */     Object localObject = null;
/* 2995 */     String str = paramString.intern();
/* 2996 */     for (int i = 0; i < paramArrayOfMethod.length; i++) {
/* 2997 */       Method localMethod = paramArrayOfMethod[i];
/* 2998 */       if ((localMethod.getName() == str) && 
/* 2999 */         (arrayContentsEq(paramArrayOfClass, localMethod.getParameterTypes())) && ((localObject == null) || 
/*      */         
/* 3001 */         (((Method)localObject).getReturnType().isAssignableFrom(localMethod.getReturnType())))) {
/* 3002 */         localObject = localMethod;
/*      */       }
/*      */     }
/* 3005 */     return (Method)(localObject == null ? localObject : getReflectionFactory().copyMethod((Method)localObject));
/*      */   }
/*      */   
/*      */   private Method getMethod0(String paramString, Class<?>[] paramArrayOfClass, boolean paramBoolean) {
/* 3009 */     MethodArray localMethodArray = new MethodArray(2);
/* 3010 */     Method localMethod = privateGetMethodRecursive(paramString, paramArrayOfClass, paramBoolean, localMethodArray);
/* 3011 */     if (localMethod != null) {
/* 3012 */       return localMethod;
/*      */     }
/*      */     
/* 3015 */     localMethodArray.removeLessSpecifics();
/* 3016 */     return localMethodArray.getFirst();
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
/*      */   private Method privateGetMethodRecursive(String paramString, Class<?>[] paramArrayOfClass, boolean paramBoolean, MethodArray paramMethodArray)
/*      */   {
/*      */     Method localMethod;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3040 */     if ((localMethod = searchMethods(privateGetDeclaredMethods(true), paramString, paramArrayOfClass)) != null)
/*      */     {
/*      */ 
/* 3043 */       if ((paramBoolean) || (!Modifier.isStatic(localMethod.getModifiers()))) {
/* 3044 */         return localMethod;
/*      */       }
/*      */     }
/* 3047 */     if (!isInterface()) {
/* 3048 */       localObject1 = getSuperclass();
/* 3049 */       if ((localObject1 != null) && 
/* 3050 */         ((localMethod = ((Class)localObject1).getMethod0(paramString, paramArrayOfClass, true)) != null)) {
/* 3051 */         return localMethod;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3056 */     Object localObject1 = getInterfaces();
/* 3057 */     for (Object localObject3 : localObject1) {
/* 3058 */       if ((localMethod = ((Class)localObject3).getMethod0(paramString, paramArrayOfClass, false)) != null)
/* 3059 */         paramMethodArray.add(localMethod);
/*      */     }
/* 3061 */     return null;
/*      */   }
/*      */   
/*      */   private Constructor<T> getConstructor0(Class<?>[] paramArrayOfClass, int paramInt)
/*      */     throws NoSuchMethodException
/*      */   {
/* 3067 */     Constructor[] arrayOfConstructor1 = privateGetDeclaredConstructors(paramInt == 0);
/* 3068 */     for (Constructor localConstructor : arrayOfConstructor1) {
/* 3069 */       if (arrayContentsEq(paramArrayOfClass, localConstructor
/* 3070 */         .getParameterTypes())) {
/* 3071 */         return getReflectionFactory().copyConstructor(localConstructor);
/*      */       }
/*      */     }
/* 3074 */     throw new NoSuchMethodException(getName() + ".<init>" + argumentTypesToString(paramArrayOfClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean arrayContentsEq(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
/*      */   {
/* 3082 */     if (paramArrayOfObject1 == null) {
/* 3083 */       return (paramArrayOfObject2 == null) || (paramArrayOfObject2.length == 0);
/*      */     }
/*      */     
/* 3086 */     if (paramArrayOfObject2 == null) {
/* 3087 */       return paramArrayOfObject1.length == 0;
/*      */     }
/*      */     
/* 3090 */     if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
/* 3091 */       return false;
/*      */     }
/*      */     
/* 3094 */     for (int i = 0; i < paramArrayOfObject1.length; i++) {
/* 3095 */       if (paramArrayOfObject1[i] != paramArrayOfObject2[i]) {
/* 3096 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 3100 */     return true;
/*      */   }
/*      */   
/*      */   private static Field[] copyFields(Field[] paramArrayOfField) {
/* 3104 */     Field[] arrayOfField = new Field[paramArrayOfField.length];
/* 3105 */     ReflectionFactory localReflectionFactory = getReflectionFactory();
/* 3106 */     for (int i = 0; i < paramArrayOfField.length; i++) {
/* 3107 */       arrayOfField[i] = localReflectionFactory.copyField(paramArrayOfField[i]);
/*      */     }
/* 3109 */     return arrayOfField;
/*      */   }
/*      */   
/*      */   private static Method[] copyMethods(Method[] paramArrayOfMethod) {
/* 3113 */     Method[] arrayOfMethod = new Method[paramArrayOfMethod.length];
/* 3114 */     ReflectionFactory localReflectionFactory = getReflectionFactory();
/* 3115 */     for (int i = 0; i < paramArrayOfMethod.length; i++) {
/* 3116 */       arrayOfMethod[i] = localReflectionFactory.copyMethod(paramArrayOfMethod[i]);
/*      */     }
/* 3118 */     return arrayOfMethod;
/*      */   }
/*      */   
/*      */   private static <U> Constructor<U>[] copyConstructors(Constructor<U>[] paramArrayOfConstructor) {
/* 3122 */     Constructor[] arrayOfConstructor = (Constructor[])paramArrayOfConstructor.clone();
/* 3123 */     ReflectionFactory localReflectionFactory = getReflectionFactory();
/* 3124 */     for (int i = 0; i < arrayOfConstructor.length; i++) {
/* 3125 */       arrayOfConstructor[i] = localReflectionFactory.copyConstructor(arrayOfConstructor[i]);
/*      */     }
/* 3127 */     return arrayOfConstructor; }
/*      */   
/*      */   private native Field[] getDeclaredFields0(boolean paramBoolean);
/*      */   
/*      */   private native Method[] getDeclaredMethods0(boolean paramBoolean);
/*      */   
/*      */   private native Constructor<T>[] getDeclaredConstructors0(boolean paramBoolean);
/*      */   
/*      */   private native Class<?>[] getDeclaredClasses0();
/* 3136 */   private static String argumentTypesToString(Class<?>[] paramArrayOfClass) { StringBuilder localStringBuilder = new StringBuilder();
/* 3137 */     localStringBuilder.append("(");
/* 3138 */     if (paramArrayOfClass != null) {
/* 3139 */       for (int i = 0; i < paramArrayOfClass.length; i++) {
/* 3140 */         if (i > 0) {
/* 3141 */           localStringBuilder.append(", ");
/*      */         }
/* 3143 */         Class<?> localClass = paramArrayOfClass[i];
/* 3144 */         localStringBuilder.append(localClass == null ? "null" : localClass.getName());
/*      */       }
/*      */     }
/* 3147 */     localStringBuilder.append(")");
/* 3148 */     return localStringBuilder.toString();
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
/* 3171 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ReflectionFactory reflectionFactory;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean desiredAssertionStatus()
/*      */   {
/* 3200 */     ClassLoader localClassLoader = getClassLoader();
/*      */     
/* 3202 */     if (localClassLoader == null) {
/* 3203 */       return desiredAssertionStatus0(this);
/*      */     }
/*      */     
/*      */ 
/* 3207 */     synchronized (localClassLoader.assertionLock) {
/* 3208 */       if (localClassLoader.classAssertionStatus != null) {
/* 3209 */         return localClassLoader.desiredAssertionStatus(getName());
/*      */       }
/*      */     }
/* 3212 */     return desiredAssertionStatus0(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static native boolean desiredAssertionStatus0(Class<?> paramClass);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnum()
/*      */   {
/* 3231 */     return ((getModifiers() & 0x4000) != 0) && (getSuperclass() == Enum.class);
/*      */   }
/*      */   
/*      */   private static ReflectionFactory getReflectionFactory()
/*      */   {
/* 3236 */     if (reflectionFactory == null)
/*      */     {
/*      */ 
/* 3239 */       reflectionFactory = (ReflectionFactory)AccessController.doPrivileged(new ReflectionFactory.GetReflectionFactoryAction());
/*      */     }
/* 3241 */     return reflectionFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 3246 */   private static boolean initted = false;
/*      */   
/* 3248 */   private static void checkInitted() { if (initted) return;
/* 3249 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       public Void run()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 3260 */         if (System.out == null)
/*      */         {
/* 3262 */           return null;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3267 */         String str = System.getProperty("sun.reflect.noCaches");
/* 3268 */         if ((str != null) && (str.equals("true"))) {
/* 3269 */           Class.access$402(false);
/*      */         }
/*      */         
/* 3272 */         Class.access$502(true);
/* 3273 */         return null;
/*      */       }
/*      */     });
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
/*      */   public T[] getEnumConstants()
/*      */   {
/* 3289 */     Object[] arrayOfObject = getEnumConstantsShared();
/* 3290 */     return arrayOfObject != null ? (Object[])arrayOfObject.clone() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   T[] getEnumConstantsShared()
/*      */   {
/* 3300 */     if (this.enumConstants == null) {
/* 3301 */       if (!isEnum()) return null;
/*      */       try {
/* 3303 */         final Method localMethod = getMethod("values", new Class[0]);
/* 3304 */         AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Void run() {
/* 3307 */             localMethod.setAccessible(true);
/* 3308 */             return null;
/*      */           }
/*      */           
/* 3311 */         });
/* 3312 */         Object[] arrayOfObject = (Object[])localMethod.invoke(null, new Object[0]);
/* 3313 */         this.enumConstants = arrayOfObject;
/*      */ 
/*      */       }
/*      */       catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException localInvocationTargetException)
/*      */       {
/* 3318 */         return null;
/*      */       } }
/* 3320 */     return this.enumConstants; }
/*      */   
/* 3322 */   private volatile transient T[] enumConstants = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Map<String, T> enumConstantDirectory()
/*      */   {
/* 3332 */     if (this.enumConstantDirectory == null) {
/* 3333 */       Object[] arrayOfObject1 = getEnumConstantsShared();
/* 3334 */       if (arrayOfObject1 == null)
/*      */       {
/* 3336 */         throw new IllegalArgumentException(getName() + " is not an enum type"); }
/* 3337 */       HashMap localHashMap = new HashMap(2 * arrayOfObject1.length);
/* 3338 */       for (Object localObject : arrayOfObject1)
/* 3339 */         localHashMap.put(((Enum)localObject).name(), localObject);
/* 3340 */       this.enumConstantDirectory = localHashMap;
/*      */     }
/* 3342 */     return this.enumConstantDirectory; }
/*      */   
/* 3344 */   private volatile transient Map<String, T> enumConstantDirectory = null;
/*      */   
/*      */ 
/*      */ 
/*      */   private volatile transient AnnotationData annotationData;
/*      */   
/*      */ 
/*      */   private volatile transient AnnotationType annotationType;
/*      */   
/*      */ 
/*      */   transient ClassValue.ClassValueMap classValueMap;
/*      */   
/*      */ 
/*      */ 
/*      */   public T cast(Object paramObject)
/*      */   {
/* 3360 */     if ((paramObject != null) && (!isInstance(paramObject)))
/* 3361 */       throw new ClassCastException(cannotCastMsg(paramObject));
/* 3362 */     return (T)paramObject;
/*      */   }
/*      */   
/*      */   private String cannotCastMsg(Object paramObject) {
/* 3366 */     return "Cannot cast " + paramObject.getClass().getName() + " to " + getName();
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
/*      */   public <U> Class<? extends U> asSubclass(Class<U> paramClass)
/*      */   {
/* 3393 */     if (paramClass.isAssignableFrom(this)) {
/* 3394 */       return this;
/*      */     }
/* 3396 */     throw new ClassCastException(toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*      */   {
/* 3405 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3407 */     return (Annotation)annotationData().annotations.get(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAnnotationPresent(Class<? extends Annotation> paramClass)
/*      */   {
/* 3417 */     return super.isAnnotationPresent(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <A extends Annotation> A[] getAnnotationsByType(Class<A> paramClass)
/*      */   {
/* 3426 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3428 */     AnnotationData localAnnotationData = annotationData();
/* 3429 */     return AnnotationSupport.getAssociatedAnnotations(localAnnotationData.declaredAnnotations, this, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Annotation[] getAnnotations()
/*      */   {
/* 3438 */     return AnnotationParser.toArray(annotationData().annotations);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <A extends Annotation> A getDeclaredAnnotation(Class<A> paramClass)
/*      */   {
/* 3448 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3450 */     return (Annotation)annotationData().declaredAnnotations.get(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> paramClass)
/*      */   {
/* 3459 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3461 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(annotationData().declaredAnnotations, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Annotation[] getDeclaredAnnotations()
/*      */   {
/* 3469 */     return AnnotationParser.toArray(annotationData().declaredAnnotations);
/*      */   }
/*      */   
/*      */ 
/*      */   private static class AnnotationData
/*      */   {
/*      */     final Map<Class<? extends Annotation>, Annotation> annotations;
/*      */     
/*      */     final Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*      */     
/*      */     final int redefinedCount;
/*      */     
/*      */     AnnotationData(Map<Class<? extends Annotation>, Annotation> paramMap1, Map<Class<? extends Annotation>, Annotation> paramMap2, int paramInt)
/*      */     {
/* 3483 */       this.annotations = paramMap1;
/* 3484 */       this.declaredAnnotations = paramMap2;
/* 3485 */       this.redefinedCount = paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private AnnotationData annotationData()
/*      */   {
/*      */     for (;;)
/*      */     {
/* 3495 */       AnnotationData localAnnotationData1 = this.annotationData;
/* 3496 */       int i = this.classRedefinedCount;
/* 3497 */       if ((localAnnotationData1 != null) && (localAnnotationData1.redefinedCount == i))
/*      */       {
/* 3499 */         return localAnnotationData1;
/*      */       }
/*      */       
/* 3502 */       AnnotationData localAnnotationData2 = createAnnotationData(i);
/*      */       
/* 3504 */       if (Atomic.casAnnotationData(this, localAnnotationData1, localAnnotationData2))
/*      */       {
/* 3506 */         return localAnnotationData2;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private AnnotationData createAnnotationData(int paramInt)
/*      */   {
/* 3513 */     Map localMap1 = AnnotationParser.parseAnnotations(getRawAnnotations(), getConstantPool(), this);
/* 3514 */     Class localClass1 = getSuperclass();
/* 3515 */     Object localObject = null;
/* 3516 */     Map localMap2; if (localClass1 != null)
/*      */     {
/* 3518 */       localMap2 = localClass1.annotationData().annotations;
/* 3519 */       for (Map.Entry localEntry : localMap2.entrySet()) {
/* 3520 */         Class localClass2 = (Class)localEntry.getKey();
/* 3521 */         if (AnnotationType.getInstance(localClass2).isInherited()) {
/* 3522 */           if (localObject == null) {
/* 3523 */             localObject = new LinkedHashMap((Math.max(localMap1
/* 3524 */               .size(), 
/* 3525 */               Math.min(12, localMap1.size() + localMap2.size())) * 4 + 2) / 3);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 3529 */           ((Map)localObject).put(localClass2, localEntry.getValue());
/*      */         }
/*      */       }
/*      */     }
/* 3533 */     if (localObject == null)
/*      */     {
/* 3535 */       localObject = localMap1;
/*      */     }
/*      */     else {
/* 3538 */       ((Map)localObject).putAll(localMap1);
/*      */     }
/* 3540 */     return new AnnotationData((Map)localObject, localMap1, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean casAnnotationType(AnnotationType paramAnnotationType1, AnnotationType paramAnnotationType2)
/*      */   {
/* 3549 */     return Atomic.casAnnotationType(this, paramAnnotationType1, paramAnnotationType2);
/*      */   }
/*      */   
/*      */   AnnotationType getAnnotationType() {
/* 3553 */     return this.annotationType;
/*      */   }
/*      */   
/*      */   Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap() {
/* 3557 */     return annotationData().declaredAnnotations;
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
/*      */   public AnnotatedType getAnnotatedSuperclass()
/*      */   {
/* 3585 */     if ((this == Object.class) || 
/* 3586 */       (isInterface()) || 
/* 3587 */       (isArray()) || 
/* 3588 */       (isPrimitive()) || (this == Void.TYPE))
/*      */     {
/* 3590 */       return null;
/*      */     }
/*      */     
/* 3593 */     return TypeAnnotationParser.buildAnnotatedSuperclass(getRawTypeAnnotations(), getConstantPool(), this);
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
/*      */   public AnnotatedType[] getAnnotatedInterfaces()
/*      */   {
/* 3628 */     return TypeAnnotationParser.buildAnnotatedInterfaces(getRawTypeAnnotations(), getConstantPool(), this);
/*      */   }
/*      */   
/*      */   static {}
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Class.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */