/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodDescriptor
/*     */   extends FeatureDescriptor
/*     */ {
/*  41 */   private final MethodRef methodRef = new MethodRef();
/*     */   
/*     */ 
/*     */   private String[] paramNames;
/*     */   
/*     */ 
/*     */   private List<WeakReference<Class<?>>> params;
/*     */   
/*     */ 
/*     */   private ParameterDescriptor[] parameterDescriptors;
/*     */   
/*     */ 
/*     */ 
/*     */   public MethodDescriptor(Method paramMethod)
/*     */   {
/*  56 */     this(paramMethod, null);
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
/*     */   public MethodDescriptor(Method paramMethod, ParameterDescriptor[] paramArrayOfParameterDescriptor)
/*     */   {
/*  71 */     setName(paramMethod.getName());
/*  72 */     setMethod(paramMethod);
/*     */     
/*  74 */     this.parameterDescriptors = (paramArrayOfParameterDescriptor != null ? (ParameterDescriptor[])paramArrayOfParameterDescriptor.clone() : null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Method getMethod()
/*     */   {
/*  84 */     Method localMethod = this.methodRef.get();
/*  85 */     if (localMethod == null) {
/*  86 */       Class localClass = getClass0();
/*  87 */       String str = getName();
/*  88 */       if ((localClass != null) && (str != null)) {
/*  89 */         Class[] arrayOfClass = getParams();
/*  90 */         if (arrayOfClass == null) {
/*  91 */           for (int i = 0; i < 3; i++)
/*     */           {
/*     */ 
/*     */ 
/*  95 */             localMethod = Introspector.findMethod(localClass, str, i, null);
/*  96 */             if (localMethod != null) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         } else {
/* 101 */           localMethod = Introspector.findMethod(localClass, str, arrayOfClass.length, arrayOfClass);
/*     */         }
/* 103 */         setMethod(localMethod);
/*     */       }
/*     */     }
/* 106 */     return localMethod;
/*     */   }
/*     */   
/*     */   private synchronized void setMethod(Method paramMethod) {
/* 110 */     if (paramMethod == null) {
/* 111 */       return;
/*     */     }
/* 113 */     if (getClass0() == null) {
/* 114 */       setClass0(paramMethod.getDeclaringClass());
/*     */     }
/* 116 */     setParams(getParameterTypes(getClass0(), paramMethod));
/* 117 */     this.methodRef.set(paramMethod);
/*     */   }
/*     */   
/*     */   private synchronized void setParams(Class<?>[] paramArrayOfClass) {
/* 121 */     if (paramArrayOfClass == null) {
/* 122 */       return;
/*     */     }
/* 124 */     this.paramNames = new String[paramArrayOfClass.length];
/* 125 */     this.params = new ArrayList(paramArrayOfClass.length);
/* 126 */     for (int i = 0; i < paramArrayOfClass.length; i++) {
/* 127 */       this.paramNames[i] = paramArrayOfClass[i].getName();
/* 128 */       this.params.add(new WeakReference(paramArrayOfClass[i]));
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getParamNames()
/*     */   {
/* 134 */     return this.paramNames;
/*     */   }
/*     */   
/*     */   private synchronized Class<?>[] getParams() {
/* 138 */     Class[] arrayOfClass = new Class[this.params.size()];
/*     */     
/* 140 */     for (int i = 0; i < this.params.size(); i++) {
/* 141 */       Reference localReference = (Reference)this.params.get(i);
/* 142 */       Class localClass = (Class)localReference.get();
/* 143 */       if (localClass == null) {
/* 144 */         return null;
/*     */       }
/* 146 */       arrayOfClass[i] = localClass;
/*     */     }
/*     */     
/* 149 */     return arrayOfClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ParameterDescriptor[] getParameterDescriptors()
/*     */   {
/* 161 */     return this.parameterDescriptors != null ? (ParameterDescriptor[])this.parameterDescriptors.clone() : null;
/*     */   }
/*     */   
/*     */   private static Method resolve(Method paramMethod1, Method paramMethod2)
/*     */   {
/* 166 */     if (paramMethod1 == null) {
/* 167 */       return paramMethod2;
/*     */     }
/* 169 */     if (paramMethod2 == null) {
/* 170 */       return paramMethod1;
/*     */     }
/* 172 */     return (!paramMethod1.isSynthetic()) && (paramMethod2.isSynthetic()) ? paramMethod1 : paramMethod2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   MethodDescriptor(MethodDescriptor paramMethodDescriptor1, MethodDescriptor paramMethodDescriptor2)
/*     */   {
/* 184 */     super(paramMethodDescriptor1, paramMethodDescriptor2);
/*     */     
/* 186 */     this.methodRef.set(resolve(paramMethodDescriptor1.methodRef.get(), paramMethodDescriptor2.methodRef.get()));
/* 187 */     this.params = paramMethodDescriptor1.params;
/* 188 */     if (paramMethodDescriptor2.params != null) {
/* 189 */       this.params = paramMethodDescriptor2.params;
/*     */     }
/* 191 */     this.paramNames = paramMethodDescriptor1.paramNames;
/* 192 */     if (paramMethodDescriptor2.paramNames != null) {
/* 193 */       this.paramNames = paramMethodDescriptor2.paramNames;
/*     */     }
/*     */     
/* 196 */     this.parameterDescriptors = paramMethodDescriptor1.parameterDescriptors;
/* 197 */     if (paramMethodDescriptor2.parameterDescriptors != null) {
/* 198 */       this.parameterDescriptors = paramMethodDescriptor2.parameterDescriptors;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   MethodDescriptor(MethodDescriptor paramMethodDescriptor)
/*     */   {
/* 207 */     super(paramMethodDescriptor);
/*     */     
/* 209 */     this.methodRef.set(paramMethodDescriptor.getMethod());
/* 210 */     this.params = paramMethodDescriptor.params;
/* 211 */     this.paramNames = paramMethodDescriptor.paramNames;
/*     */     
/* 213 */     if (paramMethodDescriptor.parameterDescriptors != null) {
/* 214 */       int i = paramMethodDescriptor.parameterDescriptors.length;
/* 215 */       this.parameterDescriptors = new ParameterDescriptor[i];
/* 216 */       for (int j = 0; j < i; j++) {
/* 217 */         this.parameterDescriptors[j] = new ParameterDescriptor(paramMethodDescriptor.parameterDescriptors[j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 223 */     appendTo(paramStringBuilder, "method", this.methodRef.get());
/* 224 */     if (this.parameterDescriptors != null) {
/* 225 */       paramStringBuilder.append("; parameterDescriptors={");
/* 226 */       for (ParameterDescriptor localParameterDescriptor : this.parameterDescriptors) {
/* 227 */         paramStringBuilder.append(localParameterDescriptor).append(", ");
/*     */       }
/* 229 */       paramStringBuilder.setLength(paramStringBuilder.length() - 2);
/* 230 */       paramStringBuilder.append("}");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/MethodDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */