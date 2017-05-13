/*     */ package java.beans;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NameGenerator
/*     */ {
/*     */   private Map<Object, String> valueToName;
/*     */   private Map<String, Integer> nameToCount;
/*     */   
/*     */   public NameGenerator()
/*     */   {
/*  50 */     this.valueToName = new IdentityHashMap();
/*  51 */     this.nameToCount = new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/*  59 */     this.valueToName.clear();
/*  60 */     this.nameToCount.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String unqualifiedClassName(Class paramClass)
/*     */   {
/*  68 */     if (paramClass.isArray()) {
/*  69 */       return unqualifiedClassName(paramClass.getComponentType()) + "Array";
/*     */     }
/*  71 */     String str = paramClass.getName();
/*  72 */     return str.substring(str.lastIndexOf('.') + 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String capitalize(String paramString)
/*     */   {
/*  79 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  80 */       return paramString;
/*     */     }
/*  82 */     return paramString.substring(0, 1).toUpperCase(Locale.ENGLISH) + paramString.substring(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String instanceName(Object paramObject)
/*     */   {
/*  94 */     if (paramObject == null) {
/*  95 */       return "null";
/*     */     }
/*  97 */     if ((paramObject instanceof Class)) {
/*  98 */       return unqualifiedClassName((Class)paramObject);
/*     */     }
/*     */     
/* 101 */     String str1 = (String)this.valueToName.get(paramObject);
/* 102 */     if (str1 != null) {
/* 103 */       return str1;
/*     */     }
/* 105 */     Class localClass = paramObject.getClass();
/* 106 */     String str2 = unqualifiedClassName(localClass);
/*     */     
/* 108 */     Integer localInteger = (Integer)this.nameToCount.get(str2);
/* 109 */     int i = localInteger == null ? 0 : localInteger.intValue() + 1;
/* 110 */     this.nameToCount.put(str2, new Integer(i));
/*     */     
/* 112 */     str1 = str2 + i;
/* 113 */     this.valueToName.put(paramObject, str1);
/* 114 */     return str1;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/NameGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */