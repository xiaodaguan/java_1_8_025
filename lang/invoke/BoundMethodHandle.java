/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import sun.invoke.util.ValueConversions;
/*     */ import sun.invoke.util.Wrapper;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BoundMethodHandle
/*     */   extends MethodHandle
/*     */ {
/*     */   static final String EXTENSION_TYPES = "LIJFD";
/*     */   static final byte INDEX_L = 0;
/*     */   static final byte INDEX_I = 1;
/*     */   static final byte INDEX_J = 2;
/*     */   static final byte INDEX_F = 3;
/*     */   static final byte INDEX_D = 4;
/*     */   
/*     */   BoundMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm)
/*     */   {
/*  57 */     super(paramMethodType, paramLambdaForm);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static MethodHandle bindSingle(MethodType paramMethodType, LambdaForm paramLambdaForm, char paramChar, Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/*  67 */       switch (paramChar) {
/*     */       case 'L': 
/*  69 */         return bindSingle(paramMethodType, paramLambdaForm, paramObject);
/*     */       
/*     */       case 'I': 
/*  72 */         return SpeciesData.EMPTY.extendWithType('I').constructor[0].invokeBasic(paramMethodType, paramLambdaForm, ValueConversions.widenSubword(paramObject));
/*     */       case 'J': 
/*  74 */         return SpeciesData.EMPTY.extendWithType('J').constructor[0].invokeBasic(paramMethodType, paramLambdaForm, ((Long)paramObject).longValue());
/*     */       case 'F': 
/*  76 */         return SpeciesData.EMPTY.extendWithType('F').constructor[0].invokeBasic(paramMethodType, paramLambdaForm, ((Float)paramObject).floatValue());
/*     */       case 'D': 
/*  78 */         return SpeciesData.EMPTY.extendWithType('D').constructor[0].invokeBasic(paramMethodType, paramLambdaForm, ((Double)paramObject).doubleValue()); }
/*  79 */       throw new InternalError("unexpected xtype: " + paramChar);
/*     */     }
/*     */     catch (Throwable localThrowable) {
/*  82 */       throw MethodHandleStatics.newInternalError(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   static MethodHandle bindSingle(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject) {
/*  87 */     return new Species_L(paramMethodType, paramLambdaForm, paramObject);
/*     */   }
/*     */   
/*     */   MethodHandle cloneExtend(MethodType paramMethodType, LambdaForm paramLambdaForm, char paramChar, Object paramObject) {
/*     */     try {
/*  92 */       switch (paramChar) {
/*  93 */       case 'L':  return cloneExtendL(paramMethodType, paramLambdaForm, paramObject);
/*  94 */       case 'I':  return cloneExtendI(paramMethodType, paramLambdaForm, ValueConversions.widenSubword(paramObject));
/*  95 */       case 'J':  return cloneExtendJ(paramMethodType, paramLambdaForm, ((Long)paramObject).longValue());
/*  96 */       case 'F':  return cloneExtendF(paramMethodType, paramLambdaForm, ((Float)paramObject).floatValue());
/*  97 */       case 'D':  return cloneExtendD(paramMethodType, paramLambdaForm, ((Double)paramObject).doubleValue());
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 100 */       throw MethodHandleStatics.newInternalError(localThrowable);
/*     */     }
/* 102 */     throw new InternalError("unexpected type: " + paramChar);
/*     */   }
/*     */   
/*     */   MethodHandle bindArgument(int paramInt, char paramChar, Object paramObject)
/*     */   {
/* 107 */     MethodType localMethodType = type().dropParameterTypes(paramInt, paramInt + 1);
/* 108 */     LambdaForm localLambdaForm = internalForm().bind(1 + paramInt, speciesData());
/* 109 */     return cloneExtend(localMethodType, localLambdaForm, paramChar, paramObject);
/*     */   }
/*     */   
/*     */   MethodHandle dropArguments(MethodType paramMethodType, int paramInt1, int paramInt2)
/*     */   {
/* 114 */     LambdaForm localLambdaForm = internalForm().addArguments(paramInt1, paramMethodType.parameterList().subList(paramInt1, paramInt1 + paramInt2));
/*     */     try {
/* 116 */       return clone(paramMethodType, localLambdaForm);
/*     */     } catch (Throwable localThrowable) {
/* 118 */       throw MethodHandleStatics.newInternalError(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   MethodHandle permuteArguments(MethodType paramMethodType, int[] paramArrayOfInt)
/*     */   {
/*     */     try {
/* 125 */       return clone(paramMethodType, this.form.permuteArguments(1, paramArrayOfInt, LambdaForm.basicTypes(paramMethodType.parameterList())));
/*     */     } catch (Throwable localThrowable) {
/* 127 */       throw MethodHandleStatics.newInternalError(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static byte extensionIndex(char paramChar)
/*     */   {
/* 134 */     int i = "LIJFD".indexOf(paramChar);
/* 135 */     if (i < 0) throw new InternalError();
/* 136 */     return (byte)i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   abstract SpeciesData speciesData();
/*     */   
/*     */ 
/*     */ 
/*     */   final Object internalProperties()
/*     */   {
/* 147 */     return "/BMH=" + internalValues();
/*     */   }
/*     */   
/*     */   final Object internalValues()
/*     */   {
/* 152 */     Object[] arrayOfObject = new Object[speciesData().fieldCount()];
/* 153 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 154 */       arrayOfObject[i] = arg(i);
/*     */     }
/* 156 */     return Arrays.asList(arrayOfObject);
/*     */   }
/*     */   
/*     */   final Object arg(int paramInt) {
/*     */     try {
/* 161 */       switch (speciesData().fieldType(paramInt)) {
/* 162 */       case 'L':  return argL(paramInt);
/* 163 */       case 'I':  return Integer.valueOf(argI(paramInt));
/* 164 */       case 'F':  return Float.valueOf(argF(paramInt));
/* 165 */       case 'D':  return Double.valueOf(argD(paramInt));
/* 166 */       case 'J':  return Long.valueOf(argJ(paramInt));
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 169 */       throw MethodHandleStatics.newInternalError(localThrowable);
/*     */     }
/* 171 */     throw new InternalError("unexpected type: " + speciesData().types + "." + paramInt); }
/*     */   
/* 173 */   final Object argL(int paramInt) throws Throwable { return speciesData().getters[paramInt].invokeBasic(this); }
/* 174 */   final int argI(int paramInt) throws Throwable { return speciesData().getters[paramInt].invokeBasic(this); }
/* 175 */   final float argF(int paramInt) throws Throwable { return speciesData().getters[paramInt].invokeBasic(this); }
/* 176 */   final double argD(int paramInt) throws Throwable { return speciesData().getters[paramInt].invokeBasic(this); }
/* 177 */   final long argJ(int paramInt) throws Throwable { return speciesData().getters[paramInt].invokeBasic(this); }
/*     */   
/*     */   abstract BoundMethodHandle clone(MethodType paramMethodType, LambdaForm paramLambdaForm) throws Throwable;
/*     */   
/*     */   abstract BoundMethodHandle cloneExtendL(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject) throws Throwable;
/*     */   
/*     */   abstract BoundMethodHandle cloneExtendI(MethodType paramMethodType, LambdaForm paramLambdaForm, int paramInt) throws Throwable;
/*     */   
/*     */   abstract BoundMethodHandle cloneExtendJ(MethodType paramMethodType, LambdaForm paramLambdaForm, long paramLong) throws Throwable;
/*     */   
/*     */   abstract BoundMethodHandle cloneExtendF(MethodType paramMethodType, LambdaForm paramLambdaForm, float paramFloat) throws Throwable;
/*     */   
/*     */   abstract BoundMethodHandle cloneExtendD(MethodType paramMethodType, LambdaForm paramLambdaForm, double paramDouble) throws Throwable;
/*     */   
/*     */   MethodHandle reinvokerTarget() {
/*     */     try {
/* 193 */       return (MethodHandle)argL(0);
/*     */     } catch (Throwable localThrowable) {
/* 195 */       throw MethodHandleStatics.newInternalError(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class Species_L
/*     */     extends BoundMethodHandle
/*     */   {
/*     */     final Object argL0;
/*     */     
/*     */     Species_L(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject)
/*     */     {
/* 207 */       super(paramLambdaForm);
/* 208 */       this.argL0 = paramObject;
/*     */     }
/*     */     
/* 211 */     MethodHandle reinvokerTarget() { return (MethodHandle)this.argL0; }
/*     */     
/*     */ 
/* 214 */     BoundMethodHandle.SpeciesData speciesData() { return SPECIES_DATA; }
/*     */     
/* 216 */     static final BoundMethodHandle.SpeciesData SPECIES_DATA = BoundMethodHandle.SpeciesData.getForClass("L", Species_L.class);
/*     */     
/*     */     final BoundMethodHandle clone(MethodType paramMethodType, LambdaForm paramLambdaForm) throws Throwable {
/* 219 */       return new Species_L(paramMethodType, paramLambdaForm, this.argL0);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle cloneExtendL(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject) throws Throwable {
/* 223 */       return SPECIES_DATA.extendWithIndex(0).constructor[0].invokeBasic(paramMethodType, paramLambdaForm, this.argL0, paramObject);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle cloneExtendI(MethodType paramMethodType, LambdaForm paramLambdaForm, int paramInt) throws Throwable {
/* 227 */       return SPECIES_DATA.extendWithIndex(1).constructor[0].invokeBasic(paramMethodType, paramLambdaForm, this.argL0, paramInt);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle cloneExtendJ(MethodType paramMethodType, LambdaForm paramLambdaForm, long paramLong) throws Throwable {
/* 231 */       return SPECIES_DATA.extendWithIndex(2).constructor[0].invokeBasic(paramMethodType, paramLambdaForm, this.argL0, paramLong);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle cloneExtendF(MethodType paramMethodType, LambdaForm paramLambdaForm, float paramFloat) throws Throwable {
/* 235 */       return SPECIES_DATA.extendWithIndex(3).constructor[0].invokeBasic(paramMethodType, paramLambdaForm, this.argL0, paramFloat);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle cloneExtendD(MethodType paramMethodType, LambdaForm paramLambdaForm, double paramDouble) throws Throwable {
/* 239 */       return SPECIES_DATA.extendWithIndex(4).constructor[0].invokeBasic(paramMethodType, paramLambdaForm, this.argL0, paramDouble);
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
/*     */   static class SpeciesData
/*     */   {
/*     */     final String types;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     final Class<? extends BoundMethodHandle> clazz;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     final MethodHandle[] constructor;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     final MethodHandle[] getters;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     final SpeciesData[] extensions;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static final SpeciesData EMPTY;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private static final HashMap<String, SpeciesData> CACHE;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     int fieldCount()
/*     */     {
/* 342 */       return this.types.length();
/*     */     }
/*     */     
/* 345 */     char fieldType(int paramInt) { return this.types.charAt(paramInt); }
/*     */     
/*     */     public String toString()
/*     */     {
/* 349 */       return "SpeciesData[" + (isPlaceholder() ? "<placeholder>" : this.clazz.getSimpleName()) + ":" + this.types + "]";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     LambdaForm.Name getterName(LambdaForm.Name paramName, int paramInt)
/*     */     {
/* 358 */       MethodHandle localMethodHandle = this.getters[paramInt];
/* 359 */       assert (localMethodHandle != null) : (this + "." + paramInt);
/* 360 */       return new LambdaForm.Name(localMethodHandle, new Object[] { paramName });
/*     */     }
/*     */     
/*     */     LambdaForm.NamedFunction getterFunction(int paramInt) {
/* 364 */       return new LambdaForm.NamedFunction(this.getters[paramInt]);
/*     */     }
/*     */     
/*     */ 
/*     */     private SpeciesData(String paramString, Class<? extends BoundMethodHandle> paramClass)
/*     */     {
/* 370 */       this.types = paramString;
/* 371 */       this.clazz = paramClass;
/* 372 */       if (!INIT_DONE) {
/* 373 */         this.constructor = new MethodHandle[1];
/* 374 */         this.getters = new MethodHandle[paramString.length()];
/*     */       } else {
/* 376 */         this.constructor = BoundMethodHandle.Factory.makeCtors(paramClass, paramString, null);
/* 377 */         this.getters = BoundMethodHandle.Factory.makeGetters(paramClass, paramString, null);
/*     */       }
/* 379 */       this.extensions = new SpeciesData["LIJFD".length()];
/*     */     }
/*     */     
/*     */     private void initForBootstrap() {
/* 383 */       assert (!INIT_DONE);
/* 384 */       if (this.constructor[0] == null) {
/* 385 */         BoundMethodHandle.Factory.makeCtors(this.clazz, this.types, this.constructor);
/* 386 */         BoundMethodHandle.Factory.makeGetters(this.clazz, this.types, this.getters);
/*     */       }
/*     */     }
/*     */     
/*     */     private SpeciesData(String paramString)
/*     */     {
/* 392 */       this.types = paramString;
/* 393 */       this.clazz = null;
/* 394 */       this.constructor = null;
/* 395 */       this.getters = null;
/* 396 */       this.extensions = null; }
/*     */     
/* 398 */     private boolean isPlaceholder() { return this.clazz == null; }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     SpeciesData extendWithType(char paramChar)
/*     */     {
/* 405 */       int i = BoundMethodHandle.extensionIndex(paramChar);
/* 406 */       SpeciesData localSpeciesData = this.extensions[i];
/* 407 */       if (localSpeciesData != null) return localSpeciesData;
/* 408 */       this.extensions[i] = (localSpeciesData = get(this.types + paramChar));
/* 409 */       return localSpeciesData;
/*     */     }
/*     */     
/*     */     SpeciesData extendWithIndex(byte paramByte) {
/* 413 */       SpeciesData localSpeciesData = this.extensions[paramByte];
/* 414 */       if (localSpeciesData != null) return localSpeciesData;
/* 415 */       this.extensions[paramByte] = (localSpeciesData = get(this.types + "LIJFD".charAt(paramByte)));
/* 416 */       return localSpeciesData;
/*     */     }
/*     */     
/*     */     private static SpeciesData get(String paramString)
/*     */     {
/* 421 */       SpeciesData localSpeciesData = lookupCache(paramString);
/* 422 */       if (!localSpeciesData.isPlaceholder())
/* 423 */         return localSpeciesData;
/* 424 */       synchronized (localSpeciesData)
/*     */       {
/*     */ 
/* 427 */         if (lookupCache(paramString).isPlaceholder()) {
/* 428 */           BoundMethodHandle.Factory.generateConcreteBMHClass(paramString);
/*     */         }
/*     */       }
/* 431 */       localSpeciesData = lookupCache(paramString);
/*     */       
/* 433 */       assert ((localSpeciesData != null) && (!localSpeciesData.isPlaceholder()));
/* 434 */       return localSpeciesData;
/*     */     }
/*     */     
/*     */ 
/* 438 */     static SpeciesData getForClass(String paramString, Class<? extends BoundMethodHandle> paramClass) { return updateCache(paramString, new SpeciesData(paramString, paramClass)); }
/*     */     
/*     */     private static synchronized SpeciesData lookupCache(String paramString) {
/* 441 */       SpeciesData localSpeciesData = (SpeciesData)CACHE.get(paramString);
/* 442 */       if (localSpeciesData != null) return localSpeciesData;
/* 443 */       localSpeciesData = new SpeciesData(paramString);
/* 444 */       assert (localSpeciesData.isPlaceholder());
/* 445 */       CACHE.put(paramString, localSpeciesData);
/* 446 */       return localSpeciesData;
/*     */     }
/*     */     
/*     */     private static synchronized SpeciesData updateCache(String paramString, SpeciesData paramSpeciesData) { SpeciesData localSpeciesData;
/* 450 */       assert (((localSpeciesData = (SpeciesData)CACHE.get(paramString)) == null) || (localSpeciesData.isPlaceholder()));
/* 451 */       assert (!paramSpeciesData.isPlaceholder());
/* 452 */       CACHE.put(paramString, paramSpeciesData);
/* 453 */       return paramSpeciesData;
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 367 */       EMPTY = new SpeciesData("", BoundMethodHandle.class);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 400 */       CACHE = new HashMap();
/* 401 */       CACHE.put("", EMPTY);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 458 */       Class localClass1 = BoundMethodHandle.class;
/* 459 */       SpeciesData localSpeciesData1 = BoundMethodHandle.SPECIES_DATA;
/* 460 */       assert ((localSpeciesData1 == null) || (localSpeciesData1 == lookupCache(""))) : localSpeciesData1;
/*     */       try {
/* 462 */         for (Class localClass2 : localClass1.getDeclaredClasses()) {
/* 463 */           if (localClass1.isAssignableFrom(localClass2)) {
/* 464 */             Class localClass3 = localClass2.asSubclass(BoundMethodHandle.class);
/* 465 */             SpeciesData localSpeciesData3 = BoundMethodHandle.Factory.speciesDataFromConcreteBMHClass(localClass3);
/* 466 */             assert (localSpeciesData3 != null) : localClass3.getName();
/* 467 */             assert (localSpeciesData3.clazz == localClass3);
/* 468 */             assert (localSpeciesData3 == lookupCache(localSpeciesData3.types));
/*     */           }
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 472 */         throw MethodHandleStatics.newInternalError(localThrowable);
/*     */       }
/*     */       
/* 475 */       for (SpeciesData localSpeciesData2 : CACHE.values()) {
/* 476 */         localSpeciesData2.initForBootstrap();
/*     */       }
/*     */     }
/*     */     
/* 480 */     private static final boolean INIT_DONE = Boolean.TRUE.booleanValue();
/*     */   }
/*     */   
/*     */   static SpeciesData getSpeciesData(String paramString)
/*     */   {
/* 485 */     return SpeciesData.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   static class Factory
/*     */   {
/*     */     static final String JLO_SIG = "Ljava/lang/Object;";
/*     */     
/*     */     static final String JLS_SIG = "Ljava/lang/String;";
/*     */     
/*     */     static final String JLC_SIG = "Ljava/lang/Class;";
/*     */     
/*     */     static final String MH = "java/lang/invoke/MethodHandle";
/*     */     
/*     */     static final String MH_SIG = "Ljava/lang/invoke/MethodHandle;";
/*     */     
/*     */     static final String BMH = "java/lang/invoke/BoundMethodHandle";
/*     */     
/*     */     static final String BMH_SIG = "Ljava/lang/invoke/BoundMethodHandle;";
/*     */     
/*     */     static final String SPECIES_DATA = "java/lang/invoke/BoundMethodHandle$SpeciesData";
/*     */     
/*     */     static final String SPECIES_DATA_SIG = "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String SPECIES_PREFIX_NAME = "Species_";
/*     */     
/*     */     static final String SPECIES_PREFIX_PATH = "java/lang/invoke/BoundMethodHandle$Species_";
/*     */     
/*     */     static final String BMHSPECIES_DATA_EWI_SIG = "(B)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String BMHSPECIES_DATA_GFC_SIG = "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String MYSPECIES_DATA_SIG = "()Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String VOID_SIG = "()V";
/*     */     
/*     */     static final String SIG_INCIPIT = "(Ljava/lang/invoke/MethodType;Ljava/lang/invoke/LambdaForm;";
/* 522 */     static final Class<?>[] TYPES = { Object.class, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE };
/*     */     
/* 524 */     static final String[] E_THROWABLE = { "java/lang/Throwable" };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     static Class<? extends BoundMethodHandle> generateConcreteBMHClass(String paramString)
/*     */     {
/* 587 */       ClassWriter localClassWriter = new ClassWriter(3);
/*     */       
/* 589 */       String str1 = "java/lang/invoke/BoundMethodHandle$Species_" + paramString;
/* 590 */       String str2 = "Species_" + paramString;
/*     */       
/* 592 */       localClassWriter.visit(50, 48, str1, null, "java/lang/invoke/BoundMethodHandle", null);
/* 593 */       localClassWriter.visitSource(str2, null);
/*     */       
/*     */ 
/* 596 */       localClassWriter.visitField(8, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;", null, null).visitEnd();
/*     */       
/*     */ 
/* 599 */       for (int i = 0; i < paramString.length(); i++) {
/* 600 */         j = paramString.charAt(i);
/* 601 */         String str3 = makeFieldName(paramString, i);
/* 602 */         String str4 = j == 76 ? "Ljava/lang/Object;" : String.valueOf(j);
/* 603 */         localClassWriter.visitField(16, str3, str4, null, null).visitEnd();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 609 */       MethodVisitor localMethodVisitor = localClassWriter.visitMethod(0, "<init>", makeSignature(paramString, true), null, null);
/* 610 */       localMethodVisitor.visitCode();
/* 611 */       localMethodVisitor.visitVarInsn(25, 0);
/* 612 */       localMethodVisitor.visitVarInsn(25, 1);
/* 613 */       localMethodVisitor.visitVarInsn(25, 2);
/*     */       
/* 615 */       localMethodVisitor.visitMethodInsn(183, "java/lang/invoke/BoundMethodHandle", "<init>", makeSignature("", true));
/*     */       
/* 617 */       int j = 0; int m; for (int k = 0; j < paramString.length(); k++)
/*     */       {
/* 619 */         m = paramString.charAt(j);
/* 620 */         localMethodVisitor.visitVarInsn(25, 0);
/* 621 */         localMethodVisitor.visitVarInsn(typeLoadOp(m), k + 3);
/* 622 */         localMethodVisitor.visitFieldInsn(181, str1, makeFieldName(paramString, j), typeSig(m));
/* 623 */         if ((m == 74) || (m == 68)) {
/* 624 */           k++;
/*     */         }
/* 617 */         j++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 628 */       localMethodVisitor.visitInsn(177);
/* 629 */       localMethodVisitor.visitMaxs(0, 0);
/* 630 */       localMethodVisitor.visitEnd();
/*     */       
/*     */ 
/* 633 */       localMethodVisitor = localClassWriter.visitMethod(16, "reinvokerTarget", "()Ljava/lang/invoke/MethodHandle;", null, null);
/* 634 */       localMethodVisitor.visitCode();
/* 635 */       localMethodVisitor.visitVarInsn(25, 0);
/* 636 */       localMethodVisitor.visitFieldInsn(180, str1, "argL0", "Ljava/lang/Object;");
/* 637 */       localMethodVisitor.visitTypeInsn(192, "java/lang/invoke/MethodHandle");
/* 638 */       localMethodVisitor.visitInsn(176);
/* 639 */       localMethodVisitor.visitMaxs(0, 0);
/* 640 */       localMethodVisitor.visitEnd();
/*     */       
/*     */ 
/* 643 */       localMethodVisitor = localClassWriter.visitMethod(16, "speciesData", "()Ljava/lang/invoke/BoundMethodHandle$SpeciesData;", null, null);
/* 644 */       localMethodVisitor.visitCode();
/* 645 */       localMethodVisitor.visitFieldInsn(178, str1, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 646 */       localMethodVisitor.visitInsn(176);
/* 647 */       localMethodVisitor.visitMaxs(0, 0);
/* 648 */       localMethodVisitor.visitEnd();
/*     */       
/*     */ 
/* 651 */       localMethodVisitor = localClassWriter.visitMethod(16, "clone", makeSignature("", false), null, E_THROWABLE);
/* 652 */       localMethodVisitor.visitCode();
/*     */       
/*     */ 
/* 655 */       localMethodVisitor.visitVarInsn(25, 0);
/* 656 */       localMethodVisitor.visitFieldInsn(178, str1, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 657 */       localMethodVisitor.visitFieldInsn(180, "java/lang/invoke/BoundMethodHandle$SpeciesData", "constructor", "[Ljava/lang/invoke/MethodHandle;");
/* 658 */       localMethodVisitor.visitInsn(3);
/* 659 */       localMethodVisitor.visitInsn(50);
/*     */       
/* 661 */       localMethodVisitor.visitVarInsn(25, 1);
/* 662 */       localMethodVisitor.visitVarInsn(25, 2);
/*     */       
/* 664 */       emitPushFields(paramString, str1, localMethodVisitor);
/*     */       
/* 666 */       localMethodVisitor.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", makeSignature(paramString, false));
/* 667 */       localMethodVisitor.visitInsn(176);
/* 668 */       localMethodVisitor.visitMaxs(0, 0);
/* 669 */       localMethodVisitor.visitEnd();
/*     */       
/*     */ 
/* 672 */       for (Class localClass2 : TYPES) {
/* 673 */         char c = Wrapper.basicTypeChar(localClass2);
/* 674 */         localMethodVisitor = localClassWriter.visitMethod(16, "cloneExtend" + c, makeSignature(String.valueOf(c), false), null, E_THROWABLE);
/* 675 */         localMethodVisitor.visitCode();
/*     */         
/*     */ 
/* 678 */         localMethodVisitor.visitFieldInsn(178, str1, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 679 */         int n = 3 + BoundMethodHandle.extensionIndex(c);
/* 680 */         assert (n <= 8);
/* 681 */         localMethodVisitor.visitInsn(n);
/* 682 */         localMethodVisitor.visitMethodInsn(182, "java/lang/invoke/BoundMethodHandle$SpeciesData", "extendWithIndex", "(B)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 683 */         localMethodVisitor.visitFieldInsn(180, "java/lang/invoke/BoundMethodHandle$SpeciesData", "constructor", "[Ljava/lang/invoke/MethodHandle;");
/* 684 */         localMethodVisitor.visitInsn(3);
/* 685 */         localMethodVisitor.visitInsn(50);
/*     */         
/* 687 */         localMethodVisitor.visitVarInsn(25, 1);
/* 688 */         localMethodVisitor.visitVarInsn(25, 2);
/*     */         
/* 690 */         emitPushFields(paramString, str1, localMethodVisitor);
/*     */         
/* 692 */         localMethodVisitor.visitVarInsn(typeLoadOp(c), 3);
/*     */         
/* 694 */         localMethodVisitor.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", makeSignature(paramString + c, false));
/* 695 */         localMethodVisitor.visitInsn(176);
/* 696 */         localMethodVisitor.visitMaxs(0, 0);
/* 697 */         localMethodVisitor.visitEnd();
/*     */       }
/*     */       
/*     */ 
/* 701 */       localMethodVisitor = localClassWriter.visitMethod(8, "<clinit>", "()V", null, null);
/* 702 */       localMethodVisitor.visitCode();
/* 703 */       localMethodVisitor.visitLdcInsn(paramString);
/* 704 */       localMethodVisitor.visitLdcInsn(Type.getObjectType(str1));
/* 705 */       localMethodVisitor.visitMethodInsn(184, "java/lang/invoke/BoundMethodHandle$SpeciesData", "getForClass", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 706 */       localMethodVisitor.visitFieldInsn(179, str1, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 707 */       localMethodVisitor.visitInsn(177);
/* 708 */       localMethodVisitor.visitMaxs(0, 0);
/* 709 */       localMethodVisitor.visitEnd();
/*     */       
/* 711 */       localClassWriter.visitEnd();
/*     */       
/*     */ 
/* 714 */       ??? = localClassWriter.toByteArray();
/* 715 */       InvokerBytecodeGenerator.maybeDump(str1, (byte[])???);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 720 */       Class localClass1 = MethodHandleStatics.UNSAFE.defineClass(str1, (byte[])???, 0, ???.length, BoundMethodHandle.class.getClassLoader(), null).asSubclass(BoundMethodHandle.class);
/* 721 */       MethodHandleStatics.UNSAFE.ensureClassInitialized(localClass1);
/*     */       
/* 723 */       return localClass1;
/*     */     }
/*     */     
/*     */     private static int typeLoadOp(char paramChar) {
/* 727 */       switch (paramChar) {
/* 728 */       case 'L':  return 25;
/* 729 */       case 'I':  return 21;
/* 730 */       case 'J':  return 22;
/* 731 */       case 'F':  return 23;
/* 732 */       case 'D':  return 24; }
/* 733 */       throw new InternalError("unrecognized type " + paramChar);
/*     */     }
/*     */     
/*     */     private static void emitPushFields(String paramString1, String paramString2, MethodVisitor paramMethodVisitor)
/*     */     {
/* 738 */       for (int i = 0; i < paramString1.length(); i++) {
/* 739 */         char c = paramString1.charAt(i);
/* 740 */         paramMethodVisitor.visitVarInsn(25, 0);
/* 741 */         paramMethodVisitor.visitFieldInsn(180, paramString2, makeFieldName(paramString1, i), typeSig(c));
/*     */       }
/*     */     }
/*     */     
/*     */     static String typeSig(char paramChar) {
/* 746 */       return paramChar == 'L' ? "Ljava/lang/Object;" : String.valueOf(paramChar);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private static MethodHandle makeGetter(Class<?> paramClass, String paramString, int paramInt)
/*     */     {
/* 754 */       String str = makeFieldName(paramString, paramInt);
/* 755 */       Class localClass = Wrapper.forBasicType(paramString.charAt(paramInt)).primitiveType();
/*     */       try {
/* 757 */         return BoundMethodHandle.LOOKUP.findGetter(paramClass, str, localClass);
/*     */       } catch (NoSuchFieldException|IllegalAccessException localNoSuchFieldException) {
/* 759 */         throw MethodHandleStatics.newInternalError(localNoSuchFieldException);
/*     */       }
/*     */     }
/*     */     
/*     */     static MethodHandle[] makeGetters(Class<?> paramClass, String paramString, MethodHandle[] paramArrayOfMethodHandle) {
/* 764 */       if (paramArrayOfMethodHandle == null) paramArrayOfMethodHandle = new MethodHandle[paramString.length()];
/* 765 */       for (int i = 0; i < paramArrayOfMethodHandle.length; i++) {
/* 766 */         paramArrayOfMethodHandle[i] = makeGetter(paramClass, paramString, i);
/* 767 */         assert (paramArrayOfMethodHandle[i].internalMemberName().getDeclaringClass() == paramClass);
/*     */       }
/* 769 */       return paramArrayOfMethodHandle;
/*     */     }
/*     */     
/*     */     static MethodHandle[] makeCtors(Class<? extends BoundMethodHandle> paramClass, String paramString, MethodHandle[] paramArrayOfMethodHandle) {
/* 773 */       if (paramArrayOfMethodHandle == null) paramArrayOfMethodHandle = new MethodHandle[1];
/* 774 */       paramArrayOfMethodHandle[0] = makeCbmhCtor(paramClass, paramString);
/* 775 */       return paramArrayOfMethodHandle;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     static BoundMethodHandle.SpeciesData speciesDataFromConcreteBMHClass(Class<? extends BoundMethodHandle> paramClass)
/*     */     {
/*     */       try
/*     */       {
/* 784 */         Field localField = paramClass.getDeclaredField("SPECIES_DATA");
/* 785 */         return (BoundMethodHandle.SpeciesData)localField.get(null);
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 787 */         throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private static String makeFieldName(String paramString, int paramInt)
/*     */     {
/* 797 */       assert ((paramInt >= 0) && (paramInt < paramString.length()));
/* 798 */       return "arg" + paramString.charAt(paramInt) + paramInt;
/*     */     }
/*     */     
/*     */     private static String makeSignature(String paramString, boolean paramBoolean) {
/* 802 */       StringBuilder localStringBuilder = new StringBuilder("(Ljava/lang/invoke/MethodType;Ljava/lang/invoke/LambdaForm;");
/* 803 */       for (char c : paramString.toCharArray()) {
/* 804 */         localStringBuilder.append(typeSig(c));
/*     */       }
/* 806 */       return ')' + (paramBoolean ? "V" : BMH_SIG);
/*     */     }
/*     */     
/*     */     static MethodHandle makeCbmhCtor(Class<? extends BoundMethodHandle> paramClass, String paramString) {
/*     */       try {
/* 811 */         return linkConstructor(BoundMethodHandle.LOOKUP.findConstructor(paramClass, MethodType.fromMethodDescriptorString(makeSignature(paramString, true), null)));
/*     */       } catch (NoSuchMethodException|IllegalAccessException|IllegalArgumentException|TypeNotPresentException localNoSuchMethodException) {
/* 813 */         throw MethodHandleStatics.newInternalError(localNoSuchMethodException);
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
/*     */     private static MethodHandle linkConstructor(MethodHandle paramMethodHandle)
/*     */     {
/* 830 */       LambdaForm localLambdaForm = paramMethodHandle.form;
/* 831 */       int i = localLambdaForm.names.length - 1;
/* 832 */       LambdaForm.Name localName1 = localLambdaForm.names[i];
/* 833 */       MemberName localMemberName1 = localName1.function.member;
/* 834 */       MethodType localMethodType1 = localMemberName1.getInvocationType();
/*     */       
/*     */ 
/*     */ 
/* 838 */       MethodType localMethodType2 = localMethodType1.changeParameterType(0, BoundMethodHandle.class).appendParameterTypes(new Class[] { MemberName.class });
/* 839 */       MemberName localMemberName2 = new MemberName(MethodHandle.class, "linkToSpecial", localMethodType2, (byte)6);
/*     */       try {
/* 841 */         localMemberName2 = MemberName.getFactory().resolveOrFail((byte)6, localMemberName2, null, NoSuchMethodException.class);
/* 842 */         if ((!$assertionsDisabled) && (!localMemberName2.isStatic())) throw new AssertionError();
/*     */       } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 844 */         throw MethodHandleStatics.newInternalError(localReflectiveOperationException);
/*     */       }
/*     */       
/* 847 */       Object[] arrayOfObject = Arrays.copyOf(localName1.arguments, localName1.arguments.length + 1);
/* 848 */       arrayOfObject[(arrayOfObject.length - 1)] = localMemberName1;
/*     */       
/* 850 */       LambdaForm.NamedFunction localNamedFunction = new LambdaForm.NamedFunction(localMemberName2);
/* 851 */       LambdaForm.Name localName2 = new LambdaForm.Name(localNamedFunction, arrayOfObject);
/* 852 */       localName2.initIndex(i);
/* 853 */       localLambdaForm.names[i] = localName2;
/* 854 */       return paramMethodHandle;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 859 */   private static final MethodHandles.Lookup LOOKUP = MethodHandles.Lookup.IMPL_LOOKUP;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 864 */   static final SpeciesData SPECIES_DATA = SpeciesData.EMPTY;
/*     */   
/* 866 */   private static final SpeciesData[] SPECIES_DATA_CACHE = new SpeciesData[5];
/*     */   
/* 868 */   private static SpeciesData checkCache(int paramInt, String paramString) { int i = paramInt - 1;
/* 869 */     SpeciesData localSpeciesData = SPECIES_DATA_CACHE[i];
/* 870 */     if (localSpeciesData != null) return localSpeciesData;
/* 871 */     SPECIES_DATA_CACHE[i] = (localSpeciesData = getSpeciesData(paramString));
/* 872 */     return localSpeciesData; }
/*     */   
/* 874 */   static SpeciesData speciesData_L() { return checkCache(1, "L"); }
/* 875 */   static SpeciesData speciesData_LL() { return checkCache(2, "LL"); }
/* 876 */   static SpeciesData speciesData_LLL() { return checkCache(3, "LLL"); }
/* 877 */   static SpeciesData speciesData_LLLL() { return checkCache(4, "LLLL"); }
/* 878 */   static SpeciesData speciesData_LLLLL() { return checkCache(5, "LLLLL"); }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/BoundMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */