/*     */ package java.lang.invoke;
/*     */ 
/*     */ import sun.invoke.util.Wrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MethodTypeForm
/*     */ {
/*     */   final int[] argToSlotTable;
/*     */   final int[] slotToArgTable;
/*     */   final long argCounts;
/*     */   final long primCounts;
/*     */   final int vmslots;
/*     */   final MethodType erasedType;
/*     */   final MethodType basicType;
/*     */   @Stable
/*     */   String typeString;
/*     */   @Stable
/*     */   MethodHandle genericInvoker;
/*     */   @Stable
/*     */   MethodHandle basicInvoker;
/*     */   @Stable
/*     */   MethodHandle namedFunctionInvoker;
/*     */   @Stable
/*     */   final LambdaForm[] lambdaForms;
/*     */   static final int LF_INVVIRTUAL = 0;
/*     */   static final int LF_INVSTATIC = 1;
/*     */   static final int LF_INVSPECIAL = 2;
/*     */   static final int LF_NEWINVSPECIAL = 3;
/*     */   static final int LF_INVINTERFACE = 4;
/*     */   static final int LF_INVSTATIC_INIT = 5;
/*     */   static final int LF_INTERPRET = 6;
/*     */   static final int LF_COUNTER = 7;
/*     */   static final int LF_REINVOKE = 8;
/*     */   static final int LF_EX_LINKER = 9;
/*     */   static final int LF_EX_INVOKER = 10;
/*     */   static final int LF_GEN_LINKER = 11;
/*     */   static final int LF_GEN_INVOKER = 12;
/*     */   static final int LF_CS_LINKER = 13;
/*     */   static final int LF_MH_LINKER = 14;
/*     */   static final int LF_GWC = 15;
/*     */   static final int LF_LIMIT = 16;
/*     */   public static final int NO_CHANGE = 0;
/*     */   public static final int ERASE = 1;
/*     */   public static final int WRAP = 2;
/*     */   public static final int UNWRAP = 3;
/*     */   public static final int INTS = 4;
/*     */   public static final int LONGS = 5;
/*     */   public static final int RAW_RETURN = 6;
/*     */   
/*     */   public MethodType erasedType()
/*     */   {
/*  83 */     return this.erasedType;
/*     */   }
/*     */   
/*     */   public MethodType basicType() {
/*  87 */     return this.basicType;
/*     */   }
/*     */   
/*     */   public LambdaForm cachedLambdaForm(int paramInt) {
/*  91 */     return this.lambdaForms[paramInt];
/*     */   }
/*     */   
/*     */   public synchronized LambdaForm setCachedLambdaForm(int paramInt, LambdaForm paramLambdaForm)
/*     */   {
/*  96 */     LambdaForm localLambdaForm = this.lambdaForms[paramInt];
/*  97 */     if (localLambdaForm != null) return localLambdaForm;
/*  98 */     return this.lambdaForms[paramInt] = paramLambdaForm;
/*     */   }
/*     */   
/*     */   public MethodHandle basicInvoker() {
/* 102 */     assert (this.erasedType == this.basicType) : ("erasedType: " + this.erasedType + " != basicType: " + this.basicType);
/* 103 */     Object localObject = this.basicInvoker;
/* 104 */     if (localObject != null) return (MethodHandle)localObject;
/* 105 */     localObject = DirectMethodHandle.make(invokeBasicMethod(this.basicType));
/* 106 */     this.basicInvoker = ((MethodHandle)localObject);
/* 107 */     return (MethodHandle)localObject;
/*     */   }
/*     */   
/*     */   static MemberName invokeBasicMethod(MethodType paramMethodType)
/*     */   {
/* 112 */     assert (paramMethodType == paramMethodType.basicType());
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 117 */       return MethodHandles.Lookup.IMPL_LOOKUP.resolveOrFail((byte)5, MethodHandle.class, "invokeBasic", paramMethodType);
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 119 */       throw MethodHandleStatics.newInternalError("JVM cannot find invoker for " + paramMethodType, localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MethodTypeForm(MethodType paramMethodType)
/*     */   {
/* 129 */     this.erasedType = paramMethodType;
/*     */     
/* 131 */     Class[] arrayOfClass1 = paramMethodType.ptypes();
/* 132 */     int i = arrayOfClass1.length;
/* 133 */     int j = i;
/* 134 */     int k = 1;
/* 135 */     int m = 1;
/*     */     
/* 137 */     int[] arrayOfInt1 = null;int[] arrayOfInt2 = null;
/*     */     
/*     */ 
/* 140 */     int n = 0;int i1 = 0;int i2 = 0;int i3 = 0;
/* 141 */     Class[] arrayOfClass2 = arrayOfClass1;
/* 142 */     Class[] arrayOfClass3 = arrayOfClass2;
/* 143 */     Wrapper localWrapper1; for (int i4 = 0; i4 < arrayOfClass2.length; i4++) {
/* 144 */       localClass2 = arrayOfClass2[i4];
/* 145 */       if (localClass2 != Object.class) {
/* 146 */         n++;
/* 147 */         localWrapper1 = Wrapper.forPrimitiveType(localClass2);
/* 148 */         if (localWrapper1.isDoubleWord()) i1++;
/* 149 */         if ((localWrapper1.isSubwordOrInt()) && (localClass2 != Integer.TYPE)) {
/* 150 */           if (arrayOfClass3 == arrayOfClass2)
/* 151 */             arrayOfClass3 = (Class[])arrayOfClass3.clone();
/* 152 */           arrayOfClass3[i4] = Integer.TYPE;
/*     */         }
/*     */       }
/*     */     }
/* 156 */     j += i1;
/* 157 */     Class localClass1 = paramMethodType.returnType();
/* 158 */     Class localClass2 = localClass1;
/* 159 */     if (localClass1 != Object.class) {
/* 160 */       i2++;
/* 161 */       localWrapper1 = Wrapper.forPrimitiveType(localClass1);
/* 162 */       if (localWrapper1.isDoubleWord()) i3++;
/* 163 */       if ((localWrapper1.isSubwordOrInt()) && (localClass1 != Integer.TYPE)) {
/* 164 */         localClass2 = Integer.TYPE;
/*     */       }
/* 166 */       if (localClass1 == Void.TYPE) {
/* 167 */         k = m = 0;
/*     */       } else
/* 169 */         m += i3;
/*     */     }
/* 171 */     if ((arrayOfClass2 == arrayOfClass3) && (localClass2 == localClass1)) {
/* 172 */       this.basicType = paramMethodType;
/*     */     } else
/* 174 */       this.basicType = MethodType.makeImpl(localClass2, arrayOfClass3, true);
/*     */     int i5;
/* 176 */     int i6; if (i1 != 0) {
/* 177 */       i5 = i + i1;
/* 178 */       arrayOfInt2 = new int[i5 + 1];
/* 179 */       arrayOfInt1 = new int[1 + i];
/* 180 */       arrayOfInt1[0] = i5;
/* 181 */       for (i6 = 0; i6 < arrayOfClass2.length; i6++) {
/* 182 */         Class localClass3 = arrayOfClass2[i6];
/* 183 */         Wrapper localWrapper2 = Wrapper.forBasicType(localClass3);
/* 184 */         if (localWrapper2.isDoubleWord()) i5--;
/* 185 */         i5--;
/* 186 */         arrayOfInt2[i5] = (i6 + 1);
/* 187 */         arrayOfInt1[(1 + i6)] = i5;
/*     */       }
/* 189 */       assert (i5 == 0);
/*     */     }
/* 191 */     this.primCounts = pack(i3, i2, i1, n);
/* 192 */     this.argCounts = pack(m, k, j, i);
/* 193 */     if (arrayOfInt2 == null) {
/* 194 */       i5 = i;
/* 195 */       arrayOfInt2 = new int[i5 + 1];
/* 196 */       arrayOfInt1 = new int[1 + i];
/* 197 */       arrayOfInt1[0] = i5;
/* 198 */       for (i6 = 0; i6 < i; i6++) {
/* 199 */         i5--;
/* 200 */         arrayOfInt2[i5] = (i6 + 1);
/* 201 */         arrayOfInt1[(1 + i6)] = i5;
/*     */       }
/*     */     }
/* 204 */     this.argToSlotTable = arrayOfInt1;
/* 205 */     this.slotToArgTable = arrayOfInt2;
/*     */     
/* 207 */     if (j >= 256) { throw MethodHandleStatics.newIllegalArgumentException("too many arguments");
/*     */     }
/*     */     
/* 210 */     this.vmslots = parameterSlotCount();
/*     */     
/* 212 */     if (this.basicType == paramMethodType) {
/* 213 */       this.lambdaForms = new LambdaForm[16];
/*     */     } else {
/* 215 */       this.lambdaForms = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static long pack(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 220 */     assert (((paramInt1 | paramInt2 | paramInt3 | paramInt4) & 0xFFFF0000) == 0);
/* 221 */     long l1 = paramInt1 << 16 | paramInt2;long l2 = paramInt3 << 16 | paramInt4;
/* 222 */     return l1 << 32 | l2;
/*     */   }
/*     */   
/* 225 */   private static char unpack(long paramLong, int paramInt) { assert (paramInt <= 3);
/* 226 */     return (char)(int)(paramLong >> (3 - paramInt) * 16);
/*     */   }
/*     */   
/*     */   public int parameterCount() {
/* 230 */     return unpack(this.argCounts, 3);
/*     */   }
/*     */   
/* 233 */   public int parameterSlotCount() { return unpack(this.argCounts, 2); }
/*     */   
/*     */   public int returnCount() {
/* 236 */     return unpack(this.argCounts, 1);
/*     */   }
/*     */   
/* 239 */   public int returnSlotCount() { return unpack(this.argCounts, 0); }
/*     */   
/*     */   public int primitiveParameterCount() {
/* 242 */     return unpack(this.primCounts, 3);
/*     */   }
/*     */   
/* 245 */   public int longPrimitiveParameterCount() { return unpack(this.primCounts, 2); }
/*     */   
/*     */   public int primitiveReturnCount() {
/* 248 */     return unpack(this.primCounts, 1);
/*     */   }
/*     */   
/* 251 */   public int longPrimitiveReturnCount() { return unpack(this.primCounts, 0); }
/*     */   
/*     */ 
/* 254 */   public boolean hasPrimitives() { return this.primCounts != 0L; }
/*     */   
/*     */   public boolean hasNonVoidPrimitives() {
/* 257 */     if (this.primCounts == 0L) return false;
/* 258 */     if (primitiveParameterCount() != 0) return true;
/* 259 */     return (primitiveReturnCount() != 0) && (returnCount() != 0);
/*     */   }
/*     */   
/* 262 */   public boolean hasLongPrimitives() { return (longPrimitiveParameterCount() | longPrimitiveReturnCount()) != 0; }
/*     */   
/*     */   public int parameterToArgSlot(int paramInt) {
/* 265 */     return this.argToSlotTable[(1 + paramInt)];
/*     */   }
/*     */   
/*     */ 
/*     */   public int argSlotToParameter(int paramInt)
/*     */   {
/* 271 */     return this.slotToArgTable[paramInt] - 1;
/*     */   }
/*     */   
/*     */   static MethodTypeForm findForm(MethodType paramMethodType) {
/* 275 */     MethodType localMethodType = canonicalize(paramMethodType, 1, 1);
/* 276 */     if (localMethodType == null)
/*     */     {
/* 278 */       return new MethodTypeForm(paramMethodType);
/*     */     }
/*     */     
/* 281 */     return localMethodType.form();
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
/*     */   public static MethodType canonicalize(MethodType paramMethodType, int paramInt1, int paramInt2)
/*     */   {
/* 302 */     Class[] arrayOfClass1 = paramMethodType.ptypes();
/* 303 */     Class[] arrayOfClass2 = canonicalizes(arrayOfClass1, paramInt2);
/* 304 */     Class localClass1 = paramMethodType.returnType();
/* 305 */     Class localClass2 = canonicalize(localClass1, paramInt1);
/* 306 */     if ((arrayOfClass2 == null) && (localClass2 == null))
/*     */     {
/* 308 */       return null;
/*     */     }
/*     */     
/* 311 */     if (localClass2 == null) localClass2 = localClass1;
/* 312 */     if (arrayOfClass2 == null) arrayOfClass2 = arrayOfClass1;
/* 313 */     return MethodType.makeImpl(localClass2, arrayOfClass2, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Class<?> canonicalize(Class<?> paramClass, int paramInt)
/*     */   {
/* 321 */     if (paramClass != Object.class)
/*     */     {
/* 323 */       if (!paramClass.isPrimitive()) {
/* 324 */         switch (paramInt) {
/*     */         case 3: 
/* 326 */           Class localClass = Wrapper.asPrimitiveType(paramClass);
/* 327 */           if (localClass != paramClass) return localClass;
/*     */           break;
/*     */         case 1: 
/*     */         case 6: 
/* 331 */           return Object.class;
/*     */         }
/* 333 */       } else if (paramClass == Void.TYPE)
/*     */       {
/* 335 */         switch (paramInt) {
/*     */         case 6: 
/* 337 */           return Integer.TYPE;
/*     */         case 2: 
/* 339 */           return Void.class;
/*     */         }
/*     */         
/*     */       } else {
/* 343 */         switch (paramInt) {
/*     */         case 2: 
/* 345 */           return Wrapper.asWrapperType(paramClass);
/*     */         case 4: 
/* 347 */           if ((paramClass == Integer.TYPE) || (paramClass == Long.TYPE))
/* 348 */             return null;
/* 349 */           if (paramClass == Double.TYPE)
/* 350 */             return Long.TYPE;
/* 351 */           return Integer.TYPE;
/*     */         case 5: 
/* 353 */           if (paramClass == Long.TYPE)
/* 354 */             return null;
/* 355 */           return Long.TYPE;
/*     */         case 6: 
/* 357 */           if ((paramClass == Integer.TYPE) || (paramClass == Long.TYPE) || (paramClass == Float.TYPE) || (paramClass == Double.TYPE))
/*     */           {
/* 359 */             return null;
/*     */           }
/* 361 */           return Integer.TYPE;
/*     */         }
/*     */       }
/*     */     }
/* 365 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Class<?>[] canonicalizes(Class<?>[] paramArrayOfClass, int paramInt)
/*     */   {
/* 372 */     Class[] arrayOfClass = null;
/* 373 */     int i = paramArrayOfClass.length; for (int j = 0; j < i; j++) {
/* 374 */       Class localClass = canonicalize(paramArrayOfClass[j], paramInt);
/* 375 */       if (localClass == Void.TYPE)
/* 376 */         localClass = null;
/* 377 */       if (localClass != null) {
/* 378 */         if (arrayOfClass == null)
/* 379 */           arrayOfClass = (Class[])paramArrayOfClass.clone();
/* 380 */         arrayOfClass[j] = localClass;
/*     */       }
/*     */     }
/* 383 */     return arrayOfClass;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 388 */     return "Form" + this.erasedType;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/MethodTypeForm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */