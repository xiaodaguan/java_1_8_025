/*      */ package java.lang.reflect;
/*      */ 
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import sun.misc.JavaLangAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.FieldAccessor;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.ReflectionFactory;
/*      */ import sun.reflect.annotation.AnnotationParser;
/*      */ import sun.reflect.annotation.AnnotationSupport;
/*      */ import sun.reflect.annotation.TypeAnnotation.TypeAnnotationTarget;
/*      */ import sun.reflect.annotation.TypeAnnotationParser;
/*      */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*      */ import sun.reflect.generics.factory.GenericsFactory;
/*      */ import sun.reflect.generics.repository.FieldRepository;
/*      */ import sun.reflect.generics.scope.ClassScope;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Field
/*      */   extends AccessibleObject
/*      */   implements Member
/*      */ {
/*      */   private Class<?> clazz;
/*      */   private int slot;
/*      */   private String name;
/*      */   private Class<?> type;
/*      */   private int modifiers;
/*      */   private transient String signature;
/*      */   private transient FieldRepository genericInfo;
/*      */   private byte[] annotations;
/*      */   private FieldAccessor fieldAccessor;
/*      */   private FieldAccessor overrideFieldAccessor;
/*      */   private Field root;
/*      */   private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*      */   
/*      */   private String getGenericSignature()
/*      */   {
/*   88 */     return this.signature;
/*      */   }
/*      */   
/*      */   private GenericsFactory getFactory() {
/*   92 */     Class localClass = getDeclaringClass();
/*      */     
/*   94 */     return CoreReflectionFactory.make(localClass, ClassScope.make(localClass));
/*      */   }
/*      */   
/*      */ 
/*      */   private FieldRepository getGenericInfo()
/*      */   {
/*  100 */     if (this.genericInfo == null)
/*      */     {
/*  102 */       this.genericInfo = FieldRepository.make(getGenericSignature(), 
/*  103 */         getFactory());
/*      */     }
/*  105 */     return this.genericInfo;
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
/*      */   Field(Class<?> paramClass1, String paramString1, Class<?> paramClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfByte)
/*      */   {
/*  122 */     this.clazz = paramClass1;
/*  123 */     this.name = paramString1;
/*  124 */     this.type = paramClass2;
/*  125 */     this.modifiers = paramInt1;
/*  126 */     this.slot = paramInt2;
/*  127 */     this.signature = paramString2;
/*  128 */     this.annotations = paramArrayOfByte;
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
/*      */   Field copy()
/*      */   {
/*  144 */     Field localField = new Field(this.clazz, this.name, this.type, this.modifiers, this.slot, this.signature, this.annotations);
/*  145 */     localField.root = this;
/*      */     
/*  147 */     localField.fieldAccessor = this.fieldAccessor;
/*  148 */     localField.overrideFieldAccessor = this.overrideFieldAccessor;
/*      */     
/*  150 */     return localField;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> getDeclaringClass()
/*      */   {
/*  158 */     return this.clazz;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  165 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getModifiers()
/*      */   {
/*  176 */     return this.modifiers;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnumConstant()
/*      */   {
/*  188 */     return (getModifiers() & 0x4000) != 0;
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
/*  200 */     return Modifier.isSynthetic(getModifiers());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> getType()
/*      */   {
/*  212 */     return this.type;
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
/*      */   public Type getGenericType()
/*      */   {
/*  240 */     if (getGenericSignature() != null) {
/*  241 */       return getGenericInfo().getGenericType();
/*      */     }
/*  243 */     return getType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  254 */     if ((paramObject != null) && ((paramObject instanceof Field))) {
/*  255 */       Field localField = (Field)paramObject;
/*      */       
/*      */ 
/*  258 */       return (getDeclaringClass() == localField.getDeclaringClass()) && (getName() == localField.getName()) && (getType() == localField.getType());
/*      */     }
/*  260 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  269 */     return getDeclaringClass().getName().hashCode() ^ getName().hashCode();
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
/*      */   public String toString()
/*      */   {
/*  294 */     int i = getModifiers();
/*      */     
/*      */ 
/*      */ 
/*  298 */     return (i == 0 ? "" : new StringBuilder().append(Modifier.toString(i)).append(" ").toString()) + getType().getTypeName() + " " + getDeclaringClass().getTypeName() + "." + getName();
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
/*      */   public String toGenericString()
/*      */   {
/*  322 */     int i = getModifiers();
/*  323 */     Type localType = getGenericType();
/*      */     
/*      */ 
/*      */ 
/*  327 */     return (i == 0 ? "" : new StringBuilder().append(Modifier.toString(i)).append(" ").toString()) + localType.getTypeName() + " " + getDeclaringClass().getTypeName() + "." + getName();
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
/*      */   @CallerSensitive
/*      */   public Object get(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  381 */     if ((!this.override) && 
/*  382 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  383 */       Class localClass = Reflection.getCallerClass();
/*  384 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  387 */     return getFieldAccessor(paramObject).get(paramObject);
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
/*      */   public boolean getBoolean(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  416 */     if ((!this.override) && 
/*  417 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  418 */       Class localClass = Reflection.getCallerClass();
/*  419 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  422 */     return getFieldAccessor(paramObject).getBoolean(paramObject);
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
/*      */   public byte getByte(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  451 */     if ((!this.override) && 
/*  452 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  453 */       Class localClass = Reflection.getCallerClass();
/*  454 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  457 */     return getFieldAccessor(paramObject).getByte(paramObject);
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
/*      */   @CallerSensitive
/*      */   public char getChar(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  488 */     if ((!this.override) && 
/*  489 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  490 */       Class localClass = Reflection.getCallerClass();
/*  491 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  494 */     return getFieldAccessor(paramObject).getChar(paramObject);
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
/*      */   @CallerSensitive
/*      */   public short getShort(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  525 */     if ((!this.override) && 
/*  526 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  527 */       Class localClass = Reflection.getCallerClass();
/*  528 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  531 */     return getFieldAccessor(paramObject).getShort(paramObject);
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
/*      */   @CallerSensitive
/*      */   public int getInt(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  562 */     if ((!this.override) && 
/*  563 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  564 */       Class localClass = Reflection.getCallerClass();
/*  565 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  568 */     return getFieldAccessor(paramObject).getInt(paramObject);
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
/*      */   @CallerSensitive
/*      */   public long getLong(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  599 */     if ((!this.override) && 
/*  600 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  601 */       Class localClass = Reflection.getCallerClass();
/*  602 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  605 */     return getFieldAccessor(paramObject).getLong(paramObject);
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
/*      */   @CallerSensitive
/*      */   public float getFloat(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  636 */     if ((!this.override) && 
/*  637 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  638 */       Class localClass = Reflection.getCallerClass();
/*  639 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  642 */     return getFieldAccessor(paramObject).getFloat(paramObject);
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
/*      */   @CallerSensitive
/*      */   public double getDouble(Object paramObject)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  673 */     if ((!this.override) && 
/*  674 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  675 */       Class localClass = Reflection.getCallerClass();
/*  676 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  679 */     return getFieldAccessor(paramObject).getDouble(paramObject);
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
/*      */   @CallerSensitive
/*      */   public void set(Object paramObject1, Object paramObject2)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  752 */     if ((!this.override) && 
/*  753 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  754 */       Class localClass = Reflection.getCallerClass();
/*  755 */       checkAccess(localClass, this.clazz, paramObject1, this.modifiers);
/*      */     }
/*      */     
/*  758 */     getFieldAccessor(paramObject1).set(paramObject1, paramObject2);
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
/*      */   @CallerSensitive
/*      */   public void setBoolean(Object paramObject, boolean paramBoolean)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  789 */     if ((!this.override) && 
/*  790 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  791 */       Class localClass = Reflection.getCallerClass();
/*  792 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  795 */     getFieldAccessor(paramObject).setBoolean(paramObject, paramBoolean);
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
/*      */   @CallerSensitive
/*      */   public void setByte(Object paramObject, byte paramByte)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  826 */     if ((!this.override) && 
/*  827 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  828 */       Class localClass = Reflection.getCallerClass();
/*  829 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  832 */     getFieldAccessor(paramObject).setByte(paramObject, paramByte);
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
/*      */   @CallerSensitive
/*      */   public void setChar(Object paramObject, char paramChar)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  863 */     if ((!this.override) && 
/*  864 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  865 */       Class localClass = Reflection.getCallerClass();
/*  866 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  869 */     getFieldAccessor(paramObject).setChar(paramObject, paramChar);
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
/*      */   @CallerSensitive
/*      */   public void setShort(Object paramObject, short paramShort)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  900 */     if ((!this.override) && 
/*  901 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  902 */       Class localClass = Reflection.getCallerClass();
/*  903 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  906 */     getFieldAccessor(paramObject).setShort(paramObject, paramShort);
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
/*      */   @CallerSensitive
/*      */   public void setInt(Object paramObject, int paramInt)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  937 */     if ((!this.override) && 
/*  938 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  939 */       Class localClass = Reflection.getCallerClass();
/*  940 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  943 */     getFieldAccessor(paramObject).setInt(paramObject, paramInt);
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
/*      */   @CallerSensitive
/*      */   public void setLong(Object paramObject, long paramLong)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/*  974 */     if ((!this.override) && 
/*  975 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/*  976 */       Class localClass = Reflection.getCallerClass();
/*  977 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/*  980 */     getFieldAccessor(paramObject).setLong(paramObject, paramLong);
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
/*      */   @CallerSensitive
/*      */   public void setFloat(Object paramObject, float paramFloat)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/* 1011 */     if ((!this.override) && 
/* 1012 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/* 1013 */       Class localClass = Reflection.getCallerClass();
/* 1014 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/* 1017 */     getFieldAccessor(paramObject).setFloat(paramObject, paramFloat);
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
/*      */   @CallerSensitive
/*      */   public void setDouble(Object paramObject, double paramDouble)
/*      */     throws IllegalArgumentException, IllegalAccessException
/*      */   {
/* 1048 */     if ((!this.override) && 
/* 1049 */       (!Reflection.quickCheckMemberAccess(this.clazz, this.modifiers))) {
/* 1050 */       Class localClass = Reflection.getCallerClass();
/* 1051 */       checkAccess(localClass, this.clazz, paramObject, this.modifiers);
/*      */     }
/*      */     
/* 1054 */     getFieldAccessor(paramObject).setDouble(paramObject, paramDouble);
/*      */   }
/*      */   
/*      */ 
/*      */   private FieldAccessor getFieldAccessor(Object paramObject)
/*      */     throws IllegalAccessException
/*      */   {
/* 1061 */     boolean bool = this.override;
/* 1062 */     FieldAccessor localFieldAccessor = bool ? this.overrideFieldAccessor : this.fieldAccessor;
/* 1063 */     return localFieldAccessor != null ? localFieldAccessor : acquireFieldAccessor(bool);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private FieldAccessor acquireFieldAccessor(boolean paramBoolean)
/*      */   {
/* 1073 */     FieldAccessor localFieldAccessor = null;
/* 1074 */     if (this.root != null) localFieldAccessor = this.root.getFieldAccessor(paramBoolean);
/* 1075 */     if (localFieldAccessor != null) {
/* 1076 */       if (paramBoolean) {
/* 1077 */         this.overrideFieldAccessor = localFieldAccessor;
/*      */       } else {
/* 1079 */         this.fieldAccessor = localFieldAccessor;
/*      */       }
/*      */     } else {
/* 1082 */       localFieldAccessor = reflectionFactory.newFieldAccessor(this, paramBoolean);
/* 1083 */       setFieldAccessor(localFieldAccessor, paramBoolean);
/*      */     }
/*      */     
/* 1086 */     return localFieldAccessor;
/*      */   }
/*      */   
/*      */ 
/*      */   private FieldAccessor getFieldAccessor(boolean paramBoolean)
/*      */   {
/* 1092 */     return paramBoolean ? this.overrideFieldAccessor : this.fieldAccessor;
/*      */   }
/*      */   
/*      */ 
/*      */   private void setFieldAccessor(FieldAccessor paramFieldAccessor, boolean paramBoolean)
/*      */   {
/* 1098 */     if (paramBoolean) {
/* 1099 */       this.overrideFieldAccessor = paramFieldAccessor;
/*      */     } else {
/* 1101 */       this.fieldAccessor = paramFieldAccessor;
/*      */     }
/* 1103 */     if (this.root != null) {
/* 1104 */       this.root.setFieldAccessor(paramFieldAccessor, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends Annotation> T getAnnotation(Class<T> paramClass)
/*      */   {
/* 1113 */     Objects.requireNonNull(paramClass);
/* 1114 */     return (Annotation)paramClass.cast(declaredAnnotations().get(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass)
/*      */   {
/* 1124 */     Objects.requireNonNull(paramClass);
/*      */     
/* 1126 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Annotation[] getDeclaredAnnotations()
/*      */   {
/* 1133 */     return AnnotationParser.toArray(declaredAnnotations());
/*      */   }
/*      */   
/*      */ 
/*      */   private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations()
/*      */   {
/* 1139 */     if (this.declaredAnnotations == null) {
/* 1140 */       this.declaredAnnotations = AnnotationParser.parseAnnotations(this.annotations, 
/* 1141 */         SharedSecrets.getJavaLangAccess()
/* 1142 */         .getConstantPool(getDeclaringClass()), 
/* 1143 */         getDeclaringClass());
/*      */     }
/* 1145 */     return this.declaredAnnotations;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native byte[] getTypeAnnotationBytes0();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public AnnotatedType getAnnotatedType()
/*      */   {
/* 1159 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 1160 */       SharedSecrets.getJavaLangAccess()
/* 1161 */       .getConstantPool(getDeclaringClass()), this, 
/*      */       
/* 1163 */       getDeclaringClass(), 
/* 1164 */       getGenericType(), TypeAnnotation.TypeAnnotationTarget.FIELD);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/reflect/Field.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */